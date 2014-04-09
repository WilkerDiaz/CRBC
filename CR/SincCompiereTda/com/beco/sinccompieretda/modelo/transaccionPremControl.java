package com.beco.sinccompieretda.modelo;

public class transaccionPremControl {
	private int codigo;
	private int detalle;
	private int nrotransacciones;
	private int nrotransaccionesxdia;
	private double maxprembs;
	private double maxprembsxdia;
	private String tienda;
	
	public transaccionPremControl(int codigo, int detalle, int nrotransacciones, int nrotransaccionesxdia, double maxprembs
			, double maxprembsxdia, String tienda) {
		// TODO Apéndice de constructor generado automáticamente
		this.codigo = codigo;
		this.detalle = detalle;
		this.nrotransacciones = nrotransacciones;
		this.nrotransaccionesxdia = nrotransaccionesxdia;
		this.maxprembs = maxprembs;
		this.maxprembsxdia = maxprembsxdia;
		this.tienda = tienda;
		
	}

	/**
	 * @return el codigo
	 */
	public int getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo el codigo a establecer
	 */
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return el detalle
	 */
	public int getDetalle() {
		return detalle;
	}

	/**
	 * @param detalle el detalle a establecer
	 */
	public void setDetalle(int detalle) {
		this.detalle = detalle;
	}

	/**
	 * @return el maxprembs
	 */
	public double getMaxprembs() {
		return maxprembs;
	}

	/**
	 * @param maxprembs el maxprembs a establecer
	 */
	public void setMaxprembs(double maxprembs) {
		this.maxprembs = maxprembs;
	}

	/**
	 * @return el maxprembsxdia
	 */
	public double getMaxprembsxdia() {
		return maxprembsxdia;
	}

	/**
	 * @param maxprembsxdia el maxprembsxdia a establecer
	 */
	public void setMaxprembsxdia(double maxprembsxdia) {
		this.maxprembsxdia = maxprembsxdia;
	}

	/**
	 * @return el nrotransacciones
	 */
	public int getNrotransacciones() {
		return nrotransacciones;
	}

	/**
	 * @param nrotransacciones el nrotransacciones a establecer
	 */
	public void setNrotransacciones(int nrotransacciones) {
		this.nrotransacciones = nrotransacciones;
	}

	/**
	 * @return el nrotransaccionesxdia
	 */
	public int getNrotransaccionesxdia() {
		return nrotransaccionesxdia;
	}

	/**
	 * @param nrotransaccionesxdia el nrotransaccionesxdia a establecer
	 */
	public void setNrotransaccionesxdia(int nrotransaccionesxdia) {
		this.nrotransaccionesxdia = nrotransaccionesxdia;
	}

	/**
	 * @return el tienda
	 */
	public String getTienda() {
		return tienda;
	}

	/**
	 * @param tienda el tienda a establecer
	 */
	public void setTienda(String tienda) {
		this.tienda = tienda;
	}

}
