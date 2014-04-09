/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : MensajeSplash.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 25/08/2004 05:25:21 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.0.0
 * Fecha       : 25/08/2004 11:31 AM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Implementación inicial
 * =============================================================================
 */
package com.becoblohm.cr.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

import org.apache.log4j.Logger;

import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;


/**
 * Descripción:
 * 		Muestra una pantalla indicando la carga del sistema.
 */
public class MensajeSplash extends JDialog {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MensajeSplash.class);

  	public JLabel texto;

	private int duration;
	private int[] colorFondo = new int[4];

	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JLabel imgRed = null;
	private javax.swing.JLabel jLabel2 = null;

	public MensajeSplash(int d) {
    	duration = d;
		try {
			String colorFondoSplash = InitCR.preferenciasCR.getConfigStringForParameter("sistema", "colorFondoSplash");
			String[] xColorFondo = InitCR.preferenciasCR.getConfigStringForParameter("sistema", colorFondoSplash).split(",");
			colorFondo[0] = new Integer(xColorFondo[0]).intValue();
			colorFondo[1] = new Integer(xColorFondo[1]).intValue();
			colorFondo[2] = new Integer(xColorFondo[2]).intValue();
			colorFondo[3] = 255;
		} catch (NoSuchNodeException e1) {
			logger.error("MensajeSplash(int)", e1);

			MensajesVentanas.mensajeError("Falla carga de preferencias del sistema");
		} catch (UnidentifiedPreferenceException e1) {
			MensajesVentanas.mensajeError("Falla carga de preferencias del sistema");
			logger.error("MensajeSplash(int)", e1);
		}
  	}

  	public void showSplash(String mensaje) {
		if (logger.isDebugEnabled()) {
			logger.debug("showSplash(String) - start");
		}

		texto = getJLabel2();
	
		//	Centra el splash en el monitor.
		int anchoMensaje = mensaje.length() * 5 + 150;
	    int width = anchoMensaje;
	    int height = 80;
	    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (screen.width-width)/2;
	    int y = (screen.height-height)/2;
	    setBounds(x,y,width,height);
	
    	// Construye la pantalla Splash
		this.texto.setText(mensaje);
		this.repaint();
		this.setContentPane(getJContentPane());
	    setVisible(true);

	    // Espera unos segundos antes de continuar.
    	try { Thread.sleep(duration); } catch (Exception e) {
			logger.error("showSplash(String)", e);
}

		if (logger.isDebugEnabled()) {
			logger.debug("showSplash(String) - end");
		}
  	}

  	public void hideSplash() {
		if (logger.isDebugEnabled()) {
			logger.debug("hideSplash() - start");
		}

	    setVisible(false);

		if (logger.isDebugEnabled()) {
			logger.debug("hideSplash() - end");
		}
  	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJContentPane() - start");
		}

		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.CENTER);
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.RAISED));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJContentPane() - end");
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel1() - start");
		}

		if(jPanel1 == null) {
			jPanel1 = new javax.swing.JPanel();
			jPanel1.setLayout(new java.awt.BorderLayout());
			jPanel1.add(getImgRed(true), java.awt.BorderLayout.WEST);
			jPanel1.add(getJLabel2(), java.awt.BorderLayout.CENTER);
			jPanel1.setBackground(new java.awt.Color(colorFondo[0], colorFondo[1], colorFondo[2]));
			jPanel1.setName("panelMensajes");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel1() - end");
		}
		return jPanel1;
	}

	/**
	 * This method initializes imgRed
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getImgRed(boolean color) {
		if (logger.isDebugEnabled()) {
			logger.debug("getImgRed(boolean) - start");
		}

		ImageIcon imagen = null;
		if (color)	imagen = new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/splash/Red-Color.png"));
		else imagen = new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/splash/Red-BnW.png"));

		if (imgRed == null)imgRed = new javax.swing.JLabel(imagen);
		else this.imgRed.setIcon(imagen);
		
		if (logger.isDebugEnabled()) {
			logger.debug("getImgRed(boolean) - end");
		}
		return imgRed;
	}

	/**
	 * Método activar.
	 * 
	 */
	public void activar(){
		if (logger.isDebugEnabled()) {
			logger.debug("activar() - start");
		}

		int intervalo = 100;
		getImgRed(true);
		MensajesVentanas.esperar(intervalo);
		getImgRed(false);
		MensajesVentanas.esperar(intervalo);
		getImgRed(true);

		if (logger.isDebugEnabled()) {
			logger.debug("activar() - end");
		}
	}

	/**
	 * Método desactivar.
	 * 
	 */
	public void desactivar(){
		if (logger.isDebugEnabled()) {
			logger.debug("desactivar() - start");
		}

		getImgRed(false);

		if (logger.isDebugEnabled()) {
			logger.debug("desactivar() - end");
		}
	}
	
	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel2() - start");
		}

		if(jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel("", JLabel.CENTER);
			jLabel2.setFont(new Font("Sans-Serif", Font.BOLD, 12));
			jLabel2.setBackground(new java.awt.Color(colorFondo[0],colorFondo[1],colorFondo[2]));
			jLabel2.setText("mensaje");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel2() - end");
		}
		return jLabel2;
	}
	
}