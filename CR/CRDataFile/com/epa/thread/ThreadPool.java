/*
 * $Id: ThreadPool.java,v 1.3 2005/03/30 17:51:00 programa4 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: DataSourceFactory
 * Paquete		: com.epa.thread
 * Programa		: ThreadPool.java
 * Creado por	: programa4
 * Creado en 	: 25/01/2005 11:09:06 AM
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
 * $Log: ThreadPool.java,v $
 * Revision 1.3  2005/03/30 17:51:00  programa4
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

import java.util.Vector;

/**
 * <code>
 * 
 *  
 *   Proyecto: DataSourceFactory 
 *   Clase: ThreadPool
 *   
 *  
 * </code>
 * 
 * <p>
 * <a href="ThreadPool.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.3 $ - $Date: 2005/03/30 17:51:00 $
 * @since 25/01/2005 @
 */
public class ThreadPool {
    class ThreadPoolRequest {
        Runnable target;

        Object lock;

        ThreadPoolRequest(Runnable t, Object l) {
            target = t;
            lock = l;
        }
    }

    class ThreadPoolThread extends Thread {
        ThreadPool parent;

        volatile boolean shouldRun = true;

        ThreadPoolThread(ThreadPool parentPool, int i) {
            super("ThreadPoolThread " + i);
            this.parent = parentPool;
        }

        /**
         * @see java.lang.Thread#run()
         */
        public void run() {
            ThreadPoolRequest obj = null;
            while (shouldRun) {
                try {
                    parent.cvFlag.getBusyFlag();
                    while (obj == null && shouldRun) {
                        try {
                            obj =  parent.objects
                                    .elementAt(0);
                            parent.objects.removeElementAt(0);
                        } catch (ArrayIndexOutOfBoundsException aiobe) {
                            obj = null;
                        } catch (ClassCastException cce) {
                            System.err.println("Unexpected data");
                            obj = null;
                        }
                        if (obj == null) {
                            try {
                                parent.cvAvailable.cvWait();
                            } catch (InterruptedException ie) {
                                return;
                            }
                        }
                    }
                } finally {
                    parent.cvFlag.freeBusyFlag();
                }
                if (!shouldRun)
                    return;
                obj.target.run();
                try {
                    parent.cvFlag.getBusyFlag();
                    nObjects--;
                    if (nObjects == 0)
                        parent.cvEmpty.cvSignal();
                } finally {
                    parent.cvFlag.freeBusyFlag();
                }
                if (obj.lock != null) {
                    synchronized (obj.lock) {
                        obj.lock.notify();
                    }
                }
                obj = null;
            }
        }
    }

    Vector<ThreadPoolRequest> objects;

    int nObjects = 0;

    CondVar cvAvailable, cvEmpty;

    BusyFlag cvFlag;

    ThreadPoolThread poolThreads[];

    boolean terminated = false;

    /**
     * @since 03/03/2005
     * @param n
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
    public ThreadPool(int n) {
        cvFlag = new BusyFlag();
        cvAvailable = new CondVar(cvFlag);
        cvEmpty = new CondVar(cvFlag);
        objects = new Vector<ThreadPoolRequest>();
        poolThreads = new ThreadPoolThread[n];
        for (int i = 0; i < n; i++) {
            poolThreads[i] = new ThreadPoolThread(this, i);
            poolThreads[i].start();
        }
    }

    private void add(Runnable target, Object lock) {
        try {
            cvFlag.getBusyFlag();
            if (terminated)
                throw new IllegalStateException("Thread pool has shut down");
            objects.addElement(new ThreadPoolRequest(target, lock));
            nObjects++;
            cvAvailable.cvSignal();
        } finally {
            cvFlag.freeBusyFlag();
        }
    }

    /**
     * @param target
     */
    public void addRequest(Runnable target) {
        add(target, null);
    }

    /**
     * @param target
     * @throws InterruptedException
     */
    public void addRequestAndWait(Runnable target) throws InterruptedException {
        Object lock = new Object();
        synchronized (lock) {
            add(target, lock);
            lock.wait();
        }
    }

    /**
     * @param terminate
     * @throws InterruptedException
     */
    public void waitForAll(boolean terminate) throws InterruptedException {
        try {
            cvFlag.getBusyFlag();
            while (nObjects != 0)
                cvEmpty.cvWait();
            if (terminate) {
                for (int i = 0; i < poolThreads.length; i++)
                    poolThreads[i].shouldRun = false;
                cvAvailable.cvBroadcast();
                terminated = true;
            }
        } finally {
            cvFlag.freeBusyFlag();
        }
    }

    /**
     * @throws InterruptedException
     */
    public void waitForAll() throws InterruptedException {
        waitForAll(false);
    }

}