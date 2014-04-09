package com.beco.colascr.transferencias.sincronizador.hibernate;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Funcionperfil implements Serializable {

    /** identifier field */
    private com.beco.colascr.transferencias.sincronizador.hibernate.FuncionperfilPK comp_id;

	/** persistent field */
	private String habilitado;

	/** persistent field */
	private String autorizado;

	/** nullable persistent field */
	private Date actualizacion;

    /** full constructor */
    public Funcionperfil(com.beco.colascr.transferencias.sincronizador.hibernate.FuncionperfilPK comp_id, String habilitado, String autorizado, Date actualizacion) {
        this.comp_id = comp_id;
        this.habilitado = habilitado;
        this.autorizado = autorizado;
        this.actualizacion = actualizacion;
    }

    /** default constructor */
    public Funcionperfil() {
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Funcionperfil) ) return false;
		Funcionperfil castOther = (Funcionperfil) other;
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
	public String getAutorizado() {
		return autorizado;
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
		.FuncionperfilPK getComp_id() {
		return comp_id;
	}

	/**
	 * @return
	 */
	public String getHabilitado() {
		return habilitado;
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
	public void setAutorizado(String string) {
		autorizado = string;
	}

	/**
	 * @param funcionperfilPK
	 */
	public void setComp_id(
		com
			.beco
			.colascr
			.transferencias
			.sincronizador
			.hibernate
			.FuncionperfilPK funcionperfilPK) {
		comp_id = funcionperfilPK;
	}

	/**
	 * @param string
	 */
	public void setHabilitado(String string) {
		habilitado = string;
	}

}
