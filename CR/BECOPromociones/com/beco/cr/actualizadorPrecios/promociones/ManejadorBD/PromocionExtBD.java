/*				
 * 
 * Creado el 24/05/2008
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.beco.cr.actualizadorPrecios.promociones.ManejadorBD;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

import java.util.Vector;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.beco.cr.actualizadorPrecios.promociones.Detector;
import com.beco.cr.actualizadorPrecios.promociones.ProductoCombo;
import com.beco.cr.actualizadorPrecios.promociones.PromocionExt;
import com.beco.cr.actualizadorPrecios.promociones.extensiones.ManejadorReportesPromociones;
import com.beco.cr.actualizadorPrecios.promociones.extensiones.ManejadorReportesPromocionesFactory;
import com.beco.cr.actualizadorPrecios.promociones.interfaz.VentanaDonacion;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarservicio.DetalleServicio;
import com.becoblohm.cr.manejarventa.Detalle;
import com.becoblohm.cr.manejarventa.Venta;
import com.becoblohm.cr.manejarventa.DetalleTransaccion;
import com.becoblohm.cr.manejarventa.Producto;
import com.becoblohm.cr.mediadoresbd.ConexionServCentral;
import com.becoblohm.cr.mediadoresbd.Conexiones;

/**
 * @author aavila
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class PromocionExtBD {
	private static final Logger logger = Logger.getLogger(PromocionExtBD.class);
	public static Date d = new Date();
	public static Time t = new Time(d.getTime());
	public static Vector<PromocionExt> v = null;
	public static VentanaDonacion vd = null;
	static SimpleDateFormat fechaActual = new SimpleDateFormat("yyyy-MM-dd");
	static String fechaActualString = fechaActual.format(d);
	static String horaActual = t.toString();
	
	private static ManejadorReportesPromociones manejadorReportes;
	
	public PromocionExtBD(){
		d = new Date(); 
	}
	/**
	 * @return
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private static Vector<PromocionExt> cargarPromociones() {
		if (logger.isDebugEnabled())logger.debug("cargarPromocion - start");
		if (PromocionExtBD.v==null){
			Vector<PromocionExt> vpromociones = new Vector<PromocionExt>();
			ResultSet promociones = null;
			String query = "select *, 0 as sumMontoTotal, 0 as sumCantidad from " +
					"promocion p inner join detallepromocionext d on (p.codpromocion = d.codpromocion) " +
					"where "
				+ " (d.estadoregistro = '" + Sesion.PROMOCION_ACTIVA + "') and "
				+ "((p.fechainicio < '" + fechaActualString + "' and p.fechafinaliza > '" + fechaActualString + "') or "
				+ "(p.fechainicio = '" + fechaActualString + "' and p.fechafinaliza <> '" + fechaActualString + "' and p.horainicio <= '" + horaActual + "') or "
				+ "(p.fechainicio <> '" + fechaActualString + "' and p.fechafinaliza = '" + fechaActualString + "' and p.horafinaliza >= '" + horaActual + "') or "
				+ "(p.fechainicio = '" + fechaActualString + "' and p.fechafinaliza = '" + fechaActualString + "' and ('" + horaActual + "' between p.horainicio and p.horafinaliza))) " +
				" AND p.tipopromocion in ('"+Sesion.TIPO_PROMOCION_REGALO_BONO_REGALO+"', '"+Sesion.TIPO_PROMOCION_REGALO_CALCOMANIA+"', '"+Sesion.TIPO_PROMOCION_REGALO_CUPON+"', '"+
				Sesion.TIPO_PROMOCION_REGALO_PRODUCTO+"') group by p.codpromocion";
			try{
				promociones = Conexiones.realizarConsulta(query, true);
				promociones.beforeFirst();
				while(promociones.next()){
					PromocionExt p = resultSet2PromocionExtRegalo(promociones);
					vpromociones.addElement(p);
				}
			}catch(Exception ex){
				logger.error("cargarPromocion()", ex);
			}finally{
				if (promociones!=null){
					try{
						promociones.close();
					}catch(SQLException e){
						logger.error("cargarPromocion()",e);
					}
					promociones=null;
				}
			}
			if(logger.isDebugEnabled())	logger.debug("cargarPromocion() - end");
			return vpromociones;
		}
		return v;
	}
	/**
	 * 
	 * @param n = 1 es para mostrar por pantalla cuantos regalos debe de entregar el usuario
	 * 		  n = 2 es para imprimir la cantidad de cupones.
	 */
	public static void iteradorRegalo(int n){
		PromocionExt pE = null;
		try{
			
		if(n==1){
			//BECO: WDIAZ 07-2012
			//Se Crea tabla temporal en Memoria de los productos en Venta, 
			CR.me.setPromoMontoCantidad(montoPromocion());
			//
			Iterator<String> i = CR.me.getPromoMontoCantidad().keySet().iterator();
			while (i.hasNext())
			{
				pE = (PromocionExt)CR.me.getPromoMontoCantidad().get(i.next());
				pE.resolverRegalo();
			}
		
		}else if(n==2){
			if(!Venta.regalosRegistrados.isEmpty()){
				Iterator<String> i = CR.me.getPromoMontoCantidad().keySet().iterator();
				while (i.hasNext())
				{
					pE = (PromocionExt)CR.me.getPromoMontoCantidad().get(i.next());
					if(PromocionExtBD.manejadorReportes==null)
						PromocionExtBD.iniciarManejadorReportes();
					try{
						PromocionExtBD.manejadorReportes.imprimeRegalos(pE);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
		}catch(Exception e){e.printStackTrace();}
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<PromocionExt> getRegalos(){
		Iterator<PromocionExt> i = PromocionExtBD.cargarPromociones().iterator();
		Vector<PromocionExt> regalosActivos = new Vector<PromocionExt>();
		while (i.hasNext()){
			PromocionExt pE = (PromocionExt)i.next();
			if ((pE.getTipoPromocion()==Sesion.TIPO_PROMOCION_REGALO_BONO_REGALO) 
					|| (pE.getTipoPromocion()==Sesion.TIPO_PROMOCION_REGALO_CALCOMANIA) 
					|| (pE.getTipoPromocion()==Sesion.TIPO_PROMOCION_REGALO_CUPON)
					|| (pE.getTipoPromocion()==Sesion.TIPO_PROMOCION_REGALO_PRODUCTO)) 
				regalosActivos.add(pE);
		}
		return regalosActivos;
	}
	
	
	private static PromocionExt resultSet2PromocionExtRegalo(ResultSet p) throws BaseDeDatosExcepcion {
		try{
		int codPromocion = p.getInt("codpromocion");
		Date fechainicio = p.getDate("fechainicio");
		Time horainicio = p.getTime("horainicio");
		Date fechafinaliza = p.getDate("fechafinaliza");
		Time horafinaliza = p.getTime("horafinaliza");
		char tipopromocion = p.getString("tipopromocion").toCharArray()[0];
		int numdetalle = p.getInt("numdetalle");
		double montoMinimo = p.getDouble("montoMinimo");
		double bsBonoRegalo = p.getDouble("bsDescuento");
		String nombrePromocion = p.getString("nombrePromocion");
		String acumulaPremio = p.getString("acumulapremio");
		
		//BECO: WDIAZ 07-2012 Campos agregados para la promocion de Premio Ilusion para tener los totales en el objeto
		double sumMontoTotal = p.getDouble("sumMontoTotal");
		int sumCantidad = p.getInt("sumCantidad");
		//
		
		PromocionExt pExt = new PromocionExt(codPromocion, fechainicio, horainicio, fechafinaliza,
				horafinaliza,  tipopromocion, numdetalle, montoMinimo, bsBonoRegalo, nombrePromocion, acumulaPremio, sumMontoTotal, sumCantidad);				   
			return pExt;
		}catch(SQLException e){throw new BaseDeDatosExcepcion("ERROR CARGANDO Regalos", e);}
	}
	
	/**
	 * Carga la PromocionExt
	 * @param p
	 * @return PromocionExt La instancia de PromocionExt con solo los datos que interesan para 
	 * transaccion premiada
	 */
	private static PromocionExt resultSet2PromocionExtTransPremiadaOCorporativas(ResultSet p) throws BaseDeDatosExcepcion {
		try{
		int codPromocion = p.getInt("codpromocion");
		Date fechainicio = p.getDate("fechainicio");
		Time horainicio = p.getTime("horainicio");
		Date fechafinaliza = p.getDate("fechafinaliza");
		Time horafinaliza = p.getTime("horafinaliza");
		int prioridad = p.getInt("prioridad");
		char tipopromocion = (p.getString("tipopromocion")).toCharArray()[0];
		int numdetalle = p.getInt("numDetalle");
		double porcDescuento = p.getDouble("porcentajeDescuento");
		String nombrePromocion = p.getString("nombrePromocion");
		
		PromocionExt pExt = new PromocionExt(codPromocion, fechainicio, horainicio, fechafinaliza,
				horafinaliza, prioridad, tipopromocion, numdetalle, porcDescuento, nombrePromocion);				   
			return pExt;
		}catch(SQLException e){throw new BaseDeDatosExcepcion("ERROR CARGANDO RDonaciones", e);}
	}
	
	/**
	 * Determina si existen transacciones por premiar en esta caja
	 * @return PromocionExt El detalle promocion correspondiente a la transaccion premiada
	 * en caso de existir una transaccion por premiar. Null en caso contrario
	 * **/
	public static PromocionExt transaccionesPorPremiar(){
		if (logger.isDebugEnabled()) logger.debug("transaccionesPorPremiar() - start");
		PromocionExt p = null;
		ResultSet promocionesExt = null;
		String query = "SELECT p.*, dpe.* "
			+"FROM transaccionpremiada t1, detallepromocionext dpe, promocion p "
			+"WHERE t1.premioPorEntregar = 1 "+
			" AND t1.codPromocion=dpe.codPromocion AND t1.numDetalle=dpe.numDetalle "+
			" AND dpe.codPromocion=p.codPromocion "+
			" AND p.fechainicio<=current_date() and fechafinaliza>=current_date() "+
			" AND dpe.estadoRegistro='"+Sesion.PROMOCION_ACTIVA+"' "+
			" ORDER BY t1.horaGanador DESC LIMIT 1";
		try{
			promocionesExt = Conexiones.realizarConsulta(query, true);
			promocionesExt.beforeFirst();
			while(promocionesExt.next()){
				p = resultSet2PromocionExtTransPremiadaOCorporativas(promocionesExt);
			}
		}catch(Exception ex){
			logger.error("transaccionesPorPremiar()", ex);
		}finally{
			if (promocionesExt!=null){
				try{
					promocionesExt.close();
				}catch(SQLException e){
					logger.error("trasnaccionpremiada()",e);
				}
				promocionesExt=null;
			}
		}
		if (logger.isDebugEnabled())logger.debug("transaccionesPorPremiar() - end");
		return p;
	} 

	
	/**
	 * Carga la PromocionExt
	 * @param p
	 * @return PromocionExt La instancia de PromocionExt con solo los datos que interesan para 
	 * cupón desprendible
	 */
	private static PromocionExt resultSet2PromocionExtCuponDesprendible(ResultSet p) throws BaseDeDatosExcepcion {
		try{
			int codPromocion = p.getInt("codpromocion");
			Date fechainicio = p.getDate("fechainicio");
			Time horainicio = p.getTime("horainicio");
			Date fechafinaliza = p.getDate("fechafinaliza");
			Time horafinaliza = p.getTime("horafinaliza");
			int prioridad = p.getInt("prioridad");
			char tipopromocion = p.getString("tipopromocion").toCharArray()[0];
			double porcDescuento =0;
			double bsDescuento = 0;
			try{
				porcDescuento = p.getDouble("porcentajeDescuento");
			} catch(Exception e){ 
				//Nada: es un cupon de bs
			}
			try{
				bsDescuento = p.getDouble("bsDescuento");
			}catch(Exception e){
				//Nada: es un cupon de %
			}		
			String codProducto = null;
			try{
				codProducto = p.getString("codProducto");
			} catch(Exception e){
				//Nada: es sobre el total de la compra
			}
			int numDetalle=0;
			try{
				numDetalle = p.getInt("numDetalle");
			}catch(Exception e){
				//Nada: estoy cargando la ventana
			}
			String nombrePromocion = p.getString("nombrePromocion");
			
			PromocionExt pExt = new PromocionExt(codPromocion, numDetalle, fechainicio, horainicio, fechafinaliza,
					horafinaliza, prioridad, tipopromocion, porcDescuento, bsDescuento, codProducto, nombrePromocion);				   
				return pExt;
		} catch(SQLException e){
			throw new BaseDeDatosExcepcion("ERROR CARGANDO RDonaciones", e);
		}
	}
	
	/**
	 * Obtiene un vector con las promocionesExt correspondientes a los cupones
	 * desprendibles que estan vigentes
	 * @return Vector listado de promociones de cupones desprendibles actuales
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<PromocionExt> cuponesActivos(){
		if (logger.isDebugEnabled()) logger.debug("cuponesActivos() - start");
		
		Vector<PromocionExt> promocionesCupones = new Vector<PromocionExt>();
				
		ResultSet promocionesExt = null;
		String query = "SELECT DISTINCT p.*, dpe.codPromocion, dpe.porcentajeDescuento, dpe.bsDescuento, dpe.nombrePromocion "
			+"FROM detallepromocionext dpe, promocion p "+
			" WHERE dpe.codPromocion=p.codPromocion "+
			" AND p.fechainicio<=current_date() and fechafinaliza>=current_date() "+ //esta vigente
			" AND dpe.estadoRegistro='"+Sesion.PROMOCION_ACTIVA+"' "+ //esta activa
			" AND (p.tipopromocion='"+Sesion.TIPO_PROMOCION_CUPON_DESCUENTO_BS+"' " +
					"OR p.tipopromocion='"+Sesion.TIPO_PROMOCION_CUPON_DESCUENTO_PORCENTAJE+"' " +
							"OR p.tipopromocion='"+Sesion.TIPO_PROMOCION_CALCOMANIA_BS+"' " +
									"OR p.tipopromocion='"+Sesion.TIPO_PROMOCION_CALCOMANIA_PORCENTAJE+"')"; //es cupon
		try{
			promocionesExt = Conexiones.realizarConsulta(query, true);
			promocionesExt.beforeFirst();
			while(promocionesExt.next()){
				PromocionExt p = resultSet2PromocionExtCuponDesprendible(promocionesExt);
				promocionesCupones.addElement(p);
			}
		}catch(Exception ex){
			logger.error("cuponesActivos()", ex);
		}finally{
			if (promocionesExt!=null){
				try{
					promocionesExt.close();
				}catch(SQLException e){
					logger.error("cuponesActivos()",e);
				}
				promocionesExt=null;
			}
		}
		if (logger.isDebugEnabled())logger.debug("cuponesActivos() - end");
		return promocionesCupones;
	}
	
	/**
	 * Obtiene todos los productos qu ese ven afectados por el cupon
	 * correspondiente a la promocion con codigo codPromocion
	 * @param codPromocion codigo de la promocion a la que pertenece el cupon
	 * @return Vector listado de productos de productos
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Producto> getProductosPorCupon(int codPromocion){
		Vector<Producto> productos = new Vector<Producto>();
		ResultSet promocionesExt = null;
		String query = "SELECT pr.* "
			+"FROM detallepromocionext dpe, promocion p, producto pr "+
			" WHERE dpe.codPromocion=p.codPromocion "+
			" AND dpe.codProducto=pr.codproducto "+
			" AND p.fechainicio<=current_date() and fechafinaliza>=current_date() "+ //esta vigente
			" AND dpe.estadoRegistro='"+Sesion.PROMOCION_ACTIVA+"' "+ //esta activa
			" AND (p.tipopromocion='"+Sesion.TIPO_PROMOCION_CALCOMANIA_BS+"' " +
					"OR p.tipopromocion='"+Sesion.TIPO_PROMOCION_CALCOMANIA_PORCENTAJE+"') "+
			" AND p.codpromocion = "+codPromocion; //es cupon para producto
		try{
			promocionesExt = Conexiones.realizarConsulta(query, true);
			promocionesExt.beforeFirst();
			while(promocionesExt.next()){
				String codProducto = promocionesExt.getString("codProducto");
				double precioRegular = promocionesExt.getDouble("precioregular");
				Producto p = new Producto(codProducto);
				p.setPrecioRegular(precioRegular);
				productos.addElement(p);
			}
		}catch(Exception ex){
			logger.error("getProductosPorCupon()", ex);
		}finally{
			if (promocionesExt!=null){
				try{
					promocionesExt.close();
				}catch(SQLException e){
					logger.error("getProductosPorCupon()",e);
				}
				promocionesExt=null;
			}
		}
		if (logger.isDebugEnabled())logger.debug("getProductosPorCupon() - end");
		return productos;
	}
	
	/**
	 * Carga la Promocion Producto complementario de Mercadeo
	 * @param p
	 * @return PromocionExt La instancia de PromocionExt con solo los datos que interesan para 
	 * Producto complementario de Mercadeo
	 */
	/*public static Vector cargarPromocionesProductoRegaladoMercadeo() {
		if (logger.isDebugEnabled())logger.debug("cargarDonacion - start");
		if (PromocionExtBD.v==null){
		Vector vpromociones = new Vector();
		ResultSet promociones = null;
		String query = "select * from promocion p inner join detallepromocionext d on (p.codpromocion = d.codpromocion) where "
			+ " (d.estadoregistro = '" + Sesion.PROMOCION_ACTIVA + "') and (tipopromocion= '"+Sesion.TIPO_PROMOCION_REGALO_PRODUCTO+"') and "
			+ "((p.fechainicio < '" + fechaActualString + "' and p.fechafinaliza > '" + fechaActualString + "') or "
			+ "(p.fechainicio = '" + fechaActualString + "' and p.fechafinaliza <> '" + fechaActualString + "' and p.horainicio <= '" + horaActual + "') or "
			+ "(p.fechainicio <> '" + fechaActualString + "' and p.fechafinaliza = '" + fechaActualString + "' and p.horafinaliza >= '" + horaActual + "') or "
			+ "(p.fechainicio = '" + fechaActualString + "' and p.fechafinaliza = '" + fechaActualString + "' and ('" + horaActual + "' between p.horainicio and p.horafinaliza)))";
		try{
			promociones = Conexiones.realizarConsulta(query, true);
			promociones.beforeFirst();
			while(promociones.next()){
			PromocionExt p = resultSet2PromocionExtProductoRegaladoMercadeo(promociones);
			Vector lineasCondiciones = PromocionExtBD.cargarCondicionesPromocion(p.getCodPromocion());
			p.setLineasCondiciones(lineasCondiciones);
			vpromociones.addElement(p);
			}
		}catch(Exception ex){
			logger.error("cargarPromocion()", ex);
		}finally{
			if (promociones!=null){
				try{
					promociones.close();
				}catch(SQLException e){
					logger.error("cargarPromocion()",e);
				}
				promociones=null;
			}
		}
		if(logger.isDebugEnabled())	logger.debug("cargarPromocion() - end");
		return vpromociones;
		}
	return v;
	}*/
	/*public static PromocionExt resultSet2PromocionExtProductoRegaladoMercadeo (ResultSet p) throws BaseDeDatosExcepcion{
		try{
			int codPromocion = p.getInt("codpromocion");
			int numdetalle = p.getInt("numdetalle");
			String codProducto = p.getString("codProducto");
			String nombrePromocion = p.getString("nombrePromocion");
			String acumulaPremio = p.getString("acumulaPremio");
			PromocionExt prm = new PromocionExt(codPromocion,numdetalle,codProducto,nombrePromocion,acumulaPremio);				   
				return prm;
			}catch(SQLException e){
				throw new BaseDeDatosExcepcion("ERROR CARGANDO Producto complementario", e);
		}
	}*/
	/**
	 * Obtiene un vector con las promocionesExt correspondientes a las promociones
	 * de ahorro en compra activas
	 * @return Vector listado de promociones de ahorro en compra activas
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<PromocionExt> ahorrosEnCompra(){
		if (logger.isDebugEnabled()) logger.debug("cuponesActivos() - start");
		
		Vector<PromocionExt> promociones = new Vector<PromocionExt>();	
		ResultSet promocionesExt = null;
		String query = "SELECT DISTINCT p.codpromocion, p.prioridad, dpe.porcentajeDescuento, dpe.montoMinimo, dpe.nombrePromocion " +
		"FROM promocion p, detallepromocionext dpe " +
		"WHERE p.tipopromocion='"+Sesion.TIPO_PROMOCION_AHORRO_COMPRA+"' AND " +
		" p.codpromocion=dpe.codPromocion  AND "  +
		"estadoRegistro='"+Sesion.PROMOCION_ACTIVA+"' AND " +
		" p.fechainicio<=current_date() AND p.fechafinaliza>=current_date() ";
		try{
			promocionesExt = Conexiones.realizarConsulta(query, true);
			promocionesExt.beforeFirst();
			while(promocionesExt.next()){
				PromocionExt p = resultSet2PromocionExtAhorroEnCompra(promocionesExt);
				promociones.addElement(p);
			}
		}catch(Exception ex){
			logger.error("ahorrosEnCompra()", ex);
		}finally{
			if (promocionesExt!=null){
				try{
					promocionesExt.close();
				}catch(SQLException e){
					logger.error("ahorrosEnCompra()",e);
				}
				promocionesExt=null;
			}
		}
		if (logger.isDebugEnabled())logger.debug("ahorrosEnCompra() - end");
		return promociones;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	/*
	 * Funcion, que dado un codigo de promocion, regresa el objeto si y solo si la promo es ahorro en compra
	 * esta activa y en la fecha adecuada.
	 */
	public static PromocionExt ahorroEnCompra(int codPromocion){
		if (logger.isDebugEnabled()) logger.debug("cuponesActivos() - start");
		
		PromocionExt promo = null;
		ResultSet promocionesExt = null;
		String query = "SELECT DISTINCT p.codpromocion, p.prioridad, dpe.porcentajeDescuento, dpe.montoMinimo, dpe.nombrePromocion " +
		"FROM promocion p, detallepromocionext dpe " +
		"WHERE p.codpromocion = "+codPromocion+" AND p.tipopromocion='"+Sesion.TIPO_PROMOCION_AHORRO_COMPRA+"' AND " +
		" p.codpromocion=dpe.codPromocion  AND "  +
		"estadoRegistro='"+Sesion.PROMOCION_ACTIVA+"' AND " +
		" p.fechainicio<=current_date() AND p.fechafinaliza>=current_date() ";
		try{
			promocionesExt = Conexiones.realizarConsulta(query, true);
			promocionesExt.beforeFirst();
			while(promocionesExt.next()){
				promo = resultSet2PromocionExtAhorroEnCompra(promocionesExt);

			}
		}catch(Exception ex){
			logger.error("ahorrosEnCompra()", ex);
		}finally{
			if (promocionesExt!=null){
				try{
					promocionesExt.close();
				}catch(SQLException e){
					logger.error("ahorrosEnCompra()",e);
				}
				promocionesExt=null;
			}
		}
		if (logger.isDebugEnabled())logger.debug("ahorrosEnCompra() - end");
		return promo;
	}
	
	/**
	 * Carga la PromocionExt
	 * @param p
	 * @return PromocionExt La instancia de PromocionExt con solo los datos que interesan para 
	 * ahorro en compra
	 */
	private static PromocionExt resultSet2PromocionExtAhorroEnCompra(ResultSet p) throws BaseDeDatosExcepcion {
		try{
		int codPromocion = p.getInt("codpromocion");
		int numDetalle = 0;
		try{
			numDetalle = p.getInt("numDetalle");
		} catch(Exception e){}
		double porcDescuento = p.getDouble("porcentajeDescuento");
		double montoMinimo = p.getDouble("montoMinimo");
		String codProducto =null;
		try{
			codProducto = p.getString("codProducto");
		}catch(Exception e){}
		String nombrePromocion = p.getString("nombrePromocion");
		int prioridad = 0;
		try{
			prioridad = p.getInt("prioridad");
		} catch(Exception e){}
		char tipoPromocion = 0;
		try{
			tipoPromocion = p.getString("tipopromocion").toCharArray()[0];
		} catch(Exception e){}
		
		PromocionExt pExt = new PromocionExt(codPromocion, 
				numDetalle,
				porcDescuento,
				montoMinimo,
				codProducto,
				nombrePromocion,
				prioridad,
				tipoPromocion
				);		   
			return pExt;
		}catch(SQLException e){throw new BaseDeDatosExcepcion("ERROR CARGANDO RDonaciones", e);}
	}
	
	/**
	 * Obtiene un vector con las promocionesExt correspondientes a las promociones
	 * de ahorro en compra activas y que corresponden con el codigo de producto indicado
	 * VERIFICA ADEMAS QUE CADA PROMOCION A AGREGAR NO ESTE EN promocionesViejas
	 * @param codProducto
	 * @param promocionesViejas
	 * @return Vector listado de promociones de ahorro en compra activas
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<PromocionExt> ahorrosEnCompraPorProducto(String codProducto, int codPromocion, double montoTransaccion){
		if (logger.isDebugEnabled()) logger.debug("ahorrosEnCompraPorProducto(String) - start");
		
		Vector<PromocionExt> promociones = new Vector<PromocionExt>();
		ResultSet promocionesExt = null;
		String query = "SELECT p.codpromocion, " +
				"dpe.numDetalle, " +
				"dpe.porcentajeDescuento, " +
				"dpe.montoMinimo, " +
				"dpe.nombrePromocion, " +
				"dpe.codProducto," +
				"p.prioridad," +
				"p.tipopromocion " +
		" FROM promocion p inner join detallepromocionext dpe on (p.codpromocion=dpe.codPromocion) " +
		" inner join  producto prod on (dpe.codProducto=prod.codproducto) " +
		" WHERE p.tipopromocion='"+Sesion.TIPO_PROMOCION_AHORRO_COMPRA+"' AND " +
		" dpe.codProducto ='"+codProducto+"' AND " +
		" (dpe.montoMinimo<="+montoTransaccion+" OR " +
		" dpe.montoMinimo<=prod.precioregular) AND " + 
		" estadoRegistro='"+Sesion.PROMOCION_ACTIVA+"' AND " +
		" p.fechainicio<=current_date() AND p.fechafinaliza>=current_date() ";
		
		if(codPromocion!=0)
			query+= " AND p.codpromocion='"+codPromocion+"' order by p.prioridad asc ";
		else 
			query+= " order by prioridad asc ";
		try{
			promocionesExt = Conexiones.realizarConsulta(query, true);
			promocionesExt.beforeFirst();
			while(promocionesExt.next()){
				PromocionExt p = resultSet2PromocionExtAhorroEnCompra(promocionesExt);
				//Verifico que la promocion no exita en promocionesViejas
				/*Iterator iteraViejas = promocionesViejas.iterator();
				boolean contiene = false;
				while (iteraViejas.hasNext()){
					Promocion promoVieja = (Promocion)iteraViejas.next();
					if(promoVieja.getCodPromocion() == p.getCodPromocion()){
						contiene = true;
						break;
					}
				}
				//agrego solo si no existe ya en las viejas
				if(!contiene)*/ 
				promociones.addElement(p);
			}
		}catch(Exception ex){
			logger.error("ahorrosEnCompra()", ex);
		}finally{
			if (promocionesExt!=null){
				try{
					promocionesExt.close();
				}catch(SQLException e){
					logger.error("ahorrosEnCompraPorProducto(String)",e);
				}
				promocionesExt=null;
			}
		}
		if (logger.isDebugEnabled())logger.debug("ahorrosEnCompraPorProducto(String) - end");
		return promociones;
	}
	
	/**
	 * Carga las condiciones de la promocion indicada
	 * @param codigoPromocion
	 * @return Vector Cada casilla del vector es una línea de las condiciones
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<String> cargarCondicionesPromocion(int codigoPromocion){
		Vector<String> lineasCondiciones =  new Vector<String>();
		
		ResultSet condiciones = null;
		String query = "SELECT lineaCondicion " +
		"FROM condicionpromocion " +
		"WHERE codigoPromocion = "+codigoPromocion +
		" ORDER BY orden ";
		try{
			condiciones = Conexiones.realizarConsulta(query, true);
			condiciones.beforeFirst();
			while(condiciones.next()){
				String l = condiciones.getString("lineaCondicion");
				lineasCondiciones.addElement(l);
			}
		}catch(Exception ex){
			logger.error("cargarCondicionesPromocion(int)", ex);
		}finally{
			if (condiciones!=null){
				try{
					condiciones.close();
				}catch(SQLException e){
					logger.error("cargarCondicionesPromocion(int)",e);
				}
				condiciones=null;
			}
		}
		return lineasCondiciones;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<PromocionExt> corporativasActivas(){
		Vector<PromocionExt> corporativas = new Vector<PromocionExt>();
		
		ResultSet promocionesExt = null;
		String query = "SELECT DISTINCT p.*, dpe.numDetalle, dpe.porcentajeDescuento, dpe.nombrePromocion "
			+"FROM detallepromocionext dpe inner join promocion p on (dpe.codPromocion=p.codPromocion) "+
			" WHERE (p.tipopromocion='"+Sesion.TIPO_PROMOCION_COORPORATIVA+"') AND " +
			" p.fechainicio<=current_date() AND fechafinaliza>=current_date() "+ //esta vigente
			" AND dpe.estadoRegistro='"+Sesion.PROMOCION_ACTIVA+"' "; //esta activa
		try{
			promocionesExt = Conexiones.realizarConsulta(query, true);
			promocionesExt.beforeFirst();
			while(promocionesExt.next()){
				PromocionExt p = resultSet2PromocionExtTransPremiadaOCorporativas(promocionesExt);
				corporativas.addElement(p);
			}
		}catch(Exception ex){
			logger.error("cuponesActivos()", ex);
		}finally{
			if (promocionesExt!=null){
				try{
					promocionesExt.close();
				}catch(SQLException e){
					logger.error("cuponesActivos()",e);
				}
				promocionesExt=null;
			}
		}
		return corporativas;
	}
	
	/**
	 * Obtiene la promocion corporativa correspondiente al codigo indicado
	 * @param codPromocion
	 * @return PromocionExt
	 */
	public static PromocionExt getPromocionCorporativaPorCodigo(int codPromocion){
		PromocionExt promocion = null;
		
		ResultSet promocionesExt = null;
		String query = "Select distinct p.*, dpe.numDetalle, dpe.porcentajeDescuento, dpe.nombrePromocion " +
				"from promocion p inner join detallepromocionext dpe on (dpe.codPromocion=p.codpromocion) " +
				"where p.codpromocion="+codPromocion+
				" and p.fechainicio<=current_date() and fechafinaliza>=current_date() "+ //esta vigente
				" AND dpe.estadoRegistro='"+Sesion.PROMOCION_ACTIVA+"' "; //esta activa"; //
		try{
			promocionesExt = Conexiones.realizarConsulta(query, true);
			promocionesExt.beforeFirst();
			if(promocionesExt.next()){
				promocion =  resultSet2PromocionExtTransPremiadaOCorporativas(promocionesExt);
			}
		}catch(Exception ex){
			logger.error("getPromocionCorporativaPorCodigo(int)", ex);
		}finally{
			if (promocionesExt!=null){
				try{
					promocionesExt.close();
				}catch(SQLException e){
					logger.error("getPromocionCorporativaPorCodigo(int)",e);
				}
				promocionesExt=null;
			}
		}
		
		return promocion;
	}
	
	/**
	 * Obtiene la promocion corporativa correspondiente al codigo indicado
	 * @return PromocionExt
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<PromocionExt> getPromocionCorporativa(){
		Vector<PromocionExt> promocion = new Vector<PromocionExt>();
		
		ResultSet promocionesExt = null;
		String query = "Select p.*, dpe.numDetalle, dpe.porcentajeDescuento, dpe.nombrePromocion " +
				"from promocion p, detallepromocionext dpe " +
				"where dpe.codPromocion=p.codpromocion and p.tipopromocion='"+Sesion.TIPO_PROMOCION_COORPORATIVA+"' and dpe.estadoRegistro='"+Sesion.PROMOCION_ACTIVA+"' " +
						"  and p.fechainicio<=current_date() AND p.fechafinaliza>=current_date()  "; //
		try{
			promocionesExt = Conexiones.realizarConsulta(query, true);
			promocionesExt.beforeFirst();
			while(promocionesExt.next()){
				promocion.add(resultSet2PromocionExtTransPremiadaOCorporativas(promocionesExt));
			}
		}catch(Exception ex){
			logger.error("getPromocionCorporativaPorCodigo(int)", ex);
		}finally{
			if (promocionesExt!=null){
				try{
					promocionesExt.close();
				}catch(SQLException e){
					logger.error("getPromocionCorporativaPorCodigo(int)",e);
				}
				promocionesExt=null;
			}
		}
		
		return promocion;
	}
	
	/**
	 * Carga la PromocionExt
	 * @param p
	 * @return PromocionExt La instancia de PromocionExt con solo los datos que interesan los diferentes
	 * tipos de combos
	 */
	private static PromocionExt resultSet2PromocionExtCombo(ResultSet p) throws BaseDeDatosExcepcion {
		try{
		int codPromocion = p.getInt("codpromocion");
		Date fechainicio = p.getDate("fechainicio");
		Time horainicio = p.getTime("horainicio");
		Date fechafinaliza = p.getDate("fechafinaliza");
		Time horafinaliza = p.getTime("horafinaliza");
		int prioridad = p.getInt("prioridad");
		char tipopromocion = (p.getString("tipopromocion")).toCharArray()[0];
		double porcDescuento = p.getDouble("porcentajeDescuento");
		double precioFinal = p.getDouble("bsDescuento");
		String nombrePromocion = p.getString("nombrePromocion");
		//String acumulaPremio="N";
		/*try{
		acumulaPremio = p.getString("acumulaPremio");
		}catch(Exception e1){
			System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: Problema dentro de acumulapremio");
			e1.printStackTrace();
			
		}*/
		
		int cantMinima = 0;
		try{
			cantMinima = p.getInt("cantMinima");
		} catch(Exception e){ }
		int cantRegalada = 0;
		try{
			cantRegalada = p.getInt("cantRegalada");
		} catch(Exception e){ }
		
		PromocionExt pExt = new PromocionExt(codPromocion, fechainicio, horainicio, fechafinaliza,
				horafinaliza, prioridad, tipopromocion, porcDescuento, nombrePromocion, cantMinima, cantRegalada, precioFinal);				   
			return pExt;
		}catch(SQLException e){
			System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: Problema dentro de acumulapremio");
			e.printStackTrace();
			throw new BaseDeDatosExcepcion("ERROR CARGANDO promociones", e);
		}
	}
	
	/**
	 * Obtiene las promociones de combo correspondientes al producto p
	 * @param p
	 * @param codPromocion Intenta obtener una sola promocion correspondiente a este codigo, si es cero obtiene todas
	 * @param variacion margen de error en la cantidad de productos que pueden no estar en la venta aun
	 * @return Vector de promociones
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<PromocionExt> getPromocionesCombo(Producto p, int codPromocion) {
		Vector<PromocionExt> promociones = new Vector<PromocionExt>();
		//Vector de promociones en caso de que sea necesario desarmar un combo ya existente
		Vector<PromocionExt> promocionesDesarmar = new Vector<PromocionExt>();
		boolean noDesarmar = true;
		Vector<Producto> contieneP = new Vector<Producto>();
		contieneP.addElement(p);
		ResultSet promocionesExt = null;
		
		String query = "SELECT DISTINCT p.*, dpe.porcentajeDescuento, " +
										" dpe.nombrePromocion, cantMinima, cantRegalada, dpe.bsDescuento  " +
			" FROM detallepromocionext dpe inner join promocion p on (dpe.codPromocion=p.codPromocion) " +
			" WHERE " +
			" p.fechainicio<=current_date() and p.fechafinaliza>=current_date() "+ //esta vigente
			" AND dpe.estadoRegistro='"+Sesion.PROMOCION_ACTIVA+"' " + 				//Activa
			" AND ( p.tipopromocion='"+Sesion.TIPO_PROMOCION_DESCUENTO_EN_COMBO+"' OR " +
					"p.tipopromocion='"+Sesion.TIPO_PROMOCION_DESCUENTO_EN_COMBO_LINEA+"' OR " +
					"p.tipopromocion='"+Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_PRODUCTO+"' OR " +
					"p.tipopromocion='"+Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_CATEGORIA+"' OR " +
					"p.tipopromocion='"+Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_DEPARTAMENTO+"' OR " +
					"p.tipopromocion='"+Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_LINEA+"' OR " +
					"p.tipopromocion='"+Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_SECCION+"' OR " +
					"p.tipopromocion='"+Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_MARCA+"' OR " +
					"p.tipopromocion='"+Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_REFERENCIA+"' " +
				" ) "; //Es un combo
		
		if(codPromocion!=0){
			query+=" AND p.codpromocion="+codPromocion+" ";
		}
		if(p!=null){
			/*try{
				marca =  p.getMarca().replace("\'", "\\\'");
			} catch(Exception e){
				
			}*/
			query+=" AND (" +
						// Límite de productos en descuento por producto
						" (p.tipopromocion='"+Sesion.TIPO_PROMOCION_DESCUENTO_EN_COMBO+"' and dpe.codProducto='"+p.getCodProducto()+"') OR "+
						// Límite de productos en descuento por linea
						" (p.tipopromocion='"+Sesion.TIPO_PROMOCION_DESCUENTO_EN_COMBO_LINEA+"' and dpe.linea='"+Integer.parseInt(p.getLineaSeccion())+"' and dpe.departamento='"+p.getCodDepartamento()+"' ) OR "+
						// Es un combo 2x1 por seccion
						" (p.tipopromocion='"+Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_SECCION+"' and dpe.departamento='"+p.getCodDepartamento()+"' and dpe.linea='"+Integer.parseInt(p.getLineaSeccion())+"' and dpe.codSeccion="+p.getSeccion()+")  OR "+
						// Es un combo 2x1 por departamento
						" (p.tipopromocion='"+Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_DEPARTAMENTO+"' and dpe.departamento='"+p.getCodDepartamento()+"')  OR "+
						// Es combo 2x1 por referencia proveedor
						" (p.tipopromocion='"+Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_REFERENCIA+"' and dpe.refProveedor='"+p.getReferenciaProveedor()+"') OR "+
						// Es combo 2x1 por marca
						//" (p.tipopromocion='"+Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_MARCA+"' and dpe.marca='"+p.getMarca()+"') OR "+
						// Es combo 2x1 por codigo de producto
						" (p.tipopromocion='"+Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_PRODUCTO+"' and dpe.codProducto='"+p.getCodProducto()+"') OR "+
						// Es 2x1 por línea
						" (p.tipopromocion='"+Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_LINEA+"' and dpe.linea='"+Integer.parseInt(p.getLineaSeccion())+"' and dpe.departamento='"+p.getCodDepartamento()+"' ) OR "+
						// Es combo 2x1 por categoria
						" (p.tipopromocion='"+Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_CATEGORIA+"' and dpe.categoria='"+p.getCategoria().trim()+"')) ".trim();
		}
		query+=" order by p.prioridad asc ";
		try{
			promocionesExt = Conexiones.realizarConsulta(query, true);
			promocionesExt.beforeFirst();
			while(promocionesExt.next()){
				PromocionExt promo = resultSet2PromocionExtCombo(promocionesExt);
				if(Sesion.productosCombo.get(promo.getCodPromocion()+"")==null){
					asignarProductosCombo(promo, 0);
					Sesion.productosCombo.put(promo.getCodPromocion()+"", promo.getProductosCombo());
				} else {
					promo.setProductosCombo(Sesion.productosCombo.get(promo.getCodPromocion()+""));
					
				}
				//Verifico si se cumple al condicion para aplicar promo
				Vector<String> condicionesNoPermitidas = new Vector<String>();
				condicionesNoPermitidas.addAll(Sesion.condicionesCombo);
				condicionesNoPermitidas.add(Sesion.condicionDesctoPrecioGarantizado);
				condicionesNoPermitidas.add(Sesion.condicionDesctoPorDefecto);
				condicionesNoPermitidas.add(Sesion.condicionCambioPrecio);
		
				
			if(codPromocion!=0 
						|| p==null 
						|| Detector.verificaCantidadesMinimasCombo(promo,condicionesNoPermitidas, false)){
					promociones.addElement(promo);
				} 
				if(p!=null && (codPromocion!=0 || Detector.verificaCantidadesMinimasCombo(promo,condicionesNoPermitidas, true))){
					promocionesDesarmar.addElement(promo);
					noDesarmar = false;
				}
				
			}
			if(!noDesarmar){
				promociones = promocionesDesarmar;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("getPromocionesCombo(Producto p, int codPromocion)", ex);
		}finally{
			if (promocionesExt!=null){
				try{
					promocionesExt.close();
				}catch(SQLException e){
					logger.error("getPromocionesCombo(Producto p, int codPromocion)",e);
				}
				promocionesExt=null;
			}
		}
		return promociones;
	}
	
	/**
	 * Asigna a la promocion todos sus productos dependiendo del tipo de promocion
	 * @param promo
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private static void asignarProductosCombo(PromocionExt promo, int numdetalle) {
		Vector<ProductoCombo> productosCombo=null;	
		productosCombo = ProductoCombo.getProductosCombo(promo, numdetalle);
		promo.setProductosCombo(productosCombo);
	}
	
	/**
	 * Obtiene las promociones de combo correspondientes al producto p
	 * @param p
	 * @param codPromocion Intenta obtener una sola promocion correspondiente a este codigo, si es cero obtiene todas
	 * @param variacion margen de error en la cantidad de productos que pueden no estar en la venta aun
	 * @return Vector de promociones
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se eliminaron casteos no necesarios
	* Fecha: agosto 2011
	*/
	public static PromocionExt getPromocionAplicadaCombo(int codPromocion, boolean esApartado) {
		PromocionExt promo = null;
		/*Vector contieneP = new Vector();
		Vector detallesp = Detector.getDetallesProd(contieneP);
		Iterator i = detallesp.iterator();
		double cantidadProductos = 0;
		while(i.hasNext()){
			DetalleTransaccion dt = (DetalleTransaccion)i.next();
			cantidadProductos +=dt.getCantidad();
		}*/
		
		ResultSet promocionesExt = null;
		String query = "SELECT DISTINCT p.*, dpe.porcentajeDescuento, " +
										" dpe.nombrePromocion, " +
										" dpe.cantMinima, " +
										"	dpe.cantRegalada, acumulaPremio, dpe.bsDescuento  "
			+"FROM detallepromocionext dpe, promocion p "+
			" WHERE dpe.codPromocion=p.codPromocion ";
		if(!esApartado)
			query+=" AND p.fechainicio<=current_date() and fechafinaliza>=current_date() "; //esta vigente
		query+=" AND dpe.estadoRegistro='"+Sesion.PROMOCION_ACTIVA+"' " + 				//Activa
			" AND ( p.tipopromocion='"+Sesion.TIPO_PROMOCION_DESCUENTO_EN_COMBO+"' OR " +
				"p.tipopromocion='"+Sesion.TIPO_PROMOCION_DESCUENTO_EN_COMBO_LINEA+"' OR " +
				"p.tipopromocion='"+Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_PRODUCTO+"' OR " +
				"p.tipopromocion='"+Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_CATEGORIA+"' OR " +
				"p.tipopromocion='"+Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_DEPARTAMENTO+"' OR " +
				"p.tipopromocion='"+Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_LINEA+"' OR " +
				"p.tipopromocion='"+Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_SECCION+"' OR " +
				"p.tipopromocion='"+Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_MARCA+"' OR " +
				"p.tipopromocion='"+Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_REFERENCIA+"' " +
			" ) "; //Es un combo
		
			if(codPromocion!=0){
				query+=" AND p.codpromocion="+codPromocion+" ";
			}
		try{
			promocionesExt = Conexiones.realizarConsulta(query, true);
			promocionesExt.beforeFirst();
			if(promocionesExt.next()){
				promo = resultSet2PromocionExtCombo(promocionesExt);
				if(Sesion.productosCombo.get(promo.getCodPromocion()+"")==null){
					asignarProductosCombo(promo, 0);
					Sesion.productosCombo.put(promo.getCodPromocion()+"", promo.getProductosCombo());
				} else {
					promo.setProductosCombo(Sesion.productosCombo.get(promo.getCodPromocion()+""));
				}
			}
		}catch(Exception ex){
			logger.error("getPromocionesCombo(Producto p, int codPromocion)", ex);
		}finally{
			if (promocionesExt!=null){
				try{
					promocionesExt.close();
				}catch(SQLException e){
					logger.error("getPromocionesCombo(Producto p, int codPromocion)",e);
				}
				promocionesExt=null;
			}
		}
		return promo;
	}
	
	/**
	 * Obtiene la promoción de producto menor precio gratis
	 * Solo puede existir una promoción de este tipo activa
	 * Es responsabilidad del backoffice belar que esto se cumpla
	 * @return PromocionExt
	 */
	public static PromocionExt getPromocionProductoGratis(){
		PromocionExt promocion = null;
		
		ResultSet promocionesExt = null;
		String query = "Select p.*, dpe.numDetalle, dpe.porcentajeDescuento, dpe.montoMinimo, dpe.cantMinima, dpe.nombrePromocion " +
				"from promocion p, detallepromocionext dpe " +
				"where dpe.codPromocion=p.codpromocion " +
				" AND p.fechainicio<=current_date() and fechafinaliza>=current_date() "+ //esta vigente
				" AND dpe.estadoRegistro='"+Sesion.PROMOCION_ACTIVA+"' " +
				" AND p.tipoPromocion ='"+Sesion.TIPO_PROMOCION_PRODUCTO_GRATIS+"' order by p.prioridad asc ";
		try{
			promocionesExt = Conexiones.realizarConsulta(query, true);
			promocionesExt.beforeFirst();
			if(promocionesExt.next()){
				promocion =  resultSet2PromocionExtProductoGratis(promocionesExt);
			}
		}catch(Exception ex){
			logger.error("getPromocionProductoGratis()", ex);
		}finally{
			if (promocionesExt!=null){
				try{
					promocionesExt.close();
				}catch(SQLException e){
					logger.error("getPromocionProductoGratis()",e);
				}
				promocionesExt=null;
			}
		}
		
		return promocion;
	}
	
	/**
	 * Carga la PromocionExt
	 * @param p
	 * @return PromocionExt La instancia de PromocionExt con solo los datos que interesan los diferentes
	 * tipos de combos
	 */
	private static PromocionExt resultSet2PromocionExtProductoGratis(ResultSet p) throws BaseDeDatosExcepcion {
		try{
		int codPromocion = p.getInt("codpromocion");
		Date fechainicio = p.getDate("fechainicio");
		Time horainicio = p.getTime("horainicio");
		Date fechafinaliza = p.getDate("fechafinaliza");
		Time horafinaliza = p.getTime("horafinaliza");
		int prioridad = p.getInt("prioridad");
		char tipopromocion = (p.getString("tipopromocion")).toCharArray()[0];
		
		double porcDescuento = p.getDouble("porcentajeDescuento");
		double montoMinimo = p.getDouble("montoMinimo");
		int cantidadMinima = p.getInt("cantMinima");
		String nombrePromocion = p.getString("nombrePromocion");
		
		PromocionExt pExt = new PromocionExt(codPromocion, fechainicio, horainicio, fechafinaliza,
				horafinaliza, prioridad, tipopromocion, porcDescuento, montoMinimo, cantidadMinima, nombrePromocion);				   
			return pExt;
		}catch(SQLException e){throw new BaseDeDatosExcepcion("ERROR CARGANDO promociones", e);}
	}
	
	/**
	 * Consulta en la base de datos las promociones existentes
	 * de combos por cantidades
	 * @param codProducto
	 * @param codsDepartamento
	 * @param marca
	 * @param linea
	 * @param seccion
	 * @param refProveedor
	 * @return Vector de PromocionExt
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
		Vector<PromocionExt> promociones = new Vector<PromocionExt>();
				
		ResultSet promocionesExt = null;
		String query =  " SELECT DISTINCT p.*, " +
						" dpe.porcentajeDescuento, " +
						" dpe.cantMinima, " +
						" dpe.cantRegalada, " +
						" dpe.nombrePromocion " +
			" FROM detallepromocionext dpe, promocion p " +
			" WHERE dpe.codPromocion=p.codPromocion " +
			" AND p.fechainicio<=current_date() and fechafinaliza>=current_date() "+ //esta vigente
			" AND dpe.estadoRegistro='"+Sesion.PROMOCION_ACTIVA+"'" + //Activa
			" AND ( p.tipopromocion='"+Sesion.TIPO_PROMOCION_DESCUENTO_EN_COMBO+"' ";
		
			if(codPromocion!=0){
				query+=" AND dpe.codPromocion="+codPromocion+" ";
				
			} else{
				if(codProducto!=null && !codProducto.equalsIgnoreCase(""))
					query+=" AND dpe.codProducto LIKE '%"+codProducto.substring(0, 9)+"%' "; //Contiene al producto indicado
				
				if(marca!=null && !marca.equalsIgnoreCase(""))
					query+=" AND dpe.marca='"+marca+"%' ";
				
				if(linea!=null && !linea.equalsIgnoreCase(""))
					query+=" AND dpe.linea='"+linea+"' ";
				
				if(refProveedor!=null  && refProveedor.equalsIgnoreCase(""))
					query+=" AND dpe.refProveedor='"+refProveedor+"' ";
				
				String in = "( ";
				for(int i=0;i<codsDepartamento.size();i++){
					String cod =  (String)codsDepartamento.elementAt(i);
					in+="'"+cod+"' ";
					if(i!=codsDepartamento.size()-1){
						in+=", ";
					}
				}
				in+=" ) ";
				if(codsDepartamento.size()!=0)
					query+=" AND dpe.departamento IN "+in; 
								
				//TODO seccion
			}
			
			query+=" ) "; //Es un combo
		
			if(codPromocion!=0){
				query+=" AND p.codpromocion="+codPromocion+" ";
			}
		try{
			promocionesExt = Conexiones.realizarConsulta(query, true);
			promocionesExt.beforeFirst();
			while(promocionesExt.next()){
				PromocionExt promo = resultSet2PromocionExtCombo(promocionesExt);
				asignarProductosCombo(promo,0);
				//Verifico si se cumple al condicion para aplicar promo
				Vector<String> condicionesNoPermitidas = new Vector<String>();
				condicionesNoPermitidas.addAll(Sesion.condicionesCombo);
				condicionesNoPermitidas.add(Sesion.condicionDesctoPrecioGarantizado);
				condicionesNoPermitidas.add(Sesion.condicionDesctoPorDefecto);
				condicionesNoPermitidas.add(Sesion.condicionCambioPrecio);
				if(codPromocion!=0 || Detector.verificaCantidadesMinimasCombo(promo,condicionesNoPermitidas, false)){
					promociones.addElement(promo);
				} 
			}
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("getPromocionesCombo(Producto p, int codPromocion)", ex);
		}finally{
			if (promocionesExt!=null){
				try{
					promocionesExt.close();
				}catch(SQLException e){
					logger.error("getPromocionesCombo(Producto p, int codPromocion)",e);
				}
				promocionesExt=null;
			}
		}
		return promociones;
	}
	
	/**
	 * Inicializa el manejador de reportes tomado de lo definido en las
	 * extensiones a utilizar
	 * @author jgraterol
	 * **/
	private static void iniciarManejadorReportes(){
		if (logger.isDebugEnabled()) {
			logger.debug("iniciarBuscador() - start");
		}

		ManejadorReportesPromocionesFactory  factory = new ManejadorReportesPromocionesFactory();
		PromocionExtBD.manejadorReportes = factory.getInstance();		
		if (logger.isDebugEnabled()) {
			logger.debug("iniciarBuscador() - end");
		}	
	}
	
	/**
	 * Obtiene el listado de cupones validos para la promocion p
	 * @param p
	 * @return Vector
	 * 			posicion 0: numtienda
	 * 			posicion 1:	fecha
	 * 			posicion 2: numcaja
	 * 			posicion 3: numtransaccion
	 * 			posicion 4: codcupon
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Vector<Object>> getCuponesValidos(PromocionExt p){
		Vector<Vector<Object>> validos = new Vector<Vector<Object>>();
		
		ResultSet cupones = null;
		String query = "select * " +
				"from "+Sesion.getDbEsquema()+".cuponesvalidos " +
				"where  codpromocion="+p.getCodPromocion(); //
		try{
			cupones = ConexionServCentral.realizarConsulta(query);
			cupones.beforeFirst();
			while(cupones.next()){
				Vector<Object> cupon = new Vector<Object>();
				cupon.addElement(new Integer(cupones.getInt("numtienda")));
				cupon.addElement(cupones.getDate("fecha"));
				cupon.addElement(new Integer(cupones.getInt("numcaja")));
				cupon.addElement(new Integer(cupones.getInt("numtransaccion")));
				cupon.addElement(cupones.getString("codcupon"));
				cupon.addElement(cupones.getString("invalidar"));
				validos.addElement(cupon);
			}
		}catch(Exception ex){
			logger.error("getPromocionCorporativaPorCodigo(int)", ex);
		}finally{
			if (cupones!=null){
				try{
					cupones.close();
				}catch(SQLException e){
					logger.error("getPromocionCorporativaPorCodigo(int)",e);
				}
				cupones=null;
			}
		}
		
		return validos;
	}
	
	/**
	 * Determina si hay condiciones para aplicar la promo de producto complementario
	 * @param venta Venta
	 * @param codPromocion Codigo de la promocion
	 * @return
	 */
	public static ResultSet hayComplementarios(Venta venta, int codPromocion) {
		ResultSet prodComplementario = null;
		String prod="(";
		Iterator<DetalleTransaccion> i = (venta.getDetallesTransaccion()).iterator();
		while(i.hasNext())
			prod += "'"+(i.next()).getProducto().getCodProducto()+"', ";
		prod += "'0')";
		String sql = "select * from detallepromocionext where codPromocion = "+codPromocion+"  and codProducto in "+prod;
		try{
			prodComplementario=Conexiones.realizarConsulta(sql, true);
		}catch(Exception ex){System.out.println("Producto Complementario");}
		return prodComplementario;
	}
	
	//BECO: WDIAZ 07-2012
	// Se Crea una Tabla Simulando una Vista para que tenga todas las promociones que imprimen un ticket y se calcula al final de la Venta
	public static void crearTablaPromoTicket()
	 {
		String borrarTabla = "DROP Table IF EXISTS "+Sesion.getDbEsquema()+".promocionticket";
		String crearTabla = "CREATE TABLE "+Sesion.getDbEsquema()+".promocionticket " +
				            "(INDEX dep_lin_ref_prod  (departamento,linea,refproveedor,codproducto)) " +
				            "ENGINE=InnoDB " +
				            	"select a.*, b.categoria, b.departamento, b.linea, b.refproveedor, b.codproducto, b.numDetalle, b.montoMinimo, b.bsDescuento, b.nombrePromocion, b.acumulaPremio " +
				            	"from promocion a, detallepromocionext b " +
				            	"where a.codpromocion = b.codPromocion " +
				            	"and b.estadoRegistro = '" + Sesion.PROMOCION_ACTIVA + "' and "
				            	+ "((a.fechainicio < '" + fechaActualString + "' and a.fechafinaliza > '" + fechaActualString + "') or "
								+ "(a.fechainicio = '" + fechaActualString + "' and a.fechafinaliza <> '" + fechaActualString + "' and a.horainicio <= '" + horaActual + "') or "
								+ "(a.fechainicio <> '" + fechaActualString + "' and a.fechafinaliza = '" + fechaActualString + "' and a.horafinaliza >= '" + horaActual + "') or "
								+ "(a.fechainicio = '" + fechaActualString + "' and a.fechafinaliza = '" + fechaActualString + "' and ('" + horaActual + "' between a.horainicio and a.horafinaliza))) and "
				            	+ "(a.tipopromocion = '"+Sesion.TIPO_PROMOCION_REGALO_BONO_REGALO+"' " +
				            	  "or a.tipopromocion = '"+Sesion.TIPO_PROMOCION_REGALO_CALCOMANIA+"' " +
				            	  "or a.tipopromocion = '"+Sesion.TIPO_PROMOCION_REGALO_CUPON+"' " +
				            	  "or a.tipopromocion = '"+Sesion.TIPO_PROMOCION_REGALO_PRODUCTO+"')" +
				            	  "GROUP BY a.codpromocion, a.tipopromocion, a.horainicio, a.horafinaliza, " +
				            	  "a.fechainicio, a.fechafinaliza, a.prioridad, b.categoria, b.departamento, b.linea, " +
				            	  "b.refproveedor, b.codproducto, b.montoMinimo, b.bsDescuento, b.nombrePromocion, " +
				            	  "b.acumulaPremio HAVING COUNT(*)>=1 ";
		
		//order by codpromocion desc limit 50000
		
		Statement loteSentenciasCR = null;
		try {
			loteSentenciasCR = Conexiones.crearSentencia(true);
			loteSentenciasCR.addBatch(borrarTabla);
			loteSentenciasCR.addBatch(crearTabla);
			Conexiones.ejecutarLoteSentencias(loteSentenciasCR, true, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("getPromocionCorporativaPorCodigo(int)",e);
		}
	 }

	//BECO: JPEREZ 09-2012
	// Se actualiza la tabla promoticket para una promocion especifica
	public static void actualizarTablaPromoTicket(int codPromocion)
	 {
		String borrarPromo = "DELETE FROM "+Sesion.getDbEsquema()+".promocionticket WHERE codpromocion="+codPromocion;
		String actualizarTabla = "INSERT INTO "+Sesion.getDbEsquema()+".promocionticket " +
				            "(codpromocion,tipopromocion,fechainicio,horainicio,fechafinaliza," +
				            "horafinaliza, prioridad, categoria, departamento,linea,refproveedor,codproducto," +
				            "numDetalle, montoMinimo, bsDescuento, nombrePromocion,acumulaPremio) " +
				            	"select a.*, b.categoria, b.departamento, b.linea, b.refproveedor, b.codproducto, b.numDetalle, b.montoMinimo, b.bsDescuento, b.nombrePromocion, b.acumulaPremio " +
				            	"from promocion a, detallepromocionext b " +
				            	"where a.codpromocion = b.codPromocion  and a.codpromocion="+codPromocion +
				            	" and b.estadoRegistro = '" + Sesion.PROMOCION_ACTIVA + "' and "
				            	+ "((a.fechainicio < '" + fechaActualString + "' and a.fechafinaliza > '" + fechaActualString + "') or "
								+ "(a.fechainicio = '" + fechaActualString + "' and a.fechafinaliza <> '" + fechaActualString + "' and a.horainicio <= '" + horaActual + "') or "
								+ "(a.fechainicio <> '" + fechaActualString + "' and a.fechafinaliza = '" + fechaActualString + "' and a.horafinaliza >= '" + horaActual + "') or "
								+ "(a.fechainicio = '" + fechaActualString + "' and a.fechafinaliza = '" + fechaActualString + "' and ('" + horaActual + "' between a.horainicio and a.horafinaliza))) and "
				            	+ "(a.tipopromocion = '"+Sesion.TIPO_PROMOCION_REGALO_BONO_REGALO+"' " +
				            	  "or a.tipopromocion = '"+Sesion.TIPO_PROMOCION_REGALO_CALCOMANIA+"' " +
				            	  "or a.tipopromocion = '"+Sesion.TIPO_PROMOCION_REGALO_CUPON+"' " +
				            	  "or a.tipopromocion = '"+Sesion.TIPO_PROMOCION_REGALO_PRODUCTO+"')" +
				            	  "GROUP BY a.codpromocion, a.tipopromocion, a.horainicio, a.horafinaliza, " +
				            	  "a.fechainicio, a.fechafinaliza, a.prioridad, b.categoria, b.departamento, b.linea, " +
				            	  "b.refproveedor, b.codproducto, b.montoMinimo, b.bsDescuento, b.nombrePromocion, " +
				            	  "b.acumulaPremio HAVING COUNT(*)>=1 ";
		
		//order by codpromocion desc limit 50000
		
		Statement loteSentenciasCR = null;
		try {
			loteSentenciasCR = Conexiones.crearSentencia(true);
			loteSentenciasCR.addBatch(borrarPromo);
			loteSentenciasCR.addBatch(actualizarTabla);
			Conexiones.ejecutarLoteSentencias(loteSentenciasCR, true, true);
		} catch (Exception e) {
			//e.printStackTrace();
			//logger.error("getPromocionCorporativaPorCodigo(int)",e);
		}
	 }
	// devuelve un Arreglo/Hash con la promocion Monto y Cantidad de las promociones de Tipo Ticket
	private static Hashtable<String, PromocionExt> montoPromocion()
	{
		Hashtable<String,PromocionExt> promoMonto = new Hashtable<String,PromocionExt>();
		PromocionExt promociones = null;
		crearTablaTempProd();
		String prodEnPromo = "select *, sum(montoTotal) as sumMontoTotal , sum(cantidad) as sumCantidad " +
							 "from productoVenta a, promocionticket b " +
							 "where " +
							 	"a.coddepartamento = b.departamento and b.linea is null and b.refProveedor is null and b.codProducto is null " +
							 "or " +
							 	"a.coddepartamento = b.departamento and a.codlineaseccion = b.linea and b.refProveedor is null and b.codProducto is null " +
							 "or " +
							 	"a.coddepartamento = b.departamento and a.codlineaseccion = b.linea and a.referenciaproveedor = b.refProveedor  and b.codProducto is null " +
							 "or " +
							 	"a.coddepartamento = b.departamento and a.codlineaseccion = b.linea and a.referenciaproveedor = b.refProveedor and a.codproducto = b.codProducto " +
							 "group by b.codpromocion  " +
							 "UNION " +
							 "SELECT NULL, NULL, NULL, NULL, 0, 0, b.*, 0 AS sumMontoTotal , 0 AS sumCantidad  FROM promocionticket b WHERE  b.tipopromocion IN ('B', 'C') ";
		try {
			ResultSet prodEnPromocion = Conexiones.realizarConsulta(prodEnPromo, true);
			prodEnPromocion.beforeFirst();
			while (prodEnPromocion.next())
			{
				promociones = resultSet2PromocionExtRegalo(prodEnPromocion);
				
				if((promociones.getTipoPromocion()==Sesion.TIPO_PROMOCION_REGALO_CUPON) ||
						(promociones.getTipoPromocion()==Sesion.TIPO_PROMOCION_REGALO_PRODUCTO)){
					//Cargando las lineas asociadas a las condiciones del sorteo
					Vector<String> lineasCondiciones = PromocionExtBD.cargarCondicionesPromocion(promociones.getCodPromocion());
					promociones.setLineasCondiciones(lineasCondiciones);
				} else
					promociones.setLineasCondiciones(new Vector<String>());
				
				actualizarSumaMontoTotal(promociones);
				promoMonto.put(prodEnPromocion.getString("codpromocion"), promociones);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			eliminarTabTemp();
		}
		return promoMonto;
		
	}
	
	// Crea la tabla temporal de los productos de la Venta
	private static void crearTablaTempProd()
	{
		String insertarProductos = null;
		Detalle detalleTransServ = null;
		double montoTotal;
		Vector<?> detalleVenta = null;
		String crearTabla = "CREATE TEMPORARY TABLE "+Sesion.getDbEsquema()+".productoVenta (coddepartamento char(2) default NULL, " +
					                                               "codlineaseccion char(2) default NULL, " +
					                                               "referenciaproveedor varchar(13) default NULL, " +
					                                               "codproducto varchar(12) default NULL, " +
					                                               "montoTotal decimal(16,5) NOT NULL default '0.00', " +
					                                               "cantidad int(11) NOT NULL default '0', " +
					                                               "PRIMARY KEY  (codproducto)) " +
					                                               "ENGINE=MEMORY COMMENT='Productos de la Venta' ";
		
		String crearTablaAux = "CREATE TEMPORARY TABLE "+Sesion.getDbEsquema()+".productoVentaAux ( " +
															        "codproducto varchar(12) default NULL, " +
															        "montoTotal decimal(16,5) NOT NULL default '0.00', " +
															        "cantidad int(11) NOT NULL default '0', " +
															        "PRIMARY KEY  (codproducto)) " +
															        "ENGINE=MEMORY COMMENT='Productos de la Venta' ";
		
	    String actualizarTabla = "REPLACE INTO "+Sesion.getDbEsquema()+".productoVenta (coddepartamento,codlineaseccion,referenciaproveedor,codproducto,montoTotal, " +
	    		                 "cantidad)(select b.coddepartamento, b.codlineaseccion, b.referenciaproveedor, b.codproducto, a.montoTotal, " +
	    		                 "a.cantidad from productoVentaAux a, producto b where a.codProducto = b.codproducto)";
		
		Statement loteSentenciasCR = null;
			try {
				loteSentenciasCR = Conexiones.crearSentencia(true);
				loteSentenciasCR.addBatch(crearTabla);
				loteSentenciasCR.addBatch(crearTablaAux);
				// Buscamos los detalles de la Venta activa
				if(CR.meVenta.getVenta()!=null)
					 detalleVenta = CR.meVenta.getVenta().getDetallesTransaccion();
				else if (CR.meServ.getApartado()!=null)
					 detalleVenta = CR.meServ.getApartado().getDetallesServicio();
				
					for(int i=0; i<detalleVenta.size(); i++)
					{
						if(CR.meVenta.getVenta()!=null)
							detalleTransServ = (DetalleTransaccion) detalleVenta.get(i);
						else if (CR.meServ.getApartado()!=null)
							detalleTransServ =  (DetalleServicio) detalleVenta.get(i);
						
						montoTotal = (detalleTransServ.getPrecioFinal()+detalleTransServ.getMontoImpuesto())*detalleTransServ.getCantidad();
						insertarProductos = "REPLACE INTO "+Sesion.getDbEsquema()+".productoVentaAux " +
								                   "(codproducto, montoTotal,cantidad) " +
								            "VALUES ('"+detalleTransServ.getProducto().getCodProducto()+"', "+montoTotal+", "+detalleTransServ.getCantidad()+")";
						loteSentenciasCR.addBatch(insertarProductos);
					}
					loteSentenciasCR.addBatch(actualizarTabla);
					Conexiones.ejecutarLoteSentencias(loteSentenciasCR, true, true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("Error Creando Tabla Temporal",e);
			}
	}
	
	public static void eliminarTabTemp()
	{
		String borrarTabla = "DROP Table IF EXISTS "+Sesion.getDbEsquema()+".productoVenta";
		String borrarTablaAux = "DROP Table IF EXISTS "+Sesion.getDbEsquema()+".productoVentaAux";
		Statement loteSentenciasCR = null;
		try {
			loteSentenciasCR = Conexiones.crearSentencia(true);
			loteSentenciasCR.addBatch(borrarTabla);
			loteSentenciasCR.addBatch(borrarTablaAux);
			Conexiones.ejecutarLoteSentencias(loteSentenciasCR, true, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error Eliminando Tablas Temporales",e);
		}
	}
	
	private static void actualizarSumaMontoTotal(PromocionExt promociones)
	{
		double monto = 0;
		if((promociones.getTipoPromocion()==Sesion.TIPO_PROMOCION_REGALO_CUPON) ||
				(promociones.getTipoPromocion()==Sesion.TIPO_PROMOCION_CALCOMANIA_BS)){

			if(CR.meVenta.getVenta()!=null)
				monto = CR.meVenta.getVenta().consultarMontoTrans();
			else if (CR.meServ.getApartado()!=null)
				monto = CR.meServ.getApartado().consultarMontoServ();
			promociones.setSumMontoTotal(monto);
		
		}
	}
	
}
