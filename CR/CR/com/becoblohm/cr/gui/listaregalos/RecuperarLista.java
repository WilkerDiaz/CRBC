/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui.listaregalos
 * Programa   : RecuperarLista.java
 * Creado por : rabreu
 * Creado en  : 01/06/2006
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
package com.becoblohm.cr.gui.listaregalos;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JDialog;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.gui.Consultas;
import com.becoblohm.cr.gui.JHighlightComboBox;
import com.becoblohm.cr.gui.UpperCaseField;
import com.becoblohm.cr.mediadoresbd.BaseDeDatosServicio;
import com.becoblohm.cr.utiles.MensajesVentanas;
import java.awt.Dimension;
import java.util.Vector;

/**
 * @author rabreu
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class RecuperarLista extends JDialog implements ActionListener, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Consultas.class);
	private javax.swing.JPanel jContentPane = null;
	private ModeloTablaLR tablaResultados = new ModeloTablaLR(2);
	private int casoConsulta, limiteInf;
	private String camposBusqueda[] = new String[]{"codlista","titular"};
	private String nombresCamposBusqueda[] = new String[]{"Cod. Lista","Titular","Fecha Evento"};
	private String seleccion = null;
	
	private javax.swing.JTextField jTextField = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JButton jButtonAceptar = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JButton jButtonCancelar = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JComboBox jComboBox = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JPanel jPanel6 = null;
	private javax.swing.JTable jTable = null;
	private javax.swing.JScrollPane jScrollPane = null;

	private javax.swing.JLabel jLabel = null;
	private javax.swing.JPanel jPanel5 = null;
	private javax.swing.JButton jButtonUp = null;
	private javax.swing.JButton jButtonDown = null;
	
	private Object[][] listasActivas = null;
	/**
	 * This is the default constructor
	 */
	public RecuperarLista() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		super();
		initialize();
		CR.me.verificarAutorizacion("LISTA DE REGALOS", "cargarListaRegalos");
		limiteInf = 0;
		casoConsulta = jComboBox.getSelectedIndex();
		listasActivas = obtenerListasActivas();
		ejecutarConsulta();
		agregarListeners();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(640, 445);
		this.setContentPane(getJContentPane());
		this.setUndecorated(true);
		this.setModal(true);
		this.setBackground(new java.awt.Color(226,226,222));
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setHgap(5);
			layFlowLayout1.setVgap(5);
			layFlowLayout1.setAlignment(java.awt.FlowLayout.CENTER);
			jContentPane.setLayout(layFlowLayout1);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJPanel1(), null);
			jContentPane.setBackground(new java.awt.Color(226,226,222));
			jContentPane.setPreferredSize(new Dimension(640, 395));
		}
		return jContentPane;
	}
	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField() {
		if(jTextField == null) {
			jTextField = new UpperCaseField();
			jTextField.setPreferredSize(new java.awt.Dimension(250,30));
		}
		return jTextField;
	}
	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel2() {
		if(jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setText("Recuperar Lista de Regalos");
			jLabel2.setPreferredSize(new java.awt.Dimension(300,36));
			jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
			jLabel2.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 16));
			jLabel2.setForeground(java.awt.Color.white);
			jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/environment_view.png")));
		}
		return jLabel2;
	}
	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel() {
		if(jPanel == null) {
			jPanel = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
			layFlowLayout2.setHgap(0);
			layFlowLayout2.setVgap(0);
			layFlowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel.setLayout(layFlowLayout2);
			jPanel.add(getJPanel2(), null);
			jPanel.add(getJPanel4(), null);
			jPanel.add(getJPanel3(), null);
			jPanel.add(getJPanel6(), null);
			jPanel.setPreferredSize(new Dimension(620, 365));
			jPanel.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel;
	}
	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel1() {
		if(jPanel1 == null) {
			jPanel1 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout11 = new java.awt.FlowLayout();
			layFlowLayout11.setHgap(0);
			layFlowLayout11.setVgap(0);
			layFlowLayout11.setAlignment(java.awt.FlowLayout.RIGHT);
			jPanel1.setLayout(layFlowLayout11);
			jPanel1.add(getJButtonAceptar(), null);
			jPanel1.add(getJButtonCancelar(), null);
			jPanel1.setPreferredSize(new java.awt.Dimension(620,30));
			jPanel1.setBackground(new java.awt.Color(226,226,222));
		}
		return jPanel1;
	}
	/**
	 * This method initializes jButtonAceptar
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButtonAceptar() {
		if(jButtonAceptar == null) {
			jButtonAceptar = new javax.swing.JButton();
			jButtonAceptar.setPreferredSize(new java.awt.Dimension(99,30));
			jButtonAceptar.setText("Aceptar");
			jButtonAceptar.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jButtonAceptar.setBackground(new java.awt.Color(226,226,222));
			jButtonAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
			jButtonAceptar.setMargin(new Insets(1,2,1,1));
		}
		return jButtonAceptar;
	}
	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel2() {
		if(jPanel2 == null) {
			jPanel2 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout21 = new java.awt.FlowLayout();
			layFlowLayout21.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout21.setHgap(5);
			layFlowLayout21.setVgap(2);
			jPanel2.setLayout(layFlowLayout21);
			jPanel2.add(getJLabel2(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(620,40));
			jPanel2.setBackground(new java.awt.Color(69,107,127));
		}
		return jPanel2;
	}
	/**
	 * This method initializes jButtonCancelar
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButtonCancelar() {
		if(jButtonCancelar == null) {
			jButtonCancelar = new javax.swing.JButton();
			jButtonCancelar.setText("Cancelar");
			jButtonCancelar.setPreferredSize(new java.awt.Dimension(99,30));
			jButtonCancelar.setBackground(new java.awt.Color(226,226,222));
			jButtonCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
			jButtonCancelar.setMargin(new Insets(1,2,1,1));
			jButtonCancelar.addActionListener(this);
			jButtonCancelar.addKeyListener(this);
		}
		return jButtonCancelar;
	}
	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel3() {
		if(jPanel3 == null) {
			jPanel3 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout3 = new java.awt.FlowLayout();
			layFlowLayout3.setHgap(5);
			layFlowLayout3.setVgap(0);
			layFlowLayout3.setAlignment(java.awt.FlowLayout.CENTER);
			jPanel3.setLayout(layFlowLayout3);
			jPanel3.add(getJTextField(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(290,65));
			jPanel3.setBackground(new java.awt.Color(242,242,238));
			jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Item", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
		}
		return jPanel3;
	}
	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel4() {
		if(jPanel4 == null) {
			jPanel4 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout4 = new java.awt.FlowLayout();
			layFlowLayout4.setHgap(0);
			layFlowLayout4.setVgap(0);
			jPanel4.setLayout(layFlowLayout4);
			jPanel4.add(getJLabel1(), null);
			jPanel4.add(getJComboBox(), null);
			jPanel4.setPreferredSize(new java.awt.Dimension(330,65));
			jPanel4.setBackground(new java.awt.Color(242,242,238));
			jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Criterio de busqueda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
		}
		return jPanel4;
	}
	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private javax.swing.JComboBox getJComboBox() {
		if(jComboBox == null) {
			jComboBox = new JHighlightComboBox();
			jComboBox.setPreferredSize(new java.awt.Dimension(200,30));
			jComboBox.setBackground(new java.awt.Color(226,226,222));
			jComboBox.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jComboBox.addItem("Número Lista");
			jComboBox.addItem("Titular");
			//jComboBox.addItem("Fecha Evento");
		}
		return jComboBox;
	}
	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel1() {
		if(jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setText("Buscar por: ");
			jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
		}
		return jLabel1;
	}
	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel6() {
		if(jPanel6 == null) {
			jPanel6 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout31 = new java.awt.FlowLayout();
			layFlowLayout31.setHgap(1);
			layFlowLayout31.setVgap(0);
			jPanel6.setLayout(layFlowLayout31);
			jPanel6.add(getJScrollPane(), null);
			jPanel6.add(getJPanel5(), null);
			jPanel6.setPreferredSize(new Dimension(620, 260));
			jPanel6.setBackground(new java.awt.Color(242,242,238));
			jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Resultados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
		}
		return jPanel6;
	}
	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private javax.swing.JTable getJTable() {
		if(jTable == null) {
			jTable = new javax.swing.JTable(tablaResultados);
			jTable.getTableHeader().setReorderingAllowed(false) ;
			jTable.getColumnModel().getColumn(0).setPreferredWidth(25);
			jTable.getColumnModel().getColumn(1).setPreferredWidth(130);
			jTable.getColumnModel().getColumn(2).setPreferredWidth(130);
			jTable.getColumnModel().getColumn(3).setPreferredWidth(45);
			jTable.getColumnModel().getColumn(4).setPreferredWidth(45);
			jTable.getColumnModel().getColumn(5).setPreferredWidth(10);
			jTable.setBackground(new java.awt.Color(242,242,238));
			jTable.setLocale(new java.util.Locale("es", "VE", ""));
			jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jTable.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jTable;
	}
	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private javax.swing.JScrollPane getJScrollPane() {
		if(jScrollPane == null) {
			jScrollPane = new javax.swing.JScrollPane();
			jScrollPane.setViewportView(getJTable());
			jScrollPane.setPreferredSize(new Dimension(583, 226));
			jScrollPane.setBackground(new java.awt.Color(242,242,238));
			jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		if(jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setText("");
			jLabel.setPreferredSize(new Dimension(25, 172));
			jLabel.setBackground(new java.awt.Color(242,242,238));
		}
		return jLabel;
	}
	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel5() {
		if(jPanel5 == null) {
			jPanel5 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout12 = new java.awt.FlowLayout();
			layFlowLayout12.setHgap(0);
			layFlowLayout12.setVgap(0);
			jPanel5.setLayout(layFlowLayout12);
			jPanel5.add(getJButtonUp(), null);
			jPanel5.add(getJLabel(), null);
			jPanel5.add(getJButtonDown(), null);
			jPanel5.setPreferredSize(new Dimension(25, 225));
			jPanel5.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel5;
	}
	/**
	 * This method initializes jButtonUp
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButtonUp() {
		if(jButtonUp == null) {
			jButtonUp = new javax.swing.JButton();
			jButtonUp.setPreferredSize(new Dimension(25, 28));
			jButtonUp.setBackground(new java.awt.Color(242,242,238));
			jButtonUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix24x24/nav_up_blue.png")));
			jButtonUp.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		}
		return jButtonUp;
	}
	/**
	 * This method initializes jButtonDown
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButtonDown() {
		if(jButtonDown == null) {
			jButtonDown = new javax.swing.JButton();
			jButtonDown.setPreferredSize(new Dimension(25, 28));
			jButtonDown.setBackground(new java.awt.Color(242,242,238));
			jButtonDown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix24x24/nav_down_blue.png")));
			jButtonDown.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		}
		return jButtonDown;
	}
	/* (no Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("keyPressed(KeyEvent) - start");
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			this.dispose();
		else if (e.getKeyCode() == KeyEvent.VK_TAB){
			if(e.getSource().equals(jTable))
				jButtonUp.requestFocus();
			if(e.getSource().equals(jButtonUp))
				jButtonDown.requestFocus();
			if(e.getSource().equals(jButtonDown))
				jButtonAceptar.requestFocus();
			if(e.getSource().equals(jButtonCancelar))
				jComboBox.requestFocus();
		} else if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
			if (tablaResultados.getRowCount() == 13)
				limiteInf += 13;
			ejecutarConsulta();
		} else if (e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
			limiteInf = (limiteInf>13) ? limiteInf-13 : 0;
			ejecutarConsulta();
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			if((!e.getSource().equals(jTable))&&(!e.getSource().equals(jComboBox)))
				jTable.requestFocus();
			else if (e.getSource().equals(jTable))
				if (jTable.getSelectedRow() == 0) {
					limiteInf = (limiteInf>1) ? limiteInf-1 : 0;;
					ejecutarConsulta();
					jTable.setRowSelectionInterval(0, 0);
				}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if ((!e.getSource().equals(jTable))&&(!e.getSource().equals(jComboBox))) {
				jTable.requestFocus();
				if (jTable.getSelectedRow() == 13) {
					limiteInf += 1;
					ejecutarConsulta();
					jTable.setRowSelectionInterval(jTable.getRowCount()-1, jTable.getRowCount()-1);
				} else if (e.getSource().equals(jTable))
					jTable.setRowSelectionInterval(jTable.getSelectedRow()+1, jTable.getSelectedRow()+1);
			} else if (jTable.getSelectedRow() == 13) {
				limiteInf += 1;
				ejecutarConsulta();
				jTable.setRowSelectionInterval(jTable.getRowCount()-1, jTable.getRowCount()-1);
			}
		} else if(e.getKeyCode() == KeyEvent.VK_ENTER)
			if ((e.getSource().equals(jTable)) || (e.getSource().equals(jTextField))) {
				if (jTable.getSelectedRow() != -1) {
					seleccion = jTable.getValueAt(jTable.getSelectedRow(),0).toString().trim(); // NumLista
					this.dispose();
				} else
					MensajesVentanas.aviso("Debe seleccionar un item de la lista");
			} else if (e.getSource().equals(jComboBox))
				jTextField.requestFocus();
		if (logger.isDebugEnabled())
			logger.debug("keyPressed(KeyEvent) - end");
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
		if (e.getSource().equals(jTextField)) {
			limiteInf = 0;
			ejecutarConsulta(Character.toUpperCase(e.getKeyChar()));
		}
	}	
	/* (no Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(jComboBox)) {
			casoConsulta = jComboBox.getSelectedIndex();
			limiteInf = 0;
			ejecutarConsulta();
		}
		else if(e.getSource().equals(jButtonAceptar)){
			if (jTable.getSelectedRow() != -1) {
				seleccion = jTable.getValueAt(jTable.getSelectedRow(),0).toString().trim();
				this.dispose();
			} else
				MensajesVentanas.aviso("Debe seleccionar un item de la lista");
		}
		else if(e.getSource().equals(jButtonCancelar))
			this.dispose();
		else if (e.getSource().equals(jButtonDown)) {
			if (tablaResultados.getRowCount()==13)
				limiteInf += 13;
			ejecutarConsulta();
		}
		else if (e.getSource().equals(jButtonUp)) {
			limiteInf = (limiteInf>13) ? limiteInf-13 : 0;
			ejecutarConsulta();
		}
	}
	/**
	 * @param tecla
	 */
	private void ejecutarConsulta(char tecla) {
		if (logger.isDebugEnabled())
			logger.debug("ejecutarConsulta(char) - start");
		Integer i = new Integer(tecla);

		if ((i.intValue()>=32 && i.intValue()<=126) || i.intValue() == 10) {
			//tablaResultados.refrescarDatos(BaseDeDatosServicio.recuperarListas(jTextField.getText() + String.valueOf(tecla),camposBusqueda[casoConsulta],limiteInf,nombresCamposBusqueda[casoConsulta]));
			tablaResultados.refrescarDatos(ubicarResultados(jTextField.getText() + String.valueOf(tecla),camposBusqueda[casoConsulta],/*limiteInf,*/nombresCamposBusqueda[casoConsulta]));
		} else {
			//tablaResultados.refrescarDatos(BaseDeDatosServicio.recuperarListas(jTextField.getText().substring(0,jTextField.getText().length()),camposBusqueda[casoConsulta],limiteInf,nombresCamposBusqueda[casoConsulta]));
			tablaResultados.refrescarDatos(ubicarResultados(jTextField.getText().substring(0,jTextField.getText().length()),camposBusqueda[casoConsulta],/*limiteInf,*/nombresCamposBusqueda[casoConsulta]));
		}
		if (jTable.getRowCount()>0)
			jTable.setRowSelectionInterval(0,0);

		if (logger.isDebugEnabled())
			logger.debug("ejecutarConsulta(char) - end");
		
	}
	/**
	 * Método ejecutarConsulta
	 */
	private void ejecutarConsulta() {
		//tablaResultados.refrescarDatos(BaseDeDatosServicio. recuperarListas(jTextField.getText(),camposBusqueda[casoConsulta],limiteInf,nombresCamposBusqueda[casoConsulta]));
		tablaResultados.refrescarDatos(ubicarResultados(jTextField.getText(),camposBusqueda[casoConsulta],/*limiteInf,*/nombresCamposBusqueda[casoConsulta]));

		if (jTable.getRowCount()>0)
			jTable.setRowSelectionInterval(0,0);
	}
	/**
	 * Método obtenerListasActivas
	 */
	private Object[][] obtenerListasActivas() {
		return BaseDeDatosServicio.recuperarListas(jTextField.getText(),camposBusqueda[casoConsulta],nombresCamposBusqueda[casoConsulta]);
	}
	
	public String getCodLista(){
		return seleccion;
	}
	
	public void agregarListeners(){
		jButtonAceptar.addActionListener(this);
		jButtonAceptar.addKeyListener(this);
		
		jButtonDown.addKeyListener(this);
		jButtonDown.addActionListener(this);
		
		jButtonUp.addKeyListener(this);
		jButtonUp.addActionListener(this);
		
		jComboBox.addActionListener(this);
		jComboBox.addKeyListener(this);
		
		jTable.addKeyListener(this);
		
		jTextField.addActionListener(this);
		jTextField.addKeyListener(this);
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private Object[][] ubicarResultados(String item, String campoBusqueda, String nombreColumna){
		Object datos[][] = null;
		Vector<String> datos_res = new Vector<String>();
		int numFilas, numColumnas = 6;
		
		int seleccionados = 0;
		if(campoBusqueda.toLowerCase().equals("titular")){
			for (int i=limiteInf; i<listasActivas.length && seleccionados<13; i++) {
				if (((String)listasActivas[i][1]).toLowerCase().indexOf(item.toLowerCase())>=0 
						|| ((String)listasActivas[i][2]).toLowerCase().indexOf(item.toLowerCase())>=0) {
					datos_res.addElement((String)listasActivas[i][0]);
					datos_res.addElement((String)listasActivas[i][1]);
					datos_res.addElement((String)listasActivas[i][2]);
					datos_res.addElement((String)listasActivas[i][3]);
					datos_res.addElement((String)listasActivas[i][4]);
					datos_res.addElement((String)listasActivas[i][5]);
					seleccionados++;
				}
			}
		}else{
			for (int i=limiteInf; i<listasActivas.length && seleccionados<13; i++) {
				if (((String)listasActivas[i][0]).toLowerCase().indexOf(item.toLowerCase())>=0) {
					datos_res.addElement((String)listasActivas[i][0]);
					datos_res.addElement((String)listasActivas[i][1]);
					datos_res.addElement((String)listasActivas[i][2]);
					datos_res.addElement((String)listasActivas[i][3]);
					datos_res.addElement((String)listasActivas[i][4]);
					datos_res.addElement((String)listasActivas[i][5]);
					seleccionados++;
				}
			}
		}

		datos_res.trimToSize();
		numFilas = datos_res.size()/numColumnas;
		datos = new Object[numFilas][numColumnas];
		int j=0,k=0;
		for(int i=0;i<datos_res.size();i++){
			datos[j][k] = datos_res.get(i);
			k++;
			if((i+1)%numColumnas == 0){
				j++;
				k=0;
			}
		}

		return datos;
	}
}
