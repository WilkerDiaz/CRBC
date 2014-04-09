/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui.listaregalos
 * Programa   : DetalleRegalo.java
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

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.border.BevelBorder;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.utiles.MensajesVentanas;

/**
 * @author rabreu
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DetalleRegalo extends JDialog implements KeyListener, ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private javax.swing.JPanel jContentPane = null;
	ModeloTablaDetalleRegalo modeloTablaDetalle = new ModeloTablaDetalleRegalo();
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton jButtonAceptar = null;
	private javax.swing.JScrollPane jScrollPane = null;
	private javax.swing.JTable jTable = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JTextField jTextField = null;
	private String nombreProd;

	/**
	 * This is the default constructor
	 */
	public DetalleRegalo (String codProd, String nombProd){
		super(MensajesVentanas.ventanaActiva);
		this.nombreProd = nombProd;
		
		initialize();
		
		jTextField.addKeyListener(this);

		jButtonAceptar.addKeyListener(this);
		jButtonAceptar.addActionListener(this);
		
		jTable.addKeyListener(this);
		
		jTextField.addKeyListener(this);
		
		modeloTablaDetalle.llenarTabla(codProd);
	}

	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("Detalles Producto");
		this.setSize(650, 400);
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
			jContentPane.setLayout(new FlowLayout());
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
		else if(e.getKeyCode() == KeyEvent.VK_TAB){
			if(e.getSource().equals(this.getJTable())){
				this.getJTextField().requestFocus();	
				jTextField.selectAll();		
			}
			else if(e.getSource().equals(this.getJTextField()))
				this.getJButtonAceptar().requestFocus();
		}	
		
		else if(e.getKeyCode() == KeyEvent.VK_ENTER)
			if(e.getSource().equals(this.getJButtonAceptar())) {
				if(jTable.getSelectedRow() != -1)
					try {
						dispose();
						try {
							finalize();
						} catch (Throwable e2) {
						}
					} catch (NumberFormatException e1) {
						MensajesVentanas.mensajeError("Debe llenar correctamente todos los datos solicitados para poder realizar la función.");
					}
				else
					MensajesVentanas.mensajeError("Debe Seleccionarse un renglon");
			}
			
			else if(e.getSource().equals(this.getJTextField())) {
			}
			
			else if(e.getSource().equals(this.getJTable())) {
				if(jTable.getSelectedRow() != 0)
					jTable.setRowSelectionInterval(jTable.getSelectedRow()-1,jTable.getSelectedRow()-1);
				else 
					jTable.setRowSelectionInterval(jTable.getRowCount()-1,jTable.getRowCount()-1);
					
				this.getJTextField().requestFocus();
				this.getJTextField().selectAll();
			}

			else if(e.getSource().equals(this.getJTextField()))
				this.getJButtonAceptar().requestFocus();		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
		if (e.getSource().equals(getJTextField())) {
			String textoFormateado = CR.me.formatearCampoNumerico(getJTextField().getText(), e);
			if (textoFormateado!=null)
				getJTextField().setText(textoFormateado);
	}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
	}
	
	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel1() {
		if(jPanel1 == null) {
			jPanel1 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setHgap(0);
			layFlowLayout1.setVgap(0);
			jPanel1.setLayout(layFlowLayout1);
			jPanel1.add(getJScrollPane(), null);
			jPanel1.setPreferredSize(new java.awt.Dimension(618,258));
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalles del producto:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
			jPanel1.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel1;
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
			jPanel2.setPreferredSize(new java.awt.Dimension(620,35));
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
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private javax.swing.JTable getJTable() {
		if(jTable == null) {
			jTable = new javax.swing.JTable(modeloTablaDetalle);
			jTable.getTableHeader().setReorderingAllowed(false) ;
			jTable.getColumnModel().getColumn(0).setPreferredWidth(30);
			jTable.getColumnModel().getColumn(1).setPreferredWidth(40);
			jTable.getColumnModel().getColumn(2).setPreferredWidth(40);
			jTable.getColumnModel().getColumn(3).setPreferredWidth(100);
			jTable.getColumnModel().getColumn(4).setPreferredWidth(200);
			jTable.setLocale(new java.util.Locale("es", "VE", ""));
			jTable.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jTable.setBackground(new java.awt.Color(242,242,238));
			jTable.setFocusable(false);
		}
		return jTable;
	}
	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private javax.swing.JScrollPane getJScrollPane() {
		if(jScrollPane == null) {
			jScrollPane = new javax.swing.JScrollPane();
			jScrollPane.setViewportView(getJTable());
			jScrollPane.setPreferredSize(new java.awt.Dimension(609,234));
			jScrollPane.setBackground(new java.awt.Color(242,242,238));
		}
		return jScrollPane;
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
			jPanel3.setPreferredSize(new java.awt.Dimension(620,310));
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
			layFlowLayout2.setVgap(10);
			layFlowLayout2.setHgap(10);
			jPanel4.setLayout(layFlowLayout2);
			jPanel4.add(getJLabel(), null);
			jPanel4.add(getJTextField(), null);
			jPanel4.setPreferredSize(new java.awt.Dimension(620,50));
			jPanel4.setBackground(new java.awt.Color(69,107,127));
		}
		return jPanel4;
	}
	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		if(jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setText("Detalles Producto: ");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel.setForeground(java.awt.Color.white);
		}
		return jLabel;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField() {
		if(jTextField == null) {
			jTextField = new javax.swing.JTextField();
			jTextField.setEditable(false);
			jTextField.setFocusable(false);
			jTextField.setPreferredSize(new java.awt.Dimension(400,20));
			jTextField.setBackground(new java.awt.Color(69,107,127));
			jTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			jTextField.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jTextField.setForeground(java.awt.Color.white);
			jTextField.setText(nombreProd);
		}
		return jTextField;
	}


	/* (no Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(jButtonAceptar))
			dispose();
	}
}