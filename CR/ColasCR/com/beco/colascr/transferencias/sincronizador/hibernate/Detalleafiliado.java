package com.beco.colascr.transferencias.sincronizador.hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import net.sf.hibernate.CallbackException;
import net.sf.hibernate.Session;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Detalleafiliado implements net.sf.hibernate.Lifecycle,Serializable {

    /** identifier field */
    private com.beco.colascr.transferencias.sincronizador.hibernate.DetalleafiliadoPK comp_id;

    /** nullable persistent field */
    private BigDecimal monto;

    /** nullable persistent field */
    private String proceso;

    /** persistent field */
    private String notificado;

    /** nullable persistent field */
    private Date actualizacion;

    /** full constructor */
    public Detalleafiliado(com.beco.colascr.transferencias.sincronizador.hibernate.DetalleafiliadoPK comp_id, BigDecimal monto, String proceso, String notificado, Date actualizacion) {
        this.comp_id = comp_id;
        this.monto = monto;
        this.proceso = proceso;
        this.notificado = notificado;
        this.actualizacion = actualizacion;
    }

    /** default constructor */
    public Detalleafiliado() {
    }

    /** minimal constructor */
    public Detalleafiliado(com.beco.colascr.transferencias.sincronizador.hibernate.DetalleafiliadoPK comp_id, String notificado) {
        this.comp_id = comp_id;
        this.notificado = notificado;
    }

    public com.beco.colascr.transferencias.sincronizador.hibernate.DetalleafiliadoPK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(com.beco.colascr.transferencias.sincronizador.hibernate.DetalleafiliadoPK comp_id) {
        this.comp_id = comp_id;
    }

    public BigDecimal getMonto() {
        return this.monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getProceso() {
        return this.proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public String getNotificado() {
        return this.notificado;
    }

    public void setNotificado(String notificado) {
        this.notificado = notificado;
    }

    public Date getActualizacion() {
        return this.actualizacion;
    }

    public void setActualizacion(Date actualizacion) {
        this.actualizacion = actualizacion;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .append("monto", getMonto())
            .append("proceso", getProceso())
            .append("notificado", getNotificado())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Detalleafiliado) ) return false;
        Detalleafiliado castOther = (Detalleafiliado) other;
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

}
