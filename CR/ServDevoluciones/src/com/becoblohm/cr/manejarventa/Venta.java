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
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.AfiliadoUsrExcepcion;
import com.becoblohm.cr.excepciones.AutorizacionExcepcion;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ClienteExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.LineaExcepcion;
import com.becoblohm.cr.excepciones.PagoExcepcion;
import com.becoblohm.cr.excepciones.ProductoExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarservicio.Apartado;
import com.becoblohm.cr.manejarservicio.Cotizacion;
import com.becoblohm.cr.manejarservicio.ListaRegalos;
import com.becoblohm.cr.mediadoresbd.BaseDeDatosVenta;
import com.becoblohm.cr.utiles.MathUtil;
import com.epa.crprinterdriver.PrinterNotConnectedException;

/** 
 * Descripción: 
 * 		Subclase de Transaccion. Maneja las transacciones de venta desde su estado inicial (Ingreso del primer
 * producto) hasta su finalizacion. Se manejan accesos a base de datos para registrar
 * las transacciones finalizadas.
 */
public class Venta extends Transaccion implements Serializable,Cloneable {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Venta.class);
	
	private Vector pagos;
	Vector numerosServicio;
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
    public static Vector donacionesRegistradas = new Vector();
    public static Vector regalosRegistrados = new Vector();
    public static Vector promocionesRegistradas = new Vector();
    public Vector pagosClonados = new Vector();
    
    public Promocion promocionProductoGratis = null;
    
    private Vector detallesAEliminar;
    
    private double montoAhorrado = 0;
    private double montoDonaciones = 0;
	
	/**
	 * Constructor de la clase Venta que llama al constructor de la superclase (Transaccion)
	 * y crea un Vector de Pagos vacio.
	 */
	public Venta() throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {		
		/*super(Sesion.getTienda().getNumero(), Sesion.getFechaSistema(), Sesion.getCaja().getNumero(),
		Sesion.getCaja().getNumRegistro(), Sesion.getHoraSistema(), Sesion.usuarioActivo.getNumFicha(), Sesion.VENTA, Sesion.PROCESO);
		pagos = new Vector();
		
		/*Sesion.setUbicacion("FACTURACION","ingresarLineaProducto");
		Auditoria.registrarAuditoria("Iniciando venta con numRegInicial: " + this.getNumRegCajaInicia(), 'T');*/
	}
	
	/**
	 * Constructor de la clase Venta que crea una nueva basandose en la que se le pasa como parámetro pero con 
	 * sus nuevos números de registros.
	 */
	public Venta(Venta vta) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {		
		/*super(Sesion.getTienda().getNumero(), Sesion.getFechaSistema(), Sesion.getCaja().getNumero(),
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
		this.codAutorizante = vta.getCodAutorizante();*/
	}
		
	/**
	 * Constructor de la clase Venta para recuperaciones de facturas en espera
	 */
	public Venta (String codFactEspera) throws BaseDeDatosExcepcion, ConexionExcepcion, AfiliadoUsrExcepcion {
		/*super();
/*		
		// Obtenemos la venta de la BaseDeDatos
		Vector ventaObtenida = BaseDeDatosVenta.recuperarDeEspera(codFactEspera);
		Vector cabeceraVenta = (Vector)ventaObtenida.elementAt(0);
		Vector detallesVenta = (Vector)ventaObtenida.elementAt(1);
	
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
			Vector detalleVenta = (Vector)detallesVenta.elementAt(i);
			String codProd = (String)detalleVenta.elementAt(0);
			String codVend = (String)detalleVenta.elementAt(3);
			String tipoCap = (String)detalleVenta.elementAt(8);
			float cant = ((Float)detalleVenta.elementAt(2)).floatValue();
			try {
				this.detallesTransaccion.addElement(new DetalleTransaccion(codProd, codVend, tipoCap, cant, this.fechaTrans, this.horaInicia, true));
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
		
		pagos = new Vector();
		
		BaseDeDatosVenta.borrarFacturaEspera(codFactEspera, this.numCajaInicia, this.codTienda, this.numRegCajaInicia, this.fechaTrans);*/
	}

	/**
	 * Constructor de la clase Venta para obtener ventas temporales para el manejo de devoluciones
	 */
	public Venta (int tda, String fechaT, int caja, int numTransaccion, boolean local) throws BaseDeDatosExcepcion, ConexionExcepcion {
		super();
		
		// Obtenemos la venta de la BaseDeDatos
		Vector ventaObtenida = BaseDeDatosVenta.obtenerVenta(tda, fechaT, caja, numTransaccion, local);
		Vector cabeceraVenta = (Vector)ventaObtenida.elementAt(0);
		Vector detallesVenta = (Vector)ventaObtenida.elementAt(1);
		Vector pagosVenta = (Vector)ventaObtenida.elementAt(2);
		Vector condicionesVenta = (Vector)ventaObtenida.elementAt(3);
	
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
			super.cliente = new Cliente((String)cabeceraVenta.elementAt(9));
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
			Vector detalleVenta = (Vector)detallesVenta.elementAt(i);
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
			//Agregado por módulo de promociones
			try{
				((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setCondicionVentaPromociones((Vector)condicionesVenta.elementAt(i));
			} catch(ArrayIndexOutOfBoundsException ex){
				((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setCondicionesVentaPromociones(new Vector());
			}
		}

		// Armamos los pagos
		pagos = new Vector();
		for (int i=0; i<pagosVenta.size(); i++) {
			Vector pagoObtenido = (Vector)pagosVenta.elementAt(i);
			Pago pagoActual = new Pago((String)pagoObtenido.elementAt(4),((Double)pagoObtenido.elementAt(5)).doubleValue(),(String)pagoObtenido.elementAt(11),
						(String)pagoObtenido.elementAt(6),(String)pagoObtenido.elementAt(7),(String)pagoObtenido.elementAt(8),
						((Integer)pagoObtenido.elementAt(9)).intValue(),(String)pagoObtenido.elementAt(10));
			pagoActual.setCodAutorizante((String)pagoObtenido.elementAt(12));
			pagos.addElement(pagoActual);
		}
	}

	/**
	 * Constructor de la clase Venta para obtener la última venta realizada por la caja.
	 * Caso de reimpresion de última facturada realizada.
	 */
	public Venta (Vector ventaObtenida) throws BaseDeDatosExcepcion, ConexionExcepcion {
		super();
		
		// Construimos la venta con los detalles de la misma enviados como parámetros
		Vector cabeceraVenta = (Vector)ventaObtenida.elementAt(0);
		Vector detallesVenta = (Vector)ventaObtenida.elementAt(1);
		Vector pagosVenta = (Vector)ventaObtenida.elementAt(2);
	
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
		/*if (cabeceraVenta.elementAt(9) != null) {
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
		}*/
			
		// Armamos el detalle de la venta
		for (int i=0; i<detallesVenta.size(); i++) {
			Vector detalleVenta = (Vector)detallesVenta.elementAt(i);
			String codProd = (String)detalleVenta.elementAt(0);
			String codVend = (String)detalleVenta.elementAt(3);
			String tipoCap = (String)detalleVenta.elementAt(8);
			float cant = ((Float)detalleVenta.elementAt(2)).floatValue();
			try {
				this.detallesTransaccion.addElement(new DetalleTransaccion(codProd, codVend, tipoCap, cant, this.fechaTrans, this.horaInicia, true));
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
		pagos = new Vector();
		for (int i=0; i<pagosVenta.size(); i++) {
			Vector pagoObtenido = (Vector)pagosVenta.elementAt(i);
			Pago pagoActual = new Pago((String)pagoObtenido.elementAt(4),((Double)pagoObtenido.elementAt(5)).doubleValue(),(String)pagoObtenido.elementAt(11),
						(String)pagoObtenido.elementAt(6),(String)pagoObtenido.elementAt(7),(String)pagoObtenido.elementAt(8),
						((Integer)pagoObtenido.elementAt(9)).intValue(),(String)pagoObtenido.elementAt(10));
			pagoActual.setCodAutorizante((String)pagoObtenido.elementAt(12));
			pagos.addElement(pagoActual);
		}
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
/*
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
*/
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
	public void agregarCantidad(float nvaCantidad, int renglon) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ProductoExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("agregarCantidad(float, int) - start");
		}
/*
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
		((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).setCondicionVenta(Sesion.condicionNormal);
		//Inicializamos la varable que depende de la condicíon de venta (en esta caso es el de la condición especial del detalle)
		((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).setCondicionEspecial(false);				
		actualizarPreciosDetalle(pCambiar);
		actualizarMontoTransaccion();
*/
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
/*
		float AcumN = 0;
		float cantidadProdsConDescto = 0;
		Vector detallesN = new Vector(); // Vector de posiciones para los productos en condiciones normales
		Vector detallesE = new Vector(); // Vector de posiciones para los productos en condición de empaque
		Vector nuevosDetalles;
		
		// Se acumulan los detalles que tiene condición 'N' y 'E' en sus respectivas variables
		for (int i=0; i<detallesTransaccion.size();i++) {
			if (((((DetalleTransaccion) detallesTransaccion.elementAt(i)).getProducto()).getCodProducto()).equals(p.getCodProducto())) {
				if ((((DetalleTransaccion)detallesTransaccion.elementAt(i)).getCondicionVenta().equalsIgnoreCase(Sesion.condicionEmpaque)) || 
					(((DetalleTransaccion)detallesTransaccion.elementAt(i)).getCondicionVenta().equalsIgnoreCase(Sesion.condicionEmpaquePromocion)) || 
					(((DetalleTransaccion)detallesTransaccion.elementAt(i)).getCondicionVenta().equalsIgnoreCase(Sesion.condicionEmpaquePromocionEmpleado)) || 
					(((DetalleTransaccion)detallesTransaccion.elementAt(i)).getCondicionVenta().equalsIgnoreCase(Sesion.condicionDesctoEmpEmpaque))) {
					cantidadProdsConDescto = (((DetalleTransaccion) detallesTransaccion.elementAt(i)).getCantidad() - (((DetalleTransaccion) detallesTransaccion.elementAt(i)).getCantidad() % p.getCantidadVentaEmpaque()));
					if (cantidadProdsConDescto == ((DetalleTransaccion) detallesTransaccion.elementAt(i)).getCantidad()){
						detallesE.addElement(new Integer(i));
					} else {
						((DetalleTransaccion) detallesTransaccion.elementAt(i)).setCondicionVenta(Sesion.condicionNormal);
						AcumN += ((DetalleTransaccion) detallesTransaccion.elementAt(i)).getCantidad();
						detallesN.addElement(new Integer(i));
					}
				} else if (!((DetalleTransaccion) detallesTransaccion.elementAt(i)).isCondicionEspecial()) {/*!((((DetalleTransaccion) detallesTransaccion.elementAt(i)).getCondicionVenta()).equals(Sesion.condicionDesctoPorDefecto)) &&  !((((DetalleTransaccion) detallesTransaccion.elementAt(i)).getCondicionVenta()).equals(Sesion.condicionDesctoEmpDesctoDefect))*/
					//if (((((DetalleTransaccion) detallesTransaccion.elementAt(i)).getCondicionVenta()).equals(Sesion.condicionNormal)) || ((((DetalleTransaccion) detallesTransaccion.elementAt(i)).getCondicionVenta()).equals(Sesion.condicionPromocion)) || ((((DetalleTransaccion) detallesTransaccion.elementAt(i)).getCondicionVenta()).equals(Sesion.condicionDesctoEmpleado)) || ((((DetalleTransaccion) detallesTransaccion.elementAt(i)).getCondicionVenta()).equals(Sesion.condicionDesctoEmpProm))) {
/*					AcumN += ((DetalleTransaccion) detallesTransaccion.elementAt(i)).getCantidad();
					detallesN.addElement(new Integer(i));
				} else { 
					if (Sesion.desctosAcumulativos && p.isIndicaDesctoEmpleado() && Sesion.isIndicaDesctoEmpleado() && this.isAplicaDesctoEmpleado()) {
						if (!((((DetalleTransaccion) detallesTransaccion.elementAt(i)).getCondicionVenta()).equals(Sesion.condicionDesctoEmpDesctoDefect)) && !((((DetalleTransaccion) detallesTransaccion.elementAt(i)).getCondicionVenta()).equals(Sesion.condicionDesctoEmpPrecioGarantizado))) {
							Vector desctosDefectos = new Vector();
							desctosDefectos.addElement(new Integer(i));
							aplicarCondicionDescuentoEmpleado (p, desctosDefectos, null, 0);
						}
					} else {
						// Aquí se chequean los detalles que son de tipo desct por defecto y se les recalcula el impuesto 
						((DetalleTransaccion)detallesTransaccion.elementAt(i)).setMontoImpuesto(this.calcularImpuesto(p,((DetalleTransaccion) detallesTransaccion.elementAt(i)).getPrecioFinal()));
					}
				}
			}
		}
		
		// Se evalúan y aplican las condiciones de venta a los detalles afectados.
		if (detallesN.size() > 0){
			nuevosDetalles = aplicarCondicionDeVenta(p, detallesN, AcumN);
			Vector nuevoDetallesN = ((Vector) nuevosDetalles.elementAt(0));
			Vector nuevosEmpaques = ((Vector) nuevosDetalles.elementAt(1));
			
			// Insertamos los nuevos empaques en el vector de detallesE original
			for (int i=0; i < nuevosEmpaques.size(); i++) {
				detallesE.addElement((Integer)nuevosEmpaques.elementAt(i));
			}
			
			// Se acumulan los detalles que se encuentran en condición de empaque
			acumularDetalles(detallesE);
			
			// Se acumulan los detalles que se encuentran en la condición distinta a la de empaque y se colocan al final del vector original
			acumularDetalles(nuevoDetallesN);
			
			// Se limpia el detalle quitando los elementos del vector de detallesN original
			for (int i = 0; i<detallesN.size(); i++) {
				detallesTransaccion.set(((Integer)detallesN.elementAt(i)).intValue(),null);
			}
		
			// Se eliminan los elementos nulos que quedan en el vector de detalles original
			while (detallesTransaccion.removeElement(null));
			
			// Limpiamos los vectores de posiciones
			detallesN.clear();
			nuevoDetallesN.clear();
			detallesE.clear();
		}
*/
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarPreciosDetalle(Producto) - end");
		}
	}
	
	/**
	 * Agrupa los detalles iguales y los coloca al final del vector de detalles original
	 * @param posiciones Vector de posiciones donde se encuentran los productos a agrupar
	 * @return void
	 */
	public void acumularDetalles(Vector posiciones) {
		if (logger.isDebugEnabled()) {
			logger.debug("acumularDetalles(Vector) - start");
		}

		Vector paraCaptura = new Vector(2);
		
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
	public void anularProducto(int renglon) throws ProductoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("anularProducto(int) - start");
		}
/*
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
		linea.setCondicionVenta(Sesion.condicionNormal);
		
		if (((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).getCantidad() <= 0) {
			Auditoria.registrarAuditoria("Anulado(s) " + (((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).getCantidad()+1) + " producto(s) " + linea.getProducto().getCodProducto() + " de la transaccion",'T');
			detallesTransaccion.removeElementAt(renglon);
		} else
			Auditoria.registrarAuditoria("Anulado(s) 1 producto(s) " + linea.getProducto().getCodProducto() + " de la transaccion",'T');
		
		actualizarPreciosDetalle(prod);
		actualizarMontoTransaccion();
*/
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
	public Vector anularProducto(int renglon, float cantidad) throws ProductoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("anularProducto(int, float) - start");
		}
/*
		DetalleTransaccion linea = null;
		Vector result = new Vector();
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
		linea.setCondicionVenta(Sesion.condicionNormal);
	
		if (((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).getCantidad() <= 0) {
			Auditoria.registrarAuditoria("Anulado(s) " + (((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).getCantidad()+cantidad) + " producto(s) " + linea.getProducto().getCodProducto() + " de la transaccion",'T');
			detallesTransaccion.removeElementAt(renglon);
		} else
			Auditoria.registrarAuditoria("Anulado(s) " + cantidad + " producto(s) " + linea.getProducto().getCodProducto() + " de la transaccion",'T');
		
		actualizarPreciosDetalle(prod);
		actualizarMontoTransaccion();
*/
		if (logger.isDebugEnabled()) {
			logger.debug("anularProducto(int, float) - end");
		}
		return new Vector();
	}
	
	/**
	 * Actualiza el Monto de a transaccion, calcula el nuevo monto a partir del detalle de la factura.
	 */
	public void actualizarMontoTransaccion() {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarMontoTransaccion() - start");
		}
/*
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
*/
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarMontoTransaccion() - end");
		}
	}
	
	/**
	 * Calcula el Monto de la transaccion, calcula el nuevo monto a partir del detalle de la factura.
	 * @author jgraterol. Agregado por módulo de promociones
	 */
	public double getMontoTransaccion() {
		return 0;
		
	}
	
	/**
	 * Dado un Vector de posiciones del detalle chequea los tipos de captura para colocarlos Teclado, 
	 * Escaner o Mixto.
	 * @param posiciones Vector a chequear los tipos de captura.
	 */
	public void verificarTipoCaptura (Vector posiciones) {
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
		
		double resto = 0;
/*		
		resto = this.consultarMontoTrans() - obtenerMontoPagado();
		if (resto <= 0) {
			this.montoVuelto = MathUtil.roundDouble(-resto);
		}
		
		// Registramos la auditoría de esta función
		Sesion.setUbicacion("PAGO","efectuarPago");
		Auditoria.registrarAuditoria("Pago con " + p.getFormaPago().getNombre() + ". MontoPago " + p.getMonto(),'T');
		
		if (logger.isDebugEnabled()) {
			logger.debug("efectuarPago(Pago) - end");
		}*/
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
/*
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
			numerosServicio = BaseDeDatosVenta.registrarTransaccion(this, dirEntregaDom);
			// Mandamos a imprimir la factura, pero antes registramos la auditoría de esta función
			Sesion.setUbicacion("FACTURACION","finalizarTransaccion");
			Auditoria.registrarAuditoria("Emitiendo Factura Nro." + this.numTransaccion + " de Caja " + this.numCajaFinaliza,'T');
			try{
				if (Sesion.impresoraFiscal) {
					CR.meVenta.setTransaccionPorImprimir(this);
				}
				FacturaVenta.imprimirFactura(this, false, CR.meVenta.getContribuyenteOrdinario());	
				
			} catch(Exception ex){
				logger.error("finalizarTransaccion()", ex);
				if (Sesion.impresoraFiscal) {
					/*
					 * Si estamos trabajando con una impresora fiscal, 
					 * simplemente no puedo dejar esta transacción viva
					 */
/*					rollbackTransaccion();
					MensajesVentanas.mensajeError("Error al imprimir la factura. \nLa transacción fue abortada");
				} else {
					// Continuamos tranquilos
					MensajesVentanas.mensajeError("Error al imprimir la factura");
				}
			}
		} catch (BaseDeDatosExcepcion e) {
			logger.error("finalizarTransaccion()", e);

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
*/
		if (logger.isDebugEnabled()) {
			logger.debug("finalizarTransaccion() - end");
		}
	}

	public void commitTransaccion() {
		if (logger.isDebugEnabled()) {
			logger.debug("commitTransaccion() - start");
		}
/*
		try {
			// Debe obtener el numero de comprobante fiscal		
			// Actualizamos el estado de la transaccion
			if (Sesion.impresoraFiscal) {
				Sesion.getCaja().commitNumTransaccion();
			}
			BaseDeDatosVenta.actualizarEdoTransaccion(this.codTienda, this.fechaTrans, 
					this.numCajaInicia, this.numRegCajaInicia, this.numComprobanteFiscal, 
					Sesion.FINALIZADA, true);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("commitTransaccion()", e);
		} catch (ConexionExcepcion e) {
			logger.error("commitTransaccion()", e);
		}
		// Sincronizamos la transacción
		new HiloSyncTransacciones().iniciar();
		// Verificamos si tiene alguna rebaja o cambio de precio
		for (Iterator i = getDetallesTransaccion().iterator(); i.hasNext();)
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
			if ((((Pago)getPagos().elementAt(i)).getFormaPago().getCodigo()).equals(Sesion.FORMA_PAGO_RETENCION)) {
				Auditoria.registrarAuditoria("Emitiendo Comprobante de retención de IVA de Caja " + this.numCajaFinaliza,'T');
				Auditoria.registrarAuditoria(getCodCajero(), getNumTransaccion(), "Comprobante de Retención de IVA", "FACTURACION", "efectuarRetencionIva");
				// Imprimimos el voucher por duplicado
				VoucherPago.imprimirComprobate(this.cliente,this.codAutorizante,p.getFormaPago(),p.getMonto(),2, this.getNumTransaccion());
			}
		}		
		
		//Verificamos si hubo servicios asociados a la transacciòn para imprimir sus comprobantes
		if(numerosServicio != null) {	
			if(this.verificarTieneEntregaDom()) {
				int numEntDom = ((Integer)numerosServicio.elementAt(0)).intValue();
				Auditoria.registrarAuditoria("Emitiendo Nota de Entrega a Domiclio Nro. " + numEntDom + " de Caja " + this.numCajaFinaliza,'T');
				Auditoria.registrarAuditoria(getCodCajero(), getNumTransaccion(), "Nota de Entrega Despacho a Domicilio", "FACTURACION", "imprimirNotaEntrega");
				//Se envía a imprimir la nota de entrega
				NotaDeEntrega.imprimirComprobante(this, numEntDom, dirEntregaDom);
			}
		
			if(this.verificarTieneClienteRetira()) {		
				int numDesp = ((Integer)numerosServicio.elementAt(1)).intValue();
				Auditoria.registrarAuditoria("Emitiendo Nota de Despacho Nro. " + numDesp + " de Caja " + this.numCajaFinaliza, 'T');
				Auditoria.registrarAuditoria(getCodCajero(), getNumTransaccion(), "Nota de Entrega Cliente Retira", "FACTURACION", "imprimirNotaEntrega");
				try {
					NotaDeDespacho.imprimirComprobante(this, numDesp);
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
/*
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
*/
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
	public Vector obtenerDescuentos(int renglon) throws ProductoExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerDescuentos(int) - start");
		}

		Vector descuentos = null;
		double margen = 0.0;
		Producto prod = null;
				
		// Aquí se verifica si el producto existe en el detalle. Lanza excepcion si no es así
		DetalleTransaccion linea = (DetalleTransaccion) detallesTransaccion.elementAt(renglon);
		prod = linea.getProducto();
		
		// Calculamos el margen de ganancia
		margen = ((prod.getPrecioRegular() - prod.getCostoLista()) / prod.getPrecioRegular()) * 100;	
		if (margen >= 0) {
			descuentos = new Vector();
			
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
	public void aplicarDesctoPorDefecto(int renglon, double descto, float cantidad, boolean esPorcentaje, String codAutorizante, boolean esPrecioGarantizado) throws ProductoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarDesctoPorDefecto(int, double, float, boolean, String, boolean) - start");
		}
