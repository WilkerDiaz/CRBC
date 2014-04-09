/*
 * $Id: CRSIPreferencesProxy.java,v 1.4 2005/03/09 20:50:05 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CRSerialInterface
 * Paquete		: com.epa.crserialinterface
 * Programa		: CRSIPreferencesProxy.java
 * Creado por	: vmedina
 * Creado en 	: Apr 22, 2004 9:09:17 AM
 * (C) Copyright 2004 SuperFerretería EPA C.A. Todos los Derechos Reservados
 * 
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: CRSIPreferencesProxy.java,v $
 * Revision 1.4  2005/03/09 20:50:05  programa8
 * CRSerialInterface al 09/03/2005
 *
 * Revision 1.1  2004/07/05 18:42:35  vmedina
 * *** empty log message ***
 *
 * Revision 1.3  2004/04/25 16:51:08  vmedina
 * Esta version ya contiene completamente implementada la capacidad
 * de salvar la config al disco
 *
 * Revision 1.2  2004/04/24 17:20:44  vmedina
 * *** empty log message ***
 *
 * Revision 1.1  2004/04/23 20:10:58  vmedina
 * Agregamos CRSIPreferencesProxy para leer y guardar las preferencias del puerto
 * Agregamos CRSILoggerProxy para Logear utilizando log4J
 * NoSuchPreferencesException fue agregada
 *
 * ===========================================================================
 */
package com.epa.crserialinterface;

import java.util.prefs.*;



/**
 * <p>Indicar aquí, en una línea que hace la clase</p>
 * <p>Agregar parrafos que describen como se usa la Clase y un resumen de como 
 * funciona.</p>
 * @author $Author: programa8 $  
 * @version $Revision: 1.4 $<br>$Date: 2005/03/09 20:50:05 $
 * <!-- A continuación indicar referencia a otras páginas o clases parecidas -->
 * <!-- o relacionados, pundiendo indicar inclusive los métodos relacionados -->
 * <!-- por ejemplo: @see     <a href="package-summary.html#charenc">Character encodings</a> -->
 * <!-- por ejemplo: @see     java.lang.Object 								 -->
 * <!-- por ejemplo: @see     java.lang.Object#toNewString() 				 -->
 * @see     {RutaObjeto}#NombreMetodo()
 * @since   <!-- Indique desde que versión del Proyecto existe esta clase 	 -->
 */
public class CRSIPreferencesProxy
{
	/* Lista de Settings del Sistema */
	private String systemPathSeparator;
	private String systemUserCountry;
	private String systemCurrentUserDirectoryPath;
	private String systemArch;
	private String systemLineSeparator;
	private String systemOSName;
	private String systemCurrentOSVersion;
	private String systemUserHomeDirectoryPath;
	private String systemTimeZone;
	private String systemDefaultFileEncoding;
	private String systemCurrentUserName;
	private String systemDefaultLanguage;
	private String systemSeparator;

	/* Lista de settings del JVM */
	private String javaFileEncodingPackage;
	private String javaTemporalWorkingDirectoryPath;
	private String javaLibraryPath;
	private String javaPreferencesFactory;
	private String javaDefaultPrinterJob;
	private String javaJDKSpecsVersion;
	private String javaDefaultClassPath;
	private String javaHomePath;
	private String javaJDKVersion;
	
	/* Lista de Atributos Privados de CRSIPreferencesProxy */
	private String currentPreferenceNodePath; 
	private String currentPortName;
	private String currentBaudRateConfigForPort;
	private String currentDataBitsConfigForPort;
	private String currentParityConfigForPort;
	private String currentStopBitConfigForPort;
	private String currentFlowControlOutConfigForPort;
	private String currentFlowControlInConfigForPort;
	
	
	/* Inicializamos la clase de preferences */ 
	Preferences thisClassPreferences;
	Preferences crsiPreferencesTopNode;
		
	/* Inicizalimos el logger para esta clase */ 
	private CRSILoggerProxy crsiLoggerProxy;
		
