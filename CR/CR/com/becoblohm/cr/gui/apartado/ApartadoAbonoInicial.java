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
 * Versi�n     : 
 * Fecha       : 
 * Analista    : 
 * Descripci�n : 
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.swing.JDialog;
import javax.swing.event.DocumentEvent;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.gui.PanelIconosCR;
import com.becoblohm.cr.gui.PanelMensajesCR;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstado;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.utiles.MensajesVentanas;

/**
 * @author gmartinelli
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ApartadoAbonoInicial extends JDialog implements ActionListener, MouseListener, KeyListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ApartadoAbonoInicial.class);

	private ModeloTablaApartado modeloTablaDetalleApartado = new ModeloTablaApartado(1);
	private DecimalFormat df = new DecimalFormat("#,##0.00");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
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
	/**
	 * This is the default constructor
	 */
	public ApartadoAbonoInicial() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		super(MensajesVentanas.ventanaActiva);
		initialize();

		this.setContentPane(getJContentPane());
			
		this.agregarListener();
		jButton12.setEnabled(false);
		
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
		this.setTitle("CR - Consulta de Apartados");
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

	private void quitarListener() {
		if (logger.isDebugEnabled()) {
			logger.debug("quitarListener() - start");
		}

		jScrollPane.removeKeyListener(this);
		jScrollPane.removeMouseListener(this);
		
		jTable.removeKeyListener(this);
		jTable.removeMouseListener(this);
		
		jButton3.removeKeyListener(this);
		jButton3.removeMouseListener(this);
		
		jButton4.removeKeyListener(this);
		jButton4.removeMouseListener(this);
		
		jButton5.removeKeyListener(this);
		jButton5.removeMouseListener(this);
		
		jButton6.removeKeyListener(this);
		jButton6.removeMouseListener(this);
		
		jButton7.removeKeyListener(this);
		jButton7.removeMouseListener(this);
		
		jButton11.removeKeyListener(this);
		jButton11.removeMouseListener(this);
		
		if (jComboBox!=null) {
			jComboBox.removeKeyListener(this);
			jComboBox.removeActionListener(this);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("quitarListener() - end");
		}
	}
	
	private void agregarListener() {
		if (logger.isDebugEnabled()) {
			logger.debug("agregarListener() - start");
		}

		jScrollPane.addKeyListener(this);
		jScrollPane.addMouseListener(this);
		
		jTable.addKeyListener(this);
		jTable.addMouseListener(this);
		
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
		
		jButton11.addKeyListener(this);
		jButton11.addMouseListener(this);
		
		if (jComboBox!=null) {
			jComboBox.addKeyListener(this);
			jComboBox.addActionListener(this);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("agregarListener() - end");
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
			jPanel.setName("ContenedorPrincipalIzquierdo");
			jPanel.add(PanelMensajesCR.getAreaMensajes(), null);
			jPanel.add(PanelMensajesCR.getPanelEstadoCaja(), null);
			jPanel.add(PanelMensajesCR.getPanelStatus(), null);
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
			jTable.getColumnModel().getColumn(1).setPreferredWidth(253);
			jTable.getColumnModel().getColumn(2).setPreferredWidth(65);
			jTable.getColumnModel().getColumn(3).setPreferredWidth(75);
			jTable.getColumnModel().getColumn(4).setPreferredWidth(76);
			jTable.getColumnModel().getColumn(5).setPreferredWidth(88);
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
			jLabel9.setPreferredSize(new java.awt.Dimension(300,40));
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
			jButton3.setText("F1 Agregar Abono");
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
			jButton4.setText("F3 Anular Apartado");
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
			jButton5.setText("F2 Anular Abono");
			jButton5.setMargin(new Insets(1,2,1,1));
			jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton5.setDefaultCapable(false);
			jButton5.setBackground(new java.awt.Color(226,226,222));
			jButton5.setEnabled(false);
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
			if (CR.meServ.getApartado().getEstadoServicio()==Sesion.APARTADO_PAGADO)
				jButton6.setEnabled(true);
			else
				jButton6.setEnabled(false);
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
			jButton7.setEnabled(false);
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
			jButton8.setEnabled(false);
			
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

		quitarListener();
		//Mapeo de F1 en eventos de Mouse
		if(e.getSource().equals(jButton3)){
			try {
				if (CR.meServ.getApartado().getCliente().getCodCliente() == null) {
					MensajesVentanas.aviso("Debe ingresar el afiliado");
				} else {
					if ((jComboBox!=null)&&(jComboBox.getSelectedIndex()<1)) {
						MensajesVentanas.aviso("Seleccione el Tipo de Abono Inicial");
						jComboBox.requestFocus();
					} else {
						if (CR.meServ.getApartado().getDetallesServicio().size()>0) {
							RegistrarAbono abonos;
							/*if (CR.meServ.recalculadoSaldoApartado()) {
								MensajesVentanas.aviso("Se Recalcular� el Saldo del Apartado.\nNuevas promociones vigentes");
								this.repintarFactura();
								abonos = new RegistrarAbono(CR.meServ.calcularMontoAbonoInicial(true), true, true);
							} else
								abonos = new RegistrarAbono(CR.meServ.calcularMontoAbonoInicial(true), false, true);
							*/
							//boolean recalculoApartado = CR.meServ.recalculadoSaldoApartado(1);
							boolean recalculoApartado = false;
							boolean recalculoPromocionesNuevas = CR.meServ.recalculadoSaldoApartado(3);
							boolean recalculoIva = CR.meServ.recalculadoSaldoApartado(2);
							if (recalculoIva) {
								if (recalculoApartado || recalculoPromocionesNuevas) {
									MensajesVentanas.aviso("Se Recalcular� el Saldo del Apartado.\nNueva Tasa y Nuevas promociones vigentes");
								} else {
									MensajesVentanas.aviso("Se Recalcular� el Saldo del Apartado.\nNueva Tasa Vigente");
								}
								this.repintarFactura();
								abonos = new RegistrarAbono(CR.meServ.getApartado().consultarMontoServ() - CR.meServ.getApartado().montoAbonos(), true,true);
							} else {
								if (recalculoApartado || recalculoPromocionesNuevas) {
									MensajesVentanas.aviso("Se Recalcular� el Saldo del Apartado.\nNuevas promociones vigentes");
									this.repintarFactura();
									abonos = new RegistrarAbono(CR.meServ.getApartado().consultarMontoServ() - CR.meServ.getApartado().montoAbonos(), true,true);
								} else {
									abonos = new RegistrarAbono(CR.meServ.getApartado().consultarMontoServ() - CR.meServ.getApartado().montoAbonos(), false,true);
								}
							}
							MensajesVentanas.centrarVentanaDialogo(abonos);
							if(abonos.isCanceladaEjecucion()) {
								this.repintarFactura();
							} else{
								dispose();
								CR.me.repintarMenuPrincipal();
							}
						}
						if (jComboBox!=null)
							CR.meServ.actualizarCondicionAbono(String.valueOf(jComboBox.getSelectedIndex()).toCharArray()[0]);
						else
							CR.meServ.actualizarCondicionAbono('1');							
						try {
							finalize();
						} catch (Throwable e1) {
							logger.error("mouseClicked(MouseEvent)", e1);
						}
					}
				}
				if (logger.isDebugEnabled()) {
					logger.debug("mouseClicked(MouseEvent) - F1");
				}
			} catch (ConexionExcepcion e1) {
				logger.error("mouseClicked(MouseEvent)", e1);

				MensajesVentanas.mensajeError(e1.getMensaje());
			} catch (ExcepcionCr e1) {
				logger.error("mouseClicked(MouseEvent)", e1);

				MensajesVentanas.mensajeError(e1.getMensaje());
			}
		}
		
		//Mapeo de F3 en Eventos de Mouse
		else if(e.getSource().equals(jButton4) &&(CR.meServ.getApartado().getEstadoServicio()==Sesion.APARTADO_ACTIVO || CR.meServ.getApartado().getEstadoServicio()==Sesion.APARTADO_VENCIDO)){
			int numApartado = CR.meServ.getApartado().getNumServicio(); //Obtenemos el n�mero del apartado para mostrarlo en el mensaje luego de ser anulado
			try {
				if (MensajesVentanas.preguntarSiNo("�Est� seguro que desea Anular el Apartado?")==0) {
					CR.meServ.anularApartado();
					MensajesVentanas.aviso("Anulado Apartado/Pedido Especial Nro. " + numApartado);
					dispose();
					CR.me.repintarMenuPrincipal();
				}
			} catch (ExcepcionCr e1) {
				logger.error("mouseClicked(MouseEvent)", e1);

				MensajesVentanas.mensajeError(e1.getMensaje());
			} catch (ConexionExcepcion e1) {
				logger.error("mouseClicked(MouseEvent)", e1);

				MensajesVentanas.mensajeError(e1.getMensaje());
			}
			if (logger.isDebugEnabled()) {
				logger.debug("mouseClicked(MouseEvent) - F3");
			}
		}
		
		//Mapeo de F9 en Eventos de Mouse
		else if(e.getSource().equals(jButton11)){
			if (MensajesVentanas.preguntarSiNo("�Seguro que desea cancelar \n la consulta actual?")==0) {
				try {
					CR.meServ.finalizarConsultaApartado();
				} catch (ConexionExcepcion e1) {
					logger.error("mouseClicked(MouseEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (ExcepcionCr e1) {
					logger.error("mouseClicked(MouseEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
				}
				dispose();	
				CR.me.repintarMenuPrincipal();
			}
			if (logger.isDebugEnabled()) {
				logger.debug("mouseClicked(MouseEvent) - F9");
			}
		}
		
		agregarListener();

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
	public void keyPressed(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}

		quitarListener();		
		//Mapeo de todas las teclas de funcion de la interfaz
		//Mapeo de F1
		if(e.getKeyCode() == KeyEvent.VK_F1){
			try {
				if (CR.meServ.getApartado().getCliente().getCodCliente() == null) {
					MensajesVentanas.aviso("Debe ingresar el afiliado");
				} else {
					if ((jComboBox!=null)&&(jComboBox.getSelectedIndex()<1)) {
						MensajesVentanas.aviso("Seleccione el Tipo de Abono Inicial");
						jComboBox.requestFocus();
					} else {
						if (CR.meServ.getApartado().getDetallesServicio().size()>0) {
							RegistrarAbono abonos;
							/*if (CR.meServ.recalculadoSaldoApartado()) {
								MensajesVentanas.aviso("Se Recalcular� el Saldo del Apartado\nNuevas promociones vigentes");
								this.repintarFactura();
								abonos = new RegistrarAbono(CR.meServ.calcularMontoAbonoInicial(true), true, true);
							} else
								abonos = new RegistrarAbono(CR.meServ.calcularMontoAbonoInicial(true), false, true);
							*/
							//boolean recalculoApartado = CR.meServ.recalculadoSaldoApartado(1);
							boolean recalculoApartado = false;
							boolean recalculoPromocionesNuevas = CR.meServ.recalculadoSaldoApartado(3);
							boolean recalculoIva = CR.meServ.recalculadoSaldoApartado(2);
							if (recalculoIva) {
								if (recalculoApartado || recalculoPromocionesNuevas) {
									MensajesVentanas.aviso("Se Recalcular� el Saldo del Apartado.\nNueva Tasa y Nuevas promociones vigentes");
								} else {
									MensajesVentanas.aviso("Se Recalcular� el Saldo del Apartado.\nNueva Tasa Vigente");
								}
								this.repintarFactura();
								abonos = new RegistrarAbono(CR.meServ.getApartado().consultarMontoServ() - CR.meServ.getApartado().montoAbonos(), true,true);
							} else {
								if (recalculoApartado || recalculoPromocionesNuevas) {
									MensajesVentanas.aviso("Se Recalcular� el Saldo del Apartado.\nNuevas promociones vigentes");
									this.repintarFactura();
									abonos = new RegistrarAbono(CR.meServ.getApartado().consultarMontoServ() - CR.meServ.getApartado().montoAbonos(), true,true);
								} else {
									abonos = new RegistrarAbono(CR.meServ.getApartado().consultarMontoServ() - CR.meServ.getApartado().montoAbonos(), false,true);
								}
							}
							MensajesVentanas.centrarVentanaDialogo(abonos);
							if(abonos.isCanceladaEjecucion()) {
								this.repintarFactura();
							} else{
								dispose();
								CR.me.repintarMenuPrincipal();
							}
						}
						if (jComboBox!=null)
							CR.meServ.actualizarCondicionAbono(String.valueOf(jComboBox.getSelectedIndex()).toCharArray()[0]);
						else
							CR.meServ.actualizarCondicionAbono('1');
							
						try {
							finalize();
						} catch (Throwable e1) {
							logger.error("keyPressed(KeyEvent)", e1);
						}
					}
				}
				if (logger.isDebugEnabled()) {
					logger.debug("keyPressed(KeyEvent) - F1");
				}
			} catch (ConexionExcepcion e1) {
				logger.error("keyPressed(KeyEvent)", e1);

				MensajesVentanas.mensajeError(e1.getMensaje());
			} catch (ExcepcionCr e1) {
				logger.error("keyPressed(KeyEvent)", e1);

				MensajesVentanas.mensajeError(e1.getMensaje());
			}
		}

		//Mapeo de F3
		else if((e.getKeyCode() == KeyEvent.VK_F3)&&(CR.meServ.getApartado().getEstadoServicio()==Sesion.APARTADO_ACTIVO || CR.meServ.getApartado().getEstadoServicio()==Sesion.APARTADO_VENCIDO)){
			int numApartado = CR.meServ.getApartado().getNumServicio(); //Obtenemos el n�mero del apartado para mostrarlo en el mensaje luego de ser anulado
			try {
				if (MensajesVentanas.preguntarSiNo("�Est� seguro que desea Anular el Apartado?")==0) {
					CR.meServ.anularApartado();
					MensajesVentanas.aviso("Anulado Apartado/Pedido Especial Nro. " + numApartado);
					dispose();
					CR.me.repintarMenuPrincipal();
				}
			} catch (ExcepcionCr e1) {
				logger.error("keyPressed(KeyEvent)", e1);

				MensajesVentanas.mensajeError(e1.getMensaje());
			} catch (ConexionExcepcion e1) {
				logger.error("keyPressed(KeyEvent)", e1);

				MensajesVentanas.mensajeError(e1.getMensaje());
			}
			if (logger.isDebugEnabled()) {
				logger.debug("keyPressed(KeyEvent) - F3");
			}
		}
		
		//Mapeo de F9
		else if(e.getKeyCode() == KeyEvent.VK_F9){
			if (MensajesVentanas.preguntarSiNo("�Seguro que desea cancelar \n la consulta actual?")==0) {			
				try {
					CR.meServ.finalizarConsultaApartado();
				} catch (ConexionExcepcion e1) {
					logger.error("keyPressed(KeyEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (ExcepcionCr e1) {
					logger.error("keyPressed(KeyEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
				}
				dispose();	
				CR.me.repintarMenuPrincipal();
			}
			if (logger.isDebugEnabled()) {
				logger.debug("keyPressed(KeyEvent) - F9");
			}
		}
		
		//Mapeo las teclas de Movimiento
		/*else if((e.getKeyCode() == KeyEvent.VK_RIGHT)||(e.getKeyCode() == KeyEvent.VK_LEFT)){
			this.getFocusOwner().transferFocus();
		}*/

		else if ((e.getKeyCode() == KeyEvent.VK_TAB)&&(e.getSource().equals(jTable))) {
			jComboBox.requestFocus();
		}

		else if (e.getSource().equals(jComboBox)) {
			if ((e.getKeyCode()!=KeyEvent.VK_DOWN)&&(e.getKeyCode()!=KeyEvent.VK_UP)) {
				jTable.requestFocus();
			}
		}
		
		agregarListener();

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
	 * M�todo repintarFactura.
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
				} else{
					nombre = new String(CR.meServ.getApartado().getCliente().getNombreCompleto());
					rif = new String(CR.meServ.getApartado().getCliente().getCodCliente());
					if(CR.meServ.getApartado().isApartadoExento()) {
						CR.me.setExento(true);
						CR.me.mostrarAviso("Cliente Exento de Impuestos\n", true);
					} else {
						CR.me.setExento(false);
					}
				}
			CR.me.setCliente(nombre, rif);
			
			getJTextField4().setText(Sesion.getTienda().getMonedaBase()+" " + String.valueOf(df.format(CR.meServ.getApartado().getMontoBase())));
			getJTextField5().setText(Sesion.getTienda().getMonedaBase()+" " + String.valueOf(df.format(CR.meServ.getApartado().getMontoImpuesto())));
			getJTextField6().setText(Sesion.getTienda().getMonedaBase()+" " + String.valueOf(df.format(CR.meServ.getApartado().getMontoBase() + CR.meServ.getApartado().getMontoImpuesto())));
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
			jLabel10.setText("Apartado Nro. " + CR.meServ.getApartado().getNumServicio());
		} catch (Exception ex){
			logger.error("repintarFactura()", ex);

			jLabel9.setText("Abono Inicial: "+Sesion.getTienda().getMonedaBase()+ df.format(0));
			CR.me.setExento(false);
			CR.me.setCliente("", "");
			getJTextField4().setText(df.format(0));
			getJTextField5().setText(df.format(0));
			getJTextField6().setText(df.format(0));
			jLabel10.setText("No.Art�culos: "+df.format(0));
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
			jLabel17.setPreferredSize(new java.awt.Dimension(140,19));
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
	 * M�todo changedUpdate
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
	 * M�todo insertUpdate
	 *
	 * @param e
	 */
	public void insertUpdate(DocumentEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("insertUpdate(DocumentEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("insertUpdate(DocumentEvent) - end");
		}
	}
	/**
	 * M�todo removeUpdate
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
