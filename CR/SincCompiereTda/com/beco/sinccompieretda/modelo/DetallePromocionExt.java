package com.beco.sinccompieretda.modelo;

/*
* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se comentó variable sin uso
* Fecha: agosto 2011
*/
public class DetallePromocionExt {
	
	private int codigo;
	private int detalle;
	private int detalleC;
	private double porcdescuento;
	private int categoria;
	private int departamento;
	private String marca;
	private String refproveedor;
	private int linea;
	private long seccion;
	private double montominimo;
	private int cantidadproducto;
	private String producto;
	private int cantproddesc;
	private double bsbonoregalo;
	private String aprobado;
	private String name;
	private int grupo;
	private String tienda;
	private String activo;
	private int tipoPromocion;
	private String regalo;
	private String regactualizado;
//	private String acumulado;

	public DetallePromocionExt(int codigo, int detalle, double porcdescuento, int categoria
			, int departamento, String marca, String refproveedor, int linea, int seccion
			, double montominimo, int cantidadproducto, String producto, int cantproddesc
			, double bsbonoregalo, String name, int grupo, String tienda, String activo, int tipoPromocion
			, String regalo, String regactualizado, String acumulado) {
		// TODO Apéndice de constructor generado automáticamente
		this.codigo = codigo;
		this.detalle = detalle;
		this.porcdescuento = porcdescuento;
		this.categoria = categoria;
		this.departamento = departamento;
		this.marca = marca;
		this.refproveedor = refproveedor;
		this.linea = linea;
		this.seccion = seccion;
		this.montominimo = montominimo;
		this.cantidadproducto = cantidadproducto;
		this.producto = producto;
		this.cantidadproducto = cantidadproducto;
		this.bsbonoregalo = bsbonoregalo;
		this.name = name;
		this.grupo = grupo;
		this.tienda = tienda;
		this.activo = activo;
		this.tipoPromocion = tipoPromocion;
		this.regalo = regalo;
		this.regactualizado = regactualizado;
		this.detalleC = detalle;
		this.cantproddesc = cantproddesc;
		//this.acumulado=acumulado;
	}
	/**
	 * @return el aprobado
	 */
	public String getAprobado() {
		if (aprobado.equals("Y"))return "A";
		else return "E";
	}
	/**
	 * @return el bsbonoregalo
	 */
	public double getBsbonoregalo() {
		return bsbonoregalo;
	}
	/**
	 * @return el cantidadproducto
	 */
	public int getCantidadproducto() {
		return cantidadproducto;
	}
	/**
	 * @return el cantproddesc
	 */
	public int getCantproddesc() {
		return cantproddesc;
	}
	/**
	 * @return el categoria
	 */
	public int getCategoria() {
		return categoria;
	}
	/**
	 * @return el codigo
	 */
	public int getCodigo() {
		return codigo;
	}
	/**
	 * @return el departamento
	 */
	public int getDepartamento() {
		return departamento;
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
	 * @return el linea
	 */
	public int getLinea() {
		return linea;
	}
	/**
	 * @return el marca
	 */
	public String getMarca() {
		return marca;
	}
	/**
	 * @return el montominimo
	 */
	public double getMontominimo() {
		return montominimo;
	}
	/**
	 * @return el name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return el porcdescuento
	 */
	public double getPorcdescuento() {
		return porcdescuento;
	}
	/**
	 * @return el prodsinconsecutivo
	 */
	public int getGrupo() {
		return grupo;
	}
	/**
	 * @return el producto
	 */
	public String getProducto() {
		return producto;
	}
	/**
	 * @return el refproveedor
	 */
	public String getRefproveedor() {
		return refproveedor;
	}
	/**
	 * @return el seccion
	 */
	public long getSeccion() {
		return seccion;
	}
	/**
	 * @return el tienda
	 */
	public String getTienda() {
		return tienda;
	}
	/**
	 * @return el activo
	 */
	public String getActivo() {
		if (activo.equals("Y") || activo.equals("A")) return "A";
		return "E";
	}
	/**
	 * @return el tipoPromocion
	 */
	public int getTipoPromocion() {
		return tipoPromocion;
	}
	/**
	 * @return el regalo
	 */
	public String getRegalo() {
		return regalo;
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
	 * @return el detalleC
	 */
	public int getDetalleC() {
		return detalleC;
	}
	/**
	 * @param activo el activo a establecer
	 */
	public void setActivo(String activo) {
		this.activo = activo;
	}
	public String getActivo1() {
		// TODO Apéndice de método generado automáticamente
		return activo;
	}
}
