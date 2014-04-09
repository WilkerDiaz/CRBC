/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : PantallaCotizacion.java
 * Creado por : Gabriel Martinelli	<gmartinelli@beco.com.ve>
 * Creado en  : May 18, 2004 - 10:21:55 AM
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
package com.becoblohm.cr.gui;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.AfiliadoUsrExcepcion;
import com.becoblohm.cr.excepciones.AutorizacionExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstado;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.perifericos.EfectuarLecturaEscaner;
import com.becoblohm.cr.utiles.Control;
import com.becoblohm.cr.utiles.MensajesVentanas;

/**
 * @author vmedina
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PantallaCotizacion extends JDialog implements ActionListener, KeyListener, DocumentListener, ScanerSwitch {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(PantallaCotizacion.class);

	private ModeloTablaCotizacion modeloTablaDetalle;// = new ModeloTablaCotizacion();
	private ModeloTabla modeloTablaDetalleVenta = new ModeloTabla(true);
	private DecimalFormat df = new DecimalFormat("#,##0.00");
	private int filaAModificar = 0;
	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel14 = null;
	private javax.swing.JScrollPane jScrollPane = null;
	private javax.swing.JTable jTable = null;
	private javax.swing.JPanel jPanel15 = null;
	private javax.swing.JPanel jPanel16 = null;
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
	private javax.swing.JLabel jLabel9 = null;
	private javax.swing.JLabel jLabel15 = null;
	private javax.swing.JLabel jLabel19 = null;
	private javax.swing.JPanel jPanel23 = null;
	private javax.swing.JScrollPane jScrollPane2 = null;
	private javax.swing.JTable jTable2 = null;
	
	private boolean ejecutandoEvento = false;
	private EfectuarLecturaEscaner lector = null;
	private boolean permitirEscaner = true;
	
	/**
	 * This is the default constructor
	 */
	public PantallaCotizacion(int tda, int servicio, Date fecha) {
		super(MensajesVentanas.ventanaActiva);
		modeloTablaDetalle = new ModeloTablaCotizacion(CR.meServ.getCotizacion().getEstadoServicio());
		initialize();
		this.ejecutandoEvento = false;
		
		jLabel9.setText(jLabel9.getText() + servicio);
		SimpleDateFormat datef = new SimpleDateFormat("dd-MM-yyyy");
		jLabel15.setText("Tienda: " + tda + ". Fecha: " + datef.format(fecha));
			
		jScrollPane.addKeyListener(this);
		//jScrollPane.addActionListener(this);
		
		jTable.addKeyListener(this);
		//jTable.addActionListener(this);
		
		if (jTable2!=null) {
			jTable2.addKeyListener(this);
			//jTable2.addActionListener(this);
		}

		jButton3.addKeyListener(this);
		jButton3.addActionListener(this);

		jButton5.addKeyListener(this);
		jButton5.addActionListener(this);

		jButton4.addKeyListener(this);
		jButton4.addActionListener(this);

		jButton7.addKeyListener(this);
		jButton7.addActionListener(this);
		
		jButton8.addKeyListener(this);
		jButton8.addActionListener(this);

		jButton6.addKeyListener(this);
		jButton6.addActionListener(this);
		
		jButton11.addKeyListener(this);
		jButton11.addActionListener(this);
		
		jButton12.setEnabled(false);

		this.repintarPantalla();
		this.getJTable().requestFocus();
		this.getJTable().setRowSelectionInterval(0,0);
		
		CR.inputEscaner.getDocument().addDocumentListener(this);
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
		this.setName("FrameDevolucion");
		this.setVisible(false);
		this.setTitle("CR - Cotización");
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
			if (CR.meServ.getCotizacion().getEstadoServicio()==Sesion.COTIZACION_ACTIVA)
				jPanel.add(getJPanel23(), null);
			jPanel.add(getJPanel15(), null);
			jPanel.add(getJPanel16(), null);
			jPanel.add(PanelMensajesCR.getAreaMensajes(), null);
			jPanel.add(PanelMensajesCR.getPanelEstadoCaja(), null);
			jPanel.add(PanelMensajesCR.getPanelStatus(), null);
			jPanel.setName("ContenedorPrincipalIzquierdo");
			jPanel.setPreferredSize(new java.awt.Dimension(660,560));
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
			jPanel1.setPreferredSize(new java.awt.Dimension(130,560));
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
			layFlowLayout16.setAlignment(java.awt.FlowLayout.RIGHT);
			layFlowLayout16.setHgap(1);
			layFlowLayout16.setVgap(1);
			jPanel14.setLayout(layFlowLayout16);
			jPanel14.add(getJScrollPane(), null);
			if (CR.meServ.getCotizacion().getEstadoServicio() == Sesion.COTIZACION_ACTIVA)
				jPanel14.setPreferredSize(new java.awt.Dimension(656,140));
			else
				jPanel14.setPreferredSize(new java.awt.Dimension(656,280));
			jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle Cotización: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), java.awt.Color.black));
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
			jTable = new javax.swing.JTable(modeloTablaDetalle);
			jTable.getTableHeader().setReorderingAllowed(false) ;
			if (CR.meServ.getCotizacion().getEstadoServicio()==Sesion.COTIZACION_ACTIVA) {
				jTable.getColumnModel().getColumn(0).setPreferredWidth(25);
				jTable.getColumnModel().getColumn(1).setPreferredWidth(88);
				jTable.getColumnModel().getColumn(2).setPreferredWidth(150);
				jTable.getColumnModel().getColumn(3).setPreferredWidth(65);
				jTable.getColumnModel().getColumn(4).setPreferredWidth(75);
				jTable.getColumnModel().getColumn(5).setPreferredWidth(75);
				jTable.getColumnModel().getColumn(6).setPreferredWidth(76);
				jTable.getColumnModel().getColumn(7).setPreferredWidth(35);
				jTable.setEnabled(false);
				jTable.setFocusable(false);
				jTable.setRowSelectionAllowed(false);
			} else {
				jTable.getColumnModel().getColumn(0).setPreferredWidth(88);
				jTable.getColumnModel().getColumn(1).setPreferredWidth(150);
				jTable.getColumnModel().getColumn(2).setPreferredWidth(65);
				jTable.getColumnModel().getColumn(3).setPreferredWidth(90);
				jTable.getColumnModel().getColumn(4).setPreferredWidth(90);
				jTable.getColumnModel().getColumn(5).setPreferredWidth(90);
				jTable.getColumnModel().getColumn(6).setPreferredWidth(35);
			}
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
			if (CR.meServ.getCotizacion().getEstadoServicio()==Sesion.COTIZACION_ACTIVA)
				jScrollPane.setPreferredSize(new java.awt.Dimension(645,100));
			else
				jScrollPane.setPreferredSize(new java.awt.Dimension(645,240));
			jScrollPane.setBackground(new java.awt.Color(242,242,238));
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
			layFlowLayout17.setHgap(5);
			layFlowLayout17.setVgap(5);
			jPanel15.setLayout(layFlowLayout17);
			jPanel15.add(getJLabel9(), null);
			jPanel15.add(getJLabel15(), null);
			jPanel15.add(getJLabel19(), null);
			jPanel15.setPreferredSize(new java.awt.Dimension(328,120));
			jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cotización: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), java.awt.Color.black));
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
			jPanel16.setPreferredSize(new java.awt.Dimension(328,120));
			jPanel16.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel16() - end");
		}
		return jPanel16;
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
			if (CR.meServ.getCotizacion().getEstadoServicio()==Sesion.COTIZACION_ACTIVA)
				jLabel10.setText("No. Artículos: ");
			else
				jLabel10.setText(" ");
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
			jPanel19.setPreferredSize(new java.awt.Dimension(320,70));
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
			jTextField4.setText("00,00");
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
			jTextField5.setText("00,00");
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
			jTextField6.setText("00,00");
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
			//jButton3.setEnabled(false);
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
				//jButton5.setEnabled(false);
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
			jButton6.setText("F4 Facturar");
			jButton6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton6.setDefaultCapable(false);
			jButton6.setMargin(new Insets(1,2,1,1));
			jButton6.setBackground(new java.awt.Color(226,226,222));
			//if (CR.meServ.getCotizacion().getEstadoServicio()!=Sesion.COTIZACION_ACTIVA)
			//	jButton6.setEnabled(false);
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
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - start");
		}
			
		if (!ejecutandoEvento) {
			//Mapeo de F1 en eventos de Mouse
			if(e.getSource().equals(jButton3)){
				this.ejecutandoEvento = true;
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				if (jTable2.getSize().height > 0) {
					this.actualizarTipoEntregaDetalles();
					CambiarCantidad cc;
					String codigo = ""; 
					if(jTable2.getSelectedRow() != -1){
						filaAModificar = jTable2.getSelectedRow();
						cc = new CambiarCantidad(filaAModificar, 1);
						codigo = (String)this.modeloTablaDetalleVenta.getDatos()[filaAModificar][0];
					} else {
						cc = new CambiarCantidad(1);
						codigo = (String)this.modeloTablaDetalleVenta.getDatos()[this.modeloTablaDetalleVenta.getRowCount()-1][0];
					}
					MensajesVentanas.centrarVentanaDialogo(cc);
					try {
						if (cc.isEjecutado()) {
							float cantidadAgregada = new Float(CR.me.formatoNumerico(cc.getJTextField().getText())).floatValue();
							CR.meServ.getCotizacion().modificarVendido(codigo.trim(), cantidadAgregada, false);
						}
					} catch (Exception e1) {
					}
				} else {
					MensajesVentanas.mensajeError("No existe venta activa");
				}
				CR.inputEscaner.getDocument().addDocumentListener(this);
				this.repintarPantalla();
				if (logger.isDebugEnabled()) {
					logger.debug("mouseClicked(MouseEvent) - F1");
				}		
			}
			
			//Mapeo de F2 en eventos de Mouse
			else if(e.getSource().equals(jButton5)){
				this.ejecutandoEvento = true;
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				if (jTable2.getSize().height > 0) {
					this.actualizarTipoEntregaDetalles();
					String codigo = "";
					try {
						AnularProducto anularProducto = null;
						if(jTable2.getSelectedRow() == -1){
							anularProducto = new AnularProducto(CR.meVenta.getVenta().getDetallesTransaccion().size()-1);
							MensajesVentanas.centrarVentanaDialogo(anularProducto);
						} else {
							anularProducto = new AnularProducto(jTable2.getSelectedRow());
							MensajesVentanas.centrarVentanaDialogo(anularProducto);
						}
						try {
							if (anularProducto.isEjecutado()) {
								float cantidadEliminada = new Float(CR.me.formatoNumerico(anularProducto.getJTextField1().getText())).floatValue();
								codigo = anularProducto.getJTextField().getText();
								CR.meServ.getCotizacion().modificarVendido(codigo.trim(), cantidadEliminada, true);
							}
						} catch (Exception e1) {}
						anularProducto = null;
						this.repintarPantalla();		
					} catch (Exception e1) {
						logger.error("mouseClicked(MouseEvent)", e1);
	
						MaquinaDeEstadoExcepcion meEx = new MaquinaDeEstadoExcepcion("Estado de caja Incorrecto");
						MensajesVentanas.mensajeError(meEx.getMensaje());
					}
				} else {
					MensajesVentanas.mensajeError("No existe venta activa");
				}
				CR.inputEscaner.getDocument().addDocumentListener(this);
				if (logger.isDebugEnabled()) {
					logger.debug("mouseClicked(MouseEvent) - F2");
				}
			}
	
			//Mapeo de F3 en Eventos de Mouse
			else if(e.getSource().equals(jButton4) && (getJButton4().isEnabled())){
				this.ejecutandoEvento = true;
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				if (jTable2.getSize().height > 0) {
					this.actualizarTipoEntregaDetalles();
					try {
						Rebajas rebajas = null;
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
							this.repintarPantalla();
						} else {
							MensajesVentanas.aviso("El producto no permite rebajas");
						}
					} catch (Exception e1) {
						logger.error("mouseClicked(MouseEvent)", e1);
	
						MaquinaDeEstadoExcepcion meEx = new MaquinaDeEstadoExcepcion("Estado de caja Incorrecto");
						MensajesVentanas.mensajeError(meEx.getMensaje());
					}
				} else {
					MensajesVentanas.mensajeError("No existe venta activa");
				}
				CR.inputEscaner.getDocument().addDocumentListener(this);
			}
			
			//Mapeo de F4 en eventos de Mouse
			if((e.getSource().equals(jButton6))&&(CR.meServ.getCotizacion().getEstadoServicio()==Sesion.COTIZACION_ACTIVA)){
				this.ejecutandoEvento = true;
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				try {
					try { CR.crVisor.enviarString("SUB-TOTAL", 0, df.format(CR.meVenta.getVenta().consultarMontoTrans()+CR.meServ.getCotizacion().consultarMontoServ()), 2); }
					catch (Exception e1) {
						logger.error("mouseClicked(MouseEvent)", e1);
					}
					if (CR.meVenta.obtenerCantidadProds() > 0){
						if (MensajesVentanas.preguntarSiNo("Desea Facturar la Cotizacion?")==0) {
								actualizarTipoEntregaDetalles();
							CR.meServ.facturarCotizacion();
							dispose();
							CR.me.repintarMenuPrincipal();
						} else {
							CR.inputEscaner.getDocument().addDocumentListener(this);
						}
					} else {
						if (MensajesVentanas.preguntarSiNo("No existen productos a facturar.\n¿Desea finalizar la consulta de esta Cotización?")==0) {
							try {
								CR.meServ.finalizarConsultaCotizacion();
								if (logger.isDebugEnabled()) {
									logger
											.debug("mouseClicked(MouseEvent) - F9");
								}
								dispose();
								CR.me.repintarMenuPrincipal();
							} catch (ExcepcionCr e3) {
								CR.inputEscaner.getDocument().addDocumentListener(this);
								logger.error("mouseClicked(MouseEvent)", e3);
							}						
						} else {
							CR.inputEscaner.getDocument().addDocumentListener(this);
						}
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
				if (logger.isDebugEnabled()) {
					logger.debug("mouseClicked(MouseEvent) - F4");
				}
			}
			
			//Mapeo de F5 en eventos de Mouse
			if(e.getSource().equals(jButton7)){
				this.ejecutandoEvento = true;
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				this.actualizarTipoEntregaDetalles();
				Consultas consultas = new Consultas();
				MensajesVentanas.centrarVentanaDialogo(consultas);
				String casoConsulta = consultas.getCodigoSeleccionado()[0];
				String codigo = consultas.getCodigoSeleccionado()[1];
				if (casoConsulta!=null) {
					try {
						if (new Integer(casoConsulta).intValue()<8) {
							CR.meVenta.ingresarLineaProducto(codigo, Sesion.capturaTeclado);
							CR.meServ.getCotizacion().modificarVendido(codigo);
						} else {
							CR.meVenta.asignarCliente(codigo);
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
				this.repintarPantalla();			
				if (logger.isDebugEnabled()) {
					logger.debug("mouseClicked(MouseEvent) - F5");
				}
			}
	
			//Mapeo de F8 en eventos del mouse
			if(e.getSource().equals(jButton8)){
				this.ejecutandoEvento = true;
				Utilitarios utilitarios = null;
				utilitarios = new Utilitarios();
				MensajesVentanas.centrarVentanaDialogo(utilitarios);
				this.repintarPantalla();
			}
			
			//Mapeo de F9 en eventos de Mouse
			if(e.getSource().equals(jButton11)){
				this.ejecutandoEvento = true;
				if (MensajesVentanas.preguntarSiNo(Sesion.MSG_CANCELAR)==0) {
					CR.inputEscaner.getDocument().removeDocumentListener(this);
					try {
						CR.meServ.finalizarConsultaCotizacion();
						
						dispose();
						CR.me.repintarMenuPrincipal();
					} catch(ExcepcionCr ex){
						logger.error("mouseClicked(MouseEvent)", ex);
						CR.inputEscaner.getDocument().addDocumentListener(this);
					}catch(Exception ex){
						logger.error("mouseClicked(MouseEvent)", ex);
					}
				}				
				//input.getDocument().addDocumentListener(this);
				if (logger.isDebugEnabled()) {
					logger.debug("mouseClicked(MouseEvent) - F9");
				}
			}		
			ejecutandoEvento = false;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - end");
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
			//Mapeo de F1
			if((e.getKeyCode() == KeyEvent.VK_F1)&&(getJButton3().isEnabled())){
				this.getJButton3().doClick();
				if (logger.isDebugEnabled()) {
					logger.debug("keyPressed(KeyEvent) - F1");
				}
			}
			
			//Mapeo de F2
			if((e.getKeyCode() == KeyEvent.VK_F2)&&(getJButton5().isEnabled())){
				this.getJButton5().doClick();
				if (logger.isDebugEnabled()) {
					logger.debug("keyPressed(KeyEvent) - F2");
				}
			}
	
			//Mapeo de F3
			if((e.getKeyCode() == KeyEvent.VK_F3)&&(getJButton4().isEnabled())){
				this.getJButton4().doClick();
				if (logger.isDebugEnabled()) {
					logger.debug("keyPressed(KeyEvent) - F3");
				}
			}
			
			//Mapeo de F4
			if((e.getKeyCode() == KeyEvent.VK_F4)&&(getJButton6().isEnabled())){
				this.getJButton6().doClick();
				if (logger.isDebugEnabled()) {
					logger.debug("keyPressed(KeyEvent) - F4");
				}
			}
			
			//Mapeo de F5
			if((e.getKeyCode() == KeyEvent.VK_F5)&&(getJButton7().isEnabled())){
				this.getJButton7().doClick();
				if (logger.isDebugEnabled()) {
					logger.debug("keyPressed(KeyEvent) - F5");
				}
			}
	
			//Mapeo de F8
			if((e.getKeyCode() == KeyEvent.VK_F8)&&(getJButton8().isEnabled())){
				this.getJButton8().doClick();
				if (logger.isDebugEnabled()) {
					logger.debug("keyPressed(KeyEvent) - F8");
				}
			}
			
			//Mapeo de F9
			if(e.getKeyCode() == KeyEvent.VK_F9){
				this.getJButton11().doClick();
				if (logger.isDebugEnabled()) {
					logger.debug("keyPressed(KeyEvent) - F9");
				}
			}
	
			// Mapeamos cualquier evento con la tecla TAB en las tablas para movernos por ellas
			if (e.getSource().equals(jTable)) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					JTable t = jTable;
					int col2 = t.getColumnModel().getColumnIndex("");
					int col = t.getColumnModel().getColumnIndex("Entrega");
					if (t.getSelectedColumn() == col || t.getSelectedColumn() == col2) {
						t.editCellAt(t.getSelectedRow(), t.getSelectedColumn());
					}
					e.consume();
					ejecutado = true;					
				}
			}
	
			if (!ejecutado) {
				this.ejecutandoEvento = true;
				this.actualizarTipoEntregaDetalles();
				//Verificamos que se presionó un número
				if ((e.getKeyChar()=='1')||(e.getKeyChar()=='2')||(e.getKeyChar()=='3')||(e.getKeyChar()=='4')||(e.getKeyChar()=='5')
					||(e.getKeyChar()=='6')||(e.getKeyChar()=='7')||(e.getKeyChar()=='8')||(e.getKeyChar()=='9')||(e.getKeyChar()=='0')) {
					// Mostramos una ventana para que el usuario introduzca el código del producto
					CR.inputEscaner.getDocument().removeDocumentListener(this);
					EjecutarConCantidad ec = new EjecutarConCantidad("Ingreso de Código:", String.valueOf(e.getKeyChar()),3);
					MensajesVentanas.centrarVentanaDialogo(ec);
					String textoLeido = ec.getJTextField1().getText();
					if (textoLeido!=null && !textoLeido.equalsIgnoreCase("") && ec.isEjecutado()) {
						CR.meServ.getCotizacion().modificarVendido(textoLeido);
					}
					CR.inputEscaner.getDocument().addDocumentListener(this);
					this.repintarPantalla();
				}
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
	private void repintarPantalla(){
		if (logger.isDebugEnabled()) {
			logger.debug("repintarPantalla() - start");
		}

		try{
			if (jTable2!=null) {
				//Inicializamos el modelo de tabla para que cargue el ComboBox repectivo
				TableColumnModel tcm = jTable2.getColumnModel();
				TableColumn tc = tcm.getColumn(7);
				tc.setCellRenderer(new EntregaRenderer());
				tc.setCellEditor(new EntregaEditor(this)); 
			}

			MaquinaDeEstado.setEstadoCaja(Sesion.getCaja().getEstado());
			modeloTablaDetalle.llenarTablaDetalle();

			if (CR.meServ.getCotizacion() != null){
				String nombre, rif;
				if (CR.meVenta.getVenta().getCliente().getCodCliente() == null){
					nombre = "";
					rif = "";
					this.getJButton4().setEnabled(true);
				} else{
					nombre = new String(CR.meVenta.getVenta().getCliente().getNombreCompleto());
					rif = new String(CR.meVenta.getVenta().getCliente().getCodCliente());
					
					//Verificamos si el cliente es un COLABORADOR para chequear si se permiten hacer rebajas para este tipo de ventas (por preferencias)
					//Si no se permite se deshabilita la tecla asociada a esta funcionalidad
					if(CR.meVenta.getVenta().getCliente().getTipoCliente() == Sesion.COLABORADOR && (CR.meVenta.getVenta().getCliente().getEstadoColaborador() == Sesion.ACTIVO) && !Sesion.permitirRebajasAempleados) {
						this.getJButton4().setEnabled(false);
					} else {
						this.getJButton4().setEnabled(true);
					}
					
					//Verificamos si el cliente es exento para mostrar icono indicador de Venta exenta
					if(CR.meVenta.getVenta().isVentaExenta()) {
						CR.me.setExento(true);
						CR.me.mostrarAviso("Cliente Exento de Impuestos", true);
					} else {
						CR.me.setExento(false);
					}

					CR.me.setCliente(nombre, rif);
				}
				if (CR.meServ.getCotizacion().getEstadoServicio()==Sesion.COTIZACION_ACTIVA)
					getJLabel19().setText("Estado: ACTIVO");
				if (CR.meServ.getCotizacion().getEstadoServicio()==Sesion.COTIZACION_FACTURADA) {
					jButton3.setEnabled(false);
					jButton3.removeKeyListener(this);
					jButton3.removeActionListener(this);
	
					jButton5.setEnabled(false);
					jButton5.removeKeyListener(this);
					jButton5.removeActionListener(this);
	
					jButton4.setEnabled(false);
					jButton4.removeKeyListener(this);
					jButton4.removeActionListener(this);
	
					jButton6.setEnabled(false);
					jButton6.removeKeyListener(this);
					jButton6.removeActionListener(this);
	
					jButton7.setEnabled(false);
					jButton7.removeKeyListener(this);
					jButton7.removeActionListener(this);
	
					jButton8.setEnabled(false);
					jButton8.removeKeyListener(this);
					jButton8.removeActionListener(this);
					
					getJLabel19().setText("Estado: FACTURADO");
					getJTextField4().setText(Sesion.getTienda().getMonedaBase()+" "+df.format(CR.meServ.getCotizacion().getMontoBase()));
					getJTextField5().setText(Sesion.getTienda().getMonedaBase()+" "+df.format(CR.meServ.getCotizacion().getMontoImpuesto()));
					getJTextField6().setText(Sesion.getTienda().getMonedaBase()+" "+df.format(CR.meServ.getCotizacion().consultarMontoServ()));
	
				} else {
					jLabel10.setText("No. Artículos: " + df.format(CR.meVenta.obtenerCantidadProds()));
					getJTextField4().setText(Sesion.getTienda().getMonedaBase()+" "+df.format(CR.meVenta.getVenta().getMontoBase()));
					getJTextField5().setText(Sesion.getTienda().getMonedaBase()+" "+df.format(CR.meVenta.getVenta().getMontoImpuesto()));
					getJTextField6().setText(Sesion.getTienda().getMonedaBase()+" "+df.format(CR.meVenta.getVenta().consultarMontoTrans()));
					modeloTablaDetalleVenta.llenarTabla();
				}
				jTable.requestFocus();
			}
		} catch (Exception ex){
			CR.me.setExento(false);
			CR.me.setCliente("","");
			this.getJButton4().setEnabled(true);
			getJTextField4().setText(df.format(0.0));
			getJTextField5().setText(df.format(0.0));
			getJTextField6().setText(df.format(0.0));
			logger.error("repintarPantalla()", ex);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("repintarPantalla() - end");
		}
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
			jLabel9.setText("Cotización Nro. ");
			jLabel9.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel9.setPreferredSize(new java.awt.Dimension(320,24));
			jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			jLabel9.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel9() - end");
		}
		return jLabel9;
	}
	/**
	 * This method initializes jLabel15
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel15() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel15() - start");
		}

		if(jLabel15 == null) {
			jLabel15 = new javax.swing.JLabel();
			jLabel15.setText("Tienda: . Fecha: ");
			jLabel15.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel15.setPreferredSize(new java.awt.Dimension(320,24));
			jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel15() - end");
		}
		return jLabel15;
	}
	/**
	 * This method initializes jLabel19
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel19() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel19() - start");
		}

		if(jLabel19 == null) {
			jLabel19 = new javax.swing.JLabel();
			jLabel19.setText("Estado: ");
			jLabel19.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel19() - end");
		}
		return jLabel19;
	}
	/**
	 * This method initializes jPanel22
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel23() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel23() - start");
		}

		if(jPanel23 == null) {
			jPanel23 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout19 = new java.awt.FlowLayout();
			layFlowLayout19.setHgap(1);
			layFlowLayout19.setVgap(1);
			layFlowLayout19.setAlignment(java.awt.FlowLayout.RIGHT);
			jPanel23.setLayout(layFlowLayout19);
			jPanel23.add(getJScrollPane2(), null);
			jPanel23.setPreferredSize(new java.awt.Dimension(656,140));
			jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle Venta: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), java.awt.Color.black));
			jPanel23.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel23() - end");
		}
		return jPanel23;
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
			jTable2 = new TablaPlus(modeloTablaDetalleVenta);
			jTable2.getTableHeader().setReorderingAllowed(false) ;
			jTable2.getColumnModel().getColumn(0).setPreferredWidth(88);
			jTable2.getColumnModel().getColumn(1).setPreferredWidth(150);
			jTable2.getColumnModel().getColumn(2).setPreferredWidth(65);
			jTable2.getColumnModel().getColumn(3).setPreferredWidth(75);
			jTable2.getColumnModel().getColumn(4).setPreferredWidth(75);
			jTable2.getColumnModel().getColumn(5).setPreferredWidth(76);
			jTable2.getColumnModel().getColumn(6).setPreferredWidth(35);
			jTable2.setLocale(new java.util.Locale("es", "VE", ""));
			jTable2.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jTable2.setBackground(new java.awt.Color(242,242,238));
			jTable2.setCellSelectionEnabled(true);

		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTable2() - end");
		}
		return jTable2;
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

	
	//WDIAZ:02-07-2013 Controlar N Eventos con el escaner (Pantallas) simultaneamente
	public void leerEscaner(final String codigoScaner) {
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
				}
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
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	public void lecturaEscaner(String codigoScaner) {
		if (logger.isDebugEnabled()) {
			logger.debug("lecturaEscaner(String) - start");
		}

		if (isPermitirEscaner()) { 
				setPermitirEscaner(false); //WDIAZ:02-07-2013 Controlar N Eventos con el escaner (Pantallas) simultaneamente
				try {
					if (CR.meServ.getCotizacion().getEstadoServicio() == Sesion.COTIZACION_ACTIVA) {
						actualizarTipoEntregaDetalles();
						ArrayList <Object>valorLeido = Control.codigoValido(codigoScaner);
						int tipoCodigo = ((Integer)valorLeido.get(0)).intValue();
						String codigo = (String)valorLeido.get(1);
						switch (tipoCodigo){
							case Control.PRODUCTO:
								CR.inputEscaner.getDocument().removeDocumentListener(this);
								CR.meVenta.ingresarLineaProducto(codigo, Sesion.capturaEscaner);
								CR.meServ.getCotizacion().modificarVendido(codigo);
								CR.inputEscaner.getDocument().addDocumentListener(this);
								break;
							case Control.CLIENTE:
								CR.inputEscaner.getDocument().removeDocumentListener(this);
								CR.meVenta.asignarCliente(codigo);
								CR.inputEscaner.getDocument().addDocumentListener(this);
								break;
							case Control.COLABORADOR:
								CR.inputEscaner.getDocument().removeDocumentListener(this);
								CR.meVenta.asignarCliente(codigo);
								CR.inputEscaner.getDocument().addDocumentListener(this);
								break;
							case Control.CODIGO_DESCONOCIDO:
								try{
									CR.meVenta.ingresarLineaProducto(codigoScaner.substring(0,Control.getLONGITUD_CODIGO()-1),Sesion.capturaEscaner);
									CR.meServ.getCotizacion().modificarVendido(codigoScaner.substring(0,Control.getLONGITUD_CODIGO()-1));
								}catch(Exception ex1){
									logger.error("lecturaEscaner(String)", ex1);
		
									try {
										CR.meVenta.ingresarLineaProducto(codigoScaner, Sesion.capturaEscaner);
										CR.meServ.getCotizacion().modificarVendido(codigoScaner);
									} catch (Exception ex2) {
										logger.error("lecturaEscaner(String)", ex2);
		
										CR.inputEscaner.getDocument().removeDocumentListener(this);
										CR.meVenta.asignarCliente(codigo);
										CR.inputEscaner.getDocument().addDocumentListener(this);
									}
								}
						}
					}
					} catch (AfiliadoUsrExcepcion e1) {
						logger.error("lecturaEscaner(String)", e1);
		
						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (UsuarioExcepcion e1) {
						logger.error("lecturaEscaner(String)", e1);
		
						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (AutorizacionExcepcion e1) {
						logger.error("lecturaEscaner(String)", e1);
		
						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (ExcepcionCr e1) {
						logger.error("lecturaEscaner(String)", e1);
		
						MensajesVentanas.mensajeError("Código no registrado");
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (ConexionExcepcion e1) {
						logger.error("lecturaEscaner(String)", e1);
		
						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					}finally{
						setPermitirEscaner(true); //WDIAZ:02-07-2013 Controlar N Eventos con el escaner (Pantallas) simultaneamente
					}
					if ((jTable2!=null)&&(jTable2.getRowCount()>0))
						jTable2.setRowSelectionInterval(jTable2.getRowCount()-1,jTable2.getRowCount()-1);
			
			repintarPantalla();
		} else {
			CR.beep();
		}
		
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

	/**
	 * Método actualizarTipoEntregaDetalles.
	 */
	private void actualizarTipoEntregaDetalles(){
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarTipoEntregaDetalles() - start");
		}

		String tipoEntrega = "";
		int columnVenta = modeloTablaDetalleVenta.findColumn("Entrega");

		//Aquí llenamos un vector con los indices seleccionados de los comboBox 
		//que indican el tipo de entrega de los detalles
		try {
			for(int i=0; i<CR.meVenta.getVenta().getDetallesTransaccion().size(); i++) {
				tipoEntrega = (String)((Object[][])modeloTablaDetalleVenta.getDatos())[i][columnVenta];
				CR.meVenta.getVenta().actualizarTipoEntrega(i,tipoEntrega);
			}
		} catch(Exception e) {
			logger.error("actualizarTipoEntregaDetalles()", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarTipoEntregaDetalles() - end");
		}
	}
	class TablaPlus extends JTable {
		/**
		 * Logger for this class
		 */

		// Esta tabla es usada solo para la tabla de venta. A la otra no se le agregan registros
		TablaPlus(TableModel dm) {
			super(dm);
		}
	}
	
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
