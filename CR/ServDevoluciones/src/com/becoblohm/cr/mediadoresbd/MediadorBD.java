
/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.manejarusuario
 * Programa   : MediadorBD.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 06/10/2003 05:28:07 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.9.1
 * Fecha       : 19-jul-04 09:18
 * Analista    : gmartinelli
 * Descripción : Uso de Esquema en los queryes CR.tabla.campo
 * =============================================================================
 * Versión     : 1.9.0
 * Fecha       : 01/07/2004 11:19 AM
 * Analista    : Programador3 - Alexis Gueédez López
 * Descripción : - Modificada la implementación del método isConexión de modo 
 * 				que la verificación de la línea sea a través de un chequeo de 
 * 				conexión de la red y no del chequeo de conexión JDBC.
 * =============================================================================
 * Versión     : 1.8.2
 * Fecha       : 29/06/2004 10:15 AM
 * Analista    : gmartinelli
 * Descripción : - Modificado método asignarUsuarioLogueado para que actualice el inicio de sesión de un usuario en
 * 					el registro de caja correspondiente de la BD.
 * =============================================================================
 * Versión     : 1.8.1
 * Fecha       : 24/03/2004 14:50 PM
 * Analista    : gmartinelli
 * Descripción : Arregladas las llamadas a FuncionExcecion para pasar un boolean
 * =============================================================================
 * Versión     : 1.8 (según CVS)
 * Fecha       : 26/02/2004 02:56:41 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Agregado Método ConectarServidor() para crear el acceso a la BD 
 * en el Servidor.
 * =============================================================================
 * Versión     : 1.0.3
 * Fecha       : 26/11/2003 01:56:41 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Modificaciones por requerimientos de integración BECO y EPA.
 * 				 Métodos agregados ->
 * 					verificarAutorizacionFuncion(String nombModulo, String nombMetodo)
 * 					verificarAutorizacionUsuario (String nombModulo, String nombMetodo, String codPerfil)
 * 					obtenerMetModFun (String nombModulo, String nombMetodo)
 * 				 Métodos eliminados ->
 * 					verificarAutorizacion(int codFuncion, String codPerfil)
 * =============================================================================
 * Versión     : 1.0.2
 * Fecha       : 31/10/2003 03:28:41 PM
 * Analista    : Gabriel Martinelli
 * Descripción : Modificación de la clase para requerimientos de BECO.
 * 				 Métodos agregados ->
 * 				 	obtenerPublicidadTienda(),
 * 				 	obtenerCargos(),
 * 				 	verificarAutorizacion(int codFuncion, String codPerfil)
 * =============================================================================
 * Versión     : 1.0.1
 * Fecha       : 31/10/2003 03:28:41 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Integración de la clase para EPA y BECO
 * =============================================================================
 * Versión     : 1.0.3
 * Fecha       : 29/01/2004 12:00:41 PM
 * Analista    : Gabriel Martinelli
 * Descripción : Modificaciones de sentencias Sql para integracion en Linux.
 * =============================================================================
 */
package com.becoblohm.cr.mediadoresbd;

import java.sql.ResultSet;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;


/**
 * 		Esta clase todas las operaciones conexion la base de datos. Funciona 
 * como mediador entre la aplicacion y la Base de Datos. Ejecuta sentencias 
 * simples y sentencias preparadas para actualización de tablas en la base 
 * de datos. También proporciona métodos para inicialización de la conexión 
 * conexion la base de datos y para el control de cada una de sus propiedades.
 */

public class MediadorBD {
	/**
	 * Logger for this class
	 */
/*	private static final Logger logger = Logger.getLogger(MediadorBD.class);

	private static int filas = 0;
	private static BuscadorCliente buscadorCliente;

	/**
	 * Constructor para MediadorBD.
	 * 		Establece los parámetros (clase, url, usuario, clave) para 
	 * conexión a la base de datos donde se ejecutará la sentencia SQL conexion 
	 * los datos del objeto Sesión.
	 */
/*	public MediadorBD(boolean local) throws BaseDeDatosExcepcion {
		try {
			new Conexiones(local);
			new Conexiones(!local);
		} catch (ConexionExcepcion e) {
			logger.error("MediadorBD(boolean)", e);
		}
	}
	
	/**
	 * *************************************************
	 * Métodos definidos por Equipo de Desarrollo de EPA
	 * *************************************************
	 */

	/**
	 * Método ejecutar.
	 * 		Ejecuta una sentencia simple SQL de actualización, insercion 
	 * o eliminación.
	 * @param sentenciaSql - Sentecia INSERT, UPDATE o DELETE
	 * @param BDLocal
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 * @return int - Número de filas afectadas por la sentencia
	 */
/*	public static int ejecutar(String sentenciaSql, boolean BDLocal) throws BaseDeDatosExcepcion, ConexionExcepcion{
		if (logger.isDebugEnabled()) {
			logger.debug("ejecutar(String, boolean) - start");
		}

		int filasAfectadas = 0;

		try {
			filasAfectadas = Conexiones.realizarSentencia(sentenciaSql, BDLocal);
		} catch (BaseDeDatosExcepcion ex) {
			logger.error("ejecutar(String, boolean)", ex);
			//throw (new BaseDeDatosExcepcion("Error al realizar sentencia " + sentenciaSql, ex));
		} catch (ConexionExcepcion ex) {
			logger.error("ejecutar(String, boolean)", ex);
			//throw (new BaseDeDatosExcepcion("Error al realizar sentencia " + sentenciaSql, ex));
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("ejecutar(String, boolean) - end");
		}
		return filasAfectadas;
	}

	/**
	 * Método ejecutar.
	 * 		Ejecuta una sentencia simple SQL de actualización, insercion 
	 * o eliminación en la BD local.
	 * @param sentenciaSql - Sentecia INSERT, UPDATE o DELETE
	 * @param BDLocal
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
 	 * @return int - Número de filas afectadas por la sentencia
	 */
/*	public static int ejecutar(String sentenciaSql) throws BaseDeDatosExcepcion, ConexionExcepcion{
		if (logger.isDebugEnabled()) {
			logger.debug("ejecutar(String) - start");
		}

		int returnint = ejecutar(sentenciaSql, true);
		if (logger.isDebugEnabled()) {
			logger.debug("ejecutar(String) - end");
		}
		return returnint;
	}

	/**
	 * Método getFilas.
	 * 		Devuelve el número de filas resultantes de una consulta.
	 * @return int - Número de filas resultantes/afectadas
	 */
/*	public static int getFilas() {
		if (logger.isDebugEnabled()) {
			logger.debug("getFilas() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getFilas() - end");
		}
		return filas;
	}

	/**
	 * Método isConexion
	 *		Verifica si esta activa la conexión con el PC indicado. 
	 * @param ip - Dirección ip del equipo a verificar.
	 * @return boolean
	 */
/*	public static boolean isConexion(String ip, int port) {
		if (logger.isDebugEnabled()) {
			logger.debug("isConexion(String, int) - start");
		}

		try {
			SocketAddress ipSocket = new InetSocketAddress(ip, port);
			Socket server = new Socket();
			server.connect(ipSocket, 1000);
			server.close();
		} catch (SocketTimeoutException e) {
			logger.error("isConexion(String, int)", e);

			if (logger.isDebugEnabled()) {
				logger.debug("isConexion(String, int) - end");
			}
			return false;
		} catch (NoRouteToHostException e) {
			logger.error("isConexion(String, int)", e);

			if (logger.isDebugEnabled()) {
				logger.debug("isConexion(String, int) - end");
			}
			return false;
		} catch (UnknownHostException e) {
			logger.error("isConexion(String, int)", e);

			if (logger.isDebugEnabled()) {
				logger.debug("isConexion(String, int) - end");
			}
			return false;
		} catch (ConnectException e) {
			logger.error("isConexion(String, int)", e);

			if (logger.isDebugEnabled()) {
				logger.debug("isConexion(String, int) - end");
			}
			return false;
		} catch (IOException e) {
			logger.error("isConexion(String, int)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isConexion(String, int) - end");
		}
		return true;
	}
	
	/**
	 * Método realizarConsulta.
	 * 		Ejecuta una sentencia SQL de consulta de registros en la BD local.
	 * @param sentenciaSql - Sentencia SELECT
	 * @param BDLocal
	 * @return ResultSet - Objeto conexion las filas resultantes de la	consulta
	 * @throws BaseDeDatosExcepcion - Cuando ocurre un error en la ejecucion de la consulta
	 */
/*	public static ResultSet realizarConsulta(String sentenciaSql) throws BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("realizarConsulta(String) - start");
		}

		ResultSet returnResultSet = realizarConsulta(sentenciaSql, true);
		if (logger.isDebugEnabled()) {
			logger.debug("realizarConsulta(String) - end");
		}
		return returnResultSet;
	}
		
	/**
	 * Método realizarConsulta.
	 * 		Ejecuta una sentencia SQL de consulta de registros en la BD indicada.
	 * @param sentenciaSql - Sentencia SELECT
	 * @param BDLocal
 	 * @return ResultSet - Objeto conexion las filas resultantes de la	consulta
	 * @throws BaseDeDatosExcepcion - Cuando ocurre un error en la ejecucion de la consulta
	 */
/*	public static ResultSet realizarConsulta(String sentenciaSql, boolean BDLocal) throws BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("realizarConsulta(String, boolean) - start");
		}

		ResultSet resultado = null;
		try {
			resultado = Conexiones.realizarConsulta(sentenciaSql, BDLocal);
			resultado.last();
			filas = resultado.getRow();
			resultado.first();
		} catch (SQLException e) {
			logger.error("realizarConsulta(String, boolean)", e);
			//throw (new BaseDeDatosExcepcion("Error al realizar sentencia " + sentenciaSql, e));
		} catch (ConexionExcepcion e) {
			logger.error("realizarConsulta(String, boolean)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("realizarConsulta(String, boolean) - end");
		}
		return resultado;
	}

	/**
	 * Método setUsuarioPerfiles
	 * 		Actualiza un lote de perfiles en la tabla usuarios en la CR.
	 * @param sentenciaSql - Sentencia SQL preparada
	 * @param valores - Lista de usuarios cuyos perfiles serán actualizados.
	 * @return int[] - Lista de números de filas afectadas por cada sentencia.
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion 
	 */
/*	public static int[] setPerfilUsuarios(String sentenciaSql, ArrayList valores) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("setPerfilUsuarios(String, ArrayList) - start");
		}

		int[] returnintArray = Conexiones.realizarLoteSentencias(sentenciaSql,
				true, valores, false);
		if (logger.isDebugEnabled()) {
			logger.debug("setPerfilUsuarios(String, ArrayList) - end");
		}
		return returnintArray;
	}
	
	/**
	 * Método setUsuarioLogueado
	 * 		Establece en la BD local y en el Servidor de Tienda el identificador del usuario que inicia sesión.
	 * @param usuario
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
/*	public static void setUsuarioLogueado(String usuario) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("setUsuarioLogueado(String) - start");
		}
		ejecutar("update caja set codusuario = '"+usuario+"' where numcaja = " + Sesion.getCaja().getNumero());
		Sesion.chequearLineaCaja();
		if (Sesion.isCajaEnLinea()) {
			if (usuario.trim().equals(""))
				ejecutar("update caja set codusuario = null where numcaja = " + Sesion.getCaja().getNumero(),false);
			else
				ejecutar("update caja set codusuario = '"+usuario+"' where numcaja = " + Sesion.getCaja().getNumero(),false);
		}		
		//TODO: Verificar efectos de este cambio
/*		if(Sesion.isCajaEnLinea())
			ejecutar("update caja set codusuario = '"+usuario+"' where numcaja = " + Sesion.getCaja().getNumero(), false);
*/
/*		if (logger.isDebugEnabled()) {
			logger.debug("setUsuarioLogueado(String) - end");
		}
	}

	/**
	 * Método setCierreCajero
	 * 		Establece en la BD local el indicador al sistema para solicitar autorizacion para el cierre de sesion.
	 * @param cierre
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
/*	public static void setCierreCajero(char cierre) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("setCierreCajero(char) - start");
		}

		ejecutar("update caja set cierrecajero = '"+String.valueOf(cierre).toUpperCase()+"' where numcaja = "+Sesion.getCaja().getNumero());

		if (logger.isDebugEnabled()) {
			logger.debug("setCierreCajero(char) - end");
		}
	}

	/**
	 * Método setEstadoCaja
	 * 		Establece en la BD local y en el Servidor de Tienda el identificador del estado de operación de la caja.
	 * @param estadoCaja
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
/*	public static void setEstadoCaja(String estadoCaja) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("setEstadoCaja(String) - start");
		}

		ejecutar("update caja set idestadocaja = '"+estadoCaja.toUpperCase()+
				"', ipcaja = '" +  Sesion.getCaja().getIp() + 
				"' where numcaja = "+Sesion.getCaja().getNumero());
//TODO: Verificar efectos de este cambio
/*		if((!Sesion.getCaja().getEstado().equals(Sesion.FACTURANDO)) && (Sesion.isCajaEnLinea()))
			ejecutar("update caja set idestadocaja = '"+estadoCaja.toUpperCase()+"' where numcaja = "+Sesion.getCaja().getNumero(), false);
*/
/*		if (logger.isDebugEnabled()) {
			logger.debug("setEstadoCaja(String) - end");
		}
	}
	
	
	
		
	public static String obtenerSerialCaja(){
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerSerialCaja() - start");
		}

		String numserial = "";
        ResultSet rs = null;		
		try {
			rs = obtenerDatosCaja(true);
			if (rs.first() == true){
				numserial = rs.getString("numserial").trim();		
			}
		} catch (BaseDeDatosExcepcion e) {
			logger.error("obtenerSerialCaja()", e);
		} catch (ConexionExcepcion e) {
			logger.error("obtenerSerialCaja()", e);
		}catch (SQLException e2) {
			logger.error("obtenerSerialCaja()", e2);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				rs = null;
			}
		}
		

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerSerialCaja() - end");
		}
		return numserial;
	}


	/**
	 * **************************************************
	 * Métodos definidos por Equipo de Desarrollo de BECO
	 * **************************************************
	 */

