/*
 * $Id: BaseDeDatosPagoPuntoAgil.java,v 1.8 2007/04/25 18:46:04 programa8 Exp $
 * ===========================================================================
 *
 * Proyecto		: PuntoAgil
 * Paquete		: com.epa.ventas.cr.puntoAgil.manejarpago
 * Programa		: BaseDeDatosPagoPuntoAgil.java
 * Creado por	: programa4
 * Creado en 	: 05/06/2006 11:56:59 AM
 * (C) Copyright 2006 Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisión	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: BaseDeDatosPagoPuntoAgil.java,v $
 * Revision 1.8  2007/04/25 18:46:04  programa8
 * Version Estable Pto Agil, controlando cargos dos dobles , una sola instancia a datosExternos , control fuera de linea
 *
 * Revision 1.7  2006/09/28 19:08:23  programa4
 * Actualizacion por requerimientos credicard
 * Se cargan datos de programas de lealtad segun banco seleccionado
 * Modificados mensajes de error para hacerlos mas legibles
 * Verificados titulos para indicar version del punto agil
 *
 * Revision 1.6  2006/07/13 16:05:15  programa4
 * Correcion de mensaje de "Punto Agil" a "El Punto Agil"
 *
 * Revision 1.5  2006/07/05 15:25:50  programa4
 * Agregado soporte a provimillas y actualizado anulacion
 *
 * Revision 1.4  2006/06/27 15:51:30  programa4
 * Refactorizadas consultas
 * Agregados metodos sincronizacion
 * Modificado manejo de excepciones
 *
 * Revision 1.3  2006/06/26 16:07:59  programa4
 * Agregada sincronizacion de datos extra de pagos
 *
 * Revision 1.2  2006/06/16 21:32:33  programa4
 * Agregada anulacion de pagos.
 * Actualizacion de datos con datos de los pagos de ventas, tarjeta de cred. epa
 * y abonos.
 * Agregado aviso de cierre si han pasado mas de 24 horas del ultimo cierre.
 * Agregado numServicio para poder referir a abonos
 * Agregadas consultas de puntos
 *
 * Revision 1.1  2006/06/10 02:15:13  programa4
 * DAO para intercambiar datos con la base de datos. Consulta de tablas maestras., Almacenar operaciones, Etc.
 *
 *
 * ===========================================================================
 */
/**
 * Clase BaseDeDatosPagoPuntoAgil
 *
 * @author programa4 - $Author: programa8 $
 * @version $Revision: 1.8 $ - $Date: 2007/04/25 18:46:04 $
 * @since 05/06/2006
 */
package com.epa.ventas.cr.puntoAgil.manejarpago;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.extensiones.impl.manejarpago.BaseDeDatosPago;
import com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarservicio.Abono;
import com.becoblohm.cr.manejarventa.Venta;
import com.becoblohm.cr.mediadoresbd.Conexiones;
import com.becoblohm.cr.utiles.MathUtil;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.ventas.cr.puntoAgil.Constantes;
import com.epa.ventas.cr.puntoAgil.DatosCajaPuntoAgil;
import com.epa.ventas.cr.puntoAgil.DatosCuentasEspecialesPuntoAgil;
import com.epa.ventas.cr.puntoAgil.DatosFormaDePagoPuntoAgil;
import com.epa.ventas.cr.puntoAgil.DatosOperacionPuntoAgil;
import com.epa.ventas.cr.puntoAgil.DatosPagoPuntoAgil;
import com.epa.ventas.cr.puntoAgil.DatosPlanesCreditoEPAPuntoAgil;
import com.epa.ventas.cr.puntoAgil.DatosTipoCuentaPuntoAgil;

/**
 * @author programa4 - $Author: programa8 $
 * @version $Revision: 1.8 $ - $Date: 2007/04/25 18:46:04 $
 * @since 05/06/2006
 */
class BaseDeDatosPagoPuntoAgil extends BaseDeDatosPago {

	private static final String TIPO_CONSULTA = "C";

	private static final String TIPO_TRANSACCION = "T";

	private static final String SQL_CUENTAS_ESPECIALES = "SELECT pace.* FROM CR.puntoAgilCuentasEspeciales pace WHERE pace.`regvigente` = 'S' AND (pace.`codbanco` IS NULL or pace.`codbanco` = ?)";

	private static final String CAMPOS_DATOS_OPERACION = "numtienda, numcaja, vtId, numSeq, tipoProceso, codformadepago, numproceso, numservicio, correlativoPagoProceso, codCajero, fecha, horaInicia, horaFinaliza, de_cedulaCliente, de_monto, de_tipoCuenta, de_ctaEspecial, de_nroCuenta, de_nroCheque, de_numSeqAnular, de_planCreditoEPA, de_porcentajeProvimillas, dt_numeroTarjeta, dt_nombreCliente, dt_TipoTarjeta, do_codRespuesta, do_mensajeRespuesta, do_mensajeError, do_nombreAutorizador, do_numeroAutorizacion, do_nombreVoucher, status, tipoOperacion, regActualizado";

	private static final String SQL_INSERT_DATOS_OPERACION = "insert into CR.puntoAgilOperacion ("
			+ CAMPOS_DATOS_OPERACION
			+ ") values "
			+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_DATOS_OPERACION = "select "
			+ CAMPOS_DATOS_OPERACION + " from CR.puntoAgilOperacion";

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
		.getLogger(BaseDeDatosPagoPuntoAgil.class);

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	protected Vector<DatosCuentasEspecialesPuntoAgil> cargarCuentasEspecialesTipoConsulta(String codBanco)
			throws BaseDeDatosExcepcion {
		return cargarCuentasEspecialesTipo(TIPO_CONSULTA, codBanco);
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	protected Vector<DatosCuentasEspecialesPuntoAgil> cargarCuentasEspecialesTipoTransaccion(String codBanco)
			throws BaseDeDatosExcepcion {
		return cargarCuentasEspecialesTipo(TIPO_TRANSACCION, codBanco);
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private Vector<DatosCuentasEspecialesPuntoAgil> cargarCuentasEspecialesTipo(String tipo, String codBanco)
			throws BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarCuentasEspecialesTipo(String, int) - start");
		}

		String consultaCuentasEspeciales = SQL_CUENTAS_ESPECIALES
				+ " AND tipo = '" + tipo + "'";
		Vector<DatosCuentasEspecialesPuntoAgil> cuentasEspeciales = ejecutarConsultaCuentasEspeciales(
			consultaCuentasEspeciales, codBanco);

		if (logger.isDebugEnabled()) {
			logger.debug("cargarCuentasEspecialesTipo(String, int) - end");
		}
		return cuentasEspeciales;
	}

