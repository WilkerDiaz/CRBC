/**
 * =============================================================================
 * Proyecto   : CajaRegistradora
 * Paquete    : com.becoblohm.cr.manejarservicio
 * Programa   : DetalleServicio.java
 * Creado por : gmartinelli
 * Creado en  : 02/04/2004 09:01:30 AM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 
 * Fecha       : 
 * Analista    : 
 * Descripción : 
 * =============================================================================
 */
package com.becoblohm.cr.manejarservicio;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.Vector;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ProductoExcepcion;
import com.becoblohm.cr.manejarventa.CondicionVenta;
import com.becoblohm.cr.manejarventa.Detalle;
import com.becoblohm.cr.manejarventa.Producto;
import com.becoblohm.cr.utiles.MathUtil;

/** 
 * Descripción: 
 * 		Clase heredada de Detalle que maneja los detalles de servicios de
 * Apartados, Cotizaciones, etc.
 */
public class DetalleServicio extends Detalle implements Cloneable,Serializable{

	private static final long serialVersionUID = 1L;
	private char estadoRegistro;
	private float cantPedidos = 0; /** Atributo de Lista de Regalos **/
	private double abonos = 0; /** Atributo de Lista de Regalos **/
	private float cantAbonadosT = 0; /** Atributo de Lista de Regalos **/
	private float cantVendidos = 0; /** Atributo de Lista de Regalos **/

	/**
	 * Constructor de la clase DetalleServicio que crea un nuevo detalle a partir de sus parametros.
	 * @param p Objeto producto a ingresar en el detalle.
	 * @param c Cantidad de items del producto.
	 * @param condV Condicion de venta (Empaque, Normal, etc).
	 * @param codSup Codigo del supervisor que autoriza el detalle (Si existe).
	 * @param precioF Precio final del producto.
	 * @param mtoImp Monto del impuesto.
	 * @param tipoCap Tipo de Captura del producto (Teclado, Escaner).
	 * @param codProm Codigo de la promocion aplicada (Si existe).
	 */
	public DetalleServicio(Producto p, float c, String condV, String codSup, double precioF, double mtoImp, String tipoCap, int codProm, String tipoEntrega) {
		super(p, c, condV, codSup, precioF, mtoImp, tipoCap, codProm, tipoEntrega);
    }
    
