/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui.listaregalos
 * Programa   : DatosLista.java
 * Creado por : rabreu
 * Creado en  : 02/06/2006
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ClienteExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.gui.JHighlightComboBox;
import com.becoblohm.cr.gui.RegistroClientesNuevos;
import com.becoblohm.cr.gui.UpperCaseField;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarservicio.ListaRegalos;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.becoblohm.cr.utiles.Validaciones;
import com.toedter.calendar.JDateChooser;


/**
 * @author rabreu
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Se agregaron imports y se eliminaron llamadas del tipo javax.swing.xyz
* Fecha: agosto 2011
*/
public class DatosLista extends JDialog implements ActionListener,KeyListener {
	
	/**
	 * 
	 */
	//WDIAZ 11-2012 para poder escribir en el archivo de errores
	private static final Logger logger = Logger
			.getLogger(DatosLista.class);
	
	private static final long serialVersionUID = 1L;
	private Cliente titular = null;  //  @jve:decl-index=0:
	private int modo;
	private static int INICIA_REGISTRO = 1;
	private static int REGISTRO_INICIADO = 2;
	private com.toedter.calendar.JDateChooser fechaEventoChooser = null;

	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JRadioButton jRadioButtonLG = null;
	private JRadioButton jRadioButtonLNG = null;
	private JLabel jLabel = null;
	private JTextField jTextFieldId = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel6 = null;
	private JPanel jPanel2 = null;
	private JLabel jLabelCorreo = null;
	private JPanel jPanel3 = null;
	private JButton jButtonAceptar = null;
	private JButton jButtonCancelar = null;
	private JPanel jPanel4 = null;
	private JLabel jLabel10 = null;
	private JLabel jLabelNombre = null;
	private JLabel jLabelDireccion = null;
	private JButton jButtonAfiliado = null;
	private JPanel jPanel5 = null;
	private JPanel jPanel6 = null;
	private JButton jButtonBuscar = null;
	private JLabel jLabel12 = null;
	private JCheckBox notificacionListaCheckBox = null;
	private JTextField jTextTitularSec = null;
	private JLabel jLabel8 = null;
	private JComboBox jComboEvento = null;
	private JLabel jLabel13 = null;
	private JLabel jLabel14 = null;
	private JCheckBox permitirVentaCheckBox = null;
	private JLabel jLabel9 = null;

	private JPanel jPanel7 = null;
	private JLabel jLabel5 = null;
	private JLabel jLabel7 = null;
	private JPanel jPanel8 = null;
	private JRadioButton jRadioButtonV = null;
	private JRadioButton jRadioButtonJ = null;
	private JRadioButton jRadioButtonG = null;
	private JRadioButton jRadioButtonE = null;
	private JRadioButton jRadioButtonP = null;

