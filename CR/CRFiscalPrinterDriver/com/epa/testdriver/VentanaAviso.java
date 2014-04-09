/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : AnularProducto.java
 * Creado por : Gabriel Martinelli <gmartinelli@beco.com.ve>
 * Creado en  : Mar 29, 2004 - 3:16:30 PM
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
package com.epa.testdriver;

import org.apache.log4j.Logger;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextArea;


import java.awt.FlowLayout;
import javax.swing.JPanel;
import java.awt.BorderLayout;
/**
 * @author vmedina
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class VentanaAviso extends JDialog implements KeyListener, MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2027892590594017731L;

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(VentanaAviso.class);

	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JLabel jLabel1 = null;
	private double anchoTexto = 0;
	private double altoTexto = 0;
	@SuppressWarnings("unused")
	private String iconUrl = null;
	private String mensaje = null;
	private String titulo = null;
	private JTextArea jTextArea = null;
	private JPanel jPanel = null;
	/**
	 * This is the default constructor
	 */
	public VentanaAviso(String title, String xMensaje, String icon) {
		super(MensajesVentanas.ventanaActiva);
		iconUrl = icon;
		mensaje = xMensaje;
		titulo = title;
		jTextArea = new JTextArea();
		jTextArea.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
		jTextArea.setText(xMensaje);
		altoTexto = jTextArea.getUI().getPreferredSize(jTextArea).getHeight();
		anchoTexto = jTextArea.getUI().getPreferredSize(jTextArea).getWidth();
		jTextArea = null;
		initialize();
		
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);
		
		
		getJButton1().requestFocus();
	}
	
	public VentanaAviso(String title, String xMensaje, String icon, boolean inicio) {
		super();
		iconUrl = icon;
		mensaje = xMensaje;
		titulo = title;
		jTextArea = new JTextArea();
		jTextArea.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
		jTextArea.setText(xMensaje);
		altoTexto = jTextArea.getUI().getPreferredSize(jTextArea).getHeight();
		anchoTexto = jTextArea.getUI().getPreferredSize(jTextArea).getWidth();
		jTextArea = null;
		initialize();
		
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);
		
		
		getJButton1().requestFocus();
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

		if (anchoTexto < 300) {
			this.setSize(300, (int) (altoTexto + 150));
		} else {
			this.setSize((int) (anchoTexto + 50), (int) (altoTexto + 150));
		}
		this.setResizable(false);
		this.setTitle(titulo);
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

		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.RAISED));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
			jContentPane.add(getJPanel3(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanel2(), java.awt.BorderLayout.SOUTH);
			jContentPane.setFocusable(false);
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

		if (e.getKeyCode() == KeyEvent.VK_A) {
			dispose();
		}
		else if((e.getKeyCode() == KeyEvent.VK_ENTER) || (e.getKeyCode() == KeyEvent.VK_SPACE)){
			if(e.getSource() == jButton1){
				dispose();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
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
	public void mouseClicked(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - start");
		}

		if(e.getSource() == jButton1){
			dispose();
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
	public void mouseReleased(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseReleased(MouseEvent) - start");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("mouseReleased(MouseEvent) - end");
		}
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
			layFlowLayout11.setHgap(5);
			layFlowLayout11.setVgap(8);
			jPanel2.setLayout(layFlowLayout11);
			jPanel2.add(getJButton1(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(95,40));
			jPanel2.setBackground(new java.awt.Color(226,226,222));
			jPanel2.setFocusable(false);
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
			jButton1 = new JButton();
			jButton1.setText("Aceptar");
			jButton1.setBackground(new java.awt.Color(226,226,222));
			//jButton1.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
			jButton1.setMnemonic(java.awt.event.KeyEvent.VK_A);
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
			jPanel3.setBackground(new java.awt.Color(242,242,238));
			jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
			jPanel3.setPreferredSize(new java.awt.Dimension(this.getWidth()-20,this.getHeight()-80));
			jPanel3.add(getJPanel4(), null);
			jPanel3.add(getJPanel(), null);
			jPanel3.setFocusable(false);
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
			FlowLayout flowLayout1 = new FlowLayout();
			jPanel4 = new javax.swing.JPanel();
			jPanel4.setLayout(flowLayout1);
			flowLayout1.setAlignment(FlowLayout.LEFT);
			jPanel4.setPreferredSize(new java.awt.Dimension((int)getJPanel3().getPreferredSize().getWidth()-2,40));
			jPanel4.setBackground(new java.awt.Color(69,107,127));
			flowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel4.add(getJLabel1(), null);
			jPanel4.setFocusable(false);
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
			jLabel1.setText(titulo);
			jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel1.setForeground(java.awt.Color.white);
			//jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource(iconUrl)));
			jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jLabel1.setFocusable(false);
			jLabel1.setSize((int)anchoTexto, (int)altoTexto);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel1() - end");
		}
		return jLabel1;
	}
	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */    
	private JTextArea getJTextArea() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextArea() - start");
		}

		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setText(mensaje);
			jTextArea.setBackground(new java.awt.Color(242,242,238));
			jTextArea.setEditable(false);
			jTextArea.setLineWrap(false);
			jTextArea.setFocusable(false);
			jTextArea.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextArea() - end");
		}
		return jTextArea;
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
			FlowLayout flowLayout2 = new FlowLayout();
			jPanel = new JPanel();
			jPanel.setLayout(flowLayout2);
			jPanel.setBackground(new java.awt.Color(242,242,238));
			flowLayout2.setHgap(5);
			flowLayout2.setVgap(10);
			jPanel.add(getJTextArea(), null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel() - end");
		}
		return jPanel;
	}
  }  //  @jve:decl-index=0:visual-constraint="10,10"