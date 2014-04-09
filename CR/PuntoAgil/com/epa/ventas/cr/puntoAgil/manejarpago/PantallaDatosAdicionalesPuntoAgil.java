/*
 * $Id: PantallaDatosAdicionalesPuntoAgil.java,v 1.6 2006/07/10 19:25:27
 * programa4 Exp $
 * ===========================================================================
 *
 * Proyecto : PuntoAgil Paquete : com.epa.ventas.cr.puntoAgil.manejarpago
 * Programa : PantallaDatosAdicionalesPuntoAgil.java Creado por : programa4
 * Creado en : 25/05/2006 11:16:27 AM (C) Copyright 2006 Todos los Derechos
 * Reservados
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo : $Name:  $ Bloqueado por : $Locker:  $ Estado de Revisión :
 * $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: PantallaDatosAdicionalesPuntoAgil.java,v $
 * Revision 1.20  2007/07/26 18:57:12  programa1
 * arreglos para imprimir voucher en secuencia 0 y ventana de tarjeta de credito EPA
 *
 * Revision 1.19  2007/05/15 13:17:21  programa8
 * Version estable 2007-05-15  prueba piloto EPA3
 *
 * Revision 1.18  2007/05/14 13:56:12  programa8
 * Version Estable EPA3 2007-05-14
 *
 * Revision 1.17  2007/05/14 13:48:35  programa8
 * Version En EPA3 Prueba Piloto 2007-05-14
 *
 * Revision 1.16  2007/05/07 15:37:14  programa8
 * Version estable Punto Agil 2007-05-07
 *
 * Revision 1.15  2007/04/25 18:46:04  programa8
 * Version Estable Pto Agil, controlando cargos dos dobles , una sola instancia a datosExternos , control fuera de linea
 *
 * Revision 1.14  2006/11/09 16:28:13  programa4
 * Traduccion de CVV a codigo de seguridad segun exigencias de credicard
 *
 * Revision 1.13  2006/09/29 15:56:58  programa8
 * Se vuelven a mostrar los datos de tarjeta, ya que solo se deben dejar
 * de registrar en la base de datos
 *
 * Revision 1.12  2006/09/28 19:08:24  programa4
 * Actualizacion por requerimientos credicard
 * Se cargan datos de programas de lealtad segun banco seleccionado
 * Modificados mensajes de error para hacerlos mas legibles
 * Verificados titulos para indicar version del punto agil
 *
 * Revision 1.11  2006/09/20 16:28:48  programa4
 * Puesta en uso de los DocumentFilter para numdocumento (maximo 4 caracteres en tarjeta de credito)
 *
 * Revision 1.10  2006/09/18 16:54:45  programa4
 * Puesta en uso de los DocumentFilter
 *
 * Revision 1.9  2006/09/07 22:55:00  programa4
 * Agregada conversion a numero de la cedula para retirar ceros (0) a la izquierda
 *
 * Revision 1.8  2006/09/07 14:36:14  programa4
 * Se agrego condicion para que tambien tome en cuenta los colaboradores a la hora de cargar la cedula en la
 * pantalla de Punto Agil
 *
 * Revision 1.7  2006/08/25 18:17:40  programa4
 * Correccion en anulacion de cheque (para que no solicite lectura banda)
 * Y convertido PuntoAgilSubSistema en singleton en lugar de metodos estaticos
 * Revision 1.6 2006/07/10
 * 19:25:27 programa4 Ajustada validacion de CVV para que permita 3 o 4 digitos
 * Ajustada validacion de numdocumento para que solo sea de 4 digitos si es
 * tarjeta
 *
 * Revision 1.5 2006/07/05 15:25:53 programa4 Agregado soporte a provimillas y
 * actualizado anulacion
 *
 * Revision 1.4 2006/06/27 15:51:55 programa4 Refactorizadas consultas Agregados
 * metodos sincronizacion Modificado manejo de excepciones
 *
 * Revision 1.3 2006/06/16 21:32:36 programa4 Agregada anulacion de pagos.
 * Actualizacion de datos con datos de los pagos de ventas, tarjeta de cred. epa
 * y abonos. Agregado aviso de cierre si han pasado mas de 24 horas del ultimo
 * cierre. Agregado numServicio para poder referir a abonos Agregadas consultas
 * de puntos
 *
 * Revision 1.2 2006/06/10 02:16:40 programa4 Agregados valores requeridos por
 * punto agil Ademas de metodo llamar a procesamiento de pagos y generacion de
 * dto de pagos
 *
 * Revision 1.1 2006/05/25 20:40:42 programa4 Primeros pasos para procesamiento
 * de cheques por Punto Agil
 *
 *
 * ===========================================================================
 */
/**
 * Clase PantallaDatosAdicionalesPuntoAgil
 *
 * @author programa4 - $Author: programa1 $
 * @version $Revision: 1.20 $ - $Date: 2007/07/26 18:57:12 $
 * @since 25/05/2006
 */
package com.epa.ventas.cr.puntoAgil.manejarpago;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.PagoExcepcion;
import com.becoblohm.cr.extensiones.ManejoPagosFactory;
import com.becoblohm.cr.extensiones.impl.manejarpago.Banco;
import com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.extensiones.impl.manejarpago.PantallaDatosAdicionales;
import com.becoblohm.cr.gui.DocumentContentFilter;
import com.becoblohm.cr.gui.TextVerifier;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.utiles.Control;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.ventas.cr.puntoAgil.Constantes;
import com.epa.ventas.cr.puntoAgil.DatosCuentasEspecialesPuntoAgil;
import com.epa.ventas.cr.puntoAgil.DatosFormaDePagoPuntoAgil;
import com.epa.ventas.cr.puntoAgil.DatosPagoPuntoAgil;
import com.epa.ventas.cr.puntoAgil.DatosPlanesCreditoEPAPuntoAgil;
import com.epa.ventas.cr.puntoAgil.DatosTarjetaPuntoAgil;
import com.epa.ventas.cr.puntoAgil.DatosTipoCuentaPuntoAgil;
import com.epa.ventas.cr.puntoAgil.PuntoAgilSubSistema;

