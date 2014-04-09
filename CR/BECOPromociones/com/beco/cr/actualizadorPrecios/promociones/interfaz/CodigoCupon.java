package com.beco.cr.actualizadorPrecios.promociones.interfaz;


/**
 * @author aavila
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.EventListener;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.beco.cr.actualizadorPrecios.UtilesActualizadorPrecios;
import com.beco.cr.actualizadorPrecios.promociones.PromocionExt;

/**
 * Descripción:
 * 
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
* Se comentó variable no usada
* Fecha: agosto 2011
*/
public class CodigoCupon extends JDialog implements ComponentListener, KeyListener, MouseListener, EventListener, DocumentListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CodigoCupon.class);  //  @jve:decl-index=0:

	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JLabel jLabel1 = null;
	private String codigo;
	//Vector donaciones = new Vector();
	Vector<Vector<Object>> cuponesValidos;
	PromocionExt p;
	boolean cuponValido = true;
	
	private javax.swing.JPasswordField jTextField = new javax.swing.JPasswordField();
	
	
	public CodigoCupon(PromocionExt p, Vector<Vector<Object>> cuponesValidos) {
		
		super(MensajesVentanas.ventanaActiva);
	
		initialize();		
		this.cuponesValidos = cuponesValidos;
		this.p = p;
		jTextField.setPreferredSize(new java.awt.Dimension(150,20));
				
		jButton.addKeyListener(this);
		jButton.addMouseListener(this);
				
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);
		
		jTextField.addKeyListener(this);
		CR.inputEscaner.getDocument().addDocumentListener(this);
	}
	
	private void initialize() {
		if (logger.isDebugEnabled()) logger.debug("initialize() - start");
		this.setTitle("Código del cupón");
		this.setSize(360, 190);
		this.setContentPane(getJContentPane());
		this.setUndecorated(true);
		this.setModal(true);
		this.setBackground(new java.awt.Color(226,226,222));
		this.addComponentListener(this);	
		if (logger.isDebugEnabled()) logger.debug("initialize() - end");
	}
	
	private javax.swing.JPanel getJContentPane() {
		if (logger.isDebugEnabled()) logger.debug("getJContentPane() - start");
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new FlowLayout());
			jContentPane.add(getJPanel3(), null);
			jContentPane.add(getJPanel2(), null);
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.RAISED));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
		}
		if (logger.isDebugEnabled()) logger.debug("getJContentPane() - end");
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
		CR.inputEscaner.getDocument().removeDocumentListener(this);
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}

		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			cuponValido = false;
			dispose();
		} else if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if (e.getSource().equals(this.getJTextField())){
				this.getJButton1().requestFocus();
			}else if (e.getSource().equals(this.getJButton1())){
				try{
					UtilesActualizadorPrecios.validarCupon(this.p, cuponesValidos, this.getJTextField().getText());
					this.codigo = this.getJTextField().getText();
					dispose();
				} catch(ExcepcionCr regDon){
					cuponValido = false;
					MensajesVentanas.mensajeError(regDon.getMensaje());
					dispose();
				} catch(Exception regDon){
					cuponValido = false;
					dispose();
				}
			}else if (e.getSource().equals(this.getJButton())){
				this.codigo=null;
				cuponValido = false;
				dispose();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_TAB){
			
		}
		jTextField.addKeyListener(this);
		jButton.addKeyListener(this);
		jButton.addMouseListener(this);
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);
		CR.inputEscaner.getDocument().addDocumentListener(this);

		if (logger.isDebugEnabled()) logger.debug("keyPressed(KeyEvent) - end");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyReleased(KeyEvent) - start");
		}
		jTextField.removeKeyListener(this);
		CR.inputEscaner.getDocument().removeDocumentListener(this);

		if (e.getSource().equals(getJTextField())) {
			
		}
		jTextField.addKeyListener(this);
		CR.inputEscaner.getDocument().addDocumentListener(this);

		if (logger.isDebugEnabled()) {
			logger.debug("keyReleased(KeyEvent) - end");
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
		if (logger.isDebugEnabled()) logger.debug("keyTyped(KeyEvent) - start");
		//falta lo de la tecla enter
		if (logger.isDebugEnabled()) logger.debug("keyTyped(KeyEvent) - end");	
	}

	public void mouseReleased(MouseEvent me1) {
		if (logger.isDebugEnabled()) logger.debug("mouseClicked(MouseEvent) - start");
		
		if (me1.getSource().equals(this.getJButton1())){
			try{
				UtilesActualizadorPrecios.validarCupon(this.p, cuponesValidos, this.getJTextField().getText());
				this.codigo = this.getJTextField().getText(); 
				dispose();
			} catch(ExcepcionCr regDon){
				cuponValido = false;
				MensajesVentanas.mensajeError(regDon.getMensaje());
				dispose();
			} catch(Exception regDon){
				regDon.printStackTrace();
				cuponValido = false;
				dispose();
			}
		}else if (me1.getSource().equals(this.getJButton())){
			dispose();
			this.codigo=null;
			cuponValido = false;
		}
		if (logger.isDebugEnabled()) logger.debug("mouseClicked(MouseEvent) - end");
	}
	public void mouseEntered(MouseEvent e) {
		if (logger.isDebugEnabled()) logger.debug("mouseEntered(MouseEvent) - start");
		if (logger.isDebugEnabled()) logger.debug("mouseEntered(MouseEvent) - end");
	}

	public void mouseExited(MouseEvent e) {
		if (logger.isDebugEnabled()) logger.debug("mouseExited(MouseEvent) - start");
		if (logger.isDebugEnabled()) logger.debug("mouseExited(MouseEvent) - end");
	}
	public void mousePressed(MouseEvent e) {
		if (logger.isDebugEnabled()) logger.debug("mousePressed(MouseEvent) - start");
		if (logger.isDebugEnabled()) logger.debug("mousePressed(MouseEvent) - end");
	}
	
	public void mouseClicked(MouseEvent me1) {
		if (logger.isDebugEnabled()) logger.debug("mouseReleased(MouseEvent) - start");
		if (logger.isDebugEnabled()) logger.debug("mouseReleased(MouseEvent) - end");
	}
	
	private javax.swing.JPanel getJPanel() {
		if (logger.isDebugEnabled()) logger.debug("getJPanel() - start");
		if(jPanel == null) {
			jPanel = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout1.setHgap(1);
			layFlowLayout1.setVgap(1);
			jPanel.setLayout(layFlowLayout1);
			layFlowLayout1.setAlignment(java.awt.FlowLayout.CENTER);
			jPanel.setPreferredSize(new java.awt.Dimension(315,47));
			jPanel.setBackground(new java.awt.Color(242,242,238));
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Introduzca el código del cupón", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
			jPanel.add(getJTextField(), null);
		}
		if (logger.isDebugEnabled()) logger.debug("getJPanel() - end");
		return jPanel;
	}


	private javax.swing.JPanel getJPanel2() {
		if (logger.isDebugEnabled()) logger.debug("getJPanel2() - start");
		if(jPanel2 == null) {
			jPanel2 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout11 = new java.awt.FlowLayout();
			layFlowLayout11.setAlignment(java.awt.FlowLayout.RIGHT);
			layFlowLayout11.setHgap(0);
			layFlowLayout11.setVgap(0);
			jPanel2.setLayout(layFlowLayout11);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(getJButton(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(320,30));
			jPanel2.setBackground(new java.awt.Color(226,226,222));
		}
		if (logger.isDebugEnabled()) logger.debug("getJPanel2() - end");
		return jPanel2;
	}

	private javax.swing.JButton getJButton() {
		if (logger.isDebugEnabled()) logger.debug("getJButton() - start");
		if(jButton == null) {
			jButton = new JHighlightButton();
			jButton.setText("Cancelar");
			jButton.setBackground(new java.awt.Color(226,226,222));
			jButton.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
		}
		if (logger.isDebugEnabled()) logger.debug("getJButton() - end");
		return jButton;
	}


	private javax.swing.JButton getJButton1() {
		if (logger.isDebugEnabled()) logger.debug("getJButton1() - start");
		if(jButton1 == null) {
			jButton1 = new JHighlightButton();
			jButton1.setText("Aceptar");
			jButton1.setBackground(new java.awt.Color(226,226,222));
			jButton1.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/add2.png")));
			//jButton1.addMouseListener(this);
			jButton1.setEnabled(true);
		}
		if (logger.isDebugEnabled()) logger.debug("getJButton1() - end");
		return jButton1;
	}

	private javax.swing.JPanel getJPanel3() {
		if (logger.isDebugEnabled()) logger.debug("getJPanel3() - start");
		if(jPanel3 == null) {
			jPanel3 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout12 = new java.awt.FlowLayout();
			layFlowLayout12.setHgap(0);
			layFlowLayout12.setVgap(0);
			jPanel3.setLayout(layFlowLayout12);
			jPanel3.add(getJPanel4(), null);
			jPanel3.add(getJPanel(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(320,100));
			jPanel3.setBackground(new java.awt.Color(242,242,238));
			jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
		}
		if (logger.isDebugEnabled()) logger.debug("getJPanel3() - end");
		return jPanel3;
	}

	private javax.swing.JPanel getJPanel4() {
		if (logger.isDebugEnabled()) logger.debug("getJPanel4() - start");
		if(jPanel4 == null) {
			jPanel4 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
			layFlowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout2.setVgap(5);
			layFlowLayout2.setHgap(5);
			jPanel4.setLayout(layFlowLayout2);
			jPanel4.add(getJLabel1(), null);
			jPanel4.setPreferredSize(new java.awt.Dimension(320,40));
			jPanel4.setBackground(new java.awt.Color(69,107,127));
		}
		if (logger.isDebugEnabled()) logger.debug("getJPanel4() - end");
		return jPanel4;
	}

	private javax.swing.JLabel getJLabel1() {
		if (logger.isDebugEnabled()) logger.debug("getJLabel1() - start");
		if(jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setText("Código del cupón");
			jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel1.setForeground(java.awt.Color.white);
			jLabel1.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/money.png")));
		}
		if (logger.isDebugEnabled()) logger.debug("getJLabel1() - end");
		return jLabel1;
	}
	
	public void componentHidden(ComponentEvent e) {}

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

	public void componentResized(ComponentEvent e) {}
	public void componentShown(ComponentEvent e) {}



	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JPasswordField getJTextField() {
		if(jTextField == null) {
			jTextField.setPreferredSize(new java.awt.Dimension(90,20));
			//jTextField.addActionListener()
		}
		return jTextField;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public boolean isCuponValido() {
		return cuponValido;
	}

	public void setCuponValido(boolean cuponValido) {
		this.cuponValido = cuponValido;
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Apéndice de método generado automáticamente
		
	}

	public void changedUpdate(DocumentEvent e) {
		// TODO Apéndice de método generado automáticamente
		
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

				String codigo = CR.inputEscaner.getText().trim();
				if (codigo.length() > 12) {
					codigo = codigo.substring(0, 12);
				}
				jTextField.setText(codigo);
				if (logger.isDebugEnabled()) {
					logger.debug("run() - end");
				}
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("insertUpdate(DocumentEvent) - end");
		}
	}

	public void removeUpdate(DocumentEvent e) {
		// TODO Apéndice de método generado automáticamente
		
	}
	
	


}
