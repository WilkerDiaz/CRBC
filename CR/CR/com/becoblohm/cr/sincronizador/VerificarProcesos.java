/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.sincronizador
 * Programa   : VerificarProcesos.java
 * Creado por : Programador3 - Alexis Gu�dez L�pez
 * Creado en  : 03/05/2004 11:37:26 AM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versi�n     : 1.1
 * Fecha       : 11/11/2004 03:45:26 AM
 * Analista    : Programador8 - Ar�stides Castillo
 * Descripci�n : Adaptaci�n del esquema bajo el cual la caja solo verificar�a
 * 				 el que haya linea en caso de que la misma se haya ido fuera
 * 				 de l�nea.
 * =============================================================================
 * Versi�n     : 1.1
 * Fecha       : 19/03/2004 11:57:26 AM
 * Analista    : Programador3
 * Descripci�n : Implementaci�n inicial.
 * =============================================================================
 */
package com.becoblohm.cr.sincronizador; 

import java.util.TimerTask;

import com.becoblohm.cr.manejadorsesion.Sesion;

/**
 * Descripci�n:
 * 	Esta clase verifica eventualmente el funcionamiento y cumplimiento de ciertos procesos 
 * o condiciones durante la ejecuci�n del sistema. Noifica al usuario la informaci�n
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
	 * M�todo run
	 * 
	 */
	public void run() {
/*		try {
			System.out.print("Verificando conexi�n...");
			if(Sesion.isCajaEnLinea()){
				if(CR.me != null)
					CR.me.setLinea(true);
				System.out.println("Caja en L�nea!!");
			}
			else{
				if(CR.me != null)
					CR.me.setLinea(false);
				System.out.println("Caja Fuera de L�nea!!");
			}	
		} catch (Exception e) {
			e.printStackTrace(System.out);
		} */
		// Nueva implementacion del verificador de linea
		// Ahora debe verificar solo en el caso en que nos encontremos fuera de l�nea.
		// Debemos respetar el que no se pueda verificar la linea
		if (Sesion.isVerificarLinea()) {
			Sesion.chequearLineaCaja();
			if (Sesion.isCajaEnLinea()) {
				System.out.println("Caja en L�nea!!");
				sincronizador.cancelarVerifLinea();
			} else
				System.out.println("Caja Fuera de L�nea!!");
			
		}
	}

}
