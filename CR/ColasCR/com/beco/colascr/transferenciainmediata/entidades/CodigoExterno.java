/**
 * =============================================================================
 * Proyecto   : TransferenciaInmediataProdsCR
 * Paquete    : com.beco.colascr.transferenciainmediata.entidades
 * Programa   : CodigoExterno.java
 * Creado por : gmartinelli
 * Creado en  : 28-jun-05 10:15:51
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1
 * Fecha       : 28-jun-05 10:15:51
 * Analista    : gmartinelli
 * Descripción : Implementación inicial.
 * =============================================================================
 */
package com.beco.colascr.transferenciainmediata.entidades; 


/**
 * Descripción:
 * 
 */

public class CodigoExterno {

	private String codProducto;
	private String codExterno;
//	private String actualizacion;
	/**
	 * Constructor para CodigoExterno.java
	 *
	 * 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Se elimino variable sin uso
	* Fecha: agosto 2011
	*/
	public CodigoExterno(String codProd, String codExt/*, Timestamp act*/) {
		this.codProducto = "'" + codProd + "'";
		this.codExterno = "'" + codExt + "'";
//		this.actualizacion = df.format(act);
	}

	/**
	 * Método getActualizacion
	 * 
	 * @return
	 * Timestamp
	 */
/*	public String getActualizacion() {
		return actualizacion;
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
	 * Método getCodProducto
	 * 
	 * @return
	 * String
	 */
	public String getCodProducto() {
		return codProducto;
	}

}
