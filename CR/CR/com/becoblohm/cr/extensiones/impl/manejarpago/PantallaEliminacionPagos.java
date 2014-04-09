/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.manejadorpago
 * Programa   : PantallaEliminacionPagos.java
 * Creado por : gmartinelli
 * Creado en  : 23-sep-04 11:10 a.m.
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
import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.manejarventa.Venta;
import com.becoblohm.cr.utiles.MensajesVentanas;

/**
 * Descripción:
 * 
 */

public class PantallaEliminacionPagos extends JDialog implements ComponentListener,  KeyListener, MouseListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(PantallaEliminacionPagos.class);

	private DecimalFormat df = new DecimalFormat("#,##0.00");
	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JPanel jPanel5 = null;
	private javax.swing.JScrollPane jScrollPane = null;
	private javax.swing.JTable jTable = null;
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
	protected Vector<Pago> pagosRealizados;
	private double montoTransaccion;
	private double montoPagado = 0;
	private Cliente cliente;
	
	/**
	 * This is the default constructor
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public PantallaEliminacionPagos(double mtoTrans, Vector<Pago> pRealizados, Cliente cte) {
		super(MensajesVentanas.ventanaActiva);
		this.cliente = cte;
		initialize();
		
		jButton.addKeyListener(this);
		jButton.addMouseListener(this);
		
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);
		
		jTable.addKeyListener(this);
		jTable.addMouseListener(this);

		jScrollPane.addKeyListener(this);
		jScrollPane.addMouseListener(this);

		jLabel5.setText(df.format(mtoTrans));
		pagosRealizados = pRealizados;
		this.montoTransaccion = mtoTrans;
		for (int i=0; i<pRealizados.size(); i++) {
			this.montoPagado += ((Pago)pRealizados.elementAt(i)).getMonto();
		}
		this.repintarPantalla();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	protected void initialize() {
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
	public void keyPressed(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}

		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			dispose();
		}
		
		else if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(e.getSource().equals(this.getJButton())) {
				dispose();
			} else {
				if(e.getSource().equals(this.getJButton1())) {
					if (getJTable().getSelectedRow()>-1) {
						if(MensajesVentanas.preguntarSiNo("Desea eliminar el pago en " + ((Pago)pagosRealizados.elementAt(getJTable().getSelectedRow())).getFormaPago().getNombre() + " por " + Sesion.getTienda().getMonedaBase() + " " + df.format(((Pago)pagosRealizados.elementAt(getJTable().getSelectedRow())).getMonto())) == 0) {
							this.eliminarPago(getJTable().getSelectedRow());
							dispose();
						}
					} else {
						MensajesVentanas.aviso("Debe seleccionar la forma de pago a eliminar");
					}
				} else {
					if(e.getSource().equals(this.getJTable())) {
						if (jTable.getSelectedRow()==0) {
							jTable.setRowSelectionInterval(jTable.getRowCount()-1,jTable.getRowCount()-1);
						} else {
							jTable.setRowSelectionInterval(jTable.getSelectedRow()-1,jTable.getSelectedRow()-1);
						}
						jTable.transferFocus();
					}
				}				
			}
		} else if((e.getKeyCode() == KeyEvent.VK_TAB)&&e.getSource().equals(this.getJTable())){
			jTable.transferFocus();
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

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - start");
		}

		if(e.getSource().equals(this.getJButton())) {
			dispose();
		}
			
		if(e.getSource().equals(this.getJButton1())) {
			if (getJTable().getSelectedRow()>-1) {
				if(MensajesVentanas.preguntarSiNo("Desea eliminar el pago en " + ((Pago)pagosRealizados.elementAt(getJTable().getSelectedRow())).getFormaPago().getNombre() + " por " + Sesion.getTienda().getMonedaBase() + " " + df.format(((Pago)pagosRealizados.elementAt(getJTable().getSelectedRow())).getMonto())) == 0) {
					this.eliminarPago(getJTable().getSelectedRow());
					if(pagosRealizados.size()==0){
						if(Venta.donacionesRegistradas.size()!=0 && pagosRealizados.size()==0){
							Venta.donacionesRegistradas.clear();
							MensajesVentanas.aviso("Debe volver a registrar las donaciones");
							CR.me.mostrarAviso("Eliminadas todas las donaciones de la venta", true);
						}
						Venta.regalosRegistrados.clear();
						Venta.promocionesRegistradas.clear();
						CR.me.getPromoMontoCantidad().clear();
					}
					dispose();
				}
			} else {
				MensajesVentanas.aviso("Debe seleccionar la forma de pago a eliminar");
			}
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
			jButton1.setText("Eliminar");
			jButton1.setBackground(new java.awt.Color(226,226,222));
			jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/quitar.png")));
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
			jLabel1.setText("Eliminar Pago");
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
			jPanel5.setPreferredSize(new java.awt.Dimension(535,193));
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
			jScrollPane.setPreferredSize(new java.awt.Dimension(505,115));
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
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTable() - end");
		}
		return jTable;
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
			restoPago = this.montoTransaccion - this.montoPagado;
			jLabel7.setText(df.format(restoPago));
		} catch (Exception ex){
			logger.error("repintarPantalla()", ex);

			modeloTablaDetallePagos.iniciarDatosTabla(this.getJTable().getColumnCount());
			jLabel7.setText(jLabel5.getText());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("repintarPantalla() - end");
		}
	}

	protected void eliminarPago(int indice) {
		if (logger.isDebugEnabled()) {
			logger.debug("eliminarPago(int) - start");
		}
		
		try {
			FormaDePago formaPago = ((Pago)pagosRealizados.elementAt(indice)).getFormaPago();
			if ((formaPago.isValidarSaldoCliente()) && (Sesion.isCajaEnLinea())) // Si es pago con condicional
				CR.meVenta.eliminarPagoCondicional(((Pago)pagosRealizados.elementAt(indice)));
			pagosRealizados.remove(indice);
		} catch (PagoExcepcion e) {
			MensajesVentanas.aviso("No se puede anular la venta activa");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("eliminarPago(int) - end");
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

}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"
