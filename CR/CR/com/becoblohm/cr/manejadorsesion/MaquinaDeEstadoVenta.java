/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.manejadorsesion
 * Programa   : MaquinaDeEstadoVenta.java
 * Creado por : irojas
 * Creado en  : 26-abr-04 8:35:54
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 2.2
 * Fecha       : 02-JUL-2008
 * Analista    : jgraterol
 * Descripción : Agregado vector de productos patrocinantes
 * =============================================================================
 * Versión     : 2.1
 * Fecha       : 09-jun-2008
 * Analista    : jgraterol
 * Descripción : Modificado el metodo consultarMontoTrans(boolean) para llamar
 * 				a la extension de promociones para solicitar donativo
 * =============================================================================
 * Versión     : 2.0
 * Fecha       : 07-feb-07 
 * Analista    : irojas
 * Descripción : - En asignar clientes se agregó la validación de RIF y CI
 * 				   correctos para SENIAT
 * =============================================================================
 * Versión     : 2.0
 * Fecha       : 20-jun-06 
 * Analista    : yzambrano
 * Descripción : - Manejo de opciones de devolución de productos por cambio
 * =============================================================================
 * Versión     : 2.0
 * Fecha       : 20-jun-06 
 * Analista    : yzambrano
 * Descripción : Buscar cliente temporal
 * =============================================================================
 * Versión     : 1.1
 * Fecha       : 10-may-04 9:42
 * Analista    : gmartinelli
 * Descripción : Actualizados comentarios JavaDocs.
 * =============================================================================
 * Versión     : 1.1
 * Fecha       : 10-may-04 9:42
 * Analista    : gmartinelli
 * Descripción : Actualizados comentarios JavaDocs.
 * =============================================================================
 * Versión     : 1.0
 * Fecha       : 26-abr-04 8:35:54
 * Analista    : irojas
 * Descripción : Implementación inicial.
 * =============================================================================
 */
package com.becoblohm.cr.manejadorsesion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.beco.cr.actualizadorPrecios.promociones.PromocionExt;
import com.beco.cr.devolucion.InterfaceRemota;
import com.becoblohm.cr.CR;
import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.excepciones.AfiliadoUsrExcepcion;
import com.becoblohm.cr.excepciones.AnulacionExcepcion;
import com.becoblohm.cr.excepciones.AutorizacionExcepcion;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ClienteExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.PagoExcepcion;
import com.becoblohm.cr.excepciones.ProductoExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.manejadorauditoria.Auditoria;
import com.becoblohm.cr.extensiones.ManejadorReportesFactory;

import com.becoblohm.cr.extensiones.ActualizadorPrecios;
import com.becoblohm.cr.extensiones.ActualizadorPreciosFactory;
import com.becoblohm.cr.extensiones.ManejoPagosFactory;
import com.becoblohm.cr.extensiones.RegistroClienteFactory;
import com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.manejarservicio.Apartado;
import com.becoblohm.cr.manejarservicio.Cotizacion;
import com.becoblohm.cr.manejarservicio.ListaRegalos;
import com.becoblohm.cr.manejarservicio.OperacionLR;
import com.becoblohm.cr.manejarventa.Anulacion;
import com.becoblohm.cr.manejarventa.CancelarDevolucion;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.manejarventa.ComparadorPagos;
import com.becoblohm.cr.manejarventa.CondicionVenta;
import com.becoblohm.cr.manejarventa.DetalleTransaccion;
import com.becoblohm.cr.manejarventa.Devolucion;
import com.becoblohm.cr.manejarventa.Producto;
import com.becoblohm.cr.manejarventa.Transaccion;
import com.becoblohm.cr.manejarventa.Venta;
import com.becoblohm.cr.mediadoresbd.BaseDeDatosServicio;
import com.becoblohm.cr.mediadoresbd.BaseDeDatosVenta;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.utiles.Control;
import com.becoblohm.cr.utiles.MathUtil;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.becoblohm.cr.utiles.Validaciones;
import com.epa.crprinterdriver.CRFiscalPrinterOperations;
import com.epa.crprinterdriver.PrinterNotConnectedException;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;

/** 
 * Descripción: 
 * 		Clase que maneja la transición de estados de la Caja registradora del 
 * módulo de Facturación (Ventas, Anulaciones y Devoluciones). También se manejan las
 * autorizaciones y auditorias de las operaciones que se realicen en las cajas
 * con respecto al módulo de Facturación.
 */
/*
* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Se comentó variable sin uso
* Fecha: agosto 2011
*/
public class MaquinaDeEstadoVenta {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(MaquinaDeEstadoVenta.class);

	private Venta venta;
	private Transaccion transaccionPorImprimir;
	
	//6/11/2008 agregado por impresora GD4
	private static boolean documentoNoFiscalPorImprimir=false;
	public static boolean errorAtencionUsuario = false;
	
	private Devolucion devolucion;
	private Anulacion anulacion;
	private DecimalFormat df = new DecimalFormat("#,##0.00");
	
	private boolean esContribuyenteOrdinario = false;
	
	private Vector<OperacionLR> abonosTotalesVentaLR = new Vector<OperacionLR>();
	
	private ActualizadorPrecios actualizadorPrecios =null;
	//public static String codCupon = "";
	public static int codPromocionCupon;
	//public static boolean invalidarCupon;
	
	/**
	 * Matriz de productos patrocinantes
	 * clave = codigo de una promocion tipo F (ahorro en compra)
	 * valor = vector de productos patrocinantes
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'TreeMap'
	* Fecha: agosto 2011
	*/
	private TreeMap<PromocionExt,Vector<Producto>> productosPatrocinantes = null;
	/**
	 * Matriz de productos que regalan uno complementario de mercadeo
	 * clave = codigo de una promocion tipo E (producto complementario (POP))
	 * valor = vector de productos patrocinantes
	 * **/
	//private HashMap ProductosComplementario = null;
	/**
	 * Contructor para MaquinaDeEstadoVenta
	 * 		Crea la ME para la venta. Inicializa los atributos
	 */
	public MaquinaDeEstadoVenta() {
		venta = null;
		devolucion = null;
		anulacion = null;
		transaccionPorImprimir = null;
		//ACTUALIZACION BECO: 02/07/2008
		if(actualizadorPrecios==null){
			iniciarActualizadorPrecios();
		}
		productosPatrocinantes = actualizadorPrecios.cargarPatrocinantes();
		//ProductosComplementario = actualizadorPrecios.cargarProductoComplementario();
	}
	
