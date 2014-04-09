/*
 * $Id: DefaultCongeladorFactura.java,v 1.3 2005/03/16 18:56:13 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.extensiones.impl
 * Programa		: DefaultCongeladorFactura.java
 * Creado por	: Programa8
 * Creado en 	: 26-ene-2005 14:23:59
 * (C) Copyright 2005 SuperFerretería EPA C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisión	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: DefaultCongeladorFactura.java,v $
 * Revision 1.3  2005/03/16 18:56:13  programa8
 * Version 1.0 - Release Candidate 1
 * *- Log de errores en temp/errorsCR.log
 * *- Ajuste de excepciones en curso normal de inicio de aplicacion y repintado de factura
 * *- Reintento de obtención de comprobante fiscal
 * *- Ajuste en monto recaudado de caja
 *
 * Revision 1.2  2005/03/10 15:54:35  programa8
 * CR al 10/03/2005. Merging desde branch STABLE
 *
 * Revision 1.1.2.6  2005/03/07 13:22:24  programa8
 * Integración Versiones Estable e Inestable al 07/03/2005
 *
 * Revision 1.1.4.1  2005/02/28 18:39:17  programa8
 * Version Inestable al 28/02/2005
 *     *-Preparación para trabajar sin administrador de ventanas.
 *     *-Reordenamiento de GUI
 *     *-Mejoras en scroll y pantallas de pagos
 *     *- Mantenimiento de estado en Cliente en Espera
 *     *- Avisos del sistema manejados por la aplicacion
 *     *- Desbloqueo de caja por otros usuarios reparado.
 *
 * Revision 1.1.2.5  2005/02/22 18:18:52  programa4
 * *** empty log message ***
 *
 * Revision 1.1.2.4  2005/02/10 14:49:35  acastillo
 * Ajuste conexiones, resultsets & statements
 *
 * Revision 1.1.2.3  2005/02/02 20:23:12  acastillo
 * Limpieza de código. 0 trailing todo's
 *
 * Revision 1.1.2.2  2005/02/02 20:19:53  acastillo
 * Limpieza de código. 0 trailing todo's
 *
 * Revision 1.1.2.1  2005/01/28 19:56:37  acastillo
 * Caja Registradora EPA3 - CR RC3
 *
 * ===========================================================================
 */
package com.becoblohm.cr.extensiones.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.extensiones.ActualizadorPreciosFactory;
import com.becoblohm.cr.extensiones.CongeladorFactura;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarventa.CondicionVenta;
import com.becoblohm.cr.manejarventa.DetalleTransaccion;
import com.becoblohm.cr.manejarventa.Venta;
import com.becoblohm.cr.mediadoresbd.Conexiones;

/**
 * <pre>
 * Proyecto: CR 
 * Clase: DefaultCongeladorFactura
 * </pre>
 * <p>
 * <a href="DefaultCongeladorFactura.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - $Author: programa8 $
 * @version $Revision: 1.3 $ - $Date: 2005/03/16 18:56:13 $
 * @since 26-ene-2005
 * @
 */
public class DefaultCongeladorFactura implements CongeladorFactura {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(DefaultCongeladorFactura.class);

	/**
	 * @since 26-ene-2005
	 * 
	 */
	public DefaultCongeladorFactura() {
		super();
	}

