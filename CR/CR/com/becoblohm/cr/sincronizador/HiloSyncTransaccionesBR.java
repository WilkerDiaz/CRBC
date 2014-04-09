/*
 * $Id: HiloSyncTransacciones.java,v 1.2.2.2 2005/04/21 21:54:25 programa8 Exp $
 * ===========================================================================
 * CENTROBECO, C.A.
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.sincronizador
 * Programa		: HiloSyncTransaccionesBR.java
 * Creado por	: JGraterol
 * Creado en 	: 21-ene-2011 13:29:48
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ===========================================================================
 */
package com.becoblohm.cr.sincronizador;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;


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
public class HiloSyncTransaccionesBR implements Runnable {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(HiloSyncTransaccionesBR.class);

	
	/**
	 * @since 04-ene-2005
	 * 
	 */
	Thread hilo;
	
	public HiloSyncTransaccionesBR() {
		super();
		hilo = new Thread(this, "Sincronización de transacciones de Bono Regalo");
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
				try {
					SincUtil.getInstance().syncTransaccionesBR();
				} catch (BaseDeDatosExcepcion e) {
					logger.error(e);
				} catch (SQLException e) {
					logger.error(e);
				}
			} finally {
				SyncCnxMonitor.getInstance().notifyOut();
				notifyAll();
			}
		}
	}
	
	public static void runAndWait() {
		HiloSyncTransaccionesBR hst = new HiloSyncTransaccionesBR();
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
