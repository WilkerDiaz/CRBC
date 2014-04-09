/*
 * Creado el 19/10/2004
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.becoblohm.cr.manejarventa;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.ProductoExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.mediadoresbd.BaseDeDatosVenta;



/**
 * @author Arístides Castillo
 *
 * Clase SavedTransaccion
 * Implementa la clase abstracta Transaccion para recuperar la subclases de la misma que hayan
 * sido guardadas en la base de datos.
 * 
 */
public class SavedTransaccion extends Transaccion {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(SavedTransaccion.class);
	
	private boolean local;
	
	/**
	 * @ Constructor de la clase
	 * @param tda Número de tienda
	 * @param fechaT Fecha de la transacción
	 * @param caja Número de caja 
	 * @param numTransaccion Número de la transacción
	 * @param local Indica si la transacción debe ser recuperada de la BD local
	 */
	public SavedTransaccion(int tda, Date fechaT, int caja, int numTransaccion, boolean local) throws BaseDeDatosExcepcion, ConexionExcepcion {
		super();
		String fechaSQL = getFechaSQL(fechaT);
		Vector cabecera = BaseDeDatosVenta.obtenerTransaccion(tda, fechaSQL, caja, numTransaccion, local); 
		this.local = local;
		// Armar cabecera de la anulacion
		codTienda = ((Integer)cabecera.elementAt(0)).intValue();
		fechaTrans = (Date)cabecera.elementAt(1);
		numCajaInicia = ((Integer)cabecera.elementAt(2)).intValue();
		numRegCajaInicia = ((Integer)cabecera.elementAt(3)).intValue();
		numCajaFinaliza = ((Integer)cabecera.elementAt(4)).intValue();
		this.numTransaccion = ((Integer)cabecera.elementAt(5)).intValue();
		tipoTransaccion = (((String)cabecera.elementAt(6)).toCharArray())[0];
		horaInicia = (Time)cabecera.elementAt(7);
		horaFin = (Time)cabecera.elementAt(8);
		cliente = new Cliente((String)cabecera.elementAt(9));
		codCajero = (String)cabecera.elementAt(10);
		montoBase = ((Double)cabecera.elementAt(11)).doubleValue();
		montoImpuesto = ((Double)cabecera.elementAt(12)).doubleValue();
		montoVuelto = ((Double)cabecera.elementAt(13)).doubleValue();
		montoRemanente = ((Double)cabecera.elementAt(14)).doubleValue();
		lineasFacturacion = ((Integer)cabecera.elementAt(15)).intValue();
		checksum = ((Integer)cabecera.elementAt(16)).intValue();
		estadoTransaccion = ((String)cabecera.elementAt(17)).toCharArray()[0];
	}
	
	public Venta getVenta() throws ClassCastException {
		if (logger.isDebugEnabled()) {
			logger.debug("getVenta() - start");
		}

		Venta v = null;
		try {
			v = new Venta(codTienda, getFechaSQL(), numCajaFinaliza, numTransaccion, local);
		} catch (BaseDeDatosExcepcion bde) {
			logger.error("getVenta()", bde);
		} catch (ConexionExcepcion ce) {
			logger.error("getVenta()", ce);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getVenta() - end");
		}
		return v;
	}
	
	public Devolucion getDevolucion() throws ClassCastException {
		if (logger.isDebugEnabled()) {
			logger.debug("getDevolucion() - start");
		}

		Devolucion d = null;
		try {
			d = new Devolucion(codTienda, getFechaSQL(), numCajaFinaliza, numTransaccion, local);
		} catch (BaseDeDatosExcepcion bde) {
			logger.error("getDevolucion()", bde);
		} catch (ConexionExcepcion ce) {
			logger.error("getDevolucion()", ce);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDevolucion() - end");
		}
		return d;		
	}
		
	/*public Anulacion getAnulacion() throws ClassCastException {
		if (logger.isDebugEnabled()) {
			logger.debug("getAnulacion() - start");
		}

		Anulacion a = null;
		try {
			a = new Anulacion(codTienda, getFechaSQL(), numCajaFinaliza, numTransaccion, local);
		} catch (BaseDeDatosExcepcion bde) {
			logger.error("getAnulacion()", bde);
		} catch (ConexionExcepcion ce) {
			logger.error("getAnulacion()", ce);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getAnulacion() - end");
		}
		return a;
	}
	
	/* (no Javadoc)
	 * @see com.becoblohm.cr.manejarventa.Transaccion#anularProducto(int)
	 */
	protected void anularProducto(int renglon)
		throws
			ProductoExcepcion,
			XmlExcepcion,
			FuncionExcepcion,
			BaseDeDatosExcepcion,
			ConexionExcepcion {

		throw new FuncionExcepcion("Método no permitido en clase");

	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.manejarventa.Transaccion#actualizarPreciosDetalle(com.becoblohm.cr.manejarventa.Producto)
	 */
	protected void actualizarPreciosDetalle(Producto prod)
		throws
			XmlExcepcion,
			FuncionExcepcion,
			BaseDeDatosExcepcion,
			ConexionExcepcion {

		throw new FuncionExcepcion("Método no permitido en clase");

	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.manejarventa.Transaccion#finalizarTransaccion()
	 */
	protected void finalizarTransaccion()
		throws
			ConexionExcepcion,
			XmlExcepcion,
			FuncionExcepcion,
			BaseDeDatosExcepcion {

		throw new FuncionExcepcion("Método no permitido en clase");

	}
	/**
	 * Metodo usado para generar la cadena de fecha SQL a partir de la fecha de la transacción
	 * @return Cadena de fecha en formato SQL
	 */
	private String getFechaSQL() {
		if (logger.isDebugEnabled()) {
			logger.debug("getFechaSQL() - start");
		}

		String returnString = getFechaSQL(fechaTrans);
		if (logger.isDebugEnabled()) {
			logger.debug("getFechaSQL() - end");
		}
		return returnString;
	}
	
	public static String getFechaSQL(Date d) {
		if (logger.isDebugEnabled()) {
			logger.debug("getFechaSQL(Date) - start");
		}

		SimpleDateFormat df = (SimpleDateFormat)DateFormat.getDateInstance();
		df.applyPattern("yyyy-MM-dd");
		String returnString = df.format(d);
		if (logger.isDebugEnabled()) {
			logger.debug("getFechaSQL(Date) - end");
		}
		return returnString;
	}
	
	public static Vector getTransacciones(int tda, int caja, Date fecha, String codCajero) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("getTransacciones(int, int, Date, String) - start");
		}

		Vector result = new Vector();
		String fechaStr = getFechaSQL(fecha);
		Vector trans = BaseDeDatosVenta.obtenerTransacciones(tda, codCajero, caja, fechaStr);
		for (Iterator i = trans.iterator(); i.hasNext();) {
			int numTrans = ((Integer)i.next()).intValue();
			result.add(new SavedTransaccion(tda, fecha, caja, numTrans, true));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getTransacciones(int, int, Date, String) - end");
		}
		return result;
	}

	/* (sin Javadoc)
	 * @see com.becoblohm.cr.manejarventa.Transaccion#commitTransaccion()
	 * 
	 * @since 20-abr-2005
	 */
	public void commitTransaccion() {
		throw new IllegalStateException("Método no permitido en clase");

	}
	/* (sin Javadoc)
	 * @see com.becoblohm.cr.manejarventa.Transaccion#rollbackTransaccion()
	 * 
	 * @since 20-abr-2005
	 */
	public void rollbackTransaccion() {
		throw new IllegalStateException("Método no permitido en clase");

	}
}