	/* (sin Javadoc)
	 * @see com.becoblohm.cr.extensiones.CongeladorFactura#colocarEnEspera(com.becoblohm.cr.manejarventa.Venta, java.lang.String)
	 * @param venta
	 * @param identificacion
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 * @since 26-ene-2005
	 */
	public void colocarEnEspera(Venta venta, String identificacion)
			throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("colocarEnEspera(Venta, String) - start");
		}

		// Se chequea si no existe una factura en espera con el mismo codigo de identificacion
		// Si existe se lanza una excepcion
		String sentenciaSQL = "select count(*) from transaccion where transaccion.codigofacturaespera = '" + identificacion
							+ "' and transaccion.estadotransaccion = '" + Sesion.ESPERA + "'";
		try {
			ResultSet result = Conexiones.realizarConsulta(sentenciaSQL,false);
			if (result.first() && (result.getInt(1) > 0 ))
				throw (new BaseDeDatosExcepcion("Error al registrar factura en espera. Ya exite factura con codigo " + identificacion + " registrada en espera"));
			result.close();
			result = null;
		} catch (SQLException e) {
			logger.error("colocarEnEspera(Venta, String)", e);

			throw (new BaseDeDatosExcepcion("Error al registrar factura en espera", e));
		} 
		
		// Si no existen facturas en espera con este codigo la registramos
		Statement loteSentenciasCR = null;
		try {
			loteSentenciasCR = Conexiones.crearSentencia(false);
			SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy-MM-dd");
			String fechaTransString = fechaTrans.format(venta.getFechaTrans());
			String codCliente = (venta.getCliente().getCodCliente() != null)
								? "'" + venta.getCliente().getCodCliente() + "'"
								: "null";
			String codAutorizante = (venta.getCodAutorizante() != null)
								? "'" + venta.getCodAutorizante() + "'"
								: "null";
			sentenciaSQL = "insert into transaccion (numtienda,fecha,numcajainicia,numregcajainicia,numcajafinaliza,numtransaccion,tipotransaccion,"
				+ "horainicia,horafinaliza,codcliente,codcajero,codigofacturaespera,montobase,montoimpuesto,vueltocliente,montoremanente,"
				+"lineasfacturacion,duracionventa,checksum,estadotransaccion,regactualizado,codautorizante) "
				+ " values (" + venta.getCodTienda() + ", " 
				+ "'" + fechaTransString + "', " + venta.getNumCajaInicia() + ", "
				+ venta.getNumRegCajaInicia() + ", " + "null," + venta.getNumRegCajaInicia() + ", "
				+ "'" + venta.getTipoTransaccion() + "', " + "'" + venta.getHoraInicia() + "', "
				+ "'" + Sesion.getHoraSistema() + "', " 
				+ codCliente + ", '" //Código del Cliente
				+ venta.getCodCajero() + "', " + "'" + identificacion + "', " + venta.getMontoBase() + ", "
				+ venta.getMontoImpuesto() + ", " + venta.getMontoVuelto() + ", " + venta.getMontoRemanente() + ", "
				+ venta.getLineasFacturacion() + ", " + venta.getDuracion() + "," + venta.getChecksum() + ", "
				+ "'" + venta.getEstadoTransaccion() + "', '" + Sesion.NO + "', "+ 
				codAutorizante + ")";
			
			loteSentenciasCR.addBatch(sentenciaSQL);
			
			// Resgistramos los detalles de transaccion		
			for (int i=0; i<venta.getDetallesTransaccion().size(); i++) {
				String codVendedor = (((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCodVendedor() != null)
									? "'" + ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCodVendedor() + "'"
									: "null";
				String codSupervisor = (((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCodSupervisor() != null)
									? "'" + ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCodSupervisor() + "'"
									: "null";
				sentenciaSQL = "insert into detalletransaccion (numtienda,fecha,numcajainicia,numregcajainicia,codproducto,codcondicionventa,correlativoitem,numcajafinaliza,numtransaccion,cantidad,"
					+ "codvendedor,precioregular,codsupervisor,preciofinal,montoimpuesto,desctoempleado,despacharproducto,codpromocion,codtipocaptura,regactualizado)"
					+ " values (" + venta.getCodTienda() + ", " 
					+ "'" + fechaTransString + "', " + venta.getNumCajaInicia() + ", " 
					+ venta.getNumRegCajaInicia() + ", '" + ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getProducto().getCodProducto() + "', "
					+ "'" + ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCondicionVenta() + "', "
					+ (i+1) + ", " + "null, " + venta.getNumRegCajaInicia() + ", " + ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCantidad() + ", "
					+ codVendedor + ", " + ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getProducto().getPrecioRegular() + ", "
					+ codSupervisor + ", " + ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getPrecioFinal() + ", "
					+ ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getMontoImpuesto() + ", "
					+ ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getDctoEmpleado() + ", "
					+ "'" + Sesion.NO + "', ";
				if (((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCodPromocion() < 0) {
					sentenciaSQL += "null, ";
				} else {
					sentenciaSQL += ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCodPromocion() + ", ";
				}
				sentenciaSQL += "'" + ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getTipoCaptura() + "', "
					+ "'" + Sesion.NO + "')";
				loteSentenciasCR.addBatch(sentenciaSQL);
				
				//ACTUALIZACION CENTROBECO 19/12/2008: Módulo de promociones
				(new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().agregarInsertTransaccionCondiciones(venta, loteSentenciasCR, fechaTransString, sentenciaSQL, i);
			}
			
			// Ahora ejecutamos todas las sentencias de forma transaccional en el servidor de la tienda
			Conexiones.ejecutarLoteSentencias(loteSentenciasCR, false, true);
		} catch (SQLException e) {
			logger.error("colocarEnEspera(Venta, String)", e);

			throw (new BaseDeDatosExcepcion("Error en sentencia SQL al registrar la factura en espera en servidor"));
		} catch (ConexionExcepcion e) {
			logger.error("colocarEnEspera(Venta, String)", e);

			throw (new BaseDeDatosExcepcion("Error de conexión con BD al registrar la factura en espera en servidor"));
		} finally {
			if (loteSentenciasCR != null) {
				try {
					loteSentenciasCR.close();
				} catch (SQLException e1) {
					logger.error("colocarEnEspera(Venta, String)", e1);
				}
				loteSentenciasCR = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("colocarEnEspera(Venta, String) - end");
		}
	}

	/* (sin Javadoc)
	 * @see com.becoblohm.cr.extensiones.CongeladorFactura#recuperarDeEspera(java.lang.String)
	 * @param identificacion
	 * @return
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 * @since 26-ene-2005
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Vector<?>> recuperarDeEspera(String codFactEspera)
			throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("recuperarDeEspera(String) - start");
		}

		Vector<Vector<?>> resultado = new Vector<Vector<?>>();
		ResultSet result;
		
		// Armamos los vectores de la factura a recuperar
		String sentenciaSQL = "select * from transaccion where transaccion.codigofacturaespera = '" + codFactEspera 
							+ "' and transaccion.estadotransaccion = '" + Sesion.ESPERA + "'";
		try {
			result = Conexiones.realizarConsulta(sentenciaSQL,false);
		} catch (BaseDeDatosExcepcion e1) {
			logger.error("recuperarDeEspera(String)", e1);

			e1.setMensaje("Error al recuperar factura en espera. " + e1.getMensaje());
			throw  e1;
		}

		Vector<Object> cabecera = new Vector<Object>();
		int tienda = 0;
		String fechaTransString = "";
		int cajaInicia = 0;
		int regCajaInicia = 0;
		try {
			if (result.first()) {
				tienda = result.getInt("numtienda");
				fechaTransString = result.getString("fecha");
				cabecera.addElement(new Integer(tienda));
				cajaInicia = result.getInt("numcajainicia");
				cabecera.addElement(new Integer(cajaInicia));
				regCajaInicia = result.getInt("numregcajainicia");
				cabecera.addElement(new Integer(regCajaInicia));
				cabecera.addElement(result.getString("tipotransaccion"));
				cabecera.addElement(result.getTime("horainicia"));
				cabecera.addElement(result.getString("codcliente"));
				cabecera.addElement(new Double(result.getDouble("montobase")));
				cabecera.addElement(new Double(result.getDouble("montoimpuesto")));
				cabecera.addElement(new Double(result.getDouble("vueltocliente")));
				cabecera.addElement(new Double(result.getDouble("montoremanente")));
				cabecera.addElement(new Integer(result.getInt("lineasfacturacion")));
				cabecera.addElement(new Integer(result.getInt("checksum")));
				cabecera.addElement(new Long(result.getLong("duracionventa")));
				cabecera.addElement(result.getString("codautorizante"));
			} else {
				throw (new BaseDeDatosExcepcion("Error al recuperar factura en espera. No existe factura con codigo " + codFactEspera));
			}
		} catch (SQLException e2) {
			logger.error("recuperarDeEspera(String)", e2);

			throw (new BaseDeDatosExcepcion("Error al recuperar factura en espera", e2));
		}
				
		Vector<Vector<Object>> detalles = new Vector<Vector<Object>>();
		sentenciaSQL = "select * from detalletransaccion where detalletransaccion.numtienda=" + tienda + " and detalletransaccion.fecha = '" + fechaTransString
			+ "' and detalletransaccion.numcajainicia=" + cajaInicia + " and detalletransaccion.numregcajainicia=" + regCajaInicia + " order by correlativoitem asc";
		try {
			result = Conexiones.realizarConsulta(sentenciaSQL,false);
		} catch (BaseDeDatosExcepcion e1) {
			logger.error("recuperarDeEspera(String)", e1);

			e1.setMensaje("Error al recuperar factura en espera. " + e1.getMensaje());
			throw  e1;
		}
		try {
			result.beforeFirst();
			while (result.next()) {
				Vector<Object> detalle = new Vector<Object>();
				detalle.addElement(result.getString("codproducto"));
				detalle.addElement(result.getString("codcondicionventa"));
				detalle.addElement(new Float(result.getFloat("cantidad")));
				detalle.addElement(result.getString("codvendedor"));
				detalle.addElement(result.getString("codsupervisor"));
				detalle.addElement(new Double(result.getDouble("preciofinal")));
				detalle.addElement(new Double(result.getDouble("montoimpuesto")));
				detalle.addElement(new Integer(result.getInt("codpromocion")));
				detalle.addElement(result.getString("codtipocaptura"));
				detalle.addElement(new Double(result.getDouble("desctoempleado")));
				detalles.addElement(detalle);
			}
		} catch (SQLException e2) {
			logger.error("recuperarDeEspera(String)", e2);

			throw (new BaseDeDatosExcepcion("Error al recuperar factura en espera", e2));
		}
		
		//ACTUALIZACION CENTROBECO 19/12/2008: Módulo de promociones
		//Obteniendo las condiciones de venta de la venta
		Vector<Vector<CondicionVenta>> condicionesVenta = new Vector<Vector<CondicionVenta>>();
		sentenciaSQL = "select * from detalletransaccioncondicion dtc " +
				" where dtc.numtienda= " + tienda + " and " +
						" dtc.fecha = '" + fechaTransString + "' and " +
						" dtc.numcajainicia = "+cajaInicia+" and " +
						" dtc.numregcajainicia = "+ regCajaInicia +" order by dtc.correlativoitem asc";
		result = Conexiones.realizarConsulta(sentenciaSQL,false);

		try {
			result.beforeFirst();
			int correlativoItem = 0;
			if(result.next()){
				correlativoItem = result.getInt("correlativoitem");
				result.previous();
			}
			Vector<CondicionVenta> condicionesVentaDetalle = new Vector<CondicionVenta>();
			while (result.next()) {
				int correlativoItemNuevo = result.getInt("correlativoitem");
				if(correlativoItem==correlativoItemNuevo){
					CondicionVenta cv = new CondicionVenta(result.getString("codcondicionventa"), result.getInt("codpromocion"),result.getDouble("porcentajeDescuento"), result.getString("nombrePromocion"), result.getInt("prioridadPromocion"));
					if(!cv.getCondicion().equalsIgnoreCase(Sesion.condicionNormal)){
						cv.setMontoDctoCombo(result.getDouble("montoDctoCombo"));
						condicionesVentaDetalle.addElement(cv);
					}
				} else {
					condicionesVenta.addElement(condicionesVentaDetalle);
					condicionesVentaDetalle = new Vector<CondicionVenta>();
					CondicionVenta cv = new CondicionVenta(result.getString("codcondicionventa"), result.getInt("codpromocion"),result.getDouble("porcentajeDescuento"), result.getString("nombrePromocion"), result.getInt("prioridadPromocion"));
					if(!cv.getCondicion().equalsIgnoreCase(Sesion.condicionNormal)){
						cv.setMontoDctoCombo(result.getDouble("montoDctoCombo"));
						condicionesVentaDetalle.addElement(cv);
					}
					correlativoItem = correlativoItemNuevo;
				}
			}
			condicionesVenta.addElement(condicionesVentaDetalle);
		} catch (SQLException e2) {
			//Nada, puede que la tabla no exista porque no se tiene la extension de promociones
		} finally {
			try {
				result.close();
			} catch (SQLException e) {
				logger.error("obtenerApartado(int, String, int)", e);
			}
			result = null;
		}
		
		resultado.addElement(cabecera);
		resultado.addElement(detalles);
		resultado.addElement(condicionesVenta);

		if (logger.isDebugEnabled()) {
			logger.debug("recuperarDeEspera(String) - end");
		}
		return resultado;

	}

	public void borrarFacturaEnEspera(String identificacion, int cajaInicia, int tienda, int regCajaInicia, Date fecha)
			throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("borrarFacturaEnEspera(String, int, int, int, Date) - start");
		}

		// Borramos la factura recuperada del servidor central
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String fechaTransString = df.format(fecha);
			Statement loteSentenciasCR = Conexiones.crearSentencia(false);
			try {
				(new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().agregarDeleteTransaccionCondiciones(identificacion, loteSentenciasCR);
				
				String sentenciaSQL = "delete from detalletransaccion where detalletransaccion.numtienda= " + tienda + " and detalletransaccion.fecha = '" + fechaTransString
					+ "' and detalletransaccion.numcajainicia=" + cajaInicia + " and detalletransaccion.numregcajainicia=" + regCajaInicia;
				//Conexiones.realizarSentencia(sentenciaSQL,false);
				loteSentenciasCR.addBatch(sentenciaSQL);
		
				sentenciaSQL = "delete from transaccion where transaccion.codigofacturaespera = '" + identificacion
					+ "' and transaccion.estadotransaccion = '" + Sesion.ESPERA + "'";
				//Conexiones.realizarSentencia(sentenciaSQL,false);
				loteSentenciasCR.addBatch(sentenciaSQL);
		
				//Ahora ejecutamos todas las sentencias de forma transaccional en el servidor de la tienda
				Conexiones.ejecutarLoteSentencias(loteSentenciasCR, false, true);
				
			} finally {
				loteSentenciasCR.close();
			}
		} catch (SQLException e) {
			logger.error("borrarFacturaEnEspera(String, int, int, int, Date)",
					e);

			throw (new BaseDeDatosExcepcion("Error al recuperar factura en espera. No se puede borrar registro de base de datos central", e));
		}	

		if (logger.isDebugEnabled()) {
			logger
					.debug("borrarFacturaEnEspera(String, int, int, int, Date) - end");
		}
	}
}
