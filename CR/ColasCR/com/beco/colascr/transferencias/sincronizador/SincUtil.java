/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.colascr.transferencias.sincronizador
 * Programa   : SincUtil.java
 * Creado por : Ileana Rojas, Gabriel Martinelli
 * Creado en  : 21/02/2005 04:23:59 PM
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
package com.beco.colascr.transferencias.sincronizador;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.beco.colascr.transferencias.comunicacionbd.BeansSincronizador;
import com.beco.colascr.transferencias.excepciones.BaseDeDatosExcepcion;

public class SincUtil {
	
	private static SincUtil instance;
	
	protected SincUtil() {
		super();
	}
	
	public static SincUtil getInstance() {
		if (instance == null) {
			instance = new SincUtil();
		}
		return instance;
	}
	
	/**
	 * Método syncTransaccion
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	private void syncTransaccion() throws BaseDeDatosExcepcion, SQLException {
		ResultSet transaccionesCR = null;
		try {
			transaccionesCR = BeansSincronizador.getTransacciones(true, true);
			//transaccionesCR.last();
			transaccionesCR.beforeFirst();
			BeansSincronizador.syncTransacciones(transaccionesCR);
			//nueva llamada a nueva funcion CRM wdiaz
			BeansSincronizador.syncTransaccionafiliadocrm();
			//fin
		} finally {
			if (transaccionesCR != null) {
				transaccionesCR.close();
				transaccionesCR = null;
			}
		}
	}

	/**
	 * Método syncAbono
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	private void syncAbono() throws SQLException, BaseDeDatosExcepcion {
		ResultSet abonosCR = null;
		try {
			abonosCR = BeansSincronizador.getAbonos(true, true);
			abonosCR.last();
			abonosCR.beforeFirst();
			BeansSincronizador.syncAbonos(abonosCR);
		} finally {
			if (abonosCR != null) {
				abonosCR.close();
				abonosCR = null;
			}
		}
	}
	
	/**
	 * Método syncAnulacionAbono
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	private void syncAnulacionAbono() throws SQLException, BaseDeDatosExcepcion {
		ResultSet anulacionAbonosCR = null;
		try {
			anulacionAbonosCR = BeansSincronizador.getAnulacionAbonos(true, true);
			anulacionAbonosCR.last();
			anulacionAbonosCR.beforeFirst();
			BeansSincronizador.syncAnulacionAbonos(anulacionAbonosCR);
		} finally {
			if (anulacionAbonosCR != null) {
				anulacionAbonosCR.close();
				anulacionAbonosCR = null;
			}
		}
	}
	
	public synchronized void syncTransacciones() {
		try {
			syncTransaccion();
		} catch (BaseDeDatosExcepcion e) {
		} catch (SQLException e) {
		}
	}
	
	public synchronized void syncAbonos() {
		try {
			syncAbono();
		} catch (BaseDeDatosExcepcion e) {
		} catch (SQLException e) {
		}
	}
	
	public synchronized void syncAnulacionAbonos() {
		try {
			syncAnulacionAbono();
		} catch (BaseDeDatosExcepcion e) {
		} catch (SQLException e) {
		}
	}
}
