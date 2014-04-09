/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.colascr.transferencias.sincronizador
 * Programa   : SincListaRegalos.java
 * Creado por : 
 * Creado en  : 
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
package com.beco.colascr.transferencias.sincronizador;

import java.util.TimerTask;

import com.beco.colascr.transferencias.comunicacionbd.BeansSincronizador;
import com.beco.colascr.transferencias.configuracion.PoliticaTarea;
import com.beco.colascr.transferencias.configuracion.Sesion;

/**
 * Descripción:
 * 
 */

public class SincListaRegalos extends TimerTask {

	public static Sincronizador sincronizador = null; 

	/**
	 * Constructor para SincronizarServidorCaja.java
	 *
	 * 
	 */
	public SincListaRegalos(Sincronizador sync) {
		super();
		sincronizador = sync;
	}
	/**
	 * Método run
	 *
	 */
	public void run() {
		long inicioTotal = System.currentTimeMillis();
		try {
			Sesion.getFrecuenciaSincListaRegalos().setEstadoTarea(PoliticaTarea.INICIADA);
			
			System.out.println("\n ***** Iniciando Ciclo de Sincronización Lista de Regalos *****");
			
			System.out.print(" Sincronizando los Afiliados que se encuentren en Listas de Regalos sin sincronizar  ----->");
			BeansSincronizador.syncAfiliadosLR();
			System.out.println();
			
			System.out.print(" Sincronizando Entidad CR.listaregalos --> AS->CR -->");
			long inicio = System.currentTimeMillis();
			BeansSincronizador.syncListaregalos();
			long fin = System.currentTimeMillis();
			System.out.println(" Finalizada Sincronización. Tardó " + (fin-inicio) + " MiliSegs.");
			
			System.out.print(" Sincronizando Entidad CR.detallelistaregalos --> AS->CR -->");
			inicio = System.currentTimeMillis();
			BeansSincronizador.syncDetallelistaregalos();
			fin = System.currentTimeMillis();
			System.out.println(" Finalizada Sincronización. Tardó " + (fin-inicio) + " MiliSegs.");
			
			System.out.print(" Sincronizando Entidad CR.operacionlistaregalos --> AS->CR -->");
			inicio = System.currentTimeMillis();
			BeansSincronizador.syncOperacionlistaregalos();
			fin = System.currentTimeMillis();
			System.out.println(" Finalizada Sincronización. Tardó " + (fin-inicio) + " MiliSegs.");			

			System.out.print(" Sincronizando Entidad CR.detalleoperacionlistaregalos --> AS->CR -->");
			inicio = System.currentTimeMillis();
			BeansSincronizador.syncDetalleoperacionlistaregalos();
			fin = System.currentTimeMillis();
			System.out.println(" Finalizada Sincronización. Tardó " + (fin-inicio) + " MiliSegs.");	
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
		long finTotal = System.currentTimeMillis();
		System.out.println("\n ***** Finalizada Sincronización Lista de Regalos. Tiempo Total: "  + (finTotal - inicioTotal) + " MiliSegs. *****");
		Sesion.getFrecuenciaSincListaRegalos().setDuracionSinc(finTotal-inicioTotal);
		Sesion.getFrecuenciaSincListaRegalos().setEstadoTarea(PoliticaTarea.FINALIZANDO);
		/*ServidorCR.sincronizador.configurarTareaProgramada(Sesion.getFrecuenciaSincListaRegalos());
		ServidorCR.sincronizador.actualizarMensajesDetalles();*/
	}
}