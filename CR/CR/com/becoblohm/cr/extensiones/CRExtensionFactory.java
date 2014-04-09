/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.extensions
 * Programa   : CRExtensionFactory.java
 * Creado por : Programador8 - Arístides Castillo
 * Creado en  : 19/12/2004 04:10:00 PM
 *
 * (c) Becoblohm, C.A.
 * =============================================================================
 */
package com.becoblohm.cr.extensiones;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * Clase abstracta raiz de la jerarquia de extensiones de CR
 * Todo factory de extension debe extender esta clase abstracta
 * @author Programa8 - Arístides Castillo
 */
public abstract class CRExtensionFactory {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(CRExtensionFactory.class);
	
	Properties props;
	public static final String propFileName = "CRExtension.properties";

	/**
	 * Constructor por defecto 
	 */
	public CRExtensionFactory() {
		super();
		loadProperties();
	}
	/**
	 * Devuelve el nombre de la clase que implementa la extension
	 * por defecto.
	 * Toda extensión debe tener un comportamiento por defecto, para
	 * de esa manera no obligar a la necesidad de creación de dicha extensión
	 * a aquellos que no requieren un comportamiento distinto al que ya 
	 * realiza la caja. 
	 * Las clases de implementación por defecto deben ser ubicadas en el paquete
	 * com.becoblohm.cr.extensiones.impl
	 * @return Nombre de la clase de implementacion por defecto
	 */
	public abstract String getDefaultImplClass();

	/**
	 * Devuelve el nombre de la interfaz usada para la extensión
	 * @return Nombre de la interfaz de extensión
	 */
	public abstract String getExtensionIntfName();
	
	/**
	 * Carga las propiedades de las extensiones desde el archivo CRExtension.properties, 
	 * para la carga de las clases que implementan las extensiones.
	 * Dicho archivo de propiedades es buscado primero en user.home y luego en 
	 * el classpath del sistema.
	 */
	private void loadProperties() {
		InputStream propIS;
		props = new Properties();
		// Busco en la carpeta de usuario
		try {
			String propsFile = System.getProperty("user.home") + File.separator + propFileName; 
			propIS = new FileInputStream(propsFile);
		} catch (FileNotFoundException e) {
			propIS = null;
		}
		// Busco como recurso del sistema en el classpath
		if (propIS == null) {
			propIS = CRExtensionFactory.class.getResourceAsStream(propFileName);
		}
		// Busca la ubicacion por defecto en el paquete extensiones
		if (propIS == null) {
			propIS = CRExtensionFactory.class.getResourceAsStream("/com/becoblohm/cr/extensiones/"+propFileName);
		}
		
  		if (propIS != null) {
			try {
				props.load(propIS);
			} catch (Throwable e) {
				logger.error("loadProperties()", e);
			}
		} 
	}
	
	private ClassLoader getClassLoader() {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		if (cl != null) {
			return cl;
		}
		cl = this.getClass().getClassLoader();
		if (cl != null) {
			return cl;
		}
		cl = ClassLoader.getSystemClassLoader();
		return cl;
	}
	
	public CRExtension getExtensionInstance() {
		String className = props.getProperty("cr.extension." + getExtensionIntfName().toLowerCase() + ".class");
		String fallbackClassName;
		if (className == null) {
			className = getDefaultImplClass();
			logger.warn(
					"getExtensionInstance() - Classname is null : fallbackClassName = "
							+ className, null);

			fallbackClassName = null;
		} else {
			fallbackClassName = getDefaultImplClass();
		}
		return getExtensionInstance(className, fallbackClassName);		
	}
	
	/*
	* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato 'Class'
	* Fecha: agosto 2011
	*/
	protected CRExtension getExtensionInstance(String className, String fallbackClassName) {
		ClassLoader cl = getClassLoader();
		Object o = null;
		Class<?> tpClass;
		try {
			if (cl != null) {
				tpClass = cl.loadClass(className);
			} else {
				tpClass = Class.forName(className);
			}
			o = tpClass.newInstance();
		} catch (ClassNotFoundException e) {
			logger.warn("getExtensionInstance(className = " + className
					+ ") - Clase no encontrada", e);

			if (fallbackClassName != null) {
				return getExtensionInstance(fallbackClassName, null);
			} else {
				throw new RuntimeException("No se pudo obtener extension", e);
			}
		} catch (IllegalAccessException e) {
			logger.error("getExtensionInstance(String, String)", e);
		} catch (InstantiationException e) {
			logger.error("getExtensionInstance(String, String)", e);
		}
		return (CRExtension)o;		
		
	}

}
