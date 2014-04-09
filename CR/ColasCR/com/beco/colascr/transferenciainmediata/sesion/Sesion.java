/**
 * =============================================================================
 * Proyecto   : TransferenciaInmediataProdsCR
 * Paquete    : com.beco.colascr.transferenciainmediata.sesion
 * Programa   : Sesion.java
 * Creado por : gmartinelli
 * Creado en  : 28-jun-05 9:11:06
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1
 * Fecha       : 28-jun-05 9:11:06
 * Analista    : gmartinelli
 * Descripción : Implementación inicial.
 * =============================================================================
 */
package com.beco.colascr.transferenciainmediata.sesion; 

import java.io.File;
import java.util.Vector;

import com.beco.colascr.transferenciainmediata.entidades.Ciudad;
import com.beco.colascr.transferenciainmediata.entidades.CodigoExterno;
import com.beco.colascr.transferenciainmediata.entidades.CondicionPromocion;
import com.beco.colascr.transferenciainmediata.entidades.DetallePromocion;
import com.beco.colascr.transferenciainmediata.entidades.DetallePromocionExt;
import com.beco.colascr.transferenciainmediata.entidades.Donacion;
import com.beco.colascr.transferenciainmediata.entidades.Estado;
import com.beco.colascr.transferenciainmediata.entidades.Producto;
import com.beco.colascr.transferenciainmediata.entidades.Promocion;
import com.beco.colascr.transferenciainmediata.entidades.TransaccionPremControl;
import com.beco.colascr.transferenciainmediata.entidades.Urbanizacion;

/**
 * Descripción:
 * 
 */

public class Sesion {
	
	private static String dbUsuario;
	private static String dbClave;
	private static String dbEsquema;
	private static Vector<Producto> productos;
	private static Vector<CodigoExterno> codExternos;
	private static Vector<Promocion> promociones;
	private static Vector<DetallePromocion> detallePromociones;
	private static Vector<Ciudad> ciudades;
	private static Vector<Estado> estados;
	private static Vector<Urbanizacion> urbanizaciones;
	//Promociones
	private static Vector<DetallePromocionExt> detallePromocionesExt;
	private static Vector<Donacion> donacion;
	private static Vector<TransaccionPremControl> transaccionPremControl;
	private static Vector<CondicionPromocion> condicionPromocion;
	public static boolean promo = false;
	public static File archivopromo = null;
	//
	public static final String IP_LOCAL = "127.0.0.1";
	public static final String BD_LOCAL = "MySql";
	
	public static final String IP_SERVIDOR = "192.168.1.2";
	public static final String BD_SERVIDOR = "as400";

	public static final char SEP_CAMPO = '[';
	public static final char DEL_CAMPO = ']';

	public static String pathArchivos = "";


	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public Sesion (String usuario, String clave, String esquema, String pathFtp) {
		Sesion.dbUsuario = usuario;
		Sesion.dbClave = clave;
		Sesion.dbEsquema = esquema;
		productos = new Vector<Producto>();
		codExternos = new Vector<CodigoExterno>();
		promociones = new Vector<Promocion>();
		detallePromociones = new Vector<DetallePromocion>();
		estados = new Vector<Estado>();
		ciudades = new Vector<Ciudad>();
		urbanizaciones = new Vector<Urbanizacion>();
		File f = new File(pathFtp);
		pathArchivos = f.getAbsolutePath() + File.separatorChar;
		pathArchivos = pathArchivos.replace(File.separatorChar,'/');
		pathArchivos = pathArchivos.replaceAll("/","//");
		System.out.println(pathArchivos);
		/**
		 * Promociones
		 * @author aavila
		 * */
		detallePromocionesExt = new Vector<DetallePromocionExt>();
		donacion =new Vector<Donacion>();
		transaccionPremControl = new Vector<TransaccionPremControl>();
		condicionPromocion= new Vector<CondicionPromocion>();
	}

	/**
	 * Método getDbClave
	 * 
	 * @return
	 * String
	 */
	public static String getDbClave() {
		return dbClave;
	}

	/**
	 * Método getDbEsquema
	 * 
	 * @return
	 * String
	 */
	public static String getDbEsquema() {
		return dbEsquema;
	}

