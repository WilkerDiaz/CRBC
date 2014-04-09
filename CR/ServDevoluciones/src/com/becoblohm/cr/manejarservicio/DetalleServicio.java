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
 * Versi�n     : 
 * Fecha       : 
 * Analista    : 
 * Descripci�n : 
 * =============================================================================
 */
package com.becoblohm.cr.manejarservicio;

import java.sql.Time;
import java.util.Date;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ProductoExcepcion;
import com.becoblohm.cr.manejarventa.Detalle;
import com.becoblohm.cr.manejarventa.Producto;
import com.becoblohm.cr.utiles.MathUtil;

/** 
 * Descripci�n: 
 * 		Clase heredada de Detalle que maneja los detalles de servicios de
 * Apartados, Cotizaciones, etc.
 */
public class DetalleServicio extends Detalle implements Cloneable {

	private char estadoRegistro;
	private float cantPedidos = 0; /** Atributo de Lista de Regalos **/
	private double abonos = 0; /** Atributo de Lista de Regalos **/
	private float cantAbonadosT = 0; /** Atributo de Lista de Regalos **/
	private float cantComprados = 0; /** Atributo de Lista de Regalos **/


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
	 * Contructor que crea un nuevo Detalle a partir del codProducto buscandolo en la base de datos. Adem�s, verifica
	 * si el producto posee una promoci�n para ver si el precio promocional es mejor al anterior. De ser as�, se aplica
	 * esa nueva promoci�n al producto.
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
		this.cantComprados = cantComprados;
		this.cantAbonadosT = cantAbonadosT;
		this.cantPedidos = cant; 
		this.abonos = abonos;
	}
	

	/**
	 * M�todo getEstadoServicio
	 * 
	 * @return char
	 */
	public char getEstadoRegistro() {
		return estadoRegistro;
	}

	/**
	 * M�todo setEstadoServicio
	 * 
	 * @param c
	 */
	public void setEstadoRegistro(char c) {
		estadoRegistro = c;
	}
	
	
	public Object clone(){
		return null;
	}

	/**
	 * @return
	 */
	public double getAbonos() {
		return abonos;
	}

	/**
	 * @return
	 */
	public float getCantAbonadosT() {
		return cantAbonadosT;
	}

	/**
	 * @return
	 */
	public float getCantVendidos() {
		return cantComprados;
	}

	/**
	 * @return
	 */
	public float getCantPedidos() {
		return cantPedidos;
	}

	/**
	 * @param d
	 */
	public void setAbonos(double d) {
		abonos = d;
	}

	/**
	 * @param f
	 */
	public void setCantAbonadosT(float f) {
		cantAbonadosT = f;
	}

	/**
	 * @param f
	 */
	public void setCantComprados(float f) {
		cantComprados = f;
	}

	/**
	 * @param f
	 */
	public void setCantPedidos(float f) {
		cantPedidos = f;
	}


	/**
	 * Metodo de Lista de Regalos
	 * @return
	 */
	public float getCantRestantes(){
		float cantAbonadosP = (int)MathUtil.roundDouble(this.getAbonos()/(this.getPrecioFinal()*((this.getProducto().getImpuesto().getPorcentaje()/100)+1)));
		float restantes = this.getCantidad() - this.cantComprados - this.cantAbonadosT - cantAbonadosP;
		float cantRestantes = (restantes > 0)
						   ? restantes
						   : 0;
		return cantRestantes;
	}
}
