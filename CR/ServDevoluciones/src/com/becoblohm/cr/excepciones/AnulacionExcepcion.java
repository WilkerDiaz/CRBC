/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.excepciones
 * Programa   : AnulacionExcepcion.java
 * Creado por : gmartinelli
 * Creado en  : 31-mar-04 10:45:15
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
 * 		Esta clase maneja la excepcion que se genera al crear una anulacion.
 * 
 */
public class AnulacionExcepcion extends ExcepcionCr {

	/**
	 * Constructor para AnulacionExcepcion.
	 * @param mensaje Mensaje que describe el error presentado.
	 */
	public AnulacionExcepcion(String mensaje) {
		super(mensaje);
	}
	
	/**
	 * Constructor para AnulacionExcepcion.
	 * @param mensaje Mensaje que describe el error presentado.
	 * @param ex Excepcion qe causo el error.
	 */
	public AnulacionExcepcion(String mensaje, Exception ex) {
		super(mensaje, ex);
	}
	
}