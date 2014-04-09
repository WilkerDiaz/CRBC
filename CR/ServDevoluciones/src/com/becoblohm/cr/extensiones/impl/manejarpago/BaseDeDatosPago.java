/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.manejadorpago
 * Programa   : BaseDeDatosPago.java
 * Creado por : gmartinelli
 * Creado en  : 14-may-04 10:32:34
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.2
 * Fecha       : 19-jul-04 07:48
 * Analista    : gmartinelli
 * Descripción : Uso de Esquema en los queryes CR.tabla.campo
 * =============================================================================
 * Versión     : 1.1
 * Fecha       : 14-may-04 10:32:34
 * Analista    : gmartinelli
 * Descripción : Implementación inicial.
 * =============================================================================
 */
package com.becoblohm.cr.extensiones.impl.manejarpago;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.PagoExcepcion;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarservicio.Abono;
import com.becoblohm.cr.manejarservicio.ListaRegalos;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.manejarventa.Venta;
import com.becoblohm.cr.mediadoresbd.Conexiones;

/**
 * Descripción:
 * 
 */

public class BaseDeDatosPago {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(BaseDeDatosPago.class);

	/**
	 * 
	 */
	protected Vector cargarFormasDePago(boolean abonoApartado) {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarFormasDePago() - start");
		}

