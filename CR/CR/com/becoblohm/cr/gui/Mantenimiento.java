/*
 * Creado el 15-jul-06
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.becoblohm.cr.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EventListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstado;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarmantenimiento.Ciudad;
import com.becoblohm.cr.manejarmantenimiento.Estado;
import com.becoblohm.cr.manejarmantenimiento.MantenimientoECU;
import com.becoblohm.cr.manejarmantenimiento.Urbanizacion;
import com.becoblohm.cr.utiles.MensajesVentanas;

/**
 * @author yzambrano
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class Mantenimiento extends JDialog implements ComponentListener, KeyListener, MouseListener, EventListener, FocusListener, ActionListener{

	private static final Logger logger = Logger
				.getLogger(RegistroClientesNuevos.class);
	
	private javax.swing.JPanel jContentPane = null;
	
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JButton jButton2 = null;  //  @jve:visual-info  decl-index=0 visual-constraint="875,160"
	private javax.swing.JButton jButton3 = null;  //  @jve:visual-info  decl-index=0 visual-constraint="879,336"
	private javax.swing.JButton jButton4 = null;
	private javax.swing.JButton jButton5 = null;
	private javax.swing.JButton jButton6 = null;
	private javax.swing.JButton jButton7 = null;
	private javax.swing.JButton jButton8 = null;
	private javax.swing.JButton jButton9 = null;
	private javax.swing.JButton jButton10 = null;
	private javax.swing.JButton jButton11 = null;	
	private javax.swing.JPanel jPanel = null;  //  @jve:visual-info  decl-index=0 visual-constraint="502,8"
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JPanel jPanel3 = null;  //  @jve:visual-info  decl-index=0 visual-constraint="399,86"
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JPanel jPanel5 = null;
	private javax.swing.JPanel jPanel6 = null;
	private javax.swing.JPanel jPanel7 = null;
	private javax.swing.JPanel jPanel8 = null;
	private javax.swing.JPanel jPanel9 = null;
	private javax.swing.JPanel jPanel10 = null;
	private javax.swing.JRadioButton jRadioButton = null;
	private javax.swing.JRadioButton jRadioButton1 = null;
	private javax.swing.JRadioButton jRadioButton2 = null;
	
	private javax.swing.JComboBox jComboBox = null; //Selección
	private javax.swing.JComboBox jComboBox1 = null; //Estados
	private javax.swing.JComboBox jComboBox2 = null; //Ciudades
	private javax.swing.JComboBox jComboBox3 = null;  //  @jve:visual-info  decl-index=0 visual-constraint="991,153"Urbanizaciones
	private javax.swing.JComboBox jComboBox4 = null; //Estados
	private javax.swing.JComboBox jComboBox5 = null;  //  @jve:visual-info  decl-index=0 visual-constraint="730,383"Ciudades
	private javax.swing.JComboBox jComboBox6 = null; //Urbanizaciones
	private javax.swing.JComboBox jComboBox7 = null; //Selección
	private javax.swing.JComboBox jComboBox8 = null; //Estados
	private javax.swing.JComboBox jComboBox9 = null; //Ciudades
	private javax.swing.JComboBox jComboBox10 = null;
	
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JLabel jLabel1 = null; 
	private javax.swing.JLabel jLabel2 = null; 
	private javax.swing.JLabel jLabel3 = null;  //  @jve:visual-info  decl-index=0 visual-constraint="417,24" 
	private javax.swing.JLabel jLabel4 = null; 
	private javax.swing.JLabel jLabel5 = null;  //  @jve:visual-info  decl-index=0 visual-constraint="727,96"
	private javax.swing.JLabel jLabel6 = null;
	private javax.swing.JLabel jLabel7 = null; 
	private javax.swing.JLabel jLabel8 = null; 
	private javax.swing.JLabel jLabel9 = null; 
	private javax.swing.JLabel jLabel10 = null;
	private javax.swing.JLabel jLabel11 = null; 
	private javax.swing.JLabel jLabel12 = null;  //  @jve:visual-info  decl-index=0 visual-constraint="715,353" 
	private javax.swing.JLabel jLabel13 = null;  //  @jve:visual-info  decl-index=0 visual-constraint="717,318" 
	private javax.swing.JLabel jLabel14 = null;  //  @jve:visual-info  decl-index=0 visual-constraint="726,337" 
	private javax.swing.JLabel jLabel15 = null;
	private javax.swing.JLabel jLabel16 = null;
	private javax.swing.JLabel jLabel17 = null; 
	private javax.swing.JLabel jLabel18 = null; 
	private javax.swing.JLabel jLabel19 = null;
	private javax.swing.JLabel jLabel20 = null;
		
	private javax.swing.JTextField jTextField = null; 
	private javax.swing.JTextField jTextField1 = null; 
	private javax.swing.JTextField jTextField2 = null;  //  @jve:visual-info  decl-index=0 visual-constraint="716,159" 
	private javax.swing.JTextField jTextField3 = null; 
	private javax.swing.JTextField jTextField4 = null;  //  @jve:visual-info  decl-index=0 visual-constraint="739,274"
	private javax.swing.JTextField jTextField5 = null;  //  @jve:visual-info  decl-index=0 visual-constraint="975,117" 
	private javax.swing.JTextField jTextField6 = null; 
	private javax.swing.JTextField jTextField7 = null; 
	private javax.swing.JTextField jTextField8 = null; 
	private javax.swing.JTextField jTextField9 = null;	
	
	private boolean servicio;
	private MantenimientoECU mt = new MantenimientoECU();
	
	/**
	 * This is the default constructor
	 */
	public Mantenimiento(boolean caso) throws MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		super();
		servicio = caso;
		initialize();
		
		jRadioButton.addKeyListener(this);
		jRadioButton.addMouseListener(this);
		jRadioButton.addActionListener(this);
		
		jRadioButton1.addKeyListener(this);
		jRadioButton1.addMouseListener(this);
		jRadioButton1.addActionListener(this);
		
		jRadioButton2.addKeyListener(this);
		jRadioButton2.addMouseListener(this);
		jRadioButton2.addActionListener(this);
		
		jComboBox.addKeyListener(this);
		jComboBox.addMouseListener(this);
		jComboBox.addActionListener(this);
		
		jButton.addMouseListener(this);
		jButton.addKeyListener(this);
		
		jButton1.addMouseListener(this);
		jButton1.addKeyListener(this);

		jButton3.addMouseListener(this);
		jButton3.addKeyListener(this);
		
		jButton2.addMouseListener(this);
		jButton2.addKeyListener(this);
		
		jButton4.addMouseListener(this);
		jButton4.addKeyListener(this);
				
		jButton5.addMouseListener(this);
		jButton5.addKeyListener(this);
		
		jComboBox4.addKeyListener(this);
		jComboBox4.addMouseListener(this);
		jComboBox4.addActionListener(this);
		
		jButton6.addMouseListener(this);
		jButton6.addKeyListener(this);
				
		jButton7.addMouseListener(this);
		jButton7.addKeyListener(this);
		
		jComboBox3.addKeyListener(this);
		jComboBox3.addMouseListener(this);
		jComboBox3.addActionListener(this);
		
		jButton8.addMouseListener(this);
		jButton8.addKeyListener(this);
				
		jButton9.addMouseListener(this);
		jButton9.addKeyListener(this);
		
		jComboBox5.addKeyListener(this);
		jComboBox5.addMouseListener(this);
		jComboBox5.addActionListener(this);
		
		jComboBox7.addKeyListener(this);
		jComboBox7.addMouseListener(this);
		jComboBox7.addActionListener(this);
		
		jComboBox8.addKeyListener(this);
		jComboBox8.addMouseListener(this);
		jComboBox8.addActionListener(this);
		
		jComboBox6.addKeyListener(this);
		jComboBox6.addMouseListener(this);
		jComboBox6.addActionListener(this);
		
		jComboBox9.addKeyListener(this);
		jComboBox9.addMouseListener(this);
		jComboBox9.addActionListener(this);	
		
		jButton11.addMouseListener(this);
		jButton11.addKeyListener(this);

		jButton10.addMouseListener(this);
		jButton10.addKeyListener(this);

		jComboBox.setSelectedIndex(1);
		

		this.repintarMantenimiento();
	//	Sesion.getCaja().setEstado(edoFinalCaja);
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

		this.setTitle("Mantenimiento de Estados, Ciudades y Urbanizaciones");
		this.setSize(361, 408);
		this.setContentPane(getJContentPane());		
		this.setUndecorated(true);
		this.setModal(true);
		this.setBackground(new java.awt.Color(226,226,222));
		this.addComponentListener(this);
		
		//agregarListeners();
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
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJPanel(getJPanel7()),null);
		}
		return jContentPane;
	}
	
	
	/**
	 * @return
	 */
	public javax.swing.JPanel getJPanel(JPanel panel) {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel() - start");
		}

		if(jPanel == null) {
			jPanel = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setAlignment(java.awt.FlowLayout.CENTER);
			layFlowLayout1.setHgap(0);
			layFlowLayout1.setVgap(10);
			jPanel.setLayout(layFlowLayout1);
			jPanel.add(getJPanel1(), null);
			jPanel.add(getJPanel7(), null);
			jPanel.add(getJPanel2(), null);
			jPanel.add(getJPanel4(), null);
			jPanel.add(getJPanel6(), null);
			jPanel.add(getJPanel9(), null);
			jPanel.add(getJPanel8(), null);
			jPanel.add(getJPanel5(), null);
			jPanel.add(getJPanel10(), null);
			jPanel.setPreferredSize(new java.awt.Dimension(365,240));
			jPanel.setBackground(new java.awt.Color(242,242,238));
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
		}else
		if (panel != null)
			jPanel.add(panel,null);

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel() - end");
		}
		return jPanel;
	}

	/**
	 * @return
	 */
	public javax.swing.JPanel getJPanel1() {
		if (logger.isDebugEnabled()) {
					logger.debug("getJPanel1() - start");
				}

				if(jPanel1 == null) {
					jPanel1 = new javax.swing.JPanel();
					java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
					layFlowLayout1.setAlignment(java.awt.FlowLayout.CENTER);
					layFlowLayout1.setVgap(5);
					layFlowLayout1.setHgap(5);
					jPanel1.setLayout(layFlowLayout1);
					jPanel1.add(getJLabel20(),null);
					jPanel1.setPreferredSize(new java.awt.Dimension(347,40));
					jPanel1.setBackground(new java.awt.Color(69,107,127));
				}

				if (logger.isDebugEnabled()) {
					logger.debug("getJPanel1) - end");
				}
		return jPanel1;
	}

	/**
	 * @return
	 */
	public javax.swing.JPanel getJPanel2() {
		if (logger.isDebugEnabled()) {
					logger.debug("getJPanel6() - start");
				}

				if(jPanel2 == null) {
					jPanel2 = new javax.swing.JPanel();
					java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
					layFlowLayout2.setAlignment(java.awt.FlowLayout.CENTER);
					layFlowLayout2.setHgap(5);
					layFlowLayout2.setVgap(7);
					jPanel2.setLayout(layFlowLayout2);
			
					// Se arma el Grupo de Botones de tipo RadioButton
					ButtonGroup group = new ButtonGroup();
					group.add(getJRadioButton());
					group.add(getJRadioButton1());
					group.add(getJRadioButton2());
					
			
					jPanel2.add(getJRadioButton(), null);
					jPanel2.add(getJRadioButton1(), null);
					jPanel2.add(getJRadioButton2(), null);
					jPanel2.setBackground(new java.awt.Color(242,242,238));
				}
				if (logger.isDebugEnabled()) {
					logger.debug("getJPanel3() - end");
				}
		return jPanel2;
	}

	/**
	 * @return
	 */
	public javax.swing.JPanel getJPanel3() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel3() - start");
		}

		if(jPanel3 == null) {
			jPanel3 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout3 = new java.awt.FlowLayout();
			layFlowLayout3.setAlignment(java.awt.FlowLayout.CENTER);
			layFlowLayout3.setHgap(5);
			layFlowLayout3.setVgap(7);
			
			jPanel3.setLayout(layFlowLayout3);
			jPanel3.add(getJLabel(),null);
			jPanel3.add(getJComboBox(), null);
			jPanel3.setSize(219, 41);
			jPanel3.setBackground(new java.awt.Color(242,242,238));
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel3() - end");
		}
		return jPanel3;
	}

	/**
	 * @return
	 */
	public javax.swing.JPanel getJPanel4() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel4() - start");
		}
		
		if(jPanel4 == null) {
			jPanel4 = new javax.swing.JPanel();
			GridLayout grid2 = new GridLayout(2,2);
			grid2.setHgap(5);
			grid2.setVgap(120);
			jPanel4.setLayout(grid2);
			jPanel4.add(getJLabel1(),null);
			jPanel4.add(getJTextField(), null);
			jPanel4.add(getJButton1(), null);
			jPanel4.add(getJButton(), null);
			jPanel4.setBackground(new java.awt.Color(242,242,238));			
			jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Agregar Estado", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
		}
		
		
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel4() - end");
		}
		return jPanel4;
	}

	/**
	 * @return
	 */
	public javax.swing.JPanel getJPanel5() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel5() - start");
		}
		
		if(jPanel5 == null) {
			jPanel5 = new javax.swing.JPanel();
			GridLayout grid = new GridLayout(4,2);
			grid.setHgap(5);
			grid.setVgap(25);
			jPanel5.setLayout(grid);
			jPanel5.add(getJLabel6(),null);
			jPanel5.add(getJComboBox1(), null);
			jPanel5.add(getJLabel7(),null);
			jPanel5.add(getJTextField2(), null);
			jPanel5.add(getJLabel4(),null);
			jPanel5.add(getJTextField4(), null);
			jPanel5.add(getJButton3(), null);
			jPanel5.add(getJButton2(), null);
			
			jPanel5.setBackground(new java.awt.Color(242,242,238));			
			jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Agregar Ciudad", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
	
		}	
		
		
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel5() - end");
		}
		return jPanel5;
	}

	/**
	 * @return
	 */
	public javax.swing.JPanel getJPanel6() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel6() - start");
		}
			
		if(jPanel6 == null) {
			jPanel6 = new javax.swing.JPanel();
			GridLayout grid3 = new GridLayout(5,2);
			grid3.setHgap(5);
			grid3.setVgap(12);
			jPanel6.setLayout(grid3);
			jPanel6.add(getJLabel9(),null);
			jPanel6.add(getJComboBox4(), null);
			jPanel6.add(getJLabel2(),null);
			jPanel6.add(getJComboBox2(), null);
			jPanel6.add(getJLabel3(),null);
			jPanel6.add(getJTextField1(), null);
			jPanel6.add(getJLabel5(),null);
			jPanel6.add(getJTextField3(), null);
			jPanel6.add(getJButton5(), null);
			jPanel6.add(getJButton4(), null);
			jPanel6.setBackground(new java.awt.Color(242,242,238));			
			jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Agregar Urbanización", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel6() - end");
		}
		return jPanel6;
	}

	/**
	 * @return
	 */
	public javax.swing.JPanel getJPanel7() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel7() - start");
		}
			
		if(jPanel7 == null) {
			
			jPanel7 = new javax.swing.JPanel();
			GridLayout grid7 = new GridLayout(1,1);
			grid7.setHgap(5);
			grid7.setVgap(5);
			jPanel7.setLayout(grid7);
			jPanel7.add(getJPanel3(),null);
			jPanel7.setBackground(new java.awt.Color(242,242,238));			
			
		}
		
		
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel7() - end");
		}
		
		return jPanel7;
	}

	/**
	 * @return
	 */
	public javax.swing.JPanel getJPanel8() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel8() - start");
		}
		
		if(jPanel8 == null) {
			jPanel8 = new javax.swing.JPanel();
			GridLayout grid8 = new GridLayout(3,2);
			grid8.setHgap(5);
			grid8.setVgap(50);
			jPanel8.setLayout(grid8);
			jPanel8.add(getJLabel10(),null);
			jPanel8.add(getJComboBox3(),null);
			jPanel8.add(getJLabel11(),null);
			jPanel8.add(getJTextField5(),null);
			jPanel8.add(getJButton9(),null);
			jPanel8.add(getJButton8(),null);
			jPanel8.setBackground(new java.awt.Color(242,242,238));			
			jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Modificar Estado", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
		}
		
		
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel8() - end");
		}
		
		return jPanel8;
	 
	}
	 /* @return8
	 */
	public javax.swing.JPanel getJPanel9() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel9() - start");
		}
		
		if(jPanel9 == null) {
			jPanel9 = new javax.swing.JPanel();
			GridLayout grid9 = new GridLayout(5,2);
			grid9.setHgap(5);
			grid9.setVgap(12);
			jPanel9.setLayout(grid9);
			jPanel9.add(getJLabel12(),null);
			jPanel9.add(getJComboBox5(),null);
			jPanel9.add(getJLabel13(),null);
			jPanel9.add(getJComboBox7(),null);
			jPanel9.add(getJLabel14(),null);
			jPanel9.add(getJTextField6(),null);
			jPanel9.add(getJLabel15(),null);
			jPanel9.add(getJTextField7(),null);
			jPanel9.add(getJButton7(),null);
			jPanel9.add(getJButton6(),null);
			
			jPanel9.setBackground(new java.awt.Color(242,242,238));			
			jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Modificar Ciudad", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
	
		}	
		
		
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel9() - end");
		}
		return jPanel9;
	}
	
	/**
	 * @return
	 */
	public javax.swing.JPanel getJPanel10() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel10() - start");
		}
			
		if(jPanel10 == null) {
			jPanel10 = new javax.swing.JPanel();
			GridLayout grid10 = new GridLayout(6,2);
			grid10.setHgap(5);
			grid10.setVgap(5);
			jPanel10.setLayout(grid10);
			jPanel10.add(getJLabel16(),null);
			jPanel10.add(getJComboBox8(),null);
			jPanel10.add(getJLabel17(),null);
			jPanel10.add(getJComboBox6(),null);
			jPanel10.add(getJLabel8(),null);
			jPanel10.add(getJComboBox9(),null);
			jPanel10.add(getJLabel19(),null);
			jPanel10.add(getJTextField9(),null);
			jPanel10.add(getJLabel18(),null);
			jPanel10.add(getJTextField8(),null);
			jPanel10.add(getJButton11(), null);
			jPanel10.add(getJButton10(), null);
			jPanel10.setBackground(new java.awt.Color(242,242,238));			
			jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Modificar Urbanización", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel10() - end");
		}
		return jPanel10;
	}

	
	/* (no Javadoc)
	 * @see java.awt.event.ComponentListener#componentHidden(java.awt.event.ComponentEvent)
	 */
	public void componentHidden(ComponentEvent e) {

	}
	/* (no Javadoc)
	 * @see java.awt.event.ComponentListener#componentMoved(java.awt.event.ComponentEvent)
	 */
	public void componentMoved(ComponentEvent e) {

	}
	/* (no Javadoc)
	 * @see java.awt.event.ComponentListener#componentResized(java.awt.event.ComponentEvent)
	 */
	public void componentResized(ComponentEvent e) {

	}
	/* (no Javadoc)
	 * @see java.awt.event.ComponentListener#componentShown(java.awt.event.ComponentEvent)
	 */
	public void componentShown(ComponentEvent e) {

	}
	/* (no Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}

		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			dispose();
		}
		
		//Mapeo de TAB sobre el componente jTable y jTextField
		if(e.getKeyCode() == KeyEvent.VK_TAB){
		}	
		try{
			
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				if(e.getSource().equals(jButton1)){
					if (!getJTextField().getText().equals(""))
					{
						if (MensajesVentanas.preguntarSiNo("¿Desea almacenar el estado \" " + getJTextField().getText() + "\"?", true, true) == 0){
							mt.almacenarEstado(getJTextField().getText());
							this.repintarMantenimiento();
						}
					}
					else
						MensajesVentanas.aviso("Introduzca un valor para el Estado");
				} else if(e.getSource().equals(jButton3)){
					if ((!getJTextField2().getText().equals("")) && (jComboBox1.getSelectedIndex() != 0))
					{
						if (MensajesVentanas.preguntarSiNo("¿Desea almacenar la ciudad \" " + getJTextField2().getText() + "\"?", true, true) == 0){
							mt.almacenarCiudad(jComboBox1.getSelectedIndex(),getJTextField2().getText(),getJTextField4().getText());
							this.repintarMantenimiento();
						}
					}
					else
						MensajesVentanas.aviso("Introduzca un valor para la Ciudad");
				}else if(e.getSource().equals(jButton5)){
					if ((!getJTextField1().getText().equals("")) && (jComboBox4.getSelectedIndex() != 0) && (jComboBox2.getSelectedIndex() != 0))
					{
						if (MensajesVentanas.preguntarSiNo("¿Desea almacenar la urbanización \" " + getJTextField1().getText() + "\"?", true, true) == 0){
							try {
								mt.almacenarUrbanizacion(jComboBox4.getSelectedIndex(),jComboBox2.getSelectedIndex(),getJTextField1().getText(),getJTextField3().getText());
							} catch (UsuarioExcepcion e1) {
								MensajesVentanas.mensajeError(e1.getMensaje());
							} catch (ExcepcionCr e1) {
								MensajesVentanas.mensajeError(e1.getMensaje());
							}
							this.repintarMantenimiento();
						}
					}
					else
						MensajesVentanas.aviso("Introduzca un valor para la Urbanización");
				}else if(e.getSource().equals(jButton9)){
					if (jButton9.getText().equals("Registrar")){
						if ((!getJTextField5().getText().equals("")) && (jComboBox3.getSelectedIndex() != 0)){
							if (MensajesVentanas.preguntarSiNo("La descripción del Estado será modificada.\n¿Está usted de acuerdo?") == 0)
							{
								mt.modificarEstado(getJTextField5().getText(),jComboBox3.getSelectedIndex(),"");
								this.repintarMantenimiento();
							}
						}
						else
							MensajesVentanas.aviso("La descripción del estado no puede estar en blanco");
					} else
						if (MensajesVentanas.preguntarSiNo("¿Desea eliminar el Estado seleccionado?") == 0)
						{
							mt.modificarEstado(getJTextField5().getText(),jComboBox3.getSelectedIndex(),"9");
							dispose();
						}
				}else if(e.getSource().equals(jButton7)){
					if (jButton7.getText().equals("Registrar")){
						if ((!getJTextField6().getText().equals("")) && (jComboBox5.getSelectedIndex() != 0) && (jComboBox7.getSelectedIndex() != 0)){
							if (MensajesVentanas.preguntarSiNo("La descripción de la Ciudad será modificada.\n¿Está usted de acuerdo?") == 0)
							{
								mt.modificarCiudad(jComboBox5.getSelectedIndex(),jComboBox7.getSelectedIndex(),getJTextField6().getText(),getJTextField7().getText(),"");
								this.repintarMantenimiento();
							}
						}
						else
							MensajesVentanas.aviso("La descripción de la ciudad no puede estar en blanco"); 
					} else
						if (MensajesVentanas.preguntarSiNo("¿Desea eliminar la ciudad seleccionada?") == 0)
						{	
							mt.modificarCiudad(jComboBox5.getSelectedIndex(),jComboBox7.getSelectedIndex(),getJTextField6().getText(),getJTextField7().getText(),"9");
							dispose();
						} 
				}else if(e.getSource().equals(jButton11)){
					if (jButton11.getText().equals("Registrar")){
						if ((!getJTextField9().getText().equals("")) && (jComboBox8.getSelectedIndex() != 0) && (jComboBox6.getSelectedIndex() != 0) && (jComboBox6.getSelectedIndex() != 0)){
							if (MensajesVentanas.preguntarSiNo("La descripción de la Urbanización será modificada.\n¿Está usted de acuerdo?") == 0)
							{	
								mt.modificarUrbanizacion(jComboBox8.getSelectedIndex(),jComboBox6.getSelectedIndex(),jComboBox9.getSelectedIndex(),getJTextField9().getText(),getJTextField8().getText(),"");
								this.repintarMantenimiento();
							}
						}
						else
							MensajesVentanas.aviso("La descripción de la Urbanización no puede estar en blanco"); 
					} else
						if (MensajesVentanas.preguntarSiNo("¿Desea eliminar la urbanización seleccionada?") == 0)
						{
							mt.modificarCiudad(jComboBox5.getSelectedIndex(),jComboBox7.getSelectedIndex(),getJTextField6().getText(),getJTextField7().getText(),"9");
							this.repintarMantenimiento();
						} 
				}
								
			}
		} catch (MaquinaDeEstadoExcepcion ex) {
			ex.printStackTrace();
		} catch (XmlExcepcion ex) {
			ex.printStackTrace();
		} catch (FuncionExcepcion ex) {
			ex.printStackTrace();
		} catch (ConexionExcepcion ex) {
			ex.printStackTrace();
		} catch (UsuarioExcepcion ex) {
			MensajesVentanas.mensajeError(ex.getMensaje());
		} catch (ExcepcionCr ex) {
			MensajesVentanas.mensajeError(ex.getMensaje());
		} 
		
	}
	/* (no Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
		
	}
	/* (no Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
		
	}
	/* (no Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
	
	}
	/* (no Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
	
	}
	/* (no Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {

	}
	/* (no Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {

	}
	/* (no Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - start");
		}
		
		try{
			if(e.getSource().equals(jButton1)){
				if (!getJTextField().getText().equals("")){
					if (MensajesVentanas.preguntarSiNo("¿Desea almacenar el estado \" " + getJTextField().getText() + "\"?", true, true) == 0){
						mt.almacenarEstado(getJTextField().getText());
						this.repintarMantenimiento();
					}
				}
				else
					MensajesVentanas.aviso("Introduzca un valor para el Estado");
			} else if(e.getSource().equals(jButton3)){
				if ((!getJTextField2().getText().equals("")) && (jComboBox1.getSelectedIndex() != 0)){
					if (MensajesVentanas.preguntarSiNo("¿Desea almacenar la ciudad \" " + getJTextField2().getText() + "\"?", true, true) == 0){
						mt.almacenarCiudad(jComboBox1.getSelectedIndex(),getJTextField2().getText(),getJTextField4().getText());
						this.repintarMantenimiento();
					}
				}
				else
					MensajesVentanas.aviso("Introduzca un valor para la Ciudad");
			}else if(e.getSource().equals(jButton5)){
				if ((!getJTextField1().getText().equals("")) && (jComboBox4.getSelectedIndex() != 0) && (jComboBox2.getSelectedIndex() != 0)){
					if (MensajesVentanas.preguntarSiNo("¿Desea almacenar la urbanización \" " + getJTextField1().getText() + "\"?", true, true) == 0){
						mt.almacenarUrbanizacion(jComboBox4.getSelectedIndex(),jComboBox2.getSelectedIndex(),getJTextField1().getText(),getJTextField3().getText());
						this.repintarMantenimiento();
					}
				}
				else
					MensajesVentanas.aviso("Introduzca un valor para la Urbanización");
			}else if(e.getSource().equals(jButton9)){
				if (jButton9.getText().equals("Registrar")){
					if ((!getJTextField5().getText().equals("")) && (jComboBox3.getSelectedIndex() != 0)){
						if (MensajesVentanas.preguntarSiNo("La descripción del Estado será modificada.\n¿Está usted de acuerdo?") == 0)
						{
							mt.modificarEstado(getJTextField5().getText(),jComboBox3.getSelectedIndex(),"");
							this.repintarMantenimiento();
						}
					}
					else
						MensajesVentanas.aviso("La descripción del estado no puede estar en blanco");
				} else
					if (MensajesVentanas.preguntarSiNo("¿Desea eliminar el Estado seleccionado?") == 0)
					{
						mt.modificarEstado(getJTextField5().getText(),jComboBox3.getSelectedIndex(),"9");
						this.repintarMantenimiento();
					}
			}else if(e.getSource().equals(jButton7)){
				if (jButton7.getText().equals("Registrar")){
					if ((!getJTextField6().getText().equals("")) && (jComboBox5.getSelectedIndex() != 0) && (jComboBox7.getSelectedIndex() != 0)){
						if (MensajesVentanas.preguntarSiNo("La descripción de la Ciudad será modificada.\n¿Está usted de acuerdo?") == 0){
							mt.modificarCiudad(jComboBox5.getSelectedIndex(),jComboBox7.getSelectedIndex(),getJTextField6().getText(),getJTextField7().getText(),"");
							this.repintarMantenimiento();
						}
					}
					else
						MensajesVentanas.aviso("La descripción de la ciudad no puede estar en blanco"); 
				} else
					if (MensajesVentanas.preguntarSiNo("¿Desea eliminar la ciudad seleccionada?") == 0)
					{
						mt.modificarCiudad(jComboBox5.getSelectedIndex(),jComboBox7.getSelectedIndex(),getJTextField6().getText(),getJTextField7().getText(),"9");
						this.repintarMantenimiento();
					} 
			}else if(e.getSource().equals(jButton11)){
				if (jButton11.getText().equals("Registrar")){
					if ((!getJTextField9().getText().equals("")) && (jComboBox8.getSelectedIndex() != 0) && (jComboBox6.getSelectedIndex() != 0) && (jComboBox6.getSelectedIndex() != 0)){
						if (MensajesVentanas.preguntarSiNo("La descripción de la Urbanización será modificada.\n¿Está usted de acuerdo?") == 0)
						{
							mt.modificarUrbanizacion(jComboBox8.getSelectedIndex(),jComboBox6.getSelectedIndex(),jComboBox9.getSelectedIndex(),getJTextField9().getText(),getJTextField8().getText(),"");
							this.repintarMantenimiento();
						}
					}
					else
						MensajesVentanas.aviso("La descripción de la Urbanización no puede estar en blanco"); 
				} else if (MensajesVentanas.preguntarSiNo("¿Desea eliminar la urbanización seleccionada?") == 0)
					{
						mt.modificarUrbanizacion(jComboBox8.getSelectedIndex(),jComboBox6.getSelectedIndex(),jComboBox9.getSelectedIndex(),getJTextField9().getText(),getJTextField8().getText(),"9");
						this.repintarMantenimiento();
					} 
			}else if ((e.getSource().equals(jButton)) || (e.getSource().equals(jButton2)) || (e.getSource().equals(jButton4)) || (e.getSource().equals(jButton6))
						|| (e.getSource().equals(jButton8)) || (e.getSource().equals(jButton10))){
					if (MensajesVentanas.preguntarSiNo("¿Desea cancelar la operación actual?") == 0){
						mt.finalizarMantenimiento(servicio);
						dispose();
						
					}
			}
		} catch (MaquinaDeEstadoExcepcion ex) {
			ex.printStackTrace();
		} catch (XmlExcepcion ex) {
			ex.printStackTrace();
		} catch (FuncionExcepcion ex) {
			ex.printStackTrace();
		} catch (ConexionExcepcion ex) {
			ex.printStackTrace();
		} catch (BaseDeDatosExcepcion ex) {
			ex.printStackTrace();
		} catch (UsuarioExcepcion e1) {
			MensajesVentanas.mensajeError(e1.getMensaje());
		} catch (ExcepcionCr e1) {
			MensajesVentanas.mensajeError(e1.getMensaje());
		} 			 
	}
		
	//}
	/* (no Javadoc)
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	public void focusGained(FocusEvent e) {
	
	}
	/* (no Javadoc)
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	public void focusLost(FocusEvent e) {

	}
	/* (no Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("actionPerformed(ActionEvent) - start");
		}
		
		if ((e.getActionCommand().equals("Agregar")) || (e.getActionCommand().equals("Modificar")) || (e.getActionCommand().equals("Eliminar")))
		{
			this.repintarMantenimiento();
		} else if (e.getSource().equals(jComboBox))
		{

			this.repintarMantenimiento();
			
		} else if (e.getSource().equals(jComboBox3))
		{
			getJTextField5().setText(((Estado)Sesion.estados.elementAt(jComboBox3.getSelectedIndex()-1)).getDescripcion());
		} else if (e.getSource().equals(jComboBox7))
		{
			getJTextField6().setText(((Ciudad)Sesion.ciudades.elementAt(jComboBox7.getSelectedIndex()-1)).getDescripcion());
			getJTextField7().setText(((Ciudad)Sesion.ciudades.elementAt(jComboBox7.getSelectedIndex()-1)).getCodArea());
		} else if (e.getSource().equals(jComboBox9))
		{
			getJTextField9().setText(((Urbanizacion)Sesion.urbanizaciones.elementAt(jComboBox9.getSelectedIndex()-1)).getDescripcion());
			getJTextField8().setText(((Urbanizacion)Sesion.urbanizaciones.elementAt(jComboBox9.getSelectedIndex()-1)).getZonaPostal());
		} else if (e.getSource().equals(jComboBox4)) {
			// Aqui viene el cambio de tipo de busqueda
			int consultaEdo = jComboBox4.getSelectedIndex();

			Sesion.cargarCiudades(consultaEdo - 1);
			llenarComboCiudades(jComboBox2);

		} else if (e.getSource().equals(jComboBox5)) {
			// Aqui viene el cambio de tipo de busqueda
			int consultaEdo = jComboBox5.getSelectedIndex();

			Sesion.cargarCiudades(consultaEdo - 1);
			llenarComboCiudades(jComboBox7);

		} else if (e.getSource().equals(jComboBox8)) {
			// Aqui viene el cambio de tipo de busqueda
			int consultaEdo = jComboBox8.getSelectedIndex();

			Sesion.cargarCiudades(consultaEdo - 1);
			llenarComboCiudades(jComboBox6);

		} else if (e.getSource().equals(jComboBox6)) {
			// Aqui viene el cambio de tipo de busqueda
			int consultaCiu = jComboBox6.getSelectedIndex();
			int consultaEst = jComboBox8.getSelectedIndex();

			Sesion.cargarUrbanizaciones(consultaEst - 1,consultaCiu - 1);
			llenarComboUrbanizaciones();
		} 

	}

	private void llenarComboCiudades(JComboBox combo)
	{
		if(combo != null) {
			combo.removeActionListener(this);
			combo.removeAllItems();
			combo.addItem("Seleccione uno");
			if(Sesion.ciudades != null)
				for (int i = 0; i < Sesion.ciudades.size(); i++)
					combo.addItem(((Ciudad)Sesion.ciudades.elementAt(i)).getDescripcion());
			combo.addActionListener(this);
		}
	
	}

	private void llenarComboUrbanizaciones()
	{
		if(jComboBox9 != null) {
			jComboBox9.removeActionListener(this);
			jComboBox9.removeAllItems();
			jComboBox9.addItem("Seleccione uno");
			if(Sesion.urbanizaciones != null)
				for (int i = 0; i < Sesion.urbanizaciones.size(); i++)
					jComboBox9.addItem(((Urbanizacion)Sesion.urbanizaciones.elementAt(i)).getDescripcion());
			jComboBox9.addActionListener(this);
		}

	}


	/**
	 * @return
	 */
	public javax.swing.JComboBox getJComboBox(){
		if (logger.isDebugEnabled()) {
			logger.debug("getJComboBox() - start");
		}

		if(jComboBox == null) {
			jComboBox = new JHighlightComboBox();
			jComboBox.setPreferredSize(new java.awt.Dimension(120,25));
			jComboBox.setBackground(new java.awt.Color(226,226,222));
			jComboBox.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jComboBox.addItem("Seleccione uno");
			jComboBox.addItem("Estado");
			jComboBox.addItem("Ciudad");
			jComboBox.addItem("Urbanización");
		}
			if (logger.isDebugEnabled()) {
				logger.debug("getJComboBox() - end");
			}
		
		return jComboBox;
	}

	/**
	 * @return
	 */
	public javax.swing.JRadioButton getJRadioButton() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJRadioButton() - start");
		}

		if(jRadioButton == null) {
			jRadioButton = new javax.swing.JRadioButton();
			jRadioButton.setText("Agregar");
			jRadioButton.setBackground(new java.awt.Color(242,242,238));
			jRadioButton.setSelected(true);
					//jRadioButton1.addChangeListener(tipoListener);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJRadioButton() - end");
		}
		return jRadioButton;
	}

	/**
	 * @return
	 */
	public javax.swing.JRadioButton getJRadioButton1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJRadioButton1() - start");
		}

		if(jRadioButton1 == null) {
			jRadioButton1 = new javax.swing.JRadioButton();
			jRadioButton1.setText("Modificar");
			jRadioButton1.setBackground(new java.awt.Color(242,242,238));
			jRadioButton1.setSelected(false);
			//jRadioButton1.addChangeListener(tipoListener);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJRadioButton1() - end");
		}
		return jRadioButton1;
	}

	/**
	 * @return
	 */
	public javax.swing.JRadioButton getJRadioButton2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJRadioButton2() - start");
		}

		if(jRadioButton2 == null) {
			jRadioButton2 = new javax.swing.JRadioButton();
			jRadioButton2.setText("Eliminar");
			jRadioButton2.setBackground(new java.awt.Color(242,242,238));
			jRadioButton2.setSelected(false);
			//jRadioButton1.addChangeListener(tipoListener);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJRadioButton2() - end");
		}
		return jRadioButton2;
	}

	/**
	 * @return
	 */
	public javax.swing.JLabel getJLabel() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel() - start");
		}

		if(jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setText("Opciones: ");
			jLabel.setPreferredSize(new java.awt.Dimension(70,10));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel() - end");
		}
		return jLabel;
	}

	/**
	 * @return
	 */
	public javax.swing.JComboBox getJComboBox1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJComboBox1() - start");
		}

		if(jComboBox1 == null) {
			jComboBox1 = new JHighlightComboBox();
			jComboBox1.setPreferredSize(new java.awt.Dimension(150,25));
			jComboBox1.setBackground(new java.awt.Color(226,226,222));
			jComboBox1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jComboBox1.addItem("Seleccione uno");
			if(Sesion.estados != null)
				for (int i = 0; i < Sesion.estados.size(); i++)
					jComboBox1.addItem(((Estado)Sesion.estados.elementAt(i)).getDescripcion());
			}

			if (logger.isDebugEnabled()) {
				logger.debug("getJComboBox1() - end");
			}
		return jComboBox1;
	}

	/**
	 * @return
	 */
	public javax.swing.JComboBox getJComboBox2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJComboBox2() - start");
		}

		if(jComboBox2== null) {
			jComboBox2 = new JHighlightComboBox();
			jComboBox2.setPreferredSize(new java.awt.Dimension(150,25));
			jComboBox2.setBackground(new java.awt.Color(226,226,222));
			jComboBox2.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jComboBox2.addItem("Seleccione uno");
			
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getJComboBox2() - end");
		}		
		return jComboBox2;
	}

	/**
	 * @return
	 */
	public javax.swing.JComboBox getJComboBox3() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJComboBox3() - start");
		}

		if(jComboBox3 == null) {
			jComboBox3 = new JHighlightComboBox();
			jComboBox3.setPreferredSize(new java.awt.Dimension(150,25));
			jComboBox3.setBackground(new java.awt.Color(226,226,222));
			jComboBox3.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jComboBox3.addItem("Seleccione uno");
			if(Sesion.estados != null)
				for (int i = 0; i < Sesion.estados.size(); i++)
					jComboBox3.addItem(((Estado)Sesion.estados.elementAt(i)).getDescripcion());
		}
		
		
		if (logger.isDebugEnabled()) {
			logger.debug("getJComboBox3() - end");
		}		
		return jComboBox3;
	}
	
	/**
		 * @return
		 */
		public javax.swing.JComboBox getJComboBox4() {
			if (logger.isDebugEnabled()) {
				logger.debug("getJComboBox4() - start");
			}

			if(jComboBox4 == null) {
				jComboBox4 = new JHighlightComboBox();
				jComboBox4.setPreferredSize(new java.awt.Dimension(150,25));
				jComboBox4.setBackground(new java.awt.Color(226,226,222));
				jComboBox4.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
				jComboBox4.addItem("Seleccione uno");
				if(Sesion.estados != null)
					for (int i = 0; i < Sesion.estados.size(); i++)
						jComboBox4.addItem(((Estado)Sesion.estados.elementAt(i)).getDescripcion());
				}

				if (logger.isDebugEnabled()) {
					logger.debug("getJComboBox4() - end");
				}
			return jComboBox4;
		}

		/**
		 * @return
		 */
		public javax.swing.JComboBox getJComboBox5() {
			if (logger.isDebugEnabled()) {
				logger.debug("getJComboBox5() - start");
			}

			if(jComboBox5== null) {
				jComboBox5 = new JHighlightComboBox();
				jComboBox5.setPreferredSize(new java.awt.Dimension(150,25));
				jComboBox5.setBackground(new java.awt.Color(226,226,222));
				jComboBox5.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
				jComboBox5.addItem("Seleccione uno");
				if(Sesion.estados != null)
					for (int i = 0; i < Sesion.estados.size(); i++)
							jComboBox5.addItem(((Estado)Sesion.estados.elementAt(i)).getDescripcion());
			}
			if (logger.isDebugEnabled()) {
				logger.debug("getJComboBox5() - end");
			}		
			return jComboBox5;
		}

		/**
		 * @return
		 */
		public javax.swing.JComboBox getJComboBox6() {
			if (logger.isDebugEnabled()) {
				logger.debug("getJComboBox6() - start");
			}

			if(jComboBox6 == null) {
				jComboBox6 = new JHighlightComboBox();
				jComboBox6.setPreferredSize(new java.awt.Dimension(150,25));
				jComboBox6.setBackground(new java.awt.Color(226,226,222));
				jComboBox6.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
				jComboBox6.addItem("Seleccione uno");
			}
		
			if (logger.isDebugEnabled()) {
				logger.debug("getJComboBox6() - end");
			}		
			return jComboBox6;
		}
		
		/**
		 * @return
		 */
		public javax.swing.JComboBox getJComboBox7() {
			if (logger.isDebugEnabled()) {
				logger.debug("getJComboBox7() - start");
			}

			if(jComboBox7== null) {
				jComboBox7 = new JHighlightComboBox();
				jComboBox7.setPreferredSize(new java.awt.Dimension(150,25));
				jComboBox7.setBackground(new java.awt.Color(226,226,222));
				jComboBox7.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
				jComboBox7.addItem("Seleccione uno");
			
			}
			if (logger.isDebugEnabled()) {
				logger.debug("getJComboBox7() - end");
			}		
			return jComboBox7;
		}

