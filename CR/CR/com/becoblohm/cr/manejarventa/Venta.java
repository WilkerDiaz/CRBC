/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.transaccion
 * Programa   : Venta.java
 * Creado por : apeinado
 * Creado en  : 06/10/2003 03:18:05 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * ----------------------------------------------------------------------------- 
 * Versión     : 1.8 
 * Fecha       : 31/07/2008 08:34 AM
 * Analista    : jgraterol
 * Descripción : Modificados los métodos de aplicar condiciones de venta y
 * 				actualizarPreciosDetalle(boolean) para que agregue las condiciones
 * 				de venta al vector creado para tal fin
 * 				El objetivo del vector de condiciones de venta es disminuir la
 * 				cantidad de condiciones creadas por el módulo de promociones
 * 				o cualquier otra modificación que se realice al algoritmo 
 * 				de actualización de precios posteriormente
 * ============================================================================= 
 * Versión     : 1.8 
 * Fecha       : 12/06/2008 9:56 PM
 * Analista    : jgraterol, aavila
 * Descripción : Agregado manejo de vector donacionesRegistradas, registra las
 * 				donaciones realizadas por venta. El vector es vacio si la extension
 * 				no esta activada o si no se realizaron donaciones
 * ============================================================================= 
 * Versión     : 1.8 
 * Fecha       : 12/06/2008 9:56 PM
 * Analista    : jgraterol, aavila
 * Descripción : Agregado manejo de vector donacionesRegistradas, registra las
 * 				donaciones realizadas por venta. El vector es vacio si la extension
 * 				no esta activada o si no se realizaron donaciones
 * ============================================================================= 
 * Versión     : 1.8 
 * Fecha       : 10/06/2008 9:56 PM
 * Analista    : jgraterol
 * Descripción : Modificado el metodo efectuarPago para que solicite el monto
 * 				de las donaciones a la extension correspondiente para no incluirlo en 
 * 				el vuelto
 * ============================================================================= 
 * Versión     : 1.8 
 * Fecha       : 30/05/2008 3:56 PM
 * Analista    : jgraterol
 * Descripción : Modificado el metodo actualizarPreciosDetalles(boolean) y 
 * 				actualizarPreciosDetalle(producto) para que hagan llamada a la extension
 * 				ActualizadorPrecios
 * 				Se eliminaron de esta clase todos los metodos que son unicamente necesarios para
 * 				los dos anteriores y estan ahora definidos en el default de la extension de 
 * 				actualizador de precios
 * ============================================================================= 
 * Versión     : 1.8
 * Fecha       : 30/05/2008 14:33 PM
 * Analista    : jgraterol
 * Descripción : Modificados los metodos aplicarCondicionDescuentoEmpleado, acumularDetalles,
 * 				aplicarCondicionEmpaque(Producto,Vector, float,int,Vector),
 * 				aplicarCondicionEmpaque(Producto,Vector),aplicarCondicionPromocion. 
 * 				Ahora son publicos para	que pueda ser llamado desde la extension de actualizacion 
 * 				de precios 
 * ============================================================================= 
 * Versión     : 1.7.3 
 * Fecha       : 29/06/2004 09:41 AM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : - Modificado método ingresarLineaProducto de modo que actualice la apertura de la caja para
 * 					facturación en el correspondiente registro de la BD.
 *  
 * ============================================================================= 
 * Versión     : 1.7.2 
 * Fecha       : 24/03/2004 14:33 PM
 * Analista    : gmartinelli
 * Descripción : Agregado constructor para recuperar una venta finalizada para tomarla
 * 				en cuenta para las devoluciones. Modificado los métodos de llamada
 * 				al detalle de transaccion para que no envien como parametro el codigo
 * 				de la tienda. Se modificaron las llamadas a las excepciones de Funciones
 * 				para usar los nuevos constructores
 * ============================================================================= 
 * Versión     : 1.7.1
 * Fecha       : 11/03/2004 8:55 AM
 * Analista    : IROJAS
 * Descripción : - En el método efectuarPago se cambió el tipo del atributo int 
 * 				   fPago a String fPago.
 * 				 - Además, se lanza una nueva excepcion "PagoExcepcion".
 * ============================================================================= 
 * Versión     : 1.7
 * Fecha       : 10/03/2004 8:55 AM
 * Analista    : GMARTINELLI
 * Descripción : - Cambios en el contructor Venta(String) porque omitia uno de los 
 * 				   registros al recuperar facturas en espera.
 * 				 - Cambiado metodo finalizarTransaccion para que devuelva un vector 
 * 				   con Strings que representan cada una de las lineas de la factura.
 * 				 - Cambiado metodo cambiarCantidad a agregarCantidad.
 * ============================================================================= 
 * Versión     : 1.6.11
 * Fecha       : 05/03/2004 11:55 AM
 * Analista    : irojas
 * Descripción : Cambio de parámetros a métodos "cambiarPreciosDetalle" y "aplicarDesctoDefecto"
 * 				 para que reciban el número deicha del usuario que autorizóla función y se lo agrega
 * 				 al detalle nuevo.
 * =============================================================================
 * Versión     : 1.6.10
 * Fecha       : 05/03/2004 10:09 AM
 * Analista    : irojas
 * Descripción : Cambio de nombre del método "aplicarDesctoEmpleado(boolean)" por 
 * 				"actualizarPreciosDetalle(boolean desctoEmpleado)". Además,fue adaptado.
 * =============================================================================
 * Versión     : 1.6.9
 * Fecha       : 02/03/2004 10:45 AM
 * Analista    : irojas
 * Descripción : Modificado metodo asignarCliente para que capture una BaseDeDatosExcepcion
 * 				 cuando ocurran probelmas con la obtención del COLABORADOR. Esto es, si el COLABORADOR 
 * 				 existe pero hay problema con su recuperación de la BD
 * =============================================================================
 * Versión     : 1.6.8
 * Fecha       : 02/03/2004 10:45 AM
 * Analista    : irojas
 * Descripción : Modificado metodo anularProducto para que elimine los productos 
 * 				 cuyas cantidades seancero directamente, sin recorrer el vector
 * 				 de detalles completo
 * =============================================================================
 * Versión     : 1.6.8
 * Fecha       : 02/03/2004 09:30 AM
 * Analista    : gmartinelli
 * Descripción : Modificado metodo cambiarCantidad para que maneje el chequeo del 
 * 				indicador de fraccion que contiene las condiciones de venta de los
 * 				productos para ver si se permiten o no cantidades decimales.
 * =============================================================================
 * Versión     : 1.6.7
 * Fecha       : 02/03/2004 09:25 AM
 * Analista    : gmartinelli
 * Descripción : Cambio de cantidad de int a float
 * =============================================================================
 * Versión     : 1.6.6
 * Fecha       : 02/03/2004 09:10 AM
 * Analista    : gmartinelli
 * Descripción : Agregado Método obtenerRenglones para obtencion de posiciones
 * 				donde se encuentra un producto.
 * 				 Modificado registro de pagos (monto efectivo y monto remanente)
 * 				para que guarde el monto total pagado y el vuelto correctamente
 * =============================================================================
 * Versión     : 1.6.5
 * Fecha       : 02/03/2004 09:02 AM
 * Analista    : gmartinelli
 * Descripción : Cambio del algoritmo de cambio de cantidades para que la interfaz no tenga
 * 				que realizar logica. Se le pasa unicamente la cantidad ingresada por el
 * 				cajero en el cuadro de texto. Comentado metodo camcularMontoPago. Arreglo de
 * 				forma de manejo de pago en efectivo
 * 				Descomentada linea 538 para imprimir factura
 * =============================================================================
 * Versión     : 1.6.4
 * Fecha       : 20/02/2004 14:49 PM
 * Analista    : gmartinelli
 * Descripción : Cambio en cambiarCantidad para que capture excepciones si se coloca un
 * 				renglon equivocado del detalle de transaccion.
 * =============================================================================
 * Versión     : 1.6.3
 * Fecha       : 20/02/2004 14:00 PM
 * Analista    : irojas
 * Descripción : - Línea 868,730, 513 y 605: Modificada pregunta para verificar 
 * 				   los descuentos a empleados. Ahora chequea si la tienda, el producto
 * 				   y el usuario permiten el descuento
 * =============================================================================
 * Versión     : 1.6.3
 * Fecha       : 20/02/2004 11:43 AM
 * Analista    : irojas
 * Descripción : - Línea 1111: Agregado método "asignarCliente", "isAplicaDesctoEmpleado" y 
 * 				  "setAplicaDesctoEmpleado"
 * 				 - Linea 88: Agregado atributo aplicaDesctoEmpleado que indica si la venta 
 * 						  permite este tipo de descuento
 * 				 - Línea 1122: Agregado método "aplicarDesctoEmpleado"
 * 				 - Agreada importación de la clase Cliente
 * =============================================================================
 * Versión     : 1.6.3
 * Fecha       : 20/02/2004 10:34 AM
 * Analista    : gmartinelli
 * Descripción : Modificado metodo actualizarPreciosDetalle (Lineas 194 y 195) para
 * 				permitir acumular los empaques que le fueron aplicados descuentos a 
 * 				COLABORADOR por ser descuentos acumulativos.
 * =============================================================================
 * Versión     : 1.6.2
 * Fecha       : 20/02/2004 08:29 AM
 * Analista    : gmartinelli
 * Descripción : Arreglado metodo de cambio de cantidad para que elimine otros productos
 * 				 del detalle cuando el renglon posea menos productos de los que se quieren 
 * 				 eliminar. (En la proxima reunión deberemos preguntar que se debe hacer si hay
 * 				 3 productos y el cajero ingresa -4. ¿Quita los tres y ya?¿Da un error de que
 * 				 no hay esa cantidad de productos y no quita nada?¿Quita los tres y dice que
 * 				 no se puedo eliminar 1 producto porque no existía?)
 * =============================================================================
 * Versión     : 1.6.1
 * Fecha       : 19/02/2004 13:38 PM
 * Analista    : gmartinelli
 * Descripción : Agregado metodos colocarEnEspera y nuevo contructor de Venta que recibe un String
 * 				 para colocar y recuperar facturas en espera respectuvamente.
 * =============================================================================
 * Versión     : 1.6 (según CVS)
 * Fecha       : 16/02/2004 09:55 AM
 * Analista    : VMEDINA
 * Descripción : Comentarizada línea 398: FacturaVenta.imprimirFactura(this)
 * =============================================================================
 * Versión     : 1.4 (según CVS)
 * Fecha       : 10/02/2004 09:55 AM
 * Analista    : IROJAS
 * Descripción : Se modificó la línea siguiente:
 * 					Línea 113: Método actualizarPreciosDetalle. 
 *							   Modificación del tipo de pregunta para comparar códigos 
 *							   del producto.Ahora como es un String se debe comparar con 
 *							   un equals y no con ==.
 * =============================================================================
 * Versión     : 1.1 (según CVS antes del cambio)
 * Fecha       : 10/02/2004 09:55 AM
 * Analista    : IROJAS
 * Descripción : Se modificó la siguiente línea:
 * 					Línea 72: Metodo ingresarLineaProducto: cambio de tipo de 
 * 							  parámetro codProd de Long a String.
 * =============================================================================
 */
package com.becoblohm.cr.manejarventa;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.beco.cr.actualizadorPrecios.promociones.DonacionRegistrada;
import com.beco.cr.actualizadorPrecios.promociones.PromocionRegistrada;
import com.beco.cr.actualizadorPrecios.promociones.RegaloRegistrado;
import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.AfiliadoUsrExcepcion;
import com.becoblohm.cr.excepciones.AutorizacionExcepcion;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ClienteExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.LineaExcepcion;
import com.becoblohm.cr.excepciones.PagoExcepcion;
import com.becoblohm.cr.excepciones.ProductoExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.gui.CambioDireccion;
import com.becoblohm.cr.gui.CantidadFraccionada;
import com.becoblohm.cr.manejadorauditoria.Auditoria;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstadoVenta;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.extensiones.ManejadorReportesFactory;


import com.becoblohm.cr.extensiones.ActualizadorPreciosFactory;
import com.becoblohm.cr.extensiones.ManejoPagosFactory;
import com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.manejarservicio.Apartado;
import com.becoblohm.cr.manejarservicio.Cotizacion;
import com.becoblohm.cr.manejarservicio.DetalleServicio;
import com.becoblohm.cr.manejarservicio.ListaRegalos;
import com.becoblohm.cr.mediadoresbd.BaseDeDatosVenta;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.sincronizador.HiloSyncTransacciones;
import com.becoblohm.cr.utiles.Control;
import com.becoblohm.cr.utiles.MathUtil;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.crprinterdriver.CRFiscalPrinterOperations;
import com.epa.crprinterdriver.PrinterNotConnectedException;

