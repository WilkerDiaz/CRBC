/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui.listaregalos
 * Programa   : AsignarCliente.java
 * Creado por : Victor Medina <linux@epa.com.ve>
 * Creado en  : Mar 2, 2004 - 8:16:56 AM
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

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.gui.JHighlightComboBox;
import com.becoblohm.cr.gui.ModeloTablaConsulta;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.utiles.MensajesVentanas;
/**
 * @author vmedina
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AsignarCliente extends JDialog implements ComponentListener, KeyListener, MouseListener, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AsignarCliente.class);

	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JTextField jTextField = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JPanel jPanel6 = null;
	private javax.swing.JScrollPane jScrollPane = null;
	private javax.swing.JTable jTable = null;
	private javax.swing.JPanel jPanel7 = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JPanel jPanel8 = null;
	private javax.swing.JPanel jPanel9 = null;
	private javax.swing.JLabel jLabel11 = null;
	private javax.swing.JComboBox jComboBox = null;
	
	private int limiteInf = 0;
	private int casoConsulta;
	private ModeloTablaConsulta resultadoConsulta = new ModeloTablaConsulta();
	private String camposBusqueda[] = new String[]{"nombre","codafiliado"};//,"direccion"};
	private String nombresCamposBusqueda[] = new String[]{"Nombre","Cod. Afiliado"};//,"Dirección"};
	private String codigoSeleccionado[] = new String[2];
	
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JButton jButton2 = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JButton jButton3 = null;
	/**
	 * This is the default constructor
	 */
	public AsignarCliente() {
		super(MensajesVentanas.ventanaActiva);
		initialize();
		limiteInf = 0;
		casoConsulta = jComboBox.getSelectedIndex();
		codigoSeleccionado[0] = null;
		ejecutarConsulta();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		if (logger.isDebugEnabled())
			logger.debug("initialize() - start");
		
		this.setSize(670, 422);
		this.setContentPane(getJContentPane());
		this.setUndecorated(true);
		this.setModal(true);
		this.setBackground(new java.awt.Color(226,226,222));
		this.addComponentListener(this);
		
		jTextField.addKeyListener(this);
		
		jTable.addKeyListener(this);
		jTable.addMouseListener(this);
		
		jButton.addKeyListener(this);
		jButton.addMouseListener(this);
		
		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);
		
		jButton2.addKeyListener(this);
		jButton2.addMouseListener(this);
		
		jButton3.addKeyListener(this);
		jButton3.addMouseListener(this);
		
		jComboBox.addActionListener(this);
		jComboBox.addKeyListener(this);
		
		if (logger.isDebugEnabled())
			logger.debug("initialize() - end");
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (logger.isDebugEnabled())
			logger.debug("getJContentPane() - start");

		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new FlowLayout());
			jContentPane.add(getJPanel8(), null);
			jContentPane.add(getJPanel7(), null);
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(BevelBorder.RAISED));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJContentPane() - end");
		return jContentPane;
	}

	/**
	 * Método actionPerformed
	 *
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("actionPerformed(ActionEvent) - start");

		if (e.getSource().equals(jComboBox)) {
			// Aqui viene el cambio de tipo de busqueda
			casoConsulta = jComboBox.getSelectedIndex();
			limiteInf = 0;
			ejecutarConsulta();
		}

		if (logger.isDebugEnabled())
			logger.debug("actionPerformed(ActionEvent) - end");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("keyPressed(KeyEvent) - start");
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			dispose();
		else if (e.getKeyCode()==KeyEvent.VK_UP) {
			if((!e.getSource().equals(jTable))&&(!e.getSource().equals(jComboBox)))
				jTable.requestFocus();
			else if (jTable.getSelectedRow()==0) {
				limiteInf = (limiteInf>1) ? limiteInf-1 : 0;;
				ejecutarConsulta();
				jTable.setRowSelectionInterval(0, 0);
			}
		}

		else if (e.getKeyCode()==KeyEvent.VK_DOWN) {
			if ((!e.getSource().equals(jTable))&&(!e.getSource().equals(jComboBox))) {
				jTable.requestFocus();
				if (jTable.getSelectedRow()==9) {
					limiteInf += 1;
					ejecutarConsulta();
					jTable.setRowSelectionInterval(jTable.getRowCount()-1, jTable.getRowCount()-1);
				} else
					jTable.setRowSelectionInterval(jTable.getSelectedRow()+1, jTable.getSelectedRow()+1);
			} else if (jTable.getSelectedRow()==9) {
				limiteInf += 1;
				ejecutarConsulta();
				jTable.setRowSelectionInterval(jTable.getRowCount()-1, jTable.getRowCount()-1);
			}
		}
		else if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(e.getSource().equals(jButton)){
				// Boton de Aceptar
				if (jTable.getSelectedRow()!=-1) {
					codigoSeleccionado[0] = String.valueOf(casoConsulta);
					codigoSeleccionado[1] = jTable.getValueAt(jTable.getSelectedRow(),0).toString().trim();
				} else
					codigoSeleccionado[0] = null;
				dispose();
			}
			
			else if(e.getSource().equals(jButton1))
				dispose();
			else if (e.getSource().equals(jButton3)) {
				if (resultadoConsulta.getRowCount()==10)
					limiteInf += 10;
				ejecutarConsulta();
			}
			
			else if (e.getSource().equals(jButton2)) {
				limiteInf = (limiteInf>10) ? limiteInf-10 : 0;
				ejecutarConsulta();
			}
			
			else if (e.getSource().equals(jComboBox))
				jTextField.requestFocus();
			else if ((e.getSource().equals(jTable))||(e.getSource().equals(jTextField)))
				if (jTable.getSelectedRow()!=-1) {
					codigoSeleccionado[0] = String.valueOf(casoConsulta);
					codigoSeleccionado[1] = jTable.getValueAt(jTable.getSelectedRow(),0).toString().trim();
					dispose();
				} else
					MensajesVentanas.aviso("Debe seleccionar un item de la lista");
		}
		
		else if(e.getKeyCode() == KeyEvent.VK_SPACE){
			if(e.getSource().equals(jButton)){
				// Boton de Aceptar
				if (jTable.getSelectedRow()!=-1) {
					codigoSeleccionado[0] = String.valueOf(casoConsulta);
					codigoSeleccionado[1] = jTable.getValueAt(jTable.getSelectedRow(),0).toString().trim();
				} else
					codigoSeleccionado[0] = null;
				dispose();
			}
			
			else if(e.getSource().equals(jButton1))
				dispose();
			else if (e.getSource().equals(jButton3)) {
				if (resultadoConsulta.getRowCount()==10)
					limiteInf += 10;
				ejecutarConsulta();
			}
			
			else if (e.getSource().equals(jButton2)) {
				limiteInf = (limiteInf>10) ? limiteInf-10 : 0;
				ejecutarConsulta();
			}
			
			else if (e.getSource().equals(jComboBox))
				jTextField.requestFocus();
			else if (e.getSource().equals(jTable))
				if (jTable.getSelectedRow()!=-1) {
					codigoSeleccionado[0] = String.valueOf(casoConsulta);
					codigoSeleccionado[1] = jTable.getValueAt(jTable.getSelectedRow(),0).toString().trim();
					dispose();
				} else
					MensajesVentanas.aviso("Debe seleccionar un item de la lista");
		}

		else if (e.getKeyCode()==KeyEvent.VK_PAGE_DOWN) {
			if (resultadoConsulta.getRowCount()==10)
				limiteInf += 10;
			ejecutarConsulta();
		}
			
		else if (e.getKeyCode()==KeyEvent.VK_PAGE_UP) {
			limiteInf = (limiteInf>10) ? limiteInf-10 : 0;
			ejecutarConsulta();
		}
		
		else if (e.getKeyCode()==KeyEvent.VK_TAB){
			if(e.getSource().equals(jTable))
				jButton2.requestFocus();
			if(e.getSource().equals(jButton2))
				jButton3.requestFocus();
			if(e.getSource().equals(jButton3))
				jButton.requestFocus();
			if(e.getSource().equals(jButton1))
				jComboBox.requestFocus();
		}

		if (logger.isDebugEnabled())
			logger.debug("keyPressed(KeyEvent) - end");
	}
	/**
	 * Método ejecutarConsulta
	 * 
	 * @param tecla
	 */
	private void ejecutarConsulta(char tecla) {

		Integer i = new Integer(tecla);

		try {
			CR.me.consultaCliente();
			// Consulta de Clientes
			if (i.intValue()>=32 && i.intValue()<=126)
				resultadoConsulta.cambiarDatosTabla(MediadorBD.buscarElemento(jTextField.getText() + String.valueOf(tecla),camposBusqueda[casoConsulta],limiteInf,2,nombresCamposBusqueda[casoConsulta]));
			else
				if ((i.intValue()==8)&&(jTextField.getText().length()>0))
					resultadoConsulta.cambiarDatosTabla(MediadorBD.buscarElemento(jTextField.getText().substring(0,jTextField.getText().length()-1),camposBusqueda[casoConsulta],limiteInf,2,nombresCamposBusqueda[casoConsulta]));
				else
					resultadoConsulta.cambiarDatosTabla(MediadorBD.buscarElemento(jTextField.getText(),camposBusqueda[casoConsulta],limiteInf,2,nombresCamposBusqueda[casoConsulta]));
		} catch (ExcepcionCr e1) {
				logger.error("keyPressed(KeyEvent)", e1);
				MensajesVentanas.mensajeError(e1.getMensaje());
		} catch (ConexionExcepcion e1) {
				logger.error("keyPressed(KeyEvent)", e1);
				MensajesVentanas.mensajeError(e1.getMensaje());
		}
		
		if (jTable.getRowCount()>0)
			jTable.setRowSelectionInterval(0,0);

		if (logger.isDebugEnabled())
			logger.debug("ejecutarConsulta(char) - end");
	}

	/**
	 * Método ejecutarConsulta
	 * 
	 * 
	 */
	private void ejecutarConsulta() {
		try {
			CR.me.consultaCliente();
			// Consulta de Clientes
			resultadoConsulta.cambiarDatosTabla(MediadorBD.buscarElemento(jTextField.getText(),camposBusqueda[casoConsulta],limiteInf,2,nombresCamposBusqueda[casoConsulta]));
		} catch (ExcepcionCr e1) {
				logger.error("keyPressed(KeyEvent)", e1);
				MensajesVentanas.mensajeError(e1.getMensaje());
		} catch (ConexionExcepcion e1) {
				logger.error("keyPressed(KeyEvent)", e1);
				MensajesVentanas.mensajeError(e1.getMensaje());
		}

		if (jTable.getRowCount()>0)
			jTable.setRowSelectionInterval(0,0);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("keyReleased(KeyEvent) - start");
		
		if (logger.isDebugEnabled())
			logger.debug("keyReleased(KeyEvent) - end");
	}
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("keyTyped(KeyEvent) - start");

		if (e.getSource().equals(jTextField)) {
			limiteInf = 0;
			ejecutarConsulta(e.getKeyChar());
		}

		if (logger.isDebugEnabled())
			logger.debug("keyTyped(KeyEvent) - end");
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("mouseClicked(MouseEvent) - start");
		
		//Mapeo de MouseClicked sobre ACEPTAR
		if(e.getSource().equals(jButton)){
			// Boton de Aceptar
			if (jTable.getSelectedRow()!=-1) {
				codigoSeleccionado[0] = String.valueOf(casoConsulta);
				codigoSeleccionado[1] = jTable.getValueAt(jTable.getSelectedRow(),0).toString().trim();
			} else
				codigoSeleccionado[0] = null;
			dispose();
		}
		
		//Mapeo de MouseClicked sobre CANCELAR
		else if(e.getSource().equals(jButton1))
			dispose();
		else if (e.getSource().equals(jButton3)) {
			if (resultadoConsulta.getRowCount()==10)
				limiteInf += 10;
			ejecutarConsulta();
		}
			
		else if (e.getSource().equals(jButton2)) {
			limiteInf = (limiteInf>10) ? limiteInf-10 : 0;
			ejecutarConsulta();
		}

		if (logger.isDebugEnabled())
			logger.debug("mouseClicked(MouseEvent) - end");
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("mouseEntered(MouseEvent) - start");
		
		if (logger.isDebugEnabled())
			logger.debug("mouseEntered(MouseEvent) - end");
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("mouseExited(MouseEvent) - start");
		
		if (logger.isDebugEnabled())
			logger.debug("mouseExited(MouseEvent) - end");
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("mousePressed(MouseEvent) - start");
		
		if (logger.isDebugEnabled())
			logger.debug("mousePressed(MouseEvent) - end");
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("mouseReleased(MouseEvent) - start");
		
		if (logger.isDebugEnabled())
			logger.debug("mouseReleased(MouseEvent) - end");
	}
	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel() {
		if (logger.isDebugEnabled())
			logger.debug("getJPanel() - start");

		if(jPanel == null) {
			jPanel = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setHgap(1);
			layFlowLayout1.setVgap(1);
			layFlowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel.setLayout(layFlowLayout1);
			jPanel.add(getJPanel2(), null);
			jPanel.setPreferredSize(new java.awt.Dimension(215,80));
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1), "Item", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.darkGray));
			jPanel.setBackground(new java.awt.Color(242,242,238));
			
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel() - end");
		return jPanel;
	}
	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel1() {
		if (logger.isDebugEnabled())
			logger.debug("getJPanel1() - start");

		if(jPanel1 == null) {
			jPanel1 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
			layFlowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout2.setHgap(5);
			layFlowLayout2.setVgap(10);
			jPanel1.setLayout(layFlowLayout2);
			jPanel1.add(getJLabel(), null);
			jPanel1.add(getJComboBox(), null);
			jPanel1.setPreferredSize(new java.awt.Dimension(400,80));
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1), "Criterios de Búsqueda Disponibles", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
			jPanel1.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel1() - end");
		return jPanel1;
	}
	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel2() {
		if (logger.isDebugEnabled())
			logger.debug("getJPanel2() - start");

		if(jPanel2 == null) {
			jPanel2 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout14 = new java.awt.FlowLayout();
			layFlowLayout14.setVgap(10);
			jPanel2.setLayout(layFlowLayout14);
			jPanel2.add(getJTextField(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(190,50));
			jPanel2.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel2() - end");
		return jPanel2;
	}
	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField() {
		if (logger.isDebugEnabled())
			logger.debug("getJTextField() - start");

		if(jTextField == null) {
			jTextField = new javax.swing.JTextField();
			jTextField.setPreferredSize(new java.awt.Dimension(185,30));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJTextField() - end");
		return jTextField;
	}
	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		if (logger.isDebugEnabled())
			logger.debug("getJLabel() - start");

		if(jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setText("Buscando Por: ");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJLabel() - end");
		return jLabel;
	}
	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel6() {
		if (logger.isDebugEnabled())
			logger.debug("getJPanel6() - start");

		if(jPanel6 == null) {
			jPanel6 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout11 = new java.awt.FlowLayout();
			layFlowLayout11.setHgap(1);
			layFlowLayout11.setVgap(1);
			jPanel6.setLayout(layFlowLayout11);
			jPanel6.add(getJScrollPane(), null);
			jPanel6.add(getJPanel3(), null);
			jPanel6.setPreferredSize(new java.awt.Dimension(615,205));
			jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Resultados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
			jPanel6.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel6() - end");
		return jPanel6;
	}
	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private javax.swing.JTable getJTable() {
		if (logger.isDebugEnabled())
			logger.debug("getJTable() - start");

		if(jTable == null) {
			jTable = new javax.swing.JTable(resultadoConsulta);
			jTable.getTableHeader().setReorderingAllowed(false) ;
			jTable.setLocale(new java.util.Locale("es", "VE", ""));
			jTable.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jTable.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJTable() - end");
		return jTable;
	}
	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private javax.swing.JScrollPane getJScrollPane() {
		if (logger.isDebugEnabled())
			logger.debug("getJScrollPane() - start");

		if(jScrollPane == null) {
			jScrollPane = new javax.swing.JScrollPane();
			jScrollPane.setViewportView(getJTable());
			jScrollPane.setPreferredSize(new java.awt.Dimension(575,180));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJScrollPane() - end");
		return jScrollPane;
	}
	/**
	 * This method initializes jPanel7
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel7() {
		if (logger.isDebugEnabled())
			logger.debug("getJPanel7() - start");

		if(jPanel7 == null) {
			jPanel7 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout22 = new java.awt.FlowLayout();
			layFlowLayout22.setAlignment(java.awt.FlowLayout.RIGHT);
			layFlowLayout22.setHgap(0);
			layFlowLayout22.setVgap(0);
			jPanel7.setLayout(layFlowLayout22);
			jPanel7.add(getJButton(), null);
			jPanel7.add(getJButton1(), null);
			jPanel7.setPreferredSize(new java.awt.Dimension(640,35));
			jPanel7.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel7() - end");
		return jPanel7;
	}
	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton() {
		if (logger.isDebugEnabled())
			logger.debug("getJButton() - start");

		if(jButton == null) {
			jButton = new JHighlightButton();
			jButton.setText("Aceptar");
			jButton.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jButton.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
			jButton.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJButton() - end");
		return jButton;
	}
	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton1() {
		if (logger.isDebugEnabled())
			logger.debug("getJButton1() - start");

		if(jButton1 == null) {
			jButton1 = new JHighlightButton();
			jButton1.setText("Cancelar");
			jButton1.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
			jButton1.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJButton1() - end");
		return jButton1;
	}
	/**
	 * This method initializes jPanel8
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel8() {
		if (logger.isDebugEnabled())
			logger.debug("getJPanel8() - start");

		if(jPanel8 == null) {
			jPanel8 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout12 = new java.awt.FlowLayout();
			layFlowLayout12.setAlignment(java.awt.FlowLayout.CENTER);
			layFlowLayout12.setVgap(0);
			layFlowLayout12.setHgap(0);
			jPanel8.setLayout(layFlowLayout12);
			jPanel8.add(getJPanel9(), null);
			jPanel8.add(getJPanel1(), null);
			jPanel8.add(getJPanel(), null);
			jPanel8.add(getJPanel6(), null);
			jPanel8.setPreferredSize(new java.awt.Dimension(640,335));
			jPanel8.setBackground(new java.awt.Color(242,242,238));
			jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel8() - end");
		return jPanel8;
	}
	/**
	 * This method initializes jPanel9
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel9() {
		if (logger.isDebugEnabled())
			logger.debug("getJPanel9() - start");

		if(jPanel9 == null) {
			jPanel9 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout13 = new java.awt.FlowLayout();
			layFlowLayout13.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout13.setVgap(5);
			layFlowLayout13.setHgap(5);
			jPanel9.setLayout(layFlowLayout13);
			jPanel9.add(getJLabel11(), null);
			jPanel9.setBackground(new java.awt.Color(69,107,127));
			jPanel9.setPreferredSize(new java.awt.Dimension(640,40));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel9() - end");
		return jPanel9;
	}
	/**
	 * This method initializes jLabel11
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel11() {
		if (logger.isDebugEnabled())
			logger.debug("getJLabel11() - start");

		if(jLabel11 == null) {
			jLabel11 = new javax.swing.JLabel();
			jLabel11.setText("Consultas");
			jLabel11.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel11.setForeground(java.awt.Color.white);
			jLabel11.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/id_card_view.png")));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJLabel11() - end");
		return jLabel11;
	}
	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private javax.swing.JComboBox getJComboBox() {
		if (logger.isDebugEnabled())
			logger.debug("getJComboBox() - start");

		if(jComboBox == null) {
			jComboBox = new JHighlightComboBox();
			jComboBox.setPreferredSize(new java.awt.Dimension(270,30));
			jComboBox.setBackground(new java.awt.Color(226,226,222));
			jComboBox.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
//			jComboBox.addItem("Producto por Código Interno");
//			jComboBox.addItem("Producto por Código Externo");
//			jComboBox.addItem("Producto por Descripción");
//			jComboBox.addItem("Producto por Departamento");
//			jComboBox.addItem("Producto por Línea/Sección");
//			jComboBox.addItem("Producto por Marca");
//			jComboBox.addItem("Producto por Modelo");
//			jComboBox.addItem("Producto por Ref. Proveedor");
			jComboBox.addItem("Cliente por Nombre");
			jComboBox.addItem("Cliente por Identificador (CI/Rif)");
			//jComboBox.addItem("Cliente por Dirección");
		}

		if (logger.isDebugEnabled())
			logger.debug("getJComboBox() - end");
		return jComboBox;
	}
	/**
	 * Método getCodigoSeleccionado
	 * 
	 * @return
	 * String
	 */
	public String[] getCodigoSeleccionado() {
		if (logger.isDebugEnabled())
			logger.debug("getCodigoSeleccionado() - start");

		if (logger.isDebugEnabled())
			logger.debug("getCodigoSeleccionado() - end");
		return codigoSeleccionado;
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
			y= this.getBounds().y;
		} else
		if (this.getY() < 0) {
			y=0;
			x= this.getBounds().x;
		} else
		if ((this.getX() + width) > screen.width){
			x=screen.width;
			y= this.getBounds().y;
		} else
		if ((this.getY() + height) > screen.height){
			y=screen.height;
			x= this.getBounds().x;
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

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel3() {
		if(jPanel3 == null) {
			jPanel3 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout15 = new java.awt.FlowLayout();
			layFlowLayout15.setHgap(0);
			layFlowLayout15.setVgap(0);
			jPanel3.setLayout(layFlowLayout15);
			jPanel3.add(getJButton2(), null);
			jPanel3.add(getJLabel1(), null);
			jPanel3.add(getJButton3(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(25,180));
			jPanel3.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel3;
	}
	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton2() {
		if(jButton2 == null) {
			jButton2 = new javax.swing.JButton();
			jButton2.setPreferredSize(new java.awt.Dimension(25,28));
			jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix24x24/nav_up_blue.png")));
			jButton2.setBackground(new java.awt.Color(242,242,238));
			jButton2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		}
		return jButton2;
	}
	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel1() {
		if(jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setText("");
			jLabel1.setPreferredSize(new java.awt.Dimension(25,127));
		}
		return jLabel1;
	}
	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton3() {
		if(jButton3 == null) {
			jButton3 = new javax.swing.JButton();
			jButton3.setPreferredSize(new java.awt.Dimension(25,28));
			jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix24x24/nav_down_blue.png")));
			jButton3.setBackground(new java.awt.Color(242,242,238));
			jButton3.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		}
		return jButton3;
	}
}