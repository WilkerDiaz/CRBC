/**
 * =============================================================================
 * Proyecto   : CajaRegistradora
 * Paquete    : com.becoblomh.impuesto
 * Programa   : Impuesto.java
 * Creado por : irojas
 * Creado en  : 06-oct-03 14:08:36
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
package com.becoblohm.cr.manejarventa;

import java.io.Serializable;
import java.util.Date;

/** 
 * Descripción: 
 * 		Clase que maneja las instancias de impuestos. Posee el codigo, la vigencia,
 * la region donde aplica y el porcentaje a aplicar.
 */
public class Impuesto implements Serializable {
	
	private String codImpuesto;
	private String codRegion;
	private String nombreImpuesto;
	private Date fechaVigencia;
	private double porcentaje;
		
	/**
	 * Constructor de la clase Impuesto que crea una instancia de Impuesto.
	 * @param codImp Codigo del Impuesto.
	 * @param region Region donde aplica el impuesto.
	 * @param nombre Nombre del Impuesto.
	 * @param fechaVigencia Fecha a partir de la cual entro en vigencia el impuesto.
	 * @param porcentaje Porcentaje a aplicar.
	 */
	public Impuesto (String codImp, String region, String nombre, Date fechaVigencia, double porcentaje) {
		this.codImpuesto = codImp;
		this.codRegion = region;
		this.nombreImpuesto = nombre;
		this.fechaVigencia = fechaVigencia;
		this.porcentaje = porcentaje;
	}

	/**
	 * Ontiene el porcentaje de Impuesto.
	 * @return double - El monto del porcentaje de impuesto.
	 */
	public double getPorcentaje() {
		return porcentaje;
	}

	/**
	 * Obtiene el codigo del impuesto.
	 * @return String - String que representa el codigo del impuesto.
	 */
	public String getCodImpuesto() {
		return codImpuesto;
	}
	/**
	 * Método getNombreImpuesto
	 * 
	 * @return
	 * String
	 */
	public String getNombreImpuesto() {
		return nombreImpuesto;
	}
	/**
	 * @param d
	 */
	public void setPorcentaje(double d) {
		porcentaje = d;
	}
	
	/**
	 * Implementa el metodo clone de object para impuesto
	 * **/
	public Object clone(){
		return null;
	}
}