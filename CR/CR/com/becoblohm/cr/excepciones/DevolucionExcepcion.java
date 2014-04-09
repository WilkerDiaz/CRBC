/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.excepciones
 * Programa   : DevolucionExcepcion.java
 * Creado por : gmartinelli
 * Creado en  : 29-mar-04 10:03:15
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
 * 		Esta clase maneja la excepcion que se genera al crear una devolucion.
 * 
 */
public class DevolucionExcepcion extends ExcepcionCr {

	/**
	 * Constructor para DevolucionExcepcion.
	 * @param mensaje Mensaje que describe el error presentado.
	 */
	public DevolucionExcepcion(String mensaje) {
		super(mensaje);
	}
	
	/**
	 * Constructor para DevolucionExcepcion.
	 * @param mensaje Mensaje que describe el error presentado.
	 * @param ex Excepcion qe causo el error.
	 */
	public DevolucionExcepcion(String mensaje, Exception ex) {
		super(mensaje, ex);
	}
	
}