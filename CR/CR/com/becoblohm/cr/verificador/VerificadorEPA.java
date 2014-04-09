/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.verificador
 * Programa   : Verificador.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 27/11/2003 04:11:59 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.6
 * Fecha       : 26/05/2004 03:44:34 PM
 * Analista    : GMARTINELLI
 * Descripción : - Agregado el atributo bloqueada para indicar la condición especial.
 * 				- Modificada la acción a tomar al capturar los eventos del usuario para 
 * 				validar condición de bloqueo de la caja.
 * =============================================================================
 * Versión     : 1.7.0
 * Fecha       : 29/06/2004 10:24:34 AM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : - Activado el cambio de contraseña al inicio de sesión en los casos que aplique.
 * 					 - Invocado el método asignarUsuarioLogueado de la clase MediadorBD de modo que al iniciar 
 * 					sesión se actualice el registro correspondiente en la BD.
 * =============================================================================
 * Versión     : 1.5 (según CVS)
 * Fecha       : 11/02/2004 08:46 AM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Actualización para uso del método setUbicacion de la clase Sesion.
 * =============================================================================
 */
package com.becoblohm.cr.verificador;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.html.HTMLDocument;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.gui.FacturacionPrincipal;
import com.becoblohm.cr.gui.OpcionesSeguridad;
import com.becoblohm.cr.gui.BarraTareaCR;
import com.becoblohm.cr.gui.SplashScreen;
import com.becoblohm.cr.manejadorauditoria.Auditoria;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstado;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;

/** 
 * Descripción: 
 * 	Clase que carga el navegador de páginas HTML que permite invocar al verificador de precios con
 * el código de un producto indicado por el usuario y desplega la información devuelta por el Servidor
 * donde se ejecuta la consulta de los precios. 
 * 
 */
