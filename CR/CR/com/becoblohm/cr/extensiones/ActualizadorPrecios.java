/*
 * ===========================================================================
 * Material Propiedad CentroBeco C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.extensiones
 * Programa		: ActualizadorPrecios.java
 * Creado por	: Jesus Graterol
 * Creado en 	: 30-may-2008 10:39:00
 * (C) Copyright 2005 CentroBeco C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Actualizaciones:
* -----------------------------------------------------------------------------.
 * Versión     : 1.1
 * Fecha       : 09-jun-2008 
 * Analista    : jgraterol
 * Descripción : Agregado metodo que suma las donaciones
 * =============================================================================
 */

package com.becoblohm.cr.extensiones;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Vector;

import com.beco.cr.actualizadorPrecios.promociones.DonacionRegistrada;
import com.beco.cr.actualizadorPrecios.promociones.ProductoCombo;
import com.beco.cr.actualizadorPrecios.promociones.PromocionExt;
import com.beco.cr.actualizadorPrecios.promociones.PromocionRegistrada;
import com.beco.cr.actualizadorPrecios.promociones.RegaloRegistrado;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.manejarventa.Anulacion;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.manejarventa.CondicionVenta;
import com.becoblohm.cr.manejarventa.DetalleTransaccion;
import com.becoblohm.cr.manejarventa.Producto;
import com.becoblohm.cr.manejarventa.Venta;


/**
 * <pre>
 * Proyecto: CR 
 * Clase: ActualizadorPrecios
 * </pre>
 * <p>
 * <a href="ActualizadorPrecios.java.html"><i>View Source</i></a>
 * </p>
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Vector', 'HashMap y otros contenedores
* Fecha: agosto 2011
*/
public interface ActualizadorPrecios extends CRExtension {
	
	public void actualizarPreciosExt(Producto prod, Venta venta) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion;
	
	
	/**
	 * Suma las donaciones tipo 1 realizadas por el cliente al subtotal 
	 * y retorna ese valor
	 * @param venta Monto subtotal de la transaccion, ya incluye impuestos
	 * @return double Subtotal con la donacion del usuario sumada
	 * **/
	public double sumarDonaciones(Venta venta, boolean solicitarDonaciones);
	
	public double sumarMontoAhorrado(Venta venta);
	
	/**
	 * Suma las donaciones tipo 2 realizadas por el cliente al subtotal 
	 * y retorna ese valor
	 * @param totalizar
	 * @return double Subtotal con la donacion del usuario sumada
	 * **/
	public void sumarDonaciones(boolean totalizar);
	
	/**
	 * Instancia el boton de agregar donaciones en menu utilitario
	 * @return JHighlightButton Boton de donaciones
	 * **/
	public JHighlightButton instanciarBoton();
	
	/**
	 * Ejecuta todo lo necesario para la promoción de transaccion
	 * premiada en caso de ser necesario
	 * @param monto Monto total a pagar de la venta
	 * **/
	public double ejecutarTransaccionPremiada(double montoAPagar,Vector<Pago> pagosRealizados, Cliente cliente);
	
	public void llamadaDeRegalos(int n, Venta venta);
	
	/**
	 * Instancia el boton de agregar cupon de descuento
	 * @return JHighlightButton Boton de donaciones
	 * **/
	public JHighlightButton instanciarBotonPromociones();


	/**
	 * Carga los productos patrocinantes
	 * @return Vector listado de productos patrocinantes
	 * **/
	public TreeMap<PromocionExt,Vector<Producto>> cargarPatrocinantes();
	
	/**
	 * Carga los productos patrocinantes
	 * @return Vector listado de productos complementarios
	 * **/
	//public HashMap cargarProductoComplementario();
	/**
	 * Ejecuta la promoción de ahorro en compra en caso de ser necesario
	 * **/
	public void ejecutarAhorroEnCompra(Venta venta);
	
	/**
	 * Verifica la existencia de promociones aplicables al producto prod
	 * @param Producto 
	 * @return Vector Contiene en la primera casilla el codigo de la promocion aplicada
	 * y en la segunda el precio final del producto
	 * **/
	public Vector<Object> verificarPromociones(Producto prod);

	//public void ejecutarProductoComplementario(int p, Venta venta);
	
	/**
	 * Obtiene las promociones nuevas del modulo de promociones que afectan al producto
	 * con el codigo indicado
	 * @param codProducto codigo del producto cuyas promociones se desean
	 * @param Vector vector de promociones existentes antes de ejecutar este metodo para el producto
	 * @return Vector de promociones
	 * **/
	//public Vector getPromocionesPatrocinantesPorProducto(String codProducto, Vector promocionesViejas);
	
	/****
     * Ejecuta el algoritmo actualizador de precios
     * @param p producto que estoy agregando
     * @param v venta actual
     */
    public void actualizarPrecios(Producto p, Venta v);

