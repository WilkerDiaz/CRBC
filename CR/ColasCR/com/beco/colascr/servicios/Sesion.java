/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.colascr.transferencias.configuracion
 * Programa   : Sesion.java
 * Creado por : Ileana Rojas, Gabriel Martinelli
 * Creado en  : 21/02/2005 04:23:59 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versi�n     : 
 * Fecha       : 
 * Analista    : 
 * Descripci�n :
 * =============================================================================
 */
package com.beco.colascr.servicios;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

import com.beco.colascr.servicios.mediadorbd.Conexiones;
import com.epa.preferencesproxy.EPAPreferenceProxy;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.NodeAlreadyExistsException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;


/** 
 * Descripci�n: 
 * 		Esta clase carga los par�metros y variables necesarias para el 
 * correcto funcionamiento del sistema. Tambi�n proporciona m�todos que 
 * permiten el control de cada una de sus propiedades.
 */
public class Sesion {

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

	// Constantes de los tipos de servicio
	public static final String COTIZACION = "01";
	public static final String APARTADO = "02";
	public static final String DESPACHO = "03";
	public static final String COMANDA = "04";
	public static final String ENTDOMICILIO = "05";
	
	// Constantes de los estados de las cotizaciones
	public static final char COTIZACION_ACTIVA = 'V';
	public static final char COTIZACION_FACTURADA = 'F';
	
	// Constantes de los estados de los servicios (Entrega a Domiclio y despachos fuera de caja)
	public static final char SERVICIO_ACTIVA = 'A';
	public static final char SERVICIO_ANULADO = 'E';
	public static final char SERVICIO_FINALIZADO = 'F';
	public static final char SERVICIO_IMPRIMIENDO = 'I';
	
	// Par�metros de conexi�n con la base de datos
	private static String dbEsquema;
	private static String dbClaseLocal;
	private static String dbUrlLocal;
	private static String dbClaseServidor;
	private static String dbClaseServidorCentral;
	private static String dbUrlServidor;
	private static String dbUrlServidorCentral;

	// El objetivo es registrar los mismos datos de acceso a BD para la CR y el Servidor
	private static String dbUsuario;	
	private static String dbClave;		

	// Constantes indicadoras de Base De Datos
	public static final String BD_LOCAL = "C"; // Constante de BD del servidor de tienda
	public static final String BD_SERVIDOR = "S"; // Constante de BD del servidor de la empresa
	
	private static int numeroTda;

	/**
	 * Contructor para Sesion
	 * 		Crea la sesi�n actual. Inicializa los par�metros (clase, url, 
	 * usuario, clave) por defecto para la conexi�n a la base de datos de 
	 * la caja registradora y del servidor para tomar todos los parametros 
	 * necesarios para el sistema.
	 */
	public Sesion () throws Exception {
		if (cargarPreferencias())
			// Creamos la conexion con la base de datos con los datos locales por defecto
			new Conexiones(true);
		else
			//MensajesVentanas.mensajeUsuario("Configuraci�n de Servidor incorrecta.\nEjecute le aplicaci�n de configuraci�n");
			throw new Exception();
	}
	
