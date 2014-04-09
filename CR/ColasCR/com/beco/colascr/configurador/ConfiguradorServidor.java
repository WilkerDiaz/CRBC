/*
 * Creado el 31-mar-05
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.beco.colascr.configurador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.beco.colascr.transferencias.configuracion.PoliticaTarea;
import com.beco.colascr.utiles.MensajesVentanas;
import com.epa.preferencesproxy.EPAPreferenceProxy;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.NodeAlreadyExistsException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;

/**
 * @author gmartinelli
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato de los distintos ArrayList
* Fecha: agosto 2011
*/
public class ConfiguradorServidor extends JFrame implements KeyListener, MouseListener, FocusListener, ActionListener {

	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JLabel jLabel = null;
	private javax.swing.JPasswordField dbClave = null;
	private javax.swing.JTextField dbEsquema = null;
	private javax.swing.JTextField dbUrlLocal = null;
	private javax.swing.JTextField dbUrlServidor = null;
	private javax.swing.JTextField dbUsuario = null;
	private javax.swing.JTextField numTienda = null;
	private javax.swing.JTextField pathArchivos = null;
	private javax.swing.JComboBox dbClaseLocal = null;
	private javax.swing.JComboBox dbClaseServidor = null;
	private javax.swing.JTextField urlServidor1 = null;
	private javax.swing.JTextField urlServidor2 = null;
	private javax.swing.JTextField urlServidor3 = null;
	private javax.swing.JTextField urlServidor4 = null;
	private javax.swing.JLabel jLabel4 = null;
	private javax.swing.JLabel jLabel5 = null;
	private javax.swing.JLabel jLabel6 = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JPanel jPanel11 = null;
	private javax.swing.JPanel jPanel12 = null;
	private javax.swing.JPanel jPanel13 = null;
	private javax.swing.JPanel jPanel14 = null;
	private javax.swing.JPanel jPanel16 = null;
	private javax.swing.JPanel jPanel17 = null;
	private javax.swing.JPanel jPanel18 = null;
	private javax.swing.JButton jButton2 = null;
	private javax.swing.JLabel jLabel7 = null;
	private javax.swing.JLabel jLabel9 = null;
	private javax.swing.JLabel jLabel10 = null;
	private javax.swing.JLabel jLabel11 = null;
	private javax.swing.JLabel jLabel12 = null;
	private javax.swing.JLabel jLabel13 = null;
	private javax.swing.JLabel jLabel14 = null;
	private javax.swing.JLabel jLabel25 = null;
	private javax.swing.JLabel jLabel26 = null;
	
	@SuppressWarnings("unused")
	private EPAPreferenceProxy preferenciasColasCR = null;
	private ArrayList <String>valoresClases = null;
	private boolean escrita = false;

