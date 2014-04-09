/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : FacturacionPrincipal.java
 * Creado por : Victor Medina	<linux@epa.com.ve>
 * Creado en  : Feb 8, 2004 - 1:25:16 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 2.0.0
 * Fecha       : 03/09/2004 06:00 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Refactorización de los panels de iconos y mensajes.
 * =============================================================================
 * Versión     : 1.2.1
 * Fecha       : 01/07/2004 11:00 AM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : - Deshabilitado el cierre de la ventana a través de los comandos 
 * 				de ventana.
 * 				 - Agregados los íconos "pagosmas" y "pagosmenos" para agregar y 
 * 				quitar formas de pago la factura activa.
 * =============================================================================
 * Versión     : 1.2.0
 * Fecha       : 25/05/2004 01:27:34 PM
 * Analista    : GMARTINELLI
 * Descripción : - Cambiadas las referencias a "CR.me" por "CR.meVenta".
 * 				- Cambiados literales de la GUI, "Factura No." por "No.Artículos" y su respectivo cálculo.
 * 				- Actualizadas referencias a la clase CambiarCantidad, ManejoPagos y Consultas.
 * 				- Implementada la invocación al método bloquearCaja de la ME.
 * 				- Mapeo de las teclas de movimiento <arriba>, <abajo> y de los dígitos para identificar 
 * 				  entrada de códigos de forma manual.
 * 				- Eliminado el objeto jTextField3.
 * 				- Clase ClockLabel hecha pública e independiente.
 * =============================================================================
 * Versión     : 1.18 (según CVS)
 * Fecha       : 17/02/2004 03:54:34 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Organización del código y modificación en la línea 1150 para optimizar
 * 				 el vaciado de la tabla del detalle de facturación.
 * =============================================================================
 * Versión     : 1.4 (SEGUN CVS)
 * Fecha       : Feb 8, 2004
 * Analista    : Victor Medina <linux@epa.com.ve>
 * Descripción : Pantalla de Facturación Principal
 * =============================================================================
 */
package com.becoblohm.cr.gui.devolucion;

import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.excepciones.AfiliadoUsrExcepcion;
import com.becoblohm.cr.excepciones.AutorizacionExcepcion;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.MontoPagoExcepcion;
import com.becoblohm.cr.excepciones.PagoExcepcion;
import com.becoblohm.cr.excepciones.ProductoExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.gui.AnularProducto;
import com.becoblohm.cr.gui.CambiarCantidad;
import com.becoblohm.cr.gui.Consultas;
import com.becoblohm.cr.gui.EjecutarConCantidad;
import com.becoblohm.cr.gui.EntregaEditor;
import com.becoblohm.cr.gui.EntregaRenderer;
import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.gui.ModeloTabla;
import com.becoblohm.cr.gui.ModeloTablaPagoInterfaz;
import com.becoblohm.cr.gui.PanelIconosCR;
import com.becoblohm.cr.gui.PanelMensajesCR;
import com.becoblohm.cr.gui.Rebajas;
import com.becoblohm.cr.gui.ScanerSwitch;
import com.becoblohm.cr.gui.Utilitarios;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstado;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstadoVenta;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.extensiones.ManejoPagosFactory;
import com.becoblohm.cr.extensiones.RegistroCliente;
import com.becoblohm.cr.extensiones.RegistroClienteFactory;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.manejarventa.CancelarDevolucion;
import com.becoblohm.cr.manejarventa.DetalleTransaccion;
import com.becoblohm.cr.manejarventa.Venta;
import com.becoblohm.cr.perifericos.EfectuarLecturaEscaner;
import com.becoblohm.cr.utiles.Control;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.crprinterdriver.CRFiscalPrinterOperations;
import com.epa.crprinterdriver.PrinterNotConnectedException;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;
/**
 * @author vmedina
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se comentaron varibles sin uso
* Fecha: agosto 2011
*/
public class PantallaDevXCambio extends JDialog implements ActionListener, MouseListener, KeyListener, DocumentListener, ScanerSwitch {
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(PantallaDevXCambio.class);

	
	private ModeloTabla modeloTablaDetalleDevolucion = new ModeloTabla(false);
	private ModeloTabla modeloTablaDetalleFacturacion = new ModeloTabla();
	private ModeloTablaPagoInterfaz modeloTablaDetallePagos = new ModeloTablaPagoInterfaz();
	private DecimalFormat df = new DecimalFormat("#,##0.00");
	private int filaAModificar=0;
	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel14 = null;
	private javax.swing.JScrollPane jScrollPane = null;
	private javax.swing.JTable jTable = null;
	private javax.swing.JPanel jPanel15 = null;
	private javax.swing.JPanel jPanel16 = null;
	private javax.swing.JPanel jPanel20 = null;	
	private javax.swing.JScrollPane jScrollPane1 = null;
	private javax.swing.JTable jTable1 = null;
	private javax.swing.JTable jTable2 = null;	
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JButton jButton2 = null;
	private javax.swing.JLabel jLabel9 = null;
	private javax.swing.JPanel jPanel17 = null;
	private javax.swing.JPanel jPanel18 = null;
	private javax.swing.JLabel jLabel10 = null;
	private javax.swing.JPanel jPanel19 = null;
	private javax.swing.JLabel jLabel11 = null;
	private javax.swing.JTextField jTextField4 = null;
	private javax.swing.JLabel jLabel12 = null;
	private javax.swing.JTextField jTextField5 = null;
	private javax.swing.JLabel jLabel13 = null;
	private javax.swing.JTextField jTextField6 = null;
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
	private javax.swing.JScrollPane jScrollPane2 = null;
	private Venta original = null;
		
	private static int column = 7;
	private static boolean inicializando = true;
	//private static Servicios servicios = null;
	private static AnularProducto anularProducto = null; 
	private static CambiarCantidad cc = null;
	private static Rebajas rebajas = null; 
	private static Consultas consultas = null;
	//private static ClientesEnEspera clienteEnEspera = null;
	private static Utilitarios utilitarios = null;
	private static EjecutarConCantidad ec = null;
	//private static PantallaVuelto pantallaVuelto = null;
	private static PantallaVentaDevolucion ventaOriginal = null;
	private boolean eventoDeIngresoProducto = false;
	private boolean ejecutandoEvento = false;
	private EfectuarLecturaEscaner lector = null;
	private boolean escanerActivo = true;
	private boolean permitirEscaner = true;
	
	private CancelarDevolucion hilo = null;
	private JButton jButton = null;
	
	RegistroClienteFactory factory = new RegistroClienteFactory();  //  @jve:decl-index=0:
	/**
	 * This is the default constructor
	 */
	public PantallaDevXCambio(double pagoInicio, double nuevoPago) {
		super(MensajesVentanas.ventanaActiva);
		inicializando = true;
		original = CR.meVenta.getDevolucion().getVentaOriginal();
		
		initialize();
		//column = modeloTablaDetalleFacturacion.findColumn("Entrega");
		this.ejecutandoEvento = false;

		jScrollPane.addKeyListener(this);
		jScrollPane.addMouseListener(this);
		
		jTable.addKeyListener(this);
		jTable.addMouseListener(this);

		jScrollPane1.addKeyListener(this);
		jScrollPane1.addMouseListener(this);
		
		jTable1.addKeyListener(this);
		jTable1.addMouseListener(this);
		
		jTable2.addKeyListener(this);
		jTable2.addMouseListener(this);		
				
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);
		
		jButton2.addKeyListener(this);
		jButton2.addMouseListener(this);
		
		jButton3.addKeyListener(this);
		jButton3.addMouseListener(this);
		
		jButton4.addKeyListener(this);
		jButton4.addMouseListener(this);
		
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
		
		jButton10.addKeyListener(this);
		jButton10.addMouseListener(this);
		
		jButton11.addKeyListener(this);
		jButton11.addMouseListener(this);
		
		jButton12.addKeyListener(this);
		jButton12.addMouseListener(this);
		
		jButton13.addMouseListener(this);
		jButton13.addKeyListener(this);
		
		jButton14.addKeyListener(this);
		jButton14.addMouseListener(this);
		
		jButton.addKeyListener(this);
		jButton.addMouseListener(this);
		
		//Comprobamos el límite de entrega a caja porque cuando regresamos de una pantalla de servicio
		//se requiere chequear si se modificó el monto recaudado (ejemplo cuando se realizo un abono a un apartado)				
		this.comprobarLimiteEntrega();

		CR.inputEscaner.getDocument().addDocumentListener(this);
		try {
			CR.meVenta.iniciarVentaDeDevolucion(nuevoPago,pagoInicio);
		} catch (UsuarioExcepcion e) {
			e.printStackTrace();
		} catch (MaquinaDeEstadoExcepcion e) {
			e.printStackTrace();
		} catch (ConexionExcepcion e) {
			e.printStackTrace();
		} catch (ExcepcionCr e) {
			e.printStackTrace();
		}
		this.repintarFactura();
		inicializando = false;
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

