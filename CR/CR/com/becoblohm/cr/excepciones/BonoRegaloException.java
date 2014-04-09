/**
 * =============================================================================
 * Proyecto   : CajaRegistradora
 * Paquete    : com.becoblohm.cr.excepciones
 * Programa   : BonoRegaloException.java
 * Creado por : jgraterol
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
public class BonoRegaloException extends ExcepcionCr {

	/**
	 * Constructor para PagoExcepcion.
	 * @param mensaje Mensaje que describe el error presentado.
	 */
	public BonoRegaloException(String mensaje) {
		super(mensaje);
	}
	
	/**
	 * Constructor para PagoExcepcion.
	 * @param mensaje Mensaje que describe el error presentado.
	 * @param ex Excepcion qe causo el error.
	 */
	public BonoRegaloException(String mensaje, Exception ex) {
		super(mensaje, ex);
	}
	
}
