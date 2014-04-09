/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.excepciones
 * Programa   : ExcepcionCr.java
 * Creado por : Programador3 - Alexis Gu�dez L�pez
 * Creado en  : 20/10/2003 04:49:19 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versi�n     : 1.1.2
 * Fecha       : 24/03/2004 14:00 PM
 * Analista    : gmartinelli
 * Descripci�n : Agregado Constructor de Excepciones que registra auditorias sin
 * 				buscar los datos de la funcion en la BaseDeDatos, colocando 0 en
 * 				los codigos de las funciones y metodos
 * =============================================================================
 * Versi�n     : 1.1.1
 * Fecha       : 02/03/2004 13:15 PM
 * Analista    : gmartinelli
 * Descripci�n : Constructores de ExcepcionCr modificados para que registren la auditoria
 * =============================================================================
 * Versi�n     : 1.1
 * Fecha       : 02/03/2004 11:02 AM
 * Analista    : gmartinelli
 * Descripci�n : Eliminado constructor 
 * 				ExcepcionCr(String mensaje, Exception ex, int codModulo, int codFuncion)
 * 				Ahora el modulo de auditoria es el encargado de buscar el codigo de la funcion
 * 				y del modulo donde se genera el registro de auditoria de tipo 'E'
 * =============================================================================
 * Versi�n     : 1.0.2
 * Fecha       : 27/11/2003 10:35:41 AM
 * Analista    : Programador3 - Alexis Gu�dez L�pez
 * Descripci�n : Actualizaci�n por modificaciones de gmartinelli, por requerimientos 
 * 			 	 del m�dulo de Auditor�a.
 * 				 Constructor agregado ->
 * 					ExcepcionCr(String mensaje, Exception ex, int codModulo, int codFuncion)
 * =============================================================================
 * Versi�n     : 1.0.1
 * Fecha       : 31/10/2003 09:28:41 AM
 * Analista    : Programador3 - Alexis Gu�dez L�pez
 * Descripci�n : Integraci�n de la clase ExcepcionCr para EPA y BECO
 * =============================================================================
 */
package com.becoblohm.cr.excepciones;

import org.apache.log4j.Logger;

import com.becoblohm.cr.manejadorauditoria.Auditoria;

/** 
 * Descripci�n: 
 * 		Esta clase establece cada una de las excepciones arrojadas por el 
 * sistema seg�n los m�todos que se empleen. Cada excepcion arrojada se encarga
 * de realizar un registro en la auditoria que describa el error (auditoria de tipo 'E')
 * presentado en el sistema.
 */

public class ExcepcionCr extends Exception {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ExcepcionCr.class);

	private String mensaje;
	private Exception excepcion;

	/**
	 * @param mensaje
	 * @param esBuscandoCodigoModulo
	 */
	public ExcepcionCr(String mensaje, boolean esBuscandoCodigoModulo) {
		super(mensaje);
		this.mensaje = mensaje;
		this.excepcion = null;

		logger.warn("ExcepcionCr(mensaje = " + mensaje
				+ ") - Excepcion CR creada", this);

		if (!esBuscandoCodigoModulo)
			Auditoria.registrarAuditoria(mensaje,'E');
	}

	/**
	 * @param mensaje
	 * @param ex
	 * @param esBuscandoCodigoModulo
	 */
	public ExcepcionCr(String mensaje, Exception ex, boolean esBuscandoCodigoModulo) {
		super(mensaje);
		this.mensaje = mensaje;
		this.excepcion = ex;

		logger.warn("ExcepcionCr(mensaje = " + mensaje
				+ ") - Excepcion CR creada", ex);

		if (!esBuscandoCodigoModulo)
				Auditoria.registrarAuditoria(mensaje,'E');
	}

	/**
	 * Constructor para ExcepcionCr. 
	 * 		Registra la excepcion en la auditor�a antres de lanzarla
	 * @param mensaje - Mensaje que describe la excepci�n presentada
	 */
	public ExcepcionCr(String mensaje) {
		super(mensaje);
		this.mensaje = mensaje;
		this.excepcion = null;

		logger.warn("ExcepcionCr(mensaje = " + mensaje
				+ ") - Excepcion CR creada", this);

		
		Auditoria.registrarAuditoria(mensaje,'E');
	}

	/**
	 * Constructor para ExcepcionCr.
	 * 		Registra la excepcion en la auditor�a antres de lanzarla
	 * @param mensaje - Mensaje que describe la excepci�n presentada
	 * @param ex - Excepcion que caus� la falla
	 */
	public ExcepcionCr(String mensaje, Exception ex) {
		super(mensaje);
		this.mensaje = mensaje;
		this.excepcion = ex;

		logger.warn("ExcepcionCr(mensaje = " + mensaje
				+ ") - Excepcion CR creada", ex);

		
		// Registramos la auditoria del error. Auditoria tipo 'E'
		Auditoria.registrarAuditoria(mensaje,'E');
	}

	/**
	 * M�todo getExcepcion.
	 * @return Exception - Excepcion arrojada por el sistema
	 */
	public Exception getExcepcion() {
		if (logger.isDebugEnabled()) {
			logger.debug("getExcepcion() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getExcepcion() - end");
		}
		return excepcion;
	}

	/**
	 * M�todo getMensaje.
	 * @return String - Descripcion de la excepci�n arrojada
	 */
	public String getMensaje() {
		if (logger.isDebugEnabled()) {
			logger.debug("getMensaje() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getMensaje() - end");
		}
		return mensaje;
	}

	/**
	 * M�todo setMensaje.
	 * @param string
	 */
	public void setMensaje(String string) {
		if (logger.isDebugEnabled()) {
			logger.debug("setMensaje(String) - start");
		}

		mensaje = string;

		if (logger.isDebugEnabled()) {
			logger.debug("setMensaje(String) - end");
		}
	}

}
