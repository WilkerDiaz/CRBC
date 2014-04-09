/*
 * $Id: DatosCuentasEspecialesPuntoAgil.java,v 1.1 2006/06/10 02:11:29 programa4 Exp $
 * ===========================================================================
 *
 * Proyecto		: PuntoAgil
 * Paquete		: com.epa.ventas.cr.puntoAgil
 * Programa		: DatosCuentasEspecialesPuntoAgil.java
 * Creado por	: programa4
 * Creado en 	: 05/06/2006 01:01:52 PM
 * (C) Copyright 2006 Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisión	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: DatosCuentasEspecialesPuntoAgil.java,v $
 * Revision 1.1  2006/06/10 02:11:29  programa4
 * Objetos dto para almacenar datos de tarjetas, pagos, respuestas, y archivos maestros
 *
 *
 * ===========================================================================
 */
/**
 * Clase DatosCuentasEspecialesPuntoAgil
 *
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.1 $ - $Date: 2006/06/10 02:11:29 $
 * @since 05/06/2006
 */
package com.epa.ventas.cr.puntoAgil;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.1 $ - $Date: 2006/06/10 02:11:29 $
 * @since 05/06/2006
 */
public class DatosCuentasEspecialesPuntoAgil {

    private Long idPuntoAgilCuentasEspeciales;

    private String cuentaEspecial;

    private String descripcion;

    private String tipo;

    /**
     * @return Returns el cuentaEspecial.
     */
    public String getCuentaEspecial() {
        return this.cuentaEspecial;
    }

    /**
     * @param _cuentaEspecial
     *            El cuentaEspecial a asignar.
     */
    public void setCuentaEspecial(String _cuentaEspecial) {
        this.cuentaEspecial = _cuentaEspecial;
    }

    /**
     * @return Returns el descripcion.
     */
    public String getDescripcion() {
        return this.descripcion;
    }

    /**
     * @param _descripcion
     *            El descripcion a asignar.
     */
    public void setDescripcion(String _descripcion) {
        this.descripcion = _descripcion;
    }

    /**
     * @return Returns el idPuntoAgilCuentasEspeciales.
     */
    public Long getIdPuntoAgilCuentasEspeciales() {
        return this.idPuntoAgilCuentasEspeciales;
    }

    /**
     * @param _idPuntoAgilCuentasEspeciales
     *            El idPuntoAgilCuentasEspeciales a asignar.
     */
    public void setIdPuntoAgilCuentasEspeciales(
            Long _idPuntoAgilCuentasEspeciales) {
        this.idPuntoAgilCuentasEspeciales = _idPuntoAgilCuentasEspeciales;
    }

    /**
     * @param _idPuntoAgilCuentasEspeciales
     *            El idPuntoAgilCuentasEspeciales a asignar.
     */
    public void setIdPuntoAgilCuentasEspeciales(
            long _idPuntoAgilCuentasEspeciales) {
        this.idPuntoAgilCuentasEspeciales = new Long(
                _idPuntoAgilCuentasEspeciales);
    }

    /**
     * @return Returns el tipo.
     */
    public String getTipo() {
        return this.tipo;
    }

    /**
     * @param _tipo
     *            El tipo a asignar.
     */
    public void setTipo(String _tipo) {
        this.tipo = _tipo;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(final Object other) {
        if (!(other instanceof DatosCuentasEspecialesPuntoAgil))
            return false;
        DatosCuentasEspecialesPuntoAgil castOther = (DatosCuentasEspecialesPuntoAgil) other;
        return new EqualsBuilder().append(this.idPuntoAgilCuentasEspeciales,
                castOther.idPuntoAgilCuentasEspeciales).isEquals();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(845140095, -1369940631).append(
                this.idPuntoAgilCuentasEspeciales).toHashCode();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        if (this.getDescripcion() == null) {
            return new ToStringBuilder(this).append("cuentaEspecial",
                    this.cuentaEspecial)
                    .append("descripcion", this.descripcion).append("tipo",
                            this.tipo).toString();
        }
        return this.getDescripcion();
    }

}
