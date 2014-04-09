/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.cr.actualizadorPrecios.promociones.ManejadorBD
 * Programa   : ProductoComboBD.java
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
 * Descripción : Mediador con la BD de la clase ProductoCombo
 * =============================================================================
 * */
package com.beco.cr.actualizadorPrecios.promociones.ManejadorBD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.beco.cr.actualizadorPrecios.promociones.Linea;
import com.beco.cr.actualizadorPrecios.promociones.ProductoCombo;
import com.beco.cr.actualizadorPrecios.promociones.PromocionExt;
import com.beco.cr.actualizadorPrecios.promociones.Referencia;
import com.beco.cr.actualizadorPrecios.promociones.Seccion;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.mediadoresbd.Conexiones;

public class ProductoComboBD {

	private static final Logger logger = Logger.getLogger(ProductoComboBD.class);
	
	/**
	 * Obtiene los productos participantes en la promocion de descuento en combo por
	 * codigo de producto
	 * @param promo
	 * @return Vector de ProductoCombo
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<ProductoCombo> getProductosDescuentoEnComboProducto(PromocionExt promo){
		Vector<ProductoCombo> productos = new Vector<ProductoCombo>();
		ResultSet productosCombo = null;
		String query = "SELECT DISTINCT dpe.codProducto, dpe. cantMinima, dpe.cantRegalada, " +
									" dpe.prodSinConsecutivo "
			+" FROM detallepromocionext dpe "+
			" WHERE dpe.codPromocion="+promo.getCodPromocion()+" "+
			" AND dpe.estadoRegistro='"+Sesion.PROMOCION_ACTIVA+"' order by dpe.prodSinConsecutivo asc "; //esta activa
		try{
			productosCombo = Conexiones.realizarConsulta(query, true);
			productosCombo.beforeFirst();
			while(productosCombo.next()){
				String codNoConsecutivo =  productosCombo.getString("prodSinConsecutivo");
				int cantMinima = productosCombo.getInt("cantMinima");
				int cantRegalada = productosCombo.getInt("cantRegalada");
				ProductoCombo prod = new ProductoCombo(codNoConsecutivo,cantMinima,cantRegalada);
				String codProducto = productosCombo.getString("codProducto");
				Vector<String> codigosAsociados = new Vector<String>();
				prod.setCodigosAsociados(codigosAsociados);
				prod.addCodigoProducto(codProducto);
				while(productosCombo.next()){
					if(!productosCombo.getString("prodSinConsecutivo").equalsIgnoreCase(codNoConsecutivo)) {
						productosCombo.previous();
						break;
					}
					codProducto = productosCombo.getString("codProducto");
					prod.addCodigoProducto(codProducto);
				}
				productos.addElement(prod);
			}
		}catch(Exception ex){
			logger.error("cuponesActivos()", ex);
			ex.printStackTrace();
		}finally{
			if (productosCombo!=null){
				try{
					productosCombo.close();
				}catch(SQLException e){
					logger.error("cuponesActivos()",e);
				}
				productosCombo=null;
			}
		}
		return productos;
	}
	
	/**
	 * Obtiene los productos participantes en la promocion de descuento en combo por
	 * linea
	 * @param promo
	 * @return Vector de ProductoCombo
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<ProductoCombo> getProductosDescuentoEnComboLinea(PromocionExt promo){
		Vector<ProductoCombo> productos = new Vector<ProductoCombo>();
		ResultSet productosCombo = null;
		String query = "SELECT DISTINCT p.codproducto, dpe. cantMinima, dpe.cantRegalada, " +
									" dpe.departamento, dpe.linea "
			+" FROM detallepromocionext dpe INNER JOIN producto p " +
					" ON (dpe.linea=p.codlineaseccion && dpe.departamento=p.coddepartamento) "+
			" WHERE dpe.codPromocion="+promo.getCodPromocion()+" "+
			" AND dpe.estadoRegistro='"+Sesion.PROMOCION_ACTIVA+"' order by dpe.departamento, dpe.linea "; //esta activa
		try{
			productosCombo = Conexiones.realizarConsulta(query, true);
			productosCombo.beforeFirst();
			while(productosCombo.next()){
				String codLinea =  productosCombo.getString("linea");
				String departamento = productosCombo.getString("departamento");
				int cantMinima = productosCombo.getInt("cantMinima");
				int cantRegalada = productosCombo.getInt("cantRegalada");
				ProductoCombo prod = new ProductoCombo(codLinea,cantMinima,cantRegalada);
				String codProducto = productosCombo.getString("codProducto");
				Vector<String> codigosAsociados = new Vector<String>();
				prod.setCodigosAsociados(codigosAsociados);
				prod.addCodigoProducto(codProducto);
				while(productosCombo.next()){
					if(!productosCombo.getString("linea").equalsIgnoreCase(codLinea) ||
							!productosCombo.getString("departamento").equalsIgnoreCase(departamento)) {
						productosCombo.previous();
						break;
					}
					codProducto = productosCombo.getString("codProducto");
					prod.addCodigoProducto(codProducto);
				}
				productos.addElement(prod);
			}
		}catch(Exception ex){
			logger.error("cuponesActivos()", ex);
		}finally{
			if (productosCombo!=null){
				try{
					productosCombo.close();
				}catch(SQLException e){
					logger.error("cuponesActivos()",e);
				}
				productosCombo=null;
			}
		}
		return productos;
	}
	
	
	/**
	 * Obtiene los productos participantes en las promociones de combo
	 * @param promo
	 * @return Vector de ProductoCombo
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<ProductoCombo> getProductosCombo(PromocionExt promo, int numDetalle){
		Vector<ProductoCombo> productos = new Vector<ProductoCombo>();
		ResultSet productosCombo = null;
		
		String departamento = null;
		String marca = null;
		String codReferencia = null;
		Linea linea = null;
		Referencia referencia = null;
		String codLinea = null;
		int codSeccion = 0; 
		
		String query = "SELECT DISTINCT dpe.cantMinima, dpe.cantRegalada, dpe.prodSinConsecutivo, ";
		
		switch (promo.getTipoPromocion()) {
			case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_DEPARTAMENTO:
				query+=" dpe.departamento ";
				break;
			case Sesion.TIPO_PROMOCION_DESCUENTO_EN_COMBO_LINEA:
			case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_LINEA:
				query+=" dpe.departamento, dpe.linea ";
				break;
			case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_MARCA:
				query+=" dpe.marca ";
				break;
			case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_REFERENCIA:
				query+=" dpe.departamento, dpe.linea, dpe.codSeccion, dpe.refProveedor ";
				break;
			case Sesion.TIPO_PROMOCION_DESCUENTO_EN_COMBO:
			case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_PRODUCTO:
				query+=" dpe.codproducto ";
				break;
			case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_SECCION:
				query+=" dpe.departamento, dpe.linea, dpe.codSeccion ";
				break;
			case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_CATEGORIA:
				query+=" dpe.categoria ";
				break;
		}
		
		query+=" FROM detallepromocionext dpe "+
			" WHERE dpe.codPromocion="+promo.getCodPromocion()+" ";
		
		if(numDetalle!=0)
			query+=" AND dpe.numDetalle="+numDetalle+" ";
		
		query+=" AND dpe.estadoRegistro='"+Sesion.PROMOCION_ACTIVA+"' order by prodSinConsecutivo "; //esta activa
		
		try{
			productosCombo = Conexiones.realizarConsulta(query, true);
			productosCombo.beforeFirst();
			while(productosCombo.next()){
				String prodSinConsecutivo = productosCombo.getString("prodSinConsecutivo");
				int cantMinima = productosCombo.getInt("cantMinima");
				int cantRegalada = productosCombo.getInt("cantRegalada");
				ProductoCombo prod = new ProductoCombo(prodSinConsecutivo, cantMinima,cantRegalada);
				
				switch(promo.getTipoPromocion()){
				case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_DEPARTAMENTO:
					departamento = productosCombo.getString("departamento");
					prod.addDepartamento(departamento);
					while(productosCombo.next()){
						if(!productosCombo.getString("prodSinConsecutivo").equalsIgnoreCase(prodSinConsecutivo)) {
							productosCombo.previous();
							break;
						}
						departamento = productosCombo.getString("departamento");
						prod.addDepartamento(departamento);
					}
					break;
				case Sesion.TIPO_PROMOCION_DESCUENTO_EN_COMBO_LINEA:
				case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_LINEA:
					departamento = productosCombo.getString("departamento");
					codLinea = productosCombo.getString("linea");
					linea = new Linea(departamento,codLinea);
					prod.addLinea(linea);
					while(productosCombo.next()){
						if(!productosCombo.getString("prodSinConsecutivo").equalsIgnoreCase(prodSinConsecutivo)) {
							productosCombo.previous();
							break;
						}
						codLinea =  productosCombo.getString("linea");
						departamento = productosCombo.getString("departamento");
						linea = new Linea(departamento,codLinea);
						prod.addLinea(linea);
					}
					break;
				case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_MARCA:
					marca = productosCombo.getString("marca");
					prod.addMarca(marca);
					while(productosCombo.next()){
						if(!productosCombo.getString("prodSinConsecutivo").equalsIgnoreCase(prodSinConsecutivo)) {
							productosCombo.previous();
							break;
						}
						marca = productosCombo.getString("marca");
						prod.addMarca(marca);
					}
					break;
				case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_REFERENCIA:
					departamento = productosCombo.getString("departamento");
					codLinea =  productosCombo.getString("linea");
					codSeccion = productosCombo.getInt("codSeccion");
					codReferencia = productosCombo.getString("refProveedor");
					referencia = new Referencia(departamento,codLinea, codSeccion, codReferencia);
					prod.addRefProveedor(referencia);
					while(productosCombo.next()){
						if(!productosCombo.getString("prodSinConsecutivo").equalsIgnoreCase(prodSinConsecutivo)) {
							productosCombo.previous();
							break;
						}
						departamento = productosCombo.getString("departamento");
						codLinea =  productosCombo.getString("linea");
						codSeccion = productosCombo.getInt("codSeccion");
						codReferencia = productosCombo.getString("refProveedor");
						referencia = new Referencia(departamento,codLinea, codSeccion, codReferencia);
						prod.addRefProveedor(referencia);
					}
					break;
				case Sesion.TIPO_PROMOCION_DESCUENTO_EN_COMBO:
				case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_PRODUCTO:
					String codProducto = productosCombo.getString("codProducto");
					String prodNoConsecutivo = productosCombo.getString("prodSinConsecutivo");
					Vector<String> codigosAsociados = new Vector<String>();
					prod.setCodigosAsociados(codigosAsociados);
					prod.addCodigoProducto(codProducto);
					while(productosCombo.next()){
						if(!productosCombo.getString("prodSinConsecutivo").equalsIgnoreCase(prodNoConsecutivo)) {
							productosCombo.previous();
							break;
						}
						codProducto = productosCombo.getString("codProducto");
						prod.addCodigoProducto(codProducto);
					}
					break;
				case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_SECCION:
					departamento = productosCombo.getString("departamento");
					codLinea = productosCombo.getString("linea");
					codSeccion = productosCombo.getInt("codSeccion");
					Seccion seccion = new Seccion(departamento,codLinea,codSeccion);
					prod.addSeccion(seccion);
					while(productosCombo.next()){
						if(!productosCombo.getString("prodSinConsecutivo").equalsIgnoreCase(prodSinConsecutivo)) {
							productosCombo.previous();
							break;
						}
						codLinea =  productosCombo.getString("linea");
						departamento = productosCombo.getString("departamento");
						codSeccion = productosCombo.getInt("codSeccion");
						seccion = new Seccion(departamento,codLinea, codSeccion);
						prod.addSeccion(seccion);
					}
					break;
				case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_CATEGORIA:
					String categoria = productosCombo.getString("categoria");
					prod.addCategoria(categoria);
					while(productosCombo.next()){
						if(!productosCombo.getString("prodSinConsecutivo").equalsIgnoreCase(prodSinConsecutivo)) {
							productosCombo.previous();
							break;
						}
						categoria = productosCombo.getString("categoria");
						prod.addCategoria(categoria);
					}
					break;
				}
				productos.addElement(prod);
			}
		}catch(Exception ex){
			logger.error("cuponesActivos()", ex);
		}finally{
			if (productosCombo!=null){
				try{
					productosCombo.close();
				}catch(SQLException e){
					logger.error("cuponesActivos()",e);
				}
				productosCombo=null;
			}
		}
		return productos;
	}

	public static Vector<ProductoCombo> getProductosComboEliminar(
			PromocionExt promo) {
		Vector<ProductoCombo> productos = new Vector<ProductoCombo>();
		ResultSet productosCombo = null;
		
		String departamento = null;
		String marca = null;
		String codReferencia = null;
		Linea linea = null;
		Referencia referencia = null;
		String codLinea = null;
		int codSeccion = 0; 
		
		String query = "SELECT DISTINCT dpe.cantMinima, dpe.cantRegalada, dpe.prodSinConsecutivo, ";
		
		switch (promo.getTipoPromocion()) {
			case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_DEPARTAMENTO:
				query+=" dpe.departamento ";
				break;
			case Sesion.TIPO_PROMOCION_DESCUENTO_EN_COMBO_LINEA:
			case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_LINEA:
				query+=" dpe.departamento, dpe.linea ";
				break;
			case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_MARCA:
				query+=" dpe.marca ";
				break;
			case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_REFERENCIA:
				query+=" dpe.departamento, dpe.linea, dpe.codSeccion, dpe.refProveedor ";
				break;
			case Sesion.TIPO_PROMOCION_DESCUENTO_EN_COMBO:
			case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_PRODUCTO:
				query+=" dpe.codproducto ";
				break;
			case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_SECCION:
				query+=" dpe.departamento, dpe.linea, dpe.codSeccion ";
				break;
			case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_CATEGORIA:
				query+=" dpe.categoria ";
				break;
		}
		
		query+=" FROM detallepromocionext dpe "+
			" WHERE dpe.codPromocion="+promo.getCodPromocion()+" ";
			
		query+=" AND dpe.estadoRegistro='"+Sesion.PROMOCION_INACTIVA+"' order by prodSinConsecutivo "; //esta activa
		
		try{
			productosCombo = Conexiones.realizarConsulta(query, true);
			productosCombo.beforeFirst();
			while(productosCombo.next()){
				String prodSinConsecutivo = productosCombo.getString("prodSinConsecutivo");
				int cantMinima = productosCombo.getInt("cantMinima");
				int cantRegalada = productosCombo.getInt("cantRegalada");
				ProductoCombo prod = new ProductoCombo(prodSinConsecutivo, cantMinima,cantRegalada);
				
				switch(promo.getTipoPromocion()){
				case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_DEPARTAMENTO:
					departamento = productosCombo.getString("departamento");
					prod.addDepartamento(departamento);
					while(productosCombo.next()){
						if(!productosCombo.getString("prodSinConsecutivo").equalsIgnoreCase(prodSinConsecutivo)) {
							productosCombo.previous();
							break;
						}
						departamento = productosCombo.getString("departamento");
						prod.addDepartamento(departamento);
					}
					break;
				case Sesion.TIPO_PROMOCION_DESCUENTO_EN_COMBO_LINEA:
				case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_LINEA:
					departamento = productosCombo.getString("departamento");
					codLinea = productosCombo.getString("linea");
					linea = new Linea(departamento,codLinea);
					prod.addLinea(linea);
					while(productosCombo.next()){
						if(!productosCombo.getString("prodSinConsecutivo").equalsIgnoreCase(prodSinConsecutivo)) {
							productosCombo.previous();
							break;
						}
						codLinea =  productosCombo.getString("linea");
						departamento = productosCombo.getString("departamento");
						linea = new Linea(departamento,codLinea);
						prod.addLinea(linea);
					}
					break;
				case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_MARCA:
					marca = productosCombo.getString("marca");
					prod.addMarca(marca);
					while(productosCombo.next()){
						if(!productosCombo.getString("prodSinConsecutivo").equalsIgnoreCase(prodSinConsecutivo)) {
							productosCombo.previous();
							break;
						}
						marca = productosCombo.getString("marca");
						prod.addMarca(marca);
					}
					break;
				case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_REFERENCIA:
					departamento = productosCombo.getString("departamento");
					codLinea =  productosCombo.getString("linea");
					codSeccion = productosCombo.getInt("codSeccion");
					codReferencia = productosCombo.getString("refProveedor");
					referencia = new Referencia(departamento,codLinea, codSeccion, codReferencia);
					prod.addRefProveedor(referencia);
					while(productosCombo.next()){
						if(!productosCombo.getString("prodSinConsecutivo").equalsIgnoreCase(prodSinConsecutivo)) {
							productosCombo.previous();
							break;
						}
						departamento = productosCombo.getString("departamento");
						codLinea =  productosCombo.getString("linea");
						codSeccion = productosCombo.getInt("codSeccion");
						codReferencia = productosCombo.getString("refProveedor");
						referencia = new Referencia(departamento,codLinea, codSeccion, codReferencia);
						prod.addRefProveedor(referencia);
					}
					break;
				case Sesion.TIPO_PROMOCION_DESCUENTO_EN_COMBO:
				case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_PRODUCTO:
					String codProducto = productosCombo.getString("codProducto");
					String prodNoConsecutivo = productosCombo.getString("prodSinConsecutivo");
					Vector<String> codigosAsociados = new Vector<String>();
					prod.setCodigosAsociados(codigosAsociados);
					prod.addCodigoProducto(codProducto);
					while(productosCombo.next()){
						if(!productosCombo.getString("prodSinConsecutivo").equalsIgnoreCase(prodNoConsecutivo)) {
							productosCombo.previous();
							break;
						}
						codProducto = productosCombo.getString("codProducto");
						prod.addCodigoProducto(codProducto);
					}
					break;
				case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_SECCION:
					departamento = productosCombo.getString("departamento");
					codLinea = productosCombo.getString("linea");
					codSeccion = productosCombo.getInt("codSeccion");
					Seccion seccion = new Seccion(departamento,codLinea,codSeccion);
					prod.addSeccion(seccion);
					while(productosCombo.next()){
						if(!productosCombo.getString("prodSinConsecutivo").equalsIgnoreCase(prodSinConsecutivo)) {
							productosCombo.previous();
							break;
						}
						codLinea =  productosCombo.getString("linea");
						departamento = productosCombo.getString("departamento");
						codSeccion = productosCombo.getInt("codSeccion");
						seccion = new Seccion(departamento,codLinea, codSeccion);
						prod.addSeccion(seccion);
					}
					break;
				case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_CATEGORIA:
					String categoria = productosCombo.getString("categoria");
					prod.addCategoria(categoria);
					while(productosCombo.next()){
						if(!productosCombo.getString("prodSinConsecutivo").equalsIgnoreCase(prodSinConsecutivo)) {
							productosCombo.previous();
							break;
						}
						categoria = productosCombo.getString("categoria");
						prod.addCategoria(categoria);
					}
					break;
				}
				productos.addElement(prod);
			}
		}catch(Exception ex){
			logger.error("cuponesActivos()", ex);
		}finally{
			if (productosCombo!=null){
				try{
					productosCombo.close();
				}catch(SQLException e){
					logger.error("cuponesActivos()",e);
				}
				productosCombo=null;
			}
		}
		return productos;
	}

}
