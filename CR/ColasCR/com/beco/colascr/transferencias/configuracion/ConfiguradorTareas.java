/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.colascr.transferencias.configuracion
 * Programa   : ConfiguradorTareas.java
 * Creado por : Gabriel Martinelli
 * Creado en  : 20/05/2005 07:54:00 AM
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
package com.beco.colascr.transferencias.configuracion;

import java.util.Date;

import com.beco.colascr.transferencias.ServidorCR;

/**
 *  Genera un hilo (Thread) que se encarga de configurar las tareas de sincronización una vez 
 * que sean ejecutadas.
 * 
 */
public class ConfiguradorTareas implements Runnable {

	public ConfiguradorTareas () {
		iniciar();
	}

	/* (no Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		while(true) {
			Date proximaEjecucion = null;
			
			try {
				//Chequeamos Tarea de Entidades Bases
				if (Sesion.getFrecuenciaSincBases().getEstadoTarea()==PoliticaTarea.FINALIZANDO) {
					proximaEjecucion = ServidorCR.sincronizador.configurarTareaProgramada(Sesion.getFrecuenciaSincBases());
					ServidorCR.sincronizador.actualizarMensajesDetalles();
					ServidorCR.sincronizador.planificarBases(proximaEjecucion);
//					ServidorCR.sincronizador.getPlanificadorBases().schedule(ServidorCR.sincronizador.getSincServCentServTdaBases(), proximaEjecucion);
				}
			}catch (Exception e) {e.printStackTrace();}

			try {
				//Chequeamos Tarea de Afiliados
				if (Sesion.getFrecuenciaSincAfiliados().getEstadoTarea()==PoliticaTarea.FINALIZANDO) {
					proximaEjecucion = ServidorCR.sincronizador.configurarTareaProgramada(Sesion.getFrecuenciaSincAfiliados());
					ServidorCR.sincronizador.actualizarMensajesDetalles();
					ServidorCR.sincronizador.planificarAfiliados(proximaEjecucion);
//					ServidorCR.sincronizador.getPlanificadorAfiliados().schedule(ServidorCR.sincronizador.getSincAfiliados(), proximaEjecucion);
				}
			}catch (Exception e) {e.printStackTrace();}
			
			/**************************/
			try {
				//Chequeamos Tarea de AfiliadosTemporales
				if (Sesion.getFrecuenciaSincAfiliadosTemporales().getEstadoTarea()==PoliticaTarea.FINALIZANDO) {
					proximaEjecucion = ServidorCR.sincronizador.configurarTareaProgramada(Sesion.getFrecuenciaSincAfiliadosTemporales());
					ServidorCR.sincronizador.actualizarMensajesDetalles();
					ServidorCR.sincronizador.planificarAfiliadosTemporales(proximaEjecucion);
//					ServidorCR.sincronizador.getPlanificadorAfiliados().schedule(ServidorCR.sincronizador.getSincAfiliados(), proximaEjecucion);
				}
			}catch (Exception e) {e.printStackTrace();}
			/**************************/			
			
			
			

			try {
				//Chequeamos Tarea de Productos
				if (Sesion.getFrecuenciaSincProductos().getEstadoTarea()==PoliticaTarea.FINALIZANDO) {
					proximaEjecucion = ServidorCR.sincronizador.configurarTareaProgramada(Sesion.getFrecuenciaSincProductos());
					ServidorCR.sincronizador.actualizarMensajesDetalles();
					ServidorCR.sincronizador.planificarProductos(proximaEjecucion);
//					ServidorCR.sincronizador.getPlanificadorProductos().schedule(ServidorCR.sincronizador.getSincServCentServTdaProds(), proximaEjecucion);
				}
			}catch (Exception e) {e.printStackTrace();}

			try {
				//Chequeamos Tarea de Ventas
				if (Sesion.getFrecuenciaSincVentas().getEstadoTarea()==PoliticaTarea.FINALIZANDO) {
					proximaEjecucion = ServidorCR.sincronizador.configurarTareaProgramada(Sesion.getFrecuenciaSincVentas());
					ServidorCR.sincronizador.actualizarMensajesDetalles();
					ServidorCR.sincronizador.planificarVentas(proximaEjecucion);
//					ServidorCR.sincronizador.getPlanificadorVentas().schedule(ServidorCR.sincronizador.getSincAfiliados(), proximaEjecucion);
			}
			}catch (Exception e) {e.printStackTrace();}
			try {
				//Chequeamos Tarea de Lista de Regalos
				if (Sesion.getFrecuenciaSincListaRegalos().getEstadoTarea()==PoliticaTarea.FINALIZANDO) {
					proximaEjecucion = ServidorCR.sincronizador.configurarTareaProgramada(Sesion.getFrecuenciaSincListaRegalos());
					ServidorCR.sincronizador.actualizarMensajesDetalles();
					ServidorCR.sincronizador.planificarListaRegalos(proximaEjecucion);
//					ServidorCR.sincronizador.getPlanificadorListaRegalos().schedule(ServidorCR.sincronizador.getSincListaRegalos(), proximaEjecucion);
				}
			}catch (Exception e) {e.printStackTrace();}
				
			//Detenemos el Servicio por 5 Segundos
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void iniciar() {
		Thread thread = new Thread(this, "Configurar Tareas");
		thread.start();
	}
}
