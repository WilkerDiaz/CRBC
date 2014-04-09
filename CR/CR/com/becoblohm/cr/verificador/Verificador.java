package com.becoblohm.cr.verificador;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.gui.BarraTareaCR;
import com.becoblohm.cr.gui.FacturacionPrincipal;
import com.becoblohm.cr.gui.OpcionesSeguridad;
import com.becoblohm.cr.gui.SplashScreen;
import com.becoblohm.cr.manejadorauditoria.Auditoria;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstado;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.utiles.MensajesVentanas;

/*
 * Creado el 05-ene-05
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */

/**
 * @author gmartinelli
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class Verificador extends JFrame implements ComponentListener, ActionListener, KeyListener, DocumentListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(VerificadorEPA.class);
	
	private javax.swing.JPanel jContentPane = null;
	
	private int ancho;
	private int alto;
	private int longitud;

	private javax.swing.JLabel jLabel = null;
	private javax.swing.JPanel jPanel = null;
	private javax.swing.JPanel jPanel1 = null;
	private javax.swing.JPanel jPanel2 = null;
	private javax.swing.JPanel jPanel3 = null;
	private javax.swing.JPanel jPanel4 = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JLabel jLabel3 = null;
	private javax.swing.JTextField jTextField = null;
	private javax.swing.JLabel jLabel4 = null;
	private javax.swing.JLabel jLabel5 = null;
	private javax.swing.JLabel jLabel6 = null;
	
	private BaseDeDatos bd;
	private int tiempoEspera;
	private boolean bloqueada = false;
	public  FacturacionPrincipal menuPrincipal = null;
	public  BarraTareaCR barraTareaFacturacion = null;
	public static SplashScreen splash = new SplashScreen(1000);
	
	/**
	 * This is the default constructor
	 */
	public Verificador(String[] args) {
		super();
		
		this.ancho = Integer.parseInt(args[0]);
		this.alto = Integer.parseInt(args[1]);
		String clave = args.length > 5 ? args[5] : "";
		this.bd = new BaseDeDatos(args[2], args[4], clave);
		this.tiempoEspera = Integer.parseInt(args[3]);
		
		if (Sesion.getCaja().getEstado().equals(Sesion.BLOQUEADA)) {
			setBloqueada(true);
		} else 
			setBloqueada(false);
			
		initialize();
		
		this.getJTextField().addKeyListener(this);
		splash.hideSplash();
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
		try{
			 CR.crVisor.enviarString("** CAJA CERRADA **", "IR A LA PROXIMA ..."); 
		}
		catch(Exception ex){
			logger.error("Verificador()", ex);
		}
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(ancho, alto);
		this.setContentPane(getJContentPane());
		this.setTitle("Verificador de Precios");
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);
		this.addComponentListener(this);
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.EAST);
			jContentPane.add(getJPanel2(), java.awt.BorderLayout.WEST);
			jContentPane.add(getJPanel3(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanel4(), java.awt.BorderLayout.SOUTH);
			jContentPane.setBackground(new java.awt.Color(112,149,182));
		}
		return jContentPane;
	}
	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		if(jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setText("");
			jLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/verificador/imagenes/tope.png")));
			jLabel.setBackground(new java.awt.Color(151,177,202));
		}
		return jLabel;
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
			layFlowLayout2.setHgap(0);
			layFlowLayout2.setVgap(0);
			layFlowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel.setLayout(layFlowLayout2);
			jPanel.add(getJLabel(), null);
			jPanel.setBackground(new java.awt.Color(151,177,202));
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
			jPanel1.add(getJLabel5(), null);
			jPanel1.setBackground(new java.awt.Color(112,149,182));
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
			jPanel2.add(getJLabel4(), null);
			jPanel2.setBackground(new java.awt.Color(112,149,182));
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
			java.awt.FlowLayout layFlowLayout21 = new java.awt.FlowLayout();
			layFlowLayout21.setHgap(0);
			layFlowLayout21.setVgap(30);
			jPanel3.setLayout(layFlowLayout21);
			if (alto>600)
				jPanel3.add(getJLabel6(), null);
			jPanel3.add(getJLabel2(), null);
			jPanel3.add(getJLabel3(), null);
			jPanel3.add(getJTextField(), null);
			jPanel3.setBackground(new java.awt.Color(112,149,182));
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
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setHgap(0);
			layFlowLayout1.setVgap(0);
			jPanel4.setLayout(layFlowLayout1);
			jPanel4.add(getJLabel1(), null);
			jPanel4.setBackground(new java.awt.Color(112,149,182));
		}
		return jPanel4;
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
			jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/verificador/imagenes/bordeInferior.png")));
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
			jLabel2.setText("");
			jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/becoblohm/cr/verificador/imagenes/codigoBarra.png")));
		}
		return jLabel2;
	}
	/**
	 * This method initializes jLabel3
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel3() {
		if(jLabel3 == null) {
			jLabel3 = new javax.swing.JLabel();
			jLabel3.setText("CODIGO DEL PRODUCTO:     ");
			jLabel3.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
			jLabel3.setForeground(java.awt.Color.white);
		}
		return jLabel3;
	}
	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField() {
		if(jTextField == null) {
			jTextField = new javax.swing.JTextField();
			jTextField.setPreferredSize(new java.awt.Dimension(125,24));
			jTextField.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
			jTextField.setForeground(new java.awt.Color(3,86,149));
		}
		return jTextField;
	}
	/**
	 * This method initializes jLabel4
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel4() {
		if(jLabel4 == null) {
			jLabel4 = new javax.swing.JLabel();
			jLabel4.setText("");
			jLabel4.setPreferredSize(new java.awt.Dimension((ancho-400)/2,15));
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
			jLabel5.setText("");
			jLabel5.setPreferredSize(new java.awt.Dimension((ancho-400)/2,15));
		}
		return jLabel5;
	}
	/**
	 * This method initializes jLabel6
	 * 
	 * @return javax.swing.JLabel
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se comentó variable sin uso
	* Fecha: agosto 2011
	*/
	private javax.swing.JLabel getJLabel6() {
		if(jLabel6 == null) {
			jLabel6 = new javax.swing.JLabel();
			jLabel6.setText("");
			//int a = 44*(new Integer(alto/600)).intValue();
			jLabel6.setPreferredSize(new java.awt.Dimension(383, new Integer(alto/600).intValue()*100));
		}
		return jLabel6;
	}
	/* (no Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent arg0) {
		longitud = jTextField.getText().length();
	}
	/* (no Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@SuppressWarnings("deprecation")
	public void keyPressed(KeyEvent arg0) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}
		if (arg0.getKeyCode()==KeyEvent.VK_ENTER) {
			try{
				Sesion.setUbicacion("UTILIDADES", "verificarCodigo");
				if (Sesion.isCajaEnLinea()){
					// Llamamos a la pantalla de Resultado
					Resultado pResult = new Resultado(ancho, alto, jTextField.getText().trim(), bd);
					pResult.setVisible(true);
					pResult.iniciarTimer(tiempoEspera);
					this.getJTextField().setText("");
				} else{
					MensajesVentanas.mensajeError("No se cargó el verificador de precios", true); 
					this.getJTextField().setText("");
				}
			} catch(Exception ex){
				logger.error("lecturaEscaner(String)", ex);
 
				MensajesVentanas.mensajeError("No se cargó el verificador de precios", true); 
				this.getJTextField().setText("");
			}	
		} else if ((arg0.getSource() instanceof JTextField) && (arg0.getKeyCode() == KeyEvent.VK_F2)) {
			boolean puedeFacturar = false;
			try{
				
				InitCR.cargarPreferencias();
				puedeFacturar = InitCR.preferenciasCR.getConfigStringForParameter("sistema", "puedeFacturar").trim().toUpperCase().equals("S") ? true : false;
				char funcionalidad = InitCR.preferenciasCR.getConfigStringForParameter("sistema", "funcionalidad").trim().toUpperCase().charAt(0);						
				CR.inputEscaner.getDocument().removeDocumentListener(this);
				if(!bloqueada){
					if (MaquinaDeEstado.validarUsuario(false,false)){
						try {
						// Verificamos si se trata de un Cajero de Servicios al Cliente para Chequear apertura de Caja Principal
							if (CR.me.verificarAperturaCajaPrincipal()) {
								Sesion.verificarReporteZ();
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
										logger.warn("keyPressed(KeyEvent)", ex);
									}
									this.hide();
									MensajesVentanas.centrarVentanaDialogo(menuPrincipal);
									barraTareaFacturacion.dispose();
									barraTareaFacturacion = null;
									this.show();
								} else{
									switch (funcionalidad) {
										case '1' :	break;
										case '2' :	break;
										default :	this.hide();
													CR.me.iraSeguridad();
													OpcionesSeguridad opcionesSeguridad = null;
													opcionesSeguridad = new OpcionesSeguridad();
													MensajesVentanas.centrarVentanaDialogo(opcionesSeguridad);
													MaquinaDeEstado.cerrarSesion();
													this.show();
													break;
									} 
								}
							} else {
								throw new UsuarioExcepcion("No se ha aperturado Caja Principal\nNotifique al Encargado de Caja Principal");
							}
						} catch (Exception e1) {
							// Error al inicio de Caja, Cerramos sesion
							//Sesion.setUbicacion("UTILIDADES","emitirReporteZ");
							MaquinaDeEstado.cerrarSesion();
							throw(e1);
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
							this.hide();
							MensajesVentanas.centrarVentanaDialogo(menuPrincipal);
							barraTareaFacturacion.dispose();
							barraTareaFacturacion = null;
							this.show();
						} else{
							this.hide();
							CR.me.iraSeguridad();
							OpcionesSeguridad opcionesSeguridad = null;
							opcionesSeguridad = new OpcionesSeguridad();
							MensajesVentanas.centrarVentanaDialogo(opcionesSeguridad);
							MaquinaDeEstado.cerrarSesion();
							this.show();
						}
					}
				}
				CR.cerrarConexionEscaner();
				CR.abrirConexionEscaner();
				CR.inputEscaner.getDocument().addDocumentListener(this);
				this.jContentPane.requestFocus();
				this.getJTextField().requestFocus();
		} catch (BaseDeDatosExcepcion ex) {
			logger.error("keyPressed(KeyEvent)", ex);
				MensajesVentanas.mensajeError(ex.getMensaje(), true);
			if(!Sesion.getCaja().getEstado().equals(Sesion.BLOQUEADA))
				fallaInicio();
			this.getJTextField().requestFocus();
		} catch (ExcepcionCr ex) {
			logger.error("keyPressed(KeyEvent)", ex);
				MensajesVentanas.mensajeError(ex.getMensaje(), true);
			if(!Sesion.getCaja().getEstado().equals(Sesion.BLOQUEADA))
				fallaInicio();
			this.getJTextField().requestFocus();	
		} catch(Exception ex){
			ex.printStackTrace();
			logger.error("keyPressed(KeyEvent)", ex);
			MensajesVentanas.mensajeError("Error al Validar Usuario. Intente de Nuevo\n" + ex.getMessage(), true);
			this.getJTextField().requestFocus();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - end");
		}
	}
}
	/* (no Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyCode()!=KeyEvent.VK_ENTER && jTextField.getText().length()>longitud) {
			try {
				jTextField.setText(jTextField.getText().substring(0, 11));
			} catch (Exception e1) {
			}
		}
	}
	/**
	 * Método actionPerformed
	 *
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {
	}

	/**
	 *  Métodos para DocumentListener
	 */
	public void insertUpdate(DocumentEvent e){
		if (logger.isDebugEnabled()) {
			logger.debug("insertUpdate(DocumentEvent) - start");
		}
		if (InitCR.getPreferencia("sistema" , "activarVerificador", "N").equals("S")) { 
			lecturaEscaner(CR.inputEscaner.getText());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("insertUpdate(DocumentEvent) - end");
		}
	}
	
	public void lecturaEscaner(String codEscaner) {
		if (logger.isDebugEnabled()) {
			logger.debug("lecturaEscaner(String) - start");
		}

		try{
			Sesion.setUbicacion("UTILIDADES", "verificarCodigo");
			this.getJTextField().setText(codEscaner.substring(0,codEscaner.trim().length()-1));
			if (Sesion.isCajaEnLinea()){
				// Llamamos a la pantalla de Resultado
				Resultado pResult = new Resultado(ancho, alto, codEscaner.substring(0,codEscaner.trim().length()-1), bd);
				pResult.setVisible(true);
				pResult.iniciarTimer(tiempoEspera);
				this.getJTextField().setText("");
			} else{
				MensajesVentanas.mensajeError("No se cargó el verificador de precios", true); 
				this.getJTextField().setText("");
			}
		} catch(Exception ex){
			logger.error("lecturaEscaner(String)", ex);
 
			MensajesVentanas.mensajeError("No se cargó el verificador de precios", true); 
			this.getJTextField().setText("");
			this.getJTextField().requestFocus();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("lecturaEscaner(String) - end");
		}
	}
	
	public void removeUpdate(DocumentEvent e){
	}
	
	public void changedUpdate(DocumentEvent e){
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
		int width = this.getWidth();
		int height = this.getHeight();
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - width) / 2;
		int y = (screen.height - height) / 2;
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
}

