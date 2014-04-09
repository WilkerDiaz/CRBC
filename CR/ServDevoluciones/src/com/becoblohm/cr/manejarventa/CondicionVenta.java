/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.transaccion
 * Programa   : CondicionVenta.java
 * Creado por : jgraterol
 * Creado en  : 30/07/2008 08:44:05 am
 *
 * (c) CENTROBECO, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * ----------------------------------------------------------------------------- 
 * Versión     : 1.0 
 * Fecha       : 30/07/2008 08:44:05 am
 * Analista    : jgraterol
 * Descripción : Se crea esta clase para solucionar problema con la cantidad de 
 * 				condiciones de venta. En lugar de crear una condicion de venta 
 * 				por cada una de las combinaciones posibles entre las condiciones
 * 				ya existente, se creará un vector de los
 * 				identificadores de las condiciones de venta simples
 * 
 * 				Es necesario agregarle el identificador de la promoción aplicada
 * 				para el registro de auditoría de las promociones
 * ============================================================================= 
 */

package com.becoblohm.cr.manejarventa;

import java.io.Serializable;

public class CondicionVenta implements Serializable {

	//Identificador de una condicion de venta
	private String condicion;
	//Codigo de la promocion que genero esta condicion de venta
	private int codPromocion;
	
	private double porcentajeDescuento;
	private String nombrePromocion;
	
	private double montoDctoCombo = 0.0;
	
	private int prioridadPromocion;
	
	/**
	 * Este constructor no recibe la promocion para el caso de 
	 * empaque y empleado
	 * @param condiciones
	 */
	public CondicionVenta(String condicion) {
		this.condicion = condicion;
	}

	/**
	 * @param condiciones
	 * @param codPromocion
	 */
	public CondicionVenta(String condicion, int codPromocion, double porcentajeDescuento, String nombrePromocion, int prioridadPromocion) {
		this.condicion = condicion;
		this.codPromocion = codPromocion;
		this.nombrePromocion = nombrePromocion;
		this.porcentajeDescuento = porcentajeDescuento;
		this.prioridadPromocion = prioridadPromocion;
	}

	/**
	 * @return el codPromocion
	 */
	public int getCodPromocion() {
		return codPromocion;
	}

	/**
	 * @param codPromocion el codPromocion a establecer
	 */
	public void setCodPromocion(int codPromocion) {
		this.codPromocion = codPromocion;
	}

	/**
	 * @return el condicion
	 */
	public String getCondicion() {
		return condicion;
	}

	/**
	 * @param condicion el condicion a establecer
	 */
	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}
	
	public Object clone(){
		return new CondicionVenta(this.condicion,this.codPromocion, this.porcentajeDescuento, this.nombrePromocion, this.prioridadPromocion);
	}

	/**
	 * @return el nombrePromocion
	 */
	public String getNombrePromocion() {
		return nombrePromocion;
	}

	/**
	 * @param nombrePromocion el nombrePromocion a establecer
	 */
	public void setNombrePromocion(String nombrePromocion) {
		this.nombrePromocion = nombrePromocion;
	}

	/**
	 * @return el porcentajeDescuento
	 */
	public double getPorcentajeDescuento() {
		return porcentajeDescuento;
	}

	/**
	 * @param porcentajeDescuento el porcentajeDescuento a establecer
	 */
	public void setPorcentajeDescuento(double porcentajeDescuento) {
		this.porcentajeDescuento = porcentajeDescuento;
	}

	public double getMontoDctoCombo() {
		return montoDctoCombo;
	}

	public void setMontoDctoCombo(double montoDctoCombo) {
		this.montoDctoCombo = montoDctoCombo;
	}

	public int getPrioridadPromocion() {
		return prioridadPromocion;
	}

	public void setPrioridadPromocion(int prioridadPromocion) {
		this.prioridadPromocion = prioridadPromocion;
	}
}
