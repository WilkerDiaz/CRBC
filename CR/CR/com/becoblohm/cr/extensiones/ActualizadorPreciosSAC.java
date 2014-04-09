/*
 * ===========================================================================
 * Material Propiedad CentroBeco C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.extensiones
 * Programa		: ActualizadorPreciosSAC.java
 * Creado por	: Jesus Graterol
 * Creado en 	: 30-may-2008 10:39:00
 * (C) Copyright 2005 CentroBeco C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Actualizaciones:
* -----------------------------------------------------------------------------.
 * Versión     : 
 * Fecha       :  
 * Analista    : 
 * Descripción : 
 * =============================================================================
 */

package com.becoblohm.cr.extensiones;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeMap;
import java.util.Vector;

import com.beco.cr.actualizadorPrecios.promociones.PromocionExt;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.manejarservicio.Apartado;
import com.becoblohm.cr.manejarservicio.DetalleServicio;
import com.becoblohm.cr.manejarservicio.Servicio;
import com.becoblohm.cr.manejarventa.Devolucion;
import com.becoblohm.cr.manejarventa.Producto;




/**
 * <pre>
 * Proyecto: CR 
 * Clase: ActualizadorPrecios
 * </pre>
 * <p>
 * <a href="ActualizadorPrecios.java.html"><i>View Source</i></a>
 * </p>
 */
public interface ActualizadorPreciosSAC extends CRExtension {
	
	public void actualizarPreciosExt(Producto p, Servicio serv, boolean aplicarPromociones) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion;

	/**
	 * Carga los productos patrocinantes
	 * @return Vector listado de productos patrocinantes
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public TreeMap<PromocionExt,Vector<Producto>> cargarPatrocinantes();
	
	/**
	 * Ejecuta la promoción de ahorro en compra en caso de ser necesario
	 * **/
	public void ejecutarAhorroEnCompra();
	
	/**
	 * Verifica la existencia de promociones aplicables al producto prod
	 * @param Producto 
	 * @return Vector Contiene en la primera casilla el codigo de la promocion aplicada
	 * y en la segunda el precio final del producto
	 * **/
	public Vector<Object> verificarPromociones(Producto prod);
	
	/****
     * Ejecuta el algoritmo actualizador de precios
     * @param p producto que estoy agregando
     * @param v venta actual
     */
    public void actualizarPrecios(Producto p, Servicio serv, boolean aplicarPromociones);
    
	
	/***
	 * Verifica la existencia y ejecuta la promoción de producto gratis
	 * 
	 **/
	public void ejecutarProductoGratis();
	
	/**
	 * Deshace la promocion de producto gratis para el detalle indicado
	 * @param detalleDeshacer
	 */
	public void deshacerProductoGratis(DetalleServicio detalleDeshacer);
	
	/**
	 * Recalcula las promociones nuevas al abonar a un apartado
	 * Debe ser llamado despues del metodo recalculadoPromocionesApartado
	 * @return boolean indica si se hizo algun recalculo en los detalles de servicio
	 */
	public boolean recalculadoPromociones();
	
	/**
	 * Agrega al lote de sentencias la insercion de condiciones de venta
	 * @param apartado
	 * @param loteSentenciasCR
	 * @param fechaServString
	 * @param sentenciaSQL
	 * @param i
	 * @throws SQLException
	 * @author jgraterol
	 */
	public void agregarInsertServicioCondiciones(Apartado apartado, Statement loteSentenciasCR, String fechaServString, String sentenciaSQL, int i, boolean isEnEspera , int numServicio) throws SQLException;
	
	/**
	 * Agrega la actualizacion de detalles de servicio al lote de sentencias
	 * @param ap
	 * @param loteSentenciasCR
	 * @throws SQLException
	 */
	public void agregarDeleteServicioCondiciones(Apartado ap, Statement loteSentenciasCR, boolean isEnEspera, int numServicio) throws SQLException ;
	
	/**
	 * Indica si el boton en servicio esta habilitado o no.
	 * @param numBoton Dada la tecla de funcion que acompaña al boton, numBoton es el numero que acompaña a la F
	 * @param enUtilitarios True si estamos en la pantalla de utilitarios, False si estamos en alguna otra.
	 */
	public boolean botonServicioHabilitado(int numBoton, boolean enUtilitarios);
	
	/**
	 * Inicia la ventana de promociones en el menú de utilitarios
	 * @param apartado
	 */
	public void ventanaPromociones(Apartado apartado);
	
	/**
	 * Suma el monto de las donaciones al apartado
	 * @param apartado
	 * @return double
	 */
	public double sumarDonaciones(Apartado apartado, double subTotal, boolean solicitarDonaciones);
	
	//public HashMap cargarProductoComplementario();
	
	/**
	 * llamada a la promocion producto complementario
	 */
    //public void ejecutarProductoComplementario(int p);
    
    /**
     *  Aplica las promociones de regalo
     * @param n accion a ejecutar: 1.- Mostrar aviso. 2.- Imprimir comprobante de regalo
     */
    public void llamadaDeRegalos(int n);
    
    public void acumularDetallesDevolucion(Devolucion dev);
}
