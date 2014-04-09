/**
 * =============================================================================
 * Fecha       : 27-11-2008
 * Programador    : wdiaz
 * Descripción : - registrar afiliados bajo la nueva estructura.
 * ============================================================================= 
 */
package com.beco.cr.crm;

import java.awt.*;
import java.awt.event.*;
import java.sql.Time;
import java.util.*;
import javax.swing.*;
import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.excepciones.*;
import com.becoblohm.cr.extensiones.RegistroClienteFactory;
import com.becoblohm.cr.gui.*;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarmantenimiento.Estado;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.becoblohm.cr.utiles.Validaciones;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;
import javax.swing.JRadioButton;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JPanel;
import com.becoblohm.cr.gui.UpperCaseField;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import java.awt.BorderLayout;
import javax.swing.JComboBox;
/**
 * @author wdiaz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Set'
* Fecha: agosto 2011
*/
public class RegistroClientesNuevosN extends JDialog implements ComponentListener, KeyListener, MouseListener, EventListener, FocusListener, ActionListener{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(RegistroClientesNuevosN.class);  //  @jve:decl-index=0:

	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JPanel jPanel5 = null;
	private javax.swing.JPanel jPanel6 = null;
	private javax.swing.JPanel jPanel7 = null;
	private javax.swing.JPanel jPanel8 = null;
	private javax.swing.JPanel jPanel13 = null;
	private javax.swing.JPanel jPanel14 = null;
	private javax.swing.JPanel jPanel19 = null;
	private javax.swing.JButton jButton = null;//CANCELAR
	private javax.swing.JButton jButton1 = null;//ACEPTAR
	
	private javax.swing.JLabel jLabel1 = null; //encabezado
	private javax.swing.JTextField jTextField = null; //Identificación
	private javax.swing.JTextField jTextField1 = null; //nombre
	private javax.swing.JTextField jTextField6 = null; //apellido
	//private javax.swing.JTextField jTextField10 = null; //Zona Residencial
	//private javax.swing.JTextField jTextField11 = null; //Correo
	private javax.swing.JRadioButton jRadioButton = null;//J
	private javax.swing.JRadioButton jRadioButton1 = null;//V
	private javax.swing.JRadioButton jRadioButton2 = null;//G
	private javax.swing.JRadioButton jRadioButton3 = null;//E
	private javax.swing.JRadioButton jRadioButton4 = null;//P
	private TipoClienteChange tipoListener = new TipoClienteChange();  //  @jve:decl-index=0:
	KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
	Set<AWTKeyStroke> forward = null;
	Set<AWTKeyStroke> backward = null;
	private JRadioButton jRadioButton20 = null;
	private JRadioButton jRadioButton21 = null;

	private JPanel jPanel1 = null;

	private UpperCaseField jTextField111 = null; //Teléfono

	private UpperCaseField jTextField1111 = null; //Cod. Area
	
	private static Time tiempoInicioCrm;
	private static Time tiempoFinalCrm;

	private JComboBox Estado = null;
	
	private Cliente clienteEmpleado = null;  //  @jve:decl-index=0:
	
	private boolean local = true;
	

	/*
	 Constructor del metodo
	 */
	public RegistroClientesNuevosN() {
		super(MensajesVentanas.ventanaActiva);
		initialize();
		//Si asignar es 2 es porque es una devolucion
		if (CR.meVenta.getDevolucion()!=null){
			getJRadioButton20().setSelected(true);
			getJRadioButton21().setEnabled(false);}
		
	}
	
	public RegistroClientesNuevosN(boolean descargar)
	{
	   initialize();
	   clienteEmpleado = DescargarCliente(CR.meVenta.getVenta().getCliente().getCodCliente());
	}

