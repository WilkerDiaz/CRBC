/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.excepciones
 * Programa   : AutorizacionExcepcion.java
 * Creado por : irojas
 * Creado en  : 05-nov-03 14:10:17
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
 * 			Maneja las excepciones provenientes de los errores de autorizaciones.
 * Funciones que requieren autorizacion, errores en los codigos de los autorizantes.
 * 
 */
public class AutorizacionExcepcion extends ExcepcionCr {

	/**
	 * Constructor para AutorizacionExcepcion.
	 * @param mensaje Mensaje que describe el error presentado.
	 */
	public AutorizacionExcepcion(String mensaje) {
		super(mensaje);
	}
	
	/**
	 * Constructor para AutorizacionExcepcion.
	 * @param mensaje Mensaje que describe el error presentado.
	 * @param ex Excepcion qe causo el error.
	 */
	public AutorizacionExcepcion(String mensaje, Exception ex) {
		super(mensaje, ex);
	}

}
