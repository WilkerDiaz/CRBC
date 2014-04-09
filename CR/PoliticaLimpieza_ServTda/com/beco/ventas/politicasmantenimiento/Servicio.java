/**
 * =============================================================================
 * Proyecto   : PoliticaMant_ServCR
 * Paquete    : com.beco.ventas.politicasmantenimiento
 * Programa   : Servicio.java
 * Creado por : irojas
 * Creado en  : 23/06/2009 - 09:01:25 PM
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
 *	Esta clase refiere a los objetos que representan Servicio. 
 */
public class Servicio {
	private int numTienda;
	private String codTipoServicio;
	private int numServicio;
	private Date fecha;
	/**
	 * @param numTienda
	 * @param codTipoServicio
	 * @param numServicio
	 * @param fecha
	 */
	public Servicio(int numTienda, String codTipoServicio, int numServicio, Date fecha) {
		super();
		this.numTienda = numTienda;
		this.codTipoServicio = codTipoServicio;
		this.numServicio = numServicio;
		this.fecha = fecha;
	}
	public String getCodTipoServicio() {
		return codTipoServicio;
	}
	public void setCodTipoServicio(String codTipoServicio) {
		this.codTipoServicio = codTipoServicio;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public int getNumServicio() {
		return numServicio;
	}
	public void setNumServicio(int numServicio) {
		this.numServicio = numServicio;
	}
	public int getNumTienda() {
		return numTienda;
	}
	public void setNumTienda(int numTienda) {
		this.numTienda = numTienda;
	}

	

}
