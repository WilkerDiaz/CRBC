/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.manejarventa
 * Programa   : Detalle.java
 * Creado por : apeinado
 * Creado en  : 06/10/2003 03:10:41 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.3
 * Fecha       : 06/08/2008 12:25 PM
 * Analista    : jgraterol
 * Descripción : Agregado atributo booleano cantidadCambiada.
 * 				Necesario para el módulo de promociones.
 * 				Para las promociones tipo Combo, es necesario saber en cada corrida
 * 				del algoritmo si la cantidad de un producto en un detalle cambio o no.
 * =============================================================================
 * Versión     : 1.3
 * Fecha       : 30/07/2008 14:25 PM
 * Analista    : jgraterol
 * Descripción : Agregado vector de condiciones de venta y algunos metodos asociados
 * =============================================================================
 * Versión     : 1.3
 * Fecha       : 02/07/2008 14:25 PM
 * Analista    : jgraterol
 * Descripción : Implementado metodo clone
 * =============================================================================
 * Versión     : 1.2.3
 * Fecha       : 24/03/2004 14:25 PM
 * Analista    : gmartinelli
 * Descripción : Eliminado el atributo de Tienda ya que ese valor se encuentra
 * 				en la cabecera de las transacciones
 * =============================================================================
 * Versión     : 1.2.2
 * Fecha       : 02/02/2004 09:45 AM
 * Analista    : gmartinelli
 * Descripción : Cambio de cantidad de int a float
 * =============================================================================
 * Versión     : 1.2.1
 * Fecha       : 19/02/2004 13:52 PM
 * Analista    : gmartinelli
 * Descripción : Agregado metodo setCodSupervisor
 * =============================================================================
 * Versión     : 1.2 (según CVS)
 * Fecha       : 16/02/2004 11:46 AM
 * Analista    : IROJAS
 * Descripción : Se modificó la inicialización del atributo codSupervisor.
 * =============================================================================
 * Versión     : 1.1 (según CVS antes del cambio)
 * Fecha       : 10/02/2004 09:51 AM
 * Analista    : IROJAS
 * Descripción : Se modificó la siguiente línea:
 * 					Línea 79: Constructor de la clase: cambio del tipo de parámetro 
 * 					codProd de Long a String.
 * =============================================================================
 */
package com.becoblohm.cr.manejarventa;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ProductoExcepcion;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.mediadoresbd.BaseDeDatosVenta;
import com.becoblohm.cr.utiles.MathUtil;

/** 
 * Descripción: 
 * 		Maneja las operaciones con los objetos de las lineas de productos de las transacciones y Servicios.
 */
public class Detalle implements Serializable {

	private Producto producto;
	private float cantidad;
	private String condicionVenta;
	private String codSupervisor;
	private double precioFinal;
	private double montoImpuesto;
	private String tipoCaptura;
	private int codPromocion;
	private String tipoEntrega;
	private boolean condicionEspecial;
	
	//ACTUALIZACION BECO: 30/07/2008 Vector de objetos CondicionVenta
	private Vector<CondicionVenta> condicionesVentaPromociones = new Vector<CondicionVenta>();
	
	//ACTUALIZACION BECO: 06/08/2008
	private boolean cantidadCambiada = false;
	private float variacion = 0;
	private double precioOriginal = 0; 
	private double montoDctoCorporativo;
	private double montoDctoCombo;
	
	public Detalle(){
		
	}
	
