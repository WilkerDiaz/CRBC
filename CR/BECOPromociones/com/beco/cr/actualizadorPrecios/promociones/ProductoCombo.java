/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.cr.actualizadorPrecios.promociones
 * Programa   : ProductoCombo.java
 * Creado por : jgraterol
 * Creado en  : 05/08/2008 11:18:05 AM
 *
 * (c) CENTROBECO, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * ----------------------------------------------------------------------------- 
 * Versión     : 1.O 
 * Fecha       : 05/08/2008 11:18:05 AM
 * Analista    : JGRATEROL
 * Descripción : Objeto que agrupa los productos que participan en un combo
 * =============================================================================
 * */

package com.beco.cr.actualizadorPrecios.promociones;

import java.util.Vector;

import com.beco.cr.actualizadorPrecios.promociones.ManejadorBD.ProductoComboBD;
import com.becoblohm.cr.manejarventa.Producto;

public class ProductoCombo {
	
	private String codNoConsecutivo;
	private int cantidadMinima;
	private int cantidadARegalar;
	//Codigos de productos asociados, estos si incluyen el consecutivo
	private Vector<String> codigosAsociados = new Vector<String>();
	
	private Vector<String> categorias = new Vector<String>();
	private Vector<String> departamentos = new Vector<String>();
	private Vector<Linea> lineas= new Vector<Linea>();
	private Vector<Seccion> secciones= new Vector<Seccion>();
	private Vector<String> marcas= new Vector<String>();
	private Vector<Referencia> referencias= new Vector<Referencia>();
	
	
	
	/**
	 * @param codNoConsecutivo
	 * @param cantidadMinima
	 * @param cantidadARegalar
	 */
	public ProductoCombo(String codNoConsecutivo, 
			int cantidadMinima, 
			int cantidadARegalar) {
		super();
		this.codNoConsecutivo = codNoConsecutivo;
		this.cantidadMinima = cantidadMinima;
		this.cantidadARegalar = cantidadARegalar;
	}
	
	/**
	 * @param codNoConsecutivo
	 * @param cantidadMinima
	 * @param cantidadARegalar
	 * @param codigosAsociados
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public ProductoCombo(String codNoConsecutivo, 
			int cantidadMinima, 
			int cantidadARegalar, 
			Vector<String> codigosAsociados) {
		super();
		this.codNoConsecutivo = codNoConsecutivo;
		this.cantidadMinima = cantidadMinima;
		this.cantidadARegalar = cantidadARegalar;
		this.codigosAsociados = codigosAsociados;
	}

	/**
	 * @return el codigosAsociados
	 */
	public Vector<String> getCodigosAsociados() {
		return codigosAsociados;
	}
	/**
	 * @param codigosAsociados el codigosAsociados a establecer
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void setCodigosAsociados(Vector<String> codigosAsociados) {
		this.codigosAsociados = codigosAsociados;
	}
	/**
	 * @return el codNoConsecutivo
	 */
	public String getCodNoConsecutivo() {
		return codNoConsecutivo;
	}
	/**
	 * @param codNoConsecutivo el codNoConsecutivo a establecer
	 */
	public void setCodNoConsecutivo(String codNoConsecutivo) {
		this.codNoConsecutivo = codNoConsecutivo;
	}
	/**
	 * @return el cantidadARegalar
	 */
	public int getCantidadARegalar() {
		return cantidadARegalar;
	}
	/**
	 * @param cantidadARegalar el cantidadARegalar a establecer
	 */
	public void setCantidadARegalar(int cantidadARegalar) {
		this.cantidadARegalar = cantidadARegalar;
	}
	/**
	 * @return el cantidadMinima
	 */
	public int getCantidadMinima() {
		return cantidadMinima;
	}
	/**
	 * @param cantidadMinima el cantidadMinima a establecer
	 */
	public void setCantidadMinima(int cantidadMinima) {
		this.cantidadMinima = cantidadMinima;
	}
	
	/**
	 * Agrega un codigo de producto si no existe ya en la lista
	 * Tambien verifica que el codigo del producto corresponda con el
	 * codigo SIN EL CONSECUTIVO
	 * @param prod
	 */
	public void addCodigoProducto(String prod) {
		if(!this.codigosAsociados.contains(prod)){
			this.codigosAsociados.addElement(prod);
		}
	}
	
