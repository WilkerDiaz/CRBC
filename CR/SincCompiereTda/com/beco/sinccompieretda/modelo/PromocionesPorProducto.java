package com.beco.sinccompieretda.modelo;

public class PromocionesPorProducto {
	int codigo;
	int detalle;
	String value;
	boolean activo=true;
	public PromocionesPorProducto(int codigo, int detalle, boolean activo) {
		super();
		this.codigo = codigo;
		this.detalle = detalle;
		this.activo = activo;
	}
	public PromocionesPorProducto(int codigo, String value, boolean activo) {
		super();
		this.codigo = codigo;
		this.value = value;
		this.activo = activo;
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
	 * @return el value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value el value a establecer
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return el activo
	 */
	public boolean isActivo() {
		return activo;
	}
	/**
	 * @param activo el activo a establecer
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	

}
