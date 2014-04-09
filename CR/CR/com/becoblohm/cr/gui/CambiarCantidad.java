/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : CambiarCantidad.java
 * Creado por : Victor Medina <linux@epa.com.ve>
 * Creado en  : Feb 12, 2004 - 2:32:33 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1.0
 * Fecha       : 25/05/2004 01:27:34 PM
 * Analista    : GMARTINELLI
 * Descripción : Agregado el atributo casoCantidad para identificar cambio de cantidad en Devolución o Facturación y sus respectivas
 * 		 		 condiciones. 
 * =============================================================================
 * Versión     : 1
 * Fecha       : Feb 12, 2004
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

import javax.swing.JDialog;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.ProductoExcepcion;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarservicio.DetalleServicio;
import com.becoblohm.cr.manejarventa.DetalleTransaccion;
import com.becoblohm.cr.utiles.MensajesVentanas;

/**
 * @author vmedina
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CambiarCantidad extends JDialog  implements ComponentListener, KeyListener, MouseListener, EventListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(CambiarCantidad.class);

	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JLabel jLabel = null;
	private javax.swing.JTextField jTextField = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JPanel jPanel3 = null;
	private int filaACambiar = -1;
	private int casoCantidad;
	private String codProducto;
	
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JLabel jLabel1 = null;
	private boolean ejecutado = false;
	/**
	 * This is the default constructor
	 * @param caso 1 - Facturacion, 2 - Apartado
	 */
	public CambiarCantidad(int caso) {
		super(MensajesVentanas.ventanaActiva);
		initialize();
		casoCantidad = caso;
		try {
			switch (caso) {
				case 1: 
					String codProd = ((DetalleTransaccion)CR.meVenta.getVenta().getDetallesTransaccion().lastElement()).getProducto().getCodProducto().toString();
					int longitud = codProd.length();
					if ((Sesion.getLongitudCodigoInterno()>-1)&&(longitud>Sesion.getLongitudCodigoInterno())) {
						codProd = codProd.substring(codProd.length()-Sesion.getLongitudCodigoInterno()); 
						for (int j=0;j<longitud-Sesion.getLongitudCodigoInterno();j++) {
							codProd = " " + codProd; 
						}
					}
					this.codProducto = codProd;
						break;
				case 2:
					String codProd2 = ((DetalleServicio)CR.meServ.getApartado().getDetallesServicio().lastElement()).getProducto().getCodProducto().toString();
					int longitud2 = codProd2.length();
					if ((Sesion.getLongitudCodigoInterno()>-1)&&(longitud2>Sesion.getLongitudCodigoInterno())) {
						codProd2 = codProd2.substring(codProd2.length()-Sesion.getLongitudCodigoInterno()); 
						for (int j=0;j<longitud2-Sesion.getLongitudCodigoInterno();j++) {
							codProd2 = " " + codProd2; 
						}
					}
				 	this.codProducto = codProd2;
						break;
				case 3:
					String codProd3 = ((DetalleServicio)CR.meServ.getListaRegalos().getDetallesServicio().lastElement()).getProducto().getCodProducto().toString();
					int longitud3 = codProd3.length();
					if ((Sesion.getLongitudCodigoInterno()>-1)&&(longitud3>Sesion.getLongitudCodigoInterno())) {
						codProd3 = codProd3.substring(codProd3.length()-Sesion.getLongitudCodigoInterno()); 
						for (int j=0;j<longitud3-Sesion.getLongitudCodigoInterno();j++) {
							codProd3 = " " + codProd3; 
						}
					}
					this.codProducto = codProd3;
					break;
			}
			jLabel1.setText(jLabel1.getText() + codProducto);
		} catch (Exception e) {
			logger.error("CambiarCantidad(int)", e);
		}
	}
	
	/**
	 * @param caso 1 - Facturacion, 2 - Apartado
	 */
	public CambiarCantidad(int filaAModificar, int caso) {
		super(MensajesVentanas.ventanaActiva);
		initialize();
		filaACambiar = filaAModificar;
		casoCantidad = caso;
		try {
			switch (caso) {
				case 1: 
					String codProd = ((DetalleTransaccion)CR.meVenta.getVenta().getDetallesTransaccion().elementAt(filaAModificar)).getProducto().getCodProducto().toString();
					int longitud = codProd.length();
					if ((Sesion.getLongitudCodigoInterno()>-1)&&(longitud>Sesion.getLongitudCodigoInterno())) {
						codProd = codProd.substring(codProd.length()-Sesion.getLongitudCodigoInterno()); 
						for (int j=0;j<longitud-Sesion.getLongitudCodigoInterno();j++) {
							codProd = " " + codProd; 
						}
					}
					this.codProducto = codProd;
						break;
				case 2: 
					String codProd2 = ((DetalleServicio)CR.meServ.getApartado().getDetallesServicio().elementAt(filaAModificar)).getProducto().getCodProducto().toString();
					int longitud2 = codProd2.length();
					if ((Sesion.getLongitudCodigoInterno()>-1)&&(longitud2>Sesion.getLongitudCodigoInterno())) {
						codProd2 = codProd2.substring(codProd2.length()-Sesion.getLongitudCodigoInterno()); 
						for (int j=0;j<longitud2-Sesion.getLongitudCodigoInterno();j++) {
							codProd2 = " " + codProd2; 
						}
					}
					this.codProducto = codProd2;
						break;
				case 3: 
					String codProd3 = ((DetalleServicio)CR.meServ.getListaRegalos().getDetallesServicio().elementAt(filaAModificar)).getProducto().getCodProducto().toString();
					int longitud3 = codProd3.length();
					if ((Sesion.getLongitudCodigoInterno()>-1)&&(longitud3>Sesion.getLongitudCodigoInterno())) {
						codProd3 = codProd3.substring(codProd3.length()-Sesion.getLongitudCodigoInterno()); 
						for (int j=0;j<longitud3-Sesion.getLongitudCodigoInterno();j++) {
							codProd3 = " " + codProd3; 
						}
					}
					this.codProducto = codProd3;
					break;
			}
			jLabel1.setText(jLabel1.getText() + codProducto);
		} catch (Exception e) {
			logger.error("CambiarCantidad(int, int)", e);
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

		this.setTitle("Agregar Producto");
		this.setBackground(new java.awt.Color(226,226,222));
		this.setSize(385, 242);
		this.setContentPane(getJContentPane());
		this.setModal(true);
		this.addComponentListener(this);
				
		jTextField.addKeyListener(this);
		
		jButton.addKeyListener(this);
		jButton.addMouseListener(this);
		
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);

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
			jContentPane.setLayout(new java.awt.FlowLayout());
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJPanel1(), null);
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.RAISED));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
			jContentPane.setPreferredSize(new java.awt.Dimension(729,100));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJContentPane() - end");
		}
		return jContentPane;
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
			jLabel.setText("Agregar Producto");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel.setForeground(java.awt.Color.white);
			jLabel.setIcon(new javax.swing.ImageIcon(Object.class.getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/shoppingbasket_add.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel() - end");
		}
		return jLabel;
	}
	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	public javax.swing.JTextField getJTextField() {
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
			jButton.setText("Aceptar");
			jButton.setBackground(new java.awt.Color(226,226,222));
			jButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
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
			jButton1.setText("Cancelar");
			jButton1.setBackground(new java.awt.Color(226,226,222));
			jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton1() - end");
		}
		return jButton1;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}
		
		//Mapeo de ESC
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
			dispose();
		}
		
		//Mapeo de ENTER desde el cuadro de texto
		else if(e.getKeyCode()==KeyEvent.VK_ENTER){
			if(e.getSource().equals(jTextField) || e.getSource().equals(jButton)){
				float cantidadEvaluar = 0;
				try {
					cantidadEvaluar = new Float(CR.me.formatoNumerico(getJTextField().getText())).floatValue();
					try {
						switch (casoCantidad) {
							case 1:	if(filaACambiar == -1)
										CR.meVenta.cambiarCantidad(cantidadEvaluar);	
									else
										CR.meVenta.cambiarCantidad(cantidadEvaluar, filaACambiar);
									break;
							case 2:	if(filaACambiar == -1)
										CR.meServ.cambiarCantidad(cantidadEvaluar);	
									else
										CR.meServ.cambiarCantidad(cantidadEvaluar, filaACambiar);
									break;
							case 3:	if(filaACambiar == -1)
										CR.meServ.cambiarCantidadLR(cantidadEvaluar);	
									else
										CR.meServ.cambiarCantidadLR(cantidadEvaluar, filaACambiar);
									break;
						}
						this.ejecutado = true;
						dispose();
					} catch (ConexionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
						jTextField.selectAll();
					} catch (ExcepcionCr e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
						jTextField.selectAll();
					}	
				} catch(Exception f) {
					logger.error("keyPressed(KeyEvent)", f);
					f.printStackTrace();
					ProductoExcepcion pe = new ProductoExcepcion("Cantidad invalida. Debe especificarse un numero decimal");
					MensajesVentanas.mensajeError(pe.getMensaje());
					jTextField.selectAll();
				}
			}
			
			else if(e.getSource().equals(jButton1)){
				dispose();
			}
		}
		
		// Mapeos de KeyPressed SPACE bar sobre los botones
		else if(e.getKeyCode()==KeyEvent.VK_SPACE){
			if(e.getSource().equals(jButton)){
				float cantidadEvaluar = 0;
				try {
					cantidadEvaluar = new Float(CR.me.formatoNumerico(getJTextField().getText())).floatValue();
					try {
						switch (casoCantidad) {
							case 1:	if(filaACambiar == -1)
										CR.meVenta.cambiarCantidad(cantidadEvaluar);	
									else
										CR.meVenta.cambiarCantidad(cantidadEvaluar, filaACambiar);
									break;
							case 2:	if(filaACambiar == -1)
										CR.meServ.cambiarCantidad(cantidadEvaluar);	
									else
										CR.meServ.cambiarCantidad(cantidadEvaluar, filaACambiar);
									break;
							case 3:	if(filaACambiar == -1)
										CR.meServ.cambiarCantidadLR(cantidadEvaluar);	
									else
										CR.meServ.cambiarCantidadLR(cantidadEvaluar, filaACambiar);
									break;									
						}
						this.ejecutado = true;
						dispose();
					} catch (ConexionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
						jTextField.selectAll();
					} catch (ExcepcionCr e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
						jTextField.selectAll();
					}			
				} catch(Exception f) {
					logger.error("keyPressed(KeyEvent)", f);

					ProductoExcepcion pe = new ProductoExcepcion("Cantidad invalida. Debe especificarse un numero decimal");
					MensajesVentanas.mensajeError(pe.getMensaje());
					jTextField.selectAll();
				}
			}

			else if(e.getSource().equals(jButton1)){
				dispose();
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
	public void mouseReleased(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - start");
		}

		if(e.getSource().equals(jButton)){
			float cantidadEvaluar = 0;
			try {
				cantidadEvaluar = new Float(CR.me.formatoNumerico(getJTextField().getText())).floatValue();
				try {
					switch (casoCantidad) {
						case 1:	if(filaACambiar == -1)
									CR.meVenta.cambiarCantidad(cantidadEvaluar);	
								else
									CR.meVenta.cambiarCantidad(cantidadEvaluar, filaACambiar);
								break;
						case 2:	if(filaACambiar == -1)
									CR.meServ.cambiarCantidad(cantidadEvaluar);	
								else
									CR.meServ.cambiarCantidad(cantidadEvaluar, filaACambiar);
								break;
						case 3:	if(filaACambiar == -1)
									CR.meServ.cambiarCantidadLR(cantidadEvaluar);	
								else
									CR.meServ.cambiarCantidadLR(cantidadEvaluar, filaACambiar);
								break;
					}
					this.ejecutado = true;
					dispose();
				} catch (ConexionExcepcion e1) {
					logger.error("mouseClicked(MouseEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
					jTextField.selectAll();
				} catch (ExcepcionCr e1) {
					logger.error("mouseClicked(MouseEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
					jTextField.selectAll();
				}			
			} catch(Exception f) {
				logger.error("mouseClicked(MouseEvent)", f);
				f.printStackTrace();
				ProductoExcepcion pe = new ProductoExcepcion("Cantidad invalida. Debe especificarse un numero decimal");
				MensajesVentanas.mensajeError(pe.getMensaje());
				jTextField.selectAll();
			}
		}
		
		else if(e.getSource().equals(jButton1)){
			dispose();
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
			layFlowLayout1.setVgap(0);
			layFlowLayout1.setHgap(0);
			jPanel.setLayout(layFlowLayout1);
			jPanel.add(getJPanel2(), null);
			jPanel.add(getJPanel4(), null);
			jPanel.add(getJPanel3(), null);
			jPanel.setPreferredSize(new java.awt.Dimension(355,155));
			jPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
			jPanel.setBackground(new java.awt.Color(242,242,238));
			jPanel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
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
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
			layFlowLayout2.setAlignment(java.awt.FlowLayout.RIGHT);
			layFlowLayout2.setVgap(0);
			layFlowLayout2.setHgap(0);
			jPanel1.setLayout(layFlowLayout2);
			jPanel1.add(getJButton(), null);
			jPanel1.add(getJButton1(), null);
			jPanel1.setPreferredSize(new java.awt.Dimension(353,35));
			jPanel1.setBackground(new java.awt.Color(226,226,222));
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
			java.awt.FlowLayout layFlowLayout3 = new java.awt.FlowLayout();
			layFlowLayout3.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout3.setVgap(10);
			layFlowLayout3.setHgap(5);
			jPanel2.setLayout(layFlowLayout3);
			jPanel2.add(getJLabel(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(353,50));
			jPanel2.setBackground(new java.awt.Color(69,107,127));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel2() - end");
		}
		return jPanel2;
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
			java.awt.FlowLayout layFlowLayout4 = new java.awt.FlowLayout();
			layFlowLayout4.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel3.setLayout(layFlowLayout4);
			jPanel3.add(getJTextField(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(325,50));
			layFlowLayout4.setVgap(0);
			layFlowLayout4.setHgap(0);
			jPanel3.setBackground(new java.awt.Color(242,242,238));
			jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cantidad a Agregar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
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
			java.awt.FlowLayout layFlowLayout11 = new java.awt.FlowLayout();
			layFlowLayout11.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel4.setLayout(layFlowLayout11);
			jPanel4.add(getJLabel1(), null);
			jPanel4.setBackground(new java.awt.Color(242,242,238));
			jPanel4.setPreferredSize(new java.awt.Dimension(353,30));
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
			jLabel1.setText("Producto: ");
			jLabel1.setPreferredSize(new java.awt.Dimension(300,20));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel1() - end");
		}
		return jLabel1;
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

	public boolean isEjecutado() {
		return ejecutado;
	}
}
