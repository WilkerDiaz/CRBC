package com.beco.sinccompieretda.modelo;
/**
 * 
 */

/**
 * @author aavila
 *
 */
public class CondicionPromocion {
	
	private int codigo;
	private int orden;
	private String linea;
	private String tienda;

	public CondicionPromocion(int codigo, int orden, String linea, String tienda) {
		// TODO Apéndice de constructor generado automáticamente
		this.codigo = codigo;
		this.orden = orden;
		this.linea = linea;
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
	 * @return el linea
	 */
	public String getLinea() {
		return linea;
	}

	/**
	 * @param linea el linea a establecer
	 */
	public void setLinea(String linea) {
		this.linea = linea;
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
