 /**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.manejadorsesion
 * Programa   : Sesion.java
 * Creado por : gmartinelli
 * Creado en  : 20-oct-03 8:03:23
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 3.0.0
 * Fecha       : 22/07/2008 09:22:34 AM
 * Analista    : jgraterol
 * Descripción : Agregadas variables para el manejo de promociones
 * =============================================================================
 * Versión     : 2.0.0
 * Fecha       : 25/07/2004 09:22:34 AM
 * Analista    : yzambrano
 * Descripción : Agregada vectores para almacenar estados, ciudades y urbanizaciones
 * =============================================================================
 * Versión     : 2.0.0
 * Fecha       : 11/07/2004 09:22:34 AM
 * Analista    : yzambrano
 * Descripción : Agregada constante para nuevos tipos de afiliados. Venezolano, 
 * 				 extranjero y pasaporte 
 * =============================================================================
 * Versión     : 2.0.0
 * Fecha       : 29/06/2004 09:22:34 AM
 * Analista    : yzambrano
 * Descripción : Agregada constante para clietnes no afiliados
 * =============================================================================
 * Versión     : 2.1.0
 * Fecha       : 01/07/2004 11:19 AM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : - Agregados los atributos tiendaEnLinea y verificarLinea con su 
 * 				respectiva inicialización en el constructor.
 * 				 - Modificada la implementación del método isCajaEnLinea de modo 
 * 				que la verificación de la línea sea a través de un chequeo de 
 * 				conexión de la red y no del chequeo de conexión JDBC, esta función
 * 				se expande al método isTiendaEnLinea. Lo que las diferencia es 
 * 				que una es para el Servidor de la Tienda y el otro para el 
 * 				Servidor Central.
 * =============================================================================
 * Versión     : 2.0.0
 * Fecha       : 29/06/2004 09:22:34 AM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : - Agregados los atributos cierreCajero y codUsuario con sus respectivos métodos get/set.
 * 					 -	Implementados los métodos setEstado, setCierreCajero, setCodUsuario y getCodUsuario de modo
 * 				que actualizen el registro correspondiente en la BD.
 * =============================================================================
 * Versión     : 1.9.0
 * Fecha       : 25/05/2004 01:27:34 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : - Actualizadas las referencias a valores de configuración para invocar los métodos de la
 * 				clase EPAPreferencesProxy a través de las preferencias "ProyectoCR" asociadas al objeto
 * 				"InitCR.preferenciasCR".
 * =============================================================================
 * Versión     : 1.8 (según CVS)
 * Fecha       : 11/02/2004 08:56:41 AM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Modificaciones para mejoras en la invocación del método obtenerModMetFun.
 * 				 Atributos agregados ->
 * 					modulo, funcion, metodo
 * 				 métodos modificados ->
 * 					métodos get/set para los atributos modulo, funcion, metodo.
 * 					setUbicacion()
 * =============================================================================
 * Versión     : 1.0.3
 * Fecha       : 27/11/2003 01:56:41 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Modificaciones por requerimientos de integración BECO y EPA.
 * 				 Atributos agregados ->
 * 					usuarioAutorizante
 * 				 métodos modificados ->
 * 					Sesion()
 * =============================================================================
 * Versión     : 1.0.2
 * Fecha       : 31/10/2003 03:28:41 PM
 * Analista    : Gabriel Martinelli
 * Descripción : Modificación de la clase para requerimientos de BECO.
 * =============================================================================
 * Versión     : 1.0.1
 * Fecha       : 30/10/2003 02:10:41 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Integración de la clase Sesion para EPA y BECO
 * =============================================================================
 */
package com.becoblohm.cr.manejadorsesion;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Vector;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.beco.cr.actualizadorPrecios.promociones.ProductoCombo;
import com.becoblohm.cr.CR;
import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.manejadorauditoria.Auditoria;
import com.becoblohm.cr.manejarmantenimiento.Ciudad;
import com.becoblohm.cr.manejarmantenimiento.Estado;
import com.becoblohm.cr.manejarmantenimiento.Urbanizacion;
import com.becoblohm.cr.manejarusuario.Usuario;
import com.becoblohm.cr.mediadoresbd.BaseDeDatosVenta;
import com.becoblohm.cr.mediadoresbd.BeansSincronizador;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.sincronizador.HiloTransferenciasInmediatas;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.crprinterdriver.CRFiscalPrinterOperations;
import com.epa.crprinterdriver.CRPrinterOperations;
import com.epa.crprinterdriver.PrinterNotConnectedException;
import com.epa.crprinterdriver.event.CRFPEvent;
import com.epa.crprinterdriver.event.FiscalPrinterListener;
import com.epa.preferencesproxy.EPAPreferenceProxy;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;

