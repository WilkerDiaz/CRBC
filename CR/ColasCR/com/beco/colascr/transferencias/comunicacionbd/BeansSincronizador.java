/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.colascr.transferencias.comunicacionbd
 * Programa   : BeansSincronizador.java
 * Creado por : Ileana Rojas, Gabriel Martinelli
 * Creado en  : 22/02/2005 09:51:59 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 
 * Fecha       : 
 * Analista    : 
 * Descripción :
 * =============================================================================
 */
package com.beco.colascr.transferencias.comunicacionbd;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.JDBCException;
import net.sf.hibernate.Query;
import net.sf.hibernate.ReplicationMode;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import com.beco.colascr.transferencias.LeerArchivo;
import com.beco.colascr.transferencias.configuracion.Sesion;
import com.beco.colascr.transferencias.excepciones.BaseDeDatosExcepcion;
import com.beco.colascr.transferencias.excepciones.ConexionExcepcion;
import com.beco.colascr.transferencias.excepciones.ConexionServidorExcepcion;
import com.beco.colascr.transferencias.sincronizador.hibernate.base.EntidadMarcable;

/**
 * Descripción:
 * 
 */

public class BeansSincronizador {	
	
	private static ReplicationMode modoReplicacion = ReplicationMode.LATEST_VERSION;

