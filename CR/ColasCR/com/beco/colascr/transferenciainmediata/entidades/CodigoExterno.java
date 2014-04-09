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
 * Versi�n     : 1.1
 * Fecha       : 28-jun-05 10:15:51
 * Analista    : gmartinelli
 * Descripci�n : Implementaci�n inicial.
 * =============================================================================
 */
package com.beco.colascr.transferenciainmediata.entidades; 


/**
 * Descripci�n:
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
	* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
	* Se elimino variable sin uso
	* Fecha: agosto 2011
	*/
	public CodigoExterno(String codProd, String codExt/*, Timestamp act*/) {
		this.codProducto = "'" + codProd + "'";
		this.codExterno = "'" + codExt + "'";
//		this.actualizacion = df.format(act);
	}

	/**
	 * M�todo getActualizacion
	 * 
	 * @return
	 * Timestamp
	 */
/*	public String getActualizacion() {
		return actualizacion;
	}

	/**
	 * M�todo getCodExterno
	 * 
	 * @return
	 * String
	 */
	public String getCodExterno() {
		return codExterno;
	}

	/**
	 * M�todo getCodProducto
	 * 
	 * @return
	 * String
	 */
	public String getCodProducto() {
		return codProducto;
	}

}
