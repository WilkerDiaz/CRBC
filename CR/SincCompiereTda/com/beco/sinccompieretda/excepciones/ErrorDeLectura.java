/**
 * =============================================================================
 * Proyecto   : AplicacionesPDAServidorDeTienda
 * Paquete    : com.beco.sistemas.aplicacionespda.servidordetienda.Excepciones
 * Programa   : ErrorDeLectura.java
 * Creado por : mgrillo
 * Creado en  : 28/05/2009 - 14:02:16
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

public class ErrorDeLectura extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1814625240953972979L;
	
	public ErrorDeLectura()
	{
		super("No se pudo leer correctamente el objeto");
	}
	public ErrorDeLectura(String err)
	{
		super(err);
	}
}