/**
	 * @return
	 */
	public javax.swing.JComboBox getJComboBox8() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJComboBox8() - start");
		}

		if(jComboBox8 == null) {
			jComboBox8 = new JHighlightComboBox();
			jComboBox8.setPreferredSize(new java.awt.Dimension(150,25));
			jComboBox8.setBackground(new java.awt.Color(226,226,222));
			jComboBox8.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jComboBox8.addItem("Seleccione uno");
			if(Sesion.estados != null)
				for (int i = 0; i < Sesion.estados.size(); i++)
					jComboBox8.addItem(((Estado)Sesion.estados.elementAt(i)).getDescripcion());
			}

			if (logger.isDebugEnabled()) {
				logger.debug("getJComboBox8() - end");
			}
		return jComboBox8;
	}

	/**
	 * @return
	 */
	public javax.swing.JComboBox getJComboBox9() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJComboBox9() - start");
		}
	
		if(jComboBox9== null) {
			jComboBox9 = new JHighlightComboBox();
			jComboBox9.setPreferredSize(new java.awt.Dimension(150,25));
			jComboBox9.setBackground(new java.awt.Color(226,226,222));
			jComboBox9.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jComboBox9.addItem("Seleccione uno");
				
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getJComboBox9() - end");
		}		
		return jComboBox9;
	}


	/**
	 * @return
	 */
	public javax.swing.JLabel getJLabel1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel1() - start");
		}

		if(jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setText("Estado: ");
			jLabel1.setPreferredSize(new java.awt.Dimension(70,10));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel1() - end");
		}
		return jLabel1;
	}

	/**
	 * @return
	 */
	public javax.swing.JLabel getJLabel2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel2() - start");
		}

		if(jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setText("Ciudad: ");
			jLabel2.setPreferredSize(new java.awt.Dimension(70,10));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel2() - end");
		}		
		return jLabel2;
	}

	/**
	 * @return
	 */
	public javax.swing.JLabel getJLabel3() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel3() - start");
		}

		if(jLabel3 == null) {
			jLabel3 = new javax.swing.JLabel();
			jLabel3.setText("Urbanización: ");
			jLabel3.setPreferredSize(new java.awt.Dimension(90,10));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel3() - end");
		}		
		return jLabel3;
	}

	/**
	 * @return
	 */
	public javax.swing.JLabel getJLabel4() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel4() - start");
		}

		if(jLabel4 == null) {
			jLabel4 = new javax.swing.JLabel();
			jLabel4.setText("Código de área: ");
			jLabel4.setPreferredSize(new java.awt.Dimension(100,10));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel4() - end");
		}		
		return jLabel4;
	}

	/**
	 * @return
	 */
	public javax.swing.JLabel getJLabel5() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel5() - start");
		}

		if(jLabel5 == null) {
			jLabel5 = new javax.swing.JLabel();
			jLabel5.setText("Zona postal: ");
			jLabel5.setPreferredSize(new java.awt.Dimension(90,10));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel5() - end");
		}		
		return jLabel5;
	}
	
	/**
	 * @return
	*/
	public javax.swing.JLabel getJLabel6() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel6() - start");
		}

		if(jLabel6 == null) {
			jLabel6 = new javax.swing.JLabel();
			jLabel6.setText("Estado: ");
			jLabel6.setPreferredSize(new java.awt.Dimension(70,10));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel6() - end");
		}
		return jLabel6;
	}

	/**
	* @return
	*/
	public javax.swing.JLabel getJLabel7() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel7() - start");
		}

		if(jLabel7 == null) {
			jLabel7 = new javax.swing.JLabel();
			jLabel7.setText("Ciudad: ");
			jLabel7.setPreferredSize(new java.awt.Dimension(70,10));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel7() - end");
		}		
		return jLabel7;
	}

	/**
	 * @return
	 */
	public javax.swing.JLabel getJLabel8() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel8() - start");
		}

		if(jLabel8 == null) {
			jLabel8 = new javax.swing.JLabel();
			jLabel8.setText("Urbanización: ");
			jLabel8.setPreferredSize(new java.awt.Dimension(90,10));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel8() - end");
		}		
		return jLabel8;
	}
	/**
	 * @return
	*/
	public javax.swing.JLabel getJLabel9() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel9() - start");
		}

		if(jLabel9 == null) {
			jLabel9 = new javax.swing.JLabel();
			jLabel9.setText("Estado: ");
			jLabel9.setPreferredSize(new java.awt.Dimension(70,10));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel9() - end");
		}
		return jLabel9;
	}

