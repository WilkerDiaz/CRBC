/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.colascr.transferencias.sincronizador
 * Programa   : SincAfiliados.java
 * Creado por : Ileana Rojas, Gabriel Martinelli
 * Creado en  : 21/02/2005 04:16:59 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versi�n     : 
 * Fecha       : 
 * Analista    : 
 * Descripci�n :
 * =============================================================================
 */
package com.beco.colascr.transferencias.sincronizador;

import java.util.TimerTask;

import com.beco.colascr.transferencias.comunicacionbd.BeansSincronizador;
import com.beco.colascr.transferencias.configuracion.PoliticaTarea;
import com.beco.colascr.transferencias.configuracion.Sesion;

/**
 * Descripci�n:
 * 
 */

public class SincAfiliadosTemporales extends TimerTask {

	public static Sincronizador sincronizador = null; 

	/**
	 * Constructor para SincronizarServidorCaja.java
	 *
	 * 
	 */
	public SincAfiliadosTemporales(Sincronizador sync) {
		super();
		sincronizador = sync;
	}
	/**
	 * M�todo run
	 *
	 */
	public void run() {
		long inicioTotal = System.currentTimeMillis();
		try {
			Sesion.getFrecuenciaSincAfiliadosTemporales().setEstadoTarea(PoliticaTarea.INICIADA);
			
			System.out.println("\n ***** Iniciando Ciclo de Sincronizaci�n Afiliados Temporales *****");
			
			System.out.print(" Sincronizando Entidad CR.afiliado1 --> CR-->AS -->");
			long inicio = System.currentTimeMillis();
			BeansSincronizador.syncAfiliadosTemporales();
			long fin = System.currentTimeMillis();
			System.out.println(" Finalizada Sincronizaci�n. Tard� " + (fin-inicio) + " MiliSegs.");
		} catch (Throwable t) {
			t.printStackTrace();
		}
		long finTotal = System.currentTimeMillis();
		System.out.println("\n ***** Finalizada Sincronizaci�n Afiliados Temporales. Tiempo Total: "  + (finTotal - inicioTotal) + " MiliSegs. *****");
		Sesion.getFrecuenciaSincAfiliadosTemporales().setDuracionSinc(finTotal-inicioTotal);
		Sesion.getFrecuenciaSincAfiliadosTemporales().setEstadoTarea(PoliticaTarea.FINALIZANDO);

	}
}