/*
 * $Id: SyncCnxMonitor.java,v 1.1.2.1 2005/03/17 21:41:56 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.sincronizador
 * Programa		: SyncCnxMonitor.java
 * Creado por	: Programa8
 * Creado en 	: 17-mar-2005 13:59:13
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
 * $Log: SyncCnxMonitor.java,v $
 * Revision 1.1.2.1  2005/03/17 21:41:56  programa8
 * Ajuste de sincronización de hilos de sincronizacion de datos,
 * los cuales se apagaban entre si la conexion que el otro usaba
 *
 * ===========================================================================
 */
package com.becoblohm.cr.sincronizador;

import org.apache.log4j.Logger;

import com.becoblohm.cr.mediadoresbd.Conexiones;

/**
 * <pre>
 * Proyecto: CR 
 * Clase: SyncCnxMonitor
 * </pre>
 * <p>
 * <a href="SyncCnxMonitor.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - Arístides Castillo
 * @version 1.0 - 17/03/2005
 * @since 17-mar-2005
 * @
 */
public class SyncCnxMonitor {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SyncCnxMonitor.class);

	/**
	 * @since 17-mar-2005
	 * 
	 */
	
	// Instancia singleton
	private static SyncCnxMonitor instance = null;
	private int uso = 0;
	
	protected SyncCnxMonitor() {
		super();
	}
	
	public static SyncCnxMonitor getInstance() {
		if (logger.isDebugEnabled()) {
			logger.debug("getInstance() - start");
		}

		if (instance == null) {
			instance = new SyncCnxMonitor();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getInstance() - end");
		}
		return instance;
	}
	
	public synchronized void notifyIn() {
		if (logger.isDebugEnabled()) {
			logger.debug("notifyIn() - start");
		}

		uso++;

		if (logger.isDebugEnabled()) {
			logger.debug("notifyIn() - end");
		}
	}

	public synchronized void notifyOut() {
		if (logger.isDebugEnabled()) {
			logger.debug("notifyOut() - start");
		}
		if (uso > 0) {
			uso--;
			if (uso == 0) {
				try {
					Conexiones.cerrarConexionesSync();
				} catch (RuntimeException e) {
					logger.error("notifyOut()", e);
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("notifyOut() - end");
		}
	}
	
}