/**
	 * @return
	*/
	public javax.swing.JLabel getJLabel10() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel10() - start");
		}

		if(jLabel10 == null) {
			jLabel10 = new javax.swing.JLabel();
			jLabel10.setText("Estado: ");
			jLabel10.setPreferredSize(new java.awt.Dimension(70,10));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel10() - end");
		}
		return jLabel10;
	}
/**
	 * @return
	*/
	public javax.swing.JLabel getJLabel11() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel11() - start");
		}

		if(jLabel11 == null) {
			jLabel11 = new javax.swing.JLabel();
			jLabel11.setText("Nuevo: ");
			jLabel11.setPreferredSize(new java.awt.Dimension(70,10));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel11() - end");
		}
		return jLabel11;
	}
	
	/**
	* @return
	*/
	public javax.swing.JLabel getJLabel12() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel12() - start");
		}

		if(jLabel12 == null) {
			jLabel12 = new javax.swing.JLabel();
			jLabel12.setText("Estado: ");
			jLabel12.setPreferredSize(new java.awt.Dimension(70,10));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel12() - end");
		}		
		return jLabel12;
	}

	/**
	 * @return
	 */
	public javax.swing.JLabel getJLabel13() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel13() - start");
		}

		if(jLabel13 == null) {
			jLabel13 = new javax.swing.JLabel();
			jLabel13.setText("Ciudad: ");
			jLabel13.setPreferredSize(new java.awt.Dimension(90,10));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel13() - end");
		}		
		return jLabel13;
	}
	/**
	 * @return
	*/
	public javax.swing.JLabel getJLabel14() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel14() - start");
		}

		if(jLabel14 == null) {
			jLabel14 = new javax.swing.JLabel();
			jLabel14.setText("Nuevo: ");
			jLabel14.setPreferredSize(new java.awt.Dimension(70,10));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel14() - end");
		}
		return jLabel14;
	}