	/**
	 * Borra un codigo de producto de la lista
	 * @param prod
	 */
	public void removeCodigoProducto(String prod) {
		if(this.codigosAsociados.contains(prod)){
			this.codigosAsociados.remove(prod);
		}
	}
	
	/**
	 * Indica si el producto se encuentra en la lista de codigos
	 * asociados
	 * @param prod
	 * @return
	 */
	public boolean contieneProducto(Producto prod){
		return this.codigosAsociados.contains(prod.getCodProducto()) ||
				(this.categorias.size()!=0 && ((String)this.categorias.elementAt(0)).equals(prod.getCategoria())) ||
				(this.departamentos.size()!=0 && ((String)this.departamentos.elementAt(0)).equals(prod.getCodDepartamento())) ||
				(this.marcas.size()!=0 &&((String)this.marcas.elementAt(0)).equals(prod.getMarca())) ||
				(this.referencias.size()!=0 &&
						((Referencia)this.referencias.elementAt(0)).getCodDepartamento().equalsIgnoreCase(prod.getCodDepartamento()) &&
						((Referencia)this.referencias.elementAt(0)).getCodLinea().equalsIgnoreCase(prod.getLineaSeccion()) &&
						((Referencia)this.referencias.elementAt(0)).getCodSeccion()==prod.getSeccion() &&
						((Referencia)this.referencias.elementAt(0)).getCodReferencia().equalsIgnoreCase(prod.getReferenciaProveedor())
				) ||
				(this.lineas.size()!=0 && 
						(((Linea)this.lineas.elementAt(0)).getCodlinea().equalsIgnoreCase(prod.getLineaSeccion()) &&  
						((Linea)this.lineas.elementAt(0)).getCodDepartamento().equalsIgnoreCase(prod.getCodDepartamento()))) ||
				(this.secciones.size()!=0 &&
						(((Seccion)this.secciones.elementAt(0)).getLinea().equalsIgnoreCase(prod.getLineaSeccion()) &&  
						((Seccion)this.secciones.elementAt(0)).getCodDepartamento().equalsIgnoreCase(prod.getCodDepartamento()) && 
						((Seccion)this.secciones.elementAt(0)).getSeccion()==prod.getSeccion()));
	}
	
	/**
	 * Obtiene los productos combo correspondientes a la promocion de descuento en combo
	 */
	/*public static Vector getProductosDescuentoEnCombo(PromocionExt promo, boolean porProducto){
		if(porProducto)
			return ProductoComboBD.getProductosDescuentoEnComboProducto(promo);
		else
			return ProductoComboBD.getProductosDescuentoEnComboLinea(promo);
	}*/
	
	/**
	 * Obtiene los productos combo correspondientes a la promocion de combos por cantidades
	 */
	public static Vector<ProductoCombo> getProductosCombo(PromocionExt promo, int numdetalle){
		return ProductoComboBD.getProductosCombo(promo, numdetalle);
	}
	
