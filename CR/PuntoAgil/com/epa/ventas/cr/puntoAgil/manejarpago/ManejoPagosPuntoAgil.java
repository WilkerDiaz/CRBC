/*
 * $Id: ManejoPagosPuntoAgil.java,v 1.12 2007/05/07 15:37:03 programa8 Exp $
 * ===========================================================================
 *
 * Proyecto : PuntoAgil Paquete : com.epa.ventas.cr.puntoAgil.manejarpago
 * Programa : ManejoPagosPuntoAgil.java Creado por : programa4 Creado en :
 * 25/05/2006 01:13:51 PM (C) Copyright 2006 Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo : $Name:  $ Bloqueado por : $Locker:  $ Estado de Revisión :
 * $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: ManejoPagosPuntoAgil.java,v $
 * Revision 1.12  2007/05/07 15:37:03  programa8
 * Version estable Punto Agil 2007-05-07
 *
 * Revision 1.11  2007/04/25 18:46:04  programa8
 * Version Estable Pto Agil, controlando cargos dos dobles , una sola instancia a datosExternos , control fuera de linea
 *
 * Revision 1.10  2006/09/28 19:08:25  programa4
 * Actualizacion por requerimientos credicard
 * Se cargan datos de programas de lealtad segun banco seleccionado
 * Modificados mensajes de error para hacerlos mas legibles
 * Verificados titulos para indicar version del punto agil
 *
 * Revision 1.9  2006/08/25 18:17:40  programa4
 * Correccion en anulacion de cheque (para que no solicite lectura banda)
 * Y convertido PuntoAgilSubSistema en singleton en lugar de metodos estaticos
 * Revision 1.8 2006/07/14 19:12:48
 * programa4 Agregada espera antes culminar cierre para dar un delay a la caja
 * antes de apagarse
 *
 * Revision 1.7 2006/07/13 16:07:30 programa4 Correcion de mensaje de "Punto
 * Agil" a "El Punto Agil" Manejo de autorizacion si no se utiliza punto agil No
 * se solicita autorizacion si hubo error VA (Falla de comunicacion con
 * merchant), o se trata de cheque Asignados mensajes a mostrar mientras se
 * comunica con pinpad
 *
 * Revision 1.6 2006/07/05 15:25:51 programa4 Agregado soporte a provimillas y
 * actualizado anulacion
 *
 * Revision 1.5 2006/06/27 15:51:31 programa4 Refactorizadas consultas Agregados
 * metodos sincronizacion Modificado manejo de excepciones
 *
 * Revision 1.4 2006/06/26 16:07:59 programa4 Agregada sincronizacion de datos
 * extra de pagos
 *
 * Revision 1.3 2006/06/16 21:32:35 programa4 Agregada anulacion de pagos.
 * Actualizacion de datos con datos de los pagos de ventas, tarjeta de cred. epa
 * y abonos. Agregado aviso de cierre si han pasado mas de 24 horas del ultimo
 * cierre. Agregado numServicio para poder referir a abonos Agregadas consultas
 * de puntos
 *
 * Revision 1.2 2006/06/10 02:15:57 programa4 Agregados metodos para instanciar
 * dao, y metodos de interfaz para interactuar con él
 *
 * Revision 1.1 2006/05/25 20:40:43 programa4 Primeros pasos para procesamiento
 * de cheques por Punto Agil
 *
 *
 * ===========================================================================
 */
package com.epa.ventas.cr.puntoAgil.manejarpago;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;