/*
		DetalleTransaccion linea = (DetalleTransaccion)this.getDetallesTransaccion().elementAt(renglon);
		Vector paraDescuento = new Vector(1);
		double impto = 0.0;
		double pf = descto;

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
			pf = pFinal;
			
			// Calculamos el impuesto
			impto = this.calcularImpuesto(prod, pFinal);
			
			// Creamos el nuevo detalle con el producto y su nuevo precio y lo agregamos al vector de detalles
			if (esPrecioGarantizado){ //Se verifica si eel descuento vino por Precio garntizado para colocar esta condición de venta al nuevo detalle
				DetalleTransaccion detalleNuevo = new DetalleTransaccion(linea.getCodVendedor(), linea.getProducto(), cantidad, Sesion.condicionDesctoPrecioGarantizado, codAutorizante, pFinal, impto, linea.getTipoCaptura(), -1, linea.getTipoEntrega());
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
		
*/
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
/*
		// Registramos la auditoría de esta función
		Sesion.setUbicacion("FACTURACION","anularVentaActiva");
		Auditoria.registrarAuditoria("Anulada Transaccion con RegInicial " + this.getNumRegCajaInicia() + " de Caja " + this.getNumCajaInicia(),'T');
*/
		if (logger.isDebugEnabled()) {
			logger.debug("anularVentaActiva() - end");
		}
	}

	
	/**
	 * Construye la matriz de descuentos aplicables al producto especificado, con sus condiciones de venta respectivas.
	 * @param prod Producto al que se le aplicará el descuento.
	 * @return Vector Vector de dos o tres posiciones que indica: - posición 0 posee la información del descuento promocional.
	 * 				   - posición 1 posee la información del descuento a empaque
	 * 				   - posición 2 (si no son acumultativos) posee la información del descuento a COLABORADOR.
	 */
	/*private Vector buscarCondicionesDeVenta(Producto prod){
		if (logger.isDebugEnabled()) {
			logger.debug("buscarCondicionesDeVenta(Producto) - start");
		}

		double dctoEmpaque,dctoEmpleado, precioEmpleado = 0.0;
		//double precioEmpaque;
		Vector descuentos = new Vector();
		
		// Calculamos las promociones
		Vector datosPromocion = verificarPromociones(prod);
		if (datosPromocion.size() > 0) {
			// Existen promociones vigentes para el producto
			Vector promo = new Vector(2);
			promo.addElement((Double)datosPromocion.elementAt(1));
			promo.addElement(Sesion.condicionPromocion);
			promo.addElement((Integer)datosPromocion.elementAt(0));
			descuentos.addElement(promo);
		}
		

		
		if (!Sesion.desctoEmpaqueAcumulativo) {
			// Calculamos el precio de condicion empaque

			dctoEmpaque = prod.getPrecioRegular() * (prod.getDesctoVentaEmpaque()/100);
			if (dctoEmpaque > 0) {
				// Agregamos el empaque al vector de condiciones de venta
				Vector empaque = new Vector(2);
				empaque.addElement(new Double(prod.getPrecioRegular() - dctoEmpaque));
				empaque.addElement(Sesion.condicionEmpaque);
				descuentos.addElement(empaque);
			}
		}
		
		if (!Sesion.desctosAcumulativos) {
			// Calculamos el precio con el descuento a COLABORADOR si aplica
			// DEBERIA VENIR UNA PREGUNTA SI APLICA EL DESCUENTO A COLABORADOR
			if (prod.isIndicaDesctoEmpleado() && Sesion.isIndicaDesctoEmpleado()&& this.isAplicaDesctoEmpleado()) {
				dctoEmpleado = prod.getPrecioRegular() * (Sesion.getDesctoVentaEmpleado()/100);
				precioEmpleado = prod.getPrecioRegular() - dctoEmpleado;
				Vector empleado = new Vector(2);
				empleado.addElement(new Double(precioEmpleado));
				empleado.addElement(Sesion.condicionDesctoEmpleado);
				descuentos.addElement(empleado);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("buscarCondicionesDeVenta(Producto) - end");
		}
		return new Vector();
	}*/
	
	/**
	 * Chequea los descuentos aplicables al producto especificado, verifica prioridades de los mismos y aplica el descuento.
	 * @param prod Producto al que se le aplicara el descuento.
	 * @param detallesN Vector de posiciones del producto .
	 * @param cantidad Cantidad de productos presentes en el detalle.
	 * @return Vector - Vector donde la posición 0 son las posiciones de los detalles cuya condición es distinta a la de empaque y en la posición 1 se encuentran los que tienen condición empaque
	 */