/**
	 * @return
	*/
	public javax.swing.JLabel getJLabel15() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel15() - start");
		}

		if(jLabel15 == null) {
			jLabel15 = new javax.swing.JLabel();
			jLabel15.setText("Código de Área: ");
			jLabel15.setPreferredSize(new java.awt.Dimension(100,10));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel15() - end");
		}
		return jLabel15;
	}
/**
	 * @return
	*/
	public javax.swing.JLabel getJLabel16() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel16() - start");
		}

		if(jLabel16 == null) {
			jLabel16 = new javax.swing.JLabel();
			jLabel16.setText("Estado: ");
			jLabel16.setPreferredSize(new java.awt.Dimension(70,10));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel16() - end");
		}
		return jLabel16;
	}
	/**
	 * @return
	 */
	public javax.swing.JLabel getJLabel17() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel17() - start");
		}

		if(jLabel17 == null) {
			jLabel17 = new javax.swing.JLabel();
			jLabel17.setText("Ciudad: ");
			jLabel17.setPreferredSize(new java.awt.Dimension(70,10));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel17() - end");
		}		
		return jLabel17;
	}
	/**
	 * @return
	 */
	public javax.swing.JLabel getJLabel18() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel18() - start");
		}

		if(jLabel18 == null) {
			jLabel18 = new javax.swing.JLabel();
			jLabel18.setText("Zona postal: ");
			jLabel18.setPreferredSize(new java.awt.Dimension(90,10));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel18() - end");
		}		
		return jLabel18;
	}	
	
