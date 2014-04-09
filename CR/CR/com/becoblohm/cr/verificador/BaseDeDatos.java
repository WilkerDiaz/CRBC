package com.becoblohm.cr.verificador;

/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.mediadores
 * Programa   : Conexiones.java
 * Creado por : gmartinelli
 * Creado en  : 04/03/2004 10:14 AM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1.0
 * Fecha       : 26/05/2004 02:31:34 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : - Modificado método crearLoteSentencias de modo que retorne el arreglo de
 * 				sentencias generadas manualmente y no extrayendo las sentencias de la instancia
 * 				de la sentencia preparada.
 * 				- Agregado el método getSentenciaSelect que retorna una sentencia <select> a 
 * 				a partir de una <insert> o una <update> indicada.
 * =============================================================================
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import org.apache.log4j.Logger;

public class BaseDeDatos {
	Conexion con;
	private static final Logger logger = Logger
			.getLogger(BaseDeDatos.class);
	
	public BaseDeDatos(String url, String usuario, String clave) {
		try {
			con = new Conexion(url,usuario,clave);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Object> obtenerProducto(String codigo) {
		try {
			Vector<Object> producto = new Vector<Object>();
			//con.realizarSentencia("INSERT INTO fecha (Modificacion) values (1)");
			ResultSet resultado = con.realizarConsulta("SELECT Nombre, PrecioRegular, PrecioPromocional, Monto, FechaDesde, FechaPromocion, FechaAct FROM Producto, impuesto, fecha WHERE tipoImpuesto=tipo and Codigo = " + codigo);
			if (resultado.first()) {
				producto.addElement(resultado.getString(1));
				double precioRegular = resultado.getDouble(2);
				double precioPromocional = resultado.getDouble(3);
				double iva = resultado.getDouble(4);
				String fechaDesde = resultado.getString(5);
				String fechaHasta = resultado.getString(6);
				double precio;
				if (productoEnPromocion(fechaDesde, fechaHasta, resultado.getTimestamp(7))) {
					precio = precioPromocional;
				} else {
					precio = precioRegular;
					precioPromocional = 0;
				}
				producto.addElement(new Double(precioRegular));
				producto.addElement(new Double(precioPromocional));
				producto.addElement(new Double(precio));
				producto.addElement(new Double(precio * (iva/100)));
				producto.addElement(new Double(precio + (precio * (iva/100))));
			} else {
				producto = null;
			}
			//con.desconectar();
			//con.realizarSentencia("delete from fecha");
			return producto;
		} catch (SQLException e) {
			logger.debug("ingresarLineaProducto(String, String) - end");
		}
		return null;
	}

	private boolean productoEnPromocion(String fechaDesde, String fechaHasta, Timestamp hoy) {
		int inicio = Integer.parseInt(fechaDesde.substring(6)) * 10000 +
					 Integer.parseInt(fechaDesde.substring(3,5)) * 100 +
					 Integer.parseInt(fechaDesde.substring(0,2));
		int fin = Integer.parseInt(fechaHasta.substring(6)) * 10000 +
				  Integer.parseInt(fechaHasta.substring(3,5)) * 100 +
				  Integer.parseInt(fechaHasta.substring(0,2));
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		//System.out.println(df.format(new Date(hoy.getTime())));
		int hoyEntero = Integer.parseInt(df.format(new Date(hoy.getTime())));
		return (hoyEntero>=inicio)&&(hoyEntero<=fin)&&(inicio!=0);
	}

}
/** 
 * 		Maneja las operaciones de conexiones a las base de datos, tanto . Funciona 
 * como mediador entre la aplicacion y la Base de Datos. Ejecuta sentencias 
 * simples y sentencias preparadas para actualización de tablas en la base 
 * de datos. También proporciona métodos para inicialización de la conexión 
 * conexion la base de datos y para el control de cada una de sus propiedades.
 */
class Conexion {
	
	Connection conexionLocal;
	private String URL;
	private String User;
	private String clave;
		
	/**
	 * Constructor para Conexiones.
	 * 		Establece los parámetros (clase, url, usuario, clave) para 
	 * conexión a la base de datos tanto local como del servidor central de
	 * la tienda.
	 */
	Conexion(String url, String usuario, String clave) throws ClassNotFoundException, SQLException {
		this.URL = url;
		this.User = usuario;
		this.clave = clave;
		conectar(url, usuario, clave);
	}
	
	/**
	 * Método conectar.
	 * 		Realiza la conexion a la base de Datos.
	 * @param local - Variable boolean que indica a que base de datos se desea conectar. Es verdadero si
	 * 				se conecta a la BD local. False si es a la base de datos centraliada de la tienda.
	 * @throws BaseDeDatosExcepcion - Si ocurre un error conexion el Driver o la conexion al URL definido
	 */
	void conectar(String url, String usuario, String clave) throws ClassNotFoundException, SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			throw (ex);
		}
		try {
			conexionLocal = DriverManager.getConnection(url, usuario, clave);
		} catch (SQLException ex) {
			throw (ex);
		}
	}
	
	void desconectar() throws ClassNotFoundException, SQLException {
		try {
			conexionLocal.close();
		} catch (SQLException ex) {
			throw (ex);
		}
	}

	/**
	 * Método realizarConsulta.
	 * 		Ejecuta una sentencia SQL de consulta de registros (Selects).
	 * @param sentenciaSql - Sentencia SELECT
	 * @param local - Indicador de lugar a realizar la sentencia (true BdLocal, false BdRemota)
	 * @return ResultSet - Objeto con las filas resultantes de la consulta
	 * @throws ConexionExcepcion - Si el servidor a la base de datos no se encuentra disponible
	 * @throws BaseDeDatosExcepcion - Cuando ocurre un error en la ejecucion
	 * 		de la consulta
	 */
	ResultSet realizarConsulta(String sentenciaSql) throws SQLException {
		return realizarConsulta(sentenciaSql, false);
	}	

	/**
	 * Método realizarConsulta.
	 * 		Ejecuta una sentencia SQL de consulta de registros (Selects) con la capacidad de desplazamiento
	 * y actualización o no de los datos resultantes.
	 * 
	 * @param sentenciaSql - Sentencia SELECT
	 * @param local - Indicador de lugar a realizar la sentencia (true BdLocal, false BdRemota)
	 * @return ResultSet - Objeto con las filas resultantes de la consulta
	 * @throws ConexionExcepcion - Si el servidor a la base de datos no se encuentra disponible
	 * @throws BaseDeDatosExcepcion - Cuando ocurre un error en la ejecucion
	 * 		de la consulta
	 */
	ResultSet realizarConsulta(String sentenciaSql, boolean actualizable) throws SQLException {
		ResultSet resultado = null;

		// Realizamos la operacion
		try {
			Statement sentencia;
			if (actualizable)
				sentencia = conexionLocal.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			else sentencia = conexionLocal.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultado = sentencia.executeQuery(sentenciaSql);
			resultado.first();
		} catch (SQLException exSQL) {
			if (resultado != null) {
				try {
					resultado.close();
				} catch (SQLException e) {
				}
			}
			try {
				desconectar();
				conectar(this.URL,this.User,this.clave);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw (exSQL);
		}
		return resultado;
	}
}
	