/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.colascr.transferencias.configuracion
 * Programa   : Sesion.java
 * Creado por : Ileana Rojas, Gabriel Martinelli
 * Creado en  : 21/02/2005 04:23:59 PM
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
package com.beco.ventas.politicasmantenimiento;

import java.io.File;
import java.util.LinkedHashMap;

import com.epa.preferencesproxy.EPAPreferenceProxy;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.NodeAlreadyExistsException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;


/** 
 * Descripción: 
 * 		Esta clase carga los parámetros y variables necesarias para el 
 * correcto funcionamiento del sistema. También proporciona métodos que 
 * permiten el control de cada una de sus propiedades.
 */
public class Sesion {

	
	// Parámetros de conexión con la base de datos
	private static String dbEsquema;
	private static String dbClaseLocal;
	private static String dbUrlLocal;
	private static String dbClaseServidor;
	private static String dbUrlServidor;

	// El objetivo es registrar los mismos datos de acceso a BD para la CR y el Servidor
	private static String dbUsuario;	
	private static String dbClave;		


	//  Variable de dirección de los archivos a ser parseados
	public static String pathArchivosPref = "";
	public static String pathArchivos = "";


	
	/**
	 * Contructor para Sesion
	 * 		Crea la sesión actual. Inicializa los parámetros (clase, url, 
	 * usuario, clave) por defecto para la conexión a la base de datos de 
	 * la caja registradora y del servidor para tomar todos los parametros 
	 * necesarios para el sistema.
	 */
	public Sesion () throws Exception {
		if (cargarPreferencias()) {
			File f = new File(pathArchivosPref);
			pathArchivos = f.getAbsolutePath() + File.separatorChar;
			pathArchivos = pathArchivos.replace(File.separatorChar,'/');
			pathArchivos = pathArchivos.replaceAll("/","//");
			System.out.println(pathArchivos);
	
			// Creamos la conexion con la base de datos con los datos locales por defecto
			new Conexiones();
		} else {
			throw new Exception("** Problema cargando las Preferencias");
		}
	}
	
	/**
	 * Método cargarPreferencias
	 * 
	 * @throws NodeAlreadyExistsException
	 * @throws NoSuchNodeException
	 * @throws UnidentifiedPreferenceException
	 */
	@SuppressWarnings("unchecked")
	private boolean cargarPreferencias() {
		EPAPreferenceProxy preferencias = new EPAPreferenceProxy("proyectocr");
		LinkedHashMap<String,String> preferenciasServ = new LinkedHashMap<String,String>();
		try {
			preferenciasServ = preferencias.getAllPreferecesForNode("colasCR");
			Sesion.setDbEsquema(preferenciasServ.get("dbEsquema").toString());
			Sesion.setDbClaseLocal(preferenciasServ.get("dbClaseLocal").toString());
			Sesion.setDbClaseServidor(preferenciasServ.get("dbClaseServidor").toString());
			Sesion.setDbUrlLocal(preferenciasServ.get("dbUrlLocal").toString());
			Sesion.setDbUrlServidor(preferenciasServ.get("dbUrlServidor").toString());
			Sesion.setDbUsuario(preferenciasServ.get("dbUsuario").toString());
			Sesion.setDbClave(preferenciasServ.get("dbClave").toString());
			Sesion.setPathArchivosPref(preferenciasServ.get("pathArchivosTransfer").toString());
		} catch (NoSuchNodeException e) {
			e.printStackTrace();
			return false;
		} catch (UnidentifiedPreferenceException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static String getDbClaseLocal() {
		return dbClaseLocal;
	}

	public static void setDbClaseLocal(String dbClaseLocal) {
		Sesion.dbClaseLocal = dbClaseLocal;
	}

	public static String getDbClaseServidor() {
		return dbClaseServidor;
	}

	public static void setDbClaseServidor(String dbClaseServidor) {
		Sesion.dbClaseServidor = dbClaseServidor;
	}

	public static String getDbClave() {
		return dbClave;
	}

	public static void setDbClave(String dbClave) {
		Sesion.dbClave = dbClave;
	}

	public static String getDbEsquema() {
		return dbEsquema;
	}

	public static void setDbEsquema(String dbEsquema) {
		Sesion.dbEsquema = dbEsquema;
	}

	public static String getDbUrlLocal() {
		return dbUrlLocal;
	}

	public static void setDbUrlLocal(String dbUrlLocal) {
		Sesion.dbUrlLocal = dbUrlLocal;
	}

	public static String getDbUrlServidor() {
		return dbUrlServidor;
	}

	public static void setDbUrlServidor(String dbUrlServidor) {
		Sesion.dbUrlServidor = dbUrlServidor;
	}

	public static String getDbUsuario() {
		return dbUsuario;
	}

	public static void setDbUsuario(String dbUsuario) {
		Sesion.dbUsuario = dbUsuario;
	}

	public static String getPathArchivos() {
		return pathArchivos;
	}

	public static void setPathArchivos(String pathArchivos) {
		Sesion.pathArchivos = pathArchivos;
	}

	public static String getPathArchivosPref() {
		return pathArchivosPref;
	}

	public static void setPathArchivosPref(String pathArchivosPref) {
		Sesion.pathArchivosPref = pathArchivosPref;
	}	
}