/**
	 * @return
	*/
	public javax.swing.JLabel getJLabel19() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel19() - start");
		}

		if(jLabel19 == null) {
			jLabel19 = new javax.swing.JLabel();
			jLabel19.setText("Nuevo: ");
			jLabel19.setPreferredSize(new java.awt.Dimension(70,10));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel19() - end");
		}
		return jLabel19;
	}
	
	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel20() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel20() - start");
		}

		if(jLabel20 == null) {
			jLabel20 = new javax.swing.JLabel();
			jLabel20.setText("Mantenimiento");
			jLabel20.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel20.setForeground(java.awt.Color.white);
			jLabel20.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/earth_location.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel20() - end");
		}
		return jLabel20;
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
				jButton.setText("Salir");
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
				jButton1.setText("Registrar");
				jButton1.setBackground(new java.awt.Color(226,226,222));
				jButton1.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
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
		private javax.swing.JButton getJButton2() {
			if (logger.isDebugEnabled()) {
				logger.debug("getJButton2() - start");
			}

			if(jButton2 == null) {
				jButton2 = new JHighlightButton();
				jButton2.setText("Salir");
				jButton2.setBackground(new java.awt.Color(226,226,222));
				jButton2.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
			}

			if (logger.isDebugEnabled()) {
				logger.debug("getJButton2() - end");
			}
			return jButton2;
		}

		/**
		 * This method initializes jButton1
		 * 
		 * @return javax.swing.JButton
		 */
		private javax.swing.JButton getJButton3() {
			if (logger.isDebugEnabled()) {
				logger.debug("getJButton3() - start");
			}

			if(jButton3 == null) {
				jButton3 = new JHighlightButton();
				jButton3.setText("Registrar");
				jButton3.setBackground(new java.awt.Color(226,226,222));
				jButton3.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
			}

			if (logger.isDebugEnabled()) {
				logger.debug("getJButton3() - end");
			}
			return jButton3;
		}
	
	/**
	* This method initializes jButton
	* 
	* @return javax.swing.JButton
	*/
	private javax.swing.JButton getJButton4() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton4() - start");
		}

		if(jButton4 == null) {
			jButton4 = new JHighlightButton();
			jButton4.setText("Salir");
			jButton4.setBackground(new java.awt.Color(226,226,222));
			jButton4.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
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
	private javax.swing.JButton getJButton5() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton5() - start");
		}

		if(jButton5 == null) {
			jButton5 = new JHighlightButton();
			jButton5.setText("Registrar");
			jButton5.setBackground(new java.awt.Color(226,226,222));
			jButton5.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton5() - end");
		}
		return jButton5;
	}
