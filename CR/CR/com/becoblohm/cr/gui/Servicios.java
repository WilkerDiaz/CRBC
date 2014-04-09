/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : Servicios.java
 * Creado por : Victor Medina <linux@epa.com.ve>
 * Creado en  : Mar 4, 2004 - 10:52:57 AM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.0.0
 * Fecha       : Mar 4, 2004
 * Analista    : Victor Medina <linux@epa.com.ve>
 * Descripción : Implementación inicial.
 * =============================================================================
 * Versión     : 1.2.0
 * Fecha       : Sep 3, 2004
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Refactorización de las interfaces gráficas de usuario.
 * =============================================================================
 * Versión     : 2.0
 * Fecha       : Jul 11, 2004
 * Analista    : yzambrano
 * Descripción : Recuperar apartado en espera a través del identificador de cliente
 * =============================================================================
 * Versión     : 2.0
 * Fecha       : Jul 25, 2004
 * Analista    : yzambrano
 * Descripción : - Agregada la funcionalidad de Mantenimiento para agregar, modificar y eliminar
 * 				   estados, ciudades y urbanizaciones.
 * =============================================================================
 * Versión     : 2.0
 * Fecha       : Ago 07, 2006
 * Analista    : yzambrano
 * Descripción : - Agregada la funcionalidad de Devolución por cambio.
 * =============================================================================
 */
package com.becoblohm.cr.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.ProductoExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.extensiones.RegistroCliente;
import com.becoblohm.cr.extensiones.RegistroClienteFactory;
import com.becoblohm.cr.gui.apartado.*;
import com.becoblohm.cr.gui.devolucion.*;
import com.becoblohm.cr.gui.listaregalos.CierreListaRegalos1;
import com.becoblohm.cr.gui.listaregalos.ConsultaListaRegalos;
import com.becoblohm.cr.gui.listaregalos.ConsultaListaTitular;
import com.becoblohm.cr.gui.listaregalos.DatosLista;
import com.becoblohm.cr.gui.listaregalos.RecuperarLista;
import com.becoblohm.cr.gui.listaregalos.RegistroListaRegalos;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarservicio.Apartado;
import com.becoblohm.cr.manejarservicio.Cotizacion;
import com.becoblohm.cr.manejarservicio.ListaRegalos;
import com.becoblohm.cr.utiles.FacturaBarCode;
import com.becoblohm.cr.utiles.MensajesVentanas;
/**
 * @author vmedina
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Servicios extends JDialog implements ComponentListener, KeyListener, ActionListener, DocumentListener {

	private static final long serialVersionUID = 1L;

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Servicios.class);  //  @jve:decl-index=0:

	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JPasswordField jPasswordField = null;
	private javax.swing.JButton jButtonAceptar = null;
	private javax.swing.JButton jButtonCancelar = null;
	private javax.swing.JPanel jPanel5 = null;
	private javax.swing.JPanel jPanel6 = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JLabel jLabel5 = null;
	private javax.swing.JPanel jPanel7 = null;
	private javax.swing.JPanel jPanel2 = null;
	private int opcion;
		
	private javax.swing.JButton jButtonF1 = null;
	private javax.swing.JButton jButtonF2 = null;
	private javax.swing.JButton jButtonF3 = null;
	private javax.swing.JButton jButtonF4 = null;
	private javax.swing.JButton jButtonF5 = null;
	private javax.swing.JButton jButtonF6 = null;
	private javax.swing.JButton jButtonF7 = null;
	private javax.swing.JButton jButtonF8 = null;
	private javax.swing.JButton jButtonF9 = null;
	private javax.swing.JButton jButtonF10 = null;
	private javax.swing.JButton jButtonF11 = null;
	private javax.swing.JButton jButtonF12 = null;
	
	private PantallaDevXCambio dxc = null;
	
	private static RegistroApartado ra = null;
	private static PantallaCotizacion pc = null;
	private static PantallaDevolucion pd = null;
	private static PantallaAnulacion pa = null;
	private static VisualizarComandas ventanaComandas = null; 
	private static Mantenimiento mantenimiento= null;
	private static ApartadoAbonoInicial aai = null; 
	private static PantallaApartado pap = null;
	private FacturacionPrincipal principal = null;
	private static PantallaVentaDevolucion pvd = null;
	private static RegistroClientesNuevos registroCliente = null;
	private static SeleccionDevoluciones devolucionesEspera = null;
	//private static RegistroClientesTemp clientTemp = null;
	RegistroClienteFactory factory = new RegistroClienteFactory();
	SimpleDateFormat fechaServ = new SimpleDateFormat("yyyyMMdd");
	FocusListener botonClicker; 
 
 	/**
	 * This is the default constructor
	 */
	public Servicios(FacturacionPrincipal principal) {
		super(MensajesVentanas.ventanaActiva);
		this.principal = principal;
		opcion = 10;
		botonClicker = new java.awt.event.FocusAdapter() { 
			public void focusGained(java.awt.event.FocusEvent e) {
				if (logger.isDebugEnabled())
					logger.debug("focusGained(java.awt.event.FocusEvent) - start");
//				if (e.getSource() instanceof JButton) {
//					MouseEvent mevt = new MouseEvent((Component)e.getSource(), 0, 0, 0, 0, 0, 1, false); 
//					mouseClicked(mevt);
//				}

				if (logger.isDebugEnabled())
					logger.debug("focusGained(java.awt.event.FocusEvent) - end");
			}
		};
		initialize();
		agregarListeners();
		CR.inputEscaner.getDocument().addDocumentListener(this);
	}
	
	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		if (logger.isDebugEnabled())
			logger.debug("initialize() - start");

		this.setContentPane(getJContentPane());
		this.setSize(560, 512);
		this.setContentPane(getJContentPane());
		this.setUndecorated(true);
		this.setModal(true);
		this.setBackground(new java.awt.Color(226,226,222));
		this.addComponentListener(this);

		Component[] compList = {jPasswordField,jButtonAceptar,jButtonCancelar};
		this.setFocusTraversalPolicy(new PoliticasDeFoco(compList));

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
			java.awt.FlowLayout layFlowLayout7 = new java.awt.FlowLayout();
			layFlowLayout7.setHgap(5);
			layFlowLayout7.setVgap(5);
			jContentPane.setLayout(layFlowLayout7);
			jContentPane.add(getJPanel7(), null);
			jContentPane.add(getJPanel2(), null);
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.SoftBevelBorder.RAISED));
			jContentPane.setBackground(new java.awt.Color(226,226,222));
			jContentPane.setPreferredSize(new java.awt.Dimension(560,512));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJContentPane() - end");
		return jContentPane;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("keyPressed(KeyEvent) - start");

		eliminarListeners();
		
		//Mapeo de ESC
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			dispose();
		}
		
		//Mapeo de ENTER
		else if((e.getKeyCode() == KeyEvent.VK_ENTER)){
			if (e.getSource().equals(getJButtonCancelar())) {
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				dispose();
			} else ejecutarAccion(e);
		}
		
		//Mapeo de F1
		else if(e.getKeyCode() == KeyEvent.VK_F1){
			jLabel5.setText("Recuperar Devolución en Espera");
			jButtonF1.requestFocus();
			this.opcion = 10;
		}
		
		//Mapeo de F2
		else if(e.getKeyCode() == KeyEvent.VK_F2){
			jLabel5.setText("Recuperar Venta para Devolución por Cambio");
			jButtonF2.requestFocus();
			this.opcion = 8;
		}
		
		//Mapeo de F3
		else if(e.getKeyCode() == KeyEvent.VK_F3){
			jLabel5.setText("Recuperar Venta para Devolución por Dinero");
			jButtonF3.requestFocus();
			this.opcion = 1;
		}
	
		//Mapeo de F4
		else if(e.getKeyCode() == KeyEvent.VK_F4){
			jLabel5.setText("Recuperar Venta para Anulación");
			jButtonF4.requestFocus();
			this.opcion = 2;
		}

		//Mapeo de F5
		else if(e.getKeyCode() == KeyEvent.VK_F5){
			jLabel5.setText("Registrar Apartado/Pedido Especial");
			jButtonF5.requestFocus();
			this.opcion = 3;
		}

		//Mapeo de F6
		else if(e.getKeyCode() == KeyEvent.VK_F6){
			jLabel5.setText("Recuperar Apartado/Pedido Especial");
			jButtonF6.requestFocus();
			this.opcion = 4;
		}
	
		//Mapeo de F7

		//CAMBIADO POR MGRILLO PARA AGREGAR EL MANEJO DE COTIZACIONES!!!}
		/*
		 Codigo anterior:
		/* <-- estaba documentado este codigo
		else if(e.getKeyCode() == KeyEvent.VK_F7){
			jLabel5.setText("Recuperar Cotización");
			jButtonF7.requestFocus();
			this.opcion = 5;
		}


		 */

		else if(e.getKeyCode() == KeyEvent.VK_F7 && getJButtonF7().isEnabled()){
			jLabel5.setText("Recuperar Cotización");
			jButtonF7.requestFocus();
			this.opcion = 5;
		}
		
