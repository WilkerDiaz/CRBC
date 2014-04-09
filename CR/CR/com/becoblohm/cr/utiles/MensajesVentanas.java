/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.utiles
 * Programa   : MensajesVentanas.java
 * Creado por : Victor Medina <linux@epa.com.ve>
 * Creado en  : Feb 10, 2004 - 11:00:14 AM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.3 (según CVS)
 * Fecha       : 26/02/2004 03:09 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Agregadas en el método "centrarVentanaDialogo" las líneas 123 y 124 
 * 				 respectivamente, las sentencias:
 * 					ventana.getRootPane().setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
 *					ventana.getRootPane().setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
 * =============================================================================
 */
package com.becoblohm.cr.utiles;

import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.extensiones.PDAFactory;
import com.becoblohm.cr.gui.VentanaConfirmacion;
import com.becoblohm.cr.gui.VentanaConfirmacionTimeOut;


class VerificadorFoco implements Runnable {
    
	Thread hiloVerif = null;
    
    
    
	/**
	 * @since 25/08/2005
	 * 
	 */
	public VerificadorFoco() {
		super();
	}
    
    
	public void start() {
		hiloVerif = new Thread(this, "Hilo de recuperacion de foco");
		hiloVerif.setDaemon(true);
		hiloVerif.start();
	}
    
	public void stop() {
		hiloVerif = null;
	}
    
	/* (sin Javadoc)
	 * @see java.lang.Runnable#run()
	 * 
	 * @since 25/08/2005
	 */
	public void run() {

		while (Thread.currentThread() == hiloVerif) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
				   public void run() {

					   if (MensajesVentanas.ventanaActiva != null 
							   && KeyboardFocusManager
								.getCurrentKeyboardFocusManager()
								.getFocusedWindow() != MensajesVentanas.ventanaActiva ) {
						   MensajesVentanas.ventanaActiva.setVisible(true);
					   }

				   }
				});
			} catch (Exception e) {
				stop();
				e.printStackTrace();
			} 
            
		}

	}
}

