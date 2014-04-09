/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui.listaregalos
 * Programa   : ConsultaListaTitular.java
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
import java.util.Calendar;

import javax.swing.JDialog;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.excepciones.AfiliadoUsrExcepcion;
import com.becoblohm.cr.excepciones.AutorizacionExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.PagoExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.gui.CambiarCantidad;
import com.becoblohm.cr.gui.Consultas;
import com.becoblohm.cr.gui.EjecutarConCantidad;
import com.becoblohm.cr.gui.FacturacionPrincipal;
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
public class ConsultaListaTitular extends JDialog implements ActionListener, KeyListener, DocumentListener, ScanerSwitch {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ConsultaListaTitular.class);
	
	private DecimalFormat df = new DecimalFormat("#,##0.00");
	private boolean permitirEscaner = true;
	private FacturacionPrincipal principal = null;
	private int estado;
	private int filaAModificar;
	private EfectuarLecturaEscaner lector = null;
	private ModeloTablaLR tablaDetalleLRTitular = new ModeloTablaLR(4);	

	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JButton jButton12 = null;
	private javax.swing.JButton jButton11 = null;
	private javax.swing.JButton jButton2 = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JButton jButton5 = null;
	private javax.swing.JButton jButton10 = null;
	private javax.swing.JButton jButton7 = null;
	private javax.swing.JButton jButton6 = null;
	private javax.swing.JButton jButton4 = null;
	private javax.swing.JButton jButton9 = null;
	private javax.swing.JLabel jLabel4 = null;
	private javax.swing.JLabel jLabel5 = null;
	private javax.swing.JButton jButton8 = null;
	private javax.swing.JLabel jLabel12 = null;
	private javax.swing.JLabel jLabel13 = null;
	private javax.swing.JLabel jLabel14 = null;
	private javax.swing.JTextField pedidosTF = null;
	private javax.swing.JLabel jLabel15 = null;
	private javax.swing.JTextField abonadosTF = null;
	private javax.swing.JLabel jLabel16 = null;
	private javax.swing.JTextField montoAbonadosTF = null;
	private javax.swing.JLabel jLabel17 = null;
	private javax.swing.JLabel jLabel19 = null;
	private javax.swing.JLabel jLabel20 = null;
	private javax.swing.JLabel jLabel21 = null;
	private javax.swing.JLabel jLabel22 = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JScrollPane jScrollPane = null;
	private javax.swing.JTable jTableTitular = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JButton jButton3 = null;
	private javax.swing.JTextField montoPedidosTF = null;
	private javax.swing.JTextField adquiridosTF = null;
	private javax.swing.JTextField montoAdquiridosTF = null;
	private javax.swing.JTextField abonosAProductosTF = null;
	private javax.swing.JTextField abonosAListaTF = null;
	private javax.swing.JTextField totalAbonosTF = null;
	private javax.swing.JButton jButton13 = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JTextField porcentajeVendidosTF = null;
	private javax.swing.JLabel jLabel3 = null;
	private javax.swing.JTextField restantesTF = null;
	private javax.swing.JLabel jLabel7 = null;
	private javax.swing.JTextField montoRestantesTF = null;
	private javax.swing.JTextField totalAbonadosVendidosTF = null;

	/**
	 * This is the default constructor
	 */
	public ConsultaListaTitular(FacturacionPrincipal principal, int estado) {
		super(MensajesVentanas.ventanaActiva);
		this.principal = principal;
		this.estado = estado;
		initialize();
		Calendar fechaActual = Calendar.getInstance();
		Calendar fechaEvento = Calendar.getInstance();
		fechaEvento.setTimeInMillis(CR.meServ.getListaRegalos().getFechaEvento().getTime()+(24*60*60*1000));
		if(fechaActual.after(fechaEvento)){
			getJButton4().setEnabled(false);
			getJButton8().setEnabled(false);
		} else
			getJButton7().setEnabled(false);
		getJButton1().setEnabled(false);
		getJButton2().setEnabled(false);
		getJButton5().setEnabled(false);
		this.repintarPantalla();
		agregarListeners();
	}
	
	public ConsultaListaTitular(FacturacionPrincipal principal) {
		super(MensajesVentanas.ventanaActiva);
		this.principal = principal;

		try {
			CR.meServ.recuperarEstadoLista(ListaRegalos.CONSULTA_TITULAR);
		} catch (MaquinaDeEstadoExcepcion e1) {
			e1.printStackTrace();
		} catch (ConexionExcepcion e1) {
			e1.printStackTrace();
		} catch (ExcepcionCr e1) {
			e1.printStackTrace();
		}

		if(CR.meServ.getListaRegalos().getDetalleAbonos().size() > 0)
			this.estado = ConsultaListaRegalos.ABONANDO;
		else
			this.estado = ConsultaListaRegalos.INICIAL;
		
		initialize();
	
		if(CR.meServ.getListaRegalosRespaldo() != null){
			getJButton1().setEnabled(true);
			getJButton2().setEnabled(true);
			getJButton5().setEnabled(true);
			getJButton7().setEnabled(false);
			getJButton8().setEnabled(false);
			getJButton10().setEnabled(false);
			getJButton11().setEnabled(false);
			getJButton12().setEnabled(false);
			getJButton13().setEnabled(false);
			getJButton4().setText("F4 Fin Modificacion");
			getJButton4().setForeground(Color.BLUE);
		}else{
			Calendar fechaActual = Calendar.getInstance();
			Calendar fechaEvento = Calendar.getInstance();
			fechaEvento.setTime(CR.meServ.getListaRegalos().getFechaEvento());
			if(fechaActual.after(fechaEvento)){
				getJButton4().setEnabled(false);
				getJButton8().setEnabled(false);
			} else
				getJButton7().setEnabled(false);
			getJButton1().setEnabled(false);
			getJButton2().setEnabled(false);
			getJButton5().setEnabled(false);
		}
		agregarListeners();
		this.repintarPantalla();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
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
			jPanel.add(getJPanel4(), null);
			jPanel.add(getJPanel3(), null);
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
			layFlowLayout4.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel3.setLayout(layFlowLayout4);
			jPanel3.add(getJLabel16(), null);
			jPanel3.add(getAbonadosTF(), null);
			jPanel3.add(getJLabel17(), null);
			jPanel3.add(getMontoAbonadosTF(), null);
			jPanel3.add(getJLabel4(), null);
			jPanel3.add(getJLabel21(), null);
			jPanel3.add(getJLabel19(), null);
			jPanel3.add(getAbonosAProductosTF(), null);
			jPanel3.add(getJLabel2(), null);
			jPanel3.add(getJLabel22(), null);
			jPanel3.add(getJLabel20(), null);
			jPanel3.add(getAbonosAListaTF(), null);
			jPanel3.add(getJLabel5(), null);
			jPanel3.add(getTotalAbonosTF(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(328,138));
			jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Resumen Abonos: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), java.awt.Color.black));
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
			layFlowLayout5.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel4.setLayout(layFlowLayout5);
			jPanel4.add(getJLabel12(), null);
			jPanel4.add(getPedidosTF(), null);
			jPanel4.add(getJLabel13(), null);
			jPanel4.add(getMontoPedidosTF(), null);
			jPanel4.add(getJLabel14(), null);
			jPanel4.add(getAdquiridosTF(), null);
			jPanel4.add(getJLabel15(), null);
			jPanel4.add(getMontoAdquiridosTF(), null);