/*	private Vector aplicarCondicionDeVenta(Producto prod, Vector detallesN, float cantidad) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarCondicionDeVenta(Producto, Vector, float) - start");
		}
		
		// Variables utilizables para los calculos
		int puntoDeChequeo = 0;
		
		// Variable de retorno
		Vector retorno = new Vector();
		Vector nuevosEmpaques = new Vector();
		Vector nuevosNoEmpaques = new Vector();
		
		// Buscamos las condiciones de venta que aplican al producto
		Vector descuentos = buscarCondicionesDeVenta(prod);
		
		// Definimos las variables necesarias para la creacion de los nuevos detalles
		String vendedor; 
		String supervisor; 
		String tipCaptura; 
		double pFinal;
		float cantidadAsignar;
		String tipoEntrega;
		
		// Verificamos si aplican condiciones de venta al producto, de no ser así se colocan como normales en los detalles
		if ((descuentos.size() > 0) && (cantidad > 0)) {
			
			// Obtenemos el mejor descuento para el producto
			int posicionMejor = 0;
			Vector dctoActual;
			double precioFinal = Double.MAX_VALUE;
			for (int i=0; i<descuentos.size(); i++) {
				dctoActual = (Vector)descuentos.elementAt(i);
				if (precioFinal > ((Double)dctoActual.elementAt(0)).doubleValue()) {
					posicionMejor = i;
					precioFinal = ((Double)dctoActual.elementAt(0)).doubleValue();
				}
			}
				
			// Si se trata de empaque, aplicamos el empaque unicamente a la cantidad de productos exacta
			// el resto seguira con el algoritmo de calculo NoEmpaque
			if (((String)((Vector)descuentos.elementAt(posicionMejor)).elementAt(1)).equals(Sesion.condicionEmpaque)) {
				
				// Separo en empaques y no empaques y aplico empaque
				float prodsEmpaque = cantidad - (cantidad % prod.getCantidadVentaEmpaque());	
				cantidad -= prodsEmpaque;			
				
				dctoActual = (Vector)descuentos.elementAt(posicionMejor);
				Vector datosNuevosEmpaques = aplicarCondicionEmpaque(prod, detallesN, prodsEmpaque, posicionMejor, dctoActual);
				
				nuevosEmpaques = (Vector)datosNuevosEmpaques.elementAt(0);
				puntoDeChequeo = ((Integer)datosNuevosEmpaques.elementAt(1)).intValue();
					
				// Elimino la condicion de venta empaque del vector de condiciones de venta
				descuentos.remove(posicionMejor);
					
				// Busco el mejor de los descuentos que restan
				posicionMejor = 0;
				precioFinal = Double.MAX_VALUE;
				for (int i=0; i<descuentos.size(); i++) {
					dctoActual = (Vector)descuentos.elementAt(i);
					if (precioFinal > ((Double)dctoActual.elementAt(0)).doubleValue()) {
						posicionMejor = i;
						precioFinal = ((Double)dctoActual.elementAt(0)).doubleValue();
					}
				}
			}
				
			// Si quedan productos y descuentos para ser aplicados
			if ((cantidad > 0) && (descuentos.size() > 0)) {
				while (puntoDeChequeo < detallesN.size()) {
					// Seguimos con los descuentos y aplicamos el mejor.
					vendedor = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCodVendedor();
					supervisor = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCodSupervisor();
					tipCaptura = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getTipoCaptura();
					dctoActual = (Vector)descuentos.elementAt(posicionMejor);
					pFinal = ((Double)dctoActual.elementAt(0)).doubleValue();
					cantidadAsignar = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCantidad();
					DetalleTransaccion nuevoDetalleTrans;
					if (((String)((Vector)descuentos.elementAt(posicionMejor)).elementAt(1)).equals(Sesion.condicionPromocion)) {
						
						// Si se trata de una promocion
						nuevoDetalleTrans = aplicarCondicionPromocion (prod, detallesN, dctoActual, puntoDeChequeo);
					} else {
						// Si no se trata de una promocion 
						nuevoDetalleTrans = aplicarCondicionDescuentoEmpleado (prod, detallesN, dctoActual, puntoDeChequeo);
					}
					detallesTransaccion.addElement(nuevoDetalleTrans);
					nuevosNoEmpaques.addElement(new Integer(detallesTransaccion.size() - 1));
					cantidad -= cantidadAsignar;
					puntoDeChequeo++;
				}
			}
		}
		
		// Colocamos las condiciones de venta normales (Si existen)
		if (cantidad > 0) {
			while (puntoDeChequeo < detallesN.size()) {
				vendedor = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCodVendedor();
				supervisor = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCodSupervisor();
				tipCaptura = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getTipoCaptura();
				pFinal = prod.getPrecioRegular();
				cantidadAsignar = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCantidad();
				tipoEntrega = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getTipoEntrega();
				pFinal = MathUtil.roundDouble(pFinal);
				DetalleTransaccion nuevoDetalleTrans = new DetalleTransaccion(
								vendedor,
								prod,
								cantidadAsignar,
								Sesion.condicionNormal,
								supervisor,
								pFinal,
								this.calcularImpuesto(prod,pFinal),
								tipCaptura,
								-1,
								tipoEntrega);
				detallesTransaccion.addElement(nuevoDetalleTrans);
				nuevosNoEmpaques.addElement(new Integer(detallesTransaccion.size() - 1));
				puntoDeChequeo++;
			}
		}
		
		// Preguntamos si los descuentos son acumulativos para aplicar el dcto a COLABORADOR sobre los precios finales
		if (Sesion.desctosAcumulativos && prod.isIndicaDesctoEmpleado() && Sesion.isIndicaDesctoEmpleado()&& this.isAplicaDesctoEmpleado()) {
			aplicarCondicionDescuentoEmpleado (prod, nuevosNoEmpaques, null, 0);
			aplicarCondicionDescuentoEmpleado (prod, nuevosEmpaques, null, 0); 
		}
		
		//Preguntamos si el descuento a empaque es acumulativo para aplicarlo
		if (Sesion.desctoEmpaqueAcumulativo && (prod.getCantidadVentaEmpaque() > 0 )) {
			 Vector nuevosEmp = aplicarCondicionEmpaque(prod, nuevosNoEmpaques);
			 nuevosEmpaques = (Vector)nuevosEmp.elementAt(0);
		}

		// Armamos el vector de retorno
		retorno.addElement(nuevosNoEmpaques);
		retorno.addElement(nuevosEmpaques);

		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarCondicionDeVenta(Producto, Vector, float) - end");
		}
		return new Vector();
	}*/
	
	/**
	 * Aplica la condición de venta"Empaque" a los productos especificados
	 * @param prod Producto al que se le aplicara el descuento a empaque.
	 * @param detallesN Vector de posiciones del producto .
	 * @param prodsEmpaque Cantidad de productos para el empaque
	 * @param posicionMejor Entero que indica la posición que posee el mejor descuento
	 * @param dctoActual Vector que indica el descuento actual que posee el producto
	 * @return Vector - Vector de los productos con la condición de venta empaque aplicada
	 */
	public Vector aplicarCondicionEmpaque(Producto prod, Vector detallesN, float prodsEmpaque, int posicionMejor, Vector dctoActual) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarCondicionEmpaque(Producto, Vector, float, int, Vector) - start");
		}
