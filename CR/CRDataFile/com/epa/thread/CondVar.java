/*
 * $Id: CondVar.java,v 1.2 2005/03/03 13:23:11 programa4 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: DataSourceFactory
 * Paquete		: com.epa.thread
 * Programa		: CondVar.java
 * Creado por	: programa4
 * Creado en 	: 25/01/2005 11:26:13 AM
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
 * $Log: CondVar.java,v $
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
 * Proyecto: DataSourceFactory 
 * Clase: CondVar
 * </code>
 * <p>
 * <a href="CondVar.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.2 $ - $Date: 2005/03/03 13:23:11 $
 * @since 25/01/2005 @
 */
public class CondVar {
    private BusyFlag SyncVar;

    /**
     * @since 03/03/2005
     * 
     */
    public CondVar() {
        this(new BusyFlag());
    }

    /**
     * @since 03/03/2005
     * @param sv
     */
    public CondVar(BusyFlag sv) {
        SyncVar = sv;
    }

    /**
     * @throws InterruptedException
     */
    public void cvWait() throws InterruptedException {
        cvTimedWait(SyncVar, 0);
    }

    /**
     * @param sv
     * @throws InterruptedException
     */
    public void cvWait(BusyFlag sv) throws InterruptedException {
        cvTimedWait(sv, 0);
    }

    /**
     * @param millis
     * @throws InterruptedException
     */
    public void cvTimedWait(int millis) throws InterruptedException {
        cvTimedWait(SyncVar, millis);
    }

    /**
     * @param sv
     * @param millis
     * @throws InterruptedException
     */
    public void cvTimedWait(BusyFlag sv, int millis)
            throws InterruptedException {
        int i = 0;
        InterruptedException errex = null;
        synchronized (this) {
            //     You must own the lock in order to use this method.
            if (sv.getBusyFlagOwner() != Thread.currentThread()) {
                throw new IllegalMonitorStateException(
                        "current thread not owner");
            }
            //     Release the lock (completely).
            while (sv.getBusyFlagOwner() == Thread.currentThread()) {
                i++;
                sv.freeBusyFlag();
            }
            //     Use wait() method.
            try {
                if (millis == 0) {
                    wait();
                } else {
                    wait(millis);
                }
            } catch (InterruptedException iex) {
                errex = iex;
            }
        }
        //     Obtain the lock (return to original state).
        for (; i > 0; i--) {
            sv.getBusyFlag();
        }
        if (errex != null)
            throw errex;
        return;
    }

    /**
     * 
     */
    public void cvSignal() {
        cvSignal(SyncVar);
    }

    /**
     * @param sv
     */
    public synchronized void cvSignal(BusyFlag sv) {
        //     You must own the lock in order to use this method.
        if (sv.getBusyFlagOwner() != Thread.currentThread()) {
            throw new IllegalMonitorStateException("current thread not owner");
        }
        notify();
    }

    /**
     * 
     */
    public void cvBroadcast() {
        cvBroadcast(SyncVar);
    }

    /**
     * @param sv
     */
    public synchronized void cvBroadcast(BusyFlag sv) {
        //     You must own the lock in order to use this method.
        if (sv.getBusyFlagOwner() != Thread.currentThread()) {
            throw new IllegalMonitorStateException("current thread not owner");
        }
        notifyAll();
    }
}