/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : MenuPromociones.java
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
package com.beco.cr.actualizadorPrecios.promociones.interfaz;

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


import com.beco.cr.actualizadorPrecios.promociones.PromocionExt;
import com.beco.cr.actualizadorPrecios.promociones.ManejadorBD.PromocionExtBD;
import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.extensiones.MenuUtilitariosOtrasFuncionesFactory;
import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.manejarservicio.Apartado;
import com.becoblohm.cr.manejarventa.Venta;
import com.becoblohm.cr.utiles.MensajesVentanas;

/**
 * Descripción:
 * 
 */
public class MenuPromociones
	extends JDialog
	implements ComponentListener, KeyListener, MouseListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MenuPromociones.class);  //  @jve:decl-index=0:

	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JPanel jPanel5 = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JPanel jPanel7 = null;
	private int opcion;
		
	private javax.swing.JButton jButton11 = null;
	private javax.swing.JButton jButton12 = null;

	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton jButton9 = null;
	
	private Venta venta;
	private Apartado apartado;
	
	/**
	 * This is the default constructor
	 */
	public MenuPromociones(Venta venta) {
		super(MensajesVentanas.ventanaActiva);
		this.venta=venta;
		initialize();
		opcion = 1;
		agregarListeners();
	}

	/**
	 * This is the default constructor
	 */
	public MenuPromociones(Apartado apartado) {
		super(MensajesVentanas.ventanaActiva);
		this.apartado=apartado;
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

		this.setSize(457, 290);
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
			jContentPane.setPreferredSize(new java.awt.Dimension(442,330));
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
			jPanel1.setPreferredSize(new java.awt.Dimension(415,295));
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
			layGridLayout1.setRows(2);
			layGridLayout1.setColumns(1);
			ivjTitledBorder.setTitle("Utilidades disponibles");
			jPanel4.setLayout(layGridLayout1);
			jPanel4.add(getJButton11(), null);
			jPanel4.add(getJButton12(), null);
			jPanel4.setBorder(ivjTitledBorder);
			jPanel4.setPreferredSize(new java.awt.Dimension(300,80));
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
			jLabel.setText("Menu Promociones");
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
			jPanel7.setPreferredSize(new java.awt.Dimension(420,180));
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
		if(e.getSource().equals(jButton9)){
			dispose();
		} else if(e.getSource().equals(jButton11) && jButton11.isEnabled()){
			this.opcion = 11;
			ejecutarAccion();
		} else if(e.getSource().equals(jButton12) && jButton12.isEnabled()){
			this.opcion = 12;
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
		//Mapeo de F11
		else if(e.getKeyCode() == KeyEvent.VK_F1 && jButton11.isEnabled()){
			this.opcion = 11;
			ejecutarAccion();
		}
		//Mapeo de F12
		else if(e.getKeyCode() == KeyEvent.VK_F2 && jButton12.isEnabled()){
			this.opcion = 12;
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
		}
		ejecutarAccion();

		if (logger.isDebugEnabled()) {
			logger.debug("ejecutarAccion(KeyEvent) - end");
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
			case 9:
	            if (getJButton9().isEnabled()) {
	                MenuUtilitariosOtrasFuncionesFactory menuFactory = new MenuUtilitariosOtrasFuncionesFactory();
	                menuFactory.getInstance().mostrarVentanaInicial(this);
	            }
				break;	
			case 11:
				//Agregada llamada al actualizador de precios (modulo de promociones)
				dispose();
				if(jButton11.getText().equalsIgnoreCase("F1 - Eliminar descuento corporativo")){
					try{
						boolean descuentoEmpleado = false;
						if(venta!=null)
							descuentoEmpleado = venta.isAplicaDesctoEmpleado();
						//CR.meVenta.agregarPromocionCorporativa();
						if(venta!=null){
							venta.setDescuentoCorporativoAplicado(0.0);
							venta.actualizarPreciosDetalle(descuentoEmpleado);
						}
						else if(apartado!=null) {
							apartado.setDescuentoCorporativoAplicado(0.0);
							apartado.actualizarPreciosDetallePromociones();
						}
					} catch (ConexionExcepcion e1) {
						logger.error("agregarCupones()", e1);
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (ExcepcionCr e1) {
						logger.error("agregarCupones()", e1);
						MensajesVentanas.mensajeError(e1.getMensaje());
					}
				} else {
					try{
						if(CR.meVenta.getVenta()!=null){
							CR.meVenta.agregarPromocionCorporativa();
						} else if(CR.meServ.getApartado()!=null){
							CR.meServ.agregarPromocionCorporativa();
						}
						VentanaCorporativas vc = new VentanaCorporativas();
						MensajesVentanas.centrarVentanaDialogo(vc);
					} catch (ConexionExcepcion e1) {
						logger.error("agregarCupones()", e1);
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (ExcepcionCr e1) {
						logger.error("agregarCupones()", e1);
						MensajesVentanas.mensajeError(e1.getMensaje());
					}
				}
				break;
			case 12:
				//Agregada llamada al actualizador de precios (modulo de promociones)
				dispose();
				agregarCupones();
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
	private javax.swing.JButton getJButton11() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton11() - start");
		}
		this.jButton11 = new JHighlightButton();
        this.jButton11.setBackground(new java.awt.Color(226, 226, 222));
        this.jButton11.setActionCommand("Corporativas");
                
        if((venta==null && apartado==null || (venta!=null && venta.getDescuentoCorporativoAplicado()==0.0)) ||
        		(apartado!=null && apartado.getDescuentoCorporativoAplicado()==0)) {
        	this.jButton11.setText("F1 - Promociones Corporativas");
        } else {
        	this.jButton11.setText("F1 - Eliminar descuento corporativo");
        } 
        
        this.jButton11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        if (venta!=null && PromocionExtBD.getPromocionCorporativa().size()!=0 )
        	this.jButton11.setEnabled(true);
        else if (apartado!=null && PromocionExtBD.getPromocionCorporativa().size()!=0  && apartado.getDetallesServicio().size()!=0)
        	this.jButton11.setEnabled(true);
        else {
        //se agregó el objeto jButton11 a la sentencia siguiente.
        //16-04-2012 jperez
        	this.jButton11.setEnabled(false);
        }
        
        
       /* if(PromocionExtBD.getPromocionCorporativa().size()==0 || venta== null) 
        	this.jButton11.setEnabled(false);
        else this.jButton11.setEnabled(true);*/
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
		this.jButton12 = new JHighlightButton();
        this.jButton12.setBackground(new java.awt.Color(226, 226, 222));
        this.jButton12.setActionCommand("cupones");
        this.jButton12.setText("F2 - Cupones de descuento");
        this.jButton12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        if(venta!=null && PromocionExt.getCuponesActivos().size()!=0)
        	this.jButton12.setEnabled(true);
        else if(apartado!=null && apartado.getDetallesServicio().size()!=0 && PromocionExt.getCuponesActivos().size()!=0)
        	this.jButton12.setEnabled(true);
        else
        	this.jButton12.setEnabled(false);
        /*if(PromocionExt.getCuponesActivos().size()==0 || CR.meVenta.getVenta()== null) 
        	this.jButton12.setEnabled(false);
        else this.jButton12.setEnabled(true);*/
        return this.jButton12;
	}
			
	


	private void agregarListeners() {
		if (logger.isDebugEnabled()) {
			logger.debug("agregarListeners() - start");
		}


		jButton9.addKeyListener(this);
		jButton9.addMouseListener(this);
		
		
		jButton11.addKeyListener(this);
		jButton11.addMouseListener(this);
		
		jButton12.addKeyListener(this);
		jButton12.addMouseListener(this);
		
		if (logger.isDebugEnabled()) {
			logger.debug("agregarListeners() - end");
		}
	}	
	
	private void eliminarListeners() {
		if (logger.isDebugEnabled()) {
			logger.debug("eliminarListeners() - start");
		}

		jButton9.removeKeyListener(this);
		jButton9.removeMouseListener(this);

		
		jButton11.removeKeyListener(this);
		jButton11.removeMouseListener(this);
		
		jButton12.removeKeyListener(this);
		jButton12.removeMouseListener(this);
		
		if (logger.isDebugEnabled()) {
			logger.debug("eliminarListeners() - end");
		}
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
	
	public void agregarCupones(){
    	try{
    		if(CR.meVenta.getVenta()!=null){
    			CR.meVenta.agregarCuponesDescuento();
    		} else if(CR.meServ.getApartado()!=null){
    			CR.meServ.agregarCuponesDescuento();
    		}
    		VentanaCuponDescuento cupones = new VentanaCuponDescuento();
    		MensajesVentanas.centrarVentanaDialogo(cupones);
    	} catch (ConexionExcepcion e1) {
			logger.error("agregarCupones()", e1);
			MensajesVentanas.mensajeError(e1.getMensaje());
		} catch (ExcepcionCr e1) {
			logger.error("agregarCupones()", e1);
			MensajesVentanas.mensajeError(e1.getMensaje());
		}
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"  @jve:decl-index=0:visual-constraint="10,10"