/*
 * Creado el 16-nov-2004
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.becoblohm.cr.extensiones;

import org.apache.log4j.Logger;

/**
 * Excepción para los casos en los que el servicio de fecha y tiempo no está
 * disponible. Al ser una excepción de tiempo de ejecucion, el compilador
 * no exige la presencia del bloque try..catch
 * 
 * @author Programa8 - Ar&iacute;stides Castillo
 *
 */
public class NotAvailableTimeServiceException extends RuntimeException {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(NotAvailableTimeServiceException.class);

	/**
	 * 
	 */
	public NotAvailableTimeServiceException() {
		super();

		logger
				.warn(
						"NotAvailableTimeServiceException() - TimeServiceException lanzada",
						this);
	}

	/**
	 * @param message
	 */
	public NotAvailableTimeServiceException(String message) {
		super(message);

		logger.warn("NotAvailableTimeServiceException(message = " + message
				+ ") - TimeServiceException lanzada", this);
	}

	/**
	 * @param cause
	 */
	public NotAvailableTimeServiceException(Throwable cause) {
		super(cause);
		logger
		.warn(
				"NotAvailableTimeServiceException() - TimeServiceException lanzada",
				cause);
		
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NotAvailableTimeServiceException(String message, Throwable cause) {
		super(message, cause);
		logger.warn("NotAvailableTimeServiceException(message = " + message
				+ ") - TimeServiceException lanzada", cause);
		
	}

}
