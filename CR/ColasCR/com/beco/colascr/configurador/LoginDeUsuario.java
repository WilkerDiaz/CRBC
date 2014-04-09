/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.colascr.configurador
 * Programa   : LoginDeUsuario.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 13/02/2004 02:44 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.0.1
 * Fecha       : 01/07/2004 11:03 AM
 * Analista    : Programador3 -Alexis Guédez López  
 * Descripción : - Deshabilitado el cierre de la ventana a través de los comandos 
 * 				de ventana.
 * 				 - Agregado el indicador para deshabilitar redimensionar la ventana.
 * 				 - Modificada temporalmente condición de desactivación del botón 
 * 				para salir del sistema.
 * =============================================================================
 */
package com.beco.colascr.configurador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPasswordField;

/**
 * Descripción:
 * 
 */
public class LoginDeUsuario extends JDialog implements ActionListener, KeyListener {
	public Vector<String> valores = new Vector<String>();
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JPasswordField jLoginField = null;
	private javax.swing.JPasswordField jPasswordField = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JLabel jLabel3 = null;
	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel jPanel4 = null;

	/**
	 * This is the default constructor
	 */
	public LoginDeUsuario() {
		super();
		initialize();
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		java.awt.GridLayout layGridLayout1 = new java.awt.GridLayout();
		layGridLayout1.setRows(4);
		this.setContentPane(getJContentPane());
		this.setSize(360, 228);
		this.setTitle("Acceso al Sistema");
		this.setBackground(new java.awt.Color(226,226,222));
		this.setModal(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
			this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.addWindowListener(new java.awt.event.WindowAdapter() { 
			public void windowOpened(java.awt.event.WindowEvent e) {    
				jLoginField.requestFocus();
			}
		});
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJPanel4(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanel2(), java.awt.BorderLayout.SOUTH);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel4() {
		if(jPanel4 == null) {
			jPanel4 = new javax.swing.JPanel();
			java.awt.GridLayout layGridLayout2 = new java.awt.GridLayout();
			layGridLayout2.setRows(2);
			layGridLayout2.setColumns(1);
			jPanel4.setLayout(layGridLayout2);
			jPanel4.add(getJPanel(), null);
			jPanel4.add(getJPanel3(), null);
		}
		return jPanel4;
	}
	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel() {
		if(jPanel == null) {
			jPanel = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout8 = new java.awt.FlowLayout();
			layFlowLayout8.setHgap(5);
			layFlowLayout8.setVgap(8);
			layFlowLayout8.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel.setLayout(layFlowLayout8);
			jPanel.add(getJLabel1(), null);
			jPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,4,0,0));
			jPanel.setBackground(new java.awt.Color(69,107,127));
			jPanel.setPreferredSize(new java.awt.Dimension(325,48));
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
			jLabel1.setText("Area Protegida: Identifiquese ");
			jLabel1.setForeground(java.awt.Color.white);
			jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/beco/colascr/utiles/iconos/ix32x32/safe.png")));
			jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jLabel1.setPreferredSize(new java.awt.Dimension(321,32));
			jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jLabel1.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
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
			layGridLayout4.setRows(2);
			layGridLayout4.setVgap(0);
			jPanel1.setLayout(layGridLayout4);
			jPanel1.add(getJLabel(), null);
			jPanel1.add(getJLoginField(), null);
			jPanel1.add(getJLabel2(), null);
			jPanel1.add(getJPasswordField(), null);
			jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel1.setBackground(new java.awt.Color(226,226,222));
			jPanel1.setPreferredSize(new java.awt.Dimension(284,60));
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
			jLabel.setText(" Identificador de Usuario:");
		}
		return jLabel;
	}
	/**
	 * This method initializes jLoginField
	 * 
	 * @return javax.swing.JPasswordField
	 */
	private javax.swing.JPasswordField getJLoginField() {
		if(jLoginField == null) {
			jLoginField = new javax.swing.JPasswordField();
			jLoginField.setName("Login");
			jLoginField.addKeyListener(this);
		}
		return jLoginField;
	}
	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel2() {
		if(jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setText(" Contraseña: ");
		}
		return jLabel2;
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
			layFlowLayout1.setVgap(10);
			jPanel2.setLayout(layFlowLayout1);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.setBackground(new java.awt.Color(226,226,222));
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
	/**
	 * This method initializes jPasswordField
	 * 
	 * @return javax.swing.JPasswordField
	 */
	private javax.swing.JPasswordField getJPasswordField() {
		if(jPasswordField == null) {
			jPasswordField = new javax.swing.JPasswordField();
			jPasswordField.setName("Clave");
			jPasswordField.addKeyListener(this);
		}
		return jPasswordField;
	}
	
	/**
	 * Método actionPerformed
	 *
	 * @param e
	 */
	public void actionPerformed(ActionEvent e){
		JButton btnComando = (JButton)e.getSource();
		if (btnComando.getText().equals("Aceptar")) {
			if (cargarDatos())
				this.dispose();
		} else {
			if (btnComando.getText().equals("Cancelar"))
				this.dispose();
		}
	}

	/**
	 * Método keyPressed
	 *
	 * @param e
	 */
	public void keyPressed(KeyEvent e){
		try {
			JPasswordField campo = (JPasswordField)e.getSource();
			if ((campo.getName().equals("Login"))&&(e.getKeyCode() == KeyEvent.VK_ENTER)){
				this.getJPasswordField().requestFocus();
			}
			else if ((campo.getName().equals("Clave"))&&(e.getKeyCode() == KeyEvent.VK_ENTER)){
				this.jButton.doClick();
			}
		} catch(Exception ex){}

		try {
			JButton boton = (JButton)e.getSource();
			if ((boton.getText().equals("Aceptar"))&&(e.getKeyCode() == KeyEvent.VK_ENTER)){
				if (cargarDatos())
					this.dispose();
			} else {
				if ((boton.getText().equals("Cancelar"))&&(e.getKeyCode() == KeyEvent.VK_ENTER))
					this.dispose();
			}
		} catch(Exception ex){}
		
		try {
			 if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
				this.dispose();
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
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<String> getValores() {
		return valores;
	}
	
	/**
	 * Método cargarDatos
	 * 
	 * @return boolean
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	boolean cargarDatos(){
		valores = new Vector<String>();
		String login = new String(this.jLoginField.getPassword()).trim();
		String clave = new String(this.jPasswordField.getPassword());
		if ((login != null)&&(clave != null)&&(login != "")&&(clave != "")){
			valores.addElement(login);
			valores.addElement(clave);
		}
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
			java.awt.GridLayout layGridLayout6 = new java.awt.GridLayout();
			layGridLayout6.setRows(1);
			layGridLayout6.setVgap(10);
			jPanel3.setLayout(layGridLayout6);
			jPanel3.add(getJLabel3(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(223,30));
			jPanel3.setBackground(new java.awt.Color(226,226,222));
			jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Función ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), java.awt.Color.black));
		}
		return jPanel3;
	}
	/**
	 * This method initializes jLabel3
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel3() {
		String funcion = "Validar Usuario";
/*		try {
			funcion = MaquinaDeEstado.buscarFuncion();
		} catch (FuncionExcepcion e) {
		} catch (BaseDeDatosExcepcion e) {
		} catch (ConexionExcepcion e) {
		} catch (SQLException e) {}
*/		
		if(jLabel3 == null) {
			jLabel3 = new javax.swing.JLabel();
			jLabel3.setText(funcion);
			jLabel3.setPreferredSize(new java.awt.Dimension(219,30));
			jLabel3.setForeground(java.awt.Color.black);
			jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			jLabel3.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
		}
		return jLabel3;
	}
	
}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"