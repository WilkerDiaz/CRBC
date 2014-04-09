/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : AnularProducto.java
 * Creado por : Gabriel Martinelli <gmartinelli@beco.com.ve>
 * Creado en  : Mar 29, 2004 - 3:16:30 PM
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
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;

import org.apache.log4j.Logger;

import com.becoblohm.cr.utiles.MensajesVentanas;
/**
 * @author vmedina
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class VentanaConfirmacion extends JDialog implements ComponentListener, KeyListener, MouseListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(VentanaConfirmacion.class);

	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JPanel jPanel5 = null;
	private int opcion = -1;
	private double anchoTexto = 0;
	private double altoTexto = 0;
    private char opcionDefault = '0';
    
	private javax.swing.JTextArea jTextArea = null;
	/**
	 * This is the default constructor
	 */
	public VentanaConfirmacion(String xMensaje) {
		this(xMensaje, null, null);
	}
	
	public VentanaConfirmacion(String xMensaje, String txtBtn0, String txtBtn1) {
		super(MensajesVentanas.ventanaActiva);
		this.getJTextArea().setText(xMensaje);
		altoTexto = jTextArea.getUI().getPreferredSize(this.jTextArea).getHeight();
		anchoTexto = jTextArea.getUI().getPreferredSize(this.jTextArea).getWidth();
		
		initialize();
		
		if (txtBtn0 != null) {
			jButton1.setText(txtBtn0);
		}
		
		if (txtBtn1 != null) {
			jButton.setText(txtBtn1);
		}
		
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);
		
		jButton.addKeyListener(this);
		jButton.addMouseListener(this);
		
		getJButton1().requestFocus();
		
	}
	
	public VentanaConfirmacion(String xMensaje, JFrame parent) {
		this(xMensaje, parent, null, null);
	}
	
	
	public VentanaConfirmacion(String xMensaje, JFrame parent, String txtBtn0, String txtBtn1) {
		super(parent);
		this.getJTextArea().setText(xMensaje);
		altoTexto = jTextArea.getUI().getPreferredSize(this.jTextArea).getHeight();
		anchoTexto = jTextArea.getUI().getPreferredSize(this.jTextArea).getWidth();
		
		initialize();

		if (txtBtn0 != null) {
			jButton1.setText(txtBtn0);
		}
		
		if (txtBtn1 != null) {
			jButton.setText(txtBtn1);
		}
		
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);
		
		jButton.addKeyListener(this);
		jButton.addMouseListener(this);
		
		getJButton1().requestFocus();
		
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	protected void initialize() {
		if (logger.isDebugEnabled()) {
			logger.debug("initialize() - start");
		}

		if ((anchoTexto+50) < 300) {
			this.setSize(300, (int) (altoTexto + 150));
		} else {
			this.setSize((int) (anchoTexto + 75), (int) (altoTexto + 150));
		}
		this.setResizable(false);
		this.setTitle("Confirmación");
		this.setContentPane(getJContentPane());
		this.setUndecorated(false);
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
			java.awt.FlowLayout layFlowLayout14 = new java.awt.FlowLayout();
			layFlowLayout14.setHgap(5);
			layFlowLayout14.setVgap(5);
			jContentPane.setLayout(layFlowLayout14);
			jContentPane.add(getJPanel3(), null);
			jContentPane.add(getJPanel2(), null);
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.RAISED));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
			jContentPane.setFocusable(false);
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

		if(e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_N){
			/*setOpcion(1); 
			dispose();*/
		}
		else if (e.getKeyCode() == KeyEvent.VK_S) {
			setOpcion(0); 
			dispose();
		}
		else if((e.getKeyCode() == KeyEvent.VK_RIGHT) || (e.getKeyCode() == KeyEvent.VK_LEFT)){
			if(e.getSource() == jButton1){
				jButton.requestFocus();
			}else if(e.getSource() == jButton){
				jButton1.requestFocus();
			}
		}
		else if((e.getKeyCode() == KeyEvent.VK_ENTER) || (e.getKeyCode() == KeyEvent.VK_SPACE)){
			if(e.getSource() == jButton1){
				setOpcion(0);
				dispose();
			}
			else if(e.getSource() == jButton){
				setOpcion(1);
				dispose(); 
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

		if(e.getSource() == jButton1){
			setOpcion(0);
			dispose();
		}
		else if(e.getSource() == jButton){
			setOpcion(1);
			dispose(); 
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
			jPanel2.setPreferredSize(new java.awt.Dimension(this.getWidth()-20,35));
			jPanel2.setBackground(new java.awt.Color(226,226,222));
			jPanel2.setFocusable(false);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel2() - end");
		}
		return jPanel2;
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
			jButton1.setText("Si");
			jButton1.setBackground(new java.awt.Color(226,226,222));
			jButton1.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
			jButton1.setMnemonic(java.awt.event.KeyEvent.VK_S);
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
			jPanel3.setPreferredSize(new java.awt.Dimension(this.getWidth()-20,this.getHeight()-80));
			jPanel3.add(getJPanel4(), null);
			jPanel3.add(getJPanel5(), null);
			jPanel3.setBackground(new java.awt.Color(242,242,238));
			jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
			jPanel3.setFocusable(false);
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
			jPanel4.setPreferredSize(new java.awt.Dimension((int)getJPanel3().getPreferredSize().getWidth()-2,40));
			jPanel4.setBackground(new java.awt.Color(69,107,127));
			jPanel4.setFocusable(false);
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
			jLabel1.setText("Confirmación");
			jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel1.setForeground(java.awt.Color.white);
			jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/help2.png")));
			jLabel1.setFocusable(false);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel1() - end");
		}
		return jLabel1;
	}
	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	protected javax.swing.JPanel getJPanel5() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel5() - start");
		}

		if(jPanel5 == null) {
			jPanel5 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout21 = new java.awt.FlowLayout();
			layFlowLayout21.setHgap(5);
			layFlowLayout21.setVgap(10);
			layFlowLayout21.setAlignment(java.awt.FlowLayout.CENTER);
			jPanel5.setLayout(layFlowLayout21);
			jPanel5.add(getJTextArea(), null);
			jPanel5.setPreferredSize(new java.awt.Dimension((int)jPanel3.getPreferredSize().getWidth()-5,(int)altoTexto+10));
			jPanel5.setBackground(new java.awt.Color(242,242,238));
			jPanel5.setFocusable(false);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel5() - end");
		}
		return jPanel5;
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
			jButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
			jButton.setText("No");
			jButton.setBackground(new java.awt.Color(226,226,222));
			jButton.setMnemonic(java.awt.event.KeyEvent.VK_N);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton() - end");
		}
		return jButton;
	}
	/**
	 * Método getOpcion
	 * 
	 * @return int
	 */
	public int getOpcion() {
		if (logger.isDebugEnabled()) {
			logger.debug("getOpcion() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getOpcion() - end");
		}
		return opcion;
	}

	/**
	 * Método setOpcion
	 * 
	 * @param i
	 */
	protected void setOpcion(int i) {
		if (logger.isDebugEnabled()) {
			logger.debug("setOpcion(int) - start");
		}

		opcion = i;

		if (logger.isDebugEnabled()) {
			logger.debug("setOpcion(int) - end");
		}
	}

	/**
	 * This method initializes jTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private javax.swing.JTextArea getJTextArea() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextArea() - start");
		}

		if(jTextArea == null) {
			jTextArea = new javax.swing.JTextArea();
			jTextArea.setBackground(new java.awt.Color(242,242,238));
			jTextArea.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			jTextArea.setEditable(false);
			jTextArea.setEnabled(true);
			jTextArea.setFocusable(false);
			jTextArea.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextArea() - end");
		}
		return jTextArea;
	}
	
	public void opcionPorDefecto(char opcion) {
		opcion = Character.toUpperCase(opcion);
		if (opcion == 'S' || opcion == 'N') {
			if (opcion == 'S') {
				jButton1.requestFocus();
			} else { 
				jButton.requestFocus();
			}
		} else {
			throw new IllegalArgumentException("Argumento inválido");
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
	
    public void setOpcionPorDefecto(char opcion) {
        this.opcionDefault = opcion;
        opcion = Character.toUpperCase(opcion);
        if (opcion == 'S' || opcion == 'N') {
            if (opcion == 'S') {
                this.getJButton1().requestFocus();
            } else {
                this.getJButton().requestFocus();
            }
        } else {
            throw new IllegalArgumentException("Argumento inválido");
        }
    }
    
    public int getOpcionDefault() {
        int opcionPorDefecto = this.opcion;
        if (this.opcionDefault == 'S') {
            opcionPorDefecto = 0;
        } else if (this.opcionDefault == 'N') {
            opcionPorDefecto = 1;
        }
        return opcionPorDefecto;
    }
	
}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"