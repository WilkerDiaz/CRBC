/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.excepciones
 * Programa   : ExcepcionLR.java
 * Creado por : rabreu
 * Basado en  : AbonoExcepcion.java
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
public class ExcepcionLR extends ExcepcionCr {

	/**
	 * Constructor para ExcepcionLR.
	 * @param mensaje Mensaje que describe el error presentado.
	 */
	public ExcepcionLR(String mensaje) {
		super(mensaje);
	}
	
	/**
	 * Constructor para ExcepcionLR.
	 * @param mensaje Mensaje que describe el error presentado.
	 * @param ex Excepcion qe causo el error.
	 */
	public ExcepcionLR(String mensaje, Exception ex) {
		super(mensaje, ex);
	}
	
}