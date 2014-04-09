/*
 * Creado el 12-nov-2004
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.becoblohm.cr.extensiones.impl;

import java.sql.Timestamp;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.becoblohm.cr.extensiones.TimeProxy;

/**
 * Implementación por defecto del time proxy.
 * Retorna la fecha y hora de la máquina local
 * @author Programa8
 */
public class DefaultTimeProxy implements TimeProxy {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(DefaultTimeProxy.class);

	/**
	 * Constructor de la clase
	 */
	public DefaultTimeProxy() {
		super();
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.utiles.timeproxy.TimeProxy#getTimestamp()
	 */
	public Timestamp getTimestamp() {
		if (logger.isDebugEnabled()) {
			logger.debug("getTimestamp() - start");
		}

		Timestamp tiempo = null;
		tiempo = new Timestamp(Calendar.getInstance().getTime().getTime());

		if (logger.isDebugEnabled()) {
			logger.debug("getTimestamp() - end");
		}
		return tiempo;		
	}

}
