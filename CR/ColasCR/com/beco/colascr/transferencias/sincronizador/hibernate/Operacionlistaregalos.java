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
public class Operacionlistaregalos implements Serializable {
	
	/** identifier field */
	private com.beco.colascr.transferencias.sincronizador.hibernate.OperacionlistaregalosPK comp_id;

	/** nullable persistent field */
	private Integer numtransaccion;
	
	/** nullable persistent field */
	private String codcliente;
	
	/** nullable persistent field */
	private String nomcliente;

	/** persistent field */
	private Date fecha;
	
	/** persistent field */
	private String tipooperacion;
	
	/** persistent field */
	private String codproducto;
	
	/** persistent field */
	private BigDecimal montobase;
	
	/** persistent field */
	private BigDecimal montoimpuesto;
	
	/** persistent field */
	private BigDecimal cantidad;
	
	/** persistent field */
	private int numtienda;
	
	/** persistent field */
	private int numcaja;
	
	/** persistent field */
	private String codcajero;
	
	/** nullable persistent field */
	private String dedicatoria;
	
	/** persistent field */
	private Date actualizacion;

	/** default constructor */
	public Operacionlistaregalos() {
	}

	/** full constructor */
	public Operacionlistaregalos(com.beco.colascr.transferencias.sincronizador.hibernate.OperacionlistaregalosPK comp_id,String codcliente,String nomcliente,Date fecha,String tipooperacion,String codproducto,BigDecimal montobase,BigDecimal cantidad,int numtienda,int numcaja,String codcajero,String dedicatoria,Date actualizacion) {
		this.comp_id = comp_id;
		this.codcliente = codcliente;
		this.nomcliente = nomcliente;
		this.fecha = fecha;
		this.tipooperacion = tipooperacion;
		this.codproducto = codproducto;
		this.montobase = montobase;
		this.cantidad = cantidad;
		this.numtienda = numtienda;
		this.numcaja = numcaja;
		this.codcajero = codcajero;
		this.dedicatoria = dedicatoria;
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
	public BigDecimal getCantidad() {
		return cantidad;
	}

	/**
	 * @return
	 */
	public String getCodcajero() {
		return codcajero;
	}

	/**
	 * @return
	 */
	public String getCodcliente() {
		return codcliente;
	}

	/**
	 * @return
	 */
	public String getCodproducto() {
		return codproducto;
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
		.OperacionlistaregalosPK getComp_id() {
		return comp_id;
	}

	/**
	 * @return
	 */
	public String getDedicatoria() {
		return dedicatoria;
	}

	/**
	 * @return
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @return
	 */
	public BigDecimal getMontobase() {
		return montobase;
	}

	/**
	 * @return
	 */
	public String getNomcliente() {
		return nomcliente;
	}

	/**
	 * @return
	 */
	public int getNumcaja() {
		return numcaja;
	}

	/**
	 * @return
	 */
	public int getNumtienda() {
		return numtienda;
	}

	/**
	 * @return
	 */
	public String getTipooperacion() {
		return tipooperacion;
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
	public void setCantidad(BigDecimal f) {
		cantidad = f;
	}

	/**
	 * @param string
	 */
	public void setCodcajero(String string) {
		codcajero = string;
	}

	/**
	 * @param string
	 */
	public void setCodcliente(String string) {
		codcliente = string;
	}

	/**
	 * @param string
	 */
	public void setCodproducto(String string) {
		codproducto = string;
	}

	/**
	 * @param operacionlistaregalosPK
	 */
	public void setComp_id(
		com
			.beco
			.colascr
			.transferencias
			.sincronizador
			.hibernate
			.OperacionlistaregalosPK operacionlistaregalosPK) {
		comp_id = operacionlistaregalosPK;
	}

	/**
	 * @param string
	 */
	public void setDedicatoria(String string) {
		dedicatoria = string;
	}

	/**
	 * @param date
	 */
	public void setFecha(Date date) {
		fecha = date;
	}

	/**
	 * @param d
	 */
	public void setMontobase(BigDecimal d) {
		montobase = d;
	}

	/**
	 * @param string
	 */
	public void setNomcliente(String string) {
		nomcliente = string;
	}

	/**
	 * @param s
	 */
	public void setNumcaja(int s) {
		numcaja = s;
	}

	/**
	 * @param d
	 */
	public void setNumtienda(int d) {
		numtienda = d;
	}

	/**
	 * @param c
	 */
	public void setTipooperacion(String s) {
		tipooperacion = s;
	}
	
	/**
	 * @return
	 */
	public Integer getNumtransaccion() {
		return numtransaccion;
	}

	/**
	 * @param i
	 */
	public void setNumtransaccion(Integer i) {
		numtransaccion = i;
	}

	/**
	 * @return
	 */
	public BigDecimal getMontoimpuesto() {
		return montoimpuesto;
	}

	/**
	 * @param decimal
	 */
	public void setMontoimpuesto(BigDecimal decimal) {
		montoimpuesto = decimal;
	}

	/* (no Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (!(other instanceof Operacionlistaregalos)) return false;
		Operacionlistaregalos castOther = (Operacionlistaregalos) other;
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
			.append("codcliente", getCodcliente())
			.append("nomcliente", getNomcliente())
			.append("fecha", getFecha())
			.append("tipooperacion", getTipooperacion())
			.append("codproducto", getCodproducto())
			.append("montobase", getMontobase())
			.append("cantidad", getCantidad())
			.append("numtienda", getNumtienda())
			.append("numcaja", getNumcaja())
			.append("codcajero", getCodcajero())
			.append("dedicatoria", getDedicatoria())
			.toString();
	}

}
