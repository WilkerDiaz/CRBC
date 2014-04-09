/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.cr.actualizadorPrecios.promociones
 * Programa   : Linea.java
 * Creado por : jgraterol
 * Creado en  : 12/02/2009 03:18:05 PM
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

public class Linea {

	private String codDepartamento;
	private String codlinea;
	
	/**
	 * @param codDepartamento
	 * @param codlinea
	 */
	public Linea(String codDepartamento, String codlinea) {
		super();
		this.codDepartamento = codDepartamento;
		this.codlinea = codlinea;
	}
	
	public String getCodDepartamento() {
		return codDepartamento;
	}
	
	public void setCodDepartamento(String codDepartamento) {
		this.codDepartamento = codDepartamento;
	}
	
	public String getCodlinea() {
		return codlinea;
	}
	
	public void setCodlinea(String codlinea) {
		this.codlinea = codlinea;
	}
	
}
