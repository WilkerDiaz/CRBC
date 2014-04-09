/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui.bonoregalo
 * Programa   : AgregarTarjeta.java
 * Creado por : jgraterol
 * Creado en  : 21-10-2010 11:12:07
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
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.utiles.MensajesVentanas;
/**
 * Descripción:
 * 
 */

public class AgregarTarjeta extends JDialog implements ComponentListener, KeyListener, ActionListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AgregarTarjeta.class);

	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanelDetalle = null;
	private javax.swing.JTextField jTextField = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JLabel jLabelDetalle = null;
	

	private boolean canceladaEjecucion = false;
	@SuppressWarnings("unused")
	private DecimalFormat df = new DecimalFormat("#,##0.00"); 
	private boolean restar=false;
	private boolean sumar=false;
	private int seleccionado;
	private String primerDigito = null;

	
	/**
	 * This is the default constructor
	 */
	public AgregarTarjeta(String digito) {
		super(MensajesVentanas.ventanaActiva);
		
		this.primerDigito=digito;
		initialize();
		jLabelDetalle.setText("Creando nuevo detalle");
		jTextField.selectAll();
		
		jTextField.addKeyListener(this);
		
		jButton.addKeyListener(this);
		jButton.addActionListener(this);
		
		jButton1.addKeyListener(this);
		jButton1.addActionListener(this);

	}
	
	
	/**
	 * This is the default constructor
	 */
	public AgregarTarjeta(boolean sumar,boolean restar, int seleccionado) {
		super(MensajesVentanas.ventanaActiva);
		this.sumar=sumar;
		this.restar=restar;
		this.seleccionado = seleccionado;
		if(!sumar && !restar)
			this.getJLabel1().setText("Agregar tarjeta");
		else
			this.getJLabel1().setText(sumar?"Sumar a una tarjeta":"Restar a una tarjeta");
		initialize();
		jLabelDetalle.setText("Modificar detalle: "+(seleccionado+1));
		jTextField.selectAll();
		
		jTextField.addKeyListener(this);
		
		jButton.addKeyListener(this);
		jButton.addActionListener(this);
		
		jButton1.addKeyListener(this);
		jButton1.addActionListener(this);

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
		this.setTitle("Agregar tarjeta");
		this.setSize(300, 250);
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
		
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}

		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			this.canceladaEjecucion = true;
			dispose();
		}
		
		else if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(e.getSource().equals(this.getJTextField())) {
				jButton1.requestFocus();
			}
			
			if (e.getSource() instanceof JButton) {
				((JButton)e.getSource()).doClick();
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
			//java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			java.awt.FlowLayout gridLayout = new java.awt.FlowLayout();
			
			gridLayout.setHgap(0);
			gridLayout.setVgap(30);
			jPanel.setLayout(gridLayout);
			jPanel.add(getJLabel2(), null);
			jPanel.add(getJTextField(), null);
			jPanel.setPreferredSize(new java.awt.Dimension(275,85));
			jPanel.setBackground(new java.awt.Color(242,242,238));
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Monto "+Sesion.getTienda().getMonedaBase(), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel() - end");
		}
		return jPanel;
	}
	
	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanelDetalle() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanelDetalle() - start");
		}

		if(jPanelDetalle == null) {
			jPanelDetalle = new javax.swing.JPanel();
			//java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			java.awt.FlowLayout flowLayout = new java.awt.FlowLayout();
			
			flowLayout.setHgap(0);
			flowLayout.setVgap(5);
			jPanelDetalle.setLayout(flowLayout);
			jPanelDetalle.add(getJLabelDetalle(), null);
			jPanelDetalle.setPreferredSize(new java.awt.Dimension(275,30));
			jPanelDetalle.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanelDetalle() - end");
		}
		return jPanelDetalle;
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
			jTextField.setText(primerDigito);
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
			jButton1.setText("Aceptar");
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
			jPanel3.add(getJPanelDetalle(),null);
			jPanel3.add(getJPanel(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(280,160));
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
			jLabel1.setText("Agregar Tarjeta");
			jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel1.setForeground(java.awt.Color.white);
			//jLabel1.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/add2.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel1() - end");
		}
		return jLabel1;
	}
	
	
	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel2() - start");
		}

		if(jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setText(" Monto:");
			jLabel2.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 16));
			
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel1() - end");
		}
		return jLabel2;
	}
	
	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabelDetalle() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabelDetalle() - start");
		}

		if(jLabelDetalle == null) {
			jLabelDetalle = new javax.swing.JLabel();
			jLabelDetalle.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 16));
			
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabelDetalle() - end");
		}
		return jLabelDetalle;
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
		jTextField.removeKeyListener(this);
		jButton.removeKeyListener(this);
		jButton.removeActionListener(this);
		jButton1.removeKeyListener(this);
		jButton1.removeActionListener(this);
		
		if(e.getSource().equals(this.getJTextField())) {
			jButton1.requestFocus();
		} else if(e.getSource().equals(this.getJButton1())) {
			//Agregar tarjeta
			try{
				if(getJTextField().getText()!=null && !getJTextField().getText().equalsIgnoreCase("") && new Double(CR.me.formatoNumerico(getJTextField().getText())).doubleValue()!=0){
					if(!sumar && !restar)
						CR.meServ.agregarLineaDetalleBR(new Double(CR.me.formatoNumerico(getJTextField().getText())).doubleValue());
					else
						CR.meServ.modificarLineaDetalleBR((sumar? new Double(CR.me.formatoNumerico(getJTextField().getText())).doubleValue() : -new Double(CR.me.formatoNumerico(getJTextField().getText())).doubleValue()),seleccionado);
					dispose();
				} else 
					MensajesVentanas.mensajeError("El campo monto es obligatorio");
			} catch(Exception ex){
				MensajesVentanas.mensajeError(ex.getMessage());
			}
		} else if(e.getSource().equals(this.getJButton())) {
			this.canceladaEjecucion = true;
			dispose();
		}
		jTextField.addKeyListener(this);
		jButton.addKeyListener(this);
		jButton.addActionListener(this);
		jButton1.addKeyListener(this);
		jButton1.addActionListener(this);
	}

}
