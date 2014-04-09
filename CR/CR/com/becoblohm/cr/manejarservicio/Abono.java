/**
 * =============================================================================
 * Proyecto   : CajaRegistradora
 * Paquete    : com.becoblohm.manejarservicio
 * Programa   : Abono.java
 * Creado por : gmartinelli
 * Creado en  : 05/04/2004 09:23 AM
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

import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.manejarventa.Producto;

/** 
 * Descripción: 
 * 		Maneja los abonos realizados a un apartado
 */
public class Abono implements Serializable{

	private static final long serialVersionUID = 1L;
	private int tienda;
	private int caja;
	private int numAbono;
	private float cantidadProductos;
	private Date fechaAbono;
	private char tipoTransAbono;
	private String codCajero;
	private Time horaInicia;
	private Time horaFinaliza;
	private double montoBase;
	private double montoVuelto;
	private double montoRemanente;
	private double montoImp;
	private char estadoAbono;
	private Vector<Pago> pagos;
	private Producto producto;
	
	Abono (int tda, int cja, int num, Date f, Time horaI, char tipoA, String cajero){
		this.tienda = tda;
		this.caja = cja;
		this.numAbono = num;
		this.fechaAbono = f;
		this.tipoTransAbono = tipoA;
		this.codCajero = cajero;
		this.horaInicia = horaI;
		this.horaFinaliza = null;
		this.montoBase = 0.0;
		this.montoVuelto = 0.0;
		this.montoRemanente = 0.0;
		this.estadoAbono = Sesion.ABONO_ACTIVO;
		this.cantidadProductos = 0;
		this.pagos = new Vector<Pago>();
	}
	
	public Abono (int tda, int cja, int num, Date f, Time horaI, Time horaF, double montoB, char estado, char tipoA, String cajero){
		this.tienda = tda;
		this.caja = cja;
		this.numAbono = num;
		this.fechaAbono = f;
		this.tipoTransAbono = tipoA;
		this.codCajero = cajero;
		this.horaInicia = horaI;
		this.horaFinaliza = horaF;
		this.montoBase = montoB;
		this.estadoAbono = estado;
		this.cantidadProductos = 0;
		this.pagos = new Vector<Pago>();
	}
	
	// Constructor para Abonos de Lista de Regalos.
	public Abono (Producto prod, int tienda, int caja, Date fecha, Time horaI, double montoB, double montoImp, String cajero){
		this.tienda = tienda;
		this.caja = caja;
		this.fechaAbono = fecha;
		this.horaInicia = horaI;
		this.montoBase = montoB;
		this.codCajero = cajero;
		this.pagos = new Vector<Pago>();
		this.producto = prod;
		this.cantidadProductos = 0;
		this.montoImp = montoImp;
	}
	
	/**
	 * Método getPagos
	 * 
	 * @return Vector
	 */
	public Vector<Pago> getPagos() {
		return pagos;
	}

	/**
	 * Método getCaja
	 * 
	 * @return int
	 */
	public int getCaja() {
		return caja;
	}

	/**
	 * Método getFechaAbono
	 * 
	 * @return Date
	 */
	public Date getFechaAbono() {
		return fechaAbono;
	}

	/**
	 * Método getNumAbono
	 * 
	 * @return int
	 */
	public int getNumAbono() {
		return numAbono;
	}

	/**
	 * Método getTienda
	 * 
	 * @return int
	 */
	public int getTienda() {
		return tienda;
	}
	
	/**
	 * Método incrementarMontoAbonado
	 * 
	 * @param mto
	 */
	public void incrementarMontoAbonado(double mto) {
		this.montoBase += mto;
	}

	/**
	 * Método getCodCajero
	 * 
	 * @return String
	 */
	public String getCodCajero() {
		return codCajero;
	}

	/**
	 * Método getEstadoAbono
	 * 
	 * @return char
	 */
	public char getEstadoAbono() {
		return estadoAbono;
	}

	/**
	 * Método getHoraFinaliza
	 * 
	 * @return Time
	 */
	public Time getHoraFinaliza() {
		return horaFinaliza;
	}

	/**
	 * Método getHoraInicia
	 * 
	 * @return Time
	 */
	public Time getHoraInicia() {
		return horaInicia;
	}

	/**
	 * Método getMontoBase
	 * 
	 * @return double
	 */
	public double getMontoBase() {
		return montoBase;
	}

	/**
	 * Método getTipoTransAbono
	 * 
	 * @return char
	 */
	public char getTipoTransAbono() {
		return tipoTransAbono;
	}

	/**
	 * Método setPagos
	 * 
	 * @param vector
	 */
	public void setPagos(Vector<Pago> vector) {
		pagos = vector;
	}

	/**
	 * @param time
	 */
	public void setHoraFinaliza(Time time) {
		horaFinaliza = time;
	}

	/**
	 * Método setEstadoAbono
	 * 
	 * @param c
	 */
	public void setEstadoAbono(char c) {
		estadoAbono = c;
	}

	/**
	 * @param d
	 */
	public void setMontoBase(double d) {
		montoBase = d;
	}

	/**
	 * Método getMontoVuelto
	 * 
	 * @return
	 * double
	 */
	public double getMontoVuelto() {
		double mtoPagado = 0.0;
		for (int i=0; i<this.pagos.size(); i++){
			Pago p= (Pago)pagos.elementAt(i);
			mtoPagado += p.getMonto();
		}
		this.montoVuelto = mtoPagado - this.montoBase;
		return montoVuelto;
	}

	/**
	 * Método setMontoVuelto
	 * 
	 * @param d
	 * void
	 */
	public void setMontoVuelto(double d) {
		montoVuelto = d;
	}

	/**
	 * Método getMontoRemanente
	 * 
	 * @return double
	 */
	public double getMontoRemanente() {
		if(this.montoVuelto > 0)
			this.montoRemanente = 1 - (this.montoVuelto - StrictMath.floor(this.montoVuelto));
		else montoRemanente = 0.00;	
		return montoRemanente;
	}

	/**
	 * Método setMontoRemanente
	 * 
	 * @param d
	 */
	public void setMontoRemanente(double d) {
		montoRemanente = d;
	}

	/**
	 * @param c
	 */
	public void setTipoTransAbono(char c) {
		tipoTransAbono = c;
	}

	public Producto getProducto(){
		return producto;
	}

	/**
	 * @param cantidad
	 */
	public void incrementarCantidad(float cantidad) {
		this.cantidadProductos += cantidad;
		
	}
	
	/**
	 * @param cantidad
	 */
	public float getCantidad() {
		return cantidadProductos;
	}
	
	public double getImpuestoProducto(){
		return montoImp;
	}
}
