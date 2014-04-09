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
 * Versi�n     : 
 * Fecha       : 
 * Analista    : 
 * Descripci�n :
 * =============================================================================
 */
package com.beco.colascr.transferencias.excepciones;


/** 
 * Descripci�n: 
 * 		Esta clase establece cada una de las excepciones arrojadas por el 
 * sistema seg�n los m�todos que se empleen.
 */

public class ExcepcionSinc extends Exception {
	private String mensaje;
	private Exception excepcion;

	/**
	 * Constructor para ExcepcionSinc. 
	 * @param mensaje - Mensaje que describe la excepci�n presentada
	 */
	public ExcepcionSinc(String mensaje) {
		super(mensaje);
		this.mensaje = mensaje;
		this.excepcion = null;
	}

	/**
	 * Constructor para ExcepcionSinc.
	 * @param mensaje - Mensaje que describe la excepci�n presentada
	 * @param ex - Excepcion que caus� la falla
	 */
	public ExcepcionSinc(String mensaje, Exception ex) {
		super(mensaje);
		this.mensaje = mensaje;
		this.excepcion = ex;
	}

	/**
	 * M�todo getExcepcion.
	 * @return Exception - Excepcion arrojada por el sistema
	 */
	public Exception getExcepcion() {
		return excepcion;
	}

	/**
	 * M�todo getMensaje.
	 * @return String - Descripcion de la excepci�n arrojada
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * M�todo setMensaje.
	 * @param string
	 */
	public void setMensaje(String string) {
		mensaje = string;
	}

}
