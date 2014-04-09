/*
 * $Id: BusyFlag.java,v 1.3 2005/03/30 17:51:01 programa4 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: DataSourceFactory
 * Paquete		: com.epa.thread
 * Programa		: BusyFlag.java
 * Creado por	: programa4
 * Creado en 	: 25/01/2005 11:19:14 AM
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
 * $Log: BusyFlag.java,v $
 * Revision 1.3  2005/03/30 17:51:01  programa4
 * Actualizacion de javadoc
 *
 * Revision 1.2  2005/03/03 13:23:11  programa4
 * Agregada documentacion
 *
 * Revision 1.1  2005/01/31 23:37:01  programa4
 * Proyecto con utilidades varias para otros proyectos
 *
 * ===========================================================================
 */
package com.epa.thread;

/**
 * <code>
 * 
 *  Proyecto: DataSourceFactory 
 *  Clase: BusyFlag
 *  
 * </code>
 * 
 * <p>
 * <a href="BusyFlag.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.3 $ - $Date: 2005/03/30 17:51:01 $
 * @since 25/01/2005 @
 */
public class BusyFlag {
    /**
     * <code>busyflag</code> un <code>BusyFlag</code>
     */
    protected Thread busyflag = null;

    /**
     * <code>busycount</code> un <code>BusyFlag</code>
     */
    protected int busycount = 0;

    /**
     * 
     */
    public synchronized void getBusyFlag() {
        while (tryGetBusyFlag() == false) {
            try {
                wait();
            } catch (Exception e) {
                //NADA QUE HACER
            }
        }
    }

    /**
     * @return busyflag
     */
    public synchronized boolean tryGetBusyFlag() {
        if (busyflag == null) {
            busyflag = Thread.currentThread();
            busycount = 1;
            return true;
        }
        if (busyflag == Thread.currentThread()) {
            busycount++;
            return true;
        }
        return false;
    }

    /**
     * 
     */
    public synchronized void freeBusyFlag() {
        if (getBusyFlagOwner() == Thread.currentThread()) {
            busycount--;
            if (busycount == 0) {
                busyflag = null;
                notify();
            }
        }
    }

    /**
     * @return busyflag
     */
    public synchronized Thread getBusyFlagOwner() {
        return busyflag;
    }
}