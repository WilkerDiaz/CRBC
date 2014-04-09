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

import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.StringTokenizer;

import javax.swing.JFrame;

import com.beco.colascr.configurador.ConfiguradorServidor;
import com.beco.colascr.transferencias.InitSincronizador;
import com.beco.colascr.transferencias.ServidorCR;
import com.beco.colascr.transferencias.configuracion.Sesion;
import com.beco.colascr.utiles.MensajesVentanas;

/**
 * Descripción:
 * 
 */

public class ConsolaPrincipal extends JFrame implements MouseListener, KeyListener {

	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JEditorPane jEditorPane = null;
	private javax.swing.JButton jButton2 = null;
	private javax.swing.JScrollPane jScrollPane = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JButton jButton3 = null;
	
	private DetalleSincronizacion detalles = null;
	/**
	 * This is the default constructor
	 */
	public ConsolaPrincipal() {
		super();
		initialize();
		
		jButton.addMouseListener(this);
		jButton1.addMouseListener(this);
		jButton2.addMouseListener(this);
		jButton3.addMouseListener(this);
		
		jButton.addKeyListener(this);
		jButton1.addKeyListener(this);
		jButton2.addKeyListener(this);
		jButton3.addKeyListener(this);

		this.setVisible(true);
		detalles = new DetalleSincronizacion();
		MensajesVentanas.centrarVentana(detalles, false);
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(460, 204);
		this.setContentPane(getJContentPane());
		this.setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/beco/colascr/utiles/iconos/ix32x32/data_replace.png")));
		this.setTitle("Servidor CR - Consola");
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
			jPanel.add(getJPanel3(), null);
			jPanel.add(getJPanel1(), null);
			jPanel.add(getJPanel2(), null);
			jPanel.setBackground(new java.awt.Color(226,226,222));
			jPanel.setPreferredSize(new java.awt.Dimension(450,170));
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
			jPanel2.add(getJButton3(), null);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(getJButton2(), null);
			jPanel2.setBackground(new java.awt.Color(226,226,222));
			jPanel2.setPreferredSize(new java.awt.Dimension(440,36));
		}
		return jPanel2;
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
			layFlowLayout3.setAlignment(java.awt.FlowLayout.CENTER);
			layFlowLayout3.setVgap(0);
			jPanel3.setLayout(layFlowLayout3);
			jPanel3.add(getJLabel(), null);
			jPanel3.add(getJLabel1(), null);
			jPanel3.add(getJLabel2(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(80,128));
			jPanel3.setBackground(new java.awt.Color(226,226,222));
			jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Estado: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
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
			jLabel1.setText("");
			jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/beco/colascr/utiles/iconos/ix48x48/trafficlight_yellow.png")));
			jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			jLabel1.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
			jLabel1.setComponentOrientation(java.awt.ComponentOrientation.UNKNOWN);
			jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.TRAILING);
		}
		return jLabel1;
	}
	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton() {
		if(jButton == null) {
			jButton = new javax.swing.JButton();
			jButton.setText("Detener");
			jButton.setBackground(new java.awt.Color(226,226,222));
			jButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/beco/colascr/utiles/iconos/ix16x16/server_error.png")));
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
			jButton1 = new javax.swing.JButton();
			jButton1.setText("Detalles");
			jButton1.setBackground(new java.awt.Color(226,226,222));
			jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/beco/colascr/utiles/iconos/ix16x16/gear_replace.png")));
		}
		return jButton1;
	}
	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel1() {
		if(jPanel1 == null) {
			jPanel1 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout6 = new java.awt.FlowLayout();
			layFlowLayout6.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout6.setVgap(0);
			jPanel1.setLayout(layFlowLayout6);
			jPanel1.add(getJScrollPane(), null);
			jPanel1.setBackground(new java.awt.Color(226,226,222));
			jPanel1.setPreferredSize(new java.awt.Dimension(355,128));
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mensajes: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
		}
		return jPanel1;
	}
	/**
	 * This method initializes jEditorPane
	 * 
	 * @return javax.swing.JEditorPane
	 */
	private javax.swing.JEditorPane getJEditorPane() {
		if(jEditorPane == null) {
			jEditorPane = new javax.swing.JEditorPane();
			jEditorPane.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
			jEditorPane.setEditable(false);
			jEditorPane.setMargin(new Insets(1,1,1,20));
			jEditorPane.setFocusable(false);
		}
		return jEditorPane;
	}
	/**
	 * Método iniciarServicio
	 * 
	 * 
	 * void
	 */
	public void iniciarServicio() {
		try {
			jButton.setEnabled(false);
			jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/beco/colascr/utiles/iconos/ix48x48/trafficlight_yellow.png")));
			jLabel2.setText("INICIANDO");
			ServidorCR.setEstadoServ(ServidorCR.SERVICIO_INICIANDO);
			new Sesion();
			InitSincronizador iniciaSinc = new InitSincronizador();
			iniciaSinc.start();
			ServidorCR.setEstadoServ(ServidorCR.SERVICIO_INICIADO);
			jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/beco/colascr/utiles/iconos/ix48x48/trafficlight_green.png")));
			jLabel2.setText("INICIADO");
			jButton.setText("Detener");
			jButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/beco/colascr/utiles/iconos/ix16x16/server_error.png")));
			jButton.setEnabled(true);
			MensajesVentanas.mensajeUsuario("Servicio Iniciado: " + new Date());
			this.repaint();
		} catch (Exception e) {
			detenerServicio();
			this.repaint();
		}
	}
	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton2() {
		if(jButton2 == null) {
			jButton2 = new javax.swing.JButton();
			jButton2.setText("Salir");
			jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/beco/colascr/utiles/iconos/ix16x16/stock_exit.png")));
			jButton2.setBackground(new java.awt.Color(226,226,222));
		}
		return jButton2;
	}
	/**
	 * Método enviarMensaje
	 * 
	 * @param mensaje
	 * void
	 */
	public void enviarMensaje(String mensaje) {
		String texto = this.jEditorPane.getText() + mensaje;
		StringTokenizer st = new StringTokenizer(texto, "\n");
		if (st.countTokens()>ServidorCR.NUMERO_LINEAS_MSG) {
			int limiteInferior = ((String)st.nextToken()).length();
			texto = texto.substring(limiteInferior + 1);
		}
		this.jEditorPane.setText(texto + "\n");
		jEditorPane.setSelectionStart(jEditorPane.getText().length());
	}
	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private javax.swing.JScrollPane getJScrollPane() {
		if(jScrollPane == null) {
			jScrollPane = new javax.swing.JScrollPane();
			jScrollPane.setViewportView(getJEditorPane());
			jScrollPane.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			jScrollPane.setPreferredSize(new java.awt.Dimension(338,98));
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
			jLabel.setText(" ");
			jLabel.setPreferredSize(new java.awt.Dimension(45,16));
		}
		return jLabel;
	}
	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel2() {
		if(jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setText("INICIANDO");
		}
		return jLabel2;
	}
	/**
	 * Método mouseClicked
	 *
	 * @param e
	 */
	public void mouseClicked(MouseEvent e) {
		if (e.getButton()==MouseEvent.BUTTON1) {
			if (e.getSource().equals(jButton2)) {
				if (ServidorCR.getEstadoServ()==ServidorCR.SERVICIO_INICIANDO)
					MensajesVentanas.mensajeUsuario("El Sevicio se está Iniciando. Por favor Espere");
				else if (ServidorCR.getEstadoServ()==ServidorCR.SERVICIO_DETENIENDO)
					MensajesVentanas.mensajeUsuario("El Sevicio se está Deteniendo. Por favor Espere");
				else if (ServidorCR.getEstadoServ()==ServidorCR.SERVICIO_DETENIDO)
					System.exit(0);
				else if (ServidorCR.getEstadoServ()==ServidorCR.SERVICIO_INICIADO) {
					detenerServicio();
					System.exit(0);
				}
			}
			else if (e.getSource().equals(jButton)) {
				if (ServidorCR.getEstadoServ()==ServidorCR.SERVICIO_INICIANDO)
					MensajesVentanas.mensajeUsuario("El Sevicio se está Iniciando. Por favor Espere");
				else if (ServidorCR.getEstadoServ()==ServidorCR.SERVICIO_DETENIENDO)
					MensajesVentanas.mensajeUsuario("El Sevicio se está Deteniendo. Por favor Espere");
				else if (ServidorCR.getEstadoServ()==ServidorCR.SERVICIO_DETENIDO)
					iniciarServicio();
				else if (ServidorCR.getEstadoServ()==ServidorCR.SERVICIO_INICIADO)
					detenerServicio();
			}
			else if (e.getSource().equals(jButton3)) {
				if (ServidorCR.getEstadoServ()!=ServidorCR.SERVICIO_DETENIDO)
					MensajesVentanas.mensajeUsuario("Se debe detener el Servicio para realizar la operación");
				else {
					ConfiguradorServidor.configuradorOpcion();
					//new CargarAfiliados();
				}
			}
			else if (e.getSource().equals(jButton1)) {
				detalles.setVisible(true);
				detalles.requestFocus();
			}
		}
	}
	/**
	 * Método detenerServicio
	 * 
	 * 
	 * void
	 */
	private void detenerServicio() {
		jButton.setEnabled(false);
		jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/beco/colascr/utiles/iconos/ix48x48/trafficlight_yellow.png")));
		jLabel2.setText("DETENIENDO");
		ServidorCR.setEstadoServ(ServidorCR.SERVICIO_DETENIENDO);
		this.repaint();
		try {
			ServidorCR.sincronizador.detenerSync();
		} catch (Exception e) {
			MensajesVentanas.mensajeUsuario("Error Finalizando Servicio de Colas.");
		}
		ServidorCR.setEstadoServ(ServidorCR.SERVICIO_DETENIDO);
		jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/beco/colascr/utiles/iconos/ix48x48/trafficlight_red.png")));
		jLabel2.setText("DETENIDO");
		jButton.setText("Iniciar");
		jButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/beco/colascr/utiles/iconos/ix16x16/server_ok.png")));
		jButton.setEnabled(true);
		ServidorCR.sincronizador.actualizarMensajesDetalles();
		ServidorCR.sincronizador.getIntervaloSincAfiliados().setProximaSincronizacion(null);
		ServidorCR.sincronizador.getIntervaloSincAfiliadosTemporales().setProximaSincronizacion(null);
		ServidorCR.sincronizador.getIntervaloSincBases().setProximaSincronizacion(null);
		ServidorCR.sincronizador.getIntervaloSincProductos().setProximaSincronizacion(null);
		ServidorCR.sincronizador.getIntervaloSincVentas().setProximaSincronizacion(null);
		ServidorCR.sincronizador.getIntervaloSincListaRegalos().setProximaSincronizacion(null);
		MensajesVentanas.mensajeUsuario("Servicio Detenido: " + new Date());
		this.repaint();
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
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton3() {
		if(jButton3 == null) {
			jButton3 = new javax.swing.JButton();
			jButton3.setText("Configurar");
			jButton3.setBackground(new java.awt.Color(226,226,222));
			jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/beco/colascr/utiles/iconos/ix16x16/data_disk.png")));
		}
		return jButton3;
	}
	/**
	 * Método keyPressed
	 *
	 * @param e
	 */
	public void keyPressed(KeyEvent e) {
		if ((e.getKeyCode()==KeyEvent.VK_ENTER)||(e.getKeyCode()==KeyEvent.VK_ESCAPE)) {
			if (e.getSource().equals(jButton2)) {
				if (ServidorCR.getEstadoServ()==ServidorCR.SERVICIO_INICIANDO)
					MensajesVentanas.mensajeUsuario("El Sevicio se está Iniciando. Por favor Espere");
				else if (ServidorCR.getEstadoServ()==ServidorCR.SERVICIO_DETENIENDO)
					MensajesVentanas.mensajeUsuario("El Sevicio se está Deteniendo. Por favor Espere");
				else if (ServidorCR.getEstadoServ()==ServidorCR.SERVICIO_DETENIDO)
					System.exit(0);
				else if (ServidorCR.getEstadoServ()==ServidorCR.SERVICIO_INICIADO) {
					detenerServicio();
					System.exit(0);
				}
			}
			else if (e.getSource().equals(jButton)) {
				if (ServidorCR.getEstadoServ()==ServidorCR.SERVICIO_INICIANDO)
					MensajesVentanas.mensajeUsuario("El Sevicio se está Iniciando. Por favor Espere");
				else if (ServidorCR.getEstadoServ()==ServidorCR.SERVICIO_DETENIENDO)
					MensajesVentanas.mensajeUsuario("El Sevicio se está Deteniendo. Por favor Espere");
				else if (ServidorCR.getEstadoServ()==ServidorCR.SERVICIO_DETENIDO)
					iniciarServicio();
				else if (ServidorCR.getEstadoServ()==ServidorCR.SERVICIO_INICIADO)
					detenerServicio();
			}
			else if (e.getSource().equals(jButton3)) {
				if (ServidorCR.getEstadoServ()!=ServidorCR.SERVICIO_DETENIDO)
					MensajesVentanas.mensajeUsuario("Se debe detener el Servicio para realizar la operación");
				else {
					ConfiguradorServidor.configuradorOpcion();
				}
			}
			else if (e.getSource().equals(jButton1)) {
				detalles.setVisible(true);
				detalles.requestFocus();
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
	 * Método getDetalles
	 * 
	 * @return
	 * DetalleSincronizacion
	 */
	public DetalleSincronizacion getDetalles() {
		return detalles;
	}

}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"
