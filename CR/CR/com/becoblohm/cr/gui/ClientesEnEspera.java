/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : ClientesEnEspera.java
 * Creado por : Victor Medina <linux@epa.com.ve>
 * Creado en  : Mar 8, 2004 - 1:41:27 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1
 * Fecha       : Mar 8, 2004
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

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarventa.Venta;
import com.becoblohm.cr.utiles.MensajesVentanas;
/**
 * @author vmedina
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ClientesEnEspera extends JDialog implements ComponentListener, KeyListener, MouseListener, EventListener, DocumentListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ClientesEnEspera.class);

	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JTextField jTextField = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JButton jButton2 = null;
	/**
	 * This is the default constructor
	 */
	public ClientesEnEspera() {
		super(MensajesVentanas.ventanaActiva);
		initialize();
		
		jTextField.addKeyListener(this);
		
		jButton.addKeyListener(this);
		jButton.addMouseListener(this);
		
		jButton1.addMouseListener(this);
		jButton1.addKeyListener(this);
		
		jButton2.addMouseListener(this);
		jButton2.addKeyListener(this);
		
		CR.inputEscaner.getDocument().addDocumentListener(this);
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

		this.setTitle("Cliente en Espera");
		this.setSize(370, 196);
		this.setContentPane(getJContentPane());
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
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
			layFlowLayout2.setAlignment(java.awt.FlowLayout.CENTER);
			jContentPane.setLayout(layFlowLayout2);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJPanel3(), null);
			jContentPane.setBackground(new java.awt.Color(226,226,222));
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.RAISED));
			jContentPane.setPreferredSize(new java.awt.Dimension(16,17));
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

		//Mapeo de ENTER
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(e.getSource().equals(jTextField)){
				jTextField.transferFocus();
			}
			
			else if(e.getSource().equals(jButton1)){
				colocarFactura();
				dispose();
				try {
					finalize();
				} catch (Throwable e1) {
					logger.error("keyPressed(KeyEvent)", e1);
				}
			}
			
			else if(e.getSource().equals(jButton2)){
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				dispose();
				try {
					finalize();
				} catch (Throwable e1) {
					logger.error("keyPressed(KeyEvent)", e1);
				}
			}

			else if(e.getSource().equals(jButton)){
				recuperarFactura();
				dispose();
				try {
					finalize();
				} catch (Throwable e1) {
					logger.error("keyPressed(KeyEvent)", e1);
				}
			}
		}
		
		//Mapeo de ESCAPE
		else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			dispose();
			try {
				finalize();
			} catch (Throwable e1) {
				logger.error("keyPressed(KeyEvent)", e1);
			}
		}
		
		
		//Mapeo de ESPACE
		else if(e.getKeyCode() == KeyEvent.VK_SPACE){
			if(e.getSource().equals(jButton)){
				colocarFactura();
				dispose();
				try {
					finalize();
				} catch (Throwable e1) {
					logger.error("keyPressed(KeyEvent)", e1);
				}
			}
			
			else if(e.getSource().equals(jButton1)){
				recuperarFactura();
				dispose();
				try {
					finalize();
				} catch (Throwable e1) {
					logger.error("keyPressed(KeyEvent)", e1);
				}
			}
			
			else if(e.getSource().equals(jButton2)){
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				dispose();
				try {
					finalize();
				} catch (Throwable e1) {
					logger.error("keyPressed(KeyEvent)", e1);
				}
			}
		}

		//Mapeo las teclas de Movimiento
		else if((e.getKeyCode() == KeyEvent.VK_RIGHT)||(e.getKeyCode() == KeyEvent.VK_LEFT)){
			this.getFocusOwner().transferFocus();
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
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - start");
		}

		if(e.getSource().equals(jButton)){
			recuperarFactura();
			dispose();
			try {
				finalize();
			} catch (Throwable e1) {
				logger.error("mouseClicked(MouseEvent)", e1);
			}
		}
		
		else if(e.getSource().equals(jButton1)){
			colocarFactura();
			dispose();
			try {
				finalize();
			} catch (Throwable e1) {
				logger.error("mouseClicked(MouseEvent)", e1);
			}
		}
		
		else if(e.getSource().equals(jButton2)){
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			dispose();
			try {
				finalize();
			} catch (Throwable e1) {
				logger.error("keyPressed(KeyEvent)", e1);
			}
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
			java.awt.FlowLayout layFlowLayout3 = new java.awt.FlowLayout();
			layFlowLayout3.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout3.setHgap(0);
			layFlowLayout3.setVgap(0);
			jPanel.setLayout(layFlowLayout3);
			jPanel.add(getJPanel1(), null);
			jPanel.add(getJPanel2(), null);
			jPanel.setPreferredSize(new java.awt.Dimension(350,110));
			jPanel.setBackground(new java.awt.Color(242,242,238));
			jPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
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
			java.awt.FlowLayout layFlowLayout4 = new java.awt.FlowLayout();
			layFlowLayout4.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout4.setHgap(5);
			layFlowLayout4.setVgap(5);
			jPanel1.setLayout(layFlowLayout4);
			jPanel1.add(getJLabel(), null);
			jPanel1.setPreferredSize(new java.awt.Dimension(348,40));
			jPanel1.setBackground(new java.awt.Color(69,107,127));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel1() - end");
		}
		return jPanel1;
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
			jLabel.setText("Cliente en Espera");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel.setForeground(java.awt.Color.white);
			jLabel.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/user1_time.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel() - end");
		}
		return jLabel;
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
			java.awt.FlowLayout layFlowLayout5 = new java.awt.FlowLayout();
			layFlowLayout5.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout5.setVgap(0);
			layFlowLayout5.setHgap(5);
			jPanel2.setLayout(layFlowLayout5);
			jPanel2.add(getJTextField(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(340,50));
			jPanel2.setBackground(new java.awt.Color(242,242,238));
			jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "No. de identificación", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel2() - end");
		}
		return jPanel2;
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
			jTextField.setPreferredSize(new java.awt.Dimension(250,19));
			jTextField.setDocument(new MyTextFieldDocument(Sesion.LONG_FACT_ESPERA));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField() - end");
		}
		return jTextField;
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
			java.awt.FlowLayout layFlowLayout6 = new java.awt.FlowLayout();
			layFlowLayout6.setAlignment(java.awt.FlowLayout.RIGHT);
			layFlowLayout6.setVgap(0);
			layFlowLayout6.setHgap(0);
			jPanel3.setLayout(layFlowLayout6);
			jPanel3.add(getJButton(), null);
			jPanel3.add(getJButton1(), null);
			jPanel3.add(getJButton2(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(350,35));
			jPanel3.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel3() - end");
		}
		return jPanel3;
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
			jButton.setBackground(new java.awt.Color(226,226,222));
			jButton.setText("Abrir");
			jButton.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/document_new.png")));
			if (!Sesion.getCaja().getEstado().equals(Sesion.INICIADA)) {
				jButton.setVisible(false);
			}
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
			jButton1.setBackground(new java.awt.Color(226,226,222));
			jButton1.setText("Guardar");
			jButton1.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/disk_blue_ok.png")));
			if (!Sesion.getCaja().getEstado().equals(Sesion.FACTURANDO)) {
				jButton1.setVisible(false);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton1() - end");
		}
		return jButton1;
	}

	/**
	 * Método colocarFactura
	 */
	private void colocarFactura(){
		if (logger.isDebugEnabled()) {
			logger.debug("colocarFactura() - start");
		}

		CR.inputEscaner.getDocument().removeDocumentListener(this);
		try {
			if(Venta.donacionesRegistradas.size()!=0){
				Venta.donacionesRegistradas.clear();
				MensajesVentanas.aviso("Debe volver a registrar las donaciones");
				CR.me.mostrarAviso("Eliminadas todas las donaciones de la venta", true);
			}
			Venta.regalosRegistrados.clear();
			Venta.promocionesRegistradas.clear();
			CR.me.getPromoMontoCantidad().clear();
			CR.meVenta.colocarFacturaEspera(getJTextField().getText());
			CR.me.repintarMenuPrincipal();
		} catch (ConexionExcepcion e1) {
			logger.error("colocarFactura()", e1);

			if (!Sesion.isCajaEnLinea()) {
				 MensajesVentanas.aviso("El sistema se encuentra operando Fuera de Línea");
			} else {
				 MensajesVentanas.mensajeError(e1.getMensaje());
			}
		} catch (ExcepcionCr e1) {
			logger.error("colocarFactura()", e1);

			MensajesVentanas.mensajeError(e1.getMensaje());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("colocarFactura() - end");
		}
	}	

	/**
	 * Método recuperarFactura
	 */
	private void recuperarFactura(){
		if (logger.isDebugEnabled()) {
			logger.debug("recuperarFactura() - start");
		}

		CR.inputEscaner.getDocument().removeDocumentListener(this);
		try {
			CR.meVenta.recuperarFacturaEspera(getJTextField().getText());
		} catch (ExcepcionCr ex) {
			logger.error("recuperarFactura()", ex);

			MensajesVentanas.mensajeError(ex.getMensaje());
		} catch (ConexionExcepcion ex) {
			logger.error("recuperarFactura()", ex);

			if (!Sesion.isCajaEnLinea()) {
				 MensajesVentanas.aviso("El sistema se encuentra operando Fuera de Línea");
			} else {
				 MensajesVentanas.mensajeError(ex.getMensaje());
			}
		}	

		if (logger.isDebugEnabled()) {
			logger.debug("recuperarFactura() - end");
		}
	}
	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 */
	public void changedUpdate(DocumentEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("changedUpdate(DocumentEvent) - start");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("changedUpdate(DocumentEvent) - end");
		}
	}
	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 */
	public void insertUpdate(DocumentEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("insertUpdate(DocumentEvent) - start");
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (logger.isDebugEnabled()) {
					logger.debug("run() - start");
				}

				jTextField.setText(CR.inputEscaner.getText());

				if (logger.isDebugEnabled()) {
					logger.debug("run() - end");
				}
			}
		});
		
		if (logger.isDebugEnabled()) {
			logger.debug("insertUpdate(DocumentEvent) - end");
		}
	}
	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
	 */
	public void removeUpdate(DocumentEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("removeUpdate(DocumentEvent) - start");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("removeUpdate(DocumentEvent) - end");
		}
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
			jButton2.setText("Cancelar");
			jButton2.setBackground(new java.awt.Color(226,226,222));
			jButton2.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton2() - end");
		}
		return jButton2;
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

class MyTextFieldDocument extends PlainDocument {

	private int longitud;

	public MyTextFieldDocument(int lon) {
		longitud = lon;
	}

	public void insertString(int offs, String str, AttributeSet a) 
	throws BadLocationException {

	if (getLength()<longitud)
		super.insertString(offs, str, a);
	}

}