	/**
	 * Método obtenerEdoFinal.
	 * 		Obtiene el estado final para un identificador de estado inicial y un nombre de metodo. Es 
	 * utilizado por la maquina de estados para asegurar la ejecucion de los metodos de la aplicacion.
	 * @param edoInicial - Estado inicial de la Caja
	 * @param nombreMetodo - Nombre del metodo que se quiere ejecutar
	 * @return String - Identificador del estado final.
	 * @throws MaquinaDeEstadoExcepcion - Cuando el estado es incorrecto
	 * @throws BaseDeDatosExcepcion - Cuando falla la conexion conexion la base de datos
	 */
/*	public static String obtenerEdoFinal(String edoInicial, String nombreMetodo) 
									throws MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerEdoFinal(String, String) - start");
		}

		String edoFinal = edoInicial;
		
		// Buscamos en la base de datos en la tabla de estados
		String sentencia = "select me.edofinal from maquinadeestado me, metodos mt where me.edoinicial=" 
						    + edoInicial.toUpperCase() + " and me.codmetodo=mt.codmetodo"
						    + " and mt.descripcion='" + nombreMetodo.trim() + "'";
		ResultSet rs = Conexiones.realizarConsulta(sentencia,true);
		try {
			if(rs.first()) {
				edoFinal = rs.getString("edofinal").toUpperCase();
			} else {
				throw (new MaquinaDeEstadoExcepcion("Estado de Caja Incorrecto"));
			}
		} catch (SQLException ex) {
			logger.error("obtenerEdoFinal(String, String)", ex);

			throw (new BaseDeDatosExcepcion("Falló la conexión con la base de datos al validar ejecución de Funciones", ex));
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				rs = null;
			}
		}
		//Auditoria.registrarAuditoria("Estado actual de la caja -> "+Sesion.getCaja().getEstado()+". Estado final de la caja -> " + edoFinal,'O');

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerEdoFinal(String, String) - end");
		}
		return edoFinal;
	}
	
	/**
	 * Método obtenerDatosTienda.
	 * 		Obtiene los datos de la tienda.
	 * @return ResultSet - El registro conexion los datos de la tienda
	 * @throws BaseDeDatosExcepcion - Cuando ocurre un error conexion la realizacion de la consulta
	 */
/*	public static ResultSet obtenerDatosTienda(boolean local) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerDatosTienda() - start");
		}

		String sentenciaSQL = "select * from tienda t, region r where t.codregion = r.codregion order by t.numtienda";
		ResultSet rs = Conexiones.realizarConsulta(sentenciaSQL,local);
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerDatosTienda() - end");
		}
		return rs;
	}

	/**
	 * Método obtenerPublicidadTienda.
	 * 		Obtiene las publicidades de la tienda.
	 * @return ResultSet - El registro conexion los datos de la tienda
	 * @throws BaseDeDatosExcepcion - Cuando ocurre un error conexion la realizacion de la consulta
	 */
/*	public static ResultSet obtenerPublicidadTienda(boolean local) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerPublicidadTienda() - start");
		}
		String sentenciaSQL = "select * from tiendapublicidad";
		ResultSet rs = Conexiones.realizarConsulta(sentenciaSQL,local);
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerPublicidadTienda() - end");
		}
		return rs;
	}

	/**
	 * Método obtenerPublicidadTienda.
	 * 		Obtiene las publicidades de la Tienda.
	 * @return ResultSet - El registro conexion con los datos de la publicidad de la Tienda
	 * @throws BaseDeDatosExcepcion - Cuando ocurre un error conexion la realizacion de la consulta
	 */
/*	public static ResultSet obtenerPublicidadTienda() throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerPublicidadTienda() - start");
		}

		String sentenciaSQL = "select * from tiendapublicidad order by orden";
		ResultSet rs = Conexiones.realizarConsulta(sentenciaSQL,true);

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerPublicidadTienda() - end");
		}
		return rs;
	}
	
	/**
	 * Método obtenerDatosCaja.
	 * 		Obtiene los datos de la caja.
	 * @return ResultSet - El registro conexion los datos de la caja
	 * @throws BaseDeDatosExcepcion - Cuando ocurre un error conexion la realizacion de la consulta
	 */
/*	public static ResultSet obtenerDatosCaja(boolean local) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerDatosCaja() - start");
		}
		String sentenciaSQL = local ? "select * from caja, estadodecaja where caja.idestadocaja = estadodecaja.idestado" : "select * from caja";
		ResultSet rs = Conexiones.realizarConsulta(sentenciaSQL,local);
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerDatosCaja() - end");
		}
		return rs;
	}

	/**
	 * Método obtenerDatosCaja.
	 * 		Obtiene los datos de la caja.
	 * @return ResultSet - El registro conexion los datos de la caja
	 * @throws BaseDeDatosExcepcion - Cuando ocurre un error conexion la realizacion de la consulta
	 */
/*	public static ResultSet obtenerDatosCaja(boolean local, int numeroCaja) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerDatosCaja() - start");
		}
		String sentenciaSQL = local ? "select * from caja, estadodecaja where caja.idestadocaja = estadodecaja.idestado" : "select * from caja where numcaja= " + numeroCaja;
		ResultSet rs = Conexiones.realizarConsulta(sentenciaSQL,local);
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerDatosCaja() - end");
		}
		return rs;
	}
	
	/**
	 * Método obtenerNombreEstado.
	 * 		Obtiene el nombre del estado dado el identificador del estado
	 * @param estado Identificador del estado
	 * @return String - Nombre del estado.
	 * @throws BaseDeDatosExcepcion - Si ocurre un error conexion la Base de Datos o el identificador de estado es invalido
	 */
/*	public static String obtenerNombreEstado(String estado) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerNombreEstado(String) - start");
		}

		String nombreEstado = null;
		ResultSet rs = null;
		try {
			String sentenciaSQL = "select estadodecaja.descripcion from estadodecaja where estadodecaja.idestado = " + estado.toUpperCase();
			rs = Conexiones.realizarConsulta(sentenciaSQL,true);
			if(rs.first() == true) {
				nombreEstado = rs.getString("descripcion");
			} else {
				throw (new BaseDeDatosExcepcion("No se encontro el nombre del identificador de estado: " + estado));
			}
		} catch (SQLException ex) {
			logger.error("obtenerNombreEstado(String)", ex);

			throw (new BaseDeDatosExcepcion("Falla en la conexion conexion la Base de Datos en: " + Sesion.getDbUrlLocal(), ex));
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				rs = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerNombreEstado(String) - end");
		}
		return nombreEstado;
	}
	
	/**
	 * Método obtenerCodCaptura.
	 * 		Obtiene el conjunto de capturas permitidos por el sistema.
	 * @return ResultSet - El conjunto de registro de los diferentes tipos de captura
	 * @throws ExcepcionCr - Cuando ocurre un error conexion la conexion conexion la base de datos
	 */
/*	public static ResultSet obtenerCodCaptura() throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCodCaptura() - start");
		}

		String sentenciaSQL = "select * from tipocaptura";
		
		ResultSet rs = Conexiones.realizarConsulta(sentenciaSQL,true);

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCodCaptura() - end");
		}
		return rs;
	}
	
	/**
	 * Método obtenerCondVenta.
	 * 		Obtiene el conjunto de condiciones de ventas permitidas por el sistema.
	 * @return ResultSet - El conjunto de registro de las diferentes condiciones de venta
	 * @throws ExcepcionCr - Cuando ocurre un error conexion la conexion conexion la base de datos
	 */
	public static ResultSet obtenerCondVenta() throws BaseDeDatosExcepcion, ConexionExcepcion {
/*		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCondVenta() - start");
		}
*/
		String sentenciaSQL = "select * from condicionventa";
		
		ResultSet rs = Conexiones.realizarConsulta(sentenciaSQL,true);

/*		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCondVenta() - end");
		}
*/
		return rs;
	}
	
	
	/**
	 * Método obtenerCambioDelDia.
	 * 		Obtiene el conjunto de cambios validos del dia asociados a su forma de pago
	 * @return ResultSet - El conjunto de registro de las diferentes condiciones de venta
	 * @throws ExcepcionCr - Cuando ocurre un error conexion la conexion conexion la base de datos
	 */
/*	public static double obtenerCambioDelDia() throws BaseDeDatosExcepcion, ConexionExcepcion, SQLException {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCambioDelDia() - start");
		}

		String sentenciaSQL = "select * from CR.cambiodeldia as c where c.actualizacion <= CURRENT_DATE() order by  c.actualizacion desc limit 1";
		double cambioDelDia;
		ResultSet rs = Conexiones.realizarConsulta(sentenciaSQL,true);
		try {
			cambioDelDia = rs.getDouble("cambiodeldia");
		} finally {
			rs.close();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCambioDelDia() - end");
		}
		return cambioDelDia;		
	}

	/**
	 * Método actualizarRegistroCaja.
	 * 		Actualiza el numero de registro de la caja en la Base de Datos.
	 * @param numRegistro - Nuevo numero de registro
	 * @throws BaseDeDatosExcepcion - Si ocurre un error conexion la Base de Datos
	 */
/*	public static void actualizarRegistroCaja(int numRegistro) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarRegistroCaja(int) - start");
		}

		String sentenciaSQL = "update caja set caja.numregistro = " + numRegistro + " where caja.numtienda = "
						+ Sesion.getTienda().getNumero() + " and caja.numcaja = " + Sesion.getCaja().getNumero();
		Conexiones.realizarSentencia(sentenciaSQL,true);

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarRegistroCaja(int) - end");
		}
	}
	
	/**
	 * Método actualizarTransaccionCaja.
	 * 		Actualiza el numero de transaccion de la caja en la Base de Datos.
	 * @param numTrans - Nuevo numero de transaccion
	 * @throws BaseDeDatosExcepcion - Si ocurre un error conexion la Base de Datos
	 */
/*	public static void actualizarTransaccionCaja(int numTrans) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarTransaccionCaja(int) - start");
		}

		String sentenciaSQL = "update caja set caja.numtransaccion = " + numTrans + " where caja.numtienda = "
						+ Sesion.getTienda().getNumero() + " and caja.numcaja = " + Sesion.getCaja().getNumero();
		Conexiones.realizarSentencia(sentenciaSQL,true);

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarTransaccionCaja(int) - end");
		}
	}

	/**
	 * Method obtenerCargos.
	 * 		Obtiene los cargos existentes en el sistema.
	 * @return ResultSet - Contiene todos los cargos
	 * @throws BaseDeDatosExcepcion Si ocurre un error de conexion con la Base de Datos
	 */
/*	public static ResultSet obtenerCargos() throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCargos() - start");
		}

		String sentenciaSQL = "select * from cargo";
		
		ResultSet rs = Conexiones.realizarConsulta(sentenciaSQL,true);

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCargos() - end");
		}
		return rs;
	}

	/**
	 * Method obtenerMetModFun.
	 * 		Permite obtener el código del metodo, modulo indicados por su descripción y el código 
	 * de la función asociada a los mismos.
	 * @return Vector - Posee en la posición 0 el codMetodo, en la posicion 1 el codModulo y en 
	 * 		la posicion 2 el codFuncion
	 * @throws BaseDeDatosExcepcion - Si ocurre un error de conexion la Base de Datos
	 * @throws ExcepcionCr - Si existe un error en las descripciones indicadas como paráemtros.
	 */
/*	public static Vector obtenerMetModFun (/*String nombModulo, String nombMetodo*//*) throws FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
/*		if (logger.isDebugEnabled()) {
			logger.debug("obtenerMetModFun() - start");
		}

		/*
		 * NOTA: El query utilizado para este método puede ser optimizado para la version 4.1 de MYSQL 
		 * con la utilización de SubQueries. como la version 4.0 no lo permite se realizó de esta forma.
		 * Propuesta de sentencia: "SELECT * FROM funcionmetodos FM, funcion F WHERE FM.codmetodo = (SELECT codmetodo FROM metodos WHERE descripcion ="+ nombMetodo + ") AND FM.codmodulo = (SELECT codmodulo FROM modulos WHERE descripcion =" + nombModulo + ") AND FM.codfuncion = F.codfuncion"
		 */
		
/*		String sentenciaSQLMet = "select * from metodos where metodos.descripcion = '"+Sesion.getMetodo()+ "'";
		String sentenciaSQLMod = "select * from modulos where modulos.descripcion = '"+Sesion.getModulo()+ "'";
		int codMetodo = -1;
		int codModulo = -1;
		int codFuncion = -1;
		Vector codigos = new Vector();
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		try {
			rs = Conexiones.realizarConsulta(sentenciaSQLMet,true);
			rs2 = Conexiones.realizarConsulta(sentenciaSQLMod,true);	
			if(rs.first() && rs2.first()) {
				codMetodo = new Integer (rs.getString("codmetodo")).intValue();
				codigos.addElement(new Integer(codMetodo));
				codModulo = new Integer (rs2.getString("codmodulo")).intValue();
				codigos.addElement(new Integer(codModulo));
				String sentenciaSQLMetMod = "select * from funcionmetodos fm, funcion f where fm.codmetodo = "+codMetodo+" and fm.codmodulo = " + codModulo + " and fm.codfuncion = f.codfuncion";
				rs3 = Conexiones.realizarConsulta(sentenciaSQLMetMod,true);
				if(rs3.first()) {
					codFuncion = new Integer (rs3.getString("codfuncion")).intValue();
					codigos.addElement(new Integer(codFuncion));
				} else {
					throw (new FuncionExcepcion("Error en la descripción de la función, método y módulo", true));
				}
			} else {
				throw (new FuncionExcepcion("Error en la descripción de método y módulo especificado", true));
			}
		} catch (SQLException ex) {
			logger.error("obtenerMetModFun()", ex);

			throw (new BaseDeDatosExcepcion("Falló la conexion conexion la base de datos al validar ejecucion de Funciones", ex));
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				rs = null;
			}
			if (rs2 != null) {
				try {
					rs2.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				rs2 = null;
			}
			if (rs3 != null) {
				try {
					rs3.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				rs3 = null;
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerMetModFun() - end");
		}
	return codigos;
	}

	/**
	 * Metodo obtenerUsuarioSistema.
	 * 		Obtiene los datos del usuario del sistema.
	 * @return Usuario - Objeto de tipo usuario con los datos basicos (codigoBarra, numFicha y nivel auditoria)
	 * @throws BaseDeDatosExcepcion - Si ocurre un error de conexion la Base de Datos
	 * @throws BaseDeDatosExcepcion - Si el usuario del sistema no se encuentra registrando en la Base de Datos
	 */
/*	public static Usuario obtenerUsuarioSistema() throws UsuarioExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerUsuarioSistema() - start");
		}

		Usuario resultado;
		ResultSet usuario = Conexiones.realizarConsulta("select * from usuario where usuario.numficha = 0 and usuario.numtienda=" + Sesion.getTienda().getNumero(),true);
		resultado = new Usuario();
		try {
			usuario.first();
			resultado.setCodigoBarra(usuario.getString("codigobarra"));
			resultado.setNumFicha(usuario.getString("numficha"));
			resultado.setNivelAuditoria(usuario.getString("nivelauditoria"));
			usuario.close();
		} catch (Exception ex) {
			logger.error("obtenerUsuarioSistema()", ex);

			throw (new UsuarioExcepcion("No se encontro el usuario del sistema en: " + Sesion.getDbUrlLocal(), ex));
		} finally {
			if (usuario != null) {
				try {
					usuario.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				usuario = null;
			}
		}


		if (logger.isDebugEnabled()) {
			logger.debug("obtenerUsuarioSistema() - end");
		}
		return resultado;
	}

	/**
	 * Método obtenerNumServicioCajaCentral
	 * 
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 * @return int
	 */
/*	public static int obtenerNumServicioCajaCentral(String tipoServicio) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerNumServicioCajaCentral(String) - start");
		}

		String sentenciaSQL = "select tiposervicio.correlativo from tiposervicio where tiposervicio.codtiposervicio = '" + tipoServicio + "'";
		ResultSet rs = Conexiones.realizarConsulta(sentenciaSQL,false);
		try {
			if (rs.first()) {
				sentenciaSQL = "update tiposervicio set tiposervicio.correlativo = " + (rs.getInt("correlativo")+1) + " where tiposervicio.codtiposervicio = '"
						+ tipoServicio + "'";
				Conexiones.realizarSentencia(sentenciaSQL,false);
				int returnint = (rs.getInt("correlativo") + 1);
				if (logger.isDebugEnabled()) {
					logger.debug("obtenerNumServicioCajaCentral(String) - end");
				}
				return returnint;
			} else
				throw new BaseDeDatosExcepcion("Error al obtener correlativo de servicio");
		} catch (SQLException e) {
			logger.error("obtenerNumServicioCajaCentral(String)", e);

			throw new BaseDeDatosExcepcion("Error al obtener correlativo de servicio");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				rs = null;
			}
		}

	}

	/**
	 * Método buscarElemento. Busca un elemento en la base de datos para el modulo de consultas
	 * @param item Cadena de caracteres que se quiere buscar
	 * @param campoBusqueda Campo sobre el cual se está realizando la búsqueda
	 * @param limiteInf Número a partir del cual se buscarán los resultados
	 * @param tipo Tipo de búsqueda. 1- Producto, 2- Cliente
	 * @param nombreColumna Nombre que se colocará en la tabla resultado del parámetro de búsqueda
	 * @return Vector - Un vector de 3 posiciones donde:
	 * 				Posición 0: Un String[] con los títulos de la tabla resultado.
	 * 				Posición 1: Un Class[] con los tipos de datos de cada columna de la tabla resultado.
	 * 				Posición 2: Un Object[][] con la matriz de resultados (Filas y Campos)
	 */
/*	public static Vector buscarElemento(String item, String campoBusqueda, int limiteInf, int tipo, String nombreColumna) {
		if (logger.isDebugEnabled()) {
			logger
					.debug("buscarElemento(String, String, int, int, String) - start");
		}

		String titulos[] = null;
		Class tipos[] = null;
		Object datos[][] = null;
		ResultSet resultado = null;
		int numColumnas = 0;
		int numFilas = 0;
		String consulta;
		item = item.replaceAll("%","\\%");
		item = item.replaceAll("\\'","\\\\'");
		item = item.replaceAll("\\\\\\*","%%");
		item = item.replaceAll("\\*","%");
		item = item.replaceAll("%%","\\*");
		switch (tipo) {
			case 1: // Consulta de Productos
					if (campoBusqueda.equalsIgnoreCase("codexterno")) {
						numColumnas = 3;
						consulta = "select p.codproducto, p.descripcionlarga, " + campoBusqueda.toLowerCase() 
							+ " from producto p inner join prodcodigoexterno pe on (p.codproducto = pe.codproducto) " +
							"where p.estadoproducto = '" + Sesion.ACTIVO + "' and " + campoBusqueda.toLowerCase() 
							+ " like '"+ item +"%' limit "+ limiteInf+",10";
					} else if (campoBusqueda.equalsIgnoreCase("d.nombre")) {
						numColumnas = 3;
						consulta = "select p.codproducto, p.descripcionlarga, " + campoBusqueda.toLowerCase() 
							+ " from producto p inner join departamento d on (p.coddepartamento = d.coddepartamento)  " +
							"inner join lineaseccion l on (p.codlineaseccion = l.codseccion  and p.coddepartamento = l.coddepartamento) " +
							"where p.estadoproducto = '" + Sesion.ACTIVO + "' and " + campoBusqueda.toLowerCase() 
							+ " like '"+ item +"%' limit "+ limiteInf+",10";
					} else if (campoBusqueda.equalsIgnoreCase("l.nombre")) {
						numColumnas = 3;
						consulta = "select p.codproducto, p.descripcionlarga, " + campoBusqueda.toLowerCase() 
							+ " from producto p inner join lineaseccion l on (p.codlineaseccion = l.codseccion  " +
							"and p.coddepartamento = l.coddepartamento) " +
							"where p.estadoproducto = '" + Sesion.ACTIVO + "' and " + campoBusqueda.toLowerCase() 
							+ " like '"+ item +"%'  limit "+ limiteInf+",10";
					} else if (campoBusqueda.equalsIgnoreCase("marca")) {
						numColumnas = 3;
						consulta = "select p.codproducto, p.descripcionlarga, " + campoBusqueda.toLowerCase() 
							+ " from producto p where p.estadoproducto = '" + Sesion.ACTIVO + "' and " + campoBusqueda.toLowerCase() 
							+ " like '"+ item +"%' order by " + campoBusqueda.toLowerCase() 
							+ " limit "+ limiteInf+",10";
					} else if (campoBusqueda.equalsIgnoreCase("modelo")) {
						numColumnas = 3;
						consulta = "select p.codproducto, p.descripcionlarga, " + campoBusqueda.toLowerCase() 
							+ " from producto p where p.estadoproducto = '" + Sesion.ACTIVO + "' and " + campoBusqueda.toLowerCase() 
							+ " like '"+ item +"%' order by " + campoBusqueda.toLowerCase() 
							+ " limit "+ limiteInf+",10";
					} else if (campoBusqueda.equalsIgnoreCase("referenciaproveedor")) {
						numColumnas = 3;
						consulta = "select p.codproducto, p.descripcionlarga, " + campoBusqueda.toLowerCase() 
							+ " from producto p where p.estadoproducto = '" + Sesion.ACTIVO + "' and " + campoBusqueda.toLowerCase() 
							+ " like '"+ item +"%' order by " + campoBusqueda.toLowerCase() 
							+ " limit "+ limiteInf+",10";
					} else if(campoBusqueda.equalsIgnoreCase("descripcioncorta")){
							numColumnas = 3;
							consulta = "select p.codproducto, p.descripcionlarga, p.descripcioncorta from producto p "
								+ "where (p." + campoBusqueda.toLowerCase() 
								+ " like '" + item + "%' or p.descripcionlarga like '" + item + "%') and p.estadoproducto = '" + Sesion.ACTIVO 
								+ "' order by p." + campoBusqueda.toLowerCase() 
								+ " limit " + limiteInf + ",10";
					} else { // Consulta por codigo 
							numColumnas = 2;
							consulta = "select p.codproducto, p.descripcionlarga from producto p "
								+ "where p.estadoproducto = '" + Sesion.ACTIVO + "' and p." + campoBusqueda.toLowerCase() 
								+ " like '" + item + "%' order by p.codproducto " +
								"limit " + limiteInf + ",10";
					}
		
					// Creamos los títulos
					titulos = new String[numColumnas];
					titulos[0] = "Código";
					titulos[1] = "Descripción";
					if (numColumnas>2) titulos[2] = nombreColumna;
							
					// Creamos los tipos de datos
					tipos = new Class[numColumnas];
					for (int i=0; i<numColumnas; i++)
						tipos[i] = String.class;
								
					try {
						resultado = Conexiones.realizarConsulta(consulta,true);
					} catch (BaseDeDatosExcepcion e) {
						logger
								.error(
										"buscarElemento(String, String, int, int, String)",
										e);
					} catch (ConexionExcepcion e) {
						logger
								.error(
										"buscarElemento(String, String, int, int, String)",
										e);
					}
							
					try {
						// Creamos los datos
						resultado.last();
						numFilas = resultado.getRow();
						datos = new Object[numFilas][numColumnas];
						resultado.beforeFirst();
						int longitud = 0;
						StringBuffer codProducto = new StringBuffer();
						while (resultado.next()){
							codProducto = new StringBuffer(resultado.getString(1));
							longitud = codProducto.length();
							if ((Sesion.getLongitudCodigoInterno()>-1) && (longitud>Sesion.getLongitudCodigoInterno())) {
								codProducto = new StringBuffer(codProducto.substring(codProducto.length() - Sesion.getLongitudCodigoInterno())); 
								for (int k=0;k<longitud-Sesion.getLongitudCodigoInterno();k++) {
									codProducto = new StringBuffer(" " + codProducto); 
								}
							}
							datos [resultado.getRow()-1][0] = codProducto;
							for (int i=1;i<numColumnas;i++) {
								datos [resultado.getRow()-1][i] = resultado.getString(i+1);
							}
						}	
					} catch (Exception e) {
						logger
								.error(
										"buscarElemento(String, String, int, int, String)",
										e);
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

					break;
			case 2: // Consulta de Clientes
					numColumnas = 2;
					titulos = new String[]{"Cod. Afiliado","Nombre"};
					tipos = new Class[]{String.class,String.class};
					if (campoBusqueda.equalsIgnoreCase("direccion")) {
						consulta = "select afiliado.codafiliado,afiliado.nombre,afiliado.direccion,afiliado.codArea,afiliado.numtelefono" 
								+ " from afiliado where afiliado." + campoBusqueda.toLowerCase() + " like '%" + item 
								+ "%' order by afiliado." + campoBusqueda.toLowerCase() + " limit " + limiteInf + ",10";
					} else {
						consulta = "select afiliado.codafiliado,afiliado.nombre" 
								+ " from afiliado where afiliado." + campoBusqueda.toLowerCase() + " like '" + item 
								+ "%' order by afiliado." + campoBusqueda.toLowerCase() + " limit " + limiteInf + ",10";
					}
					try {
						resultado = Conexiones.realizarConsulta(consulta,true);
					} catch (BaseDeDatosExcepcion e) {
						logger
								.error(
										"buscarElemento(String, String, int, int, String)",
										e);
					} catch (ConexionExcepcion e) {
						logger
								.error(
										"buscarElemento(String, String, int, int, String)",
										e);
					}
							
					try {
						// Creamos los datos
						resultado.last();
						numFilas = resultado.getRow();
						datos = new Object[numFilas][numColumnas];
						resultado.beforeFirst();
						while (resultado.next()) {
							for (int i=0;i<numColumnas;i++) {
								/*if (i==numColumnas-1)
									datos[resultado.getRow()-1][i] = (String)resultado.getString(i+1) + (String)resultado.getString(i+2);
								else*/
/*									datos[resultado.getRow()-1][i] = resultado.getString(i+1);
							}
						}
					} catch (Exception e) {
						logger
								.error(
										"buscarElemento(String, String, int, int, String)",
										e);
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
					break;
		}
				
		Vector datosRetorno = new Vector();
		datosRetorno.addElement(titulos);
		datosRetorno.addElement(tipos);
		datosRetorno.addElement(datos);
				
		if (logger.isDebugEnabled()) {
			logger
					.debug("buscarElemento(String, String, int, int, String) - end");
		}
		return datosRetorno;
	}
	
	/**
	* Obtiene un objeto Cliente con los datos del cliente que se encuentra registrado en la BD
	* @param codigo Codigo del cliente a buscar.
	* @param local Boolean que indica si se busca en la BD local o remota.
	* @return Cliente - El cliente encontrado en la base de datos.
	* @throws BaseDeDatosExcepcion Si ocurrio una falla en la conexion con la base de datos.
	* 
	* 
	*/
	
/*	public static ResultSet buscarCliente(String codigo, boolean local) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarCliente(String, boolean) - start");
		}

		ResultSet rs = null;
		String sentenciaSQL; 
		try {
			sentenciaSQL = "select * from afiliado where afiliado.codafiliado='V-"+ Control.completarCodigo(codigo) +"'";
			rs = Conexiones.realizarConsulta(sentenciaSQL,true);
			if (rs.first()) {
				if (logger.isDebugEnabled()) {
					logger.debug("buscarCliente(String, boolean) - end");
				}
				return rs;
			}
			sentenciaSQL = "select * from afiliado where afiliado.codafiliado='"+ codigo +"'";
			rs = Conexiones.realizarConsulta(sentenciaSQL,true);
			if (rs.first()) {
				if (logger.isDebugEnabled()) {
					logger.debug("buscarCliente(String, boolean) - end");
				}
				return rs;
			}
			sentenciaSQL = "select * from afiliado where afiliado.codafiliado='V"+ codigo +"'";
			rs = Conexiones.realizarConsulta(sentenciaSQL,true);
			if (rs.first()) {
				if (logger.isDebugEnabled()) {
					logger.debug("buscarCliente(String, boolean) - end");
				}
				return rs;
			}
			sentenciaSQL = "select * from afiliado where afiliado.codafiliado='J"+ codigo +"'";
			rs = Conexiones.realizarConsulta(sentenciaSQL,true);
			if (rs.first()) {
				if (logger.isDebugEnabled()) {
					logger.debug("buscarCliente(String, boolean) - end");
				}
				return rs;
			}
			sentenciaSQL = "select * from afiliado where afiliado.codafiliado='E"+ Control.completarCodigo(codigo) +"'";
			rs = Conexiones.realizarConsulta(sentenciaSQL,true);
			if (rs.first()) {
				if (logger.isDebugEnabled()) {
					logger.debug("buscarCliente(String, boolean) - end");
				}
				return rs;
			}
			sentenciaSQL = "select * from afiliado where afiliado.codafiliado='G"+ Control.completarCodigo(codigo) +"'";
			rs = Conexiones.realizarConsulta(sentenciaSQL,true);
			if (rs.first()) {
				if (logger.isDebugEnabled()) {
					logger.debug("buscarCliente(String, boolean) - end");
				}
				return rs;
			}
			sentenciaSQL = "select * from afiliado where afiliado.codafiliado='D"+ Control.completarCodigo(codigo) +"'";
			rs = Conexiones.realizarConsulta(sentenciaSQL,true);
			if (rs.first()) {
				if (logger.isDebugEnabled()) {
					logger.debug("buscarCliente(String, boolean) - end");
				}
				return rs;
			}
			if (codigo.length() <= 8) {
				StringBuffer sb = new StringBuffer(codigo);
				while (sb.length() < 8) {
					sb.insert(0, '0');
				}
				sentenciaSQL = "select * from afiliado where afiliado.numficha='"+ sb.toString() + "'";
				rs = Conexiones.realizarConsulta(sentenciaSQL,true);
				if (rs.first()) {
					if (logger.isDebugEnabled()) {
						logger.debug("buscarCliente(String, boolean) - end");
					}
					return rs;
				}
			}
			
		} catch (SQLException e) {
			logger.error("buscarCliente(String, boolean)", e);

			throw new BaseDeDatosExcepcion("Error al buscar cliente",e);
		} 

		if (logger.isDebugEnabled()) {
			logger.debug("buscarCliente(String, boolean) - end");
		}
		return rs;
	
	}
	
	/**
	* Obtiene un objeto Cliente con los datos del cliente que se encuentra registrado en la BD
	* @param codigo Codigo del cliente a buscar. Método creado para las búsquedas específicas de BECO.
	* @return Cliente - El cliente encontrado en la base de datos.
	* @throws BaseDeDatosExcepcion Si ocurrio una falla en la conexion con la base de datos.
	* 
	* 
	*/
	
/*	public static ResultSet buscarCliente(String codigo) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarCliente(String) - start");
		}

		ResultSet rs = null;
		String sentenciaSQL; 
		try {
			if (codigo.length() <= 8) {
				StringBuffer sb = new StringBuffer(codigo);
				while (sb.length() < 8) {
					sb.insert(0, '0');
				}
				sentenciaSQL = "select * from afiliado where afiliado.numficha='"+ sb.toString() +"' and estadocolaborador = '" + Sesion.ACTIVO + "'";
				rs = Conexiones.realizarConsulta(sentenciaSQL,true);
				if (rs.first()) {
					if (logger.isDebugEnabled()) {
						logger.debug("buscarCliente(String) - end");
					}
					return rs;
				}
			}
			sentenciaSQL = "select * from afiliado where afiliado.codafiliado='N-"+ codigo +"'";
			rs = Conexiones.realizarConsulta(sentenciaSQL,true);
			if (rs.first()) {
				if (logger.isDebugEnabled()) {
					logger.debug("buscarCliente(String) - end");
				}
				return rs;
			}
			sentenciaSQL = "select * from afiliado where afiliado.codafiliado='V-"+ codigo +"'";
			rs = Conexiones.realizarConsulta(sentenciaSQL,true);
			if (rs.first()) {
				if (logger.isDebugEnabled()) {
					logger.debug("buscarCliente(String) - end");
				}
				return rs;
			}
			sentenciaSQL = "select * from afiliado where afiliado.codafiliado='"+ codigo +"'";
			rs = Conexiones.realizarConsulta(sentenciaSQL,true);
			if (rs.first()) {
				if (logger.isDebugEnabled()) {
					logger.debug("buscarCliente(String) - end");
				}
				return rs;
			}
			sentenciaSQL = "select * from afiliado where afiliado.codafiliado='J-"+ codigo +"'";
			rs = Conexiones.realizarConsulta(sentenciaSQL,true);
			if (rs.first()) {
				if (logger.isDebugEnabled()) {
					logger.debug("buscarCliente(String) - end");
				}
				return rs;
			}
			sentenciaSQL = "select * from afiliado where afiliado.codafiliado='E-"+ codigo +"'";
			rs = Conexiones.realizarConsulta(sentenciaSQL,true);
			if (rs.first()) {
				if (logger.isDebugEnabled()) {
					logger.debug("buscarCliente(String) - end");
				}
				return rs;
			}
			sentenciaSQL = "select * from afiliado where afiliado.codafiliado='G-"+ codigo +"'";
			rs = Conexiones.realizarConsulta(sentenciaSQL,true);
			if (rs.first()) {
				if (logger.isDebugEnabled()) {
					logger.debug("buscarCliente(String) - end");
				}
				return rs;
			}
			sentenciaSQL = "select * from afiliado where afiliado.codafiliado='D-"+ codigo +"'";
			rs = Conexiones.realizarConsulta(sentenciaSQL,true);
			if (rs.first()) {
				if (logger.isDebugEnabled()) {
					logger.debug("buscarCliente(String) - end");
				}
				return rs;
			}

			
		} catch (SQLException e) {
			logger.error("buscarCliente(String)", e);

			throw new BaseDeDatosExcepcion("Error al buscar cliente",e);
		} 

		if (logger.isDebugEnabled()) {
			logger.debug("buscarCliente(String) - end");
		}
		return rs;
	
	}
	
	public static ArrayList obtenerMensajesCliente(String codCliente) throws ConexionExcepcion, BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerMensajesCliente(String) - start");
		}

		ArrayList mensajes = new ArrayList();
		ResultSet rs = null;
		try {
			String sentenciaSQL = new String("select mensaje, monto from detalleafiliado where (codafiliado = '"+codCliente+"') and ((notificado = '"+Sesion.NO+"') or (monto > 0))");
			rs = Conexiones.realizarConsulta(sentenciaSQL,true);
			rs.beforeFirst();
			StringBuffer mensaje = new StringBuffer();
			double monto = 0;
			while(rs.next()){
				mensaje = new StringBuffer(rs.getString("mensaje"));
				monto = rs.getDouble("monto");
				if(monto > 0)
					mensaje.append(" "+monto);
				mensajes.add(mensaje);
			}
		} catch (SQLException e) {
			logger.error("obtenerMensajesCliente(String)", e);

			throw new BaseDeDatosExcepcion("Error al obtener mensajes de cliente", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				rs = null;
			}
		}


		if (logger.isDebugEnabled()) {
			logger.debug("obtenerMensajesCliente(String) - end");
		}
		return mensajes;
	}
	
	public static void iniciarBuscador() {
		if (logger.isDebugEnabled()) {
			logger.debug("iniciarBuscador() - start");
		}

		BuscadorClienteFactory  factory = new BuscadorClienteFactory();
		buscadorCliente = factory.getBuscadorInstance();		

		if (logger.isDebugEnabled()) {
			logger.debug("iniciarBuscador() - end");
		}
	}
	
	public static Cliente obtenerCliente(String codigo) throws ConexionExcepcion, AfiliadoUsrExcepcion, BaseDeDatosExcepcion, ClienteExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCliente(String) - start");
		}

		Cliente clienteRetorno = null;
		// Atributos del cliente a cargar de la BD
		String codCliente;
			
		try {
			if (buscadorCliente == null) {
				iniciarBuscador();
			}
			long inicio = new Date().getTime();
			if (logger.isDebugEnabled()) {
				logger
						.debug("obtenerCliente(String) - Empieza a buscar cliente");
			}
			clienteRetorno = buscadorCliente.buscarCliente(codigo);
			long fin = new Date().getTime();
			if (logger.isDebugEnabled()) {
				logger
						.debug("obtenerCliente(String) - Finaliza busqueda. Tarda "
								+ (fin - inicio) + " milisegundos");
			}
			if(clienteRetorno == null) {
				throw (new ClienteExcepcion("Cliente inexistente"));
			}
		} catch (BaseDeDatosExcepcion ex) {
			logger.error("obtenerCliente(String)", ex);

			ex.setMensaje("Fallo la conexion con la base de datos al obtener datos del cliente");
			throw ex;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCliente(String) - end");
		}
		return clienteRetorno;
	}

	/**
	 * Método setClienteNotificado
	 * 
	 * @param codCliente
	 */
/*	public static void setClienteNotificado(String codCliente){
		if (logger.isDebugEnabled()) {
			logger.debug("setClienteNotificado(String) - start");
		}

		try {
			String sentenciaSQL = new String("update detalleafiliado set notificado = '"+Sesion.SI+"', actualizacion = "+Control.formatoTiempo.format(Sesion.getTimestampSistema())+" where (codafiliado = '"+codCliente+"')");
			Conexiones.realizarSentencia(sentenciaSQL,true);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("setClienteNotificado(String)", e);

			new BaseDeDatosExcepcion("Fallo la conexion con la base de datos al obtener datos del cliente", e);
		} catch (ConexionExcepcion e) {
			logger.error("setClienteNotificado(String)", e);

			new ConexionExcepcion("Falla la conexion con la base de datos al acctualizar mensajes notificados al cliente");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setClienteNotificado(String) - end");
		}
	}
	
	/**
	 * Método asignarUsuarioLogueado
	 * 
	 * @param usuario
	 */
/*	public static void asignarUsuarioLogueado(String usuario) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("asignarUsuarioLogueado(String) - start");
		}

		asignarUsuarioLogueado(usuario, false);

		if (logger.isDebugEnabled()) {
			logger.debug("asignarUsuarioLogueado(String) - end");
		}
	}

	/**
	 * Método asignarUsuarioLogueado
	 * 
	 * @param usuario - identificador del usuario logueado.
	 * @param cierreCajero - Indica si el usuario abrió la caja para facturación.
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
/*	public static void asignarUsuarioLogueado(String usuario, boolean cierreCajero) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("asignarUsuarioLogueado(String, boolean) - start");
		}
		ResultSet datosCaja = null;
		try{
			datosCaja = MediadorBD.obtenerDatosCaja(true);
			String usuarioLogueado = datosCaja.getString("codusuario");
			if((usuario != null) && (!usuarioLogueado.equals(usuario))){
				char cerrarCajero = cierreCajero == false ? Sesion.NO : Sesion.SI;  
				setCierreCajero(cerrarCajero);
			}
		} catch (SQLException e) {
			logger.error("asignarUsuarioLogueado(String, boolean)", e);
		} finally {
			if (datosCaja != null) {
				try {
					datosCaja.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				datosCaja = null;
			}
		}


		String codUsuario = usuario != null ? usuario : "";
		setUsuarioLogueado(codUsuario);

		// Abrimos la gaveta
		try{
			if (CR.me != null)
				CR.me.abrirGaveta(false); 
		} catch(Exception ex){
			logger.error("asignarUsuarioLogueado(String, boolean)", ex);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("asignarUsuarioLogueado(String, boolean) - end");
		}
	}

	/**
	 * Método obtenerTransaccionesDeVenta
	 * 
	 * @param usuario Numero de ficha del usuario que se quiere las ventas
	 * @param fecha Fecha desde la cual se quiere el reporte
	 * @param tipoTrans tipo de transaccion a recuperar (Venta, Anulacion,Devolucion)
	 * @return double[] - MontoVendidoConImpuestos, MontoImpuesto, MontoVendidoExento.
	 */
/*	public static double[] obtenerTransacciones(String usuario, Date fecha, int caja, char tipoTrans) {
		if (logger.isDebugEnabled()) {
			logger
					.debug("obtenerTransacciones(String, Date, int, char) - start");
		}

		double[] datosVenta = {0.0,0.0,0.0};
		SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy/MM/dd");
		String sentenciaSQL = "select sum(d.cantidad*d.preciofinal), sum(d.cantidad*d.montoimpuesto) from detalletransaccion d,transaccion t where t.codcajero='" + usuario 
			+ "' and t.fecha='" + fechaTrans.format(fecha) + "' and t.tipotransaccion='" + tipoTrans
			+ "' and t.numcajafinaliza = " + caja + " and t.estadotransaccion = '" + Sesion.FINALIZADA + "' and d.montoimpuesto>0 and "
			+ "t.numtienda=d.numtienda and t.fecha=d.fecha and t.numtransaccion=d.numtransaccion and t.numcajafinaliza=d.numcajafinaliza";
		ResultSet resultado = null;
		try {
			resultado = Conexiones.realizarConsulta(sentenciaSQL, true);
			datosVenta[0] = resultado.getDouble(1);
			datosVenta[1] = resultado.getDouble(2);
			sentenciaSQL = "select sum(d.cantidad*d.preciofinal) from detalletransaccion d,transaccion t where t.codcajero='" + usuario 
				+ "' and t.fecha='" + fechaTrans.format(fecha) + "' and t.tipotransaccion='" + tipoTrans
				+ "' and t.numcajafinaliza = " + caja + " and t.estadotransaccion = '" + Sesion.FINALIZADA + "' and d.montoimpuesto=0 and "
				+ "t.numtienda=d.numtienda and t.fecha=d.fecha and t.numtransaccion=d.numtransaccion and t.numcajafinaliza=d.numcajafinaliza";
			resultado = Conexiones.realizarConsulta(sentenciaSQL, true);
			datosVenta[2] = resultado.getDouble(1);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("obtenerTransacciones(String, Date, int, char)", e);
		} catch (ConexionExcepcion e) {
			logger.error("obtenerTransacciones(String, Date, int, char)", e);
		} catch (SQLException e) {
			logger.error("obtenerTransacciones(String, Date, int, char)", e);
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

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTransacciones(String, Date, int, char) - end");
		}
		return datosVenta;
	}

	/**
	 * Método obtenerTransaccionesDeVenta
	 * 
	 * @param fecha Fecha desde la cual se quiere el reporte
	 * @param caja
	 * @param tipoTrans tipo de transaccion a recuperar (Venta, Anulacion,Devolucion)
	 * @return double[] - MontoVendidoConImpuestos, MontoImpuesto, MontoVendidoExento.
	 */
/*	public static double[] obtenerTransacciones(Date fecha, int caja, char tipoTrans) {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTransacciones(Date, int, char) - start");
		}

		double[] datosVenta = {0.0,0.0,0.0};
		SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy/MM/dd");
		String sentenciaSQL = "select sum(d.cantidad*d.preciofinal), sum(d.cantidad*d.montoimpuesto) from detalletransaccion d,transaccion t where " 
			+ "t.fecha>'" + fechaTrans.format(fecha) + "' and t.tipotransaccion='" + tipoTrans
			+ "' and t.numcajafinaliza = " + caja + " and t.estadotransaccion = '" + Sesion.FINALIZADA + "' and d.montoimpuesto>0 and "
			+ "t.numtienda=d.numtienda and t.fecha=d.fecha and t.numtransaccion=d.numtransaccion and t.numcajafinaliza=d.numcajafinaliza";
		ResultSet resultado = null;
		try {
			resultado = Conexiones.realizarConsulta(sentenciaSQL, true);
			datosVenta[0] = resultado.getDouble(1);
			datosVenta[1] = resultado.getDouble(2);
			sentenciaSQL = "select sum(d.cantidad*d.preciofinal) from detalletransaccion d,transaccion t where " 
				+ "t.fecha>'" + fechaTrans.format(fecha) + "' and t.tipotransaccion='" + tipoTrans
				+ "' and t.numcajafinaliza = " + caja + " and t.estadotransaccion = '" + Sesion.FINALIZADA + "' and d.montoimpuesto=0 and "
				+ "t.numtienda=d.numtienda and t.fecha=d.fecha and t.numtransaccion=d.numtransaccion and t.numcajafinaliza=d.numcajafinaliza";
			resultado = Conexiones.realizarConsulta(sentenciaSQL, true);
			datosVenta[2] = resultado.getDouble(1);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("obtenerTransacciones(Date, int, char)", e);
		} catch (ConexionExcepcion e) {
			logger.error("obtenerTransacciones(Date, int, char)", e);
		} catch (SQLException e) {
			logger.error("obtenerTransacciones(Date, int, char)", e);
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


		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTransacciones(Date, int, char) - end");
		}
		return datosVenta;
	}

	/**
	 * Método obtenerTransaccionesDeVenta
	 * 
	 * @param usuario Numero de ficha del usuario que se quiere las ventas
	 * @param fecha Fecha desde la cual se quiere el reporte
	 * @param tipoTrans tipo de transaccion a recuperar (Venta, Anulacion,Devolucion)
	 * @return double[] - MontoVendidoConImpuestos, MontoImpuesto, MontoVendidoExento.
	 */
/*	public static Vector obtenerNumeroDeTransacciones(String usuario, Date fecha, int caja, char tipoTrans) {
		if (logger.isDebugEnabled()) {
			logger
					.debug("obtenerNumeroDeTransacciones(String, Date, int, char) - start");
		}

		Vector transacciones = new Vector();
		SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy/MM/dd");
		String sentenciaSQL = "select t.numtransaccion from transaccion t where t.codcajero='" + usuario 
			+ "' and t.fecha='" + fechaTrans.format(fecha) + "' and t.tipotransaccion='" + tipoTrans
			+ "' and t.numcajafinaliza = " + caja + " and t.estadotransaccion = '" + Sesion.FINALIZADA + "' order by t.numtransaccion";
		ResultSet resultado = null;
		try {
			resultado = Conexiones.realizarConsulta(sentenciaSQL, true);
			resultado.beforeFirst();
			while (resultado.next()) {
				transacciones.addElement(new Integer(resultado.getInt(1)));
			}
		} catch (BaseDeDatosExcepcion e) {
			logger.error(
					"obtenerNumeroDeTransacciones(String, Date, int, char)", e);
		} catch (ConexionExcepcion e) {
			logger.error(
					"obtenerNumeroDeTransacciones(String, Date, int, char)", e);
		} catch (SQLException e) {
			logger.error(
					"obtenerNumeroDeTransacciones(String, Date, int, char)", e);
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


		if (logger.isDebugEnabled()) {
			logger
					.debug("obtenerNumeroDeTransacciones(String, Date, int, char) - end");
		}
		return transacciones;
	}	

	/**
	 * Método obtenerTransaccionesDeVenta
	 * 
	 * @param fecha Fecha desde la cual se quiere el reporte
	 * @param tipoTrans tipo de transaccion a recuperar (Venta, Anulacion,Devolucion)
	 * @return double[] - MontoVendidoConImpuestos, MontoImpuesto, MontoVendidoExento.
	 */
/*	public static Vector obtenerNumeroDeTransacciones(Date fecha, int caja, char tipoTrans) {
		if (logger.isDebugEnabled()) {
			logger
					.debug("obtenerNumeroDeTransacciones(Date, int, char) - start");
		}

		Vector transacciones = new Vector();
		SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy/MM/dd");
		String sentenciaSQL = "select t.numtransaccion from transaccion t where t.fecha>'" + 
			fechaTrans.format(fecha) + "' and t.tipotransaccion='" + tipoTrans +
			"' and t.numcajafinaliza = " + caja + " and t.estadotransaccion = '" + Sesion.FINALIZADA +
			"' order by t.numtransaccion";
		ResultSet resultado = null;
		try {
			resultado = Conexiones.realizarConsulta(sentenciaSQL, true);
			resultado.beforeFirst();
			while (resultado.next()) {
				transacciones.addElement(new Integer(resultado.getInt(1)));
			}
		} catch (BaseDeDatosExcepcion e) {
			logger.error("obtenerNumeroDeTransacciones(Date, int, char)", e);
		} catch (ConexionExcepcion e) {
			logger.error("obtenerNumeroDeTransacciones(Date, int, char)", e);
		} catch (SQLException e) {
			logger.error("obtenerNumeroDeTransacciones(Date, int, char)", e);
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


		if (logger.isDebugEnabled()) {
			logger.debug("obtenerNumeroDeTransacciones(Date, int, char) - end");
		}
		return transacciones;
	}	

	/**
	 * Método obtenerPagosDeTransacciones
	 * 
	 * @param condWhere Clausula Where con los numero de transacciones
	 * @param codFPago Codigo De La forma de Pago
	 * @return double - Monto de esa forma de pago.
	 */
/*	public static double obtenerPagosDeTransacciones(String condWhere, String codFPago) {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerPagosDeTransacciones(String, String) - start");
		}

		double monto = 0;
		String sentenciaSQL = "select SUM(p.monto) FROM pagodetransaccion p WHERE p.codformadepago='"
				+ codFPago + "' and " + condWhere + " GROUP by p.codformadepago";
		ResultSet resultado = null;
		try {
			resultado = Conexiones.realizarConsulta(sentenciaSQL, true);
			if (resultado.first())
				monto = resultado.getDouble(1);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("obtenerPagosDeTransacciones(String, String)", e);
		} catch (ConexionExcepcion e) {
			logger.error("obtenerPagosDeTransacciones(String, String)", e);
		} catch (SQLException e) {
			logger.error("obtenerPagosDeTransacciones(String, String)", e);
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


		if (logger.isDebugEnabled()) {
			logger.debug("obtenerPagosDeTransacciones(String, String) - end");
		}
		return monto;
	}

	/**
	 * Método obtenerVueltos
	 * 
	 * @param condWhere Clausula Where con los numero de transacciones
	 * @return double - Monto en vueltos.
	 */
/*	public static double obtenerVueltos(String condWhere) {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerVueltos(String) - start");
		}

		double monto = 0;
		String sentenciaSQL = "select SUM(vueltocliente) FROM transaccion p WHERE " 
			+ condWhere;
		ResultSet resultado = null;
		try {
			resultado = Conexiones.realizarConsulta(sentenciaSQL, true);
			if (resultado.first())
				monto = resultado.getDouble(1);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("obtenerVueltos(String)", e);
		} catch (ConexionExcepcion e) {
			logger.error("obtenerVueltos(String)", e);
		} catch (SQLException e) {
			logger.error("obtenerVueltos(String)", e);
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


		if (logger.isDebugEnabled()) {
			logger.debug("obtenerVueltos(String) - end");
		}
		return monto;
	}

	/**
	 * Método obtenerTransaccionesDeVenta
	 * 
	 * @param fecha Fecha desde la cual se quiere el reporte
	 * @param caja Caja.
	 * @return int[] - Pos 0 minima, Por 1 maxima
	 */
/*	public static int[] obtenerTransaccionesDiarias(Date fecha, int caja) {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTransaccionesDiarias(Date, int) - start");
		}

		int[] numTrans = {0,0};
		SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy/MM/dd");
		String sentenciaSQL = "select t.numtransaccion from transaccion t where t.fecha>'" + fechaTrans.format(fecha)
			+ "' and t.numcajafinaliza = " + caja + " and t.estadotransaccion = '" + Sesion.FINALIZADA 
			+ "' order by t.numtransaccion";
		ResultSet resultado = null;
		try {
			resultado = Conexiones.realizarConsulta(sentenciaSQL, true);
			if (resultado.first()) {
				resultado.last();
				numTrans[1] = resultado.getInt(1);
				resultado.first();
				numTrans[0] = resultado.getInt(1)-1;
			}
		} catch (BaseDeDatosExcepcion e) {
			logger.error("obtenerTransaccionesDiarias(Date, int)", e);
		} catch (ConexionExcepcion e) {
			logger.error("obtenerTransaccionesDiarias(Date, int)", e);
		} catch (SQLException e) {
			logger.error("obtenerTransaccionesDiarias(Date, int)", e);
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


		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTransaccionesDiarias(Date, int) - end");
		}
		return numTrans;
	}

	/**
	 * Método getFechaTienda
	 * 
	 * @param BDLocal
	 * @return
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 * @throws SQLException
	 * return Date
	 */
/*	public static java.sql.Date getFechaTienda(boolean BDLocal) throws BaseDeDatosExcepcion, ConexionExcepcion, SQLException{
		if (logger.isDebugEnabled()) {
			logger.debug("getFechaTienda(boolean) - start");
		}

		java.sql.Date fecha = null;
		ResultSet resultado = Conexiones.realizarConsulta("select fechatienda from tienda", BDLocal);
		try {
			fecha = new java.sql.Date(resultado.getDate("fechatienda").getTime());
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

		if (logger.isDebugEnabled()) {
			logger.debug("getFechaTienda(boolean) - end");
		}
		return fecha;
	}

	/**
	 * Método setFechaUltReporteZ
	 * 
	 * @param fecha
	 */
/*	public static void setFechaUltReporteZ(Date fecha) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("setFechaUltReporteZ(Date) - start");
		}

		SimpleDateFormat fechaReporte = new SimpleDateFormat("yyyy/MM/dd");
		String sentenciaSQL = "update caja set caja.fechareportez = '" + fechaReporte.format(fecha) + "'";
		Conexiones.realizarSentencia(sentenciaSQL,true);

		if (logger.isDebugEnabled()) {
			logger.debug("setFechaUltReporteZ(Date) - end");
		}
	}

	/**
	 * Método setFechaTienda
	 * 
	 * @param fecha
	 */
/*	public static void setFechaTienda(java.sql.Date fecha) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("setFechaTienda(java.sql.Date) - start");
		}

		SimpleDateFormat fechaTienda = new SimpleDateFormat("yyyy-MM-dd");
		String sentenciaSQL = "update tienda set tienda.fechatienda = '" + fechaTienda.format(fecha) + "'";
		Conexiones.realizarSentencia(sentenciaSQL,true);

		if (logger.isDebugEnabled()) {
			logger.debug("setFechaTienda(java.sql.Date) - end");
		}
	}

	/**
	 * Método actualizarMontoRecaudadoCaja
	 * 
	 * @param monto
	 * void
	 */
/*	public static void actualizarMontoRecaudadoCaja(double monto, int numcaja) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarMontoRecaudadoCaja(double, int) - start");
		}

		String sentenciaSQL = "update caja set caja.montorecaudado = " + monto + " where numcaja = " + numcaja;
		try{
			Conexiones.realizarSentencia(sentenciaSQL,true);
		} catch (Exception ex) {
			logger.error("actualizarMontoRecaudadoCaja(double, int)", ex);

			Auditoria.registrarAuditoria("Falla actualizacion de monto recaudado en caja "+numcaja+" en Servidor", 'E');
		}
		if(Sesion.isCajaEnLinea()){
			try{
				Conexiones.realizarSentencia(sentenciaSQL,false);
			} catch (Exception ex) {
				logger.error("actualizarMontoRecaudadoCaja(double, int)", ex);

				try {
					Auditoria.registrarAuditoria("Falla actualizacion de monto recaudado en caja en Servidor", 'E');
				} catch (Exception e1) {
					logger.error("actualizarMontoRecaudadoCaja(double, int)", e1);
				}
			}
		}	

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarMontoRecaudadoCaja(double, int) - end");
		}
	}
	
	/**
	 * Método obtenerCodigoInterno. Obtiene el codigo interno de un producto a partir de su codigo externo
	 * 
	 * @param codExterno
	 * @return String - Codigo interno del producto
	 */
/*	public static String obtenerCodigoInterno(String codExterno) {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCodigoInterno(String) - start");
		}

		StringBuffer xCodProd1 = new StringBuffer(codExterno);
		StringBuffer xCodProd2 = new StringBuffer(codExterno);
		StringBuffer xCodProd3 = new StringBuffer(codExterno);
		int longitud = codExterno.length();
		if (longitud < Control.getLONGITUD_CODIGO()){
			for (int i=0; i<Control.getLONGITUD_CODIGO()-longitud-1; i++){
				xCodProd1.insert(0,'0');
			}
			for (int i=0; i<(Control.getLONGITUD_CODIGO()-1-Control.getFORMATO_PRODUCTO().length())-longitud; i++){
				xCodProd2.insert(0,'0');
			}
			if (xCodProd2.length() == Control.getLONGITUD_CODIGO()-1){
				xCodProd2.deleteCharAt(0);
				xCodProd2.insert(0,Control.getFORMATO_PRODUCTO());
			} else xCodProd2.insert(0,Control.getFORMATO_PRODUCTO());
			for (int i=0; i<Control.getLONGITUD_CODIGO()-longitud; i++){
				xCodProd3.insert(0,'0');
			}
		}

		String sentenciaSQL = null;
		if (Sesion.getLongitudCodigoInterno()>-1) {
			sentenciaSQL = "select prodcodigoexterno.codproducto from prodcodigoexterno where prodcodigoexterno.codexterno = '" + codExterno + "' OR " +
						   "((prodcodigoexterno.codproducto=prodcodigoexterno.codexterno)&&(substring(prodcodigoexterno.codproducto,length(prodcodigoexterno.codproducto)" +
						   "-" + (Sesion.getLongitudCodigoInterno()-1) + "," + Sesion.getLongitudCodigoInterno() + ")='" + codExterno + "')) " +
						   "or prodcodigoexterno.codproducto = '" + xCodProd1.toString() + "' or prodcodigoexterno.codproducto = '" + xCodProd2.toString() + "' or prodcodigoexterno.codproducto = '" + xCodProd3.toString() + "'";
		} else {
			sentenciaSQL = "select prodcodigoexterno.codproducto from prodcodigoexterno where prodcodigoexterno.codexterno = '" + codExterno + "'";
		}
		ResultSet resultado = null;
		try {
			resultado = Conexiones.realizarConsulta(sentenciaSQL, true);
			if(resultado.first()) {
				String returnString = resultado.getString(1).toUpperCase();
				if (logger.isDebugEnabled()) {
					logger.debug("obtenerCodigoInterno(String) - end");
				}
				return returnString;
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("obtenerCodigoInterno(String) - end");
				}
				return null;
			}
		} catch (SQLException ex) {
			logger.error("obtenerCodigoInterno(String)", ex);

			if (logger.isDebugEnabled()) {
				logger.debug("obtenerCodigoInterno(String) - end");
			}
			return null;
		} catch (BaseDeDatosExcepcion e) {
			logger.error("obtenerCodigoInterno(String)", e);

			if (logger.isDebugEnabled()) {
				logger.debug("obtenerCodigoInterno(String) - end");
			}
			return null;
		} catch (ConexionExcepcion e) {
			logger.error("obtenerCodigoInterno(String)", e);

			if (logger.isDebugEnabled()) {
				logger.debug("obtenerCodigoInterno(String) - end");
			}
			return null;
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

	}

	public static String buscarDescripcionFuncion(int codFun) throws BaseDeDatosExcepcion, ConexionExcepcion, SQLException{
		if (logger.isDebugEnabled()) {
			logger.debug("buscarDescripcionFuncion(int) - start");
		}

		String descFun = "";
		ResultSet resultado = Conexiones.realizarConsulta("select descripcion from funcion where funcion.codfuncion = " + codFun, true);
		try {
			descFun = resultado.getString("descripcion");
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


		if (logger.isDebugEnabled()) {
			logger.debug("buscarDescripcionFuncion(int) - end");
		}
		return descFun;
	}

	/**
	 * Método obtenerNombreUsuario
	 * 
	 * @param numFicha
	 * @return String
	 */
/*	public static String obtenerNombreUsuario(String numFicha) {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerNombreUsuario(String) - start");
		}

		String sentenciaSQL = "select nombre from usuario where numficha = '" + numFicha + "'";
		ResultSet resultado = null;
		try {
			resultado = Conexiones.realizarConsulta(sentenciaSQL,true);
			if (resultado.first()) {
				String returnString = resultado.getString(1);
				if (logger.isDebugEnabled()) {
					logger.debug("obtenerNombreUsuario(String) - end");
				}
				return returnString;
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("obtenerNombreUsuario(String) - end");
				}
				return null;
			}
		} catch (SQLException e) {
			logger.error("obtenerNombreUsuario(String)", e);

			if (logger.isDebugEnabled()) {
				logger.debug("obtenerNombreUsuario(String) - end");
			}
			return null;
		} catch (BaseDeDatosExcepcion e) {
			logger.error("obtenerNombreUsuario(String)", e);

			if (logger.isDebugEnabled()) {
				logger.debug("obtenerNombreUsuario(String) - end");
			}
			return null;
		} catch (ConexionExcepcion e) {
			logger.error("obtenerNombreUsuario(String)", e);

			if (logger.isDebugEnabled()) {
				logger.debug("obtenerNombreUsuario(String) - end");
			}
			return null;
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

	}

	/**
	 * Método obtenerNumTransacciones
	 * 
	 * @param usuario Numero de ficha del usuario que se quiere las ventas
	 * @param fecha Fecha desde la cual se quiere el reporte
	 * @param tipoTrans tipo de transaccion a recuperar (Venta, Anulacion,Devolucion)
	 * @return int - Numero de Transacciones.
	 */
/*	public static int obtenerNumTransacciones(String usuario, Date fecha, char tipoTrans) {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerNumTransacciones(String, Date, char) - start");
		}

		int nroTrans = 0;
		SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy/MM/dd");
		String sentenciaSQL = "select count(*) from transaccion t where t.codcajero='" + usuario 
			+ "' and t.fecha='" + fechaTrans.format(fecha) + "' and t.tipotransaccion='" + tipoTrans
			+ "' and t.numcajafinaliza = " + Sesion.getCaja().getNumero() + " and t.estadotransaccion = '" + Sesion.FINALIZADA + "'";
		ResultSet resultado = null;
		try {
			resultado = Conexiones.realizarConsulta(sentenciaSQL, true);
			resultado.first();
			nroTrans = resultado.getInt(1);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("obtenerNumTransacciones(String, Date, char)", e);
		} catch (ConexionExcepcion e) {
			logger.error("obtenerNumTransacciones(String, Date, char)", e);
		} catch (SQLException e) {
			logger.error("obtenerNumTransacciones(String, Date, char)", e);
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


		if (logger.isDebugEnabled()) {
			logger.debug("obtenerNumTransacciones(String, Date, char) - end");
		} 
		return nroTrans;
	}

	/**
	 * Método obtenerNumTransacciones
	 * 
	 * @param fecha Fecha desde la cual se quiere el reporte
	 * @param tipoTrans tipo de transaccion a recuperar (Venta, Anulacion,Devolucion)
	 * @return int - Numero de Transacciones.
	 */
/*	public static int obtenerNumTransacciones(Date fecha, char tipoTrans) {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerNumTransacciones(Date, char) - start");
		}

		int nroTrans = 0;
		SimpleDateFormat fechaTrans = new SimpleDateFormat("yyyy/MM/dd");
		String sentenciaSQL = "select count(*) from transaccion t where t.fecha>'" +
			fechaTrans.format(fecha) + "' and t.tipotransaccion='" + tipoTrans
			+ "' and t.numcajafinaliza = " + Sesion.getCaja().getNumero() + " and t.estadotransaccion = '" + Sesion.FINALIZADA + "'";
		ResultSet resultado = null;
		try {
			resultado = Conexiones.realizarConsulta(sentenciaSQL, true);
			resultado.first();
			nroTrans = resultado.getInt(1);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("obtenerNumTransacciones(Date, char)", e);
		} catch (ConexionExcepcion e) {
			logger.error("obtenerNumTransacciones(Date, char)", e);
		} catch (SQLException e) {
			logger.error("obtenerNumTransacciones(Date, char)", e);
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


		if (logger.isDebugEnabled()) {
			logger.debug("obtenerNumTransacciones(Date, char) - end");
		}
		return nroTrans;
	}

	/**
	 * Método setSerialCaja
	 * 
	 * @param serialFiscal
	 * void
	 */
/*	public static void setSerialCaja(String serialFiscal, int numCaja) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("setSerialCaja(String, int) - start");
		}

		String sentencia = "update caja set numserial='" + serialFiscal + "' where numcaja = " + numCaja;
		Conexiones.realizarSentencia(sentencia, true);

		if (logger.isDebugEnabled()) {
			logger.debug("setSerialCaja(String, int) - end");
		}
	}

	public static int getTransaccionesNoSync() {
		if (logger.isDebugEnabled()) {
			logger.debug("getTransaccionesNoSync() - start");
		}

		int nroTrans = 0;
		String sentenciaSQL = "select count(*) as cuantos from transaccion where " +
				"(transaccion.regactualizado = 'N') and " +
				"(transaccion.estadotransaccion = '" + Sesion.FINALIZADA + "')";
		ResultSet resultado = null;
		try {
			resultado = Conexiones.realizarConsulta(sentenciaSQL, true);
			resultado.first();
			nroTrans = resultado.getInt(1);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("getTransaccionesNoSync()", e);
		} catch (ConexionExcepcion e) {
			logger.error("getTransaccionesNoSync()", e);
		} catch (SQLException e) {
			logger.error("getTransaccionesNoSync()", e);
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


		if (logger.isDebugEnabled()) {
			logger.debug("getTransaccionesNoSync() - end");
		}
		return nroTrans;
	}
	
	public static int getTransAbonosNoSync() {
		if (logger.isDebugEnabled()) {
			logger.debug("getTransAbonosNoSync() - start");
		}

		int nroTrans = 0;
		String sentenciaSQL = "select count(*) as cuantos  from transaccionabono " +
				"where (transaccionabono.regactualizado = 'N') ";
		ResultSet resultado = null;
		try {
			resultado = Conexiones.realizarConsulta(sentenciaSQL, true);
			resultado.first();
			nroTrans = resultado.getInt(1);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("getTransAbonosNoSync()", e);
		} catch (ConexionExcepcion e) {
			logger.error("getTransAbonosNoSync()", e);
		} catch (SQLException e) {
			logger.error("getTransAbonosNoSync()", e);
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


		if (logger.isDebugEnabled()) {
			logger.debug("getTransAbonosNoSync() - end");
		}
		return nroTrans;
	}
	
	/**
	 * @param tiendaServ
	 */
/*	public static void registrarTienda(ResultSet tiendaServ, ResultSet publicidades) throws SQLException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sentencia = "insert into region (codregion,descripcion) values ('" + tiendaServ.getString("codregion") + "', '" + tiendaServ.getString("descripcion") + "')";

		try {
			Conexiones.realizarSentencia(sentencia, true);
		} catch (ConexionExcepcion e2) {
		} catch (BaseDeDatosExcepcion e2) {
		}		
		sentencia = "insert into tienda values ( " + tiendaServ.getInt("numtienda") + ", '" + tiendaServ.getString("nombresucursal") + "', '" + tiendaServ.getString("razonsocial") + "', ";
		sentencia += "'" + tiendaServ.getString("rif") + "', '" + tiendaServ.getString("nit") + "', '" + tiendaServ.getString("direccion") + "', '" + tiendaServ.getString("codarea") + "', '" + tiendaServ.getString("numtelefono") + "', ";
		sentencia += "'" + tiendaServ.getString("codareafax") + "', '" + tiendaServ.getString("numfax") + "', '" + tiendaServ.getString("direccionfiscal") + "', '" + tiendaServ.getString("codareafiscal") + "', ";
		sentencia += "'" + tiendaServ.getString("numtelefonofiscal") + "', '" + tiendaServ.getString("codareafaxfiscal") + "', '" + tiendaServ.getString("numfaxfiscal") + "', '" + tiendaServ.getString("monedabase") + "', ";
		sentencia += "'" + tiendaServ.getString("codregion") + "', " + tiendaServ.getString("limiteentregacaja")+ ", ' ', '" + tiendaServ.getString("indicadesctoempleado") + "', " + tiendaServ.getString("desctoventaempleado") + ", ";
		sentencia += "'" + tiendaServ.getString("cambioprecioencaja") + "', '";
		sentencia += df.format(tiendaServ.getDate("fechatienda"));
		sentencia += "', '" + tiendaServ.getString("utilizarvendedor") + "', '" + tiendaServ.getString("desctosacumulativos") + "', null, null,null,null)";
		try {
			Conexiones.realizarSentencia(sentencia, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sentencia = "delete from tiendapublicidad";
		try {
			Conexiones.realizarSentencia(sentencia, true);
		} catch (Exception e) {
		}
		publicidades.beforeFirst();
		while (publicidades.next()) {
			sentencia = "insert into tiendapublicidad values (" + publicidades.getInt("numtienda") + ", '" + publicidades.getString("publicidadlinea") + "', " + publicidades.getInt("orden") + ")";
			try {
				Conexiones.realizarSentencia(sentencia, true);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @param cajaServ
	 */
/*	public static void registrarCaja(ResultSet cajaServ) throws SQLException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fecha = cajaServ.getDate("fechareportez") == null ? null : df.format(cajaServ.getDate("fechareportez"));
		String sentencia = "insert into caja (numtienda, numcaja, idestadocaja, codusuario, numtransaccion, numnofiscal, numregistro, numseqmerchant, " +
							"nivelauditoria, fechareportez, versionsistema, modelo, numserial, montorecaudado, cierrecajero, ipcaja)";
		sentencia += " values ( " + cajaServ.getInt("numtienda") + ", " + cajaServ.getInt("numcaja") + ", ";
		sentencia += "'" + cajaServ.getString("idestadocaja") + "', '" + cajaServ.getString("codusuario") + "', ";
		sentencia += cajaServ.getInt("numtransaccion") + ", " + cajaServ.getInt("numnofiscal") + ", " + cajaServ.getInt("numregistro") + ", " + cajaServ.getInt("numseqmerchant") + ", ";
		sentencia += "'" + cajaServ.getString("nivelauditoria") + "', '" + fecha + "', '" + cajaServ.getString("versionsistema") + "', '" + cajaServ.getString("modelo") + "', ";
		sentencia += "'" + cajaServ.getString("numserial") + "', " + cajaServ.getDouble("montorecaudado") + ", '" + cajaServ.getString("cierrecajero") + "', '" + cajaServ.getString("ipcaja") + "')";
		
		try {
			Conexiones.realizarSentencia(sentencia, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @author yzambrano
	 *
	 *  buscar estados en atcm23
	 */
/*	public static Vector buscarEstados()
	{
		if (logger.isDebugEnabled()) {
			logger.debug("getTransaccionesNoSync() - start");
		}
		Vector est = new Vector();
		String sentencia = "select codedo as codigo, desedo as descripcion from atcm23 where staeli <> '9' or staeli is NULL";
		ResultSet resultado = null;
		//String valor = "";
		Estado datosEstado = null;
		try {
			resultado = Conexiones.realizarConsulta(sentencia, true);
			resultado.beforeFirst();
			while(resultado.next()){
				//valor = resultado.getString("codigo") + " " + resultado.getString("descripcion");
				//est.addElement(valor);
				datosEstado = new Estado(resultado.getInt("codigo"),resultado.getString("descripcion"));
				est.addElement(datosEstado);
			}
		} catch (BaseDeDatosExcepcion e) {
			logger.error("getTransaccionesNoSync()", e);
		} catch (ConexionExcepcion e) {
			logger.error("getTransaccionesNoSync()", e);
		} catch (SQLException e) {
			logger.error("getTransaccionesNoSync()", e);
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


		if (logger.isDebugEnabled()) {
			logger.debug("getTransaccionesNoSync() - end");
		}
		
		return (est);
	}
	
	
	
	/**
	 * 
	 * @author yzambrano
	 *
	 *  buscar estados en atcm23
	 *  posEstado: posición del estado seleccionado en el vector de estados en sesión
	 */
/*	public static Vector buscarCiudades(int posEstado)
	{
		if (logger.isDebugEnabled()) {
			logger.debug("getTransaccionesNoSync() - start");
		}
		//String codigoEstado = "";
		//StringTokenizer palabra = new StringTokenizer((String)Sesion.estados.elementAt(posEstado));
		//codigoEstado = palabra.nextToken();
		int codigoEstado = ((Estado)Sesion.estados.elementAt(posEstado)).getCodigo();
		
		Vector est = new Vector();
		String sentencia = "select codedo as estado, codciu as codigo, desciu as descripcion, codarea1 as codarea from atcm24 where (staeli <> '9' or staeli is NULL) and " +			"codedo = '" + codigoEstado + "' order by desciu";
		ResultSet resultado = null;
		Ciudad datosCiudad = null;
		try {
			resultado = Conexiones.realizarConsulta(sentencia, true);
			resultado.beforeFirst();
			while(resultado.next()){
				datosCiudad = new Ciudad(resultado.getInt("codigo"),resultado.getString("descripcion"),resultado.getString("codarea"),resultado.getInt("estado"));
				est.addElement(datosCiudad);	
			}
		} catch (BaseDeDatosExcepcion e) {
			logger.error("getTransaccionesNoSync()", e);
		} catch (ConexionExcepcion e) {
			logger.error("getTransaccionesNoSync()", e);
		} catch (SQLException e) {
			logger.error("getTransaccionesNoSync()", e);
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


		if (logger.isDebugEnabled()) {
			logger.debug("getTransaccionesNoSync() - end");
		}
		
		return (est);
	}
	
	/**
	 * 
	 * @author yzambrano
	 *
	 *  buscar estados en atcm23
	 */
/*	public static Vector buscarUrbanizaciones(int posEstado, int posCiudad)
	{
		if (logger.isDebugEnabled()) {
			logger.debug("getTransaccionesNoSync() - start");
		}
		Urbanizacion datosUrbanizacion = null;
		//String codigoEstado = "";
		//StringTokenizer palabra = new StringTokenizer((String)Sesion.estados.elementAt(posEstado));
		//codigoEstado = palabra.nextToken();
		
		int codigoEstado = ((Estado)Sesion.estados.elementAt(posEstado)).getCodigo();
				
		int codigoCiudad = ((Ciudad)Sesion.ciudades.elementAt(posCiudad)).getCodigo();
		
		Vector est = new Vector();
		String sentencia = "select codedo as estado, codciu as ciudad, codurb as codigo, desurb as descripcion, zonpos as zona from atcm25 where (staeli <> '9' or staeli is NULL) and " +
			"codedo = '" + codigoEstado + "' and codciu = '" + codigoCiudad + "' order by desurb";
		ResultSet resultado = null;
		try {
			resultado = Conexiones.realizarConsulta(sentencia, true);
			resultado.beforeFirst();
			while(resultado.next()){
				datosUrbanizacion = new Urbanizacion(resultado.getInt("codigo"),resultado.getString("descripcion"),resultado.getString("zona"),resultado.getInt("estado"),resultado.getInt("ciudad"));
				est.addElement(datosUrbanizacion);	
			}
		} catch (BaseDeDatosExcepcion e) {
			logger.error("getTransaccionesNoSync()", e);
		} catch (ConexionExcepcion e) {
			logger.error("getTransaccionesNoSync()", e);
		} catch (SQLException e) {
			logger.error("getTransaccionesNoSync()", e);
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
		
		
		if (logger.isDebugEnabled()) {
			logger.debug("getTransaccionesNoSync() - end");
		}
				
		return (est);
	}
	
	public static Estado getEstado(String codigo)
	{
		Estado datos = null;
		for (int i = 0; i < Sesion.estados.size(); i++)
		{
			datos = ((Estado)Sesion.estados.elementAt(i));
			if (datos.getCodigo() == Integer.parseInt(codigo))
			{
				return datos;
			}
		}
		return datos;
	}
	
	/**
	 * Método setUsuarioLogueado
	 * 		Establece en la BD local y en el Servidor de Tienda el identificador del usuario que inicia sesión.
	 * @param usuario
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
/*	public static void almacenarEstado(String descripcion) throws BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("setUsuarioLogueado(String) - start");
		}
		String sentencia = "select max(codedo) as codigo from atcm23";
		ResultSet resultado = null;
		int codigo;
		
		try {
			resultado = Conexiones.realizarConsulta(sentencia, true);
			if (resultado.first())
			{
				codigo = resultado.getInt("codigo") + 1;
			} else
			{
				codigo = 1;
			}
			ejecutar("insert into atcm23 (codedo,desedo) values (" + codigo + ",'" + descripcion + "')");
		} catch (BaseDeDatosExcepcion e) {
			logger.error("getTransaccionesNoSync()", e);
		} catch (ConexionExcepcion e) {
			logger.error("getTransaccionesNoSync()", e);
		} catch (SQLException e) {
			logger.error("getTransaccionesNoSync()", e);
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
			
		if (logger.isDebugEnabled()) {
			logger.debug("setUsuarioLogueado(String) - end");
		}
	}


	public static void modificarEstado(String descripcion, int codigo, String estaEli) throws BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("setUsuarioLogueado(String) - start");
		}
	
	
		ResultSet resultado = null;
		try {
			if (estaEli == "9")
			{
				String sentencia = "select codedo as codigo from atcm24 where codedo = " + codigo;
				resultado = Conexiones.realizarConsulta(sentencia, true);
			}
			if (resultado != null)//(|| resultado.first())
			{	
				MensajesVentanas.aviso("Elimine primero las ciudades asociadas al estado que desea eliminar");
			} else	{
				ejecutar("update atcm23 set desedo = '" + descripcion + "',staeli = '" + estaEli + "' where codedo=" + codigo);
			}
		} catch (BaseDeDatosExcepcion e) {
			logger.error("getTransaccionesNoSync()", e);
		} catch (ConexionExcepcion e) {
			logger.error("getTransaccionesNoSync()", e);
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
			
		if (logger.isDebugEnabled()) {
			logger.debug("setUsuarioLogueado(String) - end");
		}
	}


	
	public static Ciudad getCiudad(String estado, String ciudad)
	{
		if (logger.isDebugEnabled()) {
			logger.debug("getTransaccionesNoSync() - start");
		}
		Ciudad datosCiudad = null;
		String sentencia = "select codedo as estado, codciu as ciudad, desciu as descripcion, codarea1 as codarea " +						   "from atcm24 where (staeli <> '9' or staeli is NULL) and " +
			               "codedo = '" + estado + "' and codciu='" + ciudad + "'";
		ResultSet resultado = null;
		String valor = "";
		try {
			resultado = Conexiones.realizarConsulta(sentencia, true);
			resultado.beforeFirst();
			while(resultado.next()){
				datosCiudad = new Ciudad(resultado.getInt("ciudad"),resultado.getString("descripcion"),resultado.getString("codarea"),resultado.getInt("estado"));
			}
		} catch (BaseDeDatosExcepcion e) {
			logger.error("getTransaccionesNoSync()", e);
		} catch (ConexionExcepcion e) {
			logger.error("getTransaccionesNoSync()", e);
		} catch (SQLException e) {
			logger.error("getTransaccionesNoSync()", e);
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


		if (logger.isDebugEnabled()) {
			logger.debug("getTransaccionesNoSync() - end");
		}		
		
		return datosCiudad;
		
	}
	
	public static void almacenarCiudad(int estado, String descripcion, String codigoDeArea) throws BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("setUsuarioLogueado(String) - start");
		}
		String sentencia = "select max(codciu) as codigo from atcm24 where codedo=" + estado;
		ResultSet resultado = null;
		int codigo;
		
		try {
			resultado = Conexiones.realizarConsulta(sentencia, true);
			if (resultado.first())
			{
				codigo = resultado.getInt("codigo") + 1;
			} else
			{
				codigo = 1;
			}
			ejecutar("insert into atcm24 (codedo,codciu,desciu,codarea1) values (" + estado + "," + codigo + ",'" + descripcion + "','" + codigoDeArea + "')");
		} catch (BaseDeDatosExcepcion e) {
			logger.error("getTransaccionesNoSync()", e);
		} catch (ConexionExcepcion e) {
			logger.error("getTransaccionesNoSync()", e);
		} catch (SQLException e) {
			logger.error("getTransaccionesNoSync()", e);
		} finally {
			if (resultado != null) {
				try {
					resultado.close();
				}catch (SQLException e1) {
					e1.printStackTrace();
				}
				resultado = null;
			}
		}
			
		if (logger.isDebugEnabled()) {
			logger.debug("setUsuarioLogueado(String) - end");
		}
	}
	
	
	public static void modificarCiudad(Ciudad ciudad, String estaEli) throws BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("setUsuarioLogueado(String) - start");
		}
		ResultSet resultado = null;
		try {
			if (estaEli == "9")
			{
				String sentencia = "select codedo as codigo from atcm25 where codciu = " + ciudad;
				resultado = Conexiones.realizarConsulta(sentencia, true);
			}
			if (resultado != null)//(|| resultado.first())
			{	
				MensajesVentanas.aviso("Elimine primero las urbanizaciones asociadas a la ciudad que desea eliminar");
			} else	{
				ejecutar("update atcm24 set desciu = '" + ciudad.getDescripcion() + "', codarea1='" + ciudad.getCodArea() +
				"', staeli='" + estaEli + "' where codedo=" + ciudad.getCodEstado() + " and codciu = " + ciudad.getCodigo());
			}
		} catch (BaseDeDatosExcepcion e) {
			logger.error("getTransaccionesNoSync()", e);
		} catch (ConexionExcepcion e) {
			logger.error("getTransaccionesNoSync()", e);
		} 
				
		if (logger.isDebugEnabled()) {
			logger.debug("setUsuarioLogueado(String) - end");
		}
	}
	
	
	public static Urbanizacion getUrbanizacion(String estado, String ciudad, String urbanizacion)
	{
		if (logger.isDebugEnabled()) {
			logger.debug("getTransaccionesNoSync() - start");
		}
		
		Urbanizacion datosUrbanizacion = null;
		
		String sentencia = "select codedo as estado, codciu as ciudad, codurb as urbanizacion, desurb as " +			"descripcion, zonpos as zona from atcm25 where (staeli <> '9' or staeli is NULL) and " +
			"codedo = '" + estado + "' and codciu = '" + ciudad + "' and codurb = '" + urbanizacion + "'";
		ResultSet resultado = null;
		String valor = "";
		try {
			resultado = Conexiones.realizarConsulta(sentencia, true);
			resultado.beforeFirst();
			while(resultado.next()){
				datosUrbanizacion = new Urbanizacion(resultado.getInt("urbanizacion"),resultado.getString("descripcion"),resultado.getString("zona"),resultado.getInt("estado"),resultado.getInt("ciudad"));
			}
		} catch (BaseDeDatosExcepcion e) {
			logger.error("getTransaccionesNoSync()", e);
		} catch (ConexionExcepcion e) {
			logger.error("getTransaccionesNoSync()", e);
		} catch (SQLException e) {
			logger.error("getTransaccionesNoSync()", e);
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


		if (logger.isDebugEnabled()) {
			logger.debug("getTransaccionesNoSync() - end");
		}		
		
		return datosUrbanizacion;
		
	}
	
	public static void almacenarUrbanizacion(int estado, int ciudad, String descripcion, String zonaPostal) throws BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("setUsuarioLogueado(String) - start");
		}
		String sentencia = "select max(codurb) as codigo from atcm25 where codedo = " + estado + " and codciu = " + ciudad;
		ResultSet resultado = null;
		int codigo;
		
		try {
			resultado = Conexiones.realizarConsulta(sentencia, true);
			if (resultado.first())
			{
				codigo = resultado.getInt("codigo") + 1;
			} else
			{
				codigo = 1;
			}
			ejecutar("insert into atcm25 (codedo,codciu,codurb,desurb,zonpos) values (" + estado + "," + ciudad + "," + codigo + ",'" + descripcion + "','" + zonaPostal + "')");
		} catch (BaseDeDatosExcepcion e) {
			logger.error("getTransaccionesNoSync()", e);
		} catch (ConexionExcepcion e) {
			logger.error("getTransaccionesNoSync()", e);
		} catch (SQLException e) {
			logger.error("getTransaccionesNoSync()", e);
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
	
		if (logger.isDebugEnabled()) {
			logger.debug("setUsuarioLogueado(String) - end");
		}
	}
	
	public static void modificarUrbanizacion(Urbanizacion urbanizacion, String estaEli) throws BaseDeDatosExcepcion {
			if (logger.isDebugEnabled()) {
				logger.debug("setUsuarioLogueado(String) - start");
			}
		
			try {
				ejecutar("update atcm25 set desurb = '" + urbanizacion.getDescripcion() + "', zonpos='" + urbanizacion.getZonaPostal() +
				"', staeli = '" + estaEli + "' where codedo=" + urbanizacion.getCodEstado() + " and codciu = " + urbanizacion.getCodCiudad() + " and codurb = " + urbanizacion.getCodigo());
			} catch (BaseDeDatosExcepcion e) {
				logger.error("getTransaccionesNoSync()", e);
			} catch (ConexionExcepcion e) {
				logger.error("getTransaccionesNoSync()", e);
			} 
				
			if (logger.isDebugEnabled()) {
				logger.debug("setUsuarioLogueado(String) - end");
			}
		}
	*/
}