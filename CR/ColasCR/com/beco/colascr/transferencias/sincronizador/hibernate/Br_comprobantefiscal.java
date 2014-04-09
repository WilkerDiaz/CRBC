package com.beco.colascr.transferencias.sincronizador.hibernate;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.beco.colascr.transferencias.sincronizador.hibernate.base.EntidadMarcable;

/** @author Hibernate CodeGenerator */
public class Br_comprobantefiscal implements Serializable, EntidadMarcable {

    /** identifier field */
    private com.beco.colascr.transferencias.sincronizador.hibernate.Br_comprobantefiscalPK comp_id;

    /** persistent field */
    private Date fechaemision;

	/** persistent field */
	private String serialcaja;

	/** persistent field */
	private String regactualizado;
	  
	/** full constructor */
    public Br_comprobantefiscal(com.beco.colascr.transferencias.sincronizador.hibernate.Br_comprobantefiscalPK comp_id, 
    		Date fechaemision, String serialcaja, String regactualizado) {
        this.comp_id = comp_id;
        this.fechaemision = fechaemision;
        this.serialcaja = serialcaja;
        this.regactualizado = regactualizado;
    }

    /** default constructor */
    public Br_comprobantefiscal() {
    }

   public com.beco.colascr.transferencias.sincronizador.hibernate.Br_comprobantefiscalPK getComp_id() {
		return comp_id;
	}

	public void setComp_id(
			com.beco.colascr.transferencias.sincronizador.hibernate.Br_comprobantefiscalPK comp_id) {
		this.comp_id = comp_id;
	}

	public Date getFechaemision() {
		return fechaemision;
	}

	public void setFechaemision(Date fechaemision) {
		this.fechaemision = fechaemision;
	}

	public String getRegactualizado() {
		return regactualizado;
	}

	public void setRegactualizado(String regactualizado) {
		this.regactualizado = regactualizado;
	}

	public String getSerialcaja() {
		return serialcaja;
	}

	public void setSerialcaja(String serialcaja) {
		this.serialcaja = serialcaja;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
			.append("fechaemision", getFechaemision())
			.append("serialcaja", getSerialcaja())
			.append("regactualizado", getRegactualizado())
			.toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Br_comprobantefiscal) ) return false;
        Br_comprobantefiscal castOther = (Br_comprobantefiscal) other;
        return new EqualsBuilder()
            .append(this.getComp_id(), castOther.getComp_id())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getComp_id())
            .toHashCode();
    }

}
