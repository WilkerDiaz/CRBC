/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.mediadoresbd
 * Programa   : BeansSincronizador.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 19/03/2004 03:48:33 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 2.0
 * Fecha       : 20/06/2006 
 * Analista    : BECO
 * Descripción : Cambio para sincronizar cliente no registrado para que conserve
 *               la última modificación               
 * =============================================================================
 * Versión     : 2.0
 * Fecha       : 26/05/2006 08:00:00 
 * Analista    : BECO
 * Descripción : Política de Seleccion de registros a sincronizar tomando en 
 *               cuenta la fecha y hora de actualización (yyyyMMddHHmmss) 
 * =============================================================================
 * Versión     : 2.0
 * Fecha       : 10/03/2006 14:57:00 
 * Analista    : BECO
 * Descripción : Modificaciones de Políticas de Seleccion de Registros a 
 *               sincronizar
 * =============================================================================
 * Versión     : 2.0
 * Fecha       : 24/01/2005 03:32:33 AM
 * Analista    : Programador8 - Arístides Castillo 
 * Descripción : Agregado soporte de Log4J
 * =============================================================================
 * Versión     : 1.2.4
 * Fecha       : 10/06/2004 03:32:33 AM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : - Eliminada referencia a los archivos *.cfg.xml implementada la
 * 				 configuración en el método syncEntidadMaestra donde se referencia 
 * 				 dinamicamente a los archivos *.hbm.xml por la entidad a sincronizar.
 * =============================================================================
 * Versión     : 1.2.3
 * Fecha       : 09/06/2004 11:32:33 AM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : - Agregados los métodos: syncNoAfiliado y cargarBDDireccional.
 * 				- Implementación preliminar del método syncEntidadMaestra con el API Hibernate.
 * 				- Modificada visualización de los mensajes en el método syncEntidad.
 * 				- Cambiado el método de sincronización de las entidades: Producto y Afiliado.
 * 				- Organizado el método cargarEntidadesBase.
 * =============================================================================
 * Versión     : 1.2.0
 * Fecha       : 26/05/2004 11:48:33 AM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : - Eliminado método getProducto.
 * 				- Optimizada actualización de los registros locales al ser sincronizados de modo
 * 				que fuesen actualizados atómicamente por cada registro.
 * 				- Agregados métodos correspondientes a la sincronización de los registros de abonos 
 * 				para ser sincronizados atómicamente por cada registro.
 * 				- Agregado método syncArchivoEntidad que permite la sincronización de datos guardadors
 * 				en un archivo de texto separado por <tab> invocando un comando de mysql desde el S.O.
 * 				- Agregado el método synEntidadMestra que implementa la sincronización a través del API
 * 				de Hibernator el cual transfiere los datos entre sesiones asociadas a un mismo esquema
 * 				de clases POJO.
 * 				- Modificados los métodos para sincronización de las entidades ampliando el despliegue 
 * 				de información durante cada operación del sincronizador.
 * 				- Agregados los métodos syncEntidadCR, synEntidadSrv agrupan todas las llamadas a los 
 * 				métodos de sincronización por cada una de las entidades en dirección a la CR y al Servidor 
 * 				respectivamente.
 * 				- Agregado el método cargarEntidadesBaseCR que sincroniza las entidades fundamentales para
 * 				el funcionamiento de la caja en sentido Servidor -> CR.
 * =============================================================================
 * Versión     : 1.1
 * Fecha       : 19/03/2004 03:48:33 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Implementación inicial.
 * =============================================================================
 */
package com.becoblohm.cr.mediadoresbd;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.JDBCException;
import net.sf.hibernate.Query;
import net.sf.hibernate.ReplicationMode;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ConexionServidorExcepcion;
import com.becoblohm.cr.extensiones.ActualizadorPrecios;
import com.becoblohm.cr.extensiones.ActualizadorPreciosFactory;
import com.becoblohm.cr.extensiones.EntitySynchronizer;
import com.becoblohm.cr.extensiones.EntitySynchronizerFactory;
import com.becoblohm.cr.manejadorauditoria.Auditoria;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.sincronizador.hibernate.Afiliado;
import com.becoblohm.cr.sincronizador.hibernate.base.EntidadMarcable;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.sincronizador.datafile.client.ObtenerDataFile;

public class BeansSincronizador {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(BeansSincronizador.class);

	static {
		logger.setAdditivity(false);
	}

	/**
	 * Método getTransacciones Selecciona todos los registros de las
	 * transacciones.
	 * 
	 * @param local
	 *            - Indicador de la conexión de BD a utilizar: Servidor o Local.
	 * @param noSincronizadas
	 *            - Indicador para seleccionar todas las transacciones o sólo
	 *            las no sincronizadas.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql
	 *         efectuada.
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getTransacciones(boolean local,
			boolean noSincronizadas) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		String sentenciaSql = "";
		boolean actualizable;
		if (noSincronizadas) {
			sentenciaSql = new String(
					"select transaccion.numtransaccion from transaccion where (transaccion.regactualizado = 'N') and (transaccion.estadotransaccion = '"
							+ Sesion.FINALIZADA + "')");
			actualizable = false;
		} else {
			sentenciaSql = new String(
					"select transaccion.numtransaccion from transaccion where  (transaccion.estadotransaccion = "
							+ Sesion.FINALIZADA + ")");
			actualizable = true;
		}

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local,
					actualizable, true);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("getTransacciones(boolean, boolean)", e);
			// throw (new BaseDeDatosExcepcion("Error al realizar sentencia " +
			// sentenciaSql, e));
		} catch (ConexionExcepcion e) {
		}
		return resultado;
	}

	/**
	 * Método getTransaccion Selecciona la información correspondiente a la
	 * transacción indicada.
	 * 
	 * @param local
	 *            - Indicador de la conexión de BD a utilizar: Servidor o Local.
	 * @param numTransaccion
	 *            - Transacción de la que se quiere recuperar el registro de
	 *            cabecera.
	 * @return ResultSet - Retorna el registro correspondiente a la transacción.
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getTransaccion(boolean local, int numTransaccion)
			throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		String sentenciaSql = new String(
				"select * from transaccion where (transaccion.numtransaccion = "
						+ numTransaccion
						+ ") and transaccion.regactualizado='N' and transaccion.estadotransaccion='F'");
		try {
			boolean actualizable = true;
			resultado = Conexiones.realizarConsulta(sentenciaSql, local,
					actualizable, true);
			resultado.first();
		} catch (BaseDeDatosExcepcion e) {
			logger.error("getTransaccion(boolean, int)", e);
			// throw (new BaseDeDatosExcepcion("Error al realizar sentencia " +
			// sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			logger.error("getTransaccion(boolean, int)", e);
		} catch (SQLException e) {
			logger.error("getTransaccion(boolean, int)", e);
		}
		return resultado;
	}

	/**
	 * Método getDetalleTransaccion Selecciona todos los detalles de productos
	 * de la transacción indicada.
	 * 
	 * @param local
	 *            - Indicador de la conexión de BD a utilizar: Servidor o Local.
	 * @param transaccion
	 *            - Número de la transacción a sincronizar.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql
	 *         efectuada.
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getDetalleTransaccion(boolean local, int transaccion)
			throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		String sentenciaSql = new String(
				"select detalletransaccion.* from transaccion inner join detalletransaccion on (transaccion.numtienda = detalletransaccion.numtienda) and (transaccion.numcajafinaliza = detalletransaccion.numcajafinaliza) and "
						+ "(transaccion.numtransaccion = detalletransaccion.numtransaccion) and (transaccion.fecha = detalletransaccion.fecha) "
						+ "where (transaccion.regactualizado = 'N' and transaccion.estadotransaccion='F') and (transaccion.numtransaccion = "
						+ transaccion + ")");

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, true,
					true);
			resultado.beforeFirst();
		} catch (BaseDeDatosExcepcion e) {
			// throw (new
			// BaseDeDatosExcepcion("Error de BD al realizar sentencia " +
			// sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion(
					"Error de Conexión al realizar sentencia " + sentenciaSql,
					e));
		} catch (SQLException e) {
			try {
				resultado.close();
			} catch (SQLException e1) {
				logger.error("getDetalleTransaccion(boolean, int)", e1);
			}
			throw (new BaseDeDatosExcepcion(
					"Error de SQL al realizar sentencia " + sentenciaSql, e));
		}
		return resultado;
	}

	/**
	 * Método getPagoTransaccion Selecciona todos los detalles de pagos de la
	 * transacción indicada.
	 * 
	 * @param local
	 *            - Indicador de la conexión de BD a utilizar: Servidor o Local.
	 * @param transaccion
	 *            - Número de la transacción a sincronizar.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql
	 *         efectuada.
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getPagoTransaccion(boolean local, int transaccion)
			throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		String sentenciaSql = new String(
				"select pagodetransaccion.* from transaccion inner join pagodetransaccion on (transaccion.numtienda = pagodetransaccion.numtienda) and (transaccion.numcajafinaliza = pagodetransaccion.numcaja) and "
						+ "(transaccion.numtransaccion = pagodetransaccion.numtransaccion) and (transaccion.fecha = pagodetransaccion.fecha) where (transaccion.regactualizado = 'N' and transaccion.estadotransaccion='F') and (transaccion.numtransaccion = "
						+ transaccion + ")");

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, true,
					true);
			resultado.beforeFirst();
		} catch (BaseDeDatosExcepcion e) {
			// throw (new
			// BaseDeDatosExcepcion("Error de BD al realizar sentencia " +
			// sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion(
					"Error de Conexión al realizar sentencia " + sentenciaSql,
					e));
		} catch (SQLException e) {
			try {
				resultado.close();
			} catch (SQLException e1) {
				logger.error("getPagoTransaccion(boolean, int)", e1);
			}
			throw (new BaseDeDatosExcepcion(
					"Error de SQL al realizar sentencia " + sentenciaSql, e));
		}
		return resultado;
	}

	/**
	 * Método syncTransacciones Sincroniza las transacciones (cabecera, detalle
	 * de productos y pagos) desde la Caja Registradora hacia el Servidor de
	 * tienda.
	 * 
	 * @param numTransacciones
	 *            - Lista de los numeros correspondientes a las transacciones de
	 *            la caja que no han sido actualizadas, sin considerar fecha del
	 *            sistema.
	 * @throws BaseDeDatosExcepcion
	 */
	/*
	 * En esta función se realizaron modificaciones referentes a la migración a
	 * java 1.6 por jperez Sólo se parametrizó el tipo de dato de los distintos
	 * ArrayList y hashMap
	 */
	public static synchronized void syncTransacciones(ResultSet numTransacciones)
			throws BaseDeDatosExcepcion {
		ResultSet detalleProductos, detallePagos, transacciones;
		ArrayList<ArrayList<Object>> registros;
		ArrayList<Object> registro;
		String sentenciaSql;
		String[] xLoteSentencias;
		Statement loteSentenciasSrv = null;
		Statement loteSentenciasCR = null;
		StringBuffer xCampos, xValores;
		// int tries = 0;
		int numTransaccion = 0;
		//Booleano agregado para no perder el aviso de 'Faltan transacciones por sincronizar' al inicio de caja
		//jperez 03-05-2012
		boolean syncAll = true;
		if (logger.isInfoEnabled()) {
			logger.info("Comienza sincronizacion de transacciones");
		}
		try {
			loteSentenciasSrv = Conexiones.crearSentencia(false, true);
			loteSentenciasCR = Conexiones.crearSentencia(true, true);
			HashMap<String, Object> criterioClave = new HashMap<String, Object>();

			while (numTransacciones.next()) {
				
				try{
					numTransaccion = numTransacciones
							.getInt("transaccion.numtransaccion");
	
					// Obtengo información de la CR de la transacción actual
					int numColumnas = 0;
					transacciones = getTransaccion(true, numTransaccion);
					try {
						xCampos = new StringBuffer("");
						xValores = new StringBuffer("");
						numColumnas = transacciones.getMetaData().getColumnCount();
						for (int i = 0; i < numColumnas; i++) {
							xCampos.append(transacciones.getMetaData()
									.getColumnName(i + 1));
							xValores.append("?");
							if (i + 1 < numColumnas) {
								xCampos.append(", ");
								xValores.append(", ");
							}
						}
						sentenciaSql = new String("insert into transaccion ("
								+ xCampos + ") values (" + xValores + ")");
	
						transacciones.first();
						criterioClave.put("numTienda",
								transacciones.getObject("numtienda"));
						criterioClave.put("numCajaFinaliza",
								transacciones.getObject("numcajafinaliza"));
						criterioClave
								.put("fecha", transacciones.getObject("fecha"));
						transacciones.beforeFirst();
	
						registros = new ArrayList<ArrayList<Object>>();
						while (transacciones.next()) {
							registro = new ArrayList<Object>();
							for (int i = 0; i < numColumnas; i++)
								registro.add(transacciones.getObject(transacciones
										.getMetaData().getColumnName(i + 1)
										.toLowerCase()));
							registros.add(registro);
						}
						xLoteSentencias = Conexiones.crearLoteSentencias(
								transacciones.getMetaData(), sentenciaSql,
								registros, false, true);
						for (int i = 0; i < xLoteSentencias.length; i++) {
							loteSentenciasSrv.addBatch(xLoteSentencias[i]);
							// System.out.println(xLoteSentencias[i]);
						}
					} finally {
						transacciones.close();
					}
					// Obtengo el detalle de renglones de la transacción
					registros = new ArrayList<ArrayList<Object>>();
					xCampos = new StringBuffer("");
					xValores = new StringBuffer("");
					detalleProductos = BeansSincronizador.getDetalleTransaccion(
							true, numTransaccion);
					try {
						numColumnas = detalleProductos.getMetaData()
								.getColumnCount();
						for (int i = 0; i < numColumnas; i++) {
							xCampos.append(detalleProductos.getMetaData()
									.getColumnName(i + 1));
							xValores.append("?");
							if (i + 1 < numColumnas) {
								xCampos.append(", ");
								xValores.append(", ");
							}
						}
						sentenciaSql = new String(
								"insert into detalletransaccion (" + xCampos
										+ ") values (" + xValores + ")");
	
						while (detalleProductos.next()) {
							registro = new ArrayList<Object>();
							for (int i = 0; i < numColumnas; i++)
								registro.add(detalleProductos
										.getObject(detalleProductos.getMetaData()
												.getColumnName(i + 1).toLowerCase()));
							registros.add(registro);
						}
						xLoteSentencias = Conexiones.crearLoteSentencias(
								detalleProductos.getMetaData(), sentenciaSql,
								registros, false, true);
						for (int i = 0; i < xLoteSentencias.length; i++) {
							loteSentenciasSrv.addBatch(xLoteSentencias[i]);
							// System.out.println(xLoteSentencias[i]);
						}
					} finally {
						detalleProductos.close();
					}
	
					// Obtengo el detalle de pagos de la transacción
					registros = new ArrayList<ArrayList<Object>>();
					xCampos = new StringBuffer("");
					xValores = new StringBuffer("");
					detallePagos = BeansSincronizador.getPagoTransaccion(true,
							numTransaccion);
					try {
						numColumnas = detallePagos.getMetaData().getColumnCount();
						for (int i = 0; i < numColumnas; i++) {
							xCampos.append(detallePagos.getMetaData()
									.getColumnName(i + 1));
							xValores.append("?");
							if (i + 1 < numColumnas) {
								xCampos.append(", ");
								xValores.append(", ");
							}
						}
						sentenciaSql = new String("insert into pagodetransaccion ("
								+ xCampos + ") values (" + xValores + ")");
	
						while (detallePagos.next()) {
							registro = new ArrayList<Object>();
							for (int i = 0; i < numColumnas; i++)
								registro.add(detallePagos.getObject(detallePagos
										.getMetaData().getColumnName(i + 1)
										.toLowerCase()));
							registros.add(registro);
						}
	
						xLoteSentencias = Conexiones.crearLoteSentencias(
								detallePagos.getMetaData(), sentenciaSql,
								registros, false, true);
						for (int i = 0; i < xLoteSentencias.length; i++) {
							loteSentenciasSrv.addBatch(xLoteSentencias[i]);
							// System.out.println(xLoteSentencias[i]);
						}
					} finally {
						detallePagos.close();
					}
					ActualizadorPrecios ap = (new ActualizadorPreciosFactory())
							.getActualizadorPreciosInstance();
					ap.syncTransaccionesExt(numTransaccion, loteSentenciasSrv,
							loteSentenciasCR, criterioClave);
	
					// Indico actualización de los registros de transacción en la CR
					sentenciaSql = new String(
							"update transaccion set regactualizado='" + Sesion.SI
									+ "' where (numtienda = "
									+ criterioClave.get("numTienda")
									+ ") and (numcajafinaliza = "
									+ criterioClave.get("numCajaFinaliza")
									+ ") and (numtransaccion = " + numTransaccion
									+ ") and (fecha = '"
									+ criterioClave.get("fecha") + "')");
					loteSentenciasCR.addBatch(sentenciaSql);
					sentenciaSql = new String(
							"update detalletransaccion set regactualizado='"
									+ Sesion.SI + "' where (numtienda = "
									+ criterioClave.get("numTienda")
									+ ") and (numcajafinaliza = "
									+ criterioClave.get("numCajaFinaliza")
									+ ") and (numtransaccion = " + numTransaccion
									+ ") and (fecha = '"
									+ criterioClave.get("fecha") + "')");
					loteSentenciasCR.addBatch(sentenciaSql);
					sentenciaSql = new String(
							"update pagodetransaccion set regactualizado='"
									+ Sesion.SI + "' where (numtienda = "
									+ criterioClave.get("numTienda")
									+ ") and (numcaja = "
									+ criterioClave.get("numCajaFinaliza")
									+ ") and (numtransaccion = " + numTransaccion
									+ ") and (fecha = '"
									+ criterioClave.get("fecha") + "')");
					loteSentenciasCR.addBatch(sentenciaSql);
	
					// Ejecuto lote de sentencias en el DBMS del Servidor y de la CR
					Connection connSrv = Conexiones.getConexion(false, true);
					Connection connCR = Conexiones.getConexion(true, true);
					try {
						connCR.setAutoCommit(false);
						connSrv.setAutoCommit(false);
						connSrv.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
						connCR.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
						/* int[] filas = */Conexiones.ejecutarLoteSentencias(
								loteSentenciasSrv, false, false, true);
						/* filas = */Conexiones.ejecutarLoteSentencias(
								loteSentenciasCR, true, false, true);
						connSrv.commit();
						connCR.commit();
						if (logger.isInfoEnabled()) {
							logger.info("Sincronizacion exitosa. Ult. transaccion "
									+ numTransaccion);
						}
	
					} catch (Exception e) {
						connCR.rollback();
						connSrv.rollback();
						logger.error(
								"syncTransacciones(ResultSet): Rollback sinc. transacciones. T# "
										+ Sesion.getTienda().getNumero()
										+ " - Caja " + Sesion.getCaja().getNumero(),
								e);
						throw (new BaseDeDatosExcepcion(
								"Error en sentencia por lote al sincronizar transacciones"));
					} finally {
						connCR.setAutoCommit(true);
						connSrv.setAutoCommit(true);
					}
					loteSentenciasSrv.clearBatch();
					loteSentenciasCR.clearBatch();
					//Catch agregado para sincronizacion no secuencial - jperez - 03-05-2012
				}catch(BaseDeDatosExcepcion e){
					loteSentenciasSrv.clearBatch();
					loteSentenciasCR.clearBatch();
					syncAll = false;
				}
			}
			if (!syncAll){
				throw (new BaseDeDatosExcepcion(
						"Error en sentencia por lote al sincronizar transacciones"));
			}
		} catch (BatchUpdateException e) {
			logger.error("syncTransacciones(ResultSet)", e);
			throw (new BaseDeDatosExcepcion(
					"Error en sentencia por lote al sincronizar transacciones"));
		} catch (SQLException e) {
			logger.error("syncTransacciones(ResultSet)", e);
			throw (new BaseDeDatosExcepcion(
					"Error en sentencia SQL al sincronizar transacciones"));
		/*} catch (BaseDeDatosExcepcion e) {
			logger.error("syncTransacciones(ResultSet)", e);
			throw (new BaseDeDatosExcepcion(
					"Error con la BD al sincronizar transacciones"));*/
		} catch (ConexionExcepcion e) {
			logger.error("syncTransacciones(ResultSet)", e);
			throw (new BaseDeDatosExcepcion(
					"Error de conexión con BD al sincronizar transacciones"));
		} catch (Throwable t) {
			logger.error("syncTransacciones(ResultSet)", t);
		} finally {
			try {
				if (loteSentenciasCR != null)
					loteSentenciasCR.close();
			} catch (SQLException e1) {
				logger.error("syncTransacciones(ResultSet)", e1);
			}

			try {
				if (loteSentenciasSrv != null)
					loteSentenciasSrv.close();
			} catch (SQLException e1) {
				logger.error("syncTransacciones(ResultSet)", e1);
			}
		}
	}

