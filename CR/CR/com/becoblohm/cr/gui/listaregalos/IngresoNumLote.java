/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui.listaregalos
 * Programa   : Cantidad.java
 * Creado por : rabreu
 * Creado en  : 03/07/2006
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versi�n     : 
 * Fecha       : 
 * Analista    : 
 * Descripci�n : 
 * =============================================================================
 */

package com.becoblohm.cr.gui.listaregalos;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JDialog;
import javax.swing.border.BevelBorder;

import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.utiles.MensajesVentanas;

public class IngresoNumLote extends JDialog implements KeyListener, ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private javax.swing.JPanel jContentPane = null;  //  @jve:visual-info  decl-index=0 visual-constraint="153,-3"
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton jButtonCancelar = null;
	private javax.swing.JButton jButtonAceptar = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JLabel jLabel1 = null;	
	
	private String numLote = null;

	private javax.swing.JTextField jTextField = null;
	private javax.swing.JPanel jPanel6 = null;
	/**
	 * This is the default constructor
	 */
	public IngresoNumLote() {
		super(MensajesVentanas.ventanaActiva);
		
		initialize();
		
		jTextField.addKeyListener(this);

		jButtonCancelar.addKeyListener(this);
		jButtonCancelar.addActionListener(this);
		
		jButtonAceptar.addKeyListener(this);
		jButtonAceptar.addActionListener(this);
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(350, 200);
		this.setContentPane(getJContentPane());
		this.setUndecorated(true);
		this.setModal(true);
		this.setBackground(new java.awt.Color(226,226,222));
	}
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
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
			jContentPane.setPreferredSize(new java.awt.Dimension(350,200));
		}
		return jContentPane;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			dispose();
		else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(e.getSource().equals(this.getJTextField()))
				jButtonAceptar.doClick();	
		} else if(e.getSource().equals(getJTextField()))
			if(!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != KeyEvent.VK_DELETE && e.getKeyChar() != KeyEvent.VK_BACK_SPACE)
				e.consume();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
//		String textoFormateado = CR.me.formatearCampoNumerico(getJTextField().getText(), e);
//		if (textoFormateado!=null)
//			getJTextField().setText(textoFormateado);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel() {
		if(jPanel == null) {
			jPanel = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout4 = new java.awt.FlowLayout();
			layFlowLayout4.setHgap(5);
			layFlowLayout4.setVgap(8);
			jPanel.setLayout(layFlowLayout4);
			jPanel.add(getJPanel6(), null);
			jPanel.setPreferredSize(new java.awt.Dimension(328,72));
			jPanel.setBackground(new java.awt.Color(242,242,238));
			//jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, textoPanel, javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 11), java.awt.Color.black));
		}
		return jPanel;
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
			layFlowLayout11.setVgap(0);
			jPanel2.setLayout(layFlowLayout11);
			jPanel2.add(getJButtonAceptar(), null);
			jPanel2.add(getJButtonCancelar(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(330,30));
			jPanel2.setBackground(new java.awt.Color(226,226,222));
		}
		return jPanel2;
	}

	/**
	 * This method initializes jButtonCancelar
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButtonCancelar() {
		if(jButtonCancelar == null) {
			jButtonCancelar = new JHighlightButton();
			jButtonCancelar.setText("Cancelar");
			jButtonCancelar.setBackground(new java.awt.Color(226,226,222));
			jButtonCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
			jButtonCancelar.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jButtonCancelar.setMargin(new Insets(1,2,1,1));
			jButtonCancelar.setPreferredSize(new java.awt.Dimension(89,26));
		}
		return jButtonCancelar;
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
			jButtonAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
			jButtonAceptar.setMargin(new Insets(1,2,1,1));
			jButtonAceptar.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jButtonAceptar.setPreferredSize(new java.awt.Dimension(89,26));
		}
		return jButtonAceptar;
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
			jPanel3.add(getJPanel(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(330,120));
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
			layFlowLayout2.setVgap(5);
			layFlowLayout2.setHgap(10);
			jPanel4.setLayout(layFlowLayout2);
			jPanel4.add(getJLabel1(), null);
			jPanel4.setPreferredSize(new java.awt.Dimension(328,46));
			jPanel4.setBackground(new java.awt.Color(69,107,127));
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
			jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel1.setForeground(java.awt.Color.white);
			jLabel1.setText("Ingresar Lote PDT");
			jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/down_plus.png")));
		}
		return jLabel1;
	}

	/* (no Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(jButtonAceptar)){
			numLote = getJTextField().getText();
			dispose();
		}else if (e.getSource().equals(jButtonCancelar)){
			numLote = null;
			dispose();
		}
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField() {
		if(jTextField == null) {
			jTextField = new javax.swing.JTextField();
			jTextField.setPreferredSize(new java.awt.Dimension(240,20));
		}
		return jTextField;
	}
	
	public String getNumLote(){
		return numLote;
	}

	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel6() {
		if(jPanel6 == null) {
			jPanel6 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setVgap(4);
			layFlowLayout1.setHgap(5);
			jPanel6.setLayout(layFlowLayout1);
			jPanel6.add(getJTextField(), null);
			jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "N�mero de Lote:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), java.awt.Color.black));
			jPanel6.setBackground(new java.awt.Color(242,242,238));
			jPanel6.setPreferredSize(new java.awt.Dimension(280,58));
		}
		return jPanel6;
	}
}