/*
		Vector nuevosEmpaques = new Vector();
		String vendedor; 
		String supervisor; 
		String tipCaptura; 
		String tipoEntrega;
		float cantidadAsignar;
		int puntoDeChequeo = 0;
		float cantidadDetalleActual;
		Vector resultado = new Vector(2);
		
		double pFinal = ((Double)dctoActual.elementAt(0)).doubleValue();
		
		while (prodsEmpaque > 0) {
			// Si existen empaques, creo el nuevo detalle con el empaque por cada detallesN
			vendedor = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCodVendedor();
			supervisor = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCodSupervisor();
			tipCaptura = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getTipoCaptura();
			tipoEntrega = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getTipoEntrega();
			cantidadDetalleActual = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCantidad();
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
		}*/
		return new Vector();
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
	public Vector aplicarCondicionEmpaque(Producto prod, Vector detallesN) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("aplicarCondicionEmpaque(Producto, Vector) - start");
		}
/*
		Vector nuevosEmpaques = new Vector();
		String vendedor; 
		String supervisor; 
		String tipCaptura; 
		String tipoEntrega;
		float cantidadAsignar;
		int puntoDeChequeo = 0;
		float cantidadDetalleActual;
		Vector resultado = new Vector(2);
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
				// Si existen empaques, creo el nuevo detalle con el empaque por cada detallesN
				vendedor = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCodVendedor();
				supervisor = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCodSupervisor();
				tipCaptura = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getTipoCaptura();
				tipoEntrega = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getTipoEntrega();
				cantidadDetalleActual = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCantidad();
				
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
				nuevoDetalleTrans.setDctoEmpleado(descEmpleado);
				detallesTransaccion.addElement(nuevoDetalleTrans);
				nuevosEmpaques.addElement(new Integer(detallesTransaccion.size() - 1));
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
		}*/
		return new Vector();
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
	public DetalleTransaccion aplicarCondicionPromocion(Producto prod, Vector detallesN, Vector dctoActual, int puntoDeChequeo) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarCondicionPromocion(Producto, Vector, Vector, int) - start");
		}
