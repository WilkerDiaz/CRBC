/*
 * $Id: CRSILoggerProxy.java,v 1.3 2005/03/09 20:50:03 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CRSerialInterface
 * Paquete		: com.epa.crserialinterface
 * Programa		: CRSILogger.java
 * Creado por	: vmedina
 * Creado en 	: Apr 21, 2004 10:21:57 AM
 * (C) Copyright 2004 SuperFerretería EPA C.A. Todos los Derechos Reservados
 * 
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: CRSILoggerProxy.java,v $
 * Revision 1.3  2005/03/09 20:50:03  programa8
 * CRSerialInterface al 09/03/2005
 *
 * Revision 1.1  2004/07/05 18:42:35  vmedina
 * *** empty log message ***
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

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * <p>Indicar aquí, en una línea que hace la clase</p>
 * <p>Agregar parrafos que describen como se usa la Clase y un resumen de como 
 * funciona.</p>
 * @author $Author: programa8 $  
 * @version $Revision: 1.3 $<br>$Date: 2005/03/09 20:50:03 $
 * <!-- A continuación indicar referencia a otras páginas o clases parecidas -->
 * <!-- o relacionados, pundiendo indicar inclusive los métodos relacionados -->
 * <!-- por ejemplo: @see     <a href="package-summary.html#charenc">Character encodings</a> -->
 * <!-- por ejemplo: @see     java.lang.Object 								 -->
 * <!-- por ejemplo: @see     java.lang.Object#toNewString() 				 -->
 * @see     {RutaObjeto}#NombreMetodo()
 * @since   <!-- Indique desde que versión del Proyecto existe esta clase 	 -->
 */
public class CRSILoggerProxy
{
	private boolean assertDesiredEnabled = true;
	private static Logger crsiLoggerProxy;
	
	/*
	 * ---------------------------------------------------------------------------
	 * CRSILoggerProxy()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 21, 2004 10:44:44 AM
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
	public CRSILoggerProxy(String className)
	{
		crsiLoggerInit(className);
	}
	
	
	/*
	 * ---------------------------------------------------------------------------
	 * void crsiLoggerInit()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 21, 2004 10:45:36 AM
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
	private void crsiLoggerInit(String className)
	{
	
			crsiLoggerProxy = Logger.getLogger(className);
			BasicConfigurator.configure();
			crsiLoggerProxy.info(
				"Servicio de Logeo iniciado para la clase: "
					+ className);
			/*
			 * Verificamos si tenemos asserts prendidos, si esto es asi
			 * el logeo lo hacemos mas verboso
			 */
			try
			{
				assert !this.getClass().desiredAssertionStatus();
				crsiLoggerProxy.warn(
					"Minimo nivel de logeo activo, solo logeare ERROR's y WARN's");
			}
			catch (AssertionError as)
			{
				assertDesiredEnabled = false;
				crsiLoggerProxy.warn(
					"ASSERTS estan ON - Logeando al máximo nivel, logearé INFO's, DEBUGS's, WARN's y ERROR's");
			}
		
	}
	
	
	/*
	 * ---------------------------------------------------------------------------
	 * void crsiDoLogging()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 21, 2004 10:46:16 AM
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
	 * @param message
	 * @param level
	 */
	public void crsiDoLogging(String message, int level)
	{
		try
		{
			/*
			 * Probamos a ver si tenemos asserts on
			 */
			assert assertDesiredEnabled;
			/*
			 * Logeamos el mínimo, los asserts estan off, 
			 */
			if (level == 3)
			{
				/* Logeamos un error! */
				crsiLoggerProxy.error(message);
			}
			if (level == 2)
			{
				/* Logeamos un warning */
				crsiLoggerProxy.warn(message);
			}
		}
		catch (AssertionError e)
		{
			/*
			 * Logeamos todo, logeo al maximo, asserts ON!
			 */
			if (level == 0)
			{
				/* Logeamos un info */
				crsiLoggerProxy.info(message);
			}
			if (level == 1)
			{
				/* legeamos un debug */
				crsiLoggerProxy.debug(message);
			}
			if (level == 2)
			{
				/* logeamos un warning */
				crsiLoggerProxy.warn(message);
			}
			if (level == 3)
			{
				/* logeamos un error */
				crsiLoggerProxy.error(message);
			}
		}
	}
}
