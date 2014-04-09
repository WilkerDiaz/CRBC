/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.excepciones
 * Programa   : AbonoExcepcion.java
 * Creado por : gmartinelli
 * Creado en  : 05-Abr-04 09:30
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
 * 		Esta clase maneja la excepcion que se con los abonos de los apartados.
 * 
 */
public class AbonoExcepcion extends ExcepcionCr {

	/**
	 * Constructor para AbonoExcepcion.
	 * @param mensaje Mensaje que describe el error presentado.
	 */
	public AbonoExcepcion(String mensaje) {
		super(mensaje);
	}
	
	/**
	 * Constructor para AbonoExcepcion.
	 * @param mensaje Mensaje que describe el error presentado.
	 * @param ex Excepcion qe causo el error.
	 */
	public AbonoExcepcion(String mensaje, Exception ex) {
		super(mensaje, ex);
	}
	
}