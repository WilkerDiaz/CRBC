/**
 * =============================================================================
 * Proyecto   : TransferenciaInmediataProdsCR
 * Paquete    : com.beco.colascr.transferenciainmediata.entidades
 * Programa   : Estado.java
 * Creado por : gmartinelli
 * Creado en  : 06-sep-06 16:15:40
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1
 * Fecha       : 06-sep-06 16:15:40
 * Analista    : gmartinelli
 * Descripción : Implementación inicial.
 * =============================================================================
 */
package com.beco.colascr.transferenciainmediata.entidades; 

/**
 * Descripción:
 * 
 */

public class Estado {

	private int codEstado;
	private String descripcion;
	private String statusEliminacion;
	private String usuarioEliminacion;
	/**
	 * Constructor para Promocion.java
	 *
	 * 
	 */
	public Estado(int codEdo, String desEdo, String staEli, String usrEli) {
		this.codEstado = codEdo;
		this.descripcion = "'" + desEdo.trim() + "'";
		this.statusEliminacion= "'" + staEli.trim() + "'";
		this.usuarioEliminacion = "'" + usrEli.trim() + "'";
	}
	/**
	 * @return
	 */
	public int getCodEstado() {
		return codEstado;
	}

	/**
	 * @return
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @return
	 */
	public String getStatusEliminacion() {
		return statusEliminacion;
	}

	/**
	 * @return
	 */
	public String getUsuarioEliminacion() {
		return usuarioEliminacion;
	}

}
