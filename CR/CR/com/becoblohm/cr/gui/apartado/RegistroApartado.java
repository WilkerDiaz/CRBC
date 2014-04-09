/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : RegistroApartado.java
 * Creado por : Gabriel Martinelli	<gmartinelli@beco.com.ve>
 * Creado en  : Abr 12, 2004 - 9:22:10 AM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 2.0
 * Fecha       : 20/06/2006 07:32 PM
 * Analista    : yzambrano
 * Descripción : - Asignar cliente a un apartado. Pantalla de registro de cliente nuevo
 * 				 y verificación de datos. 
 * 				 - Colocar apartado en espera 
 * =============================================================================
 * Versión     : 1.0.0
 * Fecha       : 02/09/2004 07:32 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Refactoring del panel de iconos y de mensajes.
 * =============================================================================
 */
package com.becoblohm.cr.gui.apartado;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.AfiliadoUsrExcepcion;
import com.becoblohm.cr.excepciones.AutorizacionExcepcion;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.extensiones.ActualizadorPrecios;
import com.becoblohm.cr.extensiones.ActualizadorPreciosFactory;
import com.becoblohm.cr.extensiones.ActualizadorPreciosSACFactory;
import com.becoblohm.cr.gui.CambiarCantidad;
import com.becoblohm.cr.gui.Consultas;
import com.becoblohm.cr.gui.EjecutarConCantidad;
import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.gui.PanelIconosCR;
import com.becoblohm.cr.gui.PanelMensajesCR;
import com.becoblohm.cr.gui.RegistroClientesNuevos;
import com.becoblohm.cr.gui.Utilitarios;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstado;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarservicio.DetalleServicio;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.perifericos.EfectuarLecturaEscaner;
import com.becoblohm.cr.utiles.Control;
import com.becoblohm.cr.utiles.MensajesVentanas;

