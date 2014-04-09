package com.beco.cr.actualizadorPrecios;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.beco.cr.actualizadorPrecios.promociones.Detector;
import com.beco.cr.actualizadorPrecios.promociones.Donacion;
import com.beco.cr.actualizadorPrecios.promociones.DonacionRegistrada;
import com.beco.cr.actualizadorPrecios.promociones.Linea;
import com.beco.cr.actualizadorPrecios.promociones.ProductoCombo;
import com.beco.cr.actualizadorPrecios.promociones.PromocionExt;
import com.beco.cr.actualizadorPrecios.promociones.Referencia;
import com.beco.cr.actualizadorPrecios.promociones.Seccion;
import com.beco.cr.actualizadorPrecios.promociones.interfaz.CodigoCupon;
import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.PagoExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.extensiones.ManejoPagosFactory;
import com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.manejadorauditoria.Auditoria;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstadoVenta;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarservicio.Apartado;
import com.becoblohm.cr.manejarservicio.DetalleServicio;
import com.becoblohm.cr.manejarservicio.ListaRegalos;
import com.becoblohm.cr.manejarservicio.Servicio;
import com.becoblohm.cr.manejarventa.CondicionVenta;
import com.becoblohm.cr.manejarventa.Detalle;
import com.becoblohm.cr.manejarventa.DetalleTransaccion;
import com.becoblohm.cr.manejarventa.Producto;
import com.becoblohm.cr.manejarventa.Venta;
import com.becoblohm.cr.utiles.MathUtil;
import com.becoblohm.cr.utiles.MensajesVentanas;

public class UtilesActualizadorPrecios {

	private static final Logger logger = Logger.getLogger(UtilesActualizadorPrecios.class);
	public static String textoVentana = "";
	public static boolean imprimirProductoComplementario = false;
	
