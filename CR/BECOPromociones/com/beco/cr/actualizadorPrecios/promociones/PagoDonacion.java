/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.cr.actualizadorPrecios.promociones.PagoDonacion
 * Programa   : PagoDonacion.java
 * Creado por : jgraterol, aavila
 * Creado en  : 10/06/2008 10:18:05 AM
 *
 * (c) CENTRO BECO, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * ----------------------------------------------------------------------------- 
 * =============================================================================
 * */
package com.beco.cr.actualizadorPrecios.promociones;

import com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago;

public class PagoDonacion {

	private FormaDePago formaPago;
	//private DonacionRegistrada donacionRegistrada;
	private double monto;
	
	public PagoDonacion(FormaDePago formaPago, double monto) {
		this.formaPago = formaPago;
		this.monto = monto;
	}
	
	/**
	 * @return el formaPago
	 */
	public FormaDePago getFormaPago() {
		return formaPago;
	}
	/**
	 * @param formaPago el formaPago a establecer
	 */
	public void setFormaPago(FormaDePago formaPago) {
		this.formaPago = formaPago;
	}
	/**
	 * @return el monto
	 */
	public double getMonto() {
		return monto;
	}
	/**
	 * @param monto el monto a establecer
	 */
	public void setMonto(double monto) {
		this.monto = monto;
	}
	
	
	public Object clone(){
		return new PagoDonacion((FormaDePago)this.formaPago.clone(),this.monto);
	}
}