	/**
	 * Método ingresarLineaProducto
	 * 		Ingresa una nueva linea en el detalle de una venta. Si la venta no esta activa se crea la venta.
	 * @param codProd Codigo del producto a insertar
	 * @param codVendedor Codigo del vendedor (Si aplica)
	 * @param tipoCaptura Tipo de captura del producto (Teclado, Dispositivo)
	 * @throws BaseDeDatosExcepcion - Si ocurre un error con la conexion a 
	 * 		la Base de Datos
	 * @throws MaquinaDeEstadoExcepcion - Si el estado es incorrecto para 
	 * 		Ingresar Producto
	 * @throws ProductoExcepcion - Si el producto insertado no existe
	 */
	public void ingresarLineaProducto(String codProd, String codVendedor, String tipoCaptura, boolean leidoBD) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ProductoExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger
					.debug("ingresarLineaProducto(String, String, String) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "ingresarLineaProducto");
		boolean existiaVentaActiva = (venta != null) ? true : false;

		Sesion.setUbicacion("FACTURACION","ingresarLineaProducto");
		if (!existiaVentaActiva) {
			Sesion.getCaja().getNuevoNumRegCaja();
			venta = new Venta();
		}
		try {
			CR.me.verificarAutorizacion ("FACTURACION","ingresarLineaProducto", this.venta.getCliente());
			this.venta.ingresarLineaProducto(codProd, codVendedor, tipoCaptura.toUpperCase(), leidoBD);
			
			try {
				if(InitCR.preferenciasCR.getConfigStringForParameter("sistema","estiloFactura").equals("CR")){
					/**
					 * Costa Rica Style
					 */					
					try{ 
						CR.crVisor.enviarString(((DetalleTransaccion)venta.getDetallesTransaccion().lastElement()).getProducto().getDescripcionCorta(),0,df.format(((DetalleTransaccion)venta.getDetallesTransaccion().lastElement()).getPrecioFinal()+((DetalleTransaccion)venta.getDetallesTransaccion().lastElement()).getMontoImpuesto()),2); 
					}
					catch(Exception ex){
						logger.error("ingresarLineaProducto(String, String, String)",ex);
					}
				}
				else{
					/**
					 * Venezuela Style
					 */
					try{ 
						CR.crVisor.enviarString(((DetalleTransaccion)venta.getDetallesTransaccion().lastElement()).getProducto().getDescripcionCorta(),0,df.format(((DetalleTransaccion)venta.getDetallesTransaccion().lastElement()).getPrecioFinal()+((DetalleTransaccion)venta.getDetallesTransaccion().lastElement()).getMontoImpuesto()),2); 
					}
					catch(Exception ex){
						logger.error("ingresarLineaProducto(String, String, String)",ex);
					}
				}
			} catch (NoSuchNodeException e1) {
				logger.error("ingresarLineaProducto(String, String, String)",
						e1);
			} catch (UnidentifiedPreferenceException e1) {
				logger.error("ingresarLineaProducto(String, String, String)",
						e1);
			}
			
			
		} catch (BaseDeDatosExcepcion e) {
			logger.error("ingresarLineaProducto(String, String, String)", e);

			if (!existiaVentaActiva) {
				venta = null;
				Sesion.getCaja().recuperarNumReg();
			}
			throw e;
		} catch (ProductoExcepcion e) {
			logger.error("ingresarLineaProducto(String, String, String)");

			if (!existiaVentaActiva) {
				venta = null;
				Sesion.getCaja().recuperarNumReg();
			}
			throw e;
		}
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("ingresarLineaProducto(String, String, String) - end");
		}
	}
	
	/**
	 * Método ingresarLineaProducto
	 * 		Ingresa una nueva linea en el detalle de una venta. Si la venta 
	 * no esta activa se crea la venta.
	 * @param codProd - Codigo del producto a insertar
	 * @param tipoCaptura - Tipo de captura del producto (Teclado, Dispositivo)
	 * @throws BaseDeDatosExcepcion - Si ocurre un error con la conexion a 
	 * 		la Base de Datos
	 * @throws MaquinaDeEstadoExcepcion - Si el estado es incorrecto para 
	 * 		Ingresar Producto
	 * @throws ProductoExcepcion - Si el producto insertado no existe
	 */
	public void ingresarLineaProducto(String codProd, String tipoCaptura) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ProductoExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("ingresarLineaProducto(String, String) - start");
		}
		//No se pueden hacer cambios si ya la venta posee un pago especial
		if(contienePagosBeco()){
			throw new ExcepcionCr("No se pueden hacer cambios a la transacción después de agregar pagos especiales.\nDebe eliminar el pago promocional y agregarlo después de hacer los cambios");
		}
		this.ingresarLineaProducto(codProd,null,tipoCaptura, false);

		if (logger.isDebugEnabled()) {
			logger.debug("ingresarLineaProducto(String, String) - end");
		}
	}
	
	/**
	 * Método cambiarCantidad.
	 * 	Cambia la cantidad de un renglón de la venta
	 * @param cantidad Cantidad a agregar al renglon
	 * @throws BaseDeDatosExcepcion Si ocurre un error de acceso a la base de datos
	 * @throws ProductoExcepcion Si el renglón es inválido
	 * @throws MaquinaDeEstadoExcepcion Si no se están en el estado correcto
	 * @throws ExcepcionCr Si falla la búsqueda de metodo y modulo en la base de datos para la auditoria 
	 */
	public void cambiarCantidad(float cantidad) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("cambiarCantidad(float) - start");
		}
		//No se puede aplicar descuento empleado si ya la venta posee un pago especial
		if(contienePagosBeco()){
			throw new ExcepcionCr("No se pueden hacer cambios a la transacción después de agregar pagos especiales.\nDebe eliminar el pago promocional y agregarlo después de hacer los cambios");
		}
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "cambiarCantidad");
		
		Sesion.setUbicacion("FACTURACION", "cambiarCantidad");
		CR.me.verificarAutorizacion ("FACTURACION","cambiarCantidad", this.venta.getCliente());
		this.venta.agregarCantidad(cantidad, this.venta.getDetallesTransaccion().size()-1);
		
		
		try {
			if(InitCR.preferenciasCR.getConfigStringForParameter("sistema","estiloFactura").equals("CR")){
				/**
				 * Costa Rica Style
				 */			
				
				/**
				 * LINEA del Impuesto que agregue
				 * ((DetalleTransaccion)venta.getDetallesTransaccion().lastElement()).getMontoImpuesto())
				 */
				//Se envía al Visor la multiplicación de la cantidad agregada por el precio del producto
				String precio = df.format(((DetalleTransaccion)venta.getDetallesTransaccion().lastElement()).getPrecioFinal() + ((DetalleTransaccion)venta.getDetallesTransaccion().lastElement()).getMontoImpuesto());
				String cantXprecio = df.format(cantidad) + " X " +  precio;
				try{ 
					CR.crVisor.enviarString(((DetalleTransaccion)venta.getDetallesTransaccion().lastElement()).getProducto().getDescripcionCorta(), 0, cantXprecio, 2); 
				}
				catch(Exception ex){
					logger.error("cambiarCantidad(float)", ex);
				}				
			}
			else{
				/**
				 * Venezuela Style
				 */
				//Se envía al Visor la multiplicación de la cantidad agregada por el precio del producto
				String precio = df.format(((DetalleTransaccion)venta.getDetallesTransaccion().lastElement()).getPrecioFinal() + ((DetalleTransaccion)venta.getDetallesTransaccion().lastElement()).getMontoImpuesto());
				String cantXprecio = df.format(cantidad) + " X " +  precio;
				try{ 
					CR.crVisor.enviarString(((DetalleTransaccion)venta.getDetallesTransaccion().lastElement()).getProducto().getDescripcionCorta(), 0, cantXprecio, 2); 
				}
				catch(Exception ex){
					logger.error("cambiarCantidad(float)", ex);
				}
			}
		} catch (NoSuchNodeException e1) {
			logger.error("cambiarCantidad(float)",
					e1);
		} catch (UnidentifiedPreferenceException e1) {
			logger.error("cambiarCantidad(float)",
					e1);
		}

		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("cambiarCantidad(float) - end");
		}
	}
	
	/**
	 * Método cambiarCantidad.
	 * 	Cambia la cantidad de un renglón de la venta
	 * @param cantidad Cantidad a agregar al renglon
	 * @param renglon Renglon a modificar la cantidad
	 * @throws BaseDeDatosExcepcion Si ocurre un error de acceso a la base de datos
	 * @throws ProductoExcepcion Si el renglón es inválido
	 * @throws MaquinaDeEstadoExcepcion Si no se están en el estado correcto
	 * @throws ExcepcionCr Si falla la búsqueda de metodo y modulo en la base de datos para la auditoria 
	 */
	public void cambiarCantidad(float cantidad, int renglon) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("cambiarCantidad(float, int) - start");
		}

		//No se puede aplicar descuento empleado si ya la venta posee un pago especial
		if(contienePagosBeco()){
			throw new ExcepcionCr("No se pueden hacer cambios a la transacción después de agregar pagos especiales.\nDebe eliminar el pago promocional y agregarlo después de hacer los cambios");
		}
		
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "cambiarCantidad");
		
		Sesion.setUbicacion("FACTURACION", "cambiarCantidad");
		CR.me.verificarAutorizacion ("FACTURACION","cambiarCantidad", this.venta.getCliente());
		this.venta.agregarCantidad(cantidad, renglon);
		
		
		try {
			if(InitCR.preferenciasCR.getConfigStringForParameter("sistema","estiloFactura").equals("CR")){
				/**
				 * Costa Rica Style
				 */			
				
				/**
				 * LINEA del Impuesto que agregue
				 * ((DetalleTransaccion)venta.getDetallesTransaccion().lastElement()).getMontoImpuesto())
				 */
				//Se envía al Visor la multiplicación de la cantidad agregada por el precio del producto
				String precio = df.format(((DetalleTransaccion)venta.getDetallesTransaccion().lastElement()).getPrecioFinal() + ((DetalleTransaccion)venta.getDetallesTransaccion().lastElement()).getMontoImpuesto());
				String cantXprecio = df.format(cantidad) + " X " +  precio;
				try{ 
					CR.crVisor.enviarString(((DetalleTransaccion)venta.getDetallesTransaccion().lastElement()).getProducto().getDescripcionCorta(), 0, cantXprecio, 2); 
				}
				catch(Exception ex){
					logger.error("cambiarCantidad(float)", ex);
				}				
			}
			else{
				/**
				 * Venezuela Style
				 */
				//Se envía al Visor la multiplicación de la cantidad agregada por el precio del producto
				String precio = df.format(((DetalleTransaccion)venta.getDetallesTransaccion().lastElement()).getPrecioFinal() + ((DetalleTransaccion)venta.getDetallesTransaccion().lastElement()).getMontoImpuesto());
				String cantXprecio = df.format(cantidad) + " X " +  precio;
				try{ 
					CR.crVisor.enviarString(((DetalleTransaccion)venta.getDetallesTransaccion().lastElement()).getProducto().getDescripcionCorta(), 0, cantXprecio, 2); 
				}
				catch(Exception ex){
					logger.error("cambiarCantidad(float, int)", ex);
				}

			}
		} catch (NoSuchNodeException e1) {
			logger.error("cambiarCantidad(float)",
					e1);
		} catch (UnidentifiedPreferenceException e1) {
			logger.error("cambiarCantidad(float)",
					e1);
		}
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("cambiarCantidad(float, int) - end");
		}
	}

	/**
	 * Método consultarMontoTrans
	 * 		Obtiene el subtotal de la transaccion.
	 * @return double - El monto actual de la transaccion
	 * @throws BaseDeDatosExcepcion - Si ocurre un error con la conexion 
	 * 		a la Base de Datos
	 * @throws MaquinaDeEstadoExcepcion - Si el estado es incorrecto para 
	 * 		Consultar SubTotal
	 */
	public double consultarMontoTrans (boolean mostrarSubTotalVisor) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("consultarMontoTrans(boolean) - start");
		}

		double subTotal = 0;
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "consultarSubTotal");
		
		CR.me.verificarAutorizacion ("FACTURACION","consultarSubTotal", this.venta.getCliente());
		subTotal = venta.consultarMontoTrans();
		
		if (mostrarSubTotalVisor){
			try{ CR.crVisor.enviarString("TOTAL", 0, df.format(subTotal), 2); }
			catch(Exception ex){
				logger.error("consultarMontoTrans(boolean)", ex);
			}
		}	
		
		//MODIFICADO BECO: 09-JUN-2008
		//Agregada llamada al actualizador de precios para sumar o no donaciones
		if(actualizadorPrecios==null){
			iniciarActualizadorPrecios();			
		}
		//Sustituyo lo del total por lo indicado en donaciones
		subTotal = actualizadorPrecios.sumarDonaciones(this.venta, true);
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("consultarMontoTrans(boolean) - end");
		}
		return subTotal;
	}
	
	/**
	 * Método consultarMontoTrans
	 * 		Obtiene el subtotal de la transaccion.
	 * @return double - El monto actual de la transaccion
	 * @throws BaseDeDatosExcepcion - Si ocurre un error con la conexion 
	 * 		a la Base de Datos
	 * @throws MaquinaDeEstadoExcepcion - Si el estado es incorrecto para 
	 * 		Consultar SubTotal
	 */
	public double consultarMontoTrans () throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("consultarMontoTrans() - start");
		}

		double returndouble = consultarMontoTrans(false);
		if (logger.isDebugEnabled()) {
			logger.debug("consultarMontoTrans() - end");
		}
		return returndouble;
	}
	
	/**
	 * Método consultarMontoTransPDA
	 * 		Obtiene el subtotal de la transaccion para PDA. (Se eliminó la opcion de Donaciones) (Modificar al igual que consultarMontoTrans)
	 * @return double - El monto actual de la transaccion
	 * @throws BaseDeDatosExcepcion - Si ocurre un error con la conexion 
	 * 		a la Base de Datos
	 * @throws MaquinaDeEstadoExcepcion - Si el estado es incorrecto para 
	 * 		Consultar SubTotal
	 */
	public double consultarMontoTransPDA () throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("consultarMontoTrans(boolean) - start");
		}

		double subTotal = 0;
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "consultarSubTotal");
		
		CR.me.verificarAutorizacion ("FACTURACION","consultarSubTotal", this.venta.getCliente());
		subTotal = venta.consultarMontoTrans();
		
		//MODIFICADO BECO: 09-JUN-2008
		//Agregada llamada al actualizador de precios para sumar o no donaciones
		if(actualizadorPrecios==null){
			iniciarActualizadorPrecios();			
		}
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("consultarMontoTrans(boolean) - end");
		}
		return subTotal;
	}

	/**
	 * Método efectuarPago.
	 * 		Realiza un pago sobre la venta activa. Solo se maneja el efectivo
	 * @return double - El monto que falta por pagar. Si el resto es 
	 * 		positivo falta por pagar. Si el resto es negativo es el vuelto.
	 * @throws BaseDeDatosExcepcion - Si ocurre un error con la conexion 
	 * 		a la Base de Datos
	 * @throws MaquinaDeEstadoExcepcion - Si el estado es incorrecto para 
	 * 		Efectuar Pago
	 */
	public double efectuarPago(Pago p) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("efectuarPago(Pago) - start");
		}

		double resto = 0;
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "efectuarPago");
		
		//Verificamos si existen detalles en la venta para poder efectuar algún pago
		if (venta.getDetallesTransaccion().size()<=0) {
			throw (new MaquinaDeEstadoExcepcion("No puede efectuar pago. No existen detalles en la venta"));
		}
		
		CR.me.verificarAutorizacion ("Pago","efectuarPago", this.venta.getCliente());
		resto = this.venta.efectuarPago(p);

		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("efectuarPago(Pago) - end");
		}
		return resto;
	}
	
	/**
	 * Método finalizarVenta.
	 * 		Registra los datos finales de la venta (numeroTransaccion, 
	 * horaFin, etc) y la registra en la Base de Datos.
	 * @throws BaseDeDatosExcepcion - Si ocurre un error con la conexion 
	 * 		a la Base de Datos
	 * @throws MaquinaDeEstadoExcepcion - Si el estado es incorrecto para 
	 * 		Finalizar Ventas
	 * @throws PagoExcepcion - Si no se han completado los pagos de la 
	 * 		transacción
	 */
	public void finalizarVenta() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ExcepcionCr, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion, PrinterNotConnectedException {
		if (logger.isDebugEnabled()) {
			logger.debug("finalizarVenta() - start");
		}
		
		this.finalizarVenta(true);

		if (logger.isDebugEnabled()) {
			logger.debug("finalizarVenta() - end");
		}
	}

	/**
	 * Método finalizarVenta.
	 * 		Registra los datos finales de la venta (numeroTransaccion, 
	 * horaFin, etc) y la registra en la Base de Datos.
	 * 		Este método recibe un boolean que indica si se requiere chequeo de estados o no (Caso NO en reimpresion de factura)
	 * @throws BaseDeDatosExcepcion - Si ocurre un error con la conexion 
	 * 		a la Base de Datos
	 * @throws MaquinaDeEstadoExcepcion - Si el estado es incorrecto para 
	 * 		Finalizar Ventas
	 * @throws PagoExcepcion - Si no se han completado los pagos de la 
	 * 		transacción
	 */
	private int finalizarVenta(boolean verificarEdos) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ExcepcionCr, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion, PrinterNotConnectedException {
		if (logger.isDebugEnabled()) {
			logger.debug("finalizarVenta(boolean) - start");
		}
		int numTransaccion = 0;
		String edoFinalCaja = "";
		if(verificarEdos) {
			edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "finalizarVenta");
		}
		//Verificamos si existen detalles en la venta para poder finalizarla
		if (venta.getDetallesTransaccion().size()<=0) {
			throw (new MaquinaDeEstadoExcepcion("No puede finalizar la venta No existen detalles."));
		}
		if (transaccionPorImprimir != null) {
			throw (new MaquinaDeEstadoExcepcion("No puede finalizar la venta. Hay una transacción pendiente por imprimir"));
		}
		// Chequeamos que se puede finalizar la venta (Pagos completos)
		double monto = venta.consultarMontoTrans();
		if (monto - venta.obtenerMontoPagado() < 0.01) {
			// Los pagos estan completos. Finalizamos la venta
			CR.me.verificarAutorizacion ("FACTURACION","finalizarVenta", this.venta.getCliente());
			
			//PROMOCIONES: jgraterol 17/05/2010 
			//Verifico si la venta tiene pagos especiales para agregarlos como corresponde a la transaccion
			//(new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().agregarPagosEspeciales(venta);
			venta.finalizarTransaccion();
			try{ 
				CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ..."); }
			catch(Exception ex){
				logger.warn("finalizarVenta(boolean)", ex);
			}
			numTransaccion = venta.getNumTransaccion();
			venta = null;
		} else {
			// No se ha pagado completamente. Lanzamos una Excepcion
			throw (new PagoExcepcion("No se puede finalizar la venta. El monto del pago no abarca el total de la venta"));
		}
		if (verificarEdos) {
			// Actualizamos el estado de la caja
			Sesion.getCaja().setEstado(edoFinalCaja);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("finalizarVenta(boolean) - end");
		}
		return numTransaccion;
	}
		
	/**
	 * Método getVenta.
	 * 		Devuelve la venta.
	 * @return Venta - Instancia de la clase Venta
	 */
	public Venta getVenta() {
		if (logger.isDebugEnabled()) {
			logger.debug("getVenta() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getVenta() - end");
		}
		return venta;
	}
	
	/**
	 * Method obtenerDescuentos.
	 * 		Permite obtener todos los posibles descuentos (precios posibles a aplicar) que se le pueden hacer al producto
	 * 		de manera que la compañía obtenga ganacia
	 * @param renglon Código del producto al que se le quiere hacer el descuento
	 * @return Vector - Vector que indica todos los posibles precios que se le pueden aplicar al producto con ganancia a la compañía
	 * @throws ProductoExcepcion Si el producto indicado no existe
	 * @throws BaseDeDatosExcepcion Si ocurre un error con la conexion 
	 * 		a la Base de Datos
	 * @throws ExcepcionCr Si no existe venta activa o si la Tienda no permite realizar esta operación.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Double> obtenerDescuentos(int renglon) throws ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerDescuentos(int) - start");
		}

		Vector<Double> descuentos = null;
		if (Sesion.isCambiarPrecio()) {
			if (venta != null && venta.getDetallesTransaccion().size() > 0) {
				descuentos = this.venta.obtenerDescuentos(renglon);	
			} else {
				throw (new ExcepcionCr("No se puede realizar la operacion. No existe venta activa"));	
			}				
		} else {
			throw (new ExcepcionCr("No se puede realizar la operacion. La tienda no tiene activa esta opcion"));				
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerDescuentos(int) - end");
		}
		return descuentos;
	}
	
	/**
	 * Method aplicarDesctoPorDefecto.
	 * 		Permite aplicar el descuento por artículo defectuoso a un productoindicado. Se verifica si el usuario activo posee la autorización.
	 * 		Si no es así se solicita la misma.
	 * @param renglon Código del producto al que se le quiere aplicar el descuento.
	 * @param descto El descuento que se le quiere aplicar al producto. puede ser un porcentaje o un nuevo precio.
	 * @param cantidad
	 * @param esPorcentaje Boolean que indica si el descuento es un porcentaje o un nuevo precio.
	 * @throws ProductoExcepcion Si el producto indicado no existe
	 * @throws ExcepcionCr Si no existe venta activa o si la Tienda no permite realizar esta operación.
	 */
	public void aplicarDesctoPorDefecto(int renglon, double descto, float cantidad, boolean esPorcentaje)throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarDesctoPorDefecto(int, double, float, boolean) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "aplicarDesctoPorDefecto");
		String codAutorizante = null;
		DetalleTransaccion linea = null;
		
		if (Sesion.isCambiarPrecio()) {
			//Chequeos de datos necesarios antes de pedir la autorización. Es por eso que se realizan en la Máquina de estados
			//Observamos si el renglon del producto existe
			try {
				linea = (DetalleTransaccion) venta.getDetallesTransaccion().elementAt(renglon);
			} catch (Exception ex) {
				logger.error(
						"aplicarDesctoPorDefecto(int, double, float, boolean)",
						ex);

				 throw (new ProductoExcepcion("Error al obtener producto, no existe renglon " + renglon + " en la transaccion", ex));
			}
			//Obtenemos el producto
			Producto prod = linea.getProducto();
		
			if((!prod.isIndicaFraccion()) && (cantidad%1)!=0){
				throw (new ProductoExcepcion("El producto " + prod.getCodProducto() + " NO admite cantidades fraccionadas(decimales)"));
			}
		
			if(cantidad <= 0 || cantidad > linea.getCantidad()) {
				throw (new ProductoExcepcion("Cantidad de productos invalida. Debe ser menor o igual a la del renglon."));
			}
			
			if (esPorcentaje) {
				// Verificamos que el porcentaje a aplicar sea válido 
				// descto bajado a 99 por problemas en caja principal con transacciones en O
				if(descto > 99 || descto <= 0) {
					throw (new ProductoExcepcion("Valor de Porcentaje a aplicar inválido"));
				}
				// Verificamos que el descuento por defecto no haya sido aplicado
				if (venta.condicionVentaAplicada(renglon, Sesion.condicionDesctoPorDefecto)) {
					throw new ExcepcionCr("El producto ya contiene una rebaja en esta venta");
				}
			} else {
				//verificamos que el nuevo precio a aplicar sea válido
				if(descto <= 0 || descto >= linea.getPrecioFinal()) {
					throw (new ProductoExcepcion("Valor de Nuevo Precio a aplicar inválido"));
				}
				if (venta.condicionVentaAplicada(renglon, Sesion.condicionCambioPrecio)) {
					throw new ExcepcionCr("El producto ya contiene un cambio de precio en esta venta");
				}
			}
			// Verificamos si la función requiere autorización
			// Si es así realiza la operación si no es así lanza una excepción
			codAutorizante = CR.me.verificarAutorizacion ("FACTURACION","aplicarDesctoPorDefecto", this.venta.getCliente());
			this.venta.aplicarDesctoPorDefecto(renglon, descto,cantidad, esPorcentaje, codAutorizante, false);
		} else {
			throw (new MaquinaDeEstadoExcepcion("No se puede realizar la operacion. La tienda no tiene activa esta opcion"));				
		}
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);	

		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarDesctoPorDefecto(int, double, float, boolean) - end");
		}
	}
	
	/**
	 * Método anularVentaActiva.
	 * 		Anula la venta colocándola en nulo en la aplicación (No se registra en la Base de Datos).
	 * @throws BaseDeDatosExcepcion - Si ocurre un error con la conexion a la Base de Datos
	 * @throws MaquinaDeEstadoExcepcion - Si el estado es incorrecto para Anular la Venta Activa
	 * @throws ExcepcionCr - Si ocurre un error ingresando el registro de auditoria
	 */
	public void anularVentaActiva() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr, PagoExcepcion {
		
		Venta.donacionesRegistradas.clear();
		Venta.regalosRegistrados.clear();
		Venta.promocionesRegistradas.clear();
		CR.me.getPromoMontoCantidad().clear();
		
		boolean pagoCondicional = false;
		FormaDePago pago = null;
		
		if (logger.isDebugEnabled()) {
			logger.debug("anularVentaActiva() - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "anularVentaActiva");
		
		// Chequeamos que se puede finalizar la venta (Pagos completos)
		CR.me.verificarAutorizacion ("FACTURACION","anularVentaActiva", this.venta.getCliente());
		venta.anularVentaActiva();
		try{ CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ..."); }
		catch(Exception ex){
			logger.error("anularVentaActiva()", ex);
		}
	//	try{
		//Verificar si había pago con condicional
		int i = 0;
		for (i=0; (i < venta.getPagos().size()) && (pagoCondicional == false); i++) {
			pago = (FormaDePago)((Pago)venta.getPagos().elementAt(i)).getFormaPago();
			if (pago.isValidarSaldoCliente()){
				pagoCondicional = true; 
			}
		}
		if ((pagoCondicional)){//Si había pagos con condicional
			eliminarPagoCondicional((Pago)venta.getPagos().elementAt(i-1));
		}
		venta = null;
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("anularVentaActiva() - end");
		}
	/*	} catch(PagoExcepcion pe) {
			MensajesVentanas.aviso("No se puede anular la venta activa");
		}*/
	}
	
	/**
	 * Método eliminar pago con condicional
	 * 		Eliminar el pago con condicional al cliente asignado
	 */
	public void eliminarPagoCondicional (Pago pagoRealizado) throws PagoExcepcion
	{
		double monto = pagoRealizado.getMonto()*(-1);
		ManejoPagosFactory.getInstance().disminuirSaldoCliente(venta.getCliente(), monto);
	}
	
	/**
	 * Método asignarCliente
	 * 		Asigna el cliente indicado a la venta. Verifica posible autorización que se requiera 
	 * para la realización de esta función.
	 * @param codigoBarra
	 * @throws AfiliadoUsrExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	public void asignarCliente(String codigoBarra) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		this.asignarCliente(codigoBarra, true);
	}
	/**
	 * Método asignarCliente
	 * 		Asigna el cliente indicado a la venta. Verifica posible autorización que se requiera 
	 * para la realización de esta función.
	 * @param codigoBarra
	 * @throws AfiliadoUsrExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	public void asignarCliente(String codigoBarra, boolean dctoEmpleado) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("asignarCliente(String) - start");
		}
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "asignarCliente");
		boolean existiaVentaActiva = (venta != null) ? true : false;
	
		if (!existiaVentaActiva) {
			Sesion.getCaja().getNuevoNumRegCaja();
			venta = new Venta();
		}
		
		if(CR.meServ.getCotizacion() != null && existiaVentaActiva && CR.meServ.getCotizacion().isClienteOriginalDeCotizacion()) {
			try {
				MediadorBD.obtenerCliente(codigoBarra);
				MensajesVentanas.aviso("Para las cotizaciones no se puede cambiar el cliente asignado");
			} catch (ExcepcionCr e1) {
				logger.error("asignarCliente(String)", e1);

				throw new ExcepcionCr(e1.getMensaje());
			} catch (ConexionExcepcion e1) {
				logger.error("asignarCliente(String)", e1);

				throw e1;
			}
		} else {
			//Asignamos el cliente a la venta
			try {
				String autorizante = CR.me.verificarAutorizacion ("FACTURACION","asignarCliente");				
				if(venta.getCliente().getCodCliente() != null)
					if(!venta.getCliente().getCodCliente().equals(codigoBarra))
						this.borrarMensajesCliente();
				venta.asignarCliente(codigoBarra, autorizante);
				
				//Se verifica si el cliente se encuentra inactivo. Unicamente se muestra un mensaje para indicar la eventualidad
				if(venta.getCliente().getEstadoCliente() != Sesion.ACTIVO)
					CR.me.mostrarAviso("El cliente se encuentra inactivo", true);
					
				//Se envia al Visor la asignación del cliente
				String nombreCliente = venta.getCliente().getNombreCompleto();
				try{ CR.crVisor.enviarString("CLIENTE", 0, nombreCliente, 2); }
				catch(Exception ex){
					logger.error("asignarCliente(String)", ex);
}
			} catch (ConexionExcepcion e) {
				logger.error("asignarCliente(String)", e);

				if (!existiaVentaActiva) {
					venta = null;
					Sesion.getCaja().recuperarNumReg();
				}
				throw e;
			} catch (ExcepcionCr e) {
				logger.error("asignarCliente(String)", e);

				if (!existiaVentaActiva) {
					venta = null;
					Sesion.getCaja().recuperarNumReg();
				}
				throw e;
			}
				
			// Actualizamos el estado de la caja
			Sesion.getCaja().setEstado(edoFinalCaja);
				
			//Ahora verificamos si el cliente es exento de impuesto
			boolean continuar = false;
			try {
				this.facturarSinImpuesto();
				continuar = true;
			} catch (ExcepcionCr e1) {
				logger.error("asignarCliente(String)", e1);

				MensajesVentanas.mensajeError(e1.getMensaje());
				try {
					//Si falló el facturar sin impuesto igual se debe chequear la aplicaciòn del descto a COLABORADOR
					this.verificarDesctoEmpleado();
				} catch (ExcepcionCr e2) {
					logger.error("asignarCliente(String)", e2);

					MensajesVentanas.mensajeError(e2.getMensaje());
					throw e2;
				} catch (ConexionExcepcion e2) {
					logger.error("asignarCliente(String)", e2);

					MensajesVentanas.mensajeError(e2.getMensaje());
				}
			} catch (ConexionExcepcion e1) {
				logger.error("asignarCliente(String)", e1);

				// Si falló el facturar sin impuesto igual se debe chequear la aplicaciòn del descto a COLABORADOR
				MensajesVentanas.mensajeError(e1.getMensaje());
				this.verificarDesctoEmpleado();
			}
				
			if (continuar){ //Si el paso fue exitoso por la llamada a facturarSinImpuesto entonces se verifica el descto a COLABORADOR
				if (dctoEmpleado) {
					this.verificarDesctoEmpleado();
				}
				MediadorBD.setClienteNotificado(venta.getCliente().getCodCliente());
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("asignarCliente(String) - end");
		}
	}
	
	/**
	 * Método asignarCliente
	 * 		Asigna el cliente indicado a la venta. NO verifica posible autorización que se requiera 
	 * para la realización de esta función. UNICAMENTE PARA LAS CONSULTAS DE CONTIZACIONES FACTURADAS
	 * @param cliente
	 * @throws AfiliadoUsrExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	public void asignarCliente(Cliente cte) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("asignarCliente(Cliente) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "asignarCliente");
		boolean existiaVentaActiva = (venta != null) ? true : false;
	
		if (!existiaVentaActiva) {
			Sesion.getCaja().getNuevoNumRegCaja();
			venta = new Venta();
		}

		if (cte != null) {
			//Asignamos el cliente a la venta
			venta.setCliente(cte);
			
			//Se envia al Visor la asignación del cliente
			String nombreCliente = venta.getCliente().getNombreCompleto();
			try { CR.crVisor.enviarString("CLIENTE", 0, nombreCliente, 2); }
			catch (Exception e) {
				logger.error("asignarCliente(Cliente)", e);
}
		}

		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("asignarCliente(Cliente) - end");
		}
	}

	/**
	 * Método verificarDesctoEmpleado.
	 * 		Método que se encarga de verificar si el cliente previamente asignado a la venta
	 * 		es un COLABORADOR para entonces aplicar, o no, el descuento a COLABORADOR a los detalles.
	 * @throws 
	*/
	private void verificarDesctoEmpleado() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("verificarDesctoEmpleado() - start");
		}
		//No se puede aplicar descuento empleado si ya la venta posee un pago especial
		if(contienePagosBeco() && venta.getCliente().getTipoCliente() == Sesion.COLABORADOR){
			throw new ExcepcionCr("No se pueden hacer cambios a la transacción después de agregar pagos especiales.\nNo se aplicará el descuento a colaborador.\nDebe eliminar el pago promocional y agregarlo después de asignar el cliente");
		}
		
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "aplicardesctoempleado");
		boolean existiaVentaActiva = (venta != null) ? true : false;
		
		// Verificamos si el cliente asigando a la venta es un COLABORADOR
		
		//****** Fecha Actualización 02/03/2007
		//Cambio de BECO para arreglar que la autorización del descuento a empleado sólo se solicite
		//para los empleados y NO para el caso de los tipoCte='E' únicamente
		//*******
		if ((venta.getCliente().getCodCliente() != null) && (venta.getCliente().getTipoCliente() == Sesion.COLABORADOR) 
			&& (venta.getCliente().getEstadoColaborador() == Sesion.ACTIVO) && (venta.getCliente().getNumFicha() != null) && (venta.getCliente().getNumFicha() != "")) { 		
			try {
				String codAutorizante = CR.me.verificarAutorizacion ("FACTURACION","aplicardesctoempleado", this.venta.getCliente());
				venta.setCodAutorizante(codAutorizante);
				venta.actualizarPreciosDetalle(true);
			} catch (AutorizacionExcepcion  e) {
				logger.error("verificarDesctoEmpleado()", e);

				if (!existiaVentaActiva) {
					venta = null;
					Sesion.getCaja().recuperarNumReg();
				} else
					venta.actualizarPreciosDetalle(false);
				throw (e);
			}
		} else {
			try {
				venta.actualizarPreciosDetalle(false);
			} catch (ExcepcionCr  e) {
				logger.error("verificarDesctoEmpleado()", e);

				if (!existiaVentaActiva) {
					venta = null;
					Sesion.getCaja().recuperarNumReg();
				}
			}
		}
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("verificarDesctoEmpleado() - end");
		}
	}

	/**
	* Método quitarCliente
	* 		Permite quitar el cliente asignado a una venta. 
	* @return boolean
	 * @throws ExcepcionCr 
	*/
	public void quitarCliente() throws ConexionExcepcion, ExcepcionCr{
		boolean pagoCondicional = false;
		FormaDePago pago = null;
		//No se puede aplicar descuento empleado si ya la venta posee un pago especial
		if(contienePagosBeco() && venta.getCliente().getTipoCliente() == Sesion.COLABORADOR){
			throw new ExcepcionCr("No se pueden hacer cambios a la transacción después de agregar pagos especiales.\nDebe eliminar el pago promocional y agregarlo después de hacer los cambios");
		}
		for (int i=0; i<venta.getPagos().size(); i++) {
			pago = (FormaDePago)((Pago)venta.getPagos().elementAt(i)).getFormaPago();
			if (pago.isValidarSaldoCliente()){
				pagoCondicional = true; 	
			}
		}
		if (!pagoCondicional){//Verificar si había pagos con condicional
			//Eliminar el cliente
			Cliente cliente = new Cliente();
			venta.setCliente(cliente);
			//Si era empleado, eliminar el descuento de empleado
			venta.actualizarPreciosDetalle(false);
		} else {
			//No se puede eliminar el cliente
			MensajesVentanas.aviso("Debe eliminar el pago con condicional para quitar el cliente");
		}
	}
	
	/**
	* Método quitarCliente
	* 		Permite quitar el cliente asignado a una venta. 
	* @return boolean
	*/
	public void quitarClienteDevolucion() throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion{
		//Eliminar el cliente
		Cliente cliente = new Cliente();
		devolucion.setCliente(cliente);
	}
	
	/**
	 * Método facturarSinImpuesto.
	 * 		Verifica si el cliente de la venta es exento de impuesto. Si es así setea la variable de venta(cliete exento)
	 * 		a true.
	 * @throws 
	*/
	private void facturarSinImpuesto() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("facturarSinImpuesto() - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "facturarSinImpuesto");
				
		if(venta.getCliente().getCodCliente() != null && (venta.getCliente().isExento())) {
			//Verificamos la autorización de la función
			String codAutorizante = CR.me.verificarAutorizacion ("FACTURACION","facturarSinImpuesto", this.venta.getCliente());
			venta.setCodAutorizante(codAutorizante);
			venta.setVentaExenta(true);
		} else {
			venta.setVentaExenta(false);
		}
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("facturarSinImpuesto() - end");
		}
	}

	/**
	 * Método borrarMensajesCliente
	 * 
	 * 
	 */
	private void borrarMensajesCliente(){
		if (logger.isDebugEnabled()) {
			logger.debug("borrarMensajesCliente() - start");
		}

		if(CR.me != null)
			CR.me.borrarAvisos();

		if (logger.isDebugEnabled()) {
			logger.debug("borrarMensajesCliente() - end");
		}
	}
	
	/**
	 * Coloca las facturas en espera
	 * @param idEspera Codigo a colocar para la factura en espera
	 * @throws ExcepcionCr Si ocurre un error registrando la factura en espera
	 */
	public void colocarFacturaEspera(String idEspera) throws MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, PagoExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("colocarFacturaEspera(String) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "colocarFacturaEspera");
	
		// Colocamos la venta en Espera en el servidor central de la tienda
		Sesion.setUbicacion("FACTURACION","colocarfacturaespera");
		if (idEspera.length()>8)
			idEspera = idEspera.substring(0,8);
		Auditoria.registrarAuditoria("Colocada en Espera Codigo " + idEspera + ". Registro Inicial " + venta.getNumRegCajaInicia(),'T');
		venta.colocarEnEspera(idEspera);
		venta = null;
		try{ CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ..."); }
		catch(Exception ex){
			logger.error("colocarFacturaEspera(String)", ex);
}

		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("colocarFacturaEspera(String) - end");
		}
	}

	/**
	 * Recupera una factura en espera
	 * @param idEspera Codigo de la factura en espera a recuperar
	 * @throws ExcepcionCr Si ocurre un error en la recuperacion
	 */
	public void recuperarFacturaEspera(String idEspera) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("recuperarFacturaEspera(String) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "recuperarFacturaEspera");
		
		Sesion.setUbicacion("FACTURACION","recuperarFacturaEspera");
		if (idEspera.length()>8)
			idEspera = idEspera.substring(0,8);
		Auditoria.registrarAuditoria("Recuperando Factura en Espera " + idEspera + ".",'T');
		venta = new Venta(idEspera);
	
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		this.consultarMontoTransNoDonaciones(true);

		if (logger.isDebugEnabled()) {
			logger.debug("recuperarFacturaEspera(String) - end");
		}
	}
	
	/**
	 * Metodo que se encarga de devolver la cantidad de articulos que se ecuentran en la Transacción.
	 * Su función es para efectos de la interfaz, para poder mostrar la cantidad de artículos en pantalla.
	 * @return int - Cantidad de artículos presentes en la transacción
	 */
	public float obtenerCantidadProds() {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCantidadProds() - start");
		}
	
		float cantProductos = this.venta.obtenerCantidadProds();

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCantidadProds() - end");
		}
		return cantProductos;
	}
	
	/**
	 * Metodo que a partir de un codigo de producto devulve las posiciones del detalle de transaccion donde
	 * se encuentra dicho producto.
	 * @param codProducto Codigo del producto a buscar
	 * @return Vector - Vector donde cada posicion es un entero con la posicion del producto en el detalle 
	 * 					de transaccion. El primer renglon es el 0 
	 * @throws MaquinaDeEstadoExcepcion Si el estado de la caja es incorrecto
	 * @throws BaseDeDatosExcepcion Si ocurre un error en la conexion con la base de datos
	 * @throws ProductoExcepcion Si el producto no se encuentra en el detalle de la venta
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public Vector<Integer> obtenerRenglones(String codProducto, boolean isCodigoExterno) throws ProductoExcepcion, MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerRenglones(String) - start");
		}

		// Chequeamos el estado de la caja
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "obtenerRenglones");
	
		// Obtenemos el vector de renglones que poseen ese codigo de producto
		Vector<Integer> result = new Vector<Integer>();
		if (venta!=null)
			result = venta.obtenerRenglones(codProducto, isCodigoExterno);
		else
			if (devolucion!=null)
				result = this.devolucion.getVentaOriginal().obtenerRenglones(codProducto, isCodigoExterno);
	
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerRenglones(String) - end");
		}
		return result;
	}
	
	/**
	 * Método iniciarDevolucion
	 * 		Inicia una devolucion de producto.
	 * @param tienda - Tienda donde se realizo la venta original
	 * @param caja - Caja que realizo la venta original
	 * @param transaccion - Numero de factura original
	 */
	public void iniciarDevolucion(int tienda, String fechaSql, int caja, int transaccion) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("iniciarDevolucion(int, String, int, int) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "iniciarDevolucion");
		
		Sesion.setUbicacion("DEVOLUCION","iniciarDevolucion");
		
		String autorizante = CR.me.verificarAutorizacion("DEVOLUCION","iniciarDevolucion");
		
		Sesion.getCaja().getNuevoNumRegCaja();
		
		int i = 0;	
		try {
			
			InterfaceRemota objetoRemoto = null;
			try {
				objetoRemoto = (InterfaceRemota)Naming.lookup (Sesion.getRutaObjetoDevolucion());
			} catch (RemoteException e) {
				if (InitCR.preferenciasCR.getSystemOSName().toLowerCase().indexOf("linux") != -1) {
		        	// Error de Conexion, ubicamos archivo de configuracion de ruta RMI
					String ruta = null;
					File archivoRMI = new File("RMI.txt");
				    if (archivoRMI.exists()) {
						BufferedReader entrada = new BufferedReader(new FileReader(archivoRMI));
						ruta = entrada.readLine();
						entrada.close();
				    } else
				    	throw e;
				   
				    if (ruta != null && !ruta.equals("")) {
						String comando = new String("wget -O Servidor " + ruta);
						
						// Iniciamos Servicio RMI (HTTP)
						Process p = Runtime.getRuntime().exec(comando);

						// Esperamos hasta que el comando wget finalice
						int exitValue = -1;
						do {
							System.out.println(exitValue);
							try {
								Thread.sleep(1000);
								exitValue = p.exitValue();
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						} while (exitValue < 0);
				    }
	
					objetoRemoto = (InterfaceRemota)Naming.lookup (Sesion.getRutaObjetoDevolucion());
				} else
					throw e;
			}
			devolucion = objetoRemoto.obtenerDevolucion(tienda, fechaSql, caja, transaccion, autorizante,
			Sesion.getTienda().getNumero(), Sesion.getFechaSistema(), Sesion.getCaja().getNumero(),
			Sesion.getCaja().getNuevoNumRegCaja(), Sesion.getHoraSistema(), Sesion.getUsuarioActivo().getNumFicha());
			//Validar cliente que el cliente de la venta original sea diferente al cajero y al autorizante
			try {
				if (devolucion.getVentaOriginal().getCliente().getCodCliente() != null) {
					devolucion.getVentaOriginal().asignarCliente(devolucion.getVentaOriginal().getCliente().getCodCliente(),autorizante);
					devolucion.setCliente(devolucion.getVentaOriginal().getCliente());
				}
			} catch (ClienteExcepcion e1) {
				devolucion.getVentaOriginal().getCliente().setCodCliente(null);
			} 
		
			//Construir detalles de productos de la venta original
			//try{
				for (i = 0; i < devolucion.getVentaOriginal().getDetallesTransaccion().size(); i++)
				{
					Producto p = BaseDeDatosVenta.obtenerProducto(((DetalleTransaccion) devolucion.getVentaOriginal().getDetallesTransaccion().elementAt(i)).getProducto().getCodProducto(),devolucion.getVentaOriginal().getFechaTrans(),devolucion.getVentaOriginal().getHoraFin(), true);
					((DetalleTransaccion) devolucion.getVentaOriginal().getDetallesTransaccion().elementAt(i)).setProducto(p);
				}
			
		} catch (ProductoExcepcion e) {
			e.setMensaje("Código no registrado.\n Transmita el producto con código " + ((DetalleTransaccion) devolucion.getVentaOriginal().getDetallesTransaccion().elementAt(i)).getProducto().getCodProducto());
			CancelarDevolucion hilo = new CancelarDevolucion(tienda, fechaSql, caja, transaccion);
			hilo.start();
			throw e;
				
		} catch (ExcepcionCr e) {
			logger.error("iniciarDevolucion(int, String, int, int)", e);

			devolucion = null;
			throw e;
		} catch (ConexionExcepcion e) {
			logger.error("iniciarDevolucion(int, String, int, int)", e);

			devolucion = null;
			throw e;
		} catch (ConnectException e) {
			logger.error("iniciarDevolucion(int, String, int, int)", e);
			//e.printStackTrace();
			devolucion = null;
			throw new ConexionExcepcion("No se ha podido establecer conexión con el servidor.\n Comuníquese con el administrador de sistemas.");
		} catch (Exception e) {
			logger.error("iniciarDevolucion(int, String, int, int)", e);
			e.printStackTrace();
			devolucion = null;
			throw new ExcepcionCr("No se ha podido establecer conexión con el servidor.\n Comuníquese con el administrador de sistemas.");
		}
		Auditoria.registrarAuditoria("Realizando devolucion sobre transaccion " + transaccion + " de Caja " + caja + " Tienda " + tienda, 'T');
		try{ CR.crVisor.enviarString("NOTA DE CREDITO"); }
		catch(Exception ex){
			logger.error("iniciarDevolucion(int, String, int, int)", ex);
}
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("iniciarDevolucion(int, String, int, int) - end");
		}
	}
	
	public void cargarVentaOriginal () throws MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion
	{
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "iniciarVentaDeDevolucion");
//		Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);
	}
	
	/**
	 * Método devolverProducto
	 * 		Inicia una devolucion de producto. 
	 * @param renglonProd
	 * @param tipoCaptura
	 * @param cantidad
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 * void
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void devolverProducto(int renglonProd, String tipoCaptura, float cantidad) throws ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("devolverProducto(int, String, float) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "devolverProducto");

		Sesion.setUbicacion("DEVOLUCION","devolverProducto");
		CR.me.verificarAutorizacion("DEVOLUCION","devolverProducto", this.devolucion.getCliente());
		Vector<Object> descProd = devolucion.devolverProducto(renglonProd, tipoCaptura, cantidad);
		
		try{ CR.crVisor.enviarString((String)descProd.elementAt(0), 0, df.format(cantidad) + " X " + df.format((Double)descProd.elementAt(1)), 2); }
		catch(Exception ex){
			logger.error("devolverProducto(int, String, float)", ex);
}
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("devolverProducto(int, String, float) - end");
		}
	}


	/**
	 * Método devolverProducto
	 * 		Inicia una devolucion de producto. 
	 * @param renglonProd
	 * @param tipoCaptura
	 * @param cantidad
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 * void
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void devolverProductoXCambio(int renglonProd, String tipoCaptura, float cantidad) throws ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("devolverProducto(int, String, float) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "devolverProducto");

		Sesion.setUbicacion("DEVOLUCION","devolverProducto");
		CR.me.verificarAutorizacion("DEVOLUCION","devolverProducto", this.devolucion.getCliente());
		Vector<Object> descProd = devolucion.devolverProductoXCambio(renglonProd, tipoCaptura, cantidad);
		
		try{ CR.crVisor.enviarString((String)descProd.elementAt(0), 0, df.format(cantidad) + " X " + df.format((Double)descProd.elementAt(1)), 2); }
		catch(Exception ex){
			logger.error("devolverProducto(int, String, float)", ex);
}
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("devolverProducto(int, String, float) - end");
		}
	}



	/**
	 * Método getDevolucion
	 * 
	 * @return Devolucion
	 */
	public Devolucion getDevolucion() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDevolucion() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDevolucion() - end");
		}
		return devolucion;
	}

	/**
	 * Método finalizarDevolucion.
	 * 		Registra los datos finales de la devolucion (numeroTransaccion, 
	 * horaFin, etc) y la registra en la Base de Datos.
	 * @throws BaseDeDatosExcepcion - Si ocurre un error con la conexion 
	 * 		a la Base de Datos
	 * @throws MaquinaDeEstadoExcepcion - Si el estado es incorrecto para 
	 * 		Finalizar Devoluciones
	 */
	public void finalizarDevolucion() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ExcepcionCr, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion, PrinterNotConnectedException {
		if (logger.isDebugEnabled()) {
			logger.debug("finalizarDevolucion() - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "finalizarDevolucion");
		
		// Solicitar autorización para entrega de efectivo
		//CR.me.verificarAutorizacion("DEVOLUCION", "finalizarDevolucion");
		//Verificamos si existen detalles en la devolucion para poder finalizarla
		if (devolucion.getDetallesTransaccion().size()<=0) {
				throw (new MaquinaDeEstadoExcepcion("No puede finalizar la Devolucion. No existen detalles."));
		}

		if (transaccionPorImprimir != null) {
			throw (new MaquinaDeEstadoExcepcion("No puede finalizar la devolución. Hay una transacción pendiente por imprimir"));
		}
		
		
		String codAutorizante = CR.me.verificarAutorizacion("DEVOLUCION","finalizarDevolucion", this.devolucion.getCliente());
		if (codAutorizante != null) {
			devolucion.setCodAutorizante(codAutorizante);
		}
		devolucion.finalizarTransaccion();

		devolucion = null;
		try{ CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ..."); }
		catch(Exception ex){
			logger.error("finalizarDevolucion()", ex);
		}

		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("finalizarDevolucion() - end");
		}
	}

	/**
	 * Método finalizarDevolucion.
	 * 		Registra los datos finales de la devolucion (numeroTransaccion, 
	 * horaFin, etc) y la registra en la Base de Datos.
	 * @throws BaseDeDatosExcepcion - Si ocurre un error con la conexion 
	 * 		a la Base de Datos
	 * @throws MaquinaDeEstadoExcepcion - Si el estado es incorrecto para 
	 * 		Finalizar Devoluciones
	 */
	public void finalizarDevolucionXCambio() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ExcepcionCr, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion, PrinterNotConnectedException {
		if (logger.isDebugEnabled()) {
			logger.debug("finalizarDevolucion() - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "finalizarDevolucionPorCambio");
//		Si el vuelto es mayor al 10 % de la devoución solicito autorización
		String codAutorizante = null;
		//if (this.venta.getMontoVuelto() > 0 && ((this.devolucion.consultarMontoTrans()-this.venta.consultarMontoTrans()) > (this.devolucion.consultarMontoTrans()*0.1)))
		if (this.venta.getMontoVuelto() > 0 && ((this.devolucion.consultarMontoTrans()-this.venta.consultarMontoTrans()) > Sesion.getMontoMinimoDevolucion()))
			codAutorizante = CR.me.verificarAutorizacion("DEVOLUCION","finalizarDevolucionPorCambio", this.devolucion.getCliente());
		//Verificamos si existen detalles en la devolucion para poder finalizarla
		if (devolucion.getDetallesTransaccion().size()<=0) {
				throw (new MaquinaDeEstadoExcepcion("No puede finalizar la Devolucion. No existen detalles."));
		}

		if (transaccionPorImprimir != null) {
			throw (new MaquinaDeEstadoExcepcion("No puede finalizar la devolución. Hay una transacción pendiente por imprimir"));
		}
		
		
		//String codAutorizante = CR.me.verificarAutorizacion("DEVOLUCION","finalizarDevolucion", this.devolucion.getCliente());
		if (codAutorizante != null) {
			devolucion.setCodAutorizante(codAutorizante);
		}
		devolucion.finalizarTransaccion();
		
		Auditoria.registrarAuditoria("Finalizada devolución de transacción Nro. " + devolucion.getVentaOriginal().getNumTransaccion() , 'T');

		devolucion = null;
		try{ CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ..."); }
		catch(Exception ex){
			logger.error("finalizarDevolucion()", ex);
		}

		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("finalizarDevolucion() - end");
		}
	}



	/**
	/**
	 * Método anularDevolucionActiva.
	 * 		Anula la transaccion colocándola en nulo en la aplicación (No se registra en la Base de Datos).
	 * @throws BaseDeDatosExcepcion - Si ocurre un error con la conexion a la Base de Datos
	 * @throws MaquinaDeEstadoExcepcion - Si el estado es incorrecto para Anular la Venta Devolucion
	 * @throws ExcepcionCr - Si ocurre un error ingresando el registro de auditoria
	 */
	public void anularDevolucionActiva() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("anularDevolucionActiva() - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "anularDevolucionActiva");
		
		CR.me.verificarAutorizacion ("DEVOLUCION","anularDevolucionActiva", this.devolucion.getCliente());
		devolucion.anularDevolucionActiva();
		devolucion = null;
		try{ CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ..."); }
		catch(Exception ex){
			logger.error("anularDevolucionActiva()", ex);
}

		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("anularDevolucionActiva() - end");
		}
	}	

	/**
	 * Método devolucionTotal.
	 * 		Devuelve todos los productos que restan en la factura original.
	 * @throws BaseDeDatosExcepcion - Si ocurre un error con la conexion a la Base de Datos
	 * @throws MaquinaDeEstadoExcepcion - Si el estado es incorrecto para Anular la Venta Devolucion
	 * @throws ExcepcionCr - Si ocurre un error ingresando el registro de auditoria
	 */
	public void devolucionTotal() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("devolucionTotal() - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "devolverProducto");
		
		Sesion.setUbicacion("DEVOLUCION","devolverProducto");
		CR.me.verificarAutorizacion("DEVOLUCION","devolverProducto", this.devolucion.getCliente());
		int tamanoDetalles = devolucion.getVentaOriginal().getDetallesTransaccion().size();
		int pos = 0;
		while (tamanoDetalles>0) {
			try{
				devolucion.devolverProducto(pos, Sesion.capturaTeclado, ((DetalleTransaccion)devolucion.getVentaOriginal().getDetallesTransaccion().elementAt(pos)).getCantidad());
			} catch(ProductoExcepcion pe){
				pos++;
				MensajesVentanas.mensajeError(pe.getMensaje());
			}
			tamanoDetalles--;
			
		}

		try{ CR.crVisor.enviarString("NOTA DE CREDITO", 0, df.format(devolucion.consultarMontoTrans()), 2); }
		catch(Exception ex){
			logger.error("devolucionTotal()", ex);
}
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("devolucionTotal() - end");
		}
	}	

	/**
	 * Method anularProducto.
	 * 		Anula el producto indicado de la transaccion activa.
	 * @param renglon Código del producto a anular
	 * @param cantidad
	 * @throws BaseDeDatosExcepcion Si ocurre un error con la conexion 
	 * 		a la Base de Datos
	 * @throws ProductoExcepcion Si el producto insertado no existe
	 * @throws MaquinaDeEstadoExcepcion Si el estado es incorrecto para 
	 * 		Anular un producto
	 * @throws AutorizacionExcepcion Si no se tiene la autorización para realizar la operación
	 * @throws ExcepcionCr Si existe problema con la definición del método,módulo y/o función.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void anularProducto(int renglon, float cantidad) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("anularProducto(int, float) - start");
		}

		//No se puede aplicar descuento empleado si ya la venta posee un pago especial
		if(contienePagosBeco()){
			throw new ExcepcionCr("No se pueden hacer cambios a la transacción después de agregar pagos especiales.\nDebe eliminar el pago promocional y agregarlo después de hacer los cambios");
		}
		
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "anularProducto");
		
		// Verificamos si la función a la que pertenece este método necesita autorización
		// Primero Verificamos el módulo que está activo
	//	if (((venta != null) && (devolucion == null)) || ((venta != null) && (devolucion != null))) { // Estamos en el módulo de FACTURACION
		if (Sesion.getCaja().getEstado().equals("10") || Sesion.getCaja().getEstado().equals("21"))	{
			CR.me.verificarAutorizacion ("FACTURACION","anularProducto", this.venta.getCliente());		
			Vector<Object> detProd = venta.anularProducto(renglon, cantidad);
			try{ CR.crVisor.enviarString((String)detProd.elementAt(0), 0, "-" + df.format(cantidad) + " X " + df.format(((Double)detProd.elementAt(1)).doubleValue()), 2); }
			catch(Exception ex){
				logger.error("anularProducto(int, float)", ex);
			}
		} else if (Sesion.getCaja().getEstado().equals("12")){
		//	if (devolucion!= null) { // Estamos en el módulo de DEVOLUCION ------ OJO hay que chequear para otros tipos de transaccion (Anulacion)
			if (Sesion.getCaja().getEstado().equals("12")){
				CR.me.verificarAutorizacion ("DEVOLUCION","anularProducto", this.devolucion.getCliente());		
				Vector<Object> detProd = devolucion.anularProducto(renglon, cantidad);
				try{ CR.crVisor.enviarString((String)detProd.elementAt(0), 0, df.format(-(cantidad*((Double)detProd.elementAt(1)).doubleValue())), 2); }
				catch(Exception ex){
					logger.error("anularProducto(int, float)", ex);
				}
			}
		}else if(Sesion.getCaja().getEstado().equals("23")){
			CR.me.verificarAutorizacion ("FACTURACION","anularProducto", this.venta.getCliente());		
			String codProd = ((DetalleTransaccion)venta.getDetallesTransaccion().get(renglon)).getProducto().getCodProducto();
			double precio = ((DetalleTransaccion)venta.getDetallesTransaccion().get(renglon)).getPrecioFinal();
			Vector<Object> detProd = venta.anularProducto(renglon, cantidad);
			for(int j=0;j<abonosTotalesVentaLR.size();j++){
				String codProd2 = abonosTotalesVentaLR.get(j).getCodProducto(); 
				double precio2 = abonosTotalesVentaLR.get(j).getMontoBase();
				if(codProd.equals(codProd2) && precio==precio2){
					if(cantidad==abonosTotalesVentaLR.get(j).getCantidad()){
						abonosTotalesVentaLR.remove(j);
						break;
					}else if(cantidad==abonosTotalesVentaLR.get(j).getCantidad()){
					}
				}
			}
			try{ CR.crVisor.enviarString((String)detProd.elementAt(0), 0, "-" + df.format(cantidad) + " X " + df.format(((Double)detProd.elementAt(1)).doubleValue()), 2); }
			catch(Exception ex){
				logger.error("anularProducto(int, float)", ex);
			}
		}
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("anularProducto(int, float) - end");
		}
	}


	/**
		 * Method anularProducto.
		 * 		Anula el producto indicado de la transaccion activa.
		 * @param renglon Código del producto a anular
		 * @param cantidad
		 * @throws BaseDeDatosExcepcion Si ocurre un error con la conexion 
		 * 		a la Base de Datos
		 * @throws ProductoExcepcion Si el producto insertado no existe
		 * @throws MaquinaDeEstadoExcepcion Si el estado es incorrecto para 
		 * 		Anular un producto
		 * @throws AutorizacionExcepcion Si no se tiene la autorización para realizar la operación
		 * @throws ExcepcionCr Si existe problema con la definición del método,módulo y/o función.
		 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void anularProductoXCambio(int renglon, float cantidad) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("anularProducto(int, float) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "anularProducto");
		
			// Verificamos si la función a la que pertenece este método necesita autorización
			// Primero Verificamos el módulo que está activo
			if (devolucion!= null) { // Estamos en el módulo de DEVOLUCION ------ OJO hay que chequear para otros tipos de transaccion (Anulacion)
				CR.me.verificarAutorizacion ("DEVOLUCION","anularProducto", this.devolucion.getCliente());		
				Vector<Object> detProd = devolucion.anularProductoXCambio(renglon, cantidad);
				try{ CR.crVisor.enviarString((String)detProd.elementAt(0), 0, df.format(-(cantidad*((Double)detProd.elementAt(1)).doubleValue())), 2); }
				catch(Exception ex){
					logger.error("anularProducto(int, float)", ex);
				}
			}
		
		
			// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("anularProducto(int, float) - end");
		}
	}


	/**
	 * Método iniciarDevolucion
	 * 		Inicia una devolucion de producto.
	 * @param pagoInicial - Monto de la nueva Devolución
	 * @param montoAnterior - Monto de la Devolución con la selección anterior
	 * 
	 * 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void iniciarVentaDeDevolucion(double pagoInicial, double montoAnterior) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("iniciarDevolucion(int, String, int, int) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "iniciarVentaDeDevolucion");
	
		
		Sesion.getCaja().getNuevoNumRegCaja();
		if (venta == null){
			try {
				//String autorizante = CR.me.verificarAutorizacion ("FACTURACION","asignarCliente");
				venta = new Venta();
				try{
					double saldoAFavor = devolucion.consultarMontoTrans();
					//double saldoPromociones = calcularSaldoPromociones();
					venta.getPagos().addElement(ManejoPagosFactory.getInstance().realizarPagoDevolucion(saldoAFavor));
				} catch (PagoExcepcion e1){}	
				//devolucion.getPagos().addElement(ManejoPagos.realizarPagoDevolucion(devolucion.consultarMontoTrans()));
				if (devolucion.getVentaOriginal().getCliente().getCodCliente() != null) {
					boolean dctoEmp = false;
					
					venta.setCliente(devolucion.getVentaOriginal().getCliente());
					//venta.setAplicaDesctoEmpleado(devolucion.getVentaOriginal().isAplicaDesctoEmpleado());
					// Chequeamos los detalles de la venta original, si uno de ellos tiene Dcto a Empleado la nueva venta tambien
					Vector<String> condicionesEmpleado =  new Vector<String>();
					condicionesEmpleado.addElement(Sesion.condicionDesctoEmpleado);
					for (int i=0; i<devolucion.getDetallesTransaccion().size(); i++) {
						DetalleTransaccion detalleActual = (DetalleTransaccion) devolucion.getDetallesTransaccion().elementAt(i);
						if ( /*detalleActual.getCondicionVenta().equalsIgnoreCase(Sesion.condicionDesctoEmpleado) ||
							detalleActual.getCondicionVenta().equalsIgnoreCase(Sesion.condicionDesctoEmpProm) ||
							detalleActual.getCondicionVenta().equalsIgnoreCase(Sesion.condicionDesctoEmpCambPrec) ||
							detalleActual.getCondicionVenta().equalsIgnoreCase(Sesion.condicionDesctoEmpDesctoDefect) ||
							detalleActual.getCondicionVenta().equalsIgnoreCase(Sesion.condicionDesctoEmpPrecioGarantizado) ||
							detalleActual.getCondicionVenta().equalsIgnoreCase(Sesion.condicionEmpaquePromocionEmpleado) ||
							detalleActual.getCondicionVenta().equalsIgnoreCase(Sesion.condicionDesctoEmpleado)*/
							detalleActual.contieneAlgunaCondicion(condicionesEmpleado)) {
								dctoEmp = true;
								CR.meVenta.getDevolucion().getVentaOriginal().setAplicaDesctoEmpleado(true);
								break;							
						}
					}
					venta.actualizarPreciosDetalle(dctoEmp);
					//this.asignarCliente(devolucion.getVentaOriginal().getCliente().getCodCliente());
				}
				 try{
					 if (devolucion.getCliente().getCodCliente() != null){
						this.asignarCliente(devolucion.getCliente().getCodCliente(), false);
						//Se agregó está linea para que el cliente que participe en la nueva transaccion sea contribuyente wdiaz crm
						this.venta.getCliente().setContribuyente(true);} 
				 } catch (ClienteExcepcion exU){}
				
				//	venta.asignarCliente(devolucion.getVentaOriginal().getCliente().getCodCliente(),autorizante);*/
				//venta.setCliente(devolucion.getVentaOriginal().getCliente());
			} catch (ExcepcionCr e) {
				logger.error("iniciarDevolucion(int, String, int, int)", e);
	
				venta = null;
				throw e;
			} catch (ConexionExcepcion e) {
				logger.error("iniciarDevolucion(int, String, int, int)", e);

				venta = null;
				throw e;
			}
		} else
			this.actualizarMontoDevolucion(montoAnterior,pagoInicial);
		
		Auditoria.registrarAuditoria("Inicio de nueva venta para devolución por cambio por el monto " + pagoInicial, 'T');
		try{ CR.crVisor.enviarString("NOTA DE CREDITO"); }
		catch(Exception ex){
			logger.error("iniciarDevolucion(int, String, int, int)", ex);
}
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("iniciarDevolucion(int, String, int, int) - end");
		}
	}

	/**
	 * 	Actualizar el monto en efectivo disponible para la devolución por cambio
	 * @param montoInicial - Original
	 * @param nuevoMonto - Cambio de monto
	 */
	public void actualizarMontoDevolucion (double montoInicial, double nuevoMonto)
	{
		//ManejoPagos.eliminarPago(montoInicial,venta.getPagos(),venta.getCliente());
		
		ManejoPagosFactory.getInstance().recalcularPagoDevolucion(montoInicial,nuevoMonto,CR.meVenta.getVenta().getPagos());
	}

	/**
	 * Método iniciarAnulacion
	 * 		Inicia una anulacion de venta.
	 * @param tienda - Tienda donde se realizo la venta original
	 * @param caja - Caja que realizo la venta original
	 * @param transaccion - Numero de factura original
	 */
	public void iniciarAnulacion(int tienda, String fechaSql, int caja, int transaccion) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("iniciarAnulacion(int, String, int, int) - start");
		}

		this.iniciarAnulacion(tienda,fechaSql,caja,transaccion,null);

		if (logger.isDebugEnabled()) {
			logger.debug("iniciarAnulacion(int, String, int, int) - end");
		}
	}
	
	/**
	 * Método iniciarAnulacion
	 * 		Inicia una anulacion de venta. Este método recibe un boolean que indica 
	 * 		si es necesario verificar la autorización de esta función (Caso Reimpresiones)
	 * @param tienda - Tienda donde se realizo la venta original
	 * @param caja - Caja que realizo la venta original
	 * @param transaccion - Numero de factura original
	 * @param solicitarAutorizacion - indicador para chequear si se pide o no la autorización (Caso de Reimpresiones)
	 */
	private void iniciarAnulacion(int tienda, String fechaSql, int caja, int transaccion, String autorizante) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger
					.debug("iniciarAnulacion(int, String, int, int, String) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "iniciarAnulacion");
		
		Sesion.setUbicacion("ANULACION","iniciarAnulacion");
		
		// Se verifica la autorización
		if(autorizante == null)
			autorizante = CR.me.verificarAutorizacion("ANULACION","iniciarAnulacion");
		
		Sesion.getCaja().getNuevoNumRegCaja();
		try {
			anulacion = new Anulacion(tienda, fechaSql, caja, transaccion, autorizante);
		} catch (AnulacionExcepcion e) {
			logger.error("iniciarAnulacion(int, String, int, int, String)", e);

			anulacion = null;
			throw e;
		} catch (AfiliadoUsrExcepcion e) {
			logger.error("iniciarAnulacion(int, String, int, int, String)", e);

			anulacion = null;
			throw e;
		} catch (BaseDeDatosExcepcion e) {
			logger.error("iniciarAnulacion(int, String, int, int, String)", e);

			anulacion = null;
			throw e;
		} catch (ConexionExcepcion e) {
			logger.error("iniciarAnulacion(int, String, int, int, String)", e);

			anulacion = null;
			throw e;
		}
		Auditoria.registrarAuditoria("Realizando anulacion sobre transaccion " + transaccion + " de Caja " + caja + " Tienda " + tienda, 'T');
		
		try{ CR.crVisor.enviarString("ANULACION", 0, df.format(anulacion.consultarMontoTrans()), 2); }
		catch(Exception ex){
			logger.error("iniciarAnulacion(int, String, int, int, String)", ex);
		}
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);
		
		//MODULO PROMOCIONES: Asignarle a la anulacion tantas donaciones en negativo como tenia la venta original
		(new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().cancelarDonacionesAnulacion();

		if (logger.isDebugEnabled()) {
			logger
					.debug("iniciarAnulacion(int, String, int, int, String) - end");
		}
	}

	/**
	 * Método finalizarAnulacion
	 *		Registra los datos finales de la anulacion (numeroTransaccion, 
	 * horaFin, etc) y la registra en la Base de Datos. 
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	public void finalizarAnulacion() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ExcepcionCr, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion, PrinterNotConnectedException {
		if (logger.isDebugEnabled()) {
			logger.debug("finalizarAnulacion() - start");
		}

		this.finalizarAnulacion(null);
		//MODULO PROMOCIONES
		Anulacion.donacionesRegistradas.clear();
		Venta.donacionesRegistradas.clear();

		if (logger.isDebugEnabled()) {
			logger.debug("finalizarAnulacion() - end");
		}
	}
	
	/**
	 * Método finalizarAnulacion
	 *		Registra los datos finales de la anulacion (numeroTransaccion,
	 *		horaFin, etc) y la registra en la Base de Datos. Este método recibe un boolean que indica 
	 * 		si es necesario verificar la autorización de esta función (Caso Reimpresiones)
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	private void finalizarAnulacion(String autorizante) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ExcepcionCr, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion, PrinterNotConnectedException {
		if (logger.isDebugEnabled()) {
			logger.debug("finalizarAnulacion(String) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "finalizarAnulacion");
		
		//Verificamos si existen detalles en la anulacion para poder finalizarla
		if (anulacion.getDetallesTransaccion().size()<=0) {
			throw (new MaquinaDeEstadoExcepcion("No puede finalizar la Anulacion. No existen detalles."));
		}
		
		if (transaccionPorImprimir != null) {
			throw (new MaquinaDeEstadoExcepcion("No puede finalizar la anulación. Hay una transacción pendiente por imprimir"));
		}
		
		//Se verifica la autorización
		if(autorizante == null) {
			autorizante = CR.me.verificarAutorizacion("ANULACION","finalizarAnulacion", this.anulacion.getCliente());
		}
		if (autorizante != null) {
			anulacion.setCodAutorizante(autorizante);
		}

		anulacion.finalizarTransaccion();
		

		anulacion = null;
		try{ CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ..."); }
		catch(Exception ex){
			logger.error("finalizarAnulacion(String)", ex);
		}

		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("finalizarAnulacion(String) - end");
		}
	}

	/**
	 * Método getAnulacion
	 * 
	 * @return Anulacion
	 */
	public Anulacion getAnulacion() {
		if (logger.isDebugEnabled()) {
			logger.debug("getAnulacion() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getAnulacion() - end");
		}
		return anulacion;
	}

	/**
	 * Método anularAnulacionActiva.
	 * 		Anula la transaccion colocándola en nulo en la aplicación (No se registra en la Base de Datos).
	 * @throws BaseDeDatosExcepcion - Si ocurre un error con la conexion a la Base de Datos
	 * @throws MaquinaDeEstadoExcepcion - Si el estado es incorrecto para Anular la Venta Devolucion
	 * @throws ExcepcionCr - Si ocurre un error ingresando el registro de auditoria
	 */
	public void anularAnulacionActiva() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("anularAnulacionActiva() - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "anularAnulacionActiva");
		
		// Chequeamos que se puede finalizar la venta (Pagos completos)
		CR.me.verificarAutorizacion ("ANULACION","anularAnulacionActiva", this.anulacion.getCliente());
		anulacion.anularAnulacionActiva();
		anulacion = null;
		try{ CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ..."); }
		catch(Exception ex){
			logger.error("anularAnulacionActiva()", ex);
}

		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("anularAnulacionActiva() - end");
		}
	}
	
	/**
	 * Método crearVentaApartado
	 * 
	 * @param apartado
	 * @param montoPagado
	 * @param rem
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	public void crearVentaApartado(Apartado apartado, double montoPagado, double rem) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger
					.debug("crearVentaApartado(Apartado, double, double) - start");
		}

		// Chequeamos que se puede finalizar la venta (Pagos completos)
		String autorizante = CR.me.verificarAutorizacion ("FACTURACION","crearVentaApartado");
		Sesion.getCaja().getNuevoNumRegCaja();
		if (autorizante == null) {
			autorizante = apartado.getCodCajero();
		}
		Auditoria.registrarAuditoria(autorizante, apartado.getNumServicio(), 
				"Facturacion de apartado", "FACTURACION", "crearVentaApartado");
		
		venta = new Venta(apartado, montoPagado, rem);
		
		BaseDeDatosVenta.asignarVentaServicio(venta.getCodTienda(),apartado.getTipoServicio(),apartado.getNumServicio(),apartado.getFechaServicio(),venta.getNumTransaccion(),venta.getFechaTrans());
		this.venta = null;
		try{ CR.crVisor.enviarString("* CAJA ABIERTA *",
				"PROXIMO CLIENTE ..."); }
		catch(Exception ex){
			logger.error("crearVentaApartado(Apartado, double, double)", ex);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("crearVentaApartado(Apartado, double, double) - end");
		}
	}

	/**
	 * Método crearVentaApartado
	 * 
	 * @param apartado
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	public void crearVentaApartado(Apartado apartado) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("crearVentaApartado(Apartado) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "crearVentaApartado");
		
		// Chequeamos que se puede finalizar la venta (Pagos completos)
		String autorizante = CR.me.verificarAutorizacion ("FACTURACION","crearVentaApartado");
		Sesion.getCaja().getNuevoNumRegCaja();
		if (autorizante == null) {
			autorizante = apartado.getCodCajero();
		}
		Auditoria.registrarAuditoria(autorizante, apartado.getNumServicio(), 
				"Facturacion de apartado", "FACTURACION", "crearVentaApartado");

		venta = new Venta(apartado);
		
		BaseDeDatosVenta.asignarVentaServicio(venta.getCodTienda(),apartado.getTipoServicio(),apartado.getNumServicio(),apartado.getFechaServicio(),venta.getNumTransaccion(),venta.getFechaTrans());
		try{ CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ..."); }
		catch(Exception ex){
			logger.error("crearVentaApartado(Apartado)", ex);
}
		this.venta = null;
		CR.meServ.getApartado().setEstadoServicio(Sesion.APARTADO_FACTURADO);
		BaseDeDatosServicio.actualizarEdoServicio(apartado.getCodTienda(), apartado.getTipoServicio(), apartado.getNumServicio(), apartado.getFechaServicio(), apartado.getEstadoServicio(), apartado.getFechaVencimiento(),false);

		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("crearVentaApartado(Apartado) - end");
		}
	}

	/**
	 * Método recuperarComanda.
	 * 		Recupera las comandas electrónicas del día correspondientes al cliente indicado.
	 * @param identificador - Código para el criterio de búsqueda en la BD.
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws XmlExcepcion
	 * @throws FuncionExcepcion
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Vector<Object>> recuperarComanda(String identificador) throws MaquinaDeEstadoExcepcion, ProductoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("recuperarComanda(String) - start");
		}

		String edoFinalCaja = MaquinaDeEstado.getEstadoFinal("recuperarcomanda");
		Vector<Vector<Object>> comandas = null; 

		if(Sesion.isCajaEnLinea()){
			Sesion.setUbicacion("FACTURACION","recuperarcomanda");
			comandas = BaseDeDatosVenta.recuperarComanda(identificador, false);
			Auditoria.registrarAuditoria("Recuperando comandas del cliente " + identificador, 'T');

			boolean existiaVentaActiva = (venta != null) ? true : false;
			if (!existiaVentaActiva) {
				Sesion.getCaja().getNuevoNumRegCaja();
				venta = new Venta();
			}

			// Actualizamos el estado de la caja
			MaquinaDeEstado.setEstadoCaja(edoFinalCaja);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("recuperarComanda(String) - end");
		}
		return comandas;
	}

	/**
	 * Método recuperarComanda.
	 * 		Recupera las comandas electrónicas del día correspondientes al cliente indicado.
	 * @param identificador - Código para el criterio de búsqueda en la BD.
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws XmlExcepcion
	 * @throws FuncionExcepcion
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void agregarDetalleComanda(Vector<Vector<Object>> comandas) throws MaquinaDeEstadoExcepcion, ProductoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("agregarDetalleComanda(Vector) - start");
		}

		//Agrego todo el detalle a la transaccion
		float cantidad = 0;
		Iterator<Vector<Object>> ciclo = comandas.iterator();
		Vector<Object> detalle = new Vector<Object>();
		while (ciclo.hasNext()){
			detalle = ciclo.next();
			String codProducto = new String(detalle.elementAt(1).toString());
			codProducto = new String(Control.completarCodigo(codProducto));
			cantidad = Float.valueOf(detalle.elementAt(3).toString()).floatValue();
			this.ingresarLineaProducto(codProducto, null, Sesion.capturaProceso, true);
			if (cantidad > 1) cantidad = cantidad -1;
			this.venta.agregarCantidad(cantidad, this.venta.getDetallesTransaccion().size()-1);
		}
		if(comandas.size() > 0){
			BaseDeDatosVenta.actualizarDetalleComanda(comandas);
			Auditoria.registrarAuditoria("Comanda agregada a la transaccion " + venta.getNumRegCajaInicia(),'T');
		}
		if (venta.getDetallesTransaccion().size()>0) {
			try{ CR.crVisor.enviarString("COMANDA ELECTRONICA", 0, df.format(venta.consultarMontoTrans()), 2); }
			catch(Exception ex){
				logger.error("agregarDetalleComanda(Vector)", ex);
}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("agregarDetalleComanda(Vector) - end");
		}
	}
	
	/**
	 * Método facturarCotizacion
	 * 
	 * @param cotizacion
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	@SuppressWarnings("unchecked")
	public void facturarCotizacion(Cotizacion cotizacion) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("facturarCotizacion(Cotizacion) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "facturarCotizacion");
		
		// Chequeamos que se puede finalizar la venta (Pagos completos)
		Vector<Object> pago = ManejoPagosFactory.getInstance().realizarPago(MathUtil.roundDouble(cotizacion.consultarMontoServ() + CR.meVenta.consultarMontoTrans()),CR.meVenta.getVenta().getPagos(),cotizacion.getCliente(),0);
		if (((Double)pago.elementAt(1)).doubleValue() >= 0) {
			venta.facturarCotizacion(cotizacion, (Vector<Pago>)pago.elementAt(0), ((Double)pago.elementAt(1)).doubleValue());
			BaseDeDatosVenta.asignarVentaServicio(venta.getCodTienda(),cotizacion.getTipoServicio(),cotizacion.getNumServicio(),cotizacion.getFechaServicio(),venta.getNumTransaccion(),venta.getFechaTrans());
			Auditoria.registrarAuditoria("Facturada Cotizacion Nro. " + cotizacion.getNumServicio(),'T');
			BaseDeDatosServicio.actualizarEdoServicio(cotizacion.getCodTienda(),cotizacion.getTipoServicio(),cotizacion.getNumServicio(),cotizacion.getFechaServicio(),Sesion.COTIZACION_FACTURADA,false);

			this.venta = null;
		} else {
			throw new PagoExcepcion ("No se ha efectuado el pago de la cotizacion");
		}

		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("facturarCotizacion(Cotizacion) - end");
		}
	}
	
	/**
	 * Método registrarClienteTemporal
	 * 		Asigna el cliente indicado a la venta. Verifica posible autorización que se requiera 
	 * para la realización de esta función.
	 * @param nombre
	 * @param ci
	 * @param numTelf
	 * @param codArea
	 * @param tipoCliente
	 * @param contactar
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	public void asignarClienteNoAfiliado(String nombre, String apellido, String ci, String numTelf, String codArea, char tipoCliente, boolean contactar) throws ClienteExcepcion, UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger
					.debug("asignarClienteNoAfiliado(String, String, String, String, String, char, boolean) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "asignarClienteNoAfiliado");
		boolean existiaVentaActiva = (venta != null) ? true : false;
	
		if (!existiaVentaActiva) {
			Sesion.getCaja().getNuevoNumRegCaja();
			venta = new Venta();
		}
		if(CR.meServ.getCotizacion() != null && existiaVentaActiva && CR.meServ.getCotizacion().isClienteOriginalDeCotizacion()) {
			MensajesVentanas.aviso("Para las cotizaciones no se puede cambiar el cliente asignado");
		} else {
			try {
				
				//Se verifica la posible autorización que se requiera
				CR.me.verificarAutorizacion ("FACTURACION","asignarClienteNoAfiliado", this.venta.getCliente());
				
				//****** Fecha Actualización 07/02/2007
				//Cambio de BECO para agregar validaciones de RIF y CI para el SENIAT
				//*******
				Validaciones validador = new Validaciones();
				if(!validador.validarRifCedula(ci, tipoCliente)) {
					throw (new ClienteExcepcion("CI/RIF Inválido"));
				}
				//**************************
				
				//Creamos el cliente temporal para registrarlo y asignarlo a la venta activa
				Cliente clienteTemp = new Cliente(nombre, apellido, ci, numTelf,codArea, tipoCliente, contactar, Sesion.ACTIVO);
				
				if(venta.getCliente().getCodCliente() != null)
					if(!venta.getCliente().getCodCliente().equals(ci))
						this.borrarMensajesCliente();
				
				Auditoria.registrarAuditoria("Se asigna el cliente " + clienteTemp.getNombreCompleto() + " a la venta",'T');						
				//Asignamos el cliente temporal a la venta y se registra
				venta.asignarClienteNoAfiliado(clienteTemp);
				
				//Se verifica si el cliente se encuentra inactivo. Unicamente se muestra un mensaje para indicar la eventualidad
				if(venta.getCliente().getEstadoCliente() != Sesion.ACTIVO)
					CR.me.mostrarAviso("El cliente se encuentra inactivo", true);
				
				//Se envia al Visor la asignación del cliente
				String nombreCliente = venta.getCliente().getNombreCompleto();
				try{ CR.crVisor.enviarString("CLIENTE", 0, nombreCliente, 2); }
				catch(Exception ex){
					logger
							.error(
									"asignarClienteNoAfiliado(String, String, String, String, String, char, boolean)",
									ex);
}
				
				//Ahora se incializan las variables que dependian del cliente
				//Verificamos si el cliente es exento de impuesto
				//ESTO SE HACE PARA PODER ACTUALIZAR LOS RENGLONES EN CASO DE QUE SE HAYA 
				//ASIGNADO UN CLIENTE EXENTO ANTES Y LUEGO SE CAMBIA Y SE ASIGNA UN CLIENTE TEMPORAL, 
				//AQUÍ ES CAUNDO SE ACTUALIZA ESE CASO (YA NO SERÍA EXENTA LA VENTA)
				this.facturarSinImpuesto();
				
				//Se verifica si para el cliente asignado aplica el descuento a COLABORADOR.
				//ESTO SE HACE PARA PODER ACTUALIZAR LOS RENGLONES EN CASO DE QUE SE HAYA 
				//ASIGNADO UN CLIENTE COLABORADOR ANTES Y LUEGO SE CAMBIA Y SE ASIGNA UN CLIENTE TEMPORAL, 
				//AQUÍ ES CAUNDO SE ACTUALIZA ESE CASO (YA NO TENDRÍA EL DESCTO A COLABORADOR)
				this.verificarDesctoEmpleado();
			} catch (ExcepcionCr e) {
				logger
						.error(
								"asignarClienteNoAfiliado(String, String, String, String, String, char, boolean)",
								e);

				if (!existiaVentaActiva) {
					venta = null;
					Sesion.getCaja().recuperarNumReg();
				}
				throw e;
			} catch (ConexionExcepcion e) {
				logger
						.error(
								"asignarClienteNoAfiliado(String, String, String, String, String, char, boolean)",
								e);

				if (!existiaVentaActiva) {
					venta = null;
					Sesion.getCaja().recuperarNumReg();
				}
				throw e;
			}
			// Actualizamos el estado de la caja
			Sesion.getCaja().setEstado(edoFinalCaja);
		}

		if (logger.isDebugEnabled()) {
			logger
					.debug("asignarClienteNoAfiliado(String, String, String, String, String, char, boolean) - end");
		}
	}
	
	/**
	 * Método buscarClienteTemporal
	 * 		Busca el cliente indicado y lo retorna para validar su existencia
	 * @param ci
	 * @return Cliente
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	public Cliente buscarClienteTemporal(String ci, char tipoCte) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("buscarClienteTemporal(String) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "buscarClienteTemporal");
	
		if(this.getVenta() != null)
			CR.me.verificarAutorizacion ("FACTURACION","buscarClienteTemporal");
		if(this.getDevolucion() != null)
			CR.me.verificarAutorizacion ("DEVOLUCION","buscarClienteTemporal");

		//****** Fecha Actualización 21/02/2007
		//Cambio de BECO para agregar validaciones de RIF y CI para el SENIAT
		//*******
		Validaciones validador = new Validaciones();
		if(!validador.validarRifCedula(ci, tipoCte)) {
			throw (new ClienteExcepcion("CI/RIF Inválido"));
		}
		//**************************
		
		//Buscamos el cliente temporal en la BD
		Cliente clienteTemp = BaseDeDatosVenta.buscarClienteTemporal(ci);
		
		//Se registra la auditoria
		Auditoria.registrarAuditoria("Busqueda de posible Cliente No afiliado en la BD", 'T');
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);
		
		if (logger.isDebugEnabled()) {
			logger.debug("buscarClienteTemporal(String) - end");
		}
		return clienteTemp;
	}
		
	/**
	 * Method aplicarPrecioGarantizado.
	 * 		Permite aplicar el descuento por precio garantizado a un producto indicado. Se verifica si el usuario activo posee la autorización.
	 * 		Si no es así se solicita la misma.
	 * @param renglon Código del producto al que se le quiere aplicar el descuento.
	 * @param descto El descuento que se le quiere aplicar al producto. puede ser un porcentaje o un nuevo precio.
	 * @param cantidad
	 * @param esPorcentaje Boolean que indica si el descuento es un porcentaje o un nuevo precio.
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr Si no existe venta activa o si la Tienda no permite realizar esta operación.
	 */
	public void aplicarPrecioGarantizado(int renglon, double descto, float cantidad, boolean esPorcentaje)throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarPrecioGarantizado(int, double, float, boolean) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "aplicarPrecioGarantizado");
		String codAutorizante = null;
		DetalleTransaccion linea = null;
		
		if (Sesion.isCambiarPrecio()) {
			//Chequeos de datos necesarios antes de pedir la autorización. Es por eso que se realizan en la Máquina de estados
			//Observamos si el renglon del producto existe
			try {
				linea = (DetalleTransaccion) venta.getDetallesTransaccion().elementAt(renglon);
			} catch (Exception ex) {
				logger
						.error(
								"aplicarPrecioGarantizado(int, double, float, boolean)",
								ex);

				 throw (new ProductoExcepcion("Error al obtener producto, no existe renglon " + renglon + " en la transaccion", ex));
			}
			//Obtenemos el producto
			Producto prod = linea.getProducto();
		
			if((!prod.isIndicaFraccion()) && (cantidad%1)!=0){
				throw (new ProductoExcepcion("El producto " + prod.getCodProducto() + " NO admite cantidades fraccionadas(decimales)"));
			}
		
			if(cantidad <= 0 || cantidad > linea.getCantidad()) {
				throw (new ProductoExcepcion("Cantidad de productos invalida. Debe ser menor o igual a la del renglon."));
			}
			
			if (esPorcentaje) {
				// Verificamos que el porcentaje a aplicar sea válido
				if(descto > 100 || descto <= 0) {
					throw (new ProductoExcepcion("Valor de Porcentaje a aplicar inválido"));
				}
			} else {
				//verificamos que el nuevo precio a aplicar sea válido
				if(descto <= 0 || descto >= linea.getPrecioFinal()) {
					throw (new ProductoExcepcion("Valor de Nuevo Precio a aplicar inválido"));
				}
			}
			
			if (venta.condicionVentaAplicada(renglon, Sesion.condicionDesctoPrecioGarantizado)) {
				throw new ExcepcionCr("El producto ya contiene un cambio de precio garantizado en esta venta");
			}

			// Verificamos si la función requiere autorización
			// Si es así realiza la operación si no es así lanza una excepción
			codAutorizante = CR.me.verificarAutorizacion ("FACTURACION","aplicarPrecioGarantizado", this.venta.getCliente());
			this.venta.aplicarDesctoPorDefecto(renglon, descto,cantidad, esPorcentaje, codAutorizante, true);
		} else {
			throw (new MaquinaDeEstadoExcepcion("No se puede realizar la operacion. La tienda no tiene activa esta opcion"));				
		}
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);	

		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarPrecioGarantizado(int, double, float, boolean) - end");
		}
	}
	
	/**
	 * Método asignarClienteDevolucion
	 * 		Asigna el cliente indicado a la devolución. Este método es para el caso de los clientes NO Afiliados
	 * 		Con esto se pueden personalizar las notas de crédito.
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	public void asignarClienteDevolucion(String nombre, String apellido, String ci, String numTelf, String codArea, char tipoCliente, boolean contactar) throws ClienteExcepcion, UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger
					.debug("asignarClienteDevolucion(String, String, String, String, String, char, boolean) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "asignarClienteDevolucion");
			
		if(CR.meVenta.getDevolucion() != null && CR.meVenta.getDevolucion().getCliente().getCodCliente() != null && CR.meVenta.getDevolucion().isClienteOriginalDeVenta())
			MensajesVentanas.aviso("La devolución ya tiene un cliente asignado\nNo puede cambiarlo para la nota de crédito");
		else {
			//Se verifica la posible autorización que se requiera
			CR.me.verificarAutorizacion ("DEVOLUCION","asignarClienteDevolucion");
			
//			****** Fecha Actualización 21/02/2007
			//Cambio de BECO para agregar validaciones de RIF y CI para el SENIAT
			//*******
			Validaciones validador = new Validaciones();
			if(!validador.validarRifCedula(ci, tipoCliente)) {
				throw (new ClienteExcepcion("CI/RIF Inválido"));
			}
			//**************************
			
			//Creamos el cliente temporal para registrarlo y asignarlo a la devolución activa
			Cliente clienteTemp = new Cliente(nombre, apellido, ci, numTelf,codArea, tipoCliente, contactar,Sesion.ACTIVO);
			
//			Se arma el código del cliente correctamente (X-0000000)
			String idCliente = clienteTemp.getCodCliente();
			if ((clienteTemp.getTipoCliente() == Sesion.CLIENTE_VENEZOLANO) || (clienteTemp.getTipoCliente() == Sesion.CLIENTE_EXTRANJERO) || 
			   (clienteTemp.getTipoCliente() == Sesion.CLIENTE_PASAPORTE) || (clienteTemp.getTipoCliente() == Sesion.CLIENTE_NATURAL))	
				idCliente = clienteTemp.getTipoCliente() + "-" + clienteTemp.getCodCliente();
			else  {
				for (int i=0; i<(9-clienteTemp.getCodCliente().length()); i++){
					idCliente = "0" + idCliente; 
				}
				idCliente = clienteTemp.getTipoCliente() + "-" + idCliente;
			}
		
			clienteTemp.setCodCliente(idCliente);
				
			if(devolucion.getCliente().getCodCliente() != null)
				if(!devolucion.getCliente().getCodCliente().equals(ci))
					this.borrarMensajesCliente();
					
			//Asignamos el cliente temporal a la devolución y se registra
			devolucion.asignarCliente(clienteTemp);
	
			//Se verifica si el cliente se encuentra inactivo. Unicamente se muestra un mensaje para indicar la eventualidad
			if(devolucion.getCliente().getEstadoCliente() != Sesion.ACTIVO)
				CR.me.mostrarAviso("El cliente se encuentra inactivo", true);
	
			String nombreCliente = devolucion.getCliente().getNombreCompleto();
			Auditoria.registrarAuditoria("Se asigna el cliente " + nombreCliente + " a la devolución",'T');
			try{ CR.crVisor.enviarString("CLIENTE", 0, nombreCliente, 2); }
			catch(Exception ex){
				logger
						.error(
								"asignarClienteDevolucion(String, String, String, String, String, char, boolean)",
								ex);
}
				
			// Actualizamos el estado de la caja
			Sesion.getCaja().setEstado(edoFinalCaja);
		}

		if (logger.isDebugEnabled()) {
			logger
					.debug("asignarClienteDevolucion(String, String, String, String, String, char, boolean) - end");
		}
	}
	
	/**
	 * Método asignarClienteDevolucion
	 * 		Asigna el cliente indicado a la devolución. Este método es para el caso de los clientes Afiliados
	 * 		Con esto se pueden personalizar las notas de crédito.
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	public void asignarClienteDevolucion(String codigo) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("asignarClienteDevolucion(String) - start");
		}

		if(CR.meVenta.getDevolucion() != null && CR.meVenta.getDevolucion().getCliente().getCodCliente() != null && CR.meVenta.getDevolucion().isClienteOriginalDeVenta())
			MensajesVentanas.aviso("La devolución ya tiene un cliente asignado\nNo puede cambiarlo para la nota de crédito");
		else {
			//Asignamos el cliente a la venta
			String autorizante = CR.me.verificarAutorizacion ("DEVOLUCION","asignarClienteDevolucion");
			if(devolucion.getCliente().getCodCliente() != null)
				if(!devolucion.getCliente().getCodCliente().equals(codigo))
					this.borrarMensajesCliente();
			
			//Asignamos el cliente afiliado a la devolución y se registra
			devolucion.asignarCliente(codigo, autorizante);
			
			//Se verifica si el cliente se encuentra inactivo. Unicamente se muestra un mensaje para indicar la eventualidad
			if(devolucion.getCliente().getEstadoCliente() != Sesion.ACTIVO)
				CR.me.mostrarAviso("El cliente se encuentra inactivo", true);
				
			//Se envia al Visor la asignación del cliente
			String nombreCliente = devolucion.getCliente().getNombreCompleto();
			Auditoria.registrarAuditoria("Se asigna el cliente " + nombreCliente + " a la devolución",'T');
			try { CR.crVisor.enviarString("CLIENTE", 0, nombreCliente, 2); }
			catch (Exception e) {
				logger.error("asignarClienteDevolucion(String)", e);
}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("asignarClienteDevolucion(String) - end");
		}
	}
	
	/**
	 * Método reimprimirFactura
	 * 		Recupera y reimprime la última factura generada por la caja.
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void reimprimirFactura() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ExcepcionCr, ConexionExcepcion, PrinterNotConnectedException{
		if (logger.isDebugEnabled()) {
			logger.debug("reimprimirFactura() - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "reimprimirFactura");
	
		//Verificamos la autorización de la función
		String autorizante = CR.me.verificarAutorizacion ("UTILIDADES","reimprimirFactura");
		
		//Recuperamos de la Bd los datos de la última transacción realizada por la caja activa
		Vector<Vector<?>> datosUltimaVenta = BaseDeDatosVenta.obtenerUltimaVenta();
				
		//Se construye la instancia de venta con estos datos
		Venta vta = new Venta(datosUltimaVenta);
		//Se coloca al usuario como contribuyente, para la reimpresión de factura en la parte fiscal
		//modificado 09-04-2012
		vta.getCliente().setContribuyente(true);
		if (MensajesVentanas.preguntarSiNo("Desea Reimprimir la Transacción Nro " + vta.getNumTransaccion() + ".\n" +
											"Por un monto de " + df.format(vta.consultarMontoTrans()) + " " + Sesion.getTienda().getMonedaBase())==0){
			Auditoria.registrarAuditoria(autorizante, vta.getNumTransaccion(), "Reimpresión ultima factura", "UTILIDADES", "reimprimirFactura");
	
			//Registramos en la auditoria
			Sesion.setUbicacion("UTILIDADES","reimprimirFactura");
			Auditoria.registrarAuditoria("Reimprimiendo venta Nro." + vta.getNumTransaccion(),'T');
			
			//Se verifica si la venta se registró en la memoria fiscal
			//Si es así, se realiza una anulación y se crea una nueva venta a partir de la anterior
			//Si la venta no se registró en la memoria Fiscal entonces se reimprime la factura únicamente
			if(Sesion.impresoraFiscal) {
				//Obtenemos el próximo número Fiscal de la impresora para chequear con el registrado en la última venta realizada
				int numComprobanteF = Sesion.crFiscalPrinterOperations.proximoNumeroComprobanteFiscal();
				
				if (vta.getNumComprobanteFiscal() == numComprobanteF || vta.getNumComprobanteFiscal() == 0) {
					vta.setNumComprobanteFiscal(numComprobanteF);
					BaseDeDatosVenta.actualizarNumComprobanteFiscal(numComprobanteF, vta);
					
					try { CR.crVisor.enviarString("IMPRIMIENDO FACTURA"); }
					catch (Exception e) {
						logger.error("reimprimirFactura()", e);
					}			
					
	/*				*//**
					 * Bloque para manejar los contribuyentes ordinarios
					 * @author Victor Medina - linux@epa.com.ve
					 * @since Thu Mar 3 2005, 9:35AM
					 *//*
					try {
						if(InitCR.preferenciasCR.getConfigStringForParameter("facturacion","permitirContribuyentesOrdinarios").equals("S")){
							if(CR.meVenta.getVenta() != null){
								int respuesta = 0;
								respuesta = MensajesVentanas.preguntarSiNo("Deberia marcar al cliente de esta reimpresion\ncomo contribuyente ordinario?", false, false);				
								if(respuesta == 0){
									CR.meVenta.setContribuyenteOrdinario(true);
								}
							}
						}
					} catch (NoSuchNodeException e1) {
						logger.error("reimprimirFactura()", e1);
					} catch (UnidentifiedPreferenceException e1) {
						logger.error("reimprimirFactura()", e1);
					}				
	*/				
					ManejadorReportesFactory.getInstance().imprimirFacturaVenta(vta, true, CR.meVenta.esContribuyenteOrdinario);
				} else {
					// ********************** PROCESO DE ANULACIÓN *****************************
					//Registramos en la auditoria
					Sesion.setUbicacion("UTILIDADES","reimprimirFactura");
					Auditoria.registrarAuditoria("Anulando venta Nro." + vta.getNumTransaccion() + " para reimpresion",'T');
			
					//Luego de tener la venta recuperada se construye la anulación que iría asociada a la misma
					// Observamos que no se haya anulado la venta original
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					String fecha = df.format(vta.getFechaTrans());
					this.iniciarAnulacion(vta.getCodTienda(),fecha,vta.getNumCajaFinaliza(),vta.getNumTransaccion(),autorizante);
					
					
					//Se finaliza la anulación iniciada
					//CRFiscalPrinterOperations.setImpresionFiscalEnCurso(true);
					//MaquinaDeEstadoVenta.setDocumentoNoFiscalPorImprimir(true);
					this.finalizarAnulacion(autorizante);
					Anulacion.donacionesRegistradas.clear();
					//************************************************************************
									
					// Esperamos que finalice la impresión de la anulación antes de continuar con la venta
					while (CR.meVenta.getTransaccionPorImprimir()!=null || 
							CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
							MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()) {
						MensajesVentanas.aviso("La impresión de la anulación está en curso, \na continuación se imprimirá la nueva factura");
					}
					
					
					// ********************** PROCESO DE NUEVA VENTA ****************************		
					try { CR.crVisor.enviarString("* NUEVA FACTURA *"); }
					catch (Exception e) {
						logger.error("reimprimirFactura()", e);
					}
				
					Sesion.getCaja().getNuevoNumRegCaja();			
					this.venta = new Venta(vta);
				
					//Registramos en la auditoria
					Sesion.setUbicacion("UTILIDADES","reimprimirFactura");
					Auditoria.registrarAuditoria("Creando nueva venta para reimpresion",'T');
						
						
					/**
					 * Bloque para manejar los contribuyentes ordinarios
					 * @author Victor Medina - linux@epa.com.ve
					 * @since Thu Mar 3 2005, 9:35AM
					 */
					try {
						if(InitCR.preferenciasCR.getConfigStringForParameter("facturacion","permitirContribuyentesOrdinarios").equals("S")){
							if(CR.meVenta.getVenta() != null){
								int respuesta = 0;
								respuesta = MensajesVentanas.preguntarSiNo("Deberia marcar al cliente de esta\nreimpresion como contribuyente\nordinario?");				
								if(respuesta == 0){
									CR.meVenta.setContribuyenteOrdinario(true);
								}
							}
						}
					} catch (NoSuchNodeException e1) {
						logger.error("reimprimirFactura()", e1);
					} catch (UnidentifiedPreferenceException e1) {
						logger.error("reimprimirFactura()", e1);
					}				
	
					
					this.finalizarVenta(false);
					Venta.donacionesRegistradas.clear();
					Venta.regalosRegistrados.clear();
					Venta.promocionesRegistradas.clear();
					CR.me.getPromoMontoCantidad().clear();
					// ************************************************************************
				}
			} else {
				try { CR.crVisor.enviarString("* NUEVA FACTURA *"); }
				catch (Exception e) {
					logger.error("reimprimirFactura()", e);
				}
				ManejadorReportesFactory.getInstance().imprimirFacturaVenta(vta, true, CR.meVenta.getContribuyenteOrdinario());
			}
		}		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("reimprimirFactura() - end");
		}
	}
	public boolean isRenglonPermiteRebaja(int renglon){
		if (logger.isDebugEnabled()) {
			logger.debug("isRenglonPermiteRebaja(int) - start");
		}

		boolean returnboolean = ((DetalleTransaccion) getVenta()
				.getDetallesTransaccion().elementAt(renglon)).getProducto()
				.isPermiteRebaja();
		if (logger.isDebugEnabled()) {
			logger.debug("isRenglonPermiteRebaja(int) - end");
		}
		return returnboolean;
		
	}
	
	/**
	 * Cambia el status del contribuyente ordinario, 
	 * pro defecto siempre debe ser false a menos
	 * que el cliente diga lo contrario
	 * @param status
	 * @author vmedina
	 * @since  Mar 1, 2005
	 */
	public void setContribuyenteOrdinario(boolean status){
		if (logger.isDebugEnabled()) {
			logger.debug("setContribuyenteOrdinario(boolean) - start");
		}

		esContribuyenteOrdinario=status;

		if (logger.isDebugEnabled()) {
			logger.debug("setContribuyenteOrdinario(boolean) - end");
		}
	}	
	
	/**
	 * Devuelve el status del Contribuyente. Siempre debe
	 * ser false por defecto a menos que el cliente diga lo
	 * contrario
	 * @author vmedina
	 * @since Mar 1, 2005
	 */
	public boolean getContribuyenteOrdinario(){
		if (logger.isDebugEnabled()) {
			logger.debug("getContribuyenteOrdinario() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getContribuyenteOrdinario() - end");
		}
		return esContribuyenteOrdinario;
	}

	/**
	 * @return Devuelve transaccionPorImprimir.
	 */
	public Transaccion getTransaccionPorImprimir() {
		return transaccionPorImprimir;
	}
	/**
	 * @param transaccionPorImprimir El transaccionPorImprimir a establecer.
	 */
	public void setTransaccionPorImprimir(Transaccion transaccionPorImprimir) {
		this.transaccionPorImprimir = transaccionPorImprimir;
	}
	/**
	 * @param devolucion
	 */
	public void setDevolucion(Devolucion devol, String usuarioActivo) {
		this.devolucion = devol;
		if (devolucion != null) {
			try {
				CR.meVenta.cargarVentaOriginal();
			} catch (MaquinaDeEstadoExcepcion e) {
				devolucion = null;
				e.printStackTrace();
			} catch (XmlExcepcion e) {
				devolucion = null;
				e.printStackTrace();
			} catch (FuncionExcepcion e) {
				devolucion = null;
				e.printStackTrace();
			} catch (BaseDeDatosExcepcion e) {
				devolucion = null;
				e.printStackTrace();
			} catch (ConexionExcepcion e) {
				devolucion = null;
				e.printStackTrace();
			}
			devolucion.setCodCajero(usuarioActivo);
		}
		
	}

	/**
	 * @param devolucion
	 */
	public void setDevolucion(Devolucion devolucion) {
		setDevolucion(devolucion, null);
	}

	public void colocarDevolucionEnEspera () throws SQLException, UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr
	{
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "colocarDevolucionEnEspera");
	
		Auditoria.registrarAuditoria("Se colocará en espera la devolución de transaccion Nro." + devolucion.getVentaOriginal().getNumTransaccion() + " del día" + devolucion.getVentaOriginal().getFechaTrans(),'T');
		BaseDeDatosVenta.devolucionEnEspera(devolucion, venta);
		Auditoria.registrarAuditoria("Colocada en espera la devolución de transaccion Nro." + devolucion.getVentaOriginal().getNumTransaccion() + " del día" + devolucion.getVentaOriginal().getFechaTrans(),'T');
		devolucion = null;
		venta = null;
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);
		
	}
	
	public void recuperarDevolucionEnEspera (int tienda, String fecha, int caja, int transaccion) throws SQLException, UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr
		{
			String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "recuperarDevolucionEnEspera"); 
			
			Auditoria.registrarAuditoria("Intento de recuperación de devolución de transaccion Nro." + transaccion + " del día" + fecha,'T');
			BaseDeDatosVenta.recuperarDevolucionEnEspera(tienda, fecha, caja, transaccion);
			Auditoria.registrarAuditoria("Recuperada devolución de transaccion Nro." + transaccion + " del día" + fecha,'T');			
