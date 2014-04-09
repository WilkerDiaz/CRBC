/*
 * ===========================================================================
 * Material Propiedad CentroBeco C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.extensiones.impl
 * Programa		: DefaultActualizadorPrecios.java
 * Creado por	: Jesus Graterol
 * Creado en 	: 30-may-2008 10:39:00
 * (C) Copyright 2005 CentroBeco C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * ===========================================================================
 */
package com.becoblohm.cr.extensiones.impl;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import java.util.Vector;

import org.apache.log4j.Logger;


import com.beco.cr.actualizadorPrecios.promociones.DonacionRegistrada;
import com.beco.cr.actualizadorPrecios.promociones.ProductoCombo;
import com.beco.cr.actualizadorPrecios.promociones.PromocionExt;
import com.beco.cr.actualizadorPrecios.promociones.PromocionRegistrada;
import com.beco.cr.actualizadorPrecios.promociones.RegaloRegistrado;
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
import com.becoblohm.cr.manejarventa.Anulacion;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.manejarventa.CondicionVenta;
import com.becoblohm.cr.manejarventa.DetalleTransaccion;
import com.becoblohm.cr.manejarventa.Producto;
import com.becoblohm.cr.manejarventa.Promocion;
import com.becoblohm.cr.manejarventa.Venta;
import com.becoblohm.cr.utiles.MathUtil;

/**
 * <pre>
 * Proyecto: CR 
 * Clase: DefaultActualizadorPrecios
 * </pre>
 * <p>
 * <a href="DefaultActualizadorPrecios.java.html"><i>View Source</i></a>
 * </p>
 */
public class DefaultActualizadorPrecios implements ActualizadorPrecios {
	
	private static final Logger logger = Logger.getLogger(DefaultActualizadorPrecios.class);
	
	/**Boton de menu utilitarios para donaciones**/
	protected JHighlightButton jButton = new JHighlightButton();
		
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
		Vector<Vector<Integer>> nuevosDetalles;
		
