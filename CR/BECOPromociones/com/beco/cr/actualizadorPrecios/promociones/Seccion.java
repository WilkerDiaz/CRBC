/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.cr.actualizadorPrecios.promociones
 * Programa   : Seccion.java
 * Creado por : jgraterol
 * Creado en  : 12/02/2008 03:18:05 PM
 *
 * (c) CENTROBECO, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * ----------------------------------------------------------------------------- 
 * Versión     : 1.O 
 * Fecha       : 05/08/2008 11:18:05 AM
 * Analista    : JGRATEROL
 * Descripción :
 * =============================================================================
 * */
package com.beco.cr.actualizadorPrecios.promociones;

public class Seccion {
	private String codDepartamento;
	private String linea;
	private int seccion;
	
	
	
	/**
	 * @param codDepartamento
	 * @param linea
	 * @param seccion
	 */
	public Seccion(String codDepartamento, String linea, int seccion) {
		super();
		this.codDepartamento = codDepartamento;
		this.linea = linea;
		this.seccion = seccion;
	}
	
	public String getCodDepartamento() {
		return codDepartamento;
	}
	public void setCodDepartamento(String codDepartamento) {
		this.codDepartamento = codDepartamento;
	}
	public String getLinea() {
		return linea;
	}
	public void setLinea(String linea) {
		this.linea = linea;
	}
	public int getSeccion() {
		return seccion;
	}
	public void setSeccion(int seccion) {
		this.seccion = seccion;
	}
}