/** 
 * Descripción: 
 * 		Subclase de Transaccion. Maneja las transacciones de venta desde su estado inicial (Ingreso del primer
 * producto) hasta su finalizacion. Se manejan accesos a base de datos para registrar
 * las transacciones finalizadas.
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
* Fecha: agosto 2011
*/
public class Venta extends Transaccion implements Serializable,Cloneable {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Venta.class);
	
	private Vector<Pago> pagos;
	Vector<Integer> numerosServicio;
	String dirEntregaDom;
	private String serialCaja = "";
	private boolean aplicaDesctoEmpleado;
	private boolean ventaExenta = false;
	private transient DecimalFormat df = new DecimalFormat("#,##0.00");
	//********* Actualización: 09/02/2007. BECO: Cambios por razones de SENIAT
	//********* Se agrega la verificación de clientes de tipo empleados.
	//********* para estos casos no se imprimen como parte de la factura sino que se coloca
	//********* como nota al pie de página. Esto por requerimiento del SENIAT 	
	
	private boolean impEmpleadoPieDePag = false; //Imprimir empleado en pie de página y no como cliente que de
												 //derecho a crédito fiscal en la factura
    
	//Variables modulo de promociones
	public static Vector<DonacionRegistrada> donacionesRegistradas = new Vector<DonacionRegistrada>();
    public static Vector<RegaloRegistrado> regalosRegistrados = new Vector<RegaloRegistrado>();
    public static Vector<PromocionRegistrada> promocionesRegistradas = new Vector<PromocionRegistrada>();
    public Vector<Pago> pagosClonados = new Vector<Pago>();
    
    public Promocion promocionProductoGratis = null;
    
    private Vector detallesAEliminar;
    private double montoAhorrado = 0;
    private double montoDonaciones = 0;
    
    
	
	/**
	 * Constructor de la clase Venta que llama al constructor de la superclase (Transaccion)
	 * y crea un Vector de Pagos vacio.
	 */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Venta() throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {		
		super(Sesion.getTienda().getNumero(), Sesion.getFechaSistema(), Sesion.getCaja().getNumero(),
		Sesion.getCaja().getNumRegistro(), Sesion.getHoraSistema(), Sesion.usuarioActivo.getNumFicha(), Sesion.VENTA, Sesion.PROCESO);
		pagos = new Vector<Pago>();
		//MaquinaDeEstadoVenta.codCupon = "";
		MaquinaDeEstadoVenta.codPromocionCupon = 0;
		//MaquinaDeEstadoVenta.invalidarCupon=true;
		Sesion.setUbicacion("FACTURACION","ingresarLineaProducto");
		Auditoria.registrarAuditoria("Iniciando venta con numRegInicial: " + this.getNumRegCajaInicia(), 'T');
	}
	
	/**
	 * Constructor de la clase Venta que crea una nueva basandose en la que se le pasa como parámetro pero con 
	 * sus nuevos números de registros.
	 */
	public Venta(Venta vta) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {		
		super(Sesion.getTienda().getNumero(), Sesion.getFechaSistema(), Sesion.getCaja().getNumero(),
				Sesion.getCaja().getNumRegistro(), Sesion.getHoraSistema(), Sesion.usuarioActivo.getNumFicha(), Sesion.VENTA, Sesion.PROCESO);
		
		this.cliente = vta.getCliente();
		this.detallesTransaccion = vta.getDetallesTransaccion();
		this.lineasFacturacion = vta.getLineasFacturacion();
		this.montoBase = vta.getMontoBase();
		this.montoImpuesto = vta.getMontoImpuesto();
		this.montoRemanente = vta.getMontoRemanente();
		this.montoVuelto = vta.getMontoVuelto();
		this.pagos = vta.getPagos();
		this.ventaExenta = vta.isVentaExenta(); //((this.montoImpuesto == 0) && this.cliente.isExento()) ? true:false;
		this.codAutorizante = vta.getCodAutorizante();
		
		this.montoAhorrado = vta.getMontoAhorrado(); 
		this.montoDonaciones = vta.getMontoDonaciones();
	}
		
	/**
	 * Constructor de la clase Venta para recuperaciones de facturas en espera
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unchecked")
	public Venta (String codFactEspera) throws BaseDeDatosExcepcion, ConexionExcepcion, AfiliadoUsrExcepcion {
		super();
		
		// Obtenemos la venta de la BaseDeDatos
		Vector<Vector<?>> ventaObtenida = BaseDeDatosVenta.recuperarDeEspera(codFactEspera);
		Vector<Object> cabeceraVenta = (Vector<Object>)ventaObtenida.elementAt(0);
		Vector<Vector<Object>> detallesVenta = (Vector<Vector<Object>>)ventaObtenida.elementAt(1);
		
		//Agregado por módulo de promociones
		Vector<Vector<CondicionVenta>> condicionesVenta = (Vector<Vector<CondicionVenta>>)ventaObtenida.elementAt(2);
	
		// Armamos la cabecera de venta
		super.codTienda = ((Integer)cabeceraVenta.elementAt(0)).intValue();
		super.fechaTrans = Sesion.getFechaSistema();
		super.numCajaInicia = ((Integer)cabeceraVenta.elementAt(1)).intValue();
		super.numRegCajaInicia = ((Integer)cabeceraVenta.elementAt(2)).intValue();
		super.numCajaFinaliza = Sesion.getCaja().getNumero();
		super.numTransaccion = 0;
		super.tipoTransaccion = ((String)cabeceraVenta.elementAt(3)).toCharArray()[0];
		super.horaInicia = (Time)cabeceraVenta.elementAt(4);
		super.cliente = new Cliente((String)cabeceraVenta.elementAt(5));
		super.codCajero = Sesion.usuarioActivo.getNumFicha();
		super.montoBase = ((Double)cabeceraVenta.elementAt(6)).doubleValue();
		super.montoImpuesto = ((Double)cabeceraVenta.elementAt(7)).doubleValue();
		super.montoVuelto = ((Double)cabeceraVenta.elementAt(8)).doubleValue();
		super.montoRemanente = ((Double)cabeceraVenta.elementAt(9)).doubleValue();
		super.lineasFacturacion = ((Integer)cabeceraVenta.elementAt(10)).intValue();
		super.checksum = ((Integer)cabeceraVenta.elementAt(11)).intValue();
		super.estadoTransaccion = Sesion.PROCESO;
		codAutorizante = (String)cabeceraVenta.elementAt(13);
		
		// Obtenemos el cliente si es distinto de nulo
		if (cabeceraVenta.elementAt(5) != null) {
			try {
				this.cliente = MediadorBD.obtenerCliente((String)cabeceraVenta.elementAt(5));
				if (this.cliente.isExento() && this.montoImpuesto==0)
					this.ventaExenta = true;
			} catch (ExcepcionCr e) {
				logger.error("Venta(String)", e);

				this.cliente = new Cliente();
			} catch (ConexionExcepcion e) {
				logger.error("Venta(String)", e);

				this.cliente = new Cliente();
			}
		}
		
		// Chequeamos que el cliente no sea el usuario activo
		if ((this.cliente.getNumFicha()!=null) && Sesion.getUsuarioActivo().getNumFicha().equalsIgnoreCase(this.cliente.getNumFicha()))
			throw new AfiliadoUsrExcepcion("La venta posee como cliente al Usuario Activo.\nNo se pudo recuperar la transacción");
		
		// Armamos el detalle de la venta
		for (int i=0; i<detallesVenta.size(); i++) {
			Vector<Object> detalleVenta = detallesVenta.elementAt(i);
			String codProd = (String)detalleVenta.elementAt(0);
			String codVend = (String)detalleVenta.elementAt(3);
			String tipoCap = (String)detalleVenta.elementAt(8);
			float cant = ((Float)detalleVenta.elementAt(2)).floatValue();
			try {
				//ACTUALIZACION CENTROBECO 19/12/2008: Módulo de promociones
				DetalleTransaccion detalle = new DetalleTransaccion(codProd, codVend, tipoCap, cant, this.fechaTrans, this.horaInicia, true);
				try{
					detalle.setCondicionesVentaPromociones(condicionesVenta.elementAt(i));
					CondicionVenta condicionVentaCombo = detalle.getPrimeraCondicion(Sesion.condicionesCombo);
					if(condicionVentaCombo!=null)
						detalle.setMontoDctoCombo(condicionVentaCombo.getMontoDctoCombo());
				} catch(ArrayIndexOutOfBoundsException ex){
					detalle.setCondicionVentaPromociones(new Vector<CondicionVenta>());
				}
				detalle.setCondicionVenta(detalle.construirCondicionVentaString());
				this.detallesTransaccion.addElement(detalle);
			} catch (ProductoExcepcion e1) {
				logger.error("Venta(String)", e1);

				throw (new BaseDeDatosExcepcion("Error recuperando factura en espera"));
			} catch (BaseDeDatosExcepcion e1) {
				logger.error("Venta(String)", e1);

				throw (new BaseDeDatosExcepcion("Error recuperando factura en espera"));
			}
		
			// Agregamos los otros parametros
			String condVenta = (String)detalleVenta.elementAt(1);
			String codSupervisor = ((String)detalleVenta.elementAt(4));//.equalsIgnoreCase("null") ? null : (String)detalleVenta.elementAt(4);
			double precioFinal = ((Double)detalleVenta.elementAt(5)).doubleValue();
			double impuesto = ((Double)detalleVenta.elementAt(6)).doubleValue();
			int codPromo = ((Integer)detalleVenta.elementAt(7)).intValue();
			double dctoEmp = ((Double)detalleVenta.elementAt(9)).doubleValue();
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setCondicionVenta(condVenta);
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setCodSupervisor(codSupervisor);
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setPrecioFinal(precioFinal);
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setMontoImpuesto(impuesto);
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setCodPromocion(codPromo);
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setDctoEmpleado(dctoEmp);
			if (dctoEmp!=0) this.setAplicaDesctoEmpleado(true);
		}
		
		pagos = new Vector<Pago>();
		this.setMontoAhorrado((new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().sumarMontoAhorrado(this));
		BaseDeDatosVenta.borrarFacturaEspera(codFactEspera, this.numCajaInicia, this.codTienda, this.numRegCajaInicia, this.fechaTrans);
	}

	/**
	 * Constructor de la clase Venta para obtener ventas temporales para el manejo de devoluciones
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	@SuppressWarnings({ "unchecked" })
	public Venta (int tda, String fechaT, int caja, int numTransaccion, boolean local) throws BaseDeDatosExcepcion, ConexionExcepcion {
		super();
		
		// Obtenemos la venta de la BaseDeDatos
		Vector<Vector<?>> ventaObtenida = BaseDeDatosVenta.obtenerVenta(tda, fechaT, caja, numTransaccion, local);
		Vector<Object> cabeceraVenta = (Vector<Object>)ventaObtenida.elementAt(0);
		Vector<Vector<Object>> detallesVenta = (Vector<Vector<Object>>)ventaObtenida.elementAt(1);
		Vector<Vector<Object>> pagosVenta = (Vector<Vector<Object>>)ventaObtenida.elementAt(2);
	
		// Armamos la cabecera de venta
		super.codTienda = tda;
		super.fechaTrans = ((Date)cabeceraVenta.elementAt(1));
		super.numCajaInicia = ((Integer)cabeceraVenta.elementAt(2)).intValue();
		super.numRegCajaInicia = ((Integer)cabeceraVenta.elementAt(3)).intValue();
		super.numCajaFinaliza = caja;
		super.numTransaccion = numTransaccion;
		super.tipoTransaccion = ((String)cabeceraVenta.elementAt(6)).toCharArray()[0];
		super.horaInicia = (Time)cabeceraVenta.elementAt(7);
		super.horaFin = (Time)cabeceraVenta.elementAt(8);
		if (((String)cabeceraVenta.elementAt(9)) == null) {
			super.cliente = new Cliente(); 
		} else {
			try {
				super.cliente = MediadorBD.obtenerCliente((String)cabeceraVenta.elementAt(9));
			} catch (ConexionExcepcion e) {
				logger.error("Venta(int, String, int, int, boolean)", e);
			} catch (BaseDeDatosExcepcion e) {
				logger.error("Venta(int, String, int, int, boolean)", e);
			} catch (AfiliadoUsrExcepcion e) {
				logger.error("Venta(int, String, int, int, boolean)", e);
			} catch (ClienteExcepcion e) {
				logger.error("Venta(int, String, int, int, boolean)", e);
			}
		}
		super.codCajero = (String)cabeceraVenta.elementAt(10);
		this.serialCaja = (String)cabeceraVenta.elementAt(11);
		super.montoBase = ((Double)cabeceraVenta.elementAt(12)).doubleValue();
		super.montoImpuesto = ((Double)cabeceraVenta.elementAt(13)).doubleValue();
		super.montoVuelto = ((Double)cabeceraVenta.elementAt(14)).doubleValue();
		super.montoRemanente = ((Double)cabeceraVenta.elementAt(15)).doubleValue();
		super.lineasFacturacion = ((Integer)cabeceraVenta.elementAt(16)).intValue();
		super.checksum = ((Integer)cabeceraVenta.elementAt(17)).intValue();
		super.estadoTransaccion = ((String)cabeceraVenta.elementAt(18)).toCharArray()[0];
		super.numComprobanteFiscal = ((Integer)cabeceraVenta.elementAt(19)).intValue();
		super.codAutorizante = (String)cabeceraVenta.elementAt(20);
	
		// Armamos el detalle de la venta
		for (int i=0; i<detallesVenta.size(); i++) {
			Vector<Object> detalleVenta = detallesVenta.elementAt(i);
			String codProd = (String)detalleVenta.elementAt(0);
			String codVend = (String)detalleVenta.elementAt(3);
			String tipoCap = (String)detalleVenta.elementAt(8);
			float cant = ((Float)detalleVenta.elementAt(2)).floatValue();
			try {
				this.detallesTransaccion.addElement(new DetalleTransaccion(codProd, codVend, tipoCap, cant, this.fechaTrans, this.horaInicia, true));
			} catch (ProductoExcepcion e1) {
				logger.error("Venta(int, String, int, int, boolean)", e1);

				throw (new BaseDeDatosExcepcion("Existen productos en la venta original que no se encuentran en la Base De Datos"));
			} catch (BaseDeDatosExcepcion e1) {
				logger.error("Venta(int, String, int, int, boolean)", e1);

				throw (new BaseDeDatosExcepcion("Error recuperando factura para devolucion"));
			}
		
			// Agregamos los otros parametros
			double dctoEmp = ((Double)detalleVenta.elementAt(9)).doubleValue();
			String condVenta = (String)detalleVenta.elementAt(1);
			String codSupervisor = ((String)detalleVenta.elementAt(4));//.equalsIgnoreCase("null") ? null : (String)detalleVenta.elementAt(4);
			double precioFinal = ((Double)detalleVenta.elementAt(5)).doubleValue();
			double impuesto = ((Double)detalleVenta.elementAt(6)).doubleValue();
			int codPromo = ((Integer)detalleVenta.elementAt(7)).intValue();
			String codEntrega = (String)detalleVenta.elementAt(10);
			double precioRegular = ((Double)detalleVenta.elementAt(11)).doubleValue();
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setCondicionVenta(condVenta);
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setCodSupervisor(codSupervisor);
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setPrecioFinal(precioFinal);
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setMontoImpuesto(impuesto);
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setCodPromocion(codPromo);
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setDctoEmpleado(dctoEmp);
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setCodTipoEntrega(codEntrega.charAt(0));
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).getProducto().setPrecioRegular(precioRegular);
		}

		// Armamos los pagos
		pagos = new Vector<Pago>();
		for (int i=0; i<pagosVenta.size(); i++) {
			Vector<Object> pagoObtenido = pagosVenta.elementAt(i);
			Pago pagoActual = new Pago((String)pagoObtenido.elementAt(4),((Double)pagoObtenido.elementAt(5)).doubleValue(),(String)pagoObtenido.elementAt(11),
						(String)pagoObtenido.elementAt(6),(String)pagoObtenido.elementAt(7),(String)pagoObtenido.elementAt(8),
						((Integer)pagoObtenido.elementAt(9)).intValue(),(String)pagoObtenido.elementAt(10));
			pagoActual.setCodAutorizante((String)pagoObtenido.elementAt(12));
			pagos.addElement(pagoActual);
		}
		
		//MODULO PROMOCIONES: necesario recuperar donacionesRegistradas de esta transaccion para el caso de anulaciones
		Venta.donacionesRegistradas = (new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().consultarDonacionesPorVenta(tda, fechaT, caja, numTransaccion, local);
		//MaquinaDeEstadoVenta.codCupon = "";
		MaquinaDeEstadoVenta.codPromocionCupon = 0;
		//MaquinaDeEstadoVenta.invalidarCupon=true;
	}

	/**
	 * Constructor de la clase Venta para obtener la última venta realizada por la caja.
	 * Caso de reimpresion de última facturada realizada.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unchecked")
	public Venta (Vector<Vector<?>> ventaObtenida) throws BaseDeDatosExcepcion, ConexionExcepcion {
		super();
		
		// Construimos la venta con los detalles de la misma enviados como parámetros
		Vector<Object> cabeceraVenta = (Vector<Object>)ventaObtenida.elementAt(0);
		Vector<Vector<Object>> detallesVenta = (Vector<Vector<Object>>)ventaObtenida.elementAt(1);
		Vector<Vector<Object>> pagosVenta = (Vector<Vector<Object>>)ventaObtenida.elementAt(2);
		
		//Agregado por módulo de promociones
		Vector<Vector<CondicionVenta>> condicionesVenta = (Vector<Vector<CondicionVenta>>)ventaObtenida.elementAt(3);
		Vector<DonacionRegistrada> donacionesRegistradas = (Vector<DonacionRegistrada>)ventaObtenida.elementAt(4);
		Vector<PromocionRegistrada> promocionesRegistradas = (Vector<PromocionRegistrada>)ventaObtenida.elementAt(5);
		Vector<RegaloRegistrado> regalosRegistrados = (Vector<RegaloRegistrado>)ventaObtenida.elementAt(6);
	
		// Armamos la cabecera de venta
		super.codTienda = ((Integer)cabeceraVenta.elementAt(0)).intValue();
		super.fechaTrans = ((Date)cabeceraVenta.elementAt(1));
		super.numCajaInicia = ((Integer)cabeceraVenta.elementAt(2)).intValue();
		super.numRegCajaInicia = ((Integer)cabeceraVenta.elementAt(3)).intValue();
		super.numCajaFinaliza = ((Integer)cabeceraVenta.elementAt(4)).intValue();
		super.numTransaccion = ((Integer)cabeceraVenta.elementAt(5)).intValue();
		super.tipoTransaccion = ((String)cabeceraVenta.elementAt(6)).toCharArray()[0];
		super.horaInicia = (Time)cabeceraVenta.elementAt(7);
		super.horaFin = (Time)cabeceraVenta.elementAt(8);
		super.cliente = new Cliente((String)cabeceraVenta.elementAt(9));
		super.codCajero = (String)cabeceraVenta.elementAt(10);
		this.serialCaja = (String)cabeceraVenta.elementAt(11);
		super.montoBase = ((Double)cabeceraVenta.elementAt(12)).doubleValue();
		super.montoImpuesto = ((Double)cabeceraVenta.elementAt(13)).doubleValue();
		super.montoVuelto = ((Double)cabeceraVenta.elementAt(14)).doubleValue();
		super.montoRemanente = ((Double)cabeceraVenta.elementAt(15)).doubleValue();
		super.lineasFacturacion = ((Integer)cabeceraVenta.elementAt(16)).intValue();
		super.checksum = ((Integer)cabeceraVenta.elementAt(17)).intValue();
		super.estadoTransaccion = ((String)cabeceraVenta.elementAt(18)).toCharArray()[0];
		super.numComprobanteFiscal = ((Integer)cabeceraVenta.elementAt(19)).intValue();
		super.codAutorizante = (String)cabeceraVenta.elementAt(20);
	
		// Obtenemos el cliente si es distinto de nulo
		if (cabeceraVenta.elementAt(9) != null) {
			try {
				this.cliente = MediadorBD.obtenerCliente((String)cabeceraVenta.elementAt(9));
				if (this.cliente.isExento() && this.montoImpuesto==0)
					this.ventaExenta = true;
			} catch (ExcepcionCr e) {
				logger.error("Venta(Vector)", e);

				this.cliente = new Cliente();
			} catch (ConexionExcepcion e) {
				logger.error("Venta(Vector)", e);

				this.cliente = new Cliente();
			}
		}
			
		// Armamos el detalle de la venta
		for (int i=0; i<detallesVenta.size(); i++) {
			Vector<Object> detalleVenta = detallesVenta.elementAt(i);
			String codProd = (String)detalleVenta.elementAt(0);
			String codVend = (String)detalleVenta.elementAt(3);
			String tipoCap = (String)detalleVenta.elementAt(8);
			float cant = ((Float)detalleVenta.elementAt(2)).floatValue();
			try {
				DetalleTransaccion detalle=  new DetalleTransaccion(codProd, codVend, tipoCap, cant, this.fechaTrans, this.horaInicia, true);
				this.detallesTransaccion.addElement(detalle);
				try{
					detalle.setCondicionesVentaPromociones(condicionesVenta.elementAt(i));
					CondicionVenta condicionVentaCombo = detalle.getPrimeraCondicion(Sesion.condicionesCombo);
					if(condicionVentaCombo!=null)
						detalle.setMontoDctoCombo(condicionVentaCombo.getMontoDctoCombo());
				} catch(ArrayIndexOutOfBoundsException ex){
					detalle.setCondicionVentaPromociones(new Vector<CondicionVenta>());
				}
				detalle.setCondicionVenta(detalle.construirCondicionVentaString());
			} catch (ProductoExcepcion e1) {
				logger.error("Venta(Vector)", e1);

				throw (new BaseDeDatosExcepcion("Existen productos en la venta original que no se encuentran en la Base De Datos"));
			} catch (BaseDeDatosExcepcion e1) {
				logger.error("Venta(Vector)", e1);

				throw (new BaseDeDatosExcepcion("Error recuperando factura para reimpresion"));
			}
		
			// Agregamos los otros parametros
			double dctoEmp = ((Double)detalleVenta.elementAt(9)).doubleValue();
			String condVenta = (String)detalleVenta.elementAt(1);
			String codSupervisor = ((String)detalleVenta.elementAt(4));//.equalsIgnoreCase("null") ? null : (String)detalleVenta.elementAt(4);
			double precioFinal = ((Double)detalleVenta.elementAt(5)).doubleValue();
			double impuesto = ((Double)detalleVenta.elementAt(6)).doubleValue();
			int codPromo = ((Integer)detalleVenta.elementAt(7)).intValue();
			String tipoEntrega = (String)detalleVenta.elementAt(10);
			double precioRegular = ((Double)detalleVenta.elementAt(11)).doubleValue();
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setCondicionVenta(condVenta);
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setCodSupervisor(codSupervisor);
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setPrecioFinal(precioFinal);
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setMontoImpuesto(impuesto);
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setCodPromocion(codPromo);
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setDctoEmpleado(dctoEmp);
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setCodTipoEntrega(tipoEntrega.charAt(0));
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).getProducto().setPrecioRegular(precioRegular);
		}

		// Armamos los pagos
		pagos = new Vector<Pago>();
		int codPromoCupon=0;
		for (int i=0; i<pagosVenta.size(); i++) {
			Vector<Object> pagoObtenido = pagosVenta.elementAt(i);
			Pago pagoActual = new Pago((String)pagoObtenido.elementAt(4),((Double)pagoObtenido.elementAt(5)).doubleValue(),(String)pagoObtenido.elementAt(11),
						(String)pagoObtenido.elementAt(6),(String)pagoObtenido.elementAt(7),(String)pagoObtenido.elementAt(8),
						((Integer)pagoObtenido.elementAt(9)).intValue(),(String)pagoObtenido.elementAt(10));
			pagoActual.setCodAutorizante((String)pagoObtenido.elementAt(12));
			pagos.addElement(pagoActual);
			if(pagoActual.getFormaPago().getCodigo().equalsIgnoreCase(Sesion.FORMA_PAGO_CUPON_DESCUENTO))
				codPromoCupon=Integer.parseInt(pagoActual.getNumDocumento());
		}
		this.setMontoAhorrado((new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().sumarMontoAhorrado(this));
		Venta.donacionesRegistradas = donacionesRegistradas;
		Venta.promocionesRegistradas = promocionesRegistradas;
		Venta.regalosRegistrados = regalosRegistrados;
		//MaquinaDeEstadoVenta.codCupon = "";
		//MaquinaDeEstadoVenta.invalidarCupon=true;
		MaquinaDeEstadoVenta.codPromocionCupon = codPromoCupon; 
		this.setMontoDonaciones((new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().sumarDonaciones(this, false)-(this.getMontoBase()+this.getMontoImpuesto()));
	}
	
	/**
	 * Ingresa nuevas lineas de productos. Lo busca desde la base de datos y lo agrega a la transaccion.
	 * @param codProd Codigo del producto a ingresar.
	 * @param codVendedor Vendedor participante en la venta del producto (Si existe Vendedor).
	 * @param tipoCaptura Tipo de captura del producto (Teclado, Escaner).
	 * @throws BaseDeDatosExcepcion Si ocurre un error con la base de datos.
	 * @throws ProductoExcepcion Si no se encuentra el producto.
	 */
	public void ingresarLineaProducto(String codProd, String codVendedor, String tipoCaptura, boolean leidoBD) throws ProductoExcepcion, ConexionExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("ingresarLineaProducto(String, String, String) - start");
		}

		ResultSet datosCaja = null;
		try {
			Sesion.setUbicacion("FACTURACION","ingresarLineaProducto");
		
			datosCaja = MediadorBD.obtenerDatosCaja(true);

			if(datosCaja.getString("cierrecajero").toUpperCase().equals("N"))
				Sesion.getCaja().setCierreCajero(Sesion.SI);
			DetalleTransaccion detalle = new DetalleTransaccion(codProd, codVendedor, tipoCaptura, 1, this.fechaTrans, this.horaInicia, leidoBD);
			Producto prod = detalle.getProducto();
			//Se chequea si es necesaria ingresar una cantidad decimal.
			//requerimiento para que el ingresar línea producto no agregue cantidad 1 por defecto para el caso de cantidades fraccionadas
			if (prod.isIndicaFraccion()) {
				CantidadFraccionada cf = new CantidadFraccionada();
				MensajesVentanas.centrarVentanaDialogo(cf);
				detalle.setCantidad(cf.getCantidad());
			}
			if ((detallesTransaccion.size() > 0) && (tipoCaptura.equals(Sesion.capturaTeclado))) {
				detalle.setTipoEntrega(((DetalleTransaccion)detallesTransaccion.lastElement()).getTipoEntrega());
			}
			detallesTransaccion.addElement(detalle);
		
			actualizarPreciosDetalle(prod);
			actualizarMontoTransaccion();
		
			// Registramos la auditoría de esta función
			Auditoria.registrarAuditoria("Captura de Producto " + codProd + " por " + tipoCaptura,'T');
		} catch (SQLException e) {
			logger.error("ingresarLineaProducto(String, String, String)", e);

			Auditoria.registrarAuditoria("Falla acceso a BD al recuperar datos de CR", 'E');
		} finally{
			if(datosCaja != null) 
				try {
					datosCaja.close();
				} catch (SQLException e1) {
					logger
							.error(
									"ingresarLineaProducto(String, String, String)",
									e1);
				}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ingresarLineaProducto(String, String, String) - end");
		}
	}
	
	/**
	 * Metodo que cambia la cantidad de un producto
	 * @param nvaCantidad Nueva cantidad a asignar
	 * @param renglon Renglon donde se encuentra el producto. Si existen menos productos en el renglon que el
	 *		especificado en nvaCantidad, se eliminan de otros renglones con el mismo producto. 		
	 * @throws ExcepcionCr Si no existe el renglón especificado o el producto no admite cantidades fraccionadas
	 * 		y se coloca una cantidad fraccionada como parametro nvaCantidad. Si ocurre un error de resitro de auditoria.
	 * 		Si ocurre un error al obtener metodo, modulo y funcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void agregarCantidad(float nvaCantidad, int renglon) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ProductoExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("agregarCantidad(float, int) - start");
		}
		
		

		Sesion.setUbicacion("FACTURACION","cambiarCantidad");
		
		// Registramos la auditoría de esta función
		Auditoria.registrarAuditoria("cambio de cantidad",'T');
		
		Producto pCambiar;
		float cantidadRenglon;
		
		// Observamos si el renglon del producto existe
		try {
			// Obtenemos el Producto a cambiar la cantidad
			pCambiar = ((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).getProducto();
		} catch (Exception ex) {
			logger.error("agregarCantidad(float, int)", ex);

			throw (new ProductoExcepcion("Error al cambiar cantidad de producto, no existe renglon " + renglon + " en la transaccion", ex));
		}
		
		// Chequeamos  si la cantidad es fraccionada, y si el producto permite cantidades fraccionadas
		if ((!pCambiar.isIndicaFraccion())&&((nvaCantidad % 1)!=0)) {
			throw (new ProductoExcepcion("Error al cambiar cantidad de producto.\nEl producto " + pCambiar.getCodProducto() + " no admite cantidades fraccionadas (Decimales)"));
		}
		
		cantidadRenglon = ((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).getCantidad();
		if (nvaCantidad > 0) nvaCantidad = cantidadRenglon + nvaCantidad;
		else throw (new ProductoExcepcion("Error al cambiar cantidades de productos. No se pueden agregar cantidades negativas"));
		((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).setCantidad(nvaCantidad);
		Vector<String> condicionesProductoGratis = new Vector<String>();
		condicionesProductoGratis.addElement(Sesion.condicionProductoGratis);
		if(!((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).contieneAlgunaCondicion(Sesion.condicionesCombo) &&
				!((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).contieneAlgunaCondicion(condicionesProductoGratis)){
			((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).setCondicionVenta(Sesion.condicionNormal);
			//Inicializamos la varable que depende de la condicíon de venta (en esta caso es el de la condición especial del detalle)
			((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).setCondicionEspecial(false);
			((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).setCondicionesVentaPromociones(new Vector<CondicionVenta>());
			((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).setPrecioFinal(((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).getProducto().getPrecioRegular());
			((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).setMontoDctoEmpleado(0);
		}
		//if(!Sesion.getCaja().getEstado().equals("23"))
		actualizarPreciosDetalle(pCambiar);
		actualizarMontoTransaccion();

		if (logger.isDebugEnabled()) {
			logger.debug("agregarCantidad(float, int) - end");
		}
	}

	/* (non-Javadoc)
	 * @see com.becoblohm.cr.manejarventa.Transaccion#actualizarPreciosDetalle(Producto)
	 */
	protected void actualizarPreciosDetalle(Producto p) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarPreciosDetalle(Producto) - start");
		}
		
		(new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().actualizarPrecios(p, this);
		
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarPreciosDetalle(Producto) - end");
		}
	}
	
	/**
	 * Agrupa los detalles iguales y los coloca al final del vector de detalles original
	 * @param posiciones Vector de posiciones donde se encuentran los productos a agrupar
	 * @return void
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void acumularDetalles(Vector<Integer> posiciones) {
		if (logger.isDebugEnabled()) {
			logger.debug("acumularDetalles(Vector) - start");
		}

		Vector<Integer> paraCaptura = new Vector<Integer>(2);
		
		// Acumulamos los productos por vendedores (Si es el caso)
		for (int i=0; i<posiciones.size();i++) {
			if (((Integer)posiciones.elementAt(i))!=null) {
				int posicionI = ((Integer)posiciones.elementAt(i)).intValue();
			
				for (int j=i+1; j<posiciones.size(); j++) {
					if (((Integer)posiciones.elementAt(j))!=null) {
						int posicionJ = ((Integer)posiciones.elementAt(j)).intValue();
						
						if ((((DetalleTransaccion)detallesTransaccion.elementAt(posicionI)).getCodVendedor() == null) || (((DetalleTransaccion)detallesTransaccion.elementAt(posicionI)).getCodVendedor()).equals(((DetalleTransaccion)detallesTransaccion.elementAt(posicionJ)).getCodVendedor())) {
							
							// Primero verificamos los tipos de captura entre detalles
							paraCaptura.addElement(((Integer)posiciones.elementAt(i)));
							paraCaptura.addElement(((Integer)posiciones.elementAt(j)));
							verificarTipoCaptura(paraCaptura);
								
							((DetalleTransaccion)detallesTransaccion.elementAt(posicionI)).incrementarCantidad(((DetalleTransaccion)detallesTransaccion.elementAt(posicionJ)).getCantidad());
							detallesTransaccion.set(posicionJ,null);
							posiciones.set(j,null);
						}
					}
					
					paraCaptura.clear();
				}
				
			}
		}
			
		// Eliminamos los nulos del vector de posiciones de condiciones normales 'N'
		while (posiciones.removeElement(null));
			
		// Colocamos los detalles al final del vector original.
		DetalleTransaccion detalleTrans = null;
		int pos;
			
		// Movemos los detalles para colocarlos al final del vector
		for (int i=0; i<posiciones.size(); i++){
			pos = ((Integer)posiciones.elementAt(i)).intValue();
			detalleTrans = (DetalleTransaccion) detallesTransaccion.elementAt(pos);
			detallesTransaccion.addElement(detalleTrans);
		
			detallesTransaccion.set(pos,null);
			posiciones.set(i,null);
		}
			
		if (logger.isDebugEnabled()) {
			logger.debug("acumularDetalles(Vector) - end");
		}
	}
	
	/* (non-Javadoc)
	 * @see com.becoblohm.cr.manejarventa.Transaccion#anularProducto(long)
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void anularProducto(int renglon) throws ProductoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("anularProducto(int) - start");
		}

		DetalleTransaccion linea = null;
		try {
			// Obtenemos la linea a cambiar la cantidad
			linea = (DetalleTransaccion) detallesTransaccion.elementAt(renglon);
		} catch (Exception ex) {
			logger.error("anularProducto(int)", ex);

			throw (new ProductoExcepcion("Error al elimina producto, no existe renglon " + renglon + " en la transaccion", ex));
		}
		
		Producto prod = linea.getProducto();
		linea.anularProducto();
		
		// Cambiamos la condición de venta originalpara que sea recalculada
		
		
		//Actualizacion BECO por módulo de promociones
		if(!linea.contieneAlgunaCondicion(Sesion.condicionesCombo)){
			linea.setCondicionVenta(Sesion.condicionNormal);
			linea.setCondicionesVentaPromociones(new Vector<CondicionVenta>());
		}
		
		//Actualizacion BECO: Es necesario que el detalle llegue al algoritmo con cantidad cero para el
		//módulo de promociones, la anulación del detalle será realizada al final del método actualizarPreciosExt
		/*if (((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).getCantidad() <= 0) {
			Auditoria.registrarAuditoria("Anulado(s) " + (((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).getCantidad()+1) + " producto(s) " + linea.getProducto().getCodProducto() + " de la transaccion",'T');
			detallesTransaccion.removeElementAt(renglon);
		} else*/
			Auditoria.registrarAuditoria("Anulado(s) 1 producto(s) " + linea.getProducto().getCodProducto() + " de la transaccion",'T');
		
		actualizarPreciosDetalle(prod);
		actualizarMontoTransaccion();

		if (logger.isDebugEnabled()) {
			logger.debug("anularProducto(int) - end");
		}
	}

	/**
	 * Elimina una cantidad de productos de un renglon.
	 * @param renglon Renglon del producto a eliminar.
	 * @param cantidad Cantidad de productos a eliminar. Si existen menos productos que el especificado en la
	 * cantidad se elimina el renglon.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Object> anularProducto(int renglon, float cantidad) throws ProductoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("anularProducto(int, float) - start");
		}

		DetalleTransaccion linea = null;
		Vector<Object> result = new Vector<Object>();
		try {
			// Obtenemos la linea a cambiar la cantidad
			linea = (DetalleTransaccion) detallesTransaccion.elementAt(renglon);
		} catch (Exception ex) {
			logger.error("anularProducto(int, float)", ex);

			throw (new ProductoExcepcion("Error al eliminar producto, no existe renglon " + renglon + " en la transaccion", ex));
		}

		result.addElement(linea.getProducto().getDescripcionCorta());
		result.addElement(new Double(linea.getPrecioFinal() + linea.getMontoImpuesto()));
		
		// Verificamos si existen productos suficientes en el renglon
		if (linea.getCantidad() < cantidad)
			throw (new ProductoExcepcion("Error al eliminar producto. No existe(n) " + cantidad + " producto(s) en el renglon"));

		// Verificamos si el número es mayor a cero
		if (cantidad <= 0)
			throw (new ProductoExcepcion("El número debe ser un número mayor que cero (0)"));

		// Chequeamos si la nueva cantidad es fraccionada, y si el producto permite cantidades fraccionadas
		if ((!linea.getProducto().isIndicaFraccion())&&((cantidad % 1)!=0)) {
			throw (new ProductoExcepcion("Error al cambiar cantidad de producto.\nEl producto " + linea.getProducto().getCodProducto() + " no admite cantidades fraccionadas (Decimales)"));
		
		}
		
		Producto prod = linea.getProducto();
		linea.anularProducto(cantidad);
	
		// Cambiamos la condición de venta originalpara que sea recalculada
		//Actualizacion BECO por módulo de promociones
		if(!linea.contieneAlgunaCondicion(Sesion.condicionesCombo)){
			linea.setCondicionVenta(Sesion.condicionNormal);
			linea.setCondicionesVentaPromociones(new Vector<CondicionVenta>());
		}
	
		//Actualizacion BECO: Es necesario que el detalle llegue al algoritmo con cantidad cero para el
		//módulo de promociones, la anulación del detalle será realizada en los métodos acumular detalle
		Vector<String> condicionesProductoGratis = new Vector<String>();
		condicionesProductoGratis.addElement(Sesion.condicionProductoGratis);
		/*if (((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).getCantidad() <= 0 && !((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).contieneAlgunaCondicion(Sesion.condicionesCombo)
				&& !((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).contieneAlgunaCondicion(condicionesProductoGratis)) {
			Auditoria.registrarAuditoria("Anulado(s) " + (((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).getCantidad()+cantidad) + " producto(s) " + linea.getProducto().getCodProducto() + " de la transaccion",'T');
			detallesTransaccion.removeElementAt(renglon);
		} else
			Auditoria.registrarAuditoria("Anulado(s) " + cantidad + " producto(s) " + linea.getProducto().getCodProducto() + " de la transaccion",'T');
		*/
		actualizarPreciosDetalle(prod);
		actualizarMontoTransaccion();

		if (logger.isDebugEnabled()) {
			logger.debug("anularProducto(int, float) - end");
		}
		return result;
	}
	
	/**
	 * Actualiza el Monto de a transaccion, calcula el nuevo monto a partir del detalle de la factura.
	 */
	public void actualizarMontoTransaccion() {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarMontoTransaccion() - start");
		}

		double precioFinal = 0.0;
		double montoImpuesto = 0.0;
		
		for (int i=0; i<detallesTransaccion.size(); i++) {
			precioFinal += ((DetalleTransaccion) detallesTransaccion.elementAt(i)).getPrecioFinal() * ((DetalleTransaccion) detallesTransaccion.elementAt(i)).getCantidad();
			montoImpuesto += ((DetalleTransaccion) detallesTransaccion.elementAt(i)).getMontoImpuesto() * ((DetalleTransaccion) detallesTransaccion.elementAt(i)).getCantidad();
		}
		
		this.montoImpuesto = MathUtil.roundDouble(montoImpuesto);
		this.montoBase = MathUtil.roundDouble(precioFinal);

		// Si existe una retencion la recalculamos.
		CR.me.recalcularRetencionIVA();

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarMontoTransaccion() - end");
		}
	}
	
	/**
	 * Calcula el Monto de la transaccion, calcula el nuevo monto a partir del detalle de la factura.
	 * @author jgraterol. Agregado por módulo de promociones
	 */
	public double getMontoTransaccion() {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarMontoTransaccion() - start");
		}

		double precioFinal = 0.0;
		montoImpuesto = 0.0;
		
		for (int i=0; i<detallesTransaccion.size(); i++) {
			precioFinal += ((DetalleTransaccion) detallesTransaccion.elementAt(i)).getPrecioFinal() * ((DetalleTransaccion) detallesTransaccion.elementAt(i)).getCantidad();
			montoImpuesto += ((DetalleTransaccion) detallesTransaccion.elementAt(i)).getMontoImpuesto() * ((DetalleTransaccion) detallesTransaccion.elementAt(i)).getCantidad();
		}
		
		double montoDeImpuesto = MathUtil.roundDouble(montoImpuesto);
		double montoBase = MathUtil.roundDouble(precioFinal);

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarMontoTransaccion() - end");
		}
		
		return MathUtil.roundDouble(montoBase+montoDeImpuesto);
		
	}
	
	/**
	 * Dado un Vector de posiciones del detalle chequea los tipos de captura para colocarlos Teclado, 
	 * Escaner o Mixto.
	 * 
	 * Metodo cambiado de private a public para el uso del módulo de promociones
	 * 
	 * @param posiciones Vector a chequear los tipos de captura.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void verificarTipoCaptura (Vector<Integer> posiciones) {
		if (logger.isDebugEnabled()) {
			logger.debug("verificarTipoCaptura(Vector) - start");
		}

		// Verificamos el tipo de captura del producto. Si hubo más de un tipo de captura los coloca 'M'(Mixto) para todas las ocurrencias del mismo
		for (int i=0; i<posiciones.size();i++) {
			int posicionI = ((Integer)posiciones.elementAt(i)).intValue();
			
			for (int j=i+1; j<posiciones.size(); j++) {
				int posicionJ = ((Integer)posiciones.elementAt(j)).intValue();
				if (!(((DetalleTransaccion)detallesTransaccion.elementAt(posicionI)).getTipoCaptura()).equals(((DetalleTransaccion)detallesTransaccion.elementAt(posicionJ)).getTipoCaptura())) {
					((DetalleTransaccion)detallesTransaccion.elementAt(posicionI)).setTipoCaptura(Sesion.capturaMixto);
				}
			}
		}		

		if (logger.isDebugEnabled()) {
			logger.debug("verificarTipoCaptura(Vector) - end");
		}
	}
	
	/**
	 * Obtiene el monto cancelado hasta el momento.
	 * @return double - El monto de la venta cancelado.
	 */
	public double obtenerMontoPagado() {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerMontoPagado() - start");
		}

		double monto = 0.0;
		for (int i=0; i<pagos.size(); i++) {
			monto += ((Pago)pagos.elementAt(i)).getMonto();
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerMontoPagado() - end");
		}
		return monto;
	}
	
	/**
	 * Registra los pagos en efectivo.
	 * @param monto Monto a cancelar.
	 * @return double - El monto que falta por pagar. Si es positivo es lo que falta por pagar.
	 * Si es negativo es vuelto al cliente.
	 */
	/*public double efectuarPago(double monto) throws XmlExcepcion, FuncionExcepcion, PagoExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {		
		double resto, montoPago, montoPagado;
		Pago p;
		montoPagado = 0;
		
		montoPagado = obtenerMontoPagado();
		//montoPago = calcularMontoPago(monto);
		
		// Chequeamos si existen pagos en efectivo
		p = obtenerPagoEfectivo();
		if (p != null) { 
			p.actualizarMonto(monto);//montoPago);
		} else {
			//Buscamos el codigo de la forma de pago a partir de la palabra "EFECTIVO" en la BD			
			String fPago = BaseDeDatosVenta.obtenerCodFormaPago("Efectivo");
			p = new Pago(fPago, "Efectivo", monto);//montoPago);
			pagos.addElement(p);
		}
		
		/*
		 * Si resto es positivo: Monto que falta por pagar
		 * Si resto es negativo: Monto a dar como vuelto al cliente
		 * Si resto es cero: No hay vuelto, pago finalizado.
		 */

	/*	resto = this.consultarMontoTrans() - montoPagado - monto;
		if (resto <= 0) {
			this.montoRemanente = -resto;
		}
		
		// Registramos la auditoría de esta función
		Sesion.setUbicacion("PAGO","efectuarPago");
		Auditoria.registrarAuditoria("Pago con efectivo y equivalentes. MontoPago " + monto,'T');
		
		return resto;

	}*/
	
	/**
	 * Registra los pagos en efectivo.
	 * @param p
	 * @return double - El monto que falta por pagar. Si es positivo es lo que falta por pagar.
	 * Si es negativo es vuelto al cliente.
	 */
	public double efectuarPago(Pago p) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("efectuarPago(Pago) - start");
		}
		
		double resto;
		
		//MODIFICADO BECO: 10-JUN-2008
		//Agregada llamada al actualizador de precios restar del vuelto las donaciones
			
		resto = this.consultarMontoTrans() - obtenerMontoPagado()+ (new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().getDonacionesVenta();
		if (resto <= 0) {
			this.montoVuelto = MathUtil.roundDouble(-resto);
		}
		
		// Registramos la auditoría de esta función
		Sesion.setUbicacion("PAGO","efectuarPago");
		Auditoria.registrarAuditoria("Pago con " + p.getFormaPago().getNombre() + ". MontoPago " + p.getMonto(),'T');
		
		if (logger.isDebugEnabled()) {
			logger.debug("efectuarPago(Pago) - end");
		}
		return resto;

	}

	/**
	 * Calcula el monto que se debe pagar con esta nueva forma de pago, es decir, verifica si
	 * el nuevo pago sobrepasa lo que falta por pagar o si falta.
	 * @return double - Lo que se debe registrar como pago.
	 */
