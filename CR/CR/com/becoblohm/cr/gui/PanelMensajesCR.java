/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : PanelMensajesCR.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : Sep 2, 2004 - 07:32:16 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.0.0
 * Fecha       : 02/09/2004 07:32 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Implementación inicial.
 * =============================================================================
 */
package com.becoblohm.cr.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;

/**
 * @author vmedina
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PanelMensajesCR extends JPanel implements KeyListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(PanelMensajesCR.class);

	private static javax.swing.JPanel jPanel = null;
	private static javax.swing.JLabel jLabel14 = null;
	private static javax.swing.JLabel jLabel16 = null;
	private static javax.swing.JPanel jPanel22 = null;
	private static javax.swing.JPanel jPanel23 = null;
	private static String mensajeCR = new String("Sistema CR - Ver. " + CR.version + " - Linux - MySQL 5.5.15 - JDK 1.6.0_27 ");
	
	private static javax.swing.JEditorPane jEditorPane = null;
	private static javax.swing.JScrollPane jScrollPane2 = null;
	private static StringBuffer mensaje = new StringBuffer("");
	private static MensajeSplash splash = new MensajeSplash(10);
	
	/**
	 * This is the default constructor
	 */
	public PanelMensajesCR() {
		super();
		initialize();
		
		jEditorPane.addKeyListener(this);
		jScrollPane2.addKeyListener(this);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		if (logger.isDebugEnabled()) {
			logger.debug("initialize() - start");
		}

		try {
			mensajeCR = new String(InitCR.preferenciasCR.getConfigStringForParameter("sistema", "mensajeSplash"));
		} catch (NoSuchNodeException e) {
			logger.error("initialize()", e);
		} catch (UnidentifiedPreferenceException e) {
			logger.error("initialize()", e);
		}

		this.add(getJPanel(), null);
		this.setSize(665, 87);
		this.setName("PanelMensajes");
		this.setVisible(false);
		this.setLocale(new java.util.Locale("es", "VE", ""));
		this.setBackground(new java.awt.Color(226,226,222));
		mostrarAviso(mensajeCR, true);

		if (logger.isDebugEnabled()) {
			logger.debug("initialize() - end");
		}
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
			jPanel = new javax.swing.JPanel();
			jPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER,1,1));
			jPanel.add(getJScrollPane2(), null);
			jPanel.add(getJPanel23(), null);
			jPanel.add(getJPanel22(), null);
			jPanel.setPreferredSize(new java.awt.Dimension(660,80));
			jPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
			jPanel.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel() - end");
		}
		return jPanel;
	}
	/**
	 * Método mensajeDeEspera.
	 * 	Utilizada por el Verificador para visualizar un mensaje de espera mientras se cambia la }
	 * página en el navegador. 
	 */
	final static ActionListener cargandoPagina = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			if (logger.isDebugEnabled()) {
				logger.debug("actionPerformed(ActionEvent) - start");
			}

			splash.showSplash(mensaje.toString());

			if (logger.isDebugEnabled()) {
				logger.debug("actionPerformed(ActionEvent) - end");
			}
		}
	};

	final static ActionListener esperaHome = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			if (logger.isDebugEnabled()) {
				logger.debug("actionPerformed(ActionEvent) - start");
			}

			splash.hideSplash();

			if (logger.isDebugEnabled()) {
				logger.debug("actionPerformed(ActionEvent) - end");
			}
		}
	};

	final static int goHome = 1000; //milliseconds
	final static int waitPagina = 1000; //milliseconds
	final static Timer home = new Timer(goHome, esperaHome);
	final static Timer cargando = new Timer(waitPagina, cargandoPagina);

	public static void mensajeDeEspera() {
		if (logger.isDebugEnabled()) {
			logger.debug("mensajeDeEspera() - start");
		}

		home.restart();
		home.setCoalesce(false);
		home.setRepeats(false);
		cargando.restart();
		cargando.setCoalesce(false);
		cargando.setRepeats(false);

		if (logger.isDebugEnabled()) {
			logger.debug("mensajeDeEspera() - end");
		}
	}

	/**
	 * Método mostrarAviso
	 *		Métodos para la visualización de los mensajes al usuario  
	 * @param xTexto
	 * @param urgente
	 */
	public static void mostrarAviso(String xTexto, boolean urgente) {
		if (logger.isDebugEnabled()) {
			logger.debug("mostrarAviso(String, boolean) - start");
		}

		mensaje = new StringBuffer(xTexto);
		String mensajePanel = getJEditorPane().getText();
		
		if((mensajePanel.trim()).indexOf(xTexto.trim()) == -1) {
			CR.me.setAviso(false);
			CR.me.setAviso(true);
			if(urgente){
				getJEditorPane().setForeground(new Color(54,54,54));
				getJEditorPane().setFont(new Font("Dialog", Font.BOLD, 12));
			}	
			else{
				getJEditorPane().setForeground(null);
				getJEditorPane().setFont(null);
			}	
			//mensajeDeEspera();
			//MensajesVentanas.aviso(xTexto);
			StringBuffer texto = new StringBuffer(getJEditorPane().getText());
			texto.append("\n"+xTexto);
			Toolkit.getDefaultToolkit().beep();
			getJEditorPane().setText(texto.toString());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("mostrarAviso(String, boolean) - end");
		}
	}

	public static void borrarAvisos(boolean mensajeInicial) {
		if (logger.isDebugEnabled()) {
			logger.debug("borrarAvisos(boolean) - start");
		}

		CR.me.setAviso(true);
		getJEditorPane().setText("");
		if(mensajeInicial){
			mostrarAviso(mensajeCR, true);
		}
		CR.me.setAviso(false);

		if (logger.isDebugEnabled()) {
			logger.debug("borrarAvisos(boolean) - end");
		}
	}

	public static void borrarAvisos() {
		if (logger.isDebugEnabled()) {
			logger.debug("borrarAvisos() - start");
		}

		borrarAvisos(false);

		if (logger.isDebugEnabled()) {
			logger.debug("borrarAvisos() - end");
		}
	}

	public static void iniciarAvisos() {
		if (logger.isDebugEnabled()) {
			logger.debug("iniciarAvisos() - start");
		}

		borrarAvisos(true);

		if (logger.isDebugEnabled()) {
			logger.debug("iniciarAvisos() - end");
		}
	}

	/**
	 * Método mostrarEstadoCaja
	 *		Método para la visualización del nombre del estado de la caja 
	 * @param nombreEstado
	 */
	public static void mostrarEstadoCaja(String nombreEstado) {
		if (logger.isDebugEnabled()) {
			logger.debug("mostrarEstadoCaja(String) - start");
		}

		getJLabel16().setText(nombreEstado);

		if (logger.isDebugEnabled()) {
			logger.debug("mostrarEstadoCaja(String) - end");
		}
	}
	
	/**
	 * This method initializes jLabel14
	 * 
	 * @return javax.swing.JLabel
	 */
	private static javax.swing.JLabel getJLabel14() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel14() - start");
		}

		if(jLabel14 == null) {
			jLabel14 = new javax.swing.JLabel();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel14() - end");
		}
		return jLabel14;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}

		//Mapeo las teclas de Movimiento
		if(e.getKeyCode() == KeyEvent.VK_UP){
			getJScrollPane2().getVerticalScrollBar().setValue(0);
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			getJScrollPane2().getVerticalScrollBar().setValue(0);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - end");
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyReleased(KeyEvent) - start");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("keyReleased(KeyEvent) - end");
		}
	}
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyTyped(KeyEvent) - start");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("keyTyped(KeyEvent) - end");
		}
	}
	
	/**
	 * This method initializes jLabel16
	 * 
	 * @return javax.swing.JLabel
	 */
	private static javax.swing.JLabel getJLabel16() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel16() - start");
		}

		if(jLabel16 == null) {
			jLabel16 = new javax.swing.JLabel();
			jLabel16.setText(Sesion.getCaja().getNombreEstado());
			jLabel16.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jLabel16.setIcon(new ImageIcon(InitCR.class.getResource("/com/becoblohm/cr/gui/resources/icons/ix24x24/trafficlight_on.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel16() - end");
		}
		return jLabel16;
	}
	
	/**
	 * This method initializes jPanel22
	 * 
	 * @return javax.swing.JPanel
	 */
	private static javax.swing.JPanel getJPanel22() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel22() - start");
		}

		if(jPanel22 == null) {
			jPanel22 = new javax.swing.JPanel();
			jPanel22.add(getJLabel14(), null);
			jPanel22.setBackground(new java.awt.Color(242,242,238));
			jPanel22.setPreferredSize(new java.awt.Dimension(330,26));
			jPanel22.setVisible(true);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel22() - end");
		}
		return jPanel22;
	}
	
	/**
	 * This method initializes jPanel23
	 * 
	 * @return javax.swing.JPanel
	 */
	private static javax.swing.JPanel getJPanel23() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel23() - start");
		}

		if(jPanel23 == null) {
			jPanel23 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout110 = new java.awt.FlowLayout();
			layFlowLayout110.setHgap(5);
			layFlowLayout110.setVgap(0);
			layFlowLayout110.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel23.setLayout(layFlowLayout110);
			jPanel23.add(getJLabel16(), null);
			jPanel23.setBackground(new java.awt.Color(242,242,238));
			jPanel23.setPreferredSize(new java.awt.Dimension(325,26));
			jPanel23.setVisible(true);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel23() - end");
		}
		return jPanel23;
	}

	/**
	 * This method initializes jEditorPane
	 * 
	 * @return javax.swing.JEditorPane
	 */
	private static javax.swing.JEditorPane getJEditorPane() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJEditorPane() - start");
		}

		if(jEditorPane == null) {
			jEditorPane = new javax.swing.JEditorPane();
			jEditorPane.setPreferredSize(new java.awt.Dimension(654,45));
			jEditorPane.setFocusable(true);
			jEditorPane.setEditable(false);
			jEditorPane.setVisible(true);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJEditorPane() - end");
		}
		return jEditorPane;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private static javax.swing.JScrollPane getJScrollPane2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJScrollPane2() - start");
		}

		if(jScrollPane2 == null) {
			jScrollPane2 = new javax.swing.JScrollPane();
			jScrollPane2.setViewportView(getJEditorPane());
			jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			jScrollPane2.setPreferredSize(new java.awt.Dimension(652,49));
			jScrollPane2.setVisible(true);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJScrollPane2() - end");
		}
		return jScrollPane2;
	}

	/**
	 * Método getAreaMensajes
	 * 
	 * @return JScrollPane
	 */
	public static JScrollPane getAreaMensajes(){
		if (logger.isDebugEnabled()) {
			logger.debug("getAreaMensajes() - start");
		}

		JScrollPane returnJScrollPane = getJScrollPane2();
		if (logger.isDebugEnabled()) {
			logger.debug("getAreaMensajes() - end");
		}
		return returnJScrollPane;
	}	

	/**
	 * Método getPanelEstadoCaja
	 * 
	 * @return JPanel
	 */
	public static JPanel getPanelEstadoCaja(){
		if (logger.isDebugEnabled()) {
			logger.debug("getPanelEstadoCaja() - start");
		}

		JPanel returnJPanel = getJPanel23();
		if (logger.isDebugEnabled()) {
			logger.debug("getPanelEstadoCaja() - end");
		}
		return returnJPanel; 	
	}

	/**
	 * Método getPanelStatus
	 * 
	 * @return JPanel
	 */
	public static JPanel getPanelStatus(){
		if (logger.isDebugEnabled()) {
			logger.debug("getPanelStatus() - start");
		}

		JPanel returnJPanel = getJPanel22();
		if (logger.isDebugEnabled()) {
			logger.debug("getPanelStatus() - end");
		}
		return returnJPanel; 	
}

}