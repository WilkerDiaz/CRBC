/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : CambioDireccion.java
 * Creado por : Ileana Rojas <irojas@beco.com.ve>
 * Creado en  : May 27, 2004 - 4:33:30 PM
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
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EventListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.gui.UpperCaseField;
import com.becoblohm.cr.utiles.MensajesVentanas;
/**
 * @author vmedina
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DatosInvitado extends JDialog implements ComponentListener, ActionListener, EventListener, KeyListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton jButtonAceptar = null;
	private javax.swing.JButton jButtonCancelar = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel5 = null;
	private String dedicatoria = null;
	private String nombre = null;

	private javax.swing.JTextPane jTextPane = null;
	private javax.swing.JScrollPane jScrollPane = null;
	private javax.swing.JTextField jTextField = null;
	private javax.swing.JLabel jLabel = null;
	/**
	 * This is the default constructor
	 */
	public DatosInvitado() {
		super(MensajesVentanas.ventanaActiva);
		initialize();

		jButtonAceptar.addActionListener(this);
		jButtonAceptar.addKeyListener(this);
		jButtonCancelar.addActionListener(this);
		jButtonCancelar.addKeyListener(this);
		jTextField.addKeyListener(this);
		jTextPane.addKeyListener(this);
		
		if(CR.meVenta.getVenta() != null && CR.meVenta.getVenta().getCliente().getNombreCompleto() != null){
			getJTextField().setText(CR.meVenta.getVenta().getCliente().getNombreCompleto());
			jTextPane.requestFocus();
		}else if(CR.meServ.getListaRegalos()!=null && CR.meServ.getListaRegalos().getCliente()!=null){
			getJTextField().setText(CR.meServ.getListaRegalos().getCliente().getNombreCompleto());
			jTextPane.requestFocus();
		}
		
		if(CR.meServ.getListaRegalos()!=null && CR.meServ.getListaRegalos().getDedicatoria()!=null)
			jTextPane.setText(CR.meServ.getListaRegalos().getDedicatoria());
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(430, 280);
		this.setContentPane(getJContentPane());
		this.setUndecorated(true);
		this.setModal(true);
		this.setBackground(new java.awt.Color(226,226,222));
		this.setTitle("Datos del Invitado");
		this.addComponentListener(this);
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout14 = new java.awt.FlowLayout();
			layFlowLayout14.setHgap(0);
			layFlowLayout14.setVgap(0);
			jContentPane.setLayout(layFlowLayout14);
			jContentPane.add(getJPanel3(), null);
			jContentPane.add(getJPanel2(), null);
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(BevelBorder.RAISED));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel2() {
		if(jPanel2 == null) {
			jPanel2 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout11 = new java.awt.FlowLayout();
			layFlowLayout11.setAlignment(java.awt.FlowLayout.RIGHT);
			layFlowLayout11.setHgap(0);
			layFlowLayout11.setVgap(3);
			jPanel2.setLayout(layFlowLayout11);
			jPanel2.add(getJButtonAceptar(), null);
			jPanel2.add(getJButtonCancelar(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(410,30));
			jPanel2.setBackground(new java.awt.Color(226,226,222));
		}
		return jPanel2;
	}

	/**
	 * This method initializes jButtonAceptar
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButtonAceptar() {
		if(jButtonAceptar == null) {
			jButtonAceptar = new JHighlightButton();
			jButtonAceptar.setText("Aceptar");
			jButtonAceptar.setBackground(new java.awt.Color(226,226,222));
			jButtonAceptar.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
		}
		return jButtonAceptar;
	}
	/**
	 * This method initializes jButtonAceptar
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButtonCancelar() {
		if(jButtonCancelar == null) {
			jButtonCancelar = new JHighlightButton();
			jButtonCancelar.setText("Cancelar");
			jButtonCancelar.setBackground(new java.awt.Color(226,226,222));
			jButtonCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
		}
		return jButtonCancelar;
	}
	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel3() {
		if(jPanel3 == null) {
			jPanel3 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout12 = new java.awt.FlowLayout();
			layFlowLayout12.setHgap(0);
			layFlowLayout12.setVgap(0);
			jPanel3.setLayout(layFlowLayout12);
			jPanel3.add(getJPanel4(), null);
			jPanel3.add(getJPanel5(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(410,210));
			jPanel3.setBackground(new java.awt.Color(242,242,238));
			jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
		}
		return jPanel3;
	}
	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel4() {
		if(jPanel4 == null) {
			jPanel4 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
			layFlowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout2.setVgap(2);
			layFlowLayout2.setHgap(5);
			jPanel4.setLayout(layFlowLayout2);
			jPanel4.add(getJLabel1(), null);
			jPanel4.setPreferredSize(new java.awt.Dimension(410,50));
			jPanel4.setBackground(new java.awt.Color(69,107,127));
			jPanel4.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
		}
		return jPanel4;
	}
	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel1() {
		if(jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setText("Datos del Invitado");
			jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel1.setForeground(java.awt.Color.white);
			jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix48x48/id_card.png")));
		}
		return jLabel1;
	}
	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel1() {
		if(jPanel1 == null) {
			jPanel1 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout13 = new java.awt.FlowLayout();
			layFlowLayout13.setHgap(5);
			layFlowLayout13.setVgap(2);
			layFlowLayout13.setAlignment(java.awt.FlowLayout.CENTER);
			jPanel1.setLayout(layFlowLayout13);
			jPanel1.add(getJScrollPane(), null);
			jPanel1.setPreferredSize(new java.awt.Dimension(395,115));
			jPanel1.setBackground(new java.awt.Color(242,242,238));
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dedicatoria (opcional)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
		}
		return jPanel1;
	}
	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel5() {
		if(jPanel5 == null) {
			jPanel5 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout21 = new java.awt.FlowLayout();
			layFlowLayout21.setHgap(5);
			layFlowLayout21.setVgap(6);
			layFlowLayout21.setAlignment(java.awt.FlowLayout.CENTER);
			jPanel5.setLayout(layFlowLayout21);
			jPanel5.add(getJLabel(), null);
			jPanel5.add(getJTextField(), null);
			jPanel5.add(getJPanel1(), null);
			jPanel5.setPreferredSize(new java.awt.Dimension(400,155));
			jPanel5.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel5;
	}
	
	/**
	 * This method initializes jTextPane
	 * 
	 * @return javax.swing.JTextPane
	 */
	private javax.swing.JTextPane getJTextPane() {
		if(jTextPane == null) {
			jTextPane = new javax.swing.JTextPane();
			jTextPane.setPreferredSize(new java.awt.Dimension(360,80));
			jTextPane.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		}
		return jTextPane;
	}
	/**
	 * Método getNuevaDireccion
	 * 
	 * @return
	 * String
	 */
	public String getDedicatoria() {
		return dedicatoria;
	}
	
	public String getNombre(){
		return nombre;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private javax.swing.JScrollPane getJScrollPane() {
		if(jScrollPane == null) {
			jScrollPane = new javax.swing.JScrollPane();
			jScrollPane.setViewportView(getJTextPane());
			jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		}
		return jScrollPane;
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
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField() {
		if(jTextField == null) {
			jTextField = new UpperCaseField();
			jTextField.setPreferredSize(new java.awt.Dimension(280,20));
			jTextField.setText("");
			jTextField.selectAll();
		}
		return jTextField;
	}

	/* (no Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if ((e.getSource().equals(jButtonAceptar))){
			if(!jTextField.getText().equals("")){
				dedicatoria = this.getJTextPane().getText();
				nombre = this.getJTextField().getText();
				dispose();
			}else{
				MensajesVentanas.aviso("Tiene que indicar el nombre del invitado");
				jTextField.requestFocus();
			}
			
		}else if((e.getSource().equals(jButtonCancelar)))
			dispose();
		
	}
	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		if(jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setText("Nombre Invitado:");
			jLabel.setPreferredSize(new java.awt.Dimension(96,16));
		}
		return jLabel;
	}

	/* (no Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == KeyEvent.VK_ENTER){
			if(e.getSource().equals(jTextField))
				jTextPane.requestFocus();
			else if(e.getSource().equals(jTextPane)){
				jButtonAceptar.requestFocus();
				e.consume();
			}
			else if(e.getSource().equals(jButtonAceptar))
				jButtonAceptar.doClick();
			else if(e.getSource().equals(jButtonCancelar))
				jButtonCancelar.doClick();
		}else if(e.getKeyChar() == KeyEvent.VK_TAB){
			if(e.getSource().equals(jTextField))
				jTextPane.requestFocus();
			else if(e.getSource().equals(jTextPane)){
				this.getJButtonAceptar().requestFocus();
				e.consume();
			}else if(e.getSource().equals(jButtonAceptar))
				jButtonCancelar.requestFocus();
			else if(e.getSource().equals(jButtonCancelar))
				jTextField.requestFocus();
		}else if(e.getKeyChar() == KeyEvent.VK_ESCAPE)
			dispose();
	}

	/* (no Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
		if(e.getSource().equals(jTextPane))
			jTextPane.setText(jTextPane.getText().toUpperCase());
	}

	/* (no Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
		if(e.getSource().equals(jTextPane))
			if(jTextPane.getText().length() > 200){
				MensajesVentanas.aviso("Máxima longitud del mensaje alcanzado");
				e.consume();
			}
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"