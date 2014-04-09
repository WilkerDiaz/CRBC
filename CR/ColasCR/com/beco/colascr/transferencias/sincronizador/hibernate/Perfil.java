package com.beco.colascr.transferencias.sincronizador.hibernate;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Perfil implements Serializable {

    /** identifier field */
    private String codperfil;

    /** persistent field */
    private String descripcion;

	/** persistent field */
	private String nivelauditoria;

	/** persistent field */
	private String regvigente;

    /** nullable persistent field */
    private Date actualizacion;

    /** full constructor */
    public Perfil(String codperfil, String descripcion, String nivelauditoria, String regvigente, Date actualizacion) {
    	this.codperfil = codperfil;
    	this.descripcion = descripcion;
		this.nivelauditoria = nivelauditoria;
    	this.regvigente = regvigente;
        this.actualizacion = actualizacion;
    }

    /** default constructor */
    public Perfil() {
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("codperfil", getCodperfil())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Perfil) ) return false;
        Perfil castOther = (Perfil) other;
        return new EqualsBuilder()
            .append(this.getCodperfil(), castOther.getCodperfil())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodperfil())
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
	public String getCodperfil() {
		return codperfil;
	}

	/**
	 * @return
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @return
	 */
	public String getNivelauditoria() {
		return nivelauditoria;
	}

	/**
	 * @return
	 */
	public String getRegvigente() {
		return regvigente;
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
	public void setCodperfil(String string) {
		codperfil = string;
	}

	/**
	 * @param string
	 */
	public void setDescripcion(String string) {
		descripcion = string;
	}

	/**
	 * @param string
	 */
	public void setNivelauditoria(String string) {
		nivelauditoria = string;
	}

	/**
	 * @param string
	 */
	public void setRegvigente(String string) {
		regvigente = string;
	}

}
