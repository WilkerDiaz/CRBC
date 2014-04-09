/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : RegistroClientesNuevos.java
 * Creado por : Yaranaís Zambrano <yzambrano@beco.com.ve>
 * Creado en  : Jun 11, 2006 
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 2.0
 * Fecha       : 06-09-2006
 * Analista    : yzambrano
 * Descripción : Comentar funcionalidad de mantenimiento  
 * =============================================================================
 * Versión     : 2.0
 * Fecha       : 25-07-2006
 * Analista    : yzambrano
 * Descripción : - Ajustes para registrar afiliados bajo la nueva estructura.
 * 				 - Se registra la dirección dividida en estado, ciudad urbanización
 * 				   y el resto de los datos.
 * 				 - Se agregaron los nuevos tipos de afiliados  
 * =============================================================================
 * Fecha       : 25-11-2008
 * Analista    : wdiaz
 * Se incorporaron 4 campos mas para así mejorar el control de nuestros clientes. (CRM)
 * fecha de nacimiento, zona residencial, sexo, estado civil
 * =============================================================================
 */
package com.becoblohm.cr.gui;

import java.awt.AWTKeyStroke;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
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
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.EventListener;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
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
import com.becoblohm.cr.manejarmantenimiento.Ciudad;
import com.becoblohm.cr.manejarmantenimiento.Estado;
import com.becoblohm.cr.manejarmantenimiento.Urbanizacion;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.becoblohm.cr.utiles.Validaciones;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;
import java.awt.Rectangle;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.Color;
import com.toedter.calendar.JDateChooser;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Date;
import java.awt.Font;
/**
 * @author yzambrano
 * actualizado 25/11/2008 wdiaz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RegistroClientesNuevos extends JDialog implements ComponentListener, KeyListener, MouseListener, EventListener, FocusListener, ActionListener{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(RegistroClientesNuevos.class);  //  @jve:decl-index=0:

	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JPanel jPanel5 = null;
	private javax.swing.JPanel jPanel6 = null;
	private javax.swing.JPanel jPanel7 = null;
	private javax.swing.JPanel jPanel8 = null;
	private javax.swing.JPanel jPanel9 = null;
	private javax.swing.JPanel jPanel11 = null;
	private javax.swing.JPanel jPanel12 = null;  //  Ciudad
	private javax.swing.JPanel jPanel13 = null; //Direccion
	private javax.swing.JPanel jPanel14 = null; //telefono2
	private javax.swing.JPanel jPanel15 = null; //email
	private javax.swing.JPanel jPanel16 = null; //etiquetas
	private javax.swing.JPanel jPanel17 = null;
	private javax.swing.JPanel jPanel18 = null;
	private javax.swing.JPanel jPanel19 = null;
	private javax.swing.JPanel jPanel20 = null;
	private javax.swing.JPanel jPanel21 = null;
	
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	
	//private javax.swing.JButton jButton2 = null; Botón de mantenimiento
	
	private javax.swing.JLabel jLabel = null;  //identificación
	private javax.swing.JLabel jLabel1 = null; //encabezado
	private javax.swing.JLabel jLabel2 = null; //nombre
	private javax.swing.JLabel jLabel3 = null; //telefono
	private javax.swing.JLabel jLabel4 = null; //apellido
	private javax.swing.JLabel jLabel5 = null; //tipo cliente
	private javax.swing.JLabel jLabel6 = null; //calle
	private javax.swing.JLabel jLabel7 = null; //Estado
	private javax.swing.JLabel jLabel8 = null; //Ciudad
	private javax.swing.JLabel jLabel9 = null; //Urbanización
	private javax.swing.JLabel jLabel10 = null; //telefono 2
	private javax.swing.JLabel jLabel11 = null; //edificio
	private javax.swing.JLabel jLabel12 = null; //email
	private javax.swing.JLabel jLabel13 = null; //Apartamento - Casa

	private javax.swing.JTextField jTextField = null; //Identificación
	private javax.swing.JTextField jTextField1 = null; //nombre
	private javax.swing.JTextField jTextField2 = null; //telefono 2
	private javax.swing.JTextField jTextField3 = null; // cod area 2
	private javax.swing.JTextField jTextField4 = null; // calle
	private javax.swing.JTextField jTextField5 = null; // edificio
	private javax.swing.JTextField jTextField6 = null; //apellido
	private javax.swing.JTextField jTextField8 = null; //apartamento - casa
	private javax.swing.JTextField jTextField10 = null; //Cod area 1
	private javax.swing.JTextField jTextField11 = null; //Telef 1
	private javax.swing.JTextField jTextField12 = null; //Email

	
	private javax.swing.JRadioButton jRadioButton = null;
	private javax.swing.JRadioButton jRadioButton1 = null;
	private javax.swing.JRadioButton jRadioButton2 = null;
	private javax.swing.JRadioButton jRadioButton3 = null;
	private javax.swing.JRadioButton jRadioButton4 = null;

	private javax.swing.JComboBox jComboBox = null; //Estado
	private javax.swing.JComboBox jComboBox1 = null; //Ciudad
	private javax.swing.JComboBox jComboBox2 = null; //Urbanizacion

	//private Mantenimiento mantenimiento = null;
	private TipoClienteChange tipoListener = new TipoClienteChange();
	private javax.swing.JButton jButton3 = null;
	
	//Deshabilitar el tab por defecto
	KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
	Set<AWTKeyStroke> forward = null;
	Set<AWTKeyStroke> backward = null;

	private JLabel jLabel101 = null;

	private JDateChooser fechaNacimiento = null;

	private GregorianCalendar fecha = null;  //  @jve:decl-index=0:

	private JPanel jPanel91 = null;

	private JLabel jLabel121 = null;

	private JLabel jLabel1011 = null;

	private JPanel jPanel61 = null;

	private JRadioButton jRadioButton11 = null;

	private JRadioButton jRadioButton5 = null;

	private JRadioButton jRadioButton21 = null;

	private JRadioButton jRadioButton31 = null;

	private JPanel jPanel611 = null;

	private JRadioButton jRadioButton111 = null;

	private JRadioButton jRadioButton51 = null;

	private JPanel jPanel10 = null;

	/**
	 * This is the default constructor
	 */
	public RegistroClientesNuevos() {
		super(MensajesVentanas.ventanaActiva);
		initialize();
				
		jButton.addKeyListener(this);
		jButton.addMouseListener(this);
				
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);
		
	//	jButton2.addKeyListener(this);
	//	jButton2.addMouseListener(this);		
				
		jButton3.addKeyListener(this);
		jButton3.addMouseListener(this);
					
		jTextField.addKeyListener(this);
		jTextField.addMouseListener(this);
		jTextField.addFocusListener(this);
				
		jTextField1.addKeyListener(this);
		jTextField1.addMouseListener(this);
		jTextField1.addFocusListener(this);
				
		jTextField2.addKeyListener(this);
		jTextField2.addMouseListener(this);
		jTextField2.addFocusListener(this);
				
		jTextField3.addKeyListener(this);
		jTextField3.addMouseListener(this);
		jTextField3.addFocusListener(this);
				
		jTextField4.addKeyListener(this);
		jTextField4.addMouseListener(this);
		jTextField4.addFocusListener(this);
				
		jTextField5.addKeyListener(this);
		jTextField5.addMouseListener(this);
		jTextField5.addFocusListener(this);
				
		jTextField6.addKeyListener(this);
		jTextField6.addMouseListener(this);
		jTextField6.addFocusListener(this);
				
		jTextField8.addKeyListener(this);
		jTextField8.addMouseListener(this);
		jTextField8.addFocusListener(this);
				
		jTextField10.addKeyListener(this);
		jTextField10.addMouseListener(this);
		jTextField10.addFocusListener(this);
		
		jTextField11.addKeyListener(this);
		jTextField11.addMouseListener(this);
		jTextField11.addFocusListener(this);
		
		jTextField12.addKeyListener(this);
		jTextField12.addMouseListener(this);
		jTextField12.addFocusListener(this);
				
		jComboBox.addKeyListener(this);
		jComboBox.addMouseListener(this);
		jComboBox.addActionListener(this);
				
		jComboBox1.addKeyListener(this);
		jComboBox1.addMouseListener(this);
		jComboBox1.addActionListener(this);
				
		jComboBox2.addKeyListener(this);
		jComboBox2.addMouseListener(this);
		jComboBox2.addActionListener(this);
				
		jRadioButton.addKeyListener(this);
		jRadioButton.addMouseListener(this);
				
		jRadioButton1.addKeyListener(this);
		jRadioButton1.addMouseListener(this);
				
		jRadioButton2.addKeyListener(this);
		jRadioButton2.addMouseListener(this);
				
		jRadioButton3.addKeyListener(this);
		jRadioButton3.addMouseListener(this);
				
		jRadioButton111.addKeyListener(this);
		jRadioButton111.addMouseListener(this);
		
		jRadioButton51.addKeyListener(this);
		jRadioButton51.addMouseListener(this);
		
		jRadioButton11.addKeyListener(this);
		jRadioButton11.addMouseListener(this);
		
		jRadioButton5.addKeyListener(this);
		jRadioButton5.addMouseListener(this);
		
		jRadioButton21.addKeyListener(this);
		jRadioButton21.addMouseListener(this);
		
		jRadioButton31.addKeyListener(this);
		jRadioButton31.addMouseListener(this);
		
		jRadioButton4.addKeyListener(this);
		jRadioButton4.addMouseListener(this);
		
		fechaNacimiento.addKeyListener(this);
		fechaNacimiento.addMouseListener(this);
	}



	public RegistroClientesNuevos(Cliente cliente) {
		super(MensajesVentanas.ventanaActiva);
		initialize();
		
		jButton.addKeyListener(this);
		jButton.addMouseListener(this);
		
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);
		
		//jButton2.addKeyListener(this);
		//jButton2.addMouseListener(this);
		
		jButton3.addKeyListener(this);
		jButton3.addMouseListener(this);
		
		jTextField.addKeyListener(this);
		jTextField.addMouseListener(this);
		jTextField.addFocusListener(this);
				
		jTextField1.addKeyListener(this);
		jTextField1.addMouseListener(this);
		jTextField1.addFocusListener(this);
				
		jTextField2.addKeyListener(this);
		jTextField2.addMouseListener(this);
		jTextField2.addFocusListener(this);
				
		jTextField3.addKeyListener(this);
		jTextField3.addMouseListener(this);
		jTextField3.addFocusListener(this);
				
		jTextField4.addKeyListener(this);
		jTextField4.addMouseListener(this);
		jTextField4.addFocusListener(this);
				
		jTextField5.addKeyListener(this);
		jTextField5.addMouseListener(this);
		jTextField5.addFocusListener(this);
				
		jTextField6.addKeyListener(this);
		jTextField6.addMouseListener(this);
		jTextField6.addFocusListener(this);
				
		jTextField8.addKeyListener(this);
		jTextField8.addMouseListener(this);
		jTextField8.addFocusListener(this);
				
		jTextField10.addKeyListener(this);
		jTextField10.addMouseListener(this);
		jTextField10.addFocusListener(this);
		
		jTextField11.addKeyListener(this);
		jTextField11.addMouseListener(this);
		jTextField11.addFocusListener(this);
		
		jTextField12.addKeyListener(this);
		jTextField12.addMouseListener(this);
		jTextField12.addFocusListener(this);
		
		jComboBox.addKeyListener(this);
		jComboBox.addMouseListener(this);
		jComboBox.addActionListener(this);
		
		jComboBox1.addKeyListener(this);
		jComboBox1.addMouseListener(this);
		jComboBox1.addActionListener(this);
		
		jComboBox2.addKeyListener(this);
		jComboBox2.addMouseListener(this);
		jComboBox2.addActionListener(this);
		
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
		
		jRadioButton111.addKeyListener(this);
		jRadioButton111.addMouseListener(this);
		
		jRadioButton51.addKeyListener(this);
		jRadioButton51.addMouseListener(this);
		
		jRadioButton11.addKeyListener(this);
		jRadioButton11.addMouseListener(this);
		
		jRadioButton5.addKeyListener(this);
		jRadioButton5.addMouseListener(this);
		
		jRadioButton21.addKeyListener(this);
		jRadioButton21.addMouseListener(this);
		
		jRadioButton31.addKeyListener(this);
		jRadioButton31.addMouseListener(this);
		
		jRadioButton4.addKeyListener(this);
		jRadioButton4.addMouseListener(this);
		
		fechaNacimiento.addKeyListener(this);
		fechaNacimiento.addMouseListener(this);
		
		
		//Si encontró el afiliado
		if (!cliente.getCodCliente().equals(""))
		{	
			//Asignar los valores
			//Ver tipo de cliente
			if (cliente.getCodCliente().charAt(0) == Sesion.CLIENTE_JURIDICO)
				getJRadioButton().setSelected(true);
			else if ((cliente.getCodCliente().charAt(0) == Sesion.CLIENTE_GUBERNAMENTAL) || (cliente.getCodCliente().charAt(0) == Sesion.CLIENTE_DIPLOMATICO))
				getJRadioButton2().setSelected(true);
			else if (cliente.getCodCliente().charAt(0) == Sesion.CLIENTE_EXTRANJERO)
				getJRadioButton3().setSelected(true);
			else if (cliente.getCodCliente().charAt(0) == Sesion.CLIENTE_PASAPORTE)
				getJRadioButton4().setSelected(true);
			else getJRadioButton1().setSelected(true);
			
			getJTextField().setText(cliente.getCodCliente().substring(2));
			getJTextField1().setText(cliente.getNombre());
			getJTextField6().setText(cliente.getApellido());
			getJTextField4().setText(cliente.getAvCalle());
			getJTextField5().setText(cliente.getEdfCasa());
			getJTextField8().setText(cliente.getNroApto());
			getJTextField11().setText(cliente.getNumTelefono());
			getJTextField10().setText(cliente.getCodArea());
			getJTextField2().setText(cliente.getNumTelefonoSec());
			getJTextField3().setText(cliente.getCodAreaSec());
			getJTextField12().setText(cliente.getEmail());
			 
			//Nuevo crm... para cargar campos wdiaz 
			if (cliente.getEstadoCivil() == Sesion.CASADO)
					getJRadioButton5().setSelected(true);
				else if (cliente.getEstadoCivil() == Sesion.DIVORCIADO)
					getJRadioButton21().setSelected(true);
				else if (cliente.getEstadoCivil() == Sesion.VIUDO)
					getJRadioButton31().setSelected(true);
				else getJRadioButton11().setSelected(true);
				
				if (cliente.getSexo()==Sesion.FEMENINO)
					getJRadioButton51().setSelected(true);
				else
				if (cliente.getSexo()==Sesion.MASCULINO)
					getJRadioButton111().setSelected(true);
				
				fechaNacimiento.setDateFormatString("dd-MM-yyyy");
				fechaNacimiento.setDate(cliente.getFechaNaci());
				
			
			int posicion;
			if (!cliente.getCodEdo().equals("")){
				posicion = seleccionarEstado(cliente.getCodEdo());
				if (posicion > 0) 
				{
					jComboBox.setSelectedIndex(posicion);
					if (!cliente.getCodCiu().equals(""))
					{
						posicion = seleccionarCiudad(cliente.getCodCiu());
						if (posicion > 0){ 
							jComboBox1.setSelectedIndex(posicion);
							if (!cliente.getCodUrb().equals("")){
								posicion = seleccionarUrbanizacion(cliente.getCodUrb());
								if (posicion > 0) 
									jComboBox2.setSelectedIndex(posicion);
							}
						}	
					}
				}
			}
		}
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se comentaron variables sin uso
	* Fecha: agosto 2011
	*/
	public RegistroClientesNuevos(String id) {
			super(MensajesVentanas.ventanaActiva);
			initialize();
		
			jButton.addKeyListener(this);
			jButton.addMouseListener(this);
		
			jButton1.addKeyListener(this);
			jButton1.addMouseListener(this);
			
			//jButton2.addKeyListener(this);
			//jButton2.addMouseListener(this);			
		
			jButton3.addKeyListener(this);
			jButton3.addMouseListener(this);
			
			jTextField.addKeyListener(this);
			jTextField.addMouseListener(this);
			jTextField.addFocusListener(this);
						
			jTextField1.addKeyListener(this);
			jTextField1.addMouseListener(this);
			jTextField1.addFocusListener(this);
						
			jTextField2.addKeyListener(this);
			jTextField2.addMouseListener(this);
			jTextField2.addFocusListener(this);
						
			jTextField3.addKeyListener(this);
			jTextField3.addMouseListener(this);
			jTextField3.addFocusListener(this);
						
			jTextField4.addKeyListener(this);
			jTextField4.addMouseListener(this);
			jTextField4.addFocusListener(this);
						
			jTextField5.addKeyListener(this);
			jTextField5.addMouseListener(this);
			jTextField5.addFocusListener(this);
						
			jTextField6.addKeyListener(this);
			jTextField6.addMouseListener(this);
			jTextField6.addFocusListener(this);
						
			jTextField8.addKeyListener(this);
			jTextField8.addMouseListener(this);
			jTextField8.addFocusListener(this);
						
			jTextField10.addKeyListener(this);
			jTextField10.addMouseListener(this);
			jTextField10.addFocusListener(this);
				
			jTextField11.addKeyListener(this);
			jTextField11.addMouseListener(this);
			jTextField11.addFocusListener(this);
				
			jTextField12.addKeyListener(this);
			jTextField12.addMouseListener(this);
			jTextField12.addFocusListener(this);
		
			jComboBox.addKeyListener(this);
			jComboBox.addMouseListener(this);
			jComboBox.addActionListener(this);
			
			jComboBox1.addKeyListener(this);
			jComboBox1.addMouseListener(this);
			jComboBox1.addActionListener(this);
			
			jComboBox2.addKeyListener(this);
			jComboBox2.addMouseListener(this);
			jComboBox2.addActionListener(this);
				
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
			
			
		
		
			if (id.charAt(0) == Sesion.CLIENTE_JURIDICO)
				getJRadioButton().setSelected(true);
			else if ((id.charAt(0) == Sesion.CLIENTE_GUBERNAMENTAL) || (id.charAt(0) == Sesion.CLIENTE_DIPLOMATICO))
				getJRadioButton2().setSelected(true);
			else if (id.charAt(0) == Sesion.CLIENTE_EXTRANJERO)
				getJRadioButton3().setSelected(true);
			else if (id.charAt(0) == Sesion.CLIENTE_PASAPORTE)
				getJRadioButton4().setSelected(true);
			else getJRadioButton1().setSelected(true);		
			
			
			
			if ((id.charAt(0) == Sesion.CLIENTE_JURIDICO) || (id.charAt(0) == Sesion.CLIENTE_GUBERNAMENTAL) || (id.charAt(0) == Sesion.CLIENTE_VENEZOLANO) || (id.charAt(0) == Sesion.CLIENTE_EXTRANJERO) || (id.charAt(0) == Sesion.CLIENTE_PASAPORTE)) {
				try
				{
					//long valorLong = Long.parseLong(id.substring(1));
					getJTextField().setText(id.substring(1));
				}catch(Exception e){
					getJTextField().setText("");
				}
			}
			else
				try
				{
					//long valorLong = Long.parseLong(id);
					getJTextField().setText(id);
				}catch(Exception e)
				{
					getJTextField().setText("");
				}
				
		}

	

	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	@SuppressWarnings("unchecked")
	private void initialize() {
		if (logger.isDebugEnabled()) {
			logger.debug("initialize() - start");
		}

		this.setTitle("Registro de Clientes");
		this.setResizable(false);
		this.setSize(681, 411);
		this.setContentPane(getJContentPane());
		this.setUndecorated(true);
		this.setModal(true);
		this.setBackground(new java.awt.Color(226,226,222));
		this.addComponentListener(this);
		
		KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
 		forward = kfm.getDefaultFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);
 		backward = kfm.getDefaultFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS);
		kfm.setDefaultFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
		kfm.setDefaultFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);

		/* Para Lista de Regalos -> Si esta en modo titular desabilita
		 * la edición del campo de identificacion */
		if(Sesion.getCaja().getEstado().equals("22"))
			getJTextField().setEditable(false);

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
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.RAISED));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
			jContentPane.setPreferredSize(new java.awt.Dimension(1061,340));
			jContentPane.add(getJPanel2(), null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJContentPane() - end");
		}
		return jContentPane;
	}
	
	private String armarDireccion ()
	{
		DecimalFormat formatoEstado = new DecimalFormat("000");
		DecimalFormat formatoUrbanizacion = new DecimalFormat("000000");
		String nuevaDireccion = "";
		int ciudad, urb, estado;
		estado = ((Estado)Sesion.estados.elementAt(jComboBox.getSelectedIndex() - 1)).getCodigo();
		ciudad = ((Ciudad)Sesion.ciudades.elementAt(jComboBox1.getSelectedIndex() - 1)).getCodigo();
		urb = ((Urbanizacion)Sesion.urbanizaciones.elementAt(jComboBox2.getSelectedIndex() - 1)).getCodigo();
		nuevaDireccion = getJTextField4().getText() + ">" + getJTextField5().getText() + ">" + getJTextField8().getText() + ">" +
		formatoUrbanizacion.format(urb) + ">" + formatoEstado.format(ciudad) + ">" + 
		formatoEstado.format(estado);
		return nuevaDireccion;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			dispose();
		}
		//Mapeo de TAB sobre el componente jTable y jTextField
		else if (e.getKeyCode() == KeyEvent.VK_TAB) {
			if (e.getSource().equals(this.getJRadioButton1())) {
				this.getJRadioButton().requestFocus();
			} else if (e.getSource().equals(this.getJRadioButton())) {
				this.getJRadioButton2().requestFocus();
			} else if (e.getSource().equals(this.getJRadioButton2())) {
				this.getJRadioButton3().requestFocus();
			} else if (e.getSource().equals(this.getJRadioButton3())) {
				this.getJRadioButton4().requestFocus();
			} else if (e.getSource().equals(this.getJRadioButton4())) {
				this.getJTextField().requestFocus();
			} else if (e.getSource().equals(this.getJTextField())) {
				this.getJTextField1().requestFocus();
			} else if (e.getSource().equals(this.getJTextField1())) {
				this.getJTextField6().requestFocus();
			} else if (e.getSource().equals(this.getJTextField6())) {
				this.getJComboBox().requestFocus();
			} else if (e.getSource().equals(this.getJComboBox())) {
				this.getJComboBox1().requestFocus();
			} else if (e.getSource().equals(this.getJComboBox1())) {
				this.getJComboBox2().requestFocus();
			} else if (e.getSource().equals(this.getJComboBox2())) {
				this.getJTextField4().requestFocus();
			} else if (e.getSource().equals(this.getJTextField4())) {
				this.getJTextField5().requestFocus();
			} else if (e.getSource().equals(this.getJTextField5())) {
				this.getJTextField8().requestFocus();
			} else if (e.getSource().equals(this.getJTextField8())) {
				this.getJTextField10().requestFocus();
			} else if (e.getSource().equals(this.getJTextField10())) {
				this.getJTextField11().requestFocus();
			} else if (e.getSource().equals(this.getJTextField11())) {
				this.getJTextField3().requestFocus();
			} else if (e.getSource().equals(this.getJTextField3())) {
				this.getJTextField2().requestFocus();
			} else if (e.getSource().equals(this.getJTextField2())) {
				this.getJTextField12().requestFocus();
			} else if (e.getSource().equals(this.getJTextField12())) {
				this. getfechaNacimiento().requestFocus();
			}else if (e.getSource().equals(this.getfechaNacimiento())) {
				this.getJRadioButton11().requestFocus();
			}else if (e.getSource().equals(this.getJRadioButton11())) {
				this.getJRadioButton5().requestFocus();
			}else if (e.getSource().equals(this.getJRadioButton5())) {
				this.getJRadioButton21().requestFocus();
			}else if (e.getSource().equals(this.getJRadioButton21())) {
				this.getJRadioButton31().requestFocus();
			}else if (e.getSource().equals(this.getJRadioButton31())) {
				this.getJRadioButton111().requestFocus();
			}else if (e.getSource().equals(this.getJRadioButton111())) {
				this.getJRadioButton51().requestFocus();
			}else if (e.getSource().equals(this.getJRadioButton51())) {
				this.getJButton3().requestFocus();
			}else if (e.getSource().equals(this.getJButton3())) {
				this.getJButton1().requestFocus();
			} else if (e.getSource().equals(this.getJButton1())) {
				this.getJButton().requestFocus();
			} else if (e.getSource().equals(this.getJButton())) {
				this.getJRadioButton1().requestFocus();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (e.getSource().equals(this.getJButton1())) {
				if (e.getSource().equals(jButton1)) {
					if (getJTextField().getText().equals("") || getJTextField1().getText().equals("") || (getJRadioButton1().isSelected()	&& getJTextField6().getText().equals(""))
						|| (getJRadioButton2().isSelected()	&& getJTextField6().getText().equals("")) || (getJRadioButton3().isSelected() && getJTextField6().getText().equals(""))
						|| (getJRadioButton4().isSelected()	&& getJTextField6().getText().equals("")) || getJTextField4().getText().equals("") || getJTextField5().getText().equals("")
						|| getJTextField8().getText().equals("") || (jComboBox.getSelectedIndex() == 0) || (jComboBox1.getSelectedIndex() == 0) || (jComboBox2.getSelectedIndex() == 0)
/*modificado wdiaz*/	|| ((getJTextField10().getText().equals("") && getJTextField11().getText().equals("") && getJTextField2().getText().equals("") && getJTextField3().getText().equals(""))&&(getJTextField12().getText().equals("")))
						) {
						MensajesVentanas.mensajeError("Debe especificar al menos Nombre, CI o RIF,\n dirección completa y algún número telefónico del cliente");
					} else {
						String id = getJTextField().getText();
						String nombre = getJTextField1().getText();
						String apellido = getJTextField6().getText();
						String telf = getJTextField11().getText();
						String codArea = getJTextField10().getText();
						String direccion = armarDireccion();
						String codArea2 = getJTextField3().getText();
						String telf2 = getJTextField2().getText();
						String email = getJTextField12().getText();
						//Codigo nuevo. Crm Wdiaz
						char tipoCliente,sexo=' ',estadoCivil=' ';
						String zonaResidencial= ((Estado)Sesion.estados.elementAt(jComboBox.getSelectedIndex() - 1)).getDescripcion();
						Date fechaNaci= getfechaNacimiento().getDate();
						//fin
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
						
						if(getJRadioButton11().isSelected()) 
							estadoCivil=Sesion.SOLTERO;
						else if(getJRadioButton5().isSelected())
							estadoCivil=Sesion.CASADO;
						else if (getJRadioButton21().isSelected())
							estadoCivil=Sesion.DIVORCIADO;
						else 
							estadoCivil=Sesion.VIUDO;
						
						if(getJRadioButton111().isSelected()) 
							sexo=Sesion.MASCULINO;
						else if(getJRadioButton51().isSelected())
							sexo=Sesion.FEMENINO;

						if (CR.meServ.getApartado() != null) {
							try {
								CR.meServ.asignarCliente(nombre,apellido,id,telf,codArea,
										direccion,telf2,codArea2,email,tipoCliente,true,sexo,estadoCivil,fechaNaci,zonaResidencial);
								this.dispose();
							} catch (ClienteExcepcion e1) {
								logger.error("keyPressed(KeyEvent)", e1);
							
								MensajesVentanas.aviso(e1.getMensaje());
							} catch (AfiliadoUsrExcepcion e1) {
								logger.error("keyPressed(KeyEvent)", e1);

								MensajesVentanas.aviso(e1.getMensaje());
								this.dispose();
							} catch (BaseDeDatosExcepcion e1) {
								logger.error("keyPressed(KeyEvent)", e1);

								MensajesVentanas.mensajeError(e1.getMensaje());
								this.dispose();
							} catch (ConexionExcepcion e1) {
								logger.error("keyPressed(KeyEvent)", e1);

								MensajesVentanas.mensajeError(e1.getMensaje());
								this.dispose();
							} catch (UsuarioExcepcion e1) {
								logger.error("keyPressed(KeyEvent)", e1);

								MensajesVentanas.mensajeError(e1.getMensaje());
								this.dispose();
							} catch (MaquinaDeEstadoExcepcion e1) {
								logger.error("keyPressed(KeyEvent)", e1);

								MensajesVentanas.mensajeError(e1.getMensaje());
								this.dispose();
							} catch (ExcepcionCr e1) {
								logger.error("keyPressed(KeyEvent)", e1);

								MensajesVentanas.mensajeError(e1.getMensaje());
								this.dispose();
							}
						} else if (CR.meServ.getListaRegalos() != null) {
							try {
								CR.meServ.asignarClienteLR(nombre,apellido,id,telf,codArea,direccion,
											telf2,codArea2,email,tipoCliente,true,sexo,estadoCivil,fechaNaci,zonaResidencial);
								this.dispose();
							} catch (ClienteExcepcion e1) {
								logger.error("keyPressed(KeyEvent)", e1);
							
								MensajesVentanas.aviso(e1.getMensaje());							
							} catch (AfiliadoUsrExcepcion e1) {
								logger.error("keyPressed(KeyEvent)", e1);
								MensajesVentanas.aviso(e1.getMensaje());
								this.dispose();
							} catch (BaseDeDatosExcepcion e1) {
								logger.error("keyPressed(KeyEvent)", e1);
								MensajesVentanas.mensajeError(e1.getMensaje());
								this.dispose();
							} catch (ConexionExcepcion e1) {
								logger.error("keyPressed(KeyEvent)", e1);
								MensajesVentanas.mensajeError(e1.getMensaje());
								this.dispose();
							} catch (UsuarioExcepcion e1) {
								logger.error("keyPressed(KeyEvent)", e1);
								MensajesVentanas.mensajeError(e1.getMensaje());
								this.dispose();
							} catch (MaquinaDeEstadoExcepcion e1) {
								logger.error("keyPressed(KeyEvent)", e1);
								MensajesVentanas.mensajeError(e1.getMensaje());
								this.dispose();
							} catch (ExcepcionCr e1) {
								logger.error("keyPressed(KeyEvent)", e1);
								MensajesVentanas.mensajeError(e1.getMensaje());
								this.dispose();
							}
							//this.dispose();
						} else {
							try {
								if (MensajesVentanas.preguntarSiNo("¿Desea registrar como afiliado a:\n" + nombre + " " + apellido + "?") == 0) {
									CR.meServ.registrarAfiliado(nombre,apellido,id,telf,codArea,direccion,
											telf2,codArea2,email,tipoCliente,true,sexo,estadoCivil,fechaNaci,zonaResidencial);
									dispose();
								}
							} catch (MaquinaDeEstadoExcepcion e1) {
								MensajesVentanas.mensajeError(e1.getMensaje());
							} catch (ConexionExcepcion e1) {
								e1.printStackTrace();
							} catch (ExcepcionCr e1) {
								e1.printStackTrace();
							}
						}
						kfm.setDefaultFocusTraversalKeys(
							KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
							forward);
						kfm.setDefaultFocusTraversalKeys(
							KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,
							backward);
					}
				}
				/*else if (e.getSource().equals(this.getJButton2())){
					mantenimiento = null; 
					try {
						mantenimiento = new Mantenimiento(false);
						CR.meServ.iniciarMantenimiento();
						MensajesVentanas.centrarVentanaDialogo(mantenimiento);
					} catch (MaquinaDeEstadoExcepcion e2) {
						MensajesVentanas.mensajeError(e2.getMensaje());
					} catch (XmlExcepcion e2) {
						e2.printStackTrace();
					} catch (FuncionExcepcion e2) {
						e2.printStackTrace();
					} catch (BaseDeDatosExcepcion e2) {
						e2.printStackTrace();
					} catch (ConexionExcepcion e2) {
						e2.printStackTrace();
					} catch (UsuarioExcepcion e2) {
						MensajesVentanas.mensajeError(e2.getMensaje());
					} catch (ExcepcionCr e2) {
						e2.printStackTrace();
					}*/
			} else if (e.getSource().equals(this.getJButton3())) {
				//Actualizar combo estados
				Sesion.cargarEstados();
				this.llenarComboEstados();
				//Limpiar combo ciudades
				this.llenarComboCiudades();
				//Limpiar combo urbanizaciones
				this.llenarComboUrbanizaciones();
			} else if (e.getSource().equals(this.getJTextField())) {
				this.getJTextField1().requestFocus();
			} else if (e.getSource().equals(this.getJTextField1())) {
				this.getJTextField6().requestFocus();
			} else if (e.getSource().equals(this.getJComboBox())) {
				this.getJComboBox1().requestFocus();
			} else if (e.getSource().equals(this.getJComboBox1())) {
				this.getJComboBox2().requestFocus();
			} else if (e.getSource().equals(this.getJComboBox2())) {
				this.getJTextField4().requestFocus();
			}
			if (e.getSource().equals(this.getJTextField10())) {
				this.getJTextField11().requestFocus();
			}

			if (e.getSource().equals(this.getJTextField11())) {
				this.getJTextField3().requestFocus();
			}

			if (e.getSource().equals(this.getJTextField3())) {
				this.getJTextField2().requestFocus();
			}

			if (e.getSource().equals(this.getJTextField4())) {
				this.getJTextField5().requestFocus();
			}

			if (e.getSource().equals(this.getJTextField5())) {
				this.getJTextField8().requestFocus();
			}

			if (e.getSource().equals(this.getJTextField8())) {
				this.getJTextField10().requestFocus();
			}

			if (e.getSource().equals(this.getJTextField2())) {
				this.getJTextField12().requestFocus();
			}
			
			if (e.getSource().equals(this.getJTextField6())) {
				this.getJComboBox().requestFocus();
			}

			if (e.getSource().equals(this.getJRadioButton1())) {
				this.getJRadioButton().requestFocus();
			}

			if (e.getSource().equals(this.getJRadioButton())) {
				this.getJRadioButton2().requestFocus();
			}
			if (e.getSource().equals(this.getJRadioButton2())) {
				this.getJRadioButton3().requestFocus();
			}
			if (e.getSource().equals(this.getJRadioButton3())) {
				this.getJRadioButton4().requestFocus();
			}
			if (e.getSource().equals(this.getJRadioButton4())) {
				this.getJTextField().requestFocus();
			}
			//nuevo crm
			if (e.getSource().equals(this.getJTextField12())) {
				this.getfechaNacimiento(). requestFocus();
			}
			
			if (e.getSource().equals(this.getfechaNacimiento())) {
				this.getJRadioButton11().requestFocus();
			}
			if (e.getSource().equals(this.getJRadioButton11())) {
				this.getJRadioButton5().requestFocus();
			}
			if (e.getSource().equals(this.getJRadioButton5())) {
				this.getJRadioButton21().requestFocus();
			}
			if (e.getSource().equals(this.getJRadioButton21())) {
				this.getJRadioButton31().requestFocus();
			}
			if (e.getSource().equals(this.getJRadioButton31())) {
				this.getJRadioButton111().requestFocus();
			}
			if (e.getSource().equals(this.getJRadioButton111())) {
				this.getJRadioButton51().requestFocus();
			}
			if (e.getSource().equals(this.getJRadioButton51())) {
				getJRadioButton1().requestFocus();
			}
			
			//fin

			if (e.getSource().equals(this.getJButton())) {
				kfm.setDefaultFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,forward);
				kfm.setDefaultFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,backward);
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

		if(e.getSource().equals(jTextField)){
			if(Character.isLetter(e.getKeyChar()))
				e.consume();
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
			kfm.setDefaultFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forward);
			kfm.setDefaultFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backward);
			dispose();
		}
		
		if(e.getSource().equals(jButton1)){
			if (getJTextField().getText().equals("") || getJTextField1().getText().equals("") ||
			   (getJRadioButton1().isSelected() && getJTextField6().getText().equals("")) || 
			   (getJRadioButton2().isSelected() && getJTextField6().getText().equals("")) ||
			   (getJRadioButton3().isSelected() && getJTextField6().getText().equals("")) ||
			   (getJRadioButton4().isSelected() && getJTextField6().getText().equals("")) ||
			    getJTextField4().getText().equals("") ||
			    getJTextField5().getText().equals("") || getJTextField8().getText().equals("") ||
			   (jComboBox.getSelectedIndex() == 0) ||(jComboBox1.getSelectedIndex() == 0) || 
			   (jComboBox2.getSelectedIndex() == 0) ||
			   ((getJTextField10().getText().equals("") && getJTextField11().getText().equals("")	&& 
			    getJTextField2().getText().equals("") && getJTextField3().getText().equals(""))&&(getJTextField12().getText().equals("")))
			    ) {
					MensajesVentanas.mensajeError("Debe especificar al menos Nombre, CI o RIF,\n dirección completa y algún número telefónico del cliente");
			} else {
				String id = getJTextField().getText();
				String nombre = getJTextField1().getText();
				String apellido = getJTextField6().getText();
				String telf = getJTextField11().getText();
				String codArea = getJTextField10().getText();
				String direccion = armarDireccion();
				String codArea2 = getJTextField3().getText();
				String telf2 = getJTextField2().getText();
				String email = getJTextField12().getText();
				char tipoCliente;
				
				//se agrego nuevos campos sexo, estado civil y fecha de nacimiento
				Date fechaNaci= getfechaNacimiento().getDate();
				char sexo=' ';
				char estadoCivil=' ';
				String zonaResidencial= ((Estado)Sesion.estados.elementAt(jComboBox.getSelectedIndex() - 1)).getDescripcion();
				
				
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
				
				if(getJRadioButton11().isSelected()) 
					estadoCivil=Sesion.SOLTERO;
				else if(getJRadioButton5().isSelected())
					estadoCivil=Sesion.CASADO;
				else if (getJRadioButton21().isSelected())
					estadoCivil=Sesion.DIVORCIADO;
				else if (getJRadioButton31().isSelected())
					estadoCivil=Sesion.VIUDO;
				
				if(getJRadioButton111().isSelected()) 
					sexo=Sesion.MASCULINO;
				else if(getJRadioButton51().isSelected())
					sexo=Sesion.FEMENINO;
					
				if(CR.meServ.getApartado() != null /*|| (CR.meServ.getApartado() == null && CR.meVenta.getDevolucion() == null)*/) {
						try {
							CR.meServ.asignarCliente(nombre,apellido,id,telf,codArea, direccion, telf2, codArea2, email, tipoCliente,true,sexo,estadoCivil,fechaNaci,zonaResidencial);
							this.dispose();
						} catch (ClienteExcepcion e1) {
							logger.error("keyPressed(KeyEvent)", e1);
							
							MensajesVentanas.aviso(e1.getMensaje());
						} catch (AfiliadoUsrExcepcion e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							MensajesVentanas.aviso(e1.getMensaje());
							this.dispose();
						} catch (BaseDeDatosExcepcion e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							MensajesVentanas.mensajeError(e1.getMensaje());
							this.dispose();
						} catch (ConexionExcepcion e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							MensajesVentanas.mensajeError(e1.getMensaje());
							this.dispose();
						} catch (UsuarioExcepcion e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							MensajesVentanas.mensajeError(e1.getMensaje());
							this.dispose();
						} catch (MaquinaDeEstadoExcepcion e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							MensajesVentanas.mensajeError(e1.getMensaje());
							this.dispose();
						} catch (ExcepcionCr e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							MensajesVentanas.mensajeError(e1.getMensaje());
							this.dispose();
						}
						//this.dispose();	
					}else if(CR.meServ.getListaRegalos() != null) {
							try {
								CR.meServ.asignarClienteLR(nombre,apellido,id,telf,codArea, direccion, telf2, codArea2, email, tipoCliente,true,sexo,estadoCivil,fechaNaci,zonaResidencial);
								this.dispose();
							} catch (ClienteExcepcion e1) {
								logger.error("keyPressed(KeyEvent)", e1);
							
								MensajesVentanas.aviso(e1.getMensaje());							
							} catch (AfiliadoUsrExcepcion e1) {
								logger.error("keyPressed(KeyEvent)", e1);
								MensajesVentanas.aviso(e1.getMensaje());
								this.dispose();
							} catch (BaseDeDatosExcepcion e1) {
								logger.error("keyPressed(KeyEvent)", e1);
								MensajesVentanas.mensajeError(e1.getMensaje());
								this.dispose();
							} catch (ConexionExcepcion e1) {
								logger.error("keyPressed(KeyEvent)", e1);
								MensajesVentanas.mensajeError(e1.getMensaje());
								this.dispose();
							} catch (UsuarioExcepcion e1) {
								logger.error("keyPressed(KeyEvent)", e1);
								MensajesVentanas.mensajeError(e1.getMensaje());
								this.dispose();
							} catch (MaquinaDeEstadoExcepcion e1) {
								logger.error("keyPressed(KeyEvent)", e1);
								MensajesVentanas.mensajeError(e1.getMensaje());
								this.dispose();
							} catch (ExcepcionCr e1) {
								logger.error("keyPressed(KeyEvent)", e1);
								MensajesVentanas.mensajeError(e1.getMensaje());
								this.dispose();
							}
							//this.dispose();
					}else {
						try {
							if (MensajesVentanas.preguntarSiNo("¿Desea registrar como afiliado a:\n" + nombre + " " + apellido + "?") == 0){
								CR.meServ.registrarAfiliado(nombre,apellido,id,telf,codArea, direccion, telf2, codArea2, email, tipoCliente,true,sexo,estadoCivil,fechaNaci,zonaResidencial);
								dispose();
							}
						} catch (MaquinaDeEstadoExcepcion e1) {
							MensajesVentanas.mensajeError(e1.getMensaje());
						} catch (ConexionExcepcion e1) {
							MensajesVentanas.mensajeError(e1.getMensaje());
						} catch (ExcepcionCr e1) {
							e1.printStackTrace();
						}
					}
					kfm.setDefaultFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forward);
					kfm.setDefaultFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backward);	
			}
		/*} else if (e.getSource().equals(this.getJButton2())){
			mantenimiento = null; 
			try {
				mantenimiento = new Mantenimiento(false);
				CR.meServ.iniciarMantenimiento();
				MensajesVentanas.centrarVentanaDialogo(mantenimiento);
				llenarComboEstados();
				llenarComboCiudades();
				llenarComboUrbanizaciones();
			} catch (MaquinaDeEstadoExcepcion e2) {
				MensajesVentanas.mensajeError(e2.getMensaje());
			} catch (XmlExcepcion e2) {
				e2.printStackTrace();
			} catch (FuncionExcepcion e2) {
				e2.printStackTrace();
			} catch (BaseDeDatosExcepcion e2) {
				e2.printStackTrace();
			} catch (ConexionExcepcion e2) {
				e2.printStackTrace();
			} catch (UsuarioExcepcion e2) {
				MensajesVentanas.mensajeError(e2.getMensaje());
			} catch (ExcepcionCr e2) {
				e2.printStackTrace();
			}
		}*/
		}else if (e.getSource().equals(this.getJButton3())){
				//Actualizar combo estados
				Sesion.cargarEstados();
				this.llenarComboEstados();
				//Limpiar combo ciudades
				this.llenarComboCiudades();
				//Limpiar combo urbanizaciones
				this.llenarComboUrbanizaciones();
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
			jPanel.add(getJPanel16(), null);
			jPanel.setPreferredSize(new java.awt.Dimension(650,285));
			jPanel.setBackground(new java.awt.Color(242,242,238));
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Datos del Cliente: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
			jPanel.add(getJPanel21(), null);
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
			layFlowLayout11.setHgap(5);
			layFlowLayout11.setVgap(0);
			jPanel2.setLayout(layFlowLayout11);
			//jPanel2.add(getJButton2(),null);
			jPanel2.add(getJButton3(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(getJButton(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(645,30));
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
	 * This method initializes jButton1
	 *	Botón de mantenimiento 
	 * @return javax.swing.JButton
	 *
	private javax.swing.JButton getJButton2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton2() - start");
		}

		if(jButton2 == null) {
			jButton2 = new JHighlightButton();
			jButton2.setText("Mantenimiento");
			jButton2.setBackground(new java.awt.Color(226,226,222));
			jButton2.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/server_earth.png")));
			jButton2.setVisible(false);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton2() - end");
		}
		return jButton2;
	}	
	*/
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
			jPanel3.setPreferredSize(new java.awt.Dimension(660,330));
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
			layFlowLayout2.setAlignment(java.awt.FlowLayout.CENTER);
			layFlowLayout2.setVgap(5);
			layFlowLayout2.setHgap(5);
			jPanel4.setLayout(layFlowLayout2);
			jPanel4.add(getJLabel1(), null);
			jPanel4.setPreferredSize(new java.awt.Dimension(730,40));
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
			jLabel121 = new JLabel();
			jLabel121.setPreferredSize(new Dimension(160, 10));
			jLabel121.setText("Estado Civil:");
			jLabel121.setHorizontalAlignment(SwingConstants.LEADING);
			jPanel1 = new javax.swing.JPanel();
			java.awt.GridLayout layGridLayout1 = new java.awt.GridLayout();
			layGridLayout1.setRows(8);
			layGridLayout1.setColumns(0);
			jPanel1.setLayout(layGridLayout1);
			jPanel1.add(getJLabel11(), null);
			
			jPanel1.setBackground(new java.awt.Color(242,242,238));
			jPanel1.setPreferredSize(new java.awt.Dimension(110,250));
			jPanel1.add(getJLabel5(), null);
			jPanel1.add(getJLabel2(), null);
			jPanel1.add(getJLabel7(), null);
			jPanel1.add(getJLabel9(), null);
			jPanel1.add(getJLabel3(), null);
			jPanel1.add(getJLabel12(), null);
			jPanel1.add(jLabel121, null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel1() - end");
		}
		return jPanel1;
	}
	
	
	
	  private javax.swing.JPanel getJPanel16() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel16() - start");
		}

		if(jPanel16 == null) {
			jLabel1011 = new JLabel();
			jLabel1011.setPreferredSize(new Dimension(160, 10));
			jLabel1011.setText("Sexo:");
			jLabel1011.setHorizontalAlignment(SwingConstants.LEADING);
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(8);
			gridLayout.setColumns(0);
			jLabel101 = new JLabel();
			jLabel101.setHorizontalAlignment(SwingConstants.LEADING);
			jLabel101.setText("Fecha de Nacimiento:");
			jLabel101.setPreferredSize(new Dimension(160, 10));
			
			jPanel16 = new javax.swing.JPanel();
			jPanel16.setLayout(gridLayout);
			
			jPanel16.setBackground(new java.awt.Color(242,242,238));
			jPanel16.setPreferredSize(new java.awt.Dimension(125,250));
			jPanel16.add(getJLabel(), null);
			jPanel16.add(getJLabel4(), null);
			jPanel16.add(getJLabel8(), null);
			jPanel16.add(getJLabel6(), null);
			jPanel16.add(getJLabel13(), null);
			jPanel16.add(getJLabel10(), null);
			jPanel16.add(jLabel101, null);
			jPanel16.add(jLabel1011, null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel16() - end");
		}
		return jPanel16;
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
			GridLayout gridLayout2 = new GridLayout();
			gridLayout2.setRows(8);
			jPanel5 = new javax.swing.JPanel();
			jPanel5.setLayout(gridLayout2);
			jPanel5.setBackground(new java.awt.Color(242,242,238));
			jPanel5.setPreferredSize(new java.awt.Dimension(200,250));
			jPanel5.add(getJPanel6(), null);
			jPanel5.add(getJPanel8(), null);
			jPanel5.add(getJPanel11(), null);
			jPanel5.add(getJPanel18(), null);
			jPanel5.add(getJPanel20(), null);
			jPanel5.add(getJPanel14(), null);
			jPanel5.add(getJPanel15(), null);
			jPanel5.add(getJPanel10(), null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel5() - end");
		}
		return jPanel5;
	}
	
	/**
		 * This method initializes jPanel5
		 * 
		 * @return javax.swing.JPanel
		 */
		private javax.swing.JPanel getJPanel21() {
			if (logger.isDebugEnabled()) {
				logger.debug("getJPanel21() - start");
			}

			if(jPanel21 == null) {
				jPanel21 = new javax.swing.JPanel();
				GridLayout gridLayout2 = new GridLayout();
				gridLayout2.setRows(8);
				jPanel21.setLayout(gridLayout2);
				jPanel21.setBackground(new java.awt.Color(242,242,238));
				jPanel21.setPreferredSize(new java.awt.Dimension(180,250));
				jPanel21.add(getJPanel7(), null);
				jPanel21.add(getJPanel13(), null);
				jPanel21.add(getJPanel17(), null);
				jPanel21.add(getJPanel19(), null);
				jPanel21.add(getJPanel12(), null);
				jPanel21.add(getJPanel9(), null);
				jPanel21.add(getJPanel91(), null);
				jPanel21.add(getJPanel611(), null);
			}

			if (logger.isDebugEnabled()) {
				logger.debug("getJPanel21() - end");
			}
			return jPanel21;
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
			jLabel2.setText("Estado: ");
			jLabel2.setPreferredSize(new java.awt.Dimension(160,10));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel2() - end");
		}
		return jLabel2;
	}

	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel7() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel() - start");
		}

		if(jLabel7 == null) {
			jLabel7 = new javax.swing.JLabel();
			jLabel7.setText("Urbanización:");
			jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
			jLabel7.setPreferredSize(new java.awt.Dimension(160,10));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel() - end");
		}
		return jLabel7;
	}

	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel8() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel8() - start");
		}

		if(jLabel8 == null) {
			jLabel8 = new javax.swing.JLabel();
			jLabel8.setText("Ciudad:");
			jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
			jLabel8.setPreferredSize(new java.awt.Dimension(160,10));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel8() - end");
		}
		return jLabel8;
	}	

	/**
		 * This method initializes jLabel
		 * 
		 * @return javax.swing.JLabel
		 */
		private javax.swing.JLabel getJLabel4() {
			if (logger.isDebugEnabled()) {
				logger.debug("getJLabel4() - start");
			}

			if(jLabel4 == null) {
				jLabel4 = new javax.swing.JLabel();
				jLabel4.setText("Apellido:");
				jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
				jLabel4.setPreferredSize(new java.awt.Dimension(160,10));
			}

			if (logger.isDebugEnabled()) {
				logger.debug("getJLabel4() - end");
			}
			return jLabel4;
		}	

	/**
		 * This method initializes jLabel
		 * 
		 * @return javax.swing.JLabel
		 */
		private javax.swing.JLabel getJLabel6() {
			if (logger.isDebugEnabled()) {
				logger.debug("getJLabel6() - start");
			}

			if(jLabel6 == null) {
				jLabel6 = new javax.swing.JLabel();
				jLabel6.setText("Calle:");
				jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
				jLabel6.setPreferredSize(new java.awt.Dimension(160,10));
			}

			if (logger.isDebugEnabled()) {
				logger.debug("getJLabel6() - end");
			}
			return jLabel6;
		}	

	/**
			 * This method initializes jLabel
			 * 
			 * @return javax.swing.JLabel
			 */
			private javax.swing.JLabel getJLabel13() {
				if (logger.isDebugEnabled()) {
					logger.debug("getJLabel13() - start");
				}

				if(jLabel13 == null) {
					jLabel13 = new javax.swing.JLabel();
					jLabel13.setText("# Apto./Casa:");
					jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
					jLabel13.setPreferredSize(new java.awt.Dimension(160,10));
				}

				if (logger.isDebugEnabled()) {
					logger.debug("getJLabel13() - end");
				}
				return jLabel13;
			}	



	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel9() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel9() - start");
		}

		if(jLabel9 == null) {
			jLabel9 = new javax.swing.JLabel();
			jLabel9.setText("Edificio:");
			jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
			jLabel9.setPreferredSize(new java.awt.Dimension(160,10));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel9() - end");
		}
		return jLabel9;
	}
	
	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel10() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel10() - start");
		}

		if(jLabel10 == null) {
			jLabel10 = new javax.swing.JLabel();
			jLabel10.setText("Teléfono Secundario:");
			jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
			jLabel10.setPreferredSize(new java.awt.Dimension(160,10));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel10() - end");
		}
		return jLabel10;
	}
	
	/**
		 * This method initializes jLabel11
		 * 
		 * @return javax.swing.JLabel
		 */
		private javax.swing.JLabel getJLabel11() {
			if (logger.isDebugEnabled()) {
				logger.debug("getJLabel11() - start");
			}

			if(jLabel11 == null) {
				jLabel11 = new javax.swing.JLabel();
				jLabel11.setText("Tipo Cliente:");
				jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
				jLabel11.setPreferredSize(new java.awt.Dimension(160,10));
			}

			if (logger.isDebugEnabled()) {
				logger.debug("getJLabel() - end");
			}
			return jLabel11;
		}
	
	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel12() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel12() - start");
		}

		if(jLabel12 == null) {
			jLabel12 = new javax.swing.JLabel();
			jLabel12.setText("Correo Electrónico:");
			jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
			jLabel12.setPreferredSize(new java.awt.Dimension(160,10));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel12() - end");
		}
		return jLabel12;
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
			jTextField2.setInputVerifier(new TextVerifier(11, "0123456789- "));
			jTextField2.setPreferredSize(new java.awt.Dimension(105,20));
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
			layFlowLayout4.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout4.setVgap(7);
			jPanel7.setLayout(layFlowLayout4);
			jPanel7.add(getJTextField(), null);
			jPanel7.setBackground(new java.awt.Color(242,242,238));
			jPanel7.setBounds(new Rectangle(0, 0, 180, 31));
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
			layFlowLayout5.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout5.setVgap(7);
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
			layFlowLayout11.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout11.setVgap(7);
			jPanel11.setLayout(layFlowLayout11);
			jPanel11.add(getJComboBox(), null);
			jPanel11.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel11() - end");
		}
		return jPanel11;
	}
	
	private javax.swing.JPanel getJPanel12() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel12() - start");
		}

		if(jPanel12 == null) {
			jPanel12 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout12 = new java.awt.FlowLayout();
			layFlowLayout12.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout12.setVgap(7);
			jPanel12.setLayout(layFlowLayout12);
			jPanel12.add(getJTextField8(), null);
			jPanel12.setBackground(new java.awt.Color(242,242,238));
			jPanel12.setBounds(new Rectangle(0, 124, 180, 31));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel12() - end");
		}
		return jPanel12;
	}
	private javax.swing.JPanel getJPanel17() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel17() - start");
		}

		if(jPanel17 == null) {
			jPanel17 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout17 = new java.awt.FlowLayout();
			layFlowLayout17.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout17.setVgap(7);
			jPanel17.setLayout(layFlowLayout17);
			jPanel17.add(getJComboBox1(), null);
			jPanel17.setBackground(new java.awt.Color(242,242,238));
			jPanel17.setBounds(new Rectangle(0, 62, 180, 31));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel17() - end");
		}
		return jPanel17;
	}	
	private javax.swing.JPanel getJPanel18() {
			if (logger.isDebugEnabled()) {
				logger.debug("getJPanel18() - start");
			}

			if(jPanel18 == null) {
				jPanel18 = new javax.swing.JPanel();
				java.awt.FlowLayout layFlowLayout18 = new java.awt.FlowLayout();
				layFlowLayout18.setAlignment(java.awt.FlowLayout.LEFT);
				layFlowLayout18.setVgap(7);
				jPanel18.setLayout(layFlowLayout18);
				jPanel18.add(getJComboBox2(), null);
				jPanel18.setBackground(new java.awt.Color(242,242,238));
			}

			if (logger.isDebugEnabled()) {
				logger.debug("getJPanel18() - end");
			}
			return jPanel18;
		}	