		Vector formasDePago = new Vector();
		ResultSet pagos = null;
		try {
			if (abonoApartado) {
				pagos = Conexiones.realizarConsulta("select * from formadepago where (formadepago.codformadepago!='" + Sesion.FORMA_PAGO_ABONO + "') and (formadepago.codformadepago!='" + Sesion.FORMA_PAGO_RETENCION + "') and (formadepago.regvigente = '" + Sesion.SI + "') and (formadepago.requiereautorizacion='N') order by formadepago.prioridad", true);
			} else {
				pagos = Conexiones.realizarConsulta("select * from formadepago where (formadepago.codformadepago!='" + Sesion.FORMA_PAGO_ABONO + "') and (formadepago.codformadepago!='" + Sesion.FORMA_PAGO_RETENCION + "') and (formadepago.regvigente = '" + Sesion.SI + "') order by formadepago.prioridad", true);
			}
			pagos.beforeFirst();
			while (pagos.next()) {
				String codigo = pagos.getString("codformadepago");
				int tipo = pagos.getInt("tipoformadepago");
				String nombre = pagos.getString("nombre");
				String codigoBanco = pagos.getString("codbanco");
				boolean indicarCodBanco = (pagos.getString("indicarbanco").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean indicarNumDocumento = (pagos.getString("indicarnumdocumento").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean indicarNumCuenta = (pagos.getString("indicarnumcuenta").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean indicarNumConformacion = (pagos.getString("indicarnumconforma").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean indicarNumReferencia = (pagos.getString("indicarnumreferencia").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean indicarCedulaTitular = (pagos.getString("indicarcedulatitular").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean validarSaldoCliente = (pagos.getString("validarsaldocliente").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean permiteVuelto = (pagos.getString("permitevuelto").toCharArray()[0] == Sesion.SI) ? true : false;
				double montoMinimo = pagos.getDouble("montominimo");
				double montoMaximo = pagos.getDouble("montomaximo")==0 ? Double.MAX_VALUE : pagos.getDouble("montomaximo");
				double montoComision = pagos.getDouble("montocomision");
				boolean entregaParcial = (pagos.getString("entregaparcial").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean reqAutorizacion = (pagos.getString("requiereautorizacion").toCharArray()[0] == Sesion.SI) ? true : false;
				formasDePago.addElement(new FormaDePago(codigo, tipo, nombre, codigoBanco, indicarCodBanco, indicarNumDocumento,
							indicarNumCuenta, indicarNumConformacion, indicarNumReferencia, indicarCedulaTitular,
							validarSaldoCliente, permiteVuelto, montoMinimo, montoMaximo, montoComision, entregaParcial,
							reqAutorizacion));
			}
		} catch (Exception ex) {
			logger.error("cargarFormasDePago()", ex);
		} finally {
			if (pagos != null) {
				try {
					pagos.close();
				} catch (SQLException e) {
					logger.error("cargarFormasDePago()", e);
				}
				pagos = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cargarFormasDePago() - end");
		}
		return formasDePago;
	}


	/**
		 * Carga la Forma de Pago "Abonos" para asignarlo a una facturacion de una Apartado
		 */
		protected FormaDePago cargarFormaDePagoEfectivo() throws PagoExcepcion {
			if (logger.isDebugEnabled()) {
				logger.debug("cargarFormaDePagoAbono() - start");
			}

			FormaDePago formasDePago;
			ResultSet pagos = null;
			try {
				pagos = Conexiones.realizarConsulta("select * from formadepago where (formadepago.codformadepago='" + Sesion.FORMA_PAGO_EFECTIVO + "')", true);
				if (pagos.first()) {
					String codigo = pagos.getString("codformadepago");
					int tipo = pagos.getInt("tipoformadepago");
					String nombre = pagos.getString("nombre");
					String codigoBanco = pagos.getString("codbanco");
					boolean indicarCodBanco = (pagos.getString("indicarbanco").toCharArray()[0] == Sesion.SI) ? true : false;
					boolean indicarNumDocumento = (pagos.getString("indicarnumdocumento").toCharArray()[0] == Sesion.SI) ? true : false;
					boolean indicarNumCuenta = (pagos.getString("indicarnumcuenta").toCharArray()[0] == Sesion.SI) ? true : false;
					boolean indicarNumConformacion = (pagos.getString("indicarnumconforma").toCharArray()[0] == Sesion.SI) ? true : false;
					boolean indicarNumReferencia = (pagos.getString("indicarnumreferencia").toCharArray()[0] == Sesion.SI) ? true : false;
					boolean indicarCedulaTitular = (pagos.getString("indicarcedulatitular").toCharArray()[0] == Sesion.SI) ? true : false;
					boolean validarSaldoCliente = (pagos.getString("validarsaldocliente").toCharArray()[0] == Sesion.SI) ? true : false;
					boolean permiteVuelto = (pagos.getString("permitevuelto").toCharArray()[0] == Sesion.SI) ? true : false;
					double montoMinimo = pagos.getDouble("montominimo");
					double montoMaximo = pagos.getDouble("montomaximo");
					double montoComision = pagos.getDouble("montocomision");
					boolean entregaParcial = (pagos.getString("entregaparcial").toCharArray()[0] == Sesion.SI) ? true : false;
					boolean reqAutorizacion = (pagos.getString("requiereautorizacion").toCharArray()[0] == Sesion.SI) ? true : false;
					formasDePago = new FormaDePago(codigo, tipo, nombre, codigoBanco, indicarCodBanco, indicarNumDocumento,
								indicarNumCuenta, indicarNumConformacion, indicarNumReferencia, indicarCedulaTitular,
								validarSaldoCliente, permiteVuelto, montoMinimo, montoMaximo, montoComision,
								entregaParcial, reqAutorizacion);
				} else
					throw new PagoExcepcion("Error al obtener forma de pago Abonos en la Base de Datos");
			}catch (PagoExcepcion ex) {
				logger.error("cargarFormaDePagoAbono()", ex);

				throw ex; 
			}catch (Exception ex) {
				logger.error("cargarFormaDePagoAbono()", ex);
				throw new PagoExcepcion("Error al obtener forma de pago Abonos en la Base de Datos",ex);
			} finally {
				if (pagos != null) {
					try {
						pagos.close();
					} catch (SQLException e) {
						logger.error("cargarFormaDePagoAbono()", e);
					}
					pagos = null;
				}
			}

			if (logger.isDebugEnabled()) {
				logger.debug("cargarFormaDePagoAbono() - end");
			}
			return formasDePago;
		}



	/**
	 * Carga la Forma de Pago "Abonos" para asignarlo a una facturacion de una Apartado
	 */
	protected FormaDePago cargarFormaDePagoAbono() throws PagoExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarFormaDePagoAbono() - start");
		}

		FormaDePago formasDePago;
		ResultSet pagos = null;
		try {
			pagos = Conexiones.realizarConsulta("select * from formadepago where (formadepago.codformadepago='" + Sesion.FORMA_PAGO_ABONO + "')", true);
			if (pagos.first()) {
				String codigo = pagos.getString("codformadepago");
				int tipo = pagos.getInt("tipoformadepago");
				String nombre = pagos.getString("nombre");
				String codigoBanco = pagos.getString("codbanco");
				boolean indicarCodBanco = (pagos.getString("indicarbanco").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean indicarNumDocumento = (pagos.getString("indicarnumdocumento").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean indicarNumCuenta = (pagos.getString("indicarnumcuenta").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean indicarNumConformacion = (pagos.getString("indicarnumconforma").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean indicarNumReferencia = (pagos.getString("indicarnumreferencia").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean indicarCedulaTitular = (pagos.getString("indicarcedulatitular").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean validarSaldoCliente = (pagos.getString("validarsaldocliente").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean permiteVuelto = (pagos.getString("permitevuelto").toCharArray()[0] == Sesion.SI) ? true : false;
				double montoMinimo = pagos.getDouble("montominimo");
				double montoMaximo = pagos.getDouble("montomaximo");
				double montoComision = pagos.getDouble("montocomision");
				boolean entregaParcial = (pagos.getString("entregaparcial").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean reqAutorizacion = (pagos.getString("requiereautorizacion").toCharArray()[0] == Sesion.SI) ? true : false;
				formasDePago = new FormaDePago(codigo, tipo, nombre, codigoBanco, indicarCodBanco, indicarNumDocumento,
							indicarNumCuenta, indicarNumConformacion, indicarNumReferencia, indicarCedulaTitular,
							validarSaldoCliente, permiteVuelto, montoMinimo, montoMaximo, montoComision,
							entregaParcial, reqAutorizacion);
			} else
				throw new PagoExcepcion("Error al obtener forma de pago Abonos en la Base de Datos");
		}catch (PagoExcepcion ex) {
			logger.error("cargarFormaDePagoAbono()", ex);

			throw ex; 
		}catch (Exception ex) {
			logger.error("cargarFormaDePagoAbono()", ex);
			throw new PagoExcepcion("Error al obtener forma de pago Abonos en la Base de Datos",ex);
		} finally {
			if (pagos != null) {
				try {
					pagos.close();
				} catch (SQLException e) {
					logger.error("cargarFormaDePagoAbono()", e);
				}
				pagos = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cargarFormaDePagoAbono() - end");
		}
		return formasDePago;
	}


	/**
	 * Carga la Forma de Pago "Retenciones" para asignarlo a una facturacion de una Apartado
	 */
	protected FormaDePago cargarFormaDePagoRetencion() throws PagoExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarFormaDePagoRetencion() - start");
		}

		FormaDePago formasDePago;
		ResultSet pagos = null;
		try {
			pagos = Conexiones.realizarConsulta("select * from formadepago where formadepago.codformadepago='" + Sesion.FORMA_PAGO_RETENCION + "'", true);
			if (pagos.first()) {
				String codigo = pagos.getString("codformadepago");
				int tipo = pagos.getInt("tipoformadepago");
				String nombre = pagos.getString("nombre");
				String codigoBanco = pagos.getString("codbanco");
				boolean indicarCodBanco = (pagos.getString("indicarbanco").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean indicarNumDocumento = (pagos.getString("indicarnumdocumento").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean indicarNumCuenta = (pagos.getString("indicarnumcuenta").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean indicarNumConformacion = (pagos.getString("indicarnumconforma").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean indicarNumReferencia = (pagos.getString("indicarnumreferencia").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean indicarCedulaTitular = (pagos.getString("indicarcedulatitular").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean validarSaldoCliente = (pagos.getString("validarsaldocliente").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean permiteVuelto = (pagos.getString("permitevuelto").toCharArray()[0] == Sesion.SI) ? true : false;
				double montoMinimo = pagos.getDouble("montominimo");
				double montoMaximo = pagos.getDouble("montomaximo");
				double montoComision = pagos.getDouble("montocomision");
				boolean entregaParcial = (pagos.getString("entregaparcial").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean reqAutorizacion = (pagos.getString("requiereautorizacion").toCharArray()[0] == Sesion.SI) ? true : false;
				formasDePago = new FormaDePago(codigo, tipo, nombre, codigoBanco, indicarCodBanco, indicarNumDocumento,
							indicarNumCuenta, indicarNumConformacion, indicarNumReferencia, indicarCedulaTitular,
							validarSaldoCliente, permiteVuelto, montoMinimo, montoMaximo, montoComision,
							entregaParcial, reqAutorizacion);
			} else
				throw new PagoExcepcion("Error al obtener forma de pago Retención en la Base de Datos");
		}catch (PagoExcepcion ex) {
			logger.error("cargarFormaDePagoRetencion()", ex);

			throw ex; 
		}catch (Exception ex) {
			logger.error("cargarFormaDePagoRetencion()", ex);
			throw new PagoExcepcion("Error al obtener forma de pago Retención en la Base de Datos",ex);
		} finally {
			if (pagos != null) {
				try {
					pagos.close();
				} catch (SQLException e) {
					logger.error("cargarFormaDePagoRetencion()", e);
				}
				pagos = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cargarFormaDePagoRetencion() - end");
		}
		return formasDePago;
	}

	/**
	 * Método cargarFormaDePago
	 * 
	 * @param codFPago
	 * @return
	 * FormaDePago
	 */
	protected FormaDePago cargarFormaDePago(String codFPago) {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarFormaDePago(String) - start");
		}

		ResultSet pagos = null;
		try {
			pagos = Conexiones.realizarConsulta("select * from formadepago where formadepago.codformadepago='" + codFPago + "'", true);
			if (pagos.first()) {
				String codigo = pagos.getString("codformadepago");
				int tipo = pagos.getInt("tipoformadepago");
				String nombre = pagos.getString("nombre");
				String codigoBanco = pagos.getString("codbanco");
				boolean indicarCodBanco = (pagos.getString("indicarbanco").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean indicarNumDocumento = (pagos.getString("indicarnumdocumento").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean indicarNumCuenta = (pagos.getString("indicarnumcuenta").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean indicarNumConformacion = (pagos.getString("indicarnumconforma").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean indicarNumReferencia = (pagos.getString("indicarnumreferencia").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean indicarCedulaTitular = (pagos.getString("indicarcedulatitular").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean validarSaldoCliente = (pagos.getString("validarsaldocliente").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean permiteVuelto = (pagos.getString("permitevuelto").toCharArray()[0] == Sesion.SI) ? true : false;
				double montoMinimo = pagos.getDouble("montominimo");
				double montoMaximo = pagos.getDouble("montomaximo")==0 ? Double.MAX_VALUE : pagos.getDouble("montomaximo");
				double montoComision = pagos.getDouble("montocomision");
				boolean entregaParcial = (pagos.getString("entregaparcial").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean reqAutorizacion = (pagos.getString("requiereautorizacion").toCharArray()[0] == Sesion.SI) ? true : false;
				FormaDePago returnFormaDePago = new FormaDePago(codigo, tipo,
						nombre, codigoBanco, indicarCodBanco,
						indicarNumDocumento, indicarNumCuenta,
						indicarNumConformacion, indicarNumReferencia,
						indicarCedulaTitular, validarSaldoCliente,
						permiteVuelto, montoMinimo, montoMaximo, montoComision,
						entregaParcial, reqAutorizacion);
				if (logger.isDebugEnabled()) {
					logger.debug("cargarFormaDePago(String) - end");
				}
				return  returnFormaDePago;
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("cargarFormaDePago(String) - end");
				}
				return null;
			}
		} catch (Exception ex) {
			logger.error("cargarFormaDePago(String)", ex);

			logger.error("cargarFormaDePago(String)", ex);
			return null;
		} finally {
			if (pagos != null) {
				try {
					pagos.close();
				} catch (SQLException e) {
					logger.error("cargarFormaDePago(String)", e);
				}
				pagos = null;
			}
		}
	}

	/**
	 * Método obtenerBancos
	 * 
	 * @return
	 * Vector
	 */
	protected Vector obtenerBancos() {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerBancos() - start");
		}

		ResultSet bancos = null;
		try {
			Vector bancosObtenidos = new Vector();
			bancos = Conexiones.realizarConsulta("select * from banco where regvigente = '" + Sesion.SI + "'", true);
			bancos.beforeFirst();
			while (bancos.next()) {
				String codigo = bancos.getString("codbanco");
				String codExterno = bancos.getString("codexterno");
				String nombre = bancos.getString("nombre");
				bancosObtenidos.addElement(new Banco(codigo, codExterno, nombre));
			}

			if (logger.isDebugEnabled()) {
				logger.debug("obtenerBancos() - end");
			}
			return bancosObtenidos;
		} catch (Exception ex) {
			logger.error("obtenerBancos()", ex);

			logger.error("obtenerBancos()", ex);
			return new Vector();
		} finally {
			if (bancos != null) {
				try {
					bancos.close();
				} catch (SQLException e) {
					logger.error("obtenerBancos()", e);
				}
				bancos = null;
			}
		}
	}

	/**
	 * Método cargarFormasDePagoNoBancarias
	 * 
	 * 
	 * void
	 */
	protected Vector cargarFormasDePagoNoBancarias() {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarFormasDePagoNoBancarias() - start");
		}

		Vector formasDePago = new Vector();
		ResultSet pagos = null;
		try {
			pagos = Conexiones.realizarConsulta("select * from formadepago where formadepago.indicarbanco='" + Sesion.NO + "' and formadepago.codformadepago!='" + Sesion.FORMA_PAGO_ABONO + "' and formadepago.codformadepago!='" + Sesion.FORMA_PAGO_RETENCION + "' and formadepago.regvigente = '" + Sesion.SI + "' order by formadepago.prioridad", true);
			pagos.beforeFirst();
			while (pagos.next()) {
				String codigo = pagos.getString("codformadepago");
				int tipo = pagos.getInt("tipoformadepago");
				String nombre = pagos.getString("nombre");
				String codigoBanco = pagos.getString("codbanco");
				boolean indicarCodBanco = (pagos.getString("indicarbanco").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean indicarNumDocumento = (pagos.getString("indicarnumdocumento").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean indicarNumCuenta = (pagos.getString("indicarnumcuenta").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean indicarNumConformacion = (pagos.getString("indicarnumconforma").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean indicarNumReferencia = (pagos.getString("indicarnumreferencia").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean indicarCedulaTitular = (pagos.getString("indicarcedulatitular").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean validarSaldoCliente = (pagos.getString("validarsaldocliente").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean permiteVuelto = (pagos.getString("permitevuelto").toCharArray()[0] == Sesion.SI) ? true : false;
				double montoMinimo = pagos.getDouble("montominimo");
				double montoMaximo = pagos.getDouble("montomaximo")==0 ? Double.MAX_VALUE : pagos.getDouble("montomaximo");
				double montoComision = pagos.getDouble("montocomision");
				boolean entregaParcial = (pagos.getString("entregaparcial").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean reqAutorizacion = (pagos.getString("requiereautorizacion").toCharArray()[0] == Sesion.SI) ? true : false;
				formasDePago.addElement(new FormaDePago(codigo, tipo, nombre, codigoBanco, indicarCodBanco, indicarNumDocumento,
							indicarNumCuenta, indicarNumConformacion, indicarNumReferencia, indicarCedulaTitular,
							validarSaldoCliente, permiteVuelto, montoMinimo, montoMaximo, montoComision, entregaParcial,
							reqAutorizacion));
			}
		} catch (Exception ex) {
			logger.error("cargarFormasDePagoNoBancarias()", ex);
		} finally {
			if (pagos != null){
				try {
					pagos.close();
				} catch (SQLException e) {
					logger.error("cargarFormasDePagoNoBancarias()", e);
				}
				pagos = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cargarFormasDePagoNoBancarias() - end");
		}
		return formasDePago;
	}

	/**
	 * 
	 */
	protected Vector cargarTodasFormasDePago() {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarTodasFormasDePago() - start");
		}

		Vector formasDePago = new Vector();
		ResultSet pagos = null;
		try {
			pagos = Conexiones.realizarConsulta("select * from formadepago where regvigente = '" + Sesion.SI + "' order by formadepago.nombre", true);
			pagos.beforeFirst();
			while (pagos.next()) {
				String codigo = pagos.getString("codformadepago");
				int tipo = pagos.getInt("tipoformadepago");
				String nombre = pagos.getString("nombre");
				String codigoBanco = pagos.getString("codbanco");
				boolean indicarCodBanco = (pagos.getString("indicarbanco").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean indicarNumDocumento = (pagos.getString("indicarnumdocumento").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean indicarNumCuenta = (pagos.getString("indicarnumcuenta").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean indicarNumConformacion = (pagos.getString("indicarnumconforma").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean indicarNumReferencia = (pagos.getString("indicarnumreferencia").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean indicarCedulaTitular = (pagos.getString("indicarcedulatitular").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean validarSaldoCliente = (pagos.getString("validarsaldocliente").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean permiteVuelto = (pagos.getString("permitevuelto").toCharArray()[0] == Sesion.SI) ? true : false;
				double montoMinimo = pagos.getDouble("montominimo");
				double montoMaximo = pagos.getDouble("montomaximo")==0 ? Double.MAX_VALUE : pagos.getDouble("montomaximo");
				double montoComision = pagos.getDouble("montocomision");
				boolean entregaParcial = (pagos.getString("entregaparcial").toCharArray()[0] == Sesion.SI) ? true : false;
				boolean reqAutorizacion = (pagos.getString("requiereautorizacion").toCharArray()[0] == Sesion.SI) ? true : false;
				formasDePago.addElement(new FormaDePago(codigo, tipo, nombre, codigoBanco, indicarCodBanco, indicarNumDocumento,
							indicarNumCuenta, indicarNumConformacion, indicarNumReferencia, indicarCedulaTitular,
							validarSaldoCliente, permiteVuelto, montoMinimo, montoMaximo, montoComision, entregaParcial,
							reqAutorizacion));
			}
		} catch (Exception ex) {
			logger.error("cargarTodasFormasDePago()", ex);
		} finally {
			if (pagos != null) {
				try {
					pagos.close();
				} catch (SQLException e) {
					logger.error("cargarTodasFormasDePago()", e);
				}
				pagos = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cargarTodasFormasDePago() - end");
		}
		return formasDePago;
	}

	/**
	 * Método validarSaldoCliente
	 * 
	 * @param cliente
	 * @param mto
	 * @return
	 * boolean
	 */
	protected boolean validarSaldoCliente(Cliente cliente, double mto) throws PagoExcepcion {
/*		if (logger.isDebugEnabled()) {
			logger.debug("validarSaldoCliente(Cliente, double) - start");
		}

		String sentenciaSQL = "select * from saldocliente where saldocliente.codcliente='" + cliente.getCodCliente() 
				+ "' and saldocliente.saldobloqueado = '" + Sesion.NO + "'";
		ResultSet renglonCliente  = null;
		try {
			renglonCliente = ConexionServCentral.realizarConsulta(sentenciaSQL);
			if (renglonCliente.first()) {
				if (renglonCliente.getDouble("saldo")>=mto)
				{
					if (logger.isDebugEnabled()) {
						logger.debug("validarSaldoCliente(Cliente, double) - end");
					}
					// Si la persona Tiene saldo 
					// Verificamos si el cliente pertenece a la tienda donde se está procesando la venta
					// Requerimiento de CENTROBECO para evitar pagos con Condicionales en tiendas distintas a la del Cajero
					// TODO Agregar una preferencia para este caso y mantener caso de EPA
					if (!(Integer.parseInt(cliente.getNumTienda())==Sesion.getTienda().getNumero())) {
						logger.info("Cliente " + cliente.getNombre() + " de tienda " + cliente.getNumTienda() + " intentó pagar con condicional en Tienda " + Sesion.getTienda().getNumero() + ". Monto " + mto + ". Operación procesada por el Cajero " + Sesion.getUsuarioActivo().getNumFicha() + " - " + Sesion.getUsuarioActivo().getNombre());
						// El Cliente es de una Tienda Distinta. Lanzamos una nueva excepcion
						throw new PagoExcepcion("El condicional Sólo puede ser utilizado en la Tienda del Colaborador.\nColaborador pertenece a Tienda " + cliente.getNumTienda());
					}
					return true;
				}
				else
				{
					if (logger.isDebugEnabled()) {
						logger.debug("validarSaldoCliente(Cliente, double) - end");
					}
					return false;
				}
			} else
				throw new PagoExcepcion("El cliente no posee creditos con la empresa");
		}catch (PagoExcepcion ex) {
			logger.error("validarSaldoCliente(Cliente, double)", ex);

			throw ex;
		} catch (SQLException ex) {
			logger.error("validarSaldoCliente(Cliente, double)", ex);

			throw new PagoExcepcion("Error al validar saldo del Cliente " + cliente.getCodCliente(),ex);
		} catch (BaseDeDatosExcepcion ex) {
			logger.error("validarSaldoCliente(Cliente, double)", ex);

			throw new PagoExcepcion("Error al validar saldo del Cliente " + cliente.getCodCliente(),ex);
		} catch (ConexionExcepcion ex) {
			logger.error("validarSaldoCliente(Cliente, double)", ex);

			throw new PagoExcepcion("Error al validar saldo del Cliente " + cliente.getCodCliente(),ex);
		} finally {
			if (renglonCliente != null){
				try {
					renglonCliente.close();
				} catch (SQLException e) {
					logger.error("validarSaldoCliente(Cliente, double)", e);
				}
				renglonCliente = null;
			}
			
		}*/
		return false;
	}

	/**
	 * Método actualizarSaldoCliente
	 * 
	 * 
	 * void
	 */
	protected void disminuirSaldoCliente(Cliente cliente, double mto) throws PagoExcepcion {
/*		if (logger.isDebugEnabled()) {
			logger.debug("disminuirSaldoCliente(Cliente, double) - start");
		}

		String sentenciaSQL = "select * from saldocliente where saldocliente.codcliente='" + cliente.getCodCliente() 
				+ "' and saldocliente.saldobloqueado = '" + Sesion.NO + "'";
		ResultSet renglonCliente  = null;
		try {
			renglonCliente = ConexionServCentral.realizarConsulta(sentenciaSQL);
			if (renglonCliente.first()) {
				double saldoAnterior = renglonCliente.getDouble("saldo");
				sentenciaSQL = "update saldocliente set saldocliente.saldo = " + (saldoAnterior-mto) 
					+ ", saldocliente.codcajero='" + Sesion.usuarioActivo.getNumFicha() + "', saldocliente.numficha='" + cliente.getNumFicha() + "' where saldocliente.codcliente='" 
					+ cliente.getCodCliente() + "'";
				ConexionServCentral.realizarSentencia(sentenciaSQL);
			}
		}catch (Exception ex) {
			logger.error("disminuirSaldoCliente(Cliente, double)", ex);

			throw new PagoExcepcion("Error al disminuir saldo del Cliente " + cliente.getCodCliente(),ex);
		} finally {
			if (renglonCliente != null) {
				try {
					renglonCliente.close();
				} catch (SQLException e) {
					logger.error("disminuirSaldoCliente(Cliente, double)", e);
				}
				renglonCliente = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("disminuirSaldoCliente(Cliente, double) - end");
		}*/
	}

	/**
	 * Método incrementarSaldoCliente
	 * 
	 * @param cliente
	 * @param mto
	 * void
	 */
	protected void incrementarSaldoCliente(Cliente cliente, double mto) throws PagoExcepcion {
/*		if (logger.isDebugEnabled()) {
			logger.debug("incrementarSaldoCliente(Cliente, double) - start");
		}

		String sentenciaSQL = "select * from saldocliente where saldocliente.codcliente='" + cliente.getCodCliente() 
				+ "' and saldocliente.saldobloqueado = '" + Sesion.NO + "'";
		ResultSet renglonCliente = null;
		try {
			renglonCliente = ConexionServCentral.realizarConsulta(sentenciaSQL);
			if (renglonCliente.first()) {
				double saldoAnterior = renglonCliente.getDouble("saldo");
				sentenciaSQL = "update saldocliente set saldocliente.saldo = " + (saldoAnterior+mto) 
					+ ", saldocliente.codcajero='" + Sesion.usuarioActivo.getNumFicha() + "' where saldocliente.codcliente='" 
					+ cliente.getCodCliente() + "'";
				ConexionServCentral.realizarSentencia(sentenciaSQL);
			} else {
				throw new PagoExcepcion("El cliente no posee creditos con la empresa");
			}
		}catch (Exception ex) {
			logger.error("incrementarSaldoCliente(Cliente, double)", ex);

			throw new PagoExcepcion("Error al actualizar saldo del Cliente " + cliente.getCodCliente()+".\nPuede que el cliente no tenga créditos con la empresa" ,ex);
		} finally {
			if (renglonCliente != null) {
				try {
					renglonCliente.close();
				} catch (SQLException e) {
					logger.error("incrementarSaldoCliente(Cliente, double)", e);
				}
				renglonCliente = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("incrementarSaldoCliente(Cliente, double) - end");
		}*/
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#registrarPagos(com.becoblohm.cr.manejarventa.Venta,
	 *      java.sql.Statement)
	 */
	protected double registrarPagos(Statement loteSentenciasCR, Venta venta)
			throws SQLException {
    	
/*		double mtoRecaudado = -venta.getMontoVuelto();
		double mtoTotal = venta.consultarMontoTrans();
		boolean noActualizarVuelto = false;
		Pago pagoActual = null;
		boolean isPagoTotal = false;
		int i =0;
		//Registramos los pagos de la venta 
		while (i<venta.getPagos().size() && !isPagoTotal){
        	
			pagoActual = (Pago) venta.getPagos().elementAt(i);
			if (MathUtil.roundDouble(pagoActual.getMonto()) >= mtoTotal){
				isPagoTotal = true;
			}else{
				mtoTotal -= MathUtil.roundDouble(pagoActual.getMonto());
			}
        	
			if (!noActualizarVuelto && venta.getMontoVuelto() >=0 && 
					pagoActual.getFormaPago().isPermiteVuelto()){
				noActualizarVuelto = true;
			}
			if (pagoActual.getFormaPago().isIncrementaEntregaParcial()){
				mtoRecaudado += pagoActual.getMonto();
			}
        	
			registrarPago(loteSentenciasCR, venta, pagoActual, (i + 1));
			i++;
		}  
		if (!noActualizarVuelto){
			String sqlUpdateMontoVuelto = "update CR.transaccion set vueltocliente=" + 0.0
							+ " where numtransaccion = "
							+ venta.getNumTransaccion()
							+ " ";	
			 logger.info("Resetear vuelto transaccion ...");
			loteSentenciasCR.addBatch(sqlUpdateMontoVuelto);
		}   
		return mtoRecaudado;*/
		return 0;
	}

	/**
	 * @param loteSentenciasCR
	 * @param venta
	 * @param pagoActual
	 * @param correlativoItem
	 * @throws SQLException
	 */
	protected void registrarPago(Statement loteSentenciasCR, Venta venta,
			Pago pagoActual, int correlativoItem) throws SQLException {
/*		String sentenciaSQLpago;
		String fechaTransString = Control.FECHA_FORMAT.format(venta
				.getFechaTrans());
		String codBanco = (pagoActual.getCodBanco() != null) ? "'"
				+ pagoActual.getCodBanco() + "'" : "null";
		String numDocumento = (pagoActual.getNumDocumento() != null) ? "'"
				+ pagoActual.getNumDocumento() + "'" : "null";
		String numCuenta = (pagoActual.getNumCuenta() != null) ? "'"
				+ pagoActual.getNumCuenta() + "'" : "null";
		String numConf = (pagoActual.getNumConformacion() != null) ? "'"
				+ pagoActual.getNumConformacion() + "'" : "null";
		String numReferencia = (pagoActual.getNumReferencia() != 0) ? "'"
				+ pagoActual.getNumReferencia() + "'" : "null";
		String cedTitular = (pagoActual.getCedTitular() != null) ? "'"
				+ pagoActual.getCedTitular() + "'" : "null";
		String codAutorizante = (pagoActual.getCodAutorizante() != null) ? "'"
				+ pagoActual.getCodAutorizante() + "'" : "null";
		// REMPLAZADO ((Pago)venta.getPagos().elementAt(i)).getMonto() por
		// pagoActual.getMonto()
		sentenciaSQLpago = "insert into pagodetransaccion (numtienda,fecha,numcaja,"
				+ "numtransaccion,codformadepago,monto,numdocumento,numcuenta,"
				+ "numconformacion,numreferencia,cedtitular,codbanco,correlativoitem,"
				+ "regactualizado,codautorizante) values ("
				+ venta.getCodTienda()
				+ ",'"
				+ fechaTransString
				+ "',"
				+ venta.getNumCajaFinaliza()
				+ ", "
				+ venta.getNumTransaccion()
				+ ",'"
				+ pagoActual.getFormaPago().getCodigo()
				+ "',"
				+ pagoActual.getMonto()
				+ ","
				+ numDocumento
				+ ","
				+ numCuenta
				+ ","
				+ numConf
				+ ","
				+ numReferencia
				+ ","
				+ cedTitular
				+ ","
				+ codBanco
				+ ","
				+ correlativoItem
				+ ",'"
				+ Sesion.NO
				+ "', "
				+ codAutorizante + ")";
		loteSentenciasCR.addBatch(sentenciaSQLpago);*/
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#registrarPago(java.sql.Statement,
	 *      com.becoblohm.cr.manejarservicio.Abono,
	 *      com.becoblohm.cr.extensiones.impl.manejarpago.Pago, int, int)
	 */
	protected void registrarPago(Statement loteSentenciasCR, Abono ab,
			int numServ, Pago pagoActual, int correlativoItem)
			throws SQLException {
/*		String fechaAbonoString = Control.FECHA_FORMAT.format(ab
				.getFechaAbono());
		String codBanco = (pagoActual.getCodBanco() != null) ? "'"
				+ pagoActual.getCodBanco() + "'" : "null";
		String numDocumento = (pagoActual.getNumDocumento() != null) ? "'"
				+ pagoActual.getNumDocumento() + "'" : "null";
		String numCuenta = (pagoActual.getNumCuenta() != null) ? "'"
				+ pagoActual.getNumCuenta() + "'" : "null";
		String numConf = (pagoActual.getNumConformacion() != null) ? ""
				+ pagoActual.getNumConformacion() : "null";
		String numReferencia = (pagoActual.getNumReferencia() != 0) ? ""
				+ pagoActual.getNumReferencia() : "null";
		String cedTitular = (pagoActual.getCedTitular() != null) ? "'"
				+ pagoActual.getCedTitular() + "'" : "null";

		String sentenciaSQL = "insert into pagodeabonos (numtienda,numcaja,numabono,numservicio,fecha,codformadepago,codbanco,monto,numdocumento"
				+ ",numcuenta,numconformacion,numreferencia,cedtitular,correlativoitem,regactualizado) values ("
				+ ab.getTienda()
				+ ", "
				+ ab.getCaja()
				+ ", "
				+ ab.getNumAbono()
				+ ", "
				+ numServ
				+ ", "
				+ "'"
				+ fechaAbonoString
				+ "', "
				+ "'"
				+ pagoActual.getFormaPago().getCodigo()
				+ "', "
				+ codBanco
				+ ", "
				+ pagoActual.getMonto()
				+ ", "
				+ numDocumento
				+ ", "
				+ numCuenta
				+ ", "
				+ numConf
				+ ", "
				+ numReferencia
				+ ", "
				+ cedTitular
				+ ", "
				+ correlativoItem
				+ ", "
				+ "'"
				+ Sesion.NO
				+ "')";
		loteSentenciasCR.addBatch(sentenciaSQL);*/
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#registrarPagos(com.becoblohm.cr.manejarservicio.Abono,
	 *      java.sql.Statement, int)
	 */
	protected void registrarPagos(Statement loteSentenciasCR, Abono ab,
			int numServ) throws SQLException {
		// Para cada forma de pago en el abono
/*		for (int i = 0; i < ab.getPagos().size(); i++) {
			Pago pagoActual = (Pago) ab.getPagos().elementAt(i);
			int correlativoItem = (i + 1);
			this.registrarPago(loteSentenciasCR, ab, numServ, pagoActual,
					correlativoItem);
		}*/
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#registrarPago(java.sql.Statement,
	 *      com.becoblohm.cr.manejarservicio.Abono,
	 *      com.becoblohm.cr.extensiones.impl.manejarpago.Pago, int, int)
	 */
	protected void registrarPago(Statement loteSentencias, ListaRegalos lista,
			int numOperacion, Pago pago, int correlativoItem, double vuelto)
			throws SQLException {
/*				String sentenciaSQL = "insert into detalleoperacionlistaregalos (numoperacion,"
				+ "codlista,codformadepago,monto,montovuelto,correlativo) values ("
				+ numOperacion + ", "
				+ lista.getNumServicio() + ", "
				+ "'" + pago.getFormaPago().getCodigo() + "', " +
				+ pago.getMonto() + ", "
				+ vuelto + ", "
				+ correlativoItem + ")";
			
				loteSentencias.addBatch(sentenciaSQL);*/
	}

	/*
	 * (non-Javadoc)
	 */
	protected void registrarPagos(Statement loteSentenciasCR, ListaRegalos lista,
		int numOperacion, double vuelto) throws SQLException {
/*		// Para cada forma de pago de la lista de regalos
		Vector pagos = lista.getPagosAbono();
		for(int j=0;j<pagos.size();j++){
			Pago pago = (Pago)pagos.get(j);
			this.registrarPago(loteSentenciasCR, lista, numOperacion, pago, (j+1), vuelto);
		}*/
	}

	/*
	 * (non-Javadoc)
	 */
/*	protected void registrarPagosLRRemota(ListaRegalos lista, IXMLElement solicitud, double vuelto) throws SQLException {
		// Para cada forma de pago de la lista de regalos
		IXMLElement pagos = solicitud.createElement("pagos");
		solicitud.addChild(pagos);
						
		Vector vectorPagos = lista.getPagosAbono();
		for(int j=0;j<vectorPagos.size();j++){
			Pago pago = (Pago)vectorPagos.get(j);
					
			IXMLElement codformadepago = pagos.createElement("codformadepago");
			pagos.addChild(codformadepago);
			codformadepago.setContent(pago.getFormaPago().getCodigo());
					
			IXMLElement monto = pagos.createElement("monto");
			pagos.addChild(monto);
			monto.setContent(String.valueOf(pago.getMonto()));
				
			IXMLElement vueltoXML = pagos.createElement("vuelto");
			pagos.addChild(vueltoXML);
			vueltoXML.setContent(String.valueOf(vuelto));

			IXMLElement correlativo = pagos.createElement("correlativo");
			pagos.addChild(correlativo);
			correlativo.setContent(String.valueOf(j+1));
		}
	}*/
}
