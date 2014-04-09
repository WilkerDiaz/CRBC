package com.beco.colascr.transferenciainmediata.entidades;

public class Donacion {
	
	private int codDonacion;
	private String codBarraProdDonacion;
	private String fechaInicio;
	private String fechaFinaliza;
	private String nombreDonacion;
	private String descDonacion;
	private int tipoDonacion;
	private int estadoDonacion;
	private double cantidadPropuesta;
	private String regactualizado;
	private String mostrarAlTotalizar; 

	public Donacion(int codDonacion, String codBarraProdDonacion, String fechaInicio, String fechaFinaliza, String nombreDonacion
			, String descDonacion, int tipoDonacion, int estadoDonacion, double cantidadPropuesta, String regactualizado
			, String mostrarAlTotalizar) {
		// TODO Apéndice de constructor generado automáticamente
		this.codDonacion = codDonacion;
		this.codBarraProdDonacion = codBarraProdDonacion;
		this.fechaInicio = fechaInicio;
		this.fechaFinaliza = fechaFinaliza;
		this.nombreDonacion = nombreDonacion;
		this.descDonacion = descDonacion;
		this.tipoDonacion = tipoDonacion;
		this.estadoDonacion = estadoDonacion;
		this.cantidadPropuesta = cantidadPropuesta;
		this.regactualizado = regactualizado;
		this.mostrarAlTotalizar = mostrarAlTotalizar;
	}

	/**
	 * @return el cantidadPropuesta
	 */
	public double getCantidadPropuesta() {
		return cantidadPropuesta;
	}

	/**
	 * @param cantidadPropuesta el cantidadPropuesta a establecer
	 */
	public void setCantidadPropuesta(double cantidadPropuesta) {
		this.cantidadPropuesta = cantidadPropuesta;
	}

	/**
	 * @return el codBarraProdDonacion
	 */
	public String getCodBarraProdDonacion() {
		return codBarraProdDonacion;
	}

	/**
	 * @param codBarraProdDonacion el codBarraProdDonacion a establecer
	 */
	public void setCodBarraProdDonacion(String codBarraProdDonacion) {
		this.codBarraProdDonacion = codBarraProdDonacion;
	}

	/**
	 * @return el codDonacion
	 */
	public int getCodDonacion() {
		return codDonacion;
	}

	/**
	 * @param codDonacion el codDonacion a establecer
	 */
	public void setCodDonacion(int codDonacion) {
		this.codDonacion = codDonacion;
	}

	/**
	 * @return el descDonacion
	 */
	public String getDescDonacion() {
		return descDonacion;
	}

	/**
	 * @param descDonacion el descDonacion a establecer
	 */
	public void setDescDonacion(String descDonacion) {
		this.descDonacion = descDonacion;
	}

	/**
	 * @return el estadoDonacion
	 */
	public int getEstadoDonacion() {
		return estadoDonacion;
	}

	/**
	 * @param estadoDonacion el estadoDonacion a establecer
	 */
	public void setEstadoDonacion(int estadoDonacion) {
		this.estadoDonacion = estadoDonacion;
	}

	/**
	 * @return el fechaFinaliza
	 */
	public String getFechaFinaliza() {
		return fechaFinaliza;
	}

	/**
	 * @param fechaFinaliza el fechaFinaliza a establecer
	 */
	public void setFechaFinaliza(String fechaFinaliza) {
		this.fechaFinaliza = fechaFinaliza;
	}

	/**
	 * @return el fechaInicio
	 */
	public String getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio el fechaInicio a establecer
	 */
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return el mostrarAlTotalizar
	 */
	public String getMostrarAlTotalizar() {
		return mostrarAlTotalizar;
	}

	/**
	 * @param mostrarAlTotalizar el mostrarAlTotalizar a establecer
	 */
	public void setMostrarAlTotalizar(String mostrarAlTotalizar) {
		this.mostrarAlTotalizar = mostrarAlTotalizar;
	}

	/**
	 * @return el nombreDonacion
	 */
	public String getNombreDonacion() {
		return nombreDonacion;
	}

	/**
	 * @param nombreDonacion el nombreDonacion a establecer
	 */
	public void setNombreDonacion(String nombreDonacion) {
		this.nombreDonacion = nombreDonacion;
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

	/**
	 * @return el tipoDonacion
	 */
	public int getTipoDonacion() {
		return tipoDonacion;
	}

	/**
	 * @param tipoDonacion el tipoDonacion a establecer
	 */
	public void setTipoDonacion(int tipoDonacion) {
		this.tipoDonacion = tipoDonacion;
	}

}
