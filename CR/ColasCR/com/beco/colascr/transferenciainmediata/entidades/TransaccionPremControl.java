package com.beco.colascr.transferenciainmediata.entidades;

public class TransaccionPremControl {
	
	private int codPromocion;
	private int nroTransacciones;
	private int nroTransaccionesXDia;
	private double maxPremio;
	private double maxPremioXDia;
	private String regactualizado;

	public TransaccionPremControl(int codPromocion, int nroTransacciones, int nroTransaccionesXDia, double maxPremio
			, double maxPremioXDia, String regactualizado) {
		// TODO Apéndice de constructor generado automáticamente
		this.codPromocion = codPromocion;
		this.nroTransacciones = nroTransacciones;
		this.nroTransaccionesXDia = nroTransaccionesXDia;
		this.maxPremio = maxPremio;
		this.maxPremioXDia = maxPremioXDia;
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
	 * @return el maxPremio
	 */
	public double getMaxPremio() {
		return maxPremio;
	}

	/**
	 * @param maxPremio el maxPremio a establecer
	 */
	public void setMaxPremio(double maxPremio) {
		this.maxPremio = maxPremio;
	}

	/**
	 * @return el maxPremioXDia
	 */
	public double getMaxPremioXDia() {
		return maxPremioXDia;
	}

	/**
	 * @param maxPremioXDia el maxPremioXDia a establecer
	 */
	public void setMaxPremioXDia(double maxPremioXDia) {
		this.maxPremioXDia = maxPremioXDia;
	}

	/**
	 * @return el nroTransacciones
	 */
	public int getNroTransacciones() {
		return nroTransacciones;
	}

	/**
	 * @param nroTransacciones el nroTransacciones a establecer
	 */
	public void setNroTransacciones(int nroTransacciones) {
		this.nroTransacciones = nroTransacciones;
	}

	/**
	 * @return el nroTransaccionesXDia
	 */
	public int getNroTransaccionesXDia() {
		return nroTransaccionesXDia;
	}

	/**
	 * @param nroTransaccionesXDia el nroTransaccionesXDia a establecer
	 */
	public void setNroTransaccionesXDia(int nroTransaccionesXDia) {
		this.nroTransaccionesXDia = nroTransaccionesXDia;
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
