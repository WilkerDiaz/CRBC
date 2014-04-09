/*
 * $Id: LabelValueBean.java,v 1.1 2006/07/05 15:25:38 programa4 Exp $
 * ===========================================================================
 *
 * Proyecto		: PuntoAgil
 * Paquete		: com.epa.ventas.cr.puntoAgil
 * Programa		: LabelValueBean.java
 * Creado por	: programa4
 * Creado en 	: 27/06/2006 06:41:30 PM
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
 * $Log: LabelValueBean.java,v $
 * Revision 1.1  2006/07/05 15:25:38  programa4
 * Agregado soporte a provimillas y actualizado anulacion
 *
 *
 * ===========================================================================
 */
/**
 * Clase LabelValueBean
 *
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.1 $ - $Date: 2006/07/05 15:25:38 $
 * @since 27/06/2006
 */
package com.epa.ventas.cr.puntoAgil;

/**
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.1 $ - $Date: 2006/07/05 15:25:38 $
 * @since 27/06/2006
 */
class LabelValueBean {
    private final String label;

    private final String value;

    /**
     * @param _label
     * @param _value
     */
    public LabelValueBean(String _label, String _value) {
        super();
        this.label = _label;
        this.value = _value;
    }

    /**
     * @return Returns el label.
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * @return Returns el value.
     */
    public String getValue() {
        return this.value;
    }

    public String toString() {
        return this.label;
    }
}
