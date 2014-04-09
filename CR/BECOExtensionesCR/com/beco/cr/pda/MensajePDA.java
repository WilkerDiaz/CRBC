/**
 * =============================================================================
 * Proyecto   : BECOExtensionesCR
 * Paquete    : com.beco.cr.pda
 * Programa   : MensajePDA.java
 * Creado por : mmiyazono
 * Creado en  : 11/08/2009 - 01:18:00 PM
 *
 * (c) CENTROBECO, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 
 * Fecha       : 
 * Analista    : 
 * Descripción : 
 * =============================================================================
 */

package com.beco.cr.pda;

import java.util.Vector;

import com.becoblohm.cr.manejadorsesion.Sesion;

/**
 *	Esta clase refiere a los objetos que representan MensajePDA. 
 */
public class MensajePDA {
	
	private String codigoDeBarras;
	private String clave;
	private String nombreMetodo;
	private String nombreModulo;
	private Vector<DetalleProducto> detalles = new Vector<DetalleProducto>();
	private String descFormaDePago = "Promociones";
	private double montoDescuento = 0.0;
	private String mensaje = "";
	private String idEspera = "16234123";
	private double montoTotal = 0.0;
	private String tipoConsulta = Sesion.pdaTipoMensajeConsultaPrecio;
	private boolean hayError = true;
	private int inicioSesion;
	private String nombreOperacion;
	
	public MensajePDA(){
		codigoDeBarras = "";
		nombreMetodo = "";
		nombreModulo = "";
		clave = "";
		inicioSesion = -1;
		nombreOperacion = "";
	}
	
	public void setInicioSesion(int resultado){
		inicioSesion = resultado;
	}
	public String getNombreOperacion(){
		return nombreOperacion;
	}
	public void setNombreOperacion(String nombre){
		nombreOperacion = new String(nombre);
	}
	public int getInicioSesion(){
		return inicioSesion;
	}
	
	public void setCodigoDeBarras(String cod){
		codigoDeBarras = new String(cod);
	}
	
	public void setNombreMetodo(String met){
		nombreMetodo = new String(met);
	}
	
	public void setNombreModulo(String mod){
		nombreModulo = new String(mod);
	}
	
	public void setClave(String c){
		clave = c;
	}
	
	public String getClave(){
		return clave;
	}
	
	public String getCodigoDeBarras(){
		return codigoDeBarras;
	}
	
	public String getNombreMetodo(){
		return nombreMetodo;
	}
	
	public String getNombreModulo(){
		return nombreModulo;
	}
	
	/**
	 * 
	 * @param detalles
	 * @param idEspera
	 */
	public MensajePDA(Vector<DetalleProducto> detalles, String idEspera){
		this.detalles = detalles;
		this.idEspera = idEspera;
	}
	
	/**
	 * @param detalles
	 * @param descFormaDePago
	 * @param montoDescuento
	 * @param mensaje
	 * @param idEspera
	 */
	public MensajePDA(Vector<DetalleProducto> detalles, String descFormaDePago, double montoDescuento, String mensaje, String idEspera, double total) {
 
		this.detalles = detalles;
		this.descFormaDePago = descFormaDePago;
		this.montoDescuento = montoDescuento;
		this.mensaje = mensaje;
		this.idEspera = idEspera;
		this.montoTotal = total;
	}

	/**
	 * @return el descFormaDePago
	 */
	public String getDescFormaDePago() {
		return descFormaDePago;
	}

	/**
	 * @param descFormaDePago el descFormaDePago a establecer
	 */
	public void setDescFormaDePago(String descFormaDePago) {
		this.descFormaDePago = descFormaDePago;
	}

	/**
	 * @return el detalles
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<DetalleProducto> getDetalles() {
		return detalles;
	}

	/**
	 * @param detalles el detalles a establecer
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void setDetalles(Vector<DetalleProducto> detalles) {
		this.detalles = detalles;
	}

	/**
	 * @return el idEspera
	 */
	public String getIdEspera() {
		return idEspera;
	}

	/**
	 * @param idEspera el idEspera a establecer
	 */
	public void setIdEspera(String idEspera) {
		this.idEspera = idEspera;
	}

	/**
	 * @return el mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje el mensaje a establecer
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * @return el montoDescuento
	 */
	public double getMontoDescuento() {
		return montoDescuento;
	}

	/**
	 * @param montoDescuento el montoDescuento a establecer
	 */
	public void setMontoDescuento(double montoDescuento) {
		this.montoDescuento = montoDescuento;
	}

	/**
	 * @return el montoTotal
	 */
	public double getMontoTotal() {
		return montoTotal;
	}

	/**
	 * @param montoTotal el montoTotal a establecer
	 */
	public void setMontoTotal(double montoTotal) {
		this.montoTotal = montoTotal;
	}

	/**
	 * @return el tipoConsulta
	 */
	public String getTipoConsulta() {
		return tipoConsulta;
	}

	/**
	 * @param tipoConsulta el tipoConsulta a establecer
	 */
	public void setTipoConsulta(String tipoConsulta) {
		this.tipoConsulta = tipoConsulta;
	}

	/**
	 * @return el hayError
	 */
	public boolean isHayError() {
		return hayError;
	}

	/**
	 * @param hayError el hayError a establecer
	 */
	public void setHayError(boolean hayError) {
		this.hayError = hayError;
	}

 	
}
