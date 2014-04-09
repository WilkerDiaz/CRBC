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
import java.util.Calendar;

import javax.swing.JDialog;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.AnulacionExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.extensiones.ActualizadorPrecios;
import com.becoblohm.cr.extensiones.ActualizadorPreciosFactory;
import com.becoblohm.cr.extensiones.ActualizadorPreciosSACFactory;
import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.gui.PanelIconosCR;
import com.becoblohm.cr.gui.PanelMensajesCR;
import com.becoblohm.cr.gui.Utilitarios;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstado;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarservicio.Abono;
import com.becoblohm.cr.manejarservicio.DetalleServicio;
import com.becoblohm.cr.utiles.MensajesVentanas;

/**
 * @author vmedina
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PantallaApartado extends JDialog implements ActionListener, MouseListener, KeyListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(PantallaApartado.class);

	private ModeloTablaApartado modeloTablaDetalleApartado = new ModeloTablaApartado(1);
	private ModeloTablaApartado modeloTablaDetalleAbonos = new ModeloTablaApartado(2);
	private ModeloTablaApartado modeloTablaDetallePagosAbono = new ModeloTablaApartado(3);
	private DecimalFormat df = new DecimalFormat("#,##0.00");
	private int filaAbono = 0;
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
	private javax.swing.JButton jButton10 = null;
	private javax.swing.JButton jButton8 = null;
	private javax.swing.JButton jButton9 = null;
	private javax.swing.JButton jButton11 = null;
	private javax.swing.JButton jButton12 = null;
	private javax.swing.JButton jButton13 = null;
	private javax.swing.JButton jButton14 = null;
	private javax.swing.JScrollPane jScrollPane2 = null;
	private javax.swing.JTable jTable2 = null;
	private javax.swing.JPanel jPanel20 = null;
	private javax.swing.JScrollPane jScrollPane1 = null;
	private javax.swing.JTable jTable1 = null;
	private javax.swing.JLabel jLabel15 = null;
	private javax.swing.JTextField jTextField3 = null;
	private javax.swing.JLabel jLabel16 = null;
	private javax.swing.JTextField jTextField7 = null;
	private javax.swing.JLabel jLabel17 = null;
	private javax.swing.JLabel jLabel18 = null;
	
	private static RegistrarAbono abonos = null;
	private static ConsultaAbono ca = null;
	
	/**
	 * This is the default constructor
	 */
	public PantallaApartado() {
		super(MensajesVentanas.ventanaActiva);
		double prueba = CR.meServ.getApartado().consultarMontoServ()-CR.meServ.getApartado().montoAbonos();
		System.out.println(prueba<=0.001);
		if ((CR.meServ.getApartado().consultarMontoServ()-CR.meServ.getApartado().montoAbonos())<=.001)
			CR.meServ.getApartado().setEstadoServicio(Sesion.APARTADO_PAGADO);
		initialize();
		
		jButton7.setEnabled(false);
		
		jScrollPane.addKeyListener(this);
		jScrollPane.addMouseListener(this);
		
		jScrollPane1.addKeyListener(this);
		jScrollPane1.addMouseListener(this);
				
		jScrollPane2.addKeyListener(this);
		jScrollPane2.addMouseListener(this);
		
		jTable.addKeyListener(this);
		jTable.addMouseListener(this);
		
		jTable2.addKeyListener(this);
		jTable2.addMouseListener(this);
		
		jTable1.addKeyListener(this);
		jTable1.addMouseListener(this);
		
		jButton3.addKeyListener(this);
		jButton3.addMouseListener(this);
				
		jButton4.addKeyListener(this);
		jButton4.addMouseListener(this);
		
		jButton5.addKeyListener(this);
		jButton5.addMouseListener(this);
		
		if (CR.meServ.getApartado().getEstadoServicio()==Sesion.APARTADO_PAGADO) {
			jButton6.addKeyListener(this);
			jButton6.addMouseListener(this);
		}
		
		jButton11.addKeyListener(this);
		jButton11.addMouseListener(this);
		
		jButton8.addMouseListener(this);
		jButton8.addKeyListener(this);
		
		jButton12.setEnabled(false);
		
		
		this.getJLabel17().setVisible(false);
		this.getJLabel18().setVisible(false);
		
		this.repintarFactura();
		this.repintarEtiquetasVuelto();
		this.getJTable2().setRowSelectionInterval(0,0);
		this.getJTable().requestFocus();

		// Mostramos la fecha de vencimiento del Apartado		
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		Calendar fechaVencimiento = Calendar.getInstance();
		if(CR.meServ.getApartado().getTipoApartado()==0){
			fechaVencimiento.setTime(CR.meServ.getApartado().getFechaServicio());
			if (CR.meServ.getApartado().getTipoVigencia().equalsIgnoreCase("Dia")) {
				fechaVencimiento.add(Calendar.DATE,CR.meServ.getApartado().getTiempoVigencia());
			} else {
				if (CR.meServ.getApartado().getTipoVigencia().equalsIgnoreCase("Mes")) {
					fechaVencimiento.add(Calendar.MONTH,CR.meServ.getApartado().getTiempoVigencia());
				}
			}
		} else {
			fechaVencimiento.setTime(CR.meServ.getApartado().getFechaVencimiento());
			CR.me.mostrarAviso("Apartado de tipo \""+CR.meServ.getApartado().getDescripcionTipoApartado()+"\" ",true);
		}
		if(CR.meServ.getApartado().getTipoApartado()==0)
			CR.meServ.getApartado().setFechaVencimiento(fechaVencimiento.getTime());
		
		CR.me.mostrarAviso("Fecha vencimiento: " + formatoFecha.format(fechaVencimiento.getTime()), true);

		if (CR.meServ.getApartado().getEstadoServicio() == Sesion.APARTADO_VENCIDO) {
			//Se arreglan las etiquetas referentes al estado del apartado vencido
			CR.me.setCalendario(true);
			CR.me.mostrarAviso("El apartado se encuentra vencido.", true);
			jLabel10.setText("Apartado Nro. " + CR.meServ.getApartado().getNumServicio() + " __ VENCIDO __ ");
		}
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
		this.setName("FrameApartado");
		this.setVisible(false);
		this.setTitle("CR - Consulta de Apartados / Pedidos Especiales");
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
			jContentPane.setName("AreaDeApartado");
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
			jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle del Apartado: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), java.awt.Color.black));
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
			jTable.clearSelection();
			jTable.setEnabled(true);
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
			layFlowLayout17.setHgap(3);
			layFlowLayout17.setVgap(1);
			layFlowLayout17.setAlignment(java.awt.FlowLayout.RIGHT);
			jPanel15.setLayout(layFlowLayout17);
			jPanel15.add(getJScrollPane1(), null);
			jPanel15.add(getJLabel17(), null);
			jPanel15.add(getJLabel18(), null);
			jPanel15.setPreferredSize(new java.awt.Dimension(328,150));
			jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pagos del Abono: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), java.awt.Color.black));
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
			layGridLayout11.setRows(5);
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
			jPanel19.add(getJLabel15(), null);
			jPanel19.add(getJTextField3(), null);
			jPanel19.add(getJLabel16(), null);
			jPanel19.add(getJTextField7(), null);
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
			jLabel11.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jLabel11.setPreferredSize(new java.awt.Dimension(160,18));
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
			jTextField4.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jTextField4.setBackground(new java.awt.Color(242,242,238));
			jTextField4.setPreferredSize(new java.awt.Dimension(160,18));
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
			jLabel12.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jLabel12.setPreferredSize(new java.awt.Dimension(160,18));
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
			jTextField5.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jTextField5.setText(df.format(0));
			jTextField5.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			jTextField5.setBorder(javax.swing.BorderFactory.createMatteBorder(0,0,1,0,(new java.awt.Color(0,0,0))));
			jTextField5.setBackground(new java.awt.Color(242,242,238));
			jTextField5.setPreferredSize(new java.awt.Dimension(160,18));
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
			jLabel13.setPreferredSize(new java.awt.Dimension(160,18));
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
			jTextField6.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jTextField6.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			jTextField6.setEditable(false);
			jTextField6.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			jTextField6.setText(df.format(0));
			jTextField6.setBackground(new java.awt.Color(242,242,238));
			jTextField6.setPreferredSize(new java.awt.Dimension(160,18));
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
			jButton4.setActionCommand("F3 Anular Apartado");
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
			
			//Actualización CENTROBECO: JGRATEROL
			//Botón deshabilitado para detectar casos de apartados en C
			/*if (CR.meServ.getApartado().getEstadoServicio()==Sesion.APARTADO_PAGADO)
				jButton6.setEnabled(true);
			else
				jButton6.setEnabled(false);*/
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
		try{
		if((e.getSource().equals(jButton3))&&(CR.meServ.getApartado().getEstadoServicio()==Sesion.APARTADO_ACTIVO || CR.meServ.getApartado().getEstadoServicio()==Sesion.APARTADO_VENCIDO)){
			//RegistrarAbono abonos;
			abonos = null;
			//boolean recalculoApartado = CR.meServ.recalculadoSaldoApartado(1);
			boolean recalculoApartado = false;
			boolean recalculoPromocionesNuevas = CR.meServ.recalculadoSaldoApartado(3);
			boolean recalculoIva = CR.meServ.recalculadoSaldoApartado(2);
			if (recalculoIva) {
				if (recalculoApartado || recalculoPromocionesNuevas) {
					MensajesVentanas.aviso("Se Recalculará el Saldo del Apartado.\nNueva Tasa y Nuevas promociones vigentes");
					jButton4.setEnabled(false);
					jButton8.setEnabled(false);
				} else {
					MensajesVentanas.aviso("Se Recalculará el Saldo del Apartado.\nNueva Tasa Vigente");
					jButton4.setEnabled(false);
					jButton8.setEnabled(false);
				}
				this.repintarFactura();
				abonos = new RegistrarAbono(CR.meServ.getApartado().consultarMontoServ() - CR.meServ.getApartado().montoAbonos(), true,true);
			} else {
				if (recalculoApartado || recalculoPromocionesNuevas) {
					MensajesVentanas.aviso("Se Recalculará el Saldo del Apartado.\nNuevas promociones vigentes");
					this.repintarFactura();
					abonos = new RegistrarAbono(CR.meServ.getApartado().consultarMontoServ() - CR.meServ.getApartado().montoAbonos(), true,true);
					jButton4.setEnabled(false);
					jButton8.setEnabled(false);
				} else {
					abonos = new RegistrarAbono(CR.meServ.getApartado().consultarMontoServ() - CR.meServ.getApartado().montoAbonos(), false,true);
				}
			}
			
			MensajesVentanas.centrarVentanaDialogo(abonos);
			if(abonos.isCanceladaEjecucion()) {
			this.repintarFactura();
			this.repintarEtiquetasVuelto();
			} else{
				dispose();
				CR.me.repintarMenuPrincipal();
			}
		}
			
		//Mapeo de F2 en eventos de Mouse
		if(jButton5.isEnabled() && ((e.getSource().equals(jButton5))&&(CR.meServ.getApartado().getEstadoServicio()==Sesion.APARTADO_ACTIVO || CR.meServ.getApartado().getEstadoServicio()==Sesion.APARTADO_VENCIDO))){
			filaAbono = jTable2.getSelectedRow();
			Abono abono = (Abono)CR.meServ.getApartado().getAbonos().elementAt(filaAbono);
			
			if(abono.getEstadoAbono() == Sesion.ABONO_ANULADO) {
				MensajesVentanas.aviso(" El abono ya ha sido anulado ");
			} else {
				ConsultaAbono ca = new ConsultaAbono(filaAbono, true);
				MensajesVentanas.centrarVentanaDialogo(ca);
				if(ca.isCanceladaEjecucion()) {
				this.repintarFactura();
				this.repintarEtiquetasVuelto();	
				} else{
					dispose();	
					CR.me.repintarMenuPrincipal();
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("mouseClicked(MouseEvent) - F2");
			}
		}
		
		//Mapeo de F3 en Eventos de Mouse
		if(jButton4.isEnabled() && ((e.getSource().equals(jButton4))&&(CR.meServ.getApartado().getEstadoServicio()==Sesion.APARTADO_ACTIVO || CR.meServ.getApartado().getEstadoServicio()==Sesion.APARTADO_VENCIDO))){
			int numApartado = CR.meServ.getApartado().getNumServicio(); //Obtenemos el número del apartado para mostrarlo en el mensaje luego de ser anulado
			try {
				if (MensajesVentanas.preguntarSiNo("¿Está seguro que desea Anular el Apartado?")==0) {
						CR.meServ.anularApartado();
						MensajesVentanas.aviso("Anulado Apartado/Pedido Especial Nro. " + numApartado);
						dispose();
						CR.me.repintarMenuPrincipal();
				}
	
			} catch (AnulacionExcepcion e1) {
				logger.error("mouseClicked(MouseEvent)", e1);		
				//Al hacer click al botón cancelar en ventana de cargo por servicio
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
		
		//Mapeo de F4 en eventos de Mouse
		if(e.getSource().equals(jButton6)&&(CR.meServ.getApartado().getEstadoServicio()==Sesion.APARTADO_PAGADO)&&getJButton6().isEnabled()) {
			try {
				if (MensajesVentanas.preguntarSiNo("¿Está seguro que desea Facturar el Apartado?")==0) {
					CR.meVenta.crearVentaApartado(CR.meServ.getApartado());
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
				logger.debug("mouseClicked(MouseEvent) - F4");
			}
		}
		
		//Mapeo de F9 en eventos de Mouse
		if(e.getSource().equals(jButton11)){
			if (MensajesVentanas.preguntarSiNo(Sesion.MSG_CANCELAR)==0) {
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
		
		//Mapeo de F8 en eventos de Mouse
		if(jButton8.isEnabled() && e.getSource().equals(jButton8)){
			
			Utilitarios ventanaUtilitarios = new Utilitarios();
			MensajesVentanas.centrarVentanaDialogo(ventanaUtilitarios);
			this.repintarFactura();		
			if (logger.isDebugEnabled()) {
				logger.debug("mouseClicked(MouseEvent) - F8");
			}
		}

		if (e.getSource().equals(jTable2)) {
			if(jTable2.getSelectedRow() == -1){
			}
			else{
				filaAbono = jTable2.getSelectedRow();
				modeloTablaDetallePagosAbono.llenarTablaPagosAbono(filaAbono);
				this.getJTable1().setRowSelectionInterval(0,0);
				this.repintarEtiquetasVuelto();
			}
		}
		if(e.getSource().equals(jTable)){
			if(e.getClickCount()==2){
				ActualizadorPreciosFactory  factory = new ActualizadorPreciosFactory();
				ActualizadorPrecios actualizadorPrecios = factory.getActualizadorPreciosInstance();		
				actualizadorPrecios.abrirVentanaCondicionesVenta(((DetalleServicio)(CR.meServ.getApartado().getDetallesServicio().elementAt(jTable.getSelectedRow()))).getCondicionesVentaPromociones());
			}
		}
		} catch(Exception e1){
			MensajesVentanas.mensajeError("Error en el recálculo del apartado. Por favor llamar a Soporte");
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
	public void keyPressed(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}

		try{
		//Mapeo de todas las teclas de funcion de la interfaz
		if((e.getKeyCode() == KeyEvent.VK_F1)&&(CR.meServ.getApartado().getEstadoServicio()==Sesion.APARTADO_ACTIVO || CR.meServ.getApartado().getEstadoServicio()==Sesion.APARTADO_VENCIDO)){
			abonos = null;
			//boolean recalculoApartado = CR.meServ.recalculadoSaldoApartado(1);
			boolean recalculoApartado = false;
			boolean recalculoPromocionesNuevas = CR.meServ.recalculadoSaldoApartado(3);
			boolean recalculoIva = CR.meServ.recalculadoSaldoApartado(2);
			if (recalculoIva) {
				if (recalculoApartado || recalculoPromocionesNuevas) {
					MensajesVentanas.aviso("Se Recalculará el Saldo del Apartado.\nNueva Tasa y Nuevas promociones vigentes");
				} else {
					MensajesVentanas.aviso("Se Recalculará el Saldo del Apartado.\nNueva Tasa Vigente");
				}
				this.repintarFactura();
				abonos = new RegistrarAbono(CR.meServ.getApartado().consultarMontoServ() - CR.meServ.getApartado().montoAbonos(), true,true);
			} else {
				if (recalculoApartado || recalculoPromocionesNuevas) {
					MensajesVentanas.aviso("Se Recalculará el Saldo del Apartado.\nNuevas promociones vigentes");
					this.repintarFactura();
					abonos = new RegistrarAbono(CR.meServ.getApartado().consultarMontoServ() - CR.meServ.getApartado().montoAbonos(), true, true);
				} else {
					abonos = new RegistrarAbono(CR.meServ.getApartado().consultarMontoServ() - CR.meServ.getApartado().montoAbonos(), false,true);
				}
			}
			/*if (CR.meServ.recalculadoSaldoApartado()) {
				MensajesVentanas.aviso("Se Recalculará el Saldo del Apartado.\nNuevas promociones vigentes");
				this.repintarFactura();
				abonos = new RegistrarAbono(CR.meServ.getApartado().consultarMontoServ() - CR.meServ.getApartado().montoAbonos(), true, true);
			} else
				abonos = new RegistrarAbono(CR.meServ.getApartado().consultarMontoServ() - CR.meServ.getApartado().montoAbonos(), false, true);
			*/
			MensajesVentanas.centrarVentanaDialogo(abonos);
			if(abonos.isCanceladaEjecucion()) {
			this.repintarFactura();
			this.repintarEtiquetasVuelto();
			} else{
				dispose();
				CR.me.repintarMenuPrincipal();
			}
		}
		
		if(jButton4.isEnabled() && ((e.getKeyCode() == KeyEvent.VK_F2)&&(CR.meServ.getApartado().getEstadoServicio()==Sesion.APARTADO_ACTIVO || CR.meServ.getApartado().getEstadoServicio()==Sesion.APARTADO_VENCIDO))){
			filaAbono = jTable2.getSelectedRow();
			Abono abono = (Abono)CR.meServ.getApartado().getAbonos().elementAt(filaAbono);
			
			if(abono.getEstadoAbono() == Sesion.ABONO_ANULADO) {
				MensajesVentanas.aviso(" El abono ya ha sido anulado ");
			} else {
				ca = null;
				ca = new ConsultaAbono(filaAbono, true);
				MensajesVentanas.centrarVentanaDialogo(ca);
				if(ca.isCanceladaEjecucion()) {
				this.repintarFactura();
				this.repintarEtiquetasVuelto();	
				} else{
					dispose();	
					CR.me.repintarMenuPrincipal();
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("keyPressed(KeyEvent) - F2");
			}
		}
		
		//Mapeo de F3
		if(jButton4.isEnabled() && ((e.getKeyCode() == KeyEvent.VK_F3)&&(CR.meServ.getApartado().getEstadoServicio()==Sesion.APARTADO_ACTIVO || CR.meServ.getApartado().getEstadoServicio()==Sesion.APARTADO_VENCIDO))){
			int numApartado = CR.meServ.getApartado().getNumServicio(); //Obtenemos el número de apartado para poder en el mensaje de aviso luego de haberlo anulado
			try {
				if (MensajesVentanas.preguntarSiNo("¿Está seguro que desea Anular el Apartado?")==0) {
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
		
		//Mapeo de F4
		if ((e.getKeyCode() == KeyEvent.VK_F4) && (CR.meServ.getApartado().getEstadoServicio()==Sesion.APARTADO_PAGADO) && getJButton6().isEnabled()) {
			try {
				if (MensajesVentanas.preguntarSiNo("¿Está seguro que desea Facturar el Apartado?")==0) {
					CR.meVenta.crearVentaApartado(CR.meServ.getApartado());
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
				logger.debug("keyPressed(KeyEvent) - F4");
			}
			}
		
		//Mapeo de F8 
		if(jButton8.isEnabled() && e.getKeyCode() == KeyEvent.VK_F8){
			
			Utilitarios ventanaUtilitarios = new Utilitarios();
			MensajesVentanas.centrarVentanaDialogo(ventanaUtilitarios);
			this.repintarFactura();		
			if (logger.isDebugEnabled()) {
				logger.debug("keyPressed(KeyEvent) - F8");
			}
		}
		
		//Mapeo de F9
		if(e.getKeyCode() == KeyEvent.VK_F9){
			if (MensajesVentanas.preguntarSiNo(Sesion.MSG_CANCELAR)==0) {
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
		
		if (e.getSource().equals(this.getJTable2())) {
			if(jTable2.getSelectedRow() == -1){
			}
			else{
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					filaAbono = jTable2.getSelectedRow();
					ca = null;
					ca = new ConsultaAbono(filaAbono, false);
					MensajesVentanas.centrarVentanaDialogo(ca);
					if(filaAbono == 0) {
						jTable2.setRowSelectionInterval(jTable2.getRowCount()-1, jTable2.getRowCount()-1);
					} else {
						jTable2.setRowSelectionInterval(filaAbono-1, filaAbono-1);
					}

				} else if(e.getKeyCode() == KeyEvent.VK_TAB) {
					if (jTable2.getSelectedColumn() == jTable2.getColumnCount()-1) {
						if(jTable2.getSelectedRow() == jTable2.getRowCount()-1) {
							filaAbono = 0;
							modeloTablaDetallePagosAbono.llenarTablaPagosAbono(filaAbono);
							this.getJTable1().setRowSelectionInterval(0,0);
							this.getJTable1().requestFocus();
						} else {
							filaAbono = this.getJTable2().getSelectedRow() + 1;
							modeloTablaDetallePagosAbono.llenarTablaPagosAbono(filaAbono);
							this.getJTable1().setRowSelectionInterval(0,0);
						}
						this.repintarEtiquetasVuelto();
					}
				} else if(e.getKeyCode() == KeyEvent.VK_UP && this.getJTable2().getSelectedRow() > 0) {
					filaAbono = this.getJTable2().getSelectedRow() - 1;
					modeloTablaDetallePagosAbono.llenarTablaPagosAbono(filaAbono);
					this.getJTable1().setRowSelectionInterval(0,0);
					this.repintarEtiquetasVuelto();
				} else if((e.getKeyCode() == KeyEvent.VK_DOWN /*|| e.getKeyCode() == KeyEvent.VK_ENTER*/)  && this.getJTable2().getSelectedRow() < jTable2.getRowCount()-1) {
					filaAbono = this.getJTable2().getSelectedRow() + 1;
					modeloTablaDetallePagosAbono.llenarTablaPagosAbono(filaAbono);
					this.getJTable1().setRowSelectionInterval(0,0);
					this.repintarEtiquetasVuelto();
				}
			}
		}
		
		if (e.getSource().equals(this.getJTable1())) {
			if(jTable1.getSelectedRow() == -1){
			}
			else{
				if(e.getKeyCode() == KeyEvent.VK_TAB && ((jTable1.getSelectedRow() == jTable1.getRowCount()-1) && (jTable1.getSelectedColumn() == jTable1.getColumnCount()-1))){
					this.getJTable2().requestFocus();
				}
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_D){
			ActualizadorPreciosFactory  factory = new ActualizadorPreciosFactory();
			ActualizadorPrecios actualizadorPrecios = factory.getActualizadorPreciosInstance();		
			actualizadorPrecios.abrirVentanaCondicionesVenta(((DetalleServicio)(CR.meServ.getApartado().getDetallesServicio().elementAt(jTable.getSelectedRow()))).getCondicionesVentaPromociones());
		}
		} catch(Exception e1){
			MensajesVentanas.mensajeError("Error en el recálculo del apartado. Por favor llamar a Soporte");
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
	 * This method initializes jTable2
	 * 
	 * @return javax.swing.JTable
	 */
	private javax.swing.JTable getJTable2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTable2() - start");
		}

		if(jTable2 == null) {
			jTable2 = new javax.swing.JTable(modeloTablaDetalleAbonos);
			jTable2.getTableHeader().setReorderingAllowed(false) ;
			jTable2.getColumnModel().getColumn(0).setPreferredWidth(72);
			jTable2.getColumnModel().getColumn(1).setPreferredWidth(72);
			jTable2.getColumnModel().getColumn(2).setPreferredWidth(72);
			jTable2.getColumnModel().getColumn(3).setPreferredWidth(72);
			jTable2.getColumnModel().getColumn(4).setPreferredWidth(72);
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
			jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Abonos del Apartado: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), java.awt.Color.black));
			jPanel20.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel20() - end");
		}
		return jPanel20;
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
			jScrollPane1.setPreferredSize(new java.awt.Dimension(310,100));
			jScrollPane1.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJScrollPane1() - end");
		}
		return jScrollPane1;
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
			jTable1 = new javax.swing.JTable(modeloTablaDetallePagosAbono);
			jTable1.getTableHeader().setReorderingAllowed(false) ;
			jTable1.getColumnModel().getColumn(0).setPreferredWidth(72);
			jTable1.getColumnModel().getColumn(1).setPreferredWidth(72);
			jTable1.setLocale(new java.util.Locale("es", "VE", ""));
			jTable1.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jTable1.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTable1() - end");
		}
		return jTable1;
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
			jLabel15.setText("Abonos");
			jLabel15.setPreferredSize(new java.awt.Dimension(160,18));
			jLabel15.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel15() - end");
		}
		return jLabel15;
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
			jTextField3.setPreferredSize(new java.awt.Dimension(160,18));
			jTextField3.setBackground(new java.awt.Color(242,242,238));
			jTextField3.setBorder(javax.swing.BorderFactory.createMatteBorder(0,0,1,0,(new java.awt.Color(0,0,0))));
			jTextField3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			jTextField3.setEditable(false);
			jTextField3.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jTextField3.setText("00,00");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField3() - end");
		}
		return jTextField3;
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
			jLabel16.setText("Saldo");
			jLabel16.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel16.setPreferredSize(new java.awt.Dimension(160,18));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel16() - end");
		}
		return jLabel16;
	}
	/**
	 * This method initializes jTextField7
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField7() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField7() - start");
		}

		if(jTextField7 == null) {
			jTextField7 = new javax.swing.JTextField();
			jTextField7.setBackground(new java.awt.Color(242,242,238));
			jTextField7.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			jTextField7.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			jTextField7.setEditable(false);
			jTextField7.setText(df.format(0));
			jTextField7.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jTextField7.setPreferredSize(new java.awt.Dimension(160,18));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField7() - end");
		}
		return jTextField7;
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
			
			if (CR.meServ.getApartado().getAbonos().size() > 0) {
				modeloTablaDetalleAbonos.llenarTablaAbonos();	
				this.getJTable2().setRowSelectionInterval(0,0);
				filaAbono = 0;
				modeloTablaDetallePagosAbono.llenarTablaPagosAbono(filaAbono);
				this.getJTable1().setRowSelectionInterval(0,0);
			}
			
			String nombre, rif;
			if (CR.meServ.getApartado().getCliente().getCodCliente()== null){
					nombre = "";
					rif = "";
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
		
			jLabel10.setText("Apartado Nro. " + CR.meServ.getApartado().getNumServicio());
			
			if(CR.meServ.getApartado().getEstadoServicio() == Sesion.APARTADO_ANULADO_EXONERADO || CR.meServ.getApartado().getEstadoServicio() == Sesion.APARTADO_ANULADO_CON_CARGO || CR.meServ.getApartado().getEstadoServicio() == Sesion.APARTADO_FACTURADO || CR.meServ.getApartado().getEstadoServicio() == Sesion.APARTADO_PAGADO) {
				CR.me.setCalendario(false);
				this.getJTable().clearSelection();
				this.getJTable().setBackground(new java.awt.Color(226,226,222));
				this.getJTable().setEnabled(false);
				
				this.getJTable1().clearSelection();
				this.getJTable1().setBackground(new java.awt.Color(226,226,222));
								
				this.getJTable2().clearSelection();
				this.getJTable2().setBackground(new java.awt.Color(226,226,222));
				
				this.getJButton3().setEnabled(false);
				this.getJButton4().setEnabled(false);
				this.getJButton5().setEnabled(false);
				this.getJButton12().setEnabled(false);	
								
				if (CR.meServ.getApartado().getEstadoServicio() == Sesion.APARTADO_ANULADO_CON_CARGO || CR.meServ.getApartado().getEstadoServicio() == Sesion.APARTADO_ANULADO_EXONERADO) {
					jLabel10.setText("Apartado Nro. " + CR.meServ.getApartado().getNumServicio() + " __ ANULADO __ ");
					jButton6.setEnabled(false);
					jButton6.removeKeyListener(this);
					jButton6.removeMouseListener(this);
				} else { 
					if (CR.meServ.getApartado().getEstadoServicio() == Sesion.APARTADO_PAGADO) {
						jLabel10.setText("Apartado Nro. " + CR.meServ.getApartado().getNumServicio() + " __ PAGADO __ ");
						//Actualizado por centrobeco para detectar caso de apartados en C
						//jButton6.setEnabled(true);
					} else {
							jButton6.removeKeyListener(this);
							jButton6.removeMouseListener(this);
							jLabel10.setText("Apartado Nro. " + CR.meServ.getApartado().getNumServicio() + " __ FACTURADO __ ");
							jButton6.setEnabled(false);
					}
				}
			}
			
			getJTextField4().setText(Sesion.getTienda().getMonedaBase()+" "+String.valueOf(df.format(CR.meServ.getApartado().getMontoBase())));
			getJTextField5().setText(Sesion.getTienda().getMonedaBase()+" "+String.valueOf(df.format(CR.meServ.getApartado().getMontoImpuesto())));
			getJTextField6().setText(Sesion.getTienda().getMonedaBase()+" "+String.valueOf(df.format(CR.meServ.getApartado().consultarMontoServ())));
			getJTextField3().setText(Sesion.getTienda().getMonedaBase()+" "+String.valueOf(df.format(CR.meServ.getApartado().montoAbonos())));
			getJTextField7().setText(Sesion.getTienda().getMonedaBase()+" "+String.valueOf(df.format(CR.meServ.getApartado().getMontoBase() + CR.meServ.getApartado().getMontoImpuesto() - CR.meServ.getApartado().montoAbonos())));
		} catch (Exception ex){
			logger.error("repintarFactura()", ex);

			CR.me.setExento(false);	
			CR.me.setCliente("","");
			getJTextField4().setText(df.format(0));
			getJTextField5().setText(df.format(0));
			getJTextField6().setText(df.format(0));
			getJTextField3().setText(df.format(0));
			getJTextField7().setText(df.format(0));
			jLabel10.setText("Apartado Nro. ");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("repintarFactura() - end");
		}
	}

	/**
	 * Método repintarFactura.
	 */
	private void repintarEtiquetasVuelto(){
		if (logger.isDebugEnabled()) {
			logger.debug("repintarEtiquetasVuelto() - start");
		}

		try{
			jLabel18.setText(df.format(((Abono)CR.meServ.getApartado().getAbonos().elementAt(filaAbono)).getMontoVuelto()) + " ");
			if (((Abono)CR.meServ.getApartado().getAbonos().elementAt(filaAbono)).getMontoVuelto() <= 0) {
				jLabel18.setVisible(false);
				jLabel17.setVisible(false);
			} else {
				jLabel18.setVisible(true);
				jLabel17.setVisible(true);
			}
		} catch (Exception ex){
			logger.error("repintarEtiquetasVuelto()", ex);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("repintarEtiquetasVuelto() - end");
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
			jLabel17.setText("Vuelto: ");
			jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel17() - end");
		}
		return jLabel17;
	}
	/**
	 * This method initializes jLabel18
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel18() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel18() - start");
		}

		if(jLabel18 == null) {
			jLabel18 = new javax.swing.JLabel();
			jLabel18.setText("");
			jLabel18.setPreferredSize(new java.awt.Dimension(150,16));
			jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel18() - end");
		}
		return jLabel18;
	}
}
