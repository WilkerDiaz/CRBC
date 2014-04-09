/**
 * =============================================================================
 * Proyecto   : CajaRegistradora
 * Paquete    : com.becoblohm.cr.manejarservicio
 * Programa   : BaseDeDatosServicio.java
 * Creado por : gmartinelli
 * Creado en  : 02-abr-04 09:49
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 2.0
 * Fecha       : 11-jul-06 08:32
 * Analista    : yzambrano
 * Descripción : - Colocar apartado en espera, almacenarlo en las tablas temporales
 *  			   de servicios 
 * 				 - Recuperar apartado de las tablas temporales de servicios.
 * =============================================================================
 * Versión     : 1.2
 * Fecha       : 19-jul-04 08:32
 * Analista    : gmartinelli
 * Descripción : Uso de Esquema en los queryes CR.tabla.campo
 * =============================================================================
 * Versión     : 1.1
 * Fecha       : 17-May-2004
 * Analista    : gmartinelli
 * Descripción : Agregado manejo de atributos de Pagos
 * =============================================================================
 * Versión     : 1.1
 * Fecha       : 17-May-2004
 * Analista    : yzambrano
 * Descripción : Agregado manejo de apartados en espera. Busqueda de apartados 
 * 				 en espera
 * =============================================================================
 * 
 */
package com.becoblohm.cr.mediadoresbd;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import net.n3.nanoxml.IXMLElement;
import net.n3.nanoxml.XMLElement;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.AfiliadoUsrExcepcion;
import com.becoblohm.cr.excepciones.AutorizacionExcepcion;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.BonoRegaloException;
import com.becoblohm.cr.excepciones.ClienteExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.LineaExcepcion;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.PagoExcepcion;
import com.becoblohm.cr.excepciones.SesionExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.extensiones.ActualizadorPreciosSACFactory;
import com.becoblohm.cr.extensiones.ManejoPagosFactory;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.manejarbr.ComprobanteFiscal;
import com.becoblohm.cr.manejarbr.DetalleTransaccionBR;
import com.becoblohm.cr.manejarbr.OpcionBR;
import com.becoblohm.cr.manejarbr.PromocionBR;
import com.becoblohm.cr.manejarbr.VentaBR;
import com.becoblohm.cr.manejarservicio.Abono;
import com.becoblohm.cr.manejarservicio.Apartado;
import com.becoblohm.cr.manejarservicio.Cotizacion;
import com.becoblohm.cr.manejarservicio.DetalleServicio;
import com.becoblohm.cr.manejarservicio.ListaRegalos;
import com.becoblohm.cr.manejarservicio.OperacionLR;
import com.becoblohm.cr.manejarusuario.Usuario;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.manejarventa.CondicionVenta;
import com.becoblohm.cr.manejarventa.Detalle;
import com.becoblohm.cr.manejarventa.DetalleTransaccion;
import com.becoblohm.cr.manejarventa.Venta;
import com.becoblohm.cr.utiles.Control;
import com.becoblohm.cr.utiles.Validaciones;
import com.epa.crprinterdriver.PrinterNotConnectedException;

/** 
 * Descripción:
 * 		Esta clase realiza todas las operaciones de realizacion de sentencias de la
 * base de Datos para el manejo de Servicios de la Caja Registradora.
 */
