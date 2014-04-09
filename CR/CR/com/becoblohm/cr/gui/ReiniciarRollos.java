/*
 * $Id: ReiniciarRollos.java,v 1.3 2005/03/16 18:56:01 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.gui
 * Programa		: ReiniciarRollos.java
 * Creado por	: Programa8
 * Creado en 	: 03-mar-2005 10:10:20
 * (C) Copyright 2005 SuperFerretería EPA C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisión	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: ReiniciarRollos.java,v $
 * Revision 1.3  2005/03/16 18:56:01  programa8
 * Version 1.0 - Release Candidate 1
 * *- Log de errores en temp/errorsCR.log
 * *- Ajuste de excepciones en curso normal de inicio de aplicacion y repintado de factura
 * *- Reintento de obtención de comprobante fiscal
 * *- Ajuste en monto recaudado de caja
 *
 * Revision 1.2  2005/03/10 15:54:43  programa8
 * CR al 10/03/2005. Merging desde branch STABLE
 *
 * Revision 1.1.4.2  2005/03/08 14:58:56  programa8
 * Ajustes post-integracion
 *
 * Revision 1.1.4.1  2005/03/07 13:25:51  programa8
 * Implantación de interfaz para manejo de conteo de consumo de papel en impresoras
 *
 * Revision 1.1.2.1  2005/03/04 20:14:34  programa8
 * * Ajustes para trabajo sin administrador de ventanas
 * * Control de contadores de rollo de papel
 *
 * ===========================================================================
 */
package com.becoblohm.cr.gui;

import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.utiles.MensajesVentanas;
/**
 * <pre>
 * Proyecto: CR 
 * Clase: ReiniciarRollos
 * </pre>
 * <p>
 * <a href="ReiniciarRollos.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - $Author: programa8 $
 * @version $Revision: 1.3 $ - $Date: 2005/03/16 18:56:01 $
 * @since 03-mar-2005
 * @
 */