	/**
	 * Constructor para Registro de Lista de Regalos sin identificador inicial
	 */
	public DatosLista() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		super(MensajesVentanas.ventanaActiva);
		initialize();
		CR.meServ.crearListaRegalos();
		modo = INICIA_REGISTRO;
		agregarListeners();
		getJRadioButtonLNG().doClick();
	}

	/**
	 * Constructor para Registro de Lista de Regalos con identificador inicial
	 */	
	public DatosLista(String id) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		super(MensajesVentanas.ventanaActiva);
		initialize();
		CR.meServ.crearListaRegalos();
		modo = REGISTRO_INICIADO;
		String tipoCliente = id.substring(0,1);
		if(tipoCliente.equals("J"))
			getJRadioButtonJ().doClick();
		else if(tipoCliente.equals("G"))
			getJRadioButtonG().doClick();
		else if(tipoCliente.equals("E"))
			getJRadioButtonE().doClick();
		else if(tipoCliente.equals("P"))
			getJRadioButtonP().doClick();
		jTextFieldId.setText(id.substring(2));
		asignarCliente(id);
		agregarListeners();
		getJRadioButtonLNG().doClick();
	}
	
	/**
	 * Constructor para Modificar Detalles en Titular Lista Regalos
	 */
	public DatosLista(ListaRegalos listaRegalos) {
		super(MensajesVentanas.ventanaActiva);
		String tipoEvento,titularSec;
		Date fechaEvento;
		boolean notificacion, permitirVenta;
		char tipoLista;
		modo = REGISTRO_INICIADO;

		initialize();
		
		if (listaRegalos.getTitular()!=null && listaRegalos.getTitular().getCodCliente()!=null) {
			this.titular = listaRegalos.getTitular();
			String tipoCliente = titular.getCodCliente().substring(0,1);
			if(tipoCliente.equals("J"))
				getJRadioButtonJ().doClick();
			else if(tipoCliente.equals("G"))
				getJRadioButtonG().doClick();
			else if(tipoCliente.equals("E"))
				getJRadioButtonE().doClick();
			else if(tipoCliente.equals("P"))
				getJRadioButtonP().doClick();
			String codAfiliado = titular.getCodCliente().substring(2);
			jTextFieldId.setText(codAfiliado);
			try {
				asignarCliente(titular.getCodCliente());
			} catch (ClienteExcepcion e1) {
				MensajesVentanas.aviso(e1.getMensaje());
			} 
		}	
		titularSec = listaRegalos.getTitularSec();
		fechaEvento = listaRegalos.getFechaEvento();
		tipoEvento = listaRegalos.getTipoEvento();
		notificacion = listaRegalos.isNotificaciones();
		tipoLista = listaRegalos.getTipoLista();
		permitirVenta = listaRegalos.isPermitirVenta();

		if(titularSec != null)
			getJTextTitularSec().setText(titularSec);
		if(fechaEvento != null)
			getFechaEventoChooser().setDate(fechaEvento);
		if(tipoEvento != null)
			getJComboEvento().setSelectedItem(tipoEvento);

		getNotificacionListaCheckBox().setSelected(notificacion);
		getPermitirVentaCheckBox().setSelected(permitirVenta);

		agregarListeners();
		
		if(tipoLista == 'G')
			getJRadioButtonLG().doClick();
		else if(tipoLista == 'N')
			getJRadioButtonLNG().doClick();
			
		if(Sesion.getCaja().getEstado().equals("22")){
			getJRadioButtonLG().setEnabled(false);
			getJRadioButtonLNG().setEnabled(false);
			getJButtonBuscar().setEnabled(false);
			getJTextFieldId().setEditable(false);
			getJRadioButtonV().setEnabled(false);
			getJRadioButtonJ().setEnabled(false);
			getJRadioButtonG().setEnabled(false);
			getJRadioButtonE().setEnabled(false);
			getJRadioButtonP().setEnabled(false);
		}
		if(CR.meServ.getListaRegalos().getFechaEvento().before(Sesion.getFechaSistema())){
			getJComboEvento().setEnabled(false);
			getNotificacionListaCheckBox().setEnabled(false);
			getPermitirVentaCheckBox().setEnabled(false);
			getJTextTitularSec().setEditable(false);
			getFechaEventoChooser().setEnabled(false);
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(560, 445);
		this.setContentPane(getJContentPane());
		this.setUndecorated(true);
		this.setModal(true);
		this.setBackground(new java.awt.Color(226,226,222));
		this.getRootPane().setDefaultButton(getJButtonAceptar());
		
//		Component[] compList = {jRadioButtonLNG,jTextFieldId,
//			jTextTitularSec,jButtonAceptar,jButtonCancelar,jButtonAfiliado};
//		this.setFocusTraversalPolicy(new PoliticasDeFoco(compList));
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			FlowLayout layFlowLayout3 = new FlowLayout();
			layFlowLayout3.setHgap(5);
			layFlowLayout3.setVgap(5);
			layFlowLayout3.setAlignment(FlowLayout.CENTER);
			jContentPane.setLayout(layFlowLayout3);
			jContentPane.add(getJPanel3(), null);
			jContentPane.add(getJPanel2(), null);
			jContentPane.setBackground(new Color(226,226,222));
		}
		return jContentPane;
	}
	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if(jPanel == null) {
			jPanel = new JPanel();
			FlowLayout layFlowLayout4 = new FlowLayout();
			layFlowLayout4.setHgap(0);
			layFlowLayout4.setVgap(-10);
			jPanel.setLayout(layFlowLayout4);

			ButtonGroup group = new ButtonGroup();
			group.add(getJRadioButtonLNG());
			group.add(getJRadioButtonLG());
			
			jPanel.add(getJRadioButtonLNG(), null);
			jPanel.add(getJRadioButtonLG(), null);
			jPanel.setPreferredSize(new Dimension(540,40));
			jPanel.setBackground(new Color(242,242,238));
			jPanel.setBorder(BorderFactory.createTitledBorder(null, "Tipo de lista", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
		}
		return jPanel;
	}
	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if(jPanel1 == null) {
			jPanel1 = new JPanel();
			FlowLayout layFlowLayout5 = new FlowLayout();
			layFlowLayout5.setHgap(0);
			layFlowLayout5.setVgap(-5);
			layFlowLayout5.setAlignment(FlowLayout.CENTER);
			jPanel1.setLayout(layFlowLayout5);
			jPanel1.add(getJPanel7(), null);
			jPanel1.setPreferredSize(new Dimension(540,284));
			jPanel1.setBackground(new Color(242,242,238));
			jPanel1.setBorder(BorderFactory.createTitledBorder(null, "Datos del titular", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
		}
		return jPanel1;
	}
	/**
	 * This method initializes jRadioButtonLG
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButtonLG() {
		if(jRadioButtonLG == null) {
			jRadioButtonLG = new JRadioButton();
			jRadioButtonLG.setBackground(new Color(242,242,238));
			jRadioButtonLG.setText("Lista Garantizada");
		}
		return jRadioButtonLG;
	}
	/**
	 * This method initializes jRadioButtonLNG
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButtonLNG() {
		if(jRadioButtonLNG == null) {
			jRadioButtonLNG = new JRadioButton();
			jRadioButtonLNG.setBackground(new Color(242,242,238));
			jRadioButtonLNG.setText("Lista No Garantizada");
			jRadioButtonLNG.setSelected(true);
		}
		return jRadioButtonLNG;
	}
	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getJLabel() {
		if(jLabel == null) {
			jLabel = new JLabel();
			jLabel.setText("Identificación:       ");
			jLabel.setHorizontalTextPosition(SwingConstants.LEFT);
			jLabel.setPreferredSize(new Dimension(120,25));
		}
		return jLabel;
	}
	/**
	 * This method initializes jTextFieldCedula
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextFieldId() {
		if(jTextFieldId == null) {
			jTextFieldId = new UpperCaseField();
			jTextFieldId.setPreferredSize(new Dimension(160,25));
		}
		return jTextFieldId;
	}
	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getJLabel1() {
		if(jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Nombre:   ");
			jLabel1.setPreferredSize(new Dimension(120,25));
		}
		return jLabel1;
	}
	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getJLabel2() {
		if(jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("Titular Secundario:   ");
			jLabel2.setPreferredSize(new Dimension(120,25));
		}
		return jLabel2;
	}
	/**
	 * This method initializes jLabel3
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getJLabel3() {
		if(jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setText("Dirección:  ");
			jLabel3.setPreferredSize(new Dimension(120,25));
		}
		return jLabel3;
	}
	/**
	 * This method initializes jLabel4
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getJLabel4() {
		if(jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("Fecha del Evento:  ");
			jLabel4.setPreferredSize(new Dimension(120,25));
			jLabel4.setHorizontalAlignment(SwingConstants.LEADING);
			jLabel4.setHorizontalTextPosition(SwingConstants.TRAILING);
		}
		return jLabel4;
	}
	/**
	 * This method initializes jLabel6
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getJLabel6() {
		if(jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setText("Correo Electrónico:  ");
			jLabel6.setPreferredSize(new Dimension(120,25));
		}
		return jLabel6;
	}
	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if(jPanel2 == null) {
			jPanel2 = new javax.swing.JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJPanel5(), BorderLayout.WEST);
			jPanel2.add(getJPanel6(), BorderLayout.EAST);
			jPanel2.setPreferredSize(new Dimension(540,30));
			jPanel2.setBackground(new Color(226,226,222));
		}
		return jPanel2;
	}
	/**
	 * This method initializes jLabelCorreo
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getJLabelCorreo() {
		if(jLabelCorreo == null) {
			jLabelCorreo = new JLabel();
			jLabelCorreo.setText("");
			jLabelCorreo.setPreferredSize(new Dimension(380,25));
		}
		return jLabelCorreo;
	}
	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if(jPanel3 == null) {
			jPanel3 = new JPanel();
			FlowLayout layFlowLayout1 = new FlowLayout();
			layFlowLayout1.setHgap(0);
			layFlowLayout1.setVgap(0);
			layFlowLayout1.setAlignment(FlowLayout.CENTER);
			jPanel3.setLayout(layFlowLayout1);
			jPanel3.add(getJPanel4(), null);
			jPanel3.add(getJPanel(), null);
			jPanel3.add(getJPanel1(), null);
			jPanel3.setPreferredSize(new Dimension(540,365));
			jPanel3.setBackground(new Color(226,226,222));
		}
		return jPanel3;
	}
	/**
	 * This method initializes jButtonAceptar
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonAceptar() {
		if(jButtonAceptar == null) {
			jButtonAceptar = new JButton();
			jButtonAceptar.setBackground(new Color(226,226,222));
			jButtonAceptar.setPreferredSize(new Dimension(99,26));
			jButtonAceptar.setFont(new Font("Dialog", Font.BOLD, 12));
			jButtonAceptar.setText("Aceptar");
			jButtonAceptar.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
			jButtonAceptar.setHorizontalAlignment(SwingConstants.CENTER);
			jButtonAceptar.setMargin(new Insets(1,2,1,1));
		}
		return jButtonAceptar;
	}
	/**
	 * This method initializes jButtonCancelar
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonCancelar() {
		if(jButtonCancelar == null) {
			jButtonCancelar = new JButton();
			jButtonCancelar.setPreferredSize(new Dimension(99,26));
			jButtonCancelar.setBackground(new Color(226,226,222));
			jButtonCancelar.setText("Cancelar");
			jButtonCancelar.setFont(new Font("Dialog", Font.BOLD, 12));
			jButtonCancelar.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
			jButtonCancelar.setMargin(new Insets(1,2,1,1));
		}
		return jButtonCancelar;
	}
	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if(jPanel4 == null) {
			jPanel4 = new JPanel();
			FlowLayout layFlowLayout11 = new FlowLayout();
			layFlowLayout11.setAlignment(FlowLayout.LEFT);
			jPanel4.setLayout(layFlowLayout11);
			jPanel4.add(getJLabel10(), null);
			jPanel4.setPreferredSize(new Dimension(540,40));
			jPanel4.setBackground(new java.awt.Color(69,107,127));
			jPanel4.setFont(new Font("Dialog", Font.BOLD, 18));
			jPanel4.setForeground(Color.white);
		}
		return jPanel4;
	}
	/**
	 * This method initializes jLabel10
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getJLabel10() {
		if(jLabel10 == null) {
			jLabel10 = new JLabel();
			jLabel10.setText("Detalles de Lista de Regalos");
			jLabel10.setHorizontalAlignment(SwingConstants.LEFT);
			jLabel10.setFont(new Font("Dialog", Font.BOLD, 18));
			jLabel10.setForeground(Color.white);
			jLabel10.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix32x32/document_add.png")));
		}
		return jLabel10;
	}
	/**
	 * This method initializes jLabelNombre
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getJLabelNombre() {
		if(jLabelNombre == null) {
			jLabelNombre = new JLabel();
			jLabelNombre.setText("");
			jLabelNombre.setPreferredSize(new Dimension(380,25));
		}
		return jLabelNombre;
	}
	/**
	 * This method initializes jLabelDireccion
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getJLabelDireccion() {
		if(jLabelDireccion == null) {
			jLabelDireccion = new JLabel();
			jLabelDireccion.setText("");
			jLabelDireccion.setPreferredSize(new java.awt.Dimension(380,25));
		}
		return jLabelDireccion;
	}
	/**
	 * This method initializes jButtonAfiliado
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonAfiliado() {
		if(jButtonAfiliado == null) {
			jButtonAfiliado = new JButton();
			jButtonAfiliado.setPreferredSize(new Dimension(150,26));
			jButtonAfiliado.setBackground(new Color(226,226,222));
			jButtonAfiliado.setMargin(new Insets(1,2,1,1));
			jButtonAfiliado.setText("Modificar Afiliado");
			jButtonAfiliado.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/server_id_card.png")));
			//jButtonAfiliado.setVisible(false);
		}
		return jButtonAfiliado;
	}
	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if(jPanel5 == null) {
			jPanel5 = new JPanel();
			FlowLayout layFlowLayout12 = new FlowLayout();
			layFlowLayout12.setHgap(0);
			layFlowLayout12.setVgap(0);
			jPanel5.setLayout(layFlowLayout12);
			jPanel5.add(getJButtonAfiliado(), null);
			jPanel5.setBackground(new Color(226,226,222));
		}
		return jPanel5;
	}
	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel6() {
		if(jPanel6 == null) {
			jPanel6 = new JPanel();
			FlowLayout layFlowLayout2 = new FlowLayout();
			layFlowLayout2.setHgap(0);
			layFlowLayout2.setVgap(0);
			jPanel6.setLayout(layFlowLayout2);
			jPanel6.add(getJButtonAceptar(), null);
			jPanel6.add(getJButtonCancelar(), null);
			jPanel6.setBackground(new Color(226,226,222));
		}
		return jPanel6;
	}
	/**
	 * This method initializes jButtonBuscar
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonBuscar() {
		if(jButtonBuscar == null) {
			jButtonBuscar = new JButton();
			jButtonBuscar.setPreferredSize(new Dimension(125,25));
			jButtonBuscar.setMargin(new Insets(1,2,1,1));
			jButtonBuscar.setText(" Asignar Cliente");
			jButtonBuscar.setBackground(new Color(226,226,222));
			jButtonBuscar.setIcon(new ImageIcon(getClass().getResource("/com/becoblohm/cr/gui/resources/icons/ix16x16/add.png")));
			jButtonBuscar.setFocusable(false);
		}
		return jButtonBuscar;
	}
	/**
	 * This method initializes jLabel12
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getJLabel12() {
		if(jLabel12 == null) {
			jLabel12 = new JLabel();
			jLabel12.setText("Recibir notificaciones acerca de la lista:");
			jLabel12.setPreferredSize(new Dimension(270,25));
		}
		return jLabel12;
	}
	/**
	 * This method initializes notificacionListaCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getNotificacionListaCheckBox() {
		if(notificacionListaCheckBox == null) {
			notificacionListaCheckBox = new JCheckBox();
			notificacionListaCheckBox.setBackground(new Color(242,242,238));
			notificacionListaCheckBox.setSelected(true);
			notificacionListaCheckBox.setEnabled(true);
			notificacionListaCheckBox.setPreferredSize(new Dimension(25,25));
		}
		return notificacionListaCheckBox;
	}
	/* (no Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			jButtonCancelar.doClick();
		else if(e.getSource().equals(jTextFieldId)){
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				jButtonBuscar.doClick();
				e.consume();
			} 
		}else if(e.getSource().equals(jTextTitularSec))
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
				jButtonAceptar.doClick();
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
		if(e.getSource().equals(jTextFieldId) && e.getKeyChar()!=KeyEvent.VK_BACK_SPACE)
			if(!Character.isDigit(e.getKeyChar()))
				e.consume();
	}
	/**
	 * This method initializes jTextTitularSec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextTitularSec() {
		if(jTextTitularSec == null) {
			jTextTitularSec = new UpperCaseField();
			jTextTitularSec.setPreferredSize(new Dimension(300,25));
		}
		return jTextTitularSec;
	}
	
	/**
	 * This method initializes fechaEventoChooser
	 * 
	 * @return com.toedter.calendar.JDateChooser
	 */
	private JDateChooser getFechaEventoChooser() {
		if(fechaEventoChooser == null) {
			JComponent.setDefaultLocale(new Locale("es", "VE", ""));
			fechaEventoChooser = new com.toedter.calendar.JDateChooser();
			fechaEventoChooser.setPreferredSize(new java.awt.Dimension(200,25));
			fechaEventoChooser.setDateFormatString("dd-MM-yyyy");
			Calendar fecha = Calendar.getInstance();
			fecha.clear(Calendar.HOUR);
			fecha.clear(Calendar.MINUTE);
			fecha.clear(Calendar.SECOND);
			fecha.clear(Calendar.MILLISECOND);
			fecha.add(Calendar.DATE,1);
			fechaEventoChooser.setDate(fecha.getTime());
			fechaEventoChooser.setFont(new Font("Dialog", Font.BOLD, 12));
			fechaEventoChooser.setBackground(new Color(226,226,222));
		}
		return fechaEventoChooser;
	}
	/**
	 * This method initializes jLabel8
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getJLabel8() {
		if(jLabel8 == null) {
			jLabel8 = new JLabel();
			jLabel8.setText("Evento: ");
			jLabel8.setPreferredSize(new Dimension(120,27));
		}
		return jLabel8;
	}
	/**
	 * This method initializes jComboEvento
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboEvento() {
		if(jComboEvento == null) {
			jComboEvento = new JHighlightComboBox();
			jComboEvento.setPreferredSize(new Dimension(200,27));
			jComboEvento.setFont(new Font("Dialog", Font.BOLD, 13));
			jComboEvento.setBackground(new Color(226,226,222));
			cargarTipoEventoBox();
		}
		return jComboEvento;
	}
	/**
	 * 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private void cargarTipoEventoBox() {
		Vector<String> eventos = Sesion.tipoEventosLR;
		for(int i=0;i<eventos.size();i++)
			getJComboEvento().addItem(eventos.get(i));
	}

	/**
	 * This method initializes jLabel13
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getJLabel13() {
		if(jLabel13 == null) {
			jLabel13 = new JLabel();
			jLabel13.setText("");
			jLabel13.setPreferredSize(new Dimension(175,1));
		}
		return jLabel13;
	}
	/**
	 * This method initializes jLabel14
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getJLabel14() {
		if(jLabel14 == null) {
			jLabel14 = new JLabel();
			jLabel14.setText("");
			jLabel14.setPreferredSize(new Dimension(175,1));
		}
		return jLabel14;
	}
	
	/* (no Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		/* RadioButton "Lista No Garantizada" */
		if(e.getSource().equals(jRadioButtonLNG)){
			Calendar minimo = Calendar.getInstance();
			if(Sesion.getCaja().getEstado().equals("20"))
				minimo.add(Calendar.DATE,1);
			minimo.set(Calendar.HOUR_OF_DAY,0);
			minimo.set(Calendar.MINUTE,0);
			minimo.set(Calendar.SECOND,0);
			minimo.set(Calendar.MILLISECOND,0);
			Calendar maximo = Calendar.getInstance();
			maximo.add(Calendar.DATE,365);
			maximo.set(Calendar.HOUR_OF_DAY,0);
			maximo.set(Calendar.MINUTE,0);
			maximo.set(Calendar.SECOND,0);
			maximo.set(Calendar.MILLISECOND,0);
			getFechaEventoChooser().setSelectableDateRange(minimo.getTime(),maximo.getTime());
		}
		
		/* RadioButton "Lista Garantizada" */
		else if(e.getSource().equals(jRadioButtonLG)){
			if(Sesion.getCaja().getEstado().equals("20")){
				Calendar minimo = Calendar.getInstance();
				minimo.add(Calendar.DATE,1);
				minimo.set(Calendar.HOUR_OF_DAY,0);
				minimo.set(Calendar.MINUTE,0);
				minimo.set(Calendar.SECOND,0);
				minimo.set(Calendar.MILLISECOND,0);
				Calendar maximo = Calendar.getInstance();
				maximo.add(Calendar.DATE,CR.meServ.getListaRegalos().getDiasAperturaLG());
				maximo.set(Calendar.HOUR_OF_DAY,0);
				maximo.set(Calendar.MINUTE,0);
				maximo.set(Calendar.SECOND,0);
				maximo.set(Calendar.MILLISECOND,0);
				getFechaEventoChooser().setSelectableDateRange(minimo.getTime(),maximo.getTime());
			}else{
				Calendar minimo = Calendar.getInstance();
				minimo.add(Calendar.DATE,1);
				minimo.set(Calendar.HOUR_OF_DAY,0);
				minimo.set(Calendar.MINUTE,0);
				minimo.set(Calendar.SECOND,0);
				minimo.set(Calendar.MILLISECOND,0);
				getFechaEventoChooser().setSelectableDateRange(minimo.getTime(),CR.meServ.getListaRegalos().getFechaEvento());
			}
		}
		
		/* Botón "Buscar" */
		else if(e.getSource().equals(jButtonBuscar)) {
			String codigo = jTextFieldId.getText().trim();
			if(getJRadioButtonV().isSelected())
				codigo = "V-"+codigo;
			else if(getJRadioButtonJ().isSelected())
				codigo = "J-"+codigo;
			else if(getJRadioButtonG().isSelected())
				codigo = "G-"+codigo;
			else if(getJRadioButtonE().isSelected())
				codigo = "E-"+codigo;
			else if(getJRadioButtonP().isSelected())
				codigo = "P-"+codigo;
			
			try {			
				asignarCliente(codigo);
			} catch (ClienteExcepcion e1) {
				MensajesVentanas.aviso(e1.getMensaje());
			} 
		}

		/* Botón "Aceptar" */
		else if(e.getSource().equals(jButtonAceptar)){
			if(getFechaEventoChooser().getDate().before(getFechaEventoChooser().getMinSelectableDate()))
				MensajesVentanas.aviso("Verifique la fecha seleccionada para el evento");
			else if(getJRadioButtonLG().isSelected() && getFechaEventoChooser().getDate().after(getFechaEventoChooser().getMaxSelectableDate()))
				MensajesVentanas.aviso("La fecha no cumple con el límite \npermitido para Listas Garantizadas");
			else if(getNotificacionListaCheckBox().isSelected() && CR.meServ.getListaRegalos().getCliente().getCodCliente()!=null && getJLabelCorreo().getText().indexOf('@')==-1)
				MensajesVentanas.aviso("Para poder enviar las notificaciones\nse debe indicar una dirección de correo válido");
			else {
				CR.meServ.getListaRegalos().setTitularSec(getJTextTitularSec().getText().trim());
				CR.meServ.getListaRegalos().setNotificaciones(notificacionListaCheckBox.isSelected());
				CR.meServ.getListaRegalos().setPermitirVenta(permitirVentaCheckBox.isSelected());
				if(getJRadioButtonLNG().isSelected())
					CR.meServ.getListaRegalos().setTipoLista('N');
				else if(getJRadioButtonLG().isSelected())
					CR.meServ.getListaRegalos().setTipoLista('G');
				CR.meServ.getListaRegalos().setTipoEvento(jComboEvento.getSelectedItem().toString());
				CR.meServ.getListaRegalos().setFechaEvento(fechaEventoChooser.getDate());

				try {
					String codigo = jTextFieldId.getText().trim();
					if(getJRadioButtonV().isSelected())
						codigo = "V-"+codigo;
					else if(getJRadioButtonJ().isSelected())
						codigo = "J-"+codigo;
					else if(getJRadioButtonG().isSelected())
						codigo = "G-"+codigo;
					else if(getJRadioButtonE().isSelected())
						codigo = "E-"+codigo;
					else if(getJRadioButtonP().isSelected())
						codigo = "P-"+codigo;
						
					CR.meServ.asignarTitularLR(codigo);
					if(Sesion.getCaja().getEstado().equals("22"))
						CR.meServ.getListaRegalos().modificarEncabezadoListaRegalos();
				} catch (UsuarioExcepcion ex) {
					ex.printStackTrace();
				} catch (MaquinaDeEstadoExcepcion ex) {
					ex.printStackTrace();
				} catch (ConexionExcepcion ex) {
					ex.printStackTrace();
				} catch (ExcepcionCr ex) {
					//ex.printStackTrace();
				}
				this.dispose();
			}
		}

		/* Botón "Cancelar" */
		else if(e.getSource().equals(jButtonCancelar)){
			if(modo == INICIA_REGISTRO)
				try {
					CR.meServ.finalizarListaRegalos();
				} catch (UsuarioExcepcion e1) {
					e1.printStackTrace();
				} catch (MaquinaDeEstadoExcepcion e1) {
					e1.printStackTrace();
				} catch (ConexionExcepcion e1) {
					e1.printStackTrace();
				} catch (ExcepcionCr e1) {
					e1.printStackTrace();
				}
			this.dispose();
		}
		
		/* Botón Modificar Afiliado */
		else if(e.getSource().equals(jButtonAfiliado))
			if(this.titular != null) {
				RegistroClientesNuevos registroCliente = new RegistroClientesNuevos(this.titular);
				MensajesVentanas.centrarVentanaDialogo(registroCliente);
				if(CR.meServ.getListaRegalos().getCliente()!=null && CR.meServ.getListaRegalos().getCliente().getCodCliente()!=null){
					Cliente cliente = CR.meServ.getListaRegalos().getCliente();
					String codAfiliado = (cliente.getCodCliente()).substring(2);
					char tipoAfiliado = cliente.getTipoCliente();
					String nombre = cliente.getNombre();
					String apellido = cliente.getApellido();
					String direccion = cliente.getDireccion();
					String codArea = cliente.getCodArea();
					String numTelefono = cliente.getNumTelefono();
					char estadoCliente = cliente.getEstadoCliente();
					String codArea1 = cliente.getCodAreaSec();					
					String numTelefono1 = cliente.getNumTelefonoSec();
					String email = cliente.getEmail();
					boolean contactar = cliente.isContactar();
					
					this.titular = new Cliente(codAfiliado,tipoAfiliado,nombre,apellido,direccion,codArea,numTelefono,estadoCliente,codArea1,numTelefono1,email,contactar);
					
					jTextFieldId.setText(codAfiliado);
					jLabelNombre.setText(titular.getNombreCompleto());
					jLabelDireccion.setText(titular.getDireccion());
					jLabelCorreo.setText(titular.getEmail());
				}
			} else {
				RegistroClientesNuevos registroCliente = new RegistroClientesNuevos();
				MensajesVentanas.centrarVentanaDialogo(registroCliente);
				jButtonBuscar.doClick();
			}
	}

	/**
	 * 
	 */
	private void asignarCliente(String id) throws ClienteExcepcion{
		if(id.length()>0)
			try {
				ResultSet clientes = MediadorBD.buscarCliente(id);
				if(clientes.first()){
					String codAfiliado = (clientes.getString("codafiliado")).substring(2);
					char tipoAfiliado = clientes.getString("tipoafiliado").toCharArray()[0];
					
					//****** Fecha Actualización 07/02/2007
					//Cambio de BECO para agregar validaciones de RIF y CI para el SENIAT
					//*******
					Validaciones validador = new Validaciones();
					if(!validador.validarRifCedula(codAfiliado, tipoAfiliado)) {
						throw (new ClienteExcepcion("CI/RIF Inválido"));
					}
					//**************************

					String nombre = clientes.getString("nombre");
					String apellido = clientes.getString("apellido");
					String direccion = clientes.getString("direccion");
					String codArea = clientes.getString("codarea");
					String numTelefono = clientes.getString("numtelefono");
					char estadoCliente = (clientes.getString("estadoafiliado")).charAt(0);
					String codArea1 = clientes.getString("codarea1");					
					String numTelefono1 = clientes.getString("numtelefono1");
					String email = clientes.getString("email");
					boolean contactar = clientes.getBoolean("contactar");
					
					//cambios por CRM wdiaz
					char sexo = clientes.getString("genero")!= null && clientes.getString("genero").toCharArray().length > 0 
		 			? (char)(clientes.getString("genero").toCharArray()[0]) 
		 			: ' ';
		 			char estadoCivil = clientes.getString("estadocivil") != null && clientes.getString("estadocivil").toCharArray().length > 0
		 			? (char)(clientes.getString("estadocivil").toCharArray()[0])
		 			: ' ';
		 			Date fechaNaci = clientes.getDate("fechanacimiento");
		 			String zonaResidencial = clientes.getString("direccionfiscal");
					//fin de cambios
					
					this.titular = new Cliente(codAfiliado,tipoAfiliado,nombre,apellido,direccion,codArea,numTelefono,estadoCliente,codArea1,numTelefono1,email,contactar,sexo,estadoCivil,fechaNaci,zonaResidencial);
					                                  //id, tipoCliente, nombre, apellido, direccion, codArea, telf,'A', codArea2, telef2, email, contactar,sexo,estadoCivil,fechaNaci,zonaResidencial
					jTextFieldId.setText(codAfiliado);
					jLabelNombre.setText(titular.getNombreCompleto());
					jLabelDireccion.setText(titular.getDireccion());
					jLabelCorreo.setText(titular.getEmail());
				} else if(jTextFieldId.getText().length()>2 && MensajesVentanas.preguntarSiNo("Cliente no encontrado.\n¿Desea registrar los datos del cliente?") == 0) {								
					RegistroClientesNuevos registroCliente = new RegistroClientesNuevos(jTextFieldId.getText());
					MensajesVentanas.centrarVentanaDialogo(registroCliente);
					if(CR.meServ.getListaRegalos().getCliente()!=null && CR.meServ.getListaRegalos().getCliente().getCodCliente()!=null){
						Cliente cliente = CR.meServ.getListaRegalos().getCliente();
						String codAfiliado = (cliente.getCodCliente()).substring(2);
						char tipoAfiliado = cliente.getTipoCliente();
						
						//****** Fecha Actualización 07/02/2007
						//Cambio de BECO para agregar validaciones de RIF y CI para el SENIAT
						//*******
						Validaciones validador = new Validaciones();
						if(!validador.validarRifCedula(codAfiliado, tipoAfiliado)) {
							throw (new ClienteExcepcion("CI/RIF Inválido"));
						}
						//**************************
						
						String nombre = cliente.getNombre();
						String apellido = cliente.getApellido();
						String direccion = cliente.getDireccion();
						String codArea = cliente.getCodArea();
						String numTelefono = cliente.getNumTelefono();
						char estadoCliente = cliente.getEstadoCliente();
						String codArea1 = cliente.getCodAreaSec();					
						String numTelefono1 = cliente.getNumTelefonoSec();
						String email = cliente.getEmail();
						boolean contactar = cliente.isContactar();
						
						this.titular = new Cliente(codAfiliado,tipoAfiliado,nombre,apellido,direccion,codArea,numTelefono,estadoCliente,codArea1,numTelefono1,email,contactar);
					
						jTextFieldId.setText(codAfiliado);
						jLabelNombre.setText(titular.getNombreCompleto());
						jLabelDireccion.setText(titular.getDireccion());
						jLabelCorreo.setText(titular.getEmail());
					}else {
						CR.meServ.getListaRegalos().quitarCliente();
						jLabelNombre.setText("");
						jLabelDireccion.setText("");
						jLabelCorreo.setText("");
	
					}
				}
			} catch (BaseDeDatosExcepcion e1) {
				logger.error("mouseClicked(MouseEvent)", e1);
			} catch (ConexionExcepcion e1) {
				logger.error("mouseClicked(MouseEvent)", e1);
			} catch (SQLException e1) {
				logger.error("mouseClicked(MouseEvent)", e1);
			} catch (Exception e1){
				logger.error("mouseClicked(MouseEvent)", e1);
			}
	}

	private void agregarListeners(){
		getJRadioButtonLG().addActionListener(this);
		getJRadioButtonLG().addKeyListener(this);
		
		getJRadioButtonLNG().addActionListener(this);
		getJRadioButtonLNG().addKeyListener(this);
		
		getJTextFieldId().addActionListener(this);
		getJTextFieldId().addKeyListener(this);
		
		getJButtonBuscar().addActionListener(this);
		getJButtonBuscar().addKeyListener(this);
		
		getJComboEvento().addActionListener(this);
		getJComboEvento().addKeyListener(this);
		
		getFechaEventoChooser().addKeyListener(this);
		
		getJTextTitularSec().addActionListener(this);
		getJTextTitularSec().addKeyListener(this);
		
		getNotificacionListaCheckBox().addActionListener(this);
		getNotificacionListaCheckBox().addKeyListener(this);
		
		getJButtonAceptar().addActionListener(this);
		getJButtonAceptar().addKeyListener(this);
		
		getJButtonCancelar().addActionListener(this);
		getJButtonCancelar().addKeyListener(this);
		
		getJButtonAfiliado().addActionListener(this);
		getJButtonAfiliado().addKeyListener(this);
	}
	/**
	 * This method initializes jLabel9
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getJLabel9() {
		if(jLabel9 == null) {
			jLabel9 = new JLabel();
			jLabel9.setText("Permitir que los invitados se lleven los regalos:");
			jLabel9.setPreferredSize(new Dimension(270,25));
		}
		return jLabel9;
	}
	/**
	 * This method initializes permitirVentaCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getPermitirVentaCheckBox() {
		if(permitirVentaCheckBox == null) {
			permitirVentaCheckBox = new JCheckBox();
			permitirVentaCheckBox.setPreferredSize(new Dimension(25,25));
			permitirVentaCheckBox.setBackground(new Color(242,242,238));
			permitirVentaCheckBox.setSelected(true);
		}
		return permitirVentaCheckBox;
	}
	/**
	 * This method initializes jPanel7
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel7() {
		if(jPanel7 == null) {
			jPanel7 = new JPanel();
			FlowLayout layFlowLayout31 = new FlowLayout();
			layFlowLayout31.setAlignment(FlowLayout.LEFT);
			layFlowLayout31.setHgap(1);
			layFlowLayout31.setVgap(1);
			jPanel7.setLayout(layFlowLayout31);
			jPanel7.add(getJLabel5(), null);
			jPanel7.add(getJLabel7(), null);
			jPanel7.add(getJPanel8(), null);
			jPanel7.add(getJLabel(), null);
			jPanel7.add(getJTextFieldId(), null);
			jPanel7.add(getJButtonBuscar(), null);
			jPanel7.add(getJLabel1(), null);
			jPanel7.add(getJLabelNombre(), null);
			jPanel7.add(getJLabel3(), null);
			jPanel7.add(getJLabelDireccion(), null);
			jPanel7.add(getJLabel6(), null);
			jPanel7.add(getJLabelCorreo(), null);
			jPanel7.add(getJLabel8(), null);
			jPanel7.add(getJComboEvento(), null);
			jPanel7.add(getJLabel13(), null);
			jPanel7.add(getJLabel4(), null);
			jPanel7.add(getFechaEventoChooser(), null);
			jPanel7.add(getJLabel14(), null);
			jPanel7.add(getJLabel2(), null);
			jPanel7.add(getJTextTitularSec(), null);
			jPanel7.add(getJLabel12(), null);
			jPanel7.add(getNotificacionListaCheckBox(), null);
			jPanel7.add(getJLabel9(), null);
			jPanel7.add(getPermitirVentaCheckBox(), null);
			jPanel7.setPreferredSize(new Dimension(510,264));
			jPanel7.setBackground(new Color(242,242,238));
		}
		return jPanel7;
	}
	/**
	 * This method initializes jLabel5
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getJLabel5() {
		if(jLabel5 == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("Tipo Cliente:");
			jLabel5.setPreferredSize(new Dimension(130,25));
			jLabel5.setVisible(false);
		}
		return jLabel5;
	}
	/**
	 * This method initializes jLabel7
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getJLabel7() {
		if(jLabel7 == null) {
			jLabel7 = new JLabel();
			jLabel7.setText("Tipo Cliente:");
			jLabel7.setPreferredSize(new Dimension(120,25));
		}
		return jLabel7;
	}
	/**
	 * This method initializes jPanel8
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel8() {
		if(jPanel8 == null) {
			jPanel8 = new JPanel();
			FlowLayout layFlowLayout13 = new FlowLayout();
			layFlowLayout13.setAlignment(FlowLayout.LEFT);
			layFlowLayout13.setHgap(0);
			layFlowLayout13.setVgap(0);
			jPanel8.setLayout(layFlowLayout13);
			jPanel8.add(getJRadioButtonV(), null);
			jPanel8.add(getJRadioButtonJ(), null);
			jPanel8.add(getJRadioButtonG(), null);
			jPanel8.add(getJRadioButtonE(), null);
			jPanel8.add(getJRadioButtonP(), null);
			// Se arma el Grupo de Botones de tipo RadioButton
			ButtonGroup group = new ButtonGroup();
			group.add(getJRadioButtonV());
			group.add(getJRadioButtonJ());
			group.add(getJRadioButtonG());
			group.add(getJRadioButtonE());
			group.add(getJRadioButtonP());
			
			jPanel8.setPreferredSize(new Dimension(270,25));
			jPanel8.setBackground(new Color(242,242,238));
		}
		return jPanel8;
	}
	/**
	 * This method initializes jRadioButtonV
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButtonV() {
		if(jRadioButtonV == null) {
			jRadioButtonV = new JRadioButton();
			jRadioButtonV.setBackground(new Color(242,242,238));
			jRadioButtonV.setText("V");
			jRadioButtonV.setSelected(true);
		}
		return jRadioButtonV;
	}
	/**
	 * This method initializes jRadioButtonJ
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButtonJ() {
		if(jRadioButtonJ == null) {
			jRadioButtonJ = new JRadioButton();
			jRadioButtonJ.setBackground(new Color(242,242,238));
			jRadioButtonJ.setText("J");
		}
		return jRadioButtonJ;
	}
	/**
	 * This method initializes jRadioButtonG
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButtonG() {
		if(jRadioButtonG == null) {
			jRadioButtonG = new JRadioButton();
			jRadioButtonG.setBackground(new Color(242,242,238));
			jRadioButtonG.setText("G");
		}
		return jRadioButtonG;
	}
	/**
	 * This method initializes jRadioButtonE
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButtonE() {
		if(jRadioButtonE == null) {
			jRadioButtonE = new JRadioButton();
			jRadioButtonE.setBackground(new Color(242,242,238));
			jRadioButtonE.setText("E");
		}
		return jRadioButtonE;
	}
	/**
	 * This method initializes jRadioButtonP
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButtonP() {
		if(jRadioButtonP == null) {
			jRadioButtonP = new JRadioButton();
			jRadioButtonP.setBackground(new Color(242,242,238));
			jRadioButtonP.setText("P");
		}
		return jRadioButtonP;
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"
