/**
 * =============================================================================
 * Proyecto   : cr
 * Paquete    : com.becoblohm.cr.excepciones
 * Programa   : CreacionException.java
 * Creado por : Marcos Grillo
 * Creado en  : 15/04/2010 - 10:12:59
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

public class ErrorEnCreacion extends Exception
 {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ErrorEnCreacion(){
		super("***No se pudo crear la clase correctamente***");
	}
	
	public ErrorEnCreacion(String err){
		super(err);
	}
}