	/*
	 * ---------------------------------------------------------------------------
	 * CRSIPreferencesProxy()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 23, 2004 1:41:52 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * 
	 */
	public CRSIPreferencesProxy(){
		/* Nos traemos una instancia del Logger para poder logear mensajes y errores */
		crsiLoggerProxy = new CRSILoggerProxy(getClass().getName());
		initCRSIPreferencesProxy();
		initCRSIPreferencesBackingStorage();
		
	}
	
	
	/*
	 * ---------------------------------------------------------------------------
	 * void initCRSIPreferencesProxy()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 23, 2004 1:42:01 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * 
	 */
	private void initCRSIPreferencesProxy(){
		
		/* Antes que nada recuperamos los settings del sistema */
		try
		{
			setSystemPathSeparator(System.getProperty("path.separator"));
			setSystemUserCountry(System.getProperty("user.country"));
			setSystemCurrentUserDirectoryPath(System.getProperty("user.dir"));
			setSystemArch(System.getProperty("os.arch"));
			setSystemLineSeparator(System.getProperty("line.separator"));
			setSystemOSName(System.getProperty("os.name"));
			setSystemCurrentOSVersion(System.getProperty("os.version")); 
			setSystemUserHomeDirectoryPath(System.getProperty("user.home"));
			setSystemTimeZone(System.getProperty("user.timezone"));
			setSystemDefaultFileEncoding(System.getProperty("file.encoding"));
			setSystemCurrentUserName(System.getProperty("user.name"));
			setSystemDefaultLanguage(System.getProperty("user.language"));
			setSystemSeparator(System.getProperty("file.separator"));
			setJavaFileEncodingPackage(System.getProperty("file.encoding.pkg"));
			setJavaTemporalWorkingDirectoryPath(System.getProperty("java.io.tmpdir"));
			setJavaLibraryPath(System.getProperty("java.library.path")); 	
			setJavaPreferencesFactory(System.getProperty("java.util.prefs.PreferencesFactory"));
			setJavaDefaultPrinterJob(System.getProperty("java.awt.printerjob"));
			setJavaJDKSpecsVersion(System.getProperty("java.specification.version"));
			setJavaDefaultClassPath(System.getProperty("java.class.path"));
			setJavaHomePath(System.getProperty("java.home"));
			setJavaJDKVersion(System.getProperty("java.version"));
		}
		catch (RuntimeException e)
		{
			crsiLoggerProxy.crsiDoLogging("Ocurrio un error al tratar de recuperar las preferencias del sistema",3);
			crsiLoggerProxy.crsiDoLogging(e.getMessage(),3);
			crsiLoggerProxy.crsiDoLogging(e.getStackTrace().toString(),3);
		}		
	}
	
	
	/*
	 * ---------------------------------------------------------------------------
	 * void crsiPreferencesInitBackingStorage()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 23, 2004 9:23:01 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * 
	 */
	private void initCRSIPreferencesBackingStorage(){
		
		/* Inicializamos el objeto de preferences */
		thisClassPreferences = Preferences.userNodeForPackage(getClass());
		crsiPreferencesTopNode = thisClassPreferences.node(getCurrentPreferenceNodePath());
		try
		{
			crsiPreferencesTopNode.flush();
			crsiPreferencesTopNode.sync();
			crsiLoggerProxy.crsiDoLogging("Preferences correctamente inicializada en: "+crsiPreferencesTopNode.absolutePath(),1);
		}
		catch (BackingStoreException e)
		{
			crsiLoggerProxy.crsiDoLogging("Ocurrio un error al tratar de inicializar el BackingStore para CRSIPreferencesProxy",3);
			crsiLoggerProxy.crsiDoLogging(e.getMessage(),3);
			e.printStackTrace();
		}
	}
	
