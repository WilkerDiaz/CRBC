/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : VerificadorPrecios.java
 * Creado por : Victor Medina <linux@epa.com.ve>
 * Creado en  : 18/06/2004 04:11:59 PM
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

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;

import org.jdesktop.jdic.browser.WebBrowser;

/**
 * <p>Indicar aquí, en una línea que hace la clase</p>
 * <p>Agregar parrafos que describen como se usa la Clase y un resumen de como 
 * funciona.</p>
 * @author $Author: programa8 $  
 * @version $Revision: 1.4 $<br>$Date: 2005/03/10 15:51:09 $
 * <!-- A continuación indicar referencia a otras páginas o clases parecidas -->
 * <!-- o relacionados, pundiendo indicar inclusive los métodos relacionados -->
 * <!-- por ejemplo: @see     <a href="package-summary.html#charenc">Character encodings</a> -->
 * <!-- por ejemplo: @see     java.lang.Object 								 -->
 * <!-- por ejemplo: @see     java.lang.Object#toNewString() 				 -->
 * @see     {RutaObjeto}#NombreMetodo()
 * @since   <!-- Indique desde que versión del Proyecto existe esta clase 	 -->
 */
public class VerificadorPrecios extends JFrame {
	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JPanel BrowserPanel = null;
	private javax.swing.JPanel separador_01 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JPanel separador_02 = null;
	private javax.swing.JTextField jTextField = null;
	private javax.swing.JButton jButton = null;
	
	private WebBrowser webBrowser;
	
	public static void main(String[] args) {
		VerificadorPrecios verificadorPrecios = new VerificadorPrecios();
		verificadorPrecios.setVisible(true);
		verificadorPrecios.setNewUrl("file:///home/vmedina/Java%20Library/JavaTutorial/overview/index.html");
	}
	/**
	 * This is the default constructor
	 */
	public VerificadorPrecios() {
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		webBrowser = new WebBrowser();
		webBrowser.setSize(760,455);
		this.setBackground(new java.awt.Color(226,226,222));
		this.setSize(800,600);
		this.setContentPane(getJContentPane());	
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();

			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(layFlowLayout1);
			layFlowLayout1.setHgap(10);
			layFlowLayout1.setVgap(10);
			jContentPane.setBackground(new java.awt.Color(226,226,222));
			jContentPane.add(getJPanel(), null);
		}
		return jContentPane;
	}
	/**

	 * This method initializes jPanel	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel() {
		if (jPanel == null) {
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();

			jPanel = new javax.swing.JPanel();
			jPanel.setLayout(layFlowLayout2);
			layFlowLayout2.setHgap(0);
			layFlowLayout2.setVgap(0);
			jPanel.setPreferredSize(new java.awt.Dimension(770,550));
			jPanel.setBackground(new java.awt.Color(242,242,238));
			jPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
			jPanel.add(getJPanel1(), null);
			jPanel.add(getSeparador_01(), null);
			jPanel.add(getBrowserPanel(), null);
			jPanel.add(getSeparador_02(), null);
			jPanel.add(getJPanel4(), null);
		}
		return jPanel;
	}

	/**

	 * This method initializes jPanel1	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel1() {
		if (jPanel1 == null) {
			java.awt.FlowLayout layFlowLayout3 = new java.awt.FlowLayout();

			jPanel1 = new javax.swing.JPanel();
			jPanel1.setLayout(layFlowLayout3);
			jPanel1.setPreferredSize(new java.awt.Dimension(770,45));
			jPanel1.setBackground(new java.awt.Color(69,107,127));
			layFlowLayout3.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel1.add(getJLabel(), null);
		}
		return jPanel1;
	}

	/**

	 * This method initializes jLabel	

	 * 	

	 * @return javax.swing.JLabel	

	 */    
	private javax.swing.JLabel getJLabel() {
		if (jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setText("Verificador de Precios");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 16));
			jLabel.setForeground(java.awt.Color.white);
			jLabel.setIcon(new javax.swing.ImageIcon(Object.class.getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/unknown.png")));
		}
		return jLabel;
	}

	/**

	 * This method initializes BrowserPanel	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getBrowserPanel() {
		if (BrowserPanel == null) {
			java.awt.FlowLayout layFlowLayout4 = new java.awt.FlowLayout();

			BrowserPanel = new javax.swing.JPanel();
			BrowserPanel.setLayout(layFlowLayout4);
			layFlowLayout4.setHgap(0);
			layFlowLayout4.setVgap(0);
			BrowserPanel.setPreferredSize(new java.awt.Dimension(766,460));
			BrowserPanel.setBackground(new java.awt.Color(242,242,238));
			BrowserPanel.add(webBrowser);
		}
		return BrowserPanel;
	}

	/**

	 * This method initializes separador_01	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getSeparador_01() {
		if (separador_01 == null) {
			java.awt.FlowLayout layFlowLayout5 = new java.awt.FlowLayout();

			separador_01 = new javax.swing.JPanel();
			separador_01.setLayout(layFlowLayout5);
			layFlowLayout5.setHgap(0);
			layFlowLayout5.setVgap(0);
			separador_01.setPreferredSize(new java.awt.Dimension(765,2));
			separador_01.setBackground(new java.awt.Color(242,242,238));
		}
		return separador_01;
	}

	/**

	 * This method initializes jPanel4	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getJPanel4() {
		if (jPanel4 == null) {
			java.awt.FlowLayout layFlowLayout11 = new java.awt.FlowLayout();

			jPanel4 = new javax.swing.JPanel();
			jPanel4.setLayout(layFlowLayout11);
			jPanel4.setPreferredSize(new java.awt.Dimension(766,35));
			jPanel4.setBackground(new java.awt.Color(242,242,238));
			layFlowLayout11.setHgap(0);
			layFlowLayout11.setVgap(5);
			jPanel4.add(getJTextField(), null);
			jPanel4.add(getJButton(), null);
		}
		return jPanel4;
	}

	/**

	 * This method initializes separador_02	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private javax.swing.JPanel getSeparador_02() {
		if (separador_02 == null) {
			java.awt.FlowLayout layFlowLayout6 = new java.awt.FlowLayout();

			separador_02 = new javax.swing.JPanel();
			separador_02.setLayout(layFlowLayout6);
			separador_02.setPreferredSize(new java.awt.Dimension(766,2));
			separador_02.setBackground(new java.awt.Color(242,242,238));
			layFlowLayout6.setHgap(0);
			layFlowLayout6.setVgap(0);
		}
		return separador_02;
	}

	/**

	 * This method initializes jTextField	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private javax.swing.JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new javax.swing.JTextField();
			jTextField.setPreferredSize(new java.awt.Dimension(660,26));
		}
		return jTextField;
	}

	/**

	 * This method initializes jButton	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private javax.swing.JButton getJButton() {
		if (jButton == null) {
			jButton = new javax.swing.JButton();
			jButton.setText("Buscar");
			jButton.setIcon(new javax.swing.ImageIcon(Object.class.getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/view_next.png")));
			jButton.setBackground(new java.awt.Color(226,226,222));
		}
		return jButton;
	}
	
	public void setNewUrl(String newURL){
		
		try {
			webBrowser.setURL(new URL(newURL));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
