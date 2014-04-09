/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : PantallaDevolucion.java
 * Creado por : Gabriel Martinelli	<gmartinelli@beco.com.ve>
 * Creado en  : Mar 29, 2004 - 10:28:30 AM
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
package com.becoblohm.cr.gui.devolucion;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.extensiones.RegistroCliente;
import com.becoblohm.cr.extensiones.RegistroClienteFactory;
import com.becoblohm.cr.gui.Consultas;
import com.becoblohm.cr.gui.EjecutarConCantidad;
import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.gui.ModeloTabla;
import com.becoblohm.cr.gui.PanelIconosCR;
import com.becoblohm.cr.gui.PanelMensajesCR;
import com.becoblohm.cr.gui.Utilitarios;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstado;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarventa.CancelarDevolucion;
import com.becoblohm.cr.utiles.Control;
import com.becoblohm.cr.utiles.MensajesVentanas;

/**
 * @author vmedina
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PantallaVentaDevolucion extends JDialog implements ActionListener, MouseListener, KeyListener, DocumentListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(PantallaVentaDevolucion.class);

	private ModeloTabla modeloTablaDetalleFacturacion = new ModeloTabla(false);
	private ModeloTabla modeloTablaDetalleDevolucion = new ModeloTabla(false);
	private DecimalFormat df = new DecimalFormat("#,##0.00");
	private int filaVentaOriginal = 0;
	private int filaDevolucion = 0;
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
	private javax.swing.JScrollPane jScrollPane2 = null;
	private javax.swing.JTable jTable2 = null;
	private javax.swing.JPanel jPanel20 = null;
	private javax.swing.JLabel jLabel9 = null;
	private javax.swing.JLabel jLabel15 = null;
	private javax.swing.JLabel jLabel16 = null;
	private javax.swing.JLabel jLabel17 = null;
	private javax.swing.JLabel jLabel19 = null;
	private javax.swing.JEditorPane jEditorPane = null;
	
	private static EjecutarConCantidad cc = null;
	private static Utilitarios utilitarios = null;
	private PantallaDevXCambio dxc = null;
	private CancelarDevolucion hilo = null;
	
	RegistroClienteFactory factory = new RegistroClienteFactory();
	
	private double montoOriginal = 0;
	/**
	 * This is the default constructor
	 */
	public PantallaVentaDevolucion(int tda, int caja, int transaccion, Date fecha) {
		super(MensajesVentanas.ventanaActiva);
		initialize();
		
		jLabel15.setText(jLabel15.getText() + transaccion);
		jLabel16.setText(jLabel16.getText() + tda);
		jLabel17.setText(jLabel17.getText() + caja);
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		jLabel19.setText(jLabel19.getText() + df.format(fecha));
			
		jScrollPane.addKeyListener(this);
		jScrollPane.addMouseListener(this);
		
		jTable.addKeyListener(this);
		jTable.addMouseListener(this);
		
		jTable2.addKeyListener(this);
		jTable2.addMouseListener(this);
		
		jButton3.addKeyListener(this);
		jButton3.addActionListener(this);
		
		jButton4.addKeyListener(this);
		jButton4.addActionListener(this);
		
		jButton5.addKeyListener(this);
		jButton5.addActionListener(this);
		
		jButton6.addKeyListener(this);
		jButton6.addActionListener(this);
		
		jButton7.addKeyListener(this);
		jButton7.addActionListener(this);
		
		jButton8.addKeyListener(this);
		jButton8.addActionListener(this);
		
		jButton9.addKeyListener(this);
		jButton9.addActionListener(this);
		
		jButton11.addKeyListener(this);
		jButton11.addActionListener(this);
		
		jButton.addKeyListener(this);
		jButton.addActionListener(this);
		
		jButton12.setEnabled(false);

		this.repintarFactura();
		//this.getJTable().setRowSelectionInterval(0,0);
		
		this.filaVentaOriginal = 0;
		this.getJTable().requestFocus();
		
		montoOriginal = CR.meVenta.getDevolucion().getMontoBase() + CR.meVenta.getDevolucion().getMontoImpuesto();
		
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
		this.setTitle("CR - Devoluci\u00f3n por Cambio");
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
			jPanel.add(getJPanel14(), null);
			jPanel.add(getJPanel20(), null);
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
			jPanel1.add(getJButton(), null);
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
			layFlowLayout16.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout16.setHgap(1);
			layFlowLayout16.setVgap(1);
			jPanel14.setLayout(layFlowLayout16);
			jPanel14.add(getJScrollPane(), null);
			jPanel14.setPreferredSize(new java.awt.Dimension(656,125));
			jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Productos Disponibles Para Devolver: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), java.awt.Color.black));
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
			jTable = new javax.swing.JTable(modeloTablaDetalleFacturacion);
			jTable.getTableHeader().setReorderingAllowed(false) ;
			jTable.getColumnModel().getColumn(0).setPreferredWidth(88);
			jTable.getColumnModel().getColumn(1).setPreferredWidth(150);
			jTable.getColumnModel().getColumn(2).setPreferredWidth(65);
			jTable.getColumnModel().getColumn(6).setPreferredWidth(35);
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
			jScrollPane.setPreferredSize(new java.awt.Dimension(645,100));
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
			jPanel15.add(getJLabel17(), null);
			jPanel15.add(getJLabel16(), null);
			jPanel15.add(getJLabel19(), null);
			jPanel15.setPreferredSize(new java.awt.Dimension(328,150));
			jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Devolución: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), java.awt.Color.black));
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
			jLabel10.setText("Nota de Crédito");
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
			jButton3.setText("F1 Devolver Producto");
			jButton3.setPreferredSize(new java.awt.Dimension(120,40));
			jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton3.setDefaultCapable(false);
			jButton3.setMargin(new Insets(1,2,1,1));
			jButton3.setBackground(new java.awt.Color(226,226,222));
			jButton3.setEnabled(true);
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
			jButton4.setText("F3 Devolver Todo");
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
				jButton5.setText("F2 Quitar Producto");
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
			jButton8.setEnabled(true);
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
		
		//Mapeo de F1 en eventos de Mouse
		if(e.getSource().equals(jButton3)){
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			if(jTable.getSelectedRow() != -1) {
				filaVentaOriginal = jTable.getSelectedRow();
				cc = null;
				cc = new EjecutarConCantidad("Devolución de Producto: " + (String)jTable.getValueAt(filaVentaOriginal,0),filaVentaOriginal,new Float((CR.me.formatoNumerico((String)jTable.getValueAt(filaVentaOriginal,2)))).floatValue(),1);
				MensajesVentanas.centrarVentanaDialogo(cc);
				this.repintarFactura();
				if (jTable2.getRowCount()>0)
					this.getJTable2().setRowSelectionInterval(jTable2.getRowCount()-1,jTable2.getRowCount()-1);
				if (jTable.getRowCount()>0) {
					this.getJTable().setRowSelectionInterval(0,0);
					this.getJTable().requestFocus();
				} else
					this.getJTable2().requestFocus();		
			} else {
				MensajesVentanas.aviso("Debe seleccionar un producto del detalle original");
			}
			CR.inputEscaner.getDocument().addDocumentListener(this);
			if (logger.isDebugEnabled()) {
				logger.debug("mouseClicked(MouseEvent) - F1");
			}		
		}
		
		//Mapeo de F2 en eventos de Mouse
		if(e.getSource().equals(jButton5)){
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			if(jTable2.getSelectedRow() != -1) {
				filaDevolucion = jTable2.getSelectedRow();
				cc = null;
				cc = new EjecutarConCantidad("Anulación de Producto: " + (String)jTable2.getValueAt(filaDevolucion,0),filaDevolucion,new Float((CR.me.formatoNumerico((String)jTable2.getValueAt(filaDevolucion,2)))).floatValue(),2);
				MensajesVentanas.centrarVentanaDialogo(cc);
				this.repintarFactura();
				if (jTable.getRowCount()>0)
					this.getJTable().setRowSelectionInterval(jTable.getRowCount()-1,jTable.getRowCount()-1);
				if (jTable2.getRowCount()>0) {
					this.getJTable2().setRowSelectionInterval(0,0);
					this.getJTable2().requestFocus();
				} else
					this.getJTable().requestFocus();
			} else {
				MensajesVentanas.aviso("Debe seleccionar un producto del detalle de la devolucion");
			}
			CR.inputEscaner.getDocument().addDocumentListener(this);
			if (logger.isDebugEnabled()) {
				logger.debug("mouseClicked(MouseEvent) - F2");
			}
		}

		//Mapeo de F3 en Eventos de Mouse
		if(e.getSource().equals(jButton4)){
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			try {
				if (this.getJTable().getRowCount()>0) {
					CR.meVenta.devolucionTotal();
					this.repintarFactura();
					if (this.getJTable2().getRowCount()>0) {
						this.getJTable2().setRowSelectionInterval(0,0);
						this.getJTable2().requestFocus();
					}
				} else {
					MensajesVentanas.aviso("No exiten productos para devolver\nEl Detalle Original no tiene productos");
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
			CR.inputEscaner.getDocument().addDocumentListener(this);
			if (logger.isDebugEnabled()) {
				logger.debug("mouseClicked(MouseEvent) - F3");
			}
		}
		
		//Mapeo de F4 en eventos de Mouse
		if(e.getSource().equals(jButton6)){
			if (CR.meVenta.getDevolucion().getCliente().getCodCliente() != null){
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				
				if (CR.meVenta.getDevolucion().getDetallesTransaccion().size() > 0){
					dispose();
					dxc = null;
					dxc = new PantallaDevXCambio(montoOriginal,CR.meVenta.getDevolucion().getMontoBase() + CR.meVenta.getDevolucion().getMontoImpuesto());
					MensajesVentanas.centrarVentanaDialogo(dxc);
				} else {
					MensajesVentanas.aviso("No ha seleccionado ningún producto para la devolución");
				}
			} else {
				MensajesVentanas.mensajeError("Debe asignar un cliente antes de finalizar la devolución");
			}
			if (logger.isDebugEnabled()) {
				logger.debug("mouseClicked(MouseEvent) - F4");
			}
		}
		
		//Mapeo de F5 en eventos de Mouse
		else if(e.getSource().equals(jButton7)){
			CR.inputEscaner.getDocument().removeDocumentListener(this);
		//	try {
		//		CR.me.consultaProductoCliente();
				Consultas consultas = null;
				consultas = new Consultas();
				MensajesVentanas.centrarVentanaDialogo(consultas);
				String casoConsulta = consultas.getCodigoSeleccionado()[0];
				String codigo = consultas.getCodigoSeleccionado()[1];
				if (casoConsulta!=null) {
					try {
						if (new Integer(casoConsulta).intValue()<8) {
							MensajesVentanas.aviso("No se pueden ingresar productos en una devolución");
						} else {
							CR.meVenta.asignarClienteDevolucion(codigo);
						}
					} catch (ExcepcionCr e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (ConexionExcepcion e1) {
						logger.error("mouseClicked(MouseEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
					}
				}

			this.repintarFactura();			
			if (logger.isDebugEnabled()) {
				logger.debug("mouseClicked(MouseEvent) - F5");
			}
			CR.inputEscaner.getDocument().addDocumentListener(this);
		}
		
//		Mapeo de F6
		if(e.getSource().equals(jButton9)){
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			if (CR.meVenta.getDevolucion().getCliente().getCodCliente() != null){
				if (CR.meVenta.getDevolucion().getDetallesTransaccion().size() > 0){
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
				} else {
					MensajesVentanas.aviso("No ha seleccionado ningún producto para la devolución");
					CR.inputEscaner.getDocument().addDocumentListener(this);
				}
			} else {
				MensajesVentanas.mensajeError("Debe asignar un cliente antes de finalizar la devolución");
				CR.inputEscaner.getDocument().addDocumentListener(this);
			}
		}
		
		//Mapeo de F8
		if(e.getSource().equals(jButton8)){
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			utilitarios = null;
			utilitarios = new Utilitarios();
			MensajesVentanas.centrarVentanaDialogo(utilitarios);
			this.repintarFactura();
			CR.inputEscaner.getDocument().addDocumentListener(this);
		}
		
		//Mapeo de F9 en Eventos de Mouse
		if(e.getSource().equals(jButton11)){
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			if (MensajesVentanas.preguntarSiNo(Sesion.MSG_CANCELAR)==0) {
				try{
					int tienda = CR.meVenta.getDevolucion().getVentaOriginal().getCodTienda();
					SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy-MM-dd");
					String fechaTransString = fechaTrans.format(CR.meVenta.getDevolucion().getVentaOriginal().getFechaTrans());
					int caja =  CR.meVenta.getDevolucion().getVentaOriginal().getNumCajaFinaliza();
					int transaccion = CR.meVenta.getDevolucion().getVentaOriginal().getNumTransaccion();
					CR.meVenta.anularDevolucionActiva();
					if (CR.meVenta.getVenta()!=null) CR.meVenta.anularVentaActiva();
					hilo = new CancelarDevolucion(tienda, fechaTransString, caja, transaccion);
					hilo.start();
					dispose();
					CR.me.repintarMenuPrincipal();
				} catch(Exception f){
					logger.error("mouseClicked(MouseEvent)", f);
					CR.inputEscaner.getDocument().addDocumentListener(this);
				}
			}			
			if (logger.isDebugEnabled()) {
				logger.debug("mouseClicked(MouseEvent) - F9");
			}
		}
		
			else if(e.getSource().equals(jButton)) {
/************************************************/		
						
				if (jButton.getText().equals("Asignar Clientes")){
//					llamada a la extension de asigna/registrar cliente en CR  02/2009 Wdiaz
					RegistroCliente registro = factory.getInstance();
					registro.MostrarPantallaCliente(false,5);
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
			//	eventoDeIngresoProducto = true;
				this.repintarFactura();

					
				
/************************************************/
			}
		
		if (e.getSource().equals(jTable)) {
			if(jTable.getSelectedRow() == -1){
			}
			else{
				filaVentaOriginal = jTable.getSelectedRow();
			}			
		}

		if (e.getSource().equals(jTable2)) {
			if(jTable2.getSelectedRow() == -1){
			}
			else{
				filaDevolucion = jTable2.getSelectedRow();
			}			
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
		
		if (e.getClickCount()>=2) {
			if (e.getSource().equals(getJTable())) {
				// Doble Click en la tabla de Arriba
				jButton3.doClick();
			} else {
				if (e.getSource().equals(getJTable2())) {
					// Doble Click en la tabla de Abajo
					jButton5.doClick();
				}
			}
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

		//Mapeo de todas las teclas de funcion de la interfaz
		//Mapeo de F1
		if(e.getKeyCode() == KeyEvent.VK_F1) {
			this.getJButton3().doClick();
		}
		
		//Mapeo de F2
		if(e.getKeyCode() == KeyEvent.VK_F2){
			this.getJButton5().doClick();
		}

		//Mapeo de F3
		if(e.getKeyCode() == KeyEvent.VK_F3){
			this.getJButton4().doClick();
		}
		
		//Mapeo de F4
		if(e.getKeyCode() == KeyEvent.VK_F4){
			this.getJButton6().doClick();
		}
		
		//Mapeo de F5
		else if(e.getKeyCode() == KeyEvent.VK_F5){
			this.getJButton7().doClick();
		}
		
		//Mapeo de F6
		else if(e.getKeyCode() == KeyEvent.VK_F6){
			this.getJButton9().doClick();
		}
		
		//Mapeo de F8
		if(e.getKeyCode() == KeyEvent.VK_F8){
			this.getJButton8().doClick();
		}
		
		//Mapeo de F9
		if(e.getKeyCode() == KeyEvent.VK_F9){
			this.getJButton11().doClick();
		}			

		// Mapeamos cualquier evento con la tecla TAB en las tablas para movernos por ellas
		if (e.getSource().equals(jTable)) {
			if ((e.getKeyCode() == KeyEvent.VK_TAB)) {
				jTable2.requestFocus();
			}
			if ((e.getKeyCode() == KeyEvent.VK_DOWN)||(e.getKeyCode() == KeyEvent.VK_NUMPAD2))
				if ((jTable.getSelectedRow() == jTable.getRowCount()-1)||(jTable2.getRowCount()<1)) {
					if (jTable2.getRowCount()>0) {
						jTable2.requestFocus();
						jTable2.setRowSelectionInterval(0,0);
					} else
						jTable.requestFocus();
				}
		}
		if (e.getSource().equals(jTable2)) {
			if ((e.getKeyCode() == KeyEvent.VK_TAB)) {
				jTable.requestFocus();
			}
			if ((e.getKeyCode() == KeyEvent.VK_UP)||(e.getKeyCode() == KeyEvent.VK_NUMPAD8))
				if ((jTable2.getSelectedRow() == 0)||(jTable2.getRowCount()<1)) {
					if (jTable.getRowCount()>0) {
						jTable.requestFocus();
						jTable.setRowSelectionInterval(jTable.getRowCount()-1,jTable.getRowCount()-1);
					} else
						jTable2.requestFocus();
				}
		}

		if(e.getKeyCode() == KeyEvent.VK_A) {
			this.getJButton().doClick();
		}				
				
		//Verificamos que se presionó un número
		if ((e.getKeyChar()=='1')||(e.getKeyChar()=='2')||(e.getKeyChar()=='3')||(e.getKeyChar()=='4')||(e.getKeyChar()=='5')
				||(e.getKeyChar()=='6')||(e.getKeyChar()=='7')||(e.getKeyChar()=='8')||(e.getKeyChar()=='9')||(e.getKeyChar()=='0')) {
				// Mostramos una ventana para que el usuario introduzca el código del producto
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				cc =null;
				cc = new EjecutarConCantidad("Asignar un Cliente:", String.valueOf(e.getKeyChar()),5);
				MensajesVentanas.centrarVentanaDialogo(cc);
				CR.inputEscaner.getDocument().addDocumentListener(this);
				this.repintarFactura();
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
			MaquinaDeEstado.setEstadoCaja(Sesion.getCaja().getEstado());
			modeloTablaDetalleFacturacion.llenarTablaVentaOriginal();
			modeloTablaDetalleDevolucion.llenarTablaDevolucion();
			if (CR.meVenta.getDevolucion() != null){
				String nombre, rif;
				if (CR.meVenta.getDevolucion().getCliente().getCodCliente() == null){
					nombre = "";
					rif = "";
					jButton.setText("Asignar Clientes");
				} else{
					jButton.setText("Quitar Cliente Asignado");
					nombre = new String(CR.meVenta.getDevolucion().getCliente().getNombreCompleto());
					rif = new String(CR.meVenta.getDevolucion().getCliente().getCodCliente());
					if(CR.meVenta.getDevolucion().getVentaOriginal().getMontoImpuesto() == 0 && CR.meVenta.getDevolucion().getCliente().isExento()) {
						CR.me.setExento(true);
						getJEditorPane().setText("Cliente Exento de Impuestos\n");
					} else {
						CR.me.setExento(false);
					}
				}
				CR.me.setCliente(nombre, rif);
			}
			getJTextField4().setText(Sesion.getTienda().getMonedaBase()+" "+String.valueOf(df.format(CR.meVenta.getDevolucion().getMontoBase())));
			getJTextField5().setText(Sesion.getTienda().getMonedaBase()+" "+String.valueOf(df.format(CR.meVenta.getDevolucion().getMontoImpuesto())));
			getJTextField6().setText(Sesion.getTienda().getMonedaBase()+" "+String.valueOf(df.format(CR.meVenta.getDevolucion().getMontoBase() + CR.meVenta.getDevolucion().getMontoImpuesto())));
		} catch (Exception ex){
			logger.error("repintarFactura()", ex);

			CR.me.setExento(false);
			CR.me.setCliente("", "");
			getJTextField4().setText(df.format(0));
			getJTextField5().setText(df.format(0));
			getJTextField6().setText(df.format(0));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("repintarFactura() - end");
		}
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
			jTable.getTableHeader().setReorderingAllowed(false) ;
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
			jLabel9.setText("Realizando Devolución Sobre ");
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
			jLabel15.setText("Transacción Nro. ");
			jLabel15.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel15.setPreferredSize(new java.awt.Dimension(280,24));
			jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel15() - end");
		}
		return jLabel15;
	}
	/**
	 * This method initializes jLabel16
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel16() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel16() - start");
		}

		if(jLabel16 == null) {
			jLabel16 = new javax.swing.JLabel();
			jLabel16.setText("de Tienda ");
			jLabel16.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel16() - end");
		}
		return jLabel16;
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
			jLabel17.setText("Caja ");
			jLabel17.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel17() - end");
		}
		return jLabel17;
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
			jLabel19.setText("el día ");
			jLabel19.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel19() - end");
		}
		return jLabel19;
	}
	/**
	 * This method initializes jEditorPane
	 * 
	 * @return javax.swing.JEditorPane
	 */
	private javax.swing.JEditorPane getJEditorPane() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJEditorPane() - start");
		}

		if(jEditorPane == null) {
			jEditorPane = new javax.swing.JEditorPane();
			jEditorPane.setPreferredSize(new java.awt.Dimension(654,45));
			jEditorPane.setFocusable(false);
			jEditorPane.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJEditorPane() - end");
		}
		return jEditorPane;
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

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (logger.isDebugEnabled()) {
					logger.debug("run() - start");
				}

				lecturaEscaner(CR.inputEscaner.getText().trim());

				if (logger.isDebugEnabled()) {
					logger.debug("run() - end");
				}
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("insertUpdate(DocumentEvent) - end");
		}
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Se parametrizó el tipo de dato de los distintos ArrayList y
	* se comentaron variables sin uso
	* Fecha: agosto 2011
	*/
	public void lecturaEscaner(String valor) {
		if (logger.isDebugEnabled()) {
			logger.debug("lecturaEscaner(String) - start");
		}

		try {
			ArrayList <Object>valorLeido = Control.codigoValido(valor);
			//int tipoCodigo = ((Integer)valorLeido.get(0)).intValue();
			String codigo = (String)valorLeido.get(1);
			//String tipoCaptura = (String)valorLeido.get(2);
			CR.meVenta.asignarClienteDevolucion(codigo);
			this.repintarFactura();
		} catch (ConexionExcepcion e1) {
			logger.error("lecturaEscaner(String)", e1);

			MensajesVentanas.mensajeError(e1.getMensaje());
			this.repintarFactura();
		} catch (ExcepcionCr e1) {
			logger.error("lecturaEscaner(String)", e1);

			MensajesVentanas.mensajeError(e1.getMensaje());
			this.repintarFactura();
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
}