/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.sincronizador
 * Programa   : VerificarProcesos.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 03/05/2004 11:37:26 AM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1
 * Fecha       : 11/11/2004 03:45:26 AM
 * Analista    : Programador8 - Arístides Castillo
 * Descripción : Adaptación del esquema bajo el cual la caja solo verificaría
 * 				 el que haya linea en caso de que la misma se haya ido fuera
 * 				 de línea.
 * =============================================================================
 * Versión     : 1.1
 * Fecha       : 19/03/2004 11:57:26 AM
 * Analista    : Programador3
 * Descripción : Implementación inicial.
 * =============================================================================
 */
package com.becoblohm.cr.sincronizador; 

import java.util.TimerTask;

import com.becoblohm.cr.manejadorsesion.Sesion;

/**
 * Descripción:
 * 	Esta clase verifica eventualmente el funcionamiento y cumplimiento de ciertos procesos 
 * o condiciones durante la ejecución del sistema. Noifica al usuario la información
 * obtenida. 
 */

public class VerificarProcesos extends TimerTask {
	Sincronizador sincronizador;
	
	/**
	 * Constructor para SincronizarServidorCaja.java
	 *
	 * 
	 */
	public VerificarProcesos(Sincronizador sync) {
		super();
		sincronizador = sync;
	}

	/**
	 * Método run
	 * 
	 */
	public void run() {
/*		try {
			System.out.print("Verificando conexión...");
			if(Sesion.isCajaEnLinea()){
				if(CR.me != null)
					CR.me.setLinea(true);
				System.out.println("Caja en Línea!!");
			}
			else{
				if(CR.me != null)
					CR.me.setLinea(false);
				System.out.println("Caja Fuera de Línea!!");
			}	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		} */
		// Nueva implementacion del verificador de linea
		// Ahora debe verificar solo en el caso en que nos encontremos fuera de línea.
		// Debemos respetar el que no se pueda verificar la linea
		if (Sesion.isVerificarLinea()) {
			Sesion.chequearLineaCaja();
			if (Sesion.isCajaEnLinea()) {
				System.out.println("Caja en Línea!!");
				sincronizador.cancelarVerifLinea();
			} else
				System.out.println("Caja Fuera de Línea!!");
			
		}
	}

}
