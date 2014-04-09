/**
 * =============================================================================
 * Proyecto   : TransferenciaInmediataProdsCR
 * Paquete    : com.beco.colascr.transferenciainmediata.entidades
 * Programa   : Promocion.java
 * Creado por : gmartinelli
 * Creado en  : 28-jun-05 10:15:40
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1
 * Fecha       : 28-jun-05 10:15:40
 * Analista    : gmartinelli
 * Descripción : Implementación inicial.
 * =============================================================================
 */
package com.beco.colascr.transferenciainmediata.entidades; 

import java.text.SimpleDateFormat;

/**
 * Descripción:
 * 
 */

public class Promocion {

	private int codPromocion;
	private String tipoPromocion;
	private String fechaInicio;
	private String horaInicio;
	private String fechaFinal;
	private String horaFinal;
	private String prioridad;
	@SuppressWarnings("unused")
	private SimpleDateFormat dfFecha = new SimpleDateFormat("yyyy-MM-dd");
	@SuppressWarnings("unused")
	private SimpleDateFormat dfHora = new SimpleDateFormat("HH:mm:ss");  
	/**
	 * Constructor para Promocion.java
	 *
	 * 
	 */
	public Promocion(int codProm, String tipoProm, String fechaIni, String horaIni, String fechaFin, String horaFin, String prioridad) {
		this.codPromocion = codProm;
		this.tipoPromocion = "'" + tipoProm.trim() + "'";
		this.fechaInicio = "'" + fechaIni.trim() + "'";
		this.horaInicio = "'" + horaIni.trim() + "'";
		this.fechaFinal = "'" + fechaFin.trim() + "'";
		this.horaFinal = "'" + horaFin.trim() + "'";
		this.prioridad = "'" + prioridad.trim() + "'";
	}

	/**
	 * Método getCodPromocion
	 * 
	 * @return
	 * int
	 */
	public int getCodPromocion() {
		return codPromocion;
	}

	/**
	 * Método getFechaFinal
	 * 
	 * @return
	 * Date
	 */
	public String getFechaFinal() {
		return fechaFinal;
	}

	/**
	 * Método getFechaInicio
	 * 
	 * @return
	 * Date
	 */
	public String getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * Método getHoraFinal
	 * 
	 * @return
	 * Time
	 */
	public String getHoraFinal() {
		return horaFinal;
	}

	/**
	 * Método getHoraInicio
	 * 
	 * @return
	 * Time
	 */
	public String getHoraInicio() {
		return horaInicio;
	}

	/**
	 * Método getPrioridad
	 * 
	 * @return
	 * String
	 */
	public String getPrioridad() {
		return prioridad;
	}

	/**
	 * Método getTipoPromocion
	 * 
	 * @return
	 * String
	 */
	public String getTipoPromocion() {
		return tipoPromocion;
	}

}
