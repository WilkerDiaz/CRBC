/*
 * Creado el 11-nov-2004
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generaci�n de c�digo&gt;C�digo y comentarios
 */
package com.beco.colascr.servicios.excepciones;

/**
 * @author Programa8
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generaci�n de c�digo&gt;C�digo y comentarios
 */
public class ConexionLocalExcepcion extends ConexionExcepcion {

	private static final long serialVersionUID = 1L;

	public ConexionLocalExcepcion(String msg) {
		super(msg);
	}
	
	public ConexionLocalExcepcion(String msg, Exception e) {
		super(msg, e);
	}
}
