package com.beco.cr.actualizadorPrecios;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.beco.cr.actualizadorPrecios.promociones.ComparadorPromociones;
import com.beco.cr.actualizadorPrecios.promociones.Detector;
import com.beco.cr.actualizadorPrecios.promociones.Donacion;
import com.beco.cr.actualizadorPrecios.promociones.DonacionRegistrada;
import com.beco.cr.actualizadorPrecios.promociones.PromocionExt;
import com.beco.cr.actualizadorPrecios.promociones.ManejadorBD.DonacionBD;
import com.beco.cr.actualizadorPrecios.promociones.ManejadorBD.PromocionExtBD;
import com.beco.cr.actualizadorPrecios.promociones.ManejadorBD.UtilesBD;
import com.beco.cr.actualizadorPrecios.promociones.interfaz.MenuPromociones;
import com.beco.cr.actualizadorPrecios.promociones.interfaz.VentanaDonacion;
import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.extensiones.ActualizadorPreciosSAC;
import com.becoblohm.cr.manejadorauditoria.Auditoria;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarservicio.Apartado;
import com.becoblohm.cr.manejarservicio.DetalleServicio;
import com.becoblohm.cr.manejarservicio.Servicio;
import com.becoblohm.cr.manejarventa.CondicionVenta;
import com.becoblohm.cr.manejarventa.Detalle;
import com.becoblohm.cr.manejarventa.Devolucion;
import com.becoblohm.cr.manejarventa.Producto;
import com.becoblohm.cr.manejarventa.Promocion;
import com.becoblohm.cr.utiles.MathUtil;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.becoblohm.cr.utiles.VectorUtil;

public class BECOActualizadorPreciosSAC implements ActualizadorPreciosSAC{
	
