/**
 * =============================================================================
 * Proyecto   : Colas-ServTienda-ServCentral
 * Paquete    : com.beco.colascr.transferencias.gui
 * Programa   : ConsolaPrincipal.java
 * Creado por : gmartinelli
 * Creado en  : 17-may-05 14:33:37
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1
 * Fecha       : 17-may-05 14:33:37
 * Analista    : gmartinelli
 * Descripción : Implementación inicial.
 * =============================================================================
 */
package com.beco.colascr.transferencias.gui; 

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import com.beco.colascr.transferencias.ServidorCR;
import com.beco.colascr.transferencias.configuracion.Sesion;
import com.beco.colascr.transferencias.sincronizador.Sincronizador;
import com.beco.colascr.utiles.MensajesVentanas;

/**
 * Descripción:
 * 
 */

public class DetalleSincronizacion extends JFrame implements MouseListener, KeyListener {

	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JScrollPane jScrollPane = null;
	private javax.swing.JTable jTable = null;
	
	private ModeloTabla modeloTablaDetalleTareas = new ModeloTabla();

	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel5 = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JButton jButton2 = null;
	private javax.swing.JButton jButton3 = null;
	private javax.swing.JButton jButton4 = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JButton jButton5 = null;
	private javax.swing.JButton jButton6 = null;
	/**
	 * This is the default constructor
	 */
	public DetalleSincronizacion() {
		super();
		initialize();
		
		jButton.addMouseListener(this);
		jButton.addKeyListener(this);

		jButton1.addMouseListener(this);
		jButton1.addKeyListener(this);

		jButton2.addMouseListener(this);
		jButton2.addKeyListener(this);

		jButton3.addMouseListener(this);
		jButton3.addKeyListener(this);

		jButton4.addMouseListener(this);
		jButton4.addKeyListener(this);
		
		jButton5.addMouseListener(this);
		jButton5.addKeyListener(this);

		jButton6.addMouseListener(this);
		jButton6.addKeyListener(this);
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(750, 245);
		this.setContentPane(getJContentPane());
		this.setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/beco/colascr/utiles/iconos/ix32x32/data_replace.png")));
		this.setTitle("Servidor CR - Detalles de Tareas");
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
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
			layFlowLayout1.setHgap(0);
			layFlowLayout1.setVgap(0);
			jContentPane.setLayout(layFlowLayout1);
			jContentPane.add(getJPanel(), null);
			jContentPane.setPreferredSize(new java.awt.Dimension(740,235));
		}
		return jContentPane;
	}
	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel() {
		if(jPanel == null) {
			jPanel = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout4 = new java.awt.FlowLayout();
			layFlowLayout4.setAlignment(java.awt.FlowLayout.CENTER);
			layFlowLayout4.setVgap(1);
			jPanel.setLayout(layFlowLayout4);
			jPanel.add(getJPanel1(), null);
			jPanel.add(getJPanel2(), null);
			jPanel.setBackground(new java.awt.Color(226,226,222));
			jPanel.setPreferredSize(new java.awt.Dimension(740,210));
		}
		return jPanel;
	}
	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel2() {
		if(jPanel2 == null) {
			jPanel2 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout5 = new java.awt.FlowLayout();
			layFlowLayout5.setAlignment(java.awt.FlowLayout.RIGHT);
			jPanel2.setLayout(layFlowLayout5);
			jPanel2.add(getJButton(), null);
			jPanel2.setBackground(new java.awt.Color(226,226,222));
			jPanel2.setPreferredSize(new java.awt.Dimension(735,36));
		}
		return jPanel2;
	}
	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel1() {
		if(jPanel1 == null) {
			jPanel1 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout3 = new java.awt.FlowLayout();
			javax.swing.border.TitledBorder ivjTitledBorder = javax.swing.BorderFactory.createTitledBorder(null , "Mensajes: " , javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION , javax.swing.border.TitledBorder.DEFAULT_POSITION , new java.awt.Font("Dialog", java.awt.Font.BOLD, 12) , java.awt.Color.black);
			ivjTitledBorder.setTitle("Tareas: ");
			layFlowLayout3.setVgap(3);
			layFlowLayout3.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel1.setLayout(layFlowLayout3);
			jPanel1.add(getJPanel3(), null);
			jPanel1.add(getJPanel5(), null);
			jPanel1.setBorder(ivjTitledBorder);
			jPanel1.setBackground(new java.awt.Color(226,226,222));
			jPanel1.setPreferredSize(new java.awt.Dimension(730,168));
		}
		return jPanel1;
	}
	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton() {
		if(jButton == null) {
			jButton = new javax.swing.JButton();
			jButton.setText("Cerrar");
			jButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/beco/colascr/utiles/iconos/ix16x16/stock_redo.png")));
			jButton.setBackground(new java.awt.Color(226,226,222));
		}
		return jButton;
	}
	/**
	 * Método enviarMensaje
	 * 
	 * @param mensaje
	 * void
	 */
	public void enviarMensaje(String mensaje) {
/*		String texto = this.jEditorPane.getText() + mensaje;
		StringTokenizer st = new StringTokenizer(texto, "\n");
		if (st.countTokens()>ServidorCR.NUMERO_LINEAS_MSG) {
			int limiteInferior = ((String)st.nextToken()).length();
			texto = texto.substring(limiteInferior + 1);
		}
		this.jEditorPane.setText(texto + "\n");
		jEditorPane.setSelectionStart(jEditorPane.getText().length());*/
	}
	/**
	 * Método mouseClicked
	 *
	 * @param e
	 */
	public void mouseClicked(MouseEvent e) {
		if (e.getButton()==MouseEvent.BUTTON1) {
			if (e.getSource().equals(jButton)) {
				if (ServidorCR.getEstadoServ()==ServidorCR.SERVICIO_INICIANDO)
					MensajesVentanas.mensajeUsuario("El Servicio se está Iniciando. Por favor Espere");
				else if (ServidorCR.getEstadoServ()==ServidorCR.SERVICIO_DETENIENDO)
					MensajesVentanas.mensajeUsuario("El Servicio se está Deteniendo. Por favor Espere");
				else if (ServidorCR.getEstadoServ()==ServidorCR.SERVICIO_DETENIDO)
					dispose();
				else if (ServidorCR.getEstadoServ()==ServidorCR.SERVICIO_INICIADO) {
					dispose();
				}
			}
			else if (e.getSource().equals(jButton1)) {
				ServidorCR.sincronizador.iniciar(Sincronizador.BASES);
			}
			else if (e.getSource().equals(jButton2)) {
				ServidorCR.sincronizador.iniciar(Sincronizador.AFILIADOS);
			}
			else if (e.getSource().equals(jButton3)) {
				ServidorCR.sincronizador.iniciar(Sincronizador.PRODUCTOS);
			}
			else if (e.getSource().equals(jButton4)) {
				ServidorCR.sincronizador.iniciar(Sincronizador.VENTAS);
			}
			else if (e.getSource().equals(jButton5)) {
				ServidorCR.sincronizador.iniciar(Sincronizador.LISTAREGALOS);
			}
			else if (e.getSource().equals(jButton6)) {
				ServidorCR.sincronizador.iniciar(Sincronizador.AFILIADOSTEMP);
			}
		}
	}
	/**
	 * Método mouseEntered
	 *
	 * @param e
	 */
	public void mouseEntered(MouseEvent e) {
	}
	/**
	 * Método mouseExited
	 *
	 * @param e
	 */
	public void mouseExited(MouseEvent e) {
	}
	/**
	 * Método mousePressed
	 *
	 * @param e
	 */
	public void mousePressed(MouseEvent e) {
	}
	/**
	 * Método mouseReleased
	 *
	 * @param e
	 */
	public void mouseReleased(MouseEvent e) {
	}
	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private javax.swing.JTable getJTable() {
		if(jTable == null) {
			jTable = new javax.swing.JTable(modeloTablaDetalleTareas);
			jTable.getColumnModel().getColumn(0).setPreferredWidth(100);
			jTable.getColumnModel().getColumn(1).setPreferredWidth(150);
			jTable.getColumnModel().getColumn(2).setPreferredWidth(80);
			jTable.getColumnModel().getColumn(3).setPreferredWidth(150);
			jTable.setLocale(new java.util.Locale("es", "VE", ""));
			jTable.setRowHeight(jTable.getRowHeight()+3);
			jTable.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
			jTable.setBackground(new java.awt.Color(242,242,238));
			jTable.setCellSelectionEnabled(true);
			jTable.setFocusable(false);
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
			jScrollPane.setPreferredSize(new java.awt.Dimension(585,135));
		}
		return jScrollPane;
	}
	/**
	 * Método llenarTablas
	 * 
	 * 
	 * void
	 */
	public void llenarTabla() {
		modeloTablaDetalleTareas.llenarTabla(Sesion.getFrecuenciaSincBases(), Sesion.getFrecuenciaSincAfiliados(), 
											Sesion.getFrecuenciaSincAfiliadosTemporales(), Sesion.getFrecuenciaSincProductos(), 
											Sesion.getFrecuenciaSincVentas(), Sesion.getFrecuenciaSincListaRegalos());
	}
	/**
	 * Método keyPressed
	 *
	 * @param e
	 */
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_ENTER || e.getKeyCode()==KeyEvent.VK_SPACE) {
			if (e.getSource().equals(jButton)) {
				if (ServidorCR.getEstadoServ()==ServidorCR.SERVICIO_INICIANDO)
					MensajesVentanas.mensajeUsuario("El Servicio se está Iniciando. Por favor Espere");
				else if (ServidorCR.getEstadoServ()==ServidorCR.SERVICIO_DETENIENDO)
					MensajesVentanas.mensajeUsuario("El Servicio se está Deteniendo. Por favor Espere");
				else if (ServidorCR.getEstadoServ()==ServidorCR.SERVICIO_DETENIDO)
					dispose();
				else if (ServidorCR.getEstadoServ()==ServidorCR.SERVICIO_INICIADO) {
					dispose();
				}
			}
		}
	}
	/**
	 * Método keyReleased
	 *
	 * @param e
	 */
	public void keyReleased(KeyEvent e) {
	}
	/**
	 * Método keyTyped
	 *
	 * @param e
	 */
	public void keyTyped(KeyEvent e) {
	}
	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel3() {
		if(jPanel3 == null) {
			jPanel3 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout41 = new java.awt.FlowLayout();
			layFlowLayout41.setHgap(0);
			layFlowLayout41.setVgap(0);
			jPanel3.setLayout(layFlowLayout41);
			jPanel3.add(getJScrollPane(), null);
			jPanel3.setBackground(new java.awt.Color(226,226,222));
			jPanel3.setPreferredSize(new java.awt.Dimension(585,135));
		}
		return jPanel3;
	}
	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel5() {
		if(jPanel5 == null) {
			jPanel5 = new javax.swing.JPanel();
			java.awt.GridLayout layGridLayout8 = new java.awt.GridLayout();
			layGridLayout8.setRows(7);
			jPanel5.setLayout(layGridLayout8);
			jPanel5.add(getJLabel1(), null);
			jPanel5.add(getJButton1(), null);
			jPanel5.add(getJButton2(), null);
			jPanel5.add(getJButton6(), null);
			jPanel5.add(getJButton3(), null);
			jPanel5.add(getJButton4(), null);
			jPanel5.add(getJButton5(), null);
			jPanel5.setBackground(new java.awt.Color(226,226,222));
			jPanel5.setPreferredSize(new java.awt.Dimension(120,135));
		}
		return jPanel5;
	}
	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton2() {
		if(jButton2 == null) {
			jButton2 = new javax.swing.JButton();
			jButton2.setText("Sincronizar");
		}
		return jButton2;
	}
	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton4() {
		if(jButton4 == null) {
			jButton4 = new javax.swing.JButton();
			jButton4.setText("Sincronizar");
		}
		return jButton4;
	}
	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton1() {
		if(jButton1 == null) {
			jButton1 = new javax.swing.JButton();
			jButton1.setText("Sincronizar");
		}
		return jButton1;
	}
	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton3() {
		if(jButton3 == null) {
			jButton3 = new javax.swing.JButton();
			jButton3.setText("Sincronizar");
		}
		return jButton3;
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
		}
		return jLabel1;
	}
	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton5() {
		if(jButton5 == null) {
			jButton5 = new javax.swing.JButton();
			jButton5.setText("Sincronizar");
		}
		return jButton5;
	}
	/**
	 * This method initializes jButton6
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton6() {
		if(jButton6 == null) {
			jButton6 = new javax.swing.JButton();
			jButton6.setText("Sincronizar");
		}
		return jButton6;
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"