public class ReiniciarRollos extends JDialog  implements KeyListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ReiniciarRollos.class);

	private javax.swing.JPanel jContentPane = null;  //  @jve:decl-index=0:visual-constraint="11,5"
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel5 = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JPanel jPanel7 = null;
	private int opcion;
		
	private JPanel jPanel = null;
	private JCheckBox chRolloTicket = null;
	private JCheckBox chRolloAudit = null;
	private JPanel jPanel2 = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	/**
	 * This is the default constructor
	 */
	public ReiniciarRollos() {
		super();
		initialize();
		chRolloAudit.addKeyListener(this);
		chRolloTicket.addKeyListener(this);
		jButton.addKeyListener(this);
		jButton1.addKeyListener(this);
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

		this.setTitle("Cambiar rollos de impresora");
		this.setSize(347, 183);
		this.setContentPane(getJContentPane());
		this.setUndecorated(false);
		this.setModal(true);
		this.setBackground(new java.awt.Color(226,226,222));

		if (logger.isDebugEnabled()) {
			logger.debug("initialize() - end");
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
			java.awt.FlowLayout layFlowLayout7 = new java.awt.FlowLayout();
			layFlowLayout7.setHgap(5);
			layFlowLayout7.setVgap(5);
			jContentPane.setLayout(layFlowLayout7);
			jContentPane.add(getJPanel7(), null);
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.RAISED));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
			jContentPane.setPreferredSize(new java.awt.Dimension(292,200));
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
			jPanel1.setLayout(new FlowLayout());
			jPanel1.setPreferredSize(new java.awt.Dimension(330,60));
			jPanel1.setBackground(new java.awt.Color(242,242,238));
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opciones:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			jPanel1.add(getJPanel(), null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel1() - end");
		}
		return jPanel1;
	}
	/**
	 * This method initializes jPanel5
	 *
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel5() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel5() - start");
		}

		if(jPanel5 == null) {
			jPanel5 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
			layFlowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel5.setLayout(layFlowLayout2);
			jPanel5.add(getJLabel(), null);
			jPanel5.setPreferredSize(new java.awt.Dimension(330,40));
			jPanel5.setBackground(new java.awt.Color(69,107,127));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel5() - end");
		}
		return jPanel5;
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
			jLabel = new javax.swing.JLabel();
			jLabel.setText("Cambiar rollos de impresora");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel.setForeground(java.awt.Color.white);
			jLabel.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/printer_information.png")));
			jLabel.setPreferredSize(new java.awt.Dimension(320,32));
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
	private javax.swing.JPanel getJPanel7() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel7() - start");
		}

		if(jPanel7 == null) {
			jPanel7 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout31 = new java.awt.FlowLayout();
			layFlowLayout31.setHgap(0);
			layFlowLayout31.setVgap(0);
			jPanel7.setLayout(layFlowLayout31);
			jPanel7.setPreferredSize(new java.awt.Dimension(330,145));
			jPanel7.setBackground(new java.awt.Color(226,226,222));
			jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
			jPanel7.add(getJPanel5(), null);
			jPanel7.add(getJPanel1(), null);
			jPanel7.add(getJPanel2(), null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel7() - end");
		}
		return jPanel7;
	}
	
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel() - start");
		}

		if (jPanel == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			jPanel = new JPanel();
			jPanel.setLayout(flowLayout1);
			jPanel.setPreferredSize(new java.awt.Dimension(260,20));
			jPanel.setBackground(new java.awt.Color(242,242,238));
			flowLayout1.setVgap(0);
			jPanel.add(getChRolloTicket(), null);
			jPanel.add(getChRolloAudit(), null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel() - end");
		}
		return jPanel;
	}
	/**
	 * This method initializes chRolloTicket	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */    
	private JCheckBox getChRolloTicket() {
		if (logger.isDebugEnabled()) {
			logger.debug("getChRolloTicket() - start");
		}

		if (chRolloTicket == null) {
			chRolloTicket = new JCheckBox();
			chRolloTicket.setText("Rollo de tickets");
			chRolloTicket.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
			chRolloTicket.setBackground(new java.awt.Color(242,242,238));
			chRolloTicket.setSelected(true);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getChRolloTicket() - end");
		}
		return chRolloTicket;
	}
	/**
	 * This method initializes chRolloAudit	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */    
	private JCheckBox getChRolloAudit() {
		if (logger.isDebugEnabled()) {
			logger.debug("getChRolloAudit() - start");
		}

		if (chRolloAudit == null) {
			chRolloAudit = new JCheckBox();
			chRolloAudit.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
			chRolloAudit.setText("Rollo de auditoría");
			chRolloAudit.setBackground(new java.awt.Color(242,242,238));
			chRolloAudit.setSelected(true);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getChRolloAudit() - end");
		}
		return chRolloAudit;
	}
	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel2() - start");
		}

		if (jPanel2 == null) {
			FlowLayout flowLayout2 = new FlowLayout();
			jPanel2 = new JPanel();
			jPanel2.setLayout(flowLayout2);
			jPanel2.setPreferredSize(new java.awt.Dimension(330,35));
			jPanel2.setBackground(new java.awt.Color(226,226,222));
			flowLayout2.setAlignment(java.awt.FlowLayout.RIGHT);
			flowLayout2.setVgap(5);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton1(), null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel2() - end");
		}
		return jPanel2;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton() - start");
		}

		if (jButton == null) {
			jButton = new JHighlightButton();
			jButton.setText("Aceptar");
			jButton.setBackground(new java.awt.Color(226,226,222));
			jButton.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
			jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (logger.isDebugEnabled()) {
						logger
								.debug("actionPerformed(java.awt.event.ActionEvent) - start");
					}

					if (chRolloAudit.isSelected() || chRolloTicket.isSelected()) {
						Sesion.reiniciarRollosImpresora(chRolloTicket.isSelected(), 
								chRolloAudit.isSelected());
						String mensaje = "";
						if (chRolloTicket.isSelected()) {
							mensaje = " rollo de tickets";
						}
						if (chRolloAudit.isSelected()) {
							if (chRolloTicket.isSelected())
								mensaje += " y del";
							mensaje += " rollo de auditoría";
						}
						MensajesVentanas.aviso("Se inicializó el contador de consumo\n del" + mensaje);
						dispose();
					} else {
						MensajesVentanas.aviso("Debe seleccionar al menos uno de los rollos");
					}

					if (logger.isDebugEnabled()) {
						logger
								.debug("actionPerformed(java.awt.event.ActionEvent) - end");
					}
				}
			});
			
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton() - end");
		}
		return jButton;
	}
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton1() - start");
		}

		if (jButton1 == null) {
			jButton1 = new JHighlightButton();
			jButton1.setText("Cancelar");
			jButton1.setBackground(new java.awt.Color(226,226,222));
			jButton1.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
			jButton1.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (logger.isDebugEnabled()) {
						logger
								.debug("actionPerformed(java.awt.event.ActionEvent) - start");
					}

					dispose();

					if (logger.isDebugEnabled()) {
						logger
								.debug("actionPerformed(java.awt.event.ActionEvent) - end");
					}
				}
			});
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton1() - end");
		}
		return jButton1;
	}

	public void keyPressed(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (e.getSource() instanceof JButton) {
				((JButton)e.getSource()).doClick();
			} else {
				((JComponent)e.getSource()).transferFocus();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			jButton1.doClick();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - end");
		}
	}
	public void keyReleased(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyReleased(KeyEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("keyReleased(KeyEvent) - end");
		}
	}
	
	public void keyTyped(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyTyped(KeyEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("keyTyped(KeyEvent) - end");
		}
	}

	
 }  //  @jve:decl-index=0:visual-constraint="10,10"