/*
		String vendedor = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCodVendedor();
		String supervisor = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCodSupervisor();
		String tipCaptura = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getTipoCaptura();
		String tipoEntrega = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getTipoEntrega();
		double pFinal = ((Double)dctoActual.elementAt(0)).doubleValue();
		float cantidadAsignar = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCantidad();
		pFinal = MathUtil.roundDouble(pFinal);
		DetalleTransaccion nuevoDetalleTrans = new DetalleTransaccion(vendedor, prod, cantidadAsignar,
							(String)dctoActual.elementAt(1), supervisor, pFinal, 
							this.calcularImpuesto(prod, pFinal), tipCaptura,
							((Integer)dctoActual.elementAt(2)).intValue(), tipoEntrega);
							
		// Registramos la auditoría de esta función
		Sesion.setUbicacion("FACTURACION","aplicarCondicionPromocion");
		Auditoria.registrarAuditoria("Aplicada Promo " + nuevoDetalleTrans.getCodPromocion() + " Prod " + prod.getCodProducto() + " P.Reg. " + prod.getPrecioRegular() + " P.Prom. " + nuevoDetalleTrans.getPrecioFinal(),'T');
	
		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarCondicionPromocion(Producto, Vector, Vector, int) - end");
		}*/
		return null;
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
	public DetalleTransaccion aplicarCondicionDescuentoEmpleado (Producto prod, Vector posicionDetalles, Vector dctoActual, int puntoDeChequeo) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarCondicionDescuentoEmpleado(Producto, Vector, Vector, int) - start");
		}