//			Actualizamos el estado de la caja
			Sesion.getCaja().setEstado(edoFinalCaja);
			
		
		}

	/**
	 * @param venta
	 */
	public void setVenta(Venta venta) {
		this.venta = venta;
	}
	
	/**
	 * @param venta
	 */
	public void setVenta(Venta venta, String usuarioActivo) {
		this.venta = venta;
		if (venta!=null) this.venta.setCodCajero(usuarioActivo);
	}

	public void volverAFacturacion() throws BaseDeDatosExcepcion, ConexionExcepcion{
					
//		Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(Sesion.FACTURANDO);
	}
	
	/**
	 * Método finalizarVenta.
	 * 		Registra los datos finales de la venta (numeroTransaccion, 
	 * horaFin, etc) y la registra en la Base de Datos.
	 * 		Este método recibe un boolean que indica si se requiere chequeo de estados o no (Caso NO en reimpresion de factura)
	 * @throws BaseDeDatosExcepcion - Si ocurre un error con la conexion 
	 * 		a la Base de Datos
	 * @throws MaquinaDeEstadoExcepcion - Si el estado es incorrecto para 
	 * 		Finalizar Ventas
	 * @throws PagoExcepcion - Si no se han completado los pagos de la 
	 * 		transacción
	 */
	public int finalizarVentaListaRegalos(String nomInvitado,String dedicatoria) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ExcepcionCr, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion, PrinterNotConnectedException {
		if (logger.isDebugEnabled()) {
			logger.debug("finalizarVenta(boolean) - start");
		}
		Auditoria.registrarAuditoria("Finalizando Venta de Lista de Regalos",'T');

		int numTransaccion = 0;
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "finalizarVentaListaRegalos");
		
		//Verificamos si existen detalles en la venta para poder finalizarla
		if (venta.getDetallesTransaccion().size()<=0) {
			throw (new MaquinaDeEstadoExcepcion("No puede finalizar la venta No existen detalles."));
		}
		
		if (transaccionPorImprimir != null) {
			throw (new MaquinaDeEstadoExcepcion("No puede finalizar la venta. Hay una transacción pendiente por imprimir"));
		}
		
		// Chequeamos que se puede finalizar la venta (Pagos completos)
		double monto = venta.consultarMontoTrans();
			
		if (monto - venta.obtenerMontoPagado() < 0.01) {
			// Los pagos estan completos. Finalizamos la venta
			CR.me.verificarAutorizacion ("FACTURACION","finalizarVenta", this.venta.getCliente());
			
			venta.finalizarTransaccion();
			numTransaccion = venta.getNumTransaccion();
			CR.meServ.registrarVentaLR(numTransaccion,this.consultarMontoTrans(),this.getVenta().getCliente(),
								nomInvitado,dedicatoria,this.getVenta().getDetallesTransaccion());
			try{ CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ..."); }
			catch(Exception ex){
				logger.error("finalizarVenta(boolean)", ex);
			}
			venta = null;
		} else {
			// No se ha pagado completamente. Lanzamos una Excepcion
			throw (new PagoExcepcion("No se puede finalizar la venta. El monto del pago no abarca el total de la venta"));
		}

		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("finalizarVenta(boolean) - end");
		}
		return numTransaccion;
	}

	/**
	 * @param venta
	 * @param venta2
	 */
	public void crearVentaCierreLR() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		Sesion.getCaja().getNuevoNumRegCaja();
		venta = new Venta();
		this.venta.concatenarVenta(CR.meServ.getListaRegalos().getVentaParcialDeCierre1());
		this.venta.concatenarVenta(CR.meServ.getListaRegalos().getVentaParcialDeCierre2());
		this.venta.concatenarVenta(CR.meServ.getListaRegalos().getVentaParcialDeCierre3());
		this.venta.setCliente(CR.meServ.getListaRegalos().getTitular());
		this.venta.getCliente().setContribuyente(true);
	}

	public void crearVenta() throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion{
		venta = new Venta();
	}
	
	/**
	 * Método ingresarLineaProducto
	 * 		Ingresa una nueva linea en el detalle de una venta. Si la venta no esta activa se crea la venta.
	 * @param codProd Codigo del producto a insertar
	 * @param codVendedor Codigo del vendedor (Si aplica)
	 * @param tipoCaptura Tipo de captura del producto (Teclado, Dispositivo)
	 * @throws BaseDeDatosExcepcion - Si ocurre un error con la conexion a 
	 * 		la Base de Datos
	 * @throws MaquinaDeEstadoExcepcion - Si el estado es incorrecto para 
	 * 		Ingresar Producto
	 * @throws ProductoExcepcion - Si el producto insertado no existe
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unused")
	public void ingresarLineaProductoVentaLR(String codProd, String tipoCaptura, int pantallaCierre) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("ingresarLineaProducto(String, String, String) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "ingresarLineaProducto");
		boolean existiaVentaActiva = (venta != null) ? true : false;

		Sesion.setUbicacion("FACTURACION","ingresarLineaProducto");
		if (!existiaVentaActiva) {
			Sesion.getCaja().getNuevoNumRegCaja();
			venta = new Venta();
		}
		try {
			CR.me.verificarAutorizacion ("FACTURACION","ingresarLineaProducto", this.venta.getCliente());
			this.venta.ingresarLineaProducto(codProd, null, tipoCaptura.toUpperCase(), false);

			/** Verificar funcionamiento de estas modificaciones
			/** Modificación para ingresar productos en Lista de Regalos garantizando mejor precio**/
			if(CR.meServ.getListaRegalos().getTipoLista()==ListaRegalos.GARANTIZADA){ // Lista de Regalos Garantizada
				if(Sesion.getCaja().getEstado().equals("23")){ // Estado: Cierre de Lista de Regalos
					if(pantallaCierre==1) { // Se quiere precio minimo entre Abono Total y precio al momento del cierre
						// Primero se extraen las operaciones de Abonos Totales que hay sobre el producto
						Vector<OperacionLR> temp = CR.meServ.getListaRegalos().getOperacionesAbonosTotales();
						Vector<OperacionLR> abonosTotales = new Vector<OperacionLR>();
						for(int i=0;i<temp.size();i++){
							OperacionLR op = temp.get(i);
							if(op.getCodProducto().equals(codProd))
								abonosTotales.add(op);
						}
						
						// Luego se identifica el de menor precio, se elimina de las operaciones, se agrega al temporal
						// de operaciones y se agrega a la venta
						//DetalleTransaccion dettran = (DetalleTransaccion)this.venta.getDetallesTransaccion().lastElement();
						double precioMinimo = abonosTotales.get(0).getMontoBase();
						int posicion = 0;
						for(int i=1;i<abonosTotales.size();i++){
							OperacionLR operacion = abonosTotales.get(i);
							if(operacion.getMontoBase()<precioMinimo){
								posicion = i;
								precioMinimo = operacion.getMontoBase();
							}
						}
//						if(precioMinimo-1 < dettran.getPrecioFinal()*(1+(dettran.getProducto().getImpuesto().getPorcentaje()/100))){
//							dettran.setPrecioFinal(precioMinimo);
//							dettran.setCondicionVenta(Sesion.condicionPromocion);
//							this.venta.actualizarMontoTransaccion();
//							abonosTotalesVentaLR.add((OperacionLR)abonosTotales.get(posicion));
//							abonosTotales.remove(posicion);
//						}
					}else if(pantallaCierre==2){ // Coloco 5% de descuento sobre productos no vendidos de la lista que quieran llevarse
						DetalleTransaccion dettran = (DetalleTransaccion)this.venta.getDetallesTransaccion().lastElement();
						dettran.setPrecioFinal(dettran.getPrecioFinal()*0.95);
						dettran.setMontoImpuesto(this.venta.calcularImpuesto(dettran.getProducto(),dettran.getPrecioFinal()));
						dettran.setCondicionVenta(Sesion.condicionPromocion);
						this.venta.actualizarMontoTransaccion();
					}
				}
			}
			/** Fin Modificación **/
			
			try {
				if(InitCR.preferenciasCR.getConfigStringForParameter("sistema","estiloFactura").equals("CR")){
					/**
					 * Costa Rica Style
					 */					
					try{ 
						CR.crVisor.enviarString(((DetalleTransaccion)venta.getDetallesTransaccion().lastElement()).getProducto().getDescripcionCorta(),0,df.format(((DetalleTransaccion)venta.getDetallesTransaccion().lastElement()).getPrecioFinal()+((DetalleTransaccion)venta.getDetallesTransaccion().lastElement()).getMontoImpuesto()),2); 
					}
					catch(Exception ex){
						logger.error("ingresarLineaProducto(String, String, String)",ex);
					}
				}
				else{
					/**
					 * Venezuela Style
					 */
					try{ 
						CR.crVisor.enviarString(((DetalleTransaccion)venta.getDetallesTransaccion().lastElement()).getProducto().getDescripcionCorta(),0,df.format(((DetalleTransaccion)venta.getDetallesTransaccion().lastElement()).getPrecioFinal()+((DetalleTransaccion)venta.getDetallesTransaccion().lastElement()).getMontoImpuesto()),2); 
					}
					catch(Exception ex){
						logger.warn("ingresarLineaProducto(String, String, String)",ex);
					}
				}
			} catch (NoSuchNodeException e1) {
				logger.error("ingresarLineaProducto(String, String, String)",
						e1);
			} catch (UnidentifiedPreferenceException e1) {
				logger.error("ingresarLineaProducto(String, String, String)",
						e1);
			}
			
			
		} catch (BaseDeDatosExcepcion e) {
			logger.error("ingresarLineaProducto(String, String, String)", e);

			if (!existiaVentaActiva) {
				venta = null;
				Sesion.getCaja().recuperarNumReg();
			}
			throw e;
		} catch (ProductoExcepcion e) {
			logger.error("ingresarLineaProducto(String, String, String)", e);

			if (!existiaVentaActiva) {
				venta = null;
				Sesion.getCaja().recuperarNumReg();
			}
			throw e;
		}
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("ingresarLineaProducto(String, String, String) - end");
		}
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<String> getDevolucionesEnEspera() throws BaseDeDatosExcepcion, SQLException, ConexionExcepcion{
		Vector<String> devoluciones = new Vector<String>();
		if(Sesion.isCajaEnLinea()){
			 devoluciones = BaseDeDatosVenta.getDevolucionesEnEspera(); 
		}
		return devoluciones;
	}
	
	/**
	 * Inicializa el actualizador de precios tomado de lo definido en las
	 * extensiones a utilizar
	 * @author jgraterol
	 * **/
	public void iniciarActualizadorPrecios(){
		if (logger.isDebugEnabled()) {
			logger.debug("iniciarBuscador() - start");
		}

		ActualizadorPreciosFactory  factory = new ActualizadorPreciosFactory();
		actualizadorPrecios = factory.getActualizadorPreciosInstance();		
		if (logger.isDebugEnabled()) {
			logger.debug("iniciarBuscador() - end");
		}	
	}
	
	/**
	 * Solicita autorizacion para agregar cupones de descuento
	 * @throws ExcepcionCr 
	 * @throws ConexionExcepcion 
	 * @throws MaquinaDeEstadoExcepcion 
	 * @throws UsuarioExcepcion 
	 * **/
	public void agregarCuponesDescuento() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("agregarCuponesDescuento() - start");
		}
		CR.me.verificarAutorizacion("FACTURACION", "agregarCuponesDescuento");
		if (logger.isDebugEnabled()) {
			logger.debug("agregarCuponesDescuento() - end");
		}
	}

	/**
	 * Solicita autorizacion para agregar una promoción corporativa a la venta
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	public void agregarPromocionCorporativa() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("agregarPromocionCorporativa() - start");
		}
		CR.me.verificarAutorizacion("FACTURACION", "agregarPromocionCorporativa");
		
		if (logger.isDebugEnabled()) {
			logger.debug("agregarPromocionCorporativa() - end");
		}
	}
	
	/**
	 * @return el productosPatrocinantes
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en 'TreeMap'
	* Fecha: agosto 2011
	*/
	public TreeMap<PromocionExt,Vector<Producto>> getProductosPatrocinantes() {
		return productosPatrocinantes;
	}

	/**
	 * @param productosPatrocinantes el productosPatrocinantes a establecer
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Treemap'
	* Fecha: agosto 2011
	*/
	public void setProductosPatrocinantes(TreeMap<PromocionExt, Vector<Producto>> productosPatrocinantes) {
		this.productosPatrocinantes = productosPatrocinantes;
	}

	/**
	 * @return el productosComplementario
	 */
	/*public HashMap getProductosComplementario() {
		return ProductosComplementario;
	}

	/**
	 * @param productosComplementario el productosComplementario a establecer
	 */
	/*public void setProductosComplementario(HashMap productosComplementario) {
		ProductosComplementario = productosComplementario;
	}
	
	/**
	 * Deshace las promociones que se crean al totalizar
	 * @param pagosRealizados
	 * @param pagosRealizadosAnteriormente
	 */	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void deshacerPromociones(Vector<Pago> pagosRealizados, Vector<Pago> pagosRealizadosAnteriormente){
		if(Venta.donacionesRegistradas.size()!=0 && pagosRealizados.size()==0){
			Venta.donacionesRegistradas.clear();
			MensajesVentanas.aviso("Debe volver a registrar las donaciones");
			CR.me.mostrarAviso("Eliminadas todas las donaciones de la venta", true);
		}
		Venta.regalosRegistrados.clear();
		Venta.promocionesRegistradas.clear();
		Venta.donacionesRegistradas.clear();
		CR.me.getPromoMontoCantidad().clear();
		pagosRealizadosAnteriormente.clear();
		for (int i=0; i<pagosRealizados.size(); i++)
			pagosRealizadosAnteriormente.addElement(pagosRealizados.elementAt(i));
		
		try {
			CR.meVenta.getVenta().actualizarPreciosDetalle(venta.isAplicaDesctoEmpleado());
		} catch (XmlExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (FuncionExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (BaseDeDatosExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (ConexionExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
	}
	
	/**
	 * Revisa todas las promociones aplicables al momento de totalizar una venta
	 * @author jgraterol
	 */
	public void ejecucionPromociones(Venta venta){
		if (actualizadorPrecios==null)iniciarActualizadorPrecios();
		actualizadorPrecios.ejecutarAhorroEnCompra(venta);
		actualizadorPrecios.llamadaDeRegalos(1, venta);
		//actualizadorPrecios.ejecutarProductoComplementario(1, venta);
		actualizadorPrecios.ejecutarProductoGratis(venta);
		actualizadorPrecios.revisarPromociones(venta);
		actualizadorPrecios.agregarPagosEspeciales(venta);
	}

	/**
	 * Revisa todas las promociones aplicables al momento de totalizar
	 * @author mmiyazono
	 */
	public void ejecucionPromocionesPDA(){
		if (actualizadorPrecios==null)iniciarActualizadorPrecios();
		actualizadorPrecios.ejecutarAhorroEnCompra(getVenta());  
		actualizadorPrecios.ejecutarProductoGratis(getVenta());
		actualizadorPrecios.revisarPromociones(getVenta());
		actualizadorPrecios.agregarPagosEspeciales(getVenta());
	}
	
	/**
	 * Método consultarMontoTrans
	 * 		Obtiene el subtotal de la transaccion.
	 * 		Sin solicitar donaciones para sumar a este total
	 * @return double - El monto actual de la transaccion
	 * @throws BaseDeDatosExcepcion - Si ocurre un error con la conexion 
	 * 		a la Base de Datos
	 * @throws MaquinaDeEstadoExcepcion - Si el estado es incorrecto para 
	 * 		Consultar SubTotal
	 */
	public double consultarMontoTransNoDonaciones (boolean mostrarSubTotalVisor) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("consultarMontoTrans(boolean) - start");
		}

		double subTotal = 0;
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "consultarSubTotal");
		
		CR.me.verificarAutorizacion ("FACTURACION","consultarSubTotal", this.venta.getCliente());
		subTotal = venta.consultarMontoTrans();
		
		if (mostrarSubTotalVisor){
			try{ CR.crVisor.enviarString("TOTAL", 0, df.format(subTotal), 2); }
			catch(Exception ex){
				logger.error("consultarMontoTrans(boolean)", ex);
			}
		}
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("consultarMontoTrans(boolean) - end");
		}
		return subTotal;
	}
	
	/**
	 * Indica si la venta posee un contribuyente
	 * @return
	 */
	public boolean isClienteContribuyente(Venta venta){
		return (new RegistroClienteFactory()).getInstance().isContribuyente(venta);
	}
	
	public boolean contienePagosBeco(){
		for(int i=0;venta!=null && i<venta.getPagos().size();i++){
			if(((Pago)venta.getPagos().elementAt(i)).getFormaPago().getTipo()==Sesion.TIPO_PAGO_BECO){
				return true;
			}
		}
		return false;
	}

	public static boolean isDocumentoNoFiscalPorImprimir() {
		return documentoNoFiscalPorImprimir;
	}

	public static void setDocumentoNoFiscalPorImprimir(
			boolean documentoNoFiscalPorImprimir) {
		MaquinaDeEstadoVenta.documentoNoFiscalPorImprimir = documentoNoFiscalPorImprimir;
	}
	
	
	/**
	 * Calcula cuanto debe descontarse del saldo a favor
	 * por concepto de promociones que van como forma de pago
	 * @return double saldo a descontar
	 * YA NO SE USA
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public double calcularSaldoPromociones(){
		Vector<String> condiciones = new Vector<String>();
		condiciones.addElement(Sesion.condicionDescontadoPorCombo);
		condiciones.addElement(Sesion.condicionProductoGratis);
		double saldoPromociones = 0;
		//Recorrer detalles del objeto devolucion
		for(int i=0;i<devolucion.getDetallesTransaccion().size();i++){
			//Para cada uno preguntar si tiene condicion de venta GC o MP
			DetalleTransaccion detalle = (DetalleTransaccion)devolucion.getDetallesTransaccion().elementAt(i);
			if(detalle.contieneAlgunaCondicion(condiciones)){
				//Calcular cual fue el monto de descuento
				Vector<String> condicionesCC = new Vector<String>();
				condicionesCC.addElement(Sesion.condicionComboPorCantidades);
				condicionesCC.addElement(Sesion.condicionProductoGratis);
				CondicionVenta cv = detalle.getPrimeraCondicion(condicionesCC);
				saldoPromociones += (detalle.getPrecioFinal() + detalle.getMontoImpuesto())*detalle.getCantidad()*cv.getPorcentajeDescuento()/100;
			}
		}
		//Retornar acumulador
		return saldoPromociones;
	}
	
	/**
	 * Ordena los pagos de la venta por codigo de la forma de pago
	 * @param productos
	 * @return Vector
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Pago> ordenarPagosPorCodigo(Vector<Pago> pagos){
		Pago[] pagosArreglados = new Pago[pagos.size()];
		for(int i=0; i < pagosArreglados.length;++i){
			pagosArreglados[i] = pagos.elementAt(i);
		}
		Arrays.sort(pagosArreglados,new ComparadorPagos());
		Vector<Pago> auxiliar = new Vector<Pago>();
		for(int i=0; i < pagosArreglados.length;++i){
			auxiliar.add((Pago)pagosArreglados[i]);
		}
		return auxiliar;
	}
	
	
	/**
	 * Dada una venta, aplica las promociones que correspondan
	 * de acuerdo a los montos pagados por forma de pago Bono Regalo
	 */
	/*public void aplicarPromocionesBR(Venta venta){
		Vector promocionesActivasBR = CR.me.getPromocionesActivasBR();
		if(promocionesActivasBR.size()!=0){
			Vector pagos = venta.getPagos();
			for(int i=0;i<pagos.size();i++){
				Pago pago = (Pago)pagos.elementAt(i);
				if(pago.getFormaPago().getCodigo().equalsIgnoreCase(Sesion.FORMA_PAGO_BONOREGALO_E)){
					double monto = pago.getMonto();
					for(int j=0;j<=promocionesActivasBR.size();j++){
						PromocionBR promocion = (PromocionBR)promocionesActivasBR.elementAt(j);
						switch(promocion.getTipo()){
						case Sesion.TIPO_PROMOCION_BR_PORC_RECARGA:
							double recarga = MathUtil.roundDouble((monto*promocion.getPorcentaje())/100);
							try {
								Cliente cliente = venta.getCliente();	
								CR.me.recargarTarjetaPorPromocion(recarga,cliente, pago.getNumDocumento());
							} catch (UsuarioExcepcion e) {
								
							} catch (MaquinaDeEstadoExcepcion e) {
								
							} catch (XmlExcepcion e) {
								
							} catch (FuncionExcepcion e) {
								
							} catch (ConexionExcepcion e) {
								
							} catch (ExcepcionCr e) {
								
							} catch (PrinterNotConnectedException e) {
								
							}
							break;
						}
					}
				}
			}
		}
	}*/
}
