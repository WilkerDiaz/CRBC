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
 * Versión     : 
 * Fecha       : 
 * Analista    : 
 * Descripción :
 * =============================================================================
 */
package com.beco.colascr.transferencias.configuracion;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Vector;


import com.beco.colascr.transferencias.comunicacionbd.Conexiones;
import com.beco.colascr.transferencias.excepciones.BaseDeDatosExcepcion;
import com.beco.colascr.transferencias.excepciones.ConexionExcepcion;
import com.beco.colascr.utiles.MensajesVentanas;
import com.epa.preferencesproxy.EPAPreferenceProxy;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;


/** 
 * Descripción: 
 * 		Esta clase carga los parámetros y variables necesarias para el 
 * correcto funcionamiento del sistema. También proporciona métodos que 
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

	// Constantes de los estados de los apartados
	public static final char APARTADO_ACTIVO = 'P';
	public static final char APARTADO_PAGADO = 'C';
	public static final char APARTADO_FACTURADO = 'F';
	public static final char APARTADO_ANULADO_EXONERADO = 'X';
	public static final char APARTADO_ANULADO_CON_CARGO = 'E';
	public static final char APARTADO_VENCIDO = 'V';
	public static final char ABONO_ACTIVO = 'A';
	public static final char ABONO_ANULADO = 'E';	
	
	// Parámetros de conexión con la base de datos
	private static String dbEsquema;
	private static String dbClaseLocal;
	private static String dbUrlLocal;
	private static String dbClaseServidor;
	private static String dbUrlServidor;

	// El objetivo es registrar los mismos datos de acceso a BD para la CR y el Servidor
	private static String dbUsuario;	
	private static String dbClave;		

	// Constantes indicadoras de Base De Datos
	public static final String BD_LOCAL = "C"; // Constante de BD del servidor de tienda
	public static final String BD_SERVIDOR = "S"; // Constante de BD del servidor de la empresa
	
	// Indicadores de Tiempos de sincronizacion
	private static PoliticaTarea frecuenciaSincProductos = null; // Frecuencia de Sincronizacion hacia la tienda en minutos
	private static PoliticaTarea frecuenciaSincAfiliados = null; // Frecuencia de Sincronizacion hacia la tienda en minutos
	private static PoliticaTarea frecuenciaSincAfiliadosTemporales = null; // Frecuencia de Sincronizacion hacia la tienda en minutos
	private static PoliticaTarea frecuenciaSincBases = null; // Frecuencia de Sincronizacion hacia la tienda en minutos
	private static PoliticaTarea frecuenciaSincVentas = null; // Frecuencia de Sincronizacion hacia la tienda en minutos
	private static PoliticaTarea frecuenciaSincListaRegalos = null; // Frecuencia de Sincronizacion hacia ServCentral en minutos
	private static String limiteInfSinc = null; // Frecuencia de Sincronizacion hacia la tienda en minutos
	private static String limiteSupSinc = null; // Frecuencia de Sincronizacion hacia la tienda en minutos
	 
	// Indicadores de direccion de sincronizacion
	public static final int SINC_SERVCENTRAL = 2;
	public static final int SINC_SERVTDA = 1;
	
	private static Tienda tienda;
	private static int numeroTda;

	//  Variable de dirección de los archivos a ser parseados
	public static String pathArchivosPref = "";
	public static String pathArchivos = "";
	public static final char sepCampo = '[';
	public static final char delCampo = ']';
	
	// Constantes de los estados de las promociones
	public static final char PROMOCION_ACTIVA = 'A';
	public static final char PROMOCION_ANULADA = 'E';

	
	/**
	 * Contructor para Sesion
	 * 		Crea la sesión actual. Inicializa los parámetros (clase, url, 
	 * usuario, clave) por defecto para la conexión a la base de datos de 
	 * la caja registradora y del servidor para tomar todos los parametros 
	 * necesarios para el sistema.
	 */
	public Sesion () throws Exception {
		if (cargarPreferencias()) {
			cargarConfiguracionTareas();
			File f = new File(pathArchivosPref);
			pathArchivos = f.getAbsolutePath() + File.separatorChar;
			pathArchivos = pathArchivos.replace(File.separatorChar,'/');
			pathArchivos = pathArchivos.replaceAll("/","//");
			System.out.println(pathArchivos);

			//pathArchivos = "c://Documents and Settings//gmartinelli//Mis documentos//AmbienteWebSphere//CajaRegistradora//Colas-ServTienda-ServCentral//transfer//";
			//pathArchivos = "c://Documents and Settings//irojas//Mis documentos//CajaRegistradora//ColasCR//transfer//";

			/*LinkedHashMap preferenciasCaja = new LinkedHashMap();
			
			try {
				preferenciasCaja = preferencias.getAllPreferecesForNode("colasCR");
				Sesion.setDbEsquema(preferenciasCaja.get("dbEsquema").toString());
				Sesion.setDbClaseLocal(preferenciasCaja.get("dbClaseLocal").toString());
				Sesion.setDbClaseServidor(preferenciasCaja.get("dbClaseServidor").toString());
				Sesion.setDbUrlLocal(preferenciasCaja.get("dbUrlLocal").toString());
				Sesion.setDbUrlServidor(preferenciasCaja.get("dbUrlServidor").toString());
				Sesion.setDbUsuario(preferenciasCaja.get("dbUsuario").toString());
				Sesion.setDbClave(preferenciasCaja.get("dbClave").toString());
				Sesion.setNumeroTda(Integer.parseInt(preferenciasCaja.get("numTienda").toString()));
			} catch (NoSuchNodeException e) {
				e.printStackTrace();
			} catch (UnidentifiedPreferenceException e) {
				e.printStackTrace();
			}*/
	
			// Creamos la conexion con la base de datos con los datos locales por defecto
			new Conexiones();
	
			// OJO . DEBEMOS OBTENER EL NUMERO DE LA TIENDA PARA QUE FUNCIONE BIEN
			try {
				cargarDatosTienda();
			} catch (Exception e) {
				// OJO. AQUI DEBEMOS SINCRONIZAR LA TIENDA QUE DIGA EN LAS PREFERENCIAS O EN SU DEFECTO
				// REALIZAR UNA APLICACION DE CONFIGURACION PARA INGRESAR EL NUMERO DE LA TIENDA EN LA BD.
				// DEBEMOS CHEQUEAR SI ES NECESARIA LA CARGA DE LA PUBLICIDAD DE LA TIENDA EN EL SINCRONI-
				// ZADOR. PERO SI ES NECESARIA TENERLA EN LA BD JUNTO CON LA TIENDA.
				// SE DEBE REALIZAR LO MISMO CON CAJA? O SE DEBE REALIZAR EN LA CAJA, UNA APLICACION DE CONF.
				// DE CR?
				MensajesVentanas.mensajeUsuario("Error de Inicio de Sistema. No se encontró registro Tienda en BD");
				throw e;
			}
			
			// Sincronizamos fecha con el Servidor
		/*	try {
				if(!MaquinaDeEstado.sincronizarFechaHora())
					Sesion.getCaja().setEstado(Sesion.NO_OPERATIVA);
			} catch (BaseDeDatosExcepcion e) {
				MensajesVentanas.mensajeError(e.getMensaje());
			} catch (ConexionExcepcion e) {
				MensajesVentanas.mensajeError(e.getMensaje());
			}*/
		} else {
			cargarConfiguracionTareas();
			MensajesVentanas.mensajeUsuario("Configuración de Servidor incorrecta.\nEjecute le aplicación de configuración");
			throw new Exception();
		}
	}
	
	/**
	 * Método cargarPreferencias
	 * 
	 * @throws NodeAlreadyExistsException
	 * @throws NoSuchNodeException
	 * @throws UnidentifiedPreferenceException
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'LinkedHashMap' 
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unchecked")
	private boolean cargarPreferencias() {
		EPAPreferenceProxy preferencias = new EPAPreferenceProxy("proyectocr");
		LinkedHashMap<String,Object> preferenciasServ = new LinkedHashMap<String,Object>();
		try {
			preferenciasServ = preferencias.getAllPreferecesForNode("colasCR");
			Sesion.setDbEsquema(preferenciasServ.get("dbEsquema").toString());
			Sesion.setDbClaseLocal(preferenciasServ.get("dbClaseLocal").toString());
			Sesion.setDbClaseServidor(preferenciasServ.get("dbClaseServidor").toString());
			Sesion.setDbUrlLocal(preferenciasServ.get("dbUrlLocal").toString());
			Sesion.setDbUrlServidor(preferenciasServ.get("dbUrlServidor").toString());
			Sesion.setDbUsuario(preferenciasServ.get("dbUsuario").toString());
			Sesion.setDbClave(preferenciasServ.get("dbClave").toString());
			Sesion.setNumeroTda(Integer.parseInt(preferenciasServ.get("numTienda").toString()));
//			Sesion.setFrecuenciaSincBases(Integer.parseInt(preferenciasServ.get("frecuenciaSyncBases").toString()));
//			Sesion.setFrecuenciaSincProductos(Integer.parseInt(preferenciasServ.get("frecuenciaSyncProductos").toString()));
//			Sesion.setFrecuenciaSincVentas(Integer.parseInt(preferenciasServ.get("frecuenciaSyncVentas").toString()));
			Sesion.setPathArchivosPref(preferenciasServ.get("pathArchivosTransfer").toString());
		} catch (NoSuchNodeException e) {
			return false;
		} catch (UnidentifiedPreferenceException e) {
			return false;
		}
		return true;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'LinkedHashMap' 
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unchecked")
	private boolean cargarConfiguracionTareas() {
		EPAPreferenceProxy preferencias = new EPAPreferenceProxy("proyectocr");
		LinkedHashMap<String,Object> preferenciasServ = new LinkedHashMap<String,Object>();
		try {
			preferenciasServ = preferencias.getAllPreferecesForNode("colasCR");
			frecuenciaSincBases = (frecuenciaSincBases==null) 
								? new PoliticaTarea("Entidades Bases", preferenciasServ.get("tipoSincBases").toString(), preferenciasServ.get("valorSincBases").toString())
								: frecuenciaSincBases;
			frecuenciaSincProductos  = (frecuenciaSincProductos==null)
								? new PoliticaTarea("Productos", preferenciasServ.get("tipoSincProductos").toString(), preferenciasServ.get("valorSincProductos").toString())
								: frecuenciaSincProductos;
			frecuenciaSincAfiliados  = (frecuenciaSincAfiliados==null)
								? new PoliticaTarea("Afiliados", preferenciasServ.get("tipoSincAfiliados").toString(), preferenciasServ.get("valorSincAfiliados").toString())
								: frecuenciaSincAfiliados;
			frecuenciaSincAfiliadosTemporales  = (frecuenciaSincAfiliadosTemporales==null)
								? new PoliticaTarea("Afiliados Temporales", preferenciasServ.get("tipoSincAfiliadosTemporales").toString(), preferenciasServ.get("valorSincAfiliadosTemporales").toString())
								: frecuenciaSincAfiliadosTemporales;
			frecuenciaSincVentas  = (frecuenciaSincVentas==null)
								? new PoliticaTarea("Ventas/Servicios", preferenciasServ.get("tipoSincVentas").toString(), preferenciasServ.get("valorSincVentas").toString())
								: frecuenciaSincVentas;
			frecuenciaSincListaRegalos  = (frecuenciaSincListaRegalos==null)
								? new PoliticaTarea("ListaRegalos", preferenciasServ.get("tipoSincListaRegalos").toString(), preferenciasServ.get("valorSincListaRegalos").toString())
								: frecuenciaSincListaRegalos;
			limiteInfSinc = preferenciasServ.get("valorLimiteInf").toString();
			limiteSupSinc = preferenciasServ.get("valorLimiteSup").toString();
		} catch (NoSuchNodeException e) {
			e.printStackTrace();
			return false;
		} catch (UnidentifiedPreferenceException e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	private static void cargarDatosTienda() throws ConexionExcepcion, BaseDeDatosExcepcion {
		boolean buscarLocal = true;

		ResultSet datosTienda = null;
		try {
			datosTienda = Conexiones.obtenerDatosTiendaAS400(numeroTda);
			if (datosTienda.first()) {
				// Parametros de descuento a empleados y uso de vendedores
				boolean indicaDesctoEmpleado = (datosTienda.getString("indicadesctoempleado").toCharArray()[0] == SI) ? true : false;
				double desctoVentaEmpleado = datosTienda.getDouble("desctoventaempleado");
				boolean utilizarVendedor = (datosTienda.getString("utilizarvendedor").toCharArray()[0] == SI) ? true : false;

				// Parámetro que indica si se puede cambiar o no precios en caja
				boolean cambiarPrecio = (datosTienda.getString("cambioprecioencaja").toCharArray()[0] == SI) ? true : false; 

				// Variable que indica si los descuentos son acumulativos
				boolean desctosAcumulativos = (datosTienda.getString("desctosacumulativos").toCharArray()[0] == SI) ? true : false;
			
				// Obtenemos las lineas de publicidad de la tienda
				ResultSet pubTienda = Conexiones.obtenerPublicidadTienda(numeroTda, false);
				pubTienda.beforeFirst();
				Vector<String> publicidad = new Vector<String>();
				while (pubTienda.next())
					publicidad.addElement(pubTienda.getString("publicidadlinea"));
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
									datosTienda.getDate("fechatienda"), indicaDesctoEmpleado ? "S":"N", desctoVentaEmpleado,
									cambiarPrecio?"S":"N", utilizarVendedor?"S":"N", desctosAcumulativos?"S":"N");
				tienda.registrarTienda();
				buscarLocal = false;
				try {
					datosTienda.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				datosTienda = null;
			} else {
				// No tenemos conexión con Serv. AS400. Verificamos si la tienda existe en la BD local
				try { datosTienda.close(); } catch (SQLException e) {}
				datosTienda = null;
			}
		} catch (Exception ex) {
		}
		
		if (buscarLocal) {
			datosTienda = Conexiones.obtenerDatosTiendaLocal();
			try {
				if (datosTienda.first()) {
					// Parametros de descuento a empleados y uso de vendedores
					boolean indicaDesctoEmpleado = (datosTienda.getString("indicadesctoempleado").toCharArray()[0] == SI) ? true : false;
					double desctoVentaEmpleado = datosTienda.getDouble("desctoventaempleado");
					boolean utilizarVendedor = (datosTienda.getString("utilizarvendedor").toCharArray()[0] == SI) ? true : false;
	
					// Parámetro que indica si se puede cambiar o no precios en caja
					boolean cambiarPrecio = (datosTienda.getString("cambioprecioencaja").toCharArray()[0] == SI) ? true : false; 
	
					// Variable que indica si los descuentos son acumulativos
					boolean desctosAcumulativos = (datosTienda.getString("desctosacumulativos").toCharArray()[0] == SI) ? true : false;
				
					// Obtenemos las lineas de publicidad de la tienda
					ResultSet pubTienda = Conexiones.obtenerPublicidadTienda(numeroTda, true);
					pubTienda.beforeFirst();
					Vector<String> publicidad = new Vector<String>();
					while (pubTienda.next())
						publicidad.addElement(pubTienda.getString("publicidadlinea"));
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
										datosTienda.getDate("fechatienda"), indicaDesctoEmpleado ? "S":"N", desctoVentaEmpleado,
										cambiarPrecio?"S":"N", utilizarVendedor?"S":"N", desctosAcumulativos?"S":"N");
				} else {
					throw (new BaseDeDatosExcepcion("No se pudo cargar la Sesión. No se encontró registro Tienda en la Base de Datos"));
				}
			} catch (SQLException ex) {
				throw (new BaseDeDatosExcepcion("Falla en la conexión con la Base de Datos al acceder datos de Tienda en: " + Sesion.getDbUrlLocal(), ex));
			} finally {
				try {
					datosTienda.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				datosTienda = null;
			}
		}
	}

	/**
	 * Devuelve el esquema para conexión con la base de datos.
	 * @return String - Cadena de 100 caracteres
	 */
	public static String getDbEsquema() {
		return dbEsquema;
	}

	/**
	 * Devuelve la clase para conexión con la base de datos local (CR).
	 * @return String - Cadena de 100 caracteres
	 */
	public static String getDbClaseLocal() {
		return dbClaseLocal;
	}

	/**
	 * Devuelve la clase para conexión con la base de datos remota (Servidor).
	 * @return String - Cadena de 100 caracteres
	 */
	public static String getDbClaseServidor() {
		return dbClaseServidor;
	}

	/**
	 * Devuelve la clave de acceso a la base de datos remota y local.
	 * @return String - Cadena de 100 caracteres
	 */
	public static String getDbClave() {
		return dbClave;
	}

	/**
	 * Devuelve la dirección de acceso a la base de datos local.
	 * @return String - Cadena de 100 caracteres
	 */
	public static String getDbUrlLocal() {
		return dbUrlLocal;
	}

	/**
	 * Devuelve la dirección de acceso a la base de datos remota.
	 * @return String - Cadena de 100 caracteres
	 */
	public static String getDbUrlServidor() {
		return dbUrlServidor;
	}

	/**
	 * Devuelve el nombre de usuario para la conexión/acceso a la base de 
	 * datos remota y local.
	 * @return String - Cadena de 30 caracteres
	 */
	public static String getDbUsuario() {
		return dbUsuario;
	}

	/**
	 * Establece el esquema para conexión con la base de datos.
	 * @param dbEsquema - Cadena de 100 caracteres máximo
	 */
	public static void setDbEsquema(String dbEsquema) {
		Sesion.dbEsquema = dbEsquema;
	}

	/**
	 * Establece la clase para conexión con la base de datos local (CR).
	 * @param dbClaseCr - Cadena de 100 caracteres máximo
	 */
	public static void setDbClaseLocal(String dbClaseCr) {
		Sesion.dbClaseLocal = dbClaseCr;
	}

	/**
	 * Establece la clase para conexión con la base de datos remota.
	 * @param dbClaseServidor - Cadena de 100 caracteres máximo
	 */
	public static void setDbClaseServidor(String dbClaseServidor) {
		Sesion.dbClaseServidor = dbClaseServidor;
	}

	/**
	 * Establece la clave de acceso a la base de datos remota y local.
	 * @param dbClave - Cadena de 100 caracteres máximo
	 */
	public static void setDbClave(String dbClave) {
		Sesion.dbClave = dbClave;
	}

	/**
	 * Establece la dirección de acceso a la base de datos local.
	 * @param dbUrlCr - Cadena de 100 caracteres máximo
	 */
	public static void setDbUrlLocal(String dbUrlCr) {
		// La variable prop es utilizada después de la Migración a MYSQL5.5, ya que
		//los INSERT, UPDATE presentaban errores por validaciones de manejador.
		//(Truncar String y Timestamp nulos)
		String prop ="?zeroDateTimeBehavior=convertToNull&jdbcCompliantTruncation=false";
		Sesion.dbUrlLocal = dbUrlCr+prop;
	}

	/**
	 * Establece la dirección de acceso a la base de datos remota.
	 * @param dbUrlServidor - Cadena de 100 caracteres máximo
	 */
	public static void setDbUrlServidor(String dbUrlServidor) {
		Sesion.dbUrlServidor = dbUrlServidor;
	}

	/**
	 * Establece el nombre de usuario para la conexión/acceso a la base de 
	 * datos remota y local.
	 * @param dbUsuario - Cadena de 30 caracteres máximo
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
	 * @return
	 */
	public static Tienda getTienda() {
		return tienda;
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

	/**
	 * Método setPathArchivosPref
	 * 
	 * @param string
	 * void
	 */
	public static void setPathArchivosPref(String string) {
		pathArchivosPref = string;
	}

	/**
	 * Método getPathArchivos
	 * 
	 * @return
	 * String
	 */
	public static String getPathArchivos() {
		return pathArchivos;
	}

	/**
	 * Método getFrecuenciaSincBases
	 * 
	 * @return
	 * PoliticaTarea
	 */
	public static PoliticaTarea getFrecuenciaSincBases() {
		return frecuenciaSincBases;
	}

	/**
	 * Método getFrecuenciaSincProductos
	 * 
	 * @return
	 * PoliticaTarea
	 */
	public static PoliticaTarea getFrecuenciaSincProductos() {
		return frecuenciaSincProductos;
	}

	/**
	 * Método getFrecuenciaSincVentas
	 * 
	 * @return
	 * PoliticaTarea
	 */
	public static PoliticaTarea getFrecuenciaSincVentas() {
		return frecuenciaSincVentas;
	}

	/**
	 * Método setFrecuenciaSincBases
	 * 
	 * @param tarea
	 * void
	 */
	public static void setFrecuenciaSincBases(PoliticaTarea tarea) {
		frecuenciaSincBases = tarea;
	}

	/**
	 * Método setFrecuenciaSincProductos
	 * 
	 * @param tarea
	 * void
	 */
	public static void setFrecuenciaSincProductos(PoliticaTarea tarea) {
		frecuenciaSincProductos = tarea;
	}

	/**
	 * Método setFrecuenciaSincVentas
	 * 
	 * @param tarea
	 * void
	 */
	public static void setFrecuenciaSincVentas(PoliticaTarea tarea) {
		frecuenciaSincVentas = tarea;
	}

	/**
	 * Método getFrecuenciaSincAfiliados
	 * 
	 * @return
	 * PoliticaTarea
	 */
	public static PoliticaTarea getFrecuenciaSincAfiliados() {
		return frecuenciaSincAfiliados;
	}

	/**
	 * Método setFrecuenciaSincAfiliados
	 * 
	 * @param tarea
	 * void
	 */
	public static void setFrecuenciaSincAfiliados(PoliticaTarea tarea) {
		frecuenciaSincAfiliados = tarea;
	}

	/**
	 * Método getFrecuenciaSincAfiliados
	 * 
	 * @return
	 * PoliticaTarea
	 */
	public static PoliticaTarea getFrecuenciaSincAfiliadosTemporales() {
		return frecuenciaSincAfiliadosTemporales;
	}

	/**
	 * Método setFrecuenciaSincAfiliados
	 * 
	 * @param tarea
	 * void
	 */
	public static void setFrecuenciaSincAfiliadosTemporales(PoliticaTarea tarea) {
		frecuenciaSincAfiliadosTemporales = tarea;
	}
	
	
	/**
	 * Método getLimiteInfSinc
	 * 
	 * @return
	 * String
	 */
	public static String getLimiteInfSinc() {
		return limiteInfSinc;
	}

	/**
	 * Método getLimiteSupSinc
	 * 
	 * @return
	 * String
	 */
	public static String getLimiteSupSinc() {
		return limiteSupSinc;
	}

	/**
	 * Método getFrecuenciaSincAfiliados
	 * 
	 * @return
	 * PoliticaTarea
	 */
	public static PoliticaTarea getFrecuenciaSincListaRegalos() {
		return frecuenciaSincListaRegalos;
	}

	/**
	 * Método setFrecuenciaSincAfiliados
	 * 
	 * @param tarea
	 * void
	 */
	public static void setFrecuenciaSincListaRegalos(PoliticaTarea tarea) {
		frecuenciaSincListaRegalos = tarea;
	}
}