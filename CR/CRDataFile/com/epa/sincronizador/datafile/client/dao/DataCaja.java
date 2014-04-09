/*
 * $Id: DataCaja.java,v 1.2 2005/03/30 17:50:02 programa4 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: GeneradorDataFile
 * Paquete		: com.epa.sincronizador.datafile.dto
 * Programa		: DataCaja.java
 * Creado por	: programa4
 * Creado en 	: 24/02/2005 06:04:21 PM
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
 * $Log: DataCaja.java,v $
 * Revision 1.2  2005/03/30 17:50:02  programa4
 * Actualizacion de javadoc
 *
 * Revision 1.1  2005/03/09 18:53:26  programa4
 * Reorganizadas las clases en paquetes, ahora hay un paquete distinto para el client y para el server
 *
 * Revision 1.1  2005/02/25 00:17:04  programa4
 * Se agregó busqueda en planificador de fecha de ultima actualizacion y comparacion con fecha del archivo
 *
 * ===========================================================================
 */
package com.epa.sincronizador.datafile.client.dao;

/**
 * Objeto que almacena datos sobre la caja
 * <p>
 * <a href="DataCaja.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.2 $ - $Date: 2005/03/30 17:50:02 $
 * @since 24/02/2005 @
 */
public class DataCaja {
    private Integer numcaja;

    private Integer numtienda;

    /**
     * <code>SQL</code> para editar los datos de esta caja
     */
    public static final String SQL = "SELECT numcaja, numtienda FROM CR.caja";

    /**
     * @return Devuelve numcaja.
     */
    public Integer getNumcaja() {
        return this.numcaja;
    }

    /**
     * @param numCaja
     *            El numcaja a establecer.
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
     * @param numTienda
     *            El numtienda a establecer.
     */
    public void setNumtienda(Integer numTienda) {
        this.numtienda = numTienda;
    }
}