	/**
	 * Constructor de Superclase de los Detalles de Transacciones y Servicios.
	 * @param p Producto a agregar al detalle.
	 * @param c Cantidad de productos.
	 * @param condV Condicion de Venta.
	 * @param codS Supervisor que autoriza el detalle.
	 * @param precioF Precio final del producto.
	 * @param mtoImp Monto del Impuesto.
	 * @param tipoCap Tipo de Captura (Teclado, Escaner).
	 * @param codProm Codigo de la promocion (Si aplica).
	 */
	public Detalle(Producto p, float c, String condV, String codS, double precioF, double mtoImp, String tipoCap, int codProm, String tipoEnt) {
		producto = p;
		cantidad = c;
		condicionVenta = condV;
		codSupervisor = codS;
		precioFinal = precioF;
		montoImpuesto = mtoImp;
		tipoCaptura = tipoCap;
		codPromocion = codProm;
		tipoEntrega = tipoEnt;
		
		// Se incializa la variable "condicion especial" que depende de la condicion de venta
		if(condicionVenta.equals(Sesion.condicionDesctoPorDefecto) || 
				condicionVenta.equals(Sesion.condicionDesctoEmpDesctoDefect) ||
				condicionVenta.equals(Sesion.condicionCambioPrecio) || 
				condicionVenta.equals(Sesion.condicionDesctoEmpCambPrec) ||
				condicionVenta.equals(Sesion.condicionDesctoPrecioGarantizado) || 
				condicionVenta.equals(Sesion.condicionDesctoEmpPrecioGarantizado))
			condicionEspecial = true;
		else
			condicionEspecial = false;	
	}
	
