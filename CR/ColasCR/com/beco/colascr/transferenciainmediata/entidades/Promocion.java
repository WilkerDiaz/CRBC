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
 * Versi�n     : 1.1
 * Fecha       : 28-jun-05 10:15:40
 * Analista    : gmartinelli
 * Descripci�n : Implementaci�n inicial.
 * =============================================================================
 */
package com.beco.colascr.transferenciainmediata.entidades; 

import java.text.SimpleDateFormat;

/**
 * Descripci�n:
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
	 * M�todo getCodPromocion
	 * 
	 * @return
	 * int
	 */
	public int getCodPromocion() {
		return codPromocion;
	}

	/**
	 * M�todo getFechaFinal
	 * 
	 * @return
	 * Date
	 */
	public String getFechaFinal() {
		return fechaFinal;
	}

	/**
	 * M�todo getFechaInicio
	 * 
	 * @return
	 * Date
	 */
	public String getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * M�todo getHoraFinal
	 * 
	 * @return
	 * Time
	 */
	public String getHoraFinal() {
		return horaFinal;
	}

	/**
	 * M�todo getHoraInicio
	 * 
	 * @return
	 * Time
	 */
	public String getHoraInicio() {
		return horaInicio;
	}

	/**
	 * M�todo getPrioridad
	 * 
	 * @return
	 * String
	 */
	public String getPrioridad() {
		return prioridad;
	}

	/**
	 * M�todo getTipoPromocion
	 * 
	 * @return
	 * String
	 */
	public String getTipoPromocion() {
		return tipoPromocion;
	}

}