/*
		DetalleTransaccion nuevoDetalleTrans = null;
		double pFinal = 0.0;
		double montoDctoEmpleado = 0.0;
		
		if (Sesion.desctosAcumulativos){
			double precioAnterior;
			double nvoImpuesto;
			String condVentaAnterior;
			
			// Calculamos la condición de venta acumulativa para los productos especificados
			for (int i=0; i<posicionDetalles.size(); i++) {
				precioAnterior = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).getPrecioFinal();
				condVentaAnterior = ((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).getCondicionVenta();
				montoDctoEmpleado = (precioAnterior * Sesion.getDesctoVentaEmpleado()) / 100;
				pFinal = precioAnterior - montoDctoEmpleado;
				nvoImpuesto = this.calcularImpuesto(prod, pFinal);
				((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).setPrecioFinal(pFinal);
				((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).setMontoImpuesto(nvoImpuesto);
				((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).setDctoEmpleado(montoDctoEmpleado);
				
				// Chequeamos las condiciones de Venta
				if (condVentaAnterior.equals(Sesion.condicionEmpaque)) {
					((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).setCondicionVenta(Sesion.condicionDesctoEmpEmpaque);
				} else {
					if (condVentaAnterior.equals(Sesion.condicionNormal)) {
						((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).setCondicionVenta(Sesion.condicionDesctoEmpleado);
					} else {
						if (condVentaAnterior.equals(Sesion.condicionPromocion)) {
							((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).setCondicionVenta(Sesion.condicionDesctoEmpProm);
						} else {
							if (condVentaAnterior.equals(Sesion.condicionDesctoPorDefecto)) {
								((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).setCondicionVenta(Sesion.condicionDesctoEmpDesctoDefect);
							} else {
								if (condVentaAnterior.equals(Sesion.condicionCambioPrecio)) {
									((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).setCondicionVenta(Sesion.condicionDesctoEmpCambPrec);
								} else {
									if (condVentaAnterior.equals(Sesion.condicionDesctoPrecioGarantizado)) {
										((DetalleTransaccion)detallesTransaccion.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).setCondicionVenta(Sesion.condicionDesctoEmpPrecioGarantizado);
									}
								}
							}
						}
					}
				}
			}
		} else {
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
			
		}
		
		if (posicionDetalles.size() > 0) {
			// Registramos la auditoría de esta función
			Sesion.setUbicacion("FACTURACION","aplicarCondicionDescuentoEmpleado");
			Auditoria.registrarAuditoria("Aplicado Descuento a Empleado a " + this.getCliente().getCodCliente() + " Prod." + prod.getCodProducto() + ". PFinal." + pFinal ,'T');
		}
			
		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarCondicionDescuentoEmpleado(Producto, Vector, Vector, int) - end");
		}*/
		return null;
	
	}
	
	/**
	 * Obtiene los pagos realizados a la venta.
	 * @return Vector - Vector de pagos realizados.
	 */
	public Vector getPagos() {
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
/*
		if (this.getPagos().isEmpty()) {
			//registramos la venta en la Base de Datos en estado de espera
			this.lineasFacturacion = this.detallesTransaccion.size();
			this.horaFin = Sesion.getHoraSistema();
			this.estadoTransaccion = Sesion.ESPERA;
			this.setDuracion(Control.getCronometro(this.inicio, this.getDuracion()));
			BaseDeDatosVenta.colocarEnEspera(this, identificacion);
		} else
			throw new PagoExcepcion("No se pueden colocar facturas en espera con pagos asociados");
*/
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
	public void actualizarPreciosDetalle(boolean desctoEmpleado) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarPreciosDetalle(boolean) - start");
		}
