/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.colascr.transferencias.excepciones
 * Programa   : BaseDeDatosExcepcion.java
 * Creado por : Ileana Rojas, Gabriel Martinelli
 * Creado en  : 21/02/2005 02:43:59 PM
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
package com.beco.colascr.transferencias.excepciones;

/** 
 * Descripción: 
 * 		Esta clase maneja todas las excepciones provenientes del manejo 
 * de acceso/conexión a la base de datos. Fallas en la conexion, errores 
 * en las sentencias Sql, etc.
 */

public class BaseDeDatosExcepcion extends ExcepcionSinc {

	/**
	 * Constructor para BaseDeDatosExcepcion.
	 * @param mensaje - Mensaje que describe la excepcion presentada
	 */
	public BaseDeDatosExcepcion(String mensaje){
		super(mensaje);
	}
	
	/**
	 * Constructor para BaseDeDatosExcepcion.
	 * @param mensaje - Mensaje que describe la excepcion presentada
	 * @param ex - Excepcion que causa la falla
	 */
	public BaseDeDatosExcepcion(String mensaje, Exception ex) {
		super(mensaje, ex);
	}
}