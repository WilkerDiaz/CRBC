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
 * Versión     : 
 * Fecha       : 
 * Analista    : 
 * Descripción : 
 * =============================================================================
 */
package com.becoblohm.cr.manejadorsesion;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.mediadoresbd.MediadorBD;


/** 
 * Descripción: 
 * 		Esta clase carga los parámetros y variables necesarias para el 
 * correcto funcionamiento del sistema. También proporciona métodos que 
 * permiten el control de cada una de sus propiedades.
 */
public class Sesion {
	
	// Parámetros de conexión con la base de datos
	private static String dbEsquema = "CR";
	//private static String dbClaseLocal = "com.mysql.jdbc.Driver";
	private static String dbClaseLocal = "com.ibm.as400.access.AS400JDBCDriver";
	//private static String dbUrlLocal = "jdbc:as400://192.168.1.10/CR";
	//private static String dbUrlLocal = "jdbc:as400://127.0.0.1/CR";
	private static String dbUrlLocal = "jdbc:as400://192.168.1.2/CR";
	private static String dbClaseServidor = dbClaseLocal;
	private static String dbUrlServidor = dbUrlLocal;
	private static String dbClaseServidorCentral = dbClaseLocal;
	private static String dbUrlServidorCentral = dbUrlLocal;

	// El objetivo es registrar los mismos datos de acceso a BD para la CR y el Servidor
	private static String dbUsuario = "rootcr";
	private static String dbClave = "root2003";

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

	// Constantes para las variables char que indican Si o No
	public static final char SI = 'S';
	public static final char NO = 'N';
	public static final char ACTIVO = 'A';
	public static final char VIGENTE = 'V';
	
	// Constantes para los tipo de clientes
	public static final char CLIENTE_NATURAL = 'N';
	public static final char CLIENTE_JURIDICO = 'J';
	public static final char CLIENTE_GUBERNAMENTAL = 'G';
	public static final char CLIENTE_DIPLOMATICO = 'D';
	public static final char CLIENTE_VENEZOLANO = 'V';
	public static final char CLIENTE_EXTRANJERO = 'E';
	public static final char CLIENTE_PASAPORTE = 'P';

	// Constantes para la forma de Pago Retención
	public static final String FORMA_PAGO_RETENCION = "8888888888";
	public static final int TIPO_PAGO_CARGO_CUENTA = 7;
	public static double porcentajeRetencionIVA = 75;

	// Constantes para los tipos de entrega de los productos
	public static final String ENTREGA_CAJA = "Caja";
	public static final String ENTREGA_DESPACHO = "Despacho";
	public static final String ENTREGA_DOMICILIO = "Domicilio";
	public static final String ENTREGA_CLIENTE_RETIRA = "C. Retira";
	public static final char COD_ENTREGA_CAJA = 'C';
	public static final char COD_ENTREGA_DESPACHO = 'D';
	public static final char COD_ENTREGA_DOMICILIO = 'F'; //Flete
	public static final char COD_ENTREGA_CLIENTE_RETIRA = 'R';

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
	
	// Constantes de los estados de las promociones (Por producto)
	public static final char PROMOCION_ACTIVA = 'A';

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
	
	public static String codRegion = "CAR";
	
	public static final String FORMA_PAGO_EFECTIVO = "01";

	public Sesion() throws BaseDeDatosExcepcion, ConexionExcepcion {
		cargarCondicionesDeVenta();
	}
	/**
	 * Método cargarCondicionesDeVenta.
	 * 		Carga las condiciones de venta de la Base de Datos.
	 */
	public static void cargarCondicionesDeVenta() throws BaseDeDatosExcepcion, ConexionExcepcion {
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
			}
		} catch (SQLException ex) {
			throw (new BaseDeDatosExcepcion("Falla en la conexion con la Base de Datos al acceder datos de Condiciones de Venta en: " + Sesion.getDbUrlLocal(), ex));
		} finally {
			try {
				condicionesDeVenta.close();
			} catch (SQLException e) {
			}
			condicionesDeVenta = null;
		}
	}

	/**
	 * @return
	 */
	public static String getDbClaseLocal() {
		return dbClaseLocal;
	}

	/**
	 * @return
	 */
	public static String getDbClave() {
		return dbClave;
	}

	/**
	 * @return
	 */
	public static String getDbEsquema() {
		return dbEsquema;
	}

	/**
	 * @return
	 */
	public static String getDbUrlLocal() {
		return dbUrlLocal;
	}

	/**
	 * @return
	 */
	public static String getDbUsuario() {
		return dbUsuario;
	}

	/**
	 * @return
	 */
	public static String getCodRegion() {
		return codRegion;
	}

	/**
	 * @param string
	 */
	public static void setCodRegion(String string) {
		codRegion = string;
	}

}
