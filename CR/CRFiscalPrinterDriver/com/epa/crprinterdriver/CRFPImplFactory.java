/*
 * $Id: CRFPImplFactory.java,v 1.1.2.5 2005/05/09 14:28:24 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CRFiscalPrinterDriver 2.0
 * Paquete		: com.epa.crprinterdriver
 * Programa		: CRFPImplFactory.java
 * Creado por	: Programa8
 * Creado en 	: 05-may-2004 11:06:30
 * (C) Copyright 2004 SuperFerretería EPA C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisión	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: CRFPImplFactory.java,v $
 * Revision 1.1.2.5  2005/05/09 14:28:24  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.1.2.4  2005/05/09 14:19:05  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.1.2.3  2005/05/06 19:18:03  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.1.2.2  2005/05/06 18:16:59  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.1.2.1  2005/05/05 22:05:42  programa8
 * Versión 2.1:
 * *- Preparación para funcionamiento con EPSON TMU950PF
 * *- Toma en cuenta la solicitud de incremento en espera por timeout
 * *- Considera independencia de dispositivo en colchones de tiempo
 * *- Considera funciones que pueden estar implementadas en un dispositivo
 * no necesariamente implementadas en otro
 * *- Separa Subsistema base del driver de la fachada usadas por las
 * aplicaciones
 * *- Maneja estatus de activación del slip. Considera colchones de tiempo
 * para comando enviados al slip.
 * *- Patron de diseño Abstract Factory para la selección de la implementación
 * de dispositivo a partir de propiedad de la aplicación
 *
 * ===========================================================================
 */
package com.epa.crprinterdriver;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.epa.crserialinterface.Parameters;

/**
 * 
 * <p>
 * Esta clase sirve como f&aacute;brica de la implementación dependiente de
 * dispositivo. Lee el archivo de configuración del subsistema de impresi&oacute;n
 * fiscal (fiscalprinter.properties) para determinar cual es la implementación actual
 * </p>
 * <p>
 * <a href="CRFPImplFactory.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - Arístides Castillo Colmenárez - $Author: programa8 $
 * @version $Revision: 1.1.2.5 $ - $Date: 2005/05/09 14:28:24 $
 * @since 05-may-2004
 * 
 */
public abstract class CRFPImplFactory {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CRFPImplFactory.class);

	private static Properties props = new Properties();
	private static String className;
	private static final String fallbackClassName = "com.epa.crprinterdriver.impl.NPF4610Factory";
	protected CRFPSubsystem system = null;

	/**
	 * Devuelve la instancia de construcción de la implementación dependiente
	 * del dispositivo
	 * @since 05-may-2004
	 */
	/*
	* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato 'Class'
	* Fecha: agosto 2011
	*/
	public static CRFPImplFactory getInstance() {
		if (logger.isDebugEnabled()) {
			logger.debug("getInstance() - start");
		}

		loadProperties();
		className = props.getProperty("fiscalprinter.implfactory");
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
			logger.warn("getInstance(className = " + className + ") - Clase no encontrada", e);
			try {
				if (cl != null) {
					tpClass = cl.loadClass(fallbackClassName);
				} else {
					tpClass = Class.forName(fallbackClassName);
				}
				o = tpClass.newInstance();
			} catch (ClassNotFoundException e1) {
				logger.error("getInstance()", e1);
			} catch (InstantiationException e1) {
				logger.error("getInstance()", e1);
			} catch (IllegalAccessException e1) {
				logger.error("getInstance()", e1);
			}
		} catch (IllegalAccessException e) {
			logger.error("getInstance()", e);
		} catch (InstantiationException e) {
			logger.error("getInstance()", e);
		}

		CRFPImplFactory returnCRFPImplFactory = (CRFPImplFactory) o;
		if (logger.isDebugEnabled()) {
			logger.debug("getInstance() - end");
		}
		return returnCRFPImplFactory;
	}
	
	private static void loadProperties() {
		if (logger.isDebugEnabled()) {
			logger.debug("loadProperties() - start");
		}

    	try {
			props.load(CRFiscalPrinterOperations.class.getResourceAsStream("/com/epa/crprinterdriver/fiscalprinter.properties"));
		} catch (IOException e) {
			logger.error("loadProperties()", e);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("loadProperties() - end");
		}
	}
	
	private static ClassLoader getClassLoader() {
		if (logger.isDebugEnabled()) {
			logger.debug("getClassLoader() - start");
		}

		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		if (cl != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("getClassLoader() - end");
			}
			return cl;
		}
		cl = ClassLoader.getSystemClassLoader();

		if (logger.isDebugEnabled()) {
			logger.debug("getClassLoader() - end");
		}
		return cl;
	}
	
	/**
	 * Crea una instancia del int&eacute;rprete de respuesta del dispositivo actual
	 * @return Instancia del int&eacute;rprete
	 */
	public abstract CRFPResponseParser getResponseParserInstance() ;
	
	/**
	 * Crea una instancia del mapa de secuencias del dispositivo actual
	 * @return Instancia del mapa de secuencias
	 */
	public abstract CRFPSequenceMap getSequenceMapInstance();
	
	/**
	 * Crea una instancia del motor del dispositivo actual
	 * @return Instancia del mapa de secuencias
	 */
	public abstract CRFPEngine getEngineInstance(CRFPSubsystem system, Parameters serialParameters);
		
	/**
	 * Establece la instancia del subsistema del driver disponible para
	 * las implementaciones dependientes de dispositivo
	 * @param system Instancia de subsistema del driver
	 */
	public void setSystemBase(CRFPSubsystem system) {
		if (logger.isDebugEnabled()) {
			logger.debug("setSystemBase(CRFPSubsystem) - start");
		}

		this.system = system;

		if (logger.isDebugEnabled()) {
			logger.debug("setSystemBase(CRFPSubsystem) - end");
		}
	}

}
