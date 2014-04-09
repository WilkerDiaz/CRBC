/**
 * =============================================================================
 * Proyecto   : TransferenciaInmediataProdsCR
 * Paquete    : com.beco.colascr.transferenciainmediata.entidades
 * Programa   : Ciudad.java
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

public class Ciudad {

	private int codEstado;
	private int codCiudad;
	private String descripcion;
	private int codigoArea;
	private String statusEliminacion;
	private String usuarioEliminacion;
	/**
	 * Constructor para Promocion.java
	 *
	 * 
	 */
	public Ciudad(int codEdo, int codCiu, String desCiu, int codArea, String staEli, String usrEli) {
		this.codEstado = codEdo;
		this.codCiudad = codCiu;
		this.descripcion = "'" + desCiu.trim() + "'";
		this.codigoArea = codArea;
		this.statusEliminacion= "'" + staEli.trim() + "'";
		this.usuarioEliminacion = "'" + usrEli.trim() + "'";
	}
	/**
	 * @return
	 */
	public int getCodCiudad() {
		return codCiudad;
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
	public int getCodigoArea() {
		return codigoArea;
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
