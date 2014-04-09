/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.transaccion
 * Programa   : DetalleTransaccion.java
 * Creado por : apeinado
 * Creado en  : 06/10/2003 03:23:31 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.2
 * Fecha       : 02/07/2008 14:26 PM
 * Analista    : jgraterol
 * Descripción : Implementado metodo clone
 * =============================================================================
 * Versión     : 1.1.2
 * Fecha       : 24/03/2004 14:26 PM
 * Analista    : gmartinelli
 * Descripción : Eliminado el parametro del codigo de la tienda de los constructores
 * 				porque ya no se usa en la superclase, este valor ahora se tiene de 
 * 				la cabecera de la transaccion
 * =============================================================================
 * Versión     : 1.1.1
 * Fecha       : 02/03/2004 09:50 AM
 * Analista    : gmartinelli
 * Descripción : Cambio de cantidad de int a float
 * =============================================================================
 * Versión     : 1.1 (según CVS antes del cambio)
 * Fecha       : 10/02/2004 09:51 AM
 * Analista    : IROJAS
 * Descripción : Se modificó la siguiente línea:
 * 					Línea 67: Constructor de la clase: Cambio del tipo de parámetro 
 * 							  codProd de Long a String.
 * =============================================================================
 */
package com.becoblohm.cr.manejarventa;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.Vector;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ProductoExcepcion;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.utiles.MathUtil;

/** 
 * Descripción: 
 * 		Clase heredada de Detalle que maneja los detalles de las transacciones de
 * Venta, Devlucion, etc., es decir, transacciones distintas a los servicios. A diferencia
 * de los servicios, esta contiene el codigo del vendedor de la transaccion.
 */
public class DetalleTransaccion extends Detalle implements Serializable,Cloneable {
	
	private String codVendedor;
	private double montoDctoEmpleado;
	private float cantidadADevolver;


	/**
	 * Constructor de la clase DetalleTransaccion que crea un nuevo detalle a partir de sus parametros.
	 * @param codVend Codigo del Vendedor.
	 * @param p Objeto producto a ingresar en el detalle.
	 * @param c Cantidad de items del producto.
	 * @param condV Condicion de venta (Empaque, Normal, etc).
	 * @param codS Codigo del supervisor que autoriza el detalle (Si existe).
	 * @param precioF Precio final del producto.
	 * @param mtoImp Monto del impuesto.
	 * @param tipoCap Tipo de Captura del producto (Teclado, Escaner).
	 * @param codProm Codigo de la promocion aplicada (Si existe).
	 */
	public DetalleTransaccion(String codVend, Producto p, float c, String condV, String codS, double precioF, double mtoImp, String tipoCap, int codProm, String tipoEnt) {
		super(p, c, condV, codS, precioF, mtoImp, tipoCap, codProm, tipoEnt);
		codVendedor = codVend;
    }
    