		this.setSize(810,600);
		this.setContentPane(getJContentPane());
		this.setName("FramePrincipalFacturacion");
		this.setVisible(false);
		this.setTitle("CR ver. " + CR.version + " - Facturaci\u00f3n");
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
			this.setUndecorated(true);
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
			jPanel.add(getJPanel20(),null);
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
			jPanel1.setPreferredSize(new java.awt.Dimension(130,565));
			jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
			jPanel1.setBackground(new java.awt.Color(242,242,238));
			jPanel1.add(getJButton(), null);
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
			jPanel14.setPreferredSize(new java.awt.Dimension(656,125));
			jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle de Facturación: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), java.awt.Color.black));
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
			jTable = new TablaFact(modeloTablaDetalleFacturacion);
			jTable.getTableHeader().setReorderingAllowed(false) ;
			jTable.getColumnModel().getColumn(0).setPreferredWidth(88);
			jTable.getColumnModel().getColumn(1).setPreferredWidth(150);
			jTable.getColumnModel().getColumn(2).setPreferredWidth(65);
			jTable.getColumnModel().getColumn(3).setPreferredWidth(75);
			jTable.getColumnModel().getColumn(4).setPreferredWidth(75);
			jTable.getColumnModel().getColumn(5).setPreferredWidth(76);
			jTable.getColumnModel().getColumn(6).setPreferredWidth(35);
			jTable.setLocale(new java.util.Locale("es", "VE", ""));
			jTable.setRowHeight(jTable.getRowHeight()+10);
			jTable.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jTable.setBackground(new java.awt.Color(242,242,238));
			jTable.setCellSelectionEnabled(true);
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
			jScrollPane.setPreferredSize(new java.awt.Dimension(645,100));
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
			layFlowLayout17.setHgap(1);
			layFlowLayout17.setVgap(1);
			jPanel15.setLayout(layFlowLayout17);
			jPanel15.add(getJScrollPane1(), null);
			jPanel15.add(getJPanel17(), null);
			jPanel15.add(getJButton1(), null);
			jPanel15.add(getJButton2(), null);
			jPanel15.setPreferredSize(new java.awt.Dimension(328,150));
			jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Formas de Pago: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), java.awt.Color.black));
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
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private javax.swing.JTable getJTable1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTable1() - start");
		}

		if(jTable1 == null) {
			jTable1 = new javax.swing.JTable(modeloTablaDetallePagos);
			jTable1.getTableHeader().setReorderingAllowed(false) ;
			jTable1.setLocale(new java.util.Locale("es", "VE", ""));
			jTable1.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jTable1.setBackground(new java.awt.Color(242,242,238));		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTable1() - end");
		}
		return jTable1;
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
			jScrollPane1.setViewportView(getJTable1());
			jScrollPane1.setPreferredSize(new java.awt.Dimension(310,60));
			jScrollPane1.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJScrollPane1() - end");
		}
		return jScrollPane1;
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
			jButton1.setText("Forma de Pago");
			jButton1.setBackground(new java.awt.Color(226,226,222));
			jButton1.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/add.png")));
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
			jButton2.setText("Forma de Pago");
			jButton2.setBackground(new java.awt.Color(226,226,222));
			jButton2.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/quitar.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton2() - end");
		}
		return jButton2;
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
			jLabel9.setLocale(new java.util.Locale("es", "VE", ""));
			jLabel9.setText("Saldo a cancelar: " + df.format(0));
			jLabel9.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel9() - end");
		}
		return jLabel9;
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

		if(jPanel17 == null) {
			jPanel17 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout22 = new java.awt.FlowLayout();
			layFlowLayout22.setHgap(1);
			layFlowLayout22.setVgap(1);
			jPanel17.setLayout(layFlowLayout22);
			jPanel17.add(getJLabel9(), null);
			jPanel17.setPreferredSize(new java.awt.Dimension(300,25));
			jPanel17.setBackground(new java.awt.Color(242,242,238));
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
			jLabel10.setText("No.Artículos: " + df.format(0));
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
			jTextField4.setLocale(new java.util.Locale("es", "VE", ""));
			jTextField4.setText(df.format(0));
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
			jTextField5.setLocale(new java.util.Locale("es", "VE", ""));
			jTextField5.setText(df.format(0));
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
			jTextField6.setLocale(new java.util.Locale("es", "VE", ""));
			jTextField6.setText(df.format(0));
			jTextField6.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			jTextField6.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField6() - end");
		}
		return jTextField6;
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
			java.awt.FlowLayout layFlowLayout19 = new java.awt.FlowLayout();
			layFlowLayout19.setHgap(1);
			layFlowLayout19.setVgap(1);
			jPanel20.setLayout(layFlowLayout19);
			jPanel20.add(getJScrollPane2(), null);
			jPanel20.setPreferredSize(new java.awt.Dimension(656,125));
			jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle Devolución: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), java.awt.Color.black));
			jPanel20.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel20() - end");
		}
		return jPanel20;
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
			jScrollPane2.setViewportView(getJTable2());
			jScrollPane2.setPreferredSize(new java.awt.Dimension(645,100));
			jScrollPane2.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJScrollPane2() - end");
		}
		return jScrollPane2;
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
			jTable2 = new javax.swing.JTable(modeloTablaDetalleDevolucion);
			jTable2.getTableHeader().setReorderingAllowed(false) ;
			jTable2.getColumnModel().getColumn(0).setPreferredWidth(88);
			jTable2.getColumnModel().getColumn(1).setPreferredWidth(150);
			jTable2.getColumnModel().getColumn(2).setPreferredWidth(65);
			jTable2.getColumnModel().getColumn(6).setPreferredWidth(35);
			jTable2.setLocale(new java.util.Locale("es", "VE", ""));
			jTable2.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jTable2.setBackground(new java.awt.Color(242,242,238));

		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTable2() - end");
		}
		return jTable2;
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
			jButton10.setText("F7 Venta Original");
			jButton10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton10.setDefaultCapable(false);
			jButton10.setMargin(new Insets(1,2,1,1));
			jButton10.setBackground(new java.awt.Color(226,226,222));
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
			jButton13.setBackground(new java.awt.Color(226,226,222));
			jButton13.setEnabled(false);
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
		
		if (logger.isDebugEnabled()) {
			logger.debug("actionPerformed(ActionEvent) - end");
		}
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unchecked")
	public void mouseReleased(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - start");
		}

		if (!ejecutandoEvento) {
			eventoDeIngresoProducto = false;
			escanerActivo = false;
			//Mapeo de F1 en eventos de Mouse
			if(e.getSource().equals(jButton3)){
				ejecutandoEvento = true;
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				if(CR.meVenta.getVenta() == null) {
					MensajesVentanas.mensajeError("No existe Venta activa");
				} else {
					if (jTable.getRowCount() > 0) {
						this.actualizarTipoEntregaDetalles();
						cc = null;
						if(jTable.getSelectedRow() != -1){
							filaAModificar = jTable.getSelectedRow();
							cc = new CambiarCantidad(filaAModificar, 1);
						}
						else {
							cc = new CambiarCantidad(1);
						}
						MensajesVentanas.centrarVentanaDialogo(cc);
						eventoDeIngresoProducto = true;
						this.repintarFactura();
						if (logger.isDebugEnabled()) {
							logger.debug("mouseClicked(MouseEvent) - F1");
						}		
					} else {
						MensajesVentanas.mensajeError("No hay detalles en la venta actual");
					}
				}
				CR.inputEscaner.getDocument().addDocumentListener(this);
			}
	
			//Mapeo de F2 en eventos de Mouse
			else if(e.getSource().equals(jButton5)){
				ejecutandoEvento = true;
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				if(CR.meVenta.getVenta() == null) {
					MensajesVentanas.mensajeError("No existe Venta activa");
				} else {
					if (jTable.getRowCount() > 0) {
						this.actualizarTipoEntregaDetalles();
						try {
							anularProducto = null;
							if(jTable.getSelectedRow() == -1){
								anularProducto = new AnularProducto(CR.meVenta.getVenta().getDetallesTransaccion().size()-1/*,input, output*/);
								MensajesVentanas.centrarVentanaDialogo(anularProducto);
								this.repintarFactura();		
								anularProducto = null;
							}
							else{
								anularProducto = new AnularProducto(jTable.getSelectedRow()/*, input, output*/);
								MensajesVentanas.centrarVentanaDialogo(anularProducto);
								anularProducto = null;
								this.repintarFactura();	
							}
						} catch (Exception e1) {
							logger.error("mouseClicked(MouseEvent)", e1);

							MaquinaDeEstadoExcepcion meEx = new MaquinaDeEstadoExcepcion("Estado de caja Incorrecto");
							MensajesVentanas.mensajeError(meEx.getMensaje());
						}
						if (logger.isDebugEnabled()) {
							logger.debug("mouseClicked(MouseEvent) - F2");
						}
					} else {
						MensajesVentanas.mensajeError("No hay detalles en la venta actual");
					}
				}
				CR.inputEscaner.getDocument().addDocumentListener(this);
			}
	
			//Mapeo de F3 en Eventos de Mouse
			else if(e.getSource().equals(jButton4) && this.getJButton4().isEnabled()){
				ejecutandoEvento = true;
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				this.actualizarTipoEntregaDetalles();
				try {
					rebajas = null;
					int renglon;
					if(jTable.getSelectedRow() >= 0){
						renglon = jTable.getSelectedRow();
					} else {
						renglon = CR.meVenta.getVenta().getDetallesTransaccion().size()-1;
					}
					if (renglon >= 0){
						if (CR.meVenta.isRenglonPermiteRebaja(renglon)) {
							rebajas = new Rebajas(renglon);
							MensajesVentanas.centrarVentanaDialogo(rebajas);
							rebajas = null;
							this.repintarFactura();
						} else {
							MensajesVentanas.aviso("El producto no permite rebajas");
						}
					} else {
						MensajesVentanas.aviso("No ha seleccionado ningún producto para la rebaja");
					}
				} catch (Exception e1) {
					logger.error("mouseClicked(MouseEvent)", e1);

					MaquinaDeEstadoExcepcion meEx = new MaquinaDeEstadoExcepcion("Estado de Caja Incorrecto");
					MensajesVentanas.mensajeError(meEx.getMensaje());
				}
				CR.inputEscaner.getDocument().addDocumentListener(this);
			}
	
			//Mapeo de F4 en eventos de Mouse
			else if(e.getSource().equals(jButton6)){
				ejecutandoEvento = true;
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				this.actualizarTipoEntregaDetalles();
				
				try {					
					//Generar pago con cero para cambiar a estado pago
					//Pago pagoAct = new Pago("01",0,"","","","",0,"");
					//CR.meVenta.efectuarPago(pagoAct);
	
					// Finalizar la venta
					if(CR.meVenta.getVenta() == null) {
						MensajesVentanas.mensajeError("No existe Venta activa");
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} else {
						CR.meVenta.ejecucionPromociones(CR.meVenta.getVenta());
						CR.meVenta.consultarMontoTrans(true);
						// Verificamos que en caso de entregas a domicilio exista un cliente activo	
						if(CR.meVenta.getVenta().verificarTieneEntregaDom() && CR.meVenta.getVenta().getCliente().getCodCliente() == null) {
							MensajesVentanas.aviso("Debe asignar un cliente para poder hacer la entrega a domicilio");
							CR.inputEscaner.getDocument().addDocumentListener(this);
						} else if(Sesion.isRequiereCliente() && CR.meVenta.getVenta().getCliente().getCodCliente() == null) {
							MensajesVentanas.aviso("Debe asignar un cliente a la transacción");
							CR.inputEscaner.getDocument().addDocumentListener(this);
						} else {
							try {
								Vector<Object> pagos = ManejoPagosFactory.getInstance().realizarPago(CR.meVenta.consultarMontoTrans(), CR.meVenta.getVenta().getPagos(), CR.meVenta.getVenta().getCliente(), CR.meVenta.getVenta().getNumTransaccion());
								if (((Boolean)pagos.elementAt(2)).booleanValue()) {
									for (int i=0; i<((Vector<Pago>)pagos.elementAt(0)).size(); i++) {
										Pago pagoAct = ((Vector<Pago>)pagos.elementAt(0)).elementAt(i);
										CR.meVenta.efectuarPago(pagoAct);
									}
								//Se procesa la devolución
								CR.meVenta.finalizarDevolucionXCambio();
								//Esperamos que finalice la impresión de la anulación antes de continuar con la venta
								while (CR.meVenta.getTransaccionPorImprimir()!=null || 
										CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
										MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()) {
									MensajesVentanas.aviso("La impresión de la nota de crédito está en curso, \na continuación se imprimirá la nueva factura");
								}
								//Se finaliza la venta
								CR.meVenta.finalizarVenta();
										
								dispose();
								CR.me.repintarMenuPrincipal();
								this.comprobarLimiteEntrega();
							}
						} catch (MontoPagoExcepcion e1) {
							logger.error("mouseClicked(MouseEvent)", e1);
							Pago pagoAct = new Pago("01",0,"","","","",0,"");
							CR.meVenta.efectuarPago(pagoAct);
							// Chequeamos si existe un pago que permita vuelto para devolver la diferencia
							double monto = 0;
							for (int i=0; i<CR.meVenta.getVenta().getPagos().size(); i++) {
								Pago pago = (Pago) CR.meVenta.getVenta().getPagos().elementAt(i);
								if (pago.getFormaPago().isPermiteVuelto())
									monto += pago.getMonto();
								}
								if (monto >= (CR.meVenta.getVenta().obtenerMontoPagado()-CR.meVenta.consultarMontoTrans())) {
									//Finalizar la devolución									
									CR.meVenta.finalizarDevolucionXCambio();
									while (CR.meVenta.getTransaccionPorImprimir()!=null || 
											CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
											MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()) {
										MensajesVentanas.aviso("La impresión de la nota de crédito está en curso, \na continuación se imprimirá la nueva factura ");
									}										
									//Se finaliza la venta
									CR.meVenta.finalizarVenta();
									dispose();
									CR.me.repintarMenuPrincipal();
									this.comprobarLimiteEntrega();
								} else {
									CR.inputEscaner.getDocument().addDocumentListener(this);
									throw new ExcepcionCr("Debe eliminar los pagos. No permiten vuelto al Cliente");
								}
								}
							}
						}
					} catch (UsuarioExcepcion e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (MaquinaDeEstadoExcepcion e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (ConexionExcepcion e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (PagoExcepcion e1){
						logger.error("mouseClicked(MouseEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
						this.repintarFactura();
					}
					catch (ExcepcionCr e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
						//this.repintarFactura();
					} catch (PrinterNotConnectedException e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMessage());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					}
				if (logger.isDebugEnabled()) {
					logger.debug("mouseClicked(MouseEvent) - F4");
				}
			}
	
			//Mapeo de F5 en eventos de Mouse
			else if(e.getSource().equals(jButton7)){
				ejecutandoEvento = true;
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				this.actualizarTipoEntregaDetalles();
				//try {
					//CR.me.consultaProductoCliente();
					consultas = null;
					consultas = new Consultas();
					MensajesVentanas.centrarVentanaDialogo(consultas);
					String casoConsulta = consultas.getCodigoSeleccionado()[0];
					String codigo = consultas.getCodigoSeleccionado()[1];
					if (casoConsulta!=null) {
						try {
							if (new Integer(casoConsulta).intValue()<8) {
								CR.meVenta.ingresarLineaProducto(codigo, Sesion.capturaTeclado);
								eventoDeIngresoProducto = true;
							} else {
								CR.meVenta.asignarCliente(codigo, false);
							}
						} catch (ExcepcionCr e1) {
							logger.error("mouseClicked(MouseEvent)", e1);

							MensajesVentanas.mensajeError(e1.getMensaje());
						} catch (ConexionExcepcion e1) {
							logger.error("mouseClicked(MouseEvent)", e1);

							MensajesVentanas.mensajeError(e1.getMensaje());
						}
					}
				/*} catch (ExcepcionCr e1) {
					logger.error("mouseClicked(MouseEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (ConexionExcepcion e1) {
					logger.error("mouseClicked(MouseEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
				}*/
				this.repintarFactura();			
				if (logger.isDebugEnabled()) {
					logger.debug("mouseClicked(MouseEvent) - F5");
				}
				CR.inputEscaner.getDocument().addDocumentListener(this);
			}
	
			//Mapeo de F6 en eventos de Mouse
			else if(e.getSource().equals(jButton9)){
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				if (MensajesVentanas.preguntarSiNo("¿Desea colocar la devolución en espera?") == 0)
				{
					try {
						CR.meVenta.colocarDevolucionEnEspera();
						dispose();
						CR.me.repintarMenuPrincipal();
					} catch (BaseDeDatosExcepcion e1) {
						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (ConexionExcepcion e1) {
						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (SQLException e1) {
						e1.printStackTrace();
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (UsuarioExcepcion e1) {
						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (MaquinaDeEstadoExcepcion e1) {
						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (ExcepcionCr e1) {
						e1.printStackTrace();
						CR.inputEscaner.getDocument().addDocumentListener(this);
					}
				} else {
					CR.inputEscaner.getDocument().addDocumentListener(this);
				}
				if (logger.isDebugEnabled()) {
					logger.debug("mouseClicked(MouseEvent) - F6");
				}
			}
			
			//Mapeo de F7 en eventos de Mouse
			else if(e.getSource().equals(jButton10)){
				ejecutandoEvento = true;
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				this.actualizarTipoEntregaDetalles();
				try {
					CR.meVenta.cargarVentaOriginal();
					ventaOriginal = null;
					ventaOriginal = new PantallaVentaDevolucion(original.getCodTienda(),original.getNumCajaFinaliza(),original.getNumTransaccion(),original.getFechaTrans());
					//ventaOriginal.repintarFactura();
					MensajesVentanas.centrarVentanaDialogo(ventaOriginal);
					ventaOriginal = null;
					this.repintarFactura();
				} catch (MaquinaDeEstadoExcepcion e1) {
					e1.printStackTrace();
				} catch (XmlExcepcion e1) {
					e1.printStackTrace();
				} catch (FuncionExcepcion e1) {
					e1.printStackTrace();
				} catch (BaseDeDatosExcepcion e1) {
					e1.printStackTrace();
				} catch (ConexionExcepcion e1) {
					e1.printStackTrace();
				}
				
				this.comprobarLimiteEntrega();
				if (logger.isDebugEnabled()) {
					logger.debug("mouseClicked(MouseEvent) - F7");
				}
				CR.inputEscaner.getDocument().addDocumentListener(this);
			}
	
			//Mapeo de F8 en Eventos de Mouse
			else if(e.getSource().equals(jButton8)){
				ejecutandoEvento = true;
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				this.actualizarTipoEntregaDetalles();
				//com.becoblohm.cr.utiles.MensajesVentanas.centrarVentanaDialogo(new com.becoblohm.cr.MenuPrincipal());
				utilitarios = null;
				utilitarios = new Utilitarios();
				MensajesVentanas.centrarVentanaDialogo(utilitarios);
				this.repintarFactura();
				comprobarLimiteEntrega();
				if (utilitarios.isEmitidoRepZ()) {
					dispose();
				} else if (utilitarios.isCierreAutorizado()){
					dispose();
				} else {
					CR.inputEscaner.getDocument().addDocumentListener(this);
				}
				if (logger.isDebugEnabled()) {
					logger.debug("mouseClicked(MouseEvent) - F8");
				}
			}
	
			//Mapeo de F9 en Eventos de Mouse
			else if(e.getSource().equals(jButton11)){
				ejecutandoEvento = true;
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				if (MensajesVentanas.preguntarSiNo(Sesion.MSG_CANCELAR)==0) {
					this.actualizarTipoEntregaDetalles();
					try{
						int tienda = CR.meVenta.getDevolucion().getVentaOriginal().getCodTienda();
						SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy-MM-dd");
						String fechaTransString = fechaTrans.format(CR.meVenta.getDevolucion().getVentaOriginal().getFechaTrans());
						int caja =  CR.meVenta.getDevolucion().getVentaOriginal().getNumCajaFinaliza();
						int transaccion = CR.meVenta.getDevolucion().getVentaOriginal().getNumTransaccion();
						CR.meVenta.anularDevolucionActiva();
						CR.meVenta.anularVentaActiva();
						dispose();
						hilo = new CancelarDevolucion(tienda, fechaTransString, caja, transaccion);
						hilo.start();
						CR.me.repintarMenuPrincipal();
					} catch(ExcepcionCr f){
						logger.error("mouseClicked(MouseEvent)", f);

						MensajesVentanas.mensajeError(f.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (ConexionExcepcion f1) {
						logger.error("mouseClicked(MouseEvent)", f1);

						MensajesVentanas.mensajeError(f1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					}
				} else {
					CR.inputEscaner.getDocument().addDocumentListener(this);
				}
				if (logger.isDebugEnabled()) {
					logger.debug("mouseClicked(MouseEvent) - F9");
				}
			}
		
			//Mapeo de F10 en eventos de Mouse
			else if(e.getSource().equals(jButton12)){
				//Mapeo de F10
				try {
					CR.me.abrirGaveta(true);
				} catch (ConexionExcepcion e1) {
					logger.error("mouseClicked(MouseEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (ExcepcionCr e1) {
					logger.error("mouseClicked(MouseEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
				}
				if (logger.isDebugEnabled()) {
					logger.debug("mouseClicked(MouseEvent) - F10");
				}
			}
/*
			//Mapeo de F11 en eventos de Mouse
			else if(e.getSource().equals(jButton13)){
				ejecutandoEvento = true;
				//CR.inputEscaner.getDocument().removeDocumentListener(this);
				//this.actualizarTipoEntregaDetalles();
				try {
					boolean validarClaveAlSalir = true;
					try {
						validarClaveAlSalir = InitCR.preferenciasCR.getConfigStringForParameter("facturacion","validarClaveAlSalir").trim().toUpperCase().equals("S")?true:false;
					} catch (NoSuchNodeException e1) {
						MensajesVentanas.mensajeError("Falla carga de preferencias para Validación de Clave al Salir");
					} catch (UnidentifiedPreferenceException e1) {
					}
					if(MensajesVentanas.preguntarSiNo("¿Confirma que desea bloquear la caja?")==0) {
						if (validarClaveAlSalir) {
							ValidarPassword vp = new ValidarPassword();
							MensajesVentanas.centrarVentanaDialogo(vp);
							if (vp.isClaveCorrecta()) {
								CR.me.bloquearCaja();
								dispose();
							}
						} else {
							CR.me.bloquearCaja();
							dispose();
						}
					}
				} catch (UsuarioExcepcion e1) {
					logger.error("mouseClicked(MouseEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (MaquinaDeEstadoExcepcion e1) {
					logger.error("mouseClicked(MouseEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (ConexionExcepcion e1) {
					logger.error("mouseClicked(MouseEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (ExcepcionCr e1) {
					logger.error("mouseClicked(MouseEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
				}
				if (logger.isDebugEnabled()) {
					logger.debug("mouseClicked(MouseEvent) - F11");
				}
			}
	
			//Mapeo de F12 en eventos de Mouse
			else if(e.getSource().equals(jButton14)){
				ejecutandoEvento = true;
				this.actualizarTipoEntregaDetalles();
				boolean errorPago = false;
				try {
					boolean validarClaveAlSalir = true;
					try {
						validarClaveAlSalir = InitCR.preferenciasCR.getConfigStringForParameter("facturacion","validarClaveAlSalir").trim().toUpperCase().equals("S")?true:false;
					} catch (NoSuchNodeException e1) {
						MensajesVentanas.mensajeError("Falla carga de preferencias para Validación de Clave al Salir");
					} catch (UnidentifiedPreferenceException e1) {
					}
					
					if(MensajesVentanas.preguntarSiNo("Se cerrará la sesión actual.\nCualquier venta activa será anulada.\n¿Confirma que desea cerrar la sesion?")==0) {
						if (validarClaveAlSalir) {
							ValidarPassword vp = new ValidarPassword();
							MensajesVentanas.centrarVentanaDialogo(vp);
							if (vp.isClaveCorrecta()) {
								try {
									CR.meVenta.anularVentaActiva();
									this.repintarFactura();
								} catch (PagoExcepcion e1) {
									errorPago = true;
									MensajesVentanas.aviso("No se puede anular la venta activa");
									logger.error("keyPressed(KeyEvent)", e1);
								} catch (Exception e1) {
									logger.error("keyPressed(KeyEvent)", e1);
								}
								if(MaquinaDeEstado.cerrarSesion()&& !errorPago) {
									dispose();
								} else {
									this.repintarFactura();
								}
								
							} 
						} else {
							try {
								CR.meVenta.anularVentaActiva();
								this.repintarFactura();
							} catch (PagoExcepcion e1) {
								errorPago = true;
								MensajesVentanas.aviso("No se puede anular la venta activa");
								logger.error("keyPressed(KeyEvent)", e1);
							}
						 	catch (Exception e1) {
								logger.error("keyPressed(KeyEvent)", e1);
							}
							if(MaquinaDeEstado.cerrarSesion() && !errorPago) {
								dispose();
							} else {
								this.repintarFactura();
							}
							

						}
					}
				}catch(ExcepcionCr ex){
					logger.error("mouseClicked(MouseEvent)", ex);

					MensajesVentanas.mensajeError(ex.getMensaje());
				} catch (ConexionExcepcion f) {
					logger.error("mouseClicked(MouseEvent)", f);

					MensajesVentanas.mensajeError(f.getMensaje());
				}
				if (logger.isDebugEnabled()) {
					logger.debug("mouseClicked(MouseEvent) - F12");
				}
			}
*/	
			//Mapeo la tecla de +
			else if(e.getSource().equals(getJButton1())) {
				ejecutandoEvento = true;
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				this.actualizarTipoEntregaDetalles();
				try {
					if(CR.meVenta.getVenta() == null) {
						MensajesVentanas.mensajeError("No existe Venta activa");
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} else {
						CR.meVenta.consultarMontoTrans(true);
						//Verificamos que en caso de entregas a domicilio exista un cliente activo		  
						if(CR.meVenta.getVenta().verificarTieneEntregaDom() && CR.meVenta.getVenta().getCliente().getCodCliente() == null) {
							MensajesVentanas.aviso("Debe asignar un cliente para poder hacer la entrega a domicilio");
							CR.inputEscaner.getDocument().addDocumentListener(this);
						} else {
							try {
								Vector<Object> pagoRealizado = ManejoPagosFactory.getInstance().realizarNuevoPago(CR.meVenta.consultarMontoTrans(),CR.meVenta.getVenta().getPagos(), CR.meVenta.getVenta().getCliente());
								for (int i=0; i<((Vector<Pago>)pagoRealizado.elementAt(0)).size(); i++)
									CR.meVenta.efectuarPago(((Vector<Pago>)pagoRealizado.elementAt(0)).elementAt(i));
								if (((Double)pagoRealizado.elementAt(1)).doubleValue()>= -0.001) {
									jLabel9.setText("Saldo a devolver: " + df.format(((Double)pagoRealizado.elementAt(1)).doubleValue()));
									//Finalizar la devolución
									CR.meVenta.finalizarDevolucion();
									//Esperamos que finalice la impresión de la devolución
									while (CR.meVenta.getTransaccionPorImprimir() != null || 
											CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
											MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()) {
										MensajesVentanas.aviso("La impresión de la nota de crédito está en curso, \na continuación se imprimirá la nueva factura");
									}
									//Se finaliza la venta
									//this.repintarFactura();
									/*CR.meVenta.finalizarVenta();
									this.repintarFactura();
									CR.me.repintarIconos();
									this.comprobarLimiteEntrega();*/
									CR.meVenta.finalizarVenta();
									dispose();
									CR.me.repintarMenuPrincipal();
									this.comprobarLimiteEntrega();
								} else {
									jLabel9.setText("Saldo a cancelar: " + df.format(-((Double)pagoRealizado.elementAt(1)).doubleValue()));
									//CR.meVenta.volverAFacturacion();
								}
							} catch (MontoPagoExcepcion e1) {
								logger.error("mouseClicked(MouseEvent)", e1);

								e1.setMensaje("Pagos completados. Sólo se puede totalizar la transacción");
								CR.inputEscaner.getDocument().addDocumentListener(this);
								throw e1;
							}
						}
					}
				} catch (ExcepcionCr e1) {
					logger.error("mouseClicked(MouseEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
					CR.inputEscaner.getDocument().addDocumentListener(this);
				} catch (ConexionExcepcion e1) {
					logger.error("mouseClicked(MouseEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
					CR.inputEscaner.getDocument().addDocumentListener(this);
				} catch (PrinterNotConnectedException f1) {
					logger.error("mouseClicked(MouseEvent)", f1);

					MensajesVentanas.mensajeError(f1.getMessage());
					CR.inputEscaner.getDocument().addDocumentListener(this);
				}
				this.repintarFactura();
			}

			//Mapeo la tecla de -
			else if(e.getSource().equals(getJButton2())) {
				ejecutandoEvento = true;
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				this.actualizarTipoEntregaDetalles();
				try {
					ManejoPagosFactory.getInstance().recalcularPagoDevolucion(CR.meVenta.getDevolucion().getMontoBase() + CR.meVenta.getDevolucion().getMontoImpuesto(),0,CR.meVenta.getVenta().getPagos());
						Vector<Object> pagoRealizado = ManejoPagosFactory.getInstance().eliminarPago(CR.meVenta.consultarMontoTrans(),CR.meVenta.getVenta().getPagos(), CR.meVenta.getVenta().getCliente());
						//CR.meVenta.getVenta().getPagos().addElement(ManejoPagos.realizarPagoDevolucion(CR.meVenta.getDevolucion().getMontoBase() + CR.meVenta.getDevolucion().getMontoImpuesto()));
						if (((Double)pagoRealizado.elementAt(1)).doubleValue()>=0) {
							jLabel9.setText("Saldo a devolver: " + df.format(((Double)pagoRealizado.elementAt(1)).doubleValue()));
							//Finaliza la devolución
							CR.meVenta.finalizarDevolucion();							
							//Esperamos que fnalice la impresión de la devolución
							while (CR.meVenta.getTransaccionPorImprimir() != null || 
									CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
									MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()) {
								MensajesVentanas.aviso("La impresión de la anulación está en curso, \na continuación se imprimirá la nueva factura");
							}							
							CR.meVenta.finalizarVenta();
							dispose();
							CR.me.repintarMenuPrincipal();
						} else {
							jLabel9.setText("Saldo a cancelar: " + df.format(-((Double)pagoRealizado.elementAt(1)).doubleValue()));
						} 
				//	}else
				//		MensajesVentanas.aviso("No se puede eliminar pago actual");
				} catch (ExcepcionCr e1) {
					logger.error("mouseClicked(MouseEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
					CR.inputEscaner.getDocument().addDocumentListener(this);
				} catch (ConexionExcepcion e1) {
					logger.error("mouseClicked(MouseEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
					CR.inputEscaner.getDocument().addDocumentListener(this);
				} catch (PrinterNotConnectedException f1) {
					logger.error("mouseClicked(MouseEvent)", f1);

					MensajesVentanas.mensajeError(f1.getMessage());
					CR.inputEscaner.getDocument().addDocumentListener(this);
				}
				this.repintarFactura();
			}
			
			/*
			 * Mapeamos la funcion Asignar clientes al click del mouse 
			 * @author Victor Medina - linux@epa.com.ve
			 */
			else if(e.getSource().equals(jButton)) {
				CR.inputEscaner.getDocument().removeDocumentListener(this);
					
				if (jButton.getText().equals("Asignar Clientes")){
//					llamada a la extension de asigna/registrar cliente en CR  02/2009 Wdiaz 
					RegistroCliente registro = factory.getInstance();
					registro.MostrarPantallaCliente(false,10);
				} else {
					int respuesta = MensajesVentanas.preguntarSiNo("¿Desea quitar el cliente asignado?");
					if (respuesta==0){ //Si desea eliminarlo
						try {
							CR.meVenta.quitarCliente();
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
					}
				}
				CR.inputEscaner.getDocument().addDocumentListener(this);
				eventoDeIngresoProducto = true;
				this.repintarFactura();

			}
			ejecutandoEvento = false;
			escanerActivo = true;
			getJTable().requestFocus();

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
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@SuppressWarnings("unchecked")
	public void keyPressed(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}

		boolean dejarPasar = false;
		boolean ejecutado = false;
		if (!ejecutandoEvento) {
			escanerActivo = false;
			eventoDeIngresoProducto = false;
			//Mapeo de todas las teclas de funcion de la interfaz
			//Mapeo de F1
			if(e.getKeyCode() == KeyEvent.VK_F1){
				ejecutandoEvento = true;
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				
				if(CR.meVenta.getVenta() == null) {
					MensajesVentanas.mensajeError("No existe Venta activa");
				} else {
					if (jTable.getRowCount() > 0) {
						this.actualizarTipoEntregaDetalles();
						ejecutado = true;
						cc = null;
						if(jTable.getSelectedRow() != -1){
							filaAModificar = jTable.getSelectedRow();
							cc = new CambiarCantidad(filaAModificar, 1);
						}
						else {
							cc = new CambiarCantidad(1);
						}
						MensajesVentanas.centrarVentanaDialogo(cc);
						eventoDeIngresoProducto = true;
						this.repintarFactura();
						if (logger.isDebugEnabled()) {
							logger.debug("keyPressed(KeyEvent) - F1");
						}		
					} else {
						MensajesVentanas.mensajeError("No hay detalles en la venta actual");
					}
				}
				CR.inputEscaner.getDocument().addDocumentListener(this);
			}
			
			//Mapeo de F3
			else if(e.getKeyCode() == KeyEvent.VK_F3 && this.getJButton4().isEnabled()){
				ejecutandoEvento = true;
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				this.actualizarTipoEntregaDetalles();
				ejecutado = true;
				try {
					rebajas = null;
					int renglon;
					if(jTable.getSelectedRow() >= 0){
						renglon = jTable.getSelectedRow();
					} else {
						renglon = CR.meVenta.getVenta().getDetallesTransaccion().size()-1;
					}
					if (CR.meVenta.isRenglonPermiteRebaja(renglon)) {
						rebajas = new Rebajas(renglon);
						MensajesVentanas.centrarVentanaDialogo(rebajas);
						rebajas = null;
						this.repintarFactura();
					} else {
						MensajesVentanas.aviso("El producto no permite rebajas");
					}
				} catch (Exception e1) {
					logger.error("keyPressed(KeyEvent)", e1);

					MaquinaDeEstadoExcepcion meEx = new MaquinaDeEstadoExcepcion("Estado de caja Incorrecto");
					MensajesVentanas.mensajeError(meEx.getMensaje());
				}
				if (logger.isDebugEnabled()) {
					logger.debug("keyPressed(KeyEvent) - F3");
				}
				CR.inputEscaner.getDocument().addDocumentListener(this);
			}
			
			//Mapeo de F2
			else if(e.getKeyCode() == KeyEvent.VK_F2){
				ejecutandoEvento = true;
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				if(CR.meVenta.getVenta() == null) {
					MensajesVentanas.mensajeError("No existe Venta activa");
				} else {
					if (jTable.getRowCount() > 0) {
						this.actualizarTipoEntregaDetalles();
						ejecutado = true;
						try {
							anularProducto = null;
							if(jTable.getSelectedRow() == -1){
								anularProducto = new AnularProducto(CR.meVenta.getVenta().getDetallesTransaccion().size()-1);
								MensajesVentanas.centrarVentanaDialogo(anularProducto);
								this.repintarFactura();		
								anularProducto = null;
							}
							else{
								anularProducto = new AnularProducto(jTable.getSelectedRow());
								MensajesVentanas.centrarVentanaDialogo(anularProducto);
								anularProducto = null;
								this.repintarFactura();	
							}
						} catch (Exception e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							MaquinaDeEstadoExcepcion meEx = new MaquinaDeEstadoExcepcion("Estado de caja Incorrecto");
							MensajesVentanas.mensajeError(meEx.getMensaje());
						}
						if (logger.isDebugEnabled()) {
							logger.debug("keyPressed(KeyEvent) - F2");
						}
					} else {
						MensajesVentanas.mensajeError("No hay detalles en la venta actual");
					}
				}
				CR.inputEscaner.getDocument().addDocumentListener(this);
			}
			
			//Mapeo de F4
			else if(e.getKeyCode() == KeyEvent.VK_F4){
				ejecutandoEvento = true;
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				this.actualizarTipoEntregaDetalles();
				
				try {					
					//Generar pago con cero para cambiar a estado pago
					//Pago pagoAct = new Pago("01",0,"","","","",0,"");
					//CR.meVenta.efectuarPago(pagoAct);
	
					// Finalizar la venta
					if(CR.meVenta.getVenta() == null) {
						MensajesVentanas.mensajeError("No existe Venta activa");
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} else {
						CR.meVenta.ejecucionPromociones(CR.meVenta.getVenta());
						CR.meVenta.consultarMontoTrans(true);
						// Verificamos que en caso de entregas a domicilio exista un cliente activo	
						if(CR.meVenta.getVenta().verificarTieneEntregaDom() && CR.meVenta.getVenta().getCliente().getCodCliente() == null) {
							MensajesVentanas.aviso("Debe asignar un cliente para poder hacer la entrega a domicilio");
							CR.inputEscaner.getDocument().addDocumentListener(this);
						} else if(Sesion.isRequiereCliente() && CR.meVenta.getVenta().getCliente().getCodCliente() == null) {
							MensajesVentanas.aviso("Debe asignar un cliente a la transacción");
							CR.inputEscaner.getDocument().addDocumentListener(this);
						} else {
							try {
								Vector<Object> pagos = ManejoPagosFactory.getInstance().realizarPago(CR.meVenta.consultarMontoTrans(), CR.meVenta.getVenta().getPagos(), CR.meVenta.getVenta().getCliente(), CR.meVenta.getVenta().getNumTransaccion());
								if (((Boolean)pagos.elementAt(2)).booleanValue()) {
									for (int i=0; i<((Vector<Pago>)pagos.elementAt(0)).size(); i++) {
										Pago pagoAct = ((Vector<Pago>)pagos.elementAt(0)).elementAt(i);
										CR.meVenta.efectuarPago(pagoAct);
									}
								//Se procesa la devolución
								CR.meVenta.finalizarDevolucionXCambio();
								//Esperamos que finalice la impresión de la anulación antes de continuar con la venta
								while (CR.meVenta.getTransaccionPorImprimir()!=null || 
										CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
										MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()) {
									MensajesVentanas.aviso("La impresión de la anulación está en curso, \na continuación se imprimirá la nueva factura");
								}
								//Se finaliza la venta
								CR.meVenta.finalizarVenta();
										
								dispose();
								CR.me.repintarMenuPrincipal();
								this.comprobarLimiteEntrega();
							}
						} catch (MontoPagoExcepcion e1) {
							logger.error("mouseClicked(MouseEvent)", e1);
							Pago pagoAct = new Pago("01",0,"","","","",0,"");
							CR.meVenta.efectuarPago(pagoAct);
							// Chequeamos si existe un pago que permita vuelto para devolver la diferencia
							double monto = 0;
							for (int i=0; i<CR.meVenta.getVenta().getPagos().size(); i++) {
								Pago pago = (Pago) CR.meVenta.getVenta().getPagos().elementAt(i);
								if (pago.getFormaPago().isPermiteVuelto())
									monto += pago.getMonto();
								}
								if (monto >= (CR.meVenta.getVenta().obtenerMontoPagado()-CR.meVenta.consultarMontoTrans())) {
									//Finalizar la devolución									
									CR.meVenta.finalizarDevolucionXCambio();
									while (CR.meVenta.getTransaccionPorImprimir()!=null || 
											CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
											MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()) {
										MensajesVentanas.aviso("La impresión de la nota de crédito está en curso, \na continuación se imprimirá la nueva factura");
									}										
									//Se finaliza la venta
									CR.meVenta.finalizarVenta();
									dispose();
									CR.me.repintarMenuPrincipal();
									this.comprobarLimiteEntrega();
								} else {
									CR.inputEscaner.getDocument().addDocumentListener(this);
									throw new ExcepcionCr("Debe eliminar los pagos. No permiten vuelto al Cliente");
								}
								}
							}
						}
					} catch (UsuarioExcepcion e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (MaquinaDeEstadoExcepcion e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (ConexionExcepcion e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (PagoExcepcion e1){
						logger.error("mouseClicked(MouseEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
						this.repintarFactura();
					}
					catch (ExcepcionCr e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
						//this.repintarFactura();
					} catch (PrinterNotConnectedException e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMessage());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					}
				if (logger.isDebugEnabled()) {
					logger.debug("mouseClicked(MouseEvent) - F4");
				}
			}
			
			//Mapeo de F5
			else if(e.getKeyCode() == KeyEvent.VK_F5){
				ejecutandoEvento = true;
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				this.actualizarTipoEntregaDetalles();
				//try {
					//CR.me.consultaProductoCliente();
					ejecutado = true;
					consultas = null;
					consultas = new Consultas();
					MensajesVentanas.centrarVentanaDialogo(consultas);
					String casoConsulta = consultas.getCodigoSeleccionado()[0];
					String codigo = consultas.getCodigoSeleccionado()[1];
					if (casoConsulta!=null) {
						try {
							if (new Integer(casoConsulta).intValue()<8) {
								CR.meVenta.ingresarLineaProducto(codigo, Sesion.capturaTeclado);
								eventoDeIngresoProducto = true;
							} else {
								CR.meVenta.asignarCliente(codigo, false);
							}
						} catch (ExcepcionCr e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							MensajesVentanas.mensajeError(e1.getMensaje());
						} catch (ConexionExcepcion e1) {
							logger.error("keyPressed(KeyEvent)", e1);

							MensajesVentanas.mensajeError(e1.getMensaje());
						}
					}
			/*	} catch (ExcepcionCr e1) {
					logger.error("keyPressed(KeyEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (ConexionExcepcion e1) {
					logger.error("keyPressed(KeyEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
				}*/
				this.repintarFactura();			
				if (logger.isDebugEnabled()) {
					logger.debug("keyPressed(KeyEvent) - F5");
				}
				CR.inputEscaner.getDocument().addDocumentListener(this);
			}
			
			//Mapeo de F6
			else if(e.getKeyCode() == KeyEvent.VK_F6){
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				if (MensajesVentanas.preguntarSiNo("¿Desea colocar la devolución en espera?") == 0)
				{
					try {
						CR.meVenta.colocarDevolucionEnEspera();
						dispose();
						CR.me.repintarMenuPrincipal();
					} catch (BaseDeDatosExcepcion e1) {
						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (ConexionExcepcion e1) {
						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (SQLException e1) {
						e1.printStackTrace();
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (UsuarioExcepcion e1) {
						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (MaquinaDeEstadoExcepcion e1) {
						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (ExcepcionCr e1) {
						e1.printStackTrace();
						CR.inputEscaner.getDocument().addDocumentListener(this);
					}
				}
			}
			
			//Mapeo de F7
			else if(e.getKeyCode() == KeyEvent.VK_F7){
				ejecutandoEvento = true;
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				this.actualizarTipoEntregaDetalles();
				try {
					CR.meVenta.cargarVentaOriginal();
					ventaOriginal = null;
					ventaOriginal = new PantallaVentaDevolucion(original.getCodTienda(),original.getNumCajaFinaliza(),original.getNumTransaccion(),original.getFechaTrans());
					//ventaOriginal.repintarFactura();
					MensajesVentanas.centrarVentanaDialogo(ventaOriginal);
					ventaOriginal = null;
					this.repintarFactura();
				} catch (MaquinaDeEstadoExcepcion e1) {
					e1.printStackTrace();
				} catch (XmlExcepcion e1) {
					e1.printStackTrace();
				} catch (FuncionExcepcion e1) {
					e1.printStackTrace();
				} catch (BaseDeDatosExcepcion e1) {
					e1.printStackTrace();
				} catch (ConexionExcepcion e1) {
					e1.printStackTrace();
				}
				
				this.comprobarLimiteEntrega();

				CR.inputEscaner.getDocument().addDocumentListener(this);
				if (logger.isDebugEnabled()) {
					logger.debug("mouseClicked(MouseEvent) - F7");
				}
			}
			
			//Mapeo de F8
			else if(e.getKeyCode() == KeyEvent.VK_F8){
				ejecutandoEvento = true;
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				this.actualizarTipoEntregaDetalles();
				ejecutado = true;
				utilitarios = null;
				utilitarios = new Utilitarios();
				MensajesVentanas.centrarVentanaDialogo(utilitarios);
				this.repintarFactura();
				comprobarLimiteEntrega();
				if (utilitarios.isEmitidoRepZ()) {
					dispose();
				} else if (utilitarios.isCierreAutorizado()){
					dispose();
				} else {
					CR.inputEscaner.getDocument().addDocumentListener(this);
				}
			}
			
			//Mapeo de F9
			else if(e.getKeyCode() == KeyEvent.VK_F9){
				ejecutandoEvento = true;
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				if (MensajesVentanas.preguntarSiNo(Sesion.MSG_CANCELAR)==0) {
					this.actualizarTipoEntregaDetalles();
					try{
						int tienda = CR.meVenta.getDevolucion().getVentaOriginal().getCodTienda();
						SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy-MM-dd");
						String fechaTransString = fechaTrans.format(CR.meVenta.getDevolucion().getVentaOriginal().getFechaTrans());
						int caja =  CR.meVenta.getDevolucion().getVentaOriginal().getNumCajaFinaliza();
						int transaccion = CR.meVenta.getDevolucion().getVentaOriginal().getNumTransaccion();
						CR.meVenta.anularDevolucionActiva();
						CR.meVenta.anularVentaActiva();
						dispose();
						hilo = new CancelarDevolucion(tienda, fechaTransString, caja, transaccion);
						hilo.start();						
						CR.me.repintarMenuPrincipal();
					} catch(ExcepcionCr f){
						logger.error("mouseClicked(MouseEvent)", f);
						MensajesVentanas.mensajeError(f.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (ConexionExcepcion f1) {
						logger.error("mouseClicked(MouseEvent)", f1);
						MensajesVentanas.mensajeError(f1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					}
				} else {
					CR.inputEscaner.getDocument().addDocumentListener(this);
				}
			}			
			
			//Mapeo de F10
			else if(e.getKeyCode() == KeyEvent.VK_F10){
				ejecutado = true;
				ejecutandoEvento = true;
				e.consume();
				try {
				CR.me.abrirGaveta(true);
				} catch (ConexionExcepcion e1) {
					logger.error("keyPressed(KeyEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (ExcepcionCr e1) {
					logger.error("keyPressed(KeyEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
				}
			}
			
			//Mapeo de F11
/*			else if(e.getKeyCode() == KeyEvent.VK_F11){
				ejecutandoEvento = true;
				ejecutado = true;
				try {
					boolean validarClaveAlSalir = true;
					try {
						validarClaveAlSalir = InitCR.preferenciasCR.getConfigStringForParameter("facturacion","validarClaveAlSalir").trim().toUpperCase().equals("S")?true:false;
					} catch (NoSuchNodeException e1) {
						MensajesVentanas.mensajeError("Falla carga de preferencias para Validación de Clave al Salir");
					} catch (UnidentifiedPreferenceException e1) {
					}
					if(MensajesVentanas.preguntarSiNo("¿Confirma que desea bloquear la caja?")==0) {
						if (validarClaveAlSalir) {
							ValidarPassword vp = new ValidarPassword();
							MensajesVentanas.centrarVentanaDialogo(vp);
							if (vp.isClaveCorrecta()) {
								CR.me.bloquearCaja();
								dispose();
							}
						} else {
							CR.me.bloquearCaja();
							dispose();
						}
					}
				} catch (UsuarioExcepcion e1) {
					logger.error("keyPressed(KeyEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (MaquinaDeEstadoExcepcion e1) {
					logger.error("keyPressed(KeyEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (ConexionExcepcion e1) {
					logger.error("keyPressed(KeyEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (ExcepcionCr e1) {
					logger.error("keyPressed(KeyEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
				}
			}
	
			//Mapeo de F12
			else if(e.getKeyCode() == KeyEvent.VK_F12){
				ejecutandoEvento = true;
				ejecutado = true;
				boolean errorPago = false;
				if (logger.isDebugEnabled()) {
					logger.debug("keyPressed(KeyEvent) - F12");
				}
				try {
					boolean validarClaveAlSalir = true;
					try {
						validarClaveAlSalir = InitCR.preferenciasCR.getConfigStringForParameter("facturacion","validarClaveAlSalir").trim().toUpperCase().equals("S")?true:false;
					} catch (NoSuchNodeException e1) {
						MensajesVentanas.mensajeError("Falla carga de preferencias para Validación de Clave al Salir");
					} catch (UnidentifiedPreferenceException e1) {
					}
					if(MensajesVentanas.preguntarSiNo("Se cerrará la sesión actual.\nCualquier venta activa será anulada.\n¿Confirma que desea cerrar la sesion?")==0) {
						if (validarClaveAlSalir) {
							ValidarPassword vp = new ValidarPassword();
							MensajesVentanas.centrarVentanaDialogo(vp);
							if (vp.isClaveCorrecta()) {
								try {
									CR.meVenta.anularDevolucionActiva();
									CR.meVenta.anularVentaActiva();
									this.repintarFactura();
								} catch (PagoExcepcion e1) {
									errorPago = true;
									MensajesVentanas.aviso("No se puede anular la venta activa");
									logger.error("keyPressed(KeyEvent)", e1);
								} catch (Exception e1) {
									logger.error("keyPressed(KeyEvent)", e1);
								}
								if(MaquinaDeEstado.cerrarSesion()&& !errorPago) {
									dispose();
								} else {
									this.repintarFactura();
								}
							} 
						} else {
							try {
								CR.meVenta.anularDevolucionActiva();
								CR.meVenta.anularVentaActiva();
								this.repintarFactura();
							} catch (PagoExcepcion e1) {
								errorPago = true;
								MensajesVentanas.aviso("No se puede anular la venta activa");
								logger.error("keyPressed(KeyEvent)", e1);
							} catch (Exception e1) {
								logger.error("keyPressed(KeyEvent)", e1);
							}
							if(MaquinaDeEstado.cerrarSesion()&& !errorPago) {
								dispose();
							} else {
								this.repintarFactura();
							}
						}
					}
				}catch(ExcepcionCr ex){
					logger.error("keyPressed(KeyEvent)", ex);

					MensajesVentanas.mensajeError(ex.getMensaje());
				} catch (ConexionExcepcion f) {
					logger.error("keyPressed(KeyEvent)", f);

					MensajesVentanas.mensajeError(f.getMensaje());
				}
			}
*/
	
			else if((e.getKeyCode() == KeyEvent.VK_UP)||(e.getKeyCode() == KeyEvent.VK_DOWN)){
				ejecutandoEvento = true;
				ejecutado = true;
				dejarPasar = true;
				getJTable().requestFocus();
			}
			
			else if((e.getKeyCode() == KeyEvent.VK_TAB)||(e.getKeyCode() == KeyEvent.VK_RIGHT)) {
				if(e.getSource()==getJTable()){
					int valor = getJTable().getColumnModel().getColumnIndex("Entrega")-1;
					getJTable().setColumnSelectionInterval(valor, valor);
				}	
			}
			else if(e.getKeyCode() == KeyEvent.VK_ENTER){
				if (e.getSource() == getJTable()) {
				//int valor = getJTable().getColumnModel().getColumnIndex("Entrega")-1;
				//getJTable().setColumnSelectionInterval(valor, valor);
					JTable t = getJTable();
					int col = t.getColumnModel().getColumnIndex("Entrega");
					if (t.getSelectedColumn() == col) {
						t.editCellAt(t.getSelectedRow(), col);
					} else {
						t.changeSelection(t.getSelectedRow(), col, false, false);
					}
					e.consume();
					ejecutado = true;
				}
			}

			//Mapeo la tecla de +
			else if((e.getKeyCode() == KeyEvent.VK_PLUS)||(e.getKeyCode() == 107)) {
				ejecutandoEvento = true;
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				this.actualizarTipoEntregaDetalles();
				try {
					if(CR.meVenta.getVenta() == null) {
						MensajesVentanas.mensajeError("No existe Venta activa");
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} else {
						CR.meVenta.consultarMontoTrans(true);
						//Verificamos que en caso de entregas a domicilio exista un cliente activo		  
						if(CR.meVenta.getVenta().verificarTieneEntregaDom() && CR.meVenta.getVenta().getCliente().getCodCliente() == null) {
							MensajesVentanas.aviso("Debe asignar un cliente para poder hacer la entrega a domicilio");
							CR.inputEscaner.getDocument().addDocumentListener(this);
						} else {
							try {
								Vector<Object> pagoRealizado = ManejoPagosFactory.getInstance().realizarNuevoPago(CR.meVenta.consultarMontoTrans(),CR.meVenta.getVenta().getPagos(), CR.meVenta.getVenta().getCliente());
								for (int i=0; i<((Vector<Pago>)pagoRealizado.elementAt(0)).size(); i++)
									CR.meVenta.efectuarPago(((Vector<Pago>)pagoRealizado.elementAt(0)).elementAt(i));
								if (((Double)pagoRealizado.elementAt(1)).doubleValue()>= -0.001) {
									jLabel9.setText("Saldo a devolver: " + df.format(((Double)pagoRealizado.elementAt(1)).doubleValue()));
									//Finalizar la devolución
									CR.meVenta.finalizarDevolucion();
									//Esperamos que finalice la impresión de la devolución
									while (CR.meVenta.getTransaccionPorImprimir() != null || 
											CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
											MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()) {
										MensajesVentanas.aviso("La impresión de la nota de crédito está en curso, \na continuación se imprimirá la nueva factura");
									}
									//Se finaliza la venta
									CR.meVenta.finalizarVenta();
									dispose();
									CR.me.repintarMenuPrincipal();
									this.comprobarLimiteEntrega();
								} else {
									jLabel9.setText("Saldo a cancelar: " + df.format(-((Double)pagoRealizado.elementAt(1)).doubleValue()));
									//CR.meVenta.volverAFacturacion();
								}
							} catch (MontoPagoExcepcion e1) {
								logger.error("mouseClicked(MouseEvent)", e1);

								e1.setMensaje("Pagos completados. Sólo se puede totalizar la transacción");
								CR.inputEscaner.getDocument().addDocumentListener(this);
								throw e1;
							}
						}
					}
				} catch (ExcepcionCr e1) {
					logger.error("mouseClicked(MouseEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
					CR.inputEscaner.getDocument().addDocumentListener(this);
				} catch (ConexionExcepcion e1) {
					logger.error("mouseClicked(MouseEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
					CR.inputEscaner.getDocument().addDocumentListener(this);
				} catch (PrinterNotConnectedException f1) {
					logger.error("mouseClicked(MouseEvent)", f1);

					MensajesVentanas.mensajeError(f1.getMessage());
					CR.inputEscaner.getDocument().addDocumentListener(this);
				}
				this.repintarFactura();
			}
			
			//Mapeo la tecla de -
			else if((e.getKeyCode() == KeyEvent.VK_MINUS)||(e.getKeyCode() == 109)) {
				ejecutandoEvento = true;
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				this.actualizarTipoEntregaDetalles();
				try {
					ManejoPagosFactory.getInstance().recalcularPagoDevolucion(CR.meVenta.getDevolucion().getMontoBase() + CR.meVenta.getDevolucion().getMontoImpuesto(),0,CR.meVenta.getVenta().getPagos());
						Vector<Object> pagoRealizado = ManejoPagosFactory.getInstance().eliminarPago(CR.meVenta.consultarMontoTrans(),CR.meVenta.getVenta().getPagos(), CR.meVenta.getVenta().getCliente());
						//CR.meVenta.getVenta().getPagos().addElement(ManejoPagos.realizarPagoDevolucion(CR.meVenta.getDevolucion().getMontoBase() + CR.meVenta.getDevolucion().getMontoImpuesto()));
						if (((Double)pagoRealizado.elementAt(1)).doubleValue()>=0) {
							jLabel9.setText("Saldo a devolver: " + df.format(((Double)pagoRealizado.elementAt(1)).doubleValue()));
							//Finaliza la devolución
							CR.meVenta.finalizarDevolucion();							
							//Esperamos que fnalice la impresión de la devolución
							while (CR.meVenta.getTransaccionPorImprimir() != null || 
									CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
									MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()) {
								MensajesVentanas.aviso("La impresión de la nota de crédito está en curso, \na continuación se imprimirá la nueva factura");
							}							
							CR.meVenta.finalizarVenta();
							dispose();
							CR.me.repintarMenuPrincipal();
						} else {
							jLabel9.setText("Saldo a cancelar: " + df.format(-((Double)pagoRealizado.elementAt(1)).doubleValue()));
						} 
				//	}else
				//		MensajesVentanas.aviso("No se puede eliminar pago actual");
				} catch (ExcepcionCr e1) {
					logger.error("mouseClicked(MouseEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
					CR.inputEscaner.getDocument().addDocumentListener(this);
				} catch (ConexionExcepcion e1) {
					logger.error("mouseClicked(MouseEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
					CR.inputEscaner.getDocument().addDocumentListener(this);
				} catch (PrinterNotConnectedException f1) {
					logger.error("mouseClicked(MouseEvent)", f1);

					MensajesVentanas.mensajeError(f1.getMessage());
					CR.inputEscaner.getDocument().addDocumentListener(this);
				}
				this.repintarFactura();
			}
			
			/*
			 * Mapea el shortcut A, para asignar clientes a la facturacion
			 * @author: Victor Medina - linux@epa.com.ve
			 */
			else if(e.getKeyCode() == KeyEvent.VK_A) {
				ejecutado = true;
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				this.actualizarTipoEntregaDetalles();
				//Verificamos que se presionó un número
				if (Character.isLetterOrDigit(e.getKeyChar())) {
					// Mostramos una ventana para que el usuario introduzca el código del producto
	/************************************************/				
					if (jButton.getText().equals("Asignar Clientes")){
//						llamada a la extension de asigna/registrar cliente en CR  02/2009 Wdiaz
						RegistroCliente registro = factory.getInstance();
						registro.MostrarPantallaCliente(false,10);
					} else {
						int respuesta = MensajesVentanas.preguntarSiNo("¿Desea quitar el cliente asignado?");
						if (respuesta==0){ //Si desea eliminarlo
							try {
								CR.meVenta.quitarClienteDevolucion();
							} catch (XmlExcepcion e1) {
								e1.printStackTrace();
							} catch (FuncionExcepcion e1) {
								e1.printStackTrace();
							} catch (BaseDeDatosExcepcion e1) {
								e1.printStackTrace();
							} catch (ConexionExcepcion e1) {
								e1.printStackTrace();
							}	
						}
					}
					eventoDeIngresoProducto = true;
					this.repintarFactura();
	/************************************************/
					CR.inputEscaner.getDocument().addDocumentListener(this);

				}
			}	

			/*
			 * Mapea el shortcut O, para asignar contribuyentes ordinarios
			 * @author: Victor Medina - linux@epa.com.ve
			 */
		/*	else if(e.getKeyCode() == KeyEvent.VK_O) {
				ejecutado = true;
				this.actualizarTipoEntregaDetalles();
				try {
					if(InitCR.preferenciasCR.getConfigStringForParameter("facturacion","permitirContribuyentesOrdinarios").equals("S")){
						if(CR.meVenta.getVenta() != null){
							int respuesta = 0;
							if(CR.meVenta.getContribuyenteOrdinario()== false){
								respuesta = MensajesVentanas.preguntarSiNo("Esta seguro que desea marcar al cliente\nactual como contribuyente ordinario?");				
								if(respuesta == 0){
									CR.meVenta.setContribuyenteOrdinario(true);
								}
							}
							else{
								respuesta = MensajesVentanas.preguntarSiNo("El cliente ya esta marcado como \ncontribuyente ordinario \ndesea deshacer la accion?");				
								if(respuesta == 0){
									CR.meVenta.setContribuyenteOrdinario(false);
								}
							}					
						}
						else{
							MensajesVentanas.mensajeError("Primero debe asignar un cliente a la venta \nantes de poder marcarlo como contribuyente ordinario");
						}						
					}
					else{
						MensajesVentanas.mensajeError("La configuracion actual no permite\nmarcar clientes como contribuyentes ordinarios");
					}
				} catch (NoSuchNodeException e1) {
					logger.error("keyPressed(KeyEvent)", e1);
				} catch (UnidentifiedPreferenceException e1) {
					logger.error("keyPressed(KeyEvent)", e1);
				}				
				this.repintarFactura();
				if(CR.meVenta.getContribuyenteOrdinario() == true){
					PanelMensajesCR.borrarAvisos();
					PanelMensajesCR.mostrarAviso("El cliente ha sido marcado como contribuyente ordinario",true);
				}
				else{
					PanelMensajesCR.borrarAvisos();
					PanelMensajesCR.mostrarAviso("El cliente no es un contribuyente ordinario",true);
				}
			}
*/

			if (!ejecutado) {
				ejecutandoEvento = true;
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				this.actualizarTipoEntregaDetalles();
				//Verificamos que se presionó un número
				if (Character.isDigit(e.getKeyChar())) {
					// Mostramos una ventana para que el usuario introduzca el código del producto
					ec =null;
					ec = new EjecutarConCantidad("Ingreso de Código:", String.valueOf(e.getKeyChar()),3);
					MensajesVentanas.centrarVentanaDialogo(ec);
					eventoDeIngresoProducto = true;
					this.repintarFactura();
				}  
				if (Character.isLetter(e.getKeyChar())  &&  Character.toLowerCase(e.getKeyChar()) != 'c' && 
						Character.toLowerCase(e.getKeyChar()) != 'd') {
					e.consume();
				}
				CR.inputEscaner.getDocument().addDocumentListener(this);
			} else {
				if (!dejarPasar)
					e.consume();
			}
			ejecutandoEvento = false;
			escanerActivo = true;
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
	
	/**
	 * Método repintarFactura.
	 */
	private void repintarFactura(){
		if (logger.isDebugEnabled()) {
			logger.debug("repintarFactura() - start");
		}

		try{
			//Inicializamos el modelo de tabla para que cargue el ComboBox repectivo
			TableColumnModel tcm = jTable.getColumnModel();
			TableColumn tc = tcm.getColumn(7);
			tc.setCellRenderer(new EntregaRenderer());
			tc.setCellEditor(new EntregaEditor(this));
			MaquinaDeEstado.setEstadoCaja(Sesion.getCaja().getEstado());
			modeloTablaDetalleFacturacion.llenarTabla();
			modeloTablaDetalleDevolucion.llenarTablaDevolucion();
			String nombre, rif;
			if (CR.meVenta.getVenta() == null) {
				CR.me.setCliente("","");
				CR.me.setExento(false);
				jButton.setText("Asignar Clientes");
				this.getJButton4().setEnabled(true);
				getJTextField4().setText(df.format(0));
				getJTextField5().setText(df.format(0));
				getJTextField6().setText(df.format(0));
				getJLabel9().setText("Saldo a cancelar: " + df.format(0));
				getJLabel10().setText("No.Artículos: " + df.format(0));
				modeloTablaDetallePagos.iniciarDatosTabla();
			} else {
				if (CR.meVenta.getVenta().getCliente() == null || 
					CR.meVenta.getVenta().getCliente().getCodCliente()== null) {
						nombre = "";
						rif = "";
						jButton.setText("Asignar Clientes");
						this.getJButton4().setEnabled(true);
					} else{
						nombre = new String(CR.meVenta.getVenta().getCliente().getNombreCompleto());
						rif = new String(CR.meVenta.getVenta().getCliente().getCodCliente());
						jButton.setText("Quitar Cliente Asignado");
						if(CR.meVenta.getVenta().isVentaExenta()) {
							CR.me.setExento(true);
							CR.me.mostrarAviso("Cliente Exento de Impuestos", true);
						} else {
							CR.me.setExento(false);
						}
						
						//Verificamos si el cliente es un COLABORADOR para chequear si se permiten hacer rebajas para este tipo de ventas (por preferencias)
						//Si no se permite se deshabilita la tecla asociada a esta funcionalidad
						if(((CR.meVenta.getVenta().getCliente().getTipoCliente() == Sesion.COLABORADOR)&&(CR.meVenta.getVenta().getCliente().getEstadoColaborador() == Sesion.ACTIVO)) && !Sesion.permitirRebajasAempleados) {
							this.getJButton4().setEnabled(false);
						} else {
							this.getJButton4().setEnabled(true);
					}
				}
				
				CR.me.setCliente(nombre, rif);
				getJTextField4().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meVenta.getVenta().getMontoBase())));
				getJTextField5().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meVenta.getVenta().getMontoImpuesto())));
				getJTextField6().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meVenta.getVenta().getMontoBase() + CR.meVenta.getVenta().getMontoImpuesto())));
				modeloTablaDetallePagos.llenarTablaPagos(CR.meVenta.getVenta().getPagos());
				if (CR.meVenta.getVenta().consultarMontoTrans() - CR.meVenta.getVenta().obtenerMontoPagado()>=0)
					jLabel9.setText("Saldo a cancelar: " + df.format(CR.meVenta.getVenta().consultarMontoTrans() - CR.meVenta.getVenta().obtenerMontoPagado()));
				else
					jLabel9.setText("Saldo a devolver: " + df.format(CR.meVenta.getVenta().obtenerMontoPagado()-CR.meVenta.getVenta().consultarMontoTrans()));
				jLabel10.setText("No.Artículos: " + df.format(CR.meVenta.obtenerCantidadProds()));
				
				// Si se está realizando un ingreso de producto
				if (eventoDeIngresoProducto) {
					// Chequeamos si debemos colocar el mensaje de posible empaque en el producto
					if (CR.meVenta.getVenta()!=null && CR.meVenta.getVenta().getDetallesTransaccion().size() > 0) {
						DetalleTransaccion detAct = ((DetalleTransaccion)CR.meVenta.getVenta().getDetallesTransaccion().lastElement());
						float limite = detAct.getProducto().getCantidadVentaEmpaque() * ((float)Sesion.getPorcentajeMensajeEmpaque()/100);
						/*if ((detAct.getCondicionVenta().equalsIgnoreCase(Sesion.condicionNormal)||detAct.getCondicionVenta().equalsIgnoreCase(Sesion.condicionPromocion))
							&&(detAct.getProducto().getDesctoVentaEmpaque()>0)&&(detAct.getCantidad()>=limite)) {*/
						if (!detAct.isCondicionEspecial()&&(detAct.getCantidad()<detAct.getProducto().getCantidadVentaEmpaque()&&detAct.getProducto().getDesctoVentaEmpaque()>0)&&(detAct.getCantidad()>=limite)) {
							CR.me.mostrarAviso(df.format((detAct.getProducto().getCantidadVentaEmpaque()-detAct.getCantidad())) + " "+ detAct.getProducto().getAbreviadoUnidadVenta() + "  más aplicarán " + detAct.getProducto().getDesctoVentaEmpaque() + "% de descuento por empaque", true);
							CR.beep();
						}
					}
				}
			}

		} catch (Exception ex){
			logger.error("repintarFactura()", ex);
			CR.me.setCliente("","");
			CR.me.setExento(false);
			this.getJButton4().setEnabled(true);
		/*	getJTextField4().setText(df.format(0));
			getJTextField5().setText(df.format(0));
			getJTextField6().setText(df.format(0));
			getJLabel9().setText("Resto: " + df.format(0));
			getJLabel10().setText("No.Artículos: " + df.format(0));
			modeloTablaDetallePagos.iniciarDatosTabla();*/
		}

		if (logger.isDebugEnabled()) {
			logger.debug("repintarFactura() - end");
		}
	}
	
	/**
	 * Método repintarFactura.
	 */
	private void actualizarTipoEntregaDetalles(){
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarTipoEntregaDetalles() - start");
		}

		String tipoEntrega = "";

		//Aquí llenamos un vector con los indices seleccionados de los comboBox 
		//que indican el tipo de entrega de los detalles
		try {
			if (CR.meVenta.getVenta() != null)
				for(int i=0; i<CR.meVenta.getVenta().getDetallesTransaccion().size(); i++) {
					tipoEntrega = (String)((Object[][])modeloTablaDetalleFacturacion.getDatos())[i][column];
					CR.meVenta.getVenta().actualizarTipoEntrega(i,tipoEntrega);
				}
		} catch(Exception e) {
			logger.error("actualizarTipoEntregaDetalles()", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarTipoEntregaDetalles() - end");
		}
	}
	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 */
	public void changedUpdate(DocumentEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("changedUpdate(DocumentEvent) - start");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("changedUpdate(DocumentEvent) - end");
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 */
	public void insertUpdate(DocumentEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("insertUpdate(DocumentEvent) - start");
		}

		if (escanerActivo) {
			eventoDeIngresoProducto = false;
			if (lector == null) {
				lector = new EfectuarLecturaEscaner(this);
			} else
				lector.iniciar();
				
			//Código agregado porque se colgaba la caja al leer muchos productos 
			//teniendo un combo de "Entrega" seleccionado
	//		JTable t = getJTable();
			//ChangeEvent changeEvent = new ChangeEvent(t);
	//		int col = t.getColumnModel().getColumnIndex("Entrega");
	//		if (t.isEditing()) {
	//			t.editCellAt(t.getSelectedRow(), col);
	//		} 
		}

		if (logger.isDebugEnabled()) {
			logger.debug("insertUpdate(DocumentEvent) - end");
		}
	}
	//WDIAZ:02-07-2013 Controlar N Eventos con el escaner (Pantallas) simultaneamente
	public void leerEscaner(final String codigoScaner) {
		if (logger.isDebugEnabled()) {
			logger.debug("leerEscaner(String) - start");
		}

		try {
			String pref = InitCR.preferenciasCR.getConfigStringForParameter("sistema", "escanerRapido");
			if (pref != null) {
				if (pref.equals("N"))
					escanerActivo = false;
			}  else 
				escanerActivo = false;
		} catch (NoSuchNodeException e) {
			logger.error("leerEscaner(String)", e);

			escanerActivo = false;
		} catch (UnidentifiedPreferenceException e) {
			logger.error("leerEscaner(String)", e);
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
				}
			});
		} catch (InterruptedException e2) {
			logger.error("leerEscaner(String)", e2);
		} catch (InvocationTargetException e2) {
			logger.error("leerEscaner(String)", e2);
		}
		
		
		escanerActivo = true;

		if (logger.isDebugEnabled()) {
			logger.debug("leerEscaner(String) - end");
		}
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Se parametrizó el tipo de dato de los distintos ArrayList y se comentó variable sin uso
	* Fecha: agosto 2011
	*/
	public void lecturaEscaner(String codigoScaner) {
		if (logger.isDebugEnabled()) {
			logger.debug("lecturaEscaner(String) - start");
		}
		if (isPermitirEscaner()) {
			try {
				setPermitirEscaner(false); //WDIAZ:02-07-2013 Controlar N Pantallas simultaneamente
				ArrayList <Object>valorLeido = Control.codigoValido(codigoScaner, Sesion.capturaEscaner);
				int tipoCodigo = ((Integer)valorLeido.get(0)).intValue();
				String codigo = (String)valorLeido.get(1);
				//String tipoCaptura = (String)valorLeido.get(2);
				switch (tipoCodigo){
					case Control.PRODUCTO:
						CR.meVenta.ingresarLineaProducto(codigo, Sesion.capturaEscaner);
						break;
					case Control.CLIENTE:
						CR.meVenta.asignarCliente(codigo, false);
						break;
					case Control.COLABORADOR:
						CR.meVenta.asignarCliente(codigo, false);
						break;
					case Control.CODIGO_DESCONOCIDO:
						try{
							CR.meVenta.ingresarLineaProducto(codigoScaner, Sesion.capturaEscaner);
						}catch(Exception ex1){
							logger.error("lecturaEscaner(String)" + ex1.getMessage());
	
							CR.meVenta.ingresarLineaProducto(codigoScaner.substring(0,codigoScaner.length()-1),Sesion.capturaEscaner);
						}
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
			} catch (ProductoExcepcion e1) {
				logger.error("lecturaEscaner(String)" + e1.getMensaje());
	
				MensajesVentanas.mensajeError("Código no registrado");
			} catch (ExcepcionCr e1) {
				logger.error("lecturaEscaner(String)", e1);
	
				MensajesVentanas.mensajeError("Código no registrado");
			} catch (ConexionExcepcion e1) {
				logger.error("lecturaEscaner(String)", e1);
	
				MensajesVentanas.mensajeError(e1.getMensaje());
			}finally{
				setPermitirEscaner(true); //WDIAZ:02-07-2013 Controlar N Pantallas simultaneamente
			}
			repintarFactura();
		} else {
			CR.beep();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("lecturaEscaner(String) - end");
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
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
	 * Verfica si el monto recaudado en la caja sobrapasa el límete establecido en la tienda para hacer una entrega de caja.
	 * De ser así muestra en la interfaz los avisos necesarios para el cajero
	 * 
	 * @return void
	 */
	private void comprobarLimiteEntrega() {
		if (logger.isDebugEnabled()) {
			logger.debug("comprobarLimiteEntrega() - start");
		}

		if(Sesion.getCaja().getMontoRecaudado() > Sesion.getTienda().getLimiteEntregaCaja()) {
			CR.me.mostrarAviso("Debe realizar entrega a caja principal", true);
			// Enviar un bip a la caja
			CR.beep();
			// Mostrar mensaje de entrega parcial
			MensajesVentanas.aviso("Debe realizar entrega a caja principal");
			CR.me.setEntregaParcial(true);
		} else {
			CR.me.setEntregaParcial(false);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("comprobarLimiteEntrega() - end");
		}
	}
	
	class TablaFact extends JTable {
		/**
		 * Logger for this class
		 */

		/**
		 * Logger for this class
		 */

		TablaFact(TableModel dm) {
			super(dm);
		}
		
		public void tableChanged(TableModelEvent e){
			if (logger.isDebugEnabled()) {
				logger.debug("tableChanged(TableModelEvent) - start");
			}

			super.tableChanged(e);
			if ((e.getColumn() == PantallaDevXCambio.column) && (e.getType() == TableModelEvent.UPDATE)) {
				actualizarTipoEntregaDetalles();
			}
			if (getRowCount() > 0) {
				changeSelection(getRowCount() - 1, PantallaDevXCambio.column, false, false);
			}
			if (!inicializando) {
				if (jScrollPane != null && jScrollPane.getViewport() != null) {
					if (getRowCount() > 11) {
						Rectangle r = getCellRect(getRowCount() - 11, 0, true);
						jScrollPane.getViewport().setViewPosition(new Point(r.x, r.y));
					}
				}
				if (jPanel14 != null)
					jPanel14.repaint();
			}

			if (logger.isDebugEnabled()) {
				logger.debug("tableChanged(TableModelEvent) - end");
			}
		}
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton() {
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
	 * @return Devuelve permitirEscaner.
	 */
	public boolean isPermitirEscaner() {
		return permitirEscaner;
	}
	/**
	 * @param permitirEscaner El permitirEscaner a establecer.
	 */
	public void setPermitirEscaner(boolean permitirEscaner) {
		this.permitirEscaner = permitirEscaner;
	}
 }