/*	private double calcularMontoPago(double monto) {
		
		// Calculamos la cantidad que falta por pagar
		double montoVenta = this.consultarMontoTrans();
		double montoPagado = 0;
		
		for (int i=0; i<pagos.size(); i++) {
			montoPagado += ((Pago)pagos.elementAt(i)).getMonto();
		}
		
		// Calculamos el monto por pagar
		double mtoPorPagar = montoVenta - montoPagado;
		return ((mtoPorPagar >= monto) ? monto : mtoPorPagar);
	}*/
	
	/* (non-Javadoc)
	 * @see com.becoblohm.cr.manejarventa.Transaccion#finalizarTransaccion()
	 */
	public void finalizarTransaccion() throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion, PrinterNotConnectedException {
		if (logger.isDebugEnabled()) {
			logger.debug("finalizarTransaccion() - start");
		}
		this.numCajaFinaliza = Sesion.getCaja().getNumero();
		this.numComprobanteFiscal = 0;
		this.numTransaccion = Sesion.getCaja().getNuevoNumTransaccion();
		this.lineasFacturacion = this.detallesTransaccion.size();
		this.horaFin = Sesion.getHoraSistema();
		this.estadoTransaccion = Sesion.IMPRIMIENDO;
		dirEntregaDom = "";
		numerosServicio = null;
		
		//Verificamos si existe una entrega a domicilio para preguntar si se desea hacer un cambio de direcciòn
		if(this.verificarTieneEntregaDom()) {			
			if(this.getCliente()!=null) {
				CambioDireccion cambDir = new CambioDireccion(this.getCliente().getDireccion());
				MensajesVentanas.centrarVentanaDialogo(cambDir);
				dirEntregaDom = (cambDir.getNuevaDireccion()).trim();
			}
		}
		
		try {
			//********* Actualización: 09/02/2007. BECO: Cambios por razones de SENIAT
			//********* Se agrega la verificación de clientes de tipo empleados.
			//********* para estos casos no se imprimen como parte de la factura sino que se coloca
			//********* como nota al pie de página. Esto por requerimiento del SENIAT 
			if (this.getCliente().getTipoCliente() == Sesion.COLABORADOR &&  this.getCliente().getNumFicha() != null && this.getCliente().getNumFicha() != "" && this.getCliente().getEstadoColaborador() == Sesion.ACTIVO) {
				this.impEmpleadoPieDePag = true;
			}
			//Actualización CENTROBECO por impresora fiscal GD4 26/02/2009
			this.montoAhorrado = (new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().sumarMontoAhorrado(this);
			this.montoDonaciones = (new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().sumarDonaciones(this, false)-(this.getMontoBase()+this.getMontoImpuesto());
			
			numerosServicio = BaseDeDatosVenta.registrarTransaccion(this, dirEntregaDom);
			// Mandamos a imprimir la factura, pero antes registramos la auditoría de esta función
			Sesion.setUbicacion("FACTURACION","finalizarTransaccion");
			Auditoria.registrarAuditoria("Emitiendo Factura Nro." + this.numTransaccion + " de Caja " + this.numCajaFinaliza,'T');
			try{
				//Actualización CENTROBECO por impresora fiscal GD4 26/02/2009
				while (CR.meVenta.getTransaccionPorImprimir()!=null || 
						CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
						MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()) {
					MensajesVentanas.aviso("Hay un documento en curso \nEspere un momento e intente nuevamente");
				}
				if (Sesion.impresoraFiscal) {
					CR.meVenta.setTransaccionPorImprimir(this);
					//MaquinaDeEstadoVenta.documentoNoFiscalPorImprimir=true;
					CRFiscalPrinterOperations.setImpresionFiscalEnCurso(true);
				}
				ManejadorReportesFactory.getInstance().imprimirFacturaVenta(this, false, CR.meVenta.getContribuyenteOrdinario());
				
				
			} catch(Exception ex){
				logger.error("finalizarTransaccion()", ex);
				ex.printStackTrace();
				if (Sesion.impresoraFiscal) {
					/*
					 * Si estamos trabajando con una impresora fiscal, 
					 * simplemente no puedo dejar esta transacción viva
					 */
					rollbackTransaccion();
					MensajesVentanas.mensajeError("Error al imprimir la factura. \nLa transacción fue abortada");
				} else {
					// Continuamos tranquilos
					MensajesVentanas.mensajeError("Error al imprimir la factura");
				}
			}
			
			//JGRATEROL
			//SE EXTRAJO LA IMPRESIÓN DE COMPROBANTES ADICIONALES DEL TRY CATCH DE LA IMPRESIÓN DE LA FACTURA
			//EL ROLLBACK GENERABA INCONSISTENCIA ENTRE LA PLACA Y EL LIBRO
			try{
				if(!Venta.regalosRegistrados.isEmpty())
					(new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().llamadaDeRegalos(2, this);
				//if(!Venta.promocionesRegistradas.isEmpty())(new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().ejecutarProductoComplementario(2,this);
			} catch(Exception e){
				logger.error(e);
				e.printStackTrace();
			} try {
				//JGRATEROL
				//Actualización BECO: Proyecto Bono Regalo Electrónico
				//Aplicación de promoción por realizar compra con BR
				CR.meServ.aplicarPromocionesBR(this.getPagos(), this.getCliente());
			} catch(Exception e){
				logger.error(e);
				e.printStackTrace();
			}
			try{
				//Actualización módulo de promociones. Chequeo forma de pagos especiales para imprimir el voucher correspondiente
				int tamanoPagos = this.getPagos().size();
				for(int i=0;i<tamanoPagos;i++){
					Pago p = (Pago)this.getPagos().elementAt(i);
					FormaDePago fp;
					if(p.getFormaPago().getTipo()==Sesion.TIPO_PAGO_BECO){
						if(p.getFormaPago().getCodigo().equalsIgnoreCase(Sesion.FORMA_PAGO_TRANSACCION_PREMIADA)){
							boolean existePagoMercadeo = false;
							for(int j=0;j<this.getPagos().size();j++){
								Pago pago = (Pago)this.getPagos().elementAt(j);
								if(pago.getFormaPago().getCodigo().equalsIgnoreCase(Sesion.FORMA_PAGO_PROMO_MERCADEO)){
									pago.setMonto(pago.getMonto()+p.getMonto());
									existePagoMercadeo=true;
								}
							}
							if(!existePagoMercadeo){
								fp = new FormaDePago(Sesion.FORMA_PAGO_PROMO_MERCADEO,
										Sesion.TIPO_PAGO_BECO,
										BaseDeDatosVenta.obtenerNombFormaPago(Sesion.FORMA_PAGO_PROMO_MERCADEO),
										null,
										false,false,false,false,false,false,false,false,0,0,0,false,false);
								Pago pagoNuevo = new Pago(fp,p.getMonto(),null,null,null,0,0,null);
								this.getPagos().addElement(pagoNuevo);
							}
							this.getPagos().set(i, null);
						} /*else {
							(new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().imprimirFormasDePagoEspeciales(this.getCliente(), p.getFormaPago(), p.getMonto(), this.getNumTransaccion());
						}*/
					}
				}
				while(this.getPagos().remove(null)); 
				for(int i=0;i<this.getPagos().size();i++){
					Pago p = (Pago)this.getPagos().elementAt(i);
					if(p.getFormaPago().getTipo()==Sesion.TIPO_PAGO_BECO){
						/*if(i==0) {
							while (CR.meVenta.getTransaccionPorImprimir()!=null || 
									CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
									MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()) {
								Thread.sleep(1000);
								if(MaquinaDeEstadoVenta.errorAtencionUsuario)
									MensajesVentanas.aviso("Hay un documento en curso \nEspere un momento e intente nuevamente");
							}
							if(Sesion.impresoraFiscal)
								MaquinaDeEstadoVenta.setDocumentoNoFiscalPorImprimir(true);
						}*/
							
							
						(new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().imprimirFormasDePagoEspeciales(this.getCliente(), p.getFormaPago(), p.getMonto(), this.getNumTransaccion());
					}
					
					if ((((Pago)getPagos().elementAt(i)).getFormaPago().getCodigo()).equals(Sesion.FORMA_PAGO_RETENCION)) {
						Auditoria.registrarAuditoria("Emitiendo Comprobante de retención de IVA de Caja " + this.numCajaFinaliza,'T');
						Auditoria.registrarAuditoria(getCodCajero(), getNumTransaccion(), "Comprobante de Retención de IVA", "FACTURACION", "efectuarRetencionIva");
						
						//Actualización CENTROBECO por impresora fiscal GD4 26/02/2009
						/*while (CR.meVenta.getTransaccionPorImprimir()!=null || 
								CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
								MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()) {
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								// TODO Bloque catch generado automáticamente
								e.printStackTrace();
							}
							if(MaquinaDeEstadoVenta.errorAtencionUsuario){
								MensajesVentanas.aviso("Hay un documento en curso \nEspere un momento e intente nuevamente");
							}
						}
						if (Sesion.impresoraFiscal) {
							MaquinaDeEstadoVenta.setDocumentoNoFiscalPorImprimir(true);
						}*/
						
						
						// Imprimimos el voucher por duplicado
						ManejadorReportesFactory.getInstance().imprimirVoucherPago(this.cliente,this.codAutorizante,
								p.getFormaPago(),p.getMonto(),2, this.getNumTransaccion());
					}
				}
			} catch(Exception e){
				logger.error(e);
				e.printStackTrace();
			}
			
			Venta.regalosRegistrados.clear();
			Venta.promocionesRegistradas.clear();
			Venta.donacionesRegistradas.clear();
			CR.me.getPromoMontoCantidad().clear();
		} catch (BaseDeDatosExcepcion e) {
			logger.error("finalizarTransaccion()", e);
			e.printStackTrace();
			Sesion.getCaja().recuperarNumTrans();
			throw (e);
		} catch (ConexionExcepcion e) {
			logger.error("finalizarTransaccion()", e);

			Sesion.getCaja().recuperarNumTrans();
			throw (e);
		}
		if (!Sesion.impresoraFiscal) {
			// No hay paquete con el esquema de impresión no fiscal
			commitTransaccion();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("finalizarTransaccion() - end");
		}
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void commitTransaccion() {
		if (logger.isDebugEnabled()) {
			logger.debug("commitTransaccion() - start");
		}

		try {
			// Debe obtener el numero de comprobante fiscal		
			// Actualizamos el estado de la transaccion
			if (Sesion.impresoraFiscal) {
				Sesion.getCaja().commitNumTransaccion();
			}
			BaseDeDatosVenta.actualizarEdoTransaccion(this.codTienda, this.fechaTrans, 
					this.numCajaInicia, this.numRegCajaInicia, this.numComprobanteFiscal, 
					Sesion.FINALIZADA, true);
			
			//Actualizacion BECO: Promociones
			//Invalidar cupones
			BaseDeDatosVenta.invalidarCupon(this);
			
		} catch (BaseDeDatosExcepcion e) {
			logger.error("commitTransaccion()", e);
		} catch (ConexionExcepcion e) {
			logger.error("commitTransaccion()", e);
			e.printStackTrace();
		} catch (Throwable e) {
			logger.error("commitTransaccion()", e);
			e.printStackTrace();
		} 
		// Sincronizamos la transacción
		new HiloSyncTransacciones().iniciar();
		// Verificamos si tiene alguna rebaja o cambio de precio
		for (Iterator<DetalleTransaccion> i = getDetallesTransaccion().iterator(); i.hasNext();)
		{
			DetalleTransaccion dt = (DetalleTransaccion)i.next();
			if (dt.isCondicionEspecial()) {
				if (dt.getCondicionVenta().equals(Sesion.condicionDesctoPorDefecto))
					Auditoria.registrarAuditoria(dt.getCodSupervisor(), getNumTransaccion(), 
						"CV=" + dt.getCondicionVenta() + " COD=" + dt.getProducto().getCodProducto() + 
						" PF=" + dt.getPrecioFinal(), "FACTURACION", "aplicarDesctoPorDefecto");
			}
		}
		
		// Registramos las auditorias de la venta
		// Verificamos si es una venta a colaborador
		if ((getCliente().getTipoCliente() == Sesion.COLABORADOR) && (getCliente().getEstadoColaborador() == Sesion.ACTIVO) && isAplicaDesctoEmpleado()) {
			Auditoria.registrarAuditoria(getAutorizanteAuditoria(), getNumTransaccion(), "FICHA="+getCliente().getNumFicha(), "FACTURACION", "aplicarDesctoEmpleado");
		} 
		// Verificamos si es una venta exenta de impuesto
		if (isVentaExenta()) {
			Auditoria.registrarAuditoria(getAutorizanteAuditoria(), getNumTransaccion(), "ID=" + getCliente().getCodCliente(), "FACTURACION", "facturarSinImpuesto");
		}
		
		// Revisamos si tiene como forma de pago Cargo a Cuenta
		for (int i = 0; i < getPagos().size(); i++) {
			Pago p = (Pago)getPagos().elementAt(i);
			if (((Pago)getPagos().elementAt(i)).getFormaPago().getTipo() == Sesion.TIPO_PAGO_CARGO_CUENTA) {
				String autorizante = p.getCodAutorizante();
				if (autorizante == null) {
					autorizante = getCodCajero();
				}
				Auditoria.registrarAuditoria(autorizante, getNumTransaccion(), 
						"PAGO=" + p.getFormaPago().getNombre() + 
						" MTO=" + p.getMonto(), "PAGO", "cancelarCargoCuenta");
			}
			//Verficamos si alguna forma de pago es "Retención de IVA" para imprimir el comprobante correspondiente
			/*if ((((Pago)getPagos().elementAt(i)).getFormaPago().getCodigo()).equals(Sesion.FORMA_PAGO_RETENCION)) {
				Auditoria.registrarAuditoria("Emitiendo Comprobante de retención de IVA de Caja " + this.numCajaFinaliza,'T');
				Auditoria.registrarAuditoria(getCodCajero(), getNumTransaccion(), "Comprobante de Retención de IVA", "FACTURACION", "efectuarRetencionIva");
				
				//Actualización CENTROBECO por impresora fiscal GD4 26/02/2009
				while (CR.meVenta.getTransaccionPorImprimir()!=null || 
						CRFiscalPrinterOperations.impresionFiscalEnCurso ||
						MaquinaDeEstadoVenta.documentoNoFiscalPorImprimir) {
					MensajesVentanas.aviso("Hay un documento en curso \nEspere un momento e intente nuevamente");
				}
				if (Sesion.impresoraFiscal) {
					MaquinaDeEstadoVenta.documentoNoFiscalPorImprimir=true;
				}
				
				// Imprimimos el voucher por duplicado
				ManejadorReportesFactory.getInstance().imprimirVoucherPago(this.cliente,this.codAutorizante,
						p.getFormaPago(),p.getMonto(),2, this.getNumTransaccion());
			}*/
		}		
		
		//Verificamos si hubo servicios asociados a la transacciòn para imprimir sus comprobantes
		if(numerosServicio != null) {	
			if(this.verificarTieneEntregaDom()) {
				int numEntDom = (numerosServicio.elementAt(0)).intValue();
				Auditoria.registrarAuditoria("Emitiendo Nota de Entrega a Domiclio Nro. " + numEntDom + " de Caja " + this.numCajaFinaliza,'T');
				Auditoria.registrarAuditoria(getCodCajero(), getNumTransaccion(), "Nota de Entrega Despacho a Domicilio", "FACTURACION", "imprimirNotaEntrega");
				//Se envía a imprimir la nota de entrega
				ManejadorReportesFactory.getInstance().imprimirNotaDeEntrega(this, numEntDom, dirEntregaDom);
			}
		
			if(this.verificarTieneClienteRetira()) {		
				int numDesp = (numerosServicio.elementAt(1)).intValue();
				Auditoria.registrarAuditoria("Emitiendo Nota de Despacho Nro. " + numDesp + " de Caja " + this.numCajaFinaliza, 'T');
				Auditoria.registrarAuditoria(getCodCajero(), getNumTransaccion(), "Nota de Entrega Cliente Retira", "FACTURACION", "imprimirNotaEntrega");
				try {
					ManejadorReportesFactory.getInstance().imprimirNotaDeDespacho(this, numDesp);
				} catch(Exception ex){
					logger.error("finalizarTransaccion()", ex);

					MensajesVentanas.mensajeError("Mensaje Temporal --> Falló Impresión");
				}
			}
		}
		/*
		 * 			Esta es la condicion que debe comprobarse
		 * 			para enviar algun dato al modulo de despacho
		 * 			en las próximas versiones de la caja
		 * 			
		if (this.verificarTieneDespacho()) {
		}
		*/
		
		
		if (logger.isDebugEnabled()) {
			logger.debug("commitTransaccion() - end");
		}
	}
	
	public void rollbackTransaccion() {
		if (logger.isDebugEnabled()) {
			logger.debug("rollbackTransaccion() - start");
		}

		try {
			// Actualizamos el estado de la transaccion
			Sesion.getCaja().rollbackNumTransaccion();
			BaseDeDatosVenta.actualizarEdoTransaccion(this.codTienda, this.fechaTrans, 
					this.numCajaInicia, this.numRegCajaInicia, 0, 
					Sesion.ABORTADA, true);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("rollbackTransaccion()", e);
		} catch (ConexionExcepcion e) {
			logger.error("rollbackTransaccion()", e);
		}		

		if (logger.isDebugEnabled()) {
			logger.debug("rollbackTransaccion() - end");
		}
	}

	/**
	 * Obtiene los descuentos posibles para un cambio de precio por articulo defectuoso.
	 * @param renglon Posicion del producto al que se le quiere obtener los descuentos..
	 * @return Vector Vector de descuentos posibles para el producto.
	 * @throws ProductoExcepcion Si el producto no se encuentra en el detalle de transaccion.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Double> obtenerDescuentos(int renglon) throws ProductoExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerDescuentos(int) - start");
		}

		Vector<Double> descuentos = null;
		double margen = 0.0;
		Producto prod = null;
				
		// Aquí se verifica si el producto existe en el detalle. Lanza excepcion si no es así
		DetalleTransaccion linea = (DetalleTransaccion) detallesTransaccion.elementAt(renglon);
		prod = linea.getProducto();
		
		// Calculamos el margen de ganancia
		margen = ((prod.getPrecioRegular() - prod.getCostoLista()) / prod.getPrecioRegular()) * 100;	
		if (margen >= 0) {
			descuentos = new Vector<Double>();
			
			double descto1 = (prod.getPrecioRegular() - ((prod.getPrecioRegular() * margen) / 100) / 4);
			descuentos.addElement(new Double(descto1));
			double descto2 = (prod.getPrecioRegular() - ((prod.getPrecioRegular() * margen) / 100) / 2);
			descuentos.addElement(new Double(descto2));
			double descto3 = (prod.getPrecioRegular() - (((prod.getPrecioRegular() * margen) / 100) * 3) / 4);
			descuentos.addElement(new Double(descto3));
		} else {
			throw (new ProductoExcepcion("Por margenes de ganancia no se le puede aplicar el descuento al producto"));	
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerDescuentos(int) - end");
		}
		return descuentos;	
	}

	/**
	 * Aplica el descuento por articulo defectuoso a un producto.
	 * @param renglon Posicion del producto al que se le queire cambiar el precio.
	 * @param descto Descuento a aplicar.
	 * @param esPorcentaje Indicador de si es un precio o un porcentaje de descuento.
	 * @param esPrecioGarantizado Indicador de si el descuento es por precio garantizado 
	 * 							  para colocarle esta condición de venta al nuevo detalle
	 * @throws ProductoExcepcion Si el producto no se encuentra en el detalle.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se comentaron variables sin uso
	* Fecha: agosto 2011
	*/
	public void aplicarDesctoPorDefecto(int renglon, double descto, float cantidad, boolean esPorcentaje, String codAutorizante, boolean esPrecioGarantizado) throws ProductoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarDesctoPorDefecto(int, double, float, boolean, String, boolean) - start");
		}

		DetalleTransaccion linea = (DetalleTransaccion)this.getDetallesTransaccion().elementAt(renglon);
		//Vector paraDescuento = new Vector(1);
		double impto = 0.0;
		//double pf = descto;

		if (codAutorizante == null) {
			codAutorizante = Sesion.getUsuarioActivo().getNumFicha();
		}

		
		//Obtenemos el producto
		Producto prod = linea.getProducto();
		
		if (esPorcentaje) {					
			// Se calcula el nuevo precio del detalle

			double mtoDcto = prod.getPrecioRegular() * (descto / 100);
			double pFinal = prod.getPrecioRegular() - mtoDcto;
			pFinal = MathUtil.roundDouble(pFinal);
			//pf = pFinal;
			
			// Calculamos el impuesto
			impto = this.calcularImpuesto(prod, pFinal);
			
			// Creamos el nuevo detalle con el producto y su nuevo precio y lo agregamos al vector de detalles
			if (esPrecioGarantizado){ //Se verifica si eel descuento vino por Precio garntizado para colocar esta condición de venta al nuevo detalle
				DetalleTransaccion detalleNuevo = new DetalleTransaccion(linea.getCodVendedor(), linea.getProducto(), cantidad, Sesion.condicionDesctoPrecioGarantizado, codAutorizante, pFinal, impto, linea.getTipoCaptura(), -1, linea.getTipoEntrega());
				//ACTUALIZACION BECO POR MODULO DE PROMOCIONES
				detalleNuevo.addCondicion(new CondicionVenta(Sesion.condicionDesctoPrecioGarantizado,0,descto,"Rebaja Manual",0));
				
				detalleNuevo.setCodSupervisor(codAutorizante);
				detallesTransaccion.addElement(detalleNuevo);
				
				//Enviamos al visor la información del descuento aplicado
				String precio = df.format(pFinal+impto);
				String cantXprecio = Sesion.condicionDesctoPrecioGarantizado + " " + df.format(cantidad) + " X " +  precio;
				try{ CR.crVisor.enviarString(((Producto)linea.getProducto()).getDescripcionCorta(), 0, cantXprecio, 2); }
				catch(Exception ex){
					logger
							.error(
									"aplicarDesctoPorDefecto(int, double, float, boolean, String, boolean)",
									ex);
}
				
				// Registramos la auditoría de esta función
				Sesion.setUbicacion("FACTURACION","aplicarPrecioGarantizado");
				Auditoria.registrarAuditoria("Rebaja por precio garantizado en Caja " + Sesion.getNumCaja() + ", Tienda " + Sesion.getNumTienda() + ", Prod. " + prod.getCodProducto() + ", P.Final. " + descto,'T');
			} else {
				DetalleTransaccion detalleNuevo = new DetalleTransaccion(linea.getCodVendedor(), linea.getProducto(), cantidad, Sesion.condicionDesctoPorDefecto, codAutorizante, pFinal, impto, linea.getTipoCaptura(), -1, linea.getTipoEntrega());
				
				//ACTUALIZACION BECO POR MODULO DE PROMOCIONES
				detalleNuevo.addCondicion(new CondicionVenta(Sesion.condicionDesctoPorDefecto,0,descto,"Rebaja Manual",0));
				
				detalleNuevo.setCodSupervisor(codAutorizante);
				detallesTransaccion.addElement(detalleNuevo);
				
				//Enviamos al visor la información del descuento aplicado
				String precio = df.format(pFinal+impto);
				String cantXprecio = Sesion.condicionDesctoPorDefecto + " " + df.format(cantidad) + " X " +  precio;
				try{ CR.crVisor.enviarString(((Producto)linea.getProducto()).getDescripcionCorta(), 0, cantXprecio, 2); }
				catch(Exception ex){
					logger
							.error(
									"aplicarDesctoPorDefecto(int, double, float, boolean, String, boolean)",
									ex);
}
				
				// Registramos la auditoría de esta función
				Sesion.setUbicacion("FACTURACION","aplicarDesctoPorDefecto");
				Auditoria.registrarAuditoria("Rebaja Precio a Precio en Caja " + Sesion.getNumCaja() + ", Tienda " + Sesion.getNumTienda() + ", Prod. " + prod.getCodProducto() + ", P.Final. " + descto,'T');		
			}
			
			// Se elimina el producto del detalle original
			this.anularProducto(renglon,cantidad);
		} else {			
			// Calculamos el impuesto
			descto = MathUtil.roundDouble(descto);
			impto = this.calcularImpuesto(prod, descto);
			
			// Creamos el nuevo detalle con el producto y su nuevo precio y lo agregamos al vector de detalles
			if(esPrecioGarantizado) { //Se verifica si eel descuento vino por Precio garntizado para colocar esta condición de venta al nuevo detalle
				DetalleTransaccion detalleNuevo = new DetalleTransaccion(linea.getCodVendedor(), linea.getProducto(), cantidad, Sesion.condicionDesctoPrecioGarantizado, codAutorizante, descto, impto, linea.getTipoCaptura(), -1, linea.getTipoEntrega());
				
				//ACTUALIZACION BECO POR MODULO DE PROMOCIONES
				detalleNuevo.addCondicion(new CondicionVenta(Sesion.condicionDesctoPrecioGarantizado,0,0,"Rebaja Manual",0));
				
				detalleNuevo.setCodSupervisor(codAutorizante);
				detallesTransaccion.addElement(detalleNuevo);
				
				//Enviamos al visor la información del descuento aplicado
				String precio = df.format(descto+impto);
				String cantXprecio = Sesion.condicionDesctoPrecioGarantizado + " " + df.format(cantidad) + " X " +  precio;
				try{ CR.crVisor.enviarString(((Producto)linea.getProducto()).getDescripcionCorta(), 0, cantXprecio, 2); }
				catch(Exception ex){
					logger
							.error(
									"aplicarDesctoPorDefecto(int, double, float, boolean, String, boolean)",
									ex);
}
				
				// Registramos la auditoría de esta función
				Sesion.setUbicacion("FACTURACION","aplicarPrecioGarantizado");
				Auditoria.registrarAuditoria("Rebaja por precio garantizado en Caja " + Sesion.getNumCaja() + ", Tienda " + Sesion.getNumTienda() + ", Prod. " + prod.getCodProducto() + ", P.Final. " + descto,'T');
			} else {
				DetalleTransaccion detalleNuevo = new DetalleTransaccion(linea.getCodVendedor(), linea.getProducto(), cantidad, Sesion.condicionCambioPrecio, codAutorizante, descto, impto, linea.getTipoCaptura(), -1, linea.getTipoEntrega());
				
				//ACTUALIZACION BECO POR MODULO DE PROMOCIONES
				detalleNuevo.addCondicion(new CondicionVenta(Sesion.condicionCambioPrecio,0,0,"Rebaja Manual",0));
				
				detalleNuevo.setCodSupervisor(codAutorizante);
				detallesTransaccion.addElement(detalleNuevo);
				
				//Enviamos al visor la información del descuento aplicado
				String precio = df.format(descto+impto);
				String cantXprecio = Sesion.condicionCambioPrecio + " " + df.format(cantidad) + " X " +  precio;
				try{ CR.crVisor.enviarString(((Producto)linea.getProducto()).getDescripcionCorta(), 0, cantXprecio, 2); }
				catch(Exception ex){
					logger
							.error(
									"aplicarDesctoPorDefecto(int, double, float, boolean, String, boolean)",
									ex);
				}
				
				// Registramos la auditoría de esta función
				Sesion.setUbicacion("FACTURACION","aplicarDesctoPorDefecto");
				Auditoria.registrarAuditoria("Rebaja Precio a Precio en Caja " + Sesion.getNumCaja() + ", Tienda " + Sesion.getNumTienda() + ", Prod. " + prod.getCodProducto() + ", P.Final. " + descto,'T');
			}
			
			// Se elimina el producto del detalle original
			this.anularProducto(renglon,cantidad);
		}
		

		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarDesctoPorDefecto(int, double, float, boolean, String, boolean) - end");
		}
	}

	/**
	 * Anula la transaccion Actual.
	 * @throws BaseDeDatosExcepcion - Si ocurre un error al registrar la anulacion de venta en la BD.
	 * @throws ExcepcionCr - Si ocurre  un error al ingresar el registro de Auditoria.
	 */
	public void anularVentaActiva() throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("anularVentaActiva() - start");
		}

		// Registramos la auditoría de esta función
		Sesion.setUbicacion("FACTURACION","anularVentaActiva");
		Auditoria.registrarAuditoria("Anulada Transaccion con RegInicial " + this.getNumRegCajaInicia() + " de Caja " + this.getNumCajaInicia(),'T');

		if (logger.isDebugEnabled()) {
			logger.debug("anularVentaActiva() - end");
		}
	}

	
	/**
	 * Aplica la condición de venta"Empaque" a los productos especificados
	 * @param prod Producto al que se le aplicara el descuento a empaque.
	 * @param detallesN Vector de posiciones del producto .
	 * @param prodsEmpaque Cantidad de productos para el empaque
	 * @param posicionMejor Entero que indica la posición que posee el mejor descuento
	 * @param dctoActual Vector que indica el descuento actual que posee el producto
	 * @return Vector - Vector de los productos con la condición de venta empaque aplicada
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Object> aplicarCondicionEmpaque(Producto prod, 
			Vector<Integer> detallesN, float prodsEmpaque, int posicionMejor, 
			Vector<Object> dctoActual) throws XmlExcepcion, FuncionExcepcion, 
			BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarCondicionEmpaque(Producto, Vector, float, int, Vector) - start");
		}

		Vector<Integer> nuevosEmpaques = new Vector<Integer>();
		String vendedor; 
		String supervisor; 
		String tipCaptura; 
		String tipoEntrega;
		float cantidadAsignar;
		int puntoDeChequeo = 0;
		float cantidadDetalleActual;
		Vector<Object> resultado = new Vector<Object>(2);
		
		double pFinal = ((Double)dctoActual.elementAt(0)).doubleValue();
		
		while (prodsEmpaque > 0) {
			// Si existen empaques, creo el nuevo detalle con el empaque por cada detallesN
			vendedor = ((DetalleTransaccion)detallesTransaccion.elementAt((detallesN.elementAt(puntoDeChequeo)).intValue())).getCodVendedor();
			supervisor = ((DetalleTransaccion)detallesTransaccion.elementAt((detallesN.elementAt(puntoDeChequeo)).intValue())).getCodSupervisor();
			tipCaptura = ((DetalleTransaccion)detallesTransaccion.elementAt((detallesN.elementAt(puntoDeChequeo)).intValue())).getTipoCaptura();
			tipoEntrega = ((DetalleTransaccion)detallesTransaccion.elementAt((detallesN.elementAt(puntoDeChequeo)).intValue())).getTipoEntrega();
			cantidadDetalleActual = ((DetalleTransaccion)detallesTransaccion.elementAt((detallesN.elementAt(puntoDeChequeo)).intValue())).getCantidad();
			if (prodsEmpaque >= cantidadDetalleActual) {
				cantidadAsignar = cantidadDetalleActual;
				puntoDeChequeo++;
			} else {
				cantidadAsignar = prodsEmpaque;
				((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).setCantidad(cantidadDetalleActual - cantidadAsignar);
			}
			prodsEmpaque -= cantidadAsignar;
			pFinal = MathUtil.roundDouble(pFinal);
			DetalleTransaccion nuevoDetalleTrans = new DetalleTransaccion(vendedor, prod, cantidadAsignar,
						(String)dctoActual.elementAt(1), supervisor, pFinal, this.calcularImpuesto(prod, pFinal),
						tipCaptura, -1, tipoEntrega);
			
			//ACTUALIZACION BECO POR MODULO DE PROMOCIONES
			try{
				nuevoDetalleTrans.addCondicion((CondicionVenta)dctoActual.elementAt(3));
			}catch(Exception e){
				//Nada la extesion de promociones no esta activa
			}
			
			detallesTransaccion.addElement(nuevoDetalleTrans);
			nuevosEmpaques.addElement(new Integer(detallesTransaccion.size() - 1));
		}
		
		if (nuevosEmpaques.size() > 0) {
			// Registramos la auditoría de esta función
			Sesion.setUbicacion("FACTURACION","aplicarCondicionEmpaque");
			Auditoria.registrarAuditoria("Aplicada condicion Empaque Prod. " + prod.getCodProducto() + " P.Reg. " + prod.getPrecioRegular() + " P.Final " + pFinal,'T');
		}
		
		resultado.addElement(nuevosEmpaques);
		resultado.addElement(new Integer(puntoDeChequeo));
	
		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarCondicionEmpaque(Producto, Vector, float, int, Vector) - end");
		}
		return resultado;
	}
	
	/**
	 * Aplica la condición de venta"Empaque" a los productos especificados para el caso de descuento a empaque acumulativo
	 * @param prod Producto al que se le aplicara el descuento a empaque.
	 * @param detallesN Vector de posiciones del producto .
	 * @param prodsEmpaque Cantidad de productos para el empaque
	 * @param posicionMejor Entero que indica la posición que posee el mejor descuento
	 * @param dctoActual Vector que indica el descuento actual que posee el producto
	 * @return Vector - Vector de los productos con la condición de venta empaque aplicada
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Object> aplicarCondicionEmpaque(Producto prod, 
			Vector<Integer> detallesN) throws XmlExcepcion, FuncionExcepcion, 
			BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("aplicarCondicionEmpaque(Producto, Vector) - start");
		}

		Vector<Integer> nuevosEmpaques = new Vector<Integer>();
		String vendedor; 
		String supervisor; 
		String tipCaptura; 
		String tipoEntrega;
		float cantidadAsignar;
		int puntoDeChequeo = 0;
		float cantidadDetalleActual;
		Vector<Object> resultado = new Vector<Object>(2);
		float productos = 0;
		double pFinal = 0;
		
		for (int i=0; i<detallesN.size(); i++) {
			productos += ((DetalleTransaccion) detallesTransaccion.elementAt(((Integer)detallesN.elementAt(i)).intValue())).getCantidad();
			pFinal = ((DetalleTransaccion) detallesTransaccion.elementAt(((Integer)detallesN.elementAt(i)).intValue())).getPrecioFinal();
		}
			
		double dctoEmpaque = pFinal * (prod.getDesctoVentaEmpaque()/100);
		pFinal -= dctoEmpaque;
		
		float prodsEmpaque = productos - (productos % prod.getCantidadVentaEmpaque());
		
		if (dctoEmpaque > 0) {
			while (prodsEmpaque > 0) {
				//No aplicar empaque si ya esta en un combo el detalle
				if(!((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).contieneAlgunaCondicion(Sesion.condicionesCombo)){
					// Si existen empaques, creo el nuevo detalle con el empaque por cada detallesN
					vendedor = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCodVendedor();
					supervisor = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCodSupervisor();
					tipCaptura = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getTipoCaptura();
					tipoEntrega = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getTipoEntrega();
					cantidadDetalleActual = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCantidad();
					DetalleTransaccion dt = (DetalleTransaccion)((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).clone();
					
					String condVenta = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCondicionVenta();
				
					if (prodsEmpaque >= cantidadDetalleActual) {
						cantidadAsignar = cantidadDetalleActual;
						detallesTransaccion.set(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue(), null);
						detallesN.set(puntoDeChequeo, null);
						
						puntoDeChequeo++;
					} else {
						cantidadAsignar = prodsEmpaque;
						((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).setCantidad(cantidadDetalleActual - cantidadAsignar);
					}
					prodsEmpaque -= cantidadAsignar;
					double descEmpleado = 0.0; 
					
	
					// Chequeamos las condiciones de Venta
					if (condVenta.equals(Sesion.condicionPromocion)) {
						condVenta = Sesion.condicionEmpaquePromocion;
					} else {
						if (condVenta.equals(Sesion.condicionNormal)) {
							condVenta = Sesion.condicionEmpaque;
						} else {
							if (condVenta.equals(Sesion.condicionDesctoEmpleado)) {
								condVenta = Sesion.condicionDesctoEmpEmpaque;
								descEmpleado = prod.getPrecioRegular() * Sesion.getDesctoVentaEmpleado() / 100; 
							} else {
								if (condVenta.equals(Sesion.condicionDesctoEmpProm)) {
									condVenta = Sesion.condicionEmpaquePromocionEmpleado;
								} 
							}
						}
					}
					pFinal = MathUtil.roundDouble(pFinal);
					DetalleTransaccion nuevoDetalleTrans = new DetalleTransaccion(vendedor, prod, cantidadAsignar,
								condVenta, supervisor, pFinal, this.calcularImpuesto(prod, pFinal),
								tipCaptura, -1, tipoEntrega);
					
					//ACTUALIZACION BECO POR MODULO DE PROMOCIONES
					nuevoDetalleTrans.setCondicionesVentaPromociones(dt.getCondicionesVentaPromociones());
					nuevoDetalleTrans.addCondicion(new CondicionVenta(Sesion.condicionEmpaque));
					
					
					nuevoDetalleTrans.setDctoEmpleado(descEmpleado);
					detallesTransaccion.addElement(nuevoDetalleTrans);
					nuevosEmpaques.addElement(new Integer(detallesTransaccion.size() - 1));
					
				}
			}
			
			if (nuevosEmpaques.size() > 0) {
				// Registramos la auditoría de esta función
				Sesion.setUbicacion("FACTURACION","aplicarCondicionEmpaque");
				Auditoria.registrarAuditoria("Aplicada condicion Empaque Prod. " + prod.getCodProducto() + " P.Reg. " + prod.getPrecioRegular() + " P.Final " + pFinal,'T');
			}
		}
		
		// Se eliminan los elementos nulos que quedan en el vector de detallesN
		while (detallesN.removeElement(null));
		
		resultado.addElement(nuevosEmpaques);
		resultado.addElement(new Integer(puntoDeChequeo));
		
		if (logger.isDebugEnabled()) {
			logger.debug("aplicarCondicionEmpaque(Producto, Vector) - end");
		}
		return resultado;
	}
	
	/**
	 * Aplica la condición de venta "Promocion" a los productos especificados
	 * @param prod Producto al que se le aplicara el descuento a empaque.
	 * @param detallesN Vector de posiciones del producto .
	 * @param prodsEmpaque Cantidad de productos para el empaque
	 * @param posicionMejor Entero que indica la posición que posee el mejor descuento
	 * @param dctoActual Vector que indica el descuento actual que posee el producto
	 * @param puntoDeChequeo Entero que indica la posición en el detalle detransacción chequeado hasta los momentos
	 * @return DetalleTransaccion - Nuevo detalle de transacción con los productos a los que se les aplicó la promoción
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public DetalleTransaccion aplicarCondicionPromocion(Producto prod, 
			Vector<Integer> detallesN, Vector<Object> dctoActual, int puntoDeChequeo) throws 
			XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, 
			ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarCondicionPromocion(Producto, Vector, Vector, int) - start");
		}
		double pFinal=0;
		DetalleTransaccion dt = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue()));
		Vector<String> condicionesPromocion = new Vector<String>();
		condicionesPromocion.addElement(Sesion.condicionPromocion);
		Vector<String> condicionesDescuentoEnProducto = new Vector<String>();
		condicionesDescuentoEnProducto.addElement(Sesion.condicionDescuentoEnProductos);
		DetalleTransaccion nuevoDetalleTrans = null;
		if(!dt.contieneAlgunaCondicion(condicionesPromocion)){
			pFinal = ((Double)dctoActual.elementAt(0)).doubleValue();
			dt.removeCondicion(new CondicionVenta(Sesion.condicionDesctoEmpleado));
			dt.setMontoDctoEmpleado(0);
			/*if(!dt.contieneAlgunaCondicion(Sesion.condicionesCombo)){
				pFinal = ((Double)dctoActual.elementAt(0)).doubleValue();
				dt.removeCondicion(new CondicionVenta(Sesion.condicionDesctoEmpleado));
				dt.setMontoDctoEmpleado(0);
			} else*/ {
				//ACTUALIZACION BECO POR MODULO DE PROMOCIONES
				if(((CondicionVenta)dctoActual.elementAt(3)).getPorcentajeDescuento()!=0){
					double pFinalNuevo = dt.getProducto().getPrecioRegular(); 
					pFinalNuevo=pFinalNuevo-pFinalNuevo*((CondicionVenta)dctoActual.elementAt(3)).getPorcentajeDescuento()/100;
					//***********************
					//Recalculo las promociones que ya tenía aplicadas el detalle
					//en el mismo orden en que se aplican normalmente
					//**********************
					//Combos
					CondicionVenta primeraCondicionCombo = (CondicionVenta)dt.getPrimeraCondicion(Sesion.condicionesComboPrecioFinal);
					double montoDctoCombo = 0;
					if(primeraCondicionCombo!=null)
						montoDctoCombo = pFinalNuevo*primeraCondicionCombo.getPorcentajeDescuento()/100;
					pFinal= pFinalNuevo-montoDctoCombo;
					dt.setMontoDctoCombo(montoDctoCombo);
					
					//Empleado y corporativo: Simplemente eliminarlos, igual se vuelve a calcular
					//dt.removeCondicion(new CondicionVenta(Sesion.condicionDesctoEmpleado));
					//dt.setCondicionVenta(dt.construirCondicionVentaString());
					//pFinalNuevo = pFinalNuevo-dt.getMontoDctoEmpleado();
					//dt.setMontoDctoEmpleado(0);
					//dt.removeCondicion(new CondicionVenta(Sesion.condicionDescuentoCorporativo));
					//dt.setCondicionVenta(dt.construirCondicionVentaString());
					//pFinalNuevo = pFinalNuevo - dt.getMontoDctoCorporativo();
					//dt.setMontoDctoCorporativo(0);
					//Ahorro en compra
					try{
						double montoDctoEnProductos = pFinal*((CondicionVenta)dt.getPrimeraCondicion(condicionesDescuentoEnProducto)).getPorcentajeDescuento()/100;
						pFinal-=montoDctoEnProductos;
					} catch (NullPointerException e){
						//Nada, no hay descuento en productos en este detalle
					}
					
				} else{
					pFinal= ((Double)dctoActual.elementAt(0)).doubleValue();
					//Recalculo las promociones que ya tenía aplicadas el detalle
					//en el mismo orden en que se aplican normalmente
					//***********************
					//Combos
					try{
						double montoDctoCombo = pFinal*((CondicionVenta)dt.getPrimeraCondicion(Sesion.condicionesComboPrecioFinal)).getPorcentajeDescuento()/100;
						pFinal= pFinal-montoDctoCombo;
						dt.setMontoDctoCombo(montoDctoCombo);
					} catch(NullPointerException e){
						//Nada, no hay descuento por combo en este detalle
					}
					//Empleado y corporativo: Simplemente eliminarlos, igual se vuelve a calcular
					//dt.removeCondicion(new CondicionVenta(Sesion.condicionDesctoEmpleado));
					//dt.setMontoDctoEmpleado(0);
					//dt.removeCondicion(new CondicionVenta(Sesion.condicionDescuentoCorporativo));
					//dt.setMontoDctoCorporativo(0);
					//Ahorro en compra
					try{
						double montoDctoEnProductos = pFinal*((CondicionVenta)dt.getPrimeraCondicion(condicionesDescuentoEnProducto)).getPorcentajeDescuento()/100;
						pFinal-=montoDctoEnProductos;
					} catch (NullPointerException e){
						//Nada, no hay descuento en productos en este detalle
					}
				}
			}
			pFinal = pFinal<dt.getPrecioFinal()?MathUtil.roundDouble(pFinal):dt.getPrecioFinal();
			dt.setPrecioFinal(pFinal);
			dt.setMontoImpuesto(this.calcularImpuesto(prod, pFinal));
			nuevoDetalleTrans = (DetalleTransaccion)dt.clone(); 
			
			try{
				Vector<String> condicionP = new Vector<String>();
				condicionP.addElement(Sesion.condicionPromocion);
				if(!nuevoDetalleTrans.contieneAlgunaCondicion(condicionP)){
					nuevoDetalleTrans.addCondicion((CondicionVenta)dctoActual.elementAt(3));
					nuevoDetalleTrans.setCondicionVenta(nuevoDetalleTrans.construirCondicionVentaString());
				}
				((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).setCantidad(0);
				((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).setCantidadCambiada(false);
				((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).setVariacion(0);
				detallesAEliminar.addElement(detallesN.elementAt(puntoDeChequeo));
			}catch(Exception e) {
				//Nada, la extensión de promociones no está activa
			}
			
			// Registramos la auditoría de esta función
			Sesion.setUbicacion("FACTURACION","aplicarCondicionPromocion");
			Auditoria.registrarAuditoria("Aplicada Promo " + nuevoDetalleTrans.getCodPromocion() + " Prod " + prod.getCodProducto() + " P.Reg. " + prod.getPrecioRegular() + " P.Prom. " + nuevoDetalleTrans.getPrecioFinal(),'T');
		}
		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarCondicionPromocion(Producto, Vector, Vector, int) - end");
		}
		if(nuevoDetalleTrans==null){
			nuevoDetalleTrans = (DetalleTransaccion)dt.clone();
			dt.setCantidad(0);
			dt.setCantidadCambiada(false);
			dt.setVariacion(0);
		}
		return nuevoDetalleTrans;
	}
	
	/**
	 * Aplica la condición de venta "Descuento a COLABORADOR" a los productos especificados
	 * @param prod Producto al que se le aplicara el descuento a empaque.
	 * @param posicionDetalles Vector de posiciones donde se encuentran los detalles de la transacción que se desean manejar
	 * @param prodsEmpaque Cantidad de productos para el empaque
	 * @param posicionMejor Entero que indica la posición que posee el mejor descuento
	 * @param dctoActual Vector que indica el descuento actual que posee el producto
	 * @param puntoDeChequeo Entero que indica la posición en el detalle detransacción chequeado hasta los momentos
	 * @return DetalleTransaccion - Nuevo detalle de transacción con los productos a los que se les aplicó el descuento a COLABORADOR
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public DetalleTransaccion aplicarCondicionDescuentoEmpleado (
			Producto prod, Vector<Integer> posicionDetalles, 
			Vector<Object> dctoActual, int puntoDeChequeo) throws 
			XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, 
			ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarCondicionDescuentoEmpleado(Producto, Vector, Vector, int) - start");
		}

		DetalleTransaccion nuevoDetalleTrans = null;
		double pFinal = 0.0;
		double montoDctoEmpleado = 0.0;
		
		Vector<String> condicionEmpleado = new Vector<String>();
		condicionEmpleado.addElement(Sesion.condicionDesctoEmpleado);
		
		if (Sesion.desctosAcumulativos){
			double precioAnterior;
			double nvoImpuesto;
			String condVentaAnterior;
			
			// Calculamos la condición de venta acumulativa para los productos especificados
			for (int i=0; i<posicionDetalles.size(); i++) {
				//ACTUALIZACION BECO: MODULO DE PROMOCIONES:
				// LOS DETALLES EN COMBO NO SE INICIALIZAN QUITANDOLE EL DESCUENTO EMPLEADO
				if(!((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).contieneAlgunaCondicion(condicionEmpleado)) {
					precioAnterior = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).getPrecioFinal();
					condVentaAnterior = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).getCondicionVenta();
					montoDctoEmpleado = (precioAnterior * Sesion.getDesctoVentaEmpleado()) / 100;
					pFinal = MathUtil.roundDouble(precioAnterior - montoDctoEmpleado);
					nvoImpuesto = this.calcularImpuesto(prod, pFinal);
					((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).setPrecioFinal(pFinal);
					((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).setMontoImpuesto(nvoImpuesto);
					((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).setDctoEmpleado(montoDctoEmpleado);
					
									
					// Chequeamos las condiciones de Venta
					if (condVentaAnterior.equals(Sesion.condicionEmpaque)) {
						((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).setCondicionVenta(Sesion.condicionDesctoEmpEmpaque);
					} else if (condVentaAnterior.equals(Sesion.condicionNormal)) {
						((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).setCondicionVenta(Sesion.condicionDesctoEmpleado);
					} else if (condVentaAnterior.equals(Sesion.condicionPromocion)) {
						((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).setCondicionVenta(Sesion.condicionDesctoEmpProm);
					} else if (condVentaAnterior.equals(Sesion.condicionDesctoPorDefecto)) {
						((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).setCondicionVenta(Sesion.condicionDesctoEmpDesctoDefect);
					} else if (condVentaAnterior.equals(Sesion.condicionCambioPrecio)) {
						((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).setCondicionVenta(Sesion.condicionDesctoEmpCambPrec);
					} else if (condVentaAnterior.equals(Sesion.condicionDesctoPrecioGarantizado)) {
						((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).setCondicionVenta(Sesion.condicionDesctoEmpPrecioGarantizado);
					}
					//ACTUALIZACION BECO: 22/07/2008
					try{
						DetalleTransaccion detalle =((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue()));
						CondicionVenta nuevaCondicionVenta = new CondicionVenta(Sesion.condicionDesctoEmpleado,0, Sesion.getDesctoVentaEmpleado(),"Descuento Empleado", 1);
						detalle.addCondicion(nuevaCondicionVenta);
						detalle.setCondicionVenta(detalle.construirCondicionVentaString());
					}catch(Exception e){
						//Nada, la extension del módulo de promociones no esta activa
					}
				}
					
			}
		} else {
			if(!((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)posicionDetalles.elementAt(puntoDeChequeo)).intValue())).contieneAlgunaCondicion(condicionEmpleado)) {
				String vendedor = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)posicionDetalles.elementAt(puntoDeChequeo)).intValue())).getCodVendedor();
				String supervisor = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)posicionDetalles.elementAt(puntoDeChequeo)).intValue())).getCodSupervisor();
				String tipCaptura = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)posicionDetalles.elementAt(puntoDeChequeo)).intValue())).getTipoCaptura();
				String tipoEntrega = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)posicionDetalles.elementAt(puntoDeChequeo)).intValue())).getTipoEntrega();
				pFinal = ((Double)dctoActual.elementAt(0)).doubleValue();
				montoDctoEmpleado = prod.getPrecioRegular() - pFinal;
				float cantidadAsignar = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)posicionDetalles.elementAt(puntoDeChequeo)).intValue())).getCantidad();
				pFinal = MathUtil.roundDouble(pFinal);
				nuevoDetalleTrans = new DetalleTransaccion(vendedor, prod, cantidadAsignar,
										(String)dctoActual.elementAt(1), supervisor, pFinal,
										this.calcularImpuesto(prod, pFinal), tipCaptura,
										-1, tipoEntrega);
				nuevoDetalleTrans.setDctoEmpleado(montoDctoEmpleado);
				
				//ACTUALIZACION BECO: 22/07/2008
				try{
					nuevoDetalleTrans.addCondicion(((CondicionVenta)dctoActual.elementAt(3)));
					nuevoDetalleTrans.setCondicionVenta(nuevoDetalleTrans.construirCondicionVentaString());
				}catch(Exception e){
					//Nada, la extension del módulo de promociones no esta activa
				}
			}
		}
		
		if (posicionDetalles.size() > 0) {
			// Registramos la auditoría de esta función
			Sesion.setUbicacion("FACTURACION","aplicarCondicionDescuentoEmpleado");
			Auditoria.registrarAuditoria("Aplicado Descuento a Empleado a " + this.getCliente().getCodCliente() + " Prod." + prod.getCodProducto() + ". PFinal." + pFinal ,'T');
		}
			
		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarCondicionDescuentoEmpleado(Producto, Vector, Vector, int) - end");
		}
		return nuevoDetalleTrans;
	
	}
	
	/**
	 * Obtiene los pagos realizados a la venta.
	 * @return Vector - Vector de pagos realizados.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Pago> getPagos() {
		if (logger.isDebugEnabled()) {
			logger.debug("getPagos() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getPagos() - end");
		}
		return pagos;
	}
	
	/**
	 * Colocar una factura en espera. Establece los parámetros de espera y la registra a la base de datos.
	 * @param identificacion Codigo de la factura en espera
	 * @throws ExcepcionCr Si ocurre un error en el registro de la factura en espera
	 */
	public void colocarEnEspera(String identificacion) throws BaseDeDatosExcepcion, ConexionExcepcion, PagoExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("colocarEnEspera(String) - start");
		}

		if (this.getPagos().isEmpty()) {
			//registramos la venta en la Base de Datos en estado de espera
			this.lineasFacturacion = this.detallesTransaccion.size();
			this.horaFin = Sesion.getHoraSistema();
			this.estadoTransaccion = Sesion.ESPERA;
			this.setDuracion(Control.getCronometro(this.inicio, this.getDuracion()));
			BaseDeDatosVenta.colocarEnEspera(this, identificacion);
		} else
			throw new PagoExcepcion("No se pueden colocar facturas en espera con pagos asociados");

		if (logger.isDebugEnabled()) {
			logger.debug("colocarEnEspera(String) - end");
		}
	}
	
	/**
	* Método actualizarPreciosDetalles.
	* 		Actuliza los precios de todos los detalles contenidos en el vector de detalles de transacción.
	* 		Con el boolean sabemos si se debe aplicar el descuento a COLABORADOR a los detalles de la venta.
	* @param desctoEmpleado es true si se debe aplicar el descto, es false si no
	* @throws BaseDeDatosExcepcion - Si hay falla con la conexión a la BD
	* @throws MaquinaDeEstadoExcepcion - Si el estado es incorrecto
	* @throws AutorizacionExcpecion - Si no se pudo autorizar la función
	*/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void actualizarPreciosDetalle(boolean desctoEmpleado) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarPrecios(boolean) - start");
		}

		Vector<DetalleTransaccion> productos = new Vector<DetalleTransaccion>(); 
		boolean registrado= false;
		String codP = "";
	
		//	Se coloca en TRUE o FALSE la variable indicaDescuentoEmpleado de la this para que se tome en cuenta (o no) este descuento.
		if((CR.meVenta.getDevolucion()!=null && CR.meVenta.getDevolucion().getVentaOriginal().isAplicaDesctoEmpleado()))
			this.setAplicaDesctoEmpleado(true);
		else 
			this.setAplicaDesctoEmpleado(desctoEmpleado);
		Vector<String> condicionesCorporativo = new Vector<String>();
		condicionesCorporativo.addElement(Sesion.condicionDescuentoCorporativo);
		
		// Recorremos el vector de detalles para obtener los distintos productos contenidos en el mismo 
		for(int i=0; i<this.getDetallesTransaccion().size(); i++){
			//Quitando el descuento de empleado para que el algoritmo lo coloque desde cero
			if(!((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).isCondicionEspecial() && 
					!((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).contieneAlgunaCondicion(condicionesCorporativo)) {
				 ((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setCondicionVenta(Sesion.condicionNormal);
				 ((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setDctoEmpleado(0.00);
				 double pfinalNuevo = ((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).getProducto().getPrecioRegular();
				 ((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setPrecioFinal(pfinalNuevo);
				 ((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setMontoImpuesto(this.calcularImpuesto(((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).getProducto(), pfinalNuevo));
			} else {
				if (this.getCliente()!=null && ((this.getCliente().getTipoCliente() == Sesion.COLABORADOR)&&(this.getCliente().getEstadoColaborador() == Sesion.ACTIVO)) && !Sesion.permitirRebajasAempleados) {
					((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setCondicionVenta(Sesion.condicionNormal);
					((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setDctoEmpleado(0.00);
					double pfinalNuevo = ((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).getProducto().getPrecioRegular();
					((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setPrecioFinal(pfinalNuevo);
					((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setMontoImpuesto(this.calcularImpuesto(((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).getProducto(), pfinalNuevo));
				} else { // si las rebajas se permiten a los empleados
					if (((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).getCondicionVenta().equals(Sesion.condicionDesctoEmpDesctoDefect)) {
						((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setCondicionVenta(Sesion.condicionDesctoPorDefecto);
						((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setPrecioFinal(((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).getPrecioFinal() + ((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).getDctoEmpleado());
						((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setDctoEmpleado(0.00);
					} else if (((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).getCondicionVenta().equals(Sesion.condicionDesctoEmpPrecioGarantizado)){
						((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setCondicionVenta(Sesion.condicionDesctoPrecioGarantizado);
						((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setPrecioFinal(((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).getPrecioFinal() + ((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).getDctoEmpleado());
						((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setDctoEmpleado(0.00);
					} else if (((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).getCondicionVenta().equals(Sesion.condicionDesctoEmpCambPrec)) {	
						((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setCondicionVenta(Sesion.condicionCambioPrecio);
						((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setPrecioFinal(((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).getPrecioFinal() + ((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).getDctoEmpleado());
						((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setDctoEmpleado(0.00);
					}
				}
			}
			
			//ACTUALIZACION BECO 31/07/2008 Lo mismo de arriba (quitar el descuento empleado) pero ahora trabajando 
			//con el vector de condiciones de venta agregado por el módulo de promociones
			if(!((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).isCondicionEspecial() &&
					!((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).contieneAlgunaCondicion(condicionesCorporativo)){
				((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setCondicionVentaPromociones(new Vector<CondicionVenta>());
				((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setDctoEmpleado(0.00);
				((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setCondicionVenta(Sesion.condicionNormal);
				try{
					((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setMontoDctoCombo(0);
					((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setMontoDctoCorporativo(0);
					 
				} catch(NullPointerException e){
					
				}
			} else {
				if(this.getCliente()!=null && ((this.getCliente().getTipoCliente() == Sesion.COLABORADOR)&&(this.getCliente().getEstadoColaborador() == Sesion.ACTIVO)) && !Sesion.permitirRebajasAempleados){
					((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setCondicionVentaPromociones(new Vector<CondicionVenta>());
					((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setCondicionVenta(Sesion.condicionNormal);
					((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setDctoEmpleado(0.00);
					try{
						 ((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setMontoDctoCombo(0);
						 ((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setMontoDctoCorporativo(0);
					 } catch(NullPointerException e){
						 
					 }
				} else{ // si las rebajas se permiten a los empleados
					//Condiciones a verificar: Empleado y descuento por defecto
					Vector<String> condicionesEmpleadoDesctoDefecto = new Vector<String>();
					condicionesEmpleadoDesctoDefecto.add(Sesion.condicionDesctoEmpleado);
					condicionesEmpleadoDesctoDefecto.add(Sesion.condicionDesctoPorDefecto);
					
					//Condiciones a verificar: Empleado y precio garantizado
					Vector<String> condicionesEmpleadoPrecioGarantizado = new Vector<String>();
					condicionesEmpleadoPrecioGarantizado.add(Sesion.condicionDesctoEmpleado);
					condicionesEmpleadoPrecioGarantizado.add(Sesion.condicionDesctoPrecioGarantizado);
					
					//Condiciones a verificar: Empleado y Cambio de precio
					Vector<String> condicionesEmpleadoCambioPrecio = new Vector<String>();
					condicionesEmpleadoCambioPrecio.add(Sesion.condicionDesctoEmpleado);
					condicionesEmpleadoCambioPrecio.add(Sesion.condicionCambioPrecio);
					
					if(((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).contieneCondicion(condicionesEmpleadoDesctoDefecto) ||
							((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).contieneCondicion(condicionesEmpleadoPrecioGarantizado) ||
							((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).contieneCondicion(condicionesEmpleadoCambioPrecio)){
						((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).removeCondicion(new CondicionVenta(Sesion.condicionDesctoEmpleado));
						((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setPrecioFinal(((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).getPrecioFinal() + ((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).getDctoEmpleado());
						((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setDctoEmpleado(0.00);
						((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setCondicionVenta(((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).construirCondicionVentaString());
						//Elimino el corporativo
						((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).removeCondicion(new CondicionVenta(Sesion.condicionDescuentoCorporativo));
						((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setPrecioFinal(((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).getPrecioFinal() + ((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).getMontoDctoCorporativo());
						((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setMontoDctoCorporativo(0.00);
						((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).setCondicionVenta(((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).construirCondicionVentaString());
					}
				}
			}
			
			//ACTUALIZACION BECO 31/07/2008
			//Si ya no aplica el descuento empleado o el cliente no es empleado es necesario quitar la condicion
			//de venta del vector de cada detalle
			if(!isAplicaDesctoEmpleado() || this.getCliente()==null || 
					(this.getCliente().getTipoCliente() != Sesion.COLABORADOR) || 
					(this.getCliente().getEstadoColaborador() != Sesion.ACTIVO)){
				DetalleTransaccion detalle = ((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i));
				
				Vector<String> condicionEmpleado = new Vector<String>();
				condicionEmpleado.addElement(Sesion.condicionDesctoEmpleado);
				
				if(detalle.contieneAlgunaCondicion(condicionEmpleado)) {
					//Obtengo ahorro en compra si lo tiene aplicado y la elimino
					Vector<String> condicionAhorroEnCompra =  new Vector<String>();
					condicionAhorroEnCompra.addElement(Sesion.condicionDescuentoEnProductos);
					CondicionVenta cvAhorroC = detalle.getPrimeraCondicion(condicionAhorroEnCompra);
					if(cvAhorroC !=null){
						detalle.setPrecioFinal((detalle.getPrecioFinal()*100)/(100-cvAhorroC.getPorcentajeDescuento()));
						detalle.removeCondicion(cvAhorroC);
					}
					//Elimino descuento empleado
					detalle.removeCondicion(new CondicionVenta(Sesion.condicionDesctoEmpleado));
					detalle.setCondicionVenta(detalle.construirCondicionVentaString());
					detalle.setPrecioFinal(detalle.getPrecioFinal()+detalle.getMontoDctoEmpleado());
					detalle.setDctoEmpleado(0.00);
					//Elimino el corporativo
					detalle.removeCondicion(new CondicionVenta(Sesion.condicionDescuentoCorporativo));
					detalle.setPrecioFinal(detalle.getPrecioFinal() + detalle.getMontoDctoCorporativo());
					detalle.setMontoDctoCorporativo(0.00);
					detalle.setCondicionVenta(detalle.construirCondicionVentaString());
					detalle.setMontoImpuesto(this.calcularImpuesto(detalle.getProducto(), detalle.getPrecioFinal()));
				}
			}
						
			if (productos.size()== 0){
				productos.addElement(this.getDetallesTransaccion().elementAt(i));
			} else {
				registrado = false;
				for(int j=0; j<productos.size();j++) {
					codP = ((DetalleTransaccion)productos.elementAt(j)).getProducto().getCodProducto();
					if(codP.equals((((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).getProducto()).getCodProducto())){
						registrado = true;	
						break;					
					}
				}
				if(registrado == false){
					productos.addElement(this.getDetallesTransaccion().elementAt(i));
				}			
			}
		}
	
		// ACTUALIZACION BECO: 30/05/2008
		// Ahora actualizamos los precios de los detalles por cada producto.
		// Es necesario para cada producto volver a cargarle sus promociones 
		for (int i=0; i<productos.size(); i++) {
			Producto prod = ((DetalleTransaccion)productos.elementAt(i)).getProducto();
						
			//si la extension beco de promociones no esta activa el vector nuevo es vacio
			/*Vector promociones = prod.getPromociones();
			promociones.addAll(actualizadorPrecios.getPromocionesPatrocinantesPorProducto(prod.getCodProducto(), promociones));
			*/
			
			(new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().actualizarPrecios(prod, this);
		}
		
		this.actualizarMontoTransaccion();

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarPrecios(boolean) - end");
		}
	}	
	/**
	 * Asigna el cliente indicado a la venta. Si el cliente no es un afiliado se lanza una excepción indicandola eventualidad. 
	 * En caso de ser afiliado, valida que si es un COLABORADOR le aplica el descto a COLABORADOR a la venta.
	 * @param codigo
	 */
	public void asignarCliente(String codigo, String autorizante) throws XmlExcepcion, FuncionExcepcion, AfiliadoUsrExcepcion, BaseDeDatosExcepcion, LineaExcepcion, AutorizacionExcepcion, ClienteExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("asignarCliente(String, String) - start");
		}

		Cliente cliente = null;

		//Verificamos si existe el afiliado en la base de datos
		try {
			cliente = MediadorBD.obtenerCliente(codigo);
		}catch (BaseDeDatosExcepcion e){
			logger.error("asignarCliente(String, String)", e);

			throw (new BaseDeDatosExcepcion(e.getMensaje(), e));
		} catch (AfiliadoUsrExcepcion ex){
			logger.error("asignarCliente(String, String)", ex);

			throw(ex);
		}
		
		// Verificamos que el cliente no sea el usuario actual
		if ((cliente.getNumFicha()!=null) && Sesion.getUsuarioActivo().getNumFicha().equalsIgnoreCase(cliente.getNumFicha()))
			throw new AfiliadoUsrExcepcion("El cliente no puede ser el cajero activo");
		
		// Verificamos que el cliente no sea el usuario que autoriza la funcion (Si aplica)
		if ((autorizante!=null) && (cliente!=null) && autorizante.equalsIgnoreCase(cliente.getNumFicha()))
			throw (new AutorizacionExcepcion ("La función no pudo ser autorizada.\nEl autorizante debe ser distinto al cliente asignado."));

		//Registramos la auditoría de esta función
		Sesion.setUbicacion("FACTURACION","asignarCliente");
		Auditoria.registrarAuditoria("Identificando Cliente " +  cliente.getCodCliente(),'T');
		this.setCliente(cliente);
		this.verMensajesCliente();	

		if (logger.isDebugEnabled()) {
			logger.debug("asignarCliente(String, String) - end");
		}
	}

	/**
	 * Método verMensajesCliente
	 * 
	 * 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Iterator'
	* Fecha: agosto 2011
	*/
	private void verMensajesCliente(){
		if (logger.isDebugEnabled()) {
			logger.debug("verMensajesCliente() - start");
		}

		if(CR.me != null){
			if(this.getCliente().getCodCliente() != null && (this.getCliente().getMensajes().size() > 0)) {
				Iterator<StringBuffer> ciclo = this.getCliente().getMensajes().iterator();
				while(ciclo.hasNext()){
					CR.me.mostrarAviso(ciclo.next().toString(), true);
				}
				
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("verMensajesCliente() - end");
		}
	}
	
	/**
	 * Asigna el cliente No afiliado indicado a la venta y lo registra en la BD con un tipo especial (No afiliado)
	 * @param clienteTemp Cliente No afiliado a registrar y a asociar a la venta
	 */
	public void asignarClienteNoAfiliado(Cliente clienteTemp) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, AfiliadoUsrExcepcion, ConexionExcepcion  {
		if (logger.isDebugEnabled()) {
			logger.debug("asignarClienteNoAfiliado(Cliente) - start");
		}

		//Se arma el código del cliente correctamente (X-0000000)
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
		
		//Registramos el cliente temporal en la BD
		BaseDeDatosVenta.registrarClienteTemporal(clienteTemp);
		
		//Asignamos el cliente a la venta
		this.setCliente(clienteTemp);
		
		//Registramos la auditoría de esta función
		Sesion.setUbicacion("FACTURACION","asignarClienteNoAfiliado");
		Auditoria.registrarAuditoria("Asignando y registrando Cliente" +  cliente.getCodCliente()+ " No afiliado " ,'T');

		if (logger.isDebugEnabled()) {
			logger.debug("asignarClienteNoAfiliado(Cliente) - end");
		}
	}
	
	/**
	 * Método isAplicaDesctoEmpleado
	 * 
	 * @return boolean
	 */
	public boolean isAplicaDesctoEmpleado() {
		if (logger.isDebugEnabled()) {
			logger.debug("isAplicaDesctoEmpleado() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isAplicaDesctoEmpleado() - end");
		}
		return aplicaDesctoEmpleado;
	}
	
	/**
	 * @param b
	 */
	public void setAplicaDesctoEmpleado(boolean b) {
		if (logger.isDebugEnabled()) {
			logger.debug("setAplicaDesctoEmpleado(boolean) - start");
		}

		aplicaDesctoEmpleado = b;

		if (logger.isDebugEnabled()) {
			logger.debug("setAplicaDesctoEmpleado(boolean) - end");
		}
	}
	
	/**
	 * Obtiene la posicion de los productos con codigo codProducto presente en el detalle de transaccion
	 * @param codProducto Codigo del producto buscado
	 * @return Vector - Vector con las posiciones del producto en el detalle 
	 * @throws ProductoExcepcion Si el producto no se encuentra en el detalle de la venta actual
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public Vector<Integer> obtenerRenglones(String codProducto, boolean isCodigoExterno) throws ProductoExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerRenglones(String) - start");
		}

		Vector<Integer> result = new Vector<Integer>(); //Vector que contendrá el resultado del método
		String codInterno = isCodigoExterno ? MediadorBD.obtenerCodigoInterno(codProducto) : codProducto;
		if (codInterno == null) throw (new ProductoExcepcion("El producto " + codProducto + " no se encuentra en el detalle"));
	
		// Para cada detalle de transaccion presente en la venta
		for (int i=0; i<this.detallesTransaccion.size();i++){
			String codProd = ((DetalleTransaccion)this.detallesTransaccion.elementAt(i)).getProducto().getCodProducto();
			// Si el codigo de producto es el que se tiene como parametro se agrega al vector de resultados
			if ((codProd.equalsIgnoreCase(codProducto))||(codProd.equalsIgnoreCase(codInterno)))
				result.addElement(new Integer(i));
		}
	
		if (result.size()>0)
		{
			if (logger.isDebugEnabled()) {
				logger.debug("obtenerRenglones(String) - end");
			}
			return result;
		}
		else
			throw (new ProductoExcepcion("El producto " + codProducto + " no se encuentra en el detalle"));
	}

	/**
	 * Método calcularImpuesto
	 * 		Calcula el impuesto aplicable al precio del producto. Verifica si el cliente 
	 * de la venta es un cliente exento y si es asd evuelve 0.0
	 * @param prod - Producto al que se le va a aplicar el impuesto
	 * @param pFinal - double que representa el precio final sobre el cual se va hacer el calculo del impuesto. 
	 * 	ejm. Con promociones, descto a COLABORADOR, etc.
	 * @return double
	 */
	public double calcularImpuesto(Producto prod, double pFinal) {
		if (logger.isDebugEnabled()) {
			logger.debug("calcularImpuesto(Producto, double) - start");
		}

		double pImpuesto = 0.0;
		
		//Verificamos si en la venta el cliente activo es un cliente exento
		if(!this.isVentaExenta()) {
			// Calculamos el impuesto
			pImpuesto = MathUtil.stableMultiply(MathUtil.roundDouble(pFinal), ((prod.getImpuesto().getPorcentaje()) / 100));
			
		}		 

		if (logger.isDebugEnabled()) {
			logger.debug("calcularImpuesto(Producto, double) - end");
		}
		return pImpuesto;
	}
		
	/**
	 * Método isVentaExenta
	 * 
	 * @return
	 * boolean
	 */
	public boolean isVentaExenta() {
		if (logger.isDebugEnabled()) {
			logger.debug("isVentaExenta() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isVentaExenta() - end");
		}
		return ventaExenta;
	}

	/**
	 * Método setVentaExenta
	 * 
	 * @param b
	 * void
	 */
	public void setVentaExenta(boolean b) {
		if (logger.isDebugEnabled()) {
			logger.debug("setVentaExenta(boolean) - start");
		}

		ventaExenta = b;

		if (logger.isDebugEnabled()) {
			logger.debug("setVentaExenta(boolean) - end");
		}
	}
	
	/**
	 * Método obtenerCantidadProds
	 * Retorna la cantidad de productos que se encuentran en el detalle.
	 * @return int - entero que representa el total de productos presentes en la transacción.
	 */
	public float obtenerCantidadProds() {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCantidadProds() - start");
		}

		float cantTotal = 0;
		for (int i=0; i<this.detallesTransaccion.size();i++){
			cantTotal +=((DetalleTransaccion) this.getDetallesTransaccion().elementAt(i)).getCantidad();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCantidadProds() - end");
		}
		return cantTotal;
	}

	/**
	 * Constructor para Venta
	 *
	 * @param apartado
	 * @param montoPagado
	 * @param mtoVto
	 * @throws XmlExcepcion
	 * @throws FuncionExcepcion
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 * @throws PagoExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Venta (Apartado apartado, double montoPagado, double mtoVto) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion, PagoExcepcion {
		super(Sesion.getTienda().getNumero(), Sesion.getFechaSistema(), Sesion.getCaja().getNumero(),
			Sesion.getCaja().getNumRegistro(), Sesion.getHoraSistema(), Sesion.usuarioActivo.getNumFicha(),
			Sesion.VENTA, Sesion.PROCESO);
		pagos = new Vector<Pago>();
		
		Sesion.setUbicacion("FACTURACION","ingresarLineaProducto");
		Auditoria.registrarAuditoria("Iniciando facturacion con numRegInicial: " + this.getNumRegCajaInicia() + " de Apartado/PE Nro. " + apartado.getNumServicio(), 'T');
		
		// Armamos la cabecera de venta
		apartado.getCliente().setContribuyente(true);
		super.cliente = apartado.getCliente();
		super.montoBase = apartado.getMontoBase();
		super.montoImpuesto = apartado.getMontoImpuesto();
		super.montoVuelto = mtoVto;
		super.lineasFacturacion = apartado.getLineasFacturacion();
	
		// Armamos el detalle de la venta
		for (int i=0; i<apartado.getDetallesServicio().size(); i++) {
			DetalleServicio detalleActual = (DetalleServicio)apartado.getDetallesServicio().elementAt(i);
			this.detallesTransaccion.addElement(new DetalleTransaccion(null, detalleActual.getProducto(), 
					detalleActual.getCantidad(), detalleActual.getCondicionVenta(), detalleActual.getCodSupervisor(),
					detalleActual.getPrecioFinal(), detalleActual.getMontoImpuesto(), detalleActual.getTipoCaptura(),
					detalleActual.getCodPromocion(), detalleActual.getTipoEntrega()));
			Vector<CondicionVenta> condicionesVentas = detalleActual.getCondicionesVentaPromociones();
			((DetalleTransaccion)(this.detallesTransaccion.elementAt(i))).setCondicionVentaPromociones(condicionesVentas);
			((DetalleTransaccion)(this.detallesTransaccion.elementAt(i))).setCondicionVenta(((DetalleTransaccion)(this.detallesTransaccion.elementAt(i))).construirCondicionVentaString());
			for(int j=0;j<Apartado.donacionesRegistradas.size();j++){
				Venta.donacionesRegistradas.addElement(Apartado.donacionesRegistradas.elementAt(j));
			}
			Apartado.donacionesRegistradas = new Vector<DonacionRegistrada>();
			for(int j=0;j<Apartado.promocionesRegistradas.size();j++){
				Venta.promocionesRegistradas.addElement(Apartado.promocionesRegistradas.elementAt(j));
			}
			Apartado.promocionesRegistradas = new Vector<PromocionRegistrada>();
			for(int j=0;j<this.getDetallesTransaccion().size();j++){
				DetalleTransaccion dt = (DetalleTransaccion)this.getDetallesTransaccion().elementAt(j);
				Vector<String> condicionesCorporativas = new Vector<String>();
				condicionesCorporativas.addElement(Sesion.condicionDescuentoCorporativo);
				CondicionVenta cv = dt.getPrimeraCondicion(condicionesCorporativas);
				if(cv!=null){
					this.setCodDescuentoCorporativo(cv.getCodPromocion());
					break;
				}
			}
		}
		
		// Agregamos un pago de tipo abonos anteriores por el monto de los abonos
		pagos.addElement(ManejoPagosFactory.getInstance().realizarPagoAbonos(montoPagado-apartado.getAbonoNuevo().getMontoBase()));
		
		// Efectuamos el pago final
		for (int i=0; i<apartado.getAbonoNuevo().getPagos().size(); i++)
			pagos.addElement((Pago)apartado.getAbonoNuevo().getPagos().elementAt(i));
			
		//Agregado por modulo de promociones 12/12/2008
		(new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().revisarPromociones(this);
		
		// Finalizamos la venta
		try {
			this.finalizarTransaccion();
		} catch (PrinterNotConnectedException e) {
			logger.error("Venta(Apartado, double, double)", e);
		}

		//ACTUALIZACION BECO: Impresora fiscal GD4
		while (CR.meVenta.getTransaccionPorImprimir()!=null || 
				CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
				MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()) {
			//MensajesVentanas.aviso("Hay un documento en curso \nEspere un momento e intente nuevamente");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			}
			if(MaquinaDeEstadoVenta.errorAtencionUsuario){
				MensajesVentanas.aviso("Hay un documento en curso \nEspere un momento e intente nuevamente");
			}
		}
		if (Sesion.impresoraFiscal) {
			MaquinaDeEstadoVenta.setDocumentoNoFiscalPorImprimir(true);
		}
		
		// Imprimimos el comprobante de cancelacion de apartado.
		ManejadorReportesFactory.getInstance().imprimirCancelacionApartadoPedidoEspecial(apartado, false);
	}

	/**
	 * Constructor para Venta
	 *
	 * @param apartado
	 * @throws XmlExcepcion
	 * @throws FuncionExcepcion
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 * @throws PagoExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Venta (Apartado apartado) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion, PagoExcepcion {
		super(Sesion.getTienda().getNumero(), Sesion.getFechaSistema(), Sesion.getCaja().getNumero(),
			Sesion.getCaja().getNumRegistro(), Sesion.getHoraSistema(), Sesion.usuarioActivo.getNumFicha(),
			Sesion.VENTA, Sesion.PROCESO);
		pagos = new Vector<Pago>();
		
		Sesion.setUbicacion("FACTURACION","ingresarLineaProducto");
		Auditoria.registrarAuditoria("Iniciando facturacion con numRegInicial: " + this.getNumRegCajaInicia() + " de Apartado/PE Nro. " + apartado.getNumServicio(), 'T');
		
		// Armamos la cabecera de venta
		apartado.getCliente().setContribuyente(true);
		super.cliente = apartado.getCliente();
		super.montoBase = apartado.getMontoBase();
		super.montoImpuesto = apartado.getMontoImpuesto();
		super.lineasFacturacion = apartado.getLineasFacturacion();
	
		// Armamos el detalle de la venta
		for (int i=0; i<apartado.getDetallesServicio().size(); i++) {
			DetalleServicio detalleActual = (DetalleServicio)apartado.getDetallesServicio().elementAt(i);
			this.detallesTransaccion.addElement(new DetalleTransaccion(null, detalleActual.getProducto(), 
					detalleActual.getCantidad(), detalleActual.getCondicionVenta(), detalleActual.getCodSupervisor(),
					detalleActual.getPrecioFinal(), detalleActual.getMontoImpuesto(), detalleActual.getTipoCaptura(),
					detalleActual.getCodPromocion(), detalleActual.getTipoEntrega()));
			Vector<CondicionVenta> condicionesVentas = detalleActual.getCondicionesVentaPromociones();
			((DetalleTransaccion)(this.detallesTransaccion.elementAt(i))).setCondicionVentaPromociones(condicionesVentas);
			((DetalleTransaccion)(this.detallesTransaccion.elementAt(i))).setCondicionVenta(((DetalleTransaccion)(this.detallesTransaccion.elementAt(i))).construirCondicionVentaString());
			for(int j=0;j<Apartado.donacionesRegistradas.size();j++){
				Venta.donacionesRegistradas.addElement(Apartado.donacionesRegistradas.elementAt(j));
			}
			Apartado.donacionesRegistradas = new Vector<DonacionRegistrada>();
			for(int j=0;j<Apartado.promocionesRegistradas.size();j++){
				Venta.promocionesRegistradas.addElement(Apartado.promocionesRegistradas.elementAt(j));
			}
			Apartado.promocionesRegistradas = new Vector<PromocionRegistrada>();
			for(int j=0;j<this.getDetallesTransaccion().size();j++){
				DetalleTransaccion dt = (DetalleTransaccion)this.getDetallesTransaccion().elementAt(j);
				Vector<String> condicionesCorporativas = new Vector<String>();
				condicionesCorporativas.addElement(Sesion.condicionDescuentoCorporativo);
				CondicionVenta cv = dt.getPrimeraCondicion(condicionesCorporativas);
				if(cv!=null){
					this.setCodDescuentoCorporativo(cv.getCodPromocion());
					break;
				}
			}
		}
		
		// Agregamos un pago de tipo abonos anteriores por el monto de los abonos
		pagos.addElement(ManejoPagosFactory.getInstance().realizarPagoAbonos(apartado.montoAbonos()));
		//Agregado por modulo de promociones 12/12/2008
		(new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().revisarPromociones(this);
		
		// Finalizamos la venta
		try {
			this.finalizarTransaccion();
		} catch (PrinterNotConnectedException e) {
			logger.error("Venta(Apartado)", e);
		}
		
		//ACTUALIZACION BECO: Impresora fiscal GD4
		while (CR.meVenta.getTransaccionPorImprimir()!=null || 
				CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
				MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			}
			if(MaquinaDeEstadoVenta.errorAtencionUsuario)
				MensajesVentanas.aviso("Hay un documento en curso \nEspere un momento e intente nuevamente");
		}
		/*if (Sesion.impresoraFiscal) {
			MaquinaDeEstadoVenta.documentoNoFiscalPorImprimir=true;
		}*/
		
		// Imprimimos el comprobante de cancelacion de apartado.
		ManejadorReportesFactory.getInstance().imprimirCancelacionApartadoPedidoEspecial(apartado, true);
	}

	/**
	 * Método facturarCotizacion
	 * 
	 * @param cotizacion
	 * @param pagosRealizados
	 * @param mtoVto
	 * @throws XmlExcepcion
	 * @throws FuncionExcepcion
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 * @throws PagoExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void facturarCotizacion(Cotizacion cotizacion, 
			Vector<Pago> pagosRealizados, double mtoVto) throws 
			XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, 
			ConexionExcepcion, PagoExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("facturarCotizacion(Cotizacion, Vector, double) - start");
		}

		pagos = new Vector<Pago>();
		
		Sesion.setUbicacion("FACTURACION","ingresarLineaProducto");
		Auditoria.registrarAuditoria("Iniciando facturacion con numRegInicial: " + this.getNumRegCajaInicia() + " de Cotizacion Nro. " + cotizacion.getNumServicio(), 'T');
		
		// Agregamos los pagos realizados a la cotizacion
		for (int i=0; i<pagosRealizados.size(); i++)
			pagos.addElement((Pago)pagosRealizados.elementAt(i));
			
		// Finalizamos la venta
		try {
			this.finalizarTransaccion();
		} catch (PrinterNotConnectedException e) {
			logger.error("facturarCotizacion(Cotizacion, Vector, double)", e);
		}

		if (logger.isDebugEnabled()) {
			logger
					.debug("facturarCotizacion(Cotizacion, Vector, double) - end");
		}
	}
	
	/**
	 * verifica si la venta posee un despacho asociado
	 * @throws DespachoExcepcion
	 */
	public boolean verificarTieneDespacho() {
		if (logger.isDebugEnabled()) {
			logger.debug("verificarTieneDespacho() - start");
		}

		boolean poseeDespacho = false;
		
		for (int i=0;i<this.getDetallesTransaccion().size();i++) {
			if(((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).getTipoEntrega().equals(Sesion.ENTREGA_DESPACHO)) {
				poseeDespacho = true;
				break;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("verificarTieneDespacho() - end");
		}
		return poseeDespacho;
	}
	
	/**
	 * verifica si la venta posee un despacho asociado
	 * @throws DespachoExcepcion
	 */
	public boolean verificarTieneEntregaDom() {
		if (logger.isDebugEnabled()) {
			logger.debug("verificarTieneEntregaDom() - start");
		}

		boolean poseeEntregaDom = false;
		
		for (int i=0;i<this.getDetallesTransaccion().size();i++) {
			if(((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).getTipoEntrega().equals(Sesion.ENTREGA_DOMICILIO)) {
				poseeEntregaDom = true;
				break;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("verificarTieneEntregaDom() - end");
		}
		return poseeEntregaDom;
	}

	/**
	 * Verifica si la venta posee un Cliente Retira asociado
	 * @throws DespachoExcepcion
	 */
	public boolean verificarTieneClienteRetira() {
		if (logger.isDebugEnabled()) {
			logger.debug("verificarTieneClienteRetira() - start");
		}

		boolean poseeClienteRetira = false;
		
		for (int i=0;i<this.getDetallesTransaccion().size();i++) {
			//Modificado por CENTROBECO, no estaba considerado el caso despacho
			if(((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).getTipoEntrega().equals(Sesion.ENTREGA_CLIENTE_RETIRA)
					|| ((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).getTipoEntrega().equals(Sesion.ENTREGA_DESPACHO)) {
				poseeClienteRetira = true;
				break;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("verificarTieneClienteRetira() - end");
		}
		return poseeClienteRetira;
	}	
	
	/**
	 * Método actualizarTipoEntrega
	 *		Actualiza el tipo de entrega del detalle especificado. esta puede ser: "Caja","Despacho","Domicilio" 
	 * @param renglon - Es la posición del detalle de la venta donde se quiere actualizar el campo de despachados
	 * @param tipoEntrega
	 */
	public void actualizarTipoEntrega (int renglon, String tipoEntrega) {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarTipoEntrega(int, String) - start");
		}
		
		((DetalleTransaccion)this.getDetallesTransaccion().elementAt(renglon)).setTipoEntrega(tipoEntrega);

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarTipoEntrega(int, String) - end");
		}
	}

	/**
	 * Método getSerialCaja
	 * 
	 * @return
	 * String
	 */
	public String getSerialCaja() {
		if (logger.isDebugEnabled()) {
			logger.debug("getSerialCaja() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getSerialCaja() - end");
		}
		return serialCaja;
	}
	
	public boolean condicionVentaAplicada(int renglon, String condicion) {
		if (logger.isDebugEnabled()) {
			logger.debug("condicionVentaAplicada(int, String) - start");
		}

		boolean result = false;
		Producto prod = ((DetalleTransaccion)getDetallesTransaccion().elementAt(renglon)).getProducto();
		for (int i = 0; i < getDetallesTransaccion().size(); i++) {
			if (i != renglon) {
				DetalleTransaccion det = (DetalleTransaccion)getDetallesTransaccion().elementAt(i);
				if (det.getProducto().equals(prod) && 
					condicion.equals(det.getCondicionVenta())) {
					result = true;
					break;
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("condicionVentaAplicada(int, String) - end");
		}
		return result;
	}
	
	/**
	 * Constructor para Venta
	 *
	 * @param listaregalos
	 * @param montoPagado
	 * @param mtoVto
	 * @throws XmlExcepcion
	 * @throws FuncionExcepcion
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 * @throws PagoExcepcion
	 */
	 Venta (ListaRegalos listaregalos, double montoPagado, double mtoVto) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion, PagoExcepcion {
		super(Sesion.getTienda().getNumero(), Sesion.getFechaSistema(), Sesion.getCaja().getNumero(),
			Sesion.getCaja().getNumRegistro(), Sesion.getHoraSistema(), Sesion.usuarioActivo.getNumFicha(),
			Sesion.VENTA, Sesion.PROCESO);
		pagos = new Vector<Pago>();
		
		Sesion.setUbicacion("FACTURACION","ingresarLineaProducto");
		Auditoria.registrarAuditoria("Iniciando facturacion con numRegInicial: " + this.getNumRegCajaInicia() + " de Lista de Regalos Nro. " + listaregalos.getNumServicio(), 'T');
		
		// Armamos la cabecera de venta
		super.cliente = listaregalos.getCliente();
		super.montoBase = listaregalos.getMontoBase();
		super.montoImpuesto = listaregalos.getMontoImpuesto();
		super.montoVuelto = mtoVto;
		super.lineasFacturacion = listaregalos.getLineasFacturacion();
	
		// Armamos el detalle de la venta
		for (int i=0; i<listaregalos.getDetallesServicio().size(); i++) {
			DetalleServicio detalleActual = (DetalleServicio)listaregalos.getDetallesServicio().elementAt(i);
			this.detallesTransaccion.addElement(new DetalleTransaccion(null, detalleActual.getProducto(), 
					detalleActual.getCantidad(), detalleActual.getCondicionVenta(), detalleActual.getCodSupervisor(),
					detalleActual.getPrecioFinal(), detalleActual.getMontoImpuesto(), detalleActual.getTipoCaptura(),
					detalleActual.getCodPromocion(), detalleActual.getTipoEntrega()));
		}
		
		// Agregamos un pago de tipo abonos anteriores por el monto de los abonos
		pagos.addElement(ManejoPagosFactory.getInstance().realizarPagoAbonos(montoPagado-listaregalos.getMontoBase()));
		
		// Efectuamos el pago final
		for (int i=0; i<listaregalos.getPagosAbono().size(); i++)
			pagos.addElement((Pago)listaregalos.getPagosAbono().elementAt(i));
			
		// Finalizamos la venta
		try {
			this.finalizarTransaccion();
		} catch (PrinterNotConnectedException e) {
			logger.error("Venta(ListaRegalos, double, double)", e);
		}
	}
	
	 /*
		* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
		* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
		* Fecha: agosto 2011
		*/
	@SuppressWarnings("unchecked")
	public Object clone(){
		Venta venta=null;
		try {
			venta = (Venta)super.clone();
		} catch(CloneNotSupportedException ex) { }
		venta.pagos = (Vector<Pago>) venta.pagos.clone();
		for(int i=0;i<pagos.size();i++){
			Pago pago = ((Pago)venta.pagos.get(i));
			venta.pagos.set(i,(Pago)pago.clone());
		}
		venta.detallesTransaccion = (Vector<DetalleTransaccion>) venta.detallesTransaccion.clone();
		for(int i=0;i<detallesTransaccion.size();i++){
			DetalleTransaccion detalleTransaccion = ((DetalleTransaccion)venta.detallesTransaccion.get(i));
			venta.detallesTransaccion.set(i,(DetalleTransaccion)detalleTransaccion.clone());
		}
		return venta;
	}
	
	/**
	 * Constructor de la clase Venta que crea una nueva basandose en la que se le pasa como parámetro pero con 
	 * sus nuevos números de registros.
	 */
	public void concatenarVenta(Venta venta2) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {		
	
		// Armamos el detalle de la venta
		for (int i=0; i<venta2.getDetallesTransaccion().size(); i++) {
			DetalleTransaccion detalleActual = (DetalleTransaccion)venta2.getDetallesTransaccion().elementAt(i);
			this.detallesTransaccion.addElement(new DetalleTransaccion(null, detalleActual.getProducto(), 
					detalleActual.getCantidad(), detalleActual.getCondicionVenta(), detalleActual.getCodSupervisor(),
					detalleActual.getPrecioFinal(), detalleActual.getMontoImpuesto(), detalleActual.getTipoCaptura(),
					detalleActual.getCodPromocion(), detalleActual.getTipoEntrega()));
		}
		actualizarMontoTransaccion();
		//actualizarPreciosDetalle(false);
	}
	/**
	 * @return
	 */
	public boolean isImpEmpleadoPieDePag() {
		return impEmpleadoPieDePag;
	}
	
	
	/*********************************************************
	 * Actualizaciones BECO por modulo de promociones
	 *********************************************************/

	/**
	 * 
	 * @return el pagosClonado
	 */
	public Vector<Pago> getPagosClonados() {
		return pagosClonados;
	}

	/**
	 * @param pagosClonado el pagosClonado a establecer
	 */
	public void setPagosClonados(Vector<Pago> pagosClonados) {
		this.pagosClonados = pagosClonados;
	}
	
	/**
	 * Inicializa el vector de pagos clonados
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' e 'Iterator'
	* Fecha: agosto 2011
	*/
	public void asignaPagosClonados(Venta venta){
		Vector<Pago> pagos = venta.getPagos();
		Iterator<Pago> i = pagos.iterator();
		while(i.hasNext()){
			Pago pago2 = (Pago)((Pago)i.next()).clone();
			this.pagosClonados.addElement(pago2);
		}
	}
	
	/**
	 * Comprueba si el vector de pagos contiene un pago de la
	 * forma fp
	 * @param fp Forma de pago que se desea comprobar
	 * @return boolean true: si hay un pago de la forma fp
	 * 					false: caso contrario
	 * **/
	public boolean contieneFormaPago(FormaDePago fp){
		boolean contiene =false;
		Iterator<Pago> i = pagos.iterator();
		while(i.hasNext()){
			Pago p = (Pago)i.next();
			if(p.getFormaPago().getCodigo().equalsIgnoreCase(fp.getCodigo())){
				return true;
			}
		}
		return contiene;
	}
	
	/**
	 * Obtiene la posicion en la que se encuentra un detalle dentro del vector
	 * de detalles transaccion de la venta
	 * @param dt
	 * @return int posicion
	 * @throws Exception 
	 */
	public int getPosicionDetalle(DetalleTransaccion dt) throws Exception{
		boolean encontrado = false;
		for(int i=0;i<detallesTransaccion.size();i++){
			if(detallesTransaccion.elementAt(i).equals(dt)){
				encontrado = true;
				return i;
			}
		}
		if(!encontrado) throw new Exception();
		return 0;
	}

	/**
	 * @return el promocionProductoGratis
	 */
	public Promocion getPromocionProductoGratis() {
		return promocionProductoGratis;
	}

	/**
	 * @param promocionProductoGratis el promocionProductoGratis a establecer
	 */
	public void setPromocionProductoGratis(Promocion promocionProductoGratis) {
		this.promocionProductoGratis = promocionProductoGratis;
	}

	/**
	 * @return el detallesAEliminar
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector getDetallesAEliminar() {
		return detallesAEliminar;
	}

	/**
	 * @param detallesAEliminar el detallesAEliminar a establecer
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void setDetallesAEliminar(Vector detallesAEliminar) {
		this.detallesAEliminar = detallesAEliminar;
	}

	public double getMontoAhorrado() {
		return montoAhorrado;
	}

	public void setMontoAhorrado(double montoAhorrado) {
		this.montoAhorrado = montoAhorrado;
	}

	public double getMontoDonaciones() {
		return montoDonaciones;
	}

	public void setMontoDonaciones(double montoDonaciones) {
		this.montoDonaciones = montoDonaciones;
	}

	
	/*********************************************************
	 * Fin de las actualizaciones BECO por modulo de promociones
	 *********************************************************/
	
}