	/**
	 * Método getAbonos Selecciona todos los registros de las transacciones de
	 * abonos.
	 * 
	 * @param local
	 *            - Indicador de la conexión de BD a utilizar: Servidor o Local.
	 * @param noSincronizadas
	 *            - Indicador para seleccionar todas las transacciones de abono
	 *            o sólo las no sincronizadas.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql
	 *         efectuada.
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getAbonos(boolean local, boolean noSincronizadas)
			throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		String sentenciaSql = "";
		boolean actualizable;
		if (noSincronizadas) {
			sentenciaSql = new String(
					"select transaccionabono.numabono, transaccionabono.numservicio from transaccionabono where (transaccionabono.regactualizado = 'N') ");
			actualizable = false;
		} else {
			sentenciaSql = new String(
					"select transaccionabono.numabono, transaccionabono.numservicio from transaccionabono ");
			actualizable = true;
		}

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local,
					actualizable, true);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("getAbonos(boolean, boolean)", e);
			// throw (new BaseDeDatosExcepcion("Error al realizar sentencia " +
			// sentenciaSql, e));
		} catch (ConexionExcepcion e) {
		}
		return resultado;
	}

	/**
	 * Método getAbono Selecciona la información correspondiente a la
	 * transacción de abono indicada.
	 * 
	 * @param local
	 *            - Indicador de la conexión de BD a utilizar: Servidor o Local.
	 * @param numAbono
	 *            - Transacción de abono de la que se quiere recuperar el
	 *            registro de cabecera.
	 * @param numServicio
	 *            - Número del servicio asociado a la transacción de abono de la
	 *            que se quiere recuperar el registro de cabecera.
	 * @return ResultSet - Retorna el registro correspondiente a la transacción.
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getAbono(boolean local, int numAbono,
			int numServicio) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		String sentenciaSql = new String(
				"select * from transaccionabono where (transaccionabono.numabono = "
						+ numAbono + ") and (transaccionabono.numservicio = "
						+ numServicio + ")");
		try {
			boolean actualizable = true;
			resultado = Conexiones.realizarConsulta(sentenciaSql, local,
					actualizable, true);
			resultado.first();
		} catch (BaseDeDatosExcepcion e) {
			logger.error("getAbono(boolean, int, int)", e);
			// throw (new BaseDeDatosExcepcion("Error al realizar sentencia " +
			// sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			logger.error("getAbono(boolean, int, int)", e);
		} catch (SQLException e) {
			logger.error("getAbono(boolean, int, int)", e);
		}
		return resultado;
	}

	/**
	 * Método getPagoAbono Selecciona todos los detalles (productos/pagos) de la
	 * transacción indicada.
	 * 
	 * @param local
	 *            - Indicador de la conexión de BD a utilizar: Servidor o Local.
	 * @param numAbono
	 *            - Número de la transacción de abono a sincronizar.
	 * @param numServicio
	 *            - Número del servicio correspondiente a la transacción de
	 *            abono a sincronizar.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql
	 *         efectuada.
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getPagoAbono(boolean local, int numAbono,
			int numServicio) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		String sentenciaSql = new String(
				"select * from transaccionabono inner join pagodeabonos on (transaccionabono.numtienda = pagodeabonos.numtienda) and (transaccionabono.numcaja = pagodeabonos.numcaja) and "
						+ "(transaccionabono.numabono = pagodeabonos.numabono) and (transaccionabono.fecha = pagodeabonos.fecha) and (transaccionabono.numservicio = pagodeabonos.numservicio) where (transaccionabono.regactualizado = 'N') and (transaccionabono.numabono = "
						+ numAbono
						+ ") and (transaccionabono.numservicio = "
						+ numServicio + ")");

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, true,
					true);
			resultado.beforeFirst();
		} catch (BaseDeDatosExcepcion e) {
			// throw (new
			// BaseDeDatosExcepcion("Error de BD al realizar sentencia " +
			// sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion(
					"Error de Conexión al realizar sentencia " + sentenciaSql,
					e));
		} catch (SQLException e) {
			try {
				resultado.close();
			} catch (SQLException e1) {
				logger.error("getPagoAbono(boolean, int, int)", e1);
			}
			throw (new BaseDeDatosExcepcion(
					"Error de SQL al realizar sentencia " + sentenciaSql, e));
		}
		return resultado;
	}

	/**
	 * Método syncAbonos Sincroniza las transacciones de abonos (cabecera y
	 * detalles de pagos) desde la Caja Registradora hacia el Servidor de
	 * tienda.
	 * 
	 * @param numAbonos
	 *            - Lista de los numeros correspondientes a las transacciones de
	 *            la caja que no han sido actualizadas, sin considerar fecha del
	 *            sistema.
	 * @throws BaseDeDatosExcepcion
	 */
	/*
	 * En esta función se realizaron modificaciones referentes a la migración a
	 * java 1.6 por jperez Sólo se parametrizó el tipo de dato de los distintos
	 * ArrayList Fecha: agosto 2011
	 */
	public static synchronized void syncAbonos(ResultSet numAbonos)
			throws BaseDeDatosExcepcion {
		ResultSet detallePagos, abonos;
		ArrayList<ArrayList<Object>> registros;
		ArrayList<Object> registro;
		String sentenciaSql;
		String[] xLoteSentencias;
		Statement loteSentenciasSrv = null;
		Statement loteSentenciasCR = null;

		try {
			int numAbono = 0;
			int numServicio = 0;
			loteSentenciasSrv = Conexiones.crearSentencia(false, true);
			loteSentenciasCR = Conexiones.crearSentencia(true, true);

			while (numAbonos.next()) {
				numAbono = numAbonos.getInt("transaccionabono.numabono");
				numServicio = numAbonos.getInt("transaccionabono.numservicio");

				// Obtengo información de la CR de la transacción actual
				abonos = getAbono(true, numAbono, numServicio);
				Short numTienda = null;
				Short numCaja = null;
				Date fecha = null;
				try {
					numTienda = new Short(
							abonos.getShort("transaccionabono.numtienda"));
					numCaja = new Short(
							abonos.getShort("transaccionabono.numcaja"));
					fecha = abonos.getDate("transaccionabono.fecha");
					String tipoTransaccionAbono = abonos
							.getString("transaccionabono.tipotransaccionabono");
					String codigoTipoServicio = abonos
							.getString("transaccionabono.codigotiposervicio");
					Date fechaServicio = abonos
							.getDate("transaccionabono.fechaservicio");
					String codCajero = abonos
							.getString("transaccionabono.codcajero");
					Time horaInicia = abonos
							.getTime("transaccionabono.horainicia");
					Time horaFinaliza = abonos
							.getTime("transaccionabono.horafinaliza");
					Double monto = new Double(
							abonos.getDouble("transaccionabono.monto"));
					Double vueltoCliente = new Double(
							abonos.getDouble("transaccionabono.vueltoCliente"));
					Double montoRemanente = new Double(
							abonos.getDouble("transaccionabono.montoRemanente"));
					String cajaEnLinea = new String(
							abonos.getString("transaccionabono.cajaenlinea"));
					String serialCaja = new String(
							abonos.getString("transaccionabono.serialcaja"));
					String estadoAbono = abonos
							.getString("transaccionabono.estadoabono");
					String regActualizado = abonos
							.getString("transaccionabono.regactualizado");
					sentenciaSql = new String("insert into transaccionabono "
							+ "values ("
							+ numTienda
							+ ", "
							+ numCaja
							+ ", '"
							+ fecha
							+ "', "
							+ numAbono
							+ ", "
							+ numServicio
							+ "'"
							+ tipoTransaccionAbono
							+ "', "
							+ "'"
							+ codigoTipoServicio
							+ "', '"
							+ fechaServicio
							+ "', "
							+ "', "
							+ codCajero
							+ ", '"
							+ horaInicia
							+ "', '"
							+ horaFinaliza
							+ "', "
							+ monto
							+ ", "
							+ vueltoCliente
							+ ", "
							+ montoRemanente
							+ ", '"
							+ cajaEnLinea
							+ "', '"
							+ serialCaja
							+ "', '"
							+ estadoAbono + "', '" + regActualizado + "')");

					loteSentenciasSrv.addBatch(sentenciaSql);
				} finally {
					abonos.close();
				}

				// System.out.println(sentenciaSql);

				// Obtengo el detalle de pagos del abono
				registros = new ArrayList<ArrayList<Object>>();
				detallePagos = BeansSincronizador.getPagoAbono(true, numAbono,
						numServicio);
				try {
					while (detallePagos.next()) {
						registro = new ArrayList<Object>();
						registro.add(new Short(detallePagos
								.getShort("pagodeabonos.numtienda")));
						registro.add(new Short(detallePagos
								.getShort("pagodeabonos.numcaja")));
						registro.add(new Integer(detallePagos
								.getInt("pagodeabonos.numabono")));
						registro.add(detallePagos.getDate("pagodeabonos.fecha"));
						registro.add(new Integer(detallePagos
								.getInt("pagodeabonos.numservicio")));
						registro.add(detallePagos
								.getString("pagodeabonos.codformadepago"));
						registro.add(new Short(detallePagos
								.getShort("pagodeabonos.correlativoitem")));
						registro.add(detallePagos
								.getString("pagodeabonos.codbanco"));
						registro.add(new Double(detallePagos
								.getDouble("pagodeabonos.monto")));
						registro.add(detallePagos
								.getString("pagodeabonos.numdocumento"));
						registro.add(detallePagos
								.getString("pagodeabonos.numcuenta"));
						registro.add(detallePagos
								.getString("pagodeabonos.numconformacion"));
						registro.add(new Integer(detallePagos
								.getInt("pagodeabonos.numreferencia")));
						registro.add(detallePagos
								.getString("pagodeabonos.cedtitular"));
						registro.add(detallePagos
								.getString("pagodeabonos.regactualizado"));
						registros.add(registro);
					}

					sentenciaSql = new String(
							"insert into pagodeabonos (numtienda,numcaja,numabono,fecha,numservicio,codformadepago,correlativoitem,codbanco,monto,numdocumento,numcuenta,numconformacion,numreferencia,cedtitular,regactualizado) "
									+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					xLoteSentencias = Conexiones.crearLoteSentencias(
							detallePagos.getMetaData(), sentenciaSql,
							registros, false, true);
					for (int i = 0; i < xLoteSentencias.length; i++) {
						loteSentenciasSrv.addBatch(xLoteSentencias[i]);
						// System.out.println(xLoteSentencias[i]);
					}

				} finally {
					detallePagos.close();
				}
				// Indico actualización de los registros de transacción en la CR
				sentenciaSql = new String(
						"update transaccionabono set regactualizado='"
								+ Sesion.SI + "' where (numtienda = "
								+ numTienda + ") and (numcaja = " + numCaja
								+ ") and (numabono = " + numAbono
								+ ") and (numservicio = " + numServicio
								+ ") and (fecha = '" + fecha + "')");
				// System.out.println(sentenciaSql);
				loteSentenciasCR.addBatch(sentenciaSql);
				sentenciaSql = new String(
						"update pagodeabonos set regactualizado='" + Sesion.SI
								+ "' where (numtienda = " + numTienda
								+ ") and (numcaja = " + numCaja
								+ ") and (numabono = " + numAbono
								+ ") and (numservicio = " + numServicio
								+ ") and (fecha = '" + fecha + "')");
				// System.out.println(sentenciaSql);
				loteSentenciasCR.addBatch(sentenciaSql);

				// Ejecuto lote de sentencias en el DBMS del Servidor y de la CR
				Connection connSrv = Conexiones.getConexion(false, true);
				Connection connCR = Conexiones.getConexion(true, true);
				try {
					connCR.setAutoCommit(false);
					connSrv.setAutoCommit(false);
					connSrv.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
					connCR.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
					/* int[] filas = */Conexiones.ejecutarLoteSentencias(
							loteSentenciasSrv, false, false, true);
					/* filas = */Conexiones.ejecutarLoteSentencias(
							loteSentenciasCR, true, false, true);
					connCR.commit();
					connSrv.commit();
				} catch (Exception e) {
					connCR.rollback();
					connSrv.rollback();
					logger.error(
							"syncAbonos(ResultSet). Error al sinc. abonos. T# "
									+ Sesion.getTienda().getNumero()
									+ " - Caja " + Sesion.getCaja().getNumero(),
							e);
					throw (new BaseDeDatosExcepcion(
							"Error en sentencia por lote al sincronizar transacciones"));
				} finally {
					connCR.setAutoCommit(true);
					connSrv.setAutoCommit(true);
				}
				loteSentenciasSrv.clearBatch();
				loteSentenciasCR.clearBatch();
			}
		} catch (BatchUpdateException e) {
			logger.error("syncAbonos(ResultSet)", e);
			throw (new BaseDeDatosExcepcion(
					"Error en sentencia por lote al sincronizar transacciones de abono"));
		} catch (SQLException e) {
			logger.error("syncAbonos(ResultSet)", e);
			throw (new BaseDeDatosExcepcion(
					"Error en sentencia SQL al sincronizar transacciones de abono"));
		} catch (BaseDeDatosExcepcion e) {
			logger.error("syncAbonos(ResultSet)", e);
			throw (new BaseDeDatosExcepcion(
					"Error con la BD al sincronizar transacciones de abono"));
		} catch (ConexionExcepcion e) {
			logger.error("syncAbonos(ResultSet)", e);
			throw (new BaseDeDatosExcepcion(
					"Error de conexión con BD al sincronizar transacciones de abono"));
		} catch (Throwable t) {
			logger.error("syncAbonos(ResultSet)", t);
		} finally {
			try {
				if (loteSentenciasCR != null)
					loteSentenciasCR.close();
			} catch (SQLException e1) {
				logger.error("syncAbonos(ResultSet)", e1);
			}

			try {
				if (loteSentenciasSrv != null)
					loteSentenciasSrv.close();
			} catch (SQLException e1) {
				logger.error("syncAbonos(ResultSet)", e1);
			}
		}
		// logger.info(event.finalizaTarea());
	}

	/**
	 * Método getPromociones Selecciona todos los registros de las promociones.
	 * 
	 * @param local
	 *            - Indicador de la conexión de BD a utilizar: Servidor o Local.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql
	 *         efectuada.
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getPromociones(boolean local)
			throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		String sentenciaSql = "";
		boolean actualizable;
		sentenciaSql = new String(
				"select codpromocion, tipopromocion from promocion");
		actualizable = false;

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local,
					actualizable, true);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("getPromociones(boolean)", e);
		} catch (ConexionExcepcion e) {
			logger.error("getPromociones(boolean)", e);
		}
		return resultado;
	}

	/**
	 * Método getPromocion Selecciona la información correspondiente a la
	 * promoción indicada.
	 * 
	 * @param local
	 *            - Indicador de la conexión de BD a utilizar: Servidor o Local.
	 * @param codPromocion
	 *            - Promoción de la que se quiere recuperar el registro de
	 *            cabecera.
	 * @param tipoPromocion
	 *            - Tipo de promoción de la que se quiere recuperar el registro
	 *            de cabecera.
	 * @return ResultSet - Retorna el registro correspondiente a la transacción.
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getPromocion(boolean local, int codPromocion,
			char tipoPromocion) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		String sentenciaSql = new String(
				"select * from promocion where (promocion.codpromocion = "
						+ codPromocion + ") and (promocion.tipopromocion = '"
						+ tipoPromocion + "')");
		try {
			boolean actualizable = true;
			resultado = Conexiones.realizarConsulta(sentenciaSql, local,
					actualizable, true);
			resultado.first();
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion(
					"Error de BD al realizar sentencia " + sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion(
					"Error de BD al realizar sentencia " + sentenciaSql, e));
		} catch (SQLException e) {
			try {
				resultado.close();
			} catch (SQLException e1) {
				logger.error("getPromocion(boolean, int, char)", e1);
			}
			throw (new BaseDeDatosExcepcion(
					"Error de BD al realizar sentencia " + sentenciaSql, e));
		}
		return resultado;
	}

	/**
	 * Método getDetallePromocion Selecciona todos los detalles de productos
	 * asignados a la promoción indicada.
	 * 
	 * @param local
	 *            - Indicador de la conexión de BD a utilizar: Servidor o Local.
	 * @param promocion
	 *            - Código de la promoción a sincronizar.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql
	 *         efectuada.
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getDetallePromocion(boolean local, int promocion)
			throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		String sentenciaSql = new String(
				"select detallepromocion.* from promocion inner join detallepromocion on (promocion.codpromocion = detallepromocion.codpromocion)"
						+ " where (detallepromocion.codpromocion = "
						+ promocion + ")");

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, true,
					true);
			resultado.beforeFirst();
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion(
					"Error de BD al realizar sentencia " + sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion(
					"Error de Conexión al realizar sentencia " + sentenciaSql,
					e));
		} catch (SQLException e) {
			try {
				resultado.close();
			} catch (SQLException e1) {
				logger.error("getDetallePromocion(boolean, int)", e1);
			}
			throw (new BaseDeDatosExcepcion(
					"Error de SQL al realizar sentencia " + sentenciaSql, e));
		}
		return resultado;
	}

	/**
	 * Método syncPromociones Sincroniza las promociones (cabecera y detalle de
	 * productos) hacia la Caja Registradora.
	 * 
	 * @param codPromociones
	 *            - Lista de los códigos correspondientes a las promociones del
	 *            servidor, sin considerar fecha del sistema.
	 * @throws BaseDeDatosExcepcion
	 */
	/*
	 * En esta función se realizaron modificaciones referentes a la migración a
	 * java 1.6 por jperez Sólo se parametrizó el tipo de dato de los distintos
	 * ArrayList y HashMap Fecha: agosto 2011
	 */
	public static void syncPromociones(ResultSet codPromociones)
			throws BaseDeDatosExcepcion {
		ResultSet detalleProductos, promociones;
		ArrayList<ArrayList<Object>> registros;
		ArrayList<Object> registro;
		String sentenciaSql;
		String[] xLoteSentencias;
		Statement loteSentenciasCR = null;
		StringBuffer xCampos, xValores;

		try {
			int codPromocion = 0;
			char tipoPromocion = ' ';
			loteSentenciasCR = Conexiones.crearSentencia(true, true);
			HashMap<String, Object> criterioClave = new HashMap<String, Object>();

			while (codPromociones.next()) {
				codPromocion = codPromociones.getInt("codpromocion");
				tipoPromocion = codPromociones.getString("tipopromocion")
						.charAt(0);

				// Obtengo información de la CR de la transacción actual
				promociones = getPromocion(false, codPromocion, tipoPromocion);
				int numColumnas = 0;
				try {
					xCampos = new StringBuffer("");
					xValores = new StringBuffer("");
					numColumnas = promociones.getMetaData().getColumnCount();
					for (int i = 0; i < numColumnas; i++) {
						xCampos.append(promociones.getMetaData().getColumnName(
								i + 1));
						xValores.append("?");
						if (i + 1 < numColumnas) {
							xCampos.append(", ");
							xValores.append(", ");
						}
					}
					sentenciaSql = new String("insert into promocion ("
							+ xCampos + ") values (" + xValores + ")");

					promociones.first();
					criterioClave.put("codPromocion",
							promociones.getObject("codpromocion"));
					criterioClave.put("tipoPromocion",
							promociones.getObject("tipopromocion"));
					promociones.beforeFirst();

					registros = new ArrayList<ArrayList<Object>>();
					while (promociones.next()) {
						registro = new ArrayList<Object>();
						for (int i = 0; i < numColumnas; i++)
							registro.add(promociones.getObject(promociones
									.getMetaData().getColumnName(i + 1)
									.toLowerCase()));
						registros.add(registro);
					}
					xLoteSentencias = Conexiones.crearLoteSentencias(
							promociones.getMetaData(), sentenciaSql, registros,
							true, true);
					for (int i = 0; i < xLoteSentencias.length; i++) {
						loteSentenciasCR.addBatch(xLoteSentencias[i]);
						// System.out.println(xLoteSentencias[i]);
					}
				} finally {
					promociones.close();
				}

				// Obtengo el detalle de renglones de la promoción
				registros = new ArrayList<ArrayList<Object>>();
				xCampos = new StringBuffer("");
				xValores = new StringBuffer("");
				detalleProductos = BeansSincronizador.getDetallePromocion(
						false, codPromocion);
				try {
					numColumnas = detalleProductos.getMetaData()
							.getColumnCount();
					for (int i = 0; i < numColumnas; i++) {
						xCampos.append(detalleProductos.getMetaData()
								.getColumnName(i + 1));
						xValores.append("?");
						if (i + 1 < numColumnas) {
							xCampos.append(", ");
							xValores.append(", ");
						}
					}
					sentenciaSql = new String("insert into detallepromocion ("
							+ xCampos + ") values (" + xValores + ")");

					while (detalleProductos.next()) {
						registro = new ArrayList<Object>();
						for (int i = 0; i < numColumnas; i++)
							registro.add(detalleProductos
									.getObject(detalleProductos.getMetaData()
											.getColumnName(i + 1).toLowerCase()));
						registros.add(registro);
					}
					xLoteSentencias = Conexiones.crearLoteSentencias(
							detalleProductos.getMetaData(), sentenciaSql,
							registros, true, true);
					for (int i = 0; i < xLoteSentencias.length; i++) {
						loteSentenciasCR.addBatch(xLoteSentencias[i]);
					}
				} finally {
					detalleProductos.close();
				}

				// Ejecuto lote de sentencias en el DBMS del Servidor y de la CR
				Conexiones.ejecutarLoteSentencias(loteSentenciasCR, true, true,
						true);
				loteSentenciasCR.clearBatch();
			}
		} catch (BatchUpdateException e) {
			logger.error("syncPromociones(ResultSet)", e);
			throw (new BaseDeDatosExcepcion(
					"Error en sentencia por lote al sincronizar transacciones"));
		} catch (SQLException e) {
			logger.error("syncPromociones(ResultSet)", e);
			throw (new BaseDeDatosExcepcion(
					"Error en sentencia SQL al sincronizar transacciones"));
		} catch (BaseDeDatosExcepcion e) {
			logger.error("syncPromociones(ResultSet)", e);
			throw (new BaseDeDatosExcepcion(
					"Error con la BD al sincronizar transacciones"));
		} catch (ConexionExcepcion e) {
			logger.error("syncPromociones(ResultSet)", e);
			throw (new BaseDeDatosExcepcion(
					"Error de conexión con BD al sincronizar transacciones"));
		} finally {
			try {
				if (loteSentenciasCR != null)
					loteSentenciasCR.close();
			} catch (SQLException e1) {
				logger.error("syncPromociones(ResultSet)", e1);
			}
		}
	}

	/**
	 * Método syncEntidadMaestra Sincroniza la entidad indicada utilizando la
	 * configuración de Hibernate en los archivos *.cfg.xml y la estructura de
	 * la BD mapeada en los archivos *.hbm.xml transafiriendo la data de una
	 * sesión a otra.
	 * 
	 * @param entidad
	 * @param BDOrigenLocal
	 */
	/*
	 * En esta función se realizaron modificaciones referentes a la migración a
	 * java 1.6 por jperez Sólo se comentaron variables sin uso Fecha: agosto
	 * 2011
	 */
	@SuppressWarnings("unchecked")
	public synchronized static void syncEntidadMaestra(EntidadBD entidad,
			boolean BDOrigenLocal, boolean reemplazar) {
		Session sessionCR, sessionSRV;
		Transaction tx = null, txO = null;
		ResultSet resultado;
		int vanCuantos = 0, numMaxRegistros = 1000/*
												 * , grabados = 0, loteRegistros
												 * = 2000
												 */;
		String sentenciaSql, sentenciaSqlAct = null, mensaje;
		Calendar fecha = Calendar.getInstance();
		Timestamp recienteSrv = new Timestamp(fecha.getTime().getTime());
		// LogEvent event = new LogEvent();
		String destino = "C";
		if (BDOrigenLocal)
			destino = "S";
		Connection conLocal = null, conSrv = null;
		String sentenciaHql = new String("from "
				+ entidad.getNombre().toUpperCase().charAt(0)
				+ entidad.getNombre().substring(1));
		String sentenciaSQL = new String("select {" + entidad.getNombre()
				+ ".*} from " + entidad.getEsquema() + "."
				+ entidad.getNombre() + " " + entidad.getNombre());
		@SuppressWarnings("unused")
		String xSentenciaSql = new String("select "
				+ sentenciaHql.toLowerCase().replaceFirst(
						"from ",
						"count(*) as cuantosregistros from "
								+ entidad.getEsquema() + "."));
		if (Sesion.isCajaEnLinea()) {
			try {
				conLocal = Conexiones.getConexion(true, true);
				conLocal.setAutoCommit(false);
				conSrv = Conexiones.getConexion(false, true);
				conSrv.setAutoCommit(false);
				if (CR.me != null)
					CR.me.setSyncronizacion();
				long inicio = System.currentTimeMillis();
				if (logger.isInfoEnabled()) {
					logger.info("syncEntidadMaestra(EntidadBD, boolean, boolean) - Verificando entidad -> "
							+ entidad.getEsquema() + "." + entidad.getNombre());
				}
				boolean conActualizacion = entidad.isActualizacion();
				String entidadBD = new String(
						"com.becoblohm.cr.sincronizador.hibernate."
								+ entidad.getNombre().toUpperCase().charAt(0)
								+ entidad.getNombre().substring(1));

				sessionCR = ConexionHibernate.currentSession(true, conLocal);
				sessionSRV = ConexionHibernate.currentSession(false, conSrv);

				try {

					if (sessionSRV.isOpen()) {

						// Obtenemos fecha de última sincronización en el
						// Planificador de la Caja
						Connection conexion = sessionCR.connection();
						Statement sentencia = conexion.createStatement();
						try {
							if (conActualizacion) {

								sentenciaSql = new String(
										"select actualizacion as ultimasync from "
												+ entidad.getEsquema()
												+ ".planificador where entidad='"
												+ entidad.getNombre()
														.toLowerCase()
												+ "' and destino = '" + destino
												+ "'");
								resultado = sentencia
										.executeQuery(sentenciaSql);
								try {
									if (resultado.first()
											&& (resultado
													.getTimestamp("ultimasync") != null)) {
										Timestamp ultimaFechaSync = resultado
												.getTimestamp("ultimasync");
										SimpleDateFormat formato = new SimpleDateFormat(
												"yyyyMMddHHmmss");
										sentenciaHql = new String(
												"from "
														+ entidad.getNombre()
																.toUpperCase()
																.charAt(0)
														+ entidad.getNombre()
																.substring(1)
														+ " where actualizacion > '"
														+ formato
																.format(ultimaFechaSync)
														+ "'");
										sentenciaSQL = new String(
												"select {"
														+ entidad.getNombre()
														+ ".*} from "
														+ entidad.getEsquema()
														+ "."
														+ entidad.getNombre()
														+ " "
														+ entidad.getNombre()
														+ " where actualizacion > '"
														+ formato
																.format(ultimaFechaSync)
														+ "' order by actualizacion desc");
										xSentenciaSql = new String(
												"select "
														+ sentenciaHql
																.toLowerCase()
																.replaceFirst(
																		"from ",
																		"count(*) as cuantosregistros from "
																				+ entidad
																						.getEsquema()
																				+ "."));
									}
								} finally {
									resultado.close();
								}
							}
						} finally {
							sentencia.close();
						}

						// Obtenemos fecha más reciente de la tabla en el
						// servidor
						conexion = sessionSRV.connection();
						try {
							sentencia = conexion.createStatement();
						} catch (SQLException e) {
							throw new ConexionServidorExcepcion(
									"No pudo establecerse conexion con el servidor",
									e);
						}
						resultado = null;
						try {
							if (conActualizacion) {
								sentenciaSqlAct = new String(
										"select max(actualizacion) as masreciente from "
												+ entidad.getEsquema() + "."
												+ entidad.getNombre());
								resultado = sentencia
										.executeQuery(sentenciaSqlAct);
								try {
									Calendar cal = Calendar.getInstance();
									if (resultado.first()
											&& resultado
													.getTimestamp("masreciente") != null) {
										recienteSrv = new Timestamp(resultado
												.getTimestamp("masreciente")
												.getTime());
									} else {
										cal.set(1980, 0, 1, 0, 0, 0);
										recienteSrv = new Timestamp(cal
												.getTime().getTime());
									}
								} finally {
									resultado.close();
								}
							}
						} finally {
							sentencia.close();
						}

						if (entidad.isMarca()) {
							sentenciaHql = new String("from "
									+ entidad.getNombre().toUpperCase()
											.charAt(0)
									+ entidad.getNombre().substring(1));
							sentenciaSQL = "select {" + entidad.getNombre()
									+ ".*} from " + entidad.getEsquema() + "."
									+ entidad.getNombre() + " "
									+ entidad.getNombre()
									+ " where regactualizado = 'N'";
							xSentenciaSql = "select "
									+ sentenciaHql.toLowerCase().replaceFirst(
											"from ",
											"count(*) as cuantosregistros from "
													+ entidad.getEsquema()
													+ ".")
									+ " where regactualizado = 'N'";
						}

						sentencia = null;
						resultado = null;

						if (logger.isInfoEnabled()) {
							logger.info("syncEntidadMaestra(EntidadBD, boolean, boolean) - Sentencia SQL: "
									+ sentenciaSQL);
						}

						Query consulta = null;
						if (BDOrigenLocal) {
							tx = sessionSRV.beginTransaction();
							if (entidad.isMarca()) {
								txO = sessionCR.beginTransaction();
							}
							consulta = sessionCR.createSQLQuery(sentenciaSQL,
									entidad.getNombre(),
									Class.forName(entidadBD));
						} else {
							tx = sessionCR.beginTransaction();
							if (entidad.isMarca()) {
								txO = sessionSRV.beginTransaction();
							}
							consulta = sessionSRV.createSQLQuery(sentenciaSQL,
									entidad.getNombre(),
									Class.forName(entidadBD));
						}
						consulta.setMaxResults(numMaxRegistros);

						boolean vacio = false;
						consulta.setFirstResult(vanCuantos);
						vacio = consulta.list().isEmpty();
						while (!vacio && !Sesion.finalizarSync) {
							List<Object> registros = consulta.list();
							vanCuantos = vanCuantos + numMaxRegistros;
							for (int i = 0; i < registros.size(); i++) {
								Object registro = registros.get(i);
								try {
									if (BDOrigenLocal) {
										sessionSRV.replicate(registro,
												ReplicationMode.LATEST_VERSION);
										/*
										 * if (entidad.isMarca()) {
										 * 
										 * ((EntidadMarcable)registro).
										 * setRegactualizado("S");
										 * sessionCR.update(registro); }
										 */
									} else {
										sessionCR.replicate(registro,
												ReplicationMode.LATEST_VERSION);
										/*
										 * if (entidad.isMarca()) {
										 * ((EntidadMarcable
										 * )registro).setRegactualizado("S");
										 * sessionSRV.update(registro); }
										 */
									}

								} catch (HibernateException e) {
									Auditoria.registrarAuditoria(
											"Error al sincronizar entidad: "
													+ registro.toString(), 'E');
									if (logger.isDebugEnabled()) {
										logger.debug("syncEntidadMaestra(EntidadBD, boolean, boolean) - Error al sincronizar entidad: "
												+ registro.toString());
									}
								}

							}
							tx.commit();
							// nuevo ciclo para la actualizacion de
							// regactualizado de las tablas que
							// tengan como parametro marca true. crm wdiaz
							for (int i = 0; i < registros.size(); i++) {
								Object registro = registros.get(i);
								try {
									if ((BDOrigenLocal) && (entidad.isMarca())) {
										((EntidadMarcable) registro)
												.setRegactualizado("S");
										sessionCR.update(registro);

									} else {
										if (entidad.isMarca()) {
											((EntidadMarcable) registro)
													.setRegactualizado("S");
											sessionSRV.update(registro);
										}
									}

								} catch (HibernateException e) {
									Auditoria.registrarAuditoria(
											"Error al sincronizar entidad: "
													+ registro.toString(), 'E');
									if (logger.isDebugEnabled()) {
										logger.debug("syncEntidadMaestra(EntidadBD, boolean, boolean) - Error al sincronizar entidad: "
												+ registro.toString());
									}
								}

							}
							// fin del nuevo ciclo
							if (entidad.isMarca()) {
								txO.commit();
							}
							sessionCR.clear();
							sessionSRV.clear();
							consulta.setFirstResult(vanCuantos);
							vacio = consulta.list().isEmpty();
						}
						tx.commit();
						if (entidad.isMarca()) {
							txO.commit();
						}
						sessionCR.clear();
						sessionSRV.clear();
						if (recienteSrv.getTime() == 0)
							recienteSrv = new Timestamp(Calendar.getInstance()
									.getTime().getTime());
						actualizarPlanificador(entidad, false, destino,
								recienteSrv);
						long fin = System.currentTimeMillis();
						if (logger.isInfoEnabled()) {
							logger.info("syncEntidadMaestra(EntidadBD, boolean, boolean) - :: Tardó "
									+ ((fin - inicio) / 1000)
									+ " segs. en sincronizar con Hibernate.");
						}
						if (CR.me != null)
							CR.me.setCajaSincronizada(true);
					}
				} catch (Exception e) {
					if (tx != null) {
						tx.rollback();
						if (entidad.isMarca()) {
							txO.rollback();
						}
						logger.error(
								"syncEntidadMaestra(EntidadBD, boolean, boolean) Transaccion en rollback. T# "
										+ Sesion.getTienda().getNumero()
										+ " - Caja "
										+ Sesion.getCaja().getNumero(), e);
					}
					// Nuevo Proceso si da error a la hora de sincronizar.
					// no actualizara el planificador con la hora del servidor.
					// Se comento el otro proceso.
					if (CR.me != null)
						CR.me.setCajaSincronizada(false);
					if (BDOrigenLocal)
						mensaje = new String(
								"Falla sincronización de la entidad "
										+ entidad.getEsquema() + "."
										+ entidad.getNombre()
										+ " CR -> Servidor");
					else
						mensaje = new String(
								"Falla sincronización de la entidad "
										+ entidad.getEsquema() + "."
										+ entidad.getNombre()
										+ " Servidor -> CR");
					Auditoria.registrarAuditoria(mensaje, 'E');
					logger.error(
							"syncEntidadMaestra(EntidadBD, boolean, boolean) - Falla sincronización de entidad",
							e);

					/*
					 * try { if(CR.me != null) CR.me.setCajaSincronizada(false);
					 * if (recienteSrv.getTime()==0) recienteSrv=new
					 * Timestamp(Calendar.getInstance().getTime().getTime());
					 * actualizarPlanificador(entidad, true, destino,
					 * recienteSrv); if(BDOrigenLocal) mensaje = new
					 * String("Falla sincronización de la entidad "
					 * +entidad.getEsquema
					 * ()+"."+entidad.getNombre()+" CR -> Servidor"); else
					 * mensaje = new
					 * String("Falla sincronización de la entidad "
					 * +entidad.getEsquema
					 * ()+"."+entidad.getNombre()+" Servidor -> CR");
					 * Auditoria.registrarAuditoria(mensaje, 'E'); logger.error(
					 * "syncEntidadMaestra(EntidadBD, boolean, boolean) - Falla sincronización de entidad"
					 * , e); } catch (BaseDeDatosExcepcion e1) { if(CR.me !=
					 * null) CR.me.setCajaSincronizada(false);
					 * Auditoria.registrarAuditoria
					 * ("Falla de BD en actualización del planificador.", 'E');
					 * } catch (SQLException e1) { if(CR.me != null)
					 * CR.me.setCajaSincronizada(false);
					 * Auditoria.registrarAuditoria
					 * ("Falla actualización del planificador.", 'E'); }
					 */
				}
			} catch (JDBCException e) {
				if (CR.me != null)
					CR.me.setCajaSincronizada(false);
				Auditoria
						.registrarAuditoria(
								"Falla conexión con el Servidor BD durante sincronización.",
								'E');
				logger.error("syncEntidadMaestra(EntidadBD, boolean, boolean)",
						e);
			} catch (HibernateException e) {
				if (CR.me != null)
					CR.me.setCajaSincronizada(false);
				Auditoria.registrarAuditoria(
						"Falla configuración de Hibernate.", 'E');
				logger.error("syncEntidadMaestra(EntidadBD, boolean, boolean)",
						e);
			} catch (Throwable t) {
				logger.error("syncEntidadMaestra(EntidadBD, boolean, boolean)",
						t);
			} finally {
				try {
					ConexionHibernate.closeSession(true);
					ConexionHibernate.closeSession(false);
				} catch (HibernateException e3) {
					logger.error(
							"syncEntidadMaestra(EntidadBD, boolean, boolean)",
							e3);
				}
				try {
					if (conLocal != null)
						conLocal.setAutoCommit(true);
				} catch (SQLException e1) {
					logger.error(
							"syncEntidadMaestra(EntidadBD, boolean, boolean)",
							e1);
				}
				try {
					if (conSrv != null)
						conSrv.setAutoCommit(true);
				} catch (SQLException e2) {
					logger.error(
							"syncEntidadMaestra(EntidadBD, boolean, boolean)",
							e2);
				}
			}
		} else if (CR.me != null)
			CR.me.setCajaSincronizada(false);
	}

	/**
	 * Método syncEntidadMaestra Sincroniza la entidad indicada utilizando la
	 * configuración de Hibernate en los archivos *.cfg.xml y la estructura de
	 * la BD mapeada en los archivos *.hbm.xml transafiriendo la data de una
	 * sesión a otra.
	 * 
	 * @param entidad
	 * @param BDOrigenLocal
	 */
	public synchronized static void syncEntidadMaestra(EntidadBD entidad,
			boolean BDOrigenLocal) {
		syncEntidadMaestra(entidad, BDOrigenLocal, false);
	}

	/**
	 * Método sincronizarEntidad Sincroniza la entidad indicada utilizando la
	 * configuración de Hibernate en los archivos *.cfg.xml y la estructura de
	 * la BD mapeada en los archivos *.hbm.xml transafiriendo la data de una
	 * sesión a otra.
	 * 
	 * @param entidad
	 * @param BDOrigenLocal
	 */

	/**
	 * Método sincronizarEntidad Sincroniza la entidad indicada desde la CR al
	 * Servidor.
	 * 
	 * @param entidad
	 */
	/*
	 * En esta función se realizaron modificaciones referentes a la migración a
	 * java 1.6 por jperez Sólo se parametrizó el tipo de dato de los distintos
	 * ArrayList y HashMap Se comentaron variables sin uso Fecha: agosto 2011
	 */
	@SuppressWarnings("unused")
	public synchronized static void sincronizarEntidad(EntidadBD entidad,
			boolean BDOrigenLocal) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		ArrayList<ArrayList<Object>> registros;
		ArrayList<Object> registro;
		String sentenciaSql = "";
		String[] xLoteSentencias;
		Statement loteSentenciasSrv = null;
		Statement loteSentenciasCR = null;
		StringBuffer xCampos, xValores;
		String destino = "C";
		if (BDOrigenLocal)
			destino = "S";
		if (Sesion.isCajaEnLinea()) {
			try {

				if (CR.me != null)
					CR.me.setSyncronizacion();
				if (logger.isInfoEnabled()) {
					logger.info("sincronizarEntidad(EntidadBD, boolean, boolean) - Verificando entidad -> "
							+ entidad.getEsquema() + "." + entidad.getNombre());
				}
				boolean actualizable;
				sentenciaSql = new String("select * from "
						+ entidad.getEsquema() + "." + entidad.getNombre()
						+ " where (" + entidad.getNombre()
						+ ".regactualizado = '" + Sesion.NO + "')");
				actualizable = false;
				resultado = Conexiones.realizarConsulta(sentenciaSql,
						BDOrigenLocal, actualizable, true);
				loteSentenciasSrv = Conexiones.crearSentencia(false, true);
				loteSentenciasCR = Conexiones.crearSentencia(true, true);
				HashMap<String, Object> criterioClave = new HashMap<String, Object>();
				int numColumnas = resultado.getMetaData().getColumnCount();
				HashMap<String, Integer> columnas = new HashMap<String, Integer>();
				for (int i = 0; i < numColumnas; i++) {
					columnas.put(resultado.getMetaData().getColumnName(i + 1)
							.toLowerCase(), new Integer(resultado.getMetaData()
							.getColumnType(i + 1)));
				}

				resultado.beforeFirst();
				while (resultado.next()) {
					// Obtengo información de la CR de la transacción actual
					xCampos = new StringBuffer("");
					xValores = new StringBuffer("");
					for (int i = 0; i < numColumnas; i++) {
						xCampos.append(resultado.getMetaData().getColumnName(
								i + 1));
						xValores.append("?");
						if (i + 1 < numColumnas) {
							xCampos.append(", ");
							xValores.append(", ");
						}
					}
					sentenciaSql = new String("insert into "
							+ entidad.getNombre() + " (" + xCampos
							+ ") values (" + xValores + ")");
					String[] claves = entidad.getClave();
					for (int i = 0; i < claves.length; i++) {
						criterioClave.put(claves[i],
								resultado.getObject(claves[i]));
					}

					registros = new ArrayList<ArrayList<Object>>();
					registro = new ArrayList<Object>();
					for (int i = 0; i < numColumnas; i++) {
						registro.add(resultado.getObject(resultado
								.getMetaData().getColumnName(i + 1)
								.toLowerCase()));
					}
					registros.add(registro);

					xLoteSentencias = Conexiones.crearLoteSentencias(
							resultado.getMetaData(), sentenciaSql, registros,
							false, true);
					for (int i = 0; i < xLoteSentencias.length; i++) {
						loteSentenciasSrv.addBatch(xLoteSentencias[i]);
						// System.out.println(xLoteSentencias[i]);
					}

					// Indico actualización de los registros de transacción en
					// la CR
					StringBuffer criterio = new StringBuffer();
					for (int i = 0; i < claves.length; i++) {
						if ((((Integer) columnas.get(claves[i])).intValue() == Types.VARCHAR)
								|| (((Integer) columnas.get(claves[i]))
										.intValue() == Types.CHAR)
								|| (((Integer) columnas.get(claves[i]))
										.intValue() == Types.DATE))

							criterio.append("(" + claves[i] + " = '"
									+ criterioClave.get(claves[i]) + "')");
						else if (((Integer) columnas.get(claves[i])).intValue() == Types.TIMESTAMP) {
							String val = criterioClave.get(claves[i])
									.toString();
							int pos;
							if (-1 != (pos = val.indexOf('.'))) {
								val = val.substring(0, pos);
							}
							val = val.replaceAll(":", ".");
							criterio.append("(" + claves[i] + " = '" + val
									+ "')");
						} else
							criterio.append("(" + claves[i] + " = "
									+ criterioClave.get(claves[i]) + ")");
						if (i + 1 < claves.length)
							criterio.append(" and ");
					}
					sentenciaSql = new String("update " + entidad.getNombre()
							+ " set regactualizado='" + Sesion.SI + "' where "
							+ criterio.toString());
					loteSentenciasCR.addBatch(sentenciaSql);

					// Ejecuto lote de sentencias en el DBMS del Servidor y de
					// la CR
					Connection connSrv = Conexiones.getConexion(false, true);
					Connection connCR = Conexiones.getConexion(true, true);
					try {
						connCR.setAutoCommit(false);
						connSrv.setAutoCommit(false);
						connSrv.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
						connCR.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
						int[] filas = Conexiones.ejecutarLoteSentencias(
								loteSentenciasSrv, false, false, true);
						filas = Conexiones.ejecutarLoteSentencias(
								loteSentenciasCR, true, false, true);
						connCR.commit();
						connSrv.commit();
					} catch (Exception e) {
						connCR.rollback();
						connSrv.rollback();
						logger.error(
								"sincronizarEntidad(EntidadBD, boolean, boolean) - Error en sinc. T# "
										+ Sesion.getTienda().getNumero()
										+ " - Caja "
										+ Sesion.getCaja().getNumero(), e);
						throw (new BaseDeDatosExcepcion(
								"Error con la BD al sincronizar "
										+ entidad.getNombre()));
					} finally {
						connCR.setAutoCommit(true);
						connSrv.setAutoCommit(true);
					}
					loteSentenciasSrv.clearBatch();
					loteSentenciasCR.clearBatch();
				}
			} catch (BatchUpdateException e) {
				logger.error(
						"sincronizarEntidad(EntidadBD, boolean, boolean) - Error en sinc. T# "
								+ Sesion.getTienda().getNumero() + " - Caja "
								+ Sesion.getCaja().getNumero(), e);
				throw (new BaseDeDatosExcepcion(
						"Error en sentencia por lote al sincronizar "
								+ entidad.getNombre()));
			} catch (SQLException e) {
				logger.error(
						"sincronizarEntidad(EntidadBD, boolean, boolean) - Error en sinc. T# "
								+ Sesion.getTienda().getNumero() + " - Caja "
								+ Sesion.getCaja().getNumero(), e);
				throw (new BaseDeDatosExcepcion(
						"Error en sentencia SQL al sincronizar "
								+ entidad.getNombre()));
			} catch (ConexionExcepcion e) {
				logger.error(
						"sincronizarEntidad(EntidadBD, boolean, boolean) - Error en sinc. T# "
								+ Sesion.getTienda().getNumero() + " - Caja "
								+ Sesion.getCaja().getNumero(), e);
				throw (new BaseDeDatosExcepcion(
						"Error de conexión con BD al sincronizar "
								+ entidad.getNombre()));
			} catch (Throwable t) {
				logger.error(
						"sincronizarEntidad(EntidadBD, boolean, boolean) - Error en sinc. T# "
								+ Sesion.getTienda().getNumero() + " - Caja "
								+ Sesion.getCaja().getNumero(), t);
			} finally {
				if (resultado != null) {
					try {
						resultado.close();
					} catch (SQLException e2) {
						logger.error(
								"sincronizarEntidad(EntidadBD, boolean, boolean)",
								e2);
					}
					resultado = null;
				}
				try {
					if (loteSentenciasCR != null)
						loteSentenciasCR.close();
				} catch (SQLException e1) {
					logger.error(
							"sincronizarEntidad(EntidadBD, boolean, boolean)",
							e1);
				}

				try {
					if (loteSentenciasSrv != null)
						loteSentenciasSrv.close();
				} catch (SQLException e1) {
					logger.error(
							"sincronizarEntidad(EntidadBD, boolean, boolean)",
							e1);
				}
			}
		} else if (CR.me != null)
			CR.me.setCajaSincronizada(false);
	}