	/**
	 * Método getTransacciones
	 * 		Selecciona todos los registros de las transacciones.
	 * @param local - Indicador de la conexión de BD a utilizar: Servidor o Local. 
	 * @param noSincronizadas - Indicador para seleccionar todas las transacciones o sólo las
	 * no sincronizadas.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql efectuada. 
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getTransacciones(boolean local, boolean noSincronizadas) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		String sentenciaSql = "";
		boolean actualizable;
		if (noSincronizadas){
			sentenciaSql = new String("select transaccion.numtransaccion, transaccion.numcajafinaliza from transaccion where (transaccion.regactualizado = 'N') and (transaccion.estadotransaccion = '" + Sesion.FINALIZADA + "')");
			actualizable = false; 
		}
		else { 
			sentenciaSql = new String("select transaccion.numtransaccion, transaccion.numcajafinaliza from transaccion where  (transaccion.estadotransaccion = "+ Sesion.FINALIZADA +")");
			actualizable = false;
		} 

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, actualizable);
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error al recuperar transacciones: " + sentenciaSql, e));
		} catch (ConexionExcepcion e) {
		}
		return resultado;
	}

	/**
	 * Método getTransaccion
	 * 		Selecciona la información correspondiente a la transacción indicada. 
	 * @param local - Indicador de la conexión de BD a utilizar: Servidor o Local.
	 * @param numTransaccion - Transacción de la que se quiere recuperar el registro de cabecera.
	 * @return ResultSet - Retorna el registro correspondiente a la transacción.
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getTransaccion(boolean local, int numTransaccion, int numCaja) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		String sentenciaSql = new String("select * from transaccion where (transaccion.numtransaccion = "+ numTransaccion +") and (numcajafinaliza = " + numCaja + ") and regactualizado='N' and estadotransaccion='F' ");
		try {
			boolean actualizable = true;
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, actualizable);
			resultado.first();
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error al recuperar transaccion: " + sentenciaSql, e));
		} catch (ConexionExcepcion e) {
		} catch (SQLException e) {
		}
		return resultado;
	}

	/**
	 * Método getDetalleTransaccion
	 * 		Selecciona todos los detalles de productos de la transacción indicada.
	 * @param local - Indicador de la conexión de BD a utilizar: Servidor o Local. 
	 * @param transaccion - Número de la transacción a sincronizar.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql efectuada. 
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getDetalleTransaccion(boolean local, int transaccion, int caja) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		System.out.println(caja);
		String sentenciaSql = new String("select detalletransaccion.* from transaccion inner join detalletransaccion on (transaccion.numtienda = detalletransaccion.numtienda) and (transaccion.numcajafinaliza = detalletransaccion.numcajafinaliza) and " + 
			"(transaccion.numtransaccion = detalletransaccion.numtransaccion) and (transaccion.fecha = detalletransaccion.fecha) " +
			"where (transaccion.regactualizado = 'N') and (transaccion.numtransaccion = "+ transaccion+") and (transaccion.numcajafinaliza = " + caja + ")");

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, true);
			resultado.beforeFirst();
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de BD al recuperar detalle de transaccion" + sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de Conexión al realizar sentencia " + sentenciaSql, e));
		} catch (SQLException e) {
			try {
				resultado.close();
			} catch (SQLException e1) {
			}
			throw (new BaseDeDatosExcepcion("Error de SQL al realizar sentencia " + sentenciaSql, e));
		}
		return resultado;
	}
	
	/**
	 * Método getCondicionesVenta
	 * 		Selecciona todas las condiciones de venta de los detalles de las transacciones
	 * @param local - Indicador de la conexión de BD a utilizar: Servidor o Local. 
	 * @param transaccion - Número de la transacción a sincronizar.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql efectuada. 
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getCondicionesVenta(boolean local, int transaccion, int caja) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		System.out.println(caja);
		String sentenciaSql = new String("select detalletransaccioncondicion.* from transaccion inner join detalletransaccioncondicion on (transaccion.numtienda = detalletransaccioncondicion.numtienda) and (transaccion.numcajainicia = detalletransaccioncondicion.numcajainicia) and " + 
			"(transaccion.numregcajainicia = detalletransaccioncondicion.numregcajainicia) and (transaccion.fecha = detalletransaccioncondicion.fecha) " +
			"where (transaccion.regactualizado = 'N') and (transaccion.numtransaccion = "+ transaccion+") and (transaccion.numcajafinaliza = " + caja + ")");

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, true);
			resultado.beforeFirst();
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de BD al recuperar condiciones de venta de la transaccion" + sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de Conexión al realizar sentencia " + sentenciaSql, e));
		} catch (SQLException e) {
			try {
				resultado.close();
			} catch (SQLException e1) {
			}
			throw (new BaseDeDatosExcepcion("Error de SQL al realizar sentencia " + sentenciaSql, e));
		}
		return resultado;
	}

	/**
	 * Método getPagoTransaccion
	 * 		Selecciona todos los detalles de pagos de la transacción indicada.
	 * @param local - Indicador de la conexión de BD a utilizar: Servidor o Local. 
	 * @param transaccion - Número de la transacción a sincronizar.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql efectuada. 
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getPagoTransaccion(boolean local, int transaccion, int caja) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		System.out.println(caja);
		String sentenciaSql = new String("select pagodetransaccion.* from transaccion inner join pagodetransaccion on (transaccion.numtienda = pagodetransaccion.numtienda) and (transaccion.numcajafinaliza = pagodetransaccion.numcaja) and " + 
			"(transaccion.numtransaccion = pagodetransaccion.numtransaccion) and (transaccion.fecha = pagodetransaccion.fecha) where (transaccion.regactualizado = 'N') and (transaccion.numtransaccion = "+ transaccion+") and (transaccion.numcajafinaliza = " + caja + ")");

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, true);
			resultado.beforeFirst();
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de BD al recuperar pago de transaccion " + sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de Conexión al realizar sentencia " + sentenciaSql, e));
		} catch (SQLException e) {
			try {
				resultado.close();
			} catch (SQLException e1) {
			}
			throw (new BaseDeDatosExcepcion("Error de SQL al realizar sentencia " + sentenciaSql, e));
		}
		return resultado;
	}
	
	/**
	 * Método getPromocionesRegistradas
	 * IROJAS: 06/03/2009. Método necesario para sinc tablas del módulo de promociones
	 * 		Selecciona los detalles de las promociones registradas para una venta
	 * @param local - Indicador de la conexión de BD a utilizar: Servidor o Local. 
	 * @param transaccion - Número de la transacción a sincronizar.
	 * @param caja - Número de caja donde fue realizada la transacción a sincronizar.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql efectuada. 
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getPromocionesRegistradas(boolean local, int transaccion, int caja) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		System.out.println(caja);
		String sentenciaSql = new String("select promocionregistrada.* from transaccion inner join promocionregistrada on (transaccion.numtienda = promocionregistrada.numTienda) and (transaccion.numcajafinaliza = promocionregistrada.numCaja) and " + 
			"(transaccion.numtransaccion = promocionregistrada.numtransacion) and (transaccion.fecha = promocionregistrada.fecha) " +
			"where (transaccion.regactualizado = 'N') and (transaccion.numtransaccion = "+ transaccion+") and (transaccion.numcajafinaliza = " + caja + ")");
		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, true);
			resultado.beforeFirst();
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de BD al recuperar condiciones de venta de la transaccion" + sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de Conexión al realizar sentencia " + sentenciaSql, e));
		} catch (SQLException e) {
			try {
				resultado.close();
			} catch (SQLException e1) {
			}
			throw (new BaseDeDatosExcepcion("Error de SQL al realizar sentencia " + sentenciaSql, e));
		}
		return resultado;
	}

	/**
	 * Método getRegalosRegistrados
	 * IROJAS: 06/03/2009. Método necesario para sinc tablas del módulo de promociones
	 * 		Selecciona los regalos registrados de las promociones aplicadas para una venta
	 * @param local - Indicador de la conexión de BD a utilizar: Servidor o Local. 
	 * @param transaccion - Número de la transacción a sincronizar.
	 * @param caja - Número de caja donde fue realizada la transacción a sincronizar.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql efectuada. 
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getRegalosRegistrados(boolean local, int transaccion, int caja) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		System.out.println(caja);
		String sentenciaSql = new String("select regalosregistrados.* from transaccion inner join regalosregistrados on (transaccion.numtienda = regalosregistrados.numTienda) and (transaccion.numcajafinaliza = regalosregistrados.numCaja) and " + 
			"(transaccion.numtransaccion = regalosregistrados.numTransaccion) and (transaccion.fecha = regalosregistrados.fechaTransaccion) " +
			"where (transaccion.regactualizado = 'N') and (transaccion.numtransaccion = "+ transaccion+") and (transaccion.numcajafinaliza = " + caja + ")");

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, true);
			resultado.beforeFirst();
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de BD al recuperar condiciones de venta de la transaccion" + sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de Conexión al realizar sentencia " + sentenciaSql, e));
		} catch (SQLException e) {
			try {
				resultado.close();
			} catch (SQLException e1) {
			}
			throw (new BaseDeDatosExcepcion("Error de SQL al realizar sentencia " + sentenciaSql, e));
		}
		return resultado;
	}
	
	/**
	 * Método getTransaccionPremiada
	 * IROJAS: 06/03/2009. Método necesario para sinc tablas del módulo de promociones
	 * 		Selecciona los datos de la transaccion premiada aplicada para una venta
	 * @param local - Indicador de la conexión de BD a utilizar: Servidor o Local. 
	 * @param transaccion - Número de la transacción a sincronizar.
	 * @param caja - Número de caja donde fue realizada la transacción a sincronizar.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql efectuada. 
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getTransaccionPremiada(boolean local, int transaccion, int caja) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		System.out.println(caja);
		String sentenciaSql = new String("select transaccionpremiada.* from transaccion inner join transaccionpremiada on (transaccion.numtienda = transaccionpremiada.numTienda) and (transaccion.numcajafinaliza = transaccionpremiada.numCaja) and " + 
			"(transaccion.numtransaccion = transaccionpremiada.numTransaccion) and (transaccion.fecha = transaccionpremiada.fecha) " +
			"where (transaccion.regactualizado = 'N') and (transaccion.numtransaccion = "+ transaccion+") and (transaccion.numcajafinaliza = " + caja + ")");
		
		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, true);
			resultado.beforeFirst();
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de BD al recuperar condiciones de venta de la transaccion" + sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de Conexión al realizar sentencia " + sentenciaSql, e));
		} catch (SQLException e) {
			try {
				resultado.close();
			} catch (SQLException e1) {
			}
			throw (new BaseDeDatosExcepcion("Error de SQL al realizar sentencia " + sentenciaSql, e));
		}
		return resultado;
	}
	
	/**
	 * Método getDonacionesRegistradas
	 * IROJAS: 06/03/2009. Método necesario para sinc tablas del módulo de promociones
	 * 		Selecciona los datos de las donaciones registradas para una venta
	 * @param local - Indicador de la conexión de BD a utilizar: Servidor o Local. 
	 * @param transaccion - Número de la transacción a sincronizar.
	 * @param caja - Número de caja donde fue realizada la transacción a sincronizar.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql efectuada. 
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getDonacionesRegistradas(boolean local, int transaccion, int caja) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		System.out.println(caja);
		String sentenciaSql = new String("select donacionesregistradas.* from transaccion inner join donacionesregistradas on (transaccion.numtienda = donacionesregistradas.numTienda) and (transaccion.numcajafinaliza = donacionesregistradas.numCaja) and " + 
			"(transaccion.numtransaccion = donacionesregistradas.numTransaccion) and (transaccion.fecha = donacionesregistradas.fechaDonacion) " +
			"where (transaccion.regactualizado = 'N') and (transaccion.numtransaccion = "+ transaccion+") and (transaccion.numcajafinaliza = " + caja + ")");

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, true);
			resultado.beforeFirst();
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de BD al recuperar condiciones de venta de la transaccion" + sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de Conexión al realizar sentencia " + sentenciaSql, e));
		} catch (SQLException e) {
			try {
				resultado.close();
			} catch (SQLException e1) {
			}
			throw (new BaseDeDatosExcepcion("Error de SQL al realizar sentencia " + sentenciaSql, e));
		}
		return resultado;
	}
	
	/**
	 * Método getPagosDeDonaciones
	 * IROJAS: 06/03/2009. Método necesario para sinc tablas del módulo de promociones
	 * 		Selecciona los pagos de las donaciones registradas para una venta
	 * @param local - Indicador de la conexión de BD a utilizar: Servidor o Local. 
	 * @param transaccion - Número de la transacción a sincronizar.
	 * @param caja - Número de caja donde fue realizada la transacción a sincronizar.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql efectuada. 
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getPagosDeDonaciones(boolean local, int transaccion, int caja) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		System.out.println(caja);
		String sentenciaSql = new String("select pagodonacion.* from transaccion inner join pagodonacion on (transaccion.numtienda = pagodonacion.numTienda) and (transaccion.numcajafinaliza = pagodonacion.numCaja) and " + 
			"(transaccion.numtransaccion = pagodonacion.numTransaccion) and (transaccion.fecha = pagodonacion.fechaDonacion) " +
			"where (transaccion.regactualizado = 'N') and (transaccion.numtransaccion = "+ transaccion+") and (transaccion.numcajafinaliza = " + caja + ")");

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, true);
			resultado.beforeFirst();
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de BD al recuperar condiciones de venta de la transaccion" + sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de Conexión al realizar sentencia " + sentenciaSql, e));
		} catch (SQLException e) {
			try {
				resultado.close();
			} catch (SQLException e1) {
			}
			throw (new BaseDeDatosExcepcion("Error de SQL al realizar sentencia " + sentenciaSql, e));
		}
		return resultado;
	}
	/**
	 * Método syncTransacciones
	 * 		Sincroniza las transacciones (cabecera, detalle de productos y pagos) desde la Caja Registradora
	 * hacia el Servidor de tienda.
	 * @param numTransacciones - Lista de los numeros correspondientes a las transacciones de la caja que no
	 * 		han sido actualizadas, sin considerar fecha del sistema.
	 * @throws BaseDeDatosExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Se parametrizó el tipo de dato de los distintos ArrayList y HashMap.
	* Se eliminaron variables sin uso
	* Fecha: agosto 2011
	*/
	public static synchronized void syncTransacciones(ResultSet numTransacciones) throws BaseDeDatosExcepcion{
		ResultSet detalleProductos, detallePagos, transacciones;
		ArrayList <ArrayList<Object>>registros;
		ArrayList <Object>registro;
		String sentenciaSql;
		String[] xLoteSentencias;
		Statement loteSentenciasSrv = null;
		Statement loteSentenciasCR = null;
		StringBuffer xCampos, xValores;

		try {
			int numTransaccion = 0;
			int numCaja = 0;
			loteSentenciasSrv = Conexiones.crearSentencia(false);
			loteSentenciasCR = Conexiones.crearSentencia(true);
			HashMap<String,Object> criterioClave = new HashMap<String,Object>();

			while(numTransacciones.next()){
				numTransaccion = numTransacciones.getInt("transaccion.numtransaccion");
				numCaja = numTransacciones.getInt("transaccion.numcajafinaliza");
				 
				// Obtengo información de la CR de la transacción actual
				int numColumnas = 0;
				transacciones = getTransaccion(true, numTransaccion, numCaja);
				try {
					xCampos = new StringBuffer("");
					xValores = new StringBuffer("");
					numColumnas = transacciones.getMetaData().getColumnCount();
					for (int i=0; i<numColumnas; i++){
						xCampos.append(transacciones.getMetaData().getColumnName(i+1));
						xValores.append("?");
						if(i+1 < numColumnas){
							xCampos.append(", ");
							xValores.append(", ");
						}
					}
					sentenciaSql = new String("insert into transaccion ("+xCampos+") values ("+xValores+")");

					transacciones.first();
					criterioClave.put("numTienda", transacciones.getObject("numtienda"));
					criterioClave.put("numCajaFinaliza", transacciones.getObject("numcajafinaliza"));
					criterioClave.put("fecha", transacciones.getObject("fecha"));
					criterioClave.put("numCajaInicia", transacciones.getObject("numcajainicia"));
					transacciones.beforeFirst();
					
					registros = new ArrayList<ArrayList<Object>>();
					while(transacciones.next()){
						registro = new ArrayList<Object>();
						for (int i=0; i<numColumnas; i++)
							registro.add(transacciones.getObject(transacciones.getMetaData().getColumnName(i+1).toLowerCase()));
						registros.add(registro);
					}	
				} finally {
					transacciones.close();
				}

				xLoteSentencias = Conexiones.crearLoteSentencias(sentenciaSql, registros, false);
				for (int i=0; i<xLoteSentencias.length; i++){
					loteSentenciasSrv.addBatch(xLoteSentencias[i]);
				}
				
	
				// Obtengo el detalle de renglones de la transacción
				registros = new ArrayList<ArrayList<Object>>();
				xCampos = new StringBuffer("");
				xValores = new StringBuffer("");
				detalleProductos = BeansSincronizador.getDetalleTransaccion(true, numTransaccion, numCaja);
				try {
					numColumnas = detalleProductos.getMetaData().getColumnCount();
					for (int i=0; i<numColumnas; i++){
						xCampos.append(detalleProductos.getMetaData().getColumnName(i+1));
						xValores.append("?");
						if(i+1 < numColumnas){
							xCampos.append(", ");
							xValores.append(", ");
						}
					}
					sentenciaSql = new String("insert into detalletransaccion ("+xCampos+") values ("+xValores+")");

					while (detalleProductos.next()){
						registro = new ArrayList<Object>();
						for (int i=0; i<numColumnas; i++)
							registro.add(detalleProductos.getObject(detalleProductos.getMetaData().getColumnName(i+1).toLowerCase()));
						registros.add(registro);
					}
				} finally {
					detalleProductos.close();
				}
				xLoteSentencias = Conexiones.crearLoteSentencias(sentenciaSql, registros, false);
				for (int i=0; i<xLoteSentencias.length; i++){
					loteSentenciasSrv.addBatch(xLoteSentencias[i]);
				}
				
				//IROJAS 06/03/2009. Sincronización de entidades adicionales que se requiere
				//su sincronización con la venta.
				BeansSincronizador.getAdicionalesDeVenta(loteSentenciasSrv, numTransaccion, numCaja);
				
				// Obtengo el detalle de pagos de la transacción
				registros = new ArrayList<ArrayList<Object>>();
				xCampos = new StringBuffer("");
				xValores = new StringBuffer("");
				detallePagos = BeansSincronizador.getPagoTransaccion(true, numTransaccion, numCaja);
				try {
					numColumnas = detallePagos.getMetaData().getColumnCount();
					for (int i=0; i<numColumnas; i++){
						xCampos.append(detallePagos.getMetaData().getColumnName(i+1));
						xValores.append("?");
						if(i+1 < numColumnas){
							xCampos.append(", ");
							xValores.append(", ");
						}
					}
					sentenciaSql = new String("insert into pagodetransaccion ("+xCampos+") values ("+xValores+")");

					while (detallePagos.next()){
						registro = new ArrayList<Object>();
						for (int i=0; i<numColumnas; i++)
							registro.add(detallePagos.getObject(detallePagos.getMetaData().getColumnName(i+1).toLowerCase()));
						registros.add(registro);
					}
				} finally {
					detallePagos.close();
				}

				xLoteSentencias = Conexiones.crearLoteSentencias(sentenciaSql, registros, false);
				for (int i=0; i<xLoteSentencias.length; i++){
					loteSentenciasSrv.addBatch(xLoteSentencias[i]);
				}

				
				// Indico actualización de los registros de transacción en la CR
				sentenciaSql = new String("update transaccion set regactualizado='" + Sesion.SI + "' where (numtienda = "+criterioClave.get("numTienda")+") and (numcajafinaliza = "+criterioClave.get("numCajaFinaliza")+") and (numtransaccion = "+numTransaccion+") and (fecha = '"+criterioClave.get("fecha")+"')");
				loteSentenciasCR.addBatch(sentenciaSql);
				sentenciaSql = new String("update detalletransaccion set regactualizado='" + Sesion.SI + "' where (numtienda = "+criterioClave.get("numTienda")+") and (numcajafinaliza = "+criterioClave.get("numCajaFinaliza")+") and (numtransaccion = "+numTransaccion+") and (fecha = '"+criterioClave.get("fecha")+"')");
				loteSentenciasCR.addBatch(sentenciaSql);
				
				//*** IROJAS 06/03/2009. Actualización de entidades adicionales que se requiere
				//su sincronización con la venta.
				sentenciaSql = new String("update detalletransaccioncondicion set regactualizado='" + Sesion.SI + "' where (numtienda = "+criterioClave.get("numTienda")+") and (numcajainicia = "+criterioClave.get("numCajaInicia")+") and (numtransaccion = "+numTransaccion+") and (fecha = '"+criterioClave.get("fecha")+"')");
				loteSentenciasCR.addBatch(sentenciaSql);

				sentenciaSql = new String("update promocionregistrada set regactualizado='" + Sesion.SI + "' where (numTienda = "+criterioClave.get("numTienda")+") and (numCaja = "+criterioClave.get("numCajaFinaliza")+") and (numtransacion = "+numTransaccion+") and (fecha = '"+criterioClave.get("fecha")+"')");
				loteSentenciasCR.addBatch(sentenciaSql);
								
				sentenciaSql = new String("update regalosregistrados set regactualizado='" + Sesion.SI + "' where (numTienda = "+criterioClave.get("numTienda")+") and (numCaja = "+criterioClave.get("numCajaFinaliza")+") and (numTransaccion = "+numTransaccion+") and (fechaTransaccion = '"+criterioClave.get("fecha")+"')");
				loteSentenciasCR.addBatch(sentenciaSql);
				
				sentenciaSql = new String("update transaccionpremiada set regactualizado='" + Sesion.SI + "' where (numTienda = "+criterioClave.get("numTienda")+") and (numCaja = "+criterioClave.get("numCajaFinaliza")+") and (numTransaccion = "+numTransaccion+") and (fecha = '"+criterioClave.get("fecha")+"')");
				loteSentenciasCR.addBatch(sentenciaSql);
				
				sentenciaSql = new String("update donacionesregistradas set regactualizado='" + Sesion.SI + "' where (numTienda = "+criterioClave.get("numTienda")+") and (numCaja = "+criterioClave.get("numCajaFinaliza")+") and (numTransaccion = "+numTransaccion+") and (fechaDonacion = '"+criterioClave.get("fecha")+"')");
				loteSentenciasCR.addBatch(sentenciaSql);
				
				sentenciaSql = new String("update pagodonacion set regactualizado='" + Sesion.SI + "' where (numTienda = "+criterioClave.get("numTienda")+") and (numCaja = "+criterioClave.get("numCajaFinaliza")+") and (numTransaccion = "+numTransaccion+") and (fechaDonacion = '"+criterioClave.get("fecha")+"')");
				loteSentenciasCR.addBatch(sentenciaSql);
				//***
				
				sentenciaSql = new String("update pagodetransaccion set regactualizado='" + Sesion.SI + "' where (numtienda = "+criterioClave.get("numTienda")+") and (numcaja = "+criterioClave.get("numCajaFinaliza")+") and (numtransaccion = "+numTransaccion+") and (fecha = '"+criterioClave.get("fecha")+"')");
				loteSentenciasCR.addBatch(sentenciaSql);
				
				// Ejecuto lote de sentencias en el DBMS del Servidor y de la CR
				Connection connSrv = Conexiones.getConexion(false);
				Connection connCR = Conexiones.getConexion(true);
				try {
					connCR.setAutoCommit(false);
					connSrv.setAutoCommit(false);
					connSrv.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
					connCR.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
					Conexiones.ejecutarLoteSentencias(loteSentenciasSrv, false, false);
					Conexiones.ejecutarLoteSentencias(loteSentenciasCR, true, false);
					connCR.commit();
					connSrv.commit();					
				} catch (Exception e) {
					connCR.rollback();
					connSrv.rollback();
					throw (new BaseDeDatosExcepcion("Error en sentencia por lote al sincronizar transacciones"));
				} finally {
					connCR.setAutoCommit(true);
					connSrv.setAutoCommit(true);
				}
				loteSentenciasSrv.clearBatch();
				loteSentenciasCR.clearBatch();
			}
			
		} catch (BatchUpdateException e) {
			throw (new BaseDeDatosExcepcion("Error en sentencia por lote al sincronizar transacciones"));
		} catch (SQLException e) {
			throw (new BaseDeDatosExcepcion("Error en sentencia SQL al sincronizar transacciones"));
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error con la BD al sincronizar transacciones"));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de conexión con BD al sincronizar transacciones"));
		} catch (Throwable t) {
		} finally{
			try {
				if(loteSentenciasCR != null)
					loteSentenciasCR.close();
			} catch (SQLException e1) {
			}
			
			try {
				if(loteSentenciasSrv != null)
					loteSentenciasSrv.close();
			} catch (SQLException e1) {
			}	
		}
	}

	/**
	 * Método getAbonos
	 * 		Selecciona todos los registros de las transacciones de abonos.
	 * @param local - Indicador de la conexión de BD a utilizar: Servidor o Local. 
	 * @param noSincronizadas - Indicador para seleccionar todas las transacciones de abono o sólo las
	 * no sincronizadas.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql efectuada. 
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getAbonos(boolean local, boolean noSincronizadas) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		String sentenciaSql = "";
		boolean actualizable;
		if (noSincronizadas){
			sentenciaSql = new String("select transaccionabono.numabono, transaccionabono.numservicio from transaccionabono where (transaccionabono.regactualizado = '" + Sesion.NO + "') ");
			actualizable = false; 
		}
		else { 
			sentenciaSql = new String("select transaccionabono.numabono, transaccionabono.numservicio from transaccionabono ");
			actualizable = true;
		} 

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, actualizable);
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error al recuperar abonos", e));
		} catch (ConexionExcepcion e) {
		}
		return resultado;
	}

	/**
	 * Método getServDeAbonos
	 * 		Permite buscar los números de servicios a los que se les van a actualizar los abonos en el servidor
	 * @param local - Indicador de la conexión de BD a utilizar: Servidor o Local. 
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql efectuada. 
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getServDeAbonos(boolean local) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		String sentenciaSql = "";
		
		sentenciaSql = new String("select transaccionabono.numservicio from transaccionabono where (transaccionabono.regactualizado = '" + Sesion.NO + "') group by numservicio");
		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, false);
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error al recuperar número de servicios de los abonos", e));
		} catch (ConexionExcepcion e) {
		}
		return resultado;
	}
	
	/**
	 * Método getAnulacionAbonos
	 * 		Selecciona todos los registros de las transacciones de abonos.
	 * @param local - Indicador de la conexión de BD a utilizar: Servidor o Local. 
	 * @param noSincronizadas - Indicador para seleccionar todas las transacciones de abono o sólo las
	 * no sincronizadas.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql efectuada. 
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getAnulacionAbonos(boolean local, boolean noSincronizadas) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		String sentenciaSql = "";
		boolean actualizable;
		if (noSincronizadas){
			sentenciaSql = new String("select * from anulaciondeabonos where (anulaciondeabonos.regactualizado = 'N') ");
			actualizable = false; 
		}
		else { 
			sentenciaSql = new String("select * from anulaciondeabonos ");
			actualizable = true;
		} 

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, actualizable);
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error al recuperar abonos", e));
		} catch (ConexionExcepcion e) {
		}
		return resultado;
	}
	
	/**
	 * Método getAbono
	 * 		Selecciona la información correspondiente a la transacción de abono indicada. 
	 * @param local - Indicador de la conexión de BD a utilizar: Servidor o Local.
	 * @param numAbono - Transacción de abono de la que se quiere recuperar el registro de cabecera.
	 * @param numServicio - Número del servicio asociado a la transacción de abono de la que se quiere recuperar el registro de cabecera.
	 * @return ResultSet - Retorna el registro correspondiente a la transacción.
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getAbono(boolean local, int numAbono, int numServicio) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		String sentenciaSql = new String("select * from transaccionabono where (transaccionabono.numabono = "+ numAbono +") and (transaccionabono.numservicio = "+ numServicio +")");
		try {
			boolean actualizable = true;
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, actualizable);
			resultado.first();
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error al recuperar abono" + numAbono, e));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error al recuperar abono" + numAbono, e));
		} catch (SQLException e) {
			throw (new BaseDeDatosExcepcion("Error al recuperar abono" + numAbono, e));
		}
		return resultado;
	}

	/**
	 * Método getAbonosDeServicio
	 * 		Selecciona todos los abonos del servicio especificado.
	 * @param local - Indicador de la conexión de BD a utilizar: Servidor o Local.
	 * @param numServicio - Número del servicio asociado a la transacción de abono de la que se quiere recuperar el registro de cabecera.
	 * @return ResultSet - Retorna el registro correspondiente a la transacción.
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getAbonosDeServicio(boolean local, int numServicio) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		String sentenciaSql = new String("select numabono,numservicio from "+ Sesion.getDbEsquema() + ".transaccionabono where (numservicio = "+ numServicio +") and numtienda=" + Sesion.getTienda().getNumero());
		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, true);
			resultado.first();
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error al recuperar abono ", e));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error al recuperar abono", e));
		} catch (SQLException e) {
			throw (new BaseDeDatosExcepcion("Error al recuperar abono", e));
		}
		return resultado;
	}
	
	/**
	 * Método getAnulacionAbono
	 * 		Selecciona la información correspondiente a la anulacion de abono indicada. 
	 * @param local - Indicador de la conexión de BD a utilizar: Servidor o Local.
	 * @param numAbono - Anulacion de abono de la que se quiere recuperar el registro de cabecera.
	 * @param numServicio - Número del servicio asociado a la anulación de abono de la que se quiere recuperar el registro de cabecera.
	 * @return ResultSet - Retorna el registro correspondiente a la anulación de abono.
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getAnulacionAbono(boolean local, int numAbono, int numServicio) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		String sentenciaSql = new String("select * from anulaciondeabonos where (anulaciondeabonos.numabono = "+ numAbono +") and (anulaciondeabonos.numservicio = "+ numServicio +")");
		try {
			boolean actualizable = true;
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, actualizable);
			resultado.first();
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error al recuperar abono" + numAbono, e));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error al recuperar abono" + numAbono, e));
		} catch (SQLException e) {
			throw (new BaseDeDatosExcepcion("Error al recuperar abono" + numAbono, e));
		}
		return resultado;
	}

	/**
	 * Método getPagoAbono
	 * 		Selecciona todos los detalles (productos/pagos) de la transacción indicada.
	 * @param local - Indicador de la conexión de BD a utilizar: Servidor o Local. 
	 * @param numAbono - Número de la transacción de abono a sincronizar.
	 * @param numServicio - Número del servicio correspondiente a la transacción de abono a sincronizar.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql efectuada. 
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getPagoAbono(boolean local, int numTda, int numAbono, int numServicio, int numCaja, Date fecha) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
//		String sentenciaSql = new String("select * from transaccionabono inner join pagodeabonos on (transaccionabono.numtienda = pagodeabonos.numtienda) and (transaccionabono.numcaja = pagodeabonos.numcaja) and " + 
//			"(transaccionabono.numabono = pagodeabonos.numabono) and (transaccionabono.fecha = pagodeabonos.fecha) and (transaccionabono.numservicio = pagodeabonos.numservicio) where (transaccionabono.regactualizado = 'N') and (transaccionabono.numabono = "+ numAbono+") and (transaccionabono.numservicio = "+ numServicio+")");
		String sentenciaSql = new String("select * from pagodeabonos where pagodeabonos.numservicio = " + numServicio + " and pagodeabonos.numtienda = " + numTda + " and pagodeabonos.numcaja = " + numCaja + " and pagodeabonos.fecha = '" + fecha + "' and pagodeabonos.numAbono = " + numAbono +" and regactualizado= '" + Sesion.NO +"'");
		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, true);
			resultado.beforeFirst();
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de BD al realizar sentencia " + sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de Conexión al realizar sentencia " + sentenciaSql, e));
		} catch (SQLException e) {
			try {
				resultado.close();
			} catch (SQLException e1) {
			}
			throw (new BaseDeDatosExcepcion("Error de SQL al realizar sentencia " + sentenciaSql, e));
		}
		return resultado;
	}

	/**
	 * Método syncAbonos
	 * 		Sincroniza las transacciones de abonos (cabecera y detalles de pagos) desde la Caja Registradora
	 * hacia el Servidor de tienda.
	 * @param numAbonos - Lista de los numeros correspondientes a las transacciones de la caja que no
	 * 		han sido actualizadas, sin considerar fecha del sistema.
	 * @throws BaseDeDatosExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	public static synchronized void syncAbonos(ResultSet numAbonos) throws BaseDeDatosExcepcion{
		ResultSet detallePagos, abonos, consultaServAbonos, abonosEnServCent = null;
		ArrayList <ArrayList<Object>>registros;
		ArrayList <Object>registro;
		String sentenciaSql;
		String[] xLoteSentencias;
		Statement loteSentenciasSrv = null;
		Statement loteSentenciasCR = null;
		ArrayList <Integer>servDeAbonos = new ArrayList<Integer>();
		ArrayList <ArrayList<Integer>>abonosEnServidor = new ArrayList<ArrayList<Integer>>();

		boolean existe = false;

		try {
			int numAbono = 0;
			int numServicio = 0;
			loteSentenciasSrv = Conexiones.crearSentencia(false);
			loteSentenciasCR = Conexiones.crearSentencia(true);
			int numA;
			int numS;
			//Primero hacemos un select local para recuperar todos los servicios a los que se les van a actualizar los abonos
			consultaServAbonos = getServDeAbonos(true);
			try {
				consultaServAbonos.beforeFirst();
				while(consultaServAbonos.next()) {
					numS = consultaServAbonos.getInt("numservicio");
					servDeAbonos.add(new Integer(numS));
				}
			} finally {
				consultaServAbonos.close();
			}
	

			//Ahora hacemos un select en el ServCentral para recuperar los Abonos de los servicios seleccionados anteriormente
			//Con esto obtenemos abonos que ya se encuentran en el servidor
			try {
				for(int i=0; i<servDeAbonos.size(); i++) {
					numServicio = ((Integer)servDeAbonos.get(i)).intValue();
					abonosEnServCent = getAbonosDeServicio(false,numServicio);
					abonosEnServCent.beforeFirst();
					while(abonosEnServCent.next()) {
						ArrayList <Integer>abonosEnServidorX = new ArrayList<Integer>();
						numA = abonosEnServCent.getInt("numabono");
						numS = abonosEnServCent.getInt("numservicio");
						abonosEnServidorX.add(new Integer(numS));
						abonosEnServidorX.add(new Integer(numA));
						abonosEnServidor.add(abonosEnServidorX);
					}
					abonosEnServCent.close();
				}
			} finally {
				abonosEnServCent.close();
				abonosEnServCent = null;
			}

			
			while(numAbonos.next()){
				numAbono = numAbonos.getInt("transaccionabono.numabono");
				numServicio = numAbonos.getInt("transaccionabono.numservicio");
				System.out.println("Sincronizando abono: " + numAbono + " del servicio: " + numServicio);
				 
				// Obtengo información de la CR de la transacción actual
				abonos = getAbono(true, numAbono, numServicio);
				int numTienda;
				int numCaja;
				Date fecha = null;
				try {
					numTienda = abonos.getInt("transaccionabono.numtienda");
					numCaja = abonos.getInt("transaccionabono.numcaja");
					fecha = abonos.getDate("transaccionabono.fecha");
					String tipoTransaccionAbono = abonos.getString("transaccionabono.tipotransaccionabono");
					String codigoTipoServicio = abonos.getString("transaccionabono.codtiposervicio");
					Date fechaServicio = abonos.getDate("transaccionabono.fechaservicio");
					String codCajero = abonos.getString("transaccionabono.codcajero");
					Time horaInicia = abonos.getTime("transaccionabono.horainicia");
					Time horaFinaliza = abonos.getTime("transaccionabono.horafinaliza");
					Double monto = new Double(abonos.getDouble("transaccionabono.monto"));
					Double vueltoCliente = new Double(abonos.getDouble("transaccionabono.vueltoCliente"));
					Double montoRemanente = new Double(abonos.getDouble("transaccionabono.montoRemanente"));
					String cajaEnLinea = new String(abonos.getString("transaccionabono.cajaenlinea"));
					String serialCaja = new String(abonos.getString("transaccionabono.serialcaja"));
					String estadoAbono = abonos.getString("transaccionabono.estadoabono");
					String regActualizado = abonos.getString("transaccionabono.regactualizado");
						
					//Ahora se realiza el chequeo de los abonos para ver si existen el Serv Central para hacer un UPDATE o INSERT
					existe = false;
					for (int i=0; i<abonosEnServidor.size(); i++) {
						int nServ = ((Integer)((ArrayList<Integer>)abonosEnServidor.get(i)).get(0)).intValue();
						int abonoEnCentr =((Integer)((ArrayList<Integer>)abonosEnServidor.get(i)).get(1)).intValue();
						if(numServicio == nServ && numAbono == abonoEnCentr) {
							existe = true;
							break;
						} 	
					}
					
					if(existe){ //El abono se encuentra en el servidor y se debe hacer un UPDATE
						System.out.println("---->UPDATE");
						sentenciaSql = "update " + Sesion.getDbEsquema()+".transaccionabono set "
							+ Sesion.getDbEsquema()+".transaccionabono.tipotransaccionabono = '" + tipoTransaccionAbono + "', "
							+ Sesion.getDbEsquema()+".transaccionabono.estadoabono = '" + estadoAbono + "', "
						    + Sesion.getDbEsquema()+".transaccionabono.regactualizado = '" + regActualizado + "' "
							+ "where " + Sesion.getDbEsquema()+".transaccionabono.numtienda = " + numTienda 
							+ " and " + Sesion.getDbEsquema()+".transaccionabono.numcaja = " + numCaja 
							+ " and " + Sesion.getDbEsquema()+".transaccionabono.fecha = '" + fecha 
							+ "' and " + Sesion.getDbEsquema()+".transaccionabono.numAbono = " + numAbono 
							+ " and " + Sesion.getDbEsquema()+".transaccionabono.numservicio = " + numServicio;
					} else { //No existe en el Srv Central y se de hacer un INSERT
						System.out.println("---->INSERT");
						sentenciaSql = new String("insert into " + Sesion.getDbEsquema()+".transaccionabono "
							+ "values (" + numTienda + ", " + numCaja + ", '" + fecha + "', " + numAbono + ", " + numServicio
							+ ", '" + tipoTransaccionAbono + "', " + "'" + codigoTipoServicio + "', '" + fechaServicio + "', " 
							+ "'" + codCajero + "', '" + horaInicia + "', '" + horaFinaliza + "', " +  monto + ", " + vueltoCliente +", " 
							+ montoRemanente +", '"+ cajaEnLinea + "', '"+ serialCaja + "', '" + estadoAbono + "', '" + regActualizado + "')");
					}
					loteSentenciasSrv.addBatch(sentenciaSql);
				} finally {
					abonos.close();
				}
				
				if(!existe) { //Si el abono no existía en el ServCentral subimos todos sus pagos. Si existía no hace falta sincronizarlos
				// Obtengo el detalle de pagos del abono
				registros = new ArrayList<ArrayList<Object>>();
					detallePagos = BeansSincronizador.getPagoAbono(true, numTienda, numAbono, numServicio, numCaja, fecha);
				try {
					while (detallePagos.next()){
						System.out.println("Sincronizando Pago de abono: " + numAbono+ " del servicio: " + numServicio);
						registro = new ArrayList<Object>();
						registro.add(new Short(detallePagos.getShort("pagodeabonos.numtienda")));
						registro.add(new Short(detallePagos.getShort("pagodeabonos.numcaja")));
						registro.add(new Integer(detallePagos.getInt("pagodeabonos.numabono")));
						registro.add(detallePagos.getDate("pagodeabonos.fecha"));
						registro.add(new Integer(detallePagos.getInt("pagodeabonos.numservicio")));
						registro.add(detallePagos.getString("pagodeabonos.codformadepago"));
						registro.add(new Short(detallePagos.getShort("pagodeabonos.correlativoitem")));
						registro.add(detallePagos.getString("pagodeabonos.codbanco"));
						registro.add(new Double(detallePagos.getDouble("pagodeabonos.monto")));
						registro.add(detallePagos.getString("pagodeabonos.numdocumento"));
						registro.add(detallePagos.getString("pagodeabonos.numcuenta"));
						registro.add(detallePagos.getString("pagodeabonos.numconformacion"));
						registro.add(new Integer(detallePagos.getInt("pagodeabonos.numreferencia")));
						registro.add(detallePagos.getString("pagodeabonos.cedtitular"));
						registro.add(detallePagos.getString("pagodeabonos.regactualizado"));
						registros.add(registro);
					}
				} finally {
					detallePagos.close();
				}

					sentenciaSql = new String("insert into "+ Sesion.getDbEsquema()+".pagodeabonos (numtienda,numcaja,numabono,fecha,numservicio,codformadepago,correlativoitem,codbanco,monto,numdocumento,numcuenta,numconformacion,numreferencia,cedtitular,regactualizado) " +
							"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				xLoteSentencias = Conexiones.crearLoteSentencias(sentenciaSql, registros, false);
				for (int i=0; i<xLoteSentencias.length; i++){
					loteSentenciasSrv.addBatch(xLoteSentencias[i]);
				}
				}
				// Indico actualización de los registros de transacción en la CR
				sentenciaSql = new String("update transaccionabono set regactualizado='" + Sesion.SI + "' where (numtienda = "+numTienda+") and (numcaja = "+numCaja+") and (numabono = "+numAbono+") and (numservicio = "+numServicio+") and (fecha = '"+fecha+"')");
				loteSentenciasCR.addBatch(sentenciaSql);
				sentenciaSql = new String("update pagodeabonos set regactualizado='" + Sesion.SI + "' where (numtienda = "+numTienda+") and (numcaja = "+numCaja+") and (numabono = "+numAbono+") and (numservicio = "+numServicio+") and (fecha = '"+fecha+"')");
				loteSentenciasCR.addBatch(sentenciaSql);
				
				// Ejecuto lote de sentencias en el DBMS del Servidor y de la CR	
				Connection connSrv = Conexiones.getConexion(false);
				Connection connCR = Conexiones.getConexion(true);
				try {
					connCR.setAutoCommit(false);
					connSrv.setAutoCommit(false);
					connSrv.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
					connCR.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
					Conexiones.ejecutarLoteSentencias(loteSentenciasSrv, false, false);
					Conexiones.ejecutarLoteSentencias(loteSentenciasCR, true, false);
					connCR.commit();
					connSrv.commit();
				} catch (Exception e) {
					e.printStackTrace();
					connCR.rollback();
					connSrv.rollback();
					throw (new BaseDeDatosExcepcion("Error en sentencia por lote al sincronizar abonos de apartados/P.Especial"));
				} finally {
					connCR.setAutoCommit(true);
					connSrv.setAutoCommit(true);
				}				
				loteSentenciasSrv.clearBatch();
				loteSentenciasCR.clearBatch();
			}
		} catch (BatchUpdateException e) {
			throw (new BaseDeDatosExcepcion("Error en sentencia por lote al sincronizar transacciones de abono"));
		} catch (SQLException e) {
			throw (new BaseDeDatosExcepcion("Error en sentencia SQL al sincronizar transacciones de abono"));
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error con la BD al sincronizar transacciones de abono"));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de conexión con BD al sincronizar transacciones de abono"));
		}  catch (Throwable t) {
		} finally{
			try {
				if(loteSentenciasCR != null)
					loteSentenciasCR.close();
			} catch (SQLException e1) {
			}
			try {
				if(loteSentenciasSrv != null)
					loteSentenciasSrv.close();
			} catch (SQLException e1) {
			}	
		}
	}
	
	/**
	 * Método syncAnulacionAbonos
	 * 		Sincroniza las anulaciones de abonos hacia el srvidor central. Tambien actualiza el estado del 
	 *      abono en la tabla "TransaccionAbono" para colocarlo en estado de "Eliminado"
	 * @param numAnulacionAbonos - Lista de los abonos anulados que no han sido actualizados en el servidor Central
	 * @throws BaseDeDatosExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se eliminaron variables sin uso
	* Fecha: agosto 2011
	*/
	public static synchronized void syncAnulacionAbonos(ResultSet numAnulacionAbonos) throws BaseDeDatosExcepcion{
		ResultSet abonos;
		String sentenciaSql;
		Statement loteSentenciasSrv = null;
		Statement loteSentenciasCR = null;


		try {
			int numAbono = 0;
			int numServicio = 0;
			loteSentenciasSrv = Conexiones.crearSentencia(false);
			loteSentenciasCR = Conexiones.crearSentencia(true);

			while(numAnulacionAbonos.next()){
				//AQUI SE VERIFICA SI EL ABONO YA ESTÁ ARRIBA EN EL SERV CENTRAL PARA VER SI SE ACTUALIZA EL REGISTRO Y EL ESTADO DE TRANSACCIONABONO
				numAbono = numAnulacionAbonos.getInt("anulaciondeabonos.numabono");
				numServicio = numAnulacionAbonos.getInt("anulaciondeabonos.numservicio");
				//System.out.println("Sincronizando abono: " + numAbono + " del servicio: " + numServicio);
				 
				// Obtengo información de la CR de la transacción actual
				abonos = getAnulacionAbono(true, numAbono, numServicio);
				Short numTienda = null;
				Short numCaja = null;
				Date fechaAbono = null;
				int numAbonoAnulado = 0;
				Date fechaAbonoAnulado = null;
				try {
					numTienda = new Short(abonos.getShort("anulaciondeabonos.numtienda"));
					numCaja = new Short(abonos.getShort("anulaciondeabonos.numcaja"));
					fechaAbono = abonos.getDate("anulaciondeabonos.fechaabono");
					numAbonoAnulado = abonos.getInt("anulaciondeabonos.numabonoanulado");
					fechaAbonoAnulado = abonos.getDate("anulaciondeabonos.fechaabonoanulado");
					String regActualizado = abonos.getString("anulaciondeabonos.regactualizado");
					sentenciaSql = new String("insert into " +Sesion.getDbEsquema()+".anulaciondeabonos "
							+ "values (" + numTienda + ", " + numCaja + ", " + numAbono + ", '" + fechaAbono + "', " 
							+ numAbonoAnulado + ", '" + fechaAbonoAnulado + "', " + numServicio + ", '"
							+ regActualizado + "')");

					loteSentenciasSrv.addBatch(sentenciaSql);
				} finally {
					abonos.close();
				}
				
				//Hacemos el query del update de la tabla "Transaccionabono"  para actualizar el estado del abono
				sentenciaSql = new String("update "+Sesion.getDbEsquema()+".transaccionabono set estadoabono='" + Sesion.ABONO_ANULADO + "' where (numtienda = "+numTienda+") and (numcaja = "+numCaja+") and (numabono = "+numAbono+") and (numservicio = "+numServicio+") and (fecha = '"+fechaAbono+"')");
				loteSentenciasSrv.addBatch(sentenciaSql);
				
				// Indico actualización de los registros de transacción en la CR
				sentenciaSql = new String("update "+Sesion.getDbEsquema()+".anulaciondeabonos set regactualizado='" + Sesion.SI + "' where (numtienda = "+numTienda+") and (numcaja = "+numCaja+") and (numabono = "+numAbono+") and (fechaabono = '"+fechaAbono+"') and (numabonoanulado = "+numAbonoAnulado+") and (fechaabonoanulado = '"+fechaAbonoAnulado+"') and (numservicio = "+numServicio+")");
				loteSentenciasCR.addBatch(sentenciaSql);
				
				// Ejecuto lote de sentencias en el DBMS del Servidor y de la CR	
				Connection connSrv = Conexiones.getConexion(false);
				Connection connCR = Conexiones.getConexion(true);
				try {
					connCR.setAutoCommit(false);
					connSrv.setAutoCommit(false);
					connSrv.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
					connCR.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
					Conexiones.ejecutarLoteSentencias(loteSentenciasSrv, false, false);
					Conexiones.ejecutarLoteSentencias(loteSentenciasCR, true, false);
					connCR.commit();
					connSrv.commit();
				} catch (Exception e) {
					e.printStackTrace();
					connCR.rollback();
					connSrv.rollback();
					throw (new BaseDeDatosExcepcion("Error en sentencia por lote al sincronizar anulación de abonos"));
				} finally {
					connCR.setAutoCommit(true);
					connSrv.setAutoCommit(true);
				}				
				loteSentenciasSrv.clearBatch();
				loteSentenciasCR.clearBatch();
			}
		} catch (BatchUpdateException e) {
			throw (new BaseDeDatosExcepcion("Error en sentencia por lote al sincronizar anulación de abonos"));
		} catch (SQLException e) {
			throw (new BaseDeDatosExcepcion("Error en sentencia SQL al sincronizar anulación de abonos"));
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error con la BD al sincronizar anulación de abonos"));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de conexión con BD al sincronizar anulación de abonos"));
		}  catch (Throwable t) {
		} finally{
			try {
				if(loteSentenciasCR != null)
					loteSentenciasCR.close();
			} catch (SQLException e1) {
			}
			try {
				if(loteSentenciasSrv != null)
					loteSentenciasSrv.close();
			} catch (SQLException e1) {
			}	
		}
	}
	
	/**
	 * Método syncProdCodigoExterno
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncProdCodigoExterno() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"codexterno"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "prodcodigoexterno", clave, true);
		BeansSincronizador.syncEntidad(Sesion.SINC_SERVTDA, entidad);//, BDOrigen, true);
	}

	/**
	 * Método syncProducto
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncProducto() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"codproducto"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "producto", clave, true);
		BeansSincronizador.syncEntidad(Sesion.SINC_SERVTDA, entidad);//, BDOrigen, true);
	}
	
	/**
	 * Método syncModulos
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncModulos() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"codmodulo"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "modulos", clave, true);
		BeansSincronizador.syncEntidad(Sesion.SINC_SERVTDA, entidad);//, BDOrigen, true);
	}

	/**
	 * Método syncFuncion
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncFuncion() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"codfuncion", "codmodulo"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "funcion", clave, true);
		BeansSincronizador.syncEntidad(Sesion.SINC_SERVTDA, entidad);//, BDOrigen, true);
	}

	/**
	 * Método syncPerfil
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncPerfil() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"codperfil"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "perfil", clave, true);
		BeansSincronizador.syncEntidad(Sesion.SINC_SERVTDA, entidad);//, BDOrigen, true);
	}

	/**
	 * Método syncFuncion
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncFuncionPerfil() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"codperfil", "codfuncion", "codmodulo"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "funcionperfil", clave, true);
		BeansSincronizador.syncEntidad(Sesion.SINC_SERVTDA, entidad);//, BDOrigen, true);
	}

	/**
	 * Método syncUsuario
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncUsuario(int direccion) throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"numtienda", "numficha"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "usuario", clave, true);
		BeansSincronizador.syncEntidad(direccion, entidad);//, BDOrigen, true);
	}

	/**
	 * Método syncAfiliado
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncAfiliado(int direccion) throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"codafiliado"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "afiliado", clave, true);
		BeansSincronizador.syncEntidad(direccion, entidad);//, BDOrigen, true);
	}
	
	/**
	 * Método syncDetalleAfiliado
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncDetalleAfiliado(int direccion) throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"numtienda", "codafiliado", "mensaje"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "detalleafiliado", clave, true);
		BeansSincronizador.syncEntidad(direccion, entidad);//, BDOrigen, true);
	}

	/**
	 * Método syncPromocion
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncPromociones() throws SQLException, BaseDeDatosExcepcion {
		//String[] clave = {"codpromocion", "tipopromocion"};
		//EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "promocion", clave, false, false);
		//BeansSincronizador.syncEntidadDown(entidad);
		ResultSet promociones = getPromociones(false);
		if (promociones != null)
			BeansSincronizador.syncPromociones(promociones);
	}
	
	/**
	 * Método syncDevolucionVenta
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncDevolucionVenta() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"numtiendadevolucion", "fechadevolucion","numcajadevolucion","numtransacciondev","numtiendaventa","fechaventa","numcajaventa","numtransaccionvta"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "devolucionventa", clave, false,true);
		BeansSincronizador.syncEntidad(Sesion.SINC_SERVCENTRAL, entidad);
	}
	
	/**
	 * Método syncAnulacionAbonos
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	/*public static void syncAnulacionAbonos() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"numtienda", "numcaja","numabono","fechaabono","numabonoanulado","fechaabonoanulado","numservicio"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "anulaciondeabonos", clave, false,true);
		BeansSincronizador.syncEntidad(Sesion.SINC_SERVCENTRAL, entidad);
	}
	
	/**
	 * Método syncServicios
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncServicios() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"numtienda", "codtiposervicio","numservicio","fecha"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "servicio", clave, false,true);
		BeansSincronizador.syncEntidad(Sesion.SINC_SERVCENTRAL, entidad);
	}
	
	/**
	 * Método syncDetallesServicios
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncDetallesServicios() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"numtienda", "codtiposervicio","numservicio","fecha","codproducto","codcondicionventa","correlativoitem"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "detalleservicio", clave, false,true);
		BeansSincronizador.syncEntidad(Sesion.SINC_SERVCENTRAL, entidad);
	}
	
	
	/**
	 * Método syncEntidadCR
	 * 		Sincroniza la entidad indicada desde el servidor central de la empresa (BD origen) a la BD 
	 * local (BD del servidor de tienda).
	 * @param entidad - Nombre de la entidad del esquema de BD del sistema. Considera el mismo diseño de 
	 * 	datos para el DBMS origen y el destino a sincronizar.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public synchronized static void syncEntidadServCentralServTda(String entidad) throws SQLException, BaseDeDatosExcepcion {
		entidad = new String(entidad.trim().toLowerCase());
		long inicio = System.currentTimeMillis();

		System.out.print("Sincronizando Entidad " + Sesion.getDbEsquema() + "." + entidad);
		if(entidad.equals("producto")) BeansSincronizador.syncProducto();
		//llamada a funcion nueva CRM Transaccion Afiliado wdiaz
		else if(entidad.equals("transaccionafiliadocrm")) BeansSincronizador.syncTransaccionafiliadocrm();
		//fin
//		llamada a funcion nueva CRM Solicitud de Clentes wdiaz
		else if(entidad.equals("solicitudcliente")) BeansSincronizador.syncSolicitudcliente();
		//fin
		else if(entidad.equals("prodcodigoexterno")) BeansSincronizador.syncProdCodigoExterno();
		else if(entidad.equals("promocion")) BeansSincronizador.syncPromociones();
		else if(entidad.equals("modulos")) BeansSincronizador.syncModulos();
		else if(entidad.equals("funcion")) BeansSincronizador.syncFuncion();
		else if(entidad.equals("perfil")) BeansSincronizador.syncPerfil();
		else if(entidad.equals("funcionperfil")) BeansSincronizador.syncFuncionPerfil();
		else if(entidad.equals("usuario")) BeansSincronizador.syncUsuario(Sesion.SINC_SERVTDA);
		else if(entidad.equals("afiliado")) BeansSincronizador.syncAfiliado(Sesion.SINC_SERVTDA);
		else if(entidad.equals("detalleafiliado")) BeansSincronizador.syncDetalleAfiliado(Sesion.SINC_SERVTDA);
		else if(entidad.equals("departamento")) BeansSincronizador.syncDepartamento();

		else if(entidad.equals("banco")) BeansSincronizador.syncBanco();
		else if(entidad.equals("caja")) BeansSincronizador.syncCaja();
		else if(entidad.equals("cargo")) BeansSincronizador.syncCargo();
		else if(entidad.equals("condicionventa")) BeansSincronizador.syncCondicionventa();
		else if(entidad.equals("estadodecaja")) BeansSincronizador.syncEstadodecaja();
		else if(entidad.equals("formadepago")) BeansSincronizador.syncFormadepago();
		else if(entidad.equals("funcionmetodos")) BeansSincronizador.syncFuncionmetodos();
		else if(entidad.equals("impuestoregion")) BeansSincronizador.syncImpuestoregion();
		else if(entidad.equals("lineaseccion")) BeansSincronizador.syncLineaseccion();
		else if(entidad.equals("maquinadeestado")) BeansSincronizador.syncMaquinadeestado();
		else if(entidad.equals("metodos")) BeansSincronizador.syncMetodos();
		else if(entidad.equals("region")) BeansSincronizador.syncRegion();
		else if(entidad.equals("tipocaptura")) BeansSincronizador.syncTipocaptura();
		else if(entidad.equals("tiposervicio")) BeansSincronizador.syncTiposervicio();
		/**
		 * IROJAS, 04/08/2009. Sincronización de tipos de apartados especiales.
		 * Requerimiento nuevo
		 **/
		else if(entidad.equals("tipoapartado")) BeansSincronizador.syncTipoApartado();
		else if(entidad.equals("unidaddeventa")) BeansSincronizador.syncUnidaddeventa();
		else if(entidad.equals("")) BeansSincronizador.syncDepartamento();

		else if(entidad.equals("tipoeventolistaregalos")) BeansSincronizador.syncTipoeventolistaregalos();
		else if(entidad.equals("servidortienda")) BeansSincronizador.syncServidortienda();
		
		//IROJAS: agregada tabla catdep necesaria para el módulo de promociones 05/03/2009
		else if(entidad.equals("catdep")) BeansSincronizador.syncCatDep();

		//GMARTINELLI: tablas para modulo Bonos Regalo Electronico
		else if(entidad.equals("br_opcionhabilitada")) BeansSincronizador.syncBROpcionHabilitada();
		else if(entidad.equals("br_condiciones")) BeansSincronizador.syncBRCondiciones();
		else if(entidad.equals("br_promocion")) BeansSincronizador.syncBRPromocion();

		long fin = System.currentTimeMillis();
		System.out.println("  ----->  Finalizada Sincronización. Tardó " + (fin - inicio) + " MiliSegs.");
	}

	/**
	 * Método syncDepartamento
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncDepartamento() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"coddepartamento"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "departamento", clave, false, false);
		BeansSincronizador.syncEntidadBase(entidad, true);
	}

	/**
	 * Método syncBanco
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncBanco() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"codbanco"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "banco", clave, false, false);
		BeansSincronizador.syncEntidadBase(entidad, true);
	}
	
	/**
	 * Método syncCargo
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncCargo() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"codcargo"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "cargo", clave, false, false);
		BeansSincronizador.syncEntidadBase(entidad, true);
	}
	
	/**
	 * Método syncCaja
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncCaja() throws SQLException, BaseDeDatosExcepcion {
		ResultSet resultRemoto = null;
		ResultSet resultLocal = null;
		try {
			String sentenciaSql = "select numtienda, numcaja, nivelauditoria, versionsistema, modelo, ipcaja, fechaipcaja from caja where numtienda = " + Sesion.getNumeroTda();
			resultRemoto = Conexiones.realizarConsulta(sentenciaSql, false, false);
			resultRemoto.beforeFirst();
			while (resultRemoto.next()) {
				String sentenciaLocal = "select * from caja where numtienda = " + Sesion.getNumeroTda() + " and numcaja = " + resultRemoto.getInt("numcaja");
				String sentenciaSync = "";
				resultLocal = Conexiones.realizarConsulta(sentenciaLocal, true, false);
				if (resultLocal.first()) {
					sentenciaSync = "update caja set nivelauditoria = '" + resultRemoto.getString("nivelauditoria") 
								+ "', versionsistema = '" + resultRemoto.getString("versionsistema") + "', modelo = '"
								+ resultRemoto.getString("modelo") + "', ipcaja = '" + resultRemoto.getString("ipcaja")
								+ "', fechaipcaja = '" + resultRemoto.getTimestamp("fechaipcaja") + "' where numtienda = "
								+ Sesion.getNumeroTda() + " and numcaja = " + resultRemoto.getInt("numcaja");
				} else {
					sentenciaSync = "insert into caja (numtienda, numcaja, idestadocaja, nivelauditoria, versionsistema, modelo, ipcaja, fechaipcaja)"
								+ " values (" + resultRemoto.getInt("numtienda") + ", " + resultRemoto.getInt("numcaja") + ", 1, "
								+ "'" + resultRemoto.getString("nivelauditoria") + "', '" + resultRemoto.getString("versionsistema") + "', '"
								+ resultRemoto.getString("modelo") + "', '" + resultRemoto.getString("ipcaja")
								+ "', '" + resultRemoto.getTimestamp("fechaipcaja") + "')";
				}
				//System.out.println(sentenciaSync);
				Conexiones.realizarSentencia(sentenciaSync, true);
			}
		} catch(ConexionExcepcion e1) {
			e1.printStackTrace();
		} finally {
			try {
				resultLocal.close();
			} catch (Exception e) {
			}
			try {
				resultRemoto.close();
			} catch (Exception e) {
			}
			resultLocal = null;
			resultRemoto = null;
		}
	}
	/**
	 * Método syncCondicionventa
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncCondicionventa() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"codcondicionventa"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "condicionventa", clave, false, false);
		BeansSincronizador.syncEntidadBase(entidad, true);
	}
	
	/**
	 * Método syncEstadodecaja
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncEstadodecaja() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"idestado"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "estadodecaja", clave, false, false);
		BeansSincronizador.syncEntidadBase(entidad, true);
	}
	
	/**
	 * Método syncFormadepago
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncFormadepago() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"codformadepago"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "formadepago", clave, false, false);
		BeansSincronizador.syncEntidadBase(entidad, true);
	}
	
	/**
	 * Método syncFuncionmetodos
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncFuncionmetodos() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"codmodulo", "codfuncion", "codmetodo"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "funcionmetodos", clave, false, false);
		BeansSincronizador.syncEntidadBase(entidad, true);
	}
	
	/**
	 * Método syncMaquinadeestado
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncMaquinadeestado() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"edoinicial", "codmetodo"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "maquinadeestado", clave, false, false);
		BeansSincronizador.syncEntidadBase(entidad, true);
	}
	
	/**
	 * Método syncImpuestoregion
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncImpuestoregion() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"codimpuesto", "codregion", "fechaemision"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "impuestoregion", clave, false, false);
		BeansSincronizador.syncEntidadBase(entidad, true);
	}
	
	/**
	 * Método syncLineaseccion
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncLineaseccion() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"codseccion", "coddepartamento"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "lineaseccion", clave, false, false);
		BeansSincronizador.syncEntidadBase(entidad, true);
	}
	
	/**
	 * Método syncMetodos
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncMetodos() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"codmetodo"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "metodos", clave, false, false);
		BeansSincronizador.syncEntidadBase(entidad, true);
	}
	
	/**
	 * Método syncRegion
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncRegion() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"codregion"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "region", clave, false, false);
		BeansSincronizador.syncEntidadBase(entidad, true);
	}
	
	/**
	 * Método syncTipocaptura
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncTipocaptura() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"codtipocaptura"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "tipocaptura", clave, false, false);
		BeansSincronizador.syncEntidadBase(entidad, true);
	}
	
	/**
	 * Método syncTiposervicio
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncTiposervicio() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"codtiposervicio"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "tiposervicio", clave, false, false);
		BeansSincronizador.syncEntidadBase(entidad, false);
	}

	/**
	 * Método syncTipoApartado
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	/**
	 * IROJAS, 04/08/2009. Sincronización de tipos de apartados especiales.
	 * Requerimiento nuevo
	 **/
	public static void syncTipoApartado() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"codigo"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "tipoapartado", clave, false, false);
		BeansSincronizador.syncEntidadBase(entidad, false);
	}
	
	/**
	 * Método syncUnidaddeventa
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncUnidaddeventa() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"codunidadventa"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "unidaddeventa", clave, false, false);
		BeansSincronizador.syncEntidadBase(entidad, true);
	}

	/**
	 * Método getPromociones
	 * 		Selecciona todos los registros de las promociones que no esten vencidas, sin importar
	 * si estan activas.
	 * @param local - Indicador de la conexión de BD a utilizar: Servidor o Local. 
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql efectuada. 
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getPromociones(boolean local) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		String sentenciaSql = "";
		boolean actualizable;

		SimpleDateFormat fechaActual = new SimpleDateFormat("yyyy-MM-dd");
		String fechaActualString = fechaActual.format(Sesion.getFechaSistema());

		sentenciaSql = new String("select codpromocion, tipopromocion from promocion where fechafinaliza >= '" + fechaActualString + "'");

		actualizable = false;

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, actualizable);
		} catch (BaseDeDatosExcepcion e) {
			System.out.print(" ---> Error de Script.");
		} catch (ConexionExcepcion e) {
			System.out.print(" ---> Falla conexión con el Servidor BD");
		}
		return resultado;
	}

	/**
	 * Método getPromocion
	 * 		Selecciona la información correspondiente a la promoción indicada. 
	 * @param local - Indicador de la conexión de BD a utilizar: Servidor o Local.
	 * @param codPromocion - Promoción de la que se quiere recuperar el registro de cabecera.
	 * @param tipoPromocion - Tipo de promoción de la que se quiere recuperar el registro de cabecera. 
	 * @return ResultSet - Retorna el registro correspondiente a la transacción.
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getPromocion(boolean local, int codPromocion, char tipoPromocion) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		String sentenciaSql = new String("select * from promocion where (promocion.codpromocion = "+ codPromocion +") and (promocion.tipopromocion = '"+ tipoPromocion +"')");
		try {
			boolean actualizable = true;
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, actualizable);
			resultado.first();
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de BD al realizar sentencia " + sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de BD al realizar sentencia " + sentenciaSql, e));
		} catch (SQLException e) {
			try {
				resultado.close();
			} catch (SQLException e1) {
			}
			throw (new BaseDeDatosExcepcion("Error de BD al realizar sentencia " + sentenciaSql, e));
		}
		return resultado;
	}

	/**
	 * Método getDetallePromocion
	 * 		Selecciona todos los detalles de productos asignados a la promoción indicada.
	 * @param local - Indicador de la conexión de BD a utilizar: Servidor o Local. 
	 * @param promocion - Código de la promoción a sincronizar.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql efectuada. 
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getDetallePromocion(boolean local, int promocion) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		String sentenciaSql = "";
		if (!local)
			sentenciaSql = new String("select detallepromocion.codpromocion, detallepromocion.numdetalle, " +
			"detallepromocion.numcupon, detallepromocion.coddepartamento, detallepromocion.codlineaseccion, " +
			"detallepromocion.codproducto, detallepromocion.porcentajedescuento, detallepromocion.preciofinal " +
			"from promocion inner join detallepromocion on (promocion.codpromocion = detallepromocion.codpromocion)" + 
			" where (detallepromocion.codpromocion = "+ promocion+" and codtienda = " + Sesion.getTienda().getNumero() + ")");
		else
			sentenciaSql = new String("select detallepromocion.* from promocion inner join detallepromocion on (promocion.codpromocion = detallepromocion.codpromocion)" + 
			" where (detallepromocion.codpromocion = "+ promocion+")");

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, true);
			resultado.beforeFirst();
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de BD al realizar sentencia " + sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de Conexión al realizar sentencia " + sentenciaSql, e));
		} catch (SQLException e) {
			try {
				resultado.close();
			} catch (SQLException e1) {
			}
			throw (new BaseDeDatosExcepcion("Error de SQL al realizar sentencia " + sentenciaSql, e));
		}
		return resultado;
	}
	
	/**
	 * Método actualizarPlanificador
	 * 		Método que sincroniza a la Caja Registradora todas las entidades 
	 * variantes tanto en el Servidor como en la CR que son fundamentales para 
	 * el adecuado funcionamiento del sistema.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void actualizarPlanificador(EntidadBD entidad, boolean falla, String destino) throws SQLException, BaseDeDatosExcepcion {
		Calendar fecha;
		fecha = Calendar.getInstance();
		Timestamp actualizacion = new Timestamp(fecha.getTime().getTime());
		String sentenciaSql = new String("");
		ResultSet consulta;		
		try {
			sentenciaSql = new String("select count(*) as existe from "+entidad.getEsquema()+".planificador where entidad='"+entidad.getNombre().toLowerCase()+"' and destino = '"+ destino + "'");
			consulta = Conexiones.realizarConsulta(sentenciaSql, true, false);
			try {
				if(consulta.getInt("existe") > 0)
					if(falla) sentenciaSql = new String("update "+entidad.getEsquema()+".planificador set fallasincronizador='" + Sesion.SI + "' where entidad='"+entidad.getNombre().toLowerCase()+"' and destino = '" + destino + "'");
					else sentenciaSql = new String("update "+entidad.getEsquema()+".planificador set actualizacion='"+actualizacion+"', fallasincronizador='N' where entidad='"+entidad.getNombre().toLowerCase()+"' and destino = '" + destino + "'");
				else
					if(falla) sentenciaSql = new String("insert "+entidad.getEsquema()+".planificador "+"set numtienda=0, numcaja=0, entidad='"+entidad.getNombre().toLowerCase()+"', actualizacion='00000000000000', fallasincronizador='" + Sesion.SI + "', destino = '" + destino + "'"); 
					else sentenciaSql = new String("insert "+entidad.getEsquema()+".planificador "+"set numtienda=0, numcaja=0, entidad='"+entidad.getNombre().toLowerCase()+"', actualizacion='"+actualizacion+"', fallasincronizador='N', destino = '" + destino + "'");
			} finally {
				consulta.close();
			}
			Conexiones.realizarSentencia(sentenciaSql, true);
		} catch (BaseDeDatosExcepcion e) {
//			e.printStackTrace();
		} catch (ConexionExcepcion e) {
//			e.printStackTrace();
		}
	}
	
	/**
	 * Método syncPromociones
	 * 		Sincroniza las promociones (cabecera y detalle de productos) hacia el servidor de la Tienda
	 * @param codPromociones - Lista de los códigos correspondientes a las promociones del servidor, 
	 * sin considerar fecha del sistema.
	 * @throws BaseDeDatosExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	public static void syncPromociones(ResultSet codPromociones) throws BaseDeDatosExcepcion {
		ResultSet detalleProductos, promociones;
		ArrayList <ArrayList<Object>>registros;
		ArrayList <Object>registro;
		String sentenciaSql;
		String[] xLoteSentencias;
		Statement loteSentenciasCR = null;
		StringBuffer xCampos, xValores;
		boolean nuevoReg = false;

		try {
			int codPromocion = 0;
			char tipoPromocion = ' ';
			loteSentenciasCR = Conexiones.crearSentencia(true);
			//HashMap criterioClave = new HashMap();
			codPromociones.beforeFirst();
			while(codPromociones.next()){
				nuevoReg = false;
				codPromocion = codPromociones.getInt("codpromocion");
				tipoPromocion = codPromociones.getString("tipopromocion").charAt(0);
				 
				// Obtenemos la información de la promocion en la BD Remota
				promociones = getPromocion(false, codPromocion, tipoPromocion);
				
				// Observamos si existe o no la promocion en la BD Local
				nuevoReg = !getPromocion(true, codPromocion, tipoPromocion).first();

				int numColumnas = 0;
				try {
					xCampos = new StringBuffer("");
					xValores = new StringBuffer("");
					if (nuevoReg) {
						numColumnas = promociones.getMetaData().getColumnCount();
						for (int i=0; i<numColumnas; i++){
							xCampos.append(promociones.getMetaData().getColumnName(i+1));
							xValores.append("?");
							if(i+1 < numColumnas){
								xCampos.append(", ");
								xValores.append(", ");
							}
						}
						sentenciaSql = new String("insert into promocion ("+xCampos+") values ("+xValores+")");
					} else {
						numColumnas = promociones.getMetaData().getColumnCount() - 2;
						for (int i=0; i<numColumnas; i++){
							xCampos.append(promociones.getMetaData().getColumnName(i+3) + " = ?");
							if(i+1 < numColumnas)
								xCampos.append(", ");
						}
						sentenciaSql = new String("update promocion set " + xCampos + " where promocion.codpromocion = "+ codPromocion +" and promocion.tipopromocion = '"+ tipoPromocion +"'");
					}

					//promociones.first();
					//criterioClave.put("codPromocion", promociones.getObject("codpromocion"));
					//criterioClave.put("tipoPromocion", promociones.getObject("tipopromocion"));
					//promociones.beforeFirst();
					
					registros = new ArrayList<ArrayList<Object>>();
					if(promociones.first()){
						registro = new ArrayList<Object>();
						if (nuevoReg) {
							for (int i=0; i<numColumnas; i++)
								registro.add(promociones.getObject(promociones.getMetaData().getColumnName(i+1).toLowerCase()));
							registros.add(registro);
						} else {
							for (int i=0; i<numColumnas; i++) {
								registro.add(promociones.getObject(promociones.getMetaData().getColumnName(i+3).toLowerCase()));
							}
							registros.add(registro);
						}
					}	
				} finally {
					promociones.close();
				}

				xLoteSentencias = Conexiones.crearLoteSentencias(sentenciaSql, registros, true);
				for (int i=0; i<xLoteSentencias.length; i++){
					loteSentenciasCR.addBatch(xLoteSentencias[i]);
//					System.out.println(xLoteSentencias[i]);
				}
	
				// Obtengo el detalle de renglones de la promoción
				registros = new ArrayList<ArrayList<Object>>();
				ArrayList <ArrayList<Integer>>detallesProductosLocal = new ArrayList<ArrayList<Integer>>();
				detalleProductos = BeansSincronizador.getDetallePromocion(false, codPromocion);
				detallesProductosLocal = obtenerDetalles(BeansSincronizador.getDetallePromocion(true, codPromocion));
				try {
					detalleProductos.beforeFirst();
					// Para cada Detalle de Promocion
					while (detalleProductos.next()) {
						xCampos = new StringBuffer("");
						xValores = new StringBuffer("");
						registros.clear();
						int numDetalle = detalleProductos.getInt("numdetalle");
						registro = new ArrayList<Object>();

						// Observamos si existe o no el Detalle de la promocion en la BD Local
						nuevoReg = !existeDetalle(codPromocion, numDetalle, detallesProductosLocal);//!getDetallePromocion(true, codPromocion, numDetalle).first();

						if (nuevoReg) {
							numColumnas = detalleProductos.getMetaData().getColumnCount();
							for (int i=0; i<numColumnas; i++){
								xCampos.append(detalleProductos.getMetaData().getColumnName(i+1));
								xValores.append("?");
								if(i+1 < numColumnas){
									xCampos.append(", ");
									xValores.append(", ");
								}
								registro.add(detalleProductos.getObject(detalleProductos.getMetaData().getColumnName(i+1).toLowerCase()));
							}
							sentenciaSql = new String("insert into detallepromocion ("+xCampos+") values ("+xValores+")");
						} else {
							numColumnas = detalleProductos.getMetaData().getColumnCount() - 2;
							for (int i=0; i<numColumnas; i++){
								xCampos.append(detalleProductos.getMetaData().getColumnName(i+3) + " = ?");
								if(i+1 < numColumnas)
									xCampos.append(", ");
								registro.add(detalleProductos.getObject(detalleProductos.getMetaData().getColumnName(i+3).toLowerCase()));
							}
							sentenciaSql = new String("update detallepromocion set " + xCampos + " where detallepromocion.codpromocion = "+ codPromocion + " and detallepromocion.numdetalle = " + numDetalle);
						}
						registros.add(registro);
						xLoteSentencias = Conexiones.crearLoteSentencias(sentenciaSql, registros, true);
						for (int i=0; i<xLoteSentencias.length; i++){
							loteSentenciasCR.addBatch(xLoteSentencias[i]);
//							System.out.println(xLoteSentencias[i]);
						}
					}
				} finally {
					detalleProductos.close();
				}
				
				// Ejecuto lote de sentencias en el DBMS del Servidor y de la CR
				try {			
					Conexiones.ejecutarLoteSentencias(loteSentenciasCR, true, true);
				} catch (Exception e) {
					System.out.println("\nError Sincronizando Promocion número: " + codPromocion);
				}
				loteSentenciasCR.clearBatch();
			}
		} catch (BatchUpdateException e) {
			throw (new BaseDeDatosExcepcion("Error en sentencia por lote al sincronizar Promociones"));
		} catch (SQLException e) {
			throw (new BaseDeDatosExcepcion("Error en sentencia SQL al sincronizar Promociones"));
		} catch (BaseDeDatosExcepcion e) {
//			e.printStackTrace();
			throw (new BaseDeDatosExcepcion("Error con la BD al sincronizar Promociones"));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de conexión con BD al sincronizar Promociones"));
		} finally{
			try {
				if(loteSentenciasCR != null)
					loteSentenciasCR.close();
			} catch (SQLException e1) {
			}
		}
	}


	/**
	 * @param codPromocion
	 * @param numDetalle
	 * @param detallesProductosLocal
	 * @return
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	private static boolean existeDetalle(int codPromocion, int numDetalle, ArrayList <ArrayList<Integer>>detallesProductosLocal) {
		boolean resultado = false;
		for (int i=0; i<detallesProductosLocal.size(); i++) {
			ArrayList <Integer>elemento = new ArrayList<Integer>();
			elemento.add(new Integer(codPromocion));
			elemento.add(new Integer(numDetalle));
			resultado = detallesProductosLocal.contains(elemento);
			if (resultado) break;
		}
		return resultado;
	}

	/**
	 * @param set
	 * @return
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	private static ArrayList <ArrayList<Integer>> obtenerDetalles(ResultSet detalles) {
		ArrayList <ArrayList<Integer>>result = new ArrayList<ArrayList<Integer>>();
		try {
			detalles.beforeFirst();
			while (detalles.next()) {
				ArrayList <Integer>regActual = new ArrayList<Integer>();
				regActual.add(new Integer(detalles.getInt("codpromocion")));
				regActual.add(new Integer(detalles.getInt("numdetalle")));
				result.add(regActual);
			}
		} catch (Exception e) {
//			e.printStackTrace();
			return null;
		}
		return result;
	}

	/**
	 * Método syncEntidad
	 * 		Sincroniza la entidad indicada utilizando la configuración de Hibernate en los archivos 
	 * *.cfg.xml y la estructura de la BD mapeada en los archivos *.hbm.xml transafiriendo la data 
	 * desde la Base de Datos Origen a la base de dato destino 
	 * 
	 * @param entidad - Entidad a ser Sincronizada
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se eliminaron variables sin uso y se parametrizó List
	* Fecha: agosto 2011
	*/
	public synchronized static void syncEntidad(int direccion, EntidadBD entidad){
		Session sessionServOrigen, sessionServDestino;
		Transaction tx = null, txO = null;
		ResultSet resultado;
		int vanCuantos = 0, numMaxRegistros = 1000;
		String sentenciaSqlAct = null, mensaje;
		Timestamp recienteServOrigen, recienteServDestino, ultimaActualizacion = new Timestamp(0);
		Connection conOrigen = null, conDestino = null;
		
		try {
			
			// Obtenemos las conexiones necesarias para la sincronizacion
			conOrigen = Conexiones.getConexion(direccion==Sesion.SINC_SERVCENTRAL);
			conOrigen.setAutoCommit(false);
			conDestino = Conexiones.getConexion(direccion==Sesion.SINC_SERVTDA);
			conDestino.setAutoCommit(false);
			
			// Setting de Variables de control de sincronizacion
			//long inicio = System.currentTimeMillis(); // Inicio de la sincronizacion de la entidad
//			System.out.println("Verificando entidad -> " + entidad.getEsquema() + "." + entidad.getNombre() + ". Inicio: " + GregorianCalendar.getInstance());
			boolean diferenciaActualizacion = false, diferenciaRegistros = false;
			boolean conActualizacion = entidad.isActualizacion(); // Indica si la entidad posee el campo de actualizacion
			String entidadBD = new String("com.beco.colascr.transferencias.sincronizador.hibernate."+entidad.getNombre().toUpperCase().charAt(0)+entidad.getNombre().substring(1)); 
			sessionServOrigen = ConexionHibernate.currentSession(direccion==Sesion.SINC_SERVCENTRAL, conOrigen);
			sessionServDestino = ConexionHibernate.currentSession(direccion==Sesion.SINC_SERVTDA, conDestino);

			try {
				// Si tenemos las conexiones activas realizamos la sincronización, si no, esperamos el proximo intervalo
				if(sessionServOrigen.isOpen() && sessionServDestino.isOpen()){
					Connection conexionOrigen = sessionServOrigen.connection();
					Connection conexionDestino = sessionServDestino.connection();
					Statement sentencia = null;

					String sentenciaHql = new String("from "+entidad.getNombre().toUpperCase().charAt(0)+entidad.getNombre().substring(1));
					String sentenciaSQL = new String("select {"+entidad.getNombre()+".*} from "+entidad.getEsquema()+"."+entidad.getNombre());
					String xSentenciaSql = new String("select "+sentenciaHql.toLowerCase().replaceFirst("from ", "count(*) as cuantosregistros from "+entidad.getEsquema()+"."));

					Calendar fecha = GregorianCalendar.getInstance();
					fecha.set(Calendar.YEAR, 1900);
					fecha.set(Calendar.DAY_OF_MONTH, 01);
					fecha.set(Calendar.MONTH, 01);
					fecha.set(Calendar.HOUR_OF_DAY, 12);
					fecha.set(Calendar.MINUTE, 00);
					fecha.set(Calendar.SECOND, 00);

					// Fecha de actualizacion del elemento mas reciente en el servidor de la empresa (BD Remota)
					recienteServOrigen = new Timestamp(fecha.getTime().getTime());

					// Fecha de actualizacion del elemento mas reciente en el servidor de la tienda (BD Local)
					recienteServDestino = new Timestamp(fecha.getTime().getTime());

					// Fecha de última sincronizacion 
					ultimaActualizacion = new Timestamp(fecha.getTime().getTime());

					if (conActualizacion) { // Si la entidad posee el campo "Actualizacion"
						sentenciaSqlAct = new String("select max(actualizacion) as masreciente from "+entidad.getEsquema()+"."+entidad.getNombre());
						try {
							// Obtenemos la fecha de actualizacion mas reciente en la BD Destino
							sentencia = conexionDestino.createStatement();
							resultado = sentencia.executeQuery(sentenciaSqlAct);
							try {
								resultado.next();
								if(resultado.getTimestamp("masreciente") != null)
									recienteServDestino = new Timestamp(resultado.getTimestamp("masreciente").getTime());
							} finally {
								try {
									resultado.close();
								} catch (Exception e) {}
								try {
									sentencia.close();
								} catch (Exception e) {}
							}

							// Obtenemos la fecha de la última actualizacion en la BD Local (Fecha en el Planificador)
							xSentenciaSql = direccion == Sesion.SINC_SERVTDA
											? new String("select max(actualizacion) as ultimaactualizacion from " + Sesion.getDbEsquema() + ".planificador where entidad = '"+entidad.getNombre()+"' and fallasincronizador='" + Sesion.NO + "' and destino = '" + Sesion.BD_LOCAL +"'") 
											: new String("select max(actualizacion) as ultimaactualizacion from " + Sesion.getDbEsquema() + ".planificador where entidad = '"+entidad.getNombre()+"' and fallasincronizador='" + Sesion.NO + "' and destino = '" + Sesion.BD_SERVIDOR +"'");
							sentencia = direccion == Sesion.SINC_SERVTDA 
										? conexionDestino.createStatement()
										: conexionOrigen.createStatement();
							resultado = sentencia.executeQuery(xSentenciaSql);
							try {
								resultado.next();
								if(resultado.getTimestamp("ultimaactualizacion") != null)
									ultimaActualizacion = resultado.getTimestamp("ultimaactualizacion");
								else 
									ultimaActualizacion = new Timestamp(0);
							} finally {
								resultado.close();
							}
						} finally {
							sentencia.close();
						}

						try {
							// Obtenemos la fecha de actualizacion mas reciente en la BD Origen
							sentencia = conexionOrigen.createStatement();
							resultado = sentencia.executeQuery(sentenciaSqlAct);
							try {
								resultado.next();
								if(resultado.getTimestamp("masreciente") != null)
									recienteServOrigen = new Timestamp(resultado.getTimestamp("masreciente").getTime());
							} finally {
								resultado.close();
							}
						} finally {
							sentencia.close();
						}

						SimpleDateFormat formato = new SimpleDateFormat("yyyyMMddhhmm00");
						recienteServOrigen.setNanos(0);
						recienteServDestino.setNanos(0);
						ultimaActualizacion.setNanos(0);
						diferenciaActualizacion = recienteServOrigen.after(recienteServDestino);// || recienteServOrigen.after(ultimaActualizacion);
						
						if(diferenciaActualizacion) { 
							mensaje = new String("Diferencia de actualización de registros de la entidad "+entidad.getEsquema()+"."+entidad.getNombre()+":: Origen -> "+recienteServOrigen+" / Destino -> "+recienteServDestino);
//							System.out.println(mensaje);
							Date ultimaFechaSync = /*ultimaActualizacion.before(recienteServDestino) ? new Date(ultimaActualizacion.getTime()) : */new Date(recienteServDestino.getTime());
							sentenciaHql = new String("from "+entidad.getNombre().toUpperCase().charAt(0)+entidad.getNombre().substring(1)+" where actualizacion >= '"+formato.format(ultimaFechaSync)+"'");
							sentenciaSQL = new String("select {"+entidad.getNombre()+".*} from "+entidad.getEsquema()+"."+entidad.getNombre()+" "+entidad.getNombre()+" where actualizacion >= '"+formato.format(ultimaFechaSync)+"' order by actualizacion asc");
							xSentenciaSql = new String("select "+sentenciaHql.toLowerCase().replaceFirst("from ", "count(*) as cuantosregistros from "+entidad.getEsquema()+"."));
						} else {
							// Chequeamos diferencia de registros
							int numRegLocal = 0, numRegRemoto = 0;
							
							try {
								sentencia = conexionDestino.createStatement();
								resultado = sentencia.executeQuery("select count(*) as numregistros from "+entidad.getEsquema()+"."+entidad.getNombre());
								try {
									resultado.next();
									numRegLocal = resultado.getInt("numregistros");
								} finally {
									resultado.close();
								}
					
							} finally {
								sentencia.close();
							}

							try {
								sentencia = conexionOrigen.createStatement();
								resultado = sentencia.executeQuery("select count(*) as numregistros from "+entidad.getEsquema()+"."+entidad.getNombre());
								try {
									resultado.next();
									numRegRemoto = resultado.getInt("numregistros");
								} finally {
									resultado.close();
								}
							} finally {
								sentencia.close();
							}
							diferenciaRegistros = numRegRemoto != numRegLocal;
							if (diferenciaRegistros) {
								mensaje = new String("Diferencia de número de registros de la entidad "+entidad.getEsquema()+"."+entidad.getNombre()+":: Servidor -> "+numRegRemoto+" / CR -> "+numRegLocal);
//								System.out.println(mensaje);
								Date ultimaFechaSync = /*ultimaActualizacion.before(recienteServDestino) ? new Date(ultimaActualizacion.getTime()) : */new Date(recienteServDestino.getTime());
								sentenciaHql = new String("from "+entidad.getNombre().toUpperCase().charAt(0)+entidad.getNombre().substring(1)+" where actualizacion >= '"+formato.format(ultimaFechaSync)+"'");
								sentenciaSQL = new String("select {"+entidad.getNombre()+".*} from "+entidad.getEsquema()+"."+entidad.getNombre()+" "+entidad.getNombre()+" where actualizacion >= '"+formato.format(ultimaFechaSync)+"' order by actualizacion asc");
								xSentenciaSql = new String("select "+sentenciaHql.toLowerCase().replaceFirst("from ", "count(*) as cuantosregistros from "+entidad.getEsquema()+"."));
							}
						}
					} else {
						// La entidad no posee el campo actualizacion.
						if (entidad.isMarca()) {// Utilizamos el campo RegActualizado
							String sentenciaSql = "select count(*) as existe from " +entidad.getEsquema()+"."+entidad.getNombre() + " where regactualizado = '" + Sesion.NO + "'";
							ResultSet rs = Conexiones.realizarConsulta(sentenciaSql, direccion==Sesion.SINC_SERVCENTRAL, false);
							try {
								if (rs.first()) {
									if (rs.getInt("existe") > 0) {
										diferenciaRegistros = true;
									}
								}
							} finally {
								rs.close();
								rs = null;
							}
	
							sentenciaHql = new String("from "+entidad.getNombre().toUpperCase().charAt(0)+entidad.getNombre().substring(1));
							sentenciaSQL = "select {"+entidad.getNombre()+".*} from "+entidad.getEsquema()+"."+entidad.getNombre()+" "+entidad.getNombre()+" where regactualizado = '" + Sesion.NO + "'";
							xSentenciaSql = "select "+sentenciaHql.toLowerCase().replaceFirst("from ", "count(*) as cuantosregistros from "+entidad.getEsquema()+".")+" where regactualizado = '" + Sesion.NO + "'";
						} else {
							// Bajamos todos los registros
							diferenciaRegistros = true;
						}
					}
					
					// Ya estan armados los SQLs. Iniciamos la sincronizacion de datos si hay diferencias
					if (diferenciaActualizacion || diferenciaRegistros) {
						int totalRegistros = 0;
						sentencia = null; resultado = null;
						try {
							try {
								sentencia = conexionOrigen.createStatement();
							} catch (SQLException e) {
								e.printStackTrace();
								throw new ConexionServidorExcepcion("No pudo establecerse conexion con el servidor" ,e);
							}
							resultado = sentencia.executeQuery(xSentenciaSql); 
							resultado.next();
							totalRegistros = resultado.getInt("cuantosregistros");
						} finally {
							if (resultado != null)
								resultado.close();
							if (sentencia != null)
								sentencia.close();
						}
						mensaje = new String("Registros a actualizar de la entidad "+entidad.getEsquema()+"."+entidad.getNombre()+" -> "+totalRegistros+" del Servidor de Tienda");
						
//						System.out.println("Recuperando  " + totalRegistros + " registros");
//						System.out.println("Sentencia SQL: " + sentenciaSQL);
						
						Query consulta = null;
						tx = sessionServDestino.beginTransaction();
						if (entidad.isMarca()) {
							txO = sessionServOrigen.beginTransaction();
						}
						//System.out.println("\n" + sentenciaSQL);
						consulta = sessionServOrigen.createSQLQuery(sentenciaSQL,entidad.getNombre(),Class.forName(entidadBD));
						consulta.setMaxResults(numMaxRegistros);
						while(vanCuantos <= totalRegistros){
							consulta.setFirstResult(vanCuantos);
							List<Object> registros = consulta.list();
							vanCuantos = vanCuantos + numMaxRegistros;
							for(int i=0; i<registros.size(); i++){
								Object registro = registros.get(i);
								try {
									sessionServDestino.replicate(registro, BeansSincronizador.modoReplicacion);
									/*if (entidad.isMarca()) {
										((EntidadMarcable)registro).setRegactualizado("S");
										sessionServOrigen.update(registro);
									}*/
								} catch (HibernateException e) {
									e.printStackTrace();
									System.out.println("Error al sincronizar entidad: " + registro.toString());
								}
							}
							tx.commit();
							if (entidad.isMarca()) {
								for(int i=0; i<registros.size(); i++){
									Object registro = registros.get(i);
									try {
										//sessionServDestino.replicate(registro, ReplicationMode.LATEST_VERSION);
										//if (entidad.isMarca()) {
										((EntidadMarcable)registro).setRegactualizado("S");
										sessionServOrigen.update(registro);
										//}
									} catch (HibernateException e) {
										e.printStackTrace();
										System.out.println("Error al sincronizar entidad: " + registro.toString());
									}
								}

								txO.commit();
							}
							sessionServOrigen.clear();
							sessionServDestino.clear();
						}
						/*tx.commit();
						if (entidad.isMarca()) {
							txO.commit();
						}*/
						sessionServOrigen.clear();
						sessionServDestino.clear();
						actualizarPlanificador(entidad, false, Sesion.BD_LOCAL);
					} else {
//						System.out.println("No hay Diferencias");
					}
					//long fin = System.currentTimeMillis();
//					System.out.println("Finalizada Sincronizacion :: Tardó " + ((fin - inicio) / 1000) + " segs. en sincronizar con Hibernate");
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (tx!=null) {
					tx.rollback();
					if (entidad.isMarca()&&txO!=null) {
						txO.rollback();
					}
					System.out.println("Transaccion en rollback. ");
				}
				try {
					actualizarPlanificador(entidad, true, Sesion.BD_LOCAL);
					mensaje = new String("Falla sincronización de la entidad "+entidad.getEsquema()+"."+entidad.getNombre()+" Servidor -> CR");
					System.out.println(mensaje);
				} catch (BaseDeDatosExcepcion e1) {
					System.out.println("Falla de BD en actualización del planificador.");
				} catch (SQLException e1) {
					System.out.println("Falla actualización del planificador.");
				}
			}
		} catch (JDBCException e) {
			e.printStackTrace();
			System.out.println(" ---> Falla conexión con el Servidor BD durante sincronización.");
		} catch (HibernateException e) {
			e.printStackTrace();
			System.out.println(" ---> Falla configuración de Hibernate.");
		} catch (Throwable t) {
			System.out.print(" ---> Falla conexión con el Servidor BD");
			t.printStackTrace();
		} finally {
			try {
				ConexionHibernate.closeSession(true);
				ConexionHibernate.closeSession(false);
			} catch (HibernateException e3) {
				e3.printStackTrace();
			}
			try {
				if (conDestino != null)
					conDestino.setAutoCommit(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				if (conOrigen != null)
					conOrigen.setAutoCommit(true);
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * Método syncEntidadBase
	 * 		Sincroniza los registros entre dos entidades con igual estructura
	 * segun diferencias en el número de registros. Si los registros existen los
	 * reemplaza.
	 * @param entidad
	 * @param reemplazar
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se eliminaron variables sin uso
	* Fecha: agosto 2011
	*/
	public synchronized static void syncEntidadBase(EntidadBD entidad, boolean reemplazar){
		String sentenciaSql;
		String mensajeError = new String(""), syncSentido = new String("");
		ResultSet consultaServCentral, consultaServTda, registrosOrigen = null, registro = null;
		int numRegistrosServCentral = 0, numRegistrosServTda = 0;
		StringBuffer criterio = new StringBuffer();
		StringBuffer actualizacion = new StringBuffer();
		StringBuffer xCampos, xValores;
		String[] claves = entidad.getClave();
		ArrayList <Object>valores = new ArrayList<Object>(claves.length);
		PreparedStatement sentenciaPreparada = null;
			
		try {
			boolean diferenciaRegistros = false;
			sentenciaSql = new String("select count(*) as numregistros from "+entidad.getEsquema()+"."+entidad.getNombre());
				
			consultaServCentral = Conexiones.realizarConsulta(sentenciaSql, false, false);
			consultaServTda = Conexiones.realizarConsulta(sentenciaSql, true, false);
			try {
				numRegistrosServCentral = consultaServCentral.getInt("numregistros");
				numRegistrosServTda = consultaServTda.getInt("numregistros");
				diferenciaRegistros = numRegistrosServCentral != numRegistrosServTda;
				if (entidad.isSolicitud())
				  sentenciaSql = new String("select * from "+entidad.getEsquema()+"."+entidad.getNombre()+" where numtienda="+ Sesion.getTienda().getNumero());
				else
				sentenciaSql = new String("select * from "+entidad.getEsquema()+"."+entidad.getNombre());
			} finally {
				consultaServCentral.close();
				consultaServTda.close();
				consultaServCentral = null;
				consultaServTda = null;
			}
	
			try {
				if (diferenciaRegistros || reemplazar){
					//if (diferenciaRegistros) sentenciaSql = new String("select * from "+entidad.getEsquema()+"."+entidad.getNombre());
					registrosOrigen = Conexiones.realizarConsulta(sentenciaSql, false, false);
					registrosOrigen.beforeFirst();
						
					//xSentenciaSql = sentenciaSql.replace('*', '%').replaceFirst("%", "count(*) as cuantosRegistros");
					//consultaServTda = Conexiones.realizarConsulta(xSentenciaSql, true, false);
					//registrosOrigen.beforeFirst();
					//int cuantosRegistros = 0;
					//try {
					//	cuantosRegistros = consultaServTda.getInt("cuantosRegistros"); 
					//} finally {
					//	consultaCR.close();
					//	consultaCR = null;
					//}
					//Auditoria.registrarAuditoria(mensaje, 'O');
	
					for (int i=0; i<claves.length; i++){
						criterio.append("("+entidad.getEsquema()+"."+entidad.getNombre()+"."+entidad.getClave()[i]+" = ?)");
						if(i+1 < claves.length) criterio.append(" and ");
					}
	
					//boolean bdDestino = BDOrigenLocal == true ? false:true;
					while(registrosOrigen.next()){
						actualizacion = new StringBuffer();
						xCampos = new StringBuffer();
						xValores = new StringBuffer();
						sentenciaSql = new String("select * from "+entidad.getEsquema()+"."+entidad.getNombre()+" where "+criterio);
						sentenciaPreparada = Conexiones.getConexion(true).prepareStatement(sentenciaSql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	
						for (int i=0; i<claves.length; i++){
							Object o = registrosOrigen.getObject(claves[i]);
							if (o instanceof String) {
								o = ((String)o).trim();
							}
							valores.add(i, o);
							sentenciaPreparada.setObject(i+1, valores.get(i));
						}
							
						sentenciaPreparada.executeQuery();
						registro = sentenciaPreparada.getResultSet();
							
						int numColumnas = registro.getMetaData().getColumnCount();
						if(registro.first()) {
							for (int i=0; i<numColumnas; i++){
								actualizacion.append(entidad.getEsquema()+"."+entidad.getNombre()+"."+registro.getMetaData().getColumnName(i+1)+" = ?");
								if(i+1 < numColumnas) actualizacion.append(", ");
							}
							actualizacion.insert(0, " set ");
							sentenciaSql = new String("update "+entidad.getEsquema()+"."+entidad.getNombre()+" "+actualizacion+" where "+criterio);
						}
						else{
							for (int i=0; i<numColumnas; i++){
								xCampos.append(registro.getMetaData().getColumnName(i+1));
								xValores.append("?");
								if(i+1 < numColumnas){
									xCampos.append(", ");
									xValores.append(", ");
								}
							}
							sentenciaSql = new String("insert into "+entidad.getEsquema()+"."+entidad.getNombre()+" ("+xCampos+") values ("+xValores+")");
						}
						registro.beforeFirst();
	
						sentenciaPreparada = Conexiones.getConexion(true).prepareStatement(sentenciaSql);
						boolean ejecutar = true;						
	
						for (int i=1; i<=numColumnas; i++){
							String nombreColumna = new String(registrosOrigen.getMetaData().getColumnName(i));
							if((registrosOrigen.getMetaData().isNullable(i) == ResultSetMetaData.columnNoNulls) && (registrosOrigen.getObject(nombreColumna) == null)){
								ejecutar = false;
								break;
							}	
						}
	
						if(ejecutar){
							for (int i=1; i<=numColumnas; i++){
								String nombreColumna = new String(registrosOrigen.getMetaData().getColumnName(i));
								sentenciaPreparada.setObject(i, registrosOrigen.getObject(nombreColumna));
							}
							ejecutar = true;
	
							if (registro.next()){
								for (int i=numColumnas+1; i<=numColumnas+claves.length; i++){
									sentenciaPreparada.setObject(i, registrosOrigen.getObject(claves[i-(numColumnas+1)]));
								}
							}
							registro.beforeFirst();
	
							if(ejecutar) { 
								if (entidad.getNombre().equals("formadepago"))
									System.out.println("***** SENTENCIA EJECUTADA ");
								sentenciaPreparada.execute();
							}
						}
					}
					actualizarPlanificador(entidad, false, "C");
				}
			} finally {
				if (registrosOrigen != null)
				{
					registrosOrigen.close();
					registrosOrigen = null;
				}
				if (registro != null) {
					registro.close();
					registro = null;
				}
			}
		} catch (BaseDeDatosExcepcion e) {
			e.printStackTrace();
			mensajeError = new String("Falla BD en sincronización de "+entidad.getEsquema()+"."+entidad.getNombre()+syncSentido);
			new BaseDeDatosExcepcion(mensajeError);
		} catch (ConexionExcepcion e) {
			e.printStackTrace();
			mensajeError = new String("Falla conexión en sincronización de "+entidad.getEsquema()+"."+entidad.getNombre()+syncSentido);
			new ConexionExcepcion(mensajeError);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				actualizarPlanificador(entidad, true, "C");
			} catch (BaseDeDatosExcepcion e1) {
			} catch (SQLException e1) {
			}
			mensajeError = new String("Falla sentencia SQL en sincronización de "+entidad.getEsquema()+"."+entidad.getNombre()+syncSentido);
			new SQLException(mensajeError);
		} catch (Exception e) {
			e.printStackTrace();
			mensajeError = new String("Falla desconocida en sincronización de "+entidad.getEsquema()+"."+entidad.getNombre()+syncSentido);
			new SQLException(mensajeError);
		} catch (Throwable t) {
		} finally{
			try {
				if(sentenciaPreparada != null)
					sentenciaPreparada.close();
			} catch (SQLException e1) {
			}
		}
	}
	
	/**
	 * Método convertirProductos
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
/*	public static void convertirProductos() {
		String linea;
		String s;
		int i = 1;
		BufferedReader in = null;
		String archivo = "prueba";
		StringTokenizer registro = null;
		String codProducto, descCorta, descLarga, refProv, 
			   marca, modelo, codDpto,codLinSec, desctoVtaEmpX,
			   codImp, indicaDesctoEmple, indicaDespachar, 
			   edoProduct, delimitador, actualizacion1, actualizacion2, sentenciaSQL, actualizacion;
		int codUnidadVta, cantVtaEmp;
		double costoLista, precioReg, desctoVtaEmp;
		SimpleDateFormat formatoActualizacion = new SimpleDateFormat("yyyyMMddhhmmss");
			
		try {
			// Archivo de Entrada 
			FileInputStream farchivo = new FileInputStream(archivo);
			in = new BufferedReader(new InputStreamReader(farchivo));
		} catch (Exception e) {
			//NO SE ENCONTRÖ EL ARCHIVO ESPECIFICADO
			e.printStackTrace();
			System.exit(0);
		}
			
		try {
			System.out.print("\n ***** Parseando productos ***** \n");
			while((s = in.readLine()) != null){
				try{					
					registro = new StringTokenizer(s,",", true);
						
					codProducto = ((String)registro.nextElement()).replace('"',' ').trim();
					delimitador = (String)registro.nextElement();
						
					descCorta = ((String)registro.nextElement()).replace('"',' ').trim();
					delimitador = (String)registro.nextElement();
						
					descLarga = ((String) registro.nextElement()).replace('"',' ').trim();
					if (descLarga.equals(",")) {
						descLarga = null;
					} 
					delimitador = (descLarga != null)?(String)registro.nextElement():",";
						
					codUnidadVta = Integer.parseInt(((String)registro.nextElement()).trim()); 
					delimitador = (String)registro.nextElement();
						
					refProv = ((String)registro.nextElement()).replace('"',' ').trim();
					if (refProv.equals(",")) {
						refProv = null;
					}
					delimitador = (refProv != null)?(String)registro.nextElement():",";
						
					marca = ((String)registro.nextElement()).replace('"',' ').trim();			
					if (marca.equals(",")) {
						marca = null;
					}
					delimitador = (marca != null)?(String)registro.nextElement():",";
						
					modelo = ((String)registro.nextElement()).replace('"',' ').trim();
					if (modelo.equals(",")) {
						modelo = null;
					}
					delimitador = (modelo != null)?(String)registro.nextElement():",";
						
					codDpto = ((String)registro.nextElement()).replace('"',' ').trim();
					delimitador = (String)registro.nextElement();
						
					codLinSec = ((String)registro.nextElement()).replace('"',' ').trim();
					delimitador = (String)registro.nextElement();
						
					costoLista = Double.parseDouble(((String)registro.nextElement()).trim());
					delimitador = (String)registro.nextElement();
						
					precioReg = Double.parseDouble(((String)registro.nextElement()).trim());
					delimitador = (String)registro.nextElement();
						
					codImp = ((String)registro.nextElement()).replace('"',' ').trim();
					delimitador = (String)registro.nextElement();
						
					cantVtaEmp = Integer.parseInt(((String)registro.nextElement()).trim());
					delimitador = (String)registro.nextElement();
						
					desctoVtaEmpX = ((String)registro.nextElement()).replace('"',' ').trim();					
					if (desctoVtaEmpX.equals(",")) {
						desctoVtaEmp = 0;
					} else {
						desctoVtaEmp = Double.parseDouble(desctoVtaEmpX);
					}
					delimitador = (desctoVtaEmp != 0)?(String)registro.nextElement():",";
						
					indicaDesctoEmple = ((String)registro.nextElement()).replace('"',' ').trim();
					delimitador = (String)registro.nextElement();
						
					indicaDespachar = ((String)registro.nextElement()).replace('"',' ').trim();
					if (indicaDespachar.equals(",")) {
						indicaDespachar = null;
					}
					delimitador = (indicaDespachar != null)?(String)registro.nextElement():",";
						
					edoProduct = ((String)registro.nextElement()).replace('"',' ').trim();
					delimitador = (String)registro.nextElement();
						
					actualizacion1 = ((String)registro.nextElement()).trim();
					actualizacion2 = actualizacion1.substring(1,11);
					actualizacion2 = actualizacion2 + " " + actualizacion1.substring(12,14) + ":" + actualizacion1.substring(15,17) + ":" +  actualizacion1.substring(18,26); 
					actualizacion = formatoActualizacion.format(Timestamp.valueOf(actualizacion2));
					
					//*****************************************			
					//SE ARMA LA SENTENCIA INSERT SI SE LANZA UNA EXCPECIÓN SE INTENTA EL UPDATE
					//*****************************************
					sentenciaSQL = "INSERT INTO " + Sesion.getDbEsquema() + ".producto VALUES(";
					sentenciaSQL = sentenciaSQL + "'" + codProducto + "',";
					sentenciaSQL = sentenciaSQL + "'" + descCorta + "',";
					if (descLarga == null) {
						sentenciaSQL = sentenciaSQL + descLarga + ",";
					} else {
						sentenciaSQL = sentenciaSQL + "'" + descLarga + "',";										  
					}
					sentenciaSQL = sentenciaSQL + codUnidadVta + ",";
					if (refProv == null) {
						sentenciaSQL = sentenciaSQL + refProv + ",";
					} else {
						sentenciaSQL = sentenciaSQL + "'" + refProv + "',";
					}
					if (marca == null) {
						sentenciaSQL = sentenciaSQL + marca + ",";
					} else {
						sentenciaSQL = sentenciaSQL + "'" + marca + "',";												
					}
					if (modelo == null) {
						sentenciaSQL = sentenciaSQL + modelo + ",";
					} else {
						sentenciaSQL = sentenciaSQL + "'" + modelo + "',";												
					}					
					sentenciaSQL = sentenciaSQL + "'" + codDpto + "',";
					sentenciaSQL = sentenciaSQL + "'" + codLinSec + "',";
					sentenciaSQL = sentenciaSQL + costoLista + ",";
					sentenciaSQL = sentenciaSQL + precioReg +  ",";
					sentenciaSQL = sentenciaSQL + "'" + codImp + "',";
					sentenciaSQL = sentenciaSQL + cantVtaEmp + ",";
					sentenciaSQL = sentenciaSQL + desctoVtaEmp + ",";
					sentenciaSQL = sentenciaSQL + "'" + indicaDesctoEmple + "',";
					if (indicaDespachar == null) {
						sentenciaSQL = sentenciaSQL + indicaDespachar + ",";
					} else {
						sentenciaSQL = sentenciaSQL + "'" + indicaDespachar + "',";
					}
					sentenciaSQL = sentenciaSQL + "'" + edoProduct + "',";
					sentenciaSQL = sentenciaSQL + actualizacion + ")";
					
					try {
						Conexiones.realizarSentencia(sentenciaSQL, true);
					} catch (BaseDeDatosExcepcion e) { //INTENTAMOS HACER EL UPDATE DEL REGISTRO
						sentenciaSQL = "UPDATE " + Sesion.getDbEsquema() + ".producto set ";
						sentenciaSQL = sentenciaSQL + "descripcioncorta = '" + descCorta + "', ";
						sentenciaSQL = sentenciaSQL + "descripcionlarga = '" + descLarga + "', ";
						sentenciaSQL = sentenciaSQL + "codunidadventa = " + codUnidadVta + ", ";
						sentenciaSQL = sentenciaSQL + "referenciaproveedor = '" + refProv + "', ";
						sentenciaSQL = sentenciaSQL + "marca = '" + marca + "', ";
						sentenciaSQL = sentenciaSQL + "modelo = '" + modelo + "', ";
						sentenciaSQL = sentenciaSQL + "coddepartamento = '" + codDpto + "', ";
						sentenciaSQL = sentenciaSQL + "codlineaseccion = '" + codLinSec + "', ";
						sentenciaSQL = sentenciaSQL + "costolista = " + costoLista + ", ";
						sentenciaSQL = sentenciaSQL + "precioregular = " + precioReg + ", ";
						sentenciaSQL = sentenciaSQL + "codimpuesto = '" + codImp + "', ";
						sentenciaSQL = sentenciaSQL + "cantidadventaempaque = " + cantVtaEmp + ", ";
						sentenciaSQL = sentenciaSQL + "desctoventaempaque = " + desctoVtaEmp + ", ";
						sentenciaSQL = sentenciaSQL + "indicadesctoempleado = '" + indicaDesctoEmple + "', ";
						sentenciaSQL = sentenciaSQL + "indicadespachar = '" + indicaDespachar + "', ";
						sentenciaSQL = sentenciaSQL + "estadoproducto = '" + edoProduct + "', ";
						sentenciaSQL = sentenciaSQL + "actualizacion = " + actualizacion;
						sentenciaSQL = sentenciaSQL + " where producto.codproducto = " + codProducto;
						
						try {
							Conexiones.realizarSentencia(sentenciaSQL, true);
						} catch (BaseDeDatosExcepcion eu) {
						} catch (ConexionExcepcion euc) {
						}
					} catch (ConexionExcepcion e) {
					}  
				} catch (Exception e1) {
					System.out.println("     ------- Falló en un producto!!");
				}				
			}
			in.close();
		}
		catch(java.io.IOException io){io.printStackTrace();}
		catch (Exception ex){ex.printStackTrace();}
	}*/
	
	/**
	 * Método migrarProductos
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void migrarArchivo(String path, String tabla) {
		String foreignKey = "SET FOREIGN_KEY_CHECKS = ";
		String ordenSQL = "LOAD DATA INFILE '" + path + "' REPLACE INTO TABLE " + tabla + " FIELDS TERMINATED BY '" + Sesion.sepCampo + "' OPTIONALLY ENCLOSED BY '" + Sesion.delCampo + "'";
		try {
			Conexiones.realizarSentencia(foreignKey + "0",true);
			//LeerArchivo.convertirLoadProducto(path, path + "1");
			Conexiones.realizarSentencia(ordenSQL,true);
			Conexiones.realizarSentencia(foreignKey + "1",true);
		} catch (ConexionExcepcion e) {
			e.printStackTrace();
		} catch (BaseDeDatosExcepcion e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método migrarArchivoPromocion
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void migrarArchivoPromocion(String path, String tabla) {
		String foreignKey = "SET FOREIGN_KEY_CHECKS = ";
		String ordenSQL = "LOAD DATA INFILE '" + path + "' REPLACE INTO TABLE " + tabla + " FIELDS TERMINATED BY '" + Sesion.sepCampo + "' OPTIONALLY ENCLOSED BY '" + Sesion.delCampo + "'";
		try {
			Conexiones.realizarSentencia(foreignKey + "0",true);
			LeerArchivo.convertirLoadPromocion(path, path + "1");
			Conexiones.realizarSentencia(ordenSQL,true);
			Conexiones.realizarSentencia(foreignKey + "1",true);
		} catch (ConexionExcepcion e) {
			e.printStackTrace();
		} catch (BaseDeDatosExcepcion e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método migrarArchivoDetPromocion
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void migrarArchivoDetPromocion(String path, String tabla) {
		String foreignKey = "SET FOREIGN_KEY_CHECKS = ";
		String ordenSQL = "LOAD DATA INFILE '" + path + "' REPLACE INTO TABLE " + tabla + " FIELDS TERMINATED BY '" + Sesion.sepCampo + "' OPTIONALLY ENCLOSED BY '" + Sesion.delCampo + "'";
		try {
			Conexiones.realizarSentencia(foreignKey + "0",true);
			LeerArchivo.convertirLoadDetPromocion(path, path + "1");
			Conexiones.realizarSentencia(ordenSQL,true);
			Conexiones.realizarSentencia(foreignKey + "1",true);
		} catch (ConexionExcepcion e) {
			e.printStackTrace();
		} catch (BaseDeDatosExcepcion e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método migrarArchivoDetPromocionExtendida
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void migrarArchivoDetPromocionExtendida(String path, String tabla) {
		String foreignKey = "SET FOREIGN_KEY_CHECKS = ";
		String ordenSQL = "LOAD DATA INFILE '" + path + "' REPLACE INTO TABLE " + tabla + " FIELDS TERMINATED BY '" + Sesion.sepCampo + "' OPTIONALLY ENCLOSED BY '" + Sesion.delCampo + "'";
		try {
			Conexiones.realizarSentencia(foreignKey + "0",true);
			LeerArchivo.convertirLoadDetPromocionExtendida(path, path + "1");
			Conexiones.realizarSentencia(ordenSQL,true);
			Conexiones.realizarSentencia(foreignKey + "1",true);
		} catch (ConexionExcepcion e) {
			e.printStackTrace();
		} catch (BaseDeDatosExcepcion e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método migrarArchivoDonacion
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void migrarArchivoDonacion (String path, String tabla) {
		String foreignKey = "SET FOREIGN_KEY_CHECKS = ";
		String ordenSQL = "LOAD DATA INFILE '" + path + "' REPLACE INTO TABLE " + tabla + " FIELDS TERMINATED BY '" + Sesion.sepCampo + "' OPTIONALLY ENCLOSED BY '" + Sesion.delCampo + "'";
		try {
			Conexiones.realizarSentencia(foreignKey + "0",true);
			LeerArchivo.convertirLoadDonacion(path, path + "1");
			Conexiones.realizarSentencia(ordenSQL,true);
			Conexiones.realizarSentencia(foreignKey + "1",true);
		} catch (ConexionExcepcion e) {
			e.printStackTrace();
		} catch (BaseDeDatosExcepcion e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método migrarArchivoTransaccionPremiada
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void migrarArchivoTransaccionPremiada (String path, String tabla) {
		String foreignKey = "SET FOREIGN_KEY_CHECKS = ";
		String ordenSQL = "LOAD DATA INFILE '" + path + "' REPLACE INTO TABLE " + tabla + " FIELDS TERMINATED BY '" + Sesion.sepCampo + "' OPTIONALLY ENCLOSED BY '" + Sesion.delCampo + "'";
		try {
			Conexiones.realizarSentencia(foreignKey + "0",true);
			LeerArchivo.convertirLoadTransaccionPremiada(path, path + "1");
			Conexiones.realizarSentencia(ordenSQL,true);
			Conexiones.realizarSentencia(foreignKey + "1",true);
		} catch (ConexionExcepcion e) {
			e.printStackTrace();
		} catch (BaseDeDatosExcepcion e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método migrarArchivoCondicionPromocion
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void migrarArchivoCondicionPromocion (String path, String tabla) {
		String foreignKey = "SET FOREIGN_KEY_CHECKS = ";
		String ordenSQL = "LOAD DATA INFILE '" + path + "' REPLACE INTO TABLE " + tabla + " FIELDS TERMINATED BY '" + Sesion.sepCampo + "' OPTIONALLY ENCLOSED BY '" + Sesion.delCampo + "'";
		try {
			Conexiones.realizarSentencia(foreignKey + "0",true);
			LeerArchivo.convertirLoadCondicionPromocion(path, path + "1");
			Conexiones.realizarSentencia(ordenSQL,true);
			Conexiones.realizarSentencia(foreignKey + "1",true);
		} catch (ConexionExcepcion e) {
			e.printStackTrace();
		} catch (BaseDeDatosExcepcion e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método migrarArchivoProdSeccion
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void migrarArchivoProdSeccion (String path, String tabla) {
		String foreignKey = "SET FOREIGN_KEY_CHECKS = ";
		String ordenSQL = "LOAD DATA INFILE '" + path + "' REPLACE INTO TABLE " + tabla + " FIELDS TERMINATED BY '" + Sesion.sepCampo + "' OPTIONALLY ENCLOSED BY '" + Sesion.delCampo + "'";
		try {
			Conexiones.realizarSentencia(foreignKey + "0",true);
			LeerArchivo.convertirLoadProdSeccion(path, path + "1");
			Conexiones.realizarSentencia(ordenSQL,true);
			Conexiones.realizarSentencia(foreignKey + "1",true);
		} catch (ConexionExcepcion e) {
			e.printStackTrace();
		} catch (BaseDeDatosExcepcion e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Método crearTablaTmpDetPromocion
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'HashTable' 
	* Fecha: agosto 2011
	*/
	public static Hashtable<String,Boolean> crearTablaTmpDetPromocion(String path, String tablaNva, String tablaOriginal) {
		Hashtable<String,Boolean> detallesPromocion = new Hashtable<String,Boolean>();
		String foreignKey = "SET FOREIGN_KEY_CHECKS = ";
		String ordenSQL = "LOAD DATA INFILE '" + path + "' REPLACE INTO TABLE " + tablaNva + " FIELDS TERMINATED BY '" + Sesion.sepCampo + "' OPTIONALLY ENCLOSED BY '" + Sesion.delCampo + "'";
		String eliminarTablaTmp = "DROP TABLE IF EXISTS " + Sesion.getDbEsquema() + "." + tablaNva;
		String borrarDatos = "DELETE FROM " + Sesion.getDbEsquema() + "." + tablaNva;
		String crearTablaTmp = "CREATE TABLE " + Sesion.getDbEsquema() + "." + tablaNva + " AS (SELECT * FROM " + Sesion.getDbEsquema() + "." + tablaOriginal + " LIMIT 1)";
		// Cargamos los datos de las promociones transferidas en Tabla temporal para optimizar busqueda de promociones anuladas
		try {
			Conexiones.realizarSentencia(foreignKey + "0",true);
			Conexiones.realizarSentencia(eliminarTablaTmp,true);
			Conexiones.realizarSentencia(crearTablaTmp,true);
			Conexiones.realizarSentencia(borrarDatos,true);
			Conexiones.realizarSentencia(ordenSQL,true);
			Conexiones.realizarSentencia(foreignKey + "1",true);
			
			String query = "select * from "+tablaNva;
			ResultSet result = Conexiones.realizarConsulta(query,true,false);
			result.beforeFirst();
			while(result.next()){
				detallesPromocion.put(result.getInt("codpromocion")+result.getString("codproducto"), new Boolean(true));
			}
			
			
		} catch (ConexionExcepcion e) {
			e.printStackTrace();
		} catch (BaseDeDatosExcepcion e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return detallesPromocion;
		
	}

	/**
	 * Método migrarProductos
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void migrarArchivoProducto(String path, String tabla) {
		String foreignKey = "SET FOREIGN_KEY_CHECKS = ";
		String ordenSQL = "LOAD DATA INFILE '" + path + "1' REPLACE INTO TABLE " + tabla + " FIELDS TERMINATED BY '" + Sesion.sepCampo + "' OPTIONALLY ENCLOSED BY '" + Sesion.delCampo + "'";
		try {
			Conexiones.realizarSentencia(foreignKey + "0",true);
			LeerArchivo.convertirLoadProducto(path, path + "1");
			Conexiones.realizarSentencia(ordenSQL,true);
			Conexiones.realizarSentencia(foreignKey + "1",true);
		} catch (ConexionExcepcion e) {
			e.printStackTrace();
		} catch (BaseDeDatosExcepcion e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método migrarProductos
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void migrarArchivoCodExterno(String path, String tabla) {
		String foreignKey = "SET FOREIGN_KEY_CHECKS = ";
		String ordenSQL = "LOAD DATA INFILE '" + path + "1' REPLACE INTO TABLE " + tabla + " FIELDS TERMINATED BY '" + Sesion.sepCampo + "' OPTIONALLY ENCLOSED BY '" + Sesion.delCampo + "'";
		try {
			Conexiones.realizarSentencia(foreignKey + "0",true);
			LeerArchivo.convertirLoadCodExterno(path, path + "1");
			Conexiones.realizarSentencia(ordenSQL,true);
			Conexiones.realizarSentencia(foreignKey + "1",true);
		} catch (ConexionExcepcion e) {
			e.printStackTrace();
		} catch (BaseDeDatosExcepcion e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método migrarProductos
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void migrarArchivoReplace(String path, String tabla) {
		try {
			String foreignKey = "SET FOREIGN_KEY_CHECKS = ";
			String ordenSQL = "LOAD DATA INFILE '" + path + "1" + "' INTO TABLE " + tabla  + "temp" + " FIELDS TERMINATED BY '" + Sesion.sepCampo + "' OPTIONALLY ENCLOSED BY '" + Sesion.delCampo + "'";
			String sentencia = "delete from " + tabla  + "temp";
			System.out.println(sentencia);
			Conexiones.realizarSentencia(sentencia, true);
				
			LeerArchivo.convertirLoad(path, path + "1");
			System.out.println(ordenSQL);
			Conexiones.realizarSentencia(ordenSQL, true);
				
			Conexiones.realizarSentencia(foreignKey + "0", true);
			sentencia = "replace into " + tabla + " select * from " + tabla + "temp";
			System.out.println(sentencia);
			Conexiones.realizarSentencia(sentencia, true);
			Conexiones.realizarSentencia(foreignKey + "1", true);
				
			//sentencia = "delete from " + tabla  + "temp";
			System.out.println(sentencia);
			Conexiones.realizarSentencia(sentencia, true); 
		} catch (ConexionExcepcion e) {
			e.printStackTrace();
		} catch (BaseDeDatosExcepcion e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Detecta las posibles promociones anuladas que fueron transferidas en la última
	 * transferencia de productos realizada
	 * @param 
	 * @throws BaseDeDatosExcepcion Si ocurrio una falla en la conexion con la base de datos.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'HashTable' 
	* Fecha: agosto 2011
	* WDIAZ 01-2013. Se va a comentar este código, ya que no hace falta porque Compiere este enviando las promociones activas (A) o Desactivas (E) dependiendo del caso.
	*/
	/*public static void verificarPromocionesAnuladas(Hashtable<String,Boolean> promocionesTransf) throws BaseDeDatosExcepcion, ConexionExcepcion {	
		String codProducto = "";

		//Variables para calcular la fecha del día anterior
		Calendar fechaHoy = Calendar.getInstance();
		fechaHoy.setTime(Sesion.getFechaSistema());
		fechaHoy.add(Calendar.DATE,-1);
		fechaHoy.set(Calendar.HOUR_OF_DAY,23);
		fechaHoy.set(Calendar.MINUTE,00);
		fechaHoy.set(Calendar.SECOND,00);
		Date fechaSinc = fechaHoy.getTime();
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMddHHmmss");
		String fechaSincString = formatoFecha.format(fechaSinc);
		
		ResultSet rs = null;
		String sentenciaSQL = null;
		// Obtenemos los productos transferidos junto con sus promociones
		sentenciaSQL = "select p.codproducto, de.codpromocion from producto p, detallepromocion de where p.actualizacion >= '" + fechaSincString + "' and " + 
				"p.codproducto=de.codproducto and de.estadoregistro = '" + Sesion.PROMOCION_ACTIVA + "'";
		
		try {
			rs = Conexiones.realizarConsulta(sentenciaSQL,true,false);
			rs.beforeFirst();
			while (rs.next()) {
				codProducto = rs.getString(1).trim();
				int codPromo = rs.getInt(2);
				//Se verifica si el detalle de la promoción fue transferida junto con el producto (En Memoria)
				 boolean promoTransferida = promocionesTransf.get(codPromo+codProducto)!=null;
				 	//Si no se paso el detalle indica que fue anulada y hay que marcarla como 'E'
				 	if (!promoTransferida) {
						String sentencia = "update detallepromocion set detallepromocion.estadoregistro = '" + Sesion.PROMOCION_ANULADA + "' where detallepromocion.codpromocion = "
										+ codPromo + " and detallepromocion.codproducto = '" + codProducto + "'";
						Conexiones.realizarSentencia(sentencia, true);
				 	}
				 }
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			rs = null;
		}
	}*/
	
	/**
	 * Método productoTransferido Verifica si el producto se encuentra en la tabla detallepromociontransf
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	@SuppressWarnings("unused")
	private static boolean productoTransferido(int codPromo, String codProducto) {
		String sentencia = "SELECT COUNT(*) FROM " + Sesion.getDbEsquema() + ".detallepromociontransf where codpromocion=" + codPromo +
							" and codproducto=" + codProducto;
		try {
			ResultSet resultado = Conexiones.realizarConsulta(sentencia, true, false);
			resultado.first();
			return resultado.getInt(1) > 0;
		} catch (Exception e) {
			return true;
		}
	}

	/**
	 * Método actualizarEdoPromociones
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void actualizarEdoPromociones() {
		String sentencia = "update detallepromocion set detallepromocion.estadoregistro = '" + 
							Sesion.PROMOCION_ACTIVA + "' where detallepromocion.estadoregistro = ' '";
		try {
			Conexiones.realizarSentencia(sentencia,true);
		} catch (ConexionExcepcion e) {
			e.printStackTrace();
		} catch (BaseDeDatosExcepcion e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public static void syncAfiliadosNuevos() throws BaseDeDatosExcepcion {
		String sentenciaSql;
		Session sessionServOrigen = null, sessionServDestino = null;
		Connection conOrigen = null, conDestino = null;
		Transaction tx = null;
		
		try {
			String identificador = "";
			ResultSet cedulasNuevas = BeansSincronizador.getNuevasCedulas(true);
			// Obtenemos las conexiones necesarias para la sincronizacion
			conOrigen = Conexiones.getConexion(true);
			conOrigen.setAutoCommit(false);
			conDestino = Conexiones.getConexion(false);
			conDestino.setAutoCommit(false);
			
			// Setting de Variables de control de sincronizacion
			sessionServOrigen = ConexionHibernate.currentSession(true, conOrigen);
			sessionServDestino = ConexionHibernate.currentSession(false, conDestino);

			if (sessionServDestino.isOpen() && sessionServOrigen.isOpen()) {
				cedulasNuevas.beforeFirst();
				while(cedulasNuevas.next()){
					identificador = cedulasNuevas.getString("servicio.codcliente");
					String[] clave = {"codafiliado"};
					EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "afiliado", clave, true);
					
					// Obtengo información de la CR del Cliente actual
					sentenciaSql = "select {" + entidad.getNombre() + ".*} from " + entidad.getEsquema() + "." + entidad.getNombre() +" where codafiliado='" + identificador + "'";
					String entidadBD = new String("com.beco.colascr.transferencias.sincronizador.hibernate."+entidad.getNombre().toUpperCase().charAt(0)+entidad.getNombre().substring(1)); 
					tx = sessionServDestino.beginTransaction();
					Query consulta = sessionServOrigen.createSQLQuery(sentenciaSql,entidad.getNombre(),Class.forName(entidadBD));
					List<Object> registros = consulta.list();
					for(int i=0; i<registros.size(); i++){
						Object registro = registros.get(i);
						try {
							sessionServDestino.replicate(registro, ReplicationMode.LATEST_VERSION);
						} catch (HibernateException e) {
							e.printStackTrace();
							System.out.println("Error al sincronizar entidad: " + registro.toString());
						}
					}
					tx.commit();
					sessionServOrigen.clear();
					sessionServDestino.clear();
				}
			}
		} catch (BatchUpdateException e) {
			throw (new BaseDeDatosExcepcion("Error en sentencia por lote al sincronizar Afiliados"));
		} catch (SQLException e) {
			throw (new BaseDeDatosExcepcion("Error en sentencia SQL al sincronizar Afiliados"));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de conexión con BD al sincronizar Afiliados"));
		} catch (Throwable t) {
			throw (new BaseDeDatosExcepcion("Error General en Sincronizacion de Afiliados"));
		} finally{
			try {sessionServOrigen.close();} catch (Exception e1) {}
			try {sessionServDestino.close();} catch (Exception e1) {}
			try {ConexionHibernate.closeSession(true);} catch (Exception e1) {}
			try {ConexionHibernate.closeSession(false);} catch (Exception e1) {}
		}
	}

	public static ResultSet getNuevasCedulas(boolean local) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		String sentenciaSql = "";
		sentenciaSql = new String("select distinct servicio.codcliente from " + Sesion.getDbEsquema() + ".servicio where servicio.regactualizado='" + Sesion.NO + "'");
		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, true);
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error al recuperar transacciones: " + sentenciaSql, e));
		} catch (ConexionExcepcion e) {
		}
		return resultado;
	}

	/**
	 * Método syncEntidadesATCM
	 * Sincroniza entidades de Estados, Ciudades y Urbanizaciones bidireccional
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	/*public static void syncEntidadesATCM() throws SQLException, BaseDeDatosExcepcion {
		BeansSincronizador.syncEntidadesATCM(Sesion.SINC_SERVCENTRAL);
		BeansSincronizador.syncEntidadesATCM(Sesion.SINC_SERVTDA);
	}*/

	/**
	 * Método syncEntidadesATCM
	 * Sincroniza entidades de Estados, Ciudades y Urbanizaciones hacia Caja o hacia Servidor
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncEntidadesATCM(int direccion) throws SQLException, BaseDeDatosExcepcion {
		BeansSincronizador.syncAtcm23(direccion);
		BeansSincronizador.syncAtcm24(direccion);
		BeansSincronizador.syncAtcm25(direccion);
	}
	/**
	 * Método syncAtcm23
	 * 
	 * @param BDOrigen - Indicador de la entidad donde estan los datos a sincronizar. Verdadero si es 
	 * 		la Caja Registradora y falso si estan en el Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncAtcm23(int direccion) throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"codedo"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "atcm23", clave, true);
		BeansSincronizador.syncEntidad(direccion, entidad);
	}

	/**
	 * Método syncAtcm24
	 * 
	 * @param BDOrigen - Indicador de la entidad donde estan los datos a sincronizar. Verdadero si es 
	 * 		la Caja Registradora y falso si estan en el Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncAtcm24(int direccion) throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"codedo", "codciu"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "atcm24", clave, true);
		BeansSincronizador.syncEntidad(direccion, entidad);
	}

	/**
	 * Método syncAtcm25
	 * 
	 * @param BDOrigen - Indicador de la entidad donde estan los datos a sincronizar. Verdadero si es 
	 * 		la Caja Registradora y falso si estan en el Servidor.
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncAtcm25(int direccion) throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"codedo", "codciu", "codurb"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "atcm25", clave, true);
		BeansSincronizador.syncEntidad(direccion, entidad);
	}

	/**
	 * Método syncListaRegalos
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncListaregalos() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"codlista"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "listaregalos", clave, true);
		BeansSincronizador.syncEntidad(Sesion.SINC_SERVCENTRAL, entidad);
	}
	
	/**
	 * Método syncDetalleListaRegalos
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncDetallelistaregalos() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"codlista", "codproducto"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "detallelistaregalos", clave, true);
		BeansSincronizador.syncEntidad(Sesion.SINC_SERVCENTRAL, entidad);
	}
	
	/**
	 * Método syncOperacionlistaregalos
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncOperacionlistaregalos() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"numoperacion", "codlista", "codproducto", "correlativoitem"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "operacionlistaregalos", clave, true);
		BeansSincronizador.syncEntidad(Sesion.SINC_SERVCENTRAL, entidad);
	}
	
	/**
	 * Método syncDetalleoperacionlistaregalos
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncDetalleoperacionlistaregalos() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"numoperacion", "codlista", "codformadepago", "correlativo"}; 
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "detalleoperacionlistaregalos", clave, true);
		BeansSincronizador.syncEntidad(Sesion.SINC_SERVCENTRAL, entidad);
	}
		
	/**
	 * Método syncTipoeventolistaregalos
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncTipoeventolistaregalos() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"codtipoevento"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "tipoeventolistaregalos", clave, false, false);
		BeansSincronizador.syncEntidadBase(entidad, false);
	}
	
	/**
	 * Método syncTipoeventolistaregalos
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncServidortienda() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"numtienda"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "servidortienda", clave, false, false);
		BeansSincronizador.syncEntidadBase(entidad, false);
	}
	
	/**
	 * Método syncBROpcionHabilitada
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncBROpcionHabilitada() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"codmetodo", "nombreopcion"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "br_opcionhabilitada", clave, false);
		BeansSincronizador.syncEntidadBase(entidad, true);
	}
	
	/**
	 * Método syncBRCondiciones
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncBRCondiciones() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"orden"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "br_condiciones", clave, false);
		BeansSincronizador.syncEntidadBase(entidad, true);
	}
	
	/**
	 * Método syncBRPromocion
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncBRPromocion() throws SQLException, BaseDeDatosExcepcion {
		ResultSet resultRemoto = null;
		ResultSet resultLocal = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			String sentenciaSql = "select * from br_promocion where numtienda = 0 or numtienda = " + Sesion.getNumeroTda();
			resultRemoto = Conexiones.realizarConsulta(sentenciaSql, false, false);
			resultRemoto.beforeFirst();
			while (resultRemoto.next()) {
				String sentenciaLocal = "select * from br_promocion where numtienda = " + resultRemoto.getInt("numtienda") + 
					" and fechainicia = '" + sdf.format(resultRemoto.getDate("fechainicia")) + "' and tipo = '" + 
					resultRemoto.getString("tipo") + "'";
				String sentenciaSync = "";
				resultLocal = Conexiones.realizarConsulta(sentenciaLocal, true, false);
				Date fechaFin = resultRemoto.getDate("fechafinaliza");
				String fechaString = fechaFin != null ? "'" + sdf.format(fechaFin) + "'" : "null";
				if (resultLocal.first()) {
					sentenciaSync = "update br_promocion set porcentaje = " + resultRemoto.getDouble("porcentaje") 
								+ ", activo = '" + resultRemoto.getString("activo") + "', fechafinaliza = "
								+ fechaString + " where numtienda = " 
								+ resultRemoto.getInt("numtienda") + " and fechainicia = '" 
								+ sdf.format(resultRemoto.getDate("fechainicia")) + "' and tipo = '"  
								+ resultRemoto.getString("tipo") + "'";
				} else {
					sentenciaSync = "insert into br_promocion (fechainicia, porcentaje, tipo, activo, fechafinaliza, numtienda) "
						+ " values ('" + sdf.format(resultRemoto.getDate("fechainicia")) + "', " + resultRemoto.getDouble("porcentaje")  
						+ " , '" + resultRemoto.getString("tipo") + "', '" + resultRemoto.getString("activo") 
						+ "', " + fechaString + ", " + resultRemoto.getInt("numTienda") + ")";
				}
				Conexiones.realizarSentencia(sentenciaSync, true);
			}
		} catch(ConexionExcepcion e1) {
			e1.printStackTrace();
		} finally {
			try {
				resultLocal.close();
			} catch (Exception e) {
			}
			try {
				resultRemoto.close();
			} catch (Exception e) {
			}
			resultLocal = null;
			resultRemoto = null;
		}
	}

	/**
	 * 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'List' 
	* Fecha: agosto 2011
	*/
	public static void syncAfiliadosLR() throws BaseDeDatosExcepcion {
		String sentenciaSql="";
		Session sessionServOrigen = null, sessionServDestino = null;
		Connection conOrigen = null, conDestino = null;
		Transaction tx = null;
		System.out.println("3391");
		try {
			String identificador = "";
			ResultSet cedulasNuevas = null;
			sentenciaSql = new String("select distinct lr.codtitular from listaregalos lr " +
					"inner join planificador p on (lr.actualizacion>=p.actualizacion) where " +					"p.entidad='listaregalos'");
			try {
				cedulasNuevas = Conexiones.realizarConsulta(sentenciaSql, true, true);
			} catch (BaseDeDatosExcepcion e) {
				throw (new BaseDeDatosExcepcion("Error al recuperar transacciones: " + sentenciaSql, e));
			} catch (ConexionExcepcion e) {
				throw (new BaseDeDatosExcepcion("Error de conexión al recuperar transacciones: " + sentenciaSql, e));
			}
			// Obtenemos las conexiones necesarias para la sincronizacion
			conOrigen = Conexiones.getConexion(true);
			conOrigen.setAutoCommit(false);
			conDestino = Conexiones.getConexion(false);
			conDestino.setAutoCommit(false);
			
			// Setting de Variables de control de sincronizacion
			sessionServOrigen = ConexionHibernate.currentSession(true, conOrigen);
			sessionServDestino = ConexionHibernate.currentSession(false, conDestino);
			System.out.println("3414");
			if (sessionServDestino.isOpen() && sessionServOrigen.isOpen()) {
				cedulasNuevas.beforeFirst();
				while(cedulasNuevas.next()){
					identificador = cedulasNuevas.getString("codtitular");
					String[] clave = {"codafiliado"};
					EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "afiliado", clave, true);
					
					// Obtengo información de la CR del Cliente actual
					sentenciaSql = "select {" + entidad.getNombre() + ".*} from " + entidad.getEsquema() + "." + entidad.getNombre() +" where codafiliado='" + identificador + "'";
					String entidadBD = new String("com.beco.colascr.transferencias.sincronizador.hibernate."+entidad.getNombre().toUpperCase().charAt(0)+entidad.getNombre().substring(1)); 
					tx = sessionServDestino.beginTransaction();
					Query consulta = sessionServOrigen.createSQLQuery(sentenciaSql,entidad.getNombre(),Class.forName(entidadBD));
					System.out.println("3427");
					List<Object> registros = consulta.list();
					for(int i=0; i<registros.size(); i++){
						Object registro = registros.get(i);
						try {
							sessionServDestino.replicate(registro, ReplicationMode.LATEST_VERSION);
						} catch (HibernateException e) {
							e.printStackTrace();
							System.out.println("Error al sincronizar entidad: " + registro.toString());
						}
					}
					System.out.println("3438");
					tx.commit();
					sessionServOrigen.clear();
					sessionServDestino.clear();
				}
			}
		} catch (BatchUpdateException e) {
			throw (new BaseDeDatosExcepcion("Error en sentencia por lote al sincronizar Afiliados"));
		} catch (SQLException e) {
			throw (new BaseDeDatosExcepcion("Error en sentencia SQL al sincronizar Afiliados"));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de conexión con BD al sincronizar Afiliados"));
		} catch (Throwable t) {
			throw (new BaseDeDatosExcepcion("Error General en Sincronizacion de Afiliados"));
		} finally{
			try {sessionServOrigen.close();} catch (Exception e1) {}
			try {sessionServDestino.close();} catch (Exception e1) {}
			try {ConexionHibernate.closeSession(true);} catch (Exception e1) {}
			try {ConexionHibernate.closeSession(false);} catch (Exception e1) {}
		}
	}
	
	
	/**
	 * @throws com.beco.colascr.servicios.excepciones.ConexionExcepcion 
	 * @throws com.beco.colascr.servicios.excepciones.BaseDeDatosExcepcion 
	 * @throws SQLException 
	 * 
	 *
	 */
	private static void cargarAfiliadosTemporales() throws BaseDeDatosExcepcion, ConexionExcepcion, SQLException {
		ResultSet result = null;
		
		// Armamos los vectores de la factura a recuperar
		/*String sentenciaSQL = "SELECT * FROM afiliado AS a INNER JOIN transaccion AS t ON (a.codafiliado = t.codcliente)" + 
							  "	WHERE t.fecha = (CURRENT_DATE - 1) GROUP by a.codafiliado";*/

		//String sentenciaSQL = "SELECT * FROM afiliado WHERE fechaafiliacion = (CURRENT_DATE - 1)";
		
		String sentenciaSQL = "SELECT * FROM afiliado WHERE DATE_FORMAT(actualizacion, '%Y-%m-%d') = DATE_FORMAT(CURRENT_DATE - 1, '%Y-%m-%d')";

		result = Conexiones.realizarConsulta(sentenciaSQL,true,false);
		System.out.println("\n\n\n Inicio de carga en afiliado1");
		if (result.first()){
			result.beforeFirst();
			while (result.next()) {
				//System.out.println("\nAfiliado: " + result.getString("codafiliado"));
				String apellido = (result.getString("apellido")==null) ? "null" : "'" + result.getString("apellido") + "'";
				String numFicha = (result.getString("numficha")==null) ? "null" : "'" + result.getString("numficha") + "'"; 
				String codDep = (result.getString("coddepartamento")==null) ? "null" : "'" + result.getString("coddepartamento") + "'";
				String codCargo = (result.getString("codcargo")==null) ? "null" : "'" + result.getString("codcargo") + "'";
				String nitCliente = (result.getString("nitcliente")==null) ? "null" : "'" + result.getString("nitcliente") + "'";
				String dirFiscal = (result.getString("direccionfiscal")==null) ? "null" : "'" + result.getString("direccionfiscal") + "'";
				String email = (result.getString("email")==null) ? "null" : "'" + result.getString("email") + "'";
				String codArea = (result.getString("codarea")==null) ? "null" : "'" + result.getString("codarea") + "'";
				String numTlf = (result.getString("numtelefono")==null) ? "null" : "'" + result.getString("numtelefono") + "'";
				String codArea1 = (result.getString("codarea1")==null) ? "null" : "'" + result.getString("codarea1") + "'";
				String numTlf1 = (result.getString("numtelefono1")==null) ? "null" : "'" + result.getString("numtelefono1") + "'";
				
				//se estan colocando 3 nuevos parametros para el insert de esta tabla (afiliado1)
				String sentenciaSQL2 = "INSERT INTO afiliado1 (codafiliado, tipoafiliado, nombre, apellido, numtienda, numficha, " +
						"coddepartamento, codcargo, nitcliente, direccion, direccionfiscal, email, codarea, numtelefono, codarea1, numtelefono1, " +
				"fechaafiliacion, exentoimpuesto, registrado, contactar, codregion, estadoafiliado, estadocolaborador, genero, estadocivil, fechanacimiento) values ('" + 
				result.getString("codafiliado") + "','" + result.getString("tipoafiliado") + "','" +
				result.getString("nombre") + "'," + apellido + "," + result.getInt("numtienda") + "," + 
				numFicha + "," + codDep + "," + codCargo + "," + nitCliente + ",'" + result.getString("direccion") + "'," +
				dirFiscal + "," + email + "," + codArea + "," + numTlf + "," + codArea1 + "," + numTlf1 + ",'" +
				result.getString("fechaafiliacion") + "','" + result.getString("exentoimpuesto") + "','" +
				result.getString("registrado") + "','" + result.getString("contactar") + "','" +
				result.getString("codregion") + "','" + result.getString("estadoafiliado") + "','" +
				result.getString("estadocolaborador") + "','" + result.getString("genero") + "','" + result.getString("estadocivil") + "','" + result.getString("fechanacimiento") + "')";

				//System.out.println("\n" + sentenciaSQL2);
				try{
					Conexiones.realizarSentencia(sentenciaSQL2,true);
				} catch (BaseDeDatosExcepcion e) {
					//Clave duplicada				
				}	
			}
		}
		System.out.println("\n\n\n Fin de carga en afiliado1");
		try {
			result.close();
			Conexiones.cerrarConexion(true, true);
		} catch (SQLException e) {
		}
		result= null;
		
	}
	
	
	
	/**
	 * 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'List' 
	* Fecha: agosto 2011
	*/
	public static void syncAfiliadosTemporales() throws BaseDeDatosExcepcion {
		String sentenciaSql="";
		Session sessionServOrigen = null, sessionServDestino = null;
		Connection conOrigen = null, conDestino = null;
		Transaction tx = null;
		Calendar fecha, fechaPlanificador;
		fecha = Calendar.getInstance();
		fechaPlanificador = Calendar.getInstance();
		Timestamp actualizacionSelect = null;
		
		SimpleDateFormat fechaFormat = new SimpleDateFormat("yyyyMMddhhmmss");
		String actualizacionString = fechaFormat.format(fecha.getTime()) ;
	
		try {
			//19-07-2010, IROJAS
			//Se verifica si ya se sincronizó la tabla afiliadostemp para no sincronizar mas de una vez en el día
			ResultSet consultaPlanificador = null;
			try {
				sentenciaSql = new String("select * from planificador where entidad='afiliadostemp' and destino = 'S'");
				consultaPlanificador = Conexiones.realizarConsulta(sentenciaSql, true, false);
				consultaPlanificador.first();
				actualizacionSelect = consultaPlanificador.getTimestamp("actualizacion");
				fechaPlanificador.setTime(new Date(actualizacionSelect.getTime()));
			} catch (Exception e) {
			} finally {
				consultaPlanificador.close();
			}

			if (actualizacionSelect==null || fechaPlanificador.get(Calendar.DAY_OF_MONTH) != fecha.get(Calendar.DAY_OF_MONTH)) {
				// Transferir a Afiliado1 en el AS
				cargarAfiliadosTemporales();
							
				String identificador = "";
				ResultSet cedulasNuevas = null;
				sentenciaSql = new String("SELECT codafiliado FROM afiliado1");
				try {
					cedulasNuevas = Conexiones.realizarConsulta(sentenciaSql, true, true);
				} catch (BaseDeDatosExcepcion e) {
					throw (new BaseDeDatosExcepcion("Error al recuperar transacciones: " + sentenciaSql, e));
				} catch (ConexionExcepcion e) {
					throw (new BaseDeDatosExcepcion("Error de conexión al recuperar transacciones: " + sentenciaSql, e));
				}
				// Obtenemos las conexiones necesarias para la sincronizacion
				conOrigen = Conexiones.getConexion(true);
				conOrigen.setAutoCommit(false);
				conDestino = Conexiones.getConexion(false);
				conDestino.setAutoCommit(false);
				
				// Setting de Variables de control de sincronizacion
				sessionServOrigen = ConexionHibernate.currentSession(true, conOrigen);
				sessionServDestino = ConexionHibernate.currentSession(false, conDestino);
				if (sessionServDestino.isOpen() && sessionServOrigen.isOpen()) {
					cedulasNuevas.beforeFirst();
					while(cedulasNuevas.next()){
						identificador = cedulasNuevas.getString("codafiliado");
						String[] clave = {"codafiliado"};
						EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "afiliado1", clave, true);
						
						// Obtengo información de la CR del Cliente actual
						sentenciaSql = "select {" + entidad.getNombre() + ".*} from " + entidad.getEsquema() + "." + entidad.getNombre() +" where codafiliado='" + identificador + "'";
						//String entidadBD = new String("com.beco.colascr.transferencias.sincronizador.hibernate.Afiliado1");
						String entidadBD = new String("com.beco.colascr.transferencias.sincronizador.hibernate."+entidad.getNombre().toUpperCase().charAt(0)+entidad.getNombre().substring(1)); 
						tx = sessionServDestino.beginTransaction();
						Query consulta = sessionServOrigen.createSQLQuery(sentenciaSql,entidad.getNombre(),Class.forName(entidadBD));
						List<Object> registros = consulta.list();
						System.out.println("\nNumero de registros: " + registros.size());
						for(int i=0; i<registros.size(); i++){
							
							Object registro = registros.get(i);
							try {
								sessionServDestino.replicate(registro, ReplicationMode.LATEST_VERSION);
							} catch (HibernateException e) {
								e.printStackTrace();
								System.out.println("Error al sincronizar entidad: " + registro.toString());
							}
						}
						tx.commit();
						sessionServOrigen.clear();
						sessionServDestino.clear();
					}
					//19-07-2010, IROJAS
					//Agregada opción de registro en planificador de la sincornización de afiliadostemporales diario
					//Con esto se busca sincronizar esta tabla una sóla vez al día
					try {	
						sentenciaSql = new String("replace into planificador "+" (numtienda, numcaja, entidad, actualizacion, fallasincronizador, destino) values (0,0,'afiliadostemp', "+ actualizacionString+ ",'N', 'S')");
						System.out.println(sentenciaSql);
						Conexiones.realizarSentencia(sentenciaSql, true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (BatchUpdateException e) {
			throw (new BaseDeDatosExcepcion("Error en sentencia por lote al sincronizar Afiliados"));
		} catch (SQLException e) {
			throw (new BaseDeDatosExcepcion("Error en sentencia SQL al sincronizar Afiliados"));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de conexión con BD al sincronizar Afiliados"));
		} catch (Throwable t) {
			t.printStackTrace();
			throw (new BaseDeDatosExcepcion("Error General en Sincronizacion de Afiliados"));
		} finally{
			try {sessionServOrigen.close();} catch (Exception e1) {}
			try {sessionServDestino.close();} catch (Exception e1) {}
			try {ConexionHibernate.closeSession(true);} catch (Exception e1) {}
			try {ConexionHibernate.closeSession(false);} catch (Exception e1) {}
			//Borrar afiliados temporales
			try {
				Conexiones.realizarSentencia("delete from afiliado1", true);
			} catch (ConexionExcepcion e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
//	Metodos nuevos para sincronizar la tabla transaccion afiliado y Solicitud de Clientes de crm Wdiaz
	public static void syncTransaccionafiliadocrm() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"numtienda","fechatransaccion","numcajafinaliza","numtransaccion","codafiliado"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "transaccionafiliadocrm", clave, false,true);
		BeansSincronizador.syncEntidad(2,entidad);
		}
	
	public static void syncSolicitudcliente() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"numtienda", "actualizacion"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "solicitudcliente", clave, true, false);
		entidad.setSolicitud(true);
		BeansSincronizador.syncEntidadBase(entidad, false);
	}
	//fin del o los metodos
	
	/**
	 * Dado el listado de (numtienda, numservicio) elimina de la bd central 
	 * los detalles asociados a cada servicio
	 * @param servicios
	 * @throws ConexionExcepcion 
	 * @throws SQLException 
	 * @throws HibernateException 
	 * @throws BaseDeDatosExcepcion 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public static void deleteDetallesServicio(Vector<Vector<Integer>> servicios) throws ConexionExcepcion, SQLException, HibernateException, BaseDeDatosExcepcion{
		for(int i=0;i<servicios.size();i++){
			Vector<Integer> datosServicio = servicios.elementAt(i);
			int numTienda = ((Integer)datosServicio.elementAt(0)).intValue();
			int numServicio = ((Integer)datosServicio.elementAt(1)).intValue();
			Conexiones.realizarSentencia("delete from detalleservicio where codtiposervicio='" + Sesion.APARTADO + "' and numtienda="+numTienda+" and numservicio="+numServicio, false);
		}
	}
	
	/**
	 * Método syncCatDep
	 * IROJAS 05/03/2008, modificación necesaria para la sincronización del módulo de promociones
	 * Sincroniza la tabla catDep, que relaciona los departamentos con la categoría a la que pertenece
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncCatDep() throws SQLException, BaseDeDatosExcepcion {
		String[] clave = {"codcat", "codDep"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "catdep", clave, false, false);
		BeansSincronizador.syncEntidadBase(entidad, true);
	}
	
	/**
	 * Método getAdicionalesDeVenta()
	 * 		IROJAS 06/03/2008,
	 * 		Agrega al Batch de transacciones aquellas tablas adicionales que deben subir con la venta
	 * 		Ejemplo: donaciones, regalosregistrados, etc
	 * @param local - Indicador de la conexión de BD a utilizar: Servidor o Local. 
	 * @param transaccion - Número de la transacción a sincronizar.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql efectuada. 
	 * @throws BaseDeDatosExcepcion
	 * @throws SQLException 
	 * @throws ConexionExcepcion 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	public static Statement getAdicionalesDeVenta(Statement loteSentencias, int numTransaccion, int numCaja) throws BaseDeDatosExcepcion, SQLException, ConexionExcepcion {
		// MODULO DE PROMOCIONES: Obtengo las condiciones de venta de la transaccion
		ResultSet detallesCondiciones, promocionesRegistradas, regalosRegistrados, transaccionPremiada, donacionesRegistradas, pagosDonaciones;
		ArrayList <ArrayList<Object>>registros;
		ArrayList <Object>registro;
		String sentenciaSql;
		String[] xLoteSentencias;
		StringBuffer xCampos, xValores;
		int numColumnas = 0;
		
		//****** PROMOCIONES: Sinc Condiciones de Venta de la transacción
		//******
		registros = new ArrayList<ArrayList<Object>>();
		xCampos = new StringBuffer("");
		xValores = new StringBuffer("");
		detallesCondiciones = BeansSincronizador.getCondicionesVenta(true, numTransaccion, numCaja);
		try {
			numColumnas = detallesCondiciones.getMetaData().getColumnCount();
			for (int i=0; i<numColumnas; i++){
				xCampos.append(detallesCondiciones.getMetaData().getColumnName(i+1));
				xValores.append("?");
				if(i+1 < numColumnas){
					xCampos.append(", ");
					xValores.append(", ");
				}
			}
			sentenciaSql = new String("insert into detalletransaccioncondicion ("+xCampos+") values ("+xValores+")");
			
			while (detallesCondiciones.next()){
				registro = new ArrayList<Object>();
				for (int i=0; i<numColumnas; i++)
					registro.add(detallesCondiciones.getObject(detallesCondiciones.getMetaData().getColumnName(i+1).toLowerCase()));
				registros.add(registro);
			}
		} finally {
			detallesCondiciones.close();
		}
		xLoteSentencias = Conexiones.crearLoteSentencias(sentenciaSql, registros, false);
		for (int i=0; i<xLoteSentencias.length; i++){
			loteSentencias.addBatch(xLoteSentencias[i]);
		}
		//****** 
		//******

		//****** PROMOCIONES: Sinc promociones registradas por cada detalle de venta 
		//******
		registros = new ArrayList<ArrayList<Object>>();
		xCampos = new StringBuffer("");
		xValores = new StringBuffer("");
		promocionesRegistradas = BeansSincronizador.getPromocionesRegistradas(true, numTransaccion, numCaja);

		try {
			numColumnas = promocionesRegistradas.getMetaData().getColumnCount();
			for (int i=0; i<numColumnas; i++){
				xCampos.append(promocionesRegistradas.getMetaData().getColumnName(i+1));
				xValores.append("?");
				if(i+1 < numColumnas){
					xCampos.append(", ");
					xValores.append(", ");
				}
			}
			sentenciaSql = new String("insert into promocionregistrada ("+xCampos+") values ("+xValores+")");
			
			while (promocionesRegistradas.next()){
				registro = new ArrayList<Object>();
				for (int i=0; i<numColumnas; i++)
					registro.add(promocionesRegistradas.getObject(promocionesRegistradas.getMetaData().getColumnName(i+1).toLowerCase()));
				registros.add(registro);
			}
		} finally {
			promocionesRegistradas.close();
		}
		
		xLoteSentencias = Conexiones.crearLoteSentencias(sentenciaSql, registros, false);
		for (int i=0; i<xLoteSentencias.length; i++){
			loteSentencias.addBatch(xLoteSentencias[i]);
		}
		//****** 
		//******

		//****** PROMOCIONES: Sinc regalos registrados para la venta
		//******
		registros = new ArrayList<ArrayList<Object>>();
		xCampos = new StringBuffer("");
		xValores = new StringBuffer("");
		regalosRegistrados = BeansSincronizador.getRegalosRegistrados(true, numTransaccion, numCaja);
		try {
			numColumnas = regalosRegistrados.getMetaData().getColumnCount();
			for (int i=0; i<numColumnas; i++){
				xCampos.append(regalosRegistrados.getMetaData().getColumnName(i+1));
				xValores.append("?");
				if(i+1 < numColumnas){
					xCampos.append(", ");
					xValores.append(", ");
				}
			}
			sentenciaSql = new String("insert into regalosregistrados ("+xCampos+") values ("+xValores+")");
			
			while (regalosRegistrados.next()){
				registro = new ArrayList<Object>();
				for (int i=0; i<numColumnas; i++)
					registro.add(regalosRegistrados.getObject(regalosRegistrados.getMetaData().getColumnName(i+1).toLowerCase()));
				registros.add(registro);
			}
		} finally {
			regalosRegistrados.close();
		}
		xLoteSentencias = Conexiones.crearLoteSentencias(sentenciaSql, registros, false);
		for (int i=0; i<xLoteSentencias.length; i++){
			loteSentencias.addBatch(xLoteSentencias[i]);
		}
		//****** 
		//******
		
		//****** PROMOCIONES: Sinc los datos de transacción premiada para la venta
		//******
		registros = new ArrayList<ArrayList<Object>>();
		xCampos = new StringBuffer("");
		xValores = new StringBuffer("");
		transaccionPremiada = BeansSincronizador.getTransaccionPremiada(true, numTransaccion, numCaja);
		try {
			numColumnas = transaccionPremiada.getMetaData().getColumnCount();
			for (int i=0; i<numColumnas; i++){
				xCampos.append(transaccionPremiada.getMetaData().getColumnName(i+1));
				xValores.append("?");
				if(i+1 < numColumnas){
					xCampos.append(", ");
					xValores.append(", ");
				}
			}
			sentenciaSql = new String("insert into transaccionpremiada ("+xCampos+") values ("+xValores+")");
			
			while (transaccionPremiada.next()){
				registro = new ArrayList<Object>();
				for (int i=0; i<numColumnas; i++)
					registro.add(transaccionPremiada.getObject(transaccionPremiada.getMetaData().getColumnName(i+1).toLowerCase()));
				registros.add(registro);
			}
		} finally {
			transaccionPremiada.close();
		}
		xLoteSentencias = Conexiones.crearLoteSentencias(sentenciaSql, registros, false);
		for (int i=0; i<xLoteSentencias.length; i++){
			loteSentencias.addBatch(xLoteSentencias[i]);
		}
		//****** 
		//******
		
		//****** PROMOCIONES: Sinc los datos de las donaciones registradas para la venta
		//******
		registros = new ArrayList<ArrayList<Object>>();
		xCampos = new StringBuffer("");
		xValores = new StringBuffer("");
		donacionesRegistradas = BeansSincronizador.getDonacionesRegistradas(true, numTransaccion, numCaja);
		try {
			numColumnas = donacionesRegistradas.getMetaData().getColumnCount();
			for (int i=0; i<numColumnas; i++){
				xCampos.append(donacionesRegistradas.getMetaData().getColumnName(i+1));
				xValores.append("?");
				if(i+1 < numColumnas){
					xCampos.append(", ");
					xValores.append(", ");
				}
			}
			sentenciaSql = new String("insert into donacionesregistradas ("+xCampos+") values ("+xValores+")");
			
			while (donacionesRegistradas.next()){
				registro = new ArrayList<Object>();
				for (int i=0; i<numColumnas; i++)
					registro.add(donacionesRegistradas.getObject(donacionesRegistradas.getMetaData().getColumnName(i+1).toLowerCase()));
				registros.add(registro);
			}
		} finally {
			donacionesRegistradas.close();
		}
		xLoteSentencias = Conexiones.crearLoteSentencias(sentenciaSql, registros, false);
		for (int i=0; i<xLoteSentencias.length; i++){
			loteSentencias.addBatch(xLoteSentencias[i]);
		}
		//****** 
		//******
		
		//****** PROMOCIONES: Sinc los datos de los pagos de las donaciones registradas para la venta
		//******
		registros = new ArrayList<ArrayList<Object>>();
		xCampos = new StringBuffer("");
		xValores = new StringBuffer("");
		pagosDonaciones = BeansSincronizador.getPagosDeDonaciones(true, numTransaccion, numCaja);
		try {
			numColumnas = pagosDonaciones.getMetaData().getColumnCount();
			for (int i=0; i<numColumnas; i++){
				xCampos.append(pagosDonaciones.getMetaData().getColumnName(i+1));
				xValores.append("?");
				if(i+1 < numColumnas){
					xCampos.append(", ");
					xValores.append(", ");
				}
			}
			sentenciaSql = new String("insert into pagodonacion ("+xCampos+") values ("+xValores+")");
			
			while (pagosDonaciones.next()){
				registro = new ArrayList<Object>();
				for (int i=0; i<numColumnas; i++)
					registro.add(pagosDonaciones.getObject(pagosDonaciones.getMetaData().getColumnName(i+1).toLowerCase()));
				registros.add(registro);
			}
		} finally {
			pagosDonaciones.close();
		}
		xLoteSentencias = Conexiones.crearLoteSentencias(sentenciaSql, registros, false);
		for (int i=0; i<xLoteSentencias.length; i++){
			loteSentencias.addBatch(xLoteSentencias[i]);
		}
		//****** 
		//******
		
		
		return loteSentencias;
	}

	/**
	 * Método getTransaccionesBR
	 * 		Selecciona todos los registros de las transacciones realizadas por Bono Regalo.
	 * @param local - Indicador de la conexión de BD a utilizar: Servidor o Local. 
	 * @param noSincronizadas - Indicador para seleccionar todas las transacciones o sólo las
	 *                          no sincronizadas.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql efectuada. 
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getTransaccionesBR(boolean local, boolean noSincronizadas) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		String sentenciaSql = "";
		if (noSincronizadas){
			sentenciaSql = new String("select br_transaccion.numtienda, br_transaccion.fecha, br_transaccion.numcaja, br_transaccion.numtransaccion " +
					"from br_transaccion where br_transaccion.regactualizado = '" + Sesion.NO + "'");
		}
		else { 
			sentenciaSql = new String("select br_transaccion.numtienda, br_transaccion.fecha, br_transaccion.numcaja, br_transaccion.numtransaccion " +
					"from br_transaccion");
		} 

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, true);
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error al realizar sentencia " + sentenciaSql, e));
		} catch (ConexionExcepcion e) {
		}
		return resultado;
	}

	/**
	 * Método syncTransaccionesBR
	 * 		Sincroniza las transacciones (cabecera, detalle de productos y pagos) de Bonos Regalo Electrónico 
	 * desde la Caja Registradora hacia el Servidor de tienda.
	 * @param numTransacciones - Lista de los numeros correspondientes a las transacciones de Bonos Regalo de 
	 * 		la caja que no han sido actualizadas.
	 * @throws BaseDeDatosExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList y hashMap
	* Fecha: agosto 2011
	*/
	public static synchronized void syncTransaccionesBR() throws BaseDeDatosExcepcion{
		ResultSet detalleProductos, detallePagos, transaccionBR;
		ResultSet numTransaccionesBR = null;
		ArrayList <ArrayList<Object>>registros;
		ArrayList <Object>registro;
		String sentenciaSql;
		String[] xLoteSentencias;
		Statement loteSentenciasSrv = null;
		Statement loteSentenciasCR = null;
		StringBuffer xCampos, xValores;
		int numTienda = 0, numCaja = 0, numTransaccion = 0;
		Date fecha = null;
		String estadoOld = "";
		try {
			// Obtenemos transacciones por sincronizar
			numTransaccionesBR = BeansSincronizador.getTransaccionesBR(true, true);
			numTransaccionesBR.beforeFirst();
			
			loteSentenciasSrv = Conexiones.crearSentencia(false);
			loteSentenciasCR = Conexiones.crearSentencia(true);
			HashMap<String,Object> criterioClave = new HashMap<String,Object>();

			while(numTransaccionesBR.next()){
				numTienda = numTransaccionesBR.getInt("br_transaccion.numtienda");
				fecha = numTransaccionesBR.getDate("br_transaccion.fecha");
				numCaja = numTransaccionesBR.getInt("br_transaccion.numcaja");
				numTransaccion = numTransaccionesBR.getInt("br_transaccion.numtransaccion");
				
				// Eliminamos los registros existentes en base de datos central
				deleteTransaccionesBR(false, numTienda, fecha, numCaja, numTransaccion);
				
				// Obtengo información de la CR de la transacción de Bonos Regalo actual
				int numColumnas = 0;
				transaccionBR = getTransaccionBR(true, numTienda, fecha, numCaja, numTransaccion);
				try {
					xCampos = new StringBuffer("");
					xValores = new StringBuffer("");
					numColumnas = transaccionBR.getMetaData().getColumnCount();
					for (int i=0; i<numColumnas; i++){
						xCampos.append(transaccionBR.getMetaData().getColumnName(i+1));
						xValores.append("?");
						if(i+1 < numColumnas){
							xCampos.append(", ");
							xValores.append(", ");
						}
					}
					sentenciaSql = new String("insert into br_transaccion ("+xCampos+") values ("+xValores+")");
					
					if (transaccionBR.first()) {
						registros = new ArrayList<ArrayList<Object>>();
						registro = new ArrayList<Object>();
						
						criterioClave.put("numTienda", transaccionBR.getObject("numtienda"));
						criterioClave.put("fecha", transaccionBR.getObject("fecha"));
						criterioClave.put("numCaja", transaccionBR.getObject("numcaja"));
						criterioClave.put("numTransaccion", transaccionBR.getObject("numtransaccion"));
						
						for (int i=0; i<numColumnas; i++){
							registro.add(transaccionBR.getObject(transaccionBR.getMetaData().getColumnName(i+1).toLowerCase()));
							try{
								if(transaccionBR.getMetaData().getColumnName(i+1).toLowerCase().equalsIgnoreCase("estadotransaccion"))
									estadoOld = transaccionBR.getString((transaccionBR.getMetaData().getColumnName(i+1).toLowerCase()));
							} catch(Exception e){
								
							}
						}
						registros.add(registro);

						xLoteSentencias = Conexiones.crearLoteSentencias(sentenciaSql, registros, false);
						for (int i=0; i<xLoteSentencias.length; i++){
							loteSentenciasSrv.addBatch(xLoteSentencias[i]);
						}
					}
				} finally {
					transaccionBR.close();
				}
				
				// Obtengo el detalle de renglones de la transaccion de Bonos Regalo
				registros = new ArrayList<ArrayList<Object>>();
				xCampos = new StringBuffer("");
				xValores = new StringBuffer("");
				detalleProductos = BeansSincronizador.getDetalleTransaccionBR(true, numTienda, fecha, numCaja, numTransaccion);
				try {
					numColumnas = detalleProductos.getMetaData().getColumnCount();
					for (int i=0; i<numColumnas; i++){
						xCampos.append(detalleProductos.getMetaData().getColumnName(i+1));
						xValores.append("?");
						if(i+1 < numColumnas){
							xCampos.append(", ");
							xValores.append(", ");
						}
					}
					sentenciaSql = new String("insert into br_detalletransaccion ("+xCampos+") values ("+xValores+")");

					while (detalleProductos.next()){
						registro = new ArrayList<Object>();
						for (int i=0; i<numColumnas; i++)
							registro.add(detalleProductos.getObject(detalleProductos.getMetaData().getColumnName(i+1).toLowerCase()));
						registros.add(registro);
					}
					xLoteSentencias = Conexiones.crearLoteSentencias(sentenciaSql, registros, false);
					for (int i=0; i<xLoteSentencias.length; i++){
						loteSentenciasSrv.addBatch(xLoteSentencias[i]);
					}
				} finally {
					detalleProductos.close();
				}
				
				// Obtengo el detalle de pagos de la transacción
				registros = new ArrayList<ArrayList<Object>>();
				xCampos = new StringBuffer("");
				xValores = new StringBuffer("");
				detallePagos = BeansSincronizador.getPagoTransaccionBR(true, numTienda, fecha, numCaja, numTransaccion);
				try {
					numColumnas = detallePagos.getMetaData().getColumnCount();
					for (int i=0; i<numColumnas; i++){
						xCampos.append(detallePagos.getMetaData().getColumnName(i+1));
						xValores.append("?");
						if(i+1 < numColumnas){
							xCampos.append(", ");
							xValores.append(", ");
						}
					}
					sentenciaSql = new String("insert into br_pagodetransaccion ("+xCampos+") values ("+xValores+")");
		
					while (detallePagos.next()){
						registro = new ArrayList<Object>();
						for (int i=0; i<numColumnas; i++){
							if(((String)detallePagos.getMetaData().getColumnName(i+1).toLowerCase()).equalsIgnoreCase("regactualizado"))
								registro.add("N");
							else
								registro.add(detallePagos.getObject(detallePagos.getMetaData().getColumnName(i+1).toLowerCase()));
						}
						registros.add(registro);
					}

					xLoteSentencias = Conexiones.crearLoteSentencias(sentenciaSql, registros, false);
					for (int i=0; i<xLoteSentencias.length; i++){
						loteSentenciasSrv.addBatch(xLoteSentencias[i]);
					}
				} finally {
					detallePagos.close();
				}

				// Indico actualización de los registros de transacción en la CR
				String estadoActual ="";
				try{
					ResultSet transaccionBRPost = getTransaccionBR(true, numTienda, fecha, numCaja, numTransaccion);
					if(transaccionBRPost.first()){
						estadoActual = transaccionBRPost.getString("estadotransaccion");
					}
				} catch(Exception e){
					
				}
				
				if(estadoActual.equalsIgnoreCase(estadoOld)){
					sentenciaSql = new String("update br_transaccion set regactualizado='" + Sesion.SI + "' where (numtienda = "+criterioClave.get("numTienda")+") and (numcaja = "+criterioClave.get("numCaja")+") and (numtransaccion = "+criterioClave.get("numTransaccion")+") and (fecha = '"+criterioClave.get("fecha")+"')");
					loteSentenciasCR.addBatch(sentenciaSql);
				}
				sentenciaSql = new String("update br_detalletransaccion set regactualizado='" + Sesion.SI + "' where (numtienda = "+criterioClave.get("numTienda")+") and (numcaja = "+criterioClave.get("numCaja")+") and (numtransaccion = "+criterioClave.get("numTransaccion")+") and (fecha = '"+criterioClave.get("fecha")+"')");
				loteSentenciasCR.addBatch(sentenciaSql);
				sentenciaSql = new String("update br_pagodetransaccion set regactualizado='" + Sesion.SI + "' where (numtienda = "+criterioClave.get("numTienda")+") and (numcaja = "+criterioClave.get("numCaja")+") and (numtransaccion = "+criterioClave.get("numTransaccion")+") and (fecha = '"+criterioClave.get("fecha")+"')");
				loteSentenciasCR.addBatch(sentenciaSql);
			
				// Ejecuto lote de sentencias en el DBMS del Servidor y de la CR
				Connection connSrv = Conexiones.getConexion(false);
				Connection connCR = Conexiones.getConexion(true);
				try {
					connCR.setAutoCommit(false);
					connSrv.setAutoCommit(false);
					connSrv.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
					connCR.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
					Conexiones.ejecutarLoteSentencias(loteSentenciasSrv, false, false);
					Conexiones.ejecutarLoteSentencias(loteSentenciasCR, true, false);
					connSrv.commit();
					connCR.commit();
				} catch (Exception e) {
					connCR.rollback();
					connSrv.rollback();
					throw (new BaseDeDatosExcepcion("Error en sentencia por lote al sincronizar transacciones BR"));
				} finally {
					connCR.setAutoCommit(true);
					connSrv.setAutoCommit(true);
				}
				loteSentenciasSrv.clearBatch();
				loteSentenciasCR.clearBatch();
			}
		} catch (BatchUpdateException e) {
			e.printStackTrace();
			throw (new BaseDeDatosExcepcion("Error en sentencia por lote al sincronizar transacciones"));
		} catch (SQLException e) {
			e.printStackTrace();
			throw (new BaseDeDatosExcepcion("Error en sentencia SQL al sincronizar transacciones"));
		} catch (BaseDeDatosExcepcion e) {
			e.printStackTrace();
			throw (new BaseDeDatosExcepcion("Error con la BD al sincronizar transacciones"));
		} catch (ConexionExcepcion e) {
			e.printStackTrace();
			throw (new BaseDeDatosExcepcion("Error de conexión con BD al sincronizar transacciones"));
		} catch (Throwable t) {
			t.printStackTrace();
		} finally{
			try {
				if(loteSentenciasCR != null)
					loteSentenciasCR.close();
			} catch (SQLException e1) {
			}
			
			try {
				if(loteSentenciasSrv != null)
					loteSentenciasSrv.close();
			} catch (SQLException e1) {
			}
			try {
				if (numTransaccionesBR != null) {
					numTransaccionesBR.close();
					numTransaccionesBR = null;
				}
			} catch (SQLException e1) {
			}
		}
	}

	/**
	 * Método getTransaccion
	 * 		Selecciona la información correspondiente a la transacción indicada. 
	 * @param local - Indicador de la conexión de BD a utilizar: Servidor o Local.
	 * @param numTienda - Numero de la Tienda que se quiere recuperar el registro de cabecera.
	 * @param fecha - Fecha de la transaccion de BR de la que se quiere recuperar el registro de cabecera.
	 * @param numCaja - Caja de la que se quiere recuperar el registro de cabecera.
	 * @param numTransaccion - Transacción de la que se quiere recuperar el registro de cabecera.
	 * @return ResultSet - Retorna el registro correspondiente a la transacción.
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getTransaccionBR(boolean local, int numTienda, Date fecha, int numCaja, int numTransaccion) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sentenciaSql = new String("select * from br_transaccion where br_transaccion.numtienda = " + numTienda +
				" and br_transaccion.fecha = '" + df.format(fecha) + "' and br_transaccion.numcaja = " + numCaja + 
				" and br_transaccion.numtransaccion = " + numTransaccion + " and br_transaccion.regactualizado='" + Sesion.NO + "'");
		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, false);
			resultado.first();
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error al realizar sentencia " + sentenciaSql, e));
		} catch (ConexionExcepcion e) {
		} catch (SQLException e) {
		}
		return resultado;
	}

	/**
	 * Método getDetalleTransaccionBR
	 * 		Selecciona todos los detalles de productos de la transaccion de Bono Regalo indicada.
	 * @param local - Indicador de la conexión de BD a utilizar: Servidor o Local. 
	 * @param numTienda - Numero de la Tienda que se quiere recuperar el registro del detalle.
	 * @param fecha - Fecha de la transaccion de BR de la que se quiere recuperar el registro del detalle.
	 * @param numCaja - Caja de la que se quiere recuperar el registro del detalle.
	 * @param numTransaccion - Transacción de la que se quiere recuperar el registro del detalle.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql efectuada. 
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getDetalleTransaccionBR(boolean local, int numTienda, Date fecha, int numCaja, int numTransaccion) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sentenciaSql = new String("select br_detalletransaccion.* from br_detalletransaccion where " +
				"br_detalletransaccion.numtienda = " + numTienda + " and br_detalletransaccion.fecha = '" + df.format(fecha) + "' and " +
				"br_detalletransaccion.numcaja = " + numCaja + " and br_detalletransaccion.numtransaccion = " + numTransaccion);

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, true);
			resultado.beforeFirst();
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de BD al realizar sentencia " + sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de Conexión al realizar sentencia " + sentenciaSql, e));
		} catch (SQLException e) {
			try {
				resultado.close();
			} catch (SQLException e1) {
			}
			throw (new BaseDeDatosExcepcion("Error de SQL al realizar sentencia " + sentenciaSql, e));
		}
		return resultado;
	}

	/**
	 * Método getPagoTransaccionBR
	 * 		Selecciona todos los detalles de productos de la transaccion de Bono Regalo indicada.
	 * @param local - Indicador de la conexión de BD a utilizar: Servidor o Local. 
	 * @param numTienda - Numero de la Tienda que se quiere recuperar el registro de pagos.
	 * @param fecha - Fecha de la transaccion de BR de la que se quiere recuperar el registro de pagos.
	 * @param numCaja - Caja de la que se quiere recuperar el registro de pagos.
	 * @param numTransaccion - Transacción de la que se quiere recuperar el registro de pagos.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql efectuada. 
	 * @throws BaseDeDatosExcepcion
	 */
	public static ResultSet getPagoTransaccionBR(boolean local, int numTienda, Date fecha, int numCaja, int numTransaccion) throws BaseDeDatosExcepcion {
		ResultSet resultado = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sentenciaSql = new String("select br_pagodetransaccion.* from br_pagodetransaccion where " +
				"br_pagodetransaccion.numtienda = " + numTienda + " and br_pagodetransaccion.fecha = '" + df.format(fecha) + "' and " +
				"br_pagodetransaccion.numcaja = " + numCaja + " and br_pagodetransaccion.numtransaccion = " + numTransaccion);

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, true);
			resultado.beforeFirst();
		} catch (BaseDeDatosExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de BD al realizar sentencia " + sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de Conexión al realizar sentencia " + sentenciaSql, e));
		} catch (SQLException e) {
			try {
				resultado.close();
			} catch (SQLException e1) {
			}
			throw (new BaseDeDatosExcepcion("Error de SQL al realizar sentencia " + sentenciaSql, e));
		}
		return resultado;
	}

	/**
	 * Elimina de la bd los detalles asociados a la transaccion de BR
	 * @param local - Indicador de la conexión de BD a utilizar: Servidor o Local. 
	 * @param numTienda - Numero de la Tienda que se quiere recuperar el registro de pagos.
	 * @param fecha - Fecha de la transaccion de BR de la que se quiere recuperar el registro de pagos.
	 * @param numCaja - Caja de la que se quiere recuperar el registro de pagos.
	 * @param numTransaccion - Transacción de la que se quiere recuperar el registro de pagos.
	 * @throws ConexionExcepcion 
	 * @throws SQLException 
	 * @throws HibernateException 
	 * @throws BaseDeDatosExcepcion 
	 */
	public static void deleteTransaccionesBR(boolean local, int numTienda, Date fecha, int numCaja, int numTransaccion) 
			throws ConexionExcepcion, SQLException, HibernateException, BaseDeDatosExcepcion {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String where = "numtienda = " + numTienda + " and fecha = '" + df.format(fecha) + "' and numcaja = " +
				numCaja + " and numtransaccion = " + numTransaccion;
		Conexiones.realizarSentencia("delete from br_transaccion where " + where, local);
	}

	/**
	 * Método syncDevolucionVenta
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void syncComprobanteFiscalBR() throws SQLException, BaseDeDatosExcepcion {
			
		String[] clave = {"numtienda", "fecha", "numcaja", "numtransaccion", "numcomprobantefiscal", "tipocomprobante"};
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(), "br_comprobantefiscal", clave, false, true);
		BeansSincronizador.modoReplicacion = ReplicationMode.IGNORE;
		BeansSincronizador.syncEntidad(Sesion.SINC_SERVCENTRAL, entidad);
		BeansSincronizador.modoReplicacion = ReplicationMode.LATEST_VERSION;
	}
	
}
