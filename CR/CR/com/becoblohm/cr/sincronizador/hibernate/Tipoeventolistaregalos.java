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
public class Tipoeventolistaregalos {
	/** identifier field */
	private String codtipoevento;
	
	/** persistent field */
	private String descripcion;
	
	/** persistent field */
	private Date actualizacion;
	
	/** default constructor */
	public Tipoeventolistaregalos(){
		
	}
	
	/** full constructor */
	public Tipoeventolistaregalos(String codtipoevento, String descripcion, Date actualizacion){
		this.codtipoevento = codtipoevento;
		this.descripcion = descripcion;
		this.actualizacion = actualizacion;
	}
	
	/**
	 * @return
	 */
	public String getCodtipoevento() {
		return codtipoevento;
	}

	/**
	 * @return
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param string
	 */
	public void setCodtipoevento(String string) {
		codtipoevento = string;
	}

	/**
	 * @param string
	 */
	public void setDescripcion(String string) {
		descripcion = string;
	}

	public String toString() {
		return new ToStringBuilder(this)
			.append("codtipoevento", getCodtipoevento())
			.append("descripcion", getDescripcion())
			.toString();
	}

	public boolean equals(Object other) {
		if ( !(other instanceof Tipoeventolistaregalos) ) return false;
		Tipoeventolistaregalos castOther = (Tipoeventolistaregalos) other;
		return new EqualsBuilder()
			.append(this.getCodtipoevento(), castOther.getCodtipoevento())
			.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getCodtipoevento())
			.toHashCode();
	}
	/**
	 * @return
	 */
	public Date getActualizacion() {
		return actualizacion;
	}

	/**
	 * @param date
	 */
	public void setActualizacion(Date date) {
		actualizacion = date;
	}

}
