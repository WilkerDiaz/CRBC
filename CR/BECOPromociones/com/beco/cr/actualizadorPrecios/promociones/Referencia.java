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

public class Referencia {
	private String codDepartamento;
	private String codLinea;
	private int codSeccion;
	private String codReferencia;
	
	
	
	/**
	 * @param codDepartamento
	 * @param linea
	 * @param seccion
	 */
	public Referencia(String codDepartamento, String linea, int seccion, String referencia) {
		super();
		this.codDepartamento = codDepartamento;
		this.codLinea = linea;
		this.codSeccion = seccion;
		this.codReferencia = referencia;
	}



	public String getCodDepartamento() {
		return codDepartamento;
	}



	public void setCodDepartamento(String codDepartamento) {
		this.codDepartamento = codDepartamento;
	}



	public String getCodLinea() {
		return codLinea;
	}



	public void setCodLinea(String codLinea) {
		this.codLinea = codLinea;
	}



	public int getCodSeccion() {
		return codSeccion;
	}



	public void setCodSeccion(int codSeccion) {
		this.codSeccion = codSeccion;
	}



	public String getCodReferencia() {
		return codReferencia;
	}



	public void setCodReferencia(String codReferencia) {
		this.codReferencia = codReferencia;
	}
	
	
}