	/*
	 * ---------------------------------------------------------------------------
	 * void addSerialPortNodeToPreferencesTopNode()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 23, 2004 1:42:20 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param portName
	 */
	public void addSerialPortNodeToPreferencesTopNode(String portName){
		
		/* Una nueva instancia del Preferences Object para agregar el neuvo node */
		Preferences newSerialPortToAdd;
		
		/* Bajo los UNIX'ces los portName siempre comienza con "/" bajo Win32 no */
		if(portName.startsWith("/",0)){
			
			/* Existe el nodo? */
			try
			{
				if(!crsiPreferencesTopNode.nodeExists(crsiPreferencesTopNode.absolutePath()+portName)){
					/* Parece que es un port de Unix o linux*/
					newSerialPortToAdd = crsiPreferencesTopNode.node(crsiPreferencesTopNode.absolutePath()+portName);
				}else{
					crsiLoggerProxy.crsiDoLogging("El nodo "+crsiPreferencesTopNode.absolutePath()+portName+" ya existia, ignoro la orden de creación",2);
				}
			}
			catch (BackingStoreException e)
			{
				crsiLoggerProxy.crsiDoLogging("Ocurrio un error al tratar de acceder al PreferencesBackingStorage",3);
				crsiLoggerProxy.crsiDoLogging(e.getMessage(),3);
				e.printStackTrace();
			}
		}else{
			try
			{
				if(!crsiPreferencesTopNode.nodeExists(crsiPreferencesTopNode.absolutePath()+"/"+portName)){
					/* NO pace ser un port de linux, mas bien parece de windows, le agregamos un "/" */
					newSerialPortToAdd = crsiPreferencesTopNode.node(crsiPreferencesTopNode.absolutePath()+"/"+portName);
				}else{
					crsiLoggerProxy.crsiDoLogging("El nodo "+crsiPreferencesTopNode.absolutePath()+"/"+portName+" ya existia, ignoro la orden de creación",2);
				}
			}
			catch (BackingStoreException e)
			{
				crsiLoggerProxy.crsiDoLogging("Ocurrio un error al tratar de acceder al PreferencesBackingStorage",3);
				crsiLoggerProxy.crsiDoLogging(e.getMessage(),3);
				e.printStackTrace();
			}
		}
	}
	
	
	/*
	 * ---------------------------------------------------------------------------
	 * String getSerialPortAbsolutePath()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 23, 2004 2:17:20 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param portName
	 * @return
	 */
	private String getAbsolutePathForPort(String portName){
		
		/* Bajo los UNIX'ces los portName siempre comienza con "/" bajo Win32 no */
		if(portName.startsWith("/",0)){
			
			/* Existe el nodo? */
			try
			{
				if(!crsiPreferencesTopNode.nodeExists(crsiPreferencesTopNode.absolutePath()+portName)){
					return null;
				}else{
					return crsiPreferencesTopNode.absolutePath()+portName;
				}
			}
			catch (BackingStoreException e)
			{
				crsiLoggerProxy.crsiDoLogging("Ocurrio un error al tratar de acceder al PreferencesBackingStorage",3);
				crsiLoggerProxy.crsiDoLogging(e.getMessage(),3);
				e.printStackTrace();
			}
		}else{
			try
			{
				if(!crsiPreferencesTopNode.nodeExists(crsiPreferencesTopNode.absolutePath()+"/"+portName)){
					return null;
				}else{
					return crsiPreferencesTopNode.absolutePath()+"/"+portName;
				}
			}
			catch (BackingStoreException e)
			{
				crsiLoggerProxy.crsiDoLogging("Ocurrio un error al tratar de acceder al PreferencesBackingStorage",3);
				crsiLoggerProxy.crsiDoLogging(e.getMessage(),3);
				e.printStackTrace();
			}
		}		
		return null;
	}
	