/*	
		//Mapeo de F8
		else if(e.getKeyCode() == KeyEvent.VK_F8){
			jLabel5.setText("Recuperar Comanda");
			jButtonF8.requestFocus();
			this.opcion = 6;
		}
*/
		//Mapeo de F9
		else if(e.getKeyCode() == KeyEvent.VK_F9){
			jLabel5.setText("Registrar Lista de Regalos");
			jButtonF9.requestFocus();
			this.opcion = 11;
		}
		
		//Mapeo de F10
		else if(e.getKeyCode() == KeyEvent.VK_F10){
			jLabel5.setText("Recuperar Lista de Regalos");
			jButtonF10.requestFocus();
			this.opcion = 12;
			e.consume();
		}
		
		//Mapeo de F11
		else if(e.getKeyCode() == KeyEvent.VK_F11){
			jLabel5.setText("Recuperar Lista de Regalos en Espera");
			jButtonF11.requestFocus();
			this.opcion = 13;
			e.consume();
		}

		//Mapeo de F12
		else if(e.getKeyCode() == KeyEvent.VK_F12) {
			jLabel5.setText("Registro de nuevo afiliado");
			jButtonF12.requestFocus();
			this.opcion = 9;
		}		
			
		/*else if(e.getKeyCode() == KeyEvent.VK_F11) {
			jLabel5.setText("Mantenimiento de estados, ciudades y urbanizaciones");
			this.opcion = 7;
		}*/

		//Mapeo de ESPACE sobre cualquiera de los botones
		else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			if(e.getSource().equals(jButtonAceptar))
				ejecutarAccion(e);
			else if(e.getSource().equals(jButtonCancelar)) {
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				dispose();
			}
		}
		
		// Mapeo de cualquier numero fuera del cuadro de texto
		else if (Character.isLetterOrDigit(e.getKeyChar()) 
		   && !e.getSource().equals(jPasswordField)) {
			jPasswordField.requestFocus();
			
			if((String.valueOf(jPasswordField.getPassword()).trim()).equals("") || jPasswordField.getPassword() == null)
				jPasswordField.setEchoChar('\000');
				
			jPasswordField.setText(String.valueOf(jPasswordField.getPassword()) + e.getKeyChar());
		}
		
		else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			if(e.getSource().getClass().isInstance(jButtonAceptar))
				((JButton)e.getSource()).transferFocus();
			else 	
				((JPasswordField)e.getSource()).transferFocus();
		}

		else if(e.getKeyCode() == KeyEvent.VK_UP) {
			if(e.getSource().getClass().isInstance(jButtonAceptar))
				((JButton)e.getSource()).transferFocusBackward();
			else 	
				((JPasswordField)e.getSource()).transferFocusBackward();
		}
		else if(e.getKeyCode() == KeyEvent.VK_TAB) {
			if(e.getSource().getClass().isInstance(jButtonAceptar))
				((JButton)e.getSource()).transferFocus();
			else 	
				((JPasswordField)e.getSource()).transferFocus();
		}

		else if(e.getSource().equals(getJPasswordField()))
			if((String.valueOf(jPasswordField.getPassword()).trim()).equals("") || jPasswordField.getPassword() == null)
				jPasswordField.setEchoChar('\000');
		
		agregarListeners();

		if (logger.isDebugEnabled())
			logger.debug("keyPressed(KeyEvent) - end");
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

		if (logger.isDebugEnabled())
			logger.debug("keyTyped(KeyEvent) - end");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("actionPerformed(ActionEvent) - start");

		eliminarListeners();
		
		//Mapeo de MouseClicked sobre Aceptar
		if(e.getSource().equals(jButtonAceptar))
			/*if (((opcion == 3) || (opcion == 7) || (opcion == 9)) || (((opcion == 1) || (opcion == 2) || (opcion == 4) || (opcion == 5) || (opcion == 6) || (opcion == 8))
				&& (!String.valueOf(jPasswordField.getPassword()).equals(""))))*/			
			ejecutarAccion();
			/*else
				MensajesVentanas.aviso("Debe especificar un identificador");*/
		else if(e.getSource().equals(jButtonF3)){
			jLabel5.setText("Recuperar Venta para Devolución por Dinero");
			this.opcion = 1;
		}
	
		else if(e.getSource().equals(jButtonF4)){
			jLabel5.setText("Recuperar Venta para Anulación");
			this.opcion = 2;
		}

		else if(e.getSource().equals(jButtonF5)){
			jLabel5.setText("Registrar Apartado/Pedido Especial");
			this.opcion = 3;
		}

		else if(e.getSource().equals(jButtonF6)){
			jLabel5.setText("Recuperar Apartado/Pedido Especial");
			this.opcion = 4;
		}
	
		else if(e.getSource().equals(jButtonF7)){
			jLabel5.setText("Recuperar Cotización");
			this.opcion = 5;
		}
	
		else if(e.getSource().equals(jButtonF8)){
			jLabel5.setText("Recuperar Comanda");
			this.opcion = 6;
		}
		
		/*else if(e.getSource().equals(jButton8)){
			jLabel5.setText("Mantenimiento de estados, ciudades y urbanizaciones");
			this.opcion = 7;
		}*/
		
		else if(e.getSource().equals(jButtonF2)){
			jLabel5.setText("Recuperar Venta para Devolución por Cambio");
			this.opcion = 8;
		}
	
		
		else if(e.getSource().equals(jButtonF9)){
			jLabel5.setText("Registrar Lista de Regalos");
			this.opcion = 11;
		}
		
		else if(e.getSource().equals(jButtonF10)){
			jLabel5.setText("Recuperar Lista de Regalos");
			this.opcion = 12;
		}
		
		else if(e.getSource().equals(jButtonF11)){
			jLabel5.setText("Recuperar Lista de Regalos en Espera");
			this.opcion = 13;
		}
			
		
		else if(e.getSource().equals(jButtonF12)){
			jLabel5.setText("Registro de nuevo afiliado");
			this.opcion = 9;
		}
		
		else if(e.getSource().equals(jButtonF1)){
			jLabel5.setText("Recuperar Devolución en Espera");
			this.opcion = 10;
		}
		
		
		//Mapeo de MouseClicked sobre Cancelar
		else if(e.getSource().equals(jButtonCancelar)){
			CR.inputEscaner.getDocument().removeDocumentListener(this);
			dispose();
		}

		agregarListeners();

		if (logger.isDebugEnabled())
			logger.debug("actionPerformed(ActionEvent) - end");
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
			java.awt.FlowLayout layFlowLayout3 = new java.awt.FlowLayout();
			layFlowLayout3.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout3.setHgap(0);
			layFlowLayout3.setVgap(0);
			jPanel.setLayout(layFlowLayout3);
			jPanel.add(getJPanel5(), null);
			jPanel.add(getJPanel6(), null);
			jPanel.setPreferredSize(new java.awt.Dimension(537,70));
			jPanel.setBackground(new java.awt.Color(69,107,127));
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
			java.awt.FlowLayout layFlowLayout5 = new java.awt.FlowLayout();
			layFlowLayout5.setAlignment(java.awt.FlowLayout.CENTER);
			layFlowLayout5.setHgap(0);
			layFlowLayout5.setVgap(0);
			jPanel1.setLayout(layFlowLayout5);
			jPanel1.add(getJPanel3(), null);
			jPanel1.add(getJPanel4(), null);
			jPanel1.setPreferredSize(new java.awt.Dimension(540,355));
			jPanel1.setBackground(new java.awt.Color(242,242,238));
			jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel1() - end");
		return jPanel1;
	}
	/**
	 * This method initializes jPanel3
	 *
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel3() {
		if (logger.isDebugEnabled())
			logger.debug("getJPanel3() - start");

		if(jPanel3 == null) {
			jPanel3 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout4 = new java.awt.FlowLayout();
			layFlowLayout4.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout4.setHgap(1);
			layFlowLayout4.setVgap(1);
			jPanel3.setLayout(layFlowLayout4);
			jPanel3.add(getJPasswordField(), null);
			jPanel3.setPreferredSize(new java.awt.Dimension(200,55));
			jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Identificador", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.black));
			jPanel3.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel3() - end");
		return jPanel3;
	}
	/**
	 * This method initializes jPanel4
	 *
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel4() {
		if (logger.isDebugEnabled())
			logger.debug("getJPanel4() - start");

		if(jPanel4 == null) {
			jPanel4 = new javax.swing.JPanel();
			java.awt.GridLayout layGridLayout1 = new java.awt.GridLayout();
			layGridLayout1.setRows(12);
			layGridLayout1.setColumns(1);
			jPanel4.setLayout(layGridLayout1);
			jPanel4.add(getJButtonF1(), null);
			jPanel4.add(getJButtonF2(), null);
			jPanel4.add(getJButtonF3(), null);
			jPanel4.add(getJButtonF4(), null);
			jPanel4.add(getJButtonF5(), null);
			jPanel4.add(getJButtonF6(), null);
			jPanel4.add(getJButtonF7(), null);
			jPanel4.add(getJButtonF8(), null);
			jPanel4.add(getJButtonF9(), null);
			jPanel4.add(getJButtonF10(), null);
			jPanel4.add(getJButtonF11(), null);
			jPanel4.add(getJButtonF12(), null);
			jPanel4.setPreferredSize(new java.awt.Dimension(332,350));
			jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opciones: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			jPanel4.setBackground(new java.awt.Color(242,242,238));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel4() - end");
		return jPanel4;
	}
	/**
	 * This method initializes jTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JPasswordField getJPasswordField() {
		if (logger.isDebugEnabled())
			logger.debug("getJPasswordField() - start");

		if(jPasswordField == null) {
			jPasswordField = new JPasswordField();
			jPasswordField.setPreferredSize(new java.awt.Dimension(180,20));	
			jPasswordField.setEchoChar('\000');
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPasswordField() - end");
		return jPasswordField;
	}
	/**
	 * This method initializes jButtonAceptar
	 *
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButtonAceptar() {
		if (logger.isDebugEnabled())
			logger.debug("getJButton() - start");

		if(jButtonAceptar == null) {
			jButtonAceptar = new JHighlightButton();
			jButtonAceptar.setText("Aceptar");
			jButtonAceptar.setBackground(new java.awt.Color(226,226,222));
			jButtonAceptar.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
			jButtonAceptar.setPreferredSize(new java.awt.Dimension(105,26));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJButton() - end");
		return jButtonAceptar;
	}
	/**
	 * This method initializes jButtonCancelar
	 *
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButtonCancelar() {
		if (logger.isDebugEnabled())
			logger.debug("getJButton1() - start");

		if(jButtonCancelar == null) {
			jButtonCancelar = new JHighlightButton();
			jButtonCancelar.setText("Cancelar");
			jButtonCancelar.setBackground(new java.awt.Color(226,226,222));
			jButtonCancelar.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJButton1() - end");
		return jButtonCancelar;
	}
	/**
	 * This method initializes jPanel5
	 *
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel5() {
		if (logger.isDebugEnabled())
			logger.debug("getJPanel5() - start");

		if(jPanel5 == null) {
			jPanel5 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
			layFlowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel5.setLayout(layFlowLayout2);
			jPanel5.add(getJLabel(), null);
			jPanel5.setPreferredSize(new java.awt.Dimension(537,50));
			jPanel5.setBackground(new java.awt.Color(69,107,127));
			layFlowLayout2.setHgap(5);
			layFlowLayout2.setVgap(10);
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel5() - end");
		return jPanel5;
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
			layFlowLayout11.setAlignment(java.awt.FlowLayout.RIGHT);
			layFlowLayout11.setHgap(5);
			layFlowLayout11.setVgap(1);
			jPanel6.setLayout(layFlowLayout11);
			jPanel6.add(getJLabel5(), null);
			jPanel6.setPreferredSize(new java.awt.Dimension(537,20));
			jPanel6.setBackground(new java.awt.Color(69,107,127));
			
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel6() - end");
		return jPanel6;
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
			jLabel.setText("Servicios");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel.setForeground(java.awt.Color.white);
			jLabel.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/cube_molecule.png")));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJLabel() - end");
		return jLabel;
	}
	/**
	 * This method initializes jLabel5
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel5() {
		if (logger.isDebugEnabled())
			logger.debug("getJLabel5() - start");

		if(jLabel5 == null) {
			jLabel5 = new javax.swing.JLabel();
			jLabel5.setText("Recuperar Devolución en Espera");
			jLabel5.setForeground(java.awt.Color.white);
		}

		if (logger.isDebugEnabled())
			logger.debug("getJLabel5() - end");
		return jLabel5;
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
			java.awt.FlowLayout layFlowLayout31 = new java.awt.FlowLayout();
			layFlowLayout31.setHgap(0);
			layFlowLayout31.setVgap(0);
			jPanel7.setLayout(layFlowLayout31);
			jPanel7.add(getJPanel(), null);
			jPanel7.add(getJPanel1(), null);
			jPanel7.setPreferredSize(new java.awt.Dimension(540,426));
			jPanel7.setBackground(new java.awt.Color(242,242,238));
			jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel7() - end");
		return jPanel7;
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
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setHgap(0);
			layFlowLayout1.setVgap(0);
			layFlowLayout1.setAlignment(java.awt.FlowLayout.RIGHT);
			jPanel2.setLayout(layFlowLayout1);
			jPanel2.add(getJButtonAceptar(), null);
			jPanel2.add(getJButtonCancelar(), null);
			
			jPanel2.setPreferredSize(new java.awt.Dimension(540,33));
			jPanel2.setBackground(new java.awt.Color(226,226,222));
			
		}

		if (logger.isDebugEnabled())
			logger.debug("getJPanel2() - end");
		return jPanel2;
	}

	/**
	 * Método ejecutarAccion
	 * 		Recupera la transacción según sea el caso indicado.
	 */
	private void ejecutarAccion() {
		if (logger.isDebugEnabled())
			logger.debug("ejecutarAccion() - start");

		ejecutarAccion(null);

		if (logger.isDebugEnabled())
			logger.debug("ejecutarAccion() - end");
	}
	
	/**
	 * Método ejecutarAccion
	 * 		Recupera la transacción según sea el caso indicado.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private void ejecutarAccion(KeyEvent k) {
		if (logger.isDebugEnabled())
			logger.debug("ejecutarAccion(KeyEvent) - start");

		String lectura;
		CR.inputEscaner.getDocument().removeDocumentListener(this);

		//CAMBIADO POR MGRILLO PARA AGREGAR EL MANEJO DE COTIZACIONES!!!}
		/*
		 Codigo anterior:
		if ((this.opcion==10)||(this.opcion==7)||(this.opcion==9)||(this.opcion==3)||(this.opcion==11)||(this.opcion==12)||((String.valueOf(jPasswordField.getPassword())!=null)&&(!String.valueOf(jPasswordField.getPassword()).equals("")))) {
		 */
		if ((this.opcion==5)||(this.opcion==10)||(this.opcion==7)||(this.opcion==9)||(this.opcion==3)||(this.opcion==11)||(this.opcion==12)||((String.valueOf(jPasswordField.getPassword())!=null)&&(!String.valueOf(jPasswordField.getPassword()).equals("")))) {
			switch (this.opcion) {
				case 1:
					lectura = String.valueOf(jPasswordField.getPassword()).trim();
					try {
						FacturaBarCode fbc = new FacturaBarCode(lectura);
						int tda = fbc.getNumTienda();
						String fecha = fbc.getFechaSQL();
						if(fecha != null) // WDIAZ:08-2013 condición para validar Fecha correcta, 
						{
							int caja = fbc.getNumCaja();
							int transaccion = fbc.getNumTransaccion();
							CR.meVenta.iniciarDevolucion(tda,fecha,caja,transaccion);
							dispose();
							if (CR.meVenta.getDevolucion().getVentaOriginal().getCliente().getCodCliente() == null) {
								//clientTemp = null;
								//clientTemp = new RegistroClientesTemp();
								RegistroCliente registro = factory.getInstance();
								registro.MostrarPantallaCliente(false,1);
								//MensajesVentanas.centrarVentanaDialogo(clientTemp);
							}
							pd = null;
							pd = new PantallaDevolucion(tda, caja, transaccion, CR.meVenta.getDevolucion().getVentaOriginal().getFechaTrans());
							MensajesVentanas.centrarVentanaDialogo(pd);
						}else
							MensajesVentanas.mensajeError(("Data no Válida para la opción Seleccionada, Intente nuevamente "));
						
					} catch (ConexionExcepcion e1) {
						logger.error("ejecutarAccion(KeyEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (ExcepcionCr e1) {
						logger.error("ejecutarAccion(KeyEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (Exception e1) {
						logger.error("ejecutarAccion(KeyEvent)", e1);

						MensajesVentanas.mensajeError("Identificador de transaccion inválido.\nNo se pudo recuperar la transaccion");
						CR.inputEscaner.getDocument().addDocumentListener(this);
					}
					break;
				case 2:
					lectura = String.valueOf(jPasswordField.getPassword()).trim();
					try {
						FacturaBarCode fbc = new FacturaBarCode(lectura);
						int tda = fbc.getNumTienda();
						String fecha = fbc.getFechaSQL();
						if(fecha != null) // WDIAZ:08-2013 condición para validar Fecha correcta, 
						{
							int caja = fbc.getNumCaja();
							int transaccion = fbc.getNumTransaccion();
							CR.meVenta.iniciarAnulacion(tda,fecha,caja,transaccion);
							pa = null;
							pa = new PantallaAnulacion(tda, caja, transaccion, CR.meVenta.getAnulacion().getFechaTrans());
							dispose();
							MensajesVentanas.centrarVentanaDialogo(pa);
						}else
							MensajesVentanas.mensajeError(("Data no Válida para la opción Seleccionada, Intente nuevamente "));
						
					} catch (ConexionExcepcion e) {
						logger.error("ejecutarAccion(KeyEvent)", e);

						MensajesVentanas.mensajeError(e.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (ExcepcionCr e) {
						logger.error("ejecutarAccion(KeyEvent)", e);

						MensajesVentanas.mensajeError(e.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (Exception e) {
						logger.error("ejecutarAccion(KeyEvent)", e);

						MensajesVentanas.mensajeError("Identificador de transaccion inválido.\nNo se pudo recuperar la transaccion");
						CR.inputEscaner.getDocument().addDocumentListener(this);
					}
					break;
				case 3: 
					if(Sesion.isCajaEnLinea())
						try {
							dispose();
							//Modificado para apartados especiales
							if(CR.meServ.getApartado() == null)
								CR.meServ.crearApartado(String.valueOf(jPasswordField.getPassword()).trim());
							Vector<Vector<Object>> tiposApartadosEspeciales = CR.meServ.obtenerTiposApartados();
							if(tiposApartadosEspeciales.size()==0){
								ra = null;
								ra = new RegistroApartado();
								MensajesVentanas.centrarVentanaDialogo(ra);
							} else {
								TiposApartado tipo = new TiposApartado(tiposApartadosEspeciales);
								MensajesVentanas.centrarVentanaDialogo(tipo);
							}
						} catch (ConexionExcepcion e2) {
							logger.error("ejecutarAccion(KeyEvent)", e2);

							MensajesVentanas.mensajeError(e2.getMensaje());
						} catch (ExcepcionCr e2) {
							logger.error("ejecutarAccion(KeyEvent)", e2);

							MensajesVentanas.mensajeError(e2.getMensaje());
						}
					else MensajesVentanas.aviso("El sistema se encuentra operando Fuera de Línea");	
					break;
					
					
				case 4:
					if(Sesion.isCajaEnLinea()){				
					lectura = String.valueOf(jPasswordField.getPassword()).trim();
					try {
						int tda = new Integer(lectura.substring(0,3)).intValue();
						String fecha = lectura.substring(3,11);
						int numApartado = new Integer(lectura.substring(11)).intValue();
						CR.meServ.cargarApartado(tda, fecha, numApartado,'O');
						if(CR.meServ.getApartado().getAbonos().size() <=0 && (CR.meServ.getApartado().getEstadoServicio() == Sesion.APARTADO_ACTIVO || CR.meServ.getApartado().getEstadoServicio() == Sesion.APARTADO_VENCIDO) ) {
							aai = null;
							aai = new ApartadoAbonoInicial();
							dispose();
							MensajesVentanas.centrarVentanaDialogo(aai);
						} else {
							pap = null;
							pap = new PantallaApartado();
							dispose();
							MensajesVentanas.centrarVentanaDialogo(pap);
						}
					} catch (ConexionExcepcion e1) {
						logger.error("ejecutarAccion(KeyEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (ExcepcionCr e1) {
						logger.error("ejecutarAccion(KeyEvent) - Catch Manejado");

						try {
							Vector<Apartado> apartados = CR.meServ.obtenerApartados(lectura);
							if (apartados.size()>1) {
								SeleccionApartados sApartados = new SeleccionApartados(lectura,apartados);
								MensajesVentanas.centrarVentanaDialogo(sApartados);
								if (sApartados.filaSeleccionada()) {
									int tda = sApartados.obtenerTiendaSeleccionada();
									int numServ = sApartados.obtenerNumServSeleccionado();
									String fecha = fechaServ.format(sApartados.obtenerFechaSeleccionada());
									char tipoApartado = sApartados.obtenerTipoApartado();
									CR.meServ.cargarApartado(tda,fecha, numServ, tipoApartado);
									if(CR.meServ.getApartado().getAbonos().size() <=0 && (CR.meServ.getApartado().getEstadoServicio() == Sesion.APARTADO_ACTIVO || CR.meServ.getApartado().getEstadoServicio() == Sesion.APARTADO_VENCIDO) ) {
										aai = null;
										aai = new ApartadoAbonoInicial();
										dispose();
										MensajesVentanas.centrarVentanaDialogo(aai);
									} else {
										pap = null;
										pap = new PantallaApartado();
										dispose();
										MensajesVentanas.centrarVentanaDialogo(pap);
									}
								} else
									dispose();
							} else {
								int tda = ((Apartado)apartados.firstElement()).getCodTienda();
								int numServ = ((Apartado)apartados.firstElement()).getNumServicio();
								String fecha = fechaServ.format(((Apartado)apartados.firstElement()).getFechaServicio());
								char tipoApartado = ((Apartado)apartados.firstElement()).getEstadoServicio();
								CR.meServ.cargarApartado(tda,fecha,numServ,tipoApartado);
								if(CR.meServ.getApartado().getAbonos().size() <=0 && (CR.meServ.getApartado().getEstadoServicio() == Sesion.APARTADO_ACTIVO || CR.meServ.getApartado().getEstadoServicio() == Sesion.APARTADO_VENCIDO) ) {
									aai = null;
									aai = new ApartadoAbonoInicial();
									dispose();
									MensajesVentanas.centrarVentanaDialogo(aai);
								} else {
									pap = null;
									pap = new PantallaApartado();
									dispose();
									MensajesVentanas.centrarVentanaDialogo(pap);
								}
							}
						} catch (ConexionExcepcion e2) {
							logger.error("ejecutarAccion(KeyEvent)", e2);

							MensajesVentanas.mensajeError(e2.getMensaje());
							CR.inputEscaner.getDocument().addDocumentListener(this);
						} catch (ExcepcionCr e2) {
							logger.error("ejecutarAccion(KeyEvent)", e2);

							MensajesVentanas.mensajeError(e2.getMensaje());
							CR.inputEscaner.getDocument().addDocumentListener(this);
						} catch (Exception e2) {
							logger.error("ejecutarAccion(KeyEvent)", e2);

							MensajesVentanas.mensajeError("Identificador de apartado inválido.\nNo se pudo recuperar el apartado");
							CR.inputEscaner.getDocument().addDocumentListener(this);
						}
					} catch (Exception e1) {
						logger.error("ejecutarAccion(KeyEvent) - Catch Manejado");
						try {
							Vector<Apartado> apartados = CR.meServ.obtenerApartados(lectura);
							if (apartados.size()>1) {
								SeleccionApartados sApartados = new SeleccionApartados(lectura,apartados);
								MensajesVentanas.centrarVentanaDialogo(sApartados);
								if (sApartados.filaSeleccionada()) {
									int tda = sApartados.obtenerTiendaSeleccionada();
									int numServ = sApartados.obtenerNumServSeleccionado();
									char tipoApartado = sApartados.obtenerTipoApartado();
									String fecha = fechaServ.format(sApartados.obtenerFechaSeleccionada());
									CR.meServ.cargarApartado(tda,fecha, numServ, tipoApartado);
									if(CR.meServ.getApartado().getAbonos().size() <=0 && (CR.meServ.getApartado().getEstadoServicio() == Sesion.APARTADO_ACTIVO || CR.meServ.getApartado().getEstadoServicio() == Sesion.APARTADO_VENCIDO) ) {
										aai = null;
										aai = new ApartadoAbonoInicial();
										dispose();
										MensajesVentanas.centrarVentanaDialogo(aai);
									} else if (CR.meServ.getApartado().getEstadoServicio() == Sesion.ESPERA)
									{
										if(Sesion.isCajaEnLinea())
											try {
												dispose();
												ra = null;
												ra = new RegistroApartado();
												MensajesVentanas.centrarVentanaDialogo(ra);
											} catch (ConexionExcepcion e2) {
												logger.error("ejecutarAccion(KeyEvent)", e2);

												MensajesVentanas.mensajeError(e2.getMensaje());
											} catch (ExcepcionCr e2) {
												logger.error("ejecutarAccion(KeyEvent)", e2);

												MensajesVentanas.mensajeError(e2.getMensaje());
											}
										else MensajesVentanas.aviso("El sistema se encuentra operando Fuera de Línea");	
									}
									else
									{
										pap = null;
										pap = new PantallaApartado();
										dispose();
										MensajesVentanas.centrarVentanaDialogo(pap);
									}
								} else
									dispose();
							} else {
								int tda = ((Apartado)apartados.firstElement()).getCodTienda();
								int numServ = ((Apartado)apartados.firstElement()).getNumServicio();
								String fecha = fechaServ.format(((Apartado)apartados.firstElement()).getFechaServicio());
								char tipoApartado = ((Apartado)apartados.firstElement()).getEstadoServicio();
								CR.meServ.cargarApartado(tda,fecha,numServ, tipoApartado);
								if(CR.meServ.getApartado().getAbonos().size() <=0 && (CR.meServ.getApartado().getEstadoServicio() == Sesion.APARTADO_ACTIVO || CR.meServ.getApartado().getEstadoServicio() == Sesion.APARTADO_VENCIDO) ) {
									aai = null;
									aai = new ApartadoAbonoInicial();
									dispose();
									MensajesVentanas.centrarVentanaDialogo(aai);
								} else if (CR.meServ.getApartado().getEstadoServicio() == Sesion.ESPERA)
								{
									if(Sesion.isCajaEnLinea())
										try {
											dispose();
											ra = null;
											ra = new RegistroApartado();
											MensajesVentanas.centrarVentanaDialogo(ra);
										} catch (ConexionExcepcion e2) {
											logger.error("ejecutarAccion(KeyEvent)", e2);

											MensajesVentanas.mensajeError(e2.getMensaje());
										} catch (ExcepcionCr e2) {
											logger.error("ejecutarAccion(KeyEvent)", e2);

											MensajesVentanas.mensajeError(e2.getMensaje());
										}
									else MensajesVentanas.aviso("El sistema se encuentra operando Fuera de Línea");
								}
								else {
									pap = null;
									pap = new PantallaApartado();
									dispose();
									MensajesVentanas.centrarVentanaDialogo(pap);
								}
							}
						} catch (ConexionExcepcion e2) {
							logger.error("ejecutarAccion(KeyEvent)", e2);

							MensajesVentanas.mensajeError(e2.getMensaje());
							CR.inputEscaner.getDocument().addDocumentListener(this);
						} catch (ExcepcionCr e2) {
							logger.error("ejecutarAccion(KeyEvent)", e2);

							MensajesVentanas.mensajeError(e2.getMensaje());
							CR.inputEscaner.getDocument().addDocumentListener(this);
						} catch (Exception e2) {
							logger.error("ejecutarAccion(KeyEvent)", e2);

							MensajesVentanas.mensajeError("Identificador de apartado inválido.\nNo se pudo recuperar el apartado");
							CR.inputEscaner.getDocument().addDocumentListener(this);
						}
					}
					} else MensajesVentanas.aviso("El sistema se encuentra operando Fuera de Línea");
					break;
					
					
				case 5:
					if(Sesion.isCajaEnLinea()){
					lectura = String.valueOf(jPasswordField.getPassword()).trim();
					try {
						String identificador = lectura.trim();
						System.out.println("*****SE RECUPERARARA LA COTIZACION "+identificador+"****");
						Vector<Cotizacion> cotizaciones = CR.meServ.obtenerCotizaciones(identificador);
						if (cotizaciones.size()>1) {
							SeleccionCotizaciones sCotizaciones = new SeleccionCotizaciones(identificador,cotizaciones);
							MensajesVentanas.centrarVentanaDialogo(sCotizaciones);
							if (sCotizaciones.filaSeleccionada()) {
								int tda = sCotizaciones.obtenerTiendaSeleccionada();
								int numServ = sCotizaciones.obtenerNumServSeleccionado();
								Date fecha = sCotizaciones.obtenerFechaSeleccionada();
								CR.meServ.recuperarCotizacion(tda,numServ,fecha);
								pc = null;
								pc = new PantallaCotizacion(CR.meServ.getCotizacion().getCodTienda(),CR.meServ.getCotizacion().getNumServicio(),CR.meServ.getCotizacion().getFechaServicio());
								dispose();
								MensajesVentanas.centrarVentanaDialogo(pc);
							} else
								dispose();
						} else {
							int tda = ((Cotizacion)cotizaciones.firstElement()).getCodTienda();
							int numServ = ((Cotizacion)cotizaciones.firstElement()).getNumServicio();
							Date fecha = ((Cotizacion)cotizaciones.firstElement()).getFechaServicio();
							CR.meServ.recuperarCotizacion(tda,numServ,fecha);
							pc = null;
							pc = new PantallaCotizacion(CR.meServ.getCotizacion().getCodTienda(),CR.meServ.getCotizacion().getNumServicio(),CR.meServ.getCotizacion().getFechaServicio());
							dispose();
							MensajesVentanas.centrarVentanaDialogo(pc);
						}
					} catch (ConexionExcepcion e1) {
						logger.error("ejecutarAccion(KeyEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (ExcepcionCr e1) {
						logger.error("ejecutarAccion(KeyEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (Exception e1) {
						logger.error("ejecutarAccion(KeyEvent)", e1);

						MensajesVentanas.mensajeError("Identificador de cotización inválido.\nNo se pudo recuperar la cotización");
						CR.inputEscaner.getDocument().addDocumentListener(this);
					}
					} else MensajesVentanas.aviso("El sistema se encuentra operando Fuera de Línea");
					break;
				case 6: 
					if(Sesion.isCajaEnLinea())
						try {
							Vector<Vector<Object>> comandas = CR.meVenta.recuperarComanda(String.valueOf(jPasswordField.getPassword()));
							if(comandas != null){
								dispose();
								ventanaComandas = null; 
								ventanaComandas = new VisualizarComandas(comandas);
								ventanaComandas.setIdentificador(String.valueOf(jPasswordField.getPassword()));
								MensajesVentanas.centrarVentanaDialogo(ventanaComandas);
								if(ventanaComandas.getProductosFacturar() != null)
									CR.meVenta.agregarDetalleComanda(ventanaComandas.getProductosFacturar());
							}
							else MensajesVentanas.mensajeError("Para realizar esta operación la caja debe estar en línea.");
						} catch (ConexionExcepcion e1) {
							logger.error("ejecutarAccion(KeyEvent)", e1);

							MensajesVentanas.mensajeError(e1.getMensaje());
							CR.inputEscaner.getDocument().addDocumentListener(this);
						} catch (ExcepcionCr e1) {
							logger.error("ejecutarAccion(KeyEvent)", e1);

							MensajesVentanas.mensajeError(e1.getMensaje());
							CR.inputEscaner.getDocument().addDocumentListener(this);
						}
					else MensajesVentanas.aviso("El sistema se encuentra operando Fuera de Línea");
					break;
					
				case 7:
					dispose();
					
					mantenimiento = null; 
					try {
						mantenimiento = new Mantenimiento(true);
						CR.meServ.iniciarMantenimiento();
						MensajesVentanas.centrarVentanaDialogo(mantenimiento);
					} catch (MaquinaDeEstadoExcepcion e2) {
						MensajesVentanas.mensajeError(e2.getMensaje());
					} catch (XmlExcepcion e2) {
						e2.printStackTrace();
					} catch (FuncionExcepcion e2) {
						e2.printStackTrace();
					} catch (BaseDeDatosExcepcion e2) {
						e2.printStackTrace();
					} catch (ConexionExcepcion e2) {
						e2.printStackTrace();
					} catch (UsuarioExcepcion e) {
						MensajesVentanas.mensajeError(e.getMensaje());
					} catch (ExcepcionCr e) {
						e.printStackTrace();
					}
					
				break;
				case 8:
					lectura = String.valueOf(jPasswordField.getPassword()).trim();
					try {
						FacturaBarCode fbc = new FacturaBarCode(lectura);
						int tda = fbc.getNumTienda();
						String fecha = fbc.getFechaSQL();
						if(fecha != null) // WDIAZ:08-2013 condición para validar Fecha correcta, 
						{
							int caja = fbc.getNumCaja();
							int transaccion = fbc.getNumTransaccion();
							CR.meVenta.iniciarDevolucion(tda,fecha,caja,transaccion);
							dispose();
							if (CR.meVenta.getDevolucion().getVentaOriginal().getCliente().getCodCliente() == null) {
								RegistroCliente registro = factory.getInstance();
								registro.MostrarPantallaCliente(false,1);
							}
							pvd =null;
							pvd = new PantallaVentaDevolucion(tda, caja, transaccion, CR.meVenta.getDevolucion().getVentaOriginal().getFechaTrans());
							dispose();
							MensajesVentanas.centrarVentanaDialogo(pvd);
						}else
							MensajesVentanas.mensajeError(("Data no Válida para la opción Seleccionada, Intente nuevamente "));
						
					} catch (ConexionExcepcion e1) {
						logger.error("ejecutarAccion(KeyEvent)", e1);
						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (ProductoExcepcion e){
						MensajesVentanas.mensajeError(e.getMensaje()); 
						CR.inputEscaner.getDocument().addDocumentListener(this);
					}catch (ExcepcionCr e1) {
						logger.error("ejecutarAccion(KeyEvent)", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
						CR.inputEscaner.getDocument().addDocumentListener(this);
					} catch (Exception e1) {
						logger.error("ejecutarAccion(KeyEvent)", e1);

						MensajesVentanas.mensajeError("Identificador de transaccion inválido.\nNo se pudo recuperar la transaccion");
						CR.inputEscaner.getDocument().addDocumentListener(this);
						}
				break;
				case 9:
					lectura = String.valueOf(jPasswordField.getPassword()).trim();
					try {
						registroCliente = null;			
						registroCliente = new RegistroClientesNuevos();
						dispose();
						MensajesVentanas.centrarVentanaDialogo(registroCliente);
					} catch (Exception e1) {
						logger.error("ejecutarAccion(KeyEvent)", e1);

						//MensajesVentanas.mensajeError("Identificador de transaccion inválido.\nNo se pudo recuperar la transaccion");
						CR.inputEscaner.getDocument().addDocumentListener(this);
					}
				break;
				case 10:
					lectura = String.valueOf(jPasswordField.getPassword()).trim();
					if (lectura.equals("")){
						try {
							if (MensajesVentanas.preguntarSiNo("No se ha especificado un Identificador de Devolución en Espera.\n¿Desea ver el listado de las Devoluciones colocadas en Espera?")==0) {;
								Vector<String> devolucionesEnEspera = new Vector<String>();
								devolucionesEnEspera = CR.meVenta.getDevolucionesEnEspera();
								devolucionesEspera = null;			
								devolucionesEspera = new SeleccionDevoluciones(devolucionesEnEspera);
								MensajesVentanas.centrarVentanaDialogo(devolucionesEspera);
								if (devolucionesEspera.filaSeleccionada()) {
									int tda = devolucionesEspera.obtenerTiendaSeleccionada();
									String fecha = devolucionesEspera.obtenerFechaOriginal();
									String caja = devolucionesEspera.obtenerCajaOriginal();
									int transaccion = devolucionesEspera.obtenerNumeroTransaccion();
									CR.meVenta.recuperarDevolucionEnEspera(tda,fecha,Integer.parseInt(caja),transaccion);
									dispose();
									dxc = null; //como la venta ya trae los pagos de la devolución no necesito pasar ningún valor de montos de devolución
									dxc = new PantallaDevXCambio(/*montoOriginal*/0,/*CR.meVenta.getDevolucion().getMontoBase() + CR.meVenta.getDevolucion().getMontoImpuesto()*/0);
									MensajesVentanas.centrarVentanaDialogo(dxc);
									
								} else
									dispose();
							}
						//	dispose();
														
						} catch (ConexionExcepcion e1) {
							logger.error("ejecutarAccion(KeyEvent)", e1);
							MensajesVentanas.mensajeError(e1.getMensaje());
							CR.inputEscaner.getDocument().addDocumentListener(this);
						} catch (ExcepcionCr e1) {
							logger.error("ejecutarAccion(KeyEvent)", e1);
							MensajesVentanas.mensajeError(e1.getMensaje());
							CR.inputEscaner.getDocument().addDocumentListener(this);
						} catch (Exception e1) {
							logger.error("ejecutarAccion(KeyEvent)", e1);
	
							MensajesVentanas.mensajeError("No existen Devoluciones colocadas en Espera");
							CR.inputEscaner.getDocument().addDocumentListener(this);
						}						
					} else {
						try {
							FacturaBarCode fbc = new FacturaBarCode(lectura);
							int tda = fbc.getNumTienda();
							String fecha = fbc.getFechaSQL(); 
							if(fecha != null) // WDIAZ:08-2013 condición para validar Fecha correcta, 
							{
								int caja = fbc.getNumCaja();
								int transaccion = fbc.getNumTransaccion();
								CR.meVenta.recuperarDevolucionEnEspera(tda,fecha,caja,transaccion);
								dispose();
								dxc = null; //como la venta ya trae los pagos de la devolución no necesito pasar ningún valor de montos de devolución
								dxc = new PantallaDevXCambio(/*montoOriginal*/0,/*CR.meVenta.getDevolucion().getMontoBase() + CR.meVenta.getDevolucion().getMontoImpuesto()*/0);
								MensajesVentanas.centrarVentanaDialogo(dxc);
							}else
								MensajesVentanas.mensajeError(("Data no Válida para la opción Seleccionada, Intente nuevamente "));
							
						} catch (ConexionExcepcion e1) {
							logger.error("ejecutarAccion(KeyEvent)", e1);
	
							MensajesVentanas.mensajeError(e1.getMensaje());
							CR.inputEscaner.getDocument().addDocumentListener(this);
						} catch (ExcepcionCr e1) {
							logger.error("ejecutarAccion(KeyEvent)", e1);
	
							MensajesVentanas.mensajeError(e1.getMensaje());
							CR.inputEscaner.getDocument().addDocumentListener(this);
						} catch (Exception e1) {
							logger.error("ejecutarAccion(KeyEvent)", e1);
	
							MensajesVentanas.mensajeError("Identificador de transaccion inválido.\nNo se pudo recuperar la transaccion");
							CR.inputEscaner.getDocument().addDocumentListener(this);
						}
					}
					break;
				case 11:
					if(Sesion.isCajaEnLinea())
						try {
							String identificacion = "";
							DatosLista datosLista = null;
							if(getJPasswordField().getPassword().length==0){
								dispose();
								datosLista = new DatosLista();
							}else if(getJPasswordField().getPassword().length>0){
								identificacion = String.valueOf(jPasswordField.getPassword());
								if(!identificacion.equals("")){
									dispose();
									datosLista = new DatosLista(identificacion);
								}
							}
							MensajesVentanas.centrarVentanaDialogo(datosLista);
							if(CR.meServ.getListaRegalos()!=null){
								RegistroListaRegalos rl;
								rl = new RegistroListaRegalos(principal,false/*, datos*/);
								principal.dispose();
								MensajesVentanas.centrarVentanaDialogo(rl);
							}
						} catch (UsuarioExcepcion e2) {
							e2.printStackTrace();
							MensajesVentanas.mensajeError(e2.getMensaje());
						} catch (MaquinaDeEstadoExcepcion e2) {
							e2.printStackTrace();
							MensajesVentanas.mensajeError(e2.getMensaje());
						} catch (ConexionExcepcion e2) {
							e2.printStackTrace();
							MensajesVentanas.mensajeError(e2.getMensaje());
						} catch (ExcepcionCr e2) {
							e2.printStackTrace();
							MensajesVentanas.mensajeError(e2.getMensaje());
						}
					else MensajesVentanas.aviso("El sistema se encuentra operando Fuera de Línea");
					break;
				case 12:
					if(Sesion.isCajaEnLinea()){
						String codLista = "";
						try {
							if(getJPasswordField().getPassword().length==0){
								dispose();
								RecuperarLista rl;
								rl = new RecuperarLista();
								MensajesVentanas.centrarVentanaDialogo(rl);
								if(rl.getCodLista()!=null)
									codLista = rl.getCodLista();
							}else if(getJPasswordField().getPassword().length>0)
								codLista = String.valueOf(jPasswordField.getPassword());
							if(!codLista.equals("")){
								dispose();
								ConsultaListaRegalos rl = new ConsultaListaRegalos(principal, codLista);
								principal.dispose();
								MensajesVentanas.centrarVentanaDialogo(rl);
							}
						} catch (UsuarioExcepcion e2) {
							e2.printStackTrace();
							MensajesVentanas.mensajeError(e2.getMensaje());
						} catch (MaquinaDeEstadoExcepcion e2) {
							e2.printStackTrace();
							MensajesVentanas.mensajeError(e2.getMensaje());
						} catch (ConexionExcepcion e2) {
							e2.printStackTrace();
							MensajesVentanas.mensajeError(e2.getMensaje());
						} catch (ExcepcionCr e2) {
							e2.printStackTrace();
							MensajesVentanas.mensajeError(e2.getMensaje());
							if(e2.getMensaje().equals("No existe Lista de Regalos \ncon el código ingresado"))
								try {
									dispose();
									RecuperarLista rl;
									rl = new RecuperarLista();
									MensajesVentanas.centrarVentanaDialogo(rl);
									if(rl.getCodLista()!=null) {
										codLista = rl.getCodLista();
										dispose();
										ConsultaListaRegalos clr = new ConsultaListaRegalos(principal, codLista);
										principal.dispose();
										MensajesVentanas.centrarVentanaDialogo(clr);
									}
								} catch (UsuarioExcepcion e3) {
									e3.printStackTrace();
									MensajesVentanas.mensajeError(e3.getMensaje());
								} catch (MaquinaDeEstadoExcepcion e3) {
									e3.printStackTrace();
									MensajesVentanas.mensajeError(e3.getMensaje());
								} catch (ConexionExcepcion e3) {
									e3.printStackTrace();
									MensajesVentanas.mensajeError(e3.getMensaje());
								} catch (ExcepcionCr e3) {
									e3.printStackTrace();
									MensajesVentanas.mensajeError(e3.getMensaje());
								}
						}
					} else MensajesVentanas.aviso("El sistema se encuentra operando Fuera de Línea");
					break;
				case 13:
					if(Sesion.isCajaEnLinea()){
						char tipoTransaccion;
						try {
							dispose();
							tipoTransaccion = CR.meServ.recuperarListaEnEspera(String.valueOf(jPasswordField.getPassword()));
							if(tipoTransaccion == ListaRegalos.REGISTRO){
								RegistroListaRegalos rl = new RegistroListaRegalos(principal,true);
								MensajesVentanas.centrarVentanaDialogo(rl);
							}else if(tipoTransaccion == ListaRegalos.CONSULTA_INVITADO){
								ConsultaListaRegalos clr = new ConsultaListaRegalos(principal);
								MensajesVentanas.centrarVentanaDialogo(clr);
							}else if(tipoTransaccion == ListaRegalos.CONSULTA_TITULAR){
								ConsultaListaTitular clt = new ConsultaListaTitular(principal);
								MensajesVentanas.centrarVentanaDialogo(clt);
							}else if(tipoTransaccion == ListaRegalos.CIERRE_LISTA){
								CierreListaRegalos1 cl = new CierreListaRegalos1(principal,ListaRegalos.CIERRE_LISTA);
								MensajesVentanas.centrarVentanaDialogo(cl);
							}
						} catch (MaquinaDeEstadoExcepcion e2) {
							e2.printStackTrace();
							MensajesVentanas.mensajeError(e2.getMensaje());
						} catch (XmlExcepcion e2) {
							e2.printStackTrace();
							MensajesVentanas.mensajeError(e2.getMensaje());
						} catch (FuncionExcepcion e2) {
							e2.printStackTrace();
							MensajesVentanas.mensajeError(e2.getMensaje());
						} catch (BaseDeDatosExcepcion e2) {
							e2.printStackTrace();
							MensajesVentanas.mensajeError(e2.getMensaje());
						} catch (ConexionExcepcion e2) {
							e2.printStackTrace();
							MensajesVentanas.mensajeError(e2.getMensaje());
						} catch (UsuarioExcepcion e2) {
							e2.printStackTrace();
							MensajesVentanas.mensajeError(e2.getMensaje());
						} catch (ExcepcionCr e2) {
							e2.printStackTrace();
							MensajesVentanas.mensajeError(e2.getMensaje());
						}
					} else MensajesVentanas.aviso("El sistema se encuentra operando Fuera de Línea");
					break;
			}
		} else {
			//if ((k!=null)&&(k.getKeyCode() == KeyEvent.VK_ENTER))
				MensajesVentanas.aviso("Debe especificar un identificador");
			CR.inputEscaner.getDocument().addDocumentListener(this);
		}

		if (logger.isDebugEnabled())
			logger.debug("ejecutarAccion(KeyEvent) - end");
	}	
	
	/**
	 * This method initializes jButtonF5
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButtonF5() {
		if (logger.isDebugEnabled())
			logger.debug("getJButton2() - start");

		if(jButtonF5 == null) {
			jButtonF5 = new JHighlightButton();
			jButtonF5.setText("F5 - Registrar Apartado - Pedido Especial");
			jButtonF5.setBackground(new java.awt.Color(226,226,222));
			jButtonF5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButtonF5.setPreferredSize(new java.awt.Dimension(200,26));
			jButtonF5.addFocusListener(botonClicker);
		}

		if (logger.isDebugEnabled())
			logger.debug("getJButton2() - end");
		return jButtonF5;
	}
	/**
	 * This method initializes jButtonF7
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButtonF7() {
		if (logger.isDebugEnabled())
			logger.debug("getJButton3() - start");

		if(jButtonF7 == null) {
			jButtonF7 = new JHighlightButton();
			jButtonF7.setText("F7 - Recuperar una Cotización");
			jButtonF7.setBackground(new java.awt.Color(226,226,222));
			jButtonF7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			//CAMBIADO POR MGRILLO PARA AGREGAR EL MANEJO DE COTIZACIONES!!!}
			/*
			 Codigo anterior:
			//jButtonF7.setEnabled(false);
			 */
			jButtonF7.setEnabled(true);
//			jButtonF7.addFocusListener(botonClicker);
		}

		if (logger.isDebugEnabled())
			logger.debug("getJButton3() - end");
		return jButtonF7;
	}
	/**
	 * This method initializes jButtonF8
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButtonF8() {
		if (logger.isDebugEnabled())
			logger.debug("getJButton4() - start");

		if(jButtonF8 == null) {
			jButtonF8 = new JHighlightButton();
			jButtonF8.setText("F8 - Recuperar una Comanda");
			jButtonF8.setBackground(new java.awt.Color(226,226,222));
			jButtonF8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButtonF8.setEnabled(false);
//			jButtonF8.addFocusListener(botonClicker);
		}

		if (logger.isDebugEnabled())
			logger.debug("getJButton4() - end");
		return jButtonF8;
	}
	/**
	 * This method initializes jButtonF3
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButtonF3() {
		if (logger.isDebugEnabled())
			logger.debug("getJButton5() - start");

		if(jButtonF3 == null) {
			jButtonF3 = new JHighlightButton();
			jButtonF3.setText("F3 - Devolución de Productos por Dinero");
			jButtonF3.setBackground(new java.awt.Color(226,226,222));
			jButtonF3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButtonF3.addFocusListener(botonClicker);
		}

		if (logger.isDebugEnabled())
			logger.debug("getJButton5() - end");
		return jButtonF3;
	}
	/**
	 * This method initializes jButtonF4
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButtonF4() {
		if (logger.isDebugEnabled())
			logger.debug("getJButton6() - start");

		if(jButtonF4 == null) {
			jButtonF4 = new JHighlightButton();
			jButtonF4.setText("F4 - Anulación de Venta");
			jButtonF4.setBackground(new java.awt.Color(226,226,222));
			jButtonF4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButtonF4.addFocusListener(botonClicker);
		}

		if (logger.isDebugEnabled())
			logger.debug("getJButton6() - end");
		return jButtonF4;
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 */
	public void changedUpdate(DocumentEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("changedUpdate(DocumentEvent) - start");
		
		if (logger.isDebugEnabled())
			logger.debug("changedUpdate(DocumentEvent) - end");
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 */
	public void insertUpdate(DocumentEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("insertUpdate(DocumentEvent) - start");

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (logger.isDebugEnabled())
					logger.debug("run() - start");

				jPasswordField.setEchoChar('*');
				jPasswordField.setText(CR.inputEscaner.getText());
				jPasswordField.requestFocus();

				if (logger.isDebugEnabled())
					logger.debug("run() - end");
			}
		});
		
		if (logger.isDebugEnabled())
			logger.debug("insertUpdate(DocumentEvent) - end");
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
	 */
	public void removeUpdate(DocumentEvent e) {
		if (logger.isDebugEnabled())
			logger.debug("removeUpdate(DocumentEvent) - start");
		
		if (logger.isDebugEnabled())
			logger.debug("removeUpdate(DocumentEvent) - end");
	}
	/**
	 * This method initializes jButtonF6
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButtonF6() {
		if (logger.isDebugEnabled())
			logger.debug("getJButton7() - start");

		if(jButtonF6 == null) {
			jButtonF6 = new JHighlightButton();
			jButtonF6.setBackground(new java.awt.Color(226,226,222));
			jButtonF6.setText("F6 - Recuperar Apartado - Pedido Especial");
			jButtonF6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButtonF6.addFocusListener(botonClicker);
		}

		if (logger.isDebugEnabled())
			logger.debug("getJButton7() - end");
		return jButtonF6;
	}
		
	/**
	 * This method initializes jButtonF9
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButtonF9() {
		if (logger.isDebugEnabled())
			logger.debug("getJButton8() - start");
		
		if(jButtonF9 == null) {
			jButtonF9 = new JHighlightButton();
			jButtonF9.setText("F9 - Registrar Lista de Regalos");
			jButtonF9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButtonF9.setBackground(new java.awt.Color(226,226,222));
			jButtonF9.addFocusListener(botonClicker);
		}
		
		if (logger.isDebugEnabled())
			logger.debug("getJButton8() - end");
		return jButtonF9;
	}
	/**
	 * This method initializes jButtonF10
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButtonF10() {
		if (logger.isDebugEnabled())
			logger.debug("getJButton9() - start");
		
		if(jButtonF10 == null) {
			jButtonF10 = new JHighlightButton();
			jButtonF10.setBackground(new java.awt.Color(226,226,222));
			jButtonF10.setText("F10 - Recuperar Lista de Regalos");
			jButtonF10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButtonF10.addFocusListener(botonClicker);
		}
		
		if (logger.isDebugEnabled())
			logger.debug("getJButton9() - end");
		return jButtonF10;
	}
	
	/**
	 * This method initializes jButtonF6
	 * Mantenimiento
	 * @return javax.swing.JButton
	 */
	/*private javax.swing.JButton getJButton8() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton8() - start");
		}
		if(jButton8 == null) {
			jButton8 = new JHighlightButton();
			jButton8.setBackground(new java.awt.Color(226,226,222));
			jButton8.setText("F11 - Mantenimiento");
			jButton8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButton8.addFocusListener(botonClicker);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton8() - end");
		}
		return jButton8;
	}*/
	
	
	/**
	 * This method initializes jButtonF12
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButtonF12() {
		if (logger.isDebugEnabled())
			logger.debug("getJButton10() - start");
		if(jButtonF12 == null) {
			jButtonF12 = new JHighlightButton();
			jButtonF12.setBackground(new java.awt.Color(226,226,222));
			jButtonF12.setText("F12 - Registro de Afiliados");
			jButtonF12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButtonF12.addFocusListener(botonClicker);
		}
		if (logger.isDebugEnabled())
			logger.debug("getJButton10() - end");
		return jButtonF12;
	}
	
	/**
	 * This method initializes jButtonF1
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButtonF1() {
		if (logger.isDebugEnabled())
			logger.debug("getJButton13() - start");
		if(jButtonF1 == null) {
			jButtonF1 = new JHighlightButton();
			jButtonF1.setBackground(new java.awt.Color(226,226,222));
			jButtonF1.setText("F1 - Recuperar Devolución en Espera");
			jButtonF1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButtonF1.addFocusListener(botonClicker);
		}
		if (logger.isDebugEnabled())
			logger.debug("getJButton13() - end");
		return jButtonF1;
	}
	
	
	private void agregarListeners() {
		if (logger.isDebugEnabled())
			logger.debug("agregarListeners() - start");

		jButtonAceptar.addKeyListener(this);
		jButtonAceptar.addActionListener(this);
		
		jButtonCancelar.addKeyListener(this);
		jButtonCancelar.addActionListener(this);

		jButtonF1.addKeyListener(this);
		jButtonF1.addActionListener(this);

		jButtonF2.addKeyListener(this);
		jButtonF2.addActionListener(this);
	
		jButtonF3.addKeyListener(this);
		jButtonF3.addActionListener(this);

		jButtonF4.addKeyListener(this);
		jButtonF4.addActionListener(this);
		
		jButtonF5.addKeyListener(this);
		jButtonF5.addActionListener(this);
	
		jButtonF6.addKeyListener(this);
		jButtonF6.addActionListener(this);

		//CAMBIADO POR MGRILLO PARA AGREGAR EL MANEJO DE COTIZACIONES!!!}
		/*
		 Codigo anterior:
//		jButtonF7.addKeyListener(this);
//		jButtonF7.addActionListener(this);
		 */

		jButtonF7.addKeyListener(this);
		jButtonF7.addActionListener(this);
		
//		jButtonF8.addKeyListener(this);
//		jButtonF8.addActionListener(this);

		jButtonF9.addKeyListener(this);
		jButtonF9.addActionListener(this);
		
		jButtonF10.addKeyListener(this);
		jButtonF10.addActionListener(this);
		
		jButtonF11.addKeyListener(this);
		jButtonF11.addActionListener(this);
		
		jButtonF12.addKeyListener(this);
		jButtonF12.addActionListener(this);
	
		jPasswordField.addKeyListener(this);
		jPasswordField.addActionListener(this);

		if (logger.isDebugEnabled())
			logger.debug("agregarListeners() - end");
	}
	
	private void eliminarListeners() {
		if (logger.isDebugEnabled())
			logger.debug("eliminarListeners() - start");

		jButtonAceptar.removeKeyListener(this);
		jButtonAceptar.removeActionListener(this);
		
		jButtonCancelar.removeKeyListener(this);
		jButtonCancelar.removeActionListener(this);

		jButtonF1.removeKeyListener(this);
		jButtonF1.removeActionListener(this);						
			
		jButtonF2.removeKeyListener(this);
		jButtonF2.removeActionListener(this);
		
		jButtonF3.removeKeyListener(this);
		jButtonF3.removeActionListener(this);

		jButtonF4.removeKeyListener(this);
		jButtonF4.removeActionListener(this);

		jButtonF5.removeKeyListener(this);
		jButtonF5.removeActionListener(this);
		
		jButtonF6.removeKeyListener(this);
		jButtonF6.removeActionListener(this);
		//CAMBIADO POR MGRILLO PARA AGREGAR EL MANEJO DE COTIZACIONES!!!
		/*
		 Codigo anterior:
		//jButtonF7.removeKeyListener(this);
		//jButtonF7.removeActionListener(this);
		 */
		
		jButtonF7.removeKeyListener(this);
		jButtonF7.removeActionListener(this);

//		jButtonF8.removeKeyListener(this);
//		jButtonF8.removeActionListener(this);
		
		jButtonF9.removeKeyListener(this);
		jButtonF9.removeActionListener(this);
		
		jButtonF10.removeKeyListener(this);
		jButtonF10.removeActionListener(this);
		
		jButtonF11.removeKeyListener(this);
		jButtonF11.removeActionListener(this);

		jButtonF12.removeKeyListener(this);
		jButtonF12.removeActionListener(this);
			
		jPasswordField.removeKeyListener(this);
		jPasswordField.removeActionListener(this);

		if (logger.isDebugEnabled())
			logger.debug("eliminarListeners() - end");
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
	/**
	 * This method initializes jButtonF11
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButtonF11() {
		if(jButtonF11 == null) {
			jButtonF11 = new JHighlightButton();
			jButtonF11.setBackground(new java.awt.Color(226,226,222));
			jButtonF11.addFocusListener(botonClicker);
			jButtonF11.setText("F11 - Recuperar Lista de Regalos en Espera");
			jButtonF11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		}
		return jButtonF11;
	}
	/**
	 * This method initializes jButtonF2
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButtonF2() {
		if(jButtonF2 == null) {
			jButtonF2 = new JHighlightButton();
			jButtonF2.setText("F2 - Devolución de Productos por Cambio");
			jButtonF2.setBackground(new java.awt.Color(226,226,222));
			jButtonF2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jButtonF2.addFocusListener(botonClicker);
		}
		return jButtonF2;
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"