/**
	 * This method initializes jPanel8
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel15() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel15() - start");
		}

		if(jPanel15 == null) {
			jPanel15 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout15 = new java.awt.FlowLayout();
			layFlowLayout15.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout15.setVgap(7);
			jPanel15.setLayout(layFlowLayout15);
			jPanel15.add(getJTextField12(), null);
			jPanel15.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel15() - end");
		}
		return jPanel15;
	}
	
	/*
	 * This method initializes jPanel7
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel13() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel13() - start");
		}

		if(jPanel13 == null) {
			jPanel13 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout13 = new java.awt.FlowLayout();
			layFlowLayout13.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout13.setVgap(7);
			jPanel13.setLayout(layFlowLayout13);
			jPanel13.add(getJTextField6(), null);
			jPanel13.setBackground(new java.awt.Color(242,242,238));
			jPanel13.setBounds(new Rectangle(0, 31, 180, 31));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel13() - end");
		}
		return jPanel13;
	}
	 
	
	/*
		 * This method initializes jPanel7
		 * 
		 * @return javax.swing.JPanel
		 */
		private javax.swing.JPanel getJPanel19() {
			if (logger.isDebugEnabled()) {
				logger.debug("getJPanel19() - start");
			}

			if(jPanel19 == null) {
				jPanel19 = new javax.swing.JPanel();
				java.awt.FlowLayout layFlowLayout19 = new java.awt.FlowLayout();
				layFlowLayout19.setAlignment(java.awt.FlowLayout.LEFT);
				layFlowLayout19.setVgap(7);
				jPanel19.setLayout(layFlowLayout19);
				jPanel19.add(getJTextField4(), null);
				jPanel19.setBackground(new java.awt.Color(242,242,238));
				jPanel19.setBounds(new Rectangle(0, 93, 180, 31));
			}

			if (logger.isDebugEnabled()) {
				logger.debug("getJPanel9() - end");
			}
			return jPanel19;
		}
	 
	/*
		 * This method initializes jPanel7
		 * 
		 * @return javax.swing.JPanel
		 */
		private javax.swing.JPanel getJPanel20() {
			if (logger.isDebugEnabled()) {
				logger.debug("getJPanel20() - start");
			}

			if(jPanel20 == null) {
				jPanel20 = new javax.swing.JPanel();
				java.awt.FlowLayout layFlowLayout20 = new java.awt.FlowLayout();
				layFlowLayout20.setAlignment(java.awt.FlowLayout.LEFT);
				layFlowLayout20.setVgap(7);
				jPanel20.setLayout(layFlowLayout20);
				jPanel20.add(getJTextField5(), null);
				jPanel20.setBackground(new java.awt.Color(242,242,238));
			}

			if (logger.isDebugEnabled()) {
				logger.debug("getJPanel20() - end");
			}
			return jPanel20;
		}
		
	private void llenarComboEstados()
	{
		jComboBox.removeActionListener(this);
		this.jComboBox.removeAllItems();
		this.jComboBox.addItem("Seleccione uno");
		if(Sesion.estados != null)
			for (int i = 0; i < Sesion.estados.size(); i++)
				jComboBox.addItem(((Estado)Sesion.estados.elementAt(i)).getDescripcion());
		jComboBox.addActionListener(this);
		Sesion.ciudades = null;
		Sesion.urbanizaciones = null;
	}
	 
	
	/**
	 * @return jComboBox
	 */
	private Component getJComboBox() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJComboBox() - start");
		}

		if(jComboBox == null) {
			jComboBox = new JHighlightComboBox();
			jComboBox.setPreferredSize(new java.awt.Dimension(150,20));
			jComboBox.setBackground(new java.awt.Color(226,226,222));
			jComboBox.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jComboBox.addItem("Seleccione uno");
			if(Sesion.estados != null)
				for (int i = 0; i < Sesion.estados.size(); i++)
					jComboBox.addItem(((Estado)Sesion.estados.elementAt(i)).getDescripcion());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJComboBox() - end");
		}
		return jComboBox;

	}

	/**
	 * @return jComboBox
	 */
	private Component getJComboBox1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJComboBox1() - start");
		}

		if(jComboBox1 == null) {
			jComboBox1 = new JHighlightComboBox();
			jComboBox1.setPreferredSize(new java.awt.Dimension(150,20));
			jComboBox1.setBackground(new java.awt.Color(226,226,222));
			jComboBox1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jComboBox1.addItem("Seleccione uno");
			
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("getJComboBox() - end");
		}
		return jComboBox1;

	}
	
	private void llenarComboCiudades()
	{
		if(jComboBox1 == null) {
			jComboBox1.addItem("Seleccione uno");
		}
		else
		{
			jComboBox1.removeActionListener(this);
			jComboBox2.removeActionListener(this);
			jComboBox1.removeAllItems();
			jComboBox2.removeAllItems();
			jComboBox1.addItem("Seleccione uno");
			jComboBox2.addItem("Seleccione uno");
			if(Sesion.ciudades != null)
				for (int i = 0; i < Sesion.ciudades.size(); i++)
					jComboBox1.addItem(((Ciudad)Sesion.ciudades.elementAt(i)).getDescripcion());
			jComboBox1.addActionListener(this);
			jComboBox2.addActionListener(this);
		}

	}
	
	/**
	 * @return jComboBox
	 */
	private Component getJComboBox2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJComboBox2() - start");
		}

		if(jComboBox2 == null) {
			jComboBox2 = new JHighlightComboBox();
			jComboBox2.setPreferredSize(new java.awt.Dimension(150,20));
			jComboBox2.setBackground(new java.awt.Color(226,226,222));
			jComboBox2.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jComboBox2.addItem("Seleccione uno");
			
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJComboBox2() - end");
		}
		return jComboBox2;

	}
	
	private void llenarComboUrbanizaciones()
	{
		if(jComboBox2 == null) {
			jComboBox2.addItem("Seleccione uno");
		}
		else
		{
			jComboBox2.removeActionListener(this);
			jComboBox2.removeAllItems();
			jComboBox2.addItem("Seleccione uno");
			if(Sesion.urbanizaciones != null)
				for (int i = 0; i < Sesion.urbanizaciones.size(); i++)
					jComboBox2.addItem(((Urbanizacion)Sesion.urbanizaciones.elementAt(i)).getDescripcion());
			jComboBox2.addActionListener(this);
		}

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
			layFlowLayout6.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout6.setVgap(7);
			jPanel9.setLayout(layFlowLayout6);
			jPanel9.add(getJTextField3(), null);
			jPanel9.add(getJTextField2(), null);
			jPanel9.setBackground(new java.awt.Color(242,242,238));
			jPanel9.setBounds(new Rectangle(0, 155, 180, 31));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel9() - end");
		}
		return jPanel9;
	}
	
		
	/**
	 * This method initializes jPanel14
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel14() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel14() - start");
		}

		if(jPanel14 == null) {
			jPanel14 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout9 = new java.awt.FlowLayout();
			layFlowLayout9.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout9.setVgap(7);
			jPanel14.setLayout(layFlowLayout9);
			jPanel14.add(getJTextField10(), null);
			jPanel14.add(getJTextField11(), null);
			jPanel14.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel14() - end");
		}
		return jPanel14;
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
 * This method initializes jTextField1
 * 
 * @return javax.swing.JTextField
 */
