/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : BonoRegalo.java
 * Creado por : JGraterol 
 * Creado en  : Mar 4, 2004 - 10:52:57 AM
 *
 * (c) CENTROBECO, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 
 * Fecha       : 
 * Analista    : 
 * Descripción :
 * =============================================================================
 */
package com.becoblohm.cr.gui.bonoregalo;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import org.apache.log4j.Logger;
import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.BonoRegaloException;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.utiles.MensajesVentanas;
/**
 * Descripción:
 * 
 */
public class UtilitariosBonoRegalo
	extends JDialog
	implements ComponentListener, KeyListener, ActionListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BonoRegalo.class);

	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JPanel jPanel5 = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JPanel jPanel7 = null;
	private int opcion;
		
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JButton jButtonRecarga = null;
	private javax.swing.JButton jButtonReimpresion = null;
	private javax.swing.JButton jButtonAnulacion = null;
	private javax.swing.JButton jButton9 = null;
	FocusListener botonClicker;
	private javax.swing.JPanel jPanel2 = null;
	private boolean cancelar = false;
	
	/**
	 * This is the default constructor
	 */
	public UtilitariosBonoRegalo() {
		super(MensajesVentanas.ventanaActiva);
		initialize();
		opcion = 1;
		
		jButton1.addKeyListener(this);
		jButton1.addActionListener(this);
		
		jButtonRecarga.addKeyListener(this);
		jButtonRecarga.addActionListener(this);
		
		jButtonReimpresion.addKeyListener(this);
		jButtonReimpresion.addActionListener(this);

		jButtonAnulacion.addKeyListener(this);
		jButtonAnulacion.addActionListener(this);
		
		jButton9.addKeyListener(this);
		jButton9.addActionListener(this);
		
		botonClicker = new java.awt.event.FocusAdapter() { 
			public void focusGained(java.awt.event.FocusEvent e) {
				if (logger.isDebugEnabled())
					logger.debug("focusGained(java.awt.event.FocusEvent) - start");

				if (logger.isDebugEnabled())
					logger.debug("focusGained(java.awt.event.FocusEvent) - end");
			}
		};
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

		this.setSize(455, 388);
		this.setTitle("Bono Regalo Electrónico");
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
			java.awt.FlowLayout layFlowLayout7 = new java.awt.FlowLayout();
			layFlowLayout7.setHgap(5);
			layFlowLayout7.setVgap(5);
			jContentPane.setLayout(layFlowLayout7);
			jContentPane.add(getJPanel7(), null);
			jContentPane.add(getJPanel2(), null);
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.RAISED));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
			jContentPane.setPreferredSize(new java.awt.Dimension(442,100));
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
			java.awt.FlowLayout layFlowLayout3 = new java.awt.FlowLayout();
			layFlowLayout3.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout3.setHgap(0);
			layFlowLayout3.setVgap(0);
			jPanel.setLayout(layFlowLayout3);
			jPanel.add(getJPanel5(), null);
			jPanel.setPreferredSize(new java.awt.Dimension(410,70));
			jPanel.setBackground(new java.awt.Color(69,107,127));
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
			java.awt.FlowLayout flowLayout5 = new java.awt.FlowLayout();
			flowLayout5.setHgap(0);
			flowLayout5.setVgap(0);
			jPanel1.setLayout(flowLayout5);
			jPanel1.add(getJPanel4(), null);
			jPanel1.setPreferredSize(new java.awt.Dimension(415,205));
			jPanel1.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel1() - end");
		}
		return jPanel1;
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
			javax.swing.border.TitledBorder ivjTitledBorder = javax.swing.BorderFactory.createTitledBorder(null , "Opciones: " , javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION , javax.swing.border.TitledBorder.DEFAULT_POSITION , null , null);
			java.awt.GridLayout layGridLayout1 = new java.awt.GridLayout();
			layGridLayout1.setRows(4);
			layGridLayout1.setColumns(1);
			ivjTitledBorder.setTitle("Utilidades disponibles");
			jPanel4.setLayout(layGridLayout1);
			jPanel4.add(getJButton1(), null);
			jPanel4.add(getJButtonRecarga(), null);
			jPanel4.add(getJButtonReimpresion(), null);
			jPanel4.add(getJButtonAnulacion(), null);
			jPanel4.setBorder(ivjTitledBorder);
			jPanel4.setPreferredSize(new java.awt.Dimension(360,190));
			jPanel4.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel4() - end");
		}
		return jPanel4;
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
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
			layFlowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel5.setLayout(layFlowLayout2);
			jPanel5.add(getJLabel(), null);
			jPanel5.setPreferredSize(new java.awt.Dimension(420,50));
			jPanel5.setBackground(new java.awt.Color(69,107,127));
			layFlowLayout2.setVgap(10);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel5() - end");
		}
		return jPanel5;
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
			jLabel.setText("Utilitarios de Bono Regalo");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel.setForeground(java.awt.Color.white);
			jLabel.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/cube_molecule.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel() - end");
		}
		return jLabel;
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
			java.awt.FlowLayout layFlowLayout31 = new java.awt.FlowLayout();
			layFlowLayout31.setHgap(0);
			layFlowLayout31.setVgap(0);
			jPanel7.setLayout(layFlowLayout31);
			jPanel7.add(getJPanel(), null);
			jPanel7.add(getJPanel1(), null);
			jPanel7.setPreferredSize(new java.awt.Dimension(420,290));
			jPanel7.setBackground(new java.awt.Color(242,242,238));
			jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel7() - end");
		}
		return jPanel7;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}

		//Mapeo de ESC
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			//Mapeo de ESC
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
				this.getJButton9().doClick();
			}
		}
		
		//Mapeo de ENTER
		else if((e.getKeyCode() == KeyEvent.VK_ENTER)){
			
		}
		
		//Mapeo de F1
		else if(e.getKeyCode() == KeyEvent.VK_F1){
			jButton1.doClick();
		}
		
		//Mapeo de F2
		else if(e.getKeyCode() == KeyEvent.VK_F2){
			jButtonRecarga.doClick();
		}

		//Mapeo de F3
		else if(e.getKeyCode() == KeyEvent.VK_F3){
			jButtonReimpresion.doClick();
		}
		
		//Mapeo de F3
		else if(e.getKeyCode() == KeyEvent.VK_F4){
			jButtonAnulacion.doClick();
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
	 * Método ejecutar
	 * 
	 */
	private void ejecutarAccion() {
		if (logger.isDebugEnabled()) {
			logger.debug("ejecutarAccion() - start");
		}

		switch (this.opcion) {
		case 1:
			//Imprimir comprobante
			dispose();
			try {
				//CR.meServ.desbloquearTransaccionBR();
				CR.meServ.imprimirComprobanteBR();
			} catch (BaseDeDatosExcepcion e2) {
				MensajesVentanas.mensajeError("No se pudo actualizar el estado de la transacción\n Intente reimprimir el comprobante o notifique a personal de Soporte");
			} /*catch (MaquinaDeEstadoExcepcion e) {
				MensajesVentanas.mensajeError("No se pudo actualizar el estado de la transacción\n Intente reimprimir el comprobante o notifique a personal de Soporte");
			} catch (XmlExcepcion e) {
				MensajesVentanas.mensajeError("No se pudo actualizar el estado de la transacción\n Intente reimprimir el comprobante o notifique a personal de Soporte");
			} catch (FuncionExcepcion e) {
				MensajesVentanas.mensajeError("No se pudo actualizar el estado de la transacción\n Intente reimprimir el comprobante o notifique a personal de Soporte");
			} catch (ConexionExcepcion e) {
				MensajesVentanas.mensajeError("No se pudo actualizar el estado de la transacción\n Intente reimprimir el comprobante o notifique a personal de Soporte");
			}*/
			break;
		case 2:
			try {
				//CR.meServ.desbloquearTransaccionBR();
				CR.meServ.imprimirFacturaBR();
			} catch (BonoRegaloException e1) {
				MensajesVentanas.mensajeError(e1.getMessage());
			} /*catch (MaquinaDeEstadoExcepcion e) {
				MensajesVentanas.mensajeError(e.getMessage());
			} catch (XmlExcepcion e) {
				MensajesVentanas.mensajeError(e.getMessage());
			} catch (FuncionExcepcion e) {
				MensajesVentanas.mensajeError(e.getMessage());
			} catch (BaseDeDatosExcepcion e) {
				MensajesVentanas.mensajeError(e.getMessage());
			} catch (ConexionExcepcion e) {
				MensajesVentanas.mensajeError(e.getMessage());
			}*/
			dispose();
			break;
		case 3:
			//Reimprimir factura
			try {
				dispose();
				//CR.meServ.desbloquearTransaccionBR();
				CR.meServ.reimprimirFacturaBR(null);
			} catch (BonoRegaloException e) {
				MensajesVentanas.mensajeError(e.getMessage());
			}/*catch (MaquinaDeEstadoExcepcion e) {
				MensajesVentanas.mensajeError(e.getMessage());
			} catch (XmlExcepcion e) {
				MensajesVentanas.mensajeError(e.getMessage());
			} catch (FuncionExcepcion e) {
				MensajesVentanas.mensajeError(e.getMessage());
			} catch (BaseDeDatosExcepcion e) {
				MensajesVentanas.mensajeError(e.getMessage());
			} catch (ConexionExcepcion e) {
				MensajesVentanas.mensajeError(e.getMessage());
			}*/
			break;
		case 4:
			try{
				dispose();
				try {
					CR.meServ.anularVentaBR();
				} catch (UsuarioExcepcion e) {
					throw new BonoRegaloException(e.getMensaje());
				} catch (MaquinaDeEstadoExcepcion e) {
					throw new BonoRegaloException(e.getMensaje());
				} catch (ConexionExcepcion e) {
					throw new BonoRegaloException(e.getMensaje());
				} catch (ExcepcionCr e) {
					throw new BonoRegaloException(e.getMensaje());
				}
			} catch(BonoRegaloException e){
				MensajesVentanas.mensajeError(e.getMessage());
			}
			break;
		case 9:
			this.cancelar=true;
			dispose();
			break;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("ejecutarAccion() - end");
		}
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
			jButton1.setText("F1 - Reimprimir comprobante de venta");
			jButton1.setActionCommand("ReimprimirComprobante");
			jButton1.setBackground(new java.awt.Color(226,226,222));
			jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton1.addFocusListener(botonClicker);
			jButton1.setEnabled(CR.meServ.getVentaBR().getEstadoTransaccion()!=Sesion.BONO_REGALO_EN_PROCESO);
			jButton1.setEnabled(false);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton1() - end");
		}
		return jButton1;
	}
	
	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButtonRecarga() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButtonRecarga() - start");
		}

		if(jButtonRecarga == null) {
			jButtonRecarga = new JHighlightButton();
			jButtonRecarga.setText("F2 - Imprimir factura");
			jButtonRecarga.setActionCommand("ImprimirFactura");
			jButtonRecarga.setBackground(new java.awt.Color(226,226,222));
			jButtonRecarga.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButtonRecarga.addFocusListener(botonClicker);
			//jButtonRecarga.setEnabled(CR.meServ.getVentaBR().getEstadoTransaccion()!=Sesion.BONO_REGALO_EN_PROCESO);
			jButtonRecarga.setEnabled(false);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButtonRecarga() - end");
		}
		return jButtonRecarga;
	}
	
	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButtonReimpresion() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButtonReimpresion() - start");
		}

		if(jButtonReimpresion == null) {
			jButtonReimpresion = new JHighlightButton();
			jButtonReimpresion.setText("F3 - Reimprimir factura");
			jButtonReimpresion.setActionCommand("RemprimirFactura");
			jButtonReimpresion.setBackground(new java.awt.Color(226,226,222));
			jButtonReimpresion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButtonReimpresion.addFocusListener(botonClicker);
			jButtonReimpresion.setEnabled(CR.meServ.getVentaBR().getEstadoTransaccion()!=Sesion.BONO_REGALO_EN_PROCESO);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButtonReimpresion() - end");
		}
		return jButtonReimpresion;
	}
	
	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButtonAnulacion() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButtonAnulacion() - start");
		}

		if(jButtonAnulacion == null) {
			jButtonAnulacion = new JHighlightButton();
			jButtonAnulacion.setText("F4 - Anular venta");
			jButtonAnulacion.setActionCommand("AnularVenta");
			jButtonAnulacion.setBackground(new java.awt.Color(226,226,222));
			jButtonAnulacion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButtonAnulacion.addFocusListener(botonClicker);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButtonAnulacion() - end");
		}
		return jButtonAnulacion;
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
	
	
	public void actionPerformed(ActionEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("actionPerformed(ActionEvent e) - start");
		}
		eliminarListeners();
		
		if(e.getSource().equals(jButton1)) {
			jButton1.requestFocus();
			this.opcion = 1;
			ejecutarAccion();
		} else if(e.getSource().equals(jButtonRecarga)) {
			jButtonRecarga.requestFocus();
			this.opcion = 2;
			ejecutarAccion();
		} else if(e.getSource().equals(jButtonReimpresion)) {
			jButtonReimpresion.requestFocus();
			this.opcion = 3;
			ejecutarAccion();
		} else if(e.getSource().equals(jButtonAnulacion)) {
			jButtonAnulacion.requestFocus();
			this.opcion = 4;
			ejecutarAccion();
		} else if(e.getSource().equals(jButton9)) {
			jButton9.requestFocus();
			this.opcion = 9;
			ejecutarAccion();
		} 
		agregarListeners();
		if (logger.isDebugEnabled()) {
			logger.debug("actionPerformed(ActionEvent e) - end");
		}
	}
	
	private void agregarListeners() {
		if (logger.isDebugEnabled())
			logger.debug("agregarListeners() - start");

		jButton9.addKeyListener(this);
		jButton9.addActionListener(this);
		
		jButton1.addKeyListener(this);
		jButton1.addActionListener(this);

		jButtonRecarga.addKeyListener(this);
		jButtonRecarga.addActionListener(this);
		
		jButtonReimpresion.addKeyListener(this);
		jButtonReimpresion.addActionListener(this);
		
		jButtonAnulacion.addKeyListener(this);
		jButtonAnulacion.addActionListener(this);
		
		if (logger.isDebugEnabled())
			logger.debug("agregarListeners() - end");
	}
	
	private void eliminarListeners() {
		if (logger.isDebugEnabled())
			logger.debug("eliminarListeners() - start");

		jButton9.removeKeyListener(this);
		jButton9.removeActionListener(this);
		
		jButton1.removeKeyListener(this);
		jButton1.removeActionListener(this);						
		
		jButtonRecarga.removeKeyListener(this);
		jButtonRecarga.removeActionListener(this);
		
		jButtonReimpresion.removeKeyListener(this);
		jButtonReimpresion.removeActionListener(this);			
		
		jButtonAnulacion.removeKeyListener(this);
		jButtonAnulacion.removeActionListener(this);	
		
		if (logger.isDebugEnabled())
			logger.debug("eliminarListeners() - end");
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
			jButton9.setText("Cancelar");
			jButton9.setBackground(new java.awt.Color(226,226,222));
			jButton9.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton9() - end");
		}
		return jButton9;
	}
	
	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel2() {
		if(jPanel2 == null) {
			jPanel2 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setHgap(0);
			layFlowLayout1.setVgap(0);
			layFlowLayout1.setAlignment(java.awt.FlowLayout.RIGHT);
			jPanel2.setLayout(layFlowLayout1);
			jPanel2.setPreferredSize(new java.awt.Dimension(420,35));
			jPanel2.setBackground(new java.awt.Color(226,226,222));
			jPanel2.add(getJButton9(), null);
		}
		return jPanel2;
	}

	public boolean isCancelar() {
		return cancelar;
	}

	public void setCancelar(boolean cancelar) {
		this.cancelar = cancelar;
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"  @jve:decl-index=0:visual-constraint="10,10"
