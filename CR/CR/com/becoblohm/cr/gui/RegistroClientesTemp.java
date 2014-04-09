/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : RegistroClientesTemp.java
 * Creado por : Ileana Rojas <irojas@beco.com.ve>
 * Creado en  : Jun 11, 2004 - 7:45:10 AM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 2.0
 * Fecha       : 07-08-2006
 * Analista    : yzambrano
 * Descripción : Cambios para nuevos tipos de cliente. 
 * =============================================================================
 */
package com.becoblohm.cr.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EventListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.excepciones.AfiliadoUsrExcepcion;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ClienteExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;
/**
 * @author vmedina
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RegistroClientesTemp extends JDialog implements ComponentListener, KeyListener, MouseListener, EventListener , FocusListener{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(RegistroClientesTemp.class);

	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel5 = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JLabel jLabel4 = null;
	private javax.swing.JTextField jTextField2 = null;
	private javax.swing.JPanel jPanel7 = null;
	private javax.swing.JPanel jPanel8 = null;
	private javax.swing.JPanel jPanel9 = null;
	private javax.swing.JPanel jPanel10 = null;
	private javax.swing.JPanel jPanel11 = null;
	private javax.swing.JTextField jTextField = null;
	private javax.swing.JTextField jTextField1 = null;
	private javax.swing.JTextField jTextField6 = null;
	private javax.swing.JTextField jTextField3 = null;
	private javax.swing.JCheckBox jCheckBox = null;
	private javax.swing.JLabel jLabel3 = null;
	private javax.swing.JLabel jLabel5 = null;
	private javax.swing.JPanel jPanel6 = null;
	private javax.swing.JRadioButton jRadioButton = null;
	private javax.swing.JRadioButton jRadioButton1 = null;
	private javax.swing.JRadioButton jRadioButton2 = null;
	private javax.swing.JRadioButton jRadioButton3 = null;
	private javax.swing.JRadioButton jRadioButton4 = null;
	private JLabel jLabel6 = null;
	private TipoClienteChange tipoListener = new TipoClienteChange();
	/**
	 * This is the default constructor
	 */
	public RegistroClientesTemp() {
		super(MensajesVentanas.ventanaActiva);
		initialize();
		
		jButton.addKeyListener(this);
		jButton.addMouseListener(this);
		
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);
		
		jTextField.addKeyListener(this);
		jTextField.addMouseListener(this);
		jTextField.addFocusListener(this);
		
		jTextField1.addKeyListener(this);
		jTextField1.addMouseListener(this);
		
		jTextField6.addKeyListener(this);
		jTextField6.addMouseListener(this);
		
		jTextField2.addKeyListener(this);
		jTextField2.addMouseListener(this);
		
		jTextField3.addKeyListener(this);
		jTextField3.addMouseListener(this);
		
		jRadioButton.addKeyListener(this);
		jRadioButton.addMouseListener(this);
		
		jRadioButton1.addKeyListener(this);
		jRadioButton1.addMouseListener(this);
		
		jRadioButton2.addKeyListener(this);
		jRadioButton2.addMouseListener(this);
		
		jRadioButton3.addKeyListener(this);
		jRadioButton3.addMouseListener(this);
		
		jRadioButton4.addKeyListener(this);
		jRadioButton4.addMouseListener(this);		
		
		jCheckBox.addKeyListener(this);
		jCheckBox.addMouseListener(this);
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

		this.setTitle("Registro de Clientes");
		this.setSize(400, 290);
		this.setContentPane(getJContentPane());
		this.setUndecorated(true);
		this.setModal(true);
		this.setBackground(new java.awt.Color(226,226,222));
		this.addComponentListener(this);
		
		if (logger.isDebugEnabled()) {
			logger.debug("initialize() - end");
		}
	}
	
	/* initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJContentPane() - start");
		}

		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new FlowLayout());
			jContentPane.add(getJPanel3(), null);
			jContentPane.add(getJPanel2(), null);
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.RAISED));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
			jContentPane.setPreferredSize(new java.awt.Dimension(1061,340));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJContentPane() - end");
		}
		return jContentPane;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}

		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			dispose();
		}
		
		//Mapeo de TAB sobre el componente jTable y jTextField
		if(e.getKeyCode() == KeyEvent.VK_TAB){
			if(e.getSource().equals(this.getJRadioButton1())){
				this.getJRadioButton().requestFocus();			
			} else if(e.getSource().equals(this.getJRadioButton())){
				this.getJRadioButton2().requestFocus();			
			} else if(e.getSource().equals(this.getJRadioButton2())){
				this.getJRadioButton3().requestFocus();			
			} else if(e.getSource().equals(this.getJRadioButton3())){
				this.getJRadioButton4().requestFocus();			
			} else if(e.getSource().equals(this.getJRadioButton4())){
				this.getJTextField().requestFocus();			
			} else if(e.getSource().equals(this.getJTextField())){
				this.getJTextField1().requestFocus();			
			} else if(e.getSource().equals(this.getJTextField1())){
				this.getJTextField6().requestFocus();			
			} else if(e.getSource().equals(this.getJTextField6())){
				this.getJTextField3().requestFocus();	
			} else if(e.getSource().equals(this.getJTextField3())){
				this.getJTextField2().requestFocus();	
			} else if(e.getSource().equals(this.getJTextField2())){
				this.getJCheckBox().requestFocus();
			} else if(e.getSource().equals(this.getJCheckBox())){
				this.getJButton1().requestFocus();
			} else if(e.getSource().equals(this.getJButton1())){
				this.getJButton().requestFocus();
			}else if(e.getSource().equals(this.getJButton())){
				this.getJRadioButton1().requestFocus();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(e.getSource().equals(this.getJButton1())) {
				if(getJTextField().getText().equals("") || getJTextField1().getText().equals("")) {
					MensajesVentanas.mensajeError("Debe especificar al menos Nombre y CI o RIF del cliente");
				} else {
					String id = getJTextField().getText();
					String nombre = getJTextField1().getText();
					String apellido = getJTextField6().getText();
					String telf = getJTextField2().getText().trim();
					String codArea = getJTextField3().getText().trim();
					boolean contactar = getJCheckBox().isSelected();
					char tipoCliente;
					if(getJRadioButton1().isSelected()) 
						tipoCliente = Sesion.CLIENTE_VENEZOLANO;
					else if(getJRadioButton().isSelected())
						tipoCliente = Sesion.CLIENTE_JURIDICO;
					else if (getJRadioButton2().isSelected())
						tipoCliente = Sesion.CLIENTE_GUBERNAMENTAL;
					else if (getJRadioButton3().isSelected())
						tipoCliente = Sesion.CLIENTE_EXTRANJERO;
					else if (getJRadioButton4().isSelected())
						tipoCliente = Sesion.CLIENTE_PASAPORTE;
					else
						tipoCliente = Sesion.CLIENTE_VENEZOLANO;
						
					if(CR.meVenta.getVenta() != null || (CR.meVenta.getVenta() == null && CR.meVenta.getDevolucion() == null)) {
						try {
							CR.meVenta.asignarClienteNoAfiliado(nombre, apellido,id,telf,codArea,tipoCliente,contactar);
							dispose();
							//****** Fecha Actualización 07/02/2007
							//Cambio de BECO para capturar la excepción generada por el cambio de validación de CI/RIF válidos
							//***
						} catch (ClienteExcepcion e1) {
							logger.error("keyPressed(KeyEvent)", e1);
			
							MensajesVentanas.aviso(e1.getMensaje());
						} 
						 catch (AfiliadoUsrExcepcion e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							MensajesVentanas.aviso(e1.getMensaje());
						} catch (BaseDeDatosExcepcion e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							MensajesVentanas.mensajeError(e1.getMensaje());
						} catch (ConexionExcepcion e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							MensajesVentanas.mensajeError(e1.getMensaje());
						} catch (UsuarioExcepcion e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							MensajesVentanas.mensajeError(e1.getMensaje());
						} catch (MaquinaDeEstadoExcepcion e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							MensajesVentanas.mensajeError(e1.getMensaje());
						} catch (ExcepcionCr e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							MensajesVentanas.mensajeError(e1.getMensaje());
						}
					}
					if(CR.meVenta.getDevolucion() != null) {
						try {
							CR.meVenta.asignarClienteDevolucion(nombre, apellido,id,telf,codArea,tipoCliente,contactar);
							dispose();
						} catch (ClienteExcepcion e1) {
							logger.error("keyPressed(KeyEvent)", e1);
			
							MensajesVentanas.aviso(e1.getMensaje());
						} catch (AfiliadoUsrExcepcion e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							MensajesVentanas.aviso(e1.getMensaje());
						} catch (BaseDeDatosExcepcion e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							MensajesVentanas.mensajeError(e1.getMensaje());
						} catch (ConexionExcepcion e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							MensajesVentanas.mensajeError(e1.getMensaje());
						} catch (UsuarioExcepcion e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							MensajesVentanas.mensajeError(e1.getMensaje());
						} catch (MaquinaDeEstadoExcepcion e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							MensajesVentanas.mensajeError(e1.getMensaje());
						} catch (ExcepcionCr e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							MensajesVentanas.mensajeError(e1.getMensaje());
						}				
					}
				}				
			}
			
			if(e.getSource().equals(this.getJTextField())) {
				this.getJTextField1().requestFocus();
			}

			if(e.getSource().equals(this.getJTextField1())){
				if (this.getJTextField6().isEnabled()) 
					this.getJTextField6().requestFocus();
				else
					this.getJTextField3().requestFocus();
			}

			if(e.getSource().equals(this.getJTextField6())){
				this.getJTextField3().requestFocus();
			}

			if(e.getSource().equals(this.getJTextField3())){
				this.getJTextField2().requestFocus();
			}
			
			if(e.getSource().equals(this.getJTextField2())){
				this.getJCheckBox().requestFocus();
			}
			
			if(e.getSource().equals(this.getJRadioButton1())){
				this.getJRadioButton().requestFocus();
			}
			
			if(e.getSource().equals(this.getJRadioButton())){
				this.getJRadioButton2().requestFocus();
			}
			if(e.getSource().equals(this.getJRadioButton2())){
				this.getJRadioButton3().requestFocus();
			}
			if(e.getSource().equals(this.getJRadioButton3())){
				this.getJTextField().requestFocus();
			}
			
			if(e.getSource().equals(this.getJCheckBox())){
				this.getJButton1().requestFocus();
			}
			
			if(e.getSource().equals(this.getJButton())){
				dispose();
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - end");
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyReleased(KeyEvent) - start");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("keyReleased(KeyEvent) - end");
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyTyped(KeyEvent) - start");
		}
				
		if (logger.isDebugEnabled()) {
			logger.debug("keyTyped(KeyEvent) - end");
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - start");
		}

		if(e.getSource().equals(jButton)){
			dispose();
		}
		
		if(e.getSource().equals(jButton1)){
			if(getJTextField().getText().equals("") || getJTextField1().getText().equals("")
			   || getJTextField2().getText().equals("") || getJTextField3().getText().equals("")) {
				MensajesVentanas.mensajeError("Debe especificar Nombre, CI o RIF y Nro. de Teléfono del cliente");
			} else {
				String id = getJTextField().getText();
				String nombre = getJTextField1().getText();
				String apellido = getJTextField6().getText();
				String telf = getJTextField2().getText();
				String codArea = getJTextField3().getText();
				boolean contactar = getJCheckBox().isSelected();
				char tipoCliente;
				if(getJRadioButton1().isSelected()) 
					tipoCliente = Sesion.CLIENTE_VENEZOLANO;
				else if(getJRadioButton().isSelected())
					tipoCliente = Sesion.CLIENTE_JURIDICO;
				else if (getJRadioButton2().isSelected())
					tipoCliente = Sesion.CLIENTE_GUBERNAMENTAL;
				else if (getJRadioButton3().isSelected())
					tipoCliente = Sesion.CLIENTE_EXTRANJERO;
				else //if (getJRadioButton4().isSelected())
					tipoCliente = Sesion.CLIENTE_PASAPORTE;
				//else
					//tipoCliente = Sesion.CLIENTE_VENEZOLANO;
						
				if(CR.meVenta.getVenta() != null || (CR.meVenta.getVenta() == null && CR.meVenta.getDevolucion() == null)) {
					try {
						CR.meVenta.asignarClienteNoAfiliado(nombre, apellido,id,telf,codArea,tipoCliente,contactar);
						dispose();
						//****** Fecha Actualización 07/02/2007
						//Cambio de BECO para capturar la excepción generada por el cambio de validación de CI/RIF válidos
						//***
					} catch (ClienteExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
							
						MensajesVentanas.aviso(e1.getMensaje());
					} catch (AfiliadoUsrExcepcion e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						MensajesVentanas.aviso(e1.getMensaje());
					} catch (BaseDeDatosExcepcion e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (ConexionExcepcion e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (UsuarioExcepcion e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (MaquinaDeEstadoExcepcion e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (ExcepcionCr e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
					}
				} else {
				if(CR.meVenta.getDevolucion() != null) {
					try {
						CR.meVenta.asignarClienteDevolucion(nombre, apellido,id,telf,codArea,tipoCliente,contactar);
						dispose();
					} catch (ClienteExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);
							
						MensajesVentanas.aviso(e1.getMensaje());						
					} catch (AfiliadoUsrExcepcion e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						MensajesVentanas.aviso(e1.getMensaje());
					} catch (BaseDeDatosExcepcion e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (ConexionExcepcion e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (UsuarioExcepcion e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (MaquinaDeEstadoExcepcion e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (ExcepcionCr e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
					}
				}
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - end");
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseEntered(MouseEvent) - start");
		}
	
		if (logger.isDebugEnabled()) {
			logger.debug("mouseEntered(MouseEvent) - end");
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseExited(MouseEvent) - start");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("mouseExited(MouseEvent) - end");
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mousePressed(MouseEvent) - start");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("mousePressed(MouseEvent) - end");
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
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
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setAlignment(java.awt.FlowLayout.CENTER);
			layFlowLayout1.setHgap(1);
			layFlowLayout1.setVgap(0);
			jPanel.setLayout(layFlowLayout1);
			jPanel.add(getJPanel1(), null);
			jPanel.add(getJPanel5(), null);
			jPanel.setPreferredSize(new java.awt.Dimension(360,170));
			jPanel.setBackground(new java.awt.Color(242,242,238));
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Datos del Cliente: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel() - end");
		}
		return jPanel;
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
			java.awt.FlowLayout layFlowLayout11 = new java.awt.FlowLayout();
			layFlowLayout11.setAlignment(java.awt.FlowLayout.RIGHT);
			layFlowLayout11.setHgap(0);
			layFlowLayout11.setVgap(0);
			jPanel2.setLayout(layFlowLayout11);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(getJButton(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(380,30));
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
			jButton.setText("Cancelar");
			jButton.setBackground(new java.awt.Color(226,226,222));
			jButton.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
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
			jButton1.setText("Registrar");
			jButton1.setBackground(new java.awt.Color(226,226,222));
			jButton1.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton1() - end");
		}
		return jButton1;
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
			java.awt.FlowLayout layFlowLayout12 = new java.awt.FlowLayout();
			layFlowLayout12.setHgap(0);
			layFlowLayout12.setVgap(0);
			jPanel3.setLayout(layFlowLayout12);
			jPanel3.add(getJPanel4(), null);
			jPanel3.add(getJPanel(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(380,215));
			jPanel3.setBackground(new java.awt.Color(242,242,238));
			jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel3() - end");
		}
		return jPanel3;
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
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
			layFlowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout2.setVgap(5);
			layFlowLayout2.setHgap(5);
			jPanel4.setLayout(layFlowLayout2);
			jPanel4.add(getJLabel1(), null);
			jPanel4.setPreferredSize(new java.awt.Dimension(380,40));
			jPanel4.setBackground(new java.awt.Color(69,107,127));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel4() - end");
		}
		return jPanel4;
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
			jLabel1.setText("Registro de Clientes No Afiliados");
			jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel1.setForeground(java.awt.Color.white);
			jLabel1.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/businessman_add.png")));
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
			java.awt.GridLayout layGridLayout1 = new java.awt.GridLayout();
			layGridLayout1.setRows(6);
			layGridLayout1.setColumns(0);
			jPanel1.setLayout(layGridLayout1);
			jPanel1.add(getJLabel5(), null);
			jPanel1.add(getJLabel(), null);
			jPanel1.add(getJLabel2(), null);
			jPanel1.add(getJLabel6(), null);
			jPanel1.add(getJLabel3(), null);
			jPanel1.add(getJLabel4(), null);
			jPanel1.setBackground(new java.awt.Color(242,242,238));
			jPanel1.setPreferredSize(new java.awt.Dimension(160,138));

		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel1() - end");
		}
		return jPanel1;
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

		if(jPanel5 == null) {
			jPanel5 = new javax.swing.JPanel();
			java.awt.GridLayout layGridLayout2 = new java.awt.GridLayout();
			layGridLayout2.setRows(6);
			jPanel5.setLayout(layGridLayout2);
			jPanel5.add(getJPanel6(), null);
			jPanel5.add(getJPanel7(), null);
			jPanel5.add(getJPanel8(), null);
			jPanel5.add(getJPanel11(), null);
			jPanel5.add(getJPanel9(), null);
			jPanel5.add(getJPanel10(), null);
			jPanel5.setBackground(new java.awt.Color(242,242,238));
			jPanel5.setPreferredSize(new java.awt.Dimension(170,138));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel5() - end");
		}
		return jPanel5;
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
			jLabel.setText("Identificación:");
			jLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
			jLabel.setPreferredSize(new java.awt.Dimension(160,10));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel() - end");
		}
		return jLabel;
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
			jLabel2.setText("Nombre: ");
			jLabel2.setPreferredSize(new java.awt.Dimension(150,10));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel2() - end");
		}
		return jLabel2;
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

		if(jLabel4 == null) {
			jLabel4 = new javax.swing.JLabel();
			jLabel4.setText("¿Desea ser contactado?");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel4() - end");
		}
		return jLabel4;
	}
	
	private javax.swing.JLabel getJLabel6() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel6() - start");
		}

		if(jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setText("Apellido:");
			jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
			jLabel6.setPreferredSize(new java.awt.Dimension(150,12));
			//jLabel6.setEnabled(false);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel6() - end");
		}
		return jLabel6;
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

		if(jTextField2 == null) {
			jTextField2 = new javax.swing.JTextField();
			jTextField2.setInputVerifier(new TextVerifier(9, "0123456789"));
			jTextField2.setPreferredSize(new java.awt.Dimension(95,20));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField2() - end");
		}
		return jTextField2;
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

		if(jPanel7 == null) {
			jPanel7 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout4 = new java.awt.FlowLayout();
			layFlowLayout4.setAlignment(java.awt.FlowLayout.CENTER);
			layFlowLayout4.setVgap(2);
			jPanel7.setLayout(layFlowLayout4);
			jPanel7.add(getJTextField(), null);
			jPanel7.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel7() - end");
		}
		return jPanel7;
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

		if(jPanel8 == null) {
			jPanel8 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout5 = new java.awt.FlowLayout();
			layFlowLayout5.setAlignment(java.awt.FlowLayout.CENTER);
			layFlowLayout5.setVgap(2);
			jPanel8.setLayout(layFlowLayout5);
			jPanel8.add(getJTextField1(), null);
			jPanel8.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel8() - end");
		}
		return jPanel8;
	}
	
	private javax.swing.JPanel getJPanel11() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel11() - start");
		}

		if(jPanel11 == null) {
			jPanel11 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout11 = new java.awt.FlowLayout();
			layFlowLayout11.setAlignment(java.awt.FlowLayout.CENTER);
			layFlowLayout11.setVgap(2);
			jPanel11.setLayout(layFlowLayout11);
			jPanel11.add(getJTextField6(), null);
			jPanel11.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel11() - end");
		}
		return jPanel11;
	}
	/**
	 * This method initializes jPanel9
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel9() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel9() - start");
		}

		if(jPanel9 == null) {
			jPanel9 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout6 = new java.awt.FlowLayout();
			layFlowLayout6.setAlignment(java.awt.FlowLayout.CENTER);
			layFlowLayout6.setVgap(2);
			jPanel9.setLayout(layFlowLayout6);
			jPanel9.add(getJTextField3(), null);
			jPanel9.add(getJTextField2(), null);
			jPanel9.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel9() - end");
		}
		return jPanel9;
	}
	/**
	 * This method initializes jPanel10
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel10() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel10() - start");
		}

		if(jPanel10 == null) {
			jPanel10 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout7 = new java.awt.FlowLayout();
			layFlowLayout7.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout7.setVgap(1);
			jPanel10.setLayout(layFlowLayout7);
			jPanel10.add(getJCheckBox(), null);
			jPanel10.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel10() - end");
		}
		return jPanel10;
	}
	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField() - start");
		}

		if(jTextField == null) {
			jTextField = new UpperCaseField();
			//jTextField.setInputVerifier(new TextVerifier(12, "JVEGD-0123456789"));
			jTextField.setInputVerifier(new TextVerifier(9, "0123456789"));
			jTextField.setPreferredSize(new java.awt.Dimension(150,20));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField() - end");
		}
		return jTextField;
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

		if(jTextField1 == null) {
			jTextField1 = new UpperCaseField();
			jTextField1.setPreferredSize(new java.awt.Dimension(150,20));
			int maxLength = 50;
			try {
				maxLength = Integer.parseInt(InitCR.preferenciasCR.getConfigStringForParameter("sistema", "longitudNombreCliente"));
			} catch (NumberFormatException e) {
				logger.error("getJTextField1()", e);
			} catch (NoSuchNodeException e) {
				logger.error("getJTextField1()", e);
			} catch (UnidentifiedPreferenceException e) {
				logger.error("getJTextField1()", e);
			}
			jTextField1.setInputVerifier(new TextVerifier(maxLength, " .ABCDEFGHIJKLMNÑOPQRSTUVWXYZÁÉÍÓÚÜ#°&\"'/$!,-@%+;:?¡¿!0123456789"));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField1() - end");
		}
		return jTextField1;
	}
	
	/**
	 * This method initializes jTextField6
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField6() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField6() - start");
		}

		if(jTextField6 == null) {
			jTextField6 = new UpperCaseField();
			jTextField6.setPreferredSize(new java.awt.Dimension(150,20));
			int maxLength = 50;
			try {
				maxLength = Integer.parseInt(InitCR.preferenciasCR.getConfigStringForParameter("sistema", "longitudApellidoCliente"));
			} catch (NumberFormatException e) {
				logger.error("getJTextField6()", e);
			} catch (NoSuchNodeException e) {
				logger.error("getJTextField6()", e);
			} catch (UnidentifiedPreferenceException e) {
				logger.error("getJTextField6()", e);
			}
			jTextField6.setInputVerifier(new TextVerifier(maxLength, " .ABCDEFGHIJKLMNÑOPQRSTUVWXYZÁÉÍÓÚÜ'"));			
			//jTextField6.setEnabled(false);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField6() - end");
		}
		return jTextField6;
	}
	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField3() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField3() - start");
		}

		if(jTextField3 == null) {
			jTextField3 = new javax.swing.JTextField();
			jTextField3.setInputVerifier(new TextVerifier(9, "0123456789"));
			jTextField3.setPreferredSize(new java.awt.Dimension(50,20));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField3() - end");
		}
		return jTextField3;
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

		if(jCheckBox == null) {
			jCheckBox = new javax.swing.JCheckBox();
			jCheckBox.setBackground(new java.awt.Color(242,242,238));
			jCheckBox.setText(" ");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJCheckBox() - end");
		}
		return jCheckBox;
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

		if(jLabel3 == null) {
			jLabel3 = new javax.swing.JLabel();
			jLabel3.setText("Teléfono: ");
			jLabel3.setPreferredSize(new java.awt.Dimension(160,12));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel3() - end");
		}
		return jLabel3;
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
			jLabel5.setText("Tipo Cliente:");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel5() - end");
		}
		return jLabel5;
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

		if(jPanel6 == null) {
			jPanel6 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout13 = new java.awt.FlowLayout();
			layFlowLayout13.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout13.setHgap(1);
			layFlowLayout13.setVgap(0);
			jPanel6.setLayout(layFlowLayout13);
			
			// Se arma el Grupo de Botones de tipo RadioButton
		 	ButtonGroup group = new ButtonGroup();
		 	group.add(getJRadioButton1());
		 	group.add(getJRadioButton());
			group.add(getJRadioButton2());
			group.add(getJRadioButton3());
			group.add(getJRadioButton4());
			
			jPanel6.add(getJRadioButton1(), null);
			jPanel6.add(getJRadioButton(), null);
			jPanel6.add(getJRadioButton2(), null);
			jPanel6.add(getJRadioButton3(), null);
			jPanel6.add(getJRadioButton4(), null);
			jPanel6.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel6() - end");
		}
		return jPanel6;
	}
	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private javax.swing.JRadioButton getJRadioButton1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJRadioButton1() - start");
		}

		if(jRadioButton1 == null) {
			jRadioButton1 = new javax.swing.JRadioButton();
			jRadioButton1.setText("V");
			jRadioButton1.setBackground(new java.awt.Color(242,242,238));
			jRadioButton1.setSelected(true);
			jRadioButton1.addChangeListener(tipoListener);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJRadioButton1() - end");
		}
		return jRadioButton1;
	}
	/**
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private javax.swing.JRadioButton getJRadioButton2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJRadioButton2() - start");
		}

		if(jRadioButton2 == null) {
			jRadioButton2 = new javax.swing.JRadioButton();
			jRadioButton2.setText("G");
			jRadioButton2.setBackground(new java.awt.Color(242,242,238));
			jRadioButton2.addChangeListener(tipoListener);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJRadioButton2() - end");
		}
		return jRadioButton2;
	}
	
	/**
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private javax.swing.JRadioButton getJRadioButton() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJRadioButton() - start");
		}

		if(jRadioButton == null) {
			jRadioButton = new javax.swing.JRadioButton();
			jRadioButton.setText("J");
			jRadioButton.setBackground(new java.awt.Color(242,242,238));
			jRadioButton.addChangeListener(tipoListener);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJRadioButton() - end");
		}
		return jRadioButton;
	}
	/**
	 * This method initializes jRadioButton3
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private javax.swing.JRadioButton getJRadioButton3() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJRadioButton3() - start");
		}

		if(jRadioButton3 == null) {
			jRadioButton3 = new javax.swing.JRadioButton();
			jRadioButton3.setBackground(new java.awt.Color(242,242,238));
			jRadioButton3.setText("E");
			jRadioButton3.addChangeListener(tipoListener);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJRadioButton3() - end");
		}
		return jRadioButton3;
	}

	/**
	 * This method initializes jRadioButton4
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private javax.swing.JRadioButton getJRadioButton4() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJRadioButton4() - start");
		}
		if(jRadioButton4 == null) {
			jRadioButton4 = new javax.swing.JRadioButton();
			jRadioButton4.setText("P");
			jRadioButton4.setBackground(new java.awt.Color(242,242,238));
			jRadioButton4.setSelected(false);
			jRadioButton4.addChangeListener(tipoListener);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJRadioButton4() - end");
		}
		return jRadioButton4;
	}
	/**
	 * Método focusGained
	 *
	 * @param e
	 */
	public void focusGained(FocusEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("focusGained(FocusEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("focusGained(FocusEvent) - end");
		}
	}


	/**
	 * Método focusLost
	 *
	 * @param e
	 */
	public void focusLost(FocusEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("focusLost(FocusEvent) - start");
		}
		Cliente ctemp = null;
		//Verificamos si existe un cliente temporal con este identificador
		String id = getJTextField().getText().trim();
		char tipoCliente;
		if(getJRadioButton1().isSelected()) 
			tipoCliente = Sesion.CLIENTE_VENEZOLANO;
		else if(getJRadioButton().isSelected())
			tipoCliente = Sesion.CLIENTE_JURIDICO;
		else if (getJRadioButton2().isSelected())
			tipoCliente = Sesion.CLIENTE_GUBERNAMENTAL;
		else if (getJRadioButton3().isSelected())
			tipoCliente = Sesion.CLIENTE_EXTRANJERO;
		else if (getJRadioButton4().isSelected())
			tipoCliente = Sesion.CLIENTE_PASAPORTE;
		else
			tipoCliente = Sesion.CLIENTE_VENEZOLANO;
			
		try {
			if (id.length() > 0) {
				ctemp = CR.meVenta.buscarClienteTemporal(id, tipoCliente);
				if(ctemp != null) {
					this.getJTextField1().setText(ctemp.getNombre());
					this.getJTextField6().setText(ctemp.getApellido());
					this.getJTextField2().setText(ctemp.getNumTelefono().trim());
					this.getJTextField3().setText(ctemp.getCodArea().trim());
					if((ctemp.getTipoCliente() == Sesion.CLIENTE_NATURAL) || (ctemp.getTipoCliente() == Sesion.CLIENTE_VENEZOLANO))
						this.getJRadioButton1().setSelected(true);
					else if(ctemp.getTipoCliente() == Sesion.CLIENTE_JURIDICO)
						this.getJRadioButton().setSelected(true);
					else if(ctemp.getTipoCliente() == Sesion.CLIENTE_GUBERNAMENTAL)
						this.getJRadioButton2().setSelected(true);
					else if(ctemp.getTipoCliente() == Sesion.CLIENTE_EXTRANJERO)
						this.getJRadioButton3().setSelected(true);
					else if(ctemp.getTipoCliente() == Sesion.CLIENTE_PASAPORTE)	
						this.getJRadioButton4().setSelected(true);
							
					this.getJCheckBox().setSelected(ctemp.isContactar());
				} else {
					this.getJTextField1().setText("");
					this.getJTextField2().setText("");
					this.getJTextField3().setText("");
					this.getJTextField6().setText("");
					this.getJCheckBox().setSelected(false);
				}
			}
			//****** Fecha Actualización 21/02/2007
			//Cambio de BECO para capturar la excepción generada por el cambio de validación de CI/RIF válidos
			//***
		} catch (ClienteExcepcion e1) {
			logger.error("focusLost(FocusEvent)", e1);
			
			MensajesVentanas.aviso(e1.getMensaje());
		} catch (AfiliadoUsrExcepcion e1) {
			logger.error("focusLost(FocusEvent)", e1);
			
				MensajesVentanas.aviso(e1.getMensaje());
			//****** Fecha Actualización 21/02/2007
			//Cambio de BECO para No permitir registros al dar error. Siempre dejaba seguir 
			//regsitrando si no se cerraba la pantalla
			//***
				dispose();
			
		} catch (BaseDeDatosExcepcion e1) {
			logger.error("focusLost(FocusEvent)", e1);

			MensajesVentanas.mensajeError(e1.getMensaje());
			//****** Fecha Actualización 21/02/2007
			//Cambio de BECO para No permitir registros al dar error. Siempre dejaba seguir 
			//regsitrando si no se cerraba la pantalla
			//***
			dispose();
		} catch (ConexionExcepcion e1) {
			logger.error("focusLost(FocusEvent)", e1);

			MensajesVentanas.mensajeError(e1.getMensaje());
			//****** Fecha Actualización 21/02/2007
			//Cambio de BECO para No permitir registros al dar error. Siempre dejaba seguir 
			//regsitrando si no se cerraba la pantalla
			//***
			dispose();
		} catch (UsuarioExcepcion e1) {
			logger.error("focusLost(FocusEvent)", e1);

			MensajesVentanas.mensajeError(e1.getMensaje());
			//****** Fecha Actualización 21/02/2007
			//Cambio de BECO para No permitir registros al dar error. Siempre dejaba seguir 
			//regsitrando si no se cerraba la pantalla
			//***
			dispose();
		} catch (MaquinaDeEstadoExcepcion e1) {
			logger.error("focusLost(FocusEvent)", e1);

			MensajesVentanas.mensajeError(e1.getMensaje());
			//****** Fecha Actualización 21/02/2007
			//Cambio de BECO para No permitir registros al dar error. Siempre dejaba seguir 
			//regsitrando si no se cerraba la pantalla
			//***
			dispose();
		} catch (ExcepcionCr e1) {
			logger.error("focusLost(FocusEvent)", e1);

			MensajesVentanas.mensajeError(e1.getMensaje());
			//****** Fecha Actualización 21/02/2007
			//Cambio de BECO para No permitir registros al dar error. Siempre dejaba seguir 
			//regsitrando si no se cerraba la pantalla
			//***
			dispose();
		}			

		if (logger.isDebugEnabled()) {
			logger.debug("focusLost(FocusEvent) - end");
		}
	}
	
	private class TipoClienteChange implements javax.swing.event.ChangeListener {
		/**
		 * Logger for this class
		 */
 
		public void stateChanged(javax.swing.event.ChangeEvent e) {
			if (logger.isDebugEnabled()) {
				logger
						.debug("stateChanged(javax.swing.event.ChangeEvent) - start");
			}

			if (e.getSource() instanceof JRadioButton) {
				if (((JRadioButton)e.getSource()).getText().equals("V") || 
						((JRadioButton)e.getSource()).getText().equals("P") || 
				((JRadioButton)e.getSource()).getText().equals("E")) {
					getJLabel6().setEnabled(true);
					getJTextField6().setEnabled(true);
				} else {
					getJLabel6().setEnabled(false);
					getJTextField6().setEnabled(false);
				}
			}

			if (logger.isDebugEnabled()) {
				logger
						.debug("stateChanged(javax.swing.event.ChangeEvent) - end");
			}
		}
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
	
	
}