	/**
	 * M�todo cargarPreferencias
	 * 
	 * @throws NodeAlreadyExistsException
	 * @throws NoSuchNodeException
	 * @throws UnidentifiedPreferenceException
	 */
	@SuppressWarnings("unchecked")
	private boolean cargarPreferencias() {
		EPAPreferenceProxy preferencias = new EPAPreferenceProxy("proyectocr");
		LinkedHashMap<String,String> preferenciasServ = new LinkedHashMap<String,String>();
		try {
			preferenciasServ = preferencias.getAllPreferecesForNode("colasCR");
			Sesion.setDbEsquema(preferenciasServ.get("dbEsquema").toString());
			Sesion.setDbClaseLocal(preferenciasServ.get("dbClaseLocal").toString());
			Sesion.setDbClaseServidor(preferenciasServ.get("dbClaseServidor").toString());
			//Sesion.setDbClaseServidorCentral(preferenciasServ.get("dbClaseServidorCentral").toString());
			Sesion.setDbUrlLocal(preferenciasServ.get("dbUrlLocal").toString());
			Sesion.setDbUrlServidor(preferenciasServ.get("dbUrlServidor").toString());
			Sesion.setDbUsuario(preferenciasServ.get("dbUsuario").toString());
			Sesion.setDbClave(preferenciasServ.get("dbClave").toString());
			Sesion.setNumeroTda(Integer.parseInt(preferenciasServ.get("numTienda").toString()));
		} catch (NoSuchNodeException e) {
			return false;
		} catch (UnidentifiedPreferenceException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Devuelve el esquema para conexi�n con la base de datos.
	 * @return String - Cadena de 100 caracteres
	 */
	public static String getDbEsquema() {
		return dbEsquema;
	}

	/**
	 * Devuelve la clase para conexi�n con la base de datos local (CR).
	 * @return String - Cadena de 100 caracteres
	 */
	public static String getDbClaseLocal() {
		return dbClaseLocal;
	}

	/**
	 * Devuelve la clase para conexi�n con la base de datos remota (Servidor).
	 * @return String - Cadena de 100 caracteres
	 */
	public static String getDbClaseServidor() {
		return dbClaseServidor;
	}
	
	/**
	 * Devuelve la clase para conexi�n con la base de datos remota (Servidor).
	 * @return String - Cadena de 100 caracteres
	 */
	public static String getDbClaseServidorCentral() {
		return dbClaseServidorCentral;
	}

	/**
	 * Devuelve la clave de acceso a la base de datos remota y local.
	 * @return String - Cadena de 100 caracteres
	 */
	public static String getDbClave() {
		return dbClave;
	}

	/**
	 * Devuelve la direcci�n de acceso a la base de datos local.
	 * @return String - Cadena de 100 caracteres
	 */
	public static String getDbUrlLocal() {
		return dbUrlLocal;
	}

	/**
	 * Devuelve la direcci�n de acceso a la base de datos remota.
	 * @return String - Cadena de 100 caracteres
	 */
	public static String getDbUrlServidor() {
		return dbUrlServidor;
	}
	
	/**
	 * Devuelve la direcci�n de acceso a la base de datos remota.
	 * @return String - Cadena de 100 caracteres
	 */
	public static String getDbUrlServidorCentral() {
		return dbUrlServidorCentral;
	}

	/**
	 * Devuelve el nombre de usuario para la conexi�n/acceso a la base de 
	 * datos remota y local.
	 * @return String - Cadena de 30 caracteres
	 */
	public static String getDbUsuario() {
		return dbUsuario;
	}

	/**
	 * Establece el esquema para conexi�n con la base de datos.
	 * @param dbEsquema - Cadena de 100 caracteres m�ximo
	 */
	public static void setDbEsquema(String dbEsquema) {
		Sesion.dbEsquema = dbEsquema;
	}

	/**
	 * Establece la clase para conexi�n con la base de datos local (CR).
	 * @param dbClaseCr - Cadena de 100 caracteres m�ximo
	 */
	public static void setDbClaseLocal(String dbClaseCr) {
		Sesion.dbClaseLocal = dbClaseCr;
	}

	/**
	 * Establece la clase para conexi�n con la base de datos remota.
	 * @param dbClaseServidor - Cadena de 100 caracteres m�ximo
	 */
	public static void setDbClaseServidor(String dbClaseServidor) {
		Sesion.dbClaseServidor = dbClaseServidor;
	}
	
	/**
	 * Establece la clase para conexi�n con la base de datos remota.
	 * @param dbClaseServidor - Cadena de 100 caracteres m�ximo
	 */
	public static void setDbClaseServidorCentral(String dbClaseServidor) {
		Sesion.dbClaseServidorCentral = dbClaseServidor;
	}

	/**
	 * Establece la clave de acceso a la base de datos remota y local.
	 * @param dbClave - Cadena de 100 caracteres m�ximo
	 */
	public static void setDbClave(String dbClave) {
		Sesion.dbClave = dbClave;
	}

	/**
	 * Establece la direcci�n de acceso a la base de datos local.
	 * @param dbUrlCr - Cadena de 100 caracteres m�ximo
	 */
	public static void setDbUrlLocal(String dbUrlCr) {
		// La variable prop es utilizada despu�s de la Migraci�n a MYSQL5.5, ya que
		//los INSERT, UPDATE presentaban errores por validaciones de manejador.
		//(Truncar String y Timestamp nulos)
		String prop ="?zeroDateTimeBehavior=convertToNull&jdbcCompliantTruncation=false";
		Sesion.dbUrlLocal = dbUrlCr +  prop;
	}

	/**
	 * Establece la direcci�n de acceso a la base de datos remota.
	 * @param dbUrlServidor - Cadena de 100 caracteres m�ximo
	 */
	public static void setDbUrlServidor(String dbUrlServidor) {
		Sesion.dbUrlServidor = dbUrlServidor ;
	}

	/**
	 * Establece la direcci�n de acceso a la base de datos remota.
	 * @param dbUrlServidor - Cadena de 100 caracteres m�ximo
	 */
	public static void setDbUrlServidorCentral(String dbUrlServidorCentral) {
		Sesion.dbUrlServidorCentral = dbUrlServidorCentral;
	}
	
	/**
	 * Establece el nombre de usuario para la conexi�n/acceso a la base de 
	 * datos remota y local.
	 * @param dbUsuario - Cadena de 30 caracteres m�ximo
	 */
	public static void setDbUsuario(String dbUsuario) {
		Sesion.dbUsuario = dbUsuario;
	}

	/**
	 * Obtiene la fecha actual del sistema.
	 * @return Date - Fecha del sistema
	 */
	public static Date getFechaSistema() {
		return (new Date());
	}
	
	/**
	 * Obtiene la hora actual del sistema
	 * @return Time - Hora del sistema
	 */
	public static Time getHoraSistema() {
		return (new Time(new Date().getTime()));
	}

	/**
	 * Obtiene el timestamp del sistema.
	 * @return Timestamp - Timestamp del sistema
	 */
	public static Timestamp getTimestampSistema() {
		return (new Timestamp(Calendar.getInstance().getTime().getTime()));
	}	

	/**
	 * @param i
	 */
	public static void setNumeroTda(int i) {
		numeroTda = i;
	}

	/**
	 * @return
	 */
	public static int getNumeroTda() {
		return numeroTda;
	}
}