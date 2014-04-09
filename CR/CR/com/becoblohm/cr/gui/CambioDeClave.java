/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : CambioDeClave.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 13/02/2004 02:44 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.0.1
 * Fecha       : 01/07/2004 10:56 AM
 * Analista    : Programador3 - Alexis Guédez López 
 * Descripción : - Deshabilitado el cierre de la ventana a través de los comandos 
 * 				de ventana.
 * 				 - Agregado el indicador para deshabilitar redimensionar la ventana.
 * =============================================================================
 */
package com.becoblohm.cr.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.utiles.MensajesVentanas;
/**
 * Descripción:
 * 
 */
public class CambioDeClave extends JDialog 
	implements ComponentListener, ActionListener, KeyListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CambioDeClave.class);
	private boolean inicioSesion = false;

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
	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel4() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel4() - start");
		}

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

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel4() - end");
		}
		return jPanel4;
	}
 	public static void main(String[] args) {
		if (logger.isDebugEnabled()) {
			logger.debug("main(String[]) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("main(String[]) - end");
		}
	}
	/**
	 * This is the default constructor
	 */
	public CambioDeClave(boolean inicio) {
		super(InitCR.verificador);
		initialize();
		this.inicioSesion = inicio;
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (logger.isDebugEnabled()) {
					logger.debug("windowClosing(WindowEvent) - start");
				}

				cargarDatos();

				if (logger.isDebugEnabled()) {
					logger.debug("windowClosing(WindowEvent) - end");
				}
			}
		});
	}
	/**
	 * This is the default constructor
	 */
	public CambioDeClave() {
		super(MensajesVentanas.ventanaActiva);
		initialize();
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (logger.isDebugEnabled()) {
					logger.debug("windowClosing(WindowEvent) - start");
				}

				cargarDatos();

				if (logger.isDebugEnabled()) {
					logger.debug("windowClosing(WindowEvent) - end");
				}
			}
		});
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
		layGridLayout1.setRows(3);
		this.setContentPane(getJPanel3());
		this.setSize(370, 187);
		this.setTitle("Cambiar Contraseña");
		this.setBackground(new java.awt.Color(226,226,222));
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		this.setModal(true);
		this.addComponentListener(this);

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
			java.awt.FlowLayout flowLayout11 = new FlowLayout();
			flowLayout11.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout11.setVgap(5);
			jPanel.setLayout(flowLayout11);
			jPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,4,0,0));
			jPanel.setBackground(new java.awt.Color(69,107,127));
			jPanel.setPreferredSize(new java.awt.Dimension(350,40));
			jPanel.add(getJLabel1(), null);
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
			jLabel1.setText("Cambiar Contraseña");
			jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel1.setForeground(java.awt.Color.white);
			jLabel1.setForeground(java.awt.Color.white);
			jLabel1.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/lock_preferences.png")));
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
			layGridLayout4.setRows(3);
			jPanel1.setLayout(layGridLayout4);
			jPanel1.add(getJLabel(), null);
			jPanel1.add(getJClaveActual(), null);
			jPanel1.add(getJLabel2(), null);
			jPanel1.add(getJPasswordField(), null);
			jPanel1.add(getJLabel3(), null);
			jPanel1.add(getJPasswordField2(), null);
			jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(4,4,4,4));
			jPanel1.setBackground(new java.awt.Color(242,242,238));
			jPanel1.setPreferredSize(new java.awt.Dimension(330,65));
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
			jLabel.setText(" Contraseña anterior: ");
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
	private javax.swing.JPasswordField getJClaveActual() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJClaveActual() - start");
		}

		if(jClaveActual == null) {
			jClaveActual = new javax.swing.JPasswordField();
			jClaveActual.setName("ClaveActual");
			jClaveActual.addKeyListener(this);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJClaveActual() - end");
		}
		return jClaveActual;
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
			jLabel2.setText(" Contraseña nueva: ");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel2() - end");
		}
		return jLabel2;
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
			jPasswordField.setName("ClaveNueva");
			jPasswordField.addKeyListener(this);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPasswordField() - end");
		}
		return jPasswordField;
	}
	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel3() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel3() - start");
		}

		if(jLabel3 == null) {
			jLabel3 = new javax.swing.JLabel();
			jLabel3.setText(" Confirmar Contraseña: ");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel3() - end");
		}
		return jLabel3;
	}
	/**
	 * This method initializes jPasswordField
	 * 
	 * @return javax.swing.JPasswordField
	 */
	private javax.swing.JPasswordField getJPasswordField2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPasswordField2() - start");
		}

		if(jPasswordField2== null) {
			jPasswordField2 = new javax.swing.JPasswordField();
			jPasswordField2.setName("ClaveNueva2");
			jPasswordField2.addKeyListener(this);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPasswordField2() - end");
		}
		return jPasswordField2;
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
			jPanel2.setLayout(layFlowLayout1);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.setBackground(new java.awt.Color(226,226,222));
			jPanel2.setComponentOrientation(java.awt.ComponentOrientation.UNKNOWN);
			jPanel2.setPreferredSize(new java.awt.Dimension(350,35));
			layFlowLayout1.setHgap(0);
			layFlowLayout1.setVgap(3);
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
			jButton.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
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
			jButton1.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
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
	
	public void actionPerformed(ActionEvent e){
		if (logger.isDebugEnabled()) {
			logger.debug("actionPerformed(ActionEvent) - start");
		}

		JButton btnComando = (JButton)e.getSource();
		if (btnComando.getText().equals("Aceptar"))
		{
			if (cargarDatos()) {
				//this.dispose();
				try {
					CR.me.cambiarClave(valores.get(0).toString(), valores.get(1).toString());
					MensajesVentanas.aviso("Contraseña cambiada satisfactoriamente.");
					this.dispose();
				} catch (UsuarioExcepcion e1) {
					logger.error("actionPerformed(ActionEvent)", e1);
					MensajesVentanas.mensajeError(e1.getMensaje());
					if (inicioSesion) {
						if (Sesion.getUsuarioActivo().getCodPerfil().equalsIgnoreCase(Sesion.PERFIL_SUPERVISOR)) {
							//MensajesVentanas.mensajeError("Debe Cambiar clave de acceso para ingresar al Sistema\nEsta operación no puede ser cancelada");
							//Colocar el foco en el campo de clave anterior
							getJClaveActual().requestFocus();
						} else {
							this.dispose();
						}
					} else {
						this.dispose();
					}
				} catch (MaquinaDeEstadoExcepcion e1) {
					logger.error("actionPerformed(ActionEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
					if (inicioSesion) {
						if (Sesion.getUsuarioActivo().getCodPerfil().equalsIgnoreCase(Sesion.PERFIL_SUPERVISOR)) {
							//MensajesVentanas.mensajeError("Debe Cambiar clave de acceso para ingresar al Sistema\nEsta operación no puede ser cancelada");
							//Colocar el foco en el campo de clave anterior
							getJClaveActual().requestFocus();
						} else {
							this.dispose();
						}
					} else {
						this.dispose();
					}
				} catch (ConexionExcepcion e1) {
					logger.error("actionPerformed(ActionEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
					if (inicioSesion) {
						if (Sesion.getUsuarioActivo().getCodPerfil().equalsIgnoreCase(Sesion.PERFIL_SUPERVISOR)) {
							//MensajesVentanas.mensajeError("Debe Cambiar clave de acceso para ingresar al Sistema\nEsta operación no puede ser cancelada");
							//Colocar el foco en el campo de clave anterior
							getJClaveActual().requestFocus();
						} else {
							this.dispose();
						}
					} else {
						this.dispose();
					}
				} catch (ExcepcionCr e1) {
					logger.error("actionPerformed(ActionEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
					if (inicioSesion) {
						if (Sesion.getUsuarioActivo().getCodPerfil().equalsIgnoreCase(Sesion.PERFIL_SUPERVISOR)) {
							//MensajesVentanas.mensajeError("Debe Cambiar clave de acceso para ingresar al Sistema\nEsta operación no puede ser cancelada");
							//Colocar el foco en el campo de clave anterior
							getJClaveActual().requestFocus();
						} else {
							this.dispose();
						}
					} else {
						this.dispose();
					}
				}
			} 
		}
		else if (btnComando.getText().equals("Cancelar")){
			// Si se trata del cambio de clave en el inicio de Sesion
			// No se puede cancelar para los roles de supervisores
			if (inicioSesion) {
				if (Sesion.getUsuarioActivo().getCodPerfil().equalsIgnoreCase(Sesion.PERFIL_SUPERVISOR)) {
					MensajesVentanas.mensajeError("Debe Cambiar clave de acceso para ingresar al Sistema\nEsta operación no puede ser cancelada");
				} else {
					this.dispose();
				}
			} else {
				this.dispose();
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("actionPerformed(ActionEvent) - end");
		}
	}

	public void keyPressed(KeyEvent e){
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}

		try{
			JPasswordField campo = (JPasswordField)e.getSource();
			if ((campo.getName().equals("ClaveActual"))&&(e.getKeyCode() == KeyEvent.VK_ENTER))
				this.getJPasswordField().requestFocus();
			else if ((campo.getName().equals("ClaveNueva"))&&(e.getKeyCode() == KeyEvent.VK_ENTER))
				this.getJPasswordField2().requestFocus();
			else if ((campo.getName().equals("ClaveNueva2"))&&(e.getKeyCode() == KeyEvent.VK_ENTER))
				this.jButton.doClick();
		} catch(Exception ex){
			logger.error("keyPressed(KeyEvent)", ex);
}

		try{
			if (e.getSource() instanceof JButton) {
				JButton boton = (JButton)e.getSource();
				if ((boton.getText().equals("Aceptar"))&&(e.getKeyCode() == KeyEvent.VK_ENTER)){
					boton.doClick(); 
				}
				else if ((boton.getText().equals("Cancelar"))&&(e.getKeyCode() == KeyEvent.VK_ENTER)){
					// Si se trata del cambio de clave en el inicio de Sesion
					// No se puede cancelar para los roles de supervisores
					if (inicioSesion) {
						if (Sesion.getUsuarioActivo().getCodPerfil().equalsIgnoreCase(Sesion.PERFIL_SUPERVISOR)) {
							MensajesVentanas.mensajeError("Debe Cambiar clave de acceso para ingresar al Sistema\nEsta operación no puede ser cancelada");
						} else {
							this.dispose();
						}
					} else {
						this.dispose();
					}
				}
			}
		} catch(Exception ex){
			logger.error("keyPressed(KeyEvent)", ex);
}
		
		try{
			 if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
				// Si se trata del cambio de clave en el inicio de Sesion
				// No se puede cancelar para los roles de supervisores
				if (inicioSesion) {
					if (Sesion.getUsuarioActivo().getCodPerfil().equalsIgnoreCase(Sesion.PERFIL_SUPERVISOR)) {
						MensajesVentanas.mensajeError("Debe Cambiar clave de acceso para ingresar al Sistema\nEsta operación no puede ser cancelada");
					} else {
						this.dispose();
					}
				} else {
					this.dispose();
				}
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
	public Vector<String> getValores() {
		if (logger.isDebugEnabled()) {
			logger.debug("getValores() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getValores() - end");
		}
		return valores;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	boolean cargarDatos(){
		if (logger.isDebugEnabled()) {
			logger.debug("cargarDatos() - start");
		}

		valores = new Vector<String>();
		String claveActual = new String(this.jClaveActual.getPassword());
		String claveNueva = new String(this.jPasswordField.getPassword());
		String claveNueva2 = new String(this.jPasswordField2.getPassword());
		if ((claveActual != null)&&(claveNueva != null)&&(claveNueva2 != null)&&(claveActual != "")&&(claveNueva != "")&&(claveNueva2 != ""))
			if(!(claveActual.equals(claveNueva))&&(claveNueva.equals(claveNueva2))){
				valores.addElement(claveActual);
				valores.addElement(claveNueva);
			}else {
				MensajesVentanas.mensajeError("Las claves ingresadas no coinciden");
				if (logger.isDebugEnabled()) {
					logger.debug("cargarDatos() - end");
				}
				return false;
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
			java.awt.FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setAlignment(java.awt.FlowLayout.CENTER);
			flowLayout1.setHgap(0);
			flowLayout1.setVgap(0);
			jPanel3.setLayout(flowLayout1);
			jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray,1));
			jPanel3.setPreferredSize(new java.awt.Dimension(380,300));
			jPanel3.setBackground(new java.awt.Color(226,226,222));
			jPanel3.add(getJPanel4(), null);
			jPanel3.add(getJPanel2(), null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel3() - end");
		}
		return jPanel3;
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