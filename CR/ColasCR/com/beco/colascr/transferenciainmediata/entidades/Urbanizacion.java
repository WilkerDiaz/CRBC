/**
 * =============================================================================
 * Proyecto   : TransferenciaInmediataProdsCR
 * Paquete    : com.beco.colascr.transferenciainmediata.entidades
 * Programa   : Urbanizacion.java
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

public class Urbanizacion {

	private int codEstado;
	private int codCiudad;
	private int codUrbanizacion;
	private String descripcion;
	private String zonaPostal;
	private String statusEliminacion;
	private String usuarioEliminacion;
	/**
	 * Constructor para Promocion.java
	 *
	 * 
	 */
	public Urbanizacion(int codEdo, int codCiu, int codUrb, String desUrb, String zonPos, String staEli, String usrEli) {
		this.codEstado = codEdo;
		this.codCiudad = codCiu;
		this.codUrbanizacion = codUrb;
		this.descripcion = "'" + desUrb.trim() + "'";
		this.zonaPostal = "'" + zonPos + "'";
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
	public int getCodUrbanizacion() {
		return codUrbanizacion;
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

	/**
	 * @return
	 */
	public String getZonaPostal() {
		return zonaPostal;
	}

}
