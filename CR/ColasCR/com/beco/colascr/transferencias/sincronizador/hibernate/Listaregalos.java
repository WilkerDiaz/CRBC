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
public class Listaregalos implements Serializable {
	
	/** identifier field */
	private int codlista;

	/** persistent field */
	private String tipolista;
	
	/** persistent field */
	private String codtitular;
	
	/** persistent field */
	private Date fechaevento;
	
	/** persistent field */
	private String tipoevento;
	
	/** persistent field */
	private String titularsec;
	
	/** persistent field */
	private Date fechaapertura;
	
	/** persistent field */
	private int numtiendaapertura;
	
	/** persistent field */
	private int numcajaapertura;
	
	/** persistent field */
	private String codcajeroapertura;
		
	/** nullable persistent field */
	private Date fechacierre;
	
	/** nullable persistent field */
	private Integer numtiendacierre;
	
	/** nullable persistent field */
	private Integer numcajacierre;
	
	/** nullable persistent field */
	private String codcajerocierre;
	
	/** persistent field */
	private BigDecimal montobase;
	
	/** persistent field */
	private BigDecimal montoimpuesto;
	
	/** persistent field */
	private BigDecimal cantproductos;
	
	/** persistent field */
	private BigDecimal montoabonos;
	
	/** persistent field */
	private String notificaciones;
	
	/** persistent field */
	private String permitirventa;
	
	/** persistent field */
	private String estado;
		
	/** nullable persistent field */
	private Date actualizacion;
	
	/** default constructor */
	public Listaregalos() {
	}

	/** full constructor */
	public Listaregalos(int codlista,String tipolista,String codtitular,Date fechaevento,String tipoevento,String titularsec,Date fechaapertura,int numtiendaapertura,int numcajaapertura,String codcajeroapertura,Date fechacierre,Integer numtiendacierre,Integer numcajacierre,String codcajerocierre,BigDecimal montobase,BigDecimal montoimpuesto,BigDecimal cantproductos,BigDecimal montoabonos,String notificaciones, String permitirventa, Date actualizacion) {
		this.codlista = codlista;
		this.tipolista = tipolista;
		this.codtitular = codtitular;
		this.fechaevento = fechaevento;
		this.tipoevento = tipoevento;
		this.titularsec = titularsec;
		this.fechaapertura = fechaapertura;
		this.numtiendaapertura = numtiendaapertura;
		this.numcajaapertura = numcajaapertura;
		this.codcajeroapertura = codcajeroapertura;
		this.fechacierre = fechacierre;
		this.numtiendacierre = numtiendacierre;
		this.numcajacierre = numcajacierre;
		this.codcajerocierre = codcajerocierre;
		this.montobase = montobase;
		this.montoimpuesto = montoimpuesto;
		this.cantproductos = cantproductos;
		this.montoabonos = montoabonos;
		this.notificaciones = notificaciones;
		this.permitirventa = permitirventa; 
		this.actualizacion = actualizacion;
	}
	
