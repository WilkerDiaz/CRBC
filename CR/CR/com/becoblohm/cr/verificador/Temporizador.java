package com.becoblohm.cr.verificador;


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
 * Versión     : 
 * Fecha       : 
 * Analista    : 
 * Descripción : 
 * =============================================================================
 */

/**
 *  Genera un hilo (Thread) que se encarga de ejecutar la operacion de la lectura del escaner
 * dependiendo de la pantalla que se encuentre activa. Con este hilo se manejan las múltiples
 * ventanas de una ejecución de lectura de escaner, ejemplo, en facturación se lee un código de
 * barra de un cliente y se debe pedir autorización, por lo que se requiere que el escaner
 * lea otro código de barra en la ejecución del evento anterior.
 * 
 */
public class Temporizador implements Runnable {
	
	private Thread thread;
	private Resultado pantalla = null;
	private int tiempo;
	
	public Temporizador (Resultado resultado, int tiempoMSegs) {
		thread = new Thread(this, "LeerEscaner");
		pantalla = resultado;
		tiempo = tiempoMSegs;
		thread.start();
	}

	/* (no Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		/*Time inicio = new Time(new Date().getTime());
		boolean mostrar = true;
		while (mostrar) {*/
			// Si duramos mas de 5 segundos cerramos la ventana
			try {
				Thread.sleep(tiempo);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			/*Time actual = new Time(new Date().getTime());
			if (actual.getTime() > (inicio.getTime()+tiempo))
				mostrar = false;
		}*/
		pantalla.dispose();
	}

}
