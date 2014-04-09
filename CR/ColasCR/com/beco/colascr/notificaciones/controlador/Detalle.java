/**
 * =============================================================================
 * Proyecto   : AplicacionesPDAServidorDeTienda
 * Paquete    : com.beco.sistemas.aplicacionespda.servidordetienda.controlador
 * Programa   : Detalle.java
 * Creado por : Marcos Grillo
 * Creado en  : 15/12/2009 - 08:31:02 PM
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
package com.beco.colascr.notificaciones.controlador;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Vector;

/** 
 * Descripción: 
 * 		Maneja las operaciones con los objetos de las lineas de productos de las transacciones y Servicios.
 */
public class Detalle{
	private String nombre;
	private double precioRegular;
	private double montoImpuesto;
	private String codigo;
	private double cantidad;
	private String descripcionLarga;
	private double porcentaje;
	
	public Detalle(){
		nombre = "";
		precioRegular = 0.0;
		montoImpuesto = 0.0;
		codigo = "";
		cantidad = 0;
		descripcionLarga = "";
		porcentaje = 0.0;
	}
	
	public Detalle(String codigo, int cantidad){
		this.codigo = codigo;
		this.cantidad = cantidad;
	}

	public double getPorcentaje(){
		return porcentaje;
	}
	
	public void setPorcentaje(double p){
		porcentaje = p;
	}
	
	public String getDescripcionLarga(){
		return descripcionLarga;
	}
	
	public void setDescripcionLarga(String desc){
		descripcionLarga = desc;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public void setNombre(String n){
		nombre = n;
	}
	
	public double getPVP(){
		return precioRegular+montoImpuesto;
	}
	
	public double getPrecioFinal(){
		if(cantidad >= 1)
			return (precioRegular+montoImpuesto)*cantidad;
		else
			return precioRegular+montoImpuesto;
	}
	
	public double getPrecioSinImpuesto(){
		if(cantidad >= 1)
			return (precioRegular)*cantidad;
		else
			return precioRegular;
	}
	
	public String getCodigo(){
		return codigo;
	}
	
	public void setCodigo(String codigo){
		this.codigo = codigo;
	}
	
	public double getCantidad(){
		return cantidad;
	}
	
	public void setCantidad(double cantidad){
		this.cantidad = cantidad;
	}
	
	public double getPrecioRegular(){
		return precioRegular;
	}
	
	public void setPrecioRegular(double precio){
		precioRegular = precio;
	}
	
	public double getMontoImpuesto(){
		return montoImpuesto*cantidad;
	}
	
	public double getImpuestoSinCantidad(){
		return montoImpuesto;
	}
	public void setImpuesto(double imp){
		montoImpuesto = imp;
	}
	public double getSubtotal(){
		return precioRegular*cantidad;
	}
	/**
	 * Dado un vector de Detalle elimina todos los productos que se repitan
	 * y deja un solo código con todos los elementos sumados a su cantidad
	 * @param productos vector con los productos que se desean colapsar
	 * @return Vector sin códigos repetidos
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Detalle> eliminarDuplicados(Vector<Detalle> productos){
		Vector<Detalle> auxiliar = new Vector<Detalle>();
		boolean esta = false;
		int numProds = productos.size();
		for(int i=0; i < numProds; ++i){
			Detalle producto = productos.elementAt(i);
			int numProdsAux = auxiliar.size();
			for(int j=0; j < numProdsAux; ++j){
				Detalle prod = auxiliar.elementAt(j); 
				if(prod.getCodigo().equals(producto.getCodigo())){
					prod.setCantidad(prod.getCantidad()+1);
					esta = true;
					break;
				}
			}
			if(!esta){
				auxiliar.add(producto);
			}
			esta = false;
		}
		productos = auxiliar;
		return auxiliar;
	}
	
	/**
	 * Ordena un vector de detalles por su código
	 * @param productos
	 * @return Vector
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Detalle> ordenarDetalles(Vector<Detalle> productos){
		Detalle[] detalles = new Detalle[productos.size()];
		for(int i=0; i < detalles.length;++i){
			detalles[i] = productos.elementAt(i);
		}
		Arrays.sort(detalles,new MayorOMenor());
		Vector<Detalle> auxiliar = new Vector<Detalle>();
		for(int i=0; i < detalles.length;++i){
			auxiliar.add((Detalle)detalles[i]);
		}
		return auxiliar;
	}
}

class MayorOMenor implements Comparator<Object>{

	public int compare(Object arg0, Object arg1) {
		double codigo1 = Double.parseDouble(((Detalle)arg0).getCodigo());
		double codigo2 = Double.parseDouble(((Detalle)arg1).getCodigo());
		return Double.compare(codigo1,codigo2);
	}
	
}