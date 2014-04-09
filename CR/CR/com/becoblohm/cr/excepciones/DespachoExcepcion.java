/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.excepciones
 * Programa   : DespachoExcepcion.java
 * Creado por : irojas
 * Creado en  : 17-may-04 11:00:00
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
 * 		Esta clase maneja la excepcion que se genera al crear un despacho.
 * 
 */
public class DespachoExcepcion extends ExcepcionCr {

	/**
	 * Constructor para DespachoExcepcion.
	 * @param mensaje Mensaje que describe el error presentado.
	 */
	public DespachoExcepcion(String mensaje) {
		super(mensaje);
	}
	
	/**
	 * Constructor para DespachoExcepcion.
	 * @param mensaje Mensaje que describe el error presentado.
	 * @param ex Excepcion qe causo el error.
	 */
	public DespachoExcepcion(String mensaje, Exception ex) {
		super(mensaje, ex);
	}
	
}