	/**
	 * Agrupa los detalles iguales y los coloca al final del vector de detalles original
	 * @param posiciones Vector de posiciones donde se encuentran los productos a agrupar
	 * @param devolucion TODO
	 * @return void
	 * 
	 * NOTA: Sirve tanto para servicio como para venta
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unchecked")
	public static void acumularDetallesPromociones(Vector<Integer> posiciones, boolean devolucion) {
		if (logger.isDebugEnabled()) {
			logger.debug("acumularDetallesPromociones(Vector) - start");
		}
		
		//Determino el tipo de operacion que estoy realizando
		Venta venta = CR.meVenta.getVenta();
		Apartado apartado = CR.meServ.getApartado();
		ListaRegalos lr = CR.meServ.getListaRegalos();
		Vector detalles = new Vector<Detalle>();
		if(venta!=null)
			detalles = venta.getDetallesTransaccion();
		else if(apartado!=null)
			detalles = apartado.getDetallesServicio();
		else if(lr!=null)
			detalles = lr.getDetallesServicio();
		
		if(devolucion)
			detalles = CR.meVenta.getDevolucion().getDetallesTransaccion();
			
		
		Vector<Integer> paraCaptura = new Vector<Integer>(2);
		// Acumulamos los productos por vendedores (Si es el caso)
		for (int i=0; i<posiciones.size();i++) {
			if (((Integer)posiciones.elementAt(i))!=null) {
				int posicionI = ((Integer)posiciones.elementAt(i)).intValue();
				for (int j=0; j<posiciones.size(); j++) {
					if (((Integer)posiciones.elementAt(j))!=null && j!=i) {
						int posicionJ = ((Integer)posiciones.elementAt(j)).intValue();
						
						Detalle dti = (((Detalle)detalles.elementAt(posicionI)));
						Detalle dtj = (((Detalle)detalles.elementAt(posicionJ)));
						Vector<String> condicionesRecibeDescuento =  new Vector<String>();
						condicionesRecibeDescuento.addElement(Sesion.condicionDescontadoPorCombo);
						
						if(dti!=null && dtj!=null && dti.getProducto().getCodProducto().equalsIgnoreCase(dtj.getProducto().getCodProducto()) &&
								dti.contieneMismasCondiciones(dtj) && !dti.contieneAlgunaCondicion(condicionesRecibeDescuento)){
							
							float sumaCantidades =0;
							float cantidadTope =0;
							
							//dti y dtj solo pueden tener una condicion de combo, y tienen las mismas condiciones
							//por lo tanto la primera condicion de combo de dti es la única de combo aplicada a ambos
							try{
								CondicionVenta condicionCombo = dti.getPrimeraCondicion(Sesion.condicionesCombo);
								Vector<PromocionExt> promociones = PromocionExt.getPromocionesCombo(dti.getProducto(), condicionCombo.getCodPromocion());
								PromocionExt p = null;
								if(promociones.size()!=0) {
									p = (PromocionExt)promociones.elementAt(0);
									Vector<ProductoCombo> productos = p.getProductosCombo();
									for(int k=0;k<productos.size();k++){
										ProductoCombo pc = (ProductoCombo)productos.elementAt(k);
										if(pc.contieneProducto(dti.getProducto())){
											cantidadTope = pc.getCantidadMinima();
											break;
										}
										
									}
								}
							
								sumaCantidades = dti.getCantidad()+dtj.getCantidad();
								
							} catch (NullPointerException e){
								cantidadTope = Float.MAX_VALUE;
							}
							
							if(dti.getPrecioFinal()-dtj.getPrecioFinal()<=0.01 && 
								sumaCantidades<=cantidadTope) {
								// Primero verificamos los tipos de captura entre detalles
								paraCaptura.addElement(((Integer)posiciones.elementAt(i)));
								paraCaptura.addElement(((Integer)posiciones.elementAt(j)));
								if(venta!=null)
									venta.verificarTipoCaptura(paraCaptura);
								else if(apartado!=null)
									apartado.verificarTipoCaptura(paraCaptura);
									
								dti.incrementarCantidad(dtj.getCantidad());
								dti.setCantidadCambiada(false);
								dti.setVariacion(0);
								detalles.set(posicionJ,null);
								posiciones.set(j,null);
							}
						}
					}
					paraCaptura.clear();
				}
			}
		}
			
		// Eliminamos los nulos del vector de posiciones de condiciones normales 'N'
		while (posiciones.removeElement(null));
			
		// Colocamos los detalles al final del vector original.
		Detalle detalleTrans = null;
		int pos;
			
		// Movemos los detalles para colocarlos al final del vector
		for (int i=0; i<posiciones.size(); i++){
			pos = ((Integer)posiciones.elementAt(i)).intValue();
			detalleTrans = (Detalle) detalles.elementAt(pos);
			detalles.addElement(detalleTrans);
			detalles.set(pos,null);
			posiciones.set(i,null);
			
		}
			
		if (logger.isDebugEnabled()) {
			logger.debug("acumularDetallesPromociones(Vector) - end");
		}
	}
	
	/**
	 * Deshace el combo en el que esta participando el detalleActual
	 * @param venta
	 * @param AcumN
	 * @param detallesN
	 * @param detallesAActualizar Detalles sobre los cuales es necesario aplicar el algoritmo
	 * @param nuevosCombos vector de detalles que han sido agregados a combos, sacar de alli los
	 * que se deshagan
	 * @param variacion Indica la cantidad de productos agregados o eliminados si es el caso
	 * @param detalleActual
	 * @return AcunN cantidad de detalles normales que quedan ahora en la venta
	 * 
	 * NOTA: Sirve tanto para servicios como para venta
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static void deshacerCombo( 
			Vector<Integer> nuevosCombos, 
			Vector<Detalle> detallesAActualizar,
			float variacion, 
			Detalle detalleActual,
			PromocionExt promocionAAplicar) {
		
		//Determino el tipo de operacion que estoy realizando
		Venta venta = CR.meVenta.getVenta();
		Apartado apartado = CR.meServ.getApartado();
		
		CondicionVenta cvAplicada = ((CondicionVenta)(detalleActual).getPrimeraCondicion(Sesion.condicionesCombo));
		
		Vector<String> condicionDescuentoEnCombo = new Vector<String>();
		condicionDescuentoEnCombo.addElement(Sesion.condicionDescuentoEnCombo);
		Vector<String> condicionCombosCantidades = new Vector<String>();
		condicionCombosCantidades.addElement(Sesion.condicionComboPorCantidades);
		
		Vector<String> condicionesCorporativas =  new Vector<String>();
		condicionesCorporativas.addElement(Sesion.condicionDescuentoCorporativo);
		
		if(detalleActual.getCantidad()==0) {
			if(venta!=null)
				venta.getDetallesAEliminar().addElement(detalleActual);
			else if (apartado!=null)
				apartado.getDetallesAEliminar().addElement(detalleActual);
		}
		//1.1 - Obtengo todos los productos (ProductoCombo) que participan en la promocion
		int codPromocion = cvAplicada.getCodPromocion();
		PromocionExt promocionAplicada = (PromocionExt)(PromocionExt.getPromocionAplicadaCombo(codPromocion));
		Vector<ProductoCombo> integratesCombo = promocionAplicada.getProductosCombo();
		
		if(cvAplicada!=null){
			try{
				//1.2 - Para todos los productos que participan en el combo, poner normales sus detalles
				Iterator<ProductoCombo> iteraIntegrantes = integratesCombo.iterator();
				while(iteraIntegrantes.hasNext()){
					ProductoCombo productoCombo = (ProductoCombo)iteraIntegrantes.next();
					Vector<Detalle> detallesAfectados = Detector.getDetallesAfectados(promocionAplicada, productoCombo);
					UtilesActualizadorPrecios.ordernarPorCantidad(detallesAfectados);
					Iterator<Detalle> iteraDetallesAfectados = detallesAfectados.iterator();
					int cantMinima = productoCombo.getCantidadMinima();
					
					//Resto la variacion del producto
					if(productoCombo.contieneProducto(detalleActual.getProducto())) {
						cantMinima+=variacion;
					}
					
					boolean continuar = true;
					while(iteraDetallesAfectados.hasNext() && continuar){
						Detalle detalleEnCombo = iteraDetallesAfectados.next();
						CondicionVenta condDetalleEnCombo = detalleEnCombo.getPrimeraCondicion(Sesion.condicionesCombo);
						if(condDetalleEnCombo!=null && 
								condDetalleEnCombo.getCodPromocion()==codPromocion /*&& 
								(promocionAAplicar==null || 
										promocionAAplicar.comboContieneProducto(detalleEnCombo.getProducto()))*/){
							//Caso 1: Descuento en combo
							if((cantMinima>=detalleEnCombo.getCantidad()) && detalleEnCombo.getCantidad()!=0) {
								/*
								 *  DESHACER DESCUENTO EN COMBO
								 **************************************/
								if (detalleEnCombo.contieneCondicion(condicionDescuentoEnCombo)){
									
									//1.2.1- Coloco condicion normal a este detalle:
									detalleEnCombo.removeCondicion(new CondicionVenta(Sesion.condicionDescuentoEnCombo));
									detalleEnCombo.setCondicionVenta(detalleEnCombo.construirCondicionVentaString());
									
									//Verifico si tiene  ahorro en compra para deshacerlo
									Vector<String> condicionesAhorroEnCompra = new Vector<String>();
									condicionesAhorroEnCompra.addElement(Sesion.condicionDescuentoEnProductos);
									CondicionVenta cvAhorroC = detalleEnCombo.getPrimeraCondicion(condicionesAhorroEnCompra);
									double pFinalAnterior = detalleEnCombo.getPrecioFinal();
									if(cvAhorroC!=null){
										pFinalAnterior = (pFinalAnterior*100)/(100-cvAhorroC.getPorcentajeDescuento());
									}
									double montoEmpleadoAnterior=0;
									double montoCorporativoAnterior=0;
									if(venta!=null){
										montoEmpleadoAnterior = ((DetalleTransaccion)detalleEnCombo).getDctoEmpleado();
										montoCorporativoAnterior = ((DetalleTransaccion)detalleEnCombo).getMontoDctoCorporativo();
									} else if(apartado!=null){
										montoCorporativoAnterior = ((DetalleServicio)detalleEnCombo).getMontoDctoCorporativo();
									}
									pFinalAnterior =pFinalAnterior+montoEmpleadoAnterior+montoCorporativoAnterior;
									
									
									pFinalAnterior = pFinalAnterior+detalleEnCombo.getMontoDctoCombo();
									
									double montoDctoEmpleado = 0;
									double montoCorporativo = 0;
									double montoAhorroCompra = 0;
									
									//Recalculo descuento de empleado
									if((venta!=null && venta.isAplicaDesctoEmpleado()))
										montoDctoEmpleado = pFinalAnterior*Sesion.getDesctoVentaEmpleado()/100;
									pFinalAnterior = pFinalAnterior-montoDctoEmpleado;
									
									//Recalculo descuento corporativo
									if(venta!=null && detalleEnCombo.contieneAlgunaCondicion(condicionesCorporativas))
										montoCorporativo =  pFinalAnterior*venta.getDescuentoCorporativoAplicado()/100;
									else if(apartado!=null && detalleEnCombo.contieneAlgunaCondicion(condicionesCorporativas))
										montoCorporativo =  pFinalAnterior*apartado.getDescuentoCorporativoAplicado()/100;
									pFinalAnterior = pFinalAnterior-montoCorporativo;
									detalleEnCombo.setMontoDctoCorporativo(montoCorporativo);
									
									if(cvAhorroC!=null)
										montoAhorroCompra = pFinalAnterior*cvAhorroC.getPorcentajeDescuento()/100;
									pFinalAnterior = pFinalAnterior-montoAhorroCompra;
									
									detalleEnCombo.setPrecioFinal(MathUtil.roundDouble(pFinalAnterior));
									if(venta!=null){
										detalleEnCombo.setMontoImpuesto(venta.calcularImpuesto(detalleEnCombo.getProducto(), detalleEnCombo.getPrecioFinal()));
										((DetalleTransaccion)detalleEnCombo).setMontoDctoEmpleado(montoDctoEmpleado);
									} else if (apartado!=null)
										detalleEnCombo.setMontoImpuesto(apartado.calcularImpuesto(detalleEnCombo.getProducto(), detalleEnCombo.getPrecioFinal()));
									
									detalleEnCombo.setMontoDctoCombo(0.00);
									
									//1.2.2- Actualizo los precios de los detalles afectados
									if(!detalleActual.equals(detalleEnCombo)) {
										detallesAActualizar.addElement(detalleEnCombo);
									} 
									
									cantMinima-=detalleEnCombo.getCantidad();
									if(cantMinima==0){
										//Si ya deshice, la cantidad necesaria para UN SOLO COMBO pasar al siguiente producto que 
										//forma parte del combo.
										//Se debe cuidar que la cantidad de cada detalle sea MAXIMO la cantidad necesaria para un combo,
										//los combos quedan en detalles separados
										continuar=false;
									}
								}
								
								/*
								 *  DESHACER COMBOS POR CANTIDAD (TIPO 2X1)
								 **************************************/
								else if(detalleEnCombo.contieneAlgunaCondicion(condicionCombosCantidades)){
									//1.2.1- Coloco condicion normal a este detalle:
									detalleEnCombo.removeCondicion(new CondicionVenta(Sesion.condicionComboPorCantidades));
									detalleEnCombo.removeCondicion(new CondicionVenta(Sesion.condicionDescontadoPorCombo));
									detalleEnCombo.setCondicionVenta(detalleEnCombo.construirCondicionVentaString());
									//1.2.2- Actualizo los precios de los detalles afectados
									if(!detalleActual.equals(detalleEnCombo)) {
										detallesAActualizar.addElement(detalleEnCombo);
									} 
								}
							}
						}
					}
				}
			} catch(Exception e){
				logger.debug("Error eliminando producto en ejecucion del algoritomo ", e);
				e.printStackTrace();
			}
		}
		Auditoria.registrarAuditoria("Eliminada promoción de combo: "+cvAplicada.getCodPromocion(), 'T');
	}
	
	/**
	 * Aplica la condición de venta"Empaque" a los productos especificados
	 * @param prod Producto al que se le aplicara el descuento a empaque.
	 * @param detallesN Vector de posiciones del producto .
	 * @param prodsEmpaque Cantidad de productos para el empaque
	 * @param posicionMejor Entero que indica la posición que posee el mejor descuento
	 * @param dctoActual Vector que indica el descuento actual que posee el producto
	 * @return Vector - Vector de los productos con la condición de venta empaque aplicada
	 * 
	 * NOTA: SOLO VALIDO PARA SERVICIOS
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Object> aplicarCondicionEmpaque(Producto prod, 
			Vector<Integer> detallesN, float prodsEmpaque, int posicionMejor,
			Vector<Object> dctoActual, Servicio serv) throws XmlExcepcion, 
			FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarCondicionEmpaque(Producto, Vector, float, int, Vector) - start");
		}

		Vector<Integer> nuevosEmpaques = new Vector<Integer>();
		String supervisor; 
		String tipoCaptura; 
		float cantidadAsignar;
		int puntoDeChequeo = 0;
		float cantidadDetalleActual;
		Vector<Object> resultado = new Vector<Object>(2);
		
		double pFinal = ((Double)dctoActual.elementAt(0)).doubleValue();
		
		while (prodsEmpaque > 0) {
			// Si existen empaques, creo el nuevo detalle con el empaque por cada detallesN
			supervisor = ((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCodSupervisor();
			tipoCaptura = ((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getTipoCaptura();
			cantidadDetalleActual = ((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCantidad();
			if (prodsEmpaque >= cantidadDetalleActual) {
				cantidadAsignar = cantidadDetalleActual;
				puntoDeChequeo++;
			} else {
				cantidadAsignar = prodsEmpaque;
				((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).setCantidad(cantidadDetalleActual - cantidadAsignar);
			}
			prodsEmpaque -= cantidadAsignar;
			pFinal = MathUtil.roundDouble(pFinal);
			DetalleServicio nuevoDetalleTrans = 
				new DetalleServicio(
						((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getProducto(),
					cantidadAsignar,
					Sesion.condicionNormal,
					supervisor,
					((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getProducto().getPrecioRegular(),
					serv.calcularImpuesto(
							((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getProducto(), 
							((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getPrecioFinal()),
					tipoCaptura , 
					0, 
					"");
			
			try{
				nuevoDetalleTrans.addCondicion((CondicionVenta)dctoActual.elementAt(3));
			}catch(Exception e){
				//Nada la extesion de promociones no esta activa
			}
			
			serv.getDetallesServicio().addElement(nuevoDetalleTrans);
			nuevosEmpaques.addElement(new Integer(serv.getDetallesServicio().size() - 1));
		}
		
		if (nuevosEmpaques.size() > 0) {
			// Registramos la auditoría de esta función
			Sesion.setUbicacion("FACTURACION","aplicarCondicionEmpaque");
			Auditoria.registrarAuditoria("Aplicada condicion Empaque Prod. " + prod.getCodProducto() + " P.Reg. " + prod.getPrecioRegular() + " P.Final " + pFinal,'T');
		}
		
		resultado.addElement(nuevosEmpaques);
		resultado.addElement(new Integer(puntoDeChequeo));
	
		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarCondicionEmpaque(Producto, Vector, float, int, Vector) - end");
		}
		return resultado;
	}
	
	
	/**
	 * Aplica la condición de venta"Empaque" a los productos especificados para el caso de descuento a empaque acumulativo
	 * @param prod Producto al que se le aplicara el descuento a empaque.
	 * @param detallesN Vector de posiciones del producto .
	 * @param prodsEmpaque Cantidad de productos para el empaque
	 * @param posicionMejor Entero que indica la posición que posee el mejor descuento
	 * @param dctoActual Vector que indica el descuento actual que posee el producto
	 * @return Vector - Vector de los productos con la condición de venta empaque aplicada
	 * 
	 * NOTA: SOLO VALIDO PARA SERVICIOS
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Object> aplicarCondicionEmpaque(Producto prod, Vector<Integer> detallesN, Servicio serv) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("aplicarCondicionEmpaque(Producto, Vector) - start");
		}

		Vector<Integer> nuevosEmpaques = new Vector<Integer>();
		String supervisor; 
		String tipCaptura; 
		float cantidadAsignar;
		int puntoDeChequeo = 0;
		float cantidadDetalleActual;
		Vector<Object> resultado = new Vector<Object>(2);
		float productos = 0;
		double pFinal = 0;
		
		for (int i=0; i<detallesN.size(); i++) {
			productos += ((DetalleServicio) serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(i)).intValue())).getCantidad();
			pFinal = ((DetalleServicio) serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(i)).intValue())).getPrecioFinal();
		}
			
		double dctoEmpaque = pFinal * (prod.getDesctoVentaEmpaque()/100);
		pFinal -= dctoEmpaque;
		
		float prodsEmpaque = productos - (productos % prod.getCantidadVentaEmpaque());
		
		if (dctoEmpaque > 0) {
			while (prodsEmpaque > 0) {
				//No aplicar empaque si ya esta en un combo el detalle
				if(!((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).contieneAlgunaCondicion(Sesion.condicionesCombo)){
					// Si existen empaques, creo el nuevo detalle con el empaque por cada detallesN
					supervisor = ((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCodSupervisor();
					tipCaptura = ((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getTipoCaptura();
					cantidadDetalleActual = ((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCantidad();
					
					String condVenta = ((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCondicionVenta();
				
					if (prodsEmpaque >= cantidadDetalleActual) {
						cantidadAsignar = cantidadDetalleActual;
						serv.getDetallesServicio().set(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue(), null);
						detallesN.set(puntoDeChequeo, null);
						
						puntoDeChequeo++;
					} else {
						cantidadAsignar = prodsEmpaque;
						((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).setCantidad(cantidadDetalleActual - cantidadAsignar);
					}
					prodsEmpaque -= cantidadAsignar;
					
	
					// Chequeamos las condiciones de Venta
					if (condVenta.equals(Sesion.condicionPromocion)) {
						condVenta = Sesion.condicionEmpaquePromocion;
					} else {
						if (condVenta.equals(Sesion.condicionNormal)) {
							condVenta = Sesion.condicionEmpaque;
						} 
					}
					pFinal = MathUtil.roundDouble(pFinal);
					DetalleServicio nuevoDetalleTrans = 
						new DetalleServicio(
							((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getProducto(),
							cantidadAsignar,
							Sesion.condicionNormal,
							supervisor,
							((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getProducto().getPrecioRegular(),
							serv.calcularImpuesto(
									((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getProducto(), 
									((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getPrecioFinal()),
							tipCaptura , 
							0, 
							"");
					
					nuevoDetalleTrans.addCondicion(new CondicionVenta(Sesion.condicionEmpaque));
					serv.getDetallesServicio().addElement(nuevoDetalleTrans);
					nuevosEmpaques.addElement(new Integer(serv.getDetallesServicio().size() - 1));
				}
			}
			
			if (nuevosEmpaques.size() > 0) {
				// Registramos la auditoría de esta función
				Sesion.setUbicacion("FACTURACION","aplicarCondicionEmpaque");
				Auditoria.registrarAuditoria("Aplicada condicion Empaque Prod. " + prod.getCodProducto() + " P.Reg. " + prod.getPrecioRegular() + " P.Final " + pFinal,'T');
			}
		}
		
		// Se eliminan los elementos nulos que quedan en el vector de detallesN
		while (detallesN.removeElement(null));
		
		resultado.addElement(nuevosEmpaques);
		resultado.addElement(new Integer(puntoDeChequeo));
		
		if (logger.isDebugEnabled()) {
			logger.debug("aplicarCondicionEmpaque(Producto, Vector) - end");
		}
		return resultado;
	}
	
	
	/**
	 * Aplica la condicion de descuento en combo para el producto indicado
	 * NOTA: solo se va a formar UN combo por cada corrida del algoritmo
	 * @param detallesN vector de posiciones de detalles normales
	 * @param detallesC Vector de posiciones de detalles que fueron cambiados a condicion de venta combo
	 * @param dctoActual Descuento de combo a aplicar
	 * @param cantidad Cantidad de productos en detallesN
	 * @param prod Producto que se esta agregando
	 * @return Vector	[0] Nuevos combos formados
	 * 					[1] Nueva cantidad
	 * 
	 * NOTA: Sirve tanto para servicios como para venta
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Integer> aplicarCondicionDescuentoEnCombo(Vector<Object> dctoActual, Producto prod, boolean isRecalculo){
		
		Venta venta = CR.meVenta.getVenta();
		Apartado apartado = CR.meServ.getApartado();
		Vector detalles = new Vector<Detalle>();
		Vector<String> condicionesEmpleados = new Vector<String>();
		condicionesEmpleados.addElement(Sesion.condicionDesctoEmpleado);
		Vector<String> condicionesCorporativa = new Vector<String>();
		condicionesCorporativa.addElement(Sesion.condicionDescuentoCorporativo);
		if(venta!=null)
			detalles = CR.meVenta.getVenta().getDetallesTransaccion();
		else if(apartado!=null)
			detalles = apartado.getDetallesServicio();
		
		//dctoActual contiene: PRECIO FINAL, CONDICION VENTA, CODIGO PROMOCION, CondicionVenta, PromocionExt
		Vector<Integer> detallesCombos = new Vector<Integer>();
		CondicionVenta cv = (CondicionVenta)dctoActual.elementAt(3);
		PromocionExt promocion = (PromocionExt)dctoActual.elementAt(4);
		
		//Desarmar un combo que involucre al producto que estoy agregando, y que tenga menor prioridad
		//o varios hasta que se pueda armar el combo de promocion
		Vector<String> condicionesNoPermitidas = new Vector<String>();
		condicionesNoPermitidas.addAll(Sesion.condicionesCombo);
		condicionesNoPermitidas.addAll(Sesion.condicionesEspeciales);
		Vector<? extends Detalle> detallesAActualizar = resolverPrioridadesCombos(isRecalculo, detalles, detallesCombos, promocion, condicionesNoPermitidas);
		
		if(Detector.verificaCantidadesMinimasCombo(promocion, condicionesNoPermitidas, false)){
			for(int i=0;i<promocion.getProductosCombo().size();i++){
				ProductoCombo productoInvolucrado = (ProductoCombo)((ProductoCombo)promocion.getProductosCombo().elementAt(i)).clone();
				for(int j=0;j<detalles.size();j++){
					Detalle dt = (Detalle)detalles.elementAt(j);
					
					//Obtengo la promoción de ahorro en compra que tiene aplicado
					Vector<String> condicionesAhorroEnCompra = new Vector<String>();
					condicionesAhorroEnCompra.addElement(Sesion.condicionDescuentoEnProductos);
					CondicionVenta cvAhorroC = dt.getPrimeraCondicion(condicionesAhorroEnCompra);
					
					float cantidadAAsignar;
					
					//Si el producto del detalle esta promocionado
					if(UtilesActualizadorPrecios.productoPerteneceACombo(productoInvolucrado, dt.getProducto()) && 
							!dt.isCondicionEspecial() && 
							!dt.contieneAlgunaCondicion(Sesion.condicionesCombo)){
						
						double porcentajeDescuento = promocion.getPorcentajeDescuento();
						if(dt.getCantidad() > productoInvolucrado.getCantidadMinima()){
							cantidadAAsignar = productoInvolucrado.getCantidadMinima();
							Detalle detalleNuevo = (Detalle)dt.clone();
							detalleNuevo.setCantidad(cantidadAAsignar);
							detalleNuevo.setCantidadCambiada(false);
							detalleNuevo.setVariacion(0);
							detalleNuevo.addCondicion(cv);
							detalleNuevo.setCondicionVenta(detalleNuevo.construirCondicionVentaString());
							
							//Eliminar ahorro en compra
							if(cvAhorroC!=null){
								detalleNuevo.setPrecioFinal((detalleNuevo.getPrecioFinal()*100)/(100-cvAhorroC.getPorcentajeDescuento()));
							}
							//Eliminar empleado y corporativo si lo tenía para luego recalcular
							double descuentoEmpleadoAnterior = 0;
							if(venta!=null)
								descuentoEmpleadoAnterior = ((DetalleTransaccion)detalleNuevo).getMontoDctoEmpleado();
							double precioInicial = detalleNuevo.getPrecioFinal()+descuentoEmpleadoAnterior+detalleNuevo.getMontoDctoCorporativo();
							detalleNuevo.setPrecioFinal(precioInicial);
							
							//Cambio para que aplique la Combos con Precio Final 2012-10-31
							if(!(promocion.getBsDescuento()>0)){
								
								detalleNuevo.setMontoDctoCombo(detalleNuevo.getPrecioFinal()*porcentajeDescuento/100);
								detalleNuevo.setPrecioFinal(detalleNuevo.getPrecioFinal()-detalleNuevo.getMontoDctoCombo());		
							}
							else{
								//PROMOCION N PRODUCTOS, PRECIO FINAL Y
								if(detalleNuevo.getPrecioFinal()>promocion.getBsDescuento()){
									detalleNuevo.setMontoDctoCombo(detalleNuevo.getPrecioFinal()-promocion.getBsDescuento());
									detalleNuevo.setPrecioFinal(promocion.getBsDescuento());
								}	
								else{ // SI EL DESCUENTO BASE ES MAYOR AL DEL COMBO (NO HACE NADA) 
									detalleNuevo.setMontoDctoCombo(detalleNuevo.getPrecioFinal()*porcentajeDescuento/100);
									detalleNuevo.setPrecioFinal(detalleNuevo.getPrecioFinal()-detalleNuevo.getMontoDctoCombo());
								}
							}
							//FIN
							
							detalleNuevo.setCodPromocion(promocion.getCodPromocion());
							detalles.addElement(detalleNuevo);
							detallesCombos.addElement(new Integer(detalles.size()-1));
							dt.setCantidad(dt.getCantidad()-cantidadAAsignar);
							dt.setCantidadCambiada(false);
							dt.setVariacion(0);
							
							//Reaplico descuento empleado y corporativo en ese orden
							if(venta!=null && 
									detalleNuevo.contieneAlgunaCondicion(condicionesCorporativa)){
								((DetalleTransaccion)detalleNuevo).setMontoDctoEmpleado(detalleNuevo.getPrecioFinal()*Sesion.getDesctoVentaEmpleado()/100);
								((DetalleTransaccion)detalleNuevo).setPrecioFinal(detalleNuevo.getPrecioFinal()-((DetalleTransaccion)detalleNuevo).getMontoDctoEmpleado());
							}
							CondicionVenta condicionCorporativa = detalleNuevo.getPrimeraCondicion(condicionesCorporativa);
							if(condicionCorporativa!=null){
								detalleNuevo.setMontoDctoCorporativo(detalleNuevo.getPrecioFinal()*condicionCorporativa.getPorcentajeDescuento()/100);
								detalleNuevo.setPrecioFinal(detalleNuevo.getPrecioFinal()-detalleNuevo.getMontoDctoCorporativo());
							}
							
							//Aplico de nuevo ahorro en compra sobre detalleNuevo
							double montoAhorroCompraDetalleNuevo = 0;
							if(cvAhorroC!=null){
								montoAhorroCompraDetalleNuevo = detalleNuevo.getPrecioFinal()*cvAhorroC.getPorcentajeDescuento()/100;
							}
							detalleNuevo.setPrecioFinal(MathUtil.roundDouble(detalleNuevo.getPrecioFinal()-montoAhorroCompraDetalleNuevo));
							
							if(venta!=null)
								detalleNuevo.setMontoImpuesto(venta.calcularImpuesto(detalleNuevo.getProducto(), detalleNuevo.getPrecioFinal()));
							else if(apartado!=null) {
								detalleNuevo.setMontoImpuesto(apartado.calcularImpuesto(detalleNuevo.getProducto(), detalleNuevo.getPrecioFinal()));
							}
						
						} else {
							cantidadAAsignar = dt.getCantidad();
							dt.addCondicion(cv);
							dt.setCondicionVenta(dt.construirCondicionVentaString());
							
							//Eliminar ahorro en compra
							if(cvAhorroC!=null){
								dt.setPrecioFinal((dt.getPrecioFinal()*100)/(100-cvAhorroC.getPorcentajeDescuento()));
							}
							
							//Eliminar empleado y corporativo si lo tenía para luego recalcular
							double descuentoEmpleadoAnterior = 0;
							if(venta!=null)
								descuentoEmpleadoAnterior = ((DetalleTransaccion)dt).getMontoDctoEmpleado();
							double precioInicial = dt.getPrecioFinal()+descuentoEmpleadoAnterior+dt.getMontoDctoCorporativo();
							dt.setPrecioFinal(precioInicial);
							
							//Cambio para que aplique la Combos con Precio Final 2012-10-31
							if(!(promocion.getBsDescuento()>0)){
								
								dt.setMontoDctoCombo(dt.getPrecioFinal()*porcentajeDescuento/100);
								dt.setPrecioFinal(dt.getPrecioFinal()-dt.getMontoDctoCombo());		
							}
							else{
								//PROMOCION N PRODUCTOS, PRECIO FINAL Y
								if(dt.getPrecioFinal()>promocion.getBsDescuento()){
									dt.setMontoDctoCombo(dt.getPrecioFinal()-promocion.getBsDescuento());
									dt.setPrecioFinal(promocion.getBsDescuento());
								}	
								else{ // SI EL DESCUENTO BASE ES MAYOR AL DEL COMBO (NO HACE NADA) 
									dt.setMontoDctoCombo(dt.getPrecioFinal()*porcentajeDescuento/100);
									dt.setPrecioFinal(dt.getPrecioFinal()-dt.getMontoDctoCombo());
								}
							}
							//FIN
							//Recalculo descuento empleado y corporativo
							if(venta!=null && 
									dt.contieneAlgunaCondicion(condicionesEmpleados)){
								((DetalleTransaccion)dt).setMontoDctoEmpleado(dt.getPrecioFinal()*Sesion.getDesctoVentaEmpleado()/100);
								((DetalleTransaccion)dt).setPrecioFinal(dt.getPrecioFinal()-((DetalleTransaccion)dt).getMontoDctoEmpleado());
							}
							CondicionVenta condicionCorporativa = dt.getPrimeraCondicion(condicionesCorporativa);
							if(condicionCorporativa!=null){
								dt.setMontoDctoCorporativo(dt.getPrecioFinal()*condicionCorporativa.getPorcentajeDescuento()/100);
								dt.setPrecioFinal(dt.getPrecioFinal()-dt.getMontoDctoCorporativo());
							}
							
							//Recalculo ahorro en compra
							double montoAhorroCompraDt =0;
							if(cvAhorroC!=null)
								montoAhorroCompraDt = dt.getPrecioFinal()*cvAhorroC.getPorcentajeDescuento()/100;
							dt.setPrecioFinal(MathUtil.roundDouble(dt.getPrecioFinal()-montoAhorroCompraDt));
							
							
							if(venta!=null){
								dt.setMontoImpuesto(venta.calcularImpuesto(dt.getProducto(), dt.getPrecioFinal()));
								dt.setCantidad(cantidadAAsignar);
								dt.setCantidadCambiada(false);
								dt.setVariacion(0);
								dt.setCodPromocion(promocion.getCodPromocion());
								venta.getDetallesTransaccion().addElement((DetalleTransaccion)dt.clone());
								detallesCombos.addElement(new Integer(venta.getDetallesTransaccion().size()-1));
								
								dt.setCantidad(0);
								dt.setCantidadCambiada(false);
								dt.setVariacion(0);
								//venta.getDetallesAEliminar().addElement(new Integer(j));
							} else if(apartado!=null){
								dt.setMontoImpuesto(apartado.calcularImpuesto(dt.getProducto(), dt.getPrecioFinal()));
								dt.setCantidad(cantidadAAsignar);
								dt.setCantidadCambiada(false);
								dt.setVariacion(0);
								dt.setCodPromocion(promocion.getCodPromocion());	
								apartado.getDetallesServicio().addElement((DetalleServicio)dt.clone());
								detallesCombos.addElement(new Integer(apartado.getDetallesServicio().size()-1));
								
								dt.setCantidad(0);
								dt.setCantidadCambiada(false);
								dt.setVariacion(0);
								//apartado.getDetallesAEliminar().addElement(new Integer(j));
							}						
						}
						productoInvolucrado.setCantidadMinima((int)(productoInvolucrado.getCantidadMinima()-cantidadAAsignar));
						if(productoInvolucrado.getCantidadMinima()==0){
							break;
						}
					}
				}			
			}
		}
		Auditoria.registrarAuditoria("Aplicada promoción de límite de productos en descuento: "+cv.getCodPromocion(), 'T');
		/*for(int i=0;i<detallesAActualizar.size();i++){
			if(!((Detalle)detallesAActualizar.elementAt(i)).contieneAlgunaCondicion(Sesion.condicionesCombo) || !Sesion.promocionesAcumulativas){
				try{
					if(venta!=null){
						BECOActualizadorPrecios.posicionesCambiadas = true;
						(new BECOActualizadorPrecios()).actualizarPreciosExt(((Detalle)detallesAActualizar.elementAt(i)).getProducto(), CR.meVenta.getVenta());
					} else if(apartado!=null){
						BECOActualizadorPreciosSAC.posicionesCambiadas = true;
						(new BECOActualizadorPreciosSAC()).actualizarPreciosExt(((Detalle)detallesAActualizar.elementAt(i)).getProducto(), (Servicio)apartado, true);
					}
				} catch(Exception e){
					e.printStackTrace();
					logger.debug("aplicarCondicionDescuentoEnCombo(Vector, Vector, Vector, float, Producto)", e);
				}
			}
		}*/
		
		if(venta!=null && BECOActualizadorPrecios.posicionesCambiadas && detallesAActualizar.size()!=0)
			BECOActualizadorPrecios.posicionesCambiadas=false;
		else if(apartado!=null && BECOActualizadorPreciosSAC.posicionesCambiadas && detallesAActualizar.size()!=0)
			BECOActualizadorPreciosSAC.posicionesCambiadas=false;
		
		return detallesCombos;
	}

	/**
	 * Dado el vector de detalles de transacción, chequea si hay alguna promoción de menor prioridad
	 * que se pueda deshacer para aplicar la promocion "promocion", en caso afirmativo, la deshace
	 * y retorna los detalles afectados por el cambio
	 * 
	 * @param isRecalculo Indica si se trata de una operación de recálculo de apartado
	 * @param detalles  Todos los detalles de servicio o de transacción
	 * @param detallesCombos Detalles de transacción que se encuentran actualmente con condicion de combo
	 * @param promocion Promoción que se desea aplicar
	 * @param condicionesNoPermitidas Condiciones con las cuales es necesario comparar prioridades de promociones
	 * @return detallesAActualizar Detalles que salieron de combo
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private static Vector<? extends Detalle> resolverPrioridadesCombos(boolean isRecalculo, 
			Vector<Detalle> detalles, Vector<Integer> detallesCombos, PromocionExt promocion, 
			Vector<String> condicionesNoPermitidas) {
		Vector<Detalle> detallesAActualizar = new Vector<Detalle>();
		if(!Detector.verificaCantidadesMinimasCombo(promocion, condicionesNoPermitidas, false) && 
				Detector.verificaCantidadesMinimasCombo(promocion, condicionesNoPermitidas, true) && 
				!isRecalculo){
			for(int i=0;i<detalles.size() && Detector.verificaCantidadesMinimasCombo(promocion, condicionesNoPermitidas, true);i++){
				Detalle detalle = (Detalle)detalles.elementAt(i);
				Vector<PromocionExt> promocionesCombo = PromocionExt.getPromocionesCombo(detalle.getProducto(), 0);
				//La promocion a aplicar incluye a este detalle y el detalle tiene algun otro combo
				if(promocion.comboContieneProducto(detalle.getProducto()) && 
						promocionesCombo.size()>=1 && detalle.contieneAlgunaCondicion(Sesion.condicionesCombo)){
					CondicionVenta condicionDetalle = detalle.getPrimeraCondicion(Sesion.condicionesCombo);
					//Verifico las prioridades
					PromocionExt promoAplicada = PromocionExt.getPromocionAplicadaCombo(condicionDetalle.getCodPromocion());
					int prioridadAplicada = promoAplicada.getPrioridad(); 
					Auditoria.registrarAuditoria("Resolviendo prioridades entre combos ", 'T');
					if(prioridadAplicada>promocion.getPrioridad()){
						UtilesActualizadorPrecios.deshacerCombo(
								detallesCombos, //No deberia ocurrir que haya que eliminar algun detalle en este punto
								detallesAActualizar,
								0, 
								detalle,
								promocion);
					}
				}
			}
		}
		return detallesAActualizar;
	}
	
	/**
	 * Aplica la condición de venta "Promocion" a los productos especificados
	 * @param prod Producto al que se le aplicara el descuento a empaque.
	 * @param detallesN Vector de posiciones del producto .
	 * @param prodsEmpaque Cantidad de productos para el empaque
	 * @param posicionMejor Entero que indica la posición que posee el mejor descuento
	 * @param dctoActual Vector que indica el descuento actual que posee el producto
	 * @param puntoDeChequeo Entero que indica la posición en el detalle detransacción chequeado hasta los momentos
	 * @return DetalleTransaccion - Nuevo detalle de transacción con los productos a los que se les aplicó la promoción
	 * 
	 * NOTA: SOLO VALIDO PARA SERVICIO
	 * 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public static DetalleServicio aplicarCondicionPromocion(Producto prod, Vector<Integer> detallesN, Vector<?> dctoActual, int puntoDeChequeo, Servicio serv) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarCondicionPromocion(Producto, Vector, Vector, int) - start");
		}

		String supervisor = ((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCodSupervisor();
		int codPromocion = ((Integer)dctoActual.elementAt(2)).intValue();
		String tipCaptura = ((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getTipoCaptura();
		double pFinal = ((Double)dctoActual.elementAt(0)).doubleValue();
		float cantidadAsignar = ((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCantidad();
		DetalleServicio dt = ((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue()));
		pFinal = (pFinal<dt.getPrecioFinal())?MathUtil.roundDouble(pFinal):MathUtil.roundDouble(dt.getPrecioFinal());
		DetalleServicio nuevoDetalleTrans = 
			new DetalleServicio(
				((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getProducto(),
				cantidadAsignar,
				Sesion.condicionNormal,
				supervisor,
				pFinal,
				serv.calcularImpuesto(
						((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getProducto(), 
						pFinal),
				tipCaptura , 
				codPromocion, 
				"");
		try{
			nuevoDetalleTrans.addCondicion((CondicionVenta)dctoActual.elementAt(3));
			nuevoDetalleTrans.setCondicionVenta(nuevoDetalleTrans.construirCondicionVentaString());
			((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).setCantidad(0);
			((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).setCantidadCambiada(false);
			((DetalleServicio)serv.getDetallesServicio().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).setVariacion(0);
			serv.getDetallesAEliminar().addElement(detallesN.elementAt(puntoDeChequeo));
		}catch(Exception e) {
			//Nada, la extensión de promociones no está activa
		}
		
		// Registramos la auditoría de esta función
		Sesion.setUbicacion("FACTURACION","aplicarCondicionPromocion");
		Auditoria.registrarAuditoria("Aplicada Promo " + nuevoDetalleTrans.getCodPromocion() + " Prod " + prod.getCodProducto() + " P.Reg. " + prod.getPrecioRegular() + " P.Prom. " + nuevoDetalleTrans.getPrecioFinal(),'T');
	
		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarCondicionPromocion(Producto, Vector, Vector, int) - end");
		}
		return nuevoDetalleTrans;
	}
	
	/**
	 * Aplica la condición de venta "Descuento corporativo" a el producto especificado
	 * @param prod Producto al que se le aplicara el descuento a empaque.
	 * @param posicionDetalles Vector de posiciones donde se encuentran los detalles de la transacción que se desean manejar
	 * @param prodsEmpaque Cantidad de productos para el empaque
	 * @param posicionMejor Entero que indica la posición que posee el mejor descuento
	 * @param dctoActual Vector que indica el descuento actual que posee el producto
	 * @param puntoDeChequeo Entero que indica la posición en el detalle detransacción chequeado hasta los momentos
	 * @return DetalleTransaccion - Nuevo detalle de transacción con los productos a los que se les aplicó el descuento a COLABORADOR
	 * 
	 * NOTA: Tanto para venta como para servicio
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public static Detalle aplicarCondicionDescuentoCorporativo(Producto prod, Vector<Integer> posicionDetalles, Vector<Object> dctoActual, int puntoDeChequeo) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarCondicionDescuentoCorporativo(Producto, Vector, Vector, int) - start");
		}
		
		Venta venta = CR.meVenta.getVenta();
		Apartado apartado = CR.meServ.getApartado();
		Vector detalles = new Vector<Detalle>();
		if(venta!=null)
			detalles = venta.getDetallesTransaccion();
		else if(apartado!=null)
			detalles = apartado.getDetallesServicio();

		Detalle nuevoDetalleTrans = null;
		double pFinal = 0.0;
		double montoDctoCorporativo = 0.0;
		
		if (Sesion.desctosAcumulativos){
			double precioAnterior;
			double nvoImpuesto = 0;
			Vector<String> condicionesCorporativo = new Vector<String>();
			condicionesCorporativo.addElement(Sesion.condicionDescuentoCorporativo);
			// Calculamos la condición de venta acumulativa para los productos especificados
			for (int i=0; i<posicionDetalles.size(); i++) { 
				//if(!((Detalle)CR.meVenta.getVenta().getDetallesTransaccion().elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).contieneAlgunaCondicion(condicionesCorporativo)){
					precioAnterior = ((Detalle)detalles.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).getPrecioFinal();
					
					if(venta!=null){
						montoDctoCorporativo = (precioAnterior * venta.getDescuentoCorporativoAplicado()) / 100;
						pFinal = precioAnterior - montoDctoCorporativo;
						nvoImpuesto = venta.calcularImpuesto(prod, pFinal);
						((Detalle)detalles.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).setPrecioFinal(pFinal);
						((Detalle)detalles.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).setMontoImpuesto(nvoImpuesto);
						((Detalle)detalles.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).setMontoDctoCorporativo(montoDctoCorporativo);
						
						// Chequeamos las condiciones de Venta
						Detalle dt = (Detalle)venta.getDetallesTransaccion().elementAt(((Integer)posicionDetalles.elementAt(i)).intValue());
						dt.addCondicion(((CondicionVenta)dctoActual.elementAt(3)));
						dt.setCondicionVenta(dt.construirCondicionVentaString());
					}
					else if (apartado!=null){
						montoDctoCorporativo = (precioAnterior * apartado.getDescuentoCorporativoAplicado()) / 100;
						pFinal = precioAnterior - montoDctoCorporativo;
						nvoImpuesto = apartado.calcularImpuesto(prod, pFinal);
						((Detalle)detalles.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).setPrecioFinal(pFinal);
						((Detalle)detalles.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).setMontoImpuesto(nvoImpuesto);
						((Detalle)detalles.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue())).setMontoDctoCorporativo(montoDctoCorporativo);
						
						// Chequeamos las condiciones de Venta
						Detalle dt = (Detalle)apartado.getDetallesServicio().elementAt(((Integer)posicionDetalles.elementAt(i)).intValue());
						dt.addCondicion(((CondicionVenta)dctoActual.elementAt(3)));
						dt.setCondicionVenta(dt.construirCondicionVentaString());
					}
				//}
			}
		} else {
			String supervisor = ((Detalle)detalles.elementAt(((Integer)posicionDetalles.elementAt(puntoDeChequeo)).intValue())).getCodSupervisor();
			String tipCaptura = ((Detalle)detalles.elementAt(((Integer)posicionDetalles.elementAt(puntoDeChequeo)).intValue())).getTipoCaptura();
			String tipoEntrega = ((Detalle)detalles.elementAt(((Integer)posicionDetalles.elementAt(puntoDeChequeo)).intValue())).getTipoEntrega();
			pFinal = ((Double)dctoActual.elementAt(0)).doubleValue();
			montoDctoCorporativo = prod.getPrecioRegular() - pFinal;
			float cantidadAsignar = ((Detalle)CR.meVenta.getVenta().getDetallesTransaccion().elementAt(((Integer)posicionDetalles.elementAt(puntoDeChequeo)).intValue())).getCantidad();
			pFinal = MathUtil.roundDouble(pFinal);
			if(venta!=null){
				nuevoDetalleTrans = new DetalleTransaccion(
						((DetalleTransaccion)detalles.elementAt(((Integer)posicionDetalles.elementAt(puntoDeChequeo)).intValue())).getCodVendedor(), 
						prod, cantidadAsignar,
						(String)dctoActual.elementAt(1), supervisor, pFinal,
						venta.calcularImpuesto(prod, pFinal), tipCaptura,
						-1, tipoEntrega);
			} else if(apartado!=null){
				nuevoDetalleTrans = new DetalleServicio(
						((DetalleServicio)apartado.getDetallesServicio().elementAt(((Integer)posicionDetalles.elementAt(puntoDeChequeo)).intValue())).getProducto(),
						cantidadAsignar,
						Sesion.condicionNormal,
						supervisor,
						((DetalleServicio)apartado.getDetallesServicio().elementAt(((Integer)posicionDetalles.elementAt(puntoDeChequeo)).intValue())).getProducto().getPrecioRegular(),
						apartado.calcularImpuesto(
								((DetalleServicio)apartado.getDetallesServicio().elementAt(((Integer)posicionDetalles.elementAt(puntoDeChequeo)).intValue())).getProducto(), 
								((DetalleServicio)apartado.getDetallesServicio().elementAt(((Integer)posicionDetalles.elementAt(puntoDeChequeo)).intValue())).getPrecioFinal()),
						tipCaptura , 
						0, 
						"");
			}
			
			nuevoDetalleTrans.addCondicion(((CondicionVenta)dctoActual.elementAt(3)));
			Vector<String> condiciones = new Vector<String>();
			condiciones.addElement(Sesion.condicionDesctoEmpleado);
			condiciones.addAll(Sesion.condicionesCombo);
			if(nuevoDetalleTrans.contieneAlgunaCondicion(condiciones)) {
				nuevoDetalleTrans.setCondicionVenta(Sesion.condicionMixta);
			} else {
				nuevoDetalleTrans.setCondicionVenta((String)dctoActual.elementAt(1));
			}
			
			nuevoDetalleTrans.setMontoDctoCorporativo(montoDctoCorporativo);
			
		}
		Auditoria.registrarAuditoria("Aplicada promoción de descuento corporativo", 'T');
		
		if (posicionDetalles.size() > 0) {
			// Registramos la auditoría de esta función
			Sesion.setUbicacion("FACTURACION","aplicarCondicionDescuentoCorporativo");
			if(venta!=null)
				Auditoria.registrarAuditoria("Aplicado Descuento a Corporativo a " + venta.getCliente().getCodCliente() + " Prod." + prod.getCodProducto() + ". PFinal." + pFinal ,'T');
			else if(apartado!=null)
				Auditoria.registrarAuditoria("Aplicado Descuento a Corporativo a " + apartado.getCliente().getCodCliente() + " Prod." + prod.getCodProducto() + ". PFinal." + pFinal ,'T');
		}
			
		if (logger.isDebugEnabled()) {
			logger.debug("aplicarCondicionDescuentoCorporativo(Producto, Vector, Vector, int) - end");
		}
		return nuevoDetalleTrans;
	
	}
	
	/**
	 * Aplica la promoción de producto gratis
	 * @param desctoActual
	 * @param detallesN vector de detalles normales
	 * @return Vector de detalles producidos al aplicar esta promocion
	 * @throws ConexionExcepcion 
	 * @throws BaseDeDatosExcepcion 
	 * @throws PagoExcepcion 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public static Vector<Detalle> aplicarCondicionProductoGratis(PromocionExt promocion) throws PagoExcepcion, BaseDeDatosExcepcion, ConexionExcepcion{
		
		Venta venta =  CR.meVenta.getVenta();
		Apartado apartado = CR.meServ.getApartado();
		Vector<? extends Detalle> detalles =  new Vector<Detalle>();
		if(venta!=null)
			detalles = venta.getDetallesTransaccion();
		else if(apartado!=null)
			detalles = apartado.getDetallesServicio();
		
		Vector<Detalle> resultado = new Vector<Detalle>();
		Vector<String> condicionEmpaque = new Vector<String>();
		condicionEmpaque.addElement(Sesion.condicionEmpaque);
		//PromocionExt promo = (PromocionExt)desctoActual.elementAt(4);
		Detalle nuevoDetalle = null;
		//Buscando el detalle de menor precio final
		Detalle detalleMenorPrecio = null;
		double menorPrecio = Double.MAX_VALUE;
		Vector<String> condicionProductoGratis = new Vector<String>();
		condicionProductoGratis.addElement(Sesion.condicionProductoGratis);
		
		Vector<String> condicionesEmpaque = new Vector<String>();
		condicionesEmpaque.addElement(Sesion.condicionEmpaque);
		int cantProductos = 0;
		int posicionDeshacer = 0;
		int posicionMenorPrecio = 0;
		Detalle antiguoDetalleGratis = null;
		Detalle detalleDeshacer = null;
		for(int i=0;i<detalles.size();i++){
			cantProductos+=((Detalle)detalles.elementAt(i)).getCantidad();
			if(((Detalle)detalles.elementAt(i)).contieneCondicion(condicionProductoGratis)){
				//Deshacer la promocion
				detalleDeshacer = ((Detalle)detalles.elementAt(i));
				antiguoDetalleGratis = (Detalle)deshacerProductoGratis(detalleDeshacer);
				posicionDeshacer = i;
			}
			if(((Detalle)detalles.elementAt(i)).getPrecioFinal()<=menorPrecio && ((Detalle)detalles.elementAt(i)).getCantidad()!=0){
				detalleMenorPrecio = ((Detalle)detalles.elementAt(i));
				menorPrecio = ((Detalle)detalles.elementAt(i)).getPrecioFinal();
				posicionMenorPrecio=i;
			}
		}
		//Si el producto que estaba gratis antes y sigue siendo el mas barato ahora
		if(posicionDeshacer!=posicionMenorPrecio && posicionDeshacer!=0 && posicionMenorPrecio!=0){
			detalleDeshacer.setCantidad(0);
			detalleDeshacer.setVariacion(0);
			detalleDeshacer.setCantidadCambiada(false);
			resultado.addElement(antiguoDetalleGratis);
		}
			
		if(cantProductos>=promocion.getCantMinima()){
			CondicionVenta cv = new CondicionVenta(Sesion.condicionProductoGratis,promocion.getCodPromocion(),promocion.getPorcentajeDescuento(),promocion.getNombrePromocion(),promocion.getPrioridad());
			
			detalleMenorPrecio.setCantidad(detalleMenorPrecio.getCantidad()-1);
			resultado.addElement(((Detalle)detalleMenorPrecio.clone()));
			detalleMenorPrecio.setCantidad(0);
			detalleMenorPrecio.setCantidadCambiada(false);
			detalleMenorPrecio.setVariacion(0);
			
			nuevoDetalle = (Detalle)detalleMenorPrecio.clone();
			nuevoDetalle.setCantidad(1);
			nuevoDetalle.setCantidadCambiada(false);
			nuevoDetalle.setVariacion(0);
			
			nuevoDetalle.addCondicion(cv);
			nuevoDetalle.setCondicionVenta(nuevoDetalle.construirCondicionVentaString());
			//double montoADescontar = 0;
			
			if(venta!=null){
				
				CR.me.mostrarAviso("Aplicada promoción "+promocion.getNombrePromocion()+". Producto: "+detalleMenorPrecio.getProducto().getCodProducto()+" al "+promocion.getPorcentajeDescuento()+"% de descuento", false);
				venta.setPromocionProductoGratis(promocion);
			} else if(apartado!=null){
				//TODO: Caso de apartado
				//montoADescontar = (nuevoDetalle.getPrecioFinal()+nuevoDetalle.getMontoImpuesto())*promocion.getPorcentajeDescuento()/100;
			}
			
			
			nuevoDetalle.setCodPromocion(promocion.getCodPromocion());
			//nuevoDetalle.setMontoDctoCombo(montoADescontar);
		}
		Auditoria.registrarAuditoria("Aplicada promoción de producto más barato gratis: "+promocion.getCodPromocion()+". Producto afectado: "+detalleMenorPrecio.getProducto().getCodProducto(), 'T');
		if(nuevoDetalle!=null)
			resultado.addElement(nuevoDetalle);
		
		return resultado;
	}
	
	/**
	 * Deshace la promoción producto gratis en una transacción o servicio
	 * @param detalleDeshacer
	 * @return resultado : 	En la primera casilla vector de detallesN creados
	 * 						En la segunda casilla vector de detalles en combo creados
	 **/
	public static Detalle deshacerProductoGratis(Detalle detalleDeshacer){
		detalleDeshacer.removeCondicion(new CondicionVenta(Sesion.condicionProductoGratis));
		detalleDeshacer.setCondicionVenta(detalleDeshacer.construirCondicionVentaString());
		return (Detalle)detalleDeshacer.clone();
	}
	
	/**
	 * Obtiene la promocion de tipo combo de mayor prioridad
	 * @param prod Producto cuyas promociones se quiere obtener
	 * @return Vector con de objetos PromocionExt, se sigue el mismo
	 * estándar de verificarDatosPromocion:
	 * En la primera casilla el codigo de la promocion aplicada,
	 * en la segunda el precio final del producto. En la 
	 * tercera casilla se agrega el objeto promocion completo
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Vector<Object>> verificarDatosCombo(Producto prod){
		if (logger.isDebugEnabled()) {
			logger.debug("verificarDatosCombo(Producto) - start");
		}
		Vector<Vector<Object>> datosCombo = new Vector<Vector<Object>>();
		/*
		Venta venta = CR.meVenta.getVenta();
		Apartado apartado = CR.meServ.getApartado();
		*/
		PromocionExt promocionAplicada = null;
		
		// Obtenemos las promociones del producto en el detalle de transaccion
		Vector<PromocionExt> promociones = new Vector<PromocionExt>();
		promociones = PromocionExt.getPromocionesCombo(prod, 0);
		
		//EL CODIGO DE ABAJO NO HACE FALTA, VIENEN ORDENADAS POR PRIORIDAD
		// Buscamos la promocion con prioridad mas alta
		/*int posicionMayorPrioridad = 0;
		for (int i=0; i<promociones.size(); i++) {
			if (promocionAplicada == null) {
				promocionAplicada = (PromocionExt)promociones.elementAt(i);
				posicionMayorPrioridad = i;
			} else {
				if(((PromocionExt)promociones.elementAt(i)).getPrioridad() < promocionAplicada.getPrioridad()) {
					promocionAplicada = (PromocionExt)promociones.elementAt(i);
					posicionMayorPrioridad = i;
				}
			}
		}*/
		
		//Seteo el tipo de promoción aplicada al producto
		for(int i=0;i<promociones.size();i++){
			if(promociones.size()!=0){
				prod.setTipoPromocionAplicada(((PromocionExt)promociones.elementAt(0)).getTipoPromocion());
				promocionAplicada = (PromocionExt)promociones.elementAt(i);
			}
			
			// Aplicamos la promocion (si existe) al producto
			Vector<Object> result = null;
			if (promocionAplicada != null) {
				result = new Vector<Object>();
				result.addElement(new Integer(promocionAplicada.getCodPromocion()));
				if (promocionAplicada.getPorcentajeDescuento()!= 0) { // Promocion de Descuento
					double mtoDcto = prod.getPrecioRegular() * (promocionAplicada.getPorcentajeDescuento() / 100);
					result.addElement(new Double(prod.getPrecioRegular() - mtoDcto));
				} else {
					if (promocionAplicada.getPrecioFinal() != 0) { // Promocion de Precio a Precio
						result.addElement(new Double(promocionAplicada.getPrecioFinal()));
					}
					else if(promocionAplicada.getBsDescuento()>0){ //PROMOCION N PRODUCTOS, PRECIO FINAL Y
						result.addElement(new Double(promocionAplicada.getBsDescuento()));
					}
				}
				result.addElement(promocionAplicada);
			}
			datosCombo.addElement(result);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("verificarDatosCombo(Producto) - end");
		}
		return datosCombo;
	}
	
	/**
	 * Crea el vector con los datos del descuento correspondiente a cualquier combo
	 * @param datosCombos
	 * @return Vector
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public static Vector<Object> crearDescuentoCombo(Vector<Object> datosCombos) {
		//Existen promociones de combo vigentes para el producto, y el empaque no es lo mas conveniente
		PromocionExt promocionAAplicar = (PromocionExt)datosCombos.elementAt(2);
		Vector<Object> promo = new Vector<Object>();
		promo.addElement(new Double(0.0));  							//PRECIO FINAL
		String condicion = "";														
		switch (promocionAAplicar.getTipoPromocion()) {	
			case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_DEPARTAMENTO:
			case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_LINEA:
			case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_MARCA:
			case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_REFERENCIA:
			case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_PRODUCTO:
			case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_SECCION:
			case Sesion.TIPO_PROMOCION_COMBO_CANTIDADES_CATEGORIA:
				condicion = Sesion.condicionComboPorCantidades;
				break;
			case Sesion.TIPO_PROMOCION_DESCUENTO_EN_COMBO_LINEA:
			case Sesion.TIPO_PROMOCION_DESCUENTO_EN_COMBO:
				condicion = Sesion.condicionDescuentoEnCombo;		
				break;
			case Sesion.TIPO_PROMOCION_PRODUCTO_GRATIS:
				condicion = Sesion.condicionProductoGratis;
				break;
			default:
				break;
		}
		promo.addElement(condicion);									// CONDICION VENTA (string)
		promo.addElement((Integer)datosCombos.elementAt(0));			//CODIGO DE LA PROMOCION
		CondicionVenta cv = new CondicionVenta(condicion, ((Integer)datosCombos.elementAt(0)).intValue(),promocionAAplicar.getPorcentajeDescuento(), promocionAAplicar.getNombrePromocion(),promocionAAplicar.getPrioridad());
		promo.addElement(cv);											//CONDICION VENTA (Objeto)
		promo.addElement(promocionAAplicar);							//PROMOCIONEXT
		return promo;
	}
	

	/**
	 * Obtiene el descuento correspondiente a la promocion de ahorro
	 * en compra (la de mayor prioridad
	 * @param codProducto
	 * @return Vector
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Object> getDescuentosAhorroEnCompra(String codProducto){
		double montoTotal = 0;
		Venta venta = CR.meVenta.getVenta();
		Apartado apartado = CR.meServ.getApartado();
		if(venta!=null)
			montoTotal = venta.consultarMontoTrans();
		else if(apartado!=null)
			montoTotal = apartado.consultarMontoServ();
			
		Vector<Object> descuento = new Vector<Object>();
		PromocionExt promocionAplicada = null;
		Vector<PromocionExt> promocionesAhorroEnCompra = PromocionExt.getAhorrosEnCompraPorProducto(codProducto, 0, montoTotal);
		//Buscamos la promocion con prioridad mas alta
		for (int i=0; i<promocionesAhorroEnCompra.size(); i++) {
			if (promocionAplicada == null) {
				promocionAplicada = (PromocionExt)promocionesAhorroEnCompra.elementAt(i);
			} else {
				if(((PromocionExt)promocionesAhorroEnCompra.elementAt(i)).getPrioridad() < promocionAplicada.getPrioridad()) {
					promocionAplicada = (PromocionExt)promocionesAhorroEnCompra.elementAt(i);
					
				}
			}
		}
		descuento = getDescuentosAhorroEnCompra(promocionAplicada);
		return descuento;
	}
	
	/**
	 * Obtiene el descuento correspondiente a la promocion de ahorro
	 * en compra a partir de la promocion indicada
	 * @param codProducto
	 * @return Vector
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Object> getDescuentosAhorroEnCompra(PromocionExt promocionAplicada){
		Vector<Object> descuento = new Vector<Object>();
		
		if(promocionAplicada!=null){
			descuento.addElement(new Double(0.00));							//PRECIO FINAL
			descuento.add(Sesion.condicionDescuentoEnProductos);			//STRING DE LA CONDICION DE VENTA
			
			descuento.addElement(new Integer(promocionAplicada.getCodPromocion()));		//CODIGO DE LA PROMOCION
			CondicionVenta cv = new CondicionVenta(Sesion.condicionDescuentoEnProductos, 
													promocionAplicada.getCodPromocion(),
													promocionAplicada.getPorcentajeDescuento(), 
													promocionAplicada.getNombrePromocion(),
													promocionAplicada.getPrioridad());
			descuento.addElement(cv);										//CONDICION VENTA (Objeto)
			descuento.addElement(promocionAplicada);						//PROMOCIONEXT
		}
		return descuento;
	}
	
	   
    /**
	 * Aplica la condicion de descuento en productos, que incluye las siguientes promociones:
	 * 	- Ahorro en compras
	 * @param prod Producto al que se le aplicara el descuento a empaque.
	 * @param detallesN Vector de posiciones del producto .
	 * @param prodsEmpaque Cantidad de productos para el empaque
	 * @param posicionMejor Entero que indica la posición que posee el mejor descuento
	 * @param dctoActual Vector que indica el descuento actual que posee el producto
	 * @param puntoDeChequeo Entero que indica la posición en el detalle detransacción chequeado hasta los momentos
	 * @return DetalleTransaccion - Nuevo detalle de transacción con los productos a los que se les aplicó la promoción
	 * 
	 * Funciona para ventas y servicios
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' e 'Iterator'
	* Fecha: agosto 2011
	*/
	public static Detalle aplicarDescuentoEnProductos(Vector<Integer> posicionDetalles, 
			Vector<Object> dctoActual) throws XmlExcepcion, FuncionExcepcion, 
			BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarDescuentoEnProductos(Producto, Vector, Vector, int) - start");
		}
		
		Venta venta = CR.meVenta.getVenta();
		Apartado apartado = CR.meServ.getApartado();
		Vector<? extends Detalle> detalles = new Vector<Detalle>();
		double montoVenta=0;
		if(venta!=null)
			detalles = venta.getDetallesTransaccion();
		else if(apartado!=null)
			detalles = apartado.getDetallesServicio();
		
		DetalleTransaccion nuevoDetalleTrans = null;
		for(int i=0;i<posicionDetalles.size();i++){
			Detalle detalle = null;
			if(venta!=null){
				detalle = ((DetalleTransaccion)detalles.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue()));
				montoVenta = venta.getMontoTransaccion();
			}
			else if(apartado!=null){
				detalle = ((DetalleServicio)detalles.elementAt(((Integer)posicionDetalles.elementAt(i)).intValue()));
				montoVenta = apartado.getMontoServ();
			}
			Vector<String> condicionesDescuentoEnProductos = new Vector<String>();
			condicionesDescuentoEnProductos.addElement(Sesion.condicionDescuentoEnProductos);
			if(!detalle.contieneAlgunaCondicion(condicionesDescuentoEnProductos)){
				PromocionExt promocionAAplicar = (PromocionExt)dctoActual.elementAt(4);
				if(montoVenta>=promocionAAplicar.getMontoMinimo()){
					double pFinal = MathUtil.roundDouble(detalle.getPrecioFinal()-(detalle.getPrecioFinal()*promocionAAplicar.getPorcentajeDescuento()/100));
					pFinal = MathUtil.roundDouble(pFinal);
					detalle.setPrecioFinal(pFinal);
					if(venta!=null)
						detalle.setMontoImpuesto(venta.calcularImpuesto(detalle.getProducto(), pFinal));
					else if(apartado!=null)
						detalle.setMontoImpuesto(apartado.calcularImpuesto(detalle.getProducto(), pFinal));
					
					try{
						detalle.addCondicion((CondicionVenta)dctoActual.elementAt(3));
						detalle.setCondicionVenta(detalle.construirCondicionVentaString());
						detalle.getProducto().setTipoPromocionAplicada(promocionAAplicar.getTipoPromocion());
					}catch(Exception e) {
						//Nada, la extensión de promociones no está activa
					}
				}
				Auditoria.registrarAuditoria("Aplicada promoción de Ahorro en Compra: "+promocionAAplicar.getCodPromocion()+". Producto: "+detalle.getProducto().getCodProducto(), 'T');
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarDescuentoEnProductos(Producto, Vector, Vector, int) - end");
		}
		return nuevoDetalleTrans;
	}
	
	/**
	 * Recalcula las posiciones de cada detalle dentro de los vectores que recibe como parametro
	 * @return Vector que contiene
	 *  nuevosEmpaques
	 *  nuevosNoEmpaques
	 *  nuevosCombos
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Vector<Integer>> actualizarPosiciones(){
		Venta venta = CR.meVenta.getVenta();
		Apartado apartado = CR.meServ.getApartado();
		Vector<? extends Detalle> detalles = new Vector<Detalle>();
		if(venta!=null)
			detalles = venta.getDetallesTransaccion();
		else if(apartado!=null)
			detalles = apartado.getDetallesServicio();
		
		//Si se aplico recursividad debo recalcular las posiciones de los diferentes
		//tipos de detalles
		Vector<String> condicionesEmpaque = new Vector<String>();
		condicionesEmpaque.addElement(Sesion.condicionEmpaque);
		Vector<Integer> nuevosEmpaques = new Vector<Integer>();
		Vector<Integer> nuevosNoEmpaques = new Vector<Integer>();
		Vector<Integer> nuevosCombos = new Vector<Integer>();
		for(int i=0;i<detalles.size();i++){
			Integer posicionNueva = new Integer(i);
			Detalle detalle = (Detalle)detalles.elementAt(i);
			if(detalle!=null){
				if(detalle.contieneAlgunaCondicion(Sesion.condicionesCombo)){
					nuevosCombos.addElement(posicionNueva);
				} else if(detalle.contieneAlgunaCondicion(condicionesEmpaque)){
					nuevosEmpaques.addElement(posicionNueva);
				} else{
					nuevosNoEmpaques.addElement(posicionNueva);
				}
			}
		}
		Vector<Vector<Integer>> resultado =  new Vector<Vector<Integer>>();
		resultado.addElement(nuevosEmpaques);
		resultado.addElement(nuevosNoEmpaques);
		resultado.addElement(nuevosCombos);
		return resultado;
	}
	
	/**
	 * Hace un duplicado del vector de detalles de venta o servicio
	 * @param detalles
	 * @return Vector clonado
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public static Vector<? extends Detalle> clonarDetalles(Vector<? extends Detalle> detalles) {
		Vector<Detalle> clon = new Vector<Detalle>();
		for(int i=0;i<detalles.size();i++){
			clon.addElement((Detalle)(detalles.elementAt(i)).clone());
		}
		return clon;
	}
	
	/**
	 * Determina cual combo conviene mas entre el aplicado a "detalle" y la promocion en "descuentoActual"
	 * y aplica la más conveniente
	 * @param detalles
	 * @param promocionCombo
	 * @param detalleActual
	 * @return Vector de posiciones de detalles de transaccion o servicio con el combo aplicado
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Integer> aplicarComboMasConveniente(Vector<? extends Detalle> detalles, 
			Vector<Object> descuentoActual, Detalle detalleActual){
		Venta venta = CR.meVenta.getVenta();
		Apartado apartado =  CR.meServ.getApartado();
		
		Vector<Integer> resultado = new Vector<Integer>();
		Vector<? extends Detalle> duplicado = UtilesActualizadorPrecios.clonarDetalles(detalles);
		double montoActual = 0.0;
		
		if(venta!=null){
			montoActual = venta.getMontoTransaccion();
		} else if(apartado!=null){
			montoActual = apartado.getMontoServ();
		}
		
		//Deshago combo:
		UtilesActualizadorPrecios.deshacerCombo(
				new Vector<Integer>(), 
				new Vector<Detalle>(), 
				0, 
				detalleActual, 
				(PromocionExt)descuentoActual.elementAt(4)
		);
		
		//Aplico combo nuevo
		Vector<Integer> detallesEnCombo = new Vector<Integer>();
		while(Detector.verificaCantidadesMinimasCombo((PromocionExt)descuentoActual.elementAt(4),new Vector<String>(), false)){
			Vector<Integer> resultadoCombo = UtilesActualizadorPrecios.aplicarCondicionDescuentoEnCombo(descuentoActual, detalleActual.getProducto(), true);
			detallesEnCombo = resultadoCombo;
			resultado.addAll(detallesEnCombo);
			if(detallesEnCombo.size()==0){
				break;
			} 
		}
		
		double montoNuevo = 0.0;
		
		if(venta!=null){
			montoNuevo = venta.getMontoTransaccion();
		} else if(apartado!=null){
			montoNuevo = apartado.getMontoServ();
		}
		
		if(montoActual<=montoNuevo){
			//La nueva promocion no conviene mas
			if(apartado!=null)
				apartado.setDetallesServicio(duplicado);
			else if (venta!=null)
				venta.setDetallesTransaccion(duplicado);
				
			resultado = new Vector<Integer>();
		} 		
		
		return resultado;
	}
	
	/**
	 * Aplica la condicion de descuento en combo para el producto indicado
	 * NOTA: solo se va a formar UN combo por cada corrida del algoritmo
	 * @param detallesN vector de posiciones de detalles normales
	 * @param detallesC Vector de posiciones de detalles que fueron cambiados a condicion de venta combo
	 * @param dctoActual Descuento de combo a aplicar
	 * @param cantidad Cantidad de productos en detallesN
	 * @param prod Producto que se esta agregando
	 * @return Vector	[0] Nuevos combos formados
	 * 					[1] Nueva cantidad
	 * 
	 * NOTA: Sirve tanto para servicios como para venta
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Integer> aplicarCondicionComboCantidades(Vector<Object> dctoActual, 
			Producto prod, boolean isRecalculo, Vector<Integer> nuevosNoEmpaques,
			Vector<Integer> nuevosEmpaques){
		
		Venta venta = CR.meVenta.getVenta();
		Apartado apartado = CR.meServ.getApartado();
		Vector detalles = new Vector<Detalle>();
		
		if(venta!=null)
			detalles = CR.meVenta.getVenta().getDetallesTransaccion();
		else if(apartado!=null)
			detalles = apartado.getDetallesServicio();
		
		//dctoActual contiene: PRECIO FINAL, CONDICION VENTA, CODIGO PROMOCION, CondicionVenta, PromocionExt
		Vector<Integer> detallesCombos = new Vector<Integer>();
		CondicionVenta cv = (CondicionVenta)dctoActual.elementAt(3);
		PromocionExt promocion = (PromocionExt)dctoActual.elementAt(4);
		
		//Desarmar un combo que involucre al producto que estoy agregando, y que tenga menor prioridad
		//o varios hasta que se pueda armar el combo de promocion
		Vector<String> condicionesNoPermitidas = new Vector<String>();
		condicionesNoPermitidas.addAll(Sesion.condicionesCombo);
		condicionesNoPermitidas.addAll(Sesion.condicionesEspeciales);
		Vector<? extends Detalle> detallesAActualizar = resolverPrioridadesCombos(isRecalculo, detalles, detallesCombos, promocion, condicionesNoPermitidas);
		
		if(Detector.verificaCantidadesMinimasCombo(promocion, condicionesNoPermitidas, false)){
			UtilesActualizadorPrecios.ordernarPorPrecio(detalles, nuevosNoEmpaques, nuevosEmpaques);
			for(int i=0;i<promocion.getProductosCombo().size();i++){
				ProductoCombo productoInvolucrado = (ProductoCombo)((ProductoCombo)promocion.getProductosCombo().elementAt(i)).clone();
				for(int j=0;j<detalles.size();j++){
					Detalle dt = (Detalle)detalles.elementAt(j);
					float cantidadAAsignar;
					
					//Si el producto del detalle esta promocionado
					if(UtilesActualizadorPrecios.productoPerteneceACombo(productoInvolucrado, dt.getProducto()) && 
							!dt.isCondicionEspecial() && 
							!dt.contieneAlgunaCondicion(Sesion.condicionesCombo)){
						
						if(dt.getCantidad() > productoInvolucrado.getCantidadMinima()){
							cantidadAAsignar = productoInvolucrado.getCantidadMinima();
							Detalle detalleNuevo = (Detalle)dt.clone();
							detalleNuevo.setCantidad(cantidadAAsignar);

							detalleNuevo.addCondicion(cv);
							detalleNuevo.setCondicionVenta(detalleNuevo.construirCondicionVentaString());
							
							Detalle detalleNuevoDescontado = null;
							if(productoInvolucrado.getCantidadARegalar()!=0 && detalleNuevo.getCantidad()>productoInvolucrado.getCantidadARegalar()){
								detalleNuevoDescontado = (Detalle)detalleNuevo.clone();
								detalleNuevo.setCantidad(detalleNuevo.getCantidad()-productoInvolucrado.getCantidadARegalar());
								detalleNuevoDescontado.setCantidad(productoInvolucrado.getCantidadARegalar());
								detalleNuevoDescontado.setVariacion(0);
								detalleNuevoDescontado.setCantidadCambiada(false);
								detalleNuevoDescontado.addCondicion(new CondicionVenta(Sesion.condicionDescontadoPorCombo,0,0,"Recibe el descuento de un combo",1));
								detalleNuevoDescontado.setCondicionVenta(detalleNuevoDescontado.construirCondicionVentaString());
								productoInvolucrado.setCantidadARegalar(0);
							} else if(detalleNuevo.getCantidad()<=productoInvolucrado.getCantidadARegalar()){
								detalleNuevo.addCondicion(new CondicionVenta(Sesion.condicionDescontadoPorCombo,0,0,"Recibe el descuento de un combo",1));
								detalleNuevo.setCondicionVenta(detalleNuevo.construirCondicionVentaString());
								productoInvolucrado.setCantidadARegalar((int)(productoInvolucrado.getCantidadARegalar()-detalleNuevo.getCantidad()));
							}
							
							detalleNuevo.setVariacion(0);
							detalleNuevo.setCantidadCambiada(false);
							detalles.addElement(detalleNuevo);
							detallesCombos.addElement(new Integer(detalles.size()-1));
							if(detalleNuevoDescontado!=null){
								detalles.addElement(detalleNuevoDescontado);
								detallesCombos.addElement(new Integer(detalles.size()-1));
							}
							
							dt.setCantidad(dt.getCantidad()-cantidadAAsignar);
							dt.setCantidadCambiada(false);
							dt.setVariacion(0);						
						} else {
							Detalle detalleNuevo = null;
							cantidadAAsignar = dt.getCantidad();
							dt.addCondicion(cv);
							dt.setCondicionVenta(dt.construirCondicionVentaString());
							if(venta!=null){
								dt.setCantidad(cantidadAAsignar);
								dt.setCantidadCambiada(false);
								dt.setVariacion(0);
								detalleNuevo = (DetalleTransaccion)dt.clone();
								//Se castea detalleNuevo a ´DetallTransaccion´  jperez
								venta.getDetallesTransaccion().addElement((DetalleTransaccion)detalleNuevo);
								detallesCombos.addElement(new Integer(venta.getDetallesTransaccion().size()-1));
								dt.setCantidad(0);
								dt.setCantidadCambiada(false);
								dt.setVariacion(0);
								
							} else if(apartado!=null){
								dt.setCantidad(cantidadAAsignar);
								dt.setCantidadCambiada(false);
								dt.setVariacion(0);
								detalleNuevo = (DetalleServicio)dt.clone();
								//Cambio en la migración a Java6 por jperez, se castea detalleNuevo con el tipo 'DetalleServicio'
								apartado.getDetallesServicio().addElement((DetalleServicio)detalleNuevo);
								detallesCombos.addElement(new Integer(apartado.getDetallesServicio().size()-1));
								dt.setCantidad(0);
								dt.setCantidadCambiada(false);
								dt.setVariacion(0);
								
							}	
							Detalle detalleNuevoDescontado = null;
							if(productoInvolucrado.getCantidadARegalar()!=0 && detalleNuevo.getCantidad()>productoInvolucrado.getCantidadARegalar()){
								detalleNuevoDescontado = (Detalle)detalleNuevo.clone();
								
								detalleNuevo.setCantidad(detalleNuevo.getCantidad()-productoInvolucrado.getCantidadARegalar());
								detalleNuevo.setVariacion(0);
								detalleNuevo.setCantidadCambiada(false);
								
								detalleNuevoDescontado.setCantidad(productoInvolucrado.getCantidadARegalar());
								detalleNuevoDescontado.setVariacion(0);
								detalleNuevoDescontado.setCantidadCambiada(false);
								detalleNuevoDescontado.addCondicion(new CondicionVenta(Sesion.condicionDescontadoPorCombo,0,0,"Recibe el descuento de un combo",1));
								detalleNuevoDescontado.setCondicionVenta(detalleNuevoDescontado.construirCondicionVentaString());
								productoInvolucrado.setCantidadARegalar(0);
							} else if(detalleNuevo.getCantidad()<=productoInvolucrado.getCantidadARegalar()){
								detalleNuevo.addCondicion(new CondicionVenta(Sesion.condicionDescontadoPorCombo,0,0,"Recibe el descuento de un combo",1));
								detalleNuevo.setCondicionVenta(detalleNuevo.construirCondicionVentaString());
								productoInvolucrado.setCantidadARegalar((int)(productoInvolucrado.getCantidadARegalar()-detalleNuevo.getCantidad()));
							}
							if(detalleNuevoDescontado!=null){
								detalles.addElement(detalleNuevoDescontado);
								detallesCombos.addElement(new Integer(venta.getDetallesTransaccion().size()-1));
							}
						}
						productoInvolucrado.setCantidadMinima((int)(productoInvolucrado.getCantidadMinima()-cantidadAAsignar));
						if(productoInvolucrado.getCantidadMinima()==0){
							break;
						}
					}
				}			
			}
		}
		
		/*for(int i=0;i<detallesAActualizar.size();i++){
			if(!((Detalle)detallesAActualizar.elementAt(i)).contieneAlgunaCondicion(Sesion.condicionesCombo) || !Sesion.promocionesAcumulativas){
				try{
					if(venta!=null){
						BECOActualizadorPrecios.posicionesCambiadas = true;
						(new BECOActualizadorPrecios()).actualizarPreciosExt(((Detalle)detallesAActualizar.elementAt(i)).getProducto(), CR.meVenta.getVenta());
					} else if(apartado!=null){
						BECOActualizadorPreciosSAC.posicionesCambiadas = true;
						(new BECOActualizadorPreciosSAC()).actualizarPreciosExt(((Detalle)detallesAActualizar.elementAt(i)).getProducto(), (Servicio)apartado, true);
					}
				} catch(Exception e){
					e.printStackTrace();
					logger.debug("aplicarCondicionDescuentoEnCombo(Vector, Vector, Vector, float, Producto)", e);
				}
			}
		}*/
		
		Auditoria.registrarAuditoria("Aplicada promoción de combo por cantidades: "+cv.getCodPromocion(), 'T');
		
		if(venta!=null && BECOActualizadorPrecios.posicionesCambiadas && detallesAActualizar.size()!=0)
			BECOActualizadorPrecios.posicionesCambiadas=false;
		else if(apartado!=null && BECOActualizadorPreciosSAC.posicionesCambiadas && detallesAActualizar.size()!=0)
			BECOActualizadorPreciosSAC.posicionesCambiadas=false;
		
		return detallesCombos;
	}
	
	/**
	 * Indica si el producto p pertenece o no al elemento del combo indicado
	 * en productoInvolucrado 
	 * @param productoInvolucrado
	 * @paran p 
	 * @return boolean 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' e 'Iterator'
	* Fecha: agosto 2011
	*/
	public static boolean productoPerteneceACombo(ProductoCombo productoInvolucrado, Producto p){
		
		if(productoInvolucrado.getCodigosAsociados().size()!=0){
			if(productoInvolucrado.getCodigosAsociados().contains(p.getCodProducto()))
				return true;
		} else if(productoInvolucrado.getDepartamentos().size()!=0){
			Vector<String> departamentos =  productoInvolucrado.getDepartamentos();
			for(int i=0;i<departamentos.size();i++){
				if(Integer.parseInt(p.getCodDepartamento()) == Integer.parseInt(((String)productoInvolucrado.getDepartamentos().elementAt(i))))
					return true;
			}
		} else if(productoInvolucrado.getLineas().size()!=0){
			Vector<Linea> lineas =  productoInvolucrado.getLineas();
			for(int i=0;i<lineas.size();i++){
				if(Integer.parseInt(p.getLineaSeccion()) == Integer.parseInt((((Linea)productoInvolucrado.getLineas().elementAt(i)).getCodlinea())) &&
						Integer.parseInt(p.getCodDepartamento()) == Integer.parseInt(((Linea)productoInvolucrado.getLineas().elementAt(i)).getCodDepartamento()))
					return true;
			}
		} else if(productoInvolucrado.getMarcas().size()!=0){
			Vector<String> marcas =  productoInvolucrado.getMarcas();
			for(int i=0;i<marcas.size();i++){
				if(p.getMarca().equalsIgnoreCase((String)productoInvolucrado.getMarcas().elementAt(i)))
					return true;
			}
		} else if(productoInvolucrado.getReferencias().size()!=0){
			//Vector referencias =  productoInvolucrado.getReferencias();
			//for(int i=0;i<referencias.size();i++){
				//if(p.getReferenciaProveedor().equalsIgnoreCase((String)productoInvolucrado.getReferencias().elementAt(i)))
					return productoInvolucrado.contieneReferencia(new Referencia(p.getCodDepartamento(), p.getLineaSeccion(), p.getSeccion(), p.getReferenciaProveedor()));
			//}
		} else if(productoInvolucrado.getSecciones().size()!=0){
			Vector<Seccion> secciones = productoInvolucrado.getSecciones();
			for(int i=0;i<secciones.size();i++){
				Seccion seccion = (Seccion)secciones.elementAt(i);
				if(Integer.parseInt(p.getCodDepartamento()) == Integer.parseInt(seccion.getCodDepartamento()) && Integer.parseInt(p.getLineaSeccion())== Integer.parseInt(seccion.getLinea())
						&& p.getSeccion()==seccion.getSeccion()){
					return true;
				}
			}
		} else if(productoInvolucrado.getCategorias().size()!=0){
			Vector<String> categorias =  productoInvolucrado.getCategorias();
			for(int i=0;i<categorias.size();i++){
				if(p.getCategoria().equalsIgnoreCase((String)productoInvolucrado.getCategorias().elementAt(i)))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * Ordena el vector de detalles por precio en orden ascendente
	 * Implementado con algoritmo de burbuja, puede ser optimizado
	 * @param detalles
	 * @return Vector de detalles ordenado
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' e 'Iterator'
	* Fecha: agosto 2011
	*/
	public static void ordernarPorPrecio(Vector<Detalle> detalles, Vector<Integer> nuevosNoEmpaques, Vector<Integer> nuevosEmpaques){
		Detalle temp = null;
		for (int i=1; i<detalles.size(); i++){
			for (int j=0 ; j<detalles.size() - i; j++){
				Detalle detallej = (Detalle)detalles.elementAt(j);
				Detalle detalleJMasUno = (Detalle)detalles.elementAt(j+1);
				if(detallej.getPrecioFinal()>detalleJMasUno.getPrecioFinal()){
					for(int k=0;k<nuevosNoEmpaques.size();k++){
						Integer noEmpaques = nuevosNoEmpaques.elementAt(k);
						if(noEmpaques.intValue()==j)
							nuevosNoEmpaques.set(k, new Integer(j+1));
						else if (noEmpaques.intValue()==j+1)
							nuevosNoEmpaques.set(k, new Integer(j));
					}
					for(int k=0;k<nuevosEmpaques.size();k++){
						Integer empaques = (Integer)nuevosEmpaques.elementAt(k);
						if(empaques.intValue()==j)
							nuevosEmpaques.set(k, new Integer(j+1));
						else if (empaques.intValue()==j+1)
							nuevosEmpaques.set(k, new Integer(j));
					}
					temp = detallej;
					detalles.set(j, detalleJMasUno);
					detalles.set(j+1, temp);
				}
			}
		}
	}
	
	/**
	 * Ordena el vector de detalles por cantidad en orden descendente
	 * Implementado con algoritmo de burbuja, puede ser optimizado
	 * @param detalles
	 * @return Vector de detalles ordenado
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' e 'Iterator'
	* Fecha: agosto 2011
	*/
	public static void ordernarPorCantidad(Vector<Detalle> detalles){
		Detalle temp = null;
		for (int i=1; i<detalles.size(); i++){
			for (int j=0 ; j<detalles.size() - i; j++){
				Detalle detallej = (Detalle)detalles.elementAt(j);
				Detalle detalleJMasUno = (Detalle)detalles.elementAt(j+1);
				if(detallej.getCantidad()<=detalleJMasUno.getCantidad()){
					temp = detallej;
					detalles.set(j, detalleJMasUno);
					detalles.set(j+1, temp);
				}
			}
		}
	}
	
	/**
	 * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#getDonacionesRegistradasTipo1()
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' e 'Iterator'
	* Fecha: agosto 2011
	*/
	public static Vector<DonacionRegistrada> getDonacionesRegistradasTipo1(Vector<DonacionRegistrada> donaciones){
		/*Vector donaciones = null;
		if(CR.meVenta.getVenta()!=null)
			donaciones = Venta.donacionesRegistradas;
		else if (CR.meServ.getApartado()!=null)
			donaciones = Apartado.donacionesRegistradas;*/
		Iterator<DonacionRegistrada> i = donaciones.iterator();
		Vector<DonacionRegistrada> tipo1 = new Vector<DonacionRegistrada>();
		while (i.hasNext()){
			DonacionRegistrada dr = (DonacionRegistrada)i.next();
			Donacion d = Donacion.getDonacionPorCodigo(dr.getCodigoDonacion());
			if(d.getTipoDonacion()==1){
				tipo1.add(dr);				
			}
		}
		return tipo1;
	}
	
	/**
	 * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#getDonacionesRegistradasTipo1()
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' e 'Iterator'
	* Fecha: agosto 2011
	*/
	public static Vector<DonacionRegistrada> getDonacionesRegistradasTipo2(Vector<DonacionRegistrada> donaciones){
		/*Vector donaciones = null;
		if(CR.meVenta.getVenta()!=null)
			donaciones = Venta.donacionesRegistradas;
		else if (CR.meServ.getApartado()!=null)
			donaciones = Apartado.donacionesRegistradas;*/
		Iterator<DonacionRegistrada> i = donaciones.iterator();
		Vector<DonacionRegistrada> tipo2 = new Vector<DonacionRegistrada>();
		while (i.hasNext()){
			DonacionRegistrada dr = (DonacionRegistrada)i.next();
			Donacion d = Donacion.getDonacionPorCodigo(dr.getCodigoDonacion());
			if(d.getTipoDonacion()==2){
				tipo2.add(dr);				
			}
		}
		return tipo2;
	}
	
	/**
	 * Ejecuta producto complementario
	 * @param p
	 */
	/*public static void ejecutarProductoComplementario(int p, Venta venta2){
		Venta venta =  venta2;
		Apartado apartado = CR.meServ.getApartado();
		HashMap complementarios = null;
		boolean isAplicaDescuentoEmpleado = false;
		Vector promocionesRegistradas = new Vector();
		int promo = 0; 
		if(venta!=null){
			complementarios = CR.meVenta.getProductosComplementario();
			isAplicaDescuentoEmpleado = venta.isAplicaDesctoEmpleado();
			promocionesRegistradas = Venta.promocionesRegistradas;
		}else if(apartado!=null){
			complementarios = CR.meServ.getProductosComplementario();
			promocionesRegistradas = Apartado.promocionesRegistradas;
		}
		if (p==1){
	    	try{
		    	
		    	UtilesActualizadorPrecios.textoVentana = "";
		    	Vector v = new Vector();
		    	int cantidad;
		    	String acumula="Y"; 
		    	Iterator i = complementarios.keySet().iterator();
		    	while(i.hasNext()){
		    		PromocionExt promocion = (PromocionExt)i.next();
		    		Vector complementariosEstaPromo = (Vector)complementarios.get(promocion);
		    		Vector detallesTransaccionGanadores = Detector.getProd(complementariosEstaPromo, venta);
		    		if(!(detallesTransaccionGanadores.size()==0 || isAplicaDescuentoEmpleado)){
		    			Iterator k = detallesTransaccionGanadores.iterator();
		    			while (k.hasNext()){
		    				Detalle dt = (Detalle)k.next();
							if (promocion.getCodProducto().equals(dt.getProducto().getCodProducto())){
								acumula=promocion.getAcumulaPremio();
								if(acumula.equals("Y"))
									cantidad = (int)dt.getCantidad();
								else cantidad = 1;
								//int n = (int)dt.getCantidad();
		    					//while (n>0){
			    					promocionesRegistradas.add(new PromocionRegistrada(promocion.getNombrePromocion(),
				    					promocion.getCodPromocion(),dt.getProducto().getCodProducto(),cantidad));
			    					//n--;
		    					//}
		    			    	Vector w = new Vector();
		    					w.add((new Integer(cantidad)));
		    					w.add(promocion.getNombrePromocion());
		    					w.add(new Integer(promocion.getCodPromocion()));
		    					if(Detector.contienePromocion(v,promocion.getCodPromocion())){ 	
									Iterator l = v.iterator();
									while (l.hasNext()){
										Vector datos = (Vector)l.next();
										if (((Integer)datos.lastElement()).intValue() == promocion.getCodPromocion()){ 
											datos.setElementAt(new Integer(((Integer)datos.firstElement()).intValue()+cantidad), 0);
											break;
										}
									}
								}else{
									v.add(w);
									//Vector y =(Vector)v.lastElement();
									//int x = ((Integer)y.lastElement()).intValue();
						    	}
		    				}
	    				}
		    		}
		    	}
			
		    	Iterator j = v.iterator();
		    	while (j.hasNext()){
		    		Vector pr = (Vector)j.next();
		    		int cant = ((Integer)pr.firstElement()).intValue();
		    		String nomb = (String)pr.elementAt(1);
		    		if(acumula.equals("Y"))
		    			UtilesActualizadorPrecios.textoVentana += cant+" "+nomb+"\n";
		    		else UtilesActualizadorPrecios.textoVentana += "1 "+nomb+"\n";
		    	}
		    	if (!UtilesActualizadorPrecios.textoVentana.equals("")){
		    		UtilesActualizadorPrecios.imprimirProductoComplementario=true;
			    	MensajesVentanas.aviso("Debe hacer entrega de:\n"+UtilesActualizadorPrecios.textoVentana);
					CR.me.mostrarAviso("Debe hacer entrega de: "+UtilesActualizadorPrecios.textoVentana, true);
		    	}
	    	}catch(Exception e){
	    		e.printStackTrace();
	    		logger.debug("ejecutarProductoComplementario(int p)",e);
	    	}
    	}else if (p==2){
    			Iterator i = venta.promocionesRegistradas.iterator();
    			while (i.hasNext()){
    				PromocionRegistrada pr = (PromocionRegistrada)i.next();
    				if (pr.getCodProducto()!=null && PromocionExtBD.cargarCondicionesPromocion(pr.getCodigo()).size()!=0)
    					promo=pr.getCodigo();
    			}
    			Calendar dFecha = Calendar.getInstance();
    			int dia = dFecha.get(Calendar.DAY_OF_MONTH);
    			int mes = dFecha.get(Calendar.MONTH);	
    			int anio = dFecha.get(Calendar.YEAR);
    			String fecha = String.valueOf(dia)+ "/"+String.valueOf(mes+1)+"/"+String.valueOf(anio);//OJO CON ESTA INSTRUICCION <------------------------------------------------
    			Vector condiciones = null;
    			condiciones = PromocionExtBD.cargarCondicionesPromocion(promo);
    			//MensajesVentanas.aviso(fecha + " " +  String.valueOf(fecha.length()));

    			if(UtilesActualizadorPrecios.imprimirProductoComplementario) {
    				//Esperamos que finalice la impresión de la anulación antes de continuar con la venta
    				while (CR.meVenta.getTransaccionPorImprimir()!=null || 
    						CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
    						MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()) {
    					MensajesVentanas.aviso("Impresora ocupada.\nPresione Enter para continuar imprimiendo");
    				}
    				if (condiciones==null)
    					((new ManejadorReportesPromocionesFactory()).getInstance()).imprimirProdComplementario(
							UtilesActualizadorPrecios.textoVentana,fecha, new Vector());
    				else
    					((new ManejadorReportesPromocionesFactory()).getInstance()).imprimirProdComplementario(
							UtilesActualizadorPrecios.textoVentana,fecha, condiciones);
    			}
    			UtilesActualizadorPrecios.imprimirProductoComplementario = false;
	    		UtilesActualizadorPrecios.textoVentana = "";
    	}
	}*/
	
	/**
	 * Deshace la promocion de descuento en productos
	 * @param detallesPatrocinantes
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static void deshacerDescuentoEnProductos(Vector<Detalle> detallesPatrocinantes) {
		for(int j=0;j<detallesPatrocinantes.size();j++){
			DetalleServicio detalle = (DetalleServicio)detallesPatrocinantes.elementAt(j);
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
	}
	
	/**
	 * Crea el vector con los datos del descuento corporativo
	 * @param prod
	 * @return
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Object> crearDescuentoCorporativo(Producto prod) {
		Venta venta =  CR.meVenta.getVenta();
		Apartado apartado =  CR.meServ.getApartado();
		double descuentoCorporativoAplicado = 0;
		int codDescuentoCorporativoAplicado = 0;
		if(venta!=null){
			descuentoCorporativoAplicado = CR.meVenta.getVenta().getDescuentoCorporativoAplicado();	
			codDescuentoCorporativoAplicado = CR.meVenta.getVenta().getCodDescuentoCorporativo();
		}else if(apartado!=null){
			descuentoCorporativoAplicado = CR.meServ.getApartado().getDescuentoCorporativoAplicado();
			codDescuentoCorporativoAplicado = CR.meServ.getApartado().getCodDescuentoCorporativo();
		}
		double precioCorporativo;
		
		precioCorporativo = prod.getPrecioRegular() - MathUtil.roundDouble((prod.getPrecioRegular()*descuentoCorporativoAplicado/100));
		Vector<Object> corporativo = new Vector<Object>(2);
		corporativo.addElement(new Double(precioCorporativo));			//PRECIO FINAL
		corporativo.addElement(Sesion.condicionDescuentoCorporativo);	//CONDICION DE VENTA (STRING)
		corporativo.addElement(new Integer(0));							//CODIGO DE LA PROMOCION 0 PORQUE NO APLICA
		CondicionVenta cv = new CondicionVenta(Sesion.condicionDescuentoCorporativo,codDescuentoCorporativoAplicado, descuentoCorporativoAplicado, "Promoción Corporativa",1);
		corporativo.addElement(cv);										//CONDICION DE VENTA (OBJETO)
		
		return corporativo;
	}
	
	/**
	 * Aplica a la transacción o servicio activo el cupón de descuento entregado por el cliente
	 * @param p PromocionExt
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static void aplicarPromocionCuponDescuento(PromocionExt p) throws Exception{
		Venta venta = CR.meVenta.getVenta();
		Apartado apartado =  CR.meServ.getApartado();
		double montoOperacion = 0;
		if(venta!=null)
			montoOperacion = CR.meVenta.getVenta().consultarMontoTrans();
		else if (apartado!=null) {
			montoOperacion = CR.meServ.getApartado().consultarMontoServ();
		}
		try{
			//Obtener los cupones validos para esta promocion
			/*Vector<Vector<Object>> cuponesValidos = p.getCuponesValidos();
			//si no es vacio mostrar esta ventana
			boolean cuponValido = true;
			String codCupon = "";
			if(cuponesValidos.size()!=0){
				try{
					CodigoCupon c = new CodigoCupon(p,cuponesValidos);
					MensajesVentanas.centrarVentanaDialogo(c);
					cuponValido = c.isCuponValido();
					codCupon = c.getCodigo();
				} catch(Exception e){
					//nada, asumo que el cupon es valido
				}
			}*/
		//	if(cuponValido){
				//MaquinaDeEstadoVenta.codCupon = codCupon;
			//	if(cuponesValidos.size()!=0)
				//	MaquinaDeEstadoVenta.invalidarCupon = ((String)(cuponesValidos.elementAt(0)).elementAt(5)).equalsIgnoreCase("S");
				MaquinaDeEstadoVenta.codPromocionCupon = p.getCodPromocion();
				double montoPagado = 0;
				//PromocionExt p = (PromocionExt)this.cuponesActivos.get(jComboBox.getSelectedIndex());
				Vector<Producto> productosAfectados = PromocionExt.getProductosPorCupon(p.getCodPromocion());
				boolean ejecutar = true;
				
				//Calculo del monto pagado de acuerdo a la promocion
				//sobre el total
				if(p.getTipoPromocion()==Sesion.TIPO_PROMOCION_CUPON_DESCUENTO_BS){
					montoPagado = p.getBsDescuentoOBsBonoRegalo();
				} else if(p.getTipoPromocion()==Sesion.TIPO_PROMOCION_CUPON_DESCUENTO_PORCENTAJE){
					montoPagado = MathUtil.roundDouble(montoOperacion*p.getPorcentajeDescuento()/100);
				} 
				//por producto
				else if(p.getTipoPromocion()==Sesion.TIPO_PROMOCION_CALCOMANIA_BS){
					if(Detector.getProd(productosAfectados, CR.meVenta.getVenta()).size()!=0){
						montoPagado = p.getBsDescuentoOBsBonoRegalo();
					} else {
						ejecutar = false;
						MensajesVentanas.aviso("No existe el producto promocionado en la venta");
					}
				} else if(p.getTipoPromocion()==Sesion.TIPO_PROMOCION_CALCOMANIA_PORCENTAJE){
					if(Detector.getProd(productosAfectados, CR.meVenta.getVenta()).size()!=0){
						Vector<Detalle> detallesAfectados = Detector.getProd(productosAfectados, CR.meVenta.getVenta());
						Producto masCostoso = Detector.getProductoMasCostoso(detallesAfectados);
						montoPagado = MathUtil.roundDouble(masCostoso.getPrecioRegular()*p.getPorcentajeDescuento()/100);
					} else {
						ejecutar = false;
						MensajesVentanas.aviso("No existe el producto promocionado en la venta");
					}
				} 
				if(ejecutar){
					FormaDePago f = ManejoPagosFactory.getInstance().obtenerFormaDePagoCuponDescuento();
					Pago pago = new Pago(f,montoPagado,null,null,null,0,0,null);
					if(CR.meVenta.getVenta()!=null){
						if(!CR.meVenta.getVenta().contieneFormaPago(f)){
							CR.meVenta.getVenta().getPagos().add(pago);
						} else {
							MensajesVentanas.aviso("Solamente se puede aceptar un cupón de descuento desprendible");
						}
					} else if(CR.meServ.getApartado()!=null){
						boolean apartadoContienePago = false;
						for(int i=0;i<CR.meServ.getApartado().getPagosEspeciales().size();i++){
							Pago pagoApartado = (Pago)CR.meServ.getApartado().getPagosEspeciales().elementAt(i);
							if(pago.getFormaPago().getCodigo().equalsIgnoreCase(pagoApartado.getFormaPago().getCodigo()))
								apartadoContienePago = true;
						}
						if(!apartadoContienePago){
							CR.meServ.getApartado().getPagosEspeciales().addElement(pago);
							MensajesVentanas.aviso("Se ha agregado el cupón de descuento\nSolamente será válido para el abono final");
							CR.me.mostrarAviso("Agregado cupón de descuento. Bs. "+pago.getMonto(), false);
						}
						else 
							MensajesVentanas.aviso("Solamente se puede aceptar un cupón de descuento desprendible");
					}
				}
		//	}
			Auditoria.registrarAuditoria("Agregado cupón de descuento "+p.getCodPromocion(), 'T');
		} catch(Exception e) {
			logger.debug("aceptarCupon()",e);
			throw e;
		}
	}
	
	/**
	 * Chequea si el cupon correspondiente a codigo esta disponible para la promocion p
	 * @param p
	 * @param cuponesValidos: Vector<Vector>
	 * 			posicion 0: numtienda
	 * 			posicion 1:	fecha
	 * 			posicion 2: numcaja
	 * 			posicion 3: numtransaccion
	 * 			posicion 4: codcupon
	 * @param codigo
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public static void validarCupon(PromocionExt p, Vector<Vector<Object>> cuponesValidos, String codigo) throws ExcepcionCr {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		boolean valido=false;
		for(int i=0;i<cuponesValidos.size();i++){
			Vector<Object> cupon = cuponesValidos.elementAt(i);
			String codigoCupon = (String)cupon.elementAt(4);
			if(codigo.equalsIgnoreCase(codigoCupon)){
				if(((Integer)cupon.elementAt(3)).intValue()!=0) {
					String continuacionMensaje= "";
					if(((Integer)cupon.elementAt(0)).intValue()!=0){
						continuacionMensaje= " en la transacción:\n"+
						"Número: "+((Integer)cupon.elementAt(3)).intValue()+". Caja: "+((Integer)cupon.elementAt(2)).intValue()+". Fecha: "+df.format(cupon.elementAt(1))+". Tienda: "+((Integer)cupon.elementAt(0)).intValue();
					}
					throw new ExcepcionCr("El cupón indicado ya fue utilizado anteriormente"+continuacionMensaje);
				}  else
					valido=true;
			}
			if(i==cuponesValidos.size()-1 && !valido){
				throw new ExcepcionCr("El código del cupón es invalido");
			}
		}
	}
}
