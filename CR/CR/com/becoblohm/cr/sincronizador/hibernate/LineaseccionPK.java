package com.becoblohm.cr.sincronizador.hibernate;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class LineaseccionPK implements Serializable {

    /** identifier field */
    private String codseccion;

    /** identifier field */
    private String coddepartamento;

    /** full constructor */
    public LineaseccionPK(String codseccion, String coddepartamento) {
        this.codseccion = codseccion;
        this.coddepartamento = coddepartamento;
    }

    /** default constructor */
    public LineaseccionPK() {
    }

    public String getCodseccion() {
        return this.codseccion;
    }

    public void setCodseccion(String codseccion) {
        this.codseccion = codseccion;
    }

    public String getCoddepartamento() {
        return this.coddepartamento;
    }

    public void setCoddepartamento(String coddepartamento) {
        this.coddepartamento = coddepartamento;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("codseccion", getCodseccion())
            .append("coddepartamento", getCoddepartamento())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof LineaseccionPK) ) return false;
        LineaseccionPK castOther = (LineaseccionPK) other;
        return new EqualsBuilder()
            .append(this.getCodseccion(), castOther.getCodseccion())
            .append(this.getCoddepartamento(), castOther.getCoddepartamento())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCodseccion())
            .append(getCoddepartamento())
            .toHashCode();
    }

}
