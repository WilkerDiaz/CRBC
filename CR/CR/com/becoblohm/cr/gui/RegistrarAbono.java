/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : RegistrarAbono.java
 * Creado por : irojas
 * Creado en  : 29-abr-04 9:12:07
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1
 * Fecha       : 29-abr-04 9:12:07
 * Analista    : irojas
 * Descripción : Implementación inicial.
 * =============================================================================
 *  Versión     : 1.1
 * Fecha       : 23-jun-06 9:12:07
 * Analista    : yzambrano
 * Descripción : validar monto de abono inicail menor al monto total del apartado
 * =============================================================================
 */
package com.becoblohm.cr.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.EventListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JDialog;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.utiles.MensajesVentanas;
/**
 * Descripción:
 * 
 */

public class RegistrarAbono extends JDialog implements ComponentListener, KeyListener, MouseListener, EventListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RegistrarAbono.class);

	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JTextField jTextField = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JLabel jLabel1 = null;
	
	private Vector<Pago> abonoAnterior = null;
	private boolean registroInicial = false;
	private double montoMinimo = 0;
	private boolean canceladaEjecucion = false;
	private boolean reversarEnError = false;
	private boolean imprimirComprobante = true;
	private DecimalFormat df = new DecimalFormat("#,##0.00");
	
	/**
	 * This is the default constructor
	 */
	public RegistrarAbono(double minimo, boolean rEnError, boolean imprimir) {
		super(MensajesVentanas.ventanaActiva);
		initialize();
		
		jTextField.setText(df.format(minimo));
		jTextField.selectAll();
		
		jTextField.addKeyListener(this);
		
		jButton.addKeyListener(this);
		jButton.addMouseListener(this);
		
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);
		
		this.reversarEnError = rEnError;
		this.imprimirComprobante = imprimir;
	}
	
	/**
	 * Constructor para RegistrarAbono
	 *
	 * @param abonoA
	 * @param mtoMin
	 * @param imprimir
	 */
	public RegistrarAbono(Vector<Pago> abonoA, double mtoMin, boolean imprimir) {
		super(MensajesVentanas.ventanaActiva);
		initialize();
		
		jTextField.setText(df.format(mtoMin));
		jTextField.selectAll();
		
		jTextField.addKeyListener(this);
		
		jButton.addKeyListener(this);
		jButton.addMouseListener(this);
		
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);
		
		this.abonoAnterior = abonoA;
		this.registroInicial = true;
		this.montoMinimo = mtoMin;
		this.imprimirComprobante = imprimir;
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

		this.setTitle("Agregar Abono");
		this.setSize(300, 190);
		this.setContentPane(getJContentPane());
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
			jContentPane.setLayout(new FlowLayout());
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
		jTextField.removeKeyListener(this);
		jButton.removeKeyListener(this);
		jButton.removeMouseListener(this);
		jButton1.removeKeyListener(this);
		jButton1.removeMouseListener(this);

		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}

		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			CR.meServ.getApartado().cancelarAbonoActivo();
			/*if (this.reversarEnError) {
				CR.meServ.reversarCalculoApartado();
			}*/
			this.canceladaEjecucion = true;
			dispose();
		}
		
		else if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(e.getSource().equals(this.getJTextField())) {
				jButton1.requestFocus();
			}
			
			else if(e.getSource().equals(this.getJButton1())) {
				try {
					if (!this.registroInicial) {
						CR.meServ.abonar(new Double(CR.me.formatoNumerico(getJTextField().getText())).doubleValue(), this.isImprimirComprobante());
						CR.meServ.registrarAbonosApartado();
					} else {
						if (new Double(CR.me.formatoNumerico(getJTextField().getText())).doubleValue() >= (this.montoMinimo-0.01)) {
							if (this.abonoAnterior == null) {
								CR.meServ.abonar(new Double(CR.me.formatoNumerico(getJTextField().getText())).doubleValue(), this.isImprimirComprobante());
							} else {
								CR.meServ.abonar(new Double(CR.me.formatoNumerico(getJTextField().getText())).doubleValue(), abonoAnterior,this.isImprimirComprobante() );
							}
						} else
							throw new ExcepcionCr("El monto debe ser mayor o igual a " + df.format(this.montoMinimo));
					}
					dispose();
				} catch (NumberFormatException e1) {
					logger.error("keyPressed(KeyEvent)", e1);

					MensajesVentanas.mensajeError("Error en formato del Monto. Debe ser un número decimal.");
					jTextField.requestFocus();
					jTextField.selectAll();
				} catch (ExcepcionCr e1) {
					logger.error("keyPressed(KeyEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
					jTextField.requestFocus();
					jTextField.selectAll();
				} catch (ConexionExcepcion e1) {
					logger.error("keyPressed(KeyEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
					jTextField.requestFocus();
					jTextField.selectAll();
				} catch (Throwable e1) {
					logger.fatal("Finalizar Apartado", e1);
					e1.printStackTrace();
				}

			}
						
			else if(e.getSource().equals(this.getJButton())) {
				CR.meServ.getApartado().cancelarAbonoActivo();
				/*if (this.reversarEnError) {
					CR.meServ.reversarCalculoApartado();
				}*/
				this.canceladaEjecucion = true;
				dispose();
			}
		}		

		jTextField.addKeyListener(this);
		jButton.addKeyListener(this);
		jButton.addMouseListener(this);
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);

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
		jTextField.removeKeyListener(this);
		jButton.removeKeyListener(this);
		jButton.removeMouseListener(this);
		jButton1.removeKeyListener(this);
		jButton1.removeMouseListener(this);

		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - start");
		}

		if(e.getSource().equals(this.getJButton())) {
			CR.meServ.getApartado().cancelarAbonoActivo();
			/*if (this.reversarEnError) {
				CR.meServ.reversarCalculoApartado();
			}*/
			this.canceladaEjecucion = true;
			dispose();
		}
			
		else if(e.getSource().equals(this.getJButton1())) {
			try {
				if (!this.registroInicial) {
					CR.meServ.abonar(new Double(CR.me.formatoNumerico(getJTextField().getText())).doubleValue(), this.isImprimirComprobante());
					CR.meServ.registrarAbonosApartado();
				} else {
					double montoAb = new Double(CR.me.formatoNumerico(getJTextField().getText())).doubleValue();
					double montoAp = CR.meServ.getApartado().consultarMontoServ();  
					if (montoAb < montoAp){
						if (new Double(CR.me.formatoNumerico(getJTextField().getText())).doubleValue() >= (this.montoMinimo-0.01)){
							if (this.abonoAnterior == null) {
								CR.meServ.abonar(new Double(CR.me.formatoNumerico(getJTextField().getText())).doubleValue(), this.isImprimirComprobante());
							} else {
								CR.meServ.abonar(new Double(CR.me.formatoNumerico(getJTextField().getText())).doubleValue(), abonoAnterior, this.isImprimirComprobante());
							}
						} else
							throw new ExcepcionCr("El monto debe ser mayor o igual a " + df.format(this.montoMinimo));
					} else
						throw new ExcepcionCr("El monto del abono debe ser menor a " + df.format(CR.meServ.getApartado().consultarMontoServ()));
				}
				dispose();
			} catch (NumberFormatException e1) {
				logger.error("mouseClicked(MouseEvent)", e1);

				MensajesVentanas.mensajeError("Error en formato del Monto. Debe ser un número decimal.");
				jTextField.requestFocus();
				jTextField.selectAll();
			} catch (ExcepcionCr e1) {
				logger.error("mouseClicked(MouseEvent)", e1);

				MensajesVentanas.mensajeError(e1.getMensaje());
				jTextField.requestFocus();
				jTextField.selectAll();
			} catch (ConexionExcepcion e1) {
				logger.error("mouseClicked(MouseEvent)", e1);

				MensajesVentanas.mensajeError(e1.getMensaje());
				jTextField.requestFocus();
				jTextField.selectAll();
			} 
		}

		jTextField.addKeyListener(this);
		jButton.addKeyListener(this);
		jButton.addMouseListener(this);
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);

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
			jPanel.setPreferredSize(new java.awt.Dimension(275,47));
			jPanel.setBackground(new java.awt.Color(242,242,238));
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Monto "+Sesion.getTienda().getMonedaBase(), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
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
			jTextField.setPreferredSize(new java.awt.Dimension(120,20));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField() - end");
		}
		return jTextField;
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
			jPanel2.setPreferredSize(new java.awt.Dimension(280,30));
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
			jButton.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
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
			jButton1.setText("Abonar");
			jButton1.setBackground(new java.awt.Color(226,226,222));
			jButton1.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/add2.png")));
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
			jPanel3.setPreferredSize(new java.awt.Dimension(280,100));
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
			layFlowLayout2.setVgap(5);
			layFlowLayout2.setHgap(5);
			jPanel4.setLayout(layFlowLayout2);
			jPanel4.add(getJLabel1(), null);
			jPanel4.setPreferredSize(new java.awt.Dimension(280,40));
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
			jLabel1.setText("Agregar Abono");
			jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel1.setForeground(java.awt.Color.white);
			jLabel1.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/money.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel1() - end");
		}
		return jLabel1;
	}
	/**
	 * Método isCanceladaEjecucion
	 * 
	 * @return
	 * boolean
	 */
	public boolean isCanceladaEjecucion() {
		if (logger.isDebugEnabled()) {
			logger.debug("isCanceladaEjecucion() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isCanceladaEjecucion() - end");
		}
		return canceladaEjecucion;
}
	/**
	 * Método isImprimirComprobante
	 * 
	 * @return
	 * boolean
	 */
	public boolean isImprimirComprobante() {
		if (logger.isDebugEnabled()) {
			logger.debug("isImprimirComprobante() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isImprimirComprobante() - end");
		}
		return imprimirComprobante;
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