	/**
	 * Constructor de la clase DetalleTransaccion que crea un nuevo detalle a partir de sus parametros.
	 * @param codVend Codigo del Vendedor.
	 * @param p Objeto producto a ingresar en el detalle.
	 * @param c Cantidad de items del producto.
	 * @param condV Condicion de venta (Empaque, Normal, etc).
	 * @param codS Codigo del supervisor que autoriza el detalle (Si existe).
	 * @param precioF Precio final del producto.
	 * @param mtoImp Monto del impuesto.
	 * @param tipoCap Tipo de Captura del producto (Teclado, Escaner).
	 * @param codProm Codigo de la promocion aplicada (Si existe).
	 * @param cantDevuelta Número de productos a devolver.
	 */
	public DetalleTransaccion(String codVend, Producto p, float c, String condV, String codS, double precioF, double mtoImp, String tipoCap, int codProm, String tipoEnt, float cantDevuelta) {
		super(p, c, condV, codS, precioF, mtoImp, tipoCap, codProm, tipoEnt);
		codVendedor = codVend;
		cantidadADevolver = cantDevuelta;
	}    
    
    
	/**
	 * Contructor que crea un nuevo Detalle a partir del codProducto buscandolo en la base de datos.
	 * @param codProd Codigo del producto.
	 * @param codVend Codigo del Vendedor.
	 * @param tipoCap Tipo de Captura del producto (Teclado, Escaner).
	 * @param cant Cantidad de items del producto.
	 * @param fechaTrans Fecha de la transaccion.
	 * @param horaInicioTrans  Hora de inicio de la transaccion.
	 * @throws ProductoExcepcion Si el producto no se encuentra en la Base de Datos.
	 * @throws BaseDeDatosExcepcion Si ocurre un error con la Base De Datos.
	 */
    public DetalleTransaccion(String codProd, String codVend, String tipoCap, 
    		float cant, Date fechaTrans, Time horaInicioTrans, boolean leidoBD) 
    		throws ProductoExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		super(codProd, tipoCap, cant, fechaTrans, horaInicioTrans, leidoBD);
			codVendedor = codVend;	
			montoDctoEmpleado = 0;
    }

    
    
    public DetalleTransaccion(String codProd, String codVend, String tipoCap, 
    		float cant, Date fechaTrans, Time horaInicioTrans) 
    		throws ProductoExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
    	this(codProd, codVend, tipoCap, 
        		cant, fechaTrans, horaInicioTrans, false);
    }
    
	/**
	 * Obtiene el codigo del vendedor de la linea de la transaccion.
	 * @return String - String que representa el codigo del vendedor.
	 */
	public String getCodVendedor() {
		return codVendedor;
	}

	/**
	 * Método getDctoEmpleado
	 * 
	 * @return double
	 */
	public double getDctoEmpleado() {
		return montoDctoEmpleado;
	}

	/**
	 * Método setDctoEmpleado
	 * 
	 * @param nvoDcto
	 */
	public void setDctoEmpleado(double nvoDcto) {
		montoDctoEmpleado = nvoDcto;
	}
	
	public char getCodTipoEntrega() {
		char tipo = Sesion.COD_ENTREGA_CAJA; // Tipo por defecto : Caja
		if (getTipoEntrega().equals(Sesion.ENTREGA_CLIENTE_RETIRA)) {
			tipo = Sesion.COD_ENTREGA_CLIENTE_RETIRA;
		} else if (getTipoEntrega().equals(Sesion.ENTREGA_DOMICILIO)) {
			tipo = Sesion.COD_ENTREGA_DOMICILIO; // Flete
		} else if (getTipoEntrega().equals(Sesion.ENTREGA_DESPACHO)) {
			tipo = Sesion.COD_ENTREGA_DESPACHO;
		}
		return tipo;
	}
	
	public void setCodTipoEntrega(char cod) {
		switch (cod) {
			case Sesion.COD_ENTREGA_CLIENTE_RETIRA :
				setTipoEntrega(Sesion.ENTREGA_CLIENTE_RETIRA);
				break;
			case Sesion.COD_ENTREGA_CAJA :
				setTipoEntrega(Sesion.ENTREGA_CAJA);
				break;
			case Sesion.COD_ENTREGA_DOMICILIO :
				setTipoEntrega(Sesion.ENTREGA_DOMICILIO);
				break;
			case Sesion.COD_ENTREGA_DESPACHO :
				setTipoEntrega(Sesion.ENTREGA_DESPACHO);
				break;
			default : 
				setTipoEntrega("");
		}
	}

	/**
	 * @return
	 */
	public float getCantidadADevolver() {
		return cantidadADevolver;
	}

	/**
	 * Anula un producto del detalle de transaccion restando 1 en sus cantidades.
	 * @param cantidad Cantidad de productos a eliminar del renglon
	 */
	public void anularProductoXCambio(float cantidad) {
		this.cantidadADevolver -= cantidad;
		this.cantidadADevolver = (float)MathUtil.roundDouble(this.cantidadADevolver);
	}


	/**
	 * @param f
	 */
	public void setCantidadADevolver(float f) {
		cantidadADevolver = f;
	}

	public boolean isDevuelto(String codProducto)
	{
		if (this.getProducto().getCodProducto().equals(codProducto))
			return true;		
		return false;
	}
	
	/**
	 * Implementa el metodo clone de object
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Object clone(){
		DetalleTransaccion detalleTransaccion= new DetalleTransaccion(this.codVendedor, 
				this.getProducto(), 
				this.getCantidad(), 
				this.getCondicionVenta(), 
				this.getCodSupervisor(), 
				this.getPrecioFinal(), 
				this.getMontoImpuesto(), 
				this.getTipoCaptura(), 
				this.getCodPromocion(), 
				this.getTipoEntrega(), 
				this.getCantidadADevolver());
		detalleTransaccion.setCodVendedor(this.codVendedor);
		detalleTransaccion.setDctoEmpleado(this.montoDctoEmpleado);
		detalleTransaccion.setCondicionEspecial(this.isCondicionEspecial());
		detalleTransaccion.setCantidadCambiada(this.isCantidadCambiada());
		detalleTransaccion.setVariacion(this.getVariacion());
		detalleTransaccion.setMontoDctoCombo(this.getMontoDctoCombo());
		detalleTransaccion.setMontoDctoCorporativo(this.getMontoDctoCorporativo());
		detalleTransaccion.setMontoDctoEmpleado(this.montoDctoEmpleado);
		Vector<CondicionVenta> condicionesVentaClonadas = new Vector<CondicionVenta>();
		for(int i=0;i<this.getCondicionesVentaPromociones().size();i++){
			condicionesVentaClonadas.addElement((CondicionVenta)(this.getCondicionesVentaPromociones().elementAt(i)).clone());
		}
		detalleTransaccion.setCondicionesVentaPromociones(condicionesVentaClonadas);
		
		return detalleTransaccion;
	}

	/**
	 * @return el montoDctoEmpleado
	 */
	public double getMontoDctoEmpleado() {
		return montoDctoEmpleado;
	}

	/**
	 * @param montoDctoEmpleado el montoDctoEmpleado a establecer
	 */
	public void setMontoDctoEmpleado(double montoDctoEmpleado) {
		this.montoDctoEmpleado = montoDctoEmpleado;
	}

	/**
	 * @param codVendedor el codVendedor a establecer
	 */
	public void setCodVendedor(String codVendedor) {
		this.codVendedor = codVendedor;
	}
}
