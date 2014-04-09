/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.manejarventa
 * Programa   : BaseDeDatosVenta.java
 * Creado por : gmartinelli
 * Creado en  : 30-oct-03 15:02:20
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 2.1.1
 * Fecha       : 08-JUL-2008 08:14am
 * Analista    : jgraterol
 * Descripción : Las insercion de promociones al lote de sentencias al registrar la 
 * 				transacción se hace ahora en un método implementado en la extension
 * =============================================================================
 * Versión     : 2.1
 * Fecha       : 04-JUL-2008 08:14am
 * Analista    : jgraterol
 * Descripción : Agregadas las promociones nuevas a las promociones de un producto
 * =============================================================================
 * Versión     : 2.1
 * Fecha       : 10-jun-2008 02:14pm
 * Analista    : jgraterol, aavila
 * Descripción : Agregado a registrarTransaccion la insercion de las donaciones realizadas
 * 			durante la venta. En caso de que no este usandose la extension de promociones,
 * 			el vector de donaciones estara vacio y no afecta el funcionamiento de caja
 * =============================================================================
 * Versión     : 2.0
 * Fecha       : 19-jul-04 08:43
 * Analista    : yzambrano
 * Descripción : Cambios para captura de datos de clientes temporales tales como registrado, contactar
 				 y estadocliente.
 				 Si es un afiliado se lanza una excepción para indicar que no se puede registrar de nuevo
 				 sus datos
 * =============================================================================
 * Versión     : 1.6
 * Fecha       : 19-jul-04 08:43
 * Analista    : gmartinelli
 * Descripción : Uso de Esquema en los queryes CR.tabla.campo
 * =============================================================================
 * Versión     : 1.5.6
 * Fecha       : 24/03/2004 14:32 PM
 * Analista    : gmartinelli
 * Descripción : Agregados metodos:
 * 					obtenerVenta(tda,caja,trans) - Que obtiene la venta de la tda en
 * 				caja con numero de transaccion trans.
 * 					cargarDevoluciones(tda,fecha,caja,trans) - Que carga las devoluciones
 * 				realizadas sobre la venta
 * 					registrarTransaccion(devolucion) - Que registra en la base de datos una
 * 				transaccion de devolucion.
 * 				 Modificados algunas sentencias SQL de las facturas en espera y actualizacion de
 * 				los estados de transaccion por unos detalles encontrados. 
 * =============================================================================
 * Versión     : 1.5.5
 * Fecha       : 08/03/2004 05:00 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Agregado el método "recuperarComanda".
 * =============================================================================
 * Versión     : 1.5.4
 * Fecha       : 02/03/2004 11:11 AM
 * Analista    : irojas
 * Descripción : Linea 664: En el método "obtenerCliente" modificada sentenciaSQL 
 * 				 para la obtención del afiliado.
 * =============================================================================
 * Versión     : 1.5.4
 * Fecha       : 02/03/2004 09:40 AM
 * Analista    : gmartinelli
 * Descripción : Modificado metodo obtenerProducto para manejar atributos de las 
 * 				unidades de venta y chequear si permiten o no cantidades fraccionadas.
 * =============================================================================
 * Versión     : 1.5.3
 * Fecha       : 02/03/2004 09:40 AM
 * Analista    : gmartinelli
 * Descripción : Cambios de atributo de cantidades de int a float;
 * =============================================================================
 * Versión     : 1.5.2
 * Fecha       : 20/02/2004 11:33 AM
 * Analista    : irojas
 * Descripción : Agregada importación del paquete com.becoblohm.cr.excepciones.ExcepcionCr;
 * =============================================================================
 * Versión     : 1.5.2
 * Fecha       : 20/02/2004 11:01 AM
 * Analista    : irojas
 * Descripción : - Agregado método "obtenerCliente" para obtener el afiliado de la BD
 * 				 - Línea 343 del método registrarTransaccion agregó el registro del cliente a 
 * 			       la venta
 * =============================================================================
 * Versión     : 1.5.2
 * Fecha       : 20/02/2004 10:58 AM
 * Analista    : irojas
 * Descripción : Modificado método obtenerProducto para que no obtuviese el parámetro
 * 				 "desctoventaempleado" que fue eliminado de la tabla Producto en la BD
 * =============================================================================
 * Versión     : 1.5.2
 * Fecha       : 19/02/2004 11:40 AM
 * Analista    : gmartinelli
 * Descripción : Agregados metodos colocarEnEspera y recuperarDeEspera
 * 				 lineas 414 y 507 respectivamente.
 * 				 Para manejar facturas en espera
 * =============================================================================
 * Versión     : 1.5.1
 * Fecha       : 19/02/2004 11:08 AM
 * Analista    : gmartinelli
 * Descripción : Agregadas lineas 373-383 del metodo registrarTransaccion.
 * 				 Se registran ahora las formas de pago de las ventas
 * 				 en la tabla correspondiente de la Base de Datos
 * =============================================================================
 * Versión     : 1.5 (según CVS)
 * Fecha       : 16/02/2004 10:23 AM
 * Analista    : Ileana Rojas
 * Descripción : Modificación de sentencia SQL para insertar el detalle de la transacción
 * 				 por falla detectada al integrar con GUI en el código del supervisor.
 * =============================================================================
 * Versión     : 1.4 (según CVS antes del cambio)
 * Fecha       : 10/02/2004 08:01 PM
 * Analista    : Ileana Rojas
 * Descripción : Modificación de sentencia SQL para insertar una cabecera de transacción en la BD. 
 * 				 Adaptación a la nueva versión de la BD:Agregado campo código de factura en espera a la sentencia.
 * =============================================================================
 * Versión     : 1.0.1
 * Fecha       : 09/02/2004 04:20 PM
 * Analista    : Ileana Rojas
 * Descripción : Cambios de códigos de producto de Long a String
 * =============================================================================
 */
package com.becoblohm.cr.mediadoresbd;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.beco.cr.actualizadorPrecios.promociones.DonacionRegistrada;
import com.beco.cr.actualizadorPrecios.promociones.PromocionRegistrada;
import com.beco.cr.actualizadorPrecios.promociones.RegaloRegistrado;
import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.AfiliadoUsrExcepcion;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.PagoExcepcion;
import com.becoblohm.cr.excepciones.ProductoExcepcion;
import com.becoblohm.cr.extensiones.ActualizadorPrecios;
import com.becoblohm.cr.extensiones.ActualizadorPreciosFactory;
import com.becoblohm.cr.extensiones.CongeladorFactura;
import com.becoblohm.cr.extensiones.CongeladorFacturaFactory;
import com.becoblohm.cr.extensiones.ManejoPagosFactory;
import com.becoblohm.cr.extensiones.RegistroCliente;
import com.becoblohm.cr.extensiones.RegistroClienteFactory;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstadoVenta;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.manejarservicio.ListaRegalos;
import com.becoblohm.cr.manejarventa.Anulacion;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.manejarventa.CondicionVenta;
import com.becoblohm.cr.manejarventa.DetalleTransaccion;
import com.becoblohm.cr.manejarventa.Devolucion;
import com.becoblohm.cr.manejarventa.Impuesto;
import com.becoblohm.cr.manejarventa.Producto;
import com.becoblohm.cr.manejarventa.Promocion;
import com.becoblohm.cr.manejarventa.Transaccion;
import com.becoblohm.cr.manejarventa.Venta;
import com.becoblohm.cr.utiles.Control;
import com.becoblohm.cr.utiles.EAN13Code;
import com.becoblohm.cr.utiles.MathUtil;

/** 
 * Descripción:
 * 		Esta clase realiza todas las operaciones de realizacion de sentencias de la
 * base de Datos para el manejo de Transacciones de la Caja Registradora.
 */
public class BaseDeDatosVenta {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(BaseDeDatosVenta.class);

