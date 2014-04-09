package com.beco.sinccompieretda.modelo;
/**
 * 
 */

/**
 * @author aavila
 *
 */
public class DetallePromocion {
	
	private int codigo;
	private int detalle;
	private String name;
	private int categoria;
	private int departamento;
	private int linea;
	private double porcdescuento;
	private String tienda;
	private String aprobado;
	private String activo;
	private String todaLaTienda;
	
	public DetallePromocion(int codigo, int detalle, String name, int categoria, int departamento
			, int linea, double porcdescuento, String tienda, String aprobado, String activo, String todaLaTienda) {
		// TODO Apéndice de constructor generado automáticamente
		this.codigo = codigo;
		this.detalle = detalle;
		this.name = name;
		this.categoria = categoria;
		this.departamento = departamento;
		this.linea = linea;
		this.porcdescuento = porcdescuento;
		this.tienda = tienda;
		this.aprobado = aprobado;
		this.activo = activo;
		this.todaLaTienda = todaLaTienda;
		
	}

	/**
	 * @return el aprobado
	 */
	public String getAprobado() {
		if (aprobado.equals("Y")) return "A";
		else return "E";
	}

	/**
	 * @return el categoria
	 */
	public int getCategoria() {
		return categoria;
	}

	/**
	 * @param categoria el categoria a establecer
	 */
	public void setCategoria(int categoria) {
		this.categoria = categoria;
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
	 * @return el departamento
	 */
	public int getDepartamento() {
		return departamento;
	}

	/**
	 * @param departamento el departamento a establecer
	 */
	public void setDepartamento(int departamento) {
		this.departamento = departamento;
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


	/**
	 * @return el linea
	 */
	public int getLinea() {
		return linea;
	}

	/**
	 * @param linea el linea a establecer
	 */
	public void setLinea(int linea) {
		this.linea = linea;
	}

	/**
	 * @return el name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name el name a establecer
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return el porcdescuento
	 */
	public double getPorcdescuento() {
		return porcdescuento;
	}

	/**
	 * @param porcdescuento el porcdescuento a establecer
	 */
	public void setPorcdescuento(double porcdescuento) {
		this.porcdescuento = porcdescuento;
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

	/**
	 * @return el activo
	 */
	public String getActivo() {
		if (activo.equals("Y")) return "A";
		return "E";
	}

	/**
	 * @param activo el activo a establecer
	 */
	public void setActivo(String activo) {
		this.activo = activo;
	}

	public String getTodaLaTienda() {
		return todaLaTienda;
	}

	public void setTodaLaTienda(String todaLaTienda) {
		this.todaLaTienda = todaLaTienda;
	}

}