	/**
	 * Constructor de Superclase de los Detalles de Transacciones y Servicios. Busca el producto en la BD.
	 * @param codProd Codigo del producto a agregar al detalle.
	 * @param tipoCap Tipo de Captura (Teclado, Escaner).
	 * @param cant Cantidad de productos.
	 * @param fechaTrans Fecha de la transaccion (Para buscar Promociones Activas).
	 * @param horaInicioTrans (Para buscar Promociones Activas).
	 * @throws BaseDeDatosExcepcion Si ocurre un error con la Base de Datos.
	 * @throws ProductoExcepcion Si el producto no existe en la Base de Datos.
	 */
	public Detalle(String codProd, String tipoCap, float cant, Date fechaTrans, Time horaInicioTrans, boolean leidoBD) throws ProductoExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		Producto p = BaseDeDatosVenta.obtenerProducto(codProd, fechaTrans, horaInicioTrans, leidoBD);
		producto = p;
		cantidad = (float)MathUtil.roundDouble(cant);
		condicionVenta = Sesion.condicionNormal;
		codSupervisor = null;
		precioFinal = p.getPrecioRegular();
		montoImpuesto = (this.precioFinal * p.getImpuesto().getPorcentaje())/100;
		tipoCaptura = tipoCap;
		codPromocion = 0;
		tipoEntrega = p.getTipoEntrega();
		condicionEspecial = false;
	}

	public Detalle(String codProd, String tipoCap, float cant, Date fechaTrans, Time horaInicioTrans) throws ProductoExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		this(codProd, tipoCap, cant, fechaTrans, horaInicioTrans, false);
	}
	
	
	/**
	 * Anula un producto del detalle de transaccion restando 1 en sus cantidades.
	 */
	public void anularProducto() {
		//ACTUALIZACION BECO: 06/08/2008
		this.cantidadCambiada = true;
		this.variacion = -1;
		cantidad --;
	}
	
	/**
	 * Anula un producto del detalle de transaccion restando 1 en sus cantidades.
	 * @param cantidad Cantidad de productos a eliminar del renglon
	 */
	public void anularProducto(float cantidad) {
		
		//ACTUALIZACION BECO: 06/08/2008
		this.cantidadCambiada = true;
		this.variacion = -cantidad;
		
		this.cantidad -= cantidad;
		this.cantidad = (float)MathUtil.roundDouble(this.cantidad);
	}


	/**
	 * Incrementa la cantidad de productos en un detalle de transaccion.
	 * @param incremento Numero a incrementar.
	 */
	public void incrementarCantidad(float incremento) {
		cantidad += incremento;
		this.cantidad = (float)MathUtil.roundDouble(this.cantidad);
		
		//ACTUALIZACION BECO: 06/08/2008
		this.cantidadCambiada = true;
		this.variacion = incremento;
	}
	
	/**
	 * Obtiene el objeto producto.
	 * @return Producto - Objeto producto del detalle.
	 */
	public Producto getProducto() {
		return producto;
	}
	
	public void setProducto(Producto p)
	{
		producto = p;
	}
	
	/**
	 * Obtiene la cantidad de productos del detalle.
	 * @return float - Cantidad de productos.
	 */
	public float getCantidad() {
		return cantidad;
	}
	
	/**
	 * Establece el precio final.
	 * @param nvoPrecio Nuevo precio a asignar.
	 */
	public void setPrecioFinal(double nvoPrecio) {
		precioFinal = MathUtil.roundDouble(nvoPrecio);
	}
	
	/**
	 * Establece el monto del impuesto.
	 * @param d - Nuevo impuesto.
	 */
	public void setMontoImpuesto(double d) {
		montoImpuesto = d;
	}
	
	/**
	 * Establece la cantidad de articulos.
	 * @param i - Cantidad
	 */
	public void setCantidad(float i) {
		//ACTUALIZACION BECO: 06/08/2008
		this.cantidadCambiada = true;
		this.variacion = i - this.cantidad;
		
		cantidad = (float)MathUtil.roundDouble(i);
	}

	/**
	 * Obtiene la condicion de Venta.
	 * @return String - Condicion de Venta.
	 */
	public String getCondicionVenta() {
		return condicionVenta;
	}

	/**
	 * Obtiene el monto del impuesto.
	 * @return double - El monto del impuesto.
	 */
	public double getMontoImpuesto() {
		return montoImpuesto;
	}

	/**
	 * Obtiene el precio final del producto.
	 * @return double - Precio Final.
	 */
	public double getPrecioFinal() {
		return precioFinal;
	}

	/**
	 * Obtiene el tipo de captura.
	 * @return String - Tipo de captura.
	 */
	public String getTipoCaptura() {
		return tipoCaptura;
	}

	/**
	 * Obtiene el codigo de la promocion.
	 * @return int - Codigo de la promocion.
	 */
	public int getCodPromocion() {
		return codPromocion;
	}

	/**
	 * Obtiene el codigo del supervisor que autoriza el detalle.
	 * @return String - Codigo del supervisor.
	 */
	public String getCodSupervisor() {
		return codSupervisor;
	}
	
	/**
	 * Establece la condicion de Venta.
	 * @param condicionVenta Nueva condicion de Venta.
	 */
	public void setCondicionVenta(String condicionVenta) {
		// Se incializa la variable "condicion especial" que depende de la condicion de venta
		if(condicionVenta.equals(Sesion.condicionDesctoPorDefecto) || 
				condicionVenta.equals(Sesion.condicionDesctoEmpDesctoDefect) ||
				condicionVenta.equals(Sesion.condicionCambioPrecio) || 
				condicionVenta.equals(Sesion.condicionDesctoEmpCambPrec) ||
				condicionVenta.equals(Sesion.condicionDesctoPrecioGarantizado) || 
				condicionVenta.equals(Sesion.condicionDesctoEmpPrecioGarantizado))
			condicionEspecial = true;
		else
			condicionEspecial = false;	
		this.condicionVenta = condicionVenta;
	}

	/**
	 * Establece el tipo de Captura.
	 * @param tipoCaptura Nuevo tipo de Captura.
	 */
	public void setTipoCaptura(String tipoCaptura) {
		this.tipoCaptura = tipoCaptura;
	}

	/**
	 * Establece el codigo de la promocion que aplica al producto.
	 * @param codPromocion Codigo de la promocion que aplica.
	 */
	public void setCodPromocion(int codPromocion) {
		this.codPromocion = codPromocion;
	}

	/**
	 * @param string
	 */
	public void setCodSupervisor(String string) {
		codSupervisor = string;
	}

	/**
	 * Método getTipoEntrega
	 * 
	 * @return
	 * String
	 */
	public String getTipoEntrega() {
		return tipoEntrega;
	}

	/**
	 * Método setTipoEntrega
	 * 
	 * @param string
	 * void
	 */
	public void setTipoEntrega(String string) {
		tipoEntrega = string;
	}

	/**
	 * Método isCondicionEspecial
	 * 
	 * @return
	 * boolean
	 */
	public boolean isCondicionEspecial() {
		return condicionEspecial;
	}
	/**
	 * Método setCondicionEspecial
	 * 
	 * @param b
	 * void
	 */
	public void setCondicionEspecial(boolean b) {
		condicionEspecial = b;
	}

	
	/**********************************************************************************
	 * Métodos agregados por el módulo de promociones 
	 * Actualizaciones de CENTROBECO, C.A.
	 **********************************************************************************/
	
	/**
	 * Implementa clone de object
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Object clone(){

		
		Detalle clon = new Detalle();
		clon.setProducto((Producto)this.producto.clone());
		clon.setCantidad(this.cantidad);
		clon.setCondicionVenta(this.condicionVenta);
		clon.setCodSupervisor(this.codSupervisor);
		clon.setPrecioFinal(this.precioFinal);
		clon.setMontoImpuesto(this.montoImpuesto);
		clon.setTipoCaptura(this.tipoCaptura);
		clon.setCodPromocion(this.codPromocion);
		clon.setTipoEntrega(this.tipoEntrega);
		clon.setCondicionEspecial(this.condicionEspecial);
		clon.setMontoDctoCombo(this.montoDctoCombo);
		
		Vector<CondicionVenta> condicionesVentaClonadas = new Vector<CondicionVenta>();
		for(int i=0;i<condicionesVentaPromociones.size();i++){
			condicionesVentaClonadas.addElement((CondicionVenta)(condicionesVentaPromociones.elementAt(i)).clone());
		}
		clon.setCondicionesVentaPromociones(condicionesVentaClonadas);
		clon.setCantidadCambiada(this.cantidadCambiada);
		clon.setVariacion(this.variacion);
		return clon;
	}

	/**
	 * @return el condicionVentaPromociones
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<CondicionVenta> getCondicionVentaPromociones() {
		return condicionesVentaPromociones;
	}

	/**
	 * @param condicionVentaPromociones el condicionVentaPromociones a establecer
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void setCondicionVentaPromociones(
			Vector<CondicionVenta> condicionVentaPromociones) {
		this.condicionesVentaPromociones = condicionVentaPromociones;
	}
	
	/**
	 * Verifica si el listado de condiciones indicado esta COMPLETAMENTE
	 * incluido dentro del listado de condiciones aplicadas al detalle
	 * @param idCondiciones Listado de identificadores de condiciones de venta
	 * a chequear
	 * @return boolean
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public boolean contieneCondicion(Vector<String> idCondiciones){
		Iterator<CondicionVenta> i = condicionesVentaPromociones.iterator();
		int cantContenidas=0;
		while (i.hasNext()){
			CondicionVenta cv = (CondicionVenta)i.next();
			if(idCondiciones.contains(cv.getCondicion())){
				cantContenidas++;
			}
		}
		if(cantContenidas == idCondiciones.size()){
			return true;
		}
		return false;
	}
	
	/**
	 * Verifica si el listado de condiciones indicado contiene ALGUNA
	 * de las condiciones aplicadas al detalle
	 * @param idCondiciones Listado de identificadores de condiciones de venta
	 * a chequear
	 * @return boolean
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public boolean contieneAlgunaCondicion(Vector<String> idCondiciones){
		Iterator<CondicionVenta> i = condicionesVentaPromociones.iterator();
		while (i.hasNext()){
			CondicionVenta cv = (CondicionVenta)i.next();
			if(idCondiciones.contains(cv.getCondicion())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Cuenta las condiciones del vector idCondiciones que estan contenidas en este detalle
	 * @param idCondiciones Listado de identificadores de condiciones de venta
	 * a chequear
	 * @return int Numero de condiciones contenidas
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public int cantidadCondicionesContenidas(Vector<String> idCondiciones){
		Iterator<CondicionVenta> i = condicionesVentaPromociones.iterator();
		int contenidas = 0;
		while (i.hasNext()){
			CondicionVenta cv = (CondicionVenta)i.next();
			if(idCondiciones.contains(cv.getCondicion())){
				contenidas++;
			}
		}
		return contenidas;
	}
	
	/**
	 * Agrega una condicion de venta al vector de condiciones 
	 * si no existe ya
	 * @param cv
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void addCondicion(CondicionVenta cv){
		if(!condicionesVentaPromociones.contains(cv)){
				condicionesVentaPromociones.add(cv);
		}
		
		Vector<String> condicionesEspeciales = new Vector<String>();
		condicionesEspeciales.add(Sesion.condicionDesctoPorDefecto);
		condicionesEspeciales.add(Sesion.condicionCambioPrecio);
		condicionesEspeciales.add(Sesion.condicionDesctoPrecioGarantizado);
		
		if(this.contieneAlgunaCondicion(condicionesEspeciales)){
			this.condicionEspecial = true;
		}
		
	}
	
	/**
	 * Elimina la condicion de venta del vector si existe
	 * @param cv
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void removeCondicion(CondicionVenta cv){
		if(cv!=null){
			String condicion = cv.getCondicion();
			for (int i=0; i<condicionesVentaPromociones.size();i++){
				CondicionVenta condAElminar =(CondicionVenta)condicionesVentaPromociones.elementAt(i); 
				if(condAElminar.getCondicion().equalsIgnoreCase(condicion)){
					condicionesVentaPromociones.set(i, null);
					break;
				}
			}
			condicionesVentaPromociones.remove(null);
			
			Vector<String> condicionesEspeciales = new Vector<String>();
			condicionesEspeciales.add(Sesion.condicionDesctoPorDefecto);
			condicionesEspeciales.add(Sesion.condicionCambioPrecio);
			condicionesEspeciales.add(Sesion.condicionDesctoPrecioGarantizado);
			
			if(!this.contieneAlgunaCondicion(condicionesEspeciales)){
				this.condicionEspecial = false;
			}
		}
		
	}
	
	/***
	 * Obtiene el objeto CondicionVenta que corresponde con el codigo de condicon indicado
	 * @param codigoCondicion
	 * @return CondicionVenta
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public CondicionVenta getCondicionPorCodigo(String codigoCondicion){
		Iterator<CondicionVenta> i = this.condicionesVentaPromociones.iterator();
		while(i.hasNext()){
			CondicionVenta cv = (CondicionVenta)i.next();
			if(cv.getCondicion().equalsIgnoreCase(codigoCondicion)){
				return cv;
			}
		}
		return null;
	}
	
	/**
	 * Obtiene la promocion (de la tabla Promocion, no extendida) a la cual corresponde el codigo indicado
	 * si es que se encuentra aplicada al producto contenido en este detalle
	 * @param codPromocion
	 * @return CondicionVenta
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Promocion getPromocionCondicionP(String condicion){
		Iterator<CondicionVenta> i = condicionesVentaPromociones.iterator();
		while (i.hasNext()){
			CondicionVenta cv = (CondicionVenta)i.next();
			if(condicion.equalsIgnoreCase((cv.getCondicion()))){
				return this.getProducto().getPromocionPorCodigo(cv.getCodPromocion());
			}
		}
		return null;
	}
	
	/**
	 * Obtiene la promocion (de la tabla Promocion, no extendida) a la cual corresponde la
	 * primera coincidencia de codigo de condicion entre los que estan en el vector que
	 * recibe como parametro
	 * @param codPromocion
	 * @return CondicionVenta
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Promocion getPromocionCondicionP(Vector<String> condiciones){
		Iterator<CondicionVenta> i = condicionesVentaPromociones.iterator();
		while (i.hasNext()){
			CondicionVenta cv = (CondicionVenta)i.next();
			for(int j=0;j<condiciones.size();j++){
				if(((String)condiciones.elementAt(j)).equalsIgnoreCase((cv.getCondicion()))){
					return this.getProducto().getPromocionPorCodigo(cv.getCodPromocion());
				}
			}
		}
		return null;
	}

	/**
	 * @return el cantidadCambiada
	 */
	public boolean isCantidadCambiada() {
		return cantidadCambiada;
	}

	/**
	 * @param cantidadCambiada el cantidadCambiada a establecer
	 */
	public void setCantidadCambiada(boolean cantidadCambiada) {
		this.cantidadCambiada = cantidadCambiada;
	}

	/**
	 * @return el condicionesVentaPromociones
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<CondicionVenta> getCondicionesVentaPromociones() {
		return condicionesVentaPromociones;
	}

	/**
	 * @param condicionesVentaPromociones el condicionesVentaPromociones a establecer
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void setCondicionesVentaPromociones(Vector<CondicionVenta> condicionesVentaPromociones) {
		this.condicionesVentaPromociones = condicionesVentaPromociones;
	}

	/**
	 * @return el variacion
	 */
	public float getVariacion() {
		return variacion;
	}

	/**
	 * @param variacion el variacion a establecer
	 */
	public void setVariacion(float variacion) {
		this.variacion = variacion;
	}
	
	/****
	 * Determina si dos detalles contienen las mismas condiciones
	 * @param dt DetalleTransaccion a comparar
	 * @return boolean
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public boolean contieneMismasCondiciones(Detalle dt){
		Vector<String> condicionesThisSttring = new Vector<String>();
		for(int i=0;i<this.condicionesVentaPromociones.size();i++){
			CondicionVenta condicion = (CondicionVenta)this.getCondicionesVentaPromociones().elementAt(i);
			condicionesThisSttring.add(condicion.getCondicion());
		}
		return dt.contieneCondicion(condicionesThisSttring) && condicionesThisSttring.size()==dt.getCondicionesVentaPromociones().size();
	}
	
	/****
	 * Obtiene la primera condicion de venta del detalle que coincida con alguno
	 * de los codigos en el vector
	 * @param condiciones
	 * @return CondicionVenta
	 */
	public CondicionVenta getPrimeraCondicion(Vector<String> condiciones){
		for(int i=0;i<condiciones.size();i++){
			CondicionVenta cv = getCondicionPorCodigo((String)condiciones.elementAt(i));
			if(cv!=null)
				return cv;
		}
		return null;
	}
	
	/****
	 * Dado el vector de condiciones de venta construye el string correspondiente
	 * @return String
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public String construirCondicionVentaString(){
		Vector<String> condicionEmpleadoCorporativo = new Vector<String>();
		condicionEmpleadoCorporativo.addElement(Sesion.condicionDesctoEmpleado);
		condicionEmpleadoCorporativo.addElement(Sesion.condicionDescuentoCorporativo);
		
		Vector<String> condicionDesctoEmpleadoProm = new Vector<String>();
		condicionDesctoEmpleadoProm.addElement(Sesion.condicionDesctoEmpleado);
		condicionDesctoEmpleadoProm.addElement(Sesion.condicionPromocion);
		
		Vector<String> condicionDesctoEmpleadoEmpaque = new Vector<String>();
		condicionDesctoEmpleadoEmpaque.addElement(Sesion.condicionDesctoEmpleado);
		condicionDesctoEmpleadoEmpaque.addElement(Sesion.condicionEmpaque);
		
		Vector<String> condicionDesctoEmpleadoCambPrec = new Vector<String>();
		condicionDesctoEmpleadoCambPrec.addElement(Sesion.condicionDesctoEmpleado);
		condicionDesctoEmpleadoCambPrec.addElement(Sesion.condicionCambioPrecio);
		
		Vector<String> condicionDesctoEmpleadoDesctoDefecto = new Vector<String>();
		condicionDesctoEmpleadoDesctoDefecto.addElement(Sesion.condicionDesctoEmpleado);
		condicionDesctoEmpleadoDesctoDefecto.addElement(Sesion.condicionDesctoPorDefecto);
		
		Vector<String> condicionDesctoEmpleadoPrecioGarantizado = new Vector<String>();
		condicionDesctoEmpleadoPrecioGarantizado.addElement(Sesion.condicionDesctoEmpleado);
		condicionDesctoEmpleadoPrecioGarantizado.addElement(Sesion.condicionDesctoPrecioGarantizado);
		
		Vector<String> condicionEmpaquePromocion = new Vector<String>();
		condicionEmpaquePromocion.addElement(Sesion.condicionEmpaque);
		condicionEmpaquePromocion.addElement(Sesion.condicionPromocion);
		
		Vector<String> condicionEmpaquePromocionEmpleado = new Vector<String>();
		condicionEmpaquePromocionEmpleado.addElement(Sesion.condicionEmpaque);
		condicionEmpaquePromocionEmpleado.addElement(Sesion.condicionPromocion);
		condicionEmpaquePromocionEmpleado.addElement(Sesion.condicionDesctoEmpleado); 

		
		Vector<String> condicionesAntiguas = new Vector<String>();
		condicionesAntiguas.addElement(Sesion.condicionDesctoEmpleado);
		condicionesAntiguas.addElement(Sesion.condicionPromocion);
		condicionesAntiguas.addElement(Sesion.condicionEmpaque);
		condicionesAntiguas.addElement(Sesion.condicionCambioPrecio);
		condicionesAntiguas.addElement(Sesion.condicionDesctoPorDefecto);
		condicionesAntiguas.addElement(Sesion.condicionDesctoPrecioGarantizado);
		condicionesAntiguas.addElement(Sesion.condicionVentaPorVolumen);
		
		Vector<String> condicionCorporativo = new Vector<String>();
		condicionCorporativo.addElement(Sesion.condicionDescuentoCorporativo);
		
		Vector<String> condicionProductoGratis = new Vector<String>();
		condicionProductoGratis.addElement(Sesion.condicionProductoGratis);
		
		Vector<String> condicionDescontadoPorCombo= new Vector<String>();
		condicionDescontadoPorCombo.addElement(Sesion.condicionDescontadoPorCombo);
		
		Vector<String> condicionesNormal = new Vector<String>();
		condicionesNormal.addElement(Sesion.condicionNormal);
		
		if(this.contieneAlgunaCondicion(condicionProductoGratis)){
			return Sesion.condicionProductoGratis;
		} else {
			if(this.contieneAlgunaCondicion(condicionesAntiguas) && 
					this.contieneAlgunaCondicion(Sesion.condicionesNuevas)){
				return Sesion.condicionMixta;
			} else if(this.cantidadCondicionesContenidas(Sesion.condicionesNuevas)>1){
				return Sesion.condicionMixta;			
			} else if(this.contieneCondicion(condicionDesctoEmpleadoProm) ){
				return Sesion.condicionDesctoEmpProm;
			} else if(this.contieneCondicion(condicionDesctoEmpleadoEmpaque)){
				return Sesion.condicionDesctoEmpEmpaque;
			} else if(this.contieneCondicion(condicionDesctoEmpleadoCambPrec)){
				return Sesion.condicionDesctoEmpCambPrec;
			} else if(this.contieneCondicion(condicionDesctoEmpleadoDesctoDefecto)){
				return Sesion.condicionDesctoEmpDesctoDefect;
			} else if(this.contieneCondicion(condicionDesctoEmpleadoPrecioGarantizado)){
				return Sesion.condicionDesctoEmpPrecioGarantizado;
			} else if(this.contieneCondicion(condicionEmpaquePromocion)){
				return Sesion.condicionEmpaquePromocion;
			} else if(this.contieneCondicion(condicionEmpaquePromocionEmpleado)){
				return Sesion.condicionEmpaquePromocionEmpleado;
			}  else if(this.condicionesVentaPromociones.size()==1 || (this.condicionesVentaPromociones.size()==2 
					&& this.contieneCondicion(condicionDescontadoPorCombo))){
				return ((CondicionVenta)this.condicionesVentaPromociones.elementAt(0)).getCondicion();
			} else if(this.condicionesVentaPromociones.size()==0 || this.contieneAlgunaCondicion(condicionesNormal)) {
				return Sesion.condicionNormal;
			}
		}
		return "";
	}

	/**
	 * @return el precioOriginal
	 */
	public double getPrecioOriginal() {
		return precioOriginal;
	}

	/**
	 * @param precioOriginal el precioOriginal a establecer
	 */
	public void setPrecioOriginal(double precioOriginal) {
		this.precioOriginal = precioOriginal;
	}
	
	/**
	 * @return el montoDctoCorporativo
	 */
	public double getMontoDctoCorporativo() {
		return montoDctoCorporativo;
	}

	/**
	 * @param montoDctoCorporativo el montoDctoCorporativo a establecer
	 */
	public void setMontoDctoCorporativo(double montoDctoCorporativo) {
		this.montoDctoCorporativo = montoDctoCorporativo;
	}

	/**
	 * @return el montoDctoCombo
	 */
	public double getMontoDctoCombo() {
		return montoDctoCombo;
	}

	/**
	 * @param montoDctoCombo el montoDctoCombo a establecer
	 */
	public void setMontoDctoCombo(double montoDctoCombo) {
		this.montoDctoCombo = montoDctoCombo;
	}
	
	/**********************************************************************************
	 * Hasta aquí los métodos agregados por el módulo de promociones 
	 * Actualizaciones de CENTROBECO, C.A.
	 **********************************************************************************/
}