	/**
	 * Contructor que crea un nuevo Detalle a partir del codProducto buscandolo en la base de datos.
	 * @param codProd Codigo del producto.
	 * @param tipoCap Tipo de Captura del producto (Teclado, Escaner).
	 * @param cant Cantidad de items del producto.
	 * @param fechaTrans Fecha del servicio.
	 * @param horaInicioTrans  Hora de inicio del servicio.
	 * @throws ProductoExcepcion Si el producto no se encuentra en la Base de Datos.
	 * @throws BaseDeDatosExcepcion Si ocurre un error con la Base De Datos.
	 */
	DetalleServicio(String codProd, String tipoCap, float cant, Date fechaTrans, Time horaInicioTrans) throws ProductoExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		super(codProd, tipoCap, cant, fechaTrans, horaInicioTrans);
    }

	/**
	 * Contructor que crea un nuevo Detalle a partir del codProducto buscandolo en la base de datos. Además, verifica
	 * si el producto posee una promoción para ver si el precio promocional es mejor al anterior. De ser así, se aplica
	 * esa nueva promoción al producto.
	 * @param codProd Codigo del producto.
	 * @param tipoCap Tipo de Captura del producto (Teclado, Escaner).
	 * @param cant Cantidad de items del producto.
	 * @param fechaTrans Fecha del servicio.
	 * @param horaInicioTrans  Hora de inicio del servicio.
	 * @param codPromo  Codigo de la promocion aplicada al producto.
	 * @throws ProductoExcepcion Si el producto no se encuentra en la Base de Datos.
	 * @throws BaseDeDatosExcepcion Si ocurre un error con la Base De Datos.
	 */
	DetalleServicio(String codProd, String tipoCap, float cant, Date fechaTrans, Time horaInicioTrans, int codPromo, double precioReg, double precioFin, double mtoImp, String condVta) throws ProductoExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		super(codProd, tipoCap, cant, fechaTrans, horaInicioTrans);
		this.getProducto().setPrecioRegular(precioReg);
		this.setCodPromocion(codPromo);
		this.setCondicionVenta(condVta);
		this.setPrecioFinal(precioFin);
		this.setMontoImpuesto(mtoImp);
	}

	/**
	 * Método getEstadoServicio
	 * 
	 * @return char
	 */
	public char getEstadoRegistro() {
		return estadoRegistro;
	}

	/**
	 * Método setEstadoServicio
	 * 
	 * @param c
	 */
	public void setEstadoRegistro(char c) {
		estadoRegistro = c;
	}
	
	/**
	 * Constructor para el DetalleServicio de Lista de Regalos.
	 * @param codProd
	 * @param tipoCap
	 * @param cant
	 * @param fechaTrans
	 * @param horaInicioTrans
	 * @throws ProductoExcepcion
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	public DetalleServicio(String codProd, String tipoCap, float cant, Date fechaTrans, Time horaInicioTrans, double precioReg, double precioFin, double mtoImp,
					int cantComprados, int cantAbonadosT, double abonos) throws ProductoExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		super(codProd, tipoCap, cant, fechaTrans, horaInicioTrans);
		this.getProducto().setPrecioRegular(precioReg);
		this.setPrecioFinal(precioFin);
		this.setMontoImpuesto(mtoImp);
		this.cantVendidos = cantComprados;
		this.cantAbonadosT = cantAbonadosT;
		this.cantPedidos = cant; 
		this.abonos = abonos;
	}
	
	/**
	 * Metodo de Lista de Regalos
	 * @return
	 */
	public float getCantPedidos() {
		return cantPedidos;
	}

	/**
	 * Metodo de Lista de Regalos
	 * @param f
	 */
	public void setCantPedidos(float f) {
		cantPedidos = f;
	}
	
	/**
	 * Metodo de Lista de Regalos
	 * @return
	 */
	public float getCantVendidos(){
		return cantVendidos;
	}
	
	/**
	 * Metodo de Lista de Regalos
	 * @return
	 */
	public float getCantRestantes(){
		float cantAbonadosP = (int)MathUtil.roundDouble(this.getAbonos()/(this.getPrecioFinal()*((this.getProducto().getImpuesto().getPorcentaje()/100)+1)));
		float restantes = this.getCantidad() - this.cantVendidos - this.cantAbonadosT - cantAbonadosP;
		float cantRestantes = (restantes > 0)
						   ? restantes
						   : 0;
		return cantRestantes;
	}
	
	/**
	 * Metodo de Lista de Regalos
	 * @return
	 */
	public double getAbonos(){
		return abonos;
	}

	/**
	 * @param abono
	 */	
	public void setAbonos(double abono){
		this.abonos = abono;
	}

	/**
	 * @param cant
	 */
	public void setCantVendidos(float cant) {
		this.cantVendidos = cant;
	}
	
	/**
	 * @return Cantidad de productos que poseen abonos totales
	 */
	public float getCantAbonadosT() {
		return cantAbonadosT;
	}
	
	/**
	 * Establece la cantidad de productos con abonos totales para este detalle
	 * 
	 * @param cant Cantidad a establecer
	 */
	public void setCantAbonadosT(float cant) {
		this.cantAbonadosT = cant;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Object clone(){
		DetalleServicio detalle = new DetalleServicio(
				this.getProducto(), 
				this.getCantidad(), 
				this.getCondicionVenta(), 
				this.getCodSupervisor(), 
				this.getPrecioFinal(), 
				this.getMontoImpuesto(), 
				this.getTipoCaptura(), 
				this.getCodPromocion(), 
				this.getTipoEntrega());
		detalle.setEstadoRegistro(this.estadoRegistro);
		detalle.setCantPedidos(this.cantPedidos);
		detalle.setAbonos(this.abonos);
		detalle.setCantAbonadosT(this.cantAbonadosT);
		detalle.setCantVendidos(this.cantVendidos);
		detalle.setCondicionVenta(this.getCondicionVenta());
		detalle.setMontoDctoCombo(this.getMontoDctoCombo());
		Vector<CondicionVenta> condicionesVentaClonadas = new Vector<CondicionVenta>();
		for(int i=0;i<this.getCondicionesVentaPromociones().size();i++){
			condicionesVentaClonadas.addElement((CondicionVenta)(this.getCondicionesVentaPromociones().elementAt(i)).clone());
		}
		detalle.setCondicionesVentaPromociones(condicionesVentaClonadas);
		detalle.setCondicionVenta(detalle.construirCondicionVentaString());
		detalle.setCodPromocion(this.getCodPromocion());
		return detalle;
	}
}