	/**
	 * Implementa el método clon de Object
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Object clone(){
		Vector<String> codigos = new Vector<String>();
		Vector<String> categorias =  new Vector<String>();
		Vector<String> departamentos =  new Vector<String>();
		Vector<Linea> lineas =  new Vector<Linea>();
		Vector<Seccion> secciones = new Vector<Seccion>();
		Vector<String> marcas =  new Vector<String>();
		Vector<Referencia> referencias =  new Vector<Referencia>();
		
		ProductoCombo clon = new ProductoCombo(this.codNoConsecutivo, this.cantidadMinima, this.getCantidadARegalar());
		for(int i=0;i<this.codigosAsociados.size();i++){
			codigos.addElement(this.codigosAsociados.elementAt(i));
		}
		for(int i=0;i<this.categorias.size();i++){
			categorias.addElement(this.categorias.elementAt(i));
		}
		for(int i=0;i<this.departamentos.size();i++){
			departamentos.addElement(this.departamentos.elementAt(i));
		}
		for(int i=0;i<this.marcas.size();i++){
			marcas.addElement(this.marcas.elementAt(i));
		}
		for(int i=0;i<this.referencias.size();i++){
			referencias.addElement(this.referencias.elementAt(i));
		}
		for(int i=0;i<this.lineas.size();i++){
			lineas.addElement(new Linea(((Linea)this.lineas.elementAt(i)).getCodDepartamento(),((Linea)this.lineas.elementAt(i)).getCodlinea()));
		}
		for(int i=0;i<this.secciones.size();i++){
			secciones.addElement(new Seccion(((Seccion)this.secciones.elementAt(i)).getCodDepartamento(),((Seccion)this.secciones.elementAt(i)).getLinea(),((Seccion)this.secciones.elementAt(i)).getSeccion()));
		}
		clon.setCodigosAsociados(codigos);
		clon.setCategorias(categorias);
		clon.setDepartamentos(departamentos);
		clon.setMarcas(marcas);
		clon.setReferencias(referencias);
		clon.setLineas(lineas);
		clon.setSecciones(secciones);
		
		return clon;
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<String> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(Vector<String> departamentos) {
		this.departamentos = departamentos;
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Linea> getLineas() {
		return lineas;
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void setLineas(Vector<Linea> lineas) {
		this.lineas = lineas;
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<String> getMarcas() {
		return marcas;
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void setMarcas(Vector<String> marcas) {
		this.marcas = marcas;
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Referencia> getReferencias() {
		return referencias;
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void setReferencias(Vector<Referencia> referencias) {
		this.referencias = referencias;
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Seccion> getSecciones() {
		return secciones;
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void setSecciones(Vector<Seccion> secciones) {
		this.secciones = secciones;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<String> getCategorias() {
		return categorias;
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void setCategorias(Vector<String> categorias) {
		this.categorias = categorias;
	}
	
	//***************************************************
	//Agregan a los vectores correspondientes
	//***************************************************
	
	public void addCategoria(String categoria){
		this.categorias.addElement(categoria);
	}
	
	public void addDepartamento(String codDepartamento){
		if(!this.departamentos.contains(codDepartamento))
			this.departamentos.addElement(codDepartamento);
	}
	
	public void addLinea(Linea linea){
		boolean contiene = false;
		for(int i=0;i<this.lineas.size();i++){
			if(Integer.parseInt(((Linea)this.lineas.elementAt(i)).getCodlinea())==Integer.parseInt((linea.getCodlinea())) && 
					Integer.parseInt(((Linea)this.lineas.elementAt(i)).getCodDepartamento())==Integer.parseInt((linea.getCodDepartamento()))){
				contiene=true;
				break;
			}
		}
		if(!contiene)
			this.lineas.addElement(linea);
	}
	
	
	public void addSeccion(Seccion seccion){
		this.secciones.addElement(seccion);
	}
	
	public void addMarca(String marca){
		if(!this.marcas.contains(marca))
			this.marcas.addElement(marca);
	}
	
	public void addRefProveedor(Referencia refProveedor){
		if(!this.contieneReferencia(refProveedor))
			this.referencias.addElement(refProveedor);
	}
	
	public boolean contieneReferencia (Referencia refProveedor)
	{
		Referencia referencia = null;
		
		int codDepartamento = Integer.parseInt(refProveedor.getCodDepartamento());
		int codLinea = Integer.parseInt(refProveedor.getCodLinea());
		int codSeccion = refProveedor.getCodSeccion();
		String codReferencia = refProveedor.getCodReferencia();
		
		int codDepartamentoV = 0;
		int codLineaV = 0;
		int codSeccionV = 0;
		String codReferenciaV = null;
		
		for (int i = 0; i<this.referencias.size(); i++)
		{
			 referencia = (Referencia) this.referencias.get(i);
			 codDepartamentoV = Integer.parseInt(referencia.getCodDepartamento());
			 codLineaV = Integer.parseInt(referencia.getCodLinea());
			 codSeccionV = referencia.getCodSeccion();
			 codReferenciaV = referencia.getCodReferencia();
			 
			 if (codDepartamento == codDepartamentoV && codLinea == codLineaV && codSeccion == codSeccionV && codReferencia.trim().equals(codReferenciaV.trim()))
				 return true;
			 
		}
		
		return false;
			
			
	}

	public static Vector<ProductoCombo> getProductosComboEliminar(
			PromocionExt promo) {
		return ProductoComboBD.getProductosComboEliminar(promo);
	}
	
	
	
}
