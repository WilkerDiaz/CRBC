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
 * Versi�n     : 
 * Fecha       : 
 * Analista    : 
 * Descripci�n : 
 * =============================================================================
 */
package com.becoblohm.cr.excepciones;

/** 
 * Descripci�n: 
 * 		Esta clase corresponde a excepciones que corresponden cuando la caja se
 * 	    encuentra fuera de l�nea y se deben ejecutar acciones especiales en la interfaz 
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
	 * @param ex Excepcion qe caus� el error.
	 */
	public LineaExcepcion(String mensaje, Exception ex) {
		super(mensaje, ex);
	}

}