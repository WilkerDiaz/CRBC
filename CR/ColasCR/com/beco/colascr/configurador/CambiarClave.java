/**
 * =============================================================================
 * Proyecto   : 
 * Paquete    : 
 * Programa   : CambiarClave.java
 * Creado por : 
 * Creado en  : 04/04/2005 
 *
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 
 * Fecha       : 
 * Analista    :  
 * Descripción : 
 * =============================================================================
 */

package com.beco.colascr.configurador;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.beco.colascr.utiles.MensajesVentanas;

/**
 * Descripción:
 * 
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
* Fecha: agosto 2011
*/
public class CambiarClave extends JDialog implements ActionListener, KeyListener {
	public Vector<String> valores = new Vector<String>();
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JLabel jLabel3 = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JPasswordField jClaveActual = null;
	private javax.swing.JPasswordField jPasswordField = null;
	private javax.swing.JPasswordField jPasswordField2 = null;
	private javax.swing.JPanel jPanel3 = null;
	private JPanel jPanel4 = null;
	private String claveAnterior = null;
	private boolean error = true;
	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			java.awt.FlowLayout flowLayout2 = new FlowLayout();
			flowLayout2.setAlignment(java.awt.FlowLayout.CENTER);
			flowLayout2.setHgap(0);
			flowLayout2.setVgap(0);
			jPanel4.setLayout(flowLayout2);
			jPanel4.setPreferredSize(new java.awt.Dimension(350,120));
			jPanel4.setBackground(new java.awt.Color(242,242,238));
			jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
			jPanel4.add(getJPanel(), null);
			jPanel4.add(getJPanel1(), null);
		}
		return jPanel4;
	}
	/**
	 * This is the default constructor
	 */
	public CambiarClave(String claveAnt) {
		super();
		claveAnterior = claveAnt!=null ? claveAnt : "";
		initialize();
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				cargarDatos();
			}
		});
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		java.awt.GridLayout layGridLayout1 = new java.awt.GridLayout();
		layGridLayout1.setRows(3);
		this.setContentPane(getJPanel3());
		this.setSize(370, 197);
		this.setTitle("Cambiar Contraseña");
		this.setBackground(new java.awt.Color(226,226,222));
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		this.setModal(true);
	}
	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel() {
		if(jPanel == null) {
			jPanel = new javax.swing.JPanel();
			java.awt.FlowLayout flowLayout11 = new FlowLayout();
			flowLayout11.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout11.setVgap(5);
			jPanel.setLayout(flowLayout11);
			jPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,4,0,0));
			jPanel.setBackground(new java.awt.Color(69,107,127));
			jPanel.setPreferredSize(new java.awt.Dimension(350,40));
			jPanel.add(getJLabel1(), null);
		}
		return jPanel;
	}
	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel1() {
		if(jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setText("Cambiar Contraseña");
			jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel1.setForeground(java.awt.Color.white);
			jLabel1.setForeground(java.awt.Color.white);
			jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/beco/colascr/utiles/iconos/ix32x32/lock_preferences.png")));
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
			java.awt.GridLayout layGridLayout4 = new java.awt.GridLayout();
			layGridLayout4.setColumns(2);
			layGridLayout4.setRows(3);
			layGridLayout4.setVgap(5);
			jPanel1.setLayout(layGridLayout4);
			jPanel1.add(getJLabel(), null);
			jPanel1.add(getJClaveActual(), null);
			jPanel1.add(getJLabel2(), null);
			jPanel1.add(getJPasswordField(), null);
			jPanel1.add(getJLabel3(), null);
			jPanel1.add(getJPasswordField2(), null);
			jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(4,4,4,4));
			jPanel1.setBackground(new java.awt.Color(242,242,238));
			jPanel1.setPreferredSize(new java.awt.Dimension(330,78));
		}
		return jPanel1;
	}
	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		if(jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setText(" Contraseña anterior: ");
		}
		return jLabel;
	}
	/**
	 * This method initializes jLoginField
	 * 
	 * @return javax.swing.JPasswordField
	 */
	private javax.swing.JPasswordField getJClaveActual() {
		if(jClaveActual == null) {
			jClaveActual = new javax.swing.JPasswordField();
			jClaveActual.setName("ClaveActual");
			jClaveActual.addKeyListener(this);
			if (claveAnterior.equals("")) {
				jClaveActual.setEnabled(false);
				jClaveActual.setEditable(false);
			}
		}
		return jClaveActual;
	}
	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel2() {
		if(jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setText(" Contraseña nueva: ");
		}
		return jLabel2;
	}
	/**
	 * This method initializes jPasswordField
	 * 
	 * @return javax.swing.JPasswordField
	 */
	private javax.swing.JPasswordField getJPasswordField() {
		if(jPasswordField == null) {
			jPasswordField = new javax.swing.JPasswordField();
			jPasswordField.setName("ClaveNueva");
			jPasswordField.addKeyListener(this);
		}
		return jPasswordField;
	}
	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel3() {
		if(jLabel3 == null) {
			jLabel3 = new javax.swing.JLabel();
			jLabel3.setText(" Confirmar Contraseña: ");
		}
		return jLabel3;
	}
	/**
	 * This method initializes jPasswordField
	 * 
	 * @return javax.swing.JPasswordField
	 */
	private javax.swing.JPasswordField getJPasswordField2() {
		if(jPasswordField2== null) {
			jPasswordField2 = new javax.swing.JPasswordField();
			jPasswordField2.setName("ClaveNueva2");
			jPasswordField2.addKeyListener(this);
		}
		return jPasswordField2;
	}


	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel2() {
		if(jPanel2 == null) {
			jPanel2 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setAlignment(java.awt.FlowLayout.RIGHT);
			jPanel2.setLayout(layFlowLayout1);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.setBackground(new java.awt.Color(226,226,222));
			jPanel2.setComponentOrientation(java.awt.ComponentOrientation.UNKNOWN);
			jPanel2.setPreferredSize(new java.awt.Dimension(350,35));
			layFlowLayout1.setHgap(0);
			layFlowLayout1.setVgap(3);
		}
		return jPanel2;
	}
	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton() {
		if(jButton == null) {
			jButton = new JButton();
			jButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/beco/colascr/utiles/iconos/ix16x16/check2.png")));
			jButton.setText("Aceptar");
			jButton.addActionListener(this);
			jButton.addKeyListener(this);
			jButton.setBackground(new java.awt.Color(226,226,222));
		}
		return jButton;
	}
	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton1() {
		if(jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/beco/colascr/utiles/iconos/ix16x16/delete2.png")));
			jButton1.setText("Cancelar");
			jButton1.addActionListener(this);
			jButton1.addKeyListener(this);
			jButton1.setBackground(new java.awt.Color(226,226,222));
		}
		return jButton1;
	}
	
	public void actionPerformed(ActionEvent e){
		JButton btnComando = (JButton)e.getSource();
		if (btnComando.getText().equals("Aceptar"))
		{
			if (cargarDatos()) {
				this.dispose();
			} else {
				MensajesVentanas.mensajeError("No se pudo cambiar la contraseña\nClaves incorrectas"); 
			}
		}
		else if (btnComando.getText().equals("Cancelar")){
			this.dispose();
		}
	}

	public void keyPressed(KeyEvent e){
		try{
			JPasswordField campo = (JPasswordField)e.getSource();
			if ((campo.getName().equals("ClaveActual"))&&(e.getKeyCode() == KeyEvent.VK_ENTER))
				this.getJPasswordField().requestFocus();
			else if ((campo.getName().equals("ClaveNueva"))&&(e.getKeyCode() == KeyEvent.VK_ENTER))
				this.getJPasswordField2().requestFocus();
			else if ((campo.getName().equals("ClaveNueva2"))&&(e.getKeyCode() == KeyEvent.VK_ENTER))
				this.jButton.doClick();
		} catch(Exception ex){}

		try{
			JButton boton = (JButton)e.getSource();
			if ((boton.getText().equals("Aceptar"))&&(e.getKeyCode() == KeyEvent.VK_ENTER)){
				if (cargarDatos()) {
					this.dispose();
				} 
			}
			else if ((boton.getText().equals("Cancelar"))&&(e.getKeyCode() == KeyEvent.VK_ENTER)){
				this.dispose();
			}
			else if ((boton.getText().equals("Apagar"))&&(e.getKeyCode() == KeyEvent.VK_ENTER)){
				System.exit(0);
			}
		} catch(Exception ex){}
		
		try{
			 if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
				this.dispose();
			 }
		} catch(Exception ex){}
	}

	public void keyReleased(KeyEvent e){
	}

	public void keyTyped(KeyEvent e){
	}
	
	/**
	 * Returns the valores.
	 * @return Vector
	 */
	public String getNuevaContraseña() {
		if (!error)
			return (String) valores.elementAt(1);
		else
			return null;
	}
	
	boolean cargarDatos(){
		valores = new Vector<String>();
		String claveActual = new String(this.jClaveActual.getPassword());
		String claveNueva = new String(this.jPasswordField.getPassword());
		String claveNueva2 = new String(this.jPasswordField2.getPassword());
		if ((claveActual != null)&&(claveNueva != null)&&(claveNueva2 != null)&&(claveActual != "")&&(claveNueva != "")&&(claveNueva2 != ""))
			if(claveActual.equals(claveAnterior)&&!(claveActual.equals(claveNueva))&&(claveNueva.equals(claveNueva2))){
				valores.addElement(claveActual);
				valores.addElement(claveNueva);
				error = false;
			}else return false;
		else return false;
		return true;
	}
	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel3() {
		if(jPanel3 == null) {
			jPanel3 = new javax.swing.JPanel();
			java.awt.FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setAlignment(java.awt.FlowLayout.CENTER);
			flowLayout1.setHgap(0);
			flowLayout1.setVgap(5);
			jPanel3.setLayout(flowLayout1);
			jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray,1));
			jPanel3.setPreferredSize(new java.awt.Dimension(380,300));
			jPanel3.setBackground(new java.awt.Color(226,226,222));
			jPanel3.add(getJPanel4(), null);
			jPanel3.add(getJPanel2(), null);
		}
		return jPanel3;
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"