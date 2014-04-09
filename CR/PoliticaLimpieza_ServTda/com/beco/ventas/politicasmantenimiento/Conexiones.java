package com.beco.ventas.politicasmantenimiento;
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/** 
 * 		Maneja las operaciones de conexiones a las base de datos, tanto . Funciona 
 * como mediador entre la aplicacion y la Base de Datos. Ejecuta sentencias 
 * simples y sentencias preparadas para actualización de tablas en la base 
 * de datos. También proporciona métodos para inicialización de la conexión 
 * conexion la base de datos y para el control de cada una de sus propiedades.
 */
public class Conexiones {
	
	private static Connection conexionLocal;
		
	/**
	 * Constructor para Conexiones.
	 * 		Establece los parámetros (clase, url, usuario, clave) para 
	 * conexión a la base de datos tanto local como del servidor central de
	 * la tienda.
	 */
	public Conexiones() throws ClassNotFoundException, SQLException {
		conectar(Sesion.getDbUrlLocal(), Sesion.getDbUsuario(), Sesion.getDbClave());
	}
	
	/**
	 * Método conectar.
	 * 		Realiza la conexion a la base de Datos.
	 * @param local - Variable boolean que indica a que base de datos se desea conectar. Es verdadero si
	 * 				se conecta a la BD local. False si es a la base de datos centraliada de la tienda.
	 * @throws BaseDeDatosExcepcion - Si ocurre un error conexion el Driver o la conexion al URL definido
	 */
	public static void conectar(String url, String usuario, String clave) throws ClassNotFoundException, SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			throw (ex);
		}
		try {
			conexionLocal = DriverManager.getConnection(url, usuario, clave);
		} catch (SQLException ex) {
			throw (ex);
		}
	}
	
	public static void desconectar() throws ClassNotFoundException, SQLException {
		try {
			conexionLocal.close();
		} catch (SQLException ex) {
			throw (ex);
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
	public static ResultSet realizarConsulta(String sentenciaSql) throws SQLException {
		return realizarConsulta(sentenciaSql, false);
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
	public static ResultSet realizarConsulta(String sentenciaSql, boolean actualizable) throws SQLException {
		ResultSet resultado = null;

		// Realizamos la operacion
		try {
			Statement sentencia;
			if (actualizable)
				sentencia = conexionLocal.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			else sentencia = conexionLocal.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultado = sentencia.executeQuery(sentenciaSql);
			resultado.first();
		} catch (SQLException exSQL) {
			//exSQL.printStackTrace();
			throw (exSQL);
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
	public static int realizarSentencia(String sentenciaSql) throws SQLException {
	
		// Realizamos la operacion
		Statement sentencia = null;
		try {
			sentencia = conexionLocal.createStatement();
		} catch (SQLException exSQL) {
			//exSQL.printStackTrace();
			//throw (new ConexionExcepcion("Error al realizar sentencia " + sentenciaSql, exSQL));
		}
		try {
			return sentencia.executeUpdate(sentenciaSql);
		} catch (SQLException exSQL) {
			//exSQL.printStackTrace();
			throw (exSQL);
		}
	}
	
	static int[] ejecutarLoteSentencias(Statement loteSentencias, boolean local, boolean atomicas) throws SQLException {
		int[] filasAfectadas = null;

		try {
			if (atomicas){
				conexionLocal.setAutoCommit(false);
				conexionLocal.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			}
			
			filasAfectadas = loteSentencias.executeBatch();
			if (atomicas){
				conexionLocal.commit();
				conexionLocal.setAutoCommit(true);
			}
		}catch (SQLException exSQL) {
			try {
				if (atomicas){
					conexionLocal.rollback();
					conexionLocal.setAutoCommit(true);
				}
			} catch (SQLException e) {
				e.printStackTrace(System.out);
			}
			try {
				conectar(Sesion.getDbUrlLocal(), Sesion.getDbUsuario(), Sesion.getDbClave());
			} catch (ClassNotFoundException e) {
				System.out.println("** Error de conexión con la BD, clase no encontrada");
				e.printStackTrace();
			} catch (SQLException e) {
				throw (e);
			}
			throw (exSQL);
		}
		return filasAfectadas;
	}


	public static Connection getConexionLocal() {
		return conexionLocal;
	}
}
	