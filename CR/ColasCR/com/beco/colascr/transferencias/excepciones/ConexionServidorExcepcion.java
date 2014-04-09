/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.colascr.transferencias.excepciones
 * Programa   : ConexionServidorExcepcion.java
 * Creado por : Ileana Rojas, Gabriel Martinelli
 * Creado en  : 21/02/2005 03:01:59 PM
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

public class ConexionServidorExcepcion extends ConexionExcepcion {

	/**
	 * @param mensaje
	 */
	public ConexionServidorExcepcion(String mensaje) {
		super(mensaje, null);
	}

	/**
	 * @param mensaje
	 * @param ex
	 */
	public ConexionServidorExcepcion(String mensaje, Exception ex) {
		super(mensaje, ex);
		/*Sesion.setCajaEnLinea(false);
		Conexiones.setConectadoServidor(false);*/
	}

}
