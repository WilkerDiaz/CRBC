/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.colascr.transferencias.excepciones
 * Programa   : ExcepcionSinc.java
 * Creado por : Ileana Rojas, Gabriel Martinelli
 * Creado en  : 21/02/2005 02:40:59 PM
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
package com.beco.colascr.transferencias.excepciones;


/** 
 * Descripción: 
 * 		Esta clase establece cada una de las excepciones arrojadas por el 
 * sistema según los métodos que se empleen.
 */

public class ExcepcionSinc extends Exception {
	private String mensaje;
	private Exception excepcion;

	/**
	 * Constructor para ExcepcionSinc. 
	 * @param mensaje - Mensaje que describe la excepción presentada
	 */
	public ExcepcionSinc(String mensaje) {
		super(mensaje);
		this.mensaje = mensaje;
		this.excepcion = null;
	}

	/**
	 * Constructor para ExcepcionSinc.
	 * @param mensaje - Mensaje que describe la excepción presentada
	 * @param ex - Excepcion que causó la falla
	 */
	public ExcepcionSinc(String mensaje, Exception ex) {
		super(mensaje);
		this.mensaje = mensaje;
		this.excepcion = ex;
	}

	/**
	 * Método getExcepcion.
	 * @return Exception - Excepcion arrojada por el sistema
	 */
	public Exception getExcepcion() {
		return excepcion;
	}

	/**
	 * Método getMensaje.
	 * @return String - Descripcion de la excepción arrojada
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * Método setMensaje.
	 * @param string
	 */
	public void setMensaje(String string) {
		mensaje = string;
	}

}
