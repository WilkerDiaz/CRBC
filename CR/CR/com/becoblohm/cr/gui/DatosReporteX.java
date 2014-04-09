/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : DatosReporteX.java
 * Creado por : Gabriel Martinelli
 * Creado en  : 19/10/2004 02:44 PM
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
package com.becoblohm.cr.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.utiles.Control;
import com.becoblohm.cr.utiles.MensajesVentanas;

public class DatosReporteX extends JDialog implements ComponentListener, KeyListener, MouseListener, ActionListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DatosReporteX.class);

	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JLabel jLabel3 = null;
	private javax.swing.JLabel jLabel4 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JTextField jTextField = null;
	private JSpinner selectorFecha = null;
	private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
	private String usuario = null;
	private Date fecha = null;
	

	/**
	 * This is the default constructor
	 */
	public DatosReporteX() {
		super(MensajesVentanas.ventanaActiva);
		initialize();
		this.getJTextField().selectAll();
		
		getJTextField().addMouseListener(this);
		getJTextField().addKeyListener(this);

		getSelectorFecha().addMouseListener(this);
		getSelectorFecha().addKeyListener(this);
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

		this.setContentPane(getJContentPane());
		this.setSize(271, 186);
		this.setTitle("Emitir Reporte X");
		this.setBackground(new java.awt.Color(226,226,222));
		this.setModal(true);
		this.setResizable(false);
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
			jContentPane.setLayout(new java.awt.FlowLayout());
			jContentPane.add(getJPanel1(), null);
			jContentPane.add(getJPanel2(), null);
			jContentPane.setPreferredSize(new java.awt.Dimension(270,186));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
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
			java.awt.FlowLayout layFlowLayout11 = new java.awt.FlowLayout();
			layFlowLayout11.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout11.setVgap(7);
			jPanel.setLayout(layFlowLayout11);
			jPanel.add(getJLabel1(), null);
			jPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,4,0,0));
			jPanel.setBackground(new java.awt.Color(69,107,127));
			jPanel.setPreferredSize(new java.awt.Dimension(255,46));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel() - end");
		}
		return jPanel;
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
			jLabel1.setText("Reporte X");
			jLabel1.setForeground(java.awt.Color.white);
			jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/reportex.png")));
			jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel1() - end");
		}
		return jLabel1;
	}
	/**
	 * Método getSelectorFecha
	 * 
	 * @return JSpinner
	 */
	private JSpinner getSelectorFecha(){
		if (logger.isDebugEnabled()) {
			logger.debug("getSelectorFecha() - start");
		}

		if(selectorFecha == null){
			Date today;
			today = Sesion.getFechaSistema();
			Calendar limite = Calendar.getInstance();
			selectorFecha = new JSpinner(new SpinnerDateModel(today, null, limite.getTime(), Calendar.DAY_OF_MONTH));
			JSpinner.DateEditor de = new JSpinner.DateEditor(selectorFecha, "dd/MM/yyyy");
			selectorFecha.setEditor(de);
			selectorFecha.setPreferredSize(new java.awt.Dimension(120,20));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getSelectorFecha() - end");
		}
		return selectorFecha;
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
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setAlignment(java.awt.FlowLayout.RIGHT);
			layFlowLayout1.setVgap(0);
			layFlowLayout1.setHgap(0);
			jPanel2.setLayout(layFlowLayout1);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.setBackground(new java.awt.Color(226,226,222));
			jPanel2.setPreferredSize(new java.awt.Dimension(255,35));
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
			jButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
			jButton.setText("Aceptar");
			jButton.addActionListener(this);
			jButton.addKeyListener(this);
			jButton.setBackground(new java.awt.Color(226,226,222));
			jButton.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jButton.setHorizontalTextPosition(javax.swing.SwingConstants.TRAILING);
			jButton.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
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
			jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
			jButton1.setText("Cancelar");
			jButton1.addActionListener(this);
			jButton1.addKeyListener(this);
			jButton1.setBackground(new java.awt.Color(226,226,222));
			jButton1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jButton1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton1() - end");
		}
		return jButton1;
	}
	public void actionPerformed(ActionEvent e){
		if (logger.isDebugEnabled()) {
			logger.debug("actionPerformed(ActionEvent) - start");
		}

		JButton btnComando = (JButton)e.getSource();
		if (btnComando.getText().equals("Aceptar")) {
			if (cargarDatos()) {
				try {
					int longitud = usuario.length();
					StringBuffer identificador = new StringBuffer(usuario);
					for (int i=0; i<Control.getLONGITUD_ID()-longitud; i++){
						identificador.insert(0,'0');
					}
					CR.me.emitirReporteX(identificador.substring(0, Control.getLONGITUD_ID()), fecha);
					this.dispose();
				} catch (ExcepcionCr e1) {
					logger.error("actionPerformed(ActionEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (ConexionExcepcion e1) {
					logger.error("actionPerformed(ActionEvent)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
				}
			} else {
				MensajesVentanas.aviso("Ingrese los Datos Solicitados");
			}
		} else {
			if (btnComando.getText().equals("Cancelar")) {
				this.dispose();
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("actionPerformed(ActionEvent) - end");
		}
	}

	public void keyPressed(KeyEvent e){
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}

		//JButton boton = (JButton)e.getSource();
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (e.getSource().equals(jButton)) {
				if (cargarDatos()) {
					try {
						int longitud = usuario.length();
						StringBuffer identificador = new StringBuffer(usuario);
						for (int i=0; i<Control.getLONGITUD_ID()-longitud; i++){
							identificador.insert(0,'0');
						}
						CR.me.emitirReporteX(identificador.substring(0, Control.getLONGITUD_ID()), fecha);
						this.dispose();
					} catch (ExcepcionCr e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (ConexionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
					}
				} else {
					MensajesVentanas.aviso("Ingrese los Datos Solicitados");
				}
			} else if (e.getSource().equals(jButton1)) {
				this.dispose();
			} else if (e.getSource().equals(this.getJTextField())) {
				jTextField.transferFocus();
			} else if (e.getSource().equals(this.getSelectorFecha())) {
				getSelectorFecha().transferFocus();
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
			this.dispose();
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - end");
		}
	}

	public void keyReleased(KeyEvent e){
		if (logger.isDebugEnabled()) {
			logger.debug("keyReleased(KeyEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("keyReleased(KeyEvent) - end");
		}
	}

	public void keyTyped(KeyEvent e){
		if (logger.isDebugEnabled()) {
			logger.debug("keyTyped(KeyEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("keyTyped(KeyEvent) - end");
		}
	}
	
	boolean cargarDatos(){
		if (logger.isDebugEnabled()) {
			logger.debug("cargarDatos() - start");
		}

		usuario = jTextField.getText();		
		fecha = (Date)this.selectorFecha.getValue();
		if ((fecha == null) || (usuario==null) || (usuario.equalsIgnoreCase(""))) {
			if (logger.isDebugEnabled()) {
				logger.debug("cargarDatos() - end");
			}
			return false;
		}
		else {
			if (logger.isDebugEnabled()) {
				logger.debug("cargarDatos() - end");
			}
			return true;
		}
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
			jLabel3.setText("Usuario");
			jLabel3.setMinimumSize(new java.awt.Dimension(84,20));
			jLabel3.setPreferredSize(new java.awt.Dimension(58,16));
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
			jLabel4.setText("Fecha");
			jLabel4.setMinimumSize(new java.awt.Dimension(71,20));
			jLabel4.setPreferredSize(new java.awt.Dimension(58,16));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel4() - end");
		}
		return jLabel4;
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
			layFlowLayout2.setHgap(10);
			layFlowLayout2.setAlignment(java.awt.FlowLayout.CENTER);
			jPanel4.setLayout(layFlowLayout2);
			jPanel4.add(getJLabel3(), null);
			jPanel4.add(getJTextField(), null);
			jPanel4.add(getJLabel4(), null);
			jPanel4.add(getSelectorFecha(), null);
			jPanel4.setMinimumSize(new java.awt.Dimension(168,20));
			jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel4.setPreferredSize(new java.awt.Dimension(255,60));
			jPanel4.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel4() - end");
		}
		return jPanel4;
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
			jTextField.setText(Sesion.getUsuarioActivo().getNumFicha());
			jTextField.setHorizontalAlignment(javax.swing.JTextField.LEADING);
			jTextField.setPreferredSize(new java.awt.Dimension(120,20));
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
			layFlowLayout21.setHgap(0);
			layFlowLayout21.setVgap(0);
			jPanel1.setLayout(layFlowLayout21);
			jPanel1.add(getJPanel(), null);
			jPanel1.add(getJPanel4(), null);
			jPanel1.setPreferredSize(new java.awt.Dimension(255,105));
			jPanel1.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel1() - end");
		}
		return jPanel1;
	}
	/**
	 * Método mouseClicked
	 *
	 * @param e
	 */
	public void mouseClicked(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - end");
		}
	}
	/**
	 * Método mouseEntered
	 *
	 * @param e
	 */
	public void mouseEntered(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseEntered(MouseEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("mouseEntered(MouseEvent) - end");
		}
	}
	/**
	 * Método mouseExited
	 *
	 * @param e
	 */
	public void mouseExited(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseExited(MouseEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("mouseExited(MouseEvent) - end");
		}
	}
	/**
	 * Método mousePressed
	 *
	 * @param e
	 */
	public void mousePressed(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mousePressed(MouseEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("mousePressed(MouseEvent) - end");
		}
	}
	/**
	 * Método mouseReleased
	 *
	 * @param e
	 */
	public void mouseReleased(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseReleased(MouseEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("mouseReleased(MouseEvent) - end");
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