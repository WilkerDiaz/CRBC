package com.becoblohm.cr.sincronizador.hibernate;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Lineaseccion implements Serializable {

    /** identifier field */
    private com.becoblohm.cr.sincronizador.hibernate.LineaseccionPK comp_id;

    /** persistent field */
    private String nombre;

    /** full constructor */
    public Lineaseccion(com.becoblohm.cr.sincronizador.hibernate.LineaseccionPK comp_id, String nombre) {
        this.comp_id = comp_id;
        this.nombre = nombre;
    }

    /** default constructor */
    public Lineaseccion() {
    }

    public com.becoblohm.cr.sincronizador.hibernate.LineaseccionPK getComp_id() {
        return this.comp_id;
    }

    public void setComp_id(com.becoblohm.cr.sincronizador.hibernate.LineaseccionPK comp_id) {
        this.comp_id = comp_id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("comp_id", getComp_id())
            .append("nombre", getNombre())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Lineaseccion) ) return false;
        Lineaseccion castOther = (Lineaseccion) other;
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