import ve.com.megasoft.universal.VPosUniversal;
import ve.com.megasoft.universal.error.VposUniversalException;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.BonoRegaloException;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.PagoExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.extensiones.impl.manejarpago.BaseDeDatosPago;
import com.becoblohm.cr.extensiones.impl.manejarpago.DefaultManejoPagos;
import com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.extensiones.impl.manejarpago.PantallaDatosAdicionales;
import com.becoblohm.cr.extensiones.impl.manejarpago.PantallaEliminacionPagos;
import com.becoblohm.cr.extensiones.impl.manejarpago.PantallaPagos;
import com.becoblohm.cr.gui.VentanaEspera;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarbr.OpcionBR;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.ventas.cr.puntoAgil.Constantes;
import com.epa.ventas.cr.puntoAgil.DatosCajaPuntoAgil;
import com.epa.ventas.cr.puntoAgil.DatosCuentasEspecialesPuntoAgil;
import com.epa.ventas.cr.puntoAgil.DatosFormaDePagoPuntoAgil;
import com.epa.ventas.cr.puntoAgil.DatosOperacionPuntoAgil;
import com.epa.ventas.cr.puntoAgil.DatosPlanesCreditoEPAPuntoAgil;
import com.epa.ventas.cr.puntoAgil.DatosTarjetaPuntoAgil;
import com.epa.ventas.cr.puntoAgil.DatosTipoCuentaPuntoAgil;
import com.epa.ventas.cr.puntoAgil.PuntoAgilSubSistema;

/**
 * @author programa4 - $Author: programa8 $
 * @version $Revision: 1.12 $ - $Date: 2007/05/07 15:37:03 $
 * @since 25/05/2006
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se comentó varialbe sin uso
* Fecha: agosto 2011
*/
public class ManejoPagosPuntoAgil extends DefaultManejoPagos {

//	private static final String PREPÁRESE_PARA_USAR_PUNTO_ÁGIL = "Prepárese para usar El Punto Ágil";

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
		.getLogger(ManejoPagosPuntoAgil.class);
	
	private boolean lineaPuntoAgil = true;
	
	private boolean esJuridico = false;

	
	public boolean isEsJuridico() {
		return esJuridico;
	}
	
	public void setEsJuridico(boolean juridico) {
		this.esJuridico = juridico;
	}
	
	public BaseDeDatosPago getInstanceBaseDeDatosPago() {
		if (instanceBaseDeDatosPago == null) {
			instanceBaseDeDatosPago = new BaseDeDatosPagoPuntoAgil();
		}
		return instanceBaseDeDatosPago;
	}

	/**
	 * Devuelve el dato haciendo cast a BaseDeDatosPagoPuntoAgil
	 *
	 * @return BaseDeDatosPagoPuntoAgil
	 */
	public BaseDeDatosPagoPuntoAgil getInstanceBaseDeDatosPagoPuntoAgil() {
		return (BaseDeDatosPagoPuntoAgil) getInstanceBaseDeDatosPago();
	}

	/**
	 * Carga las cuentas especiales que son de tipo consulta.
	 * @param codBanco
	 *
	 * @return Vector
	 * @throws BaseDeDatosExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<DatosCuentasEspecialesPuntoAgil> cargarCuentasEspecialesTipoConsulta(String codBanco)
			throws BaseDeDatosExcepcion {
		return this
			.getInstanceBaseDeDatosPagoPuntoAgil()
			.cargarCuentasEspecialesTipoConsulta(codBanco);
	}

	/**
	 * Carga las cuentas especiales que son de tipo consulta.
	 * @param codBanco
	 *
	 * @return Vector
	 * @throws BaseDeDatosExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<DatosCuentasEspecialesPuntoAgil> cargarCuentasEspecialesTipoTransaccion(String codBanco)
			throws BaseDeDatosExcepcion {
		return this
			.getInstanceBaseDeDatosPagoPuntoAgil()
			.cargarCuentasEspecialesTipoTransaccion(codBanco);
	}

	/**
	 * Carga las cuentas especiales que son de tipo consulta.
	 *
	 * @return Vector
	 * @throws BaseDeDatosExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<DatosPlanesCreditoEPAPuntoAgil> cargarPlanesCreditoEPA() throws BaseDeDatosExcepcion {
		return this
			.getInstanceBaseDeDatosPagoPuntoAgil()
			.cargarPlanesCreditoEPA();
	}

	/**
	 * Carga las cuentas especiales que son de tipo consulta.
	 *
	 * @return Vector
	 * @throws BaseDeDatosExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<DatosTipoCuentaPuntoAgil> cargarTiposCuenta() throws BaseDeDatosExcepcion {
		return this.getInstanceBaseDeDatosPagoPuntoAgil().cargarTiposCuenta();
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	protected PantallaPagos instancePantallaPago(double _montoMaximo,
			Vector<Pago> _pagosAnteriores, Cliente _cliente) {
		this.setLineaPuntoAgil(true);
		PantallaPagos pp = super.instancePantallaPago(
			_montoMaximo, _pagosAnteriores, _cliente);
		pp.setTitle(pp.getTitle() + Constantes.TITULO_VERSION);
		return pp;
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	protected PantallaPagos instancePantallaPago(double _montoMinimo,
			Vector<Pago> _pagosAnteriores, Cliente _cliente, Vector<FormaDePago> _formasPago) {
		this.setLineaPuntoAgil(true);
		PantallaPagos pp = super.instancePantallaPago(
			_montoMinimo, _pagosAnteriores, _cliente, _formasPago);
		pp.setTitle(pp.getTitle() + Constantes.TITULO_VERSION);
		return pp;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#instancePantallaEliminacionPago(double,
	 *      java.util.Vector, com.becoblohm.cr.manejarventa.Cliente)
	 */

