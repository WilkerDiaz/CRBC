/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui.listaregalos
 * Programa   : ConsultaListaRegalos.java
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.excepciones.AfiliadoUsrExcepcion;
import com.becoblohm.cr.excepciones.AutorizacionExcepcion;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.MontoPagoExcepcion;
import com.becoblohm.cr.excepciones.PagoExcepcion;
import com.becoblohm.cr.excepciones.ProductoExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.gui.EjecutarConCantidad;
import com.becoblohm.cr.gui.EntregaEditor;
import com.becoblohm.cr.gui.EntregaRenderer;
import com.becoblohm.cr.gui.FacturacionPrincipal;
import com.becoblohm.cr.gui.ModeloTabla;
import com.becoblohm.cr.gui.PanelIconosCR;
import com.becoblohm.cr.gui.PanelMensajesCR;
import com.becoblohm.cr.gui.ScanerSwitch;
import com.becoblohm.cr.gui.ValidarPassword;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.extensiones.ManejoPagosFactory;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.manejarservicio.DetalleServicio;
import com.becoblohm.cr.manejarservicio.ListaRegalos;
import com.becoblohm.cr.manejarservicio.OperacionLR;
import com.becoblohm.cr.manejarventa.DetalleTransaccion;
import com.becoblohm.cr.manejarventa.Venta;
import com.becoblohm.cr.perifericos.EfectuarLecturaEscaner;
import com.becoblohm.cr.utiles.Control;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.crprinterdriver.PrinterNotConnectedException;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;