	/**
	 * Método syncEntidad Sincroniza los registros entre dos entidades con igual
	 * estructura segun diferencias en el número de registros y fecha de
	 * actualización de los mismos.
	 * 
	 * @param entidad
	 * @param BDOrigenLocal
	 */
	public synchronized static void syncEntidad(EntidadBD entidad,
			boolean BDOrigenLocal) {
		syncEntidad(entidad, BDOrigenLocal, false);
	}

	/**
	 * Método syncEntidad Sincroniza los registros entre dos entidades con igual
	 * estructura segun diferencias en el número de registros y fecha de
	 * actualización de los mismos y en caso de indicarle reemplazar actualiza
	 * los registros de la BD destino en caso de existir.
	 * 
	 * @param entidad
	 * @param BDOrigenLocal
	 * @param reemplazar
	 */
	/*
	 * En esta función se realizaron modificaciones referentes a la migración a
	 * java 1.6 por jperez Sólo se parametrizó el tipo de dato de los distintos
	 * ArrayList Fecha: agosto 2011
	 */
	@SuppressWarnings("unused")
	public synchronized static void syncEntidad(EntidadBD entidad,
			boolean BDOrigenLocal, boolean reemplazar) {
		String sentenciaSql, xSentenciaSql;
		String mensaje = new String(""), mensajeError = new String(""), syncSentido = new String(
				"");
		ResultSet consultaServidor, consultaCR, registrosOrigen = null, registro = null;
		int numRegistrosSrv = 0, numRegistrosCR = 0;
		StringBuffer criterio = new StringBuffer();
		StringBuffer actualizacion = new StringBuffer();
		StringBuffer xCampos, xValores;
		String[] claves = entidad.getClave();
		ArrayList<Object> valores = new ArrayList<Object>(claves.length);
		PreparedStatement sentenciaPreparada = null;
		Timestamp recienteCR;
		Calendar fecha = Calendar.getInstance();
		Timestamp recienteSrv = new Timestamp(fecha.getTime().getTime());
		Date ultimaFechaSync;

		String destino = "C";
		if (BDOrigenLocal)
			destino = "S";

		if (BDOrigenLocal)
			syncSentido = new String(" CR -> Servidor");
		else
			syncSentido = new String(" Servidor -> CR");
		if (Sesion.isCajaEnLinea()) {
			try {
				if (CR.me != null)
					CR.me.setSyncronizacion();
				if (logger.isInfoEnabled()) {
					logger.info("syncEntidad(EntidadBD, boolean, boolean) - Verificando entidad -> "
							+ entidad.getEsquema() + "." + entidad.getNombre());
				}
				boolean diferenciaActualizacion = false, diferenciaRegistros = false;
				boolean conActualizacion = entidad.isActualizacion();
				if (conActualizacion)
					sentenciaSql = new String(
							"select count(*) as numregistros, max(actualizacion) as masreciente from "
									+ entidad.getEsquema() + "."
									+ entidad.getNombre());
				else
					sentenciaSql = new String(
							"select count(*) as numregistros from "
									+ entidad.getEsquema() + "."
									+ entidad.getNombre());

				consultaServidor = Conexiones.realizarConsulta(sentenciaSql,
						false, false, true);
				consultaCR = Conexiones.realizarConsulta(sentenciaSql, true,
						false, true);
				try {
					numRegistrosSrv = consultaServidor.getInt("numregistros");
					numRegistrosCR = consultaCR.getInt("numregistros");
					fecha = GregorianCalendar.getInstance();
					fecha.set(Calendar.YEAR, 1900);
					fecha.set(Calendar.DAY_OF_MONTH, 01);
					fecha.set(Calendar.MONTH, 01);
					fecha.set(Calendar.HOUR_OF_DAY, 12);
					fecha.set(Calendar.MINUTE, 00);
					fecha.set(Calendar.SECOND, 00);
					recienteSrv = new Timestamp(fecha.getTime().getTime());
					recienteCR = new Timestamp(fecha.getTime().getTime());

					if (BDOrigenLocal)
						diferenciaRegistros = numRegistrosCR != numRegistrosSrv;
					else
						diferenciaRegistros = numRegistrosSrv != numRegistrosCR;

					sentenciaSql = new String("select * from "
							+ entidad.getEsquema() + "." + entidad.getNombre());

					if (conActualizacion) {
						if (consultaServidor.getTimestamp("masreciente") != null)
							recienteSrv = new Timestamp(consultaServidor
									.getTimestamp("masreciente").getTime());

						if (consultaCR.getTimestamp("masreciente") != null)
							recienteCR = new Timestamp(consultaCR.getTimestamp(
									"masreciente").getTime());

						if (BDOrigenLocal)
							diferenciaActualizacion = recienteCR
									.after(recienteSrv);
						else
							diferenciaActualizacion = recienteSrv
									.after(recienteCR);

						xSentenciaSql = new String(
								"select actualizacion as ultimasync from "
										+ entidad.getEsquema()
										+ ".planificador where entidad='"
										+ entidad.getNombre().toLowerCase()
										+ "' and fallasincronizador='N' and destino = '"
										+ destino + "'");
						consultaCR = Conexiones.realizarConsulta(xSentenciaSql,
								true, false, true);
						if (consultaCR.next()) {
							if (consultaCR.getTimestamp("ultimasync") != null) {
								ultimaFechaSync = new Date(consultaCR
										.getTimestamp("ultimasync").getTime());
								SimpleDateFormat formato = new SimpleDateFormat(
										"yyyyMMdd000000");
								sentenciaSql = new String("select * from "
										+ entidad.getEsquema() + "."
										+ entidad.getNombre()
										+ " where actualizacion >= '"
										+ formato.format(ultimaFechaSync) + "'");
							}
						}
					}

				} finally {
					consultaServidor.close();
					consultaCR.close();
					consultaServidor = null;
					consultaCR = null;
				}

				try {
					if (diferenciaRegistros || diferenciaActualizacion
							|| reemplazar) {
						if (diferenciaRegistros)
							sentenciaSql = new String("select * from "
									+ entidad.getEsquema() + "."
									+ entidad.getNombre());
						registrosOrigen = Conexiones.realizarConsulta(
								sentenciaSql, BDOrigenLocal, false, true);
						registrosOrigen.beforeFirst();

						if (diferenciaRegistros) {
							mensaje = new String(
									"Diferencia en número de registros de la entidad "
											+ entidad.getEsquema() + "."
											+ entidad.getNombre()
											+ ":: Servidor -> "
											+ numRegistrosSrv + " / CR -> "
											+ numRegistrosCR);
							// Auditoria.registrarAuditoria(mensaje, 'O');
						}
						if (diferenciaActualizacion) {
							mensaje = new String(
									"Diferencia de actualización de registros de la entidad "
											+ entidad.getEsquema() + "."
											+ entidad.getNombre()
											+ ":: Servidor -> " + recienteSrv
											+ " / CR -> " + recienteCR);
							// Auditoria.registrarAuditoria(mensaje, 'O');
						}

						xSentenciaSql = sentenciaSql.replace('*', '%')
								.replaceFirst("%",
										"count(*) as cuantosRegistros");
						consultaCR = Conexiones.realizarConsulta(xSentenciaSql,
								BDOrigenLocal, false, true);
						registrosOrigen.beforeFirst();
						int cuantosRegistros = 0;
						try {
							cuantosRegistros = consultaCR
									.getInt("cuantosRegistros");
						} finally {
							consultaCR.close();
							consultaCR = null;
						}
						if (BDOrigenLocal)
							mensaje = new String(
									"Registros a actualizar de la entidad "
											+ entidad.getEsquema() + "."
											+ entidad.getNombre() + " -> "
											+ cuantosRegistros
											+ " del Servidor");
						else
							mensaje = new String(
									"Registros a actualizar de la entidad "
											+ entidad.getEsquema() + "."
											+ entidad.getNombre() + " -> "
											+ cuantosRegistros + " de la CR");
						// Auditoria.registrarAuditoria(mensaje, 'O');

						for (int i = 0; i < claves.length; i++) {
							criterio.append("(" + entidad.getEsquema() + "."
									+ entidad.getNombre() + "."
									+ entidad.getClave()[i] + " = ?)");
							if (i + 1 < claves.length)
								criterio.append(" and ");
						}

						boolean bdDestino = BDOrigenLocal == true ? false
								: true;
						while (registrosOrigen.next()) {
							actualizacion = new StringBuffer();
							xCampos = new StringBuffer();
							xValores = new StringBuffer();
							sentenciaSql = new String("select * from "
									+ entidad.getEsquema() + "."
									+ entidad.getNombre() + " where "
									+ criterio);
							sentenciaPreparada = Conexiones.getConexion(
									bdDestino, true).prepareStatement(
									sentenciaSql,
									ResultSet.TYPE_SCROLL_INSENSITIVE,
									ResultSet.CONCUR_READ_ONLY);

							for (int i = 0; i < claves.length; i++) {
								Object o = registrosOrigen.getObject(claves[i]);
								if (o instanceof String) {
									o = ((String) o).trim();
								}
								valores.add(i, o);
								sentenciaPreparada.setObject(i + 1,
										valores.get(i));
							}

							sentenciaPreparada.executeQuery();
							registro = sentenciaPreparada.getResultSet();

							int numColumnas = registro.getMetaData()
									.getColumnCount();
							if (registro.first()) {
								for (int i = 0; i < numColumnas; i++) {
									actualizacion.append(entidad.getEsquema()
											+ "."
											+ entidad.getNombre()
											+ "."
											+ registro.getMetaData()
													.getColumnName(i + 1)
											+ " = ?");
									if (i + 1 < numColumnas)
										actualizacion.append(", ");
								}
								actualizacion.insert(0, " set ");
								sentenciaSql = new String("update "
										+ entidad.getEsquema() + "."
										+ entidad.getNombre() + " "
										+ actualizacion + " where " + criterio);
							} else {
								for (int i = 0; i < numColumnas; i++) {
									xCampos.append(registro.getMetaData()
											.getColumnName(i + 1));
									xValores.append("?");
									if (i + 1 < numColumnas) {
										xCampos.append(", ");
										xValores.append(", ");
									}
								}
								sentenciaSql = new String("insert into "
										+ entidad.getEsquema() + "."
										+ entidad.getNombre() + " (" + xCampos
										+ ") values (" + xValores + ")");
							}
							registro.beforeFirst();

							sentenciaPreparada = Conexiones.getConexion(
									bdDestino, true).prepareStatement(
									sentenciaSql);
							boolean ejecutar = true;

							for (int i = 1; i <= numColumnas; i++) {
								String nombreColumna = new String(
										registrosOrigen.getMetaData()
												.getColumnName(i));
								if ((registrosOrigen.getMetaData()
										.isNullable(i) == ResultSetMetaData.columnNoNulls)
										&& (registrosOrigen
												.getObject(nombreColumna) == null)) {
									ejecutar = false;
									break;
								}
							}

							if (ejecutar) {
								for (int i = 1; i <= numColumnas; i++) {
									String nombreColumna = new String(
											registrosOrigen.getMetaData()
													.getColumnName(i));
									sentenciaPreparada.setObject(i,
											registrosOrigen
													.getObject(nombreColumna));
								}
								ejecutar = true;

								if (registro.next()) {
									for (int i = numColumnas + 1; i <= numColumnas
											+ claves.length; i++) {
										sentenciaPreparada
												.setObject(
														i,
														registrosOrigen
																.getObject(claves[i
																		- (numColumnas + 1)]));
									}
									if (conActualizacion)
										ejecutar = registrosOrigen
												.getTimestamp("actualizacion")
												.after(registro
														.getTimestamp("actualizacion"));
								}
								registro.beforeFirst();

								if (ejecutar)
									sentenciaPreparada.execute();
							}
						}
						actualizarPlanificador(entidad, false, destino);
						mensaje = new String("Sincronizada "
								+ entidad.getEsquema() + "."
								+ entidad.getNombre() + syncSentido);
						// Auditoria.registrarAuditoria(mensaje, 'O');
					}

				} finally {
					if (registrosOrigen != null) {
						registrosOrigen.close();
						registrosOrigen = null;
					}
					if (registro != null) {
						registro.close();
						registro = null;
					}
				}
				if (CR.me != null)
					CR.me.setCajaSincronizada(true);
			} catch (BaseDeDatosExcepcion e) {
				if (CR.me != null)
					CR.me.setCajaSincronizada(false);
				mensajeError = new String("Falla BD en sincronización de "
						+ entidad.getEsquema() + "." + entidad.getNombre()
						+ syncSentido);
				new BaseDeDatosExcepcion(mensajeError);
				logger.error(
						"syncEntidad(EntidadBD, boolean, boolean) - Error en sinc. T# "
								+ Sesion.getTienda().getNumero() + " - Caja "
								+ Sesion.getCaja().getNumero(), e);
			} catch (ConexionExcepcion e) {
				if (CR.me != null)
					CR.me.setCajaSincronizada(false);
				mensajeError = new String(
						"Falla conexión en sincronización de "
								+ entidad.getEsquema() + "."
								+ entidad.getNombre() + syncSentido);
				logger.error(
						"syncEntidad(EntidadBD, boolean, boolean) - Error en sinc. T# "
								+ Sesion.getTienda().getNumero() + " - Caja "
								+ Sesion.getCaja().getNumero(), e);
				new ConexionExcepcion(mensajeError);
			} catch (SQLException e) {
				try {

					logger.error(
							"syncEntidad(EntidadBD, boolean, boolean) - Error en sinc. T# "
									+ Sesion.getTienda().getNumero()
									+ " - Caja " + Sesion.getCaja().getNumero(),
							e);
					actualizarPlanificador(entidad, true, destino);
				} catch (BaseDeDatosExcepcion e1) {
					if (CR.me != null)
						CR.me.setCajaSincronizada(false);
					Auditoria.registrarAuditoria(
							"Falla de BD en actualización del planificador.",
							'E');
				} catch (SQLException e1) {
					if (CR.me != null)
						CR.me.setCajaSincronizada(false);
					Auditoria.registrarAuditoria(
							"Falla actualización del planificador.", 'E');
				}
				mensajeError = new String(
						"Falla sentencia SQL en sincronización de "
								+ entidad.getEsquema() + "."
								+ entidad.getNombre() + syncSentido);
				new SQLException(mensajeError);
			} catch (Exception e) {
				logger.error(
						"syncEntidad(EntidadBD, boolean, boolean) - Error en sinc. T# "
								+ Sesion.getTienda().getNumero() + " - Caja "
								+ Sesion.getCaja().getNumero(), e);
				if (CR.me != null)
					CR.me.setCajaSincronizada(false);
				mensajeError = new String(
						"Falla desconocida en sincronización de "
								+ entidad.getEsquema() + "."
								+ entidad.getNombre() + syncSentido);
				new SQLException(mensajeError);
			} catch (Throwable t) {
				logger.error("syncEntidad(EntidadBD, boolean, boolean)", t);
			} finally {
				try {
					if (sentenciaPreparada != null)
						sentenciaPreparada.close();
				} catch (SQLException e1) {
				}
			}
		} else if (CR.me != null)
			CR.me.setCajaSincronizada(false);
	}

