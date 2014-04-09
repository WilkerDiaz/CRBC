/*
 * Creado el 29/10/2004
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.becoblohm.cr.mediadoresbd;

import java.text.DateFormat;
import java.util.Date;

/**
 * @author Programa8
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class LogEvent {
	private String metodo;
	private Date tiempoInicio;
	private Date tiempoFin;
	private String mensaje;
	private String tarea = "Tarea";
	private boolean cronometro = false;
	private DateFormat df =  DateFormat.getTimeInstance(DateFormat.LONG);
	
	/**
	 * @return Returns the mensaje.
	 */
	public String getMensaje() {
		return mensaje;
	}
	/**
	 * @param mensaje The mensaje to set.
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	/**
	 * @return Returns the metodo.
	 */
	public String getMetodo() {
		return metodo;
	}
	/**
	 * @param metodo The metodo to set.
	 */
	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}
	/**
	 * @return Returns the tiempoFin.
	 */
	public String getTiempoFin() {
		return df.format(tiempoFin);
	}
	/**
	 * @return Returns the tiempoInicio.
	 */
	public String getTiempoInicio() {
		return df.format(tiempoInicio);
	}
	/**
	 * @param cronometro The cronometro to set.
	 */
	public void setCronometro(boolean cronometro) {
		this.cronometro = cronometro;
		if (cronometro) {
			tiempoInicio = new Date();
			tiempoFin = null;
		} else {
			tiempoFin = new Date();
		}
	}
	
	public String logCronometro(){
		String msg = "No se ha iniciado ninguna tarea";
		if (tiempoInicio != null) {
			if (cronometro) {
				msg = "Tarea " + tarea + " en ejecucion. Duración: " + getDuracion() + " segundos";
			} else {
				msg = "Tarea " + tarea + " finalizada. Duración: " + getDuracion() + " segundos";
			}
		}
		setMensaje(msg);
		return this.toString();
	}
	
	public LogEvent setEvent(String mensaje) {
		setMensaje(mensaje);
		return this;
	}
	
	public String toString() {
		String result = metodo + " - " + mensaje + "";
		return result;
	}
	
	public String iniciaTarea() {
		setCronometro(true);
		setMensaje("Iniciada tarea " + tarea);
		return getTiempoInicio() + " " +  this.toString() ;
	}
	
	public String iniciaTarea(String tarea) {
		setTarea(tarea);
		return iniciaTarea();
	}
	
	public int getDuracion() {
		int segundos = 0;
		if (cronometro) {
			segundos = (int)((new Date().getTime() - tiempoInicio.getTime()) / 1000);			
		} else if ((tiempoFin != null) && (tiempoInicio != null)){
			segundos = (int)((tiempoFin.getTime() - tiempoInicio.getTime()) / 1000);			
		}
		return segundos;
	}
	
	public String finalizaTarea() {
		setCronometro(false);
		setMensaje("Finalizada tarea " + tarea + ". Duracion: " + getDuracion() + " segundos");
		return getTiempoFin() + " " + this.toString();
	} 
	/**
	 * @return Returns the tarea.
	 */
	public String getTarea() {
		return tarea;
	}
	/**
	 * @param tarea The tarea to set.
	 */
	public void setTarea(String tarea) {
		this.tarea = tarea;
	}
}