/**
 * @author rabreu
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class ConsultaListaRegalos extends JDialog implements ActionListener, KeyListener, DocumentListener, ScanerSwitch {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ConsultaListaRegalos.class);
			
	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JButton jButton12 = null;
	private javax.swing.JButton jButton11 = null;
	private javax.swing.JButton jButton2 = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JButton jButton4 = null;
	private javax.swing.JButton jButton10 = null;
	private javax.swing.JButton jButton6 = null;
	private javax.swing.JButton jButton8 = null;
	private javax.swing.JButton jButton9 = null;
	private javax.swing.JLabel jLabel4 = null;
	private javax.swing.JLabel jLabel5 = null;
	private javax.swing.JLabel jLabel6 = null;
	private javax.swing.JLabel jLabel12 = null;
	private javax.swing.JLabel jLabel13 = null;
	private javax.swing.JLabel jLabel14 = null;
	private javax.swing.JTextField jTextField = null;
	private javax.swing.JLabel jLabel15 = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JScrollPane jScrollPane = null;
	private javax.swing.JScrollPane jScrollPane1 = null;
	private javax.swing.JTable jTableInvitado = null;
	private javax.swing.JTable jTableAbonos = null;
	private javax.swing.JTable jTableFactura = null;
	private DecimalFormat df = new DecimalFormat("#,##0.00");
	private boolean permitirEscaner = true;
	private EfectuarLecturaEscaner lector = null;
	
	private ModeloTablaLR tablaDetalleAbonos = new ModeloTablaLR(5);
	private ModeloTablaLR tablaConsultaInvitado = new ModeloTablaLR(3);
	private ModeloTabla tablaFacturaLR = new ModeloTabla();
	public static int INICIAL = 0;
	public static int ABONANDO = 1;
	public static int FACTURANDO = 2;
	private int estado;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JPanel jPanel5 = null;
	private FacturacionPrincipal principal = null;
	
	private javax.swing.JButton jButton7 = null;
	private javax.swing.JTextField jTextField1 = null;
	private javax.swing.JTextField jTextField2 = null;
	private javax.swing.JTextField jTextField3 = null;
	private javax.swing.JTextField jTextField4 = null;
	private javax.swing.JTextField jTextField5 = null;
	private javax.swing.JTextField jTextField6 = null;
	private javax.swing.JTextField jTextField7 = null;
	private javax.swing.JButton jButton13 = null;
	
	private javax.swing.JButton jButton5 = null;
	private javax.swing.JButton jButton3 = null;
	
	/**
	 * Carga una lista de regalos y la muestra en la pantalla de consulta.
	 */
	public ConsultaListaRegalos(FacturacionPrincipal principal, String codLista) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, BaseDeDatosExcepcion, ExcepcionCr, ConexionExcepcion {
		super(MensajesVentanas.ventanaActiva);

		CR.meServ.cargarListaRegalos(codLista);
		this.principal = principal;
		initialize();
		
		getJTextField4().setText(CR.meServ.getListaRegalos().getTitular().getNombreCompleto());
		getJTextField5().setText(CR.meServ.getListaRegalos().getTitularSec());
		getJTextField6().setText(String.valueOf(CR.meServ.getListaRegalos().getTipoEvento()));
		Date fecha = CR.meServ.getListaRegalos().getFechaEvento();
		getJTextField7().setText(new SimpleDateFormat("dd-MM-yyyy").format(fecha));		
		estado = INICIAL;
		
		// TODO Para nueva version
		// Aviso de que es una Lista Garantizada y se vendió un producto en otra tienda
//		if(CR.meServ.getListaRegalos().getTipoLista()==ListaRegalos.GARANTIZADA){
//			Vector vendidos = CR.meServ.getListaRegalos().getVendidos
//			if(vendidos.size()>0)
//		}
		
		this.repintarPantalla();
		if(CR.meServ.getListaRegalos().isPermitirVenta())
			CR.inputEscaner.getDocument().addDocumentListener(this);
		agregarListeners();

	}
	
	public ConsultaListaRegalos(FacturacionPrincipal principal, int estado) {
		super(MensajesVentanas.ventanaActiva);
		this.principal = principal;
		this.estado = estado;
		initialize();
		getJTextField4().setText(CR.meServ.getListaRegalos().getTitular().getNombreCompleto());
		getJTextField5().setText(CR.meServ.getListaRegalos().getTitularSec());
		getJTextField6().setText(String.valueOf(CR.meServ.getListaRegalos().getTipoEvento()));
		Date fecha = CR.meServ.getListaRegalos().getFechaEvento();
		getJTextField7().setText(new SimpleDateFormat("dd-MM-yyyy").format(fecha));
		this.repintarPantalla();
		if(CR.meServ.getListaRegalos().isPermitirVenta())
			CR.inputEscaner.getDocument().addDocumentListener(this);
		agregarListeners();
	}
	
	public ConsultaListaRegalos(FacturacionPrincipal principal) {
		super(MensajesVentanas.ventanaActiva);
		
		// Se recupera el estado "Consulta Lista de Regalos" en la maquina de estado
		try {
			CR.meServ.recuperarEstadoLista(ListaRegalos.CONSULTA_INVITADO);
		} catch (MaquinaDeEstadoExcepcion e1) {
			e1.printStackTrace();
		} catch (ConexionExcepcion e1) {
			e1.printStackTrace();
		} catch (ExcepcionCr e1) {
			e1.printStackTrace();
		}
		
		// Se inicializan los valores de la interfaz
		this.principal = principal;
		initialize();
		getJTextField4().setText(CR.meServ.getListaRegalos().getTitular().getNombreCompleto());
		getJTextField5().setText(CR.meServ.getListaRegalos().getTitularSec());
		getJTextField6().setText(String.valueOf(CR.meServ.getListaRegalos().getTipoEvento()));
		if(CR.meVenta.getVenta() != null)
			this.estado = FACTURANDO;
		else if(CR.meServ.getListaRegalos().getDetalleAbonos() != null)
			this.estado = ABONANDO;
		else
			this.estado = INICIAL;
			
		try {
			Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(CR.meServ.getListaRegalos().getFechaEvento().toString());
			getJTextField7().setText(new SimpleDateFormat("dd-MM-yyyy").format(fecha));
		} catch (ParseException e) {}
		this.repintarPantalla();
		if(CR.meServ.getListaRegalos().isPermitirVenta())
			CR.inputEscaner.getDocument().addDocumentListener(this);
		agregarListeners();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setSize(810, 600);
		this.setContentPane(getJContentPane());
		this.setBackground(new java.awt.Color(226,226,222));
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setModal(true);
		this.setResizable(false);
		this.setName("FramePrincipalLR");
		this.setTitle("CR - Lista de Regalos");
		this.setLocale(new java.util.Locale("es", "VE", ""));
		this.setUndecorated(false);
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout21 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1);
			layFlowLayout21.setHgap(0);
			layFlowLayout21.setVgap(0);
			jContentPane.setLayout(layFlowLayout21);
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
			jPanel.add(getJPanel5(), null);
			jPanel.add(getJPanel3(), null);
			jPanel.add(getJPanel4(), null);
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
	 * This method initializes jButton12
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton12() {
		if(jButton12 == null) {
			jButton12 = new javax.swing.JButton();
			jButton12.setBackground(new java.awt.Color(226,226,222));
			jButton12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton12.setPreferredSize(new java.awt.Dimension(120,40));
			jButton12.setText("F12 Salir");
			jButton12.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton12.setHorizontalTextPosition(javax.swing.SwingConstants.TRAILING);
			jButton12.setActionCommand("F1 Facturar");
			jButton12.setMargin(new Insets(1,2,1,1));
			jButton12.setEnabled(false);
		}
		return jButton12;
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
			jPanel3.add(getJTextField4(), null);
			jPanel3.add(getJLabel2(), null);
			jPanel3.add(getJTextField5(), null);
			jPanel3.add(getJLabel5(), null);
			jPanel3.add(getJTextField6(), null);
			jPanel3.add(getJLabel6(), null);
			jPanel3.add(getJTextField7(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(328,138));
			jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalles de la Lista: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), java.awt.Color.black));
			jPanel3.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel3;
	}
	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel4() {
		if(jPanel4 == null) {
			jPanel4 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout5 = new java.awt.FlowLayout();
			layFlowLayout5.setHgap(1);
			layFlowLayout5.setVgap(1);
			layFlowLayout5.setAlignment(java.awt.FlowLayout.CENTER);
			jPanel4.setLayout(layFlowLayout5);
			jPanel4.add(getJLabel12(), null);
			jPanel4.add(getJTextField(), null);
			jPanel4.add(getJLabel13(), null);
			jPanel4.add(getJTextField1(), null);
			jPanel4.add(getJLabel14(), null);
			jPanel4.add(getJTextField2(), null);
			jPanel4.add(getJLabel15(), null);
			jPanel4.add(getJTextField3(), null);
			jPanel4.setPreferredSize(new java.awt.Dimension(328,138));
			jPanel4.setBackground(new java.awt.Color(242,242,238));
			jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Resumen Selección: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), java.awt.Color.black));
		}
		return jPanel4;
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
			jButton4.setText("F4 Totalizar");
			jButton4.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton4.setMargin(new Insets(1,2,1,1));
		}
		return jButton4;
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
	 * This method initializes jButton8
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton8() {
		if(jButton8 == null) {
			jButton8 = new javax.swing.JButton();
			jButton8.setPreferredSize(new java.awt.Dimension(120,40));
			jButton8.setBackground(new java.awt.Color(226,226,222));
			jButton8.setMargin(new Insets(1,2,1,1));
			jButton8.setText("F8 Abono a Lista");
			jButton8.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		}
		return jButton8;
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
			jButton5.setMargin(new Insets(1,2,1,1));
			jButton5.setText("F5 Consultas");
			jButton5.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		}
		return jButton5;
	}
	/**
	 * This method initializes jButton9
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton9() {
		if(jButton9 == null) {
			jButton9 = new javax.swing.JButton();
			jButton9.setPreferredSize(new java.awt.Dimension(120,40));
			jButton9.setBackground(new java.awt.Color(226,226,222));
			jButton9.setMargin(new Insets(1,2,1,1));
			jButton9.setText("F9 Cancelar");
			jButton9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton9.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
		}
		return jButton9;
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
			jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
			jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jLabel4.setPreferredSize(new java.awt.Dimension(80,24));
			jLabel4.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
			//jLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
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
			jLabel5.setPreferredSize(new java.awt.Dimension(80,24));
			jLabel5.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
			//jLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
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
			jLabel6.setPreferredSize(new java.awt.Dimension(80,24));
			jLabel6.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
			//jLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
			jLabel6.setFocusable(false);
		}
		return jLabel6;
	}
	/**
	 * This method initializes jLabel12
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel12() {
		if(jLabel12 == null) {
			jLabel12 = new javax.swing.JLabel();
			jLabel12.setText("Productos:");
			jLabel12.setPreferredSize(new java.awt.Dimension(90,24));
			jLabel12.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
			//jLabel12.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
			jLabel12.setFocusable(false);
		}
		return jLabel12;
	}
	/**
	 * This method initializes jLabel13
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel13() {
		if(jLabel13 == null) {
			jLabel13 = new javax.swing.JLabel();
			jLabel13.setText("Subtotal:");
			jLabel13.setPreferredSize(new java.awt.Dimension(90,24));
			jLabel13.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
			//jLabel13.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
			jLabel13.setFocusable(false);
		}
		return jLabel13;
	}
	/**
	 * This method initializes jLabel14
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel14() {
		if(jLabel14 == null) {
			jLabel14 = new javax.swing.JLabel();
			jLabel14.setText("IVA:");
			jLabel14.setPreferredSize(new java.awt.Dimension(90,24));
			jLabel14.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
			jLabel14.setFocusable(false);
		}
		return jLabel14;
	}
	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField() {
		if(jTextField == null) {
			jTextField = new javax.swing.JTextField();
			jTextField.setPreferredSize(new java.awt.Dimension(200,24));
			jTextField.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jTextField.setEditable(false);
			jTextField.setBackground(new java.awt.Color(242,242,238));
			jTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			jTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			jTextField.setFocusable(false);
		}
		return jTextField;
	}
	/**
	 * This method initializes jLabel15
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel15() {
		if(jLabel15 == null) {
			jLabel15 = new javax.swing.JLabel();
			jLabel15.setText("Total:");
			jLabel15.setPreferredSize(new java.awt.Dimension(90,24));
			jLabel15.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
			//jLabel15.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
			jLabel15.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jLabel15.setFocusable(false);
		}
		return jLabel15;
	}
	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton1() {
		if(jButton1 == null) {
			jButton1 = new javax.swing.JButton();
			jButton1.setText("F1 Venta de Producto");
			jButton1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton1.setPreferredSize(new java.awt.Dimension(120,40));
			jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton1.setBackground(new java.awt.Color(226,226,222));
			jButton1.setMargin(new Insets(1,2,1,1));
		}
		return jButton1;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
		if(CR.meServ.getListaRegalos().isPermitirVenta())
			CR.inputEscaner.getDocument().removeDocumentListener(this);
		eliminarListeners();
		this.actualizarTipoEntregaDetalles();
		
		/* Boton "Agregar Selección" */
		if(e.getSource().equals(jButton1)){
			int fila = jTableInvitado.getSelectedRow();
			if(fila > -1){
				String nombreItem = tablaConsultaInvitado.getValueAt(fila,1).toString();
				Cantidad cant = new Cantidad(Cantidad.PRODUCTOS, nombreItem);
				MensajesVentanas.centrarVentanaDialogo(cant);
				String cantidadStr = cant.getCantidad();
				if(cantidadStr != null){
					float cantidad = Float.parseFloat(cantidadStr);
					DetalleServicio detalle = (DetalleServicio)CR.meServ.getListaRegalos().getDetallesServicio().get(fila);
					String codprod = tablaConsultaInvitado.getValueAt(fila,0).toString();
					if(cantidad < 1)
						MensajesVentanas.aviso("Debe ingresar una cantidad mayor a 0");
					else if(cantidad > detalle.getCantRestantes())
						MensajesVentanas.aviso("No puede adquirir más productos \nde los restantes");
					else
						try {
							// Se verifica si ya hay productos similares agregados a Venta.
							// Si ya hay se revisa que la nueva cantidad no supere a cantRestante
							try{
								Vector<Integer> renglones = CR.meVenta.obtenerRenglones(codprod, false);
								int cantActual = 0;
								for(int i=0;i<renglones.size();i++) {
									Vector<DetalleTransaccion> dets = CR.meVenta.getVenta().getDetallesTransaccion();
									DetalleTransaccion det = (DetalleTransaccion)dets.get(Integer.parseInt(renglones.get(i).toString())); 
									cantActual += det.getCantidad();
								}
								if(cantActual+cantidad>detalle.getCantRestantes())
									MensajesVentanas.aviso("No puede adquirir más productos \nde los restantes");
								else {
									CR.meVenta.ingresarLineaProductoVentaLR(codprod,Sesion.capturaTeclado,0);
									if(cantidad > 1)
										CR.meVenta.cambiarCantidad(cantidad-1);
									estado = FACTURANDO;
								}
							}catch(ProductoExcepcion e1){
								// Esta excepcion significa que el producto no esta en el detalle de venta todavia
								if(cantidad<=detalle.getCantRestantes()){
									CR.meVenta.ingresarLineaProductoVentaLR(codprod,Sesion.capturaTeclado,0);
									if(cantidad > 1)
										CR.meVenta.cambiarCantidad(cantidad-1);
								}
								estado = FACTURANDO;
							}
							repintarPantalla();
						} catch (UsuarioExcepcion e1) {
							e1.printStackTrace();
						} catch (MaquinaDeEstadoExcepcion e1) {
							e1.printStackTrace();
						} catch (ConexionExcepcion e1) {
							e1.printStackTrace();
						} catch (ExcepcionCr e1) {
							e1.printStackTrace();
						}
				}
			} else
				MensajesVentanas.aviso("Debe seleccionar algun item de la Lista");
		}
		
		/* Boton "Eliminar Selección" */
		else if(e.getSource().equals(jButton2)){
			if(estado==FACTURANDO){
				int fila = jTableFactura.getSelectedRow();
				if(CR.meVenta.getVenta()!=null && fila > -1){
					String nombreItem = jTableFactura.getValueAt(fila,1).toString();
					Cantidad cant = new Cantidad(Cantidad.PRODUCTOS,nombreItem);
					MensajesVentanas.centrarVentanaDialogo(cant);
					String cantidadStr = cant.getCantidad();
					if(cantidadStr != null){
						int cantidad = Integer.parseInt(cantidadStr);
						try {
							CR.meVenta.anularProducto(fila, cantidad);
							if(CR.meVenta.getVenta().getDetallesTransaccion().size()==0)
								estado = INICIAL;
								//CR.meVenta.anularVentaActiva();
							repintarPantalla();
						} catch (UsuarioExcepcion e1) {
							e1.printStackTrace();
						} catch (MaquinaDeEstadoExcepcion e1) {
							e1.printStackTrace();
						} catch (ConexionExcepcion e1) {
							e1.printStackTrace();
						} catch (ExcepcionCr e1) {
							e1.printStackTrace();
						}
					}
				} else
					MensajesVentanas.aviso("Debe seleccionar algun item de la factura");
			}else if(estado==ABONANDO){
				int fila = jTableAbonos.getSelectedRow();
				if(fila > -1){
					CR.meServ.getListaRegalos().eliminarAbono(fila);
					if(CR.meServ.getListaRegalos().getDetalleAbonos().size()==0)
						estado=INICIAL;
				} else
					MensajesVentanas.aviso("Debe seleccionar el abono a eliminar");
				repintarPantalla();
			}else{
				//MensajesVentanas.aviso("Nada seleccionado para eliminar");
			}
		}

		/* Botón Impresiones */
		else if(e.getSource().equals(jButton3)){
			Impresiones impresiones = new Impresiones();
			MensajesVentanas.centrarVentanaDialogo(impresiones);
		}

		/* Botón Finalizar */
		else if(e.getSource().equals(jButton4)){
			if(estado == ABONANDO)
				try {
					double montovuelto = 0;
					if(CR.meServ.getListaRegalos().getDetalleAbonos() == null)
						MensajesVentanas.mensajeError("No existe transacción activa");
					else {
						DatosInvitado di = new DatosInvitado();
						MensajesVentanas.centrarVentanaDialogo(di);
						try {
							if(di.getNombre()!=null){
								ListaRegalos lista = CR.meServ.getListaRegalos();
								
								Vector<Object> pagos = ManejoPagosFactory.getInstance().realizarPago(lista.getMontoAbonos(),lista.getPagosAbono(), lista.getCliente(), lista.getNumServicio());
								if (((Boolean)pagos.elementAt(2)).booleanValue()) {
									montovuelto = ((Double)pagos.elementAt(1)).doubleValue();
									for (int i=0; i<((Vector<Pago>)pagos.elementAt(0)).size(); i++) {
										Pago pagoAct = ((Vector<Pago>)pagos.elementAt(0)).elementAt(i);
										lista.realizarPagoAbono(pagoAct);
									}
									
									try {
										CR.meServ.registrarAbonosLR(montovuelto,di.getNombre(),di.getDedicatoria());
										dispose();
										CR.me.repintarMenuPrincipal();										
									} catch (ConexionExcepcion e2) {
										e2.printStackTrace();
										MensajesVentanas.mensajeError(e2.getMensaje());
									}
								}
							}
						} catch (PagoExcepcion e1) {
							e1.printStackTrace();
							MensajesVentanas.mensajeError("Error procesando el pago");
						} catch (MontoPagoExcepcion e1) {
							// Chequeamos si existe un pago que permita vuelto para devolver la diferencia
							double monto = 0;
							for (int i=0; i<CR.meServ.getListaRegalos().getPagosAbono().size(); i++) {
								Pago pago = (Pago) CR.meServ.getListaRegalos().getPagosAbono().elementAt(i);
								if (pago.getFormaPago().isPermiteVuelto())
									monto += pago.getMonto();
							}
							if (monto >= (CR.meServ.getListaRegalos().getMontoPagado()-CR.meServ.getListaRegalos().getMontoAbonos()))
								try {
									//double vuelto = monto - (CR.meServ.getListaRegalos().getMontoPagado()-CR.meServ.getListaRegalos().getMontoAbonos());
									CR.meServ.registrarAbonosLR(montovuelto,di.getNombre(),di.getDedicatoria());	
									this.comprobarLimiteEntrega();
									dispose();
									CR.me.repintarMenuPrincipal();
								} catch (ConexionExcepcion e2) {
									e2.printStackTrace();
									MensajesVentanas.mensajeError(e2.getMensaje());
									repintarPantalla();
								}
							else
								throw new ExcepcionCr("Debe eliminar los pagos. No permiten vuelto al Cliente");
						}
					}
				} catch (ExcepcionCr e1) {
					MensajesVentanas.mensajeError(e1.getMensaje());
					repintarPantalla();
				}
			else if(estado == FACTURANDO)
				try {				
					if(CR.meVenta.getVenta() == null)
						MensajesVentanas.mensajeError("No existe Venta activa");
					else {
						CR.meVenta.consultarMontoTrans(true);
						//Agregado promociones de regalos a la hora de comprar un producto. jperez
						try{
							CR.meServ.ejecucionPromociones();
						}catch(Exception ex){
							ex.printStackTrace();
						}
						// Verificamos que en caso de entregas a domicilio exista un cliente activo	
						if(CR.meVenta.getVenta().verificarTieneEntregaDom() && CR.meVenta.getVenta().getCliente().getCodCliente() == null)
							MensajesVentanas.aviso("Debe asignar un cliente para poder hacer la entrega a domicilio");
						else if(Sesion.isRequiereCliente() && CR.meVenta.getVenta().getCliente().getCodCliente() == null)
							MensajesVentanas.aviso("Debe asignar un cliente a la transacción");
						else {
							DatosInvitado di = new DatosInvitado();
							MensajesVentanas.centrarVentanaDialogo(di);
							try {
								if (CR.meServ.getListaRegalos().getCliente()!=null)
									CR.meServ.getListaRegalos().getCliente().setContribuyente(true);
								CR.meVenta.asignarCliente(CR.meServ.getListaRegalos().getCliente());
								if(di.getNombre()!=null){
									double montoTrans = CR.meVenta.consultarMontoTrans();
									Vector<Object> pagos = ManejoPagosFactory.getInstance().realizarPago(montoTrans,CR.meVenta.getVenta().getPagos(),CR.meVenta.getVenta().getCliente(),0);
									if (((Boolean)pagos.elementAt(2)).booleanValue()) {
										for (int i=0; i<((Vector<Pago>)pagos.elementAt(0)).size(); i++) {
											Pago pagoAct = ((Vector<Pago>)pagos.elementAt(0)).elementAt(i);
											CR.meVenta.efectuarPago(pagoAct);
										}
										
										//Se finaliza la venta
										CR.meVenta.finalizarVentaListaRegalos(di.getNombre(),di.getDedicatoria());
										//this.comprobarLimiteEntrega();
										dispose();
										CR.me.repintarMenuPrincipal();
									}
								}
							} catch (MontoPagoExcepcion e1) {
								logger.error("mouseClicked(MouseEvent)", e1);

								// Chequeamos si existe un pago que permita vuelto para devolver la diferencia
								double monto = 0;
								for (int i=0; i<CR.meVenta.getVenta().getPagos().size(); i++) {
									Pago pago = (Pago) CR.meVenta.getVenta().getPagos().elementAt(i);
									if (pago.getFormaPago().isPermiteVuelto())
										monto += pago.getMonto();
								}
								if (monto >= (CR.meVenta.getVenta().obtenerMontoPagado()-CR.meVenta.consultarMontoTrans())) {
									//Se finaliza la venta
									CR.meVenta.finalizarVentaListaRegalos(di.getNombre(),di.getDedicatoria());
									//this.comprobarLimiteEntrega();
									dispose();
									CR.me.repintarMenuPrincipal();
								} else
									throw new ExcepcionCr("Debe eliminar los pagos. No permiten vuelto al Cliente");
							}
						}
					}
				} catch(ExcepcionCr f) {
					logger.error("mouseClicked(MouseEvent)", f);

					MensajesVentanas.mensajeError(f.getMensaje());
					repintarPantalla();
				} catch (ConexionExcepcion f1) {
					logger.error("mouseClicked(MouseEvent)", f1);

					MensajesVentanas.mensajeError(f1.getMensaje());
					repintarPantalla();
				} catch (PrinterNotConnectedException f1) {
					logger.error("mouseClicked(MouseEvent)", f1);

					MensajesVentanas.mensajeError(f1.getMessage());
					repintarPantalla();
				}
			else if(estado == INICIAL)
				MensajesVentanas.aviso("No hay abonos/productos seleccionados");
		}

		/* Botón Consultas */
		else if(e.getSource().equals(jButton5)){
			AsignarCliente consultas = new AsignarCliente();
			MensajesVentanas.centrarVentanaDialogo(consultas);
			String casoConsulta = consultas.getCodigoSeleccionado()[0];
			String codigo = consultas.getCodigoSeleccionado()[1];
			if (casoConsulta != null)
				try {
					CR.meServ.asignarClienteLR(codigo);
					this.repintarPantalla();
				} catch (ConexionExcepcion e1) {
					e1.printStackTrace();
					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (ExcepcionCr e1) {
					e1.printStackTrace();
					MensajesVentanas.mensajeError(e1.getMensaje());
				}
		}
		
		/* Botón Cliente en espera */
		else if(e.getSource().equals(jButton6)){
			if(CR.meServ.getListaRegalos().getCliente() == null || CR.meServ.getListaRegalos().getCliente().getCodCliente()==null){
				Identificacion id = new Identificacion();
				MensajesVentanas.centrarVentanaDialogo(id);
				String identificacion = id.getCantidad();
				if(identificacion != null)
					if(!(identificacion.trim()).equals(""))
						try {
							int numTienda = Sesion.getTienda().getNumero();
							int numCaja = Sesion.getCaja().getNumero();
							char tipoTransaccion = ListaRegalos.CONSULTA_INVITADO;
							CR.meServ.colocarListaEnEspera(numTienda,numCaja,identificacion,tipoTransaccion);
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
					else
						MensajesVentanas.aviso("Para colocar la lista en espera\ndebe ingresar un identificador");
			} else if(MensajesVentanas.preguntarSiNo("¿Está seguro que desea colocar en espera?")==0)
				try {
					String identificacion = CR.meServ.getListaRegalos().getTitular().getCodCliente();
					CR.meServ.colocarListaEnEspera(identificacion,Sesion.getTienda().getNumero(),Sesion.getCaja().getNumero(),ListaRegalos.CONSULTA_INVITADO);
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
		
		/* Botón Abonar a Producto */
		else if(e.getSource().equals(jButton7)){
			int fila = jTableInvitado.getSelectedRow();
			if(fila > -1){
				// Detalle del regalo
				DetalleServicio detalle = (DetalleServicio)CR.meServ.getListaRegalos().getDetallesServicio().get(fila);
				String nombreItem = detalle.getProducto().getDescripcionCorta();
				double precioItem = (detalle.getPrecioFinal()*((detalle.getProducto().getImpuesto().getPorcentaje()/100)+1));
				double abonado = detalle.getAbonos();
				double resta = precioItem - abonado;

				Cantidad cant;
				// Ventana de diálogo de cantidad
				if(resta>0)
					cant = new Cantidad(Cantidad.ABONO_PRODUCTO, nombreItem, resta);
				else
					cant = new Cantidad(Cantidad.ABONO_PRODUCTO, nombreItem, precioItem);

				MensajesVentanas.centrarVentanaDialogo(cant);

				// Si ingresaron una cantidad en la ventana se continua
				if(cant.getCantidad() != null){
					char tipoAbono = cant.getTipoAbono();
					try {
						if(tipoAbono==OperacionLR.ABONO_PARCIAL){
							double montoAbono = Double.parseDouble(CR.me.formatoNumerico(cant.getCantidad()));

							if(Math.round(montoAbono-precioItem)==0){
								CR.meServ.abonarListaRegalos(1, fila);
								estado = ABONANDO;
							}else if(montoAbono > 0){
								CR.meServ.abonarListaRegalos(montoAbono, fila);
								estado = ABONANDO;
							} else
								MensajesVentanas.aviso("No se pudo agregar este abono");
						} else if(tipoAbono==OperacionLR.ABONO_TOTAL){
							int cantidad = Integer.parseInt(cant.getCantidad());
							if(cantidad > 0){
								CR.meServ.abonarListaRegalos(cantidad, fila);
								estado = ABONANDO;
							} else
								MensajesVentanas.aviso("No se pudo agregar el abono");
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
						MensajesVentanas.aviso(e1.getMensaje());
					}
					repintarPantalla();
				}
			} else
				MensajesVentanas.aviso("Debe seleccionar algun item de la Lista");
		}
		
		/* Botón Abonar a Lista*/
		else if(e.getSource().equals(jButton8)){
			double montoAbono;
			Cantidad cant = new Cantidad(Cantidad.ABONO_LISTA, "Lista de Regalos");
			MensajesVentanas.centrarVentanaDialogo(cant);
			if(cant.getCantidad() != null){
				montoAbono = Double.parseDouble(CR.me.formatoNumerico(cant.getCantidad()));
				if(montoAbono > 0){
					try{
						CR.meServ.abonarListaRegalos(montoAbono);
						estado = ABONANDO;
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
					repintarPantalla();
				}
			}
		}
		
		/* Boton Cancelar */
		else if(e.getSource().equals(jButton9)){
			if(MensajesVentanas.preguntarSiNo("¿Confirma que desea salir sin facturar?")==0){			
				try {
					finalize();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
				try {
					if(CR.meVenta.getVenta() != null)
						CR.meVenta.anularVentaActiva();
					CR.meServ.finalizarListaRegalos();
					dispose();
					CR.me.repintarMenuPrincipal();
				} catch (UsuarioExcepcion e1) {
					e1.printStackTrace();
					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (MaquinaDeEstadoExcepcion e1) {
					e1.printStackTrace();
					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (PagoExcepcion e1) {
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
//			if(MensajesVentanas.preguntarSiNo("¿Confirma que desea cancelar el registro?")==0){
//				try {
//					if(CR.meVenta.getVenta() != null)
//						CR.meVenta.anularVentaActiva();
//					CR.meServ.eliminarAbonosLR();
//					estado = INICIAL;
//					getJScrollPane1().setViewportView(getJTableFactura());
//				} catch (UsuarioExcepcion e1) {
//					e1.printStackTrace();
//					MensajesVentanas.mensajeError(e1.getMensaje());
//				} catch (MaquinaDeEstadoExcepcion e1) {
//					e1.printStackTrace();
//					MensajesVentanas.mensajeError(e1.getMensaje());
//				} catch (PagoExcepcion e1) {
//					e1.printStackTrace();
//					MensajesVentanas.mensajeError(e1.getMensaje());
//				} catch (ConexionExcepcion e1) {
//					e1.printStackTrace();
//					MensajesVentanas.mensajeError(e1.getMensaje());
//				} catch (ExcepcionCr e1) {
//					e1.printStackTrace();
//					MensajesVentanas.mensajeError(e1.getMensaje());
//				}
//				this.repintarPantalla();
//			}
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
			if(MensajesVentanas.preguntarSiNo("¿Confirma que desea salir sin facturar?")==0){			
				try {
					finalize();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
				try {
					if(CR.meVenta.getVenta() != null)
						CR.meVenta.anularVentaActiva();
					CR.meServ.finalizarListaRegalos();
					dispose();
					CR.me.repintarMenuPrincipal();
				} catch (UsuarioExcepcion e1) {
					e1.printStackTrace();
					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (MaquinaDeEstadoExcepcion e1) {
					e1.printStackTrace();
					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (PagoExcepcion e1) {
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
		}
				
		/* Modo Titular */
		else if(e.getSource().equals(jButton13))
			try {
				CR.meServ.titularListaRegalos();
				dispose();
				ConsultaListaTitular clt = new ConsultaListaTitular(principal, estado);
				MensajesVentanas.centrarVentanaDialogo(clt);
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
		if(CR.meServ.getListaRegalos()!=null && CR.meServ.getListaRegalos().isPermitirVenta())
			CR.inputEscaner.getDocument().addDocumentListener(this);
		agregarListeners();
	}


	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel2() {
		if (logger.isDebugEnabled())
			logger.debug("getJPanel2() - start");

		if(jPanel2 == null) {
			jPanel2 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout16 = new java.awt.FlowLayout();
			layFlowLayout16.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout16.setHgap(0);
			layFlowLayout16.setVgap(0);
			jPanel2.setLayout(layFlowLayout16);
			jPanel2.add(getJScrollPane(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(656,132));
			jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle de Lista de Regalos No. "+CR.meServ.getListaRegalos().getNumServicio()+": ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), java.awt.Color.black));
			jPanel2.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel2() - end");
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
			jScrollPane.setViewportView(getJTableInvitado());
			jScrollPane.setPreferredSize(new java.awt.Dimension(645,110));
			jScrollPane.setBackground(new java.awt.Color(242,242,238));
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private javax.swing.JTable getJTableInvitado() {
		if(jTableInvitado == null) {
			jTableInvitado = new javax.swing.JTable(tablaConsultaInvitado);
			jTableInvitado.getTableHeader().setReorderingAllowed(false) ;
			jTableInvitado.getColumnModel().getColumn(0).setPreferredWidth(75);
			jTableInvitado.getColumnModel().getColumn(1).setPreferredWidth(180);
			jTableInvitado.getColumnModel().getColumn(2).setPreferredWidth(30);
			jTableInvitado.getColumnModel().getColumn(3).setPreferredWidth(30);
			jTableInvitado.getColumnModel().getColumn(4).setPreferredWidth(70);
			jTableInvitado.getColumnModel().getColumn(5).setPreferredWidth(70);
			jTableInvitado.getColumnModel().getColumn(6).setPreferredWidth(70);
			jTableInvitado.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jTableInvitado.setBackground(new java.awt.Color(242,242,238));
			jTableInvitado.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e) {				
					getJTableAbonos().getSelectionModel().clearSelection();
					getJTableFactura().getSelectionModel().clearSelection();
				}
			});
		}
		return jTableInvitado;
	}
	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private javax.swing.JTable getJTableAbonos() {
		if(jTableAbonos == null) {
			jTableAbonos = new javax.swing.JTable(tablaDetalleAbonos);
			jTableAbonos.getTableHeader().setReorderingAllowed(false) ;
			jTableAbonos.getColumnModel().getColumn(0).setPreferredWidth(80);
			jTableAbonos.getColumnModel().getColumn(1).setPreferredWidth(150);
			jTableAbonos.getColumnModel().getColumn(2).setPreferredWidth(20);
			jTableAbonos.getColumnModel().getColumn(3).setPreferredWidth(70);
			jTableAbonos.getColumnModel().getColumn(4).setPreferredWidth(70);
			jTableAbonos.getColumnModel().getColumn(5).setPreferredWidth(70);
			jTableAbonos.getColumnModel().getColumn(6).setPreferredWidth(70);
			jTableAbonos.getColumnModel().getColumn(7).setPreferredWidth(5);
			jTableAbonos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jTableAbonos.setBackground(new java.awt.Color(242,242,238));
			jTableAbonos.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e) {				
					getJTableInvitado().getSelectionModel().clearSelection();
				}
			});
		}
		return jTableAbonos;
	}
	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel2() {
		if(jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setText("");
			jLabel2.setPreferredSize(new java.awt.Dimension(80,24));
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
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
			layFlowLayout2.setHgap(0);
			layFlowLayout2.setVgap(0);
			jPanel5.setLayout(layFlowLayout2);
			jPanel5.add(getJScrollPane1(), null);
			jPanel5.setPreferredSize(new java.awt.Dimension(656,132));
			jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Productos seleccionados para facturar:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), java.awt.Color.black));
			jPanel5.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel5;
	}
	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private javax.swing.JScrollPane getJScrollPane1() {
		if(jScrollPane1 == null) {
			jScrollPane1 = new javax.swing.JScrollPane();
			if(estado == ABONANDO)
				jScrollPane1.setViewportView(getJTableAbonos());
			else
				jScrollPane1.setViewportView(getJTableFactura());
			jScrollPane1.setPreferredSize(new java.awt.Dimension(645,110));
			jScrollPane1.setBackground(new java.awt.Color(242,242,238));
		}
		return jScrollPane1;
	}
	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private javax.swing.JTable getJTableFactura() {
		if(jTableFactura == null) {
			jTableFactura = new javax.swing.JTable(tablaFacturaLR);
			jTableFactura.getColumnModel().getColumn(0).setPreferredWidth(88);
			jTableFactura.getColumnModel().getColumn(1).setPreferredWidth(150);
			jTableFactura.getColumnModel().getColumn(2).setPreferredWidth(65);
			jTableFactura.getColumnModel().getColumn(3).setPreferredWidth(75);
			jTableFactura.getColumnModel().getColumn(4).setPreferredWidth(75);
			jTableFactura.getColumnModel().getColumn(5).setPreferredWidth(76);
			jTableFactura.getColumnModel().getColumn(6).setPreferredWidth(35);
			jTableFactura.setRowHeight(jTableFactura.getRowHeight()+5);
			jTableFactura.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jTableFactura.setBackground(new java.awt.Color(242,242,238));
			jTableFactura.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e) {				
					getJTableInvitado().getSelectionModel().clearSelection();
				}
			});
		}
		return jTableFactura;
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
			jButton7.setText("F7 Abono a Producto");
			jButton7.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton7.setMargin(new Insets(1,2,1,1));
		}
		return jButton7;
	}
	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField1() {
		if(jTextField1 == null) {
			jTextField1 = new javax.swing.JTextField();
			jTextField1.setPreferredSize(new java.awt.Dimension(200,24));
			jTextField1.setEditable(false);
			jTextField1.setBackground(new java.awt.Color(242,242,238));
			jTextField1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			jTextField1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jTextField1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			jTextField1.setFocusable(false);
		}
		return jTextField1;
	}
	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField2() {
		if(jTextField2 == null) {
			jTextField2 = new javax.swing.JTextField();
			jTextField2.setPreferredSize(new java.awt.Dimension(200,24));
			jTextField2.setEditable(false);
			jTextField2.setBackground(new java.awt.Color(242,242,238));
			jTextField2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			jTextField2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			jTextField2.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jTextField2.setFocusable(false);
		}
		return jTextField2;
	}
	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField3() {
		if(jTextField3 == null) {
			jTextField3 = new javax.swing.JTextField();
			jTextField3.setPreferredSize(new java.awt.Dimension(200,24));
			jTextField3.setEditable(false);
			jTextField3.setBackground(new java.awt.Color(242,242,238));
			jTextField3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			jTextField3.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jTextField3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			jTextField3.setFocusable(false);
		}
		return jTextField3;
	}
	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField4() {
		if(jTextField4 == null) {
			jTextField4 = new javax.swing.JTextField();
			jTextField4.setPreferredSize(new java.awt.Dimension(220,24));
			jTextField4.setEditable(false);
			jTextField4.setBackground(new java.awt.Color(242,242,238));
			jTextField4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			jTextField4.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jTextField4.setFocusable(false);
		}
		return jTextField4;
	}
	/**
	 * This method initializes jTextField5
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField5() {
		if(jTextField5 == null) {
			jTextField5 = new javax.swing.JTextField();
			jTextField5.setPreferredSize(new java.awt.Dimension(220,24));
			jTextField5.setEditable(false);
			jTextField5.setBackground(new java.awt.Color(242,242,238));
			jTextField5.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			jTextField5.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jTextField5.setFocusable(false);
		}
		return jTextField5;
	}
	/**
	 * This method initializes jTextField6
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField6() {
		if(jTextField6 == null) {
			jTextField6 = new javax.swing.JTextField();
			jTextField6.setPreferredSize(new java.awt.Dimension(220,24));
			jTextField6.setEditable(false);
			jTextField6.setBackground(new java.awt.Color(242,242,238));
			jTextField6.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			jTextField6.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jTextField6.setFocusable(false);
		}
		return jTextField6;
	}
	/**
	 * This method initializes jTextField7
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField7() {
		if(jTextField7 == null) {
			jTextField7 = new javax.swing.JTextField();
			jTextField7.setPreferredSize(new java.awt.Dimension(220,24));
			jTextField7.setEditable(false);
			jTextField7.setBackground(new java.awt.Color(242,242,238));
			jTextField7.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			jTextField7.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jTextField7.setFocusable(false);
		}
		return jTextField7;
	}
	/**
	 * This method initializes jButton13
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton13() {
		if(jButton13 == null) {
			jButton13 = new javax.swing.JButton();
			jButton13.setPreferredSize(new java.awt.Dimension(120,40));
			jButton13.setBackground(new java.awt.Color(226,226,222));
			jButton13.setText("Modo Titular");
			jButton13.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton13.setMargin(new Insets(1,2,1,1));
			jButton13.setMnemonic('T');
		}
		return jButton13;
	}
	
	private void repintarPantalla(){
		String nombre, codigo;
		try{
			tablaConsultaInvitado.llenarTablaConsultaInvitado();
			if(estado == ABONANDO) {
				tablaDetalleAbonos.llenarTablaAbonos();
				if(tablaDetalleAbonos.getRowCount()>0){
					// Mueve el foco de la tabla a la fila del último producto ingresado. 
					getJTableAbonos().setRowSelectionInterval(tablaDetalleAbonos.getRowCount()-1, tablaDetalleAbonos.getRowCount()-1);
					getJTableAbonos().setColumnSelectionInterval(1, 1); 
					getJTableAbonos().scrollRectToVisible(getJTableAbonos().getCellRect(getJTableAbonos().getSelectedRow(), 1, true));
				}
			} else if (estado == FACTURANDO){
				TableColumnModel tcm = jTableFactura.getColumnModel();
				TableColumn tc = tcm.getColumn(7);
				tc.setCellRenderer(new EntregaRenderer());
				tc.setCellEditor(new EntregaEditor(this));
				tablaFacturaLR.llenarTabla();
				// Mueve el foco de la tabla a la fila del último producto ingresado. 
				getJTableFactura().setRowSelectionInterval(tablaFacturaLR.getRowCount()-1, tablaFacturaLR.getRowCount()-1);
				getJTableFactura().setColumnSelectionInterval(1, 1); 
				getJTableFactura().scrollRectToVisible(getJTableFactura().getCellRect(getJTableFactura().getSelectedRow(), 1, true));
			}else if(estado == INICIAL){
				TableColumnModel tcm = jTableFactura.getColumnModel();
				TableColumn tc = tcm.getColumn(7);
				tc.setCellRenderer(new EntregaRenderer());
				tc.setCellEditor(new EntregaEditor(this));
				tablaFacturaLR.llenarTabla();
			}

			if (CR.meServ.getListaRegalos().getCliente()!= null ){
				nombre = CR.meServ.getListaRegalos().getCliente().getNombreCompleto();
				codigo = CR.meServ.getListaRegalos().getCliente().getCodCliente();
				CR.me.setCliente(nombre, codigo);
			}

			if(estado == FACTURANDO) {
				getJTextField().setText(String.valueOf(CR.meVenta.getVenta().getDetallesTransaccion().size()));				
				getJTextField1().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meVenta.getVenta().getMontoBase())));
				getJTextField2().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meVenta.getVenta().getMontoImpuesto())));
				getJTextField3().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meVenta.getVenta().getMontoBase() + CR.meVenta.getVenta().getMontoImpuesto())));
				jButton7.setEnabled(false);
				jButton8.setEnabled(false);
				getJScrollPane1().setViewportView(getJTableFactura());
			} else if(estado == ABONANDO){
				getJTextField().setText(String.valueOf(CR.meServ.getListaRegalos().getCantAbonos()));
				getJTextField1().setText(Sesion.getTienda().getMonedaBase()+" "+String.valueOf(df.format(CR.meServ.getListaRegalos().getMontoAbonos())));
				getJTextField2().setText(Sesion.getTienda().getMonedaBase()+" "+String.valueOf(df.format(0)));
				getJTextField3().setText(Sesion.getTienda().getMonedaBase()+" "+String.valueOf(df.format(CR.meServ.getListaRegalos().getMontoAbonos())));
				jButton1.setEnabled(false);
				jButton2.setEnabled(true);
				jButton2.setText("F2 Eliminar abono");
				getJScrollPane1().setViewportView(getJTableAbonos());
			} else if(estado == INICIAL){
				getJTextField().setText("0");
				getJTextField1().setText(df.format(0));
				getJTextField2().setText(df.format(0));
				getJTextField3().setText(df.format(0));
				if(!CR.meServ.getListaRegalos().isPermitirVenta()){
					jButton2.setText("F2 Eliminar Producto");
					jButton1.setEnabled(false);
					jButton2.setEnabled(false);
				} else {
					jButton2.setText("F2 Eliminar Producto");
					jButton1.setEnabled(true);
					jButton2.setEnabled(true);
				}
				jButton7.setEnabled(true);
				jButton8.setEnabled(true);
				getJScrollPane1().setViewportView(getJTableFactura());
			}
		} catch (Exception ex){
			MensajesVentanas.mensajeError("Error refrescando la tabla");
			CR.me.setCliente("","");
			getJTextField().setText("0");
			getJTextField1().setText(df.format(0));
			getJTextField2().setText(df.format(0));
			getJTextField3().setText(df.format(0));
		}

		if (logger.isDebugEnabled())
			logger.debug("repintarFactura() - end");
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
		else if(e.getKeyCode() == KeyEvent.VK_F8)
			getJButton8().doClick();
		else if(e.getKeyCode() == KeyEvent.VK_F9)
			getJButton9().doClick();
		else if(e.getKeyCode() == KeyEvent.VK_F10)
			getJButton10().doClick();
		else if(e.getKeyCode() == KeyEvent.VK_F11)
			getJButton11().doClick();
		else if(e.getKeyCode() == KeyEvent.VK_F12)
			getJButton12().doClick();
		else if(e.getKeyCode() == KeyEvent.VK_T)
			getJButton13().doClick();
		else if (Character.isDigit(e.getKeyChar())) {
			eliminarListeners();
			if((estado == INICIAL || estado == FACTURANDO) && CR.meServ.getListaRegalos().isPermitirVenta()){
				Venta estadoAnterior = CR.meVenta.getVenta(); 
				// Mostramos una ventana para que el usuario introduzca el código del producto
				EjecutarConCantidad ec = new EjecutarConCantidad("Ingreso de Código:", String.valueOf(e.getKeyChar()),12);
				MensajesVentanas.centrarVentanaDialogo(ec);
				
				//Si se inicio una venta cambiamos el estado a FACTURANDO.
				if(estadoAnterior == null && CR.meVenta.getVenta()!= null)
					estado = FACTURANDO;

				this.repintarPantalla();
			}
			agregarListeners();
		} else if(e.getKeyCode() == KeyEvent.VK_A) {
			if (CR.meServ.getListaRegalos().getCliente()==null || CR.meServ.getListaRegalos().getCliente().getCodCliente()==null){
				EjecutarConCantidad	ec = new EjecutarConCantidad("Asignar un Cliente:", "",13);
				MensajesVentanas.centrarVentanaDialogo(ec);
				this.repintarPantalla();
			} else {
				int respuesta = MensajesVentanas.preguntarSiNo("¿Desea quitar el cliente asignado?");
				if (respuesta==0)
					CR.meServ.getListaRegalos().quitarCliente();
			}
			this.repintarPantalla();
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
					};
			});
		} catch (InterruptedException e) {
		} catch (InvocationTargetException e) {
		}
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	public void lecturaEscaner(String codigoScaner) {
		try {
			//valorLeido se parametriza 'Object' dado que contiene Integer y String
			ArrayList <Object>valorLeido = Control.codigoValido(codigoScaner);
			int tipoCodigo = ((Integer)valorLeido.get(0)).intValue();
			String codigo = (String)valorLeido.get(1);
			boolean pertenece = CR.meServ.getListaRegalos().perteneceProducto(codigo, Sesion.capturaEscaner);
			switch (tipoCodigo){
				case Control.PRODUCTO:
					if(pertenece){
						CR.meVenta.ingresarLineaProductoVentaLR(codigo, Sesion.capturaEscaner,0);
						estado = FACTURANDO;
						break;
					} else
						MensajesVentanas.mensajeError("El producto no pertenece a la Lista o\nIngresó una cantidad mayor a la cantidad restante");
				case Control.CLIENTE:
					CR.meVenta.asignarCliente(codigo);
					break;
				case Control.COLABORADOR:
					CR.meVenta.asignarCliente(codigo);
					break;
				case Control.CODIGO_DESCONOCIDO: 
					try{
						if(pertenece){
							CR.meVenta.ingresarLineaProductoVentaLR(codigoScaner.substring(0,Control.getLONGITUD_CODIGO()-1),Sesion.capturaEscaner,0);
							estado = FACTURANDO;
						} else
							MensajesVentanas.mensajeError("El producto no pertenece a la Lista o\nIngresó una cantidad mayor a la cantidad restante");
					}catch(Exception ex1){
						try {
							if(pertenece) {
								CR.meVenta.ingresarLineaProductoVentaLR(codigoScaner, Sesion.capturaEscaner,0);
								estado = FACTURANDO;
							} else
								MensajesVentanas.mensajeError("El producto no pertenece a la Lista o\nIngresó una cantidad mayor a la cantidad restante");
						} catch (Exception ex2) {
							if(pertenece) {
								CR.meVenta.ingresarLineaProductoVentaLR(codigoScaner.substring(0,codigoScaner.length()-1),Sesion.capturaEscaner,0);
								estado = FACTURANDO;
							} else
								MensajesVentanas.mensajeError("El producto no pertenece a la Lista o\nIngresó una cantidad mayor a la cantidad restante");
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
	
	private void agregarListeners(){
			
		jButton1.addActionListener(this);
		jButton1.addKeyListener(this);
			
		jButton2.addActionListener(this);
		jButton2.addKeyListener(this);

		jButton3.addActionListener(this);
		jButton3.addKeyListener(this);
						
		jButton4.addActionListener(this);
		jButton4.addKeyListener(this);
		
		jButton5.addKeyListener(this);
		jButton5.addActionListener(this);
	
		jButton6.addActionListener(this);
		jButton6.addKeyListener(this);
			
		jButton7.addActionListener(this);
		jButton7.addKeyListener(this);
		
		jButton8.addKeyListener(this);
		jButton8.addActionListener(this);
				
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
			
		getJTableFactura().addKeyListener(this);
		getJTableInvitado().addKeyListener(this);
		getJTableAbonos().addKeyListener(this);
	}
		
	private void eliminarListeners(){
			
		jButton1.removeActionListener(this);
		jButton1.removeKeyListener(this);
			
		jButton2.removeActionListener(this);
		jButton2.removeKeyListener(this);
			
		jButton7.removeActionListener(this);
		jButton7.removeKeyListener(this);
		
		jButton8.removeActionListener(this);
		jButton8.removeKeyListener(this);
		
		jButton5.removeActionListener(this);
		jButton5.removeKeyListener(this);
			
		jButton4.removeActionListener(this);
		jButton4.removeKeyListener(this);
	
		jButton3.removeActionListener(this);
		jButton3.removeKeyListener(this);
		
		jButton6.removeActionListener(this);
		jButton6.removeKeyListener(this);
			
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
			
		getJTableFactura().removeKeyListener(this);
		getJTableInvitado().removeKeyListener(this);
		getJTableAbonos().removeKeyListener(this);
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
	 * Método repintarFactura.
	 */
	private void actualizarTipoEntregaDetalles(){
		if (logger.isDebugEnabled())
			logger.debug("actualizarTipoEntregaDetalles() - start");

		String tipoEntrega = "";

		//Aquí llenamos un vector con los indices seleccionados de los comboBox 
		//que indican el tipo de entrega de los detalles
		try {
			if (CR.meVenta.getVenta() != null)
				for(int i=0; i<CR.meVenta.getVenta().getDetallesTransaccion().size(); i++) {
					tipoEntrega = (String)((Object[][])tablaFacturaLR.getDatos())[i][7];
					CR.meVenta.getVenta().actualizarTipoEntrega(i,tipoEntrega);
				}
		} catch(Exception e) {
			logger.error("actualizarTipoEntregaDetalles()", e);
		}

		if (logger.isDebugEnabled())
			logger.debug("actualizarTipoEntregaDetalles() - end");
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
			jButton3.setText("F3 Impresiones");
			jButton3.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton3.setMargin(new Insets(1,2,1,1));
		}
		return jButton3;
	}
	
	/**
	 * Verfica si el monto recaudado en la caja sobrapasa el límete establecido en la tienda para hacer una entrega de caja.
	 * De ser así muestra en la interfaz los avisos necesarios para el cajero
	 * 
	 * @return void
	 */
	private void comprobarLimiteEntrega() {
		if(Sesion.getCaja().getMontoRecaudado() > Sesion.getTienda().getLimiteEntregaCaja()) {
			CR.me.mostrarAviso("Debe realizar entrega a caja principal", true);
			// Enviar un bip a la caja
			CR.beep();
			// Mostrar mensaje de entrega parcial
			MensajesVentanas.aviso("Debe realizar entrega a caja principal");
			CR.me.setEntregaParcial(true);
		} else
			CR.me.setEntregaParcial(false);
	}

}  //  @jve:visual-info  decl-index=0 visual-constraint="11,31"