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
 * Versión     : 
 * Fecha       : 
 * Analista    : 
 * Descripción : 
 * =============================================================================
 */

package com.becoblohm.cr.gui.listaregalos;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.border.BevelBorder;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.manejarservicio.OperacionLR;
import com.becoblohm.cr.utiles.MensajesVentanas;

public class Cantidad extends JDialog implements KeyListener, ActionListener {
	
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
	
	private String cantidad = null;
	private String etiqueta1,campo,textoPanel; 
	private String nombreItem;
	public static int PRODUCTOS = 1;
	public static int ABONO_PRODUCTO = 2;
	public static int ABONO_LISTA = 3;
	private DecimalFormat df = new DecimalFormat("#,##0.00");
	private double precioItem;

	private javax.swing.JTextField jTextField = null;
	private javax.swing.JTextField jTextField1 = null;
	
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JRadioButton jRadioButton = null;
	private javax.swing.JRadioButton jRadioButton1 = null;
	private javax.swing.JPanel jPanel5 = null;
	private javax.swing.JPanel jPanel6 = null;
	/**
	 * This is the default constructor
	 */
	public Cantidad(int seleccion, String nombreItem) {
		super(MensajesVentanas.ventanaActiva);
		this.nombreItem = nombreItem;
		switch(seleccion){
			case 1:
				etiqueta1 = "Cantidad Productos";
				textoPanel = " Introduzca Cantidad: ";
				campo = "1";
				break;
			case 3:
				etiqueta1 = "Cantidad Abono";
				textoPanel = " Introduzca Monto: ";
				campo = df.format(0);
				break;
		}

		initialize();
		
		jTextField.addKeyListener(this);
		
		jRadioButton.addKeyListener(this);
		jRadioButton.addActionListener(this);
		
		jRadioButton1.addKeyListener(this);
		jRadioButton.addActionListener(this);
		
		jButtonCancelar.addKeyListener(this);
		jButtonCancelar.addActionListener(this);
		
		jButtonAceptar.addKeyListener(this);
		jButtonAceptar.addActionListener(this);
		
		jLabel1.setText(etiqueta1);
		jTextField.setText(campo);
		jPanel5.setVisible(false);
		jTextField.selectAll();
		//jRadioButton.doClick();
	}

