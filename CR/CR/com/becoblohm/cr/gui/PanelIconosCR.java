/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : PanelIconosCR.java
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

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;

/**
 * @author vmedina
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PanelIconosCR extends JPanel {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PanelIconosCR.class);

	private static javax.swing.JPanel jPanel2 = null;
	private static javax.swing.JPanel jPanel3 = null;
	private static javax.swing.JPanel jPanel4 = null;
	private static javax.swing.JLabel jLabel1 = null;
	private static javax.swing.JPanel jPanel5 = null;
	private static javax.swing.JPanel jPanel6 = null;
	private static javax.swing.JLabel jLabel = null;
	private static javax.swing.JPanel jPanel7 = null;
	private static javax.swing.JPanel jPanel8 = null;
	private static javax.swing.JPanel jPanel9 = null;
	private static javax.swing.JLabel jLabel2 = null;
	private static javax.swing.JTextField jTextField = null;
	private static javax.swing.JLabel jLabel3 = null;
	private static javax.swing.JPanel jPanel10 = null;
	private static javax.swing.JPanel jPanel11 = null;
	private static javax.swing.JLabel jLabel4 = null;
	private static javax.swing.JTextField jTextField1 = null;
	private static javax.swing.JPanel jPanel12 = null;
	private static javax.swing.JLabel jLabel5 = null;
	private static javax.swing.JTextField jTextField2 = null;
	private static javax.swing.JPanel jPanel13 = null;
	private static javax.swing.JLabel jLabel6 = null;
	private static javax.swing.JLabel jLabel7 = null;
	private static javax.swing.JLabel jLabel8 = null;
	private static javax.swing.JLabel jLabel9 = null;
	private static javax.swing.JLabel jLabel10 = null;
	private static javax.swing.JLabel jLabel15 = null;
	private static javax.swing.JPanel jPanel20 = null;
	private static javax.swing.JPanel jPanel21 = null;
	private static javax.swing.JLabel jLabel17 = null;
	private static javax.swing.JLabel jLabel18 = null;
	private static javax.swing.JLabel jLabel19 = null;
	private static javax.swing.JLabel jLabel20 = null;
	private static boolean calendarOn = false;
	private static boolean printerOk = true;
	private static boolean escanerOk = true;
	private static boolean visorOk = true;
	private static boolean avisoOn = false;
	private static boolean lineaOn = true;
	private static boolean entParcialOn = false;
	private static String usuario = "";
	private static String codCliente = "";
	private static String nomCliente = "";
	private int procSync = 0;
	
	/**
	 * This is the default constructor
	 */
	public PanelIconosCR() {
		super();
		initialize();
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

		this.add(getJPanel2(), null);
		this.add(getJPanel10(), null);
		this.setSize(659, 96);
		this.setName("PanelIconos");
		this.setVisible(false);
		this.setLocale(new java.util.Locale("es", "VE", ""));
		this.setBackground(new java.awt.Color(226,226,222));
		this.setPreferredSize(new java.awt.Dimension(659,90));

		if (logger.isDebugEnabled()) {
			logger.debug("initialize() - end");
		}
	}	

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private static javax.swing.JPanel getJPanel2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel2() - start");
		}

		if(jPanel2 == null) {
			jPanel2 = new javax.swing.JPanel();
			java.awt.GridLayout layGridLayout1 = new java.awt.GridLayout();
			layGridLayout1.setRows(1);
			layGridLayout1.setColumns(2);
			layGridLayout1.setHgap(1);
			layGridLayout1.setVgap(1);
			jPanel2.setLayout(layGridLayout1);
			jPanel2.add(getJPanel4(), null);
			jPanel2.add(getJPanel3(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(656,50));
			jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			jPanel2.setBackground(new java.awt.Color(242,242,238));
			jPanel2.setVisible(true);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel2() - end");
		}
		return jPanel2;
	}
	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private static javax.swing.JPanel getJPanel3() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel3() - start");
		}

		if(jPanel3 == null) {
			jPanel3 = new javax.swing.JPanel();
			java.awt.GridLayout layGridLayout2 = new java.awt.GridLayout();
			layGridLayout2.setRows(2);
			layGridLayout2.setColumns(1);
			layGridLayout2.setHgap(1);
			layGridLayout2.setVgap(1);
			jPanel3.setLayout(layGridLayout2);
			jPanel3.add(getJPanel9(), null);
			jPanel3.add(getJPanel6(), null);
			jPanel3.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel3() - end");
		}
		return jPanel3;
	}
	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private static javax.swing.JPanel getJPanel4() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel4() - start");
		}

		if(jPanel4 == null) {
			jPanel4 = new javax.swing.JPanel();
			jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT,0,0));
			jPanel4.add(getJLabel1(), getJLabel1().getName());
			jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
			jPanel4.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel4() - end");
		}
		return jPanel4;
	}
	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private static javax.swing.JLabel getJLabel1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel1() - start");
		}

		if(jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 16));
			try {
				String logo = new String(InitCR.preferenciasCR.getConfigStringForParameter("sistema", "logo"));
				jLabel1.setIcon(new javax.swing.ImageIcon(InitCR.class.getResource("/com/becoblohm/cr/gui/resources/icons/splash/"+logo)));
			} catch (NoSuchNodeException e) {
				logger.error("getJLabel1()", e);

				MensajesVentanas.mensajeError("Falla cargando preferencias para el logo de la Organización");
			} catch (UnidentifiedPreferenceException e) {
				MensajesVentanas.mensajeError("Falla cargando preferencias para el logo de la Organización");
				logger.error("getJLabel1()", e);
			}
			jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
			jLabel1.setName("jLabel1");
			jLabel1.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel1() - end");
		}
		return jLabel1;
	}
	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private static javax.swing.JPanel getJPanel5() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel5() - start");
		}

		if(jPanel5 == null) {
			jPanel5 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setHgap(0);
			layFlowLayout1.setVgap(0);
			jPanel5.setLayout(layFlowLayout1);
			jPanel5.add(getJLabel(), null);
			jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
			jPanel5.setPreferredSize(new java.awt.Dimension(326,24));
			jPanel5.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel5() - end");
		}
		return jPanel5;
	}
	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private static javax.swing.JPanel getJPanel6() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel6() - start");
		}

		if(jPanel6 == null) {
			jPanel6 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
			layFlowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout2.setHgap(1);
			layFlowLayout2.setVgap(0);
			jPanel6.setLayout(layFlowLayout2);
			jPanel6.add(getJPanel7(), null);
			jPanel6.add(getJPanel8(), null);
			jPanel6.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel6() - end");
		}
		return jPanel6;
	}
	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private static javax.swing.JLabel getJLabel() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel() - start");
		}

		if(jLabel == null) {
			jLabel = new ClockLabelCR();
			jLabel.setVisible(true);		
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel() - end");
		}
		return jLabel;
	}
	/**
	 * This method initializes jPanel7
	 * 
	 * @return javax.swing.JPanel
	 */
	private static javax.swing.JPanel getJPanel7() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel7() - start");
		}

		if(jPanel7 == null) {
			jPanel7 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout11 = new java.awt.FlowLayout();
			layFlowLayout11.setHgap(3);
			layFlowLayout11.setVgap(3);
			layFlowLayout11.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel7.setLayout(layFlowLayout11);
			jPanel7.add(getJLabel2(), null);
			jPanel7.add(getJTextField(), null);
			jPanel7.setPreferredSize(new java.awt.Dimension(260,24));
			jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
			jPanel7.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel7() - end");
		}
		return jPanel7;
	}
	/**
	 * This method initializes jPanel8
	 * 
	 * @return javax.swing.JPanel
	 */
	private static javax.swing.JPanel getJPanel8() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel8() - start");
		}

		if(jPanel8 == null) {
			jPanel8 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout3 = new java.awt.FlowLayout();
			layFlowLayout3.setHgap(0);
			layFlowLayout3.setVgap(0);
			jPanel8.setLayout(layFlowLayout3);
			jPanel8.add(getJLabel17(), null);			
			jPanel8.add(getJLabel3(), null);
			jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
			jPanel8.setPreferredSize(new java.awt.Dimension(65,24));
			jPanel8.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel8() - end");
		}
		return jPanel8;
	}
	/**
	 * This method initializes jPanel9
	 * 
	 * @return javax.swing.JPanel
	 */
	private static javax.swing.JPanel getJPanel9() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel9() - start");
		}

		if(jPanel9 == null) {
			jPanel9 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout12 = new java.awt.FlowLayout();
			layFlowLayout12.setVgap(0);
			layFlowLayout12.setHgap(1);
			layFlowLayout12.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel9.setLayout(layFlowLayout12);
			jPanel9.add(getJPanel5(), null);
			jPanel9.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel9() - end");
		}
		return jPanel9;
	}
	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private static javax.swing.JLabel getJLabel2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel2() - start");
		}

		if(jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setText(" Cajero:  ");
			jLabel2.setPreferredSize(new java.awt.Dimension(60,15));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel2() - end");
		}
		return jLabel2;
	}
	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private static javax.swing.JTextField getJTextField() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField() - start");
		}

		if(jTextField == null) {
			jTextField = new javax.swing.JTextField();
			jTextField.setEnabled(true);
			jTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			jTextField.setFocusable(false);
			jTextField.setPreferredSize(new java.awt.Dimension(180,16));
			jTextField.setBackground(new java.awt.Color(242,242,238));
			jTextField.setEditable(false);
			jTextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField() - end");
		}
		return jTextField;
	}
	/**
	 * This method initializes jLabel3
	 * 
	 * @return javax.swing.JLabel
	 */
	private static javax.swing.JLabel getJLabel3() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel3() - start");
		}

		if(jLabel3 == null) {
			jLabel3 = new javax.swing.JLabel();
			jLabel3.setText(String.valueOf(Sesion.getCaja().getNumero()));
			jLabel3.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel3() - end");
		}
		return jLabel3;
	}
	/**
	 * This method initializes jPanel10
	 * 
	 * @return javax.swing.JPanel
	 */
	private static javax.swing.JPanel getJPanel10() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel10() - start");
		}

		if(jPanel10 == null) {
			jPanel10 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout13 = new java.awt.FlowLayout();
			layFlowLayout13.setHgap(1);
			layFlowLayout13.setVgap(0);
			layFlowLayout13.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel10.setLayout(layFlowLayout13);
			jPanel10.add(getJPanel11(), null);
			jPanel10.add(getJPanel12(), null);
			jPanel10.add(getJPanel21(), null);
			jPanel10.setPreferredSize(new java.awt.Dimension(656,31));
			jPanel10.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			jPanel10.setBackground(new java.awt.Color(242,242,238));
			jPanel10.setVisible(true);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel10() - end");
		}
		return jPanel10;
	}
	/**
	 * This method initializes jPanel11
	 * 
	 * @return javax.swing.JPanel
	 */
	private static javax.swing.JPanel getJPanel11() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel11() - start");
		}

		if(jPanel11 == null) {
			jPanel11 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout14 = new java.awt.FlowLayout();
			layFlowLayout14.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout14.setHgap(3);
			layFlowLayout14.setVgap(5);
			jPanel11.setLayout(layFlowLayout14);
			jPanel11.add(getJLabel4(), null);
			jPanel11.add(getJTextField1(), null);
			jPanel11.setPreferredSize(new java.awt.Dimension(260,30));
			jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
			jPanel11.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel11() - end");
		}
		return jPanel11;
	}
	/**
	 * This method initializes jLabel4
	 * 
	 * @return javax.swing.JLabel
	 */
	private static javax.swing.JLabel getJLabel4() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel4() - start");
		}

		if(jLabel4 == null) {
			jLabel4 = new javax.swing.JLabel();
			jLabel4.setText(" Cliente: ");
			jLabel4.setBackground(new java.awt.Color(242,242,238));
			jLabel4.setPreferredSize(new java.awt.Dimension(60,15));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel4() - end");
		}
		return jLabel4;
	}
	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private static javax.swing.JTextField getJTextField1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField1() - start");
		}

		if(jTextField1 == null) {
			jTextField1 = new javax.swing.JTextField();
			jTextField1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			jTextField1.setEditable(false);
			if (CR.meVenta.getVenta() != null && CR.meVenta.getVenta().getCliente().getCodCliente() != null) jTextField1.setText(CR.meVenta.getVenta().getCliente().getNombreCompleto());
			jTextField1.setBackground(new java.awt.Color(242,242,238));
			jTextField1.setPreferredSize(new java.awt.Dimension(180,15));
			jTextField1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
			jTextField1.setFocusable(false);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField1() - end");
		}
		return jTextField1;
	}
	/**
	 * This method initializes jPanel12
	 * 
	 * @return javax.swing.JPanel
	 */
	private static javax.swing.JPanel getJPanel12() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel12() - start");
		}

		if(jPanel12 == null) {
			jPanel12 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout21 = new java.awt.FlowLayout();
			layFlowLayout21.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout21.setHgap(3);
			layFlowLayout21.setVgap(5);
			jPanel12.setLayout(layFlowLayout21);
			jPanel12.add(getJLabel5(), null);
			jPanel12.add(getJTextField2(), null);
			jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
			jPanel12.setPreferredSize(new java.awt.Dimension(200,30));
			jPanel12.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel12() - end");
		}
		return jPanel12;
	}
	/**
	 * This method initializes jLabel5
	 * 
	 * @return javax.swing.JLabel
	 */
	private static javax.swing.JLabel getJLabel5() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel5() - start");
		}

		if(jLabel5 == null) {
			jLabel5 = new javax.swing.JLabel();
			jLabel5.setText(" C.I./RIF: ");
			jLabel5.setPreferredSize(new java.awt.Dimension(60,15));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel5() - end");
		}
		return jLabel5;
	}
	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private static javax.swing.JTextField getJTextField2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField2() - start");
		}

		if(jTextField2 == null) {
			jTextField2 = new javax.swing.JTextField();
			if (CR.meVenta.getVenta() != null && CR.meVenta.getVenta().getCliente().getCodCliente() != null) jTextField2.setText(CR.meVenta.getVenta().getCliente().getCodCliente());
			jTextField2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			jTextField2.setEditable(false);
			jTextField2.setBackground(new java.awt.Color(242,242,238));
			jTextField2.setPreferredSize(new java.awt.Dimension(120,15));
			jTextField2.setFocusable(false);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField2() - end");
		}
		return jTextField2;
	}
	/**
	 * This method initializes jPanel13
	 * 
	 * @return javax.swing.JPanel
	 */
	private static javax.swing.JPanel getJPanel13() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel13() - start");
		}

		if(jPanel13 == null) {
			jPanel13 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout15 = new java.awt.FlowLayout();
			layFlowLayout15.setHgap(2);
			layFlowLayout15.setVgap(6);
			layFlowLayout15.setAlignment(java.awt.FlowLayout.RIGHT);
			jPanel13.setLayout(layFlowLayout15);
			jPanel13.add(getJLabel19(false), null);
			if(InitCR.isFallaDispositivo()){
				jPanel13.add(getJLabel6(false), null); 
				jPanel13.add(getJLabel9(false), null); 
				jPanel13.add(getJLabel10(false), null); 
			} else {
				jPanel13.add(getJLabel6(true), null);
				jPanel13.add(getJLabel9(true), null);
				jPanel13.add(getJLabel10(true), null);
			} 
			jPanel13.add(getJLabel7(Sesion.isCajaEnLinea()), null);
			jPanel13.add(getJLabel8(0), null);
			jPanel13.setPreferredSize(new java.awt.Dimension(110,28));
			jPanel13.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel13() - end");
		}
		return jPanel13;
	}

	/*
	 *	Iconos que muestran el estado de la caja durante la ejecución del sistema 
	 */
	public static void setCalendarOn(){
		if (logger.isDebugEnabled()) {
			logger.debug("setCalendarOn() - start");
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (logger.isDebugEnabled()) {
					logger.debug("run() - start");
				}

				getJLabel20().setVisible(true);

				if (logger.isDebugEnabled()) {
					logger.debug("run() - end");
				}
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("setCalendarOn() - end");
		}
	}

	public static void setCalendarOff(){
		if (logger.isDebugEnabled()) {
			logger.debug("setCalendarOff() - start");
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (logger.isDebugEnabled()) {
					logger.debug("run() - start");
				}

				getJLabel20().setVisible(false);

				if (logger.isDebugEnabled()) {
					logger.debug("run() - end");
				}
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("setCalendarOff() - end");
		}
	}

	public static void setEntregaParcialOn(){
		if (logger.isDebugEnabled()) {
			logger.debug("setEntregaParcialOn() - start");
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (logger.isDebugEnabled()) {
					logger.debug("run() - start");
				}

				getJLabel18().setVisible(true);

				if (logger.isDebugEnabled()) {
					logger.debug("run() - end");
				}
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("setEntregaParcialOn() - end");
		}
	}

	public static void setEntregaParcialOff(){
		if (logger.isDebugEnabled()) {
			logger.debug("setEntregaParcialOff() - start");
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (logger.isDebugEnabled()) {
					logger.debug("run() - start");
				}

				getJLabel18().setVisible(false);

				if (logger.isDebugEnabled()) {
					logger.debug("run() - end");
				}
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("setEntregaParcialOff() - end");
		}
	}

	public static void setExentoOn(){
		if (logger.isDebugEnabled()) {
			logger.debug("setExentoOn() - start");
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (logger.isDebugEnabled()) {
					logger.debug("run() - start");
				}

				getJLabel15().setVisible(true);

				if (logger.isDebugEnabled()) {
					logger.debug("run() - end");
				}
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("setExentoOn() - end");
		}
	}

	public static void setExentoOff(){
		if (logger.isDebugEnabled()) {
			logger.debug("setExentoOff() - start");
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (logger.isDebugEnabled()) {
					logger.debug("run() - start");
				}

				getJLabel15().setVisible(false);

				if (logger.isDebugEnabled()) {
					logger.debug("run() - end");
				}
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("setExentoOff() - end");
		}
	}

	public static void setPrinterOk(){
		if (logger.isDebugEnabled()) {
			logger.debug("setPrinterOk() - start");
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (logger.isDebugEnabled()) {
					logger.debug("run() - start");
				}

				getJLabel6(true);

				if (logger.isDebugEnabled()) {
					logger.debug("run() - end");
				}
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("setPrinterOk() - end");
		}
	}

	public static void setPrinterError(){
		if (logger.isDebugEnabled()) {
			logger.debug("setPrinterError() - start");
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (logger.isDebugEnabled()) {
					logger.debug("run() - start");
				}

				getJLabel6(false);

				if (logger.isDebugEnabled()) {
					logger.debug("run() - end");
				}
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("setPrinterError() - end");
		}
	}

	public static void setEscanerOk(){
		if (logger.isDebugEnabled()) {
			logger.debug("setEscanerOk() - start");
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (logger.isDebugEnabled()) {
					logger.debug("run() - start");
				}

				getJLabel9(true);

				if (logger.isDebugEnabled()) {
					logger.debug("run() - end");
				}
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("setEscanerOk() - end");
		}
	}

	public static void setEscanerError(){
		if (logger.isDebugEnabled()) {
			logger.debug("setEscanerError() - start");
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (logger.isDebugEnabled()) {
					logger.debug("run() - start");
				}

				getJLabel9(false);

				if (logger.isDebugEnabled()) {
					logger.debug("run() - end");
				}
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("setEscanerError() - end");
		}
	}

	public static void setVisorOk(){
		if (logger.isDebugEnabled()) {
			logger.debug("setVisorOk() - start");
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (logger.isDebugEnabled()) {
					logger.debug("run() - start");
				}

				getJLabel10(true);

				if (logger.isDebugEnabled()) {
					logger.debug("run() - end");
				}
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("setVisorOk() - end");
		}
	}

	public static void setVisorError(){
		if (logger.isDebugEnabled()) {
			logger.debug("setVisorError() - start");
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (logger.isDebugEnabled()) {
					logger.debug("run() - start");
				}

				getJLabel10(false);

				if (logger.isDebugEnabled()) {
					logger.debug("run() - end");
				}
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("setVisorError() - end");
		}
	}

	public static void setLineaOn(){
		if (logger.isDebugEnabled()) {
			logger.debug("setLineaOn() - start");
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (logger.isDebugEnabled()) {
					logger.debug("run() - start");
				}

				getJLabel7(true);

				if (logger.isDebugEnabled()) {
					logger.debug("run() - end");
				}
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("setLineaOn() - end");
		}
	}

	public static void setLineaOff(){
		if (logger.isDebugEnabled()) {
			logger.debug("setLineaOff() - start");
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (logger.isDebugEnabled()) {
					logger.debug("run() - start");
				}

				getJLabel7(false);

				if (logger.isDebugEnabled()) {
					logger.debug("run() - end");
				}
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("setLineaOff() - end");
		}
	}

	public static void setCajaSincronizada(){
		if (logger.isDebugEnabled()) {
			logger.debug("setCajaSincronizada() - start");
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (logger.isDebugEnabled()) {
					logger.debug("run() - start");
				}

				getJLabel8(1);

				if (logger.isDebugEnabled()) {
					logger.debug("run() - end");
				}
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("setCajaSincronizada() - end");
		}
	}

	public static void setCajaSincronizando(){
		if (logger.isDebugEnabled()) {
			logger.debug("setCajaSincronizando() - start");
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (logger.isDebugEnabled()) {
					logger.debug("run() - start");
				}

				getJLabel8(2);

				if (logger.isDebugEnabled()) {
					logger.debug("run() - end");
				}
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("setCajaSincronizando() - end");
		}
	}

	public static void setCajaNoSincronizada(){
		if (logger.isDebugEnabled()) {
			logger.debug("setCajaNoSincronizada() - start");
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (logger.isDebugEnabled()) {
					logger.debug("run() - start");
				}

				getJLabel8(0);

				if (logger.isDebugEnabled()) {
					logger.debug("run() - end");
				}
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("setCajaNoSincronizada() - end");
		}
	}

	public static void setAvisoOn(){
		if (logger.isDebugEnabled()) {
			logger.debug("setAvisoOn() - start");
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (logger.isDebugEnabled()) {
					logger.debug("run() - start");
				}

				getJLabel19(true);

				if (logger.isDebugEnabled()) {
					logger.debug("run() - end");
				}
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("setAvisoOn() - end");
		}
	}

	public static void setAvisoOff(){
		if (logger.isDebugEnabled()) {
			logger.debug("setAvisoOff() - start");
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (logger.isDebugEnabled()) {
					logger.debug("run() - start");
				}

				getJLabel19(false);

				if (logger.isDebugEnabled()) {
					logger.debug("run() - end");
				}
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("setAvisoOff() - end");
		}
	}
	
	/**
	 * This method initializes jLabel6
	 * 
	 * @return javax.swing.JLabel
	 */
	private static javax.swing.JLabel getJLabel6(boolean ok) {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel6(boolean) - start");
		}

		ImageIcon imagen = null;
		if (ok) imagen = new ImageIcon(InitCR.class.getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/printer_ok.png"));
		else imagen = new ImageIcon(InitCR.class.getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/printer_error.png"));
		
		if (jLabel6 == null)jLabel6 = new javax.swing.JLabel(imagen);
		else jLabel6.setIcon(imagen);

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel6(boolean) - end");
		}
		return jLabel6;
	}

	/**
	 * This method initializes jLabel7
	 * 
	 * @return javax.swing.JLabel
	 */
	private static javax.swing.JLabel getJLabel7(boolean ok) {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel7(boolean) - start");
		}

		ImageIcon imagen = null;
		if (ok) imagen = new ImageIcon(InitCR.class.getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/server_client_exchange.png"));
		else imagen = new ImageIcon(InitCR.class.getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/server_error.png"));
		
		if (jLabel7 == null)jLabel7 = new javax.swing.JLabel(imagen);
		else jLabel7.setIcon(imagen);

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel7(boolean) - end");
		}
		return jLabel7;
	}

	/**
	 * This method initializes jLabel8
	 * 
	 * @return javax.swing.JLabel
	 */
	private static javax.swing.JLabel getJLabel8(int proceso) {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel8(int) - start");
		}

		ImageIcon imagen = null;
		if (proceso == 1) imagen = new ImageIcon(InitCR.class.getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/data_ok.png"));
		else if (proceso == 2) imagen = new ImageIcon(InitCR.class.getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/data_replace.png"));
		else if (proceso == 0) imagen = new ImageIcon(InitCR.class.getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/data_error.png"));
		
		if (jLabel8 == null)jLabel8 = new javax.swing.JLabel(imagen);
		else jLabel8.setIcon(imagen);

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel8(int) - end");
		}
		return jLabel8;
	}

	/**
	 * This method initializes jLabel9
	 * 
	 * @return javax.swing.JLabel
	 */
	private static javax.swing.JLabel getJLabel9(boolean ok) {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel9(boolean) - start");
		}

		ImageIcon imagen = null;
		if (ok) imagen = new ImageIcon(InitCR.class.getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/escaner_ok.png"));
		else imagen = new ImageIcon(InitCR.class.getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/escaner_error.png"));
		
		if (jLabel9 == null)jLabel9 = new javax.swing.JLabel(imagen);
		else jLabel9.setIcon(imagen);

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel9(boolean) - end");
		}
		return jLabel9;
	}
	/**
	 * This method initializes jLabel10
	 * 
	 * @return javax.swing.JLabel
	 */
	private static javax.swing.JLabel getJLabel10(boolean ok) {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel10(boolean) - start");
		}

		ImageIcon imagen = null;
		if (ok) imagen = new ImageIcon(InitCR.class.getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/visor_ok.png"));
		else imagen = new ImageIcon(InitCR.class.getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/visor_error.png"));
		
		if (jLabel10 == null)jLabel10 = new javax.swing.JLabel(imagen);
		else jLabel10.setIcon(imagen);

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel10(boolean) - end");
		}
		return jLabel10;
	}
	/**
	 * This method initializes jLabel19
	 * 
	 * @return javax.swing.JLabel
	 */
	private static javax.swing.JLabel getJLabel19(boolean ok) {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel19(boolean) - start");
		}

		ImageIcon imagen = null;
		if (ok) imagen = new ImageIcon(InitCR.class.getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/warning.png"));
		else imagen = new ImageIcon(InitCR.class.getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/warning_off.png"));		
		if (jLabel19 == null)jLabel19 = new javax.swing.JLabel(imagen);
		else jLabel19.setIcon(imagen);

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel19(boolean) - end");
		}
		return jLabel19;
	}
	
	/**
	 * This method initializes jLabel15
	 * 
	 * @return javax.swing.JLabel
	 */
	private static javax.swing.JLabel getJLabel15() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel15() - start");
		}

		if(jLabel15 == null) {
			jLabel15 = new javax.swing.JLabel();
			jLabel15.setIcon(new javax.swing.ImageIcon(InitCR.class.getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/exento.png")));
			jLabel15.setVisible(false);
			jLabel15.setText("");
			//jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			//jLabel15.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			//jLabel15.setComponentOrientation(java.awt.ComponentOrientation.UNKNOWN);
			//jLabel15.setPreferredSize(new java.awt.Dimension(32,24));
			//jLabel15.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel15() - end");
		}
		return jLabel15;
	}
	
	/**
	 * This method initializes jPanel20
	 * 
	 * @return javax.swing.JPanel
	 */
	private static javax.swing.JPanel getJPanel20() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel20() - start");
		}

		if(jPanel20 == null) {
			jPanel20 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout19 = new java.awt.FlowLayout();
			layFlowLayout19.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout19.setHgap(2);
			layFlowLayout19.setVgap(6);
			jPanel20.setLayout(layFlowLayout19);
			jPanel20.add(getJLabel15(), null);
			jPanel20.add(getJLabel18(), null);
			jPanel20.add(getJLabel20(), null);
			jPanel20.setPreferredSize(new java.awt.Dimension(80,28));
			jPanel20.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel20() - end");
		}
		return jPanel20;
	}
	
	/**
	 * This method initializes jPanel21
	 * 
	 * @return javax.swing.JPanel
	 */
	private static javax.swing.JPanel getJPanel21() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel21() - start");
		}

		if(jPanel21 == null) {
			jPanel21 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout24 = new java.awt.FlowLayout();
			layFlowLayout24.setHgap(0);
			layFlowLayout24.setVgap(0);
			layFlowLayout24.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel21.setLayout(layFlowLayout24);
			jPanel21.add(getJPanel20(), null);
			jPanel21.add(getJPanel13(), null);
			jPanel21.setPreferredSize(new java.awt.Dimension(192,30));
			jPanel21.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
			jPanel21.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel21() - end");
		}
		return jPanel21;
	}

	/**
	 * This method initializes jLabel17
	 * 
	 * @return javax.swing.JLabel
	 */
	private static javax.swing.JLabel getJLabel17() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel17() - start");
		}

		if(jLabel17 == null) {
			jLabel17 = new javax.swing.JLabel();
			jLabel17.setText(" ");
			jLabel17.setIcon(new javax.swing.ImageIcon(InitCR.class.getResource("/com/becoblohm/cr/gui/resources/icons/ix24x24/caja.png")));
			jLabel17.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel17() - end");
		}
		return jLabel17;
	}

	/**
	 * This method initializes jLabel18
	 * 
	 * @return javax.swing.JLabel
	 */
	private static javax.swing.JLabel getJLabel18() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel18() - start");
		}

		if(jLabel18 == null) {
			jLabel18 = new javax.swing.JLabel();
			jLabel18.setText("");
			jLabel18.setIcon(new javax.swing.ImageIcon(InitCR.class.getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/moneybag.png")));
			jLabel18.setVisible(false);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel18() - end");
		}
		return jLabel18;
	}

	/**
	 * This method initializes jLabel20
	 * 
	 * @return javax.swing.JLabel
	 */
	private static javax.swing.JLabel getJLabel20() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel20() - start");
		}

		if(jLabel20 == null) {
			jLabel20 = new javax.swing.JLabel();
			jLabel20.setText("");
			jLabel20.setIcon(new javax.swing.ImageIcon(InitCR.class.getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/fecha-hora.png")));
			//jLabel20.setPreferredSize(new java.awt.Dimension(24,24));
			jLabel20.setVisible(false);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel20() - end");
		}
		return jLabel20;
	}
	
	/**
	 * Método getPanelLogo
	 * 
	 * @return JPanel
	 */
	public static JPanel getPanelLogo(){
		if (logger.isDebugEnabled()) {
			logger.debug("getPanelLogo() - start");
		}
		JPanel returnJPanel = getJPanel2();
		if (logger.isDebugEnabled()) {
			logger.debug("getPanelLogo() - end");
		}
		return returnJPanel; 
	}	

	/**
	 * Método getPanelIconos
	 * 
	 * @return JPanel
	 */
	public static JPanel getPanelIconos(){
		if (logger.isDebugEnabled()) {
			logger.debug("getPanelIconos() - start");
		}

		JPanel returnJPanel = getJPanel10();
		if (logger.isDebugEnabled()) {
			logger.debug("getPanelIconos() - end");
		}
		return returnJPanel; 
	}	

	/**
	 * Método setCliente
	 * 
	 * @param nombre
	 * @param codigo
	 */
	public static void setCliente(final String nombre, final String codigo){
		if (logger.isDebugEnabled()) {
			logger.debug("setCliente(String, String) - start");
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (logger.isDebugEnabled()) {
					logger.debug("run() - start");
				}

				getJTextField1().setText(nombre);
				getJTextField1().select(0,0);
				getJTextField2().setText(codigo);
				getJTextField2().select(0,0);

				if (logger.isDebugEnabled()) {
					logger.debug("run() - end");
				}
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("setCliente(String, String) - end");
		}
	}

	/**
	 * Método setUsuario
	 * 
	 * @param nombre
	 */
	public static void setUsuario(final String nombre){
		if (logger.isDebugEnabled()) {
			logger.debug("setUsuario(String) - start");
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (logger.isDebugEnabled()) {
					logger.debug("run() - start");
				}

				getJTextField().setText(nombre);
				getJTextField().select(0,0);

				if (logger.isDebugEnabled()) {
					logger.debug("run() - end");
				}
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("setUsuario(String) - end");
		}
	}

}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"