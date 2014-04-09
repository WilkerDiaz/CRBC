/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.mediadores
 * Programa   : Conexiones.java
 * Creado por : gmartinelli
 * Creado en  : 04/03/2004 10:14 AM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1.0
 * Fecha       : 26/05/2004 02:31:34 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : - Modificado método crearLoteSentencias de modo que retorne el arreglo de
 * 				sentencias generadas manualmente y no extrayendo las sentencias de la instancia
 * 				de la sentencia preparada.
 * 				- Agregado el método getSentenciaSelect que retorna una sentencia <select> a 
 * 				a partir de una <insert> o una <update> indicada.
 * =============================================================================
 */
package com.becoblohm.cr.mediadoresbd;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ConexionLocalExcepcion;
import com.becoblohm.cr.excepciones.ConexionServidorExcepcion;
import com.becoblohm.cr.manejadorsesion.Sesion;

/** 
 * 		Maneja las operaciones de conexiones a las base de datos, tanto . Funciona 
 * como mediador entre la aplicacion y la Base de Datos. Ejecuta sentencias 
 * simples y sentencias preparadas para actualización de tablas en la base 
 * de datos. También proporciona métodos para inicialización de la conexión 
 * conexion la base de datos y para el control de cada una de sus propiedades.
 */
public class ConexionesLR {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ConexionesLR.class);
	
	private static boolean conectadoLocal = false;
	private static boolean conectadoServidor = false;
	private static boolean conectadoSyncLocal = false;
	private static boolean conectadoSyncServidor = false;
	private static Connection conexionLocal;
	private static Connection conexionServidor;
	private static boolean conectadoAudit = false;
	private static Connection conexionSyncLocal;
	private static Connection conexionSyncServidor;
		
	/**
	 * Constructor para Conexiones.
	 * 		Establece los parámetros (clase, url, usuario, clave) para 
	 * conexión a la base de datos tanto local como del servidor central de
	 * la tienda.
	 */
	public ConexionesLR(String dbUrlTienda) throws ConexionExcepcion {
		conectar(dbUrlTienda);
	}

	private static void conectar(String dbUrlTienda) throws ConexionExcepcion {
		if(Sesion.isCajaEnLinea()){
			try {
				Class.forName(Sesion.getDbClaseServidor());
			} catch (ClassNotFoundException ex) {
				conectadoServidor = false;
				throw (new ConexionExcepcion("Falla en la conexión con la Base de Datos Remota. No se encuentra el Driver.", ex));
			}
			try {
				conexionServidor = DriverManager.getConnection(dbUrlTienda, Sesion.getDbUsuario(), Sesion.getDbClave());
				conectadoServidor = true;
			} catch (SQLException ex) {
				conectadoServidor = false; 
				throw (new ConexionServidorExcepcion("Falla en la conexion a la BD Remota. Servidor inactivo, Url, Usuario o contraseña no válida. No se pudo conectar", ex));
			}
		} else {
			conectadoServidor = false;
			throw (new ConexionServidorExcepcion("Falla en la conexión con la Base de Datos Remota. Conexion no disponible"));				
		}
	}
	
	private static void conectar(boolean local, boolean sync) throws ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("conectar(boolean, boolean) - start");
		}

		conectar(local, false, null, null, sync);

		if (logger.isDebugEnabled()) {
			logger.debug("conectar(boolean, boolean) - end");
		}
	}

	private static void conectar(boolean local, boolean audit, String host, Properties props, boolean sync) throws ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("conectar(boolean, boolean, String, Properties, boolean) - start");
		}