/**
 * @author programa4 - $Author: programa1 $
 * @version $Revision: 1.20 $ - $Date: 2007/07/26 18:57:12 $
 * @since 25/05/2006
 */
public class PantallaDatosAdicionalesPuntoAgil extends PantallaDatosAdicionales {

	private static final String ACTION_COMMAND_REQUIERE_DATO_ADICIONAL = "RDA";

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
		.getLogger(PantallaDatosAdicionalesPuntoAgil.class);

	private final DatosTarjetaPuntoAgil datosTarjetaPuntoAgil;

	private javax.swing.JLabel jLabel3;

	private javax.swing.JLabel jLabel4;

	private javax.swing.JLabel jLabel5;

	private javax.swing.JLabel jLabel6;

	private javax.swing.JPasswordField jPasswordFieldNumTarjeta;

	private javax.swing.JLabel jLabelTipoTarjeta;

	private javax.swing.JLabel jLabelNombreTarjetaHabiente;

	private javax.swing.JLabel jLabelFechaVencimiento;

	private javax.swing.JPanel jPanel12;

	private javax.swing.JPanel jPanel13;

	private javax.swing.JPanel jPanel14;

	private javax.swing.JPanel jPanelPlanCreditoEPA;

	private JTextField jTextFieldPlanCreditoEPA;

	private JTextField jTextFieldCodigoSeguridad;

	private ButtonGroup buttonGroupPlanCreditoEPA;

	private javax.swing.JComboBox jComboBoxCuentasEspeciales;

	private javax.swing.JComboBox jComboBoxTipoCuenta;

	private Vector<DatosTipoCuentaPuntoAgil> tiposCuenta;

	private Vector<DatosCuentasEspecialesPuntoAgil> cuentasEspeciales;

	private Vector<DatosPlanesCreditoEPAPuntoAgil> planesCreditoEPA;

	/**
	 * Datos del pago, solo debe asignarse en el momento de que se efectua el
	 * pago
	 */
	private DatosPagoPuntoAgil datosPago;

	/** Indica si se debe procesar como pago (true) o como anulacion */
	private final boolean procesarPago;

	/**
	 * @param _pago
	 * @param _datosTarjetaPuntoAgil
	 * @param _monto
	 * @throws PagoExcepcion
	 * @see PantallaDatosAdicionales#PantallaDatosAdicionales(FormaDePago,
	 *      double)
	 */
	public PantallaDatosAdicionalesPuntoAgil(DatosFormaDePagoPuntoAgil _pago,
			DatosTarjetaPuntoAgil _datosTarjetaPuntoAgil, double _monto)
			throws PagoExcepcion {
		this(_pago, _datosTarjetaPuntoAgil, _monto, true);
	}

	/**
	 * @param _pago
	 * @param _datosTarjetaPuntoAgil
	 * @param _monto
	 * @param _procesarPago
	 *            Indica si se procesara pago por punto agil.
	 * @throws PagoExcepcion
	 * @see PantallaDatosAdicionales#PantallaDatosAdicionales(FormaDePago,
	 *      double)
	 */
	public PantallaDatosAdicionalesPuntoAgil(DatosFormaDePagoPuntoAgil _pago,
			DatosTarjetaPuntoAgil _datosTarjetaPuntoAgil, double _monto,
			boolean _procesarPago) throws PagoExcepcion {
		super(_pago, _monto);
		this.datosTarjetaPuntoAgil = _datosTarjetaPuntoAgil;
		this.procesarPago = _procesarPago;
		initVectores();
		getJPanel5();
		getJComboBoxBanco();

		getJComboBoxCuentasEspeciales().addKeyListener(this);
		getJComboBoxCuentasEspeciales().addFocusListener(this);
		getJComboBoxCuentasEspeciales().addMouseListener(this);

		getJComboBoxTipoCuenta().addKeyListener(this);
		getJComboBoxTipoCuenta().addFocusListener(this);
		getJComboBoxTipoCuenta().addMouseListener(this);

		getJTextFieldCodigoSeguridad().addKeyListener(this);
		getJTextFieldCodigoSeguridad().addFocusListener(this);
		getJTextFieldCodigoSeguridad().addMouseListener(this);

		getJTextFieldPlanCreditoEPA().addKeyListener(this);
		getJTextFieldPlanCreditoEPA().addFocusListener(this);
		getJTextFieldPlanCreditoEPA().addMouseListener(this);
		
		

	}

	/**
	 * @return DatosFormaDePagoPuntoAgil
	 */
	private DatosFormaDePagoPuntoAgil getDatosFormaDePagoPuntoAgil() {
		return (DatosFormaDePagoPuntoAgil) this.formaPago;
	}

	protected void initialize() throws PagoExcepcion {
		initVectores();
		super.initialize();
		this.setTitle(TITULO_DATOS_ADICIONALES_DE_PAGO
				+ Constantes.TITULO_VERSION);
	}