/*
		Vector productos = new Vector(); 
		boolean registrado= false;
		String codP = "";
	
		//	Se coloca en TRUE o FALSE la variable indicaDescuentoEmpleado de la venta para que se tome en cuenta (o no) este descuento.
		 if(desctoEmpleado){
			 this.setAplicaDesctoEmpleado(true);
		 } else {
			 this.setAplicaDesctoEmpleado(false);
		 }
			 
		// Recorremos el vector de detalles para obtener los distintos productos contenidos en el mismo 
		for(int i=0; i<detallesTransaccion.size(); i++){
			//if(!((DetalleTransaccion)detallesTransaccion.elementAt(i)).getCondicionVenta().equals(Sesion.condicionDesctoEmpDesctoDefect) && !((DetalleTransaccion)detallesTransaccion.elementAt(i)).getCondicionVenta().equals(Sesion.condicionDesctoPorDefecto) && !((DetalleTransaccion)detallesTransaccion.elementAt(i)).getCondicionVenta().equals(Sesion.condicionDesctoPrecioGarantizado)){
			if(!((DetalleTransaccion)detallesTransaccion.elementAt(i)).isCondicionEspecial()) {
				 ((DetalleTransaccion)detallesTransaccion.elementAt(i)).setCondicionVenta(Sesion.condicionNormal);
				 ((DetalleTransaccion)detallesTransaccion.elementAt(i)).setDctoEmpleado(0.00);
			} else {
				if (this.getCliente()!=null && ((this.getCliente().getTipoCliente() == Sesion.COLABORADOR)&&(this.getCliente().getEstadoColaborador() == Sesion.ACTIVO)) && !Sesion.permitirRebajasAempleados) {
					((DetalleTransaccion)detallesTransaccion.elementAt(i)).setCondicionVenta(Sesion.condicionNormal);
					((DetalleTransaccion)detallesTransaccion.elementAt(i)).setDctoEmpleado(0.00);
				} else {
					if (((DetalleTransaccion)detallesTransaccion.elementAt(i)).getCondicionVenta().equals(Sesion.condicionDesctoEmpDesctoDefect)) {
						((DetalleTransaccion)detallesTransaccion.elementAt(i)).setCondicionVenta(Sesion.condicionDesctoPorDefecto);
						((DetalleTransaccion)detallesTransaccion.elementAt(i)).setPrecioFinal(((DetalleTransaccion)detallesTransaccion.elementAt(i)).getPrecioFinal() + ((DetalleTransaccion)detallesTransaccion.elementAt(i)).getDctoEmpleado());
						((DetalleTransaccion)detallesTransaccion.elementAt(i)).setDctoEmpleado(0.00);
					} else {
						if (((DetalleTransaccion)detallesTransaccion.elementAt(i)).getCondicionVenta().equals(Sesion.condicionDesctoEmpPrecioGarantizado)) {
							((DetalleTransaccion)detallesTransaccion.elementAt(i)).setCondicionVenta(Sesion.condicionDesctoPrecioGarantizado);
							((DetalleTransaccion)detallesTransaccion.elementAt(i)).setPrecioFinal(((DetalleTransaccion)detallesTransaccion.elementAt(i)).getPrecioFinal() + ((DetalleTransaccion)detallesTransaccion.elementAt(i)).getDctoEmpleado());
							((DetalleTransaccion)detallesTransaccion.elementAt(i)).setDctoEmpleado(0.00);
						} else {
							if (((DetalleTransaccion)detallesTransaccion.elementAt(i)).getCondicionVenta().equals(Sesion.condicionDesctoEmpCambPrec)) {
								((DetalleTransaccion)detallesTransaccion.elementAt(i)).setCondicionVenta(Sesion.condicionCambioPrecio);
								((DetalleTransaccion)detallesTransaccion.elementAt(i)).setPrecioFinal(((DetalleTransaccion)detallesTransaccion.elementAt(i)).getPrecioFinal() + ((DetalleTransaccion)detallesTransaccion.elementAt(i)).getDctoEmpleado());
								((DetalleTransaccion)detallesTransaccion.elementAt(i)).setDctoEmpleado(0.00);
							}
						}
					}
				}
			}
			
			if (productos.size()== 0){
				productos.addElement(detallesTransaccion.elementAt(i));
			} else {
				registrado = false;
				for(int j=0; j<productos.size();j++) {
					codP = ((DetalleTransaccion)productos.elementAt(j)).getProducto().getCodProducto();
					if(codP.equals((((DetalleTransaccion)detallesTransaccion.elementAt(i)).getProducto()).getCodProducto())){
						registrado = true;	
						break;					
					}
				}
				if(registrado == false){
					productos.addElement(detallesTransaccion.elementAt(i));
				}			
			}
		}
	
		// Ahora actualizamos los precios de los detalles por cada producto.
		for (int i=0; i<productos.size(); i++) {
			Producto prod = ((DetalleTransaccion)productos.elementAt(i)).getProducto();
			this.actualizarPreciosDetalle(prod); 
		}
		actualizarMontoTransaccion();
*/
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarPreciosDetalle(boolean) - end");
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
/*
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
*/
		if (logger.isDebugEnabled()) {
			logger.debug("asignarCliente(String, String) - end");
		}
	}

	/**
	 * Método verMensajesCliente
	 * 
	 * 
	 */
	private void verMensajesCliente(){
		if (logger.isDebugEnabled()) {
			logger.debug("verMensajesCliente() - start");
		}
/*
		if(CR.me != null){
			if(this.getCliente().getCodCliente() != null && (this.getCliente().getMensajes().size() > 0)) {
				Iterator ciclo = this.getCliente().getMensajes().iterator();
				while(ciclo.hasNext()){
					CR.me.mostrarAviso(ciclo.next().toString(), true);
				}
				
			}
		}
*/
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
/*
		//Se arma el código del cliente correctamente (X-0000000)
		String idCliente = clienteTemp.getCodCliente();
		if (clienteTemp.getTipoCliente() == Sesion.CLIENTE_NATURAL)
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
*/
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
	public Vector obtenerRenglones(String codProducto, boolean isCodigoExterno) throws ProductoExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerRenglones(String) - start");
		}
