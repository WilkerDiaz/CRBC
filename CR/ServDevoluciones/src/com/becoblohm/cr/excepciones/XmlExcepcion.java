/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.excepciones
 * Programa   : XmlExcepcion.java
 * Creado por : gmartinelli - Gabriel Martinelli
 * Creado en  : 02/03/2004 11:10 AM
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
package com.becoblohm.cr.excepciones;

/** 
 * Descripción: 
 * 		Esta clase maneja todas las excepciones provenientes del manejo 
 * de acceso (Lectura y Escritura) a los archivos Xml utilizados en la aplicacion
 */

public class XmlExcepcion extends ExcepcionCr {

	/**
	 * Constructor para XmlExcepcion.
	 * @param mensaje - Mensaje que describe la excepcion presentada
	 */
	public XmlExcepcion(String mensaje){
		super(mensaje);
	}
	
	/**
	 * Constructor para XmlExcepcion.
	 * @param mensaje - Mensaje que describe la excepcion presentada
	 * @param ex - Excepcion que causa la falla
	 */
	public XmlExcepcion(String mensaje, Exception ex) {
		super(mensaje, ex);
	}
}