/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.colascr
 * Programa   : ServidorCR.java
 * Creado por : Ileana Rojas, Gabriel Martinelli
 * Creado en  : 21/02/2005 02:05:59 PM
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

package com.beco.colascr.transferencias;

import com.beco.colascr.servicios.Servidor;
//import com.beco.colascr.servicios.mediadorbd.CargarAfiliados;
import com.beco.colascr.transferencias.configuracion.ConfiguradorTareas;
import com.beco.colascr.transferencias.configuracion.Sesion;
import com.beco.colascr.transferencias.gui.ConsolaPrincipal;
import com.beco.colascr.transferencias.sincronizador.Sincronizador;


/** 
 * Descripción: 
 * 
 * 
 */
public class ServidorCR {
	
	public static final int SERVICIO_DETENIDO = 0;
	public static final int SERVICIO_INICIANDO = 1;
	public static final int SERVICIO_INICIADO = 2;
	public static final int SERVICIO_DETENIENDO = 3;
	public static final int NUMERO_LINEAS_MSG = 30;
	
	public static Sincronizador sincronizador;
	private static ConsolaPrincipal pantInicial = null;
	private static int estadoServ = 0;
	public static ConfiguradorTareas configuradorTareas = null;
	
	/**
	 * Constructor for CR.
	 */
	public ServidorCR() {
		sincronizador = new Sincronizador(Sesion.getFrecuenciaSincBases(), Sesion.getFrecuenciaSincProductos(), Sesion.getFrecuenciaSincVentas(), Sesion.getFrecuenciaSincAfiliados(), Sesion.getFrecuenciaSincListaRegalos(),Sesion.getFrecuenciaSincAfiliadosTemporales());
	}

	/**
	 * Método main.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			configuradorTareas = new ConfiguradorTareas();
			pantInicial = new ConsolaPrincipal();
			pantInicial.iniciarServicio();
			new Servidor();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Método getPantInicial
	 * 
	 * @return
	 * ConsolaPrincipal
	 */
	public static ConsolaPrincipal getPantInicial() {
		return pantInicial;
	}

	/**
	 * Método getEstadoServ
	 * 
	 * @return
	 * int
	 */
	public static int getEstadoServ() {
		return estadoServ;
	}

	/**
	 * Método setEstadoServ
	 * 
	 * @param i
	 * void
	 */
	public static void setEstadoServ(int i) {
		estadoServ = i;
	}

}