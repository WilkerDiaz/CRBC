/*
 * Creado el 24/05/2008
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.beco.cr.actualizadorPrecios.promociones;

import java.sql.ResultSet;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;
import com.beco.cr.actualizadorPrecios.promociones.ManejadorBD.PromocionExtBD;
import com.beco.cr.actualizadorPrecios.promociones.ManejadorBD.UtilesBD;
import com.becoblohm.cr.CR;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarventa.DetalleTransaccion;
import com.becoblohm.cr.manejarventa.Producto;
import com.becoblohm.cr.manejarventa.Promocion;
import com.becoblohm.cr.manejarventa.Venta;
import com.becoblohm.cr.utiles.MensajesVentanas;



/**
 * @author aavila
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
/*
* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
* Fecha: agosto 2011
*/
public class PromocionExt extends Promocion {

	private String categoria = null;
	private String marca = null;
	private String refProveedor = null;
	private double montoMinimo = 0;
	private int cantMinima = 0;
	private int cantRegalada = 0;
	private double bsDescuento = 0;
	private String nombrePromocion = null;
	private String acumulaPremio=null;
	
	//Campos agregados por Extension de la Promocion Premio Ilusion
	private double sumMontoTotal = 0;
	private int sumCantidad = 0; 
	
	//Si la promocion es un combo este vector contiene los 
	//productos para combo... un listado de objetos
	//ProductoCombo
	Vector<ProductoCombo> productosCombo = new Vector<ProductoCombo>();
	
	//Lineas de las condiciones del sorteo en caso de cupon
	private Vector<String> lineasCondiciones = new Vector<String>();

	public PromocionExt(int codPromocion, Date fechaInicio, 
			Time horaInicio, Date fechaFinaliza, Time horaFinaliza, 
			int prioridad, char tipoPromocion, int numDetalle, 
			String codDepartamento, String codLineaSeccion, 
			String categoria, String marca, String refProveedor, 
			double montoMinimo, int cantMinima, 
			int cantRegalada, String codProducto, double bsDescuento, 
			double porcentajeDescuento, String nombrePromocion) {
		// TODO Apéndice de constructor generado automáticamente
		super(codPromocion,tipoPromocion,fechaInicio,horaInicio,fechaFinaliza,horaFinaliza,codDepartamento,codLineaSeccion,codProducto,porcentajeDescuento);
		this.categoria = categoria;
		this.marca = marca;
		this.refProveedor = refProveedor;
		this.montoMinimo = montoMinimo;
		this.cantMinima = cantMinima;
		this.cantRegalada = cantRegalada;
		this.codProducto = codProducto;
		this.bsDescuento = bsDescuento;
		this.nombrePromocion = nombrePromocion;
	}
	
	/**
	 * Constructor solo para transaccion premiada
	 * **/
	public PromocionExt(int codPromocion, Date fechaInicio, 
			Time horaInicio, Date fechaFinaliza, Time horaFinaliza, 
			int prioridad, char tipoPromocion, int numDetalle, double porcDescuento, String nombrePromocion) {
		// TODO Apéndice de constructor generado automáticamente
		super(codPromocion, tipoPromocion,fechaInicio,horaInicio,fechaFinaliza,horaFinaliza,prioridad,numDetalle,porcDescuento);
		this.nombrePromocion = nombrePromocion;
	}
	
	/**
	 * Constructor solo para combos
	 * **/
	public PromocionExt(int codPromocion, Date fechaInicio, 
			Time horaInicio, Date fechaFinaliza, Time horaFinaliza, 
			int prioridad, char tipoPromocion, double porcDescuento, String nombrePromocion,
			int cantMinima, int cantRegalada/*, String acumulaPremio*/) {
		super(codPromocion, tipoPromocion,fechaInicio,horaInicio,fechaFinaliza,horaFinaliza,prioridad,porcDescuento);
		this.nombrePromocion = nombrePromocion;
		this.cantMinima=cantMinima;
		this.cantRegalada=cantRegalada;
		//this.acumulaPremio= acumulaPremio;
	}
	
	/**
	 * Constructor solo para combos (Cantidad N precio Final X)
	 * Se reutiliza el campo en BD bsDescuento, con la finalidad de indicar el precio final de los productos del combo
	 * **/
	