	/**
	 * Método syncAfiliado
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan el el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncAfiliado(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "codafiliado" };
		boolean actualizacion = true;
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "afiliado",
				clave, actualizacion);
		BeansSincronizador.syncEntidadMaestra(entidad, BDOrigen, false);
	}

	/**
	 * Método syncAnulacionDeAbonos
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan el el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncAnulacionDeAbonos(boolean BDOrigen)
			throws SQLException, BaseDeDatosExcepcion {
		String[] clave = { "numtienda", "numcaja", "numabono", "fechaabono",
				"numabonoanulado", "fechaabonoanulado", "numservicio" };
		boolean actualizacion = false;
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"anulaciondeabonos", clave, actualizacion);
		BeansSincronizador.sincronizarEntidad(entidad, BDOrigen);
	}

	/**
	 * Método syncAuditoria
	 * 
	 * @param BDOrigenLocal
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan el el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncAuditoria(boolean BDOrigenLocal)
			throws SQLException, BaseDeDatosExcepcion {
		String[] clave = { "idauditoria", "numtienda", "numcaja", "codusuario",
				"tiporegistro", "fecha" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "auditoria",
				clave, false, true);
		BeansSincronizador.syncEntidadMaestra(entidad, BDOrigenLocal);
	}

	/**
	 * Método syncBanco
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan el el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncBanco(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "codbanco" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "banco",
				clave, false);
		BeansSincronizador.syncEntidad(entidad, BDOrigen, true);
	}

	/**
	 * Método syncCaja
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan el el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncCaja(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "numtienda", "numcaja" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "caja", clave,
				false);
		BeansSincronizador.syncEntidad(entidad, BDOrigen, true);
	}

	/**
	 * Método syncCargo
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan el el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncCargo(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "codcargo" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "cargo",
				clave, false);
		BeansSincronizador.syncEntidad(entidad, BDOrigen, true);
	}

	/**
	 * Método syncClientesTemporales
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncClientesTemporales(boolean BDOrigen)
			throws SQLException, BaseDeDatosExcepcion {
		String[] clave = { "codafiliado" };
		boolean actualizacion = true;
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "afiliado",
				clave, actualizacion);
		BeansSincronizador.syncEntidadMaestra(entidad, BDOrigen, true);
	}

	/**
	 * Método syncCondicionVenta
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan el el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncCondicionVenta(boolean BDOrigen)
			throws SQLException, BaseDeDatosExcepcion {
		String[] clave = { "codcondicionventa" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"condicionventa", clave, false);
		BeansSincronizador.syncEntidad(entidad, BDOrigen, true);
	}

	/**
	 * Método syncDepartamento
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan el el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncDepartamento(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "coddepartamento" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"departamento", clave, false);
		BeansSincronizador.syncEntidad(entidad, BDOrigen, true);
	}

	/**
	 * Método syncDetalleAfiliado
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan el el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncDetalleAfiliado(boolean BDOrigen)
			throws SQLException, BaseDeDatosExcepcion {
		String[] clave = { "numtienda", "codafiliado", "mensaje" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"detalleafiliado", clave, true);
		BeansSincronizador.syncEntidad(entidad, BDOrigen);
	}

	/**
	 * Método syncDetallePromocion
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan el el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncDetallePromocion(boolean BDOrigen)
			throws SQLException, BaseDeDatosExcepcion {
		String[] clave = { "codpromocion", "numdetalle" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"detallepromocion", clave, false);
		EntitySynchronizerFactory factory = new EntitySynchronizerFactory();
		EntitySynchronizer instance = factory.getInstance();
		instance.synchronize(entidad, BDOrigen, false, false);
		// BeansSincronizador.syncEntidad(entidad, BDOrigen, true);
	}

	/**
	 * Método syncDetalleServicio (Ojo)->Sincronizar Servicio atómicamente??
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan el el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncDetalleServicio(boolean BDOrigen)
			throws SQLException, BaseDeDatosExcepcion {
		String[] clave = { "numtienda", "codtiposervicio", "numservicio",
				"fecha", "codproducto", "codcondicionventa", "correlativoitem" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"detalleservicio", clave, false);
		BeansSincronizador.syncEntidad(entidad, BDOrigen, true);
	}

	/**
	 * Método syncDetalleTransaccion (Ojo)->Sincronizar Transaccion
	 * atómicamente??
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan el el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncDetalleTransaccion(boolean BDOrigen)
			throws SQLException, BaseDeDatosExcepcion {
		String[] clave = { "numtienda", "fecha", "numcajainicia",
				"codproducto", "codcondicionventa", "correlativoitem" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"detalletransaccion", clave, false);
		BeansSincronizador.syncEntidad(entidad, BDOrigen);
	}

	/**
	 * Método syncDevolucionVenta
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan el el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncDevolucionVenta(boolean BDOrigen)
			throws SQLException, BaseDeDatosExcepcion {
		String[] clave = { "numtiendadevolucion", "fechadevolucion",
				"numcajadevolucion", "numtransacciondev", "numtiendaventa",
				"fechaventa", "numcajaventa", "numtransaccionvta" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"devolucionventa", clave, false);
		BeansSincronizador.sincronizarEntidad(entidad, BDOrigen);
	}

	/**
	 * Método syncEstadoCaja
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan el el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncEstadoCaja(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "idestado" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"estadodecaja", clave, false);
		BeansSincronizador.syncEntidad(entidad, BDOrigen, true);
	}

	/**
	 * Método syncFormaDePago (Ojo)->Claves Correctas??
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan el el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncFormaDePago(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "codformadepago" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "formadepago",
				clave, false);
		BeansSincronizador.syncEntidad(entidad, BDOrigen, true);
	}

	/**
	 * Método syncFuncion
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan el el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncFuncion(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "codfuncion", "codmodulo" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "funcion",
				clave, true);
		BeansSincronizador.syncEntidad(entidad, BDOrigen);
	}

	/**
	 * Método syncFuncionMetodos
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncFuncionMetodos(boolean BDOrigen)
			throws SQLException, BaseDeDatosExcepcion {
		String[] clave = { "codmodulo", "codfuncion", "codmetodo" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"funcionmetodos", clave, false);
		BeansSincronizador.syncEntidad(entidad, BDOrigen, true);
	}

	/**
	 * Método syncFuncionPerfil
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncFuncionPerfil(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "codperfil", "codmodulo", "codfuncion" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"funcionperfil", clave, true);
		BeansSincronizador.syncEntidad(entidad, BDOrigen);
	}

	/**
	 * Método syncImpuestoRegion
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncImpuestoRegion(boolean BDOrigen)
			throws SQLException, BaseDeDatosExcepcion {
		String[] clave = { "codimpuesto", "codregion", "fechaemision" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"impuestoregion", clave, false);
		BeansSincronizador.syncEntidad(entidad, BDOrigen, true);
	}

	/**
	 * Método syncLineaSeccion
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncLineaSeccion(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "codseccion", "coddepartamento" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"lineaseccion", clave, false);
		BeansSincronizador.syncEntidadMaestra(entidad, BDOrigen, true);
	}

	/**
	 * Método syncME
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncME(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "edoinicial", "codmetodo" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"maquinadeestado", clave, false);
		BeansSincronizador.syncEntidad(entidad, BDOrigen, true);
	}

	/**
	 * Método syncMetodos
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncMetodos(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "codmetodo" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "metodos",
				clave, false);
		BeansSincronizador.syncEntidad(entidad, BDOrigen, true);
	}

	/**
	 * Método syncModulos
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncModulos(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "codmodulo" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "modulos",
				clave, false);
		BeansSincronizador.syncEntidad(entidad, BDOrigen);
	}

	/**
	 * Método syncPagoDeAbonos
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncPagoDeAbonos(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "numtienda", "numcaja", "numabono", "fecha",
				"codformadepago" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"pagodeabonos", clave, false);
		BeansSincronizador.syncEntidad(entidad, BDOrigen);
	}

	/**
	 * Método syncPagoDeTransaccion
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncPagoDeTransaccion(boolean BDOrigen)
			throws SQLException, BaseDeDatosExcepcion {
		String[] clave = { "numtienda", "fecha", "numcaja", "numtransaccion",
				"codformadepago" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"pagodetransaccion", clave, false);
		BeansSincronizador.syncEntidad(entidad, BDOrigen);
	}

	/**
	 * Método syncPerfil
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncPerfil(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "codperfil" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "perfil",
				clave, false);
		BeansSincronizador.syncEntidad(entidad, BDOrigen);
	}

	/**
	 * Método syncPlanificador
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncPlanificador(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "numtienda", "numcaja", "entidad" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"planificador", clave, true);
		BeansSincronizador.syncEntidad(entidad, BDOrigen, true);
	}

	/**
	 * Método syncProdCodigoExterno
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncProdCodigoExterno(boolean BDOrigen)
			throws SQLException, BaseDeDatosExcepcion {
		String[] clave = { "codexterno" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"prodcodigoexterno", clave, true);
		EntitySynchronizerFactory factory = new EntitySynchronizerFactory();
		EntitySynchronizer instance = factory.getInstance();
		instance.synchronize(entidad, BDOrigen, false, false);
	}

	/**
	 * Método syncProducto
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncProducto(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "codproducto" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "producto",
				clave, true);
		EntitySynchronizerFactory factory = new EntitySynchronizerFactory();
		EntitySynchronizer instance = factory.getInstance();
		instance.synchronize(entidad, BDOrigen, false, true);
		// BeansSincronizador.syncEntidadMaestra(entidad, BDOrigen);
	}

	/**
	 * Método syncPromocion
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncPromocion(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "codpromocion", "tipopromocion" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "promocion",
				clave, false);
		EntitySynchronizerFactory factory = new EntitySynchronizerFactory();
		EntitySynchronizer instance = factory.getInstance();
		instance.synchronize(entidad, BDOrigen, false, false);
		// BeansSincronizador.syncEntidad(entidad, BDOrigen, true);
	}

	/**
	 * Método syncReciboEmitido
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncReciboEmitido(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "numtienda", "numcaja", "numrecibo", "fechaemision" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"reciboemitido", clave, false);
		BeansSincronizador.sincronizarEntidad(entidad, BDOrigen);
	}

	/**
	 * Método syncRegion
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncRegion(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "codregion" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "region",
				clave, false);
		BeansSincronizador.syncEntidad(entidad, BDOrigen, true);
	}

	/**
	 * Método syncServicio
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncServicio(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "numtienda", "codtiposervicio", "numservicio",
				"fecha" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "servicio",
				clave, false);
		BeansSincronizador.syncEntidad(entidad, BDOrigen, true);
	}

	/**
	 * Método syncTienda
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncTienda(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "numtienda" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "tienda",
				clave, false);
		BeansSincronizador.syncEntidad(entidad, BDOrigen, true);
	}

	/**
	 * Método syncTiendaPublicidad (Ojo)->Entidades sin claves...Acciones??
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncTiendaPublicidad(boolean BDOrigen)
			throws SQLException, BaseDeDatosExcepcion {
		String[] clave = { "numtienda", "orden" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"tiendapublicidad", clave, false);
		BeansSincronizador.syncEntidad(entidad, BDOrigen, true);
	}

	/**
	 * Método syncTipoCaptura
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncTipoCaptura(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "codtipocaptura" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "tipocaptura",
				clave, false);
		BeansSincronizador.syncEntidad(entidad, BDOrigen, true);
	}

	/**
	 * Método syncTipoServicio
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncTipoServicio(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "codtiposervicio" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"tiposervicio", clave, false);
		BeansSincronizador.syncEntidad(entidad, BDOrigen, true);
	}

	/**
	 * Método syncTransaccion (Ojo)->Validar claves correctas!!
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncTransaccion(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "numtienda", "fecha", "numcajainicia",
				"numregcajainicia", "numcajafinaliza", "numtransaccion" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "transaccion",
				clave, false);
		BeansSincronizador.syncEntidad(entidad, BDOrigen);
	}

	/**
	 * Método syncTransaccion (Ojo)->Validar claves correctas!!
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncTransaccionAbono(boolean BDOrigen)
			throws SQLException, BaseDeDatosExcepcion {
		String[] clave = { "numtienda", "numcaja", "fecha", "numabono",
				"tipotransaccionabono" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"transaccionabono", clave, false);
		BeansSincronizador.syncEntidad(entidad, BDOrigen);
	}

	/**
	 * Método syncUnidadDeVenta
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncUnidadDeVenta(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "codunidadventa" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"unidaddeventa", clave, false);
		BeansSincronizador.syncEntidad(entidad, BDOrigen, true);
	}

	/**
	 * Método syncUsuario
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncUsuario(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "numtienda", "numficha" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "usuario",
				clave, true);
		BeansSincronizador.syncEntidad(entidad, BDOrigen);
	}

	/**
	 * Método syncCambioDelDia
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncCambioDelDia(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "tipoformadepago", "cambiodeldia", "actualizacion" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"cambiodeldia", clave, true);
		try {
			BeansSincronizador.syncEntidad(entidad, BDOrigen);
		} catch (Exception e) {
			logger.error(
					"syncEntidad(EntidadBD, boolean, boolean) - Error en sincronizando la tabla: "
							+ entidad.getNombre() + " en  T# "
							+ Sesion.getTienda().getNumero() + " - Caja "
							+ Sesion.getCaja().getNumero(), e);
		}
	}

	/**
	 * Método syncAtcm23
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncAtcm23(boolean BDOrigen) {
		String[] clave = { "codedo" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "atcm23",
				clave, true);
		try {
			BeansSincronizador.syncEntidad(entidad, BDOrigen);
		} catch (Exception e) {
			logger.error(
					"syncEntidad(EntidadBD, boolean, boolean) - Error en sincronizando la tabla: "
							+ entidad.getNombre() + " en  T# "
							+ Sesion.getTienda().getNumero() + " - Caja "
							+ Sesion.getCaja().getNumero(), e);
		}
	}

	/**
	 * Método syncAtcm24
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncAtcm24(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "codedo", "codciu" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "atcm24",
				clave, true);
		try {
			BeansSincronizador.syncEntidad(entidad, BDOrigen);
		} catch (Exception e) {
			logger.error(
					"syncEntidad(EntidadBD, boolean, boolean) - Error en sincronizando la tabla: "
							+ entidad.getNombre() + " en  T# "
							+ Sesion.getTienda().getNumero() + " - Caja "
							+ Sesion.getCaja().getNumero(), e);
		}
	}

	/**
	 * Método syncAtcm25
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncAtcm25(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "codedo", "codciu", "codurb" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "atcm25",
				clave, true);
		try {
			BeansSincronizador.syncEntidad(entidad, BDOrigen);
		} catch (Exception e) {
			logger.error(
					"syncEntidad(EntidadBD, boolean, boolean) - Error en sincronizando la tabla: "
							+ entidad.getNombre() + " en  T# "
							+ Sesion.getTienda().getNumero() + " - Caja "
							+ Sesion.getCaja().getNumero(), e);
		}
	}

	/**
	 * Método syncEntidadCR Sincroniza la entidad indicada desde la BD del
	 * servidor (BD origen) a la BD de la CR.
	 * 
	 * @param entidad
	 *            - Nombre de la entidad del esquema de BD del sistema.
	 *            Considera el mismo diseño de datos para el DBMS origen y el
	 *            destino a sincronizar.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public synchronized static void syncEntidadCR(String entidad)
			throws SQLException, BaseDeDatosExcepcion {
		if (/* !Sesion.getCaja().getEstado().equals(Sesion.FACTURANDO) && */Sesion
				.isCajaEnLinea()) {
			entidad = new String(entidad.trim().toLowerCase());

			// //nueva CRM wdiaz
			/*
			 * if(entidad.equals("transaccionafiliadocrm"))
			 * BeansSincronizador.syncTransaccionafiliadocrm(false);
			 */
			// fin
			if (entidad.equals("afiliado"))
				BeansSincronizador.syncAfiliado(false);
			// Solicitud Cliente CRM
			else if (entidad.equals("solicitarcliente"))
				BeansSincronizador.syncsolicitarcliente(false);
			//
			else if (entidad.equals("anulaciondeabonos"))
				BeansSincronizador.syncAnulacionDeAbonos(false);
			else if (entidad.equals("auditoria"))
				BeansSincronizador.syncAuditoria(false);
			else if (entidad.equals("banco"))
				BeansSincronizador.syncBanco(false);
			else if (entidad.equals("caja"))
				BeansSincronizador.syncCaja(false);
			else if (entidad.equals("cargo"))
				BeansSincronizador.syncCargo(false);
			else if (entidad.equals("condicionventa"))
				BeansSincronizador.syncCondicionVenta(false);
			else if (entidad.equals("departamento"))
				BeansSincronizador.syncDepartamento(false);
			else if (entidad.equals("detalleafiliado"))
				BeansSincronizador.syncDetalleAfiliado(false);
			// else if(entidad.equals("detallepromocion"))
			// BeansSincronizador.sincronizarTotalTablas("detallepromocion");
			else if (entidad.equals("detallepromocion"))
				BeansSincronizador.syncDetallePromocion(false);
			else if (entidad.equals("detalleservicio"))
				BeansSincronizador.syncDetalleServicio(false);
			else if (entidad.equals("devolucionventa"))
				BeansSincronizador.syncDevolucionVenta(false);
			else if (entidad.equals("estadodecaja"))
				BeansSincronizador.syncEstadoCaja(false);
			else if (entidad.equals("formadepago"))
				BeansSincronizador.syncFormaDePago(false);
			else if (entidad.equals("funcion"))
				BeansSincronizador.syncFuncion(false);
			else if (entidad.equals("funcionmetodos"))
				BeansSincronizador.syncFuncionMetodos(false);
			else if (entidad.equals("funcionperfil"))
				BeansSincronizador.syncFuncionPerfil(false);
			else if (entidad.equals("impuestoregion"))
				BeansSincronizador.syncImpuestoRegion(false);
			else if (entidad.equals("lineaseccion"))
				BeansSincronizador.syncLineaSeccion(false);
			else if (entidad.equals("maquinadeestado"))
				BeansSincronizador.syncME(false);
			else if (entidad.equals("metodos"))
				BeansSincronizador.syncMetodos(false);
			else if (entidad.equals("modulos"))
				BeansSincronizador.syncModulos(false);
			else if (entidad.equals("perfil"))
				BeansSincronizador.syncPerfil(false);
			else if (entidad.equals("planificador"))
				BeansSincronizador.syncPlanificador(false);
			else if (entidad.equals("producto"))
				BeansSincronizador.syncProducto(false);
			else if (entidad.equals("prodcodigoexterno"))
				BeansSincronizador.syncProdCodigoExterno(false);
			// else if(entidad.equals("promocion"))
			// BeansSincronizador.sincronizarTotalTablas("promocion");
			else if (entidad.equals("promocion"))
				BeansSincronizador.syncPromocion(false);
			else if (entidad.equals("reciboemitido"))
				BeansSincronizador.syncPromocion(false);
			else if (entidad.equals("region"))
				BeansSincronizador.syncRegion(false);
			else if (entidad.equals("saldocliente"))
				BeansSincronizador.syncRegion(false);
			else if (entidad.equals("servicio"))
				BeansSincronizador.syncServicio(false);
			else if (entidad.equals("tienda"))
				BeansSincronizador.syncTienda(false);
			else if (entidad.equals("tiendapublicidad"))
				BeansSincronizador.syncTiendaPublicidad(false);
			else if (entidad.equals("tipocaptura"))
				BeansSincronizador.syncTipoCaptura(false);
			else if (entidad.equals("tiposervicio"))
				BeansSincronizador.syncTipoServicio(false);
			else if (entidad.equals("unidadventa"))
				BeansSincronizador.syncUnidadDeVenta(false);
			else if (entidad.equals("usuario"))
				BeansSincronizador.syncUsuario(false);
			else if (entidad.equals("atcm23"))
				BeansSincronizador.syncAtcm23(false);
			else if (entidad.equals("atcm24"))
				BeansSincronizador.syncAtcm24(false);
			else if (entidad.equals("atcm25"))
				BeansSincronizador.syncAtcm25(false);
			// *** 11/11/2008 IROJAS.
			// *** Agregada sincronización de promociones extendidas
			else if (entidad.equals("promocionesextendidas"))
				BeansSincronizador.sincronizarPromocionesExtendidas();
			// ********

			else if (entidad.equals("servidortienda"))
				BeansSincronizador.syncServidorTienda(false);
			else if (entidad.equals("tipoeventolistaregalos"))
				BeansSincronizador.syncTipoEventoListaRegalos(false);

			// Entidades Bono Regalo Electronico
			else if (entidad.equals("br_opcionhabilitada"))
				BeansSincronizador.syncBROpcionHabilitada(false);
			else if (entidad.equals("br_condiciones"))
				BeansSincronizador.syncBRCondiciones(false);
			else if (entidad.equals("br_promocion"))
				BeansSincronizador.syncBRPromocion(false);
			/*
			 * 
			 */
			else if (entidad.equals("cambiodeldia")) {
				BeansSincronizador.syncCambioDelDia(false);
			}
		} else if (CR.me != null)
			CR.me.setCajaSincronizada(false);
	}

	/**
	 * Método syncEntidadSrv Sincroniza la entidad indicada desde la BD de la
	 * Caja Registradora (BD origen) hacia BD del Servidor.
	 * 
	 * @param entidad
	 *            - Nombre de la entidad del esquema de BD del sistema.
	 *            Considera el mismo diseño de datos para el DBMS origen y el
	 *            destino a sincronizar.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public synchronized static void syncEntidadSrv(String entidad)
			throws SQLException, BaseDeDatosExcepcion {
		if (/* !Sesion.getCaja().getEstado().equals(Sesion.FACTURANDO) && */Sesion
				.isCajaEnLinea()) {
			entidad = new String(entidad.trim().toLowerCase());

			// nueva CRM wdiaz
			if (entidad.equals("transaccionafiliadocrm"))
				BeansSincronizador.syncTransaccionafiliadocrm(true);
			// fin
			if (entidad.equals("afiliado"))
				syncAfiliado(true);
			else if (entidad.equals("anulaciondeabonos"))
				BeansSincronizador.syncAnulacionDeAbonos(true);
			else if (entidad.equals("auditoria"))
				BeansSincronizador.syncAuditoria(true);
			else if (entidad.equals("banco"))
				BeansSincronizador.syncBanco(true);
			else if (entidad.equals("caja"))
				BeansSincronizador.syncCaja(true);
			else if (entidad.equals("cargo"))
				BeansSincronizador.syncCargo(true);
			else if (entidad.equals("condicionventa"))
				BeansSincronizador.syncCondicionVenta(true);
			else if (entidad.equals("departamento"))
				BeansSincronizador.syncDepartamento(true);
			else if (entidad.equals("detalleafiliado"))
				BeansSincronizador.syncDetalleAfiliado(true);
			else if (entidad.equals("detallepromocion"))
				BeansSincronizador.syncDetallePromocion(true);
			else if (entidad.equals("detalleservicio"))
				BeansSincronizador.syncDetalleServicio(true);
			else if (entidad.equals("devolucionventa"))
				BeansSincronizador.syncDevolucionVenta(true);
			else if (entidad.equals("estadodecaja"))
				BeansSincronizador.syncEstadoCaja(true);
			else if (entidad.equals("formadepago"))
				BeansSincronizador.syncFormaDePago(true);
			else if (entidad.equals("funcion"))
				BeansSincronizador.syncFuncion(true);
			else if (entidad.equals("funcionmetodos"))
				BeansSincronizador.syncFuncionMetodos(true);
			else if (entidad.equals("funcionperfil"))
				BeansSincronizador.syncFuncionPerfil(true);
			else if (entidad.equals("impuestoregion"))
				BeansSincronizador.syncImpuestoRegion(true);
			else if (entidad.equals("lineaseccion"))
				BeansSincronizador.syncLineaSeccion(true);
			else if (entidad.equals("maquinadeestado"))
				BeansSincronizador.syncME(true);
			else if (entidad.equals("metodos"))
				BeansSincronizador.syncMetodos(true);
			else if (entidad.equals("modulos"))
				BeansSincronizador.syncModulos(true);
			else if (entidad.equals("perfil"))
				BeansSincronizador.syncPerfil(true);
			else if (entidad.equals("planificador"))
				BeansSincronizador.syncPlanificador(true);
			else if (entidad.equals("prodcodigoexterno"))
				BeansSincronizador.syncProdCodigoExterno(true);
			else if (entidad.equals("producto"))
				BeansSincronizador.syncProducto(true);
			else if (entidad.equals("promocion"))
				BeansSincronizador.syncPromocion(true);
			else if (entidad.equals("reciboemitido"))
				BeansSincronizador.syncPromocion(true);
			else if (entidad.equals("region"))
				BeansSincronizador.syncRegion(true);
			else if (entidad.equals("saldocliente"))
				BeansSincronizador.syncRegion(true);
			else if (entidad.equals("servicio"))
				BeansSincronizador.syncServicio(true);
			else if (entidad.equals("tienda"))
				BeansSincronizador.syncTienda(true);
			else if (entidad.equals("tiendapublicidad"))
				BeansSincronizador.syncTiendaPublicidad(true);
			else if (entidad.equals("tipocaptura"))
				BeansSincronizador.syncTipoCaptura(true);
			else if (entidad.equals("tiposervicio"))
				BeansSincronizador.syncTipoServicio(true);
			else if (entidad.equals("unidadventa"))
				BeansSincronizador.syncUnidadDeVenta(true);
			else if (entidad.equals("usuario"))
				BeansSincronizador.syncUsuario(true);
			else if (entidad.equals("atcm23"))
				BeansSincronizador.syncAtcm23(true);
			else if (entidad.equals("atcm24"))
				BeansSincronizador.syncAtcm24(true);
			else if (entidad.equals("atcm25"))
				BeansSincronizador.syncAtcm25(true);
			else if (entidad.equals("servidortienda"))
				BeansSincronizador.syncServidorTienda(false);
			else if (entidad.equals("tipoeventolistaregalos"))
				BeansSincronizador.syncTipoEventoListaRegalos(false);
		} else if (CR.me != null)
			CR.me.setCajaSincronizada(false);
	}

	/**
	 * Método cargarEntidadesBase Método que sincroniza a la Caja Registradora
	 * todas las entidades necesarias para el correcto funcionamiento del
	 * sistema.
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void cargarEntidadesBaseCR() throws SQLException,
			BaseDeDatosExcepcion {
		if (Sesion.isCajaEnLinea()) {
			String mensajeError = new String(
					"Falla carga de data inicial Servidor --> CR");
			try {
				String mensaje = new String(
						"Cargando data inicial de entidades base: Servidor --> CR");
				Auditoria.registrarAuditoria(mensaje, 'O');

				// Entidades relativas al sistema
				syncEntidadSrv("caja");
				syncEntidadCR("modulos");
				syncEntidadCR("funcion");
				syncEntidadCR("metodos");
				syncEntidadCR("perfil");
				syncEntidadCR("estadodecaja");
				syncEntidadCR("funcionmetodos");
				syncEntidadCR("maquinadeestado");
				syncEntidadCR("funcionperfil");
				syncEntidadCR("banco");
				syncEntidadCR("cargo");
				syncEntidadCR("condicionventa");
				syncEntidadCR("departamento");
				syncEntidadCR("formadepago");
				syncEntidadCR("region");
				syncEntidadCR("impuestoregion");
				syncEntidadCR("lineaseccion");
				syncEntidadCR("tienda");
				syncEntidadCR("tiendapublicidad");
				syncEntidadCR("cambiodeldia");
				syncEntidadCR("atcm23");
				syncEntidadCR("atcm24");
				syncEntidadCR("atcm25");

				syncEntidadCR("servidortienda");
				syncEntidadCR("tipoeventolistaregalos");

				// Entidades complementarias
				syncEntidadCR("tipocaptura");
				syncEntidadCR("tiposervicio");
				syncEntidadCR("unidadventa");
				syncEntidadCR("usuario");

				// Nuevo Crm llamada a funcion para sincronizar dicha datos a CR
				BeansSincronizador.syncEntidadCR("solicitarcliente");

				// Entidades necesarias para facturacion
				char cargarProductos = InitCR.preferenciasCR
						.getConfigStringForParameter("db",
								"garantizarFacturacion").toUpperCase()
						.charAt(0);
				if (cargarProductos == Sesion.SI) {
					syncEntidadCR("producto");
					syncEntidadCR("prodcodigoexterno");
					syncEntidadCR("promocion");
					syncEntidadCR("detallepromocion");

					// *** 11/11/2008 IROJAS. Agregada sincronización de
					// promociones adiconales para BECO
					syncEntidadCR("promocionesextendidas");
					// ***
				}

				// Entidades de Bono Regalo Electronico
				syncEntidadCR("br_opcionhabilitada");
				syncEntidadCR("br_condiciones");
				syncEntidadCR("br_promocion");

				mensaje = new String(
						"Terminada carga de data inicial Servidor --> CR");
				Sesion.cargarCondicionesDeVenta();
				Sesion.cargarTiposDeCaptura();
				Auditoria.registrarAuditoria(mensaje, 'O');
			} catch (BaseDeDatosExcepcion e1) {
				logger.error("cargarEntidadesBaseCR()", e1);
				Auditoria.registrarAuditoria(mensajeError, 'E');
				MensajesVentanas.mensajeError(mensajeError);
			} catch (Exception e) {
				Auditoria.registrarAuditoria(mensajeError, 'E');
				MensajesVentanas.mensajeError(mensajeError + "\n"
						+ e.getMessage());
			}
		}
	}

	public static boolean isSincronizadoCodExt() {
		SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd000000");
		String sentenciaSql = "select count(*) as existe from planificador where "
				+ "entidad = 'prodcodigoexterno' and fallasincronizador = 'N' and actualizacion > '"
				+ formato.format(new java.util.Date()) + "'";
		boolean result = false;
		try {
			ResultSet rs = Conexiones.realizarConsulta(sentenciaSql, true,
					false, true);
			if (rs != null) {
				try {
					if (rs.getInt("existe") > 0)
						result = true;
				} finally {
					rs.close();
				}
			}
		} catch (BaseDeDatosExcepcion e) {
		} catch (ConexionExcepcion e) {
		} catch (SQLException e) {
		}
		return result;
	};

	/**
	 * Método actualizarEntidadesSistema Método que sincroniza a la Caja
	 * Registradora todas las entidades variantes tanto en el Servidor como en
	 * la CR que son fundamentales para el adecuado funcionamiento del sistema.
	 * 
	 * @param BDDestinoLocal
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void actualizarEntidadesSistema(boolean BDDestinoLocal)
			throws SQLException, BaseDeDatosExcepcion {
		if (Sesion.isCajaEnLinea()) {
			if (BDDestinoLocal) {
				syncEntidadCR("modulos");
				syncEntidadCR("funcion");
				syncEntidadCR("metodos");
				syncEntidadCR("funcionmetodos");
				syncEntidadCR("maquinadeestado");
				syncEntidadCR("perfil");
				syncEntidadCR("usuario");
				syncEntidadCR("funcionperfil");
				syncEntidadCR("atcm23");
				syncEntidadCR("atcm24");
				syncEntidadCR("atcm25");

				// Entidades para Servicio Lista de Regalos
				syncEntidadCR("servidortienda");
				syncEntidadCR("tipoeventolistaregalos");
				syncEntidadCR("tipoeventolistaregalos");
			} else {
				syncEntidadSrv("modulos");
				syncEntidadSrv("funcion");
				syncEntidadSrv("metodos");
				syncEntidadSrv("funcionmetodos");
				syncEntidadSrv("maquinadeestado");
				syncEntidadSrv("perfil");
				syncEntidadSrv("usuario");
				syncEntidadSrv("funcionperfil");
				syncEntidadSrv("atcm23");
				syncEntidadSrv("atcm24");
				syncEntidadSrv("atcm25");

				// Entidades para Servicio Lista de Regalos
				syncEntidadSrv("servidortienda");
				syncEntidadSrv("tipoeventolistaregalos");
			}
		}
	}

	/**
	 * Método actualizarConfiguracionAcceso Método que sincroniza a la Caja
	 * Registradora todas las entidades relativas al acceso al sistema.
	 * 
	 * @param BDDestinoLocal
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void actualizarConfiguracionAcceso(boolean BDDestinoLocal)
			throws SQLException, BaseDeDatosExcepcion {
		if (Sesion.isCajaEnLinea()) {
			if (BDDestinoLocal) {
				syncEntidadCR("perfil");
				syncEntidadCR("usuario");
				syncEntidadCR("funcionperfil");
			} else {
				syncEntidadSrv("perfil");
				syncEntidadSrv("usuario");
				syncEntidadSrv("funcionperfil");
			}
		}
	}

	/**
	 * Método actualizarPlanificador Método que sincroniza a la Caja
	 * Registradora todas las entidades variantes tanto en el Servidor como en
	 * la CR que son fundamentales para el adecuado funcionamiento del sistema.
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void actualizarPlanificador(EntidadBD entidad, boolean falla,
			String destino) throws SQLException, BaseDeDatosExcepcion {
		Calendar fecha;
		fecha = Calendar.getInstance();
		Timestamp actualizacion = new Timestamp(fecha.getTime().getTime());
		BeansSincronizador.actualizarPlanificador(entidad, falla, destino,
				actualizacion);
	}

	/**
	 * Método actualizarPlanificador Método que sincroniza a la Caja
	 * Registradora todas las entidades variantes tanto en el Servidor como en
	 * la CR que son fundamentales para el adecuado funcionamiento del sistema.
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void actualizarPlanificador(EntidadBD entidad, boolean falla,
			String destino, Timestamp actualizacion) throws SQLException,
			BaseDeDatosExcepcion {
		String sentenciaSql = new String("");
		ResultSet consulta;
		try {
			sentenciaSql = new String("select count(*) as existe from "
					+ entidad.getEsquema() + ".planificador where numtienda="
					+ Sesion.getTienda().getNumero() + " and numcaja="
					+ Sesion.getCaja().getNumero() + " and entidad='"
					+ entidad.getNombre().toLowerCase() + "' and destino = '"
					+ destino + "'");
			consulta = Conexiones.realizarConsulta(sentenciaSql, true, false,
					true);
			try {
				if (consulta.getInt("existe") > 0)
					if (falla) {

						// Recuperamos la fecha de actualización del
						// planificador de la caja
						sentenciaSql = new String("select actualizacion from "
								+ entidad.getEsquema()
								+ ".planificador where numtienda="
								+ Sesion.getTienda().getNumero()
								+ " and numcaja="
								+ Sesion.getCaja().getNumero()
								+ " and entidad='"
								+ entidad.getNombre().toLowerCase()
								+ "' and destino = '" + destino + "'");
						consulta = Conexiones.realizarConsulta(sentenciaSql,
								true, false, true);
						consulta.first();
						Timestamp ultimaFechaSync = consulta
								.getTimestamp("actualizacion");
						SimpleDateFormat formato = new SimpleDateFormat(
								"yyyyMMddHHmmss");
						sentenciaSql = new String("update "
								+ entidad.getEsquema()
								+ ".planificador set actualizacion = '"
								+ formato.format(ultimaFechaSync)
								+ "', fallasincronizador='" + Sesion.SI
								+ "' where numtienda="
								+ Sesion.getTienda().getNumero()
								+ " and numcaja="
								+ Sesion.getCaja().getNumero()
								+ " and entidad='"
								+ entidad.getNombre().toLowerCase()
								+ "' and destino = '" + destino + "'");
					} else
						sentenciaSql = new String("update "
								+ entidad.getEsquema()
								+ ".planificador set actualizacion='"
								+ actualizacion
								+ "', fallasincronizador='N' where numtienda="
								+ Sesion.getTienda().getNumero()
								+ " and numcaja="
								+ Sesion.getCaja().getNumero()
								+ " and entidad='"
								+ entidad.getNombre().toLowerCase()
								+ "' and destino = '" + destino + "'");
				else if (falla)
					sentenciaSql = new String(
							"insert "
									+ entidad.getEsquema()
									+ ".planificador "
									+ "set numtienda="
									+ Sesion.getTienda().getNumero()
									+ ", numcaja="
									+ Sesion.getCaja().getNumero()
									+ ", entidad='"
									+ entidad.getNombre().toLowerCase()
									+ "', actualizacion='19000101000000', fallasincronizador='"
									+ Sesion.SI + "', destino = '" + destino
									+ "'");
				else
					sentenciaSql = new String("insert " + entidad.getEsquema()
							+ ".planificador " + "set numtienda="
							+ Sesion.getTienda().getNumero() + ", numcaja="
							+ Sesion.getCaja().getNumero() + ", entidad='"
							+ entidad.getNombre().toLowerCase()
							+ "', actualizacion='" + actualizacion
							+ "', fallasincronizador='N', destino = '"
							+ destino + "'");
			} finally {
				consulta.close();
			}
			Conexiones.realizarSentencia(sentenciaSql, true, true);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("actualizarPlanificador(EntidadBD, boolean, String)",
					e);
		} catch (ConexionExcepcion e) {
			logger.error("actualizarPlanificador(EntidadBD, boolean, String)",
					e);
		}
	}

	/**
	 * 
	 *
	 */
	@SuppressWarnings("unchecked")
	public static synchronized void syncClientesNoRegistrados() {
		// Solo actualizo los afiliados con registrados = 'C' y con destino a
		// 'S'ervidor
		// Mantengo la actualización via Hibernate, de manera momentánea
		Session sessionCR = null, sessionSRV = null;
		String[] clave = { "codafiliado" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "afiliado",
				clave, true);
		String entidadBD = new String(
				"com.becoblohm.cr.sincronizador.hibernate."
						+ entidad.getNombre().toUpperCase().charAt(0)
						+ entidad.getNombre().substring(1));
		Query consulta;
		int numMaxRegistros = 1000;
		int vanCuantos = 0;
		Transaction tx = null, txLocal = null;
		if (logger.isInfoEnabled()) {
			logger.info("syncClientesNoRegistrados() - Verificando entidad -> "
					+ entidad.getEsquema() + "." + entidad.getNombre());
		}
		Connection conLocal = null, conSrv = null;

		if (Sesion.isCajaEnLinea()) {
			try {
				conLocal = Conexiones.getConexion(true, true);
				conLocal.setAutoCommit(false);
				conSrv = Conexiones.getConexion(false, true);
				conSrv.setAutoCommit(false);
				sessionCR = ConexionHibernate.currentSession(true, conLocal);
				sessionSRV = ConexionHibernate.currentSession(false, conSrv);
				tx = sessionSRV.beginTransaction();
				txLocal = sessionCR.beginTransaction();
				String sentenciaSQL = "select {" + entidad.getNombre()
						+ ".*} from " + entidad.getEsquema() + "."
						+ entidad.getNombre() + " " + entidad.getNombre()
						+ " where registrado = '" + Sesion.CLIENTE_REGISTRADO
						+ "'";
				String xSentenciaSql = sql2CountSql(sentenciaSQL);
				consulta = sessionCR.createSQLQuery(sentenciaSQL,
						entidad.getNombre(), Class.forName(entidadBD));
				consulta.setMaxResults(numMaxRegistros);
				consulta.setFirstResult(vanCuantos);
				Connection conexion = sessionCR.connection();
				Statement sentencia = conexion.createStatement();
				int totalRegistros = 0;
				try {
					ResultSet resultado = sentencia.executeQuery(xSentenciaSql);
					try {
						resultado.next();
						totalRegistros = resultado.getInt("cuantosregistros");
					} finally {
						resultado.close();
						resultado = null;
					}
				} finally {
					sentencia.close();
					sentencia = null;
				}

				while (vanCuantos < totalRegistros) {
					List<Afiliado> registros = consulta.list();
					vanCuantos = vanCuantos + numMaxRegistros;
					consulta.setFirstResult(vanCuantos);
					for (int i = 0; i < registros.size(); i++) {
						Afiliado registro = (Afiliado) registros.get(i);
						registro.setRegistrado(Character.toString(Sesion.SI));
						try {
							sessionSRV.replicate(registro,
									ReplicationMode.LATEST_VERSION);
							sessionCR.update(registro);
						} catch (HibernateException e) {
							Auditoria.registrarAuditoria(
									"Error al sincronizar entidad: "
											+ registro.toString(), 'E');
							logger.error("syncClientesNoRegistrados() - Error al sincronizar entidad: "
									+ registro.toString());
						}
					}
					tx.commit();
					txLocal.commit();
					sessionCR.clear();
					sessionSRV.clear();
				}
				tx.commit();
				txLocal.commit();
				sessionCR.clear();
				sessionSRV.clear();
				actualizarPlanificador(entidad, false, "S");

			} catch (HibernateException e) {
				if (tx != null) {
					try {
						tx.rollback();
						txLocal.rollback();
						sessionCR.clear();
						sessionSRV.clear();
					} catch (HibernateException ex) {
						logger.error("syncClientesNoRegistrados()", ex);
					}
				}
				logger.error("syncClientesNoRegistrados()", e);
			} catch (ClassNotFoundException e) {
				logger.error("syncClientesNoRegistrados()", e);
			} catch (SQLException e) {
				logger.error("syncClientesNoRegistrados()", e);
			} catch (BaseDeDatosExcepcion e) {
				logger.error("syncClientesNoRegistrados()", e);
			} catch (ConexionExcepcion e) {
				logger.error("syncClientesNoRegistrados()", e);
			} finally {
				try {
					ConexionHibernate.closeSession(true);
					ConexionHibernate.closeSession(false);
				} catch (HibernateException e3) {
					logger.error("syncClientesNoRegistrados()", e3);
				}
				try {
					if (conLocal != null)
						conLocal.setAutoCommit(true);
				} catch (SQLException e1) {
					logger.error("syncClientesNoRegistrados()", e1);
				}
				try {
					if (conSrv != null)
						conSrv.setAutoCommit(true);
				} catch (SQLException e2) {
					logger.error(
							"syncEntidadMaestra(EntidadBD, boolean, boolean)",
							e2);
				}
			}
		}
	}

	/**
	 * Método syncBROpcionHabilitada
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncBROpcionHabilitada(boolean BDOrigen)
			throws SQLException, BaseDeDatosExcepcion {
		String[] clave = { "codmetodo", "nombreopcion" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"br_opcionhabilitada", clave, false, false);
		BeansSincronizador.syncEntidad(entidad, BDOrigen, true);
	}

	/**
	 * Método syncBRCondiciones
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncBRCondiciones(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		String[] clave = { "orden" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"br_condiciones", clave, false, false);
		BeansSincronizador.syncEntidad(entidad, BDOrigen, true);
	}

	/**
	 * Método syncBRPromocion
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncBRPromocion(boolean BDOrigen) throws SQLException,
			BaseDeDatosExcepcion {
		try {
			String[] clave = { "fechainicia", "tipo" };
			EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
					"br_promocion", clave, false, false);
			BeansSincronizador.syncEntidad(entidad, BDOrigen, true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * @param b
	 */
	private static void syncTipoEventoListaRegalos(boolean BDOrigen) {
		String[] clave = { "codtipoevento" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"tipoeventolistaregalos", clave, true);
		try {
			BeansSincronizador.syncEntidad(entidad, BDOrigen);
		} catch (Exception e) {
			logger.error(
					"syncEntidad(EntidadBD, boolean, boolean) - Error en sincronizando la tabla: "
							+ entidad.getNombre() + " en  T# "
							+ Sesion.getTienda().getNumero() + " - Caja "
							+ Sesion.getCaja().getNumero(), e);
		}
	}

	/**
	 * @param b
	 */
	private static void syncServidorTienda(boolean BDOrigen) {
		String[] clave = { "numtienda" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"servidortienda", clave, true);
		try {
			BeansSincronizador.syncEntidad(entidad, BDOrigen);
		} catch (Exception e) {
			logger.error(
					"syncEntidad(EntidadBD, boolean, boolean) - Error en sincronizando la tabla: "
							+ entidad.getNombre() + " en  T# "
							+ Sesion.getTienda().getNumero() + " - Caja "
							+ Sesion.getCaja().getNumero(), e);
		}
	}

	public static String sql2CountSql(String sql) {
		// Recibe un SQL preparado para Hibernate y retorna
		// un sql de tipo count
		// Se debe sustituir "{xxxx}" por "count(*) as cuantosregistros"
		StringBuffer buffer = new StringBuffer(sql);
		int inicio = buffer.indexOf("{");
		int fin = buffer.indexOf("}");
		buffer.replace(inicio, fin + 1, "count(*) as cuantosregistros");
		return buffer.toString();
	}

	public static void sincronizarCliente(ResultSet datosCliente)
			throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("sincronizarCliente(ResultSet) - start");
		}

		String sql = "insert into afiliado (codafiliado, tipoafiliado, nombre, "
				+ "apellido, numtienda, numficha, coddepartamento, codcargo, "
				+ "nitcliente, direccion, direccionfiscal, codarea, numtelefono, "
				+ "fechaafiliacion, exentoimpuesto, registrado, contactar, codregion, "
				+ "estadoafiliado, estadocolaborador, actualizacion) values (?, ?, "
				+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'N', 'N', '***', ?, ?, ?)";
		try {
			PreparedStatement stmt = Conexiones.getConexion(true, true)
					.prepareStatement(sql);
			stmt.setString(1, datosCliente.getString("codafiliado"));
			stmt.setString(2, datosCliente.getString("tipoafiliado"));
			stmt.setString(3, datosCliente.getString("nombre"));
			stmt.setString(4, datosCliente.getString("apellido"));
			stmt.setShort(5, datosCliente.getShort("numtienda"));
			stmt.setString(6, datosCliente.getString("numficha"));
			stmt.setString(7, datosCliente.getString("coddepartamento"));
			stmt.setString(8, datosCliente.getString("codcargo"));
			stmt.setString(9, datosCliente.getString("nitcliente"));
			stmt.setString(10, datosCliente.getString("direccion"));
			stmt.setString(11, datosCliente.getString("direccion"));
			stmt.setString(12, datosCliente.getString("codarea"));
			stmt.setString(13, datosCliente.getString("numtelefono"));
			stmt.setDate(14, datosCliente.getDate("fechaafiliacion"));
			stmt.setString(15, datosCliente.getString("exentoimpuesto"));
			stmt.setString(16, datosCliente.getString("estadoafiliado"));
			stmt.setString(17, datosCliente.getString("estadocolaborador"));
			stmt.setTimestamp(18,
					new Timestamp(datosCliente.getDate("fechaafiliacion")
							.getTime()));
			stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error("sincronizarCliente(ResultSet)", e);
		} finally {

		}

		if (logger.isDebugEnabled()) {
			logger.debug("sincronizarCliente(ResultSet) - end");
		}
	}

	/**
	 * Método cargarEntidadesBase Método que sincroniza a la Caja Registradora
	 * todas las entidades necesarias para el correcto funcionamiento del
	 * sistema.
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void cargarEntidadesConfiguracion() throws SQLException,
			BaseDeDatosExcepcion {
		if (Sesion.isCajaEnLinea()) {
			String mensajeError = new String(
					"Falla carga de data de Configuración");
			try {
				String mensaje = new String("Cargando data de Configuración");
				Auditoria.registrarAuditoria(mensaje, 'O');

				// Entidades relativas al sistema
				try {
					syncEntidadSrv("caja");
				} catch (Exception e1) {
				}
				try {
					syncEntidadCR("modulos");
				} catch (Exception e1) {
				}
				try {
					syncEntidadCR("funcion");
				} catch (Exception e1) {
				}
				try {
					syncEntidadCR("metodos");
				} catch (Exception e1) {
				}
				try {
					syncEntidadCR("funcionmetodos");
				} catch (Exception e1) {
				}
				try {
					syncEntidadCR("estadodecaja");
				} catch (Exception e1) {
				}
				try {
					syncEntidadCR("maquinadeestado");
				} catch (Exception e1) {
				}
				try {
					syncEntidadCR("perfil");
				} catch (Exception e1) {
				}
				try {
					syncEntidadCR("usuario");
				} catch (Exception e1) {
				}
				try {
					syncEntidadCR("funcionperfil");
				} catch (Exception e1) {
				}

				// Entidades relativas al negocio
				try {
					syncEntidadCR("region");
				} catch (Exception e1) {
				}
				try {
					syncEntidadCR("tienda");
				} catch (Exception e1) {
				}
				try {
					syncEntidadCR("tiendapublicidad");
				} catch (Exception e1) {
				}
				try {
					syncEntidadCR("usuario");
				} catch (Exception e1) {
				}
				try {
					syncEntidadCR("cargo");
				} catch (Exception e1) {
				}

				mensaje = new String("Terminada carga de data de Configuración");
				Auditoria.registrarAuditoria(mensaje, 'O');
				/*
				 * } catch (BaseDeDatosExcepcion e1) {
				 * logger.error("cargarEntidadesBaseCR()", e1);
				 * Auditoria.registrarAuditoria(mensajeError, 'E');
				 * MensajesVentanas.mensajeError(mensajeError);
				 */} catch (Exception e) {
				Auditoria.registrarAuditoria(mensajeError, 'E');
				MensajesVentanas.mensajeError(mensajeError + "\n"
						+ e.getMessage());
			}
		}
	}

	/**
	 * Método sincronizarTotalTablas Sincroniza el total de la tabla indicada
	 * del Servidor a la caja
	 * 
	 * @param BDOrigen
	 *            - Indicador de la entidad donde estan los datos a sincronizar.
	 *            Verdadero si es la Caja Registradora y falso si estan en el
	 *            Servidor.
	 * @param tabla
	 *            - Nombre de la tabla del esquema que se desea sincronizar
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	/*
	 * En esta función se realizaron modificaciones referentes a la migración a
	 * java 1.6 por jperez Sólo se parametrizó el tipo de dato de los distintos
	 * ArrayList Fecha: agosto 2011
	 */
	public static void sincronizarTotalTablas(String tabla)
			throws SQLException, BaseDeDatosExcepcion {
		ResultSet filas = null;
		ArrayList<ArrayList<Object>> registros;
		ArrayList<Object> registro;
		String sentenciaSql;
		String[] xLoteSentencias;
		StringBuffer xCampos, xValores;

		try {
			Conexiones
					.realizarSentencia("SET FOREIGN_KEY_CHECKS=0", true, true);
			String sentencia = "SELECT * FROM " + Sesion.getDbEsquema() + "."
					+ tabla.trim();
			filas = Conexiones.realizarConsulta(sentencia, false, false, true);

			filas.beforeFirst();
			while (filas.next()) {
				int numColumnas = 0;
				xCampos = new StringBuffer("");
				xValores = new StringBuffer("");
				numColumnas = filas.getMetaData().getColumnCount();
				for (int i = 0; i < numColumnas; i++) {
					xCampos.append(filas.getMetaData().getColumnName(i + 1));
					xValores.append("?");
					if (i + 1 < numColumnas) {
						xCampos.append(", ");
						xValores.append(", ");
					}
				}
				sentenciaSql = new String("replace into "
						+ Sesion.getDbEsquema() + "." + tabla.trim() + " ("
						+ xCampos + ") values (" + xValores + ")");
				registros = new ArrayList<ArrayList<Object>>();
				registro = new ArrayList<Object>();
				for (int i = 0; i < numColumnas; i++)
					registro.add(filas.getObject(filas.getMetaData()
							.getColumnName(i + 1).toLowerCase()));
				registros.add(registro);

				xLoteSentencias = Conexiones.crearLoteSentencias(
						filas.getMetaData(), sentenciaSql, registros, true);
				try {
					Conexiones
							.realizarSentencia(xLoteSentencias[0], true, true);
				} catch (Exception e) {
					logger.error("sincronizarTotalTablas(String)", e);
				}
			}
			Conexiones
					.realizarSentencia("SET FOREIGN_KEY_CHECKS=1", true, true);
		} catch (SQLException e) {
			try {
				Conexiones.realizarSentencia("SET FOREIGN_KEY_CHECKS=1", true,
						true);
			} catch (Exception ex) {
			}
			throw (new BaseDeDatosExcepcion(
					"Error en sentencia SQL al sincronizar " + tabla));
		} catch (BaseDeDatosExcepcion e) {
			try {
				Conexiones.realizarSentencia("SET FOREIGN_KEY_CHECKS=1", true,
						true);
			} catch (Exception ex) {
			}
			throw (new BaseDeDatosExcepcion("Error con la BD al sincronizar "
					+ tabla));
		} catch (ConexionExcepcion e) {
			try {
				Conexiones.realizarSentencia("SET FOREIGN_KEY_CHECKS=1", true,
						true);
			} catch (Exception ex) {
			}
			throw (new BaseDeDatosExcepcion(
					"Error de conexión con BD al sincronizar " + tabla));
		} finally {
			filas.close();
		}
	}

	// NUEVO: Subir todos los Afiliados Asociados a Transacciones
	public static void cargarAfiliadosTemporales() {

		ResultSet afiliadosATransferir = null;
		String sentenciaSQL = "SELECT * FROM afiliado AS a INNER JOIN transaccion AS t ON (a.codafiliado = t.codcliente)"
				+ "	WHERE t.fecha = CURRENT_DATE GROUP by a.codafiliado";
		try {
			afiliadosATransferir = Conexiones.realizarConsulta(sentenciaSQL,
					true);
		} catch (Exception e) {
		}

		try {
			afiliadosATransferir.beforeFirst();
			while (afiliadosATransferir.next()) {

				String apellido = (afiliadosATransferir.getString("apellido") == null) ? "null"
						: "'" + afiliadosATransferir.getString("apellido")
								+ "'";
				String numFicha = (afiliadosATransferir.getString("numficha") == null) ? "null"
						: "'" + afiliadosATransferir.getString("numficha")
								+ "'";
				String codDep = (afiliadosATransferir
						.getString("coddepartamento") == null) ? "null" : "'"
						+ afiliadosATransferir.getString("coddepartamento")
						+ "'";
				String codCargo = (afiliadosATransferir.getString("codcargo") == null) ? "null"
						: "'" + afiliadosATransferir.getString("codcargo")
								+ "'";
				String nitCliente = (afiliadosATransferir
						.getString("nitcliente") == null) ? "null" : "'"
						+ afiliadosATransferir.getString("nitcliente") + "'";
				String dirFiscal = (afiliadosATransferir
						.getString("direccionfiscal") == null) ? "null" : "'"
						+ afiliadosATransferir.getString("direccionfiscal")
						+ "'";
				String email = (afiliadosATransferir.getString("email") == null) ? "null"
						: "'" + afiliadosATransferir.getString("email") + "'";
				String codArea = (afiliadosATransferir.getString("codarea") == null) ? "null"
						: "'" + afiliadosATransferir.getString("codarea") + "'";
				String numTlf = (afiliadosATransferir.getString("numtelefono") == null) ? "null"
						: "'" + afiliadosATransferir.getString("numtelefono")
								+ "'";
				String codArea1 = (afiliadosATransferir.getString("codarea1") == null) ? "null"
						: "'" + afiliadosATransferir.getString("codarea1")
								+ "'";
				String numTlf1 = (afiliadosATransferir
						.getString("numtelefono1") == null) ? "null" : "'"
						+ afiliadosATransferir.getString("numtelefono1") + "'";
				String fechanacimiento = (afiliadosATransferir
						.getDate("fechanacimiento") == null) ? "null" : "'"
						+ afiliadosATransferir.getDate("fechanacimiento") + "'";

				String sentenciaSQL2 = "REPLACE INTO afiliado (codafiliado, tipoafiliado, nombre, apellido, numtienda, numficha, "
						+ "coddepartamento, codcargo, nitcliente, direccion, direccionfiscal, email, codarea, numtelefono, codarea1, numtelefono1, "
						+ "fechaafiliacion, exentoimpuesto, registrado, contactar, codregion, estadoafiliado, estadocolaborador, genero, estadocivil, fechanacimiento) values ('"
						+ afiliadosATransferir.getString("codafiliado")
						+ "','"
						+ afiliadosATransferir.getString("tipoafiliado")
						+ "','"
						+ afiliadosATransferir.getString("nombre")
						+ "',"
						+ apellido
						+ ","
						+ afiliadosATransferir.getInt("numtienda")
						+ ","
						+ numFicha
						+ ","
						+ codDep
						+ ","
						+ codCargo
						+ ","
						+ nitCliente
						+ ",'"
						+ afiliadosATransferir.getString("direccion")
						+ "',"
						+ dirFiscal
						+ ","
						+ email
						+ ","
						+ codArea
						+ ","
						+ numTlf
						+ ","
						+ codArea1
						+ ","
						+ numTlf1
						+ ",'"
						+ afiliadosATransferir.getString("fechaafiliacion")
						+ "','"
						+ afiliadosATransferir.getString("exentoimpuesto")
						+ "','"
						+ afiliadosATransferir.getString("registrado")
						+ "','"
						+ afiliadosATransferir.getString("contactar")
						+ "','"
						+ afiliadosATransferir.getString("codregion")
						+ "','"
						+ afiliadosATransferir.getString("estadoafiliado")
						+ "','"
						+ afiliadosATransferir.getString("estadocolaborador")
						+ "','"
						+ afiliadosATransferir.getString("genero")
						+ "','"
						+ afiliadosATransferir.getString("estadocivil")
						+ "'," + fechanacimiento + ")";
				try {
					Conexiones.realizarSentencia(sentenciaSQL2, false);
				} catch (Exception e) {
				} catch (Throwable e1) {
				}
			}
		} catch (Exception e) {
		} finally {
			try {
				afiliadosATransferir.close();
			} catch (Exception e) {
			}
			afiliadosATransferir = null;
		}
	}

	/**
	 * 11/11/2008 IROJAS. AGREGADO MÉTODO PARA PROYECTO DE PROMOCIONES Método
	 * sincronizarPromocionesExtendidas Sincroniza el total de las tablas
	 * relacionadas con las promcoiones adiconales en CR de BECO desde el
	 * servidor de Tda hacia las cajas.
	 * 
	 * @param
	 * @param
	 * @throws BaseDeDatosExcepcion
	 */

	public static void sincronizarPromocionesExtendidas() {
		Sesion.chequearLineaCaja();
		if (Sesion.isCajaEnLinea()) {
			String[] tablas = { "CR.detallepromocionext.txt.gz",
					"CR.productoparacombo.txt.gz",
					"CR.participaencombo.txt.gz", "CR.prodseccion.txt.gz",
					"CR.donacion.txt.gz", "CR.condicionpromocion.txt.gz",
					"CR.catdep.txt.gz" };
			try {
				ObtenerDataFile.obtenerVariosDataFile(tablas);
			} catch (Exception e) {
				logger.error(
						"sincronizarPromocionesExtendidas() - Error en sincronizando las tablas de promociones extendidas en  T# "
								+ Sesion.getTienda().getNumero()
								+ " - Caja "
								+ Sesion.getCaja().getNumero(), e);
			}
		} else {
			logger.error("No se sincronizaron los archivos de Promociones Extendidas, Caja fuera de linea");
		}
	}

	/**
	 * Método getDonacionesRegistradas Selecciona todos los detalles de
	 * productos de la transacción indicada.
	 * 
	 * @param local
	 *            - Indicador de la conexión de BD a utilizar: Servidor o Local.
	 * @param transaccion
	 *            - Número de la transacción a sincronizar.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql
	 *         efectuada.
	 * @throws BaseDeDatosExcepcion
	 * @autor aavila Promociones 11/11/2008
	 */
	public static ResultSet getDonacionesRegistradas(boolean local,
			int transaccion) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		String sentenciaSql = new String(
				"select donacionesregistradas.* from transaccion inner join donacionesregistradas on (transaccion.numtienda = donacionesregistradas.numtienda) and (transaccion.numcajafinaliza = donacionesregistradas.numcaja) and "
						+ "(transaccion.numtransaccion = donacionesregistradas.numtransaccion) and (transaccion.fecha = donacionesregistradas.fechaDonacion) "
						+ "where (transaccion.regactualizado = 'N') and (transaccion.numtransaccion = "
						+ transaccion + ")");

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, true,
					true);
			resultado.beforeFirst();
		} catch (BaseDeDatosExcepcion e) {
			// throw (new
			// BaseDeDatosExcepcion("Error de BD al realizar sentencia " +
			// sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion(
					"Error de Conexión al realizar sentencia " + sentenciaSql,
					e));
		} catch (SQLException e) {
			try {
				resultado.close();
			} catch (SQLException e1) {
				logger.error("getDonacionesRegistradas(boolean, int)", e1);
			}
			throw (new BaseDeDatosExcepcion(
					"Error de SQL al realizar sentencia " + sentenciaSql, e));
		}
		return resultado;
	}

	/**
	 * Método getRegalosRegistrados Selecciona todos los detalles de productos
	 * de la transacción indicada.
	 * 
	 * @param local
	 *            - Indicador de la conexión de BD a utilizar: Servidor o Local.
	 * @param transaccion
	 *            - Número de la transacción a sincronizar.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql
	 *         efectuada.
	 * @throws BaseDeDatosExcepcion
	 * @autor aavila Promociones 11/11/2008
	 */
	public static ResultSet getRegalosRegistrados(boolean local, int transaccion)
			throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		String sentenciaSql = new String(
				"select regalosregistrados.* from transaccion inner join regalosregistrados on (transaccion.numtienda = regalosregistrados.numtienda) and (transaccion.numcajafinaliza = regalosregistrados.numcaja) and "
						+ "(transaccion.numtransaccion = regalosregistrados.numtransaccion) and (transaccion.fecha = regalosregistrados.fechatransaccion) "
						+ "where (transaccion.regactualizado = 'N') and (transaccion.numtransaccion = "
						+ transaccion + ")");

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, true,
					true);
			resultado.beforeFirst();
		} catch (BaseDeDatosExcepcion e) {
			// throw (new
			// BaseDeDatosExcepcion("Error de BD al realizar sentencia " +
			// sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion(
					"Error de Conexión al realizar sentencia " + sentenciaSql,
					e));
		} catch (SQLException e) {
			try {
				resultado.close();
			} catch (SQLException e1) {
				logger.error("getRegalosRegistrados(boolean, int)", e1);
			}
			throw (new BaseDeDatosExcepcion(
					"Error de SQL al realizar sentencia " + sentenciaSql, e));
		}
		return resultado;
	}

	/**
	 * Método getPromocionRegistrada Selecciona todos los detalles de productos
	 * de la transacción indicada.
	 * 
	 * @param local
	 *            - Indicador de la conexión de BD a utilizar: Servidor o Local.
	 * @param transaccion
	 *            - Número de la transacción a sincronizar.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql
	 *         efectuada.
	 * @throws BaseDeDatosExcepcion
	 * @autor aavila Promociones 11/11/2008
	 */
	public static ResultSet getPromocionRegistrada(boolean local,
			int transaccion) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		String sentenciaSql = new String(
				"select promocionregistrada.* from transaccion inner join promocionregistrada on (transaccion.numtienda = promocionregistrada.numtienda) and (transaccion.numcajafinaliza = promocionregistrada.numcaja) and "
						+ "(transaccion.numtransaccion = promocionregistrada.numtransacion) and (transaccion.fecha = promocionregistrada.fecha) "
						+ "where (transaccion.regactualizado = 'N') and (transaccion.numtransaccion = "
						+ transaccion + ")");

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, true,
					true);
			resultado.beforeFirst();
		} catch (BaseDeDatosExcepcion e) {
			// throw (new
			// BaseDeDatosExcepcion("Error de BD al realizar sentencia " +
			// sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion(
					"Error de Conexión al realizar sentencia " + sentenciaSql,
					e));
		} catch (SQLException e) {
			try {
				resultado.close();
			} catch (SQLException e1) {
				logger.error("getPromocionRegistrada(boolean, int)", e1);
			}
			throw (new BaseDeDatosExcepcion(
					"Error de SQL al realizar sentencia " + sentenciaSql, e));
		}
		return resultado;
	}

	/**
	 * Método getPagoDonacion Selecciona todos los detalles de productos de la
	 * transacción indicada.
	 * 
	 * @param local
	 *            - Indicador de la conexión de BD a utilizar: Servidor o Local.
	 * @param transaccion
	 *            - Número de la transacción a sincronizar.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql
	 *         efectuada.
	 * @throws BaseDeDatosExcepcion
	 * @autor aavila Promociones 11/11/2008
	 */
	public static ResultSet getPagoDonacion(boolean local, int transaccion)
			throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		String sentenciaSql = new String(
				"select pagodonacion.* from pagodonacion inner join donacionesregistradas on (donacionesregistradas.numtienda = pagodonacion.numtienda) and (donacionesregistradas.numcaja = pagodonacion.numcaja) and "
						+ "(donacionesregistradas.numtransaccion = pagodonacion.numtransaccion) and (donacionesregistradas.fechaDonacion = pagodonacion.fechaDonacion) and (donacionesregistradas.codigo=pagodonacion.codigoDonacionRegistrada) where (donacionesregistradas.regactualizado = 'N') and (donacionesregistradas.numtransaccion = "
						+ transaccion + ")");

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, true,
					true);
			resultado.beforeFirst();
		} catch (BaseDeDatosExcepcion e) {
			// throw (new
			// BaseDeDatosExcepcion("Error de BD al realizar sentencia " +
			// sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion(
					"Error de Conexión al realizar sentencia " + sentenciaSql,
					e));
		} catch (SQLException e) {
			try {
				resultado.close();
			} catch (SQLException e1) {
				logger.error("getPagoDonacion(boolean, int)", e1);
			}
			throw (new BaseDeDatosExcepcion(
					"Error de SQL al realizar sentencia " + sentenciaSql, e));
		}
		return resultado;
	}

	public static ResultSet getTransaccionPremiada(boolean local,
			int transaccion) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		String sentenciaSql = new String(
				"select transaccionpremiada.* from transaccion inner join transaccionpremiada on (transaccion.numtienda = transaccionpremiada.numtienda) and (transaccion.numcajafinaliza = transaccionpremiada.numcaja) and "
						+ "(transaccion.numtransaccion = transaccionpremiada.numtransaccion) and (transaccion.fecha = transaccionpremiada.fecha) "
						+ "where (transaccion.regactualizado = 'N') and (transaccion.numtransaccion = "
						+ transaccion + ")");

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, true,
					true);
			resultado.beforeFirst();
		} catch (BaseDeDatosExcepcion e) {
			// throw (new
			// BaseDeDatosExcepcion("Error de BD al realizar sentencia " +
			// sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion(
					"Error de Conexión al realizar sentencia " + sentenciaSql,
					e));
		} catch (SQLException e) {
			try {
				resultado.close();
			} catch (SQLException e1) {
				logger.error("getTransaccionPremiada(boolean, int)", e1);
			}
			throw (new BaseDeDatosExcepcion(
					"Error de SQL al realizar sentencia " + sentenciaSql, e));
		}
		return resultado;
	}

	public static ResultSet getDetalleTransaccionCondicion(boolean local,
			int transaccion) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		String sentenciaSql = new String(
				"SELECT detalletransaccioncondicion.* "
						+ "FROM detalletransaccion INNER JOIN detalletransaccioncondicion "
						+ "ON (detalletransaccion.numtienda = detalletransaccioncondicion.numtienda) "
						+ "AND (detalletransaccion.numcajainicia = detalletransaccioncondicion.numcajainicia) "
						+ "AND (detalletransaccion.numregcajainicia = detalletransaccioncondicion.numregcajainicia) "
						+ "AND (detalletransaccion.fecha = detalletransaccioncondicion.fecha) "
						+ "AND (detalletransaccion.codproducto = detalletransaccioncondicion.codproducto) "
						+ "AND (detalletransaccion.codcondicionventa = detalletransaccioncondicion.condicionventa) "
						+ "AND (detalletransaccion.correlativoitem = detalletransaccioncondicion.correlativoitem) "
						+ "WHERE (detalletransaccion.regactualizado = 'N' AND detalletransaccion.numtransaccion="
						+ transaccion + ")");
		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, true,
					true);
			resultado.beforeFirst();
		} catch (BaseDeDatosExcepcion e) {
			// throw (new
			// BaseDeDatosExcepcion("Error de BD al realizar sentencia " +
			// sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion(
					"Error de Conexión al realizar sentencia " + sentenciaSql,
					e));
		} catch (SQLException e) {
			try {
				resultado.close();
			} catch (SQLException e1) {
				logger.error("getDetalleTransaccionCondicion(boolean, int)", e1);
			}
			throw (new BaseDeDatosExcepcion(
					"Error de SQL al realizar sentencia " + sentenciaSql, e));
		}
		return resultado;
	}

	// Metodos nuevos para sincronizar la tabla transaccion afiliado de crm
	// Wdiaz
	public static void syncTransaccionafiliadocrm(boolean BDOrigen)
			throws SQLException, BaseDeDatosExcepcion {
		String[] clave = { "numtienda", "fechatransaccion", "numcajafinaliza",
				"numtransaccion", "codafiliado" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"transaccionafiliadocrm", clave, false, true);
		BeansSincronizador.syncEntidadMaestra(entidad, BDOrigen);
	}

	public static void syncsolicitarcliente(boolean BDOrigen)
			throws SQLException, BaseDeDatosExcepcion {
		String[] clave = { "numtienda", "actualizacion" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"solicitudcliente", clave, true, false);
		BeansSincronizador.syncEntidad(entidad, BDOrigen);

		// Solicitar Cliente CRM
		boolean resp = false;
		try {
			resp = FechaActualPC();
		} catch (BaseDeDatosExcepcion e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (resp)
			Sesion.Pedir_Datos_Cliente = Sesion.NO;
		// fin

	}

	public static boolean FechaActualPC() throws SQLException,
			BaseDeDatosExcepcion {
		ResultSet fila = null;
		Calendar fecha = Calendar.getInstance();
		Timestamp fechaSrv = new Timestamp(fecha.getTime().getTime());
		String sentencia = "SELECT * FROM CR.solicitudcliente ORDER BY actualizacion DESC limit 1";
		try {
			fila = Conexiones.realizarConsulta(sentencia, true, false, false);
			fila.beforeFirst();
			if (fila.next()) {
				Timestamp fechaInicio = fila.getTimestamp("fechainicio");
				Timestamp fechaFinal = fila.getTimestamp("fechafinal");
				if ((fechaSrv.after(fechaInicio))
						&& (fechaSrv.before(fechaFinal)))
					return true;
				else
					return false;
			}
		} catch (ConexionExcepcion e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	// fin del o los metodos

	/**
	 * Método getTransaccionesBR Selecciona todos los registros de las
	 * transacciones realizadas por Bono Regalo.
	 * 
	 * @param local
	 *            - Indicador de la conexión de BD a utilizar: Servidor o Local.
	 * @param noSincronizadas
	 *            - Indicador para seleccionar todas las transacciones o sólo
	 *            las no sincronizadas.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql
	 *         efectuada.
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getTransaccionesBR(boolean local,
			boolean noSincronizadas) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		String sentenciaSql = "";
		if (noSincronizadas) {
			sentenciaSql = new String(
					"select br_transaccion.numtienda, br_transaccion.fecha, br_transaccion.numcaja, br_transaccion.numtransaccion "
							+ "from br_transaccion where br_transaccion.regactualizado = '"
							+ Sesion.NO + "'");
		} else {
			sentenciaSql = new String(
					"select br_transaccion.numtienda, br_transaccion.fecha, br_transaccion.numcaja, br_transaccion.numtransaccion "
							+ "from br_transaccion");
		}

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, true,
					true);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("getTransacciones(boolean, boolean)", e);
			throw (new BaseDeDatosExcepcion("Error al realizar sentencia "
					+ sentenciaSql, e));
		} catch (ConexionExcepcion e) {
		}
		return resultado;
	}

	/**
	 * Método syncTransaccionesBR Sincroniza las transacciones (cabecera,
	 * detalle de productos y pagos) de Bonos Regalo Electrónico desde la Caja
	 * Registradora hacia el Servidor de tienda.
	 * 
	 * @param numTransacciones
	 *            - Lista de los numeros correspondientes a las transacciones de
	 *            Bonos Regalo de la caja que no han sido actualizadas.
	 * @throws BaseDeDatosExcepcion
	 */
	/*
	 * En esta función se realizaron modificaciones referentes a la migración a
	 * java 1.6 por jperez Sólo se parametrizó el tipo de dato de los distintos
	 * ArrayList y HashMap Fecha: agosto 2011
	 */
	public static synchronized void syncTransaccionesBR(
			ResultSet numTransaccionesBR) throws BaseDeDatosExcepcion {
		ResultSet detalleProductos, detallePagos, detallesComprobantes, transaccionBR;
		ArrayList<ArrayList<Object>> registros;
		ArrayList<Object> registro;
		String sentenciaSql;
		String[] xLoteSentencias;
		Statement loteSentenciasSrv = null;
		Statement loteSentenciasCR = null;
		StringBuffer xCampos, xValores;
		int numTienda = 0, numCaja = 0, numTransaccion = 0;
		Date fecha = null;

		if (logger.isInfoEnabled()) {
			logger.info("Comienza sincronizacion de transacciones de Bonos Regalo");
		}
		try {
			loteSentenciasSrv = Conexiones.crearSentencia(false, true);
			loteSentenciasCR = Conexiones.crearSentencia(true, true);
			HashMap<String, Object> criterioClave = new HashMap<String, Object>();

			while (numTransaccionesBR.next()) {
				numTienda = numTransaccionesBR
						.getInt("br_transaccion.numtienda");
				fecha = numTransaccionesBR.getDate("br_transaccion.fecha");
				numCaja = numTransaccionesBR.getInt("br_transaccion.numcaja");
				numTransaccion = numTransaccionesBR
						.getInt("br_transaccion.numtransaccion");

				// Obtengo información de la CR de la transacción de Bonos
				// Regalo actual
				int numColumnas = 0;
				transaccionBR = getTransaccionBR(true, numTienda, fecha,
						numCaja, numTransaccion);
				try {
					xCampos = new StringBuffer("");
					xValores = new StringBuffer("");
					numColumnas = transaccionBR.getMetaData().getColumnCount();
					for (int i = 0; i < numColumnas; i++) {
						xCampos.append(transaccionBR.getMetaData()
								.getColumnName(i + 1));
						xValores.append("?");
						if (i + 1 < numColumnas) {
							xCampos.append(", ");
							xValores.append(", ");
						}
					}
					sentenciaSql = new String("replace into br_transaccion ("
							+ xCampos + ") values (" + xValores + ")");

					if (transaccionBR.first()) {
						registros = new ArrayList<ArrayList<Object>>();
						registro = new ArrayList<Object>();

						criterioClave.put("numTienda",
								transaccionBR.getObject("numtienda"));
						criterioClave.put("fecha",
								transaccionBR.getObject("fecha"));
						criterioClave.put("numCaja",
								transaccionBR.getObject("numcaja"));
						criterioClave.put("numTransaccion",
								transaccionBR.getObject("numtransaccion"));

						for (int i = 0; i < numColumnas; i++)
							registro.add(transaccionBR.getObject(transaccionBR
									.getMetaData().getColumnName(i + 1)
									.toLowerCase()));
						registros.add(registro);

						xLoteSentencias = Conexiones.crearLoteSentencias(
								transaccionBR.getMetaData(), sentenciaSql,
								registros, false, true);
						for (int i = 0; i < xLoteSentencias.length; i++) {
							loteSentenciasSrv.addBatch(xLoteSentencias[i]);
						}
					}
				} finally {
					transaccionBR.close();
				}

				// Obtengo el detalle de renglones de la transaccion de Bonos
				// Regalo
				registros = new ArrayList<ArrayList<Object>>();
				xCampos = new StringBuffer("");
				xValores = new StringBuffer("");
				detalleProductos = BeansSincronizador.getDetalleTransaccionBR(
						true, numTienda, fecha, numCaja, numTransaccion);
				try {
					numColumnas = detalleProductos.getMetaData()
							.getColumnCount();
					for (int i = 0; i < numColumnas; i++) {
						xCampos.append(detalleProductos.getMetaData()
								.getColumnName(i + 1));
						xValores.append("?");
						if (i + 1 < numColumnas) {
							xCampos.append(", ");
							xValores.append(", ");
						}
					}
					sentenciaSql = new String(
							"replace into br_detalletransaccion (" + xCampos
									+ ") values (" + xValores + ")");

					while (detalleProductos.next()) {
						registro = new ArrayList<Object>();
						for (int i = 0; i < numColumnas; i++)
							registro.add(detalleProductos
									.getObject(detalleProductos.getMetaData()
											.getColumnName(i + 1).toLowerCase()));
						registros.add(registro);
					}
					xLoteSentencias = Conexiones.crearLoteSentencias(
							detalleProductos.getMetaData(), sentenciaSql,
							registros, false, true);
					for (int i = 0; i < xLoteSentencias.length; i++) {
						loteSentenciasSrv.addBatch(xLoteSentencias[i]);
					}
				} finally {
					detalleProductos.close();
				}

				// Obtengo el detalle de renglones de los comprobantes fiscales
				// de Bonos Regalo
				registros = new ArrayList<ArrayList<Object>>();
				xCampos = new StringBuffer("");
				xValores = new StringBuffer("");
				detallesComprobantes = BeansSincronizador
						.getComprobantesFiscalesBR(true, numTienda, fecha,
								numCaja, numTransaccion);
				try {
					numColumnas = detallesComprobantes.getMetaData()
							.getColumnCount();
					for (int i = 0; i < numColumnas; i++) {
						xCampos.append(detallesComprobantes.getMetaData()
								.getColumnName(i + 1));
						xValores.append("?");
						if (i + 1 < numColumnas) {
							xCampos.append(", ");
							xValores.append(", ");
						}
					}
					sentenciaSql = new String(
							"replace into br_comprobantefiscal (" + xCampos
									+ ") values (" + xValores + ")");

					while (detallesComprobantes.next()) {
						registro = new ArrayList<Object>();
						for (int i = 0; i < numColumnas; i++)
							registro.add(detallesComprobantes
									.getObject(detallesComprobantes
											.getMetaData().getColumnName(i + 1)
											.toLowerCase()));
						registros.add(registro);
					}
					xLoteSentencias = Conexiones.crearLoteSentencias(
							detallesComprobantes.getMetaData(), sentenciaSql,
							registros, false, true);
					for (int i = 0; i < xLoteSentencias.length; i++) {
						loteSentenciasSrv.addBatch(xLoteSentencias[i]);
					}
				} finally {
					detallesComprobantes.close();
				}

				// Obtengo el detalle de pagos de la transacción
				registros = new ArrayList<ArrayList<Object>>();
				xCampos = new StringBuffer("");
				xValores = new StringBuffer("");
				detallePagos = BeansSincronizador.getPagoTransaccionBR(true,
						numTienda, fecha, numCaja, numTransaccion);
				try {
					numColumnas = detallePagos.getMetaData().getColumnCount();
					for (int i = 0; i < numColumnas; i++) {
						xCampos.append(detallePagos.getMetaData()
								.getColumnName(i + 1));
						xValores.append("?");
						if (i + 1 < numColumnas) {
							xCampos.append(", ");
							xValores.append(", ");
						}
					}
					sentenciaSql = new String(
							"replace into br_pagodetransaccion (" + xCampos
									+ ") values (" + xValores + ")");

					while (detallePagos.next()) {
						registro = new ArrayList<Object>();
						for (int i = 0; i < numColumnas; i++)
							registro.add(detallePagos.getObject(detallePagos
									.getMetaData().getColumnName(i + 1)
									.toLowerCase()));
						registros.add(registro);
					}

					xLoteSentencias = Conexiones.crearLoteSentencias(
							detallePagos.getMetaData(), sentenciaSql,
							registros, false, true);
					for (int i = 0; i < xLoteSentencias.length; i++) {
						loteSentenciasSrv.addBatch(xLoteSentencias[i]);
					}
				} finally {
					detallePagos.close();
				}

				// Indico actualización de los registros de transacción en la CR
				sentenciaSql = new String(
						"update br_transaccion set regactualizado='"
								+ Sesion.SI + "' where (numtienda = "
								+ criterioClave.get("numTienda")
								+ ") and (numcaja = "
								+ criterioClave.get("numCaja")
								+ ") and (numtransaccion = "
								+ criterioClave.get("numTransaccion")
								+ ") and (fecha = '"
								+ criterioClave.get("fecha") + "')");
				loteSentenciasCR.addBatch(sentenciaSql);
				sentenciaSql = new String(
						"update br_detalletransaccion set regactualizado='"
								+ Sesion.SI + "' where (numtienda = "
								+ criterioClave.get("numTienda")
								+ ") and (numcaja = "
								+ criterioClave.get("numCaja")
								+ ") and (numtransaccion = "
								+ criterioClave.get("numTransaccion")
								+ ") and (fecha = '"
								+ criterioClave.get("fecha") + "')");
				loteSentenciasCR.addBatch(sentenciaSql);
				sentenciaSql = new String(
						"update br_pagodetransaccion set regactualizado='"
								+ Sesion.SI + "' where (numtienda = "
								+ criterioClave.get("numTienda")
								+ ") and (numcaja = "
								+ criterioClave.get("numCaja")
								+ ") and (numtransaccion = "
								+ criterioClave.get("numTransaccion")
								+ ") and (fecha = '"
								+ criterioClave.get("fecha") + "')");
				loteSentenciasCR.addBatch(sentenciaSql);
				sentenciaSql = new String(
						"update br_comprobantefiscal set regactualizado='"
								+ Sesion.SI + "' where (numtienda = "
								+ criterioClave.get("numTienda")
								+ ") and (numcaja = "
								+ criterioClave.get("numCaja")
								+ ") and (numtransaccion = "
								+ criterioClave.get("numTransaccion")
								+ ") and (fecha = '"
								+ criterioClave.get("fecha") + "')");
				loteSentenciasCR.addBatch(sentenciaSql);

				// Ejecuto lote de sentencias en el DBMS del Servidor y de la CR
				Connection connSrv = Conexiones.getConexion(false, true);
				Connection connCR = Conexiones.getConexion(true, true);
				try {
					connCR.setAutoCommit(false);
					connSrv.setAutoCommit(false);
					connSrv.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
					connCR.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
					Conexiones.ejecutarLoteSentencias(loteSentenciasSrv, false,
							false, true);
					Conexiones.ejecutarLoteSentencias(loteSentenciasCR, true,
							false, true);
					connSrv.commit();
					connCR.commit();
					if (logger.isInfoEnabled()) {
						logger.info("Sincronizacion exitosa. Ult. transaccion BR "
								+ numTransaccion);
					}

				} catch (Exception e) {
					connCR.rollback();
					connSrv.rollback();
					logger.error(
							"syncTransaccionesBR(ResultSet): Rollback sinc. transacciones BR. T# "
									+ Sesion.getTienda().getNumero()
									+ " - Caja " + Sesion.getCaja().getNumero(),
							e);
					throw (new BaseDeDatosExcepcion(
							"Error en sentencia por lote al sincronizar transacciones BR"));
				} finally {
					connCR.setAutoCommit(true);
					connSrv.setAutoCommit(true);
				}
				loteSentenciasSrv.clearBatch();
				loteSentenciasCR.clearBatch();
			}
		} catch (BatchUpdateException e) {
			logger.error("syncTransaccionesBR(ResultSet)", e);
			throw (new BaseDeDatosExcepcion(
					"Error en sentencia por lote al sincronizar transacciones"));
		} catch (SQLException e) {
			logger.error("syncTransaccionesBR(ResultSet)", e);
			throw (new BaseDeDatosExcepcion(
					"Error en sentencia SQL al sincronizar transacciones"));
		} catch (BaseDeDatosExcepcion e) {
			logger.error("syncTransaccionesBR(ResultSet)", e);
			throw (new BaseDeDatosExcepcion(
					"Error con la BD al sincronizar transacciones"));
		} catch (ConexionExcepcion e) {
			logger.error("syncTransaccionesBR(ResultSet)", e);
			throw (new BaseDeDatosExcepcion(
					"Error de conexión con BD al sincronizar transacciones"));
		} catch (Throwable t) {
			logger.error("syncTransaccionesBR(ResultSet)", t);
		} finally {
			try {
				if (loteSentenciasCR != null)
					loteSentenciasCR.close();
			} catch (SQLException e1) {
				logger.error("syncTransaccionesBR(ResultSet)", e1);
			}

			try {
				if (loteSentenciasSrv != null)
					loteSentenciasSrv.close();
			} catch (SQLException e1) {
				logger.error("syncTransaccionesBR(ResultSet)", e1);
			}
		}
	}

	/**
	 * Método getTransaccion Selecciona la información correspondiente a la
	 * transacción indicada.
	 * 
	 * @param local
	 *            - Indicador de la conexión de BD a utilizar: Servidor o Local.
	 * @param numTienda
	 *            - Numero de la Tienda que se quiere recuperar el registro de
	 *            cabecera.
	 * @param fecha
	 *            - Fecha de la transaccion de BR de la que se quiere recuperar
	 *            el registro de cabecera.
	 * @param numCaja
	 *            - Caja de la que se quiere recuperar el registro de cabecera.
	 * @param numTransaccion
	 *            - Transacción de la que se quiere recuperar el registro de
	 *            cabecera.
	 * @return ResultSet - Retorna el registro correspondiente a la transacción.
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getTransaccionBR(boolean local, int numTienda,
			Date fecha, int numCaja, int numTransaccion)
			throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sentenciaSql = new String(
				"select * from br_transaccion where br_transaccion.numtienda = "
						+ numTienda + " and br_transaccion.fecha = '"
						+ df.format(fecha) + "' and br_transaccion.numcaja = "
						+ numCaja + " and br_transaccion.numtransaccion = "
						+ numTransaccion
						+ " and br_transaccion.regactualizado='" + Sesion.NO
						+ "'");
		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, false,
					true);
			resultado.first();
		} catch (BaseDeDatosExcepcion e) {
			logger.error("getTransaccionBR(boolean, int)", e);
			throw (new BaseDeDatosExcepcion("Error al realizar sentencia "
					+ sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			logger.error("getTransaccionBR(boolean, int)", e);
		} catch (SQLException e) {
			logger.error("getTransaccionBR(boolean, int)", e);
		}
		return resultado;
	}

	/**
	 * Método getDetalleTransaccionBR Selecciona todos los detalles de productos
	 * de la transaccion de Bono Regalo indicada.
	 * 
	 * @param local
	 *            - Indicador de la conexión de BD a utilizar: Servidor o Local.
	 * @param numTienda
	 *            - Numero de la Tienda que se quiere recuperar el registro del
	 *            detalle.
	 * @param fecha
	 *            - Fecha de la transaccion de BR de la que se quiere recuperar
	 *            el registro del detalle.
	 * @param numCaja
	 *            - Caja de la que se quiere recuperar el registro del detalle.
	 * @param numTransaccion
	 *            - Transacción de la que se quiere recuperar el registro del
	 *            detalle.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql
	 *         efectuada.
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getDetalleTransaccionBR(boolean local,
			int numTienda, Date fecha, int numCaja, int numTransaccion)
			throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sentenciaSql = new String(
				"select br_detalletransaccion.* from br_detalletransaccion where "
						+ "br_detalletransaccion.numtienda = " + numTienda
						+ " and br_detalletransaccion.fecha = '"
						+ df.format(fecha) + "' and "
						+ "br_detalletransaccion.numcaja = " + numCaja
						+ " and br_detalletransaccion.numtransaccion = "
						+ numTransaccion);

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, true,
					true);
			resultado.beforeFirst();
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion(
					"Error de BD al realizar sentencia " + sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion(
					"Error de Conexión al realizar sentencia " + sentenciaSql,
					e));
		} catch (SQLException e) {
			try {
				resultado.close();
			} catch (SQLException e1) {
				logger.error("getDetalleTransaccion(boolean, int)", e1);
			}
			throw (new BaseDeDatosExcepcion(
					"Error de SQL al realizar sentencia " + sentenciaSql, e));
		}
		return resultado;
	}

	/**
	 * Método getComprobantesFiscalesBR Selecciona todos los detalles de
	 * comprobantes fiscales de la transaccion de Bono Regalo indicada.
	 * 
	 * @param local
	 *            - Indicador de la conexión de BD a utilizar: Servidor o Local.
	 * @param numTienda
	 *            - Numero de la Tienda que se quiere recuperar el registro del
	 *            detalle.
	 * @param fecha
	 *            - Fecha de la transaccion de BR de la que se quiere recuperar
	 *            el registro del detalle.
	 * @param numCaja
	 *            - Caja de la que se quiere recuperar el registro del detalle.
	 * @param numTransaccion
	 *            - Transacción de la que se quiere recuperar el registro del
	 *            detalle.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql
	 *         efectuada.
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getComprobantesFiscalesBR(boolean local,
			int numTienda, Date fecha, int numCaja, int numTransaccion)
			throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sentenciaSql = new String(
				"select br_comprobantefiscal.* from br_comprobantefiscal where "
						+ "br_comprobantefiscal.numtienda = " + numTienda
						+ " and br_comprobantefiscal.fecha = '"
						+ df.format(fecha) + "' and "
						+ "br_comprobantefiscal.numcaja = " + numCaja
						+ " and br_comprobantefiscal.numtransaccion = "
						+ numTransaccion);

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, true,
					true);
			resultado.beforeFirst();
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion(
					"Error de BD al realizar sentencia " + sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion(
					"Error de Conexión al realizar sentencia " + sentenciaSql,
					e));
		} catch (SQLException e) {
			try {
				resultado.close();
			} catch (SQLException e1) {
				logger.error("getDetalleTransaccion(boolean, int)", e1);
			}
			throw (new BaseDeDatosExcepcion(
					"Error de SQL al realizar sentencia " + sentenciaSql, e));
		}
		return resultado;
	}

	/**
	 * Método getPagoTransaccionBR Selecciona todos los detalles de productos de
	 * la transaccion de Bono Regalo indicada.
	 * 
	 * @param local
	 *            - Indicador de la conexión de BD a utilizar: Servidor o Local.
	 * @param numTienda
	 *            - Numero de la Tienda que se quiere recuperar el registro de
	 *            pagos.
	 * @param fecha
	 *            - Fecha de la transaccion de BR de la que se quiere recuperar
	 *            el registro de pagos.
	 * @param numCaja
	 *            - Caja de la que se quiere recuperar el registro de pagos.
	 * @param numTransaccion
	 *            - Transacción de la que se quiere recuperar el registro de
	 *            pagos.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql
	 *         efectuada.
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getPagoTransaccionBR(boolean local, int numTienda,
			Date fecha, int numCaja, int numTransaccion)
			throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sentenciaSql = new String(
				"select br_pagodetransaccion.* from br_pagodetransaccion where "
						+ "br_pagodetransaccion.numtienda = " + numTienda
						+ " and br_pagodetransaccion.fecha = '"
						+ df.format(fecha) + "' and "
						+ "br_pagodetransaccion.numcaja = " + numCaja
						+ " and br_pagodetransaccion.numtransaccion = "
						+ numTransaccion);

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, true,
					true);
			resultado.beforeFirst();
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion(
					"Error de BD al realizar sentencia " + sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion(
					"Error de Conexión al realizar sentencia " + sentenciaSql,
					e));
		} catch (SQLException e) {
			try {
				resultado.close();
			} catch (SQLException e1) {
				logger.error("getDetalleTransaccion(boolean, int)", e1);
			}
			throw (new BaseDeDatosExcepcion(
					"Error de SQL al realizar sentencia " + sentenciaSql, e));
		}
		return resultado;
	}
}
