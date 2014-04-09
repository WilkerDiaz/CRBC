/**
 * =============================================================================
 * Proyecto   : cr
 * Paquete    : com.becoblohm.cr.excepciones
 * Programa   : ErrorDeEscritura.java
 * Creado por : Marcos Grillo
 * Creado en  : 15/04/2010 - 14:10:12
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

public class ErrorDeEscritura extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2704946000299012896L;
	
	public ErrorDeEscritura(){
		super("***No se pudo leer correctamente***");
	}
	
	public ErrorDeEscritura(String err){
		super(err);
	}
}
