/**
 * =============================================================================
 * Proyecto   : TransferenciaInmediataProdsCR
 * Paquete    : com.beco.colascr.transferenciainmediata.manejoprocesos
 * Programa   : HiloSincronizacion.java
 * Creado por : gmartinelli
 * Creado en  : 28-jun-05 8:40:43
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1
 * Fecha       : 28-jun-05 8:40:43
 * Analista    : gmartinelli
 * Descripción : Implementación inicial.
 * =============================================================================
 */
package com.beco.colascr.transferenciainmediata.manejoprocesos; 

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import com.beco.colascr.transferenciainmediata.mediadoresbd.Mediador;
import com.beco.colascr.transferenciainmediata.sesion.Caja;

/**
 * Descripción:
 * 
 */

public class HiloSincronizacion implements Runnable {

	private String ipBdDestino;
	private Mediador mediador;
	private int numeroCaja;
	/**
	 * Constructor para HiloSincronizacion.java
	 *
	 * 
	 */
	public HiloSincronizacion(Caja caja, Mediador med) {
		super();
		this.ipBdDestino = caja.getIpCaja().trim();
		this.numeroCaja = caja.getNumeroCaja();
		this.mediador = med;
		iniciar();
	}

	/**
	 * Método run
	 *
	 * 
	 */
	public void run() {
		// Chequeamos si la Ip se encuentra en Línea
		if (isIpEnLinea(ipBdDestino)) {
			
			//System.out.println("Sincronizamos Caja: " + numeroCaja + ". Ip: " + ipBdDestino);
			mediador.grabarProductos(ipBdDestino, numeroCaja);
			//mediador.grabarEstados(ipBdDestino, numeroCaja);
			//mediador.grabarCiudades(ipBdDestino, numeroCaja);
			//mediador.grabarUrbanizaciones(ipBdDestino, numeroCaja);
		} else {
			System.out.println("Ip " + ipBdDestino + " fuera de Linea");
		}
	}

	/**
	 * Método isIpEnLinea
	 * 
	 * @return
	 * boolean
	 */
	private boolean isIpEnLinea(String ip) {
		try {
			SocketAddress ipSocket = new InetSocketAddress(ip, 3306);
			Socket server = new Socket();
			server.connect(ipSocket, 10000);
			return true;
		} catch (SocketTimeoutException e) {
			return false;
		} catch (NoRouteToHostException e) {
			return false;
		} catch (UnknownHostException e) {
			return false;
		} catch (ConnectException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}

	public void iniciar() {
		Thread thread;
		thread = new Thread(this, "SincCaja" + numeroCaja);
		thread.start();
	}

}
