/**
 * =============================================================================
 * Proyecto   : cr
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : SolicitarCodDet.java
 * Creado por : Marcos Grillo
 * Creado en  : 14/04/2010 - 01:41:29
 *
 * (c) CENTROBECO, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versiónn     : 
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

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.utiles.MensajesVentanas;
/**
 * Solicita un código de producto, se puede utilizar el escáner.
 * @author Marcos Grillo
 *
 */
public class SolicitarCodDet extends JDialog implements ComponentListener, KeyListener
,ActionListener, DocumentListener {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(EjecutarConCantidad.class);  //  @jve:decl-index=0:

	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JTextField jTextField1 = null;
	private javax.swing.JPanel jPanel5 = null;
	private String codigo;  //  @jve:decl-index=0:
	private int opcion;

	
	/**
	 * Constructor. Coloca en JLabel1 un mensaje.
	 * @param mensaje String con el mensaje
	 * @param title String con el titulo de la ventana
	 */
	public SolicitarCodDet(String mensaje,String title) {
		super(MensajesVentanas.ventanaActiva);
		
		initialize();
		
		jTextField1.addKeyListener(this);

		jButton.addKeyListener(this);
		jButton.addActionListener(this);
		
		jButton1.addKeyListener(this);
		jButton1.addActionListener(this);
		
		this.getJLabel1().setText(mensaje);
		this.setTitle(title);
		CR.inputEscaner.getDocument().addDocumentListener(this);
	}
		
	/**
	 * Inicializa la ventana y la coloca con el formato de caja
	 * 
	 */
	private void initialize() {
		if (logger.isDebugEnabled())
			logger.debug("initialize(int) - start");

		this.setSize(430, 188);
		this.setContentPane(getJContentPane());
		this.setUndecorated(true);
		this.setModal(true);
		this.setBackground(new java.awt.Color(226,226,222));
		this.addComponentListener(this);
		if (logger.isDebugEnabled())
			logger.debug("initialize(int) - end");
	}
	
	/**
	 * Inicializa jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (logger.isDebugEnabled())
			logger.debug("getJContentPane() - start");

		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout14 = new java.awt.FlowLayout();
			layFlowLayout14.setHgap(0);
			layFlowLayout14.setVgap(0);
			jContentPane.setLayout(layFlowLayout14);
			jContentPane.add(getJPanel3(), null);
			jContentPane.add(getJPanel2(), null);
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.RAISED));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJContentPane() - end");
		return jContentPane;
	}
	
	
	/**
	 * Inicializa jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel2() {
		if (logger.isDebugEnabled())
			logger.debug("getJPanel2() - start");

		if(jPanel2 == null) {
			jPanel2 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout11 = new java.awt.FlowLayout();
			layFlowLayout11.setAlignment(java.awt.FlowLayout.RIGHT);
			layFlowLayout11.setHgap(0);
			layFlowLayout11.setVgap(3);
			jPanel2.setLayout(layFlowLayout11);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(getJButton(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(410,35));
			jPanel2.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel2() - end");
		return jPanel2;
	}

	/**
	 * Inicializa jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton() {
		if (logger.isDebugEnabled())
			logger.debug("getJButton() - start");

		if(jButton == null) {
			jButton = new JHighlightButton();
			jButton.setText("Cancelar");
			jButton.setBackground(new java.awt.Color(226,226,222));
			jButton.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJButton() - end");
		return jButton;
	}

	/**
	 * Inicializa jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton1() {
		if (logger.isDebugEnabled())
			logger.debug("getJButton1() - start");

		if(jButton1 == null) {
			jButton1 = new JHighlightButton();
			jButton1.setText("Aceptar");
			jButton1.setBackground(new java.awt.Color(226,226,222));
			jButton1.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJButton1() - end");
		return jButton1;
	}
	/**
	 * Inicializa jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel3() {
		if (logger.isDebugEnabled())
			logger.debug("getJPanel3() - start");

		if(jPanel3 == null) {
			jPanel3 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout12 = new java.awt.FlowLayout();
			layFlowLayout12.setHgap(0);
			layFlowLayout12.setVgap(0);
			jPanel3.setLayout(layFlowLayout12);
			jPanel3.add(getJPanel4(), null);
			jPanel3.add(getJPanel5(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(410,112));
			jPanel3.setBackground(new java.awt.Color(242,242,238));
			jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel3() - end");
		return jPanel3;
	}
	/**
	 * Inicializa jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel4() {
		if (logger.isDebugEnabled())
			logger.debug("getJPanel4() - start");

		if(jPanel4 == null) {
			jPanel4 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
			layFlowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout2.setVgap(5);
			layFlowLayout2.setHgap(5);
			jPanel4.setLayout(layFlowLayout2);
			jPanel4.add(getJLabel1(), null);
			jPanel4.setPreferredSize(new java.awt.Dimension(410,40));
			jPanel4.setBackground(new java.awt.Color(69,107,127));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel4() - end");
		return jPanel4;
	}
	/**
	 * Inicializa jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel1() {
		if (logger.isDebugEnabled())
			logger.debug("getJLabel1() - start");

		if(jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setText("");
			jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel1.setForeground(java.awt.Color.white);
			jLabel1.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/help2.png")));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJLabel1() - end");
		return jLabel1;
	}
	/**
	 * Inicializa jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel1() {
		if (logger.isDebugEnabled())
			logger.debug("getJPanel1() - start");

		if(jPanel1 == null) {
			jPanel1 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout13 = new java.awt.FlowLayout();
			layFlowLayout13.setHgap(1);
			layFlowLayout13.setVgap(1);
			layFlowLayout13.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel1.setLayout(layFlowLayout13);
			jPanel1.add(getJTextField1(), null);
			jPanel1.setPreferredSize(new java.awt.Dimension(395,53));
			jPanel1.setBackground(new java.awt.Color(242,242,238));
			
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Código", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel1() - end");
		return jPanel1;
	}
	/**
	 * Inicializa jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField1() {
		if (logger.isDebugEnabled())
			logger.debug("getJTextField1() - start");

		if(jTextField1 == null) {
			jTextField1 = new UpperCaseField();
			jTextField1.setPreferredSize(new java.awt.Dimension(200,20));
			jTextField1.setBackground(java.awt.Color.white);
		}

		if (logger.isDebugEnabled())
			logger.debug("getJTextField1() - end");
		return jTextField1;
	}
	/**
	 * Inicializa jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel5() {
		if (logger.isDebugEnabled())
			logger.debug("getJPanel5() - start");

		if(jPanel5 == null) {
			jPanel5 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout21 = new java.awt.FlowLayout();
			layFlowLayout21.setHgap(5);
			layFlowLayout21.setVgap(5);
			layFlowLayout21.setAlignment(java.awt.FlowLayout.CENTER);
			jPanel5.setLayout(layFlowLayout21);
			jPanel5.add(getJPanel1(), null);
			jPanel5.setPreferredSize(new java.awt.Dimension(395,60));
			jPanel5.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel5() - end");
		return jPanel5;
	}

	/**
	 * Maneja los eventos de teclado
	 * @param e Evento recibido
	 */
	public void keyPressed(KeyEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("keyPressed(KeyEvent) - start");
		if((e.getKeyCode() == KeyEvent.VK_ENTER)&&(e.getSource().equals(jButton1)||e.getSource().equals(jTextField1))){
			this.getJButton1().doClick();
		}else if((e.getKeyCode() == KeyEvent.VK_ENTER)&&(e.getSource().equals(jButton))){
			this.getJButton().doClick();
		}else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			this.getJButton().doClick();
		}

		if (logger.isDebugEnabled())
			logger.debug("keyPressed(KeyEvent) - end");
	}

	/**
	 * Maneja los eventos de teclado
	 * @param e Evento recibido
	 */
	public void keyReleased(KeyEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("keyReleased(KeyEvent) - start");


		if (logger.isDebugEnabled())
			logger.debug("keyReleased(KeyEvent) - end");
	}

	/**
	 * Maneja los eventos de teclado
	 * @param e Evento recibido
	 */
	public void keyTyped(KeyEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("keyTyped(KeyEvent) - start");
				
		if (logger.isDebugEnabled())
			logger.debug("keyTyped(KeyEvent) - end");
	}
	
	/**
	 * Método llamado al esconder la ventana
	 * @param e información del evento
	 */
	public void componentHidden(ComponentEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("componentHidden(ComponentEvent) - start");

		if (logger.isDebugEnabled())
			logger.debug("componentHidden(ComponentEvent) - end");
	}

	/**
	 * Método llamado al mover la ventana
	 * @param e información del evento
	 */
	public void componentMoved(ComponentEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("componentMoved(ComponentEvent) - start");
		
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
		
		if (logger.isDebugEnabled())
			logger.debug("componentMoved(ComponentEvent) - end");
	}


	/**
	 * Método llamado al redefinir los tamaños de la ventana
	 * @param e información del evento
	 */
	public void componentResized(ComponentEvent e) {
	
		if (logger.isDebugEnabled())
			logger.debug("componentResized(ComponentEvent) - start");
	
		if (logger.isDebugEnabled())
			logger.debug("componentResized(ComponentEvent) - end");
	}

	/**
	 * Método llamado al mostrar la ventana
	 * @param e información del evento
	 */
	public void componentShown(ComponentEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("componentShown(ComponentEvent) - start");
		
		if (logger.isDebugEnabled())
			logger.debug("componentShown(ComponentEvent) - end");
	}
	
	/**
	 * Método llamado al mostrar la ventana
	 * @param e información del evento
	 */
	public void changedUpdate(DocumentEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("changedUpdate(DocumentEvent) - start");
		
		if (logger.isDebugEnabled())
			logger.debug("changedUpdate(DocumentEvent) - end");
	}

	/**
	 * Método llamado al recibir una lectura del escáner
	 * @param e información del evento
	 */
	public void insertUpdate(DocumentEvent e){
		if (logger.isDebugEnabled())
			logger.debug("insertUpdate(DocumentEvent) - start");
		
		codigo = CR.inputEscaner.getText().trim();
		if(codigo.equals(""))
			codigo = jTextField1.getText();
		if(codigo.equals("")){
			MensajesVentanas.mensajeError("Debe introducir un código");
			return;
		}
		jTextField1.setText(codigo);
		opcion = 1;
		ejecutarAccion();
		if (logger.isDebugEnabled())
			logger.debug("insertUpdate(DocumentEvent) - end");
	}

	/**
	 * @param e información del evento
	 */
	public void removeUpdate(DocumentEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("removeUpdate(DocumentEvent) - start");
		
		if (logger.isDebugEnabled())
			logger.debug("removeUpdate(DocumentEvent) - end");
		
	}
	
	/**
	 * Método llamado al recibir un evento sobre un objeto con un action listener
	 * @param e información del evento
	 */
	public void actionPerformed(ActionEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("actionPerformed(ActionEvent) - start");
		
		Object o = e.getSource();
		if(o == jButton1){			
			jTextField1.setText("");
			MensajesVentanas.mensajeError("Debe ingresar el código mediante el uso del Escaner");
		}else if(o == jButton){
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			dispose();
		}
		
		if (logger.isDebugEnabled())
			logger.debug("actionPerformed(ActionEvent) - end");
	}
	
	
	private void ejecutarAccion(){
		if (logger.isDebugEnabled())
			logger.debug("ejecutarAccion() - start");
		switch(opcion){
		case 1:

			
			int tam = codigo.length();
			for(int i=0; i < tam;++i){
				if(!Character.isDigit(codigo.charAt(i))){
					MensajesVentanas.mensajeError("Código inválido");
					codigo = "";
					return;
				}
			}
			while(tam < Sesion.tamCodigoBeco+Sesion.tamConsecutivo){
				codigo = "0"+codigo;
				++tam;
			}
			System.out.println("CODIGO FINAL: "+codigo);
			this.getJButton().setEnabled(false);
			this.getJButton1().setEnabled(false);
			this.getJTextField1().setEnabled(false);
			try{
				CR.me.transferirProducto(codigo);
				MensajesVentanas.aviso("Producto " + codigo + " transferido satisfactoriamente");
			}catch(Exception e){
				MensajesVentanas.mensajeError("No se pudo sincronizar el producto: "+e.getMessage());
				e.printStackTrace();
				codigo = "";
				jTextField1.setText("");
			}
			
			this.getJButton().setEnabled(true);
			this.getJButton1().setEnabled(true);
			this.getJTextField1().setEnabled(true);
			
			codigo = "";
			jTextField1.setText("");
			break;
		}
		if (logger.isDebugEnabled())
			logger.debug("ejecutarAccion() - end");
	
	}
}
