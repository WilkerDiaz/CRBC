package com.beco.colascr.transferenciainmediata.entidades;

public class CondicionPromocion {
	
	private int codPromocion;
	private int lineaCondicion;
	private int orden;
	private String regactualizado;

	public CondicionPromocion(int codPromocion, int lineaCondicion, int orden, String regactualizado) {
		// TODO Apéndice de constructor generado automáticamente
		this.codPromocion = codPromocion;
		this.lineaCondicion = lineaCondicion;
		this.orden = orden;
		this.regactualizado = regactualizado;
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
	 * @return el lineaCondicion
	 */
	public int getLineaCondicion() {
		return lineaCondicion;
	}

	/**
	 * @param lineaCondicion el lineaCondicion a establecer
	 */
	public void setLineaCondicion(int lineaCondicion) {
		this.lineaCondicion = lineaCondicion;
	}

	/**
	 * @return el orden
	 */
	public int getOrden() {
		return orden;
	}

	/**
	 * @param orden el orden a establecer
	 */
	public void setOrden(int orden) {
		this.orden = orden;
	}

	/**
	 * @return el regactualizado
	 */
	public String getRegactualizado() {
		return regactualizado;
	}

	/**
	 * @param regactualizado el regactualizado a establecer
	 */
	public void setRegactualizado(String regactualizado) {
		this.regactualizado = regactualizado;
	}

}
