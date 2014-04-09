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
 * Versi�n     : 
 * Fecha       : 
 * Analista    : 
 * Descripci�n : 
 * =============================================================================
 */
package com.becoblohm.cr.excepciones;

/** 
 * Descripci�n: 
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