	/** minimal constructor */
	public Listaregalos(int codlista,String tipolista,String codtitular,Date fechaevento,String tipoevento,String titularsec,Date fechaapertura,int numtiendaapertura,int numcajaapertura,String codcajeroapertura,BigDecimal montobase,BigDecimal montoimpuesto,BigDecimal cantproductos,BigDecimal montoabonos,String notificaciones,String permitirventa,Date actualizacion) {
		this.codlista = codlista;
		this.tipolista = tipolista;
		this.codtitular = codtitular;
		this.fechaevento = fechaevento;
		this.tipoevento = tipoevento;
		this.titularsec = titularsec;
		this.fechaapertura = fechaapertura;
		this.numtiendaapertura = numtiendaapertura;
		this.numcajaapertura = numcajaapertura;
		this.codcajeroapertura = codcajeroapertura;
		this.montobase = montobase;
		this.montoimpuesto = montoimpuesto;
		this.cantproductos = cantproductos;
		this.montoabonos = montoabonos;
		this.notificaciones = notificaciones;
		this.permitirventa = permitirventa;
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
	public BigDecimal getCantproductos() {
		return cantproductos;
	}

	/**
	 * @return
	 */
	public String getCodcajeroapertura() {
		return codcajeroapertura;
	}

	/**
	 * @return
	 */
	public String getCodcajerocierre() {
		return codcajerocierre;
	}

	/**
	 * @return
	 */
	public int getCodlista() {
		return codlista;
	}

	/**
	 * @return
	 */
	public String getCodtitular() {
		return codtitular;
	}

	/**
	 * @return
	 */
	public Date getFechaapertura() {
		return fechaapertura;
	}

	/**
	 * @return
	 */
	public Date getFechacierre() {
		return fechacierre;
	}

	/**
	 * @return
	 */
	public Date getFechaevento() {
		return fechaevento;
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
	public BigDecimal getMontobase() {
		return montobase;
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
	public int getNumcajaapertura() {
		return numcajaapertura;
	}

	/**
	 * @return
	 */
	public Integer getNumcajacierre() {
		return numcajacierre;
	}

	/**
	 * @return
	 */
	public int getNumtiendaapertura() {
		return numtiendaapertura;
	}

	/**
	 * @return
	 */
	public Integer getNumtiendacierre() {
		return numtiendacierre;
	}

	/**
	 * @return
	 */
	public String getTipoevento() {
		return tipoevento;
	}

	/**
	 * @return
	 */
	public String getTipolista() {
		return tipolista;
	}

	/**
	 * @return
	 */
	public String getTitularsec() {
		return titularsec;
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
	public void setCantproductos(BigDecimal f) {
		cantproductos = f;
	}

	/**
	 * @param string
	 */
	public void setCodcajeroapertura(String string) {
		codcajeroapertura = string;
	}

	/**
	 * @param string
	 */
	public void setCodcajerocierre(String string) {
		codcajerocierre = string;
	}

	/**
	 * @param i
	 */
	public void setCodlista(int i) {
		codlista = i;
	}

	/**
	 * @param string
	 */
	public void setCodtitular(String string) {
		codtitular = string;
	}

	/**
	 * @param date
	 */
	public void setFechaapertura(Date date) {
		fechaapertura = date;
	}

	/**
	 * @param date
	 */
	public void setFechacierre(Date date) {
		fechacierre = date;
	}

	/**
	 * @param date
	 */
	public void setFechaevento(Date date) {
		fechaevento = date;
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
	public void setMontobase(BigDecimal d) {
		montobase = d;
	}

	/**
	 * @param d
	 */
	public void setMontoimpuesto(BigDecimal d) {
		montoimpuesto = d;
	}

	/**
	 * @param i
	 */
	public void setNumcajaapertura(int i) {
		numcajaapertura = i;
	}

	/**
	 * @param i
	 */
	public void setNumcajacierre(Integer i) {
		numcajacierre = i;
	}

	/**
	 * @param s
	 */
	public void setNumtiendaapertura(int s) {
		numtiendaapertura = s;
	}

	/**
	 * @param s
	 */
	public void setNumtiendacierre(Integer s) {
		numtiendacierre = s;
	}

	/**
	 * @param string
	 */
	public void setTipoevento(String string) {
		tipoevento = string;
	}

	/**
	 * @param c
	 */
	public void setTipolista(String c) {
		tipolista = c;
	}

	/**
	 * @param string
	 */
	public void setTitularsec(String string) {
		titularsec = string;
	}

	/**
	 * @return
	 */
	public String getNotificaciones() {
		return notificaciones;
	}

	/**
	 * @return
	 */
	public String getPermitirventa() {
		return permitirventa;
	}

	/**
	 * @param string
	 */
	public void setNotificaciones(String string) {
		notificaciones = string;
	}

	/**
	 * @param string
	 */
	public void setPermitirventa(String string) {
		permitirventa = string;
	}

	/**
	 * @return
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param string
	 */
	public void setEstado(String string) {
		estado = string;
	}

	/* (no Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (!(other instanceof Listaregalos)) return false;
		Listaregalos castOther = (Listaregalos) other;
		return new EqualsBuilder()
			.append(this.getCodlista(), castOther.getCodlista())
			.isEquals();
	}

	/* (no Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getCodlista())
			.toHashCode();
	}

	/* (no Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
			.append("codlista", getCodlista())
			.append("tipolista", getTipolista())
			.append("codtitular", getCodtitular())
			.append("fechaevento", getFechaevento())
			.append("tipoevento", getTipoevento())
			.append("titularsec", getTitularsec())
			.append("fechaapertura", getFechaapertura())
			.append("numtiendaapertura", getNumtiendaapertura())
			.append("numcajaapertura", getNumcajaapertura())
			.append("codcajeroapertura", getCodcajeroapertura())
			.append("fechacierre", getFechacierre())
			.append("numtiendacierre", getNumtiendacierre())
			.append("numcajacierre", getNumcajacierre())
			.append("codcajerocierre", getCodcajerocierre())
			.append("montobase", getMontobase())
			.append("montoimpuesto", getMontoimpuesto())
			.append("cantproductos", getCantproductos())
			.append("montoabonos", getMontoabonos())
			.append("notificaciones", getMontoabonos())
			.append("permitirVenta", getMontoabonos())
			.toString();
	}

}
