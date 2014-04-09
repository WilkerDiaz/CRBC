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

public class SincAfiliados extends TimerTask {

	public static Sincronizador sincronizador = null; 

	/**
	 * Constructor para SincronizarServidorCaja.java
	 *
	 * 
	 */
	public SincAfiliados(Sincronizador sync) {
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
			Sesion.getFrecuenciaSincAfiliados().setEstadoTarea(PoliticaTarea.INICIADA);
			
			System.out.println("\n ***** Iniciando Ciclo de Sincronización Afiliados *****");
			
			System.out.print(" Sincronizando Entidad CR.afiliado --> AS->CR -->");
			long inicio = System.currentTimeMillis();
			BeansSincronizador.migrarArchivoReplace(Sesion.getPathArchivos() + "afiliados", "afiliado");
			long fin = System.currentTimeMillis();
			System.out.println(" Finalizada Sincronización. Tardó " + (fin-inicio) + " MiliSegs.");
			
			System.out.print(" Sincronizando Entidad CR.detalleafiliado --> AS->CR -->");
			inicio = System.currentTimeMillis();
			BeansSincronizador.migrarArchivo(Sesion.getPathArchivos() + "detAfiliados", "detalleafiliado");
			fin = System.currentTimeMillis();
			System.out.println(" Finalizada Sincronización. Tardó " + (fin-inicio) + " MiliSegs.");
			
			System.out.print(" Sincronizamos los Estados, Urbanizaciones y Ciudades  ----->");
			BeansSincronizador.syncEntidadesATCM(Sesion.SINC_SERVTDA);
			System.out.println();

		} catch (Throwable t) {
			t.printStackTrace();
		}
		long finTotal = System.currentTimeMillis();
		System.out.println("\n ***** Finalizada Sincronización Afiliados. Tiempo Total: "  + (finTotal - inicioTotal) + " MiliSegs. *****");
		Sesion.getFrecuenciaSincAfiliados().setDuracionSinc(finTotal-inicioTotal);
		Sesion.getFrecuenciaSincAfiliados().setEstadoTarea(PoliticaTarea.FINALIZANDO);
		/*ServidorCR.sincronizador.configurarTareaProgramada(Sesion.getFrecuenciaSincAfiliados());
		ServidorCR.sincronizador.actualizarMensajesDetalles();*/
	}
}