	private void initialize() {
		if (logger.isDebugEnabled()) {
			logger.debug("initialize() - start");
		}

		this.setTitle("Registro de Clientes");
		this.setResizable(false);
		this.setSize(new Dimension(285, 302));
		this.setContentPane(getJContentPane());
		this.setBackground(new java.awt.Color(226,226,222));
		this.addComponentListener(this);
		
		KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
 		forward = kfm.getDefaultFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);
 		backward = kfm.getDefaultFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS);
		kfm.setDefaultFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
		kfm.setDefaultFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
		
		//20/07/2010. IROJAS..Bloqueo de botón cancelar de manera temporal
		//20/09/2010. IROJAS..DesBloqueo de botón cancelar
		jButton.addKeyListener(this); // Botón Cancelar
		jButton.addMouseListener(this); // Botón Cancelar
				
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);
		
		jTextField.addKeyListener(this);
		jTextField.addMouseListener(this);
		jTextField.addFocusListener(this);
				
		jTextField1.addKeyListener(this);
		jTextField1.addMouseListener(this);
		jTextField1.addFocusListener(this);
				
		jTextField6.addKeyListener(this);
		jTextField6.addMouseListener(this);
		jTextField6.addFocusListener(this);
				
		//jTextField10.addKeyListener(this);
		//jTextField10.addMouseListener(this);
		//jTextField10.addFocusListener(this);
		
		Estado.addKeyListener(this);
		Estado.addMouseListener(this);
		Estado.addFocusListener(this);
				
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
		
		jRadioButton20.addKeyListener(this);
		jRadioButton20.addMouseListener(this);
		
		jRadioButton21.addKeyListener(this);
		jRadioButton21.addMouseListener(this);
		
		jTextField111.addKeyListener(this);
		jTextField111.addMouseListener(this);
		
		jTextField1111.addKeyListener(this);
		jTextField1111.addMouseListener(this);
		
		// Agregado para Tomar tiempo de Ventana Abierta.. Toma el tiempo en que se muestra la pantalla
			setTiempoInicioCrm(Sesion.getHoraSistema());
		// Fin


		if (logger.isDebugEnabled()) {
			logger.debug("initialize() - end");
		}
	}
	
	private javax.swing.JPanel getJContentPane() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJContentPane() - start");
		}

		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.RAISED));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
			jContentPane.setPreferredSize(new java.awt.Dimension(1061,340));
			jContentPane.add(getJPanel3(), BorderLayout.CENTER);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJContentPane() - end");
		}
		return jContentPane;
	}
	
	public void keyPressed(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			//CR.meVenta.setContribuyente(false);
			//20/07/2010. IROJAS..Bloqueo de botón cancelar de manera temporal
			//20/09/2010. IROJAS..Desbloqueo de botón cancelar de manera temporal
			try {
				if(CR.meVenta.getVenta() != null)
					CR.meVenta.quitarCliente();
				else if(CR.meVenta.getDevolucion() != null)
					CR.meVenta.quitarClienteDevolucion();
				else if(CR.meServ.getVentaBR()!=null)
					CR.meServ.quitarCliente();
			} catch (XmlExcepcion e1) {
				e1.printStackTrace();
			} catch (FuncionExcepcion e1) {
				e1.printStackTrace();
			} catch (BaseDeDatosExcepcion e1) {
				e1.printStackTrace();
			} catch (ConexionExcepcion e1) {
				e1.printStackTrace();
			} catch (ExcepcionCr e1){
				MensajesVentanas.mensajeError(e1.getMensaje());
			}
			setTiempoInicioCrm(null);
			setTiempoFinalCrm(null);
			dispose();
		}
		//Mapeo de TAB sobre el componente jTable y jTextField
		else if (e.getKeyCode() == KeyEvent.VK_TAB) {
			if (e.getSource().equals(this.getJRadioButton1())) {
				this.getJRadioButton().requestFocus();
			} else if (e.getSource().equals(this.getJRadioButton())) {
				this.getJRadioButton3().requestFocus();
			}  else if (e.getSource().equals(this.getJRadioButton3())) {
				this.getJRadioButton4().requestFocus();
			} else if (e.getSource().equals(this.getJRadioButton4())) {
				this.getJRadioButton2().requestFocus();
			} else if (e.getSource().equals(this.getJRadioButton2())) {
				this.getJTextField().requestFocus();
			
			}else if (e.getSource().equals(this.getJTextField()) && this.getJRadioButton20().isEnabled()) {
				this.getJRadioButton20().requestFocus();
			}else if (e.getSource().equals(this.getJTextField())) {
				this.getJRadioButton21().requestFocus();
			} else if (e.getSource().equals(this.getJRadioButton20()) && this.getJRadioButton21().isEnabled()) {
				this.getJRadioButton21().requestFocus();
			} else if (e.getSource().equals(this.getJRadioButton20())) {
				this.getJButton1().requestFocus();
			}else if (e.getSource().equals(this.getJRadioButton21()) && this.getJTextField1().isEnabled()) {
				this.getJTextField1().requestFocus();
			}else if (e.getSource().equals(this.getJRadioButton21())) {
				this.getJButton1().requestFocus();
			
			
			}else if (e.getSource().equals(this.getJTextField1())) {
				this.getJTextField6().requestFocus();
			}else if (e.getSource().equals(this.getJTextField6())) {
				this.getJTextField111().requestFocus();
			} else if (e.getSource().equals(this.getJTextField111())) {
				this.getJTextField1111().requestFocus();
			}else if (e.getSource().equals(this.getJTextField1111())) {
				this.getEstado().requestFocus();
			}  else if (e.getSource().equals(this.getEstado())) {
				this.getJButton1().requestFocus();
			} else if (e.getSource().equals(this.getJButton1())) {
				this.getJButton().requestFocus();
			} else if (e.getSource().equals(this.getJButton())) {
				this.transferFocus();
				//this.getJRadioButton1().requestFocus();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (e.getSource().equals(this.getJButton1())) {
				if (e.getSource().equals(jButton1)) {
					
					if ((getJRadioButton().isSelected() || getJRadioButton20().isSelected())&& ((getJTextField().getText().equals(""))|| (getJTextField1().getText().equals("")))
							&& clienteEmpleado != null && (clienteEmpleado.getNumFicha() == null || clienteEmpleado.getNumFicha().equals("")))
					    MensajesVentanas.mensajeError("Debe especificar C/I y Nombre");
				    else
					if (getJTextField().getText().equals("") && clienteEmpleado != null  
							&& (clienteEmpleado.getNumFicha() == null || clienteEmpleado.getNumFicha().equals("")))
						MensajesVentanas.mensajeError("Debe especificar la C/I");
					 
					/*
					 * if (getJTextField().getText().equals("") || ((getJTextField1111().getText().equals("")))
							&& clienteEmpleado != null  && (clienteEmpleado.getNumFicha() == null || clienteEmpleado.getNumFicha().equals("")))
						MensajesVentanas.mensajeError("Debe especificar la C/I, Telefono");
					 */
					
					
					else {
						String id = getJTextField().getText();
						String nombre = getJTextField1().getText();
						String apellido = getJTextField6().getText();
						//String Correo = getJTextField11().getText();
						String ZonaResidencial = String.valueOf(getEstado().getSelectedItem());
						String CodArea= getJTextField111().getText();
						String Telefono= getJTextField1111().getText();
						char tipoCliente;
						boolean contribuyente;
						
						if(getJRadioButton20().isSelected())
							contribuyente=true;
						else
							contribuyente=false;
						
						if (getJRadioButton1().isSelected())
							tipoCliente = Sesion.CLIENTE_VENEZOLANO;
						else if (getJRadioButton().isSelected())
							tipoCliente = Sesion.CLIENTE_JURIDICO;
						else if (getJRadioButton2().isSelected())
							tipoCliente = Sesion.CLIENTE_GUBERNAMENTAL;
						else if (getJRadioButton3().isSelected())
							tipoCliente = Sesion.CLIENTE_EXTRANJERO;
						else
							tipoCliente = Sesion.CLIENTE_PASAPORTE;

						 try {
							 (new RegistroClienteFactory().getInstance()).registrarAsignarCliente(id, nombre, apellido, ZonaResidencial, CodArea, Telefono, tipoCliente, contribuyente,this.clienteEmpleado, this.local);
		
						} catch (MaquinaDeEstadoExcepcion e1) {
							MensajesVentanas.mensajeError(e1.getMensaje());
						} catch (ConexionExcepcion e1) {
							MensajesVentanas.mensajeError(e1.getMensaje());
						} catch (ExcepcionCr e1) {
							MensajesVentanas.mensajeError(e1.getMensaje());
						}	
						finally{
						   this.dispose();
						}
						kfm.setDefaultFocusTraversalKeys(
							KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
							forward);
						kfm.setDefaultFocusTraversalKeys(
							KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,
							backward);
					}
					
//					 Agregado para Tomar tiempo de Ventana Abierta. Toma el tiempo en que se asigna el cliente a la Venta. WDiaz 02-10-2009
					setTiempoFinalCrm(Sesion.getHoraSistema());
				}
	
			}   
			if (e.getSource().equals(this.getJRadioButton1())) {
				this.getJRadioButton().requestFocus();
			} else if (e.getSource().equals(this.getJRadioButton())) {
				this.getJRadioButton3().requestFocus();
			}  else if (e.getSource().equals(this.getJRadioButton3())) {
				this.getJRadioButton4().requestFocus();
			} else if (e.getSource().equals(this.getJRadioButton4())) {
				this.getJRadioButton2().requestFocus();
			} else if (e.getSource().equals(this.getJRadioButton2())) {
				this.getJTextField().requestFocus();
				
			}else if (e.getSource().equals(this.getJTextField()) && this.getJRadioButton20().isEnabled()) {
				this.getJRadioButton20().requestFocus();
			}else if (e.getSource().equals(this.getJTextField())) {
				this.getJRadioButton21().requestFocus();
			} else if (e.getSource().equals(this.getJRadioButton20()) && this.getJRadioButton21().isEnabled()) {
				this.getJRadioButton21().requestFocus();
			} else if (e.getSource().equals(this.getJRadioButton20())) {
				this.getJButton1().requestFocus();
			}else if (e.getSource().equals(this.getJRadioButton21()) && this.getJTextField1().isEnabled()) {
				this.getJTextField1().requestFocus();
			}else if (e.getSource().equals(this.getJRadioButton21())) {
				this.getJButton1().requestFocus();
				
			}else if (e.getSource().equals(this.getJTextField1())) {
				this.getJTextField6().requestFocus();
			}else if (e.getSource().equals(this.getJTextField6())) {
				this.getJTextField111().requestFocus();
			} else if (e.getSource().equals(this.getJTextField111())) {
				this.getJTextField1111().requestFocus();
			}else if (e.getSource().equals(this.getJTextField1111())) {
				this.getEstado().requestFocus();
			}  else if (e.getSource().equals(this.getEstado())) {
				this.getJButton1().requestFocus();
			} else if (e.getSource().equals(this.getJButton1())) {
				this.getJButton().requestFocus();
			} else if (e.getSource().equals(this.getJButton())) {
				this.getJRadioButton1().requestFocus();
			}  
			if (e.getSource().equals(this.getJButton())) {
				kfm.setDefaultFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,forward);
				kfm.setDefaultFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,backward);
				try {
					if(CR.meVenta.getVenta() != null)
						CR.meVenta.quitarCliente();
					else if(CR.meVenta.getDevolucion() != null)
						CR.meVenta.quitarClienteDevolucion();
					else if(CR.meServ.getVentaBR()!=null)
						CR.meServ.quitarCliente();
				} catch (XmlExcepcion e1) {
					e1.printStackTrace();
				} catch (FuncionExcepcion e1) {
					e1.printStackTrace();
				} catch (BaseDeDatosExcepcion e1) {
					e1.printStackTrace();
				} catch (ConexionExcepcion e1) {
					e1.printStackTrace();
				} catch (ExcepcionCr e1){
					MensajesVentanas.mensajeError(e1.getMensaje());
				}	
				setTiempoInicioCrm(null);
				setTiempoFinalCrm(null);
				dispose();
			}
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

		if(e.getSource().equals(jTextField)){
			if(Character.isLetter(e.getKeyChar()))
				e.consume();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("keyTyped(KeyEvent) - end");
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - start");
		}

		if(e.getSource().equals(jButton)){
			kfm.setDefaultFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forward);
			kfm.setDefaultFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backward);
			try {
				if(CR.meVenta.getVenta() != null)
				   CR.meVenta.quitarCliente();
				else if(CR.meVenta.getDevolucion() != null)
					CR.meVenta.quitarClienteDevolucion();
				else if(CR.meServ.getVentaBR()!=null)
					CR.meServ.quitarCliente();
			} catch (XmlExcepcion e1) {
				e1.printStackTrace();
			} catch (FuncionExcepcion e1) {
				e1.printStackTrace();
			} catch (BaseDeDatosExcepcion e1) {
				e1.printStackTrace();
			} catch (ConexionExcepcion e1) {
				e1.printStackTrace();
			} catch (ExcepcionCr e1){
				MensajesVentanas.mensajeError(e1.getMensaje());
			}
			setTiempoInicioCrm(null);
			setTiempoFinalCrm(null);
			dispose();
		}
		
		if(e.getSource().equals(jButton1)){
			if ((getJRadioButton().isSelected() || getJRadioButton20().isSelected())&& ((getJTextField().getText().equals(""))|| (getJTextField1().getText().equals("")))
					&& clienteEmpleado != null && (clienteEmpleado.getNumFicha() == null || clienteEmpleado.getNumFicha().equals("")))
			    MensajesVentanas.mensajeError("Debe especificar C/I y Nombre");
			else
				if (getJTextField().getText().equals("") && clienteEmpleado != null && 
						(clienteEmpleado.getNumFicha() == null || clienteEmpleado.getNumFicha().equals("")))
					MensajesVentanas.mensajeError("Debe especificar la C/I");
			
					/*
					 * if (getJTextField().getText().equals("") || ((getJTextField1111().getText().equals("")))
						&& clienteEmpleado != null && (clienteEmpleado.getNumFicha() == null || clienteEmpleado.getNumFicha().equals("")))
					MensajesVentanas.mensajeError("Debe especificar la C/I, Telefono");
					 */
			else {
				String id = getJTextField().getText();
				String nombre = getJTextField1().getText();
				String apellido = getJTextField6().getText();
			//	String Correo = getJTextField11().getText();
				String ZonaResidencial = String.valueOf(((Estado)Sesion.estados.elementAt(getEstado().getSelectedIndex())).getDescripcion());
				String CodArea= getJTextField111().getText();
				String Telefono= getJTextField1111().getText();
				char tipoCliente;
				boolean contribuyente;
				
				if(getJRadioButton20().isSelected())
					contribuyente=true;
				else
					contribuyente=false;
				
				if(getJRadioButton1().isSelected()) 
					tipoCliente = Sesion.CLIENTE_VENEZOLANO;
				else if(getJRadioButton().isSelected())
					tipoCliente = Sesion.CLIENTE_JURIDICO;
				else if (getJRadioButton2().isSelected())
					tipoCliente = Sesion.CLIENTE_GUBERNAMENTAL;
				else if (getJRadioButton3().isSelected())
					tipoCliente = Sesion.CLIENTE_EXTRANJERO;
				else
					tipoCliente = Sesion.CLIENTE_PASAPORTE;
				
				try {
						
					(new RegistroClienteFactory().getInstance()).registrarAsignarCliente(id, nombre, apellido, ZonaResidencial, CodArea, Telefono, tipoCliente, contribuyente, this.clienteEmpleado, this.local);
			
				} catch (MaquinaDeEstadoExcepcion e1) {
						MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (ConexionExcepcion e1) {
						MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (ExcepcionCr e1) {
						MensajesVentanas.mensajeError(e1.getMensaje());
				}finally{
					   this.dispose();
				}
					
					kfm.setDefaultFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forward);
					kfm.setDefaultFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backward);	
			}
			//Agregado para Tomar tiempo de Ventana Abierta. Toma el tiempo en que se asigna el cliente a la Venta. Wdiaz 02-10-2009
			setTiempoFinalCrm(Sesion.getHoraSistema());
		}
		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - end");
		}
	}

	public void mouseEntered(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseEntered(MouseEvent) - start");
		}
	
		if (logger.isDebugEnabled()) {
			logger.debug("mouseEntered(MouseEvent) - end");
		}
	}
	
	public void mouseExited(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseExited(MouseEvent) - start");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("mouseExited(MouseEvent) - end");
		}
	}
	
	public void mousePressed(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mousePressed(MouseEvent) - start");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("mousePressed(MouseEvent) - end");
		}
	}

	public void mouseClicked(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseReleased(MouseEvent) - start");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("mouseReleased(MouseEvent) - end");
		}
	}

	private javax.swing.JPanel getJPanel2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel2() - start");
		}

		if(jPanel2 == null) {
			jPanel2 = new javax.swing.JPanel();
			jPanel2.setLayout(null);
			//jPanel2.add(getJButton2(),null);
			jPanel2.setPreferredSize(new java.awt.Dimension(645,30));
			jPanel2.setBackground(new java.awt.Color(242,242,238));
			jPanel2.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaptionBorder, 1));
			jPanel2.setBounds(new Rectangle(5, 219, 263, 40));
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(getJButton1(), null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel2() - end");
		}
		return jPanel2;
	}

	private javax.swing.JButton getJButton() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton() - start");
		}

		if(jButton == null) {
			jButton = new JHighlightButton();
			jButton.setText("Cancelar");
			jButton.setBackground(new java.awt.Color(226,226,222));
			jButton.setBounds(new Rectangle(137, 6, 119, 30));
			jButton.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
			//20/07/2010. IROJAS..Bloqueo de botón cancelar de manera temporal
			//20/09/2010. IROJAS..Desbloqueo de botón cancelar de manera temporal
			jButton.setEnabled(true);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton() - end");
		}
		return jButton;
	}

	private javax.swing.JButton getJButton1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton1() - start");
		}

		if(jButton1 == null) {
			jButton1 = new JHighlightButton();
			jButton1.setText("Aceptar");
			jButton1.setBackground(new java.awt.Color(226,226,222));
			jButton1.setBounds(new Rectangle(11, 6, 115, 30));
			jButton1.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton1() - end");
		}
		return jButton1;
	}
	
	private javax.swing.JPanel getJPanel3() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel3() - start");
		}

		if(jPanel3 == null) {
			jPanel3 = new javax.swing.JPanel();
			jPanel3.setLayout(null);
			jPanel3.setPreferredSize(new java.awt.Dimension(645,305));
			jPanel3.setBackground(new java.awt.Color(242,242,238));
			jPanel3.add(getJPanel4(), null);
			jPanel3.add(getJPanel5(), null);
			jPanel3.add(getJPanel2(), null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel3() - end");
		}
		return jPanel3;
	}

	private javax.swing.JPanel getJPanel4() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel4() - start");
		}

		if(jPanel4 == null) {
			jPanel4 = new javax.swing.JPanel();
			jPanel4.setLayout(null);
			jPanel4.setPreferredSize(new java.awt.Dimension(730,40));
			jPanel4.setBackground(new java.awt.Color(69,107,127));
			jPanel4.setBounds(new Rectangle(3, 3, 264, 34));
			jPanel4.add(getJLabel1(), null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel4() - end");
		}
		return jPanel4;
	}
	
	private javax.swing.JLabel getJLabel1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel1() - start");
		}

		if(jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setText("Registro de Clientes ");
			jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jLabel1.setForeground(java.awt.Color.white);
			jLabel1.setBounds(new Rectangle(7, 3, 252, 24));
			jLabel1.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/businessman_add.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel1() - end");
		}
		return jLabel1;
	}

	private javax.swing.JPanel getJPanel5() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel5() - start");
		}

		if(jPanel5 == null) {
			jPanel5 = new javax.swing.JPanel();
			jPanel5.setLayout(null);
			jPanel5.setBackground(new java.awt.Color(242,242,238));
			jPanel5.setPreferredSize(new java.awt.Dimension(200,230));
			jPanel5.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaptionBorder, 1));
			jPanel5.setBounds(new Rectangle(5, 40, 262, 176));
			jPanel5.add(getJPanel8(), null);
			jPanel5.add(getJPanel14(), null);
			jPanel5.add(getJPanel7(), null);
			jPanel5.add(getJPanel13(), null);
			jPanel5.add(getJPanel1(), null);
			jPanel5.add(getJPanel19(), null);
			jPanel5.add(getJPanel6(), null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel5() - end");
		}
		return jPanel5;
	}
	
	private javax.swing.JPanel getJPanel7() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel7() - start");
		}

		if(jPanel7 == null) {
			jPanel7 = new javax.swing.JPanel();
			jPanel7.setBorder(BorderFactory.createTitledBorder(null, " Cedula/Rif: ", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), Color.black));
			jPanel7.setLayout(null);
			jPanel7.setBackground(new java.awt.Color(242,242,238));
			jPanel7.setBounds(new Rectangle(4, 45, 125, 40));
			jPanel7.add(getJTextField(), null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel7() - end");
		}
		return jPanel7;
	}
	
	private javax.swing.JPanel getJPanel8() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel8() - start");
		}

		if(jPanel8 == null) {
			jPanel8 = new javax.swing.JPanel();
			jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Nombre: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
			jPanel8.setLayout(null);
			jPanel8.setBackground(new java.awt.Color(242,242,238));
			jPanel8.setBounds(new Rectangle(4, 91, 125, 40));
			jPanel8.add(getJTextField1(), null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel8() - end");
		}
		return jPanel8;
	}
	
	private javax.swing.JPanel getJPanel13() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel13() - start");
		}

		if(jPanel13 == null) {
			jPanel13 = new javax.swing.JPanel();
			jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Apellido: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
			jPanel13.setLayout(null);
			jPanel13.setBackground(new java.awt.Color(242,242,238));
			jPanel13.setBounds(new Rectangle(133, 91, 125, 40));
			jPanel13.add(getJTextField6(), null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel13() - end");
		}
		return jPanel13;
	}

	private javax.swing.JPanel getJPanel19() {
			if (logger.isDebugEnabled()) {
				logger.debug("getJPanel19() - start");
			}
			

			if(jPanel19 == null) {
				jPanel19 = new javax.swing.JPanel();
				jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fact. Personalizada:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 11), java.awt.Color.black));
				jPanel19.setLayout(null);
				jPanel19.setBackground(new java.awt.Color(242,242,238));
				jPanel19.setBounds(new Rectangle(133, 45, 125, 40));
				jPanel19.add(getJRadioButton20(), null);
				jPanel19.add(getJRadioButton21(), null);
				ButtonGroup grupo = new ButtonGroup();
				grupo.add(getJRadioButton20());
				grupo.add(getJRadioButton21());
			}

			if (logger.isDebugEnabled()) {
				logger.debug("getJPanel9() - end");
			}
			return jPanel19;
		} 

	private javax.swing.JPanel getJPanel14() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel14() - start");
		}

		if(jPanel14 == null) {
			jPanel14 = new javax.swing.JPanel();
			jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Edo. de Residencia:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
			jPanel14.setLayout(null);
			jPanel14.setBackground(new java.awt.Color(242,242,238));
			jPanel14.setBounds(new Rectangle(133, 135, 126, 40));
			jPanel14.add(getEstado(), null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel14() - end");
		}
		return jPanel14;
	}

	private javax.swing.JTextField getJTextField() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField() - start");
		}

		if(jTextField == null) {
			jTextField = new UpperCaseField();
			//jTextField.setInputVerifier(new TextVerifier(12, "JVEGD-0123456789"));
			jTextField.setInputVerifier(new TextVerifier(9, "0123456789"));
			jTextField.setBounds(new Rectangle(6, 14, 111, 20));
			jTextField.setPreferredSize(new java.awt.Dimension(150,20));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField() - end");
		}
		return jTextField;
	}
	
	private javax.swing.JTextField getJTextField1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField1() - start");
		}

		if(jTextField1 == null) {
			jTextField1 = new UpperCaseField();
			jTextField1.setPreferredSize(new java.awt.Dimension(150,20));
			jTextField1.setBounds(new Rectangle(6, 14, 112, 20));
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
	
	private javax.swing.JTextField getJTextField6() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField6() - start");
		}

		if(jTextField6 == null) {
			jTextField6 = new UpperCaseField();
			jTextField6.setPreferredSize(new java.awt.Dimension(150,20));
			jTextField6.setBounds(new Rectangle(7, 14, 113, 20));
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
			
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField6() - end");
		}
		return jTextField6;
	}
	
	/*private javax.swing.JTextField getJTextField10() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField10() - start");
		}

		if(jTextField10 == null) {
			jTextField10 = new JTextField();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField10() - end");
		}
		return jTextField10;
	}*/
	
	
	private javax.swing.JPanel getJPanel6() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel6() - start");
		}

		if(jPanel6 == null) {
			jPanel6 = new javax.swing.JPanel();
			jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Tipo de Cliente: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
			jPanel6.setLayout(null);
			
			// Se arma el Grupo de Botones de tipo RadioButton
		 	ButtonGroup group = new ButtonGroup();
		 	group.add(getJRadioButton1());
		 	group.add(getJRadioButton());
			group.add(getJRadioButton2());
			group.add(getJRadioButton3());
			group.add(getJRadioButton4());
			
			jPanel6.setBackground(new java.awt.Color(242,242,238));
			jPanel6.setBounds(new Rectangle(3, 1, 255, 40));
			jPanel6.add(getJRadioButton1(), null);
			jPanel6.add(getJRadioButton(), null);
			jPanel6.add(getJRadioButton3(), null);
			jPanel6.add(getJRadioButton4(), null);
			jPanel6.add(getJRadioButton2(), null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel6() - end");
		}
		return jPanel6;
	}
	
	private javax.swing.JRadioButton getJRadioButton1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJRadioButton1() - start");
		}

		if(jRadioButton1 == null) {
			jRadioButton1 = new javax.swing.JRadioButton();
			jRadioButton1.setText("V");
			jRadioButton1.setBackground(new java.awt.Color(242,242,238));
			jRadioButton1.setBounds(new Rectangle(19, 16, 42, 17));
			jRadioButton1.setSelected(true);
			jRadioButton1.addChangeListener(tipoListener);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJRadioButton1() - end");
		}
		return jRadioButton1;
	}
	
	private javax.swing.JRadioButton getJRadioButton2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJRadioButton2() - start");
		}

		if(jRadioButton2 == null) {
			jRadioButton2 = new javax.swing.JRadioButton();
			jRadioButton2.setText("G");
			jRadioButton2.setBounds(new Rectangle(198, 16, 50, 17));
			jRadioButton2.setBackground(new java.awt.Color(242,242,238));
			jRadioButton2.addChangeListener(tipoListener);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJRadioButton2() - end");
		}
		return jRadioButton2;
	}
	
	private javax.swing.JRadioButton getJRadioButton() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJRadioButton() - start");
		}

		if(jRadioButton == null) {
			jRadioButton = new javax.swing.JRadioButton();
			jRadioButton.setText("J");
			jRadioButton.setBounds(new Rectangle(64, 16, 42, 17));
			jRadioButton.setBackground(new java.awt.Color(242,242,238));
			jRadioButton.addChangeListener(tipoListener);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJRadioButton() - end");
		}
		return jRadioButton;
	}
	
	private javax.swing.JRadioButton getJRadioButton3() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJRadioButton3() - start");
		}

		if(jRadioButton3 == null) {
			jRadioButton3 = new javax.swing.JRadioButton();
			jRadioButton3.setBackground(new java.awt.Color(242,242,238));
			jRadioButton3.setBounds(new Rectangle(110, 16, 42, 17));
			jRadioButton3.setText("E");
			jRadioButton3.addChangeListener(tipoListener);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJRadioButton3() - end");
		}
		return jRadioButton3;
	}

	private javax.swing.JRadioButton getJRadioButton4() {
			if (logger.isDebugEnabled()) {
				logger.debug("getJRadioButton4() - start");
			}

			if(jRadioButton4 == null) {
				jRadioButton4 = new javax.swing.JRadioButton();
				jRadioButton4.setBackground(new java.awt.Color(242,242,238));
				jRadioButton4.setBounds(new Rectangle(155, 16, 41, 17));
				jRadioButton4.setText("P");
				jRadioButton4.addChangeListener(tipoListener);
			}

			if (logger.isDebugEnabled()) {
				logger.debug("getJRadioButton4() - end");
			}
			return jRadioButton4;
		}

	public void focusGained(FocusEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("focusGained(FocusEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("focusGained(FocusEvent) - end");
		}
	}

	public void focusLost(FocusEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("focusLost(FocusEvent) - start");
		}
		if (e.getSource().equals(jTextField)){

		//Verificamos si existe un cliente temporal con este identificador
			String id=getJTextField().getText();
			if (id.length() > 0) {
				char tipoCliente;
				if(getJRadioButton1().isSelected()) 
					tipoCliente = Sesion.CLIENTE_VENEZOLANO;
				else if(getJRadioButton().isSelected())
					tipoCliente = Sesion.CLIENTE_JURIDICO;
				else if (getJRadioButton2().isSelected())
					tipoCliente = Sesion.CLIENTE_GUBERNAMENTAL;
				else if (getJRadioButton3().isSelected())
					tipoCliente = Sesion.CLIENTE_EXTRANJERO;
				else
					tipoCliente = Sesion.CLIENTE_PASAPORTE;
				
			Validaciones validador = new Validaciones();
			boolean cedRifValido = validador.validarRifCedula(id, tipoCliente);
			if(!cedRifValido) {
					getJTextField().setText("");
					getJTextField().requestFocus();
					MensajesVentanas.aviso("Cedula/Rif Invalido");
			}	
			else
				clienteEmpleado = DescargarCliente(id);
		}
	}else 
		/*if (e.getSource().equals(jTextField11))
			{
				TextVerifier veri= new TextVerifier();
				veri.verify1(jTextField11);
			}
		 */
		if (logger.isDebugEnabled()) {
			logger.debug("focusLost(FocusEvent) - end");
		}
	}
	
	private class TipoClienteChange implements javax.swing.event.ChangeListener {
	
	public void stateChanged(javax.swing.event.ChangeEvent e) {
			if (logger.isDebugEnabled()) {
				logger
						.debug("stateChanged(javax.swing.event.ChangeEvent) - start");
			}

			if (e.getSource() instanceof JRadioButton) {
				if (((JRadioButton)e.getSource()).getText().equals("J") || ((JRadioButton)e.getSource()).getText().equals("G")){ 
					if (((JRadioButton)e.getSource()).isSelected()) {
						//jTextField6.setEnabled(false);
						jRadioButton21.setEnabled(false);
						jRadioButton20.setSelected(true);
					}
				}else{
					if (((JRadioButton)e.getSource()).isSelected()) {
						jTextField6.setEnabled(true);
						jRadioButton21.setEnabled(true);
						jRadioButton21.setSelected(true);
					}
				}
			}

			if (logger.isDebugEnabled()) {
				logger
						.debug("stateChanged(javax.swing.event.ChangeEvent) - end");
			}
		}
	}
	public void componentHidden(ComponentEvent e) {
	}
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

	public void componentResized(ComponentEvent e) {
	}

	public void componentShown(ComponentEvent e) {
	}	

	public void actionPerformed(ActionEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("actionPerformed(ActionEvent) - start");
		} 
		 
		
		if (logger.isDebugEnabled()) {
			logger.debug("actionPerformed(ActionEvent) - end");
		}
	}

	private JRadioButton getJRadioButton20() {
		if (jRadioButton20 == null) {
			jRadioButton20 = new JRadioButton();
			jRadioButton20.setBounds(new Rectangle(23, 13, 43, 24));
			jRadioButton20.setText("S");
			jRadioButton20.setBackground(new Color(242, 242, 238));
		}
		return jRadioButton20;
	}

	private JRadioButton getJRadioButton21() {
		if (jRadioButton21 == null) {
			jRadioButton21 = new JRadioButton();
			jRadioButton21.setBounds(new Rectangle(69, 13, 47, 24));
			jRadioButton21.setSelected(true);
			jRadioButton21.setText("N");
			jRadioButton21.setBackground(new Color(242, 242, 238));
		}
		return jRadioButton21;
	}
	
	public Cliente DescargarCliente(String id)
	{
		try {
		Cliente ctemp = CR.meServ.buscarClienteTemporal(id);
		
		if(ctemp != null) {
			if (ctemp.getCodCliente().charAt(0) == Sesion.CLIENTE_JURIDICO)
				getJRadioButton().setSelected(true);
			else if ((ctemp.getCodCliente().charAt(0) == Sesion.CLIENTE_GUBERNAMENTAL) || (ctemp.getCodCliente().charAt(0) == Sesion.CLIENTE_DIPLOMATICO))
				getJRadioButton2().setSelected(true);
			else if (ctemp.getCodCliente().charAt(0) == Sesion.CLIENTE_EXTRANJERO)
				getJRadioButton3().setSelected(true);
			else if (ctemp.getCodCliente().charAt(0) == Sesion.CLIENTE_PASAPORTE)
				getJRadioButton4().setSelected(true);
			else getJRadioButton1().setSelected(true);
			

			getJRadioButton().setEnabled(false);
			getJRadioButton2().setEnabled(false);
			getJRadioButton3().setEnabled(false);
			getJRadioButton4().setEnabled(false);
			getJRadioButton1().setEnabled(false);
			
			getJTextField().setText(ctemp.getCodCliente().substring(2));
			getJTextField1().setText(ctemp.getNombre());
			getJTextField6().setText(ctemp.getApellido());
			//getJTextField11().setText(ctemp.getEmail());
			//getJTextField10().setText(ctemp.getDirFiscal());
			String direccionfiscal = ctemp.getDirFiscal();
			if ((direccionfiscal != null) && !(direccionfiscal.equals("")))
			      getEstado().setSelectedItem(direccionfiscal);
			getJTextField111().setText(ctemp.getCodArea());
			getJTextField1111().setText(ctemp.getNumTelefono());
			
			if ((ctemp.getNumFicha()!=null)&&(ctemp.getEstadoColaborador()=='A'))
			{
				getJRadioButton20().setEnabled(false);
				getJRadioButton21().setSelected(true);
				getJRadioButton21().requestFocus();
				
				//**** IROJAS 11/06/2010 
				//**** Cambio CRM para el bloqueo de la pantalla cuando el cliente es empleado
				getJRadioButton().setEnabled(false);
				getJRadioButton2().setEnabled(false);
				getJRadioButton3().setEnabled(false);
				getJRadioButton4().setEnabled(false);
				getJRadioButton1().setEnabled(false);
				getJTextField().setEnabled(false);
				getJTextField1().setEnabled(false);
				getJTextField6().setEnabled(false);
				getEstado().setEnabled(false);
				getJTextField111().setEnabled(false);
				getJTextField1111().setEnabled(false);
				//**** 
				
			}
			if (ctemp.getCodCliente()!=null)	
				getJTextField().setEnabled(false); 	
			
		}
		return ctemp;
		} catch (AfiliadoUsrExcepcion e1) {
			logger.error("focusLost(FocusEvent)", e1);
			
			MensajesVentanas.aviso(e1.getMensaje());
		} catch (BaseDeDatosExcepcion e1) {
			logger.error("focusLost(FocusEvent)", e1);

			MensajesVentanas.mensajeError(e1.getMensaje());
		} catch (ConexionExcepcion e1) {
			logger.error("focusLost(FocusEvent)", e1);

			MensajesVentanas.mensajeError(e1.getMensaje());
		} catch (UsuarioExcepcion e1) {
			logger.error("focusLost(FocusEvent)", e1);

			MensajesVentanas.mensajeError(e1.getMensaje());
		} catch (MaquinaDeEstadoExcepcion e1) {
			logger.error("focusLost(FocusEvent)", e1);

			MensajesVentanas.mensajeError(e1.getMensaje());
		} catch (ExcepcionCr e1) {
			logger.error("focusLost(FocusEvent)", e1);

			MensajesVentanas.mensajeError(e1.getMensaje());
		}
		return null;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBackground(new Color(242, 242, 238));
			jPanel1.setBorder(BorderFactory.createTitledBorder(null, "Telefono: ", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), Color.black));
			jPanel1.setBounds(new Rectangle(4, 135, 125, 40));
			jPanel1.add(getJTextField111(), null);
			jPanel1.add(getJTextField1111(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jTextField111	
	 * 	
	 * @return com.becoblohm.cr.gui.UpperCaseField	
	 */
	private UpperCaseField getJTextField111() {
		if (jTextField111 == null) {
			jTextField111 = new UpperCaseField();
			jTextField111.setBounds(new Rectangle(7, 14, 36, 20));
			jTextField111.setPreferredSize(new Dimension(105, 20));
			jTextField111.setInputVerifier(new TextVerifier(4, "0123456789"));
		}
		return jTextField111;
	}

	/**
	 * This method initializes jTextField1111	
	 * 	
	 * @return com.becoblohm.cr.gui.UpperCaseField	
	 */
	private UpperCaseField getJTextField1111() {
		if (jTextField1111 == null) {
			jTextField1111 = new UpperCaseField();
			jTextField1111.setBounds(new Rectangle(45, 14, 74, 20));
			jTextField1111.setPreferredSize(new Dimension(105, 20));
			jTextField1111.setInputVerifier(new TextVerifier(9, "0123456789"));
		}
		return jTextField1111;
	}

	public static Time getTiempoFinalCrm() {
		return tiempoFinalCrm;
	}

	public static void setTiempoFinalCrm(Time tiempoFinalCrm) {
		RegistroClientesNuevosN.tiempoFinalCrm = tiempoFinalCrm;
	}

	public static Time getTiempoInicioCrm() {
		return tiempoInicioCrm;
	}

	public static void setTiempoInicioCrm(Time tiempoInicioCrm) {
		RegistroClientesNuevosN.tiempoInicioCrm = tiempoInicioCrm;
	}

	/**
	 * This method initializes Estado	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getEstado() {
		if (Estado == null) {
			Estado = new JComboBox();
			Estado.setBounds(new Rectangle(6, 15, 116, 20));
            Sesion.cargarEstados();
			if(Sesion.estados != null)
				for (int i = 0; i < Sesion.estados.size(); i++)
					Estado.addItem((((Estado)Sesion.estados.elementAt(i)).getDescripcion()).trim());
			if(Sesion.getTienda()!=null && Sesion.getTienda().getEstado() != null)
			{
				String estado = Sesion.getTienda().getEstado();
				Estado.setSelectedItem(estado.trim());
			}
			else
				Estado.setSelectedIndex(9);
			
		}
		return Estado;
	}

	public boolean isLocal() {
		return local;
	}

	public void setLocal(boolean local) {
		this.local = local;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"  
