package com.becoblohm.cr.extensiones.impl;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.beco.cr.actualizadorPrecios.promociones.PromocionExt;
import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.extensiones.ActualizadorPreciosSAC;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarservicio.Apartado;
import com.becoblohm.cr.manejarservicio.DetalleServicio;
import com.becoblohm.cr.manejarservicio.Servicio;
import com.becoblohm.cr.manejarventa.DetalleTransaccion;
import com.becoblohm.cr.manejarventa.Devolucion;
import com.becoblohm.cr.manejarventa.Producto;
import com.becoblohm.cr.manejarventa.Promocion;
import com.becoblohm.cr.manejarventa.Venta;
import com.becoblohm.cr.utiles.MathUtil;

public class DefaultActualizadorPreciosSAC  implements ActualizadorPreciosSAC {

	private static final Logger logger = Logger.getLogger(DefaultActualizadorPreciosSAC.class);
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void actualizarPreciosExt(Producto p, Servicio serv, boolean aplicarPromociones) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		float AcumN = 0;
		Vector<Integer> detallesN = new Vector<Integer>(); // Vector de posiciones para los productos en condiciones normales
		Vector<Vector<Integer>> nuevosDetalles;
		// Se acumulan los detalles que no sean rebajas y se colocan en Normal
		for (int i=0; i<serv.getDetallesServicio().size(); i++) {
			DetalleServicio detalleActual = (DetalleServicio)serv.getDetallesServicio().elementAt(i);
			if (detalleActual.getProducto().getCodProducto().equals(p.getCodProducto())) {
				if (!detalleActual.isCondicionEspecial()) {/*!detalleActual.getCondicionVenta().equalsIgnoreCase(Sesion.condicionDesctoPorDefecto)*/
					AcumN += detalleActual.getCantidad();
					detallesN.addElement(new Integer(i));
				}
			}
		}		

