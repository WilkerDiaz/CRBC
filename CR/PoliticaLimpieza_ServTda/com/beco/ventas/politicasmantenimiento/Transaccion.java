/**
 * =============================================================================
 * Proyecto   : PoliticaMant_ServCR
 * Paquete    : com.beco.ventas.politicasmantenimiento
 * Programa   : Transaccion.java
 * Creado por : irojas
 * Creado en  : 22/06/2009 - 06:45:51 PM
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

package com.beco.ventas.politicasmantenimiento;

import java.util.Date;

/**
 *	Esta clase refiere a los objetos que representan Transaccion. 
 */
public class Transaccion {
	private int numTienda;
	private Date fecha;
	private int numCaja;
	private int numTransaccion;
	private int numCajaInicia;
	
	/**
	 * @param numTienda
	 * @param fecha
	 * @param numCaja
	 * @param numTransaccion
	 */
	public Transaccion(int numTienda, Date fecha, int numCaja, int numTransaccion, int numCajaInicia) {
		this.numTienda = numTienda;
		this.fecha = fecha;
		this.numCaja = numCaja;
		this.numTransaccion = numTransaccion;
		this.numCajaInicia = numCajaInicia;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getNumCaja() {
		return numCaja;
	}

	public void setNumCaja(int numCaja) {
		this.numCaja = numCaja;
	}

	public int getNumTienda() {
		return numTienda;
	}

	public void setNumTienda(int numTienda) {
		this.numTienda = numTienda;
	}

	public int getNumTransaccion() {
		return numTransaccion;
	}

	public void setNumTransaccion(int numTransaccion) {
		this.numTransaccion = numTransaccion;
	}

	public int getNumCajaInicia() {
		return numCajaInicia;
	}

	public void setNumCajaInicia(int numCajaInicia) {
		this.numCajaInicia = numCajaInicia;
	}
	
}
