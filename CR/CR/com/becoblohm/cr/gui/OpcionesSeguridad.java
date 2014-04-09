/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : OpcionesSeguridad.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 13/02/2004 02:44 PM
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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPasswordField;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.PerfilExcepcion;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstado;
import com.becoblohm.cr.manejarsistema.Funcion;
import com.becoblohm.cr.manejarsistema.Modulo;
import com.becoblohm.cr.manejarusuario.Colaborador;
import com.becoblohm.cr.manejarusuario.ListaFuncion;
import com.becoblohm.cr.manejarusuario.Perfil;
import com.becoblohm.cr.manejarusuario.Usuario;
import com.becoblohm.cr.mediadoresbd.BeansSincronizador;
import com.becoblohm.cr.sincronizador.SyncCnxMonitor;
import com.becoblohm.cr.utiles.Control;
import com.becoblohm.cr.utiles.MensajesVentanas;

/**
 * Descripción:
 * 
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Class'
* Fecha: agosto 2011
*/
public class OpcionesSeguridad extends JDialog implements ComponentListener, ActionListener, KeyListener, MouseListener, DocumentListener, FocusListener {

	private static final long serialVersionUID = 1L;

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(OpcionesSeguridad.class);

	private Usuario xUsuario = new Usuario();
	private String panelActivo = null;
	private String panel1 = new String("usuarios");  //  @jve:decl-index=0:
	private String panel2 = new String("perfiles");
	String[] titulos = new String[]{"Módulo","Nombre Función","Es Visible","Autorizado"};
	Object[][] datos = {{"","",Boolean.FALSE,Boolean.FALSE}};
	Class<?>[] tipos = new Class[]{String.class, String.class, Boolean.class, Boolean.class};
	private ModeloTablaFunciones modeloTablaFunciones = new ModeloTablaFunciones(datos, titulos, tipos);;

	String[] titulos2 = new String[]{"Identificador","Nombre Usuario"};
	Object[][] datos2 = {{"",""}};
	Class<?>[] tipos2 = new Class[]{String.class, String.class, Boolean.class};
	private ModeloTablaUsuariosPerfil modeloTablaUsuariosPerfil = new ModeloTablaUsuariosPerfil(datos2, titulos2, tipos2);
	private ModeloTablaUsuariosSistema modeloTablaUsuariosSistema = new ModeloTablaUsuariosSistema(datos2, titulos2, tipos2);
	private Perfil perfilActual = new Perfil();
	
	private javax.swing.JPanel jPanel = null;  
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JPanel jPanel5 = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JLabel jLabel3 = null;
	private javax.swing.JLabel jLabel4 = null;
	public javax.swing.JPasswordField txtCodigo = null;
	private javax.swing.JTextField jTextField1 = null;
	private javax.swing.JPasswordField jPasswordField = null;
	private javax.swing.JTextField jTextField2 = null;
	private javax.swing.JPanel jPanel6 = null;
	private javax.swing.JPanel jPanel7 = null;
	private javax.swing.JCheckBox jCheckBox = null;
	private javax.swing.JCheckBox jCheckBox1 = null;
	private javax.swing.JCheckBox jCheckBox2 = null;
	private javax.swing.JCheckBox jCheckBox3 = null;
	private javax.swing.JSlider jSlider = null;
	private javax.swing.JSlider jSlider2 = null;
	private javax.swing.JPanel jPanel8 = null;
	private javax.swing.JPanel jPanel9 = null;
	private javax.swing.JComboBox jComboBox = null;
	private javax.swing.JButton jButton2 = null;
	private javax.swing.JButton jButton3 = null;
	private javax.swing.JPanel jPanel10 = null;
	private javax.swing.JSplitPane jSplitPane1 = null;
	private javax.swing.JPanel jPanel11 = null;
	private javax.swing.JPanel jPanel13 = null;
	private javax.swing.JButton jButton6 = null;
	private javax.swing.JButton jButton7 = null;
	private javax.swing.JButton jButton5 = null;
	private javax.swing.JTabbedPane jTabbedPane1 = null;
	private javax.swing.JPanel jPanel14 = null;
	private javax.swing.JPanel jPanel12 = null;
	private javax.swing.JPanel jPanel15 = null;
	private javax.swing.JPanel jPanel16 = null;
	private javax.swing.JPanel jPanel17 = null;
	private javax.swing.JPanel jPanel18 = null;
	private javax.swing.JPanel jPanel19 = null;
	private javax.swing.JPanel jPanel20= null;
	
	private javax.swing.JComboBox jComboBox1 = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JScrollPane jScrollPane1 = null;
	private javax.swing.JTable jTable = null;
	private javax.swing.JLabel jLabel5 = null;
	private javax.swing.JLabel jLabel6 = null;
	private javax.swing.JScrollPane jScrollPane = null;
	private javax.swing.JTable jTable1 = null;
	private javax.swing.JScrollPane jScrollPane2 = null;
	private javax.swing.JTable jTable2 = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton jButton4 = null;
	private javax.swing.JButton jButton8 = null;
	private javax.swing.JLabel jLabel7 = null;
	private javax.swing.JLabel jLabel8 = null;
	
	private boolean cambiadoClave = false;
	private boolean cambiadoCodBarra = false;
	
	/**
	 * This is the default constructor
	 */
	public OpcionesSeguridad() throws ConexionExcepcion {
		super(MensajesVentanas.ventanaActiva);
		initialize();
		CR.inputEscaner.getDocument().addDocumentListener(this);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() throws ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("initialize() - start");
		}

