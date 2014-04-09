/**
 * =============================================================================
 * Proyecto   : CRBECOEPA
 * Paquete    : com.becoblohm.cr.verificador
 * Programa   : SplashPrecio.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 27/11/2003 04:11:59 PM
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
package com.becoblohm.cr.verificador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

import org.apache.log4j.Logger;

import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;

/** 
 * Descripción: 
 * 	Clase que muestra centrada una pantalla de inicio y activación de los componentes 
 * del sistema, por una cantidad indicada de tiempo. 
 * 
 */
public class SplashPrecio extends JWindow {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SplashPrecio.class);

  private String urlEspera;
  public JLabel texto = new JLabel("", JLabel.CENTER);
  public static JEditorPane esperaPrecio = new JEditorPane();
  
  public SplashPrecio(String xUrlEspera) {
  	urlEspera = xUrlEspera;
  }

  // A simple little method to show a title screen in the center
  // of the screen for the amount of time given in the constructor
  public void showSplash() {
	if (logger.isDebugEnabled()) {
		logger.debug("showSplash() - start");
	}

    JPanel content = (JPanel)getContentPane();
    content.setBackground(Color.white);

    // Set the window's bounds, centering the window
    int width = 265;
    int height = 130;
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (screen.width-width)/2;
    int y = (screen.height-height)/2;
    setBounds(x,y,width,height);

    try {
		esperaPrecio.setPage(urlEspera);
		esperaPrecio.setEditable(false);
		int[] colorFondo = new int[4];
		String paramColorFondo = InitCR.preferenciasCR.getConfigStringForParameter("sistema", "colorFondoSplash");
		String[] xColorFondo = InitCR.preferenciasCR.getConfigStringForParameter("sistema", paramColorFondo).split(",");
		colorFondo[0] = new Integer(xColorFondo[0]).intValue();
		colorFondo[1] = new Integer(xColorFondo[1]).intValue();
		colorFondo[2] = new Integer(xColorFondo[2]).intValue();
		colorFondo[3] = 255;
		esperaPrecio.setBackground(new Color(colorFondo[0], colorFondo[1], colorFondo[2], colorFondo[3]));
	} catch (NoSuchNodeException e1) {
		logger.error("showSplash()", e1);

		MensajesVentanas.mensajeError("Falla carga de preferencias del sistema");
	} catch (UnidentifiedPreferenceException e1) {
		MensajesVentanas.mensajeError("Falla carga de preferencias del sistema");
		logger.error("showSplash()", e1);
	} catch (IOException e) {
		logger.error("showSplash()", e);
	}

    content.add(esperaPrecio, BorderLayout.CENTER);
    setVisible(true);

	if (logger.isDebugEnabled()) {
		logger.debug("showSplash() - end");
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

  public void showSplashAndExit() {
	if (logger.isDebugEnabled()) {
		logger.debug("showSplashAndExit() - start");
	}

    showSplash();
    hideSplash();
    System.exit(0);

	if (logger.isDebugEnabled()) {
		logger.debug("showSplashAndExit() - end");
	}
  }

/*  public static void main(String[] args) {
    // Throw a nice little title page up on the screen first
    SplashPrecio splash = new SplashPrecio(5000);
    //splash.showSplashAndExit();
    splash.showSplash();
  }*/

}