//			jPanel4.add(getJLabel16(), null);
//			jPanel4.add(getAbonadosTF(), null);
//			jPanel4.add(getJLabel17(), null);
//			jPanel4.add(getMontoAbonadosTF(), null);
			jPanel4.add(getJLabel3(), null);
			jPanel4.add(getRestantesTF(), null);
			jPanel4.add(getJLabel7(), null);
			jPanel4.add(getMontoRestantesTF(), null);
			jPanel4.add(getJLabel(), null);
			jPanel4.add(getPorcentajeVendidosTF(), null);
			jPanel4.setPreferredSize(new java.awt.Dimension(328,138));
			jPanel4.setBackground(new java.awt.Color(242,242,238));
			jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Resumen Ventas: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), java.awt.Color.black));
		}
		return jPanel4;
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
	 * This method initializes jButton7
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton7() {
		if(jButton7 == null) {
			jButton7 = new javax.swing.JButton();
			jButton7.setPreferredSize(new java.awt.Dimension(120,40));
			jButton7.setBackground(new java.awt.Color(226,226,222));
			jButton7.setMargin(new Insets(1,2,1,1));
			jButton7.setText("F7 Cerrar Lista");
			jButton7.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		}
		return jButton7;
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
			jButton4.setText("F4 Modificar Lista");
			jButton4.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton4.setActionCommand("Modificar Lista");
		}
		return jButton4;
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
			jButton9.setActionCommand("Cancelar Consulta");
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
			jLabel4.setText("A. Parciales:");
			jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
			jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jLabel4.setPreferredSize(new java.awt.Dimension(80,20));
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
			jLabel5.setText("Total Abonos:");
			jLabel5.setPreferredSize(new java.awt.Dimension(180,20));
			jLabel5.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
			//jLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
			jLabel5.setFocusable(false);
		}
		return jLabel5;
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
			jButton8.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton8.setText("F8 Anular Lista");
		}
		return jButton8;
	}
	/**
	 * This method initializes jLabel12
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel12() {
		if(jLabel12 == null) {
			jLabel12 = new javax.swing.JLabel();
			jLabel12.setText("Pedidos:");
			jLabel12.setPreferredSize(new java.awt.Dimension(70,20));
			jLabel12.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
			//jLabel12.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
			jLabel12.setFocusable(false);
		}
		return jLabel12;
	}
	/**
	 * This method initializes jLabel16
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel16() {
		if(jLabel16 == null) {
			jLabel16 = new javax.swing.JLabel();
			jLabel16.setText("A. Totales:");
			jLabel16.setPreferredSize(new java.awt.Dimension(80,20));
			jLabel16.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
			//jLabel12.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
			jLabel16.setFocusable(false);
		}
		return jLabel16;
	}
	/**
	 * This method initializes jLabel17
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel17() {
		if(jLabel17 == null) {
			jLabel17 = new javax.swing.JLabel();
			jLabel17.setText("Monto:");
			jLabel17.setPreferredSize(new java.awt.Dimension(70,20));
			jLabel17.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
			//jLabel12.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
			jLabel17.setFocusable(false);
		}
		return jLabel17;
	}
	/**
	 * This method initializes jLabel20
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel20() {
		if(jLabel20 == null) {
			jLabel20 = new javax.swing.JLabel();
			jLabel20.setText("Monto:");
			jLabel20.setPreferredSize(new java.awt.Dimension(70,20));
			jLabel20.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
			jLabel20.setFocusable(false);
		}
		return jLabel20;
	}
	/**
	 * This method initializes jLabel19
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel19() {
		if(jLabel19 == null) {
			jLabel19 = new javax.swing.JLabel();
			jLabel19.setText("Monto:");
			jLabel19.setPreferredSize(new java.awt.Dimension(70,20));
			jLabel19.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
			jLabel19.setFocusable(false);
		}
		return jLabel19;
	}
	/**
	 * This method initializes jLabel21
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel21() {
		if(jLabel21 == null) {
			jLabel21 = new javax.swing.JLabel();
			jLabel21.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jLabel21.setHorizontalAlignment(javax.swing.JTextField.CENTER);
			jLabel21.setText("N/A");
			jLabel21.setPreferredSize(new java.awt.Dimension(30,20));
			jLabel21.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
			jLabel21.setFocusable(false);
		}
		return jLabel21;
	}
	/**
	 * This method initializes jLabel22
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel22() {
		if(jLabel22 == null) {
			jLabel22 = new javax.swing.JLabel();
			jLabel22.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jLabel22.setHorizontalAlignment(javax.swing.JTextField.CENTER);
			jLabel22.setText("N/A");
			jLabel22.setPreferredSize(new java.awt.Dimension(30,20));
			jLabel22.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
			jLabel22.setFocusable(false);
		}
		return jLabel22;
	}
	/**
	 * This method initializes jLabel13
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel13() {
		if(jLabel13 == null) {
			jLabel13 = new javax.swing.JLabel();
			jLabel13.setText("Monto Pedidos:");
			jLabel13.setPreferredSize(new java.awt.Dimension(105,20));
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
			jLabel14.setText("Vendidos:");
			jLabel14.setPreferredSize(new java.awt.Dimension(70,20));
			jLabel14.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
			jLabel14.setFocusable(false);
		}
		return jLabel14;
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
			pedidosTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			pedidosTF.setEditable(false);
			pedidosTF.setBackground(new java.awt.Color(242,242,238));
			pedidosTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			pedidosTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
			pedidosTF.setFocusable(false);
		}
		return pedidosTF;
	}
	/**
	 * This method initializes jLabel15
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel15() {
		if(jLabel15 == null) {
			jLabel15 = new javax.swing.JLabel();
			jLabel15.setText("Monto Vendidos:");
			jLabel15.setPreferredSize(new java.awt.Dimension(105,20));
			jLabel15.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
			//jLabel15.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
			jLabel15.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
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
			jButton1.setText("F1 Agregar Producto");
			jButton1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton1.setPreferredSize(new java.awt.Dimension(120,40));
			jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton1.setBackground(new java.awt.Color(226,226,222));
			jButton1.setMargin(new Insets(1,2,1,1));
		}
		return jButton1;
	}
	
	public void actionPerformed(ActionEvent e) {
		eliminarListeners();

		/* Boton "Agregar Producto" */
		if(e.getSource() == jButton1){
			if(CR.meServ.getListaRegalos().getDetallesServicio().size() > 0){
				CambiarCantidad cc;
				int fila = jTableTitular.getSelectedRow();
				if(fila!=-1 && (!jTableTitular.getValueAt(fila,0).equals("000000000000"))){
					filaAModificar = (jTableTitular.getValueAt(0, 0).equals("000000000000")) ? fila - 1 : fila;
					cc = new CambiarCantidad(filaAModificar, 3);
					MensajesVentanas.centrarVentanaDialogo(cc);
				} else if (fila == -1){
					cc = new CambiarCantidad(3);
					MensajesVentanas.centrarVentanaDialogo(cc);
				}
			} else
				MensajesVentanas.aviso("No Existen productos en la Lista de Regalos");
			this.repintarPantalla();
		}
		
		/* Boton "Eliminar Producto" */
		else if(e.getSource() == jButton2){
			if(CR.meServ.getListaRegalos().getDetallesServicio().size() > 0){
				int fila = jTableTitular.getSelectedRow();
				if(fila!=-1 && (!jTableTitular.getValueAt(fila,0).equals("000000000000"))){
					filaAModificar = (jTableTitular.getValueAt(0, 0).equals("000000000000")) ? fila - 1 : fila;
					AnularProducto anularProducto = new AnularProducto(filaAModificar, 1);
					MensajesVentanas.centrarVentanaDialogo(anularProducto);
					this.repintarPantalla();
				} else if (fila == -1){
					AnularProducto anularProducto = new AnularProducto(CR.meServ.getListaRegalos().getDetallesServicio().size()-1, 1);
					MensajesVentanas.centrarVentanaDialogo(anularProducto);
					this.repintarPantalla();
				}
			} else
				MensajesVentanas.aviso("No Existen productos en la Lista de Regalos");
		}
		
		/* Boton "Detalles del evento" */
		else if(e.getSource().equals(jButton3)){
			DatosLista datosLista = new DatosLista(CR.meServ.getListaRegalos());
			MensajesVentanas.centrarVentanaDialogo(datosLista);
			this.repintarPantalla();
			
		}
		
		/* Botón Modificar Lista y Fin Modificación */
		else if(e.getSource().equals(jButton4)) {
			if(getJButton4().getActionCommand().equals("Modificar Lista"))
				try {
					CR.meServ.modificarListaRegalosInicia();
					CR.inputEscaner.getDocument().addDocumentListener(this);
					getJButton1().setEnabled(true);
					getJButton2().setEnabled(true);
					getJButton5().setEnabled(true);
					//getJButton3().setEnabled(true);
					getJButton7().setEnabled(false);
					getJButton8().setEnabled(false);
					getJButton10().setEnabled(false);
					getJButton11().setEnabled(false);
					getJButton13().setEnabled(false);
					getJButton4().setText("F4 Fin Modificacion");
					getJButton4().setActionCommand("Fin Modificacion");
					getJButton9().setActionCommand("Cancelar Modificacion");
					getJButton4().setForeground(Color.BLUE);
					getJButton9().setForeground(Color.BLUE);
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
			else if(MensajesVentanas.preguntarSiNo("¿Confirma que desea guardar los cambios?")==0){
				CR.inputEscaner.getDocument().removeDocumentListener(this);

				try {
					CR.meServ.modificarDetallesListaRegalos();
					getJButton1().setEnabled(false);
					getJButton2().setEnabled(false);
					getJButton5().setEnabled(false);
					//getJButton3().setEnabled(false);
					getJButton7().setEnabled(true);
					getJButton8().setEnabled(true);
					getJButton10().setEnabled(true);
					getJButton11().setEnabled(true);
					getJButton13().setEnabled(true);
					getJButton4().setText("F4 Modificar Lista");
					getJButton4().setActionCommand("Modificar Lista");
					getJButton9().setActionCommand("Cancelar Consulta");
					getJButton4().setForeground(Color.BLACK);
					getJButton9().setForeground(Color.BLACK);
					Calendar fechaActual = Calendar.getInstance();
					Calendar fechaEvento = Calendar.getInstance();
					fechaEvento.setTime(CR.meServ.getListaRegalos().getFechaEvento());
					if(fechaActual.after(fechaEvento)){
						getJButton7().setEnabled(true);
						getJButton8().setEnabled(false);
					}else{
						getJButton7().setEnabled(false);
						getJButton8().setEnabled(true);			
					}
					if(MensajesVentanas.preguntarSiNo("¿Desea que se imprima un nuevo reporte de lista?")==0)
						CR.meServ.getListaRegalos().imprimirReporteLista(false);
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

		/* Boton "Consultas" */
		else if(e.getSource().equals(jButton5)){
			Consultas consultas = new Consultas();
			MensajesVentanas.centrarVentanaDialogo(consultas);
			String casoConsulta = consultas.getCodigoSeleccionado()[0];
			String codigo = consultas.getCodigoSeleccionado()[1];
			if (casoConsulta != null)
				try {
					CR.meServ.ingresarProductoLR(codigo, Sesion.capturaTeclado);
					this.repintarPantalla();
				} catch (ExcepcionCr e1) {
					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (ConexionExcepcion e1) {
					MensajesVentanas.mensajeError(e1.getMensaje());
				}
		}
	
		/* Botón Cerrar Lista */
		else if(e.getSource().equals(jButton7))
			try {
				CR.meServ.cerrarListaRegalos();
				if(CR.meVenta.getVenta()!= null)
					CR.meVenta.anularVentaActiva();
				dispose();
				CierreListaRegalos1 clr1 = new CierreListaRegalos1(principal,Sesion.LISTAREGALOS_CERRADA);
				MensajesVentanas.centrarVentanaDialogo(clr1);
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
		else if(e.getSource().equals(jButton8))
			try {
				CR.meServ.anularListaRegalosInicia();
				if(CR.meVenta.getVenta()!= null)
					CR.meVenta.anularVentaActiva();
				dispose();
				CierreListaRegalos1 clr = new CierreListaRegalos1(principal,Sesion.LISTAREGALOS_ANULADA);
				MensajesVentanas.centrarVentanaDialogo(clr);
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
		else if(e.getSource().equals(jButton6)){
			if(MensajesVentanas.preguntarSiNo("¿Está seguro que desea colocar en espera?")==0)
				try {
					String identificacion = CR.meServ.getListaRegalos().getTitular().getCodCliente();
					CR.meServ.colocarListaEnEspera(identificacion,Sesion.getTienda().getNumero(),Sesion.getCaja().getNumero(),ListaRegalos.CONSULTA_TITULAR);
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

		/* Boton Cancelar */
		else if(e.getSource().equals(jButton9)){
			if(getJButton9().getActionCommand().equals("Cancelar Modificacion")){
				if(MensajesVentanas.preguntarSiNo("¿Confirma que desea cancelar los cambios?")==0){
					CR.inputEscaner.getDocument().removeDocumentListener(this);
					getJButton1().setEnabled(false);
					getJButton2().setEnabled(false);
					getJButton5().setEnabled(false);
					//getJButton3().setEnabled(false);
					getJButton7().setEnabled(true);
					getJButton8().setEnabled(true);
					getJButton10().setEnabled(true);
					getJButton11().setEnabled(true);
					getJButton13().setEnabled(true);
					getJButton4().setText("F4 Modificar Lista");
					getJButton4().setActionCommand("Modificar Lista");
					getJButton9().setActionCommand("Cancelar Consulta");
					getJButton4().setForeground(Color.BLACK);
					getJButton9().setForeground(Color.BLACK);
					Calendar fechaActual = Calendar.getInstance();
					Calendar fechaEvento = Calendar.getInstance();
					fechaEvento.setTime(CR.meServ.getListaRegalos().getFechaEvento());
					if(fechaActual.after(fechaEvento)){
						getJButton7().setEnabled(true);
						getJButton8().setEnabled(false);
					}else{
						getJButton7().setEnabled(false);
						getJButton8().setEnabled(true);			
					}
					try {
						CR.meServ.modificarListaRegalosCancela();
						this.repintarPantalla();
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
			} else if(getJButton9().getActionCommand().equals("Cancelar Consulta"))
				if(MensajesVentanas.preguntarSiNo("¿Confirma que desea salir?")==0) {			
					dispose();
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

		}
				
		/* Modo Invitado */
		else if(e.getSource().equals(jButton13))
			try {
				CR.meServ.invitadoListaRegalos();
				dispose();
				ConsultaListaRegalos clr = new ConsultaListaRegalos(principal, estado);
				MensajesVentanas.centrarVentanaDialogo(clr);
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
			jPanel2.setPreferredSize(new java.awt.Dimension(656,264));
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
			jScrollPane.setViewportView(getJTableTitular());
			jScrollPane.setPreferredSize(new java.awt.Dimension(647,242));
			jScrollPane.setBackground(new java.awt.Color(242,242,237));
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private javax.swing.JTable getJTableTitular() {
		if(jTableTitular == null) {
			jTableTitular = new javax.swing.JTable(tablaDetalleLRTitular);
			jTableTitular.getTableHeader().setReorderingAllowed(false) ;
			jTableTitular.getColumnModel().getColumn(0).setPreferredWidth(65);
			jTableTitular.getColumnModel().getColumn(1).setPreferredWidth(175);
			jTableTitular.getColumnModel().getColumn(2).setPreferredWidth(60);
			jTableTitular.getColumnModel().getColumn(3).setPreferredWidth(30);
			jTableTitular.getColumnModel().getColumn(4).setPreferredWidth(30);
			jTableTitular.getColumnModel().getColumn(5).setPreferredWidth(30);
			jTableTitular.getColumnModel().getColumn(6).setPreferredWidth(60);
			jTableTitular.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jTableTitular.setBackground(new java.awt.Color(242,242,238));
			jTableTitular.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						int fila = jTableTitular.getSelectedRow();
						String codProd = tablaDetalleLRTitular.getValueAt(fila,0).toString();
						String nombreProd = tablaDetalleLRTitular.getValueAt(fila,1).toString();
						DetalleRegalo dr = new DetalleRegalo(codProd,nombreProd);
						MensajesVentanas.centrarVentanaDialogo(dr);
					}
				}
			});
		}
		return jTableTitular;
	}
	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel2() {
		if(jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setText("A. Lista:");
			jLabel2.setPreferredSize(new java.awt.Dimension(80,20));
			jLabel2.setFocusable(false);
		}
		return jLabel2;
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
			jButton3.setText("F3 Modificar Detalles");
			jButton3.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton3.setMargin(new Insets(1,2,1,1));
			jButton3.setActionCommand("F3 Modificar Detalles");
		}
		return jButton3;
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
			adquiridosTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
			adquiridosTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
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
	/**
	 * This method initializes abonosAProductosTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getAbonosAProductosTF() {
		if(abonosAProductosTF == null) {
			abonosAProductosTF = new javax.swing.JTextField();
			abonosAProductosTF.setPreferredSize(new java.awt.Dimension(130,20));
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
	 * This method initializes abonosAListaTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getAbonosAListaTF() {
		if(abonosAListaTF == null) {
			abonosAListaTF = new javax.swing.JTextField();
			abonosAListaTF.setPreferredSize(new java.awt.Dimension(130,20));
			abonosAListaTF.setEditable(false);
			abonosAListaTF.setBackground(new java.awt.Color(242,242,238));
			abonosAListaTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			abonosAListaTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			abonosAListaTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			abonosAListaTF.setFocusable(false);
		}
		return abonosAListaTF;
	}
	/**
	 * This method initializes totalAbonosTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTotalAbonosTF() {
		if(totalAbonosTF == null) {
			totalAbonosTF = new javax.swing.JTextField();
			totalAbonosTF.setPreferredSize(new java.awt.Dimension(130,20));
			totalAbonosTF.setEditable(false);
			totalAbonosTF.setBackground(new java.awt.Color(242,242,238));
			totalAbonosTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			totalAbonosTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			totalAbonosTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			totalAbonosTF.setFocusable(false);
		}
		return totalAbonosTF;
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
			jButton13.setText("Modo Invitado");
			jButton13.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jButton13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton13.setMargin(new Insets(1,2,1,1));
			jButton13.setMnemonic('I');
		}
		return jButton13;
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
		else if(e.getKeyCode() == KeyEvent.VK_I)
			getJButton13().doClick();
		else if (Character.isDigit(e.getKeyChar())) {
			eliminarListeners();
			if(getJButton4().getText().equals("F4 Fin Modificacion")){
				// Mostramos una ventana para que el usuario introduzca el código del producto
				EjecutarConCantidad ec = new EjecutarConCantidad("Ingreso de Código:", String.valueOf(e.getKeyChar()),11);
				MensajesVentanas.centrarVentanaDialogo(ec);
				this.repintarPantalla();
			}
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
			ArrayList<Object> valorLeido = Control.codigoValido(codigoScaner);
			int tipoCodigo = ((Integer)valorLeido.get(0)).intValue();
			String codigo = (String)valorLeido.get(1);
			switch (tipoCodigo){
				case Control.PRODUCTO:
					CR.meServ.ingresarProductoLR(codigo, Sesion.capturaEscaner);
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
					}catch(Exception ex1){
						try {
							CR.meServ.ingresarProductoLR(codigoScaner, Sesion.capturaEscaner);
						} catch (Exception ex2) {
							//CR.meServ.asignarCliente(codigo);
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
			
		jButton5.addActionListener(this);
		jButton5.addKeyListener(this);
			
		jButton7.addActionListener(this);
		jButton7.addKeyListener(this);
			
		jButton8.addActionListener(this);
		jButton8.addKeyListener(this);
			
		jButton6.addActionListener(this);
		jButton6.addKeyListener(this);
			
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
			
		jTableTitular.addKeyListener(this);
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
			
		jButton7.removeActionListener(this);
		jButton7.removeKeyListener(this);
			
		jButton8.removeActionListener(this);
		jButton8.removeKeyListener(this);

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

		jTableTitular.removeKeyListener(this);
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
	
	private void repintarPantalla(){
		String nombre, codigo;
		try{
			tablaDetalleLRTitular.llenarTablaDetallesTitular();

			nombre = CR.meServ.getListaRegalos().getTitular().getNombreCompleto();
			codigo = CR.meServ.getListaRegalos().getTitular().getCodCliente();
			CR.me.setCliente(nombre, codigo);

			getPedidosTF().setText(String.valueOf(CR.meServ.getListaRegalos().getCantPedidos()));
			getMontoPedidosTF().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meServ.getListaRegalos().getMontoBase() + CR.meServ.getListaRegalos().getMontoImpuesto())));
			getAdquiridosTF().setText(String.valueOf(CR.meServ.getListaRegalos().getCantVendidos()));
			getMontoAdquiridosTF().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meServ.getListaRegalos().getMontoVendidos())));
			getRestantesTF().setText(String.valueOf(CR.meServ.getListaRegalos().getCantRestantes()));
			getMontoRestantesTF().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meServ.getListaRegalos().getMontoRestantes())));
			getTotalAbonadosVendidosTF().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meServ.getListaRegalos().getMontoVendidos()+CR.meServ.getListaRegalos().getMontoAbonadosTotales())));

			porcentajeVendidosTF.setText(CR.meServ.getListaRegalos().getPorcentajeVendidoAbonado()+" %");
			
			getAbonadosTF().setText(String.valueOf(CR.meServ.getListaRegalos().getCantAbonadosTotales()));
			getMontoAbonadosTF().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meServ.getListaRegalos().getMontoAbonadosTotales())));
			getAbonosAProductosTF().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meServ.getListaRegalos().getMontoAbonosProds()-CR.meServ.getListaRegalos().getMontoAbonadosTotales())));
			getAbonosAListaTF().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meServ.getListaRegalos().getMontoAbonosLista())));
			getTotalAbonosTF().setText(Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meServ.getListaRegalos().getMontoAbonosLista()+CR.meServ.getListaRegalos().getMontoAbonosProds())));
		} catch (Exception ex){
			ex.printStackTrace();
			MensajesVentanas.mensajeError("Error refrescando la tabla");
			CR.me.setCliente("","");
			getPedidosTF().setText("0");
			getMontoPedidosTF().setText(df.format(0));
			getAdquiridosTF().setText("0");
			getMontoAdquiridosTF().setText(df.format(0));
			getMontoAbonadosTF().setText(df.format(0));
			getRestantesTF().setText("0");
			getMontoRestantesTF().setText(df.format(0));
			getTotalAbonadosVendidosTF().setText(df.format(0));
		}

		if (logger.isDebugEnabled())
			logger.debug("repintarFactura() - end");
	}
	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		if(jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setText("Porcentaje Vendido+Abonado:");
			jLabel.setPreferredSize(new java.awt.Dimension(180,20));
		}
		return jLabel;
	}
	/**
	 * This method initializes porcentajeVendidosTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getPorcentajeVendidosTF() {
		if(porcentajeVendidosTF == null) {
			porcentajeVendidosTF = new javax.swing.JTextField();
			porcentajeVendidosTF.setPreferredSize(new java.awt.Dimension(50,20));
			porcentajeVendidosTF.setEditable(false);
			porcentajeVendidosTF.setBackground(new java.awt.Color(242,242,238));
			porcentajeVendidosTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			porcentajeVendidosTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
			porcentajeVendidosTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			porcentajeVendidosTF.setFocusable(false);
		}
		return porcentajeVendidosTF;
	}

	/**
	 * This method initializes montoAbonadosTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getMontoAbonadosTF() {
		if(montoAbonadosTF == null) {
			montoAbonadosTF = new javax.swing.JTextField();
			montoAbonadosTF.setPreferredSize(new java.awt.Dimension(130,20));
			montoAbonadosTF.setEditable(false);
			montoAbonadosTF.setBackground(new java.awt.Color(242,242,238));
			montoAbonadosTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			montoAbonadosTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			montoAbonadosTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			montoAbonadosTF.setFocusable(false);
		}
		return montoAbonadosTF;
	}
	/**
	 * This method initializes jLabel3
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel3() {
		if(jLabel3 == null) {
			jLabel3 = new javax.swing.JLabel();
			jLabel3.setText("Restantes:");
			jLabel3.setPreferredSize(new java.awt.Dimension(70,20));
		}
		return jLabel3;
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
	 * This method initializes abonadosTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getAbonadosTF() {
		if(abonadosTF == null) {
			abonadosTF = new javax.swing.JTextField();
			abonadosTF.setPreferredSize(new java.awt.Dimension(30,20));
			abonadosTF.setEditable(false);
			abonadosTF.setBackground(new java.awt.Color(242,242,238));
			abonadosTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			abonadosTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			abonadosTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
			abonadosTF.setFocusable(false);
		}
		return abonadosTF;
	}
	/**
	 * This method initializes jLabel7
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel7() {
		if(jLabel7 == null) {
			jLabel7 = new javax.swing.JLabel();
			jLabel7.setText("Monto Restantes:");
			jLabel7.setPreferredSize(new java.awt.Dimension(105,20));
		}
		return jLabel7;
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

	/**
	 * This method initializes montoAbonadosTF
	 * 
	 * @return javax.swing.JTextField
	 */
	@SuppressWarnings("unused")
	private javax.swing.JTextField montoAbonadosTF() {
		if(montoAbonadosTF == null) {
			montoAbonadosTF = new javax.swing.JTextField();
			montoAbonadosTF.setPreferredSize(new java.awt.Dimension(105,20));
			montoAbonadosTF.setEditable(false);
			montoAbonadosTF.setBackground(new java.awt.Color(242,242,238));
			montoAbonadosTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			montoAbonadosTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			montoAbonadosTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			montoAbonadosTF.setFocusable(false);

		}
		return montoAbonadosTF;
	}
	/**
	 * This method initializes totalAbonadosVendidosTF
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTotalAbonadosVendidosTF() {
		if(totalAbonadosVendidosTF == null) {
			totalAbonadosVendidosTF = new javax.swing.JTextField();
			totalAbonadosVendidosTF.setPreferredSize(new java.awt.Dimension(110,20));
			totalAbonadosVendidosTF.setEditable(false);
			totalAbonadosVendidosTF.setBackground(new java.awt.Color(242,242,238));
			totalAbonadosVendidosTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			totalAbonadosVendidosTF.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			totalAbonadosVendidosTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			totalAbonadosVendidosTF.setFocusable(false);

		}
		return totalAbonadosVendidosTF;
	}
}
