/*
 * Creado el 20/10/2004
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.becoblohm.cr.visortransacciones;

import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Vector;

import javax.swing.JApplet;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarventa.SavedTransaccion;
import com.becoblohm.cr.utiles.MensajesVentanas;


/**
 * @author Arístides Castillo
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class VisorTransaccionesApplet extends JApplet implements ListSelectionListener {

	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JTabbedPane jTabbedPane = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JScrollPane jScrollPane = null;
	private javax.swing.JTable jTable = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JTextField txtCajeroLst = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JPanel jPanel5 = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JPanel jPanel6 = null;
	private javax.swing.JButton btnActLista = null;
	private javax.swing.JPanel jPanel7 = null;
	private javax.swing.JEditorPane editDetalle = null;
	private javax.swing.JPanel jPanel8 = null;
	private javax.swing.JPanel jPanel9 = null;
	private javax.swing.JPanel jPanel10 = null;
	private javax.swing.JPanel jPanel11 = null;
	private javax.swing.JTextField txtFechaLst = null;
	private javax.swing.JLabel jLabel3 = null;
	private javax.swing.JLabel jLabel4 = null;
	private javax.swing.JLabel jLabel5 = null;
	private javax.swing.JButton btnActDet = null;
	private javax.swing.JTextField txtCajeroDet = null;
	private javax.swing.JTextField txtFechaDet = null;
	private javax.swing.JTextField txtNumTransDet = null;

	private int tienda = 1;
	private int caja = 1;
	private ModeloTablaAuditoria modeloTablaAud = new ModeloTablaAuditoria();
	private ModeloEditorTransaccion modeloEditor = new ModeloEditorTransaccion();
	private int selectedRow; 
	
	private javax.swing.JScrollPane jScrollPane1 = null;
	/**
	 * This is the default constructor
	 */
	public VisorTransaccionesApplet() {
		super();
		init();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void init() {
		this.setSize(500, 510);
		this.setContentPane(getJContentPane());
		this.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		try {
	//		new Conexiones("192.168.101.114");
			Sesion.prepararSesionAuditoria();
			modeloEditor.setUrlXSLVenta("com/becoblohm/cr/visorauditoria/xsltVenta.xsl");
			modeloEditor.setUrlXSLAnulacion("com/becoblohm/cr/visorauditoria/xsltAnulacion.xsl");
			modeloEditor.setUrlXSLDevolucion("com/becoblohm/cr/visorauditoria/xsltDevolucion.xsl");
		} catch(BaseDeDatosExcepcion e) {
			e.printStackTrace();
			MensajesVentanas.aviso("No pudo establecerse conexion con la caja");
			throw new RuntimeException("No se puede inicializar el visor de auditoria");			
		} catch (ConexionExcepcion e) {
			e.printStackTrace();
			MensajesVentanas.aviso("No pudo establecerse conexion con la caja");			
			throw new RuntimeException("No se puede inicializar el visor de auditoria");			
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
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setVgap(0);
			jContentPane.setLayout(layFlowLayout1);
			jContentPane.add(getJPanel2(), null);
			jContentPane.add(getJTabbedPane(), null);
			jContentPane.setBackground(new java.awt.Color(242,242,238));
			jContentPane.setPreferredSize(new java.awt.Dimension(480,510));
			jContentPane.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		}
		return jContentPane;
	}
	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private javax.swing.JTabbedPane getJTabbedPane() {
		if(jTabbedPane == null) {
			jTabbedPane = new javax.swing.JTabbedPane();
			jTabbedPane.addTab("Listado", null, getJPanel(), null);
			jTabbedPane.addTab("Detalle", null, getJPanel1(), null);
			jTabbedPane.setPreferredSize(new java.awt.Dimension(480,440));
			jTabbedPane.setBackground(new java.awt.Color(226,226,222));
		}
		return jTabbedPane;
	}
	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel() {
		if(jPanel == null) {
			jPanel = new javax.swing.JPanel();
			jPanel.add(getJPanel3(), null);
			jPanel.add(getJScrollPane(), null);
			jPanel.setBackground(new java.awt.Color(242,242,238));
			jPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
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
			jPanel1.add(getJPanel7(), null);
			jPanel1.add(getJScrollPane1(), null);
			jPanel1.setBackground(new java.awt.Color(242,242,238));
			jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		}
		return jPanel1;
	}
	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel2() {
		if(jPanel2 == null) {
			jPanel2 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
			layFlowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout2.setVgap(5);
			jPanel2.setLayout(layFlowLayout2);
			jPanel2.add(getJLabel(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(500,60));
			jPanel2.setBackground(new java.awt.Color(69,109,127));
		}
		return jPanel2;
	}
	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		if(jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setText("Visor de Auditoría");
			jLabel.setForeground(java.awt.Color.white);
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix48x48/security_agent.png")));
		}
		return jLabel;
	}
	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private javax.swing.JTable getJTable() {
		if(jTable == null) {
			jTable = new javax.swing.JTable(modeloTablaAud);
			jTable.getTableHeader().setReorderingAllowed(false) ;
			jTable.getColumnModel().getColumn(0).setPreferredWidth(50);
			jTable.getColumnModel().getColumn(1).setPreferredWidth(80);
			jTable.getColumnModel().getColumn(2).setPreferredWidth(30);
			jTable.getColumnModel().getColumn(3).setPreferredWidth(150);
			jTable.getColumnModel().getColumn(4).setPreferredWidth(100);
			jTable.getColumnModel().getColumn(5).setPreferredWidth(100);
			jTable.setBackground(new java.awt.Color(242,242,238));
			jTable.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jTable.getSelectionModel().addListSelectionListener(this);			
			jTable.addKeyListener(new java.awt.event.KeyAdapter() { 
				public void keyPressed(java.awt.event.KeyEvent e) {    
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						buscarDetalle();
					}				
				}
			});
			jTable.addMouseListener(new java.awt.event.MouseAdapter() { 
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() > 1) {
						buscarDetalle();						    
					}
				}
			});
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
			jScrollPane.setPreferredSize(new java.awt.Dimension(460,300));
			jScrollPane.setBackground(new java.awt.Color(242,242,238));
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel3() {
		if(jPanel3 == null) {
			jPanel3 = new javax.swing.JPanel();
			javax.swing.border.TitledBorder ivjTitledBorder = javax.swing.BorderFactory.createTitledBorder(null, "Valores de búsqueda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null);
			java.awt.FlowLayout layFlowLayout3 = new java.awt.FlowLayout();
			layFlowLayout3.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout3.setVgap(2);
			ivjTitledBorder.setTitleFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			ivjTitledBorder.setTitle("Valores de Búsqueda");
			jPanel3.setLayout(layFlowLayout3);
			jPanel3.add(getJPanel4(), null);
			jPanel3.add(getJPanel5(), null);
			jPanel3.add(getJPanel6(), null);
			jPanel3.setBorder(ivjTitledBorder);
			jPanel3.setBackground(new java.awt.Color(242,242,238));
			jPanel3.setPreferredSize(new java.awt.Dimension(460,90));
		}
		return jPanel3;
	}
	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel1() {
		if(jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setText("Ficha de Cajero:");
			jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel1.setPreferredSize(new java.awt.Dimension(100,14));
		}
		return jLabel1;
	}
	/**
	 * This method initializes txtCajeroLst
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtCajeroLst() {
		if(txtCajeroLst == null) {
			txtCajeroLst = new javax.swing.JTextField();
			txtCajeroLst.setPreferredSize(new java.awt.Dimension(100,17));
			txtCajeroLst.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			txtCajeroLst.addKeyListener(new java.awt.event.KeyAdapter() {   
				public void keyPressed(java.awt.event.KeyEvent e) {    
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						txtFechaLst.requestFocus();
					}				
				} 
			});
		}
		return txtCajeroLst;
	}
	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel4() {
		if(jPanel4 == null) {
			jPanel4 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout11 = new java.awt.FlowLayout();
			layFlowLayout11.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout11.setVgap(2);
			layFlowLayout11.setHgap(5);
			jPanel4.setLayout(layFlowLayout11);
			jPanel4.add(getJLabel1(), null);
			jPanel4.add(getTxtCajeroLst(), null);
			jPanel4.setBackground(new java.awt.Color(242,242,238));
			jPanel4.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jPanel4.setPreferredSize(new java.awt.Dimension(217,20));
		}
		return jPanel4;
	}
	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel5() {
		if(jPanel5 == null) {
			jPanel5 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout21 = new java.awt.FlowLayout();
			layFlowLayout21.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout21.setVgap(2);
			jPanel5.setLayout(layFlowLayout21);
			jPanel5.add(getJLabel2(), null);
			jPanel5.add(getTxtFechaLst(), null);
			jPanel5.setPreferredSize(new java.awt.Dimension(220,20));
			jPanel5.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jPanel5.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel5;
	}
	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel2() {
		if(jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setText("Fecha:");
			jLabel2.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel2.setPreferredSize(new java.awt.Dimension(50,14));
		}
		return jLabel2;
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
			layFlowLayout31.setAlignment(java.awt.FlowLayout.RIGHT);
			jPanel6.setLayout(layFlowLayout31);
			jPanel6.add(getBtnActLista(), null);
			jPanel6.setPreferredSize(new java.awt.Dimension(440,35));
			jPanel6.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel6;
	}
	/**
	 * This method initializes btnActLista
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getBtnActLista() {
		if(btnActLista == null) {
			btnActLista = new javax.swing.JButton();
			btnActLista.setBackground(new java.awt.Color(242,242,238));
			btnActLista.setText("Actualizar");
			btnActLista.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/data_refresh.png")));
			btnActLista.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			btnActLista.setSelected(true);
			btnActLista.setMnemonic(java.awt.event.KeyEvent.VK_ENTER);
			btnActLista.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {
					actualizarLst();    
				}
			});
		}
		return btnActLista;
	}
	/**
	 * This method initializes jPanel7
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel7() {
		if(jPanel7 == null) {
			jPanel7 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout12 = new java.awt.FlowLayout();
			javax.swing.border.TitledBorder ivjTitledBorder1 = javax.swing.BorderFactory.createTitledBorder(null, "Valores de Búsqueda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null);
			layFlowLayout12.setVgap(2);
			layFlowLayout12.setHgap(5);
			ivjTitledBorder1.setTitleFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			jPanel7.setLayout(layFlowLayout12);
			jPanel7.add(getJPanel8(), null);
			jPanel7.add(getJPanel9(), null);
			jPanel7.add(getJPanel10(), null);
			jPanel7.add(getJPanel11(), null);
			jPanel7.setBorder(ivjTitledBorder1);
			jPanel7.setBackground(new java.awt.Color(242,242,238));
			jPanel7.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jPanel7.setPreferredSize(new java.awt.Dimension(460,110));
		}
		return jPanel7;
	}
	/**
	 * This method initializes editDetalle
	 * 
	 * @return javax.swing.JEditorPane
	 */
	private javax.swing.JEditorPane getEditDetalle() {
		if(editDetalle == null) {
			editDetalle = new javax.swing.JEditorPane();
			editDetalle.setContentType("text/html");
			editDetalle.setPreferredSize(new java.awt.Dimension(450,200));
			editDetalle.setEditable(false);
		}
		return editDetalle;
	}
	/**
	 * This method initializes jPanel8
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel8() {
		if(jPanel8 == null) {
			jPanel8 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout22 = new java.awt.FlowLayout();
			layFlowLayout22.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout22.setVgap(2);
			jPanel8.setLayout(layFlowLayout22);
			jPanel8.add(getJLabel3(), null);
			jPanel8.add(getTxtCajeroDet(), null);
			jPanel8.setBackground(new java.awt.Color(242,242,238));
			jPanel8.setPreferredSize(new java.awt.Dimension(217,20));
		}
		return jPanel8;
	}
	/**
	 * This method initializes jPanel9
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel9() {
		if(jPanel9 == null) {
			jPanel9 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout32 = new java.awt.FlowLayout();
			layFlowLayout32.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout32.setVgap(2);
			jPanel9.setLayout(layFlowLayout32);
			jPanel9.add(getJLabel4(), null);
			jPanel9.add(getTxtFechaDet(), null);
			jPanel9.setBackground(new java.awt.Color(242,242,238));
			jPanel9.setPreferredSize(new java.awt.Dimension(217,20));
		}
		return jPanel9;
	}
	/**
	 * This method initializes jPanel10
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel10() {
		if(jPanel10 == null) {
			jPanel10 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout4 = new java.awt.FlowLayout();
			layFlowLayout4.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout4.setVgap(2);
			jPanel10.setLayout(layFlowLayout4);
			jPanel10.add(getJLabel5(), null);
			jPanel10.add(getTxtNumTransDet(), null);
			jPanel10.setBackground(new java.awt.Color(242,242,238));
			jPanel10.setPreferredSize(new java.awt.Dimension(440,20));
		}
		return jPanel10;
	}
	/**
	 * This method initializes jPanel11
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel11() {
		if(jPanel11 == null) {
			jPanel11 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout5 = new java.awt.FlowLayout();
			layFlowLayout5.setAlignment(java.awt.FlowLayout.RIGHT);
			jPanel11.setLayout(layFlowLayout5);
			jPanel11.add(getBtnActDet(), null);
			jPanel11.setBackground(new java.awt.Color(242,242,238));
			jPanel11.setPreferredSize(new java.awt.Dimension(440,35));
		}
		return jPanel11;
	}
	/**
	 * This method initializes txtFechaLst
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtFechaLst() {
		if(txtFechaLst == null) {
			txtFechaLst = new javax.swing.JTextField();
			txtFechaLst.setPreferredSize(new java.awt.Dimension(100,17));
			txtFechaLst.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			txtFechaLst.addKeyListener(new java.awt.event.KeyAdapter() { 
				public void keyPressed(java.awt.event.KeyEvent e) {    
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						btnActLista.requestFocus();
						actualizarLst();
					}				
				}
			});
		}
		return txtFechaLst;
	}
	/**
	 * This method initializes jLabel3
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel3() {
		if(jLabel3 == null) {
			jLabel3 = new javax.swing.JLabel();
			jLabel3.setText("Ficha de Cajero:");
			jLabel3.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel3.setPreferredSize(new java.awt.Dimension(100,14));
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
			jLabel4.setText("Fecha:");
			jLabel4.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel4.setPreferredSize(new java.awt.Dimension(50,14));
		}
		return jLabel4;
	}
	/**
	 * This method initializes jLabel5
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel5() {
		if(jLabel5 == null) {
			jLabel5 = new javax.swing.JLabel();
			jLabel5.setText("Num. Transacción:");
			jLabel5.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel5.setPreferredSize(new java.awt.Dimension(100,14));
		}
		return jLabel5;
	}
	/**
	 * This method initializes btnActDet
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getBtnActDet() {
		if(btnActDet == null) {
			btnActDet = new javax.swing.JButton();
			btnActDet.setBackground(new java.awt.Color(242,242,238));
			btnActDet.setText("Actualizar");
			btnActDet.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			btnActDet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/data_refresh.png")));
			btnActDet.setMnemonic(java.awt.event.KeyEvent.VK_ENTER);
			btnActDet.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					actualizarDet();
				}
			});
		}
		return btnActDet;
	}
	/**
	 * This method initializes txtCajeroDet
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtCajeroDet() {
		if(txtCajeroDet == null) {
			txtCajeroDet = new javax.swing.JTextField();
			txtCajeroDet.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			txtCajeroDet.setPreferredSize(new java.awt.Dimension(100,17));
			txtCajeroDet.addKeyListener(new java.awt.event.KeyAdapter() { 
				public void keyPressed(java.awt.event.KeyEvent e) {    
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						txtFechaDet.requestFocus();
					}					
				}
			});
		}
		return txtCajeroDet;
	}
	/**
	 * This method initializes txtFechaDet
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtFechaDet() {
		if(txtFechaDet == null) {
			txtFechaDet = new javax.swing.JTextField();
			txtFechaDet.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			txtFechaDet.setPreferredSize(new java.awt.Dimension(100,17));
			txtFechaDet.addKeyListener(new java.awt.event.KeyAdapter() { 
				public void keyPressed(java.awt.event.KeyEvent e) {    
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						txtNumTransDet.requestFocus();
					}					
				}
			});
		}
		return txtFechaDet;
	}
	/**
	 * This method initializes txtNumTransDet
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtNumTransDet() {
		if(txtNumTransDet == null) {
			txtNumTransDet = new javax.swing.JTextField();
			txtNumTransDet.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			txtNumTransDet.setPreferredSize(new java.awt.Dimension(100,17));
			txtNumTransDet.addKeyListener(new java.awt.event.KeyAdapter() { 
				public void keyPressed(java.awt.event.KeyEvent e) {    
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						btnActDet.requestFocus();
						actualizarDet();
					}					
				}
			});
		}
		return txtNumTransDet;
	}
	
	private void actualizarLst() {
		if ((txtCajeroLst.getText().length() != 0) && (txtFechaLst.getText().length() != 0 )) {
			try {
				DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, new java.util.Locale("es", "VE", ""));
				Vector<SavedTransaccion> tr = SavedTransaccion.getTransacciones(tienda, caja, df.parse(txtFechaLst.getText()), completarCodCajero(txtCajeroLst.getText()));
				((ModeloTablaAuditoria)jTable.getModel()).setTransacciones(tr);			
			} catch(BaseDeDatosExcepcion be) {
				be.printStackTrace();
			} catch (ConexionExcepcion ce) {
				ce.printStackTrace();
			} catch (ParseException pre) {
				pre.printStackTrace();
			}
		} else {
			MensajesVentanas.aviso("Debe indicar la ficha del cajero y la fecha de las transacciones");
		}
	}
	
	private void buscarDetalle() {
		jTabbedPane.setSelectedIndex(1);
		txtCajeroDet.setText(txtCajeroLst.getText());
		txtFechaDet.setText(txtFechaLst.getText());
		txtNumTransDet.setText(Integer.toString(((ModeloTablaAuditoria)jTable.getModel()).getTransaccion(selectedRow).getNumTransaccion()));
		actualizarDet();
	}
	
	private void actualizarDet() {
		if ((txtCajeroDet.getText().length() != 0) && (txtFechaDet.getText().length() != 0) 
			&& (txtNumTransDet.getText().length() != 0)) {
			String result = null;
			DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, new java.util.Locale("es", "VE", ""));
			try {
				SavedTransaccion t = new SavedTransaccion(tienda, df.parse(txtFechaDet.getText()), caja, Integer.parseInt(txtNumTransDet.getText()), true);
				switch (t.getTipoTransaccion()) {
					case Sesion.ANULACION :
						result = modeloEditor.getHtmlTransaccion(t.getAnulacion());
						break;
					case Sesion.VENTA :
						result = modeloEditor.getHtmlTransaccion(t.getVenta());
						break;	
					case Sesion.DEVOLUCION :
						result = modeloEditor.getHtmlTransaccion(t.getDevolucion());
						break;
				}
				editDetalle.setText(result);

			} catch (ParseException pe) {
				pe.printStackTrace();
			} catch (BaseDeDatosExcepcion bde) {
				bde.printStackTrace();
			} catch (ConexionExcepcion ce) {
				ce.printStackTrace();
			}
		}
		
	}
	
	private String completarCodCajero(String codCajero) {
		StringBuffer buf = new StringBuffer(codCajero);
		while (buf.length() < 8) {
			buf.insert(0, "0");
		}
		return buf.toString();
	}
	/* (no Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void valueChanged(ListSelectionEvent e) {
		selectedRow = jTable.getSelectedRow();
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private javax.swing.JScrollPane getJScrollPane1() {
		if(jScrollPane1 == null) {
			jScrollPane1 = new javax.swing.JScrollPane();
			jScrollPane1.setViewportView(getEditDetalle());
			jScrollPane1.setViewportBorder(javax.swing.BorderFactory.createTitledBorder(null, "Transacción", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), null));
			jScrollPane1.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			jScrollPane1.setBackground(new java.awt.Color(242,242,238));
			jScrollPane1.setPreferredSize(new java.awt.Dimension(460,280));
		}
		return jScrollPane1;
	}
}
