/**
 * =============================================================================
 * Proyecto   : CRBECOEPA
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : Servicios.java
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.utiles.MensajesVentanas;
/**
 * @author vmedina
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Reportes
	extends JDialog
	implements ComponentListener, MouseListener, KeyListener, ActionListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Reportes.class);

	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel5 = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JPanel jPanel7 = null;
	private int opcion;
		
	private javax.swing.JButton jButton6 = null;
	private javax.swing.JButton jButton7 = null;
	
	boolean emitidoRepZ = false;
	
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JButton jButton = null;
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - start");
		}

		eliminarListeners();

		//Mapeo de MouseClicked sobre Cancelar
		if(e.getSource().equals(jButton)){
			dispose();
		}
			
		//Mapeo de MouseClicked sobre Aceptar
		if(e.getSource().equals(jButton6)){
			opcion = 1;
			ejecutarAccion();
		}

		if(e.getSource().equals(jButton7)){
			opcion = 2;
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
		if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE){
			if (e.getSource().equals(getJButton())) {
				dispose();
			} else ejecutarAccion(e);
		}
		
		//Mapeo de F5
		if(e.getKeyCode() == KeyEvent.VK_F5){
			this.opcion = 1;
			ejecutarAccion();
		}
		
		//Mapeo de F6
		if(e.getKeyCode() == KeyEvent.VK_F6){
			this.opcion = 2;
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

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("actionPerformed(ActionEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("actionPerformed(ActionEvent) - end");
		}
	}

	/**
	 * This is the default constructor
	 */
	public Reportes() {
		super(MensajesVentanas.ventanaActiva);
		initialize();
		opcion = 1;
		agregarListeners();
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

		this.setTitle("Reportes");
		this.setSize(292, 235);
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
			jContentPane.add(getJPanel(), null);
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.RAISED));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
			jContentPane.setPreferredSize(new java.awt.Dimension(292,200));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJContentPane() - end");
		}
		return jContentPane;
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
			java.awt.GridLayout layGridLayout1 = new java.awt.GridLayout();
			layGridLayout1.setRows(2);
			layGridLayout1.setColumns(1);
			layGridLayout1.setHgap(0);
			layGridLayout1.setVgap(0);
			jPanel1.setLayout(layGridLayout1);
			jPanel1.add(getJButton6(), null);
			jPanel1.add(getJButton7(), null);
			jPanel1.setPreferredSize(new java.awt.Dimension(260,90));
			jPanel1.setBackground(new java.awt.Color(242,242,238));
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opciones:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel1() - end");
		}
		return jPanel1;
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
			jPanel5.setPreferredSize(new java.awt.Dimension(275,40));
			jPanel5.setBackground(new java.awt.Color(69,107,127));
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
			jLabel.setText("Reportes");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel.setForeground(java.awt.Color.white);
			jLabel.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/line-chart.png")));
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
			jPanel7.setPreferredSize(new java.awt.Dimension(275,150));
			jPanel7.setBackground(new java.awt.Color(242,242,238));
			jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
			jPanel7.add(getJPanel5(), null);
			jPanel7.add(getJPanel1(), null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel7() - end");
		}
		return jPanel7;
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
			if(boton.getActionCommand().equals("ReporteX"))
				opcion = 1;
			else if(boton.getActionCommand().equals("ReporteZ"))
				opcion = 2;	
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
			if(boton.getActionCommand().equals("ReporteX"))
				opcion = 1;
			else if(boton.getActionCommand().equals("ReporteZ"))
				opcion = 2;	
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
			dispose();
			DatosReporteX pantallaRepX = new DatosReporteX();
			MensajesVentanas.centrarVentanaDialogo(pantallaRepX);
			break;
		case 2:
			dispose();
			try {
				//if (MensajesVentanas.preguntarSiNo("Al generar el reporte Z se finalizan las operaciones de la caja en el día.\n¿Desea Continuar?")==0) {
				Calendar fechaHoy = Calendar.getInstance();
				Calendar fechaDiaSiguiente = Calendar.getInstance();
				fechaDiaSiguiente.add(Calendar.DATE, 1);
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				
				if (MensajesVentanas.preguntarSiNo("Se emitirá el reporte Z del día " + df.format(fechaHoy.getTime()) + ".\nLa caja quedará inutilizada hasta el día " + df.format(fechaDiaSiguiente.getTime()) + "\n¿Desea Continuar?")==0) {
					CR.me.emitirReporteZ();
					this.emitidoRepZ = true;
				}
			} catch (ExcepcionCr e) {
				logger.error("ejecutarAccion()", e);

				MensajesVentanas.mensajeError(e.getMensaje());
			} catch (ConexionExcepcion e) {
				logger.error("ejecutarAccion()", e);

				MensajesVentanas.mensajeError(e.getMensaje());
			}
			break;
		}		

		if (logger.isDebugEnabled()) {
			logger.debug("ejecutarAccion() - end");
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
			jButton6.setText("F5 - Emitir Reporte X");
			jButton6.setActionCommand("ReporteX");
			jButton6.setBackground(new java.awt.Color(226,226,222));
			jButton6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
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
			jButton7.setText("F6 - Emitir Reporte Z");
			jButton7.setActionCommand("ReporteZ");
			jButton7.setBackground(new java.awt.Color(226,226,222));
			jButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton7() - end");
		}
		return jButton7;
	}
	/**
	 * Método isEmitidoRepZ
	 * 
	 * @return
	 * boolean
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

	private void agregarListeners() {
		if (logger.isDebugEnabled()) {
			logger.debug("agregarListeners() - start");
		}

		jButton6.addKeyListener(this);
		jButton6.addMouseListener(this);

		jButton7.addKeyListener(this);
		jButton7.addMouseListener(this);
		
		jButton.addKeyListener(this);
		jButton.addMouseListener(this);

		if (logger.isDebugEnabled()) {
			logger.debug("agregarListeners() - end");
		}
	}
	
	private void eliminarListeners() {
		if (logger.isDebugEnabled()) {
			logger.debug("eliminarListeners() - start");
		}

		jButton6.removeKeyListener(this);
		jButton6.removeMouseListener(this);

		jButton7.removeKeyListener(this);
		jButton7.removeMouseListener(this);

		jButton.removeKeyListener(this);
		jButton.removeMouseListener(this);
		
		if (logger.isDebugEnabled()) {
			logger.debug("eliminarListeners() - end");
		}
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel() {
		if(jPanel == null) {
			jPanel = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setHgap(0);
			layFlowLayout1.setVgap(0);
			layFlowLayout1.setAlignment(java.awt.FlowLayout.RIGHT);
			jPanel.setLayout(layFlowLayout1);
			jPanel.add(getJButton(), null);
			jPanel.setPreferredSize(new java.awt.Dimension(275,35));
			jPanel.setBackground(new java.awt.Color(226,226,222));
		}
		return jPanel;
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
			logger.debug("getJButton9() - end");
		}
		return jButton;
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