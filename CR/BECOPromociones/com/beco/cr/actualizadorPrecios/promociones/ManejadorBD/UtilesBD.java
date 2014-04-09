/*
 * Creado el 20/05/2008
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.beco.cr.actualizadorPrecios.promociones.ManejadorBD;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.beco.cr.actualizadorPrecios.promociones.CatDep;
import com.beco.cr.actualizadorPrecios.promociones.ComparadorPromociones;
import com.beco.cr.actualizadorPrecios.promociones.PromocionExt;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarventa.Producto;
import com.becoblohm.cr.mediadoresbd.Conexiones;

/**
 * @author aavila
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class UtilesBD {
	
	private static final Logger logger = Logger.getLogger(UtilesBD.class);
	public static Vector<CatDep> vcatdep = new Vector<CatDep>();
	
	/**
	 * Carga todos los departamentos de una categoría
	 * @return
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<CatDep> catdep(String codCategoria){
		if (logger.isDebugEnabled()){
			logger.debug("cargar categoria departamentos - start");
		}
		
		ResultSet cade = null;
		
		if (vcatdep.isEmpty()) {
			try{
				cade = Conexiones.realizarConsulta("select * from "+Sesion.getDbEsquema()+".catdep", true);
				cade.beforeFirst();
				while(cade.next()){
					CatDep catdep = resultSet2CatDep(cade);
					System.out.println(catdep);
					vcatdep.addElement(catdep);
				}
			}catch(Exception ex){
				System.out.println(vcatdep);
				logger.error("cargarRegalo()", ex);
			}finally{
				if (cade!=null){
					try{
						cade.close();
					}catch(SQLException e){
						logger.error("cargarRegalo()",e);
					}
					cade=null;
				}
			}
		} 
		if(logger.isDebugEnabled()){
			logger.debug("cargarRegalo() - end");
		}
		return vcatdep;
	}
	public static CatDep resultSet2CatDep (ResultSet rscd)throws BaseDeDatosExcepcion{
		try{
			String categoria = rscd.getString("codCat");
			System.out.println(categoria);	
			String departamento = rscd.getString("codDep");
			System.out.println(departamento);
			CatDep cd = new CatDep(categoria, departamento);
			return cd;
		}catch(SQLException e){
			throw new BaseDeDatosExcepcion("ERROR CARGANDO CatDep", e);
		}
	}
	
	/**
	 * Obtiene todos los departamentos de una categoria
	 * @param codCategoria
	 * @return Vector de departamentos
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' e 'Iterator'
	* Fecha: agosto 2011
	*/
	public static Vector<String> getCategoria(String codCategoria){
		Vector<String> departamentos = new Vector<String>();
		Iterator<CatDep> i = catdep(codCategoria).iterator();
		while (i.hasNext()){
			CatDep cd = (CatDep)i.next();
			if (cd.getCodCat().equals(codCategoria))
				departamentos.addElement(cd.getCodDep());
		}
		return departamentos;
	}

	//solo para prueba
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static String prueba(String cat){
		String p = "";
		Iterator<String> i = getCategoria(cat).iterator();
		while (i.hasNext()){
			p += i.next().toString()+"-"; 
			}
		return p;
	}
	
	/**
	 * Carga los productos patrocinantes
	 * @return HashMap productos patrocinantes
	 * (una matriz con los productos patrocinantes por cada promocion)
	 * Clave = Codigo de la promocion
	 * Valor = vector de productos patrocinantes
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' y 'TreeMap'
	* Fecha: agosto 2011
	*/
	public static TreeMap<PromocionExt,Vector<Producto>> cargarPatrocinantes(){
		TreeMap<PromocionExt,Vector<Producto>> patrocinantes  = 
			new TreeMap<PromocionExt,Vector<Producto>>(new ComparadorPromociones());
		ResultSet resultPatrocinantes = null;
		Vector<PromocionExt> promocionesAhorro = PromocionExt.getAhorrosEnCompra();
		try{
			Iterator<PromocionExt> resultPromociones = promocionesAhorro.iterator();
			while(resultPromociones.hasNext()){
				PromocionExt promo = (PromocionExt)resultPromociones.next();
				//Obtener los productos por promocion 
				String query = "SELECT DISTINCT p.codproducto " +
						"FROM promocion pr, detallepromocionext dpe, producto p " +
						"WHERE pr.codpromocion="+promo.getCodPromocion()+" AND " +
								"dpe.codPromocion=pr.codpromocion AND " +
								"dpe.codProducto=p.codproducto ";
				resultPatrocinantes = Conexiones.realizarConsulta(query, true);
				resultPatrocinantes.beforeFirst();
				Vector<Producto> productosDeEstaPromo = new Vector<Producto>();
				while(resultPatrocinantes.next()){
					Producto p = new Producto(resultPatrocinantes.getString("codproducto"));
					productosDeEstaPromo.addElement(p);
				}
				patrocinantes.put(promo, productosDeEstaPromo);
			}
		}catch(Exception ex){
			logger.error("cargarPatrocinantes()", ex);
		}finally{
			if (resultPatrocinantes!=null || promocionesAhorro.size()!=0){
				try{
					resultPatrocinantes.close();
				}catch(SQLException e){
					logger.error("cargarRegalo()",e);
				}
				resultPatrocinantes=null;
			}
		}
		return patrocinantes;
	}
	
	public static Producto getPatrocinante(int codpromocion, int numdetalle){
 		Producto p = null;
		//Obtener los productos por promocion 
		String query = "SELECT DISTINCT p.codproducto " +
				"FROM promocion pr, detallepromocionext dpe, producto p " +
				"WHERE pr.codpromocion="+codpromocion+" AND " +
						"dpe.codPromocion=pr.codpromocion AND " +
						"dpe.codProducto=p.codproducto  AND dpe.estadoregistro='A' AND " +
						"dpe.numdetalle ="+numdetalle;
		ResultSet resultPatrocinantes;
		try {
			resultPatrocinantes = Conexiones.realizarConsulta(query, true);
			resultPatrocinantes.beforeFirst();
			while(resultPatrocinantes.next()){
				p = new Producto(resultPatrocinantes.getString("codproducto"));
			}
		} catch (BaseDeDatosExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConexionExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return p;
	}
	/*public static HashMap cargarProductoComplementario(){
		HashMap prodComplementario  = new HashMap();
		ResultSet resultProdComplementario = null;
		//Vector promocionesProdComplementario = PromocionExtBD.cargarPromocionesProductoRegaladoMercadeoArreglado();
		try{
			Iterator prodReg = promocionesProdComplementario.iterator();
			while(prodReg.hasNext()){
				PromocionExt promo = (PromocionExt)prodReg.next();
				//Obtener los productos por promocion 
				String query = "SELECT DISTINCT p.codproducto " +
						"FROM promocion pr, detallepromocionext dpe, producto p " +
						"WHERE pr.codpromocion="+promo.getCodPromocion()+" AND " +
								"dpe.codPromocion=pr.codpromocion AND " +
								"dpe.codProducto=p.codproducto and pr.tipopromocion='"+Sesion.TIPO_PROMOCION_REGALO_PRODUCTO+"' limit 1";
				resultProdComplementario = Conexiones.realizarConsulta(query, true);
				resultProdComplementario.beforeFirst();
				Vector productosDeEstaPromo = new Vector();
				while(resultProdComplementario.next()){
					Producto p = new Producto(resultProdComplementario.getString("codproducto"));
					productosDeEstaPromo.addElement(p);
				}
				prodComplementario.put(promo, productosDeEstaPromo);
			}
		}catch(Exception ex){
			logger.error("cargarPatrocinantes()", ex);
		}finally{
			if (resultProdComplementario!=null || promocionesProdComplementario.size()!=0){
				try{
					resultProdComplementario.close();
				}catch(SQLException e){
					logger.error("cargarRegalo()",e);
				}
				resultProdComplementario=null;
			}
		}
		return prodComplementario;
	}*/
}
