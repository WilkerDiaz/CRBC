package com.beco.colascr.transferencias.sincronizador.hibernate;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Funcion implements Serializable {

    /** identifier field */
    private com.beco.colascr.transferencias.sincronizador.hibernate.FuncionPK comp_id;

    /** persistent field */
    private String descripcion;

	/** persistent field */
	private String nivelauditoria;

	/** persistent field */
	private String regvigente;

	/** persistent field */
	private String reqautorizacion;

	/** nullable persistent field */
	private Date actualizacion;

    /** full constructor */
    public Funcion(com.beco.colascr.transferencias.sincronizador.hibernate.FuncionPK comp_id, String descripcion, String nivelauditoria, String regvigente, String reqautorizacion, Date actualizacion) {
        this.comp_id = comp_id;
        this.descripcion = descripcion;
        this.nivelauditoria = nivelauditoria;
        this.regvigente = regvigente;
        this.reqautorizacion = reqautorizacion;
        this.actualizacion = actualizacion;
    }

    /** default constructor */
    public Funcion() {
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Funcion) ) return false;
		Funcion castOther = (Funcion) other;
        return new EqualsBuilder()
            .append(this.getComp_id(), castOther.getComp_id())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getComp_id())
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
	public com
		.beco
		.colascr
		.transferencias
		.sincronizador
		.hibernate
		.FuncionPK getComp_id() {
		return comp_id;
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
	 * @return
	 */
	public String getReqautorizacion() {
		return reqautorizacion;
	}

	/**
	 * @param date
	 */
	public void setActualizacion(Date date) {
		actualizacion = date;
	}

	/**
	 * @param funcionPK
	 */
	public void setComp_id(
		com
			.beco
			.colascr
			.transferencias
			.sincronizador
			.hibernate
			.FuncionPK funcionPK) {
		comp_id = funcionPK;
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

	/**
	 * @param string
	 */
	public void setReqautorizacion(String string) {
		reqautorizacion = string;
	}

}
