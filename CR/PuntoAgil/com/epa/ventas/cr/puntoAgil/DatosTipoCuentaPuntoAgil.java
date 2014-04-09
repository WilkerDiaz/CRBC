/*
 * $Id: DatosTipoCuentaPuntoAgil.java,v 1.2 2006/06/26 16:05:35 programa4 Exp $
 * ===========================================================================
 *
 * Proyecto		: PuntoAgil
 * Paquete		: com.epa.ventas.cr.puntoAgil
 * Programa		: DatosTipoCuentaPuntoAgil.java
 * Creado por	: programa4
 * Creado en 	: 06/06/2006 10:42:16 AM
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
 * $Log: DatosTipoCuentaPuntoAgil.java,v $
 * Revision 1.2  2006/06/26 16:05:35  programa4
 * Renombrados algunos campos
 *
 * Revision 1.1  2006/06/10 02:11:31  programa4
 * Objetos dto para almacenar datos de tarjetas, pagos, respuestas, y archivos maestros
 *
 *
 * ===========================================================================
 */
/**
 * Clase DatosTipoCuentaPuntoAgil
 *
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.2 $ - $Date: 2006/06/26 16:05:35 $
 * @since 06/06/2006
 */
package com.epa.ventas.cr.puntoAgil;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.2 $ - $Date: 2006/06/26 16:05:35 $
 * @since 06/06/2006
 */
public class DatosTipoCuentaPuntoAgil {

    private int idPuntoAgilTipoCuenta;

    private String tipoCuentaPuntoAgil;

    private String descripcion;

    private String abrTipoCuenta;

    /**
     * @return Returns el abrTipoCuenta.
     */
    public String getAbrTipoCuenta() {
        return this.abrTipoCuenta;
    }

    /**
     * @param _abrTipoCuenta
     *            El abrTipoCuenta a asignar.
     */
    public void setAbrTipoCuenta(String _abrTipoCuenta) {
        this.abrTipoCuenta = _abrTipoCuenta;
    }

    /**
     * @return Returns el idPuntoAgilTipoCuenta.
     */
    public int getIdPuntoAgilTipoCuenta() {
        return this.idPuntoAgilTipoCuenta;
    }

    /**
     * @param _idPuntoAgilTipoCuenta
     *            El idPuntoAgilTipoCuenta a asignar.
     */
    public void setIdPuntoAgilTipoCuenta(int _idPuntoAgilTipoCuenta) {
        this.idPuntoAgilTipoCuenta = _idPuntoAgilTipoCuenta;
    }

    /**
     * @return Returns el descripcion.
     */
    public String getDescripcion() {
        return this.descripcion;
    }

    /**
     * @param _tipoCuentaDescripcion
     *            El descripcion a asignar.
     */
    public void setDescripcion(String _tipoCuentaDescripcion) {
        this.descripcion = _tipoCuentaDescripcion;
    }

    /**
     * @return Returns el tipoCuentaPuntoAgil.
     */
    public String getTipoCuentaPuntoAgil() {
        return this.tipoCuentaPuntoAgil;
    }

    /**
     * @param _tipoCuentaPuntoAgil
     *            El tipoCuentaPuntoAgil a asignar.
     */
    public void setTipoCuentaPuntoAgil(String _tipoCuentaPuntoAgil) {
        this.tipoCuentaPuntoAgil = _tipoCuentaPuntoAgil;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(final Object other) {
        if (!(other instanceof DatosTipoCuentaPuntoAgil))
            return false;
        DatosTipoCuentaPuntoAgil castOther = (DatosTipoCuentaPuntoAgil) other;
        return new EqualsBuilder().append(this.idPuntoAgilTipoCuenta,
                castOther.idPuntoAgilTipoCuenta).isEquals();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(767532643, -1579706483).append(
                this.idPuntoAgilTipoCuenta).toHashCode();
    }

    public String toString() {
        if (this.getDescripcion() == null) {
            return super.toString();
        }
        return this.getDescripcion();
    }
}