	public PromocionExt(int codPromocion, Date fechaInicio, 
			Time horaInicio, Date fechaFinaliza, Time horaFinaliza, 
			int prioridad, char tipoPromocion, double porcDescuento, String nombrePromocion,
			int cantMinima, int cantRegalada, double precioFinal) {
		super(codPromocion, tipoPromocion,fechaInicio,horaInicio,fechaFinaliza,horaFinaliza,prioridad,porcDescuento);
		this.bsDescuento = precioFinal;
		this.nombrePromocion = nombrePromocion;
		this.cantMinima=cantMinima;
		this.cantRegalada=cantRegalada;
	}
	/**
	 * Constructor solo para combos
	 * **/
	public PromocionExt(int codPromocion, Date fechaInicio, 
			Time horaInicio, Date fechaFinaliza, Time horaFinaliza, 
			int prioridad, char tipoPromocion, double porcDescuento, double montoMinimo, 
			int cantidadMinima, String nombrePromocion) {
		super(codPromocion, tipoPromocion,fechaInicio,horaInicio,fechaFinaliza,horaFinaliza,prioridad,porcDescuento);
		this.nombrePromocion = nombrePromocion;
		this.montoMinimo = montoMinimo;
		this.cantMinima = cantidadMinima;
	}
	
	/**
	 * Constructor solo para cupon de descuento (puede ser sobre producto o total de compra)
	 * **/
	public PromocionExt(int codPromocion, 
			int numDetalle,
			Date fechaInicio, 
			Time horaInicio, 
			Date fechaFinaliza, 
			Time horaFinaliza, 
			int prioridad, 
			char tipoPromocion, 
			double porcDescuento,
			double bsDescuento,
			String codProducto,
			String nombrePromocion
			) {
		super(codPromocion,tipoPromocion,fechaInicio,horaInicio,fechaFinaliza,horaFinaliza,codProducto,porcDescuento);
		this.codPromocion = codPromocion;
		this.bsDescuento = bsDescuento;
		this.codProducto = codProducto;
		this.nombrePromocion = nombrePromocion;
		this.numDetalle = numDetalle;
	}
	
	/**
	 * Constructor solo para ahorro en compra (puede ser sobre producto o total de compra)
	 * **/
	public PromocionExt(int codPromocion, 
			int numDetalle,
			double porcDescuento,
			double montoMinimo,
			String codProducto,
			String nombrePromocion,
			int prioridad,
			char tipoPromocion
			) {
		super(codPromocion,porcDescuento,codProducto,numDetalle,prioridad, tipoPromocion);
		this.montoMinimo = montoMinimo;
		this.nombrePromocion = nombrePromocion;
	}

	public PromocionExt(int codPromocion, Date fechaInicio, Time horaInicio, 
			Date fechaFinaliza, Time horaFinaliza, char tipoPromocion, 
			int numDetalle, double montoMinimo, double bsBonoRegalo, String nombrePromocion, String acumulapremio, double sumMontoTotal, int sumCantidad) {
		super(codPromocion, 
				tipoPromocion, 
				fechaInicio, 
				horaInicio, 
				fechaFinaliza, 
				horaFinaliza,
				numDetalle);
		this.montoMinimo = montoMinimo;
		this.bsDescuento = bsBonoRegalo;
		this.nombrePromocion = nombrePromocion;
		this.acumulaPremio=acumulapremio;
		// BECO: WDIAZ 07-2012 Nuevas variables para extension de promocion Premio Ilusion
		this.sumMontoTotal = sumMontoTotal;
		this.sumCantidad = sumCantidad; 
	}
	
	public PromocionExt(int codPromocion, int numDetalle, String codProducto, 
			String nombrePromocion, String acumulaPremio){
		super(codPromocion, numDetalle,codProducto);
		this.nombrePromocion = nombrePromocion;
		this.acumulaPremio=acumulaPremio;
	}


	/**
	 * @return el bsDescuento o Bs en Bono regalo
	 */
	public double getBsDescuentoOBsBonoRegalo() {
		return bsDescuento;
	}

	/**
	 * @param bsDescuento el bsDescuento a establecer
	 */
	public void setBsDescuento(double bsDescuento) {
		this.bsDescuento = bsDescuento;
	}

	/**
	 * @return el cantMinima
	 */
	public int getCantMinima() {
		return cantMinima;
	}

