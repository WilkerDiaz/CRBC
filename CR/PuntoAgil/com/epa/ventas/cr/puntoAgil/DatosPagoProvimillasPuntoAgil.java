/*
 * $Id: DatosPagoProvimillasPuntoAgil.java,v 1.1 2006/07/05 15:25:48 programa4 Exp $
 * ===========================================================================
 *
 * Proyecto		: PuntoAgil
 * Paquete		: com.epa.ventas.cr.puntoAgil
 * Programa		: DatosPagoProvimillasPuntoAgil.java
 * Creado por	: programa4
 * Creado en 	: 27/06/2006 06:55:15 PM
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
 * $Log: DatosPagoProvimillasPuntoAgil.java,v $
 * Revision 1.1  2006/07/05 15:25:48  programa4
 * Agregado soporte a provimillas y actualizado anulacion
 *
 *
 * ===========================================================================
 */
/**
 * Clase DatosPagoProvimillasPuntoAgil
 *
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.1 $ - $Date: 2006/07/05 15:25:48 $
 * @since 27/06/2006
 */
package com.epa.ventas.cr.puntoAgil;

/**
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.1 $ - $Date: 2006/07/05 15:25:48 $
 * @since 27/06/2006
 */
public class DatosPagoProvimillasPuntoAgil {

    private final String porcentaje;

    private final String plan;

    /**
     * @param _porcentaje
     * @param _plan
     */
    public DatosPagoProvimillasPuntoAgil(String _porcentaje, String _plan) {
        super();
        this.porcentaje = _porcentaje;
        this.plan = _plan;
    }

    /**
     * @return Returns el plan.
     */
    public String getPlan() {
        return this.plan;
    }

    /**
     * @return Returns el porcentaje.
     */
    public String getPorcentaje() {
        return this.porcentaje;
    }

}
