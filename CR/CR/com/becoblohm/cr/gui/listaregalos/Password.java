/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui.listaregalos
 * Programa   : Password.java
 * Creado por : rabreu
 * Creado en  : 03/07/2006
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
package com.becoblohm.cr.gui.listaregalos;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPasswordField;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstado;
import com.becoblohm.cr.utiles.MensajesVentanas;

/**
 * Descripción:
 * 
 */

public class Password extends JDialog implements ComponentListener, KeyListener, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(Password.class);

	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPasswordField jPasswordField = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JLabel jLabel1 = null;
	private boolean claveCorrecta;
	
	/**
	 * This is the default constructor
	 */
	public Password() {
		super(MensajesVentanas.ventanaActiva);
		initialize();
		
		jPasswordField.addKeyListener(this);
		
		jButton.addKeyListener(this);
		jButton.addActionListener(this);
		
		jButton1.addKeyListener(this);
		jButton1.addActionListener(this);
		
		this.claveCorrecta = false;
		
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		if (logger.isDebugEnabled())
			logger.debug("initialize() - start");

		this.setSize(260, 195);
		this.setContentPane(getJContentPane());
		this.setUndecorated(true);
		this.setModal(true);
		this.setBackground(new java.awt.Color(226,226,222));
		this.addComponentListener(this);
		
		if (logger.isDebugEnabled())
			logger.debug("initialize() - end");
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (logger.isDebugEnabled())
			logger.debug("getJContentPane() - start");

		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout6 = new java.awt.FlowLayout();
			layFlowLayout6.setHgap(4);
			layFlowLayout6.setVgap(4);
			jContentPane.setLayout(layFlowLayout6);
			jContentPane.add(getJPanel3(), null);
			jContentPane.add(getJPanel2(), null);
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(BevelBorder.RAISED));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJContentPane() - end");
		return jContentPane;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("keyPressed(KeyEvent) - start");

		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			dispose();
			this.claveCorrecta = false;
		}
		
		else if(e.getKeyCode() == KeyEvent.VK_ENTER)
			if(e.getSource().equals(this.getJPasswordField()))
				jButton1.doClick();
			else if(e.getSource().equals(this.getJButton1()))
				try {
					String clave = String.valueOf(jPasswordField.getPassword()).trim();
					MaquinaDeEstado.validarCierre(clave);
					this.claveCorrecta = true;
					dispose();
				} catch (UsuarioExcepcion e1) {
					logger.error("keyPressed(KeyEvent)", e1);
					this.claveCorrecta = false;
					MensajesVentanas.mensajeError(e1.getMensaje());
					jPasswordField.requestFocus();
					jPasswordField.selectAll();
				} catch (Exception e2) {
					logger.error("keyPressed(KeyEvent)", e2);
				}
			else if(e.getSource().equals(this.getJButton())) {
				dispose();
				this.claveCorrecta = false;
			}		

		if (logger.isDebugEnabled())
			logger.debug("keyPressed(KeyEvent) - end");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("keyReleased(KeyEvent) - start");

		if (logger.isDebugEnabled())
			logger.debug("keyReleased(KeyEvent) - end");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("keyTyped(KeyEvent) - start");
				
		if (logger.isDebugEnabled())
			logger.debug("keyTyped(KeyEvent) - end");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
