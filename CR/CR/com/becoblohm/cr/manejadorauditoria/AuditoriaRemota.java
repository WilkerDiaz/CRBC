/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.manejadorauditoria
 * Programa   : AuditoriaRemota.java
 * Creado por : gmartinelli
 * Creado en  : 14/04/2004 03:36:46 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.0.0
 * Fecha       : 14/04/2004 - 03:36:46 PM
 * Analista    : Gabriel Martinelli
 * Descripción :  
 * =============================================================================
 */
package com.becoblohm.cr.manejadorauditoria;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;

import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.excepciones.SesionExcepcion;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;

/**
 * Descripción:
 * 
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
* Fecha: agosto 2011
*/
public class AuditoriaRemota implements Runnable {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(AuditoriaRemota.class);
	
	private ServerSocket myServerSocket = null;
	private boolean conectadoSocket = false;
	private Vector<Socket> myWriterSockets = null;
	private Thread thread;
	private int puertoSocket;
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se comento la linia 66 'never used' y se parametrizó 'LinkedHashMap'
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unchecked")
	public AuditoriaRemota() throws SesionExcepcion {
		// Obtenemos la preferencia del puerto del socket
		//EPAPreferenceProxy preferenciasCR = InitCR.preferenciasCR;
		LinkedHashMap<String,Object> preferencias = new LinkedHashMap<String,Object>();
		
		try {
			preferencias = InitCR.preferenciasCR.getAllPreferecesForNode("sistema");
		} catch (NoSuchNodeException e) {
			logger.error("AuditoriaRemota()", e);

			throw new SesionExcepcion("No se pudo cargar la audtoria remota\nError con las preferencias");
		} catch (UnidentifiedPreferenceException e) {
			logger.error("AuditoriaRemota()", e);

			throw new SesionExcepcion("No se pudo cargar la audtoria remota\nError con las preferencias");
		}
		
		String puerto = (String) preferencias.get("PuertoSocket");
		if (puerto == null) {
			puerto = "7000";
			try {
				InitCR.preferenciasCR.setConfigStringForParameter("sistema", "PuertoSocket", "7000");
			} catch (NoSuchNodeException e1) {
				logger.error("AuditoriaRemota()", e1);
			} catch (UnidentifiedPreferenceException e1) {
				logger.error("AuditoriaRemota()", e1);
			}
		} else {
			puerto = preferencias.get("PuertoSocket").toString();
		}
		this.puertoSocket = new Integer(puerto).intValue();	

		// Creamos el hilo de escritura el socket
		thread = new Thread(this, "AuditoriaRemota");
		thread.start();
	}
	/**
	 * Método escribirSocket
	 * 
	 * @param registro
	 */
	public void escribirSocket(Element registro) {
		if (logger.isDebugEnabled()) {
			logger.debug("escribirSocket(Element) - start");
		}

		int errores = 0;
		if (conectadoSocket) {
			for (int i=0; i<myWriterSockets.size(); i++) {
				try {
					Socket myWriterSocket = (Socket)myWriterSockets.elementAt(i);
					ObjectOutputStream out = new ObjectOutputStream(myWriterSocket.getOutputStream());
					// Escribimos en el socket
					out.writeObject("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" + registro.toString());
				} catch (Exception e1) {
					logger.error("escribirSocket(Element)", e1);

					errores++;
					myWriterSockets.set(i,null);
				}
			}
			if (errores==myWriterSockets.size()) conectadoSocket=false;
			while (myWriterSockets.removeElement(null));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("escribirSocket(Element) - end");
		}
	}

	/**
	 * Método run
	 *
	 * 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void run() {

		myWriterSockets = new Vector<Socket>();
		boolean remotoConectado = false;
		while (true) {
			while (!remotoConectado) {
				try {
					myServerSocket = new ServerSocket(puertoSocket);
					remotoConectado = true;
				} catch (IOException e2) {
					logger.error("run()", e2);

					remotoConectado = false;
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e1) {
						logger.error("run()", e1);
					}	
				}
			}
			conectadoSocket = false;
			do {
				try {
					Socket myWriterSocket = myServerSocket.accept();
					myWriterSockets.addElement(myWriterSocket);
					conectadoSocket = true;
				} catch (Exception e) {
					logger.error("run()", e);
				}
			} while(true);
		}

	}
}
