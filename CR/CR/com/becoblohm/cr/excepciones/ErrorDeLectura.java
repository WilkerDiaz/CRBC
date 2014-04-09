/**
 * =============================================================================
 * Proyecto   : cr
 * Paquete    : com.becoblohm.cr.excepciones
 * Programa   : ErrorDeLectura.java
 * Creado por : Marcos Grillo
 * Creado en  : 15/04/2010 - 14:02:16
 *
 * (c) CENTROBECO, C.A.
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

public class ErrorDeLectura extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1814625240953972979L;
	
	public ErrorDeLectura()
	{
		super("No se pudo leer correctamente el objeto");
	}
	public ErrorDeLectura(String err)
	{
		super(err);
	}
}
