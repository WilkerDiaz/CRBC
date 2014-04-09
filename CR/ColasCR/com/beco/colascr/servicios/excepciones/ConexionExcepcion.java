/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.madiadores
 * Programa   : ConexionExcepcion.java
 * Creado por : gmartinelli
 * Creado en  : 04/03/2004 11:12 AM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 
 * Fecha       : 
 * Analista    : 
 * Descripción : 
 * =============================================================================
 */
package com.beco.colascr.servicios.excepciones;

import org.apache.log4j.Logger;

//import com.becoblohm.cr.mediadoresbd.MediadorAuditoria;

/** 
 * Descripción: 
 * 		Esta clase maneja la excepcion de conexion con la base de datos, si esta excepcion 
 * ocurre es porque no existeel servidor de base de datos activo ypor tanto se intenta registrar
 * la auditoria temporal y no se continua con los intentos de insercion de registros de auditorias
 * de error en la base de datos.
 */

public class ConexionExcepcion extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ConexionExcepcion.class);

	private String mensaje;
	/**
	 * Constructor para ConexionExcepcion. Registra la excepcion en la auditoría temporal antres de lanzarla.
	 * Utiliza el metodo 0, modulo 0 y nivel 1 porque no se puede obtener dicha informacion 
	 * @param mensaje - Mensaje que describe la excepción presentada
	 */
	public ConexionExcepcion(String mensaje) {
		super(mensaje);
		this.mensaje = mensaje;
		logger.warn("ConexionExcepcion(mensaje = " + mensaje
				+ ") - Excepcion de Conexion Creada", this);
		
		// Registramos la auditoria del error. Auditoria tipo 'E' en el temporal
		// Tratamos de registrar el error en el temporal (Colocamos metodo y func 0, y nivel en 1)
		//MediadorAuditoria.escribirArchivoTemp(mensaje, 'E', 0, 0, 1, false);
	}

	/**
	 * Constructor para ConexionExcepcion
	 * 		Registra la excepcion en la auditoría temporal antres de lanzarla.
	 * Utiliza el metodo 0 y modulo 0 porque no se puede obtener dicha informacion 
	 * @param mensaje - Mensaje que describe la excepción presentada
	 * @param ex - Excepcion que causó la falla
	 */
	public ConexionExcepcion(String mensaje, Exception ex) {
		super(mensaje);
		this.mensaje = mensaje;
		logger.warn("ConexionExcepcion(mensaje = " + mensaje
				+ ") - Excepcion de Conexion Creada", ex);

		
		// Registramos la auditoria del error. Auditoria tipo 'E' en el temporal
		// Tratamos de registrar el error en el temporal (Colocamos metodo y func 0, y nivel en 1)
		//MediadorAuditoria.escribirArchivoTemp(mensaje, 'E', 0, 0, 1, false);
	}

	/**
	 * Método getMensaje
	 * 
	 * @return String
	 */
	public String getMensaje() {
		return mensaje;
	}

}
