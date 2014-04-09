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
 * Versi�n     : 
 * Fecha       : 
 * Analista    : 
 * Descripci�n : 
 * =============================================================================
 */

/**
 *  Genera un hilo (Thread) que se encarga de ejecutar la operacion de la lectura del escaner
 * dependiendo de la pantalla que se encuentre activa. Con este hilo se manejan las m�ltiples
 * ventanas de una ejecuci�n de lectura de escaner, ejemplo, en facturaci�n se lee un c�digo de
 * barra de un cliente y se debe pedir autorizaci�n, por lo que se requiere que el escaner
 * lea otro c�digo de barra en la ejecuci�n del evento anterior.
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
