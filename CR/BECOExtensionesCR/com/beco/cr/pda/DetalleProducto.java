/*
 * ===========================================================================
 * Material Propiedad CentroBeco C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.beco.cr.pda
 * Programa		: BECOPDA.java
 * Creado por	: varios autores
 * Creado en 	: 11-08-2009 
 * (C) Copyright 2005 CentroBeco C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * ===========================================================================
 */
package com.beco.cr.pda;

/**
 * 
 *	Esta clase refiere a los objetos que representan DetalleProducto.
 */ 
public class DetalleProducto {

	private String codProducto = "";
	private String descProducto = "";
	private double precioRegular = 0.0;
	private double precioFinal = 0.0;
	private double cantidad = 0.0;
	private double montoVenta = 0.0;
	private String condVenta = "";
	
	public DetalleProducto(String codigo, double cantidad){
		this.codProducto = codigo;
		this.cantidad = cantidad;
	}
	
	/**
	 * 
	 * @param codigo
	 * @param descripcion
	 * @param precioReg
	 * @param precioFin
	 * @param cantidad
	 * @param montoVenta
	 * @param condicion
	 */
	public DetalleProducto(String codigo, String descripcion, double precioReg, double precioFin, double cantidad, double monto, String condicion){
		this.codProducto = codigo;
		this.descProducto = descripcion;
		this.precioRegular = precioReg;
		this.precioFinal = precioFin;
		this.cantidad = cantidad;
		this.montoVenta = monto;
		this.condVenta = condicion;
	}

	/**
	 * @return el cantidad
	 */
	public double getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad el cantidad a establecer
	 */
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return el codProducto
	 */
	public String getCodProducto() {
		return codProducto;
	}

	/**
	 * @param codProducto el codProducto a establecer
	 */
	public void setCodProducto(String codProducto) {
		this.codProducto = codProducto;
	}

	/**
	 * @return el condVenta
	 */
	public String getCondVenta() {
		return condVenta;
	}

	/**
	 * @param condVenta el condVenta a establecer
	 */
	public void setCondVenta(String condVenta) {
		this.condVenta = condVenta;
	}

	/**
	 * @return el descProducto
	 */
	public String getDescProducto() {
		return descProducto;
	}

	/**
	 * @param descProducto el descProducto a establecer
	 */
	public void setDescProducto(String descProducto) {
		this.descProducto = descProducto;
	}

	/**
	 * @return el montoVenta
	 */
	public double getMontoVenta() {
		return montoVenta;
	}

	/**
	 * @param montoVenta el montoVenta a establecer
	 */
	public void setMontoVenta(double monto) {
		this.montoVenta = monto;
	}

	/**
	 * @return el precioFinal
	 */
	public double getPrecioFinal() {
		return precioFinal;
	}

	/**
	 * @param precioFinal el precioFinal a establecer
	 */
	public void setPrecioFinal(double precioFinal) {
		this.precioFinal = precioFinal;
	}

	/**
	 * @return el precioRegular
	 */
	public double getPrecioRegular() {
		return precioRegular;
	}

	/**
	 * @param precioRegular el precioRegular a establecer
	 */
	public void setPrecioRegular(double precioRegular) {
		this.precioRegular = precioRegular;
	}

	
}