	//Insertar en devolución en espera	
	static final String almacenar = "insert into devolucionespera (numtienda,fecha,caja,numtransaccion,tipotransaccion,objeto)"
				+ "values (?,?,?,?,?,?)";
	static final String recuperar = "SELECT tipotransaccion, objeto FROM devolucionespera WHERE numtienda = ? and fecha = ? and caja = ? and numtransaccion = ?";
	static final String recuperarTodas = "SELECT * FROM devolucionespera WHERE tipotransaccion = 'D'";
	/**
	 * Obtiene un objeto Producto con los datos del producto, sus promociones y su impuesto.
	 * @param codProd Codigo del producto a buscar.
	 * @param fechaTrans Fecha de la transaccion (Para la busqueda de promociones activas).
	 * @param horaInicioTrans Hora inicio de la transaccion (Para la busqueda de promociones activas).
	 * @return Producto - El producto encontrado en la base de datos.
	 * @throws BaseDeDatosExcepcion Si ocurrio una falla en la conexion con la base de datos.
	 * @throws ProductoExcepcion Si el producto no existe.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Producto obtenerProducto(String codProd, Date fechaTrans, Time horaInicioTrans, boolean leidoBD) throws ProductoExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerProducto(String, Date, Time, boolean) - start");
		}

		Producto prodRetorno = null;
		
		// Atributos del producto a crear
		String codProducto = "";
		String descripcionCorta;
		String descripcionLarga;
		int codUnidadVenta;
		String descUnidadVenta;
		String abreviadoUnidadVenta;
		boolean indicaFraccion;
		String referenciaProveedor;
		String marca;
		String modelo;
		String codDepartamento;
		String lineaSeccion;
		double costoLista;
		double precioRegular;
		float cantidadVentaEmpaque = Float.MAX_VALUE;
		double desctoVentaEmpaque = 0.0;
		boolean indicaDesctoEmpleado = false;
		double desctoVentaEmpleado = 0.0;
		char estadoProducto;
		String tipoEntrega = Sesion.ENTREGA_CAJA;
		Date fechaActualizacion;
		Time horaActualizacion;
		Impuesto impuesto = null;
		Vector<Promocion> promociones = new Vector<Promocion>();
		int longitud = codProd.length();
		StringBuffer xCodProd1 = new StringBuffer(codProd);
		StringBuffer xCodProd2 = new StringBuffer(codProd);
		StringBuffer xCodProd3 = new StringBuffer(codProd);
		if (longitud <= Control.getLONGITUD_CODIGO()){
			for (int i=0; i<Control.getLONGITUD_CODIGO()-longitud; i++){
				xCodProd1.insert(0,'0');
			}
			for (int i=0; i<(Control.getLONGITUD_CODIGO()-1-Control.getFORMATO_PRODUCTO().length())-longitud; i++){
				xCodProd2.insert(0,'0');
			}
			if (xCodProd2.length() == Control.getLONGITUD_CODIGO()-1){
				xCodProd2.deleteCharAt(0);
				xCodProd2.insert(0,Control.getFORMATO_PRODUCTO());
			} else xCodProd2.insert(0,Control.getFORMATO_PRODUCTO());
			String code13Prod = EAN13Code.getCode(xCodProd1.toString());
			if (code13Prod != null)
				xCodProd3 = new StringBuffer(code13Prod);
			else
				xCodProd3 = null;
		}

		ResultSet rs;
		String sentenciaSQL = null;
		if (leidoBD) {
			sentenciaSQL = "select * from producto p where p.codproducto = '" + codProd + "'";			
		} else {
			sentenciaSQL = "select * from prodcodigoexterno pe inner join producto p on (p.codproducto = pe.codproducto)where ";
			
			if (xCodProd3 != null) 
				sentenciaSQL += "  pe.codexterno = '" + xCodProd3.toString() + "' or " ;
							
			sentenciaSQL += "pe.codexterno = '" + codProd + "'";	
		}
		
		rs = Conexiones.realizarConsulta(sentenciaSQL,true);
		try {
			if (!rs.first()) {
				if (!leidoBD) {
					rs.close();
					sentenciaSQL = "select * from prodcodigoexterno pe inner join producto p on (p.codproducto = pe.codproducto)where (pe.codexterno = '" + xCodProd1.toString() + "') or (pe.codexterno = '" + xCodProd2.toString() + "')";
					rs = Conexiones.realizarConsulta(sentenciaSQL,true);
				}
			}
		} catch (SQLException e) {
			logger.error("obtenerProducto(String, Date, Time, boolean)", e);

			try {
				rs.close();
			} catch (SQLException e1) {
				logger.error("obtenerProducto(String, Date, Time, boolean)", e1);
			}
			throw (new BaseDeDatosExcepcion("Falló la conexión con la base de datos al obtener datos del producto", e));
		} 

		try {
			if(rs.first()) {
				codProducto = rs.getString("codproducto");
				descripcionCorta = rs.getString("descripcioncorta");
				descripcionLarga = rs.getString("descripcionlarga");
				codUnidadVenta = rs.getInt("codunidadventa");
				referenciaProveedor = rs.getString("referenciaproveedor");
				marca = rs.getString("marca");
				modelo = rs.getString("modelo");
				codDepartamento = rs.getString("coddepartamento");
				lineaSeccion = rs.getString("codlineaseccion");
				// Se agreegó estas 2 lineas para comparar en Entero
				int departamento = Integer.parseInt(codDepartamento);
				int linea = Integer.parseInt(lineaSeccion);
				
				costoLista = rs.getDouble("costolista");
				precioRegular = rs.getDouble("precioregular");
				
				
				
				// Si el producto posee precio cero, lanzamos una excepcion por inconsistencias en lan BD
				if (precioRegular<=0)
					throw new ProductoExcepcion("Producto No Válido");
				if (rs.getInt("cantidadventaempaque") > 0) {
					cantidadVentaEmpaque = rs.getFloat("cantidadventaempaque");
					desctoVentaEmpaque = rs.getDouble("desctoventaempaque");
				}
				if (rs.getString("indicadesctoempleado").toCharArray()[0] == Sesion.SI) {
					indicaDesctoEmpleado = true;
				}
				if (rs.getString("indicadespachar").toCharArray()[0] == Sesion.SI) {
					tipoEntrega = Sesion.ENTREGA_DESPACHO;
				}
				estadoProducto = (char)(rs.getString("estadoproducto").toCharArray()[0]);
				fechaActualizacion = rs.getDate("actualizacion");
				horaActualizacion = rs.getTime("actualizacion");
				// Buscamos los impuestos
                sentenciaSQL = "select * from impuestoregion where impuestoregion.codimpuesto = '" + rs.getString("codimpuesto")
							+ "' and ((impuestoregion.codregion = '" + Sesion.getTienda().getCodRegion() + "') or (impuestoregion.codregion = '***')) order by impuestoregion.fechaemision desc";
				rs.close();
				rs = Conexiones.realizarConsulta(sentenciaSQL,true);
				boolean encontradoImpuesto = false;
				if (rs.first()) {
					rs.beforeFirst();
					while((!encontradoImpuesto)&&(rs.next())) {
						String codImp = rs.getString("codimpuesto");
						String codReg = rs.getString("codregion");
						Date fVigencia = rs.getDate("fechaemision");
						Date fSistema = Sesion.getFechaSistema();
						if (fSistema.compareTo(fVigencia) >= 0) {
							impuesto = new Impuesto(codImp, codReg, rs.getString("descripcion"), fVigencia, rs.getDouble("porcentaje"));
							encontradoImpuesto=true;
						}
					}
				} else {
					throw (new ProductoExcepcion("Error al cargar el impuesto del producto " + codProducto));
				}
				rs.close();
				if (!encontradoImpuesto)
					throw (new ProductoExcepcion("Error al cargar el impuesto del producto " + codProducto));
					
				// Buscamos la descripcion de la unidad de venta del producto
				sentenciaSQL = "select * from unidaddeventa where unidaddeventa.codunidadventa = " + codUnidadVenta;
				rs = Conexiones.realizarConsulta(sentenciaSQL,true);
				if (rs.first()) {
					descUnidadVenta = rs.getString("descripcion");
					abreviadoUnidadVenta = rs.getString("abreviado");
					indicaFraccion = (rs.getString("indicafraccion").toCharArray()[0] == Sesion.SI)
									? true : false;
				} else {
					throw (new ProductoExcepcion("Error al cargar la descripcion de la unidad de venta " + codUnidadVenta + ". Producto: "+ codProducto));
				}
				rs.close();
				// Buscamos las promociones vigentes
				//MensajesVentanas.aviso("Buscar promociones producto: " + codProducto);
				SimpleDateFormat fechaActual = new SimpleDateFormat("yyyy-MM-dd");
				String fechaActualString = fechaActual.format(fechaTrans);
				String horaActual = horaInicioTrans.toString();
				sentenciaSQL = "select * from promocion p inner join detallepromocion d on (p.codpromocion = d.codpromocion) where "
						+ "((d.codproducto = '" + codProducto + "' and CONVERT(d.coddepartamento, unsigned) = '" + departamento + "' and CONVERT(d.codlineaseccion, unsigned)='" + linea + "')"
						+ " or (d.codproducto is null and CONVERT(d.coddepartamento, unsigned) = '" + departamento + "' and d.codlineaseccion is null)"
						+ " or (d.codproducto is null and CONVERT(d.coddepartamento, unsigned) = '" + departamento + "' and CONVERT(d.codlineaseccion, unsigned) = '" + linea + "')) and "
						+ " (d.estadoregistro = '" + Sesion.PROMOCION_ACTIVA + "') and "
						+ "((p.fechainicio < '" + fechaActualString + "' and p.fechafinaliza > '" + fechaActualString + "' and (('" + horaActual + "' between p.horainicio and p.horafinaliza)) or p.horainicio=p.horafinaliza) or "
						+ "(p.fechainicio = '" + fechaActualString + "' and p.fechafinaliza <> '" + fechaActualString + "' and p.horainicio <= '" + horaActual + "') or "
						+ "(p.fechainicio <> '" + fechaActualString + "' and p.fechafinaliza = '" + fechaActualString + "' and p.horafinaliza >= '" + horaActual + "') or "
						+ "(p.fechainicio = '" + fechaActualString + "' and p.fechafinaliza = '" + fechaActualString + "' and ('" + horaActual + "' between p.horainicio and p.horafinaliza))) order by p.prioridad asc ";
			//	MensajesVentanas.aviso("Mostar query");
			//	System.out.println("Ejecutar: " + sentenciaSQL);
			//	MensajesVentanas.aviso(sentenciaSQL.substring(0,sentenciaSQL.length()/3) + sentenciaSQL.substring(sentenciaSQL.length()/3,sentenciaSQL.length()/3));
				rs = Conexiones.realizarConsulta(sentenciaSQL,true);
				rs.beforeFirst();
				//MensajesVentanas.aviso("ejecutó query");
				while (rs.next()) {
					//MensajesVentanas.aviso("Promocion encontrada: " + rs.getInt("codpromocion"));
					int codPromocion = rs.getInt("codpromocion");
					char tipoPromocion = (char)(rs.getString("tipopromocion").toCharArray()[0]);
					Date fechaInicio = rs.getDate("fechainicio");
					Time horaInicio = rs.getTime("horainicio");
					Date fechaFinaliza = rs.getDate("fechafinaliza");
					Time horaFinaliza = rs.getTime("horafinaliza");
					int prioridad = rs.getInt("prioridad");
					int numDetalle = rs.getInt("numdetalle");
					int numCupon = rs.getInt("numcupon");
					double porcentajeDescuento = rs.getDouble("porcentajedescuento");
					double precioFinal = rs.getDouble("preciofinal");
					Promocion p = new Promocion (codPromocion, tipoPromocion, fechaInicio, horaInicio,
												fechaFinaliza, horaFinaliza, prioridad, numDetalle, numCupon,
												codDepartamento, lineaSeccion, codProducto, porcentajeDescuento,
												precioFinal);
					promociones.addElement(p);
				}
				
				//MensajesVentanas.aviso("Terminó busqueda de promociones");
				// Creamos el producto a retornar
				prodRetorno = new Producto(codProducto, descripcionCorta, descripcionLarga, codUnidadVenta,
										descUnidadVenta, abreviadoUnidadVenta, indicaFraccion, referenciaProveedor,
										marca, modelo, codDepartamento, lineaSeccion, costoLista, precioRegular,
										cantidadVentaEmpaque, desctoVentaEmpaque, indicaDesctoEmpleado,
										desctoVentaEmpleado, estadoProducto, fechaActualizacion, horaActualizacion,
										impuesto, promociones, tipoEntrega);
				
				//Cargamos la seccion de producto - Actualizacion BECO 19-02-2009 Promociones
				try{
					sentenciaSQL = " SELECT codSeccion FROM prodseccion where codProducto='"+prodRetorno.getCodProducto()+"' and codDepartamento='"+prodRetorno.getCodDepartamento()+"' and codLinea='"+prodRetorno.getLineaSeccion()+"' ";
					rs = Conexiones.realizarConsulta(sentenciaSQL,true);
					rs.beforeFirst();
					if(rs.next()){
						int codSeccion = rs.getInt("codSeccion");
						prodRetorno.setSeccion(codSeccion);
					}
					
					sentenciaSQL = " SELECT codcat FROM catdep where codDep='"+Integer.parseInt(prodRetorno.getCodDepartamento())+"' ";
					rs = Conexiones.realizarConsulta(sentenciaSQL,true);
					rs.beforeFirst();
					if(rs.next()){
						String codCategoria = rs.getString("codcat");
						prodRetorno.setCategoria(codCategoria);
					}
					
				} catch(Exception e){
					//La tabla no existe porque no esta activo el módulo de promociones
				}
				
				
			} else {
				throw (new ProductoExcepcion("Código no registrado"));
			}
		} catch (SQLException ex) {
			logger.error("obtenerProducto(String, Date, Time)", ex);

			throw (new BaseDeDatosExcepcion("Falló la conexión con la base de datos al obtener datos del producto", ex));
		} finally {
			try {
				rs.close();
			} catch (SQLException e1) {
				logger.error("obtenerProducto(String, Date, Time)", e1);
			}
			rs = null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerProducto(String, Date, Time) - end");
		}
		return prodRetorno;
	}
	
	/**
	 * Obtiene el codigo de una forma de pago (Solo es una prueba para manejo de efectivo).
	 * @param nombre que contiene el nombre de la forma de pago.
	 * @return String - String que representa el codigo de la forma de pago.
	 */
	public static String obtenerCodFormaPago(String nombre) throws PagoExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCodFormaPago(String) - start");
		}

		String codFormaPago = "";
		String sentenciaSQL = "select formadepago.codformadepago from formadepago where formadepago.nombre LIKE '%"+nombre+"%'";

			try {
				ResultSet rs = Conexiones.realizarConsulta(sentenciaSQL,true);
				try {
					if(rs.first()) {
						codFormaPago = rs.getString("codformadepago");
					} else {
						throw (new PagoExcepcion("Forma de Pago " + nombre + " inexistente en la Base de Datos"));
					}
				} finally {
					rs.close();
					rs = null;
				}
			} catch (SQLException ex) {
				logger.error("obtenerCodFormaPago(String)", ex);

				throw (new BaseDeDatosExcepcion("Fallo la conexion con la base de datos al obtener datos del producto", ex));
			}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCodFormaPago(String) - end");
		}
		return codFormaPago;
	}

	/**
	 * Obtiene el nombre de una forma de pago (Solo es una prueba para manejo de efectivo).
	 * @param codFP - Contiene el código de la forma de pago.
	 * @return String - String que representa el nombre de la forma de pago.
	 */
	public static String obtenerNombFormaPago(String codFP) throws PagoExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerNombFormaPago(String) - start");
		}

		String nombFormaPago = "";
		String sentenciaSQL = "select formadepago.nombre from formadepago where formadepago.codformadepago = "+codFP;

			try {
				ResultSet rs = Conexiones.realizarConsulta(sentenciaSQL,true);
				try {
					if(rs.first()) {
						nombFormaPago = rs.getString("nombre");
					} else {
						throw (new PagoExcepcion("Forma de Pago " + codFP + " inexistente en la Base de Datos"));
					}
				} finally {
					rs.close();
					rs = null;
				}
			} catch (SQLException ex) {
				logger.error("obtenerNombFormaPago(String)", ex);

				throw (new BaseDeDatosExcepcion("Fallo la conexion con la base de datos al obtener datos de la forma de pago", ex));
			}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerNombFormaPago(String) - end");
		}
		return nombFormaPago;
	}

	/**
	 * Actualiza el numero de transaccion de la caja en la Base de Datos.
	 * @param numRegistro Nuevo numero de transaccion.
	 * @throws BaseDeDatosExcepcion Si ocurre un error con la Base de Datos.
	 */
	static void actualizarTransaccionCaja(int numTrans) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarTransaccionCaja(int) - start");
		}

		String sentenciaSQL = "update caja set caja.numtransaccion = " + numTrans + " where caja.numtienda = "
						+ Sesion.getTienda().getNumero() + " and caja.numcaja = " + Sesion.getCaja().getNumero();
		Conexiones.realizarSentencia(sentenciaSQL, true);

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarTransaccionCaja(int) - end");
		}
	}

	/**
	 * Registra una transaccion de tipo Venta en la Base de Datos.
	 * @param venta Venta que se registrara en la base de datos.
	 * @return Vector Devuelve un vector de dos posiciones que contienen los número de servicio que se guardaron en la BD
	 * 				  Posicion 0 = Número de servicio asociado a la entrega a domiclio asociada a la transacción(en caso de que exista)
	 * 				  Posicion 1 = Número de servicio asociado al despacho asociado a la transacción (en caso de que exista)
	 * 				  Este Vector se devuelve para objetivos de impresion de reportes de los servicios
	 * @throws BaseDeDatosExcepcion Si ocurre un error con la inserción en la Base de Datos.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Integer> registrarTransaccion(Venta venta, String dirEntregaDom) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("registrarTransaccion(Venta, String) - start");
		}

		Vector<Integer> numerosServ = null;
		
		// Registramos la cabecera de venta
		Statement loteSentenciasCR  = null;
		try {
			loteSentenciasCR = Conexiones.crearSentencia(true);
			char cajaEnLinea = Sesion.isCajaEnLinea() == true ? Sesion.SI : Sesion.NO;		
			SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy-MM-dd");
			String fechaTransString = fechaTrans.format(venta.getFechaTrans());
			String codCliente = "";
			
			//********* Actualización: 09/02/2007. BECO: Cambios por razones de SENIAT
			//********* Se agrega la verificación de clientes de tipo empleados.
			//********* para estos casos no se imprimen como parte de la factura sino que se coloca
			//********* como nota al pie de página. Esto por requerimiento del SENIAT 
			/*if (venta.isImpEmpleadoPieDePag())
				codCliente = "null";
			else*/
			codCliente = (venta.getCliente().getCodCliente() != null && 
						(CR.meVenta.isClienteContribuyente(venta)|| venta.isImpEmpleadoPieDePag()))
						? "'" + venta.getCliente().getCodCliente() + "'"
						: "null";
			
			//Verificamos si existia un servicio de entrega a domicilio o un despacho fuera de caja para esta transacción
			int numimpnotaentrega = 0;
			int numimpnotadespacho = 0;
			if(venta.verificarTieneEntregaDom() || venta.verificarTieneClienteRetira()) {
				//numerosServ = BaseDeDatosServicio.registrarServicios(venta, dirEntregaDom, loteSentenciasCR);
				// Código temporal para
				numerosServ = new Vector<Integer>();
				numerosServ.addElement(new Integer(-1));
				numerosServ.addElement(new Integer(-1));
				if (venta.verificarTieneEntregaDom()) {
					numerosServ.setElementAt(new Integer(venta.getNumTransaccion()), 0);
					numimpnotaentrega = 1;
				}
				if (venta.verificarTieneClienteRetira()){
					numerosServ.setElementAt(new Integer(venta.getNumTransaccion()), 1);
					numimpnotadespacho = 1;
				}
			}
			String codAutorizanteV;
			if (venta.getCodAutorizante() == null)
				codAutorizanteV = "null";
			else
				codAutorizanteV = "'" + venta.getCodAutorizante() + "'";
			
			String sentenciaSQL = "insert into transaccion (numtienda,fecha,numcajainicia,numregcajainicia,numcajafinaliza,numtransaccion,tipotransaccion,"
				+ "horainicia,horafinaliza,codcliente,codcajero,codigofacturaespera,montobase,montoimpuesto,vueltocliente,montoremanente,lineasfacturacion,"
				+ "cajaenlinea,serialcaja,numcomprobantefiscal,duracionventa,checksum,estadotransaccion,regactualizado, codautorizante, numimpnotaentrega, numimpnotadespacho) "
				+ "values (" + venta.getCodTienda() + ", " 
				+ "'" + fechaTransString + "', "
				+ venta.getNumCajaInicia() + ", "
				+ venta.getNumRegCajaInicia() + ", "
				+ venta.getNumCajaFinaliza() + ", "
				+ venta.getNumTransaccion() + ", "
				+ "'" + venta.getTipoTransaccion() + "', "
				+ "'" + venta.getHoraInicia() + "', "
				+ "'" + venta.getHoraFin() + "', "
				+ codCliente + ", '"
				+ venta.getCodCajero() + "', "
				+ "null, " //Código de la factura en espera
				+ venta.getMontoBase() + ", "
				+ venta.getMontoImpuesto() + ", "
				+ venta.getMontoVuelto() + ", "
				+ venta.getMontoRemanente() + ", "
				+ venta.getLineasFacturacion() + ", "
				+ "'" + cajaEnLinea + "', "
				+ "'" + Sesion.getCaja().getSerial() + "', "
				+ venta.getNumComprobanteFiscal() + ", "
				+ venta.getDuracion() + ", "
				+ venta.getChecksum() + ", "
				+ "'" + venta.getEstadoTransaccion() + "', "
				+ "'" + Sesion.IMPRIMIENDO + "', " + codAutorizanteV +", " +
				+ numimpnotaentrega + ", "+ numimpnotadespacho +")";
				
			loteSentenciasCR.addBatch(sentenciaSQL);
			
			
			
			// Registramos los detalles de la venta
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
					+ "'" + fechaTransString + "', "
					+ venta.getNumCajaInicia() + ", "
					+ venta.getNumRegCajaInicia() + ", '"
					+ ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getProducto().getCodProducto() + "', "
					+ "'" + ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCondicionVenta() + "', "
					+ (i+1) + ", "
					+ venta.getNumCajaFinaliza() + ", "
					+ venta.getNumTransaccion() + ", "
					+ ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCantidad() + ", "
					+ codVendedor + ", "
					+ ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getProducto().getPrecioRegular() + ", "
					+ codSupervisor + ", "
					+ ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getPrecioFinal() + ", "
					+ MathUtil.cutDouble((((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getMontoImpuesto()), 5, true) + ", "
					+ ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getDctoEmpleado() + ", "
					+ "'" + ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCodTipoEntrega() + "', ";
				if (((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCodPromocion() < 0) {
					sentenciaSQL += "null, ";
				} else {
					sentenciaSQL += ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCodPromocion() + ", ";
				}
				sentenciaSQL += "'" + ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getTipoCaptura() + "', "
					+ "'" + Sesion.NO + "')";
				
				//Agregamos al Batch la sentencia a realizar
				loteSentenciasCR.addBatch(sentenciaSQL);
				
				//Inserto las nuevas condiciones
				(new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().agregarInsertTransaccionCondiciones(venta, loteSentenciasCR, fechaTransString, sentenciaSQL, i);
			
			}
			
			double mtoRecaudado = ManejoPagosFactory.getInstance().registrarPagos(loteSentenciasCR, venta);
		
			//INSERT DE LA NUEVA TABLA TRANSACCIONAFILIADOCRM Crm Wdiaz
			RegistroClienteFactory factory = new RegistroClienteFactory();
			RegistroCliente registro = factory.getInstance();
			registro.insertarTransaccionafiliadocrm(venta, loteSentenciasCR, fechaTransString);
			//FIN DEL INSERT
			
			//ACTUALIZACION:BECO 08-JUL-2008
			ActualizadorPrecios ap = (new ActualizadorPreciosFactory()).getActualizadorPreciosInstance();
			ap.agregarPromocionesALoteSentencias(venta, loteSentenciasCR, fechaTransString);
			
			//Ahora ejecutamos todas las sentencias de forma transaccional en la caja
			Conexiones.ejecutarLoteSentencias(loteSentenciasCR, true, true);
			Sesion.getCaja().incrementarMontoRecaudado(mtoRecaudado);
		} catch (SQLException e) {
			logger.error("registrarTransaccion(Venta, String)", e);

			throw (new BaseDeDatosExcepcion("Error en sentencia SQL al registrar la transaccion"));
		} catch (ConexionExcepcion e) {
			logger.error("registrarTransaccion(Venta, String)", e);

			throw (new BaseDeDatosExcepcion("Error de conexión con BD al registrar la transaccion"));
		} finally {
			if (loteSentenciasCR != null) {
				try {
					loteSentenciasCR.close();
				} catch (SQLException e1) {
					logger.error("registrarTransaccion(Venta, String)", e1);
				}
				loteSentenciasCR = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("registrarTransaccion(Venta, String) - end");
		}
		return numerosServ;	
	}

	/**
	 * Actualiza el estado de la transaccion en la base de datos.
	 * @param tda Tienda donde se realizo la transaccion.
	 * @param fecha Fecha de la transaccion.
	 * @param cajaInicia Numero de Caja donde fue iniciada la transaccion.
	 * @param regCajaInicia Numero de registro asignado a la transaccion.
	 * @param edo Nuevo estado a asignar.
	 * @throws BaseDeDatosExcepcion Si ocurre un error con la actualizacion del estado.
	 */
	public static void actualizarEdoTransaccion(int tda, Date fecha, int cajaInicia, int regCajaInicia,
												int numCompF, char edo, boolean local) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("actualizarEdoTransaccion(int, Date, int, int, char, boolean) - start");
		}
		SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy-MM-dd");
		String fechaTransString = fechaTrans.format(fecha);
		String sentenciaSQL = null;
		if (edo == Sesion.ABORTADA) {
			/*
			* Modificaciones referentes a la migración a MySQL 5.5 por jperez
			* Sólo se cambió 'delete frm pagodetransaccion' por 'delete from p'
			* Fecha: septiembre 2011
			*/
			sentenciaSQL = "delete from p " +
			"using pagodetransaccion p inner join transaccion t " +
			"on (p.numtienda = t.numtienda and p.fecha = t.fecha and " +
			"p.numcaja = t.numcajafinaliza and p.numtransaccion = " +
			"t.numtransaccion) where t.numtienda=" + tda
				+ " and t.fecha = '" + fechaTransString + "' and t.numcajainicia=" + cajaInicia 
				+ " and t.numregcajainicia=" + regCajaInicia;
			Conexiones.realizarSentencia(sentenciaSQL,local);
			try{
				/*
				* Modificaciones referentes a la migración a MySQL 5.5 por jperez
				* Sólo se cambió 'delete from regalosregistrados' por 'delete from rr'
				* Fecha: septiembre 2011
				*/
				sentenciaSQL = "delete from rr " +
					"using regalosregistrados rr inner join transaccion t " +
					"on (rr.numTienda = t.numtienda and rr.fechaTransaccion = t.fecha and " +
					"rr.numCaja = t.numcajafinaliza and rr.numTransaccion = " +
					"t.numtransaccion) where estadotransaccion='" +
					Sesion.IMPRIMIENDO+"'";
				Conexiones.realizarSentencia(sentenciaSQL, true);
				/*
				* Modificaciones referentes a la migración a MySQL 5.5 por jperez
				* Sólo se cambió 'delete from promocionregistrada' por 'delete from pr'
				* Fecha: septiembre 2011
				*/
				sentenciaSQL = "delete from pr " +
					"using promocionregistrada pr inner join transaccion t " +
					"on (pr.numTienda = t.numtienda and pr.fecha = t.fecha and " +
					"pr.numCaja = t.numcajafinaliza and pr.numtransacion = " +
					"t.numtransaccion) where estadotransaccion='" +
					Sesion.IMPRIMIENDO+"'";
				Conexiones.realizarSentencia(sentenciaSQL, true);
				sentenciaSQL = "update transaccionpremiada inner join transaccion " +
					"on (transaccionpremiada.numTienda = transaccion.numtienda and transaccionpremiada.fecha = transaccion.fecha and " +
					"transaccionpremiada.numCaja = transaccion.numcajafinaliza and transaccionpremiada.numTransaccion = " +
					"transaccion.numtransaccion) set premioPorEntregar=1 where transaccion.estadotransaccion='" +
					Sesion.IMPRIMIENDO+"'";
					Conexiones.realizarSentencia(sentenciaSQL, true);
					/*
					* Modificaciones referentes a la migración a MySQL 5.5 por jperez
					* Sólo se cambió 'delete from pagodonacion' por 'delete from pd'
					* Fecha: septiembre 2011
					*/
				sentenciaSQL = "delete from pd " +
					"using pagodonacion pd inner join donacionesregistradas dr " +
					"on (pd.numTienda = dr.numTienda and pd.fechaDonacion = dr.fechaDonacion and " +
					"pd.numCaja = dr.numCaja and pd.numTransaccion = " +
					"dr.numTransaccion and pd.codigoDonacion=dr.codigoDonacion and pd.codigoDonacionRegistrada=dr.codigo) inner join transaccion t " +
					"on (pd.numTienda = t.numtienda and pd.fechaDonacion = t.fecha and " +
					"pd.numCaja = t.numcajafinaliza and pd.numTransaccion = " +
					"t.numtransaccion) " +
					"where estadotransaccion='"+Sesion.IMPRIMIENDO+"'";
				Conexiones.realizarSentencia(sentenciaSQL, true);
				/*
				* Modificaciones referentes a la migración a MySQL 5.5 por jperez
				* Sólo se cambió 'delete from donacionesregistradas' por 'delete from dr'
				* Fecha: septiembre 2011
				*/
				sentenciaSQL = "delete from dr " +
					"using donacionesregistradas dr inner join transaccion t " +
					"on (dr.numTienda = t.numtienda and dr.fechaDonacion = t.fecha and " +
					"dr.numCaja = t.numcajafinaliza and dr.numTransaccion = " +
					"t.numtransaccion) where estadotransaccion='" +
					Sesion.IMPRIMIENDO+"'";
				Conexiones.realizarSentencia(sentenciaSQL, true);
				/*
				* Modificaciones referentes a la migración a MySQL 5.5 por jperez
				* Sólo se cambió 'delete from detalletransaccioncondicion' por 'delete from p'
				* Fecha: septiembre 2011
				*/
				sentenciaSQL = "delete from p " +
					"using detalletransaccioncondicion p inner join transaccion t " +
					"on (p.numtienda = t.numtienda and p.fecha = t.fecha and " +
					"p.numcajainicia = t.numcajafinaliza and p.numtransaccion = " +
					"t.numtransaccion) where t.estadotransaccion = '"+ 
					Sesion.IMPRIMIENDO + "'";
				Conexiones.realizarSentencia(sentenciaSQL, true);
			} catch(Exception e){
				e.printStackTrace();
				//No esta activo el modulo de promociones
			}
		}
		sentenciaSQL = "update transaccion set transaccion.numcomprobantefiscal = "+ numCompF;
		if (edo == Sesion.ABORTADA) {
			sentenciaSQL += ", transaccion.numtransaccion = null" ;
		}
		sentenciaSQL += ", transaccion.estadotransaccion = '" + edo + "', transaccion.regactualizado='N' where transaccion.numtienda=" + tda
				+ " and transaccion.fecha = '" + fechaTransString + "' and transaccion.numcajainicia=" + cajaInicia 
				+ " and transaccion.numregcajainicia=" + regCajaInicia;
		Conexiones.realizarSentencia(sentenciaSQL,local);

		if (logger.isDebugEnabled()) {
			logger
					.debug("actualizarEdoTransaccion(int, Date, int, int, char, boolean) - end");
		}
	}
	
	/**
	 * Coloca una factura en espera en el servidor central de la tienda
	 * @param venta Objeto venta a agregar en espera
	 * @param identificacion Codigo con que se colocara la factura en espera
	 * @throws BaseDeDatosExcepcion Si ocurre un error registrando la factura
	 */
	public static void colocarEnEspera(Venta venta, String identificacion) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("colocarEnEspera(Venta, String) - start");
		}

		CongeladorFacturaFactory factory = new CongeladorFacturaFactory();
		CongeladorFactura congelador = factory.getInstance();
		congelador.colocarEnEspera(venta, identificacion);

		if (logger.isDebugEnabled()) {
			logger.debug("colocarEnEspera(Venta, String) - end");
		}
	}

	/**
	 * Recupera una factura en espera
	 * @param codFactEspera Codigo de la factura en espera a recuperar
	 * @return Vector - Un vector que en la primera posicion contiene la cabecera de venta y en 
	 * 					la segunda el vector de detalle.
	 * @throws BaseDeDatosExcepcion Si ocurre un error recuperando la factura en espera
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Vector<?>> recuperarDeEspera(String codFactEspera) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("recuperarDeEspera(String) - start");
		}

		CongeladorFacturaFactory factory = new CongeladorFacturaFactory();
		CongeladorFactura congelador = factory.getInstance();
		Vector<Vector<?>> returnVector = congelador.recuperarDeEspera(codFactEspera);
		if (logger.isDebugEnabled()) {
			logger.debug("recuperarDeEspera(String) - end");
		}
		return returnVector;
	}

	/**
	 * Borra la factura en espera recuperada
	 * @param codFactEspera Codigo de la factura en espera a recuperar
	 * @return Vector - Un vector que en la primera posicion contiene la cabecera de venta y en 
	 * 					la segunda el vector de detalle.
	 * @throws BaseDeDatosExcepcion Si ocurre un error recuperando la factura en espera
	 */
	public static void borrarFacturaEspera(String codFactEspera, int cajaInicia, int tienda, int regCajaInicia, Date fecha) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("borrarFacturaEspera(String, int, int, int, Date) - start");
		}

		CongeladorFacturaFactory factory = new CongeladorFacturaFactory();
		CongeladorFactura congelador = factory.getInstance();
		congelador.borrarFacturaEnEspera(codFactEspera, cajaInicia, tienda, regCajaInicia, fecha);

		if (logger.isDebugEnabled()) {
			logger
					.debug("borrarFacturaEspera(String, int, int, int, Date) - end");
		}
	}
	
	/**
	 * Método recuperarComanda.
	 * 		Recupera las comandas electrónicas del día correspondientes al cliente indicado.
	 * @param identificador Codigo de la factura en espera a recuperar
	 * @return Vector - Un vector que en la primera posicion contiene la cabecera de venta y en 
	 * 					la segunda el vector de detalle.
 	 * @throws BaseDeDatosExcepcion Si ocurre un error recuperando la factura en espera
	 * @throws ConexionExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Vector<Object>> recuperarComanda(String identificador, boolean BDLocal) throws BaseDeDatosExcepcion, ProductoExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("recuperarComanda(String, boolean) - start");
		}

		Vector<Vector<Object>> comandas = new Vector<Vector<Object>>();
		ResultSet resultado = null;
		boolean existeVendedor = Sesion.isUtilizarVendedor();
		Producto xProducto = null;
		
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
		String hoy = formatoFecha.format(Sesion.getFechaSistema());

		String sentenciaSQL = "select * from servicio inner join detalleservicio on (servicio.numservicio = detalleservicio.numservicio) and (servicio.numtienda = detalleservicio.numtienda) and (servicio.codtiposervicio = detalleservicio.codtiposervicio) and (servicio.fecha =detalleservicio.fecha) where (servicio.codtiposervicio = '"+Sesion.COMANDA+"') and (servicio.numtienda = "+ Sesion.getTienda().getNumero() + ") and (servicio.fecha ='"+hoy+"') and (servicio.codcliente = '" + identificador + "' or servicio.codcliente like '%" + identificador + "' or servicio.codcliente like '%-" + Control.completarCodigo(identificador) + "' or servicio.numservicio = " + identificador + ")";
		try {
			resultado = Conexiones.realizarConsulta(sentenciaSQL, BDLocal);
			resultado.first();
			if (resultado.getRow() > 0){
				resultado.beforeFirst();
				while (resultado.next()) {
					Vector<Object> detalle = new Vector<Object>();
					detalle.addElement(resultado.getString("numservicio"));
					String codProducto = new String(resultado.getString("codproducto"));
					xProducto = BaseDeDatosVenta.obtenerProducto(codProducto, Sesion.getFechaSistema(), Sesion.getHoraSistema(), true);
					detalle.addElement(codProducto);
					detalle.addElement(xProducto.getDescripcionLarga());
					detalle.addElement(new Float(resultado.getFloat("cantidad")));
					detalle.addElement(resultado.getString("estadoservicio"));
					detalle.addElement(resultado.getString("estadoregistro"));
					if (existeVendedor)
						detalle.addElement(resultado.getString("codcajero"));
					comandas.addElement(detalle);
				}

				// Actualizamos la comanda recuperada
				try{
					sentenciaSQL = "update servicio set estadoservicio = '"+Sesion.FINALIZADA+"' where servicio.numtienda = "+ Sesion.getTienda().getNumero() + " and (servicio.codcliente = '" + identificador + "' or servicio.codcliente like '%" + identificador + "' or servicio.codcliente like '%-" + Control.completarCodigo(identificador) + "' or servicio.numservicio = " + identificador + ") and servicio.codtiposervicio = '"+Sesion.COMANDA+"' and servicio.fecha ='"+hoy+"'";
					Conexiones.realizarSentencia(sentenciaSQL, BDLocal);
				} catch (BaseDeDatosExcepcion e) {
					logger.error("recuperarComanda(String, boolean)", e);

					throw (new BaseDeDatosExcepcion("No se pudo actualizar el registro recuperado.", e));				
				}
			} else throw new BaseDeDatosExcepcion("No existen comandas para el identificador " +identificador);
		} catch (BaseDeDatosExcepcion e1) {
			logger.error("recuperarComanda(String, boolean)", e1);

			e1.setMensaje("Falla al recuperar comanda electrónica\n"+e1.getMensaje());
			throw  e1;
		} catch (SQLException e2) {
			logger.error("recuperarComanda(String, boolean)", e2);

			throw (new BaseDeDatosExcepcion("Error al recuperar comanda electrónica", e2));
		} finally {
			if (resultado != null) {
				try {
					resultado.close();
				} catch (SQLException e) {
					logger.error("recuperarComanda(String, boolean)", e);
				}
				resultado = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("recuperarComanda(String, boolean) - end");
		}
		return comandas;
	}

	/**
	 * Método actualizarDetalleComanda.
	 * 		Actualiza los productos de la comanda electrónica que fueron facturados el día correspondiente al cliente indicado.
	 * @param identificador Codigo de la factura en espera a recuperar
	 * @return Vector - Un vector que en la primera posicion contiene la cabecera de venta y en 
	 * 					la segunda el vector de detalle.
	 * @throws BaseDeDatosExcepcion Si ocurre un error recuperando la factura en espera
	 * @throws ConexionExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' e 'Iterator'
	* Fecha: agosto 2011
	*/
	public static void actualizarDetalleComanda(Vector<Vector<Object>> comandasFacturadas) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarDetalleComanda(Vector) - start");
		}

		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
		String hoy = formatoFecha.format(Sesion.getFechaSistema());
		String numServicio = new String();
		String codProducto = new String();
		float cantidad = 0;

		try {
			Iterator<Vector<Object>> ciclo = comandasFacturadas.iterator();
			/*
			* Modificación realizada durante la migración a Java6 por jperez
			* Sólo se parametrizó el tipo de dato de los ArrayList 
			*/
			ArrayList <ArrayList<Object>>comandas = new ArrayList<ArrayList<Object>>();
			//Se parametrizó detalle como 'Object' ya que almacena tanto String como Float
			ArrayList <Object>detalle = new ArrayList<Object>();
			Vector<Object> elemento = new Vector<Object>();
			while (ciclo.hasNext()){
				elemento = ciclo.next();
				numServicio = new String(elemento.elementAt(0).toString());
				codProducto = new String(Control.completarCodigo(elemento.elementAt(1).toString()));
				cantidad = Float.valueOf(elemento.elementAt(3).toString()).floatValue();
				detalle.add(numServicio);
				detalle.add(codProducto);
				detalle.add(new Float(cantidad));
				comandas.add(detalle);
				detalle = new ArrayList<Object>();
			}
			String sentenciaSQL = "update detalleservicio set estadoregistro = '"+Sesion.FINALIZADA+"' where (numtienda = "+ Sesion.getTienda().getNumero() + ") and (numservicio = ?) and (codtiposervicio = '"+Sesion.COMANDA+"') and (codproducto = ?) and (cantidad = ?) and (fecha ='"+hoy+"')";
			Conexiones.realizarLoteSentencias(sentenciaSQL, false, comandas, false);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("actualizarDetalleComanda(Vector)", e);

			throw (new BaseDeDatosExcepcion("No se pudo actualizar el detalle del servicio recuperado.", e));				
		}

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarDetalleComanda(Vector) - end");
		}
	}

	/**
	 * Método eliminarComandas.
	 * 		Eliminar las comandas electrónicas del día.
	 * @throws BaseDeDatosExcepcion Si ocurre un error recuperando la factura en espera
	 * @throws ConexionExcepcion
	 */
	public static void eliminarComandas() throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("eliminarComandas() - start");
		}

		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
		String hoy = formatoFecha.format(Sesion.getFechaSistema());
		String sentenciaSQL = "";

		try {
			//sentenciaSQL = "delete from comanda where numtienda = "+ Sesion.getTienda().getNumero() + " and identificador = '" + identificador + "'";
			sentenciaSQL = new String("delete from servicio where servicio.numtienda = "+ Sesion.getTienda().getNumero() + " and servicio.codtiposervicio = '"+Sesion.COMANDA+"' and servicio.fecha ='"+hoy+"'");
			Conexiones.realizarSentencia(sentenciaSQL,false);
		} catch (BaseDeDatosExcepcion e1) {
			logger.error("eliminarComandas()", e1);

			e1.setMensaje("Falla al eliminar comanda electrónica\n"+e1.getMensaje());
			throw  e1;
		}	

		if (logger.isDebugEnabled()) {
			logger.debug("eliminarComandas() - end");
		}
	}
	/**
	 * Permite recuperar una transacción desde base de datos, sin necesidad de conocer el tipo de la misma.
	 * Solo recupera el encabezado de las misma
	 * @param tda Tienda de la transaccion
	 * @param fechaT Fecha de la transaccion
	 * @param cajaF Caja de la transacción
	 * @param numTr Número de la transacción
	 * @param local Indica si debe ser obtenida de la base de datos local
	 * @return Vector con los componentes (encabezado) de la transacción
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Object> obtenerTransaccion(int tda, String fechaT, int cajaF, int numTr, boolean local) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("obtenerTransaccion(int, String, int, int, boolean) - start");
		}

		Vector<Object> result = new Vector<Object>();
		ResultSet rsCabecera = null;
		String sentenciaSQL = "select * from transaccion where transaccion.numtienda = " + tda
				+ " and transaccion.fecha = '" + fechaT + "' and transaccion.numcajafinaliza = "
				+ cajaF + " and transaccion.numtransaccion = "
				+ numTr + " and transaccion.estadotransaccion = '" + Sesion.FINALIZADA + "'";
		try {
			rsCabecera = Conexiones.realizarConsulta(sentenciaSQL, local);
			if (rsCabecera.first()) {
				result.add(new Integer(rsCabecera.getInt("numtienda")));
				result.add(rsCabecera.getDate("fecha"));
				result.add(new Integer(rsCabecera.getInt("numcajainicia")));
				result.add(new Integer(rsCabecera.getInt("numregcajainicia")));
				result.add(new Integer(rsCabecera.getInt("numcajafinaliza")));
				result.add(new Integer(rsCabecera.getInt("numtransaccion")));
				result.add(rsCabecera.getString("tipotransaccion"));
				result.add(rsCabecera.getTime("horainicia"));
				result.add(rsCabecera.getTime("horafinaliza"));
				result.add(rsCabecera.getString("codcliente"));
				result.add(rsCabecera.getString("codcajero"));
				result.add(new Double(rsCabecera.getDouble("montobase")));
				result.add(new Double(rsCabecera.getDouble("montoimpuesto")));
				result.add(new Double(rsCabecera.getDouble("vueltocliente")));
				result.add(new Double(rsCabecera.getDouble("montoremanente")));
				result.add(new Integer(rsCabecera.getInt("lineasfacturacion")));
				result.add(new Integer(rsCabecera.getInt("checksum")));
				result.add(rsCabecera.getString("estadotransaccion"));
			}
		} catch (SQLException e) {
			logger.error("obtenerTransaccion(int, String, int, int, boolean)",
					e);

			throw new BaseDeDatosExcepcion("Error al recuperar transacción "+ numTr +" en tienda "+ tda, e);
		} finally {
			if (rsCabecera != null) {
				try {
					rsCabecera.close();
				} catch (SQLException e1) {
					logger
							.error(
									"obtenerTransaccion(int, String, int, int, boolean)",
									e1);
				}
				rsCabecera = null;
			}
				
		}

		if (logger.isDebugEnabled()) {
			logger
					.debug("obtenerTransaccion(int, String, int, int, boolean) - end");
		}
		return result;
	}
	/**
	 * Método que recupera de la base de datos las transacciones de un cajero en una caja, \
	 * en una fecha dada
	 * @param tda
	 * @param codCajero
	 * @param caja
	 * @param fecha
	 * @return Vector contieniendo los números de transacciones registradas
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Integer> obtenerTransacciones(int tda, String codCajero, int caja, String fecha) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("obtenerTransacciones(int, String, int, String) - start");
		}

		Vector<Integer> result = new Vector<Integer>();
		ResultSet rsTrans;
		String sentenciaSQL = "select numtransaccion, horafinaliza "+
				"from transaccion where  transaccion.numtienda = "+tda +
				" and transaccion.fecha = '" + fecha + "' and transaccion.numcajafinaliza = " +  
				caja + " and codcajero = '" + codCajero + "' and transaccion.estadotransaccion = '"
				+ Sesion.FINALIZADA + "' order by horafinaliza asc";
		rsTrans = Conexiones.realizarConsulta(sentenciaSQL, true);
		try {
			rsTrans.beforeFirst();
			while (rsTrans.next()) {
				result.add(new Integer(rsTrans.getInt("numtransaccion")));
			}
		} catch (SQLException e) {
			logger.error("obtenerTransacciones(int, String, int, String)", e);

			throw new BaseDeDatosExcepcion("Error al recuperar transacciones de Tienda " + tda 
					+ " en caja " + caja, e); 
					
		} finally {
			try {
				rsTrans.close();
			} catch (SQLException e1) {
				logger.error("obtenerTransacciones(int, String, int, String)",
						e1);
			}
			rsTrans = null;
		}

		if (logger.isDebugEnabled()) {
			logger
					.debug("obtenerTransacciones(int, String, int, String) - end");
		}
		return result;
	}

	
	/**
	 * Obtiene los datos de una venta
	 * @param tda Tienda donde se realizo la venta
	 * @param caja numero de caja que realizo la venta
	 * @param numTransaccion Numero de factura
	 * @return Vector - Un vector que en la primera posicion contiene la cabecera de venta y en 
	 * 					la segunda el vector de detalles y en la tercera los pagos.
	 * @throws BaseDeDatosExcepcion Si ocurre un error recuperando la transaccion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Vector<?>> obtenerVenta(int tda, String fechaT, int caja, int numTransaccion, boolean local) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("obtenerVenta(int, String, int, int, boolean) - start");
		}

		Vector<Vector<?>> resultado = new Vector<Vector<?>>();
		ResultSet result = null;
		boolean servTienda = true;
		Date fecha;
		//TODO: Verificar rapidez de estas sentencias
		
		// Armamos los vectores de la factura a recuperar
		String sentenciaSQL = "select * from transaccion where transaccion.numtienda = " + tda + " and transaccion.fecha = '"
				+ fechaT + "' and transaccion.numcajafinaliza = " + caja + " and transaccion.numtransaccion = " + numTransaccion 
				+ " and transaccion.tipotransaccion = '" + Sesion.VENTA + "' and transaccion.estadotransaccion = '" + Sesion.FINALIZADA + "'";
		
		// Si hay que buscar la informacion local
		if (local) {
			result = Conexiones.realizarConsulta(sentenciaSQL,local);
		} else {
			if (Sesion.isCajaEnLinea()) {
				// Si no, buscamos en el servidor central de tienda o de la empresa
				try {
					result = Conexiones.realizarConsulta(sentenciaSQL,false);
				} catch (BaseDeDatosExcepcion e1) {
					logger.error(
							"obtenerVenta(int, String, int, int, boolean)", e1);

					result = null;
				} catch (ConexionExcepcion e1) {
					logger.error(
							"obtenerVenta(int, String, int, int, boolean)", e1);

					result = null;
				}
				try {
					if ((result==null)||(!result.first())) {
						if (result != null)
							result.close();
						servTienda = false;
						result = ConexionServCentral.realizarConsulta(sentenciaSQL);
					}
				} catch (Exception e) {
					logger.error(
							"obtenerVenta(int, String, int, int, boolean)", e);

					if (result != null)
						try {
							result.close();
						} catch (SQLException e3) {
							logger
									.error(
											"obtenerVenta(int, String, int, int, boolean)",
											e3);
						}
					throw new BaseDeDatosExcepcion("Error al recuperar transaccion numero " + numTransaccion + " de Tienda " + tda);
				}
			} else {
				throw (new ConexionExcepcion("Error al recuperar factura " + numTransaccion + "\nCaja Fuera de Línea"));
			}

		}
		//CARGAMOS LA CABECERA DE LA VENTA DE LA BD
		Vector<Object> cabecera = new Vector<Object>();
		try {
			if (result.first()) {
				cabecera.addElement(new Integer(tda));
				fecha = result.getDate("fecha");
				cabecera.addElement(fecha);
				cabecera.addElement(new Integer(result.getInt("numcajainicia")));
				cabecera.addElement(new Integer(result.getInt("numregcajainicia")));
				cabecera.addElement(new Integer(caja));
				cabecera.addElement(new Integer(numTransaccion));
				cabecera.addElement(result.getString("tipotransaccion"));
				cabecera.addElement(result.getTime("horainicia"));
				cabecera.addElement(result.getTime("horafinaliza"));
				cabecera.addElement(result.getString("codcliente"));
				cabecera.addElement(result.getString("codcajero"));
				cabecera.addElement(result.getString("serialcaja"));
				cabecera.addElement(new Double(result.getDouble("montobase")));
				cabecera.addElement(new Double(result.getDouble("montoimpuesto")));
				cabecera.addElement(new Double(result.getDouble("vueltocliente")));
				cabecera.addElement(new Double(result.getDouble("montoremanente")));
				cabecera.addElement(new Integer(result.getInt("lineasfacturacion")));
				cabecera.addElement(new Integer(result.getInt("checksum")));
				cabecera.addElement(result.getString("estadotransaccion"));
				cabecera.addElement(new Integer(result.getInt("numcomprobantefiscal")));
				cabecera.addElement(result.getString("codautorizante"));
			} else {
				throw (new BaseDeDatosExcepcion("No existe transaccion de Venta numero " + numTransaccion + " realizada por la caja " + caja + " en Tienda " + tda));
			}
		} catch (SQLException e2) {
			logger.error("obtenerVenta(int, String, int, int, boolean)", e2);

			throw (new BaseDeDatosExcepcion("Error al recuperar factura " + numTransaccion, e2));
		} finally {
			try {
				result.close();
			} catch (SQLException e) {
				logger.error("obtenerVenta(int, String, int, int, boolean)", e);
			}
			result = null;
		}
				
		//CARGAMOS CADA DETALLE DE LA VENTA DE LA BD
		Vector<Vector<Object>> detalles = new Vector<Vector<Object>>();
		sentenciaSQL = "select * from detalletransaccion where detalletransaccion.numtienda=" + tda + " and detalletransaccion.fecha = '" + fecha
			+ "' and detalletransaccion.numcajafinaliza=" + caja + " and detalletransaccion.numtransaccion=" + numTransaccion + " order by detalletransaccion.correlativoitem asc";

		// Si hay que buscar la informacion local
		if (local) {
			result = Conexiones.realizarConsulta(sentenciaSQL,local);
		} else {
			// Si no, buscamos en el servidor central de tienda o de la empresa
			if (servTienda) 
				result = Conexiones.realizarConsulta(sentenciaSQL,false);
			else
				result = ConexionServCentral.realizarConsulta(sentenciaSQL);
		}

		try {
			result.beforeFirst();
			while (result.next()) {
				Vector<Object> detalle = new Vector<Object>();
				detalle.addElement(result.getString("codproducto"));
				detalle.addElement(result.getString("codcondicionventa").trim());
				detalle.addElement(new Float(result.getFloat("cantidad")));
				detalle.addElement(result.getString("codvendedor"));
				detalle.addElement(result.getString("codsupervisor"));
				detalle.addElement(new Double(result.getDouble("preciofinal")));
				detalle.addElement(new Double(result.getDouble("montoimpuesto")));
				detalle.addElement(new Integer(result.getInt("codpromocion")));
				detalle.addElement(result.getString("codtipocaptura"));
				detalle.addElement(new Double(result.getDouble("desctoempleado")));
				detalle.addElement(result.getString("despacharproducto"));
				detalle.addElement(new Double(result.getDouble("precioregular")));
				detalles.addElement(detalle);
			}
		} catch (SQLException e2) {
			logger.error("obtenerVenta(int, String, int, int, boolean)", e2);

			throw (new BaseDeDatosExcepcion("Error al recuperar factura " + numTransaccion, e2));
		} finally {
			try {
				result.close();
			} catch (SQLException e) {
				logger.error("obtenerVenta(int, String, int, int, boolean)", e);
			}
			result = null;
		}
		
		//CARGAMOS LOS PAGOS DE LA VENTA DE LA BD
		Vector<Vector<Object>> pagos = new Vector<Vector<Object>>();
		sentenciaSQL = "select * from pagodetransaccion where pagodetransaccion.numtienda=" + tda + " and pagodetransaccion.fecha = '" + fecha
			+ "' and pagodetransaccion.numcaja=" + caja + " and pagodetransaccion.numtransaccion=" + numTransaccion + " order by pagodetransaccion.fecha";

		// Si hay que buscar la informacion local
		if (local) {
			result = Conexiones.realizarConsulta(sentenciaSQL,local);
		} else {
			// Si no, buscamos en el servidor central de tienda o de la empresa
			if (servTienda)
				result = Conexiones.realizarConsulta(sentenciaSQL,false);
			else
				result = ConexionServCentral.realizarConsulta(sentenciaSQL);
		}

		try {
			result.beforeFirst();
			while (result.next()) {
				Vector<Object> pago = new Vector<Object>();
				pago.addElement(new Integer(tda));
				pago.addElement(fecha);
				pago.addElement(new Integer(caja));
				pago.addElement(new Integer(numTransaccion));
				pago.addElement(result.getString("codformadepago"));
				pago.addElement(new Double(result.getDouble("monto")));
				pago.addElement(result.getString("numdocumento"));
				pago.addElement(result.getString("numcuenta"));
				pago.addElement(result.getString("numconformacion"));
				pago.addElement(new Integer(result.getInt("numreferencia")));
				pago.addElement(result.getString("cedtitular"));
				pago.addElement(result.getString("codbanco"));
				pago.addElement(result.getString("codautorizante"));
				pagos.addElement(pago);
			}
		} catch (SQLException e2) {
			logger.error("obtenerVenta(int, String, int, int, boolean)", e2);

			throw (new BaseDeDatosExcepcion("Error al recuperar factura " + numTransaccion, e2));
		} finally {
			try {
				result.close();
			} catch (SQLException e) {
				logger.error("obtenerVenta(int, String, int, int, boolean)", e);
			}
			result = null;
		}
		
		resultado.addElement(cabecera);
		resultado.addElement(detalles);
		resultado.addElement(pagos);

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerVenta(int, String, int, int, boolean) - end");
		}
		return resultado;

	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Vector<?>> obtenerDevolucion(int tienda, String fecha, int caja, int numTransaccion, boolean local) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("obtenerDevolucion(int, String, int, int, boolean) - start");
		}

		Vector<Vector<?>> result = new Vector<Vector<?>>();
		ResultSet result1 = null;
		String sentenciaSQL;
		sentenciaSQL = "select t.*, d.numtiendaventa, d.fechaventa, " + 
				"d.numcajaventa, d.numtransaccionvta from transaccion t inner join "+
				"devolucionventa d on (d.numtiendadevolucion = t.numtienda and "+
				"d.fechadevolucion = t.fecha and d.numcajadevolucion = t.numcajafinaliza "+
				"and d.numtransacciondev = t.numtransaccion) where t.numtienda = " + tienda
				+ " and t.fecha = '" + fecha + "' and t.numcajafinaliza = "
				+ caja + " and t.numtransaccion = "
				+ numTransaccion + " and t.tipotransaccion = '" + Sesion.DEVOLUCION
				+ "' and t.estadotransaccion = '" + Sesion.FINALIZADA + "'";	
		try {
			result1 = Conexiones.realizarConsulta(sentenciaSQL,local);
		} catch (BaseDeDatosExcepcion e1) {
			logger.error("obtenerDevolucion(int, String, int, int, boolean)",
					e1);
		} catch (ConexionExcepcion e1) {
			logger.error("obtenerDevolucion(int, String, int, int, boolean)",
					e1);
		}
		try {
			if ((result1==null)||(!result1.first())) {
				if (result1 != null)
					result1.close();
				result1 = ConexionServCentral.realizarConsulta(sentenciaSQL);
			}
		}  catch (Exception e) {
			logger
					.error("obtenerDevolucion(int, String, int, int, boolean)",
							e);

			if (result1 != null)
				try {
					result1.close();
				} catch (SQLException e3) {
					logger
							.error(
									"obtenerDevolucion(int, String, int, int, boolean)",
									e3);
				}
			throw new BaseDeDatosExcepcion("Error al recuperar transaccion numero " + numTransaccion + " de Tienda " + tienda);
		}

		try {
			result1.beforeFirst();
			while (result1.next()) {
				result = new Vector<Vector<?>>();
				Vector<Object> cabecera = new Vector<Object>();
				cabecera.addElement(new Integer(tienda));
				cabecera.addElement(result1.getDate("fecha"));
				cabecera.addElement(new Integer(result1.getInt("numcajainicia")));
				cabecera.addElement(new Integer(result1.getInt("numregcajainicia")));
				cabecera.addElement(new Integer(result1.getInt("numcajafinaliza")));
				cabecera.addElement(new Integer(result1.getInt("numtransaccion")));
				cabecera.addElement(result1.getString("tipotransaccion"));
				cabecera.addElement(result1.getTime("horainicia"));
				cabecera.addElement(result1.getTime("horafinaliza"));
				cabecera.addElement(result1.getString("codcliente"));
				cabecera.addElement(result1.getString("codcajero"));
				cabecera.addElement(null);
				cabecera.addElement(new Double(result1.getDouble("montobase")));
				cabecera.addElement(new Double(result1.getDouble("montoimpuesto")));
				cabecera.addElement(new Double(result1.getDouble("vueltocliente")));
				cabecera.addElement(new Double(result1.getDouble("montoremanente")));
				cabecera.addElement(new Integer(result1.getInt("lineasfacturacion")));
				cabecera.addElement(new Integer(result1.getInt("checksum")));
				cabecera.addElement(result1.getString("estadotransaccion"));
				cabecera.add(new Integer(result1.getInt("numtiendaventa")));
				cabecera.add(result1.getDate("fechaventa"));
				cabecera.add(new Integer(result1.getInt("numcajaventa")));
				cabecera.add(new Integer(result1.getInt("numtransaccionvta")));
				cabecera.add(result1.getString("codautorizante"));
						
				//Detalles de las devoluciones
				Vector<Vector<Object>> detalles = new Vector<Vector<Object>>();
				sentenciaSQL = "select * from detalletransaccion where detalletransaccion.numtienda=" + tienda
					+ " and detalletransaccion.fecha = '" + fecha + "' and detalletransaccion.numcajafinaliza=" 
					+ caja + " and detalletransaccion.numtransaccion=" 
					+ numTransaccion + " order by detalletransaccion.correlativoitem asc";

				ResultSet result2 = null;
				try {
					result2 = Conexiones.realizarConsulta(sentenciaSQL,local);
				} catch (BaseDeDatosExcepcion e1) {
					logger
							.error(
									"obtenerDevolucion(int, String, int, int, boolean)",
									e1);
				} catch (ConexionExcepcion e1) {
					logger
							.error(
									"obtenerDevolucion(int, String, int, int, boolean)",
									e1);
				}
				try {
					if ((result2==null)||(!result2.first())) {
						if (result2 != null)
							result2.close();
						result2 = ConexionServCentral.realizarConsulta(sentenciaSQL);
					}
				} catch (Exception e) {
					logger
							.error(
									"obtenerDevolucion(int, String, int, int, boolean)",
									e);

					if (result2 != null)
						result2.close();
					throw new BaseDeDatosExcepcion("Error al recuperar transaccion numero " + numTransaccion + " de Tienda " + tienda);
				}

				try {
					result2.beforeFirst();
					while (result2.next()) {
						Vector<Object> detalle = new Vector<Object>();
						detalle.addElement(result2.getString("codproducto"));
						detalle.addElement(result2.getString("codcondicionventa").trim());
						detalle.addElement(new Float(result2.getFloat("cantidad")));
						detalle.addElement(result2.getString("codvendedor"));
						detalle.addElement(result2.getString("codsupervisor"));
						detalle.addElement(new Double(result2.getDouble("preciofinal")));
						detalle.addElement(new Double(result2.getDouble("montoimpuesto")));
						detalle.addElement(new Integer(result2.getInt("codpromocion")));
						detalle.addElement(result2.getString("codtipocaptura"));
						detalles.addElement(detalle);
					}
				} catch (SQLException e2) {
					logger
							.error(
									"obtenerDevolucion(int, String, int, int, boolean)",
									e2);

					throw (new BaseDeDatosExcepcion("Error al recuperar factura " + numTransaccion + " para devolucion", e2));
				} finally {
					result2.close();
					result2 = null;
				}
				
				result.addElement(cabecera);
				result.addElement(detalles);
			}	
		} catch (SQLException e2) {
			logger.error("obtenerDevolucion(int, String, int, int, boolean)",
					e2);

			throw (new BaseDeDatosExcepcion("Error al recuperar factura " + numTransaccion + " para devolucion", e2));
		} finally {
			try {
				result1.close();
			} catch (SQLException e3) {
				logger
						.error(
								"obtenerDevolucion(int, String, int, int, boolean)",
								e3);
			}
			result1 = null;
		}
		
		if (logger.isDebugEnabled()) {
			logger
					.debug("obtenerDevolucion(int, String, int, int, boolean) - end");
		}
		return result;
	}
	/**
	 * Obtiene los datos de una anulación para la recuperación de la misma desde la BD
	 * @param tienda
	 * @param fecha
	 * @param caja
	 * @param numTransaccion
	 * @return Partes constituyentes de la anulacion, para construir el objeto Anulacion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Vector<?>> obtenerAnulacion(int tienda, String fecha, int caja, int numTransaccion, boolean local) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("obtenerAnulacion(int, String, int, int, boolean) - start");
		}

		Vector<Vector<?>> result = new Vector<Vector<?>>();
		Vector<Object> cabecera = new Vector<Object>();
		Vector<Vector<Object>> detalles = new Vector<Vector<Object>>();
		ResultSet rsCabecera = null, rsDetalles = null;
		String sentenciaSQL = "select t.*, d.numtiendaventa, d.fechaventa, " + 
				"d.numcajaventa, d.numtransaccionvta from transaccion t inner join "+
				"devolucionventa d on (d.numtiendadevolucion = t.numtienda and "+
				"d.fechadevolucion = t.fecha and d.numcajadevolucion = t.numcajafinaliza "+
				"and d.numtransacciondev = t.numtransaccion) where t.numtienda = " + tienda
				+ " and t.fecha = '" + fecha + "' and t.numcajafinaliza = "
				+ caja + " and t.numtransaccion = "
				+ numTransaccion + " and t.tipotransaccion = '" + Sesion.ANULACION
				+ "' and t.estadotransaccion = '" + Sesion.FINALIZADA + "'";
		try {
			rsCabecera = Conexiones.realizarConsulta(sentenciaSQL, local);
			if (rsCabecera.first()) {
				cabecera.add(new Integer(rsCabecera.getInt("numtienda")));
				cabecera.add(rsCabecera.getDate("fecha"));
				cabecera.add(new Integer(rsCabecera.getInt("numcajainicia")));
				cabecera.add(new Integer(rsCabecera.getInt("numregcajainicia")));
				cabecera.add(new Integer(rsCabecera.getInt("numcajafinaliza")));
				cabecera.add(new Integer(rsCabecera.getInt("numtransaccion")));
				cabecera.add(rsCabecera.getString("tipotransaccion"));
				cabecera.add(rsCabecera.getTime("horainicia"));
				cabecera.add(rsCabecera.getTime("horafinaliza"));
				cabecera.add(rsCabecera.getString("codcliente"));
				cabecera.add(rsCabecera.getString("codcajero"));
				cabecera.add(new Double(rsCabecera.getDouble("montobase")));
				cabecera.add(new Double(rsCabecera.getDouble("montoimpuesto")));
				cabecera.add(new Double(rsCabecera.getDouble("vueltocliente")));
				cabecera.add(new Double(rsCabecera.getDouble("montoremanente")));
				cabecera.add(new Integer(rsCabecera.getInt("lineasfacturacion")));
				cabecera.add(new Integer(rsCabecera.getInt("checksum")));
				cabecera.add(rsCabecera.getString("estadotransaccion"));
				cabecera.add(new Integer(rsCabecera.getInt("numtiendaventa")));
				cabecera.add(rsCabecera.getDate("fechaventa"));
				cabecera.add(new Integer(rsCabecera.getInt("numcajaventa")));
				cabecera.add(new Integer(rsCabecera.getInt("numtransaccionvta")));
				cabecera.add(rsCabecera.getString("codautorizante"));
			}
			sentenciaSQL = "select * from detalletransaccion where detalletransaccion.numtienda=" + tienda
				+ " and detalletransaccion.fecha = '" + fecha + "' and detalletransaccion.numcajafinaliza=" 
				+ caja + " and detalletransaccion.numtransaccion=" 
				+ numTransaccion + " order by detalletransaccion.correlativoitem asc";
			rsDetalles = Conexiones.realizarConsulta(sentenciaSQL, local);
			try {
				rsDetalles.beforeFirst();
				while (rsDetalles.next()) {
					Vector<Object> detalle = new Vector<Object>();
					detalle.addElement(rsDetalles.getString("codproducto"));
					detalle.addElement(rsDetalles.getString("codcondicionventa"));
					detalle.addElement(new Float(rsDetalles.getFloat("cantidad")));
					detalle.addElement(rsDetalles.getString("codvendedor"));
					detalle.addElement(rsDetalles.getString("codsupervisor"));
					detalle.addElement(new Double(rsDetalles.getDouble("preciofinal")));
					detalle.addElement(new Double(rsDetalles.getDouble("montoimpuesto")));
					detalle.addElement(new Integer(rsDetalles.getInt("codpromocion")));
					detalle.addElement(rsDetalles.getString("codtipocaptura"));
					detalles.addElement(detalle);
				}
			} catch (SQLException e2) {
				logger.error(
						"obtenerAnulacion(int, String, int, int, boolean)", e2);

				throw (new BaseDeDatosExcepcion("Error al recuperar factura " + numTransaccion + " para devolucion", e2));
			}
			result.add(cabecera);
			result.add(detalles);
		} catch (SQLException e) {
			logger.error("obtenerAnulacion(int, String, int, int, boolean)", e);

			throw new BaseDeDatosExcepcion("Error al recuperar anulacion " + numTransaccion + " de la tienda " + tienda, e);
		} finally {
			if (rsCabecera != null) {
				try {
					rsCabecera.close();
				} catch (SQLException e1) {
					logger.error(
							"obtenerAnulacion(int, String, int, int, boolean)",
							e1);
				}
				rsCabecera = null;
			}
			if (rsDetalles != null) {
				try {
					rsDetalles.close();
				} catch (SQLException e1) {
					logger.error(
							"obtenerAnulacion(int, String, int, int, boolean)",
							e1);
				}
				rsDetalles = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger
					.debug("obtenerAnulacion(int, String, int, int, boolean) - end");
		}
		return result;
	}
	

	/**
	 * @param tienda
	 * @param fecha
	 * @param cajaOriginal
	 * @param numTransaccion
	 * @return Vector
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Devolucion> cargarDevoluciones(int tienda, Date fecha, int cajaOriginal, int numTransaccion, Venta vtaOrig) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("cargarDevoluciones(int, Date, int, int, Venta) - start");
		}

		Vector<Devolucion> resultadoFinal = new Vector<Devolucion>();
		ResultSet resultPrimario = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		// Armamos los vectores de la factura a recuperar
		String sentenciaSQL = "select devolucionventa.numtiendadevolucion, devolucionventa.fechadevolucion, devolucionventa.numcajadevolucion, devolucionventa.numtransacciondev "
				+ "from devolucionventa where devolucionventa.numtiendaventa = " + tienda + " and devolucionventa.fechaventa = '" + df.format(fecha) 
				+ "' and devolucionventa.numcajaventa = " + cajaOriginal + " and devolucionventa.numtransaccionvta = " + numTransaccion
				+ " order by devolucionventa.fechadevolucion desc, devolucionventa.numtransacciondev desc";
		try {
			resultPrimario = Conexiones.realizarConsulta(sentenciaSQL,false);
		} catch (BaseDeDatosExcepcion e1) {
			logger.error("cargarDevoluciones(int, Date, int, int, Venta)", e1);
		} catch (ConexionExcepcion e1) {
			logger.error("cargarDevoluciones(int, Date, int, int, Venta)", e1);
		}
		try {
			if ((resultPrimario==null)||(!resultPrimario.first())) {
				if (resultPrimario != null)
					resultPrimario.close();
				resultPrimario = ConexionServCentral.realizarConsulta(sentenciaSQL);
			}
		} catch (Exception e) {
			logger.error("cargarDevoluciones(int, Date, int, int, Venta)", e);

			if (resultPrimario != null)
				try {
					resultPrimario.close();
				} catch (SQLException e3) {
					logger.error(
							"cargarDevoluciones(int, Date, int, int, Venta)",
							e3);
				}
			throw new BaseDeDatosExcepcion("Error al recuperar transaccion numero " + numTransaccion + " de Tienda " + tienda);
		}

		try {
			resultPrimario.beforeFirst();
			while (resultPrimario.next()) {
				try {
					resultadoFinal.addElement(new Devolucion(obtenerDevolucion(resultPrimario.getInt("numtiendadevolucion"), 
							df.format(resultPrimario.getDate("fechadevolucion")), resultPrimario.getInt("numcajadevolucion"), resultPrimario.getInt("numtransacciondev"), false), vtaOrig));
				} catch (SQLException e2) {
					logger.error(
							"cargarDevoluciones(int, Date, int, int, Venta)",
							e2);

					throw (new BaseDeDatosExcepcion("Error al recuperar factura " + numTransaccion + " para devolucion", e2));
				}
			}
		}
		catch (SQLException e2) {
			logger.error("cargarDevoluciones(int, Date, int, int, Venta)", e2);

			throw (new BaseDeDatosExcepcion("Error al recuperar factura " + numTransaccion + " para devolucion", e2));
		} finally {
			try {
				resultPrimario.close();
			} catch (SQLException e3) {
				logger.error("cargarDevoluciones(int, Date, int, int, Venta)",
						e3);
			}
			resultPrimario = null;
		}

		if (logger.isDebugEnabled()) {
			logger
					.debug("cargarDevoluciones(int, Date, int, int, Venta) - end");
		}
		return resultadoFinal;
	}

	public static void registrarTransaccion(Devolucion devolucion) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("registrarTransaccion(Devolucion) - start");
		}

		// Registramos la cabecera de venta
		Statement loteSentenciasCR = null;
		try {
			loteSentenciasCR = Conexiones.crearSentencia(true);
			char cajaEnLinea = Sesion.isCajaEnLinea() == true ? Sesion.SI : Sesion.NO;
			SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy-MM-dd");
			String fechaTransString = fechaTrans.format(devolucion.getFechaTrans());
			String codCliente = (devolucion.getCliente().getCodCliente() != null)
								? "'" + devolucion.getCliente().getCodCliente() + "'"
								: "null";
			String codAutorizante = (devolucion.getCodAutorizante() != null)
								? "'" + devolucion.getCodAutorizante() + "'"
								: "null";
			String sentenciaSQL = "insert into transaccion (numtienda,fecha,numcajainicia,numregcajainicia,numcajafinaliza,numtransaccion,tipotransaccion,"
				+ "horainicia,horafinaliza,codcliente,codcajero,codigofacturaespera,montobase,montoimpuesto,vueltocliente,montoremanente,lineasfacturacion,cajaenlinea,"
				+ "serialcaja,numcomprobantefiscal,checksum,estadotransaccion,regactualizado, codautorizante)"
				+ " values (" + devolucion.getCodTienda() + ", " 
				+ "'" + fechaTransString + "', "
				+ devolucion.getNumCajaInicia() + ", "
				+ devolucion.getNumRegCajaInicia() + ", "
				+ devolucion.getNumCajaFinaliza() + ", "
				+ devolucion.getNumTransaccion() + ", "
				+ "'" + devolucion.getTipoTransaccion() + "', "
				+ "'" + devolucion.getHoraInicia() + "', "
				+ "'" + devolucion.getHoraFin() + "', "
				+ codCliente + ", '"
				+ devolucion.getCodCajero() + "', "
				+ "null, " //Código de la factura en espera
				+ devolucion.getMontoBase() + ", "
				+ devolucion.getMontoImpuesto() + ", "
				+ devolucion.getMontoVuelto() + ", "
				+ devolucion.getMontoRemanente() + ", "
				+ devolucion.getLineasFacturacion() + ", "
				+ "'" + cajaEnLinea + "', "
				+ "'" + Sesion.getCaja().getSerial() + "', "
				+ devolucion.getNumComprobanteFiscal() + ", "
				+ devolucion.getChecksum() + ", "
				+ "'" + devolucion.getEstadoTransaccion() + "', "
				+ "'" + Sesion.NO + "', " + codAutorizante + ")";
			// Conexiones.realizarSentencia(sentenciaSQL,true);
			loteSentenciasCR.addBatch(sentenciaSQL);
			
			// Registramos los detalles de la venta
			for (int i=0; i<devolucion.getDetallesTransaccion().size(); i++) {
				String codVendedor = (((DetalleTransaccion)devolucion.getDetallesTransaccion().elementAt(i)).getCodVendedor() != null)
									? "'" + ((DetalleTransaccion)devolucion.getDetallesTransaccion().elementAt(i)).getCodVendedor() + "'"
									: "null";
				String codSupervisor = (((DetalleTransaccion)devolucion.getDetallesTransaccion().elementAt(i)).getCodSupervisor() != null)
									? "'" + ((DetalleTransaccion)devolucion.getDetallesTransaccion().elementAt(i)).getCodSupervisor() + "'"
									: "null";
				sentenciaSQL = "insert into detalletransaccion (numtienda,fecha,numcajainicia,numregcajainicia,codproducto,codcondicionventa,correlativoitem,numcajafinaliza,numtransaccion,cantidad,"
					+ "codvendedor,precioregular,codsupervisor,preciofinal,montoimpuesto,desctoempleado,despacharproducto,codpromocion,codtipocaptura,regactualizado)"
					+ " values (" + devolucion.getCodTienda() + ", " 
					+ "'" + fechaTransString + "', "
					+ devolucion.getNumCajaInicia() + ", "
					+ devolucion.getNumRegCajaInicia() + ", '"
					+ ((DetalleTransaccion)devolucion.getDetallesTransaccion().elementAt(i)).getProducto().getCodProducto() + "', "
					+ "'" + ((DetalleTransaccion)devolucion.getDetallesTransaccion().elementAt(i)).getCondicionVenta() + "', "
					+ (i+1) + ", "
					+ devolucion.getNumCajaFinaliza() + ", "
					+ devolucion.getNumTransaccion() + ", "
					+ ((DetalleTransaccion)devolucion.getDetallesTransaccion().elementAt(i)).getCantidad() + ", "
					+ codVendedor + ", "
					+ ((DetalleTransaccion)devolucion.getDetallesTransaccion().elementAt(i)).getProducto().getPrecioRegular() + ", "
					+ codSupervisor + ", "
					+ ((DetalleTransaccion)devolucion.getDetallesTransaccion().elementAt(i)).getPrecioFinal() + ", "
					+ MathUtil.cutDouble(((DetalleTransaccion)devolucion.getDetallesTransaccion().elementAt(i)).getMontoImpuesto(), 5, true) + ", "
					+ ((DetalleTransaccion)devolucion.getDetallesTransaccion().elementAt(i)).getDctoEmpleado() + ", "
					+ "'" + Sesion.NO + "', ";
				if (((DetalleTransaccion)devolucion.getDetallesTransaccion().elementAt(i)).getCodPromocion() < 0) {
					sentenciaSQL += "null, ";
				} else {
					sentenciaSQL += ((DetalleTransaccion)devolucion.getDetallesTransaccion().elementAt(i)).getCodPromocion() + ", ";
				}
				sentenciaSQL += "'" + ((DetalleTransaccion)devolucion.getDetallesTransaccion().elementAt(i)).getTipoCaptura() + "', "
					+ "'" + Sesion.NO + "')";
				//Conexiones.realizarSentencia(sentenciaSQL,true);
				loteSentenciasCR.addBatch(sentenciaSQL);
			}
			
			double mtoRecaudado = -devolucion.getMontoVuelto();
			// Registramos los pagos de la devolucion
			for (int i=0; i<devolucion.getPagos().size(); i++) {
				Pago pagoActual = (Pago)devolucion.getPagos().elementAt(i);
				String codBanco = (pagoActual.getCodBanco() != null)
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
				sentenciaSQL = "insert into pagodetransaccion (numtienda,fecha,numcaja,numtransaccion,codformadepago,monto,numdocumento,numcuenta,numconformacion,numreferencia,cedtitular,codbanco,correlativoitem,regactualizado) values (" 
						+ devolucion.getCodTienda() + ",'" + fechaTransString + "'," + devolucion.getNumCajaFinaliza() + ", "
						+ devolucion.getNumTransaccion() + ",'" + pagoActual.getFormaPago().getCodigo() + "'," 
						+ pagoActual.getMonto() + "," + numDocumento + "," + numCuenta + "," +  numConf + ","
						+ numReferencia + "," + cedTitular + "," + codBanco + "," + (i+1) + ",'" + Sesion.NO + "')";
				loteSentenciasCR.addBatch(sentenciaSQL);
				// Si el pago incrementa el monto recaudado de la caja
				if (pagoActual.getFormaPago().isIncrementaEntregaParcial()) {
					mtoRecaudado += pagoActual.getMonto();
				}
			}
			
			// Registramos la devolucion en la tabla devolucionventa
			sentenciaSQL = "insert into devolucionventa (numtiendadevolucion,fechadevolucion,numcajadevolucion,numtransacciondev,numtiendaventa,fechaventa,numcajaventa,numtransaccionvta,tipotransaccion,regactualizado) values (" 
						+ devolucion.getCodTienda() + ",'" + fechaTransString + "'," + devolucion.getNumCajaFinaliza() + ", "
						+ devolucion.getNumTransaccion() + ", ";
			fechaTransString = fechaTrans.format(devolucion.getVentaOriginal().getFechaTrans());
			sentenciaSQL += devolucion.getVentaOriginal().getCodTienda() + ",'" + fechaTransString + "'," 
						+ devolucion.getVentaOriginal().getNumCajaFinaliza() + ", " + devolucion.getVentaOriginal().getNumTransaccion()
						+ ",'" + devolucion.getTipoTransaccion() + "','" + Sesion.NO + "')";
			
			loteSentenciasCR.addBatch(sentenciaSQL);
			
			//Ahora ejecutamos todas las sentencias de forma transaccional en la CR
			Conexiones.ejecutarLoteSentencias(loteSentenciasCR, true, true);
			Sesion.getCaja().disminuirMontoRecaudado(mtoRecaudado);		
		} catch (SQLException e) {
			logger.error("registrarTransaccion(Devolucion)", e);

			throw (new BaseDeDatosExcepcion("Error al registrar factura de devolucion", e));
		} catch (ConexionExcepcion e) {
			logger.error("registrarTransaccion(Devolucion)", e);

			throw (new BaseDeDatosExcepcion("Error al registrar factura de devolucion", e));
		} finally {
			if (loteSentenciasCR != null) {
				try {
					loteSentenciasCR.close();
				} catch (SQLException e1) {
					logger.error("registrarTransaccion(Devolucion)", e1);
				}
				loteSentenciasCR = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("registrarTransaccion(Devolucion) - end");
		}
	}

	/**
	 * Verifica si exise una transaccion con ese identificador de tipo tipoTrans
	 * @param tda Tienda donde se realizo la venta
	 * @param caja numero de caja que realizo la venta
	 * @param numTransaccion Numero de factura
	 * @param tipoTrans Tipo de Transaccion
	 * @return boolean - Indica si se han realizado o no anulaciones.
	 * @throws BaseDeDatosExcepcion Si ocurre un error recuperando la transaccion
	 */
	public static boolean anuladaTransaccion (int tda, String fecha, int caja, int numTransaccion, char tipoTrans, boolean chequeosEnLinea) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("anuladaTransaccion(int, String, int, int, char, boolean) - start");
		}

		boolean resultado = false;
		int contadorResult = 0;
		ResultSet result = null;
		
		// Armamos los vectores de la factura a recuperar
		String sentenciaSQL = "select count(*) from devolucionventa where devolucionventa.numtiendaventa = " + tda + " and devolucionventa.fechaventa = '" + fecha
				+ "' and devolucionventa.numcajaventa = " + caja + " and devolucionventa.numtransaccionvta = " + numTransaccion + " and devolucionventa.tipotransaccion = '"
				+ Sesion.ANULACION + "'";

		try {
			result = Conexiones.realizarConsulta(sentenciaSQL,true);
			try {
				result.first();
				contadorResult += result.getInt(1);
			} catch (SQLException e2) {
				logger
						.error(
								"anuladaTransaccion(int, String, int, int, char, boolean)",
								e2);

				try {
					result.close();
				} catch (SQLException e) {
					logger
							.error(
									"anuladaTransaccion(int, String, int, int, char, boolean)",
									e);
				}
				result = null;
			}
		} catch (BaseDeDatosExcepcion e1) {
			logger.error(
					"anuladaTransaccion(int, String, int, int, char, boolean)",
					e1);

			result = null;
		} catch (ConexionExcepcion e1) {
			logger.error(
					"anuladaTransaccion(int, String, int, int, char, boolean)",
					e1);

			result = null;
		}

		if ((result==null)||(contadorResult==0)) {
			if(Sesion.isCajaEnLinea()){
				try {
					result = Conexiones.realizarConsulta(sentenciaSQL,false);
					try {
						result.first();
						contadorResult += result.getInt(1);
					} catch (SQLException e2) {
						logger
								.error(
										"anuladaTransaccion(int, String, int, int, char, boolean)",
										e2);

						try {
							result.close();
						} catch (SQLException e) {
							logger
									.error(
											"anuladaTransaccion(int, String, int, int, char, boolean)",
											e);
						}
						result = null;
					}
				} catch (BaseDeDatosExcepcion e1) {
					logger
							.error(
									"anuladaTransaccion(int, String, int, int, char, boolean)",
									e1);

					result = null;
				} catch (ConexionExcepcion e1) {
					logger
							.error(
									"anuladaTransaccion(int, String, int, int, char, boolean)",
									e1);

					result = null;
				}
			} else {
				if (chequeosEnLinea) {
					throw (new ConexionExcepcion("Error al recuperar factura " + numTransaccion + "\nCaja Fuera de Línea"));
				}
			}
		}
		
		if ((result==null)||(contadorResult==0)) {
			if(Sesion.isCajaEnLinea()){
				try {
					result = ConexionServCentral.realizarConsulta(sentenciaSQL);
					try {
						result.first();
						contadorResult += result.getInt(1);
					} catch (SQLException e2) {
						logger
								.error(
										"anuladaTransaccion(int, String, int, int, char, boolean)",
										e2);

						try {
							result.close();
						} catch (SQLException e) {
							logger
									.error(
											"anuladaTransaccion(int, String, int, int, char, boolean)",
											e);
						}
						result = null;
					}
				} catch (BaseDeDatosExcepcion e1) {
					logger
							.error(
									"anuladaTransaccion(int, String, int, int, char, boolean)",
									e1);

					result = null;
				} catch (ConexionExcepcion e1) {
					logger
							.error(
									"anuladaTransaccion(int, String, int, int, char, boolean)",
									e1);

					result = null;
				}
			} else {
				if (chequeosEnLinea) {
					throw (new ConexionExcepcion("Error al recuperar factura " + numTransaccion + "\nCaja Fuera de Línea"));
				}
			}
		}
		try {
			if(result != null)
				result.close();
		} catch (SQLException e) {
			logger.error(
					"anuladaTransaccion(int, String, int, int, char, boolean)",
					e);
		}
		result = null;
		resultado = (contadorResult > 0) ? true : false;

		if (logger.isDebugEnabled()) {
			logger
					.debug("anuladaTransaccion(int, String, int, int, char, boolean) - end");
		}
		return resultado;
	}
	
	/**
	 * Verifica si exise una transaccion con ese identificador de tipo tipoTrans
	 * @param tda Tienda donde se realizo la venta
	 * @param caja numero de caja que realizo la venta
	 * @param numTransaccion Numero de factura
	 * @param tipoTrans Tipo de Transaccion
	 * @return boolean - Indica si se han realizado o no anulaciones.
	 * @throws BaseDeDatosExcepcion Si ocurre un error recuperando la transaccion
	 */
	public static boolean tieneDevoluciones (int tda, String fecha, int caja, int numTransaccion, char tipoTrans, boolean chequeosEnLinea) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("tieneDevoluciones(int, String, int, int, char, boolean) - start");
		}

		boolean resultado = false;
		int contadorResult = 0;
		ResultSet result = null;
		
		// Armamos los vectores de la factura a recuperar
		String sentenciaSQL = "select count(*) from devolucionventa where devolucionventa.numtiendaventa = " + tda + " and devolucionventa.fechaventa = '" + fecha
				+ "' and devolucionventa.numcajaventa = " + caja + " and devolucionventa.numtransaccionvta = " + numTransaccion + " and devolucionventa.tipotransaccion = '"
				+ Sesion.DEVOLUCION + "'";

		try {
			result = Conexiones.realizarConsulta(sentenciaSQL,true);
			try {
				result.first();
				contadorResult += result.getInt(1);
			} catch (SQLException e2) {
				logger
						.error(
								"tieneDevoluciones(int, String, int, int, char, boolean)",
								e2);

				try {
					result.close();
				} catch (SQLException e) {
					logger
							.error(
									"tieneDevoluciones(int, String, int, int, char, boolean)",
									e);
				}
				result = null;
			}
		} catch (BaseDeDatosExcepcion e1) {
			logger.error(
					"tieneDevoluciones(int, String, int, int, char, boolean)",
					e1);

			result = null;
		} catch (ConexionExcepcion e1) {
			logger.error(
					"tieneDevoluciones(int, String, int, int, char, boolean)",
					e1);

			result = null;
		}

		if ((result==null)||(contadorResult==0)) {
			if(Sesion.isCajaEnLinea()) {
				try {
					if (result != null)
						try {
							result.close();
						} catch (SQLException e3) {
							logger
									.error(
											"tieneDevoluciones(int, String, int, int, char, boolean)",
											e3);
						}
					result = Conexiones.realizarConsulta(sentenciaSQL,false);
					try {
						result.first();
						contadorResult += result.getInt(1);
					} catch (SQLException e2) {
						logger
								.error(
										"tieneDevoluciones(int, String, int, int, char, boolean)",
										e2);

						try {
							result.close();
						} catch (SQLException e) {
							logger
									.error(
											"tieneDevoluciones(int, String, int, int, char, boolean)",
											e);
						}
						result = null;
					}
				} catch (BaseDeDatosExcepcion e1) {
					logger
							.error(
									"tieneDevoluciones(int, String, int, int, char, boolean)",
									e1);

					result = null;
				} catch (ConexionExcepcion e1) {
					logger
							.error(
									"tieneDevoluciones(int, String, int, int, char, boolean)",
									e1);

					result = null;
				}
			} else {
				if (chequeosEnLinea) {
					throw (new ConexionExcepcion("Error al recuperar factura " + numTransaccion + "\nCaja Fuera de Línea"));
				}
			}
		}
		
		if ((result==null)||(contadorResult==0)) {
			if(Sesion.isCajaEnLinea()) {
				try {
					if (result != null) {
						try {
							result.close();
						} catch (SQLException e) {
							logger
									.error(
											"tieneDevoluciones(int, String, int, int, char, boolean)",
											e);
						}
					}
					result = ConexionServCentral.realizarConsulta(sentenciaSQL);
					try {
						result.first();
						contadorResult += result.getInt(1);
					} catch (SQLException e2) {
						logger
								.error(
										"tieneDevoluciones(int, String, int, int, char, boolean)",
										e2);

						try {
							result.close();
						} catch (SQLException e) {
							logger
									.error(
											"tieneDevoluciones(int, String, int, int, char, boolean)",
											e);
						}
						result = null;
					}
				} catch (BaseDeDatosExcepcion e1) {
					logger
							.error(
									"tieneDevoluciones(int, String, int, int, char, boolean)",
									e1);

					result = null;
				} catch (ConexionExcepcion e1) {
					logger
							.error(
									"tieneDevoluciones(int, String, int, int, char, boolean)",
									e1);

					result = null;
				}
			} else {
				if (chequeosEnLinea) {
					throw (new ConexionExcepcion("Error al recuperar factura " + numTransaccion + "\nCaja Fuera de Línea"));
				}
			}
		}
		try {
			if(result != null) 	
				result.close();
		} catch (SQLException e) {
			logger.error(
					"tieneDevoluciones(int, String, int, int, char, boolean)",
					e);
		}
		result = null;
		resultado = (contadorResult>0) ? true : false;

		if (logger.isDebugEnabled()) {
			logger
					.debug("tieneDevoluciones(int, String, int, int, char, boolean) - end");
		}
		return resultado;
	}

	public static void registrarTransaccion(Anulacion anulacion) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("registrarTransaccion(Anulacion) - start");
		}
		Statement loteSentenciasCR = null;
		try {
		 loteSentenciasCR = Conexiones.crearSentencia(true);
			// Registramos la cabecera de venta
			char cajaEnLinea = Sesion.isCajaEnLinea() == true ? Sesion.SI : Sesion.NO;
			SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy-MM-dd");
			String fechaTransString = fechaTrans.format(anulacion.getFechaTrans());
			String codCliente = (anulacion.getCliente().getCodCliente() != null)
								? "'" + anulacion.getCliente().getCodCliente() + "'"
								: "null";
			String codAutorizante = (anulacion.getCodAutorizante() != null)
								? "'" + anulacion.getCodAutorizante() + "'"
								: "null";
			String sentenciaSQL = "insert into transaccion (numtienda,fecha,numcajainicia,numregcajainicia,numcajafinaliza,numtransaccion,tipotransaccion,"
				+ "horainicia,horafinaliza,codcliente,codcajero,codigofacturaespera,montobase,montoimpuesto,vueltocliente,montoremanente,lineasfacturacion,cajaenlinea,serialcaja,numcomprobantefiscal,"
				+ "checksum,estadotransaccion,regactualizado, codautorizante)"
				+ " values (" + anulacion.getCodTienda() + ", " 
				+ "'" + fechaTransString + "', "
				+ anulacion.getNumCajaInicia() + ", "
				+ anulacion.getNumRegCajaInicia() + ", "
				+ anulacion.getNumCajaFinaliza() + ", "
				+ anulacion.getNumTransaccion() + ", "
				+ "'" + anulacion.getTipoTransaccion() + "', "
				+ "'" + anulacion.getHoraInicia() + "', "
				+ "'" + anulacion.getHoraFin() + "', "
				+ codCliente + ", '"
				+ anulacion.getCodCajero() + "', "
				+ "null, " //Código de la factura en espera
				+ anulacion.getMontoBase() + ", "
				+ anulacion.getMontoImpuesto() + ", "
				+ anulacion.getVentaOriginal().getMontoVuelto() + ", "
				+ anulacion.getVentaOriginal().getMontoRemanente() + ", "
				+ anulacion.getLineasFacturacion() + ", "
				+ "'" + cajaEnLinea + "', "			
				+ "'" + Sesion.getCaja().getSerial() + "', "
				+ anulacion.getNumComprobanteFiscal() + ", "
				+ anulacion.getChecksum() + ", "
				+ "'" + anulacion.getEstadoTransaccion() + "', "
				+ "'" + Sesion.NO + "', "+codAutorizante+")";
			//Conexiones.realizarSentencia(sentenciaSQL,true);
			loteSentenciasCR.addBatch(sentenciaSQL);
						
			// Registramos los detalles de la venta
			for (int i=0; i<anulacion.getDetallesTransaccion().size(); i++) {
				String codVendedor = (((DetalleTransaccion)anulacion.getDetallesTransaccion().elementAt(i)).getCodVendedor() != null)
									? "'" + ((DetalleTransaccion)anulacion.getDetallesTransaccion().elementAt(i)).getCodVendedor() + "'"
									: "null";
				String codSupervisor = (((DetalleTransaccion)anulacion.getDetallesTransaccion().elementAt(i)).getCodSupervisor() != null)
									? "'" + ((DetalleTransaccion)anulacion.getDetallesTransaccion().elementAt(i)).getCodSupervisor() + "'"
									: "null";
				sentenciaSQL = "insert into detalletransaccion (numtienda,fecha,numcajainicia,numregcajainicia,codproducto,codcondicionventa,correlativoitem,numcajafinaliza,numtransaccion,cantidad,"
					+ "codvendedor,precioregular,codsupervisor,preciofinal,montoimpuesto,desctoempleado,despacharproducto,codpromocion,codtipocaptura,regactualizado)"
					+ " values (" + anulacion.getCodTienda() + ", " 
					+ "'" + fechaTransString + "', "
					+ anulacion.getNumCajaInicia() + ", "
					+ anulacion.getNumRegCajaInicia() + ", '"
					+ ((DetalleTransaccion)anulacion.getDetallesTransaccion().elementAt(i)).getProducto().getCodProducto() + "', "
					+ "'" + ((DetalleTransaccion)anulacion.getDetallesTransaccion().elementAt(i)).getCondicionVenta() + "', "
					+ (i+1) + ", "
					+ anulacion.getNumCajaFinaliza() + ", "
					+ anulacion.getNumTransaccion() + ", "
					+ ((DetalleTransaccion)anulacion.getDetallesTransaccion().elementAt(i)).getCantidad() + ", "
					+ codVendedor + ", "
					+ ((DetalleTransaccion)anulacion.getDetallesTransaccion().elementAt(i)).getProducto().getPrecioRegular() + ", "
					+ codSupervisor + ", "
					+ ((DetalleTransaccion)anulacion.getDetallesTransaccion().elementAt(i)).getPrecioFinal() + ", "
					+ MathUtil.cutDouble(((DetalleTransaccion)anulacion.getDetallesTransaccion().elementAt(i)).getMontoImpuesto(), 5, true) + ", "
					+ ((DetalleTransaccion)anulacion.getDetallesTransaccion().elementAt(i)).getDctoEmpleado() + ", "
					+ "'" + Sesion.ENTREGA_CAJA + "', ";
				if (((DetalleTransaccion)anulacion.getDetallesTransaccion().elementAt(i)).getCodPromocion() <= 0) {
					sentenciaSQL += "null, ";
				} else {
					sentenciaSQL += ((DetalleTransaccion)anulacion.getDetallesTransaccion().elementAt(i)).getCodPromocion() + ", ";
				}
				sentenciaSQL += "'" + ((DetalleTransaccion)anulacion.getDetallesTransaccion().elementAt(i)).getTipoCaptura() + "', "
					+ "'" + Sesion.NO + "')";
				//Conexiones.realizarSentencia(sentenciaSQL,true);
				loteSentenciasCR.addBatch(sentenciaSQL);
			}
			
			(new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().agregarDonacionesCanceladas(anulacion, loteSentenciasCR, fechaTransString);

			double mtoRecaudado = -anulacion.getVentaOriginal().getMontoVuelto();
			// Registramos los pagos de la venta
			for (int i=0; i<anulacion.getVentaOriginal().getPagos().size(); i++) {
				Pago pagoActual = (Pago)anulacion.getVentaOriginal().getPagos().elementAt(i);
				String codBanco = (pagoActual.getCodBanco() != null)
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
				sentenciaSQL = "insert into pagodetransaccion (numtienda,fecha,numcaja,numtransaccion,codformadepago,monto,numdocumento,numcuenta,numconformacion,numreferencia,cedtitular,codbanco,correlativoitem,regactualizado) values (" 
						+ anulacion.getCodTienda() + ",'" + fechaTransString + "'," + anulacion.getNumCajaFinaliza() + ", "
						+ anulacion.getNumTransaccion() + ",'" + pagoActual.getFormaPago().getCodigo() + "'," 
						+ ((Pago)anulacion.getVentaOriginal().getPagos().elementAt(i)).getMonto() + ","
						+ numDocumento + "," + numCuenta + "," +  numConf + "," + numReferencia + "," + cedTitular + "," + codBanco + "," + (i+1) + ",'" + Sesion.NO + "')";
				loteSentenciasCR.addBatch(sentenciaSQL);
				// Si el pago incrementa el monto recaudado de la caja
				if (pagoActual.getFormaPago().isIncrementaEntregaParcial()) {
					mtoRecaudado += pagoActual.getMonto();
				}
			}

			// Registramos la anulacion en la tabla devolucionventa para saber la union entre la ventaoriginal y la anulacion
			sentenciaSQL = "insert into devolucionventa (numtiendadevolucion,fechadevolucion,numcajadevolucion,numtransacciondev,numtiendaventa,fechaventa,numcajaventa,numtransaccionvta,tipotransaccion,regactualizado) values (" 
						+ anulacion.getCodTienda() + ",'" + fechaTransString + "'," + anulacion.getNumCajaFinaliza() + ", "
						+ anulacion.getNumTransaccion() + ", ";
			fechaTransString = fechaTrans.format(anulacion.getVentaOriginal().getFechaTrans());
			sentenciaSQL += anulacion.getVentaOriginal().getCodTienda() + ",'" + fechaTransString + "'," 
						+ anulacion.getVentaOriginal().getNumCajaFinaliza() + ", " + anulacion.getVentaOriginal().getNumTransaccion()
						+ ",'" + anulacion.getTipoTransaccion() + "','" + Sesion.NO + "')";
			
			loteSentenciasCR.addBatch(sentenciaSQL);

			//Ahora ejecutamos todas las sentencias de forma transaccional en la CR
			Conexiones.ejecutarLoteSentencias(loteSentenciasCR, true, true);
			Sesion.getCaja().disminuirMontoRecaudado(mtoRecaudado);
		} catch (SQLException e) {
			logger.error("registrarTransaccion(Anulacion)", e);

			throw (new BaseDeDatosExcepcion("Error al registrar factura de anulacion", e));
		} catch (ConexionExcepcion e) {
			logger.error("registrarTransaccion(Anulacion)", e);

			throw (new BaseDeDatosExcepcion("Error al registrar factura de anulacion", e));
		} finally {
			if (loteSentenciasCR != null) {
				try {
					loteSentenciasCR.close();
				} catch (SQLException e1) {
					logger.error("registrarTransaccion(Anulacion)", e1);
				}
				loteSentenciasCR = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("registrarTransaccion(Anulacion) - end");
		}
	}

	/**
	 * Actualiza el estado del Servicio
	 */
	public static void asignarVentaServicio(int tda, String tipoServ, int numServ, Date fechaServ, int numTransVta, Date fechaTransVta) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("asignarVentaServicio(int, String, int, Date, int, Date) - start");
		}

		SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy-MM-dd");
		String fechaTransString = fechaTrans.format(fechaServ);
		String sentenciaSQL = "update servicio set servicio.numtransaccionventa = " + numTransVta 
				+ ", servicio.fechatransaccionvnta = '" + fechaTrans.format(fechaTransVta)
				+ "', servicio.numcajaventa = " + Sesion.getCaja().getNumero()
				+ " where servicio.numtienda=" + tda
				+ " and servicio.fecha = '" + fechaTransString 
				+ "' and servicio.codtiposervicio='" + tipoServ
				+ "' and servicio.numservicio=" + numServ;

		while (!Sesion.isCajaEnLinea()) {
			logger.error("Caja Fuera de Linea al intentar asignar venta a servicio");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		Conexiones.realizarSentencia(sentenciaSQL,false);

		if (logger.isDebugEnabled()) {
			logger
					.debug("asignarVentaServicio(int, String, int, Date, int, Date) - end");
		}
	}
	
	/**
	 * Registra el cliente No Afiliado en la Base de Datos.
	 * @param cliente Cliente que se registrara en la tabla "Afiliado" en la base de datos con tipoAfiliado="R"(Registrado).
	 * @throws BaseDeDatosExcepcion Si ocurre un error con la insercion en la Base de Datos.
	 */
	public static void registrarClienteTemporal(Cliente cliente) throws BaseDeDatosExcepcion, ConexionExcepcion, AfiliadoUsrExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("registrarClienteTemporal(Cliente) - start");
		}

		// Registramos al cliente
		try {		
			SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
			String fechaReg = fecha.format(Sesion.getHoraSistema());
			char contactar = ' ';
			if(cliente.isContactar())
				contactar = Sesion.SI;
			else 
				contactar = Sesion.NO;
			Timestamp actualizacion = new Timestamp(Calendar.getInstance().getTime().getTime());				
		
		//Verificamos si ya existía este cliente temporal
		 String sentenciaSQL = "select * from afiliado where codafiliado = '" + cliente.getCodCliente() + "'";
		 ResultSet resultado = Conexiones.realizarConsulta(sentenciaSQL,true);
		 try {
			 if (!resultado.first()) {
				sentenciaSQL = "insert into afiliado (codafiliado,tipoafiliado,nombre,apellido,numtienda,codarea,numtelefono,fechaafiliacion,"
					+ "registrado, contactar, direccion, actualizacion) "
					+ "values ('" + cliente.getCodCliente().trim() + "', " 
					+ "'" + cliente.getTipoCliente() + "', "
					+ "'" + cliente.getNombre() + "', "
					+ "'" + cliente.getApellido()+ "', "
					+ Sesion.getNumTienda() + ", "
					+ "'" + cliente.getCodArea() + "', "
					+ "'" + cliente.getNumTelefono() + "', "
					+ "'" + fechaReg + "', "
					+ "'" + Sesion.CLIENTE_REGISTRADO + "', "
					+ "'" + contactar + "', "
					+ "'>>>>>', "
					+ "'" + actualizacion + "')";
					
				Conexiones.realizarSentencia(sentenciaSQL,true);
			 } else {
				char registrado = (char)(resultado.getString("registrado").toCharArray()[0]);
				//Verficamos si el cliente es registrado (temporal) o es un cliente afiliado
				//Si es un afiliado se lanza una excepción para indicar que no se puede hacer esta operación
				if ((registrado == Sesion.NO) && (CR.meVenta.getDevolucion() == null))
					throw (new AfiliadoUsrExcepcion("El cliente ya es un afiliado\nNo se puede registrar como cliente NO Afiliado"));
					
				sentenciaSQL = "update afiliado set " 
								+ "tipoafiliado = '" + cliente.getTipoCliente() + "', "
								+ "nombre = '" + cliente.getNombre() + "', "
								+ "apellido = '" + cliente.getApellido()+ "', "
								+ "codarea = '" + cliente.getCodArea() + "', "
								+ "numtelefono = '" + cliente.getNumTelefono() + "', "
								+ "registrado = '" + Sesion.CLIENTE_REGISTRADO + "', "
								+ "contactar = '" + contactar + "', "
								+ "actualizacion = '" + actualizacion + "'"
								+ " where codafiliado = '" + cliente.getCodCliente() + "'";
				Conexiones.realizarSentencia(sentenciaSQL,true);
			 }
		 } finally {
		 	resultado.close();
		 }
		} catch (AfiliadoUsrExcepcion e) {
			logger.error("registrarClienteTemporal(Cliente)", e);

			throw (e);
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
	 * Busca el cliente temporal en la Base de Datos en la tabla afiliado por tipo de cliente 'R' (R=registrado al momento).
	 * @param ci String que indica el código del cliente a buscar. debe ser su Cédula o su RIF
	 * @throws BaseDeDatosExcepcion Si ocurre un error con la busqueda en la Base de Datos.
	 */

	public static Cliente buscarClienteTemporal(String ci) throws BaseDeDatosExcepcion, ConexionExcepcion, AfiliadoUsrExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarClienteTemporal(String) - start");
		}
		Cliente ctemp = null;
		// Buscamos al cliente
		try {		
			//SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
			//String fechaReg = fecha.format(Sesion.getHoraSistema());
			
			
		//Verificamos si ya existía este cliente temporal
		//String sentenciaSQL = "select * from afiliado where codafiliado like '%" + ci + "'";
		
		//****** Fecha Actualización 21/02/2007
		//Cambio de BECO para agregar validaciones de RIF y CI para el SENIAT
		//Arreglo para que la búsqueda de clientes con query que utiliza SubString
		//*******
		 String sentenciaSQL = "select * from afiliado where substring(codafiliado, 3) = '" + ci.trim() + "'";
		 ResultSet resultado = Conexiones.realizarConsulta(sentenciaSQL,true);
		 try {
			 if (resultado.first()){
			 	char tipoCliente = (char)(resultado.getString("tipoafiliado").toCharArray()[0]);
			 	String nombre = resultado.getString("nombre");
			 	String apellido = resultado.getString("apellido");
			 	String codArea = resultado.getString("codarea");
			 	String numTelf = resultado.getString("numtelefono");
				char contactarC;
			 	try{
					contactarC =(char)(resultado.getString("contactar").toCharArray()[0]);
			 	}
			 	catch (Exception e)
			 	{ 
					contactarC = 'N';
			 	}
				char registrado;
			 	try{
					registrado = (char)(resultado.getString("registrado").toCharArray()[0]);
			 	}
				catch (Exception e)
				{ 
					registrado = 'N';
				}
				char edoCliente;
				try{
					 edoCliente = (char)(resultado.getString("estadoafiliado").toCharArray()[0]);
				}
				catch (Exception e)
				{ 
					edoCliente = ' ';
				}
			 	
				//Verficamos si el cliente es registrado (temporal) o es un cliente afiliado
				//Si es un afiliado se lanza una excepción para indicar que no se puede hacer esta operación
				if ((registrado == Sesion.NO)&&(!Sesion.getCaja().getEstado().equals("14")))
					throw (new AfiliadoUsrExcepcion("El cliente ya es un afiliado\nNo se puede registrar como cliente NO Afiliado"));
				
			 	boolean contactar;
			 	if(contactarC == Sesion.SI) 
			 		contactar = true;
			 	else 
			 		contactar = false;

				ctemp = new Cliente (nombre, apellido, ci, numTelf, codArea, tipoCliente, contactar, edoCliente);
			 } 
		 } finally {
		 	resultado.close();
		 	resultado = null;
		 }
		 
			if (logger.isDebugEnabled()) {
				logger.debug("buscarClienteTemporal(String) - end");
			}
		 return ctemp;
		} catch (AfiliadoUsrExcepcion e) {
			logger.error("buscarClienteTemporal(String)", e);
			if (CR.meVenta.getDevolucion() == null)
				throw (e);
			else return ctemp;
		} catch (ConexionExcepcion e) {
			logger.error("buscarClienteTemporal(String)", e);

			throw (new BaseDeDatosExcepcion("Error de conexión con BD al consultar cliente NO Afiliado"));
		} catch (BaseDeDatosExcepcion e) {
			logger.error("buscarClienteTemporal(String)", e);

			throw (new BaseDeDatosExcepcion("Error en ejecución del query al consultar cliente NO afiliado"));
		} catch (SQLException e) {
			logger.error("buscarClienteTemporal(String)", e);

			throw (new BaseDeDatosExcepcion("Error en sentencia SQL al consultar cliente NO afiliado"));
		}
	}
	
	/**
	 * Obtiene los datos de la última venta para reimpresion
	 * @param 
	 * @return Vector - Un vector que en la primera posicion contiene la cabecera de venta y en 
	 * 					la segunda el vector de detalles y en la tercera los pagos.
	 * @throws BaseDeDatosExcepcion Si ocurre un error recuperando la transaccion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Vector<?>> obtenerUltimaVenta() throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerUltimaVenta() - start");
		}

		Vector<Vector<?>> resultado = new Vector<Vector<?>>();
		ResultSet result = null;
		Date fecha;
		int numTransaccion;
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fechaSQL = df.format(Sesion.getFechaSistema());
		
		// Armamos los vectores de la factura a recuperar
		String sentenciaSQL = "select * from transaccion where transaccion.numtienda = " + Sesion.getTienda().getNumero() + " and transaccion.fecha = '"
							+ fechaSQL + "' and transaccion.numcajafinaliza = " + Sesion.getCaja().getNumero() + " and transaccion.tipotransaccion = '" + Sesion.VENTA 
							+ "' and transaccion.estadotransaccion = '" + Sesion.FINALIZADA + "' and transaccion.numtransaccion = " + Sesion.getCaja().getNumTransaccion();
		
		// Se busca la informacion en la BD local
		result = Conexiones.realizarConsulta(sentenciaSQL,true);

		//CARGAMOS LA CABECERA DE LA VENTA DE LA BD
		Vector<Object> cabecera = new Vector<Object>();
		try {
			if (result.first()) {
				cabecera.addElement(new Integer(result.getInt("numtienda")));
				fecha = result.getDate("fecha");
				cabecera.addElement(fecha);
				cabecera.addElement(new Integer(result.getInt("numcajainicia")));
				cabecera.addElement(new Integer(result.getInt("numregcajainicia")));
				cabecera.addElement(new Integer(result.getInt("numcajafinaliza")));
				numTransaccion = result.getInt("numtransaccion");
				cabecera.addElement(new Integer(numTransaccion));
				cabecera.addElement(result.getString("tipotransaccion"));
				cabecera.addElement(result.getTime("horainicia"));
				cabecera.addElement(result.getTime("horafinaliza"));
				cabecera.addElement(result.getString("codcliente"));
				cabecera.addElement(result.getString("codcajero"));
				cabecera.addElement(result.getString("serialcaja"));
				cabecera.addElement(new Double(result.getDouble("montobase")));
				cabecera.addElement(new Double(result.getDouble("montoimpuesto")));
				cabecera.addElement(new Double(result.getDouble("vueltocliente")));
				cabecera.addElement(new Double(result.getDouble("montoremanente")));
				cabecera.addElement(new Integer(result.getInt("lineasfacturacion")));
				cabecera.addElement(new Integer(result.getInt("checksum")));
				cabecera.addElement(result.getString("estadotransaccion"));
				cabecera.addElement(new Integer(result.getInt("numcomprobantefiscal")));
				cabecera.addElement(result.getString("codautorizante"));
			} else {
				throw (new BaseDeDatosExcepcion("Ultima Transaccion de tipo VENTA inexistente"));
			}
		} catch (SQLException e2) {
			logger.error("obtenerUltimaVenta()", e2);

			throw (new BaseDeDatosExcepcion("Error al recuperar ultima transaccion", e2));
		} finally {
			try {
				result.close();
			} catch (SQLException e) {
				logger.error("obtenerUltimaVenta()", e);
			}
			result = null;
		}
				
		//CARGAMOS CADA DETALLE DE LA VENTA DE LA BD
		Vector<Vector<Object>> detalles = new Vector<Vector<Object>>();
		sentenciaSQL = "select * from detalletransaccion where detalletransaccion.numtienda=" + Sesion.getTienda().getNumero() + " and detalletransaccion.fecha = '" + fechaSQL
			+ "' and detalletransaccion.numcajafinaliza=" + Sesion.getCaja().getNumero() + " and detalletransaccion.numtransaccion=" + numTransaccion + " order by detalletransaccion.correlativoitem asc";

		// Se busca en la BD local los datos de los detalles de la transacción
		result = Conexiones.realizarConsulta(sentenciaSQL,true);
			
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
				detalle.addElement(result.getString("despacharproducto"));
				detalle.addElement(new Double(result.getDouble("precioregular")));
				detalles.addElement(detalle);
			}
		} catch (SQLException e2) {
			logger.error("obtenerUltimaVenta()", e2);

			throw (new BaseDeDatosExcepcion("Error al recuperar detalles de la factura " + numTransaccion, e2));
		} finally {
			try {
				result.close();
			} catch (SQLException e) {
				logger.error("obtenerUltimaVenta()", e);
			}
			result = null;
		}
		
		//ACTUALIZACION CENTROBECO 19/12/2008: Módulo de promociones
		//Obteniendo las condiciones de venta de la venta
		Vector<Vector<CondicionVenta>> condicionesVenta = new Vector<Vector<CondicionVenta>>();
		sentenciaSQL = "select * from detalletransaccioncondicion dtc " +
				" where dtc.numtienda= " + ((Integer)cabecera.elementAt(0)).intValue() + " and " +
						" dtc.fecha = '" + ((Date)cabecera.elementAt(1)) + "' and " +
						" dtc.numcajainicia = "+((Integer)cabecera.elementAt(2)).intValue()+" and " +
						" dtc.numregcajainicia = "+ ((Integer)cabecera.elementAt(3)) +" order by dtc.correlativoitem asc";
		result = Conexiones.realizarConsulta(sentenciaSQL,true);

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

		
		//CARGAMOS LOS PAGOS DE LA VENTA DE LA BD
		Vector<Vector<Object>> pagos = new Vector<Vector<Object>>();
		sentenciaSQL = "select * from pagodetransaccion where pagodetransaccion.numtienda=" + Sesion.getTienda().getNumero() + " and pagodetransaccion.fecha = '" + fechaSQL
			+ "' and pagodetransaccion.numcaja=" + Sesion.getCaja().getNumero() + " and pagodetransaccion.numtransaccion=" + numTransaccion + " order by pagodetransaccion.fecha";

		// Se buscan en la BD local los pagos de la transacción
		result = Conexiones.realizarConsulta(sentenciaSQL,true);

		try {
			result.beforeFirst();
			while (result.next()) {
				Vector<Object> pago = new Vector<Object>();
				pago.addElement(new Integer(Sesion.getTienda().getNumero()));
				pago.addElement(fecha);
				pago.addElement(new Integer(Sesion.getCaja().getNumero()));
				pago.addElement(new Integer(numTransaccion));
				pago.addElement(result.getString("codformadepago"));
				pago.addElement(new Double(result.getDouble("monto")));
				pago.addElement(result.getString("numdocumento"));
				pago.addElement(result.getString("numcuenta"));
				pago.addElement(result.getString("numconformacion"));
				pago.addElement(new Integer(result.getInt("numreferencia")));
				pago.addElement(result.getString("cedtitular"));
				pago.addElement(result.getString("codbanco"));
				pago.addElement(result.getString("codautorizante"));
				pagos.addElement(pago);
			}
		} catch (SQLException e2) {
			logger.error("obtenerUltimaVenta()", e2);

			throw (new BaseDeDatosExcepcion("Error al recuperar pagos de la factura " + numTransaccion, e2));
		} finally {
			try {
				result.close();
			} catch (SQLException e) {
				logger.error("obtenerUltimaVenta()", e);
			}
			result = null;
		}
		
		//MODIFICACION CENTROBECO: Caso agregado por promociones
		Vector<DonacionRegistrada> donacionesRegistradas = (new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().consultarDonacionesPorVenta(Sesion.getTienda().getNumero(), fechaSQL, Sesion.getCaja().getNumero(), numTransaccion, true);
		Vector<PromocionRegistrada> promocionesRegistradas = (new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().consultarPromocionesPorVenta(Sesion.getTienda().getNumero(), fechaSQL, Sesion.getCaja().getNumero(), numTransaccion, true);
		Vector<RegaloRegistrado> regalosRegistrados = (new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().consultarRegalosPorVenta(Sesion.getTienda().getNumero(), fechaSQL, Sesion.getCaja().getNumero(), numTransaccion, true);
		
		resultado.addElement(cabecera);
		resultado.addElement(detalles);
		resultado.addElement(pagos);
		resultado.addElement(condicionesVenta);
		resultado.addElement(donacionesRegistradas);
		resultado.addElement(promocionesRegistradas);
		resultado.addElement(regalosRegistrados);

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerUltimaVenta() - end");
		}
		return resultado;
	}
	
	/**
	 * Actualiza el numero de comprobante fiscal de la venta indicada en la Base de Datos.
	 * @param numComprobanteFiscal Nuevo numero de comrpobante fiscal de la transacción.
	 * @param vta Venta a la que se le va actualizar el número de comprobante Fiscal
	 * @throws BaseDeDatosExcepcion Si ocurre un error con la Base de Datos.
	 */
	public static void actualizarNumComprobanteFiscal(int numComprobanteFiscal, Venta vta) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarNumComprobanteFiscal(int, Venta) - start");
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fechaTransSQL = df.format(vta.getFechaTrans());
		String sentenciaSQL = "update transaccion set transaccion.numcomprobantefiscal = " + numComprobanteFiscal 
						+ " where transaccion.numtienda = "+ vta.getCodTienda() 
						+ " and transaccion.fecha = '" + fechaTransSQL + "'"
						+ " and transaccion.numcajainicia=" + vta.getNumCajaInicia() 
						+ " and transaccion.numregcajainicia=" + vta.getNumRegCajaInicia();
		
		Conexiones.realizarSentencia(sentenciaSQL, true);

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarNumComprobanteFiscal(int, Venta) - end");
		}
	}
	/**
	 * Obtiene el vector de detalles de transacción ordenado primero por tipo de entrega y luego por código de producto
	 * @param vta Venta a la que pertenece el vector de detalles que se obtendrá
	 * @throws BaseDeDatosExcepcion Si ocurre un error con la Base de Datos.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Integer> obtenerDetallesOrdenados(Transaccion vta) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerDetallesOrdenados(Transaccion) - start");
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fechaTransSQL = df.format(vta.getFechaTrans());
		
		Vector<Integer> detallesOrdenados = new Vector<Integer>();
		ResultSet result = null;
		String sentenciaSQL = "select correlativoitem from detalletransaccion where detalletransaccion.numtienda=" + vta.getCodTienda() + " and detalletransaccion.fecha = '" + fechaTransSQL
			+ "' and detalletransaccion.numcajafinaliza=" + vta.getNumCajaFinaliza() + " and detalletransaccion.numtransaccion=" + vta.getNumTransaccion() + " order by detalletransaccion.despacharproducto, detalletransaccion.codproducto asc";

		result = Conexiones.realizarConsulta(sentenciaSQL,true);
		try {
			result.beforeFirst();
			while (result.next()) {
				detallesOrdenados.addElement(new Integer(result.getInt("correlativoitem")));
			}
		} catch (SQLException e2) {
			logger.error("obtenerDetallesOrdenados(Transaccion)", e2);

			throw (new BaseDeDatosExcepcion("Error al recuperar detalles de transaccion para ordenarlos", e2));
		} finally {
			try {
				result.close();
			} catch (SQLException e) {
				logger.error("obtenerDetallesOrdenados(Transaccion)", e);
			}
			result = null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerDetallesOrdenados(Transaccion) - end");
		}
		return detallesOrdenados;
	}
	
	/**
	 * Método getUltimaTransaccion
	 * 		Selecciona las fechas ordenada descendentemente de los registros de transacciones.
	 * @param local - Indicador de la conexión de BD a utilizar: Servidor o Local. 
	 * @param noSincronizadas - Indicador para seleccionar todas las transacciones o sólo las
	 * no sincronizadas.
	 * @return ResultSet - Retorna los registros devueltos por la sentencia Sql efectuada. 
	 * @throws BaseDeDatosExcepcion
	 */
	public static java.sql.Date getUltimaTransaccion(boolean local, boolean noSincronizadas) throws BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("getUltimaTransaccion(boolean, boolean) - start");
		}

		ResultSet resultado = null;
		String sentenciaSql = "";
		boolean actualizable;
		java.sql.Date ultimaFecha = null;
		
		if (noSincronizadas){
			sentenciaSql = new String("select transaccion.fecha from transaccion where (transaccion.regactualizado = 'N') and (transaccion.estadotransaccion = '" + Sesion.FINALIZADA + "') and fecha >= '"+Sesion.getCaja().getFechaUltRepZ()+"' order by fecha desc");
			actualizable = false; 
		}
		else { 
			sentenciaSql = new String("select transaccion.fecha from transaccion where  (transaccion.estadotransaccion = '"+ Sesion.FINALIZADA +"') and fecha >= '"+Sesion.getCaja().getFechaUltRepZ()+"' order by fecha desc");
			actualizable = true;
		} 

		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, local, actualizable);
			resultado.first();
			if(resultado.getRow() > 0)
				ultimaFecha = resultado.getDate(1);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("getUltimaTransaccion(boolean, boolean)", e);		
		} catch (ConexionExcepcion e) {
			logger.error("getUltimaTransaccion(boolean, boolean)", e);		
		} catch (SQLException e) {
			logger.error("getUltimaTransaccion(boolean, boolean)", e);		
		} finally {
			if (resultado != null) {
				try {
					resultado.close();
				} catch (SQLException e1) {
					logger.error("getUltimaTransaccion(boolean, boolean)", e1);
				}
				resultado = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getUltimaTransaccion(boolean, boolean) - end");
		}
		return ultimaFecha;
	}
	
	public static void rollbackTransPendientesXImprimir() {
		if (logger.isDebugEnabled()) {
			logger.debug("rollbackTransPendientesXImprimir() - start");
		}

		try {
			/*
			* Modificaciones referentes a la migración a MySQL 5.5 por jperez
			* Sólo se cambió 'delete from pagodetransaccion' por 'delete from p'
			* Fecha: septiembre 2011
			*/
			String sentenciaSql = "delete from p " +
				"using pagodetransaccion p inner join transaccion t " +
				"on (p.numtienda = t.numtienda and p.fecha = t.fecha and " +
				"p.numcaja = t.numcajafinaliza and p.numtransaccion = " +
				"t.numtransaccion) where t.estadotransaccion = '"+ 
				Sesion.IMPRIMIENDO + "'";
			Conexiones.realizarSentencia(sentenciaSql, true);
			/*
			* Modificaciones referentes a la migración a MySQL 5.5 por jperez
			* Sólo se cambió 'delete from regalosregistrados' por 'delete from rr'
			* Fecha: septiembre 2011
			*/
			try{
				sentenciaSql = "delete from rr " +
					"using regalosregistrados rr inner join transaccion t " +
					"on (rr.numTienda = t.numtienda and rr.fechaTransaccion = t.fecha and " +
					"rr.numCaja = t.numcajafinaliza and rr.numTransaccion = " +
					"t.numtransaccion) where estadotransaccion='" +
					Sesion.IMPRIMIENDO+"'";
				Conexiones.realizarSentencia(sentenciaSql, true);
				/*
				* Modificaciones referentes a la migración a MySQL 5.5 por jperez
				* Sólo se cambió 'delete from promocionregistrada' por 'delete from pr'
				* Fecha: septiembre 2011
				*/
				sentenciaSql = "delete from pr " +
					"using promocionregistrada pr inner join transaccion t " +
					"on (pr.numTienda = t.numtienda and pr.fecha = t.fecha and " +
					"pr.numCaja = t.numcajafinaliza and pr.numtransacion = " +
					"t.numtransaccion) where estadotransaccion='" +
					Sesion.IMPRIMIENDO+"'";
				Conexiones.realizarSentencia(sentenciaSql, true);
				sentenciaSql = "update transaccionpremiada inner join transaccion " +
					"on (transaccionpremiada.numTienda = transaccion.numtienda and transaccionpremiada.fecha = transaccion.fecha and " +
					"transaccionpremiada.numCaja = transaccion.numcajafinaliza and transaccionpremiada.numTransaccion = " +
					"transaccion.numtransaccion) set premioPorEntregar=1 where transaccion.estadotransaccion='" +
					Sesion.IMPRIMIENDO+"'";
					Conexiones.realizarSentencia(sentenciaSql, true);
					/*
					* Modificaciones referentes a la migración a MySQL 5.5 por jperez
					* Sólo se cambió 'delete from pagodonacion' por 'delete from pd'
					* Fecha: septiembre 2011
					*/
				sentenciaSql = "delete from pd " +
					"using pagodonacion pd inner join donacionesregistradas dr " +
					"on (pd.numTienda = dr.numTienda and pd.fechaDonacion = dr.fechaDonacion and " +
					"pd.numCaja = dr.numCaja and pd.numTransaccion = " +
					"dr.numTransaccion and pd.codigoDonacion=dr.codigoDonacion and pd.codigoDonacionRegistrada=dr.codigo) inner join transaccion t " +
					"on (pd.numTienda = t.numtienda and pd.fechaDonacion = t.fecha and " +
					"pd.numCaja = t.numcajafinaliza and pd.numTransaccion = " +
					"t.numtransaccion) " +
					"where estadotransaccion='"+Sesion.IMPRIMIENDO+"'";
				Conexiones.realizarSentencia(sentenciaSql, true);
				/*
				* Modificaciones referentes a la migración a MySQL 5.5 por jperez
				* Sólo se cambió 'delete from donacionesregistradas' por 'delete from dr'
				* Fecha: septiembre 2011
				*/
				sentenciaSql = "delete from dr " +
					"using donacionesregistradas dr inner join transaccion t " +
					"on (dr.numTienda = t.numtienda and dr.fechaDonacion = t.fecha and " +
					"dr.numCaja = t.numcajafinaliza and dr.numTransaccion = " +
					"t.numtransaccion) where estadotransaccion='" +
					Sesion.IMPRIMIENDO+"'";
				Conexiones.realizarSentencia(sentenciaSql, true);
				/*
				* Modificaciones referentes a la migración a MySQL 5.5 por jperez
				* Sólo se cambió 'delete from detalletransaccioncondicion' por 'delete from p'
				* Fecha: septiembre 2011
				*/
				sentenciaSql = "delete from  p " +
					"using detalletransaccioncondicion p inner join transaccion t " +
					"on (p.numtienda = t.numtienda and p.fecha = t.fecha and " +
					"p.numcajainicia = t.numcajafinaliza and p.numtransaccion = " +
					"t.numtransaccion) where t.estadotransaccion = '"+ 
					Sesion.IMPRIMIENDO + "'";
				Conexiones.realizarSentencia(sentenciaSql, true);
				/*
				* Modificaciones referentes a la migración a MySQL 5.5 por jperez
				* Sólo se cambió 'delete from transaccionafiliadocrm' por 'delete from ta'
				* Fecha: septiembre 2011
				*/
				// Se Elimina registro transaccionafiliadocrm si la transaccion da cualquier error.
				sentenciaSql = "DELETE FROM ta " +
						" USING transaccionafiliadocrm ta INNER JOIN transaccion t " +
						"ON (ta.numtienda = t.numtienda AND ta.fechatransaccion = t.fecha AND " +
						"ta.numcajafinaliza = t.numcajafinaliza AND ta.numtransaccion = t.numtransaccion) " +
						"WHERE t.estadotransaccion='"+Sesion.IMPRIMIENDO+"'";
			    Conexiones.realizarSentencia(sentenciaSql, true);
			//fin
				
			} catch(Exception e){
				e.printStackTrace();
				//No esta activo el modulo de promociones
			}
			/*
			* Modificaciones referentes a la migración a MySQL 5.5 por jperez
			* Sólo se cambió 'delete from devolucionventa' por 'delete from d'
			* Fecha: septiembre 2011
			*/
			sentenciaSql = "delete from d " +
				"using devolucionventa d inner join transaccion t " +
				"on (d.numtiendadevolucion = t.numtienda and d.fechadevolucion = t.fecha and " +
				"d.numcajadevolucion = t.numcajafinaliza and d.numtransacciondev = " +
				"t.numtransaccion) where t.estadotransaccion = '"+ 
				Sesion.IMPRIMIENDO + "'";
			Conexiones.realizarSentencia(sentenciaSql, true);
			sentenciaSql = "update transaccion set estadotransaccion = '" + 
				Sesion.ABORTADA +"', numtransaccion = null where estadotransaccion = '" +
				Sesion.IMPRIMIENDO +"'";
			Conexiones.realizarSentencia(sentenciaSql, true);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("rollbackTransPendientesXImprimir()", e);
		} catch (ConexionExcepcion e) {
			logger.error("rollbackTransPendientesXImprimir()", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("rollbackTransPendientesXImprimir() - end");
		}
	}
	
	public static void rollbackAnulacionDevolucion(int numtiendadev, Date fecha, int numcajadev, int numtransaccion) {
		if (logger.isDebugEnabled()) {
			logger
					.debug("rollbackAnulacionDevolucion(int, Date, int, int) - start");
		}

		try {
			SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy-MM-dd");
			String fechaTransString = fechaTrans.format(fecha);
			String sentenciaSql = "delete from devolucionventa where numtiendadevolucion = " + 
				numtiendadev + " and fechadevolucion = '"+ fechaTransString +
				"' and numcajadevolucion = " + numcajadev+ " and numtransacciondev = " + 
				numtransaccion;
			Conexiones.realizarSentencia(sentenciaSql, true);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("rollbackAnulacionDevolucion(int, Date, int, int)", e);
		} catch (ConexionExcepcion e) {
			logger.error("rollbackAnulacionDevolucion(int, Date, int, int)", e);
		}
		
		if (logger.isDebugEnabled()) {
			logger
					.debug("rollbackAnulacionDevolucion(int, Date, int, int) - end");
		}
	}

	/**
	 * @param devolucion
	 */
	public static boolean isTransaccionRecuperada(int tienda, String fechaT, int cajaOriginal, int numTransaccion) throws BaseDeDatosExcepcion, ConexionExcepcion {
		String sentenciaSQL = "select * from " + Sesion.getDbEsquema() + ".transaccionrecuperada where numtienda=" + tienda +
			" and fecha='" + fechaT + "' and numcaja=" + cajaOriginal + " and numtransaccion=" + numTransaccion;
		ResultSet resultado = Conexiones.realizarConsulta(sentenciaSQL, true);
		try {
			return resultado.first();
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * @param devolucion
	 */
	public static void marcarTransaccionRecuperada(int tienda, String fechaT, int cajaOriginal, int numTransaccion) throws BaseDeDatosExcepcion, ConexionExcepcion {
		String sentenciaSQL = "insert into " + Sesion.getDbEsquema() + ".transaccionrecuperada values (" + tienda +
			", '" + fechaT + "', " + cajaOriginal + ", " + numTransaccion + ")";
		Conexiones.realizarSentencia(sentenciaSQL, true);
	}

	/**
	 * 
	 * @param devolucion
	 * @param venta
	 * @throws BaseDeDatosExcepcion
	 * @throws SQLException
	 * @throws ConexionExcepcion
	 */
	
	public static void devolucionEnEspera (Devolucion devolucion, Venta venta) throws BaseDeDatosExcepcion, SQLException, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("devolucionEnEspera (Devolucion, Venta) - start");
		}
		PreparedStatement pstmt = null;
		
		try {
		//	String className = devolucion.getClass().getName().equals("com.becoblohm.cr.manejarventa.Devolucion") ? "D":"V";
			SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy-MM-dd");
			String fechaTransString = fechaTrans.format(devolucion.getVentaOriginal().getFechaTrans());
			
			pstmt = Conexiones.getConexion(false).prepareStatement(almacenar);
			
			pstmt.setInt(1, devolucion.getVentaOriginal().getCodTienda());
			pstmt.setString(2, fechaTransString);
			pstmt.setInt(3, devolucion.getVentaOriginal().getNumCajaFinaliza());
			pstmt.setInt(4, devolucion.getVentaOriginal().getNumTransaccion());
			pstmt.setString(5, String.valueOf(Sesion.DEVOLUCION));
			Object objetoDevolucion = (Object)devolucion;
			pstmt.setObject(6, objetoDevolucion );
			pstmt.addBatch();
			if (venta != null)
				if (venta.getDetallesTransaccion().size() > 0) //Si se había registrado algún artículo para nueva venta en devolución por cambio
				{
					
					pstmt.setInt(1, devolucion.getVentaOriginal().getCodTienda());
					pstmt.setString(2, fechaTransString); //Coloco la fecha de la transaccion original
					pstmt.setInt(3, devolucion.getVentaOriginal().getNumCajaFinaliza());
					pstmt.setInt(4, devolucion.getVentaOriginal().getNumTransaccion());
					pstmt.setString(5, String.valueOf(Sesion.VENTA));
					objetoDevolucion = (Object)venta;
					pstmt.setObject(6, objetoDevolucion );
					pstmt.addBatch();
				}
			Conexiones.ejecutarLoteSentencias(pstmt,false,true);
			pstmt.close();
			
		} catch (ConexionExcepcion e1){
			logger.error("devolucionEnEspera (Devolucion, Venta) ", e1);

			throw (new ConexionExcepcion("Error al colocar devolución en espera ", e1));
		} catch (SQLException e2) {
			logger.error("devolucionEnEspera (Devolucion, Venta) ", e2);

			throw (new BaseDeDatosExcepcion("Error al colocar devolución en espera ", e2));
		} catch (BaseDeDatosExcepcion e2)
		{
			logger.error("devolucionEnEspera (Devolucion, Venta) ", e2);

			throw (new BaseDeDatosExcepcion("Ya se ha colocado en espera la transaccion No. " + devolucion.getVentaOriginal().getNumTransaccion(), e2));
		}
		 finally {
			try {
				pstmt.close();	
			} catch (SQLException e) {
				logger.error("devolucionEnEspera (Devolucion, Venta)", e);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("devolucionEnEspera (Devolucion, Venta) - end");
		}

	}

	/**
	 * 
	 * @param tienda
	 * @param fecha
	 * @param caja
	 * @param transaccion
	 * @throws SQLException
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr 
	 */
	public static void recuperarDevolucionEnEspera (int tienda, String fecha, int caja, int transaccion) throws SQLException, ConexionExcepcion, ExcepcionCr{
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			// Para recuperar la Devolución busco devolución y venta (si existen)
			pstmt = Conexiones.getConexion(false).prepareStatement(recuperar);
			pstmt.setInt(1, tienda);
			pstmt.setString(2, fecha);
			pstmt.setInt(3, caja);
			pstmt.setInt(4, transaccion);
			
			rs = pstmt.executeQuery();
			rs.beforeFirst();
			while (rs.next())
			{
				String tipoTransaccion = rs.getString("tipotransaccion");
				//MODIFICACION AL MIGRAR DE JAVA1.4 A 1.6 Y MYSQL4 A MYSQL5. FUE NECESARIO TRANSFORMAR
				//EL ARREGLO DE BYTES QUE DEVUELVE MYSQL AL RECUPERAR UN TIPO BLOB A TIPO OBJETO 'DEVOLUCION'
				if (tipoTransaccion.equals(String.valueOf(Sesion.DEVOLUCION)))
				{
					if(rs.getBytes("objeto")!=null){
						ByteArrayInputStream bs = new ByteArrayInputStream(rs.getBytes("objeto"));	
						ObjectInputStream is = new ObjectInputStream(bs);
						Devolucion objetoDevolucion = (Devolucion)is.readObject();
						CR.meVenta.setDevolucion(objetoDevolucion,Sesion.usuarioActivo.getNumFicha());
						is.close();
						bs.close();
					}
					
				} else {
					if(rs.getBytes("objeto")!=null){
						ByteArrayInputStream bs = new ByteArrayInputStream(rs.getBytes("objeto"));	
						ObjectInputStream is = new ObjectInputStream(bs);
						Venta objetoVenta = (Venta)is.readObject();
						CR.meVenta.setVenta(objetoVenta, Sesion.usuarioActivo.getNumFicha());
						is.close();
						bs.close();
					}
				}
				
			}
			String sentenciaSql = "delete from devolucionespera " +
							"where numtienda = " + tienda + " and fecha = '" + fecha + "' and caja = " +
							caja + " and numtransaccion = " + transaccion;
			if (Conexiones.realizarSentencia(sentenciaSql, false) <= 0)
			{
				throw (new BaseDeDatosExcepcion("No existe transaccion en espera correspondiente a ese identificador"));
			}

		}catch (ConexionExcepcion e){
			logger.error("devolucionEnEspera (Devolucion, Venta)  3413");
			CR.meVenta.setDevolucion(null);
			CR.meVenta.setVenta(null);
			throw (new ConexionExcepcion("Error al recuperar devolución en espera ", e));
		}catch (SQLException e){
			logger.error("devolucionEnEspera (Devolucion, Venta)  3418");
			CR.meVenta.setDevolucion(null);
			CR.meVenta.setVenta(null);
			throw (new BaseDeDatosExcepcion("Error al recuperar devolución en espera ", e));
		} catch (BaseDeDatosExcepcion e){	
			logger.error("devolucionEnEspera (Devolucion, Venta)  3423");
			CR.meVenta.setDevolucion(null);
			CR.meVenta.setVenta(null);
			throw (new BaseDeDatosExcepcion("No existe transaccion en espera correspondiente a ese identificador", e));
		} catch( Exception e){	
			logger.error("devolucionEnEspera (Devolucion, Venta) ", e);
			CR.meVenta.setDevolucion(null);
			CR.meVenta.setVenta(null);
			e.printStackTrace();
			throw (new ExcepcionCr("Error al recuperar devolución en espera ", e));
		}finally {
			try {
				rs.close();
				pstmt.close();	
			} catch (SQLException e) {
				logger.error("devolucionEnEspera (Devolucion, Venta)", e);
			}
		}

	
	}
/**
 * Devuelve un arreglo con 4 posiciones para cada devolucion en espera
 * @return Arreglo en el orden ci, nombre trxOriginal, fechaTrxOriginal con los datos de las devoluciones en espera
 * @throws SQLException
 * @throws ConexionExcepcion
 * @throws BaseDeDatosExcepcion
 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<String> getDevolucionesEnEspera()throws SQLException, ConexionExcepcion, BaseDeDatosExcepcion{
		Vector<String> devolucionesPendientes = new Vector<String>();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String ci;
		String nombreCliente;
		try {
			// Para recuperar la Devolución busco devolución y venta (si existen)
			pstmt = Conexiones.getConexion(false).prepareStatement(recuperarTodas);

			rs = pstmt.executeQuery();
			rs.beforeFirst();
			while (rs.next())
			{
				//MODIFICACION AL MIGRAR DE JAVA1.4 A 1.6 Y MYSQL4 A MYSQL5. FUE NECESARIO TRANSFORMAR
				//EL ARREGLO DE BYTES QUE DEVUELVE MYSQL AL RECUPERAR UN TIPO BLOB A TIPO OBJETO 'DEVOLUCION'
				Devolucion objetoDevolucion;
				if(rs.getBytes("objeto")!=null){
					ByteArrayInputStream bs = new ByteArrayInputStream(rs.getBytes("objeto"));	
					ObjectInputStream is = new ObjectInputStream(bs);
					objetoDevolucion = (Devolucion)is.readObject();
					is.close();
				
				
				ci = String.valueOf(objetoDevolucion.getCliente().getCodCliente());
				devolucionesPendientes.addElement(ci);
				nombreCliente = objetoDevolucion.getCliente().getNombre() + " " + objetoDevolucion.getCliente().getApellido();	
				devolucionesPendientes.addElement(nombreCliente);
				}
				devolucionesPendientes.addElement(String.valueOf(rs.getInt("numtienda")));
				devolucionesPendientes.addElement(String.valueOf(rs.getString("fecha")));
				devolucionesPendientes.addElement(String.valueOf(rs.getInt("caja")));
				devolucionesPendientes.addElement(String.valueOf(rs.getInt("numtransaccion")));
			}
		}catch (ConexionExcepcion e){
			logger.error("devolucionEnEspera (Devolucion, Venta) ", e);
			CR.meVenta.setDevolucion(null);
			CR.meVenta.setVenta(null);
			throw (new ConexionExcepcion("Error al recuperar devolución en espera ", e));
		}catch (SQLException e){
			logger.error("devolucionEnEspera (Devolucion, Venta) ", e);
			CR.meVenta.setDevolucion(null);
			CR.meVenta.setVenta(null);
			throw (new BaseDeDatosExcepcion("Error al recuperar devolución en espera ", e));
		} catch (Exception e) {
			logger.error("devolucionEnEspera (Devolucion, Venta) ", e);
			CR.meVenta.setDevolucion(null);
			CR.meVenta.setVenta(null);
			throw (new BaseDeDatosExcepcion("Error al recuperar devolución en espera ", e));
		}finally {
			try {
				rs.close();
				pstmt.close();	
			} catch (SQLException e) {
				logger.error("devolucionEnEspera (Devolucion, Venta)", e);
			}
		}
		
		
		return devolucionesPendientes;
				
	}

	/**
	 * Si la venta tiene forma de pago de cupon de descuento
	 * se invalida el cupon
	 * @param venta
	 * @throws ConexionExcepcion 
	 * @throws BaseDeDatosExcepcion 
	 */
	public static void invalidarCupon(Venta venta) throws BaseDeDatosExcepcion, ConexionExcepcion{
		DateFormat df =  new SimpleDateFormat("yyyy-MM-dd");
		for(int i=0;i<venta.getPagos().size();i++){
			Pago pago =  (Pago)venta.getPagos().elementAt(i);
			if(pago.getFormaPago().getCodigo().equalsIgnoreCase(Sesion.FORMA_PAGO_CUPON_DESCUENTO)){
			//	String codCupon = MaquinaDeEstadoVenta.codCupon;
				int codPromocionCupon = MaquinaDeEstadoVenta.codPromocionCupon;
			/*	if(MaquinaDeEstadoVenta.invalidarCupon){
					String sentenciaSQL = "update "+Sesion.getDbEsquema()+".cuponesvalidos set numtienda="+Sesion.getTienda().getNumero()+", fecha='"+df.format(venta.getFechaTrans())+"', numcaja="+venta.getNumCajaFinaliza()+", numtransaccion="+venta.getNumTransaccion()+" where codpromocion="+codPromocionCupon+" and codcupon='"+codCupon+"'";
					ConexionServCentral.realizarSentencia(sentenciaSQL);
				}*/
				break;
			}
		}
	}
	
}
