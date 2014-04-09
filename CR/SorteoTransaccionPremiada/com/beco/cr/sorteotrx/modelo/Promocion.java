/**
 * 
 */
package com.beco.cr.sorteotrx.modelo;

/**
 * @author aavila
 *
 */
public class Promocion {
	
	public int transacciones;
	public double bs;
	public int codPromocion;

	public Promocion(int transacciones, double bs, int codPromocion) {
		// TODO Apéndice de constructor generado automáticamente
		this.transacciones=transacciones;
		this.bs=bs;
		this.codPromocion=codPromocion;	
	}

	/**
	 * @return el bs
	 */
	public double getBs() {
		return bs;
	}

	/**
	 * @param bs el bs a establecer
	 */
	public void setBs(double bs) {
		this.bs = bs;
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
	 * @return el transacciones
	 */
	public int getTransacciones() {
		return transacciones;
	}

	/**
	 * @param transacciones el transacciones a establecer
	 */
	public void setTransacciones(int transacciones) {
		this.transacciones = transacciones;
	}

}
