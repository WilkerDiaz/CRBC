/**
 * =============================================================================
 * Proyecto   : AplicacionesPDAServidorDeTienda
 * Paquete    : com.beco.sistemas.aplicacionespda.servidordetienda.basededatos
 * Programa   : ConectarConBd.java
 * Creado por : Marcos Grillo
 * Creado en  : 05/06/2009 - 08:56:30
 *
 * (c) CENTROBECO, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versi�n     : 
 * Fecha       : 
 * Analista    : 
 * Descripci�n : 
 * =============================================================================
 */

package com.beco.colascr.notificaciones.basededatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 * Clase que sirve como interfaz entre la base de datos y la aplicaci�n. Se 
 * conecta con una base de datos, hace consultas, sentencias y batchs. 
 * 
 * @author Marcos Grillo
 */
public class ConectarConBd {

	protected Connection conexionLocal;
	protected String dbUrl = null;
	protected String dbUsuario = null;
	protected String dbClave = null;

	/**
	 * 	Establece los par�metros y se conecta a la base de datos tanto local
	 * como de otro servidor.
	 * @param url direcci�n del servidor con la base de datos. 
	 * @param usuario identificai�n necesaria para el acceso a la base de
	 * datos.
	 * @param clave contrase�a del usuario para conectarse a la base de datos
	 * @throws ClassNotFoundException ocurre cuando no se encuentra el driver
	 * jdbc que es necesario para la conexi�n y las consultas.
	 * @throws SQLException error que se lanza cuando no se puede conectar
	 * con la base de datos.
	 */
	public ConectarConBd(String url, String usuario, String clave) throws ClassNotFoundException, SQLException {
		conectar(url, usuario, clave);
	}
	
	/**
	 * M�todo conectar.
	 * 		Realiza la conexion a la base de Datos.
	 * @param url direcci�n del servidor con la base de datos. 
	 * @param usuario identificai�n necesaria para el acceso a la base de
	 * datos.
	 * @param clave contrase�a del usuario para conectarse a la base de datos
	 * @throws ClassNotFoundException ocurre cuando no se encuentra el driver
	 * jdbc que es necesario para la conexi�n y las consultas.
	 * @throws SQLException error que se lanza cuando no se puede conectar
	 * con la base de datos.
	 */
	public void conectar(String url, String usuario, String clave) throws ClassNotFoundException, SQLException {
		//Cambios realizados en la migraci�n a Mysql 5.5 por jperez
		String prop ="?zeroDateTimeBehavior=convertToNull&jdbcCompliantTruncation=false";
		this.dbUrl = url;
		this.dbUsuario = usuario;
		this.dbClave = clave;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			throw (ex);
		}
		try {
			conexionLocal = DriverManager.getConnection(dbUrl+prop, dbUsuario, dbClave);
		} catch (SQLException ex) {
			throw (ex);
		}
	}
	
	/**
	 * Termina la conexi�n con la base de datos.
	 */
	public void desconectar(){
		try {
			conexionLocal.close();
		} catch (Exception ex) {
		}
	}

	/**
	 * Ejecuta una sentencia SQL de consulta de registros. Se tiene como precondici�n la ejecuci�n de
	 *  la funci�n conectar.
	 * @param sentenciaSql Sentencia SQL.
	 * @return ResultSet Objeto con las filas resultantes de la consulta.
	 * @throws Exception Indicando una falla al momento de la ejecuci�n de la
	 * consulta.
	 */
	public ResultSet realizarConsulta(String sentenciaSql) throws Exception {
		return realizarConsulta(sentenciaSql, false);
	}	

	/**
	 * Ejecuta una sentencia SQL que devuelva alg�n resultado, este tiene la
	 * capacidad de desplazamiento y permite establecer la actualizaci�n o no
	 * de los datos resultantes. Se tiene como precondici�n la ejecuci�n de
	 *  la funci�n conectar.
	 * 
	 * @param sentenciaSql Sentencia SQL.
	 * @param actualizable determina si el resultSet a devolver debe ser
	 * actualizable o no. 
	 * @return ResultSet Objeto con las filas resultantes de la consulta.
	 * @throws Exception Indicando una falla al momento de la ejecuci�n de la
	 * consulta.
	 */
	public ResultSet realizarConsulta(String sentenciaSql, 
									boolean actualizable) throws Exception {
		ResultSet resultado = null;

		// Realizamos la operacion
		try {
			Statement sentencia;
			if (actualizable)
				sentencia = conexionLocal.
							createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
											ResultSet.CONCUR_UPDATABLE);
			else sentencia = conexionLocal.
							createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
											ResultSet.CONCUR_READ_ONLY);
			resultado = sentencia.executeQuery(sentenciaSql);
			resultado.first();
		} catch (Exception exSQL) {
			try {
				resultado.close();
			} catch (Exception e) {
			}
			try {
				desconectar();
				conectar(this.dbUrl,this.dbUsuario,this.dbClave);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw (exSQL);
		}
		return resultado;
	}

	/**
	 * Realiza las sentencias de inserci�n y actualizaci�n en la base de datos.
	 * Se tiene como precondici�n la ejecuci�n de la funci�n conectar.
	 * @param sentenciaSql - Sentencia SQL a realizar
	 * @throws Exception Indicando una falla al momento de la ejecuci�n de la
	 * consulta.
	 * */
	public void realizarSentencia(String sentenciaSql) throws Exception {
		Statement sentencia = null;
		try {
			sentencia = conexionLocal.createStatement();
		} catch (SQLException exSQL) {
			throw new Exception("no se pudo realizar la sentencia "+
								sentenciaSql+", ocurrio el siguiente error: "+
								exSQL.getMessage());
		}
		try {
			sentencia.executeUpdate(sentenciaSql);
		} catch (SQLException exSQL) {
			throw new Exception("no se pudo realizar la sentencia "+
					sentenciaSql+", ocurrio el siguiente error: "+
					exSQL.getMessage());
		}
	}
	
	/**
	 * Permite ejecutar un conjunto de sentencias, realiza rollback si 
	 * alguna de las sentencias falla. Se tiene como precondici�n la
	 * ejecuci�n de la funci�n conectar.
	 * @param sentencias Contiene todos los comandos SQL que se realizar�n
	 * en la base de datos
	 * @throws Exception si ocurre alg�n error en la base de datos
	 */
	/*
	* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
	* S�lo se parametriz� el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void ejecutarLoteDeSentencias(Vector<String> sentencias) throws Exception{
		int numSentencias = sentencias.size();
		conexionLocal.setAutoCommit(false);
		conexionLocal.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		Statement loteDeSentencias = conexionLocal.createStatement();
		for(int i=0; i < numSentencias; ++i){
			loteDeSentencias.addBatch((String)sentencias.elementAt(i));
		}
		try{
			loteDeSentencias.executeBatch();
			conexionLocal.commit();
			conexionLocal.setAutoCommit(true);
		}catch(SQLException e){
			conexionLocal.rollback();
			loteDeSentencias.close();
			conexionLocal.setAutoCommit(true);
			throw new Exception("No se pudo ejecutar la sentencia: "+e.getMessage());
		}
		loteDeSentencias.close();
	}
}
