/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
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
package com.becoblohm.cr.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstado;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.utiles.Control;
import com.becoblohm.cr.utiles.MensajesVentanas;

/**
 * Descripción:
 * 
 */
public class LoginDeUsuario extends JDialog implements ComponentListener, ActionListener, KeyListener, DocumentListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LoginDeUsuario.class);

	public Vector<Object> valores = new Vector<Object>();
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JButton jButton2 = null;
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
		super(MensajesVentanas.ventanaActiva);
		initialize();
		if(CR.inputEscaner == null)
			CR.abrirConexionEscaner();
		CR.inputEscaner.getDocument().addDocumentListener(this);
	}
	
	public LoginDeUsuario(boolean nada) {
		super(InitCR.verificador, true);
		initialize();
		if(CR.inputEscaner == null)
			CR.abrirConexionEscaner();
		CR.inputEscaner.getDocument().addDocumentListener(this);
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

		java.awt.GridLayout layGridLayout1 = new java.awt.GridLayout();
		layGridLayout1.setRows(4);
		this.getContentPane().setLayout(layGridLayout1);
		this.getContentPane().add(getJPanel(), null);
		this.getContentPane().add(getJPanel3(), null);
		this.getContentPane().add(getJPanel1(), null);
		this.getContentPane().add(getJPanel2(), null);
		this.setSize(360, 228);
		this.setTitle("Acceso al Sistema");
		this.setBackground(new java.awt.Color(226,226,222));
		this.setModal(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addComponentListener(this);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() { 
			public void windowOpened(java.awt.event.WindowEvent e) {
				if (logger.isDebugEnabled()) {
					logger
							.debug("windowOpened(java.awt.event.WindowEvent) - start");
				}
    
				jLoginField.requestFocus();

				if (logger.isDebugEnabled()) {
					logger
							.debug("windowOpened(java.awt.event.WindowEvent) - end");
				}
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("initialize() - end");
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

		if(jPanel == null) {
			jPanel = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout8 = new java.awt.FlowLayout();
			layFlowLayout8.setHgap(5);
			layFlowLayout8.setVgap(4);
			layFlowLayout8.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel.setLayout(layFlowLayout8);
			jPanel.add(getJLabel1(), null);
			jPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,4,0,0));
			jPanel.setBackground(new java.awt.Color(69,107,127));
			jPanel.setPreferredSize(new java.awt.Dimension(325,48));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel() - end");
		}
		return jPanel;
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
			jLabel1.setText("Area Protegida: Identifiquese ");
			jLabel1.setForeground(java.awt.Color.white);
			jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/trafficlight_red.png")));
			jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jLabel1.setPreferredSize(new java.awt.Dimension(321,48));
			jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jLabel1.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel1() - end");
		}
		return jLabel1;
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

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel1() - end");
		}
		return jPanel1;
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

		if(jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setText(" Identificador de Usuario:");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel() - end");
		}
		return jLabel;
	}
	/**
	 * This method initializes jLoginField
	 * 
	 * @return javax.swing.JPasswordField
	 */
	private javax.swing.JPasswordField getJLoginField() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLoginField() - start");
		}

		if(jLoginField == null) {
			jLoginField = new javax.swing.JPasswordField();
			jLoginField.setName("Login");
			jLoginField.addKeyListener(this);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLoginField() - end");
		}
		return jLoginField;
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

		if(jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setText(" Contraseña: ");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel2() - end");
		}
		return jLabel2;
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
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setAlignment(java.awt.FlowLayout.RIGHT);
			layFlowLayout1.setVgap(10);
			jPanel2.setLayout(layFlowLayout1);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(getJButton2(), null);
			jPanel2.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel2() - end");
		}
		return jPanel2;
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

		if(jButton == null) {
			jButton = new JHighlightButton();
			jButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
			jButton.setText("Aceptar");
			jButton.addActionListener(this);
			jButton.addKeyListener(this);
			jButton.setBackground(new java.awt.Color(226,226,222));
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

		if(jButton1 == null) {
			jButton1 = new JHighlightButton();
			jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
			jButton1.setText("Cancelar");
			jButton1.addActionListener(this);
			jButton1.addKeyListener(this);
			jButton1.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton1() - end");
		}
		return jButton1;
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

		if(jButton2 == null) {
			jButton2 = new JHighlightButton();
			jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/monitor_sleep.png")));
			jButton2.setText("Apagar");
			jButton2.addActionListener(this);
			jButton2.addKeyListener(this);
			if(CR.me != null){
				if((CR.meVenta.getVenta() != null) || (!Sesion.getCaja().getEstado().equals(Sesion.CONSULTA))) 
					jButton2.setVisible(false);
			}	
			else if(Sesion.getCaja().getEstado().equals(Sesion.INICIALIZANDO))
				jButton2.setVisible(true);
			else if(!Sesion.getCaja().getEstado().equals(Sesion.CONSULTA))
				jButton2.setVisible(false);
			jButton2.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton2() - end");
		}
		return jButton2;
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

		if(jPasswordField == null) {
			jPasswordField = new javax.swing.JPasswordField();
			jPasswordField.setName("Clave");
			jPasswordField.addKeyListener(this);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPasswordField() - end");
		}
		return jPasswordField;
	}
	
	/**
	 * Método actionPerformed
	 *
	 * @param e
	 */
	public void actionPerformed(ActionEvent e){
		if (logger.isDebugEnabled()) {
			logger.debug("actionPerformed(ActionEvent) - start");
		}

		JButton btnComando = (JButton)e.getSource();
		if (btnComando.getText().equals("Aceptar"))
		{
			if (cargarDatos()) {
				if(CR.inputEscaner != null)
					CR.inputEscaner.getDocument().removeDocumentListener(this);
				this.dispose();
			} 
		}
		else if (btnComando.getText().equals("Cancelar")){
			if(CR.inputEscaner != null)
				CR.inputEscaner.getDocument().removeDocumentListener(this);
			this.dispose();
		}
		else if (btnComando.getText().equals("Apagar")){
			if(Sesion.getCaja().getEstado().equals(Sesion.CONSULTA) || Sesion.getCaja().getEstado().equals(Sesion.INICIALIZANDO)){
				if(CR.inputEscaner != null){
					CR.inputEscaner.getDocument().removeDocumentListener(this);
					try {
						CR.cerrarConexiones();
					} catch (NullPointerException ex) {
						logger.error("actionPerformed(ActionEvent)", ex);
					}
				}
				MaquinaDeEstado.apagarSistema();
			}	
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
	public void keyPressed(KeyEvent e){
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}

		try{
			if (e.getSource() instanceof JPasswordField) {
				JPasswordField campo = (JPasswordField)e.getSource();
				if ((campo.getName().equals("Login"))&&(e.getKeyCode() == KeyEvent.VK_ENTER)){
					this.getJPasswordField().requestFocus();
				}
				else if ((campo.getName().equals("Clave"))&&(e.getKeyCode() == KeyEvent.VK_ENTER)){
					this.jButton.doClick();
				}
			}
		} catch(Exception ex){
			logger.error("keyPressed(KeyEvent)", ex);
		}

		try{
			if (e.getSource() instanceof JButton) {
				JButton boton = (JButton)e.getSource();
				if ((boton.getText().equals("Aceptar"))&&(e.getKeyCode() == KeyEvent.VK_ENTER)){
					if (cargarDatos()) {
						CR.inputEscaner.getDocument().removeDocumentListener(this);
						this.dispose();
					} 
				}
				else if ((boton.getText().equals("Cancelar"))&&(e.getKeyCode() == KeyEvent.VK_ENTER)){
					if(CR.inputEscaner != null)
						CR.inputEscaner.getDocument().removeDocumentListener(this);
					this.dispose();
				}
				else if ((boton.getText().equals("Apagar"))&&(e.getKeyCode() == KeyEvent.VK_ENTER)){
					if(CR.inputEscaner != null){
						CR.inputEscaner.getDocument().removeDocumentListener(this);
						try {
							CR.cerrarConexiones();
						} catch (NullPointerException ex) {
							logger.error("keyPressed(KeyEvent)", ex);
						}
					}
					MaquinaDeEstado.apagarSistema();
				}
			}
		} catch(Exception ex){
			logger.error("keyPressed(KeyEvent)", ex);
}
		
		try{
			 if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				this.dispose();
			 }
		} catch(Exception ex){
			logger.error("keyPressed(KeyEvent)", ex);
}

		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - end");
		}
	}

	public void keyReleased(KeyEvent e){
		if (logger.isDebugEnabled()) {
			logger.debug("keyReleased(KeyEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("keyReleased(KeyEvent) - end");
		}
	}

	public void keyTyped(KeyEvent e){
		if (logger.isDebugEnabled()) {
			logger.debug("keyTyped(KeyEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("keyTyped(KeyEvent) - end");
		}
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
	public Vector<Object> getValores() {
		if (logger.isDebugEnabled()) {
			logger.debug("getValores() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getValores() - end");
		}
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
		if (logger.isDebugEnabled()) {
			logger.debug("cargarDatos() - start");
		}

		valores = new Vector<Object>();
		String login = new String(this.jLoginField.getPassword()).trim();
		String clave = new String(this.jPasswordField.getPassword());
		if ((login != null)&&(clave != null)&&(login != "")&&(clave != "")){
			valores.addElement(login);
			valores.addElement(clave);
			Boolean entradaManual = (login.length() == Control.getLONGITUD_CODIGO()) || (login.length() == Control.getLONGITUD_CODIGO() + 1) ? Boolean.FALSE:Boolean.TRUE;
			valores.addElement(entradaManual);
		}
		else {
			if (logger.isDebugEnabled()) {
				logger.debug("cargarDatos() - end");
			}
			return false;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cargarDatos() - end");
		}
		return true;
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
			java.awt.GridLayout layGridLayout6 = new java.awt.GridLayout();
			layGridLayout6.setRows(1);
			layGridLayout6.setVgap(10);
			jPanel3.setLayout(layGridLayout6);
			jPanel3.add(getJLabel3(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(223,30));
			jPanel3.setBackground(new java.awt.Color(226,226,222));
			jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Función ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), java.awt.Color.black));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel3() - end");
		}
		return jPanel3;
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

		String funcion = " ";
		try {
			funcion = MaquinaDeEstado.buscarFuncion();
		} catch (FuncionExcepcion e) {
			logger.error("getJLabel3()", e);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("getJLabel3()", e);
		} catch (ConexionExcepcion e) {
			logger.error("getJLabel3()", e);
		} catch (SQLException e) {
			logger.error("getJLabel3()", e);
}
		
		if(jLabel3 == null) {
			jLabel3 = new javax.swing.JLabel();
			jLabel3.setText(funcion);
			jLabel3.setPreferredSize(new java.awt.Dimension(219,30));
			jLabel3.setForeground(java.awt.Color.black);
			jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			jLabel3.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel3() - end");
		}
		return jLabel3;
	}
	
	/**
	 * Método changedUpdate
	 *
	 * @param e
	 */
	public void changedUpdate(DocumentEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("changedUpdate(DocumentEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("changedUpdate(DocumentEvent) - end");
		}
	}
	/**
	 * Método insertUpdate
	 *
	 * @param e
	 */
	public void insertUpdate(DocumentEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("insertUpdate(DocumentEvent) - start");
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (logger.isDebugEnabled()) {
					logger.debug("run() - start");
				}

				getJLoginField().setText(CR.inputEscaner.getText());
				getJPasswordField().requestFocus();

				if (logger.isDebugEnabled()) {
					logger.debug("run() - end");
				}
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("insertUpdate(DocumentEvent) - end");
		}
	}
	/**
	 * Método removeUpdate
	 *
	 * @param e
	 */
	public void removeUpdate(DocumentEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("removeUpdate(DocumentEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("removeUpdate(DocumentEvent) - end");
		}
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	@SuppressWarnings("unused")
	private javax.swing.JPanel getJContentPane() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJContentPane() - start");
		}

		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJPanel4(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanel2(), java.awt.BorderLayout.SOUTH);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJContentPane() - end");
		}
		return jContentPane;
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
			jPanel4 = new javax.swing.JPanel();
			java.awt.GridLayout layGridLayout2 = new java.awt.GridLayout();
			layGridLayout2.setRows(2);
			layGridLayout2.setColumns(1);
			jPanel4.setLayout(layGridLayout2);
			jPanel4.add(getJPanel(), null);
			jPanel4.add(getJPanel3(), null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel4() - end");
		}
		return jPanel4;
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
}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"