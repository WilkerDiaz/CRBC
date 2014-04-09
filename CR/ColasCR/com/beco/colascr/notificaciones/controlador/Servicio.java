/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.manejarservicio
 * Programa   : Servicio.java
 * Creado por : gmartinelli
 * Creado en  : 02/04/2004 09:08 AM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1
 * Fecha       : 14-may-04 10:17
 * Analista    : gmartinelli
 * Descripción : Implementado manejo de Auditorias.
 * =============================================================================
 */
package com.beco.colascr.notificaciones.controlador;

import java.io.Serializable;
import java.util.Vector;



/** 
 * Descripción: 
 * 		Superclase de Servicios. Maneja las operaciones con Servicios (Sean Apartados,
 * Cotizaciones, etc.)
 */
public class Servicio implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8013253814489941439L;
	protected String nombreTienda;
	protected String codigoCorreo;
	protected String direccionTienda;
	protected String numTelefono;
	protected String tipoServicio;
	protected int numServicio;
	protected String fechaServicio;
	protected String nombreCliente;
	protected double montoBase;
	protected double montoTotal;
	protected double montoImpuesto;
	protected double saldoRestante;
	protected double montoAbonado;
	protected int lineasFacturacion;
	protected Vector<Detalle> detallesServicio;
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Servicio(){
		nombreTienda = "";
		direccionTienda = "";
		numTelefono = "";
		tipoServicio = "";
		fechaServicio = "";
		nombreCliente = "";
		detallesServicio = new Vector<Detalle>();
	}
	
	public String getNombreTienda(){
		return nombreTienda;
	}
	
	public String getDireccionTienda(){
		return direccionTienda;
	}
	public String getNumTlf(){
		return numTelefono;
	}
	public int getNumServicio(){
		return numServicio;
	}
	public String getFechaServicio(){
		return fechaServicio;
	}
	public String getNombreCliente(){
		return nombreCliente;
	}
	public double getMontoBase(){
		return montoBase;
	}
	public double getMontoImpuesto(){
		return montoImpuesto;
	}
	public double getMontoTotal(){
		return montoTotal;
	}
	public int getLineasFacturacion(){
		return lineasFacturacion;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Detalle> getDetalles(){
		return detallesServicio;
	}
	public String getTipoServicio(){
		return tipoServicio;
	}
	public String getCorreo(){
		return codigoCorreo;
	}
	
	public void setNombreTienda(String t){
		nombreTienda = t;
	}
	public void setCorreo(String c){
		codigoCorreo = c;
	}
	public void setDireccionTienda(String d){
		direccionTienda = d;
	}
	public void setNumTlf(String n){
		numTelefono = n;
	}
	public void setNumServicio(int n){
		numServicio = n;
	}
	public void setFechaServicio(String f){
		fechaServicio = f;
	}
	public void setNombreCliente(String n){
		nombreCliente = n;
	}
	public void setMontoBase(double m){
		montoBase = m;
	}
	public void setMontoImpuesto(double m){
		montoImpuesto = m;
	}
	public void setMontoTotal(double m){
		montoTotal = m;
	}
	public void setLineasFacturacion(int l){
		lineasFacturacion = l;
	}
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void setDetalles(Vector<Detalle> v){
		detallesServicio = v;
	}	
	public void setTipoServicio(String t){
		tipoServicio = t;
	}

	public double getMontoAbonado() {
		return montoAbonado;
	}

	public void setMontoAbonado(double montoAbonado) {
		this.montoAbonado = montoAbonado;
	}

	public double getSaldoRestante() {
		return saldoRestante;
	}

	public void setSaldoRestante(double saldoRestante) {
		this.saldoRestante = saldoRestante;
	}
}
