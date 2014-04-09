/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : Utilitarios.java
 * Creado por : Victor Medina <linux@epa.com.ve>
 * Creado en  : Mar 4, 2004 - 10:52:57 AM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1
 * Fecha       : Mar 4, 2004
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.extensiones.ActualizadorPrecios;
import com.becoblohm.cr.extensiones.ActualizadorPreciosFactory;
import com.becoblohm.cr.extensiones.ActualizadorPreciosSACFactory;
import com.becoblohm.cr.extensiones.MenuUtilitariosOtrasFuncionesFactory;
import com.becoblohm.cr.gui.bonoregalo.BonoRegalo;
import com.becoblohm.cr.manejadorauditoria.Auditoria;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstado;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.crprinterdriver.PrinterNotConnectedException;
import javax.swing.border.SoftBevelBorder;
/**
 * Descripción:
 * 
 */
public class Utilitarios
	extends JDialog
	implements ComponentListener, KeyListener, MouseListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Utilitarios.class);

	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JPanel jPanel5 = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JPanel jPanel7 = null;
	private int opcion;
	private boolean emitidoRepZ;
	private boolean sesionCerrada;
		
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JButton jButton2 = null;
	private javax.swing.JButton jButton3 = null;
	private javax.swing.JButton jButton4 = null;
	private javax.swing.JButton jButton5 = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton6 = null;
	private javax.swing.JButton jButton7 = null;
	private javax.swing.JButton jButton8 = null;
	private javax.swing.JButton jButton10 = null;
	private javax.swing.JButton jButton11 = null;
	private javax.swing.JButton jButton12 = null;
	private javax.swing.JButton jButtonBonoRegalo = null;
	private javax.swing.JButton jButtonSinc = null;
	
	private static Reportes r = null;
	private static RegistroClientesTemp clientTemp = null;
	private static OpcionesSeguridad menuSeguridad = null;
	
	//MODIFICADO BECO 12/06/2008
	private ActualizadorPrecios actualizadorPrecios =null;


	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton jButton9 = null;

	/**
	 * This is the default constructor
	 */
	public Utilitarios() {
		super(MensajesVentanas.ventanaActiva);
		initialize();
		opcion = 1;
		
		agregarListeners();

		emitidoRepZ = false;
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

		this.setSize(457, 520);
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
			jContentPane.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
			jContentPane.setPreferredSize(new java.awt.Dimension(442,350));
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
			jPanel.setPreferredSize(new java.awt.Dimension(420,70));
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
			java.awt.FlowLayout layFlowLayout5 = new java.awt.FlowLayout();
			layFlowLayout5.setAlignment(java.awt.FlowLayout.CENTER);
			layFlowLayout5.setHgap(0);
			layFlowLayout5.setVgap(0);
			jPanel1.setLayout(layFlowLayout5);
			jPanel1.add(getJPanel4(), null);
			jPanel1.setPreferredSize(new java.awt.Dimension(415,355));
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
			layGridLayout1.setRows(14);
			layGridLayout1.setColumns(1);
			ivjTitledBorder.setTitle("Utilidades disponibles");
			jPanel4.setLayout(layGridLayout1);
			jPanel4.add(getJButton5(), null);
			jPanel4.add(getJButton6(), null);
			jPanel4.add(getJButton1(), null);
			jPanel4.add(getJButton7(), null);
			jPanel4.add(getJButton(), null);
			jPanel4.add(getJButton3(), null);
			jPanel4.add(getJButton4(), null);
			jPanel4.add(getJButton2(), null);
			jPanel4.add(getJButton8(), null);
			jPanel4.add(getJButton10(), null);
			jPanel4.add(getJButton11(), null);
			jPanel4.add(getJButton12(), null);
			jPanel4.add(getJButtonSinc(), null);
			jPanel4.add(getJButtonBonoRegalo(), null);
			jPanel4.setBorder(ivjTitledBorder);
			jPanel4.setPreferredSize(new java.awt.Dimension(300,350));
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
			jLabel.setText("Utilitarios");
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
			jPanel7.setPreferredSize(new java.awt.Dimension(420,428));
			jPanel7.setBackground(new java.awt.Color(242,242,238));
			jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel7() - end");
		}
		return jPanel7;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - start");
		}

		eliminarListeners();
		
		if(e.getSource().equals(jButton5)){
			this.opcion = 1;
			ejecutarAccion();
		}
		else if(e.getSource().equals(jButton2)){
			this.opcion = 8;
			ejecutarAccion();
		}
		else if(e.getSource().equals(jButton3)){
			this.opcion = 6;
			ejecutarAccion();
		}
		else if(e.getSource().equals(jButton4)){
			this.opcion = 7;
			ejecutarAccion();
		}
		else if(e.getSource().equals(jButton1)){
			this.opcion = 3;
			ejecutarAccion();
		}
		else if(e.getSource().equals(jButton)){
			this.opcion = 5;
			ejecutarAccion();
		}
		else if(e.getSource().equals(jButton6)){
			this.opcion = 2;
			ejecutarAccion();
		}		
		else if(e.getSource().equals(jButton7)){
			this.opcion = 4;
			ejecutarAccion();
		} else if(e.getSource().equals(jButton8)){
			this.opcion = 9;
			ejecutarAccion();
		} else if(e.getSource().equals(jButton10)){
			this.opcion = 10;
			ejecutarAccion();
		} else if(e.getSource().equals(jButton9)){
			dispose();
		} else if(e.getSource().equals(jButton11) && jButton11.isEnabled()){
			this.opcion = 11;
			ejecutarAccion();
		} else if(e.getSource().equals(jButton12) && jButton12.isEnabled()){
			this.opcion = 12;
			ejecutarAccion();
		} else if(e.getSource().equals(jButtonBonoRegalo) && jButtonBonoRegalo.isEnabled()){
			this.opcion = 16;
			ejecutarAccion();
		} else if(e.getSource().equals(jButtonSinc)){
			this.opcion = 20;
			ejecutarAccion();
		}
		
		
		agregarListeners();

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

		eliminarListeners();
		
		//Mapeo de ESC
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			dispose();
		}
		
		//Mapeo de ENTER
		else if((e.getKeyCode() == KeyEvent.VK_ENTER)){
			if (e.getSource().equals(getJButton9())) {
				dispose();
			} else ejecutarAccion(e);
		}
		
		//Mapeo de Espacio
		else if(e.getKeyCode() == KeyEvent.VK_SPACE){
			if(e.getSource().equals(jButton9)){
				dispose();
			}
		}
				
		//Mapeo de F1
		else if(e.getKeyCode() == KeyEvent.VK_F1){
			this.opcion = 1;
			ejecutarAccion();
		}

		//Mapeo de F2
		else if(e.getKeyCode() == KeyEvent.VK_F2){
			this.opcion = 2;
			ejecutarAccion();
		}

		//Mapeo de F3
		else if(e.getKeyCode() == KeyEvent.VK_F3){
			this.opcion = 3;
			ejecutarAccion();
		}

		//Mapeo de F4
		else if(e.getKeyCode() == KeyEvent.VK_F4){
			this.opcion = 4;
			ejecutarAccion();
		}

		//Mapeo de F5
		else if(e.getKeyCode() == KeyEvent.VK_F5){
			this.opcion = 5;
			ejecutarAccion();
		}

		//Mapeo de F6
		else if(e.getKeyCode() == KeyEvent.VK_F6){
			this.opcion = 6;
			ejecutarAccion();
		}
		
		//Mapeo de F7
		else if(e.getKeyCode() == KeyEvent.VK_F7){
			this.opcion = 7;
			ejecutarAccion();
		}
		
		//Mapeo de F8
		else if(e.getKeyCode() == KeyEvent.VK_F8){
			this.opcion = 8;
			ejecutarAccion();
		}

		//Mapeo de F9
		else if(e.getKeyCode() == KeyEvent.VK_F9){
			this.opcion = 9;
			ejecutarAccion();
		}
		
		//Mapeo de F10
		else if(e.getKeyCode() == KeyEvent.VK_F10){
			this.opcion = 10;
			ejecutarAccion();
	 	}
		//Mapeo de F11
		else if(e.getKeyCode() == KeyEvent.VK_F11 && jButton11.isEnabled()){
			this.opcion = 11;
			ejecutarAccion();
		}
		//Mapeo de F12
		else if(e.getKeyCode() == KeyEvent.VK_F12 && jButton12.isEnabled()){
			this.opcion = 12;
			ejecutarAccion();
		}
		//Mapeo de B
		else if(e.getKeyCode() == KeyEvent.VK_B){
			this.opcion = 16;
			ejecutarAccion();
		}

		else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			if(e.getSource() instanceof JButton)
				((JButton)e.getSource()).transferFocus();
		}

		else if(e.getKeyCode() == KeyEvent.VK_UP) {
			if(e.getSource() instanceof JButton)
				((JButton)e.getSource()).transferFocusBackward();
		}
		
		else if(e.getKeyCode() == KeyEvent.VK_TAB) {
			if(e.getSource() instanceof JButton)
				((JButton)e.getSource()).transferFocus();
		}
		
		else if(e.getKeyCode() == KeyEvent.VK_T){
			this.opcion = 20;
			ejecutarAccion();
		}
		

		agregarListeners();

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
	 * Método ejecutarAccion
	 * 		Recupera la transacción según sea el caso indicado.
	 */
	private void ejecutarAccion(KeyEvent k) {
		if (logger.isDebugEnabled()) {
			logger.debug("ejecutarAccion(KeyEvent) - start");
		}

		if(k.getSource() instanceof JButton){
			JButton boton = new JButton();
			boton = (JButton)k.getSource();
			if(boton.getActionCommand().equals("ClienteTemporal"))
				opcion = 1;	
			else if(boton.getActionCommand().equals("ReimprimirFactura"))
				opcion = 2;
			else if(boton.getActionCommand().equals("Reportes"))
				opcion = 3;	
			else if(boton.getActionCommand().equals("RetencionIVA"))
				opcion = 4;	
			else if(boton.getActionCommand().equals("AvisoEntrega"))
				opcion = 5;
			else if(boton.getActionCommand().equals("CambiarClave"))
				opcion = 6;
			else if(boton.getActionCommand().equals("verificarLinea"))
				opcion = 7;	
			else if(boton.getActionCommand().equals("Seguridad"))
				opcion = 8;	
			else if(boton.getActionCommand().equals("Salir"))
				opcion = 10;
			else if (boton.getActionCommand().equals(this.getJButton8().getActionCommand()))
				opcion = 9;
			else if(boton.getActionCommand().equalsIgnoreCase("Bono Regalo"))
				opcion=16;
			else if (boton.getActionCommand().equals("TransInm"))
				opcion = 20;
		}
		ejecutarAccion();

		if (logger.isDebugEnabled()) {
			logger.debug("ejecutarAccion(KeyEvent) - end");
		}
	}

	/**
	 * Método ejecutarAccion
	 * 		Recupera la transacción según sea el caso indicado.
	 */
	@SuppressWarnings("unused")
	private void ejecutarAccion(MouseEvent k) {
		if (logger.isDebugEnabled()) {
			logger.debug("ejecutarAccion(MouseEvent) - start");
		}

		if(k.getSource() instanceof JButton){
			JButton boton = new JButton();
			boton = (JButton)k.getSource();
			if(boton.getActionCommand().equals("ClienteTemporal"))
				opcion = 1;	
			else if(boton.getActionCommand().equals("ReimprimirFactura"))
				opcion = 2;
			else if(boton.getActionCommand().equals("Reportes"))
				opcion = 3;	
			else if(boton.getActionCommand().equals("RetencionIVA"))
				opcion = 4;	
			else if(boton.getActionCommand().equals("AvisoEntrega"))
				opcion = 5;
			else if(boton.getActionCommand().equals("CambiarClave"))
				opcion = 6;
			else if(boton.getActionCommand().equals("verificarLinea"))
				opcion = 7;	
			else if(boton.getActionCommand().equals("Seguridad"))
				opcion = 8;	
			else if(boton.getActionCommand().equals("Salir"))
				opcion = 10;
			else if (boton.getActionCommand().equals(this.getJButton8().getActionCommand()))
				opcion = 9;
			else if (boton.getActionCommand().equals("TransInm"))
				opcion = 20;
		}
		ejecutarAccion();

		if (logger.isDebugEnabled()) {
			logger.debug("ejecutarAccion(MouseEvent) - end");
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
			//dispose();
			//clientTemp = null;
			//clientTemp = new RegistroClientesTemp();
			//MensajesVentanas.centrarVentanaDialogo(clientTemp);
			break;
		case 2:
			dispose();
			try {
				CR.meVenta.reimprimirFactura();
			} catch (ConexionExcepcion e2) {
				logger.error("ejecutarAccion()", e2);

				MensajesVentanas.mensajeError(e2.getMensaje());
			} catch (ExcepcionCr e2) {
				logger.error("ejecutarAccion()", e2);

				MensajesVentanas.mensajeError(e2.getMensaje());
			} catch (PrinterNotConnectedException e2) {
				logger.error("ejecutarAccion()", e2);

				MensajesVentanas.mensajeError(e2.getMessage());
			}
			break;
		case 3:
				dispose();
				r = null;
				r = new Reportes();
				MensajesVentanas.centrarVentanaDialogo(r);
				emitidoRepZ = r.isEmitidoRepZ();
				break;
			case 4:
				dispose();
				try {
					CR.me.efectuarRetencionIVA();
				} catch (ConexionExcepcion e3) {
					logger.error("ejecutarAccion()", e3);
					MensajesVentanas.mensajeError(e3.getMensaje());
				} catch (ExcepcionCr e3) {
					logger.error("ejecutarAccion()", e3);
					MensajesVentanas.mensajeError(e3.getMensaje());
				}
				break;
			case 5:
				dispose();
				try {
					CR.me.suspenderAvisoEntregaCaja();
				} catch (ConexionExcepcion e2) {
					logger.error("ejecutarAccion()", e2);

					MensajesVentanas.mensajeError(e2.getMensaje());
				} catch (ExcepcionCr e2) {
					logger.error("ejecutarAccion()", e2);

					MensajesVentanas.mensajeError(e2.getMensaje());
				}
				break;
			case 6:
				dispose();
				MaquinaDeEstado.cambiarClave();
				break;
			case 7:
				if(getJButton4().isEnabled()){
					try {
						CR.me.sacarRecuperarLinea();
						dispose();
					} catch (ConexionExcepcion e1) {
						logger.error("ejecutarAccion()", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (ExcepcionCr e1) {
						logger.error("ejecutarAccion()", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
					}
				}
				break;
			case 8:
				if(getJButton2().isEnabled()){
					try {
						if (Sesion.getCaja().getEstado().equals("10") || Sesion.getCaja().getEstado().equals("11")){
							if(MensajesVentanas.preguntarSiNo("¿Desea cancelar la transacción actual?")==0) {
								CR.meVenta.anularVentaActiva();
							}
							else break;
						}		
						dispose();
						CR.me.iraSeguridad();
						menuSeguridad = null;
						menuSeguridad = new OpcionesSeguridad();
						MensajesVentanas.centrarVentanaDialogo(menuSeguridad);
						menuSeguridad = null;
					} catch (ConexionExcepcion e) {
						logger.error("ejecutarAccion()", e);

						MensajesVentanas.mensajeError("No se pudieron cargar Perfiles");
					} catch (ExcepcionCr e) {
						logger.error("ejecutarAccion()", e);

						MensajesVentanas.mensajeError(e.getMensaje());
					}
				}
				break;
			case 9:
	            if (getJButton8().isEnabled()) {
	                MenuUtilitariosOtrasFuncionesFactory menuFactory = new MenuUtilitariosOtrasFuncionesFactory();
	                menuFactory.getInstance().mostrarVentanaInicial(this);
	            }
				break;
			case 10:
				if (getJButton10().isEnabled()) {
					try {
						if (Sesion.getCaja().getEstado().equals("10") || Sesion.getCaja().getEstado().equals("11")){
							if(MensajesVentanas.preguntarSiNo("¿Desea cancelar la transacción actual?")==0) {
								CR.meVenta.anularVentaActiva();
						}
							else break;
						}
						
						dispose();
						sesionCerrada = CR.me.desbloquearCajaAutorizado();
						Auditoria.registrarAuditoria("Caja desbloqueada con autorización",'O');
						if (logger.isDebugEnabled()) {
							logger.debug("desbloquearCaja() - end");
						}
								
					} catch (UsuarioExcepcion e) {
						MensajesVentanas.mensajeError(e.getMensaje());
					} catch (MaquinaDeEstadoExcepcion e) {
						MensajesVentanas.mensajeError(e.getMensaje());
					} catch (ConexionExcepcion e) {
						MensajesVentanas.mensajeError(e.getMensaje());
					} catch (ExcepcionCr e) {
						MensajesVentanas.mensajeError(e.getMensaje());
					}
				}
				break;	
			case 11:
				//Agregada llamada al actualizador de precios (modulo de promociones)
				dispose();
				if(actualizadorPrecios==null){
					iniciarActualizadorPrecios();			
				}
				actualizadorPrecios.sumarDonaciones(false);
				break;
			case 12:
				//Agregada llamada al actualizador de precios (modulo de promociones)
				dispose();
				if(actualizadorPrecios==null){
					iniciarActualizadorPrecios();			
				}
				if(CR.meVenta.getVenta()!=null)
					actualizadorPrecios.ventanaPromociones(CR.meVenta.getVenta());
				if(CR.meServ.getApartado()!=null)
					(new ActualizadorPreciosSACFactory()).getActualizadorPreciosSACInstance().ventanaPromociones(CR.meServ.getApartado());
				break;
			case 16:
				dispose();
				BonoRegalo bono = new BonoRegalo();
				MensajesVentanas.centrarVentanaDialogo(bono);
				break;
			
			case 20:
				dispose();
				SolicitarCodDet s = new SolicitarCodDet("Introduzca el código del producto","Sincronización Inmediata");
				MensajesVentanas.centrarVentanaDialogo(s);
				s = null;
				break;
		}
				
			

		if (logger.isDebugEnabled()) {
			logger.debug("ejecutarAccion() - end");
		}
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
			jButton5.setText("F1 - Registrar Cliente Temporal");
			jButton5.setActionCommand("ClienteTemporal");
			jButton5.setBackground(new java.awt.Color(226,226,222));
			jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton5.setEnabled(false);
			
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton5() - end");
		}
		return jButton5;
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
			jButton3.setText("F6 - Cambiar contraseña");
			jButton3.setActionCommand("CambiarClave");
			jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton3.setBackground(new java.awt.Color(226,226,222));
			jButton3.addKeyListener(this);
			jButton3.setEnabled(CR.meVenta.getVenta()!=null || Sesion.getCaja().getEstado().equalsIgnoreCase(Sesion.INICIADA));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton3() - end");
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
			jButton2.setText("F8 - Opciones de Seguridad");
			jButton2.setActionCommand("Seguridad");
			jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton2.setBackground(new java.awt.Color(226,226,222));
			jButton2.addKeyListener(this);
			if(CR.meVenta.getVenta()!=null){
				if(!MaquinaDeEstado.isAccesoFuncion(4, 1)){
					jButton2.setEnabled(false);
				} else jButton2.setEnabled(true);
			} else 
				jButton2.setEnabled(Sesion.getCaja().getEstado().equalsIgnoreCase(Sesion.INICIADA));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton2() - end");
		}
		return jButton2;
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
			jButton4.setBackground(new java.awt.Color(226,226,222));
			if (Sesion.isVerificarLinea() && Sesion.isCajaEnLinea())
				jButton4.setText("F7 - Colocar Fuera de Línea");
			else if(Sesion.isVerificarLinea() && !Sesion.isCajaEnLinea()) 
				jButton4.setText("F7 - Suspender Verificación de Linea");
			else jButton4.setText("F7 - Recuperar Línea");
			jButton4.setActionCommand("verificarLinea");
			jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			if(CR.meVenta.getVenta()!=null){
				if(!MaquinaDeEstado.isAccesoFuncion(44, 1)){
					jButton4.setEnabled(false);
				} else jButton4.setEnabled(true);
			} else
				jButton4.setEnabled(Sesion.getCaja().getEstado().equalsIgnoreCase(Sesion.INICIADA));
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
			jButton1.setText("F3 - Emitir Reportes");
			jButton1.setActionCommand("Reportes");
			jButton1.setBackground(new java.awt.Color(226,226,222));
			jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton1.setEnabled(CR.meVenta.getVenta()!=null || Sesion.getCaja().getEstado().equalsIgnoreCase(Sesion.INICIADA));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton1() - end");
		}
		return jButton1;
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
			jButton.setPreferredSize(new java.awt.Dimension(145,26));
			jButton.setBackground(new java.awt.Color(226,226,222));
			jButton.setText("F5 - Suspender Aviso Entrega a Caja");
			jButton.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jButton.setActionCommand("AvisoEntrega");
			jButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton.setEnabled(CR.meVenta.getVenta()!=null  || Sesion.getCaja().getEstado().equalsIgnoreCase(Sesion.INICIADA));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton() - end");
		}
		return jButton;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton10() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton10() - start");
		}

		if(jButton10 == null) {
			jButton10 = new JHighlightButton();
			jButton10.setText("F10 - Cerrar Sesión");
			jButton10.setActionCommand("Salir");
			jButton10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton10.setBackground(new java.awt.Color(226,226,222));
			jButton10.addKeyListener(this);
			jButton10.setEnabled(CR.meVenta.getVenta()!=null || Sesion.getCaja().getEstado().equalsIgnoreCase(Sesion.INICIADA));
			
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton10() - end");
		}
		return jButton10;
	}
	
	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton11() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton11() - start");
		}
		if(jButton11 == null) {
			//MODIFICADO BECO 12/06/2008
			//Agregada llamada al actualizador de precios para sumar o no donaciones
			if(actualizadorPrecios==null){
				iniciarActualizadorPrecios();			
			}
			jButton11 = actualizadorPrecios.instanciarBoton();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton11() - end");
		}
		return jButton11;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton12() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton12() - start");
		}
		if(jButton12 == null) {
			//MODIFICADO BECO 30/06/2008
			//Agregada llamada al actualizador de precios 
			if(actualizadorPrecios==null){
				iniciarActualizadorPrecios();			
			}
			jButton12 = actualizadorPrecios.instanciarBotonPromociones();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton12() - end");
		}
		return jButton12;
	}
	
	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButtonBonoRegalo() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButtonBonoRegalo() - start");
		}
		
		if(jButtonBonoRegalo == null) {
			jButtonBonoRegalo = new JHighlightButton();
			jButtonBonoRegalo.setText("B - Bono Regalo");
			jButtonBonoRegalo.setActionCommand("Bono Regalo");
			jButtonBonoRegalo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButtonBonoRegalo.setBackground(new java.awt.Color(226,226,222));	
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("getJButtonBonoRegalo() - end");
		}
		return jButtonBonoRegalo;
	}
			
	/**
	 * Método isEmitidoRepZ
	 * 
	 * @return boolean
	 */
	public boolean isEmitidoRepZ() {
		if (logger.isDebugEnabled()) {
			logger.debug("isEmitidoRepZ() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isEmitidoRepZ() - end");
		}
		return emitidoRepZ;
	}

			
	/**
	 * Método isCierreAutorizado
	 * 
	 * @return boolean
	 */
	public boolean isCierreAutorizado() {
		if (logger.isDebugEnabled()) {
			logger.debug("isCierreAutorizado() - start");
		}
	
		if (logger.isDebugEnabled()) {
			logger.debug("isCierreAutorizado() - end");
		}
		return sesionCerrada;
	}



	private void agregarListeners() {
		if (logger.isDebugEnabled()) {
			logger.debug("agregarListeners() - start");
		}

		jButton.addKeyListener(this);
		jButton.addMouseListener(this);

		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);

		jButton2.addKeyListener(this);
		jButton2.addMouseListener(this);

		jButton3.addKeyListener(this);
		jButton3.addMouseListener(this);

		jButton4.addKeyListener(this);
		jButton4.addMouseListener(this);

		//jButton5.addKeyListener(this);
		//jButton5.addMouseListener(this);
		
		jButton6.addKeyListener(this);
		jButton6.addMouseListener(this);

		jButton7.addKeyListener(this);
		jButton7.addMouseListener(this);
		
		jButton8.addKeyListener(this);
		jButton8.addMouseListener(this);

		jButton9.addKeyListener(this);
		jButton9.addMouseListener(this);
		
		jButton10.addKeyListener(this);
		jButton10.addMouseListener(this);
		
		jButton11.addKeyListener(this);
		jButton11.addMouseListener(this);
		
		jButton12.addKeyListener(this);
		jButton12.addMouseListener(this);

		jButtonSinc.addKeyListener(this);
		jButtonSinc.addMouseListener(this);
		
		jButtonBonoRegalo.addKeyListener(this);
		jButtonBonoRegalo.addMouseListener(this);
		
		if (logger.isDebugEnabled()) {
			logger.debug("agregarListeners() - end");
		}
	}	
	
	private void eliminarListeners() {
		if (logger.isDebugEnabled()) {
			logger.debug("eliminarListeners() - start");
		}

		jButton.removeKeyListener(this);
		jButton.removeMouseListener(this);

		jButton1.removeKeyListener(this);
		jButton1.removeMouseListener(this);

		jButton2.removeKeyListener(this);
		jButton2.removeMouseListener(this);

		jButton3.removeKeyListener(this);
		jButton3.removeMouseListener(this);

		jButton4.removeKeyListener(this);
		jButton4.removeMouseListener(this);

		//jButton5.removeKeyListener(this);
		//jButton5.removeMouseListener(this);
		
		jButton6.removeKeyListener(this);
		jButton6.removeMouseListener(this);
		
		jButton7.removeKeyListener(this);
		jButton7.removeMouseListener(this);
		
		jButton8.removeKeyListener(this);
		jButton8.removeMouseListener(this);

		jButton9.removeKeyListener(this);
		jButton9.removeMouseListener(this);

		jButton10.removeKeyListener(this);
		jButton10.removeMouseListener(this);
		
		jButton11.removeKeyListener(this);
		jButton11.removeMouseListener(this);
		
		jButton12.removeKeyListener(this);
		jButton12.removeMouseListener(this);
		
		jButtonBonoRegalo.removeKeyListener(this);
		jButtonBonoRegalo.removeMouseListener(this);
		jButtonSinc.removeKeyListener(this);
		jButtonSinc.removeMouseListener(this);
		
		if (logger.isDebugEnabled()) {
			logger.debug("eliminarListeners() - end");
		}
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
			jButton6.setBackground(new java.awt.Color(226,226,222));
			jButton6.setActionCommand("ReimprimirFactura");
			jButton6.setText("F2 - Reimprimir Última Factura");
			jButton6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton6.setEnabled(CR.meVenta.getVenta()!=null  || Sesion.getCaja().getEstado().equalsIgnoreCase(Sesion.INICIADA));
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
			jButton7.setBackground(new java.awt.Color(226,226,222));
			jButton7.setActionCommand("RetencionIVA");
			jButton7.setText("F4 - Retener IVA");
			jButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton7.setEnabled(CR.meVenta.getVenta()!=null  || Sesion.getCaja().getEstado().equalsIgnoreCase(Sesion.INICIADA));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton7() - end");
		}
		return jButton7;
	}
	private javax.swing.JButton getJButton8() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton8() - start");
		}

		if(jButton8 == null) {
		    MenuUtilitariosOtrasFuncionesFactory menuFactory = new MenuUtilitariosOtrasFuncionesFactory();
            this.jButton8 = menuFactory.getInstance().instanciarBoton();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton8() - end");
		}
		return jButton8;
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
			jPanel2.add(getJButton9(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(420,35));
			jPanel2.setBackground(new java.awt.Color(226,226,222));
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


	/**
	 * Botón agregado para la sincronización inmediata, código tomado del botón Cerrar Sesión.	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private javax.swing.JButton getJButtonSinc() {
		if (jButtonSinc == null) {
			jButtonSinc = new JHighlightButton();
			jButtonSinc.setText("T - Transferencia Inmediata");
			jButtonSinc.setActionCommand("TransInm");
			jButtonSinc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButtonSinc.setBackground(new java.awt.Color(226,226,222));
			jButtonSinc.addKeyListener(this);
			jButtonSinc.setEnabled(CR.meVenta.getVenta()!=null || Sesion.getCaja().getEstado().equalsIgnoreCase(Sesion.INICIADA));
		}
		return jButtonSinc;
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
	
	/*
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

}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"  @jve:decl-index=0:visual-constraint="10,10"
