/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : SerialCaja.java
 * Creado por : irojas
 * Creado en  : 14-oct-04 08:01:23
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1
 * Fecha       : 14-oct-04 08:01:23
 * Analista    : irojas
 * Descripción : Implementación inicial.
 * =============================================================================
 */
package com.becoblohm.cr.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EventListener;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.utiles.MensajesVentanas;

/**
 * Descripción:
 * 
 */

public class SerialDispositivoFiscal extends JDialog implements ComponentListener, KeyListener, MouseListener, EventListener, DocumentListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(SerialDispositivoFiscal.class);

	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JTextField jTextField = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JLabel jLabel1 = null;
	private String serial;
	
	/**
	 * This is the default constructor
	 */
	public SerialDispositivoFiscal() {
		super();
		initialize();
		
		jTextField.addKeyListener(this);
		
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);
		
		serial = "";
		
		CR.inputEscaner.getDocument().addDocumentListener(this);
		
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

		this.setSize(295, 186);
		this.setContentPane(getJContentPane());
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
			jContentPane.setLayout(new FlowLayout());
			jContentPane.add(getJPanel3(), null);
			jContentPane.add(getJPanel2(), null);
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.RAISED));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
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
			if(e.getSource().equals(this.getJTextField())) {
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				if(getJTextField().getText().trim().equals("") || getJTextField().getText() == null) {
					MensajesVentanas.aviso("Debe introducir el serial del dispositivo para poder continuar");
					this.getJTextField().requestFocus();
					CR.inputEscaner.getDocument().addDocumentListener(this);
				} else {
					serial = getJTextField().getText().trim();
					dispose();
				}
			}
				
			else if(e.getSource().equals(this.getJButton1())) {
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				if(getJTextField().getText().trim().equals("") || getJTextField().getText() == null) {
					MensajesVentanas.aviso("Debe introducir el serial del dispositivo para poder continuar");
					this.getJTextField().requestFocus();
					CR.inputEscaner.getDocument().addDocumentListener(this);
				} else {
					serial = getJTextField().getText().trim();
					dispose();
				}
			}
		}
		
		else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			if(e.getSource().equals(this.getJButton1())) {
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				if(getJTextField().getText().trim().equals("") || getJTextField().getText() == null) {
					MensajesVentanas.aviso("Debe introducir el serial del dispositivo para poder continuar");
					this.getJTextField().requestFocus();
					CR.inputEscaner.getDocument().addDocumentListener(this);
				} else {
					serial = getJTextField().getText().trim();
					dispose();
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
			
		if(e.getSource().equals(this.getJButton1())) {
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			if(getJTextField().getText().trim().equals("") || getJTextField().getText() == null) {
				MensajesVentanas.aviso("Debe introducir el serial del dispositivo para poder continuar");
				this.getJTextField().requestFocus();
				CR.inputEscaner.getDocument().addDocumentListener(this);
			} else {
				serial = getJTextField().getText().trim();
				dispose();
			}
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
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout1.setHgap(5);
			layFlowLayout1.setVgap(1);
			jPanel.setLayout(layFlowLayout1);
			jPanel.add(getJTextField(), null);
			jPanel.setPreferredSize(new java.awt.Dimension(275,47));
			jPanel.setBackground(new java.awt.Color(242,242,238));
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Introduzca el serial del dispositivo fiscal: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel() - end");
		}
		return jPanel;
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
			jTextField.setPreferredSize(new java.awt.Dimension(200,20));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField() - end");
		}
		return jTextField;
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
			java.awt.FlowLayout layFlowLayout11 = new java.awt.FlowLayout();
			layFlowLayout11.setAlignment(java.awt.FlowLayout.RIGHT);
			layFlowLayout11.setHgap(0);
			layFlowLayout11.setVgap(0);
			jPanel2.setLayout(layFlowLayout11);
			jPanel2.add(getJButton1(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(280,35));
			jPanel2.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel2() - end");
		}
		return jPanel2;
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
			jButton1.setText("Aceptar");
			jButton1.setBackground(new java.awt.Color(226,226,222));
			jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
			jButton1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 11));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton1() - end");
		}
		return jButton1;
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
			java.awt.FlowLayout layFlowLayout12 = new java.awt.FlowLayout();
			layFlowLayout12.setHgap(0);
			layFlowLayout12.setVgap(0);
			jPanel3.setLayout(layFlowLayout12);
			jPanel3.add(getJPanel4(), null);
			jPanel3.add(getJPanel(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(280,100));
			jPanel3.setBackground(new java.awt.Color(242,242,238));
			jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
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
	private javax.swing.JPanel getJPanel4() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel4() - start");
		}

		if(jPanel4 == null) {
			jPanel4 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
			layFlowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout2.setVgap(10);
			layFlowLayout2.setHgap(5);
			jPanel4.setLayout(layFlowLayout2);
			jPanel4.add(getJLabel1(), null);
			jPanel4.setPreferredSize(new java.awt.Dimension(280,50));
			jPanel4.setBackground(new java.awt.Color(69,107,127));
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
	private javax.swing.JLabel getJLabel1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel1() - start");
		}

		if(jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setText("Serial Dispositivo Fiscal");
			jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jLabel1.setForeground(java.awt.Color.white);
			jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/cpu_view.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel1() - end");
		}
		return jLabel1;
	}
	/**
	 * Método getSerial
	 * 
	 * @return
	 * String
	 */
	public String getSerial() {
		if (logger.isDebugEnabled()) {
			logger.debug("getSerial() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getSerial() - end");
		}
		return serial;
	}

	/**
	 * Método changedUpdate
	 *
	 * @param e
	 */
	public void changedUpdate(DocumentEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("changedUpdate(DocumentEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("changedUpdate(DocumentEvent) - end");
		}
	}

	/**
	 * Método insertUpdate
	 *
	 * @param e
	 */
	public void insertUpdate(DocumentEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("insertUpdate(DocumentEvent) - start");
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (logger.isDebugEnabled()) {
					logger.debug("run() - start");
				}

				jTextField.setText(CR.inputEscaner.getText());
				jTextField.requestFocus();
				jTextField.selectAll();
				serial = getJTextField().getText().trim();

				if (logger.isDebugEnabled()) {
					logger.debug("run() - end");
				}
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("insertUpdate(DocumentEvent) - end");
		}
	}

	/**
	 * Método removeUpdate
	 *
	 * @param e
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