/**
* This method initializes jButton
* 
* @return javax.swing.JButton
*/
	private javax.swing.JButton getJButton6() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton() - start");
		}

		if(jButton6 == null) {
			jButton6 = new JHighlightButton();
			jButton6.setText("Salir");
			jButton6.setBackground(new java.awt.Color(226,226,222));
			jButton6.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton6() - end");
		}
		return jButton6;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton7() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton7() - start");
		}

		if(jButton7 == null) {
			jButton7 = new JHighlightButton();
			jButton7.setText("Registrar");
			jButton7.setBackground(new java.awt.Color(226,226,222));
			jButton7.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton7() - end");
		}
		return jButton7;
	}
		
/**
* This method initializes jButton
* 
* @return javax.swing.JButton
*/
	private javax.swing.JButton getJButton8() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton8() - start");
		}

		if(jButton8 == null) {
			jButton8 = new JHighlightButton();
			jButton8.setText("Salir");
			jButton8.setBackground(new java.awt.Color(226,226,222));
			jButton8.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton8() - end");
		}
		return jButton8;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton9() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton9() - start");
		}

		if(jButton9 == null) {
			jButton9 = new JHighlightButton();
			jButton9.setText("Registrar");
			jButton9.setBackground(new java.awt.Color(226,226,222));
			jButton9.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton9() - end");
		}
		return jButton9;
	}

