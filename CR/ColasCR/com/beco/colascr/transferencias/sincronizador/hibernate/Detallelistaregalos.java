/*
 * Creado el 14/11/2006
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.beco.colascr.transferencias.sincronizador.hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author rabreu
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class Detallelistaregalos implements Serializable {

	/** identifier field */
	private com.beco.colascr.transferencias.sincronizador.hibernate.DetallelistaregalosPK comp_id;

	/** persistent field */
	private int correlativoitem;

	/** persistent field */
	private BigDecimal cantidad;

	/** persistent field */
	private BigDecimal precioregular;

	/** persistent field */
	private BigDecimal preciofinal;

	/** persistent field */
	private BigDecimal montoimpuesto;

	/** persistent field */
	private String codtipocaptura;

	/** nullable persistent field */
	private Integer codpromocion;
	
	/** persistent field */
	private BigDecimal cantcomprado;
	
	/** persistent field */
	private BigDecimal montoabonos;
	
	/** nullable persistent field */
	private Date actualizacion;
	
	/** default constructor */
	public Detallelistaregalos(){
	}
	
	/** full constructor */
	public Detallelistaregalos(com.beco.colascr.transferencias.sincronizador.hibernate.DetallelistaregalosPK comp_id,int correlativoitem,BigDecimal cantidad,BigDecimal precioregular,BigDecimal preciofinal,BigDecimal montoimpuesto,String codtipocaptura,Integer codpromocion,BigDecimal cantcomprado,BigDecimal montoabonos,Date actualizacion){
		this.comp_id = comp_id;
		this.correlativoitem = correlativoitem;
		this.cantidad = cantidad;
		this.precioregular = precioregular;
		this.preciofinal = preciofinal;
		this.montoimpuesto = montoimpuesto;
		this.codtipocaptura = codtipocaptura;
		this.codpromocion = codpromocion;
		this.cantcomprado = cantcomprado;
		this.montoabonos = montoabonos;
		this.actualizacion = actualizacion;		
	}
	/**
	 * @return
	 */
	public Date getActualizacion() {
		return actualizacion;
	}

	/**
	 * @return
	 */
	public BigDecimal getCantcomprado() {
		return cantcomprado;
	}

	/**
	 * @return
	 */
	public BigDecimal getCantidad() {
		return cantidad;
	}

	/**
	 * @return
	 */
	public Integer getCodpromocion() {
		return codpromocion;
	}

	/**
	 * @return
	 */
	public String getCodtipocaptura() {
		return codtipocaptura;
	}

	/**
	 * @return
	 */
	public com
		.beco
		.colascr
		.transferencias
		.sincronizador
		.hibernate
		.DetallelistaregalosPK getComp_id() {
		return comp_id;
	}

	/**
	 * @return
	 */
	public int getCorrelativoitem() {
		return correlativoitem;
	}

	/**
	 * @return
	 */
	public BigDecimal getMontoabonos() {
		return montoabonos;
	}

	/**
	 * @return
	 */
	public BigDecimal getMontoimpuesto() {
		return montoimpuesto;
	}

	/**
	 * @return
	 */
	public BigDecimal getPreciofinal() {
		return preciofinal;
	}

	/**
	 * @return
	 */
	public BigDecimal getPrecioregular() {
		return precioregular;
	}

	/**
	 * @param date
	 */
	public void setActualizacion(Date date) {
		actualizacion = date;
	}

	/**
	 * @param f
	 */
	public void setCantcomprado(BigDecimal f) {
		cantcomprado = f;
	}

	/**
	 * @param f
	 */
	public void setCantidad(BigDecimal f) {
		cantidad = f;
	}

	/**
	 * @param i
	 */
	public void setCodpromocion(Integer i) {
		codpromocion = i;
	}

	/**
	 * @param c
	 */
	public void setCodtipocaptura(String c) {
		codtipocaptura = c;
	}

	/**
	 * @param detallelistaregalosPK
	 */
	public void setComp_id(
		com
			.beco
			.colascr
			.transferencias
			.sincronizador
			.hibernate
			.DetallelistaregalosPK detallelistaregalosPK) {
		comp_id = detallelistaregalosPK;
	}

	/**
	 * @param s
	 */
	public void setCorrelativoitem(int s) {
		correlativoitem = s;
	}

	/**
	 * @param d
	 */
	public void setMontoabonos(BigDecimal d) {
		montoabonos = d;
	}

	/**
	 * @param d
	 */
	public void setMontoimpuesto(BigDecimal d) {
		montoimpuesto = d;
	}

	/**
	 * @param d
	 */
	public void setPreciofinal(BigDecimal d) {
		preciofinal = d;
	}

	/**
	 * @param d
	 */
	public void setPrecioregular(BigDecimal d) {
		precioregular = d;
	}
	
	/* (no Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (!(other instanceof Detallelistaregalos)) return false;
		Detallelistaregalos castOther = (Detallelistaregalos) other;
		return new EqualsBuilder()
			.append(this.getComp_id(), castOther.getComp_id())
			.isEquals();
	}

	/* (no Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getComp_id())
			.toHashCode();
	}

	/* (no Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
			.append("correlativoitem", getCorrelativoitem())
			.append("cantidad", getCantidad())
			.append("precioregular", getPrecioregular())
			.append("preciofinal", getPreciofinal())
			.append("montoimpuesto", getMontoimpuesto())
			.append("codtipocaptura", getCodtipocaptura())
			.append("codpromocion", getCodpromocion())
			.append("cantcomprado", getCantcomprado())
			.append("montoabonos", getMontoabonos())
			.toString();
	}
}