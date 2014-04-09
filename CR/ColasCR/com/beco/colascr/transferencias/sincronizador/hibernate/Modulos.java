package com.beco.colascr.transferencias.sincronizador.hibernate;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Modulos implements Serializable {

    /** identifier field */
    private int codmodulo;

    /** persistent field */
    private String descripcion;

	/** persistent field */
	private String regvigente;

    /** nullable persistent field */
    private Date actualizacion;

    /** full constructor */
    public Modulos(int codmodulo, String descripcion, String regvigente, Date actualizacion) {
    	this.codmodulo = codmodulo;
    	this.descripcion = descripcion;
    	this.regvigente = regvigente;
        this.actualizacion = actualizacion;
    }

    /** default constructor */
    public Modulos() {
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("codmodulo", getCodmodulo())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Modulos) ) return false;
        Modulos castOther = (Modulos) other;
        return new EqualsBuilder()
            .append(this.getCodmodulo(), castOther.getCodmodulo())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodmodulo())
            .toHashCode();
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
	public int getCodmodulo() {
		return codmodulo;
	}

	/**
	 * @return
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param date
	 */
	public void setActualizacion(Date date) {
		actualizacion = date;
	}

	/**
	 * @param i
	 */
	public void setCodmodulo(int i) {
		codmodulo = i;
	}

	/**
	 * @param string
	 */
	public void setDescripcion(String string) {
		descripcion = string;
	}

	/**
	 * @return
	 */
	public String getRegvigente() {
		return regvigente;
	}

	/**
	 * @param string
	 */
	public void setRegvigente(String string) {
		regvigente = string;
	}

}
