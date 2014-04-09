/*
 * Creado el 26/10/2004
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.becoblohm.cr.visortransacciones;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;

import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarventa.SavedTransaccion;
import com.becoblohm.cr.mediadoresbd.Conexiones;
import com.becoblohm.cr.utiles.MensajesVentanas;
/**
 * @author Programa8
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class VisorTransacciones extends JFrame implements ListSelectionListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(VisorTransacciones.class);

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
	private javax.swing.JLabel jLabel3 = null;
	private javax.swing.JLabel jLabel4 = null;
	private javax.swing.JLabel jLabel5 = null;
	private javax.swing.JButton btnActDet = null;
	private javax.swing.JTextField txtCajeroDet = null;

	private javax.swing.JTextField txtNumTransDet = null;
	private JSpinner selectorFechaLst = null;
	private JSpinner selectorFechaDet = null;
	private int tienda;
	private int caja;
	private ModeloTablaAuditoria modeloTablaAud = new ModeloTablaAuditoria();
	private ModeloEditorTransaccion modeloEditor = new ModeloEditorTransaccion();
	private int selectedRow; 
	private Properties props = new Properties();
	private SelectCaja selectDialog;
	private javax.swing.JScrollPane jScrollPane1 = null;


 	public static void main(String[] args) {
		if (logger.isDebugEnabled()) {
			logger.debug("main(String[]) - start");
		}

		try {
			VisorTransacciones app = new VisorTransacciones();
			app.execute();
		} catch (IOException e) {
			logger
					.error(
							"main(String[]) - No se encuentra archivo auditoria.properties",
							e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("main(String[]) - end");
		}
	}
	/**
	 * This is the default constructor
	 */
	public VisorTransacciones() throws IOException {
		super();
		initialize();
		InputStream in = VisorTransacciones.class.getResourceAsStream("/com/becoblohm/cr/visortransacciones/auditoria.properties");
		props.load(in);
		tienda = Integer.parseInt(props.getProperty("tienda"));
		modeloEditor.setUrlXSLVenta(props.getProperty("xslt.venta"));
		modeloEditor.setUrlXSLAnulacion(props.getProperty("xslt.anulacion"));
		modeloEditor.setUrlXSLDevolucion(props.getProperty("xslt.devolucion"));		
		selectDialog = new SelectCaja(props);
	}

	private void conectar(CnxCaja c) {
		if (logger.isDebugEnabled()) {
			logger.debug("conectar(CnxCaja) - start");
		}

		try {
			new Conexiones(c.getIP(), props);
			caja = c.getCodigo();
			Sesion.prepararSesionAuditoria();
		} catch(BaseDeDatosExcepcion e) {
			logger.error("conectar(CnxCaja)", e);
			MensajesVentanas.aviso("No pudo establecerse conexion con la caja");
			System.exit(0);			
		} catch (ConexionExcepcion e) {
			logger.error("conectar(CnxCaja)", e);
			MensajesVentanas.aviso("No pudo establecerse conexion con la caja");			
			System.exit(0);			
		} 

		if (logger.isDebugEnabled()) {
			logger.debug("conectar(CnxCaja) - end");
		}
	}
	
	public void execute() {
		if (logger.isDebugEnabled()) {
			logger.debug("execute() - start");
		}

		if (selectDialog.mostrar()) {
			conectar(selectDialog.getCnxSelected());
			MensajesVentanas.centrarVentana(this);
		} else {
			System.exit(0);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("execute() - end");
		}
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

		this.setSize(500, 535);
		this.setContentPane(getJContentPane());
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		this.setTitle("Visor de Auditoría");
		this.setResizable(false);

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
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setVgap(0);
			jContentPane.setLayout(layFlowLayout1);
			jContentPane.add(getJPanel2(), null);
			jContentPane.add(getJTabbedPane(), null);
			jContentPane.setBackground(new java.awt.Color(242,242,238));
			jContentPane.setPreferredSize(new java.awt.Dimension(480,510));
			jContentPane.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJContentPane() - end");
		}
		return jContentPane;
	}
	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private javax.swing.JTabbedPane getJTabbedPane() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTabbedPane() - start");
		}

		if(jTabbedPane == null) {
			jTabbedPane = new javax.swing.JTabbedPane();
			jTabbedPane.addTab("Listado", null, getJPanel(), null);
			jTabbedPane.addTab("Detalle", null, getJPanel1(), null);
			jTabbedPane.setPreferredSize(new java.awt.Dimension(480,440));
			jTabbedPane.setBackground(new java.awt.Color(226,226,222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTabbedPane() - end");
		}
		return jTabbedPane;
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
			jPanel.add(getJPanel3(), null);
			jPanel.add(getJScrollPane(), null);
			jPanel.setBackground(new java.awt.Color(242,242,238));
			jPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
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
			jPanel1.add(getJPanel7(), null);
			jPanel1.add(getJScrollPane1(), null);
			jPanel1.setBackground(new java.awt.Color(242,242,238));
			jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel1() - end");
		}
		return jPanel1;
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
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
			layFlowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout2.setVgap(5);
			jPanel2.setLayout(layFlowLayout2);
			jPanel2.add(getJLabel(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(500,60));
			jPanel2.setBackground(new java.awt.Color(69,109,127));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel2() - end");
		}
		return jPanel2;
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
			jLabel.setText("Visor de Auditoría");
			jLabel.setForeground(java.awt.Color.white);
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix48x48/security_agent.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel() - end");
		}
		return jLabel;
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
					if (logger.isDebugEnabled()) {
						logger
								.debug("keyPressed(java.awt.event.KeyEvent) - start");
					}
    
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						buscarDetalle();
					}				

					if (logger.isDebugEnabled()) {
						logger
								.debug("keyPressed(java.awt.event.KeyEvent) - end");
					}
				}
			});
			jTable.addMouseListener(new java.awt.event.MouseAdapter() { 
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (logger.isDebugEnabled()) {
						logger
								.debug("mouseClicked(java.awt.event.MouseEvent) - start");
					}

					if (e.getClickCount() > 1) {
						buscarDetalle();						    
					}

					if (logger.isDebugEnabled()) {
						logger
								.debug("mouseClicked(java.awt.event.MouseEvent) - end");
					}
				}
			});
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
			jScrollPane.setPreferredSize(new java.awt.Dimension(460,300));
			jScrollPane.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJScrollPane() - end");
		}
		return jScrollPane;
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

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel3() - end");
		}
		return jPanel3;
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
			jLabel1.setText("Ficha de Cajero:");
			jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel1.setPreferredSize(new java.awt.Dimension(100,14));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel1() - end");
		}
		return jLabel1;
	}
	/**
	 * This method initializes txtCajeroLst
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtCajeroLst() {
		if (logger.isDebugEnabled()) {
			logger.debug("getTxtCajeroLst() - start");
		}

		if(txtCajeroLst == null) {
			txtCajeroLst = new javax.swing.JTextField();
			txtCajeroLst.setPreferredSize(new java.awt.Dimension(100,17));
			txtCajeroLst.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			txtCajeroLst.addKeyListener(new java.awt.event.KeyAdapter() {   
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (logger.isDebugEnabled()) {
						logger
								.debug("keyPressed(java.awt.event.KeyEvent) - start");
					}
    
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						selectorFechaLst.requestFocus();
					}				

					if (logger.isDebugEnabled()) {
						logger
								.debug("keyPressed(java.awt.event.KeyEvent) - end");
					}
				} 
			});
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getTxtCajeroLst() - end");
		}
		return txtCajeroLst;
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

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel4() - end");
		}
		return jPanel4;
	}
	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel5() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel5() - start");
		}

		if(jPanel5 == null) {
			jPanel5 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout21 = new java.awt.FlowLayout();
			layFlowLayout21.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout21.setVgap(2);
			jPanel5.setLayout(layFlowLayout21);
			jPanel5.add(getJLabel2(), null);
			//jPanel5.add(getTxtFechaLst(), null);
			jPanel5.add(getSelectorFechaLst(), null);
			jPanel5.setPreferredSize(new java.awt.Dimension(220,20));
			jPanel5.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jPanel5.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel5() - end");
		}
		return jPanel5;
	}
	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel2() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel2() - start");
		}

		if(jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setText("Fecha:");
			jLabel2.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel2.setPreferredSize(new java.awt.Dimension(50,14));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel2() - end");
		}
		return jLabel2;
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
			java.awt.FlowLayout layFlowLayout31 = new java.awt.FlowLayout();
			layFlowLayout31.setAlignment(java.awt.FlowLayout.RIGHT);
			jPanel6.setLayout(layFlowLayout31);
			jPanel6.add(getBtnActLista(), null);
			jPanel6.setPreferredSize(new java.awt.Dimension(440,35));
			jPanel6.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel6() - end");
		}
		return jPanel6;
	}
	/**
	 * This method initializes btnActLista
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getBtnActLista() {
		if (logger.isDebugEnabled()) {
			logger.debug("getBtnActLista() - start");
		}

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
					if (logger.isDebugEnabled()) {
						logger
								.debug("actionPerformed(java.awt.event.ActionEvent) - start");
					}

					actualizarLst();    

					if (logger.isDebugEnabled()) {
						logger
								.debug("actionPerformed(java.awt.event.ActionEvent) - end");
					}
				}
			});
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getBtnActLista() - end");
		}
		return btnActLista;
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

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel7() - end");
		}
		return jPanel7;
	}
	/**
	 * This method initializes editDetalle
	 * 
	 * @return javax.swing.JEditorPane
	 */
	private javax.swing.JEditorPane getEditDetalle() {
		if (logger.isDebugEnabled()) {
			logger.debug("getEditDetalle() - start");
		}

		if(editDetalle == null) {
			editDetalle = new javax.swing.JEditorPane();
			editDetalle.setContentType("text/html");
			editDetalle.setPreferredSize(new java.awt.Dimension(450,200));
			editDetalle.setEditable(false);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getEditDetalle() - end");
		}
		return editDetalle;
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
			java.awt.FlowLayout layFlowLayout22 = new java.awt.FlowLayout();
			layFlowLayout22.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout22.setVgap(2);
			jPanel8.setLayout(layFlowLayout22);
			jPanel8.add(getJLabel3(), null);
			jPanel8.add(getTxtCajeroDet(), null);
			jPanel8.setBackground(new java.awt.Color(242,242,238));
			jPanel8.setPreferredSize(new java.awt.Dimension(217,20));
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
			java.awt.FlowLayout layFlowLayout32 = new java.awt.FlowLayout();
			layFlowLayout32.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout32.setVgap(2);
			jPanel9.setLayout(layFlowLayout32);
			jPanel9.add(getJLabel4(), null);
//			jPanel9.add(getTxtFechaDet(), null);
			jPanel9.add(getSelectorFechaDet(), null);
			jPanel9.setBackground(new java.awt.Color(242,242,238));
			jPanel9.setPreferredSize(new java.awt.Dimension(217,20));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel9() - end");
		}
		return jPanel9;
	}
	/**
	 * This method initializes jPanel10
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel10() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel10() - start");
		}

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

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel10() - end");
		}
		return jPanel10;
	}
	/**
	 * This method initializes jPanel11
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel11() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel11() - start");
		}

		if(jPanel11 == null) {
			jPanel11 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout5 = new java.awt.FlowLayout();
			layFlowLayout5.setAlignment(java.awt.FlowLayout.RIGHT);
			jPanel11.setLayout(layFlowLayout5);
			jPanel11.add(getBtnActDet(), null);
			jPanel11.setBackground(new java.awt.Color(242,242,238));
			jPanel11.setPreferredSize(new java.awt.Dimension(440,35));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel11() - end");
		}
		return jPanel11;
	}
	/**
	 * This method initializes txtFechaLst
	 * 
	 * @return javax.swing.JTextField
	 */
	
	private JSpinner getSelectorFechaLst(){
		if (logger.isDebugEnabled()) {
			logger.debug("getSelectorFechaLst() - start");
		}

		if(selectorFechaLst == null){
			selectorFechaLst = new JSpinner(new SpinnerDateModel());
			JSpinner.DateEditor de = new JSpinner.DateEditor(selectorFechaLst, "dd/MM/yyyy");
			selectorFechaLst.setEditor(de);
			selectorFechaLst.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			selectorFechaLst.setPreferredSize(new java.awt.Dimension(100,17));
			selectorFechaLst.addKeyListener(new java.awt.event.KeyAdapter() { 
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (logger.isDebugEnabled()) {
						logger
								.debug("keyPressed(java.awt.event.KeyEvent) - start");
					}
    
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						btnActLista.requestFocus();
						actualizarLst();
					}				

					if (logger.isDebugEnabled()) {
						logger
								.debug("keyPressed(java.awt.event.KeyEvent) - end");
					}
				}
			});
			
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getSelectorFechaLst() - end");
		}
		return selectorFechaLst;
	}
	
	private JSpinner getSelectorFechaDet(){
		if (logger.isDebugEnabled()) {
			logger.debug("getSelectorFechaDet() - start");
		}

		if(selectorFechaDet == null){
			selectorFechaDet = new JSpinner(new SpinnerDateModel());
			JSpinner.DateEditor de = new JSpinner.DateEditor(selectorFechaDet, "dd/MM/yyyy");
			selectorFechaDet.setEditor(de);
			selectorFechaDet.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			selectorFechaDet.setPreferredSize(new java.awt.Dimension(100,17));
			selectorFechaDet.addKeyListener(new java.awt.event.KeyAdapter() { 
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (logger.isDebugEnabled()) {
						logger
								.debug("keyPressed(java.awt.event.KeyEvent) - start");
					}
    
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						txtNumTransDet.requestFocus();
					}					

					if (logger.isDebugEnabled()) {
						logger
								.debug("keyPressed(java.awt.event.KeyEvent) - end");
					}
				}
			});			
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getSelectorFechaDet() - end");
		}
		return selectorFechaDet;
	}	
	/**
	 * This method initializes jLabel3
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel3() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel3() - start");
		}

		if(jLabel3 == null) {
			jLabel3 = new javax.swing.JLabel();
			jLabel3.setText("Ficha de Cajero:");
			jLabel3.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel3.setPreferredSize(new java.awt.Dimension(100,14));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel3() - end");
		}
		return jLabel3;
	}
	/**
	 * This method initializes jLabel4
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel4() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel4() - start");
		}

		if(jLabel4 == null) {
			jLabel4 = new javax.swing.JLabel();
			jLabel4.setText("Fecha:");
			jLabel4.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel4.setPreferredSize(new java.awt.Dimension(50,14));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel4() - end");
		}
		return jLabel4;
	}
	/**
	 * This method initializes jLabel5
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel5() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel5() - start");
		}

		if(jLabel5 == null) {
			jLabel5 = new javax.swing.JLabel();
			jLabel5.setText("Num. Transacción:");
			jLabel5.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jLabel5.setPreferredSize(new java.awt.Dimension(100,14));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel5() - end");
		}
		return jLabel5;
	}
	/**
	 * This method initializes btnActDet
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getBtnActDet() {
		if (logger.isDebugEnabled()) {
			logger.debug("getBtnActDet() - start");
		}

		if(btnActDet == null) {
			btnActDet = new javax.swing.JButton();
			btnActDet.setBackground(new java.awt.Color(242,242,238));
			btnActDet.setText("Actualizar");
			btnActDet.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			btnActDet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/data_refresh.png")));
			btnActDet.setMnemonic(java.awt.event.KeyEvent.VK_ENTER);
			btnActDet.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (logger.isDebugEnabled()) {
						logger
								.debug("actionPerformed(java.awt.event.ActionEvent) - start");
					}
    
					actualizarDet();

					if (logger.isDebugEnabled()) {
						logger
								.debug("actionPerformed(java.awt.event.ActionEvent) - end");
					}
				}
			});
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getBtnActDet() - end");
		}
		return btnActDet;
	}
	/**
	 * This method initializes txtCajeroDet
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtCajeroDet() {
		if (logger.isDebugEnabled()) {
			logger.debug("getTxtCajeroDet() - start");
		}

		if(txtCajeroDet == null) {
			txtCajeroDet = new javax.swing.JTextField();
			txtCajeroDet.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			txtCajeroDet.setPreferredSize(new java.awt.Dimension(100,17));
			txtCajeroDet.addKeyListener(new java.awt.event.KeyAdapter() { 
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (logger.isDebugEnabled()) {
						logger
								.debug("keyPressed(java.awt.event.KeyEvent) - start");
					}
    
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						selectorFechaDet.requestFocus();
					}					

					if (logger.isDebugEnabled()) {
						logger
								.debug("keyPressed(java.awt.event.KeyEvent) - end");
					}
				}
			});
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getTxtCajeroDet() - end");
		}
		return txtCajeroDet;
	}
	/**
	 * This method initializes txtFechaDet
	 * 
	 * @return javax.swing.JTextField
	 */

	/**
	 * This method initializes txtNumTransDet
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getTxtNumTransDet() {
		if (logger.isDebugEnabled()) {
			logger.debug("getTxtNumTransDet() - start");
		}

		if(txtNumTransDet == null) {
			txtNumTransDet = new javax.swing.JTextField();
			txtNumTransDet.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			txtNumTransDet.setPreferredSize(new java.awt.Dimension(100,17));
			txtNumTransDet.addKeyListener(new java.awt.event.KeyAdapter() { 
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (logger.isDebugEnabled()) {
						logger
								.debug("keyPressed(java.awt.event.KeyEvent) - start");
					}
    
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						btnActDet.requestFocus();
						actualizarDet();
					}					

					if (logger.isDebugEnabled()) {
						logger
								.debug("keyPressed(java.awt.event.KeyEvent) - end");
					}
				}
			});
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getTxtNumTransDet() - end");
		}
		return txtNumTransDet;
	}
	
	private void actualizarLst() {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarLst() - start");
		}

		if ((txtCajeroLst.getText().length() != 0)) {
			try {
				Vector<SavedTransaccion> tr = SavedTransaccion.getTransacciones(tienda, caja, ((SpinnerDateModel)selectorFechaLst.getModel()).getDate(), completarCodCajero(txtCajeroLst.getText()));
				((ModeloTablaAuditoria)jTable.getModel()).setTransacciones(tr);			
			} catch(BaseDeDatosExcepcion be) {
				logger.error("actualizarLst()", be);
			} catch (ConexionExcepcion ce) {
				logger.error("actualizarLst()", ce);
			}
		} else {
			MensajesVentanas.aviso("Debe indicar la ficha del cajero y la fecha de las transacciones");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarLst() - end");
		}
	}
	
	private void buscarDetalle() {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarDetalle() - start");
		}

		jTabbedPane.setSelectedIndex(1);
		txtCajeroDet.setText(txtCajeroLst.getText());
		((SpinnerDateModel)selectorFechaDet.getModel()).setValue(((SpinnerDateModel)selectorFechaLst.getModel()).getDate());
		txtNumTransDet.setText(Integer.toString(((ModeloTablaAuditoria)jTable.getModel()).getTransaccion(selectedRow).getNumTransaccion()));
		actualizarDet();

		if (logger.isDebugEnabled()) {
			logger.debug("buscarDetalle() - end");
		}
	}
	
	private void actualizarDet() {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarDet() - start");
		}

		if ((txtCajeroDet.getText().length() != 0) 	&& (txtNumTransDet.getText().length() != 0)) {
			String result = null;
			try {
				SavedTransaccion t = new SavedTransaccion(tienda, ((SpinnerDateModel)selectorFechaDet.getModel()).getDate(), caja, Integer.parseInt(txtNumTransDet.getText()), true);
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

			} catch (BaseDeDatosExcepcion bde) {
				logger.error("actualizarDet()", bde);
			} catch (ConexionExcepcion ce) {
				logger.error("actualizarDet()", ce);
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarDet() - end");
		}
	}
	
	private String completarCodCajero(String codCajero) {
		if (logger.isDebugEnabled()) {
			logger.debug("completarCodCajero(String) - start");
		}

		StringBuffer buf = new StringBuffer(codCajero);
		while (buf.length() < 8) {
			buf.insert(0, "0");
		}
		String returnString = buf.toString();
		if (logger.isDebugEnabled()) {
			logger.debug("completarCodCajero(String) - end");
		}
		return returnString;
	}
	/* (no Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void valueChanged(ListSelectionEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("valueChanged(ListSelectionEvent) - start");
		}

		selectedRow = jTable.getSelectedRow();

		if (logger.isDebugEnabled()) {
			logger.debug("valueChanged(ListSelectionEvent) - end");
		}
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private javax.swing.JScrollPane getJScrollPane1() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJScrollPane1() - start");
		}

		if(jScrollPane1 == null) {
			jScrollPane1 = new javax.swing.JScrollPane();
			jScrollPane1.setViewportView(getEditDetalle());
			jScrollPane1.setViewportBorder(javax.swing.BorderFactory.createTitledBorder(null, "Transacción", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 10), null));
			jScrollPane1.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			jScrollPane1.setBackground(new java.awt.Color(242,242,238));
			jScrollPane1.setPreferredSize(new java.awt.Dimension(460,280));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJScrollPane1() - end");
		}
		return jScrollPane1;
	}

}
