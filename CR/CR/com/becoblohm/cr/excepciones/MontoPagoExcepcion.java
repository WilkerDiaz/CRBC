/**
 * =============================================================================
 * Proyecto   : CajaRegistradora
 * Paquete    : com.becoblohm.cr.excepciones
 * Programa   : PagoExcepcion.java
 * Creado por : gmartinelli
 * Creado en  : 30-oct-03 10:20:28
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
 * 		Esta clase maneja todas las excepciones provenientes por problemas con los pagos.
 * 
 */
public class MontoPagoExcepcion extends ExcepcionCr {

	/**
	 * Constructor para PagoExcepcion.
	 * @param mensaje Mensaje que describe el error presentado.
	 */
	public MontoPagoExcepcion(String mensaje) {
		super(mensaje);
	}
	
	/**
	 * Constructor para PagoExcepcion.
	 * @param mensaje Mensaje que describe el error presentado.
	 * @param ex Excepcion qe causo el error.
	 */
	public MontoPagoExcepcion(String mensaje, Exception ex) {
		super(mensaje, ex);
	}
	
}