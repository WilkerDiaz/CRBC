/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.colascr.transferencias
 * Programa   : InitSincronizador.java
 * Creado por : Ileana Rojas, Gabriel Martinelli
 * Creado en  : 21/02/2005 02:05:59 PM
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
package com.beco.colascr.transferencias;

/**
 * Descripción:
 * 
 */
public class InitSincronizador extends Thread {
	
	/**
	 * Method bootScreen.
	 */
	public static void bootScreen() {
			new ServidorCR();
			ServidorCR.sincronizador.iniciar();
	}

	public void run() {
		bootScreen();
	}
}