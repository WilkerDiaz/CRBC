/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.manejarpago
 * Programa   : PantallaPagos.java
 * Creado por : gmartinelli
 * Creado en  : 30-abr-04 8:09
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     :
 * Fecha       :
 * Analista    :
 * Descripción :
 * ---------------------------------------------------------------------------
 * $Log: PantallaDatosAdicionales.java,v $
 * Revision 1.9  2007/07/26 19:02:29  programa1
 * ajuste del tamaño de ventana de tarjeta de credito EPA
 *
 * Revision 1.8  2007/05/15 13:22:50  programa8
 * Version Ayudar es Sencillo (Donacion de clientes) prueba piloto EPA15
 *
 * Revision 1.7  2007/04/25 22:02:41  programa8
 * Version CR-1.1.23 estable
 *
 * Revision 1.6  2006/09/28 22:49:10  programa4
 * Corregido bug de numero de cuenta para cheque (se limitaba a 10 cuando deben ser 20)
 *
 * Revision 1.5  2006/09/28 19:04:55  programa4
 * Modificada forma de cargar titulo y bancos.
 * La modificacion del combo de bancos es para poder identificar el codigo del banco cargado mas facilmente
 *
 * Revision 1.4  2006/09/20 16:30:55  programa4
 * Puesta en uso de los DocumentFilter
 *
 * Revision 1.3  2006/09/18 16:53:05  programa4
 * Puesta en uso de los DocumentFilter
 *
 * Revision 1.2  2006/07/10 19:38:09  programa8
 * Manejo de pagos convertido en extension
 * Convertida en extension menu de utilitarios (F8 - F9)
 *
 * Revision 1.1.2.6  2006/06/28 13:34:19  programa4
 * Arreglado para que cuando haya excepcion armando pago, no lo agregue
 *
 * Revision 1.1.2.5  2006/06/27 15:22:47  programa4
 * Modificado manejo de excepciones
 *
 * Revision 1.1.2.4  2006/06/13 19:23:17  programa4
 * Se hacen metodos de llamada a botones aceptar y cancelar protected,
 * para que se puedan acceder desde las clases hijas
 *
 * Revision 1.1.2.3  2006/06/12 12:40:59  programa4
 * Aplicadas refactorizaciones para facilitar funcionamiento de punto agil.
 *
 * Se aplica una modificacion para que arme el objeto pago directamente, en lugar de armar un vector.
 *
 * Revision 1.1.2.2  2006/05/25 20:27:50  programa4
 * Refactorizaciones para poder hacer modificaciones en la extension
 *
 * Revision 1.1.2.1  2006/05/24 18:46:11  programa4
 * Convertido el SubSistema de Pagos a Extension
 *
 * Revision 1.10.2.1  2006/05/23 18:35:13  programa4
 * Se incrementa encapsulacion,
 * Otros paquetes que requieran usar BaseDeDatosPago lo hacen a traves de DefaultManejoPagos
 *
 * Las demas clases que no son dto, ahora tienen ambito de paquete, para que
 * no puedan ser llamadas directamente desde afuera del paquete.
 *
 * Todo esto con la intencion de preparar para convertir este paquete en una extension.
 *
 * Revision 1.10  2005/08/04 12:29:29  programa8
 * Ajuste de Validación en Datos Adicionales de Pago
 *
 * Revision 1.9  2005/03/16 18:56:19  programa8
 * Version 1.0 - Release Candidate 1
 * *- Log de errores en temp/errorsCR.log
 * *- Ajuste de excepciones en curso normal de inicio de aplicacion y repintado de factura
 * *- Reintento de obtención de comprobante fiscal
 * *- Ajuste en monto recaudado de caja
 *
 * Revision 1.8  2005/03/10 15:50:55  programa8
 * CR al 10/03/2005. Merging desde branch STABLE
 *
 * Revision 1.7.4.9  2005/03/09 20:58:47  red_de_epa\alinux
 * Cambio para verificar si los campos de numero de cedula, documenrto etc etc etc son validos y no tienen alfabeticos
 *
 * Revision 1.7.4.8  2005/03/07 13:49:38  programa8
 * Ajustes de detalles en GUI
 *
 * Revision 1.7.6.1  2005/02/28 18:13:27  programa8
 * Version Inestable al 28/02/2005
 *     *-Preparación para trabajar sin administrador de ventanas.
 *     *-Reordenamiento de GUI
 *     *-Mejoras en scroll y pantallas de pagos
 *     *- Mantenimiento de estado en Cliente en Espera
 *     *- Avisos del sistema manejados por la aplicacion
 *     *- Desbloqueo de caja por otros usuarios reparado.
 *
 * Revision 1.7.4.7  2005/02/02 20:23:12  acastillo
 * Limpieza de código. 0 trailing todo's
 *
 * Revision 1.7.4.6  2005/02/02 20:19:53  acastillo
 * Limpieza de código. 0 trailing todo's
 *
 * Revision 1.7.4.5  2005/02/02 14:45:12  acastillo
 * Limpieza de código. 0 warning
 *
 * Revision 1.7.4.4  2005/01/12 01:51:51  acastillo
 * Ajustes en GUI
 *
 * Revision 1.7.4.3  2005/01/06 15:43:08  vmedina
 * Agregue la lista de cambios a la cabecera del archivo
 *
 * ===========================================================================
 */