	/**
	 * @throws PagoExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private void initVectores() throws PagoExcepcion {
		ManejoPagosPuntoAgil mppa = (ManejoPagosPuntoAgil) ManejoPagosFactory
			.getInstance();
		try {
			String codBanco = null;
			if (this.tiposCuenta == null) {
				this.tiposCuenta = mppa.cargarTiposCuenta();
			}

			if (this.cuentasEspeciales == null
					|| (this.cuentasEspeciales.size() <= 1)) {
				if (isConsultaPuntos()) {
					// EN CASO DE QUE SE TRATE DE CONSULTA DE PUNTOS
					this.cuentasEspeciales = mppa
						.cargarCuentasEspecialesTipoConsulta(codBanco);
				} else {
					this.cuentasEspeciales = mppa
						.cargarCuentasEspecialesTipoTransaccion(codBanco);
				}
			}
			if (this.planesCreditoEPA == null) {
				this.planesCreditoEPA = mppa.cargarPlanesCreditoEPA();
				Vector<DatosPlanesCreditoEPAPuntoAgil> planesCredito = new Vector<DatosPlanesCreditoEPAPuntoAgil>();
				//Plan especifico para clientes Juridicos
				if (mppa.isEsJuridico()){
					for (int i=0; i<this.planesCreditoEPA.size(); i++){
						DatosPlanesCreditoEPAPuntoAgil plan = 
							 this.planesCreditoEPA.elementAt(i);
						
						if (plan.getDescripcion().trim().equalsIgnoreCase(Constantes.TCEPA_PLAN)){
							planesCredito.addElement(plan);
							getJTextFieldPlanCreditoEPA().setVisible(false);
							
						}
					}
					this.planesCreditoEPA = planesCredito;
					
				}else{
					getJTextFieldPlanCreditoEPA().setVisible(true);
				}
				
			}
		} catch (BaseDeDatosExcepcion e) {
			throw new PagoExcepcion(e.getMensaje(), e);
		}
	}

	/**
	 * @return boolean
	 */
	private boolean isConsultaPuntos() {
		return this.formaPago == Constantes.FDP_CONSULTA_PUNTOS_CREDITO
				|| this.formaPago == Constantes.FDP_CONSULTA_PUNTOS_DEBITO;
	}

	protected void initSolicitarDato() {
		this.solicitarDato = new boolean[11];
		super.initSolicitarDato();
		DatosFormaDePagoPuntoAgil fPago = getDatosFormaDePagoPuntoAgil();
		solicitarDato[6] = fPago.isRequiereLecturaBanda() ? true : false;
		solicitarDato[7] = fPago.isIndicarPlan() ? true : false;
		solicitarDato[8] = fPago.isIndicarCuentaEspecial() ? true : false;
		solicitarDato[9] = fPago.isIndicarTipoCuenta() ? true : false;
		solicitarDato[10] = fPago.getLongitudCodigoSeguridad() > 0 ? true
				: false;
	}

	public void keyPressed(KeyEvent _e) {
		
		super.keyPressed(_e);
		if (_e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (_e.getSource().equals(this.getJComboBoxCuentasEspeciales())) {
				this.getJComboBoxCuentasEspeciales().transferFocus();
			}
			if (_e.getSource().equals(this.getJComboBoxTipoCuenta())) {
				this.getJComboBoxTipoCuenta().transferFocus();
			}
			if (_e.getSource().equals(this.getJTextFieldCodigoSeguridad())) {
				this.getJTextFieldCodigoSeguridad().transferFocus();
			}
			if (_e.getSource().equals(this.getJTextFieldPlanCreditoEPA())) {
				this.getJTextFieldPlanCreditoEPA().transferFocus();
			}
			if (_e.getSource() instanceof JRadioButton) {
				((JRadioButton) _e.getSource()).transferFocus();
			}	
			
		}
	}

	protected boolean validarRestoDatos() {
		boolean superValidar = super.validarRestoDatos();
		if (superValidar) {
			return superValidar;
		}

		String numDocumento = getJTextFieldNumDocumento().getText();
		String cvv = getJTextFieldCodigoSeguridad().getText();
		boolean numDocumentoInvalido = false;
		if (this.solicitarDato[6]) {
			numDocumentoInvalido = (this.datosTarjetaPuntoAgil == null
					|| StringUtils.isBlank(this.datosTarjetaPuntoAgil
						.getNumeroTarjeta())
					|| StringUtils.isBlank(numDocumento)
					|| numDocumento.length() != 4 || !this.datosTarjetaPuntoAgil
				.getNumeroTarjeta()
				.endsWith(numDocumento));
		}
		if (numDocumentoInvalido) {
			MensajesVentanas.mensajeError("Número de Documento Invalido");
			getJTextFieldNumDocumento().requestFocus();
			return numDocumentoInvalido;
		}
		boolean cvvInvalido = false;
		if (this.solicitarDato[10]) {
			cvvInvalido = StringUtils.isBlank(cvv)
					|| cvv.length() != this
						.getDatosFormaDePagoPuntoAgil()
						.getLongitudCodigoSeguridad();
		}
		if (cvvInvalido) {
			MensajesVentanas.mensajeError("Codigo de Seguridad Invalido, verifique longitud.");
			getJTextFieldCodigoSeguridad().requestFocus();
			return cvvInvalido;
		}

		boolean planCreditoInvalido = false;
		if (this.solicitarDato[7]) {
			if (getButtonGroupPlanCreditoEPA().getSelection() == null) {
				planCreditoInvalido = true;
			} else {
				String plan = getButtonGroupPlanCreditoEPA()
					.getSelection()
					.getActionCommand();
				if (StringUtils.isBlank(plan) && !StringUtils.isNumeric(plan)
						&& Integer.parseInt(plan) < 0
						&& Integer.parseInt(plan) > 35) {
					planCreditoInvalido = true;
				}
			}
		}

		if (planCreditoInvalido) {
			MensajesVentanas.mensajeError("Plan de Crédito EPA Inválido");
			return planCreditoInvalido;
		}

		boolean tipoCuentaInvalido = false;
		if (this.solicitarDato[9]) {
			tipoCuentaInvalido = (getJComboBoxTipoCuenta().getSelectedIndex() < 1);
		}
		if (tipoCuentaInvalido) {
			MensajesVentanas.mensajeError("Tipo de Cuenta Inválido");
			getJComboBoxTipoCuenta().requestFocus();
			return tipoCuentaInvalido;
		}

		// boolean cuentasEspecialesInvalido = false;
		// if (solicitarDato[8]) {
		// cuentasEspecialesInvalido = (getJComboBoxCuentasEspeciales()
		// .getSelectedIndex() < 1);
		// }
		// if (cuentasEspecialesInvalido) {
		// MensajesVentanas.mensajeError("Tipo de Operación Inválido");
		// return cuentasEspecialesInvalido;
		// }
		return false;
	}

