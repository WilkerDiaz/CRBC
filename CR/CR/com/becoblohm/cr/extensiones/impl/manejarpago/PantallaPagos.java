/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.manejarpago
 * Programa   : PantallaPagos.java
 * Creado por : gmartinelli
 * Creado en  : 30-abr-04 8:09
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1
 * Fecha       : 25/06/2008
 * Analista    : jgraterol
 * Descripción : Modificado el metodo realizarPago:
 * 				Al agregar la primera forma de pago verifica si hay transaccción
 * 				premiada en la extension correspondiente
 * =============================================================================
 */
package com.becoblohm.cr.extensiones.impl.manejarpago;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.JDialog;

import org.apache.log4j.Logger;

import ve.com.megasoft.universal.error.VposUniversalException;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.PagoExcepcion;
import com.becoblohm.cr.extensiones.ManejadorReportesFactory;
import com.becoblohm.cr.extensiones.ActualizadorPrecios;
import com.becoblohm.cr.extensiones.ActualizadorPreciosFactory;
import com.becoblohm.cr.extensiones.ManejoPagosFactory;
import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.gui.JHighlightComboBox;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.utiles.MensajesVentanas;

/**
 * Descripción:
 * 
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
* Fecha: agosto 2011
*/
public class PantallaPagos extends JDialog implements ComponentListener, KeyListener, MouseListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PantallaPagos.class);

	private DecimalFormat df = new DecimalFormat("#,##0.00");
	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JTextField jTextField = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JPanel jPanel5 = null;
	private javax.swing.JScrollPane jScrollPane = null;
	private javax.swing.JTable jTable = null;
	private javax.swing.JButton jButton2 = null;
	private javax.swing.JComboBox jComboBox = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JLabel jLabel3 = null;
	private javax.swing.JLabel jLabel4 = null;
	private javax.swing.JLabel jLabel5 = null;
	private javax.swing.JLabel jLabel6 = null;
	private javax.swing.JLabel jLabel7 = null;
	private javax.swing.JLabel jLabel8 = null;
	private javax.swing.JLabel jLabel9 = null;
	
	private ModeloTablaPago modeloTablaDetallePagos = new ModeloTablaPago();
	private Vector<Pago> pagosRealizados;
	private Vector<Pago> pagosRealizadosAnteriormente;
	private double montoMinimo;
	private double montoPagado = 0;
	private Vector<FormaDePago> formasDePago;
	private boolean unicoPago = false;
	private Cliente cliente;
	private boolean reqAutorizacion = false;
	private String codAutorizante = null;
	private boolean pagosAgregados = false;
	private int numTransaccion = 0;
	
	ActualizadorPrecios actualizadorPrecios = null;  //  @jve:decl-index=0:
	
	/**
	 * This is the default constructor
	 *
	 * @param mtoMinimo
	 * @param pRealizados
	 * @param fPagos
	 * @param cte
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public PantallaPagos(double mtoMinimo, Vector<Pago> pRealizados, Vector<FormaDePago> fPagos,
			Cliente cte) {
	
		this(mtoMinimo, pRealizados, fPagos, false, cte);

	}
	
	/**
	 * This is the default constructor
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public PantallaPagos(double mtoMinimo, Vector<Pago> pRealizados, Vector<FormaDePago> fPagos, Cliente cte, int numTrans) {
		super(MensajesVentanas.ventanaActiva);
		this.formasDePago = fPagos;
		initialize();
		
		jTextField.addKeyListener(this);
		
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);

		jButton2.addKeyListener(this);
		jButton2.addMouseListener(this);
		
		jComboBox.addKeyListener(this);
		jComboBox.addMouseListener(this);
		
		jLabel5.setText(df.format(mtoMinimo));
		pagosRealizados = new Vector<Pago>();
		pagosRealizadosAnteriormente = pRealizados;
		for (int i=0; i<pRealizados.size(); i++) {
			Pago pagoAct = (Pago)pRealizados.elementAt(i);
			montoPagado += pagoAct.getMonto();
			pagosRealizados.addElement(new Pago(pagoAct.getFormaPago(),pagoAct.getMonto(),pagoAct.getCodBanco(),pagoAct.getNumDocumento(),pagoAct.getNumCuenta(),pagoAct.getNumConformacion(),pagoAct.getNumReferencia(),pagoAct.getCedTitular()));
		}
		this.montoMinimo = mtoMinimo;
		this.cliente = cte;
		this.numTransaccion = numTrans;
		this.repintarPantalla();
	}

	/**
	 * This is the default constructor
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public PantallaPagos(double mto, Vector<Pago> pRealizados, Vector<FormaDePago> fPagos, boolean unicoP, Cliente cte) {
		super(MensajesVentanas.ventanaActiva);
		this.formasDePago = fPagos;
		this.unicoPago = unicoP;
		this.cliente = cte;
		initialize();
		
		jTextField.addKeyListener(this);
		
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);

		jButton2.addKeyListener(this);
		jButton2.addMouseListener(this);
		
		jComboBox.addKeyListener(this);
		jComboBox.addMouseListener(this);
		
		jLabel5.setText(df.format(mto));
		pagosRealizados = new Vector<Pago>();
		pagosRealizadosAnteriormente = pRealizados;
		for (int i=0; i<pRealizados.size(); i++) {
			Pago pagoAct = (Pago)pRealizados.elementAt(i);
			montoPagado += pagoAct.getMonto();
			pagosRealizados.addElement(new Pago(pagoAct.getFormaPago(),pagoAct.getMonto(),pagoAct.getCodBanco(),pagoAct.getNumDocumento(),pagoAct.getNumCuenta(),pagoAct.getNumConformacion(),pagoAct.getNumReferencia(),pagoAct.getCedTitular()));
		}
		this.montoMinimo = mto;
		
		this.repintarPantalla();
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

		this.setSize(550, 350);
		this.setContentPane(getJContentPane());
		this.setUndecorated(true);
		this.setModal(true);
		this.setBackground(new java.awt.Color(226,226,222));
		this.setTitle("Pago");
		this.addComponentListener(this);
		
		if (logger.isDebugEnabled()) {
			logger.debug("initialize() - end");
		}
	}
	private void eliminarListeners() {
		jTextField.removeKeyListener(this);
		jButton1.removeKeyListener(this);
		jButton1.removeMouseListener(this);
		jButton2.removeKeyListener(this);
		jButton2.removeMouseListener(this);
		jComboBox.removeKeyListener(this);
		jComboBox.removeMouseListener(this);
	}
	private void agregarListeners() {
		jTextField.addKeyListener(this);
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);
		jButton2.addKeyListener(this);
		jButton2.addMouseListener(this);
		jComboBox.addKeyListener(this);
		jComboBox.addMouseListener(this);
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
			jContentPane.setLayout(new FlowLayout());
			jContentPane.add(getJPanel3(), null);
			jContentPane.add(getJPanel2(), null);
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.RAISED));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
			jContentPane.setPreferredSize(new java.awt.Dimension(550,330));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJContentPane() - end");
		}
		return jContentPane;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		eliminarListeners();
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}

		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if(jButton2.isEnabled()){
				if(CR.meVenta.getVenta()!=null){
					CR.meVenta.deshacerPromociones(pagosRealizados, pagosRealizadosAnteriormente);
					//Limpiando los pagos especiales
					(new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().eliminarPagosEspeciales();
				} else if(CR.meServ.getApartado()!=null)
					CR.meServ.deshacerPromociones(pagosRealizados, pagosRealizadosAnteriormente);
				else if(CR.meServ.getVentaBR()!=null)
					CR.meServ.deshacerPromocionesBR(pagosRealizados, pagosRealizadosAnteriormente);
				dispose();
			}
		}
		
		//Mapeo de TAB sobre el componente jTable y jTextField
		if(e.getKeyCode() == KeyEvent.VK_TAB){
			if(e.getSource().equals(this.getJTable())){
				this.getJButton1().requestFocus();			
			}
		}	
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(e.getSource().equals(this.getJTextField())) {
				this.getJButton1().requestFocus();
			} else {
				if(e.getSource().equals(this.getJComboBox())) {
					this.getJComboBox().transferFocus();
				} else {
					if(e.getSource().equals(this.getJButton1())) {
						if (!this.getJTextField().getText().equalsIgnoreCase("")) {
							try {
								boolean pagoRealizado = realizaNuevoPago((FormaDePago)formasDePago.elementAt(jComboBox.getSelectedIndex()), new Double(CR.me.formatoNumerico(getJTextField().getText())).doubleValue());
								if (reqAutorizacion && pagoRealizado) {
									// Imprimimos el voucher por duplicado
									ManejadorReportesFactory.getInstance().imprimirVoucherPago(cliente,codAutorizante,(FormaDePago)formasDePago.elementAt(jComboBox.getSelectedIndex()),new Double(CR.me.formatoNumerico(getJTextField().getText())).doubleValue(),2, numTransaccion);
								}
								if (unicoPago){
									pagosRealizadosAnteriormente.clear();
									for (int i=0; i<pagosRealizados.size(); i++)
										pagosRealizadosAnteriormente.addElement(pagosRealizados.elementAt(i));
									if (pagoRealizado)
										dispose();
								}
							} catch (NumberFormatException e1) {
								logger.error("keyPressed(KeyEvent)", e1);

								MensajesVentanas.mensajeError("Error en formato del Monto. Debe ser un número decimal.");
							}
							this.repintarPantalla();
							getJComboBox().setSelectedIndex(0);
							this.getJComboBox().requestFocus();
						} else {
							this.getJButton1().transferFocus();
						}
					} else {
						if(e.getSource().equals(this.getJButton2())) {
							if(this.jButton2.isEnabled()){
								if(CR.meVenta.getVenta()!=null) {
									CR.meVenta.deshacerPromociones(pagosRealizados, pagosRealizadosAnteriormente);
									(new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().eliminarPagosEspeciales();
								}
								else if(CR.meServ.getApartado()!=null)
									CR.meServ.deshacerPromociones(pagosRealizados, pagosRealizadosAnteriormente);
								else if(CR.meServ.getVentaBR()!=null)
									CR.meServ.deshacerPromocionesBR(pagosRealizados, pagosRealizadosAnteriormente);
								dispose();
							}
						}
					}
				}
			}
		}
		
		if (e.getSource().equals(this.getJTable()) && e.getKeyCode() == KeyEvent.VK_TAB) {
			this.getJButton1().requestFocus();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - end");
		}
		agregarListeners();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyReleased(KeyEvent) - start");
		}

		if (e.getSource().equals(getJTextField())) {
			String textoFormateado = CR.me.formatearCampoNumerico(getJTextField().getText(), e);
			if (textoFormateado!=null)
				getJTextField().setText(textoFormateado);
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

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		eliminarListeners();
		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - start");
		}

		if(e.getSource().equals(this.getJButton1())) {
			try {
				boolean pagoRealizado = realizaNuevoPago((FormaDePago)formasDePago.elementAt(jComboBox.getSelectedIndex()), new Double(CR.me.formatoNumerico(getJTextField().getText())).doubleValue());
				if (reqAutorizacion && pagoRealizado) {
					// Imprimimos el voucher por duplicado
					ManejadorReportesFactory.getInstance().imprimirVoucherPago(cliente,codAutorizante,(FormaDePago)formasDePago.elementAt(jComboBox.getSelectedIndex()),new Double(CR.me.formatoNumerico(getJTextField().getText())).doubleValue(),2, numTransaccion);
				}
				if (unicoPago){
					pagosRealizadosAnteriormente.clear();
					for (int i=0; i<pagosRealizados.size(); i++)
						pagosRealizadosAnteriormente.addElement(pagosRealizados.elementAt(i));
					if (pagoRealizado){
						dispose();
					}
				}
			} catch (NumberFormatException e1) {
				logger.error("mouseClicked(MouseEvent)", e1);

				MensajesVentanas.mensajeError("Error en formato del Monto. Debe ser un número decimal.");
			}
			this.repintarPantalla();
			getJComboBox().setSelectedIndex(0);
			this.getJComboBox().requestFocus();
			this.getJTable().setRowSelectionInterval(0,0);
		}
			
		if(e.getSource().equals(this.getJButton2())) {
			if(this.jButton2.isEnabled()){
				if(CR.meVenta.getVenta()!=null) {
					CR.meVenta.deshacerPromociones(pagosRealizados, pagosRealizadosAnteriormente);
					(new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().eliminarPagosEspeciales();
				}
				else if(CR.meServ.getApartado()!=null)
					CR.meServ.deshacerPromociones(pagosRealizados, pagosRealizadosAnteriormente);
				else if(CR.meServ.getVentaBR()!=null)
					CR.meServ.deshacerPromocionesBR(pagosRealizados, pagosRealizadosAnteriormente);
				dispose();
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - end");
		}
		agregarListeners();
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
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout1.setHgap(1);
			layFlowLayout1.setVgap(1);
			jPanel.setLayout(layFlowLayout1);
			jPanel.add(getJTextField(), null);
			jPanel.setPreferredSize(new java.awt.Dimension(235,60));
			jPanel.setBackground(new java.awt.Color(242,242,238));
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Monto: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel() - end");
		}
		return jPanel;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField() - start");
		}

		if(jTextField == null) {
			jTextField = new javax.swing.JTextField();
			jTextField.setPreferredSize(new java.awt.Dimension(195,30));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField() - end");
		}
		return jTextField;
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
			java.awt.FlowLayout layFlowLayout21 = new java.awt.FlowLayout();
			layFlowLayout21.setHgap(1);
			layFlowLayout21.setVgap(1);
			layFlowLayout21.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel1.setLayout(layFlowLayout21);
			jPanel1.add(getJComboBox(), null);
			jPanel1.setPreferredSize(new java.awt.Dimension(285,60));
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Forma de Pago: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
			jPanel1.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel1() - end");
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel2() - start");
		}

		if(jPanel2 == null) {
			jPanel2 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout11 = new java.awt.FlowLayout();
			layFlowLayout11.setAlignment(java.awt.FlowLayout.RIGHT);
			layFlowLayout11.setHgap(0);
			layFlowLayout11.setVgap(0);
			jPanel2.setLayout(layFlowLayout11);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(getJButton2(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(520,35));
			jPanel2.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel2() - end");
		}
		return jPanel2;
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
			jButton1.setText("Pagar");
			jButton1.setBackground(new java.awt.Color(226,226,222));
			jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/add2.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton1() - end");
		}
		return jButton1;
	}
	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel3() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel3() - start");
		}

		if(jPanel3 == null) {
			jPanel3 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout12 = new java.awt.FlowLayout();
			layFlowLayout12.setHgap(0);
			layFlowLayout12.setVgap(0);
			jPanel3.setLayout(layFlowLayout12);
			jPanel3.add(getJPanel4(), null);
			jPanel3.add(getJPanel1(), null);
			jPanel3.add(getJPanel(), null);
			jPanel3.add(getJPanel5(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(525,260));
			jPanel3.setBackground(new java.awt.Color(242,242,238));
			jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel3() - end");
		}
		return jPanel3;
	}
	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel4() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel4() - start");
		}

		if(jPanel4 == null) {
			jPanel4 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
			layFlowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout2.setVgap(10);
			layFlowLayout2.setHgap(5);
			jPanel4.setLayout(layFlowLayout2);
			jPanel4.add(getJLabel1(), null);
			jPanel4.setPreferredSize(new java.awt.Dimension(525,50));
			jPanel4.setBackground(new java.awt.Color(69,107,127));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel4() - end");
		}
		return jPanel4;
	}
	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel1() - start");
		}

		if(jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setText("Efectuar Pago");
			jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jLabel1.setForeground(java.awt.Color.white);
			jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/money2.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel1() - end");
		}
		return jLabel1;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel5() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel5() - start");
		}

		if(jPanel5 == null) {
			jPanel5 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout13 = new java.awt.FlowLayout();
			layFlowLayout13.setHgap(5);
			layFlowLayout13.setVgap(1);
			layFlowLayout13.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel5.setLayout(layFlowLayout13);
			jPanel5.add(getJScrollPane(), null);
			jPanel5.add(getJLabel4(), null);
			jPanel5.add(getJLabel2(), null);
			jPanel5.add(getJLabel3(), null);
			jPanel5.add(getJLabel8(), null);
			jPanel5.add(getJLabel(), null);
			jPanel5.add(getJLabel5(), null);
			jPanel5.add(getJLabel9(), null);
			jPanel5.add(getJLabel6(), null);
			jPanel5.add(getJLabel7(), null);
			jPanel5.setPreferredSize(new java.awt.Dimension(520,148));
			jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Pago: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
			jPanel5.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel5() - end");
		}
		return jPanel5;
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
			jScrollPane.setPreferredSize(new java.awt.Dimension(500,70));
			jScrollPane.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJScrollPane() - end");
		}
		return jScrollPane;
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
			jTable = new javax.swing.JTable(modeloTablaDetallePagos);
			jTable.getTableHeader().setReorderingAllowed(false) ;
			jTable.getColumnModel().getColumn(0).setPreferredWidth(72);
			jTable.getColumnModel().getColumn(1).setPreferredWidth(72);
			jTable.setLocale(new java.util.Locale("es", "VE", ""));
			jTable.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jTable.setBackground(new java.awt.Color(242,242,238));
			jTable.setEnabled(false);
			jTable.setFocusable(false);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTable() - end");
		}
		return jTable;
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
			jButton2.setBackground(new java.awt.Color(226,226,222));
			if (unicoPago) {
				jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
				jButton2.setText("Cancelar");
			} else {
				jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
				jButton2.setText("Finalizar");
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton2() - end");
		}
		return jButton2;
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
			jComboBox = new JHighlightComboBox();
			jComboBox.setPreferredSize(new java.awt.Dimension(270,30));
			jComboBox.setAutoscrolls(true);
			jComboBox.setMaximumRowCount(6);
			jComboBox.setBackground(new java.awt.Color(226,226,222));
			jComboBox.setMinimumSize(new java.awt.Dimension(31,150));
			jComboBox.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			for (int i=0; i<formasDePago.size();i++)
				jComboBox.addItem(((FormaDePago)formasDePago.elementAt(i)).getNombre());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJComboBox() - end");
		}
		return jComboBox;
	}
	
	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel() - start");
		}

		if(jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setText("Monto a Pagar: ");
			jLabel.setBackground(new java.awt.Color(242,242,238));
			jLabel.setPreferredSize(new java.awt.Dimension(100,16));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel() - end");
		}
		return jLabel;
	}
	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel2() - start");
		}

		if(jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setText("Total Pagado: ");
			jLabel2.setBackground(new java.awt.Color(242,242,238));
			jLabel2.setPreferredSize(new java.awt.Dimension(100,16));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel2() - end");
		}
		return jLabel2;
	}
	/**
	 * This method initializes jLabel3
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel3() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel3() - start");
		}

		if(jLabel3 == null) {
			jLabel3 = new javax.swing.JLabel();
			jLabel3.setText("0,00");
			jLabel3.setPreferredSize(new java.awt.Dimension(120,16));
			jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel3() - end");
		}
		return jLabel3;
	}
	/**
	 * This method initializes jLabel4
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel4() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel4() - start");
		}

		if(jLabel4 == null) {
			jLabel4 = new javax.swing.JLabel();
			jLabel4.setText("");
			jLabel4.setPreferredSize(new java.awt.Dimension(269,16));
			jLabel4.setVisible(true);
			jLabel4.setEnabled(true);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel4() - end");
		}
		return jLabel4;
	}
	/**
	 * This method initializes jLabel5
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel5() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel5() - start");
		}

		if(jLabel5 == null) {
			jLabel5 = new javax.swing.JLabel();
			jLabel5.setText("0,00");
			jLabel5.setPreferredSize(new java.awt.Dimension(120,16));
			jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel5() - end");
		}
		return jLabel5;
	}
	/**
	 * This method initializes jLabel6
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel6() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel6() - start");
		}

		if(jLabel6 == null) {
			jLabel6 = new javax.swing.JLabel();
			jLabel6.setText("Restan: ");
			jLabel6.setPreferredSize(new java.awt.Dimension(100,16));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel6() - end");
		}
		return jLabel6;
	}
	/**
	 * This method initializes jLabel7
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel7() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel7() - start");
		}

		if(jLabel7 == null) {
			jLabel7 = new javax.swing.JLabel();
			jLabel7.setText("0,00");
			jLabel7.setPreferredSize(new java.awt.Dimension(120,16));
			jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel7() - end");
		}
		return jLabel7;
	}
	/**
	 * This method initializes jLabel8
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel8() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel8() - start");
		}

		if(jLabel8 == null) {
			jLabel8 = new javax.swing.JLabel();
			jLabel8.setText("");
			jLabel8.setPreferredSize(new java.awt.Dimension(269,16));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel8() - end");
		}
		return jLabel8;
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
			jLabel9.setText("");
			jLabel9.setPreferredSize(new java.awt.Dimension(269,16));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel9() - end");
		}
		return jLabel9;
	}

	/**
	 * Método repintarFactura.
	 */
	private void repintarPantalla(){
		if (logger.isDebugEnabled()) {
			logger.debug("repintarPantalla() - start");
		}

		try{
			modeloTablaDetallePagos.llenarTablaPagos(this.pagosRealizados);
			this.getJTable().setRowSelectionInterval(0,0);
			double restoPago = 0;
			jLabel3.setText(df.format(this.montoPagado));
			restoPago = this.montoMinimo - this.montoPagado;
			if (restoPago > 0.01)
				jLabel6.setText("Restan: ");
			else {
				pagosRealizadosAnteriormente.clear();
				for (int i=0; i<pagosRealizados.size(); i++)
					pagosRealizadosAnteriormente.addElement(pagosRealizados.elementAt(i));
				dispose();
			}
			jLabel7.setText(df.format(restoPago));
			DecimalFormat df = new DecimalFormat("#,##0.00");
			this.getJTextField().setText(df.format(restoPago));
			this.getJTextField().selectAll();
			this.getJTextField().requestFocus();
		} catch (Exception ex){
			logger.error("repintarPantalla()", ex);

			modeloTablaDetallePagos.iniciarDatosTabla(this.getJTable().getColumnCount());
			jLabel7.setText(jLabel5.getText());
			this.getJTextField().selectAll();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("repintarPantalla() - end");
		}
	}

	private boolean realizaNuevoPago(FormaDePago formaPago, double mto) {
		if (logger.isDebugEnabled()) {
			logger.debug("realizaNuevoPago(FormaDePago, double) - start");
		}
		// Vemos si se requiere autorizacion para pedirla
		reqAutorizacion = formaPago.isRequiereAutorizacion();
		if (formaPago.isRequiereAutorizacion()) {
			try {
				codAutorizante = CR.me.verificarAutorizacionPago();
			} catch (ExcepcionCr e) {
				logger.error("realizaNuevoPago(FormaDePago, double)", e);

				MensajesVentanas.mensajeError(e.getMensaje());

				if (logger.isDebugEnabled()) {
					logger.debug("realizaNuevoPago(FormaDePago, double) - end");
				}
				return false;
			} catch (ConexionExcepcion e) {
				logger.error("realizaNuevoPago(FormaDePago, double)", e);

				MensajesVentanas.mensajeError(e.getMensaje());

				if (logger.isDebugEnabled()) {
					logger.debug("realizaNuevoPago(FormaDePago, double) - end");
				}
				return false;
			}
		}
		
		//ACTUALIZACION BECO 25/06/2008
		boolean esPremiada = false;
		//Cuento los pagos realizados que son distintos a los pagos especiales
		int pagosNoEspeciales=0;
		for(int i=0;i<this.pagosRealizados.size();i++){
			Pago p = (Pago)this.pagosRealizados.elementAt(i);
			if(p.getFormaPago().getTipo()!=Sesion.TIPO_PAGO_BECO || p.getFormaPago().getCodigo().equalsIgnoreCase(Sesion.FORMA_PAGO_TRANSACCION_PREMIADA)){
				pagosNoEspeciales++;
			}
		}
		if(pagosNoEspeciales==0){
			//es la primera forma de pago
			if(actualizadorPrecios==null){
				iniciarActualizadorPrecios();
			}
			this.montoPagado+=actualizadorPrecios.ejecutarTransaccionPremiada(this.montoMinimo-this.montoPagado, this.pagosRealizados, cliente);
			pagosAgregados = true;
			
			if(montoPagado!=0 && 
					((Pago)this.pagosRealizados.elementAt(this.pagosRealizados.size()-1)).getFormaPago().getCodigo().equalsIgnoreCase(Sesion.FORMA_PAGO_TRANSACCION_PREMIADA)) {
				try { CR.crVisor.enviarString("¡TRANSACCION PREMIADA!", 0, df.format(mto), 2); }
				catch (Exception e) {
					logger
							.error(
									"realizaNuevoPago(FormaDePago, double)",
									e);
				}
				esPremiada=true;
				this.jButton2.setEnabled(false);
			}			
		}
		
		if(!esPremiada){
			// Observamos si el pago cumple los límites minimos y maximos
			if ((mto>=formaPago.getMontoMinimo())&&(mto<=formaPago.getMontoMaximo())) {
				if (((this.montoMinimo-this.montoPagado-mto)<=-0.01)&&(!formaPago.isPermiteVuelto())) {
					PagoExcepcion p = new PagoExcepcion("Debe especificar un monto menor o igual al restante.");
					MensajesVentanas.mensajeError(p.getMensaje() + "\nLa forma de pago no admite vuelto");
	
					if (logger.isDebugEnabled()) {
						logger.debug("realizaNuevoPago(FormaDePago, double) - end");
					}
					return false;
				} else {
					boolean valido = true;
					PagoExcepcion pe = null;
					if (formaPago.isValidarSaldoCliente()) {
						if (cliente.getCodCliente()==null) {
							pe = new PagoExcepcion("Debe existir un cliente asignado para utilizar la forma de pago " + formaPago.getNombre());
							valido = false;
						} else {
							try {
								if (!ManejoPagosFactory.getInstance().validarSaldoCliente(cliente,mto)) {
									pe = new PagoExcepcion("El Saldo del cliente no es suficiente o se encuentra bloqueado para efectuar la operacion");
									valido = false;
								}
							} catch (PagoExcepcion e) {
								logger.error(
										"realizaNuevoPago(FormaDePago, double)", e);
	
								//MensajesVentanas.mensajeError(e.getMensaje());
								pe = e;
								valido = false;
							}
						}
					}
					if (valido) {
						// Vemos si existe un pago con esa forma de Pago que no requiera datos adicionales
						boolean pagoNuevo = true;
						for (int i=0; i<this.pagosRealizados.size(); i++) {
							if (((Pago)pagosRealizados.elementAt(i)).getFormaPago().getCodigo().equalsIgnoreCase(formaPago.getCodigo())) {
								// Si no se requieren datos adicionales se acumulan. Sino, se crea un nuevo pago
								if(!formaPago.isIndicarBanco()&&!formaPago.isIndicarCedulaTitular()&&!formaPago.isIndicarNumConformacion()&&!formaPago.isIndicarNumCuenta()
									&&!formaPago.isIndicarNumDocumento()&&!formaPago.isIndicarNumReferencia()&&
									// Parche para forma de pagos bancarias sin datos adicionales
									!formaPago.getCodigo().equalsIgnoreCase("02")&&!formaPago.getCodigo().equalsIgnoreCase("03") &&
									!formaPago.getCodigo().equalsIgnoreCase("04")&&!formaPago.getCodigo().equalsIgnoreCase("12")
									&& !formaPago.getCodigo().equalsIgnoreCase("18") && !formaPago.getCodigo().equalsIgnoreCase("19")
									&& !formaPago.getCodigo().equalsIgnoreCase("21")) {
									if (formaPago.isValidarSaldoCliente()) {
										try {
											ManejoPagosFactory.getInstance().disminuirSaldoCliente(cliente,mto);
										} catch (PagoExcepcion e) {
											logger
													.error(
															"realizaNuevoPago(FormaDePago, double)",
															e);
	
											MensajesVentanas.mensajeError(e.getMensaje());
											valido = false;
										}
									}
									if (valido) {
	
										/*
										 * Agregamos mas dollares a la misma forma de pago si ya existe una igual
										 */
										if(formaPago.getTipo() == 17){
											try {
												/*
												 * AQUI DEBERIAMOS TRAERNOS EL CAMBIO ACTUAL
												 */
												double cambioDelDia = MediadorBD.obtenerCambioDelDia();
												mto = mto*cambioDelDia;	
											} catch (BaseDeDatosExcepcion e1) {
												logger
														.error(
																"realizaNuevoPago(FormaDePago, double)",
																e1);
											} catch (ConexionExcepcion e1) {
												logger
														.error(
																"realizaNuevoPago(FormaDePago, double)",
																e1);
											} catch (SQLException e1) {
												logger
														.error(
																"realizaNuevoPago(FormaDePago, double)",
																e1);
											}	
										}
										((Pago)pagosRealizados.elementAt(i)).actualizarMonto(mto);
										this.montoPagado += mto;
										pagosAgregados = true;
										try { CR.crVisor.enviarString(((Pago)pagosRealizados.elementAt(i)).getFormaPago().getNombre().toUpperCase(), 0, df.format(mto), 2); }
										catch (Exception e) {
											logger
													.error(
															"realizaNuevoPago(FormaDePago, double)",
															e);
										}
									}
									pagoNuevo = false;
								}
								break;
							}
						}
						if (pagoNuevo) {
							if (formaPago.isValidarSaldoCliente()) { 
								try {
									ManejoPagosFactory.getInstance().disminuirSaldoCliente(cliente,mto);
								} catch (PagoExcepcion e1) {
									logger
											.error(
													"realizaNuevoPago(FormaDePago, double)",
													e1);
	
									MensajesVentanas.mensajeError(e1.getMensaje());
									valido = false;
								}
							}
							/********************************************************************************
							 * Creación de un nuevo pago
							 ********************************************************************************/
							if (valido) {
								Pago pago;
								// Chequeamos si se requieren datos adicionales
								try {
									// Si es un pago con cheque, lo imprimimos
									/*if (formaPago.getNombre().equalsIgnoreCase(
										"Cheque"))
										ManejadorReportesFactory.getInstance().imprimirCheque(mto, Sesion.getFechaSistema());*/
									if (formaPago.isIndicarBanco()
											|| formaPago.isIndicarCedulaTitular()
											|| formaPago.isIndicarNumConformacion()
											|| formaPago.isIndicarNumCuenta()
											|| formaPago.isIndicarNumDocumento()
											|| formaPago.isIndicarNumReferencia()) {
										
										
										/**
										 * Modificado por CENTROBECO, C.A.
										 * Migración a VPosUniversal
										 */
										/*PantallaDatosAdicionales pda = ManejoPagosFactory
											.getInstance()
											.instancePantallaDatosAdicionales(
												formaPago, mto);
										MensajesVentanas.centrarVentanaDialogo(pda);
										pda.obtenerDatosAdicionales();*/
										
										//IROJAS: 14/01/2011
										//Modificado que verifique si el cliente es nulo antes de realizar el cliente.getCodCliente()
										if (cliente!=null)
											pago = ManejoPagosFactory.getInstance().obtenerPagoOperacion(formaPago, mto, cliente.getCodCliente());
										else
											pago = ManejoPagosFactory.getInstance().obtenerPagoOperacion(formaPago, mto, null);
										mto=pago.getMonto();
									} else {
										String numDoc = null;
										String numCta = null;
										String codBanco = null;
										int numConf = 0;
										int numRef = 0;
										String cedTitular = null;
	
								
										/*
										 * Si la forma de pago es en moneda extranjera
										 */
										if(formaPago.getTipo() == 17){
											try {
												/*
												 * AQUI DEBERIAMOS TRAERNOS EL CAMBIO ACTUAL
												 */
												double cambioDelDia = MediadorBD.obtenerCambioDelDia();
												mto = mto*cambioDelDia;
											} catch (BaseDeDatosExcepcion e1) {
												logger
														.error(
																"realizaNuevoPago(FormaDePago, double)",
																e1);
											} catch (ConexionExcepcion e1) {
												logger
														.error(
																"realizaNuevoPago(FormaDePago, double)",
																e1);
											} catch (SQLException e1) {
												logger
														.error(
																"realizaNuevoPago(FormaDePago, double)",
																e1);
											}								
										}
									
										pago = new Pago(
										formaPago, mto, codBanco, numDoc,
										numCta, numConf, numRef, cedTitular);
									}	
									
									if (formaPago.isRequiereAutorizacion()) {
										pago.setCodAutorizante(codAutorizante);
									}
									pagosRealizados.addElement(pago);
									this.montoPagado += mto;
									pagosAgregados = true;
									try { CR.crVisor.enviarString(pago.getFormaPago().getNombre().toUpperCase(), 0, df.format(mto), 2); }
									catch (Exception e) {
										logger
												.error(
														"realizaNuevoPago(FormaDePago, double)",
														e);
									}
								} catch (PagoExcepcion e) {
									logger
											.error(
													"realizaNuevoPago(FormaDePago, double)",
													e);
	
									MensajesVentanas.mensajeError(e.getMensaje());
	
									if (logger.isDebugEnabled()) {
										logger
												.debug("realizaNuevoPago(FormaDePago, double) - end");
									}
									return false;
								} catch (VposUniversalException e1) {
									logger
									.error(
											"realizaNuevoPago(FormaDePago, double)",
											e1);
									return false;
								}
								
							/*************************************************************************************
							 * Fin de la creación de un nuevo pago
							 *************************************************************************************/
							} else {
								if (logger.isDebugEnabled()) {
									logger
											.debug("realizaNuevoPago(FormaDePago, double) - end");
								}
								return false;
							}
						}
					} else {
						MensajesVentanas.mensajeError(pe.getMensaje());
	
						if (logger.isDebugEnabled()) {
							logger
									.debug("realizaNuevoPago(FormaDePago, double) - end");
						}
						return false;
					}
				}
			} else {
				PagoExcepcion p;
				if (mto<formaPago.getMontoMinimo())
					p = new PagoExcepcion("El monto del pago en: " + formaPago.getNombre() + " debe ser mayor a " + formaPago.getMontoMinimo());
				else
					p = new PagoExcepcion("El monto del pago en: " + formaPago.getNombre() + " debe ser menor a " + formaPago.getMontoMaximo());
				MensajesVentanas.mensajeError(p.getMensaje());
	
				if (logger.isDebugEnabled()) {
					logger.debug("realizaNuevoPago(FormaDePago, double) - end");
				}
				return false;
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("realizaNuevoPago(FormaDePago, double) - end");
		}
		return true;
	}
	/**
	 * Método isPagosAgregados
	 * 
	 * @return
	 * boolean
	 */
	public boolean isPagosAgregados() {
		if (logger.isDebugEnabled()) {
			logger.debug("isPagosAgregados() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isPagosAgregados() - end");
		}
		return pagosAgregados;
	}

	/**
	 * Método componentHidden
	 *
	 * @param e
	 */
	public void componentHidden(ComponentEvent e) {
	}

	/**
	 * Método componentMoved
	 *
	 * @param e
	 */
	public void componentMoved(ComponentEvent e) {
		int width = (int)this.getSize().getWidth();
		int height = (int)this.getSize().getHeight();
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x=0,y=0;
		
		if (this.getX() < 0) {
			x=0;
			y= (int)this.getBounds().y;
		} else
		if (this.getY() < 0) {
			y=0;
			x= (int)this.getBounds().x;
		} else
		if ((this.getX() + width) > screen.width){
			x=screen.width;
			y= (int)this.getBounds().y;
		} else
		if ((this.getY() + height) > screen.height){
			y=screen.height;
			x= (int)this.getBounds().x;
		} else {
			x=this.getX();
			y=this.getY();	
		}
		
		this.setBounds(x, y, width, height);
	}

	/**
	 * Método componentResized
	 *
	 * @param e
	 */
	public void componentResized(ComponentEvent e) {
	}

	/**
	 * Método componentShown
	 *
	 * @param e
	 */
	public void componentShown(ComponentEvent e) {
	}
	
	/*********************************************************
	 * Actualizaciones BECO por modulo de promociones
	 *********************************************************/
	/**
	 * Inicializa el actualizador de precios tomado de lo definido en las
	 * extensiones a utilizar
	 * @author jgraterol
	 * **/
	public void iniciarActualizadorPrecios(){
		if (logger.isDebugEnabled()) {
			logger.debug("iniciarBuscador() - start");
		}

		ActualizadorPreciosFactory  factory = new ActualizadorPreciosFactory();
		actualizadorPrecios = factory.getActualizadorPreciosInstance();		
		if (logger.isDebugEnabled()) {
			logger.debug("iniciarBuscador() - end");
		}	
	}
	
	/**********************************************************
	 * HASTA AQUI LAS ACTUALIZACIONES DE BECO POR MODULO DE PROMOCIONES
	 * *********************************************************/

}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"