		// Se acumulan los detalles que tiene condición 'N' y 'E' en sus respectivas variables
		for (int i=0; i<venta.getDetallesTransaccion().size();i++) {
			if (((((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)).getProducto()).getCodProducto()).equals(p.getCodProducto())) {
				if ((((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCondicionVenta().equalsIgnoreCase(Sesion.condicionEmpaque)) || 
					(((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCondicionVenta().equalsIgnoreCase(Sesion.condicionEmpaquePromocion)) || 
					(((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCondicionVenta().equalsIgnoreCase(Sesion.condicionEmpaquePromocionEmpleado)) || 
					(((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i)).getCondicionVenta().equalsIgnoreCase(Sesion.condicionDesctoEmpEmpaque))) {
					cantidadProdsConDescto = (((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)).getCantidad() - (((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)).getCantidad() % p.getCantidadVentaEmpaque()));
					if (cantidadProdsConDescto == ((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)).getCantidad()){
						detallesE.addElement(new Integer(i));
					} else {
						((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)).setCondicionVenta(Sesion.condicionNormal);
						AcumN += ((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)).getCantidad();
						detallesN.addElement(new Integer(i));
					}
				} else if (!((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)).isCondicionEspecial()) {/*!((((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)).getCondicionVenta()).equals(Sesion.condicionDesctoPorDefecto)) &&  !((((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)).getCondicionVenta()).equals(Sesion.condicionDesctoEmpDesctoDefect))*/
					//if (((((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)).getCondicionVenta()).equals(Sesion.condicionNormal)) || ((((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)).getCondicionVenta()).equals(Sesion.condicionPromocion)) || ((((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)).getCondicionVenta()).equals(Sesion.condicionDesctoEmpleado)) || ((((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)).getCondicionVenta()).equals(Sesion.condicionDesctoEmpProm))) {
					AcumN += ((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)).getCantidad();
					detallesN.addElement(new Integer(i));
				} else { 
					if (Sesion.desctosAcumulativos && p.isIndicaDesctoEmpleado() && Sesion.isIndicaDesctoEmpleado() && venta.isAplicaDesctoEmpleado()) {
						if (!((((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)).getCondicionVenta()).equals(Sesion.condicionDesctoEmpDesctoDefect)) && !((((DetalleTransaccion) venta.getDetallesTransaccion().elementAt(i)).getCondicionVenta()).equals(Sesion.condicionDesctoEmpPrecioGarantizado))) {
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
		
		// Se evalúan y aplican las condiciones de venta a los detalles afectados.
		if (detallesN.size() > 0){
			nuevosDetalles = aplicarCondicionDeVenta(p, detallesN, AcumN, venta);
			Vector<Integer> nuevoDetallesN = ( nuevosDetalles.elementAt(0));
			Vector<Integer> nuevosEmpaques = ( nuevosDetalles.elementAt(1));
			
			// Insertamos los nuevos empaques en el vector de detallesE original
			for (int i=0; i < nuevosEmpaques.size(); i++) {
				detallesE.addElement((Integer)nuevosEmpaques.elementAt(i));
			}
			
			// Se acumulan los detalles que se encuentran en condición de empaque
			venta.acumularDetalles(detallesE);
			
			// Se acumulan los detalles que se encuentran en la condición distinta a la de empaque y se colocan al final del vector original
			venta.acumularDetalles(nuevoDetallesN);
			
			// Se limpia el detalle quitando los elementos del vector de detallesN original
			for (int i = 0; i<detallesN.size(); i++) {
				venta.getDetallesTransaccion().set(((Integer)detallesN.elementAt(i)).intValue(),null);
			}
			
			//Codigo agregado aqui para el caso de anular producto
			for(int i=0;i<CR.meVenta.getVenta().getDetallesTransaccion().size();i++){
				DetalleTransaccion dt = ((DetalleTransaccion)CR.meVenta.getVenta().getDetallesTransaccion().elementAt(i));
				try{
					if (dt.getCantidad() <= 0) {
						Auditoria.registrarAuditoria("Anulado(s) " + dt.getCantidad() + " producto(s) " + dt.getProducto().getCodProducto() + " de la transaccion",'T');
						CR.meVenta.getVenta().getDetallesTransaccion().set(i, null);
					}
				} catch(NullPointerException np){
					
				}
			}
		
			// Se eliminan los elementos nulos que quedan en el vector de detalles original
			while (venta.getDetallesTransaccion().removeElement(null));
			
			// Limpiamos los vectores de posiciones
			detallesN.clear();
			nuevoDetallesN.clear();
			detallesE.clear();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarPrecios(Producto) - end");
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
		
		// Buscamos las condiciones de venta que aplican al producto
		Vector<Vector<Object>> descuentos = buscarCondicionesDeVenta(prod, venta);
		
		// Definimos las variables necesarias para la creacion de los nuevos detalles
		String vendedor; 
		String supervisor; 
		String tipCaptura; 
		double pFinal;
		float cantidadAsignar;
		String tipoEntrega;
		
		// Verificamos si aplican condiciones de venta al producto, de no ser así se colocan como normales en los detalles
		if ((descuentos.size() > 0) && (cantidad > 0)) {
			
			// Obtenemos el mejor descuento para el producto
			int posicionMejor = 0;
			Vector<Object> dctoActual;
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
				
				// Separo en empaques y no empaques y aplico empaque
				float prodsEmpaque = cantidad - (cantidad % prod.getCantidadVentaEmpaque());	
				cantidad -= prodsEmpaque;			
				
				dctoActual = descuentos.elementAt(posicionMejor);
				Vector<Object> datosNuevosEmpaques = venta.aplicarCondicionEmpaque(prod, detallesN, prodsEmpaque, posicionMejor, dctoActual);
				
				nuevosEmpaques = (Vector<Integer>)datosNuevosEmpaques.elementAt(0);
				puntoDeChequeo = ((Integer)datosNuevosEmpaques.elementAt(1)).intValue();
					
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
					vendedor = ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCodVendedor();
					supervisor = ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCodSupervisor();
					tipCaptura = ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getTipoCaptura();
					dctoActual = descuentos.elementAt(posicionMejor);
					pFinal = ((Double)dctoActual.elementAt(0)).doubleValue();
					cantidadAsignar = ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCantidad();
					DetalleTransaccion nuevoDetalleTrans;
					if (((String)(descuentos.elementAt(posicionMejor)).elementAt(1)).equals(Sesion.condicionPromocion)) {
						
						// Si se trata de una promocion
						nuevoDetalleTrans = venta.aplicarCondicionPromocion (prod, detallesN, dctoActual, puntoDeChequeo);
					} else {
						// Si no se trata de una promocion 
						nuevoDetalleTrans = venta.aplicarCondicionDescuentoEmpleado (prod, detallesN, dctoActual, puntoDeChequeo);
					}
					venta.getDetallesTransaccion().addElement(nuevoDetalleTrans);
					nuevosNoEmpaques.addElement(new Integer(venta.getDetallesTransaccion().size() - 1));
					cantidad -= cantidadAsignar;
					puntoDeChequeo++;
				}
			}
		}
		
		// Colocamos las condiciones de venta normales (Si existen)
		if (cantidad > 0) {
			while (puntoDeChequeo < detallesN.size()) {
				vendedor = ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCodVendedor();
				supervisor = ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCodSupervisor();
				tipCaptura = ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getTipoCaptura();
				pFinal = prod.getPrecioRegular();
				cantidadAsignar = ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getCantidad();
				tipoEntrega = ((DetalleTransaccion)venta.getDetallesTransaccion().elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue())).getTipoEntrega();
				pFinal = MathUtil.roundDouble(pFinal);
				DetalleTransaccion nuevoDetalleTrans = new DetalleTransaccion(
								vendedor,
								prod,
								cantidadAsignar,
								Sesion.condicionNormal,
								supervisor,
								pFinal,
								venta.calcularImpuesto(prod,pFinal),
								tipCaptura,
								-1,
								tipoEntrega);
				venta.getDetallesTransaccion().addElement(nuevoDetalleTrans);
				nuevosNoEmpaques.addElement(new Integer(venta.getDetallesTransaccion().size() - 1));
				puntoDeChequeo++;
			}
		}
		
		// Preguntamos si los descuentos son acumulativos para aplicar el dcto a COLABORADOR sobre los precios finales
		if (Sesion.desctosAcumulativos && prod.isIndicaDesctoEmpleado() && Sesion.isIndicaDesctoEmpleado()&& venta.isAplicaDesctoEmpleado()) {
			venta.aplicarCondicionDescuentoEmpleado (prod, nuevosNoEmpaques, null, 0);
			venta.aplicarCondicionDescuentoEmpleado (prod, nuevosEmpaques, null, 0); 
		}
		
		//Preguntamos si el descuento a empaque es acumulativo para aplicarlo
		if (Sesion.desctoEmpaqueAcumulativo && (prod.getCantidadVentaEmpaque() > 0 )) {
			 Vector<Object> nuevosEmp = venta.aplicarCondicionEmpaque(prod, nuevosNoEmpaques);
			 nuevosEmpaques = (Vector<Integer>)nuevosEmp.elementAt(0);
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
	 * 				   - posición 2 (si no son acumultativos) posee la información del descuento a COLABORADOR.
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
		
		// Calculamos las promociones
		Vector<Object> datosPromocion = venta.verificarPromociones(prod);
		if (datosPromocion.size() > 0) {
			// Existen promociones vigentes para el producto
			Vector<Object> promo = new Vector<Object>(2);
			promo.addElement((Double)datosPromocion.elementAt(1));
			promo.addElement(Sesion.condicionPromocion);
			promo.addElement((Integer)datosPromocion.elementAt(0));
			promo.addElement(new CondicionVenta(Sesion.condicionPromocion,0,((Double)datosPromocion.elementAt(2)).doubleValue(),"Promoción Básica",0));
			descuentos.addElement(promo);
		}
		

		
		if (!Sesion.desctoEmpaqueAcumulativo) {
			// Calculamos el precio de condicion empaque

			dctoEmpaque = prod.getPrecioRegular() * (prod.getDesctoVentaEmpaque()/100);
			if (dctoEmpaque > 0) {
				// Agregamos el empaque al vector de condiciones de venta
				Vector<Object> empaque = new Vector<Object>(2);
				empaque.addElement(new Double(prod.getPrecioRegular() - dctoEmpaque));
				empaque.addElement(Sesion.condicionEmpaque);
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
				empleado.addElement(new Double(precioEmpleado));
				empleado.addElement(Sesion.condicionDesctoEmpleado);
				descuentos.addElement(empleado);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("buscarCondicionesDeVenta(Producto) - end");
		}
		return descuentos;
	}
	
	/**
	 * No suma ninguna donacion al subtotal de la factura
	 * @param subTotal Subtotal de la transaccion
	 * @return double El monto del subtotal sin sumar donaciones
	 * **/
	public double sumarDonaciones(Venta venta, boolean solicitarDonaciones){
		return venta.consultarMontoTrans();
	}
	
	public double sumarMontoAhorrado(Venta venta){
		return 0.0;
	}
	
	/*
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
        this.jButton.setEnabled(false);
        return this.jButton;
    }
    
    //NADA, NUNCA SE EJECUTA
    public void sumarDonaciones(boolean totalizar){}
    
    public void sumarMontoAhorrado(){}

    
    /**
     * Si no esta activa la extension de promociones, devuelve el monto sin afectarse
     * @param monto total a pagar
     * @return double monto pagado despues de transaccion premiada
     * **/
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
    public double ejecutarTransaccionPremiada(double monto, Vector<Pago> pagosRealizados, Cliente cliente){
    	return 0;
    }
    public void llamadaDeRegalos(int n, Venta venta){}

	/*
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
        this.jButton.setEnabled(false);
        return this.jButton;
    }
    
    //NADA, NUNCA SE EJECUTA
    //public void agregarCupones(){}

    /*
     * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#cargarPatrocinantes()
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Treemap'
	* Fecha: agosto 2011
	*/
    public TreeMap<PromocionExt,Vector<Producto>> cargarPatrocinantes(){
    	return new TreeMap<PromocionExt,Vector<Producto>>();
    }
    
    /**
     * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#ejecutarAhorroEnCompra()
     */
    public void ejecutarAhorroEnCompra(Venta venta){
    	//NADA, CAJA SIN PROMOCIONES
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
    public Vector<Object> verificarPromociones(Producto prod){
		if (logger.isDebugEnabled()) {
			logger.debug("verificarPromociones(Producto) - start");
		}

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
			result.addElement(new Double(promocionAplicada.getPorcentajeDescuento()));
			
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("verificarPromociones(Producto) - end");
		}
		return result;
    }

    /*public HashMap cargarProductoComplementario(){
    	return new HashMap();
    }*/
    public void ejecutarProductoComplementario(int p, Venta venta){
    	//NADA, CAJA SIN PROMOCIONES
    }
    
    /**
     * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#getPromocionesNuevasSobreProducto(String)
     */
   /* public Vector getPromocionesPatrocinantesPorProducto(String codProducto, Vector promocionesViejas){
    	return new Vector();
    }*/
    
    /**
     * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#actualizarPrecios(Producto, Venta)
     */
    public void actualizarPrecios(Producto p, Venta v){
    	try{
    		this.actualizarPreciosExt(p, v);
    	}catch (Exception e){
    		logger.debug("actualizarPrecios(Producto p, Venta v)", e);
    	}
    }
    
	/**
	 * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#agregarPromocionesALoteSentencias(Venta, Statement, String)
	 */
	public void agregarPromocionesALoteSentencias(Venta venta, Statement loteSentenciasCR, String fechaTransString) throws SQLException {
		//NADA, si la extension no esta activa no hay promociones
	}
	
	public void ventanaPromociones(Venta venta){
		//Nada, nunca se ejecuta
	}
	
	/**
	 * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#revisarDescuentoCorporativo()
	 */
	public void revisarPromociones(Venta venta){
		//Nada
	}
	
	/**
	 * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#ejecutarProductoGratis(Venta venta)
	 */
	public void ejecutarProductoGratis(Venta venta){
		//Nada
	}
	
	/**
	 * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#deshacerProductoGratis()
	 */
	public void deshacerProductoGratis(DetalleTransaccion detalleDeshacer){
		//Nada
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'HashMap'
	* Fecha: agosto 2011
	*/
	public void syncTransaccionesExt(int numTransaccion, Statement loteSentenciasSrv
			, Statement loteSentenciasCR, HashMap<String,Object> criterioClave){
		//nada
	}
	
	/**
	 * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#abrirVentanaCondicionesVenta()
	 */
	public void abrirVentanaCondicionesVenta(Vector<CondicionVenta> condiciones){
		
	}
	
	/**
	 * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#getDonacionesVenta()
	 */
	public double getDonacionesVenta(){
		return 0;
	}
	
	/**
	 * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.ActualizadorPrecios#getDonacionesRegistradasTipo1()
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Vector getDonacionesRegistradasTipo1(Vector donaciones){
		return new Vector();
	}

	public void agregarDeleteTransaccionCondiciones(Venta ap, Statement loteSentenciasCR) throws SQLException {
		// TODO Apéndice de método generado automáticamente
		
	}

	public void agregarInsertTransaccionCondiciones(Venta venta, Statement loteSentenciasCR, String fechaServString, String sentenciaSQL, int i) throws SQLException {
		// TODO Apéndice de método generado automáticamente
		
	}

	public void agregarDeleteTransaccionCondiciones(String identificacionEspera, Statement loteSentenciasCR) throws SQLException {
		// TODO Apéndice de método generado automáticamente
		
	}

	public void cargarPromocionesCombo() {
		// TODO Apéndice de método generado automáticamente
		
	}

	public void imprimirFormasDePagoEspeciales(Cliente cte, FormaDePago fPago, double monto , int numTrans) {
		// TODO Apéndice de método generado automáticamente
		
	}

	public void agregarPagosEspeciales(Venta venta) {
		// TODO Apéndice de método generado automáticamente
		
	}

	public void eliminarPagosEspeciales() {
		// TODO Apéndice de método generado automáticamente
		
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<DonacionRegistrada> consultarDonacionesPorVenta(int codTienda, String fechaTrans, int numCaja, int numTransaccion, boolean local){
		return new Vector<DonacionRegistrada>();
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<PromocionRegistrada> consultarPromocionesPorVenta(int codTienda, String fechaTrans, int numCaja, int numTransaccion, boolean local){
		return new Vector<PromocionRegistrada>();
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<RegaloRegistrado> consultarRegalosPorVenta(int codTienda, String fechaTrans, int numCaja, int numTransaccion, boolean local){
		return new Vector<RegaloRegistrado>();
	}
	
	public void cancelarDonacionesAnulacion(){
		
	}
	
	public void agregarDonacionesCanceladas(Anulacion anulacion, Statement loteSentenciasCR, String fechaTransString) throws SQLException {
		
	}
	

	public boolean recalcularProductosCombo(int codPromocion, int numDetalle,Vector<ProductoCombo> productos){
		 return false;
	 }
	
	public void crearTablaPromoTicket()
	{
		
	}

	public ArrayList getPromoSorteoInfo(Integer clave) {
		// TODO Auto-generated method stub
		return new ArrayList();
	}
	
	public void actualizarTablaPromoTicket(int codPromocion){
		
	}

	public ArrayList<Object> getPromoSorteoInfo(String clave) {
		// TODO Auto-generated method stub
		return new ArrayList<Object>();
	}

	public boolean recalcularAhorroEnCompra(int codPromocion, int numDetalle,Vector<Producto> productosAhorroAux) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