	protected void validacionPreEfectuarPago() throws PagoExcepcion {
		super.validacionPreEfectuarPago();
		if (this.error == false && this.procesarPago) {
			if (logger.isDebugEnabled()) {
				logger.debug("Entrada Procesar Pago PantallaDatosAdicionalesPuntoAgil  (412)");
			}
			// PROCEDER A PROCESAR PAGO POR PUNTO AGIL
			DatosPagoPuntoAgil pago = (DatosPagoPuntoAgil) this.armarPago();
			PuntoAgilSubSistema.getInstance().procesarPago(pago);
			this.datosPago = pago;
			
			if (logger.isDebugEnabled()) {
				logger.debug("Salida Procesar Pago PantallaDatosAdicionalesPuntoAgil (420)");
			}
		} else {
			PuntoAgilSubSistema.setSw(true);
		}
	}

	protected Pago armarPago() throws PagoExcepcion {
		// Se verifica si ya no hay un pago generado que haya sido procesado.
		DatosPagoPuntoAgil pago = this.datosPago;
		if (pago == null) {
			Integer tipoProceso;
			if (this.isConsultaPuntos()) {
				tipoProceso = Constantes.TIPO_PROCESO_PSEUDO_TRANSACCION_PUNTO_AGIL;
			} else {
				ManejoPagosPuntoAgil manejoPagosPuntoAgil = (ManejoPagosPuntoAgil) ManejoPagosFactory
					.getInstance();
				try {
					tipoProceso = manejoPagosPuntoAgil
						.obtenerTipoProcesoSegunEstadoCaja(Sesion
							.getCaja()
							.getEstado());
				} catch (BaseDeDatosExcepcion e) {
					throw new PagoExcepcion(e.getMensaje(), e);
				}
			}
			pago = new DatosPagoPuntoAgil(super.armarPago(), tipoProceso);

			String cvv = solicitarDato[10] ? getJTextFieldCodigoSeguridad()
				.getText() : null;

			String plan = solicitarDato[7]
					&& getButtonGroupPlanCreditoEPA().getSelection() != null ? getButtonGroupPlanCreditoEPA()
				.getSelection()
				.getActionCommand()
					: null;

			// RESTAR 1 A SELECTEDINDEX SI SE ACTIVA 'SELECCIONE OPCION'
			String ctaEspecial = solicitarDato[8] ? ((DatosCuentasEspecialesPuntoAgil) cuentasEspeciales
				.elementAt(getJComboBoxCuentasEspeciales().getSelectedIndex()))
				.getCuentaEspecial()
					: null;

			String tipoCuenta = solicitarDato[9] ? ((DatosTipoCuentaPuntoAgil) tiposCuenta
				.elementAt(getJComboBoxTipoCuenta().getSelectedIndex() - 1))
				.getTipoCuentaPuntoAgil()
					: null;

			if (solicitarDato[7] && plan != null
					&& StringUtils.isBlank(ctaEspecial)) {
				// ASIGNAR CUENTA ESPECIAL '0' CUANDO ES CON PAGO DE CREDITO EPA
				ctaEspecial = "0";
			}

			if (solicitarDato[7] && StringUtils.isNotBlank(plan)
					&& plan.equals(ACTION_COMMAND_REQUIERE_DATO_ADICIONAL)) {
				plan = StringUtils.isNotBlank(getJTextFieldPlanCreditoEPA()
					.getText()) ? getJTextFieldPlanCreditoEPA().getText()
						: null;
			}

			pago.setPlan(plan);
			pago.setCodSeguridad(cvv);
			pago.setCtasEspeciales(ctaEspecial);
			pago.setTipoCuenta(tipoCuenta);
			pago.setDatosTarjetaPuntoAgil(this.datosTarjetaPuntoAgil);
		}
		return pago;
	}

