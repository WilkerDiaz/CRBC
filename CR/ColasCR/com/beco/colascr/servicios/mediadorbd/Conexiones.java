/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.colascr.transferencias.comunicacionbd
 * Programa   : Conexiones.java
 * Creado por : Ileana Rojas, Gabriel Martinelli
 * Creado en  : 21/02/2005 02:49:59 PM
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
package com.beco.colascr.servicios.mediadorbd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.beco.colascr.servicios.Sesion;
import com.beco.colascr.servicios.excepciones.BaseDeDatosExcepcion;
import com.beco.colascr.servicios.excepciones.ConexionExcepcion;
import com.beco.colascr.servicios.excepciones.ConexionLocalExcepcion;
import com.beco.colascr.servicios.excepciones.ConexionServidorExcepcion;

/** 
 * 		Maneja las operaciones de conexiones a las base de datos, tanto . Funciona 
 * como mediador entre la aplicacion y la Base de Datos. Ejecuta sentencias 
 * simples y sentencias preparadas para actualización de tablas en la base 
 * de datos. También proporciona métodos para inicialización de la conexión 
 * conexion la base de datos y para el control de cada una de sus propiedades.
 */
public class Conexiones {

	private static boolean conectadoLocal = false;
	private static boolean conectadoServidor = false;
	private static Connection conexionLocal;
	private static Connection conexionServidor;
		
	/**
	 * Constructor para Conexiones.
	 * 		Establece los parámetros (clase, url, usuario, clave) para 
	 * conexión a la base de datos tanto local como del servidor central de
	 * la tienda.
	 */
	public Conexiones(boolean local) throws ConexionExcepcion {
		if ((local)&&(!conectadoLocal))
			conectar(true);
		if ((!local)&&(!conectadoServidor))
			conectar(false);
	}
	/**
	 * Constructor para conexiones.
	 * 		Establece la clase Conexiones para su uso con el visor de auditoria.
	 * Se conecta con la caja a auditar segun el host indicado
	 * @param host Nombre del equipo (caja) a ser auditada
	 * @throws ConexionExcepcion
	 */
/*	public Conexiones(String host, Properties p) throws ConexionExcepcion {
		conectar(false, true, host, p);
	}
	
	/**
	 * Método conectar.
	 * 		Realiza la conexion a la base de Datos.
	 * @param local - Variable boolean que indica a que base de datos se desea conectar. Es verdadero si
	 * 				se conecta a la BD local. False si es a la base de datos centraliada de la tienda.
	 * @throws BaseDeDatosExcepcion - Si ocurre un error conexion el Driver o la conexion al URL definido
	 */
/*	private static void conectar(boolean local) throws ConexionExcepcion {
		conectar(local, false, null, null);
	}
	
	private static void conectar(boolean local, boolean sync) throws ConexionExcepcion {
		conectar(local, false, null, null, sync);
	}

	/**
	 * Método conectar.
	 * 		Realiza la conexion a la base de Datos.
	 * @param local - Variable boolean que indica a que base de datos se desea conectar. Es verdadero si
	 * 				se conecta a la BD local. False si es a la base de datos centraliada de la tienda.
	 * @param audit - Indica si debe realizar una conexion de auditoria
	 * @param host - Para las conexiones de auditoria, el host al cual se va a conectar
	 * @param props - Para las conexiones de auditoria, la configuracion de la conexion
	 * @throws ConexionExcepcion
	 */
/*	private static void conectar(boolean local, boolean audit, String host, Properties props) throws ConexionExcepcion {
		conectar(local, audit, host, props, false);
	}*/
	