/**
 * @author gmartinelli
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RegistroApartado extends JDialog implements ActionListener, MouseListener, KeyListener, DocumentListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(RegistroApartado.class);

	private ModeloTablaApartado modeloTablaDetalleApartado = new ModeloTablaApartado(1);
	private DecimalFormat df = new DecimalFormat("#,##0.00");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	private int filaAModificar=0;
	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel14 = null;
	private javax.swing.JScrollPane jScrollPane = null;
	private javax.swing.JTable jTable = null;
	private javax.swing.JPanel jPanel15 = null;
	private javax.swing.JPanel jPanel16 = null;
	private javax.swing.JLabel jLabel9 = null;
	private javax.swing.JPanel jPanel18 = null;
	private javax.swing.JLabel jLabel10 = null;
	private javax.swing.JPanel jPanel19 = null;
	private javax.swing.JLabel jLabel11 = null;
	private javax.swing.JTextField jTextField4 = null;
	private javax.swing.JLabel jLabel12 = null;
	private javax.swing.JTextField jTextField5 = null;
	private javax.swing.JLabel jLabel13 = null;
	private javax.swing.JTextField jTextField6 = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton3 = null;
	private javax.swing.JButton jButton4 = null;
	private javax.swing.JButton jButton5 = null;
	private javax.swing.JButton jButton6 = null;
	private javax.swing.JButton jButton7 = null;
	private javax.swing.JButton jButton9 = null;
	private javax.swing.JButton jButton10 = null;
	private javax.swing.JButton jButton8 = null;
	private javax.swing.JButton jButton11 = null;
	private javax.swing.JButton jButton12 = null;
	private javax.swing.JButton jButton13 = null;
	private javax.swing.JButton jButton14 = null;
	private javax.swing.JLabel jLabel17 = null;
	private javax.swing.JComboBox jComboBox = null;
	private javax.swing.JLabel jLabel18 = null;
	private EfectuarLecturaEscaner lector = null;

	private static RegistroClientesNuevos registroCliente = null;
	private static EjecutarConCantidad ec = null;
	private boolean eventoDeIngresoProducto = false;
	private boolean ejecutandoEvento = false;
	private boolean escanerActivo = true;
	
	/**
	 * This is the default constructor
	 */
	public RegistroApartado() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		super(MensajesVentanas.ventanaActiva);
		//Modificación para apartados especiales, este codigo ahora se ejecuta en la ventana de servicios que llama a esta
		/*
		 if(CR.meServ.getApartado() == null)
			CR.meServ.crearApartado();
		*/
		/*else
			CR.meServ.getApartado().setCodCajero(Sesion.usuarioActivo.getNumFicha());*/
		
		initialize();

		this.setContentPane(getJContentPane());
			
		//Se verifica de las preferencias(variable cargada en apartado) si se pueden hacer rebajas
		if(CR.meServ.getApartado().isPermiteRebajas()) {
			jButton4.addKeyListener(this);
			jButton4.addMouseListener(this);
			jButton4.setEnabled(true);
		} else {
			jButton4.setEnabled(false);
		}
		
		if (jComboBox!=null) {
			jComboBox.addKeyListener(this);
			jComboBox.addActionListener(this);
		}
		
		jButton12.setEnabled(false);
		
		agregarListeners();
		
		CR.inputEscaner.getDocument().addDocumentListener(this);
		this.repintarFactura();
		if(CR.meServ.getApartado().getTipoApartado()!=0)
			CR.me.mostrarAviso("Apartado de tipo \""+CR.meServ.getApartado().getDescripcionTipoApartado()+"\" " +
					", vence el "+dateFormat.format(CR.meServ.getApartado().getFechaVencimiento()),true);
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

		this.setContentPane(getJContentPane());
		this.setSize(810,600);
		this.setName("FramePrincipalApartado");
		this.setVisible(false);
		this.setTitle("CR - Registro de Apartados");
		this.setResizable(false);
		this.setLocale(new java.util.Locale("es", "VE", ""));
		this.setModal(true);
		this.setBackground(new java.awt.Color(226,226,222));
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setUndecorated(true);
		
		if (logger.isDebugEnabled()) {
			logger.debug("initialize() - end");
		}
	}	

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJContentPane() - start");
		}

		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER,1,1));
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJPanel1(), null);
			jContentPane.setName("AreaDeFacturacion");
			jContentPane.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jContentPane.setPreferredSize(new java.awt.Dimension(810,600));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJContentPane() - end");
		}
		return jContentPane;
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
			jPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER,1,1));
			jPanel.add(PanelIconosCR.getPanelLogo(), null);
			jPanel.add(PanelIconosCR.getPanelIconos(), null);
			jPanel.add(getJPanel14(), null);
			jPanel.add(getJPanel15(), null);
			jPanel.add(getJPanel16(), null);
			jPanel.add(PanelMensajesCR.getAreaMensajes(), null);
			jPanel.add(PanelMensajesCR.getPanelEstadoCaja(), null);
			jPanel.add(PanelMensajesCR.getPanelStatus(), null);
			jPanel.setName("ContenedorPrincipalIzquierdo");
			jPanel.setPreferredSize(new java.awt.Dimension(660,565));
			jPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
			jPanel.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel() - end");
		}
		return jPanel;
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
			jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER,1,1));
			jPanel1.add(getJButton3(), null);
			jPanel1.add(getJButton5(), null);
			jPanel1.add(getJButton4(), null);
			jPanel1.add(getJButton6(), null);
			jPanel1.add(getJButton7(), null);
			jPanel1.add(getJButton9(), null);
			jPanel1.add(getJButton10(), null);
			jPanel1.add(getJButton8(), null);
			jPanel1.add(getJButton11(), null);
			jPanel1.add(getJButton12(), null);
			jPanel1.add(getJButton13(), null);
			jPanel1.add(getJButton14(), null);
			jPanel1.add(getJButton(), null);
			jPanel1.setPreferredSize(new java.awt.Dimension(130,565));
			jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
			jPanel1.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel1() - end");
		}
		return jPanel1;
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
			java.awt.FlowLayout layFlowLayout16 = new java.awt.FlowLayout();
			layFlowLayout16.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout16.setHgap(1);
			layFlowLayout16.setVgap(1);
			jPanel14.setLayout(layFlowLayout16);
			jPanel14.add(getJScrollPane(), null);
			jPanel14.setPreferredSize(new java.awt.Dimension(656,250));
			jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle de Apartado: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), java.awt.Color.black));
			jPanel14.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel14() - end");
		}
		return jPanel14;
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
			jTable = new javax.swing.JTable(modeloTablaDetalleApartado);
			jTable.getTableHeader().setReorderingAllowed(false) ;
			jTable.getColumnModel().getColumn(0).setPreferredWidth(88);
			jTable.getColumnModel().getColumn(1).setPreferredWidth(150);
			jTable.getColumnModel().getColumn(2).setPreferredWidth(65);
			jTable.getColumnModel().getColumn(3).setPreferredWidth(75);
			jTable.getColumnModel().getColumn(4).setPreferredWidth(75);
			jTable.getColumnModel().getColumn(5).setPreferredWidth(76);
			jTable.setLocale(new java.util.Locale("es", "VE", ""));
			jTable.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jTable.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTable() - end");
		}
		return jTable;
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
			jScrollPane.setViewportView(getJTable());
			jScrollPane.setPreferredSize(new java.awt.Dimension(645,225));
			jScrollPane.setBackground(new java.awt.Color(242,242,238));
			jScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,5,0,0));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJScrollPane() - end");
		}
		return jScrollPane;
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

		if(jPanel15 == null) {
			jPanel15 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout17 = new java.awt.FlowLayout();
			layFlowLayout17.setAlignment(java.awt.FlowLayout.CENTER);
			layFlowLayout17.setVgap(10);
			jPanel15.setLayout(layFlowLayout17);
			jPanel15.add(getJLabel17(), null);
			if (CR.meServ.getApartado().getTiposDeAbonosInicial()>1)
				jPanel15.add(getJComboBox(), null);
			else
				jPanel15.add(getJLabel18(), null);
			jPanel15.add(getJLabel9(), null);
			jPanel15.setPreferredSize(new java.awt.Dimension(328,150));
			jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Monto Abono Inicial: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), java.awt.Color.black));
			jPanel15.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel15() - end");
		}
		return jPanel15;
	}
	/**
	 * This method initializes jPanel16
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel16() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel16() - start");
		}

		if(jPanel16 == null) {
			jPanel16 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout18 = new java.awt.FlowLayout();
			layFlowLayout18.setHgap(1);
			layFlowLayout18.setVgap(1);
			jPanel16.setLayout(layFlowLayout18);
			jPanel16.add(getJPanel18(), null);
			jPanel16.add(getJPanel19(), null);
			jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Totales: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), null));
			jPanel16.setPreferredSize(new java.awt.Dimension(328,150));
			jPanel16.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel16() - end");
		}
		return jPanel16;
	}
	/**
	 * This method initializes jLabel9
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel9() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel9() - start");
		}

		if(jLabel9 == null) {
			jLabel9 = new javax.swing.JLabel();
			jLabel9.setText("Abono Inicial: "+Sesion.getTienda().getMonedaBase()+" 0,00");
			jLabel9.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel9.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
			jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel9() - end");
		}
		return jLabel9;
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

		if(jPanel18 == null) {
			jPanel18 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout23 = new java.awt.FlowLayout();
			layFlowLayout23.setHgap(1);
			layFlowLayout23.setVgap(1);
			jPanel18.setLayout(layFlowLayout23);
			jPanel18.add(getJLabel10(), null);
			jPanel18.setPreferredSize(new java.awt.Dimension(320,25));
			jPanel18.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel18() - end");
		}
		return jPanel18;
	}
	/**
	 * This method initializes jLabel10
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel10() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel10() - start");
		}

		if(jLabel10 == null) {
			jLabel10 = new javax.swing.JLabel();
			jLabel10.setText("Apartado Nro. ");
			jLabel10.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel10() - end");
		}
		return jLabel10;
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

		if(jPanel19 == null) {
			jPanel19 = new javax.swing.JPanel();
			java.awt.GridLayout layGridLayout11 = new java.awt.GridLayout();
			layGridLayout11.setRows(3);
			layGridLayout11.setColumns(2);
			layGridLayout11.setHgap(1);
			layGridLayout11.setVgap(1);
			jPanel19.setLayout(layGridLayout11);
			jPanel19.add(getJLabel11(), null);
			jPanel19.add(getJTextField4(), null);
			jPanel19.add(getJLabel12(), null);
			jPanel19.add(getJTextField5(), null);
			jPanel19.add(getJLabel13(), null);
			jPanel19.add(getJTextField6(), null);
			jPanel19.setPreferredSize(new java.awt.Dimension(320,100));
			jPanel19.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel19() - end");
		}
		return jPanel19;
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
			jLabel11.setText("Sub-Total:");
			jLabel11.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel11() - end");
		}
		return jLabel11;
	}
	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField4() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField4() - start");
		}

		if(jTextField4 == null) {
			jTextField4 = new javax.swing.JTextField();
			jTextField4.setEditable(false);
			jTextField4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			jTextField4.setText("0,00");
			jTextField4.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			jTextField4.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jTextField4.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField4() - end");
		}
		return jTextField4;
	}
	/**
	 * This method initializes jLabel12
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel12() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel12() - start");
		}

		if(jLabel12 == null) {
			jLabel12 = new javax.swing.JLabel();
			jLabel12.setText("Impuestos:");
			jLabel12.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel12() - end");
		}
		return jLabel12;
	}
	/**
	 * This method initializes jTextField5
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField5() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField5() - start");
		}

		if(jTextField5 == null) {
			jTextField5 = new javax.swing.JTextField();
			jTextField5.setEditable(false);
			jTextField5.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jTextField5.setText("0,00");
			jTextField5.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			jTextField5.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			jTextField5.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField5() - end");
		}
		return jTextField5;
	}
	/**
	 * This method initializes jLabel13
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel13() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel13() - start");
		}

		if(jLabel13 == null) {
			jLabel13 = new javax.swing.JLabel();
			jLabel13.setText("Total:");
			jLabel13.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel13() - end");
		}
		return jLabel13;
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
			jTextField6 = new javax.swing.JTextField();
			jTextField6.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jTextField6.setEditable(false);
			jTextField6.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			jTextField6.setText("0,00");
			jTextField6.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			jTextField6.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField6() - end");
		}
		return jTextField6;
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

		if(jButton3 == null) {
			jButton3 = new JHighlightButton();
			jButton3.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton3.setText("F1 Agregar Producto");
			jButton3.setPreferredSize(new java.awt.Dimension(120,40));
			jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton3.setDefaultCapable(false);
			jButton3.setMargin(new Insets(1,2,1,1));
			jButton3.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton3() - end");
		}
		return jButton3;
	}
	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton4() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton4() - start");
		}

		if(jButton4 == null) {
			jButton4 = new JHighlightButton();
			jButton4.setPreferredSize(new java.awt.Dimension(120,40));
			jButton4.setText("F3 Rebajas");
			jButton4.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton4.setDefaultCapable(false);
			jButton4.setMargin(new Insets(1,2,1,1));
			jButton4.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton4() - end");
		}
		return jButton4;
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
			jButton5.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton5.setPreferredSize(new java.awt.Dimension(120,40));
			jButton5.setText("F2 Eliminar Producto");
			jButton5.setMargin(new Insets(1,2,1,1));
			jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton5.setDefaultCapable(false);
			jButton5.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton5() - end");
		}
		return jButton5;
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
			jButton6.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton6.setPreferredSize(new java.awt.Dimension(120,40));
			jButton6.setText("F4 Totalizar");
			jButton6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton6.setDefaultCapable(false);
			jButton6.setMargin(new Insets(1,2,1,1));
			jButton6.setBackground(new java.awt.Color(226,226,222));
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
			jButton7.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton7.setPreferredSize(new java.awt.Dimension(120,40));
			jButton7.setText("F5 Consultas");
			jButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton7.setDefaultCapable(false);
			jButton7.setMargin(new Insets(1,2,1,1));
			jButton7.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton7() - end");
		}
		return jButton7;
	}
	/**
	 * This method initializes jButton9
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton9() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton9() - start");
		}

		if(jButton9 == null) {
			jButton9 = new JHighlightButton();
			jButton9.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton9.setPreferredSize(new java.awt.Dimension(120,40));
			jButton9.setText("F6 Cliente en Espera");
			jButton9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton9.setDefaultCapable(false);
			jButton9.setMargin(new Insets(1,2,1,1));
			jButton9.setBackground(new java.awt.Color(226,226,222));
			jButton9.setEnabled(true);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton9() - end");
		}
		return jButton9;
	}
	/**
	 * This method initializes jButton10
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton10() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton10() - start");
		}

		if(jButton10 == null) {
			jButton10 = new JHighlightButton();
			jButton10.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton10.setPreferredSize(new java.awt.Dimension(120,40));
			jButton10.setText("F7 Servicios");
			jButton10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton10.setDefaultCapable(false);
			jButton10.setMargin(new Insets(1,2,1,1));
			jButton10.setBackground(new java.awt.Color(226,226,222));
			jButton10.setEnabled(false);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton10() - end");
		}
		return jButton10;
	}
	/**
	 * This method initializes jButton8
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton8() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton8() - start");
		}

		if(jButton8 == null) {
			jButton8 = new JHighlightButton();
			jButton8.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton8.setPreferredSize(new java.awt.Dimension(120,40));
			jButton8.setText("F8 Utilitarios");
			jButton8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton8.setDefaultCapable(false);
			jButton8.setMargin(new Insets(1,2,1,1));
			jButton8.setBackground(new java.awt.Color(226,226,222));
			jButton8.setEnabled((new ActualizadorPreciosSACFactory()).getActualizadorPreciosSACInstance().botonServicioHabilitado(8, false));
			
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton8() - end");
		}
		return jButton8;
	}
	/**
	 * This method initializes jButton11
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton11() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton11() - start");
		}

		if(jButton11 == null) {
			jButton11 = new JHighlightButton();
			jButton11.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton11.setPreferredSize(new java.awt.Dimension(120,40));
			jButton11.setText("F9 Cancelar");
			jButton11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton11.setDefaultCapable(false);
			jButton11.setMargin(new Insets(1,2,1,1));
			jButton11.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton11() - end");
		}
		return jButton11;
	}
	/**
	 * This method initializes jButton12
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton12() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton12() - start");
		}

		if(jButton12 == null) {
			jButton12 = new JHighlightButton();
			jButton12.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton12.setPreferredSize(new java.awt.Dimension(120,40));
			jButton12.setText("F10 Gaveta");
			jButton12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton12.setDefaultCapable(false);
			jButton12.setMargin(new Insets(1,2,1,1));
			jButton12.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton12() - end");
		}
		return jButton12;
	}
	/**
	 * This method initializes jButton13
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton13() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton13() - start");
		}

		if(jButton13 == null) {
			jButton13 = new JHighlightButton();
			jButton13.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton13.setPreferredSize(new java.awt.Dimension(120,40));
			jButton13.setText("F11 Bloquear");
			jButton13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton13.setDefaultCapable(false);
			jButton13.setMargin(new Insets(1,2,1,1));
			jButton13.setEnabled(false);
			jButton13.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton13() - end");
		}
		return jButton13;
	}
	/**
	 * This method initializes jButton14
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton14() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton14() - start");
		}

		if(jButton14 == null) {
			jButton14 = new JHighlightButton();
			jButton14.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton14.setPreferredSize(new java.awt.Dimension(120,40));
			jButton14.setText("F12 Salir");
			jButton14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton14.setDefaultCapable(false);
			jButton14.setMargin(new Insets(1,2,1,1));
			jButton14.setBackground(new java.awt.Color(226,226,222));
			jButton14.setEnabled(false);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton14() - end");
		}
		return jButton14;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("actionPerformed(ActionEvent) - start");
		}

		if (e.getSource().equals(jComboBox)) {
			this.repintarFactura();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("actionPerformed(ActionEvent) - end");
		}
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - start");
		}

		eliminarListeners();
		
		//Mapeo de F1 en eventos de Mouse
		if(e.getSource().equals(jButton3)){
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			if(CR.meServ.getApartado().getDetallesServicio().size() > 0){
				CambiarCantidad cc;
				if(jTable.getSelectedRow() != -1){
					filaAModificar = jTable.getSelectedRow();
					cc = new CambiarCantidad(filaAModificar, 2);
				} else {
					cc = new CambiarCantidad(2);
				}
				MensajesVentanas.centrarVentanaDialogo(cc);
			} else {
				MensajesVentanas.aviso("No Existen productos en el \nApartado/Pedido Especial");
			}
			
			CR.inputEscaner.getDocument().addDocumentListener(this);
			this.repintarFactura();
			if (logger.isDebugEnabled()) {
				logger.debug("mouseClicked(MouseEvent) - F1");
			}
		}
		
		// Mapeo de F2 en eventos de Mouse
		else if(e.getSource().equals(jButton5)){
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			if(CR.meServ.getApartado().getDetallesServicio().size() > 0){
				if(jTable.getSelectedRow() == -1){
					AnularProductoApartado anularProducto = new AnularProductoApartado(CR.meServ.getApartado().getDetallesServicio().size()-1);
					MensajesVentanas.centrarVentanaDialogo(anularProducto);
					this.repintarFactura();				
				} else {
					AnularProductoApartado anularProducto = new AnularProductoApartado(jTable.getSelectedRow());
					MensajesVentanas.centrarVentanaDialogo(anularProducto);
					this.repintarFactura();	
				}
			} else {
				MensajesVentanas.aviso("No Existen productos en el \nApartado/Pedido Especial");
			}
			CR.inputEscaner.getDocument().addDocumentListener(this);
			if (logger.isDebugEnabled()) {
				logger.debug("mouseClicked(MouseEvent) - F2");
			}
		}

		//Mapeo de F3 en Eventos de Mouse
		else if(e.getSource().equals(jButton4) && this.getJButton4().isEnabled()){
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			if(jTable.getSelectedRow() >= 0){
				RebajasApartado rebajas = new RebajasApartado(jTable.getSelectedRow());
				MensajesVentanas.centrarVentanaDialogo(rebajas);
				this.repintarFactura();
			}
			else{
				RebajasApartado rebajas = new RebajasApartado(CR.meServ.getApartado().getDetallesServicio().size()-1);
				MensajesVentanas.centrarVentanaDialogo(rebajas);
				this.repintarFactura();
			}
			CR.inputEscaner.getDocument().addDocumentListener(this);
			if (logger.isDebugEnabled()) {
				logger.debug("mouseClicked(MouseEvent) - F3");
			}
		}
		
		//Mapeo de F4 en eventos de Mouse
		else if(e.getSource().equals(jButton6)){
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			try {
				if (CR.meServ.getApartado().getCliente().getCodCliente() == null) {
					MensajesVentanas.aviso("Debe ingresar el afiliado");
					CR.inputEscaner.getDocument().addDocumentListener(this);
				} else {
					if ((jComboBox!=null)&&(jComboBox.getSelectedIndex()<1)) {
						MensajesVentanas.aviso("Seleccione el Tipo de Abono Inicial");
						jComboBox.requestFocus();
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} else {
						if ((CR.meServ.getApartado().getMontoBase()+CR.meServ.getApartado().getMontoImpuesto())>=CR.meServ.getApartado().getMtoMinimoApartado()) {
							if (CR.meServ.getApartado().getDetallesServicio().size()>0) {
								repintarFactura();
								if (CR.meServ.getApartado().getAbonoNuevo()!=null) {
									RegistrarAbono abonos = new RegistrarAbono(CR.meServ.getApartado().getAbonoNuevo().getPagos(), CR.meServ.calcularMontoAbonoInicial(true), false);
									MensajesVentanas.centrarVentanaDialogo(abonos);
									this.repintarFactura();
								} else {
									RegistrarAbono abonos = new RegistrarAbono(null, CR.meServ.calcularMontoAbonoInicial(true), false);
									MensajesVentanas.centrarVentanaDialogo(abonos);
									this.repintarFactura();
								}
						}
						if (jComboBox!=null)
							CR.meServ.registrarApartado(String.valueOf(jComboBox.getSelectedIndex()).toCharArray()[0]);
						else
							CR.meServ.registrarApartado('1');
						dispose();
						CR.me.repintarMenuPrincipal();
						try {
							finalize();
						} catch (Throwable e1) {
							logger.error("mouseClicked(MouseEvent)", e1);
						}
						} else {
							MensajesVentanas.aviso("El monto del apartado debe ser mayor a " + df.format(CR.meServ.getApartado().getMtoMinimoApartado()));
							CR.inputEscaner.getDocument().addDocumentListener(this);
					}
				}
				}
				if (logger.isDebugEnabled()) {
					logger.debug("mouseClicked(MouseEvent) - F4");
				}
			} catch (ConexionExcepcion e1) {
				logger.error("mouseClicked(MouseEvent)", e1);

				MensajesVentanas.mensajeError(e1.getMensaje());
				CR.inputEscaner.getDocument().addDocumentListener(this);
			} catch (ExcepcionCr e1) {
				logger.error("mouseClicked(MouseEvent)", e1);

				MensajesVentanas.mensajeError(e1.getMensaje());
				CR.inputEscaner.getDocument().addDocumentListener(this);
			}
		}
		
		//Mapeo de F5 en eventos de Mouse
		else if(e.getSource().equals(jButton7)){
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			Consultas consultas = new Consultas();
			MensajesVentanas.centrarVentanaDialogo(consultas);
			String casoConsulta = consultas.getCodigoSeleccionado()[0];
			String codigo = consultas.getCodigoSeleccionado()[1];
			if (casoConsulta!=null) {
				try {
					if (new Integer(casoConsulta).intValue()<8) {
						CR.meServ.ingresarProductoApartado(codigo, Sesion.capturaTeclado);
					} else { //Cargar pantalla de registro de cliente 
						String id = consultas.getCodigoSeleccionado()[1];
						Cliente ctemp = CR.meServ.buscarClienteTemporal(id);
						registroCliente = null;			
						if(ctemp != null) { //Si el cliente ya estaba registrado
							registroCliente = new RegistroClientesNuevos(ctemp);
						} else { // Si el cliente NO estaba registrado
							registroCliente = new RegistroClientesNuevos(id);
						}
						MensajesVentanas.centrarVentanaDialogo(registroCliente);
					}
				} catch (ExcepcionCr e1) {
					logger.error("mouseClicked(MouseEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (ConexionExcepcion e1) {
					logger.error("mouseClicked(MouseEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
				}
			}
			CR.inputEscaner.getDocument().addDocumentListener(this);
			this.repintarFactura();			
			if (logger.isDebugEnabled()) {
				logger.debug("mouseClicked(MouseEvent) - F5");
			}
		}
//		Mapeo de F6
			else if(e.getSource().equals(jButton9)){
				//  ejecutado = true;
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				if ((CR.meServ.obtenerCantidadProds()>0) && (CR.meServ.getApartado().getCliente().getCodCliente() != null)){
					if (MensajesVentanas.preguntarSiNo("¿Desea colocar en espera el apartado actual?") == 0){
						try {
							CR.meServ.colocarApartadoEspera("NA");
							dispose();
							CR.me.repintarMenuPrincipal();
						} catch (BaseDeDatosExcepcion e1) {
							MensajesVentanas.mensajeError(e1.getMensaje());
							CR.inputEscaner.getDocument().addDocumentListener(this);
					  	} catch (ConexionExcepcion e1) {
							MensajesVentanas.mensajeError(e1.getMensaje());
							CR.inputEscaner.getDocument().addDocumentListener(this);
						} catch (UsuarioExcepcion e1) {
							MensajesVentanas.mensajeError(e1.getMensaje());
							CR.inputEscaner.getDocument().addDocumentListener(this);
						} catch (ExcepcionCr e1) {
							MensajesVentanas.mensajeError(e1.getMensaje());
							CR.inputEscaner.getDocument().addDocumentListener(this);
						}
				  	}
				} else {
					MensajesVentanas.mensajeError("Datos insuficientes para registro de apartado");
					CR.inputEscaner.getDocument().addDocumentListener(this);
				}
			  }
		
		//Mapeo de F8 
		else if(e.getSource().equals(jButton8)){
			
			Utilitarios ventanaUtilitarios = new Utilitarios();
			MensajesVentanas.centrarVentanaDialogo(ventanaUtilitarios);
			this.repintarFactura();
			if (logger.isDebugEnabled()) {
				logger.debug("mouseClicked(MouseEvent) - F8");
			}
		}
		
		//Mapeo de F9 en Eventos de Mouse
		else if(e.getSource().equals(jButton11)){
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			if (MensajesVentanas.preguntarSiNo(Sesion.MSG_CANCELAR)==0) {
				try {
					CR.meServ.anularApartadoActivo();
					dispose();
					CR.me.repintarMenuPrincipal();
					try {
						finalize();
					} catch (Throwable e1) {
						logger.error("keyPressed(KeyEvent)", e1);
					}
				} catch (ExcepcionCr e1) {
					logger.error("mouseClicked(MouseEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
					CR.inputEscaner.getDocument().addDocumentListener(this);
				} catch (ConexionExcepcion e1) {
					logger.error("mouseClicked(MouseEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
					CR.inputEscaner.getDocument().addDocumentListener(this);
				}
			}			
			if (logger.isDebugEnabled()) {
				logger.debug("mouseClicked(MouseEvent) - F9");
			}
		
		}
		else if(e.getSource().equals(jButton)) {
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			ec =null;
			ec = new EjecutarConCantidad("Asignar un Cliente:", "",7);
			MensajesVentanas.centrarVentanaDialogo(ec);
			eventoDeIngresoProducto = true;
			this.repintarFactura();
		
			eventoDeIngresoProducto = true;
			
			ejecutandoEvento = false;
			escanerActivo = true;
			getJTable().requestFocus();
			
			CR.inputEscaner.getDocument().addDocumentListener(this);
			if (logger.isDebugEnabled()) {
				logger.debug("mouseClicked(MouseEvent) - end");
			}
		}  else if(e.getSource().equals(jTable)){
			//Caso agregado por centrobeco, modulo de promociones
			if(e.getClickCount()==2){
				ActualizadorPreciosFactory  factory = new ActualizadorPreciosFactory();
				ActualizadorPrecios actualizadorPrecios = factory.getActualizadorPreciosInstance();		
				actualizadorPrecios.abrirVentanaCondicionesVenta(((DetalleServicio)(CR.meServ.getApartado().getDetallesServicio().elementAt(jTable.getSelectedRow()))).getCondicionesVentaPromociones());
			}
		}
	
		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - end");
		}
		
		agregarListeners();
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
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}

		boolean ejecutado = false;
		eliminarListeners();
		
		//Mapeo de todas las teclas de funcion de la interfaz
		//Mapeo de F1
		if(e.getKeyCode() == KeyEvent.VK_F1){
			ejecutado = true;
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			if(CR.meServ.getApartado().getDetallesServicio().size() > 0){
				CambiarCantidad cc;
				if(jTable.getSelectedRow() != -1){
					filaAModificar = jTable.getSelectedRow();
					cc = new CambiarCantidad(filaAModificar, 2);
				} else {
					cc = new CambiarCantidad(2);
				}
				MensajesVentanas.centrarVentanaDialogo(cc);
			} else {
				MensajesVentanas.aviso("No Existen productos en el \nApartado/Pedido Especial");
			}
			CR.inputEscaner.getDocument().addDocumentListener(this);
			this.repintarFactura();
			if (logger.isDebugEnabled()) {
				logger.debug("keyPressed(KeyEvent) - F1");
			}
		}
		
		//Mapeo de F2
		else if(e.getKeyCode() == KeyEvent.VK_F2){
			ejecutado = true;
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			if(CR.meServ.getApartado().getDetallesServicio().size() > 0){
				if(jTable.getSelectedRow() == -1){
					AnularProductoApartado anularProducto = new AnularProductoApartado(CR.meServ.getApartado().getDetallesServicio().size()-1);
					MensajesVentanas.centrarVentanaDialogo(anularProducto);
					this.repintarFactura();				
				} else {
					AnularProductoApartado anularProducto = new AnularProductoApartado(jTable.getSelectedRow());
					MensajesVentanas.centrarVentanaDialogo(anularProducto);
					this.repintarFactura();	
				}
			} else {
				MensajesVentanas.aviso("No Existen productos en el Apartado/Pedido Especial");
			}
			CR.inputEscaner.getDocument().addDocumentListener(this);
			if (logger.isDebugEnabled()) {
				logger.debug("keyPressed(KeyEvent) - F2");
			}
		}

		//Mapeo de F3
		else if(e.getKeyCode() == KeyEvent.VK_F3 && this.getJButton4().isEnabled()){
			ejecutado = true;
			CR.inputEscaner.getDocument().removeDocumentListener(this);

			if(jTable.getSelectedRow() >= 0){
				RebajasApartado rebajas = new RebajasApartado(jTable.getSelectedRow());
				MensajesVentanas.centrarVentanaDialogo(rebajas);
				this.repintarFactura();
			}
			else{
				RebajasApartado rebajas = new RebajasApartado(CR.meServ.getApartado().getDetallesServicio().size()-1);
				MensajesVentanas.centrarVentanaDialogo(rebajas);
				this.repintarFactura();
			}
			CR.inputEscaner.getDocument().addDocumentListener(this);
			if (logger.isDebugEnabled()) {
				logger.debug("keyPressed(KeyEvent) - F3");
			}
		}
		
		//Mapeo de F4
		else if(e.getKeyCode() == KeyEvent.VK_F4){
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			ejecutado = true;
			try {
				if (CR.meServ.getApartado().getCliente().getCodCliente() == null) {
					MensajesVentanas.aviso("Debe ingresar el afiliado");
					CR.inputEscaner.getDocument().addDocumentListener(this);
				} else {
					if ((jComboBox!=null)&&(jComboBox.getSelectedIndex()<1)) {
						MensajesVentanas.aviso("Seleccione el Tipo de Abono Inicial");
						jComboBox.requestFocus();
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} else {
						if ((CR.meServ.getApartado().getMontoBase()+CR.meServ.getApartado().getMontoImpuesto())>=CR.meServ.getApartado().getMtoMinimoApartado()) {
						if (CR.meServ.getApartado().getDetallesServicio().size()>0) {
							repintarFactura();
							if (CR.meServ.getApartado().getAbonoNuevo()!=null) {
								RegistrarAbono abonos = new RegistrarAbono(CR.meServ.getApartado().getAbonoNuevo().getPagos(), CR.meServ.calcularMontoAbonoInicial(true), false);
								MensajesVentanas.centrarVentanaDialogo(abonos);
								this.repintarFactura();
							} else {
								RegistrarAbono abonos = new RegistrarAbono(null, CR.meServ.calcularMontoAbonoInicial(true), false);
								MensajesVentanas.centrarVentanaDialogo(abonos);
								this.repintarFactura();
							}
						}
						if (jComboBox!=null)
							CR.meServ.registrarApartado(String.valueOf(jComboBox.getSelectedIndex()).toCharArray()[0]);
						else
							CR.meServ.registrarApartado('1');
						dispose();
						CR.me.repintarMenuPrincipal();
						try {
							finalize();
						} catch (Throwable e1) {
							logger.error("keyPressed(KeyEvent)", e1);
						}
						} else {
							MensajesVentanas.aviso("El monto del apartado debe ser mayor a " + df.format(CR.meServ.getApartado().getMtoMinimoApartado()));
							CR.inputEscaner.getDocument().addDocumentListener(this);
						}
				}
				}
				if (logger.isDebugEnabled()) {
					logger.debug("keyPressed(KeyEvent) - F4");
				}
			} catch (ConexionExcepcion e1) {
				logger.error("keyPressed(KeyEvent)", e1);

				MensajesVentanas.mensajeError(e1.getMensaje());
				CR.inputEscaner.getDocument().addDocumentListener(this);
			} catch (ExcepcionCr e1) {
				logger.error("keyPressed(KeyEvent)", e1);

				MensajesVentanas.mensajeError(e1.getMensaje());
				CR.inputEscaner.getDocument().addDocumentListener(this);
			}
		}
		
		//Mapeo de F5
		else if(e.getKeyCode() == KeyEvent.VK_F5){
			ejecutado = true;
			CR.inputEscaner.getDocument().removeDocumentListener(this);
		//	try {
		//		CR.me.consultaProductoCliente();
				Consultas consultas = new Consultas();
				MensajesVentanas.centrarVentanaDialogo(consultas);
				String casoConsulta = consultas.getCodigoSeleccionado()[0];
				String codigo = consultas.getCodigoSeleccionado()[1];
				if (casoConsulta!=null) {
					try {
						if (new Integer(casoConsulta).intValue()<8) {
							CR.meServ.ingresarProductoApartado(codigo, Sesion.capturaTeclado);
						} else { //Cargar pantalla de registro de cliente 
							String id = consultas.getCodigoSeleccionado()[1];
							Cliente ctemp = CR.meServ.buscarClienteTemporal(id);
							registroCliente = null;			
							if(ctemp != null) { //Si el cliente ya estaba registrado
								registroCliente = new RegistroClientesNuevos(ctemp);
							} else { // Si el cliente NO estaba registrado
								registroCliente = new RegistroClientesNuevos(id);
							}
							MensajesVentanas.centrarVentanaDialogo(registroCliente);
						}
					} catch (ExcepcionCr e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (ConexionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
					}
				}
			CR.inputEscaner.getDocument().addDocumentListener(this);
			this.repintarFactura();			
			if (logger.isDebugEnabled()) {
				logger.debug("keyPressed(KeyEvent) - F5");
			}
		}
		
		//Mapeo de F6
		else if(e.getKeyCode() == KeyEvent.VK_F6){
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			ejecutado = true;
			if ((CR.meServ.obtenerCantidadProds()>0) && (CR.meServ.getApartado().getCliente().getCodCliente() != null)){
				if (MensajesVentanas.preguntarSiNo("¿Desea colocar en espera el apartado actual?") == 0){
					try {
						CR.meServ.colocarApartadoEspera("NA");
						dispose();
						CR.me.repintarMenuPrincipal();
					} catch (BaseDeDatosExcepcion e1) {
						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (ConexionExcepcion e1) {
						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (UsuarioExcepcion e1) {
						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (ExcepcionCr e1) {
						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					}
				}
			} else {
				MensajesVentanas.mensajeError("Datos insuficientes para registro de apartado");
				CR.inputEscaner.getDocument().addDocumentListener(this);
			}
	
		}
		
		//Mapeo de F8 
		else if(e.getKeyCode() == KeyEvent.VK_F8){
			
			Utilitarios ventanaUtilitarios = new Utilitarios();
			MensajesVentanas.centrarVentanaDialogo(ventanaUtilitarios);
			this.repintarFactura();
			if (logger.isDebugEnabled()) {
				logger.debug("keyPressed(KeyEvent) F8");
			}
		}
		
		//Mapeo de F9
		else if(e.getKeyCode() == KeyEvent.VK_F9){
			ejecutado = true;
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			if (MensajesVentanas.preguntarSiNo(Sesion.MSG_CANCELAR)==0) {
				try {
					CR.meServ.anularApartadoActivo();
					dispose();
					CR.me.repintarMenuPrincipal();
					try {
						finalize();
					} catch (Throwable e1) {
						logger.error("keyPressed(KeyEvent)", e1);
					}
				} catch (ExcepcionCr e1) {
					logger.error("keyPressed(KeyEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
					CR.inputEscaner.getDocument().addDocumentListener(this);
				} catch (ConexionExcepcion e1) {
					logger.error("keyPressed(KeyEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
					CR.inputEscaner.getDocument().addDocumentListener(this);
				}
			}			
		}
		
		//Mapeo las teclas de Movimiento
		
		
		else if(e.getKeyCode() == KeyEvent.VK_A) {
			ejecutado = true;
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			if (Character.isLetterOrDigit(e.getKeyChar())) {
			// Mostramos una ventana para que el usuario introduzca el código del cliente
				ec =null;
				ec = new EjecutarConCantidad("Asignar un Cliente:", "",7);
				MensajesVentanas.centrarVentanaDialogo(ec);
				eventoDeIngresoProducto = true;
				this.repintarFactura();
				eventoDeIngresoProducto = true;
				ejecutandoEvento = false;
				escanerActivo = true;
				getJTable().requestFocus();
			}
			CR.inputEscaner.getDocument().addDocumentListener(this);
		}
		else if ((e.getKeyCode() == KeyEvent.VK_TAB)&&(e.getSource().equals(jTable))) {
			jComboBox.requestFocus();
		}

		else if (e.getSource().equals(jComboBox)) {
			if ((e.getKeyCode()!=KeyEvent.VK_DOWN)&&(e.getKeyCode()!=KeyEvent.VK_UP)) {
				ejecutado = true;
				jTable.requestFocus();
			}
		}
		//Caso agregado para modulo de promociones
		else if (e.getKeyCode() == KeyEvent.VK_D){
			ActualizadorPreciosFactory  factory = new ActualizadorPreciosFactory();
			ActualizadorPrecios actualizadorPrecios = factory.getActualizadorPreciosInstance();		
			int lineaSeleccionada= jTable.getSelectedRow();
			//Si no hay ningun producto seleccionado, no se realiza ninguna accion
			//Modificacion 10-04-2012 jperez
			if (lineaSeleccionada>=0)
				actualizadorPrecios.abrirVentanaCondicionesVenta(((DetalleServicio)(CR.meServ.getApartado().getDetallesServicio().elementAt(lineaSeleccionada))).getCondicionesVentaPromociones());
		}
		
		if (!ejecutado) {
			//Verificamos que se presionó un número
			if ((e.getKeyChar()=='1')||(e.getKeyChar()=='2')||(e.getKeyChar()=='3')||(e.getKeyChar()=='4')||(e.getKeyChar()=='5')
				||(e.getKeyChar()=='6')||(e.getKeyChar()=='7')||(e.getKeyChar()=='8')||(e.getKeyChar()=='9')||(e.getKeyChar()=='0')) {
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				// Mostramos una ventana para que el usuario introduzca el código del producto
				EjecutarConCantidad ec = new EjecutarConCantidad("Ingreso de Codigo:", String.valueOf(e.getKeyChar()),4);
				MensajesVentanas.centrarVentanaDialogo(ec);
				CR.inputEscaner.getDocument().addDocumentListener(this);
				this.repintarFactura();
			}
		}
		
		agregarListeners();

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
	
	/**
	 * Método repintarFactura.
	 */
	private void repintarFactura(){
		if (logger.isDebugEnabled()) {
			logger.debug("repintarFactura() - start");
		}

		try{
			MaquinaDeEstado.setEstadoCaja(Sesion.getCaja().getEstado());
			modeloTablaDetalleApartado.llenarTablaDetalleApartado();
			String nombre, rif;
				if (CR.meServ.getApartado().getCliente().getCodCliente()== null ){
					nombre = "";
					rif = "";
					jButton.setText("Asignar Clientes");
				} else{
					nombre = new String(CR.meServ.getApartado().getCliente().getNombreCompleto());
					rif = new String(CR.meServ.getApartado().getCliente().getCodCliente());
					if(CR.meServ.getApartado().isApartadoExento()) {
						CR.me.setExento(true);
						CR.me.mostrarAviso("Cliente Exento de Impuestos", true);
					} else {
						CR.me.setExento(false);
					}
				}
			CR.me.setCliente(nombre, rif);
			
			getJTextField4().setText(Sesion.getTienda().getMonedaBase()+" "+String.valueOf(df.format(CR.meServ.getApartado().getMontoBase())));
			getJTextField5().setText(Sesion.getTienda().getMonedaBase()+" "+String.valueOf(df.format(CR.meServ.getApartado().getMontoImpuesto())));
			getJTextField6().setText(Sesion.getTienda().getMonedaBase()+" "+String.valueOf(df.format(CR.meServ.getApartado().getMontoBase() + CR.meServ.getApartado().getMontoImpuesto())));
			try {
				if (jComboBox!=null)
					Sesion.PORC_MINIMO_ABONO = new Double(((String)jComboBox.getSelectedItem()).replace('%',' ')).doubleValue();
				else
					Sesion.PORC_MINIMO_ABONO = new Double(jLabel18.getText().replace('%',' ')).doubleValue();
			} catch (Exception e1) {
				logger.error("repintarFactura()", e1);

				Sesion.PORC_MINIMO_ABONO = 0;
			}
			jLabel9.setText("Abono Inicial: "+Sesion.getTienda().getMonedaBase()+" " + String.valueOf(df.format(CR.meServ.calcularMontoAbonoInicial())));
			jLabel10.setText("No.Artículos: " + String.valueOf(df.format(CR.meServ.obtenerCantidadProds())));
		} catch (Exception ex){
			logger.error("repintarFactura()", ex);

			jLabel9.setText("Abono Inicial: "+Sesion.getTienda().getMonedaBase()+ df.format(0));
			CR.me.setExento(false);
			CR.me.setCliente("", "");
			getJTextField4().setText(df.format(0));
			getJTextField5().setText(df.format(0));
			getJTextField6().setText(df.format(0));
			jLabel10.setText("No.Artículos: "+df.format(0));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("repintarFactura() - end");
		}
	}
	
	/**
	 * This method initializes jLabel17
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel17() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel17() - start");
		}

		if(jLabel17 == null) {
			jLabel17 = new javax.swing.JLabel();
			jLabel17.setText("Tipo Abono Inicial: ");
			jLabel17.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			//jLabel17.setPreferredSize(new java.awt.Dimension(140,19));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel17() - end");
		}
		return jLabel17;
	}

	/**
	 * This method initializes jLabel18
	 * 
	 * @return javax.swing.JComboBox
	 */
	private javax.swing.JLabel getJLabel18() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel18() - start");
		}

		if(jLabel18 == null) {
			jLabel18 = new javax.swing.JLabel();
			jLabel18.setBackground(new java.awt.Color(226,226,222));
			jLabel18.setText(CR.meServ.getApartado().getPorcentajesDeAbonos()[0] + "%");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel18() - end");
		}
		return jLabel18;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private javax.swing.JComboBox getJComboBox() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJComboBox() - start");
		}

		if(jComboBox == null) {
			jComboBox = new javax.swing.JComboBox();
			jComboBox.setBackground(new java.awt.Color(226,226,222));
			jComboBox.setPreferredSize(new java.awt.Dimension(100,25));
			jComboBox.setEditable(false);
			jComboBox.addItem("------");
			for (int i=0; i<CR.meServ.getApartado().getTiposDeAbonosInicial(); i++)
				jComboBox.addItem(CR.meServ.getApartado().getPorcentajesDeAbonos()[i] + "%");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJComboBox() - end");
		}
		return jComboBox;
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
			jButton.setBackground(new java.awt.Color(226,226,222));
			jButton.setPreferredSize(new java.awt.Dimension(120,40));
			jButton.setText("Asignar Clientes");
			jButton.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton.setMnemonic(java.awt.event.KeyEvent.VK_A);
			jButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton.setDefaultCapable(false);
			jButton.setMargin(new Insets(1,2,1,1));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton() - end");
		}
		return jButton;
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

		if (lector == null) {
			lector = new EfectuarLecturaEscaner(this); 
		} else {
			lector.iniciar();
		}	

		if (logger.isDebugEnabled()) {
			logger.debug("insertUpdate(DocumentEvent) - end");
		}
	}
	
	public synchronized void leerEscaner(final String codigoScaner){
		if (logger.isDebugEnabled()) {
			logger.debug("leerEscaner(String) - start");
		}

		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					if (logger.isDebugEnabled()) {
						logger.debug("run() - start");
					}

					lecturaEscaner(codigoScaner);

					if (logger.isDebugEnabled()) {
						logger.debug("run() - end");
					}
				};
			});
		} catch (InterruptedException e) {
			logger.error("leerEscaner(String)", e);
		} catch (InvocationTargetException e) {
			logger.error("leerEscaner(String)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("leerEscaner(String) - end");
		}
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se eliminó variable sin uso
	* Fecha: agosto 2011
	*/
	public void lecturaEscaner(String codigoScaner) {
		if (logger.isDebugEnabled()) {
			logger.debug("lecturaEscaner(String) - start");
		}

		try {
			ArrayList <Object>valorLeido = Control.codigoValido(codigoScaner);
			int tipoCodigo = ((Integer)valorLeido.get(0)).intValue();
			String codigo = (String)valorLeido.get(1);
			switch (tipoCodigo){
				case Control.PRODUCTO:
					CR.meServ.ingresarProductoApartado(codigo, Sesion.capturaEscaner);
					break;
				case Control.CLIENTE:
					CR.meServ.asignarCliente(codigo);
					break;
				case Control.COLABORADOR:
					CR.meServ.asignarCliente(codigo);
					break;
				case Control.CODIGO_DESCONOCIDO: 
					try{
						CR.meServ.ingresarProductoApartado(codigoScaner.substring(0,Control.getLONGITUD_CODIGO()-1),Sesion.capturaEscaner);
					}catch(Exception ex1){
						logger.error("lecturaEscaner(String)", ex1);

						try {
							CR.meServ.ingresarProductoApartado(codigoScaner, Sesion.capturaEscaner);
						} catch (Exception ex2) {
							logger.error("lecturaEscaner(String)", ex2);

							CR.meServ.asignarCliente(codigo);
						}
					}
					break;
			}
		} catch (AfiliadoUsrExcepcion e1) {
			logger.error("lecturaEscaner(String)", e1);

			MensajesVentanas.mensajeError(e1.getMensaje());
		} catch (UsuarioExcepcion e1) {
			logger.error("lecturaEscaner(String)", e1);

			MensajesVentanas.mensajeError(e1.getMensaje());
		} catch (AutorizacionExcepcion e1) {
			logger.error("lecturaEscaner(String)", e1);

			MensajesVentanas.mensajeError(e1.getMensaje());
		} catch (ExcepcionCr e1) {
			logger.error("lecturaEscaner(String)", e1);

			MensajesVentanas.mensajeError("Código no registrado");
		} catch (ConexionExcepcion e1) {
			logger.error("lecturaEscaner(String)", e1);

			MensajesVentanas.mensajeError(e1.getMensaje());
		}
		this.repintarFactura();

		if (logger.isDebugEnabled()) {
			logger.debug("lecturaEscaner(String) - end");
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
	
	private void agregarListeners() {
		if (logger.isDebugEnabled()) {
			logger.debug("agregarListeners() - start");
		}

		jScrollPane.addKeyListener(this);
		jScrollPane.addMouseListener(this);
		
		jTable.addKeyListener(this);
		jTable.addMouseListener(this);
		
		jButton3.addKeyListener(this);
		jButton3.addMouseListener(this);
		
		jButton5.addKeyListener(this);
		jButton5.addMouseListener(this);
		
		jButton6.addKeyListener(this);
		jButton6.addMouseListener(this);
		
		jButton7.addKeyListener(this);
		jButton7.addMouseListener(this);
		
		jButton8.addKeyListener(this);
		jButton8.addMouseListener(this);
		
		jButton9.addKeyListener(this);
		jButton9.addMouseListener(this);
		
		jButton11.addKeyListener(this);
		jButton11.addMouseListener(this);

		jButton.addKeyListener(this);
		jButton.addMouseListener(this);
		
		if (logger.isDebugEnabled()) {
			logger.debug("agregarListeners() - end");
		}
	}

	private void eliminarListeners() {
		if (logger.isDebugEnabled()) {
			logger.debug("eliminarListeners() - start");
		}

		jScrollPane.removeKeyListener(this);
		jScrollPane.removeMouseListener(this);
		
		jTable.removeKeyListener(this);
		jTable.removeMouseListener(this);
		
		jButton3.removeKeyListener(this);
		jButton3.removeMouseListener(this);
		
		jButton5.removeKeyListener(this);
		jButton5.removeMouseListener(this);
		
		jButton6.removeKeyListener(this);
		jButton6.removeMouseListener(this);
		
		jButton7.removeKeyListener(this);
		jButton7.removeMouseListener(this);
		
		jButton8.removeKeyListener(this);
		jButton8.removeMouseListener(this);
		
		jButton11.removeKeyListener(this);
		jButton11.removeMouseListener(this);

		jButton9.removeKeyListener(this);
		jButton9.removeMouseListener(this);

		jButton.removeKeyListener(this);
		jButton.removeMouseListener(this);

		if (logger.isDebugEnabled()) {
			logger.debug("eliminarListeners() - end");
		}
	}
	

	/**
	 * Método colocarEspera
	 * Coloca el apartado en espera siempre y cuando no se hayan hecho abonos y se haya asignado a un cliente.
	 */
	@SuppressWarnings("unused")
	private void colocarEspera(){
		if (logger.isDebugEnabled()) {
			logger.debug("colocarEspera() - start");
		}
			CR.inputEscaner.getDocument().removeDocumentListener(this);
		try {
			CR.meServ.colocarApartadoEspera(CR.meServ.getApartado().getCliente().getCodCliente());
			CR.me.repintarMenuPrincipal();
		} catch (ConexionExcepcion e1) {
			logger.error("colocarFactura()", e1);

			if (!Sesion.isCajaEnLinea()) {
				 MensajesVentanas.aviso("El sistema se encuentra operando Fuera de Línea");
			} else {
				 MensajesVentanas.mensajeError(e1.getMensaje());
			}
		} catch (ExcepcionCr e1) {
			logger.error("colocarEspera()", e1);
			MensajesVentanas.mensajeError(e1.getMensaje());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("colocarEspera() - end");
		}
	}	

	
	
}
