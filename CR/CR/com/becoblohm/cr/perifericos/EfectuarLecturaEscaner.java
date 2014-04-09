/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.perifericos
 * Programa   : EfectuarLecturaEscaner.java
 * Creado por : Gabriel Martinelli
 * Creado en  : 28/08/2004 10:47:54 AM
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
package com.becoblohm.cr.perifericos;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.gui.FacturacionPrincipal;
import com.becoblohm.cr.gui.PantallaCotizacion;
import com.becoblohm.cr.gui.apartado.RegistroApartado;
import com.becoblohm.cr.gui.devolucion.PantallaDevXCambio;
import com.becoblohm.cr.gui.listaregalos.CierreListaRegalos1;
import com.becoblohm.cr.gui.listaregalos.CierreListaRegalos2;
import com.becoblohm.cr.gui.listaregalos.CierreListaRegalos3;
import com.becoblohm.cr.gui.listaregalos.ConsultaListaRegalos;
import com.becoblohm.cr.gui.listaregalos.ConsultaListaTitular;
import com.becoblohm.cr.gui.listaregalos.RegistroListaRegalos;

/**
 *  Genera un hilo (Thread) que se encarga de ejecutar la operacion de la lectura del escaner
 * dependiendo de la pantalla que se encuentre activa. Con este hilo se manejan las m�ltiples
 * ventanas de una ejecuci�n de lectura de escaner, ejemplo, en facturaci�n se lee un c�digo de
 * barra de un cliente y se debe pedir autorizaci�n, por lo que se requiere que el escaner
 * lea otro c�digo de barra en la ejecuci�n del evento anterior.
 * 
 */
public class EfectuarLecturaEscaner implements Runnable {
	
	private String inputMessage;
	private Object pantallaActiva; 
	
	public EfectuarLecturaEscaner (Object pActiva) {
		this.pantallaActiva = pActiva;
		iniciar();
	}

	/* (no Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		if (pantallaActiva.getClass().getName().equalsIgnoreCase(FacturacionPrincipal.class.getName()))
			((FacturacionPrincipal)pantallaActiva).leerEscaner(inputMessage);
		else if (pantallaActiva.getClass().getName().equalsIgnoreCase(RegistroApartado.class.getName()))
			((RegistroApartado)pantallaActiva).leerEscaner(inputMessage);
		else if (pantallaActiva.getClass().getName().equalsIgnoreCase(PantallaCotizacion.class.getName()))
			((PantallaCotizacion)pantallaActiva).leerEscaner(inputMessage);
		else if (pantallaActiva.getClass().getName().equalsIgnoreCase(RegistroListaRegalos.class.getName()))
			((RegistroListaRegalos)pantallaActiva).leerEscaner(inputMessage);
		else if (pantallaActiva.getClass().getName().equalsIgnoreCase(ConsultaListaRegalos.class.getName()))
			((ConsultaListaRegalos)pantallaActiva).leerEscaner(inputMessage);
		else if (pantallaActiva.getClass().getName().equalsIgnoreCase(ConsultaListaTitular.class.getName()))
			((ConsultaListaTitular)pantallaActiva).leerEscaner(inputMessage);
		else if (pantallaActiva.getClass().getName().equalsIgnoreCase(CierreListaRegalos1.class.getName()))
			((CierreListaRegalos1)pantallaActiva).leerEscaner(inputMessage);
		else if (pantallaActiva.getClass().getName().equalsIgnoreCase(CierreListaRegalos2.class.getName()))
			((CierreListaRegalos2)pantallaActiva).leerEscaner(inputMessage);
		else if (pantallaActiva.getClass().getName().equalsIgnoreCase(CierreListaRegalos3.class.getName()))
			((CierreListaRegalos3)pantallaActiva).leerEscaner(inputMessage);
		else if (pantallaActiva.getClass().getName().equalsIgnoreCase(PantallaCotizacion.class.getName()))
			((PantallaCotizacion)pantallaActiva).leerEscaner(inputMessage);
		else if (pantallaActiva.getClass().getName().equalsIgnoreCase(PantallaDevXCambio.class.getName()))
			((PantallaDevXCambio)pantallaActiva).leerEscaner(inputMessage);
	}
	
	public synchronized void iniciar() {
		Thread thread;
		inputMessage = CR.inputEscaner.getText().trim();
		thread = new Thread(this, "LeerEscaner");
		thread.start();
	}

}
