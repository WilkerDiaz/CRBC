/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.colascr.transferencias.excepciones
 * Programa   : ConexionExcepcion.java
 * Creado por : Ileana Rojas, Gabriel Martinelli
 * Creado en  : 21/02/2005 02:44:59 PM
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
 * 		Esta clase maneja la excepcion de conexion con la base de datos, si esta excepcion 
 * ocurre es porque no existe el servidor de base de datos activo
 */

public class ConexionExcepcion extends ExcepcionSinc {
	private String mensaje;
	@SuppressWarnings("unused")
	private Exception excepcion;

	/**
	 * Constructor para ConexionExcepcion. 
	 * @param mensaje - Mensaje que describe la excepci�n presentada
	 */
	public ConexionExcepcion(String mensaje) {
		super(mensaje);
		this.mensaje = mensaje;
		this.excepcion = null;
	}

	/**
	 * Constructor para ConexionExcepcion
	 * @param mensaje - Mensaje que describe la excepci�n presentada
	 * @param ex - Excepcion que caus� la falla
	 */
	public ConexionExcepcion(String mensaje, Exception ex) {
		super(mensaje);
		this.mensaje = mensaje;
		this.excepcion = ex;
	}

	/**
	 * M�todo getMensaje
	 * 
	 * @return String
	 */
	public String getMensaje() {
		return mensaje;
	}

}
