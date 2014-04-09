/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : Rebajas.java
 * Creado por : Victor Medina <linux@epa.com.ve>
 * Creado en  : Feb 27, 2004 - 1:41:58 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1
 * Fecha       : Feb 27, 2004
 * Analista    : Victor Medina <linux@epa.com.ve>
 * Descripción : 
 * =============================================================================
 */
package com.becoblohm.cr.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EventListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.manejarventa.DetalleTransaccion;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.utiles.MensajesVentanas;
/**
 * @author vmedina
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Rebajas extends JDialog implements ComponentListener, KeyListener, MouseListener, EventListener, DocumentListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Rebajas.class);

	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JTextField jTextField = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JButton jButton2 = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JLabel jLabel = null;
	
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JScrollPane jScrollPane = null;
	private ModeloTabla modeloTablaAnularProducto = new ModeloTabla();
	private int filaSeleccionada=0;
	private int filaSeleccionadaInicial=0;
	private String  productoABuscar;
	
	private javax.swing.JTable jTable = null;
	private javax.swing.JTextField jTextField1 = null;
	private javax.swing.JTextField jTextField2 = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JRadioButton jRadioButton = null;
	private javax.swing.JPanel jPanel9 = null;
	private javax.swing.JRadioButton jRadioButton1 = null;
	private javax.swing.JRadioButton jRadioButton2 = null;
	private javax.swing.JPanel jPanel8 = null;
	private javax.swing.JPanel jPanel5 = null;
	private javax.swing.JPanel jPanel6 = null;
	private javax.swing.JPanel jPanel7 = null;
	private javax.swing.JPanel jPanel10 = null;
	
	/**
	 * Constructor para Rebajas.java
	 *
	 * @param filaACambiar - 1-Facturacion, 2-Apartado
	 */
	public Rebajas(int filaACambiar) {
		super(MensajesVentanas.ventanaActiva);
		initialize();
		
		filaSeleccionadaInicial = filaACambiar;
		
		jTextField.addKeyListener(this);
		jTextField.addMouseListener(this);
		
		jTextField1.addKeyListener(this);
		jTextField1.addMouseListener(this);
		
		jTextField2.addKeyListener(this);
		jTextField2.addMouseListener(this);
		
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);
		
		jButton2.addKeyListener(this);
		jButton2.addMouseListener(this);
		
		jTable.addKeyListener(this);
		jTable.addMouseListener(this);

		jRadioButton.addKeyListener(this);
		jRadioButton.addMouseListener(this);
		
		jRadioButton1.addKeyListener(this);
		jRadioButton1.addMouseListener(this);
		
		jRadioButton2.addKeyListener(this);
		jRadioButton2.addMouseListener(this);

		productoABuscar = ((DetalleTransaccion)CR.meVenta.getVenta().getDetallesTransaccion().elementAt(filaSeleccionadaInicial)).getProducto().getCodProducto().toString();
			
		jTextField.setText(productoABuscar);
		jTextField.selectAll();
		jTextField1.setText("1");
		jTextField2.setText("0");
		jRadioButton.setSelected(true);
		try {
			modeloTablaAnularProducto.llenarTabla(productoABuscar, false);
			if(modeloTablaAnularProducto.getRowCount() != 0)
				jTable.setRowSelectionInterval(0,0);
		} catch (ExcepcionCr e) {
			logger.error("Rebajas(int)", e);

			MensajesVentanas.mensajeError(e.getMensaje());
		} catch (ConexionExcepcion e) {
			logger.error("Rebajas(int)", e);

			MensajesVentanas.mensajeError(e.getMensaje());
		}
		
		
		//Objetos para leer del scanner
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

		this.setTitle("Cambiar Precio");
		this.setSize(650, 395);
		this.setContentPane(getJContentPane());
		this.setResizable(false);
		this.setUndecorated(true);		
		this.setModal(true);
		this.setBackground(new java.awt.Color(226,226,222));
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
			java.awt.FlowLayout layFlowLayout11 = new java.awt.FlowLayout();
			layFlowLayout11.setAlignment(java.awt.FlowLayout.CENTER);
			layFlowLayout11.setVgap(5);
			layFlowLayout11.setHgap(0);
			jContentPane.setLayout(layFlowLayout11);
			jContentPane.add(getJPanel3(), null);
			jContentPane.add(getJPanel1(), null);
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.RAISED));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
			jContentPane.setPreferredSize(new java.awt.Dimension(1046,256));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJContentPane() - end");
		}
		return jContentPane;
	}
	
	private void aplicarDescuento(int renglon, String descuento, String cantidad, boolean esPorcentaje, boolean esPrecioGarantizado){
		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarDescuento(int, String, String, boolean, boolean) - start");
		}
		
		double descuentoAAplicar;
		
		try {
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			//Tratamos de sacar algun numero del string
			descuentoAAplicar = new Double(descuento.trim()).doubleValue();
			float cant = new Float(cantidad.trim()).floatValue();
			
			if(esPrecioGarantizado) {
				//CR.meVenta.aplicarPrecioGarantizado(new Integer(CR.meVenta.obtenerRenglones(jTextField.getText().trim().toString().trim()).elementAt(renglon).toString()).intValue(), descuentoAAplicar, cant, esPorcentaje);
				CR.meVenta.aplicarPrecioGarantizado(new Integer(CR.meVenta.obtenerRenglones(productoABuscar, false).elementAt(renglon).toString()).intValue(), descuentoAAplicar, cant, esPorcentaje);
			} else {
				//CR.meVenta.aplicarDesctoPorDefecto(new Integer(CR.meVenta.obtenerRenglones(jTextField.getText().trim().toString().trim()).elementAt(renglon).toString()).intValue(), descuentoAAplicar, cant, esPorcentaje);
				CR.meVenta.aplicarDesctoPorDefecto(new Integer(CR.meVenta.obtenerRenglones(productoABuscar, false).elementAt(renglon).toString()).intValue(), descuentoAAplicar, cant, esPorcentaje);
			}
			dispose();
			try {
				finalize();
			} catch (Throwable e1) {
				logger
						.error(
								"aplicarDescuento(int, String, String, boolean, boolean)",
								e1);
			}
		} catch (NumberFormatException e) {
			logger.error(
					"aplicarDescuento(int, String, String, boolean, boolean)",
					e);

			MensajesVentanas.mensajeError("Debe llenar correctamente todos los datos solicitados para poder realizar la función.");
			CR.inputEscaner.getDocument().addDocumentListener(this);
		} catch (ConexionExcepcion e) {
			logger.error(
					"aplicarDescuento(int, String, String, boolean, boolean)",
					e);

			MensajesVentanas.mensajeError(e.getMensaje());
			CR.inputEscaner.getDocument().addDocumentListener(this);
		} catch (ExcepcionCr e) {
			logger.error(
					"aplicarDescuento(int, String, String, boolean, boolean)",
					e);

			MensajesVentanas.mensajeError(e.getMensaje());
			CR.inputEscaner.getDocument().addDocumentListener(this);
		}

		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarDescuento(int, String, String, boolean, boolean) - end");
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}
		
		//Mapeo de ESC sobre cualquier componente
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			dispose();
			try {
				finalize();
			} catch (Throwable e1) {
				logger.error("keyPressed(KeyEvent)", e1);
			}
		}
		
		else if(e.getKeyCode() == KeyEvent.VK_TAB){
			if(e.getSource().equals(this.getJTable())){
				this.getJRadioButton().requestFocus();
			}
			else if(e.getSource().equals(this.getJTextField1())){
				this.getJTextField2().requestFocus();
				this.getJTextField2().selectAll();
			}
			else if(e.getSource().equals(this.getJButton1())){
				this.getJTextField().requestFocus();
				this.getJTextField().selectAll();
			}
		} else {
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				if(e.getSource().equals(this.getJButton1())){
					CR.inputEscaner.getDocument().removeDocumentListener(this);
					dispose();
					try {
						finalize();
					} catch (Throwable e1) {
						logger.error("keyPressed(KeyEvent)", e1);
					}
				}
				
				else if(e.getSource().equals(this.getJButton2())){
					if(jTable.getSelectedRow() == -1){
						CR.inputEscaner.getDocument().removeDocumentListener(this);
						MensajesVentanas.mensajeError("Debe seleccionar un renglón.");
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} else {
						if(this.getJRadioButton().isSelected()){ //Aplicar porcentaje como descuento
							filaSeleccionada = jTable.getSelectedRow();
							this.aplicarDescuento(filaSeleccionada, CR.me.formatoNumerico(getJTextField2().getText()),CR.me.formatoNumerico(getJTextField1().getText()), true, false);
						} else if(this.getJRadioButton1().isSelected()) { //Aplicar Cambio de Precio
							filaSeleccionada = jTable.getSelectedRow();
							this.aplicarDescuento(filaSeleccionada, CR.me.formatoNumerico(getJTextField2().getText()), CR.me.formatoNumerico(getJTextField1().getText()), false, false);
						} else if(this.getJRadioButton2().isSelected()){ //Aplicar precio Garantizado
							filaSeleccionada = jTable.getSelectedRow();
							this.aplicarDescuento(filaSeleccionada, CR.me.formatoNumerico(getJTextField2().getText()),CR.me.formatoNumerico(getJTextField1().getText()), false, true);
						}
					}
				}
					
				else if(e.getSource().equals(this.getJTextField())) {
					CR.inputEscaner.getDocument().removeDocumentListener(this);
					try {
						productoABuscar = jTextField.getText().trim();
						modeloTablaAnularProducto.llenarTabla(productoABuscar, true);
						if(modeloTablaAnularProducto.getRowCount() > 0){
							jTable.setRowSelectionInterval(0,0);
							jTable.requestFocus();
						}
					} catch (ExcepcionCr e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
						modeloTablaAnularProducto.iniciarDatosTabla(modeloTablaAnularProducto.getColumnCount());
					} catch (ConexionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
						modeloTablaAnularProducto.iniciarDatosTabla(modeloTablaAnularProducto.getColumnCount());
					}
					CR.inputEscaner.getDocument().addDocumentListener(this);
				}
				
				else if(e.getSource().equals(this.getJTextField1())) {
					this.getJTextField2().requestFocus();
					this.getJTextField2().selectAll();
				}
				else if(e.getSource().equals(this.getJTextField2())) {
					this.getJButton2().requestFocus();
				}
				else if(e.getSource().equals(this.getJTable())) {
					if(jTable.getSelectedRow() != 0)
						jTable.setRowSelectionInterval(jTable.getSelectedRow()-1,jTable.getSelectedRow()-1);
					else 
						jTable.setRowSelectionInterval(jTable.getRowCount()-1,jTable.getRowCount()-1);
					
					this.getJRadioButton().requestFocus();
				}
				else if(e.getSource().equals(this.getJRadioButton())){
					this.getJRadioButton1().requestFocus();
				}
				
				else if(e.getSource().equals(this.getJRadioButton1())){
					this.getJRadioButton2().requestFocus();
				}
				else if(e.getSource().equals(this.getJRadioButton2())){
					this.getJTextField1().requestFocus();
					this.getJTextField1().selectAll();
				}
			} else {
				if(e.getKeyCode() == KeyEvent.VK_UP){
					if(e.getSource().equals(this.getJRadioButton1())){
						this.getJRadioButton().requestFocus();
					}
					else if(e.getSource().equals(this.getJRadioButton2())){
						this.getJRadioButton1().requestFocus();
					}
				} else {
					if(e.getKeyCode() == KeyEvent.VK_DOWN){
						if(e.getSource().equals(this.getJRadioButton())){
							this.getJRadioButton1().requestFocus();
						}
						else if(e.getSource().equals(this.getJRadioButton1())){
							this.getJRadioButton2().requestFocus();
						}
					} else {
						// Cualquier otra tecla que se presione mientras el foco lo tengan los RadioButton
						// Seran tomadas como para seleccionar los mismos
						if(e.getSource().equals(this.getJRadioButton())) {
							if (jLabel2.getText().equalsIgnoreCase("Precio: "))
								this.getJTextField2().setText("");
							jLabel2.setText("Porcentaje: ");
							jRadioButton.setSelected(true);
						}
						else if(e.getSource().equals(this.getJRadioButton1())) {
							if (jLabel2.getText().equalsIgnoreCase("Porcentaje: "))
								this.getJTextField2().setText("");
							jLabel2.setText("Precio: ");
							jRadioButton1.setSelected(true);
						}
						else if(e.getSource().equals(this.getJRadioButton2())) {
							if (jLabel2.getText().equalsIgnoreCase("Porcentaje: "))
								this.getJTextField2().setText("");
							jLabel2.setText("Precio: ");
							jRadioButton2.setSelected(true);
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

		if (e.getSource().equals(getJTextField1())) {
			String textoFormateado = CR.me.formatearCampoNumerico(getJTextField1().getText(), e);
			if (textoFormateado!=null)
				getJTextField1().setText(textoFormateado);
	}
		if (e.getSource().equals(getJTextField2())) {
			String textoFormateado = CR.me.formatearCampoNumerico(getJTextField2().getText(), e);
			if (textoFormateado!=null)
				getJTextField2().setText(textoFormateado);
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
		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - start");
		}
		
		if(e.getSource().equals(jTable)){
			if(jTable.getSelectedRow() == -1){
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				MensajesVentanas.mensajeError("Debe seleccionar un renglón.");
				CR.inputEscaner.getDocument().addDocumentListener(this);
			}
			else{
				filaSeleccionada = jTable.getSelectedRow();
			}
		}
				
		//Mapeo de eventos de Mouse
		else if(e.getSource().equals(jButton1)){
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			dispose();
			try {
				finalize();
			} catch (Throwable e1) {
				logger.error("mouseClicked(MouseEvent)", e1);
			}
		}
		
		else if(e.getSource().equals(jButton2)){
			if(jTable.getSelectedRow() == -1){
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				MensajesVentanas.mensajeError("Debe seleccionar un renglón.");
				CR.inputEscaner.getDocument().addDocumentListener(this);
			} else {
				if(this.getJRadioButton().isSelected()){ //Aplicar porcentaje como descuento
					filaSeleccionada = jTable.getSelectedRow();
					this.aplicarDescuento(filaSeleccionada, CR.me.formatoNumerico(getJTextField2().getText()), CR.me.formatoNumerico(getJTextField1().getText()), true, false);
				} else if(this.getJRadioButton1().isSelected()) { //Aplicar Cambio de Precio
					filaSeleccionada = jTable.getSelectedRow();
					this.aplicarDescuento(filaSeleccionada, CR.me.formatoNumerico(getJTextField2().getText()), CR.me.formatoNumerico(getJTextField1().getText()), false, false);
				} else if(this.getJRadioButton2().isSelected()){ //Aplicar precio Garantizado
					filaSeleccionada = jTable.getSelectedRow();
					this.aplicarDescuento(filaSeleccionada, CR.me.formatoNumerico(getJTextField2().getText()), CR.me.formatoNumerico(getJTextField1().getText()), false, true);
				}
			}
		}
		
		else if(e.getSource().equals(this.getJRadioButton())) {
			if (jLabel2.getText().equalsIgnoreCase("Precio: "))
				this.getJTextField2().setText("");
			jLabel2.setText("Porcentaje: ");
		}
		else if(e.getSource().equals(this.getJRadioButton1()) || e.getSource().equals(this.getJRadioButton2())) {
			if (jLabel2.getText().equalsIgnoreCase("Porcentaje: "))
				this.getJTextField2().setText("");
			jLabel2.setText("Precio: ");
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
			layFlowLayout1.setHgap(5);
			layFlowLayout1.setVgap(0);
			jPanel.setLayout(layFlowLayout1);
			jPanel.add(getJTextField(), null);
			jPanel.setPreferredSize(new java.awt.Dimension(590,45));
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Identificador", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
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
			java.awt.FlowLayout layFlowLayout12 = new java.awt.FlowLayout();
			layFlowLayout12.setAlignment(java.awt.FlowLayout.RIGHT);
			layFlowLayout12.setHgap(0);
			layFlowLayout12.setVgap(0);
			jPanel1.setLayout(layFlowLayout12);
			jPanel1.add(getJButton2(), null);
			jPanel1.add(getJButton1(), null);
			jPanel1.setPreferredSize(new java.awt.Dimension(620,35));
			jPanel1.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel1() - end");
		}
		return jPanel1;
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
			jTextField.setPreferredSize(new java.awt.Dimension(200,20));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField() - end");
		}
		return jTextField;
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
			jButton1.setText("Cancelar");
			jButton1.setBackground(new java.awt.Color(226,226,222));
			jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
			
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
			jButton2.setText("Aceptar");
			jButton2.setBackground(new java.awt.Color(226,226,222));
			jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton2() - end");
		}
		return jButton2;
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
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
			layFlowLayout2.setAlignment(java.awt.FlowLayout.CENTER);
			layFlowLayout2.setVgap(0);
			layFlowLayout2.setHgap(0);
			jPanel3.setLayout(layFlowLayout2);
			jPanel3.add(getJPanel2(), null);
			jPanel3.add(getJPanel(), null);
			jPanel3.add(getJPanel4(), null);
			jPanel3.add(getJPanel9(), null);
			jPanel3.add(getJPanel8(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(620,310));
			jPanel3.setBackground(new java.awt.Color(242,242,238));
			jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel3() - end");
		}
		return jPanel3;
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
			java.awt.FlowLayout layFlowLayout3 = new java.awt.FlowLayout();
			layFlowLayout3.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout3.setVgap(5);
			layFlowLayout3.setHgap(5);
			jPanel2.setLayout(layFlowLayout3);
			jPanel2.add(getJLabel(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(620,40));
			jPanel2.setBackground(new java.awt.Color(69,107,127));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel2() - end");
		}
		return jPanel2;
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
			jLabel.setText("Cambiar Precio");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel.setForeground(java.awt.Color.white);
			jLabel.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/money.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel() - end");
		}
		return jLabel;
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
			jPanel4.add(getJScrollPane(), null);
			jPanel4.setPreferredSize(new java.awt.Dimension(590,120));
			jPanel4.setBackground(new java.awt.Color(242,242,238));
			jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle de la Transacción", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel4() - end");
		}
		return jPanel4;
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
			jScrollPane.setPreferredSize(new java.awt.Dimension(570,80));
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
			jTable = new javax.swing.JTable(modeloTablaAnularProducto);
			jTable.getTableHeader().setReorderingAllowed(false) ;
			jTable.getColumnModel().getColumn(0).setPreferredWidth(88);
			jTable.getColumnModel().getColumn(1).setPreferredWidth(150);
			jTable.getColumnModel().getColumn(2).setPreferredWidth(65);
			jTable.getColumnModel().getColumn(3).setPreferredWidth(75);
			jTable.getColumnModel().getColumn(4).setPreferredWidth(75);
			jTable.getColumnModel().getColumn(5).setPreferredWidth(76);
			jTable.getColumnModel().getColumn(6).setPreferredWidth(35);
			jTable.getColumnModel().getColumn(7).setPreferredWidth(50);
			jTable.setLocale(new java.util.Locale("es", "VE", ""));
			jTable.setRowHeight(jTable.getRowHeight()+3);
			jTable.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jTable.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTable() - end");
		}
		return jTable;
	}
	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField1() - start");
		}

		if(jTextField1 == null) {
			jTextField1 = new javax.swing.JTextField();
			jTextField1.setPreferredSize(new java.awt.Dimension(80,20));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField1() - end");
		}
		return jTextField1;
	}
	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField2() - start");
		}

		if(jTextField2 == null) {
			jTextField2 = new javax.swing.JTextField();
			jTextField2.setPreferredSize(new java.awt.Dimension(80,20));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField2() - end");
		}
		return jTextField2;
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
			jLabel1.setText("Cantidad: ");
			jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel1() - end");
		}
		return jLabel1;
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
			jLabel2.setText("Porcentaje: ");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel2() - end");
		}
		return jLabel2;
	}
	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private javax.swing.JRadioButton getJRadioButton() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJRadioButton() - start");
		}

		if(jRadioButton == null) {
			jRadioButton = new javax.swing.JRadioButton();
			jRadioButton.setText("Porcentaje");
			jRadioButton.setBackground(new java.awt.Color(242,242,238));
			jRadioButton.setPreferredSize(new java.awt.Dimension(200,18));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJRadioButton() - end");
		}
		return jRadioButton;
	}
	/**
	 * This method initializes jPanel9
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel9() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel9() - start");
		}

		if(jPanel9 == null) {
			jPanel9 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout14 = new java.awt.FlowLayout();
			layFlowLayout14.setVgap(0);
			layFlowLayout14.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout14.setHgap(5);
			jPanel9.setLayout(layFlowLayout14);
			
			// Se arma el Grupo de Botones de tipo RadioButton
			ButtonGroup group = new ButtonGroup();
			group.add(getJRadioButton());
			group.add(getJRadioButton1());
			group.add(getJRadioButton2());

			jPanel9.add(getJRadioButton(), null);
			jPanel9.add(getJRadioButton1(), null);
			jPanel9.add(getJRadioButton2(), null);
			jPanel9.setPreferredSize(new java.awt.Dimension(295,85));
			jPanel9.setBackground(new java.awt.Color(242,242,238));
			jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tipo de Cambio", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel9() - end");
		}
		return jPanel9;
	}
	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private javax.swing.JRadioButton getJRadioButton1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJRadioButton1() - start");
		}

		if(jRadioButton1 == null) {
			jRadioButton1 = new javax.swing.JRadioButton();
			jRadioButton1.setText("Cambio de Precio");
			jRadioButton1.setBackground(new java.awt.Color(242,242,238));
			jRadioButton1.setPreferredSize(new java.awt.Dimension(200,18));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJRadioButton1() - end");
		}
		return jRadioButton1;
	}
	/**
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private javax.swing.JRadioButton getJRadioButton2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJRadioButton2() - start");
		}

		if(jRadioButton2 == null) {
			jRadioButton2 = new javax.swing.JRadioButton();
			jRadioButton2.setText("Precio Garantizado");
			jRadioButton2.setBackground(new java.awt.Color(242,242,238));
			jRadioButton2.setPreferredSize(new java.awt.Dimension(200,18));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJRadioButton2() - end");
		}
		return jRadioButton2;
	}
	/**
	 * This method initializes jPanel8
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel8() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel8() - start");
		}

		if(jPanel8 == null) {
			jPanel8 = new javax.swing.JPanel();
			java.awt.GridLayout layGridLayout3 = new java.awt.GridLayout();
			layGridLayout3.setRows(2);
			layGridLayout3.setColumns(2);
			layGridLayout3.setHgap(0);
			jPanel8.setLayout(layGridLayout3);
			jPanel8.add(getJPanel7(), null);
			jPanel8.add(getJPanel5(), null);
			jPanel8.add(getJPanel10(), null);
			jPanel8.add(getJPanel6(), null);
			jPanel8.setPreferredSize(new java.awt.Dimension(295,85));
			jPanel8.setBackground(new java.awt.Color(242,242,238));
			jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Rebaja", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel8() - end");
		}
		return jPanel8;
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
			java.awt.FlowLayout layFlowLayout4 = new java.awt.FlowLayout();
			layFlowLayout4.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout4.setHgap(0);
			jPanel5.setLayout(layFlowLayout4);
			jPanel5.add(getJTextField1(), null);
			jPanel5.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel5() - end");
		}
		return jPanel5;
	}
	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel6() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel6() - start");
		}

		if(jPanel6 == null) {
			jPanel6 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout5 = new java.awt.FlowLayout();
			layFlowLayout5.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout5.setHgap(0);
			jPanel6.setLayout(layFlowLayout5);
			jPanel6.add(getJTextField2(), null);
			jPanel6.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel6() - end");
		}
		return jPanel6;
	}
	/**
	 * This method initializes jPanel7
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel7() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel7() - start");
		}

		if(jPanel7 == null) {
			jPanel7 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout6 = new java.awt.FlowLayout();
			layFlowLayout6.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout6.setHgap(15);
			jPanel7.setLayout(layFlowLayout6);
			jPanel7.add(getJLabel1(), null);
			jPanel7.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel7() - end");
		}
		return jPanel7;
	}
	/**
	 * This method initializes jPanel10
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel10() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel10() - start");
		}

		if(jPanel10 == null) {
			jPanel10 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout7 = new java.awt.FlowLayout();
			layFlowLayout7.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout7.setHgap(15);
			jPanel10.setLayout(layFlowLayout7);
			jPanel10.add(getJLabel2(), null);
			jPanel10.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel10() - end");
		}
		return jPanel10;
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
	
	public void lecturaEscaner(String codEscaner) {
		if (logger.isDebugEnabled()) {
			logger.debug("lecturaEscaner(String) - start");
		}

		String codProducto;
		try {
			codProducto = MediadorBD.obtenerCodigoInterno(codEscaner.substring(0,11));
			if (codProducto==null) {
				codProducto = MediadorBD.obtenerCodigoInterno(codEscaner);
				jTextField.setText(codEscaner);
			} else
				jTextField.setText(codEscaner.substring(0,11));
		} catch (Exception e1) {
			logger.error("lecturaEscaner(String)", e1);

			codProducto = MediadorBD.obtenerCodigoInterno(codEscaner);
			jTextField.setText(codEscaner);
		}
		
		if (codProducto==null)
			productoABuscar = codEscaner;
		else		
			productoABuscar = codProducto;
			
		try {
			modeloTablaAnularProducto.llenarTabla(productoABuscar, false);
			if(modeloTablaAnularProducto.getRowCount() != 0)
				jTable.setRowSelectionInterval(0,0);
		} catch (ExcepcionCr e1) {
			logger.error("lecturaEscaner(String)", e1);

			modeloTablaAnularProducto.iniciarDatosTabla(modeloTablaAnularProducto.getColumnCount());
			MensajesVentanas.mensajeError(e1.getMensaje());
		} catch (ConexionExcepcion e1) {
			logger.error("lecturaEscaner(String)", e1);

			modeloTablaAnularProducto.iniciarDatosTabla(modeloTablaAnularProducto.getColumnCount());
			MensajesVentanas.mensajeError(e1.getMensaje());
		}
		
		jTextField.selectAll();
		jTextField1.setText("1");
		jTextField2.setText("0");
		jRadioButton.setSelected(true);

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
}