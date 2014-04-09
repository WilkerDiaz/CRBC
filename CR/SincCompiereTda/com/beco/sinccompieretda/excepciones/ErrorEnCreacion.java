/**
 * =============================================================================
 * Proyecto   : AplicacionesPDAServidorDeTienda
 * Paquete    : com.beco.sistemas.aplicacionespda.servidordetienda.red
 * Programa   : CreacionException.java
 * Creado por : mgrillo
 * Creado en  : 28/05/2009 - 10:12:59
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

public class ErrorEnCreacion extends Exception
 {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ErrorEnCreacion(){
		super("***No se pudo crear la clase correctamente***");
	}
	
	public ErrorEnCreacion(String err){
		super(err);
	}
}
