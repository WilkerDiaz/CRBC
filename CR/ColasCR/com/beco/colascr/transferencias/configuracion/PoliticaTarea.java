/**
 * =============================================================================
 * Proyecto   : Colas-ServTienda-ServCentral
 * Paquete    : com.beco.colascr.transferencias.configuracion
 * Programa   : PoliticaTarea.java
 * Creado por : gmartinelli
 * Creado en  : 18-may-05 14:43:54
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versi�n     : 1.1
 * Fecha       : 18-may-05 14:43:54
 * Analista    : gmartinelli
 * Descripci�n : Implementaci�n inicial.
 * =============================================================================
 */
package com.beco.colascr.transferencias.configuracion; 

import java.util.Date;

import com.beco.colascr.transferencias.ServidorCR;

/**
 * Descripci�n:
 * 
 */

public class PoliticaTarea {
	
	public static final int DIARIO = 1; 
	public static final int POR_TIEMPO = 2; 
	public static final int AL_INICIAR = 0;
	public static final int INICIADA = 1;
	public static final int FINALIZANDO = 2;
	public static final int FINALIZADA = 3;
	
	private String nombreTarea = "";
	private int tipoTarea = 0;
	private int valorEjecucionTarea = 0;
	private Date ultimaSincronizacion = null;
	private Date proximaSincronizacion = null;
	private int estadoTarea = PoliticaTarea.FINALIZADA;
	private double duracionSinc = 0;
	
	PoliticaTarea(String nombTarea) {
		this.nombreTarea = nombTarea;
		this.tipoTarea = PoliticaTarea.AL_INICIAR;
		this.valorEjecucionTarea = 0;
	}

	PoliticaTarea(String nombTarea, String tipo, String hora) {
		this.nombreTarea = nombTarea;
		this.tipoTarea = Integer.parseInt(tipo);
		this.valorEjecucionTarea = Integer.parseInt(hora);
	}

	PoliticaTarea(String nombTarea, Date proximaSin, Date ultimaSin, double durSin) {
		this.nombreTarea = nombTarea;
		this.tipoTarea = PoliticaTarea.AL_INICIAR;
		this.valorEjecucionTarea = 0;
		this.ultimaSincronizacion = ultimaSin;
		this.proximaSincronizacion = proximaSin;
		this.duracionSinc = durSin;
	}

	PoliticaTarea(String nombTarea, String tipo, String hora, Date proximaSin, Date ultimaSin, double durSin) {
		this.nombreTarea = nombTarea;
		this.tipoTarea = Integer.parseInt(tipo);
		this.valorEjecucionTarea = Integer.parseInt(hora);
		this.ultimaSincronizacion = ultimaSin;
		this.proximaSincronizacion = proximaSin;
		this.duracionSinc = durSin;
	}

	/**
	 * M�todo getTipoTarea
	 * 
	 * @return
	 * int
	 */
	public int getTipoTarea() {
		return tipoTarea;
	}

	/**
	 * M�todo getValorEjecucionTarea
	 * 
	 * @return
	 * int
	 */
	public int getValorEjecucionTarea() {
		return valorEjecucionTarea;
	}

	/**
	 * M�todo setTipoTarea
	 * 
	 * @param i
	 * void
	 */
	public void setTipoTarea(int i) {
		tipoTarea = i;
	}

	/**
	 * M�todo setValorEjecucionTarea
	 * 
	 * @param i
	 * void
	 */
	public void setValorEjecucionTarea(int i) {
		valorEjecucionTarea = i;
	}

	/**
	 * M�todo getNombreTarea
	 * 
	 * @return
	 * String
	 */
	public String getNombreTarea() {
		return nombreTarea;
	}

	/**
	 * M�todo setNombreTarea
	 * 
	 * @param string
	 * void
	 */
	public void setNombreTarea(String string) {
		nombreTarea = string;
	}

	/**
	 * M�todo getProximaSincronizacion
	 * 
	 * @return
	 * Date
	 */
	public Date getProximaSincronizacion() {
		return proximaSincronizacion;
	}

	/**
	 * M�todo getUltimaSincronizacion
	 * 
	 * @return
	 * Date
	 */
	public Date getUltimaSincronizacion() {
		return ultimaSincronizacion;
	}

	/**
	 * M�todo setProximaSincronizacion
	 * 
	 * @param date
	 * void
	 */
	public void setProximaSincronizacion(Date date) {
		proximaSincronizacion = date;
		this.setEstadoTarea(PoliticaTarea.FINALIZADA);
	}

	/**
	 * M�todo setUltimaSincronizacion
	 * 
	 * @param date
	 * void
	 */
	public void setUltimaSincronizacion(Date date) {
		ultimaSincronizacion = date;
	}

	/**
	 * M�todo getEstadoTarea
	 * 
	 * @return
	 * int
	 */
	public int getEstadoTarea() {
		return estadoTarea;
	}

	/**
	 * M�todo setEstadoTarea
	 * 
	 * @param i
	 * void
	 */
	public void setEstadoTarea(int i) {
		estadoTarea = i;
		ServidorCR.sincronizador.actualizarMensajesDetalles();
	}

	/**
	 * M�todo getDuracionSinc
	 * 
	 * @return
	 * double
	 */
	public double getDuracionSinc() {
		return duracionSinc;
	}

	/**
	 * M�todo setDuracionSinc
	 * 
	 * @param d
	 * void
	 */
	public void setDuracionSinc(double d) {
		duracionSinc = d;
	}

}