	//SE COMENTA SI LOS BANCOS EXIGEN OCULTA DATOS DE LA TARJETA
	protected JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = super.getJPanel5();
			jPanel5.setName("");
		}
		if (this.datosTarjetaPuntoAgil != null && "".equals(jPanel5.getName())) {
			this.setSize(new java.awt.Dimension(this.getWidth(), this
				.getHeight() + 48));
			this.getJPanel3().setPreferredSize(
					new java.awt.Dimension(585, (int) (this.getJPanel3()
							.getPreferredSize().getHeight() + 48)));
			jPanel5.setPreferredSize(new java.awt.Dimension(575, 84));
			jPanel5.add(getJLabel3(), null);
			jPanel5.add(getJPasswordFieldNumTarjeta(), null);
			jPanel5.add(getJLabel4(), null);
			jPanel5.add(getJLabelTipoTarjeta(), null);
			jPanel5.add(getJLabel5(), null);
			jPanel5.add(getJLabelNombreTarjetaHabiente(), null);
			jPanel5.add(getJLabel6(), null);
			jPanel5.add(getJLabelFechaVencimiento(), null);
			jPanel5.setName(this.datosTarjetaPuntoAgil.getNombreCliente());
			this.repaint();
		}
		return jPanel5;
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

		if (jLabel3 == null) {
			jLabel3 = new javax.swing.JLabel();
			jLabel3.setText("Num. Tarjeta: ");
			jLabel3.setBackground(new java.awt.Color(242, 242, 238));
			jLabel3.setPreferredSize(new java.awt.Dimension(110, 16));
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

		if (jLabel4 == null) {
			jLabel4 = new javax.swing.JLabel();
			jLabel4.setText("Tipo Tarjeta: ");
			jLabel4.setBackground(new java.awt.Color(242, 242, 238));
			jLabel4.setPreferredSize(new java.awt.Dimension(125, 16));
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

		if (jLabel5 == null) {
			jLabel5 = new javax.swing.JLabel();
			jLabel5.setText("Nombre: ");
			jLabel5.setBackground(new java.awt.Color(242, 242, 238));
			jLabel5.setPreferredSize(new java.awt.Dimension(110, 16));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel5() - end");
		}
		return jLabel5;
	}

	/**
	 * This method initializes jLabel6
	 *
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel6() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel6() - start");
		}

		if (jLabel6 == null) {
			jLabel6 = new javax.swing.JLabel();
			jLabel6.setText("Fecha Vencimiento: ");
			jLabel6.setBackground(new java.awt.Color(242, 242, 238));
			jLabel6.setPreferredSize(new java.awt.Dimension(125, 16));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel6() - end");
		}
		return jLabel6;
	}

	protected JTextField getJTextFieldCedula() {
		Cliente cliente = null;
		if (this.jTextFieldCedula == null) {
			this.jTextFieldCedula = super.getJTextFieldCedula();
			if (CR.meServ.getListaRegalos() != null) {
				cliente = CR.meServ.getListaRegalos().getCliente();
			//Wdiaz 10-10-2009 Modificacion Para asignar en Merchant Cliente en la Pantalla de Datos Adicionales por Servicio al Cliente
			}else
				if (CR.meVenta.getVenta() != null) {
					cliente = CR.meVenta.getVenta().getCliente();
			//Wdiaz 10-10-2009 Modificacion Para asignar en Merchant Cliente en la Pantalla de Datos Adicionales por Servicio al Cliente
			}else
				if(CR.meServ.getApartado() != null){
				 	cliente = CR.meServ.getApartado().getCliente();
				}
				
				// 23-09-2009 WDiaz y IRojas Modificacion Para asignar en Merchant Cliente en la Pantalla de Datos Adicionales 
				//if (cliente.getTipoCliente() == Sesion.CLIENTE_NATURAL
					//	|| cliente.getTipoCliente() == Sesion.COLABORADOR) {
			if ((CR.meVenta.getVenta() != null)||(CR.meServ.getApartado() != null) ||(CR.meServ.getListaRegalos() != null)){//realizado por WDIAZ	
				if (cliente != null && cliente.getCodCliente() != null  && ((cliente.getTipoCliente() == Sesion.CLIENTE_VENEZOLANO) || 
						(cliente.getTipoCliente() == Sesion.CLIENTE_NATURAL) || 
						(cliente.getTipoCliente() == Sesion.CLIENTE_EXTRANJERO) || 
						(cliente.getTipoCliente() == Sesion.COLABORADOR))){
					String cedula = Control.removerCaracteresNoListados(cliente
						.getCodCliente(), "0123456789");
					if (StringUtils.isNotBlank(cedula)
							&& StringUtils.isNumeric(cedula)) {
						this.jTextFieldCedula.setText(String.valueOf(Integer
							.parseInt(cedula)));
					}
				}
			}
			//}
		}
		return this.jTextFieldCedula;
	}

	protected JTextField getJTextFieldNumDocumento() {
		if (this.jTextFieldNumDocumento == null) {
			this.jTextFieldNumDocumento = super.getJTextFieldNumDocumento();
			if (this.solicitarDato[6]) {
				this.jTextFieldNumDocumento.setInputVerifier(new TextVerifier(
					4, "0123456789"));
				AbstractDocument doc = (AbstractDocument) this.jTextFieldNumDocumento
					.getDocument();
				doc
					.setDocumentFilter(new DocumentContentFilter(
						4, "0123456789"));
			}
		}
		return this.jTextFieldNumDocumento;
	}

	/**
	 * This method initializes jPasswordFieldNumTarjeta
	 *
	 * @return JPasswordField
	 */
	private javax.swing.JPasswordField getJPasswordFieldNumTarjeta() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel3() - start");
		}

		if (jPasswordFieldNumTarjeta == null) {
			jPasswordFieldNumTarjeta = new javax.swing.JPasswordField();
			jPasswordFieldNumTarjeta.setEditable(false);
			jPasswordFieldNumTarjeta.setEnabled(false);
			jPasswordFieldNumTarjeta.setPreferredSize(new java.awt.Dimension(
				165, 16));
			jPasswordFieldNumTarjeta
				.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			if (this.datosTarjetaPuntoAgil != null) {
				jPasswordFieldNumTarjeta.setText(this.datosTarjetaPuntoAgil
					.getNumeroTarjeta());
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel3() - end");
		}
		return jPasswordFieldNumTarjeta;
	}

	/**
	 * This method initializes jLabelTipoTarjeta
	 *
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabelTipoTarjeta() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel5() - start");
		}

		if (jLabelTipoTarjeta == null) {
			jLabelTipoTarjeta = new javax.swing.JLabel();
			jLabelTipoTarjeta.setPreferredSize(new java.awt.Dimension(130, 16));
			jLabelTipoTarjeta
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			if (this.datosTarjetaPuntoAgil != null) {
				jLabelTipoTarjeta.setText(this.datosTarjetaPuntoAgil
					.getTipoTarjeta());
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel5() - end");
		}
		return jLabelTipoTarjeta;
	}

	/**
	 * This method initializes jLabelNombreTarjetaHabiente
	 *
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabelNombreTarjetaHabiente() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel3() - start");
		}

		if (jLabelNombreTarjetaHabiente == null) {
			jLabelNombreTarjetaHabiente = new javax.swing.JLabel();
			jLabelNombreTarjetaHabiente
				.setPreferredSize(new java.awt.Dimension(165, 16));
			jLabelNombreTarjetaHabiente
				.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jLabelNombreTarjetaHabiente.setText(this.datosTarjetaPuntoAgil
				.getNombreCliente());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabel3() - end");
		}
		return jLabelNombreTarjetaHabiente;
	}

	/**
	 * This method initializes jLabelFechaVencimiento
	 *
	 * @return JLabel
	 */
	private javax.swing.JLabel getJLabelFechaVencimiento() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJLabelFechaVencimiento() - start");
		}

		if (jLabelFechaVencimiento == null) {
			jLabelFechaVencimiento = new javax.swing.JLabel();
			jLabelTipoTarjeta.setPreferredSize(new java.awt.Dimension(130, 16));
			jLabelTipoTarjeta
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			if (this.datosTarjetaPuntoAgil != null) {
				jLabelFechaVencimiento.setText(this.datosTarjetaPuntoAgil
					.getFechaVencimiento());
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJLabelFechaVencimiento() - end");
		}
		return jLabelFechaVencimiento;
	}

	protected JPanel getJPanel6() {
		if (jPanel6 == null) {
			jPanel6 = super.getJPanel6();
			if (getDatosFormaDePagoPuntoAgil().getLongitudCodigoSeguridad() > 0) {
				jPanel6.add(getJPanel14(), null);
			}
			if (getDatosFormaDePagoPuntoAgil().isIndicarTipoCuenta()) {
				jPanel6.add(getJPanel12(), null);
			}
			if (getDatosFormaDePagoPuntoAgil().isIndicarCuentaEspecial()) {
				jPanel6.add(getJPanel13(), null);
			}
			if (getDatosFormaDePagoPuntoAgil().isIndicarPlan()) {
				jPanel6.add(getJPanelPlanCreditoEPA(), null);
			}
		}
		return jPanel6;
	}

	/**
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel12() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel() - start");
		}

		if (jPanel12 == null) {
			jPanel12 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setHgap(0);
			layFlowLayout1.setVgap(0);
			jPanel12.setLayout(layFlowLayout1);
			jPanel12.add(getJComboBoxTipoCuenta(), null);
			jPanel12.setBackground(new java.awt.Color(242, 242, 238));
			jPanel12.setPreferredSize(new java.awt.Dimension(180, 50));
			jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(
				null, "Tipo de Cuenta: ",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel() - end");
		}
		return jPanel12;
	}

	/**
	 * This method initializes jComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private javax.swing.JComboBox getJComboBoxTipoCuenta() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJComboBox() - start");
		}

		if (jComboBoxTipoCuenta == null) {
			jComboBoxTipoCuenta = new javax.swing.JComboBox();
			jComboBoxTipoCuenta
				.setBackground(new java.awt.Color(224, 224, 222));
			jComboBoxTipoCuenta.setEnabled(true); // Generated
			jComboBoxTipoCuenta.setActionCommand("comboBoxChanged"); // Generated
			jComboBoxTipoCuenta.setSelectedIndex(-1); // Generated
			jComboBoxTipoCuenta
				.setPreferredSize(new java.awt.Dimension(170, 25));
			jComboBoxTipoCuenta.addItem("Seleccione Uno");
			for (int i = 0; i < tiposCuenta.size(); i++) {
				jComboBoxTipoCuenta.addItem(tiposCuenta.elementAt(i));
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJComboBox() - end");
		}
		return jComboBoxTipoCuenta;
	}

	/**
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel13() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel() - start");
		}

		if (jPanel13 == null) {
			jPanel13 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setHgap(0);
			layFlowLayout1.setVgap(0);
			jPanel13.setLayout(layFlowLayout1);
			jPanel13.add(getJComboBoxCuentasEspeciales(), null);
			jPanel13.setBackground(new java.awt.Color(242, 242, 238));
			jPanel13.setPreferredSize(new java.awt.Dimension(180, 50));
			jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(
				null, "Tipo de Operacion: ",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel() - end");
		}
		return jPanel13;
	}

	/**
	 * This method initializes jComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private javax.swing.JComboBox getJComboBoxCuentasEspeciales() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJComboBox() - start");
		}

		if (jComboBoxCuentasEspeciales == null) {
			jComboBoxCuentasEspeciales = new javax.swing.JComboBox();
			jComboBoxCuentasEspeciales.setBackground(new java.awt.Color(
				224, 224, 222));
			jComboBoxCuentasEspeciales.setPreferredSize(new java.awt.Dimension(
				170, 25));
			// jComboBoxCuentasEspeciales.addItem("Seleccione Uno");
			for (int i = 0; i < cuentasEspeciales.size(); i++) {
				jComboBoxCuentasEspeciales.addItem(cuentasEspeciales
					.elementAt(i));
			}
		}
		// SI EL COMBO YA ESTABA INICIALIZADO CON UN SOLO ELEMENTO,
		// PERO SE IDENTIFICARON ELEMENTOS ADICIONALES POR LOS BEANES DE LA TARJETA
		// SE VUELVE A CARGAR LA LISTA
		if (jComboBoxCuentasEspeciales != null
				&& this.cuentasEspeciales != null) {
			if ((jComboBoxCuentasEspeciales.getItemCount() <= 1 && this.cuentasEspeciales
				.size() > 1)
					|| (jComboBoxCuentasEspeciales.getItemCount() == 0)) {
				//LIMPIO EL COMBO BOX
				jComboBoxCuentasEspeciales.removeAllItems();
				//LO VUELVO A CARGAR
				for (int i = 0; i < cuentasEspeciales.size(); i++) {
					jComboBoxCuentasEspeciales.addItem(cuentasEspeciales
						.elementAt(i));
				}
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getJComboBox() - end");
		}
		return jComboBoxCuentasEspeciales;
	}

	/**
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Enumeration'
	* Fecha: agosto 2011
	*/
	private javax.swing.JPanel getJPanelPlanCreditoEPA() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel() - start");
		}

		if (jPanelPlanCreditoEPA == null) {
			jPanelPlanCreditoEPA = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setHgap(0);
			layFlowLayout1.setVgap(0);
			layFlowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
			jPanelPlanCreditoEPA.setLayout(layFlowLayout1);
			jPanelPlanCreditoEPA
				.setBackground(new java.awt.Color(242, 242, 238));
//			jPanelPlanCreditoEPA.setPreferredSize(new java.awt.Dimension(
//				(int) (this.getJPanel6().getPreferredSize().getWidth() * 0.97),
//				50));
			jPanelPlanCreditoEPA.setPreferredSize(new java.awt.Dimension(
					(int) (this.getJPanel6().getPreferredSize().getWidth() * 0.97),
					80));

			jPanelPlanCreditoEPA.setBorder(javax.swing.BorderFactory
				.createTitledBorder(
					null, "Plan de Crédito EPA a utilizar: ",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			Enumeration<AbstractButton> radioButtons = getButtonGroupPlanCreditoEPA()
				.getElements();
			while (radioButtons.hasMoreElements()) {
				JRadioButton element = (JRadioButton) radioButtons
					.nextElement();
				jPanelPlanCreditoEPA.add(element, null);
			}
			
			javax.swing.JLabel jLabelCuota = new javax.swing.JLabel();
			jLabelCuota.setText("                                     ");
			jLabelCuota.setBackground(new java.awt.Color(242, 242, 238));
			jLabelCuota.setPreferredSize(new java.awt.Dimension(240, 25));
			jPanelPlanCreditoEPA.add(jLabelCuota,null);
			jPanelPlanCreditoEPA.add(getJTextFieldPlanCreditoEPA(), null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel() - end");
		}
		return jPanelPlanCreditoEPA;
	}

	private ButtonGroup getButtonGroupPlanCreditoEPA() {
		if (this.buttonGroupPlanCreditoEPA == null) {
			this.buttonGroupPlanCreditoEPA = new ButtonGroup();
			for (int i = 0; i < this.planesCreditoEPA.size(); i++) {
				final DatosPlanesCreditoEPAPuntoAgil datosPlanesCreditoEPAPuntoAgil = (DatosPlanesCreditoEPAPuntoAgil) this.planesCreditoEPA
					.get(i);
				Action rdbAction = new AbstractAction(
						datosPlanesCreditoEPAPuntoAgil.getDescripcion().trim()) {
					public void actionPerformed(ActionEvent _e) {
						JRadioButton rdButton = (JRadioButton) _e.getSource();
						boolean requiereDatoAdicional = datosPlanesCreditoEPAPuntoAgil
							.isRequiereDatoAdicional();

						getJTextFieldPlanCreditoEPA().setEnabled(
							requiereDatoAdicional);

						if (requiereDatoAdicional) {
							// ACTION COMMAND QUE INDICA QUE REQUIERE PREGUNTAR
							// POR EL DATO ADICIONAL
							rdButton
								.setActionCommand(ACTION_COMMAND_REQUIERE_DATO_ADICIONAL);
							getJTextFieldPlanCreditoEPA().requestFocus();
						}
					}
				};
				rdbAction.putValue(Action.MNEMONIC_KEY, new Integer(
					datosPlanesCreditoEPAPuntoAgil
						.getAbrPlanCredito()
						.charAt(0)));
				rdbAction.putValue(
					Action.ACTION_COMMAND_KEY, datosPlanesCreditoEPAPuntoAgil
						.getPlanCredito());

				JRadioButton radioButton = new JRadioButton(rdbAction);
				radioButton.setPreferredSize(new java.awt.Dimension(150, 25));
				radioButton.setBackground(new java.awt.Color(242, 242, 238));

				radioButton.addKeyListener(this);
				radioButton.addFocusListener(this);
				radioButton.addMouseListener(this);
				this.buttonGroupPlanCreditoEPA.add(radioButton);
			}
		}
		return buttonGroupPlanCreditoEPA;
	}

	/**
	 * This method initializes jTextFieldPlanCreditoEPA
	 *
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextFieldPlanCreditoEPA() {
		if (logger.isDebugEnabled()) {
			logger.debug("getjTextFieldPlanCreditoEPA() - start");
		}

		if (jTextFieldPlanCreditoEPA == null) {
			jTextFieldPlanCreditoEPA = new javax.swing.JTextField();
			jTextFieldPlanCreditoEPA.setPreferredSize(new java.awt.Dimension(
				40, 16));
			jTextFieldPlanCreditoEPA.setEnabled(false);
			jTextFieldPlanCreditoEPA.setInputVerifier(new TextVerifier(
				2, "0123456789"));
			AbstractDocument doc = (AbstractDocument) jTextFieldPlanCreditoEPA
				.getDocument();
			doc.setDocumentFilter(new DocumentContentFilter(2, "0123456789"));

		}

		if (logger.isDebugEnabled()) {
			logger.debug("getjTextFieldPlanCreditoEPA() - end");
		}
		return jTextFieldPlanCreditoEPA;
	}

	/**
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel14() {
		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel() - start");
		}

		if (jPanel14 == null) {
			jPanel14 = new javax.swing.JPanel();
			java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
			layFlowLayout1.setHgap(0);
			layFlowLayout1.setVgap(0);
			jPanel14.setLayout(layFlowLayout1);
			jPanel14.add(getJTextFieldCodigoSeguridad(), null);
			jPanel14.setBackground(new java.awt.Color(242, 242, 238));
			jPanel14.setPreferredSize(new java.awt.Dimension(180, 50));
			jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(
				null, "Código de Seguridad: ",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getJPanel() - end");
		}
		return jPanel14;
	}

	/**
	 * This method initializes jTextFieldCodigoSeguridad
	 *
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextFieldCodigoSeguridad() {
		if (logger.isDebugEnabled()) {
			logger.debug("getjTextFieldCVV() - start");
		}

		if (jTextFieldCodigoSeguridad == null) {
			jTextFieldCodigoSeguridad = new javax.swing.JTextField();
			jTextFieldCodigoSeguridad.setPreferredSize(new java.awt.Dimension(
				130, 16));
			jTextFieldCodigoSeguridad.setInputVerifier(new TextVerifier(this
				.getDatosFormaDePagoPuntoAgil()
				.getLongitudCodigoSeguridad(), "0123456789"));
			AbstractDocument doc = (AbstractDocument) jTextFieldCodigoSeguridad
				.getDocument();
			doc.setDocumentFilter(new DocumentContentFilter(this
				.getDatosFormaDePagoPuntoAgil()
				.getLongitudCodigoSeguridad(), "0123456789"));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getjTextFieldCVV() - end");
		}
		return jTextFieldCodigoSeguridad;
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	protected JComboBox getJComboBoxBanco() {
		if (jComboBoxBanco == null) {
			jComboBoxBanco = super.getJComboBoxBanco();

			//ITEM LISTENER PARA CARGAR PROGRAMAS DE LEALTAD SEGUN BANCO
			this.jComboBoxBanco.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent _e) {
					if (_e.getStateChange() == ItemEvent.SELECTED
							&& _e.getItem() instanceof Banco) {
						ManejoPagosPuntoAgil mppa = (ManejoPagosPuntoAgil) ManejoPagosFactory
							.getInstance();
						Banco bco = (Banco) _e.getItem();

						Vector<DatosCuentasEspecialesPuntoAgil> ctasEspeciales = PantallaDatosAdicionalesPuntoAgil.this.cuentasEspeciales;
						try {
							Vector<DatosCuentasEspecialesPuntoAgil> ctasEsp;
							if (PantallaDatosAdicionalesPuntoAgil.this
								.isConsultaPuntos()) {
								ctasEsp = mppa
									.cargarCuentasEspecialesTipoConsulta(bco
										.getCodBanco());
							} else {
								ctasEsp = mppa
									.cargarCuentasEspecialesTipoTransaccion(bco
										.getCodBanco());
							}
							ctasEspeciales.clear();
							ctasEspeciales.addAll(ctasEsp);
						} catch (BaseDeDatosExcepcion e) {
							MensajesVentanas
								.mensajeError("ERROR CARGANDO CUENTAS ESPECIALES DE ESTE BANCO");
							logger.error(e.getLocalizedMessage(), e);
							return;
						}
						JComboBox comboCtas = PantallaDatosAdicionalesPuntoAgil.this
							.getJComboBoxCuentasEspeciales();
						comboCtas.removeAllItems();
						for (int i = 0; i < ctasEspeciales.size(); i++) {
							comboCtas.addItem(ctasEspeciales.elementAt(i));
						}
					}
				}
			});
		}
		return jComboBoxBanco;
	}
	
	/* (sin Javadoc)
	 * @see com.becoblohm.cr.extensiones.impl.manejarpago.PantallaDatosAdicionales#efectuarPago()
	 */
	protected void efectuarPago() {
		
	//logger.error("Valor   SW : "+PuntoAgilSubSistema.isSw());
	
		
	if (PuntoAgilSubSistema.isSw()){
		PuntoAgilSubSistema.setSw(false);
		if (logger.isDebugEnabled()) {
			logger.debug("ENTRO A EFECTUAR PAGO:");
		}
		super.efectuarPago();
		
	}else{
		if (logger.isDebugEnabled()) {
			logger.debug("Hilos en reitentos al metodo efectuarPago()");
		}
	}
	
	}
}
