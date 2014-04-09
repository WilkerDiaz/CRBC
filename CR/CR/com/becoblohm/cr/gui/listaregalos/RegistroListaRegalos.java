/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui.listaregalos
 * Programa   : RegistroListaRegalos.java
 * Creado por : rabreu
 * Creado en  : 03/07/2006
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
package com.becoblohm.cr.gui.listaregalos;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.excepciones.AfiliadoUsrExcepcion;
import com.becoblohm.cr.excepciones.AutorizacionExcepcion;
import com.becoblohm.cr.excepciones.ClienteExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.gui.CambiarCantidad;
import com.becoblohm.cr.gui.Consultas;
import com.becoblohm.cr.gui.EjecutarConCantidad;
import com.becoblohm.cr.gui.PanelIconosCR;
import com.becoblohm.cr.gui.PanelMensajesCR;
import com.becoblohm.cr.gui.ScanerSwitch;
import com.becoblohm.cr.gui.ValidarPassword;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarservicio.ListaRegalos;
import com.becoblohm.cr.perifericos.EfectuarLecturaEscaner;
import com.becoblohm.cr.utiles.Control;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;

/**
 * @author rabreu
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class RegistroListaRegalos extends JDialog implements ActionListener, KeyListener, DocumentListener, ScanerSwitch {
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JButton jButton9 = null;
	private javax.swing.JButton jButton11 = null;
	private javax.swing.JButton jButton2 = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JTextField notificacionesTF = null;
	private javax.swing.JTextField fechaEventoTF = null;
	private javax.swing.JTextField eventoTF = null;
	private javax.swing.JButton jButton5 = null;
	private javax.swing.JButton jButton10 = null;
	private javax.swing.JButton jButton3 = null;
	private javax.swing.JButton jButton6 = null;
	private javax.swing.JButton jButton12 = null;
	private javax.swing.JLabel jLabel4 = null;
	private javax.swing.JLabel jLabel5 = null;
	private javax.swing.JLabel jLabel6 = null;
	private javax.swing.JLabel jLabel7 = null;
	private javax.swing.JLabel jLabel8 = null;
	private javax.swing.JButton jButton4 = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JScrollPane jScrollPane = null;
	private javax.swing.JTable jTable = null;
	
	private DecimalFormat df = new DecimalFormat("#,##0.00");
	private ModeloTablaLR modeloTablaRegistroLR = new ModeloTablaLR(1);
	private DatosLista datosLista = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JPanel jPanel5 = null;
	private javax.swing.JLabel jLabel11 = null;
	private javax.swing.JLabel jLabel25 = null;
	private javax.swing.JLabel jLabel27 = null;
	private javax.swing.JTextField subTotalTF = null;
	private javax.swing.JTextField ivaTF = null;
	private javax.swing.JTextField totalTF = null;
	private javax.swing.JTextField cantProductosTF = null;
	private javax.swing.JTextField titularTF = null;
	private javax.swing.JTextField titularSecTF = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JTextField tipoListaTF = null;
	private EfectuarLecturaEscaner lector = null;
	private int filaAModificar = 0;
	private boolean permitirEscaner = true;
	
	private JDialog principal = null;
	
	private javax.swing.JButton jButton7 = null;
	private javax.swing.JButton jButton8 = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JTextField permitirVentaTF = null;
	private javax.swing.JButton jButton13 = null;

	/**
	 * This is the default constructor
	 */