	/**
	 * @param cantMinima el cantMinima a establecer
	 */
	public void setCantMinima(int cantMinima) {
		this.cantMinima = cantMinima;
	}

	/**
	 * @return el cantRegalada
	 */
	public int getCantRegalada() {
		return cantRegalada;
	}

	/**
	 * @param cantRegalada el cantRegalada a establecer
	 */
	public void setCantRegalada(int cantRegalada) {
		this.cantRegalada = cantRegalada;
	}

	/**
	 * @return el categoria
	 */
	public String getCategoria() {
		return categoria;
	}

	/**
	 * @param categoria el categoria a establecer
	 */
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}


	/**
	 * @return el marca
	 */
	public String getMarca() {
		return marca;
	}

	/**
	 * @param marca el marca a establecer
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}

	/**
	 * @return el montoMinimo
	 */
	public double getMontoMinimo() {
		return montoMinimo;
	}

	/**
	 * @param montoMinimo el montoMinimo a establecer
	 */
	public void setMontoMinimo(double montoMinimo) {
		this.montoMinimo = montoMinimo;
	}
	
	/**
	 * Obtiene las promociones de cupon de descuento desprendibles
	 * que estan activas en la base de datos
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<PromocionExt> getCuponesActivos(){
		return PromocionExtBD.cuponesActivos();
	}
	
	/**
	 * Obtiene todos los productos qu ese ven afectados por el cupon
	 * correspondiente a la promocion con codigo codPromocion
	 * @param codPromocion codigo de la promocion a la que pertenece el cupon
	 * @return Vector listado de productos
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Producto> getProductosPorCupon(int codPromocion){
		return PromocionExtBD.getProductosPorCupon(codPromocion);
	}


	
	public String getNombrePromocion() {
		return nombrePromocion;
	}
	
	public void setNombrePromocion(String nombrePromocion) {
		this.nombrePromocion = nombrePromocion;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se elimino variables no utilizadas
	* Fecha: agosto 2011
	*/
	public void resolverRegalo(){

		DecimalFormat df = new DecimalFormat("#,##0.00");
		int numEntregables = 0;
		
		try{
			//int numEntregables = (int)(montoOperacion/pE.getMontoMinimo());
			// se esta cambiando el montoOperacion global por el monto que viene del parametro monto cantidad
			if (getMontoMinimo()>0)
			    numEntregables = (int)(getSumMontoTotal()/getMontoMinimo());
			//
			if (getTipoPromocion()==Sesion.TIPO_PROMOCION_REGALO_CUPON && numEntregables>0){
				//31-07-2012 Agregada la opción de acumula premio en regalo_cupon. jperez
				if(numEntregables!=0 && getAcumulaPremio().equalsIgnoreCase("Y"))
					numEntregables=1;
				RegaloRegistrado rr = new RegaloRegistrado(numEntregables,
						0,0,0,null,this.getCodPromocion(),this.getNumDetalle());
				
				Venta.regalosRegistrados.add(rr);
				CR.me.mostrarAviso(" Cupon(es) para Sorteo "+getNombrePromocion()+": "+numEntregables, true);
				MensajesVentanas.aviso("Debe realizar entrega de "+numEntregables+" "+getNombrePromocion());		
			
			}else if(getTipoPromocion()==Sesion.TIPO_PROMOCION_REGALO_CALCOMANIA && numEntregables>0){
				RegaloRegistrado rr = new RegaloRegistrado(numEntregables,0,0,0,null,this.getCodPromocion(),this.getNumDetalle());
				Venta.regalosRegistrados.add(rr);
				CR.me.mostrarAviso(" Calcomania(s): "+numEntregables, true);
				MensajesVentanas.aviso("Debe realizar entrega de "+numEntregables+" "+getNombrePromocion());	
			
			}else if (getTipoPromocion()==Sesion.TIPO_PROMOCION_REGALO_BONO_REGALO && numEntregables>0){
				RegaloRegistrado rr = new RegaloRegistrado(getBsDescuentoOBsBonoRegalo()*numEntregables,
						0,0,0,null,this.getCodPromocion(),this.getNumDetalle());
				Venta.regalosRegistrados.add(rr);
				//DecimalFormat df = new DecimalFormat("#,##0.00");
				CR.me.mostrarAviso(" Bs. en BonoRegalo: "+df.format(getBsDescuentoOBsBonoRegalo()*numEntregables), true);
				MensajesVentanas.aviso("Debe realizar entrega de "+df.format(getBsDescuentoOBsBonoRegalo()*numEntregables)+" Bs. en BonoRegalo(s)");
				/**CR.me.mostrarAviso(" ticket de Bs.: "+df.format(regbs*numEntregables), true);
				MensajesVentanas.aviso("Debe realizar entrega de un ticket de "+df.format(regbs*numEntregables)+" Bs.");*/
				//CR.me.mostrarAviso("Debe hacer entrega de un cupon de 10% de descuento", true);
				//MensajesVentanas.aviso("Debe hacer entrega de un cupon de 10% de descuento");
			}else if (getTipoPromocion()==Sesion.TIPO_PROMOCION_REGALO_PRODUCTO){
				//numEntregables = getNumObsequiosEntregables(venta);
				numEntregables = getSumCantidad();
				if(numEntregables!=0 && getAcumulaPremio().equalsIgnoreCase("Y"))
					numEntregables=1;
				if (getAcumulaPremio()!=null){
					if (numEntregables!=0){
						CR.me.mostrarAviso(" Obsequio: "+numEntregables, true);
						MensajesVentanas.aviso("Debe realizar entrega de "+numEntregables+" "+getNombrePromocion());
						setCantRegalada(numEntregables);
						RegaloRegistrado rr = new RegaloRegistrado (numEntregables,0,0,0,null,this.getCodPromocion(),this.getNumDetalle());
						Venta.regalosRegistrados.add(rr);
					}
				}
			}
			}catch(Exception f){
			System.out.print(f);
		}
	}

	public double getSumMontoTotal() {
		return sumMontoTotal;
	}

	public void setSumMontoTotal(double sumMontoTotal) {
		this.sumMontoTotal = sumMontoTotal;
	}

	public int getSumCantidad() {
		return sumCantidad;
	}

	public void setSumCantidad(int sumCantidad) {
		this.sumCantidad = sumCantidad;
	}

	public int getNumObsequiosEntregables(Venta venta) {
		int numEntregables = 0;
		ResultSet prodComplementario = PromocionExtBD.hayComplementarios(venta,codPromocion);
		try{
			Iterator<DetalleTransaccion> i = (venta.getDetallesTransaccion()).iterator();
			prodComplementario.beforeFirst();
			while (prodComplementario.next()){
				String producto = prodComplementario.getString("codProducto");
				i= (venta.getDetallesTransaccion()).iterator();
				while (i.hasNext()){
					DetalleTransaccion dt =((DetalleTransaccion)i.next());
					if (dt.getProducto().getCodProducto().equals(producto)){
						numEntregables += dt.getCantidad();
					}
				}
			}
			if(numEntregables!=0 && acumulaPremio.equalsIgnoreCase("Y")){
				numEntregables=1;
			}
		}catch(Exception ex){System.out.println("Producto Complementario");}
		return numEntregables;
	}
	
	/****
	 * Obtiene las promociones de ahorro en compra vigentes
	 * @return Vector promociones vigentes de ahorro en compra
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<PromocionExt> getAhorrosEnCompra(){
		return PromocionExtBD.ahorrosEnCompra();
	}

	public static PromocionExt getAhorroEnCompra(int codPromocion){
		return PromocionExtBD.ahorroEnCompra(codPromocion);
	}
	/**
	 * @return el refProveedor
	 */
	public String getRefProveedor() {
		return refProveedor;
	}

	/**
	 * @param refProveedor el refProveedor a establecer
	 */
	public void setRefProveedor(String refProveedor) {
		this.refProveedor = refProveedor;
	}

	/**
	 * @return el bsDescuento
	 */
	public double getBsDescuento() {
		return bsDescuento;
	}
	
	/****
	 * Obtiene las promociones de ahorro en compra vigentes
	 * @return Vector promociones vigentes de ahorro en compra
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<PromocionExt> getAhorrosEnCompraPorProducto(String codProducto, int codPromocion, double montoTransaccion){
		return PromocionExtBD.ahorrosEnCompraPorProducto(codProducto,codPromocion, montoTransaccion);
	}

	/**
	 * @return el lineasCondiciones
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<String> getLineasCondiciones() {
		return lineasCondiciones;
	}

	/**
	 * @param lineasCondiciones el lineasCondiciones a establecer
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void setLineasCondiciones(Vector<String> lineasCondiciones) {
		this.lineasCondiciones = lineasCondiciones;
	}
	
	/**
	 * Obtiene las promociones de cupon de descuento desprendibles
	 * que estan activas en la base de datos
	 * **/
	public static Vector<PromocionExt> getCorporativasActivas(){
		return PromocionExtBD.corporativasActivas();
	}
	
	
	/**
	 * Obtiene la promocion corporativa a partir del codigo de la promocion
	 * @param codPromocion
	 * @return PromocionExt
	 */
	public static PromocionExt getPromocionCorporativaPorCodigo(int codPromocion){
		return PromocionExtBD.getPromocionCorporativaPorCodigo(codPromocion);	
	}
	
	/**
	 * Obtiene las promociones de combo correspondientes al producto p
	 * @param p
	 * @param variacion Cantidad de productos que se admite que no esten en la venta para obtener sus promociones
	 * @return Vector de promociones
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<PromocionExt> getPromocionesCombo(Producto p, int codPromocion){
		return PromocionExtBD.getPromocionesCombo(p, codPromocion);
	}

	/**
	 * @return el productosCombo
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<ProductoCombo> getProductosCombo() {
		return productosCombo;
	}

	/**
	 * @param productosCombo el productosCombo a establecer
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void setProductosCombo(Vector<ProductoCombo> productosCombo) {
		this.productosCombo = productosCombo;
	}
	
	/**
	 * Indica si el producto p esta promocionado en el combo correspondiente a 
	 * esta promocion
	 * @param p
	 * @return boolean
	 */
	public boolean comboContieneProducto(Producto p){
		for(int i=0;i<this.productosCombo.size();i++){
			if(((ProductoCombo)this.productosCombo.elementAt(i)).contieneProducto(p)){
				return true;
			}
		}
		return false;
	}
	
	public Object clone(){
		PromocionExt p = (PromocionExt)super.clone();
		p.setCategoria(this.categoria);
		p.setMarca(this.marca);
		p.setRefProveedor(this.refProveedor);
		p.setMontoMinimo(this.montoMinimo);
		p.setCantRegalada(this.cantRegalada);
		p.setCantMinima(this.cantMinima);
		p.setBsDescuento(this.bsDescuento);
		p.setNombrePromocion(this.nombrePromocion);
		return p;
	}
	
	/**
	 * Obtiene la promoción de combo correspondientes al codigo indicado
	 * @param p
	 * @param variacion Cantidad de productos que se admite que no esten en la venta para obtener sus promociones
	 * @return Vector de promociones
	 */
	public static PromocionExt getPromocionAplicadaCombo(int codPromocion){
		return PromocionExtBD.getPromocionAplicadaCombo(codPromocion, CR.meServ.getApartado()!=null);
	}
	
	/***
	 * Obtiene la promoción de producto gratis, se asume que solo existe una activa
	 * @return PromocionExt
	 */	public static PromocionExt getPromocionProductoGratis(){
		return PromocionExtBD.getPromocionProductoGratis();
	}
	 
	 /*
		* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
		* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
		* Fecha: agosto 2011
		*/
	 public static Vector<PromocionExt> getPromocionesComboCantidades(
			 int codPromocion, 
			 String codProducto, 
			 Vector<String> codsDepartamento,
			 String marca, 
			 String linea,
			 String seccion, 
			 String refProveedor
			 ){
		 return PromocionExtBD.getPromocionesComboCantidades(
				 codPromocion, 
				 codProducto, 
				 codsDepartamento,
				 marca, 
				 linea,
				 seccion, 
				 refProveedor);
	 }
	 
	 /**
	  * Agrega la información del detalle indicado en numDetalle al vector de ProductoCombo
	  * de la promoción indicada por codPromocion
	  */
	 /*
		* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
		* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
		* Fecha: agosto 2011
		*/
	 public static boolean recalcularProductosCombo(int codPromocion, int numDetalle,Vector<ProductoCombo> productosDef){
		 //Si la promoción ya existe en el hashtable
		 boolean combo = false;
		if (Sesion.productosCombo.containsKey(codPromocion+"")){
			combo = true;
			//Creo el objeto promociónExt
			PromocionExt promo = PromocionExt.getPromocionAplicadaCombo(codPromocion);
			//Capturo el vector en memoria de productos de la promocion a modificar
			Vector<ProductoCombo> productosComboMemoria = Sesion.productosCombo.get(codPromocion+"");
			
			//Capturo el vector de productos q modificaran la promocion
			//Como se proporciona un detalle deberia devolver un solo producto
			Vector<ProductoCombo> productosComboNew = ProductoCombo.getProductosCombo(promo, numDetalle);
			if (productosComboNew.size()>0){
				ProductoCombo producto = productosComboNew.elementAt(0);
				for(ProductoCombo prod:productosComboMemoria){
					if(producto.getCodNoConsecutivo().equalsIgnoreCase(prod.getCodNoConsecutivo())){
						switch(promo.getTipoPromocion()){
						case Sesion.TIPO_PROMOCION_DESCUENTO_EN_COMBO:
						case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_PRODUCTO:						
							prod.addCodigoProducto(producto.getCodigosAsociados().elementAt(0));
							break;
						}
					}
				}
				//Se agregan los productos en el vector de productos auxiliar
				boolean agregar = true;
				for(ProductoCombo prod:productosDef){
					if(producto.getCodNoConsecutivo().equalsIgnoreCase(prod.getCodNoConsecutivo())){
						agregar = false;
						switch(promo.getTipoPromocion()){
						case Sesion.TIPO_PROMOCION_DESCUENTO_EN_COMBO:
						case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_PRODUCTO:						
							prod.addCodigoProducto(producto.getCodigosAsociados().elementAt(0));
							break;
						}
					}
				}
				if(agregar){
					switch(promo.getTipoPromocion()){
					case Sesion.TIPO_PROMOCION_DESCUENTO_EN_COMBO:
					case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_PRODUCTO:						
						productosDef.add(producto);
					}
					
				}
			}
		//Si no existe agregamos todos los detalles actuales de la bd.
		}else{
			PromocionExt promo = PromocionExt.getPromocionAplicadaCombo(codPromocion);
			if (promo != null){
				combo = true;
			}
			
			
		}
		return combo;
		 
		 /*
		 Iterator<String> iterador =  Sesion.productosCombo.keySet().iterator();
		 boolean found = false;
		 while(iterador.hasNext()){
			 int codPromo = Integer.parseInt((String)iterador.next());
			 PromocionExt promo = PromocionExt.getPromocionAplicadaCombo(codPromo);
			 if(promo.getCodPromocion()==codPromocion){
				 found = true;
				 Vector<ProductoCombo> productos = ProductoCombo.getProductosCombo((PromocionExt)promo, numDetalle);
				 if(productos.size()!=0){
					 ProductoCombo pc = (ProductoCombo)productos.elementAt(0);
					 Vector<ProductoCombo> productosCombo = Sesion.productosCombo.get(promo.getCodPromocion()+"");
					 for(int i=0;i<productosCombo.size();i++){
						ProductoCombo producto = (ProductoCombo)productosCombo.elementAt(i);
						if(producto.getCodNoConsecutivo().equalsIgnoreCase(pc.getCodNoConsecutivo())){
							switch(promo.getTipoPromocion()){
							case Sesion.TIPO_PROMOCION_DESCUENTO_EN_COMBO:
							case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_PRODUCTO:
								producto.addCodigoProducto((String)pc.getCodigosAsociados().elementAt(0));
								break;
							}
						}
					 }
				 }
			 }
		 }*/
	 }
	 
	 /**
	  * Si la promoción es de cupones de descuento obtiene el 
	  * listado de cupones validos
	  * @return Vector
	  * 			posicion 0: numtienda
	  * 			posicion 1:	fecha
	  * 			posicion 2: numcaja
	  * 			posicion 3: numtransaccion
	  * 			posicion 4: codcupon
	  */
	 public Vector<Vector<Object>> getCuponesValidos(){
		 Vector<Vector<Object>> validos = new Vector<Vector<Object>>();
		 if(this.getTipoPromocion()==Sesion.TIPO_PROMOCION_CUPON_DESCUENTO_PORCENTAJE ||
				 this.getTipoPromocion()==Sesion.TIPO_PROMOCION_CUPON_DESCUENTO_BS){
			 validos =  PromocionExtBD.getCuponesValidos(this);
		 }
		 return validos;
	 }

	public String getAcumulaPremio() {
		return acumulaPremio;
	}

	public void setAcumulaPremio(String acumulaPremio) {
		this.acumulaPremio = acumulaPremio;
	}
	//BECO: WDIAZ 07-2012
	public static void crearTablaPromoTicket()
	 {
		class hiloCrearTabla extends Thread{
			public void run() {
				try {
					PromocionExtBD.crearTablaPromoTicket();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	      }//cerramos el Hilo
		new hiloCrearTabla().start();
	}
	
	//jperez 09/2012
	public static void actualizarTablaPromoTicket(int codPromocion)
	 {
		PromocionExtBD.actualizarTablaPromoTicket(codPromocion);
	}

	//jperez 09/2012
	public static boolean recalcularAhorroEnCompra(int codPromocion,
			int numDetalle,Vector<Producto> productosAhorroAux) {
		 TreeMap<PromocionExt,Vector<Producto>> patrocinantesServ = CR.meServ.getProductosPatrocinantes();
		 TreeMap<PromocionExt,Vector<Producto>> patrocinantesVenta = CR.meVenta.getProductosPatrocinantes();
		 //Capturo la promoción asociada al codigo dado
		 PromocionExt promo = PromocionExt.getAhorroEnCompra(codPromocion);
		 //capturo el producto asociado al numero detalle proporcionado
		 Producto p = UtilesBD.getPatrocinante(codPromocion, numDetalle);
		 //Si la promo existe y es ahorro en compra
		 if(promo!=null){
			 //Y ademas el producto se encuentra en la BD de la caja
			 if( p!=null){
				 //Verifico si la promoción ya se encuentra en el vector de promociones alojado en memoria
				 if(patrocinantesServ.containsKey(promo)){
					 //Capturo el vector de productos en memoria asociados a la promo
					 Vector<Producto> prodsMemory = patrocinantesServ.get(promo);
					 boolean contains = false;
					 //Itero en los productos en memoria hasta conseguir el que deseo insertar
					 for(Producto prod:prodsMemory){
						 //Si el producto ya está en memoria no hago nada y termino el ciclo.
						 if(prod.getCodProducto().equals(p.getCodProducto())){
						 	contains = true;
						 	break;
						 }
					 }
					 //Si el producto no está en memoria lo agrego.
					 if(!contains) prodsMemory.add(p);
				//Si la promoción no se encuentra en memoria
				 }else{
					 //Creo un nuevo vector de productos con el producto que quiero agregar
					 Vector<Producto> productos = new Vector<Producto>();
					// p = (Producto)p.clone();
					 productos.add(p);
					 //Agrego la promocion y su vector en memoria
					 patrocinantesServ.put(promo, productos);
				 }
	
				 //repito el procedimiento para el vector en Maquina de estado venta
				 if(patrocinantesVenta.containsKey(promo)){
					 Vector<Producto> prodsMemory = patrocinantesVenta.get(promo);
					 boolean contains = false;
					 for(Producto prod:prodsMemory){
						 if(prod.getCodProducto().equals(p.getCodProducto())){
						 	contains = true;
						 	break;
						 }
					 }
					 if(!contains){
						 p = (Producto)p.clone();
						 prodsMemory.add(p);
					 }
				 }else{
					 Vector<Producto> productos = new Vector<Producto>();
					 p = (Producto)p.clone();
					 productos.add(p);
					 patrocinantesVenta.put(promo, productos);
				 }
				 boolean contains = false;
				 //Agrego el producto en el vector nuevo que reemplazará el actual en la promoción
				 for(Producto prod:productosAhorroAux){
					 if(prod.getCodProducto().equals(p.getCodProducto())){
					 	contains = true;
					 	break;
					 }
				 }
				 if(productosAhorroAux.size()>760){
					 System.out.println("CHECK");
				 }
				 if(!contains) {
					 p = (Producto)p.clone();
					 if (p.getCodProducto().equals("000867620003")){
						 System.out.println("O.o");
					 }
					 productosAhorroAux.add(p);
				 }
			 }
			 return true;
		 }
		
		 
		
		// TODO Auto-generated method stub
		return false;
	}
	
			
	
}