/*	public void mouseReleased(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - start");
		}

		if(e.getSource().equals(this.getJButton())) {
			dispose();
			this.claveCorrecta = false;
		}
			
		else if(e.getSource().equals(this.getJButton1())) {
			try {
				String clave = String.valueOf(jPasswordField.getPassword()).trim();
				MaquinaDeEstado.validarCierre(clave);
				this.claveCorrecta = true;
				dispose();
			} catch (UsuarioExcepcion e1) {
				logger.error("keyPressed(KeyEvent)", e1);
				this.claveCorrecta = false;
				MensajesVentanas.mensajeError(e1.getMensaje());
				jPasswordField.requestFocus();
				jPasswordField.selectAll();
			} catch (Exception e2) {
				logger.error("keyPressed(KeyEvent)", e2);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - end");
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
/*	public void mouseEntered(MouseEvent e) {
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
/*	public void mouseExited(MouseEvent e) {
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
/*	public void mousePressed(MouseEvent e) {
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
/*	public void mouseClicked(MouseEvent e) {
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
		if (logger.isDebugEnabled())
			logger.debug("getJPanel() - start");

		if(jPanel == null) {
			jPanel = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout1.setHgap(5);
			layFlowLayout1.setVgap(5);
			jPanel.setLayout(layFlowLayout1);
			jPanel.add(getJPasswordField(), null);
			jPanel.setPreferredSize(new java.awt.Dimension(240,70));
			jPanel.setBackground(new java.awt.Color(242,242,238));
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Introduzca su Clave: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel() - end");
		return jPanel;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JPasswordField getJPasswordField() {
		if (logger.isDebugEnabled())
			logger.debug("getJTextField() - start");

		if(jPasswordField == null) {
			jPasswordField = new javax.swing.JPasswordField();
			jPasswordField.setPreferredSize(new java.awt.Dimension(150,20));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJTextField() - end");
		return jPasswordField;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel2() {
		if (logger.isDebugEnabled())
			logger.debug("getJPanel2() - start");

		if(jPanel2 == null) {
			jPanel2 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout11 = new java.awt.FlowLayout();
			layFlowLayout11.setAlignment(java.awt.FlowLayout.RIGHT);
			layFlowLayout11.setHgap(0);
			layFlowLayout11.setVgap(0);
			jPanel2.setLayout(layFlowLayout11);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(getJButton(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(240,35));
			jPanel2.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel2() - end");
		return jPanel2;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton() {
		if (logger.isDebugEnabled())
			logger.debug("getJButton() - start");

		if(jButton == null) {
			jButton = new JHighlightButton();
			jButton.setText("Cancelar");
			jButton.setBackground(new java.awt.Color(226,226,222));
			jButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
			jButton.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jButton.setMargin(new Insets(1,2,1,1));
			jButton.setPreferredSize(new java.awt.Dimension(99,26));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJButton() - end");
		return jButton;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton1() {
		if (logger.isDebugEnabled())
			logger.debug("getJButton1() - start");

		if(jButton1 == null) {
			jButton1 = new JHighlightButton();
			jButton1.setText("Aceptar");
			jButton1.setBackground(new java.awt.Color(226,226,222));
			jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
			jButton1.setMargin(new Insets(1,2,1,1));
			jButton1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jButton1.setPreferredSize(new java.awt.Dimension(99,26));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJButton1() - end");
		return jButton1;
	}
	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel3() {
		if (logger.isDebugEnabled())
			logger.debug("getJPanel3() - start");

		if(jPanel3 == null) {
			jPanel3 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout12 = new java.awt.FlowLayout();
			layFlowLayout12.setHgap(0);
			layFlowLayout12.setVgap(0);
			jPanel3.setLayout(layFlowLayout12);
			jPanel3.add(getJPanel4(), null);
			jPanel3.add(getJPanel(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(240,110));
			jPanel3.setBackground(new java.awt.Color(242,242,238));
			jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel3() - end");
		return jPanel3;
	}
	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel4() {
		if (logger.isDebugEnabled())
			logger.debug("getJPanel4() - start");

		if(jPanel4 == null) {
			jPanel4 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
			layFlowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout2.setVgap(3);
			layFlowLayout2.setHgap(5);
			jPanel4.setLayout(layFlowLayout2);
			jPanel4.add(getJLabel1(), null);
			jPanel4.setPreferredSize(new java.awt.Dimension(240,40));
			jPanel4.setBackground(new java.awt.Color(69,107,127));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel4() - end");
		return jPanel4;
	}
	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel1() {
		if (logger.isDebugEnabled())
			logger.debug("getJLabel1() - start");

		if(jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setText("Autorización");
			jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel1.setForeground(java.awt.Color.white);
			jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/key1.png")));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJLabel1() - end");
		return jLabel1;
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
			y= this.getBounds().y;
		} else
		if (this.getY() < 0) {
			y=0;
			x= this.getBounds().x;
		} else
		if ((this.getX() + width) > screen.width){
			x=screen.width;
			y= this.getBounds().y;
		} else
		if ((this.getY() + height) > screen.height){
			y=screen.height;
			x= this.getBounds().x;
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

	/**
	 * Método isClaveCorrecta
	 * 
	 * @return
	 * boolean
	 */
	public boolean isClaveCorrecta() {
		return claveCorrecta;
	}

	/* (no Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("actionPerformed(ActionEvent) - start");

		JButton btnComando = (JButton)e.getSource();
		if (btnComando.getText().equals("Aceptar"))
			try {
				String clave = String.valueOf(jPasswordField.getPassword()).trim();
				MaquinaDeEstado.validarCierre(clave);
				this.claveCorrecta = true;
				dispose();
			} catch (UsuarioExcepcion e1) {
				logger.error("keyPressed(KeyEvent)", e1);
				this.claveCorrecta = false;
				MensajesVentanas.mensajeError(e1.getMensaje());
				jPasswordField.requestFocus();
				jPasswordField.selectAll();
			} catch (Exception e2) {
				logger.error("keyPressed(KeyEvent)", e2);
			}
		else if (btnComando.getText().equals("Cancelar")){
			dispose();
			this.claveCorrecta = false;
		}

		if (logger.isDebugEnabled())
			logger.debug("actionPerformed(ActionEvent) - end");		
	}

}