	private static void conectar(boolean local) throws ConexionExcepcion {
		if (local) { // Se establece la conexion con la BD local
			try {
				Class.forName(Sesion.getDbClaseLocal());
			} catch (ClassNotFoundException ex) {
				conectadoLocal = false;
				throw (new ConexionExcepcion("Falla en la conexión con la Base de Datos Local. No se encuentra el Driver", ex));
			}
			try {
				conexionLocal = DriverManager.getConnection(Sesion.getDbUrlLocal(), Sesion.getDbUsuario(), Sesion.getDbClave());
				conectadoLocal = true;
			} catch (SQLException ex) {
				ex.printStackTrace();
				conectadoLocal = false; 
				throw (new ConexionExcepcion("Falla en la conexion a la BD Local. Servidor inactivo, Url, Usuario o contraseña no válida. No se pudo conectar", ex));
			}
		} else { // Se establece la conexion con la BD remota (Servidor central)
			try {
				Class.forName(Sesion.getDbClaseServidor());
			} catch (ClassNotFoundException ex) {
				conectadoServidor = false;
				throw (new ConexionServidorExcepcion("Falla en la conexión con la Base de Datos Remota. No se encuentra el Driver", ex));
			}
			try {
				conexionServidor = DriverManager.getConnection(Sesion.getDbUrlServidor(), Sesion.getDbUsuario(), Sesion.getDbClave());
				conectadoServidor = true;
			} catch (SQLException ex) {
				conectadoServidor = false; 
				throw (new ConexionServidorExcepcion("Falla en la conexion a la BD Remota. Servidor inactivo, Url, Usuario o contraseña no válida. No se pudo conectar", ex));
			}
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
	/*public static ResultSet realizarConsulta(String sentenciaSql, boolean local) {
		return realizarConsulta(sentenciaSql, local, false);
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
/*	static ResultSet realizarConsulta(String sentenciaSql, boolean local, boolean actualizable) throws ConexionExcepcion, BaseDeDatosExcepcion {
		return realizarConsulta(sentenciaSql, local, actualizable, false);
	}
*/
	static ResultSet realizarConsulta(String sentenciaSql, boolean local, boolean actualizable) throws ConexionExcepcion, BaseDeDatosExcepcion {
		ResultSet resultado = null;
		Connection conexionUtilizada = null;
		boolean conexionActiva;

		if (local) conexionActiva = conectadoLocal;
		else conexionActiva = conectadoServidor;
		
		// Si la conexion no esta activa la creamos
		if (!conexionActiva)
			conectar(local);

		if (local) conexionUtilizada = conexionLocal;
		else conexionUtilizada = conexionServidor;

		// Realizamos la operacion
		try {
			Statement sentencia = null;
			try {
				if (actualizable)
					sentencia = conexionUtilizada.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				else sentencia = conexionUtilizada.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			} catch (SQLException e) {
				if (local)
					throw new ConexionExcepcion("Error al conectar a BD", e);
				else
					throw new ConexionServidorExcepcion("Error al conectar al servidor", e);
			}
			resultado = null;
			resultado = new CRResultSet(sentencia.executeQuery(sentenciaSql));
			resultado.first();
		} catch (SQLException exSQL){
			if (resultado != null)
				try {
					resultado.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			exSQL.printStackTrace();
			conectar(local);
			throw (new BaseDeDatosExcepcion("Error al realizar sentencia", exSQL));
		}
		return resultado;
	}
	
	public static ResultSet realizarConsulta(String sentenciaSql, boolean local) throws ConexionExcepcion, BaseDeDatosExcepcion {
		return realizarConsulta(sentenciaSql,local,false);
	}

	public static int realizarSentencia(String sentenciaSql, boolean local) throws ConexionExcepcion, BaseDeDatosExcepcion {
		Connection conexionUtilizada;
		conexionUtilizada = getConexion(local);
		// Realizamos la operacion
		PreparedStatement sentencia = null;
		try {
			sentencia = conexionUtilizada.prepareStatement(sentenciaSql);
		} catch (SQLException exSQL) {
			exSQL.printStackTrace();
			if (local) 
				throw new ConexionExcepcion("Error de Conexion", exSQL);
			else
				throw new ConexionServidorExcepcion("Error de Conexion", exSQL);
		}
		try {
			return sentencia.executeUpdate();
		} catch (SQLException exSQL) {
			//exSQL.printStackTrace();
			throw (new BaseDeDatosExcepcion("Error al ejecutar sentencia", exSQL));
		} finally {
			try {
				sentencia.close();
			} catch (SQLException e) {
				//e.printStackTrace();
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
/*	static int[] realizarLoteSentencias(String sentenciaSql, boolean local, ArrayList valores, boolean atomicas) throws ConexionExcepcion, BaseDeDatosExcepcion {
		return realizarLoteSentencias(sentenciaSql, local, valores, atomicas, false);
	}

	
	static int[] realizarLoteSentencias(String sentenciaSql, boolean local, ArrayList valores, boolean atomicas, boolean sync) throws ConexionExcepcion, BaseDeDatosExcepcion {
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
			int numParams = numParams(sentenciaSql);
			for (int i=0; i<valores.size(); i++){
				if (numParams > 1)
					for (int j=0; j<numParams; j++)
						sentenciaPreparada.setObject(j+1, ((ArrayList)valores.get(i)).get(j)); 
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
			try {
				if (atomicas){
					conexionUtilizada.rollback();
					conexionUtilizada.setAutoCommit(true);
				}
			} catch (SQLException e) {
				e.printStackTrace(System.out);
			}
			conectar(local, sync);
		} finally {
			if (sentenciaPreparada != null) {
				try {
					sentenciaPreparada.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				sentenciaPreparada = null;
			}
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
/*	static String[] crearLoteSentencias(String sentenciaSql, ArrayList valores, boolean local) throws ConexionExcepcion, BaseDeDatosExcepcion {
		return crearLoteSentencias(sentenciaSql, valores, local, false);
	}

	*/
/*	static String[] crearLoteSentencias(String sentenciaSql, ArrayList valores, boolean local) throws ConexionExcepcion, BaseDeDatosExcepcion {
		String[] sentencias = new String[valores.size()];
		StringBuffer sentencia = null;
		Connection conexionUtilizada = getConexion(local);
		PreparedStatement sentenciaPreparada = null;
		PreparedStatement selectPreparado = null;
		Object valor = null;
		
		// Realizamos la operación
		try {
			sentenciaPreparada = conexionUtilizada.prepareStatement(sentenciaSql);
			String sentenciaSelect = getSentenciaSelect(sentenciaSql);
			selectPreparado = conexionUtilizada.prepareStatement(sentenciaSelect);
			
			for (int i=0; i<valores.size(); i++){
				sentencia = new StringBuffer(sentenciaSql);
				if (selectPreparado.getMetaData().getColumnCount() > 1)
					for (int j=0; j<selectPreparado.getMetaData().getColumnCount(); j++){
						//sentenciaPreparada.setObject(j+1, ((ArrayList)valores.get(i)).get(j));
						valor = ((ArrayList)valores.get(i)).get(j);
						for(int k=sentencia.indexOf("?"); k<=sentencia.length()-1; k++){
							if (sentencia.charAt(k)=='?'){
								sentencia.deleteCharAt(k);
								if(valor == null) sentencia.insert(k, "null");
								else{
									switch(selectPreparado.getMetaData().getColumnType(j+1)){
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
					sentenciaPreparada.setObject(1, valores.get(i));
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
			Conexiones.getConexion(local);
			throw (new BaseDeDatosExcepcion("Error al crear sentencia preparada: " + sentenciaSql, exSQL));
		} finally {
			if (sentenciaPreparada != null) {
				try {
					sentenciaPreparada.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				sentenciaPreparada = null;
			}
			if (selectPreparado != null) {
				try {
					selectPreparado.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				selectPreparado = null;
			}
		}
		return sentencias;
	}
	
	/**
	 * Método getSentenciaSelect
	 * 		Retorna el equivalente a una consulta de una sentencia insert indicada.
	 * @param sentenciaSql
	 * @return String
	 */
/*	static String getSentenciaSelect(String sentenciaSql){
		StringBuffer cadena = new StringBuffer(sentenciaSql);
		int posicion = cadena.indexOf("values");
		int posicionWhere = cadena.indexOf("where");
		String parametros = new String("");
		if (posicion == -1){
			posicion = cadena.indexOf("set");
			parametros = new String(cadena.substring(posicion+3, (posicionWhere==-1) ? cadena.length() : posicionWhere));
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
		return cadena.toString();	
	}

	/**
	 * Método numParams
	 * 		Retorna el número de parámetros "?" definidos en la sentencia preparada indicada.
	 * @param sentenciaSql
	 * @return int
	 */
/*	static int numParams(String sentenciaSql){
		char[] cadena = sentenciaSql.toCharArray();
		int params = 0;
		for(int i=0; i<cadena.length; i++)
			if (cadena[i]=='?') params++;
		return params;	
	}	
	/**
	 * Método getConexion
	 * 		Retorna la conexión a utilizar según indicación del parámetro local.
	 * @param local - Indicador del lugar a realizar la sentencia (true BdLocal, false BdRemota).
	 * @return Connection - Objeto de tipo conexión retornado.
	 * @throws ConexionExcepcion
	 */
/*	public static synchronized Connection getConexion(boolean local) throws ConexionExcepcion{
		return getConexion(local, false);
	}
*/
	static Connection getConexion(boolean local) throws ConexionExcepcion{
		boolean conexionActiva = false;
		Connection conexion;

		if (local) conexionActiva = conectadoLocal;
		else conexionActiva = conectadoServidor;
		
		// Si la conexion no esta activa la creamos
		if (!conexionActiva)
			conectar(local);
		if (local) conexion = conexionLocal;
		else conexion = conexionServidor;

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
/*	public static int[] ejecutarLoteSentencias(Statement loteSentencias, boolean local, boolean atomicas) throws ConexionExcepcion, BaseDeDatosExcepcion {
		return ejecutarLoteSentencias(loteSentencias, local, atomicas, false);
	}
*/
/*	static int[] ejecutarLoteSentencias(Statement loteSentencias, boolean local, boolean atomicas) throws ConexionExcepcion, BaseDeDatosExcepcion {
		Connection conexionUtilizada = getConexion(local);
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
			try {
				if (atomicas){
					conexionUtilizada.rollback();
					conexionUtilizada.setAutoCommit(true);
				}
			} catch (SQLException e) {
			}
			conectar(local);
			exSQL.printStackTrace();
			throw (new BaseDeDatosExcepcion("Error al realizar lote de sentencias " + loteSentencias, exSQL));
		}
		return filasAfectadas;
	}
	
/*	
	public static Statement crearSentencia (boolean local) throws ConexionExcepcion {
		return crearSentencia(local, false);
	}
	*/
/*	public static Statement crearSentencia (boolean local) throws ConexionExcepcion {
		Statement s = null;
		try {
			s = getConexion(local).createStatement();
		} catch (SQLException e) {
			if (local) 
				throw new ConexionExcepcion("Error de Conexion con BD Local", e);
			else
				throw new ConexionServidorExcepcion("Error de Conexion con el servidor", e);
		}
		return s;
	}

/*
	public static void setConectadoServidor(boolean conectadoServidor) {
		Conexiones.conectadoServidor = conectadoServidor;
	}
	*/
	public static synchronized void cerrarConexion(boolean local) {
		try {
			if (local)
				conexionLocal.close();
			else
				conexionServidor.close();
		} catch (Exception e) {
			//e.printStackTrace();
		} finally {
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
	
	public static int[] ejecutarLoteSentencias(Statement loteSentencias, boolean atomicas) throws ConexionExcepcion, BaseDeDatosExcepcion {
		Connection conexionUtilizada = getConexion(true);
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
			try {
				if (atomicas){
					conexionUtilizada.rollback();
					conexionUtilizada.setAutoCommit(true);
				}
			} catch (SQLException e) {
				e.printStackTrace(System.out);
			}
			conectar(true);
			throw (new BaseDeDatosExcepcion("Error al realizar lote de sentencias " + loteSentencias, exSQL));
		}
	
		return filasAfectadas;
	}
	
	public static Statement crearSentencia (boolean local) throws ConexionExcepcion {
		Statement returnStatement = crearSentencia(local, false);

		return returnStatement;
	}
		
	public static Statement crearSentencia (boolean local, boolean sync) throws ConexionExcepcion {
		Statement s = null;
		try {
			s = getConexion(local).createStatement();
		} catch (SQLException e) {
			if (local) 
				throw new ConexionLocalExcepcion("Error de Conexion con BD Local", e);
			else
				throw new ConexionServidorExcepcion("Error de Conexion con el servidor", e);
		}

		return s;
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
		int[] returnintArray = ejecutarLoteSentencias(loteSentencias, local, atomicas, false);

		return returnintArray;
	}
	
	static int[] ejecutarLoteSentencias(Statement loteSentencias, boolean local, boolean atomicas, boolean sync) throws ConexionExcepcion, BaseDeDatosExcepcion {
		Connection conexionUtilizada = getConexion(local);
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
			try {
				if (atomicas){
					conexionUtilizada.rollback();
					conexionUtilizada.setAutoCommit(true);
				}
			} catch (SQLException e) {
				e.printStackTrace(System.out);
			}
			conectar(local);
			throw (new BaseDeDatosExcepcion("Error al realizar lote de sentencias " + loteSentencias, exSQL));
		}

		return filasAfectadas;
	}
	
	public static synchronized void cerrarConexiones(){
		cerrarConexion(true);
		cerrarConexion(false);
	}
}
