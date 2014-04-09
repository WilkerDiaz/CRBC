/**
 * =============================================================================
 * Proyecto   : CRTrabajando
 * Paquete    : com.becoblohm.cr.manejadorpago
 * Programa   : PantallaReversoDevoluciones.java
 * Creado por : gmartinelli
 * Creado en  : 04-jun-04 3:11 a.m.
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
package com.becoblohm.cr.extensiones.impl.manejarpago;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.JDialog;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.PagoExcepcion;
import com.becoblohm.cr.extensiones.ManejoPagosFactory;
import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.utiles.MensajesVentanas;

/**
 * Descripción:
 * 
 */

public class PantallaReversoDevoluciones extends JDialog implements ComponentListener, KeyListener, MouseListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(PantallaReversoDevoluciones.class);

	private DecimalFormat df = new DecimalFormat("#,##0.00");
	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JTextField jTextField = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JPanel jPanel5 = null;
	private javax.swing.JScrollPane jScrollPane = null;
	private javax.swing.JTable jTable = null;
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
	private double montoAReversar;
	private double montoPagado = 0;
	private Vector<FormaDePago> formasDePago;
	private boolean pagadoMontoTotal = false;
	private Cliente cliente;
	private boolean pagosAgregados;
	
	/**
	 * This is the default constructor
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public PantallaReversoDevoluciones(double mtoAReversar, Vector<Pago> pRealizados, Vector<FormaDePago> fPagos, Cliente cte) {
		super(MensajesVentanas.ventanaActiva);
		this.formasDePago = fPagos;
		this.cliente = cte;
		initialize();
		
		jTextField.addKeyListener(this);
		
		jButton.addKeyListener(this);
		jButton.addMouseListener(this);
		
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);

		jComboBox.addKeyListener(this);
		jComboBox.addMouseListener(this);
		
		jLabel5.setText(df.format(mtoAReversar));
		pagosRealizados = new Vector<Pago>();
		pagosRealizadosAnteriormente = pRealizados;
		for (int i=0; i<pRealizados.size(); i++) {
			Pago pagoAct = (Pago)pRealizados.elementAt(i);
			montoPagado += pagoAct.getMonto();
			pagosRealizados.addElement(new Pago(pagoAct.getFormaPago(),pagoAct.getMonto(),pagoAct.getCodBanco(),pagoAct.getNumDocumento(),pagoAct.getNumCuenta(),pagoAct.getNumConformacion(),pagoAct.getNumReferencia(),pagoAct.getCedTitular()));
		}
		this.montoAReversar = mtoAReversar;
		pagosAgregados = false;
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

		this.setSize(565, 333);
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
			java.awt.FlowLayout layFlowLayout14 = new java.awt.FlowLayout();
			layFlowLayout14.setAlignment(java.awt.FlowLayout.LEFT);
			jContentPane.setLayout(layFlowLayout14);
			jContentPane.add(getJPanel3(), null);
			jContentPane.add(getJPanel2(), null);
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.RAISED));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJContentPane() - end");
		}
		return jContentPane;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@SuppressWarnings("unused")
	public void keyPressed(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}

		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (pagosAgregados) {
				if(MensajesVentanas.preguntarSiNo("Se eliminaran todos los pagos realizados hasta el momento.\n¿Está seguro que desea cancelar?") == 0)
					dispose();
			} else {
				dispose();
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(e.getSource().equals(this.getJTextField())) {
				this.getJTextField().transferFocus();
			} else {
				if(e.getSource().equals(this.getJComboBox())) {
					this.getJComboBox().transferFocus();
				} else {
					if(e.getSource().equals(this.getJButton())) {
						dispose();
					} else {
						if(e.getSource().equals(this.getJButton1())) {
							if (!this.getJTextField().getText().equalsIgnoreCase("")) {
								boolean pagoRealizado = realizaNuevoPago((FormaDePago)formasDePago.elementAt(jComboBox.getSelectedIndex()), new Double(CR.me.formatoNumerico(getJTextField().getText())).doubleValue());
								this.repintarPantalla();
								this.getJTextField().requestFocus();
							} else {
								this.getJButton1().transferFocus();
							}
						}
					}
				}
			}
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
	@SuppressWarnings("unused")
	public void mouseReleased(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - start");
		}

		if(e.getSource().equals(this.getJButton())) {
			if (pagosAgregados) {
				if(MensajesVentanas.preguntarSiNo("Se eliminaran todos los pagos realizados hasta el momento.\n¿Está seguro que desea cancelar?") == 0)
					dispose();
			} else {
				dispose();
			}
		}
			
		if(e.getSource().equals(this.getJButton1())) {
			try {
				boolean pagoRealizado = realizaNuevoPago((FormaDePago)formasDePago.elementAt(jComboBox.getSelectedIndex()), new Double(CR.me.formatoNumerico(getJTextField().getText())).doubleValue());
			} catch (NumberFormatException e1) {
				logger.error("mouseClicked(MouseEvent)", e1);

				MensajesVentanas.mensajeError("Error en formato del Monto. Debe ser un número decimal.");
			}
			this.repintarPantalla();
			this.getJTable().setRowSelectionInterval(0,0);
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
			jPanel.setPreferredSize(new java.awt.Dimension(245,45));
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
			jTextField.setPreferredSize(new java.awt.Dimension(195,20));
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
			jPanel1.setPreferredSize(new java.awt.Dimension(290,45));
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Forma de Reverso: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
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
			jPanel2.add(getJButton(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(540,35));
			jPanel2.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel2() - end");
		}
		return jPanel2;
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

		if(jButton == null) {
			jButton = new JHighlightButton();
			jButton.setText("Cancelar");
			jButton.setBackground(new java.awt.Color(226,226,222));
			jButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton() - end");
		}
		return jButton;
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
			jPanel3.add(getJPanel(), null);
			jPanel3.add(getJPanel1(), null);
			jPanel3.add(getJPanel5(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(540,245));
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
			jPanel4.setPreferredSize(new java.awt.Dimension(535,50));
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
			jLabel1.setText("Reverso de Dinero");
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
			layFlowLayout13.setHgap(10);
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
			jPanel5.setPreferredSize(new java.awt.Dimension(535,148));
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
			jScrollPane.setPreferredSize(new java.awt.Dimension(505,70));
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
			jComboBox.setPreferredSize(new java.awt.Dimension(270,20));
			jComboBox.setAutoscrolls(true);
			jComboBox.setMaximumRowCount(3);
			jComboBox.setBackground(new java.awt.Color(226,226,222));
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
			jLabel.setText("Monto a Reversar: ");
			jLabel.setBackground(new java.awt.Color(242,242,238));
			jLabel.setPreferredSize(new java.awt.Dimension(115,16));
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
			jLabel2.setPreferredSize(new java.awt.Dimension(115,16));
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
			jLabel3.setPreferredSize(new java.awt.Dimension(125,16));
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
			jLabel4.setPreferredSize(new java.awt.Dimension(244,16));
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
			jLabel5.setPreferredSize(new java.awt.Dimension(125,16));
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
			jLabel6.setPreferredSize(new java.awt.Dimension(115,16));
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
			jLabel7.setPreferredSize(new java.awt.Dimension(125,16));
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
			jLabel8.setPreferredSize(new java.awt.Dimension(244,16));
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
			jLabel9.setPreferredSize(new java.awt.Dimension(244,16));
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
			restoPago = this.montoAReversar - this.montoPagado;
			if (restoPago > 0)
				jLabel6.setText("Restan: ");
			else {
				pagosRealizadosAnteriormente.clear();
				for (int i=0; i<pagosRealizados.size(); i++)
					pagosRealizadosAnteriormente.addElement(pagosRealizados.elementAt(i));
				pagadoMontoTotal = true;
				dispose();
			}
			jLabel7.setText(df.format(restoPago));
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

		// Observamos si el reverso posee un monto correcto (Mayor que cero)
		if (mto>0) {
			if (this.montoAReversar-this.montoPagado-mto<0) {
				// Si el monto NO es mayor que lo que se quiere reversar. Ocurre un error 
				PagoExcepcion p = new PagoExcepcion("Debe especificar un monto menor o igual al restante.");
				MensajesVentanas.mensajeError(p.getMensaje() + "\nLos reversos no pueden ser mayores al Monto a Reversar");

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
						MensajesVentanas.mensajeError(pe.getMensaje());
						valido = false;
					}
				}
				if (valido) {
					// Vemos si existe un reverso con esa forma de Pago
					if (formaPago.isValidarSaldoCliente()) {
						try {
							ManejoPagosFactory.getInstance().incrementarSaldoCliente(cliente,mto);
						} catch (PagoExcepcion e1) {
							logger
									.error(
											"realizaNuevoPago(FormaDePago, double)",
											e1);

							MensajesVentanas.mensajeError(e1.getMensaje());
							valido = false;
						}
					}
					if (valido) {
						boolean pagoNuevo = true;
						for (int i=0; i<this.pagosRealizados.size(); i++) {
							if (((Pago)pagosRealizados.elementAt(i)).getFormaPago().getCodigo().equalsIgnoreCase(formaPago.getCodigo())) {
								((Pago)pagosRealizados.elementAt(i)).actualizarMonto(mto);
								this.montoPagado += mto;
								pagoNuevo = false;
								pagosAgregados = true;
								break;
							}
						}
						if (pagoNuevo) {
							Pago p = new Pago(formaPago, mto, null, null, null, null, 0, null);
							pagosRealizados.addElement(p);
							this.montoPagado += mto;
							pagosAgregados = true;
						}
					} else {
						if (logger.isDebugEnabled()) {
							logger
									.debug("realizaNuevoPago(FormaDePago, double) - end");
						}
						return false;
					}
				} else {
					if (logger.isDebugEnabled()) {
						logger
								.debug("realizaNuevoPago(FormaDePago, double) - end");
					}
					return false;
				}
			}
		} else {
			PagoExcepcion p = new PagoExcepcion("El monto del pago debe ser un número positivo");
			MensajesVentanas.mensajeError(p.getMensaje());

			if (logger.isDebugEnabled()) {
				logger.debug("realizaNuevoPago(FormaDePago, double) - end");
			}
			return false;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("realizaNuevoPago(FormaDePago, double) - end");
		}
		return true;
	}
	/**
	 * Método isError
	 * 
	 * @return
	 * boolean
	 */
	public int getResultado() {
		if (logger.isDebugEnabled()) {
			logger.debug("getResultado() - start");
		}

		int returnint = (!pagosAgregados) ? 0 : (pagadoMontoTotal) ? 2 : 1;
		if (logger.isDebugEnabled()) {
			logger.debug("getResultado() - end");
		}
		return returnint;
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

}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"