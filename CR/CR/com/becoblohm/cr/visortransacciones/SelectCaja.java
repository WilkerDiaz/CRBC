/*
 * Creado el 26/10/2004
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.becoblohm.cr.visortransacciones;

import java.util.Properties;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JDialog;

import com.becoblohm.cr.utiles.MensajesVentanas;

/**
 * @author Programa8
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class SelectCaja extends JDialog {

	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JPanel jPanel = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton btnAceptar = null;
	private javax.swing.JButton btnCancelar = null;
	private javax.swing.JLabel jLabel1 = null;
	private JComboBox cmbCnx = null;
	private Properties props;
	private CnxCaja cnxSelected = null;
	private boolean result = false;
	
	/**
	 * This is the default constructor
	 */
	public SelectCaja(Properties p) {
		super();
		props = p;
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(250, 170);
		this.setContentPane(getJContentPane());
		this.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
		this.setTitle("Seleccione la caja a auditar");
		this.setBackground(new java.awt.Color(226,226,222));
		this.setModal(true);
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setVgap(0);
			jContentPane.setLayout(layFlowLayout1);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJPanel1(), null);
			jContentPane.add(getJPanel2(), null);
			jContentPane.setBackground(new java.awt.Color(226,226,222));
		}
		return jContentPane;
	}
	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel() {
		if(jPanel == null) {
			jPanel = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
			layFlowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel.setLayout(layFlowLayout2);
			jPanel.add(getJLabel(), null);
			jPanel.setBackground(new java.awt.Color(69,107,127));
			jPanel.setPreferredSize(new java.awt.Dimension(240,45));
		}
		return jPanel;
	}
	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		if(jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setText("Selección de Caja");
			jLabel.setForeground(java.awt.Color.white);
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jLabel.setPreferredSize(new java.awt.Dimension(200,40));
			jLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/server_client.png")));
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
			java.awt.FlowLayout layFlowLayout4 = new java.awt.FlowLayout();
			layFlowLayout4.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout4.setVgap(15);
			layFlowLayout4.setHgap(15);
			jPanel1.setLayout(layFlowLayout4);
			jPanel1.add(getJLabel1(), null);
			jPanel1.add(getCmbCnx(), null);
			jPanel1.setBackground(new java.awt.Color(226,226,222));
			jPanel1.setPreferredSize(new java.awt.Dimension(240,50));
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
			java.awt.FlowLayout layFlowLayout3 = new java.awt.FlowLayout();
			layFlowLayout3.setAlignment(java.awt.FlowLayout.RIGHT);
			layFlowLayout3.setVgap(10);
			jPanel2.setLayout(layFlowLayout3);
			jPanel2.add(getBtnAceptar(), null);
			jPanel2.add(getBtnCancelar(), null);
			jPanel2.setBackground(new java.awt.Color(226,226,222));
			jPanel2.setPreferredSize(new java.awt.Dimension(240,50));
		}
		return jPanel2;
	}
	/**
	 * This method initializes btnAceptar
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getBtnAceptar() {
		if(btnAceptar == null) {
			btnAceptar = new javax.swing.JButton();
			btnAceptar.setText("Aceptar");
			btnAceptar.setBackground(new java.awt.Color(226,226,222));
			btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
			btnAceptar.setEnabled(false);
			btnAceptar.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {
					result = true;    
					setVisible(false);
				}
			});
		}
		return btnAceptar;
	}
	/**
	 * This method initializes btnCancelar
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getBtnCancelar() {
		if(btnCancelar == null) {
			btnCancelar = new javax.swing.JButton();
			btnCancelar.setText("Cancelar");
			btnCancelar.setBackground(new java.awt.Color(226,226,222));
			btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
			btnCancelar.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					result = false;
					setVisible(false);
				}
			});
		}
		return btnCancelar;
	}
	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel1() {
		if(jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setText("Caja");
			jLabel1.setPreferredSize(new java.awt.Dimension(50,20));
		}
		return jLabel1;
	}
	/**
	 * This method initializes cmbCnx
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCmbCnx() {
		if(cmbCnx == null) {
			Vector<CnxCaja> cnxs;
			cmbCnx = new javax.swing.JComboBox();
			cmbCnx.setPreferredSize(new java.awt.Dimension(140,20));
			cnxs = CnxCaja.getConexiones(props);
			for (int i = 0; i < cnxs.size(); i++) {
				cmbCnx.addItem(cnxs.get(i));
			}
			cmbCnx.setSelectedIndex(-1);
			cmbCnx.addItemListener(new java.awt.event.ItemListener() { 
				public void itemStateChanged(java.awt.event.ItemEvent e) {    
					cnxSelected = (CnxCaja)e.getItem();
					btnAceptar.setEnabled(true);
				}
			});

		}
		return cmbCnx;
	}
	/**
	 * @return
	 */
	public CnxCaja getCnxSelected() {
		return cnxSelected;
	}

	public boolean mostrar() {
		btnAceptar.setEnabled(false);
		MensajesVentanas.centrarVentanaDialogo(this);
		return result;
	}


}  //  @jve:visual-info  decl-index=0 visual-constraint="65,22"