/**
 * @author vmedina
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MensajesVentanas {
	private static VentanaConfirmacion confirmacion = null;
	public static JDialog ventanaActiva = null;
	private static VerificadorFoco verifFoco = new VerificadorFoco();
	public static JFrame defaultFrame = InitCR.verificador;
	
	/**
	 * Método mensajeError.
	 * 	Visualiza una ventana de dialogo de Error con el mensaje especificado. La ventana utilizada
	 * es preliminar debido a que en la GUI debe definirse un estándar para los mensajes. 
	 * @param mensaje - Texto informatico del error.
	 */
	public static void mensajeError(String mensaje) {
		mensajeError(mensaje, InitCR.iniciando);
	}
	
	public static void mensajeError(String mensaje, boolean inicio) {
		aviso("Mensaje de Error", mensaje, "/com/becoblohm/cr/gui/resources/icons/ix32x32/error.png", inicio);
	}

	/**
	 * Método aviso.
	 * 	Visualiza una ventana de dialogo Informativo con el mensaje especificado. La ventana utilizada
	 * es preliminar debido a que en la GUI debe definirse un estándar para los mensajes. 
	 * @param mensaje - Texto informativo.
	 */
	public static void aviso(String mensaje) {
		aviso(mensaje, InitCR.iniciando);
	}
	
	public static void aviso(String mensaje, boolean inicio) {
		aviso("Aviso del Sistema", mensaje, "/com/becoblohm/cr/gui/resources/icons/ix32x32/warning.png", inicio);
	}
	
	public static void aviso(String titulo, String mensaje, String urlIcon, boolean inicio) {
		(new PDAFactory()).getPDAInstance().mensajesPorPantalla(titulo, mensaje, urlIcon, inicio, ventanaActiva);
	}

	/**
	 * Método esperar.
	 * 	Permite retardar la ejecución de algún proceso por un intervalo dado de milisegundos.
	 * @param duracion - Milisegundos que debe retrasarse la secuencia normal de ejecución.
	 */
	public static void esperar(int duracion) {
		try { Thread.sleep((long)duracion); 
		} catch (Exception e) {
			mensajeError(e.getMessage());
		}
	}

	/**
	 * Método centrarVentana.
	 * 	Esta utilidad permite desplegar una pantalla instancia de un JFrame y la centra en pantalla.
	 * @param ventana - Instancia de la clase JFrame.
	 */
	public static void centrarVentana(JFrame ventana) {
		int width = ventana.getWidth();
		int height = ventana.getHeight();
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - width) / 2;
		int y = (screen.height - height) / 2;
		ventana.setBounds(x, y, width, height);
		ventana.setVisible(true);
	}

	/**
	 * Método centrarVentanaInterna.
	 * 	Esta utilidad permite desplegar una pantalla instancia de un JInternalFrame y la centra 
	 * en el espacio perteneciente a la instancia JFrame contenedora.
	 * @param ventana - Pantalla hija a centrar.
	 * @param ventanaPadre - Pantalla padre (contenedora).
	 */
	public static void centrarVentanaInterna(JInternalFrame ventana, JFrame ventanaPadre) {
		int width = (int)ventana.getPreferredSize().getWidth();
		int height = (int)ventana.getPreferredSize().getHeight();
		Dimension screen = ventanaPadre.getSize();
		int x = (screen.width - width) / 2;
		int y = (screen.height - height) / 2;
		try {
			ventana.setSelected(true);
		} catch (PropertyVetoException e) {
		}
		ventana.setBounds(x, y, width, height);
		ventana.pack();
		ventana.setVisible(true);
	}

	/**
	 * Método centrarVentanaDialogo
	 * 	Esta utilidad permite desplegar una pantalla instancia de un JDialog y la centra en
	 * el monitor en forma modal. 
	 * @param ventana - Pantalla a centrar. 
	 */
	public static void centrarVentanaDialogo(JDialog ventana) {

	    centrarVentanaDialogo(ventana, false);

	}
	
	
	/**
	 * Método centrarVentanaDialogo
	 * 	Esta utilidad permite desplegar una pantalla instancia de un JDialog y la centra en
	 * el monitor en forma modal. 
	 * @param ventana - Pantalla a centrar.
	 * @param pack - Indica si se debe o no realizar un pack a la ventana 
	 */
	private static void centrarVentanaDialogo(JDialog ventana, boolean pack) {

		int width = (int)ventana.getSize().getWidth();
		int height = (int)ventana.getSize().getHeight();
		ventana.setUndecorated(false);
		// Cambio de tamaño para adaptación a esquema sin administrador de ventana
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
//		Dimension screen = new Dimension(815, 655);
		int x = (screen.width - width) / 2;
		int y = (screen.height - height) / 2;
		ventana.getRootPane().setBorder(new BevelBorder(BevelBorder.LOWERED));
		ventana.setBounds(x, y, width, height);
		ventana.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		ventana.setModal(true);
		ventana.getRootPane().setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		ventana.getRootPane().setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
		stackDialog(ventana, pack);

	}
	
	public static void stackDialog(JDialog ventana, boolean pack) {
		JDialog ventAnterior = ventanaActiva;
		ventanaActiva = ventana;
		/*if (pack) {
		    ventana.pack();
		}*/

		ventana.setVisible(true);
		
		ventanaActiva = ventAnterior;
		/*if (ventAnterior != null)
			ventAnterior.setVisible(true);
		else if (defaultFrame != null && defaultFrame.isVisible()) {
		    defaultFrame.setVisible(true);
		}*/
	}
	
	/**
	 * Método preguntarSiNo. 
	 * 		Muestra un dialogo con el mensaje para esperar una respuesta del usuario
	 * 
	 * @return int
	 */
	public static int preguntarSiNo(String mensaje) {
		return preguntarSiNo(mensaje, false); 
	}
	public static int preguntarSiNo(String mensaje, boolean inicio) {
		return preguntarSiNo(mensaje, inicio, true); 
	}
	
	public static int preguntarSiNo(String mensaje, boolean inicio, boolean siPorDefecto) {
		confirmacion = null;
		if (inicio) {
			confirmacion = new VentanaConfirmacion(mensaje, InitCR.verificador);
		} else {
			confirmacion = new VentanaConfirmacion(mensaje);
		}
		if (!siPorDefecto) {
			confirmacion.opcionPorDefecto('N');
		}
		centrarVentanaDialogo(confirmacion, false);
		return confirmacion.getOpcion(); 
	}

	public static int preguntarOpciones(String mensaje, String opc0, String opc1, 
			int defaultOpc) {
		int result = -1;
		if (InitCR.iniciando || ventanaActiva == null) {
			result = preguntarOpciones(mensaje, opc0, opc1, defaultOpc, true);
		} else {
			result = preguntarOpciones(mensaje, opc0, opc1, defaultOpc, false);
		}
		
		return result;
	}
	
	
	public static int preguntarOpciones(String mensaje, String opc0, String opc1, 
			int defaultOpc, boolean inicio) {
		confirmacion = null;
		if (inicio) {
			confirmacion = new VentanaConfirmacion(mensaje, InitCR.verificador, opc0, opc1);
		} else {
			confirmacion = new VentanaConfirmacion(mensaje, opc0, opc1);
		}
		if (defaultOpc == 1) {
			confirmacion.opcionPorDefecto('N');
		}
		centrarVentanaDialogo(confirmacion, false);
		return confirmacion.getOpcion(); 
		
	}
	
	public static void iniciarVerificadorFoco() {

	    verifFoco.start();

	}
		
	public static void detenerVerificadorFoco() {
		try {
		    verifFoco.stop();
		} catch (Exception e1) {
		}
	}
	
    public static int preguntarOpcionesTimeOut(String mensaje, String opc0,
            String opc1, int defaultOpc, long timeOut) {

        int result = -1;
        
        MensajesVentanas.detenerVerificadorFoco();
        if (InitCR.iniciando || ventanaActiva == null) {
            result = preguntarOpcionesTimeOut(mensaje, opc0, opc1, defaultOpc,
                    true, timeOut);
        } else {
            result = preguntarOpcionesTimeOut(mensaje, opc0, opc1, defaultOpc,
                    false, timeOut);
        }

        return result;
    }
    
    public static int preguntarOpcionesTimeOut(String mensaje, String opc0,
            String opc1, int defaultOpc, boolean inicio, long timeOut) {

        confirmacion = null;
        if (inicio) {
            confirmacion = new VentanaConfirmacionTimeOut(mensaje,
                    defaultFrame, opc0, opc1, timeOut);
        } else {
            confirmacion = new VentanaConfirmacionTimeOut(mensaje, opc0, opc1,
                    timeOut);
        }
        if (defaultOpc == 1) {
            confirmacion.setOpcionPorDefecto('N');
        } else {
            confirmacion.setOpcionPorDefecto('S');
        }
        centrarVentanaDialogo(confirmacion, true);
        int returnint = confirmacion.getOpcion();
        return returnint;

    }
}