    /**
	 * Agrega a loteSentenciasCR todas las promociones creadas en la extension
	 * de promociones de caja y que fueron efectuadas en la venta actual
	 * @param venta
	 * @param loteSentenciasCR
	 * @param fechaTransString
	 * @throws SQLException
	 */
	public void agregarPromocionesALoteSentencias(Venta venta, Statement loteSentenciasCR, String fechaTransString) throws SQLException;
	
	/**
	 * Abre la ventana con las opciones de promociones
	 */
	public void ventanaPromociones(Venta venta);
	
	/**
	 * Revisa si existe o no una promoción por agregar al vector de promociones registradas
	 **/
	public void revisarPromociones(Venta venta);
	
	/***
	 * Verifica la existencia y ejecuta la promoción de producto gratis
	 * 
	 **/
	public void ejecutarProductoGratis(Venta venta);
	
	/**
	 * Deshace la promocion de producto gratis para el detalle indicado
	 * @param detalleDeshacer
	 */
	public void deshacerProductoGratis(DetalleTransaccion detalleDeshacer);
	/**
	 * Promociones 14/11/2008
	 * @author aavila 
	 * **/
	public void syncTransaccionesExt(int numTransaccion, Statement loteSentenciasSrv, Statement loteSentenciasCR, HashMap<String,Object> criterioClave);
	
	/**
	 * Abre la ventana de condiciones de venta si existe
	 *
	 */
	public void abrirVentanaCondicionesVenta(Vector<CondicionVenta> condiciones);
	
	/**
	 * Obtiene el total de las donaciones realizadas en una venta
	 * @return double monto total de donaciones realizadas
	 */
	public double getDonacionesVenta();
	
	/**
	 * Obtiene el listado de donaciones tipo 1 realizadas (las de "dona tu vuelto")
	 * @return Vector
	 */
	public Vector<DonacionRegistrada> getDonacionesRegistradasTipo1(Vector<DonacionRegistrada> donaciones); 

	/**
	 * Agrega al lote de sentencias las condiciones de venta
	 * @param venta
	 * @param loteSentenciasCR
	 * @param fechaServString
	 * @param sentenciaSQL
	 * @param i
	 * @throws SQLException
	 */
	public void agregarInsertTransaccionCondiciones(Venta venta, Statement loteSentenciasCR, String fechaServString, String sentenciaSQL, int i) throws SQLException;
	
	/**
	 * Agrega a lote de sentencias el query de eliminacion de condiciones de venta
	 * @param ap
	 * @param loteSentenciasCR
	 * @throws SQLException
	 */
	public void agregarDeleteTransaccionCondiciones(Venta ap, Statement loteSentenciasCR) throws SQLException;
	
	/**
	 * Agrega a lote de sentencias el query de eliminacion de condiciones de venta
	 * @param ap
	 * @param loteSentenciasCR
	 * @throws SQLException
	 */
	public void agregarDeleteTransaccionCondiciones(String identificacionEspera, Statement loteSentenciasCR) throws SQLException;
	
	/**
	 * Consulta en la base de datos todas las promociones de
	 * combo activas y genera una tabla (Sesion.productosCombo)
	 * que relaciona codigos de promocion con todos sus codigos asociados
	 */
	public void cargarPromocionesCombo();
	
	public void imprimirFormasDePagoEspeciales(Cliente cte, FormaDePago fPago, double monto , int numTrans);
	
	public void agregarPagosEspeciales(Venta venta);
	
	public void eliminarPagosEspeciales();
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<DonacionRegistrada> consultarDonacionesPorVenta(int codTienda, String fechaTrans, int numCaja, int numTransaccion, boolean local);
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<PromocionRegistrada> consultarPromocionesPorVenta(int codTienda, String fechaTrans, int numCaja, int numTransaccion, boolean local);
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<RegaloRegistrado> consultarRegalosPorVenta(int codTienda, String fechaTrans, int numCaja, int numTransaccion, boolean local);
	public void cancelarDonacionesAnulacion();
	public void agregarDonacionesCanceladas(Anulacion anulacion, Statement loteSentenciasCR, String fechaTransString) throws SQLException;
	
	/**
	  * Agrega la información del detalle indicado en numDetalle al vector de ProductoCombo
	  * de la promoción indicada por codPromocion
	 * @return 
	  */
	 public boolean recalcularProductosCombo(int codPromocion, int numDetalle,Vector<ProductoCombo> productos);
	 
	 
	//BECO: WDIAZ 07-2012 
	 public void crearTablaPromoTicket();
	 
	 //jperez 09-2012
	 public void actualizarTablaPromoTicket(int codPromocion);
	 
	 public ArrayList<Object> getPromoSorteoInfo(String clave);


	public boolean recalcularAhorroEnCompra(int codPromocion, int numDetalle, Vector<Producto> productosAhorroAux);

	//jperez 29-08-2012
	//public void recalcularProductosComboEliminados(int codPromocion);


	 
}
