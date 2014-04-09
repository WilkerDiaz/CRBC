/**
 * =============================================================================
 * Proyecto   : CRBECOEPA
 * Paquete    : com.becoblohm.cr.excepciones
 * Programa   : LineaExcepcion.java
 * Creado por : irojas
 * Creado en  : 11/03/2004 13:28 PM
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
 * 		Esta clase corresponde a excepciones que corresponden cuando la caja se
 * 	    encuentra fuera de línea y se deben ejecutar acciones especiales en la interfaz 
 * 	    cuando estas son lanzadas.
 */

public class LineaExcepcion extends ExcepcionCr {

	/**
	 * Constructor para LineaExcepcion.
	 * @param mensaje
	 */
	public LineaExcepcion(String mensaje){
		super(mensaje);
	}

	/**
	 * Constructor para LineaExcepcion.
	 * @param mensaje Mensaje que describe el error presentado.
	 * @param ex Excepcion qe causó el error.
	 */
	public LineaExcepcion(String mensaje, Exception ex) {
		super(mensaje, ex);
	}

}