	/**
	 * @param sql
	 * @param codBanco
	 * @return Vector
	 * @throws BaseDeDatosExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private Vector<DatosCuentasEspecialesPuntoAgil> ejecutarConsultaCuentasEspeciales(String sql, String codBanco)
			throws BaseDeDatosExcepcion {
		Vector<DatosCuentasEspecialesPuntoAgil> cuentasEspeciales = new Vector<DatosCuentasEspecialesPuntoAgil>();
		PreparedStatement pstmtCuentasEspeciales = null;
		ResultSet rsCuentasEspeciales = null;
		try {
			pstmtCuentasEspeciales = Conexiones.prepararSentencia(
				sql, true, false);
			pstmtCuentasEspeciales.setString(1, codBanco);
			rsCuentasEspeciales = pstmtCuentasEspeciales.executeQuery();
			rsCuentasEspeciales.beforeFirst();
			while (rsCuentasEspeciales.next()) {
				DatosCuentasEspecialesPuntoAgil datosCuentasEspecialesPuntoAgil = new DatosCuentasEspecialesPuntoAgil();
				datosCuentasEspecialesPuntoAgil
					.setIdPuntoAgilCuentasEspeciales(rsCuentasEspeciales
						.getLong("idPuntoAgilCuentasEspeciales"));
				datosCuentasEspecialesPuntoAgil
					.setDescripcion(rsCuentasEspeciales
						.getString("descripcion"));
				datosCuentasEspecialesPuntoAgil
					.setCuentaEspecial(rsCuentasEspeciales
						.getString("cuentaEspecial"));
				datosCuentasEspecialesPuntoAgil.setTipo(rsCuentasEspeciales
					.getString("tipo"));
				cuentasEspeciales.add(datosCuentasEspecialesPuntoAgil);
			}
		} catch (SQLException e) {
			throw new BaseDeDatosExcepcion(
				"Error consultando Cuentas Especiales", e);
		} catch (ConexionExcepcion e) {
			throw new BaseDeDatosExcepcion(
				"Error consultando Cuentas Especiales", e);
		} finally {
			if (rsCuentasEspeciales != null) {
				try {
					rsCuentasEspeciales.close();
				} catch (SQLException e) {
					logger
						.error("ejecutarConsultaCuentasEspeciales(String)", e);
				}
				rsCuentasEspeciales = null;
			}
			if (pstmtCuentasEspeciales != null) {
				try {
					pstmtCuentasEspeciales.close();
				} catch (SQLException e) {
					logger
						.error("ejecutarConsultaCuentasEspeciales(String)", e);
				}
				pstmtCuentasEspeciales = null;
			}
		}
		return cuentasEspeciales;
	}

	protected FormaDePago resultSet2FormaPago(ResultSet _rsPagos)
			throws BaseDeDatosExcepcion {
		FormaDePago fdp = super.resultSet2FormaPago(_rsPagos);
		DatosFormaDePagoPuntoAgil formadePago = cargarDatosFormaDePagoPuntoAgil(fdp);
		return formadePago;
	}

	/**
	 * Método cargarFormaDePago
	 *
	 * @param fdp
	 * @return FormaDePago
	 * @throws BaseDeDatosExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se comentó variable sin uso
	* Fecha: agosto 2011
	*/
	private DatosFormaDePagoPuntoAgil cargarDatosFormaDePagoPuntoAgil(
			FormaDePago fdp) throws BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarDatosFormaDePagoPuntoAgil(String) - start");
		}
		if (fdp == null) {
			return null;
		}

		String sql = "select * from CR.puntoAgilFormadePago where codformadepago='"
				+ fdp.getCodigo() + "'";
		ResultSet rsPagos = null;
		try {
			rsPagos = Conexiones.realizarConsulta(sql, true);
			if (rsPagos.first()) {
				DatosFormaDePagoPuntoAgil formaDePago = resultSet2DatosFormaDePagoPuntoAgil(
					rsPagos, fdp);
				if (logger.isDebugEnabled()) {
//					int k=0;
					logger
						.debug("cargarDatosFormaDePagoPuntoAgil(String) - end");
				}
				return formaDePago;
			} else {
				if (logger.isDebugEnabled()) {
					logger
						.debug("cargarDatosFormaDePagoPuntoAgil(String) - end");
				}
				return null;
			}
		} catch (SQLException e) {
			throw new BaseDeDatosExcepcion(
				"Error consultando Datos Formas de Pago para Punto Agil", e);
		} catch (ConexionExcepcion e) {
			throw new BaseDeDatosExcepcion(
				"Error consultando Datos Formas de Pago para Punto Agil", e);
		} finally {
			if (rsPagos != null) {
				try {
					rsPagos.close();
				} catch (SQLException e) {
					logger.error("cargarDatosFormaDePagoPuntoAgil(String)", e);
				}
				rsPagos = null;
			}
		}
	}

	private DatosFormaDePagoPuntoAgil resultSet2DatosFormaDePagoPuntoAgil(
			ResultSet _rsPagos, FormaDePago fdp) throws SQLException {
		DatosFormaDePagoPuntoAgil formaPago = new DatosFormaDePagoPuntoAgil(fdp);
		boolean permitePuntoAgil = (_rsPagos
			.getString("permitePuntoAgil")
			.toCharArray()[0] == Sesion.SI) ? true : false;
		boolean requiereLecturaBanda = (_rsPagos.getString(
			"requiereLecturaBanda").toCharArray()[0] == Sesion.SI) ? true
				: false;
		boolean imprimirVoucher = (_rsPagos
			.getString("imprimirVoucher")
			.toCharArray()[0] == Sesion.SI) ? true : false;
		boolean indicarPlan = (_rsPagos.getString("indicarPlan").toCharArray()[0] == Sesion.SI) ? true
				: false;
		boolean indicarCuentaEspecial = (_rsPagos.getString(
			"indicarCuentaEspecial").toCharArray()[0] == Sesion.SI) ? true
				: false;
		boolean indicarTipoCuenta = (_rsPagos
			.getString("indicarTipoCuenta")
			.toCharArray()[0] == Sesion.SI) ? true : false;

		int longitudCodigoSeguridad = _rsPagos
			.getInt("longitudCodigoSeguridad");

		String tipoTarjetaPuntoAgil = _rsPagos
			.getString("tipoTarjetaPuntoAgil");

		formaPago.setTipoTarjetaPuntoAgil(tipoTarjetaPuntoAgil);
		formaPago.setImprimirVoucher(imprimirVoucher);
		formaPago.setIndicarCuentaEspecial(indicarCuentaEspecial);
		formaPago.setIndicarTipoCuenta(indicarTipoCuenta);
		formaPago.setIndicarPlan(indicarPlan);
		formaPago.setPermitePuntoAgil(permitePuntoAgil);
		formaPago.setRequiereLecturaBanda(requiereLecturaBanda);
		formaPago.setLongitudCodigoSeguridad(longitudCodigoSeguridad);
		return formaPago;
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	protected Vector<DatosTipoCuentaPuntoAgil> cargarTiposCuenta() throws BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarTiposCuenta(String) - start");
		}

		String sql = "SELECT * FROM CR.puntoAgilTipoCuenta WHERE regvigente = 'S'";
		Vector<DatosTipoCuentaPuntoAgil> tiposDeCuenta = new Vector<DatosTipoCuentaPuntoAgil>();
		ResultSet rsTiposCuenta = null;
		try {
			rsTiposCuenta = Conexiones.realizarConsulta(sql, true);
			rsTiposCuenta.beforeFirst();
			while (rsTiposCuenta.next()) {
				DatosTipoCuentaPuntoAgil datosTipoCuentaPuntoAgil = resultSet2DatosTipoCuentaPuntoAgil(rsTiposCuenta);
				tiposDeCuenta.add(datosTipoCuentaPuntoAgil);
			}
		} catch (SQLException e) {
			throw new BaseDeDatosExcepcion(
				"Error consultando Datos Tipo de Cuenta para Punto Agil", e);
		} catch (ConexionExcepcion e) {
			throw new BaseDeDatosExcepcion(
				"Error consultando Datos Tipo de Cuenta para Punto Agil", e);
		} finally {
			if (rsTiposCuenta != null) {
				try {
					rsTiposCuenta.close();
				} catch (SQLException e) {
					logger.error("cargarTiposCuenta(String)", e);
				}
				rsTiposCuenta = null;
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("cargarTiposCuenta() - end");
		}
		return tiposDeCuenta;
	}

	private DatosTipoCuentaPuntoAgil resultSet2DatosTipoCuentaPuntoAgil(
			ResultSet _rsTipoCuenta) throws SQLException {
		DatosTipoCuentaPuntoAgil datosTipoCuenta = new DatosTipoCuentaPuntoAgil();

		int idPuntoAgilTipoCuenta = _rsTipoCuenta
			.getInt("idPuntoAgilTipoCuenta");
		String tipoCuentaPuntoAgil = _rsTipoCuenta
			.getString("tipoCuentaPuntoAgil");
		String tipoCuentaDescripcion = _rsTipoCuenta.getString("descripcion");
		String abrTipoCuenta = _rsTipoCuenta.getString("abrTipoCuenta");
		datosTipoCuenta.setAbrTipoCuenta(abrTipoCuenta);
		datosTipoCuenta.setIdPuntoAgilTipoCuenta(idPuntoAgilTipoCuenta);
		datosTipoCuenta.setDescripcion(tipoCuentaDescripcion);
		datosTipoCuenta.setTipoCuentaPuntoAgil(tipoCuentaPuntoAgil);
		return datosTipoCuenta;
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	protected Vector<DatosPlanesCreditoEPAPuntoAgil> cargarPlanesCreditoEPA() throws BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarPlanesCreditoEPA(String) - start");
		}

		String sql = "SELECT * FROM CR.puntoAgilPlanCreditoEPA WHERE regvigente = 'S'";
		Vector<DatosPlanesCreditoEPAPuntoAgil> planesCreditoEPA = new Vector<DatosPlanesCreditoEPAPuntoAgil>();
		ResultSet rsPlanesCreditoEPA = null;
		try {
			rsPlanesCreditoEPA = Conexiones.realizarConsulta(sql, true);
			rsPlanesCreditoEPA.beforeFirst();
			while (rsPlanesCreditoEPA.next()) {
				DatosPlanesCreditoEPAPuntoAgil datosPlanesCreditoEPAPuntoAgil = resultSet2DatosPlanCreditoEPAPuntoAgil(rsPlanesCreditoEPA);
				planesCreditoEPA.add(datosPlanesCreditoEPAPuntoAgil);
			}
		} catch (SQLException e) {
			throw new BaseDeDatosExcepcion(
				"Error consultando Datos Planes Credito EPA para Punto Agil", e);
		} catch (ConexionExcepcion e) {
			throw new BaseDeDatosExcepcion(
				"Error consultando Datos Planes Credito EPA para Punto Agil", e);
		} finally {
			if (rsPlanesCreditoEPA != null) {
				try {
					rsPlanesCreditoEPA.close();
				} catch (SQLException e) {
					logger.error("cargarPlanesCreditoEPA(String)", e);
				}
				rsPlanesCreditoEPA = null;
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("cargarPlanesCreditoEPA() - end");
		}
		return planesCreditoEPA;
	}

	private DatosPlanesCreditoEPAPuntoAgil resultSet2DatosPlanCreditoEPAPuntoAgil(
			ResultSet _rsPlanCreditoEPA) throws SQLException {
		DatosPlanesCreditoEPAPuntoAgil datosPlanCreditoEPA = new DatosPlanesCreditoEPAPuntoAgil();

		int idPuntoAgilPlanCreditoEPA = _rsPlanCreditoEPA
			.getInt("idPuntoAgilPlanCreditoEPA");
		String planCredito = _rsPlanCreditoEPA.getString("planCredito");
		String descripcion = _rsPlanCreditoEPA.getString("descripcion");
		String requiereDatoAdicional = _rsPlanCreditoEPA
			.getString("requiereDatoAdicional");
		String abrPlanCredito = _rsPlanCreditoEPA.getString("abrPlanCredito");

		datosPlanCreditoEPA.setAbrPlanCredito(abrPlanCredito);
		datosPlanCreditoEPA.setDescripcion(descripcion);
		datosPlanCreditoEPA
			.setIdPuntoAgilPlanCreditoEPA(idPuntoAgilPlanCreditoEPA);
		datosPlanCreditoEPA.setPlanCredito(planCredito);
		datosPlanCreditoEPA.setRequiereDatoAdicional(requiereDatoAdicional);
		return datosPlanCreditoEPA;
	}

	protected boolean insertarDatosOperacionPuntoAgil(
			DatosOperacionPuntoAgil dopa) throws BaseDeDatosExcepcion {
		int[] salida = { 0 };
		PreparedStatement ps = null;
		boolean local = true;
		boolean sync = false;
		try {
			try {
				Conexiones.iniciarTransaccion(local, sync);
				ps = Conexiones.prepararSentencia(
					SQL_INSERT_DATOS_OPERACION, local, sync);
				insertarDatosOperacionPuntoAgilAsignarParametros(dopa, ps);
				salida = ps.executeBatch();
				Conexiones.commitTransaccion(local, sync);
			
			} catch (SQLException e) {
				Conexiones.rollbackTransaccion(local, sync);
				MensajesVentanas
					.mensajeError("ERROR INSERTANDO OPERACION EL PUNTO AGIL EN BASE DE DATOS: "
							+ SystemUtils.LINE_SEPARATOR + e);
				throw new BaseDeDatosExcepcion(
					"Error guardando resultados operacion El Punto Agil", e);
			}
		} catch (ConexionExcepcion e) {
			throw new BaseDeDatosExcepcion(
				"Error guardando resultados operacion El Punto Agil", e);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					logger
						.error(
							"insertarDatosOperacionPuntoAgil(DatosOperacionPuntoAgil)",
							e);
				}
			}
		}
		// VERIFICAR SI SE INSERTARON LOS REGISTROS PARA EL BOOLEAN
		int sum = 0;
		for (int i = 0; i < salida.length; i++) {
			int j = salida[i];
			if (j > 0 || j == Statement.SUCCESS_NO_INFO) {
				sum++;
			}
		}
		logger.info("Insertados Datos Operacion: "
				+ ArrayUtils.toString(salida));

		return (sum > 0);
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se comentó variable sin uso
	* Fecha: agosto 2011
	*/
	protected void sincronizarDatosOperacionPuntoAgil()
			throws BaseDeDatosExcepcion {
		
		logger.info("Comienza sincronizacion Operaciones Punto Agil");
		
		PreparedStatement psInsertarServidor = null;
		Statement actualizarOperacionesServidor = null;
		Statement actualizarOperacionesLocal = null;
		boolean sync = true;
		try {
			try {
				
				validarStatus();
				
				DatosOperacionPuntoAgil[] dopaInsertar = cargarOperacionesPuntoAgilRegActualizado(
					Constantes.REGISTRO_ACTUALIZADO_CREADO, true, sync);
				DatosOperacionPuntoAgil[] dopaActualizar = cargarOperacionesPuntoAgilRegActualizado(
					Constantes.REGISTRO_ACTUALIZADO_NO, true, sync);
				Conexiones.iniciarTransaccion(false, sync);
				Conexiones.iniciarTransaccion(true, sync);
				psInsertarServidor = Conexiones.prepararSentencia(
					SQL_INSERT_DATOS_OPERACION, false, sync);

				actualizarOperacionesServidor = Conexiones.crearSentencia(
					false, sync);
				actualizarOperacionesLocal = Conexiones.crearSentencia(
					true, sync);

				for (int i = 0; i < dopaInsertar.length; i++) {
					DatosOperacionPuntoAgil agil = dopaInsertar[i];
					agil.setRegActualizado(Constantes.REGISTRO_ACTUALIZADO_SI);
					insertarDatosOperacionPuntoAgilAsignarParametros(
						agil, psInsertarServidor);
					agregarUpdateOperacionPuntoAgil(
						actualizarOperacionesLocal, agil);
				}
				int[] insertadosServidor = psInsertarServidor.executeBatch();
				if (logger.isInfoEnabled()) {
					logger.info("Insertados Datos Operacion: "
							+ MathUtil.sum(insertadosServidor)
							+ SystemUtils.LINE_SEPARATOR
							+ ArrayUtils.toString(insertadosServidor));
				}

				for (int i = 0; i < dopaActualizar.length; i++) {
					DatosOperacionPuntoAgil agil = dopaActualizar[i];
					agil.setRegActualizado(Constantes.REGISTRO_ACTUALIZADO_SI);
					agregarUpdateOperacionPuntoAgil(
						actualizarOperacionesServidor, agil);
					agregarUpdateOperacionPuntoAgil(
						actualizarOperacionesLocal, agil);
				}
				int[] actualizadosServidor = actualizarOperacionesServidor
					.executeBatch();
				if (logger.isInfoEnabled()) {
					logger.info("Actualizados Servidor Datos Operacion: "
							+ MathUtil.sum(actualizadosServidor)
							+ SystemUtils.LINE_SEPARATOR
							+ ArrayUtils.toString(actualizadosServidor));
				}

				int[] actualizadosLocal = actualizarOperacionesLocal
					.executeBatch();
				if (logger.isInfoEnabled()) {
					logger.info("Actualizados Local Datos Operacion: "
							+ MathUtil.sum(actualizadosLocal)
							+ SystemUtils.LINE_SEPARATOR
							+ ArrayUtils.toString(actualizadosLocal));
				}

				Conexiones.commitTransaccion(false, sync);
				Conexiones.commitTransaccion(true, sync);
				
			} catch (SQLException e) {
				
//				MensajesVentanas.mensajeError("ERROR EN SINCRONIZACION: "
//						+ SystemUtils.LINE_SEPARATOR + e);
//				
//				Conexiones.rollbackTransaccion(false, sync);
//				Conexiones.rollbackTransaccion(true, sync);
//				***
				if (Sesion.isCajaEnLinea()) { 
					
					Conexiones.rollbackTransaccion(false, sync);
					Conexiones.rollbackTransaccion(true, sync);
					
					logger
							.info("Inicio de control de la transaccion punto agil de clave duplicada");

					int maxTransaccionSrv = 0;
				/*	int countSrvp = 0;
					int countSrvd = 0;		*/		

					String sentenciaSQL = "select max(numSeq) from CR.puntoAgilOperacion  where numtienda = "
							+ Sesion.getNumTienda()
							+ " and numcaja = "
							+ Sesion.getNumCaja()
							+ "";

						ResultSet resultadoPuntoAgilSrv = Conexiones
								.realizarConsulta(sentenciaSQL, false,
										false, true);											
						
						try {
							if (resultadoPuntoAgilSrv.first() && resultadoPuntoAgilSrv != null) {
								logger.warn("Iniciar ejecucion local  PuntoAgil (regactualizado)");
								maxTransaccionSrv = resultadoPuntoAgilSrv.getInt(1);
								String sentenciaSQLCaja = "select * from CR.puntoAgilOperacion  where numtienda = "
									+ Sesion.getNumTienda()
									+ " and numcaja = "
									+ Sesion.getNumCaja()
									+ " and numSeq <= "
									+ maxTransaccionSrv
									+ " and regactualizado = 'C'";
								ResultSet resultadoPtoAgilLocal = Conexiones
								.realizarConsulta(sentenciaSQLCaja, true);
								
								if (resultadoPtoAgilLocal.first() && resultadoPtoAgilLocal != null){
									logger.warn("Iniciar ejecucion del update PuntoAgil (regactualizado)");											
										int numSeq = resultadoPtoAgilLocal.getInt("numSeq");																	
										String sentenciaUpdatePtoAgil = 
										"update CR.puntoAgilOperacion set regactualizado ='"
										+ Constantes.REGISTRO_ACTUALIZADO_SI
										+ "' where (numtienda = "
										+ Sesion.getNumTienda()
										+ ") and (numcaja = "
										+ Sesion.getNumCaja()
										+ ") and (numSeq  <= "
										+ maxTransaccionSrv
										+ ")";
																		
										Conexiones.realizarSentencia(sentenciaUpdatePtoAgil, true, false);
										logger.warn("Se actualizo el registro de N° Secuencia : "+numSeq+"");									
								}
							}
						} catch (BaseDeDatosExcepcion e1) {
							// TODO Bloque catch generado automáticamente
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Bloque catch generado automáticamente
							e1.printStackTrace();
						}
						
						
					}			
			//***
								
//				throw new BaseDeDatosExcepcion(
//					"Error sincronizando resultados operacion El Punto Agil con servidor",
//					e);
			}
		} catch (ConexionExcepcion e) {
			if (Sesion.isCajaEnLinea()){
				MensajesVentanas.mensajeError("ERROR EN SINCRONIZACION: "
						+ SystemUtils.LINE_SEPARATOR + e);
				throw new BaseDeDatosExcepcion(
						"Error sincronizando resultados operacion El Punto Agil con servidor",
						e);
			}
			
		} finally {
			if (psInsertarServidor != null) {
				try {
					psInsertarServidor.close();
				} catch (SQLException e) {
					logger
						.error(
							"insertarDatosOperacionPuntoAgil(DatosOperacionPuntoAgil)",
							e);
				}
			}

			if (actualizarOperacionesServidor != null) {
				try {
					actualizarOperacionesServidor.close();
				} catch (SQLException e) {
					logger
						.error(
							"insertarDatosOperacionPuntoAgil(DatosOperacionPuntoAgil)",
							e);
				}
			}
			if (actualizarOperacionesLocal != null) {
				try {
					actualizarOperacionesLocal.close();
				} catch (SQLException e) {
					logger
						.error(
							"insertarDatosOperacionPuntoAgil(DatosOperacionPuntoAgil)",
							e);
				}
			}
		}
	}

	/**
	 * @param dopa
	 * @param ps
	 * @throws SQLException
	 */
	private void insertarDatosOperacionPuntoAgilAsignarParametros(
			DatosOperacionPuntoAgil dopa, PreparedStatement ps)
			throws SQLException {
		ps.setObject(1, dopa.getNumtienda());
		ps.setObject(2, dopa.getNumcaja());
		ps.setObject(3, dopa.getVtId());
		ps.setObject(4, dopa.getNumSeq());
		ps.setObject(5, dopa.getTipoProceso());
		ps.setObject(6, dopa.getCodformadepago());
		ps.setObject(7, dopa.getNumproceso());
		ps.setObject(8, dopa.getNumservicio());
		ps.setObject(9, dopa.getCorrelativoPagoProceso());
		ps.setObject(10, dopa.getCodCajero());
		ps.setObject(11, dopa.getFecha());
		ps.setObject(12, dopa.getHoraInicia());
		ps.setObject(13, dopa.getHoraFinaliza());
		ps.setObject(14, dopa.getDe_cedulaCliente());
		ps.setObject(15, dopa.getDe_monto());
		ps.setObject(16, dopa.getDe_tipoCuenta());
		ps.setObject(17, dopa.getDe_ctaEspecial());
		ps.setObject(18, dopa.getDe_nroCuenta());
		ps.setObject(19, dopa.getDe_nroCheque());
		ps.setObject(20, dopa.getDe_numSeqAnular());
		ps.setObject(21, dopa.getDe_planCreditoEPA());
		ps.setObject(22, dopa.getDe_porcentajeProvimillas());
		ps.setObject(23, dopa.getDt_numeroTarjeta());
		//NO SE GUARDA NOMBRE DEL CLIENTE POR REQUERIMIENTO CREDICARD
		//ps.setObject(24, dopa.getDt_nombreCliente());
		ps.setObject(24, null);
		ps.setObject(25, dopa.getDt_TipoTarjeta());
		ps.setObject(26, dopa.getDo_codRespuesta());
		ps.setObject(27, dopa.getDo_mensajeRespuesta());
		ps.setObject(28, dopa.getDo_mensajeError());
		ps.setObject(29, dopa.getDo_nombreAutorizador());
		ps.setObject(30, dopa.getDo_numeroAutorizacion());
		ps.setObject(31, dopa.getDo_nombreVoucher());
		ps.setObject(32, dopa.getStatus());
		ps.setObject(33, dopa.getTipoOperacion());
		ps.setObject(34, dopa.getRegActualizado());
		ps.addBatch();
	}

	protected void registrarPago(Statement _loteSentenciasCR, Venta _venta,
			Pago _pagoActual, int _correlativoItem) throws SQLException {
		super.registrarPago(
			_loteSentenciasCR, _venta, _pagoActual, _correlativoItem);
		if (_pagoActual instanceof DatosPagoPuntoAgil) {
			DatosPagoPuntoAgil pago = (DatosPagoPuntoAgil) _pagoActual;
			DatosOperacionPuntoAgil[] operacionesPuntoAgil = pago
				.getDatosOperacionPuntoAgilArray();
			for (int i = 0; i < operacionesPuntoAgil.length; i++) {
				DatosOperacionPuntoAgil agil = operacionesPuntoAgil[i];
				agil.setNumproceso(_venta.getNumTransaccion());
				agil.setCorrelativoPagoProceso(_correlativoItem);
				agil.cambiarRegActualizado();
				agregarUpdateOperacionPuntoAgil(_loteSentenciasCR, agil);
			}
		}
	}

	protected void registrarPago(Statement _loteSentenciasCR, Abono _ab,
			int _numServ, Pago _pagoActual, int _correlativoItem)
			throws SQLException {
		super.registrarPago(
			_loteSentenciasCR, _ab, _numServ, _pagoActual, _correlativoItem);
		if (_pagoActual instanceof DatosPagoPuntoAgil) {
			DatosPagoPuntoAgil pago = (DatosPagoPuntoAgil) _pagoActual;
			DatosOperacionPuntoAgil[] operacionesPuntoAgil = pago
				.getDatosOperacionPuntoAgilArray();
			for (int i = 0; i < operacionesPuntoAgil.length; i++) {
				DatosOperacionPuntoAgil agil = operacionesPuntoAgil[i];
				agil.setNumproceso(_ab.getNumAbono());
				agil.setNumservicio(_numServ);
				agil.setCorrelativoPagoProceso(_correlativoItem);
				agil.cambiarRegActualizado();
				agregarUpdateOperacionPuntoAgil(_loteSentenciasCR, agil);
			}
		}
	}

	/**
	 * @param _loteSentenciasCR
	 * @param agil
	 * @throws SQLException
	 */
	void agregarUpdateOperacionPuntoAgil(Statement _loteSentenciasCR,
			DatosOperacionPuntoAgil agil) throws SQLException {
		String sqlUpdateOperacionPuntoAgil = "update CR.puntoAgilOperacion set "
				+ "numproceso = "
				+ agil.getNumproceso()
				+ ", "
				+ "numservicio = "
				+ agil.getNumservicio()
				+ ", "
				+ "correlativoPagoProceso = "
				+ agil.getCorrelativoPagoProceso()
				+ ", "
				+ "status = '"
				+ agil.getStatus()
				+ "', "
				+ "regActualizado = '"
				+ agil.getRegActualizado()
				+ "' "
				+ "where numtienda = "
				+ agil.getNumtienda()
				+ " and numcaja = "
				+ agil.getNumcaja()
				+ " and vtId = '"
				+ agil.getVtId()
				+ "' and numSeq = "
				+ agil.getNumSeq();
		_loteSentenciasCR.addBatch(sqlUpdateOperacionPuntoAgil);
	}

	protected DatosOperacionPuntoAgil cargarOperacionPuntoAgil(String vtId,
			int numSeq) throws BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarOperacionPuntoAgil(String, int) - start");
		}
		String sql = SQL_DATOS_OPERACION + " where vtId = '" + vtId
				+ "' and numSeq = " + numSeq;
		ResultSet rsOperacionPuntoAgil = null;
		try {
			rsOperacionPuntoAgil = Conexiones.realizarConsulta(sql, true);
			if (rsOperacionPuntoAgil.first()) {
				DatosOperacionPuntoAgil operacionPuntoAgil = resultSet2DatosOperacionPuntoAgil(rsOperacionPuntoAgil);
				if (logger.isDebugEnabled()) {
					logger.debug("cargarOperacionPuntoAgil(String, int) - end");
				}
				return operacionPuntoAgil;
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("cargarOperacionPuntoAgil(String, int) - end");
				}
				return null;
			}
		} catch (SQLException e) {
			throw new BaseDeDatosExcepcion(
				"Error sincronizando resultados operacion El Punto Agil con servidor",
				e);
		} catch (ConexionExcepcion e) {
			throw new BaseDeDatosExcepcion(
				"Error sincronizando resultados operacion El Punto Agil con servidor",
				e);
		} finally {
			if (rsOperacionPuntoAgil != null) {
				try {
					rsOperacionPuntoAgil.close();
				} catch (SQLException e) {
					logger.error("cargarOperacionPuntoAgil(String, int)", e);
				}
				rsOperacionPuntoAgil = null;
			}
		}
	}

	protected DatosOperacionPuntoAgil[] cargarOperacionesPuntoAgilRegActualizado(
			String regActualizado, boolean local, boolean sync)
			throws BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger
				.debug("cargarOperacionesPuntoAgilRegActualizado(String) - start");
		}
		String sql = SQL_DATOS_OPERACION + " where regActualizado = '"
				+ regActualizado + "'";
		/*
		* Modificación realizada durante la migración a Java6 por jperez
		* Sólo se parametrizó el tipo de dato de la lista y el ArrayList
		*/
		List <DatosOperacionPuntoAgil>operacionesPuntoAgil = new ArrayList<DatosOperacionPuntoAgil>();
		ResultSet rsOperacionPuntoAgil = null;
		try {
			rsOperacionPuntoAgil = Conexiones.realizarConsulta(
				sql, local, false, sync);
			rsOperacionPuntoAgil.beforeFirst();
			while (rsOperacionPuntoAgil.next()) {
				DatosOperacionPuntoAgil operacionPuntoAgil = resultSet2DatosOperacionPuntoAgil(rsOperacionPuntoAgil);
				operacionesPuntoAgil.add(operacionPuntoAgil);
			}
		} catch (SQLException e) {
			throw new BaseDeDatosExcepcion(
				"Error cargando resultados operacion El Punto Agil con servidor",
				e);
		} catch (ConexionExcepcion e) {
			throw new BaseDeDatosExcepcion(
				"Error cargando resultados operacion El Punto Agil con servidor",
				e);
		} finally {
			if (rsOperacionPuntoAgil != null) {
				try {
					rsOperacionPuntoAgil.close();
				} catch (SQLException e) {
					logger.error(
						"cargarOperacionesPuntoAgilRegActualizado(String)", e);
				}
				rsOperacionPuntoAgil = null;
			}
		}
		return (DatosOperacionPuntoAgil[]) operacionesPuntoAgil
			.toArray(new DatosOperacionPuntoAgil[operacionesPuntoAgil.size()]);
	}

	private DatosOperacionPuntoAgil resultSet2DatosOperacionPuntoAgil(
			ResultSet _rsOperacion) throws SQLException {
		int tipoProceso = _rsOperacion.getInt("tipoProceso");
		int tipoOperacion = _rsOperacion.getInt("tipoOperacion");

		DatosOperacionPuntoAgil op = new DatosOperacionPuntoAgil(new Integer(
			tipoProceso), new Integer(tipoOperacion));
		op.setCodCajero(_rsOperacion.getString("codCajero"));

		op.setNumtienda((Number) _rsOperacion.getObject("numtienda"));
		op.setNumcaja((Number) _rsOperacion.getObject("numcaja"));
		op.setVtId((String) _rsOperacion.getObject("vtId"));
		op.setNumSeq((Number) _rsOperacion.getObject("numSeq"));
		op.setCodformadepago((String) _rsOperacion.getObject("codformadepago"));
		op.setNumproceso((Number) _rsOperacion.getObject("numproceso"));
		op.setCorrelativoPagoProceso((Number) _rsOperacion
			.getObject("correlativoPagoProceso"));
		op.setCodCajero((String) _rsOperacion.getObject("codCajero"));
		op.setFecha((java.sql.Date) _rsOperacion.getObject("fecha"));
		op.setHoraInicia((java.sql.Time) _rsOperacion.getObject("horaInicia"));
		op.setHoraFinaliza((java.sql.Time) _rsOperacion
			.getObject("horaFinaliza"));
		op.setDe_cedulaCliente((String) _rsOperacion
			.getObject("de_cedulaCliente"));
		op.setDe_monto((Number) _rsOperacion.getObject("de_monto"));
		op.setDe_tipoCuenta((String) _rsOperacion.getObject("de_tipoCuenta"));
		op.setDe_ctaEspecial((String) _rsOperacion.getObject("de_ctaEspecial"));
		op.setDe_nroCuenta((BigDecimal) _rsOperacion.getObject("de_nroCuenta"));
		op.setDe_nroCheque((BigDecimal) _rsOperacion.getObject("de_nroCheque"));
		op.setDe_numSeqAnular((Number) _rsOperacion
			.getObject("de_numSeqAnular"));
		op.setDe_porcentajeProvimillas((BigDecimal) _rsOperacion
			.getObject("de_porcentajeProvimillas"));
		op.setDe_planCreditoEPA((String) _rsOperacion
			.getObject("de_planCreditoEPA"));
		op.setDt_numeroTarjeta((String) _rsOperacion
			.getObject("dt_numeroTarjeta"));
		op.setDt_nombreCliente((String) _rsOperacion
			.getObject("dt_nombreCliente"));
		op.setDt_TipoTarjeta((String) _rsOperacion.getObject("dt_TipoTarjeta"));
		op.setDo_codRespuesta((String) _rsOperacion
			.getObject("do_codRespuesta"));
		op.setDo_mensajeRespuesta((String) _rsOperacion
			.getObject("do_mensajeRespuesta"));
		op.setDo_mensajeError((String) _rsOperacion
			.getObject("do_mensajeError"));
		op.setDo_nombreAutorizador((String) _rsOperacion
			.getObject("do_nombreAutorizador"));
		op.setDo_numeroAutorizacion((String) _rsOperacion
			.getObject("do_numeroAutorizacion"));
		op.setDo_nombreVoucher((String) _rsOperacion
			.getObject("do_nombreVoucher"));
		op.setStatus((String) _rsOperacion.getObject("status"));
		op.setRegActualizado((String) _rsOperacion.getObject("regActualizado"));
		return op;
	}

	boolean actualizarOperacionPuntoAgil(DatosOperacionPuntoAgil dopa)
			throws BaseDeDatosExcepcion {
		boolean sync = false;
		Statement actualizarOperacionesLocal = null;
		try {
			try {
				Conexiones.iniciarTransaccion(true, sync);
				actualizarOperacionesLocal = Conexiones.crearSentencia(
					true, sync);
				agregarUpdateOperacionPuntoAgil(
					actualizarOperacionesLocal, dopa);
				int[] actualizadosLocal = actualizarOperacionesLocal
					.executeBatch();
				Conexiones.commitTransaccion(true, sync);
				long actualizados = MathUtil.sum(actualizadosLocal);
				return actualizados > 0;
			} catch (SQLException e) {
				Conexiones.rollbackTransaccion(true, sync);
				MensajesVentanas
					.mensajeError("Error actualizando resultados operacion El Punto Agil: "
							+ SystemUtils.LINE_SEPARATOR + e);
				throw new BaseDeDatosExcepcion(
					"Error actualizando resultados operacion El Punto Agil", e);
			}
		} catch (ConexionExcepcion e) {
			MensajesVentanas.mensajeError("ERROR EN SINCRONIZACION: "
					+ SystemUtils.LINE_SEPARATOR + e);
			throw new BaseDeDatosExcepcion(
				"Error actualizando resultados operacion El Punto Agil", e);
		} finally {
			if (actualizarOperacionesLocal != null) {
				try {
					actualizarOperacionesLocal.close();
				} catch (SQLException e) {
					logger
						.error(
							"actualizarOperacionPuntoAgil(DatosOperacionPuntoAgil)",
							e);
				}
			}
		}
	}

	protected Integer obtenerTipoProcesoSegunEstadoCaja(String idestado)
			throws BaseDeDatosExcepcion {
		Integer tipoProceso = null;
		String sql = "select tipoProceso from CR.puntoAgilProcesoEstadoCaja where idestado = '"
				+ idestado + "'";
		ResultSet rsOperacionPuntoAgil = null;
		try {
			rsOperacionPuntoAgil = Conexiones.realizarConsulta(sql, true);
			if (rsOperacionPuntoAgil.first()) {
				tipoProceso = new Integer(rsOperacionPuntoAgil
					.getInt("tipoProceso"));
			}
		} catch (SQLException e) {
			throw new BaseDeDatosExcepcion(
				"Error cargando resultados operacion El Punto Agil con servidor",
				e);
		} catch (ConexionExcepcion e) {
			throw new BaseDeDatosExcepcion(
				"Error cargando resultados operacion El Punto Agil con servidor",
				e);
		} finally {
			if (rsOperacionPuntoAgil != null) {
				try {
					rsOperacionPuntoAgil.close();
				} catch (SQLException e) {
					logger
						.error("obtenerTipoProcesoSegunEstadoCaja(String)", e);
				}
				rsOperacionPuntoAgil = null;
			}
		}
		return tipoProceso;
	}

	protected int obtenerMaximoNumSeq(String vtId, boolean local)
			throws BaseDeDatosExcepcion {
		int maxNumSeq = 0;
		String sql = "select max(numSeq) as numSeq from CR.puntoAgilOperacion where vtId = '"
				+ vtId + "'";
		ResultSet rsMaxNumSeq = null;
		try {
			rsMaxNumSeq = Conexiones.realizarConsulta(sql, local);
			if (rsMaxNumSeq.first()) {
				maxNumSeq = rsMaxNumSeq.getInt("numSeq");
			}
		} catch (SQLException e) {
			throw new BaseDeDatosExcepcion(
				"Error cargando resultados operacion El Punto Agil con servidor",
				e);
		} catch (ConexionExcepcion e) {
			throw new BaseDeDatosExcepcion(
				"Error cargando resultados operacion El Punto Agil con servidor",
				e);
		} finally {
			if (rsMaxNumSeq != null) {
				try {
					rsMaxNumSeq.close();
				} catch (SQLException e) {
					logger.error("obtenerMaximoNumSeq(String, boolean)", e);
				}
				rsMaxNumSeq = null;
			}
		}
		return maxNumSeq;
	}

	protected DatosCajaPuntoAgil cargarDatosCajaPuntoAgil()
			throws BaseDeDatosExcepcion {
		String sql = "select numtienda, numcaja, fechaCierreMerchant from CR.puntoagilcaja where numtienda = "
				+ Sesion.getTienda().getNumero()
				+ " and numcaja = "
				+ Sesion.getCaja().getNumero();
		ResultSet rsDatosCaja = null;
		try {
			rsDatosCaja = Conexiones.realizarConsulta(sql, true);
			if (rsDatosCaja.first()) {
				DatosCajaPuntoAgil cajaPuntoAgil = resultSet2DatosCajaPuntoAgil(rsDatosCaja);
				if (logger.isDebugEnabled()) {
					logger.debug("cargarDatosCajaPuntoAgil() - end");
				}
				return cajaPuntoAgil;
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("cargarDatosCajaPuntoAgil() - end");
				}
				return null;
			}
		} catch (SQLException e) {
			throw new BaseDeDatosExcepcion(
				"Error cargando resultados operacion El Punto Agil con servidor",
				e);
		} catch (ConexionExcepcion e) {
			throw new BaseDeDatosExcepcion(
				"Error cargando resultados operacion El Punto Agil con servidor",
				e);
		} finally {
			if (rsDatosCaja != null) {
				try {
					rsDatosCaja.close();
				} catch (SQLException e) {
					logger.error("cargarDatosCajaPuntoAgil()", e);
				}
				rsDatosCaja = null;
			}
		}
	}

	private DatosCajaPuntoAgil resultSet2DatosCajaPuntoAgil(
			ResultSet _rsDatosCaja) throws SQLException {
		DatosCajaPuntoAgil cajaPuntoAgil = new DatosCajaPuntoAgil();
		int numtienda = _rsDatosCaja.getInt("numtienda");
		int numcaja = _rsDatosCaja.getInt("numcaja");
		Timestamp fechaCierreMerchant = _rsDatosCaja
			.getTimestamp("fechaCierreMerchant");
		cajaPuntoAgil.setNumtienda(numtienda);
		cajaPuntoAgil.setNumcaja(numcaja);
		cajaPuntoAgil.setFechaCierreMerchant(fechaCierreMerchant);
		return cajaPuntoAgil;
	}

	protected boolean insertarDatosCajaPuntoAgil(DatosCajaPuntoAgil datosCaja)
			throws BaseDeDatosExcepcion {
		String sql = "insert into CR.puntoagilcaja (numtienda, numcaja, fechaCierreMerchant) values ("
				+ datosCaja.getNumtienda()
				+ ", "
				+ datosCaja.getNumcaja()
				+ ", '" + datosCaja.getFechaCierreMerchant() + "')";
		int insertados;
		try {
			insertados = Conexiones.realizarSentencia(sql, true);
		} catch (ConexionExcepcion e) {
			throw new BaseDeDatosExcepcion(
				"Error cargando resultados operacion El Punto Agil con servidor",
				e);
		}
		return (insertados > 0 || insertados == Statement.SUCCESS_NO_INFO);
	}

	protected boolean actualizarDatosCajaPuntoAgil(DatosCajaPuntoAgil datosCaja)
			throws BaseDeDatosExcepcion {
		String sql = "update CR.puntoagilcaja set fechaCierreMerchant = '"
				+ datosCaja.getFechaCierreMerchant() + "' where numtienda = "
				+ datosCaja.getNumtienda() + " and numcaja = "
				+ datosCaja.getNumcaja();
		try {
			int actualizados = Conexiones.realizarSentencia(sql, true);
			return (actualizados > 0 || actualizados == Statement.SUCCESS_NO_INFO);
		} catch (ConexionExcepcion e) {
			throw new BaseDeDatosExcepcion(
				"Error cargando resultados operacion El Punto Agil con servidor",
				e);
		}
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se comentó variable sin uso
	* Fecha: agosto 2011
	*/
	public void validarStatus(){
		logger
		.info("Inicio de control de la transaccion punto agil de status");

		int maxTransaccionSrv = 0;
		/*int countSrvp = 0;
		int countSrvd = 0;		*/	

		String sentenciaSQL = "select max(numSeq) from CR.puntoAgilOperacion  where numtienda = "
		+ Sesion.getNumTienda()
		+ " and numcaja = "
		+ Sesion.getNumCaja()
		+ "";
		try {
			ResultSet resultadoPuntoAgilSrv = Conexiones
			.realizarConsulta(sentenciaSQL, false,
					false, true);											
	
	
		if (resultadoPuntoAgilSrv.first() && resultadoPuntoAgilSrv != null) {
			logger.warn("Iniciar ejecucion local  PuntoAgil (regactualizado)");
			maxTransaccionSrv = resultadoPuntoAgilSrv.getInt(1);
																			
				String sentenciaUpdatePtoAgil = 
				"update CR.puntoAgilOperacion set regactualizado ='"
				+ Constantes.REGISTRO_ACTUALIZADO_SI
				+ "' where (numtienda = "
				+ Sesion.getNumTienda()
				+ ") and (numcaja = "
				+ Sesion.getNumCaja()
				+ ") and (numSeq  <= "
				+ maxTransaccionSrv
				+ ")";
													
			Conexiones.realizarSentencia(sentenciaUpdatePtoAgil, true, false);
			logger.warn("Fin de estatus");
			
		}
		
	} catch (BaseDeDatosExcepcion e1) {
		// TODO Bloque catch generado automáticamente
		e1.printStackTrace();
	} catch (SQLException e1) {
		// TODO Bloque catch generado automáticamente
		e1.printStackTrace();
	}catch (ConexionExcepcion e) {
		// TODO Bloque catch generado automáticamente
		e.printStackTrace();
	}
	
	}
}
