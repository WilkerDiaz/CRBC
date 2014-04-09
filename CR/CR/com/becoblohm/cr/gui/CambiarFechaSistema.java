/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : LoginDeUsuario.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 13/02/2004 02:44 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.0.1
 * Fecha       : 01/07/2004 11:03 AM
 * Analista    : Programador3 -Alexis Guédez López  
 * Descripción : - Deshabilitado el cierre de la ventana a través de los comandos 
 * 				de ventana.
 * 				 - Agregado el indicador para deshabilitar redimensionar la ventana.
 * 				 - Modificada temporalmente condición de desactivación del botón 
 * 				para salir del sistema.
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JSpinner;
import javax.swing.KeyStroke;
import javax.swing.SpinnerDateModel;

/**
 * Descripción:
 * 
 */
public class CambiarFechaSistema extends JDialog 
implements ComponentListener, ActionListener, KeyListener {
	public Vector<Object> valores = new Vector<Object>();
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JLabel jLabel3 = null;
	private javax.swing.JLabel jLabel4 = null;
	private javax.swing.JPanel jPanel4 = null;
	private JSpinner selectorFecha = null;
	private Date fechaTienda = null;
	private Date fechaCaja = null;
	private Date fechaCierre = null;
	private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");;
	@SuppressWarnings("unused")
	private boolean limitarFecha = false;
	private Date fechaLimite = null;
	
	/**
	 * This is the default constructor
	 */
	public CambiarFechaSistema(Date fechaTienda, Date fechaCaja, Date fechaCierre) {
		super();
		Calendar time = GregorianCalendar.getInstance();
		time.add(Calendar.DATE, 2);
		this.fechaTienda = fechaTienda;
		this.fechaCaja = fechaCaja;
		this.fechaCierre = fechaCierre;
		this.limitarFecha = false;
		this.fechaLimite = time.getTime();
		initialize();
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				cargarDatos();
			}
		});
	}
	/**
	 * This is the default constructor
	 */
	public CambiarFechaSistema(Date fechaTienda, Date fechaCaja, Date fechaCierre, boolean limitarFecha, Date fechaLimite) {
		super();
		this.fechaTienda = fechaTienda;
		this.fechaCaja = fechaCaja;
		this.fechaCierre = fechaCierre;
		this.limitarFecha = limitarFecha;
		this.fechaLimite = fechaLimite;
		initialize();
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				cargarDatos();
			}
		});
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		java.awt.GridLayout layGridLayout1 = new java.awt.GridLayout();
		layGridLayout1.setRows(3);
		this.setContentPane(getJContentPane());
		this.getContentPane().setLayout(layGridLayout1);
		this.setSize(360, 197);
		this.setTitle("Inicio del Sistema");
		this.setBackground(new java.awt.Color(226,226,222));
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setModal(true);
		this.setResizable(false);
		getRootPane().setDefaultButton(jButton);
		this.addComponentListener(this);
	}
	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel() {
		if(jPanel == null) {
			jPanel = new javax.swing.JPanel();
			java.awt.GridLayout layGridLayout3 = new java.awt.GridLayout();
			layGridLayout3.setRows(1);
			jPanel.setLayout(layGridLayout3);
			jPanel.add(getJLabel1(), null);
			jPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,4,0,0));
			jPanel.setBackground(new java.awt.Color(69,107,127));
		}
		return jPanel;
	}
	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel1() {
		if(jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setText("Configuración del Sistema - Diferencia en fecha");
			jLabel1.setForeground(java.awt.Color.white);
			jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix48x48/fecha-hora.png")));
		}
		return jLabel1;
	}
	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		if(jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setText(" Cambiar Fecha/Hora:");
			jLabel.setPreferredSize(new java.awt.Dimension(20,16));
		}
		return jLabel;
	}
	/**
	 * Método getSelectorFecha
	 * 
	 * @return JSpinner
	 */
	private JSpinner getSelectorFecha(){
		if(selectorFecha == null){
			Date today, diaMenor, diaMayor;
			today = new Date();
			diaMenor = new Date(fechaCierre.getTime());
			diaMayor = new Date(fechaLimite.getTime());
			Calendar limite = Calendar.getInstance();
			limite.setTime(diaMayor);
			limite.add(Calendar.MINUTE, 1);
			selectorFecha = new JSpinner(new SpinnerDateModel(today, 
				diaMenor, limite.getTime(), Calendar.MONTH));
			JSpinner.DateEditor de = new JSpinner.DateEditor(selectorFecha, "dd/MM/yyyy HH:mm");
			de.getTextField().getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "ok");
			de.getTextField().getActionMap().put("ok", new OKAction());
			de.getTextField().getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "cancel");
			de.getTextField().getActionMap().put("cancel", new CancelAction());			
			selectorFecha.setEditor(de);
		}
		return selectorFecha;
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
			layFlowLayout1.setAlignment(java.awt.FlowLayout.RIGHT);
			layFlowLayout1.setVgap(10);
			jPanel2.setLayout(layFlowLayout1);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.setBackground(new java.awt.Color(226,226,222));
		}
		return jPanel2;
	}
	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton() {
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
			jButton.setMnemonic(java.awt.event.KeyEvent.VK_ENTER);

		}
		return jButton;
	}
	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton1() {
		if(jButton1 == null) {
			jButton1 = new JHighlightButton();
			jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
			jButton1.setText("Cancelar");
			jButton1.addActionListener(this);
			jButton1.addKeyListener(this);
			jButton1.setBackground(new java.awt.Color(226,226,222));
			jButton1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jButton1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
			jButton1.setMnemonic(java.awt.event.KeyEvent.VK_ESCAPE);
		}
		return jButton1;
	}
	public void actionPerformed(ActionEvent e){
		JButton btnComando = (JButton)e.getSource();
		if (btnComando.getText().equals("Aceptar"))
		{
			if (cargarDatos()) {
				this.dispose();
			} 
		}
		else if (btnComando.getText().equals("Cancelar")){
			this.dispose();
		}
	}

	public void keyPressed(KeyEvent e){
		try{
			JButton boton = (JButton)e.getSource();
			if ((boton.getText().equals("Aceptar"))&&(e.getKeyCode() == KeyEvent.VK_ENTER)){
				if (cargarDatos()) {
					this.dispose();
				} 
			}
			else if ((boton.getText().equals("Cancelar"))&&(e.getKeyCode() == KeyEvent.VK_ENTER)){
				this.dispose();
			}
		} catch(Exception ex){}
		
		try{
			 if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
				this.dispose();
			 }
		} catch(Exception ex){}
	}

	public void keyReleased(KeyEvent e){
	}

	public void keyTyped(KeyEvent e){
	}
	
	/**
	 * Returns the valores.
	 * @return Vector
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Object> getValores() {
		return valores;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Se parametrizó el tipo de dato contenido en los 'Vector' y se comentó código muerto
	* Fecha: agosto 2011
	*/
	boolean cargarDatos(){
		valores = new Vector<Object>();
		Timestamp fecha = new Timestamp(((Date)this.selectorFecha.getValue()).getTime());
		if (fecha != null){
			valores.addElement(fecha);
			valores.addElement(Boolean.TRUE);
		}
		//else return false;
		return true;
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanel4(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanel2(), java.awt.BorderLayout.SOUTH);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jLabel3
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel3() {
		if(jLabel3 == null) {
			jLabel3 = new javax.swing.JLabel();
			jLabel3.setText(" Fecha Tienda: "+formatoFecha.format(fechaTienda));
			jLabel3.setMinimumSize(new java.awt.Dimension(84,20));
			jLabel3.setPreferredSize(new java.awt.Dimension(84,20));
		}
		return jLabel3;
	}
	/**
	 * This method initializes jLabel4
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel4() {
		if(jLabel4 == null) {
			jLabel4 = new javax.swing.JLabel();
			jLabel4.setText(" Fecha Caja: "+formatoFecha.format(fechaCaja));
			jLabel4.setPreferredSize(new java.awt.Dimension(71,20));
			jLabel4.setMinimumSize(new java.awt.Dimension(71,20));
		}
		return jLabel4;
	}
	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel4() {
		if(jPanel4 == null) {
			jPanel4 = new javax.swing.JPanel();
			java.awt.GridLayout layGridLayout31 = new java.awt.GridLayout();
			layGridLayout31.setRows(2);
			layGridLayout31.setColumns(2);
			layGridLayout31.setVgap(5);
			layGridLayout31.setHgap(0);
			jPanel4.setLayout(layGridLayout31);
			jPanel4.add(getJLabel3(), null);
			jPanel4.add(getJLabel4(), null);
			jPanel4.add(getJLabel(), null);
			jPanel4.add(getSelectorFecha(), null);
			jPanel4.setMinimumSize(new java.awt.Dimension(168,20));
			jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel4.setPreferredSize(new java.awt.Dimension(170,60));
			jPanel4.setBackground(new java.awt.Color(226,226,222));
		}
		return jPanel4;
	}
	
	class OKAction extends AbstractAction {
		public OKAction() {
			super("ok");
		}
		
		public void actionPerformed(ActionEvent e) {
			jButton.doClick();
		}
	}
	
	class CancelAction extends AbstractAction {
		public CancelAction() {
			super("cancel");
		}
		
		public void actionPerformed(ActionEvent e) {
			jButton1.doClick();
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
	
}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"

