/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.mediadores
 * Programa   : ConexionServCentral.java
 * Creado por : gmartinelli
 * Creado en  : 26/03/2004 09:12 AM
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
package com.becoblohm.cr.mediadoresbd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.manejadorsesion.Sesion;

/** 
 * 		Maneja las operaciones de conexiones a la base de datos centralizada de la
 * empresa para la busqueda de información en la base de datos que se encuentra en
 * dicho servidor.
 */
public class ConexionServCentral {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ConexionServCentral.class);
	
	private static Connection conexion;
	private static boolean conectado = false;
		
	/**
	 * Método conectar.
	 * 		Realiza la conexion a la base de Datos.
	 * @param local Variable boolean que indica a que base de datos se desea conectar. Es verdadero si
	 * 				se conecta a la BD local. False si es a la base de datos centraliada de la tienda.
	 * @throws BaseDeDatosExcepcion Si ocurre un error conexion el Driver o la conexion al URL definido
	 */
	private static void conectar() throws ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("conectar() - start");
		}

		try {
			Class.forName(Sesion.getDbClaseServidorCentral());
		} catch (ClassNotFoundException ex) {
			logger.error("conectar()", ex);

			conectado = false;
			throw (new ConexionExcepcion("Falla en la conexión con la Base de Datos Centralizada. No se encuentra el Driver: ", ex));
		}
		try {
			conexion = DriverManager.getConnection(Sesion.getDbUrlServidorCentral(), Sesion.getDbUsuario(), Sesion.getDbClave());
			conectado = true;
		} catch (SQLException ex) {
			logger.error("conectar()", ex);

			conectado = false;
			throw (new ConexionExcepcion("Falla en la conexion a la BD Centralizada. Servidor inactivo, Url, Usuario o contraseña no valida. No se pudo conectar", ex));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("conectar() - end");
		}
	}
	
	/**
	 * Método realizarConsulta.
	 * 		Ejecuta una sentencia SQL de consulta de registros (Selects).
	 * @param sentenciaSql - Sentencia SELECT
	 * @param local - indicador de lugar a realizar la sentencia (true BdLocal, false BdRemota)
	 * @return ResultSet - Objeto con las filas resultantes de la consulta
	 * @throws ConexionExcepcion - Si el servidor a la base de datos no se encuentra disponible
	 * @throws BaseDeDatosExcepcion - Cuando ocurre un error en la ejecucion
	 * 		de la consulta
	 */
	public static ResultSet realizarConsulta(String sentenciaSql) throws ConexionExcepcion, BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("realizarConsulta(String) - start");
		}

		ResultSet resultado = null;
		Statement sentencia = null;
		
		// Conectamos al servidor central de la empresa
		if(!conectado)
			conectar();

		// Preparamos el query a ejecutar
		try {
			sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException exSQL) {
			logger.error("realizarConsulta(String)", exSQL);

			// Desconectamos la conexion con el servidor central
			try {
				conexion.close();
			} catch (SQLException e) {
				logger.error("realizarConsulta(String)", e);
			}
			conectado = false;
			throw new ConexionExcepcion("Error conectando al servidor central de la empresa",exSQL);
		}

		// Ejecutamos el query
		try {
			sentencia.executeQuery(sentenciaSql);
			resultado = new CRResultSet(sentencia.getResultSet());
			resultado.first();
		} catch (SQLException exSQL) {
			// Si hay un error en la ejecución desconectamos la conexion con el servidor central
			try {
				conexion.close();
			} catch (SQLException e) {
				logger.error("realizarConsulta(String)", e);
			}
			conectado = false;

			// Reconectamos al servidor central y reintentamos una vez mas el Query desde el principio
			try {
				conectar();
				sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				sentencia.executeQuery(sentenciaSql);
				resultado = new CRResultSet(sentencia.getResultSet());
				resultado.first();
			} catch (SQLException e) {
				// Si nuevamente hay un error ejecutando el query, lanzamos una excepción de Base de Datos
				logger.error("realizarConsulta(String)", exSQL);
				throw (new BaseDeDatosExcepcion("Error al realizar sentencia " + sentenciaSql, e));
			}
		}  

		if (logger.isDebugEnabled()) {
			logger.debug("realizarConsulta(String) - end");
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
	public static int realizarSentencia(String sentenciaSql) throws ConexionExcepcion, BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("realizarSentencia(String) - start");
		}

		PreparedStatement sentencia = null;

		// Conectamos al servidor central de la empresa
		if(!conectado)
			conectar();

		try {
			sentencia = conexion.prepareStatement(sentenciaSql);
		} catch (SQLException exSQL) {
			logger.error("realizarSentencia(String)", exSQL);

			// Desconectamos la conexion con el servidor central
			try {
				conexion.close();
			} catch (SQLException e) {
				logger.error("realizarSentencia(String)", e);
			}
			conectado = false;
			throw new ConexionExcepcion("Error conectando al servidor central de la empresa",exSQL);
		}
		int result;
		try {
			result = sentencia.executeUpdate();
		} catch (SQLException exSQL) {
			logger.error("realizarSentencia(String)", exSQL);

			throw (new BaseDeDatosExcepcion("Error al ejecutar sentencia " + sentenciaSql, exSQL));
		} finally {
			try {
				sentencia.close();
			} catch (SQLException e) {
				logger.error("realizarSentencia(String)", e);
			}
			sentencia = null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("realizarSentencia(String) - end");
		}
		return result;
	}
}
