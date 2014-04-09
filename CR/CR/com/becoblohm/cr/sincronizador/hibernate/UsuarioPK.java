package com.becoblohm.cr.sincronizador.hibernate;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class UsuarioPK implements Serializable {

    /** identifier field */
    private Short numtienda;

    /** identifier field */
    private String numficha;

    /** full constructor */
    public UsuarioPK(Short numtienda, String numficha) {
        this.numtienda = numtienda;
        this.numficha = numficha;
    }

    /** default constructor */
    public UsuarioPK() {
    }

    public Short getNumtienda() {
        return this.numtienda;
    }

    public void setNumtienda(Short numtienda) {
        this.numtienda = numtienda;
    }

    public String getNumficha() {
        return this.numficha;
    }

    public void setNumficha(String numficha) {
        this.numficha = numficha;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("numtienda", getNumtienda())
            .append("numficha", getNumficha())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof UsuarioPK) ) return false;
        UsuarioPK castOther = (UsuarioPK) other;
        return new EqualsBuilder()
            .append(this.getNumtienda(), castOther.getNumtienda())
            .append(this.getNumficha(), castOther.getNumficha())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getNumtienda())
            .append(getNumficha())
            .toHashCode();
    }

}
