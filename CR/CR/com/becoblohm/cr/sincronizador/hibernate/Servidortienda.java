/*
 * Creado el 11/12/2006
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.becoblohm.cr.sincronizador.hibernate;

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
public class Servidortienda {
	
	/** identifier field */
	private int numtienda;
	
	/** nullable persistent field */
	private String dbclase;
	
	/** persistent field */
	private String dburlservidor;
	
	/** nullable persistent field */
	private String dbusuario;

	/** nullable persistent field */
	private String dbclave;
	
	/** persistent field */
	private Date actualizacion;

	
	/** default constructor */
	public Servidortienda(){
		
	}

	/** full constructor */
	public Servidortienda(int numtienda,String dbclase,String dburlservidor,String dbusuario,String dbclave,Date actualizacion){
		this.numtienda = numtienda;
		this.dbclase = dbclase;
		this.dburlservidor = dburlservidor;
		this.dbusuario = dbusuario;
		this.dbclave = dbclave;
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
	public String getDbclase() {
		return dbclase;
	}

	/**
	 * @return
	 */
	public String getDbclave() {
		return dbclave;
	}

	/**
	 * @return
	 */
	public String getDburlservidor() {
		return dburlservidor;
	}

	/**
	 * @return
	 */
	public String getDbusuario() {
		return dbusuario;
	}

	/**
	 * @return
	 */
	public int getNumtienda() {
		return numtienda;
	}

	/**
	 * @param date
	 */
	public void setActualizacion(Date date) {
		actualizacion = date;
	}

	/**
	 * @param string
	 */
	public void setDbclase(String string) {
		dbclase = string;
	}

	/**
	 * @param string
	 */
	public void setDbclave(String string) {
		dbclave = string;
	}

	/**
	 * @param string
	 */
	public void setDburlservidor(String string) {
		dburlservidor = string;
	}

	/**
	 * @param string
	 */
	public void setDbusuario(String string) {
		dbusuario = string;
	}

	/**
	 * @param i
	 */
	public void setNumtienda(int i) {
		numtienda = i;
	}

	public String toString() {
		return new ToStringBuilder(this)
			.append("numtienda", getNumtienda())
			.append("dbclase", getDbclase())
			.append("dburlservidor", getDburlservidor())
			.append("dbusuario", getDbusuario())
			.append("dbclave", getDbclave())
			.toString();
	}

	public boolean equals(Object other) {
		if ( !(other instanceof Servidortienda) ) return false;
		Servidortienda castOther = (Servidortienda) other;
		return new EqualsBuilder()
			.append(this.getNumtienda(), castOther.getNumtienda())
			.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getNumtienda())
			.toHashCode();
	}
}