	/**
	 * Método getDbUsuario
	 * 
	 * @return
	 * String
	 */
	public static String getDbUsuario() {
		return dbUsuario;
	}

	/**
	 * Método agregarPromocion
	 * 
	 * @param promocion
	 * void
	 */
	public static void agregarPromocion(Promocion promocion) {
		Sesion.promociones.addElement(promocion);
	}

	/**
	 * Método agregarPromocion
	 * 
	 * @param promocion
	 * void
	 */
	public static void agregarDetallePromocion(DetallePromocion detPromocion) {
		Sesion.detallePromociones.addElement(detPromocion);
	}
	/**
	 * Método agregarEstado
	 * 
	 * @param promocion
	 * void
	 */
	public static void agregarEstado(Estado estado) {
		Sesion.estados.addElement(estado);
	}
	/**
	 * Método agregarCiudad
	 * 
	 * @param promocion
	 * void
	 */
	public static void agregarCiudad(Ciudad ciudad) {
		Sesion.ciudades.addElement(ciudad);
	}
	/**
	 * Método agregarUrbanizacion
	 * 
	 * @param promocion
	 * void
	 */
	public static void agregarUrbanizacion(Urbanizacion urbanizacion) {
		Sesion.urbanizaciones.addElement(urbanizacion);
	}
	/**
	 * Método agregarPromocion
	 * 
	 * @param promocion
	 * void
	 */
	public static void agregarProducto(Producto producto) {
		Sesion.productos.addElement(producto);
	}
	/**
	 * Método agregarPromocion
	 * 
	 * @param promocion
	 * void
	 */
	public static void agregarCodExterno(CodigoExterno codExterno) {
		Sesion.codExternos.addElement(codExterno);
	}
	/**
	 * Método getCodExternos
	 * 
	 * @return
	 * Vector
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public static Vector<CodigoExterno> getCodExternos() {
		return codExternos;
	}

	/**
	 * Método getDetallePromociones
	 * 
	 * @return
	 * Vector
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public static Vector<DetallePromocion> getDetallePromociones() {
		return detallePromociones;
	}

	/**
	 * Método getProductos
	 * 
	 * @return
	 * Vector
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public static Vector<Producto> getProductos() {
		return productos;
	}

	/**
	 * Método getPromociones
	 * 
	 * @return
	 * Vector
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public static Vector<Promocion> getPromociones() {
		return promociones;
	}

	/**
	 * Método getCiudades
	 * 
	 * @return
	 * Vector
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public static Vector<Ciudad> getCiudades() {
		return ciudades;
	}
	/**
	 * Método getEstados
	 * 
	 * @return
	 * Vector
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public static Vector<Estado> getEstados() {
		return estados;
	}
	/**
	 * Método getUrbanizaciones
	 * 
	 * @return
	 * Vector
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public static Vector<Urbanizacion> getUrbanizaciones() {
		return urbanizaciones;
	}
/**
 * Promociones
 * @author aavila
 * **/
	public static void agregarDetallePromocionExt(DetallePromocionExt detallePromocionExt) {
		Sesion.detallePromocionesExt.addElement(detallePromocionExt);
	}

	public static void agregarDonacion(Donacion donacion) {
		Sesion.donacion.addElement(donacion);
	}
	
	public static void agregarTransaccionPremControl(TransaccionPremControl transaccionPremControl) {
		Sesion.transaccionPremControl.addElement(transaccionPremControl);
	}
	
	public static void agregarCondicionPromocion(CondicionPromocion condicionPromocion) {
		Sesion.condicionPromocion.addElement(condicionPromocion);
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public static Vector<DetallePromocionExt> getDetallePromocionesExt() {
		return detallePromocionesExt;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public static Vector<Donacion> getDonacion(){
		return donacion;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public static Vector<TransaccionPremControl> getTransaccionPremControl(){
		return transaccionPremControl;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public static Vector<CondicionPromocion> getCondicionPromocion(){
		return condicionPromocion;
	}

	public static boolean isPromo() {
		return promo;
	}

	public static void setPromo(boolean promo) {
		Sesion.promo = promo;
	}

	
	
}