	private static final Logger logger = Logger.getLogger(BECOActualizadorPreciosSAC.class);
	public static boolean posicionesCambiadas = false;
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void actualizarPreciosExt(Producto p, Servicio serv, boolean aplicarPromociones) 
	throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion{
		float AcumN = 0;
		float cantidadProdsConDescto = 0;
		Vector<Integer> detallesN = new Vector<Integer>(); // Vector de posiciones para los productos en condiciones normales
		Vector<Integer> detallesE = new Vector<Integer>(); // Vector de posiciones para los productos en condición de empaque
		
		Vector<String> condicionesEmpaque = new Vector<String>();
		condicionesEmpaque.add(Sesion.condicionEmpaque);
		
		Vector<Vector<Integer>> nuevosDetalles;
		Vector<DetalleServicio> detallesCreados = new Vector<DetalleServicio>();
		//Detalles sobre los cuales es necesario ejecutar el algoritmo de nuevo
		Vector<Detalle> detallesAActualizar = new Vector<Detalle>();
		//Vector de detalles que quedaron con cero items
		serv.setDetallesAEliminar(new Vector());
		
		boolean cantidadCambiada = false;
		
		// Se acumulan los detalles que tiene condición 'N' y 'E' en sus respectivas variables
		for (int i=0; i<serv.getDetallesServicio().size();i++) {
			Vector<String> condicionCorporativo = new Vector<String>();
			condicionCorporativo.addElement(Sesion.condicionDescuentoCorporativo);
			
			if (((((DetalleServicio) serv.getDetallesServicio().elementAt(i)).getProducto()).getCodProducto()).equals(p.getCodProducto())) {
				if((((DetalleServicio) serv.getDetallesServicio().elementAt(i))).contieneAlgunaCondicion(condicionCorporativo)) {
					DetalleServicio detalle = (((DetalleServicio) serv.getDetallesServicio().elementAt(i)));
					CondicionVenta cv = ((DetalleServicio)serv.getDetallesServicio().elementAt(i)).getPrimeraCondicion(condicionCorporativo);
					PromocionExt promo = PromocionExt.getPromocionCorporativaPorCodigo(cv.getCodPromocion());
					detalle.setPrecioFinal(MathUtil.roundDouble((detalle.getPrecioFinal()*100)/(100-promo.getPorcentajeDescuento())));
					detalle.setMontoImpuesto(serv.calcularImpuesto(detalle.getProducto(), detalle.getPrecioFinal()));
					detalle.removeCondicion(new CondicionVenta(Sesion.condicionDescuentoCorporativo));
					detalle.setCondicionVenta(((DetalleServicio)serv.getDetallesServicio().elementAt(i)).construirCondicionVentaString());
				}
				if ((((DetalleServicio)serv.getDetallesServicio().elementAt(i)).contieneAlgunaCondicion(condicionesEmpaque))) {
					//Si es alguna de las posibilidades de empaque
					cantidadProdsConDescto = (((DetalleServicio) serv.getDetallesServicio().elementAt(i)).getCantidad() - (((DetalleServicio) serv.getDetallesServicio().elementAt(i)).getCantidad() % p.getCantidadVentaEmpaque()));
					if (cantidadProdsConDescto == ((DetalleServicio) serv.getDetallesServicio().elementAt(i)).getCantidad()){
						detallesE.addElement(new Integer(i));
					} else {
						if(!((DetalleServicio) serv.getDetallesServicio().elementAt(i)).contieneAlgunaCondicion(Sesion.condicionesCombo)){
							((DetalleServicio) serv.getDetallesServicio().elementAt(i)).setCondicionVentaPromociones(new Vector<CondicionVenta>());
							((DetalleServicio) serv.getDetallesServicio().elementAt(i)).setCondicionVenta(Sesion.condicionNormal);
							if(!VectorUtil.contieneInt(detallesN, i)){
								AcumN += ((DetalleServicio) serv.getDetallesServicio().elementAt(i)).getCantidad();
								detallesN.addElement(new Integer(i));
							}
						}
					}
				} else if(((DetalleServicio)serv.getDetallesServicio().elementAt(i)).contieneAlgunaCondicion(Sesion.condicionesCombo)){			
					if(((DetalleServicio)serv.getDetallesServicio().elementAt(i)).isCantidadCambiada()){
						cantidadCambiada = true;
						float variacion = ((DetalleServicio)serv.getDetallesServicio().elementAt(i)).getVariacion();
						if(variacion>0){
							try{
								//Disminuyo del detalle la variacion
								((DetalleServicio)serv.getDetallesServicio().elementAt(i)).setCantidad(((DetalleServicio)serv.getDetallesServicio().elementAt(i)).getCantidad()-variacion);
								((DetalleServicio)serv.getDetallesServicio().elementAt(i)).setCantidadCambiada(false);
								((DetalleServicio)serv.getDetallesServicio().elementAt(i)).setVariacion(0);
								
								//Si estoy agregando productos, como ya pertenece a un combo, hay que crear un detalle nuevo
								String tipoCaptura = Sesion.PROCESO+"";
								String codSup = ((DetalleServicio)serv.getDetallesServicio().elementAt(i)).getCodSupervisor();
								DetalleServicio detalleAgregado = 
									new DetalleServicio(
										((DetalleServicio)serv.getDetallesServicio().elementAt(i)).getProducto(),
										variacion,
										Sesion.condicionNormal,
										codSup,
										((DetalleServicio)serv.getDetallesServicio().elementAt(i)).getProducto().getPrecioRegular(),
										serv.calcularImpuesto(((DetalleServicio)serv.getDetallesServicio().elementAt(i)).getProducto(), ((DetalleServicio)serv.getDetallesServicio().elementAt(i)).getPrecioFinal()),
										tipoCaptura , 
										0, 
										"");
								detallesCreados.addElement(detalleAgregado);
							} catch(Exception e){
								logger.debug("actualizarPreciosExt(Producto p, Venta venta) ", e);
							}
						} else if(variacion <=0) { //Productos eliminados del combo
							DetalleServicio detalleActual = (DetalleServicio)serv.getDetallesServicio().elementAt(i);
							UtilesActualizadorPrecios.deshacerCombo(new Vector<Integer>() /*borrar*/, detallesAActualizar, variacion, detalleActual, null);
							if(!VectorUtil.contieneInt(detallesN, i)){
								detallesN.addElement(new Integer(i));
							}
						}
					} else {
						if(!VectorUtil.contieneInt(detallesN, i)){
							AcumN+=((DetalleServicio) serv.getDetallesServicio().elementAt(i)).getCantidad();
							detallesN.addElement(new Integer(i));
						}
					}
				} else if (!((DetalleServicio) serv.getDetallesServicio().elementAt(i)).isCondicionEspecial() && !VectorUtil.contieneInt(detallesN, i)) {
					//Si no tiene ninguna rebaja
					AcumN += ((DetalleServicio) serv.getDetallesServicio().elementAt(i)).getCantidad();
					detallesN.addElement(new Integer(i));
				} else {
					// Aquí se chequean los detalles que son de tipo desct por defecto y se les recalcula el impuesto 
					((DetalleServicio)serv.getDetallesServicio().elementAt(i)).setMontoImpuesto(serv.calcularImpuesto(p,((DetalleServicio) serv.getDetallesServicio().elementAt(i)).getPrecioFinal()));
				}
			} 
		}
		//Los detalles nuevos que fueron creados en el ciclo anterior se agregan al final del vector de detalles transaccion
		for(int i=0;i<detallesCreados.size();i++){
			serv.getDetallesServicio().addElement((DetalleServicio)detallesCreados.elementAt(i));
			detallesN.addElement(new Integer(serv.getDetallesServicio().size()-1));
			AcumN+=((DetalleServicio)detallesCreados.elementAt(i)).getCantidad();
		}
		
		// Se evalúan y aplican las condiciones de venta a los detalles afectados.
		if(aplicarPromociones){
			if (detallesN.size() > 0 || cantidadCambiada || serv.getDescuentoCorporativoAplicado()!=0){
				nuevosDetalles = aplicarCondicionDeVenta(p, detallesN, AcumN, serv);
				Vector<Integer> nuevoDetallesN = ( nuevosDetalles.elementAt(0));
				Vector<Integer> nuevosEmpaques = ( nuevosDetalles.elementAt(1));
				Vector<Integer> nuevosCombos = (nuevosDetalles.elementAt(2));
				
				detallesE = nuevosEmpaques;
				
				
				// Se acumulan los detalles que se encuentran en condición de empaque
				UtilesActualizadorPrecios.acumularDetallesPromociones(detallesE, false);
				
				// Se acumulan los detalles que se encuentran en la condición distinta a la de empaque y se colocan al final del vector original
				UtilesActualizadorPrecios.acumularDetallesPromociones(nuevoDetallesN, false);
				
				UtilesActualizadorPrecios.acumularDetallesPromociones(nuevosCombos, false);
				
				
				// Se eliminan los detalles que quedaron con cantidad cero
				for(int i=0;i<serv.getDetallesServicio().size();i++){
					try{
						DetalleServicio dt = ((DetalleServicio)serv.getDetallesServicio().elementAt(i));
						if (dt.getCantidad() <= 0) {
							serv.getDetallesServicio().set(i, null);
						}
					}catch(Exception e){
						//El detalle ya fue eliminado, no deberia ocurrir
					}
				}
			
				// Se eliminan los elementos nulos que quedan en el vector de detalles original
				while (serv.getDetallesServicio().removeElement(null));
				
				// Limpiamos los vectores de posiciones
				detallesN.clear();
				nuevoDetallesN.clear();
				detallesE.clear();
				
			}
			//Llama recursivamente al algoritmo de actualizacion de precios sobre los detalles afectados por los combos
			//En los que participaba el producto prod
			for(int i=0;i<detallesAActualizar.size();i++){
				if(!((DetalleServicio)detallesAActualizar.elementAt(i)).contieneAlgunaCondicion(Sesion.condicionesCombo) || !Sesion.promocionesAcumulativas){
					BECOActualizadorPreciosSAC.posicionesCambiadas = true;
					actualizarPreciosExt(((DetalleServicio)detallesAActualizar.elementAt(i)).getProducto(), serv, aplicarPromociones);
				}
			}
			if(BECOActualizadorPreciosSAC.posicionesCambiadas && detallesAActualizar.size()!=0)
				BECOActualizadorPreciosSAC.posicionesCambiadas=false;
		} else {
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
	@SuppressWarnings({ "unchecked"})
	public Vector<Vector<Integer>> aplicarCondicionDeVenta(Producto prod, Vector<Integer> detallesN, float cantidad, Servicio serv) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
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
		Vector<Vector<Object>> descuentos = buscarCondicionesDeVenta(prod, serv);
		
		// Definimos las variables necesarias para la creacion de los nuevos detalles
		String supervisor; 
		String tipCaptura; 
		double pFinal;
		float cantidadAsignar;
		
		boolean corporativosParaConcurso = true;
		
		// Verificamos si aplican condiciones de venta al producto, de no ser así se colocan como normales en los detalles
		if ((descuentos.size() > 0)) {
			
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
				Vector<Object> datosNuevosEmpaques = UtilesActualizadorPrecios.aplicarCondicionEmpaque(prod, detallesN, prodsEmpaque, posicionMejor, dctoActual, serv);
				
				nuevosEmpaques = (Vector<Integer>)datosNuevosEmpaques.elementAt(0);
				
				//a cuantos detalles de transaccion les he aplicado sus promociones
				puntoDeChequeo = ((Integer)datosNuevosEmpaques.elementAt(1)).intValue();
					
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
					if(BECOActualizadorPreciosSAC.posicionesCambiadas){
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
					/*&& corporativosParaConcurso*/)) {
				while (puntoDeChequeo < detallesN.size()) {
					DetalleServicio nuevoDetalleTrans;
					cantidadAsignar = ((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCantidad();
					supervisor = ((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCodSupervisor();
					tipCaptura = ((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getTipoCaptura();
					dctoActual = descuentos.elementAt(posicionMejor);
					pFinal = ((Double)dctoActual.elementAt(0)).doubleValue();
					if ((((CondicionVenta)(descuentos.elementAt(posicionMejor)).elementAt(3)).getCondicion()).equals(Sesion.condicionPromocion)) {
						// Si se trata de una promocion
						nuevoDetalleTrans = UtilesActualizadorPrecios.aplicarCondicionPromocion (prod, detallesN, dctoActual, puntoDeChequeo, serv);
						nuevoDetalleTrans.setPrecioOriginal(nuevoDetalleTrans.getPrecioFinal());
						serv.getDetallesServicio().addElement(nuevoDetalleTrans);
						nuevosNoEmpaques.addElement(new Integer(serv.getDetallesServicio().size() - 1));
						cantidad -= cantidadAsignar;
						puntoDeChequeo++;
					} else if ((((CondicionVenta)(descuentos.elementAt(posicionMejor)).elementAt(3)).getCondicion()).equals(Sesion.condicionDescuentoCorporativo)) {
						if(corporativosParaConcurso){
						// Aplicar descuento corporativo a los productos que no estan en empaque (detallesN)
							nuevoDetalleTrans = (DetalleServicio)UtilesActualizadorPrecios.aplicarCondicionDescuentoCorporativo(prod, detallesN, dctoActual, puntoDeChequeo);
							serv.getDetallesServicio().addElement(nuevoDetalleTrans);
							nuevosNoEmpaques.addElement(new Integer(serv.getDetallesServicio().size() - 1));
						}
						cantidad -= cantidadAsignar;
						puntoDeChequeo++;
					} else if ((((CondicionVenta)(descuentos.elementAt(posicionMejor)).elementAt(3)).getCondicion()).equals(Sesion.condicionDesctoEmpleado)) {
						cantidad -= cantidadAsignar;
						puntoDeChequeo++;
					} 
				}
			}
		}
		
		// Colocamos las condiciones de venta normales (Si existen)
		if (cantidad > 0) {
			while (puntoDeChequeo < detallesN.size()) {
				supervisor = ((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCodSupervisor();
				tipCaptura = ((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getTipoCaptura();
				pFinal = ((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getPrecioFinal();
				cantidadAsignar = ((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCantidad();
				String condicion = ((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCondicionVenta();
				
				Vector<CondicionVenta> condicionVentaPromociones = ((DetalleServicio)((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).clone()).getCondicionesVentaPromociones();
				
				pFinal = MathUtil.roundDouble(pFinal);
				if(condicion==null || condicion.equalsIgnoreCase("") || condicionVentaPromociones.size()==0){
					pFinal = ((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getProducto().getPrecioRegular();
					condicion = Sesion.condicionNormal;
				}
				DetalleServicio nuevoDetalleTrans = 
					new DetalleServicio(
						((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getProducto(),
						cantidadAsignar,
						condicion,
						supervisor,
						pFinal,
						serv.calcularImpuesto(
								((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getProducto(), 
								((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getPrecioFinal()),
						tipCaptura , 
						0, 
						"");
				nuevoDetalleTrans.setCondicionVentaPromociones(condicionVentaPromociones);
				serv.getDetallesServicio().addElement(nuevoDetalleTrans);
				
				//Marco el detalle anterior como sucio
				((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).setCantidad(0);
				((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).setVariacion(0);
				((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).setCantidadCambiada(false);
				
				/*if(nuevoDetalleTrans.contieneAlgunaCondicion(Sesion.condicionesCombo)){
					nuevosCombos.addElement(new Integer(serv.getDetallesServicio().size() - 1));
				} else {
					
				}*/
				
				if(nuevoDetalleTrans.getCondicionesVentaPromociones().size()==0){
					nuevoDetalleTrans.setPrecioFinal(nuevoDetalleTrans.getProducto().getPrecioRegular());
					nuevosNoEmpaques.addElement(new Integer(serv.getDetallesServicio().size() - 1));
				}
				puntoDeChequeo++;
			}
		}
		
		if(Sesion.promocionesAcumulativas){
			
			Vector<Vector<Object>> datosCombos = UtilesActualizadorPrecios.verificarDatosCombo(prod);
			for(int i=0;i<datosCombos.size();i++){
				Vector<Object> dato = datosCombos.elementAt(i);
				Vector<Object> dctoActual =  UtilesActualizadorPrecios.crearDescuentoCombo(dato);
				if(((String)(dctoActual.elementAt(1))).equalsIgnoreCase(Sesion.condicionDescuentoEnCombo)){
					PromocionExt promocionAplicada = (PromocionExt)dato.elementAt(2); //(PromocionExt)(PromocionExt.getPromocionesCombo(prod, ((CondicionVenta)dctoActual.elementAt(3)).getCodPromocion())).elementAt(0);
					Vector<Integer> detallesEnCombo = new Vector<Integer>();
					while(/*cantidad>0 &&*/ Detector.verificaCantidadesMinimasCombo(promocionAplicada,new Vector<String>(), false)){
						Vector<Integer> resultado = UtilesActualizadorPrecios.aplicarCondicionDescuentoEnCombo(dctoActual, prod, false);
						detallesEnCombo.addAll(resultado);
						if(resultado.size()==0)
							break;
						else 
							nuevosCombos = detallesEnCombo;
					}
				}
				if(BECOActualizadorPreciosSAC.posicionesCambiadas){
					Vector<Vector<Integer>> nuevasPosiciones = UtilesActualizadorPrecios.actualizarPosiciones();
					nuevosEmpaques = nuevasPosiciones.elementAt(0);
					nuevosNoEmpaques = nuevasPosiciones.elementAt(1);
					nuevosCombos = nuevasPosiciones.elementAt(2);
				}
			}
			
		}
		
		if(Sesion.promocionesAcumulativas){
			if(CR.meServ.getApartado().getDescuentoCorporativoAplicado()!=0.0) {
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
			 Vector<Object> nuevosEmp = UtilesActualizadorPrecios.aplicarCondicionEmpaque(prod, nuevosNoEmpaques, serv);
			 nuevosEmpaques = (Vector<Integer>)nuevosEmp.elementAt(0);
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
	 * 				   - posición 0 posee la información del descuento promocional.
	 * 				   - posición 1 posee la información del descuento a empaque
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' y se eliminaron
	* variables sin uso
	* Fecha: agosto 2011
	*/
	private Vector<Vector<Object>> buscarCondicionesDeVenta(Producto prod, Servicio serv){
		if (logger.isDebugEnabled()) {
			logger.debug("buscarCondicionesDeVenta(Producto, Servicio serv) - start");
		}

		double dctoEmpaque = 0.0;
		//double precioEmpaque;
		Vector<Vector<Object>> descuentos = new Vector<Vector<Object>>();
		
		//true: aplicar combo de alguna promoción
		//false: aplicar empaque
		//En caso de que sea promoción se devolveran todas las existentes para que participen en el
		//concurso por prioridades en aplicarCondicionVenta
		boolean aplicarCombo = false; 
		
		Vector<Object> datosCombos = null;
		
		if(!Sesion.promocionesAcumulativas || 
				(CR.meServ.getApartado()!=null)&& CR.meServ.getApartado().getAbonos().size()!=0){
			Vector<Vector<Object>> datos = UtilesActualizadorPrecios.verificarDatosCombo(prod);
			if(datos.size()!=0)
				datosCombos = datos.elementAt(0);
		}
		
		//Empaque no puede ser aplicado junto con ningun combo, entonces verifico si
		//alguno de los combos tiene un porcentaje que convenga mas al cliente que el empaque
		
		if(datosCombos!=null && ((PromocionExt)datosCombos.elementAt(2)).getPorcentajeDescuento()>=prod.getDesctoVentaEmpaque()){
			aplicarCombo = true;
		}
		
		// Calculamos las promociones
		Vector<Object> datosPromocion = verificarPromociones(prod);
		if (datosPromocion.size() > 0) {
			// Existen promociones vigentes para el producto
			Vector<Object> promo = new Vector<Object>();
			promo.addElement((Double)datosPromocion.elementAt(1));  //PRECIO FINAL
			promo.addElement(Sesion.condicionPromocion);			//CONDICION VENTA (string)
			promo.addElement((Integer)datosPromocion.elementAt(0));	//CODIGO DE LA PROMOCION
			CondicionVenta cv = new CondicionVenta(Sesion.condicionPromocion, ((Integer)datosPromocion.elementAt(0)).intValue(),((Double)datosPromocion.elementAt(2)).doubleValue(),"Promoción básica", ((Integer)datosPromocion.elementAt(3)).intValue());
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
		
		//Nos interesa que el descuento corporativo este aplicado siempre en el vector.
		//Pero hay que tener cuidado, no participa en el concurso (promocion que mas conviene)
		//si los descuentos son acumulativos
		/*if (CR.meServ.getApartado().getDescuentoCorporativoAplicado()!=0.0) {
			Vector descuentosCorporativos = UtilesActualizadorPrecios.crearDescuentoCorporativo(prod);
			descuentos.addAll(descuentosCorporativos);
		}*/
		
		if (datosCombos!= null && datosCombos.size() > 0 && aplicarCombo && 
				(!Sesion.promocionesAcumulativas || 
						(CR.meServ.getApartado()!=null) && CR.meServ.getApartado().getAbonos().size()!=0)) {
			Vector<Object> promo = UtilesActualizadorPrecios.crearDescuentoCombo(datosCombos);
			descuentos.addElement(promo);
		}
		
		if(!Sesion.promocionesAcumulativas || 
				(CR.meServ.getApartado()!=null && CR.meServ.getApartado().getAbonos().size()!=0)){
			Vector<Object> promo = UtilesActualizadorPrecios.getDescuentosAhorroEnCompra(prod.getCodProducto());
			if(promo.size()!=0)
				descuentos.addElement(promo);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("buscarCondicionesDeVenta(Producto, Servicio serv) - end");
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
			result.addElement(new Double(promocionAplicada.getPorcentajeDescuento()));
			result.addElement(new Integer(promocionAplicada.getPrioridad()));
		}
		return result;
	}
	
	/**
     * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#actualizarPrecios(Producto p, Servicio serv, boolean aplicarPromociones)
     */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' y 'TreeMap'
	* Fecha: agosto 2011
	*/
    public void actualizarPrecios(Producto p, Servicio serv, boolean aplicarPromociones){
    	try{
	    	//SI HAY PROMOCION DE AHORRO EN COMPRA ENTONCES ES NECESARIO ACTUALIZAR TODOS LOS PRECIOS
			//if(Detector.isAhorroEnCompra() /*|| serv.getDescuentoCorporativoAplicado()!=0*/){
				//CR.meVenta.getVenta().actualizarMontoTransaccion();
				//CR.meVenta.getVenta().actualizarPreciosDetalle(CR.meVenta.getVenta().isAplicaDesctoEmpleado());
				actualizarPreciosExt(p, serv, aplicarPromociones);
				serv.actualizarMontoServ();
				if(Detector.isAhorroEnCompra()){
					TreeMap<PromocionExt,Vector<Producto>> patrocinantes = 
						new TreeMap<PromocionExt,Vector<Producto>>(new ComparadorPromociones());
					patrocinantes = CR.meServ.getProductosPatrocinantes();
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
			    		UtilesActualizadorPrecios.deshacerDescuentoEnProductos(detallesPatrocinantes);	
			    		serv.actualizarMontoServ();
			    		if(Detector.isAhorroEnCompra()){
			    			for(int j=0;j<detallesPatrocinantes.size();j++){
				    			Vector<Object> descuentoAhorroEnCompra = UtilesActualizadorPrecios.getDescuentosAhorroEnCompra(promocion);
				    			UtilesActualizadorPrecios.aplicarDescuentoEnProductos(detallesPatrocinantesPosiciones, descuentoAhorroEnCompra);
				    			UtilesActualizadorPrecios.acumularDetallesPromociones(detallesPatrocinantesPosiciones, false);
				    			//Se eliminan los detalles que quedaron con cantidad cero
				    			for(int i=0;i<serv.getDetallesServicio().size();i++){
				    				try{
				    					DetalleServicio dt = ((DetalleServicio)serv.getDetallesServicio().elementAt(i));
				    					if (dt.getCantidad() <= 0) {
				    						serv.getDetallesServicio().set(i, null);
				    					}
				    				}catch(Exception e){
				    					//El detalle ya fue eliminado, no deberia ocurrir
				    				}
				    			}
				    		
				    			// Se eliminan los elementos nulos que quedan en el vector de detalles original
				    			while (serv.getDetallesServicio().removeElement(null));
				    		}
				    		patrocinantes = new TreeMap<PromocionExt,Vector<Producto>>(patrocinantes.headMap(promocion));
			    		}
					}
				}
			/*} else {
				//SI NO, ACTUALIZAR SOLO ESTE PRODUCTO
				actualizarPreciosExt(p, serv);
			}*/
    	}catch (Exception e){
    		logger.debug("actualizarPrecios(Producto p, Venta v)", e);
    		e.printStackTrace();
    	}
    }
    
    /**
	 * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#deshacerProductoGratis()
	 */
	public void deshacerProductoGratis(DetalleServicio detalleDeshacer){
		UtilesActualizadorPrecios.deshacerProductoGratis(detalleDeshacer);
	}
	
	/**
     * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#cargarPatrocinantes()
     */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en el 'TreeMap'
	* Fecha: agosto 2011
	*/
    public TreeMap<PromocionExt,Vector<Producto>> cargarPatrocinantes(){
    	return UtilesBD.cargarPatrocinantes();
    }
    
    /** (non-Javadoc)
    *
    * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#ejecutarAhorroEnCompra()
    */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
   public void ejecutarAhorroEnCompra(){
   	try{
	    	TreeMap<PromocionExt,Vector<Producto>> patrocinantes = CR.meServ.getProductosPatrocinantes();
	    	Iterator<PromocionExt> i = patrocinantes.keySet().iterator();
	    	while(i.hasNext()){
	    		PromocionExt promocion = (PromocionExt)i.next();
	    		Vector<Producto> patrocinantesEstaPromo = patrocinantes.get(promocion);
	    		Vector<Detalle> detallesDeTransaccionAfectados = Detector.getProd(patrocinantesEstaPromo, null);
	    		double totalTransaccion = CR.meServ.getApartado().consultarMontoServ();
	    		if(detallesDeTransaccionAfectados.size()!=0 && 
	    				totalTransaccion>=promocion.getMontoMinimo()) {
	    			
	    			boolean promocionAplicada=false;
	    			Iterator<Detalle> iteraDetalle = detallesDeTransaccionAfectados.iterator();
	    			while(iteraDetalle.hasNext()){
	    				DetalleServicio detalle = (DetalleServicio)iteraDetalle.next();
	    				Vector<String> condiciones =  new Vector<String>();
	    				condiciones.addElement(Sesion.condicionDescuentoEnProductos);
	    				CondicionVenta cv =  detalle.getPrimeraCondicion(condiciones);
	    				if(cv!=null){
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
    *  (non-Javadoc)
    *
    * @see com.becoblohm.cr.extensiones.ActualizadorPreciosSAC#ejecutarProductoGratis()
    * */
   /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void ejecutarProductoGratis(){
		PromocionExt promocionProductoGratis = PromocionExt.getPromocionProductoGratis();
		Vector<String> condicionEmpaque = new Vector<String>();
		condicionEmpaque.addElement(Sesion.condicionEmpaque);
		if(promocionProductoGratis!=null && promocionProductoGratis.getMontoMinimo()<=CR.meServ.getApartado().consultarMontoServ()){
			float cantidad = 0;
			for(int i=0;i<CR.meServ.getApartado().getDetallesServicio().size();i++){
				cantidad +=((DetalleServicio)CR.meServ.getApartado().getDetallesServicio().elementAt(i)).getCantidad();
			}
			if(cantidad>=promocionProductoGratis.getCantMinima()){
				
				CR.meServ.getApartado().setPromocionProductoGratis(promocionProductoGratis);
				//Obtener el producto que tiene precio final mas barato
				double menorPrecio = Double.MAX_VALUE;
				DetalleServicio dt = null;
				for(int i=0;i<CR.meServ.getApartado().getDetallesServicio().size();i++){
					DetalleServicio detalle = (DetalleServicio)CR.meServ.getApartado().getDetallesServicio().elementAt(i);
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
				try{
					(new BECOActualizadorPreciosSAC()).actualizarPreciosExt(dt.getProducto(), CR.meServ.getApartado(), true);
					CR.meServ.getApartado().actualizarMontoServ();
				} catch (Exception e){
					e.printStackTrace();
					logger.debug("ejecutarProductoGratis()", e);
				}
			}
		}
	}
	
   /**
    *  (non-Javadoc)
    *
    * @see com.becoblohm.cr.extensiones.ActualizadorPreciosSAC#recalcularPromociones()
    * */
	//TODO: este metodo esta hecho para el caso promocionesacumulativas unicamente
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public boolean recalculadoPromociones(){
		Apartado apartado =  CR.meServ.getApartado();
		Vector<Integer> posicionesDetalles =  new Vector<Integer>();
		boolean result = false;
		if(!apartado.isApartadoRecalculado()){
			double montoImpuesto=0.0;
			Vector<Integer> nuevosCombos = new Vector<Integer>();
			//apartado.setDetalleAnterior(new Vector());
			for(int i=0;i<apartado.getDetallesServicio().size();i++){
				posicionesDetalles.addElement(new Integer(i));
				if(!VectorUtil.contieneInt(nuevosCombos, i)){
					DetalleServicio detalle =  (DetalleServicio)apartado.getDetallesServicio().elementAt(i);
					//apartado.getDetalleAnterior().addElement(detalle.clone());
					//Obtengo las condiciones de venta vigentes para el producto de este detalle
					Vector<Vector<Object>> descuentosNuevos = buscarCondicionesDeVenta(detalle.getProducto(), apartado);
					for(int j=0;j<descuentosNuevos.size();j++){
						Vector<Object> descuentoActual = descuentosNuevos.elementAt(j);
						PromocionExt promoDescuento=null;
						try{
							promoDescuento = (PromocionExt)descuentoActual.elementAt(4);
						} catch (ArrayIndexOutOfBoundsException ex) { 
							// Es una promocion P
						}
					
						if(((String)descuentoActual.elementAt(1)).equalsIgnoreCase(Sesion.condicionPromocion)){
							Vector<String> condicionesPromocion =  new Vector<String>();
							condicionesPromocion.addElement(Sesion.condicionPromocion);
							Promocion prom = (Promocion)detalle.getProducto().getPromociones().elementAt(0);
							//Obteniendo la promocion aplicada inicialmente
							CondicionVenta primeraCondicionPromocion = detalle.getPrimeraCondicion(condicionesPromocion);
							if((primeraCondicionPromocion!=null && (primeraCondicionPromocion.getCodPromocion()!= prom.getCodPromocion() || 
									primeraCondicionPromocion.getPorcentajeDescuento()!=prom.getPorcentajeDescuento() || 
									detalle.getPrecioFinal()!=prom.getPrecioFinal())) || 
									primeraCondicionPromocion==null){
								
								double pFinal = detalle.getProducto().getPrecioRegular();
								Vector<String> descuentoEnProductos = new Vector<String>();
								descuentoEnProductos.addElement(Sesion.condicionDescuentoEnProductos);
								//Aplicar nueva promocion
								if(prom.getPorcentajeDescuento()!=0)
									pFinal = MathUtil.roundDouble(pFinal*(1-(prom.getPorcentajeDescuento()/100)));
								else 
									pFinal = prom.getPrecioFinal();
								
								double montoDctoCombo =0;
								double montoDescuentoCorporativo =0;

								//Actualizo el nuevo descuento en combo
								if(detalle.contieneAlgunaCondicion(Sesion.condicionesCombo)){
									montoDctoCombo = pFinal*((CondicionVenta)detalle.getPrimeraCondicion(Sesion.condicionesCombo)).getPorcentajeDescuento()/100;
									pFinal-=montoDctoCombo;
								}
								
								
								//Actualizo el nuevo descuento corporativo
								Vector<String> condicionesDescuentoCorporativo = new Vector<String>();
								condicionesDescuentoCorporativo.addElement(Sesion.condicionDescuentoEnProductos);
								if(detalle.contieneAlgunaCondicion(condicionesDescuentoCorporativo)){
									montoDescuentoCorporativo = pFinal*((CondicionVenta)detalle.getPrimeraCondicion(condicionesDescuentoCorporativo)).getPorcentajeDescuento()/100;
									pFinal -=montoDescuentoCorporativo;
								}
								
								//Actualizo el nuevo descuento por ahorro en compra
								if(detalle.contieneAlgunaCondicion(descuentoEnProductos)){
									double montoDctoAhorroEnCompra = pFinal*((CondicionVenta)detalle.getPrimeraCondicion(descuentoEnProductos)).getPorcentajeDescuento()/100;
									pFinal -= montoDctoAhorroEnCompra;
								}
								

								//Si el nuevo pFinal es menor al del detalle, aplico la promocion
								if (pFinal<(detalle.getPrecioFinal()-0.01) && (detalle.getCondicionVentaPromociones().size()==0 && prom!=null)) {
									result = true;
									detalle.setMontoDctoCombo(montoDctoCombo);
									detalle.setMontoDctoCorporativo(montoDescuentoCorporativo);
									Auditoria.registrarAuditoria("Recalculando Saldo de Apartado. Nueva promoción aplicada al producto " + detalle.getProducto().getCodProducto(), 'T');
									detalle.setCodPromocion(prom.getCodPromocion());
									detalle.removeCondicion(primeraCondicionPromocion);
									detalle.addCondicion(new CondicionVenta(Sesion.condicionPromocion,prom.getCodPromocion(),prom.getPorcentajeDescuento(),"Promoción Básica", prom.getPrioridad()));
									detalle.setCondicionVenta(detalle.construirCondicionVentaString());
									detalle.setPrecioFinal(pFinal);
									if ((detalle.getMontoImpuesto()==0)&&(apartado.getCliente().isExento())) {
										montoImpuesto = 0.0;
									} else {
										montoImpuesto = MathUtil.stableMultiply(detalle.getPrecioFinal(), detalle.getProducto().getImpuesto().getPorcentaje())/100;
									}						
									detalle.setMontoImpuesto(montoImpuesto);
									result = true;
								}
								else if(primeraCondicionPromocion!=null){
									//Agrego la antigua condicion de venta
									detalle.addCondicion(primeraCondicionPromocion);
								}
							}
						} 
						else if(((String)descuentoActual.elementAt(1)).equalsIgnoreCase(Sesion.condicionDescuentoEnCombo)){
							CondicionVenta primeraCondicionCombo = detalle.getPrimeraCondicion(Sesion.condicionesCombo);
							if(detalle.contieneAlgunaCondicion(Sesion.condicionesCombo) && 
									promoDescuento.getCodPromocion()!=primeraCondicionCombo.getCodPromocion()){
								nuevosCombos.addAll(UtilesActualizadorPrecios.aplicarComboMasConveniente(
											CR.meServ.getApartado().getDetallesServicio(),
											descuentoActual, 
											detalle
										));
								result = nuevosCombos.size()>0;
							} else {
								Vector<Integer> detallesEnCombo = new Vector<Integer>();
								while(Detector.verificaCantidadesMinimasCombo(promoDescuento,new Vector<String>(), false)){
									Vector<Integer> resultado = UtilesActualizadorPrecios.aplicarCondicionDescuentoEnCombo(descuentoActual, detalle.getProducto(), true);
									detallesEnCombo = resultado;
									nuevosCombos.addAll(detallesEnCombo);
									if(detallesEnCombo.size()==0){
										break;
									} else 
										result = true;
								}
							}
						}
						else {
							CR.meServ.getApartado().actualizarMontoServ();
							Vector<String> condicionDescuentoEnProductos =  new Vector<String>();
							condicionDescuentoEnProductos.addElement(Sesion.condicionDescuentoEnProductos);
							CondicionVenta cvAnterior =  (CondicionVenta)detalle.getPrimeraCondicion(condicionDescuentoEnProductos);
							if(promoDescuento!=null && ((String)descuentoActual.elementAt(1)).equalsIgnoreCase(Sesion.condicionDescuentoEnProductos)){
								if(cvAnterior==null || (promoDescuento.getCodPromocion()!=cvAnterior.getCodPromocion() && 
										cvAnterior.getPorcentajeDescuento()<promoDescuento.getPorcentajeDescuento())){
									if(cvAnterior!=null) {
										detalle.removeCondicion(cvAnterior);
										detalle.setPrecioFinal(MathUtil.roundDouble((detalle.getPrecioFinal()*100)/(100-cvAnterior.getPorcentajeDescuento())));
									}
									detalle.addCondicion(new CondicionVenta(
											Sesion.condicionDescuentoEnProductos,
											promoDescuento.getCodPromocion(),
											promoDescuento.getPorcentajeDescuento(),
											promoDescuento.getNombrePromocion(),
											promoDescuento.getPrioridad())
									);
									detalle.setCondicionVenta(detalle.construirCondicionVentaString());
									detalle.setPrecioFinal(MathUtil.roundDouble(detalle.getPrecioFinal()-detalle.getPrecioFinal()*promoDescuento.getPorcentajeDescuento()/100));
									result=true;
								}
							}
						}
					}
				}
			}
			
			UtilesActualizadorPrecios.acumularDetallesPromociones(posicionesDetalles, false);
			
			for(int i=0;i<apartado.getDetallesServicio().size();i++){
				DetalleServicio detalle = (DetalleServicio)apartado.getDetallesServicio().elementAt(i);
				try{
					if(detalle.getCantidad()==0){
						apartado.getDetallesServicio().set(i, null);
					}
				}catch(NullPointerException e){
					
				}
			}
			while(apartado.getDetallesServicio().remove(null));
		}
		if(result){
			apartado.setApartadoRecalculado(true);
			apartado.setLineasFacturacion(apartado.getDetallesServicio().size());
		}
		return result;
	}
	
	/**
    *  (non-Javadoc)
    *
    * @see com.becoblohm.cr.extensiones.ActualizadorPreciosSAC#agregarInsertServicioCondiciones()
    * */
	public void agregarInsertServicioCondiciones(Apartado apartado, Statement loteSentenciasCR, String fechaServString, String sentenciaSQL, int i, boolean isEnEspera, int numServicio) throws SQLException {
		String tabla ="";
		if(isEnEspera){
			tabla = " detalleserviciotempcondicion ";
		}
		else{
			tabla = " detalleserviciocondicion ";
			numServicio = apartado.getNumServicio();
		}
		if(((DetalleServicio)apartado.getDetallesServicio().elementAt(i)).getCondicionesVentaPromociones().size()==0){
			sentenciaSQL = "insert into "+tabla+" (numtienda, codtiposervicio, numservicio, fecha, codproducto, condicionventa, correlativoitem, codcondicionventa, nombrePromocion, porcentajeDescuento, codpromocion, montoDctoCombo, prioridadPromocion) " +
				"values ( "+apartado.getCodTienda()+", " +
				"'" +apartado.getTipoServicio()+"'," +
				numServicio+", " +
				"'"+fechaServString+"'," +
				"'" + ((DetalleServicio)apartado.getDetallesServicio().elementAt(i)).getProducto().getCodProducto() + "', " +
				"'" + ((DetalleServicio)apartado.getDetallesServicio().elementAt(i)).getCondicionVenta() + "'," +
				(i+1)+", " +
				"'N'," +
				"'Normal'," +
				"0.00," +
				"0," +
				((DetalleServicio)apartado.getDetallesServicio().elementAt(i)).getMontoDctoCombo()+"," +
				"0)";
			
			loteSentenciasCR.addBatch(sentenciaSQL);
		} else {
			for(int j=0;j<((DetalleServicio)apartado.getDetallesServicio().elementAt(i)).getCondicionesVentaPromociones().size();j++){
				CondicionVenta condicion = (CondicionVenta)((DetalleServicio)apartado.getDetallesServicio().elementAt(i)).getCondicionesVentaPromociones().elementAt(j);
				sentenciaSQL = "insert into "+tabla+" (numtienda, codtiposervicio, numservicio, fecha, codproducto, condicionventa, correlativoitem, codcondicionventa, nombrePromocion, porcentajeDescuento, codpromocion, montoDctoCombo, prioridadPromocion) " +
						"values ( "+apartado.getCodTienda()+", " +
								"'" +apartado.getTipoServicio()+"'," +
								numServicio+", " +
								"'"+fechaServString+"'," +
								"'" + ((DetalleServicio)apartado.getDetallesServicio().elementAt(i)).getProducto().getCodProducto() + "', " +
								"'" + ((DetalleServicio)apartado.getDetallesServicio().elementAt(i)).getCondicionVenta() + "'," +
								(i+1)+", " +
								"'"+condicion.getCondicion()+"'," +
								"'"+condicion.getNombrePromocion()+"'," +
								condicion.getPorcentajeDescuento()+"," +
								condicion.getCodPromocion()+"," +
								((DetalleServicio)apartado.getDetallesServicio().elementAt(i)).getMontoDctoCombo()+"," +
								condicion.getPrioridadPromocion()+")";
				loteSentenciasCR.addBatch(sentenciaSQL);
			}
		}
	}
	
	/**
    *  (non-Javadoc)
    *
    * @see com.becoblohm.cr.extensiones.ActualizadorPreciosSAC#agregarUpdateCondicionesServicio(Apartado ap, Statement loteSentenciasCR)
    * */
	public void agregarDeleteServicioCondiciones(Apartado ap, Statement loteSentenciasCR, boolean isEnEspera, int numServicio) throws SQLException {
		String sentencia;
		String tabla="";
		if(isEnEspera){
			tabla=" detalleserviciotempcondicion ";
		}
		else{ 
			tabla = " detalleserviciocondicion ";
			numServicio = ap.getNumServicio();
		}
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
		sentencia = "delete from "+tabla+" "+
					"where numtienda = " + ap.getCodTienda() + " and " +
					"codtiposervicio = '" + ap.getTipoServicio() + "' and " +
					"numservicio = " + numServicio + " and " +
					"fecha = '" + df2.format(ap.getFechaServicio()) + "'";
		loteSentenciasCR.addBatch(sentencia);
	}

	/**
    *  (non-Javadoc)
    *
    * @see com.becoblohm.cr.extensiones.ActualizadorPreciosSAC#botonServicioHabilitado(int numBoton, boolean enUtilitarios)
    * */
	public boolean botonServicioHabilitado(int numBoton, boolean enUtilitarios) {
		if(!enUtilitarios)
			return true;
		
		return false;
	}
	/**
	 * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#ventanaPromociones()
	 */
	public void ventanaPromociones(Apartado apartado) {
		MenuPromociones mp = new MenuPromociones(apartado);
		MensajesVentanas.centrarVentanaDialogo(mp);
	}
	
	/**
	 * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#sumarDonaciones(java.lang.double)
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public double sumarDonaciones(Apartado apartado, double subTotal, boolean solicitarDonaciones){
		
		
		if(apartado.getDetallesServicio().size()!=0){
			Vector<Donacion> donaciones1 = DonacionBD.getDonacionesTipo1();
			Vector<Donacion> donaciones2 = DonacionBD.getDonacionesTipo2(true);
			
			if(donaciones1.size()!=0 && UtilesActualizadorPrecios.getDonacionesRegistradasTipo1(Apartado.donacionesRegistradas).size()==0 && solicitarDonaciones){
				VentanaDonacion ventanaDonacion = new VentanaDonacion((float)(5-subTotal%5));//(1-(subTotal%(int)subTotal)));
				MensajesVentanas.centrarVentanaDialogo(ventanaDonacion);
				//if(ventanaDonacion.isDono()) subTotal+=ventanaDonacion.getMontoDonacion();
			}
			
			if(donaciones2.size()!=0 && UtilesActualizadorPrecios.getDonacionesRegistradasTipo2(Apartado.donacionesRegistradas).size()==0 && solicitarDonaciones){
				VentanaDonacion ventanaDonacion = new VentanaDonacion(true);
				MensajesVentanas.centrarVentanaDialogo(ventanaDonacion);
			}
			
			Iterator<DonacionRegistrada> donaciones = Apartado.donacionesRegistradas.iterator();
			while(donaciones.hasNext()){
				DonacionRegistrada d = (DonacionRegistrada)donaciones.next();
				subTotal+=d.getMontoDonado();
			}
		}
		
		return subTotal;
	}
	
	/*public HashMap cargarProductoComplementario(){
    	return UtilesBD.cargarProductoComplementario();
    }*/
	
	/**
	 * llamada a la promocion producto complementario
	 */
    /*public void ejecutarProductoComplementario(int p){
    	UtilesActualizadorPrecios.ejecutarProductoComplementario(p, null);
    }*/
    
    /**
	 * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#llamadaDeRegalos(int n)
	 * **/
    public void llamadaDeRegalos(int n){
		if(n==1 && !PromocionExtBD.getRegalos().isEmpty()){
			PromocionExtBD.iteradorRegalo(1);
		}else if(n==2){
		if (!PromocionExtBD.getRegalos().isEmpty())
			PromocionExtBD.iteradorRegalo(2);
		}
	}


	public void acumularDetallesDevolucion(Devolucion dev) {
		Vector<Integer> posiciones = new Vector<Integer>();
		for(int i=0;i<dev.getDetallesTransaccion().size();i++){
			posiciones.addElement(new Integer(i));
		}
		UtilesActualizadorPrecios.acumularDetallesPromociones(posiciones, true);
	}
}