/** 
 * Descripción: 
 * 		Esta clase carga los parámetros y variables necesarias para el 
 * correcto funcionamiento del sistema. También proporciona métodos que 
 * permiten el control de cada una de sus propiedades.
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
* Fecha: agosto 2011
*/
public class Sesion implements FiscalPrinterListener {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Sesion.class);

	// Nueva constante para codigo de Perfil de Supervisor
	public static final String PERFIL_SUPERVISOR = "3";
	
	// Constantes para los estados de la caja
	public static final String APAGADA = "1";
	public static final String INICIALIZANDO = "2";
	public static final String NO_OPERATIVA = "3";
	public static final String CERRADA = "4";
	public static final String CONSULTA = "5";
	public static final String INICIADA = "6";
	public static final String BLOQUEADA = "7";
	public static final String FACTURANDO = "10";
	public static final String ANULANDO = "13";

	// Constantes para las variables char que indican Si o No
	public static final char SI = 'S';
	public static final char NO = 'N';
	public static final char ACTIVO = 'A';
	public static final char VIGENTE = 'V';
	
	// Constantes de los tipos de transaccion
	public static final char VENTA = 'V';
	public static final char DEVOLUCION = 'D';
	public static final char ANULACION = 'A';
	
	// Constantes de los estados de las transacciones
	public static final char PROCESO = 'P';
	public static final char ESPERA = 'E';
	public static final char IMPRIMIENDO = 'I';
	public static final char FINALIZADA = 'F';
	public static final char ABORTADA = 'A';

	// Constantes de los tipos de servicio
	public static final String COTIZACION = "01";
	public static final String APARTADO = "02";
	public static final String DESPACHO = "03";
	public static final String COMANDA = "04";
	public static final String ENTDOMICILIO = "05";
	public static final String LISTAREGALOS = "06";
	public static double PORC_MINIMO_ABONO = 30;
	
	// Constantes de los estados de las cotizaciones
	public static final char COTIZACION_ACTIVA = 'V';
	public static final char COTIZACION_FACTURADA = 'F';
	public static final char COTIZACION_BLOQUEADA = 'B';
	
	// Constantes de los estados de los servicios (Entrega a Domiclio y despachos fuera de caja)
	public static final char SERVICIO_ACTIVA = 'A';
	public static final char SERVICIO_ANULADO = 'E';
	public static final char SERVICIO_FINALIZADO = 'F';
	public static final char SERVICIO_IMPRIMIENDO = 'I';

	// Constantes de los estados de los apartados
	public static final char APARTADO_ACTIVO = 'P';
	public static final char APARTADO_PAGADO = 'C';
	public static final char APARTADO_FACTURADO = 'F';
	public static final char APARTADO_ANULADO_EXONERADO = 'X';
	public static final char APARTADO_ANULADO_CON_CARGO = 'E';
	public static final char APARTADO_VENCIDO = 'V';
	public static final char ABONO_ACTIVO = 'A';
	public static final char ABONO_ANULADO = 'E';
	public static final char ABONO_FINAL = 'F';
	public static final String FORMA_PAGO_ABONO = "15";
	public static final String FORMA_PAGO_EFECTIVO = "01";
	public static final String FORMA_PAGO_CONDICIONAL = "08";
	
		//Constantes de los estados de Lista de Regalos
	public static final char LISTAREGALOS_ACTIVA = 'A';
	public static final char LISTAREGALOS_CERRADA = 'C';
	public static final char LISTAREGALOS_ANULADA = 'N';
	public static final char LISTAREGALOS_INACTIVA = 'X';
	
	// Constantes para los tipos de entrega de los productos
	public static final String ENTREGA_CAJA = "Caja";
	public static final String ENTREGA_DESPACHO = "Despacho";
	public static final String ENTREGA_DOMICILIO = "Domicilio";
	public static final String ENTREGA_CLIENTE_RETIRA = "C. Retira";
	
	public static final char COD_ENTREGA_CAJA = 'C';
	public static final char COD_ENTREGA_DESPACHO = 'D';
	public static final char COD_ENTREGA_DOMICILIO = 'F'; //Flete
	public static final char COD_ENTREGA_CLIENTE_RETIRA = 'R';
	
	
	// Constantes para la forma de Pago Retención
	public static final String FORMA_PAGO_RETENCION = "8888888888";
	public static final int TIPO_PAGO_CARGO_CUENTA = 7;
	public static double porcentajeRetencionIVA = 75;
	
	//Tipo de forma de pago para las formas de pago que otorga BECO por alguna promocion
	public static final int TIPO_PAGO_BECO = 20;

	// Constantes para mensajes de ventanas
	public static final String MSG_CANCELAR = "¿Seguro que desea cancelar \n la transacción actual?";

	// Constantes para Facturas en Espera
	public static final int LONG_FACT_ESPERA = 8;
	
	// Parámetros de conexión con la base de datos
	private static String dbEsquema;
	private static String dbClaseLocal;
	private static String dbUrlLocal;
	private static String dbClaseServidor;
	private static String dbUrlServidor;
	private static String dbClaseServidorCentral;
	private static String dbUrlServidorCentral;
	private static String rutaObjetoDevolucion;

	// El objetivo es registrar los mismos datos de acceso a BD para la CR y el Servidor
	private static String dbUsuario;	
	private static String dbClave;	

	// Parámetros relativos a la Tienda de la sesión actual
	private static Tienda tienda;
	private static boolean indicaDesctoEmpleado;
	private static double desctoVentaEmpleado;
	private static boolean utilizarVendedor;
	private static boolean cambiarPrecio;

	// Parámetros relativos al Usuario de la sesión actual
	public static Usuario usuarioActivo;
	public static Usuario usuarioAutorizante;
	private static Time horaIngreso;

	// Parámetros relativos al seguimiento de la secuencia de ejecución del sistema
	private static String metodo;
	private static String modulo;
	private static String funcion;

	// Parámetros relativos a la facturacion de la sesión actual
	private static boolean requiereCliente;
	private static boolean autorizarCierreCajero;
	public static boolean finalizarSync = false;
	/*
	 * (Ojo) Se mantienen en la clase Sesion si son exclusivos de Realizar Ventas???
	 * Consultar al equipo de desarrollo de BECO.
	 */
	// Parámetros correspondientes a Realizar Ventas
	public static String capturaMixto;
	public static String capturaEscaner;
	public static String capturaTeclado;	
	public static String capturaProceso;
	public static String condicionEmpaque;
	public static String condicionNormal;
	public static String condicionPromocion;
	public static String condicionDesctoEmpleado;
	public static String condicionCambioPrecio;
	public static String condicionDesctoPorDefecto;
	public static String condicionDesctoPrecioGarantizado;
	public static String condicionDesctoEmpProm;
	public static String condicionDesctoEmpEmpaque;
	public static String condicionDesctoEmpCambPrec;
	public static String condicionDesctoEmpDesctoDefect;
	public static String condicionDesctoEmpPrecioGarantizado;
	public static String condicionEmpaquePromocion;
	public static String condicionEmpaquePromocionEmpleado;
	public static String condicionVentaPorVolumen;
	
	//ACTUALIZACION BECO: 22/07/2008
	//condiciones de venta para promociones
	public static String condicionDescuentoCorporativo;
	public static String condicionDescuentoEnCombo;
	public static String condicionProductoGratis;
	public static String condicionComboPorCantidades; //2x1, 3x2, etc
	public static String condicionDescuentoEnProductos;
	public static String condicionDescontadoPorCombo; 
	public static String condicionMixta;
	
	
	public static Vector<String> condicionesCombo = new Vector<String>();
	public static Vector<String> condicionesComboPrecioFinal = new Vector<String>();
	public static Vector<String> condicionesEspeciales = new Vector<String>();
	public static Vector<String> condicionesNuevas = new Vector<String>();
	
	/*
	* Modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en 'HashTable'
	* Fecha: agosto 2011
	*/
	public static Hashtable<String,Vector<ProductoCombo>> productosCombo = new Hashtable<String,Vector<ProductoCombo>>();
	

	// Variable que indica si los descuentos son acumulativos (descuento a COLABORADOR + descuento mejor)
	public static boolean desctosAcumulativos;
	
	//Agregado por módulo de promociones
	public static boolean promocionesAcumulativas = true;
	
	// Variable que indica si los descuentos a empaques son acumulativos 
	public static boolean desctoEmpaqueAcumulativo;
	
	// Variable que indica si se permite hacer rebajas cuando se le factura a un cliente COLABORADOR
	public static boolean permitirRebajasAempleados;
	
	// Constante que nos indica el numero de digitos a mostrar en las tablas con respecto a los codigos internos
	public static int longitudCodigoInterno;
	
	// Constante que nos indica el numero de productos a partir del cual debe mostrarse un aviso al cajero
	private static double porcentajeMensajeEmpaque;

	// Constantes para la informacion a imprimir en el dorso de los cheques
	private static String tipoCuentaCheque;
	private static String numeroCuentaCheque;
	private static String nombreBancoCheque;
	
	// Parámetros de la sesión
	private static int nivelAuditoria;

	// Parámetros relativos a la Caja de la sesión actual
	private static Caja caja;
	private static boolean cajaEnLinea;
	private static boolean tiendaEnLinea;
	private static boolean verificarLinea;
	private static boolean servCentralEnLinea;
	
	// Instancia de las Impresoras
	public static boolean impresoraFiscal;
	public static CRPrinterOperations crPrinterOperations;
	public static CRFiscalPrinterOperations crFiscalPrinterOperations;	
	private static int numCajaPref;

	// Constantes para los tipo de clientes
	public static final char CLIENTE_NATURAL = 'N';
	public static final char CLIENTE_JURIDICO = 'J';
	public static final char CLIENTE_GUBERNAMENTAL = 'G';
	public static final char CLIENTE_DIPLOMATICO = 'D';
	public static final char CLIENTE_VENEZOLANO = 'V';
	public static final char CLIENTE_EXTRANJERO = 'E';
	public static final char CLIENTE_PASAPORTE = 'P';
	
	//Nuevas constantes para CRM... Estado Civil
	public static final char SOLTERO = 'S';
	public static final char CASADO = 'C';
	public static final char DIVORCIADO = 'D';
	public static final char VIUDO = 'V';
	
	//Nuevas constantes para CRM... Genero
	public static final char MASCULINO = 'M';
	public static final char FEMENINO = 'F';
	
	//Constante para verificar si se debera pedir los datos del cliente para las transacciones en caja crm..
	public static char Pedir_Datos_Cliente = 'S';
	
	// Constante para los clientes registrados por la caja
	public static final char CLIENTE_REGISTRADO = 'C';
	
	// Constante para los clientes registrados como afiliados
	public static final char CLIENTE_AFILIADO = 'N';

	//Constantes que indica el tipo de afiliado de los empleados ('E').Está asociado al tipo de cliente en la tabla "Afiliado de la BD
	public static final char COLABORADOR = 'E';
	
	//Constantes que indican el número de veces que se imprimen ciertos reportes
	public static final int NRO_COMPROBANTES_APARTADO = 2;
	public static final int NRO_COMPROBANTES_ABONO_LR = 2;
	public static final int NRO_NOTAS_ENTREGA = 3;
	public static final int NRO_NOTAS_DESPACHO = 3;
	
	public static String colorCombo;

	// Constante para el tipo de manjeo de la gaveta (por impresora o por visor)
	public static final String GAVETA_POR_IMPRESORA = "impresora";
	public static final String GAVETA_POR_VISOR = "visor";
	public static boolean aperturaGavetaPorImpresora = false;
	
	//Variables que indican los tipo de entregas disponibles que se mostraran en el comboBox de los tipos
	//de entrega de la pantalla principal
	public static boolean entregaCaja = true;
	public static boolean entregaCteRetira;
	public static boolean entregaDespacho;
	public static boolean entregaDomicilio;
	
	// Constantes de los estados de las promociones (Por producto)
	public static final char PROMOCION_ACTIVA = 'A';
	public static final char PROMOCION_INACTIVA = 'E';
	
	// Declaración de arreglo para almacenar estados
	public static Vector<Estado> estados = null;
	public static Vector<Ciudad> ciudades = null;
	public static Vector<Urbanizacion> urbanizaciones = null;
	public static Vector<String> tipoEventosLR = null;
	
	//Monto mínimo autorizado para devolución
	private static float montoMinimoDevolucion = 0;
	
	
	//Indica si el reporte z se ejecuto ok
	public static boolean reporteZOK = true;
	//Constantes para la forma de Pago Transacción premiada
	public static final String FORMA_PAGO_TRANSACCION_PREMIADA = "9999999999";
	public static final String FORMA_PAGO_PROMO_MERCADEO = "5555555555";
	public static final String FORMA_PAGO_PROMO_MERCHANDISING = "6666666666";
	public static final String FORMA_PAGO_OBSEQUIO_BR = "4444444444";

	//Indiica que esta en espera la respuesta de ejecucion de un reporte z
	public static boolean imprimiendoReporteZ = false;
	
	
	//Constantes para la forma de pago por cupones de descuento
	public static final String FORMA_PAGO_CUPON_DESCUENTO = "7777777777";
	public static final String FORPA_PAGO_CUPON_DESCUENTO_AS = "20";
	
	//Constantes para los tipos de promocion
	public static final char TIPO_PROMOCION_TRANSACCION_PREMIADA = 'A';
	public static final char TIPO_PROMOCION_REGALO_BONO_REGALO = 'B';
	public static final char TIPO_PROMOCION_REGALO_CALCOMANIA = 'C';
	public static final char TIPO_PROMOCION_REGALO_CUPON = 'D';
	public static final char TIPO_PROMOCION_REGALO_PRODUCTO = 'E';
	public static final char TIPO_PROMOCION_AHORRO_COMPRA = 'F'; //(patrocinantes sobre el total de la compra)
	public static final char TIPO_PROMOCION_DESCUENTO_EN_COMBO = 'G'; //(patrocinantes por cantidad Y AMAND) POR PRODUCTO
	public static final char TIPO_PROMOCION_PRODUCTO_GRATIS = 'H';
	
	public static final char TIPO_PROMOCION_COMBO_CANTIDADES_PRODUCTO = 'I';
	public static final char TIPO_PROMOCION_COMBO_CANTIDADES_CATEGORIA = 'X';
	public static final char TIPO_PROMOCION_COMBO_CANTIDADES_DEPARTAMENTO = 'S';
	public static final char TIPO_PROMOCION_COMBO_CANTIDADES_LINEA = 'T';
	public static final char TIPO_PROMOCION_COMBO_CANTIDADES_SECCION = 'U';
	public static final char TIPO_PROMOCION_COMBO_CANTIDADES_MARCA = 'V';
	public static final char TIPO_PROMOCION_COMBO_CANTIDADES_REFERENCIA = 'W';
	
	public static final char TIPO_PROMOCION_DESCUENTO_CATALOGO = 'J';
	public static final char TIPO_PROMOCION_CUPON_DESCUENTO_BS = 'K';
	public static final char TIPO_PROMOCION_CUPON_DESCUENTO_PORCENTAJE = 'L';
	public static final char TIPO_PROMOCION_CALCOMANIA_BS = 'M';
	public static final char TIPO_PROMOCION_CALCOMANIA_PORCENTAJE = 'N';
	public static final char TIPO_PROMOCION_COORPORATIVA = 'O';
	public static final char TIPO_PROMOCION_DESCUENTO_SOBRE_PRODUCTO = 'P';
	public static final char TIPO_PROMOCION_DESCUENTO_EN_COMBO_LINEA = 'R'; //(patrocinantes por cantidad Y AMAND) POR LINEA
	
	//Promociones de bono regalo
	public static final char TIPO_PROMOCION_BR_PORC_RECARGA = 'A';
	
	//Constantes para PDA
	public static final String pdaTipoMensajeConsultaPrecio="ConsultarPrecio";
	public static final String pdaTipoMensajeVentaEspera="VentaEnEspera";
	public static final String pdaTipoMensajeRecuperarVenta="RecuperarVenta"; 
	public static final String pdaCampoIDEspera="idventa";
	public static final String pdaCampoDetalle="detalle";
	public static final String pdaCodProducto="codproducto";
	public static final String pdaCantidad="cantidad";
	public static final String pdaDetalle="detalle";
	public static final String pdaIdVenta="idventa";
	public static final String pdaOperacionExitosa="operacionExitosa";
	public static final String pdaError="error";
	public static final String pdaTipoMensajeIniciarSesion = "IniciarSesion";
	public static final String pdaOperacionInicioSesion = "inicioSesion";
	
	//Constantes para sincronizacion inmediata:
	public static int tiempoTimeOut = 5000;
	public static String ipServidorPDA = "";
	public static int puertoServidorPDA = 35993;
	public static int puertoEscuchaCRPDA = 35995;

	public final static int POLITICA_ANUAL = 1;                 // Frecuencia de Sincronización anual. Cada 30/06
	public final static int POLITICA_MENSUAL = 2;               // Frecuencia de Sincronización mensual. Fin de mes
	public final static int POLITICA_DIARIO = 3;                // Frecuencia de Sincronización diaria. Fin de día
	 
	 
	
	 // Números asignados a las operaciones del sistemaPDA
	public static final int OperacionVenta = 1;
	public static final int OperacionIndicadores = 2;
	public static final int OperacionTransferencia= 3;
	public static final int OperacionCarteles = 4;
	public static final int OperacionCotizacion = 5;
	public static final int OperacionGuardarCotizacion = 5001;
	
	public static final int tamConsecutivo = 3;
	public static final int tamCodigoBeco = 9;
	
	public static HiloTransferenciasInmediatas HILO_TRANSFERENCIAS;
	public static String pathVouchers = "C:\\\\vPos\\voucher\\";
	
	public static String scriptsComandos = "aScriptComandos.txt";
	public static String sentenciaSQL = "aSentenciaSQL.txt";
	public static String cambioPreferencias = "aCambioPreferencias.txt"; 
	public static boolean pantallaVueltoAceptada = false;
	
	/**
	 * Formas de pago
	 */
	public static final String FORMA_PAGO_DEBITO = "03";
	public static final String FORMA_PAGO_CREDITO = "04";
	public static final String FORMA_PAGO_CHEQUE_SERVICIO = "06";
	public static final String FORMA_PAGO_BONOREGALO_E = "22";
	
	/**
	 * Estado para las transacciones de bono regalo
	 */
	//Se imprimio comprobante fiscal
	public static final char BONO_REGALO_FACTURADA = 'F';
	//Se completo la transaccion sin que hayan salido comprobantes
	public static final char BONO_REGALO_COMPLETADA = 'C';
	//la transaccion esta en proceso
	public static final char BONO_REGALO_EN_PROCESO = 'P';
	//se imprimió el comprobante fiscal
	public static final char BONO_REGALO_LISTO = 'L';
	//se anuló la venta de Bono Regalo
	public static final char BONO_REGALO_ANULADA = 'A';
	//se anuló la venta no facturada
	public static final char BONO_REGALO_ELIMINADA = 'X';
	
	/**
	 * Tipos tr transaccion para Bono Regalo
	 */
	public static final char TIPO_TRANSACCION_BR_VENTA = 'V';
	public static final char TIPO_TRANSACCION_BR_RECARGA = 'R';
	public static final char TIPO_TRANSACCION_BR_RECARGA_PROMO = 'P';
	
	/**
	 * Tipos de comprobante fiscal de Bono Regalo
	 */
	public static final char TIPO_COMPROBANTE_BR_VENTA='V';
	public static final char TIPO_COMPROBANTE_BR_ANULACION='A';
	
	/**
	 * Acciones de BR
	 */
	public static final char ACCION_BONO_REGALO_REIMPRESION ='R';
	public static final char ACCION_BONO_REGALO_ANULACION ='A';
	public static final char ACCION_BONO_REGALO_FACTURACION ='F';
	
	/**
	 * Estado registro de detalle transaccion de bono regalo
	 */
	public static final char DETALLE_BR_ACTIVO='A';
	public static final char DETALLE_BR_ELIMINADO='E';
	
	/**
	 * Datos de smtp
	 * Agregado 28-02-2012 para el envio de correos de notificación 
	 * jperez
	 */
	private static String smtpHost =  "smtp.gmail.com";
	private static int smtpPort = 465;
	
	private static String mailRemitente="soporte@beco.com.ve";//"s10e909d";
	private static String passwordRemitente="s10e909d";//"s10e909d";
	
	
	/**
	 * Contructor para Sesion
	 * 		Crea la sesión actual. Inicializa los parámetros (clase, url, 
	 * usuario, clave) por defecto para la conexión a la base de datos de 
	 * la caja registradora y del servidor para tomar todos los parametros 
	 * necesarios para el sistema.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'LinkedHashMap'
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unchecked")
	public Sesion () throws UsuarioExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		// Creamos la conexion con la base de datos con los datos locales por defecto
		
		iniciarDatos();

		setUbicacion("INICIO DEL SISTEMA", "iniciarSesion");

		// Inicializamos el nivel de auditoria de la sesion
		new Auditoria();

		// Verificamos la condición para inicio de caja fuera de línea
		boolean verificar = true;
		try {
			verificar = InitCR.preferenciasCR.getConfigStringForParameter("sistema", "verificarLinea").equalsIgnoreCase("S") ? true : false;
		} catch (NoSuchNodeException e1) {
			logger.error("Sesion()", e1);

			Auditoria.registrarAuditoria("Falla carga de las preferencias del sistema.", 'E');
		} catch (UnidentifiedPreferenceException e1) {
			logger.error("Sesion()", e1);

			Auditoria.registrarAuditoria("Falla carga de las preferencias del sistema.", 'E');
		} catch (Exception e) {
			logger.error("Sesion()", e);
		}
		
		Sesion.setVerificarLinea(verificar);
		
		chequearLineaCaja();

		// Sincronizamos Datos Bases si no se encuentran en la Caja (Registro Caja Inexistente) 
		// Datos bases son: Tienda, Publicidad, Caja, Estados, Funcion, Perfil, Region, Usuario, Modulos,
		// Metodos, FuncionMetodos, MaquinaDeEstados, FuncionPerfil
		sincronizarConfiguracionCaja();
		// Obtenemos los datos de la tienda
		cargarDatosTienda();
		
		// Obtenemos datos la caja
		cargarDatosCaja();

		// Configuración de Tabla puntoagilcaja para Manejo de Pago por Merchant
		MediadorBD.registrarConfPuntoagil(Sesion.getTienda().getNumero(), Sesion.getCaja().getNumero());

		//30/05/2011 Manejo de estado APAGADA para no permitir el inicio de varias cajas a la vez
		/*if (!caja.getEstado().equals(Sesion.APAGADA)) {
			System.exit(0);
		}*/
		
		
		/*
		 * Pasar la caja al estado "INICIALIZANDO" y se efectúa el chequeo de los periféricos
		 * Si ocurre un error se pasa la caja al estado "NO OPERATIVA"
		 */
		InitCR.estadoAlIniciar = caja.getEstado();
		InitCR.usuarioAlIniciar = caja.getUsuarioLogueado();
		caja.setEstado(INICIALIZANDO);
		Sesion.isTiendaEnLinea();

		// Sincronizamos fecha con el Servidor
		try {
			if(!MaquinaDeEstado.sincronizarFechaHora())
				Sesion.getCaja().setEstado(Sesion.NO_OPERATIVA);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("Sesion()", e);

			MensajesVentanas.mensajeError(e.getMensaje());
		} catch (ConexionExcepcion e) {
			logger.error("Sesion()", e);

			MensajesVentanas.mensajeError(e.getMensaje());
		}
		// Inicializamos el usuario autorizante
		usuarioAutorizante = null;

		// Al iniciar establecemos al usuario del sistema como usuario activo
		setUsuarioActivo(MediadorBD.obtenerUsuarioSistema());	
		
		// Obtenemos los códigos de los tipos de captura
		cargarTiposDeCaptura();
		
		// Obtenemos las condiciones de Venta
		cargarCondicionesDeVenta();
		
		//ACTUALIZACION BECO: 06/01/2009 - Módulo de promociones
		//Obtenemos los productos que estan promocionados en algun combo
		//(new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().cargarPromocionesCombo();

		// Iniciamos sesión de la Caja Registradora 
		caja.setEstado(CONSULTA);
		String cajaEnLinea = Sesion.isCajaEnLinea() == true ? " - En Línea":" - Fuera de Línea";
		SimpleDateFormat fechaActual = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS", new Locale("es", "VE"));
		String fechaActualString = fechaActual.format(Sesion.getFechaSistema());
		Auditoria.registrarAuditoria("Caja "+Sesion.getCaja().getNumero()+" de Tienda "+Sesion.getTienda().getNumero()+" - Iniciada "+fechaActualString+cajaEnLinea,'O');
		
		// Inicializamos la impresora
		LinkedHashMap<String,Object> preferencias = new LinkedHashMap<String,Object>();
		
		try {
			preferencias = InitCR.preferenciasCR.getAllPreferecesForNode("sistema");
			impresoraFiscal = preferencias.get("impresoraFiscal").toString().equalsIgnoreCase("S");
			String manejoGaveta = preferencias.get("manejoGaveta").toString().trim();
			if (manejoGaveta.equals(Sesion.GAVETA_POR_IMPRESORA)) {
				aperturaGavetaPorImpresora = true;
			} else {
				aperturaGavetaPorImpresora = false;
			}
			EPAPreferenceProxy preferencesproxy = InitCR.prefsDispositivos;
			preferencias = preferencesproxy.getAllPreferecesForNode("impresora");
			String puerto = preferencias.get("Puerto Serial").toString();
			String baudRate = preferencias.get("Baud Rate").toString();
			String dataBits = preferencias.get("Data Bits").toString();
			String fci = preferencias.get("Flow Control In").toString();
			String fco = preferencias.get("Flow Control Out").toString();
			String parity = preferencias.get("Parity").toString();
			String stopBits = preferencias.get("Stop Bits").toString();

			if(impresoraFiscal) {
				crFiscalPrinterOperations = new CajaFiscalDriver(puerto, baudRate, fci, fco, dataBits, stopBits, parity);
				crFiscalPrinterOperations.addFiscalPrinterListener(this);
				crFiscalPrinterOperations.setPrinterConfig();
				crFiscalPrinterOperations.abrirPuertoImpresora();
				crFiscalPrinterOperations.resetPrinter();
				crFiscalPrinterOperations.cerrarPuertoImpresora();
			} else {
				crPrinterOperations = new CRPrinterOperations(puerto, baudRate, fci, fco, dataBits, stopBits, parity);
				crPrinterOperations.setPrinterConfig();
				crPrinterOperations.initializarPrinter();
			}
		} catch (NoSuchNodeException e) {
			logger.error("Sesion()", e);

			Auditoria.registrarAuditoria("Falla carga de preferencias de para Datos de Impresora.", 'E');
		} catch (UnidentifiedPreferenceException e) {
			logger.error("Sesion()", e);

			Auditoria.registrarAuditoria("Falla carga de las preferencias para Datos de Impresora.", 'E');
		}

	/*	Date fechaCierreCaja = Sesion.getCaja().getFechaUltRepZ();
		java.sql.Date fechaTienda = new java.sql.Date(Sesion.getFechaSistema().getTime());
		java.sql.Date ultFechaTransaccion = BaseDeDatosVenta.getUltimaTransaccion(true, false);
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

		// Observamos que no existan transacciones anteriores después del último RepZ y antes de la fecha actual
		if((ultFechaTransaccion != null)&&(!formatoFecha.format(ultFechaTransaccion).equalsIgnoreCase(formatoFecha.format(fechaTienda)))&&(fechaCierreCaja==null||ultFechaTransaccion.after(fechaCierreCaja))){
			try {
				if (fechaCierreCaja!=null)
					MensajesVentanas.aviso("Debe realizar cierre de caja antes de iniciar facturación. \nUltimo cierre: "+formatoFecha.format(fechaCierreCaja));
				else {
					MensajesVentanas.aviso("Debe realizar cierre de caja antes de iniciar facturación.");
					Calendar fechaBaseZ = Calendar.getInstance();
					fechaBaseZ.set(1970,1,1);
					Sesion.getCaja().setFechaUltRepZ(fechaBaseZ.getTime());
				}
				MaquinaDeEstado.realizarCierreCaja(true);
			} catch (UsuarioExcepcion e) {
				logger.error("Sesion()", e);

				MensajesVentanas.mensajeError(e.getMensaje());
			} catch (MaquinaDeEstadoExcepcion e) {
				logger.error("Sesion()", e);

				MensajesVentanas.mensajeError(e.getMensaje());
			} catch (ExcepcionCr e) {
				logger.error("Sesion()", e);

				MensajesVentanas.mensajeError(e.getMensaje());
			}
		} else if (impresoraFiscal) {
			// Si es una impresora fiscal, debe chequarse si necesita un z
			crFiscalPrinterOperations.abrirPuertoImpresora();
			if (crFiscalPrinterOperations.isRequiereZ()) {
				MensajesVentanas.aviso("La impresora fiscal requiere un Z. \nSe emitirá a continuación");
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -1);
				Sesion.getCaja().setFechaUltRepZ(cal.getTime());
				MaquinaDeEstado.realizarZImpresora();
			}
			crFiscalPrinterOperations.cerrarPuertoImpresora();
		}
		
	*/
		
		// Cargamos las preferencias de facturacion
		try {
			preferencias = InitCR.preferenciasCR.getAllPreferecesForNode("facturacion");
			Sesion.requiereCliente = String.valueOf(preferencias.get("requiereCliente")).trim().toUpperCase().equals("S") ? true:false;
			Sesion.autorizarCierreCajero = String.valueOf(preferencias.get("autorizarCierreCajero")).trim().toUpperCase().equals("S") ? true:false;
			Sesion.longitudCodigoInterno = new Integer(preferencias.get("longitudCodigoInterno").toString()).intValue();
			Sesion.permitirRebajasAempleados = (preferencias.get("permitirRebajasAempleados").toString().toCharArray()[0] == Sesion.SI) ? true : false;
			Sesion.desctoEmpaqueAcumulativo = (preferencias.get("empaquesAcumulativos").toString().toCharArray()[0] == Sesion.SI) ? true : false;
			Sesion.porcentajeMensajeEmpaque = new Double(preferencias.get("porcentajeMensajeEmpaque").toString()).doubleValue();
			Sesion.numeroCuentaCheque = preferencias.get("numeroCuentaCheque").toString();
			Sesion.tipoCuentaCheque = preferencias.get("tipoCuentaCheque").toString();
			Sesion.nombreBancoCheque = preferencias.get("nombreBancoCheque").toString();
			Sesion.porcentajeRetencionIVA = new Double(preferencias.get("porcentajeRetencionIVA").toString()).doubleValue();
			Sesion.colorCombo = preferencias.get("colorCombo").toString();
			Sesion.entregaCteRetira= (preferencias.get("entregaClienteRetira").toString().toCharArray()[0] == Sesion.SI) ? true : false;
			Sesion.entregaDespacho= (preferencias.get("entregaDespacho").toString().toCharArray()[0] == Sesion.SI) ? true : false;
			Sesion.entregaDomicilio= (preferencias.get("entregaDomicilio").toString().toCharArray()[0] == Sesion.SI) ? true : false;
			Sesion.montoMinimoDevolucion = new Float(preferencias.get("montoMinimoDevolucion").toString()).floatValue();
			
			//BECO 11-2012 Carga de parametros para Transferencia inmediata y PDA 
			Sesion.tiempoTimeOut = (!preferencias.get("timeOutPDA").equals("")) ? Integer.parseInt(preferencias.get("timeOutPDA").toString()):5000;
			Sesion.ipServidorPDA = (!preferencias.get("ipServidorPDA").equals("")) ? preferencias.get("ipServidorPDA").toString():"";
			Sesion.puertoServidorPDA = (!preferencias.get("puertoServidorPDA").equals("")) ? Integer.parseInt(preferencias.get("puertoServidorPDA").toString()):35993;
			//FIN
			
		} catch (NoSuchNodeException e) {
			logger.error("Sesion()", e);

			Auditoria.registrarAuditoria("Falla carga de preferencias de para Datos de Impresora.", 'E');
		} catch (UnidentifiedPreferenceException e) {
			logger.error("Sesion()", e);

			Auditoria.registrarAuditoria("Falla carga de las preferencias para Datos de Impresora.", 'E');
		} catch (Exception e) {
			logger.error("Sesion()", e);
		}
		
		
		setTiendaEnLinea(Sesion.isTiendaEnLinea());
		try {
			setVerificarLinea(InitCR.preferenciasCR.getConfigStringForParameter("sistema", "verificarLinea").equals("S") ? true:false);
		} catch (NoSuchNodeException e) {
			logger.error("Sesion()", e);

			Auditoria.registrarAuditoria("Parámetro de verificación de conexión no establecido", 'E');
			setVerificarLinea(true);
		} catch (UnidentifiedPreferenceException e) {
			logger.error("Sesion()", e);
			setVerificarLinea(true);
		}
		
		//Cargar los datos para dirección de clientes
		cargarEstados();
		
		//Cargar los tipos de eventos disponibles para una Lista de Regalos
		cargarTipoEventosLR();
	}
	
	public static void verificarReporteZ() throws ConexionExcepcion, ExcepcionCr, PrinterNotConnectedException 
	{
		while (CR.meVenta.getTransaccionPorImprimir()!=null || 
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
		Date fechaCierreCaja = getCaja().getFechaUltRepZ();
		java.sql.Date fechaTienda = new java.sql.Date(Sesion.getFechaSistema().getTime());
		java.sql.Date ultFechaTransaccion;
		try {
			ultFechaTransaccion = BaseDeDatosVenta.getUltimaTransaccion(true, false);
			SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

		// Observamos que no existan transacciones anteriores después del último RepZ y antes de la fecha actual
		if((ultFechaTransaccion != null)&&(!formatoFecha.format(ultFechaTransaccion).equalsIgnoreCase(formatoFecha.format(fechaTienda)))&&(fechaCierreCaja==null||ultFechaTransaccion.after(fechaCierreCaja))){
			try {
				if (fechaCierreCaja!=null)
					MensajesVentanas.aviso("Debe realizar cierre de caja antes de iniciar facturación. \nUltimo cierre: "+formatoFecha.format(fechaCierreCaja));
				else {
					MensajesVentanas.aviso("Debe realizar cierre de caja antes de iniciar facturación.");
					Calendar fechaBaseZ = Calendar.getInstance();
					fechaBaseZ.set(1970,1,1);
					Sesion.getCaja().setFechaUltRepZ(fechaBaseZ.getTime());
				}
				MaquinaDeEstado.realizarCierreCaja(true);
				
			} catch (UsuarioExcepcion e) {
				logger.error("Sesion()", e);

				//MensajesVentanas.mensajeError(e.getMensaje());
				throw (e);
			} catch (MaquinaDeEstadoExcepcion e) {
				logger.error("Sesion()", e);

				//MensajesVentanas.mensajeError(e.getMensaje());
				throw (e);
			} catch (ExcepcionCr e) {
				logger.error("Sesion()", e);

				//MensajesVentanas.mensajeError(e.getMensaje());
				throw (e);
			}
		} else if (impresoraFiscal) {
			// Si es una impresora fiscal, debe chequarse si necesita un z
			//crFiscalPrinterOperations.abrirPuertoImpresora();
			if (crFiscalPrinterOperations.isRequiereZ()) {
				MensajesVentanas.aviso("La impresora fiscal requiere un Z. \nSe emitirá a continuación");
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -1);
				MaquinaDeEstado.realizarZImpresora();
				Sesion.getCaja().setFechaUltRepZ(cal.getTime());
			}
			//crFiscalPrinterOperations.cerrarPuertoImpresora();
		}
		} catch (BaseDeDatosExcepcion e1) {
			e1.printStackTrace();
			throw(e1);
		}
		catch (ConexionExcepcion e2) {
			e2.printStackTrace();
			throw(e2);
		}
		catch (ExcepcionCr e2) {
			e2.printStackTrace();
			throw(e2);
		}
		catch (PrinterNotConnectedException e2) {
			e2.printStackTrace();
			throw(e2);
		}
		
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private static void cargarDatosTienda() throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarDatosTienda() - start");
		}

		ResultSet datosTienda = MediadorBD.obtenerDatosTienda(true);
		try {
			if (datosTienda.first()) {
				// Parametros de descuento a empleados y uso de vendedores
				indicaDesctoEmpleado = (datosTienda.getString("indicadesctoempleado").toCharArray()[0] == SI) ? true : false;
				desctoVentaEmpleado = datosTienda.getDouble("desctoventaempleado");
				utilizarVendedor = (datosTienda.getString("utilizarvendedor").toCharArray()[0] == SI) ? true : false;

				// Parámetro que indica si se puede cambiar o no precios en caja
				cambiarPrecio = (datosTienda.getString("cambioprecioencaja").toCharArray()[0] == SI) ? true : false; 

				// Variable que indica si los descuentos son acumulativos
				desctosAcumulativos = (datosTienda.getString("desctosacumulativos").toCharArray()[0] == SI) ? true : false;
			
				// Obtenemos las lineas de publicidad de la tienda
				ResultSet pubTienda = MediadorBD.obtenerPublicidadTienda();
				pubTienda.beforeFirst();
				Vector<String> publicidad = new Vector<String>();
				while (pubTienda.next()) {
					publicidad.addElement(pubTienda.getString("publicidadlinea"));
				}
				pubTienda.close();
				pubTienda = null;
				// Instanciamos la tienda para la sesión
				tienda = new Tienda(datosTienda.getInt("numtienda"), datosTienda.getString("nombresucursal"),
									datosTienda.getString("razonsocial"), datosTienda.getString("rif"),
									datosTienda.getString("nit"), datosTienda.getString("direccion"),
									datosTienda.getString("codarea"), datosTienda.getString("numtelefono"),
									datosTienda.getString("codareafax"), datosTienda.getString("numfax"),
									datosTienda.getString("direccionfiscal"), datosTienda.getString("codareafiscal"),
									datosTienda.getString("numtelefonofiscal"), datosTienda.getString("codareafaxfiscal"),
									datosTienda.getString("numfaxfiscal"), publicidad, datosTienda.getString("monedabase"),
									datosTienda.getString("codregion"), datosTienda.getString("descripcion"),
									datosTienda.getString("codregion"), datosTienda.getDouble("limiteentregacaja"),
									datosTienda.getDate("fechatienda"));
			} else {
				throw (new BaseDeDatosExcepcion("No se pudo cargar la Sesión. No se encontró registro Tienda en la Base de Datos"));
			}
		} catch (SQLException ex) {
			logger.error("cargarDatosTienda()", ex);

			throw (new BaseDeDatosExcepcion("Falla en la conexión con la Base de Datos al acceder datos de Tienda en: " + Sesion.getDbUrlLocal(), ex));
		} finally {
			try {
				datosTienda.close();
			} catch (SQLException e) {
				logger.error("cargarDatosTienda()", e);
			}
			datosTienda = null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cargarDatosTienda() - end");
		}
	}

	/**
	 * Método cargarTiposDeCaptura.
	 * 		Carga los tipos de captura de la Base de Datos.
	 */
	public static void cargarTiposDeCaptura() throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarTiposDeCaptura() - start");
		}

		// Obtenemos los códigos de los tipos de captura
		ResultSet codigosTipoCaptura = MediadorBD.obtenerCodCaptura();
		try {
			codigosTipoCaptura.beforeFirst();
			while (codigosTipoCaptura.next()) {
				if (codigosTipoCaptura.getString("descripcion").toUpperCase().equals("MIXTO")) {
					capturaMixto = codigosTipoCaptura.getString("codtipocaptura");
				} else if (codigosTipoCaptura.getString("descripcion").toUpperCase().equals("ESCANER")) {
					capturaEscaner = codigosTipoCaptura.getString("codtipocaptura");
				} else if (codigosTipoCaptura.getString("descripcion").toUpperCase().equals("TECLADO")) {
					capturaTeclado = codigosTipoCaptura.getString("codtipocaptura");
				} else if (codigosTipoCaptura.getString("descripcion").toUpperCase().equals("PROCESO")) {
					capturaProceso = codigosTipoCaptura.getString("codtipocaptura");
				}
			}
		} catch (SQLException ex) {
			logger.error("cargarTiposDeCaptura()", ex);

			throw (new BaseDeDatosExcepcion("Falla en la conexión con la Base de Datos al acceder datos de Tipos de Captura en: " + Sesion.getDbUrlLocal(), ex));
		} finally {
			try {
				codigosTipoCaptura.close();
			} catch (SQLException e) {
				logger.error("cargarTiposDeCaptura()", e);
			}
			codigosTipoCaptura = null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cargarTiposDeCaptura() - end");
		}
	}
	
	private static void cargarDatosCaja() throws ConexionExcepcion, BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarDatosCaja() - start");
		}		
		ResultSet datosCaja = MediadorBD.obtenerDatosCaja(true);
		try {
			Date fechaReporteZ = datosCaja.getDate("fechareportez");
			if (datosCaja.first()) {
				//Comprobacion fecha null agregada durante la migracion de java y mysql
				//por jperez
				if(fechaReporteZ==null){
					Calendar fechaBaseZ = Calendar.getInstance();
					fechaBaseZ.set(1970,1,1);
					fechaReporteZ = fechaBaseZ.getTime();
				}
				// Instanciamos la caja para la sesión
				caja = new Caja(datosCaja.getInt("numcaja"), datosCaja.getString("idestadocaja"),
									datosCaja.getString("descripcion"), datosCaja.getString("numserial"),
									datosCaja.getInt("numtransaccion"),	datosCaja.getInt("numregistro"),
									datosCaja.getInt("numseqmerchant"),fechaReporteZ);
				caja.setMontoRecaudado(datosCaja.getDouble("montorecaudado"));
				nivelAuditoria = datosCaja.getInt("nivelauditoria");
				caja.setNumTransaccionBR(datosCaja.getInt("numtransaccionbr"));
			} else {
				throw (new BaseDeDatosExcepcion("No se pudo cargar la Sesión. No se encontró registro Caja en la Base de Datos"));
			}
		} catch (SQLException ex) {
			logger.error("cargarDatosCaja()", ex);

			throw (new BaseDeDatosExcepcion("Falla en la conexión con la Base de Datos al acceder datos de Caja en: " + Sesion.getDbUrlLocal(), ex));
		} finally {
			try {
				datosCaja.close();
			} catch (SQLException e) {
				logger.error("cargarDatosCaja()", e);
			}
			datosCaja = null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cargarDatosCaja() - end");
		}
	}
			
	/**
	 * Método cargarCondicionesDeVenta.
	 * 		Carga las condiciones de venta de la Base de Datos.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static void cargarCondicionesDeVenta() throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarCondicionesDeVenta() - start");
		}

		// Obtenemos las condiciones de Venta
		ResultSet condicionesDeVenta = MediadorBD.obtenerCondVenta();
		try {
			condicionesDeVenta.beforeFirst();
			while (condicionesDeVenta.next()) {
				if (condicionesDeVenta.getString("descripcion").equals("NORMAL")) {
					condicionNormal = condicionesDeVenta.getString("CODCONDICIONVENTA");
				} else if (condicionesDeVenta.getString("descripcion").equals("EMPAQUE")) {
					condicionEmpaque = condicionesDeVenta.getString("CODCONDICIONVENTA");
				} else if (condicionesDeVenta.getString("descripcion").equals("PROMOCION")) {
					condicionPromocion = condicionesDeVenta.getString("CODCONDICIONVENTA");
				} else if (condicionesDeVenta.getString("descripcion").equals("DESCTOEMPLEADO")) {
					condicionDesctoEmpleado = condicionesDeVenta.getString("CODCONDICIONVENTA");
				} else if (condicionesDeVenta.getString("descripcion").equals("CAMBIODEPRECIO")) {
					condicionCambioPrecio = condicionesDeVenta.getString("CODCONDICIONVENTA");
				} else if (condicionesDeVenta.getString("descripcion").equals("DESCTOPORDEFECTO")) {
					condicionDesctoPorDefecto = condicionesDeVenta.getString("CODCONDICIONVENTA");
				} else if (condicionesDeVenta.getString("descripcion").equals("DESCTOPRECIOGARANTIZADO")) {
					condicionDesctoPrecioGarantizado = condicionesDeVenta.getString("CODCONDICIONVENTA");
				} else if (condicionesDeVenta.getString("descripcion").equals("DESCTOEMP/PROM")) {
					condicionDesctoEmpProm = condicionesDeVenta.getString("CODCONDICIONVENTA");
				} else if (condicionesDeVenta.getString("descripcion").equals("DESCTOEMP/EMPAQUE")) {
					condicionDesctoEmpEmpaque = condicionesDeVenta.getString("CODCONDICIONVENTA");
				} else if (condicionesDeVenta.getString("descripcion").equals("DESCTOEMP/CAMBIOPRECIO")) {
					condicionDesctoEmpCambPrec = condicionesDeVenta.getString("CODCONDICIONVENTA");
				} else if (condicionesDeVenta.getString("descripcion").equals("DESCTOEMP/DESCTOPORDEFECTO")) {
					condicionDesctoEmpDesctoDefect = condicionesDeVenta.getString("CODCONDICIONVENTA");
				} else if (condicionesDeVenta.getString("descripcion").equals("DESCTOEMP/DESCTOPRECGARANT")) {
					condicionDesctoEmpPrecioGarantizado = condicionesDeVenta.getString("CODCONDICIONVENTA");
				} else if (condicionesDeVenta.getString("descripcion").equals("EMPAQUE/PROMOCION")) {
					condicionEmpaquePromocion = condicionesDeVenta.getString("CODCONDICIONVENTA");
				} else if (condicionesDeVenta.getString("descripcion").equals("EMPAQUE/PROMO/DESCTOEMP")) {
					condicionEmpaquePromocionEmpleado = condicionesDeVenta.getString("CODCONDICIONVENTA");
				} else if (condicionesDeVenta.getString("descripcion").equals("VENTAPORVOLUMEN")) {
					condicionVentaPorVolumen = condicionesDeVenta.getString("CODCONDICIONVENTA");
				} 
				// Condiciones para promociones
				  else if (condicionesDeVenta.getString("descripcion").equals("DESCUENTOCORPORATIVO")) {
					condicionDescuentoCorporativo = condicionesDeVenta.getString("CODCONDICIONVENTA");
				} else if (condicionesDeVenta.getString("descripcion").equals("DESCUENTOENCOMBO")) {
					condicionDescuentoEnCombo = condicionesDeVenta.getString("CODCONDICIONVENTA");
				} else if (condicionesDeVenta.getString("descripcion").equals("PROMOCIONMIXTA")) {
					condicionMixta = condicionesDeVenta.getString("CODCONDICIONVENTA");
				} else if (condicionesDeVenta.getString("descripcion").equals("PRODUCTOGRATIS")) {
					condicionProductoGratis = condicionesDeVenta.getString("CODCONDICIONVENTA");
				} else if (condicionesDeVenta.getString("descripcion").equals("COMBOCANTIDADES")) {
					condicionComboPorCantidades = condicionesDeVenta.getString("CODCONDICIONVENTA");
				} else if (condicionesDeVenta.getString("descripcion").equals("DESCUENTOENPRODUCTO")) {
					condicionDescuentoEnProductos = condicionesDeVenta.getString("CODCONDICIONVENTA");
				}else if (condicionesDeVenta.getString("descripcion").equals("DESCONTADOPORCOMBO")) {
					condicionDescontadoPorCombo = condicionesDeVenta.getString("CODCONDICIONVENTA");
				}
			}
			condicionesCombo.addElement(condicionDescuentoEnCombo);
			condicionesCombo.addElement(condicionComboPorCantidades);
			
			condicionesComboPrecioFinal.addElement(condicionDescuentoEnCombo);
			
			condicionesEspeciales.addElement(condicionDesctoPrecioGarantizado);
			condicionesEspeciales.addElement(condicionDesctoPorDefecto);
			condicionesEspeciales.addElement(condicionCambioPrecio);
			
			condicionesNuevas = new Vector<String>();
			condicionesNuevas.addAll(Sesion.condicionesCombo);
			condicionesNuevas.addElement(Sesion.condicionProductoGratis);
			condicionesNuevas.addElement(Sesion.condicionDescuentoCorporativo);
			condicionesNuevas.addElement(Sesion.condicionDescuentoEnProductos);
			condicionesNuevas.addElement(Sesion.condicionComboPorCantidades);
			//No agrego la condicion gratisporcombo porque no debe ser tomada en cuenta
			
			
		} catch (SQLException ex) {
			logger.error("cargarCondicionesDeVenta()", ex);

			throw (new BaseDeDatosExcepcion("Falla en la conexion con la Base de Datos al acceder datos de Condiciones de Venta en: " + Sesion.getDbUrlLocal(), ex));
		} finally {
			try {
				condicionesDeVenta.close();
			} catch (SQLException e) {
				logger.error("cargarCondicionesDeVenta()", e);
			}
			condicionesDeVenta = null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cargarCondicionesDeVenta() - end");
		}
	}

	/**
	 * Método iniciarDatos.
	 * 		Inicializa los parámetros (clase, url, usuario, clave) por 
	 * defecto para conexión a la base de datos de la caja registradora 
	 * y del servidor.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'LinkedHashMap'
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unchecked")
	public static void iniciarDatos() {
		if (logger.isDebugEnabled()) {
			logger.debug("iniciarDatos() - start");
		}

		LinkedHashMap<String,Object> preferencias = new LinkedHashMap<String,Object>();
		
		try {
			preferencias = InitCR.preferenciasCR.getAllPreferecesForNode("db");
			Sesion.setDbEsquema(preferencias.get("dbEsquema").toString());
			Sesion.setDbClaseLocal(preferencias.get("dbClaseLocal").toString());
			Sesion.setDbClaseServidor(preferencias.get("dbClaseServidor").toString());
			Sesion.setDbClaseServidorCentral(preferencias.get("dbClaseServidorCentral").toString());
			Sesion.setDbUrlLocal(preferencias.get("dbUrlLocal").toString());
			Sesion.setDbUrlServidor(preferencias.get("dbUrlServidor").toString());
			Sesion.setDbUrlServidorCentral(preferencias.get("dbUrlServidorCentral").toString());
			Sesion.setDbUsuario(preferencias.get("dbUsuario").toString());
			Sesion.setDbClave(preferencias.get("dbClave").toString());

			try {
				Sesion.setRutaObjetoDevolucion(preferencias.get("objetoRMI").toString());
			} catch (Exception e) {
				Sesion.setRutaObjetoDevolucion("//192.168.1.3/ServicioRMI/ObjetoDevolucion");
			}


			preferencias = InitCR.preferenciasCR.getAllPreferecesForNode("facturacion");
			Sesion.setNumCajaPref(Integer.parseInt(preferencias.get("numeroCaja").toString()));
		} catch (NoSuchNodeException e) {
			logger.error("iniciarDatos()", e);

			Auditoria.registrarAuditoria("Falla carga de preferencias de BD.", 'E');
		} catch (UnidentifiedPreferenceException e) {
			logger.error("iniciarDatos()", e);

			Auditoria.registrarAuditoria("Falla carga de las preferencias.", 'E');
		}

		if (logger.isDebugEnabled()) {
			logger.debug("iniciarDatos() - end");
		}
	}

	
	/**
	 * Devuelve un indicador sobre el modo de operación de la caja iniciada.
	 * @return boolean - Verdadero si hay conexión con el servidor, false en
	 * 		caso contrario
	 */
	public static boolean isCajaEnLinea() {
		if (logger.isDebugEnabled()) {
			logger.debug("isCajaEnLinea() - start");
		}

		Sesion.chequearLineaCaja();
		
		if (logger.isDebugEnabled()) {
			logger.debug("isCajaEnLinea() - end");
		}
		return cajaEnLinea;
	}
	
	/**
	 * Devuelve un indicador sobre la conexión de la caja con el ServCentral
	 * @return boolean - Verdadero si hay conexión con el servidor central, false en
	 * 		caso contrario
	 */
	public static boolean isServCentralEnLinea() {
		if (logger.isDebugEnabled()) {
			logger.debug("isServCentralEnLinea() - start");
		}

		Sesion.chequearLineaServCentral();
		
		if (logger.isDebugEnabled()) {
			logger.debug("isServCentralEnLinea() - end");
		}
		return servCentralEnLinea;
	}

	/**
	 * Devuelve un indicador sobre el modo de operación de la tienda con respecto a la 
	 * oficina cetnral.
	 * @return boolean - Verdadero si hay conexión con el servidor central, false en
	 * 		caso contrario
	 */
	public static boolean isTiendaEnLinea() {
		if (logger.isDebugEnabled()) {
			logger.debug("isTiendaEnLinea() - start");
		}

		String urlServidorCentral, ipServidorCentral;
		tiendaEnLinea = false;
		if(Sesion.isVerificarLinea()){
			try {
				urlServidorCentral =
					InitCR.preferenciasCR.getConfigStringForParameter(
					"db",
					"dbUrlServidorCentral");
				ipServidorCentral = new String(urlServidorCentral.substring(urlServidorCentral.lastIndexOf("//")+2, urlServidorCentral.lastIndexOf("/")));
				int portServCentral = Integer.parseInt(InitCR.preferenciasCR.getConfigStringForParameter("sistema", "puertoServCentral"));
				setTiendaEnLinea(MediadorBD.isConexion(ipServidorCentral, portServCentral));
			} catch (NoSuchNodeException e) {
				logger.error("isTiendaEnLinea()", e);

				Auditoria.registrarAuditoria("Parámetro de verificación de conexión no establecido", 'E');
			} catch (UnidentifiedPreferenceException e) {
				logger.error("isTiendaEnLinea()", e);
			}
		} else setTiendaEnLinea(tiendaEnLinea);

		if (logger.isDebugEnabled()) {
			logger.debug("isTiendaEnLinea() - end");
		}
		return tiendaEnLinea;
	}

	/**
	 * Devuelve el esquema para conexión con la base de datos.
	 * @return String - Cadena de 100 caracteres
	 */
	public static String getDbEsquema() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDbEsquema() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDbEsquema() - end");
		}
		return dbEsquema;
	}

	/**
	 * Devuelve la clase para conexión con la base de datos local (CR).
	 * @return String - Cadena de 100 caracteres
	 */
	public static String getDbClaseLocal() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDbClaseLocal() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDbClaseLocal() - end");
		}
		return dbClaseLocal;
	}

	/**
	 * Devuelve la clase para conexión con la base de datos remota (Servidor).
	 * @return String - Cadena de 100 caracteres
	 */
	public static String getDbClaseServidor() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDbClaseServidor() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDbClaseServidor() - end");
		}
		return dbClaseServidor;
	}

	/**
	 * Devuelve la clase para conexión con la base de datos centralizada (ServidorCentral).
	 * @return String - Cadena de 100 caracteres
	 */
	public static String getDbClaseServidorCentral() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDbClaseServidorCentral() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDbClaseServidorCentral() - end");
		}
		return dbClaseServidorCentral;
	}

	/**
	 * Devuelve la clave de acceso a la base de datos remota y local.
	 * @return String - Cadena de 100 caracteres
	 */
	public static String getDbClave() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDbClave() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDbClave() - end");
		}
		return dbClave;
	}

	/**
	 * Devuelve la dirección de acceso a la base de datos local.
	 * @return String - Cadena de 100 caracteres
	 */
	public static String getDbUrlLocal() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDbUrlLocal() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDbUrlLocal() - end");
		}
		return dbUrlLocal;
	}

	/**
	 * Devuelve la dirección de acceso a la base de datos remota.
	 * @return String - Cadena de 100 caracteres
	 */
	public static String getDbUrlServidor() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDbUrlServidor() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDbUrlServidor() - end");
		}
		return dbUrlServidor;
	}

	/**
	 * Devuelve la dirección de acceso a la base de datos centralizada.
	 * @return String - Cadena de 100 caracteres
	 */
	public static String getDbUrlServidorCentral() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDbUrlServidorCentral() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDbUrlServidorCentral() - end");
		}
		return dbUrlServidorCentral;
	}

	/**
	 * Devuelve el nombre de usuario para la conexión/acceso a la base de 
	 * datos remota y local.
	 * @return String - Cadena de 30 caracteres
	 */
	public static String getDbUsuario() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDbUsuario() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDbUsuario() - end");
		}
		return dbUsuario;
	}

	/**
	 * Devuelve la hora de ingreso del usuario actualmente iniciado en el
	 * sistema de caja registradora.
	 * @return Time
	 */
	public static Time getHoraIngreso() {
		if (logger.isDebugEnabled()) {
			logger.debug("getHoraIngreso() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getHoraIngreso() - end");
		}
		return horaIngreso;
	}

	/**
	 * Devuelve el nivel de auditoría ("1".."5") con el cual está operando la sesión
	 * actual de la caja.
	 * @return int - Numérico de 1 dígito
	 */
	public static int getNivelAuditoria() {
		if (logger.isDebugEnabled()) {
			logger.debug("getNivelAuditoria() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getNivelAuditoria() - end");
		}
		return nivelAuditoria;
	}

	/**
	 * Devuelve el número de la caja iniciada.
	 * @return short
	 */
	public static short getNumCaja() {
		if (logger.isDebugEnabled()) {
			logger.debug("getNumCaja() - start");
		}

		short returnshort = (short) caja.getNumero();
		if (logger.isDebugEnabled()) {
			logger.debug("getNumCaja() - end");
		}
		return returnshort;
	}

	/**
	 * Devuelve el número de la tienda iniciada.
	 * @return short
	 */
	public static short getNumTienda() {
		if (logger.isDebugEnabled()) {
			logger.debug("getNumTienda() - start");
		}

		short returnshort = (short) tienda.getNumero();
		if (logger.isDebugEnabled()) {
			logger.debug("getNumTienda() - end");
		}
		return returnshort;
	}

	/**
	 * Devuelve un indicador "S"-Si / "N"-No sobre la existencia de 
	 * vendedores en la tienda.
	 * @return boolean - Verdadero si es "S", falso si es "N"
	 */
	public static boolean isUtilizarVendedor() {
		if (logger.isDebugEnabled()) {
			logger.debug("isUtilizarVendedor() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isUtilizarVendedor() - end");
		}
		return utilizarVendedor;
	}

	/**
	 * Establece un indicador sobre el modo de operación de la caja iniciada.
	 * @param enLinea - Verdadero si hay conexión con el servidor, false en
	 * 		caso contrario
	 */
	public static void setCajaEnLinea(boolean enLinea) {
		if (logger.isDebugEnabled()) {
			logger.debug("setCajaEnLinea(boolean) - start");
		}

		Sesion.cajaEnLinea = enLinea;
		if(CR.me != null){
			if(enLinea) CR.me.setLinea(true);
			else CR.me.setLinea(false);
		}
		if (!enLinea && isVerificarLinea()) {
			if (CR.sincronizador != null)
				CR.sincronizador.iniciarVerificarLinea();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setCajaEnLinea(boolean) - end");
		}
	}

	/**
	 * Método setTiendaEnLinea
	 * 
	 * @param enLinea
	 */
	public static void setTiendaEnLinea(boolean enLinea) {
		if (logger.isDebugEnabled()) {
			logger.debug("setTiendaEnLinea(boolean) - start");
		}

		tiendaEnLinea = enLinea;

		if (logger.isDebugEnabled()) {
			logger.debug("setTiendaEnLinea(boolean) - end");
		}
	}

	/**
	 * Establece el esquema para conexión con la base de datos.
	 * @param dbEsquema - Cadena de 100 caracteres máximo
	 */
	public static void setDbEsquema(String dbEsquema) {
		if (logger.isDebugEnabled()) {
			logger.debug("setDbEsquema(String) - start");
		}

		Sesion.dbEsquema = dbEsquema;

		if (logger.isDebugEnabled()) {
			logger.debug("setDbEsquema(String) - end");
		}
	}

	/**
	 * Establece la clase para conexión con la base de datos local (CR).
	 * @param dbClaseCr - Cadena de 100 caracteres máximo
	 */
	public static void setDbClaseLocal(String dbClaseCr) {
		if (logger.isDebugEnabled()) {
			logger.debug("setDbClaseLocal(String) - start");
		}

		Sesion.dbClaseLocal = dbClaseCr;

		if (logger.isDebugEnabled()) {
			logger.debug("setDbClaseLocal(String) - end");
		}
	}

	/**
	 * Establece la clase para conexión con la base de datos remota.
	 * @param dbClaseServidor - Cadena de 100 caracteres máximo
	 */
	public static void setDbClaseServidor(String dbClaseServidor) {
		if (logger.isDebugEnabled()) {
			logger.debug("setDbClaseServidor(String) - start");
		}

		Sesion.dbClaseServidor = dbClaseServidor;

		if (logger.isDebugEnabled()) {
			logger.debug("setDbClaseServidor(String) - end");
		}
	}

	/**
	 * Establece la clase para conexión con la base de datos centralizada.
	 * @param dbClaseServidorCentral - Cadena de 100 caracteres máximo
	 */
	public static void setDbClaseServidorCentral(String dbClaseServidorCentral) {
		if (logger.isDebugEnabled()) {
			logger.debug("setDbClaseServidorCentral(String) - start");
		}

		Sesion.dbClaseServidorCentral = dbClaseServidorCentral;

		if (logger.isDebugEnabled()) {
			logger.debug("setDbClaseServidorCentral(String) - end");
		}
	}

	/**
	 * Establece la clave de acceso a la base de datos remota y local.
	 * @param dbClave - Cadena de 100 caracteres máximo
	 */
	public static void setDbClave(String dbClave) {
		if (logger.isDebugEnabled()) {
			logger.debug("setDbClave(String) - start");
		}

		Sesion.dbClave = dbClave;

		if (logger.isDebugEnabled()) {
			logger.debug("setDbClave(String) - end");
		}
	}

	/**
	 * Establece la dirección de acceso a la base de datos local.
	 * @param dbUrlCr - Cadena de 100 caracteres máximo
	 */
	public static void setDbUrlLocal(String dbUrlCr) {
		if (logger.isDebugEnabled()) {
			logger.debug("setDbUrlLocal(String) - start");
		}
		// La variable prop es utilizada después de la Migración a MYSQL5.5, ya que
		//los INSERT, UPDATE presentaban errores por validaciones de manejador.
		//(Truncar String y Timestamp nulos)
		String prop ="?zeroDateTimeBehavior=convertToNull&jdbcCompliantTruncation=false";
		Sesion.dbUrlLocal = dbUrlCr + prop;

		if (logger.isDebugEnabled()) {
			logger.debug("setDbUrlLocal(String) - end");
		}
	}

	/**
	 * Establece la dirección de acceso a la base de datos remota.
	 * @param dbUrlServidor - Cadena de 100 caracteres máximo
	 */
	public static void setDbUrlServidor(String dbUrlServidor) {
		if (logger.isDebugEnabled()) {
			logger.debug("setDbUrlServidor(String) - start");
		}
		// La variable prop es utilizada después de la Migración a MYSQL5.5, ya que
		//los INSERT, UPDATE presentaban errores por validaciones de manejador.
		//(Truncar String y Timestamp nulos)
		String prop ="?zeroDateTimeBehavior=convertToNull&jdbcCompliantTruncation=false";
		Sesion.dbUrlServidor = dbUrlServidor + prop;

		if (logger.isDebugEnabled()) {
			logger.debug("setDbUrlServidor(String) - end");
		}
	}

	/**
	 * Establece la dirección de acceso a la base de datos remota.
	 * @param dbUrlServidorCentral - Cadena de 100 caracteres máximo
	 */
	public static void setDbUrlServidorCentral(String dbUrlServidorCentral) {
		if (logger.isDebugEnabled()) {
			logger.debug("setDbUrlServidorCentral(String) - start");
		}

		Sesion.dbUrlServidorCentral = dbUrlServidorCentral;

		if (logger.isDebugEnabled()) {
			logger.debug("setDbUrlServidorCentral(String) - end");
		}
	}

	/**
	 * Establece el nombre de usuario para la conexión/acceso a la base de 
	 * datos remota y local.
	 * @param dbUsuario - Cadena de 30 caracteres máximo
	 */
	public static void setDbUsuario(String dbUsuario) {
		if (logger.isDebugEnabled()) {
			logger.debug("setDbUsuario(String) - start");
		}

		Sesion.dbUsuario = dbUsuario;

		if (logger.isDebugEnabled()) {
			logger.debug("setDbUsuario(String) - end");
		}
	}

	/**
	 * Establece la hora de ingreso del usuario actualmente iniciado en el
	 * sistema de caja registradora.
	 * @param horaIngreso - Hora de inicio de ingreso del usuario (HH:MM:SS)
	 */
	public static void setHoraIngreso(Time horaIngreso) {
		if (logger.isDebugEnabled()) {
			logger.debug("setHoraIngreso(Time) - start");
		}

		Sesion.horaIngreso = horaIngreso;

		if (logger.isDebugEnabled()) {
			logger.debug("setHoraIngreso(Time) - end");
		}
	}

	/**
	 * Establece el nivel de auditoría ("1".."5") con el cual está operando la sesión
	 * actual de la caja.
	 * @param nivelAuditoria - Numérico de 1 dígito
	 */
	public static void setNivelAuditoria(int nivelAuditoria) {
		if (logger.isDebugEnabled()) {
			logger.debug("setNivelAuditoria(int) - start");
		}

		Sesion.nivelAuditoria = nivelAuditoria;

		if (logger.isDebugEnabled()) {
			logger.debug("setNivelAuditoria(int) - end");
		}
	}

	/**
	 * Establece el número de la caja iniciada.
	 * @param numCaja
	 */
	public static void setNumCaja(short numCaja) {
		if (logger.isDebugEnabled()) {
			logger.debug("setNumCaja(short) - start");
		}

		Sesion.caja.setNumero((int)numCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("setNumCaja(short) - end");
		}
	}

	/**
	 * Devuelve el número de la tienda iniciada.
	 * @param numTienda
	 */
	public static void setNumTienda(short numTienda) {
		if (logger.isDebugEnabled()) {
			logger.debug("setNumTienda(short) - start");
		}

		Sesion.tienda.setNumero((int)numTienda);

		if (logger.isDebugEnabled()) {
			logger.debug("setNumTienda(short) - end");
		}
	}

	/**
	 * Establece un indicador "S"-Si / "N"-No sobre la existencia de 
	 * vendedores en la tienda.
	 * @param utilizarVendedor - Verdadero si es "S", falso si es "N"
	 */
	public static void setUtilizarVendedor(boolean utilizarVendedor) {
		if (logger.isDebugEnabled()) {
			logger.debug("setUtilizarVendedor(boolean) - start");
		}

		Sesion.utilizarVendedor = utilizarVendedor;

		if (logger.isDebugEnabled()) {
			logger.debug("setUtilizarVendedor(boolean) - end");
		}
	}

	/**
	 * Retorna la informacion de la caja.
	 * @return Caja - La Caja activa en la sesion
	 */
	public static Caja getCaja() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCaja() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getCaja() - end");
		}
		return caja;
	}

	/**
	 * Retorna el porcentaje de descuento a COLABORADOR.
	 * @return double - Porcentaje de descuento a COLABORADOR
	 */
	public static double getDesctoVentaEmpleado() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDesctoVentaEmpleado() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDesctoVentaEmpleado() - end");
		}
		return desctoVentaEmpleado;
	}

	/**
	 * Retorna si se permite descuento a COLABORADOR o no.
	 * @return boolean - Si se permite descuento a COLABORADOR o no
	 */
	public static boolean isIndicaDesctoEmpleado() {
		if (logger.isDebugEnabled()) {
			logger.debug("isIndicaDesctoEmpleado() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isIndicaDesctoEmpleado() - end");
		}
		return indicaDesctoEmpleado;
	}

	/**
	 * Retorna la informacion de la tienda.
	 * @return Tienda - La Tienda activa en la sesion
	 */
	public static Tienda getTienda() {
		if (logger.isDebugEnabled()) {
			logger.debug("getTienda() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getTienda() - end");
		}
		return tienda;
	}

	/**
	 * Retorna la informacion del usuario logeado en la caja (si existe).
	 * @return Usuario - El Usuario activo
	 */
	public static Usuario getUsuarioActivo() {
		if (logger.isDebugEnabled()) {
			logger.debug("getUsuarioActivo() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getUsuarioActivo() - end");
		}
		return usuarioActivo;
	}

	/**
	 * Establece la informacion del usuario logeado en la caja (si existe).
	 * @return Usuario - El Usuario activo
	 */
	public static void setUsuarioActivo(Usuario usuarioActivo) {
		if (logger.isDebugEnabled()) {
			logger.debug("setUsuarioActivo(Usuario) - start");
		}

		Sesion.usuarioActivo = usuarioActivo;
		if(CR.me != null)
			CR.me.setUsuario(usuarioActivo.getNombre());

		if (logger.isDebugEnabled()) {
			logger.debug("setUsuarioActivo(Usuario) - end");
		}
	}

	/**
	 * Obtiene la fecha actual del sistema.
	 * @return Date - Fecha del sistema
	 */
	public static Date getFechaSistema() {
		if (logger.isDebugEnabled()) {
			logger.debug("getFechaSistema() - start");
		}

		Date returnDate = (new Date());
		if (logger.isDebugEnabled()) {
			logger.debug("getFechaSistema() - end");
		}
		return returnDate;
	}
	
	/**
	 * Obtiene la hora actual del sistema
	 * @return Time - Hora del sistema
	 */
	public static Time getHoraSistema() {
		if (logger.isDebugEnabled()) {
			logger.debug("getHoraSistema() - start");
		}

		Time returnTime = (new Time(new Date().getTime()));
		if (logger.isDebugEnabled()) {
			logger.debug("getHoraSistema() - end");
		}
		return returnTime;
	}

	/**
	 * Obtiene el timestamp del sistema.
	 * @return Timestamp - Timestamp del sistema
	 */
	public static Timestamp getTimestampSistema() {
		if (logger.isDebugEnabled()) {
			logger.debug("getTimestampSistema() - start");
		}

		Timestamp returnTimestamp = (new Timestamp(Calendar.getInstance()
				.getTime().getTime()));
		if (logger.isDebugEnabled()) {
			logger.debug("getTimestampSistema() - end");
		}
		return returnTimestamp;
	}	

	/**
	 * Devuelve un indicador "S"-Si / "N"-No sobre la autorización o no 
	 * para cambio de precios por caja.
	 * @return boolean - Verdadero si es "S", falso si es "N"
	 */
	public static boolean isCambiarPrecio() {
		if (logger.isDebugEnabled()) {
			logger.debug("isCambiarPrecio() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isCambiarPrecio() - end");
		}
		return cambiarPrecio;
	}
	
	/**
	 * Devuelve el nombre identificador de la función actual del sistema.
	 * @return short
	 */
	public static String getFuncion() {
		if (logger.isDebugEnabled()) {
			logger.debug("getFuncion() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getFuncion() - end");
		}
		return Sesion.funcion;
	}

	/**
	 * Devuelve el nombre identificador del módulo actual del sistema.
	 * @return short
	 */
	public static String getModulo() {
		if (logger.isDebugEnabled()) {
			logger.debug("getModulo() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getModulo() - end");
		}
		return Sesion.modulo;
	}

	/**
	 * Establece el nombre identificador de la función actual del sistema.
	 * @param codFuncion The Funcion to set
	 */
	@SuppressWarnings("unused")
	private static void setFuncion(String funcion) {
		if (logger.isDebugEnabled()) {
			logger.debug("setFuncion(String) - start");
		}

		Sesion.funcion = funcion;

		if (logger.isDebugEnabled()) {
			logger.debug("setFuncion(String) - end");
		}
	}

	/**
	 * Establece el nombre identificador del módulo actual del sistema.
	 * @param codModulo The Modulo to set
	 */
	private static void setModulo(String modulo) {
		if (logger.isDebugEnabled()) {
			logger.debug("setModulo(String) - start");
		}

		Sesion.modulo = modulo;

		if (logger.isDebugEnabled()) {
			logger.debug("setModulo(String) - end");
		}
	}

	/**
	 * Establece el nombre identificador del método actual del sistema.
	 */
	public static String getMetodo() {
		if (logger.isDebugEnabled()) {
			logger.debug("getMetodo() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getMetodo() - end");
		}
		return metodo;
	}

	/**
	 * Establece el nombre identificador del método actual del sistema.
	 * @param codModulo The Metodo to set
	 */
	private static void setMetodo(String metodo) {
		if (logger.isDebugEnabled()) {
			logger.debug("setMetodo(String) - start");
		}

		Sesion.metodo = metodo;

		if (logger.isDebugEnabled()) {
			logger.debug("setMetodo(String) - end");
		}
	}
	
	/**
	 * Método setUbicacion.
	 * 	Establece el módulo-método que se ejecuta actualmente
	 * @param modulo - Nombre del módulo
	 * @param metodo - Nombre del método
	 */
	public static void setUbicacion(String modulo, String metodo){
		if (logger.isDebugEnabled()) {
			logger.debug("setUbicacion(String, String) - start");
		}

		setModulo(modulo);
		setMetodo(metodo);

		if (logger.isDebugEnabled()) {
			logger.debug("setUbicacion(String, String) - end");
		}
	}

	/**
	 * Método isVerificarLinea
	 * 
	 * @return boolean
	 */
	public static boolean isVerificarLinea() {
		if (logger.isDebugEnabled()) {
			logger.debug("isVerificarLinea() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isVerificarLinea() - end");
		}
		return verificarLinea;
	}

	/**
	 * Método setVerificarLinea
	 * 
	 * @param verificar
	 */
	public static void setVerificarLinea(boolean verificar) {
		if (logger.isDebugEnabled()) {
			logger.debug("setVerificarLinea(boolean) - start");
		}

		verificarLinea = verificar;
		if (!verificar)
			setCajaEnLinea(verificar);
		else
			if (CR.sincronizador != null)
				CR.sincronizador.iniciarVerificarLinea();

		if (logger.isDebugEnabled()) {
			logger.debug("setVerificarLinea(boolean) - end");
		}
	}

	/**
	 * Método getLongitudCodigoInterno
	 * 
	 * @return int
	 */
	public static int getLongitudCodigoInterno() {
		if (logger.isDebugEnabled()) {
			logger.debug("getLongitudCodigoInterno() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getLongitudCodigoInterno() - end");
		}
		return longitudCodigoInterno;
	}

	/**
	 * Método getPorcentajeMensajeEmpaque
	 * 
	 * @return int
	 */
	public static double getPorcentajeMensajeEmpaque() {
		if (logger.isDebugEnabled()) {
			logger.debug("getPorcentajeMensajeEmpaque() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getPorcentajeMensajeEmpaque() - end");
		}
		return porcentajeMensajeEmpaque;
	}

	/**
	 * Método getNumeroCuentaCheque
	 * 
	 * @return String
	 */
	public static String getNumeroCuentaCheque() {
		if (logger.isDebugEnabled()) {
			logger.debug("getNumeroCuentaCheque() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getNumeroCuentaCheque() - end");
		}
		return numeroCuentaCheque;
	}

	/**
	 * Método getTipoCuentaCheque
	 * 
	 * @return String
	 */
	public static String getTipoCuentaCheque() {
		if (logger.isDebugEnabled()) {
			logger.debug("getTipoCuentaCheque() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getTipoCuentaCheque() - end");
		}
		return tipoCuentaCheque;
	}

	/**
	 * Método isRequiereCliente
	 * 
	 * @return boolean
	 */
	public static boolean isRequiereCliente() {
		if (logger.isDebugEnabled()) {
			logger.debug("isRequiereCliente() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isRequiereCliente() - end");
		}
		return requiereCliente;
	}

	/**
	 * Método isAutorizarCierreCajero
	 * 
	 * @return boolean
	 */
	public static boolean isAutorizarCierreCajero() {
		if (logger.isDebugEnabled()) {
			logger.debug("isAutorizarCierreCajero() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isAutorizarCierreCajero() - end");
		}
		return autorizarCierreCajero;
	}
	/**
	 * Método que prepara una sesión para su uso con el Visor de Auditoria
	 */
	public static void prepararSesionAuditoria() throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("prepararSesionAuditoria() - start");
		}

		cargarDatosTienda();

		if (logger.isDebugEnabled()) {
			logger.debug("prepararSesionAuditoria() - end");
		}
	}
	/**
	 * @return
	 */
	public static double getPorcentajeRetencionIVA() {
		if (logger.isDebugEnabled()) {
			logger.debug("getPorcentajeRetencionIVA() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getPorcentajeRetencionIVA() - end");
		}
		return porcentajeRetencionIVA;
	}
	
	public static void chequearLineaCaja() {
		if (logger.isDebugEnabled()) {
			logger.debug("chequearLineaCaja() - start");
		}
		String urlServidor, ipServidor;
		try {
			urlServidor =
				InitCR.preferenciasCR.getConfigStringForParameter(
				"db",
				"dbUrlServidor");
			ipServidor = new String(urlServidor.substring(urlServidor.lastIndexOf("//")+2, urlServidor.lastIndexOf("/")));
			int portServ = Integer.parseInt(InitCR.preferenciasCR.getConfigStringForParameter("sistema", "puertoServ"));
			Sesion.setCajaEnLinea(MediadorBD.isConexion(ipServidor, portServ));
		} catch (NoSuchNodeException e) {
			logger.error("chequearLineaCaja()", e);

			Auditoria.registrarAuditoria("Parámetro de verificación de conexión no establecido", 'E');
		} catch (UnidentifiedPreferenceException e) {
			logger.error("chequearLineaCaja()", e);
		}		

		if (logger.isDebugEnabled()) {
			logger.debug("chequearLineaCaja() - end");
		}
	}
	
	/**
	 * Método agregado por IROJAS (03/07/2009) para eliminar la dependencia de MERCHANT 
	 * con el Serv de Tda. Ahora se pregunta es por este chequear en PuntoAgil.
	 * Valida que se esté en línea con el servidor Central y no con el de Tda.
	 */
	public static void chequearLineaServCentral() {
		if (logger.isDebugEnabled()) {
			logger.debug("chequearLineaServCentral() - start");
		}

		String urlServidorCentral, ipServidorCentral;
		try {
			urlServidorCentral =
				InitCR.preferenciasCR.getConfigStringForParameter(
				"db",
				"dbUrlServidorCentral");
			ipServidorCentral = new String(urlServidorCentral.substring(urlServidorCentral.lastIndexOf("//")+2, urlServidorCentral.lastIndexOf("/")));
			Sesion.setServCentralEnLinea(MediadorBD.isConexionServCentral(ipServidorCentral));
		} catch (NoSuchNodeException e) {
			logger.error("chequearLineaServCentral()", e);

			Auditoria.registrarAuditoria("Parámetro de verificación de conexión no establecido", 'E');
		} catch (UnidentifiedPreferenceException e) {
			logger.error("chequearLineaServCentral()", e);
		}		

		if (logger.isDebugEnabled()) {
			logger.debug("chequearLineaServCentral() - end");
		}
	}
	
	public static String getNombreBancoCheque() {
		if (logger.isDebugEnabled()) {
			logger.debug("getNombreBancoCheque() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getNombreBancoCheque() - end");
		}
		return nombreBancoCheque;
	}
	
	public static void reiniciarRollosImpresora(boolean ticket, boolean audit) {
		if (logger.isDebugEnabled()) {
			logger.debug("reiniciarRollosImpresora(boolean, boolean) - start");
		}

		if (ticket) {
			if (impresoraFiscal) {
				crFiscalPrinterOperations.inicializarContadorTicket();
			} else {
				//TODO: Colocar el metodo relacionado con la impresora no fiscal
			}
		}
		if (audit) {
			if (impresoraFiscal) {
				crFiscalPrinterOperations.inicializarContadorAuditoria();
			} else {
				// TODO: Colocar el método relacionado con la impresora no fiscal
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("reiniciarRollosImpresora(boolean, boolean) - end");
		}
	}
	
	public static void iniciarChequeoDispositivos() {
		new ChequeadorEstatusImpresora();
	}
	
	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.event.FiscalPrinterListener#eventOccured(com.epa.crprinterdriver.event.CRFPEvent)
	 * @param e
	 * @since 20-abr-2005
	 */
	public void eventOccured(CRFPEvent e) {
		
		switch (e.getType()) {
			case CRFPEvent.IMPRESION_OK :
				if(Sesion.imprimiendoReporteZ){
					Sesion.reporteZOK = true;
				} else {
					if (CR.meVenta != null && e.isDocFiscal() && CR.meVenta.getTransaccionPorImprimir() != null) {
						if (e.getNumComprobante() > 0) {
							CR.meVenta.getTransaccionPorImprimir().setNumComprobanteFiscal(e.getNumComprobante());
						} else {
							try {
								int numCF = crFiscalPrinterOperations.proximoNumeroComprobanteFiscal();
								CR.meVenta.getTransaccionPorImprimir().setNumComprobanteFiscal(numCF - 1);
							} catch (PrinterNotConnectedException e2) {
								logger.error("eventOccured(CRFPEvent)", e2);
							}
						}
						CR.meVenta.getTransaccionPorImprimir().commitTransaccion();
					} else if (CR.meServ!=null && e.isDocFiscal() && CR.meServ.getTransaccionPorImprimir()!=null) {
						if (e.getNumComprobante() > 0) {
							CR.meServ.getTransaccionPorImprimir().setNumComprobanteFiscal(e.getNumComprobante());
						} else {
							try {
								int numCF = crFiscalPrinterOperations.proximoNumeroComprobanteFiscal();
								CR.meServ.getTransaccionPorImprimir().setNumComprobanteFiscal(numCF - 1);
							} catch (PrinterNotConnectedException e2) {
								logger.error("eventOccured(CRFPEvent)", e2);
							}
						}
						CR.meServ.getTransaccionPorImprimir().commitTransaccion();
					}
				}
				
				CR.meVenta.setTransaccionPorImprimir(null);
				CR.meServ.setTransaccionPorImprimir(null);	
				CRFiscalPrinterOperations.setImpresionFiscalEnCurso(false);
				MaquinaDeEstadoVenta.setDocumentoNoFiscalPorImprimir(false);
			
				break;
			case CRFPEvent.ERROR_ATENCION_USUARIO :
				MaquinaDeEstadoVenta.errorAtencionUsuario=true;
				boolean falloReporteZ =false;
				if(Sesion.imprimiendoReporteZ){
					Sesion.reporteZOK = false;
					Sesion.imprimiendoReporteZ = false;
					falloReporteZ = true;
				}
				final int[] result = new int[1];
				result[0] = 0;
				try {
					SwingUtilities.invokeAndWait(
					new Runnable(){
						public void run() {
							MensajesVentanas.aviso("Problema al imprimir documento. Chequee la impresora"); 
							MaquinaDeEstadoVenta.errorAtencionUsuario=false;
						}
					});
				} catch (InterruptedException e1) {
					MaquinaDeEstadoVenta.errorAtencionUsuario=false;
					logger.error("eventOccured(CRFPEvent)", e1);
				} catch (InvocationTargetException e1) {
					MaquinaDeEstadoVenta.errorAtencionUsuario=false;
					logger.error("eventOccured(CRFPEvent)", e1);
				}
				if(result[0] == 0) {
					e.setReintentar(true);
					if(falloReporteZ)
						e.setReintentar(false);
				} else {
					//Abortar documento
					if(e.isDocFiscal() && CR.meVenta.getTransaccionPorImprimir() != null){
						CR.meVenta.getTransaccionPorImprimir().rollbackTransaccion();
						
					} 
					e.setReintentar(false);
					CR.meVenta.setTransaccionPorImprimir(null);
					CR.meServ.setTransaccionPorImprimir(null);	
					CRFiscalPrinterOperations.setImpresionFiscalEnCurso(false);
					MaquinaDeEstadoVenta.setDocumentoNoFiscalPorImprimir(false);
					
					if(Sesion.imprimiendoReporteZ){
						Sesion.imprimiendoReporteZ = false;
					}
				}
				//}
				break;
			case CRFPEvent.ERROR_CRITICO :
				MaquinaDeEstadoVenta.errorAtencionUsuario=true;
				if(Sesion.imprimiendoReporteZ){
					Sesion.reporteZOK = false;
					Sesion.imprimiendoReporteZ = false;
				}
				
				if (CR.meVenta != null && e.isDocFiscal() && CR.meVenta.getTransaccionPorImprimir() != null) {
					CR.meVenta.getTransaccionPorImprimir().rollbackTransaccion();
					SwingUtilities.invokeLater(new Runnable(){
						public void run() {
							MensajesVentanas.aviso("Error al imprimir comprobante fiscal. La transacción fue abortada");
							//MaquinaDeEstadoVenta.errorAtencionUsuario=false;
						}
					});
					MaquinaDeEstadoVenta.errorAtencionUsuario=false;
				} else {
					SwingUtilities.invokeLater(new Runnable(){
						public void run() {
							MensajesVentanas.aviso("Error en impresora fiscal. La operación fue abortada");
							//MaquinaDeEstadoVenta.errorAtencionUsuario=false;
						}
					});
					MaquinaDeEstadoVenta.errorAtencionUsuario=false;
				}
				CR.meVenta.setTransaccionPorImprimir(null);
				CR.meServ.setTransaccionPorImprimir(null);	
				CRFiscalPrinterOperations.setImpresionFiscalEnCurso(false);
				MaquinaDeEstadoVenta.setDocumentoNoFiscalPorImprimir(false);
				break;
			case CRFPEvent.FALTA_PAPEL :
				MaquinaDeEstadoVenta.errorAtencionUsuario=true;
				try {
					SwingUtilities.invokeAndWait(new Runnable(){
						public void run() {
							MensajesVentanas.aviso("Falta papel en la impresora.\n Verificar antes de continuar imprimiendo");
							MaquinaDeEstadoVenta.errorAtencionUsuario=false;
						}
					});
				} catch (InterruptedException e1) {
					MaquinaDeEstadoVenta.errorAtencionUsuario=false;
					logger.error("eventOccured(CRFPEvent)", e1);
				} catch (InvocationTargetException e1) {
					MaquinaDeEstadoVenta.errorAtencionUsuario=false;
					logger.error("eventOccured(CRFPEvent)", e1);
				}
				break;
			case CRFPEvent.REQUIERE_Z :
				MaquinaDeEstadoVenta.errorAtencionUsuario=true;
				try {
					SwingUtilities.invokeAndWait(new Runnable(){
						public void run() {
							MensajesVentanas.aviso("La impresora requiere un Z.\nSe procederá a la impresión del mismo");
							MaquinaDeEstadoVenta.errorAtencionUsuario=false;
						}
					});
				} catch (InterruptedException e1) {
					MaquinaDeEstadoVenta.errorAtencionUsuario=false;
					logger.error("eventOccured(CRFPEvent)", e1);
				} catch (InvocationTargetException e1) {
					MaquinaDeEstadoVenta.errorAtencionUsuario=false;
					logger.error("eventOccured(CRFPEvent)", e1);
				}
				break;
		}
	}
	
	/**
	 * 
	 */
	private void sincronizarConfiguracionCaja() throws BaseDeDatosExcepcion, ConexionExcepcion {	
		ResultSet datosCaja = MediadorBD.obtenerDatosCaja(true, Sesion.getNumCajaPref());
		try {
			if (!datosCaja.first()) {
				try {
					datosCaja.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				datosCaja = null;
				
				// No existe registro de Caja. Cargamos la configuración
				// Obtenemos la Tienda desde el Serv. Central
				ResultSet tiendaServ = MediadorBD.obtenerDatosTienda(false);
				
				ResultSet publicidadServ = MediadorBD.obtenerPublicidadTienda(false);
				ResultSet cajaServ = MediadorBD.obtenerDatosCaja(false, Sesion.getNumCajaPref());
				try {
					if (tiendaServ.first()&&(cajaServ.first())) {
						BeansSincronizador.cargarEntidadesConfiguracion();
						MediadorBD.registrarTienda(tiendaServ, publicidadServ);
						MediadorBD.registrarCaja(cajaServ);
					} else {
						throw (new BaseDeDatosExcepcion("No se Pudo configurar El Sistema. Falla en conexión con BD"));
					}
				} finally {
					try {
						tiendaServ.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					tienda = null;
				}
			}
		} catch (SQLException ex) {
			throw (new BaseDeDatosExcepcion("No se Pudo configurar El Sistema. Falla en conexión con BD"));
		} finally {
			try {
				datosCaja.close();
			} catch (Exception e) {
				//e.printStackTrace();
			}
			datosCaja = null;
		}
	}

	/**
	 * Método getNumCajaPref
	 * 
	 * @return
	 * int
	 */
	public static int getNumCajaPref() {
		return numCajaPref;
	}

	/**
	 * Método setNumCajaPref
	 * 
	 * @param i
	 * void
	 */
	public static void setNumCajaPref(int i) {
		numCajaPref = i;
	}

	/**
	 * Método cargarUbicación
	 * 
	 * 	Permite cargar información referente a estados, ciudades y urbanizaciones para registro de clientes
	 * 
	 * 
	 * */


	public static void cargarEstados ()
	{
		estados = MediadorBD.buscarEstados();
	}

	public static void cargarCiudades (int estado)
	{
		ciudades = MediadorBD.buscarCiudades(estado);
	}

	public static void cargarUrbanizaciones (int estado, int ciudad)
	{
		urbanizaciones = MediadorBD.buscarUrbanizaciones(estado,ciudad);
	}

	public static void cargarTipoEventosLR() {
		tipoEventosLR = MediadorBD.cargarTipoEventosLR();
	}
	/**
	 * @return
	 */
	public static String getRutaObjetoDevolucion() {
		return rutaObjetoDevolucion;
	}

	/**
	 * @param string
	 */
	public static void setRutaObjetoDevolucion(String string) {
		rutaObjetoDevolucion = string;
	}
	
	public static float getMontoMinimoDevolucion(){
		return montoMinimoDevolucion;
	}

	public static void setServCentralEnLinea(boolean servCentralEnLinea) {
		Sesion.servCentralEnLinea = servCentralEnLinea;
	}
	
	// Agregado 28-02-2012 para el envio de correos de notificación 
	// jperez
	public static String getSmtpHost() {
		return smtpHost;
	}
	public static void setSmtpHost(String smtpHost) {
		Sesion.smtpHost = smtpHost;
	}
	public static int getSmtpPort() {
		return smtpPort;
	}
	public static void setSmtpPort(int smtpPort) {
		Sesion.smtpPort = smtpPort;
	}
	
	public static String getPasswordRemitente(){
		return passwordRemitente;
	}
	
	public static void setPasswordRemitente(String passwordRemitente){
		Sesion.passwordRemitente = passwordRemitente;
	}
	
	public static String getMailRemitente(){
		return mailRemitente;
	}
	
	public static void setMailRemitente(String mailRemitente){
		Sesion.mailRemitente = mailRemitente;
	}


}

/**
 *  Genera un hilo (Thread) que se encarga de chequear el estatus de la impresora.
 */
class ChequeadorEstatusImpresora implements Runnable {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ChequeadorEstatusImpresora.class);
	
	private Thread thread;
	
	public ChequeadorEstatusImpresora () {
		thread = new Thread(this, "Chequeo Impresora");
		thread.start();
	}

	/* (no Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {

		while (true) {
			try {
				if (Sesion.impresoraFiscal) {
//					if (Sesion.crFiscalPrinterOperations.estadoDSR()) {
						CR.me.setPrinter(true);
//					} else {
//						CR.me.setPrinter(false);
//					}
				} else {
					if (Sesion.crPrinterOperations.estadoDSR()) {
						CR.me.setPrinter(true);
					} else {
						CR.me.setPrinter(false);
					}
				}
			} catch (Exception e) {
				//logger.error("run()", e);

				try {
					CR.me.setPrinter(false);	
				} catch (Exception e1) {
					//logger.error("run()", e1);
				}
			}
			
			//Chequeo de Escanner
			try {
				boolean val = false;
				if (CR.getConexionEscaner() != null) 
					val = CR.getConexionEscaner().testDSR();
				CR.me.setEscaner(val);
			} catch (Exception e) {
				//logger.error("run()", e);

				try {
					CR.me.setEscaner(false);
				} catch (Exception e1) {
					//logger.error("run()", e1);
				}
			}

			//Chequeo de Visor
			try {
				CR.me.setVisor(CR.crVisor.estadoDSR());
			} catch (Exception e) {
				//logger.error("run()", e);

				try {
					CR.me.setVisor(false);				
				}catch (Exception e1) {
					//logger.error("run()", e1);
				}
			}

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				logger.error("run()", e);
			}
		}
	}	
}