//	public RegistroListaRegalos(JDialog principal, Vector datos) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
//		super(MensajesVentanas.ventanaActiva);
//		this.principal = principal;
//		cargarDatos(datos);
//		initialize();
//		this.agregarListeners();
//		CR.inputEscaner.getDocument().addDocumentListener(this);
//	}

	public RegistroListaRegalos(JDialog principal, boolean enEspera) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr{
		super(MensajesVentanas.ventanaActiva);
		if(enEspera)
			CR.meServ.recuperarEstadoLista(ListaRegalos.REGISTRO);
		this.principal = principal;
		initialize();
		this.repintarPantalla();
		this.agregarListeners();
		CR.inputEscaner.getDocument().addDocumentListener(this);
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(810, 600);
		this.setContentPane(getJContentPane());
		this.setBackground(new java.awt.Color(226,226,222));
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setModal(true);
		this.setResizable(false);
		this.setName("FrameRegistroLR");
		this.setTitle("CR - Registro de Lista de Regalos");
		this.setLocale(new java.util.Locale("es", "VE", ""));
		this.setUndecorated(false);
		this.setLocationRelativeTo(this);
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER,1,1));
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJPanel1(), null);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJPanel1(), null);
			jContentPane.setBackground(new java.awt.Color(226,226,222));
			jContentPane.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
			jContentPane.setPreferredSize(new java.awt.Dimension(810,600));
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
			java.awt.FlowLayout layFlowLayout3 = new java.awt.FlowLayout();
			layFlowLayout3.setHgap(0);
			layFlowLayout3.setVgap(0);
			layFlowLayout3.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel.setLayout(layFlowLayout3);
			jPanel.add(PanelIconosCR.getPanelLogo(), null);
			jPanel.add(PanelIconosCR.getPanelIconos(), null);
			jPanel.add(getJPanel2(), null);
			jPanel.add(getJPanel3(), null);
			jPanel.add(getJPanel5(), null);
			jPanel.add(PanelMensajesCR.getAreaMensajes(), null);
			jPanel.add(PanelMensajesCR.getPanelEstadoCaja(), null);
			jPanel.add(PanelMensajesCR.getPanelStatus(), null);
			jPanel.setBackground(new java.awt.Color(242,242,238));
			jPanel.setPreferredSize(new java.awt.Dimension(660,560));
			jPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
		}
		return jPanel;
	}
	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel1() {
		if(jPanel1 == null) {
			jPanel1 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setHgap(1);
			layFlowLayout1.setVgap(1);
			jPanel1.setLayout(layFlowLayout1);
			jPanel1.add(getJButton1(), null);
			jPanel1.add(getJButton2(), null);
			jPanel1.add(getJButton3(), null);
			jPanel1.add(getJButton4(), null);
			jPanel1.add(getJButton5(), null);
			jPanel1.add(getJButton6(), null);
			//jPanel1.add(getJButton7(), null);
			jPanel1.add(getJButton7(), null);
			jPanel1.add(getJButton8(), null);
			jPanel1.add(getJButton9(), null);
			jPanel1.add(getJButton10(), null);
			jPanel1.add(getJButton11(), null);
			jPanel1.add(getJButton12(), null);
			jPanel1.add(getJButton13(), null);
			jPanel1.setBackground(new java.awt.Color(242,242,238));
			jPanel1.setPreferredSize(new java.awt.Dimension(130,560));
			jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
		}
		return jPanel1;
	}
	/**
	 * This method initializes jButton9
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton9() {
		if(jButton9 == null) {
			jButton9 = new javax.swing.JButton();
			jButton9.setBackground(new java.awt.Color(226,226,222));
			jButton9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton9.setPreferredSize(new java.awt.Dimension(120,40));
			jButton9.setText("F9 Cancelar");
			jButton9.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton9.setMargin(new Insets(1,2,1,1));
			jButton9.setEnabled(true);
		}
		return jButton9;
	}
	/**
	 * This method initializes jButton11
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton11() {
		if(jButton11 == null) {
			jButton11 = new javax.swing.JButton();
			jButton11.setPreferredSize(new java.awt.Dimension(120,40));
			jButton11.setBackground(new java.awt.Color(226,226,222));
			jButton11.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton11.setText("F11 Bloquear");
			jButton11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton11.setMargin(new Insets(1,2,1,1));
			jButton11.setEnabled(false);
		}
		return jButton11;
	}
	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton2() {
		if(jButton2 == null) {
			jButton2 = new javax.swing.JButton();
			jButton2.setPreferredSize(new java.awt.Dimension(120,40));
			jButton2.setBackground(new java.awt.Color(226,226,222));
			jButton2.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton2.setText("F2 Eliminar Producto");
			jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton2.setMargin(new Insets(1,2,1,1));
			jButton2.setEnabled(true);
		}
		return jButton2;
	}
	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel3() {
		if(jPanel3 == null) {
			jPanel3 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout4 = new java.awt.FlowLayout();
			layFlowLayout4.setHgap(1);
			layFlowLayout4.setVgap(1);
			jPanel3.setLayout(layFlowLayout4);
			jPanel3.add(getJLabel4(), null);
			jPanel3.add(getTitularTF(), null);
			jPanel3.add(getJLabel2(), null);
			jPanel3.add(getTitularSecTF(), null);
			jPanel3.add(getJLabel5(), null);
			jPanel3.add(getEventoTF(), null);
			jPanel3.add(getJLabel6(), null);
			jPanel3.add(getFechaEventoTF(), null);
			jPanel3.add(getJLabel(), null);
			jPanel3.add(getTipoListaTF(), null);
			jPanel3.add(getJLabel27(), null);
			jPanel3.add(getNotificacionesTF(), null);
			jPanel3.add(getJLabel1(), null);
			jPanel3.add(getPermitirVentaTF(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(328,170));
			jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalles del Evento: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), java.awt.Color.black));
			jPanel3.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel3;
	}
	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton5() {
		if(jButton5 == null) {
			jButton5 = new javax.swing.JButton();
			jButton5.setPreferredSize(new java.awt.Dimension(120,40));
			jButton5.setBackground(new java.awt.Color(226,226,222));
			jButton5.setText("F5 Consultas");
			jButton5.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton5.setMargin(new Insets(1,2,1,1));
			jButton5.setEnabled(true);
		}
		return jButton5;
	}
	/**
	 * This method initializes jButton10
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton10() {
		if(jButton10 == null) {
			jButton10 = new javax.swing.JButton();
			jButton10.setPreferredSize(new java.awt.Dimension(120,40));
			jButton10.setBackground(new java.awt.Color(226,226,222));
			jButton10.setText("F10 Gaveta");
			jButton10.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton10.setMargin(new Insets(1,2,1,1));
		}
		return jButton10;
	}
	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton3() {
		if(jButton3 == null) {
			jButton3 = new javax.swing.JButton();
			jButton3.setPreferredSize(new java.awt.Dimension(120,40));
			jButton3.setBackground(new java.awt.Color(226,226,222));
			jButton3.setMargin(new Insets(1,2,1,1));
			jButton3.setText("F3 Detalles del Evento");
			jButton3.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		}
		return jButton3;
	}
	/**
	 * This method initializes jButton6
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton6() {
		if(jButton6 == null) {
			jButton6 = new javax.swing.JButton();
			jButton6.setPreferredSize(new java.awt.Dimension(120,40));
			jButton6.setBackground(new java.awt.Color(226,226,222));
			jButton6.setMargin(new Insets(1,2,1,1));
			jButton6.setText("F6 Colocar en Espera");
			jButton6.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		}
		return jButton6;
	}
	/**
	 * This method initializes jButton12
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton12() {
		if(jButton12 == null) {
			jButton12 = new javax.swing.JButton();
			jButton12.setPreferredSize(new java.awt.Dimension(120,40));
			jButton12.setBackground(new java.awt.Color(226,226,222));
			jButton12.setMargin(new Insets(1,2,1,1));
			jButton12.setText("F12 Salir");
			jButton12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton12.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton12.setEnabled(false);
		}
		return jButton12;
	}
	/**
	 * This method initializes jLabel4
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel4() {
		if(jLabel4 == null) {
			jLabel4 = new javax.swing.JLabel();
			jLabel4.setText("Titular(es):");
			jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.TRAILING);
			jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
			jLabel4.setPreferredSize(new java.awt.Dimension(100,19));
			jLabel4.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
			jLabel4.setFocusable(false);
		}
		return jLabel4;
	}
	/**
	 * This method initializes jLabel5
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel5() {
		if(jLabel5 == null) {
			jLabel5 = new javax.swing.JLabel();
			jLabel5.setText("Evento:");
			jLabel5.setPreferredSize(new java.awt.Dimension(100,19));
			jLabel5.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
			jLabel5.setFocusable(false);
		}
		return jLabel5;
	}
	/**
	 * This method initializes jLabel6
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel6() {
		if(jLabel6 == null) {
			jLabel6 = new javax.swing.JLabel();
			jLabel6.setText("Fecha:");
			jLabel6.setPreferredSize(new java.awt.Dimension(100,19));
			jLabel6.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
			jLabel6.setFocusable(false);
		}
		return jLabel6;
	}
	/**
	 * This method initializes jLabel7
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel7() {
		if(jLabel7 == null) {
			jLabel7 = new javax.swing.JLabel();
			jLabel7.setText("Total:");
			jLabel7.setPreferredSize(new java.awt.Dimension(100,25));
			jLabel7.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jLabel7.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
			jLabel7.setFocusable(false);
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
			jLabel8.setText("Cant. Productos:");
			jLabel8.setPreferredSize(new java.awt.Dimension(100,25));
			jLabel8.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
			jLabel8.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jLabel8.setFocusable(false);
		}
		return jLabel8;
	}
	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton4() {
		if(jButton4 == null) {
			jButton4 = new javax.swing.JButton();
			jButton4.setPreferredSize(new java.awt.Dimension(120,40));
			jButton4.setBackground(new java.awt.Color(226,226,222));
			jButton4.setMargin(new Insets(1,2,1,1));
			jButton4.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton4.setText("F4 Completar Registro");
		}
		return jButton4;
	}
	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton1() {
		if(jButton1 == null) {
			jButton1 = new javax.swing.JButton();
			jButton1.setText("F1 Agregar Producto");
			jButton1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton1.setPreferredSize(new java.awt.Dimension(120,40));
			jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton1.setBackground(new java.awt.Color(226,226,222));
			jButton1.setMargin(new Insets(1,2,1,1));
			jButton1.setEnabled(true);
		}
		return jButton1;
	}
	
	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel2() {

		if(jPanel2 == null) {
			jPanel2 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout16 = new java.awt.FlowLayout();
			layFlowLayout16.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout16.setHgap(1);
			layFlowLayout16.setVgap(1);
			jPanel2.setLayout(layFlowLayout16);
			jPanel2.add(getJScrollPane(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(656,230));
			jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle de Lista de Regalos: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), java.awt.Color.black));
			jPanel2.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel2;
	}
	
	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private javax.swing.JScrollPane getJScrollPane() {
		if(jScrollPane == null) {
			jScrollPane = new javax.swing.JScrollPane();
			jScrollPane.setViewportView(getJTable());
			jScrollPane.setPreferredSize(new java.awt.Dimension(645,205));
			jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private javax.swing.JTable getJTable() {
		if(jTable == null) {
			jTable = new javax.swing.JTable(modeloTablaRegistroLR);
			jTable.getTableHeader().setReorderingAllowed(false) ;
			//jTable.setPreferredSize(new java.awt.Dimension(375,200));
			jTable.getColumnModel().getColumn(0).setPreferredWidth(80);
			jTable.getColumnModel().getColumn(1).setPreferredWidth(160);
			jTable.getColumnModel().getColumn(2).setPreferredWidth(40);
			jTable.getColumnModel().getColumn(3).setPreferredWidth(80);
			jTable.getColumnModel().getColumn(4).setPreferredWidth(80);
			jTable.setBackground(new java.awt.Color(242,242,238));
			jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return jTable;
	}
	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel2() {
		if(jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setPreferredSize(new java.awt.Dimension(100,19));
			jLabel2.setFocusable(false);
		}
		return jLabel2;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel5() {
		if(jPanel5 == null) {
			jPanel5 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout11 = new java.awt.FlowLayout();
			layFlowLayout11.setHgap(1);
			layFlowLayout11.setVgap(5);
			jPanel5.setLayout(layFlowLayout11);
			jPanel5.add(getJLabel8(), null);
			jPanel5.add(getCantProductosTF(), null);
			jPanel5.add(getJLabel11(), null);
			jPanel5.add(getSubTotalTF(), null);
			jPanel5.add(getJLabel25(), null);
			jPanel5.add(getIvaTF(), null);
			jPanel5.add(getJLabel7(), null);
			jPanel5.add(getTotalTF(), null);
			jPanel5.setPreferredSize(new java.awt.Dimension(328,170));
			jPanel5.setBackground(new java.awt.Color(242,242,238));
			jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalles de la Lista:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), java.awt.Color.black));
		}
		return jPanel5;
	}
	/**
	 * This method initializes jLabel11
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel11() {
		if(jLabel11 == null) {
			jLabel11 = new javax.swing.JLabel();
			jLabel11.setText("Subtotal:");
			jLabel11.setPreferredSize(new java.awt.Dimension(100,25));
			jLabel11.setFocusable(false);
		}
		return jLabel11;
	}
	/**
	 * This method initializes jLabel25
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel25() {
		if(jLabel25 == null) {
			jLabel25 = new javax.swing.JLabel();
			jLabel25.setText("IVA:");
			jLabel25.setPreferredSize(new java.awt.Dimension(100,25));
			jLabel25.setFocusable(false);
		}
		return jLabel25;
	}
	/**
	 * This method initializes jLabel27
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel27() {
		if(jLabel27 == null) {
			jLabel27 = new javax.swing.JLabel();
			jLabel27.setText("Notificaciones:");
			jLabel27.setPreferredSize(new java.awt.Dimension(100,19));
			jLabel27.setFocusable(false);
		}
		return jLabel27;
	}
	/**
	 * This method initializes subTotalTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getSubTotalTF() {
		if(subTotalTF == null) {
			subTotalTF = new javax.swing.JTextField();
			subTotalTF.setPreferredSize(new java.awt.Dimension(200,25));
			subTotalTF.setEditable(false);
			subTotalTF.setBackground(new java.awt.Color(242,242,238));
			subTotalTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			subTotalTF.setText("0,00");
			subTotalTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			subTotalTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			subTotalTF.setFocusable(false);
		}
		return subTotalTF;
	}
	/**
	 * This method initializes ivaTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getIvaTF() {
		if(ivaTF == null) {
			ivaTF = new javax.swing.JTextField();
			ivaTF.setPreferredSize(new java.awt.Dimension(200,25));
			ivaTF.setEditable(false);
			ivaTF.setBackground(new java.awt.Color(242,242,238));
			ivaTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			ivaTF.setText("0,00");
			ivaTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			ivaTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			ivaTF.setFocusable(false);
		}
		return ivaTF;
	}
	/**
	 * This method initializes totalTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTotalTF() {
		if(totalTF == null) {
			totalTF = new javax.swing.JTextField();
			totalTF.setPreferredSize(new java.awt.Dimension(200,25));
			totalTF.setEditable(false);
			totalTF.setBackground(new java.awt.Color(242,242,238));
			totalTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			totalTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			totalTF.setText("0,00");
			totalTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			totalTF.setFocusable(false);
		}
		return totalTF;
	}
	/**
	 * This method initializes cantProductosTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getCantProductosTF() {
		if(cantProductosTF == null) {
			cantProductosTF = new javax.swing.JTextField();
			cantProductosTF.setEditable(false);
			cantProductosTF.setPreferredSize(new java.awt.Dimension(200,25));
			cantProductosTF.setBackground(new java.awt.Color(242,242,238));
			cantProductosTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			cantProductosTF.setText("0");
			cantProductosTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			cantProductosTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			cantProductosTF.setFocusable(false);
		}
		return cantProductosTF;
	}
	/**
	 * This method initializes titularTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTitularTF() {
		if(titularTF == null) {
			titularTF = new javax.swing.JTextField();
			titularTF.setPreferredSize(new java.awt.Dimension(210,19));
			titularTF.setEditable(false);
			titularTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			titularTF.setHorizontalAlignment(javax.swing.JTextField.LEADING);
			titularTF.setBackground(new java.awt.Color(242,242,238));
			titularTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			titularTF.setFocusable(false);
		}
		return titularTF;
	}
	/**
	 * This method initializes titularSecTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTitularSecTF() {
		if(titularSecTF == null) {
			titularSecTF = new javax.swing.JTextField();
			titularSecTF.setPreferredSize(new java.awt.Dimension(210,19));
			titularSecTF.setEnabled(true);
			titularSecTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			titularSecTF.setEditable(false);
			titularSecTF.setBackground(new java.awt.Color(242,242,238));
			titularSecTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			titularSecTF.setFocusable(false);
		}
		return titularSecTF;
	}
	/**
	 * This method initializes eventoTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getEventoTF() {
		if(eventoTF == null) {
			eventoTF = new javax.swing.JTextField();
			eventoTF.setPreferredSize(new java.awt.Dimension(210,19));
			eventoTF.setEditable(false);
			eventoTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			eventoTF.setBackground(new java.awt.Color(242,242,238));
			eventoTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			eventoTF.setFocusable(false);
		}
		return eventoTF;
	}
	/**
	 * This method initializes fechaEventoTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getFechaEventoTF() {
		if(fechaEventoTF == null) {
			fechaEventoTF = new javax.swing.JTextField();
			fechaEventoTF.setPreferredSize(new java.awt.Dimension(210,19));
			fechaEventoTF.setEditable(false);
			fechaEventoTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			fechaEventoTF.setBackground(new java.awt.Color(242,242,238));
			fechaEventoTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			fechaEventoTF.setFocusable(false);
		}
		return fechaEventoTF;
	}
	/**
	 * This method initializes notificacionesTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getNotificacionesTF() {
		if(notificacionesTF == null) {
			notificacionesTF = new javax.swing.JTextField();
			notificacionesTF.setPreferredSize(new java.awt.Dimension(210,19));
			notificacionesTF.setEditable(false);
			notificacionesTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			notificacionesTF.setBackground(new java.awt.Color(242,242,238));
			notificacionesTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			notificacionesTF.setFocusable(false);
		}
		return notificacionesTF;
	}	
	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		if(jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setText("Tipo de Lista:");
			jLabel.setPreferredSize(new java.awt.Dimension(100,19));
			jLabel.setFocusable(false);
		}
		return jLabel;
	}
	/**
	 * This method initializes tipoListaTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTipoListaTF() {
		if(tipoListaTF == null) {
			tipoListaTF = new javax.swing.JTextField();
			tipoListaTF.setPreferredSize(new java.awt.Dimension(210,19));
			tipoListaTF.setEditable(false);
			tipoListaTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			tipoListaTF.setBackground(new java.awt.Color(242,242,238));
			tipoListaTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			tipoListaTF.setFocusable(false);
		}
		return tipoListaTF;
	}
	
	/* (no Javadoc)
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 */
	public void changedUpdate(DocumentEvent e) {

	}

	/* (no Javadoc)
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 */
	public void insertUpdate(DocumentEvent e) {
		if (lector == null)
			lector = new EfectuarLecturaEscaner(this);
		else
			lector.iniciar();
	}

	public synchronized void leerEscaner(final String codigoScaner){
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					lecturaEscaner(codigoScaner);
				}});
		} catch (InterruptedException e2) {
		} catch (InvocationTargetException e2) {
		}
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	public void lecturaEscaner(String codigoScaner) {
		try {
			ArrayList <Object>valorLeido = Control.codigoValido(codigoScaner);
			int tipoCodigo = ((Integer)valorLeido.get(0)).intValue();
			String codigo = (String)valorLeido.get(1);
			switch (tipoCodigo){
				case Control.PRODUCTO:
					CR.meServ.ingresarProductoLR(codigo, Sesion.capturaEscaner);
					break;
				case Control.CLIENTE:
					CR.meServ.asignarCliente(codigo);
					break;
				case Control.COLABORADOR:
					CR.meServ.asignarCliente(codigo);
					break;
				case Control.CODIGO_DESCONOCIDO: 
					try{
						CR.meServ.ingresarProductoLR(codigoScaner.substring(0,Control.getLONGITUD_CODIGO()-1),Sesion.capturaEscaner);
					}catch(Exception ex1){
						try {
							CR.meServ.ingresarProductoLR(codigoScaner, Sesion.capturaEscaner);
						} catch (Exception ex2) {
							CR.meServ.asignarCliente(codigo);
						}
					}
					break;
			}
		} catch (AfiliadoUsrExcepcion e1) {
			MensajesVentanas.mensajeError(e1.getMensaje());
		} catch (UsuarioExcepcion e1) {
			MensajesVentanas.mensajeError(e1.getMensaje());
		} catch (AutorizacionExcepcion e1) {
			MensajesVentanas.mensajeError(e1.getMensaje());
		} catch (ExcepcionCr e1) {
			MensajesVentanas.mensajeError("Código no registrado");
		} catch (ConexionExcepcion e1) {
			MensajesVentanas.mensajeError(e1.getMensaje());
		}
		this.repintarPantalla();
	}

	/* (no Javadoc)
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
	 */
	public void removeUpdate(DocumentEvent e) {
		
	}
	
	/**
	 * Método repintarFactura.
	 */
	private void repintarPantalla(){
		try {
			ListaRegalos listaRegalos = CR.meServ.getListaRegalos();
		
			modeloTablaRegistroLR.llenarTablaRegistroLR();
			// Mueve el foco de la tabla a la fila del último producto ingresado. 
			if(modeloTablaRegistroLR.getRowCount()>0){
				getJTable().setRowSelectionInterval(modeloTablaRegistroLR.getRowCount()-1, modeloTablaRegistroLR.getRowCount()-1);
				getJTable().setColumnSelectionInterval(1, 1); 
				getJTable().scrollRectToVisible(getJTable().getCellRect(getJTable().getSelectedRow(), 1, true));
			}
			if(CR.meServ.getListaRegalos() != null) {
				if (listaRegalos.getCliente().getCodCliente()!= null ){
					titularTF.setText(listaRegalos.getCliente().getNombreCompleto());
					CR.me.setCliente(listaRegalos.getCliente().getNombreCompleto(),listaRegalos.getCliente().getCodCliente());
				}
				titularSecTF.setText(listaRegalos.getTitularSec());
				fechaEventoTF.setText(new SimpleDateFormat("dd-MM-yyyy").format(listaRegalos.getFechaEvento()));
				getEventoTF().setText(listaRegalos.getTipoEvento()); // Tipo de Evento
				if(listaRegalos.isNotificaciones()) // Notificaciones
					getNotificacionesTF().setText("Enviar");
				else 
					getNotificacionesTF().setText("No Enviar");
				if(listaRegalos.getTipoLista() == 'N')
					getTipoListaTF().setText("No Garantizada");
				else
					getTipoListaTF().setText("Garantizada");
				if(listaRegalos.isPermitirVenta())
					getPermitirVentaTF().setText("Permitir");
				else
					getPermitirVentaTF().setText("No Permitir");
								
				getCantProductosTF().setText(String.valueOf(listaRegalos.getCantPedidos()));
				getSubTotalTF().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(listaRegalos.getMontoBase())));
				getIvaTF().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(listaRegalos.getMontoImpuesto())));
				getTotalTF().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(listaRegalos.getMontoBase() + listaRegalos.getMontoImpuesto())));
			}
		} catch (Exception ex) {
			CR.me.setCliente("","");
			titularTF.setText("");
			titularSecTF.setText("");
			fechaEventoTF.setText("");
			notificacionesTF.setText("");
			permitirVentaTF.setText("");
			getCantProductosTF().setText("0");
			getSubTotalTF().setText(df.format(0));
			getIvaTF().setText(df.format(0));
			getTotalTF().setText(df.format(0));
		}
	}

	public void actionPerformed(ActionEvent e) {
		eliminarListeners(); 
		CR.inputEscaner.getDocument().removeDocumentListener(this);
		
		/* Boton "Agregar Producto" */
		if(e.getSource() == jButton1){
			if(CR.meServ.getListaRegalos().getDetallesServicio().size() > 0){
				CambiarCantidad cc;
				if(jTable.getSelectedRow() != -1){
					filaAModificar = jTable.getSelectedRow();
					cc = new CambiarCantidad(filaAModificar, 3);
				} else
					cc = new CambiarCantidad(3);
				MensajesVentanas.centrarVentanaDialogo(cc);
			} else
				MensajesVentanas.aviso("No Existen productos en la Lista de Regalos");
			this.repintarPantalla();
		}
		
		/* Boton "Eliminar Producto" */
		else if(e.getSource() == jButton2){
			if(CR.meServ.getListaRegalos().getDetallesServicio().size() > 0){
				if(jTable.getSelectedRow() == -1){
					AnularProducto anularProducto = new AnularProducto(CR.meServ.getListaRegalos().getDetallesServicio().size()-1, 1);
					MensajesVentanas.centrarVentanaDialogo(anularProducto);
					this.repintarPantalla();
				} else {
					AnularProducto anularProducto = new AnularProducto(jTable.getSelectedRow(), 1);
					MensajesVentanas.centrarVentanaDialogo(anularProducto);
					this.repintarPantalla();	
				}
			} else
				MensajesVentanas.aviso("No Existen productos en la Lista de Regalos");
			this.repintarPantalla();
		}
		
		/* Boton "Detalles del evento" */
		else if(e.getSource().equals(jButton3)){
			datosLista = new DatosLista(CR.meServ.getListaRegalos());
			MensajesVentanas.centrarVentanaDialogo(datosLista);
			this.repintarPantalla();
		}

		/* Botón "Completar Registro" */
		else if(e.getSource().equals(jButton4)){
			if (CR.meServ.getListaRegalos().getTitular()==null || CR.meServ.getListaRegalos().getTitular().getCodCliente()=="")
				MensajesVentanas.aviso("Debe asignar un cliente titular a la lista");
			else if(CR.meServ.getListaRegalos().getTipoLista() == ListaRegalos.GARANTIZADA &&
				CR.meServ.getListaRegalos().getMontoBase() < CR.meServ.getListaRegalos().getMontoAperturaLG())
				MensajesVentanas.aviso("El monto mínimo de apertura para una\n Lista Garantizada es "+Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meServ.getListaRegalos().getMontoAperturaLG())));
			else if(CR.meServ.getListaRegalos().getDetallesServicio().size() < 1)
				MensajesVentanas.aviso("Debe ingresar productos a la lista");
			else if(MensajesVentanas.preguntarSiNo("¿Confirma que desea completar el Registro?")==0)
				try {
					CR.meServ.registrarListaRegalos();
					CR.meServ.finalizarListaRegalos();
					//MensajesVentanas.aviso("Registro completado");
					dispose();
					CR.me.repintarMenuPrincipal();
				} catch (UsuarioExcepcion e1) {
					e1.printStackTrace();
					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (MaquinaDeEstadoExcepcion e1) {
					e1.printStackTrace();
					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (ConexionExcepcion e1) {
					e1.printStackTrace();
					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (ExcepcionCr e1) {
					e1.printStackTrace();
					MensajesVentanas.mensajeError(e1.getMensaje());
				}
		}
				
		/* Boton "Consultas" */
		else if(e.getSource().equals(jButton5)){
			Consultas consultas = new Consultas();
			MensajesVentanas.centrarVentanaDialogo(consultas);
			String casoConsulta = consultas.getCodigoSeleccionado()[0];
			String codigo = consultas.getCodigoSeleccionado()[1];
			if (casoConsulta != null)
				try {
					if (new Integer(casoConsulta).intValue() < 8)
						CR.meServ.ingresarProductoLR(codigo, Sesion.capturaTeclado);
					else {
						CR.meServ.asignarTitularLR(codigo);
						titularTF.setText(CR.meServ.getListaRegalos().getTitular().getNombreCompleto());
					}
				}catch (ClienteExcepcion e1) {
					MensajesVentanas.aviso(e1.getMensaje()); 
				} catch (ExcepcionCr e1) {
					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (ConexionExcepcion e1) {
					MensajesVentanas.mensajeError(e1.getMensaje());
				}
			this.repintarPantalla();
		}
		
		/* Botón Colocar En Espera */
		else if(e.getSource().equals(jButton6)){
			
			if(CR.meServ.getListaRegalos().getCliente() == null || CR.meServ.getListaRegalos().getCliente().getCodCliente()==null){
				Identificacion id = new Identificacion();
				MensajesVentanas.centrarVentanaDialogo(id);
				String identificacion = id.getCantidad();
				if(identificacion != null)
					if(!(identificacion.trim()).equals("")){
						int numTienda = Sesion.getTienda().getNumero();
						int numCaja = Sesion.getCaja().getNumero();
						char tipoTransaccion = ListaRegalos.REGISTRO;
						try {
							CR.meServ.colocarListaEnEspera(identificacion,numTienda,numCaja,tipoTransaccion);
							this.dispose();
							CR.me.repintarMenuPrincipal();
						} catch (UsuarioExcepcion e1) {
							e1.printStackTrace();
							MensajesVentanas.mensajeError(e1.getMensaje());
						} catch (MaquinaDeEstadoExcepcion e1) {
							e1.printStackTrace();
							MensajesVentanas.mensajeError(e1.getMensaje());
						} catch (ConexionExcepcion e1) {
							e1.printStackTrace();
							MensajesVentanas.mensajeError(e1.getMensaje());
						} catch (ExcepcionCr e1) {
							e1.printStackTrace();
							MensajesVentanas.mensajeError(e1.getMensaje());
						}
					} else
						MensajesVentanas.aviso("Para colocar la lista en espera\ndebe ingresar un identificador");
			} else if(MensajesVentanas.preguntarSiNo("¿Está seguro que desea colocar en espera?")==0){
				int numTienda = Sesion.getTienda().getNumero();
				int numCaja = Sesion.getCaja().getNumero();
				char tipoTransaccion = ListaRegalos.REGISTRO;
				String identificacion = CR.meServ.getListaRegalos().getCliente().getCodCliente();
				try {
					CR.meServ.colocarListaEnEspera(identificacion,numTienda,numCaja,tipoTransaccion);
					this.dispose();
					CR.me.repintarMenuPrincipal();
				} catch (UsuarioExcepcion e1) {
					e1.printStackTrace();
					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (MaquinaDeEstadoExcepcion e1) {
					e1.printStackTrace();
					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (ConexionExcepcion e1) {
					e1.printStackTrace();
					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (ExcepcionCr e1) {
					e1.printStackTrace();
					MensajesVentanas.mensajeError(e1.getMensaje());
				}
			}
/*			if(MensajesVentanas.preguntarSiNo("¿Confirma que desea colocar la lista en espera?")==0){
				int numTienda = Sesion.getTienda().getNumero();
				int numCaja = Sesion.getCaja().getNumero();
				char tipoTransaccion = ListaRegalos.REGISTRO;
				try {
					CR.meServ.colocarListaEnEspera(numTienda,numCaja,tipoTransaccion);
					this.dispose();
					CR.me.repintarMenuPrincipal();
				} catch (UsuarioExcepcion e1) {
					e1.printStackTrace();
					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (MaquinaDeEstadoExcepcion e1) {
					e1.printStackTrace();
					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (ConexionExcepcion e1) {
					e1.printStackTrace();
					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (ExcepcionCr e1) {
					e1.printStackTrace();
					MensajesVentanas.mensajeError(e1.getMensaje());
				}
			}*/
		}
		
		/* Botón "Ingresar Lote PDT" */
		else if(e.getSource().equals(jButton7)){
			IngresoNumLote in = new IngresoNumLote();
			MensajesVentanas.centrarVentanaDialogo(in);
			String numLote = in.getNumLote();
			Vector<String[]> noEncontrados;
			if(numLote == null){}
			else if(numLote.trim().equals(""))
				MensajesVentanas.mensajeError("Debe ingresar el número de lote \nasignado por el PDT");
			else {
				try {
					noEncontrados = CR.meServ.ingresarLoteProductosLR(numLote);
					String mensaje = null; 
					if(noEncontrados.size()>0){
						mensaje = "Productos inexistentes en la BD:";
						for(int i=0;i<noEncontrados.size();i++){
							mensaje += "\n";
							mensaje += ((String[])noEncontrados.get(i))[0];
						}
						MensajesVentanas.aviso(mensaje);
					}				
				} catch (UsuarioExcepcion e1) {
					e1.printStackTrace();
					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (MaquinaDeEstadoExcepcion e1) {
					e1.printStackTrace();
					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (ConexionExcepcion e1) {
					e1.printStackTrace();
					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (ExcepcionCr e1) {
					e1.printStackTrace();
					MensajesVentanas.mensajeError(e1.getMensaje());
				}

				this.repintarPantalla();
			}
		}

		/* Boton Cancelar */
		else if(e.getSource().equals(jButton9)){
			if(MensajesVentanas.preguntarSiNo("¿Confirma que desea cancelar el registro?")==0){
				try {
					CR.meServ.finalizarListaRegalos();
					dispose();
					finalize();
				} catch (UsuarioExcepcion e2) {
					e2.printStackTrace();
				} catch (MaquinaDeEstadoExcepcion e2) {
					e2.printStackTrace();
				} catch (ConexionExcepcion e2) {
					e2.printStackTrace();
				} catch (ExcepcionCr e2) {
					e2.printStackTrace();
				} catch (Throwable e2) {
					e2.printStackTrace();
				}
				CR.me.repintarMenuPrincipal();
			}
		}

		/* Botón Gaveta */
		else if(e.getSource().equals(jButton10))
			try {
				CR.me.abrirGaveta(true);
			} catch (ConexionExcepcion e1) {
				MensajesVentanas.mensajeError(e1.getMensaje());
			} catch (ExcepcionCr e1) {
				MensajesVentanas.mensajeError(e1.getMensaje());
			}
		else if(e.getSource().equals(jButton11))
			try {
				boolean validarClaveAlSalir = true;
				try {
					validarClaveAlSalir = InitCR.preferenciasCR.getConfigStringForParameter("facturacion","validarClaveAlSalir").trim().toUpperCase().equals("S")?true:false;
				} catch (NoSuchNodeException e1) {
					MensajesVentanas.mensajeError("Falla carga de preferencias para Validación de Clave al Salir");
				} catch (UnidentifiedPreferenceException e1) {
				}
				if(MensajesVentanas.preguntarSiNo("¿Confirma que desea bloquear la caja?")==0)
					if (validarClaveAlSalir) {
						ValidarPassword vp = new ValidarPassword();
						MensajesVentanas.centrarVentanaDialogo(vp);
						if (vp.isClaveCorrecta()) {
							CR.me.bloquearCaja();
							principal.dispose();
							dispose();
						}
					} else {
						CR.me.bloquearCaja();
						principal.dispose();
						dispose();
					}
			} catch (UsuarioExcepcion e1) {
				MensajesVentanas.mensajeError(e1.getMensaje());
			} catch (MaquinaDeEstadoExcepcion e1) {
				MensajesVentanas.mensajeError(e1.getMensaje());
			} catch (ConexionExcepcion e1) {
				MensajesVentanas.mensajeError(e1.getMensaje());
			} catch (ExcepcionCr e1) {
				MensajesVentanas.mensajeError(e1.getMensaje());
			}
		else if(e.getSource().equals(jButton12)){
			if(MensajesVentanas.preguntarSiNo("¿Desea salir sin completar el registro?")==0){			
				try {
					CR.meServ.finalizarListaRegalos();
					dispose();
					finalize();
				} catch (UsuarioExcepcion e2) {
					e2.printStackTrace();
				} catch (MaquinaDeEstadoExcepcion e2) {
					e2.printStackTrace();
				} catch (ConexionExcepcion e2) {
					e2.printStackTrace();
				} catch (ExcepcionCr e2) {
					e2.printStackTrace();
				} catch (Throwable e2) {
					e2.printStackTrace();
				}
				CR.me.repintarMenuPrincipal();
			}
		}
		
		/* Botón "Asignar Clientes" */
		else if(e.getSource().equals(jButton13)) {
			EjecutarConCantidad ec = null;
			ec = new EjecutarConCantidad("Asignar un Cliente:", "",14);
			//titularTF.setText(CR.meServ.getListaRegalos().getTitular().getNombreCompleto());
			MensajesVentanas.centrarVentanaDialogo(ec);
			this.repintarPantalla();
			getJTable().requestFocus();
		}
		
		agregarListeners();
		CR.inputEscaner.getDocument().addDocumentListener(this);
	}

	/* (no Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_F1)
			getJButton1().doClick();
		else if(e.getKeyCode() == KeyEvent.VK_F2)
			getJButton2().doClick();
		else if(e.getKeyCode() == KeyEvent.VK_F3)
			getJButton3().doClick();
		else if(e.getKeyCode() == KeyEvent.VK_F4)
			getJButton4().doClick();
		else if(e.getKeyCode() == KeyEvent.VK_F5)
			getJButton5().doClick();
		else if(e.getKeyCode() == KeyEvent.VK_F6)
			getJButton6().doClick();
		else if(e.getKeyCode() == KeyEvent.VK_F7)
			getJButton7().doClick();
		else if(e.getKeyCode() == KeyEvent.VK_F9)
			getJButton9().doClick();
		else if(e.getKeyCode() == KeyEvent.VK_F10)
			getJButton10().doClick();
		else if(e.getKeyCode() == KeyEvent.VK_F11)
			getJButton11().doClick();
		else if(e.getKeyCode() == KeyEvent.VK_F12)
			getJButton12().doClick();
		else if(e.getKeyCode() == KeyEvent.VK_A) {
			eliminarListeners();
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			// Mostramos una ventana para que el usuario introduzca el código del cliente
			EjecutarConCantidad ec =null;
			ec = new EjecutarConCantidad("Asignar un Cliente:", "",7);
			MensajesVentanas.centrarVentanaDialogo(ec);
			this.repintarPantalla();
			getJTable().requestFocus();
			CR.inputEscaner.getDocument().addDocumentListener(this);
			agregarListeners();
		}else if (Character.isDigit(e.getKeyChar())) {
			eliminarListeners();
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			// Mostramos una ventana para que el usuario introduzca el código del producto
			EjecutarConCantidad ec = new EjecutarConCantidad("Ingreso de Código:", String.valueOf(e.getKeyChar()),11);
			MensajesVentanas.centrarVentanaDialogo(ec);
			this.repintarPantalla();			
			CR.inputEscaner.getDocument().addDocumentListener(this);
			agregarListeners();
		}
	}

	/* (no Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
	}

	/* (no Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
	}

	
	private void agregarListeners(){
		
		jButton1.addActionListener(this);
		jButton1.addKeyListener(this);
		
		jButton2.addActionListener(this);
		jButton2.addKeyListener(this);
		
		jButton3.addActionListener(this);
		jButton3.addKeyListener(this);
		
		jButton4.addActionListener(this);
		jButton4.addKeyListener(this);
		
		jButton5.addActionListener(this);
		jButton5.addKeyListener(this);
		
		jButton6.addActionListener(this);
		jButton6.addKeyListener(this);
		
		jButton7.addActionListener(this);
		jButton7.addKeyListener(this);
		
		jButton9.addActionListener(this);
		jButton9.addKeyListener(this);
		
		jButton10.addActionListener(this);
		jButton10.addKeyListener(this);
		
		jButton11.addActionListener(this);
		jButton11.addKeyListener(this);
		
		jButton12.addActionListener(this);
		jButton12.addKeyListener(this);
		
		jButton13.addActionListener(this);
		jButton13.addKeyListener(this);
					
		jTable.addKeyListener(this);
	}
	
	private void eliminarListeners(){
		
		jButton1.removeActionListener(this);
		jButton1.removeKeyListener(this);
		
		jButton2.removeActionListener(this);
		jButton2.removeKeyListener(this);
		
		jButton3.removeActionListener(this);
		jButton3.removeKeyListener(this);
		
		jButton4.removeActionListener(this);
		jButton4.removeKeyListener(this);
		
		jButton5.removeActionListener(this);
		jButton5.removeKeyListener(this);
		
		jButton6.removeActionListener(this);
		jButton6.removeKeyListener(this);
		
		jButton7.removeActionListener(this);
		jButton7.removeKeyListener(this);
		
		jButton9.removeActionListener(this);
		jButton9.removeKeyListener(this);
		
		jButton10.removeActionListener(this);
		jButton10.removeKeyListener(this);
		
		jButton11.removeActionListener(this);
		jButton11.removeKeyListener(this);
		
		jButton12.removeActionListener(this);
		jButton12.removeKeyListener(this);
		
		jButton13.removeActionListener(this);
		jButton13.removeKeyListener(this);

		jTable.removeKeyListener(this);
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.gui.ScanerSwitch#isPermitirEscaner()
	 */
	public boolean isPermitirEscaner() {
		return permitirEscaner;
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.gui.ScanerSwitch#setPermitirEscaner(boolean)
	 */
	public void setPermitirEscaner(boolean permitirEscaner) {
		this.permitirEscaner = permitirEscaner;		
	}
	/**
	 * This method initializes jButton7
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton7() {
		if(jButton7 == null) {
			jButton7 = new javax.swing.JButton();
			jButton7.setPreferredSize(new java.awt.Dimension(120,40));
			jButton7.setBackground(new java.awt.Color(226,226,222));
			jButton7.setText("F7 Ingresar Lote PDT");
			jButton7.setMargin(new Insets(1,2,1,1));
			jButton7.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		}
		return jButton7;
	}
	/**
	 * This method initializes jButton8
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton8() {
		if(jButton8 == null) {
			jButton8 = new javax.swing.JButton();
			jButton8.setPreferredSize(new java.awt.Dimension(120,40));
			jButton8.setBackground(new java.awt.Color(226,226,222));
			jButton8.setText("F8 Utilitarios");
			jButton8.setMargin(new Insets(1,2,1,1));
			jButton8.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton8.setEnabled(false);
		}
		return jButton8;
	}
	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel1() {
		if(jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setText("Permitir Venta:");
			jLabel1.setPreferredSize(new java.awt.Dimension(100,19));
		}
		return jLabel1;
	}
	/**
	 * This method initializes permitirVentaTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getPermitirVentaTF() {
		if(permitirVentaTF == null) {
			permitirVentaTF = new javax.swing.JTextField();
			permitirVentaTF.setPreferredSize(new java.awt.Dimension(210,19));
			permitirVentaTF.setEditable(false);
			permitirVentaTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			permitirVentaTF.setBackground(new java.awt.Color(242,242,238));
			permitirVentaTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			permitirVentaTF.setFocusable(false);
		}
		return permitirVentaTF;
	}
	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton13() {
		if(jButton13 == null) {
			jButton13 = new javax.swing.JButton();
			jButton13.setPreferredSize(new java.awt.Dimension(120,40));
			jButton13.setBackground(new java.awt.Color(226,226,222));
			jButton13.setText("Asignar Clientes");
			jButton13.setMnemonic(java.awt.event.KeyEvent.VK_A);
			jButton13.setMargin(new Insets(1,2,1,1));
			jButton13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton13.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
		}
		return jButton13;
	}
}
