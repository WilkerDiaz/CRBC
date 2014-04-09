/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.manejarpago
 * Programa   : Banco.java
 * Creado por : gmartinelli
 * Creado en  : 31-may-04 07:56:00
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
package com.becoblohm.cr.extensiones.impl.manejarpago;


/** 
 * Descripción: 
 * 		Maneja las instancias de los bancos existentes en la basede datos
 */
public class Banco {
	
	private String codBanco;
	private String codExterno;
	private String nombre;

	/**
	 * Constructos de la clase
	 * @param codBco Codigo del banco.
	 * @param codExt Codigo Externo.
	 * @param nomb nombre del baco.
	 */
	public Banco(String codBco, String codExt, String nomb) {
		this.codBanco = codBco;
		this.codExterno = codExt;
		this.nombre = nomb;
	}
	
	/**
	 * Método getCodBanco
	 * 
	 * @return
	 * String
	 */
	public String getCodBanco() {
		return codBanco;
	}

	/**
	 * Método getCodExterno
	 * 
	 * @return
	 * String
	 */
	public String getCodExterno() {
		return codExterno;
	}

	/**
	 * Método getNombre
	 * 
	 * @return
	 * String
	 */
	public String getNombre() {
		return nombre;
	}
	
	public String toString() {
		return this.getNombre();
	}

}