	private javax.swing.JLabel jLabel17 = null;
	private javax.swing.JPanel jPanel8 = null;
	private int caso = 0;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JLabel jLabel3 = null;
	private javax.swing.JLabel jLabel8 = null;
	private javax.swing.JPanel jPanel5 = null;
	private javax.swing.JPanel jPanel6 = null;
	private javax.swing.JPanel jPanel7 = null;
	private javax.swing.JPanel jPanel9 = null;
	private javax.swing.JPanel jPanel19 = null;
	private javax.swing.JPanel jPanel20 = null;
	private javax.swing.JComboBox frecuenciaBases = null;
	private javax.swing.JComboBox frecuenciaAfiliados = null;
	private javax.swing.JComboBox frecuenciaProductos = null;
	private javax.swing.JComboBox frecuenciaVentas = null;
	private javax.swing.JComboBox frecuenciaAfiliadosTemporales = null;
	private JSpinner selectorFechaBases = null;
	private JSpinner selectorFechaProductos = null;
	private JSpinner selectorFechaAfiliados = null;
	private JSpinner selectorFechaVentas = null;
	private JSpinner selectorFechaListaRegalos = null;
	private JSpinner selectorFechaAfiliadosTemporales = null;
	private JSpinner limiteInferior = null;
	private JSpinner limiteSuperior = null;
	private javax.swing.JLabel jLabel15 = null;
	private javax.swing.JLabel jLabel16 = null;
	private javax.swing.JLabel jLabel18 = null;
	private javax.swing.JLabel jLabel19 = null;
	private javax.swing.JLabel jLabel20 = null;
	private javax.swing.JLabel jLabel21 = null;
	private javax.swing.JLabel jLabel22 = null;
	private javax.swing.JLabel jLabel23 = null;
	private javax.swing.JLabel jLabel24 = null;
	private javax.swing.JPanel jPanel10 = null;
	private javax.swing.JComboBox frecuenciaListaRegalos = null;
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	public static void main(String[] args) {
		ConfiguradorServidor configurador = new ConfiguradorServidor(0);
		MensajesVentanas.centrarVentana(configurador, true);
		
		ArrayList <String>informacionUsuario = cargarInformacionUsuario();
		
		if (informacionUsuario==null)
			configurador.cargarPreferencias();
		else {
			if (usuarioValido(informacionUsuario))
				configurador.cargarPreferencias();
			else {
				MensajesVentanas.mensajeError("Usuario Inválido o contraseña incorrecta\nNo se pudo acceder a la aplicación");
				//System.exit(1);
			}
		}
		configurador.seleccionarTda();
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	public static void configuradorOpcion() {
		ConfiguradorServidor configurador = new ConfiguradorServidor(1);
		MensajesVentanas.centrarVentana(configurador, true);
		
		ArrayList <String>informacionUsuario = cargarInformacionUsuario();
		
		if (informacionUsuario==null)
			configurador.cargarPreferencias();
		else {
			if (usuarioValido(informacionUsuario))
				configurador.cargarPreferencias();
			else {
				MensajesVentanas.mensajeError("Usuario Inválido o contraseña incorrecta\nNo se pudo acceder a la aplicación");
			}
		}
		configurador.seleccionarTda();
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	private static ArrayList <String>cargarInformacionUsuario() {
		EPAPreferenceProxy preferenciasColas = new EPAPreferenceProxy("proyectocr");
		if (preferenciasColas.isNodeConfigured("colasCR")) {
			try {
				ArrayList <String>usuario = new ArrayList<String>(2);
				usuario.add(preferenciasColas.getConfigStringForParameter("colasCR", "dbUsuario"));
				usuario.add(preferenciasColas.getConfigStringForParameter("colasCR", "dbClave"));
				return usuario;
			} catch (Exception e) {
				return null;
			}
		} else
			return null;		
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList y Vector
	* Fecha: agosto 2011
	*/
	private static boolean usuarioValido(ArrayList <String>usuario) {
		LoginDeUsuario pantallaLogin = new LoginDeUsuario();
		MensajesVentanas.centrarVentanaDialogo(pantallaLogin);
		try {
			if (pantallaLogin.cargarDatos()) {
				Vector<String> valores = pantallaLogin.getValores();
				return (valores.elementAt(0).equals(usuario.get(0))&&valores.elementAt(1).equals(usuario.get(1)));
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	/**
	 * This is the default constructor
	 */
	public ConfiguradorServidor(int caso) {
		super();
		initialize();
		this.caso = caso;
	
		numTienda.addKeyListener(this);
		numTienda.addMouseListener(this);
		numTienda.addFocusListener(this);

		pathArchivos.addKeyListener(this);
		pathArchivos.addMouseListener(this);
		pathArchivos.addFocusListener(this);
		
		dbUsuario.addKeyListener(this);
		dbUsuario.addMouseListener(this);
		dbUsuario.addFocusListener(this);

		dbClaseLocal.addKeyListener(this);
		dbClaseLocal.addMouseListener(this);

		dbClaseServidor.addKeyListener(this);
		dbClaseServidor.addMouseListener(this);

		urlServidor1.addKeyListener(this);
		urlServidor1.addMouseListener(this);
		urlServidor1.addFocusListener(this);

		urlServidor2.addKeyListener(this);
		urlServidor2.addMouseListener(this);
		urlServidor2.addFocusListener(this);

		urlServidor3.addKeyListener(this);
		urlServidor3.addMouseListener(this);
		urlServidor3.addFocusListener(this);

		urlServidor4.addKeyListener(this);
		urlServidor4.addMouseListener(this);
		urlServidor4.addFocusListener(this);

		dbEsquema.addKeyListener(this);
		dbEsquema.addMouseListener(this);
		dbEsquema.addFocusListener(this);
		
		jButton.addKeyListener(this);
		jButton.addMouseListener(this);

		jButton1.addKeyListener(this);
		jButton1.addMouseListener(this);

		jButton2.addKeyListener(this);
		jButton2.addMouseListener(this);

		frecuenciaBases.addKeyListener(this);
		frecuenciaBases.addMouseListener(this);
		frecuenciaBases.addActionListener(this);

		frecuenciaAfiliados.addKeyListener(this);
		frecuenciaAfiliados.addMouseListener(this);
		frecuenciaAfiliados.addActionListener(this);
		
		frecuenciaAfiliadosTemporales.addKeyListener(this);
		frecuenciaAfiliadosTemporales.addMouseListener(this);
		frecuenciaAfiliadosTemporales.addActionListener(this);

		frecuenciaProductos.addKeyListener(this);
		frecuenciaProductos.addMouseListener(this);
		frecuenciaProductos.addActionListener(this);

		frecuenciaVentas.addKeyListener(this);
		frecuenciaVentas.addMouseListener(this);
		frecuenciaVentas.addActionListener(this);
		
		frecuenciaListaRegalos.addKeyListener(this);
		frecuenciaListaRegalos.addMouseListener(this);
		frecuenciaListaRegalos.addActionListener(this);
		
		if (getFrecuenciaBases().getSelectedIndex()<1) {
			getSelectorFechaBases().setVisible(false);
			getJLabel15().setVisible(false);
		}

		if (getFrecuenciaAfiliados().getSelectedIndex()<1) {
			getSelectorFechaAfiliados().setVisible(false);
			getJLabel16().setVisible(false);
		}
		
		if (getFrecuenciaAfiliadosTemporales().getSelectedIndex()<1) {
			getSelectorFechaAfiliadosTemporales().setVisible(false);
			getJLabel26().setVisible(false);
		}
		
		if (getFrecuenciaProductos().getSelectedIndex()<1) {
			getSelectorFechaProductos().setVisible(false);
			getJLabel18().setVisible(false);
		}

		if (getFrecuenciaVentas().getSelectedIndex()<1) {
			getSelectorFechaVentas().setVisible(false);
			getJLabel19().setVisible(false);
		}
		
		if (getFrecuenciaListaRegalos().getSelectedIndex()<1) {
			getSelectorFechaListaRegalos().setVisible(false);
			getJLabel24().setVisible(false);
		}
	}
	
	void seleccionarTda() {	
		numTienda.selectAll();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(500, 525);
		this.setContentPane(getJContentPane());
		this.setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);
		this.setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/beco/colascr/utiles/iconos/ix16x16/data_disk.png")));
		this.setResizable(false);
		this.setTitle("Configuración Servidor de Tienda CR");
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
			layFlowLayout1.setVgap(5);
			layFlowLayout1.setHgap(0);
			jContentPane.setLayout(layFlowLayout1);
			jContentPane.add(getJPanel2(), null);
			jContentPane.add(getJPanel1(), null);
			jContentPane.add(getJPanel(), null);
			jContentPane.setBackground(new java.awt.Color(226,226,222));
			jContentPane.setPreferredSize(new java.awt.Dimension(1450,420));
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
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
			layFlowLayout2.setVgap(0);
			layFlowLayout2.setAlignment(java.awt.FlowLayout.RIGHT);
			jPanel.setLayout(layFlowLayout2);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.setBackground(new java.awt.Color(226,226,222));
			jPanel.setPreferredSize(new java.awt.Dimension(490,26));
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
			java.awt.FlowLayout layFlowLayout21 = new java.awt.FlowLayout();
			layFlowLayout21.setVgap(0);
			jPanel1.setLayout(layFlowLayout21);
			jPanel1.add(getJPanel3(), null);
			jPanel1.add(getJPanel4(), null);
			jPanel1.setBackground(new java.awt.Color(242,242,238));
			jPanel1.setPreferredSize(new java.awt.Dimension(480,410));
			jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(69,107,127),1));
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
			jButton.setText("Aceptar");
			jButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/beco/colascr/utiles/iconos/ix16x16/check2.png")));
			jButton.setBackground(new java.awt.Color(226,226,222));
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
			jButton1.setText("Cancelar");
			jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/beco/colascr/utiles/iconos/ix16x16/delete2.png")));
			jButton1.setBackground(new java.awt.Color(226,226,222));
		}
		return jButton1;
	}
	/**
	 * This method initializes dbUsuario
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getDbUsuario() {
		if(dbUsuario == null) {
			dbUsuario = new javax.swing.JTextField();
			dbUsuario.setText("");
			dbUsuario.setColumns(10);
			dbUsuario.setPreferredSize(new java.awt.Dimension(60,20));
		}
		return dbUsuario;
	}
	/**
	 * This method initializes dbClave
	 * 
	 * @return javax.swing.JPasswordField
	 */
	private javax.swing.JPasswordField getDbClave() {
		if(dbClave == null) {
			dbClave = new javax.swing.JPasswordField();
			dbClave.setText("");
			dbClave.setColumns(10);
			dbClave.setPreferredSize(new java.awt.Dimension(60,20));
			dbClave.setEnabled(false);
		}
		return dbClave;
	}
	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel2() {
		if(jPanel2 == null) {
			jPanel2 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout3 = new java.awt.FlowLayout();
			layFlowLayout3.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel2.setLayout(layFlowLayout3);
			jPanel2.add(getJLabel(), null);
			jPanel2.setBackground(new java.awt.Color(69,107,127));
			jPanel2.setPreferredSize(new java.awt.Dimension(480,42));
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
			jLabel.setText("Configuración");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jLabel.setForeground(java.awt.Color.white);
			jLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/beco/colascr/utiles/iconos/ix32x32/exchange.png")));
		}
		return jLabel;
	}

	/**
	 * 
	 */
	private void cargarPreferencias() {
		dbUrlLocal = new JTextField();
		dbUrlServidor = new JTextField();
		EPAPreferenceProxy preferenciasColasCR = new EPAPreferenceProxy("proyectocr");
		if (preferenciasColasCR.isNodeConfigured("colasCR")) {
			try {
				dbClaseLocal.setSelectedItem(preferenciasColasCR.getConfigStringForParameter("colasCR", "dbClaseLocal"));
				dbClaseServidor.setSelectedItem(preferenciasColasCR.getConfigStringForParameter("colasCR", "dbClaseServidor"));
				dbClave.setText(preferenciasColasCR.getConfigStringForParameter("colasCR", "dbClave"));
				dbEsquema.setText(preferenciasColasCR.getConfigStringForParameter("colasCR", "dbEsquema"));
				dbUrlLocal.setText(preferenciasColasCR.getConfigStringForParameter("colasCR", "dbUrlLocal"));
				dbUrlServidor.setText(preferenciasColasCR.getConfigStringForParameter("colasCR", "dbUrlServidor"));
				dbUsuario.setText(preferenciasColasCR.getConfigStringForParameter("colasCR", "dbUsuario"));
				numTienda.setText(preferenciasColasCR.getConfigStringForParameter("colasCR", "numTienda"));
				pathArchivos.setText(preferenciasColasCR.getConfigStringForParameter("colasCR", "pathArchivosTransfer"));
				getFrecuenciaBases().setSelectedIndex(Integer.parseInt(preferenciasColasCR.getConfigStringForParameter("colasCR", "tipoSincBases")));
				getFrecuenciaAfiliados().setSelectedIndex(Integer.parseInt(preferenciasColasCR.getConfigStringForParameter("colasCR", "tipoSincAfiliados")));
				getFrecuenciaAfiliadosTemporales().setSelectedIndex(Integer.parseInt(preferenciasColasCR.getConfigStringForParameter("colasCR", "tipoSincAfiliadosTemporales")));
				getFrecuenciaProductos().setSelectedIndex(Integer.parseInt(preferenciasColasCR.getConfigStringForParameter("colasCR", "tipoSincProductos")));
				getFrecuenciaVentas().setSelectedIndex(Integer.parseInt(preferenciasColasCR.getConfigStringForParameter("colasCR", "tipoSincVentas")));
				getFrecuenciaListaRegalos().setSelectedIndex(Integer.parseInt(preferenciasColasCR.getConfigStringForParameter("colasCR", "tipoSincListaRegalos")));
				
				String valorTiempo = "";
				Calendar defecto = Calendar.getInstance();
				switch (getFrecuenciaBases().getSelectedIndex()) {
					case PoliticaTarea.AL_INICIAR: valorTiempo = "0000";
						defecto.set(defecto.get(Calendar.YEAR), defecto.get(Calendar.MONTH), defecto.get(Calendar.DATE), 0, 0);
						break;
					case PoliticaTarea.DIARIO: valorTiempo = preferenciasColasCR.getConfigStringForParameter("colasCR", "valorSincBases");
						defecto.set(defecto.get(Calendar.YEAR), defecto.get(Calendar.MONTH), defecto.get(Calendar.DATE), Integer.parseInt(valorTiempo.substring(0,2)), Integer.parseInt(valorTiempo.substring(2)));
						break;
					case PoliticaTarea.POR_TIEMPO: valorTiempo = preferenciasColasCR.getConfigStringForParameter("colasCR", "valorSincBases");
						defecto.set(defecto.get(Calendar.YEAR), defecto.get(Calendar.MONTH), defecto.get(Calendar.DATE), Integer.parseInt(valorTiempo)/60, Integer.parseInt(valorTiempo)%60);
				}
				selectorFechaBases.setValue(defecto.getTime());

				switch (getFrecuenciaAfiliados().getSelectedIndex()) {
					case PoliticaTarea.AL_INICIAR: valorTiempo = "0000";
						defecto.set(defecto.get(Calendar.YEAR), defecto.get(Calendar.MONTH), defecto.get(Calendar.DATE), 0, 0);
						break;
					case PoliticaTarea.DIARIO: valorTiempo = preferenciasColasCR.getConfigStringForParameter("colasCR", "valorSincAfiliados");
						defecto.set(defecto.get(Calendar.YEAR), defecto.get(Calendar.MONTH), defecto.get(Calendar.DATE), Integer.parseInt(valorTiempo.substring(0,2)), Integer.parseInt(valorTiempo.substring(2)));
						break;
					case PoliticaTarea.POR_TIEMPO: valorTiempo = preferenciasColasCR.getConfigStringForParameter("colasCR", "valorSincAfiliados");
						defecto.set(defecto.get(Calendar.YEAR), defecto.get(Calendar.MONTH), defecto.get(Calendar.DATE), Integer.parseInt(valorTiempo)/60, Integer.parseInt(valorTiempo)%60);
				}
				selectorFechaAfiliados.setValue(defecto.getTime());
				
				switch (getFrecuenciaAfiliadosTemporales().getSelectedIndex()) {
				case PoliticaTarea.AL_INICIAR: valorTiempo = "0000";
					defecto.set(defecto.get(Calendar.YEAR), defecto.get(Calendar.MONTH), defecto.get(Calendar.DATE), 0, 0);
					break;
				case PoliticaTarea.DIARIO: valorTiempo = preferenciasColasCR.getConfigStringForParameter("colasCR", "valorSincAfiliadosTemporales");
					defecto.set(defecto.get(Calendar.YEAR), defecto.get(Calendar.MONTH), defecto.get(Calendar.DATE), Integer.parseInt(valorTiempo.substring(0,2)), Integer.parseInt(valorTiempo.substring(2)));
					break;
				case PoliticaTarea.POR_TIEMPO: valorTiempo = preferenciasColasCR.getConfigStringForParameter("colasCR", "valorSincAfiliadosTemporales");
					defecto.set(defecto.get(Calendar.YEAR), defecto.get(Calendar.MONTH), defecto.get(Calendar.DATE), Integer.parseInt(valorTiempo)/60, Integer.parseInt(valorTiempo)%60);
				}
				selectorFechaAfiliadosTemporales.setValue(defecto.getTime());

				switch (getFrecuenciaProductos().getSelectedIndex()) {
					case PoliticaTarea.AL_INICIAR: valorTiempo = "0000";
						defecto.set(defecto.get(Calendar.YEAR), defecto.get(Calendar.MONTH), defecto.get(Calendar.DATE), 0, 0);
						break;
					case PoliticaTarea.DIARIO: valorTiempo = preferenciasColasCR.getConfigStringForParameter("colasCR", "valorSincProductos");
						defecto.set(defecto.get(Calendar.YEAR), defecto.get(Calendar.MONTH), defecto.get(Calendar.DATE), Integer.parseInt(valorTiempo.substring(0,2)), Integer.parseInt(valorTiempo.substring(2)));
						break;
					case PoliticaTarea.POR_TIEMPO: valorTiempo = preferenciasColasCR.getConfigStringForParameter("colasCR", "valorSincProductos");
						defecto.set(defecto.get(Calendar.YEAR), defecto.get(Calendar.MONTH), defecto.get(Calendar.DATE), Integer.parseInt(valorTiempo)/60, Integer.parseInt(valorTiempo)%60);
				}
				selectorFechaProductos.setValue(defecto.getTime());

				switch (getFrecuenciaVentas().getSelectedIndex()) {
					case PoliticaTarea.AL_INICIAR: valorTiempo = "0000";
						defecto.set(defecto.get(Calendar.YEAR), defecto.get(Calendar.MONTH), defecto.get(Calendar.DATE), 0, 0);
						break;
					case PoliticaTarea.DIARIO: valorTiempo = preferenciasColasCR.getConfigStringForParameter("colasCR", "valorSincVentas");
						defecto.set(defecto.get(Calendar.YEAR), defecto.get(Calendar.MONTH), defecto.get(Calendar.DATE), Integer.parseInt(valorTiempo.substring(0,2)), Integer.parseInt(valorTiempo.substring(2)));
						break;
					case PoliticaTarea.POR_TIEMPO: valorTiempo = preferenciasColasCR.getConfigStringForParameter("colasCR", "valorSincVentas");
						defecto.set(defecto.get(Calendar.YEAR), defecto.get(Calendar.MONTH), defecto.get(Calendar.DATE), Integer.parseInt(valorTiempo)/60, Integer.parseInt(valorTiempo)%60);
				}
				selectorFechaVentas.setValue(defecto.getTime());
				
				switch (getFrecuenciaListaRegalos().getSelectedIndex()) {
					case PoliticaTarea.AL_INICIAR: valorTiempo = "0000";
						defecto.set(defecto.get(Calendar.YEAR), defecto.get(Calendar.MONTH), defecto.get(Calendar.DATE), 0, 0);
						break;
					case PoliticaTarea.DIARIO: valorTiempo = preferenciasColasCR.getConfigStringForParameter("colasCR", "valorSincListaRegalos");
						defecto.set(defecto.get(Calendar.YEAR), defecto.get(Calendar.MONTH), defecto.get(Calendar.DATE), Integer.parseInt(valorTiempo.substring(0,2)), Integer.parseInt(valorTiempo.substring(2)));
						break;
					case PoliticaTarea.POR_TIEMPO: valorTiempo = preferenciasColasCR.getConfigStringForParameter("colasCR", "valorSincListaRegalos");
						defecto.set(defecto.get(Calendar.YEAR), defecto.get(Calendar.MONTH), defecto.get(Calendar.DATE), Integer.parseInt(valorTiempo)/60, Integer.parseInt(valorTiempo)%60);
				}
				selectorFechaListaRegalos.setValue(defecto.getTime());
				
				valorTiempo = preferenciasColasCR.getConfigStringForParameter("colasCR", "valorLimiteInf");
				if (valorTiempo!=null)
					defecto.set(defecto.get(Calendar.YEAR), defecto.get(Calendar.MONTH), defecto.get(Calendar.DATE), Integer.parseInt(valorTiempo.substring(0,2)), Integer.parseInt(valorTiempo.substring(2)), 0);
				else 
					defecto.set(defecto.get(Calendar.YEAR), defecto.get(Calendar.MONTH), defecto.get(Calendar.DATE), 9, 0, 0);
				limiteInferior.setValue(defecto.getTime());

				valorTiempo = preferenciasColasCR.getConfigStringForParameter("colasCR", "valorLimiteSup");
				if (valorTiempo!=null)
					defecto.set(defecto.get(Calendar.YEAR), defecto.get(Calendar.MONTH), defecto.get(Calendar.DATE), Integer.parseInt(valorTiempo.substring(0,2)), Integer.parseInt(valorTiempo.substring(2)), 0);
				else 
					defecto.set(defecto.get(Calendar.YEAR), defecto.get(Calendar.MONTH), defecto.get(Calendar.DATE), 23, 0, 0);
				limiteSuperior.setValue(defecto.getTime());
			} catch (Exception e) {
			}
			this.convertirUrlServ();
		}
	}
	
	/**
	 * This method initializes dbClaseLocal
	 * 
	 * @return javax.swing.JComboBox
	 */
	private javax.swing.JComboBox getDbClaseLocal() {
		if(dbClaseLocal == null) {
			dbClaseLocal = new javax.swing.JComboBox();
			dbClaseLocal.setBackground(new java.awt.Color(226,226,222));
			dbClaseLocal.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			dbClaseLocal.setPreferredSize(new java.awt.Dimension(186,20));
			cargarValoresClase(dbClaseLocal);
		}
		return dbClaseLocal;
	}

	/**
	 * This method initializes dbClaseServidor
	 * 
	 * @return javax.swing.JComboBox
	 */
	private javax.swing.JComboBox getDbClaseServidor() {
		if(dbClaseServidor == null) {
			dbClaseServidor = new javax.swing.JComboBox();
			dbClaseServidor.setBackground(new java.awt.Color(226,226,222));
			dbClaseServidor.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			dbClaseServidor.setPreferredSize(new java.awt.Dimension(186,20));
			cargarValoresClase(dbClaseServidor);
		}
		return dbClaseServidor;
	}
	/**
	 * @param frecuenciaListaRegalos
	 */
	private void cargarValoresClase(JComboBox jCombo) {
		jCombo.addItem("com.ibm.as400.access.AS400JDBCDriver");
		jCombo.addItem("com.mysql.jdbc.Driver");
		valoresClases = new ArrayList<String>();
		valoresClases.add("jdbc:as400://");
		valoresClases.add("jdbc:mysql://");
	}
	/**
	 * @param frecuenciaListaRegalos
	 */
	private void cargarValoresFrecuencias(JComboBox jCombo) {
		jCombo.addItem("Al Iniciar");
		jCombo.addItem("Diario");
		jCombo.addItem("Tiempo");
	}
	/**
	 * This method initializes dbEsquema
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getDbEsquema() {
		if(dbEsquema == null) {
			dbEsquema = new javax.swing.JTextField();
			dbEsquema.setColumns(10);
		}
		return dbEsquema;
	}
	/**
	 * This method initializes numTienda
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getNumTienda() {
		if(numTienda == null) {
			numTienda = new javax.swing.JTextField();
			numTienda.setColumns(3);
			numTienda.setDocument(new MyTextFieldDocument(3));
		}
		return numTienda;
	}
	/**
	 * This method initializes numTienda
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getPathArchivos() {
		if(pathArchivos == null) {
			pathArchivos = new javax.swing.JTextField();
			pathArchivos.setColumns(10);
		}
		return pathArchivos;
	}
	/**
	 * This method initializes urlServidor1
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getUrlServidor1() {
		if(urlServidor1 == null) {
			urlServidor1 = new javax.swing.JTextField();
			urlServidor1.setColumns(3);
			urlServidor1.setDocument(new MyTextFieldDocument(3));
		}
		return urlServidor1;
	}
	/**
	 * This method initializes urlServidor2
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getUrlServidor2() {
		if(urlServidor2 == null) {
			urlServidor2 = new javax.swing.JTextField();
			urlServidor2.setColumns(3);
			urlServidor2.setDocument(new MyTextFieldDocument(3));
		}
		return urlServidor2;
	}
	/**
	 * This method initializes urlServidor3
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getUrlServidor3() {
		if(urlServidor3 == null) {
			urlServidor3 = new javax.swing.JTextField();
			urlServidor3.setColumns(3);
			urlServidor3.setDocument(new MyTextFieldDocument(3));
		}
		return urlServidor3;
	}
	/**
	 * This method initializes urlServidor4
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getUrlServidor4() {
		if(urlServidor4 == null) {
			urlServidor4 = new javax.swing.JTextField();
			urlServidor4.setColumns(3);
			urlServidor4.setDocument(new MyTextFieldDocument(3));
		}
		return urlServidor4;
	}
	/**
	 * This method initializes jLabel4
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel4() {
		if(jLabel4 == null) {
			jLabel4 = new javax.swing.JLabel();
			jLabel4.setText(".");
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
			jLabel5.setText(".");
		}
		return jLabel5;
	}
	/**
	 * This method initializes jLabel6
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel6() {
		if(jLabel6 == null) {
			jLabel6 = new javax.swing.JLabel();
			jLabel6.setText(".");
		}
		return jLabel6;
	}
	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel3() {
		if(jPanel3 == null) {
			jPanel3 = new javax.swing.JPanel();
			java.awt.GridLayout layGridLayout1 = new java.awt.GridLayout();
			layGridLayout1.setRows(15);
			layGridLayout1.setColumns(1);
			jPanel3.setLayout(layGridLayout1);
			jPanel3.add(getJLabel9(), null);
			jPanel3.add(getJLabel12(), null);
			jPanel3.add(getJLabel11(), null);
			jPanel3.add(getJLabel10(), null);
			jPanel3.add(getJLabel7(), null);
			jPanel3.add(getJLabel14(), null);
			jPanel3.add(getJLabel13(), null);
			jPanel3.add(getJLabel17(), null);
			jPanel3.add(getJLabel1(), null);
			jPanel3.add(getJLabel2(), null);
			jPanel3.add(getJLabel25(), null);
			jPanel3.add(getJLabel3(), null);
			jPanel3.add(getJLabel8(), null);
			jPanel3.add(getJLabel23(), null);
			jPanel3.add(getJLabel20(), null);
			jPanel3.setBackground(new java.awt.Color(242,242,238));
			jPanel3.setPreferredSize(new java.awt.Dimension(227,406));
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
			java.awt.GridLayout layGridLayout3 = new java.awt.GridLayout();
			layGridLayout3.setRows(15);
			jPanel4.setLayout(layGridLayout3);
			jPanel4.add(getJPanel11(), null);
			jPanel4.add(getJPanel12(), null);
			jPanel4.add(getJPanel13(), null);
			jPanel4.add(getJPanel14(), null);
			jPanel4.add(getJPanel16(), null);
			jPanel4.add(getJPanel17(), null);
			jPanel4.add(getJPanel18(), null);
			jPanel4.add(getJPanel8(), null);
			jPanel4.add(getJPanel5(), null);
			jPanel4.add(getJPanel6(), null);
			jPanel4.add(getJPanel20(), null);
			jPanel4.add(getJPanel7(), null);
			jPanel4.add(getJPanel9(), null);
			jPanel4.add(getJPanel10(), null);
			jPanel4.add(getJPanel19(), null);
			jPanel4.setBackground(new java.awt.Color(242,242,238));
			jPanel4.setPreferredSize(new java.awt.Dimension(241,406));
		}
		return jPanel4;
	}

	/**
	 * This method initializes jPanel11
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel11() {
		if(jPanel11 == null) {
			jPanel11 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout4 = new java.awt.FlowLayout();
			layFlowLayout4.setHgap(5);
			layFlowLayout4.setVgap(6);
			layFlowLayout4.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel11.setLayout(layFlowLayout4);
			jPanel11.add(getNumTienda(), null);
			jPanel11.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel11;
	}
	/**
	 * This method initializes jPanel12
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel12() {
		if(jPanel12 == null) {
			jPanel12 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout5 = new java.awt.FlowLayout();
			layFlowLayout5.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout5.setHgap(5);
			layFlowLayout5.setVgap(6);
			jPanel12.setLayout(layFlowLayout5);
			jPanel12.add(getDbUsuario(), null);
			jPanel12.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel12;
	}
	/**
	 * This method initializes jPanel13
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel13() {
		if(jPanel13 == null) {
			jPanel13 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout6 = new java.awt.FlowLayout();
			layFlowLayout6.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout6.setHgap(5);
			layFlowLayout6.setVgap(3);
			jPanel13.setLayout(layFlowLayout6);
			jPanel13.add(getDbClave(), null);
			jPanel13.add(getJButton2(), null);
			jPanel13.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel13;
	}
	/**
	 * This method initializes jPanel14
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel14() {
		if(jPanel14 == null) {
			jPanel14 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout7 = new java.awt.FlowLayout();
			layFlowLayout7.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout7.setHgap(5);
			layFlowLayout7.setVgap(6);
			jPanel14.setLayout(layFlowLayout7);
			jPanel14.add(getDbClaseLocal(), null);
			jPanel14.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel14;
	}
	/**
	 * This method initializes jPanel16
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel16() {
		if(jPanel16 == null) {
			jPanel16 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout9 = new java.awt.FlowLayout();
			layFlowLayout9.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout9.setHgap(5);
			layFlowLayout9.setVgap(6);
			jPanel16.setLayout(layFlowLayout9);
			jPanel16.add(getDbClaseServidor(), null);
			jPanel16.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel16;
	}
	/**
	 * This method initializes jPanel17
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel17() {
		if(jPanel17 == null) {
			jPanel17 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout10 = new java.awt.FlowLayout();
			layFlowLayout10.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout10.setHgap(5);
			layFlowLayout10.setVgap(6);
			jPanel17.setLayout(layFlowLayout10);
			jPanel17.add(getUrlServidor1(), null);
			jPanel17.add(getJLabel4(), null);
			jPanel17.add(getUrlServidor2(), null);
			jPanel17.add(getJLabel5(), null);
			jPanel17.add(getUrlServidor3(), null);
			jPanel17.add(getJLabel6(), null);
			jPanel17.add(getUrlServidor4(), null);
			jPanel17.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel17;
	}
	/**
	 * This method initializes jPanel18
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel18() {
		if(jPanel18 == null) {
			jPanel18 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout11 = new java.awt.FlowLayout();
			layFlowLayout11.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout11.setHgap(5);
			layFlowLayout11.setVgap(6);
			jPanel18.setLayout(layFlowLayout11);
			jPanel18.add(getDbEsquema(), null);
			jPanel18.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel18;
	}
	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton2() {
		if(jButton2 == null) {
			jButton2 = new javax.swing.JButton();
			jButton2.setText("Cambiar");
			jButton2.setBackground(new java.awt.Color(226,226,222));
			jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/beco/colascr/utiles/iconos/ix16x16/lock_information.png")));
		}
		return jButton2;
	}
	/**
	 * This method initializes jLabel7
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel7() {
		if(jLabel7 == null) {
			jLabel7 = new javax.swing.JLabel();
			jLabel7.setText("    Clase BD Servidor: ");
		}
		return jLabel7;
	}
	/**
	 * This method initializes jLabel9
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel9() {
		if(jLabel9 == null) {
			jLabel9 = new javax.swing.JLabel();
			jLabel9.setText("    Tienda: ");
			jLabel9.setPreferredSize(new java.awt.Dimension(56,31));
		}
		return jLabel9;
	}
	/**
	 * This method initializes jLabel10
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel10() {
		if(jLabel10 == null) {
			jLabel10 = new javax.swing.JLabel();
			jLabel10.setText("    Clase BD Local: ");
		}
		return jLabel10;
	}
	/**
	 * This method initializes jLabel11
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel11() {
		if(jLabel11 == null) {
			jLabel11 = new javax.swing.JLabel();
			jLabel11.setText("    Clave: ");
		}
		return jLabel11;
	}
	/**
	 * This method initializes jLabel12
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel12() {
		if(jLabel12 == null) {
			jLabel12 = new javax.swing.JLabel();
			jLabel12.setText("    Usuario: ");
		}
		return jLabel12;
	}
	/**
	 * This method initializes jLabel13
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel13() {
		if(jLabel13 == null) {
			jLabel13 = new javax.swing.JLabel();
			jLabel13.setText("    Nombre BD: ");
		}
		return jLabel13;
	}
	/**
	 * This method initializes jLabel14
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel14() {
		if(jLabel14 == null) {
			jLabel14 = new javax.swing.JLabel();
			jLabel14.setText("    URL Servidor: ");
		}
		return jLabel14;
	}
	
	private void convertirUrlServ() {
		String url = dbUrlServidor.getText();
		int posInic = url.indexOf('/') + 2;
		int posFin = url.indexOf('.');
		this.getUrlServidor1().setText(url.substring(posInic,posFin));
		posInic = posFin + 1;
		posFin = url.indexOf('.',posInic);
		this.getUrlServidor2().setText(url.substring(posInic,posFin));
		posInic = posFin + 1;
		posFin = url.indexOf('.',posInic);
		this.getUrlServidor3().setText(url.substring(posInic,posFin));
		posInic = posFin + 1;
		posFin = url.indexOf('/',posInic);
		this.getUrlServidor4().setText(url.substring(posInic,posFin));
	}

	private String construirURL(JComboBox combo, String url1, String url2, String url3, String url4) {
		return valoresClases.get(combo.getSelectedIndex()) + url1 + "." + url2 + "." + url3 + "." + url4 + "/" + dbEsquema.getText().toUpperCase();
	}

	/* (no Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_ESCAPE) {
			dispose();
			if (this.caso==0)
				System.exit(0);
		} 
		else if (e.getSource().equals(getJButton1())&&(e.getKeyCode()==KeyEvent.VK_ENTER||e.getKeyCode()==KeyEvent.VK_SPACE)) {
			dispose();
			if (this.caso==0)
				System.exit(0);
		}
		else if (e.getSource().equals(getJButton())&&(e.getKeyCode()==KeyEvent.VK_ENTER||e.getKeyCode()==KeyEvent.VK_SPACE)) {
			if (validarCampos()) guardarValores();
		}
		else if (e.getSource().equals(getJButton2())&&(e.getKeyCode()==KeyEvent.VK_ENTER||e.getKeyCode()==KeyEvent.VK_SPACE)) {
			jButton2.transferFocus();
			CambiarClave cc = new CambiarClave(new String(getDbClave().getPassword()));
			MensajesVentanas.centrarVentanaDialogo(cc);
			String nvaClave = cc.getNuevaContraseña();
			if (nvaClave!=null)
				getDbClave().setText(nvaClave);
		}
	}

	/* (no Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
		if (escrita) {
			if (e.getSource().equals(getUrlServidor1())&&getUrlServidor1().getText().length()==3) {
				urlServidor1.transferFocus();
				urlServidor2.selectAll();
			}
			else if (e.getSource().equals(getUrlServidor2())&&getUrlServidor2().getText().length()==3) {
				urlServidor2.transferFocus();
				urlServidor3.selectAll();
			}
			else if (e.getSource().equals(getUrlServidor3())&&getUrlServidor3().getText().length()==3) {
				urlServidor3.transferFocus();
				urlServidor4.selectAll();
			}
			escrita = false;
		}
	}

	/* (no Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
		escrita = true;
	}

	/* (no Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
		if (e.getButton()==MouseEvent.BUTTON1) {
			if (e.getSource().equals(getJButton1())) {
				dispose();
				if (this.caso==0)
					System.exit(0);
			}
			else if (e.getSource().equals(getJButton())) {
				if (validarCampos()) guardarValores();
			}
			else if (e.getSource().equals(getJButton2())) {
				CambiarClave cc = new CambiarClave(new String(getDbClave().getPassword()));
				MensajesVentanas.centrarVentanaDialogo(cc);
				String nvaClave = cc.getNuevaContraseña();
				if (nvaClave!=null)
					getDbClave().setText(nvaClave);
			}
		}
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
	}
	
	private boolean validarCampos() {
		if (getNumTienda().getText().equals("")) {
			MensajesVentanas.aviso("Debe Especificar el Número de la Tienda");
			return false;
		} else {
			int url1 = Integer.parseInt(getUrlServidor1().getText());
			int url2 = Integer.parseInt(getUrlServidor2().getText());
			int url3 = Integer.parseInt(getUrlServidor3().getText());
			int url4 = Integer.parseInt(getUrlServidor4().getText());
			if (url1<0||url1>255||url2<0||url2>255||url3<0||url3>255||url4<0||url4>255) {
				MensajesVentanas.aviso("Url de Servidor Inválido\nVerifique el URL");
				return false;
			} else {
				if (getDbEsquema().getText().equals("")) {
					MensajesVentanas.aviso("Debe Especificar el Nombre de la Base de Datos");
					return false;
				} else {
					if (getPathArchivos().getText().equals("")) {
						MensajesVentanas.aviso("Debe Especificar el path donde se encuentran los archivos a transferir");
						return false;
					} else {
						Calendar fechaSup = Calendar.getInstance();
						Calendar fechaInf = Calendar.getInstance();
						SimpleDateFormat dfHora = new SimpleDateFormat("HH");
						fechaSup.set(fechaSup.get(Calendar.YEAR), fechaSup.get(Calendar.MONTH), fechaSup.get(Calendar.DATE), Integer.parseInt(dfHora.format((Date)limiteSuperior.getValue())), Integer.parseInt(dfHora.format((Date)limiteSuperior.getValue())), 0);
						fechaInf.set(fechaInf.get(Calendar.YEAR), fechaInf.get(Calendar.MONTH), fechaInf.get(Calendar.DATE), Integer.parseInt(dfHora.format((Date)limiteInferior.getValue())), Integer.parseInt(dfHora.format((Date)limiteInferior.getValue())), 0);
						if (fechaSup.before(fechaInf)) {
							MensajesVentanas.aviso("El Límite Superior debe ser mayor al Límite Inferior");
							return false;
						} else {
							return true;
						}
					}
				}
			}
		}
	}
	
	private void guardarValores() {
		// Armamos los URLs
		
		dbUrlLocal.setText(construirURL(dbClaseLocal,"127","0","0","1"));
		dbUrlServidor.setText(construirURL(dbClaseServidor,urlServidor1.getText(),urlServidor2.getText(),urlServidor3.getText(),urlServidor4.getText()));
		
		// Guardamos las preferencias
		EPAPreferenceProxy preferenciasColasCR = new EPAPreferenceProxy("proyectocr");
		if(!preferenciasColasCR.isNodeConfigured("colasCR")){
		// La config no existe, la agregamos
			try {
				preferenciasColasCR.addNewNodeToPreferencesTop("colasCR");
			} catch (NodeAlreadyExistsException e) {
				//e.printStackTrace();
			} catch (UnidentifiedPreferenceException e) {
				//e.printStackTrace();
			}
		}
		
		try {
			preferenciasColasCR.setConfigStringForParameter("colasCR", "dbClaseLocal", dbClaseLocal.getSelectedItem().toString());
			preferenciasColasCR.setConfigStringForParameter("colasCR", "dbClaseServidor", dbClaseServidor.getSelectedItem().toString());
			preferenciasColasCR.setConfigStringForParameter("colasCR", "dbClave", new String(dbClave.getPassword()));
			preferenciasColasCR.setConfigStringForParameter("colasCR", "dbEsquema", dbEsquema.getText());
			preferenciasColasCR.setConfigStringForParameter("colasCR", "dbUrlLocal", dbUrlLocal.getText());
			preferenciasColasCR.setConfigStringForParameter("colasCR", "dbUrlServidor", dbUrlServidor.getText());
			preferenciasColasCR.setConfigStringForParameter("colasCR", "dbUsuario", dbUsuario.getText());
			preferenciasColasCR.setConfigStringForParameter("colasCR", "numTienda", numTienda.getText());
			preferenciasColasCR.setConfigStringForParameter("colasCR", "pathArchivosTransfer", pathArchivos.getText());

			preferenciasColasCR.setConfigStringForParameter("colasCR", "tipoSincBases", String.valueOf(getFrecuenciaBases().getSelectedIndex()));
			preferenciasColasCR.setConfigStringForParameter("colasCR", "tipoSincAfiliados", String.valueOf(getFrecuenciaAfiliados().getSelectedIndex()));
			preferenciasColasCR.setConfigStringForParameter("colasCR", "tipoSincProductos", String.valueOf(getFrecuenciaProductos().getSelectedIndex()));
			preferenciasColasCR.setConfigStringForParameter("colasCR", "tipoSincVentas", String.valueOf(getFrecuenciaVentas().getSelectedIndex()));
			preferenciasColasCR.setConfigStringForParameter("colasCR", "tipoSincListaRegalos", String.valueOf(getFrecuenciaListaRegalos().getSelectedIndex()));
			preferenciasColasCR.setConfigStringForParameter("colasCR", "tipoSincAfiliadosTemporales", String.valueOf(getFrecuenciaAfiliadosTemporales().getSelectedIndex()));

			SimpleDateFormat df = new SimpleDateFormat("HHmm");
			String valorPreferencia = "";
			switch (getFrecuenciaBases().getSelectedIndex()) {
				case PoliticaTarea.AL_INICIAR: valorPreferencia = "0000";
					break;
				case PoliticaTarea.DIARIO: valorPreferencia = df.format((Date)getSelectorFechaBases().getValue());
					break;
				case PoliticaTarea.POR_TIEMPO: Calendar fecha = Calendar.getInstance();
					fecha.setTime((Date)getSelectorFechaBases().getValue());
					valorPreferencia = String.valueOf((fecha.get(Calendar.HOUR)*60) + fecha.get(Calendar.MINUTE));
			}
			preferenciasColasCR.setConfigStringForParameter("colasCR", "valorSincBases", valorPreferencia);
			
			switch (getFrecuenciaAfiliados().getSelectedIndex()) {
				case PoliticaTarea.AL_INICIAR: valorPreferencia = "0000";
					break;
				case PoliticaTarea.DIARIO: valorPreferencia = df.format((Date)getSelectorFechaAfiliados().getValue());
					break;
				case PoliticaTarea.POR_TIEMPO: Calendar fecha = Calendar.getInstance();
					fecha.setTime((Date)getSelectorFechaAfiliados().getValue());
					valorPreferencia = String.valueOf((fecha.get(Calendar.HOUR)*60) + fecha.get(Calendar.MINUTE));
			}
			preferenciasColasCR.setConfigStringForParameter("colasCR", "valorSincAfiliados", valorPreferencia);
			
			switch (getFrecuenciaAfiliadosTemporales().getSelectedIndex()) {
			case PoliticaTarea.AL_INICIAR: valorPreferencia = "0000";
				break;
			case PoliticaTarea.DIARIO: valorPreferencia = df.format((Date)getSelectorFechaAfiliadosTemporales().getValue());
				break;
			case PoliticaTarea.POR_TIEMPO: Calendar fecha = Calendar.getInstance();
				fecha.setTime((Date)getSelectorFechaAfiliadosTemporales().getValue());
				valorPreferencia = String.valueOf((fecha.get(Calendar.HOUR)*60) + fecha.get(Calendar.MINUTE));
			}
			preferenciasColasCR.setConfigStringForParameter("colasCR", "valorSincAfiliadosTemporales", valorPreferencia);

			switch (getFrecuenciaProductos().getSelectedIndex()) {
				case PoliticaTarea.AL_INICIAR: valorPreferencia = "0000";
					break;
				case PoliticaTarea.DIARIO: valorPreferencia = df.format((Date)getSelectorFechaProductos().getValue());
					break;
				case PoliticaTarea.POR_TIEMPO: Calendar fecha = Calendar.getInstance();
					fecha.setTime((Date)getSelectorFechaProductos().getValue());
					valorPreferencia = String.valueOf((fecha.get(Calendar.HOUR)*60) + fecha.get(Calendar.MINUTE));
			}
			preferenciasColasCR.setConfigStringForParameter("colasCR", "valorSincProductos", valorPreferencia);

			switch (getFrecuenciaVentas().getSelectedIndex()) {
				case PoliticaTarea.AL_INICIAR: valorPreferencia = "0000";
					break;
				case PoliticaTarea.DIARIO: valorPreferencia = df.format((Date)getSelectorFechaVentas().getValue());
					break;
				case PoliticaTarea.POR_TIEMPO: Calendar fecha = Calendar.getInstance();
					fecha.setTime((Date)getSelectorFechaVentas().getValue());
					valorPreferencia = String.valueOf((fecha.get(Calendar.HOUR)*60) + fecha.get(Calendar.MINUTE));
			}
			preferenciasColasCR.setConfigStringForParameter("colasCR", "valorSincVentas", valorPreferencia);

			switch (getFrecuenciaListaRegalos().getSelectedIndex()) {
				case PoliticaTarea.AL_INICIAR: valorPreferencia = "0000";
					break;
				case PoliticaTarea.DIARIO: valorPreferencia = df.format((Date)getSelectorFechaListaRegalos().getValue());
					break;
				case PoliticaTarea.POR_TIEMPO: Calendar fecha = Calendar.getInstance();
					fecha.setTime((Date)getSelectorFechaListaRegalos().getValue());
					valorPreferencia = String.valueOf((fecha.get(Calendar.HOUR)*60) + fecha.get(Calendar.MINUTE));
			}
			preferenciasColasCR.setConfigStringForParameter("colasCR", "valorSincListaRegalos", valorPreferencia);
			
			valorPreferencia = df.format((Date)getLimiteInferior().getValue());
			preferenciasColasCR.setConfigStringForParameter("colasCR", "valorLimiteInf", valorPreferencia);

			valorPreferencia = df.format((Date)getLimiteSuperior().getValue());
			preferenciasColasCR.setConfigStringForParameter("colasCR", "valorLimiteSup", valorPreferencia);

			MensajesVentanas.aviso("Datos de Configuración Almacenados");
			dispose();
			if (this.caso==0)
				System.exit(0);
		} catch (NoSuchNodeException e1) {
			e1.printStackTrace();
		} catch (UnidentifiedPreferenceException e1) {
			e1.printStackTrace();
		}

	}

	/* (no Javadoc)
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	public void focusGained(FocusEvent e) {
		if (e.getSource().equals(getNumTienda())) getNumTienda().selectAll();
		else if (e.getSource().equals(getDbUsuario())) getDbUsuario().selectAll();
		else if (e.getSource().equals(urlServidor1)) urlServidor1.selectAll();
		else if (e.getSource().equals(urlServidor2)) urlServidor2.selectAll();
		else if (e.getSource().equals(urlServidor3)) urlServidor3.selectAll();
		else if (e.getSource().equals(urlServidor4)) urlServidor4.selectAll();
		else if (e.getSource().equals(getDbEsquema())) getDbEsquema().selectAll();
		else if (e.getSource().equals(getPathArchivos())) getPathArchivos().selectAll();
	}

	/* (no Javadoc)
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	public void focusLost(FocusEvent e) {
	}

	/**
	 * This method initializes jLabel17
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel17() {
		if(jLabel17 == null) {
			jLabel17 = new javax.swing.JLabel();
			jLabel17.setText("    Ruta Archivos Transferencia:");
		}
		return jLabel17;
	}
	/**
	 * This method initializes jPanel8
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel8() {
		if(jPanel8 == null) {
			jPanel8 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout4 = new java.awt.FlowLayout();
			layFlowLayout4.setHgap(5);
			layFlowLayout4.setVgap(6);
			layFlowLayout4.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel8.setLayout(layFlowLayout4);
			jPanel8.add(getPathArchivos(), null);
			jPanel8.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel8;
	}
	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel1() {
		if(jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setText("    Frecuencia Sinc. Bases:");
		}
		return jLabel1;
	}
	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel2() {
		if(jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setText("    Frecuencia Sinc. Afiliados:");
		}
		return jLabel2;
	}
	
	/**
	 * This method initializes jLabel25
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel25() {
		if(jLabel25 == null) {
			jLabel25 = new javax.swing.JLabel();
			jLabel25.setText("    Frecuencia Sinc. Afiliados Temporales:");
		}
		return jLabel25;
	}
	
	/**
	 * This method initializes jLabel3
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel3() {
		if(jLabel3 == null) {
			jLabel3 = new javax.swing.JLabel();
			jLabel3.setText("    Frecuencia Sinc. Productos:");
		}
		return jLabel3;
	}
	/**
	 * This method initializes jLabel8
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel8() {
		if(jLabel8 == null) {
			jLabel8 = new javax.swing.JLabel();
			jLabel8.setText("    Frecuencia Sinc. Ventas:");
		}
		return jLabel8;
	}
	/**
	 * This method initializes jLabel23
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel23() {
		if(jLabel23 == null) {
			jLabel23 = new javax.swing.JLabel();
			jLabel23.setText("    Frecuencia Sinc. Lista Regalos:");
		}
		return jLabel23;
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
			layFlowLayout12.setVgap(6);
			layFlowLayout12.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel5.setLayout(layFlowLayout12);
			jPanel5.add(getFrecuenciaBases(), null);
			jPanel5.add(getSelectorFechaBases(), null);
			jPanel5.add(getJLabel15(), null);
			jPanel5.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel5;
	}
	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel6() {
		if(jPanel6 == null) {
			jPanel6 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout22 = new java.awt.FlowLayout();
			layFlowLayout22.setVgap(6);
			layFlowLayout22.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel6.setLayout(layFlowLayout22);
			jPanel6.add(getFrecuenciaAfiliados(), null);
			jPanel6.add(getSelectorFechaAfiliados(), null);
			jPanel6.add(getJLabel16(), null);
			jPanel6.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel6;
	}
	/**
	 * This method initializes jPanel7
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel7() {
		if(jPanel7 == null) {
			jPanel7 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout31 = new java.awt.FlowLayout();
			layFlowLayout31.setVgap(6);
			layFlowLayout31.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel7.setLayout(layFlowLayout31);
			jPanel7.add(getFrecuenciaProductos(), null);
			jPanel7.add(getSelectorFechaProductos(), null);
			jPanel7.add(getJLabel18(), null);
			jPanel7.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel7;
	}
	
	/**
	 * This method initializes jPanel20
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel20() {
		if(jPanel20 == null) {
			jPanel20 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout31 = new java.awt.FlowLayout();
			layFlowLayout31.setVgap(6);
			layFlowLayout31.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel20.setLayout(layFlowLayout31);
			jPanel20.add(getFrecuenciaAfiliadosTemporales(), null);
			jPanel20.add(getSelectorFechaAfiliadosTemporales(), null);
			jPanel20.add(getJLabel26(), null);
			jPanel20.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel20;
	}
	
	
	
	/**
	 * This method initializes jPanel9
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel9() {
		if(jPanel9 == null) {
			jPanel9 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout41 = new java.awt.FlowLayout();
			layFlowLayout41.setVgap(6);
			layFlowLayout41.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel9.setLayout(layFlowLayout41);
			jPanel9.add(getFrecuenciaVentas(), null);
			jPanel9.add(getSelectorFechaVentas(), null);
			jPanel9.add(getJLabel19(), null);
			jPanel9.setBackground(new java.awt.Color(242,242,238));
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
			java.awt.FlowLayout layFlowLayout42 = new java.awt.FlowLayout();
			layFlowLayout42.setVgap(6);
			layFlowLayout42.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel10.setLayout(layFlowLayout42);
			jPanel10.add(getFrecuenciaListaRegalos(), null);
			jPanel10.add(getSelectorFechaListaRegalos(), null);
			jPanel10.add(getJLabel24(), null);
			jPanel10.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel10;
	}
	/**
	 * This method initializes frecuenciaBases
	 * 
	 * @return javax.swing.JComboBox
	 */
	private javax.swing.JComboBox getFrecuenciaBases() {
		if(frecuenciaBases == null) {
			frecuenciaBases = new javax.swing.JComboBox();
			frecuenciaBases.setBackground(new java.awt.Color(226,226,222));
			frecuenciaBases.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			frecuenciaBases.setPreferredSize(new java.awt.Dimension(100,20));
			cargarValoresFrecuencias(frecuenciaBases);
		}
		return frecuenciaBases;
	}
	/**
	 * This method initializes frecuenciaAfiliados
	 * 
	 * @return javax.swing.JComboBox
	 */
	private javax.swing.JComboBox getFrecuenciaAfiliados() {
		if(frecuenciaAfiliados == null) {
			frecuenciaAfiliados = new javax.swing.JComboBox();
			frecuenciaAfiliados.setBackground(new java.awt.Color(226,226,222));
			frecuenciaAfiliados.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			frecuenciaAfiliados.setPreferredSize(new java.awt.Dimension(100,20));
			cargarValoresFrecuencias(frecuenciaAfiliados);
		}
		return frecuenciaAfiliados;
	}
	
	/**
	 * This method initializes frecuenciaAfiliados
	 * 
	 * @return javax.swing.JComboBox
	 */
	private javax.swing.JComboBox getFrecuenciaAfiliadosTemporales() {
		if(frecuenciaAfiliadosTemporales == null) {
			frecuenciaAfiliadosTemporales = new javax.swing.JComboBox();
			frecuenciaAfiliadosTemporales.setBackground(new java.awt.Color(226,226,222));
			frecuenciaAfiliadosTemporales.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			frecuenciaAfiliadosTemporales.setPreferredSize(new java.awt.Dimension(100,20));
			cargarValoresFrecuencias(frecuenciaAfiliadosTemporales);
		}
		return frecuenciaAfiliadosTemporales;
	}
	
	
	/**
	 * This method initializes frecuenciaProductos
	 * 
	 * @return javax.swing.JComboBox
	 */
	private javax.swing.JComboBox getFrecuenciaProductos() {
		if(frecuenciaProductos == null) {
			frecuenciaProductos = new javax.swing.JComboBox();
			frecuenciaProductos.setBackground(new java.awt.Color(226,226,222));
			frecuenciaProductos.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			frecuenciaProductos.setPreferredSize(new java.awt.Dimension(100,20));
			cargarValoresFrecuencias(frecuenciaProductos);
		}
		return frecuenciaProductos;
	}
	/**
	 * This method initializes frecuenciaVentas
	 * 
	 * @return javax.swing.JComboBox
	 */
	private javax.swing.JComboBox getFrecuenciaVentas() {
		if(frecuenciaVentas == null) {
			frecuenciaVentas = new javax.swing.JComboBox();
			frecuenciaVentas.setBackground(new java.awt.Color(226,226,222));
			frecuenciaVentas.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			frecuenciaVentas.setPreferredSize(new java.awt.Dimension(100,20));
			cargarValoresFrecuencias(frecuenciaVentas);
		}
		return frecuenciaVentas;
	}
	/**
	 * This method initializes frecuenciaListaRegalos
	 * 
	 * @return javax.swing.JComboBox
	 */
	private javax.swing.JComboBox getFrecuenciaListaRegalos() {
		if(frecuenciaListaRegalos == null) {
			frecuenciaListaRegalos = new javax.swing.JComboBox();
			frecuenciaListaRegalos.setBackground(new java.awt.Color(226,226,222));
			frecuenciaListaRegalos.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			frecuenciaListaRegalos.setPreferredSize(new java.awt.Dimension(100,20));
			cargarValoresFrecuencias(frecuenciaListaRegalos);
		}
		return frecuenciaListaRegalos;
	}
	/**
	 * Método getSelectorFechaBases
	 * 
	 * @return JSpinner
	 */
	private JSpinner getSelectorFechaBases(){
		if(selectorFechaBases == null){
			selectorFechaBases = new JSpinner(new SpinnerDateModel());
			JSpinner.DateEditor de = new JSpinner.DateEditor(selectorFechaBases, "HH:mm");
			selectorFechaBases.setEditor(de);
			Calendar defecto = Calendar.getInstance();
			defecto.set(defecto.get(Calendar.YEAR), defecto.get(Calendar.MONTH), defecto.get(Calendar.DATE), 0, 0);
			selectorFechaBases.setValue(defecto.getTime());
		}
		return selectorFechaBases;
	}
	/**
	 * Método getSelectorFechaAfiliados
	 * 
	 * @return JSpinner
	 */
	private JSpinner getSelectorFechaAfiliados(){
		if(selectorFechaAfiliados == null){
			selectorFechaAfiliados = new JSpinner(new SpinnerDateModel());
			JSpinner.DateEditor de = new JSpinner.DateEditor(selectorFechaAfiliados, "HH:mm");
			selectorFechaAfiliados.setEditor(de);
			Calendar defecto = Calendar.getInstance();
			defecto.set(defecto.get(Calendar.YEAR), defecto.get(Calendar.MONTH), defecto.get(Calendar.DATE), 0, 0);
			selectorFechaAfiliados.setValue(defecto.getTime());
		}
		return selectorFechaAfiliados;
	}
	
	/**
	 * Método getSelectorFechaAfiliadosTemporales
	 * 
	 * @return JSpinner
	 */
	private JSpinner getSelectorFechaAfiliadosTemporales(){
		if(selectorFechaAfiliadosTemporales == null){
			selectorFechaAfiliadosTemporales = new JSpinner(new SpinnerDateModel());
			JSpinner.DateEditor de = new JSpinner.DateEditor(selectorFechaAfiliadosTemporales, "HH:mm");
			selectorFechaAfiliadosTemporales.setEditor(de);
			Calendar defecto = Calendar.getInstance();
			defecto.set(defecto.get(Calendar.YEAR), defecto.get(Calendar.MONTH), defecto.get(Calendar.DATE), 0, 0);
			selectorFechaAfiliadosTemporales.setValue(defecto.getTime());
		}
		return selectorFechaAfiliadosTemporales;
	}
	
	
	
	/**
	 * Método getSelectorFechaProductos
	 * 
	 * @return JSpinner
	 */
	private JSpinner getSelectorFechaProductos(){
		if(selectorFechaProductos == null){
			selectorFechaProductos = new JSpinner(new SpinnerDateModel());
			JSpinner.DateEditor de = new JSpinner.DateEditor(selectorFechaProductos, "HH:mm");
			selectorFechaProductos.setEditor(de);
			Calendar defecto = Calendar.getInstance();
			defecto.set(defecto.get(Calendar.YEAR), defecto.get(Calendar.MONTH), defecto.get(Calendar.DATE), 0, 0);
			selectorFechaProductos.setValue(defecto.getTime());
		}
		return selectorFechaProductos;
	}
	/**
	 * Método getSelectorFechaVentas
	 * 
	 * @return JSpinner
	 */
	private JSpinner getSelectorFechaVentas(){
		if(selectorFechaVentas == null){
			selectorFechaVentas = new JSpinner(new SpinnerDateModel());
			JSpinner.DateEditor de = new JSpinner.DateEditor(selectorFechaVentas, "HH:mm");
			selectorFechaVentas.setEditor(de);
			Calendar defecto = Calendar.getInstance();
			defecto.set(defecto.get(Calendar.YEAR), defecto.get(Calendar.MONTH), defecto.get(Calendar.DATE), 0, 0);
			selectorFechaVentas.setValue(defecto.getTime());
		}
		return selectorFechaVentas;
	}
	/**
	 * @return
	 */
	private JSpinner getSelectorFechaListaRegalos() {
		if(selectorFechaListaRegalos == null){
			selectorFechaListaRegalos = new JSpinner(new SpinnerDateModel());
			JSpinner.DateEditor de = new JSpinner.DateEditor(selectorFechaListaRegalos, "HH:mm");
			selectorFechaListaRegalos.setEditor(de);
			Calendar defecto = Calendar.getInstance();
			defecto.set(defecto.get(Calendar.YEAR), defecto.get(Calendar.MONTH), defecto.get(Calendar.DATE), 0, 0);
			selectorFechaListaRegalos.setValue(defecto.getTime());
		}
		return selectorFechaListaRegalos;
	}
	/**
	 * Método getLimiteInferior
	 * 
	 * @return JSpinner
	 */
	private JSpinner getLimiteInferior(){
		if(limiteInferior == null){
			limiteInferior = new JSpinner(new SpinnerDateModel());
			JSpinner.DateEditor de = new JSpinner.DateEditor(limiteInferior, "HH:mm");
			limiteInferior.setEditor(de);
			Calendar defecto = Calendar.getInstance();
			defecto.set(defecto.get(Calendar.YEAR), defecto.get(Calendar.MONTH), defecto.get(Calendar.DATE), 0, 0);
			limiteInferior.setValue(defecto.getTime());
		}
		return limiteInferior;
	}
	/**
	 * Método getLimiteSuperior
	 * 
	 * @return JSpinner
	 */
	private JSpinner getLimiteSuperior(){
		if(limiteSuperior == null){
			limiteSuperior = new JSpinner(new SpinnerDateModel());
			JSpinner.DateEditor de = new JSpinner.DateEditor(limiteSuperior, "HH:mm");
			limiteSuperior.setEditor(de);
			Calendar defecto = Calendar.getInstance();
			defecto.set(defecto.get(Calendar.YEAR), defecto.get(Calendar.MONTH), defecto.get(Calendar.DATE), 0, 0);
			limiteSuperior.setValue(defecto.getTime());
		}
		return limiteSuperior;
	}
	/**
	 * This method initializes jLabel15
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel15() {
		if(jLabel15 == null) {
			jLabel15 = new javax.swing.JLabel();
			jLabel15.setText("hh:mm");
			jLabel15.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jLabel15;
	}
	/**
	 * This method initializes jLabel16
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel16() {
		if(jLabel16 == null) {
			jLabel16 = new javax.swing.JLabel();
			jLabel16.setText("hh:mm");
			jLabel16.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jLabel16;
	}
	
	/**
	 * This method initializes jLabel26
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel26() {
		if(jLabel26 == null) {
			jLabel26 = new javax.swing.JLabel();
			jLabel26.setText("hh:mm");
			jLabel26.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jLabel26;
	}
	
	
	/**
	 * This method initializes jLabel18
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel18() {
		if(jLabel18 == null) {
			jLabel18 = new javax.swing.JLabel();
			jLabel18.setText("hh:mm");
			jLabel18.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jLabel18;
	}
	/**
	 * This method initializes jLabel19
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel19() {
		if(jLabel19 == null) {
			jLabel19 = new javax.swing.JLabel();
			jLabel19.setText("hh:mm");
			jLabel19.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jLabel19;
	}
	/**
	 * This method initializes jLabel19
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel24() {
		if(jLabel24 == null) {
			jLabel24 = new javax.swing.JLabel();
			jLabel24.setText("hh:mm");
			jLabel24.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
		}
		return jLabel24;
	}
	/**
	 * This method initializes jPanel9
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel19() {
		if(jPanel19 == null) {
			jPanel19 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout41 = new java.awt.FlowLayout();
			layFlowLayout41.setVgap(6);
			layFlowLayout41.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel19.setLayout(layFlowLayout41);
			jPanel19.add(getJLabel22(), null);
			jPanel19.add(getLimiteInferior(), null);
			jPanel19.add(getJLabel21(), null);
			jPanel19.add(getLimiteSuperior(), null);
			jPanel19.setBackground(new java.awt.Color(242,242,238));
		}
		return jPanel19;
	}
	/**
	 * Método actionPerformed
	 *
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(getFrecuenciaBases())) {
			if (getFrecuenciaBases().getSelectedIndex()<1) {
				getSelectorFechaBases().setVisible(false);
				getJLabel15().setVisible(false);
			} else {
				getSelectorFechaBases().setVisible(true);
				getJLabel15().setVisible(true);
			}
		}
		else if (e.getSource().equals(getFrecuenciaAfiliados())) {
			if (getFrecuenciaAfiliados().getSelectedIndex()<1) {
				getSelectorFechaAfiliados().setVisible(false);
				getJLabel16().setVisible(false);
			} else {
				getSelectorFechaAfiliados().setVisible(true);
				getJLabel16().setVisible(true);
			}
		}
		else if (e.getSource().equals(getFrecuenciaAfiliadosTemporales())) {
			if (getFrecuenciaAfiliadosTemporales().getSelectedIndex()<1) {
				getSelectorFechaAfiliadosTemporales().setVisible(false);
				getJLabel26().setVisible(false);
			} else {
				getSelectorFechaAfiliadosTemporales().setVisible(true);
				getJLabel26().setVisible(true);
			}
		}
		else if (e.getSource().equals(getFrecuenciaProductos())) {
			if (getFrecuenciaProductos().getSelectedIndex()<1) {
				getSelectorFechaProductos().setVisible(false);
				getJLabel18().setVisible(false);
			} else {
				getSelectorFechaProductos().setVisible(true);
				getJLabel18().setVisible(true);
			}
		}
		else if (e.getSource().equals(getFrecuenciaVentas())) {
			if (getFrecuenciaVentas().getSelectedIndex()<1) {
				getSelectorFechaVentas().setVisible(false);
				getJLabel19().setVisible(false);
			} else {
				getSelectorFechaVentas().setVisible(true);
				getJLabel19().setVisible(true);
			}
		}
		else if (e.getSource().equals(getFrecuenciaListaRegalos())) {
			if (getFrecuenciaListaRegalos().getSelectedIndex()<1) {
				getSelectorFechaListaRegalos().setVisible(false);
				getJLabel24().setVisible(false);
			} else {
				getSelectorFechaListaRegalos().setVisible(true);
				getJLabel24().setVisible(true);
			}
		}
	}
	/**
	 * This method initializes jLabel20
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel20() {
		if(jLabel20 == null) {
			jLabel20 = new javax.swing.JLabel();
			jLabel20.setText("    Rango Sincronización:");
		}
		return jLabel20;
	}
	/**
	 * This method initializes jLabel21
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel21() {
		if(jLabel21 == null) {
			jLabel21 = new javax.swing.JLabel();
			jLabel21.setText("  Fin: ");
			jLabel21.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
		}
		return jLabel21;
	}
	/**
	 * This method initializes jLabel22
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel22() {
		if(jLabel22 == null) {
			jLabel22 = new javax.swing.JLabel();
			jLabel22.setText("Inicio: ");
			jLabel22.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
		}
		return jLabel22;
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"

class MyTextFieldDocument extends PlainDocument {

	private int longitud;

	public MyTextFieldDocument(int lon) {
		longitud = lon;
	}

	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		try {
			Integer.parseInt(str);
			if ((getLength()<longitud))
				super.insertString(offs, str, a);
		} catch (NumberFormatException e1) {
		}
	}
}
