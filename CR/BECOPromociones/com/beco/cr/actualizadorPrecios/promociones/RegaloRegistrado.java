package com.beco.cr.actualizadorPrecios.promociones;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.mediadoresbd.Conexiones;

public class RegaloRegistrado {

	private double cantidad;
	private int numTransaccion;
	private int numCajaFinaliza;
	private int codTienda;
	private Date fechaTrans;
	private int codPromocion;
	private int numdetalle;

	public RegaloRegistrado(double cantidad, int numTransaccion,
			int numCajaFinaliza, int codTienda, Date fechaTrans,
			int codPromocion, int numdetalle) {
		super();
		this.cantidad = cantidad;
		this.numTransaccion = numTransaccion;
		this.numCajaFinaliza = numCajaFinaliza;
		this.codTienda = codTienda;
		this.fechaTrans = fechaTrans;
		this.codPromocion = codPromocion;
		this.numdetalle = numdetalle;
	}

	/**
	 * @return el cantidad
	 */
	public double getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad
	 *            el cantidad a establecer
	 */
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return el codPromocion
	 */
	public int getCodPromocion() {
		return codPromocion;
	}

	/**
	 * @param codPromocion
	 *            el codPromocion a establecer
	 */
	public void setCodPromocion(int codPromocion) {
		this.codPromocion = codPromocion;
	}

	/**
	 * @return el codTienda
	 */
	public int getCodTienda() {
		return codTienda;
	}

	/**
	 * @param codTienda
	 *            el codTienda a establecer
	 */
	public void setCodTienda(int codTienda) {
		this.codTienda = codTienda;
	}

	/**
	 * @return el fechaTrans
	 */
	public Date getFechaTrans() {
		return fechaTrans;
	}

	/**
	 * @param fechaTrans
	 *            el fechaTrans a establecer
	 */
	public void setFechaTrans(Date fechaTrans) {
		this.fechaTrans = fechaTrans;
	}

	/**
	 * @return el numCajaFinaliza
	 */
	public int getNumCajaFinaliza() {
		return numCajaFinaliza;
	}

	/**
	 * @param numCajaFinaliza
	 *            el numCajaFinaliza a establecer
	 */
	public void setNumCajaFinaliza(int numCajaFinaliza) {
		this.numCajaFinaliza = numCajaFinaliza;
	}

	/**
	 * @return el numdetalle
	 */
	public int getNumdetalle() {
		return numdetalle;
	}

	/**
	 * @param numdetalle
	 *            el numdetalle a establecer
	 */
	public void setNumdetalle(int numdetalle) {
		this.numdetalle = numdetalle;
	}

	/**
	 * @return el numTransaccion
	 */
	public int getNumTransaccion() {
		return numTransaccion;
	}

	/**
	 * @param numTransaccion
	 *            el numTransaccion a establecer
	 */
	public void setNumTransaccion(int numTransaccion) {
		this.numTransaccion = numTransaccion;
	}

	/**
	 * Obtiene las promociones registradas en una determinada transacción
	 * 
	 * @param codTienda
	 * @param fechaTrans
	 * @param numCaja
	 * @param numTransaccion
	 * @param local
	 *            indica si la consulta se realiza en la caja o en el servidor
	 * @return Vector
	 */
	/*
	 * En esta función se realizaron modificaciones referentes a la migración a
	 * java 1.6 por jperez Sólo se parametrizó el tipo de dato contenido en los
	 * 'Vector' Fecha: agosto 2011
	 */
	public static Vector<RegaloRegistrado> consultarRegalosPorVenta(
			int codTienda, String fechaTrans, int numCaja, int numTransaccion,
			boolean local) {
		Vector<RegaloRegistrado> regalos = new Vector<RegaloRegistrado>();
		ResultSet result = null;
		String sentenciaSQL = "select * from regalosregistrados dr "
				+ " where dr.numTienda= " + codTienda + " and "
				+ " dr.fechaTransaccion = '" + fechaTrans + "' and "
				+ " dr.numCaja = " + numCaja + " and "
				+ " dr.numTransaccion = " + numTransaccion;
		try {
			result = Conexiones.realizarConsulta(sentenciaSQL, local);
			result.beforeFirst();
			while (result.next()) {
				RegaloRegistrado rr = new RegaloRegistrado(
						result.getDouble("cantidad"),
						result.getInt("numTransaccion"),
						result.getInt("numCaja"), result.getInt("numTienda"),
						result.getDate("fechaTransaccion"),
						result.getInt("codPromocion"),
						result.getInt("numDetalle"));
				regalos.addElement(rr);
			}

		} catch (SQLException e2) {
			// Nada, puede que la tabla no exista porque no se tiene la
			// extension de promociones
		} catch (BaseDeDatosExcepcion e) {
			e.printStackTrace();
		} catch (ConexionExcepcion e) {
			e.printStackTrace();
		} finally {
			try {
				result.close();
			} catch (SQLException e) {
			}
			result = null;
		}
		return regalos;
	}

}
