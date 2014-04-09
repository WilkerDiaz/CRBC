/*
 * $Id: DatosCajaPuntoAgil.java,v 1.1 2006/06/16 21:32:46 programa4 Exp $
 * ===========================================================================
 *
 * Proyecto		: PuntoAgil
 * Paquete		: com.epa.ventas.cr.puntoAgil
 * Programa		: DatosCajaPuntoAgil.java
 * Creado por	: programa4
 * Creado en 	: 16/06/2006 08:33:18 AM
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
 * $Log: DatosCajaPuntoAgil.java,v $
 * Revision 1.1  2006/06/16 21:32:46  programa4
 * Agregada anulacion de pagos.
 * Actualizacion de datos con datos de los pagos de ventas, tarjeta de cred. epa
 * y abonos.
 * Agregado aviso de cierre si han pasado mas de 24 horas del ultimo cierre.
 * Agregado numServicio para poder referir a abonos
 * Agregadas consultas de puntos
 *
 *
 * ===========================================================================
 */
/**
 * Clase DatosCajaPuntoAgil
 *
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.1 $ - $Date: 2006/06/16 21:32:46 $
 * @since 16/06/2006
 */
package com.epa.ventas.cr.puntoAgil;

import java.sql.Timestamp;
import java.util.Date;

import com.becoblohm.cr.manejadorsesion.Sesion;

/**
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.1 $ - $Date: 2006/06/16 21:32:46 $
 * @since 16/06/2006
 */
public class DatosCajaPuntoAgil {

    private int numtienda;

    private int numcaja;

    private Timestamp fechaCierreMerchant;

    /**
     * Constructor por defecto. No se inicializa ningun valor.
     */
    public DatosCajaPuntoAgil() {
        // NO SE INICIALIZA NINGUN VALOR
    }

    /**
     * Si inicializar es true, se asigna tienda y caja de esta sesion y la fecha
     * actual.
     *
     * @param inicializar
     */
    public DatosCajaPuntoAgil(boolean inicializar) {
        this();
        if (inicializar) {
            this.setNumtienda(Sesion.getTienda().getNumero());
            this.setNumcaja(Sesion.getCaja().getNumero());
            this.setFechaCierreMerchant(Sesion.getFechaSistema());
        }
    }

    /**
     * @return Returns el fechaCierreMerchant.
     */
    public Timestamp getFechaCierreMerchant() {
        return this.fechaCierreMerchant;
    }

    /**
     * @param _fechaCierreMerchant
     *            El fechaCierreMerchant a asignar.
     */
    public void setFechaCierreMerchant(Timestamp _fechaCierreMerchant) {
        this.fechaCierreMerchant = _fechaCierreMerchant;
    }

    /**
     * @param _fechaCierreMerchant
     *            El fechaCierreMerchant a asignar.
     */
    public void setFechaCierreMerchant(Date _fechaCierreMerchant) {
        this.fechaCierreMerchant = new Timestamp(_fechaCierreMerchant.getTime());
    }

    /**
     * @return Returns el numcaja.
     */
    public int getNumcaja() {
        return this.numcaja;
    }

    /**
     * @param _numcaja
     *            El numcaja a asignar.
     */
    public void setNumcaja(int _numcaja) {
        this.numcaja = _numcaja;
    }

    /**
     * @return Returns el numtienda.
     */
    public int getNumtienda() {
        return this.numtienda;
    }

    /**
     * @param _numtienda
     *            El numtienda a asignar.
     */
    public void setNumtienda(int _numtienda) {
        this.numtienda = _numtienda;
    }
}
