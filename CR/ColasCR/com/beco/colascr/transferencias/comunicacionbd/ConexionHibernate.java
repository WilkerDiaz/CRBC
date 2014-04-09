/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.mediadoresbd
 * Programa   : ConexionHibernate.java
 * Creado por : Programador3
 * Creado en  : 11/06/2004 04:55:02 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.2.0
 * Fecha       : 29/06/2004 10:16:02 AM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : - Modificado acceso al archivo de configuración de Hibernate y cambiados valroes de los parámetros
 * 					de C3PO.
 * 					 - Modificación del método de creación del sessionFactory de modo que sea parametrizable la sesión 
 * 					a crear.
 * =============================================================================
 * Versión     : 1.1
 * Fecha       : 11/06/2004 04:55:02 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Implementación inicial.
 * =============================================================================
 */
package com.beco.colascr.transferencias.comunicacionbd; 

import java.net.URL;
import java.sql.Connection;
import java.util.Properties;

import com.beco.colascr.transferencias.InitSincronizador;
import com.beco.colascr.transferencias.configuracion.Sesion;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.cfg.Configuration;

/**
 * Descripción:
 * 		Configura el archivo para el pool de conexiones para la CR y para el 
 * Servidor necesarios para la sincronización de las entidades previamente 
 * definidas y mapeadas por Hibernate.
 */

public class ConexionHibernate {
	private static SessionFactory sessionFactoryCR;
	private static SessionFactory sessionFactorySRV;

	/*
	 * Variables estáticas que permiten el control de las conexiones activas para cada
	 * cliente para el respectivo tipo de conexión.
	 */
	 public static final ThreadLocal<Session> sessionCR = new ThreadLocal<Session>();
	 public static final ThreadLocal<Session> sessionSRV = new ThreadLocal<Session>();

	static {
		crearSessionFactory(true);
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se comentaron variables sin uso
	* Fecha: agosto 2011
	*/
	public static void crearSessionFactory(boolean ambasBD){
		Properties propiedades;
		String dialectoSRV, dialectoCR, /*poolSize,*/ batchSize, mostrarSql;
		//String cacheSize, maxInactivo, chequeaInactivos;
		String /*usuarioBD, claveBD,*/ configHibernate;
		String dialectoMySQL = new String("net.sf.hibernate.dialect.MySQLDialect");
		String dialectoDB2400 = new String("net.sf.hibernate.dialect.DB2400Dialect");
		//String poolConexiones = new String("net.sf.hibernate.connection.C3P0ConnectionProvider");

		try {
			if(Sesion.getDbClaseServidor().lastIndexOf("as400") > 0)
			dialectoSRV = new String(dialectoDB2400);
			else dialectoSRV = new String(dialectoMySQL);
			dialectoCR = dialectoMySQL;
			//poolSize = "1";
			batchSize = "15";
			mostrarSql = "false";
			/*cacheSize = "50";
			maxInactivo = "0";
			chequeaInactivos = "60";	*/
			/*usuarioBD = new String(Sesion.getDbUsuario());
			claveBD = new String(Sesion.getDbClave());*/
			configHibernate = new String("/com/beco/colascr/transferencias/sincronizador/hibernate/hibernate.entidades.cfg.xml");

			propiedades = new Properties();
			// Propiedades para C3PO
//			propiedades.setProperty("hibernate.connection.provider_class", poolConexiones);
//			propiedades.setProperty("hibernate.c3p0.idleConnectionTestPeriod", chequeaInactivos);
//			propiedades.setProperty("hibernate.c3p0.minPoolSize", "1");
//			propiedades.setProperty("hibernate.c3p0.maxPoolSize", poolSize);
//			propiedades.setProperty("hibernate.c3p0.maxIdleTime", maxInactivo);
//			propiedades.setProperty("hibernate.c3p0.maxStatements", cacheSize);
			// Propiedades para Hibernate
//			propiedades.setProperty("hibernate.connection.username", usuarioBD);
//			propiedades.setProperty("hibernate.connection.password", claveBD);
//			propiedades.setProperty("hibernate.connection.pool_size", poolSize);
			propiedades.setProperty("hibernate.jdbc.batch_size", batchSize);
			propiedades.setProperty("hibernate.dialect", dialectoCR);
			propiedades.setProperty("hibernate.show_sql", mostrarSql);

			// Propiedades para BD local
			if(ambasBD){
//				propiedades.setProperty("hibernate.connection.driver_class", InitCR.preferenciasCR.getConfigStringForParameter("db", "dbClaseLocal"));
//				propiedades.setProperty("hibernate.connection.url", InitCR.preferenciasCR.getConfigStringForParameter("db", "dbUrlLocal"));
				propiedades.setProperty("hibernate.dialect", dialectoCR);
				URL url = ConexionHibernate.class.getResource(configHibernate);
				sessionFactoryCR = new Configuration().configure(url).setProperties(propiedades).buildSessionFactory();

			}
			
			// Propiedades para BD del servidor de tienda 
//			propiedades.setProperty("hibernate.connection.driver_class", InitCR.preferenciasCR.getConfigStringForParameter("db", "dbClaseServidor"));
//			propiedades.setProperty("hibernate.connection.url", InitCR.preferenciasCR.getConfigStringForParameter("db", "dbUrlServidor"));
			propiedades.setProperty("hibernate.dialect", dialectoSRV);
			URL url = InitSincronizador.class.getResource(configHibernate);
			sessionFactorySRV = new Configuration().configure(url).setProperties(propiedades).buildSessionFactory();
		} catch (HibernateException ex) {
//			ex.printStackTrace();
			throw new RuntimeException("Problema de Configuracion: " + ex.getMessage());
		}
	}

	/**
	 * Método currentSession
	 * 		Retorna la conexión activa para Hibernate.
	 * @param local - Indicador del tipo de conexión a retornar: true -> CR y false -> SRV
	 * @return Session
	 * @throws HibernateException
	 */
	public static Session currentSession(boolean local, Connection cnx) throws HibernateException {
		Session session = null;
		if((sessionFactoryCR == null)||(sessionFactorySRV == null)) crearSessionFactory(true);
		if(local){
			session = (Session) sessionCR.get();
			// Open a new Session, if this Thread has none yet
			if (session == null) {
				session = sessionFactoryCR.openSession(cnx);
				sessionCR.set(session);
			}
		}
		else {
			session = (Session) sessionSRV.get();
			// Open a new Session, if this Thread has none yet
			if (session == null) {
				session = sessionFactorySRV.openSession(cnx);
				sessionSRV.set(session);
			}
		}
		return session;
	}

	/**
	 * Método closeSession
	 * 		Cierra la conexión activa indicada de Hibernate.
	 * @param local - Indicador del tipo de conexión a retornar: true -> CR y false -> SRV
	 * @throws HibernateException
	 */
	public static void closeSession(boolean local) throws HibernateException {
		Session session = null;
		if(local){
			session = (Session) sessionCR.get();
			sessionCR.set(null);
			if (session != null) 
				session.close();
		}
		else {
			session = (Session) sessionSRV.get();
			sessionSRV.set(null);
			if (session != null) 
				session.close();
		}
	}	

}
