/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.tienda
 * Programa   : Caja.java
 * Creado por : gmartinelli
 * Creado en  : 06-oct-03 14:00:43
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1.1
 * Fecha       : 25/05/2004 01:27:34 PM
 * Analista    : GMARTINELLI
 * Descripción : - Agregada la condición: 
 *					usuarioLogueado.equals("null") => usuarioLogueado = new String("")
 *				al método setUsuarioLogueado.
 * =============================================================================
 * Versión     : 1.1.0
 * Fecha       : 25/05/2004 01:27:34 PM
 * Analista    : GMARTINELLI
 * Descripción : - Se crearon los métodos recuperarNumTrans(), getNuevoNumNoFiscal(), 
 * 				incrementarNumNoFiscal(). 
 * =============================================================================
 */
package com.becoblohm.cr.manejadorsesion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.manejadorauditoria.Auditoria;
import com.becoblohm.cr.mediadoresbd.Conexiones;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.utiles.MathUtil;

/** 
 * Descripción: 
 * 		Esta clase contiene los atributos propios de las Cajas Registradoras, en
 * esta clase se trabajan los valores de los numeros de registros, transacciones,
 * documentos no fiscales, reportes Z, etc.
 */
public class Caja {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Caja.class);

	private int numero;
	private String estado;
	private String nombreEstado;
	private String serial;
	private int numTransaccion;
	private int numRegistro;
	private int numSeqMerchant;
	private Date fechaUltRepZ;
	private char cierreCajero;
	private String codUsuario;
	private double montoRecaudado;
	private String ip;
	private boolean numTrBloqueado = false;
	
	/**
	 * Numtransaccion para ventas de bono regalo
	 */
	private int numTransaccionBR;
	
	
	/**
	 * Constructor para Caja.
	 * @param numCaja Numero de caja en la tienda.
	 * @param edoCaja Identificador del estado donde se encuentra la caja.
	 * @param nombEdo Nombre del estado en el que se encuentra la caja.
	 * @param numTrans Ultima transaccion fiscal realizada.
	 * @param numNoFiscal Ultimo documento no fiscal emitido.
	 * @param numReg Numero de operaciones realizadas.
	 * @param numSeqMerchant Numero de secuencia utilizadas por Merchant.
	 * @param fechaUltRepZ Fecha del ultimo reporte Z.
	 */
	Caja(int numCaja, String edoCaja, String nombEdo, String serial, int numTrans, int numReg, 
			int numSeqMerchant, Date fechaUltRepZ) {
		this.numero = numCaja;
		this.estado = edoCaja;
		this.nombreEstado = nombEdo;
		this.serial = serial;
		this.numTransaccion = numTrans;
		this.numRegistro = numReg;
		this.numSeqMerchant = numSeqMerchant;
		this.fechaUltRepZ = fechaUltRepZ;
		this.ip = Conexiones.getIPCaja();
	}
	
	/**
	 * Incrementa el numero de registro de la caja registradora y acutaliza el registro en la BD.
	 * @return int - Numero del ultimo registro (Venta, Anulacion, Devolucion, Servicio, etc) iniciado por la Caja
	 * @throws BaseDeDatosExcepcion Si ocurre un error con la conexion con la Base de Datos.
	 */
	private int incrementarNumReg() throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("incrementarNumReg() - start");
		}

		numRegistro++;
		MediadorBD.actualizarRegistroCaja(numRegistro);

		if (logger.isDebugEnabled()) {
			logger.debug("incrementarNumReg() - end");
		}
		return numRegistro;
	}
	
	/**
	 * Incrementa el numero de transaccion realizada por la caja y actualiza dicho numero en la BD.
	 * @return int - Numero de la ultima transaccion realizada por la caja.
	 * @throws BaseDeDatosExcepcion Si ocurre un error con la conexion con la Base de Datos.
	 */
	private int incrementarNumTrans() throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("incrementarNumTrans() - start");
		}

		numTransaccion++;
		MediadorBD.actualizarTransaccionCaja(numTransaccion);

		if (logger.isDebugEnabled()) {
			logger.debug("incrementarNumTrans() - end");
		}
		return numTransaccion;
	}

	/**
	 * Incrementa el numero de transaccion realizada por la caja y actualiza dicho numero en la BD.
	 * @return int - Numero de la ultima transaccion realizada por la caja.
	 * @throws BaseDeDatosExcepcion Si ocurre un error con la conexion con la Base de Datos.
	 */
	private int incrementarNumServ(String tipoServicio) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("incrementarNumServ(String) - start");
		}

		int numNoFiscal = MediadorBD.obtenerNumServicioCajaCentral(tipoServicio);

		if (logger.isDebugEnabled()) {
			logger.debug("incrementarNumServ(String) - end");
		}
		return numNoFiscal;
	}

	/**
	 * Retorna el identificador del estado actual de la Caja.
	 * @return String - Identificador del Estado
	 */
	public String getEstado() {
		if (logger.isDebugEnabled()) {
			logger.debug("getEstado() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getEstado() - end");
		}
		return estado;
	}

	/**
	 * Retorna el nombre del estado actual de la Caja.
	 * @return String - Nombre del Estado
	 */
	public String getNombreEstado() {
		if (logger.isDebugEnabled()) {
			logger.debug("getNombreEstado() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getNombreEstado() - end");
		}
		return nombreEstado;
	}

	/**
	 * Obtiene el nuevo numero de registro para asignar a las operaciones.
	 * @return int - Nuevo numero de registro.
	 * @throws BaseDeDatosExcepcion Si ocurre un error con la conexion con la Base de Datos.
	 */
	public int getNuevoNumRegCaja() throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("getNuevoNumRegCaja() - start");
		}

		incrementarNumReg();

		if (logger.isDebugEnabled()) {
			logger.debug("getNuevoNumRegCaja() - end");
		}
		return numRegistro;
	}
	
	/**
	 * Obtiene el nuevo numero de transacci&oacute;n para asignar a la operaci&oacute;n.
	 * Si se usa el esquema de impresi&oacute;n fiscal, este m&eacute;todo no compromete el 
	 * n&uacute;mero de transacci&oacute;n hasta que se llama al m&eacute;todo commitNumtransaccion().
	 * 
	 * El bloqueo del n&uacute;mero de transacci&oacute;n puede ser liberado con el m&eacute;todo
	 * rollbackNumTransaccion() sin afectar el registro de n&uacute;meros de transacci&oacute;n
	 * @return int - Nuevo numero de transacci&oacute;n.
	 * @throws BaseDeDatosExcepcion Si ocurre un error con la conexi&oacute;n con la Base de Datos.
	 */
	public int getNuevoNumTransaccion() throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("getNuevoNumTransaccion() - start");
		}
		
		int result = 0;
		
		if (Sesion.impresoraFiscal) {
			if (!numTrBloqueado) {
				result = numTransaccion + 1;
				numTrBloqueado = true;
			} else {
				if (logger.isDebugEnabled()) {
					logger
							.error("getNuevoNumTransaccion() - Estado ilegal - Un número de transacción se encuentra bloqueado en este momento");
				}

				throw new IllegalStateException("Ya se ha bloqueado un numero " +
						"de transacción");
			}
		} else {
			incrementarNumTrans();
			result = numTransaccion;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getNuevoNumTransaccion() - end");
		}
		return result;
	}
	
	/**
	 * Compromete el n&uacute;mero de transacci&oacute;n bloqueado en el esquema de
	 * impresi&oacute;n fiscal por el m&eacute;todo getNuevoNumTransaccion()
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	public void commitNumTransaccion() throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("commitNumTransaccion() - start");
		}

		if (Sesion.impresoraFiscal && numTrBloqueado) {
			incrementarNumTrans();
			numTrBloqueado = false;
		} else {
			if (Sesion.impresoraFiscal) {
				logger
						.error(
								"commitNumTransaccion() - Estado ilegal - No se ha bloqueado previamente un número de transacción",
								null);
			throw new IllegalStateException("No se ha bloquedo un número de " +
						"transacción");
			} else {
				if (logger.isDebugEnabled()) {
					logger
							.error("commitNumTransaccion() - Estado ilegal - No hay impresora fiscal en uso");
				}

				throw new IllegalStateException("Método sólo usado para esquema " +
						"de impresora fiscal");
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("commitNumTransaccion() - end");
		}
	}
	/**
	 * Deshace el bloqueo del n&uacute;mero de transacci&oacute;n en el esquema de
	 * impresi&oacute;n fiscal realizado previamente por el m&eacute;todo 
	 * getNuevoNumTransaccion()
	 * 
	 */
	public void rollbackNumTransaccion() {
		if (logger.isDebugEnabled()) {
			logger.debug("rollbackNumTransaccion() - start");
		}

		numTrBloqueado = false;

		if (logger.isDebugEnabled()) {
			logger.debug("rollbackNumTransaccion() - end");
		}
	}
	
	/**
	 * Obtiene el nuevo numero de transaccion para asignar a la operacion.
	 * @return int - Nuevo numero de transaccion.
	 * @throws BaseDeDatosExcepcion Si ocurre un error con la conexion con la Base de Datos.
	 */
	public int getNuevoNumServicio(String tipoServicio) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("getNuevoNumServicio(String) - start");
		}

		int returnint = incrementarNumServ(tipoServicio);
		if (logger.isDebugEnabled()) {
			logger.debug("getNuevoNumServicio(String) - end");
		}
		return returnint;
	}

	/**
	 * Obtiene el numero de la Caja.
	 * @return int - Numero de Caja en la tienda.
	 */
	public int getNumero() {
		if (logger.isDebugEnabled()) {
			logger.debug("getNumero() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getNumero() - end");
		}
		return numero;
	}
	
	/**
	 * Obtiene el numero de registro de la caja registradora.
	 * @return int - Numero de registro actual.
	 */
	public int getNumRegistro() {
		if (logger.isDebugEnabled()) {
			logger.debug("getNumRegistro() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getNumRegistro() - end");
		}
		return numRegistro;
	}
	
	/**
	 * Utilizado para recuperar el numero anterior de registro cuando una operacion se inicia incorrectamente. 
	 * Ejemplo si en estado Iniciada se introduce un producto incorrecto.
	 * @throws BaseDeDatosExcepcion Si ocurre un error con la conexion con la Base de Datos.
	 */
	public void recuperarNumReg() throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("recuperarNumReg() - start");
		}

		numRegistro--;
		MediadorBD.actualizarRegistroCaja(numRegistro);

		if (logger.isDebugEnabled()) {
			logger.debug("recuperarNumReg() - end");
		}
	}
	
	/**
	 * Utilizado para recuperar el numero anterior de transacciòn cuando ocurre un error y se requierer reversar
	 * el número de transacción asignado a la caja 
	 * Ejemplo si al hacer un insert o update de una transacciòn y ocurre un error.
	 * @throws BaseDeDatosExcepcion Si ocurre un error con la conexion con la Base de Datos.
	 */
	public void recuperarNumTrans() throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("recuperarNumTrans() - start");
		}
		
		if (Sesion.impresoraFiscal) {
			rollbackNumTransaccion();
		} else {
			numTransaccion --;
			MediadorBD.actualizarTransaccionCaja (numTransaccion);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("recuperarNumTrans() - end");
		}
	}
	
	/**
	 * Asigna un nuevo identificador de estado a la Caja Registradora. Actualiza el atributo 
	 * nombreEstado con el nombre que corresponde al nuevo identificador.
	 * @param estado Nuevo identificador de estado de Caja.
	 * @throws BaseDeDatosExcepcion Si se asigna un estado con identificador invalido u ocurre 
	 * un falla con la conexion de la base de datos
	 */
	public void setEstado(String estado) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("setEstado(String) - start");
		}

		try {
			this.estado = estado;
			this.nombreEstado = MediadorBD.obtenerNombreEstado(estado);
			MediadorBD.setEstadoCaja(estado);
			if(CR.me != null)
				CR.me.mostrarEstadoCaja(nombreEstado);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("setEstado(String)", e);

			Auditoria.registrarAuditoria("Falla acceso a BD al actualizar estado de la caja", 'E');
		} catch (ConexionExcepcion e) {
			logger.error("setEstado(String)", e);

			Auditoria.registrarAuditoria("Falla conexión a BD al actualizar estado de la caja", 'E');
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setEstado(String) - end");
		}
	}

	/**
	 * Establece el número identificador de la caja.
	 * @param numero - Número entero a establecer
	 */
	public void setNumero(int numero) {
		if (logger.isDebugEnabled()) {
			logger.debug("setNumero(int) - start");
		}

		this.numero = numero;

		if (logger.isDebugEnabled()) {
			logger.debug("setNumero(int) - end");
		}
	}

	/**
	 * Método getSerial
	 * 
	 * @return
	 * String
	 */
	public String getSerial() {
		if (logger.isDebugEnabled()) {
			logger.debug("getSerial() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getSerial() - end");
		}
		return serial;
	}

	/**
	 * Método getFechaUltRepZ
	 * 
	 * @return
	 * Date
	 */
	public Date getFechaUltRepZ() {
		if (logger.isDebugEnabled()) {
			logger.debug("getFechaUltRepZ() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getFechaUltRepZ() - end");
		}
		return fechaUltRepZ;
	}

	/**
	 * Método setCierreCajero
	 * 
	 * @param cierre
	 */
	public void setCierreCajero(char cierre) {
		if (logger.isDebugEnabled()) {
			logger.debug("setCierreCajero(char) - start");
		}

		try {
			cierreCajero = cierre;
			MediadorBD.setCierreCajero(cierre);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("setCierreCajero(char)", e);

			Auditoria.registrarAuditoria("Falla acceso a BD al actualizar cierreCajero de CR", 'E');
		} catch (ConexionExcepcion e) {
			logger.error("setCierreCajero(char)", e);

			Auditoria.registrarAuditoria("Falla conexión a BD al actualizar cierreCajero de CR", 'E');
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setCierreCajero(char) - end");
		}
	}

	/**
	 * Método getUsuarioLogueado
	 * 
	 * @return String
	 */
	public String getUsuarioLogueado() {
		if (logger.isDebugEnabled()) {
			logger.debug("getUsuarioLogueado() - start");
		}

		ResultSet datosCaja = null;
		try {
			datosCaja = MediadorBD.obtenerDatosCaja(true);
			setUsuarioLogueado(datosCaja.getString("codusuario"));			
		} catch (BaseDeDatosExcepcion e) {
			logger.error("getUsuarioLogueado()", e);

			Auditoria.registrarAuditoria("Falla acceso a BD al recuperar codusuario de CR", 'E');
		} catch (ConexionExcepcion e) {
			logger.error("getUsuarioLogueado()", e);

			Auditoria.registrarAuditoria("Falla conexión a BD al recuperar codusuario de CR", 'E');
		} catch (SQLException e) {
			logger.error("getUsuarioLogueado()", e);

			Auditoria.registrarAuditoria("Falla sentencia a BD al recuperar codusuario de CR", 'E');
		} finally {
			if (datosCaja != null) {
				try {
					datosCaja.close();
				} catch (SQLException e1) {
					logger.error("getUsuarioLogueado()", e1);
				}
				datosCaja = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getUsuarioLogueado() - end");
		}
		return codUsuario;
	}

	/**
	 * Método setUsuarioLogueado
	 * 
	 * @param usuarioLogueado
	 */
	public void setUsuarioLogueado(String usuarioLogueado) {
		if (logger.isDebugEnabled()) {
			logger.debug("setUsuarioLogueado(String) - start");
		}

		try {
			codUsuario = usuarioLogueado;
			if(usuarioLogueado == null) usuarioLogueado = new String("");
			MediadorBD.setUsuarioLogueado(usuarioLogueado);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("setUsuarioLogueado(String)", e);

			Auditoria.registrarAuditoria("Falla acceso a BD al actualizar codusuario logueado", 'E');
		} catch (ConexionExcepcion e) {
			logger.error("setUsuarioLogueado(String)", e);

			Auditoria.registrarAuditoria("Falla conexión a BD al actualizar codusuario logueado", 'E');
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setUsuarioLogueado(String) - end");
		}
	}

	/**
	 * Método getNumTransaccion
	 * 
	 * @return int
	 */
	public int getUltimaTransaccion() {
		if (logger.isDebugEnabled()) {
			logger.debug("getUltimaTransaccion() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getUltimaTransaccion() - end");
		}
		return numTransaccion;
	}
	/**
	 * Método setFechaUltRepZ
	 * 
	 * @param fecha
	 */
	public void setFechaUltRepZ(Date fecha) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("setFechaUltRepZ(Date) - start");
		}

		fechaUltRepZ = fecha;
		MediadorBD.setFechaUltReporteZ(fecha);

		if (logger.isDebugEnabled()) {
			logger.debug("setFechaUltRepZ(Date) - end");
		}
	}

	/**
	 * Método getMontoRecaudado
	 * 
	 * @return double
	 */
	public double getMontoRecaudado() {
		if (logger.isDebugEnabled()) {
			logger.debug("getMontoRecaudado() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getMontoRecaudado() - end");
		}
		return montoRecaudado;
	}
	/**
	 * Método setMontoRecaudado
	 * 
	 * @param d
	 */
	public void setMontoRecaudado(double d) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("setMontoRecaudado(double) - start");
		}

		montoRecaudado = d;
		MediadorBD.actualizarMontoRecaudadoCaja(this.montoRecaudado, this.numero);

		if (logger.isDebugEnabled()) {
			logger.debug("setMontoRecaudado(double) - end");
		}
	}

	/**
	 * Método incrementarMontoRecaudado
	 * 
	 * @param montoNuevoPago
	 */
	public void incrementarMontoRecaudado(double montoNuevoPago) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("incrementarMontoRecaudado(double) - start");
		}

		this.montoRecaudado += montoNuevoPago;
		this.montoRecaudado = MathUtil.roundDouble(this.montoRecaudado);
		MediadorBD.actualizarMontoRecaudadoCaja(this.montoRecaudado, this.numero);

		if (logger.isDebugEnabled()) {
			logger.debug("incrementarMontoRecaudado(double) - end");
		}
	}

	/**
	 * Método disminuirMontoRecaudado
	 * 
	 * @param montoNuevoPago
	 */
	public void disminuirMontoRecaudado(double montoNuevoPago) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("disminuirMontoRecaudado(double) - start");
		}

		this.montoRecaudado -= montoNuevoPago;
		this.montoRecaudado = MathUtil.roundDouble(this.montoRecaudado);		
		MediadorBD.actualizarMontoRecaudadoCaja(this.montoRecaudado, this.numero);

		if (logger.isDebugEnabled()) {
			logger.debug("disminuirMontoRecaudado(double) - end");
		}
	}
	/**
	 * Método setSerial
	 * 
	 * @param string
	 */
	public void setSerial(String serialFiscal) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("setSerial(String) - start");
		}

		serial = serialFiscal;
		MediadorBD.setSerialCaja(serialFiscal, this.numero);

		if (logger.isDebugEnabled()) {
			logger.debug("setSerial(String) - end");
		}
	}
	/**
	 * Método getNumTransaccion
	 * 
	 * @return
	 * int
	 */
	public int getNumTransaccion() {
		if (logger.isDebugEnabled()) {
			logger.debug("getNumTransaccion() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getNumTransaccion() - end");
		}
		return numTransaccion;
	}

	/**
	 * @return Devuelve ip.
	 */
	public String getIp() {
		return ip;
	}
	
    /**
     * @return Returns el numSeqMerchant.
     */
    public int getNumSeqMerchant() {
        return this.numSeqMerchant;
    }
	
    /**
     * @param _numSeqMerchant
     *            El numSeqMerchant a asignar.
     */
    public void setNumSeqMerchant(int _numSeqMerchant) {
        this.numSeqMerchant = _numSeqMerchant;
    }
    
    /**
     * Guarda el numero de secuencia actual de merchant en la base de datos.
     * @throws ConexionExcepcion
     * @throws BaseDeDatosExcepcion
     */
    public void actualizarNumSeqMerchant() throws BaseDeDatosExcepcion, ConexionExcepcion {
        MediadorBD.actualizarNumSeqMerchantCaja(this.numSeqMerchant);
    }
   
    
	/**
	 * Obtiene el nuevo numero de transacci&oacute;n para asignar a la operaci&oacute;n de venta de Bonos Regalo.
	 * Si se usa el esquema de impresi&oacute;n fiscal, este m&eacute;todo no compromete el 
	 * n&uacute;mero de transacci&oacute;n hasta que se llama al m&eacute;todo commitNumtransaccion().
	 * 
	 * El bloqueo del n&uacute;mero de transacci&oacute;n puede ser liberado con el m&eacute;todo
	 * rollbackNumTransaccion() sin afectar el registro de n&uacute;meros de transacci&oacute;n
	 * @return int - Nuevo numero de transacci&oacute;n.
	 * @throws BaseDeDatosExcepcion Si ocurre un error con la conexi&oacute;n con la Base de Datos.
	 */
	public int getNuevoNumTransaccionBR() throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("getNuevoNumTransaccionBR() - start");
		}
		
		int result = 0;
		
		incrementarNumTransBR();
		result = numTransaccionBR;
		
		if (logger.isDebugEnabled()) {
			logger.debug("getNuevoNumTransaccionBR() - end");
		}
		return result;
	}

	public int getNumTransaccionBR() {
		return numTransaccionBR;
	}

	public void setNumTransaccionBR(int numTransaccionBR) {
		this.numTransaccionBR = numTransaccionBR;
	}
	
	private int incrementarNumTransBR() throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("incrementarNumTrans() - start");
		}

		numTransaccionBR++;
		MediadorBD.actualizarTransaccionBRCaja(numTransaccionBR);

		if (logger.isDebugEnabled()) {
			logger.debug("incrementarNumTrans() - end");
		}
		return numTransaccionBR;
	}

	public char getCierreCajero() {
		return cierreCajero;
	}
	
	public String getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(String codUsuario) {
		this.codUsuario = codUsuario;
	}
	
}
