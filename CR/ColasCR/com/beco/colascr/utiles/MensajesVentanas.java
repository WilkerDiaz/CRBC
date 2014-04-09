/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.colascr.utiles
 * Programa   : MensajesVentanas.java
 * Creado por : Ileana Rojas, Gabriel Martinelli
 * Creado en  : 21/02/2005 03:19:59 PM
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

package com.beco.colascr.utiles;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import com.beco.colascr.transferencias.ServidorCR;


/**
 * @author vmedina
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MensajesVentanas {
	public static JDialog ventanaActiva = null;

	/**
	 * Método mensajeError.
	 * 	Visualiza una ventana de dialogo de Error con el mensaje especificado. La ventana utilizada
	 * es preliminar debido a que en la GUI debe definirse un estándar para los mensajes. 
	 * @param mensaje - Texto informatico del error.
	 */
	public static void mensajeError(String mensaje) {
		JOptionPane.showMessageDialog(ventanaActiva, new String(mensaje), 
				new String("Mensaje de Error"), 
				JOptionPane.ERROR_MESSAGE, 
				new ImageIcon(Object.class.getResource("/com/beco/colascr/utiles/iconos/ix32x32/error.png")));
	}

	/**
	 * Método aviso.
	 * 	Visualiza una ventana de dialogo Informativo con el mensaje especificado. La ventana utilizada
	 * es preliminar debido a que en la GUI debe definirse un estándar para los mensajes. 
	 * @param mensaje - Texto informativo.
	 */
	public static void aviso(String mensaje) {
		JOptionPane.showMessageDialog(ventanaActiva, new String(mensaje), 
				new String("Aviso del Sistema"), 
				JOptionPane.WARNING_MESSAGE,
				new ImageIcon(Object.class.getResource("/com/beco/colascr/utiles/iconos/ix32x32/warning.png")));
	}
	
	public static void mensajeUsuario(String mensaje) {
		ServidorCR.getPantInicial().enviarMensaje(mensaje);
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
	public static void centrarVentana(JFrame ventana, boolean visible) {
		int width = ventana.getWidth();
		int height = ventana.getHeight();
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - width) / 2;
		int y = (screen.height - height) / 2;
		ventana.setBounds(x, y, width, height);
		ventana.setVisible(visible);
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
		int width = (int)ventana.getSize().getWidth();
		int height = (int)ventana.getSize().getHeight();
		ventana.setUndecorated(false);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - width) / 2;
		int y = (screen.height - height) / 2;
		ventana.setBounds(x, y, width, height);
		ventana.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		ventana.setModal(true);
		ventana.getRootPane().setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		ventana.getRootPane().setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
		JDialog ventAnterior = ventanaActiva;
		ventanaActiva = ventana;
		ventana.setVisible(true);
		ventanaActiva = ventAnterior;
	}
}