/**
* This method initializes jButton
* 
* @return javax.swing.JButton
*/
	private javax.swing.JButton getJButton10() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton10() - start");
		}

		if(jButton10 == null) {
			jButton10 = new JHighlightButton();
			jButton10.setText("Salir");
			jButton10.setBackground(new java.awt.Color(226,226,222));
			jButton10.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton10() - end");
		}
		return jButton10;
	}

	/**
	 * This method initializes jButton11
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton11() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton11() - start");
		}

		if(jButton11 == null) {
			jButton11 = new JHighlightButton();
			jButton11.setText("Registrar");
			jButton11.setBackground(new java.awt.Color(226,226,222));
			jButton11.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton11() - end");
		}
		return jButton11;
	}

	/**
	 * @return
	 */
	public javax.swing.JTextField getJTextField() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField() - start");
		}

		if(jTextField == null) {
			jTextField = new UpperCaseField();
			jTextField.setInputVerifier(new TextVerifier(50, " ABCDEFGHIJKLMNÑOPQRSTUVWXYZ."));
			jTextField.setPreferredSize(new java.awt.Dimension(150,25));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField() - end");
		}
		
		return jTextField;
	}

	/**
	 * @return
	 */
	public javax.swing.JTextField getJTextField1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField1() - start");
		}

		if(jTextField1 == null) {
			jTextField1 = new UpperCaseField();
			jTextField1.setInputVerifier(new TextVerifier(50, " ABCDEFGHIJKLMNÑOPQRSTUVWXYZ."));
			jTextField1.setPreferredSize(new java.awt.Dimension(150,25));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField1() - end");
		}		
		return jTextField1;
	}

	/**
	 * @return
	 */
	public javax.swing.JTextField getJTextField2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField2() - start");
		}

		if(jTextField2 == null) {
			jTextField2 = new UpperCaseField();
			jTextField2.setInputVerifier(new TextVerifier(50, " ABCDEFGHIJKLMNÑOPQRSTUVWXYZ."));
			jTextField2.setPreferredSize(new java.awt.Dimension(150,25));
		} 
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField2() - end");
		}		
		return jTextField2;
	}

	/**
	 * Código de área
	 * @return
	 */
	public javax.swing.JTextField getJTextField3() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField3() - start");
		}

		if(jTextField3 == null) {
			jTextField3 = new UpperCaseField();
			jTextField3.setInputVerifier(new TextVerifier(5, "1234567890.-"));
			jTextField3.setPreferredSize(new java.awt.Dimension(150,25));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField3() - end");
		}		
		return jTextField3;
	}

	/**
	 * Zona postal
	 * @return
	 */
	public javax.swing.JTextField getJTextField4() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField4() - start");
		}

		if(jTextField4 == null) {
			jTextField4 = new UpperCaseField();
			jTextField4.setInputVerifier(new TextVerifier(6, " ABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890.-"));
			jTextField4.setPreferredSize(new java.awt.Dimension(150,25));
		} 
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField4() - end");
		}		
		return jTextField4;
	}
	
	/**
	 * Zona postal
	 * @return
     */
	public javax.swing.JTextField getJTextField7() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField7() - start");
		}

		if(jTextField7 == null) {
			jTextField7 = new UpperCaseField();
			jTextField7.setInputVerifier(new TextVerifier(6, "1234567890"));
			jTextField7.setPreferredSize(new java.awt.Dimension(150,25));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField7() - end");
		}		
		return jTextField7;
	}