private javax.swing.JTextField getJTextField8() {
	if (logger.isDebugEnabled()) {
		logger.debug("getJTextField8() - start");
	}

	if(jTextField8 == null) {
		jTextField8 = new UpperCaseField();
		jTextField8.setPreferredSize(new java.awt.Dimension(150,20));
		int maxLength = 30;
		jTextField8.setInputVerifier(new TextVerifier(maxLength, " .ABCDEFGHIJKLMNÑOPQRSTUVWXYZÁÉÍÓÚÜ'0123456789-º#"));
	}

	if (logger.isDebugEnabled()) {
		logger.debug("getJTextField8() - end");
	}
	return jTextField8;
}

/**
 * This method initializes jTextField1
 * 
 * @return javax.swing.JTextField
 */
private javax.swing.JTextField getJTextField4() {
	if (logger.isDebugEnabled()) {
		logger.debug("getJTextField4() - start");
	}

	if(jTextField4 == null) {
		jTextField4 = new UpperCaseField();
		jTextField4.setPreferredSize(new java.awt.Dimension(150,20));
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
		logger.debug("getJTextField4() - end");
	}
	return jTextField4;
}
	
	
/**
 * This method initializes jTextField1
 * 
 * @return javax.swing.JTextField
 */
private javax.swing.JTextField getJTextField5() {
	if (logger.isDebugEnabled()) {
		logger.debug("getJTextField5() - start");
	}

	if(jTextField5 == null) {
		jTextField5 = new UpperCaseField();
		jTextField5.setPreferredSize(new java.awt.Dimension(150,20));
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
		jTextField5.setInputVerifier(new TextVerifier(maxLength, " .ABCDEFGHIJKLMNÑOPQRSTUVWXYZÁÉÍÓÚÜ#°&\"'/$!,-@%+;:?¡¿!0123456789"));
	}

	if (logger.isDebugEnabled()) {
		logger.debug("getJTextField1() - end");
	}
	return jTextField5;
}
	
	
/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField12() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField12() - start");
		}

		if(jTextField12 == null) {
			jTextField12 = new UpperCaseField();
			jTextField12.setPreferredSize(new java.awt.Dimension(150,20));
			jTextField12.setText("@");
			int maxLength = 50;
			jTextField12.setInputVerifier(new TextVerifier(maxLength, ".ABCDEFGHIJKLMNÑOPQRSTUVWXYZÁÉÍÓÚÜ@_0123456789-"));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField12() - end");
		}
		return jTextField12;
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
			jTextField3.setInputVerifier(new TextVerifier(4, "0123456789 "));
			jTextField3.setPreferredSize(new java.awt.Dimension(40,20));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField3() - end");
		}
		return jTextField3;
	}
	
	
	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField10() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField10() - start");
		}

		if(jTextField10 == null) {
			jTextField10 = new javax.swing.JTextField();
			jTextField10.setInputVerifier(new TextVerifier(4, "0123456789 "));
			jTextField10.setPreferredSize(new java.awt.Dimension(40,20));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField10() - end");
		}
		return jTextField10;
	}
	
	
	/**
		 * This method initializes jTextField2
		 * 
		 * @return javax.swing.JTextField
		 */
		private javax.swing.JTextField getJTextField11() {
			if (logger.isDebugEnabled()) {
				logger.debug("getJTextField11() - start");
			}

			if(jTextField11 == null) {
				jTextField11 = new javax.swing.JTextField();
				jTextField11.setInputVerifier(new TextVerifier(11, "0123456789- "));
				jTextField11.setPreferredSize(new java.awt.Dimension(105,20));
			}

			if (logger.isDebugEnabled()) {
				logger.debug("getJTextField11() - end");
			}
			return jTextField11;
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
			jLabel3.setPreferredSize(new java.awt.Dimension(160,8));
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
			jLabel5.setText("Nombre:");
			jLabel5.setPreferredSize(new java.awt.Dimension(160,10));
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
		 * This method initializes jRadioButton3
		 * 
		 * @return javax.swing.JRadioButton
		 */
		private javax.swing.JRadioButton getJRadioButton4() {
			if (logger.isDebugEnabled()) {
				logger.debug("getJRadioButton4() - start");
			}

			if(jRadioButton4 == null) {
				jRadioButton4 = new javax.swing.JRadioButton();
				jRadioButton4.setBackground(new java.awt.Color(242,242,238));
				jRadioButton4.setText("P");
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
		if (e.getSource().equals(jTextField)){

		//Verificamos si existe un cliente temporal con este identificador
		String id = getJTextField().getText();
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
		else{
		try {
			if (id.length() > 0) {
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
								
					getJTextField().setText(ctemp.getCodCliente().substring(2));
					getJTextField1().setText(ctemp.getNombre());
					getJTextField6().setText(ctemp.getApellido());
					getJTextField4().setText(ctemp.getAvCalle());
					getJTextField5().setText(ctemp.getEdfCasa());
					getJTextField8().setText(ctemp.getNroApto());
					getJTextField11().setText(ctemp.getNumTelefono());
					getJTextField10().setText(ctemp.getCodArea());
					getJTextField2().setText(ctemp.getNumTelefonoSec());
					getJTextField3().setText(ctemp.getCodAreaSec());
					getJTextField12().setText(ctemp.getEmail());
					
					//se esta realizando la carga de la consulta de cliente si existe.. nuevos datos
					 if (ctemp.getEstadoCivil() == Sesion.CASADO)
						getJRadioButton5().setSelected(true);
					else if (ctemp.getEstadoCivil() == Sesion.DIVORCIADO)
						getJRadioButton21().setSelected(true);
					else if (ctemp.getEstadoCivil() == Sesion.VIUDO)
						getJRadioButton31().setSelected(true);
					else getJRadioButton11().setSelected(true);
					
					if (ctemp.getSexo()==Sesion.FEMENINO)
						getJRadioButton51().setSelected(true);
					else
					if (ctemp.getSexo()==Sesion.MASCULINO)
						getJRadioButton111().setSelected(true);
					
					fechaNacimiento.setDateFormatString("dd-MM-yyyy");
					fechaNacimiento.setDate(ctemp.getFechaNaci());
					
					
					//fin de la carga de los nuevos datos
					
					
								
					int posicion;
					if (!ctemp.getCodEdo().equals("")){
						posicion = seleccionarEstado(ctemp.getCodEdo());
						if (posicion > -1) 
						{
							jComboBox.setSelectedIndex(posicion);
							if (!ctemp.getCodCiu().equals(""))
							{
								posicion = seleccionarCiudad(ctemp.getCodCiu());
								if (posicion > -1){ 
									jComboBox1.setSelectedIndex(posicion);
									if (!ctemp.getCodUrb().equals("")){
										posicion = seleccionarUrbanizacion(ctemp.getCodUrb());
										if (posicion > -1) 
											jComboBox2.setSelectedIndex(posicion);
									}
								}	
							}
						}
					}
				}
			}
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
			//Realizado por WDiaz.. Limpia la pantalla si no existe el cliente
			MensajesVentanas.mensajeError(e1.getMensaje());
			LimpiarPantalla();
		}

		} 
		if (logger.isDebugEnabled()) {
			logger.debug("focusLost(FocusEvent) - end");
		}
	}
		else
			if (e.getSource().equals(jTextField12))
			{
				TextVerifier veri= new TextVerifier();
				veri.verify1(jTextField12);
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
				if (((JRadioButton)e.getSource()).getText().equals("J") || 
						((JRadioButton)e.getSource()).getText().equals("G")) {
					jLabel4.setEnabled(false);
					jTextField6.setEnabled(false);
				} else {
					jLabel4.setEnabled(true);
					jTextField6.setEnabled(true);
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
	
	
	private int seleccionarEstado (String codigo)
	{

		for (int i = 0; i < Sesion.estados.size(); i++)
		{
			try{
				if (((Estado)Sesion.estados.elementAt(i)).getCodigo() == Integer.parseInt(codigo))
				{
					return (i + 1);
				}
			}catch (NumberFormatException nfe){
				return 0;
			}
			
		}
		return -1;
	}
	
	private int seleccionarCiudad (String codigo)
	{
		
		for (int i = 0; i < Sesion.ciudades.size(); i++)
		{
			try{
				if (((Ciudad)Sesion.ciudades.elementAt(i)).getCodigo() == Integer.parseInt(codigo))
				{
					return (i + 1);
				}
			}catch (NumberFormatException nfe){
				return 0;
			}
		}
		return -1;
	}

	private int seleccionarUrbanizacion (String codigo)
	{
		
		for (int i = 0; i < Sesion.urbanizaciones.size(); i++)
		{
			try{
				if (((Urbanizacion)Sesion.urbanizaciones.elementAt(i)).getCodigo() == Integer.parseInt(codigo))
				{
					return (i + 1);
				}
			}catch (NumberFormatException nfe){
				return 0;
			}
		}
		return -1;
	}		

	
	/**
	 * Método actionPerformed
	 *
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("actionPerformed(ActionEvent) - start");
		}

		if (e.getSource().equals(jComboBox)) {
			// Aqui viene el cambio de tipo de busqueda
			int consultaEdo = jComboBox.getSelectedIndex();
			Sesion.cargarCiudades(consultaEdo - 1);
			llenarComboCiudades();

		} 
		
		if (e.getSource().equals(jComboBox1)) {
			// Aqui viene el cambio de tipo de busqueda
			int consultaEdo = jComboBox.getSelectedIndex();
			int consultaCiu = jComboBox1.getSelectedIndex();
			
			Sesion.cargarUrbanizaciones(consultaEdo - 1, consultaCiu - 1);
			llenarComboUrbanizaciones();
		} 
		
		if (logger.isDebugEnabled()) {
			logger.debug("actionPerformed(ActionEvent) - end");
		}
	}
	

	
	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton3() {
		if(jButton3 == null) {
			jButton3 = new JHighlightButton();
			jButton3.setText("Actualizar");
			jButton3.setBackground(new java.awt.Color(226,226,222));
			jButton3.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/server_earth.png")));
		}
		return jButton3;
	}



	/**
	 * This method initializes fechaNacimiento	
	 * 	
	 * @return com.toedter.calendar.JDateChooser	
	 */
	private JDateChooser getfechaNacimiento() {
		if (fechaNacimiento == null) {
			//Date date = getFecha().getTime();
			fechaNacimiento = new JDateChooser();
			fechaNacimiento.setPreferredSize(new Dimension(200, 25));
			//fechaNacimiento.setDateFormatString("dd-MM-yyyy");
			fechaNacimiento.setFont(new Font("Dialog", Font.BOLD, 12));
			fechaNacimiento.setBounds(new Rectangle(4, 4, 151, 22));
			//fechaNacimiento.setDate(date);
			fechaNacimiento.setBackground(new Color(226, 226, 222));
		}
		return fechaNacimiento;
	}



	/**
	 * This method initializes fecha	
	 * 	
	 * @return java.util.GregorianCalendar	
	 */
	@SuppressWarnings("unused")
	private GregorianCalendar getFecha() {
		if (fecha == null) {
			fecha = (GregorianCalendar) Calendar.getInstance();
		}
		return fecha;
	}



	/**
	 * This method initializes jPanel91	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel91() {
		if (jPanel91 == null) {
			jPanel91 = new JPanel();
			jPanel91.setBounds(new Rectangle(0, 186, 180, 31));
			jPanel91.setLayout(null);
			jPanel91.setBackground(new Color(242, 242, 238));
			jPanel91.add(getfechaNacimiento(), null);
		}
		return jPanel91;
	}



	/**
	 * This method initializes jPanel61	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel61() {
		if (jPanel61 == null) {
			jPanel61 = new JPanel();
			java.awt.FlowLayout layFlowLayout15 = new java.awt.FlowLayout();
			layFlowLayout15.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel61.setLayout(layFlowLayout15);
			jPanel61.setBackground(new Color(242, 242, 238));
			jPanel61.add(getJRadioButton11(), null);
			jPanel61.add(getJRadioButton5(), null);
			jPanel61.add(getJRadioButton21(), null);
			jPanel61.add(getJRadioButton31(), null);
			ButtonGroup group = new ButtonGroup();
		 	group.add(getJRadioButton11());
		 	group.add(getJRadioButton5());
			group.add(getJRadioButton21());
			group.add(getJRadioButton31());
		}
		return jPanel61;
	}



	/**
	 * This method initializes jRadioButton11	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton11() {
		if (jRadioButton11 == null) {
			jRadioButton11 = new JRadioButton();
			jRadioButton11.setBackground(new Color(242, 242, 238));
			jRadioButton11.setText("S");
			jRadioButton11.setSelected(true);
		}
		return jRadioButton11;
	}



	/**
	 * This method initializes jRadioButton5	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton5() {
		if (jRadioButton5 == null) {
			jRadioButton5 = new JRadioButton();
			jRadioButton5.setBackground(new Color(242, 242, 238));
			jRadioButton5.setText("C");
		}
		return jRadioButton5;
	}



	/**
	 * This method initializes jRadioButton21	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton21() {
		if (jRadioButton21 == null) {
			jRadioButton21 = new JRadioButton();
			jRadioButton21.setBackground(new Color(242, 242, 238));
			jRadioButton21.setText("D");
		}
		return jRadioButton21;
	}



	/**
	 * This method initializes jRadioButton31	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton31() {
		if (jRadioButton31 == null) {
			jRadioButton31 = new JRadioButton();
			jRadioButton31.setBackground(new Color(242, 242, 238));
			jRadioButton31.setText("V");
		}
		return jRadioButton31;
	}



	/**
	 * This method initializes jPanel611	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel611() {
		if (jPanel611 == null) {
			FlowLayout layFlowLayout151 = new FlowLayout();
			layFlowLayout151.setAlignment(FlowLayout.LEFT);
			jPanel611 = new JPanel();
			jPanel611.setLayout(layFlowLayout151);
			jPanel611.setBounds(new Rectangle(1, 218, 156, 29));
			jPanel611.setBackground(new Color(242, 242, 238));
			jPanel611.add(getJRadioButton111(), null);
			jPanel611.add(getJRadioButton51(), null);
			ButtonGroup group = new ButtonGroup();
		 	group.add(getJRadioButton111());
		 	group.add(getJRadioButton51());
		}
		return jPanel611;
	}



	/**
	 * This method initializes jRadioButton111	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton111() {
		if (jRadioButton111 == null) {
			jRadioButton111 = new JRadioButton();
			jRadioButton111.setBackground(new Color(242, 242, 238));
			jRadioButton111.setText("M");
		}
		return jRadioButton111;
	}



	/**
	 * This method initializes jRadioButton51	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButton51() {
		if (jRadioButton51 == null) {
			jRadioButton51 = new JRadioButton();
			jRadioButton51.setBackground(new Color(242, 242, 238));
			jRadioButton51.setText("F");
			jRadioButton51.setSelected(true);
		}
		return jRadioButton51;
	}



	/**
	 * This method initializes jPanel10	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel10() {
		if (jPanel10 == null) {
			jPanel10 = new JPanel();
			java.awt.FlowLayout layFlowLayout15 = new java.awt.FlowLayout();
			layFlowLayout15.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout15.setVgap(1);
			jPanel10.setLayout(layFlowLayout15);
			jPanel10.setBackground(new java.awt.Color(242,242,238));
			jPanel10.add(getJPanel61(), null);
		}
		return jPanel10;
	}



	public void LimpiarPantalla()
	{
//		Realizado por Wdiaz... 18/09/2009
		fechaNacimiento.setCalendar(new GregorianCalendar());
		getJTextField1().setText("");
		getJTextField2().setText("");
		getJTextField3().setText("");
		getJTextField4().setText("");
		getJTextField5().setText("");
		getJTextField6().setText("");
		getJTextField8().setText("");
		getJTextField10().setText("");
		getJTextField11().setText("");
		getJTextField12().setText("");
		
		
		
	//fin
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="10,11"
