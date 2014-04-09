/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui.bonoregalo
 * Programa   : VentaBonoRegalo.java
 * Creado por : Jesús Graterol	<jgraterol@beco.com.ve>
 * Creado en  : Sep 29, 2010 
 *
 * (c) CENTROBECO, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     :
 * Fecha       : 
 * Analista    : 
 * Descripción : 
 * =============================================================================
 */
package com.becoblohm.cr.gui.bonoregalo;


import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.BonoRegaloException;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.MontoPagoExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.extensiones.ManejoPagosFactory;
import com.becoblohm.cr.extensiones.RegistroCliente;
import com.becoblohm.cr.extensiones.RegistroClienteFactory;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.gui.EjecutarConCantidad;
import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.gui.ModeloTablaPagoInterfaz;
import com.becoblohm.cr.gui.PanelIconosCR;
import com.becoblohm.cr.gui.PanelMensajesCR;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstado;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.crprinterdriver.PrinterNotConnectedException;
/**
 * @author vmedina
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class VentaBonoRegalo extends JDialog implements ComponentListener, KeyListener, ActionListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(VentaBonoRegalo.class);  //  @jve:decl-index=0:

	private ModeloTablaBR modeloTablaDetalleBR = new ModeloTablaBR();
	private ModeloTablaPagoInterfaz modeloTablaDetallePagos = new ModeloTablaPagoInterfaz();
	private DecimalFormat df = new DecimalFormat("#,##0.00");
	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel14 = null;
	private javax.swing.JScrollPane jScrollPane = null;
	private javax.swing.JTable jTable = null;
	private javax.swing.JPanel jPanel15 = null;
	private javax.swing.JPanel jPanel16 = null;
	private javax.swing.JScrollPane jScrollPane1 = null;
	private javax.swing.JTable jTable1 = null;
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
	
	private static int column = 7;
	private static boolean inicializando = true;
	
	private boolean ejecutandoEvento = false;
		
	private JButton jButton = null;
	
	RegistroClienteFactory factory = new RegistroClienteFactory();  //  @jve:decl-index=0:
	
	private String digito = null;

	/**
	 * This is the default constructor
	 */
	public VentaBonoRegalo() {
		super(MensajesVentanas.ventanaActiva);
		inicializando = true;
		initialize();
		this.ejecutandoEvento = false;

		jScrollPane.addKeyListener(this);
		
		jTable.addKeyListener(this);

		jScrollPane1.addKeyListener(this);
		
		jTable1.addKeyListener(this);
		
		jButton1.addKeyListener(this);
		jButton1.addActionListener(this);
		
		jButton2.addKeyListener(this);
		jButton2.addActionListener(this);
		
		jButton3.addKeyListener(this);
		jButton3.addActionListener(this);
		
		jButton4.addKeyListener(this);
		jButton4.addActionListener(this);
		
		jButton5.addKeyListener(this);
		jButton5.addActionListener(this);
		
		jButton6.addKeyListener(this);
		jButton6.addActionListener(this);
		
		jButton7.addActionListener(this);
		jButton7.addKeyListener(this);
		
		jButton8.addKeyListener(this);
		jButton8.addActionListener(this);

		jButton9.addKeyListener(this);
		jButton9.addActionListener(this);
		
		jButton10.addKeyListener(this);
		jButton10.addActionListener(this);
		
		jButton11.addKeyListener(this);
		jButton11.addActionListener(this);
		
		jButton12.addKeyListener(this);
		jButton12.addActionListener(this);
	
		jButton13.addKeyListener(this);
		jButton13.addActionListener(this);
		
		jButton14.addKeyListener(this);
		jButton14.addActionListener(this);
		
		jButton.addKeyListener(this);
		jButton.addActionListener(this);

		this.repintarFactura();		
		//Comprobamos el límite de entrega a caja porque cuando regresamos de una pantalla de servicio
		//se requiere chequear si se modificó el monto recaudado (ejemplo cuando se realizo un abono a un apartado)				
		this.comprobarLimiteEntrega();

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
		this.setName("FrameVentaBonosRegalo");
		this.setVisible(false);
		this.setTitle("CR ver. " + CR.version + " - Facturaci\u00f3n de Bonos Regalo");
		this.setResizable(false);
		this.setLocale(new java.util.Locale("es", "VE", ""));
		this.setModal(true);
		this.setBackground(new java.awt.Color(226,226,222));
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setUndecorated(true);
		
		jTable.getColumnModel().getColumn(0).setMaxWidth(60);
		
		
		
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
			jPanel14.setPreferredSize(new java.awt.Dimension(656,250));
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
			jTable = new TablaFact(modeloTablaDetalleBR);
			jTable.getTableHeader().setReorderingAllowed(false) ;
			jTable.getColumnModel().getColumn(0).setPreferredWidth(278);
			jTable.getColumnModel().getColumn(1).setPreferredWidth(278);
			
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
			jScrollPane.setPreferredSize(new java.awt.Dimension(645,230));
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
			if(CR.meServ.getVentaBR()!=null)
				jButton1.setEnabled(!CR.meServ.getVentaBR().isTarjetasRecargadas());
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
			try {	
				if (CR.meServ.getVentaBR().getPagos().size()==0) jButton2.setEnabled(false);
				else jButton2.setEnabled(true);
			}catch(Exception e){jButton2.setEnabled(false);}
			jButton2.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/quitar.png")));
			if(CR.meServ.getVentaBR()!=null)
				jButton2.setEnabled(!CR.meServ.getVentaBR().isTarjetasRecargadas());
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
			jLabel9.setText("RESTO: " + df.format(0));
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
			jButton3.setText("F1 Sumar a tarjeta");
			jButton3.setPreferredSize(new java.awt.Dimension(120,40));
			jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton3.setDefaultCapable(false);
			jButton3.setMargin(new Insets(1,2,1,1));
			jButton3.setBackground(new java.awt.Color(226,226,222));
			if(CR.meServ.getVentaBR()!=null)
				jButton3.setEnabled(!CR.meServ.getVentaBR().isTarjetasRecargadas());
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
			jButton4.setText("F3 Venta Corporativa");
			jButton4.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton4.setDefaultCapable(false);
			jButton4.setMargin(new Insets(1,2,1,1));
			jButton4.setBackground(new java.awt.Color(226,226,222));
			if(CR.meServ.getVentaBR()!=null)
				jButton4.setEnabled(!CR.meServ.getVentaBR().isTarjetasRecargadas());
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
				jButton5.setText("F2 Restar a tarjeta");
				jButton5.setMargin(new Insets(1,2,1,1));
				jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
				jButton5.setDefaultCapable(false);
				jButton5.setBackground(new java.awt.Color(226,226,222));
				if(CR.meServ.getVentaBR()!=null)
					jButton5.setEnabled(!CR.meServ.getVentaBR().isTarjetasRecargadas());
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
			jButton6.setEnabled(CR.meServ.getVentaBR().isTarjetasRecargadas());
			if(CR.meServ.getVentaBR()!=null)
				jButton6.setEnabled(!CR.meServ.getVentaBR().isTarjetasRecargadas());
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
			jButton7.setText("F5 Nuevo Detalle");
			jButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton7.setDefaultCapable(false);
			jButton7.setMargin(new Insets(1,2,1,1));
			jButton7.setBackground(new java.awt.Color(226,226,222));
			if(CR.meServ.getVentaBR()!=null)
				jButton7.setEnabled(!CR.meServ.getVentaBR().isTarjetasRecargadas());
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
			jButton9.setEnabled(false);
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
			if(CR.meServ.getVentaBR()!=null)
				jButton8.setEnabled(CR.meServ.getVentaBR().isTarjetasRecargadas());
			
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
			jButton12.setEnabled(false);
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
		
		eliminarListeners();
		
		if (!ejecutandoEvento) {
				
			//Mapeo de F3 en eventos de Mouse
			if(e.getSource().equals(jButton4)){
				ejecutarAccion(10);
			}
			
			//Mapeo de F4 en eventos de Mouse
			if(e.getSource().equals(jButton6)){
				ejecutarAccion(1);
			}
			
			//Mapeo de F8 en Eventos de Mouse
			else if(e.getSource().equals(jButton8)){
				ejecutarAccion(4);
			}
	
			//Mapeo de F9 en Eventos de Mouse
			else if(e.getSource().equals(jButton11)){
				ejecutarAccion(3);
			}
		
			//Mapeo de F1 en Eventos de Mouse
			else if(e.getSource().equals(jButton3)){
				ejecutarAccion(8);
			}
			
			//Mapeo de F5 en eventos de Mouse
			if(e.getSource().equals(jButton7)){
				ejecutarAccion(9);
			}
			
			//Mapeo de F10 en eventos de Mouse
			/*else if(e.getSource().equals(jButton12)){
				ejecutarAccion(4);
			}*/
	
			//Mapeo la tecla de +
			else if(e.getSource().equals(getJButton1())) {
				ejecutarAccion(5);
			}

			//Mapeo la tecla de -
			else if(e.getSource().equals(getJButton2())&& jButton2.isEnabled()) {
				ejecutarAccion(6);
			}
			
			/*
			 * Mapeamos la funcion Asignar clientes al click del mouse 
			 * @author Victor Medina - linux@epa.com.ve
			 */
			else if(e.getSource().equals(jButton)) {		
				ejecutarAccion(7);									
			} 
			
			else if(e.getSource().equals(jButton5)){
				ejecutarAccion(2);
			}
			
			ejecutandoEvento = false;
			getJTable().requestFocus();
		}

		agregarListeners();
		if (logger.isDebugEnabled()) {
			logger.debug("actionPerformed(ActionEvent) - end");
		}
	}
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unchecked")
	public void ejecutarAccion(int opcion) {
		boolean cancelar = true;
		switch(opcion){
		case 1:
			ejecutandoEvento = true;
			try {				
				if(CR.meServ.getVentaBR() == null) {
					MensajesVentanas.mensajeError("No existe Venta activa");
				} else {
					
					double totalAPagar = CR.meServ.consultarMontoTransBR(true);
					
					if(CR.meServ.getVentaBR().getCliente()==null || CR.meServ.getVentaBR().getCliente().getCodCliente() == null) {
						MensajesVentanas.aviso("Debe asignar un cliente a la transacción");
					} else {
						try {
							Vector<Object> pagos = ManejoPagosFactory.getInstance().realizarPago(totalAPagar, CR.meServ.getVentaBR().getPagos(), CR.meServ.getVentaBR().getCliente(), CR.meServ.getVentaBR().getNumTransaccion());
							if (((Boolean)pagos.elementAt(2)).booleanValue()) {
								for (int i=0; i<((Vector<Pago>)pagos.elementAt(0)).size(); i++) {
									Pago pagoAct = ((Vector<Pago>)pagos.elementAt(0)).elementAt(i);
									CR.meServ.efectuarPago(pagoAct);
								}
								double monto = CR.meServ.getVentaBR().consultarMontoTrans();
								
								if (monto - CR.meServ.getVentaBR().obtenerMontoPagado() < 0.01) {
									//Desbloquear impresora
									CR.meServ.desbloquearTransaccionBR();
									//Se finaliza la venta
									CR.meServ.finalizarVentaBR(true, true, true);
	
									CR.me.repintarIconos();
									this.comprobarLimiteEntrega();
									this.dispose();
									CR.me.repintarMenuPrincipal();
								}
							}
							this.repintarFactura();
							
						} catch (MontoPagoExcepcion e1) {
							logger.error("ejecutarAccion(int)", e1);

							// Chequeamos si existe un pago que permita vuelto para devolver la diferencia
							double monto = 0;
							for (int i=0; i<CR.meServ.getVentaBR().getPagos().size(); i++) {
								Pago pago = (Pago) CR.meServ.getVentaBR().getPagos().elementAt(i);
								if (pago.getFormaPago().isPermiteVuelto())
									monto += pago.getMonto();
							}
							if (monto >= (CR.meServ.getVentaBR().obtenerMontoPagado()-totalAPagar)) {
								//Se finaliza la venta
								CR.meServ.finalizarVentaBR(true, true, true);
	
								this.repintarFactura();
								CR.me.repintarIconos();
								this.comprobarLimiteEntrega();
							} else
								throw new ExcepcionCr("Debe eliminar los pagos. No permiten vuelto al Cliente");
						}
					}
				}
			} catch(ExcepcionCr f) {
				logger.error("ejecutarAccion(int)", f);

				MensajesVentanas.mensajeError(f.getMensaje());
				repintarFactura();
			} catch (ConexionExcepcion f1) {
				logger.error("ejecutarAccion(int)", f1);

				MensajesVentanas.mensajeError(f1.getMensaje());
				repintarFactura();
			} catch (PrinterNotConnectedException f1) {
				logger.error("ejecutarAccion(int)", f1);

				MensajesVentanas.mensajeError(f1.getMessage());
				repintarFactura();
			}
			if (logger.isDebugEnabled()) {
				logger.debug("ejecutarAccion(int) - F4");
			}
			break;
		case 2: //restar
			if(jTable.getSelectedRow()<0){
				MensajesVentanas.mensajeError("Debe seleccionar un detalle para su modificación");
				break;
			}
			AgregarTarjeta agregarTarjetaResta = new AgregarTarjeta(false, true, jTable.getSelectedRow());
			MensajesVentanas.centrarVentanaDialogo(agregarTarjetaResta);
			this.repintarFactura();
			break;
		case 4:
			//Abrir ventana de utilitarios
			UtilitariosBonoRegalo utilitariosBR =  new UtilitariosBonoRegalo();
			MensajesVentanas.centrarVentanaDialogo(utilitariosBR);
			/*if(!utilitariosBR.isCancelar())
				dispose();*/
			cancelar = true;
			//OJO: no hay break a proposito
		case 3: 
			ejecutandoEvento = true;
			if (cancelar || MensajesVentanas.preguntarSiNo(Sesion.MSG_CANCELAR)==0) {
				try{
					CR.meServ.desbloquearTransaccionBR();
					CR.meServ.anularVentaBRActiva();
					this.dispose();
					CR.me.repintarMenuPrincipal();
					try {
						finalize();
					} catch (Throwable e1) {
						logger.error("keyPressed(KeyEvent)", e1);
					}
				} catch(ExcepcionCr f){
					logger.error("ejecutarAccion(int)", f);

					MensajesVentanas.mensajeError(f.getMensaje());
				} catch (ConexionExcepcion f1) {
					logger.error("ejecutarAccion(int)", f1);
				} catch (Exception f1) {
					logger.error("ejecutarAccion(int)", f1);
				} 
			}
			break;
		case 5:
			ejecutandoEvento = true;
			try {				
				if(CR.meServ.getVentaBR() == null) {
					MensajesVentanas.mensajeError("No existe Venta activa");
				} else {
					//esta condicion se realizo para no volver a mostrar la Pantalla Registrar Clientes CRM cuando ya este asignado Wdiaz 
					RegistroCliente registro = factory.getInstance();
					registro.MostrarPantallaCliente(true,6);
					this.repintarFactura();
					
					double totalAPagar = CR.meServ.consultarMontoTransBR(true);
					
					if(CR.meServ.getVentaBR().getCliente().getCodCliente() == null) {
						MensajesVentanas.aviso("Debe asignar un cliente a la transacción");
					} else {
						try {
							Vector<Object> pagos = ManejoPagosFactory.getInstance().realizarPago(totalAPagar, CR.meServ.getVentaBR().getPagos(), CR.meServ.getVentaBR().getCliente(), CR.meServ.getVentaBR().getNumTransaccion());
							if (((Boolean)pagos.elementAt(2)).booleanValue()) {
								for (int i=0; i<((Vector<Pago>)pagos.elementAt(0)).size(); i++) {
									Pago pagoAct = ((Vector<Pago>)pagos.elementAt(0)).elementAt(i);
									CR.meServ.efectuarPago(pagoAct);
								}
								
								//Se finaliza la venta
								CR.meServ.finalizarVentaBR(true, true, true);
	
								CR.me.repintarIconos();
								this.comprobarLimiteEntrega();
							}
							this.repintarFactura();
						} catch (MontoPagoExcepcion e1) {
							logger.error("mouseClicked(MouseEvent)", e1);
	
							// Chequeamos si existe un pago que permita vuelto para devolver la diferencia
							double monto = 0;
							for (int i=0; i<CR.meServ.getVentaBR().getPagos().size(); i++) {
								Pago pago = (Pago) CR.meServ.getVentaBR().getPagos().elementAt(i);
								if (pago.getFormaPago().isPermiteVuelto())
									monto += pago.getMonto();
							}
							if (monto >= (CR.meServ.getVentaBR().obtenerMontoPagado()-totalAPagar)) {
								//Se finaliza la venta
								CR.meServ.finalizarVentaBR(true, true, true);
	
								this.repintarFactura();
								CR.me.repintarIconos();
								this.comprobarLimiteEntrega();
							} else
								throw new ExcepcionCr("Debe eliminar los pagos. No permiten vuelto al Cliente");
						}
					}
				}
			} catch(ExcepcionCr f) {
				logger.error("ejecutarAccion(int)", f);
	
				MensajesVentanas.mensajeError(f.getMensaje());
				repintarFactura();
			} catch (ConexionExcepcion f1) {
				logger.error("ejecutarAccion(int)", f1);
	
				MensajesVentanas.mensajeError(f1.getMensaje());
				repintarFactura();
			} catch (PrinterNotConnectedException f1) {
				logger.error("ejecutarAccion(int)", f1);
	
				MensajesVentanas.mensajeError(f1.getMessage());
				repintarFactura();
			}
			break;
		case 6:
			ejecutandoEvento = true;
			try {
				Vector<Object> pagoRealizado = ManejoPagosFactory.getInstance().eliminarPago(CR.meServ.consultarMontoTransBR(true),CR.meServ.getVentaBR().getPagos(), CR.meServ.getVentaBR().getCliente());
				
				if (((Double)pagoRealizado.elementAt(1)).doubleValue()>=0) {
					jLabel9.setText("VUELTO: " + df.format(((Double)pagoRealizado.elementAt(1)).doubleValue()));
					this.repintarFactura();
					CR.meServ.finalizarVentaBR(true, true, true);
				} else {
					jLabel9.setText("RESTO: " + df.format(-((Double)pagoRealizado.elementAt(1)).doubleValue()));
				}
			} catch (ExcepcionCr e1) {
				logger.error("ejecutarAccion(int)", e1);

				MensajesVentanas.mensajeError(e1.getMensaje());
			} catch (ConexionExcepcion e1) {
				logger.error("ejecutarAccion(int)", e1);

				MensajesVentanas.mensajeError(e1.getMensaje());
			} catch (PrinterNotConnectedException f1) {
				logger.error("ejecutarAccion(int)", f1);

				MensajesVentanas.mensajeError(f1.getMessage());
			}
			this.repintarFactura();
			break;
		case 7:
			if (jButton.getText().equals("Asignar Clientes")){
				//llamada a la extension de asigna/registrar cliente en CR  02/2009 Wdiaz 
				RegistroCliente registro = factory.getInstance();
				registro.MostrarPantallaCliente(false,6);				
			} else if (jButton.getText().equals("Quitar Cliente Asignado")){
				int respuesta = MensajesVentanas.preguntarSiNo("¿Desea quitar el cliente asignado?");
				if (respuesta==0){ //Si desea eliminarlo
					try {
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
				}
			} else if(jButton.getText().equals("Cambiar Cliente Asignado")){
				try {
					CR.meServ.cambiarClienteAsignado();
					try{
						CR.meServ.desbloquearTransaccionBR();
						CR.meServ.anularVentaBRActiva();
						this.dispose();
						CR.me.repintarMenuPrincipal();
						try {
							finalize();
						} catch (Throwable e1) {
							logger.error("keyPressed(KeyEvent)", e1);
						}
					} catch(ExcepcionCr f){
						logger.error("ejecutarAccion(int)", f);

						MensajesVentanas.mensajeError(f.getMensaje());
					} catch (ConexionExcepcion f1) {
						logger.error("ejecutarAccion(int)", f1);
					} catch (Exception f1) {
						logger.error("ejecutarAccion(int)", f1);
					} 
				} catch (BonoRegaloException e) {
					MensajesVentanas.mensajeError(e.getMessage());
				}
			}
			this.repintarFactura();
			break;
		case 8: //Sumar
			if(jTable.getSelectedRow()<0){
				MensajesVentanas.mensajeError("Debe seleccionar un detalle para su modificación");
				break;
			}
			AgregarTarjeta agregarTarjetaSuma = new AgregarTarjeta(true, false, jTable.getSelectedRow());
			MensajesVentanas.centrarVentanaDialogo(agregarTarjetaSuma);
			this.repintarFactura();
			break;
		case 9:
			//F5 Nuevo detalle
			AgregarTarjeta agregarTarjeta = new AgregarTarjeta(this.digito);
			MensajesVentanas.centrarVentanaDialogo(agregarTarjeta);
			this.repintarFactura();
			break;
		case 10:
			//F3 Ventas Corporativas
			Cliente vendCorp = CR.meServ.getVentaBR().getVendedor();
			if (vendCorp == null) {
				EjecutarConCantidad ecc = new EjecutarConCantidad("Colaborador Corporativo", "", 15);
				MensajesVentanas.centrarVentanaDialogo(ecc);
				vendCorp = CR.meServ.getVentaBR().getVendedor();
				if (vendCorp != null) {
					CR.me.borrarAvisos();
					CR.me.mostrarAviso("Asignado Vendedor Corporativo: " + vendCorp.getNumFicha() + " - " + vendCorp.getNombreCompleto(), true);
					this.getJButton4().setText("F3 Eliminar Corporativo");
				}
			} else {
				if (MensajesVentanas.preguntarSiNo("Está seguro que desea eliminar el vendedor corporativo\n" +
					vendCorp.getNumFicha() + " - " + vendCorp.getNombreCompleto())==0) {
					CR.me.borrarAvisos();
					this.getJButton4().setText("F3 Venta Corporativa");
					CR.meServ.getVentaBR().setVendedor(null);
				}
			}
			
			this.repintarFactura();
			break;
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
		if (!ejecutandoEvento) {
			//Mapeo de todas las teclas de funcion de la interfaz
			
			//Mapeo de F3
			if(e.getKeyCode() == KeyEvent.VK_F3 && jButton4.isEnabled()){
				jButton4.doClick();
			}

			//Mapeo de F4
			if(e.getKeyCode() == KeyEvent.VK_F4 && jButton6.isEnabled()){
				ejecutado=true;
				jButton6.doClick();
			}
			
			//Mapeo de F5
			if(e.getKeyCode() == KeyEvent.VK_F5 && jButton7.isEnabled()){
				ejecutado=true;
				jButton7.doClick();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_F1 && jButton3.isEnabled()){
				ejecutado=true;
				jButton3.doClick();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_F2 && jButton5.isEnabled()){
				ejecutado=true;
				jButton5.doClick();
			}
			
			//Mapeo de F8
			else if(e.getKeyCode() == KeyEvent.VK_F8){
				ejecutado=true;
				jButton8.doClick();
			}
			
			//Mapeo de F9
			else if(e.getKeyCode() == KeyEvent.VK_F9 && jButton11.isEnabled()){
				ejecutado=true;
				jButton11.doClick();
			}			
			
			//Mapeo de F10
			else if(e.getKeyCode() == KeyEvent.VK_F10 && jButton12.isEnabled()){
				ejecutado=true;
				jButton12.doClick();
			}
			
				
			//Mapeo la tecla de +
			else if((e.getKeyCode() == KeyEvent.VK_PLUS)||(e.getKeyCode() == 107) && jButton1.isEnabled()) {
				ejecutado=true;
				jButton1.doClick();
			}
			
			//Mapeo la tecla de -
			else if((e.getKeyCode() == KeyEvent.VK_MINUS)||(e.getKeyCode() == 109) && jButton2.isEnabled()) {
				ejecutado=true;
				jButton2.doClick();
			}
			
			else if(e.getKeyCode() == KeyEvent.VK_A && jButton.isEnabled()) {
				ejecutado=true;
				jButton.doClick();
			}	
			
			if (!ejecutado) {
				ejecutandoEvento = true;
				
				//Verificamos que se presionó un número
				if (Character.isDigit(e.getKeyChar())) {
					AgregarTarjeta agregarTarjeta = new AgregarTarjeta(e.getKeyChar()+"");
					MensajesVentanas.centrarVentanaDialogo(agregarTarjeta);
					this.repintarFactura();
				}
			} else {
					e.consume();
			}

			
			ejecutandoEvento = false;
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
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se comentó variable sin uso
	* Fecha: agosto 2011
	*/
	public void repintarFactura(){
		if (logger.isDebugEnabled()) {
			logger.debug("repintarFactura() - start");
		}

		try{
			try {	
				if (CR.meServ.getVentaBR().getPagos().size()==0) jButton2.setEnabled(false);
				else jButton2.setEnabled(true);
			}catch(Exception e){jButton2.setEnabled(false);}
			//Inicializamos el modelo de tabla para que cargue el ComboBox repectivo
			//TableColumnModel tcm = jTable.getColumnModel();
			MaquinaDeEstado.setEstadoCaja(Sesion.getCaja().getEstado());
			modeloTablaDetalleBR.llenarTabla();
			String nombre, rif;
			if (CR.meServ.getVentaBR() == null) {
				CR.me.setCliente("","");
				CR.me.setExento(false);
				jButton.setText("Asignar Clientes");
				getJTextField4().setText(df.format(0));
				getJTextField5().setText(df.format(0));
				getJTextField6().setText(df.format(0));
				getJLabel9().setText("Resto: " + df.format(0));
				getJLabel10().setText("No.Artículos: " + df.format(0));
				modeloTablaDetallePagos.iniciarDatosTabla();
			} else {
				if (CR.meServ.getVentaBR().getCliente() == null || 
						CR.meServ.getVentaBR().getCliente().getCodCliente()== null){
						nombre = "";
						rif = "";
						jButton.setText("Asignar Clientes");
						
				} else{
					nombre = new String(CR.meServ.getVentaBR().getCliente().getNombreCompleto());
					rif = new String(CR.meServ.getVentaBR().getCliente().getCodCliente());
					
					if(CR.meServ.getVentaBR().getEstadoTransaccion()!=Sesion.BONO_REGALO_EN_PROCESO)
						jButton.setText("Cambiar Cliente Asignado");
					else
						jButton.setText("Quitar Cliente Asignado");
					if(CR.meServ.getVentaBR().isVentaExenta()) {
						CR.me.setExento(true);
						CR.me.mostrarAviso("Cliente Exento de Impuestos", true);
					} else {
						CR.me.setExento(false);
					}
						
				}
					
				CR.me.setCliente(nombre, rif);
				getJTextField4().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meServ.getVentaBR().getMontoBase())));
				getJTextField5().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meServ.getVentaBR().getMontoImpuesto())));
				getJTextField6().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meServ.getVentaBR().getMontoBase() + CR.meServ.getVentaBR().getMontoImpuesto())));
				modeloTablaDetallePagos.llenarTablaPagos(CR.meServ.getVentaBR().getPagos());
				
				if (CR.meServ.getVentaBR().consultarMontoTrans() - CR.meServ.getVentaBR().obtenerMontoPagado()>=0)
					jLabel9.setText("RESTO: " + df.format(CR.meServ.consultarMontoTransBR(true) - CR.meServ.getVentaBR().obtenerMontoPagado()));
				else
					jLabel9.setText("VUELTO: " + df.format(CR.meServ.getVentaBR().obtenerMontoPagado()-CR.meServ.getVentaBR().consultarMontoTrans()));
				jLabel10.setText("No.Artículos: " + df.format(CR.meServ.obtenerCantidadProdsBR()));
				
				
				if(CR.meServ.getVentaBR()!=null)
					jButton2.setEnabled(!CR.meServ.getVentaBR().isTarjetasRecargadas());
			}

		} catch (Exception ex){
			logger.error("repintarFactura()", ex);
			CR.me.setCliente("","");
			CR.me.setExento(false);
			getJTextField4().setText(df.format(0));
			getJTextField5().setText(df.format(0));
			getJTextField6().setText(df.format(0));
			getJLabel9().setText("Resto: " + df.format(0));
			getJLabel10().setText("No.Artículos: " + df.format(0));
			modeloTablaDetallePagos.iniciarDatosTabla();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("repintarFactura() - end");
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
			
			if (getRowCount() > 0) {
				changeSelection(getRowCount() - 1, VentaBonoRegalo.column, false, false);
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
			if(CR.meServ.getVentaBR()!=null)
				jButton.setEnabled(!CR.meServ.getVentaBR().isTarjetasRecargadas() ||
						(CR.meServ.getVentaBR().getEstadoTransaccion()!=Sesion.BONO_REGALO_EN_PROCESO));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton() - end");
		}
		return jButton;
	}

	public void componentHidden(ComponentEvent e) {
		// TODO Apéndice de método generado automáticamente
		
	}

	public void componentMoved(ComponentEvent e) {
		// TODO Apéndice de método generado automáticamente
		
	}

	public void componentResized(ComponentEvent e) {
		// TODO Apéndice de método generado automáticamente
		
	}

	public void componentShown(ComponentEvent e) {
		// TODO Apéndice de método generado automáticamente
		
	}
	private void agregarListeners() {
		if (logger.isDebugEnabled()) {
			logger.debug("agregarListeners() - start");
		}

		jScrollPane.addKeyListener(this);
		
		
		jTable.addKeyListener(this);
		

		jScrollPane1.addKeyListener(this);
		
		
		jTable1.addKeyListener(this);
		
		
		jButton1.addKeyListener(this);
		jButton1.addActionListener(this);
		
		
		jButton2.addKeyListener(this);
		jButton2.addActionListener(this);
		
		jButton3.addKeyListener(this);
		jButton3.addActionListener(this);
		
		jButton4.addKeyListener(this);
		jButton4.addActionListener(this);
		
		jButton5.addKeyListener(this);
		jButton5.addActionListener(this);
		
		jButton6.addKeyListener(this);
		jButton6.addActionListener(this);
		
		jButton7.addActionListener(this);
		jButton7.addKeyListener(this);
		
		jButton8.addKeyListener(this);
		jButton8.addActionListener(this);

		jButton9.addKeyListener(this);
		jButton9.addActionListener(this);
		
		jButton10.addKeyListener(this);
		jButton10.addActionListener(this);
		
		jButton11.addKeyListener(this);
		jButton11.addActionListener(this);
		
		jButton12.addKeyListener(this);
		jButton12.addActionListener(this);
	
		jButton13.addKeyListener(this);
		jButton13.addActionListener(this);
		
		jButton14.addKeyListener(this);
		jButton14.addActionListener(this);
		
		jButton.addKeyListener(this);
		jButton.addActionListener(this);
		
		if (logger.isDebugEnabled()) {
			logger.debug("agregarListeners() - end");
		}
	}

	private void eliminarListeners() {
		if (logger.isDebugEnabled()) {
			logger.debug("eliminarListeners() - start");
		}

		jScrollPane.removeKeyListener(this);
		
		
		jTable.removeKeyListener(this);
		

		jScrollPane1.removeKeyListener(this);
		
		
		jTable1.removeKeyListener(this);
		
		
		jButton1.removeKeyListener(this);
		jButton1.removeActionListener(this);
		
		
		jButton2.removeKeyListener(this);
		jButton2.removeActionListener(this);
		
		jButton3.removeKeyListener(this);
		jButton3.removeActionListener(this);
		
		jButton4.removeKeyListener(this);
		jButton4.removeActionListener(this);
		
		jButton5.removeKeyListener(this);
		jButton5.removeActionListener(this);
		
		jButton6.removeKeyListener(this);
		jButton6.removeActionListener(this);
		
		jButton7.removeActionListener(this);
		jButton7.removeKeyListener(this);
		
		jButton8.removeKeyListener(this);
		jButton8.removeActionListener(this);

		jButton9.removeKeyListener(this);
		jButton9.removeActionListener(this);
		
		jButton10.removeKeyListener(this);
		jButton10.removeActionListener(this);
		
		jButton11.removeKeyListener(this);
		jButton11.removeActionListener(this);
		
		jButton12.removeKeyListener(this);
		jButton12.removeActionListener(this);
	
		jButton13.removeKeyListener(this);
		jButton13.removeActionListener(this);
		
		jButton14.removeKeyListener(this);
		jButton14.removeActionListener(this);
		
		jButton.removeKeyListener(this);
		jButton.removeActionListener(this);

		if (logger.isDebugEnabled()) {
			logger.debug("eliminarListeners() - end");
		}
	}
}
