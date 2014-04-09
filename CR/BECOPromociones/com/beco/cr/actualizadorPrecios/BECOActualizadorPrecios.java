/**
 * ===========================================================================
 * Material Propiedad CentroBeco C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.beco.cr.actualizadorPrecios
 * Programa		: BECOActualizadorPrecios.java
 * Creado por	: Jesus Graterol
 * Creado en 	: 05-jun-2008 8:30:00
 * (C) Copyright 2005 CentroBeco C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * =============================================================================
 */
package com.beco.cr.actualizadorPrecios;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.beco.cr.actualizadorPrecios.promociones.ComparadorPromociones;
import com.beco.cr.actualizadorPrecios.promociones.Detector;
import com.beco.cr.actualizadorPrecios.promociones.Donacion;
import com.beco.cr.actualizadorPrecios.promociones.DonacionRegistrada;
import com.beco.cr.actualizadorPrecios.promociones.PagoDonacion;
import com.beco.cr.actualizadorPrecios.promociones.ProductoCombo;
import com.beco.cr.actualizadorPrecios.promociones.PromocionExt;
import com.beco.cr.actualizadorPrecios.promociones.PromocionRegistrada;
import com.beco.cr.actualizadorPrecios.promociones.RegaloRegistrado;
import com.beco.cr.actualizadorPrecios.promociones.ManejadorBD.BeansSincronizador;
import com.beco.cr.actualizadorPrecios.promociones.ManejadorBD.DonacionBD;
import com.beco.cr.actualizadorPrecios.promociones.ManejadorBD.PromocionExtBD;
import com.beco.cr.actualizadorPrecios.promociones.ManejadorBD.UtilesBD;
import com.beco.cr.actualizadorPrecios.promociones.extensiones.ManejadorReportesPromocionesFactory;
import com.beco.cr.actualizadorPrecios.promociones.interfaz.MenuPromociones;
import com.beco.cr.actualizadorPrecios.promociones.interfaz.PantallaCondicionesVenta;
import com.beco.cr.actualizadorPrecios.promociones.interfaz.PantallaDetallesCambiados;
import com.beco.cr.actualizadorPrecios.promociones.interfaz.VentanaDonacion;
import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.extensiones.ActualizadorPrecios;
import com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.manejadorauditoria.Auditoria;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarservicio.Apartado;
import com.becoblohm.cr.manejarventa.Anulacion;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.manejarventa.CondicionVenta;
import com.becoblohm.cr.manejarventa.Detalle;
import com.becoblohm.cr.manejarventa.DetalleTransaccion;
import com.becoblohm.cr.manejarventa.Producto;
import com.becoblohm.cr.manejarventa.Promocion;
import com.becoblohm.cr.manejarventa.Venta;
import com.becoblohm.cr.mediadoresbd.BaseDeDatosVenta;
import com.becoblohm.cr.utiles.MathUtil;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.becoblohm.cr.utiles.VectorUtil;

/**
 * <pre>
 * Proyecto: CR 
 * Clase: BECOActualizadorPrecios
 * </pre>
 * <p>
 * <a href="BECOActualizadorPrecios.java.html"><i>View Source</i></a>
 * </p>
 * 
 */
public class BECOActualizadorPrecios implements ActualizadorPrecios {

	private static final Logger logger = Logger.getLogger(BECOActualizadorPrecios.class);
	
	/**Boton de menu utilitarios para donaciones**/
	protected JHighlightButton jButton = new JHighlightButton();
	public static boolean posicionesCambiadas = false;
	
	/**
	 * Actualiza los precios de la venta indicada para un producto
	 * @param p Producto cuyos precios se van a actualizar
	 * @param venta Venta actual sobre la que se realiza la actualizacion
	 * @throws ConexionExcepcion 
	 * @throws BaseDeDatosExcepcion 
	 * @throws FuncionExcepcion 
	 * @throws XmlExcepcion 
	 * */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void actualizarPreciosExt(Producto p, Venta venta) 
		throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion{
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarPrecios(Producto) - start");
		}
		float AcumN = 0;
		float cantidadProdsConDescto = 0;
		Vector<Integer> detallesN = new Vector<Integer>(); // Vector de posiciones para los productos en condiciones normales
		Vector<Integer> detallesE = new Vector<Integer>(); // Vector de posiciones para los productos en condición de empaque
		
		Vector<String> condicionesEmpaque = new Vector<String>();
		condicionesEmpaque.add(Sesion.condicionEmpaque);
		Vector<String> condicionesProductoGratis = new Vector<String>();
		condicionesProductoGratis.addElement(Sesion.condicionProductoGratis);
		
		Vector<Vector<Integer>> nuevosDetalles;
		Vector<DetalleTransaccion> detallesCreados = new Vector<DetalleTransaccion>();
		//Detalles sobre los cuales es necesario ejecutar el algoritmo de nuevo
		Vector<Detalle> detallesAActualizar = new Vector<Detalle>();
		//Vector de detalles que quedaron con cero items
		venta.setDetallesAEliminar(new Vector());
		
		boolean cantidadCambiada = false;
		
		Vector<String> condicionCorporativo = new Vector<String>();
		condicionCorporativo.addElement(Sesion.condicionDescuentoCorporativo);
		
