/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : SplashScreen.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 06/10/2003 03:35:21 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.2
 * Fecha       : 19/02/2004 11:31 AM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Optimización de la llamada al método "activar".
 * 				 Generalización de los valores iniciales para cargar el splash y
 * 				 el color de fondo del área de mensajes indicadores. 
 * =============================================================================
 */
package com.becoblohm.cr.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;
/**
 * Descripción:
 * 		Muestra una pantalla indicando la carga del sistema.
 */
public class SplashScreen extends JDialog {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SplashScreen.class);

  	public JLabel texto;

	private int duration;
	private int[] colorFondo = new int[4];
	private String mensajeInicial = null;
	private boolean mensajeColorBlanco = false;

	private javax.swing.JWindow jWindow = null;
	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JLabel imgOS = null;
	private javax.swing.JLabel imgJava = null;
	private javax.swing.JLabel imgConfiguracion = null;
	private javax.swing.JLabel imgME = null;
	private javax.swing.JLabel imgHilos = null;
	private javax.swing.JLabel imgDispositivos = null;
	private javax.swing.JLabel imgBD = null;
	private javax.swing.JLabel imgRed = null;
	private javax.swing.JLabel jLabel2 = null;

	public SplashScreen(int d) {
    	duration = d;
		try {
			mensajeInicial = new String(InitCR.preferenciasCR.getConfigStringForParameter("sistema", "mensajeSplash") + CR.version);
			mensajeColorBlanco = InitCR.preferenciasCR.getConfigStringForParameter("sistema", "mensajeColorBlanco").trim().toUpperCase().charAt(0) == 'S' ? true:false;
			String colorFondoSplash = InitCR.preferenciasCR.getConfigStringForParameter("sistema", "colorFondoSplash");
			String[] xColorFondo = InitCR.preferenciasCR.getConfigStringForParameter("sistema", colorFondoSplash).split(",");
			colorFondo[0] = new Integer(xColorFondo[0]).intValue();
			colorFondo[1] = new Integer(xColorFondo[1]).intValue();
			colorFondo[2] = new Integer(xColorFondo[2]).intValue();
			colorFondo[3] = 255;
		} catch (NoSuchNodeException e1) {
			logger.error("SplashScreen(int)", e1);

			MensajesVentanas.mensajeError("Falla carga de preferencias del sistema");
		} catch (UnidentifiedPreferenceException e1) {
			MensajesVentanas.mensajeError("Falla carga de preferencias del sistema");
			logger.error("SplashScreen(int)", e1);
		}
  	}

  	public void showSplash() {
		if (logger.isDebugEnabled()) {
			logger.debug("showSplash() - start");
		}

		texto = getJLabel2();
	
		//	Centra el splash en el monitor.
	    int width = getJLabel().getIcon().getIconWidth();
	    if (width<400) width = 400;
	    int height = getJLabel().getIcon().getIconHeight()+10;
	    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (screen.width-width)/2;
	    int y = (screen.height-height)/2;
	    setBounds(x,y,width,height);
	
	    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	    
    	// Construye la pantalla Splash
		cambiarTexto(mensajeInicial);
		this.setContentPane(getJContentPane());
	    setVisible(true);

	    // Espera unos segundos antes de continuar.
    	try { Thread.sleep(duration); } catch (Exception e) {
			logger.error("showSplash()", e);
}

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

  	public void cambiarTexto(String xTexto) {
		if (logger.isDebugEnabled()) {
			logger.debug("cambiarTexto(String) - start");
		}

	    this.texto.setText(xTexto);
    	try { Thread.sleep(1000); } catch (Exception e) {
			logger.error("cambiarTexto(String)", e);
}
    	this.repaint();

		if (logger.isDebugEnabled()) {
			logger.debug("cambiarTexto(String) - end");
		}
  	}

  	public void showSplashAndExit() {
		if (logger.isDebugEnabled()) {
			logger.debug("showSplashAndExit() - start");
		}

	    showSplash();
    	cambiarTexto(mensajeInicial);
    	hideSplash();
    	System.exit(0);

		if (logger.isDebugEnabled()) {
			logger.debug("showSplashAndExit() - end");
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
			jContentPane.add(getJPanel(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.SOUTH);
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.RAISED));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJContentPane() - end");
		}
		return jContentPane;
	}

	/**
	 * This method initializes jWindow
	 * 
	 * @return javax.swing.JWindow
	 */
	@SuppressWarnings("unused")
	private javax.swing.JWindow getJWindow() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJWindow() - start");
		}

		if(jWindow == null) {
			jWindow = new javax.swing.JWindow();
			jWindow.setContentPane(getJContentPane());
			jWindow.setSize(400, 410);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJWindow() - end");
		}
		return jWindow;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel() - start");
		}

		if(jPanel == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			jPanel = new javax.swing.JPanel();
			jPanel.setLayout(flowLayout1);
			jPanel.setBackground(new java.awt.Color(colorFondo[0],colorFondo[1],colorFondo[2]));
			jPanel.setName("panelPrincipal");
			flowLayout1.setHgap(0);
			flowLayout1.setVgap(0);
			jPanel.add(getJLabel(), null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel() - end");
		}
		return jPanel;
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
			jPanel1.add(getJPanel2(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJLabel2(), java.awt.BorderLayout.SOUTH);
			jPanel1.setBackground(new java.awt.Color(colorFondo[0], colorFondo[1], colorFondo[2]));
			jPanel1.setName("panelMensajes");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel1() - end");
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel2() - start");
		}

		if(jPanel2 == null) {
			jPanel2 = new javax.swing.JPanel();
			jPanel2.add(getImgConfiguracion(false), null);
			jPanel2.add(getImgJava(false), null);
			jPanel2.add(getImgBD(false), null);
			jPanel2.add(getImgRed(false), null);
			jPanel2.add(getImgME(false), null);
			jPanel2.add(getImgDispositivos(false), null);
			jPanel2.add(getImgHilos(false), null);
			jPanel2.setBackground(new java.awt.Color(colorFondo[0], colorFondo[1], colorFondo[2]));
			jPanel1.setName("panelIndicadores");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel2() - end");
		}
		return jPanel2;
	}

	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel() - start");
		}

		if(jLabel == null) {
			try {
				String imagenSplash = new String(InitCR.preferenciasCR.getConfigStringForParameter("sistema", "imagenSplash"));
				jLabel = new javax.swing.JLabel(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/splash/"+imagenSplash)));
			} catch (NoSuchNodeException e) {
				logger.error("getJLabel()", e);

				MensajesVentanas.mensajeError("Falla cargando preferencias para imagen del splash");
			} catch (UnidentifiedPreferenceException e) {
				MensajesVentanas.mensajeError("Falla cargando preferencias para imagen del splash");
				logger.error("getJLabel()", e);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel() - end");
		}
		return jLabel;
	}

	/**
	 * This method initializes imgOS
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getImgOS(boolean color) {
		if (logger.isDebugEnabled()) {
			logger.debug("getImgOS(boolean) - start");
		}

		ImageIcon imagen = null;
		if (color)	imagen = new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/splash/OS-Color.png"));
		else imagen = new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/splash/OS-BnW.png"));
		
		if (imgOS == null)imgOS = new javax.swing.JLabel(imagen);
		else this.imgOS.setIcon(imagen);

		if (logger.isDebugEnabled()) {
			logger.debug("getImgOS(boolean) - end");
		}
		return imgOS;
	}

	/**
	 * This method initializes imgJava
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getImgJava(boolean color) {
		if (logger.isDebugEnabled()) {
			logger.debug("getImgJava(boolean) - start");
		}

		ImageIcon imagen = null;
		if (color) imagen = new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/splash/Java-Color.png"));
		else imagen = new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/splash/Java-BnW.png"));
		
		if (imgJava == null)imgJava = new javax.swing.JLabel(imagen);
		else this.imgJava.setIcon(imagen);

		if (logger.isDebugEnabled()) {
			logger.debug("getImgJava(boolean) - end");
		}
		return imgJava;
	}

	/**
	 * This method initializes imgConfiguracion
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getImgConfiguracion(boolean color) {
		if (logger.isDebugEnabled()) {
			logger.debug("getImgConfiguracion(boolean) - start");
		}

		ImageIcon imagen = null;
		if (color)	imagen = new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/splash/Configuracion-Color.png"));
		else imagen = new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/splash/Configuracion-BnW.png"));
		
		if (imgConfiguracion == null)imgConfiguracion = new javax.swing.JLabel(imagen);
		else this.imgConfiguracion.setIcon(imagen);

		if (logger.isDebugEnabled()) {
			logger.debug("getImgConfiguracion(boolean) - end");
		}
		return imgConfiguracion;
	}

	/**
	 * This method initializes imgME
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getImgME(boolean color) {
		if (logger.isDebugEnabled()) {
			logger.debug("getImgME(boolean) - start");
		}

		ImageIcon imagen = null;
		if (color)	imagen = new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/splash/MaquinaDeEstado-Color.png"));
		else imagen = new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/splash/MaquinaDeEstado-BnW.png"));
		
		if (imgME == null)imgME = new javax.swing.JLabel(imagen);
		else this.imgME.setIcon(imagen);
		
		if (logger.isDebugEnabled()) {
			logger.debug("getImgME(boolean) - end");
		}
		return imgME;
	}

	/**
	 * This method initializes imgHilos
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getImgHilos(boolean color) {
		if (logger.isDebugEnabled()) {
			logger.debug("getImgHilos(boolean) - start");
		}

		ImageIcon imagen = null;
		if (color)	imagen = new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/splash/Hilos-Color.png"));
		else imagen = new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/splash/Hilos-BnW.png"));
		
		if (imgHilos == null)imgHilos = new javax.swing.JLabel(imagen);
		else this.imgHilos.setIcon(imagen);

		if (logger.isDebugEnabled()) {
			logger.debug("getImgHilos(boolean) - end");
		}
		return imgHilos;
	}

	/**
	 * This method initializes imgDispositivos
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getImgDispositivos(boolean color) {
		if (logger.isDebugEnabled()) {
			logger.debug("getImgDispositivos(boolean) - start");
		}

		ImageIcon imagen = null;
		if (color)	imagen = new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/splash/Dispositivos-Color.png"));
		else imagen = new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/splash/Dispositivos-BnW.png"));
		
		if (imgDispositivos == null)imgDispositivos = new javax.swing.JLabel(imagen);
		else this.imgDispositivos.setIcon(imagen);

		if (logger.isDebugEnabled()) {
			logger.debug("getImgDispositivos(boolean) - end");
		}
		return imgDispositivos;
	}

	/**
	 * This method initializes imgBD
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getImgBD(boolean color) {
		if (logger.isDebugEnabled()) {
			logger.debug("getImgBD(boolean) - start");
		}

		ImageIcon imagen = null;
		if (color)	imagen = new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/splash/BaseDeDatos-Color.png"));
		else imagen = new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/splash/BaseDeDatos-BnW.png"));
		
		if (imgBD == null)imgBD = new javax.swing.JLabel(imagen);
		else this.imgBD.setIcon(imagen);

		if (logger.isDebugEnabled()) {
			logger.debug("getImgBD(boolean) - end");
		}
		return imgBD;
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
	 * @param imagen - La posición del indicador a activar.
	 */
	public void activar(int imagen){
		if (logger.isDebugEnabled()) {
			logger.debug("activar(int) - start");
		}

		int intervalo = 500;
		switch (imagen){
			case 1: getJPanel2().remove(1);
					getJPanel2().add(getImgOS(false), 1);
					getImgOS(true);
					MensajesVentanas.esperar(intervalo);
					getImgOS(false);
					MensajesVentanas.esperar(intervalo);
					getImgOS(true);
					break;
			case 2: getImgJava(true);
					MensajesVentanas.esperar(intervalo);
					getImgJava(false);
					MensajesVentanas.esperar(intervalo);
					getImgJava(true);
					break;
			case 3: getImgConfiguracion(true);
					MensajesVentanas.esperar(intervalo);
					getImgConfiguracion(false);
					MensajesVentanas.esperar(intervalo);
					getImgConfiguracion(true);
					break;
			case 4: getImgME(true);
					MensajesVentanas.esperar(intervalo);
					getImgME(false);
					MensajesVentanas.esperar(intervalo);
					getImgME(true);
					break;
			case 5: getImgHilos(true);
					MensajesVentanas.esperar(intervalo);
					getImgHilos(false);
					MensajesVentanas.esperar(intervalo);
					getImgHilos(true);
					break;
			case 6: getImgDispositivos(true);
					MensajesVentanas.esperar(intervalo);
					getImgDispositivos(false);
					MensajesVentanas.esperar(intervalo);
					getImgDispositivos(true);
					break;
			case 7: getImgBD(true);
					MensajesVentanas.esperar(intervalo);
					getImgBD(false);
					MensajesVentanas.esperar(intervalo);
					getImgBD(true);
					break;
			case 8: getImgRed(true);
					MensajesVentanas.esperar(intervalo);
					getImgRed(false);
					MensajesVentanas.esperar(intervalo);
					getImgRed(true);
					break;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("activar(int) - end");
		}
	}

	/**
	 * Método desactivar.
	 * @param imagen - La posición del indicador a desactivar.
	 */
	public void desactivar(int imagen){
		if (logger.isDebugEnabled()) {
			logger.debug("desactivar(int) - start");
		}

		switch (imagen){
			case 1: getImgOS(false);
					break;
			case 2: getImgJava(false);
					break;
			case 3: getImgConfiguracion(false);
					break;
			case 4: getImgME(false);
					break;
			case 5: getImgHilos(false);
					break;
			case 6: getImgDispositivos(false);
					break;
			case 7: getImgBD(false);
					break;
			case 8: getImgRed(false);
					break;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("desactivar(int) - end");
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
			if(mensajeColorBlanco) jLabel2.setForeground(Color.WHITE);
			jLabel2.setBackground(new java.awt.Color(colorFondo[0],colorFondo[1],colorFondo[2]));
			jLabel2.setText("mensaje");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel2() - end");
		}
		return jLabel2;
	}
}