//		Properties dbProps = new Properties();
		if (audit) {
			// Establece una conexion para el visor de auditoria.
			conectadoAudit = true;
			try {
				Class.forName(Sesion.getDbClaseLocal());
			} catch (ClassNotFoundException e) {
				logger
						.error(
								"conectar(boolean, boolean, String, Properties, boolean)",
								e);

				conectadoLocal = false;
				throw new ConexionExcepcion("Error al cargar el driver de base de datos");
			}
//			String url = "jdbc:"+ props.getProperty("db.protocolo", "mysql") +"://"+ host +"/" + props.getProperty("db.esquema", "CR");

//			String user = props.getProperty("db.user", "root");
//			String passwd = props.getProperty("db.passwd", "");
			try {
				conexionLocal = DriverManager.getConnection(Sesion.getDbUrlLocal(), Sesion.getDbUsuario(), Sesion.getDbClave());
				conectadoLocal = true;
			} catch (SQLException ex) {
				logger
						.error(
								"conectar(boolean, boolean, String, Properties, boolean)",
								ex);

				conectadoLocal = false; 
				throw (new ConexionLocalExcepcion("Falla en la conexion a la BD Local. Servidor inactivo, Url, Usuario o contraseña no válida. No se pudo conectar", ex));
			}
			
			
		} else {
			if (!conectadoAudit) {
				if (sync) {
					if (local) { // Se establece la conexion con la BD local (Base de Datos de Caja)
						try {
							Class.forName(Sesion.getDbClaseLocal());
						} catch (ClassNotFoundException ex) {
							logger
									.error(
											"conectar(boolean, boolean, String, Properties, boolean)",
											ex);

							conectadoSyncLocal = false;
							throw (new ConexionExcepcion("Falla en la conexión con la Base de Datos Local. No se encuentra el Driver: " , ex));
						}
						try {
							conexionSyncLocal = DriverManager.getConnection(Sesion.getDbUrlLocal(), Sesion.getDbUsuario(), Sesion.getDbClave());
							conectadoSyncLocal = true;
						} catch (SQLException ex) {
							logger
									.error(
											"conectar(boolean, boolean, String, Properties, boolean)",
											ex);

							conectadoSyncLocal = false; 
							throw (new ConexionExcepcion("Falla en la conexion a la BD Local. Servidor inactivo, Url, Usuario o contraseña no válida. No se pudo conectar", ex));
						}
					} else { // Se establece la conexion con la BD remota (Servidor central de tienda)
						if(Sesion.isCajaEnLinea()){
							try {
								Class.forName(Sesion.getDbClaseServidor());
							} catch (ClassNotFoundException ex) {
								logger
										.error(
												"conectar(boolean, boolean, String, Properties, boolean)",
												ex);

								conectadoSyncServidor = false;
								throw (new ConexionExcepcion("Falla en la conexión con la Base de Datos Remota. No se encuentra el Driver.", ex));
							}
							try {
//								dbProps.setProperty("user", Sesion.getDbUsuario());
//								dbProps.setProperty("password", Sesion.getDbClave());
//								dbProps.setProperty("date format", "iso");
//								conexionSyncServidor = DriverManager.getConnection(Sesion.getDbUrlServidor(),  dbProps);
								conexionSyncServidor = DriverManager.getConnection(Sesion.getDbUrlServidor(), Sesion.getDbUsuario(), Sesion.getDbClave());
								conectadoSyncServidor = true;
							} catch (SQLException ex) {
								logger
										.error(
												"conectar(boolean, boolean, String, Properties, boolean)",
												ex);

								conectadoSyncServidor = false; 
								throw (new ConexionServidorExcepcion("Falla en la conexion a la BD Remota. Servidor inactivo, Url, Usuario o contraseña no válida. No se pudo conectar", ex));
							}
						} else {
							conectadoSyncServidor = false;
							throw (new ConexionServidorExcepcion("Falla en la conexión con la Base de Datos Remota. Conexion no disponible"));				
						}	
					}
					
				} else {
					if (local) { // Se establece la conexion con la BD local (Base de Datos de Caja)
						try {
							Class.forName(Sesion.getDbClaseLocal());
						} catch (ClassNotFoundException ex) {
							logger
									.error(
											"conectar(boolean, boolean, String, Properties, boolean)",
											ex);

							conectadoLocal = false;
							throw (new ConexionExcepcion("Falla en la conexión con la Base de Datos Local. No se encuentra el Driver: " , ex));
						}
						try {
							conexionLocal = DriverManager.getConnection(Sesion.getDbUrlLocal(), Sesion.getDbUsuario(), Sesion.getDbClave());
							conectadoLocal = true;
						} catch (SQLException ex) {
							logger
									.error(
											"conectar(boolean, boolean, String, Properties, boolean)",
											ex);

							conectadoLocal = false; 
							throw (new ConexionExcepcion("Falla en la conexion a la BD Local. Servidor inactivo, Url, Usuario o contraseña no válida. No se pudo conectar", ex));
						}
					} else { // Se establece la conexion con la BD remota (Servidor central de tienda)
						if(Sesion.isCajaEnLinea()){
							try {
								Class.forName(Sesion.getDbClaseServidor());
							} catch (ClassNotFoundException ex) {
								logger
										.error(
												"conectar(boolean, boolean, String, Properties, boolean)",
												ex);

								conectadoServidor = false;
								throw (new ConexionExcepcion("Falla en la conexión con la Base de Datos Remota. No se encuentra el Driver.", ex));
							}
							try {
//								dbProps.setProperty("user", Sesion.getDbUsuario());
//								dbProps.setProperty("password", Sesion.getDbClave());
//								dbProps.setProperty("date format", "iso");
//								conexionServidor = DriverManager.getConnection(Sesion.getDbUrlServidor(),  dbProps);
								conexionServidor = DriverManager.getConnection(Sesion.getDbUrlServidor(), Sesion.getDbUsuario(), Sesion.getDbClave());
								conectadoServidor = true;
							} catch (SQLException ex) {
								logger
										.error(
												"conectar(boolean, boolean, String, Properties, boolean)",
												ex);

								conectadoServidor = false; 
								throw (new ConexionServidorExcepcion("Falla en la conexion a la BD Remota. Servidor inactivo, Url, Usuario o contraseña no válida. No se pudo conectar", ex));
							}
						} else {
							conectadoServidor = false;
							throw (new ConexionServidorExcepcion("Falla en la conexión con la Base de Datos Remota. Conexion no disponible"));				
						}	
					}
					
				}
			} else {
				throw new ConexionExcepcion("No puede realizarse conexion. Abierta conexion de auditoria");
			}
		}

		if (logger.isDebugEnabled()) {
			logger
					.debug("conectar(boolean, boolean, String, Properties, boolean) - end");
		}
	}	

	/**
	 * Método realizarConsulta.
	 * 		Ejecuta una sentencia SQL de consulta de registros (Selects).
	 * @param sentenciaSql - Sentencia SELECT
	 * @param local - Indicador de lugar a realizar la sentencia (true BdLocal, false BdRemota)
	 * @return ResultSet - Objeto con las filas resultantes de la consulta
	 * @throws ConexionExcepcion - Si el servidor a la base de datos no se encuentra disponible
	 * @throws BaseDeDatosExcepcion - Cuando ocurre un error en la ejecucion
	 * 		de la consulta
	 */
	public static ResultSet realizarConsulta(String sentenciaSql, boolean local) throws ConexionExcepcion, BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("realizarConsulta(String, boolean) - start");
		}

		ResultSet returnResultSet = realizarConsulta(sentenciaSql, local, false);
		if (logger.isDebugEnabled()) {
			logger.debug("realizarConsulta(String, boolean) - end");
		}
		return returnResultSet;
	}	
	
	/**
	 * Método realizarConsulta.
	 * 		Ejecuta una sentencia SQL de consulta de registros (Selects) con la capacidad de desplazamiento
	 * y actualización o no de los datos resultantes.
	 * 
	 * @param sentenciaSql - Sentencia SELECT
	 * @param local - Indicador de lugar a realizar la sentencia (true BdLocal, false BdRemota)
	 * @return ResultSet - Objeto con las filas resultantes de la consulta
	 * @throws ConexionExcepcion - Si el servidor a la base de datos no se encuentra disponible
	 * @throws BaseDeDatosExcepcion - Cuando ocurre un error en la ejecucion
	 * 		de la consulta
	 */
	static ResultSet realizarConsulta(String sentenciaSql, boolean local, boolean actualizable) throws ConexionExcepcion, BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("realizarConsulta(String, boolean, boolean) - start");
		}

		ResultSet returnResultSet = realizarConsulta(sentenciaSql, local,
				actualizable, false);
		if (logger.isDebugEnabled()) {
			logger.debug("realizarConsulta(String, boolean, boolean) - end");
		}
		return returnResultSet;
	}

	public static ResultSet realizarConsulta(String sentenciaSql, boolean local, boolean actualizable, boolean sync) throws ConexionExcepcion, BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("realizarConsulta(String, boolean, boolean, boolean) - start");
		}

		ResultSet resultado = null;
		Connection conexionUtilizada = null;
		boolean conexionActiva;

		if (sync) {
			if (local) conexionActiva = conectadoSyncLocal;
			else conexionActiva = conectadoSyncServidor;
		} else {
			if (local) conexionActiva = conectadoLocal;
			else conexionActiva = conectadoServidor;
		}
		
		// Si la conexion no esta activa la creamos
		if (!conexionActiva)
			conectar(local,sync);
		if (sync) {
			if (local) conexionUtilizada = conexionSyncLocal;
			else conexionUtilizada = conexionSyncServidor;
		} else {
			if (local) conexionUtilizada = conexionLocal;
			else conexionUtilizada = conexionServidor;
		}

		// Realizamos la operacion
		try {
			Statement sentencia = null;
			try {
				if (actualizable)
					sentencia = conexionUtilizada.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				else sentencia = conexionUtilizada.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			} catch (SQLException e) {
				logger.error(
						"realizarConsulta(String, boolean, boolean, boolean)",
						e);

				if (local) {
					throw new ConexionLocalExcepcion("Error al conectar a BD", e);
				} else {
					throw new ConexionServidorExcepcion("Error al conectar al servidor", e);
				}
			}
			resultado = null;

			if (logger.isDebugEnabled()) {
				logger.debug("realizarConsulta(sentenciaSql = " + sentenciaSql
						+ ", local = " + local + ", sync = " + sync
						+ ") - Consulta SQL");
			}

			resultado = new CRResultSet(sentencia.executeQuery(sentenciaSql));
			resultado.first();
		} catch (SQLException exSQL){
			if (resultado != null) {
				try {
					resultado.close();
				} catch (SQLException e) {
					logger
							.error(
									"realizarConsulta(String, boolean, boolean, boolean)",
									e);
				}
			}
			logger.error("realizarConsulta(String, boolean, boolean, boolean)",
					exSQL);
			conectar(local, sync);
			throw (new BaseDeDatosExcepcion("Error al realizar sentencia", exSQL));
		}

		if (logger.isDebugEnabled()) {
			logger
					.debug("realizarConsulta(String, boolean, boolean, boolean) - end");
		}
		return resultado;
	}
	
	
	/**
	 * Método realizarSentencia.
	 * 		Realiza las sentencias de insersion y actualizacion en la base de datos
	 * @param sentenciaSql - Sentencia SQL a realizar
	 * @param local - Indicador de lugar a realizar la sentencia (true BdLocal, false BdRemota)
	 * @throws ConexionExcepcion - Si el servidor a la base de datos no se encuentra disponible
	 * @throws BaseDeDatosExcepcion - Cuando ocurre un error en la ejecucion de la sentencia
	 */
	static int realizarSentencia(String sentenciaSql, boolean local) throws ConexionExcepcion, BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("realizarSentencia(String, boolean) - start");
		}

		int returnint = realizarSentencia(sentenciaSql, local, false);
		if (logger.isDebugEnabled()) {
			logger.debug("realizarSentencia(String, boolean) - end");
		}
		return returnint;
	}

	
	static int realizarSentencia(String sentenciaSql, boolean local, boolean sync) throws ConexionExcepcion, BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("realizarSentencia(String, boolean, boolean) - start");
		}

		Connection conexionUtilizada;
		conexionUtilizada = getConexion(local, sync);
		// Realizamos la operacion
		PreparedStatement sentencia = null;
		try {
			sentencia = conexionUtilizada.prepareStatement(sentenciaSql);
		} catch (SQLException exSQL) {
			logger.error("realizarSentencia(String, boolean, boolean)", exSQL);
			if (local) 
				throw new ConexionLocalExcepcion("Error de Conexion", exSQL);
			else
				throw new ConexionServidorExcepcion("Error de Conexion", exSQL);
		}
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("realizarSentencia(sentenciaSql = " + sentenciaSql
						+ ", local = " + local + ", sync = " + sync
						+ ") - Sentencia ejecutada");
			}

			int returnint = sentencia.executeUpdate();
			if (logger.isDebugEnabled()) {
				logger.debug("realizarSentencia(String, boolean, boolean) - end");
			}
			return returnint;
		} catch (SQLException exSQL) {
			logger.error("realizarSentencia(String, boolean, boolean)", exSQL);
			throw (new BaseDeDatosExcepcion("Error al ejecutar sentencia", exSQL));
		} finally {
			try {
				sentencia.close();
			} catch (SQLException e) {
				logger.error("realizarSentencia(String, boolean, boolean)", e);
			}
			sentencia = null;
		}
	}
	
	/**
	 * Método realizarLoteSentencias
	 * 		Ejecuta un lote de sentencias SQL según los valores indicados.
	 * @param sentenciaSql - Sentencia SQL preparada.
	 * @param local - Indicador del lugar a realizar la sentencia (true BdLocal, false BdRemota).
	 * @param valores - Lista de valores correspondientes a los parámetros que serán actualizados.
	 * @param atomicas - Indicador para ejecución de sentencias atómicas (transaccionales).
	 * @return int[] - Lista de números de filas afectadas por cada sentencia.
	 * @throws ConexionExcepcion
	 * @throws BaseDeDatosExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	static int[] realizarLoteSentencias(String sentenciaSql, boolean local, ArrayList <ArrayList<Object>>valores, boolean atomicas) throws ConexionExcepcion, BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("realizarLoteSentencias(String, boolean, ArrayList, boolean) - start");
		}

		int[] returnintArray = realizarLoteSentencias(sentenciaSql, local,
				valores, atomicas, false);
		if (logger.isDebugEnabled()) {
			logger
					.debug("realizarLoteSentencias(String, boolean, ArrayList, boolean) - end");
		}
		return returnintArray;
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	static int[] realizarLoteSentencias(String sentenciaSql, boolean local, ArrayList <ArrayList<Object>>valores, boolean atomicas, boolean sync) throws ConexionExcepcion, BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("realizarLoteSentencias(String, boolean, ArrayList, boolean, boolean) - start");
		}

		Connection conexionUtilizada = getConexion(local, sync);
		int[] filasAfectadas = null;

		// Realizamos la operación
		PreparedStatement sentenciaPreparada = null;
		try {
			if (atomicas){
				conexionUtilizada.setAutoCommit(false);
				conexionUtilizada.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			}
			
			sentenciaPreparada = conexionUtilizada.prepareStatement(sentenciaSql);

			if (logger.isDebugEnabled()) {
				logger.debug("realizarLoteSentencias(sentenciaSql = "
						+ sentenciaSql + ", local = " + local + ", sync = "
						+ sync + ", veces = " + valores.size() + ") - Sentencia para ejecutarse por lotes");
			}

			int numParams = numParams(sentenciaSql);
			for (int i=0; i<valores.size(); i++){
				if (numParams > 1)
					for (int j=0; j<numParams; j++)
						sentenciaPreparada.setObject(j+1, ((ArrayList<Object>)valores.get(i)).get(j)); 
				else sentenciaPreparada.setObject(1, valores.get(i));
				sentenciaPreparada.addBatch();
				//System.out.println(sentenciaPreparada.toString());
			}
			filasAfectadas = sentenciaPreparada.executeBatch();
			if (atomicas){
				conexionUtilizada.commit();
				conexionUtilizada.setAutoCommit(true);
			}
		} catch (SQLException exSQL) {
			logger
					.error(
							"realizarLoteSentencias(String, boolean, ArrayList, boolean, boolean)",
							exSQL);

			try {
				if (atomicas){
					conexionUtilizada.rollback();
					conexionUtilizada.setAutoCommit(true);
				}
			} catch (SQLException e) {
				logger
						.error(
								"realizarLoteSentencias(String, boolean, ArrayList, boolean, boolean)",
								e);

				e.printStackTrace(System.out);
			}
			conectar(local, sync);
		} finally {
			if (sentenciaPreparada != null) {
				try {
					sentenciaPreparada.close();
				} catch (SQLException e) {
					logger
							.error(
									"realizarLoteSentencias(String, boolean, ArrayList, boolean, boolean)",
									e);
				}
				sentenciaPreparada = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger
					.debug("realizarLoteSentencias(String, boolean, ArrayList, boolean, boolean) - end");
		}
		return filasAfectadas;
	}
	
	/**
	 * Método crearLoteSentencias
	 * 		Crea un lote de sentencias a partir de una sentencia preparada donde se cargaran los valores
	 * para sus parámetros.
	 * @param sentenciaSql - Sentencia SQL preparada
	 * @param local - Indicador de lugar a realizar la sentencia (true BdLocal, false BdRemota)
	 * @param valores - Lista de valores que serán actualizados, estos pudiesen ser una lista con los valores
	 * 		para cada fila o sentencia a ejecutar.
	 * @return int[] - Lista de números de filas afectadas por cada sentencia
	 * @throws ConexionExcepcion
	 * @throws BaseDeDatosExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	static String[] crearLoteSentencias(ResultSetMetaData columnsSentenciaSql, String sentenciaSql, ArrayList <ArrayList<Object>>valores, boolean local) throws ConexionExcepcion, BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("crearLoteSentencias(String, ArrayList, boolean) - start");
		}

		String[] returnStringArray = crearLoteSentencias(columnsSentenciaSql, sentenciaSql, valores,
				local, false);
		if (logger.isDebugEnabled()) {
			logger
					.debug("crearLoteSentencias(String, ArrayList, boolean) - end");
		}
		return returnStringArray;
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	static String[] crearLoteSentencias(ResultSetMetaData columnsSentenciaSql, String sentenciaSql, ArrayList <ArrayList<Object>>valores, boolean local, boolean sync) throws ConexionExcepcion, BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("crearLoteSentencias(String, ArrayList, boolean, boolean) - start");
		}

		String[] sentencias = new String[valores.size()];
		StringBuffer sentencia = null;
		//PreparedStatement sentenciaPreparada = null;
		//PreparedStatement selectPreparado = null;
		Object valor = null;
		
		// Realizamos la operación
		try {
//			sentenciaPreparada = conexionUtilizada.prepareStatement(sentenciaSql);
//			String sentenciaSelect = getSentenciaSelect(sentenciaSql);
//			selectPreparado = conexionUtilizadaLocal.prepareStatement(sentenciaSelect);
			
			for (int i=0; i<valores.size(); i++){
				sentencia = new StringBuffer(sentenciaSql);
				if (columnsSentenciaSql.getColumnCount() > 1)
					for (int j=0; j<columnsSentenciaSql.getColumnCount(); j++){
						//sentenciaPreparada.setObject(j+1, ((ArrayList)valores.get(i)).get(j));
						valor = ((ArrayList<Object>)valores.get(i)).get(j);
						for(int k=sentencia.indexOf("?"); k<=sentencia.length()-1; k++){
							if (sentencia.charAt(k)=='?'){
								sentencia.deleteCharAt(k);
								if(valor == null) sentencia.insert(k, "null");
								else{
									switch(columnsSentenciaSql.getColumnType(j+1)){
										case 3: sentencia.insert(k, valor);
												break;
										case 8: sentencia.insert(k, valor);
												break;
										case 6: sentencia.insert(k, valor);
												break;
										case 4: sentencia.insert(k, valor);
												break;
										case 2: sentencia.insert(k, valor);
												break;
										case 5: sentencia.insert(k, valor);
												break;
										default: sentencia.insert(k, "'"+valor.toString()+"'");
									}
								}
								break; 
							}
						}	
					}	 
				else{
//					sentenciaPreparada.setObject(1, valores.get(i));
					int pos = sentencia.indexOf("?");
					sentencia.deleteCharAt(pos);
					valor = valores.get(i);
					if(valor == null) sentencia.insert(pos, "null");
					else sentencia.insert(pos, valor);
				}
				//sentenciaPreparada.addBatch();
				//sentencias[i] = sentenciaPreparada.toString().substring(sentenciaPreparada.toString().indexOf(":")+1);
				sentencias[i] = sentencia.toString();
			}
		}catch (SQLException exSQL) {
			logger.error(
					"crearLoteSentencias(String, ArrayList, boolean, boolean)",
					exSQL);

			ConexionesLR.getConexion(local, sync);
			throw (new BaseDeDatosExcepcion("Error al crear sentencia preparada: " + sentenciaSql, exSQL));
		} finally {
//			if (sentenciaPreparada != null) {
//				try {
//					sentenciaPreparada.close();
//				} catch (SQLException e) {
//					logger
//							.error(
//									"crearLoteSentencias(String, ArrayList, boolean, boolean)",
//									e);
//				}
//				sentenciaPreparada = null;
//			}
//			if (selectPreparado != null) {
//				try {
//					selectPreparado.close();
//				} catch (SQLException e) {
//					logger
//							.error(
//									"crearLoteSentencias(String, ArrayList, boolean, boolean)",
//									e);
//				}
//				selectPreparado = null;
//			}
		}

		if (logger.isDebugEnabled()) {
			logger
					.debug("crearLoteSentencias(String, ArrayList, boolean, boolean) - end");
		}
		return sentencias;
	}
	
	/**
	 * Método getSentenciaSelect
	 * 		Retorna el equivalente a una consulta de una sentencia insert indicada.
	 * @param sentenciaSql
	 * @return String
	 */
	static String getSentenciaSelect(String sentenciaSql){
		if (logger.isDebugEnabled()) {
			logger.debug("getSentenciaSelect(String) - start");
		}

		StringBuffer cadena = new StringBuffer(sentenciaSql);
		int posicion = cadena.indexOf("values");
		String parametros = new String("");
		if (posicion == -1){
			posicion = cadena.indexOf("set");
			parametros = new String(cadena.substring(posicion+3, cadena.length()));
			cadena = new StringBuffer(cadena.substring(0, posicion));
			parametros = new String(parametros.replaceAll("=",""));
			parametros = new String(parametros.replace('?','-'));
			parametros = new String(parametros.replaceAll("-",""));
		}else{
			cadena = new StringBuffer(cadena.substring(0, posicion));
			posicion = cadena.indexOf("(");
			parametros = new String(cadena.substring(posicion+1, cadena.lastIndexOf(")")));
			cadena = new StringBuffer(cadena.substring(0, posicion));
		}
		cadena = new StringBuffer(cadena.toString().replaceAll("update", "select "+parametros+" from"));
		cadena = new StringBuffer(cadena.toString().replaceAll("insert into", "select "+parametros+" from"));
		cadena = new StringBuffer(cadena.toString().replaceAll("replace into", "select "+parametros+" from"));
		String returnString = cadena.toString();
		if (logger.isDebugEnabled()) {
			logger.debug("getSentenciaSelect(String) - end");
		}
		return returnString;	
	}

	/**
	 * Método numParams
	 * 		Retorna el número de parámetros "?" definidos en la sentencia preparada indicada.
	 * @param sentenciaSql
	 * @return int
	 */
	static int numParams(String sentenciaSql){
		if (logger.isDebugEnabled()) {
			logger.debug("numParams(String) - start");
		}

		char[] cadena = sentenciaSql.toCharArray();
		int params = 0;
		for(int i=0; i<cadena.length; i++)
			if (cadena[i]=='?') params++;

		if (logger.isDebugEnabled()) {
			logger.debug("numParams(String) - end");
		}
		return params;	
	}	
	/**
	 * Método getConexion
	 * 		Retorna la conexión a utilizar según indicación del parámetro local.
	 * @param local - Indicador del lugar a realizar la sentencia (true BdLocal, false BdRemota).
	 * @return Connection - Objeto de tipo conexión retornado.
	 * @throws ConexionExcepcion
	 */
	public static synchronized Connection getConexion(boolean local) throws ConexionExcepcion{
		if (logger.isDebugEnabled()) {
			logger.debug("getConexion(boolean) - start");
		}

		Connection returnConnection = getConexion(local, false);
		if (logger.isDebugEnabled()) {
			logger.debug("getConexion(boolean) - end");
		}
		return returnConnection;
	}

	public static Connection getConexion(boolean local, boolean sync) throws ConexionExcepcion{
		if (logger.isDebugEnabled()) {
			logger.debug("getConexion(boolean, boolean) - start");
		}

		boolean conexionActiva = false;
		Connection conexion;

		if (sync) {
			if (local) conexionActiva = conectadoSyncLocal;
			else conexionActiva = conectadoSyncServidor;
		} else {
			if (local) conexionActiva = conectadoLocal;
			else conexionActiva = conectadoServidor;
		}
		
		// Si la conexion no esta activa la creamos
		if (!conexionActiva)
			conectar(local,sync);
		if (sync) {
			if (local) conexion = conexionSyncLocal;
			else conexion = conexionSyncServidor;
		} else {
			if (local) conexion = conexionLocal;
			else conexion = conexionServidor;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getConexion(boolean, boolean) - end");
		}
		return conexion;	
	}

	/**
	 * Método ejecutarLoteSentencias
	 * 		Ejecuta un lote de sentencias SQL.
	 * @param loteSentencias - Sentencia del objeto conexión con el lote de instrucciones SQL.
	 * @param local - Indicador del lugar a realizar la sentencia (true BdLocal, false BdRemota).
	 * @param atomicas - Indicador para ejecución de sentencias atómicas (transaccionales).
	 * @return int[] - Lista de números de filas afectadas por cada sentencia.
	 * @throws ConexionExcepcion
	 * @throws BaseDeDatosExcepcion
	 */
	public static int[] ejecutarLoteSentencias(Statement loteSentencias, boolean local, boolean atomicas) throws ConexionExcepcion, BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("ejecutarLoteSentencias(Statement, boolean, boolean) - start");
		}

		int[] returnintArray = ejecutarLoteSentencias(loteSentencias, local, atomicas, false);
		if (logger.isDebugEnabled()) {
			logger.debug("ejecutarLoteSentencias(Statement, boolean, boolean) - end");
		}
		return returnintArray;
	}

	static int[] ejecutarLoteSentencias(Statement loteSentencias, boolean local, boolean atomicas, boolean sync) throws ConexionExcepcion, BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("ejecutarLoteSentencias(Statement, boolean, boolean, boolean) - start");
		}

		Connection conexionUtilizada = getConexion(local, sync);
		int[] filasAfectadas = null;

		try {
			if (atomicas){
				conexionUtilizada.setAutoCommit(false);
				conexionUtilizada.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			}
			
			filasAfectadas = loteSentencias.executeBatch();
			if (atomicas){
				conexionUtilizada.commit();
				conexionUtilizada.setAutoCommit(true);
			}
		}catch (SQLException exSQL) {
			logger
					.error(
							"ejecutarLoteSentencias(Statement, boolean, boolean, boolean)",
							exSQL);

			try {
				if (atomicas){
					conexionUtilizada.rollback();
					conexionUtilizada.setAutoCommit(true);
				}
			} catch (SQLException e) {
				logger
						.error(
								"ejecutarLoteSentencias(Statement, boolean, boolean, boolean)",
								e);

				e.printStackTrace(System.out);
			}
			conectar(local, sync);
			throw (new BaseDeDatosExcepcion("Error al realizar lote de sentencias " + loteSentencias, exSQL));
		}

		if (logger.isDebugEnabled()) {
			logger
					.debug("ejecutarLoteSentencias(Statement, boolean, boolean, boolean) - end");
		}
		return filasAfectadas;
	}

	
	public static Statement crearSentencia (boolean local) throws ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("crearSentencia(boolean) - start");
		}

		Statement returnStatement = crearSentencia(local, false);
		if (logger.isDebugEnabled()) {
			logger.debug("crearSentencia(boolean) - end");
		}
		return returnStatement;
	}
	
	public static Statement crearSentencia (boolean local, boolean sync) throws ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("crearSentencia(boolean, boolean) - start");
		}

		Statement s = null;
		try {
			s = getConexion(local, sync).createStatement();
		} catch (SQLException e) {
			logger.error("crearSentencia(boolean, boolean)", e);

			if (local) 
				throw new ConexionLocalExcepcion("Error de Conexion con BD Local", e);
			else
				throw new ConexionServidorExcepcion("Error de Conexion con el servidor", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("crearSentencia(boolean, boolean) - end");
		}
		return s;
	}
	
	public static PreparedStatement prepararSentencia (String sql, boolean local, 
			boolean sync) throws SQLException, ConexionExcepcion{
		if (logger.isDebugEnabled()) {
			logger.debug("prepararSentencia(String, boolean, boolean) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("prepararSentencia(sql = " + sql + ", local = "
					+ local + ", sync = " + sync + ") - Sentencia preparada");
		}

		PreparedStatement returnPreparedStatement = getConexion(local, sync)
				.prepareStatement(sql);
		if (logger.isDebugEnabled()) {
			logger.debug("prepararSentencia(String, boolean, boolean) - end");
		}
		return returnPreparedStatement;
	}


	public static void setConectadoServidor(boolean conectadoServidor) {
		if (logger.isDebugEnabled()) {
			logger.debug("setConectadoServidor(boolean) - start");
		}

		ConexionesLR.conectadoServidor = conectadoServidor;

		if (logger.isDebugEnabled()) {
			logger.debug("setConectadoServidor(boolean) - end");
		}
	}
	
	public static synchronized void cerrarConexion(boolean local, boolean sync) {
		if (logger.isDebugEnabled()) {
			logger.debug("cerrarConexion(boolean, boolean) - start");
		}

		try {
			if (sync) {
				if (local) {
					if (conexionSyncLocal!= null)
						conexionSyncLocal.close();
				}
				else {
					if (conexionSyncServidor != null)
					conexionSyncServidor.close();
				}
			} else {
				if (local) {
					if (conexionLocal != null)
					conexionLocal.close();
				}
				else {
					if (conexionServidor != null)
					conexionServidor.close();
				}
			}
		} catch (SQLException e) {
			logger.error("cerrarConexion(boolean, boolean)", e);
		} finally {
			if (sync) {
				if (local) {
					conexionSyncLocal = null;
					conectadoSyncLocal = false;
				}
				else {
					conexionSyncServidor = null;
					conectadoSyncServidor = false;
				}
			} else {
				if (local) {
					conexionLocal = null;
					conectadoLocal = false;
				}
				else {
					conexionServidor = null;
					conectadoServidor = false;
				}
			}
			
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("cerrarConexion(boolean, boolean) - end");
		}
	}
	
	public static synchronized void cerrarConexionesSync(){
		if (logger.isDebugEnabled()) {
			logger.debug("cerrarConexionesSync() - start");
		}

		try {
			cerrarConexion(true, true);
			cerrarConexion(false, true);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cerrarConexionesSync() - end");
		}
	}
	
	public static String getIPCaja() {
		String ip = "";		
		try {
			InetAddress a = InetAddress.getLocalHost();
			ip = a.toString();
			int i = ip.indexOf("/") + 1;
			if (i > 0) {
				ip = ip.substring(i);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ip;
	}
	
	public static ResultSet realizarConsulta(String sentenciaSql, String dbUrlTienda) throws ConexionExcepcion, BaseDeDatosExcepcion {
		ResultSet resultado = null;
		Connection conexionUtilizada = null;
		boolean conexionActiva;

		conexionActiva = conectadoServidor;
		
		// Si la conexion no esta activa la creamos
		if (!conexionActiva)
			conectar(dbUrlTienda);
		
		conexionUtilizada = conexionServidor;

		// Realizamos la operacion
		try {
			Statement sentencia = null;
			try {
				/*if (actualizable)
					sentencia = conexionUtilizada.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				else*/ sentencia = conexionUtilizada.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			} catch (SQLException e) {
				throw new ConexionServidorExcepcion("Error al conectar al servidor", e);
			}
			resultado = null;

			resultado = new CRResultSet(sentencia.executeQuery(sentenciaSql));
			resultado.first();
		} catch (SQLException exSQL){
			if (resultado != null) {
				try {
					resultado.close();
				} catch (SQLException e) {

				}
			}
			conectar(dbUrlTienda);
			throw (new BaseDeDatosExcepcion("Error al realizar sentencia", exSQL));
		}

		return resultado;
	}
	
	public static synchronized void cerrarConexion() {
		if (logger.isDebugEnabled()) {
			logger.debug("cerrarConexion(boolean, boolean) - start");
		}

		try {
			if (conexionServidor != null)
				conexionServidor.close();
		} catch (SQLException e) {
			logger.error("cerrarConexion(boolean, boolean)", e);
		} finally {
			conexionServidor = null;
			conectadoServidor = false;
		}
	}
	
}
