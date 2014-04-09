/*
 * Creado el 11-nov-2004
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.beco.colascr.servicios.excepciones;

//import com.becoblohm.cr.manejadorsesion.Sesion;
//import com.becoblohm.cr.mediadoresbd.Conexiones;

/**
 * @author Programa8
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class ConexionServidorExcepcion extends ConexionExcepcion {

	private static final long serialVersionUID = 1L;

	/**
	 * @param mensaje
	 */
	public ConexionServidorExcepcion(String mensaje) {
		super(mensaje, null);
	}

	/**
	 * @param mensaje
	 * @param ex
	 */
	public ConexionServidorExcepcion(String mensaje, Exception ex) {
		super(mensaje, ex);
//		Sesion.setCajaEnLinea(false);
//		Conexiones.setConectadoServidor(false);
	}

}