		if(aplicarPromociones){
			// Se evalúan y aplican las condiciones de venta a los detalles afectados.
			if (detallesN.size() > 0){
				nuevosDetalles = aplicarCondicionDeVenta(p, detallesN, AcumN, serv);
				Vector<Integer> nuevosDetallesN = ( nuevosDetalles.elementAt(0));
				Vector<Integer> nuevosEmpaques = ( nuevosDetalles.elementAt(1));
				
				// Se acumulan los detalles que se encuentran en condición de empaque
				serv.acumularDetalles(nuevosEmpaques);
				
				// Se acumulan los detalles que se encuentran en la condición distinta a la de empaque y se colocan al final del vector original
				serv.acumularDetalles(nuevosDetallesN);
				
				// Se limpia el detalle quitando los elementos del vector de detallesN original
				for (int i = 0; i<detallesN.size(); i++) {
					serv.getDetallesServicio().set(((Integer)detallesN.elementAt(i)).intValue(),null);
				}
			
				// Se eliminan los elementos nulos que quedan en el vector de detalles original
				while (serv.getDetallesServicio().removeElement(null));
				
				// Limpiamos los vectores de posiciones
				detallesN.clear();
				nuevosDetallesN.clear();
				nuevosEmpaques.clear();
			}
		}else{
			serv.acumularDetalles(detallesN);
			// Se eliminan los elementos nulos que quedan en el vector de detalles original
			while (serv.getDetallesServicio().removeElement(null));
		}
	}
	
	
	/**
	 * Chequea los descuentos aplicables al producto especificado, verifica prioridades de los mismos y aplica el descuento.
	 * @param prod Producto al que se le aplicara el descuento.
	 * @param detallesN Vector de posiciones del producto .
	 * @param cantidad Cantidad de productos presentes en el detalle.
	 * @return Vector - Vector donde la posición 0 son las posiciones de los detalles cuya condición es distinta a la de empaque y en la posición 1 se encuentran los que tienen condición empaque
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Vector<Integer>> aplicarCondicionDeVenta(Producto prod, Vector<Integer> detallesN, float cantidad, Servicio serv) {
		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarCondicionDeVenta(Producto, Vector, float) - start");
		}
		
		// Variables utilizables para los calculos
		int puntoDeChequeo = 0;
		//float cantidadDetalleActual;
		
		// Variable de retorno
		Vector<Vector<Integer>> retorno = new Vector<Vector<Integer>>();
		Vector<Integer> nuevosEmpaques = new Vector<Integer>();
		Vector<Integer> nuevosNoEmpaques = new Vector<Integer>();
		
		// Buscamos las condiciones de venta que aplican al producto
		Vector<Vector<Object>> descuentos = buscarCondicionesDeVenta(prod);
		
		// Verificamos si aplican condiciones de venta al producto, de no ser así se colocan como normales en los detalles
		if ((descuentos.size() > 0) && (cantidad > 0)) {
			// Obtenemos el mejor descuento para el producto
			int posicionMejor = 0;
			Vector<Object> dctoActual = new Vector<Object>();
			double precioFinal = Double.MAX_VALUE;
			for (int i=0; i<descuentos.size(); i++) {
				dctoActual = descuentos.elementAt(i);
				if (precioFinal > ((Double)dctoActual.elementAt(0)).doubleValue()) {
					posicionMejor = i;
					precioFinal = ((Double)dctoActual.elementAt(0)).doubleValue();
				}
			}
				
			// Si se trata de empaque, aplicamos el empaque unicamente a la cantidad de productos exacta
			// el resto seguira con el algoritmo de calculo NoEmpaque
			if (((String)(descuentos.elementAt(posicionMejor)).elementAt(1)).equals(Sesion.condicionEmpaque)) {
				
				// Separamos en empaques y no empaques y aplicamos el descuento a los empaques
				float prodsEmpaque = cantidad - (cantidad % prod.getCantidadVentaEmpaque());	
				cantidad -= prodsEmpaque;
							
				while (prodsEmpaque > 0) {
					// Si existen empaques, creo el nuevo detalle con el empaque por cada detallesN
					DetalleServicio det = ((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue()));
					String tipCaptura = det.getTipoCaptura();
					float cantidadDetalleActual = det.getCantidad();
					float cantidadAsignar;
					double pFinal = ((Double)dctoActual.elementAt(0)).doubleValue();
					
					if (prodsEmpaque >= cantidadDetalleActual) {
						cantidadAsignar = cantidadDetalleActual;
						puntoDeChequeo++;
					} else {
						cantidadAsignar = prodsEmpaque;
						det.setCantidad(cantidadDetalleActual - cantidadAsignar);
					}
					prodsEmpaque -= cantidadAsignar;
					pFinal = MathUtil.roundDouble(pFinal);
					DetalleServicio nuevoDetalle = new DetalleServicio(prod, cantidadAsignar,
								(String)dctoActual.elementAt(1), null, pFinal, serv.calcularImpuesto(prod, pFinal),
								tipCaptura, -1, prod.getTipoEntrega());
					/* Lista de Regalos - Inicio */
					nuevoDetalle.setAbonos(det.getAbonos());
					nuevoDetalle.setCantVendidos(det.getCantVendidos());
					/* Lista de Regalos - Final */
					serv.getDetallesServicio().addElement(nuevoDetalle);
					nuevosEmpaques.addElement(new Integer(serv.getDetallesServicio().size() - 1));
				}
					
				// Elimino la condicion de venta empaque del vector de condiciones de venta
				descuentos.remove(posicionMejor);
					
				// Busco el mejor de los descuentos que restan
				posicionMejor = 0;
				precioFinal = Double.MAX_VALUE;
				for (int i=0; i<descuentos.size(); i++) {
					dctoActual = descuentos.elementAt(i);
					if (precioFinal > ((Double)dctoActual.elementAt(0)).doubleValue()) {
						posicionMejor = i;
						precioFinal = ((Double)dctoActual.elementAt(0)).doubleValue();
					}
				}
			}
				
			// Si quedan productos y descuentos para ser aplicados
			if ((cantidad > 0) && (descuentos.size() > 0)) {
				while (puntoDeChequeo < detallesN.size()) {
					// Seguimos con los descuentos y aplicamos el mejor.
					DetalleServicio det = ((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue()));
					String tipCaptura = det.getTipoCaptura();
					dctoActual = descuentos.elementAt(posicionMejor);
					double pFinal = ((Double)dctoActual.elementAt(0)).doubleValue();
					float cantidadAsignar = det.getCantidad();
					DetalleServicio nuevoDetalleServ;
					if (((String)(descuentos.elementAt(posicionMejor)).elementAt(1)).equals(Sesion.condicionPromocion)) {
						// Si se trata de una promocion
						pFinal = MathUtil.roundDouble(pFinal);
						nuevoDetalleServ = new DetalleServicio(prod, cantidadAsignar,
											(String)dctoActual.elementAt(1), null, pFinal, 
											serv.calcularImpuesto(prod, pFinal), tipCaptura,
											((Integer)dctoActual.elementAt(2)).intValue(), prod.getTipoEntrega());
						/* Lista de Regalos - Inicio */
						nuevoDetalleServ.setAbonos(det.getAbonos());
						nuevoDetalleServ.setCantVendidos(det.getCantVendidos());
						/* Lista de Regalos - Final */
						serv.getDetallesServicio().addElement(nuevoDetalleServ);
						nuevosNoEmpaques.addElement(new Integer(serv.getDetallesServicio().size() - 1));
						cantidad -= cantidadAsignar;
						puntoDeChequeo++;
					}
				}
			}
		}
		
		// Colocamos las condiciones de venta normales (Si existen)
		if (cantidad > 0) {
			while (puntoDeChequeo < detallesN.size()) {
				DetalleServicio det = ((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue()));
				String tipCaptura = det.getTipoCaptura();
				double pFinal = prod.getPrecioRegular();
				pFinal = MathUtil.roundDouble(pFinal);
				float cantidadAsignar = det.getCantidad();
				DetalleServicio nuevoDetalleServ = new DetalleServicio(
								prod,
								cantidadAsignar,
								Sesion.condicionNormal,
								null,
								pFinal,
								serv.calcularImpuesto(prod,pFinal),
								tipCaptura,
								-1, det.getTipoEntrega());
				/* Lista de Regalos - Inicio */
				nuevoDetalleServ.setAbonos(det.getAbonos());
				nuevoDetalleServ.setCantVendidos(det.getCantVendidos());
				/* Lista de Regalos - Final */
				serv.getDetallesServicio().addElement(nuevoDetalleServ);
				nuevosNoEmpaques.addElement(new Integer(serv.getDetallesServicio().size() - 1));
				puntoDeChequeo++;
			}
		}

		// Armamos el vector de retorno
		retorno.addElement(nuevosNoEmpaques);
		retorno.addElement(nuevosEmpaques);

		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarCondicionDeVenta(Producto, Vector, float) - end");
		}
		return retorno;
	}
	
	/**
	 * Construye la matriz de descuentos aplicables al producto especificado, con sus condiciones de venta respectivas.
	 * @param prod Producto al que se le aplicará el descuento.
	 * @return Vector Vector de dos o tres posiciones que indica: 
	 * 				   - posición 0 posee la información del descuento promocional.
	 * 				   - posición 1 posee la información del descuento a empaque
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private Vector<Vector<Object>> buscarCondicionesDeVenta(Producto prod){
		if (logger.isDebugEnabled()) {
			logger.debug("buscarCondicionesDeVenta(Producto) - start");
		}

		double dctoEmpaque;
		Vector<Vector<Object>> descuentos = new Vector<Vector<Object>>();
		
		// Calculamos las promociones
		Vector<Object> datosPromocion = verificarPromociones(prod);
		if (datosPromocion.size() > 0) {
			// Existen promociones vigentes para el producto
			Vector<Object> promo = new Vector<Object>(2);
			promo.addElement((Double)datosPromocion.elementAt(1));
			promo.addElement(Sesion.condicionPromocion);
			promo.addElement((Integer)datosPromocion.elementAt(0));
			descuentos.addElement(promo);
		}
		
		// Calculamos el precio de condicion empaque
		dctoEmpaque = prod.getPrecioRegular() * (prod.getDesctoVentaEmpaque()/100);
		if (dctoEmpaque > 0) {
			// Agregamos el empaque al vector de condiciones de venta
			Vector<Object> empaque = new Vector<Object>(2);
			empaque.addElement(new Double(prod.getPrecioRegular() - dctoEmpaque));
			empaque.addElement(Sesion.condicionEmpaque);
			descuentos.addElement(empaque);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("buscarCondicionesDeVenta(Producto) - end");
		}
		return descuentos;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Object> verificarPromociones(Producto prod){
		Promocion promocionAplicada = null;
		
		// Obtenemos las promociones del producto en el detalle de transaccion
		Vector<Promocion> promociones = new Vector<Promocion>();
		promociones = prod.getPromociones();
		
		// Buscamos la promocion con prioridad mas alta
		for (int i=0; i<promociones.size(); i++) {
			if (promocionAplicada == null) {
				promocionAplicada = (Promocion)promociones.elementAt(i);
			} else {
				if(((Promocion)promociones.elementAt(i)).getPrioridad() < promocionAplicada.getPrioridad()) {
					promocionAplicada = (Promocion)promociones.elementAt(i);
				}
			}
		}
		
		// Aplicamos la promocion (si existe) al producto
		Vector<Object> result = new Vector<Object>(2);
		if (promocionAplicada != null) {
			result.addElement(new Integer(promocionAplicada.getCodPromocion()));
			if (promocionAplicada.getPorcentajeDescuento()!= 0) { // Promocion de Descuento
				double mtoDcto = prod.getPrecioRegular() * (promocionAplicada.getPorcentajeDescuento() / 100);
				result.addElement(new Double(prod.getPrecioRegular() - mtoDcto));
			} else {
				if (promocionAplicada.getPrecioFinal() != 0) { // Promocion de Precio a Precio
					result.addElement(new Double(promocionAplicada.getPrecioFinal()));
				}
			}
		}
		return result;
	}
	
    /**
     * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPreciosSAC#actualizarPrecios(Producto, Venta)
     */
    public void actualizarPrecios(Producto p, Servicio serv, boolean aplicarPromociones){
    	try{
    		this.actualizarPreciosExt(p, serv, aplicarPromociones);
    	}catch (Exception e){
    		logger.debug("actualizarPrecios(Producto p, Servicio serv, boolean aplicarPromociones)", e);
    	}
    }
    
    /**
	 * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPreciosSAC#deshacerProductoGratis()
	 */
	public void deshacerProductoGratis(DetalleServicio detalleDeshacer){
		//NADA
	}
	
	/**
     * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPreciosSAC#cargarPatrocinantes()
     */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'HashMap'
	* Fecha: agosto 2011
	*/
    public TreeMap<PromocionExt,Vector<Producto>> cargarPatrocinantes(){
    	return new TreeMap<PromocionExt,Vector<Producto>>();
    }
    
    public void ejecutarAhorroEnCompra(){
    	//NADA
    }
    
    public void ejecutarProductoGratis(){
    	//NADA
    }
    
    /**
     * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPreciosSAC#recalcularPromociones()
     */
    public boolean recalculadoPromociones(){
    	return CR.meServ.getApartado().recalculadoPromocionesApartado();
    }
    
    /**
     * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPreciosSAC#agregarInsertServicioCondiciones()
     */
    public void agregarInsertServicioCondiciones(Apartado apartado, Statement loteSentenciasCR, String fechaServString, String sentenciaSQL, int i, boolean isEnEspera,  int numServicio) throws SQLException{
    	//nada
    }
    
    /**
     * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPreciosSAC#agregarInsertServicioCondiciones()
     */
    public void agregarDeleteServicioCondiciones(Apartado ap, Statement loteSentenciasCR, boolean isEnEspera, int numServicio) throws SQLException {
    	//nada
    }

    /**
     * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPreciosSAC#botonServicioHabilitado(int numBoton, boolean enUtilitarios)
     */
	public boolean botonServicioHabilitado(int numBoton, boolean enUtilitarios) {
		if(!enUtilitarios)
			return false;
		if(CR.meVenta.getVenta()!=null)
			return true;
		return false;
	}


	public void ventanaPromociones(Apartado apartado) {
		// TODO Apéndice de método generado automáticamente
		
	}
	
	/**
	 * No suma ninguna donacion al subtotal de la factura
	 * @param subTotal Subtotal de la transaccion
	 * @return double El monto del subtotal sin sumar donaciones
	 * **/
	public double sumarDonaciones(Apartado apartado, double subTotal, boolean solicitarDonaciones){
		return subTotal;
	}
	/*
	public HashMap cargarProductoComplementario(){
    	return new HashMap();
    }
	*/
	/**
	 * llamada a la promocion producto complementario
	 */
    public void ejecutarProductoComplementario(int p){
    
    }


	public void llamadaDeRegalos(int n) {
		// TODO Apéndice de método generado automáticamente
		
	}


	public void acumularDetallesDevolucion(Devolucion dev) {
		for (int i=dev.getDetallesTransaccion().size()-1;i>=0;i--) {
			if (dev.getDetallesTransaccion().elementAt(i) != null) {
				for (int j=i-1; j>=0; j--) {
					DetalleTransaccion detalle1 = (DetalleTransaccion)dev.getDetallesTransaccion().elementAt(i);
					DetalleTransaccion detalle2 = (DetalleTransaccion)dev.getDetallesTransaccion().elementAt(j);
					if ((detalle2!=null)
					  &&(detalle1.getProducto().getCodProducto().equalsIgnoreCase(detalle2.getProducto().getCodProducto())) 
					  &&(detalle1.getCondicionVenta().equalsIgnoreCase(detalle2.getCondicionVenta()))
					  &&(detalle1.getPrecioFinal() == detalle2.getPrecioFinal())) {
						detalle1.setCantidad(detalle1.getCantidad()+detalle2.getCantidad());
						dev.getDetallesTransaccion().set(j, null);
					}
				}
			}
		}
		while (dev.getDetallesTransaccion().removeElement(null));
		
	}
}