		// Se acumulan los detalles que tiene condición 'N' y 'E' en sus respectivas variables
		for (int i=0; i<venta.getDetallesTransaccion().size();i++) {
			if (((((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)).getProducto()).getCodProducto()).equals(p.getCodProducto())) {
				
				//Si estoy eliminando un producto debo verificar que se siga cumpliendo la promocion de producto gratis
				if( venta.getPromocionProductoGratis()!=null && 
						((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)).getVariacion()<0){
					
					if(((PromocionExt)venta.getPromocionProductoGratis()).getMontoMinimo()>venta.consultarMontoTrans() ||
							Detector.contarCantidadProductos(venta.getDetallesTransaccion())<((PromocionExt)venta.getPromocionProductoGratis()).getCantMinima()){
						for(int j=0;j<venta.getDetallesTransaccion().size();j++){
							DetalleTransaccion dt = (DetalleTransaccion)venta.getDetallesTransaccion().elementAt(j);
							if(dt.contieneAlgunaCondicion(condicionesProductoGratis)){
								CR.me.mostrarAviso("Eliminada promoción "+((PromocionExt)venta.getPromocionProductoGratis()).getNombrePromocion(), false);
								UtilesActualizadorPrecios.deshacerProductoGratis(dt);
								Auditoria.registrarAuditoria("Eliminada promoción de producto gratis: "+((PromocionExt)venta.getPromocionProductoGratis()).getCodPromocion()+". Producto Afectado:"+dt.getProducto().getCodProducto(), 'T');
							}
						}
					}
				}
				
				if((((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i))).contieneAlgunaCondicion(condicionCorporativo)) {
					DetalleTransaccion detalle = (((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)));
					CondicionVenta cv = ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getPrimeraCondicion(condicionCorporativo);
					PromocionExt promo = PromocionExt.getPromocionCorporativaPorCodigo(cv.getCodPromocion());
					detalle.setPrecioFinal(MathUtil.cutDouble((detalle.getPrecioFinal()*100)/(100-promo.getPorcentajeDescuento()), 2, true));
					detalle.setMontoImpuesto(venta.calcularImpuesto(detalle.getProducto(), detalle.getPrecioFinal()));
					detalle.setMontoDctoCorporativo(0);
					detalle.removeCondicion(new CondicionVenta(Sesion.condicionDescuentoCorporativo));
					detalle.setCondicionVenta(((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).construirCondicionVentaString());
				}
				if ((((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).contieneAlgunaCondicion(condicionesEmpaque))) {
					//Si es alguna de las posibilidades de empaque
					cantidadProdsConDescto = (((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)).getCantidad() - (((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)).getCantidad() % p.getCantidadVentaEmpaque()));
					if (cantidadProdsConDescto == ((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)).getCantidad()){
						detallesE.addElement(new Integer(i));
					} else {
						if(!((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)).contieneAlgunaCondicion(Sesion.condicionesCombo)){
							((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)).setCondicionVentaPromociones(new Vector<CondicionVenta>());
							((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)).setCondicionVenta(Sesion.condicionNormal);
							if(!VectorUtil.contieneInt(detallesN, i)){
								AcumN += ((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)).getCantidad();
								detallesN.addElement(new Integer(i));
							}
						}
					}
				} else if(((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).contieneAlgunaCondicion(Sesion.condicionesCombo)
						|| ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).contieneAlgunaCondicion(condicionesProductoGratis)){			
					if(((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).isCantidadCambiada()){
						cantidadCambiada = true;
						float variacion = ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getVariacion();
						if(variacion>0){
							try{
								//Disminuyo del detalle la variacion
								((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).setCantidad(((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCantidad()-variacion);
								((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).setCantidadCambiada(false);
								((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).setVariacion(0);
								
								//Si estoy agregando productos, como ya pertenece a un combo, hay que crear un detalle nuevo
								String codVendedor = null;
								String tipoCaptura = Sesion.PROCESO+"";
								DetalleTransaccion detalleAgregado = 
									new DetalleTransaccion(
										((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getProducto().getCodProducto(),
										codVendedor, 
										tipoCaptura , 
										variacion, 
										venta.getFechaTrans(), 
										venta.getHoraInicia(), 
										false);
								detallesCreados.addElement(detalleAgregado);
								
								
								// //BECO:27-11-2012 para agregar los combos ya armados
								if(!VectorUtil.contieneInt(detallesN, i)){
									detallesN.addElement(new Integer(i));
								}
								
							} catch(Exception e){
								logger.debug("actualizarPreciosExt(Producto p, Venta venta) ", e);
							}
						} else if(variacion <=0) { //Productos eliminados del combo
							DetalleTransaccion detalleActual = (DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i);
							if(((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).contieneAlgunaCondicion(Sesion.condicionesCombo)) {
								UtilesActualizadorPrecios.deshacerCombo(new Vector<Integer>() /*borrar*/, detallesAActualizar, variacion, detalleActual, null);
								if(!VectorUtil.contieneInt(detallesN, i)){
									detallesN.addElement(new Integer(i));
								}
							}
							else {
								CR.me.mostrarAviso("Eliminada promoción "+((PromocionExt)venta.getPromocionProductoGratis()).getNombrePromocion(), false);
								Auditoria.registrarAuditoria("Eliminada promoción de producto gratis: "+((PromocionExt)venta.getPromocionProductoGratis()).getCodPromocion()+". Producto afectado: "+detalleActual.getProducto().getCodProducto(), 'T');
								UtilesActualizadorPrecios.deshacerProductoGratis(detalleActual);
							}
						} 
					} else {
						if(!VectorUtil.contieneInt(detallesN, i)){
							AcumN+=((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)).getCantidad();
							detallesN.addElement(new Integer(i));
						}
					}
					
				} else if (!((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)).isCondicionEspecial() && !VectorUtil.contieneInt(detallesN, i)) {
					//Si no tiene ninguna rebaja
					AcumN += ((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)).getCantidad();
					detallesN.addElement(new Integer(i));
				} else {
					if (Sesion.desctosAcumulativos && p.isIndicaDesctoEmpleado() && Sesion.isIndicaDesctoEmpleado() && venta.isAplicaDesctoEmpleado()) {
						Vector<String> condicionesEmpleado = new Vector<String>();
						condicionesEmpleado.add(Sesion.condicionCambioPrecio);
						condicionesEmpleado.add(Sesion.condicionDesctoPorDefecto);
						condicionesEmpleado.add(Sesion.condicionDesctoPrecioGarantizado);
						if (!((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)).contieneAlgunaCondicion(condicionesEmpleado)) {
							//Si no es una rebaja de aplica el descuento empleado
							Vector<Integer> desctosDefectos = new Vector<Integer>();
							desctosDefectos.addElement(new Integer(i));
							venta.aplicarCondicionDescuentoEmpleado(p, desctosDefectos, null, 0);
						}
					} else {
						// Aquí se chequean los detalles que son de tipo desct por defecto y se les recalcula el impuesto 
						((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).setMontoImpuesto(venta.calcularImpuesto(p,((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)).getPrecioFinal()));
					}
				}
			} 
		}
		//Los detalles nuevos que fueron creados en el ciclo anterior se agregan al final del vector de detalles transaccion
		for(int i=0;i<detallesCreados.size();i++){
			venta.getDetallesTransaccion().addElement((DetalleTransaccion)detallesCreados.elementAt(i));
			detallesN.addElement(new Integer(venta.getDetallesTransaccion().size()-1));
			AcumN+=((DetalleTransaccion)detallesCreados.elementAt(i)).getCantidad();
		}
		
		// Se evalúan y aplican las condiciones de venta a los detalles afectados.
		if (detallesN.size() > 0 || cantidadCambiada || venta.getDescuentoCorporativoAplicado()!=0){
			nuevosDetalles = aplicarCondicionDeVenta(p, detallesN, AcumN, venta);
			Vector<Integer> nuevoDetallesN = ( nuevosDetalles.elementAt(0));
			Vector<Integer> nuevosEmpaques = (nuevosDetalles.elementAt(1));
			Vector<Integer> nuevosCombos = (nuevosDetalles.elementAt(2));
			
			detallesE = nuevosEmpaques;
			
			
			// Se acumulan los detalles que se encuentran en condición de empaque
			UtilesActualizadorPrecios.acumularDetallesPromociones(detallesE, false);
			
			// Se acumulan los detalles que se encuentran en la condición distinta a la de empaque y se colocan al final del vector original
			UtilesActualizadorPrecios.acumularDetallesPromociones(nuevoDetallesN, false);
			
			UtilesActualizadorPrecios.acumularDetallesPromociones(nuevosCombos, false);
			
			
			// Se eliminan los detalles que quedaron con cantidad cero
			for(int i=0;i<venta.getDetallesTransaccion().size();i++){
				try{
					DetalleTransaccion dt = ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i));
					if (dt.getCantidad() <= 0 ) {
						venta.getDetallesTransaccion().set(i, null);
					}
				}catch(Exception e){
					//esto ocurre cuando el detalle ya fue eliminado, no deberia pasar
				}
			}
		
			// Se eliminan los elementos nulos que quedan en el vector de detalles original
			while (venta.getDetallesTransaccion().removeElement(null));
			
			// Limpiamos los vectores de posiciones
			detallesN.clear();
			nuevoDetallesN.clear();
			detallesE.clear();
			
		}
		//Llama recursivamente al algoritmo de actualizacion de precios sobre los detalles afectados por los combos
		//En los que participaba el producto prod
		for(int i=0;i<detallesAActualizar.size();i++){
			if(!((DetalleTransaccion)detallesAActualizar.elementAt(i)).contieneAlgunaCondicion(Sesion.condicionesCombo) || !Sesion.promocionesAcumulativas){
				BECOActualizadorPrecios.posicionesCambiadas = true;
				actualizarPreciosExt(((DetalleTransaccion)detallesAActualizar.elementAt(i)).getProducto(), venta);
			}
		}
		if(BECOActualizadorPrecios.posicionesCambiadas && detallesAActualizar.size()!=0)
			BECOActualizadorPrecios.posicionesCambiadas=false;
			
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarPrecios(Producto) - end");
		}
	}
	
	/**
	 * Chequea los descuentos aplicables al producto especificado, verifica prioridades de los mismos y aplica el descuento.
	 * @param prod Producto al que se le aplicara el descuento.
	 * @param detallesN Vector de posiciones del producto .
	 * @param cantidad Cantidad de productos presentes en el detalle.
	 * @return Vector - Vector donde la posición 
	 * 		0: son las posiciones de los detalles del producto cuya condición es distinta a la de empaque 
	 * 		1: se encuentran los que tienen condición empaque
	 * 		2: se encuentran los detalles que participan en combos
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unchecked")
	private Vector<Vector<Integer>> aplicarCondicionDeVenta(Producto prod, Vector<Integer> detallesN, float cantidad, Venta venta) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarCondicionDeVenta(Producto, Vector, float) - start");
		}
		
		// Variables utilizables para los calculos
		int puntoDeChequeo = 0;
		
		// Variable de retorno
		Vector<Vector<Integer>> retorno = new Vector<Vector<Integer>>();
		Vector<Integer> nuevosEmpaques = new Vector<Integer>();
		Vector<Integer> nuevosNoEmpaques = new Vector<Integer>();
		Vector<Integer> nuevosCombos = new Vector<Integer>();
		
		// Buscamos las condiciones de venta que aplican al producto
		Vector<Vector<Object>> descuentos = buscarCondicionesDeVenta(prod, venta);
		
		// Definimos las variables necesarias para la creacion de los nuevos detalles
		String vendedor; 
		String supervisor; 
		String tipCaptura; 
		double pFinal;
		float cantidadAsignar;
		String tipoEntrega;
		
		boolean corporativosParaConcurso = true;
		
		// Verificamos si aplican condiciones de venta al producto, de no ser así se colocan como normales en los detalles
		if ((descuentos.size() > 0) /*&& (cantidad > 0)*/) {
			
			// Obtenemos el mejor descuento para el producto
			int posicionMejor = 0;
			Vector<Object> dctoActual;
			double precioFinal = Double.MAX_VALUE;
			for (int i=0; i<descuentos.size(); i++) {
				dctoActual = descuentos.elementAt(i);
				if (precioFinal > ((Double)dctoActual.elementAt(0)).doubleValue()) {
					//El descuento corporativo no participa en el concurso si los descuentos son acumulativos
					if(Sesion.desctosAcumulativos && ((String)dctoActual.elementAt(1)).equalsIgnoreCase(Sesion.condicionDescuentoCorporativo)){
						//No hay ningun descuento a aplicar distinto al corporativo
						corporativosParaConcurso = false;
					} else {
						posicionMejor = i;
						precioFinal = ((Double)dctoActual.elementAt(0)).doubleValue();
					}
				}
			}
			
			// Si se trata de empaque, aplicamos el empaque unicamente a la cantidad de productos exacta
			// el resto seguira con el algoritmo de calculo NoEmpaque
			if ((((CondicionVenta)(descuentos.elementAt(posicionMejor)).elementAt(3)).getCondicion()).equals(Sesion.condicionEmpaque)) {
				
				// Separo en empaques y no empaques y aplico empaque
				float prodsEmpaque = cantidad - (cantidad % prod.getCantidadVentaEmpaque());	
				cantidad -= prodsEmpaque;			
				
				dctoActual = descuentos.elementAt(posicionMejor);
				Vector<Object> datosNuevosEmpaques = venta.aplicarCondicionEmpaque(prod, detallesN, prodsEmpaque, posicionMejor, dctoActual);
				
				nuevosEmpaques = (Vector<Integer>)datosNuevosEmpaques.elementAt(0);
				
				//a cuantos detalles de transaccion les he aplicado sus promociones
				puntoDeChequeo = ((Integer)datosNuevosEmpaques.elementAt(1)).intValue();
					
				// Elimino la condicion de venta empaque del vector de condiciones de venta
				descuentos.remove(posicionMejor);
					
				// Busco el mejor de los descuentos que restan
				posicionMejor = 0;
				precioFinal = Double.MAX_VALUE;
				for (int i=0; i<descuentos.size(); i++) {
					dctoActual = (Vector<Object>)descuentos.elementAt(i);
					if (precioFinal > ((Double)dctoActual.elementAt(0)).doubleValue()) {
						//El descuento corporativo no participa en el concurso si los descuentos son acumulativos
						if(!Sesion.desctosAcumulativos || !((String)dctoActual.elementAt(1)).equalsIgnoreCase(Sesion.condicionDescuentoCorporativo)){
							posicionMejor = i;
							precioFinal = ((Double)dctoActual.elementAt(0)).doubleValue();
						}
					}
				}
					
			} else if ((((CondicionVenta)(descuentos.elementAt(posicionMejor)).elementAt(3)).getCondicion()).equals(Sesion.condicionDescuentoEnCombo)) {
				if(!Sesion.promocionesAcumulativas){
					dctoActual = descuentos.elementAt(posicionMejor);
					PromocionExt promocionAplicada = (PromocionExt)(PromocionExt.getPromocionesCombo(prod, ((CondicionVenta)(descuentos.elementAt(posicionMejor)).elementAt(3)).getCodPromocion())).elementAt(0);
					Vector<Integer> detallesEnCombo = new Vector<Integer>();
					while(Detector.verificaCantidadesMinimasCombo(promocionAplicada,new Vector<String>(), false)){
						Vector<Integer> resultado = UtilesActualizadorPrecios.aplicarCondicionDescuentoEnCombo(dctoActual, prod, false);
						detallesEnCombo = resultado;
						if(detallesEnCombo.size()==0)
							break;
						else 
							nuevosCombos = detallesEnCombo;
					}
					if(BECOActualizadorPrecios.posicionesCambiadas){
						Vector<Vector<Integer>> nuevasPosiciones = UtilesActualizadorPrecios.actualizarPosiciones();
						nuevosEmpaques = nuevasPosiciones.elementAt(0);
						nuevosNoEmpaques = nuevasPosiciones.elementAt(1);
						nuevosCombos = nuevasPosiciones.elementAt(2);
					}
					
					// Elimino la condicion de venta empaque del vector de condiciones de venta
					descuentos.remove(posicionMejor);
					
					// Busco el mejor de los descuentos que restan
					posicionMejor = 0;
					precioFinal = Double.MAX_VALUE;
					for (int i=0; i<descuentos.size(); i++) {
						dctoActual = descuentos.elementAt(i);
						if (precioFinal > ((Double)dctoActual.elementAt(0)).doubleValue()) {
							//El descuento corporativo no participa en el concurso si los descuentos son acumulativos
							if(!Sesion.desctosAcumulativos || !((String)dctoActual.elementAt(1)).equalsIgnoreCase(Sesion.condicionDescuentoCorporativo)){
								posicionMejor = i;
								precioFinal = ((Double)dctoActual.elementAt(0)).doubleValue();
							}
						}
					}
				}
			}
			
			
			// Si quedan productos y descuentos para ser aplicados
			if ((/*(cantidad > 0) ||*/ (descuentos.size() > 0) 
					&& corporativosParaConcurso)) {
				//boolean productoGratisAplicado = false;			
				while (puntoDeChequeo < detallesN.size()) {
					DetalleTransaccion nuevoDetalleTrans;
					cantidadAsignar = ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCantidad();
					Vector<String> condicionProductoGratis = new Vector<String>();
					condicionProductoGratis.addElement(Sesion.condicionProductoGratis);
					vendedor = ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCodVendedor();
					supervisor = ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCodSupervisor();
					tipCaptura = ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getTipoCaptura();
					dctoActual = (Vector<Object>)descuentos.elementAt(posicionMejor);
					pFinal = ((Double)dctoActual.elementAt(0)).doubleValue();
					if ((((CondicionVenta)(descuentos.elementAt(posicionMejor)).elementAt(3)).getCondicion()).equals(Sesion.condicionPromocion)) {
						// Si se trata de una promocion
						nuevoDetalleTrans = venta.aplicarCondicionPromocion (prod, detallesN, dctoActual, puntoDeChequeo);
						nuevoDetalleTrans.setPrecioOriginal(nuevoDetalleTrans.getPrecioFinal());
						venta.getDetallesTransaccion().addElement(nuevoDetalleTrans);
						nuevosNoEmpaques.addElement(new Integer(venta.getDetallesTransaccion().size() - 1));
						cantidad -= cantidadAsignar;
						puntoDeChequeo++;
					} /*else if ((((CondicionVenta)((Vector)descuentos.elementAt(posicionMejor)).elementAt(3)).getCondicion()).equals(Sesion.condicionDescuentoCorporativo)) {
						// Aplicar descuento corporativo a los productos que no estan en empaque (detallesN)
						if(corporativosParaConcurso){
							nuevoDetalleTrans = (DetalleTransaccion)UtilesActualizadorPrecios.aplicarCondicionDescuentoCorporativo(prod, detallesN, dctoActual, puntoDeChequeo);
							venta.getDetallesTransaccion().addElement(nuevoDetalleTrans);
							nuevosNoEmpaques.addElement(new Integer(venta.getDetallesTransaccion().size() - 1));
						}
						cantidad -= cantidadAsignar;
						puntoDeChequeo++;
					}*/ else if ((((CondicionVenta)(descuentos.elementAt(posicionMejor)).elementAt(3)).getCondicion()).equals(Sesion.condicionDesctoEmpleado)) {
						// Si no se trata de una promocion aplico el descuento de empleado al producto
						nuevoDetalleTrans = venta.aplicarCondicionDescuentoEmpleado (prod, detallesN, dctoActual, puntoDeChequeo);
						venta.getDetallesTransaccion().addElement(nuevoDetalleTrans);
						nuevosNoEmpaques.addElement(new Integer(venta.getDetallesTransaccion().size() - 1));
						cantidad -= cantidadAsignar;
						puntoDeChequeo++;
					}
				}
			}
		}
		
		// Colocamos las condiciones de venta normales (Si existen)
		if (cantidad > 0) {
			while (puntoDeChequeo < detallesN.size()) {
				//if(!((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).contieneAlgunaCondicion(Sesion.condicionesNuevas)){
					vendedor = ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCodVendedor();
					supervisor = ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCodSupervisor();
					tipCaptura = ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getTipoCaptura();
					pFinal = ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getPrecioFinal();
					cantidadAsignar = ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCantidad();
					tipoEntrega = ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getTipoEntrega();
					String condicion = ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCondicionVenta();
					
					Vector<CondicionVenta> condicionVentaPromociones = ((DetalleTransaccion)((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).clone()).getCondicionesVentaPromociones();
					
					pFinal = MathUtil.cutDouble(pFinal,2, true);
					if(condicion==null || condicion.equalsIgnoreCase("") || condicionVentaPromociones.size()==0){
						pFinal = ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getProducto().getPrecioRegular();
						condicion = Sesion.condicionNormal;
					}
					DetalleTransaccion nuevoDetalleTrans = new DetalleTransaccion(
									vendedor,
									prod,
									cantidadAsignar,
									condicion,
									supervisor,
									pFinal,
									venta.calcularImpuesto(prod,pFinal),
									tipCaptura,
									-1,
									tipoEntrega);
					nuevoDetalleTrans.setCondicionVentaPromociones(condicionVentaPromociones);
					nuevoDetalleTrans.setMontoDctoEmpleado(((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getMontoDctoEmpleado());
					nuevoDetalleTrans.setMontoDctoCombo(((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getMontoDctoCombo());
					venta.getDetallesTransaccion().addElement(nuevoDetalleTrans);
					
					//Marco el detalle anterior como sucio
					((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).setCantidad(0);
					((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).setVariacion(0);
					((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).setCantidadCambiada(false);
					
					
					if(nuevoDetalleTrans.getCondicionesVentaPromociones().size()==0){
						nuevoDetalleTrans.setPrecioFinal(nuevoDetalleTrans.getProducto().getPrecioRegular());
						nuevosNoEmpaques.addElement(new Integer(venta.getDetallesTransaccion().size() - 1));
					}
					else
						if(nuevoDetalleTrans.contieneAlgunaCondicion(Sesion.condicionesCombo)) //BECO:27-11-2012, para agregar al vector los combos ya armados
							nuevosCombos.addElement(new Integer(venta.getDetallesTransaccion().size() - 1));
					
				puntoDeChequeo++;
			}
		}
		
		if(Sesion.promocionesAcumulativas){
			//TODO temporal, resolver cuando se hagan los casos no acumulativos
			/*Vector descuentoAhorroEnCompra = UtilesActualizadorPrecios.getDescuentosAhorroEnCompra(prod.getCodProducto());
			if (descuentoAhorroEnCompra.size()!=0 && ((CondicionVenta)descuentoAhorroEnCompra.elementAt(3)).getCondicion().equalsIgnoreCase(Sesion.condicionDescuentoEnProductos)) {
				UtilesActualizadorPrecios.aplicarDescuentoEnProductos(prod, nuevosNoEmpaques, descuentoAhorroEnCompra);
				UtilesActualizadorPrecios.aplicarDescuentoEnProductos(prod, nuevosCombos, descuentoAhorroEnCompra);
				UtilesActualizadorPrecios.aplicarDescuentoEnProductos(prod, nuevosEmpaques, descuentoAhorroEnCompra);
			}*/
			
			Vector<Vector<Object>> datosCombos = UtilesActualizadorPrecios.verificarDatosCombo(prod);
			for(int i=0;i<datosCombos.size();i++){
				Vector<Object> dato = (Vector<Object>)datosCombos.elementAt(i);
				Vector<Object> dctoActual =  UtilesActualizadorPrecios.crearDescuentoCombo(dato);
				if(((String)(dctoActual.elementAt(1))).equalsIgnoreCase(Sesion.condicionDescuentoEnCombo)){
					PromocionExt promocionAplicada = (PromocionExt)dato.elementAt(2); //(PromocionExt)(PromocionExt.getPromocionesCombo(prod, ((CondicionVenta)dctoActual.elementAt(3)).getCodPromocion())).elementAt(0);
					Vector<Integer> detallesEnCombo = new Vector<Integer>();
					while(Detector.verificaCantidadesMinimasCombo(promocionAplicada,new Vector<String>(), false)){
						Vector<Integer> resultado = UtilesActualizadorPrecios.aplicarCondicionDescuentoEnCombo(dctoActual, prod, false);
						detallesEnCombo.addAll(resultado);
						if(resultado.size()==0)
							break;
						else 
							nuevosCombos.addAll(resultado) ; //BECO:27-11-2012 se cambio a addall y el parametro por resultado
					}
				} else if(((String)dctoActual.elementAt(1)).equalsIgnoreCase(Sesion.condicionComboPorCantidades)){
					PromocionExt promocionAplicada = (PromocionExt)dato.elementAt(2); //(PromocionExt)(PromocionExt.getPromocionesCombo(prod, ((CondicionVenta)dctoActual.elementAt(3)).getCodPromocion())).elementAt(0);
					Vector<Integer> detallesEnCombo = new Vector<Integer>();
					while(Detector.verificaCantidadesMinimasCombo(promocionAplicada,new Vector<String>(), false)){
						Vector<Integer> resultado = UtilesActualizadorPrecios.aplicarCondicionComboCantidades(dctoActual, prod, false, nuevosNoEmpaques, nuevosEmpaques);
						detallesEnCombo.addAll(resultado);
						if(resultado.size()==0)
							break;
						else 
							nuevosCombos.addAll(resultado) ;//BECO:27-11-2012 se cambio a addall y el parametro por resultado
					}
				}
				
			}
			/*if(BECOActualizadorPrecios.posicionesCambiadas){
				Vector nuevasPosiciones = UtilesActualizadorPrecios.actualizarPosiciones();
				nuevosEmpaques = (Vector)nuevasPosiciones.elementAt(0);
				nuevosNoEmpaques = (Vector)nuevasPosiciones.elementAt(1);
				nuevosCombos = (Vector)nuevasPosiciones.elementAt(2);
			}*/
		}
		
		// Preguntamos si los descuentos son acumulativos para aplicar el dcto a COLABORADOR sobre los precios finales
		if(Sesion.desctosAcumulativos){
			if (prod.isIndicaDesctoEmpleado() && 
					Sesion.isIndicaDesctoEmpleado()&& 
					venta.isAplicaDesctoEmpleado()) {
				venta.aplicarCondicionDescuentoEmpleado (prod, nuevosNoEmpaques, null, 0);
				venta.aplicarCondicionDescuentoEmpleado (prod, nuevosEmpaques, null, 0);
				venta.aplicarCondicionDescuentoEmpleado (prod, nuevosCombos, null, 0);
			}
		}
		
		if(Sesion.promocionesAcumulativas){
			if(CR.meVenta.getVenta().getDescuentoCorporativoAplicado()!=0.0) {
				Vector<Object> desctoActual = UtilesActualizadorPrecios.crearDescuentoCorporativo(prod);
				if(((String)desctoActual.elementAt(1)).equalsIgnoreCase(Sesion.condicionDescuentoCorporativo)){
					//Llamada a aplicar descuento corporativo
					UtilesActualizadorPrecios.aplicarCondicionDescuentoCorporativo(prod, nuevosNoEmpaques, desctoActual, 0);
					UtilesActualizadorPrecios.aplicarCondicionDescuentoCorporativo(prod, nuevosEmpaques, desctoActual, 0);
					UtilesActualizadorPrecios.aplicarCondicionDescuentoCorporativo(prod, nuevosCombos, desctoActual, 0);
				}
			}
		}
		
		//Preguntamos si el descuento a empaque es acumulativo para aplicarlo
		if (Sesion.desctoEmpaqueAcumulativo && (prod.getCantidadVentaEmpaque() > 0 )) {
			 Vector<Object> nuevosEmp = venta.aplicarCondicionEmpaque(prod, nuevosNoEmpaques);
			 nuevosEmpaques = (Vector<Integer>)nuevosEmp.elementAt(0);
		}
		
		if(Sesion.promocionesAcumulativas){
			PromocionExt promocionProductoGratis = PromocionExt.getPromocionProductoGratis();
			CR.meVenta.getVenta().actualizarMontoTransaccion();
			if(promocionProductoGratis!=null && promocionProductoGratis.getMontoMinimo()<=CR.meVenta.getVenta().consultarMontoTrans()){
				try{
					Vector<Detalle> nuevosDetalleTrans = UtilesActualizadorPrecios.aplicarCondicionProductoGratis(promocionProductoGratis);
					for(int i=0;i<nuevosDetalleTrans.size();i++){
						venta.getDetallesTransaccion().addElement((DetalleTransaccion)nuevosDetalleTrans.elementAt(i));
						if(!((DetalleTransaccion)nuevosDetalleTrans.elementAt(i)).contieneAlgunaCondicion(Sesion.condicionesCombo))
							nuevosNoEmpaques.addElement(new Integer(venta.getDetallesTransaccion().size() - 1));
						else 
							nuevosCombos.addElement(new Integer(venta.getDetallesTransaccion().size() - 1));
					}
				} catch(Exception e){
					e.printStackTrace();
					logger.error("Error aplucando promoción de producto gratis", e);
				}
			}
		}

		// Armamos el vector de retorno
		retorno.addElement(nuevosNoEmpaques);
		retorno.addElement(nuevosEmpaques);
		retorno.addElement(nuevosCombos);

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
	 * 				   - posición 0 posee la información del descuento promocional. (PRECIO FINAL, CONDICION VENTA, CODIGO PROMOCION, CondicionVenta)
	 * 				   - posición 1 posee la información del descuento a empaque 	(PRECIO FINAL, CONDICION VENTA, CODIGO PROMOCION, CondicionVenta)
	 * 				   - posición 2 (si no son acumultativos) posee la información del descuento a COLABORADOR. (PRECIO FINAL, CONDICION VENTA, CODIGO PROMOCION, CondicionVenta)
	 * 				   - posicion 3 posee la información del descuento corporativo (PRECIO FINAL, CONDICION VENTA, CODIGO PROMOCION, CondicionVenta)
	 * 				   - posicion 4 posee la información de la promoción de descuento en combos (PRECIO FINAL, CONDICION VENTA, CODIGO PROMOCION, CondicionVenta, PromocionExt)
	 * 				   - posicion 5 posee la información de la promoción de producto de menor precio gratis
	 * NOTA: en los casos donde no aplica, el código de promoción es cero
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private Vector<Vector<Object>> buscarCondicionesDeVenta(Producto prod, Venta venta){
		if (logger.isDebugEnabled()) {
			logger.debug("buscarCondicionesDeVenta(Producto) - start");
		}

		double dctoEmpaque,dctoEmpleado, precioEmpleado = 0.0;
		//double precioEmpaque;
		Vector<Vector<Object>> descuentos = new Vector<Vector<Object>>();
		
		//true: aplicar combo de alguna promoción
		//false: aplicar empaque
		//En caso de que sea promoción se devolveran todas las existentes para que participen en el
		//concurso por prioridades en aplicarCondicionVenta
		boolean aplicarCombo = false; 
		
		//Vector datosCombos = null;
		
		/*if(!Sesion.promocionesAcumulativas)
			datosCombos = UtilesActualizadorPrecios.verificarDatosCombo(prod);*/
		
		//Empaque no puede ser aplicado junto con ningun combo, entonces verifico si
		//alguno de los combos tiene un porcentaje que convenga mas al cliente que el empaque
		
		/*if(datosCombos!=null && ((PromocionExt)datosCombos.elementAt(2)).getPorcentajeDescuento()>=prod.getDesctoVentaEmpaque()){
			aplicarCombo = true;
		}*/
		
		// Calculamos las promociones
		Vector<Object> datosPromocion = verificarPromociones(prod);
		if (datosPromocion.size() > 0) {
			// Existen promociones vigentes para el producto
			Vector<Object> promo = new Vector<Object>(2);
			promo.addElement((Double)datosPromocion.elementAt(1));  //PRECIO FINAL
			promo.addElement(Sesion.condicionPromocion);			//CONDICION VENTA (string)
			promo.addElement((Integer)datosPromocion.elementAt(0));	//CODIGO DE LA PROMOCION
			CondicionVenta cv = new CondicionVenta(Sesion.condicionPromocion, ((Integer)datosPromocion.elementAt(0)).intValue(),((Double)datosPromocion.elementAt(2)).doubleValue(),"Promoción básica",((Integer)datosPromocion.elementAt(3)).intValue());
			promo.addElement(cv);									//CONDICION VENTA (Objeto)
			descuentos.addElement(promo);
		}
		

		
		if (!Sesion.desctoEmpaqueAcumulativo && !aplicarCombo) {
			// Calculamos el precio de condicion empaque

			dctoEmpaque = prod.getPrecioRegular() * (prod.getDesctoVentaEmpaque()/100);
			if (dctoEmpaque > 0) {
				// Agregamos el empaque al vector de condiciones de venta
				Vector<Object> empaque = new Vector<Object>(2);
				empaque.addElement(new Double(prod.getPrecioRegular() - dctoEmpaque));	//PRECIO FINAL
				empaque.addElement(Sesion.condicionEmpaque);							//CONDICION DE VENTA (STRING)
				empaque.addElement(new Integer(0));										//CODIGO DE LA PROMO 0 PORQUE NO APLICA
				CondicionVenta cv = new CondicionVenta(Sesion.condicionEmpaque);
				empaque.addElement(cv);													//CONDICION DE VENTA (OBJETO)
				descuentos.addElement(empaque);
			}
		}
		
		if (!Sesion.desctosAcumulativos) {
			// Calculamos el precio con el descuento a COLABORADOR si aplica
			// DEBERIA VENIR UNA PREGUNTA SI APLICA EL DESCUENTO A COLABORADOR
			if (prod.isIndicaDesctoEmpleado() && Sesion.isIndicaDesctoEmpleado()&& venta.isAplicaDesctoEmpleado()) {
				dctoEmpleado = prod.getPrecioRegular() * (Sesion.getDesctoVentaEmpleado()/100);	
				precioEmpleado = prod.getPrecioRegular() - dctoEmpleado;
				Vector<Object> empleado = new Vector<Object>(2);
				empleado.addElement(new Double(precioEmpleado));		//PRECIO FINAL
				empleado.addElement(Sesion.condicionDesctoEmpleado);	//CONDICION DE VENTA (String)
				empleado.addElement(new Integer(0));					//CODIGO DE LA PROMO 0 PORQUE NO APLICA
				CondicionVenta cv = new CondicionVenta(Sesion.condicionDesctoEmpleado,0,Sesion.getDesctoVentaEmpleado(),"Descuento Empleado",1);
				empleado.addElement(cv);								//CONDICION DE VENTA (objeto)
				
				descuentos.addElement(empleado);
			}
		}
		
		//Nos interesa que el descuento corporativo este aplicado siempre en el vector.
		//Pero hay que tener cuidado, no participa en el concurso (promocion que mas conviene)
		//si los descuentos son acumulativos
		/*if (CR.meVenta.getVenta().getDescuentoCorporativoAplicado()!=0.0) {
			Vector descuentosCorporativos = UtilesActualizadorPrecios.crearDescuentoCorporativo(prod);
			descuentos.addAll(descuentosCorporativos);
		}*/
		
		/*if (datosCombos!= null && datosCombos.size() > 0 && aplicarCombo && !Sesion.promocionesAcumulativas) {
			Vector promo = UtilesActualizadorPrecios.crearDescuentoCombo(datosCombos);
			descuentos.addElement(promo);
		}*/
		
		if(!Sesion.promocionesAcumulativas){
			Vector<Object> promo = UtilesActualizadorPrecios.getDescuentosAhorroEnCompra(prod.getCodProducto());
			if(promo.size()!=0)
				descuentos.addElement(promo);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("buscarCondicionesDeVenta(Producto) - end");
		}
		return descuentos;
	}
	
	/**
	 * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#sumarDonaciones(java.lang.double)
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public double sumarDonaciones(Venta venta, boolean solicitarDonaciones){
		
		double subTotal = venta.consultarMontoTrans();
		if(venta.getDetallesTransaccion().size()!=0){
			Vector<Donacion> donaciones1 = DonacionBD.getDonacionesTipo1();
			Vector<Donacion> donaciones2 = DonacionBD.getDonacionesTipo2(true);
			
			if(donaciones1.size()!=0 && getDonacionesRegistradasTipo1(Venta.donacionesRegistradas).size()==0 && solicitarDonaciones){
				VentanaDonacion ventanaDonacion = new VentanaDonacion((float)(5-subTotal%5));//(float)(1-(subTotal%(int)subTotal)));
				MensajesVentanas.centrarVentanaDialogo(ventanaDonacion);
				//if(ventanaDonacion.isDono()) subTotal+=ventanaDonacion.getMontoDonacion();
			}
			
			if(donaciones2.size()!=0 && UtilesActualizadorPrecios.getDonacionesRegistradasTipo2(Venta.donacionesRegistradas).size()==0 && solicitarDonaciones){
				VentanaDonacion ventanaDonacion = new VentanaDonacion(true);
				MensajesVentanas.centrarVentanaDialogo(ventanaDonacion);
			}
			
			Iterator<DonacionRegistrada> donaciones = Venta.donacionesRegistradas.iterator();
			while(donaciones.hasNext()){
				DonacionRegistrada d = (DonacionRegistrada)donaciones.next();
				subTotal+=d.getMontoDonado();
			}
		}
		
		return subTotal;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en 'Iterator'
	* Fecha: agosto 2011
	*/
	public double sumarMontoAhorrado(Venta venta) {
		double montoAhorrado1 = 0, montoAhorrado2 = 0; 
		if(venta.getDetallesTransaccion().size()!=0){
			Iterator<DetalleTransaccion> i = venta.getDetallesTransaccion().iterator();
			while (i.hasNext()){
				DetalleTransaccion dt = (DetalleTransaccion)i.next();
				if (!dt.getCondicionVenta().equals(Sesion.condicionNormal)){
					double montoAhorradoEsteDetalle = MathUtil.roundDouble(dt.getProducto().getPrecioRegular()+
							venta.calcularImpuesto(dt.getProducto(),dt.getProducto().getPrecioRegular())) 
							- MathUtil.roundDouble(dt.getPrecioFinal() + venta.calcularImpuesto(dt.getProducto(),dt.getPrecioFinal()));
					montoAhorradoEsteDetalle = montoAhorradoEsteDetalle*dt.getCantidad(); 
					montoAhorrado1 +=  montoAhorradoEsteDetalle;
				}
			}
			if (venta.getPagos().size()>0){
				Iterator<Pago> j = venta.getPagos().iterator();
				while (j.hasNext()){	
					Pago p = (Pago)j.next();
					if (p.getFormaPago().getCodigo().equals(Sesion.FORMA_PAGO_PROMO_MERCADEO)
							||p.getFormaPago().getCodigo().equals(Sesion.FORMA_PAGO_PROMO_MERCHANDISING)
							||p.getFormaPago().getCodigo().equals(Sesion.FORMA_PAGO_TRANSACCION_PREMIADA)
							||p.getFormaPago().getCodigo().equals(Sesion.FORMA_PAGO_CUPON_DESCUENTO))
						montoAhorrado2 += p.getMonto();
				}
			}
		}
		return montoAhorrado1+montoAhorrado2;
	}
	
	/**
     * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#instanciarBoton()
     */
    public JHighlightButton instanciarBoton() {
    	this.jButton = new JHighlightButton();
        this.jButton.setBackground(new java.awt.Color(226, 226, 222));
        this.jButton.setActionCommand("donaciones");
        this.jButton.setText("F11 - Donaciones");
        this.jButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        if(DonacionBD.getDonacionesTipo2(false).size()==0 || CR.meVenta.getVenta()== null) 
        	this.jButton.setEnabled(false);
        else this.jButton.setEnabled(true);
        return this.jButton;
    }
    
	/**
	 * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#sumarDonaciones()
	 * **/
	public void sumarDonaciones(boolean totalizar){
		VentanaDonacion v = new VentanaDonacion(totalizar);
		MensajesVentanas.centrarVentanaDialogo(v);
	}
	
	/**
	 * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#ejecutarTransaccionPremiada(double)
	 * **/
	public double ejecutarTransaccionPremiada(double montoAPagar, Vector<Pago> pagosRealizados, Cliente cliente){
		double montoPagado=0;

		try{
			PromocionExt detallePromo = Detector.proximaTransaccionPremiada();
			//Si hay transacciones por premiar y no hay descuento empleado
			if(detallePromo!=null && !CR.meVenta.getVenta().isAplicaDesctoEmpleado() && CR.meVenta.getVenta().getCliente().getTipoCliente()!=Sesion.CLIENTE_JURIDICO){
				MensajesVentanas.aviso("¡La transacción es premiada! \n\n"+detallePromo.getPorcentajeDescuento()+"% de descuento");
				montoPagado = MathUtil.roundDouble((montoAPagar-getDonacionesVenta())*detallePromo.getPorcentajeDescuento()/100);
				FormaDePago f = new FormaDePago(Sesion.FORMA_PAGO_TRANSACCION_PREMIADA,
						Sesion.TIPO_PAGO_BECO,
						BaseDeDatosVenta.obtenerNombFormaPago(Sesion.FORMA_PAGO_TRANSACCION_PREMIADA),
						null,
						false,false,false,false,false,false,false,false,0,0,0,false,false);
				Pago pago = new Pago(f,montoPagado,null,null,null,0,0,null);
				pagosRealizados.add(pago);
			}
		}catch(Exception e){
			logger.debug("ejecutarTransaccionPremiada(double montoAPagar, Vector pagosRealizados, Cliente cliente)",e);
		}
		return montoPagado;
	}
	

	public void llamadaDeRegalos(int n, Venta venta){
		//Cambio WDIAZ 07-2012 para que imprima ticket en devoluciones
		//if (!venta.isAplicaDesctoEmpleado() && CR.meVenta.getDevolucion()==null){
		if (!venta.isAplicaDesctoEmpleado()){
			if(n==1 && !PromocionExtBD.getRegalos().isEmpty()){
				PromocionExtBD.iteradorRegalo(1);
			}else if(n==2){
			if (!PromocionExtBD.getRegalos().isEmpty())
				PromocionExtBD.iteradorRegalo(2);
			}
		}
	}
	
	/**
     * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#instanciarBotonCupones()
     */
    public JHighlightButton instanciarBotonPromociones() {
    	this.jButton = new JHighlightButton();
        this.jButton.setBackground(new java.awt.Color(226, 226, 222));
        this.jButton.setActionCommand("promociones");
        this.jButton.setText("F12 - Promociones");
        this.jButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        if((CR.meServ.getApartado()==null || CR.meServ.getApartado().getDetallesServicio().size()==0) && 
        		CR.meVenta.getVenta()== null) 
        	this.jButton.setEnabled(false);
        else this.jButton.setEnabled(true);
        return this.jButton;
    }
    
	/*/
     * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#cargarPatrocinantes()
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en el 'Treemap'
	* Fecha: agosto 2011
	*/
    public TreeMap<PromocionExt,Vector<Producto>> cargarPatrocinantes(){
    	return UtilesBD.cargarPatrocinantes();
    }
    
    /*public HashMap cargarProductoComplementario(){
    	return UtilesBD.cargarProductoComplementario();
    }*/

     /** (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#ejecutarAhorroEnCompra(Venta venta)
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector', 'Iterator' y 'TreeMap'
	* Fecha: agosto 2011
	*/
    public void ejecutarAhorroEnCompra(Venta venta){
    	try{
	    	TreeMap<PromocionExt,Vector<Producto>> patrocinantes = CR.meVenta.getProductosPatrocinantes();
	    	Iterator<PromocionExt> i = patrocinantes.keySet().iterator();
	    	while(i.hasNext()){
	    		PromocionExt promocion = (PromocionExt)i.next();
	    		Vector<Producto> patrocinantesEstaPromo = patrocinantes.get(promocion);
	    		Vector<Detalle> detallesDeTransaccionAfectados = Detector.getProd(patrocinantesEstaPromo, venta);
	    		double totalTransaccion = venta.consultarMontoTrans() - getDonacionesVenta();
	    		if(detallesDeTransaccionAfectados.size()!=0 && 
	    				totalTransaccion>=promocion.getMontoMinimo()) {
	    			
	    			boolean promocionAplicada=false;
	    			Iterator<Detalle> iteraDetalle = detallesDeTransaccionAfectados.iterator();
	    			while(iteraDetalle.hasNext()){
	    				DetalleTransaccion detalle = (DetalleTransaccion)iteraDetalle.next();
	    				Producto productoPromocionado = detalle.getProducto();
	    				if(productoPromocionado.getTipoPromocionAplicada()==Sesion.TIPO_PROMOCION_AHORRO_COMPRA){
		    				promocionAplicada = true;
		    				break;
	    				}
	    			}
	    			
	    			if(promocionAplicada){
		    			MensajesVentanas.aviso("Se aplicó la promoción "+promocion.getNombrePromocion()+"\n "+
		    					promocion.getPorcentajeDescuento()+"% de descuento en todos los productos patrocinantes ");
		    			CR.me.mostrarAviso("Se aplicó la promoción "+promocion.getNombrePromocion()+". "+
		    					promocion.getPorcentajeDescuento()+"% de descuento en todos los productos patrocinantes ", true);
	    			}
	    		}
	    	}
    	}catch(Exception e){
    		e.printStackTrace();
    		logger.debug("ejecutarAhorroEnCompra()",e);
    	}
    }
    
     /**
     * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#verificarPromociones(Producto)
	 */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Object> verificarPromociones(Producto prod) {
				
		if (logger.isDebugEnabled()) {
			logger.debug("verificarPromociones(Producto) - start");
		}

		Promocion promocionAplicada = null;
		
		// Obtenemos las promociones del producto en el detalle de transaccion
		Vector<Promocion> promociones = new Vector<Promocion>();
		promociones = prod.getPromociones();
		
		// Buscamos la promocion con prioridad mas alta
		int posicionMayorPrioridad = 0;
		for (int i=0; i<promociones.size(); i++) {
			if (promocionAplicada == null) {
				promocionAplicada = (Promocion)promociones.elementAt(i);
				posicionMayorPrioridad = i;
			} else {
				if(((Promocion)promociones.elementAt(i)).getPrioridad() < promocionAplicada.getPrioridad()) {
					promocionAplicada = (Promocion)promociones.elementAt(i);
					posicionMayorPrioridad = i;
				}
			}
		}
		
		//Seteo el tipo de promoción aplicada al producto
		if(promociones.size()!=0)
			prod.setTipoPromocionAplicada(((Promocion)promociones.elementAt(posicionMayorPrioridad)).getTipoPromocion());
		//Calculamos el precio final
		Vector<Object> result = new Vector<Object>();
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
			result.addElement(new Double(promocionAplicada.getPorcentajeDescuento()));
			result.addElement(new Integer(promocionAplicada.getPrioridad()));
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("verificarPromociones(Producto) - end");
		}
		return result;
	}
	/**
	 * llamada a la promocion producto complementario
	 */
    /*public void ejecutarProductoComplementario(int p, Venta venta){
    	if(CR.meVenta.getDevolucion()==null)
    		UtilesActualizadorPrecios.ejecutarProductoComplementario(p, venta);
    }*/
    
    /****
     * Determina si debe actualizar uno o todos los detalles de la venta
     * Esto depende de si hay alguna promocion que dependa del total de la venta
     * @param p producto que estoy agregando
     * @param v venta actual
     */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector', 'Iterator' y 'TreeMap'
	* Fecha: agosto 2011
	*/
    public void actualizarPrecios(Producto p, Venta v){
    	try{
	    	//SI HAY PROMOCION DE AHORRO EN COMPRA ENTONCES ES NECESARIO ACTUALIZAR TODOS LOS PRECIOS
			//if(Detector.isAhorroEnCompra() /*|| v.getDescuentoCorporativoAplicado()!=0*/){
				//CR.meVenta.getVenta().actualizarMontoTransaccion();
				//CR.meVenta.getVenta().actualizarPreciosDetalle(CR.meVenta.getVenta().isAplicaDesctoEmpleado());
				actualizarPreciosExt(p, v);
				CR.meVenta.getVenta().actualizarMontoTransaccion();
				if(Detector.isAhorroEnCompra()){
					TreeMap<PromocionExt,Vector<Producto>> patrocinantes = 
						new TreeMap<PromocionExt,Vector<Producto>>(new ComparadorPromociones());
					patrocinantes = CR.meVenta.getProductosPatrocinantes();
					while(!patrocinantes.isEmpty()){
						PromocionExt promocion = (PromocionExt)patrocinantes.lastKey();
						Vector<Producto> patrocinantesEstaPromo = patrocinantes.get(promocion);
			    		Vector<String> codigos = new Vector<String>();
			    		for(int j=0;j<patrocinantesEstaPromo.size();j++){
			    			Producto prod = (Producto)patrocinantesEstaPromo.elementAt(j);
			    			codigos.addElement(prod.getCodProducto());
			    		}
			    		Vector<Integer> detallesPatrocinantesPosiciones = Detector.getDetallesProdPosiciones(codigos);
			    		Vector<Detalle> detallesPatrocinantes = Detector.getDetallesProd(patrocinantesEstaPromo);
			    		//Deshago ahorro en compra si la tenia aplicada
			    		for(int j=0;j<detallesPatrocinantes.size();j++){
			    			DetalleTransaccion detalle = (DetalleTransaccion)detallesPatrocinantes.elementAt(j);
			    			Vector<String> condicionesAhorroEnCompra = new Vector<String>();
			    			condicionesAhorroEnCompra.addElement(Sesion.condicionDescuentoEnProductos);
			    			CondicionVenta cv = detalle.getPrimeraCondicion(condicionesAhorroEnCompra);
			    			
			    			if(cv!=null){
			    				detalle.removeCondicion(cv);
			    				detalle.setCondicionVenta(detalle.construirCondicionVentaString());
			    				//Aumentar el precio final
			    				detalle.setPrecioFinal(MathUtil.roundDouble((detalle.getPrecioFinal()*100)/(100-cv.getPorcentajeDescuento())));
			    			}
			    		}	
			    		v.actualizarMontoTransaccion();
			    		if(Detector.isAhorroEnCompra()){
			    			for(int j=0;j<detallesPatrocinantes.size();j++){
				    			Vector<Object> descuentoAhorroEnCompra = UtilesActualizadorPrecios.getDescuentosAhorroEnCompra(promocion);
				    			if(descuentoAhorroEnCompra.size()!=0){
					    			UtilesActualizadorPrecios.aplicarDescuentoEnProductos(detallesPatrocinantesPosiciones, descuentoAhorroEnCompra);
					    			UtilesActualizadorPrecios.acumularDetallesPromociones(detallesPatrocinantesPosiciones, false);
					    			//Se eliminan los detalles que quedaron con cantidad cero
					    			for(int i=0;i<v.getDetallesTransaccion().size();i++){
					    				try{
					    					DetalleTransaccion dt = ((DetalleTransaccion)v.getDetallesTransaccion().elementAt(i));
					    					if (dt.getCantidad() <= 0) {
					    						v.getDetallesTransaccion().set(i, null);
					    					}
					    				}catch(Exception e){
					    					//El detalle ya fue eliminado, no deberia ocurrir
					    				}
					    			}
				    			}
				    			// Se eliminan los elementos nulos que quedan en el vector de detalles original
				    			while (v.getDetallesTransaccion().removeElement(null));
			    			}
			    		}
			    		patrocinantes = new TreeMap<PromocionExt,Vector<Producto>>(patrocinantes.headMap(promocion));
					}
				}
			/*} else {
				//SI NO, ACTUALIZAR SOLO ESTE PRODUCTO
				actualizarPreciosExt(p, v);
			}*/
    	}catch (Exception e){
    		logger.debug("actualizarPrecios(Producto p, Venta v)", e);
    		e.printStackTrace();
    	}
    }
    
	/**
	 * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#agregarPromocionesALoteSentencias(Venta, Statement, String)
	 */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void agregarPromocionesALoteSentencias(Venta venta, Statement loteSentenciasCR, String fechaTransString) throws SQLException {
		String sentenciaSQL;
		venta.asignaPagosClonados(venta);
		Iterator<DonacionRegistrada> i = Venta.donacionesRegistradas.iterator();
		int codigo = 0;
		while(i.hasNext()) {
			codigo++;
			DonacionRegistrada d = (DonacionRegistrada)i.next();
			sentenciaSQL = "insert into donacionesregistradas (montoDonado,fechaDonacion,numTransaccion,numCaja,numTienda,codigoDonacion,codigo) values ("+
			d.getMontoDonado()+",'"+fechaTransString+"',"+venta.getNumTransaccion()+","+venta.getNumCajaFinaliza()+","+venta.getCodTienda()+","+d.getCodigoDonacion()+","+codigo+")";
			loteSentenciasCR.addBatch(sentenciaSQL);
			d.asignarFormasPago(venta);
			Iterator<PagoDonacion> j = d.getFormasPago().iterator();
			int codigoPago = 0;
			while (j.hasNext()){
				codigoPago++;
				PagoDonacion pd = (PagoDonacion)j.next();
				sentenciaSQL = "insert into pagodonacion (fechaDonacion,numTransaccion,numCaja,numTienda,codigoDonacion,codigoDonacionRegistrada,codigoFormaPago,monto,codigoPago) values ('"+ 
				fechaTransString+"',"+venta.getNumTransaccion()+","+venta.getNumCajaFinaliza()+","+venta.getCodTienda()+","+d.getCodigoDonacion()+","+codigo+",'"+pd.getFormaPago().getCodigo()+"',"+pd.getMonto()+","+codigoPago+")";
				loteSentenciasCR.addBatch(sentenciaSQL);
			}
		}
		Iterator<RegaloRegistrado> j = Venta.regalosRegistrados.iterator();
		while(j.hasNext()) {
			RegaloRegistrado rr = (RegaloRegistrado)j.next();
			sentenciaSQL = "insert into regalosregistrados (cantidad,numTransaccion,numCaja,numTienda,fechaTransaccion,codPromocion,numDetalle) values ("+
			rr.getCantidad()+","+venta.getNumTransaccion()+","+venta.getNumCajaFinaliza()+","+venta.getCodTienda()+",'"+fechaTransString+"',"+rr.getCodPromocion()+","+rr.getNumdetalle()+")";
			loteSentenciasCR.addBatch(sentenciaSQL);
		}
		Iterator<PromocionRegistrada> k = Venta.promocionesRegistradas.iterator();
		int n = 0;
		while(k.hasNext()) {
			n++;
			PromocionRegistrada rpcr = (PromocionRegistrada)k.next();
			sentenciaSQL = "insert into promocionregistrada (numTransacion,numCaja,numTienda,fecha,codPromocion,codigoProducto,codigo,cantproducto) values ("+
			venta.getNumTransaccion()+","+venta.getNumCajaFinaliza()+","+venta.getCodTienda()+",'"+fechaTransString+"',"+rpcr.getCodigo()+","+rpcr.getCodProducto()+","+n+","+rpcr.getCantidad()+")";
			loteSentenciasCR.addBatch(sentenciaSQL);
		}
	}
	
	/**
	 * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#ventanaPromociones()
	 */
	public void ventanaPromociones(Venta venta) {
		MenuPromociones mp = new MenuPromociones(venta);
		MensajesVentanas.centrarVentanaDialogo(mp);
	}

	
	/**
	 * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#revisarDescuentoCorporativo()
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' e 'Iterator'
	* Fecha: agosto 2011
	*/
	public void revisarPromociones(Venta venta){
		Vector<DetalleTransaccion> detallesTransaccion = venta.getDetallesTransaccion();
		Iterator<DetalleTransaccion> detalles = detallesTransaccion.iterator();
		while (detalles.hasNext()){
			DetalleTransaccion detalle = (DetalleTransaccion)detalles.next();
			//si se aplico un descuento corporativo agregarlo al vector de promociones registradas
			Vector<String> condicionesCorporativo = new Vector<String>();
			condicionesCorporativo.add(Sesion.condicionDescuentoCorporativo);
			
			Vector<String> condicionesPromocion = new Vector<String>();
			condicionesPromocion.add(Sesion.condicionPromocion);
			
			Vector<String> condicionesProductoGratis = new Vector<String>();
			condicionesProductoGratis.addElement(Sesion.condicionProductoGratis);
			
			Vector<String> condicionesDescuentoEnProducto = new Vector<String>();
			condicionesDescuentoEnProducto.addElement(Sesion.condicionDescuentoEnProductos);
			
			if(detalle.contieneCondicion(condicionesCorporativo)) {
				Producto p = detalle.getProducto();
				//PromocionExt promocionAplicada = PromocionExt.getPromocionCorporativaPorCodigo(venta.getCodDescuentoCorporativo());
				//for(int i=0;i<cantidad;i++){
					PromocionRegistrada pr = new PromocionRegistrada("",venta.getCodDescuentoCorporativo(),p.getCodProducto(), detalle.getCantidad());
					Venta.promocionesRegistradas.add(pr);
				//}
			} 
			
			if (detalle.contieneCondicion(condicionesPromocion)) {
				Producto p = detalle.getProducto();
				CondicionVenta cv = (CondicionVenta)detalle.getPrimeraCondicion(condicionesPromocion);
				//for(int i=0;i<cantidad;i++){
					PromocionRegistrada pr = new PromocionRegistrada("",cv.getCodPromocion(),p.getCodProducto(), detalle.getCantidad());
					Venta.promocionesRegistradas.add(pr);
				//}
			} 
			
			if(detalle.contieneAlgunaCondicion(Sesion.condicionesCombo)){
				Producto p = detalle.getProducto();
				CondicionVenta cv =  detalle.getPrimeraCondicion(Sesion.condicionesCombo);
				//Promocion promocionAplicada = PromocionExt.getPromocionAplicadaCombo(detalle.getPrimeraCondicion(Sesion.condicionesCombo).getCodPromocion());
				//for(int i=0;i<cantidad;i++){
					PromocionRegistrada pr = new PromocionRegistrada("",cv.getCodPromocion(),p.getCodProducto(), detalle.getCantidad());
					Venta.promocionesRegistradas.add(pr);
				//}
			}
			
			if(detalle.contieneAlgunaCondicion(condicionesProductoGratis)){
				Producto p = detalle.getProducto();
				CondicionVenta cv = detalle.getPrimeraCondicion(condicionesProductoGratis);
				//Promocion promocionAplicada = PromocionExt.getPromocionProductoGratis();
				//for(int i=0;i<cantidad;i++){
					PromocionRegistrada pr = new PromocionRegistrada("",cv.getCodPromocion(),p.getCodProducto(), detalle.getCantidad());
					Venta.promocionesRegistradas.add(pr);
				//}
			}
			
			if(detalle.contieneAlgunaCondicion(condicionesDescuentoEnProducto)){
				Producto p = detalle.getProducto();
				Vector<PromocionExt> promociones = PromocionExt.getAhorrosEnCompraPorProducto(
						p.getCodProducto(), 
						detalle.getPrimeraCondicion(condicionesDescuentoEnProducto).getCodPromocion(),
						venta.consultarMontoTrans());
				if(promociones.size()!=0){
					//Promocion promocionAplicada = (PromocionExt)promociones.elementAt(0);
					CondicionVenta cv =  detalle.getPrimeraCondicion(condicionesDescuentoEnProducto);
					//for(int i=0;i<cantidad;i++){
						PromocionRegistrada pr = new PromocionRegistrada("",cv.getCodPromocion(),p.getCodProducto(), detalle.getCantidad());
						Venta.promocionesRegistradas.add(pr);
					//}
				}
			}
		}
	}

	
	/**
	*  (non-Javadoc)
    *
    * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#ejecutarProductoGratis()
    * */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void ejecutarProductoGratis(Venta venta){
		PromocionExt promocionProductoGratis = PromocionExt.getPromocionProductoGratis();
		Vector<String> condicionEmpaque = new Vector<String>();
		condicionEmpaque.addElement(Sesion.condicionEmpaque);
		if(promocionProductoGratis!=null && promocionProductoGratis.getMontoMinimo()<=venta.consultarMontoTrans()){
			float cantidad = 0;
			for(int i=0;i<venta.getDetallesTransaccion().size();i++){
				cantidad +=((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCantidad();
			}
			if(cantidad>=promocionProductoGratis.getCantMinima()){
				
				venta.setPromocionProductoGratis(promocionProductoGratis);
				//Obtener el producto que tiene precio final mas barato
				double menorPrecio = Double.MAX_VALUE;
				DetalleTransaccion dt = null;
				for(int i=0;i<venta.getDetallesTransaccion().size();i++){
					DetalleTransaccion detalle = (DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i);
					if(detalle.getPrecioFinal()<menorPrecio && 
							!detalle.contieneAlgunaCondicion(Sesion.condicionesCombo) &&
							!detalle.contieneAlgunaCondicion(condicionEmpaque)){
						menorPrecio =  detalle.getPrecioFinal();
						dt = detalle;
					}
				}
				if(dt!=null && promocionProductoGratis!=null){
					MensajesVentanas.aviso("Promoción "+promocionProductoGratis.getNombrePromocion()+": \n" +
							"El producto "+dt.getProducto().getCodProducto()+" se descuenta en un "+promocionProductoGratis.getPorcentajeDescuento()+"%");
					
				}
				//Actualizar el producto de menor precio
				/*try{
					actualizarPreciosExt(dt.getProducto(), venta);
					venta.actualizarMontoTransaccion();
				} catch (Exception e){
					e.printStackTrace();
					logger.debug("ejecutarProductoGratis()", e);
				}*/
			}
		}
	}

	/**
	 * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#deshacerProductoGratis()
	 */
	public void deshacerProductoGratis(DetalleTransaccion detalleDeshacer){
		UtilesActualizadorPrecios.deshacerProductoGratis(detalleDeshacer);
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en 'HashMap'
	* Fecha: agosto 2011
	*/
	public void syncTransaccionesExt(int numTransaccion, 
			Statement loteSentenciasSrv, Statement loteSentenciasCR, 
			HashMap<String,Object> criterioClave) {
		// TODO Apéndice de método generado automáticamente
		try {
			BeansSincronizador.syncTransaccionesExt(numTransaccion, 
					loteSentenciasSrv, loteSentenciasCR, criterioClave);
		} catch (BaseDeDatosExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (ConexionExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
	}
	
	/**
	 * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#abrirVentanaCondicionesVenta()
	 */
	public void abrirVentanaCondicionesVenta(Vector<CondicionVenta> condiciones){
		PantallaCondicionesVenta pcv = new PantallaCondicionesVenta(condiciones);
		MensajesVentanas.centrarVentanaDialogo(pcv);
	}
	
	/**
	 * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#getDonacionesVenta()
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Iterator'
	* Fecha: agosto 2011
	*/
	public double getDonacionesVenta(){
		Iterator<DonacionRegistrada> i = Venta.donacionesRegistradas.iterator();
		double total=0;
		while(i.hasNext()) total+=((DonacionRegistrada)i.next()).getMontoDonado();
		return total;
	}
	
	/**
	 * Inicializa el manejador de reportes tomado de lo definido en las
	 * extensiones a utilizar
	 * @author jgraterol
	 * **/
	/*private void iniciarManejadorReportes(){
		if (logger.isDebugEnabled()) {
			logger.debug("iniciarBuscador() - start");
		}

		ManejadorReportesPromocionesFactory  factory = new ManejadorReportesPromocionesFactory();
		manejadorReportes = factory.getInstance();		
		if (logger.isDebugEnabled()) {
			logger.debug("iniciarBuscador() - end");
		}	
	}*/
	
	/**
    *  (non-Javadoc)
    *
    * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#agregarDeleteTransaccionCondiciones()
    * */
	public void agregarInsertTransaccionCondiciones(Venta venta, Statement loteSentenciasCR, String fechaServString, String sentenciaSQL, int i) throws SQLException {
		if(((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCondicionesVentaPromociones().size()==0){
			sentenciaSQL = "insert into detalletransaccioncondicion (numtienda, fecha, numcajainicia, numregcajainicia, codproducto, condicionventa, correlativoitem, codcondicionventa, nombrePromocion, porcentajeDescuento, codpromocion, montoDctoCombo,numtransaccion, prioridadPromocion) " +
			"values ( "+venta.getCodTienda()+", " +
					"'"+fechaServString+"'," +
					venta.getNumCajaInicia()+"," +
					venta.getNumRegCajaInicia()+"," +
					"'" + ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getProducto().getCodProducto() + "', " +
					"'" + ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCondicionVenta() + "'," +
					(i+1)+", " +
					"'N'," +
					"'Normal'," +
					"0.00," +
					"0," +
					"0.00," +
					venta.getNumTransaccion()+"," +
					"0)";
			loteSentenciasCR.addBatch(sentenciaSQL);
		} else {
			for(int j=0;j<((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCondicionesVentaPromociones().size();j++){
				CondicionVenta condicion = (CondicionVenta)((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCondicionesVentaPromociones().elementAt(j);
				double montoDescuentoCombo = 0.0;
				if(condicion.getCondicion().equalsIgnoreCase(Sesion.condicionDescuentoEnCombo))
					montoDescuentoCombo = ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getMontoDctoCombo();
				sentenciaSQL = "insert into detalletransaccioncondicion (numtienda, fecha, numcajainicia, numregcajainicia, codproducto, condicionventa, correlativoitem, codcondicionventa, nombrePromocion, porcentajeDescuento, codpromocion, montoDctoCombo,numtransaccion, prioridadPromocion) " +
						"values ( "+venta.getCodTienda()+", " +
								"'"+fechaServString+"'," +
								venta.getNumCajaInicia()+"," +
								venta.getNumRegCajaInicia()+"," +
								"'" + ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getProducto().getCodProducto() + "', " +
								"'" + ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCondicionVenta() + "'," +
								(i+1)+", " +
								"'"+condicion.getCondicion()+"'," +
								"'"+condicion.getNombrePromocion()+"'," +
								condicion.getPorcentajeDescuento()+"," +
								condicion.getCodPromocion()+"," +
								montoDescuentoCombo+"," +
								venta.getNumTransaccion()+"," +
								condicion.getPrioridadPromocion()+")";
				loteSentenciasCR.addBatch(sentenciaSQL);
			}
		}
	}
	
	/**
    *  (non-Javadoc)
    *
    * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#agregarDeleteTransaccionCondiciones(Apartado ap, Statement loteSentenciasCR)
    * */
	public void agregarDeleteTransaccionCondiciones(Venta venta, Statement loteSentenciasCR) throws SQLException {
		String sentencia;
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
		sentencia = "delete from detalletransaccioncondicion "+
					"where numtienda = " + venta.getCodTienda() + " and " +
					"fecha = '" + df2.format(venta.getFechaTrans()) + "' and " +
					"numcajainicia = " + venta.getNumCajaInicia() + " and " +
					"numregcajainicia = " + venta.getNumRegCajaInicia();
		loteSentenciasCR.addBatch(sentencia);
	}
	
	/**
    *  (non-Javadoc)
    *
    * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#agregarDeleteTransaccionCondiciones(Apartado ap, Statement loteSentenciasCR)
    * */
	public void agregarDeleteTransaccionCondiciones(String identificacionEspera, Statement loteSentenciasCR) throws SQLException {
		String sentencia;
		/*
		* Modificaciones referentes a la migración a MySQL 5.5 por jperez
		* Sólo se cambió 'delete from detalletransaccioncondicion' por 'delete from dtc'
		* Fecha: septiembre 2011
		*/
		sentencia = "delete from dtc using " +
					"detalletransaccioncondicion dtc inner join transaccion t on (" +
						"t.numtienda = dtc.numtienda and " +
						"t.fecha = dtc.fecha and " +
						"t.numcajainicia = dtc.numcajainicia and " +
						"t.numregcajainicia = dtc.numregcajainicia)  "+
					"where t.codigofacturaespera = '"+ identificacionEspera+"' ";
		loteSentenciasCR.addBatch(sentencia);
	}

	/**
    *  (non-Javadoc)
    *
    * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#cargarPromocionesCombo()
    * */
	public void cargarPromocionesCombo() {
		PromocionExt.getPromocionesCombo(null, 0);
	}

	/**
	 * Imprime los voucher correspondientes a las formas de pago BECO
	 * @param cte
	 * @param fPago
	 * @param monto
	 * @param numTrans
	 */
	public void imprimirFormasDePagoEspeciales(Cliente cte, FormaDePago fPago, double monto , int numTrans ) {
		(new ManejadorReportesPromocionesFactory()).getInstance().imprimirVoucherPago(cte, fPago, monto, numTrans);		
	}

	/**
	 * Chequea las condiciones de venta de los detalles de la transaccion
	 * y decide si es necesario agregar un pago especial
	 * (Mercadeo, Merchandising, etc)
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void agregarPagosEspeciales(Venta venta2) {
		Venta venta = venta2;
		Vector<String> condicionesProductoGratis = new Vector<String>();
		condicionesProductoGratis.addElement(Sesion.condicionProductoGratis);
		Vector<String> condicionDescontadoPorCombo = new Vector<String>();
		condicionDescontadoPorCombo.addElement(Sesion.condicionDescontadoPorCombo);
		double montoMerchandising=0;
		double montoMercadeo=0;
		Vector<Vector<Object>> detallesModificados= new Vector<Vector<Object>>();
		if(venta!=null){
			Vector<DetalleTransaccion> detallesTransaccion = venta.getDetallesTransaccion();
			for(int i=0;i<detallesTransaccion.size();i++){
				DetalleTransaccion dt = (DetalleTransaccion)detallesTransaccion.elementAt(i);
				if(dt.contieneAlgunaCondicion(condicionesProductoGratis)){
					CondicionVenta cv = dt.getPrimeraCondicion(condicionesProductoGratis);
					//Solo aparece como forma de pago si es un 100% de descuento
					PromocionExt promocion = (PromocionExt)venta.getPromocionProductoGratis();
					if(cv.getPorcentajeDescuento()==100){
						montoMerchandising+=(((dt.getPrecioFinal()*dt.getCantidad())+dt.getMontoImpuesto()*dt.getCantidad())*promocion.getPorcentajeDescuento()/100);
						
						Vector<Object> tuplaDetalles = new Vector<Object>();
						tuplaDetalles.addElement(dt);
						tuplaDetalles.addElement(new Double(cv.getPorcentajeDescuento()));
						detallesModificados.addElement(tuplaDetalles);
					} else {
						dt.setPrecioFinal(dt.getPrecioFinal()-(dt.getPrecioFinal()*promocion.getPorcentajeDescuento()/100));
						dt.setMontoImpuesto(venta.calcularImpuesto(dt.getProducto(), dt.getPrecioFinal()));
						venta.actualizarMontoTransaccion();
						
						Vector<Object> tuplaDetalles = new Vector<Object>();
						tuplaDetalles.addElement(dt);
						tuplaDetalles.addElement(new Double(promocion.getPorcentajeDescuento()));
						detallesModificados.addElement(tuplaDetalles);
					}
				} else {
					if(dt.contieneAlgunaCondicion(condicionDescontadoPorCombo)){
						Vector<String> condicionesPrecioCero = new Vector<String>();
						condicionesPrecioCero.addElement(Sesion.condicionComboPorCantidades);
						CondicionVenta cv = dt.getPrimeraCondicion(condicionesPrecioCero);
						double porcentajeDescuento = cv.getPorcentajeDescuento();
						if(porcentajeDescuento==100){
							montoMerchandising+=(((dt.getPrecioFinal()*dt.getCantidad())+dt.getMontoImpuesto()*dt.getCantidad())*porcentajeDescuento/100);

							Vector<Object> tuplaDetalles = new Vector<Object>();
							tuplaDetalles.addElement(dt);
							tuplaDetalles.addElement(new Double(porcentajeDescuento));
							detallesModificados.addElement(tuplaDetalles);
						} else{
							dt.setPrecioFinal(dt.getPrecioFinal()-(dt.getPrecioFinal()*porcentajeDescuento/100));
							dt.setMontoImpuesto(venta.calcularImpuesto(dt.getProducto(), dt.getPrecioFinal()));
							venta.actualizarMontoTransaccion();

							Vector<Object> tuplaDetalles = new Vector<Object>();
							tuplaDetalles.addElement(dt);
							tuplaDetalles.addElement(new Double(porcentajeDescuento));
							detallesModificados.addElement(tuplaDetalles);
						}
					}				
				}
			}
		}
		if(detallesModificados.size()!=0){
			PantallaDetallesCambiados pantallaAviso = new PantallaDetallesCambiados(detallesModificados);
			MensajesVentanas.centrarVentanaDialogo(pantallaAviso);
		}
		try{
			if(montoMercadeo!=0){
				FormaDePago f = new FormaDePago(Sesion.FORMA_PAGO_PROMO_MERCADEO,
						Sesion.TIPO_PAGO_BECO,
						BaseDeDatosVenta.obtenerNombFormaPago(Sesion.FORMA_PAGO_PROMO_MERCADEO),
						null,
						false,false,false,false,false,false,false,false,0,0,0,false,false);
				Pago pago = new Pago(f,MathUtil.roundDouble(montoMercadeo),null,null,null,0,0,null);
				venta.getPagos().add(pago);
			}
			if(montoMerchandising!=0){
				FormaDePago f = new FormaDePago(Sesion.FORMA_PAGO_PROMO_MERCHANDISING,
						Sesion.TIPO_PAGO_BECO,
						BaseDeDatosVenta.obtenerNombFormaPago(Sesion.FORMA_PAGO_PROMO_MERCHANDISING),
						null,
						false,false,false,false,false,false,false,false,0,0,0,false,false);
				Pago pago = new Pago(f,MathUtil.roundDouble(montoMerchandising),null,null,null,0,0,null);
				venta.getPagos().add(pago);
			}
		} catch(Exception e){
			logger.error("agregarPagosEspeciales()", e);
		}
	}

	/**
	 * Elimina los pagos especiales (Promoción mercadeo y promoción merchandising) 
	 * del listado de pagos realizados
	 */
	public void eliminarPagosEspeciales() {
		Venta venta =  CR.meVenta.getVenta();
		if(venta!=null){
			for(int i=0;i<venta.getPagos().size();i++){
				Pago p = (Pago)venta.getPagos().elementAt(i);
				if(p.getFormaPago().getTipo()==Sesion.TIPO_PAGO_BECO)
					venta.getPagos().set(i, null);
			}
			while(venta.getPagos().remove(null));
		}
	}
	
	/**
	 * Obtiene las donaciones registradas en una determinada transacción
	 * @param codTienda
	 * @param fechaTrans
	 * @param numCaja
	 * @param numTransaccion
	 * @param local indica si la consulta se realiza en la caja o en el servidor
	 * @return
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<DonacionRegistrada> consultarDonacionesPorVenta(int codTienda, String fechaTrans, int numCaja, int numTransaccion, boolean local){
		return DonacionRegistrada.consultarDonacionesPorVenta(codTienda, fechaTrans, numCaja, numTransaccion, local);
	}
	
	/**
	 * Obtiene las promociones registradas en una determinada transacción
	 * @param codTienda
	 * @param fechaTrans
	 * @param numCaja
	 * @param numTransaccion
	 * @param local indica si la consulta se realiza en la caja o en el servidor
	 * @return
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<PromocionRegistrada> consultarPromocionesPorVenta(int codTienda, String fechaTrans, int numCaja, int numTransaccion, boolean local){
		return PromocionRegistrada.consultarPromocionesPorVenta(codTienda, fechaTrans, numCaja, numTransaccion, local);
	}
	
	/**
	 * Obtiene las promociones registradas en una determinada transacción
	 * @param codTienda
	 * @param fechaTrans
	 * @param numCaja
	 * @param numTransaccion
	 * @param local indica si la consulta se realiza en la caja o en el servidor
	 * @return
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<RegaloRegistrado> consultarRegalosPorVenta(int codTienda, String fechaTrans, int numCaja, int numTransaccion, boolean local){
		return RegaloRegistrado.consultarRegalosPorVenta(codTienda, fechaTrans, numCaja, numTransaccion, local);
	}
	
	/**
	 * Al anular una factura es necesario ingresar donaciones en negativo 
	 * por los mismos montos de la venta original
	 *
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void cancelarDonacionesAnulacion(){
		Vector<DonacionRegistrada> donacionesAnuladas = new Vector<DonacionRegistrada>();
		for(int i=0;i<Venta.donacionesRegistradas.size();i++){
			DonacionRegistrada dr = (DonacionRegistrada)Venta.donacionesRegistradas.elementAt(i);
			DonacionRegistrada donacionNegativa = (DonacionRegistrada)dr.clone();
			donacionNegativa.setMontoDonado(dr.getMontoDonado()*-1);
			donacionesAnuladas.addElement(donacionNegativa);
		}
		Anulacion.donacionesRegistradas = donacionesAnuladas;
	}
	
	/**
	 * Agrega a la anulacion el grupo de donaciones canceladas
	 * @param anulacion
	 * @param loteSentenciasCR
	 * @param fechaTransString
	 * @throws SQLException
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' e 'Iterator'
	* Fecha: agosto 2011
	*/
	public void agregarDonacionesCanceladas(Anulacion anulacion, Statement loteSentenciasCR, String fechaTransString) throws SQLException {
		String sentenciaSQL;
		Iterator<DonacionRegistrada> i = Anulacion.donacionesRegistradas.iterator();
		anulacion.getVentaOriginal().asignaPagosClonados(anulacion.getVentaOriginal());
		int codigo = 0;
		while(i.hasNext()) {
			codigo++;
			DonacionRegistrada d = (DonacionRegistrada)i.next();
			sentenciaSQL = "insert into donacionesregistradas (montoDonado,fechaDonacion,numTransaccion,numCaja,numTienda,codigoDonacion,codigo) values ("+
			d.getMontoDonado()+",'"+fechaTransString+"',"+anulacion.getNumTransaccion()+","+anulacion.getNumCajaFinaliza()+","+anulacion.getCodTienda()+","+d.getCodigoDonacion()+","+codigo+")";
			loteSentenciasCR.addBatch(sentenciaSQL);
			d.asignarFormasPago(anulacion.getVentaOriginal());
			Iterator<PagoDonacion> j = d.getFormasPago().iterator();
			int codigoPago = 0;
			while (j.hasNext()){
				codigoPago++;
				PagoDonacion pd = (PagoDonacion)j.next();
				sentenciaSQL = "insert into pagodonacion (fechaDonacion,numTransaccion,numCaja,numTienda,codigoDonacion,codigoDonacionRegistrada,codigoFormaPago,monto,codigoPago) values ('"+ 
				fechaTransString+"',"+anulacion.getNumTransaccion()+","+anulacion.getNumCajaFinaliza()+","+anulacion.getCodTienda()+","+d.getCodigoDonacion()+","+codigo+",'"+pd.getFormaPago().getCodigo()+"',"+pd.getMonto()+","+codigoPago+")";
				loteSentenciasCR.addBatch(sentenciaSQL);
			}
		}
	}

	public Vector<DonacionRegistrada> getDonacionesRegistradasTipo1(Vector<DonacionRegistrada> donaciones) {
		return UtilesActualizadorPrecios.getDonacionesRegistradasTipo1(donaciones);
	}

	/**
	  * Agrega la información del detalle indicado en numDetalle al vector de ProductoCombo
	  * de la promoción indicada por codPromocion
	  */
	 public boolean recalcularProductosCombo(int codPromocion, int numDetalle,Vector<ProductoCombo> productos){
		 return PromocionExt.recalcularProductosCombo(codPromocion, numDetalle,productos);
	 }

	 //BECO: WDIAZ 07-2012
	 public void crearTablaPromoTicket()
	 {
		 PromocionExt.crearTablaPromoTicket();
	 }
//jperez, funcion utilizada en transferencia inmediata
	 public void actualizarTablaPromoTicket(int codPromocion){
		 PromocionExt.actualizarTablaPromoTicket(codPromocion);
	 }

	public ArrayList<Object> getPromoSorteoInfo(String clave) {
		ArrayList<Object> info = new ArrayList<Object>();
		PromocionExt valor = (PromocionExt)CR.me.getPromoMontoCantidad().get(clave);
		String name = valor.getNombrePromocion();
		if(name.length()>30)name = valor.getNombrePromocion().substring(0, 30);
		Double monto = new Double(valor.getSumMontoTotal());
		Double montoMinimo = new Double(valor.getMontoMinimo());
		char x = valor.getTipoPromocion();

		if(monto.doubleValue() >= montoMinimo.doubleValue() && x == Sesion.TIPO_PROMOCION_REGALO_CUPON){
			info.add(name);
			info.add(monto);
		}
		return info;
	}

	@Override
	public boolean recalcularAhorroEnCompra(int codPromocion, int numDetalle,Vector<Producto> productosAhorroAux) {
		// TODO Auto-generated method stub
		return PromocionExt.recalcularAhorroEnCompra(codPromocion, numDetalle,productosAhorroAux);
	}
	
}