	/*
	 * ---------------------------------------------------------------------------
	 * String getConfigStringForParameter()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 23, 2004 2:44:22 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param portName
	 * @param desiredParameter
	 * @return
	 */
	private String getConfigStringForParameter(String portName, String desiredParameter){
	
		Preferences preferenceToRetrieve;
		preferenceToRetrieve = crsiPreferencesTopNode.node(getAbsolutePathForPort(portName));
		return preferenceToRetrieve.get(desiredParameter,null);		
	}
	
	
	/*
	 * ---------------------------------------------------------------------------
	 * int setConfigStringForParameter()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 23, 2004 3:34:55 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return int 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param portName
	 * @param desiredParameter
	 * @param configString
	 * @return
	 */
	private int setConfigStringForParameter(String portName, String desiredParameter, String configString){
		Preferences preferenceToStore;
		
		try
		{
			if(isSerialPortConfigured(portName)){
				preferenceToStore = crsiPreferencesTopNode.node(getAbsolutePathForPort(portName));
				preferenceToStore.put(desiredParameter,configString);
				return 1;
			}
		}
		catch (RuntimeException e)
		{
			crsiLoggerProxy.crsiDoLogging("Ocurrio un error al tratar de almacenar: "+" como parametro para "+portName+" dentro del BackingStorage: "+getAbsolutePathForPort(portName),3);
			crsiLoggerProxy.crsiDoLogging(e.getMessage(),3);
			return 0;
			
		}
		return 0;
	}
	
	
	/*
	 * ---------------------------------------------------------------------------
	 * boolean isSerialPortConfigured()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 23, 2004 2:29:43 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return boolean 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param portName
	 * @return
	 */
	public boolean isSerialPortConfigured(String portName){

		/* Bajo los UNIX'ces los portName siempre comienza con "/" bajo Win32 no */
		if(portName.startsWith("/",0)){
			
			/* Existe el nodo? */
			try
			{
				if(!crsiPreferencesTopNode.nodeExists(crsiPreferencesTopNode.absolutePath()+portName)){
					return false;
				}else{
					return true;
				}
			}
			catch (BackingStoreException e)
			{
				crsiLoggerProxy.crsiDoLogging("Ocurrio un error al tratar de acceder al PreferencesBackingStorage",3);
				crsiLoggerProxy.crsiDoLogging(e.getMessage(),3);
				e.printStackTrace();
			}
		}else{
			try
			{
				if(!crsiPreferencesTopNode.nodeExists(crsiPreferencesTopNode.absolutePath()+"/"+portName)){
					return false;
				}else{
					return true;
				}
			}
			catch (BackingStoreException e)
			{
				crsiLoggerProxy.crsiDoLogging("Ocurrio un error al tratar de acceder al PreferencesBackingStorage",3);
				crsiLoggerProxy.crsiDoLogging(e.getMessage(),3);
				e.printStackTrace();
			}
		}				
		return false;
	}
	
	
	/*
	 * ---------------------------------------------------------------------------
	 * void crsiSyncFlush()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 23, 2004 1:42:33 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * 
	 */
	public void crsiSyncFlush(){
		
		try
		{
			crsiPreferencesTopNode.flush();
			crsiPreferencesTopNode.sync();
		}
		catch (BackingStoreException e)
		{
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
	}
	
	/*
	 * ---------------------------------------------------------------------------
	 * String getJavaDefaultClassPath()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:04:59 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getJavaDefaultClassPath()
	{
		return javaDefaultClassPath;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * String getJavaDefaultPrinterJob()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:04:59 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getJavaDefaultPrinterJob()
	{
		return javaDefaultPrinterJob;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * String getJavaFileEncodingPackage()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:04:59 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getJavaFileEncodingPackage()
	{
		return javaFileEncodingPackage;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * String getJavaHomePath()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:04:59 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getJavaHomePath()
	{
		return javaHomePath;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * String getJavaJDKSpecsVersion()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:04:59 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getJavaJDKSpecsVersion()
	{
		return javaJDKSpecsVersion;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * String getJavaJDKVersion()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:04:59 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getJavaJDKVersion()
	{
		return javaJDKVersion;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * String getJavaLibraryPath()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:04:59 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getJavaLibraryPath()
	{
		return javaLibraryPath;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * String getJavaPreferencesFactory()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:04:59 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getJavaPreferencesFactory()
	{
		return javaPreferencesFactory;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * String getJavaTemporalWorkingDirectoryPath()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:04:59 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getJavaTemporalWorkingDirectoryPath()
	{
		return javaTemporalWorkingDirectoryPath;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * String getSystemArch()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:04:59 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getSystemArch()
	{
		return systemArch;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * String getSystemCurrentOSVersion()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:04:59 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getSystemCurrentOSVersion()
	{
		return systemCurrentOSVersion;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * String getSystemCurrentUserDirectoryPath()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:04:59 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getSystemCurrentUserDirectoryPath()
	{
		return systemCurrentUserDirectoryPath;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * String getSystemCurrentUserName()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:04:59 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getSystemCurrentUserName()
	{
		return systemCurrentUserName;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * String getSystemDefaultFileEncoding()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:04:59 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getSystemDefaultFileEncoding()
	{
		return systemDefaultFileEncoding;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * String getSystemDefaultLanguage()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:04:59 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getSystemDefaultLanguage()
	{
		return systemDefaultLanguage;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * String getSystemLineSeparator()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:04:59 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getSystemLineSeparator()
	{
		return systemLineSeparator;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * String getSystemOSName()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:04:59 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getSystemOSName()
	{
		return systemOSName;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * String getSystemPathSeparator()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:04:59 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getSystemPathSeparator()
	{
		return systemPathSeparator;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * String getSystemSeparator()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:05:00 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getSystemSeparator()
	{
		return systemSeparator;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * String getSystemTimeZone()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:05:00 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getSystemTimeZone()
	{
		return systemTimeZone;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * String getSystemUserCountry()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:05:00 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getSystemUserCountry()
	{
		return systemUserCountry;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * String getSystemUserHomeDirectoryPath()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:05:00 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getSystemUserHomeDirectoryPath()
	{
		return systemUserHomeDirectoryPath;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setJavaDefaultClassPath()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:05:00 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param string
	 */
	private void setJavaDefaultClassPath(String string)
	{
		javaDefaultClassPath = string;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setJavaDefaultPrinterJob()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:05:00 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param string
	 */
	private void setJavaDefaultPrinterJob(String string)
	{
		javaDefaultPrinterJob = string;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setJavaFileEncodingPackage()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:05:00 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param string
	 */
	private void setJavaFileEncodingPackage(String string)
	{
		javaFileEncodingPackage = string;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setJavaHomePath()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:05:00 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param string
	 */
	private void setJavaHomePath(String string)
	{
		javaHomePath = string;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setJavaJDKSpecsVersion()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:05:00 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param string
	 */
	private void setJavaJDKSpecsVersion(String string)
	{
		javaJDKSpecsVersion = string;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setJavaJDKVersion()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:05:00 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param string
	 */
	private void setJavaJDKVersion(String string)
	{
		javaJDKVersion = string;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setJavaLibraryPath()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:05:00 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param string
	 */
	private void setJavaLibraryPath(String string)
	{
		javaLibraryPath = string;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setJavaPreferencesFactory()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:05:00 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param string
	 */
	private void setJavaPreferencesFactory(String string)
	{
		javaPreferencesFactory = string;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setJavaTemporalWorkingDirectoryPath()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:05:00 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param string
	 */
	private void setJavaTemporalWorkingDirectoryPath(String string)
	{
		javaTemporalWorkingDirectoryPath = string;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setSystemArch()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:05:00 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param string
	 */
	private void setSystemArch(String string)
	{
		systemArch = string;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setSystemCurrentOSVersion()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:05:00 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param string
	 */
	private void setSystemCurrentOSVersion(String string)
	{
		systemCurrentOSVersion = string;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setSystemCurrentUserDirectoryPath()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:05:01 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param string
	 */
	private void setSystemCurrentUserDirectoryPath(String string)
	{
		systemCurrentUserDirectoryPath = string;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setSystemCurrentUserName()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:05:01 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param string
	 */
	private void setSystemCurrentUserName(String string)
	{
		systemCurrentUserName = string;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setSystemDefaultFileEncoding()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:05:01 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param string
	 */
	private void setSystemDefaultFileEncoding(String string)
	{
		systemDefaultFileEncoding = string;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setSystemDefaultLanguage()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:05:01 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param string
	 */
	private void setSystemDefaultLanguage(String string)
	{
		systemDefaultLanguage = string;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setSystemLineSeparator()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:05:01 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param string
	 */
	private void setSystemLineSeparator(String string)
	{
		systemLineSeparator = string;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setSystemOSName()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:05:02 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param string
	 */
	private void setSystemOSName(String string)
	{
		systemOSName = string;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setSystemPathSeparator()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:05:02 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param string
	 */
	private void setSystemPathSeparator(String string)
	{
		systemPathSeparator = string;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setSystemSeparator()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:05:02 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param string
	 */
	private void setSystemSeparator(String string)
	{
		systemSeparator = string;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setSystemTimeZone()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:05:02 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param string
	 */
	private void setSystemTimeZone(String string)
	{
		systemTimeZone = string;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setSystemUserCountry()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:05:02 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param string
	 */
	private void setSystemUserCountry(String string)
	{
		systemUserCountry = string;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setSystemUserHomeDirectoryPath()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 22, 2004 11:05:02 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param string
	 */
	private void setSystemUserHomeDirectoryPath(String string)
	{
		systemUserHomeDirectoryPath = string;
	}
	/*
	 * ---------------------------------------------------------------------------
	 * String getCurrentPreferenceNodePath()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 23, 2004 9:34:32 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getCurrentPreferenceNodePath()
	{
		StringBuffer tempPreferenceNodePathString = new StringBuffer(getSystemArch()+"/"+getSystemOSName()+"/"+"SerialPorts");
		return tempPreferenceNodePathString.toString();
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setCurrentPreferenceNodePath()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 23, 2004 9:34:32 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param string
	 */
	public void setCurrentPreferenceNodePath()
	{
		currentPreferenceNodePath = getCurrentPreferenceNodePath();
	}

	/*
	 * ---------------------------------------------------------------------------
	 * String getCurrentBaudRateConfigForPort()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 23, 2004 1:57:30 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getCurrentBaudRateConfigForPort(String portName) throws NoSuchPreferenceException
	{
		/* Antes que nada, existe alguna configuración para el puerto? */
		if(isSerialPortConfigured(portName)){
			if(!getConfigStringForParameter(portName, "Baud Rate").equals(null)){
				currentBaudRateConfigForPort = getConfigStringForParameter(portName, "Baud Rate");
			}else{
				crsiLoggerProxy.crsiDoLogging("Ha Ocurrido un Error al tratar de leer el Config de Baud Rate desde el BackingStrore",3);
				throw new NoSuchPreferenceException("NO existe un config definido para Baud Rate");
			}
			
		}
		return null;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * String getCurrentDataBitsConfigForPort()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 23, 2004 1:57:30 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getCurrentDataBitsConfigForPort(String portName) throws NoSuchPreferenceException
	{
		/* Antes que nada, existe alguna configuración para el puerto? */
		if(isSerialPortConfigured(portName)){
			if(!getConfigStringForParameter(portName, "Data Bits").equals(null)){
				currentBaudRateConfigForPort = getConfigStringForParameter(portName, "Data Bits");
			}else{
				crsiLoggerProxy.crsiDoLogging("Ha Ocurrido un Error al tratar de leer el Config de Data Bits desde el BackingStrore",3);
				throw new NoSuchPreferenceException("NO existe un config definido para Data Bits");
			}
			
		}
		return null;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * String getCurrentFlowControlInConfigForPort()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 23, 2004 1:57:31 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getCurrentFlowControlInConfigForPort(String portName) throws NoSuchPreferenceException
	{
		/* Antes que nada, existe alguna configuración para el puerto? */
		if(isSerialPortConfigured(portName)){
			if(!getConfigStringForParameter(portName, "Flow Control In").equals(null)){
				currentBaudRateConfigForPort = getConfigStringForParameter(portName, "Flow Control In");
			}else{
				crsiLoggerProxy.crsiDoLogging("Ha Ocurrido un Error al tratar de leer el Config de Flow Control In desde el BackingStrore",3);
				throw new NoSuchPreferenceException("NO existe un config definido para Flow Control In");
			}
		}
		return null;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * String getCurrentFlowControlOutConfigForPort()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 23, 2004 1:57:32 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getCurrentFlowControlOutConfigForPort(String portName) throws NoSuchPreferenceException
	{
		/* Antes que nada, existe alguna configuración para el puerto? */
		if(isSerialPortConfigured(portName)){
			if(!getConfigStringForParameter(portName, "Flow Control Out").equals(null)){
				currentBaudRateConfigForPort = getConfigStringForParameter(portName, "Flow Control Out");
			}else{
				crsiLoggerProxy.crsiDoLogging("Ha Ocurrido un Error al tratar de leer el Config de Flow Control Out desde el BackingStrore",3);
				throw new NoSuchPreferenceException("NO existe un config definido para Flow Control Out");
			}
		}
		return null;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * String getCurrentParityConfigForPort()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 23, 2004 1:57:32 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getCurrentParityConfigForPort(String portName) throws NoSuchPreferenceException
	{
		/* Antes que nada, existe alguna configuración para el puerto? */
		if(isSerialPortConfigured(portName)){
			if(!getConfigStringForParameter(portName, "Parity").equals(null)){
				currentBaudRateConfigForPort = getConfigStringForParameter(portName, "Parity");
			}else{
				crsiLoggerProxy.crsiDoLogging("Ha Ocurrido un Error al tratar de leer el Config de Parity desde el BackingStrore",3);
				throw new NoSuchPreferenceException("NO existe un config definido para Parity");
			}
		}
		return null;
	}


	/*
	 * ---------------------------------------------------------------------------
	 * String getCurrentStopBitConfigForPort()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 23, 2004 1:57:32 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getCurrentStopBitConfigForPort(String portName) throws NoSuchPreferenceException
	{
		/* Antes que nada, existe alguna configuración para el puerto? */
		if(isSerialPortConfigured(portName)){
			if(!getConfigStringForParameter(portName, "Stops Bits").equals(null)){
				currentBaudRateConfigForPort = getConfigStringForParameter(portName, "Stops Bits");
			}else{
				crsiLoggerProxy.crsiDoLogging("Ha Ocurrido un Error al tratar de leer el Config de Stops Bits Out desde el BackingStrore",3);
				throw new NoSuchPreferenceException("NO existe un config definido para Stops Bits");
			}
		}
		return null;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setCurrentBaudRateConfigForPort()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 23, 2004 1:57:32 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param string
	 */
	public void setCurrentBaudRateConfigForPort(String newBaudRate, String portName)
	{
		setConfigStringForParameter(portName,"Baud Rate", newBaudRate);
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setCurrentDataBitsConfigForPort()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 23, 2004 1:57:33 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param string
	 */
	public void setCurrentDataBitsConfigForPort(String newDataBits, String portName)
	{
		setConfigStringForParameter(portName, "Data Bits", newDataBits);
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setCurrentFlowControlInConfigForPort()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 23, 2004 1:57:33 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param string
	 */
	public void setCurrentFlowControlInConfigForPort(String newFlowControlIn, String portName)
	{
		setConfigStringForParameter(portName, "Flow Control In", newFlowControlIn);
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setCurrentFlowControlOutConfigForPort()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 23, 2004 1:57:33 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param string
	 */
	public void setCurrentFlowControlOutConfigForPort(String newFlowControlOut, String portName)
	{
		setConfigStringForParameter(portName, "Flow Control Out", newFlowControlOut);
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setCurrentParityConfigForPort()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 23, 2004 1:57:33 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param string
	 */
	public void setCurrentParityConfigForPort(String newParity, String portName)
	{
		setConfigStringForParameter(portName,"Parity", newParity);
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setCurrentPortName()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 23, 2004 1:57:33 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param string
	 */
	public void setCurrentPortName(String string)
	{
		currentPortName = string;
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setCurrentStopBitConfigForPort()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 23, 2004 1:57:33 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param string
	 */
	public void setCurrentStopBitConfigForPort(String newStopBits,String portName)
	{
		setConfigStringForParameter(portName, "Stop Bits", newStopBits);
	}

}
	
