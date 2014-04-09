package com.beco.colascr.transferencias.sincronizador.hibernate;

import java.io.Serializable;

import net.sf.hibernate.CallbackException;
import net.sf.hibernate.Session;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.beco.colascr.transferencias.sincronizador.hibernate.base.EntidadMarcable;

/** @author Hibernate CodeGenerator */
public class Anulaciondeabonos implements net.sf.hibernate.Lifecycle,Serializable, EntidadMarcable {

    /** identifier field */
    private com.beco.colascr.transferencias.sincronizador.hibernate.AnulaciondeabonosPK comp_id;
    
    /** persistent field */
    private String regactualizado;

    /** full constructor */
    public Anulaciondeabonos(com.beco.colascr.transferencias.sincronizador.hibernate.AnulaciondeabonosPK comp_id, String regactualizado) {
        this.comp_id = comp_id;
        this.regactualizado = regactualizado;
    }

    /** default constructor */
    public Anulaciondeabonos() {
    }

    public com.beco.colascr.transferencias.sincronizador.hibernate.AnulaciondeabonosPK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(com.beco.colascr.transferencias.sincronizador.hibernate.AnulaciondeabonosPK comp_id) {
        this.comp_id = comp_id;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Anulaciondeabonos) ) return false;
        Anulaciondeabonos castOther = (Anulaciondeabonos) other;
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
	 * Método onSave
	 *
	 * @param arg0
	 * @return
	 * @throws CallbackException
	 */
	public boolean onSave(Session arg0) throws CallbackException {
		return false;
	}

	/**
	 * Método onUpdate
	 *
	 * @param arg0
	 * @return
	 * @throws CallbackException
	 */
	public boolean onUpdate(Session arg0) throws CallbackException {
		return false;
	}

	/**
	 * Método onDelete
	 *
	 * @param arg0
	 * @return
	 * @throws CallbackException
	 */
	public boolean onDelete(Session arg0) throws CallbackException {
		return false;
	}

	/**
	 * Método onLoad
	 *
	 * @param arg0
	 * @param arg1
	 */
	public void onLoad(Session arg0, Serializable arg1) {
	}

	/**
	 * Método getRegactualizado
	 * 
	 * @return
	 * String
	 */
	public String getRegactualizado() {
		return regactualizado;
	}


	/**
	 * Método setRegactualizado
	 * 
	 * @param string
	 * void
	 */
	public void setRegactualizado(String string) {
		regactualizado = string;
	}
}
