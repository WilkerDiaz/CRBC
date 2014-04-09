/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : CargoPorServicio.java
 * Creado por : Victor Medina <linux@epa.com.ve>
 * Creado en  : Feb 27, 2004 - 1:41:58 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1
 * Fecha       : Feb 27, 2004
 * Analista    : Victor Medina <linux@epa.com.ve>
 * Descripción : 
 * =============================================================================
 */
package com.becoblohm.cr.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.EventListener;

import javax.swing.JDialog;
import javax.swing.event.DocumentEvent;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.AnulacionExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.utiles.MensajesVentanas;

/**
 * @author vmedina
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CargoPorServicio extends JDialog implements ComponentListener, KeyListener, MouseListener, EventListener, ActionListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(CargoPorServicio.class);

	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JButton jButton2 = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JLabel jLabel = null;
	
	private javax.swing.JPanel jPanel4 = null;	
	private javax.swing.JPanel jPanel8 = null;
	
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JLabel jLabel3 = null;
	private javax.swing.JTextField jTextField = null;
	private javax.swing.JTextField jTextField1 = null;
	private javax.swing.JTextField jTextField2 = null;
	private javax.swing.JCheckBox jCheckBox = null;
	private boolean cobroObligatorio = false;
	private DecimalFormat df = new DecimalFormat("#,##0.00");
	
	/**
	 * Constructor para CargoPorServicio
	 *
	 * @param cobrarCargoObligatorio
	 */
	public CargoPorServicio(boolean cobrarCargoObligatorio) {
		super(MensajesVentanas.ventanaActiva);
		initialize();
		
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);
				
		jButton2.addKeyListener(this);
		jButton2.addMouseListener(this);
		
		jCheckBox.addKeyListener(this);
		jCheckBox.addMouseListener(this);

		jCheckBox.addActionListener(this);
		
		this.cobroObligatorio = cobrarCargoObligatorio;
		
		if(cobrarCargoObligatorio) {
			this.getJCheckBox().setSelected(false);
			this.getJCheckBox().setEnabled(false);
		}
		actualizarEtiquetas();
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

		this.setSize(500, 316);
		this.setContentPane(getJContentPane());
		this.setResizable(false);
		this.setUndecorated(true);		
		this.setModal(true);
		this.setBackground(new java.awt.Color(226,226,222));
		this.addComponentListener(this);
		
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

		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout11 = new java.awt.FlowLayout();
			layFlowLayout11.setAlignment(java.awt.FlowLayout.CENTER);
			layFlowLayout11.setVgap(5);
			layFlowLayout11.setHgap(0);
			jContentPane.setLayout(layFlowLayout11);
			jContentPane.add(getJPanel3(), null);
			jContentPane.add(getJPanel1(), null);
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.RAISED));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
			jContentPane.setPreferredSize(new java.awt.Dimension(1046,246));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJContentPane() - end");
		}
		return jContentPane;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}
				
		if(e.getKeyCode() == KeyEvent.VK_ENTER){				
			if(e.getSource().equals(this.getJCheckBox())){
				this.getJButton2().requestFocus();
			}
			else if(e.getSource().equals(this.getJButton2())){
				/*if(this.jCheckBox.isSelected()) {
					try {
						CR.meServ.exonerarCargoPorServicio();
						dispose();
					} catch (ConexionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
						actualizarEtiquetas();
					} catch (ExcepcionCr e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
						actualizarEtiquetas();
					}
				} else {
					try {
						CR.meServ.cobroCargoPorServicio();
						dispose();
					} catch (ConexionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
						actualizarEtiquetas();
					} catch (ExcepcionCr e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
						actualizarEtiquetas();
					}
				}
				try {
					CR.meServ.getApartado().anularApartado();
					try { CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ..."); }
					catch (Exception ex) {
						logger.error("anularApartado()", ex);
					}
					CR.meServ.setApartado(null);
				} catch (BaseDeDatosExcepcion e1) {
					logger.error("keyPressed(KeyEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (AnulacionExcepcion e1) {
					logger.error("keyPressed(KeyEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (ConexionExcepcion e1) {
					logger.error("keyPressed(KeyEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
				}/**/
				try {
					if(this.jCheckBox.isSelected())
						CR.meServ.exonerarCargoPorServicio();
					else
						CR.meServ.cobroCargoPorServicio();	
					//PRUEBA
					CR.meServ.getApartado().anularApartado();
					CR.meServ.setApartado(null);
					dispose();
					//PRUEBA
				} catch (AnulacionExcepcion e1) {
					logger.error("keyPressed(KeyEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (ConexionExcepcion e1) {
					logger.error("keyPressed(KeyEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
					actualizarEtiquetas();
				} catch (ExcepcionCr e1) {
					logger.error("keyPressed(KeyEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
					actualizarEtiquetas();
				}
			}	
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
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - start");
		}

		if(e.getSource().equals(jButton1)){
			dispose();
		} else if(e.getSource().equals(jButton2)){
		/*	if(this.jCheckBox.isSelected()) {
				try {
					CR.meServ.exonerarCargoPorServicio();
					dispose();
				} catch (ConexionExcepcion e1) {
					logger.error("mouseClicked(MouseEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
					actualizarEtiquetas();
				} catch (ExcepcionCr e1) {
					logger.error("mouseClicked(MouseEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
					actualizarEtiquetas();
				}
			} else {
				try {
					CR.meServ.cobroCargoPorServicio();
					dispose();
				} catch (ConexionExcepcion e1) {
					logger.error("mouseClicked(MouseEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
					actualizarEtiquetas();
				} catch (ExcepcionCr e1) {
					logger.error("mouseClicked(MouseEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
					actualizarEtiquetas();
				}
			}
			try {
				CR.meServ.getApartado().anularApartado();
				try { CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ..."); }
				catch (Exception ex) {
					logger.error("anularApartado()", ex);
				}
				CR.meServ.setApartado(null);
			} catch (BaseDeDatosExcepcion e1) {
				logger.error("mouseClicked(MouseEvent)", e1);

				MensajesVentanas.mensajeError(e1.getMensaje());
			} catch (AnulacionExcepcion e1) {
				logger.error("mouseClicked(MouseEvent)", e1);

				MensajesVentanas.mensajeError(e1.getMensaje());
			} catch (ConexionExcepcion e1) {
				logger.error("mouseClicked(MouseEvent)", e1);

				MensajesVentanas.mensajeError(e1.getMensaje());
			}*/
			try {
				if(this.jCheckBox.isSelected())
					CR.meServ.exonerarCargoPorServicio();
				else
					CR.meServ.cobroCargoPorServicio();
				CR.meServ.getApartado().anularApartado();
				CR.meServ.setApartado(null);
				dispose();
			
			} catch (AnulacionExcepcion e1) {
				logger.error("mouseClicked(MouseEvent)", e1);
				MensajesVentanas.mensajeError(e1.getMensaje());
			
			} catch (ConexionExcepcion e1) {
				logger.error("mouseClicked(MouseEvent)", e1);
				MensajesVentanas.mensajeError(e1.getMensaje());
				actualizarEtiquetas();
				
			} catch (ExcepcionCr e1) {
				logger.error("mouseClicked(MouseEvent)", e1);
				MensajesVentanas.mensajeError(e1.getMensaje());
				actualizarEtiquetas();
			}
		}
		if(e.getSource().equals(jCheckBox)){
			actualizarEtiquetas();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - end");
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseEntered(MouseEvent) - start");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("mouseEntered(MouseEvent) - end");
		}
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseExited(MouseEvent) - start");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("mouseExited(MouseEvent) - end");
		}
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mousePressed(MouseEvent) - start");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("mousePressed(MouseEvent) - end");
		}
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseReleased(MouseEvent) - start");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("mouseReleased(MouseEvent) - end");
		}
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
			java.awt.FlowLayout layFlowLayout12 = new java.awt.FlowLayout();
			layFlowLayout12.setAlignment(java.awt.FlowLayout.RIGHT);
			layFlowLayout12.setHgap(0);
			layFlowLayout12.setVgap(0);
			jPanel1.setLayout(layFlowLayout12);
			jPanel1.add(getJButton2(), null);
			jPanel1.add(getJButton1(), null);
			jPanel1.setPreferredSize(new java.awt.Dimension(470,35));
			jPanel1.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel1() - end");
		}
		return jPanel1;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton1() - start");
		}

		if(jButton1 == null) {
			jButton1 = new JHighlightButton();
			jButton1.setText("Cancelar");
			jButton1.setBackground(new java.awt.Color(226,226,222));
			jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton1() - end");
		}
		return jButton1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton2() - start");
		}

		if(jButton2 == null) {
			jButton2 = new JHighlightButton();
			jButton2.setText("Aceptar");
			jButton2.setBackground(new java.awt.Color(226,226,222));
			jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton2() - end");
		}
		return jButton2;
	}
	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel3() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel3() - start");
		}

		if(jPanel3 == null) {
			jPanel3 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
			layFlowLayout2.setAlignment(java.awt.FlowLayout.CENTER);
			layFlowLayout2.setVgap(0);
			layFlowLayout2.setHgap(0);
			jPanel3.setLayout(layFlowLayout2);
			jPanel3.add(getJPanel2(), null);
			jPanel3.add(getJPanel4(), null);
			jPanel3.add(getJPanel8(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(470,232));
			jPanel3.setBackground(new java.awt.Color(242,242,238));
			jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel3() - end");
		}
		return jPanel3;
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
			java.awt.FlowLayout layFlowLayout3 = new java.awt.FlowLayout();
			layFlowLayout3.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout3.setVgap(10);
			layFlowLayout3.setHgap(5);
			jPanel2.setLayout(layFlowLayout3);
			jPanel2.add(getJLabel(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(470,50));
			jPanel2.setBackground(new java.awt.Color(69,107,127));
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
			jLabel = new javax.swing.JLabel();
			jLabel.setText("Cobro Cargo Por Servicio");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jLabel.setForeground(java.awt.Color.white);
			jLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/money_envelope.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel() - end");
		}
		return jLabel;
	}
	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel4() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel4() - start");
		}

		if(jPanel4 == null) {
			jPanel4 = new javax.swing.JPanel();
			jPanel4.add(getJPanel(), null);
			jPanel4.setPreferredSize(new java.awt.Dimension(470,150));
			jPanel4.setBackground(new java.awt.Color(242,242,238));
			jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1), " Detalle del Cargo por Servicio ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel4() - end");
		}
		return jPanel4;
	}
	/**
	 * This method initializes jPanel8
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel8() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel8() - start");
		}

		if(jPanel8 == null) {
			jPanel8 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout31 = new java.awt.FlowLayout();
			layFlowLayout31.setHgap(0);
			layFlowLayout31.setVgap(3);
			jPanel8.setLayout(layFlowLayout31);
			jPanel8.add(getJCheckBox(), null);
			jPanel8.setPreferredSize(new java.awt.Dimension(466,30));
			jPanel8.setBackground(new java.awt.Color(242,242,238));
			jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel8() - end");
		}
		return jPanel8;
	}
	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 */
	public void changedUpdate(DocumentEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("changedUpdate(DocumentEvent) - start");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("changedUpdate(DocumentEvent) - end");
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 */
	public void insertUpdate(DocumentEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("insertUpdate(DocumentEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("insertUpdate(DocumentEvent) - end");
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
	 */
	public void removeUpdate(DocumentEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("removeUpdate(DocumentEvent) - start");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("removeUpdate(DocumentEvent) - end");
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
			jPanel.setLayout(new java.awt.FlowLayout());
			jPanel.add(getJLabel3(), null);
			jPanel.add(getJTextField2(), null);
			jPanel.add(getJLabel1(), null);
			jPanel.add(getJTextField(), null);
			jPanel.add(getJLabel2(), null);
			jPanel.add(getJTextField1(), null);
			jPanel.setBackground(new java.awt.Color(242,242,238));
			jPanel.setPreferredSize(new java.awt.Dimension(400,115));
			jPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel() - end");
		}
		return jPanel;
	}
	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel1() - start");
		}

		if(jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setText("  Cargo por Servicio:");
			jLabel1.setPreferredSize(new java.awt.Dimension(200,30));
			jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel1() - end");
		}
		return jLabel1;
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
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setText("  Total Anulación:");
			jLabel2.setPreferredSize(new java.awt.Dimension(200,30));
			jLabel2.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel2() - end");
		}
		return jLabel2;
	}
	/**
	 * This method initializes jLabel3
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel3() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel3() - start");
		}

		if(jLabel3 == null) {
			jLabel3 = new javax.swing.JLabel();
			jLabel3.setText("  Abonos Realizados:");
			jLabel3.setPreferredSize(new java.awt.Dimension(200,30));
			jLabel3.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel3() - end");
		}
		return jLabel3;
	}
	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField() - start");
		}

		if(jTextField == null) {
			jTextField = new javax.swing.JTextField();
			jTextField.setBackground(new java.awt.Color(242,242,238));
			jTextField.setBorder(javax.swing.BorderFactory.createMatteBorder(0,0,1,0,(new java.awt.Color(0,0,0))));
			jTextField.setPreferredSize(new java.awt.Dimension(160,30));
			jTextField.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jTextField.setEditable(false);
			jTextField.setText("00,00");
			jTextField.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
			jTextField.setFocusable(false);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField() - end");
		}
		return jTextField;
	}
	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField1() - start");
		}

		if(jTextField1 == null) {
			jTextField1 = new javax.swing.JTextField();
			jTextField1.setBackground(new java.awt.Color(242,242,238));
			jTextField1.setPreferredSize(new java.awt.Dimension(160,30));
			jTextField1.setBorder(javax.swing.BorderFactory.createMatteBorder(0,0,0,0,(new java.awt.Color(0,0,0))));
			jTextField1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jTextField1.setEditable(false);
			jTextField1.setText("00,00");
			jTextField1.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
			jTextField1.setFocusable(false);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField1() - end");
		}
		return jTextField1;
	}
	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField2() - start");
		}

		if(jTextField2 == null) {
			jTextField2 = new javax.swing.JTextField();
			jTextField2.setBackground(new java.awt.Color(242,242,238));
			jTextField2.setPreferredSize(new java.awt.Dimension(160,30));
			jTextField2.setBorder(javax.swing.BorderFactory.createMatteBorder(0,0,0,0,(new java.awt.Color(0,0,0))));
			jTextField2.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jTextField2.setEditable(false);
			jTextField2.setText("00,00");
			jTextField2.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
			jTextField2.setFocusable(false);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField2() - end");
		}
		return jTextField2;
	}
	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private javax.swing.JCheckBox getJCheckBox() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJCheckBox() - start");
		}

		if(jCheckBox == null) {
			jCheckBox = new javax.swing.JCheckBox();
			jCheckBox.setText("Exonerar");
			jCheckBox.setBackground(new java.awt.Color(242,242,238));
			jCheckBox.setPreferredSize(new java.awt.Dimension(400,20));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJCheckBox() - end");
		}
		return jCheckBox;
	}
	
	/**
	 * This method actualizarEtiquetas
	 * 
	 * @return void
	 */
	private void actualizarEtiquetas() {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarEtiquetas() - start");
		}

		if(jCheckBox.isSelected()) {
			this.getJTextField2().setText(df.format(CR.meServ.getApartado().montoAbonos()));
			this.getJTextField().setText ("00,00");
			this.getJTextField1().setText(df.format(CR.meServ.getApartado().montoAbonos()));
		} else {
			this.getJTextField2().setText(df.format(CR.meServ.getApartado().montoAbonos()));
			this.getJTextField().setText (df.format(CR.meServ.getApartado().calcularMtoCargoPorServicio()));
			this.getJTextField1().setText(df.format(CR.meServ.getApartado().montoAbonos()- CR.meServ.getApartado().calcularMtoCargoPorServicio()));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarEtiquetas() - end");
		}
	}

	/**
	 * Método actionPerformed
	 *
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("actionPerformed(ActionEvent) - start");
		}

		if(e.getSource().equals(this.getJCheckBox())) {
			actualizarEtiquetas();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("actionPerformed(ActionEvent) - end");
		}
	}

	/**
	 * Método componentHidden
	 *
	 * @param e
	 */
	public void componentHidden(ComponentEvent e) {
	}

	/**
	 * Método componentMoved
	 *
	 * @param e
	 */
	public void componentMoved(ComponentEvent e) {
		int width = (int)this.getSize().getWidth();
		int height = (int)this.getSize().getHeight();
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x=0,y=0;
		
		if (this.getX() < 0) {
			x=0;
			y= (int)this.getBounds().y;
		} else
		if (this.getY() < 0) {
			y=0;
			x= (int)this.getBounds().x;
		} else
		if ((this.getX() + width) > screen.width){
			x=screen.width;
			y= (int)this.getBounds().y;
		} else
		if ((this.getY() + height) > screen.height){
			y=screen.height;
			x= (int)this.getBounds().x;
		} else {
			x=this.getX();
			y=this.getY();	
		}
		
		this.setBounds(x, y, width, height);
	}

	/**
	 * Método componentResized
	 *
	 * @param e
	 */
	public void componentResized(ComponentEvent e) {
	}

	/**
	 * Método componentShown
	 *
	 * @param e
	 */
	public void componentShown(ComponentEvent e) {
	}
}