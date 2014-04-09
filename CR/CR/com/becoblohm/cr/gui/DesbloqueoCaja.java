/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : Desbloqueocaja.java
 * Creado por : Ileana Rojas <irojas@beco.com.ve>
 * Creado en  : May 14, 2004 - 9:30:29 AM
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
package com.becoblohm.cr.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EventListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.utiles.Control;
/**
 * @author irojas
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DesbloqueoCaja extends JDialog implements ComponentListener, KeyListener, MouseListener, EventListener, DocumentListener{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DesbloqueoCaja.class);

	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JLabel jLabel = null;
	private Vector<Object> valores = new Vector<Object>();
	private javax.swing.JPasswordField jPasswordField = null;
	private javax.swing.JPasswordField jPasswordField1 = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JLabel jLabel3 = null;
	private boolean manual = true;
	/**
	 * This is the default constructor
	 */
	public DesbloqueoCaja() {
		super(InitCR.verificador);
		initialize();
		
		jButton.addKeyListener(this);
		jButton.addMouseListener(this);
		
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);
		
		jPasswordField.addKeyListener(this);
		jPasswordField1.addKeyListener(this);
		
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

		this.setSize(410, 226);
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
			java.awt.FlowLayout layFlowLayout21 = new java.awt.FlowLayout();
			layFlowLayout21.setHgap(0);
			layFlowLayout21.setVgap(0);
			jContentPane.setLayout(layFlowLayout21);
			jContentPane.add(getJPanel3(), null);
			jContentPane.add(getJPanel2(), null);
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.RAISED));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
			jContentPane.setPreferredSize(new java.awt.Dimension(440,125));
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

		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			dispose();
		}
		
		else if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(e.getSource().equals(this.getJPasswordField())) { 
				this.getJPasswordField().transferFocus();
			} else if(e.getSource().equals(this.getJPasswordField1())) {
				this.getJPasswordField1().transferFocus();
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				if (cargarDatos()) {
					this.dispose();
				} else
					CR.inputEscaner.getDocument().addDocumentListener(this);
			} else if(e.getSource().equals(this.getJButton1())) {
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				if (cargarDatos()) {
					this.dispose();
				} else
					CR.inputEscaner.getDocument().addDocumentListener(this);
			} else if(e.getSource().equals(this.getJButton())){
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				dispose();
			}
		}
		
		else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			dispose();
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

		if(e.getSource().equals(jButton)){
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			dispose();
		}
		
		else if(e.getSource().equals(jButton1)){
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			if (cargarDatos()) {
				this.dispose();
			} else 
				CR.inputEscaner.getDocument().addDocumentListener(this);
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
			layFlowLayout1.setAlignment(java.awt.FlowLayout.CENTER);
			layFlowLayout1.setHgap(5);
			layFlowLayout1.setVgap(1);
			jPanel.setLayout(layFlowLayout1);
			jPanel.add(getJLabel3(), null);
			jPanel.setPreferredSize(new java.awt.Dimension(380,40));
			jPanel.setBackground(new java.awt.Color(242,242,238));
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Usuario ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
			jPanel.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel() - end");
		}
		return jPanel;
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
			layFlowLayout11.setVgap(3);
			jPanel2.setLayout(layFlowLayout11);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(getJButton(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(390,35));
			jPanel2.setBackground(new java.awt.Color(226,226,222));
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
	private javax.swing.JButton getJButton() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton() - start");
		}

		if(jButton == null) {
			jButton = new JHighlightButton();
			jButton.setText("Cancelar");
			jButton.setBackground(new java.awt.Color(226,226,222));
			jButton.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
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
	private javax.swing.JButton getJButton1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton1() - start");
		}

		if(jButton1 == null) {
			jButton1 = new JHighlightButton();
			jButton1.setText("Aceptar");
			jButton1.setBackground(new java.awt.Color(226,226,222));
			jButton1.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
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
			jPanel3.add(getJPanel1(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(390,150));
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
			layFlowLayout2.setVgap(5);
			layFlowLayout2.setHgap(5);
			jPanel4.setLayout(layFlowLayout2);
			jPanel4.add(getJLabel1(), null);
			jPanel4.setPreferredSize(new java.awt.Dimension(390,40));
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
			jLabel1.setText("Caja Bloqueada");
			jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel1.setForeground(java.awt.Color.white);
			jLabel1.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/lock_new.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel1() - end");
		}
		return jLabel1;
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
				java.awt.GridLayout layGridLayout4 = new java.awt.GridLayout();
				layGridLayout4.setColumns(2);
				layGridLayout4.setRows(2);
				jPanel1.setLayout(layGridLayout4);
				jPanel1.add(getJLabel(), null);
				jPanel1.add(getJPasswordField(), null);
				jPanel1.add(getJLabel2(), null);
				jPanel1.add(getJPasswordField1(), null);
				jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(4,4,4,4));
				jPanel1.setBackground(new java.awt.Color(242,242,238));
				jPanel1.setPreferredSize(new java.awt.Dimension(380,50));
			}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel1() - end");
		}
			return jPanel1;
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
			jLabel.setText(" Identificador de Usuario: ");
			jLabel.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel() - end");
		}
		return jLabel;
	}
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Object> getValores() {
		if (logger.isDebugEnabled()) {
			logger.debug("getValores() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getValores() - end");
		}
		return valores;
	}
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	boolean cargarDatos(){
		if (logger.isDebugEnabled()) {
			logger.debug("cargarDatos() - start");
		}

		valores = new Vector<Object>();
		String login = new String(this.jPasswordField.getPassword()).trim();
		String clave = new String(this.jPasswordField1.getPassword());
		if ((login != null)&&(clave != null)&&(login != "")&&(clave != "")){
			valores.addElement(login);
			valores.addElement(clave);
			Boolean entradaManual = (login.length() == Control.getLONGITUD_CODIGO() || login.length() == Control.getLONGITUD_CODIGO() + 1) ? Boolean.FALSE:Boolean.TRUE;
			entradaManual = new Boolean(entradaManual.booleanValue() || manual);
			valores.addElement(entradaManual);
		}
		else {
			if (logger.isDebugEnabled()) {
				logger.debug("cargarDatos() - end");
			}
			return false;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cargarDatos() - end");
		}
		return true;
	}
	
	/**
	 * This method initializes jPasswordField
	 * 
	 * @return javax.swing.JPasswordField
	 */
	private javax.swing.JPasswordField getJPasswordField() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPasswordField() - start");
		}

		if(jPasswordField == null) {
			jPasswordField = new javax.swing.JPasswordField();
			jPasswordField.addKeyListener(new java.awt.event.KeyAdapter() { 
				public void keyTyped(java.awt.event.KeyEvent e) {
					if (logger.isDebugEnabled()) {
						logger
								.debug("keyTyped(java.awt.event.KeyEvent) - start");
					}

					manual = true;

					if (logger.isDebugEnabled()) {
						logger.debug("keyTyped(java.awt.event.KeyEvent) - end");
					}
				}
			});
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPasswordField() - end");
		}
		return jPasswordField;
	}
	/**
	 * This method initializes jPasswordField1
	 * 
	 * @return javax.swing.JPasswordField
	 */
	private javax.swing.JPasswordField getJPasswordField1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPasswordField1() - start");
		}

		if(jPasswordField1 == null) {
			jPasswordField1 = new javax.swing.JPasswordField();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPasswordField1() - end");
		}
		return jPasswordField1;
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
			jLabel2.setText(" Contraseña: ");
			jLabel2.setBackground(new java.awt.Color(226,226,222));
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
			if(Sesion.getUsuarioActivo() != null) {
				jLabel3.setText(Sesion.getUsuarioActivo().getNombre());
			} else{ 
				jLabel3.setText(" ");
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel3() - end");
		}
		return jLabel3;
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

				getJPasswordField().setText(CR.inputEscaner.getText());
				getJPasswordField1().requestFocus();
				manual = false;

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