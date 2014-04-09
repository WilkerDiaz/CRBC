/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui.listaregalos
 * Programa   : CierreListaRegalos.java
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

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JTable;
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
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.PagoExcepcion;
import com.becoblohm.cr.excepciones.ProductoExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.gui.AnularProducto;
import com.becoblohm.cr.gui.Consultas;
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
import com.becoblohm.cr.manejarservicio.DetalleServicio;
import com.becoblohm.cr.manejarservicio.ListaRegalos;
import com.becoblohm.cr.manejarventa.DetalleTransaccion;
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
public class CierreListaRegalos1 extends JDialog implements ActionListener, KeyListener, DocumentListener, ScanerSwitch {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CierreListaRegalos1.class);
			
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
	private javax.swing.JButton jButton9 = null;
	private javax.swing.JLabel jLabel4 = null;
	private javax.swing.JLabel jLabel5 = null;
	private javax.swing.JLabel jLabel6 = null;
	private javax.swing.JLabel jLabel12 = null;
	private javax.swing.JLabel jLabel13 = null;
	private javax.swing.JLabel jLabel14 = null;
	private javax.swing.JTextField restaTF = null;
	private javax.swing.JLabel jLabel15 = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JScrollPane jScrollPane = null;
	private javax.swing.JScrollPane jScrollPane1 = null;
	private javax.swing.JTable jTableCierre = null;
	private javax.swing.JTable jTableFactura = null;
	private DecimalFormat df = new DecimalFormat("#,##0.00");
	private boolean permitirEscaner = true;
	private EfectuarLecturaEscaner lector = null;
	
	private ModeloTablaLR tablaCierre = new ModeloTablaLR(6);
	private ModeloTabla tablaFacturaLR = new ModeloTabla();
	private static int INICIAL = 0;
	private static int FACTURANDO = 2;
	private int estado;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JPanel jPanel5 = null;
	private FacturacionPrincipal principal = null;
	private char estadoLista;
	
	private javax.swing.JTextField subTotalTF = null;
	private javax.swing.JTextField ivaTF = null;
	private javax.swing.JTextField totalTF = null;
	private javax.swing.JTextField pedidosTF = null;
	private javax.swing.JTextField montoPedidosTF = null;
	private javax.swing.JTextField adquiridosTF = null;
	private javax.swing.JTextField montoAdquiridosTF = null;
	private javax.swing.JButton jButton5 = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JTextField abonosAListaTF = null;
	private javax.swing.JButton jButton3 = null;
	private javax.swing.JButton jButton7 = null;
	private javax.swing.JButton jButton8 = null;
	private javax.swing.JTextField montoRestantesTF = null;
	
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JTextField restantesTF = null;
	private javax.swing.JLabel jLabel3 = null;
	private javax.swing.JTextField abonosAProductosTF = null;
	private javax.swing.JLabel jLabel8 = null;
	/**
	 * This is the default constructor
	 */
	public CierreListaRegalos1(FacturacionPrincipal principal, char estadoLista) throws ConexionExcepcion, ExcepcionCr {
		super(MensajesVentanas.ventanaActiva);
		//Se guarda la referencia a la ventana principal para poder volver a ella
		//directamente cuando se coloca en espera la lista
		CR.meServ.cerrarListaRegalos();
		if(CR.meServ.getListaRegalos().getVentaParcialDeCierre1()!=null){
			CR.meVenta.setVenta(CR.meServ.getListaRegalos().getVentaParcialDeCierre1());
			estado = FACTURANDO;
		}else{
			CR.meVenta.crearVenta();
			estado = INICIAL;
		}
		this.principal = principal;
		this.estadoLista = estadoLista;
		initialize();
		
/*		try {
			CR.meServ.recuperarEstadoLista(ListaRegalos.CIERRE_LISTA);
		} catch (MaquinaDeEstadoExcepcion e1) {
			e1.printStackTrace();
		} catch (ConexionExcepcion e1) {
			e1.printStackTrace();
		} catch (ExcepcionCr e1) {
			e1.printStackTrace();
		}
*/
		this.repintarPantalla();
		
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
			layFlowLayout4.setAlignment(java.awt.FlowLayout.CENTER);
			jPanel3.setLayout(layFlowLayout4);
			jPanel3.add(getJLabel4(), null);
			jPanel3.add(getPedidosTF(), null);
			jPanel3.add(getJLabel2(), null);
			jPanel3.add(getMontoPedidosTF(), null);
			jPanel3.add(getJLabel5(), null);
			jPanel3.add(getAdquiridosTF(), null);
			jPanel3.add(getJLabel6(), null);
			jPanel3.add(getMontoAdquiridosTF(), null);
			jPanel3.add(getJLabel1(), null);
			jPanel3.add(getRestantesTF(), null);
			jPanel3.add(getJLabel3(), null);
			jPanel3.add(getMontoRestantesTF(), null);
			jPanel3.add(getJLabel8(), null);
			jPanel3.add(getAbonosAProductosTF(), null);
			jPanel3.add(getJLabel(), null);
			jPanel3.add(getAbonosAListaTF(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(328,130));
			jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Resumen Precierre de la Lista: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), java.awt.Color.black));
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
			layFlowLayout5.setVgap(2);
			layFlowLayout5.setAlignment(java.awt.FlowLayout.CENTER);
			jPanel4.setLayout(layFlowLayout5);
			jPanel4.add(getJLabel13(), null);
			jPanel4.add(getSubTotalTF(), null);
			jPanel4.add(getJLabel14(), null);
			jPanel4.add(getIvaTF(), null);
			jPanel4.add(getJLabel15(), null);
			jPanel4.add(getTotalTF(), null);
			jPanel4.add(getJLabel12(), null);
			jPanel4.add(getRestaTF(), null);
			jPanel4.setPreferredSize(new java.awt.Dimension(328,130));
			jPanel4.setBackground(new java.awt.Color(242,242,238));
			jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Resumen Selección de Cierre de la Lista: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), java.awt.Color.black));
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
			jButton4.setText("F4 Continuar");
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
			jButton5.setEnabled(false);
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
			jLabel4.setText("Pedidos:");
			jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
			jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jLabel4.setPreferredSize(new java.awt.Dimension(70,20));
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
			jLabel5.setText("Vendidos:");
			jLabel5.setPreferredSize(new java.awt.Dimension(70,20));
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
			jLabel6.setText("Monto Vendidos:");
			jLabel6.setPreferredSize(new java.awt.Dimension(105,20));
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
			jLabel12.setText("Monto Abonos:");
			jLabel12.setPreferredSize(new java.awt.Dimension(120,24));
			jLabel12.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
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
	 * This method initializes restaTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getRestaTF() {
		if(restaTF == null) {
			restaTF = new javax.swing.JTextField();
			restaTF.setPreferredSize(new java.awt.Dimension(180,24));
			restaTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			restaTF.setEditable(false);
			restaTF.setBackground(new java.awt.Color(242,242,238));
			restaTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			restaTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			restaTF.setFocusable(false);
		}
		return restaTF;
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
			jButton1.setEnabled(true);
		}
		return jButton1;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void actionPerformed(ActionEvent e) {
		CR.inputEscaner.getDocument().removeDocumentListener(this);
		eliminarListeners();
		this.actualizarTipoEntregaDetalles();

		/* Boton "Agregar Selección" */
		if(e.getSource().equals(jButton1)){
			int fila = -1;
			JTable tabla = null;
			if(jTableCierre.getSelectedRow() > -1){
				fila = jTableCierre.getSelectedRow();
				tabla = jTableCierre;
			}
			
			/*Si el producto se selecciona de la lista a facturar se eliminó
			 * jperez 12-04-2012
			 else if(jTableFactura.getSelectedRow() > -1){
				fila = jTableFactura.getSelectedRow();
				tabla = jTableFactura;
			}*/
			
			if(fila > -1){
				String nombreItem = tabla.getValueAt(fila,1).toString();
				Cantidad cant = new Cantidad(Cantidad.PRODUCTOS, nombreItem);
				MensajesVentanas.centrarVentanaDialogo(cant);
				if(cant.getCantidad() != null){
					float cantidad = Float.parseFloat(cant.getCantidad());
					DetalleServicio detalle = (DetalleServicio)CR.meServ.getListaRegalos().getDetalleAbonadosTotales().get(fila);
					String codprod = detalle.getProducto().getCodProducto();
					float cantAbonados = detalle.getCantAbonadosT();
					
					if(cantidad < 1)
						MensajesVentanas.aviso("Debe ingresar una cantidad mayor a 0");
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
								if(cantActual+cantidad>cantAbonados)
									MensajesVentanas.aviso("No puede agregar más productos \nde los abonados");
								else {
									if(cantidad > 1){
										CR.meVenta.ingresarLineaProductoVentaLR(codprod,Sesion.capturaTeclado,1);
										CR.meVenta.cambiarCantidad(cantidad-1);
										//CR.meServ.getListaRegalos().cambiarCantAbonados(-(cantidad+1),fila);
									} else
										CR.meVenta.ingresarLineaProductoVentaLR(codprod,Sesion.capturaTeclado,1);
										//CR.meServ.getListaRegalos().cambiarCantAbonados(-1,fila);
									estado = FACTURANDO;
								}
							}catch(ProductoExcepcion e1){
								// Esta excepcion significa que el producto no esta en el detalle de venta todavia
								if(cantidad>cantAbonados)
									MensajesVentanas.aviso("No puede agregar más productos \nde los abonados");
								else {
									if(cantidad > 1){
										CR.meVenta.ingresarLineaProductoVentaLR(codprod,Sesion.capturaTeclado,1);
										CR.meVenta.cambiarCantidad(cantidad-1);
										//CR.meServ.getListaRegalos().cambiarCantAbonados(-(cantidad+1),fila);
									} else
										CR.meVenta.ingresarLineaProductoVentaLR(codprod,Sesion.capturaTeclado,1);
										//CR.meServ.getListaRegalos().cambiarCantAbonados(-1,fila);
									estado = FACTURANDO;
								}
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
			}//Si el usuario selecciona productos de la lista incorrecta muestra un mensaje de error
			//jperez 12-04-2012
			else
				MensajesVentanas.aviso("Debe seleccionar algún producto de la lista \nde abonados");
		}
		
		/* Boton "Eliminar Selección" */
		else if(e.getSource().equals(jButton2)){
			int fila = jTableFactura.getSelectedRow();
			if(fila > -1){
				if(CR.meVenta.getVenta().getDetallesTransaccion().size() > 0){
					if(fila!=-1 && (!jTableFactura.getValueAt(fila,0).equals("000000000000"))){
						AnularProducto anularProducto = new AnularProducto(jTableFactura.getSelectedRow());
						MensajesVentanas.centrarVentanaDialogo(anularProducto);
						this.repintarPantalla();
					} else if (fila == -1){
						AnularProducto anularProducto = new AnularProducto(CR.meVenta.getVenta().getDetallesTransaccion().size()-1);
						MensajesVentanas.centrarVentanaDialogo(anularProducto);
						this.repintarPantalla();
					}
				} else
					MensajesVentanas.aviso("No Existen productos en la Lista de Regalos");
			} else
				MensajesVentanas.aviso("Debe seleccionar algún producto de la factura");
		}
		
		/* Botón Venta de Abonados */
		else if(e.getSource().equals(jButton3)){
			Vector<DetalleServicio> abonados = CR.meServ.getListaRegalos().getDetalleAbonadosTotales();
			if(abonados.size()==0)
				MensajesVentanas.aviso("No hay productos para agregar a factura");
			else
				for(int i=0;i<abonados.size();i++){
					DetalleServicio detalle = (DetalleServicio)abonados.get(i);
					try {
						int cantActual = 0;
						Vector<Integer> renglones = CR.meVenta.obtenerRenglones(detalle.getProducto().getCodProducto(), false);
						for(int j=0;j<renglones.size();j++) {
							Vector<DetalleTransaccion> dets = CR.meVenta.getVenta().getDetallesTransaccion();
							DetalleTransaccion det = (DetalleTransaccion)dets.get(Integer.parseInt(renglones.get(j).toString())); 
							cantActual += det.getCantidad();
						}
						if(detalle.getCantAbonadosT()<cantActual){
							CR.meVenta.ingresarLineaProductoVentaLR(detalle.getProducto().getCodProducto(),detalle.getTipoCaptura(),1);
							if(detalle.getCantAbonadosT()-cantActual>1)
								CR.meVenta.cambiarCantidad(detalle.getCantAbonadosT()-cantActual-1);
							estado = FACTURANDO;
						}
					} catch (ProductoExcepcion e1) {
						try {
							CR.meVenta.ingresarLineaProductoVentaLR(detalle.getProducto().getCodProducto(),detalle.getTipoCaptura(),1);
							if(detalle.getCantAbonadosT()>1)
								CR.meVenta.cambiarCantidad(detalle.getCantAbonadosT()-1);
							estado = FACTURANDO;
						} catch (UsuarioExcepcion e2) {
							e2.printStackTrace();
						} catch (MaquinaDeEstadoExcepcion e2) {
							e2.printStackTrace();
						} catch (ConexionExcepcion e2) {
							e2.printStackTrace();
						} catch (ExcepcionCr e2) {
							e2.printStackTrace();
						}
					} catch (UsuarioExcepcion e1) {
						e1.printStackTrace();
					} catch (MaquinaDeEstadoExcepcion e1) {
						e1.printStackTrace();
					} catch (ConexionExcepcion e1) {
						e1.printStackTrace();
					} catch (ExcepcionCr e1) {
						e1.printStackTrace();
					}finally{
						repintarPantalla();
					}
				}
		}

		/* Botón Continuar */
		else if(e.getSource().equals(jButton4))
			try {
				if(CR.meVenta.getVenta()==null)
					CR.meVenta.crearVenta();
				CR.meServ.getListaRegalos().guardarVentaParcialCierre(CR.meVenta.getVenta(),1);
				CierreListaRegalos2 clr2 = new CierreListaRegalos2(principal,estadoLista);
				dispose();
				MensajesVentanas.centrarVentanaDialogo(clr2);
			} catch (ConexionExcepcion e1) {
				e1.printStackTrace();
			} catch (ExcepcionCr e1) {
				e1.printStackTrace();
			}
		else if(e.getSource().equals(jButton5)){
			Consultas consultas = new Consultas();
			MensajesVentanas.centrarVentanaDialogo(consultas);
			String casoConsulta = consultas.getCodigoSeleccionado()[0];
			String codigo = consultas.getCodigoSeleccionado()[1];
			if (casoConsulta != null)
				try {
					CR.meVenta.ingresarLineaProductoVentaLR(codigo, Sesion.capturaTeclado,1);
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
			if(MensajesVentanas.preguntarSiNo("¿Está seguro que desea colocar en espera?")==0)
				try {
					String id = CR.meServ.getListaRegalos().getTitular().getCodCliente();
					CR.meServ.getListaRegalos().guardarVentaParcialCierre(CR.meVenta.getVenta(),1);
					CR.meServ.colocarListaEnEspera(id,Sesion.getTienda().getNumero(),Sesion.getCaja().getNumero(),ListaRegalos.CIERRE_LISTA);
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

		/* Botón Imprimir Precierre */
		else if(e.getSource().equals(jButton7))
			try {
				CR.meServ.getListaRegalos().imprimirPrecierre();
			} catch (ExcepcionCr e1) {
				e1.printStackTrace();
				MensajesVentanas.aviso(e1.getMensaje());
			}
		else if(e.getSource().equals(jButton9)){		
			if(MensajesVentanas.preguntarSiNo("¿Confirma que desea cancelar el cierre?")==0){				
				try {
					finalize();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
				try {
					if(CR.meVenta.getVenta() != null)
						CR.meVenta.anularVentaActiva();
					CR.meServ.cancelarCierreListaRegalos();
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
//					CR.meVenta.crearVenta();
//					//CR.meServ.getListaRegalos().recargarAbonadosTotalAlCierre();
//					//estado = INICIAL;
//					getRestaTF().setForeground(Color.BLACK);
//					getJScrollPane1().setViewportView(getJTableFactura());
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
		agregarListeners();
		CR.inputEscaner.getDocument().addDocumentListener(this);
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
			jPanel2.setPreferredSize(new java.awt.Dimension(656,136));
			int numLista = CR.meServ.getListaRegalos().getNumServicio();
			jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Productos con Abonos Totales de Lista de Regalos No. "+numLista+": ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
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
			jScrollPane.setViewportView(getJTableCierre());
			jScrollPane.setPreferredSize(new java.awt.Dimension(645,114));
			jScrollPane.setBackground(new java.awt.Color(242,242,238));
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private javax.swing.JTable getJTableCierre() {
		if(jTableCierre == null) {
			jTableCierre = new javax.swing.JTable(tablaCierre);
			jTableCierre.getTableHeader().setReorderingAllowed(false) ;
			jTableCierre.getColumnModel().getColumn(0).setPreferredWidth(65);
			jTableCierre.getColumnModel().getColumn(1).setPreferredWidth(175);
			jTableCierre.getColumnModel().getColumn(2).setPreferredWidth(60);
			jTableCierre.getColumnModel().getColumn(3).setPreferredWidth(30);
			jTableCierre.getColumnModel().getColumn(4).setPreferredWidth(30);
			jTableCierre.getColumnModel().getColumn(5).setPreferredWidth(30);
			//jTableTitular.getColumnModel().getColumn(6).setPreferredWidth(60);
			jTableCierre.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jTableCierre.setBackground(new java.awt.Color(242,242,238));
			jTableCierre.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						int fila = jTableCierre.getSelectedRow();
						String codProd = tablaCierre.getValueAt(fila,0).toString();
						String nombreProd = tablaCierre.getValueAt(fila,1).toString();
						DetalleRegalo dr = new DetalleRegalo(codProd,nombreProd);
						MensajesVentanas.centrarVentanaDialogo(dr);
					}
					getJTableFactura().getSelectionModel().clearSelection();
				}
			});
		}
		return jTableCierre;
	}

	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel2() {
		if(jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setText("Monto Pedidos:");
			jLabel2.setPreferredSize(new java.awt.Dimension(105,20));
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
			jPanel5.setPreferredSize(new java.awt.Dimension(656,136));
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
			jScrollPane1.setViewportView(getJTableFactura());
			jScrollPane1.setPreferredSize(new java.awt.Dimension(645,114));
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
			jTableFactura.getTableHeader().setReorderingAllowed(false) ;
			jTableFactura.getColumnModel().getColumn(0).setPreferredWidth(88);
			jTableFactura.getColumnModel().getColumn(1).setPreferredWidth(150);
			jTableFactura.getColumnModel().getColumn(2).setPreferredWidth(65);
			jTableFactura.getColumnModel().getColumn(3).setPreferredWidth(75);
			jTableFactura.getColumnModel().getColumn(4).setPreferredWidth(75);
			jTableFactura.getColumnModel().getColumn(5).setPreferredWidth(76);
			jTableFactura.getColumnModel().getColumn(6).setPreferredWidth(35);
			jTableFactura.setBackground(new java.awt.Color(242,242,238));
			jTableFactura.setRowHeight(jTableFactura.getRowHeight()+5);
			jTableFactura.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jTableFactura.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e) {				
					getJTableCierre().getSelectionModel().clearSelection();
				}
			});
		}
		return jTableFactura;
	}
	/**
	 * This method initializes subTotalTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getSubTotalTF() {
		if(subTotalTF == null) {
			subTotalTF = new javax.swing.JTextField();
			subTotalTF.setPreferredSize(new java.awt.Dimension(210,24));
			subTotalTF.setEditable(false);
			subTotalTF.setBackground(new java.awt.Color(242,242,238));
			subTotalTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			subTotalTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			subTotalTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
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
			ivaTF.setPreferredSize(new java.awt.Dimension(210,24));
			ivaTF.setEditable(false);
			ivaTF.setBackground(new java.awt.Color(242,242,238));
			ivaTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			ivaTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			ivaTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
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
			totalTF.setPreferredSize(new java.awt.Dimension(210,24));
			totalTF.setEditable(false);
			totalTF.setBackground(new java.awt.Color(242,242,238));
			totalTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			totalTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			totalTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			totalTF.setFocusable(false);
		}
		return totalTF;
	}
	/**
	 * This method initializes pedidosTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getPedidosTF() {
		if(pedidosTF == null) {
			pedidosTF = new javax.swing.JTextField();
			pedidosTF.setPreferredSize(new java.awt.Dimension(30,20));
			pedidosTF.setEditable(false);
			pedidosTF.setBackground(new java.awt.Color(242,242,238));
			pedidosTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			pedidosTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			pedidosTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
			pedidosTF.setFocusable(false);
		}
		return pedidosTF;
	}
	/**
	 * This method initializes montoPedidosTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getMontoPedidosTF() {
		if(montoPedidosTF == null) {
			montoPedidosTF = new javax.swing.JTextField();
			montoPedidosTF.setPreferredSize(new java.awt.Dimension(105,20));
			montoPedidosTF.setEditable(false);
			montoPedidosTF.setBackground(new java.awt.Color(242,242,238));
			montoPedidosTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			montoPedidosTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			montoPedidosTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			montoPedidosTF.setFocusable(false);
		}
		return montoPedidosTF;
	}
	/**
	 * This method initializes adquiridosTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getAdquiridosTF() {
		if(adquiridosTF == null) {
			adquiridosTF = new javax.swing.JTextField();
			adquiridosTF.setPreferredSize(new java.awt.Dimension(30,20));
			adquiridosTF.setEditable(false);
			adquiridosTF.setBackground(new java.awt.Color(242,242,238));
			adquiridosTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			adquiridosTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			adquiridosTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
			adquiridosTF.setFocusable(false);
		}
		return adquiridosTF;
	}
	/**
	 * This method initializes montoAdquiridosTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getMontoAdquiridosTF() {
		if(montoAdquiridosTF == null) {
			montoAdquiridosTF = new javax.swing.JTextField();
			montoAdquiridosTF.setPreferredSize(new java.awt.Dimension(105,20));
			montoAdquiridosTF.setEditable(false);
			montoAdquiridosTF.setBackground(new java.awt.Color(242,242,238));
			montoAdquiridosTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			montoAdquiridosTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			montoAdquiridosTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			montoAdquiridosTF.setFocusable(false);
		}
		return montoAdquiridosTF;
	}
	private void repintarPantalla(){
		String nombre, codigo;
		try{
			TableColumnModel tcm = jTableFactura.getColumnModel();
			TableColumn tc = tcm.getColumn(7);
			tc.setCellRenderer(new EntregaRenderer());
			tc.setCellEditor(new EntregaEditor(this));
			tablaCierre.llenarTablaCierre1();
			tablaFacturaLR.llenarTabla();
			
			// Mueve el foco de la tabla a la fila del último producto ingresado.
			if(tablaFacturaLR.getRowCount()>0){
				getJTableFactura().setRowSelectionInterval(tablaFacturaLR.getRowCount()-1, tablaFacturaLR.getRowCount()-1);
				getJTableFactura().setColumnSelectionInterval(1, 1); 
				getJTableFactura().scrollRectToVisible(getJTableFactura().getCellRect(getJTableFactura().getSelectedRow(), 1, true));
			}

			if (CR.meServ.getListaRegalos().getCliente()!= null ){
				nombre = CR.meServ.getListaRegalos().getCliente().getNombreCompleto();
				codigo = CR.meServ.getListaRegalos().getCliente().getCodCliente();
				CR.me.setCliente(nombre, codigo);
			}

			if(estado == FACTURANDO) {
				double abonos = CR.meServ.getListaRegalos().getMontoAbonosLista()+CR.meServ.getListaRegalos().getMontoAbonosProds();
				double montoBase= 0;
				double montoImpuesto = 0;
				try{
					montoBase = CR.meVenta.getVenta().getMontoBase()+CR.meServ.getListaRegalos().getVentaParcialDeCierre2().getMontoBase()+CR.meServ.getListaRegalos().getVentaParcialDeCierre3().getMontoBase();
					montoImpuesto = CR.meVenta.getVenta().getMontoImpuesto()+CR.meServ.getListaRegalos().getVentaParcialDeCierre2().getMontoImpuesto()+CR.meServ.getListaRegalos().getVentaParcialDeCierre3().getMontoImpuesto();
				}catch(Exception e){
					montoBase = CR.meVenta.getVenta().getMontoBase();
					montoImpuesto = CR.meVenta.getVenta().getMontoImpuesto();					
				}
				double total = montoBase + montoImpuesto;
				getPedidosTF().setText(String.valueOf(CR.meServ.getListaRegalos().getCantPedidos()));
				getMontoPedidosTF().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meServ.getListaRegalos().getMontoBase())));
				getAdquiridosTF().setText(String.valueOf(CR.meServ.getListaRegalos().getCantVendidos()));
				getMontoAdquiridosTF().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meServ.getListaRegalos().getMontoVendidos())));
				getAbonosAProductosTF().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meServ.getListaRegalos().getMontoAbonosProds())));
				getAbonosAListaTF().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meServ.getListaRegalos().getMontoAbonosLista())));
				getRestantesTF().setText(String.valueOf(CR.meServ.getListaRegalos().getCantRestantes()));
				getMontoRestantesTF().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meServ.getListaRegalos().getMontoRestantes())));
				getRestaTF().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(abonos-total)));

				if(total>abonos) getRestaTF().setForeground(Color.RED);
				else getRestaTF().setForeground(Color.BLACK);

				getSubTotalTF().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(montoBase)));
				getIvaTF().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(montoImpuesto)));
				getTotalTF().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(total)));
				getJScrollPane().setViewportView(getJTableCierre());
			} else if(estado == INICIAL){
				double abonos = CR.meServ.getListaRegalos().getMontoAbonosLista()+CR.meServ.getListaRegalos().getMontoAbonosProds();
				getPedidosTF().setText(String.valueOf(CR.meServ.getListaRegalos().getCantPedidos()));
				getMontoPedidosTF().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meServ.getListaRegalos().getMontoBase())));
				getAdquiridosTF().setText(String.valueOf(CR.meServ.getListaRegalos().getCantVendidos()));
				getMontoAdquiridosTF().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meServ.getListaRegalos().getMontoVendidos())));
				getAbonosAProductosTF().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meServ.getListaRegalos().getMontoAbonosProds())));
				getAbonosAListaTF().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meServ.getListaRegalos().getMontoAbonosLista())));
				getRestantesTF().setText(String.valueOf(CR.meServ.getListaRegalos().getCantRestantes()));
				getMontoRestantesTF().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meServ.getListaRegalos().getMontoRestantes())));
				getRestaTF().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(abonos)));

				getSubTotalTF().setText(df.format(0));
				getIvaTF().setText(df.format(0));
				getTotalTF().setText(df.format(0));
				getJButton1().setEnabled(true);
				getJButton2().setEnabled(true);
				getJScrollPane1().setViewportView(getJTableFactura());
			}
		} catch (Exception ex){
			ex.printStackTrace();
			MensajesVentanas.mensajeError("Error refrescando la pantalla");
			CR.me.setCliente("","");
			getRestaTF().setText(df.format(0));
			getSubTotalTF().setText(df.format(0));
			getIvaTF().setText(df.format(0));
			getTotalTF().setText(df.format(0));
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
		else if(e.getKeyCode() == KeyEvent.VK_F9)
			getJButton9().doClick();
		else if(e.getKeyCode() == KeyEvent.VK_F10)
			getJButton10().doClick();
		else if(e.getKeyCode() == KeyEvent.VK_F11)
			getJButton11().doClick();
		else if(e.getKeyCode() == KeyEvent.VK_F12)
			getJButton12().doClick();
		else if (Character.isDigit(e.getKeyChar())) {
			eliminarListeners();
			// Mostramos una ventana para que el usuario introduzca el código del producto
			EjecutarConCantidad ec = new EjecutarConCantidad("Ingreso de Código:", String.valueOf(e.getKeyChar()),3);
			MensajesVentanas.centrarVentanaDialogo(ec);
			this.repintarPantalla();
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
	

	/* (no Javadoc)
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 */
	public void changedUpdate(DocumentEvent e) {
	}

	/* (no Javadoc)
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 */
	public void insertUpdate(DocumentEvent e) {
		//jperez12-04-2012 Se elimino la captura de escaner desde la pantalla de cierre1 
		/*if (lector == null)
			lector = new EfectuarLecturaEscaner(this); 
		else
			lector.iniciar();*/
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
			ArrayList <Object>valorLeido = Control.codigoValido(codigoScaner);
			int tipoCodigo = ((Integer)valorLeido.get(0)).intValue();
			String codigo = (String)valorLeido.get(1);
			switch (tipoCodigo){
				case Control.PRODUCTO:
					CR.meVenta.ingresarLineaProductoVentaLR(codigo, Sesion.capturaEscaner,1);
					estado = FACTURANDO;
					break;
				case Control.CLIENTE:
					//CR.meServ.asignarCliente(codigo);
					break;
				case Control.COLABORADOR:
					//CR.meServ.asignarCliente(codigo);
					break;
				case Control.CODIGO_DESCONOCIDO: 
					try{
						CR.meServ.ingresarProductoLR(codigoScaner.substring(0,Control.getLONGITUD_CODIGO()-1),Sesion.capturaEscaner);
						estado = FACTURANDO;
					}catch(Exception ex1){
						try {
							CR.meVenta.ingresarLineaProductoVentaLR(codigoScaner, Sesion.capturaEscaner,1);
							estado = FACTURANDO;
						} catch (Exception ex2) {
							CR.meVenta.ingresarLineaProductoVentaLR(codigoScaner.substring(0,codigoScaner.length()-1),Sesion.capturaEscaner,1);
							estado = FACTURANDO;
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
			
		getJButton1().addActionListener(this);
		getJButton1().addKeyListener(this);
			
		getJButton2().addActionListener(this);
		getJButton2().addKeyListener(this);
			
		getJButton3().addActionListener(this);
		getJButton3().addKeyListener(this);

		getJButton4().addActionListener(this);
		getJButton4().addKeyListener(this);
			
		getJButton5().addKeyListener(this);
		getJButton5().addActionListener(this);
			
		getJButton6().addActionListener(this);
		getJButton6().addKeyListener(this);

		getJButton7().addActionListener(this);
		getJButton7().addKeyListener(this);
			
		getJButton9().addActionListener(this);
		getJButton9().addKeyListener(this);
			
		getJButton10().addActionListener(this);
		getJButton10().addKeyListener(this);
			
		getJButton11().addActionListener(this);
		getJButton11().addKeyListener(this);
			
		getJButton12().addActionListener(this);
		getJButton12().addKeyListener(this);
			
		getJTableFactura().addKeyListener(this);
		getJTableCierre().addKeyListener(this);
	}
		
	private void eliminarListeners(){
			
		getJButton1().removeActionListener(this);
		getJButton1().removeKeyListener(this);
			
		getJButton2().removeActionListener(this);
		getJButton2().removeKeyListener(this);
		
		getJButton3().removeActionListener(this);
		getJButton3().removeKeyListener(this);
		
		getJButton5().removeActionListener(this);
		getJButton5().removeKeyListener(this);
			
		getJButton4().removeActionListener(this);
		getJButton4().removeKeyListener(this);
	
		getJButton6().removeActionListener(this);
		getJButton6().removeKeyListener(this);
	
		getJButton7().removeActionListener(this);
		getJButton7().removeKeyListener(this);
			
		getJButton9().removeActionListener(this);
		getJButton9().removeKeyListener(this);
			
		getJButton10().removeActionListener(this);
		getJButton10().removeKeyListener(this);
			
		getJButton11().removeActionListener(this);
		getJButton11().removeKeyListener(this);
			
		getJButton12().removeActionListener(this);
		getJButton12().removeKeyListener(this);
		
		getJTableFactura().removeKeyListener(this);
		getJTableCierre().removeKeyListener(this);
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
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		if(jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setText("Abonos a Lista: ");
			jLabel.setPreferredSize(new java.awt.Dimension(150,20));
		}
		return jLabel;
	}
	/**
	 * This method initializes abonosAListaTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getAbonosAListaTF() {
		if(abonosAListaTF == null) {
			abonosAListaTF = new javax.swing.JTextField();
			abonosAListaTF.setPreferredSize(new java.awt.Dimension(100,20));
			abonosAListaTF.setEditable(false);
			abonosAListaTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			abonosAListaTF.setBackground(new java.awt.Color(242,242,238));
			abonosAListaTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			abonosAListaTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		}
		return abonosAListaTF;
	}
	
//	/**
//	 * Verfica si el monto recaudado en la caja sobrapasa el límete establecido en la tienda para hacer una entrega de caja.
//	 * De ser así muestra en la interfaz los avisos necesarios para el cajero
//	 * 
//	 * @return void
//	 */
//	private void comprobarLimiteEntrega() {
//		if(Sesion.getCaja().getMontoRecaudado() > Sesion.getTienda().getLimiteEntregaCaja()) {
//			CR.me.mostrarAviso("Debe realizar entrega a caja principal", true);
//			// Enviar un bip a la caja
//			CR.beep();
//			// Mostrar mensaje de entrega parcial
//			MensajesVentanas.aviso("Debe realizar entrega a caja principal");
//			CR.me.setEntregaParcial(true);
//		} else
//			CR.me.setEntregaParcial(false);
//	}
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
			jButton3.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton3.setText("F3 Vender Todo");
			jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton3.setMargin(new Insets(1,2,1,1));
		}
		return jButton3;
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
			jButton7.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton7.setText("F7 Imprimir Precierre");
			jButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton7.setMargin(new Insets(1,2,1,1));
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
			jButton8.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton8.setText("F8 Utilitarios");
			jButton8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton8.setMargin(new Insets(1,2,1,1));
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
			jLabel1.setText("Restantes:");
			jLabel1.setPreferredSize(new java.awt.Dimension(70,20));
		}
		return jLabel1;
	}
	/**
	 * This method initializes restantesTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getRestantesTF() {
		if(restantesTF == null) {
			restantesTF = new javax.swing.JTextField();
			restantesTF.setPreferredSize(new java.awt.Dimension(30,20));
			restantesTF.setEditable(false);
			restantesTF.setBackground(new java.awt.Color(242,242,238));
			restantesTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			restantesTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			restantesTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
			restantesTF.setFocusable(false);
		}
		return restantesTF;
	}
	/**
	 * This method initializes jLabel3
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel3() {
		if(jLabel3 == null) {
			jLabel3 = new javax.swing.JLabel();
			jLabel3.setText("Monto Restantes:");
			jLabel3.setPreferredSize(new java.awt.Dimension(105,20));
		}
		return jLabel3;
	}
	/**
	 * This method initializes abonosAProductosTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getAbonosAProductosTF() {
		if(abonosAProductosTF == null) {
			abonosAProductosTF = new javax.swing.JTextField();
			abonosAProductosTF.setPreferredSize(new java.awt.Dimension(100,20));
			abonosAProductosTF.setEditable(false);
			abonosAProductosTF.setBackground(new java.awt.Color(242,242,238));
			abonosAProductosTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			abonosAProductosTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			abonosAProductosTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			abonosAProductosTF.setFocusable(false);
		}
		return abonosAProductosTF;
	}
	/**
	 * This method initializes jLabel8
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel8() {
		if(jLabel8 == null) {
			jLabel8 = new javax.swing.JLabel();
			jLabel8.setText("Abonos a Productos:");
			jLabel8.setPreferredSize(new java.awt.Dimension(150,20));
		}
		return jLabel8;
	}

	/**
	 * This method initializes montoRestantesTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getMontoRestantesTF() {
		if(montoRestantesTF == null) {
			montoRestantesTF = new javax.swing.JTextField();
			montoRestantesTF.setPreferredSize(new java.awt.Dimension(105,20));
			montoRestantesTF.setEditable(false);
			montoRestantesTF.setBackground(new java.awt.Color(242,242,238));
			montoRestantesTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			montoRestantesTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			montoRestantesTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			montoRestantesTF.setFocusable(false);
		}
		return montoRestantesTF;
	}
}