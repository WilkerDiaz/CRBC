/*
 * $Id: DatosTarjetaPuntoAgil.java,v 1.4 2007/04/25 18:46:04 programa8 Exp $
 * ===========================================================================
 *
 * Proyecto		: PuntoAgil
 * Paquete		: com.epa.ventas.cr.puntoAgil
 * Programa		: DatosTarjetaPuntoAgil.java
 * Creado por	: programa4
 * Creado en 	: 31/05/2006 02:51:42 PM
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
 * $Log: DatosTarjetaPuntoAgil.java,v $
 * Revision 1.4  2007/04/25 18:46:04  programa8
 * Version Estable Pto Agil, controlando cargos dos dobles , una sola instancia a datosExternos , control fuera de linea
 *
 * Revision 1.3  2006/07/17 13:33:45  programa4
 * Corregido bug en enmascaramiento de tarjetas
 *
 * Revision 1.2  2006/06/16 21:32:44  programa4
 * Agregada anulacion de pagos.
 * Actualizacion de datos con datos de los pagos de ventas, tarjeta de cred. epa
 * y abonos.
 * Agregado aviso de cierre si han pasado mas de 24 horas del ultimo cierre.
 * Agregado numServicio para poder referir a abonos
 * Agregadas consultas de puntos
 *
 * Revision 1.1  2006/06/10 02:11:30  programa4
 * Objetos dto para almacenar datos de tarjetas, pagos, respuestas, y archivos maestros
 *
 *
 * ===========================================================================
 */
package com.epa.ventas.cr.puntoAgil;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * DTO con los datos leidos de la tarjeta
 *
 * @author programa4 - $Author: programa8 $
 * @version $Revision: 1.4 $ - $Date: 2007/04/25 18:46:04 $
 * @since 31/05/2006
 */
public class DatosTarjetaPuntoAgil {

    private String numeroTarjeta;

    private String fechaVencimiento;

    private String tipoTarjeta;

    private String nombreCliente;
    
    //private boolean sw;

    /**
     * @return Returns el fechaVencimiento.
     */
    public String getFechaVencimiento() {
        return this.fechaVencimiento;
    }

    /**
     * @param _fechaVencimiento
     *            El fechaVencimiento a asignar.
     */
    public void setFechaVencimiento(String _fechaVencimiento) {
        this.fechaVencimiento = _fechaVencimiento;
    }

    /**
     * @return Returns el nombreCliente.
     */
    public String getNombreCliente() {
        return this.nombreCliente;
    }

    /**
     * @param _nombreCliente
     *            El nombreCliente a asignar.
     */
    public void setNombreCliente(String _nombreCliente) {
        this.nombreCliente = _nombreCliente;
    }

    /**
     * @return Returns el numeroTarjeta.
     */
    public String getNumeroTarjeta() {
        return this.numeroTarjeta;
    }

    /**
     * @return Returns el numeroTarjeta.
     */
    public String getNumeroTarjetaFormateada() {
        if (this.numeroTarjeta != null) {
            StringBuffer sb = new StringBuffer(this.numeroTarjeta);
            for (int i = 6; i < this.numeroTarjeta.length() - 4; i++) {
                sb.replace(i, i + 1, "*");
            }
            return sb.toString();
        }
        return this.numeroTarjeta;
    }

    /**
     * @param _numeroTarjeta
     *            El numeroTarjeta a asignar.
     */
    public void setNumeroTarjeta(String _numeroTarjeta) {
        this.numeroTarjeta = _numeroTarjeta;
    }

    /**
     * @return Returns el tipoTarjeta.
     */
    public String getTipoTarjeta() {
        return this.tipoTarjeta;
    }

    /**
     * @param _tipoTarjeta
     *            El tipoTarjeta a asignar.
     */
    public void setTipoTarjeta(char _tipoTarjeta) {
        this.setTipoTarjeta(String.valueOf(_tipoTarjeta));
    }

    /**
     * @param _tipoTarjeta
     *            El tipoTarjeta a asignar.
     */
    public void setTipoTarjeta(String _tipoTarjeta) {
        this.tipoTarjeta = _tipoTarjeta;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this).append("numeroTarjeta",
                this.numeroTarjeta).append("fechaVencimiento",
                this.fechaVencimiento).append("tipoTarjeta", this.tipoTarjeta)
                .append("nombreCliente", this.nombreCliente).toString();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(final Object other) {
        if (!(other instanceof DatosTarjetaPuntoAgil))
            return false;
        DatosTarjetaPuntoAgil castOther = (DatosTarjetaPuntoAgil) other;
        return new EqualsBuilder().append(this.numeroTarjeta,
                castOther.numeroTarjeta).isEquals();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(-1374740287, -936831679).append(
                this.numeroTarjeta).toHashCode();
    }

//	public boolean isSw() {
//		return sw;
//	}
//	public void setSw(boolean sw) {
//		this.sw = sw;
//	}
}