public class VerificadorEPA extends JFrame implements ActionListener, KeyListener, DocumentListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(VerificadorEPA.class);

	public static String urlLocal = new String("");
	public static String urlEspera = new String("");
	public static String urlVerificador = new String("");
	public static String urlPrincipal = new String("");
	public static JEditorPane navigatorCR = new JEditorPane();
	public static JPasswordField codigo = new JPasswordField();	
	public static SplashPrecio splashPrecio;
	public static SplashScreen splash = new SplashScreen(1000);

	public  FacturacionPrincipal menuPrincipal = null;
	public  BarraTareaCR barraTareaFacturacion = null;
	private static String mensajeInicial = new String("  Verificador de precios:  ");
	private static JLabel statusBar = new JLabel(mensajeInicial);
	private static JScrollPane scrollBar = new JScrollPane(navigatorCR);
	private static JPanel panel = new JPanel();
	private static JButton botonBuscar = new JButton("Buscar");
	private static HTMLDocument docHTML = new HTMLDocument();
	private boolean bloqueada = false;

	/**
	 * Constructor para Verificador.
	 */
	public VerificadorEPA() {
		if (Sesion.getCaja().getEstado().equals(Sesion.BLOQUEADA)) {
			setBloqueada(true);
		} else 
			setBloqueada(false);
		
		urlLocal = new String(CR.class.getResource("/com/becoblohm/cr/verificador/verificadorCR.html").toString());
		urlEspera = new String(CR.class.getResource("/com/becoblohm/cr/verificador/mensajeEspera.html").toString());
		try {
			urlPrincipal = new String(InitCR.preferenciasCR.getConfigStringForParameter("sistema", "urlPrincipal"));
			if (urlPrincipal.startsWith("/com/becoblohm/cr/")) 
				urlPrincipal = urlLocal;
			urlVerificador = new String(InitCR.preferenciasCR.getConfigStringForParameter("sistema", "urlVerificador"));
		} catch (NoSuchNodeException e1) {
			logger.error("Verificador()", e1);

			Auditoria.registrarAuditoria("No se pudieron cargar preferencias del sistema", 'E');
		} catch (UnidentifiedPreferenceException e1) {
			logger.error("Verificador()", e1);

			Auditoria.registrarAuditoria("Error desconocido en carga de preferencias del sistema", 'E');
		}

		//this.splash.showSplash();
		this.setTitle("Sistema de Caja Registradora - Version " + CR.version);
		this.setResizable(false);
		this.getContentPane().setLayout(new BorderLayout());
		splashPrecio = new SplashPrecio(urlEspera);
		if (InitCR.getPreferencia("sistema" , "activarVerificador", "N").equals("S")) {
			cambiarURL(urlPrincipal);
		} else {
			cambiarURL(urlLocal);
		}
		this.getContentPane().add(scrollBar, BorderLayout.CENTER);
	    codigo.setSize(50, 50);
	    codigo.addKeyListener(this);
	    codigo.setName("Codigo");
	    botonBuscar.addKeyListener(this);
	    botonBuscar.addActionListener(this);
	    botonBuscar.setName("Buscar");
	    panel.setLayout(new BorderLayout());
	    panel.add(codigo, BorderLayout.CENTER);
	    panel.add(botonBuscar, BorderLayout.EAST);
	    panel.add(statusBar, BorderLayout.WEST);
	    this.getContentPane().add(panel, BorderLayout.SOUTH);
	    navigatorCR.addHyperlinkListener(new SimpleLinkListener(navigatorCR, statusBar));
		navigatorCR.addKeyListener(this);
		navigatorCR.setName("Navegador");
		splash.hideSplash();
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

		//Este es para manejar el event al tratar de cerrar la ventana (la X de la ventana)
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (logger.isDebugEnabled()) {
					logger.debug("windowClosing(WindowEvent) - start");
				}

				if(Sesion.getCaja().getEstado().equals(Sesion.BLOQUEADA)){
					MensajesVentanas.aviso("Caja bloqueada por el usuario #"+Sesion.getUsuarioActivo().getNumFicha()+".");
					Auditoria.registrarAuditoria("Intento de cierre del sistema. "+"Caja bloqueada por el usuario #"+Sesion.getUsuarioActivo().getNumFicha(), 'E');
				}	
				else{
					MensajesVentanas.aviso("Seleccione la opción en el menú para apagar la Caja Registradora.");
					Auditoria.registrarAuditoria("Intento de cierre anormal del sistema.", 'E');
				}	

				if (logger.isDebugEnabled()) {
					logger.debug("windowClosing(WindowEvent) - end");
				}
			}
		});
		
		CR.inputEscaner.getDocument().addDocumentListener(this);
		try{ CR.crVisor.enviarString("** CAJA CERRADA **", "IR A LA PROXIMA ..."); }
		catch(Exception ex){
			logger.error("Verificador()", ex);
}
		
	}

	/**
	 * Método cambiarURL.
	 * 	Cambia el archivo desplegado en el navegador (Verificador de Precios)
	 * @param url - Dirección de la página a mostrar.
	 */
	public void cambiarURL(String url){
		if (logger.isDebugEnabled()) {
			logger.debug("cambiarURL(String) - start");
		}

		try {
			if ((!(url.equals(urlPrincipal)))&&(!(url.equals(urlLocal)))){
				if(!(url.startsWith("http:")||url.startsWith("file:"))){
					if(url.startsWith("/")){
						url = "file:" + url;
					}				
					else{
						File file = new File(url);
						url = file.toURL().toString();
					}
				}
				docHTML = (HTMLDocument) navigatorCR.getDocument();
				docHTML.addDocumentListener(this);
				splashPrecio.showSplash();
			} 
			navigatorCR.setPage(url);
			navigatorCR.setEditable(false);
//			if (url.equals(urlPrincipal)&&(!(url.equals(urlLocal)))) Sesion.setCajaEnLinea(true);
			codigo.setText("");
			mensajeDeEspera();
			if (!(statusBar.getText().equals(mensajeInicial))) statusBar.setText(mensajeInicial);
		} catch (IOException e) {
			logger.error("cambiarURL(String)", e);

			MensajesVentanas.mensajeError("No se cargó el verificador de precios", true); 
			statusBar.setText("No se pudo cargar el verificador!!");
			cambiarURL(urlLocal);
			codigo.setText("");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cambiarURL(String) - end");
		}
	}

	/**
	 *  Métodos para ActionListener
	 */
	public void actionPerformed(ActionEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("actionPerformed(ActionEvent) - start");
		}

		try{
			 
			
			Sesion.setUbicacion("UTILIDADES", "verificarCodigo");
			if (Sesion.isCajaEnLinea() && InitCR.getPreferencia("sistema" , "activarVerificador", "N").equals("S")){
				if ((e.getSource() instanceof JButton)&&(e.getID() == 1001)){
					String xCodigo = new String(codigo.getPassword());
					if (!xCodigo.equals("")){
						Auditoria.registrarAuditoria("Consultando el código " + xCodigo, 'O');

						String codProd;
						String conPre;
						try {
							codProd = xCodigo.substring(0,xCodigo.length() - 3);
							conPre = xCodigo.substring(xCodigo.length()-3);
						} catch (Exception ex) {
							logger.error("actionPerformed(ActionEvent)", ex);

							codProd = "";
							conPre = "";
						}
						String parametros = "cprodu=" + codProd + "&conpre=" + conPre;
						parametros += "&tienda=" + Sesion.getTienda().getNumero();
						SimpleDateFormat fechaActual = new SimpleDateFormat("dd.MM.yyyy");
						String fechaActualString = fechaActual.format(Sesion.getFechaSistema());
						parametros += "&fecha=" + fechaActualString;
						cambiarURL(urlVerificador + parametros);

						/*if (xCodigo.length() < 11)
							xCodigo = new String("0000"+xCodigo+"7");*/
						//cambiarURL(urlVerificador + xCodigo);
					}
				}
				codigo.requestFocus();
			} else{
				MensajesVentanas.mensajeError("No se cargó el verificador de precios", true); 
				codigo.setText("");
			}
		} catch(Exception ex){
			logger.error("actionPerformed(ActionEvent)", ex);
 
			MensajesVentanas.mensajeError("No se cargó el verificador de precios", true); 
			codigo.setText("");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("actionPerformed(ActionEvent) - end");
		}
	}

	/**
	 *  Métodos para KeyListener
	 */
	public void keyPressed(KeyEvent e){
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}

		boolean puedeFacturar = false;
		try{
			InitCR.cargarPreferencias();
			puedeFacturar = InitCR.preferenciasCR.getConfigStringForParameter("sistema", "puedeFacturar").trim().toUpperCase().equals("S") ? true : false;
			char funcionalidad = InitCR.preferenciasCR.getConfigStringForParameter("sistema", "funcionalidad").trim().toUpperCase().charAt(0);						
			if (((e.getSource() instanceof JEditorPane)||(e.getSource() instanceof JPasswordField)||(e.getSource() instanceof JButton))&&(e.getKeyCode() == KeyEvent.VK_F2)){
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				if(!bloqueada){
					if (MaquinaDeEstado.validarUsuario(false,false)){
						if ((Sesion.usuarioActivo.isIndicaCambiarClave()&&(Sesion.usuarioActivo.isPuedeCambiarClave()))){
							MensajesVentanas.aviso("Debe cambiar clave de acceso", true);
							MaquinaDeEstado.cambiarClaveInicio();
						}
						if(puedeFacturar){
							menuPrincipal = null;
							if (barraTareaFacturacion == null)
								barraTareaFacturacion = new BarraTareaCR();
							else 
								barraTareaFacturacion.repintarMenuPrincipal();
							//menuPrincipal = new FacturacionPrincipal();
							menuPrincipal = barraTareaFacturacion.getFacturacion();
							try{ CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ..."); }
							catch(Exception ex){
								logger.error("keyPressed(KeyEvent)", ex);
}
							this.setVisible(false);
							MensajesVentanas.centrarVentanaDialogo(menuPrincipal);
							this.setVisible(true);
						} else{
							switch (funcionalidad) {
								case '1' :	break;
								case '2' :	break;
								default :	CR.me.iraSeguridad();
											OpcionesSeguridad opcionesSeguridad = null;
											opcionesSeguridad = new OpcionesSeguridad();
											MensajesVentanas.centrarVentanaDialogo(opcionesSeguridad);
											MaquinaDeEstado.cerrarSesion();
											break;
							} 
						}
					}
				} else {
					if (CR.me.desbloquearCaja()) {
						if ((Sesion.usuarioActivo.isIndicaCambiarClave()&&(Sesion.usuarioActivo.isPuedeCambiarClave()))){
							MensajesVentanas.aviso("Debe cambiar clave de acceso", true);
							MaquinaDeEstado.cambiarClaveInicio();
						}
						this.setBloqueada(false);
						MediadorBD.asignarUsuarioLogueado(Sesion.getUsuarioActivo().getNumFicha());
						if(puedeFacturar){
							menuPrincipal = null;
							if (barraTareaFacturacion == null)
								barraTareaFacturacion = new BarraTareaCR();
							else 
								barraTareaFacturacion.repintarMenuPrincipal();
							//menuPrincipal = new FacturacionPrincipal();
							menuPrincipal = barraTareaFacturacion.getFacturacion();
							try{ CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ..."); }
							catch(Exception ex){
								logger.error("keyPressed(KeyEvent)", ex);
}
							MensajesVentanas.centrarVentanaDialogo(menuPrincipal);
							barraTareaFacturacion.dispose();
							barraTareaFacturacion = null;
						} else{
							CR.me.iraSeguridad();
							OpcionesSeguridad opcionesSeguridad = null;
							opcionesSeguridad = new OpcionesSeguridad();
							MensajesVentanas.centrarVentanaDialogo(opcionesSeguridad);
							MaquinaDeEstado.cerrarSesion();
						}
					}
				}
				CR.cerrarConexionEscaner();
				CR.abrirConexionEscaner();
				CR.inputEscaner.getDocument().addDocumentListener(this);
				navigatorCR.requestFocus();
			}
			else if (((e.getSource() instanceof JPasswordField)||(e.getSource() instanceof JButton))&&(e.getKeyCode() == KeyEvent.VK_ENTER))
			{
				botonBuscar.doClick();
			}
			else if ((e.getKeyCode() == KeyEvent.VK_UP)||(e.getKeyCode() == KeyEvent.VK_DOWN)||(e.getKeyCode() == KeyEvent.VK_PAGE_UP)||(e.getKeyCode() == KeyEvent.VK_PAGE_DOWN))
			{
				codigo.requestFocus();
			}
			else if (((e.getSource() instanceof JButton)||(e.getSource() instanceof JEditorPane))&&(e.getKeyCode() != KeyEvent.VK_ENTER))
			{
				codigo.requestFocus();
			}
		} catch (BaseDeDatosExcepcion ex) {
			logger.error("keyPressed(KeyEvent)", ex);

			MensajesVentanas.mensajeError(ex.getMensaje(), true);
			if(!Sesion.getCaja().getEstado().equals(Sesion.BLOQUEADA))
				fallaInicio();
		} catch (ExcepcionCr ex) {
			logger.error("keyPressed(KeyEvent)", ex);

			MensajesVentanas.mensajeError(ex.getMensaje(), true);
			if(!Sesion.getCaja().getEstado().equals(Sesion.BLOQUEADA))
				fallaInicio();
		} catch(Exception ex){
			logger.error("keyPressed(KeyEvent)", ex);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - end");
		}
	}
	public void keyReleased(KeyEvent e){
		if (logger.isDebugEnabled()) {
			logger.debug("keyReleased(KeyEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("keyReleased(KeyEvent) - end");
		}
	}
	public void keyTyped(KeyEvent e){
		if (logger.isDebugEnabled()) {
			logger.debug("keyTyped(KeyEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("keyTyped(KeyEvent) - end");
		}
	}

	/**
	 * Método fallaInicio
	 * 
	 * 
	 */
	public void fallaInicio(){
		if (logger.isDebugEnabled()) {
			logger.debug("fallaInicio() - start");
		}

		if((Sesion.getCaja().getUsuarioLogueado() != null) && (!Sesion.getCaja().getUsuarioLogueado().equals("")) && (!Sesion.getCaja().getEstado().equals(Sesion.BLOQUEADA)))
			try {
				MaquinaDeEstado.cerrarSesion();
			} catch (ExcepcionCr e1) {
				MensajesVentanas.mensajeError("No pudo cerrarse la sesión del usuario: "+Sesion.usuarioActivo.getNumFicha(), true);
				logger.error("fallaInicio()", e1);
			} catch (ConexionExcepcion e1) {
				MensajesVentanas.mensajeError("No pudo cerrarse la sesión del usuario: "+Sesion.usuarioActivo.getNumFicha(), true);
				logger.error("fallaInicio()", e1);
			}

		if (logger.isDebugEnabled()) {
			logger.debug("fallaInicio() - end");
		}
	}

	/**
	 *  Métodos para DocumentListener
	 */
	public void insertUpdate(DocumentEvent e){
		if (logger.isDebugEnabled()) {
			logger.debug("insertUpdate(DocumentEvent) - start");
		}
		if (InitCR.getPreferencia("sistema" , "activarVerificador", "N").equals("S")) { 
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if (logger.isDebugEnabled()) {
						logger.debug("run() - start");
					}

					lecturaEscaner(CR.inputEscaner.getText());

					if (logger.isDebugEnabled()) {
						logger.debug("run() - end");
					}
				}
			});
		}

		if (logger.isDebugEnabled()) {
			logger.debug("insertUpdate(DocumentEvent) - end");
		}
	}
	public void lecturaEscaner(String codEscaner) {
		if (logger.isDebugEnabled()) {
			logger.debug("lecturaEscaner(String) - start");
		}

		mensajeDeEspera();
		try{
			Sesion.setUbicacion("UTILIDADES", "verificarCodigo");
			if (Sesion.isCajaEnLinea()){
				String xCodigo = "";
				try {
					xCodigo = new String(codEscaner.substring(0,11));
				} catch (Exception ex) {
					logger.error("lecturaEscaner(String)", ex);

					xCodigo = new String(codEscaner.trim());
				}
				if (!xCodigo.equals("")){
					Auditoria.registrarAuditoria("Consultando el codigo " + xCodigo, 'O');
					String codProd;
					String conPre;
					try {
						codProd = xCodigo.substring(0,xCodigo.length() - 3);
						conPre = xCodigo.substring(xCodigo.length()-3);
					} catch (Exception ex) {
						logger.error("lecturaEscaner(String)", ex);

						codProd = "";
						conPre = "";
					}
					String parametros = "cprodu=" + codProd + "&conpre=" + conPre;
					parametros += "&tienda=" + Sesion.getTienda().getNumero();
					SimpleDateFormat fechaActual = new SimpleDateFormat("dd.MM.yyyy");
					String fechaActualString = fechaActual.format(Sesion.getFechaSistema());
					parametros += "&fecha=" + fechaActualString;
					cambiarURL(urlVerificador + parametros);
				}
				codigo.requestFocus();
			} else{
				MensajesVentanas.mensajeError("No se cargó el verificador de precios", true); 
				codigo.setText("");
			}
		} catch(Exception ex){
			logger.error("lecturaEscaner(String)", ex);
 
			MensajesVentanas.mensajeError("No se cargó el verificador de precios", true); 
			codigo.setText("");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("lecturaEscaner(String) - end");
		}
	}
	
	public void removeUpdate(DocumentEvent e){
		if (logger.isDebugEnabled()) {
			logger.debug("removeUpdate(DocumentEvent) - start");
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (logger.isDebugEnabled()) {
					logger.debug("run() - start");
				}

				mensajeDeEspera();

				if (logger.isDebugEnabled()) {
					logger.debug("run() - end");
				}
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("removeUpdate(DocumentEvent) - end");
		}
	}
	public void changedUpdate(DocumentEvent e){
		if (logger.isDebugEnabled()) {
			logger.debug("changedUpdate(DocumentEvent) - start");
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (logger.isDebugEnabled()) {
					logger.debug("run() - start");
				}

				mensajeDeEspera();

				if (logger.isDebugEnabled()) {
					logger.debug("run() - end");
				}
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("changedUpdate(DocumentEvent) - end");
		}
	}

	/**
	 * Método mensajeDeEspera.
	 * 	Utilizada por el Verificador para visualizar un mensaje de espera mientras se cambia la }
	 * página en el navegador. 
	 */
	final static ActionListener cargandoPagina = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			if (logger.isDebugEnabled()) {
				logger.debug("actionPerformed(ActionEvent) - start");
			}

			splashPrecio.hideSplash();

			if (logger.isDebugEnabled()) {
				logger.debug("actionPerformed(ActionEvent) - end");
			}
		}
	};

	final static ActionListener esperaHome = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			if (logger.isDebugEnabled()) {
				logger.debug("actionPerformed(ActionEvent) - start");
			}

			try {
				navigatorCR.setPage(VerificadorEPA.urlPrincipal);
			} catch (IOException e) {
				logger.error("actionPerformed(ActionEvent)", e);
			}

			if (logger.isDebugEnabled()) {
				logger.debug("actionPerformed(ActionEvent) - end");
			}
		}
	};

	final static int goHome = 6000; //milliseconds
	final static int waitPagina = 1000; //milliseconds
	final static Timer home = new Timer(goHome, esperaHome);
	final static Timer cargando = new Timer(waitPagina, cargandoPagina);

	public static void mensajeDeEspera() {
		if (logger.isDebugEnabled()) {
			logger.debug("mensajeDeEspera() - start");
		}

		home.restart();
		home.setCoalesce(false);
		home.setRepeats(false);
		cargando.restart();
		cargando.setCoalesce(false);
		cargando.setRepeats(false);

		if (logger.isDebugEnabled()) {
			logger.debug("mensajeDeEspera() - end");
		}
	}

	/**
	 * Método setBloqueada
	 * 
	 * @param b
	 * void
	 */
	public void setBloqueada(boolean b) {
		if (logger.isDebugEnabled()) {
			logger.debug("setBloqueada(boolean) - start");
		}

		bloqueada = b;

		if (logger.isDebugEnabled()) {
			logger.debug("setBloqueada(boolean) - end");
		}
	}

}
