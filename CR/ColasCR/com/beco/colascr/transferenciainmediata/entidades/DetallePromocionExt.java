package com.beco.colascr.transferenciainmediata.entidades;

public class DetallePromocionExt {
	
	private int codPromocion;
	private int numDetalle;
	private double porcentajeDescto;
	private String codCateg;
	private String codDpto;
	private String marca;
	private String codLinea;
	private String refProv;
	private double montoMinimo;
	private int cantidadMinima;
	private int cantidadRegalada;
	private String codproducto;
	private double bsDescuento;
	private String estadoRegistro;
	private String nombrePromocion;
	private int prodSinConsecutivo;
	private String codSeccion;
	private String acumulaPremio; 
	
	public DetallePromocionExt(int codPromocion, int numDetalle, double porcentajeDescto, String codCateg, String codDpto
			, String marca, String codLinea, String refProv, double montoMinimo, int cantidadMinima, int cantidadRegalada
			, String codproducto, double bsDescuento, String estadoRegistro, String nombrePromocion, int prodSinConsecutivo
			, String codSeccion, String acumulaPremio) {
		// TODO Apéndice de constructor generado automáticamente
		this.codPromocion = codPromocion;
		this.numDetalle = numDetalle;
		this.porcentajeDescto = porcentajeDescto;
		this.codCateg = codCateg;
		this.codDpto = codDpto;
		this.marca = marca;
		this.codLinea = codLinea;
		this.refProv = refProv;
		this.montoMinimo = montoMinimo;
		this.cantidadMinima = cantidadMinima;
		this.cantidadRegalada = cantidadRegalada;
		this.codproducto = codproducto;
		this.bsDescuento = bsDescuento;
		this.estadoRegistro = estadoRegistro;
		this.nombrePromocion = nombrePromocion;
		this.prodSinConsecutivo = prodSinConsecutivo;
		this.codSeccion = codSeccion;
		this.acumulaPremio=acumulaPremio;
	}

	/**
	 * @return el bsDescuento
	 */
	public double getBsDescuento() {
		return bsDescuento;
	}

	/**
	 * @param bsDescuento el bsDescuento a establecer
	 */
	public void setBsDescuento(double bsDescuento) {
		this.bsDescuento = bsDescuento;
	}

	/**
	 * @return el cantidadMinima
	 */
	public int getCantidadMinima() {
		return cantidadMinima;
	}

	/**
	 * @param cantidadMinima el cantidadMinima a establecer
	 */
	public void setCantidadMinima(int cantidadMinima) {
		this.cantidadMinima = cantidadMinima;
	}

	/**
	 * @return el cantidadRegalada
	 */
	public int getCantidadRegalada() {
		return cantidadRegalada;
	}

	/**
	 * @param cantidadRegalada el cantidadRegalada a establecer
	 */
	public void setCantidadRegalada(int cantidadRegalada) {
		this.cantidadRegalada = cantidadRegalada;
	}

	/**
	 * @return el codCateg
	 */
	public String getCodCateg() {
		return codCateg;
	}

	/**
	 * @param codCateg el codCateg a establecer
	 */
	public void setCodCateg(String codCateg) {
		this.codCateg = codCateg;
	}

	/**
	 * @return el codDpto
	 */
	public String getCodDpto() {
		return codDpto;
	}

	/**
	 * @param codDpto el codDpto a establecer
	 */
	public void setCodDpto(String codDpto) {
		this.codDpto = codDpto;
	}

	/**
	 * @return el codLinea
	 */
	public String getCodLinea() {
		return codLinea;
	}

	/**
	 * @param codLinea el codLinea a establecer
	 */
	public void setCodLinea(String codLinea) {
		this.codLinea = codLinea;
	}

	/**
	 * @return el codproducto
	 */
	public String getCodproducto() {
		return codproducto;
	}

	/**
	 * @param codproducto el codproducto a establecer
	 */
	public void setCodproducto(String codproducto) {
		this.codproducto = codproducto;
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
	 * @return el codSeccion
	 */
	public String getCodSeccion() {
		return codSeccion;
	}

	/**
	 * @param codSeccion el codSeccion a establecer
	 */
	public void setCodSeccion(String codSeccion) {
		this.codSeccion = codSeccion;
	}

	/**
	 * @return el estadoRegistro
	 */
	public String getEstadoRegistro() {
		return estadoRegistro;
	}

	/**
	 * @param estadoRegistro el estadoRegistro a establecer
	 */
	public void setEstadoRegistro(String estadoRegistro) {
		this.estadoRegistro = estadoRegistro;
	}

	/**
	 * @return el marca
	 */
	public String getMarca() {
		return marca;
	}

	/**
	 * @param marca el marca a establecer
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}

	/**
	 * @return el montoMinimo
	 */
	public double getMontoMinimo() {
		return montoMinimo;
	}

	/**
	 * @param montoMinimo el montoMinimo a establecer
	 */
	public void setMontoMinimo(double montoMinimo) {
		this.montoMinimo = montoMinimo;
	}

	/**
	 * @return el nombrePromocion
	 */
	public String getNombrePromocion() {
		return nombrePromocion;
	}

	/**
	 * @param nombrePromocion el nombrePromocion a establecer
	 */
	public void setNombrePromocion(String nombrePromocion) {
		this.nombrePromocion = nombrePromocion;
	}

	/**
	 * @return el numDetalle
	 */
	public int getNumDetalle() {
		return numDetalle;
	}

	/**
	 * @param numDetalle el numDetalle a establecer
	 */
	public void setNumDetalle(int numDetalle) {
		this.numDetalle = numDetalle;
	}

	/**
	 * @return el porcentajeDescto
	 */
	public double getPorcentajeDescto() {
		return porcentajeDescto;
	}

	/**
	 * @param porcentajeDescto el porcentajeDescto a establecer
	 */
	public void setPorcentajeDescto(double porcentajeDescto) {
		this.porcentajeDescto = porcentajeDescto;
	}

	/**
	 * @return el prodSinConsecutivo
	 */
	public int getProdSinConsecutivo() {
		return prodSinConsecutivo;
	}

	/**
	 * @param prodSinConsecutivo el prodSinConsecutivo a establecer
	 */
	public void setProdSinConsecutivo(int prodSinConsecutivo) {
		this.prodSinConsecutivo = prodSinConsecutivo;
	}

	/**
	 * @return el refProv
	 */
	public String getRefProv() {
		return refProv;
	}

	/**
	 * @param refProv el refProv a establecer
	 */
	public void setRefProv(String refProv) {
		this.refProv = refProv;
	}

	public String getAcumulaPremio() {
		return acumulaPremio;
	}

	public void setAcumulaPremio(String acumulaPremio) {
		this.acumulaPremio = acumulaPremio;
	}

}
