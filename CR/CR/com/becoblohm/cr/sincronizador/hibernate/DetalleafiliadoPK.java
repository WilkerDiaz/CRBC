package com.becoblohm.cr.sincronizador.hibernate;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class DetalleafiliadoPK implements Serializable {

    /** identifier field */
    private Short numtienda;

    /** identifier field */
    private String codafiliado;

    /** identifier field */
    private String mensaje;

    /** full constructor */
    public DetalleafiliadoPK(Short numtienda, String codafiliado, String mensaje) {
        this.numtienda = numtienda;
        this.codafiliado = codafiliado;
        this.mensaje = mensaje;
    }

    /** default constructor */
    public DetalleafiliadoPK() {
    }

    public Short getNumtienda() {
        return this.numtienda;
    }

    public void setNumtienda(Short numtienda) {
        this.numtienda = numtienda;
    }

    public String getCodafiliado() {
        return this.codafiliado;
    }

    public void setCodafiliado(String codafiliado) {
        this.codafiliado = codafiliado;
    }

    public String getMensaje() {
        return this.mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("numtienda", getNumtienda())
            .append("codafiliado", getCodafiliado())
            .append("mensaje", getMensaje())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof DetalleafiliadoPK) ) return false;
        DetalleafiliadoPK castOther = (DetalleafiliadoPK) other;
        return new EqualsBuilder()
            .append(this.getNumtienda(), castOther.getNumtienda())
            .append(this.getCodafiliado(), castOther.getCodafiliado())
            .append(this.getMensaje(), castOther.getMensaje())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getNumtienda())
            .append(getCodafiliado())
            .append(getMensaje())
            .toHashCode();
    }

}