/**
 * @return
 */
	public javax.swing.JTextField getJTextField5() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField5() - start");
		}
	
		if(jTextField5 == null) {
			jTextField5 = new UpperCaseField();
			jTextField5.setInputVerifier(new TextVerifier(50, " ABCDEFGHIJKLMNÑOPQRSTUVWXYZ."));
			jTextField5.setPreferredSize(new java.awt.Dimension(150,25));
		}
	
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField5() - end");
		}
			
		return jTextField5;
	}

	/**
		 * @return
		 */
		public javax.swing.JTextField getJTextField6() {
			if (logger.isDebugEnabled()) {
				logger.debug("getJTextField6() - start");
			}

			if(jTextField6 == null) {
				jTextField6 = new UpperCaseField();
				jTextField6.setInputVerifier(new TextVerifier(50, " ABCDEFGHIJKLMNÑOPQRSTUVWXYZ."));
				jTextField6.setPreferredSize(new java.awt.Dimension(150,25));
			}

			if (logger.isDebugEnabled()) {
				logger.debug("getJTextField6() - end");
			}		
			return jTextField6;
		}
		
		/**
		 * Código de área
		 * @return
		 */
		public javax.swing.JTextField getJTextField8() {
			if (logger.isDebugEnabled()) {
				logger.debug("getJTextField8() - start");
			}

			if(jTextField8 == null) {
				jTextField8 = new UpperCaseField();
				jTextField8.setInputVerifier(new TextVerifier(5, "1234567890.-"));
				jTextField8.setPreferredSize(new java.awt.Dimension(150,25));
			}

			if (logger.isDebugEnabled()) {
				logger.debug("getJTextField8() - end");
			}		
			return jTextField8;
		}		


	public javax.swing.JTextField getJTextField9() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField1() - start");
		}

		if(jTextField9 == null) {
			jTextField9 = new UpperCaseField();
			jTextField9.setInputVerifier(new TextVerifier(50, " ABCDEFGHIJKLMNÑOPQRSTUVWXYZ."));
			jTextField9.setPreferredSize(new java.awt.Dimension(150,25));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField9() - end");
		}		
		return jTextField9;
	}		
		
	@SuppressWarnings("unused")
	private void agregarListeners() {
		if (logger.isDebugEnabled()) {
			logger.debug("agregarListeners() - start");
		}

		jButton.addKeyListener(this);
		jButton.addMouseListener(this);
		
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);

		jRadioButton.addKeyListener(this);
		jRadioButton.addMouseListener(this);

		jRadioButton1.addKeyListener(this);
		jRadioButton1.addMouseListener(this);

		jRadioButton2.addKeyListener(this);
		jRadioButton2.addMouseListener(this);

		if (logger.isDebugEnabled()) {
			logger.debug("agregarListeners() - end");
		}
	}
	
	@SuppressWarnings("unused")
	private void eliminarListeners() {
		if (logger.isDebugEnabled()) {
			logger.debug("eliminarListeners() - start");
		}

		jButton.removeKeyListener(this);
		jButton.removeMouseListener(this);
		
		jButton1.removeKeyListener(this);
		jButton1.removeMouseListener(this);

		if (logger.isDebugEnabled()) {
			logger.debug("eliminarListeners() - end");
		}
	}

	public void repintarMantenimiento()
	{
		if (logger.isDebugEnabled()) {
			logger.debug("repintarFactura() - start");
		}

		if (jPanel4.isVisible()){
			jPanel4.setVisible(false);
			jTextField.setText("");
		}
		if (jPanel8.isVisible()){
			jPanel8.setVisible(false);
			jTextField5.setText("");
		}	
		if (jPanel5.isVisible()){
			jPanel5.setVisible(false);
			jTextField2.setText("");
			jTextField4.setText("");
			jComboBox1.removeActionListener(this);
			this.jComboBox1.removeAllItems();
			this.jComboBox1.addItem("Seleccione uno");
			if(Sesion.estados != null)
				for (int i = 0; i < Sesion.estados.size(); i++)
					jComboBox1.addItem(((Estado)Sesion.estados.elementAt(i)).getDescripcion());
			jComboBox1.addActionListener(this);
		}
		if (jPanel10.isVisible()){
			jPanel10.setVisible(false);
			jTextField9.setText("");
			jTextField8.setText("");
			jComboBox8.removeActionListener(this);
			this.jComboBox8.removeAllItems();
			this.jComboBox8.addItem("Seleccione uno");
			if(Sesion.estados != null)
				for (int i = 0; i < Sesion.estados.size(); i++)
					jComboBox8.addItem(((Estado)Sesion.estados.elementAt(i)).getDescripcion());
			jComboBox8.addActionListener(this);
			Sesion.ciudades = null;
			Sesion.urbanizaciones = null;
			jComboBox6.removeActionListener(this);
			this.jComboBox6.removeAllItems();
			this.jComboBox6.addItem("Seleccione uno");
			jComboBox6.addActionListener(this);
			jComboBox9.removeActionListener(this);
			this.jComboBox9.removeAllItems();
			this.jComboBox9.addItem("Seleccione uno");
			jComboBox9.addActionListener(this);
		}		
		if (jPanel9.isVisible()){
			jTextField6.setText("");
			jTextField7.setText("");
			jPanel9.setVisible(false);
		}
		if (jPanel6.isVisible()){
			jPanel6.setVisible(false);	
			jTextField1.setText("");
			jTextField3.setText("");
		}
		
			
		
		try{
			MaquinaDeEstado.setEstadoCaja(Sesion.getCaja().getEstado());
			if (getJComboBox().getSelectedIndex() != 0)
			{
				if ((getJComboBox().getSelectedIndex() == 1) && (getJRadioButton().isSelected()))
				{
					getJPanel4().setVisible(true);
				} else if ((getJComboBox().getSelectedIndex() == 1) && (getJRadioButton1().isSelected()))
				{
					getJLabel11().setVisible(true);
					getJTextField5().setVisible(true);
					jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Modificar Estado", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
					getJButton9().setText("Registrar");
					getJPanel8().setVisible(true);
				} else if ((getJComboBox().getSelectedIndex() == 1) && (getJRadioButton2().isSelected()))
				{
					getJLabel11().setVisible(false);
					getJTextField5().setVisible(false);
					jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Eliminar Estado", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
					getJButton9().setText("Eliminar");
					getJPanel8().setVisible(true);
				} else if ((getJComboBox().getSelectedIndex() == 2) && (getJRadioButton().isSelected()))
				{
					getJPanel5().setVisible(true);
				} else if ((getJComboBox().getSelectedIndex() == 2) && (getJRadioButton1().isSelected()))
				{
					getJLabel14().setVisible(true);
					getJTextField6().setVisible(true);
					getJLabel15().setVisible(true);
					getJTextField7().setVisible(true);	
					jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Modificar Ciudad", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));					
					getJButton7().setText("Registrar");
					getJPanel9().setVisible(true);
				} else if ((getJComboBox().getSelectedIndex() == 2) && (getJRadioButton2().isSelected()))
				{
					getJLabel14().setVisible(false);
					getJTextField6().setVisible(false);
					getJLabel15().setVisible(false);
					getJTextField7().setVisible(false);	
					jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Eliminar Ciudad", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));				
					getJButton7().setText("Eliminar");
					getJPanel9().setVisible(true);
				} else if ((getJComboBox().getSelectedIndex() == 3) && (getJRadioButton().isSelected()))
				{
					getJPanel6().setVisible(true);
				} else if ((getJComboBox().getSelectedIndex() == 3) && (getJRadioButton1().isSelected()))
				{
					getJLabel19().setVisible(true);
					getJTextField9().setVisible(true);
					getJLabel18().setVisible(true);
					getJTextField8().setVisible(true);					
					jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Modificar Urbanización", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
					getJButton11().setText("Registrar");
					getJPanel10().setVisible(true);
				} else if ((getJComboBox().getSelectedIndex() == 3) && (getJRadioButton2().isSelected()))
				{
					getJLabel19().setVisible(false);
					getJTextField9().setVisible(false);
					getJLabel18().setVisible(false);
					getJTextField8().setVisible(false);
					jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Eliminar Urbanización", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
					getJButton11().setText("Eliminar");
					getJPanel10().setVisible(true);
				}						
			}
			if (servicio == false) //Mantenimiento desde menú de servicio
			{
				jButton.setText("Cancelar");
				jButton2.setText("Cancelar");
				jButton4.setText("Cancelar");
				jButton10.setText("Cancelar");
				jButton8.setText("Cancelar");
				jButton6.setText("Cancelar");
			} 
		} catch (Exception ex){
			
		}

		if (logger.isDebugEnabled()) {
			logger.debug("repintarFactura() - end");
		}		
	}


} 