		this.setContentPane(getJPanel10());
		this.setSize(800, 600);
		this.setTitle("Seguridad - Actualización de Usuarios");
		this.setModal(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addComponentListener(this);
		
		if (logger.isDebugEnabled()) {
			logger.debug("initialize() - end");
		}
	}
	/**
	 * This method initializes jTabbedPane1
	 * 
	 * @return javax.swing.JTabbedPane1
	 */
	private javax.swing.JTabbedPane getJTabbedPane1() throws ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTabbedPane1() - start");
		}

		if(jTabbedPane1 == null) {
			jTabbedPane1 = new javax.swing.JTabbedPane();
			jTabbedPane1.setPreferredSize(new java.awt.Dimension(256,500));
			jTabbedPane1.setName("opciones");
			jTabbedPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			jTabbedPane1.setBackground(new java.awt.Color(226,226,222));
			jTabbedPane1.setComponentOrientation(java.awt.ComponentOrientation.UNKNOWN);
			jTabbedPane1.setAlignmentX(0.5F);
			agregarOpciones(jTabbedPane1, panel1);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTabbedPane1() - end");
		}
		return jTabbedPane1;
	}
	
	/**
	 * Método agregarPestañas
	 * 
	 * @param jTabbedPane
	 * @param opciones
	 * @throws ConexionExcepcion
	 */
	private void agregarOpciones(JTabbedPane jTabbedPane, String opciones) throws ConexionExcepcion{
		if (logger.isDebugEnabled()) {
			logger.debug("agregarOpciones(JTabbedPane, String) - start");
		}

		if((this.panelActivo == null) || (!this.panelActivo.equals(opciones))){
			jTabbedPane.removeAll();
			this.panelActivo = new String(opciones);
			if(opciones.equals(panel1)){
				jTabbedPane.addTab("Detalles de Usuario ", null, getJPanel4(), null);
			}
			else if(opciones.equals(panel2)){
				jTabbedPane.addTab("Detalles del Perfil ", null, getJPanel17(), null);
				jTabbedPane.addTab("Usuarios Agregados ", null, getJPanel19(), null);
			}
		}	

		if (logger.isDebugEnabled()) {
			logger.debug("agregarOpciones(JTabbedPane, String) - end");
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

		if (jPanel == null) {
			jPanel = new javax.swing.JPanel();
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJButton2(), java.awt.BorderLayout.WEST);
			jPanel.add(getJButton3(), null);
			jPanel.setSize(300, 45);
			jPanel.setPreferredSize(new java.awt.Dimension(256,45));
			jPanel.setComponentOrientation(java.awt.ComponentOrientation.LEFT_TO_RIGHT);
			jPanel.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel() - end");
		}
		return jPanel;
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

		if (jButton == null) {
			jButton = new JHighlightButton();
			jButton.setIcon(
				new javax.swing.ImageIcon(
					getClass().getResource(
					"/com/becoblohm/cr/gui/resources/icons/ix24x24/check2.png")));
			jButton.setText("Aceptar");
			jButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			jButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jButton.setPreferredSize(new java.awt.Dimension(115, 35));
			jButton.setComponentOrientation(
				java.awt.ComponentOrientation.LEFT_TO_RIGHT);
			jButton.setMaximumSize(new java.awt.Dimension(85, 38));
			jButton.setMinimumSize(new java.awt.Dimension(85, 30));
			jButton.setBackground(new java.awt.Color(226,226,222));
			jButton.addActionListener(this);
			jButton.addKeyListener(this);
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

		if (jButton1 == null) {
			jButton1 = new JHighlightButton();
			jButton1.setIcon(
				new javax.swing.ImageIcon(
					getClass().getResource(
						"/com/becoblohm/cr/gui/resources/icons/ix24x24/delete2.png")));
			jButton1.setText("Cancelar");
			jButton1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			jButton1.setHorizontalTextPosition(
				javax.swing.SwingConstants.RIGHT);
			jButton1.setPreferredSize(new java.awt.Dimension(115, 35));
			jButton1.setBackground(new java.awt.Color(226,226,222));
			jButton1.addActionListener(this);
			jButton1.addKeyListener(this);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton1() - end");
		}
		return jButton1;
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

		if (jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setText("Opciones de Seguridad");
			jLabel.setBackground(new java.awt.Color(69,107,127));
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel.setForeground(java.awt.Color.white);
			jLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix48x48/box_software.png")));
			jLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
			jLabel.setComponentOrientation(java.awt.ComponentOrientation.UNKNOWN);
			jLabel.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel() - end");
		}
		return jLabel;
	}
	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel4() throws ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel4() - start");
		}

		if (jPanel4 == null) {
			jPanel4 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout6 = new java.awt.FlowLayout();
			layFlowLayout6.setAlignment(java.awt.FlowLayout.CENTER);
			layFlowLayout6.setHgap(7);
			layFlowLayout6.setVgap(7);
			jPanel4.setLayout(layFlowLayout6);
			jPanel4.add(getJPanel5(), null);
			jPanel4.add(getJPanel2(), null);
			jPanel4.add(getJPanel6(), null);
			jPanel4.add(getJPanel8(), null);
			jPanel4.add(getJPanel9(), null);
			jPanel4.setSize(553, 500);
			jPanel4.setPreferredSize(new java.awt.Dimension(200,500));
			jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			jPanel4.setMinimumSize(new java.awt.Dimension(300,118));
			jPanel4.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel4() - end");
		}
		return jPanel4;
	}
	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel5() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel5() - start");
		}

		if (jPanel5 == null) {
			jPanel5 = new javax.swing.JPanel();
			java.awt.GridLayout layGridLayout28 = new java.awt.GridLayout();
			layGridLayout28.setRows(4);
			layGridLayout28.setColumns(2);
			jPanel5.setLayout(layGridLayout28);
			jPanel5.add(getJLabel1(), null);
			jPanel5.add(getTxtCodigo(), null);
			jPanel5.add(getJLabel2(), null);
			jPanel5.add(getJTextField1(), null);
			jPanel5.add(getJLabel3(), null);
			jPanel5.add(getJTextField2(), null);
			jPanel5.add(getJLabel4(), null);
			jPanel5.add(getJPasswordField(), null);
			jPanel5.setPreferredSize(new java.awt.Dimension(400,90));
			jPanel5.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel5() - end");
		}
		return jPanel5;
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

		if (jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setText("Identificación Impresa:");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel1() - end");
		}
		return jLabel1;
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

		if (jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setText("Identificador de Usuario: ");
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

		if (jLabel3 == null) {
			jLabel3 = new javax.swing.JLabel();
			jLabel3.setText("Nombre del Usuario:");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel3() - end");
		}
		return jLabel3;
	}
	/**
	 * This method initializes jLabel4
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel4() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel4() - start");
		}

		if (jLabel4 == null) {
			jLabel4 = new javax.swing.JLabel();
			jLabel4.setText("Contraseña: ");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel4() - end");
		}
		return jLabel4;
	}
	/**
	 * This method initializes txtCodigo
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtCodigo() {
		if (logger.isDebugEnabled()) {
			logger.debug("getTxtCodigo() - start");
		}

		if (txtCodigo == null) {
			txtCodigo = new javax.swing.JPasswordField();
			txtCodigo.setName("codigoBarra");
			txtCodigo.addKeyListener(this);
			txtCodigo.addFocusListener(this);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getTxtCodigo() - end");
		}
		return txtCodigo;
	}
	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField1() - start");
		}

		if (jTextField1 == null) {
			jTextField1 = new javax.swing.JTextField();
			jTextField1.setName("numFicha");
			jTextField1.addKeyListener(this);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField1() - end");
		}
		return jTextField1;
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

		if (jPasswordField == null) {
			jPasswordField = new javax.swing.JPasswordField();
			jPasswordField.setEchoChar('*');
			jPasswordField.addKeyListener(this);
			jPasswordField.setEnabled(false);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPasswordField() - end");
		}
		return jPasswordField;
	}
	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField2() - start");
		}

		if (jTextField2 == null) {
			jTextField2 = new javax.swing.JTextField();
			jTextField2.addKeyListener(this);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField2() - end");
		}
		return jTextField2;
	}
	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel6() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel6() - start");
		}

		if (jPanel6 == null) {
			jPanel6 = new javax.swing.JPanel();
			jPanel6.add(getJPanel7(), null);
			jPanel6.setPreferredSize(new java.awt.Dimension(500, 80));
			jPanel6.setBorder(
				javax.swing.BorderFactory.createTitledBorder(
					null,
					"Opciones Adicionales ",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION,
					null,
					null));
			jPanel6.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel6() - end");
		}
		return jPanel6;
	}
	/**
	 * This method initializes jPanel7
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel7() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel7() - start");
		}

		if (jPanel7 == null) {
			jPanel7 = new javax.swing.JPanel();
			java.awt.GridLayout layGridLayout29 = new java.awt.GridLayout();
			layGridLayout29.setRows(3);
			jPanel7.setLayout(layGridLayout29);
			jPanel7.add(getJCheckBox(), null);
			jPanel7.add(getJCheckBox1(), null);
			jPanel7.add(getJCheckBox2(), null);
			jPanel7.setPreferredSize(new java.awt.Dimension(490, 50));
			jPanel7.setFont(
				new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
			jPanel7.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel7() - end");
		}
		return jPanel7;
	}
	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private javax.swing.JCheckBox getJCheckBox() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJCheckBox() - start");
		}

		if (jCheckBox == null) {
			jCheckBox = new javax.swing.JCheckBox();
			jCheckBox.setText("Cambiar contraseña en el próximo login");
			jCheckBox.setBackground(new java.awt.Color(226,226,222));
			jCheckBox.addKeyListener(this);
			jCheckBox.addMouseListener(this);
			jCheckBox.addActionListener(this);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJCheckBox() - end");
		}
		return jCheckBox;
	}
	/**
	 * This method initializes jCheckBox1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private javax.swing.JCheckBox getJCheckBox1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJCheckBox1() - start");
		}

		if (jCheckBox1 == null) {
			jCheckBox1 = new javax.swing.JCheckBox();
			jCheckBox1.setText("Usuario activo");
			jCheckBox1.setBackground(new java.awt.Color(226,226,222));
			jCheckBox1.addKeyListener(this);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJCheckBox1() - end");
		}
		return jCheckBox1;
	}
	/**
	 * This method initializes jCheckBox2
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private javax.swing.JCheckBox getJCheckBox2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJCheckBox2() - start");
		}

		if (jCheckBox2 == null) {
			jCheckBox2 = new javax.swing.JCheckBox();
			jCheckBox2.setName("");
			jCheckBox2.setText("El usuario puede cambiar su contraseña");
			jCheckBox2.setBackground(new java.awt.Color(226,226,222));
			jCheckBox2.addKeyListener(this);
			jCheckBox2.addMouseListener(this);
			jCheckBox2.addActionListener(this);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJCheckBox2() - end");
		}
		return jCheckBox2;
	}
	/**
	 * This method initializes jSlider
	 * 
	 * @return javax.swing.JSlider
	 */
	private javax.swing.JSlider getJSlider() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJSlider() - start");
		}

		if (jSlider == null) {
			jSlider = new javax.swing.JSlider();
			//jSlider.setExtent(5);
			jSlider.setMajorTickSpacing(1);
			jSlider.setMaximum(5);
			jSlider.setMinorTickSpacing(1);
			jSlider.setPaintLabels(true);
			jSlider.setPaintTicks(false);
			jSlider.setPaintTrack(true);
			jSlider.setName("Nivel");
			jSlider.setFont(
				new java.awt.Font("Dialog", java.awt.Font.PLAIN, 8));
			jSlider.setPreferredSize(new java.awt.Dimension(200, 40));
			jSlider.setMinimum(1);
			jSlider.setOrientation(javax.swing.JSlider.HORIZONTAL);
			jSlider.setSnapToTicks(true);
			jSlider.setValue(1);
			jSlider.setBackground(new java.awt.Color(226,226,222));
			jSlider.addKeyListener(this);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJSlider() - end");
		}
		return jSlider;
	}
	/**
	 * This method initializes jPanel8
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel8() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel8() - start");
		}

		if (jPanel8 == null) {
			jPanel8 = new javax.swing.JPanel();
			jPanel8.add(getJSlider(), null);
			jPanel8.setPreferredSize(new java.awt.Dimension(500,70));
			jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nivel de Auditoría ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
			jPanel8.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel8() - end");
		}
		return jPanel8;
	}
	/**
	 * This method initializes jPanel9
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel9() throws ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel9() - start");
		}

		if (jPanel9 == null) {
			jPanel9 = new javax.swing.JPanel();
			jPanel9.add(getJComboBox(), null);
			jPanel9.setPreferredSize(new java.awt.Dimension(500, 70));
			jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Seleccionar Perfil ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
			jPanel9.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel9() - end");
		}
		return jPanel9;
	}
	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Iterator'
	* Fecha: agosto 2011
	*/
	private javax.swing.JComboBox getJComboBox() throws ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("getJComboBox() - start");
		}

		if (jComboBox == null) {
			jComboBox = new javax.swing.JComboBox();
			jComboBox.setFont(
				new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jComboBox.setMaximumRowCount(15);
			jComboBox.setPreferredSize(new java.awt.Dimension(130, 20));
			jComboBox.setBackground(new java.awt.Color(226,226,222));
			Iterator<?> perfiles;
			try {
				perfiles = MaquinaDeEstado.cargarRegistros(new Perfil(), true).iterator();
				while (perfiles.hasNext()){
					jComboBox.addItem(((Perfil)perfiles.next()).getDescripcion());
				}
			} catch (ExcepcionCr e) {
				logger.error("getJComboBox()", e);

				MensajesVentanas.mensajeError(e.getMensaje());
			}
			jComboBox.addKeyListener(this);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJComboBox() - end");
		}
		return jComboBox;
	}
	/**
	 * This method initializes jCheckBox3
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private javax.swing.JCheckBox getJCheckBox3() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJCheckBox3() - start");
		}

		if (jCheckBox3 == null) {
			jCheckBox3 = new javax.swing.JCheckBox();
			jCheckBox3.setText("Perfil Activo");
			jCheckBox3.setBackground(new java.awt.Color(226,226,222));
			jCheckBox3.addKeyListener(this);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJCheckBox3() - end");
		}
		return jCheckBox3;
	}
	/**
	 * This method initializes jSlider2
	 * 
	 * @return javax.swing.JSlider
	 */
	private javax.swing.JSlider getJSlider2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJSlider2() - start");
		}

		if (jSlider2 == null) {
			jSlider2 = new javax.swing.JSlider();
			jSlider2.setMajorTickSpacing(1);
			jSlider2.setMaximum(5);
			jSlider2.setMinorTickSpacing(1);
			jSlider2.setPaintLabels(true);
			jSlider2.setPaintTicks(false);
			jSlider2.setPaintTrack(true);
			jSlider2.setName("NivelPerfil");
			jSlider2.setFont(
				new java.awt.Font("Dialog", java.awt.Font.PLAIN, 8));
			jSlider2.setPreferredSize(new java.awt.Dimension(200, 40));
			jSlider2.setMinimum(1);
			jSlider2.setOrientation(javax.swing.JSlider.HORIZONTAL);
			jSlider2.setSnapToTicks(true);
			jSlider2.setValue(1);
			jSlider2.setBackground(new java.awt.Color(226,226,222));
			jSlider2.addKeyListener(this);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJSlider2() - end");
		}
		return jSlider2;
	}
	/**
	 * This method initializes jPanel15
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel15() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel15() - start");
		}

		if (jPanel15 == null) {
			jPanel15 = new javax.swing.JPanel();
			jPanel15.add(getJSlider2(), null);
			jPanel15.setPreferredSize(new java.awt.Dimension(500,70));
			jPanel15.setBorder(
				javax.swing.BorderFactory.createTitledBorder(
					null,
					"Nivel de Auditoría ",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION,
					null,
					null));
			jPanel15.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel15() - end");
		}
		return jPanel15;
	}
	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel16() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel16() - start");
		}

		if (jPanel16 == null) {
			jPanel16 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout3 = new java.awt.FlowLayout();
			layFlowLayout3.setAlignment(java.awt.FlowLayout.CENTER);
			jPanel16.setLayout(layFlowLayout3);
			jPanel16.add(getJComboBox1(), null);
			jPanel16.add(getJCheckBox3(), null);
			jPanel16.setPreferredSize(new java.awt.Dimension(300,100));
			jPanel16.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel16() - end");
		}
		return jPanel16;
	}
	/**
	 * This method initializes jPanel17
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel17() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel17() - start");
		}

		if (jPanel17 == null) {
			jPanel17 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout7 = new java.awt.FlowLayout();
			layFlowLayout7.setAlignment(java.awt.FlowLayout.CENTER);
			layFlowLayout7.setHgap(7);
			layFlowLayout7.setVgap(7);
			jPanel17.setLayout(layFlowLayout7);
			jPanel17.add(getJPanel18(), null);
			jPanel17.add(getJPanel15(), null);
			jPanel17.add(getJPanel1(), null);
			jPanel17.add(getJLabel5(), null);
			jPanel17.add(getJLabel6(), null);
			jPanel17.setSize(556, 500);
			jPanel17.setPreferredSize(new java.awt.Dimension(200,500));
			jPanel17.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			jPanel17.setMinimumSize(new java.awt.Dimension(300,118));
			jPanel17.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel17() - end");
		}
		return jPanel17;
	}
	/**
	 * This method initializes jPanel18
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel18() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel18() - start");
		}

		if (jPanel18 == null) {
			jPanel18 = new javax.swing.JPanel();
			java.awt.GridLayout layGridLayout2 = new java.awt.GridLayout();
			javax.swing.border.TitledBorder ivjTitledBorder = javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black);
			layGridLayout2.setRows(1);
			ivjTitledBorder.setTitleColor(java.awt.Color.lightGray);
			jPanel18.setLayout(layGridLayout2);
			jPanel18.add(getJPanel16(), null);
			jPanel18.setBorder(ivjTitledBorder);
			jPanel18.setPreferredSize(new java.awt.Dimension(500, 80));
			jPanel18.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel18() - end");
		}
		return jPanel18;
	}
	/**
	 * This method initializes jPanel19
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel19() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel19() - start");
		}

		if (jPanel19 == null) {
			jPanel19 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout8 = new java.awt.FlowLayout();
			layFlowLayout8.setAlignment(java.awt.FlowLayout.CENTER);
			layFlowLayout8.setHgap(7);
			layFlowLayout8.setVgap(7);
			jPanel19.setLayout(layFlowLayout8);
			jPanel19.add(getJPanel20(), null);
			jPanel19.setPreferredSize(new java.awt.Dimension(600,500));
			jPanel19.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			jPanel19.setMinimumSize(new java.awt.Dimension(300,118));
			jPanel19.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel19() - end");
		}
		return jPanel19;
	}
	/**
	 * This method initializes jPanel20
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel20() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel20() - start");
		}

		if(jPanel20 == null) {
			jPanel20 = new javax.swing.JPanel();
			java.awt.GridLayout layGridLayout1 = new java.awt.GridLayout();
			layGridLayout1.setRows(1);
			layGridLayout1.setHgap(15);
			layGridLayout1.setVgap(0);
			jPanel20.setLayout(layGridLayout1);
			jPanel20.add(getJScrollPane(), null);
			jPanel20.add(getJScrollPane2(), null);
			jPanel20.setBackground(new java.awt.Color(226,226,222));
			jPanel20.setPreferredSize(new java.awt.Dimension(600,400));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel20() - end");
		}
		return jPanel20;
	}
	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton2() - start");
		}

		if (jButton2 == null) {
			jButton2 = new JHighlightButton();
			jButton2.setText("Buscar ");
			jButton2.setIcon(
				new javax.swing.ImageIcon(
					getClass().getResource(
						"/com/becoblohm/cr/gui/resources/icons/ix24x24/replace.png")));
			jButton2.setPreferredSize(new java.awt.Dimension(115, 35));
			jButton2.setMaximumSize(new java.awt.Dimension(85, 38));
			jButton2.setMinimumSize(new java.awt.Dimension(85, 38));
			jButton2.setBackground(new java.awt.Color(226,226,222));
			jButton2.addActionListener(this);
			jButton2.addKeyListener(this);
			jButton2.setEnabled(false);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton2() - end");
		}
		return jButton2;
	}
	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton3() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton3() - start");
		}

		if (jButton3 == null) {
			try {
				jButton3 = new JHighlightButton();
				jButton3.setIcon(
					new javax.swing.ImageIcon(
						getClass().getResource(
							"/com/becoblohm/cr/gui/resources/icons/ix24x24/first_aid_box.png")));
				jButton3.setText("Ayuda ");
				jButton3.setPreferredSize(new java.awt.Dimension(115, 35));
				jButton3.setMaximumSize(new java.awt.Dimension(85, 38));
				jButton3.setMinimumSize(new java.awt.Dimension(85, 38));
				jButton3.setBorderPainted(true);
				jButton3.setBackground(new java.awt.Color(226,226,222));
				jButton3.addActionListener(this);
				jButton3.addKeyListener(this);
				jButton3.setEnabled(false);
			} catch (java.lang.Throwable e) {
				logger.error("getJButton3()", e);

				//  Do Something
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton3() - end");
		}
		return jButton3;
	}

	/**
	 * Method validarDatos.
	 * @return boolean
	 */
	public boolean camposVacios(){
		if (logger.isDebugEnabled()) {
			logger.debug("camposVacios() - start");
		}

		if ((jTextField1.getText().equals("")) && (jTextField2.getText().equals("")) && (new String(jPasswordField.getPassword()).equals("")) || (new String(jPasswordField.getPassword()).equals("")) || (new String(txtCodigo.getPassword()).equals("")))
		{
			if (logger.isDebugEnabled()) {
				logger.debug("camposVacios() - end");
			}
			return true;
		}
		else
		{
			if (logger.isDebugEnabled()) {
				logger.debug("camposVacios() - end");
			}
			return false;
		}
	}

	/**
	 * Method limpiarValores.
	 */
	public void limpiarValores(){
		if (logger.isDebugEnabled()) {
			logger.debug("limpiarValores() - start");
		}
		//this.txtCodigo.removeFocusListener(this);
		if(panelActivo.equals(panel1)){
			this.cambiadoClave = false;
			this.cambiadoCodBarra = false;
			this.txtCodigo.setEnabled(true);
			this.jPasswordField.setEnabled(true);
			this.txtCodigo.setText("");
			this.jTextField1.setText("");
			this.jTextField2.setText("");
			this.jPasswordField.setText("");
			if (jCheckBox.isSelected()) this.jCheckBox.doClick();
			if (jCheckBox1.isSelected()) this.jCheckBox1.doClick();
			if (jCheckBox2.isSelected()) this.jCheckBox2.doClick();
			this.jSlider.setValue(1);
			this.jComboBox.setSelectedIndex(0);
			//this.txtCodigo.requestFocus();
		}  else if(panelActivo.equals(panel2)){
			getJComboBox1().setSelectedIndex(0);
			getJSlider2().setValue(new Integer("1").intValue());
			modeloTablaFunciones.iniciarDatosTabla(modeloTablaFunciones.getTitulos().length);
			modeloTablaUsuariosPerfil.iniciarDatosTabla(modeloTablaUsuariosPerfil.getTitulos().length);
			modeloTablaUsuariosSistema.iniciarDatosTabla(modeloTablaUsuariosSistema.getTitulos().length);
		}
		//this.txtCodigo.addFocusListener(this);
		if (logger.isDebugEnabled()) {
			logger.debug("limpiarValores() - end");
		}
	}

	/**
	 * Method cargarValores.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se comentó código muerto y variables sin uso
	* Fecha: agosto 2011
	*/
	public void cargarValores(){
		if (logger.isDebugEnabled()) {
			logger.debug("cargarValores() - start");
		}

		if(panelActivo.equals(panel1)){
			if (xUsuario.getCodigoBarra() != null){
				//String codigo = null;//new String(Control.criptografo.desencriptar(xUsuario.getCodigoBarra().getBytes()));
				/*if (codigo != null){
					this.txtCodigo.setText(codigo);
					this.txtCodigo.setEnabled(false);
				} else {*/
					this.txtCodigo.setText(xUsuario.getCodigoBarra());
					this.txtCodigo.setEnabled(false);
				//} 
			}
			this.jTextField1.setText(xUsuario.getNumFicha());
			this.jTextField2.setText(xUsuario.getNombre());

			if (xUsuario.getClave() != null){
				//String codigo = null;//new String(Control.criptografo.desencriptar(xUsuario.getClave().getBytes()));
				/*if (codigo != null){
					this.jPasswordField.setText(codigo);
					this.jPasswordField.setEnabled(false);
				} else {*/
					this.jPasswordField.setText(xUsuario.getClave());
					this.jPasswordField.setEnabled(false);
				//} 
			}

			if ((xUsuario.isIndicaCambiarClave())&&(!(jCheckBox.isSelected()))){
				this.jCheckBox.doClick();
			}
			if ((xUsuario.isRegVigente())&&(!(jCheckBox1.isSelected()))){
				this.jCheckBox1.doClick();
			}
			if ((xUsuario.isPuedeCambiarClave())&&(!(jCheckBox2.isSelected()))){
				this.jCheckBox2.doClick();
			}
			this.jSlider.setValue(new Integer(xUsuario.getNivelAuditoria()).intValue());
			this.jComboBox.setSelectedIndex(new Integer(xUsuario.getCodPerfil()).intValue()-1);
			this.jTextField1.requestFocus();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cargarValores() - end");
		}
	}
	
	/**
	 * Method enviarValores.
	 * @return Usuario
	 */
	public Usuario enviarValores(){
		if (logger.isDebugEnabled()) {
			logger.debug("enviarValores() - start");
		}

		if(panelActivo.equals(panel1)){
			String codigo = new String(txtCodigo.getPassword());
			if ((!(codigo.equals(""))) && (!(codigo == null)) /*&& (codigo.length() == Control.getLONGITUD_CODIGO())*/){
				xUsuario.setCodigoBarra(codigo);
			}	
			xUsuario.setNumFicha(this.jTextField1.getText());
			xUsuario.setNombre(this.jTextField2.getText());
			String clave = new String(this.jPasswordField.getPassword());
			if (!(clave.equals(""))){
				xUsuario.setClave(clave);
			}	
			xUsuario.setIndicaCambiarClave(this.jCheckBox.isSelected());
			xUsuario.setPuedeCambiarClave(this.jCheckBox2.isSelected());
			xUsuario.setRegVigente(this.jCheckBox1.isSelected());
			xUsuario.setNivelAuditoria(new Integer(this.jSlider.getValue()).toString());
			Perfil xPerfil = new Perfil();
			xPerfil.setDescripcion(this.jComboBox.getSelectedItem().toString());
			try {
				xPerfil.obtenerDatos(xPerfil);
			} catch (ExcepcionCr e) {
				logger.error("enviarValores()", e);

				MensajesVentanas.mensajeError(e.getMensaje());
			}
			xUsuario.setCodPerfil(xPerfil.getCodPerfil());
			xUsuario.setTiempoVigenciaClave((short)30);
			xUsuario.setCambioClave(this.cambiadoClave);
			xUsuario.setCambioCodbarra(this.cambiadoCodBarra);
		}	

		if (logger.isDebugEnabled()) {
			logger.debug("enviarValores() - end");
		}
		return xUsuario;
	}

	/**
	 * Método getFuncionesPerfil
	 * 	Retorna las funciones configuradas para el perfil selecionado.
	 * 
	 * @return LinkedHashSet
	 */
	private LinkedHashSet<ListaFuncion> getFuncionesPerfil(){
		if (logger.isDebugEnabled()) {
			logger.debug("getFuncionesPerfil() - start");
		}

		LinkedHashSet<ListaFuncion> funcionesPerfil = new LinkedHashSet<ListaFuncion>();
		ListaFuncion xMenu = new ListaFuncion();
		Funcion xFuncion = new Funcion();
		Modulo xModulo = new Modulo();
		try {
			if(panelActivo.equals(panel2)){
				for(int i=0; i<modeloTablaFunciones.datos.length; i++){
					xModulo = new Modulo();
					xModulo.setDescripcion(modeloTablaFunciones.datos[i][0].toString());
					xModulo.obtenerDatos(xModulo);
					xFuncion = new Funcion();
					xFuncion.setDescripcion(modeloTablaFunciones.datos[i][1].toString());
					xFuncion.setCodModulo(xModulo.getCodModulo());
					xFuncion.setRaiz(xModulo);
					xFuncion = (Funcion)xFuncion.obtenerDatos(xFuncion).get(0);
					xMenu = new ListaFuncion();
					xMenu.setFuncion(xFuncion);
					boolean habilitado = modeloTablaFunciones.datos[i][2] == Boolean.TRUE ? true : false;
					boolean autorizado = modeloTablaFunciones.datos[i][3] == Boolean.TRUE ? true : false;
					xMenu.setHabilitado(habilitado);
					xMenu.setAutorizado(autorizado);
					funcionesPerfil.add(xMenu);
				}
			}
		} catch (BaseDeDatosExcepcion e) {
			logger.error("getFuncionesPerfil()", e);

			MensajesVentanas.mensajeError(e.getMensaje());
		} catch (FuncionExcepcion e) {
			logger.error("getFuncionesPerfil()", e);

			MensajesVentanas.mensajeError(e.getMensaje());
		} catch (ExcepcionCr e) {
			MensajesVentanas.mensajeError(e.getMensaje());
			logger.error("getFuncionesPerfil()", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getFuncionesPerfil() - end");
		}
		return funcionesPerfil;
	}

	/**
	 * Método actionPerformed
	 *
	 * @param evento
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList y se comentó código muerto
	* Fecha: agosto 2011
	*/
	public void actionPerformed(ActionEvent evento) {
		if (logger.isDebugEnabled()) {
			logger.debug("actionPerformed(ActionEvent) - start");
		}

		try {
			if (evento.getSource().equals(getJComboBox1())){
				if (!(jComboBox1.getSelectedItem().toString().equals(""))){
					Perfil xPerfil = new Perfil();
					xPerfil.setDescripcion(jComboBox1.getSelectedItem().toString());
					try {
						try{ 
							xPerfil = xPerfil.obtenerDatos(xPerfil);
							perfilActual = xPerfil;
						} catch (PerfilExcepcion e1) {
							logger.error("actionPerformed(ActionEvent)", e1);

							/*int resp = MensajesVentanas.preguntarSiNo("Perfil no registrado!! Desea configurarlo?");
							MensajesVentanas.aviso(String.valueOf(resp));
							if(resp != 0){
								getJComboBox1().setSelectedIndex(0);
								modeloTablaFunciones.iniciarDatosTabla(modeloTablaFunciones.titulos.length);
								throw new PerfilExcepcion(e1.getMensaje());
							} else{
								xPerfil.setRegVigente(true);
							}*/
						}
						if((xPerfil.isRegVigente() && !getJCheckBox3().isSelected()) || (!xPerfil.isRegVigente() && getJCheckBox3().isSelected())) 
							getJCheckBox3().doClick();
						int[] totales = modeloTablaFunciones.llenarTabla(xPerfil);
						getJLabel6().setText(totales[0]+"/"+totales[1]);
						modeloTablaUsuariosSistema.llenarTabla(modeloTablaUsuariosPerfil.llenarTabla(xPerfil));
					} catch (BaseDeDatosExcepcion e1) {
						logger.error("actionPerformed(ActionEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (PerfilExcepcion e1) {
						logger.error("actionPerformed(ActionEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (ExcepcionCr e1) {
						logger.error("actionPerformed(ActionEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
					}
				} else modeloTablaFunciones.iniciarDatosTabla(modeloTablaFunciones.titulos.length);
			}
			else if (evento.getSource() instanceof JButton){
				JButton	boton = (JButton)evento.getSource();
				if(boton.getText().equals("Aceptar") && (evento.getID() == 1001)){

					// Pestaña de Opciones de Usuario
					if(panelActivo.equals(panel1)){
						if (!(camposVacios())){
							CR.me.iraSeguridad();
							CR.me.actualizarDatos(enviarValores());
							MensajesVentanas.aviso("Operación satisfactoria!!");
							limpiarValores();
						}
						else MensajesVentanas.mensajeError("Datos incompletos");
						//this.txtCodigo.setText("");
						this.txtCodigo.requestFocus();
					}
					
					// Pestaña de Opciones de Perfiles
					else if(panelActivo.equals(panel2)){
						ArrayList<ListaFuncion> funciones = new ArrayList<ListaFuncion>(getFuncionesPerfil());
						//if (getFuncionesPerfil() != null){
						if (funciones != null){
							CR.me.iraSeguridad();
							Perfil xPerfil = new Perfil();
							xPerfil.setDescripcion(getJComboBox1().getSelectedItem().toString());
							boolean vigente = getJCheckBox3().isSelected() == true ? true:false;
							xPerfil.setRegVigente(vigente);
							xPerfil.setNivelAuditoria(String.valueOf(getJSlider2().getValue()));
							xPerfil.setFunciones(funciones);
							xPerfil.actualizarDatos(xPerfil);
							xPerfil.vincularFunciones(funciones);
							MensajesVentanas.aviso("Operación satisfactoria!!");
							limpiarValores();
						}
						//else MensajesVentanas.mensajeError("Funciones no configuradas");
					}
				} else if(boton.getName()!= null && boton.getName().equals("setCodBarra") && (evento.getID() == 1001)){
					getTxtCodigo().setEnabled(true);
					getTxtCodigo().setText("");
					getTxtCodigo().requestFocus();
					this.cambiadoCodBarra = true;
				} else if(boton.getName()!= null && boton.getName().equals("setClave") && (evento.getID() == 1001)){
					getJPasswordField().setEnabled(true);
					getJPasswordField().setText("");
					getJPasswordField().requestFocus();	
					this.cambiadoClave = true;	
				} else if(boton.getText().equals("Cancelar") && (evento.getID() == 1001)){
					limpiarValores();
					getJButton7().requestFocus();
				} else if(boton.getActionCommand().equals("opcUsuarios") && (evento.getID() == 1001)){
					agregarOpciones(jTabbedPane1, panel1);
				} else if(boton.getActionCommand().equals("opcPerfiles") && (evento.getID() == 1001)){
					agregarOpciones(jTabbedPane1, panel2);
				} else if(boton.getActionCommand().equals("opcSalir") && (evento.getID() == 1001)){
					try {
						SyncCnxMonitor.getInstance().notifyIn();
						// Actualiza estas entidades inmediatamente
						BeansSincronizador.actualizarConfiguracionAcceso(false);
						
						//Preguntamos si entraste por caja registradora o directo por el módulo de usuario
						//Esto según la preferencia puedefacturar de sistemas
						boolean puedeFacturar = false;
						InitCR.cargarPreferencias();
						puedeFacturar = InitCR.preferenciasCR.getConfigStringForParameter("sistema", "puedeFacturar").trim().toUpperCase().equals("S") ? true : false;
						if (puedeFacturar)
							CR.me.volverInicio();
							
						CR.inputEscaner.getDocument().removeDocumentListener(this);
						this.dispose();
					} catch (Throwable e1) {
						logger.error("actionPerformed(ActionEvent)", e1);
					} finally {
						SyncCnxMonitor.getInstance().notifyOut();
					}
				}
			}
		} catch (ExcepcionCr e) {
			logger.error("actionPerformed(ActionEvent)", e);

			MensajesVentanas.mensajeError(e.getMensaje());
		} catch (ConexionExcepcion e) {
			logger.error("actionPerformed(ActionEvent)", e);

			MensajesVentanas.mensajeError(e.getMensaje());
		}	

		if (logger.isDebugEnabled()) {
			logger.debug("actionPerformed(ActionEvent) - end");
		}
	}

	/**
	 * Método keyPressed
	 *
	 * @param e
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	public void keyPressed(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}

		try {
			if (e.getSource() instanceof JTabbedPane) {
				this.getFocusOwner().transferFocus();	
			}
			else if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {

				//Pestaña de Opciones de Usuario
				if(this.panelActivo.equals(panel1)){
					if /*((*/(e.getSource() instanceof JPasswordField) /*|| (e.getSource() instanceof JTextField)))*/ {
						this.getFocusOwner().transferFocus();
						if ((e.getSource() instanceof JPasswordField)){
							((JPasswordField)e.getSource()).getName().equals("codigoBarra");
							String xCodigo = new String(txtCodigo.getPassword());
							try{
								xUsuario = xUsuario.cargarDatos(xCodigo);
							} catch (ExcepcionCr ex) {
								logger.error("keyPressed(KeyEvent)", ex);

								xUsuario = null;
							}
							if (xUsuario != null) cargarValores();
							else { 
								try{
									String identificador = Control.codigoValido(xCodigo).get(1).toString(); 
									xUsuario = new Usuario();
									xUsuario.setDatosPersonales(new Colaborador().obtenerDatos(identificador));
									xUsuario.setNumFicha(xUsuario.getDatosPersonales().getNumFicha());
									xUsuario.setNombre(xUsuario.getDatosPersonales().getNombre());
									cargarValores();
								} catch (ExcepcionCr ex) {
									logger.error("keyPressed(KeyEvent)", ex);

									MensajesVentanas.mensajeError(ex.getMensaje());
									limpiarValores();
								}
							}
						}
						else if (((JTextField)e.getSource()).getName().equals("numFicha")) {
							if (new String(txtCodigo.getPassword()).length() < Control.getLONGITUD_CODIGO()){
								xUsuario = xUsuario.cargarDatos(jTextField1.getText());
								cargarValores();
							}
						}
					} else if (e.getSource().equals(this.getJCheckBox())){
						if(!this.getJCheckBox2().isSelected() && !this.getJCheckBox().isSelected()) {
							this.getJCheckBox2().setSelected(true);
							//this.getJCheckBox().doClick();
						}
					} else if (e.getSource().equals(this.getJCheckBox2())){
						if(this.getJCheckBox().isSelected() && this.getJCheckBox2().isSelected()) {
							this.getJCheckBox().setSelected(false);	
						}
					} else if (e.getSource() instanceof JButton){
						JButton	boton = (JButton)e.getSource();
						boton.doClick();
					} else if ((e.getSource() instanceof JSlider)||(e.getSource() instanceof JComboBox)) {
						this.getFocusOwner().transferFocus();
						}
					}
					
				//Pestaña de Opciones de Perfiles
				else if(this.panelActivo.equals(panel2)){
					if (e.getSource() instanceof JTable) {
						if (e.getSource().equals(getJTable2())) {
							getJTable1().requestFocus();
						}
						else if (e.getSource().equals(getJTable1())) {
							ArrayList<String> nuevosMiembros = new ArrayList<String>();
							Usuario xUsuario = new Usuario();
							xUsuario.setNumFicha(getJTable1().getValueAt(getJTable1().getSelectedRow(), 0).toString());
							nuevosMiembros.add(xUsuario.getNumFicha());
							if(!(perfilActual.getCodPerfil().equals("0"))){
								perfilActual.agregarMiembros(nuevosMiembros);
								modeloTablaUsuariosSistema.llenarTabla(modeloTablaUsuariosPerfil.llenarTabla(perfilActual));
							}	
						}
						else{
							int fila = jTable.getSelectedRow();
							int columna = jTable.getSelectedColumn();
							if(getJTable().isCellEditable(fila, columna)){
								Boolean valor = (Boolean)getJTable().getValueAt(fila, columna);
								if(valor.booleanValue()) 
									getJTable().setValueAt(Boolean.FALSE, fila, columna);
								else
									getJTable().setValueAt(Boolean.TRUE, fila, columna);
							}
						}
					} else if (e.getSource() instanceof JButton){
						JButton	boton = (JButton)e.getSource();
						boton.doClick();
					} else if ((e.getSource() instanceof JSlider)||(e.getSource() instanceof JComboBox)) {
						this.getFocusOwner().transferFocus();
					}
				}
	
				} else if ((e.getKeyCode() == KeyEvent.VK_DOWN)&&(!(e.getSource() instanceof JComboBox)&&(!(e.getSource() instanceof JTable)))) {
					this.getFocusOwner().transferFocus();
				} else if ((e.getKeyCode() == KeyEvent.VK_UP)&&(!(e.getSource() instanceof JComboBox))&&(!(e.getSource() instanceof JTable))) {
					this.getFocusOwner().transferFocusBackward();
				} else if ((e.getKeyCode() == KeyEvent.VK_RIGHT)&&(e.getSource() instanceof JButton)) {
					this.getFocusOwner().transferFocus();
				} else if ((e.getKeyCode() == KeyEvent.VK_LEFT)&&(e.getSource() instanceof JButton)) {
					this.getFocusOwner().transferFocusBackward();
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					if((panelActivo.equals(panel1)) && (e.getSource().equals(txtCodigo))){
						limpiarValores();
					}	
					else if((panelActivo.equals(panel2)) && (e.getSource() instanceof JTable))
						getJButton().requestFocus();
					else getJButton7().requestFocus();	
				}
		} catch (ExcepcionCr ex) {
			logger.error("keyPressed(KeyEvent)", ex);

			MensajesVentanas.mensajeError(ex.getMensaje());
		} catch (Exception ex) {
			logger.error("keyPressed(KeyEvent)", ex);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - end");
		}
	}

	public void keyReleased(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyReleased(KeyEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("keyReleased(KeyEvent) - end");
		}
	}

	public void keyTyped(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyTyped(KeyEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("keyTyped(KeyEvent) - end");
		}
	}

	/**
	 * This method initializes jPanel10
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel10() throws ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel10() - start");
		}

		if(jPanel10 == null) {
			jPanel10 = new javax.swing.JPanel();
			jPanel10.add(getJSplitPane1(), null);
			jPanel10.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel10() - end");
		}
		return jPanel10;
	}
	/**
	 * This method initializes jSplitPane1
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private javax.swing.JSplitPane getJSplitPane1() throws ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("getJSplitPane1() - start");
		}

		if(jSplitPane1 == null) {
			jSplitPane1 = new javax.swing.JSplitPane();
			jSplitPane1.setLeftComponent(getJPanel11());
			jSplitPane1.setRightComponent(getJPanel13());
			jSplitPane1.setPreferredSize(new java.awt.Dimension(750,550));
			jSplitPane1.setBackground(new java.awt.Color(226,226,222));
			jSplitPane1.setContinuousLayout(false);
			jSplitPane1.setDividerLocation(120);
			jSplitPane1.setAutoscrolls(true);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJSplitPane1() - end");
		}
		return jSplitPane1;
	}
	/**
	 * This method initializes jPanel11
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel11() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel11() - start");
		}

		if(jPanel11 == null) {
			jPanel11 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
			layFlowLayout2.setHgap(0);
			layFlowLayout2.setVgap(0);
			jPanel11.setLayout(layFlowLayout2);
			jPanel11.add(getJButton5(), null);
			jPanel11.add(getJButton6(), null);
			jPanel11.add(getJButton7(), null);
			jPanel11.setPreferredSize(new java.awt.Dimension(90,120));
			jPanel11.setAutoscrolls(true);
			jPanel11.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel11() - end");
		}
		return jPanel11;
	}
	/**
	 * This method initializes jPanel13
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel13() throws ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel13() - start");
		}

		if(jPanel13 == null) {
			jPanel13 = new javax.swing.JPanel();
			jPanel13.add(getJPanel14(), null);
			jPanel13.setPreferredSize(new java.awt.Dimension(660,300));
			jPanel13.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel13() - end");
		}
		return jPanel13;
	}
	/**
	 * This method initializes jButton6
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton6() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton6() - start");
		}

		if(jButton6 == null) {
			jButton6 = new JHighlightButton();
			jButton6.setText("Perfiles");
			jButton6.setActionCommand("opcPerfiles");
			jButton6.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix48x48/businessmen.png")));
			jButton6.setBackground(new java.awt.Color(226,226,222));
			jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			jButton6.setPreferredSize(new java.awt.Dimension(90,90));
			jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			jButton6.setBorderPainted(false);
			jButton6.addActionListener(this);
			jButton6.addKeyListener(this);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton6() - end");
		}
		return jButton6;
	}
	/**
	 * This method initializes jButton7
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton7() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton7() - start");
		}

		if(jButton7 == null) {
			jButton7 = new JHighlightButton();
			jButton7.setText("Salir");
			jButton7.setActionCommand("opcSalir");
			jButton7.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix48x48/undo.png")));
			jButton7.setBackground(new java.awt.Color(226,226,222));
			jButton7.setPreferredSize(new java.awt.Dimension(90,90));
			jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			jButton7.setBorderPainted(false);
			jButton7.addActionListener(this);
			jButton7.addKeyListener(this);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton7() - end");
		}
		return jButton7;
	}
	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton5() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton5() - start");
		}

		if(jButton5 == null) {
			jButton5 = new JHighlightButton();
			jButton5.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix48x48/user1_refresh.png")));
			jButton5.setText("Usuarios");
			jButton5.setActionCommand("opcUsuarios");
			jButton5.setBackground(new java.awt.Color(226,226,222));
			jButton5.setPreferredSize(new java.awt.Dimension(90,90));
			jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			jButton5.setBorderPainted(false);
			jButton5.addActionListener(this);
			jButton5.addKeyListener(this);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton5() - end");
		}
		return jButton5;
	}
	/**
	 * This method initializes jPanel14
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel14() throws ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel14() - start");
		}

		if(jPanel14 == null) {
			jPanel14 = new javax.swing.JPanel();
			jPanel14.setLayout(new java.awt.BorderLayout());
			jPanel14.add(getJPanel12(), java.awt.BorderLayout.NORTH);
			jPanel14.add(getJTabbedPane1(), BorderLayout.CENTER);
			jPanel14.add(getJPanel(), BorderLayout.SOUTH);
			jPanel14.setPreferredSize(new java.awt.Dimension(610,500));
			jPanel14.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel14() - end");
		}
		return jPanel14;
	}
	/**
	 * This method initializes jPanel12
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel12() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel12() - start");
		}

		if(jPanel12 == null) {
			jPanel12 = new javax.swing.JPanel();
			jPanel12.setLayout(new java.awt.BorderLayout());
			jPanel12.add(getJLabel(), java.awt.BorderLayout.NORTH);
			jPanel12.setBackground(new java.awt.Color(69,107,127));
			jPanel12.setPreferredSize(new java.awt.Dimension(250,48));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel12() - end");
		}
		return jPanel12;
	}
	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Iterator'
	* Fecha: agosto 2011
	*/
	private javax.swing.JComboBox getJComboBox1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJComboBox1() - start");
		}

		if(jComboBox1 == null) {
			jComboBox1 = new javax.swing.JComboBox();
			jComboBox1.setEditable(true);
			jComboBox1.setPreferredSize(new java.awt.Dimension(150,20));
			Iterator<?> perfiles;
			try {
				jComboBox1.addItem("");
				perfiles = MaquinaDeEstado.cargarRegistros(new Perfil(), false).iterator();
				while (perfiles.hasNext()){
					jComboBox1.addItem(((Perfil)perfiles.next()).getDescripcion());
				}
			} catch (ExcepcionCr e) {
				logger.error("getJComboBox1()", e);

				MensajesVentanas.mensajeError(e.getMensaje());
			} catch (ConexionExcepcion e) {
				logger.error("getJComboBox1()", e);

				MensajesVentanas.mensajeError(e.getMensaje());
			}
			jComboBox1.addActionListener(this);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJComboBox1() - end");
		}
		return jComboBox1;
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
			java.awt.FlowLayout layFlowLayout4 = new java.awt.FlowLayout();
			layFlowLayout4.setAlignment(java.awt.FlowLayout.RIGHT);
			jPanel1.setLayout(layFlowLayout4);
			jPanel1.add(getJScrollPane1(), null);
			jPanel1.setBackground(new java.awt.Color(226,226,222));
			jPanel1.setPreferredSize(new java.awt.Dimension(500,300));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel1() - end");
		}
		return jPanel1;
	}
	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private javax.swing.JTable getJTable() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTable() - start");
		}

		if(jTable == null) {
			jTable = new javax.swing.JTable(modeloTablaFunciones);
			jTable.getTableHeader().setReorderingAllowed(false) ;
			jTable.getColumnModel().getColumn(0).setPreferredWidth(40);
			jTable.getColumnModel().getColumn(1).setPreferredWidth(260);
			jTable.getColumnModel().getColumn(2).setPreferredWidth(35);
			jTable.getColumnModel().getColumn(3).setPreferredWidth(35);
			jTable.setLocale(new java.util.Locale("es", "VE", ""));
			jTable.setRowHeight(jTable.getRowHeight()+3);
			jTable.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jTable.setBackground(new java.awt.Color(242,242,238));
			jTable.setName("opcFunciones");
			jTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			jTable.setShowGrid(false);
			jTable.setAutoscrolls(true);
			jTable.setCellSelectionEnabled(true);
			jTable.setColumnSelectionAllowed(true);
			jTable.setEnabled(true);
			jTable.setRowSelectionAllowed(true);
			jTable.setShowHorizontalLines(true);
			jTable.setShowVerticalLines(true);
			jTable.addKeyListener(this);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTable() - end");
		}
		return jTable;
	}
	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private javax.swing.JScrollPane getJScrollPane1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJScrollPane1() - start");
		}

		if(jScrollPane1 == null) {
			jScrollPane1 = new javax.swing.JScrollPane();
			jScrollPane1.setViewportView(getJTable());
			jScrollPane1.setPreferredSize(new java.awt.Dimension(490,200));
			jScrollPane1.setAutoscrolls(true);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJScrollPane1() - end");
		}
		return jScrollPane1;
	}
	/**
	 * This method initializes jLabel5
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel5() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel5() - start");
		}

		if(jLabel5 == null) {
			jLabel5 = new javax.swing.JLabel();
			jLabel5.setText(" Funciones Asignadas: ");
			jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel5() - end");
		}
		return jLabel5;
	}
	/**
	 * This method initializes jLabel6
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel6() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel6() - start");
		}

		if(jLabel6 == null) {
			jLabel6 = new javax.swing.JLabel();
			jLabel6.setText("0/0");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel6() - end");
		}
		return jLabel6;
	}
	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private javax.swing.JTable getJTable1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTable1() - start");
		}

		if(jTable1 == null) {
			jTable1 = new javax.swing.JTable(modeloTablaUsuariosSistema);
			jTable1.getTableHeader().setReorderingAllowed(false) ;
			jTable1.getColumnModel().getColumn(0).setPreferredWidth(80);
			jTable1.getColumnModel().getColumn(1).setPreferredWidth(260);
			jTable1.setLocale(new java.util.Locale("es", "VE", ""));
			jTable1.setRowHeight(jTable1.getRowHeight()+3);
			jTable1.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jTable1.setBackground(new java.awt.Color(242,242,238));
			jTable1.setName("usersMiembros");
			jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			jTable1.setShowGrid(false);
			jTable1.setAutoscrolls(true);
			jTable1.setCellSelectionEnabled(false);
			jTable1.setColumnSelectionAllowed(false);
			jTable1.setEnabled(true);
			jTable1.setRowSelectionAllowed(true);
			jTable1.setShowHorizontalLines(true);
			jTable1.setShowVerticalLines(true);
			jTable1.setForeground(java.awt.Color.black);
			jTable1.addKeyListener(this);
			jTable1.addMouseListener(this);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTable1() - end");
		}
		return jTable1;
	}
	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private javax.swing.JScrollPane getJScrollPane() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJScrollPane() - start");
		}

		if(jScrollPane == null) {
			jScrollPane = new javax.swing.JScrollPane();
			javax.swing.border.TitledBorder ivjTitledBorder2 = javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.RAISED), "Usuarios del Sistema", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null);
			ivjTitledBorder2.setTitle("Usuarios del Sistema");
			jScrollPane.setViewportView(getJTable1());
			jScrollPane.setBorder(ivjTitledBorder2);
			//jScrollPane.setPreferredSize(new java.awt.Dimension(500,180));
			jScrollPane.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJScrollPane() - end");
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jTable2
	 * 
	 * @return javax.swing.JTable
	 */
	private javax.swing.JTable getJTable2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTable2() - start");
		}

		if(jTable2 == null) {
			jTable2 = new javax.swing.JTable(modeloTablaUsuariosPerfil);
			jTable2.getTableHeader().setReorderingAllowed(false) ;
			jTable2.getColumnModel().getColumn(0).setPreferredWidth(80);
			jTable2.getColumnModel().getColumn(1).setPreferredWidth(260);
			jTable2.setLocale(new java.util.Locale("es", "VE", ""));
			jTable2.setRowHeight(jTable2.getRowHeight()+3);
			jTable2.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jTable2.setBackground(new java.awt.Color(242,242,238));
			jTable2.setName("usersSistema");
			jTable2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			jTable2.setShowGrid(false);
			jTable2.setAutoscrolls(true);
			jTable2.setCellSelectionEnabled(false);
			jTable2.setColumnSelectionAllowed(false);
			jTable2.setEnabled(true);
			jTable2.setRowSelectionAllowed(true);
			jTable2.setShowHorizontalLines(true);
			jTable2.setShowVerticalLines(true);
			jTable2.addKeyListener(this);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTable2() - end");
		}
		return jTable2;
	}
	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private javax.swing.JScrollPane getJScrollPane2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJScrollPane2() - start");
		}

		if(jScrollPane2 == null) {
			jScrollPane2 = new javax.swing.JScrollPane();
			javax.swing.border.TitledBorder ivjTitledBorder1 = javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.RAISED), "Usuarios del Sistema", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null);
			ivjTitledBorder1.setTitle("Miembros del Perfil");
			jScrollPane2.setViewportView(getJTable2());
			jScrollPane2.setBorder(ivjTitledBorder1);
			//jScrollPane2.setPreferredSize(new java.awt.Dimension(500,180));
			jScrollPane2.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJScrollPane2() - end");
		}
		return jScrollPane2;
	}

	/**
	 * Método mouseClicked
	 *
	 * @param e
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	public void mouseReleased(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - start");
		}

		try{
			if(this.panelActivo.equals(panel1)){
				if (e.getSource() instanceof JButton){
					JButton	boton = (JButton)e.getSource();
					boton.doClick();
				} else if (e.getSource().equals(this.getJCheckBox())){
					if(!this.getJCheckBox2().isSelected() && this.getJCheckBox().isSelected()) {
						this.getJCheckBox2().setSelected(true);
					}
				} else if (e.getSource().equals(this.getJCheckBox2())){
					if(this.getJCheckBox().isSelected() && !this.getJCheckBox2().isSelected()) {
						this.getJCheckBox().setSelected(false);		
					}
				} else if ((e.getSource() instanceof JSlider)||(e.getSource() instanceof JComboBox)) {
					this.getFocusOwner().transferFocus();
					}
			}
				
			//Pestaña de Opciones de Perfiles
			if(this.panelActivo.equals(panel2)){
				if (e.getSource() instanceof JTable) {
					if (e.getSource().equals(getJTable1())) {
						ArrayList<String> nuevosMiembros = new ArrayList<String>();
						Usuario xUsuario = new Usuario();
						xUsuario.setNumFicha(getJTable1().getValueAt(getJTable1().getSelectedRow(), 0).toString());
						nuevosMiembros.add(xUsuario.getNumFicha());
						if(!(perfilActual.getCodPerfil().equals("0"))){
							perfilActual.agregarMiembros(nuevosMiembros);
							modeloTablaUsuariosSistema.llenarTabla(modeloTablaUsuariosPerfil.llenarTabla(perfilActual));
						}	
					}
				} else if (e.getSource() instanceof JButton){
					JButton	boton = (JButton)e.getSource();
					boton.doClick();
				} else if ((e.getSource() instanceof JSlider)||(e.getSource() instanceof JComboBox)) {
					this.getFocusOwner().transferFocus();
				}
			}
		} catch (ExcepcionCr ex) {
			logger.error("mouseClicked(MouseEvent)", ex);

			MensajesVentanas.mensajeError(ex.getMensaje());
		} catch (Exception ex) {
			logger.error("mouseClicked(MouseEvent)", ex);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - end");
		}
	}

	/**
	 * Método mouseEntered
	 *
	 * @param e
	 */
	public void mouseEntered(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseEntered(MouseEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("mouseEntered(MouseEvent) - end");
		}
	}

	/**
	 * Método mouseExited
	 *
	 * @param e
	 */
	public void mouseExited(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseExited(MouseEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("mouseExited(MouseEvent) - end");
		}
	}

	/**
	 * Método mousePressed
	 *
	 * @param e
	 */
	public void mousePressed(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mousePressed(MouseEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("mousePressed(MouseEvent) - end");
		}
	}

	/**
	 * Método mouseReleased
	 *
	 * @param e
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
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel2() {
		if(jPanel2 == null) {
			jPanel2 = new javax.swing.JPanel();
			java.awt.GridLayout layGridLayout11 = new java.awt.GridLayout();
			layGridLayout11.setRows(4);
			layGridLayout11.setColumns(1);
			jPanel2.setLayout(layGridLayout11);
			jPanel2.add(getJButton4(), null);
			jPanel2.add(getJLabel7(), null);
			jPanel2.add(getJLabel8(), null);
			jPanel2.add(getJButton8(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(95,90));
			jPanel2.setBackground(new java.awt.Color(226,226,222));
		}
		return jPanel2;
	}
	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton4() {
		if(jButton4 == null) {
			jButton4 = new JHighlightButton();
			jButton4.setText("Cambiar");
			jButton4.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 9));
			jButton4.setBackground(new java.awt.Color(226,226,222));
			jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/lock_new.png")));
			jButton4.setName("setCodBarra");
			jButton4.addActionListener(this);
			jButton4.addKeyListener(this);
		}
		return jButton4;
	}
	/**
	 * This method initializes jButton8
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton8() {
		if(jButton8 == null) {
			jButton8 = new JHighlightButton();
			jButton8.setText("Cambiar");
			jButton8.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 9));
			jButton8.setBackground(new java.awt.Color(226,226,222));
			jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/lock_new.png")));
			jButton8.setName("setClave");
			jButton8.addActionListener(this);
			jButton8.addKeyListener(this);
		}
		return jButton8;
	}
	/**
	 * This method initializes jLabel7
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel7() {
		if(jLabel7 == null) {
			jLabel7 = new javax.swing.JLabel();
			jLabel7.setText("");
		}
		return jLabel7;
	}
	/**
	 * This method initializes jLabel8
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel8() {
		if(jLabel8 == null) {
			jLabel8 = new javax.swing.JLabel();
			jLabel8.setText("");
		}
		return jLabel8;
	}

	/**
	 * Método changedUpdate
	 *
	 * @param e
	 */
	public void changedUpdate(DocumentEvent e) {
	}

	/**
	 * Método insertUpdate
	 *
	 * @param e
	 */
	public void insertUpdate(DocumentEvent e) {
		if (this.panelActivo.equals(panel1)) {
			limpiarValores();
			getTxtCodigo().setText(CR.inputEscaner.getText().trim());
			getTxtCodigo().requestFocus();
			/*String xCodigo = new String(txtCodigo.getPassword());
			try{
				xUsuario = xUsuario.cargarDatos(xCodigo);
			} catch (ExcepcionCr ex) {
				logger.error("keyPressed(KeyEvent)", ex);

				xUsuario = null;
			}
			if (xUsuario != null) cargarValores();
			else { 
				try{
					String identificador = Control.codigoValido(xCodigo).get(1).toString(); 
					xUsuario = new Usuario();
					xUsuario.setDatosPersonales(new Colaborador().obtenerDatos(identificador));
					xUsuario.setNumFicha(xUsuario.getDatosPersonales().getNumFicha());
					xUsuario.setNombre(xUsuario.getDatosPersonales().getNombre());
					cargarValores();
				} catch (ExcepcionCr ex) {
					logger.error("keyPressed(KeyEvent)", ex);

					MensajesVentanas.mensajeError(ex.getMensaje());
					limpiarValores();
				}
			}*/
		}
	}

	/**
	 * Método removeUpdate
	 *
	 * @param e
	 */
	public void removeUpdate(DocumentEvent e) {
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
/*		int width = (int)this.getSize().getWidth();
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
		
		this.setBounds(x, y, width, height);*/
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

	/* (no Javadoc)
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	public void focusGained(FocusEvent e) {

	}

	/* (no Javadoc)
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	public void focusLost(FocusEvent e) {
		if (e.getSource() == getTxtCodigo() && (!getTxtCodigo().getText().equals(""))) {
				//Pestaña de Opciones de Usuario
				if(this.panelActivo.equals(panel1)){
					if /*((*/(e.getSource() instanceof JPasswordField) /*|| (e.getSource() instanceof JTextField)))*/ {
						//this.getFocusOwner().transferFocus();
						if ((e.getSource() instanceof JPasswordField)){
							((JPasswordField)e.getSource()).getName().equals("codigoBarra");
							String xCodigo = new String(txtCodigo.getPassword());
							try{
								xUsuario = xUsuario.cargarDatos(xCodigo);
							} catch (ExcepcionCr ex) {
								logger.error("keyPressed(KeyEvent)", ex);

								xUsuario = null;
							}
							if (xUsuario != null) cargarValores();
							else { 
								try{
									String identificador = Control.codigoValido(xCodigo).get(1).toString(); 
									xUsuario = new Usuario();
									xUsuario.setDatosPersonales(new Colaborador().obtenerDatos(identificador));
									xUsuario.setNumFicha(xUsuario.getDatosPersonales().getNumFicha());
									xUsuario.setNombre(xUsuario.getDatosPersonales().getNombre());
									cargarValores();
								} catch (ExcepcionCr ex) {
									logger.error("keyPressed(KeyEvent)", ex);

									/*MensajesVentanas.mensajeError(ex.getMensaje());
									limpiarValores();*/
								}
							}
						}
						else if (((JTextField)e.getSource()).getName().equals("numFicha")) {
							if (new String(txtCodigo.getPassword()).length() < Control.getLONGITUD_CODIGO()){
								try {
									xUsuario = xUsuario.cargarDatos(jTextField1.getText());
								} catch (ExcepcionCr ex) {
									logger.error("keyPressed(KeyEvent)", ex);
						
									MensajesVentanas.mensajeError(ex.getMensaje());
								} catch (Exception ex) {
									logger.error("keyPressed(KeyEvent)", ex);
								}
								cargarValores();
							}
						}
					} else if (e.getSource().equals(this.getJCheckBox())){
						if(!this.getJCheckBox2().isSelected() && !this.getJCheckBox().isSelected()) {
							this.getJCheckBox2().setSelected(true);
							//this.getJCheckBox().doClick();
						}
					} else if (e.getSource().equals(this.getJCheckBox2())){
						if(this.getJCheckBox().isSelected() && this.getJCheckBox2().isSelected()) {
							this.getJCheckBox().setSelected(false);	
						}
					} else if (e.getSource() instanceof JButton){
						JButton	boton = (JButton)e.getSource();
						boton.doClick();
					} else if ((e.getSource() instanceof JSlider)||(e.getSource() instanceof JComboBox)) {
						this.getFocusOwner().transferFocus();
						}
					}
			
		}
		
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"