	/**
	 * This is the default constructor
	 */
	public Cantidad(int seleccion, String nombreItem, double montoInicial) {
		super(MensajesVentanas.ventanaActiva);
		this.nombreItem = nombreItem;
		this.precioItem = montoInicial;
		switch(seleccion){
			case 2:
				etiqueta1 = "Cantidad Abono";
				textoPanel = " Introduzca Monto: ";
				campo = df.format(montoInicial);
				break;
		}
		initialize();
		
		jTextField.addKeyListener(this);
		
		jRadioButton.addKeyListener(this);
		jRadioButton.addActionListener(this);
		
		jRadioButton1.addKeyListener(this);
		jRadioButton1.addActionListener(this);
			
		jButtonCancelar.addKeyListener(this);
		jButtonCancelar.addActionListener(this);
		
		jButtonAceptar.addKeyListener(this);
		jButtonAceptar.addActionListener(this);
		
		jLabel1.setText(etiqueta1);
		jRadioButton.doClick();
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(360, 235);
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
		String textoFormateado = CR.me.formatearCampoNumerico(getJTextField().getText(), e);
		if (textoFormateado!=null)
			getJTextField().setText(textoFormateado);
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
			layFlowLayout4.setHgap(2);
			layFlowLayout4.setVgap(1);
			jPanel.setLayout(layFlowLayout4);
			jPanel.add(getJPanel6(), null);
			jPanel.add(getJPanel5(), null);
			jPanel.setPreferredSize(new java.awt.Dimension(338,73));
			jPanel.setBackground(new java.awt.Color(242,242,238));
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, textoPanel, javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 11), java.awt.Color.black));
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
			jPanel2.setPreferredSize(new java.awt.Dimension(340,30));
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
			jPanel3.add(getJPanel1(), null);
			jPanel3.add(getJPanel(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(340,155));
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
			layFlowLayout2.setVgap(3);
			layFlowLayout2.setHgap(5);
			jPanel4.setLayout(layFlowLayout2);
			jPanel4.add(getJLabel1(), null);
			jPanel4.setPreferredSize(new java.awt.Dimension(338,50));
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
			jLabel1.setText("Producto:");
			jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/down_plus.png")));
		}
		return jLabel1;
	}

	/* (no Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(jRadioButton)){
			jTextField.setText(df.format(precioItem));
			getJPanel().setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Introduzca Monto: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 11), java.awt.Color.black));
			jTextField.requestFocus();
			jTextField.selectAll();
		}else if(e.getSource().equals(jRadioButton1)){
			jTextField.setText("1");
			getJPanel().setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Introduzca Cantidad: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 11), java.awt.Color.black));
			jTextField.requestFocus();
			jTextField.selectAll();
		}else if (e.getSource().equals(jButtonAceptar)){
			cantidad = jTextField.getText();
			dispose();
		}else if (e.getSource().equals(jButtonCancelar))
			dispose();
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField() {
		if(jTextField == null) {
			jTextField = new javax.swing.JTextField();
			jTextField.setPreferredSize(new java.awt.Dimension(180,20));
		}
		return jTextField;
	}
	
	public String getCantidad(){
		return cantidad;
	}
	
	public char getTipoAbono(){
		char tipoAbono = ' ';
		if(jRadioButton.isSelected())
			tipoAbono = OperacionLR.ABONO_PARCIAL;
		else if(jRadioButton1.isSelected())
			tipoAbono = OperacionLR.ABONO_TOTAL;
		return tipoAbono;
	}
	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField1() {
		if(jTextField1 == null) {
			jTextField1 = new javax.swing.JTextField();
			jTextField1.setPreferredSize(new java.awt.Dimension(250,24));
			jTextField1.setEditable(false);
			jTextField1.setFocusable(false);
			jTextField1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			jTextField1.setBackground(new java.awt.Color(242,242,238));
			jTextField1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jTextField1.setText(nombreItem);
			jTextField1.select(0,0);
		}
		return jTextField1;
	}
	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		if(jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setPreferredSize(new java.awt.Dimension(61,24));
			jLabel.setText("Producto:  ");
		}
		return jLabel;
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
			layFlowLayout13.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout13.setVgap(3);
			jPanel1.setLayout(layFlowLayout13);
			jPanel1.add(getJLabel(), null);
			jPanel1.add(getJTextField1(), null);
			jPanel1.setPreferredSize(new java.awt.Dimension(338,30));
			jPanel1.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel1;
	}
	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private javax.swing.JRadioButton getJRadioButton() {
		if(jRadioButton == null) {
			jRadioButton = new javax.swing.JRadioButton();
			jRadioButton.setBackground(new java.awt.Color(242,242,238));
			jRadioButton.setText("Abono Parcial");
			jRadioButton.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
		}
		return jRadioButton;
	}
	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private javax.swing.JRadioButton getJRadioButton1() {
		if(jRadioButton1 == null) {
			jRadioButton1 = new javax.swing.JRadioButton();
			jRadioButton1.setBackground(new java.awt.Color(242,242,238));
			jRadioButton1.setText("Abono Total");
			jRadioButton1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
		}
		return jRadioButton1;
	}
	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel5() {
		if(jPanel5 == null) {
			jPanel5 = new javax.swing.JPanel();
			java.awt.GridLayout layGridLayout2 = new java.awt.GridLayout();
			layGridLayout2.setRows(2);
			layGridLayout2.setColumns(1);
			jPanel5.setLayout(layGridLayout2);
			ButtonGroup bg = new ButtonGroup();
			bg.add(getJRadioButton());
			bg.add(getJRadioButton1());
			jPanel5.add(getJRadioButton(), null);
			jPanel5.add(getJRadioButton1(), null);
			jPanel5.setBackground(new java.awt.Color(242,242,238));
			jPanel5.setPreferredSize(new java.awt.Dimension(115,44));
		}
		return jPanel5;
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
			layFlowLayout1.setVgap(11);
			jPanel6.setLayout(layFlowLayout1);
			jPanel6.add(getJTextField(), null);
			jPanel6.setBackground(new java.awt.Color(242,242,238));
			jPanel6.setPreferredSize(new java.awt.Dimension(190,44));
		}
		return jPanel6;
	}
}