	public PantallaEliminacionPagos instancePantallaEliminacionPago(
			double _montoTrans, Vector<Pago> _pagosAnteriores, Cliente _cliente) {
		return new PantallaEliminacionPagosPuntoAgil(
			_montoTrans, _pagosAnteriores, _cliente);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#instancePantallaDatosAdicionales(com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago,
	 *      double)
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se comentó variable sin uso y código muerto
	* Fecha: agosto 2011
	*/
	public PantallaDatosAdicionales instancePantallaDatosAdicionales(
			FormaDePago _formaPago, double _mto) throws PagoExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("Entrada Pantalla-Datos Adicionales 210");		
		}
		PantallaDatosAdicionales pantalla = null;
//		int usarPuntoAgil = 0;
		if (_formaPago instanceof DatosFormaDePagoPuntoAgil) {
			DatosFormaDePagoPuntoAgil fdp = (DatosFormaDePagoPuntoAgil) _formaPago;
			if (fdp.isPermitePuntoAgil()) {
//				usarPuntoAgil = MensajesVentanas.preguntarOpciones(
//						PREPÁRESE_PARA_USAR_PUNTO_ÁGIL, "Aceptar", "Cancelar",
//						0);
				//if (usarPuntoAgil == 0) {
					//Sesion.chequearLineaCaja();
					//if (PuntoAgilSubSistema.chequearLineaMerchant()) { //*** IROJAS 09-07-2009, quitar dependencia con el serv de tda
						if (fdp.isRequiereLecturaBanda()) {
							DatosTarjetaPuntoAgil datosTarjetaPuntoAgil = PuntoAgilSubSistema
									.getInstance().lecturaBanda();
							if (datosTarjetaPuntoAgil != null){
								if (!datosTarjetaPuntoAgil.getTipoTarjeta()
										.equalsIgnoreCase(
												fdp.getTipoTarjetaPuntoAgil())) {
									throw new PagoExcepcion(
											"El tipo de pago seleccionado no corresponde "
													+ "con la tarjeta seleccionada.");
								}
								if (datosTarjetaPuntoAgil != null) {
									if (fdp.getCodigo().trim().equalsIgnoreCase("13")){
										setEsJuridico(isJuridica(datosTarjetaPuntoAgil.getNumeroTarjeta()));
									}
									pantalla = new PantallaDatosAdicionalesPuntoAgil(
											fdp, datosTarjetaPuntoAgil, _mto);
									
									if (this.isEsJuridico()){
										setEsJuridico(false);
									}
									
								}
							}
													
						} else {
							pantalla = new PantallaDatosAdicionalesPuntoAgil(
									fdp, null, _mto);
						}
					/*} else {
						MensajesVentanas
								.aviso("No se puede usar el Punto Agil\n"
										+ "la caja se encuentra fuera de linea");
					}*/
				//}
			}
		}
		if (pantalla == null) {
			// POR REQUERIMIENTO REPORTADO EN CORREO DE FECHA 10/07/2006
			// SI NO SE USA EL PUNTO AGIL SE REQUIERE AUTORIZACION A MENOS
			// QUE LA FORMA DE PAGO SEA CHEQUE
			//boolean requiereAutorizacion = false;
			boolean autorizado = false;
//			if (this.isLineaPuntoAgil() && Sesion.isCajaEnLinea()
//					&& _formaPago instanceof DatosFormaDePagoPuntoAgil) {
//				DatosFormaDePagoPuntoAgil fdp = (DatosFormaDePagoPuntoAgil) _formaPago;
//				requiereAutorizacion = fdp.isPermitePuntoAgil()
//						&& fdp.isRequiereLecturaBanda();
//			}
			/*if (requiereAutorizacion && false) {
				try {
					String codautorizante = MaquinaDeEstado.autorizarFuncion(
						"PuntoAgil", "OPERACIONPTOAGIL");
					if (codautorizante != null) {
						autorizado = true;
					}
				} catch (Exception e) {
					autorizado = false;
					MensajesVentanas.mensajeError(e.getMessage());
				}
			} else {*/
				autorizado = true;
			//}
			if (autorizado) {
				pantalla = super.instancePantallaDatosAdicionales(
					_formaPago, _mto);
			} else {
				throw new PagoExcepcion(
					"No está autorizado para recibir pagos sin usar El Punto Agil");
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Entrada Pantalla-Datos Adicionales 282" + pantalla);
		}
		return pantalla;
	}

	/**
	 * Carga las cuentas especiales que son de tipo consulta.
	 *
	 * @param dopa
	 *
	 * @return boolean
	 * @throws BaseDeDatosExcepcion
	 */
	public boolean insertarDatosOperacionPuntoAgil(DatosOperacionPuntoAgil dopa)
			throws BaseDeDatosExcepcion {
		return this
			.getInstanceBaseDeDatosPagoPuntoAgil()
			.insertarDatosOperacionPuntoAgil(dopa);
	}

	/**
	 * Carga una operacion de punto agil guardada en la base de datos.
	 *
	 * @param vtId
	 * @param numSeq
	 *
	 * @return DatosOperacionPuntoAgil
	 * @throws BaseDeDatosExcepcion
	 */
	public DatosOperacionPuntoAgil cargarOperacionPuntoAgil(String vtId,
			int numSeq) throws BaseDeDatosExcepcion {
		return this
			.getInstanceBaseDeDatosPagoPuntoAgil()
			.cargarOperacionPuntoAgil(vtId, numSeq);
	}

	/**
	 * Actualiza una operacion de punto agil previamente guardada en la base de
	 * datos, en una sola transaccion.
	 *
	 * @param _dopa
	 * @return true si pudo actualizar
	 * @throws BaseDeDatosExcepcion
	 */
	public boolean actualizarOperacionPuntoAgil(DatosOperacionPuntoAgil _dopa)
			throws BaseDeDatosExcepcion {
		return this
			.getInstanceBaseDeDatosPagoPuntoAgil()
			.actualizarOperacionPuntoAgil(_dopa);
	}

	/**
	 * Obtiene el tipo de proceso que puede vincularse a la transaccion según el
	 * estado de la caja.
	 *
	 * @param idestado
	 *            estado de la caja
	 *
	 * @return tipo de proceso
	 * @throws BaseDeDatosExcepcion
	 */
	public Integer obtenerTipoProcesoSegunEstadoCaja(String idestado)
			throws BaseDeDatosExcepcion {
		return this
			.getInstanceBaseDeDatosPagoPuntoAgil()
			.obtenerTipoProcesoSegunEstadoCaja(idestado);
	}

	/**
	 * Devuelve el numero de secuencia más alto para el vtId dado, obteniendo el
	 * mayor de la tabla de operaciones de punto agil.
	 *
	 * @param vtId
	 * @return int
	 * @throws BaseDeDatosExcepcion
	 */
	public int obtenerMaximoNumSeq(String vtId) throws BaseDeDatosExcepcion {
		int maxLocal = this
			.getInstanceBaseDeDatosPagoPuntoAgil()
			.obtenerMaximoNumSeq(vtId, true);
		
		//***** IROJAS 09-07-2009.
		//***** Cambio realizado para quitar la dependencia de MERCHANT con el serv de tienda
		//***** Se captura le excepcion de conexion para que no siga intentado conectarse
		int maxServer = -1;
		try {
			maxServer = this
			.getInstanceBaseDeDatosPagoPuntoAgil()
			.obtenerMaximoNumSeq(vtId, false);
		} catch (BaseDeDatosExcepcion e) {
			if (logger.isDebugEnabled()) {
				logger.debug("Error de conexion con la BD de Tda.No se obtuvo el maxnumseq de ServTda");
			}
		}
		return Math.max(maxLocal, maxServer);

	}

	/**
	 * Obtiene informacion de la caja con respecto al punto agil
	 *
	 * @return DatosCajaPuntoAgil
	 * @throws BaseDeDatosExcepcion
	 */
	public DatosCajaPuntoAgil cargarDatosCajaPuntoAgil()
			throws BaseDeDatosExcepcion {
		return this
			.getInstanceBaseDeDatosPagoPuntoAgil()
			.cargarDatosCajaPuntoAgil();
	}

	/**
	 * Intenta actualizar los datos de la caja con respecto a punto agil, si no
	 * logra actualizar, intenta insertar.
	 *
	 * @param datosCaja
	 *            Datos de caja a actualizar o insertar.
	 * @return boolean
	 * @throws BaseDeDatosExcepcion
	 */
	public boolean actualizarInsertarDatosCajaPuntoAgil(
			DatosCajaPuntoAgil datosCaja) throws BaseDeDatosExcepcion {
		boolean salida = this
			.getInstanceBaseDeDatosPagoPuntoAgil()
			.actualizarDatosCajaPuntoAgil(datosCaja);
		if (!salida) {
			salida = this
				.getInstanceBaseDeDatosPagoPuntoAgil()
				.insertarDatosCajaPuntoAgil(datosCaja);
		}
		return salida;
	}

	public void sincronizarDatosExtraPagos() throws BaseDeDatosExcepcion {
		super.sincronizarDatosExtraPagos();
		SincronizadorPuntoAgil.getInstance().sincronizarOperaciones();
		SincronizadorPuntoAgil.getInstance().sincronizarPuntoAgilCaja();
	}

	public void sincronizarDatosMaestroPagos() throws BaseDeDatosExcepcion {
		super.sincronizarDatosMaestroPagos();
		SincronizadorPuntoAgil.getInstance().sincronizarMaestros();
	}

	public void efectuarCierre() {
		super.efectuarCierre();
		PuntoAgilSubSistema.getInstance().cierre();
		while (Sesion.crFiscalPrinterOperations.isImprimiendo()) {
			// ESPERA DE UN MINUTO MIENTRAS SE EFECTUA REPORTE DE CIERRE
			VentanaEspera.esperar(1000, "ESPERE MIENTRAS SE IMPRIME "
					+ SystemUtils.LINE_SEPARATOR
					+ "REPORTE DE CIERRE DE EL PUNTO AGIL");
		}
	}

	/**
	 * @return Returns el lineaPuntoAgil.
	 */
	public boolean isLineaPuntoAgil() {
		return this.lineaPuntoAgil;
	}

	/**
	 * @param _lineaPuntoAgil
	 *            El lineaPuntoAgil a asignar.
	 */
	public void setLineaPuntoAgil(boolean _lineaPuntoAgil) {
		this.lineaPuntoAgil = _lineaPuntoAgil;
	}
	
	public boolean isJuridica (String numTarjeta){
		
		boolean result = false;
		String nTarjeta =  StringUtils.deleteWhitespace(numTarjeta);
		
		nTarjeta = nTarjeta.substring(6,8);		
		int numtarjeta = Integer.parseInt(nTarjeta);

		if (numtarjeta >= Constantes.TCEPA_NUMER0){
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Obtiene el pago mediante comunicación con VPosUniversal
	 * @throws ExcepcionCr 
	 * @throws ConexionExcepcion 
	 * @throws MaquinaDeEstadoExcepcion 
	 * @throws UsuarioExcepcion 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se comentó código muerto
	* Fecha: agosto 2011
	*/
	public Pago obtenerPagoOperacion(FormaDePago _formaPago, double _mto, String cedula) throws PagoExcepcion, VposUniversalException{
		Pago pago = null;
		
		if (logger.isDebugEnabled()) {
			logger.debug("Entrada Pantalla-Datos Adicionales 210");		
		}
		
		
		/**
		 * CENTROBECO, C.A.
		 * BONO REGALO ELECTRONICO
		 * Si la forma de pago es bono regalo electronico, habilitar solo opcion de compra
		 * COMENTADO POR SEPARACION DE METODOS DE CONSULTAS Y COMPRAS /
		if(_formaPago.getCodigo().equalsIgnoreCase(Sesion.FORMA_PAGO_BONOREGALO_E)){
			try {
				CR.meServ.configurarVPosCards("PAGO", "OBTENERPAGOOPERACION");
			} catch (UsuarioExcepcion e) {
				throw new PagoExcepcion(e.getMensaje());
			} catch (MaquinaDeEstadoExcepcion e) {
				throw new PagoExcepcion(e.getMensaje());
			} catch (ConexionExcepcion e) {
				throw new PagoExcepcion(e.getMensaje());
			} catch (ExcepcionCr e) {
				throw new PagoExcepcion(e.getMensaje());
			}
			
		}
		*/
		
		
		PantallaDatosAdicionales pantalla = null;
		if (_formaPago instanceof DatosFormaDePagoPuntoAgil) {
			DatosFormaDePagoPuntoAgil fdp = (DatosFormaDePagoPuntoAgil) _formaPago;
			if (fdp.isPermitePuntoAgil()) {
				if (fdp.isRequiereLecturaBanda()) {

					/*DatosTarjetaPuntoAgil datosTarjetaPuntoAgil = PuntoAgilSubSistema
							.getInstance().lecturaBanda();
					if (datosTarjetaPuntoAgil != null){
						if (!datosTarjetaPuntoAgil.getTipoTarjeta()
								.equalsIgnoreCase(
										fdp.getTipoTarjetaPuntoAgil())) {
							throw new PagoExcepcion(
									"El tipo de pago seleccionado no corresponde "
											+ "con la tarjeta seleccionada.");
						}
						if (datosTarjetaPuntoAgil != null) {
							if (fdp.getCodigo().trim().equalsIgnoreCase("13")){
								setEsJuridico(isJuridica(datosTarjetaPuntoAgil.getNumeroTarjeta()));
							}
							pantalla = new PantallaDatosAdicionalesPuntoAgil(
									fdp, datosTarjetaPuntoAgil, _mto);
							
							if (this.isEsJuridico()){
								setEsJuridico(false);
							}
							
						}
					}*/
					if(MensajesVentanas.preguntarOpcionesTimeOut(
						"Preparese para deslizar Tarjeta", "Aceptar",
						"Cancelar", 0, 5000)==0){
						try {
							//OBTENEMOS VPOSUNIVERSAL Y LLAMAMOS AL MÉTODO DE TARJETAS
							pago = PuntoAgilSubSistema.getInstance().tarjetas(_formaPago, _mto, cedula);
						} finally {
							MensajesVentanas.iniciarVerificadorFoco();
						}
					} else {
						pantalla =null;
					}						
				} else {
					pantalla = new PantallaDatosAdicionalesPuntoAgil(
							fdp, null, _mto);
				}
			}
		}
		if (pantalla == null && pago==null) {
			// POR REQUERIMIENTO REPORTADO EN CORREO DE FECHA 10/07/2006
			// SI NO SE USA EL PUNTO AGIL SE REQUIERE AUTORIZACION A MENOS
			// QUE LA FORMA DE PAGO SEA CHEQUE
			//boolean requiereAutorizacion = false;
			boolean autorizado = false;
			/*if (requiereAutorizacion && false) {
				try {
					String codautorizante = MaquinaDeEstado.autorizarFuncion(
						"PuntoAgil", "OPERACIONPTOAGIL");
					if (codautorizante != null) {
						autorizado = true;
					}
				} catch (Exception e) {
					autorizado = false;
					MensajesVentanas.mensajeError(e.getMessage());
				}
			} else {*/
				autorizado = true;
			//}
			if (autorizado) {
				pantalla = super.instancePantallaDatosAdicionales(
					_formaPago, _mto);
				MensajesVentanas.centrarVentanaDialogo(pantalla);
			} else {
				throw new PagoExcepcion(
					"No está autorizado para recibir pagos sin usar El Punto Agil");
			}
		}
		
		if(pago==null){
			pago = pantalla.obtenerDatosAdicionales();
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("Entrada Pantalla-Datos Adicionales 282" + pantalla);
		}
		
		return pago;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' y se comentó código muerto
	* Fecha: agosto 2011
	*/
	public void configurarOpcionesCards(Vector<OpcionBR> opcionesActivas) throws ExcepcionCr {
		BufferedReader archivoEdicion = null;
		FileInputStream farchivo = null;
		
		// Obtenemos las opciones activas para el modulo seleccionado
		//Vector opcionesActivas = obtenerOpciones(idModulo);
		
		if (opcionesActivas.isEmpty()) {
			throw new ExcepcionCr("Opciones no encontradas para el módulo seleccionado");
		} else {
			// Apertura de archivo de configuracion vposuniversal.ini
			try {
				farchivo = new FileInputStream(Constantes.RUTA_CONF_VPOS + "vposuniversal.ini");
				archivoEdicion = new BufferedReader(new InputStreamReader(farchivo));
			} catch (Exception e1) {
				throw new ExcepcionCr("Archivo de configuracion vposuniversal.ini no encontrado", e1);
			}
	
			// Búsqueda de línea de configuración [CARDS]
			String linea;
			StringBuffer salida = new StringBuffer();
			if (archivoEdicion != null) {
				
				try {
					while((linea = archivoEdicion.readLine()) != null){
						salida.append(linea + "\n");
						if (linea.equalsIgnoreCase("[GIFTCARDS-CONSULTA]")) {
							// Realizamos la lectura de las próximas 2 líneas
							linea = archivoEdicion.readLine();
							salida.append(linea + "\n");
							linea = archivoEdicion.readLine();
							break;
						}
					}
					
					
					salida.append("nroproducto=" + opcionesActivas.size() + "\n");
					
					// Por cada opcion Activa ingresamos las líneas de configuracion
					for (int i=0; i<opcionesActivas.size(); i++) {
						OpcionBR opcionActual = (OpcionBR)opcionesActivas.elementAt(i);
						salida.append("*-----------" + "\n");
						salida.append("tipo" + i + "=" + opcionActual.getNombreOpcion() + "\n");
						salida.append("imagen" + i + "=" + opcionActual.getRutaImagen() + "\n");
						salida.append("tecla" + i + "=" + opcionActual.getTecla() + "\n");
					}
					
					// Agregamos el resto del archivo de configuracion vPos
					boolean realizarEscritura = false;
					while((linea = archivoEdicion.readLine()) != null){
						if (realizarEscritura) {
							salida.append(linea + "\n");
						} else {
							if (linea.equalsIgnoreCase("[CARDS-PIN]")) {
								realizarEscritura = true;
								salida.append("\n*--------------------------------------------------------------*\n");
								salida.append("*  Productos CARDS para el manejo de PIN\n");
								salida.append("*--------------------------------------------------------------*\n");
								salida.append(linea + "\n");
							}
						}
					}
					
					archivoEdicion.close();
				} catch (Exception e2) {
					throw new ExcepcionCr("Error leyendo archivo de configuracion vposuniversal.ini", e2);
				} finally {
					try{archivoEdicion.close();}catch(Exception e){}
				}

				BufferedWriter out = null;
				try{
					// Copiamos el contenido de "salida" a archivo de configuracion
					FileOutputStream farchivoOut = new FileOutputStream(Constantes.RUTA_CONF_VPOS + "vposuniversal.ini");
					out = new BufferedWriter(new OutputStreamWriter(farchivoOut));
					out.write(salida.toString());
				} catch (Exception e1) {
					throw new ExcepcionCr("No se pudo escribir sobr archivo de configuracion vposuniversal.ini", e1);
				} finally {
					try{out.close();}catch(Exception e){}
				}
				
			}/* else {
				throw new ExcepcionCr("Problema en la lectura de archivo de configuracion vposuniversal.ini");
			}*/
		}
	}
	
	/**
	 * Consulta de saldo de tarjetas de bonos regalo
	 */
	public void consultaSaldoCards() throws BonoRegaloException{
		if(MensajesVentanas.preguntarOpcionesTimeOut(
			"Preparese para deslizar Tarjeta", "Aceptar",
			"Cancelar", 0, 5000)==0){
			try {
				PuntoAgilSubSistema.getInstance().obtenerOperacionConsultaCards();
			} finally {
				MensajesVentanas.iniciarVerificadorFoco();
			}
		}
	}
	
	/**
	 * Consulta de saldo de tarjetas de bonos regalo
	 * @return Vector: 	Primera casilla número de tarjeta recargada
	 * 					Segunda casilla número de secuencia de la operacion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Object> cargaRecargaSaldoCards(double monto, String cedula) throws BonoRegaloException {
		//TODO Gabriel Llamada a pantalla de datos de recarga / llamada a vPos
		Vector<Object> resultado = new Vector<Object>();
		VPosUniversal vPosUniversal = null;
		DecimalFormat df = new DecimalFormat("#,##0.00");
		String montoFormateado = df.format(monto);
		if(MensajesVentanas.preguntarOpcionesTimeOut(
				"Preparese para recargar tarjeta por BsF. "+montoFormateado, "Aceptar",
				"Cancelar", 0, 5000)!=1){
			try {
				vPosUniversal = PuntoAgilSubSistema.getInstance().obtenerOperacionRecargaSaldoCards(montoFormateado, cedula);
				if(vPosUniversal.getNumSeq()!=0 && vPosUniversal.getCodRespuesta().equalsIgnoreCase("00")){
					resultado.add(vPosUniversal.getNumeroTarjeta());
					resultado.add(new Integer(vPosUniversal.getNumSeq()));
				} else {
					throw new BonoRegaloException("Transaccion fallida "+vPosUniversal.getCodRespuesta());
				}
			} finally {
				MensajesVentanas.iniciarVerificadorFoco();
			}
		}
		return resultado;
	}
	
	
	/**
	 * Anula la transaccion de CARDs realizada con el numero de secuencia indicado sobre la tarjeta dada
	 * @param codTarjeta
	 * @param numSeq
	 * @return
	 * @throws BonoRegaloException
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se eliminó variable sin uso
	* Fecha: agosto 2011
	*/
	public int procesarAnulacionTarjeta(String codTarjeta, int numSeq) throws BonoRegaloException{
		int resultado = 0;
		VPosUniversal vPosUniversal = null;
		if(MensajesVentanas.preguntarOpcionesTimeOut(
				"Preparese para deslizar tarjeta: "+codTarjeta, "Aceptar",
				"Cancelar", 0, 5000)!=1){
			try {
				vPosUniversal = PuntoAgilSubSistema.getInstance().anulacionTransaccionBR(numSeq);
				resultado = vPosUniversal.getNumSeq();
			} finally {
				MensajesVentanas.iniciarVerificadorFoco();
			}
		}
		
		return resultado;
	}
}
