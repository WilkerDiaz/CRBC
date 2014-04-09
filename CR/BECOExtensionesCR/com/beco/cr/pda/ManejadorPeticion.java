/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.cr.pda
 * Programa   : ManejadorPeticion.java
 * Creado por : irojas
 * Creado en  : 10/08/2009 - 07:57:19 PM
 *
 * (c) CENTROBECO, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 
 * Fecha       : 
 * Analista    : 
 * Descripción : 
 * =============================================================================
 */

package com.beco.cr.pda;

import java.net.ServerSocket;

import org.apache.log4j.Logger;


/**
 *	Esta clase refiere a los objetos que representan ManejadorPeticion. 
 */
public class ManejadorPeticion {
	/**
	 * Logger for this class
	 */ 
	private static final Logger logger = Logger
			.getLogger(ManejadorPeticion.class);
	
	private int puertoSocket = 0;
	
	/**
	 * 
	 *
	 */
	public ManejadorPeticion(int puerto) {
		ServerSocket serverSocket = null;
		//***** Se abre el socket para establecer la comunicación de recepción y envío
		if (logger.isDebugEnabled()) {
			logger.debug("ManejadorPeticion() - start");
		}
		this.puertoSocket = puerto;
		try {
			serverSocket = new ServerSocket(this.puertoSocket);
		} catch (Exception e) {
			logger.error("ManejadorPeticion()", e);
		}
		
		//*****Se escuchan peticiones
    	while(true){
    		try{
    			HiloControl hilo = new HiloControl(serverSocket.accept());
    			hilo.start();
			}catch(Exception e){
				logger.error("ManejadorPeticion()", e);
    		}
    	}
	}
}
