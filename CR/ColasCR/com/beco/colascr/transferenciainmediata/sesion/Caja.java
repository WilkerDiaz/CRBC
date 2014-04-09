/**
 * =============================================================================
 * Proyecto   : TransferenciaInmediataProdsCR
 * Paquete    : com.beco.colascr.transferenciainmediata.sesion
 * Programa   : Caja.java
 * Creado por : gmartinelli
 * Creado en  : 28-jun-05 9:26:05
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versi�n     : 1.1
 * Fecha       : 28-jun-05 9:26:05
 * Analista    : gmartinelli
 * Descripci�n : Implementaci�n inicial.
 * =============================================================================
 */
package com.beco.colascr.transferenciainmediata.sesion; 

/**
 * Descripci�n:
 * 
 */

public class Caja {
	
	private int numeroCaja = 0;
	private String ipCaja = "";
	/**
	 * Constructor para Caja.java
	 *
	 * 
	 */
	public Caja(int numero, String ip) {
		this.numeroCaja = numero;
		this.ipCaja = ip;
	}

	/**
	 * M�todo getIpCaja
	 * 
	 * @return
	 * String
	 */
	public String getIpCaja() {
		return ipCaja;
	}

	/**
	 * M�todo getNumeroCaja
	 * 
	 * @return
	 * int
	 */
	public int getNumeroCaja() {
		return numeroCaja;
	}

}
