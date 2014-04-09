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

import java.awt.Component;
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
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.BonoRegaloException;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.gui.PoliticasDeFoco;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.utiles.FacturaBarCode;
import com.becoblohm.cr.utiles.MensajesVentanas;
import java.awt.Color;
/**
 * Descripción:
 * 
 */
public class BonoRegalo
	extends JDialog
	implements ComponentListener, KeyListener, ActionListener, DocumentListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BonoRegalo.class);

	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JPanel jPanel5 = null;
	private javax.swing.JPanel jPanel6 = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JLabel jLabel5 = null;
	private javax.swing.JPanel jPanel7 = null;
	private javax.swing.JPanel jPanel2 = null;
	private int opcion;
		
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JButton jButtonRecarga = null;
	private javax.swing.JButton jButton2 = null;
	private javax.swing.JButton jButton3 = null;
	private javax.swing.JButton jButton4 = null;
	private javax.swing.JButton jButton9 = null;
	private javax.swing.JPanel jPanel3 = null;
	private FocusListener botonClicker;
	private javax.swing.JPasswordField jPasswordField = null;

	private JHighlightButton jButtonAceptar = null;
	

	/**
	 * This is the default constructor
	 */
	public BonoRegalo() {
		super(MensajesVentanas.ventanaActiva);
		initialize();
		opcion = 0;
		
		agregarListeners();
		
		// Agregar listener para manejo de scanner
		CR.inputEscaner.getDocument().addDocumentListener(this);
		
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

		this.setSize(555, 378);
		this.setTitle("Bono Regalo Electrónico");
		this.setContentPane(getJContentPane());
		this.setUndecorated(true);
		this.setModal(true);
		this.setBackground(new java.awt.Color(226,226,222));
		this.addComponentListener(this);
		
		Component[] compList = {jPasswordField,jButtonAceptar,jButton9};
		this.setFocusTraversalPolicy(new PoliticasDeFoco(compList));
		
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
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.RAISED));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
			jContentPane.setPreferredSize(new java.awt.Dimension(442,100));
			jContentPane.add(getJPanel2(), null);
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
			jPanel.add(getJPanel6(), null);
			jPanel.setPreferredSize(new java.awt.Dimension(510,70));
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
			jPanel1.add(getJPanel3(),null);
			jPanel1.add(getJPanel4(), null);
			jPanel1.setPreferredSize(new java.awt.Dimension(515,215));
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
			layGridLayout1.setRows(5);
			layGridLayout1.setColumns(1);
			ivjTitledBorder.setTitle("Utilidades disponibles");
			jPanel4.setLayout(layGridLayout1);
			jPanel4.add(getJButton1(), null);
			jPanel4.add(getJButtonRecarga(), null);
			jPanel4.add(getJButton2(), null);
			jPanel4.add(getJButton3(), null);
			jPanel4.add(getJButton4(), null);
			jPanel4.setBorder(ivjTitledBorder);
			jPanel4.setPreferredSize(new java.awt.Dimension(360,210));
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
	 * This method initializes jPanel6
	 *
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel6() {
		if (logger.isDebugEnabled())
			logger.debug("getJPanel6() - start");

		if(jPanel6 == null) {
			jPanel6 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout11 = new java.awt.FlowLayout();
			layFlowLayout11.setAlignment(java.awt.FlowLayout.RIGHT);
			layFlowLayout11.setHgap(5);
			layFlowLayout11.setVgap(1);
			jPanel6.setLayout(layFlowLayout11);
			jPanel6.add(getJLabel5(), null);
			jPanel6.setPreferredSize(new java.awt.Dimension(505,20));
			jPanel6.setBackground(new java.awt.Color(69,107,127));
			
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel6() - end");
		return jPanel6;
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
			jLabel.setText("Bono Regalo");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel.setForeground(java.awt.Color.white);
			jLabel.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/chest.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel() - end");
		}
		return jLabel;
	}
	/**
	 * This method initializes jLabel5
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel5() {
		if (logger.isDebugEnabled())
			logger.debug("getJLabel5() - start");

		if(jLabel5 == null) {
			jLabel5 = new javax.swing.JLabel();
			jLabel5.setText("Seleccione una opción");
			jLabel5.setForeground(java.awt.Color.white);
		}

		if (logger.isDebugEnabled())
			logger.debug("getJLabel5() - end");
		return jLabel5;
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
			jPanel7.setPreferredSize(new java.awt.Dimension(520,290));
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
			this.getJButton9().doClick();
		}
		
		//Mapeo de ENTER
		else if((e.getKeyCode() == KeyEvent.VK_ENTER)){
			if (e.getSource() instanceof JButton){
				((JButton)e.getSource()).doClick();
				if (!e.getSource().equals(getJButtonAceptar()) && !e.getSource().equals(jButton9)) {
					jButtonAceptar.requestFocus();
				}
			} else if (e.getSource().equals(getJPasswordField()))
				jButtonAceptar.requestFocus();
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
			jButton2.doClick();
		}

		//Mapeo de F4
		else if(e.getKeyCode() == KeyEvent.VK_F4){
			jButton3.doClick();
		}
		
		//Mapeo de F5
		else if(e.getKeyCode() == KeyEvent.VK_F5){
			jButton4.doClick();
		}

		else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			if(e.getSource().getClass().isInstance(jButtonAceptar))
				((JButton)e.getSource()).transferFocus();
			else 	
				((JPasswordField)e.getSource()).transferFocus();
		}

		else if(e.getKeyCode() == KeyEvent.VK_UP) {
			if(e.getSource().getClass().isInstance(jButtonAceptar))
				((JButton)e.getSource()).transferFocusBackward();
			else 	
				((JPasswordField)e.getSource()).transferFocusBackward();
		}
		
		else if(e.getKeyCode() == KeyEvent.VK_TAB) {
			if(e.getSource().getClass().isInstance(jButtonAceptar))
				((JButton)e.getSource()).transferFocus();
			else 	
				((JPasswordField)e.getSource()).transferFocus();
		} 
		
		else if (Character.isDigit(e.getKeyChar()) 
		   && !e.getSource().equals(jPasswordField) && getJPasswordField().isEditable()) {
			jPasswordField.setText(String.valueOf(jPasswordField.getPassword()) + e.getKeyChar());
			if((String.valueOf(jPasswordField.getPassword()).trim()).equals("") || jPasswordField.getPassword() == null)
				jPasswordField.setEchoChar('\000');
			jPasswordField.requestFocus();
		}
		
		else if(e.getSource().equals(getJPasswordField()))
			if((String.valueOf(jPasswordField.getPassword()).trim()).equals("") || jPasswordField.getPassword() == null)
				jPasswordField.setEchoChar('\000');
		
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

		if(e.getSource().equals(getJPasswordField()) && jPasswordField.getPassword().length <= 1)
				jPasswordField.setEchoChar('\000');
		
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

		// Eliminar listener para manejo de scanner
		CR.inputEscaner.getDocument().removeDocumentListener(this);

		switch (this.opcion) {
		case 1:
			try {
				dispose();
				CR.meServ.crearVentaBR(Sesion.TIPO_TRANSACCION_BR_VENTA, true);
				VentaBonoRegalo ventanaBR = new VentaBonoRegalo();
				MensajesVentanas.centrarVentanaDialogo(ventanaBR);
			} catch (ConexionExcepcion e) {
				MensajesVentanas.mensajeError(e.getMensaje());
			} catch (ExcepcionCr e) {
				MensajesVentanas.mensajeError(e.getMensaje());
			}
			break;
		case 2:
			try {
				dispose();
				CR.meServ.crearVentaBR(Sesion.TIPO_TRANSACCION_BR_RECARGA, true);
				VentaBonoRegalo ventanaBR = new VentaBonoRegalo();
				MensajesVentanas.centrarVentanaDialogo(ventanaBR);
			} catch (ConexionExcepcion e) {
				MensajesVentanas.mensajeError(e.getMensaje());
			} catch (ExcepcionCr e) {
				MensajesVentanas.mensajeError(e.getMensaje());
			}
			break;
		case 3:
			try {
				dispose();
				CR.meServ.consultarSaldoBR();
			} catch (BonoRegaloException e) {
				MensajesVentanas.mensajeError(e.getMensaje());
			} catch (UsuarioExcepcion e) {
				MensajesVentanas.mensajeError(e.getMensaje());
			} catch (MaquinaDeEstadoExcepcion e) {
				MensajesVentanas.mensajeError(e.getMensaje());
			} catch (ConexionExcepcion e) {
				MensajesVentanas.mensajeError(e.getMensaje());
			} catch (ExcepcionCr e) {
				MensajesVentanas.mensajeError(e.getMensaje());
			}
			break;
		case 4:
			if (getJPasswordField().getPassword().length > 0) {
				String lectura;
				lectura = String.valueOf(jPasswordField.getPassword()).trim();
				this.opcion =0;
				try {
					this.dispose();
					FacturaBarCode fbc = new FacturaBarCode(lectura);
					int tda = fbc.getNumTienda();
					String fecha = fbc.getFechaSQL();
					int caja = fbc.getNumCaja();
					int transaccion = fbc.getNumTransaccion();
					CR.meServ.recuperarVentaBR(tda,fecha,caja,transaccion);
					VentaBonoRegalo ventanaVentaBR = new VentaBonoRegalo();
					MensajesVentanas.centrarVentanaDialogo(ventanaVentaBR);
				} catch(BonoRegaloException br){
					MensajesVentanas.mensajeError(br.getMessage());
				} catch (Exception e) {
					logger.error("ejecutarAccion(KeyEvent)", e);
					MensajesVentanas.mensajeError("Identificador de transacción inválido.\nNo se pudo recuperar la transacción");
				}
			} else {
				MensajesVentanas.aviso("Identificador de transacción inválido.\nDebe especificar un identificador");
				getJPasswordField().requestFocus();
			}
			break;		
		case 5:
			dispose();
			DatosReimpresionConsolidado drc = new DatosReimpresionConsolidado();
			MensajesVentanas.centrarVentanaDialogo(drc);
			break;
		case 9:
			dispose();
			break;
		default:
			MensajesVentanas.aviso("Debe seleccionar una opción");
			getJPasswordField().requestFocus();
			
			// Agregar listener para manejo de scanner
			CR.inputEscaner.getDocument().addDocumentListener(this);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("ejecutarAccion() - end");
		}
	}
	
	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton3() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton2() - start");
		}

		if(jButton3 == null) {
			jButton3 = new JHighlightButton();
			jButton3.setText("F4 - Recuperar venta de Bono Regalo");
			jButton3.setActionCommand("Recuperar venta de Bono Regalo");
			jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton3.setBackground(new java.awt.Color(226,226,222));
			jButton3.addFocusListener(botonClicker);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton2() - end");
		}
		return jButton3;
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
			jButton2.setText("F3 - Consultar saldo");
			jButton2.setActionCommand("Consulta de saldo");
			jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton2.setBackground(new java.awt.Color(226,226,222));
			jButton2.addFocusListener(botonClicker);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton2() - end");
		}
		return jButton2;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton4() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton4() - start");
		}

		if(jButton4 == null) {
			jButton4 = new JHighlightButton();
			jButton4.setText("F5 - Reimprimir Consolidado");
			jButton4.setActionCommand("Consulta de saldo");
			jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton4.setBackground(new java.awt.Color(226,226,222));
			jButton4.addFocusListener(botonClicker);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton4() - end");
		}
		return jButton4;
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
			jButton1.setText("F1 - Compra de tarjetas");
			jButton1.setActionCommand("Recargas");
			jButton1.setBackground(new java.awt.Color(226,226,222));
			jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton1.addFocusListener(botonClicker);
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
			jButtonRecarga.setText("F2 - Recargar tarjetas");
			jButtonRecarga.setActionCommand("Recargas");
			jButtonRecarga.setBackground(new java.awt.Color(226,226,222));
			jButtonRecarga.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButtonRecarga.addFocusListener(botonClicker);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButtonRecarga() - end");
		}
		return jButtonRecarga;
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
			jPanel2.setPreferredSize(new java.awt.Dimension(520,35));
			jPanel2.setBackground(new java.awt.Color(226,226,222));
			jPanel2.add(getJButtonAceptar(), null);
			jPanel2.add(getJButton9(), null);
		}
		return jPanel2;
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

	public void actionPerformed(ActionEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("actionPerformed(ActionEvent e) - start");
		}
		eliminarListeners();
		
		if(e.getSource().equals(jButtonAceptar)){
			ejecutarAccion();
		}
		if(e.getSource().equals(jButton1)) {
			jButton1.requestFocus();
			this.opcion = 1;
			getJLabel5().setText(jButton1.getText().substring(5));
			getJPasswordField().setEnabled(false);
			getJPasswordField().setEditable(false);
		} else if(e.getSource().equals(jButtonRecarga)) {
			jButtonRecarga.requestFocus();
			this.opcion = 2;
			getJLabel5().setText(jButtonRecarga.getText().substring(5));
			getJPasswordField().setEnabled(false);
			getJPasswordField().setEditable(false);
		} else if(e.getSource().equals(jButton2)) {
			jButton2.requestFocus();
			this.opcion = 3;
			getJLabel5().setText(jButton2.getText().substring(5));
			getJPasswordField().setEnabled(false);
			getJPasswordField().setEditable(false);
		} else if(e.getSource().equals(jButton3)){
			jButton3.requestFocus();
			this.opcion = 4;
			getJLabel5().setText(jButton3.getText().substring(5));
			getJPasswordField().setEnabled(true);
			getJPasswordField().setEditable(true);
		} else if(e.getSource().equals(jButton4)) {
			jButton4.requestFocus();
			this.opcion = 5;
			getJLabel5().setText(jButton4.getText().substring(5));
			getJPasswordField().setEnabled(false);
			getJPasswordField().setEditable(false);
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
	
	/**
	 * This method initializes jPanel3
	 *
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel3() {
		if (logger.isDebugEnabled())
			logger.debug("getJPanel3() - start");

		if(jPanel3 == null) {
			jPanel3 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout4 = new java.awt.FlowLayout();
			layFlowLayout4.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout4.setHgap(1);
			layFlowLayout4.setVgap(1);
			jPanel3.setLayout(layFlowLayout4);
			jPanel3.add(getJPasswordField(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(140,55));
			jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Identificador", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
			jPanel3.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel3() - end");
		return jPanel3;
	}
	
	/**
	 * This method initializes jTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JPasswordField getJPasswordField() {
		if (logger.isDebugEnabled())
			logger.debug("getJPasswordField() - start");

		if(jPasswordField == null) {
			jPasswordField = new JPasswordField();
			jPasswordField.setPreferredSize(new java.awt.Dimension(120,20));	
			jPasswordField.setEchoChar('\000');
			jPasswordField.requestFocus();
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPasswordField() - end");
		return jPasswordField;
	}

	/**
	 * This method initializes jButtonAceptar	
	 * 	
	 * @return com.becoblohm.cr.gui.JHighlightButton	
	 */
	private JHighlightButton getJButtonAceptar() {
		if (jButtonAceptar == null) {
			jButtonAceptar = new JHighlightButton();
			jButtonAceptar.setBackground(new Color(226, 226, 222));
			jButtonAceptar.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
			jButtonAceptar.setText("Aceptar");
			jButtonAceptar.setPreferredSize(new Dimension(105, 26));
		}
		return jButtonAceptar;
	}
	
	public void insertUpdate(DocumentEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("insertUpdate(DocumentEvent) - start");

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (logger.isDebugEnabled())
					logger.debug("run() - start");

				jPasswordField.setEchoChar('*');
				jPasswordField.setText(CR.inputEscaner.getText());
				jPasswordField.requestFocus();
				jPasswordField.selectAll();

				if (logger.isDebugEnabled())
					logger.debug("run() - end");
			}
		});
		
		if (logger.isDebugEnabled())
			logger.debug("insertUpdate(DocumentEvent) - end");
	}


	public void changedUpdate(DocumentEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("changedUpdate(DocumentEvent) - start");
		
		if (logger.isDebugEnabled())
			logger.debug("changedUpdate(DocumentEvent) - end");
	}

	public void removeUpdate(DocumentEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("removeUpdate(DocumentEvent) - start");
		
		if (logger.isDebugEnabled())
			logger.debug("removeUpdate(DocumentEvent) - end");
	}

	private void agregarListeners() {
		if (logger.isDebugEnabled())
			logger.debug("agregarListeners() - start");

		jButtonAceptar.addKeyListener(this);
		jButtonAceptar.addActionListener(this);
		
		jButton9.addKeyListener(this);
		jButton9.addActionListener(this);

		jButton1.addKeyListener(this);
		jButton1.addActionListener(this);

		jButtonRecarga.addKeyListener(this);
		jButtonRecarga.addActionListener(this);
		
		jButton2.addKeyListener(this);
		jButton2.addActionListener(this);
	
		jButton3.addKeyListener(this);
		jButton3.addActionListener(this);

		jButton4.addKeyListener(this);
		jButton4.addActionListener(this);

		jPasswordField.addKeyListener(this);
		jPasswordField.addActionListener(this);

		if (logger.isDebugEnabled())
			logger.debug("agregarListeners() - end");
	}
	
	private void eliminarListeners() {
		if (logger.isDebugEnabled())
			logger.debug("eliminarListeners() - start");

		jButtonAceptar.removeKeyListener(this);
		jButtonAceptar.removeActionListener(this);
		
		jButton9.removeKeyListener(this);
		jButton9.removeActionListener(this);

		jButton1.removeKeyListener(this);
		jButton1.removeActionListener(this);						
		
		jButtonRecarga.removeKeyListener(this);
		jButtonRecarga.removeActionListener(this);
			
		jButton2.removeKeyListener(this);
		jButton2.removeActionListener(this);
		
		jButton3.removeKeyListener(this);
		jButton3.removeActionListener(this);
			
		jButton4.removeKeyListener(this);
		jButton4.removeActionListener(this);

		jPasswordField.removeKeyListener(this);
		jPasswordField.removeActionListener(this);

		if (logger.isDebugEnabled())
			logger.debug("eliminarListeners() - end");
	}
} 