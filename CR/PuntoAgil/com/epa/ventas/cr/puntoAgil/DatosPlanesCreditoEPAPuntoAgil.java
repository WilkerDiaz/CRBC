/*
 * $Id: DatosPlanesCreditoEPAPuntoAgil.java,v 1.1 2006/06/10 02:11:29 programa4 Exp $
 * ===========================================================================
 *
 * Proyecto		: PuntoAgil
 * Paquete		: com.epa.ventas.cr.puntoAgil
 * Programa		: DatosPlanesCreditoEPAPuntoAgil.java
 * Creado por	: programa4
 * Creado en 	: 06/06/2006 12:49:29 PM
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
 * $Log: DatosPlanesCreditoEPAPuntoAgil.java,v $
 * Revision 1.1  2006/06/10 02:11:29  programa4
 * Objetos dto para almacenar datos de tarjetas, pagos, respuestas, y archivos maestros
 *
 *
 * ===========================================================================
 */
package com.epa.ventas.cr.puntoAgil;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 *
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.1 $ - $Date: 2006/06/10 02:11:29 $
 * @since 06/06/2006
 */
public class DatosPlanesCreditoEPAPuntoAgil {

    private int idPuntoAgilPlanCreditoEPA;

    private String descripcion;

    private String planCredito;

    private String requiereDatoAdicional;

    private String abrPlanCredito;

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(final Object other) {
        if (!(other instanceof DatosPlanesCreditoEPAPuntoAgil))
            return false;
        DatosPlanesCreditoEPAPuntoAgil castOther = (DatosPlanesCreditoEPAPuntoAgil) other;
        return new EqualsBuilder().append(this.idPuntoAgilPlanCreditoEPA,
                castOther.idPuntoAgilPlanCreditoEPA).isEquals();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(198407141, 1337582361).append(
                this.idPuntoAgilPlanCreditoEPA).toHashCode();
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
     * @return Returns el idPuntoAgilPlanCreditoEPA.
     */
    public int getIdPuntoAgilPlanCreditoEPA() {
        return this.idPuntoAgilPlanCreditoEPA;
    }

    /**
     * @param _idPuntoAgilPlanCreditoEPA
     *            El idPuntoAgilPlanCreditoEPA a asignar.
     */
    public void setIdPuntoAgilPlanCreditoEPA(int _idPuntoAgilPlanCreditoEPA) {
        this.idPuntoAgilPlanCreditoEPA = _idPuntoAgilPlanCreditoEPA;
    }

    /**
     * @return Returns el planCredito.
     */
    public String getPlanCredito() {
        return this.planCredito;
    }

    /**
     * @param _planCredito
     *            El planCredito a asignar.
     */
    public void setPlanCredito(String _planCredito) {
        this.planCredito = _planCredito;
    }

    /**
     * @return Returns el requiereDatoAdicional.
     */
    public String getRequiereDatoAdicional() {
        return this.requiereDatoAdicional;
    }

    /**
     * @param _requiereDatoAdicional
     *            El requiereDatoAdicional a asignar.
     */
    public void setRequiereDatoAdicional(String _requiereDatoAdicional) {
        this.requiereDatoAdicional = _requiereDatoAdicional;
    }

    /**
     *
     * @return boolean isRequiereDatoAdicional
     */
    public boolean isRequiereDatoAdicional() {
        return "S".equalsIgnoreCase(this.getRequiereDatoAdicional());
    }

    /**
     * @return Returns el abrPlanCredito.
     */
    public String getAbrPlanCredito() {
        return this.abrPlanCredito;
    }

    /**
     * @param _abrPlanCredito El abrPlanCredito a asignar.
     */
    public void setAbrPlanCredito(String _abrPlanCredito) {
        this.abrPlanCredito = _abrPlanCredito;
    }
}