public class BaseDeDatosServicio {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BaseDeDatosServicio.class);


	
	/**
	 * obtenerNumServicioEspera
	 * 		Obtener el número de servicio que se asiganrá al apartado en espera. Se busca el código máximo 
	 * entre los apartados colocados en espera.
	 * @return entero correspondiente al maximo código de servicio mas uno. 
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */

	private static int obtenerNumServicioEspera () throws BaseDeDatosExcepcion, ConexionExcepcion
	{
		ResultSet result = null;
		String sentenciaSQL = "select max(numservicio) numero from serviciotemp";
		result = Conexiones.realizarConsulta(sentenciaSQL,false);
		try {
			if (result.first())  
				return (result.getInt("numero") + 1);
			else //No había ningún servicio en espera
				return (1);
		} catch (SQLException e2) {
			logger.error("obtenerApartado(int, String, int)", e2);

		} finally {
			try {
				result.close();
			} catch (SQLException e) {
				logger.error("obtenerApartado(int, String, int)", e);
			}
			result = null;
		}				
		return(-1);
	}
	
	/**
	 * Obtiene los datos de un Servicio de Apartado realizado
	 * @param tda Tienda donde se realizó el apartado.
	 * @param numServicio numero del servicio de apartado.
	 * @return Vector - Un vector que en la primera posicion contiene la cabecera del apartado y en 
	 * 					la segunda el vector de detalles del mismo.
	 * @throws BaseDeDatosExcepcion Si ocurre un error recuperando el apartado
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Object> obtenerApartado(int tda, String fechaString, int numServicio) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerApartado(int, String, int) - start");
		}

		Vector<Object> resultado = new Vector<Object>();
		ResultSet result = null;
		Date fecha, fechaTrans;
		String condicionVenta = "";
		Vector<Vector<CondicionVenta>> condicionesVenta = new Vector<Vector<CondicionVenta>>();
		
		// Armamos los vectores de la factura a recuperar
		String sentenciaSQL = "select servicio.*, tipoapartado.descripcion from servicio left outer join tipoapartado " +
				" on (servicio.tipoapartado=tipoapartado.codigo) where servicio.numtienda = " + tda + " and servicio.fecha='"+ fechaString
							+ "' and servicio.codtiposervicio = '" + Sesion.APARTADO + "' and servicio.numservicio = " + numServicio;
		result = Conexiones.realizarConsulta(sentenciaSQL,false);
		Vector<Object> cabecera = new Vector<Object>();
		try {
			if (result.first()) {
				cabecera.addElement(new Integer(tda));
				cabecera.addElement(result.getString("codtiposervicio"));
				cabecera.addElement(new Integer(result.getInt("numservicio")));
				fecha = result.getDate("fecha");
				cabecera.addElement(fecha);
				cabecera.addElement(result.getString("codcliente").trim());
				cabecera.addElement(new Double(result.getDouble("montobase")));
				cabecera.addElement(new Double(result.getDouble("montoimpuesto")));
				cabecera.addElement(new Integer(result.getInt("lineasfacturacion")));
				cabecera.addElement(result.getString("condicionabono"));
				cabecera.addElement(result.getString("codcajero"));
				cabecera.addElement(new Integer(result.getInt("numtransaccionventa")));
				fechaTrans = result.getDate("fechatransaccionvnta");
				cabecera.addElement(fechaTrans);
				cabecera.addElement(result.getTime("horainicia"));
				cabecera.addElement(result.getTime("horafinaliza"));
				cabecera.addElement(result.getString("estadoservicio"));
				cabecera.addElement(new Integer(result.getInt("tipoapartado")));
				cabecera.addElement(result.getDate("fechavence"));
				cabecera.addElement(result.getString("descripcion"));
			} else {
				sentenciaSQL = "select serviciotemp.*,tipoapartado.descripcion from serviciotemp left outer join tipoapartado " +
						"on (serviciotemp.tipoapartado=tipoapartado.codigo) where serviciotemp.numtienda = " + tda + " and serviciotemp.fecha='"+ fechaString
							+ "' and serviciotemp.codtiposervicio = '" + Sesion.APARTADO + "' and serviciotemp.numservicio = " + numServicio;
				result = Conexiones.realizarConsulta(sentenciaSQL,false);
				cabecera = new Vector<Object>();
				//try {
					if (result.first()) {
						cabecera.addElement(new Integer(tda));
						cabecera.addElement(result.getString("codtiposervicio"));
						cabecera.addElement(new Integer(result.getInt("numservicio")));
						fecha = result.getDate("fecha");
						cabecera.addElement(fecha);
						cabecera.addElement(result.getString("codcliente").trim());
						cabecera.addElement(new Double(result.getDouble("montobase")));
						cabecera.addElement(new Double(result.getDouble("montoimpuesto")));
						cabecera.addElement(new Integer(result.getInt("lineasfacturacion")));
						cabecera.addElement(result.getString("condicionabono"));
						cabecera.addElement(result.getString("codcajero"));
						cabecera.addElement(new Integer(result.getInt("numtransaccionventa")));
						fechaTrans = result.getDate("fechatransaccionvnta");
						cabecera.addElement(fechaTrans);
						cabecera.addElement(result.getTime("horainicia"));
						cabecera.addElement(result.getTime("horafinaliza"));
						cabecera.addElement(result.getString("estadoservicio"));
						cabecera.addElement(new Integer(result.getInt("tipoapartado")));
						cabecera.addElement(result.getDate("fechavence"));
						cabecera.addElement(result.getString("descripcion"));
						//Elimino el servicio recuperado para que no pueda ser recuperado por otra caja.
						Conexiones.realizarSentencia("delete from serviciotemp where serviciotemp.numtienda = " + tda + " and serviciotemp.fecha='"+ fechaString
							+ "' and serviciotemp.codtiposervicio = '" + Sesion.APARTADO + "' and serviciotemp.numservicio = " + numServicio, false);
					} else {
						throw (new BaseDeDatosExcepcion("No existe servicio de apartado número " + numServicio + " realizado en Tienda " + tda));
					}
				//throw (new BaseDeDatosExcepcion("No existe servicio de apartado número " + numServicio + " realizado en Tienda " + tda));
			}
		} catch (SQLException e2) {
			logger.error("obtenerApartado(int, String, int)", e2);

			throw (new BaseDeDatosExcepcion("Error al recuperar servicio " + numServicio + " de apartado", e2));
		} finally {
			try {
				result.close();
			} catch (SQLException e) {
				logger.error("obtenerApartado(int, String, int)", e);
			}
			result = null;
		}
				
		Vector<Vector<Object>> detallesServ = new Vector<Vector<Object>>();
		sentenciaSQL = "select * from detalleservicio where detalleservicio.numtienda= " + tda + " and detalleservicio.fecha = '" + fecha
			+ "' and detalleservicio.codtiposervicio= '" + Sesion.APARTADO + "' and detalleservicio.numservicio= " + numServicio + " order by detalleservicio.correlativoitem asc";
		result = Conexiones.realizarConsulta(sentenciaSQL,false);

		try {
			
			if (result.first()) {
				result.beforeFirst();
				while (result.next()) {
					Vector<Object> detalle = new Vector<Object>();
					detalle.addElement(result.getString("codproducto"));
					condicionVenta = result.getString("codcondicionventa");
					detalle.addElement(condicionVenta);
					detalle.addElement(new Float(result.getFloat("cantidad")));
					detalle.addElement(new Double(result.getDouble("precioregular")));
					detalle.addElement(new Double(result.getDouble("preciofinal")));
					detalle.addElement(new Double(result.getDouble("montoimpuesto")));
					detalle.addElement(result.getString("codtipocaptura"));
					detalle.addElement(new Integer(result.getInt("codpromocion")));
					detallesServ.addElement(detalle);
				}
				
				// ACTUALIZADO POR MODULO DE PROMOCIONES:
				// Obteniendo las condiciones de venta del servicio
				sentenciaSQL = "select * from detalleserviciocondicion dsc " +
						" where dsc.numtienda= " + tda + " and " +
								" dsc.fecha = '" + fecha + "' and " +
								" dsc.codtiposervicio= '" + Sesion.APARTADO + "' and " +
								" dsc.numservicio= " + numServicio+" order by dsc.correlativoitem asc";
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
					/*if(condicionesVentaDetalle.size()==0){
						int posDetalle = condicionesVentaDetalle.size();
						int codPromocion = ((Integer)((Vector)detallesServ.elementAt(posDetalle)).elementAt(7)).intValue();
						String codCondicion = (String)((Vector)detallesServ.elementAt(posDetalle)).elementAt(1);
						String nombrePromo =""; 
						if(codPromocion!=0){
							nombrePromo = "Promoción Básica";
						}
						if(!codCondicion.equalsIgnoreCase(Sesion.condicionNormal)){
							CondicionVenta condicionDetalle = new CondicionVenta(codCondicion,codPromocion, 0 ,nombrePromo,1);
							condicionDetalle.setMontoDctoCombo(0);
							condicionesVentaDetalle.addElement(condicionDetalle);
						}
					}*/
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
			}
			else
			{
				detallesServ = new Vector<Vector<Object>>();
				sentenciaSQL = "select * from detalleserviciotemp where detalleserviciotemp.numtienda= " + tda + " and detalleserviciotemp.fecha = '" + fecha
								+ "' and detalleserviciotemp.codtiposervicio= '" + Sesion.APARTADO + "' and detalleserviciotemp.numservicio= " + numServicio + " order by detalleserviciotemp.correlativoitem asc";
				result = Conexiones.realizarConsulta(sentenciaSQL,false);

				try {
					result.beforeFirst();
					while (result.next()) {
						Vector<Object> detalle = new Vector<Object>();
						detalle.addElement(result.getString("codproducto"));
						condicionVenta = result.getString("codcondicionventa");
						detalle.addElement(condicionVenta);
						detalle.addElement(new Float(result.getFloat("cantidad")));
						detalle.addElement(new Double(result.getDouble("precioregular")));
						detalle.addElement(new Double(result.getDouble("preciofinal")));
						detalle.addElement(new Double(result.getDouble("montoimpuesto")));
						detalle.addElement(result.getString("codtipocaptura"));
						detalle.addElement(new Integer(result.getInt("codpromocion")));
						detallesServ.addElement(detalle);
					}
					
					// ACTUALIZADO POR MODULO DE PROMOCIONES:
					// Obteniendo las condiciones de venta del servicio
					sentenciaSQL = "select * from detalleserviciotempcondicion dsc " +
							" where dsc.numtienda= " + tda + " and " +
									" dsc.fecha = '" + fecha + "' and " +
									" dsc.codtiposervicio= '" + Sesion.APARTADO + "' and " +
									" dsc.numservicio= " + numServicio+" order by dsc.correlativoitem asc";
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
						/*if(condicionesVentaDetalle.size()==0){
							int posDetalle = condicionesVentaDetalle.size();
							int codPromocion = ((Integer)((Vector)detallesServ.elementAt(posDetalle)).elementAt(7)).intValue();
							String codCondicion = (String)((Vector)detallesServ.elementAt(posDetalle)).elementAt(1);
							String nombrePromo ="";
							if(codPromocion!=0){
								nombrePromo = "Promoción Básica";
							}
							if(!codCondicion.equalsIgnoreCase(Sesion.condicionNormal)){
								CondicionVenta condicionDetalle = new CondicionVenta(codCondicion,codPromocion, 0 ,nombrePromo,1);
								condicionDetalle.setMontoDctoCombo(0);
								condicionesVentaDetalle.addElement(condicionDetalle);
							}
						}*/
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
					
					//Elimino el servicio recuperado para que no pueda ser cargado en otra caja
					Conexiones.realizarSentencia("delete from detalleserviciotempcondicion where detalleserviciotempcondicion.numtienda= " + tda + " and detalleserviciotempcondicion.fecha = '" + fecha
							+ "' and detalleserviciotempcondicion.codtiposervicio= '" + Sesion.APARTADO + "' and detalleserviciotempcondicion.numservicio= " + numServicio, false);
					
					Conexiones.realizarSentencia("delete from detalleserviciotemp where detalleserviciotemp.numtienda= " + tda + " and detalleserviciotemp.fecha = '" + fecha
								+ "' and detalleserviciotemp.codtiposervicio= '" + Sesion.APARTADO + "' and detalleserviciotemp.numservicio= " + numServicio, false);
					
				} catch (SQLException e3) {
					logger.error("obtenerApartado(int, String, int)", e3);
					throw (new BaseDeDatosExcepcion("Error al recuperar servicio " + numServicio + " de apartado", e3));
				}
			}
		} catch (SQLException e2) {
		//} catch (Exception e2) {
			logger.error("obtenerApartado(int, String, int)", e2);
			throw (new BaseDeDatosExcepcion("Error al recuperar servicio " + numServicio + " de apartado", e2));
		} finally {
			try {
				result.close();
			} catch (SQLException e) {
				logger.error("obtenerApartado(int, String, int)", e);
			} catch(NullPointerException np){
				//Nada, ya fue cerrado result
			}
			result = null;
		}

		resultado.addElement(cabecera);
		resultado.addElement(detallesServ);
		//Agregado por módulo de promociones
		resultado.addElement(condicionesVenta);

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerApartado(int, String, int) - end");
		}
		return resultado;
	}

	/**
	 * Obtiene los datos de los Abonos realizados al servicio especificado.
	 * @param tda Tienda donde se realizó el apartado.
	 * @param numServicio número del servicio de apartado.
	 * @return Vector - Un vector que cotiene objetos de tipo Abono correspondientes al servicio especificado.
	 * @throws BaseDeDatosExcepcion Si ocurre un error recuperando los abonos
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Abono> obtenerAbonos(int tda, int numServicio) throws PagoExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerAbonos(int, int) - start");
		}

		ResultSet result = null;
		ResultSet result2 = null;
		Vector<Abono> abonos = new Vector<Abono>();
			
		// Armamos los vectores de la factura a recuperar
		String sentenciaSQL = "select * from  transaccionabono where transaccionabono.numtienda = " + tda + " and transaccionabono.numservicio = " + numServicio + " order by transaccionabono.numabono";
			
		result = Conexiones.realizarConsulta(sentenciaSQL,false);
	
		try {
			result.beforeFirst();
			while (result.next()) {
				Abono abono = new Abono(new Integer(tda).intValue(), new Integer(result.getInt("numcaja")).intValue(),new Integer(result.getInt("numabono")).intValue(), result.getDate("fecha"), result.getTime("horainicia"), result.getTime("horafinaliza"), result.getDouble("monto"), result.getString("estadoabono").toCharArray()[0], result.getString("tipotransaccionabono").toCharArray()[0], result.getString("codcajero"));
				Vector<Pago> pagosAbono = new Vector<Pago>();
				
				// Armamos el vector de pagos que posee cada uno de los abonos
				String sentenciaSQL2 = "select * from pagodeabonos where pagodeabonos.numservicio = " + numServicio + " and pagodeabonos.numtienda = " + tda + " and pagodeabonos.numcaja = " +abono.getCaja()+ " and pagodeabonos.numabono = "+ abono.getNumAbono()+ " and pagodeabonos.fecha = '" + abono.getFechaAbono() + "' order by pagodeabonos.fecha desc";
				result2 = Conexiones.realizarConsulta(sentenciaSQL2,false);
				try {
					result2.beforeFirst();
					while (result2.next()) {
						Pago pago = new Pago(result2.getString("codformadepago"), new Double(result2.getDouble("monto")).doubleValue(), result2.getString("codbanco"), result2.getString("numdocumento"), result2.getString("numcuenta"), result2.getString("numconformacion"), result2.getInt("numreferencia"), result2.getString("cedtitular"));
						pagosAbono.addElement(pago);
					}
				} catch (SQLException e2) {
					logger.error("obtenerAbonos(int, int)", e2);

					throw (new BaseDeDatosExcepcion("Error al recuperar pagos del abono número " + abono.getNumAbono() + " de apartado " + numServicio, e2));
				}
				result2.close();
				result2 = null;
				//Ahora insertamos el abono construido en el vector de abonos del servicio especificado
				abono.setPagos(pagosAbono);
				abonos.addElement(abono);
			}
		} catch (SQLException e2) {
			logger.error("obtenerAbonos(int, int)", e2);

			throw (new BaseDeDatosExcepcion("Error al recuperar abonos del servicio " + numServicio + " de apartado", e2));
		} finally {
			try {
				result.close();
			} catch (SQLException e) {
				logger.error("obtenerAbonos(int, int)", e);
			}
			result= null;
			if (result2 != null) {
				try {
					result2.close();
				} catch (SQLException e1) {
					logger.error("obtenerAbonos(int, int)", e1);
				}
				result2 = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerAbonos(int, int) - end");
		}
		return abonos;
	}

	private static void registrarAbono(Statement loteSentenciasCR, Abono ab, int numServ, String codTipoServ, Date fechaServ) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("registrarAbono(Statement, Abono, int, String, Date) - start");
		}

		try {
			//Statement loteSentenciasCR = Conexiones.getConexion(true).createStatement();
			// Registramos los datos del abono en la tabla transaccionabono
			String fechaAbonoString = Control.FECHA_FORMAT.format(ab.getFechaAbono());
			String fechaServString = Control.FECHA_FORMAT.format(fechaServ);
			
			/*String sentenciaSQL = "select * from transaccionabono where numtienda = " + ab.getTienda() + " and numcaja = " + ab.getCaja() + " and fecha = '" + fechaAbonoString + "' and numAbono = " + ab.getNumAbono() + " and numservicio = " + numServ;
			ResultSet consultaAbono = Conexiones.realizarConsulta(sentenciaSQL,false);
			
			if (consultaAbono.first()) {
				// Intentamos realizar un update
				 sentenciaSQL = "update transaccionabono set "
					 + "tipotransaccionabono = '" + ab.getTipoTransAbono() + "', "
					 + "numservicio = " + numServ + ", "
					 + "monto = " + ab.getMontoBase() + ", "
					 + "montoremanente = " + ab.getMontoRem() + ", "
					 + "estadoabono = '" + ab.getEstadoAbono() + "', "
					 + "regactualizado = '" + Sesion.NO + "' "
					 + "where numtienda = " + ab.getTienda() + " and numcaja = " + ab.getCaja() 
					 + " and numabono = " + ab.getNumAbono() + " and numservicio = " + numServ + " and fecha = '" + fechaAbonoString + "'";
				 loteSentenciasCR.addBatch(sentenciaSQL);	
			} else {*/
			String sentenciaSQL = "insert into transaccionabono (numtienda,numcaja,fecha,numabono,tipotransaccionabono,numservicio,codtiposervicio,fechaservicio,codcajero,horainicia,horafinaliza,monto,vueltocliente,serialcaja,estadoabono,regactualizado) values ("
				+ ab.getTienda() + ", " + ab.getCaja() + ", '" + fechaAbonoString + "', " + ab.getNumAbono() + ", "
				+ "'" + ab.getTipoTransAbono() + "', " + numServ + ", '" + codTipoServ + "', '" + fechaServString + "', "
				+ "'" + ab.getCodCajero() + "', '" + ab.getHoraInicia() + "', '" + ab.getHoraFinaliza() + "', "
				+ ab.getMontoBase() + ", " + ab.getMontoVuelto() + ", '" + Sesion.getCaja().getSerial() + "', '" + ab.getEstadoAbono() + "', '" + Sesion.NO + "')";
			loteSentenciasCR.addBatch(sentenciaSQL);	
			//}
				
			// Para cada forma de pago en el abono
			ManejoPagosFactory.getInstance().registrarPagos(loteSentenciasCR, ab, numServ);
			
		} catch (SQLException e) {
			logger.error("registrarAbono(Statement, Abono, int, String, Date)",
					e);

			throw (new BaseDeDatosExcepcion("Error en sentencia SQL al registrar el abono"));
		}/* catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de conexión con BD al registrar el abono"));
		}*/

		if (logger.isDebugEnabled()) {
			logger
					.debug("registrarAbono(Statement, Abono, int, String, Date) - end");
		}
	}
	 
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se comentaron variables sin uso
	* Fecha: agosto 2011
	*/
	public static void registrarAbonosApartado(Apartado ap) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("registrarAbonosApartado(Apartado) - start");
		}

		Statement loteSentenciasCR = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			loteSentenciasCR = Conexiones.crearSentencia(false);
			//if (ap.isRecalcularSaldo()) {
				String sentencia = "update servicio set " +
							"servicio.montobase = " + ap.getMontoBase() + ", " + 
							"servicio.montoimpuesto = " + ap.getMontoImpuesto() + ", " +
							"servicio.regactualizado = '" + Sesion.NO + "', " +
							"servicio.fechavence='"+df.format(ap.getFechaVencimiento())+"' " +
							"where servicio.numtienda = " + ap.getCodTienda() + " and " +
							"servicio.codtiposervicio = '" + ap.getTipoServicio() + "' and " +
							"servicio.numservicio = " + ap.getNumServicio() + " and " +
							"servicio.fecha = '" + df.format(ap.getFechaServicio()) + "'";
				loteSentenciasCR.addBatch(sentencia);
				
				//Caso agregado por módulo de promociones
				(new ActualizadorPreciosSACFactory()).getActualizadorPreciosSACInstance().agregarDeleteServicioCondiciones(ap, loteSentenciasCR, false ,ap.getNumServicio());
				
				// Actualizamos los detalles de transaccion por si se realizaron cambios
				// ACTUALIZACION CENTROBECO: 26/12/2008
				// Por los combos la cantidad de detalles de servicio puede variar en cada recalculo
				// es preferible borrarlos y volverlos a insertar
				
				
				/*for (int i=0;i<ap.getDetallesServicio().size();i++) {
					DetalleServicio detalle = (DetalleServicio)ap.getDetallesServicio().elementAt(i);
					String promo = (detalle.getCodPromocion()>0) ? String.valueOf(detalle.getCodPromocion()) : "null";
					String sentenciaSQL = "update detalleservicio set " +
								"detalleservicio.codcondicionventa = '" + detalle.getCondicionVenta() + "', " + 
								"detalleservicio.preciofinal = " + detalle.getPrecioFinal() + ", " +
								"detalleservicio.montoimpuesto = " + detalle.getMontoImpuesto() + ", " +
								"detalleservicio.codpromocion = " + promo + ", " +
								"detalleservicio.regactualizado = '" + Sesion.NO + "' " +
								"where detalleservicio.correlativoitem = " + (i+1) + " and " +
								"detalleservicio.numtienda = " + ap.getCodTienda() + " and " +
								"detalleservicio.codtiposervicio = '" + ap.getTipoServicio() + "' and " +
								"detalleservicio.numservicio = " + ap.getNumServicio() + " and " +
								"detalleservicio.fecha = '" + df.format(ap.getFechaServicio()) + "'";
					loteSentenciasCR.addBatch(sentenciaSQL);
					
					//Caso agregado por módulo de promociones
					(new ActualizadorPreciosSACFactory()).getActualizadorPreciosSACInstance().agregarInsertServicioCondiciones(ap, loteSentenciasCR, df.format(ap.getFechaServicio()), sentenciaSQL, i, false, ap.getNumServicio());
				}*/
				String sentenciaSQL = " delete from detalleservicio where " +
						"numtienda = " + ap.getCodTienda() + " and " +
						"codtiposervicio = '" + ap.getTipoServicio() + "' and " +
						"numservicio = " + ap.getNumServicio() + " and " +
						"fecha = '" + df.format(ap.getFechaServicio()) + "'";
				loteSentenciasCR.addBatch(sentenciaSQL);
				
				// Registramos los detalles del servicio
				String fechaServString = new SimpleDateFormat("yyyy-MM-dd").format(ap.getFechaServicio());
				for (int i=0; i<ap.getDetallesServicio().size(); i++) {
					String promocion = (((DetalleServicio)ap.getDetallesServicio().elementAt(i)).getCodPromocion() > 0)
										? String.valueOf(((DetalleServicio)ap.getDetallesServicio().elementAt(i)).getCodPromocion())
										: "null";
					sentenciaSQL = "insert into detalleservicio (numtienda,codtiposervicio,numservicio,fecha,codproducto,codcondicionventa,correlativoitem,cantidad,precioregular,preciofinal,montoimpuesto,codtipocaptura,codpromocion) values ("
						+ ap.getCodTienda() + ", " 
						+ "'" + ap.getTipoServicio() + "', "
						+ ap.getNumServicio() + ", " 
						+ "'" + fechaServString + "', "
						+ "'" + ((DetalleServicio)ap.getDetallesServicio().elementAt(i)).getProducto().getCodProducto() + "', "
						+ "'" + ((DetalleServicio)ap.getDetallesServicio().elementAt(i)).getCondicionVenta() + "', "
						+ (i+1) + ", "
						+ ((DetalleServicio)ap.getDetallesServicio().elementAt(i)).getCantidad() + ", "
						+ ((DetalleServicio)ap.getDetallesServicio().elementAt(i)).getProducto().getPrecioRegular() + ", "
						+ ((DetalleServicio)ap.getDetallesServicio().elementAt(i)).getPrecioFinal() + ", "
						+ ((DetalleServicio)ap.getDetallesServicio().elementAt(i)).getMontoImpuesto() + ", "
						+ "'" + ((DetalleServicio)ap.getDetallesServicio().elementAt(i)).getTipoCaptura() + "', "
						+ promocion + ")";
					//Conexiones.realizarSentencia(sentenciaSQL,true);
					loteSentenciasCR.addBatch(sentenciaSQL);
					
					//A cada detalle le registro sus condiciones de venta
					(new ActualizadorPreciosSACFactory()).getActualizadorPreciosSACInstance().agregarInsertServicioCondiciones(ap, loteSentenciasCR, fechaServString, sentenciaSQL, i, false,  ap.getNumServicio());
				}
			//}
			
			for (int i =0; i<ap.getAbonos().size(); i++){
				Abono ab = (Abono)ap.getAbonos().elementAt(i);
				// Registramos los datos del abono en la tabla transaccionabono
				String fechaAbonoString = new SimpleDateFormat("yyyy-MM-dd").format(ab.getFechaAbono());
				sentenciaSQL = "select * from transaccionabono where transaccionabono.numtienda = " + ab.getTienda() + " and transaccionabono.numcaja = " + ab.getCaja() + " and transaccionabono.fecha = '" + fechaAbonoString + "' and transaccionabono.numAbono = " + ab.getNumAbono() + " and transaccionabono.numservicio = " + ap.getNumServicio();
				ResultSet consultaAbono = Conexiones.realizarConsulta(sentenciaSQL,false);
				char cajaEnLinea = Sesion.isCajaEnLinea() == true ? Sesion.SI:Sesion.NO;
				try {
					if (consultaAbono.first()) {
						// Intentamos realizar un update
						 sentenciaSQL = "update transaccionabono set "
							 + "transaccionabono.tipotransaccionabono = '" + ab.getTipoTransAbono() + "', "
							 + "transaccionabono.monto = " + ab.getMontoBase() + ", "
							 + "vueltocliente = " + ab.getMontoVuelto() + ", "
							 + "montoremanente = " + ab.getMontoRemanente() + ", "
							 + "cajaenlinea = '" + cajaEnLinea + "', "
							 + "transaccionabono.estadoabono = '" + ab.getEstadoAbono() + "', "
							 + "transaccionabono.regactualizado = '" + Sesion.NO + "' "
							 + "where transaccionabono.numtienda = " + ab.getTienda() 
							 + " and transaccionabono.numcaja = " + ab.getCaja() 
							 + " and transaccionabono.fecha = '" + fechaAbonoString 
							 + "' and transaccionabono.numAbono = " + ab.getNumAbono() 
							 + " and transaccionabono.numservicio = " + ap.getNumServicio();
						 loteSentenciasCR.addBatch(sentenciaSQL);
					} else {
						sentenciaSQL = "insert into transaccionabono (numtienda,numcaja,fecha,numabono,tipotransaccionabono,numservicio,codtiposervicio,fechaservicio,codcajero,horainicia,horafinaliza,monto,vueltocliente,montoremanente,cajaenlinea,serialcaja,estadoabono,regactualizado) values ("
						+ ab.getTienda() + ", " + ab.getCaja() + ", '" + fechaAbonoString + "', " + ab.getNumAbono() + ", "
						+ "'" + ab.getTipoTransAbono() + "', " + ap.getNumServicio() + ", '" + ap.getTipoServicio() + "', '" + fechaServString + "', "
						+ "'" + ab.getCodCajero() + "', '" + ab.getHoraInicia() + "', '" + ab.getHoraFinaliza() + "', "
						+ ab.getMontoBase() + ", "+ ab.getMontoVuelto() + ", " + ab.getMontoRemanente() + ", '" + cajaEnLinea + "', '" + Sesion.getCaja().getSerial() + "', '" + ab.getEstadoAbono() + "', '" + Sesion.NO + "')";
						loteSentenciasCR.addBatch(sentenciaSQL);
					}
				} finally {
					consultaAbono.close();
					consultaAbono = null;
				}
				
				// Para cada forma de pago en el abono
				for (int j=0; j<ab.getPagos().size(); j++) {
					Pago pagoActual = (Pago)ab.getPagos().elementAt(j);
					/*String codBanco = (pagoActual.getCodBanco() != null)
									? "'" + pagoActual.getCodBanco() + "'"
									: "null";
					String numDocumento = (pagoActual.getNumDocumento() != null)
										? "'" + pagoActual.getNumDocumento() + "'"
										: "null";
					String numCuenta = (pagoActual.getNumCuenta() != null)
									? "'" + pagoActual.getNumCuenta() + "'"
									: "null";
					String numConf = (pagoActual.getNumConformacion() != null)
					? "'" + pagoActual.getNumConformacion() + "'"
							: "null";
					String numReferencia = (pagoActual.getNumReferencia() != 0)
										? "'" + pagoActual.getNumReferencia() + "'"
										: "null";
					String cedTitular = (pagoActual.getCedTitular() != null)
										? "'" + pagoActual.getCedTitular() + "'"
										: "null";
				*/
					sentenciaSQL = "select * from pagodeabonos where pagodeabonos.numservicio = " + ap.getNumServicio() + " and pagodeabonos.numtienda = " + ab.getTienda() + " and pagodeabonos.numcaja = " + ab.getCaja() + " and pagodeabonos.fecha = '" + fechaAbonoString + "' and pagodeabonos.numAbono = " + ab.getNumAbono() + " and pagodeabonos.codformadepago = '" + pagoActual.getFormaPago().getCodigo() +"'";
					ResultSet consultaPagoAbono = Conexiones.realizarConsulta(sentenciaSQL,false);
					try {
						if (!consultaPagoAbono.first()) {
							ManejoPagosFactory.getInstance().registrarPago(loteSentenciasCR, ab, ap.getNumServicio(), pagoActual, j + 1);
						}
					} finally {
						consultaPagoAbono.close();
						consultaPagoAbono = null;
					}
				}
			}
			//Ahora ejecutamos todas las sentencias de forma transaccional en la caja
			while (!Sesion.isCajaEnLinea()) {
				logger.error("Caja Fuera de Linea al intentar insertar abono");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
			Conexiones.ejecutarLoteSentencias(loteSentenciasCR, false, true);
		} catch (SQLException e) {
			logger.error("registrarAbonosApartado(Apartado)", e);

			throw (new BaseDeDatosExcepcion("Error en sentencia SQL al registrar el abono"));
		} catch (ConexionExcepcion e) {
			logger.error("registrarAbonosApartado(Apartado)", e);

			throw (new BaseDeDatosExcepcion("Error de conexión con BD al registrar el abono"));
		} finally {
			if (loteSentenciasCR != null) {
				try {
					loteSentenciasCR.close();
				} catch (SQLException e1) {
					logger.error("registrarAbonosApartado(Apartado)", e1);
				}
				loteSentenciasCR = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("registrarAbonosApartado(Apartado) - end");
		}
	}
	
	public static void registrarApartado(Apartado apartado) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("registrarApartado(Apartado) - start");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Statement loteSentenciasCR = null;
		try {
			loteSentenciasCR = Conexiones.crearSentencia(false);
			// Registramos la cabecera del servicio
			String fechaServString = new SimpleDateFormat("yyyy-MM-dd").format(apartado.getFechaServicio());
			String codCliente = (apartado.getCliente().getCodCliente() != null)
								? "'" + apartado.getCliente().getCodCliente() + "'"
								: "null";
			String condAbono = (apartado.getCondicionAbono() != ' ')
								? "'" + apartado.getCondicionAbono() + "'"
								: "null";
			String sentenciaSQL = "insert into servicio (numtienda,codtiposervicio,numservicio,fecha,codcliente,montobase,montoimpuesto,lineasfacturacion,condicionabono,codcajero,horainicia,horafinaliza,estadoservicio, tipoapartado, fechavence) values (" 
				+ apartado.getCodTienda() + ", " 
				+ "'" + apartado.getTipoServicio() + "', "
				+ apartado.getNumServicio() + ", " 
				+ "'" + fechaServString + "', "
				+ codCliente.trim() + ", "
				+ apartado.getMontoBase() + ", "
				+ apartado.getMontoImpuesto() + ", "
				+ apartado.getLineasFacturacion() + ", "
				+ condAbono + ", "
				+ "'" + apartado.getCodCajero() + "', "
				+ "'" + apartado.getHoraInicia() + "', "
				+ "'" + apartado.getHoraFin() + "', "
				+ "'" + apartado.getEstadoServicio() + "', " 
				+ apartado.getTipoApartado()+", " 
				+ "'"+sdf.format(apartado.getFechaVencimiento())+"' )";
			//Conexiones.realizarSentencia(sentenciaSQL,true);
			loteSentenciasCR.addBatch(sentenciaSQL);
			
			// Registramos los detalles del servicio
			for (int i=0; i<apartado.getDetallesServicio().size(); i++) {
				String promocion = (((DetalleServicio)apartado.getDetallesServicio().elementAt(i)).getCodPromocion() > 0)
									? String.valueOf(((DetalleServicio)apartado.getDetallesServicio().elementAt(i)).getCodPromocion())
									: "null";
				sentenciaSQL = "insert into detalleservicio (numtienda,codtiposervicio,numservicio,fecha,codproducto,codcondicionventa,correlativoitem,cantidad,precioregular,preciofinal,montoimpuesto,codtipocaptura,codpromocion) values ("
					+ apartado.getCodTienda() + ", " 
					+ "'" + apartado.getTipoServicio() + "', "
					+ apartado.getNumServicio() + ", " 
					+ "'" + fechaServString + "', "
					+ "'" + ((DetalleServicio)apartado.getDetallesServicio().elementAt(i)).getProducto().getCodProducto() + "', "
					+ "'" + ((DetalleServicio)apartado.getDetallesServicio().elementAt(i)).getCondicionVenta() + "', "
					+ (i+1) + ", "
					+ ((DetalleServicio)apartado.getDetallesServicio().elementAt(i)).getCantidad() + ", "
					+ ((DetalleServicio)apartado.getDetallesServicio().elementAt(i)).getProducto().getPrecioRegular() + ", "
					+ ((DetalleServicio)apartado.getDetallesServicio().elementAt(i)).getPrecioFinal() + ", "
					+ ((DetalleServicio)apartado.getDetallesServicio().elementAt(i)).getMontoImpuesto() + ", "
					+ "'" + ((DetalleServicio)apartado.getDetallesServicio().elementAt(i)).getTipoCaptura() + "', "
					+ promocion + ")";
				//Conexiones.realizarSentencia(sentenciaSQL,true);
				loteSentenciasCR.addBatch(sentenciaSQL);
				
				//A cada detalle le registro sus condiciones de venta
				(new ActualizadorPreciosSACFactory()).getActualizadorPreciosSACInstance().agregarInsertServicioCondiciones(apartado, loteSentenciasCR, fechaServString, sentenciaSQL, i, false,  apartado.getNumServicio());
			}
			
			// Registramos los abonos realizados
			for (int i=0; i<apartado.getAbonos().size(); i++) {
				registrarAbono(loteSentenciasCR, (Abono)apartado.getAbonos().elementAt(i), apartado.getNumServicio(), apartado.getTipoServicio(), apartado.getFechaServicio());
			}
			Conexiones.ejecutarLoteSentencias(loteSentenciasCR, false, true);
		} catch (SQLException e) {
			logger.error("registrarApartado(Apartado)", e);

			throw (new BaseDeDatosExcepcion("Error en sentencia SQL al registrar el apartado"));
		} catch (ConexionExcepcion e) {
			logger.error("registrarApartado(Apartado)", e);

			throw (new BaseDeDatosExcepcion("Error de conexión con BD al registrar el apartado"));
		} finally {
			if (loteSentenciasCR != null) {
				try {
					loteSentenciasCR.close();
				} catch (SQLException e1) {
					logger.error("registrarApartado(Apartado)", e1);
				}
				loteSentenciasCR = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("registrarApartado(Apartado) - end");
		}
	}
	
	/**
	 * Registra el cliente No Afiliado en la Base de Datos Local.
	 * @param cliente Cliente que se registrara en la tabla "Afiliado" en la base de datos con tipoAfiliado="R"(Registrado).
	 * @throws BaseDeDatosExcepcion Si ocurre un error con la insercion en la Base de Datos.
	 */
	public static void registrarClienteTemporal(Cliente cliente) throws BaseDeDatosExcepcion, ConexionExcepcion, AfiliadoUsrExcepcion {
		BaseDeDatosServicio.registrarClienteTemporal(cliente, true);
	}
	
	/**
	 * Registra el cliente No Afiliado en la Base de Datos.
	 * @param cliente Cliente que se registrara en la tabla "Afiliado" en la base de datos con tipoAfiliado="R"(Registrado).
	 * @param local Destino de Registro del Cliente (Local o Remoto).
	 * @throws BaseDeDatosExcepcion Si ocurre un error con la insercion en la Base de Datos.
	 */
	public static void registrarClienteTemporal(Cliente cliente, boolean local) throws BaseDeDatosExcepcion, ConexionExcepcion, AfiliadoUsrExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("registrarClienteTemporal(Cliente) - start");
		}

		// Registramos al cliente
		try {		
			SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
			String fechaReg = fecha.format(Sesion.getHoraSistema());
			String fechaNaci="";
			char contactar = ' ';
			if(cliente.isContactar())
				contactar = Sesion.SI;
			else 
				contactar = Sesion.NO;
			
			if (cliente.getFechaNaci()!=null)
				fechaNaci=fecha.format(cliente.getFechaNaci());

			Timestamp actualizacion = new Timestamp(Calendar.getInstance().getTime().getTime());				
		
			//Verificamos si ya existía este cliente temporal
			 String sentenciaSQL = "select * from afiliado where codafiliado = '" + cliente.getCodCliente() + "'";
			 ResultSet resultado = Conexiones.realizarConsulta(sentenciaSQL,local);
			 try {
				 if (!resultado.first()) {
					sentenciaSQL = "insert into afiliado (codafiliado,tipoafiliado,nombre,apellido,numtienda," +
						"direccion,email,codarea,numtelefono,codarea1,numtelefono1," +
						"fechaafiliacion,registrado, contactar, actualizacion,genero,estadocivil,fechanacimiento,direccionfiscal) "
						+ "values ('" + cliente.getCodCliente().trim() + "', " 
						+ "'" + cliente.getTipoCliente() + "', "
						+ "'" + Validaciones.validarString(cliente.getNombre())+ "', "
						+ "'" + Validaciones.validarString(cliente.getApellido())+ "', "
						+ Sesion.getNumTienda() + ", "
						+ "'" + Validaciones.validarString(cliente.getDireccionAlmacenada()) + "', "
						+ "'" + cliente.getEmail() + "', "
						+ "'" + cliente.getCodArea() + "', "
						+ "'" + cliente.getNumTelefono() + "', "
						+ "'" + cliente.getCodAreaSec() + "', "
						+ "'" + cliente.getNumTelefonoSec() + "', "
						+ "'" + fechaReg + "', "
						+ "'" + Sesion.CLIENTE_AFILIADO + "', "
						+ "'" + contactar + "', "
						+ "'" + actualizacion + "',"
						+ "'" + cliente.getSexo() + "', "         //campo nuevo Crm wdiaz
						+ "'" + cliente.getEstadoCivil() + "',"   //campo nuevo Crm wdiaz
						+ "'" + fechaNaci+ "',"                   //campo nuevo Crm wdiaz
						+ "'" + Validaciones.validarString(cliente.getDirFiscal())+ "') ";   //campo nuevo Crm wdiaz
					
					Conexiones.realizarSentencia(sentenciaSQL,local);
				 } else {
					//char registrado = (char)(resultado.getString("registrado").toCharArray()[0]);
					//Verficamos si el cliente es registrado (temporal) o es un cliente afiliado
					//Si es un afiliado se lanza una excepción para indicar que no se puede hacer esta operación
					sentenciaSQL = "update afiliado set " 
									+ "tipoafiliado = '" + cliente.getTipoCliente() + "', "
									+ "nombre = '" + Validaciones.validarString(cliente.getNombre()) + "', "
									+ "apellido = '" + Validaciones.validarString(cliente.getApellido())+ "', "
									+ "direccion = '" + Validaciones.validarString(cliente.getDireccionAlmacenada()) + "', "
									+ "codarea = '" + cliente.getCodArea() + "', "
									+ "numtelefono = '" + cliente.getNumTelefono() + "', "
									+ "codarea1 = '" + cliente.getCodAreaSec() + "', "
									+ "numtelefono1 = '" + cliente.getNumTelefonoSec() + "', "
									+ "email = '" + cliente.getEmail() + "', "
									+ "registrado = '" + Sesion.CLIENTE_AFILIADO + "', "
									+ "contactar = '" + contactar + "', "
									+ "actualizacion = '" + actualizacion + "',"
									+ "genero = '" + cliente.getSexo() + "', "					 //campo nuevo Crm wdiaz
									+ "estadocivil = '" + cliente.getEstadoCivil() + "', "       //campo nuevo Crm wdiaz
									+ "fechanacimiento = '" + fechaNaci + "',"					 //campo nuevo Crm wdiaz
									+ "direccionfiscal = '" + Validaciones.validarString(cliente.getDirFiscal())+ "'"		 //campo nuevo Crm wdiaz
									+ " where codafiliado = '" + cliente.getCodCliente() + "'";
					Conexiones.realizarSentencia(sentenciaSQL,local);
				 }
			 } finally {
				resultado.close();
			}
		} catch (ConexionExcepcion e) {
			logger.error("registrarClienteTemporal(Cliente)", e);

			throw (new BaseDeDatosExcepcion("Error de conexión con BD al registrar cliente NO afiliado"));
		} catch (BaseDeDatosExcepcion e) {
			logger.error("registrarClienteTemporal(Cliente)", e);

			throw (new BaseDeDatosExcepcion("Error en ejecución del query al registrar cliente NO afiliado"));
		} catch (SQLException e) {
			logger.error("registrarClienteTemporal(Cliente)", e);

			throw (new BaseDeDatosExcepcion("Error en sentencia SQL al registrar cliente NO afiliado"));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("registrarClienteTemporal(Cliente) - end");
		}
	}

	/**
	 * Actualiza el estado del Servicio de 'Activo' a 'Anulado'. Igual con todos los abonos del mismo
	 */
	public static void anularApartado(Apartado apartado) throws BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("anularApartado(Apartado) - start");
		}

		Statement loteSentenciasCR = null;
		try {
			//Se actualiza el estado del servicio
			loteSentenciasCR = Conexiones.crearSentencia(false);
			String sentenciaSQL = "update servicio set servicio.estadoservicio = '" + apartado.getEstadoServicio() 
								  + "',servicio.numcajaanula = " + Sesion.getCaja().getNumero() 
								  + ",servicio.codusuarioanula = '" + Sesion.getUsuarioActivo().getNumFicha() 
								  + "',servicio.regactualizado = '" + Sesion.NO 
								  + "' where servicio.numtienda=" + apartado.getCodTienda()
								  + " and servicio.fecha = '" 
								  + Control.FECHA_FORMAT.format(apartado.getFechaServicio()) 
								  + "' and servicio.codtiposervicio='" + apartado.getTipoServicio()
								  + "' and servicio.numservicio=" + apartado.getNumServicio();
			loteSentenciasCR.addBatch(sentenciaSQL);
			
			//**** 11/06/2009 IROJAS. Modificación para anulación de apartados. Problema al Sinc al AS400
			//SE ACTUALIZAN LOS DETALLES DE SERVICIOS
			 sentenciaSQL = "update detalleservicio set detalleservicio.regactualizado = '" + Sesion.NO  
			  + "' where detalleservicio.numtienda=" + apartado.getCodTienda()
			  + " and detalleservicio.fecha = '" 
			  + Control.FECHA_FORMAT.format(apartado.getFechaServicio()) 
			  + "' and detalleservicio.codtiposervicio='" + apartado.getTipoServicio()
			  + "' and detalleservicio.numservicio=" + apartado.getNumServicio();
			loteSentenciasCR.addBatch(sentenciaSQL);
			//****
			
			// Se actualizan los estados de los abonos del servicio
			for (int i=0; i<apartado.getAbonos().size(); i++) {
				Abono abono = ((Abono)apartado.getAbonos().elementAt(i)); 
				if(abono.getEstadoAbono() != Sesion.ABONO_ANULADO){
					sentenciaSQL = "update transaccionabono set transaccionabono.estadoabono = '" + Sesion.ABONO_ANULADO + "',transaccionabono.regactualizado = '" + Sesion.NO + "' where transaccionabono.numtienda = " + abono.getTienda()
					+ " and transaccionabono.numcaja = " + abono.getCaja() + " and transaccionabono.fecha = '" + Control.FECHA_FORMAT.format(abono.getFechaAbono()) + "' and  transaccionabono.numabono = " + abono.getNumAbono() + " and  transaccionabono.numservicio = " + apartado.getNumServicio();
					loteSentenciasCR.addBatch(sentenciaSQL);
					
					//Guardamos en la tabla anulación de abono
					sentenciaSQL = "insert into anulaciondeabonos (numtienda,numcaja,numabono,fechaabono,numabonoanulado,fechaabonoanulado,numservicio) values ("
													+ abono.getTienda() + ", " 
													+ abono.getCaja() + ", "
													+ abono.getNumAbono() + ", " 
													+ "'" + Control.FECHA_FORMAT.format(abono.getFechaAbono()) + "', "
													+ abono.getNumAbono() + ", "
													+ "'" + Control.FECHA_FORMAT.format(Sesion.getFechaSistema()) + "', "
													+ apartado.getNumServicio() + ")";
					loteSentenciasCR.addBatch(sentenciaSQL);
				}
			}
	
			Conexiones.ejecutarLoteSentencias(loteSentenciasCR, false, true);
			
		} catch (SQLException e) {
			logger.error("anularApartado(Apartado)", e);

			throw (new BaseDeDatosExcepcion("Error en sentencia SQL al actualizar el estado del apartado"));
		} catch (ConexionExcepcion e) {
			logger.error("anularApartado(Apartado)", e);

			throw (new BaseDeDatosExcepcion("Error de conexión con BD al registrar el estado del apartado"));
		} finally {
			if (loteSentenciasCR != null) {
				try {
					loteSentenciasCR.close();
				} catch (SQLException e1) {
					logger.error("anularApartado(Apartado)", e1);
				}
				loteSentenciasCR = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("anularApartado(Apartado) - end");
		}
	}

	/**
	 * Actualiza el estado del Abono especificado
	 */
	public static void actualizarEdoAbono(int numServ, Abono abono, char nvoEdo) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarEdoAbono(int, Abono, char) - start");
		}

		Statement loteSentenciasCR  = null;
		try {
			loteSentenciasCR = Conexiones.crearSentencia(false);
			SimpleDateFormat fechaAbono = new SimpleDateFormat("yyyy-MM-dd");
			String sentenciaSQL = "update transaccionabono set transaccionabono.estadoabono = '" + nvoEdo + "', transaccionabono.regactualizado='" + Sesion.NO + "' where transaccionabono.numtienda = " + abono.getTienda()
					+ " and transaccionabono.numcaja = " + abono.getCaja() + " and transaccionabono.fecha = '" + fechaAbono.format(abono.getFechaAbono()) + "' and transaccionabono.numabono = " + abono.getNumAbono() + " and  transaccionabono.numservicio = " + numServ ;
			loteSentenciasCR.addBatch(sentenciaSQL);
			
			if (nvoEdo == Sesion.ABONO_ANULADO) {
				sentenciaSQL = "insert into anulaciondeabonos (numtienda,numcaja,numabono,fechaabono,numabonoanulado,fechaabonoanulado,numservicio) values ("
									+ abono.getTienda() + ", " 
									+ abono.getCaja() + ", "
									+ abono.getNumAbono() + ", " 
									+ "'" + fechaAbono.format(abono.getFechaAbono()) + "', "
									+ abono.getNumAbono() + ", "
									+ "'" + fechaAbono.format(abono.getFechaAbono()) + "', "
									+ numServ + ")";
				loteSentenciasCR.addBatch(sentenciaSQL);
			}
			Conexiones.ejecutarLoteSentencias(loteSentenciasCR, false, true);
			//Conexiones.realizarSentencia(sentenciaSQL,false);
		} catch (SQLException e) {
			logger.error("actualizarEdoAbono(int, Abono, char)", e);

				throw (new BaseDeDatosExcepcion("Error en sentencia SQL al actualizar el estado del abono"));
		} finally {
			if (loteSentenciasCR != null) {
				try {
					loteSentenciasCR.close();
				} catch (SQLException e1) {
					logger.error("actualizarEdoAbono(int, Abono, char)", e1);
				}
				loteSentenciasCR = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarEdoAbono(int, Abono, char) - end");
		}
	}
	
	/**
	 * Obtiene los datos de un Servicio de Apartado realizado
	 * @param tda Tienda donde se realizó el apartado.
	 * @param numServicio numero del servicio de apartado.
	 * @return Vector - Un vector que en la primera posicion contiene la cabecera del apartado y en 
	 * 					la segunda el vector de detalles del mismo.
	 * @throws BaseDeDatosExcepcion Si ocurre un error recuperando el apartado
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Vector<?>> obtenerCotizacion(int tda, int numServicio, Date fechaServ) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCotizacion(int, int, Date) - start");
		}
		
		Vector<Vector<?>> resultado = new Vector<Vector<?>>();
		ResultSet result = null;
		Date fecha, fechaTrans;
		String estadoServ = "";
		SimpleDateFormat fechaStr = new SimpleDateFormat("yyyy-MM-dd");
		// Armamos los vectores del servicio a recuperar
		String sentenciaSQL = "select * from servicio where servicio.codtiposervicio = '" + Sesion.COTIZACION + "' "
							+ " and servicio.numservicio = " + numServicio + " and servicio.numtienda = " + tda 
							+ " and servicio.fecha = '" + fechaStr.format(fechaServ) + "'";
		result = Conexiones.realizarConsulta(sentenciaSQL,false);
		Vector<Object> cabecera = new Vector<Object>();
		try {
			if (result.first()) {
				//CAMBIADO POR MGRILLO PARA AGREGAR EL MANEJO DE COTIZACIONES!!!}
				/*
					Código agregado.
					
				*/
				estadoServ = result.getString("estadoservicio");
				if(estadoServ.equals(""+Sesion.COTIZACION_BLOQUEADA))
					throw new BaseDeDatosExcepcion("Servicio " + numServicio + " bloqueado");
				bloquearCotizacion(numServicio);
				
				tda = result.getInt("numtienda");
				numServicio = result.getInt("numservicio");
				cabecera.addElement(new Integer(result.getInt("numtienda")));
				cabecera.addElement(result.getString("codtiposervicio"));
				cabecera.addElement(new Integer(result.getInt("numservicio")));
				fecha = result.getDate("fecha");
				cabecera.addElement(fecha);
				cabecera.addElement(result.getString("codcliente").trim());
				cabecera.addElement(new Double(result.getDouble("montobase")));
				cabecera.addElement(new Double(result.getDouble("montoimpuesto")));
				cabecera.addElement(new Integer(result.getInt("lineasfacturacion")));
				cabecera.addElement(result.getString("codcajero"));
				cabecera.addElement(new Integer(result.getInt("numtransaccionventa")));
				fechaTrans = result.getDate("fechatransaccionvnta");
				cabecera.addElement(fechaTrans);
				cabecera.addElement(result.getTime("horainicia"));
				cabecera.addElement(result.getTime("horafinaliza"));
				cabecera.addElement(result.getString("estadoservicio"));
			} else {
				throw (new BaseDeDatosExcepcion("No existe la Cotizacion " + numServicio));
			}
		} catch (SQLException e2) {
			logger.error("obtenerCotizacion(int, int, Date)", e2);

			throw (new BaseDeDatosExcepcion("Error al recuperar cotización " + numServicio, e2));
		}
						
		Vector<Vector<Object>> detallesServ = new Vector<Vector<Object>>();
		result = null;
		sentenciaSQL = "select * from detalleservicio where detalleservicio.numtienda= " + tda + " and detalleservicio.fecha = '" + fecha
			+ "' and detalleservicio.codtiposervicio= '" + Sesion.COTIZACION + "' and detalleservicio.numservicio= " + numServicio + " order by detalleservicio.correlativoitem asc";
		result = Conexiones.realizarConsulta(sentenciaSQL,false);
		
		try {
			result.beforeFirst();
			while (result.next()) {
				Vector<Object> detalle = new Vector<Object>();
				detalle.addElement(result.getString("codproducto"));
				detalle.addElement(result.getString("codcondicionventa"));
				detalle.addElement(new Float(result.getFloat("cantidad")));
				detalle.addElement(new Double(result.getDouble("precioregular")));
				detalle.addElement(new Double(result.getDouble("preciofinal")));
				detalle.addElement(new Double(result.getDouble("montoimpuesto")));
				detalle.addElement(result.getString("codtipocaptura"));
				detalle.addElement(new Integer(result.getInt("codpromocion")));
				detalle.addElement(result.getString("estadoregistro"));
				detallesServ.addElement(detalle);
			}
		} catch (SQLException e2) {
			logger.error("obtenerCotizacion(int, int, Date)", e2);

			throw (new BaseDeDatosExcepcion("Error al recuperar cotizacion número " + numServicio, e2));
		}
				
		resultado.addElement(cabecera);
		resultado.addElement(detallesServ);

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCotizacion(int, int, Date) - end");
		}
		return resultado;
	}
	
	
	/**
	 * Desbloquea una cotización para que pueda ser retomada por otra caja
	 * @author mgrillo
	 * @param id identificador de la cotización
	 * @throws BaseDeDatosExcepcion, ConexionExcepcion Si ocurre un error al realizar la consulta
	 */
	public static void desbloquearCotizacion(int id) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("desbloquearCotizacion(int) - start");
		}
		String sentencia = "update servicio set estadoservicio = '"+Sesion.COTIZACION_ACTIVA+"' where numservicio = '"+id+"' and codtiposervicio = '"+Sesion.COTIZACION+"'";
		Conexiones.realizarSentencia(sentencia,false);
		if (logger.isDebugEnabled()) {
			logger.debug("desbloquearCotizacion(int) - end");
		}
	}
	
	/**
	 * Bloquea una cotización para que pueda ser retomada por otra caja
	 * @author mgrillo
	 * @param id identificador de la cotización
	 * @throws BaseDeDatosExcepcion, ConexionExcepcion Si ocurre un error al realizar la consulta
	 */
	public static void bloquearCotizacion(int id) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("desbloquearCotizacion(int) - start");
		}
		String sentencia = "update servicio set estadoservicio = '"+Sesion.COTIZACION_BLOQUEADA+"' where numservicio = '"+id+"' and codtiposervicio = '"+Sesion.COTIZACION+"'";
		Conexiones.realizarSentencia(sentencia,false);
		if (logger.isDebugEnabled()) {
			logger.debug("desbloquearCotizacion(int) - end");
		}
	}
	
	/**
	 * Registra los servicios asociados a la transaccion indicada (en caso de que existan)
	 * @param venta Venta que se registrara en la base de datos.
	 * @param nuevaDir String que indica la dirección de la entrega a domicilio si esta existe
	 * @return Vector Devuelve un vector de dos posiciones que contienen los número de servicio que se guardaron en la BD
	 * 				  Posicion 0 = Número de servicio asociado a la entrega a domiclio asociada a la transacción(en caso de que exista)
	 * 				  Posicion 1 = Número de servicio asociado al despacho asociado a la transacción (en caso de que exista)
	 * 				  Este Vector se devuelve para oobjetivos de impresion de reportes de los servicios
	 * @throws BaseDeDatosExcepcion Si ocurre un error con la insersion en la Base de Datos.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Integer> registrarServicios(Venta venta, String nuevaDir, Statement loteSentenciasCR) throws SQLException, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("registrarServicios(Venta, String, Statement) - start");
		}

		Vector<Integer> numerosServicios = new Vector<Integer>();
		numerosServicios.addElement(new Integer(-1));
		numerosServicios.addElement(new Integer(-1));
		
		//Statement loteSentenciasCR = Conexiones.getConexion(true).createStatement();
		
		//Verificamos si existia un servicio de entrega a domicilio para esta transacción
		if(venta.verificarTieneEntregaDom()) {
			int numEntregaDom = BaseDeDatosServicio.registrarEntregaDomicilio(venta, nuevaDir, loteSentenciasCR);
			numerosServicios.setElementAt(new Integer(numEntregaDom), 0);
		}
			
		//Verificamos si existia un servicio de depacho fuera de caja para esta transacciòn
		if(venta.verificarTieneClienteRetira()) {
			int numDespacho = BaseDeDatosServicio.registrarDespacho(venta, loteSentenciasCR);
			numerosServicios.setElementAt(new Integer(numDespacho), 1);
		}
		
		//Conexiones.ejecutarLoteSentencias(loteSentenciasCR, false, true);
		
		if (logger.isDebugEnabled()) {
			logger.debug("registrarServicios(Venta, String, Statement) - end");
		}
		return numerosServicios;
	}
	
	private static int registrarEntregaDomicilio(Venta venta, String nuevaDir, Statement loteSentenciasCR) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("registrarEntregaDomicilio(Venta, String, Statement) - start");
		}

		try {
			//Statement loteSentenciasCR = Conexiones.getConexion(true).createStatement();
			// Registramos la cabecera del servicio
			String fechaTransString = new SimpleDateFormat("yyyy-MM-dd").format(venta.getFechaTrans());
			String codCliente = (venta.getCliente().getCodCliente() != null)
								? "'" + venta.getCliente().getCodCliente() + "'"
								: "null";
			int numServ = Sesion.getCaja().getNuevoNumServicio(Sesion.ENTDOMICILIO);

			//Buscamos el número de líneas del despacho para registralo en la cabecera de 
			int numDetallesDespachados= 0;
			for(int i=0; i<venta.getDetallesTransaccion().size(); i++) {
				if(((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getTipoEntrega().equals(Sesion.ENTREGA_DOMICILIO)) {
					numDetallesDespachados += 1;
				}
			}
			
			String sentenciaSQL = "insert into servicio (numtienda,codtiposervicio,numservicio,fecha,codcliente,montobase,montoimpuesto,lineasfacturacion,codcajero,numtransaccionventa,fechatransaccionvnta,direcciondespacho,horainicia,horafinaliza,estadoservicio) values (" 
				+ venta.getCodTienda() + ", " 
				+ "'" + Sesion.ENTDOMICILIO + "', "
				+ numServ + ", " 
				+ "'" + fechaTransString + "', "
				+ codCliente.trim() + ", "
				+ venta.getMontoBase() + ", "
				+ venta.getMontoImpuesto() + ", "
				+ numDetallesDespachados + ", "
				+ "'" + venta.getCodCajero() + "', "
				+ venta.getNumTransaccion() + ", "
				+ "'" + fechaTransString + "', "
				+ "'" + nuevaDir+ "', "
				+ "'" + venta.getHoraInicia() + "', "
				+ "'" + venta.getHoraFin() + "', "
				+ "'" + Sesion.SERVICIO_ACTIVA + "')";
			loteSentenciasCR.addBatch(sentenciaSQL);
			
			// Registramos los detalles del servicio
			for (int i=0; i<venta.getDetallesTransaccion().size(); i++) {				
				if(((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getTipoEntrega().equals(Sesion.ENTREGA_DOMICILIO)){
					String promocion = (((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCodPromocion() > 0)
										? String.valueOf(((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCodPromocion())
										: "null";
					sentenciaSQL = "insert into detalleservicio (numtienda,codtiposervicio,numservicio,fecha,codproducto,codcondicionventa,correlativoitem,cantidad,precioregular,preciofinal,montoimpuesto,codtipocaptura,codpromocion) values ("
					+ venta.getCodTienda() + ", " 
					+ "'" + Sesion.ENTDOMICILIO + "', "
					+ numServ + ", " 
					+ "'" + fechaTransString + "', "
					+ "'" + ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getProducto().getCodProducto() + "', "
					+ "'" + ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCondicionVenta() + "', "
					+ (i+1) + ", "
					+ ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCantidad() + ", "
					+ ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getProducto().getPrecioRegular() + ", "
					+ ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getPrecioFinal() + ", "
					+ ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getMontoImpuesto() + ", "
					+ "'" + ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getTipoCaptura() + "', "
					+ promocion + ")";
					loteSentenciasCR.addBatch(sentenciaSQL);
				}
			}			
			//Conexiones.ejecutarLoteSentencias(loteSentenciasCR, false, true);

			if (logger.isDebugEnabled()) {
				logger
						.debug("registrarEntregaDomicilio(Venta, String, Statement) - end");
			}
			return numServ;
		} catch (SQLException e) {
			logger.error("registrarEntregaDomicilio(Venta, String, Statement)",
					e);

			throw (new BaseDeDatosExcepcion("Error en sentencia SQL al registrar la entrega a domicilio"));
		} catch (ConexionExcepcion e) {
			logger.error("registrarEntregaDomicilio(Venta, String, Statement)",
					e);

			throw (new BaseDeDatosExcepcion("Error de conexión con BD al registrar la entrega a domicilio"));
		}
	}

	private static int registrarDespacho(Venta venta, Statement loteSentenciasCR) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("registrarDespacho(Venta, Statement) - start");
		}

		try {
			//Statement loteSentenciasCR = Conexiones.getConexion(true).createStatement();
			// Registramos la cabecera del servicio
			String fechaTransString = new SimpleDateFormat("yyyy-MM-dd").format(venta.getFechaTrans());
			String codCliente = (venta.getCliente().getCodCliente() != null)
								? "'" + venta.getCliente().getCodCliente() + "'"
								: "null";
			int numServ = Sesion.getCaja().getNuevoNumServicio(Sesion.DESPACHO);
			String nuevaDir = " ";

			//Buscamos el número de líneas del despacho para registralo en la cabecera de 
			int numDetallesDespachados= 0;
			for(int i=0; i<venta.getDetallesTransaccion().size(); i++) {
				if(((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getTipoEntrega().equals(Sesion.ENTREGA_CLIENTE_RETIRA)) {
					numDetallesDespachados += 1;
				}
			}
			
			String sentenciaSQL = "insert into servicio (numtienda,codtiposervicio,numservicio,fecha,codcliente,montobase,montoimpuesto,lineasfacturacion,codcajero,numtransaccionventa,fechatransaccionvnta,direcciondespacho,horainicia,horafinaliza,estadoservicio) values (" 
				+ venta.getCodTienda() + ", " 
				+ "'" + Sesion.DESPACHO + "', "
				+ numServ + ", " 
				+ "'" + fechaTransString + "', "
				+ codCliente + ", "
				+ venta.getMontoBase() + ", "
				+ venta.getMontoImpuesto() + ", "
				+ numDetallesDespachados + ", "
				+ "'" + venta.getCodCajero() + "', "
				+ venta.getNumTransaccion() + ", "
				+ "'" + fechaTransString + "', "
				+ "'" + nuevaDir+ "', "
				+ "'" + venta.getHoraInicia() + "', "
				+ "'" + venta.getHoraFin() + "', "
				+ "'" + Sesion.SERVICIO_ACTIVA + "')";
			loteSentenciasCR.addBatch(sentenciaSQL);
			
			// Registramos los detalles del servicio
			for (int i=0; i<venta.getDetallesTransaccion().size(); i++) {				
				if(((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getTipoEntrega().equals(Sesion.ENTREGA_DESPACHO)){
					String promocion = (((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCodPromocion() > 0)
										? String.valueOf(((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCodPromocion())
										: "null";
					sentenciaSQL = "insert into detalleservicio (numtienda,codtiposervicio,numservicio,fecha,codproducto,codcondicionventa,correlativoitem,cantidad,precioregular,preciofinal,montoimpuesto,codtipocaptura,codpromocion) values ("
					+ venta.getCodTienda() + ", " 
					+ "'" + Sesion.DESPACHO + "', "
					+ numServ + ", " 
					+ "'" + fechaTransString + "', "
					+ "'" + ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getProducto().getCodProducto() + "', "
					+ "'" + ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCondicionVenta() + "', "
					+ (i+1) + ", "
					+ ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCantidad() + ", "
					+ ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getProducto().getPrecioRegular() + ", "
					+ ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getPrecioFinal() + ", "
					+ ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getMontoImpuesto() + ", "
					+ "'" + ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getTipoCaptura() + "', "
					+ promocion + ")";
					loteSentenciasCR.addBatch(sentenciaSQL);
				}
			}			
			//Conexiones.ejecutarLoteSentencias(loteSentenciasCR, false, true);

			if (logger.isDebugEnabled()) {
				logger.debug("registrarDespacho(Venta, Statement) - end");
			}
			return numServ;
		} catch (SQLException e) {
			logger.error("registrarDespacho(Venta, Statement)", e);

			throw (new BaseDeDatosExcepcion("Error en sentencia SQL al registrar el despacho"));
		} catch (ConexionExcepcion e) {
			logger.error("registrarDespacho(Venta, Statement)", e);

			throw (new BaseDeDatosExcepcion("Error de conexión con BD al registrar el despacho"));
		}
	}

	/**
	 * Actualiza el estado del Servicio de 'Activo' a 'Anulado'. Igual con todos los abonos del mismo
	 */
	public static void actualizarEdoServicio(int tda, String tipoServ, int numServ, Date fechaServ, char nvoEdo, boolean local) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("actualizarEdoServicio(int, String, int, Date, char, boolean) - start");
		}

		SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy-MM-dd");
		String fechaTransString = fechaTrans.format(fechaServ);
		String sentenciaSQL = "update servicio set servicio.estadoservicio = '" + nvoEdo + "', servicio.regactualizado='" + Sesion.NO + "' where servicio.numtienda=" + tda
				+ " and servicio.fecha = '" + fechaTransString + "' and servicio.codtiposervicio='" + tipoServ
				+ "' and servicio.numservicio=" + numServ;
		
		while (!Sesion.isCajaEnLinea()) {
			logger.error("Caja Fuera de Linea al intentar actualizar estado de servicio");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		Conexiones.realizarSentencia(sentenciaSQL,local);

		if (logger.isDebugEnabled()) {
			logger
					.debug("actualizarEdoServicio(int, String, int, Date, char, boolean) - end");
		}
	}
	
	/**
	 * Actualiza el estado del Servicio de 'Activo' a 'Anulado'. Igual con todos los abonos del mismo
	 * AGREGADO PARRA APARTADOS ESPECIALES
	 */
	public static void actualizarEdoServicio(int tda, String tipoServ, int numServ, Date fechaServ, char nvoEdo, Date fechaVence, boolean local) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("actualizarEdoServicio(int, String, int, Date, char, boolean) - start");
		}

		SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy-MM-dd");
		String fechaTransString = fechaTrans.format(fechaServ);
		String sentenciaSQL = "update servicio set servicio.estadoservicio = '" + nvoEdo + "', servicio.regactualizado='" + Sesion.NO + "', fechavence='"+fechaTrans.format(fechaVence)+"' where servicio.numtienda=" + tda
				+ " and servicio.fecha = '" + fechaTransString + "' and servicio.codtiposervicio='" + tipoServ
				+ "' and servicio.numservicio=" + numServ;
		while (!Sesion.isCajaEnLinea()) {
			logger.error("Caja Fuera de Linea al intentar actualizar estado de servicio");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		Conexiones.realizarSentencia(sentenciaSQL,local);

		if (logger.isDebugEnabled()) {
			logger
					.debug("actualizarEdoServicio(int, String, int, Date, char, boolean) - end");
		}
	}
	
	/**
	 * Obtiene las cotizaciones de un identificador
	 * @param identificador Identificador del servicio.
	 * @return Vector - Un vector con cada una de las cotizaciones con ese identificador.
	 * @throws BaseDeDatosExcepcion Si ocurre un error recuperando el apartado
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Cotizacion> obtenerCotizaciones(String identificador) throws AfiliadoUsrExcepcion, BaseDeDatosExcepcion, ClienteExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCotizaciones(String) - start");
		}

		Vector<Cotizacion> resultado = new Vector<Cotizacion>();
		ResultSet result = null;
		// Armamos los vectores del servicio a recuperar
		String sentenciaSQL = "select * from servicio where servicio.codtiposervicio = '" + Sesion.COTIZACION + "' "
							+ " and (servicio.numservicio = " + identificador + " or RTRIM(servicio.codcliente) like '%" + identificador +"')" 
							+ " order by servicio.fecha desc";
		result = Conexiones.realizarConsulta(sentenciaSQL,false);
		try {
			result.beforeFirst();
			while (result.next()) {
				int tda = result.getInt("numtienda");
				int numServicio = result.getInt("numservicio");
				Date fecha = result.getDate("fecha");
				String codCliente = result.getString("codcliente").trim();
				double mtoBase = result.getDouble("montobase");
				double mtoImpuesto = result.getDouble("montoimpuesto");
				int lineasFacturacion = result.getInt("lineasfacturacion");
				String codCajero = result.getString("codcajero");
				int numTransVta = result.getInt("numtransaccionventa");
				Date fechaTrans = result.getDate("fechatransaccionvnta");
				Time horaInicia = result.getTime("horainicia");
				Time horaFin = result.getTime("horafinaliza");
				char edoServ = result.getString("estadoservicio").toCharArray()[0];

				SimpleDateFormat fechaStr = new SimpleDateFormat("yyyy-MM-dd");
				sentenciaSQL = "select * from detalleservicio where detalleservicio.numtienda= " + tda + " and detalleservicio.fecha = '" + fechaStr.format(fecha)
					+ "' and detalleservicio.codtiposervicio= '" + Sesion.COTIZACION + "' and detalleservicio.numservicio= " + numServicio + " order by detalleservicio.correlativoitem asc";
				ResultSet result2 = Conexiones.realizarConsulta(sentenciaSQL,false);
		
				Vector<Vector<Object>> detallesServ = new Vector<Vector<Object>>();
				try {
					result2.beforeFirst();
					while (result2.next()) {
						Vector<Object> detalle = new Vector<Object>();
						detalle.addElement(result2.getString("codproducto"));
						detalle.addElement(result2.getString("codcondicionventa"));
						detalle.addElement(new Float(result2.getFloat("cantidad")));
						detalle.addElement(new Double(result2.getDouble("precioregular")));
						detalle.addElement(new Double(result2.getDouble("preciofinal")));
						detalle.addElement(new Double(result2.getDouble("montoimpuesto")));
						detalle.addElement(result2.getString("codtipocaptura"));
						detalle.addElement(new Integer(result2.getInt("codpromocion")));
						detalle.addElement(result2.getString("estadoregistro"));
						detallesServ.addElement(detalle);
					}
				} catch (SQLException e2) {
					logger.error("obtenerCotizaciones(String)", e2);

					throw (new BaseDeDatosExcepcion("Error al recuperar cotizacion número " + numServicio, e2));
				} finally {
					result2.close();
					result2 = null;
				}
				resultado.addElement(new Cotizacion(tda,numServicio,fecha,codCliente,mtoBase,mtoImpuesto,
													lineasFacturacion,codCajero,numTransVta,fechaTrans,
													horaInicia,horaFin,edoServ,detallesServ));
			}
		} catch (SQLException e2) {
			logger.error("obtenerCotizaciones(String)", e2);

			throw (new BaseDeDatosExcepcion("Error al recuperar cotización " + identificador, e2));
		} finally {
			try {
				result.close();
			} catch (SQLException e) {
				logger.error("obtenerCotizaciones(String)", e);
			}
			result = null;
		}
		if (resultado.size()>0)
		{
			if (logger.isDebugEnabled()) {
				logger.debug("obtenerCotizaciones(String) - end");
			}
			return resultado;
		}
		else
			throw new BaseDeDatosExcepcion("Cotización con identificador " + identificador + " inexistente.");
	}

	/**
	 * Actualiza la condición del abono del Servicio de Apartado.
	 */
	public static void actualizarCondicionAbono(Apartado apartado, char condAbono, boolean local) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("actualizarCondicionAbono(Apartado, char, boolean) - start");
		}

		SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy-MM-dd");
		String fechaServString = fechaTrans.format(apartado.getFechaServicio());
		String sentenciaSQL = "update servicio set servicio.condicionabono = '" + condAbono + "', servicio.regactualizado='" + Sesion.NO + "' where servicio.numtienda=" + apartado.getCodTienda()
				+ " and servicio.fecha = '" + fechaServString + "' and servicio.codtiposervicio='" + apartado.getTipoServicio()
				+ "' and servicio.numservicio=" + apartado.getNumServicio();
		Conexiones.realizarSentencia(sentenciaSQL,local);

		if (logger.isDebugEnabled()) {
			logger
					.debug("actualizarCondicionAbono(Apartado, char, boolean) - end");
		}
	}
	
	/**
	 * Obtiene los apartados de un cliente
	 * @param identificador Identificador del cliente.
	 * @return Vector - Un vector con cada una de los apartados con ese identificador.
	 * @throws BaseDeDatosExcepcion Si ocurre un error recuperando los apartado
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Apartado> obtenerApartados(String identificador) throws AfiliadoUsrExcepcion, BaseDeDatosExcepcion, ClienteExcepcion, SesionExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerApartados(String) - start");
		}
//BUSQUEDA DE APARTADOS PENDIENTES
		Vector<Apartado> resultado = new Vector<Apartado>();
		ResultSet result = null;
		ResultSet resultEspera = null;
		String sentenciaSQL = null;
		String sentenciaEspera = null;
		// Armamos los vectores del servicio a recuperar
		try {
			sentenciaSQL = "select * from servicio where servicio.codtiposervicio = '" + Sesion.APARTADO + "' "
			+ " and (servicio.numservicio = " + Integer.parseInt(identificador) + " or RTRIM(servicio.codcliente) like '%" + identificador +"') and (servicio.estadoservicio <> '" + Sesion.APARTADO_FACTURADO + "' and servicio.estadoservicio <> '" + Sesion.APARTADO_ANULADO_CON_CARGO + "' and servicio.estadoservicio <> '" + Sesion.APARTADO_ANULADO_EXONERADO + "')"
			+ " order by servicio.fecha desc";
		} catch (NumberFormatException e) {
			logger.error("obtenerApartados(String)", e);

			sentenciaSQL = "select * from servicio where servicio.codtiposervicio = '" + Sesion.APARTADO + "' "
			+ " and (RTRIM(servicio.codcliente) like '%" + identificador +"') and (servicio.estadoservicio <> '" + Sesion.APARTADO_FACTURADO + "' and servicio.estadoservicio <> '" + Sesion.APARTADO_ANULADO_CON_CARGO + "' and servicio.estadoservicio <> '" + Sesion.APARTADO_ANULADO_EXONERADO + "')"
			+ " order by servicio.fecha desc";
		}
		sentenciaEspera = "select * from serviciotemp where serviciotemp.codtiposervicio = '" + Sesion.APARTADO + "' "
		+ " and (RTRIM(serviciotemp.codcliente) like '%" + identificador +"') and " +
			"(serviciotemp.estadoservicio = '" + Sesion.ESPERA + "')"
		+ " order by serviciotemp.fecha desc";
		
		result = Conexiones.realizarConsulta(sentenciaSQL,false);
		resultEspera = Conexiones.realizarConsulta(sentenciaEspera,false);
		
		try {
			result.beforeFirst();
			while (result.next()) {
				int tda = result.getInt("numtienda");
				int numServicio = result.getInt("numservicio");
				Date fecha = result.getDate("fecha");
				String codCliente = result.getString("codcliente").trim();
				double mtoBase = result.getDouble("montobase");
				double mtoImpuesto = result.getDouble("montoimpuesto");
				int lineasFacturacion = result.getInt("lineasfacturacion");
				String codCajero = result.getString("codcajero");
				int numTransVta = result.getInt("numtransaccionventa");
				Date fechaTrans = result.getDate("fechatransaccionvnta");
				Time horaInicia = result.getTime("horainicia");
				Time horaFin = result.getTime("horafinaliza");
				char edoServ = result.getString("estadoservicio").toCharArray()[0];
				int tipoApartado = result.getInt("tipoapartado");
				Date fechaVence = result.getDate("fechavence");
				
				resultado.addElement(new Apartado(tda,numServicio,fecha,codCliente,mtoBase,mtoImpuesto,
													lineasFacturacion,codCajero,numTransVta,fechaTrans,
													horaInicia,horaFin,edoServ,fechaVence,tipoApartado));
			}
		//En espera	
			resultEspera.beforeFirst();
			while (resultEspera.next()) {
				int tda = resultEspera.getInt("numtienda");
				int numServicio = resultEspera.getInt("numservicio");
				Date fecha = resultEspera.getDate("fecha");
				String codCliente = resultEspera.getString("codcliente").trim();
				double mtoBase = resultEspera.getDouble("montobase");
				double mtoImpuesto = resultEspera.getDouble("montoimpuesto");
				int lineasFacturacion = resultEspera.getInt("lineasfacturacion");
				String codCajero = Sesion.usuarioActivo.getNumFicha();//resultEspera.getString("codcajero");
				int numTransVta = resultEspera.getInt("numtransaccionventa");
				Date fechaTrans = resultEspera.getDate("fechatransaccionvnta");
				Time horaInicia = resultEspera.getTime("horainicia");
				Time horaFin = resultEspera.getTime("horafinaliza");
				char edoServ = resultEspera.getString("estadoservicio").toCharArray()[0];
				int tipoApartado = resultEspera.getInt("tipoapartado");
				Date fechaVence = resultEspera.getDate("fechavence");
				
				resultado.addElement(new Apartado(tda,numServicio,fecha,codCliente,mtoBase,mtoImpuesto,
													lineasFacturacion,codCajero,numTransVta,fechaTrans,
													horaInicia,horaFin,edoServ,fechaVence,tipoApartado));
			}
		} catch (SQLException e2) {
			logger.error("obtenerApartados(String)", e2);

			throw (new BaseDeDatosExcepcion("Error al recuperar apartado " + identificador, e2));
		} finally {
			try {
				result.close();
				resultEspera.close();
			} catch (SQLException e1) {
				logger.error("obtenerApartados(String)", e1);
			}
			result = null;
			resultEspera = null;
		}
		if (resultado.size()>0)
		{
			if (logger.isDebugEnabled()) {
				logger.debug("obtenerApartados(String) - end");
			}
			return resultado;
		}
		else
			throw new BaseDeDatosExcepcion("Apartado con identificador " + identificador + " inexistente.");

//		BUSQUEDA DE APARTADOS EN ESPERA

	}


	/**
	 * Método colocarEnEspera
	 * 	Alamacenar en el servidor de tienda el apartado en espera. Se almacena cabecera y detalle de apartado sin abonos
	 * @param apartado
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	public static void colocarEnEspera(Apartado apartado)
			throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("colocarEnEspera(Apartado) - start");
		}
		
		
		Statement loteSentenciasCR = null;
		try {
			loteSentenciasCR = Conexiones.crearSentencia(false);
			// Registramos la cabecera del servicio
			String fechaServString = new SimpleDateFormat("yyyy-MM-dd").format(apartado.getFechaServicio());
			String codCliente = (apartado.getCliente().getCodCliente() != null)
								? "'" + apartado.getCliente().getCodCliente() + "'"
								: "null";
			String condAbono = (apartado.getCondicionAbono() != ' ')
								? "'" + apartado.getCondicionAbono() + "'"
								: "null";
			int numServicioEspera = obtenerNumServicioEspera ();
			String sentenciaSQL = "insert into serviciotemp (numtienda,codtiposervicio,numservicio,fecha,codcliente,montobase,montoimpuesto,lineasfacturacion,condicionabono,codcajero,horainicia,horafinaliza,estadoservicio,tipoapartado,fechavence) values (" 
				+ apartado.getCodTienda() + ", " 
				+ "'" + apartado.getTipoServicio() + "', "
				/*+ apartado.getNumServicio() + ", " */
				+ numServicioEspera + ", "
				+ "'" + fechaServString + "', "
				+ codCliente.trim() + ", "
				+ apartado.getMontoBase() + ", "
				+ apartado.getMontoImpuesto() + ", "
				+ apartado.getLineasFacturacion() + ", "
				+ condAbono + ", "
				+ "'" + apartado.getCodCajero() + "', "
				+ "'" + apartado.getHoraInicia() + "', "
				+ "'" + apartado.getHoraFin() + "', "
				+ "'" + apartado.getEstadoServicio() + "', " 
				+ apartado.getTipoApartado()+", " 
				+"'"+apartado.getFechaVencimiento()+"')";
			//Conexiones.realizarSentencia(sentenciaSQL,true);
			loteSentenciasCR.addBatch(sentenciaSQL);
			
			// Registramos los detalles del servicio
			for (int i=0; i<apartado.getDetallesServicio().size(); i++) {
				String promocion = (((DetalleServicio)apartado.getDetallesServicio().elementAt(i)).getCodPromocion() > 0)
									? String.valueOf(((DetalleServicio)apartado.getDetallesServicio().elementAt(i)).getCodPromocion())
									: "null";
				sentenciaSQL = "insert into detalleserviciotemp (numtienda,codtiposervicio,numservicio,fecha,codproducto,codcondicionventa,correlativoitem,cantidad,precioregular,preciofinal,montoimpuesto,codtipocaptura,codpromocion) values ("
					+ apartado.getCodTienda() + ", " 
					+ "'" + apartado.getTipoServicio() + "', "
					/*+ apartado.getNumServicio() + ", " */
					+ numServicioEspera + ", "
					+ "'" + fechaServString + "', "
					+ "'" + ((DetalleServicio)apartado.getDetallesServicio().elementAt(i)).getProducto().getCodProducto() + "', "
					+ "'" + ((DetalleServicio)apartado.getDetallesServicio().elementAt(i)).getCondicionVenta() + "', "
					+ (i+1) + ", "
					+ ((DetalleServicio)apartado.getDetallesServicio().elementAt(i)).getCantidad() + ", "
					+ ((DetalleServicio)apartado.getDetallesServicio().elementAt(i)).getProducto().getPrecioRegular() + ", "
					+ ((DetalleServicio)apartado.getDetallesServicio().elementAt(i)).getPrecioFinal() + ", "
					+ ((DetalleServicio)apartado.getDetallesServicio().elementAt(i)).getMontoImpuesto() + ", "
					+ "'" + ((DetalleServicio)apartado.getDetallesServicio().elementAt(i)).getTipoCaptura() + "', "
					+ promocion + ")";
				//Conexiones.realizarSentencia(sentenciaSQL,true);
				loteSentenciasCR.addBatch(sentenciaSQL);
				
				(new ActualizadorPreciosSACFactory()).getActualizadorPreciosSACInstance().agregarInsertServicioCondiciones(apartado, loteSentenciasCR, fechaServString, sentenciaSQL, i, true, numServicioEspera);
			}
			
			
			Conexiones.ejecutarLoteSentencias(loteSentenciasCR, false, true);
			
			//Realizado por WDíaz 27-10-2009. Sincroniza afiliados por Apartados en Espera con Clientes Nuevos o Actualizados
			  //BeansSincronizador.syncAfiliado(true);
		} catch (SQLException e) {
			logger.error("registrarApartado(Apartado)", e);

			throw (new BaseDeDatosExcepcion("Error en sentencia SQL al registrar el apartado"));
		} catch (ConexionExcepcion e) {
			logger.error("registrarApartado(Apartado)", e);

			throw (new BaseDeDatosExcepcion("Error de conexión con BD al registrar el apartado en espera"));
		} finally {
			if (loteSentenciasCR != null) {
				try {
					loteSentenciasCR.close();
				} catch (SQLException e1) {
					logger.error("registrarApartado(Apartado)", e1);
				}
				loteSentenciasCR = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("registrarApartado(Apartado) - end");
		}
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Object> obtenerListaRegalosCerrada(String numLista) throws BaseDeDatosExcepcion, ConexionExcepcion{
		return obtenerListaRegalos(numLista,false);
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Object> obtenerListaRegalos(String numLista) throws BaseDeDatosExcepcion, ConexionExcepcion {
		return obtenerListaRegalos(numLista,true);
	}

	/**
	 * Obtiene la lista de regalos de la base de datos
	 * 
	 * @param codLista
	 * @return
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private static Vector<Object> obtenerListaRegalos(String numLista, boolean activa) throws BaseDeDatosExcepcion, ConexionExcepcion {
		Vector<Object> resultado = new Vector<Object>(3);
		Vector<Object> cabecera = new Vector<Object>(18);
		Vector<Vector<Object>> detalles = new Vector<Vector<Object>>();
		Vector<Vector<Object>> operaciones = new Vector<Vector<Object>>();
		ResultSet result = null;
		String sentenciaSQL = "";
		
		if(activa)
			sentenciaSQL = "select * from listaregalos where listaregalos.codlista = '"+numLista+"' and estado = 'A'";
		else
			sentenciaSQL = "select * from listaregalos where listaregalos.codlista = '"+numLista+"' and estado = 'C'";

		result = Conexiones.realizarConsulta(sentenciaSQL,false);
		
		try {
			result.beforeFirst();
			if(result.first()){
				cabecera.addElement(new Integer(result.getInt("codlista")));
				cabecera.addElement(result.getString("tipolista"));
				cabecera.addElement(result.getString("codtitular"));
				cabecera.addElement(result.getDate("fechaevento"));
				cabecera.addElement(result.getString("tipoevento"));
				cabecera.addElement(result.getString("titularsec"));
				cabecera.addElement(result.getDate("fechaapertura"));
				cabecera.addElement(new Integer(result.getInt("numtiendaapertura")));
				cabecera.addElement(new Integer(result.getInt("numcajaapertura")));
				cabecera.addElement(result.getString("codcajeroapertura"));
				cabecera.addElement(result.getDate("fechacierre"));
				cabecera.addElement(new Integer(result.getInt("numtiendacierre")));
				cabecera.addElement(new Integer(result.getInt("numcajacierre")));
				cabecera.addElement(result.getString("codcajerocierre"));
				cabecera.addElement(new Double(result.getDouble("montobase")));
				cabecera.addElement(new Double(result.getDouble("montoimpuesto")));
				cabecera.addElement(new Integer(result.getInt("cantproductos")));
				cabecera.addElement(new Double(result.getDouble("montoabonos")));
				cabecera.addElement(result.getString("notificaciones"));
				cabecera.addElement(result.getString("permitirventa"));
			} else {
				throw new BaseDeDatosExcepcion("No existe la lista de regalos "+numLista);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		sentenciaSQL = "select * from detallelistaregalos where detallelistaregalos.codlista = '"+numLista+"' and cantidad>0";
				
		result = Conexiones.realizarConsulta(sentenciaSQL,false);
		try {
			result.beforeFirst();
			while(result.next()){
				Vector<Object> detalle = new Vector<Object>(13);
				String codlista = result.getString("codlista");
				detalle.addElement(codlista);
				String codproducto = result.getString("codproducto");
				detalle.addElement(codproducto);
				//detalle.addElement(result.getString("descproducto"));
				detalle.addElement(new Integer(result.getInt("correlativoitem")));
				detalle.addElement(new Integer(result.getInt("cantidad")));
				detalle.addElement(new Double(result.getDouble("precioregular")));
				detalle.addElement(new Double(result.getDouble("preciofinal")));
				detalle.addElement(new Double(result.getDouble("montoimpuesto")));
				detalle.addElement(result.getString("codtipocaptura"));
				detalle.addElement(new Integer(result.getInt("codpromocion")));
				detalle.addElement(new Integer(result.getInt("cantcomprado")));

				String query = "select sum(op.cantidad) as cantcancelados from operacionlistaregalos op where " +
							"op.tipooperacion = 'T' and op.codlista = "+codlista+" and op.codproducto = '"+codproducto+"'";
				Integer cantcancelados = new Integer (0);
				try {
					cantcancelados = new Integer((Conexiones.realizarConsulta(query,false)).getInt("cantcancelados"));
				} catch (Exception e) {}			
				detalle.addElement(cantcancelados);
				
				query = "select sum(montobase) as montoabonos from operacionlistaregalos where " +
							"tipooperacion = 'A' and codlista = '"+codlista+"' and codproducto = '"+codproducto+"'";
				Double montoabonos = new Double(0);
				try {
					montoabonos = new Double((Conexiones.realizarConsulta(query,false)).getDouble("montoabonos"));
				} catch (Exception e) {}
				detalle.addElement(montoabonos);
				detalles.addElement(detalle);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	
		// Obtenemos los detalles producto de Ventas con consecutivos de precio distintos y los agregamos a los detalles actuales
		obtenerDetallesNoPedidos(numLista, detalles);
		
		
//		Primero extraigo las operaciones de abono a la lista
		sentenciaSQL = "select op.codproducto, op.montobase, op.montoimpuesto, op.nomcliente, op.fecha, op.dedicatoria,"
						+ "op.numtienda, op.numtransaccion, op.numoperacion, op.codcajero from operacionlistaregalos as op "
						+ "where op.codlista = '"+numLista+"' and op.codproducto = '000000000000' and op.tipooperacion = '"+OperacionLR.ABONO_LISTA+"'";
		
		result = Conexiones.realizarConsulta(sentenciaSQL,false);
		try {
			result.beforeFirst();
			while(result.next()){
				Vector<Object> operacion = new Vector<Object>(9);
				operacion.addElement(new Integer(result.getInt("numoperacion")));
				operacion.addElement(new Integer(result.getInt("numtransaccion")));
				operacion.addElement(result.getString("codproducto"));
				operacion.addElement(new String("ABONO A LISTA"));
				operacion.addElement(result.getString("nomcliente"));
				operacion.addElement(new Double(result.getDouble("montobase")));
				operacion.addElement(new Double(result.getDouble("montoimpuesto")));
				operacion.addElement(new Integer(1));
				operacion.addElement(result.getDate("fecha"));
				operacion.addElement(result.getString("dedicatoria"));
				operacion.addElement(new Character('L'));
				operacion.addElement(new Integer(result.getInt("numtienda")));
				operacion.addElement(result.getString("codcajero"));
				operaciones.addElement(operacion);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
//		Luego extraigo las operaciones de compras y abonos a productos
		sentenciaSQL = "SELECT op.numtransaccion,op.numoperacion,op.codproducto, prod.descripcionlarga, op.nomcliente, op.montobase, op.montoimpuesto, op.cantidad, op.fecha, op.dedicatoria, " 
						+ "op.tipooperacion, op.numtienda, op.codcajero FROM operacionlistaregalos AS op INNER JOIN producto AS prod ON (op.codproducto = prod.codproducto) " 
						+ "WHERE op.codlista = '"+numLista+"'";

		result = Conexiones.realizarConsulta(sentenciaSQL,false);
		try {
			result.beforeFirst();
			while(result.next()){
				Vector<Object> operacion = new Vector<Object>(9);
				operacion.addElement(new Integer(result.getInt("numoperacion")));
				operacion.addElement(new Integer(result.getInt("numtransaccion")));
				operacion.addElement(result.getString("codproducto"));
				operacion.addElement(result.getString("descripcionlarga"));
				operacion.addElement(result.getString("nomcliente"));
				operacion.addElement(new Double(result.getDouble("montobase")));
				operacion.addElement(new Double(result.getDouble("montoimpuesto")));
				operacion.addElement(new Integer(result.getInt("cantidad")));
				operacion.addElement(result.getDate("fecha"));
				operacion.addElement(result.getString("dedicatoria"));
				operacion.addElement(new Character(result.getString("tipooperacion").toCharArray()[0]));
				operacion.addElement(new Integer(result.getInt("numtienda")));
				operacion.addElement(result.getString("codcajero"));
				operaciones.addElement(operacion);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		resultado.addElement(cabecera);
		resultado.addElement(detalles);
		resultado.addElement(operaciones);
		
		Conexiones.cerrarConexion(false,false);
						
		return resultado;
	}
	
	/**
	 * Obtiene los productos vendidos con distinto consecutivo al solicitado
	 * 
	 * @param numLista
	 * @param detallesOriginales
	 * @return
	 */
	public static void obtenerDetallesNoPedidos(String numLista, Vector<Vector<Object>> detallesOriginales) {
		String sentenciaSQL = "select * from detallelistaregalos where detallelistaregalos.codlista = '"+numLista+"' and cantidad=0";
		ResultSet result = null;
		
		try {
			result = Conexiones.realizarConsulta(sentenciaSQL,false);
			result.beforeFirst();
			while(result.next()){
				String codProducto = result.getString("codproducto");
				int compradosConsec = result.getInt("cantcomprado");
				
				// Obtener posición(es) del Vector donde se encuentra(n) el/los producto(s) similar(es) con otro consecutivo
				Vector<Integer> posiciones = obtenerRenglones(codProducto, detallesOriginales);
				
				int i=0;
				while ((compradosConsec>0) && (i<posiciones.size())) {
					Vector<Object> detalleActual =  detallesOriginales.elementAt(((Integer)posiciones.elementAt(i)).intValue());
					//String producto = (String)detalleActual.elementAt(1);
					int pedidos = ((Integer)detalleActual.elementAt(3)).intValue();
					int vendidos = ((Integer)detalleActual.elementAt(9)).intValue();
					int restantes = pedidos - vendidos;
					if (restantes > 0) {
						if (compradosConsec <= restantes) {
							detalleActual.set(9, new Integer(vendidos + compradosConsec));
							compradosConsec = 0;
						} else {
							detalleActual.set(9, new Integer(vendidos + restantes));
							compradosConsec -= restantes;
						}
					}
					i++;
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try{result.close();}catch (Exception e) {}
		}
	}	

	/**
	 * Obtiene los renglones de los productos originales
	 * 
	 * @param numLista
	 * @param detallesOriginales
	 * @return
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Integer> obtenerRenglones(String codProducto, Vector<Vector<Object>> detallesOriginales) {
		Vector<Integer> resultado = new Vector<Integer>();
		for (int i=0; i<detallesOriginales.size(); i++) {
			Vector<Object> detalleActual =  detallesOriginales.elementAt(i);
			String producto = (String)detalleActual.elementAt(1);
			if (producto.substring(0, 9).equals(codProducto.substring(0, 9))) {
				resultado.add(new Integer(i));
			}
		}
		
		return resultado;
	}
		
	/**
	 * Obtiene el afiliado de la base de datos
	 * 
	 * @param codAfiliado
	 * @return
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("rawtypes")
	public static Vector obtenerAfiliado(String codAfiliado) throws BaseDeDatosExcepcion, ConexionExcepcion{
		Vector resultado = new Vector();
		ResultSet result = null;
		String sentenciaSQL = null;
		
		sentenciaSQL = "select * from afiliado where afiliado.codafiliado like '%"+codAfiliado+"'";
		
		result = Conexiones.realizarConsulta(sentenciaSQL,true);
		
		try {
			result.beforeFirst();
			while (result.next()) {
				result.getString("nombre");
			}
		}catch(Exception e){
			
		}
		finally{
			try {
				result.close();
			} catch (SQLException e1) {
				logger.error("obtenerApartados(String)", e1);
			}
		}
		if (resultado.size()>0) {
			return resultado;
		}
		else
			throw new BaseDeDatosExcepcion("Afiliado con codigo " + codAfiliado + " no existe.");
	}
	
	
	/**
	 * Registra una nueva lista de regalos
	 * 
	 * @param listaRegalos
	 * @return
	 * @throws BaseDeDatosExcepcion
	 */
	public static int registrarListaRegalos(ListaRegalos listaRegalos) throws BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("registrarListaRegalos(ListaRegalos) - start");
		}
		String codLista = "";

		Statement loteSentencias = null;
		try {
			loteSentencias = Conexiones.crearSentencia(false);

			// Registramos la cabecera del servicio
			String fechaAperturaString = new SimpleDateFormat("yyyy-MM-dd").format(listaRegalos.getFechaApertura());
			String fechaCierreString = "";
			String fechaEventoString = new SimpleDateFormat("yyyy-MM-dd").format(listaRegalos.getFechaEvento());
			String codTitular = listaRegalos.getCliente().getCodCliente();
			codLista = obtenerNuevoCodLR();
			String notificaciones = listaRegalos.isNotificaciones()
								? "S"
								: "N";
			String permitirventa = listaRegalos.isPermitirVenta()
								? "S"
								: "N";

			// Registramos la lista en el servidor central primero para asegurar que reserva su nuevo codigo.
			String fecha = new SimpleDateFormat("yyyyMMddhhmmss").format(Sesion.getFechaSistema());		
			String sentenciaSQL = "insert into cr.listaregaloscentral (codlista,titular,titularsec,fechaevento,tipoevento," +
				"numtienda,notificaciones,estado,tiendaultestado,fechaultestado) values("
				+ codLista + ", "
				+ "'" + listaRegalos.getCliente().getNombreCompleto() + "', "
				+ "'" + listaRegalos.getTitularSec() + "', "
				+ "'" + fechaEventoString + "', "
				+ "'" + listaRegalos.getTipoEvento() + "', "
				+ Sesion.getTienda().getNumero() + ", "
				+ "'" + notificaciones + "', "
				+ "'" + Sesion.LISTAREGALOS_INACTIVA + "', "
				+ Sesion.getTienda().getNumero() + ", "
				+ "'" + fecha + "')";
				
			ConexionServCentral.realizarSentencia(sentenciaSQL);
			
			sentenciaSQL = "insert into listaregalos (codlista,tipolista,codtitular,fechaevento,tipoevento,titularsec,fechaapertura," +
				"numtiendaapertura,numcajaapertura,codcajeroapertura,fechacierre,numtiendacierre,numcajacierre,codcajerocierre," +
				"montobase,montoimpuesto,cantproductos,notificaciones,permitirventa,estado) values (" 
				+ codLista + ", " 
				+ "'" + listaRegalos.getTipoLista() + "', " 
				+ "'" + codTitular + "', "
				+ "'" + fechaEventoString + "', "
				+ "'" + listaRegalos.getTipoEvento() + "', "
				+ "'" + listaRegalos.getTitularSec() + "', "
				+ "'" + fechaAperturaString + "', "
				+ listaRegalos.getCodTienda() + ", "
				+ listaRegalos.getCodCajaApertura() + ", "
				+ "'" + listaRegalos.getCodCajero() + "', "
				+ "'" + fechaCierreString + "', "
				+ "null" + ", "
				+ "null" + ", "
				+ "null" + ", "
				+ listaRegalos.getMontoBase() + ", "
				+ listaRegalos.getMontoImpuesto() + ", "
				+ listaRegalos.getCantPedidos() + ", "
				+ "'" + notificaciones + "', "
				+ "'" + permitirventa + "', "
				+ "'" + Sesion.LISTAREGALOS_INACTIVA + "')";

			loteSentencias.addBatch(sentenciaSQL);
		
			// Registramos los detalles del servicio
			for (int i=0; i<listaRegalos.getDetallesServicio().size(); i++) {
				String promocion = (((DetalleServicio)listaRegalos.getDetallesServicio().elementAt(i)).getCodPromocion() > 0)
									? String.valueOf(((DetalleServicio)listaRegalos.getDetallesServicio().elementAt(i)).getCodPromocion())
									: "null";
				sentenciaSQL = "insert into detallelistaregalos (codlista,codproducto,correlativoitem,cantidad,precioregular," +
								"preciofinal,montoimpuesto,codtipocaptura,codpromocion,cantcomprado,montoabonos) values ("
					+ codLista + ", " 
					+ "'" + ((DetalleServicio)listaRegalos.getDetallesServicio().elementAt(i)).getProducto().getCodProducto() + "', "
					+ (i+1) + ", "
					+ ((DetalleServicio)listaRegalos.getDetallesServicio().elementAt(i)).getCantidad() + ", "
					+ ((DetalleServicio)listaRegalos.getDetallesServicio().elementAt(i)).getProducto().getPrecioRegular() + ", "
					+ ((DetalleServicio)listaRegalos.getDetallesServicio().elementAt(i)).getPrecioFinal() + ", "
					+ ((DetalleServicio)listaRegalos.getDetallesServicio().elementAt(i)).getMontoImpuesto() + ", "
					+ "'" + ((DetalleServicio)listaRegalos.getDetallesServicio().elementAt(i)).getTipoCaptura() + "', "
					+ promocion + ", "
					+ "0" +", "
					+ "0.0" + ")";

				loteSentencias.addBatch(sentenciaSQL);
			}
			
			Conexiones.ejecutarLoteSentencias(loteSentencias, false, true);
			
			fecha = new SimpleDateFormat("yyyyMMddhhmmss").format(Sesion.getFechaSistema());
			sentenciaSQL = "update cr.listaregaloscentral set estado= '"+Sesion.LISTAREGALOS_ACTIVA+"', "
						+ "tiendaultestado="+Sesion.getTienda().getNumero()+", fechaultestado= '"+ fecha +"' "
						+ "where codlista = " + codLista;
			ConexionServCentral.realizarSentencia(sentenciaSQL);
						
			sentenciaSQL = "update CR.listaregalos set estado = '"+Sesion.LISTAREGALOS_ACTIVA+"' where codlista = " + codLista;
			Conexiones.realizarSentencia(sentenciaSQL,false);
		} catch (SQLException e) {
			logger.error("registrarListaRegalos(ListaRegalos)", e);
			throw (new BaseDeDatosExcepcion("Error en sentencia SQL al registrar la lista de regalos"));
		} catch (ConexionExcepcion e) {
			logger.error("registrarListaRegalos(ListaRegalos)", e);
			throw (new BaseDeDatosExcepcion("Error de conexión con BD al registrar la lista de regalos"));
		} finally {
			if (loteSentencias != null) {
				try {
					loteSentencias.close();
				} catch (SQLException e1) {
					logger.error("registrarListaRegalos(ListaRegalos)", e1);
				}
				loteSentencias = null;
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("registrarListaRegalos(ListaRegalos) - end");
		}
		return Integer.parseInt(codLista);
	}
	
	/**
	 * Obtiene un nuevo número para asignarlo a una lista de regalos
	 * 
	 * @return
	 */
	private static String obtenerNuevoCodLR(){
		int resultado = 0;
		String sentenciaSQL;
		ResultSet result;
		
		sentenciaSQL = "SELECT MAX(codlista) AS codlista FROM cr.listaregaloscentral";
		try {
			result = ConexionServCentral.realizarConsulta(sentenciaSQL);
			resultado = result.getInt("codlista");
		} catch (BaseDeDatosExcepcion e) {
		} catch (ConexionExcepcion e) {
		} catch (SQLException e) {
		}
		return String.valueOf(resultado + 1);
	}
	
	/**
	 * Recupera el encabezado de la cantidad de listas de regalos indicadas
	 * 
	 * @param item
	 * @param campoBusqueda
	 * @param limiteInf
	 * @param nombreColumna
	 * @return
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Object[][] recuperarListas(String item, String campoBusqueda, String nombreColumna){
		Object datos[][] = null;
		Vector<String> datos_res = new Vector<String>();
		ResultSet resultado = null;
		String consulta = "";
		int numFilas, numColumnas = 6;//,listasActivas = 0;
		
		/*try {
			listasActivas = (ConexionServCentral.realizarConsulta("select count(*) from cr.listaregaloscentral where estado='A'")).getInt(1);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		/*limiteInf += 13;
		
		int filas = (limiteInf<listasActivas)
				  ? 13
				  : Math.abs(limiteInf-listasActivas-13);*/
		
		if(campoBusqueda.toLowerCase().equals("titular")){
			/*consulta = "select * from (select * from (select codlista,titular,titularsec,tipoevento,fechaevento,numtienda,estado "
				+ "from cr.listaregaloscentral where (cr.listaregaloscentral.titular like '%"+ item +"%' or cr.listaregaloscentral.titularsec " 
				+ "like '%"+ item +"%') and cr.listaregaloscentral.estado = '"+Sesion.LISTAREGALOS_ACTIVA+"' order by titular "
				+ "asc fetch first " + limiteInf + " rows only) limitinf order by titular "
				+ "desc fetch first "+filas+" rows only) cantitems order by codlista asc";
			*/
			consulta = "select codlista, titular, titularsec, tipoevento, fechaevento, numtienda, estado "
					+ "from cr.listaregaloscentral where (cr.listaregaloscentral.titular like '%" + item 
					+ "%' or cr.listaregaloscentral.titularsec like '%" + item + "%') and cr.listaregaloscentral.estado = '" 
					+ Sesion.LISTAREGALOS_ACTIVA + "' order by titular asc";
		}else{
			/*consulta = "select * from (select * from (select codlista,titular,titularsec,tipoevento,fechaevento,numtienda,estado "
				+ "from cr.listaregaloscentral where cr.listaregaloscentral." + campoBusqueda.toLowerCase() + " like '%"+ item 
				+ "%' and cr.listaregaloscentral.estado = '"+Sesion.LISTAREGALOS_ACTIVA+"' order by " + campoBusqueda.toLowerCase()
				+ " asc fetch first " + limiteInf + " rows only) limitinf order by " + campoBusqueda.toLowerCase()
				+ " desc fetch first "+filas+" rows only) cantitems order by codlista asc";
			*/
			consulta = "select codlista, titular, titularsec, tipoevento, fechaevento, numtienda, estado "
					+ "from cr.listaregaloscentral where cr.listaregaloscentral." + campoBusqueda.toLowerCase() + " like '%"
					+ item + "%' and cr.listaregaloscentral.estado = '" + Sesion.LISTAREGALOS_ACTIVA + "' order by " + campoBusqueda.toLowerCase()
					+ " asc";
		}

		try {
			resultado = ConexionServCentral.realizarConsulta(consulta);
		} catch (BaseDeDatosExcepcion e1) {
			e1.printStackTrace();
		} catch (ConexionExcepcion e1) {
			e1.printStackTrace();
		}
					
		try {
			// Creamos los datos
			resultado.beforeFirst();
			while (resultado.next()) {
				//if(resultado.getString("estado").toCharArray()[0] == Sesion.LISTAREGALOS_ACTIVA){
				datos_res.addElement(resultado.getString("codlista"));
				datos_res.addElement(resultado.getString("titular"));
				datos_res.addElement(resultado.getString("titularsec"));
				datos_res.addElement(resultado.getString("tipoevento"));
				datos_res.addElement(resultado.getString("fechaevento"));
				datos_res.addElement(resultado.getString("numtienda"));
				//}
			}
		} catch (Exception e) {
			logger.error("recuperarListas(String, String, int, int, String)", e);
		} finally {
			if (resultado != null) {
				try {
					resultado.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				resultado = null;
			}
		}

		datos_res.trimToSize();
		numFilas = datos_res.size()/numColumnas;
		datos = new Object[numFilas][numColumnas];
		int j=0,k=0;
		for(int i=0;i<datos_res.size();i++){
			datos[j][k] = datos_res.get(i);
			k++;
			if((i+1)%numColumnas == 0){
				j++;
				k=0;
			}
		}

		return datos;		
//		datosRetorno.addElement(datos);
//		return datosRetorno;
	}
	


	/**
	 * Realiza las operaciones requeridas para cerrar una lista de regalos
	 * 
	 * @param lista
	 * @param estado
	 * @param detallesVenta
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static void cerrarListaRegalos(ListaRegalos lista, char estado, Vector<DetalleTransaccion> detallesVenta) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ExcepcionCr, ConexionExcepcion, PrinterNotConnectedException {
		String sentenciaSQL = "";
		String fechaCierre = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		if(lista.getCodTiendaApertura()!= lista.getCodTienda())
			cerrarListaRegalosRemota(lista,estado,detallesVenta);
		else {
			sentenciaSQL = "update CR.listaregalos set "
						+ "fechacierre = '" + fechaCierre + "', "
						+ "numtiendacierre = " + lista.getCodTiendaCierre() + ", "
						+ "numcajacierre = " + lista.getCodCajaCierre() + ", "
						+ "codcajerocierre = '" + lista.getCodCajeroCierre() + "', "
						+ "estado = '" + estado + "' "
						+ "where codlista = " + lista.getNumServicio();
						
			Conexiones.realizarSentencia(sentenciaSQL,false);
			
			String fecha = new SimpleDateFormat("yyyyMMddhhmmss").format(Sesion.getFechaSistema());		
			sentenciaSQL = "update CR.listaregaloscentral set estado= '"+estado+"',"
						+ "fechaultestado='"+fecha+"',tiendaultestado="+Sesion.getTienda().getNumero()+" "
						+ "where codlista = " + lista.getNumServicio();
			ConexionServCentral.realizarSentencia(sentenciaSQL);
		}
	}
	
	/**
	 * Realiza las operaciones necesarias para realizar el cierre de una
	 * lista de regalos que pertenece a una tienda distinta
	 * 
	 * @param lista
	 * @param estado
	 * @param detallesVenta
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private static void cerrarListaRegalosRemota(ListaRegalos lista, char estado, Vector<?> detallesVenta) throws BaseDeDatosExcepcion, ConexionExcepcion {
		String dbUrlServidor = "", consulta = "";
		ResultSet result = null;
		String fechaCierre = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		
		try{
			consulta = "select dburlservidor from servidortienda where numtienda = "+lista.getCodTiendaApertura();
			result = Conexiones.realizarConsulta(consulta,true);
			dbUrlServidor = result.getString("dburlservidor");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		IXMLElement solicitud = new XMLElement("listaregalos");
		solicitud.setAttribute("tipo","cierre");
		
		IXMLElement element = solicitud.createElement("codlista");
		solicitud.addChild(element);
		element.setContent(String.valueOf(lista.getNumServicio()));

		element = solicitud.createElement("fechacierre");
		solicitud.addChild(element);
		element.setContent(fechaCierre);
		
		element = solicitud.createElement("numtiendacierre");
		solicitud.addChild(element);
		element.setContent(String.valueOf(lista.getCodTiendaCierre()));

		element = solicitud.createElement("numcajacierre");
		solicitud.addChild(element);
		element.setContent(String.valueOf(lista.getCodCajaCierre()));

		element = solicitud.createElement("codcajerocierre");
		solicitud.addChild(element);
		element.setContent(lista.getCodCajeroCierre());
		
		element = solicitud.createElement("estado");
		solicitud.addChild(element);
		element.setContent(String.valueOf(estado));
		
		IXMLElement detalles = solicitud.createElement("detallesventa");
		solicitud.addChild(detalles);
				
		for(int i=0;i<detallesVenta.size();i++){
			IXMLElement detalle = detalles.createElement("detalle");
			detalles.addChild(detalle);
				
			Detalle detalleVenta = (Detalle)detallesVenta.get(i);
					
			element = detalle.createElement("codproducto");
			detalle.addChild(element);
			element.setContent(detalleVenta.getProducto().getCodProducto());
					
			element = detalle.createElement("montobase");
			detalle.addChild(element);
			element.setContent(String.valueOf(detalleVenta.getPrecioFinal()));
					
			element = detalle.createElement("montoimpuesto");
			detalle.addChild(element);
			element.setContent(String.valueOf(detalleVenta.getMontoImpuesto()));
								
			element = detalle.createElement("cantidad");
			detalle.addChild(element);
			element.setContent(String.valueOf(detalleVenta.getCantidad()));
		}
		
		ConexionTiendaRemota.ejecutarSentencia(solicitud,dbUrlServidor);
	}
	
	/**
	 * Registra una venta de una lista de regalos
	 * 
	 * @param numTienda
	 * @param numTiendaOrigen
	 * @param numCaja
	 * @param codCajero
	 * @param codLista
	 * @param codCliente
	 * @param nomCliente
	 * @param dedicatoria
	 * @param detallesVenta
	 * @throws ConexionExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static void registrarVentaLR(int numTransaccion,int numTienda,int numTiendaOrigen,int numCaja, String codCajero,int codLista,
						String codCliente,String nomCliente,String dedicatoria, Vector<?> detallesVenta) throws ConexionExcepcion {
		if(numTienda != numTiendaOrigen)
			// Si la lista pertenece a otra tienda se hace la peticion remota
			registrarVentaLRRemota(numTransaccion,numTienda,numTiendaOrigen,numCaja,codCajero,codLista,codCliente,nomCliente,dedicatoria,detallesVenta);
		else {
			Detalle detalle = null;
			ResultSet resultado = null;
			String sentenciaSQL = "";
			String codProd = "";
			Statement loteSentencias = null;
			float cantComprado = 0;
			int numOperacion = 0;
			
			String fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			try {
				loteSentencias = Conexiones.crearSentencia(false);

				// Se asigna un nuevo numoperacion 
				sentenciaSQL = "select ifnull(max(numoperacion),0) as numoperacion from operacionlistaregalos "
								+ "where codlista = '" + codLista + "'";
				resultado = Conexiones.realizarConsulta(sentenciaSQL,false);
				if (resultado.first())
					numOperacion = (resultado.getInt("numoperacion"))+1;


				for(int i=0;i<detallesVenta.size();i++){
					detalle = (Detalle)detallesVenta.get(i);
					codProd = detalle.getProducto().getCodProducto();
	
					sentenciaSQL = "select cantcomprado from detallelistaregalos where codproducto = '"
								+ codProd + "' and codlista = '" + codLista + "'";
								
					resultado = Conexiones.realizarConsulta(sentenciaSQL,false);
					if (resultado.first())
						cantComprado = resultado.getInt("cantcomprado");
					
					cantComprado += detalle.getCantidad();
					
					// Si el producto no existia en el detalle de la lista entonces lo agrega colocando la cantidad
					// pedida como cero. Esto debería suceder solo en el Cierre de Lista cuando los titulares agreguen
					// nuevos productos para llevarse.
					sentenciaSQL = "update detallelistaregalos set detallelistaregalos.cantcomprado = '"
									+ cantComprado + "' where codproducto = '" + codProd + "' and codlista = '"
									+ codLista + "'";
					if(Conexiones.realizarSentencia(sentenciaSQL,false) == 0){
						String promocion = detalle.getCodPromocion() > 0
										 ? String.valueOf(detalle.getCodPromocion())
										 : "null";
						ResultSet result = Conexiones.realizarConsulta("select ifnull(max(correlativoitem),1) as correlativo from detallelistaregalos where codlista="+codLista,false);
						int correlativo = result.getInt("correlativo");
						sentenciaSQL = "insert into detallelistaregalos (codlista,codproducto,correlativoitem,cantidad,precioregular," +
										"preciofinal,montoimpuesto,codtipocaptura,codpromocion,cantcomprado,montoabonos) values ("
							+ codLista + ", " 
							+ "'" + codProd + "', "
							+ (correlativo+1) + ", "
							+ "0.0" + ", "
							+ detalle.getProducto().getPrecioRegular() + ", "
							+ detalle.getPrecioFinal() + ", "
							+ detalle.getMontoImpuesto() + ", "
							+ "'" + detalle.getTipoCaptura() + "', "
							+ promocion + ", "
							+ detalle.getCantidad() +", "
							+ "0.0" + ")";
						
						loteSentencias.addBatch(sentenciaSQL);
					}
					
					String codClientetxt = (codCliente != null)
										? "'"+codCliente+"'"
										: "null";
										
					String nomClientetxt = (nomCliente != null)
										? "'"+nomCliente+"'"
										: "null";
										
					String dedicatoriatxt = (dedicatoria != null)
										? "'"+dedicatoria+"'"
										: "null";
					
					//ACTUALIZACION PROMOCIONES: cuando hay combos pueden existir varios detalles iguales
					//Se agrega correlativoitem para diferenciarlos
					sentenciaSQL = "insert into CR.operacionlistaregalos(numoperacion, numtransaccion, codcliente, "
									+ "nomcliente, codlista, fecha, tipooperacion, codproducto, montobase, montoimpuesto, "
									+ "cantidad, numtienda, numcaja, codcajero, dedicatoria, correlativoitem) values ("
									+ numOperacion + ", "
									+ numTransaccion + ", "
									+ codClientetxt + ", "
									+ nomClientetxt + ", "
									+ codLista + ", "
									+ "'" + fecha + "', "
									+ "'"+OperacionLR.VENTA + "', "
									+ "'" + codProd + "', "
									+ detalle.getPrecioFinal() + ", "
									+ detalle.getMontoImpuesto() + ", "
									+ detalle.getCantidad() + ", "
									+ Sesion.getNumTienda() + ", "
									+ Sesion.getNumCaja() + ", "
									+ "'" + codCajero + "', "
									+ dedicatoriatxt + ", " +
									(i+1)+")";
					
					loteSentencias.addBatch(sentenciaSQL);
				}
				
				Conexiones.ejecutarLoteSentencias(loteSentencias, false, true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (BaseDeDatosExcepcion e) {
				e.printStackTrace();
			} catch (ConexionExcepcion e) {
				e.printStackTrace();
			}	finally{
				Conexiones.cerrarConexion(false,true);
			}
		}
	}
	
	/**
	 * Registra un abono a un producto de una lista o a una lista de regalos 
	 * 
	 * @param codTienda
	 * @param codCajero
	 * @param lista
	 * @param codCliente
	 * @param nomCliente
	 * @param dedicatoria
	 * @param detallesAbono
	 * @param vuelto
	 * @return
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static int registrarAbonoLR(int codTienda,String codCajero, ListaRegalos lista, String codCliente, 
									String nomCliente, String dedicatoria, Vector<Abono> detallesAbono,
									double vuelto) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if(codTienda != lista.getCodTiendaApertura())
			return BaseDeDatosServicio.registrarAbonoLRRemota(codCajero,lista,codCliente,
									nomCliente, dedicatoria, detallesAbono, vuelto);
		else {
			ResultSet resultado = null;
			String sentenciaSQL = null;
			int numOperacion = 0;
			String fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

			try {
				
				
				sentenciaSQL = "select ifnull(max(numoperacion),0) as numoperacion from operacionlistaregalos "
								+ "where codlista = '" + lista.getNumServicio() + "'";
						 				
				resultado = Conexiones.realizarConsulta(sentenciaSQL,false);
				if (resultado.first()) {
					try{
						numOperacion = resultado.getInt("numoperacion") + 1;
					}catch (SQLException e){}
				}
				// Registra el abono y verifica si se completó correctamente el registro.
				// Si el registro no está en la BD reintenta una vez el registro.
				registrarAbono(numOperacion,detallesAbono,lista,codCliente,nomCliente,dedicatoria,fecha,codCajero,vuelto);			
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BaseDeDatosExcepcion("Error SQL registrando el abono: "+e.getMessage());
			} catch (BaseDeDatosExcepcion e) {
				e.printStackTrace();
				throw new BaseDeDatosExcepcion("Error en base de datos registrando el abono: "+e.getMessage());
			} catch (ConexionExcepcion e) {
				e.printStackTrace();
				throw new ConexionExcepcion("Error en conexión registrando el abono: "+e.getMessage());
			}
			finally{
				String sentenciaSQL2 = "select count(*) as cantoper from operacionlistaregalos where codlista="+lista.getNumServicio()+" and numoperacion="+numOperacion;
				ResultSet resultado2 = Conexiones.realizarConsulta(sentenciaSQL2,false);
				int countOperaciones;
				try {
					countOperaciones = resultado2.getInt("cantoper");
					if(countOperaciones == 0)
						registrarAbono(numOperacion,detallesAbono,lista,codCliente,nomCliente,dedicatoria,fecha,codCajero,vuelto);
				} catch (SQLException e) {
					e.printStackTrace();
					throw new BaseDeDatosExcepcion("Error SQL registrando el abono: "+e.getMessage());
				}

				Conexiones.cerrarConexion(false,false);
			}
			return numOperacion;
		}
	}
	
	/**
	 * @param numOperacion
	 * @param abono
	 * @throws ConexionExcepcion 
	 * @throws BaseDeDatosExcepcion 
	 * @throws SQLException 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private static void registrarAbono(int numOperacion,Vector<Abono> detallesAbono,ListaRegalos lista,
				String codCliente,String nomCliente, String dedicatoria,String fecha,String codCajero,
				double vuelto) throws ConexionExcepcion, BaseDeDatosExcepcion, SQLException {
		Abono abono;
		String codProd,sentenciaSQL;
		ResultSet resultado;
		double montoAbono = 0;
		Statement loteSentencias = Conexiones.crearSentencia(false);
		
		for(int i=0;i<detallesAbono.size();i++){
			abono = (Abono)detallesAbono.get(i);
			double montoItem = abono.getMontoBase();
			double impuestoItem = abono.getImpuestoProducto();
			if(abono.getProducto()==null){
				codProd = "000000000000";
				sentenciaSQL = "select montoabonos from CR.listaregalos where codlista = '" + lista.getNumServicio() + "'";
				resultado = Conexiones.realizarConsulta(sentenciaSQL,false);												

				if (resultado.first()) {
					montoAbono = resultado.getDouble("montoabonos");
				}
				montoAbono += (abono.getMontoBase() + impuestoItem) * abono.getCantidad();

				sentenciaSQL = "update CR.listaregalos set listaregalos.montoabonos = '"
								+ montoAbono + "' where codlista = '" + lista.getNumServicio() + "'";
								
				loteSentencias.addBatch(sentenciaSQL);

			} else {
				codProd = abono.getProducto().getCodProducto();
				sentenciaSQL = "select montoabonos from CR.detallelistaregalos where codproducto = '"  
								+ codProd + "' and codlista = '" + lista.getNumServicio() + "'";
				resultado = Conexiones.realizarConsulta(sentenciaSQL,false);												

				if (resultado.first()) {
					montoAbono = resultado.getDouble("montoabonos");
				}
				montoAbono += (abono.getMontoBase() + impuestoItem) * abono.getCantidad();
									
				sentenciaSQL = "update CR.detallelistaregalos set montoabonos = " + montoAbono 
								+ " where codproducto = '" + codProd + "' and codlista = " + lista.getNumServicio();

				loteSentencias.addBatch(sentenciaSQL);
			}
			
			String codClientetxt = (codCliente != null)
								? "'"+codCliente+"'"
								: "null";
								
			String nomClientetxt = (nomCliente != null)
								? "'"+nomCliente+"'"
								: "null";
								
			String dedicatoriatxt = (dedicatoria != null)
								? "'"+dedicatoria+"'"
								: "null";

			sentenciaSQL = "insert into CR.operacionlistaregalos(numoperacion, codcliente, nomcliente, "
				+ "codlista, fecha, tipooperacion, codproducto, montobase, montoimpuesto, cantidad, "
				+ "numtienda, numcaja, codcajero, dedicatoria, correlativoitem) values ("
				+ numOperacion + ", "
				+ codClientetxt + ", "
				+ nomClientetxt + ", "
				+ lista.getNumServicio() + ", "
				+ "'" + fecha + "', "
				+ "'" + abono.getTipoTransAbono() + "', "
				+ "'" + codProd + "', "
				+ montoItem + ", "
				+ impuestoItem + ", "
				+ abono.getCantidad() + ", "
				+ Sesion.getNumTienda() + ", "
				+ Sesion.getNumCaja() + ", "
				+ "'" + codCajero + "'," 
				+ dedicatoriatxt + ", " +
				(i+1)+")";

			loteSentencias.addBatch(sentenciaSQL);				
		}

		//Se registran los pagos a través de la extensión del módulo de pagos.
		ManejoPagosFactory.getInstance().registrarPagos(loteSentencias,lista,numOperacion,vuelto);

		Conexiones.ejecutarLoteSentencias(loteSentencias, false, true);		
	}

	public static void modificarEncabezadoListaRegalos(ListaRegalos listaRegalos) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if(listaRegalos.getCodTiendaApertura()!=listaRegalos.getCodTienda())
			modificarEncabezadoListaRemota(listaRegalos);
		else {
			ResultSet resultado = null;
			String sentenciaSQL = null;
			Statement loteSentencias = null;
			int numTransaccion = 0;
			String fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
	
			try {
				loteSentencias = Conexiones.crearSentencia(false);
	
				String fechaEventoString = new SimpleDateFormat("yyyy-MM-dd").format(listaRegalos.getFechaEvento());
	
				sentenciaSQL = "select ifnull(max(numoperacion),0) as numoperacion from operacionlistaregalos "
								+ "where codlista = '" + listaRegalos.getNumServicio() + "'";
						 				
				resultado = Conexiones.realizarConsulta(sentenciaSQL,false);
				if (resultado.first()) {
					try{
						numTransaccion = resultado.getInt("numoperacion") + 1;
					}catch (SQLException e){}
				}
				String titularsec = (listaRegalos.getTitularSec() != null)
									? "'"+listaRegalos.getTitularSec()+"'"
									: "null";
									
				String notificaciones = (listaRegalos.isNotificaciones())
									? "'S'"
									: "'N'";
									
				String permitirventa = (listaRegalos.isPermitirVenta())
									? "'S'"
									: "'N'";
						
				sentenciaSQL = "update CR.listaregalos set "
							+ "fechaevento = '" + fechaEventoString + "', "
							+ "tipoevento = '" + listaRegalos.getTipoEvento() + "', "
							+ "titularsec = " + titularsec + ", "
							+ "notificaciones = " + notificaciones + ", "
							+ "permitirventa = " + permitirventa + " "
							+ "where codlista = " + listaRegalos.getNumServicio();
				loteSentencias.addBatch(sentenciaSQL);

				//ACTUALIZACION PROMOCIONES: correlativo item por caso de combos
				sentenciaSQL = "insert into CR.operacionlistaregalos(numoperacion, codcliente, nomcliente, "
					+ "codlista, fecha, tipooperacion, codproducto, montobase, cantidad, "
					+ "numtienda, numcaja, codcajero, dedicatoria, correlativoitem) values ("
					+ numTransaccion + ", "
					+ "'" + listaRegalos.getCliente().getCodCliente() + "', "
					+ "'" + listaRegalos.getCliente().getNombreCompleto() + "', "
					+ listaRegalos.getNumServicio() + ", "
					+ "'" + fecha + "', "
					+ "'" + "M" + "', "
					+ "'" + "', "
					+ "0" + ", "
					+ "1.0" + ", "
					+ Sesion.getNumTienda() + ", "
					+ Sesion.getNumCaja() + ", "
					+ "'" + listaRegalos.getCodCajero() + "'," 
					+ "'" + "" + "', 1)";
					
				loteSentencias.addBatch(sentenciaSQL);
						
				Conexiones.ejecutarLoteSentencias(loteSentencias, false, true);
				
				// Actualizamos la lista en el servidor central
				sentenciaSQL = "update CR.listaregaloscentral set "
								+ "titular = '" + listaRegalos.getTitular().getNombreCompleto() + "', "
								+ "titularsec = '" + listaRegalos.getTitularSec() + "', "
								+ "tipoevento = '" + listaRegalos.getTipoEvento() + "', "
								+ "fechaevento = '" + fechaEventoString + "' "
								+ "where codlista = '" + listaRegalos.getNumServicio() + "'";
	
				ConexionServCentral.realizarSentencia(sentenciaSQL);
			} catch (SQLException e) {
				logger.error("modificarDatosListaRegalos(ListaRegalos)", e);
				throw (new BaseDeDatosExcepcion("Error en sentencia SQL al registrar la lista de regalos"));
			} catch (ConexionExcepcion e) {
				logger.error("modificarDatosListaRegalos(ListaRegalos)", e);
				throw (new BaseDeDatosExcepcion("Error de conexión con BD al registrar la lista de regalos"));
			} finally {
				if (loteSentencias != null) {
					try {
						loteSentencias.close();
					} catch (SQLException e1) {
						logger.error("modificarDatosListaRegalos(ListaRegalos)", e1);
					}
					loteSentencias = null;
				}
			}
		}		
	}

	/**
	 * Guarda las modificaciones realizadas a una lista de regalos
	 * 
	 * @param listaRegalos
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	public static void modificarDetallesListaRegalos(ListaRegalos listaRegalos) throws BaseDeDatosExcepcion, ConexionExcepcion {
		
		if(listaRegalos.getCodTiendaApertura()!=listaRegalos.getCodTienda())
			modificarDetallesListaRemota(listaRegalos);
		else {
			ResultSet resultado = null;
			String sentenciaSQL = null;
			Statement loteSentencias = null;
			int numTransaccion = 0;
			String fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
	
			try {
				loteSentencias = Conexiones.crearSentencia(false);
	
				sentenciaSQL = "select ifnull(max(numoperacion),0) as numoperacion from operacionlistaregalos "
								+ "where codlista = '" + listaRegalos.getNumServicio() + "'";
						 				
				resultado = Conexiones.realizarConsulta(sentenciaSQL,false);
				try{
					numTransaccion = resultado.getInt("numoperacion") + 1;
				}catch (SQLException e){}

				sentenciaSQL = "update CR.listaregalos set "
							+ "montobase = " + listaRegalos.getMontoBase() + ", "
							+ "montoimpuesto = " + listaRegalos.getMontoImpuesto() + ", "
							+ "cantproductos = " + listaRegalos.getCantPedidos() + " "
							+ "where codlista = " + listaRegalos.getNumServicio();
				loteSentencias.addBatch(sentenciaSQL);

				sentenciaSQL = "delete from CR.detallelistaregalos where codlista = " + listaRegalos.getNumServicio();
				loteSentencias.addBatch(sentenciaSQL);
			
				// Registramos los detalles del servicio
				for (int i=0; i<listaRegalos.getDetallesServicio().size(); i++) {
					String promocion = (((DetalleServicio)listaRegalos.getDetallesServicio().elementAt(i)).getCodPromocion() > 0)
										? String.valueOf(((DetalleServicio)listaRegalos.getDetallesServicio().elementAt(i)).getCodPromocion())
										: "null";
					sentenciaSQL = "insert into CR.detallelistaregalos (codlista,codproducto,correlativoitem,cantidad,precioregular," +
									"preciofinal,montoimpuesto,codtipocaptura,codpromocion,cantcomprado,montoabonos) values ("
						+ listaRegalos.getNumServicio() + ", " 
						+ "'" + ((DetalleServicio)listaRegalos.getDetallesServicio().elementAt(i)).getProducto().getCodProducto() + "', "
						+ (i+1) + ", "
						+ ((DetalleServicio)listaRegalos.getDetallesServicio().elementAt(i)).getCantidad() + ", "
						+ ((DetalleServicio)listaRegalos.getDetallesServicio().elementAt(i)).getProducto().getPrecioRegular() + ", "
						+ ((DetalleServicio)listaRegalos.getDetallesServicio().elementAt(i)).getPrecioFinal() + ", "
						+ ((DetalleServicio)listaRegalos.getDetallesServicio().elementAt(i)).getMontoImpuesto() + ", "
						+ "'" + ((DetalleServicio)listaRegalos.getDetallesServicio().elementAt(i)).getTipoCaptura() + "', "
						+ promocion + ", "
						+  ((DetalleServicio)listaRegalos.getDetallesServicio().elementAt(i)).getCantVendidos()+", "
						+ ((DetalleServicio)listaRegalos.getDetallesServicio().elementAt(i)).getAbonos() + ");";
					loteSentencias.addBatch(sentenciaSQL);
				}
		
				sentenciaSQL = "insert into CR.operacionlistaregalos(numoperacion, codcliente, nomcliente, "
					+ "codlista, fecha, tipooperacion, codproducto, montobase, cantidad, "
					+ "numtienda, numcaja, codcajero, dedicatoria, correlativoitem) values ("
					+ numTransaccion + ", "
					+ "'" + listaRegalos.getCliente().getCodCliente() + "', "
					+ "'" + listaRegalos.getCliente().getNombreCompleto() + "', "
					+ listaRegalos.getNumServicio() + ", "
					+ "'" + fecha + "', "
					+ "'" + "M" + "', "
					+ "'" + "', "
					+ "0" + ", "
					+ "1.0" + ", "
					+ Sesion.getNumTienda() + ", "
					+ Sesion.getNumCaja() + ", "
					+ "'" + listaRegalos.getCodCajero() + "'," 
					+ "'" + "" + "',1)";
					
				loteSentencias.addBatch(sentenciaSQL);
						
				Conexiones.ejecutarLoteSentencias(loteSentencias, false, true);
			} catch (SQLException e) {
				logger.error("registrarListaRegalos(ListaRegalos)", e);
				throw (new BaseDeDatosExcepcion("Error en sentencia SQL al registrar la lista de regalos"));
			} catch (ConexionExcepcion e) {
				logger.error("registrarListaRegalos(ListaRegalos)", e);
				throw (new BaseDeDatosExcepcion("Error de conexión con BD al registrar la lista de regalos"));
			} finally {
				if (loteSentencias != null) {
					try {
						loteSentencias.close();
					} catch (SQLException e1) {
						logger.error("registrarListaRegalos(ListaRegalos)", e1);
					}
					loteSentencias = null;
				}
			}
		}
	}

	/**
	 * Guarda las modificaciones realizadas a una lista de regalos
	 * alojada en un servidor de tienda remoto
	 * 
	 * @param lista
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private static void modificarDetallesListaRemota(ListaRegalos lista) throws BaseDeDatosExcepcion, ConexionExcepcion {
		String consulta = "", dbUrlServidor = "";
		ResultSet result = null;
		int numTienda = 0;
		
		try {
			consulta = "select numtienda from cr.listaregaloscentral where codlista = "+lista.getNumServicio();
			result = ConexionServCentral.realizarConsulta(consulta);
			numTienda = result.getInt("numtienda");
			
			consulta = "select dburlservidor from servidortienda where numtienda = "+numTienda;
			result = Conexiones.realizarConsulta(consulta,true);
			dbUrlServidor = result.getString("dburlservidor");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		IXMLElement solicitud = new XMLElement("listaregalos");
		solicitud.setAttribute("tipo","modificardetalles");
		
		IXMLElement cabecera = new XMLElement("cabecera");
		solicitud.addChild(cabecera);
		
		IXMLElement elemento = new XMLElement("codlista");
		cabecera.addChild(elemento);
		elemento.setContent(String.valueOf(lista.getNumServicio()));

		elemento = new XMLElement("codtitular");
		cabecera.addChild(elemento);
		elemento.setContent(lista.getTitular().getCodCliente());
		
		elemento = new XMLElement("titular");
		cabecera.addChild(elemento);
		elemento.setContent(lista.getTitular().getNombreCompleto());
				
		elemento = new XMLElement("montobase");
		cabecera.addChild(elemento);
		elemento.setContent(String.valueOf(lista.getMontoBase()));
		
		elemento = new XMLElement("montoimpuesto");
		cabecera.addChild(elemento);
		elemento.setContent(String.valueOf(lista.getMontoImpuesto()));
		
		elemento = new XMLElement("cantproductos");
		cabecera.addChild(elemento);
		elemento.setContent(String.valueOf(lista.getCantPedidos()));
		
		elemento = new XMLElement("montoabonos");
		cabecera.addChild(elemento);
		elemento.setContent(String.valueOf(lista.getMontoAbonos()));
		
		elemento = new XMLElement("numtienda");
		cabecera.addChild(elemento);
		elemento.setContent(String.valueOf(lista.getCodTienda()));
		
		elemento = new XMLElement("numcaja");
		cabecera.addChild(elemento);
		elemento.setContent(String.valueOf(Sesion.getNumCaja()));
		
		elemento = new XMLElement("codcajero");
		cabecera.addChild(elemento);
		elemento.setContent(String.valueOf(lista.getCodCajero()));

		elemento = new XMLElement("fecha");
		cabecera.addChild(elemento);
		elemento.setContent(new SimpleDateFormat("yyyy-MM-dd").format(Sesion.getFechaSistema()));
		
		Vector<DetalleServicio> vectordetalles = lista.getDetallesServicio();
		
		IXMLElement detalles = solicitud.createElement("detalles");
		solicitud.addChild(detalles);
		
		for(int i=0;i<vectordetalles.size();i++) {
			DetalleServicio detalleservicio = (DetalleServicio)vectordetalles.get(i);
			IXMLElement producto = detalles.createElement("producto");
			detalles.addChild(producto);

			elemento = detalles.createElement("codproducto");
			producto.addChild(elemento);
			elemento.setContent(detalleservicio.getProducto().getCodProducto());

			elemento = detalles.createElement("correlativoitem");
			producto.addChild(elemento);
			elemento.setContent(String.valueOf(i));
			
			elemento = detalles.createElement("cantidad");
			producto.addChild(elemento);
			elemento.setContent(String.valueOf(detalleservicio.getCantidad()));
			
			elemento = detalles.createElement("precioregular");
			producto.addChild(elemento);
			elemento.setContent(String.valueOf(detalleservicio.getProducto().getPrecioRegular()));
			
			elemento = detalles.createElement("preciofinal");
			producto.addChild(elemento);
			elemento.setContent(String.valueOf(detalleservicio.getPrecioFinal()));
			
			elemento = detalles.createElement("montoimpuesto");
			producto.addChild(elemento);
			elemento.setContent(String.valueOf(detalleservicio.getMontoImpuesto()));
			
			elemento = detalles.createElement("codtipocaptura");
			producto.addChild(elemento);
			elemento.setContent(detalleservicio.getTipoCaptura());
			
			String codpromocion = (detalleservicio.getCodPromocion() > 0)
								? String.valueOf(detalleservicio.getCodPromocion())
								: "null";
			
			elemento = detalles.createElement("codpromocion");
			producto.addChild(elemento);
			elemento.setContent(codpromocion);
			
			elemento = detalles.createElement("cantcomprado");
			producto.addChild(elemento);
			elemento.setContent(String.valueOf(detalleservicio.getCantVendidos()));
			
			elemento = detalles.createElement("montoabonos");
			producto.addChild(elemento);
			elemento.setContent(String.valueOf(detalleservicio.getAbonos()));
		}
		ConexionTiendaRemota.ejecutarSentencia(solicitud,dbUrlServidor);
	}
	
	/**
	 * Guarda las modificaciones realizadas a una lista de regalos
	 * alojada en un servidor de tienda remoto
	 * 
	 * @param lista
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	private static void modificarEncabezadoListaRemota(ListaRegalos lista) throws BaseDeDatosExcepcion, ConexionExcepcion {
		String consulta = "", dbUrlServidor = "";
		ResultSet result = null;
		int numTienda = 0;
		
		try {
			consulta = "select numtienda from cr.listaregaloscentral where codlista = "+lista.getNumServicio();
			result = ConexionServCentral.realizarConsulta(consulta);
			numTienda = result.getInt("numtienda");
			
			consulta = "select dburlservidor from servidortienda where numtienda = "+numTienda;
			result = Conexiones.realizarConsulta(consulta,true);
			dbUrlServidor = result.getString("dburlservidor");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		String notificaciones = (lista.isNotificaciones())
							? "'S'"
							: "'N'";
									
		String permitirventa = (lista.isPermitirVenta())
							? "'S'"
							: "'N'";
		
		IXMLElement solicitud = new XMLElement("listaregalos");
		solicitud.setAttribute("tipo","modificarencabezado");
		
		IXMLElement cabecera = new XMLElement("cabecera");
		solicitud.addChild(cabecera);
			
		IXMLElement elemento = new XMLElement("codlista");
		cabecera.addChild(elemento);
		elemento.setContent(String.valueOf(lista.getNumServicio()));

		elemento = new XMLElement("fechaevento");
		cabecera.addChild(elemento);
		elemento.setContent(String.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(lista.getFechaEvento())));
		
		elemento = new XMLElement("tipoevento");
		cabecera.addChild(elemento);
		elemento.setContent(lista.getTipoEvento());

		elemento = new XMLElement("codtitular");
		cabecera.addChild(elemento);
		elemento.setContent(lista.getTitular().getCodCliente());
		
		elemento = new XMLElement("titular");
		cabecera.addChild(elemento);
		elemento.setContent(lista.getTitular().getNombreCompleto());
		
		elemento = new XMLElement("titularsec");
		cabecera.addChild(elemento);
		elemento.setContent(lista.getTitularSec());
	
		elemento = new XMLElement("numtienda");
		cabecera.addChild(elemento);
		elemento.setContent(String.valueOf(lista.getCodTienda()));
		
		elemento = new XMLElement("numcaja");
		cabecera.addChild(elemento);
		elemento.setContent(String.valueOf(Sesion.getNumCaja()));
		
		elemento = new XMLElement("codcajero");
		cabecera.addChild(elemento);
		elemento.setContent(String.valueOf(lista.getCodCajero()));
		
		elemento = new XMLElement("notificaciones");
		cabecera.addChild(elemento);
		elemento.setContent(notificaciones);
		
		elemento = new XMLElement("permitirventa");
		cabecera.addChild(elemento);
		elemento.setContent(permitirventa);
		
		elemento = new XMLElement("fecha");
		cabecera.addChild(elemento);
		elemento.setContent(new SimpleDateFormat("yyyy-MM-dd").format(Sesion.getFechaSistema()));

		ConexionTiendaRemota.ejecutarSentencia(solicitud,dbUrlServidor);
	}
	
	/**
	 * Coloca en espera una lista de regalos
	 * 
	 * @param numTienda
	 * @param numCaja
	 * @param codAfiliado
	 * @param numOperacion
	 * @param tipoTransaccion
	 * @throws ConexionExcepcion
	 * @throws BaseDeDatosExcepcion
	 */
	public static void colocarListaEnEspera(int numTienda, int numCaja,String codAfiliado,int numOperacion,char tipoTransaccion) throws ConexionExcepcion, BaseDeDatosExcepcion{
		String fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		final String almacenar = "insert into listaregalosespera(numtienda,fecha,caja,codafiliado,tipotransaccion,objeto,objeto2) values (?,?,?,?,?,?,?)";

		PreparedStatement pstmt = null;
		try {
			pstmt = Conexiones.getConexion(false).prepareStatement(almacenar);
			pstmt.setInt(1, numTienda);
			pstmt.setString(2, fecha);
			pstmt.setInt(3, numCaja);
			pstmt.setString(4, codAfiliado);
			pstmt.setString(5, String.valueOf(tipoTransaccion));
			Object objetoLista = (Object)CR.meServ.getListaRegalos();
			pstmt.setObject(6, objetoLista);
			if(tipoTransaccion == ListaRegalos.CONSULTA_TITULAR){
				Object objetoLista2 = (Object)CR.meServ.getListaRegalosRespaldo();
				pstmt.setObject(7, objetoLista2);
			} else {
				Object objetoVenta = (Object)CR.meVenta.getVenta();
				pstmt.setObject(7, objetoVenta);
			}
			pstmt.addBatch();
			Conexiones.ejecutarLoteSentencias(pstmt,false,true);
			pstmt.close();
		} catch (ConexionExcepcion e1){
			throw (new ConexionExcepcion("Error al colocar lista en espera ", e1));
		} catch (SQLException e2) {
			throw (new BaseDeDatosExcepcion("Error en SentenciaSQL al colocar lista en espera ", e2));
		} catch (BaseDeDatosExcepcion e2){
			throw (new BaseDeDatosExcepcion("Ya se ha colocado en espera una lista del cliente " + codAfiliado, e2));
		}
		finally {
			try {
				pstmt.close();	
			} catch (SQLException e) {

			}
		}
	}
	
	/**
	 * Recupera una lista de regalos en espera
	 * 
	 * @param numTienda
	 * @param numCaja
	 * @param codAfiliado
	 * @return
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	public static char recuperarListaEnEspera(int numTienda, int numCaja, String codAfiliado) throws BaseDeDatosExcepcion, ConexionExcepcion {
		ResultSet rs = null;
		String tipoTransaccion = null;
		final String recuperar = "select tipotransaccion,objeto,objeto2 from listaregalosespera where numtienda = ? and fecha = ? and caja = ? and codafiliado like ?";
		String fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		
		PreparedStatement pstmt = null;
		try {
			// Para recuperar la Devolución busco devolución y venta (si existen)
			pstmt = Conexiones.getConexion(false).prepareStatement(recuperar);
			pstmt.setInt(1, numTienda);
			pstmt.setString(2, fecha);
			pstmt.setInt(3, numCaja);
			pstmt.setString(4, "%"+codAfiliado);
			
			rs = pstmt.executeQuery();
			rs.beforeFirst();
			//MODIFICACION AL MIGRAR DE JAVA1.4 A 1.6 Y MYSQL4 A MYSQL5. FUE NECESARIO TRANSFORMAR
			//EL ARREGLO DE BYTES QUE DEVUELVE MYSQL AL RECUPERAR UN TIPO BLOB A TIPO OBJETO 'LISTAREGALOS'
			if (rs.next()){
				tipoTransaccion = rs.getString("tipotransaccion");
				if (tipoTransaccion.equals(String.valueOf(ListaRegalos.REGISTRO))){
					try {
						if(rs.getBytes("objeto")!=null){
							ByteArrayInputStream bs = new ByteArrayInputStream(rs.getBytes("objeto"));	
							ObjectInputStream is = new ObjectInputStream(bs);
							ListaRegalos objetoLista = (ListaRegalos)is.readObject();
							CR.meServ.setListaRegalos(objetoLista);
							is.close();
						}
					} catch (Exception e) {
						CR.meServ.setListaRegalos(null);
						throw (new BaseDeDatosExcepcion("Error al recuperar lista de regalos en espera ", e));
					}
					
				}else if(tipoTransaccion.equals(String.valueOf(ListaRegalos.CONSULTA_TITULAR))){
					try {
						ByteArrayInputStream bs;
						ObjectInputStream is;
						if(rs.getBytes("objeto")!=null){
							bs = new ByteArrayInputStream(rs.getBytes("objeto"));	
							is = new ObjectInputStream(bs);
							ListaRegalos objetoLista = (ListaRegalos)is.readObject();
							CR.meServ.setListaRegalos(objetoLista);
							is.close();
						}
						if(rs.getBytes("objeto2")!=null){
							bs = new ByteArrayInputStream(rs.getBytes("objeto2"));
							is = new ObjectInputStream(bs);
							ListaRegalos objetoListaR = (ListaRegalos)is.readObject();
							CR.meServ.setListaRegalosRespaldo(objetoListaR);
							is.close();
						}
					} catch (Exception e) {
						CR.meServ.setListaRegalos(null);
						throw (new BaseDeDatosExcepcion("Error al recuperar lista de regalos en espera ", e));
					}
				}else if(tipoTransaccion.equals(String.valueOf(ListaRegalos.CONSULTA_INVITADO))){
					
					try {
						ByteArrayInputStream bs;
						ObjectInputStream is;
						if(rs.getBytes("objeto")!=null){
							bs = new ByteArrayInputStream(rs.getBytes("objeto"));	
							is = new ObjectInputStream(bs);
							ListaRegalos objetoLista = (ListaRegalos)is.readObject();
							CR.meServ.setListaRegalos(objetoLista);
							is.close();
						}
						if(rs.getBytes("objeto2")!=null){
							bs = new ByteArrayInputStream(rs.getBytes("objeto2"));
							is = new ObjectInputStream(bs);
							Venta objetoVenta = (Venta)is.readObject();
							CR.meVenta.setVenta(objetoVenta);
							is.close();
						}
					} catch (Exception e) {
						CR.meServ.setListaRegalos(null);
						throw (new BaseDeDatosExcepcion("Error al recuperar lista de regalos en espera ", e));
					}
				}else if(tipoTransaccion.equals(String.valueOf(ListaRegalos.CIERRE_LISTA))){
					try {
						ByteArrayInputStream bs;
						ObjectInputStream is;
						if(rs.getBytes("objeto")!=null){
							bs = new ByteArrayInputStream(rs.getBytes("objeto"));	
							is = new ObjectInputStream(bs);
							ListaRegalos objetoLista = (ListaRegalos)is.readObject();
							CR.meServ.setListaRegalos(objetoLista);
							is.close();
						}
						if(rs.getBytes("objeto2")!=null){
							bs = new ByteArrayInputStream(rs.getBytes("objeto2"));
							is = new ObjectInputStream(bs);
							Venta objetoVenta = (Venta)is.readObject();
							CR.meVenta.setVenta(objetoVenta);
							is.close();
						}
					} catch (Exception e) {
						CR.meServ.setListaRegalos(null);
						throw (new BaseDeDatosExcepcion("Error al recuperar lista de regalos en espera ", e));
					}
				}
			}
			String sentenciaSql = "delete from listaregalosespera " +
							"where numtienda = " + numTienda + " and fecha = '" + fecha + "' and caja = " +
							numCaja + " and codafiliado like '%" + codAfiliado + "'";
			if (Conexiones.realizarSentencia(sentenciaSql, false) <= 0){
				throw (new BaseDeDatosExcepcion("No existe lista de regalos en espera correspondiente a ese cliente"));
			}

		}catch (ConexionExcepcion e){
			CR.meServ.setListaRegalos(null);
			throw (new ConexionExcepcion("Error conectando para recuperar lista de regalos en espera ", e));
		}catch (SQLException e){
			CR.meServ.setListaRegalos(null);
			throw (new BaseDeDatosExcepcion("Error al recuperar lista de regalos en espera ", e));
		}/* catch (BaseDeDatosExcepcion e){	
			CR.meServ.setListaRegalos(null);
			throw (new BaseDeDatosExcepcion("No existe transaccion en espera correspondiente a ese identificador", e));
		}*/finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				logger.error("recuperarListaEnEspera (Devolucion, Venta)", e);
			}
		}
		return (tipoTransaccion.toCharArray()[0]);
	}
	
	/**
	 * Carga una lista de regalos alojada en un servidor de tienda remoto
	 * 
	 * @param codLista
	 * @param numTienda
	 * @return
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	public static IXMLElement obtenerListaRegalosRemota(String codLista,int numTienda) throws BaseDeDatosExcepcion, ConexionExcepcion {
		ResultSet result = null;
		String consulta = "", dbUrlServidor = "";
		IXMLElement respuesta = null;
		
		try {
			consulta = "select dburlservidor from servidortienda where numtienda = "+numTienda;
			result = Conexiones.realizarConsulta(consulta,true);
			dbUrlServidor = result.getString("dburlservidor");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		IXMLElement solicitud = new XMLElement("listaregalos");
		solicitud.setAttribute("tipo","solicitud");
		IXMLElement codigo = solicitud.createElement("codigo");
		solicitud.addChild(codigo);
		codigo.setContent(codLista);
		IXMLElement tienda = solicitud.createElement("tienda");
		solicitud.addChild(tienda);
		tienda.setContent(String.valueOf(numTienda));
		
		respuesta = ConexionTiendaRemota.realizarConsulta(solicitud,dbUrlServidor);

		return respuesta;
	}
	
	/**
	 * Devuelve el numero de tienda que tiene alojada la lista de regalos
	 * 
	 * @param codLista
	 * @return
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	public static int obtenerTiendaOrigen(String codLista) throws ConexionExcepcion, ExcepcionCr{
		String consulta = "";
		ResultSet result = null;
		int numTienda = 0;
		
		try {
			consulta = "select numtienda from cr.listaregaloscentral where codlista = "+codLista;
			result = ConexionServCentral.realizarConsulta(consulta);
			if(result.first())
				numTienda = result.getInt("numtienda");
			else
				throw new ExcepcionCr("No existe Lista de Regalos \ncon el código ingresado");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return numTienda;
	}
	
	/**
	 * Registra una venta a una lista de regalos alojada en un servidor
	 * de tienda remoto
	 * 
	 * @param numtienda
	 * @param numtiendaorigen
	 * @param numcaja
	 * @param codcajero
	 * @param codlista
	 * @param codcliente
	 * @param nomcliente
	 * @param dedicatoria
	 * @param detallesVenta
	 * @throws ConexionExcepcion
	 */
	private static void registrarVentaLRRemota(int numTransaccion,int numtienda,int numtiendaorigen,int numcaja, String codcajero,int codlista,
							String codcliente,String nomcliente,String dedicatoria, Vector<?> detallesVenta) throws ConexionExcepcion {
		IXMLElement element = null;
		String consulta = "",dbUrlServidor = "";
		ResultSet result;
		
		try{
			consulta = "select dburlservidor from servidortienda where numtienda = "+numtiendaorigen;
			result = Conexiones.realizarConsulta(consulta,true);
			dbUrlServidor = result.getString("dburlservidor");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (BaseDeDatosExcepcion e) {
			e.printStackTrace();
		} catch (ConexionExcepcion e) {
			e.printStackTrace();
		}
		
		IXMLElement solicitud = new XMLElement("listaregalos");
		solicitud.setAttribute("tipo","venta");
		
		element = solicitud.createElement("numtransaccion");
		solicitud.addChild(element);
		element.setContent(String.valueOf(numTransaccion));

		element = solicitud.createElement("codcliente");
		solicitud.addChild(element);
		element.setContent(codcliente);

		element = solicitud.createElement("nomcliente");
		solicitud.addChild(element);
		element.setContent(nomcliente);
		
		element = solicitud.createElement("codlista");
		solicitud.addChild(element);
		element.setContent(String.valueOf(codlista));
						
		element = solicitud.createElement("numtienda");
		solicitud.addChild(element);
		element.setContent(String.valueOf(numtienda));
		
		element = solicitud.createElement("numcaja");
		solicitud.addChild(element);
		element.setContent(String.valueOf(numcaja));
		
		element = solicitud.createElement("codcajero");
		solicitud.addChild(element);
		element.setContent(codcajero);
		
		element = solicitud.createElement("dedicatoria");
		solicitud.addChild(element);
		element.setContent(dedicatoria);
		
		IXMLElement detalles = solicitud.createElement("detallesventa");
		solicitud.addChild(detalles);
		
		for(int i=0;i<detallesVenta.size();i++){
			IXMLElement detalle = detalles.createElement("detalle");
			detalles.addChild(detalle);
		
			Detalle detalleVenta = (Detalle)detallesVenta.get(i);
			
			element = detalle.createElement("codproducto");
			detalle.addChild(element);
			element.setContent(detalleVenta.getProducto().getCodProducto());
			
			element = detalle.createElement("montobase");
			detalle.addChild(element);
			element.setContent(String.valueOf(detalleVenta.getPrecioFinal()));
			
			element = detalle.createElement("montoimpuesto");
			detalle.addChild(element);
			element.setContent(String.valueOf(detalleVenta.getMontoImpuesto()));
					
			element = detalle.createElement("cantidad");
			detalle.addChild(element);
			element.setContent(String.valueOf(detalleVenta.getCantidad()));
		}
		ConexionTiendaRemota.ejecutarSentencia(solicitud,dbUrlServidor);
	}
	
	/**
	 * Registra un abono a un producto de una lista o a una lista
	 * de regalos alojada en un servidor de tienda remoto
	 * 
	 * @param codcajero
	 * @param lista
	 * @param codcliente
	 * @param nomcliente
	 * @param dedicatoria
	 * @param detallesabono
	 * @param vuelto
	 * @return
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	private static int registrarAbonoLRRemota(String codcajero, ListaRegalos lista, String codcliente, 
									String nomcliente, String dedicatoria, Vector<Abono> detallesabono,
									double vuelto) throws BaseDeDatosExcepcion, ConexionExcepcion {
		ResultSet resultado = null;
		String consulta = "", dbUrlServidor = "";
		//String sentenciaSQL = "";
		IXMLElement element = null;
		int numtransaccion = 0;
		String fecha = Control.FECHA_FORMAT.format(new Date(System.currentTimeMillis()));
		
		try {
			consulta = "select dburlservidor from servidortienda where numtienda = "+lista.getCodTiendaApertura();
			resultado = Conexiones.realizarConsulta(consulta,true);
			if(resultado.first())
				dbUrlServidor = resultado.getString("dburlservidor");
			else
				throw new BaseDeDatosExcepcion("Error localizando servidor remoto");
				
			IXMLElement solicitud = new XMLElement("listaregalos");
			solicitud.setAttribute("tipo","abono");
	
			element = solicitud.createElement("codcliente");
			solicitud.addChild(element);
			element.setContent(codcliente);
	
			element = solicitud.createElement("nomcliente");
			solicitud.addChild(element);
			element.setContent(nomcliente);
			
			element = solicitud.createElement("codlista");
			solicitud.addChild(element);
			element.setContent(String.valueOf(lista.getNumServicio()));
							
			element = solicitud.createElement("numtienda");
			solicitud.addChild(element);
			element.setContent(String.valueOf(Sesion.getTienda().getNumero()));
			
			element = solicitud.createElement("numcaja");
			solicitud.addChild(element);
			element.setContent(String.valueOf(Sesion.getCaja().getNumero()));
			
			element = solicitud.createElement("codcajero");
			solicitud.addChild(element);
			element.setContent(codcajero);
	
			element = solicitud.createElement("fecha");
			solicitud.addChild(element);
			element.setContent(fecha);
			
			element = solicitud.createElement("horainicio");
			solicitud.addChild(element);
			element.setContent(String.valueOf(lista.getHoraInicia()));
			
			element = solicitud.createElement("horafin");
			solicitud.addChild(element);
			element.setContent(String.valueOf(lista.getHoraFin()));
			
			element = solicitud.createElement("serialcaja");
			solicitud.addChild(element);
			element.setContent(Sesion.getCaja().getSerial());
			
			element = solicitud.createElement("dedicatoria");
			solicitud.addChild(element);
			element.setContent(dedicatoria);
			
			element = solicitud.createElement("detallesAbonos");
			solicitud.addChild(element);
			
			for(int i=0;i<detallesabono.size();i++) {
				Abono abono = (Abono)detallesabono.get(i);
				IXMLElement detalle = element.createElement("detalle");
				element.addChild(detalle);
				
				IXMLElement codprod = detalle.createElement("codprod");
				detalle.addChild(codprod);
				if(abono.getProducto()==null)
					codprod.setContent("000000000000");
				else
					codprod.setContent(abono.getProducto().getCodProducto());
				
				IXMLElement montoabono = detalle.createElement("montoabono");
				detalle.addChild(montoabono);
				montoabono.setContent(String.valueOf(abono.getMontoBase()));
				
				IXMLElement montoimpuesto = detalle.createElement("montoimpuesto");
				detalle.addChild(montoimpuesto);
				if(abono.getTipoTransAbono()=='T')
					montoimpuesto.setContent(String.valueOf(abono.getImpuestoProducto()));
				else
					montoimpuesto.setContent(String.valueOf(0));
				
				IXMLElement tipoabono = detalle.createElement("tipoabono");
				detalle.addChild(tipoabono);
				tipoabono.setContent(String.valueOf(abono.getTipoTransAbono()));
				
				IXMLElement cantidad = detalle.createElement("cantidad");
				detalle.addChild(cantidad);
				cantidad.setContent(String.valueOf(abono.getCantidad()));
			}
				
			IXMLElement pagos = solicitud.createElement("pagos");
			solicitud.addChild(pagos);
						
			Vector<Pago> vectorPagos = lista.getPagosAbono();
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

			numtransaccion = ConexionTiendaRemota.ejecutarSentenciaNumTransaccion(solicitud,dbUrlServidor);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Conexiones.cerrarConexion(true,true);
		}
		return numtransaccion;
	}

	/**
	 * @param numLote
	 * @return
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<String[]> obtenerLoteProductos(String numLote) throws ConexionExcepcion, ExcepcionCr {
		Vector<String[]> lote = new Vector<String[]>();
		Connection conexion = null;
		Statement sentencia = null;
		String consulta = null;
		ResultSet resultado = null;

		try {
			conexion = DriverManager.getConnection(ListaRegalos.dbPdtUrlServidor,ListaRegalos.dbPdtUsuario,ListaRegalos.dbPdtClave);
			sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			consulta = "select etiqueta, cantidad from result where lote = " + numLote;

			resultado = new CRResultSet(sentencia.executeQuery(consulta));
			
			while(resultado.next()){
				String codProducto = resultado.getString("etiqueta");
				String cantidad = resultado.getString("cantidad");
				lote.add(new String[]{codProducto,cantidad});
			}
			
			// consulta = "delete from result where lote = " + numLote;
			// sentencia.executeUpdate(consulta);
			
			sentencia = null;
			conexion.close();
		} catch (SQLException e) {
			conexion = null;
			e.printStackTrace();
			throw new ExcepcionCr("Error conectando para recuperar lote");
		}

		return lote;
	}
	
	public static String getEstadoListaRegalos(int codlista){
		ResultSet result;
		String estado = null;
		String consulta = "select estado from CR.listaregaloscentral where codlista="+codlista;
		try {
			result = ConexionServCentral.realizarConsulta(consulta);
			estado = result.getString("estado");
		} catch (BaseDeDatosExcepcion e) {
		} catch (ConexionExcepcion e) {
		} catch (SQLException e) {
		}
		return estado;
	}
	
	public static void setEstadoListaRegalos(int codlista, char estado) throws BaseDeDatosExcepcion, ConexionExcepcion{
		String fecha = new SimpleDateFormat("yyyyMMddhhmmss").format(Sesion.getFechaSistema());
		String sentenciaSQL = "update CR.listaregaloscentral set estado='"+estado+"',"
							+ "fechaultestado='"+fecha+"',"
							+ "tiendaultestado="+Sesion.getTienda().getNumero()+" "
							+ "where codlista="+codlista;
		ConexionServCentral.realizarSentencia(sentenciaSQL);
	}
	
	/**
	 * Verifica si exise una transaccion con ese identificador de tipo tipoTrans
	 * 
	 * @param tda
	 *            Tienda donde se realizo la venta
	 * @param caja
	 *            numero de caja que realizo la venta
	 * @param numAbono
	 *            Numero de factura
	 * @param fecha
	 *            fecha de la venta
	 * @param numservicio
	 * @param chequeosEnLinea
	 * @return boolean - Indica si se han realizado o no anulaciones.
	 * @throws ConexionExcepcion
	 */
	public static boolean anuladoAbono(int tda, int caja, int numAbono,
			String fecha, int numservicio, boolean chequeosEnLinea)
			throws ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("anuladoAbono(int, int, int, String, int, boolean) - start");
		}

		boolean resultado = false;
		int contadorResult = 0;
		ResultSet result = null;

		// Armamos los vectores de la factura a recuperar
		String sentenciaSQL = "select count(*) from anulaciondeabonos where anulaciondeabonos.numtienda = "
				+ tda
				+ " and anulaciondeabonos.numcaja = "
				+ caja
				+ " and anulaciondeabonos.numabonoanulado = "
				+ numAbono
				+ " and anulaciondeabonos.fechaabonoanulado = '"
				+ fecha
				+ "'and anulaciondeabonos.numservicio = " + numservicio + "";

		try {
			result = Conexiones.realizarConsulta(sentenciaSQL, true);
			try {
				result.first();
				contadorResult += result.getInt(1);
			} catch (SQLException e2) {
				logger
						.error(
								"anuladoAbono(int, int, int, String, int, boolean)",
								e2);

				try {
					result.close();
				} catch (SQLException e) {
					logger
							.error(
									"anuladoAbono(int, int, int, String, int, boolean)",
									e);
				}
				result = null;
			}
		} catch (BaseDeDatosExcepcion e1) {
			logger.error(
					"anuladoAbono(int, int, int, String, int, boolean)",
					e1);

			result = null;
		} catch (ConexionExcepcion e1) {
			logger.error(
					"anuladoAbono(int, int, int, String, int, boolean)",
					e1);

			result = null;
		}

		if ((result == null) || (contadorResult == 0)) {
			if (Sesion.isCajaEnLinea()) {
				try {
					result = Conexiones.realizarConsulta(sentenciaSQL, false);
					try {
						result.first();
						contadorResult += result.getInt(1);
					} catch (SQLException e2) {
						logger
								.error(
										"anuladoAbono(int, int, int, String, int, boolean)",
										e2);

						try {
							result.close();
						} catch (SQLException e) {
							logger
									.error(
											"anuladoAbono(int, int, int, String, int, boolean)",
											e);
						}
						result = null;
					}
				} catch (BaseDeDatosExcepcion e1) {
					logger
							.error(
									"anuladoAbono(int, int, int, String, int, boolean)",
									e1);

					result = null;
				} catch (ConexionExcepcion e1) {
					logger
							.error(
									"anuladoAbono(int, int, int, String, int, boolean)",
									e1);

					result = null;
				}
			} else {
				if (chequeosEnLinea) {
					throw (new ConexionExcepcion("Error al recuperar factura "
							+ numAbono + "\nCaja Fuera de Línea"));
				}
			}
		}

		if ((result == null) || (contadorResult == 0)) {
			if (Sesion.isCajaEnLinea()) {
				try {
					result = ConexionServCentral.realizarConsulta(sentenciaSQL);
					try {
						result.first();
						contadorResult += result.getInt(1);
					} catch (SQLException e2) {
						logger
								.error(
										"anuladoAbono(int, int, int, String, int, boolean)",
										e2);

						try {
							result.close();
						} catch (SQLException e) {
							logger
									.error(
											"anuladoAbono(int, int, int, String, int, boolean)",
											e);
						}
						result = null;
					}
				} catch (BaseDeDatosExcepcion e1) {
					logger
							.error(
									"anuladoAbono(int, int, int, String, int, boolean)",
									e1);

					result = null;
				} catch (ConexionExcepcion e1) {
					logger
							.error(
									"anuladoAbono(int, int, int, String, int, boolean)",
									e1);

					result = null;
				}
			} else {
				if (chequeosEnLinea) {
					throw (new ConexionExcepcion("Error al recuperar factura "
							+ numAbono + "\nCaja Fuera de Línea"));
				}
			}
		}
		try {
			if (result != null)
				result.close();
		} catch (SQLException e) {
			logger.error(
					"anuladoAbono(int, int, int, String, int, boolean)",
					e);
		}
		result = null;
		resultado = (contadorResult > 0) ? true : false;

		if (logger.isDebugEnabled()) {
			logger
					.debug("anuladoAbono(int, int, int, String, int, boolean) - end");
		}
		return resultado;
	}
	
	/**************************************************************************************
	 * Métodos agregados por apartados especiales 
	 **************************************************************************************/
	
	/**
	 * Consulta en la base de datos la existencia de algun tipo de apartado especial
	 * @throws ConexionExcepcion 
	 * @throws BaseDeDatosExcepcion 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Vector<Object>> obtenerTiposApartadosVigentes() throws BaseDeDatosExcepcion, ConexionExcepcion{
		Vector<Vector<Object>> tipos = new Vector<Vector<Object>>();
		
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerApartadosVigentes() - start");
		}

		ResultSet result = null;
			
		// Armamos los vectores de la factura a recuperar
		String sentenciaSQL = "	select * " +
				" from  tipoapartado " +
				" where fechadesde<=CURRENT_DATE() and fechahasta>=CURRENT_DATE()";
			
		result = Conexiones.realizarConsulta(sentenciaSQL,false);
	
		try {
			result.beforeFirst();
			while (result.next()) {
				Vector<Object> datosTipoApartado = new Vector<Object>();
				datosTipoApartado.addElement(new Integer(result.getInt("codigo")));
				datosTipoApartado.addElement(result.getString("descripcion"));
				datosTipoApartado.addElement(result.getDate("fechadesde"));
				datosTipoApartado.addElement(result.getDate("fechahasta"));
				datosTipoApartado.addElement(result.getDate("fechavence"));
				tipos.addElement(datosTipoApartado);
			}
		} catch (SQLException e2) {
			logger.error("obtenerApartadosVigentes()", e2);

			throw (new BaseDeDatosExcepcion("Error al recuperar tipos de apartados vigentes", e2));
		} finally {
			try {
				result.close();
			} catch (SQLException e) {
				logger.error("obtenerApartadosVigentes()", e);
			}
			result= null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerApartadosVigentes() - end");
		}
		
		return tipos;
	}
	
	/**************************************************************************************
	 * Fin de métodos agregados por apartados especiales 
	 **************************************************************************************/
	
	/**
	 * obtenerOpcionesCards
	 *     Obtiene las opciones de Cards disponibles para el metodo especificado
	 * @param idMetodo
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<OpcionBR> obtenerOpcionesCards(int idMetodo) throws BaseDeDatosExcepcion {
		Vector<OpcionBR> resultado = new Vector<OpcionBR>();
		
		try {
			String Sql = "SELECT * FROM br_opcionhabilitada WHERE codmetodo = " + idMetodo + 
					" and habilitado = '" + Sesion.SI + "' order by orden";
			ResultSet rs = MediadorBD.realizarConsulta(Sql);
			rs.beforeFirst();
			while (rs.next()) {
				OpcionBR opcion = new OpcionBR(idMetodo, rs.getInt("orden"), 
						rs.getString("nombreopcion"), rs.getString("rutaimagen"), 
						rs.getString("tecla"), rs.getString("habilitado"));
				resultado.add(opcion);
			}
		} catch (Exception e) {
			throw new BaseDeDatosExcepcion("Error Obteniendo opciones de CARDS desde la BD", e);
		}
		
		return resultado;
	}
	
	
	/**
	 * Actualiza la transaccion a regactualizado=N para que suba a servidor 
	 * @param ventaBR
	 * @throws BaseDeDatosExcepcion
	 */
	public static void actualizarVentaBRNoSincronizada(VentaBR ventaBR, boolean local) throws BaseDeDatosExcepcion{
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarVentaBRNoSincronizada(VentaBR) - start");
		}
		SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy-MM-dd");
		String fechaTransString = fechaTrans.format(ventaBR.getFechaTrans());
		try {
			Statement loteSentenciasCR  = null;
			loteSentenciasCR = Conexiones.crearSentencia(local);
			
			String sentenciaSQL = "update "+Sesion.getDbEsquema()+".br_transaccion set regactualizado='"+Sesion.NO+"' where " +
					"numtienda="+ventaBR.getCodTienda()+" and " +
					"fecha='"+fechaTransString+"' and " +
					"numcaja="+ventaBR.getNumCajaFinaliza()+" and " +
					"numtransaccion="+ventaBR.getNumTransaccion();
				
			//Ahora ejecutamos todas las sentencias de forma transaccional en la caja
			loteSentenciasCR.addBatch(sentenciaSQL);
			
			sentenciaSQL = "update "+Sesion.getDbEsquema()+".br_detalletransaccion set regactualizado='"+Sesion.NO+"' where " +
			"numtienda="+ventaBR.getCodTienda()+" and " +
			"fecha='"+fechaTransString+"' and " +
			"numcaja="+ventaBR.getNumCajaFinaliza()+" and " +
			"numtransaccion="+ventaBR.getNumTransaccion();
			loteSentenciasCR.addBatch(sentenciaSQL);
			
			sentenciaSQL = "update "+Sesion.getDbEsquema()+".br_pagodetransaccion set regactualizado='"+Sesion.NO+"' where " +
			"numtienda="+ventaBR.getCodTienda()+" and " +
			"fecha='"+fechaTransString+"' and " +
			"numcaja="+ventaBR.getNumCajaFinaliza()+" and " +
			"numtransaccion="+ventaBR.getNumTransaccion();
			loteSentenciasCR.addBatch(sentenciaSQL);
			
			sentenciaSQL = "update "+Sesion.getDbEsquema()+".br_comprobantefiscal set regactualizado='"+Sesion.NO+"' where " +
			"numtienda="+ventaBR.getCodTienda()+" and " +
			"fecha='"+fechaTransString+"' and " +
			"numcaja="+ventaBR.getNumCajaFinaliza()+" and " +
			"numtransaccion="+ventaBR.getNumTransaccion();
			loteSentenciasCR.addBatch(sentenciaSQL);
			
			
			Conexiones.ejecutarLoteSentencias(loteSentenciasCR, local, true);
			
		} catch (ConexionExcepcion e) {
			logger.debug("actualizarVentaBRNoSincronizada(VentaBR)", e);
			throw new BaseDeDatosExcepcion(e.getMensaje());
		} catch (SQLException e) {
			logger.debug("actualizarVentaBRNoSincronizada(VentaBR)", e);
			throw new BaseDeDatosExcepcion("Problema ejecutando sentencia en base de datos");
		} 
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarVentaBRNoSincronizada(VentaBR) - end");
		}
	}
	
	/**
	 * Registra en la base de datos la transacción de venta de bonos regalos
	 * @param ventaBR
	 * @throws BaseDeDatosExcepcion 
	 */
	public static void registrarTransaccionBR(VentaBR ventaBR) throws BaseDeDatosExcepcion{
		if (logger.isDebugEnabled()) {
			logger.debug("registrarTransaccionBR(VentaBR) - start");
		}
		
		Statement loteSentenciasCR  = null;
		try {
			loteSentenciasCR = Conexiones.crearSentencia(true);
			
			char cajaEnLinea = Sesion.isCajaEnLinea() == true ? Sesion.SI : Sesion.NO;		
			SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy-MM-dd");
			String fechaTransString = fechaTrans.format(ventaBR.getFechaTrans());
			Cliente cliente = ventaBR.getCliente();
			String clienteString = "";
			if(cliente==null)
				clienteString="null";
			else
				clienteString = "'"+cliente.getCodCliente()+"'";
			
			String sentenciaSQL = "insert into "+Sesion.getDbEsquema()+".br_transaccion " +
					"(numtienda," +
					"fecha," +
					"numcaja," +
					"numtransaccion," +
					"tipotransaccion," +
					"horainicia," +
					"codcliente," +
					"codcajero," +
					"cajaenlinea," +
					"duracionventa," +
					"estadotransaccion," +
					"regactualizado," +
					"codautorizante," +
					"montobase," +
					"montoimpuesto, " +
					"vueltocliente," +
					"montoremanente," +
					"lineasfacturacion," +
					"codvendedor) "
				+ "values (" + Sesion.getTienda().getNumero() + ", " 
				+ "'" + fechaTransString + "', "
				+ Sesion.getNumCaja() + ", "
				+ ventaBR.getNumTransaccion() + ", "
				+ "'" + ventaBR.getTipoTransaccion() + "', "
				+ "'" + ventaBR.getHoraInicia() + "', "
				+ clienteString+" , "
				+ "'" + ventaBR.getCodCajero() + "', "
				+ "'" + cajaEnLinea + "', "
				+ ventaBR.getDuracion() + ", "
				+ "'"+ventaBR.getEstadoTransaccion()+"', "
				+ "'"+Sesion.NO+"', " 
				+ "null," 
				+ ventaBR.getMontoBase()+"," 
				+ ventaBR.getMontoImpuesto()+", "
				+ ventaBR.getMontoVuelto()+", " 
				+ ventaBR.getMontoRemanente()+", "
				+ ventaBR.getDetallesTransaccionBR().size()+", " +
				(ventaBR.getVendedor()!=null ? "'" + ventaBR.getVendedor().getNumFicha() + "'" : "null") + ")";
				
			loteSentenciasCR.addBatch(sentenciaSQL);
			
			for(int i=0;i<ventaBR.getDetallesTransaccionBR().size();i++){
				DetalleTransaccionBR detalleTransaccionBR = (DetalleTransaccionBR)ventaBR.getDetallesTransaccionBR().elementAt(i);
				sentenciaSQL="insert into "+Sesion.getDbEsquema()+".br_detalletransaccion " +
					"(numtienda," +
					"fecha," +
					"numcaja," +
					"numtransaccion," +
					"codtarjeta," +
					"correlativoitem," +
					"montobase," +
					"montoimpuesto," +
					"regactualizado )"
				+ "values (" + Sesion.getTienda().getNumero() + ", " 
				+ "'" + fechaTransString + "', "
				+ Sesion.getNumCaja() + ", "
				+ ventaBR.getNumTransaccion() + ", "
				+ (detalleTransaccionBR.getCodTarjeta()!=null?"'" + detalleTransaccionBR.getCodTarjeta() + "'":"null")+", "
				+ (i+1)+", "
				+ detalleTransaccionBR.getMonto()+", "
				+ 0 + ", "
				+ "'N')";
				loteSentenciasCR.addBatch(sentenciaSQL);			
			}
			double montoRecaudado = ManejoPagosFactory.getInstance().registrarPagos(loteSentenciasCR, ventaBR);
			Conexiones.ejecutarLoteSentencias(loteSentenciasCR, true, true);
			Sesion.getCaja().incrementarMontoRecaudado(montoRecaudado);
		} catch (ConexionExcepcion e) {
			logger.debug("registrarTransaccionBR(VentaBR)");
			throw (new BaseDeDatosExcepcion("Error de conexión con BD al registrar la transaccion de Bono Regalo"));
		} catch (SQLException e) {
			logger.debug("registrarTransaccionBR(VentaBR)");
			throw (new BaseDeDatosExcepcion("Error de al registrar la transaccionde Bono Regalo"));
		}
		if (logger.isDebugEnabled()) {
			logger.debug("registrarTransaccionBR(VentaBR) - end");
		}
	}
	
	/**
	 * Actualiza el estado de los detalles de la transaccion de venta o recarga de Bonos Regalo
	 * @param ventaBR
	 * @throws BaseDeDatosExcepcion
	 */
	public static void actualizarCodigosTarjetaBR(VentaBR venta, DetalleTransaccionBR detalle, int i, boolean local) throws BaseDeDatosExcepcion{
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarCodigosTarjetaBR(Vector) - start");
		}
		
		
		SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy-MM-dd");
		String fechaTransString = fechaTrans.format(venta.getFechaTrans());
		
		try {
			//if(detalle.getCodTarjeta()!=null){
				String sentenciaSQL = " update "+Sesion.getDbEsquema()+".br_detalletransaccion " +
						" set codtarjeta="+(detalle.getCodTarjeta()!=null?"'"+detalle.getCodTarjeta()+"'":"null")+", numseq="+detalle.getNumSeq() +
						" where numtienda="+Sesion.getNumTienda()+" and fecha='"+fechaTransString+"' and numcaja="+venta.getNumCajaFinaliza()+"" +
								" and numtransaccion="+venta.getNumTransaccion()+" and correlativoitem="+(i+1);
				//venta.setTarjetasRecargadas(venta.getTarjetasRecargadas()+1);
				Conexiones.realizarSentencia(sentenciaSQL, local);
			//}
		} catch (BaseDeDatosExcepcion e) {
			logger.debug("actualizarCodigosTarjetaBR(Vector)");
			e.printStackTrace();
			throw (new BaseDeDatosExcepcion("Error de al registrar la transaccion de Bono Regalo"));
		} catch (ConexionExcepcion e) {
			logger.debug("actualizarCodigosTarjetaBR(Vector)");
			throw (new BaseDeDatosExcepcion("Error de conexión con BD al registrar la transaccion de Bono Regalo"));
		} 
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarCodigosTarjetaBR(Vector) - end");
		}
	}
	
	/**
	 * Actualiza el estado de los detalles de la transaccion de venta o recarga de Bonos Regalo
	 * @param ventaBR
	 * @throws BaseDeDatosExcepcion
	 */
	public static void anularCodigosTarjetaBR(VentaBR venta, DetalleTransaccionBR detalle, int i, boolean local) throws BaseDeDatosExcepcion{
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarCodigosTarjetaBR(Vector) - start");
		}
		
		
		SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy-MM-dd");
		String fechaTransString = fechaTrans.format(venta.getFechaTrans());
		
		try {
			String sentenciaSQL = " update "+Sesion.getDbEsquema()+".br_detalletransaccion " +
					" set estadoregistro='"+detalle.getEstadoRegistro() +"' "+
					" where numtienda="+Sesion.getNumTienda()+" and fecha='"+fechaTransString+"' and numcaja="+venta.getNumCajaFinaliza()+"" +
							" and numtransaccion="+venta.getNumTransaccion()+" and montobase="+detalle.getMonto()+" and correlativoitem="+(i+1);
			venta.setTarjetasRecargadas(venta.getTarjetasRecargadas()+1);
			Conexiones.realizarSentencia(sentenciaSQL, local);
			
		} catch (BaseDeDatosExcepcion e) {
			logger.debug("actualizarCodigosTarjetaBR(Vector)");
			e.printStackTrace();
			throw (new BaseDeDatosExcepcion("Error de al registrar la transaccion de Bono Regalo"));
		} catch (ConexionExcepcion e) {
			logger.debug("actualizarCodigosTarjetaBR(Vector)");
			throw (new BaseDeDatosExcepcion("Error de conexión con BD al registrar la transaccion de Bono Regalo"));
		} 
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarCodigosTarjetaBR(Vector) - end");
		}
	}
	
	/**
	 * Obtiene el vector de condiciones del servicio de Bono Regalo
	 * @return Vector
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<String> consultarCondicionesBR(){
		Vector<String> resultado = new Vector<String>();
		ResultSet result = null;
		
		// Armamos los vectores de la factura a recuperar
		String sentenciaSQL = "select lineacondicion from "+Sesion.getDbEsquema()+".br_condiciones where habilitado='S' order by orden";
		try {
			result = Conexiones.realizarConsulta(sentenciaSQL,true);
			result.beforeFirst();
			while (result.next()) {
				resultado.addElement(result.getString("lineacondicion"));
			} 
		} catch (SQLException e2) {
			logger.error("consultarCondicionesBR()", e2);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("consultarCondicionesBR()", e);
			e.printStackTrace();
		} catch (ConexionExcepcion e) {
			logger.error("consultarCondicionesBR()", e);
			e.printStackTrace();
		} finally {
			try {
				result.close();
			} catch (SQLException e) {
				logger.error("consultarCondicionesBR()", e);
			}
			result = null;
		}
		
		return resultado;
	}
	
	/**
	 * Cambia el estado de la venta o recarga de bonos regalo localmente o en el servidor de tienda de acuerdo a lo requerido
	 * @param venta
	 * @param nuevoEstado
	 * @param local
	 * @throws BaseDeDatosExcepcion
	 */
	public static void actualizarEstadoVentaBR(VentaBR venta, char nuevoEstado,String codautorizante, boolean local) throws BaseDeDatosExcepcion{
		actualizarEstadoVentaBR(venta, nuevoEstado, codautorizante, local, null);
	}
	
	/**
	 * Cambia el estado de la venta o recarga de bonos regalo localmente o en el servidor de tienda de acuerdo a lo requerido
	 * @param venta
	 * @param nuevoEstado
	 * @param local
	 * @throws BaseDeDatosExcepcion
	 */
	public static void actualizarEstadoVentaBR(VentaBR venta, char nuevoEstado,String codautorizante, boolean local, Date fechaAnulacion) throws BaseDeDatosExcepcion{
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarEstadoVentaBR(VentaBR) - start");
		}
		SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy-MM-dd");
		String fechaTransString = fechaTrans.format(venta.getFechaTrans());
		try {
			String sentenciaSQL = "update "+Sesion.getDbEsquema()+".br_transaccion set " +
				"estadotransaccion='"+nuevoEstado+"', codautorizante="+(codautorizante==null?"null":"'"+codautorizante+"'") +
				", fechaanulacion ="+(fechaAnulacion==null?"null":"'"+fechaTrans.format(fechaAnulacion)+"'") + " where " +
					"numtienda="+venta.getCodTienda()+" and fecha='"+fechaTransString+"' and numcaja="+venta.getNumCajaFinaliza()+" and numtransaccion="+venta.getNumTransaccion();
			Conexiones.realizarSentencia(sentenciaSQL, local);
		} catch (BaseDeDatosExcepcion e) {
			logger.debug("actualizarEstadoVentaBR(VentaBR) ");
			throw (new BaseDeDatosExcepcion("Error de al actualizar el estado de la transaccionde Bono Regalo"));
		} catch (ConexionExcepcion e) {
			logger.debug("actualizarEstadoVentaBR(VentaBR)");
			throw (new BaseDeDatosExcepcion("Error de conexión con BD actualizando el estado de la transaccion de Bono Regalo"));
		} 
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarEstadoVentaBR(VentaBR) - end");
		}
	}
	
	
	/**
	 * Obtiene la venta de bonos regalo correspondiente a los parámetros ingresados
	 * @param tienda
	 * @param fecha
	 * @param caja
	 * @param numtransaccion
	 * @return
	 * @throws BonoRegaloException 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' y se comentó código muerto
	* Fecha: agosto 2011
	*/
	public static VentaBR recuperarVentaBR(int tienda, String fecha, int caja, int numtransaccion, int numCajaBloquea) throws BonoRegaloException{
		VentaBR ventaBR = null;
		ResultSet result = null;
		
		// Armamos los vectores de la factura a recuperar
		String sentenciaSQL = "select * from "+Sesion.getDbEsquema()+".br_transaccion " +
				"where numtienda="+tienda+" and fecha='"+fecha+"' and numcaja="+caja+" and " +
						"numtransaccion="+numtransaccion+" and estadotransaccion!='A'";
		try {
			result = Conexiones.realizarConsulta(sentenciaSQL,false);
			result.beforeFirst();
			if (result.next()) {
				ventaBR = new VentaBR(
						tienda, 
						result.getDate("fecha"),
						caja,
						numtransaccion,
						result.getString("tipotransaccion").charAt(0),
						result.getString("codcajero"),
						result.getString("estadotransaccion").charAt(0),
						result.getDouble("montobase"),
						result.getDouble("montoimpuesto"),
						result.getDouble("vueltocliente"),
						result.getDouble("montoremanente"),
						result.getInt("lineasfacturacion"),
						result.getString("codautorizante"),
						result.getString("codvendedor")
				);
				
				ventaBR.asignarCliente(MediadorBD.obtenerCliente(result.getString("codcliente"), false), null);
				
				ResultSet result2 = null;
				
				if(ventaBR!=null){
					// Obtenemos los detalles del servicio
					String sentenciaSQL2 = "select * from "+Sesion.getDbEsquema()+".br_detalletransaccion " +
							"where numtienda="+tienda+" and fecha='"+fecha+"' and numcaja="+caja+" and " +
									"numtransaccion="+numtransaccion;
					try {
						result2 = Conexiones.realizarConsulta(sentenciaSQL2,false);
						result2.beforeFirst();
						while (result2.next()) {
							DetalleTransaccionBR detalle = new DetalleTransaccionBR(
									result2.getString("codtarjeta"),
									result2.getDouble("montobase"),
									result2.getInt("numseq"),
									result2.getString("estadoregistro").charAt(0));
							ventaBR.getDetallesTransaccionBR().addElement(detalle);
						}
					} catch (SQLException e2) {
						throw e2;
					} catch (BaseDeDatosExcepcion e2) {
						throw e2;
					} catch (ConexionExcepcion e2) {
						throw e2;
					} finally{
						try {
							result2.close();
						} catch (SQLException e) {
							logger.error("recuperarVentaBR(int , String , int , int )", e);
						}
						result2 = null;
					}
					
					//Obtenemos los pagos del servicio
					Vector<Pago> pagos = new Vector<Pago>();
					ResultSet result3 = null;
					String sentenciaSQL3 = "select * from "+Sesion.getDbEsquema()+".br_pagodetransaccion " +
					"where numtienda="+tienda+" and fecha='"+fecha+"' and numcaja="+caja+" and " +
							"numtransaccion="+numtransaccion;
					try {
						result3 = Conexiones.realizarConsulta(sentenciaSQL3,false);
						result3.beforeFirst();
						while (result3.next()) {
							Pago pago = new Pago(
									ManejoPagosFactory.getInstance().cargarFormaDePago(result3.getString("codformadepago")),
									result3.getDouble("monto"),
									result3.getString("codbanco"),
									result3.getString("numdocumento"),
									result3.getString("numcuenta"),
									result3.getString("numconformacion"),
									result3.getInt("numreferencia"),
									result3.getString("cedtitular")
							);
							pagos.addElement(pago);
							ventaBR.efectuarPago(pago);
						}
					} catch (SQLException e2) {
						throw e2;
					} catch (BaseDeDatosExcepcion e2) {
						throw e2;
					} catch (ConexionExcepcion e2) {
						throw e2;
					} finally{
						try {
							result3.close();
						} catch (SQLException e) {
							logger.error("recuperarVentaBR(int , String , int , int )", e);
						}
						result3 = null;
					}
					
					ventaBR.setPagos(pagos);
					
					//Obtenemos los comprobantes fiscales anteriores al servicio
					Vector<ComprobanteFiscal> comprobantes = new Vector<ComprobanteFiscal>();
					ResultSet result4 = null;
					String sentenciaSQL4 = "select * from "+Sesion.getDbEsquema()+".br_comprobantefiscal " +
					"where numtienda="+tienda+" and fecha='"+fecha+"' and numcaja="+caja+" and " +
							"numtransaccion="+numtransaccion+" order by numcomprobantefiscal";
					try {
						result4 = Conexiones.realizarConsulta(sentenciaSQL4,false);
						result4.beforeFirst();
						while (result4.next()) {
							comprobantes.addElement(
									new ComprobanteFiscal(
											ventaBR,
											result4.getInt("numcomprobantefiscal"),
											result4.getDate("fechaemision"),
											result4.getString("tipocomprobante").charAt(0),
											result4.getString("serialcaja")
											)
							);
						}
					} catch (SQLException e2) {
						throw e2;
					} catch (BaseDeDatosExcepcion e2) {
						throw e2;
					} catch (ConexionExcepcion e2) {
						throw e2;
					} finally{
						try {
							result4.close();
						} catch (SQLException e) {
							logger.error("recuperarVentaBR(int , String , int , int )", e);
						}
						result4 = null;
					}
					
					ventaBR.setComprobantesFiscales(comprobantes);
					
					
					SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy-MM-dd");
					String fechaTransString = fechaTrans.format(ventaBR.getFechaTrans());
					//Insertar en transaccionbloqueada
					try {
						if(numCajaBloquea==0){
							sentenciaSQL = " insert into "+Sesion.getDbEsquema()+".br_transaccionbloqueada (numtienda, fecha, numcaja, numtransaccion, numcajabloquea) values" +
									"( "+ventaBR.getCodTienda()+"," +
									" '"+fechaTransString+"'," +
									ventaBR.getNumCajaFinaliza()+"," +
									ventaBR.getNumTransaccion()+"," +
									Sesion.getCaja().getNumero()+") ";
							Conexiones.realizarSentencia(sentenciaSQL, false);
						}
					} catch (BaseDeDatosExcepcion e) {
						logger.debug("recuperarVentaBR(int tienda, String fecha, int caja, int numtransaccion)");
						throw (new BaseDeDatosExcepcion("Error de al recuperar la transaccion de Bono Regalo"));
					} catch (ConexionExcepcion e) {
						logger.debug("recuperarVentaBR(int tienda, String fecha, int caja, int numtransaccion)");
						throw (new BaseDeDatosExcepcion("Error de conexión con BD al recuperar la transaccion de Bono Regalo"));
					} 
					
				} /*else
					throw new BonoRegaloException("Identificador de transacción inválido.\nNo se pudo recuperar la transacción");
					*/
			} else {
				throw new BonoRegaloException("Identificador de transacción inválido.\nNo se pudo recuperar la transacción");
			}

		} catch (SQLException e2) {
			logger.error("recuperarVentaBR(int tienda, String fecha, int caja, int numtransaccion)", e2);
			throw (new BonoRegaloException("Error al recuperar la transaccion de Bono Regalo", e2));
		} catch (BaseDeDatosExcepcion e) {
			logger.error("recuperarVentaBR(int tienda, String fecha, int caja, int numtransaccion)", e);
			throw (new BonoRegaloException("Error al recuperar la transaccion de Bono Regalo", e));
		} catch (ConexionExcepcion e) {
			logger.error("recuperarVentaBR(int tienda, String fecha, int caja, int numtransaccion)", e);
			throw (new BonoRegaloException("Error al recuperar la transaccion de Bono Regalo", e));
		} catch (XmlExcepcion e) {
			logger.error("recuperarVentaBR(int tienda, String fecha, int caja, int numtransaccion)", e);
			throw (new BonoRegaloException("Error al recuperar la transaccion de Bono Regalo", e));
		} catch (FuncionExcepcion e) {
			logger.error("recuperarVentaBR(int tienda, String fecha, int caja, int numtransaccion)", e);
			throw (new BonoRegaloException("Error al recuperar la transaccion de Bono Regalo", e));
		} catch (AfiliadoUsrExcepcion e) {
			logger.error("recuperarVentaBR(int tienda, String fecha, int caja, int numtransaccion)", e);
			throw (new BonoRegaloException("Error al recuperar la transaccion de Bono Regalo", e));
		} catch (LineaExcepcion e) {
			logger.error("recuperarVentaBR(int tienda, String fecha, int caja, int numtransaccion)", e);
			throw (new BonoRegaloException("Error al recuperar la transaccion de Bono Regalo", e));
		} catch (AutorizacionExcepcion e) {
			logger.error("recuperarVentaBR(int tienda, String fecha, int caja, int numtransaccion)", e);
			throw (new BonoRegaloException("Error al recuperar la transaccion de Bono Regalo", e));
		} catch (ClienteExcepcion e) {
			logger.error("recuperarVentaBR(int tienda, String fecha, int caja, int numtransaccion)", e);
			throw (new BonoRegaloException("Error al recuperar la transaccion de Bono Regalo", e));
		} finally {
			try {
				result.close();
			} catch (SQLException e) {
				logger.error("consultarCondicionesBR()", e);
			}
			result = null;
		}
		
		return ventaBR;
	}
	
	/**
	 * Actualizar registro de impresión del comprobante fiscal de Bono Regalo
	 * @param venta
	 * @throws BaseDeDatosExcepcion 
	 */
	public static void actualizarComprobanteFiscalBR(VentaBR ventaBR, char tipoComprobante, boolean local) throws BaseDeDatosExcepcion{
		if (logger.isDebugEnabled()) {
			logger.debug("registrarTransaccionBR(VentaBR) - start");
		}
		
		Statement loteSentenciasCR  = null;
		try {
			loteSentenciasCR = Conexiones.crearSentencia(local);
			SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy-MM-dd");
			String fechaTransString = fechaTrans.format(Sesion.getFechaSistema());
			String fechaTransStringTrx = fechaTrans.format(ventaBR.getFechaTrans());
			
			String sentenciaSQL = "insert into "+Sesion.getDbEsquema()+".br_comprobantefiscal " +
					"(numtienda," +
					"fecha," +
					"numcaja," +
					"numtransaccion," +
					"numcomprobantefiscal," +
					"fechaemision," +
					"tipocomprobante," +
					"serialcaja) "
				+ "values (" + Sesion.getTienda().getNumero() + ", " 
				+ "'" + fechaTransStringTrx + "', "
				+ Sesion.getNumCaja() + ", "
				+ ventaBR.getNumTransaccion() + ", "
				+ ventaBR.getNumComprobanteFiscal() + "," 
				+ "'"+fechaTransString+"', "
				+ "'" + tipoComprobante + "', "
				+ "'"+Sesion.getCaja().getSerial()+"')";
				
			loteSentenciasCR.addBatch(sentenciaSQL);
			
			sentenciaSQL = " update "+Sesion.getDbEsquema()+".br_transaccion set estadotransaccion='"+ventaBR.getEstadoTransaccion()+"', horafinaliza='"+ventaBR.getHoraFin()+"', duracionventa="+ventaBR.getDuracion()+" where " +
					" numtienda="+ventaBR.getCodTienda()+" and " +
					" numtransaccion="+ventaBR.getNumTransaccion()+" and " +
					" fecha='"+fechaTransStringTrx+"' and" +
					" numcaja="+ventaBR.getNumCajaFinaliza()+" ";
			loteSentenciasCR.addBatch(sentenciaSQL);
			
			if(Sesion.TIPO_TRANSACCION_BR_RECARGA_PROMO==ventaBR.getTipoTransaccion() && 
					ventaBR.getAccion()!=Sesion.ACCION_BONO_REGALO_ANULACION){
				sentenciaSQL = "insert into "+Sesion.getDbEsquema()+".br_registropromocion values("+Sesion.getTienda().getNumero()+"," +
						"'"+fechaTransStringTrx+"',"+Sesion.getNumCaja()+","+ventaBR.getNumTransaccion()+", '"+ventaBR.getCodTarjetaPagoPromocion()+"', '"+((DetalleTransaccionBR)ventaBR.getDetallesTransaccionBR().elementAt(0)).getCodTarjeta().substring(14)+"', '"+Sesion.NO+"')";
				
				loteSentenciasCR.addBatch(sentenciaSQL);
			}
			
			if(ventaBR.getAccion()==Sesion.ACCION_BONO_REGALO_ANULACION){
				sentenciaSQL = " update "+Sesion.getDbEsquema()+".br_detalletransaccion set codtarjeta=null " +
						"where numtienda="+ventaBR.getCodTienda()+" and " +
						"fecha='"+fechaTransStringTrx+"' and "+
						"numcaja="+ventaBR.getNumCajaFinaliza()+" and " +
						"numtransaccion="+ventaBR.getNumTransaccion()+" and " +
						"estadoregistro='"+Sesion.DETALLE_BR_ELIMINADO+"'";
				loteSentenciasCR.addBatch(sentenciaSQL);
						
			}
			
			Conexiones.ejecutarLoteSentencias(loteSentenciasCR, local, true);
		} catch (ConexionExcepcion e) {
			logger.debug("actualizarVentaBRNoSincronizada(VentaBR)", e);
			throw new BaseDeDatosExcepcion(e.getMensaje());
		} catch (SQLException e) {
			logger.debug("actualizarVentaBRNoSincronizada(VentaBR)", e);
			throw new BaseDeDatosExcepcion("Problema ejecutando sentencia en base de datos");
		} 
		if (logger.isDebugEnabled()) {
			logger.debug("registrarTransaccionBR(VentaBR) - end");
		}
	}
	
	/**
	 * Elimina la transaccion bloqueada de la base de datos
	 * @param venta
	 */
	public static void desbloquearTransaccionBR(VentaBR venta){
		if (logger.isDebugEnabled()) {
			logger.debug("desbloquearTransaccionBR(VentaBR) - start");
		}
		SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy-MM-dd");
		String fechaTransString = fechaTrans.format(venta.getFechaTrans());
		try {
			String sentenciaSQL = "delete from "+Sesion.getDbEsquema()+".br_transaccionbloqueada where " +
					" numtienda="+venta.getCodTienda()+" and" +
					" fecha='"+fechaTransString+"' and" +
					" numcaja="+venta.getNumCajaFinaliza()+" and" +
					" numtransaccion="+venta.getNumTransaccion();
			Conexiones.realizarSentencia(sentenciaSQL, false);
		} catch (BaseDeDatosExcepcion e) {
			logger.debug("desbloquearTransaccionBR(VentaBR)");
		} catch (ConexionExcepcion e) {
			logger.debug("desbloquearTransaccionBR(VentaBR)");
		} 
		if (logger.isDebugEnabled()) {
			logger.debug("desbloquearTransaccionBR(VentaBR) - end");
		}
	}
	
	/**
	 * Obtiene las operaciones de Carga/Regarga de BR realizadas por el usuario
	 *	@return Vector: Pos 0: Campo Boolean que indica si existen operaciones de Bonos Regalos
	 *				    Pos 1: Campo Entero que indica el número de comprobante de Caja Principal
	 *					Pos 2: Campo Vector con las formas de pagos y los montos registrados
	 * @throws ConexionExcepcion 
	 * @throws BaseDeDatosExcepcion 
	 * @throws SQLException 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public static Vector<Object> obtenerOperacionesBR(Usuario usuarioActivo, Date fecha) throws BaseDeDatosExcepcion, ConexionExcepcion, SQLException {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerOperacionesBR() - start");
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dfCP = new SimpleDateFormat("yyyyMMdd");
		Vector<Object> resultado = new Vector<Object>();
		boolean existenOperaciones = false;
		int nroComprobante = 0;
		Vector<Vector<Object>> pagos = new Vector<Vector<Object>>();

		//String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "obtenerOperacionesBR");
		//CR.me.verificarAutorizacion("FACTURACION", "obtenerOperacionesBR");
		String sql = "SELECT fp.nombre, SUM(CASE WHEN fp.codformadepago<>'" + Sesion.FORMA_PAGO_EFECTIVO + "'" +
					" THEN p.monto ELSE p.monto - t.vueltocliente END), fp.prioridad FROM br_transaccion t INNER JOIN" +
					" br_pagodetransaccion p  ON (t.numtienda=p.numtienda AND t.fecha=p.fecha AND t.numcaja=p.numcaja" +
					" AND t.numtransaccion=p.numtransaccion) INNER JOIN formadepago fp ON (p.codformadepago=fp.codformadepago)" +
					" WHERE estadotransaccion <> 'P' AND estadotransaccion <> 'A' AND codcajero = '" + usuarioActivo.getNumFicha() + "'" +
					" AND t.fecha = '" + df.format(fecha) + "' GROUP by fp.nombre, fp.prioridad order by fp.prioridad";

		ResultSet transacciones = Conexiones.realizarConsulta(sql, false);
		
		transacciones.beforeFirst();
		while (transacciones.next()) {
			existenOperaciones = true;
			Vector<Object> pagoActual = new Vector<Object>();
			pagoActual.add(transacciones.getString(1));
			pagoActual.add(new Double(transacciones.getDouble(2)));
			pagos.add(pagoActual);
		}
		try{transacciones.close();}catch (Exception e) {}
		
		if (existenOperaciones) {
			// Ubicamos comprobante en Caja Principal
			String sentenciaSql = "select nrocom from ictfile.capd11 where tienda = " + Sesion.getTienda().getNumero() + 
					" and tipreg='IFA' and correg=7 and concep like '%" + usuarioActivo.getNumFicha() +
					"%' and fecreg='" + dfCP.format(fecha) + "'";
			ResultSet consCP = ConexionServCentral.realizarConsulta(sentenciaSql);
			if (consCP.first()) {
				nroComprobante = consCP.getInt(1);
			}
			try{consCP.close();}catch (Exception e) {}
		}
		// Armamos el resultado del método
		resultado.add(new Boolean(existenOperaciones));
		resultado.add(new Integer(nroComprobante));
		resultado.add(pagos);
		
		//Actualizamos el estado de la caja
		//MaquinaDeEstado.setEstadoCaja(edoFinalCaja);
		
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerOperacionesBR() - end");
		}
		
		return resultado;
	}

	/**
	 * Obtiene el numero de caja que tiene bloqueada una transaccion de bonos regalo
	 * @param numtienda
	 * @param fecha
	 * @param caja
	 * @param numtransaccion
	 * @return
	 */
	public static int getNumCajaBloquea(int numtienda, String fecha, int caja, int numtransaccion){
		int resultado =0;
		ResultSet result3 = null;
		String sentenciaSQL3 = "select * from "+Sesion.getDbEsquema()+".br_transaccionbloqueada " +
		"where numtienda="+numtienda+" and fecha='"+fecha+"' and numcaja="+caja+" and " +
				"numtransaccion="+numtransaccion;
		try {
			result3 = Conexiones.realizarConsulta(sentenciaSQL3,false);
			result3.beforeFirst();
			while (result3.next()) {
				resultado = result3.getInt("numcajabloquea");
			}
		} catch (SQLException e2) {
			return 0;
		} catch (BaseDeDatosExcepcion e2) {
			return 0;
		} catch (ConexionExcepcion e2) {
			return 0;
		} finally{
			try {
				result3.close();
			} catch (SQLException e) {
				logger.error("getNumCajaBloquea(int , String , int , int )", e);
			}
			result3 = null;
		}
		return resultado;
	}
	
	/**
	 * Cambia el cliente asignado a la venta
	 * @param venta
	 * @throws BonoRegaloException 
	 */
	public static void actualizarClienteAsignado(VentaBR venta) throws BonoRegaloException{
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarClienteAsignado(VentaBR) - start");
		}
		SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy-MM-dd");
		String fechaTransString = fechaTrans.format(venta.getFechaTrans());
		try {
			String sentenciaSQL = "update "+Sesion.getDbEsquema()+".br_transaccion " +
					" set codcliente='"+venta.getCliente().getCodCliente()+"' where " +
					" numtienda="+venta.getCodTienda()+" and fecha='"+fechaTransString+"' and numcaja="+venta.getNumCajaFinaliza()+" and numtransaccion="+venta.getNumTransaccion();
			Conexiones.realizarSentencia(sentenciaSQL, false);
		} catch (BaseDeDatosExcepcion e) {
			logger.debug("actualizarClienteAsignado(VentaBR)");
			throw new BonoRegaloException("No se pudo cambiar el cliente asignado");
		} catch (ConexionExcepcion e) {
			logger.debug("actualizarClienteAsignado(VentaBR)");
			throw new BonoRegaloException("No se pudo cambiar el cliente asignado");
		} 
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarClienteAsignado(VentaBR) - end");
		}
	}
	
	/**
	 * Consulta en la bd las promociones activas de bono regalo
	 * @return
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<PromocionBR> getPromocionesActivasBR() throws BaseDeDatosExcepcion{
		Vector<PromocionBR> promociones = new Vector<PromocionBR>();
		SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy-MM-dd");
		ResultSet result3 = null;
		String sentenciaSQL3 = "select * from "+Sesion.getDbEsquema()+".br_promocion where " +
				" (fechainicia<='"+fechaTrans.format(Sesion.getFechaSistema())+"' AND activo='"+Sesion.SI+"') OR" +
				" (fechainicia<='"+fechaTrans.format(Sesion.getFechaSistema())+"' AND fechafinaliza>='"+fechaTrans.format(Sesion.getFechaSistema())+"') ";
		
		try {
			result3 = Conexiones.realizarConsulta(sentenciaSQL3,true);
			result3.beforeFirst();
			while (result3.next()) {
				PromocionBR promocion = new PromocionBR(
						result3.getDate("fechainicia"), 
						result3.getDate("fechainicia"),
						result3.getDouble("porcentaje"), 
						result3.getString("activo").equalsIgnoreCase("S"),
						result3.getString("tipo").charAt(0));
				promociones.addElement(promocion);
			}
		} catch (SQLException e2) {
			throw new BaseDeDatosExcepcion("Problema consultando promociones de Bono Regalo\n No se podrá recargar la tarjeta");
		} catch (ConexionExcepcion e2) {
			throw new BaseDeDatosExcepcion("Problema consultando promociones de Bono Regalo\n No se podrá recargar la tarjeta");
		} catch (Exception e){
			throw new BaseDeDatosExcepcion("Problema consultando promociones de Bono Regalo\n No se podrá recargar la tarjeta");
		} finally{
			try {
				result3.close();
			} catch (SQLException e) {
				logger.error("getPromocionesActivasBR( )", e);
			}
			result3 = null;
		}
		return promociones;
	}
}
