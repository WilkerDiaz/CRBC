/*
 * Creado el 17/05/2008
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.beco.cr.actualizadorPrecios.promociones;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

import com.becoblohm.cr.CR;
import com.beco.cr.actualizadorPrecios.promociones.ManejadorBD.PromocionExtBD;
import com.beco.cr.actualizadorPrecios.promociones.ManejadorBD.UtilesBD;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarservicio.Apartado;
import com.becoblohm.cr.manejarventa.CondicionVenta;
import com.becoblohm.cr.manejarventa.Detalle;
import com.becoblohm.cr.manejarventa.DetalleTransaccion;
import com.becoblohm.cr.manejarventa.Producto;
import com.becoblohm.cr.manejarventa.Venta;
import com.becoblohm.cr.mediadoresbd.Conexiones;
/**
 * @author aavila
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Vector' e 'Iterator'
* Fecha: agosto 2011
*/
public class Detector {

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' e 'Iterator'
	* Fecha: agosto 2011
	*/
	public String getCodMenorPrecio(){
		Vector<DetalleTransaccion> v = CR.meVenta.getVenta().getDetallesTransaccion();
		String masBarato = null;
		Iterator<DetalleTransaccion> i = v.iterator();
		double precio = Double.MAX_VALUE;
		while (i.hasNext()){
			DetalleTransaccion x =(DetalleTransaccion)i.next();
			if (precio>x.getProducto().getPrecioRegular()/x.getCantidad()){
				precio = x.getProducto().getPrecioRegular();
				masBarato = x.getProducto().getCodProducto()+" - "+x.getProducto().getDescripcionCorta();	
			}
		}
		return masBarato;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Se eliminó una variable que no se utilizaba
	* Fecha: agosto 2011
	*/
	public boolean masBaratoGratis (double monto){
		if (monto>Promociones.getPrecioProdGratis()) return true;
			return false;
	}
	
	/***
	 * Obtiene los productos del departamento dep
	 * que existen en la venta
	 * @param dep
	 * @return Vector de Detalles que contienen los productos
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' e 'Iterator'
	* Fecha: agosto 2011
	*/
	public static Vector<DetalleTransaccion> getProdPorDepartamento(String dep, PromocionExt promocion, Vector<String> condiciones){
		Vector<DetalleTransaccion> v = CR.meVenta.getVenta().getDetallesTransaccion();
		Vector<DetalleTransaccion> p = new Vector<DetalleTransaccion>();
		Iterator<DetalleTransaccion> i = v.iterator();
		while (i.hasNext()){
			DetalleTransaccion x = (DetalleTransaccion)i.next();
			CondicionVenta cv = x.getPrimeraCondicion(condiciones);
			int codPromocion = 0;
			if(cv!=null)
				codPromocion = cv.getCodPromocion();
			if (Integer.parseInt(x.getProducto().getCodDepartamento())==Integer.parseInt(dep) && (promocion==null || codPromocion==promocion.getCodPromocion())){
				p.addElement(x);
			}
		}
		return p;	
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' e 'Iterator'
	* Fecha: agosto 2011
	*/
	public static Vector<DetalleTransaccion> getProdPorLinea(String dep, String linea, PromocionExt promocion, Vector<String> condiciones){
		Vector<DetalleTransaccion> v = CR.meVenta.getVenta().getDetallesTransaccion();
		Iterator<DetalleTransaccion> i = v.iterator();
		Vector<DetalleTransaccion> p = new Vector<DetalleTransaccion>();
		while (i.hasNext()){
			DetalleTransaccion x = (DetalleTransaccion)i.next();
			CondicionVenta cv = x.getPrimeraCondicion(condiciones);
			int codPromocion = 0;
			if(cv!=null)
				codPromocion = cv.getCodPromocion();
			if ((Integer.parseInt(x.getProducto().getCodDepartamento())==Integer.parseInt(dep)) && Integer.parseInt(x.getProducto().getLineaSeccion())==Integer.parseInt(linea) &&
					(promocion==null || codPromocion==promocion.getCodPromocion()))
				p.addElement(x);
		}
		return p;	
	}
	
	/***
	 * Detalles de los productos que estan en la venta y que 
	 * que pertenecen a la marca indicada
	 * @param marca
	 * @return Vector de DetalleTransaccion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<DetalleTransaccion> getProdMarca(String marca, PromocionExt promocion, Vector<String> condiciones){
		Vector<DetalleTransaccion> v = CR.meVenta.getVenta().getDetallesTransaccion();
		Iterator<DetalleTransaccion> i = v.iterator();
		Vector<DetalleTransaccion> p = new Vector<DetalleTransaccion>();
		while (i.hasNext()){
			DetalleTransaccion x = (DetalleTransaccion)i.next();
			CondicionVenta cv = x.getPrimeraCondicion(condiciones);
			int codPromocion = 0;
			if(cv!=null)
				codPromocion = cv.getCodPromocion();
			if (x.getProducto().getMarca().equals(marca) && 
					(promocion==null || codPromocion==promocion.getCodPromocion()))
				p.addElement(x);
		}
		return p;	
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' e 'Iterator'
	* Fecha: agosto 2011
	*/
	public static Vector<DetalleTransaccion> getProdRefProveedor(Referencia referencia, PromocionExt promocion, Vector<String> condiciones){
		Vector<DetalleTransaccion> v = CR.meVenta.getVenta().getDetallesTransaccion();
		Iterator<DetalleTransaccion> i = v.iterator();
		Vector<DetalleTransaccion> p = new Vector<DetalleTransaccion>();
		while (i.hasNext()){
			DetalleTransaccion x = (DetalleTransaccion)i.next();
			CondicionVenta cv =  x.getPrimeraCondicion(condiciones);
			int codPromocion = 0;
			if(cv!=null)
				codPromocion = cv.getCodPromocion();
			if ((Integer.parseInt(x.getProducto().getCodDepartamento())==Integer.parseInt(referencia.getCodDepartamento())) && Integer.parseInt(x.getProducto().getLineaSeccion())==Integer.parseInt(referencia.getCodLinea()) 
				&& x.getProducto().getSeccion()==referencia.getCodSeccion()	&& x.getProducto().getReferenciaProveedor().trim().equals(referencia.getCodReferencia().trim()) && (promocion==null || codPromocion==cv.getCodPromocion())){
				p.addElement(x);
			}
		}
		return p;	
	}
	
	/****
	 * Obtiene todos los detalles que están en la venta de una categoria
	 * @param codCategoria
	 * @return Vector de DetalleTransaccion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' e 'Iterator'
	* Fecha: agosto 2011
	*/
	public static Vector<DetalleTransaccion> getProdCategoria(String codCategoria, PromocionExt promocion, Vector<String> condiciones){
		Vector<String> w = UtilesBD.getCategoria(codCategoria);
		Iterator<String> i = w.iterator();
		Vector<DetalleTransaccion> p = new Vector<DetalleTransaccion>();
		while(i.hasNext()){
			String dep = (String)i.next();
			Vector<DetalleTransaccion> prods = Detector.getProdPorDepartamento(dep, promocion, condiciones);
			for (int j = 0 ; j < prods.size() ; j++)
				p.addElement(prods.elementAt(j));
		}
		return p;
	}
	
	/**
	 * Determina si en el detalle transaccion hay algun producto de los 
	 * contenidos en el vector prod
	 * @param prod vector de productos
	 * @param Vector productos del detalle transaccion en prod
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Detalle> getProd(Vector<Producto> prod, Venta venta2){
		Venta venta =  venta2;
		Apartado apartado = CR.meServ.getApartado();
		Vector<? extends Detalle> v = new Vector<Detalle>();
		if(venta!=null)
			v = venta.getDetallesTransaccion();
		else if(apartado!=null)
			v = apartado.getDetallesServicio();
		Vector<Detalle> p = new Vector<Detalle>();
		for (int i=0;i<prod.size();i++){
			Producto pr = (Producto)prod.elementAt(i);
			for (int j=0;j<v.size();j++){
				Detalle x = (Detalle)v.elementAt(j);
				if (x.getProducto().getCodProducto().equals(pr.getCodProducto()))
					p.addElement(x);
			}
		}
		return p;
	}
	
	/**
	 * Obtiene los detalles de transaccion que contienen productos
	 * iguales al producto indicado (sin tomar en cuenta el consecutivo
	 * de precios)
	 * @param prod vector de productos
	 * @param Vector productos del detalle transaccion en prod
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public static Vector<Detalle> getDetallesProd(Vector<Producto> prod){
		Venta venta =  CR.meVenta.getVenta();
		Apartado apartado = CR.meServ.getApartado();
		Vector<? extends Detalle> v = new Vector<Detalle>();
		if(venta!=null)
			v = venta.getDetallesTransaccion();
		else if(apartado!=null)
			v = apartado.getDetallesServicio();

		Vector<Detalle> p = new Vector<Detalle>();
		for (int i=0;i<prod.size();i++){
			Producto pr = (Producto)prod.elementAt(i);
			for (int j=0;j<v.size();j++){
				Detalle x = (Detalle)v.elementAt(j);
				try{
					if (x.getProducto().getCodProducto().startsWith(pr.getCodProducto()))
						p.addElement(x);
				}catch(NullPointerException np){
					//Nada, ya ese detalle no existe por una ejecucion previa de acumular detalles
				}
			}
		}
		return p;
	}
	
	/**
	 * Obtiene los detalles de transaccion que contienen productos
	 * iguales al producto indicado en el combo (pueden ser de distinto
	 * codigo beco
	 * @param producto producto indicado en combo (ProductoCombo)
	 * @param Vector productos del detalle transaccion en prod
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Detalle> getDetallesProductoCodBeco(ProductoCombo producto, PromocionExt promo, Vector<String> condicionesVenta){
		Venta venta =  CR.meVenta.getVenta();
		Apartado apartado = CR.meServ.getApartado();
		Vector<? extends Detalle> v = new Vector<Detalle>();
		if(venta!=null)
			v = venta.getDetallesTransaccion();
		else if(apartado!=null)
			v = apartado.getDetallesServicio();
		
		Vector<String> prod = producto.getCodigosAsociados();
		Vector<Detalle> p = new Vector<Detalle>();
		for (int i=0;i<prod.size();i++){
			String pr = (String)prod.elementAt(i);
			for (int j=0;j<v.size();j++){
				Detalle x = (Detalle)v.elementAt(j);
				try{
					CondicionVenta cv = x.getPrimeraCondicion(condicionesVenta);
					int codPromocion =0;
					if(cv!=null)
						codPromocion =cv.getCodPromocion();
					if (x.getProducto().getCodProducto().startsWith(pr) && (promo==null || codPromocion==promo.getCodPromocion()))
						p.addElement(x);
				}catch(NullPointerException np){
					//Nada, ya ese detalle no existe por una ejecucion previa de acumular detalles
				}
			}
		}
		return p;
	}
	
	
	/**
	 * Obtiene las posiciones de los detalles de transaccion que contienen productos
	 * iguales al producto indicado (sin tomar en cuenta el consecutivo
	 * de precios)
	 * @param prod vector de productos
	 * @param Vector productos del detalle transaccion en prod
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public static Vector<Integer> getDetallesProdPosiciones(Vector<String> prod){
		Venta venta =  CR.meVenta.getVenta();
		Apartado apartado = CR.meServ.getApartado();
		Vector<? extends Detalle> v = new Vector<Detalle>();
		if(venta!=null)
			v = venta.getDetallesTransaccion();
		else if(apartado!=null)
			v = apartado.getDetallesServicio();
		Vector<Integer> p = new Vector<Integer>();
		for (int i=0;i<prod.size();i++){
			String pr = (String)prod.elementAt(i);
			for (int j=0;j<v.size();j++){
				Detalle x = (Detalle)v.elementAt(j);
				if (x.getProducto().getCodProducto().equalsIgnoreCase(pr))
					p.addElement(new Integer(j));
			}
		}
		return p;
	}
	/*public PromocionRegistrada hayProdComplementario(Venta venta){
		if (!PromocionExtBD.cargarPromocionesProductoRegaladoMercadeoArreglado().isEmpty()){
			Iterator i = PromocionExtBD.cargarPromocionesProductoRegaladoMercadeoArreglado(venta).iterator();
			Vector prod = new Vector();
			String nombre = "";
			int codigo = 0;
			while (i.hasNext()){
				PromocionExt pExt = (PromocionExt)i.next();
				Producto p = new Producto(pExt.getCodProducto());
				nombre = pExt.getNombrePromocion();
				codigo = pExt.getCodPromocion();
				prod.add(p);
			}
			if(!Detector.getProd(prod, venta).isEmpty()){
				Vector vdt = Detector.getProd(prod, venta);
				Iterator j = vdt.iterator();
				while (j.hasNext()){
					DetalleTransaccion dt = (DetalleTransaccion)vdt.firstElement();
					PromocionRegistrada p = new PromocionRegistrada(nombre,codigo,dt.getProducto().getCodProducto(),dt.getCantidad());
					return p;
				}
			}
		}
		return null;
	}
	
	/*public PromocionRegistrada hayProdComplementario(Venta venta){
		if (!PromocionExtBD.cargarPromocionesProductoRegaladoMercadeo().isEmpty()){
			Iterator i = PromocionExtBD.cargarPromocionesProductoRegaladoMercadeo().iterator();
			Vector prod = new Vector();
			String nombre = "";
			int codigo = 0;
			while (i.hasNext()){
				PromocionExt pExt = (PromocionExt)i.next();
				Producto p = new Producto(pExt.getCodProducto());
				nombre = pExt.getNombrePromocion();
				codigo = pExt.getCodPromocion();
				prod.add(p);
			}
			if(!Detector.getProd(prod, venta).isEmpty()){
				Vector vdt = Detector.getProd(prod, venta);
				Iterator j = vdt.iterator();
				while (j.hasNext()){
					DetalleTransaccion dt = (DetalleTransaccion)vdt.firstElement();
					PromocionRegistrada p = new PromocionRegistrada(nombre,codigo,dt.getProducto().getCodProducto(),dt.getCantidad());
					return p;
				}
			}
		}
		return null;
	}*/

	/****
	 * Todos los detalles de los productos que estan en la venta
	 * y que coinciden con una categoria y marca indicada
	 * @param codCategoria
	 * @param marca
	 * @return Vector de DetalleTransaccion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' e 'Iterator'
	* Fecha: agosto 2011
	*/
	public Vector<DetalleTransaccion> getProdCategoriaMarca(String codCategoria, String marca){
		Vector<String> w = UtilesBD.getCategoria(codCategoria);
		Vector<DetalleTransaccion> p = new Vector<DetalleTransaccion>();
		Iterator<String> i = w.iterator();
		while(i.hasNext()){
			String dep = (String)i.next();
			for (int j = 0; j  < this.getProdPorDeparMarca(dep,marca).size(); j++)
				p.addElement(this.getProdPorDeparMarca(dep,marca).elementAt(j));
		}
		return p;
	}
	
	/****
	 * Obtiene los detalles de la venta cuyos productos pertenecen al mismo departamento
	 * y marca que los indicados
	 * @param dep
	 * @param marca
	 * @return Vector de DetalleTransaccion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<DetalleTransaccion> getProdPorDeparMarca(String dep, String marca){
		Vector<DetalleTransaccion> v = CR.meVenta.getVenta().getDetallesTransaccion();
		Iterator<DetalleTransaccion> i = v.iterator();
		Vector<DetalleTransaccion> p = new Vector<DetalleTransaccion>();
		while (i.hasNext()){
			DetalleTransaccion x = (DetalleTransaccion)i.next();
			if (x.getProducto().getCodDepartamento().equals(dep)  &&  x.getProducto().getMarca().equals(marca)) 
				p.addElement(x);
		}
		return p;	
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<DetalleTransaccion> getProdPorLineaMarca(String dep, String linea, String marca){
		Vector<DetalleTransaccion> v = CR.meVenta.getVenta().getDetallesTransaccion();
		Iterator<DetalleTransaccion> i = v.iterator();
		Vector<DetalleTransaccion> p = new Vector<DetalleTransaccion>();
		while (i.hasNext()){
			DetalleTransaccion x = (DetalleTransaccion)i.next();
			if (x.getProducto().getCodDepartamento().equals(dep)  &&  x.getProducto().getLineaSeccion().equals(linea)  &&  x.getProducto().getMarca().equals(marca))
				p.addElement(x);
		}
		return p;	
	}
	
	/****
	 * Obtiene de la Venta los detalles de transaccion que corresponden 
	 * con los productos indicados en el vector dado y que ademas
	 * son de la marca que se pasa como parametro
	 * @param prod
	 * @param marca
	 * @return Vector de DetalleTransaccion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<DetalleTransaccion> getProdMarca(Vector<Producto> prod, String marca){
		Vector<DetalleTransaccion> v = CR.meVenta.getVenta().getDetallesTransaccion();
		Vector<DetalleTransaccion> p = new Vector<DetalleTransaccion>();
		for (int i=0;i<prod.size();i++){
			Producto pr = (Producto)prod.elementAt(i);
			for (int j=0;j<v.size();j++){
				DetalleTransaccion x = (DetalleTransaccion)v.elementAt(j);
				if (x.getProducto().getCodProducto().equals(pr.getCodProducto()) && x.getProducto().getMarca().equals(marca))
					p.addElement(x);
			}
		}
		return p;
	}

	/**
	 * Los detalles de los productos que estan en la venta y que pertenecen a la
	 * categoria y referencia a proveedor indicada
	 * @param codCategoria
	 * @param refProv
	 * @return Vector de DetalleTransaccion
	 */ 
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' e 'Iterator'
	* Fecha: agosto 2011
	*/
	public Vector<DetalleTransaccion> getProdCategoriaRefProv(String codCategoria, String refProv){
		Vector<String> w = UtilesBD.getCategoria(codCategoria);
		Vector<DetalleTransaccion> p = new Vector<DetalleTransaccion>();
		Iterator<String> i = w.iterator();
		while(i.hasNext()){
			String dep = (String)i.next();
			for (int j = 0; j < this.getProdPorDeparRefProv(dep, refProv).size(); j++)
				p.addElement(this.getProdPorDeparRefProv(dep, refProv).elementAt(j));
		}
		return p;
	}
	
	/****
	 * Obtiene los detalles de la venta cuyos 
	 * productos pertenecen al mismo departamento y referencia a proveedor que los indicados
	 * @param dep
	 * @param refProv
	 * @return Vector de DetalleTransaccion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<DetalleTransaccion> getProdPorDeparRefProv(String dep, String refProv){
		Vector<DetalleTransaccion> v = CR.meVenta.getVenta().getDetallesTransaccion();
		Iterator<DetalleTransaccion> i = v.iterator();
		Vector<DetalleTransaccion> p = new Vector<DetalleTransaccion>();
		while (i.hasNext()){
			DetalleTransaccion x = (DetalleTransaccion)i.next();
			if (x.getProducto().getCodDepartamento().equals(dep)  &&  x.getProducto().getReferenciaProveedor().equals(refProv)) 
				p.addElement(x);
		}
		return p;	
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' e 'Iterator'
	* Fecha: agosto 2011
	*/
	public Vector<DetalleTransaccion> getProdPorLineaRefProv(String dep, String linea, String refProv){
		Vector<DetalleTransaccion> v = CR.meVenta.getVenta().getDetallesTransaccion();
		Iterator<DetalleTransaccion> i = v.iterator();
		Vector<DetalleTransaccion> p = new Vector<DetalleTransaccion>();
		while (i.hasNext()){
			DetalleTransaccion x = (DetalleTransaccion)i.next();
			if (x.getProducto().getCodDepartamento().equals(dep)  &&  x.getProducto().getLineaSeccion().equals(linea)  &&  x.getProducto().getReferenciaProveedor().equals(refProv))
				p.addElement(x);
		}
		return p;	
	}
	
	/**
	 * Obtiene los detalles de la venta que pertenecen a la misma referncia proveedor que la indicada
	 * @param prod
	 * @param refProv
	 * @return
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<DetalleTransaccion> getProdRefProv(Vector<Producto> prod, String refProv){
		Vector<DetalleTransaccion> v = CR.meVenta.getVenta().getDetallesTransaccion();
		Vector<DetalleTransaccion> p = new Vector<DetalleTransaccion>();
		for (int i=0;i<prod.size();i++){
			Producto pr = (Producto)prod.elementAt(i);
			for (int j=0;j<v.size();j++){
				DetalleTransaccion x = (DetalleTransaccion)v.elementAt(j);
				if (x.getProducto().getReferenciaProveedor().equals(pr) && x.getProducto().getMarca().equals(refProv))
					p.addElement(x);
			}
		}
		return p;
	}
	
	/**
	 * Determina si la proxima transaccion de esta caja debe ser premiada
	 * @return PromocionExt true: si la proxima transaccion debe ser premiada
	 * retorna el detalle de la promocion, null en caso contrario
	 * **/
	public static PromocionExt proximaTransaccionPremiada(){
		return PromocionExtBD.transaccionesPorPremiar();
	}
	
	/**
	 * Obtiene el producto con mayor costo de un vector de detallestransaccion
	 * @param detallesAfectados detalles de transaccion
	 * @return Producto producto con mayor costo
	 * **/
	public static Producto getProductoMasCostoso(Vector<Detalle> detallesAfectados){
		Producto p = null;
		double costo =0;
		Iterator<Detalle> i = detallesAfectados.iterator();
		while (i.hasNext()){
			DetalleTransaccion dt = (DetalleTransaccion) i.next();
			Producto prod = dt.getProducto();
			if(costo<prod.getPrecioRegular()){
				costo = prod.getPrecioRegular();
				p = prod;
			}
		}
		return p;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' e 'Iterator'
	* Fecha: agosto 2011
	*/
	public static boolean contienePromocion(Vector<Vector<Integer>> v, int n){
		Iterator<Vector<Integer>> i = v.iterator();
		while (i.hasNext()){
			Vector<Integer> j = (Vector<Integer>)i.next();
			int m = ((Integer)j.lastElement()).intValue();
			if (n==m) return true;
		}
		return false;
	}
	
	/***
	 * Determina si estan dadas las condiciones para ejecutar
	 * la promocion de ahorro en compra al agregar el producto p
	 * @param p Producto que se esta agregando
	 * @return boolean
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector', 'Iterator' y TreeMap
	* Fecha: agosto 2011
	*/
	public static boolean isAhorroEnCompra(){
		
		Venta venta =  CR.meVenta.getVenta();
		Apartado  apartado = CR.meServ.getApartado();
		TreeMap<PromocionExt,Vector<Producto>> patrocinantes = new TreeMap<PromocionExt,Vector<Producto>>(new ComparadorPromociones());
		if(venta!=null)
			patrocinantes = CR.meVenta.getProductosPatrocinantes();
		else if(apartado!=null)
			patrocinantes = CR.meServ.getProductosPatrocinantes();
		
    	Iterator<PromocionExt> i = patrocinantes.keySet().iterator();
    	while(i.hasNext()){
    		PromocionExt promocion = (PromocionExt)i.next();
    		Vector<Producto> patrocinantesEstaPromo = (Vector<Producto>)patrocinantes.get(promocion);
    		Vector<Detalle> detallesDeTransaccionAfectados = Detector.getProd(patrocinantesEstaPromo, venta);
    		Vector<String> condicionesAhorroEnCompra = new Vector<String>();
    		condicionesAhorroEnCompra.addElement(Sesion.condicionDescuentoEnProductos);
    		double totalTransaccion = 0;
    		if(venta!=null)
    			totalTransaccion = venta.getMontoTransaccion();
    		else if(apartado!=null)
    			totalTransaccion = apartado.getMontoServ();
    			
    		if(
    				(detallesDeTransaccionAfectados.size()!=0 && //Hay productos patrocinantes 
    						((Detalle)detallesDeTransaccionAfectados.elementAt(0)).contieneCondicion(condicionesAhorroEnCompra) //Almenos uno de ellos tiene aplicada la promocion
    				) || 
    				(totalTransaccion>=promocion.getMontoMinimo())
    			){
    			return true;
    		}
    	}
		return false;
	}
	
	/***
	 * Retorna la cantidad de productos que hay en la venta que no estan 
	 * asignados a ningun combo o empaque
	 * @param producto
	 * @param condiciones Vector de codigos de condiciones de venta que no deben estar asignadas
	 * al detalle
	 * @param promo Promocion a comparar prioridades, si contiene alguno de las condiciones de promo, 
	 * solo seran tomados en cuenta para la suma si la prioridad de la promocion que tengan aplicada es mayor
	 * que la prioridad de promo
	 * @return float
	 */
/*	public static float getCantidadEnVenta(ProductoCombo producto, Vector condiciones, PromocionExt promo){
		float cantidad = 0;
		Vector detalles = new Vector();
		if(producto.getDepartamentos().size()!=0){
			//Obtengo el primer elemento porque no hay aun varios grupos para
			//las promociones por departamento
			detalles = Detector.getProdPorDepartamento((String)producto.getDepartamentos().elementAt(0), null, new Vector());
		} else if(producto.getLineas().size()!=0){
			//Obtengo el primer elemento porque no hay aun varios grupos para
			//las promociones por linea
			Linea linea = (Linea)producto.getLineas().elementAt(0);
			detalles = Detector.getProdPorLinea(linea.getCodDepartamento(), linea.getCodlinea(), null, new Vector());
		} else if(producto.getMarcas().size()!=0){
			//Obtengo el primer elemento porque no hay aun varios grupos para
			//las promociones por marca
			detalles = Detector.getProdMarca((String)producto.getMarcas().elementAt(0), null, new Vector());
		} else if(producto.getReferencias().size()!=0){
			//Obtengo el primer elemento porque no hay aun varios grupos para
			//las promociones por referencia
			detalles = Detector.getProdRefProveedor((String)producto.getReferencias().elementAt(0), null, new Vector());
		} else if(producto.getCodigosAsociados().size()!=0){
			detalles = Detector.getDetallesProductoCodBeco(producto, null, new Vector());
		} else if(producto.getSecciones().size()!=0){
			detalles = Detector.getProdSeccion((Seccion)(producto.getSecciones().elementAt(0)),null, new Vector());
		} else if(producto.getCategorias().size()!=0){
			detalles = Detector.getProdCategoria((String)producto.getCategorias().elementAt(0), null, new Vector());
		}
		
		for(int i=0;i<detalles.size();i++){
			CondicionVenta primeraCondicionCombo = ((Detalle)detalles.elementAt(i)).getPrimeraCondicion(Sesion.condicionesCombo);
			
			if(
					!((Detalle)detalles.elementAt(i)).isCondicionEspecial() &&
					(
							(
									!((Detalle)detalles.elementAt(i)).contieneAlgunaCondicion(condiciones)
							) 
							||
							(
									((Detalle)detalles.elementAt(i)).contieneAlgunaCondicion(condiciones) && 
									promo!=null && 
									promo.getPrioridad()<primeraCondicionCombo.getPrioridadPromocion()
							)
					)
				)
				cantidad += ((Detalle)detalles.elementAt(i)).getCantidad();
		}
		return cantidad;
	}*/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' e 'Iterator'
	* Fecha: agosto 2011
	*/
	public static float getCantidadEnVenta(ProductoCombo producto, Vector<String> condiciones, PromocionExt promo){
		float cantidad = 0;
		Vector<Detalle> detalles = new Vector<Detalle>();
		if(producto.getDepartamentos().size()!=0){
			//Obtengo el primer elemento porque no hay aun varios grupos para
			//las promociones por departamento
			for (int i=0; i<producto.getDepartamentos().size();i++)
				detalles.addAll(Detector.getProdPorDepartamento((String)producto.getDepartamentos().elementAt(i), null, new Vector<String>()));
		} else if(producto.getLineas().size()!=0){
			//Obtengo el primer elemento porque no hay aun varios grupos para
			//las promociones por linea
			for (int i=0; i<producto.getLineas().size();i++){
				Linea linea = (Linea)producto.getLineas().elementAt(i);
				detalles.addAll(Detector.getProdPorLinea(linea.getCodDepartamento(), linea.getCodlinea(), null, new Vector<String>()));
			}
		} else if(producto.getMarcas().size()!=0){
			//Obtengo el primer elemento porque no hay aun varios grupos para
			//las promociones por marca
			for (int i=0; i<producto.getMarcas().size();i++)
				detalles.addAll(Detector.getProdMarca((String)producto.getMarcas().elementAt(i), null, new Vector<String>()));
		} else if(producto.getReferencias().size()!=0){
			//Obtengo el primer elemento porque no hay aun varios grupos para
			//las promociones por referencia
			for (int i=0; i<producto.getReferencias().size();i++)
				detalles.addAll(
						Detector.getProdRefProveedor(
								(Referencia)producto.getReferencias().elementAt(i)
								, null
								, new Vector<String>()
								)
								);
		} else if(producto.getCodigosAsociados().size()!=0){
			detalles.addAll(Detector.getDetallesProductoCodBeco(producto, null, new Vector<String>()));
		} else if(producto.getSecciones().size()!=0){
			for (int i=0; i<producto.getSecciones().size();i++)
				detalles.addAll(Detector.getProdSeccion((Seccion)(producto.getSecciones().elementAt(i)),null, new Vector<String>()));
		} else if(producto.getCategorias().size()!=0){
			for (int i=0; i<producto.getCategorias().size();i++)
				detalles.addAll(Detector.getProdCategoria((String)producto.getCategorias().elementAt(i), null, new Vector<String>()));
		}
		
		for(int i=0;i<detalles.size();i++){
			CondicionVenta primeraCondicionCombo = ((Detalle)detalles.elementAt(i)).getPrimeraCondicion(Sesion.condicionesCombo);
			
			if(
					!((Detalle)detalles.elementAt(i)).isCondicionEspecial() &&
					(
							(
									!((Detalle)detalles.elementAt(i)).contieneAlgunaCondicion(condiciones)
							) 
							||
							(
									((Detalle)detalles.elementAt(i)).contieneAlgunaCondicion(condiciones) && 
									promo!=null && 
									promo.getPrioridad()<primeraCondicionCombo.getPrioridadPromocion()
							)
					)
				)
				cantidad += ((Detalle)detalles.elementAt(i)).getCantidad();
		}
		return cantidad;
	}
	/****
	 * Determina si en una venta se cumple o no las condiciones necesarias para que se de
	 * un combo en cuanto a cantidades minimas de productos se refiere
	 * @param promo
	 * @param condiciones Vector de condiciones que no pueden estar asignadas a los detalles a sumar
	 * @param compararPrioridades indica si se debe comparar las prioridades de las promociones ya aplicadas para totalizar
	 * @return boolean
	 */
	public static boolean verificaCantidadesMinimasCombo(PromocionExt promo, Vector<String> condiciones, boolean compararPrioridades){
		Vector<ProductoCombo> productosInvolucrados = promo.getProductosCombo();
		for(int i=0;i<productosInvolucrados.size();i++){
			ProductoCombo pc = (ProductoCombo)productosInvolucrados.elementAt(i);
			
			if((compararPrioridades && (Detector.getCantidadEnVenta(pc, condiciones, promo))<pc.getCantidadMinima()) ||
					(!compararPrioridades && (Detector.getCantidadEnVenta(pc, condiciones, null))<pc.getCantidadMinima())){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Determina la cantidad de veces que una promoción de combo es aplicable en la venta actual
	 * @param promo
	 * @return boolean
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static int getCantidadCombosPosibles(PromocionExt promo){
		Vector<ProductoCombo> productosInvolucrados = promo.getProductosCombo();
		Vector<Integer> cantidades = new Vector<Integer>();
		int resultado = Integer.MAX_VALUE;
		for(int i=0;i<productosInvolucrados.size();i++){
			ProductoCombo pc = (ProductoCombo)productosInvolucrados.elementAt(i);
			float cantidadEnVenta = Detector.getCantidadEnVenta(pc, new Vector<String>(), null);
			float cantidadCombos = cantidadEnVenta/pc.getCantidadMinima();
			cantidades.addElement(new Integer((int)Math.floor(cantidadCombos)));
		}
		//Ahora el menor de los elementos en cantidades es el resultado
		for(int i=0;i<cantidades.size();i++){
			if(((Integer)cantidades.elementAt(i)).intValue()>0 && 
					((Integer)cantidades.elementAt(i)).intValue()<resultado){
				resultado = ((Integer)cantidades.elementAt(i)).intValue();
			}
		}
		if(resultado!=Integer.MAX_VALUE){
			return resultado;
		} else {
			return 0;
		}
	}
	
	/**
	 * Cuenta la cnatidad de productos en una transaccion 
	 * @param detalles Detalles de la transaccion
	 * @return int numero de productos
	 */
	public static int contarCantidadProductos(Vector<DetalleTransaccion> detalles){
		int cantidad = 0;
		for(int i=0;i<detalles.size();i++){
			cantidad+=((Detalle)detalles.elementAt(i)).getCantidad();
		}
		return cantidad;
	}
	
	/**
	 * Obtiene la sección a la que pertenece el producto
	 * @param codProducto
	 * @return String codSeccion
	 */
	public static String getSeccionProducto(Producto p){
		String codSeccion = "";
		ResultSet seccion = null;
		String query = "SELECT codSeccion " +
		"FROM prodseccion " +
		"WHERE codProducto='"+p.getCodProducto()+"' "+
		" and codDepartamento='"+p.getCodDepartamento()+"' and codLinea='"+p.getLineaSeccion()+"' ";
		try{
			seccion = Conexiones.realizarConsulta(query, true);
			seccion.beforeFirst();
			if(seccion.next()){
				codSeccion = seccion.getString("codSeccion");
			}
		}catch(Exception ex){

		}finally{
			if (seccion!=null){
				try{
					seccion.close();
				}catch(SQLException e){

				}
				seccion=null;
			}
		}
		return codSeccion;
	}
	
	/**
	 * Obtiene la categoria a la que pertenece el producto
	 * @param codProducto
	 * @return String codSeccion
	 */
	public static String getCategoriaProducto(Producto p){
		String codCategoria = "";
		ResultSet categoria = null;
		String query = "SELECT codcat " +
		"FROM catdep " +
		"WHERE codDep='"+p.getCodDepartamento()+"' ";
		try{
			categoria = Conexiones.realizarConsulta(query, true);
			categoria.beforeFirst();
			if(categoria.next()){
				codCategoria = categoria.getString("codcat");
			}
		}catch(Exception ex){

		}finally{
			if (categoria!=null){
				try{
					categoria.close();
				}catch(SQLException e){

				}
				categoria=null;
			}
		}
		return codCategoria;
	}
	
	/****
	 * Obtiene todos los detalles que estan en la venta de una seccion indicada
	 * @param Seccion seccion
	 * @return Vector de DetalleTransaccion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Detalle> getProdSeccion(Seccion seccion, PromocionExt promocion, Vector<String> condiciones){
		Vector<? extends Detalle> detalles =  new Vector<Detalle>();
		if(CR.meVenta.getVenta()!=null){
			detalles = CR.meVenta.getVenta().getDetallesTransaccion();
		}else if (CR.meServ.getApartado()!=null){
			detalles = CR.meServ.getApartado().getDetallesServicio();
		}
		Vector<Detalle> detallesResult = new Vector<Detalle>();
		for(int i=0;i<detalles.size();i++){
			Detalle d = (Detalle)detalles.elementAt(i);
			CondicionVenta cv =  d.getPrimeraCondicion(condiciones);
			int codPromocion = 0;
			if(cv!=null)
				codPromocion = cv.getCodPromocion();
			if(d.getProducto().getCodDepartamento().equalsIgnoreCase(seccion.getCodDepartamento()) &&
					d.getProducto().getLineaSeccion().equalsIgnoreCase(seccion.getLinea()) &&
					d.getProducto().getSeccion()==seccion.getSeccion() &&
					(promocion==null || codPromocion==promocion.getCodPromocion())){
				detallesResult.addElement(d);
			}
		}
		return detallesResult;
	}
	
	/**
	 * Retorna los detalles de la venta afectados por la promocionAplicada y que pertenecen al productoCombo
	 * @param promocionAplicada
	 * @param productoCombo
	 * @return Vector
	 */
	public static Vector<Detalle> getDetallesAfectados(PromocionExt promocionAplicada, ProductoCombo productoCombo){
		Vector<Detalle> detallesAfectados = new Vector<Detalle>();
		switch(promocionAplicada.getTipoPromocion()){
		case Sesion.TIPO_PROMOCION_DESCUENTO_EN_COMBO:
		case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_PRODUCTO:
			detallesAfectados = Detector.getDetallesProductoCodBeco(productoCombo, promocionAplicada, Sesion.condicionesCombo);
			break;
		case Sesion.TIPO_PROMOCION_DESCUENTO_EN_COMBO_LINEA:
		case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_LINEA:
			for(int i=0;i<productoCombo.getLineas().size();i++){
				Linea linea = (Linea)productoCombo.getLineas().elementAt(i);
				detallesAfectados.addAll(Detector.getProdPorLinea(linea.getCodDepartamento(),linea.getCodlinea(), promocionAplicada, Sesion.condicionesCombo));
			}
			break;
		case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_CATEGORIA:
			for(int i=0;i<productoCombo.getCategorias().size();i++)
				detallesAfectados.addAll(Detector.getProdCategoria((String)productoCombo.getCategorias().elementAt(i), promocionAplicada, Sesion.condicionesCombo));
			break;
		case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_DEPARTAMENTO:
			for(int i=0;i<productoCombo.getDepartamentos().size();i++)
				detallesAfectados.addAll(Detector.getProdPorDepartamento((String)productoCombo.getDepartamentos().elementAt(i), promocionAplicada, Sesion.condicionesCombo));
			break;
		case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_SECCION:
			for(int i=0;i<productoCombo.getSecciones().size();i++)
				detallesAfectados.addAll(Detector.getProdSeccion((Seccion)productoCombo.getSecciones().elementAt(i), promocionAplicada, Sesion.condicionesCombo));
			break;
		case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_MARCA:
			for(int i=0;i<productoCombo.getMarcas().size();i++)
				detallesAfectados.addAll(Detector.getProdMarca((String)productoCombo.getMarcas().elementAt(i), promocionAplicada, Sesion.condicionesCombo));
			break;
		case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_REFERENCIA:
			for(int i=0;i<productoCombo.getReferencias().size();i++)
				detallesAfectados.addAll(Detector.getProdRefProveedor((Referencia)productoCombo.getReferencias().elementAt(i), promocionAplicada, Sesion.condicionesCombo));
		}
		return detallesAfectados;
	}
}//Fin Clase
