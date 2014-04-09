/*
 * $Id: SincronizacionEntidad.java,v 1.2 2005/03/30 17:50:03 programa4 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: GeneradorDataFile
 * Paquete		: com.epa.sincronizador.datafile.dto
 * Programa		: SincronizacionEntidad.java
 * Creado por	: programa4
 * Creado en 	: 24/02/2005 06:08:06 PM
 * (C) Copyright 2005 SuperFerretería EPA C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisión	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: SincronizacionEntidad.java,v $
 * Revision 1.2  2005/03/30 17:50:03  programa4
 * Actualizacion de javadoc
 *
 * Revision 1.1  2005/03/09 18:53:26  programa4
 * Reorganizadas las clases en paquetes, ahora hay un paquete distinto para el client y para el server
 *
 * Revision 1.2  2005/03/01 19:01:40  programa4
 * Se corrigen ciertos detalles para cuando no exista ningún registro previo de la entidad
 *
 * Revision 1.1  2005/02/25 00:17:04  programa4
 * Se agregó busqueda en planificador de fecha de ultima actualizacion y comparacion con fecha del archivo
 *
 * ===========================================================================
 */
package com.epa.sincronizador.datafile.client.dao;

import java.sql.Timestamp;

/**
 * <code>
 * Proyecto: GeneradorDataFile <br/>
 * Clase: SincronizacionEntidad
 * </code>
 * <p>
 * <a href="SincronizacionEntidad.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.2 $ - $Date: 2005/03/30 17:50:03 $
 * @since 24/02/2005 @
 */
public class SincronizacionEntidad {

    private Integer numtienda;

    private Integer numcaja;

    private String entidad;

    private Timestamp actualizacion;

    private String fallasincronizador;

    private String destino;

    /**
     * <code>SQL_SELECT</code> un <code>SincronizacionEntidad</code>
     */
    public static final String SQL_SELECT = "SELECT numtienda, numcaja, entidad, actualizacion, fallasincronizador, destino FROM CR.planificador WHERE numtienda = ? AND numcaja = ? AND entidad = ? AND destino = 'C'"; 

    /**
     * <code>SQL_SELECT</code> un <code>SincronizacionEntidad</code>
     */
    public static final String SQL_SELECT_COUNT = "SELECT COUNT(*) as cuenta FROM CR.planificador WHERE numtienda = ? AND numcaja = ? AND entidad = ? AND destino = 'C'"; 

    /**
     * <code>SQL_REPLACE</code> un <code>SincronizacionEntidad</code>
     */
    public static final String SQL_REPLACE = "REPLACE INTO CR.planificador (numtienda, numcaja, entidad, actualizacion, fallasincronizador, destino ) VALUES (?, ?, ?, ?, ?, 'C')";
    /**
     * @return Devuelve destino.
     */
    public String getDestino() {
        return this.destino;
    }
    /**
     * @param destinoSincro El destino a establecer.
     */
    public void setDestino(String destinoSincro) {
        this.destino = destinoSincro;
    }
    /**
     * @return Devuelve entidad.
     */
    public String getEntidad() {
        return this.entidad;
    }
    /**
     * @param entidadSincro El entidad a establecer.
     */
    public void setEntidad(String entidadSincro) {
        this.entidad = entidadSincro;
    }
    /**
     * @return Devuelve fallasincronizador.
     */
    public String getFallasincronizador() {
        return this.fallasincronizador;
    }
    /**
     * @param fallaSincronizador El fallasincronizador a establecer.
     */
    public void setFallasincronizador(String fallaSincronizador) {
        this.fallasincronizador = fallaSincronizador;
    }
    /**
     * @return Devuelve numcaja.
     */
    public Integer getNumcaja() {
        return this.numcaja;
    }
    /**
     * @param numCaja El numcaja a establecer.
     */
    public void setNumcaja(Integer numCaja) {
        this.numcaja = numCaja;
    }
    /**
     * @return Devuelve numtienda.
     */
    public Integer getNumtienda() {
        return this.numtienda;
    }
    /**
     * @param numTienda El numtienda a establecer.
     */
    public void setNumtienda(Integer numTienda) {
        this.numtienda = numTienda;
    }
    
    /**
     * @return Devuelve actualizacion.
     */
    public Timestamp getActualizacion() {
        return this.actualizacion;
    }
    /**
     * @param fechaActualizacion El actualizacion a establecer.
     */
    public void setActualizacion(Timestamp fechaActualizacion) {
        this.actualizacion = fechaActualizacion;
    }

}