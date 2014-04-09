/*
 * $Id: HiloSyncTransacciones.java,v 1.2.2.2 2005/04/21 21:54:25 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.sincronizador
 * Programa		: HiloSyncTransacciones.java
 * Creado por	: Programa8
 * Creado en 	: 04-ene-2005 13:29:48
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
 * Objeto de creación de hilo de sincronización de transacciones
 * en el mismo instante en que se finaliza una transacción
 * ===========================================================================
 */
package com.becoblohm.cr.sincronizador;

import org.apache.log4j.Logger;


/**
 * <pre>
 * Proyecto: CR 
 * Clase: HiloSyncTransacciones
 * </pre>
 * <p>
 * <a href="HiloSyncTransacciones.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - $Author: programa8 $
 * @version 1.0 - 04/01/2005
 * @since 04-ene-2005
 * @
 */
public class HiloSyncTransacciones implements Runnable {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(HiloSyncTransacciones.class);

	
	/**
	 * @since 04-ene-2005
	 * 
	 */
	Thread hilo;
	
	public HiloSyncTransacciones() {
		super();
		hilo = new Thread(this, "Sincronización de transacciones");
	}
	
	public void iniciar() {
		hilo.start();
	}
	
	/* (sin Javadoc)
	 * @see java.lang.Runnable#run()
	 * 
	 * @since 04-ene-2005
	 */
	public void run() {
		synchronized(this) {
			try {
				SyncCnxMonitor.getInstance().notifyIn();
				SincUtil.getInstance().syncTransacciones();
			} finally {
				SyncCnxMonitor.getInstance().notifyOut();
				notifyAll();
			}
		}
	}
	
	public static void runAndWait() {
		HiloSyncTransacciones hst = new HiloSyncTransacciones();
		synchronized(hst) {
			try {
				hst.iniciar();
				hst.wait(300000);
			} catch (InterruptedException e) {
				logger.error("runAndWait()", e);
			}
		}
	}

}