package com.becoblohm.cr.extensiones.impl.manejarpago;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.text.AbstractDocument;

import org.apache.log4j.Logger;

import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.PagoExcepcion;
import com.becoblohm.cr.extensiones.ManejoPagosFactory;
import com.becoblohm.cr.gui.DocumentContentFilter;
import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.gui.TextVerifier;
import com.becoblohm.cr.utiles.Control;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;

/**
 * Descripción:
 *
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
* Fecha: agosto 2011
*/
public class PantallaDatosAdicionales extends JDialog implements KeyListener,
		MouseListener, FocusListener {
	protected static final String TITULO_DATOS_ADICIONALES_DE_PAGO = "Datos Adicionales de Pago";

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
		.getLogger(PantallaDatosAdicionales.class);

	protected javax.swing.JPanel jContentPane = null;

	private javax.swing.JPanel jPanel2 = null;

	private javax.swing.JButton jButtonCancelar = null;

	protected javax.swing.JPanel jPanel3 = null;

	private javax.swing.JPanel jPanel4 = null;

	private javax.swing.JLabel jLabel1 = null;

	protected javax.swing.JPanel jPanel5 = null;

	private javax.swing.JButton jButtonAceptar = null;

	private javax.swing.JLabel jLabel = null;

	private javax.swing.JLabel jLabel2 = null;

	private javax.swing.JLabel jLabelFormaPago = null;

	private javax.swing.JLabel jLabelMontoPago = null;

	protected javax.swing.JPanel jPanel6 = null;

	protected javax.swing.JTextField jTextFieldNumDocumento = null;

	private javax.swing.JTextField jTextFieldNumCuenta = null;

	protected javax.swing.JTextField jTextFieldNumConformacion = null;

	private javax.swing.JTextField jTextFieldNumReferencia = null;

	protected javax.swing.JTextField jTextFieldCedula = null;

	private javax.swing.JPanel jPanel7 = null;

	private javax.swing.JPanel jPanel8 = null;

	private javax.swing.JPanel jPanel9 = null;

	private javax.swing.JPanel jPanel10 = null;

	private javax.swing.JPanel jPanel11 = null;

	private javax.swing.JPanel jPanel = null;

	protected javax.swing.JComboBox jComboBoxBanco = null;

	protected boolean solicitarDato[] = new boolean[6];

	protected int datosASolicitar = 0;

	protected boolean error = true;

	private String lectura = "";

	protected final FormaDePago formaPago;

	protected final double monto;

	private boolean reiniciarNumeracion = false;

	protected Vector<Banco> bancos;

	/**
	 * This is the default constructor
	 *
	 * @throws PagoExcepcion
	 *
	 * @throws BaseDeDatosExcepcion
	 */
	public PantallaDatosAdicionales(FormaDePago fPago, double _monto)
			throws PagoExcepcion {
		super(MensajesVentanas.ventanaActiva);
		this.formaPago = fPago;
		this.monto = _monto;
		initSolicitarDato();
		for (int i = 0; i < solicitarDato.length; i++) {
			datosASolicitar += solicitarDato[i] ? 1 : 0;
		}
		bancos = ManejoPagosFactory.getInstance().obtenerBancos();

		initialize();

		this.jLabelFormaPago.setText(fPago.getNombre());
		this.jLabelMontoPago.setText(Control.formatearMonto(monto));

		jButtonCancelar.addKeyListener(this);
		jButtonCancelar.addMouseListener(this);

		jButtonAceptar.addKeyListener(this);
		jButtonAceptar.addMouseListener(this);

		getJTextFieldNumDocumento().addKeyListener(this);
		getJTextFieldNumDocumento().addFocusListener(this);
		getJTextFieldNumCuenta().addKeyListener(this);
		getJTextFieldNumCuenta().addFocusListener(this);
		getJTextFieldNumConformacion().addKeyListener(this);
		getJTextFieldNumConformacion().addFocusListener(this);
		getJTextFieldNumReferencia().addKeyListener(this);
		getJTextFieldNumReferencia().addFocusListener(this);
		getJTextFieldCedula().addKeyListener(this);
		getJTextFieldCedula().addFocusListener(this);
		getJComboBoxBanco().addKeyListener(this);
		getJComboBoxBanco().addFocusListener(this);
		getJComboBoxBanco().addMouseListener(this);

	}

	/**
	 * @param fPago
	 *            void
	 */
	protected void initSolicitarDato() {
		solicitarDato[0] = formaPago.isIndicarBanco() ? true : false;
		solicitarDato[1] = formaPago.isIndicarNumDocumento() ? true : false;
		solicitarDato[2] = formaPago.isIndicarNumCuenta() ? true : false;
		solicitarDato[3] = formaPago.isIndicarNumConformacion() ? true : false;
		solicitarDato[4] = formaPago.isIndicarNumReferencia() ? true : false;
		solicitarDato[5] = formaPago.isIndicarCedulaTitular() ? true : false;
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 * @throws PagoExcepcion
	 * @throws BaseDeDatosExcepcion
	 */
	protected void initialize() throws PagoExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("initialize() - start");
		}

		if (datosASolicitar > 6) {
			this.setSize(595, 371);
		} else if (datosASolicitar > 3) {
			this.setSize(595, 348);
			//this.setSize(595, 318);
		} else {
			this.setSize(595, 265);
		}
		this.setContentPane(getJContentPane());
		this.setUndecorated(true);
		this.setModal(true);
		this.setBackground(new java.awt.Color(226, 226, 222));
		this.setTitle(TITULO_DATOS_ADICIONALES_DE_PAGO);

		if (logger.isDebugEnabled()) {
			logger.debug("initialize() - end");
		}
	}

	/**
	 * This method initializes jContentPane
	 *
	 * @return javax.swing.JPanel
	 */
	protected javax.swing.JPanel getJContentPane() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJContentPane() - start");
		}

		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout14 = new java.awt.FlowLayout();
			layFlowLayout14.setHgap(5);
			jContentPane.setLayout(layFlowLayout14);
			jContentPane.add(getJPanel3(), null);
			jContentPane.add(getJPanel2(), null);
			jContentPane.setBorder(new javax.swing.border.SoftBevelBorder(
				javax.swing.border.SoftBevelBorder.RAISED));
			jContentPane.setBackground(new java.awt.Color(226, 226, 222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJContentPane() - end");
		}
		return jContentPane;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - start");
		}
		
		
		
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			error = true;
			dispose();
		}

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		
			reiniciarNumeracion = true;
			if (e.getSource().equals(this.getJComboBoxBanco())) {
				getJComboBoxBanco().transferFocus();
			}
			if (e.getSource().equals(this.getJTextFieldNumDocumento())) {
				getJTextFieldNumDocumento().transferFocus();
			}
			if (e.getSource().equals(this.getJTextFieldNumCuenta())) {
				getJTextFieldNumCuenta().transferFocus();
			}
			if (e.getSource().equals(this.getJTextFieldNumConformacion())) {
				try {
					int ref = new Integer(this
						.getJTextFieldNumConformacion()
						.getText()).intValue();
					getJTextFieldNumConformacion().transferFocus();
				} catch (Exception e1) {
					logger.error("keyPressed(KeyEvent)", e1);

					MensajesVentanas
						.mensajeError("Número de Conformación Inválido");
					getJTextFieldNumConformacion().setText("");
				}
			}
			if (e.getSource().equals(this.getJTextFieldNumReferencia())) {
				try {
					int ref = new Integer(jTextFieldNumReferencia.getText())
						.intValue();
					getJTextFieldNumReferencia().transferFocus();
				} catch (Exception e1) {
					logger.error("keyPressed(KeyEvent)", e1);

					MensajesVentanas
						.mensajeError("Número de Referencia Inválido");
					getJTextFieldNumReferencia().setText("");
				}
			}
			if (e.getSource().equals(this.getJTextFieldCedula())) {
				getJTextFieldCedula().transferFocus();
			}
			

		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER
				|| e.getKeyCode() == KeyEvent.VK_SPACE) {
			
			
			
			if (e.getSource().equals(this.getJButtonCancelar())) {
				error = true;
				dispose();
			}

			if (e.getSource().equals(this.getJButtonAceptar())) {
				
				efectuarPago();
				
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("keyPressed(KeyEvent) - end");
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyReleased(KeyEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("keyReleased(KeyEvent) - end");
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("keyTyped(KeyEvent) - Lecturas: " + lectura.length()
					+ ". Reiniciar Numeracion: " + reiniciarNumeracion);
		}
		if (reiniciarNumeracion) {
			// Verificamos qué se ingresó
			if (lectura.length() == 37) {
				// Se realizó una lectura por la lectora de cheque
				jButtonCancelar.transferFocus();
				if ((lectura.charAt(0) != '_') && (lectura.charAt(0) != ' ')) {
					this.getJTextFieldNumDocumento().setText(
						lectura.substring(1, 9));
					this.getJTextFieldNumCuenta().setText(
						lectura.substring(10, 14) + lectura.substring(15, 19)
								+ lectura.substring(20, 22)
								+ lectura.substring(23, 33));
					this.getJTextFieldNumCuenta().transferFocus();
					// Chequeamos los cuadros de texto para borrar si se ingresó
					// en algún cuadro.
					if (getJTextFieldNumConformacion()
						.getText()
						.equalsIgnoreCase("lectura"))
						getJTextFieldNumConformacion().setText("");
					if (getJTextFieldNumReferencia()
						.getText()
						.equalsIgnoreCase("lectura"))
						getJTextFieldNumReferencia().setText("");
					if (getJTextFieldCedula().getText().equalsIgnoreCase(
						"lectura"))
						getJTextFieldCedula().setText("");
				} else
					MensajesVentanas.mensajeError("Falla en Lectura de Cheque");
			}
			if (lectura.length() >= 50 && lectura.indexOf("_") >= 0
					&& lectura.indexOf("¿") >= 0) {
				// Si es una lectura de tarjeta.
				jButtonCancelar.transferFocus();
				if ((lectura.charAt(0) != '_') && (lectura.charAt(0) != ' ')) {
					StringTokenizer st = new StringTokenizer(lectura);
					st.nextToken("_");
					String documento = st.nextToken("¿").substring(5);
					this.getJTextFieldNumDocumento().setText(documento);
					this.getJTextFieldNumDocumento().transferFocus();
					// Chequeamos los cuadros de texto para borrar si se ingresó
					// en algún cuadro.
					if (getJTextFieldNumCuenta().getText().equalsIgnoreCase(
						"lectura"))
						getJTextFieldNumCuenta().setText("");
					if (getJTextFieldNumConformacion()
						.getText()
						.equalsIgnoreCase("lectura"))
						getJTextFieldNumConformacion().setText("");
					if (getJTextFieldNumReferencia()
						.getText()
						.equalsIgnoreCase("lectura"))
						getJTextFieldNumReferencia().setText("");
					if (getJTextFieldCedula().getText().equalsIgnoreCase(
						"lectura"))
						getJTextFieldCedula().setText("");
				} else
					MensajesVentanas
						.mensajeError("Falla en Lectura de Tarjeta");
			}
			reiniciarNumeracion = false;
			lectura = String.valueOf(e.getKeyChar());
		} else
			lectura += String.valueOf(e.getKeyChar());

		if (logger.isDebugEnabled()) {
			logger.debug("keyTyped(KeyEvent) - end");
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - start");
		}
		
		if (e.getSource().equals(this.getJButtonCancelar())) {
			this.error = true;
			dispose();
		}

		if (e.getSource().equals(this.getJButtonAceptar())) {
			if (logger.isDebugEnabled()) {
				logger.debug("mouseClicked(MouseEvent) - start (526)"+ e.getSource());
			}
			efectuarPago();
			
			if (logger.isDebugEnabled()) {
				logger.debug("mouseClicked(MouseEvent) - end  (528)"+ e.getSource());
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("mouseClicked(MouseEvent) - end");
		}
	}

	/**
	 * void
	 */
	protected void efectuarPago() {
		try {
			validacionPreEfectuarPago();
		} catch (PagoExcepcion e) {
			this.error = true;
			dispose();
		} finally {
			if (this.error == false) {
				dispose();
			}
		}
	}

	/**
	 * Pre validar pago
	 * @throws PagoExcepcion
	 */
	protected void validacionPreEfectuarPago() throws PagoExcepcion {
		try {
			// Chequemos que se ingresaron los datos necesarios
			if (InitCR.preferenciasCR.getConfigStringForParameter(
				"facturacion", "cedulaObligatoria").equals("S")
					|| InitCR.preferenciasCR.getConfigStringForParameter(
						"facturacion", "numeroConfirmacionObligatorio").equals(
						"S")) {
				/*
				 * uno de los datos o los dos, no es (son) obligatorio(s), ya
				 * sabemos que al menos uno no es obligatorio revisamos cual de
				 * los dos es el obligatorio a continuacion
				 */
				this.error = false;
				// Si es la cedula obligatoria, verificamos que tenga datos
				if (InitCR.preferenciasCR.getConfigStringForParameter(
					"facturacion", "cedulaObligatoria").equals("S")) {
					if ((solicitarDato[5] && (jTextFieldCedula.getText().equals(
						"") || Integer.parseInt(jTextFieldCedula.getText().trim())== 0))) {
						MensajesVentanas
							.mensajeError("Debe escribir todos los datos solicitados, la cedula es obligatoria");
						this.error = true;
						getJButtonCancelar().transferFocus();
					}
				}

				// Si es el numero de confirmacion, verificamos que tenga datos
				if (InitCR.preferenciasCR.getConfigStringForParameter(
					"facturacion", "numeroConfirmacionObligatorio").equals("S")) {
					if ((solicitarDato[3] && jTextFieldNumConformacion
						.getText()
						.equals(""))) {
						MensajesVentanas
							.mensajeError("Debe escribir todos los datos solicitados, el numero de confirmacion es obligatorio");
						this.error = true;
						getJButtonCancelar().transferFocus();
					}
				}

				// Verificamos el resto de los datos
				if (validarRestoDatos()) {
					MensajesVentanas
						.mensajeError("Debe escribir todos los datos solicitados");
					this.error = true;
					getJButtonCancelar().transferFocus();
				}
			} else {
				// Ninguno de los dos datos es obligatorio, solo chequeamos el
				// resto
				if (validarRestoDatos()) {
					this.error = true;
					MensajesVentanas
						.mensajeError("Debe escribir todos los datos solicitados");
					getJButtonCancelar().transferFocus();
				}
			}
		} catch (NoSuchNodeException e) {
			// SI NO EXISTE PREFERENCIA SE ASUME QUE NO SON OBLIGATORIOS LOS
			// VALORES
			logger.error("efectuarPago(): " + e, e);
		} catch (UnidentifiedPreferenceException e) {
			// SI NO EXISTE PREFERENCIA SE ASUME QUE NO SON OBLIGATORIOS LOS
			// VALORES
			logger.error("efectuarPago(): " + e, e);
		}

	}

	/**
	 * @return boolean
	 */
	protected boolean validarRestoDatos() {
		return (solicitarDato[0] && jComboBoxBanco.getSelectedIndex() < 1)
				|| (solicitarDato[1] && jTextFieldNumDocumento
					.getText()
					.equals(""))
				|| (solicitarDato[2] && jTextFieldNumCuenta
					.getText()
					.equals(""))
				|| (solicitarDato[4] && jTextFieldNumReferencia
					.getText()
					.equals(""));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseEntered(MouseEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("mouseEntered(MouseEvent) - end");
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseExited(MouseEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("mouseExited(MouseEvent) - end");
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mousePressed(MouseEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("mousePressed(MouseEvent) - end");
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("mouseReleased(MouseEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("mouseReleased(MouseEvent) - end");
		}
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

		if (jPanel2 == null) {
			jPanel2 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout11 = new java.awt.FlowLayout();
			layFlowLayout11.setAlignment(java.awt.FlowLayout.RIGHT);
			layFlowLayout11.setHgap(0);
			layFlowLayout11.setVgap(0);
			jPanel2.setLayout(layFlowLayout11);
			jPanel2.add(getJButtonAceptar(), null);
			jPanel2.add(getJButtonCancelar(), null);
			jPanel2.setPreferredSize(new java.awt.Dimension(570, 35));
			jPanel2.setBackground(new java.awt.Color(226, 226, 222));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel2() - end");
		}
		return jPanel2;
	}

	/**
	 * This method initializes jButton
	 *
	 * @return javax.swing.JButton
	 */
	protected javax.swing.JButton getJButtonCancelar() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton() - start");
		}

		if (jButtonCancelar == null) {
			jButtonCancelar = new JHighlightButton();
			jButtonCancelar.setText("Cancelar");
			jButtonCancelar.setBackground(new java.awt.Color(226, 226, 222));
			jButtonCancelar
				.setIcon(new javax.swing.ImageIcon(
					getClass()
						.getResource(
							"/com/becoblohm/cr/gui/resources/icons/ix16x16/delete2.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton() - end");
		}
		return jButtonCancelar;
	}

	/**
	 * This method initializes jPanel3
	 *
	 * @return javax.swing.JPanel
	 */
	protected javax.swing.JPanel getJPanel3() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel3() - start");
		}

		if (jPanel3 == null) {
			jPanel3 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout12 = new java.awt.FlowLayout();
			layFlowLayout12.setHgap(0);
			layFlowLayout12.setVgap(0);
			jPanel3.setLayout(layFlowLayout12);
			if (datosASolicitar > 6) {
				jPanel3.setPreferredSize(new java.awt.Dimension(585, 283));
			} else if (datosASolicitar > 3) {
				jPanel3.setPreferredSize(new java.awt.Dimension(585, 260));
			} else {
				jPanel3.setPreferredSize(new java.awt.Dimension(585, 177));
			}
			jPanel3.add(getJPanel4(), null);
			jPanel3.add(getJPanel5(), null);
			jPanel3.add(getJPanel6(), null);
			jPanel3.setBackground(new java.awt.Color(242, 242, 238));
			jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(
				new java.awt.Color(69, 107, 127), 1));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel3() - end");
		}
		return jPanel3;
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

		if (jPanel4 == null) {
			jPanel4 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout2 = new java.awt.FlowLayout();
			layFlowLayout2.setAlignment(java.awt.FlowLayout.LEFT);
			layFlowLayout2.setVgap(10);
			layFlowLayout2.setHgap(5);
			jPanel4.setLayout(layFlowLayout2);
			jPanel4.add(getJLabel1(), null);
			jPanel4.setPreferredSize(new java.awt.Dimension(570, 50));
			jPanel4.setBackground(new java.awt.Color(69, 107, 127));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel4() - end");
		}
		return jPanel4;
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

		if (jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setText("Datos Adicionales");
			jLabel1
				.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jLabel1.setForeground(java.awt.Color.white);
			jLabel1
				.setIcon(new javax.swing.ImageIcon(
					getClass()
						.getResource(
							"/com/becoblohm/cr/gui/resources/icons/ix32x32/money_envelope.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel1() - end");
		}
		return jLabel1;
	}

	/**
	 * This method initializes jPanel5
	 *
	 * @return javax.swing.JPanel
	 */
	protected javax.swing.JPanel getJPanel5() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel5() - start");
		}

		if (jPanel5 == null) {
			jPanel5 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout13 = new java.awt.FlowLayout();
			layFlowLayout13.setHgap(6);
			layFlowLayout13.setVgap(1);
			layFlowLayout13.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel5.setLayout(layFlowLayout13);
			jPanel5.add(getJLabel2(), null);
			jPanel5.add(getJLabelFormaPago(), null);
			jPanel5.add(getJLabel(), null);
			jPanel5.add(getJLabelMontoPago(), null);
			jPanel5.setPreferredSize(new java.awt.Dimension(570, 48));
			jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(
				null, "Datos Adicionales para Pago: ",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
				java.awt.Color.black));
			jPanel5.setBackground(new java.awt.Color(242, 242, 238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel5() - end");
		}
		return jPanel5;
	}

	/**
	 * This method initializes jButton1
	 *
	 * @return javax.swing.JButton
	 */
	protected javax.swing.JButton getJButtonAceptar() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJButton1() - start");
		}

		if (jButtonAceptar == null) {
			jButtonAceptar = new JHighlightButton();
			jButtonAceptar.setText("Aceptar");
			jButtonAceptar.setBackground(new java.awt.Color(226, 226, 222));
			jButtonAceptar
				.setIcon(new javax.swing.ImageIcon(getClass().getResource(
					"/com/becoblohm/cr/gui/resources/icons/ix16x16/check2.png")));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJButton1() - end");
		}
		return jButtonAceptar;
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

		if (jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setText("Monto del Pago: ");
			jLabel.setBackground(new java.awt.Color(242, 242, 238));
			jLabel.setPreferredSize(new java.awt.Dimension(125, 16));
			jLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel() - end");
		}
		return jLabel;
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

		if (jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setText("Forma de Pago: ");
			jLabel2.setBackground(new java.awt.Color(242, 242, 238));
			jLabel2.setPreferredSize(new java.awt.Dimension(110, 16));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel2() - end");
		}
		return jLabel2;
	}

	/**
	 * This method initializes jLabel3
	 *
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabelFormaPago() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel3() - start");
		}

		if (jLabelFormaPago == null) {
			jLabelFormaPago = new javax.swing.JLabel();
			jLabelFormaPago.setPreferredSize(new java.awt.Dimension(165, 16));
			jLabelFormaPago
				.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel3() - end");
		}
		return jLabelFormaPago;
	}

	/**
	 * This method initializes jLabel5
	 *
	 * @return javax.swing.JLabel
	 */
	protected javax.swing.JLabel getJLabelMontoPago() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel5() - start");
		}

		if (jLabelMontoPago == null) {
			jLabelMontoPago = new javax.swing.JLabel();
			jLabelMontoPago.setPreferredSize(new java.awt.Dimension(130, 16));
			jLabelMontoPago
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabelMontoPago.setText("");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel5() - end");
		}
		return jLabelMontoPago;
	}

	/**
	 * This method initializes jPanel6
	 *
	 * @return javax.swing.JPanel
	 */
	protected javax.swing.JPanel getJPanel6() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel6() - start");
		}

		if (jPanel6 == null) {
			jPanel6 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout13 = new java.awt.FlowLayout();
			layFlowLayout13.setHgap(4);
			layFlowLayout13.setVgap(2);
			layFlowLayout13.setAlignment(java.awt.FlowLayout.LEFT);
			jPanel6.setLayout(layFlowLayout13);
			if (solicitarDato[0])
				jPanel6.add(getJPanel(), null);
			if (solicitarDato[1])
				jPanel6.add(getJPanel7(), null);
			if (solicitarDato[2])
				jPanel6.add(getJPanel8(), null);
			if (solicitarDato[4])
				jPanel6.add(getJPanel10(), null);
			if (solicitarDato[5])
				jPanel6.add(getJPanel11(), null);
			if (solicitarDato[3])
				jPanel6.add(getJPanel9(), null);
			if (datosASolicitar > 6) {
				jPanel6.setPreferredSize(new java.awt.Dimension(570, 183));
			} else if (datosASolicitar > 3) {
				jPanel6.setPreferredSize(new java.awt.Dimension(570, 160));				
			} else {
				jPanel6.setPreferredSize(new java.awt.Dimension(570, 77));
			}
			jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(
				javax.swing.BorderFactory.createLineBorder(
					java.awt.Color.gray, 1), "Datos Obligatorios: ",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
				java.awt.Color.black));
			jPanel6.setBackground(new java.awt.Color(242, 242, 238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel6() - end");
		}
		return jPanel6;
	}

	/**
	 * This method initializes jTextField1
	 *
	 * @return javax.swing.JTextField
	 */
	protected javax.swing.JTextField getJTextFieldNumDocumento() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField1() - start");
		}

		if (jTextFieldNumDocumento == null) {
			jTextFieldNumDocumento = new javax.swing.JTextField();
			jTextFieldNumDocumento.setPreferredSize(new java.awt.Dimension(
				165, 20));
			jTextFieldNumDocumento.setInputVerifier(new TextVerifier(
				10, "0123456789"));
			AbstractDocument doc = (AbstractDocument) jTextFieldNumDocumento
				.getDocument();
			doc.setDocumentFilter(new DocumentContentFilter(10, "0123456789"));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField1() - end");
		}
		return jTextFieldNumDocumento;
	}

	/**
	 * This method initializes jTextField2
	 *
	 * @return javax.swing.JTextField
	 */
	protected javax.swing.JTextField getJTextFieldNumCuenta() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField2() - start");
		}

		if (jTextFieldNumCuenta == null) {
			jTextFieldNumCuenta = new javax.swing.JTextField();
			jTextFieldNumCuenta
				.setPreferredSize(new java.awt.Dimension(165, 20));
			jTextFieldNumCuenta.setInputVerifier(new TextVerifier(
				20, "0123456789"));
			AbstractDocument doc = (AbstractDocument) jTextFieldNumCuenta
				.getDocument();
			doc.setDocumentFilter(new DocumentContentFilter(20, "0123456789"));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField2() - end");
		}
		return jTextFieldNumCuenta;
	}

	/**
	 * This method initializes jTextFieldNumConformacion
	 *
	 * @return javax.swing.JTextField
	 */
	protected javax.swing.JTextField getJTextFieldNumConformacion() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField3() - start");
		}

		if (jTextFieldNumConformacion == null) {
			jTextFieldNumConformacion = new javax.swing.JTextField();
			jTextFieldNumConformacion.setPreferredSize(new java.awt.Dimension(
				165, 20));
			jTextFieldNumConformacion.setInputVerifier(new TextVerifier(
				10, "0123456789"));
			AbstractDocument doc = (AbstractDocument) jTextFieldNumConformacion
				.getDocument();
			doc.setDocumentFilter(new DocumentContentFilter(10, "0123456789"));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField3() - end");
		}
		return jTextFieldNumConformacion;
	}

	/**
	 * This method initializes jTextField4
	 *
	 * @return javax.swing.JTextField
	 */
	protected javax.swing.JTextField getJTextFieldNumReferencia() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField4() - start");
		}

		if (jTextFieldNumReferencia == null) {
			jTextFieldNumReferencia = new javax.swing.JTextField();
			jTextFieldNumReferencia.setPreferredSize(new java.awt.Dimension(
				165, 20));
			jTextFieldNumReferencia.setInputVerifier(new TextVerifier(
				10, "0123456789"));
			AbstractDocument doc = (AbstractDocument) jTextFieldNumReferencia
				.getDocument();
			doc.setDocumentFilter(new DocumentContentFilter(10, "0123456789"));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField4() - end");
		}
		return jTextFieldNumReferencia;
	}

	/**
	 * This method initializes jTextField5
	 *
	 * @return javax.swing.JTextField
	 */
	protected javax.swing.JTextField getJTextFieldCedula() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField5() - start");
		}

		if (jTextFieldCedula == null) {
			jTextFieldCedula = new javax.swing.JTextField();
			jTextFieldCedula.setPreferredSize(new java.awt.Dimension(165, 20));
			jTextFieldCedula
				.setInputVerifier(new TextVerifier(10, "0123456789"));
			AbstractDocument doc = (AbstractDocument) jTextFieldCedula
				.getDocument();
			doc.setDocumentFilter(new DocumentContentFilter(10, "0123456789"));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJTextField5() - end");
		}
		return jTextFieldCedula;
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

		if (jPanel7 == null) {
			jPanel7 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout13 = new java.awt.FlowLayout();
			layFlowLayout13.setHgap(0);
			layFlowLayout13.setVgap(0);
			layFlowLayout13.setAlignment(java.awt.FlowLayout.CENTER);
			jPanel7.setLayout(layFlowLayout13);
			jPanel7.add(getJTextFieldNumDocumento(), null);
			jPanel7.setPreferredSize(new java.awt.Dimension(180, 50));
			jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(
				null, "Número de Documento: ",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
				java.awt.Color.black));
			jPanel7.setBackground(new java.awt.Color(242, 242, 238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel7() - end");
		}
		return jPanel7;
	}

	/**
	 * This method initializes jPanel7
	 *
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel8() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel8() - start");
		}

		if (jPanel8 == null) {
			jPanel8 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout13 = new java.awt.FlowLayout();
			layFlowLayout13.setHgap(0);
			layFlowLayout13.setVgap(0);
			layFlowLayout13.setAlignment(java.awt.FlowLayout.CENTER);
			jPanel8.setLayout(layFlowLayout13);
			jPanel8.add(getJTextFieldNumCuenta(), null);
			jPanel8.setPreferredSize(new java.awt.Dimension(180, 50));
			jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(
				null, "Número de Cuenta: ",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
				java.awt.Color.black));
			jPanel8.setBackground(new java.awt.Color(242, 242, 238));
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

		if (jPanel9 == null) {
			jPanel9 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout13 = new java.awt.FlowLayout();
			layFlowLayout13.setHgap(0);
			layFlowLayout13.setVgap(0);
			layFlowLayout13.setAlignment(java.awt.FlowLayout.CENTER);
			jPanel9.setLayout(layFlowLayout13);
			jPanel9.add(getJTextFieldNumConformacion(), null);
			jPanel9.setPreferredSize(new java.awt.Dimension(180, 50));
			jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(
				null, "Número de Conformación: ",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
				java.awt.Color.black));
			jPanel9.setBackground(new java.awt.Color(242, 242, 238));
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

		if (jPanel10 == null) {
			jPanel10 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout13 = new java.awt.FlowLayout();
			layFlowLayout13.setHgap(0);
			layFlowLayout13.setVgap(0);
			layFlowLayout13.setAlignment(java.awt.FlowLayout.CENTER);
			jPanel10.setLayout(layFlowLayout13);
			jPanel10.add(getJTextFieldNumReferencia(), null);
			jPanel10.setPreferredSize(new java.awt.Dimension(180, 50));
			jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(
				null, "Número de Referencia: ",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
				java.awt.Color.black));
			jPanel10.setBackground(new java.awt.Color(242, 242, 238));
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

		if (jPanel11 == null) {
			jPanel11 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout13 = new java.awt.FlowLayout();
			layFlowLayout13.setHgap(0);
			layFlowLayout13.setVgap(0);
			layFlowLayout13.setAlignment(java.awt.FlowLayout.CENTER);
			jPanel11.setLayout(layFlowLayout13);
			jPanel11.add(getJTextFieldCedula(), null);
			jPanel11.setPreferredSize(new java.awt.Dimension(180, 50));
			jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(
				null, "Cédula del Titular: ",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
				java.awt.Color.black));
			jPanel11.setBackground(new java.awt.Color(242, 242, 238));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel11() - end");
		}
		return jPanel11;
	}

	/**
	 * Método obtenerDatosAdicionales
	 *
	 * @return Vector
	 */
	public Pago obtenerDatosAdicionales() throws PagoExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerDatosAdicionales() - start");
		}
		// Si hubo un error en el ingreso (Se canceló el ingreso de datos)
		if (error){
			throw new PagoExcepcion(
				"Error al registrar Pago.\nNo se ingresaron los datos necesarios");
		}
		Pago pagoResultante = armarPago();

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerDatosAdicionales() - end");
		}
		return pagoResultante;
	}

	/**
	 * void
	 *
	 * @throws PagoExcepcion
	 */
	protected Pago armarPago() throws PagoExcepcion {
		String codBanco = solicitarDato[0] ? ((Banco) bancos
			.elementAt(getJComboBoxBanco().getSelectedIndex() - 1))
			.getCodBanco() : null;
		String numDocumento = getJTextFieldNumDocumento()
			.getText()
			.equalsIgnoreCase("") ? null : getJTextFieldNumDocumento()
			.getText();
		String numCuenta = getJTextFieldNumCuenta().getText().equalsIgnoreCase(
			"") ? null : getJTextFieldNumCuenta().getText();
		Integer numConformacion = getJTextFieldNumConformacion()
			.getText()
			.equalsIgnoreCase("") ? new Integer(0) : new Integer(
			getJTextFieldNumConformacion().getText());
		Integer numReferencia = getJTextFieldNumReferencia()
			.getText()
			.equalsIgnoreCase("") ? new Integer(0) : new Integer(
			getJTextFieldNumReferencia().getText());
		String numCedula = getJTextFieldCedula().getText().equalsIgnoreCase("") ? null
				: getJTextFieldCedula().getText();
		Pago pago = new Pago(
			this.formaPago, this.monto, codBanco, numDocumento, numCuenta,
			numConformacion.intValue(), numReferencia.intValue(), numCedula);

		return pago;
	}

	/**
	 * Método focusGained
	 *
	 * @param e
	 */
	public void focusGained(FocusEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("focusGained(FocusEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("focusGained(FocusEvent) - end");
		}
	}

	/**
	 * Método focusLost
	 *
	 * @param e
	 */
	public void focusLost(FocusEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("focusLost(FocusEvent) - start");
		}

		reiniciarNumeracion = true;
		

		if (logger.isDebugEnabled()) {
			logger.debug("focusLost(FocusEvent) - end");
		}
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

		if (jPanel == null) {
			jPanel = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setHgap(0);
			layFlowLayout1.setVgap(0);
			jPanel.setLayout(layFlowLayout1);
			jPanel.add(getJComboBoxBanco(), null);
			jPanel.setBackground(new java.awt.Color(242, 242, 238));
			jPanel.setPreferredSize(new java.awt.Dimension(180, 50));
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(
				null, "Banco: ",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel() - end");
		}
		return jPanel;
	}

	/**
	 * This method initializes jComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	protected javax.swing.JComboBox getJComboBoxBanco() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJComboBox() - start");
		}

		if (jComboBoxBanco == null) {
			jComboBoxBanco = new javax.swing.JComboBox();
			jComboBoxBanco.setBackground(new java.awt.Color(224, 224, 222));
			jComboBoxBanco.setPreferredSize(new java.awt.Dimension(170, 25));
			if (bancos.size() > 1) {
				jComboBoxBanco.addItem("Seleccione Uno");
			}
			for (int i = 0; i < bancos.size(); i++)
				jComboBoxBanco.addItem(bancos.elementAt(i));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJComboBox() - end");
		}
		return jComboBoxBanco;
	}

	public boolean isError() {
		return error;
	}
	
	
} // @jve:visual-info decl-index=0 visual-constraint="10,10"
