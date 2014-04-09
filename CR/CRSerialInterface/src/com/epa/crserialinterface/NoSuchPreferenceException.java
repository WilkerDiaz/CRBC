/*
 * $Id: NoSuchPreferenceException.java,v 1.2 2005/03/09 20:50:04 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CRSerialInterface
 * Paquete		: com.epa.crserialinterface
 * Programa		: NoSuchPreferenceException.java
 * Creado por	: vmedina
 * Creado en 	: Apr 23, 2004 2:00:16 PM
 * (C) Copyright 2004 SuperFerretería EPA C.A. Todos los Derechos Reservados
 * 
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: NoSuchPreferenceException.java,v $
 * Revision 1.2  2005/03/09 20:50:04  programa8
 * CRSerialInterface al 09/03/2005
 *
 * Revision 1.1  2004/07/05 18:42:35  vmedina
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

/**
 * <p>Indicar aquí, en una línea que hace la clase</p>
 * <p>Agregar parrafos que describen como se usa la Clase y un resumen de como 
 * funciona.</p>
 * @author $Author: programa8 $  
 * @version $Revision: 1.2 $<br>$Date: 2005/03/09 20:50:04 $
 * <!-- A continuación indicar referencia a otras páginas o clases parecidas -->
 * <!-- o relacionados, pundiendo indicar inclusive los métodos relacionados -->
 * <!-- por ejemplo: @see     <a href="package-summary.html#charenc">Character encodings</a> -->
 * <!-- por ejemplo: @see     java.lang.Object 								 -->
 * <!-- por ejemplo: @see     java.lang.Object#toNewString() 				 -->
 * @see     {RutaObjeto}#NombreMetodo()
 * @since   <!-- Indique desde que versión del Proyecto existe esta clase 	 -->
 */
public class NoSuchPreferenceException extends Exception
{
	public NoSuchPreferenceException(String str) {
			super(str);
		}
		public NoSuchPreferenceException() {
			super();
		}
}
