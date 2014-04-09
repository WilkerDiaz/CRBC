/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : VisualizarComandas.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : Agosto 26, 2004 - 10:09:56 AM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1
 * Fecha       : Agosto 26, 2004
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Implementación inicial
 * =============================================================================
 */
package com.becoblohm.cr.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JDialog;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.ProductoExcepcion;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.utiles.Control;
import com.becoblohm.cr.utiles.MensajesVentanas;
/**
 * @author vmedina
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Se comentaron variables sin uso y parametrizaron algunos contenedores
* Fecha: agosto 2011
*/
public class VisualizarComandas extends JDialog implements ComponentListener, KeyListener, MouseListener, ActionListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(VisualizarComandas.class);

	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JTextField jTextField = null;
	private javax.swing.JPanel jPanel6 = null;
	private javax.swing.JScrollPane jScrollPane = null;
	private javax.swing.JTable jTable = null;
	private javax.swing.JPanel jPanel7 = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JPanel jPanel8 = null;
	private javax.swing.JPanel jPanel9 = null;
	private javax.swing.JLabel jLabel11 = null;
	private int limiteInf = 0;
/*	private int casoConsulta;
	private ModeloTablaConsulta resultadoConsulta = new ModeloTablaConsulta();
	private String camposBusqueda[] = new String[]{"codproducto","codexterno","descripcioncorta","d.nombre","l.nombre","marca","modelo","referenciaproveedor","nombre","codafiliado"};//,"direccion"};
	private String nombresCamposBusqueda[] = new String[]{"Codigo","Cod. Externo","Descripción","Departamento","Línea/Sección","Marca","Modelo","Ref. Proveedor","Nombre","Cod. Afiliado"};//,"Dirección"};*/
	private String codigoSeleccionado[] = new String[2];
	
	private String identificador = new String();
	private Vector<Vector<Object>> comandas = new Vector<Vector<Object>>();
	private ModeloTablaComanda modeloTablaComanda = new ModeloTablaComanda();
	private Vector<Vector<Object>> productosFacturar = new Vector<Vector<Object>>(); 
	
	private javax.swing.JButton jButton2 = null;
	/**
	 * This is the default constructor
	 */
	public VisualizarComandas(Vector<Vector<Object>> xComandas) {
		super(MensajesVentanas.ventanaActiva);
		this.comandas = xComandas;
		initialize();
		modeloTablaComanda.llenarTabla(this.comandas);
		limiteInf = 0;
		codigoSeleccionado[0] = null;
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
		
		this.setSize(550, 369);
		this.setContentPane(getJContentPane());
		this.setUndecorated(true);
		this.setModal(true);
		this.setBackground(new java.awt.Color(226,226,222));
		
		jTextField.addKeyListener(this);
		
		jTable.addKeyListener(this);
		jTable.addMouseListener(this);
		
		jButton.addKeyListener(this);
		jButton.addMouseListener(this);
		
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);
		
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
			jContentPane.add(getJPanel8(), null);
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.RAISED));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJContentPane() - end");
		}
		return jContentPane;
	}

	/**
	 * Método actionPerformed
	 *
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("actionPerformed(ActionEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("actionPerformed(ActionEvent) - end");
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void keyPressed(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}

		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			dispose();
		}
		else if ((e.getKeyCode()==KeyEvent.VK_DOWN)&&(!e.getSource().equals(jTable))) {
			jTable.requestFocus();
			try {
				jTable.setRowSelectionInterval(jTable.getSelectedRow()+1,jTable.getSelectedRow()+1);
			} catch (Exception e1) {
				logger.error("keyPressed(KeyEvent)", e1);
			}
		}
		else if((e.getKeyCode() == KeyEvent.VK_SPACE)||(e.getKeyCode() == KeyEvent.VK_ENTER)){
			if(e.getSource().equals(jButton)){
				if(this.getProductosFacturar().size() > 0){
					dispose();
				}
				else if(MensajesVentanas.preguntarSiNo("Desea agregar todas las comandas") == 0){
					productosFacturar = comandas;
					dispose();			
				}
			}
			else if(e.getSource().equals(jButton1)){
				productosFacturar = null;
				dispose();
			}
			else if(e.getSource().equals(jButton2)){
				productosFacturar = comandas;
				dispose();
			}
			else if (e.getSource().equals(jTable)) {
				if (jTable.getSelectedRow()!=-1) {
					Vector<Object> xProducto = new Vector<Object>();
					xProducto.add(0, (String)jTable.getValueAt(jTable.getSelectedRow(),0));
					xProducto.add(1, (String)jTable.getValueAt(jTable.getSelectedRow(),1));
					xProducto.add(2, (String)jTable.getValueAt(jTable.getSelectedRow(),2));
					xProducto.add(3, Control.str2Float(jTable.getValueAt(jTable.getSelectedRow(),3).toString()));
					xProducto.add(4, new String(jTable.getValueAt(jTable.getSelectedRow(),4).toString()));
					if(Sesion.isUtilizarVendedor())
						xProducto.add(5, (String)jTable.getValueAt(jTable.getSelectedRow(),5));
					jTable.setValueAt("S", jTable.getSelectedRow(), 4);
					jTable.removeRowSelectionInterval(jTable.getSelectedRow(), jTable.getSelectedRow());
					productosFacturar.add(productosFacturar.size(), xProducto);	
				}
			}

			if (e.getKeyCode() == KeyEvent.VK_ENTER){
				if (e.getSource().equals(jTextField)){
					try {
						this.comandas = CR.meVenta.recuperarComanda(getJTextField().getText());
						this.repintarComandas();
					} catch (ConexionExcepcion e1) {
						logger.error("keyPressed(KeyEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
					}catch (ProductoExcepcion e1) {
						logger.error("keyPressed(KeyEvent) "+ e1.getMessage());
						MensajesVentanas.mensajeError(e1.getMensaje());
					} catch (ExcepcionCr e1) {
						logger.error("keyPressed(KeyEvent) ", e1);
						MensajesVentanas.mensajeError(e1.getMensaje());
					}				
				}
			}	
		}
		else if (e.getKeyCode()==KeyEvent.VK_TAB){
			if(e.getSource().equals(jTable))
				jButton.requestFocus();
			if(e.getSource().equals(jButton1))
				jTextField.requestFocus();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - end");
		}
	}

	/**
	 * Método repintarComandas.
	 */
	private void repintarComandas(){
		if (logger.isDebugEnabled()) {
			logger.debug("repintarComandas() - start");
		}

		try{
			modeloTablaComanda.llenarTabla(this.comandas);
		} catch (Exception ex){
			logger.error("repintarComandas()", ex);

			modeloTablaComanda.iniciarDatosTabla();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("repintarComandas() - end");
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
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void mouseReleased(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - start");
		}
		
		//Mapeo de MouseClicked sobre ACEPTAR
		if(e.getSource().equals(jButton)){
			if(this.getProductosFacturar().size() > 0){
				dispose();
			}
			else if(MensajesVentanas.preguntarSiNo("Desea agregar todas las comandas") == 0){
				productosFacturar = comandas;
				dispose();			
			}
		}
		//Mapeo de MouseClicked sobre CANCELAR
		else if(e.getSource().equals(jButton1)){
			productosFacturar = null;
			dispose();
		}
		else if(e.getSource().equals(jButton2)){
			productosFacturar = comandas;
			dispose();
		}
		else if (e.getSource().equals(jTable)) {
			if (jTable.getSelectedRow()!=-1) {
				Vector<Object> xProducto = new Vector<Object>();
				xProducto.add(0, (String)jTable.getValueAt(jTable.getSelectedRow(),0));
				xProducto.add(1, (String)jTable.getValueAt(jTable.getSelectedRow(),1));
				xProducto.add(2, (String)jTable.getValueAt(jTable.getSelectedRow(),2));
				xProducto.add(3, Control.str2Float(jTable.getValueAt(jTable.getSelectedRow(),3).toString()));
				xProducto.add(4, new String(jTable.getValueAt(jTable.getSelectedRow(),4).toString()));
				if(Sesion.isUtilizarVendedor())
					xProducto.add(5, (String)jTable.getValueAt(jTable.getSelectedRow(),5));
				jTable.setValueAt("S", jTable.getSelectedRow(), 4);
				jTable.removeRowSelectionInterval(jTable.getSelectedRow(), jTable.getSelectedRow());
				productosFacturar.add(productosFacturar.size(), xProducto);	
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
			javax.swing.border.TitledBorder ivjTitledBorder = javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray , 1) , "Item" , javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION , javax.swing.border.TitledBorder.DEFAULT_POSITION , new java.awt.Font("Dialog", java.awt.Font.BOLD, 12) , java.awt.Color.darkGray);
			ivjTitledBorder.setTitle("Identificador");
			jPanel.setLayout(null);
			jPanel.add(getJPanel2(), null);
			jPanel.setBorder(ivjTitledBorder);
			jPanel.setPreferredSize(new java.awt.Dimension(530,80));
			jPanel.setBackground(new java.awt.Color(242,242,238));
			
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel() - end");
		}
		return jPanel;
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
			jPanel2.setLayout(null);
			jPanel2.add(getJTextField(), null);
			jPanel2.add(getJButton2(), null);
			jPanel2.setBounds(18, 22, 484, 50);
			jPanel2.setPreferredSize(new java.awt.Dimension(140,50));
			jPanel2.setBackground(new java.awt.Color(242,242,238));
			jPanel2.setComponentOrientation(java.awt.ComponentOrientation.LEFT_TO_RIGHT);
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
			jTextField.setBounds(65, 14, 147, 20);
			jTextField.setPreferredSize(new java.awt.Dimension(100,20));
			jTextField.setComponentOrientation(java.awt.ComponentOrientation.LEFT_TO_RIGHT);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField() - end");
		}
		return jTextField;
	}
	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel6() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel6() - start");
		}

		if(jPanel6 == null) {
			jPanel6 = new javax.swing.JPanel();
			javax.swing.border.TitledBorder ivjTitledBorder1 = javax.swing.BorderFactory.createTitledBorder(null , "Resultados" , javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION , javax.swing.border.TitledBorder.DEFAULT_POSITION , new java.awt.Font("Dialog", java.awt.Font.BOLD, 12) , java.awt.Color.black);
			java.awt.FlowLayout layFlowLayout11 = new java.awt.FlowLayout();
			layFlowLayout11.setHgap(1);
			layFlowLayout11.setVgap(1);
			ivjTitledBorder1.setTitle("Detalle de productos");
			jPanel6.setLayout(layFlowLayout11);
			jPanel6.add(getJScrollPane(), null);
			jPanel6.setBorder(ivjTitledBorder1);
			jPanel6.setPreferredSize(new java.awt.Dimension(530,180));
			jPanel6.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel6() - end");
		}
		return jPanel6;
	}
	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private javax.swing.JTable getJTable() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTable() - start");
		}

		if(jTable == null) {
			jTable = new javax.swing.JTable(modeloTablaComanda);
			jTable.getTableHeader().setReorderingAllowed(false) ;
			jTable.getColumnModel().getColumn(0).setPreferredWidth(61);
			jTable.getColumnModel().getColumn(1).setPreferredWidth(81);
			jTable.getColumnModel().getColumn(2).setPreferredWidth(290);
			jTable.getColumnModel().getColumn(3).setPreferredWidth(60);
			jTable.getColumnModel().getColumn(4).setPreferredWidth(59);
			if (Sesion.isUtilizarVendedor())
				jTable.getColumnModel().getColumn(5).setPreferredWidth(59);
			jTable.setLocale(new java.util.Locale("es", "VE", ""));
			jTable.setRowHeight(jTable.getRowHeight()+3);
			jTable.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jTable.setBackground(new java.awt.Color(242,242,238));
			jTable.setRowSelectionAllowed(true);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTable() - end");
		}
		return jTable;
	}
	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private javax.swing.JScrollPane getJScrollPane() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJScrollPane() - start");
		}

		if(jScrollPane == null) {
			jScrollPane = new javax.swing.JScrollPane();
			jScrollPane.setViewportView(getJTable());
			jScrollPane.setPreferredSize(new java.awt.Dimension(520,130));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJScrollPane() - end");
		}
		return jScrollPane;
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
			java.awt.FlowLayout layFlowLayout22 = new java.awt.FlowLayout();
			layFlowLayout22.setAlignment(java.awt.FlowLayout.RIGHT);
			layFlowLayout22.setHgap(0);
			layFlowLayout22.setVgap(0);
			jPanel7.setLayout(layFlowLayout22);
			jPanel7.add(getJButton(), null);
			jPanel7.add(getJButton1(), null);
			jPanel7.setPreferredSize(new java.awt.Dimension(530,30));
			jPanel7.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel7() - end");
		}
		return jPanel7;
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
			jButton.setText("Aceptar");
			jButton.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jButton.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
			jButton.setBackground(new java.awt.Color(226,226,222));
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
			jButton1.setText("Cancelar");
			jButton1.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
			jButton1.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton1() - end");
		}
		return jButton1;
	}
	/**
	 * This method initializes jPanel8
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel8() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel8() - start");
		}

		if(jPanel8 == null) {
			jPanel8 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout12 = new java.awt.FlowLayout();
			layFlowLayout12.setAlignment(java.awt.FlowLayout.CENTER);
			layFlowLayout12.setVgap(0);
			layFlowLayout12.setHgap(0);
			jPanel8.setLayout(layFlowLayout12);
			jPanel8.add(getJPanel9(), null);
			jPanel8.add(getJPanel(), null);
			jPanel8.add(getJPanel6(), null);
			jPanel8.add(getJPanel7(), null);
			jPanel8.setPreferredSize(new java.awt.Dimension(530,330));
			jPanel8.setBackground(new java.awt.Color(242,242,238));
			jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel8() - end");
		}
		return jPanel8;
	}
	/**
	 * This method initializes jPanel9
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel9() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel9() - start");
		}

		if(jPanel9 == null) {
			jPanel9 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout13 = new java.awt.FlowLayout();
			layFlowLayout13.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout13.setVgap(5);
			layFlowLayout13.setHgap(5);
			jPanel9.setLayout(layFlowLayout13);
			jPanel9.add(getJLabel11(), null);
			jPanel9.setBackground(new java.awt.Color(69,107,127));
			jPanel9.setPreferredSize(new java.awt.Dimension(530,40));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel9() - end");
		}
		return jPanel9;
	}
	/**
	 * This method initializes jLabel11
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel11() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel11() - start");
		}

		if(jLabel11 == null) {
			jLabel11 = new javax.swing.JLabel();
			jLabel11.setText("Comandas");
			jLabel11.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel11.setForeground(java.awt.Color.white);
			jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/data_scroll.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel11() - end");
		}
		return jLabel11;
	}
	/**
	 * Método getCodigoSeleccionado
	 * 
	 * @return
	 * String
	 */
	public String[] getCodigoSeleccionado() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCodigoSeleccionado() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getCodigoSeleccionado() - end");
		}
		return codigoSeleccionado;
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
			jButton2.setBounds(265, 7, 139, 34);
			jButton2.setBackground(new java.awt.Color(226,226,222));
			jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/data_add.png")));
			jButton2.setText("Agregar todas");
			jButton2.addMouseListener(this);
			jButton2.addKeyListener(this);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton2() - end");
		}
		return jButton2;
	}

	/**
	 * Método setIdentificador
	 * 
	 * @param string
	 */
	public void setIdentificador(String string) {
		if (logger.isDebugEnabled()) {
			logger.debug("setIdentificador(String) - start");
		}

		identificador = string;
		this.getJTextField().setText(identificador);

		if (logger.isDebugEnabled()) {
			logger.debug("setIdentificador(String) - end");
		}
	}

	/**
	 * Método getProductosFacturar
	 * 
	 * @return Vector
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Vector<Object>> getProductosFacturar() {
		if (logger.isDebugEnabled()) {
			logger.debug("getProductosFacturar() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getProductosFacturar() - end");
		}
		return productosFacturar;
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

}  //  @jve:visual-info  decl-index=0 visual-constraint="66,45"