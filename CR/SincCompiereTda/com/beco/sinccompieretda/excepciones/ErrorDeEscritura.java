/**
 * =============================================================================
 * Proyecto   : AplicacionesPDAServidorDeTienda
 * Paquete    : com.beco.sistemas.aplicacionespda.servidordetienda.Excepciones
 * Programa   : ErrorDeEscritura.java
 * Creado por : mgrillo
 * Creado en  : 28/05/2009 - 14:10:12
 *
 * (c) CENTROBECO, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 
 * Fecha       : 
 * Analista    : 
 * Descripción : 
 * =============================================================================
 */

package com.beco.sinccompieretda.excepciones;

public class ErrorDeEscritura extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2704946000299012896L;
	
	public ErrorDeEscritura(){
		super("***No se pudo leer correctamente***");
	}
	
	public ErrorDeEscritura(String err){
		super(err);
	}
}