/*
		Vector result = new Vector(); //Vector que contendrá el resultado del método
		String codInterno = MediadorBD.obtenerCodigoInterno(codProducto);
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
*/
		return new Vector();
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
	public Venta (Apartado apartado, double montoPagado, double mtoVto) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion, PagoExcepcion {
		/*super(Sesion.getTienda().getNumero(), Sesion.getFechaSistema(), Sesion.getCaja().getNumero(),
			Sesion.getCaja().getNumRegistro(), Sesion.getHoraSistema(), Sesion.usuarioActivo.getNumFicha(),
			Sesion.VENTA, Sesion.PROCESO);
		/*pagos = new Vector();
		
		Sesion.setUbicacion("FACTURACION","ingresarLineaProducto");
		Auditoria.registrarAuditoria("Iniciando facturacion con numRegInicial: " + this.getNumRegCajaInicia() + " de Apartado/PE Nro. " + apartado.getNumServicio(), 'T');
		
		// Armamos la cabecera de venta
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
		}
		
		// Agregamos un pago de tipo abonos anteriores por el monto de los abonos
		pagos.addElement(ManejoPagos.realizarPagoAbonos(montoPagado-apartado.getAbonoNuevo().getMontoBase()));
		
		// Efectuamos el pago final
		for (int i=0; i<apartado.getAbonoNuevo().getPagos().size(); i++)
			pagos.addElement((Pago)apartado.getAbonoNuevo().getPagos().elementAt(i));
			
		// Finalizamos la venta
		try {
			this.finalizarTransaccion();
		} catch (PrinterNotConnectedException e) {
			logger.error("Venta(Apartado, double, double)", e);
		}

		// Imprimimos el comprobante de cancelacion de apartado.
		CancelacionApartadoPedidoEspecial.imprimirComprobate(apartado, false);*/
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
	public Venta (Apartado apartado) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion, PagoExcepcion {
		/*super(Sesion.getTienda().getNumero(), Sesion.getFechaSistema(), Sesion.getCaja().getNumero(),
			Sesion.getCaja().getNumRegistro(), Sesion.getHoraSistema(), Sesion.usuarioActivo.getNumFicha(),
			Sesion.VENTA, Sesion.PROCESO);
		/*pagos = new Vector();
		
		Sesion.setUbicacion("FACTURACION","ingresarLineaProducto");
		Auditoria.registrarAuditoria("Iniciando facturacion con numRegInicial: " + this.getNumRegCajaInicia() + " de Apartado/PE Nro. " + apartado.getNumServicio(), 'T');
		
		// Armamos la cabecera de venta
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
		}
		
		// Agregamos un pago de tipo abonos anteriores por el monto de los abonos
		pagos.addElement(ManejoPagos.realizarPagoAbonos(apartado.montoAbonos()));
		
		// Finalizamos la venta
		try {
			this.finalizarTransaccion();
		} catch (PrinterNotConnectedException e) {
			logger.error("Venta(Apartado)", e);
		}
		
		// Imprimimos el comprobante de cancelacion de apartado.
		CancelacionApartadoPedidoEspecial.imprimirComprobate(apartado, true);*/
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
	public void facturarCotizacion(Cotizacion cotizacion, Vector pagosRealizados, double mtoVto) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion, PagoExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("facturarCotizacion(Cotizacion, Vector, double) - start");
		}
/*
		pagos = new Vector();
		
		Sesion.setUbicacion("FACTURACION","ingresarLineaProducto");
		Auditoria.registrarAuditoria("Iniciando facturacion con numRegInicial: " + this.getNumRegCajaInicia() + " de Cotizacion Nro. " + cotizacion.getNumServicio(), 'T');
		
		// Armamos la cabecera de venta
		this.montoBase += cotizacion.getMontoBase();
		this.montoImpuesto += cotizacion.getMontoImpuesto();
		this.montoVuelto = mtoVto;
		this.lineasFacturacion += cotizacion.getLineasFacturacion();
	
		// Armamos el detalle de la venta
		for (int i=0; i<cotizacion.getDetallesServicio().size(); i++) {
			if (((Boolean)cotizacion.getEntregar().elementAt(i)).booleanValue()) {
				DetalleServicio detalleActual = (DetalleServicio)cotizacion.getDetallesServicio().elementAt(i);
				this.detallesTransaccion.addElement(new DetalleTransaccion(null, detalleActual.getProducto(), 
					detalleActual.getCantidad(), detalleActual.getCondicionVenta(), detalleActual.getCodSupervisor(),
					detalleActual.getPrecioFinal(), detalleActual.getMontoImpuesto(), detalleActual.getTipoCaptura(),
					detalleActual.getCodPromocion(), detalleActual.getTipoEntrega()));
			}
		}
		// Agregamos los pagos realizados a la cotizacion
		for (int i=0; i<pagosRealizados.size(); i++)
			pagos.addElement((Pago)pagosRealizados.elementAt(i));
			
		// Finalizamos la venta
		try {
			this.finalizarTransaccion();
		} catch (PrinterNotConnectedException e) {
			logger.error("facturarCotizacion(Cotizacion, Vector, double)", e);
		}
*/
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
			if(((DetalleTransaccion)this.getDetallesTransaccion().elementAt(i)).getTipoEntrega().equals(Sesion.ENTREGA_CLIENTE_RETIRA)) {
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

	}
	
	
	public Object clone(){
		Venta venta=null;
//		try {
//			venta = (Venta)super.clone();
//		} catch(CloneNotSupportedException ex) { }
//		venta.pagos = (Vector) venta.pagos.clone();
//		for(int i=0;i<pagos.size();i++){
//			Pago pago = ((Pago)venta.pagos.get(i));
//			venta.pagos.set(i,pago.clone());
//		}
//		venta.detallesTransaccion = (Vector) venta.detallesTransaccion.clone();
//		for(int i=0;i<detallesTransaccion.size();i++){
//			DetalleTransaccion detalleTransaccion = ((DetalleTransaccion)venta.detallesTransaccion.get(i));
//			venta.detallesTransaccion.set(i,detalleTransaccion.clone());
//		}
		return venta;
	}
	
	/**
	 * Constructor de la clase Venta que crea una nueva basandose en la que se le pasa como parámetro pero con 
	 * sus nuevos números de registros.
	 */
	public void concatenarVenta(Venta venta2) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {		
//	
//		// Armamos el detalle de la venta
//		for (int i=0; i<venta2.getDetallesTransaccion().size(); i++) {
//			DetalleTransaccion detalleActual = (DetalleTransaccion)venta2.getDetallesTransaccion().elementAt(i);
//			this.detallesTransaccion.addElement(new DetalleTransaccion(venta2.getCodCajero(), detalleActual.getProducto(), 
//					detalleActual.getCantidad(), detalleActual.getCondicionVenta(), detalleActual.getCodSupervisor(),
//					detalleActual.getPrecioFinal(), detalleActual.getMontoImpuesto(), detalleActual.getTipoCaptura(),
//					detalleActual.getCodPromocion(), detalleActual.getTipoEntrega()));
//		}
//		actualizarMontoTransaccion();
//		actualizarPreciosDetalle(false);
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
	public Vector getPagosClonados() {
		return pagosClonados;
	}

	/**
	 * @param pagosClonado el pagosClonado a establecer
	 */
	public void setPagosClonados(Vector pagosClonados) {
		this.pagosClonados = pagosClonados;
	}
	
	/**
	 * Inicializa el vector de pagos clonados
	 * **/
	public void asignaPagosClonados(Venta venta){
		//nada
	}
	
	/**
	 * Comprueba si el vector de pagos contiene un pago de la
	 * forma fp
	 * @param fp Forma de pago que se desea comprobar
	 * @return boolean true: si hay un pago de la forma fp
	 * 					false: caso contrario
	 * **/
	public boolean contieneFormaPago(FormaDePago fp){
		return false;
	}
	
	/**
	 * Obtiene la posicion en la que se encuentra un detalle dentro del vector
	 * de detalles transaccion de la venta
	 * @param dt
	 * @return int posicion
	 * @throws Exception 
	 */
	public int getPosicionDetalle(DetalleTransaccion dt) throws Exception{
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
	public Vector getDetallesAEliminar() {
		return detallesAEliminar;
	}

	/**
	 * @param detallesAEliminar el detallesAEliminar a establecer
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