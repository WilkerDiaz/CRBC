/**
 * =============================================================================
 * Proyecto   : TransferenciaInmediataProdsCR
 * Paquete    : com.beco.colascr.transferenciainmediata.entidades
 * Programa   : DetallePromocion.java
 * Creado por : gmartinelli
 * Creado en  : 28-jun-05 10:16:01
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versi�n     : 1.1
 * Fecha       : 28-jun-05 10:16:01
 * Analista    : gmartinelli
 * Descripci�n : Implementaci�n inicial.
 * =============================================================================
 */
package com.beco.colascr.transferenciainmediata.entidades; 

/**
 * Descripci�n:
 * 
 */

public class DetallePromocion {

	private int codPromocion;
	private int numDetalle;
	private int numCupon;
	private String codDepartamento;
	private String codLineaSeccion;
	private String codProducto;
	private double porcentaje;
	private double precio;
	private String estadoRegistro;
	/**
	 * Constructor para DetallePromocion.java
	 *
	 * 
	 */
	public DetallePromocion(int codProm, int numDet, int numCup, String codDep, String codLin, String codPro, double porc, double preFin, String estadoRegistro) {
		this.codPromocion = codProm;
		this.numDetalle = numDet;
		this.numCupon = numCup;
		this.codDepartamento = (codDep==null) ? "null" : "'" + codDep.trim() + "'";
		this.codLineaSeccion = (codLin==null) ? "null" : "'" + codLin.trim() + "'";
		this.codProducto = (codPro==null) ? "null" : "'" + codPro.trim() + "'";
		this.porcentaje = porc;
		this.precio = preFin;
		this.estadoRegistro = estadoRegistro;
	}

	/**
	 * M�todo getCodDepartamento
	 * 
	 * @return
	 * String
	 */
	public String getCodDepartamento() {
		return codDepartamento;
	}

	/**
	 * M�todo getCodLineaSeccion
	 * 
	 * @return
	 * String
	 */
	public String getCodLineaSeccion() {
		return codLineaSeccion;
	}

	/**
	 * M�todo getCodProducto
	 * 
	 * @return
	 * String
	 */
	public String getCodProducto() {
		return codProducto;
	}

	/**
	 * M�todo getCodPromocion
	 * 
	 * @return
	 * int
	 */
	public int getCodPromocion() {
		return codPromocion;
	}

	/**
	 * M�todo getNumCupon
	 * 
	 * @return
	 * int
	 */
	public int getNumCupon() {
		return numCupon;
	}

	/**
	 * M�todo getNumDetalle
	 * 
	 * @return
	 * int
	 */
	public int getNumDetalle() {
		return numDetalle;
	}

	/**
	 * M�todo getPorcentaje
	 * 
	 * @return
	 * double
	 */
	public double getPorcentaje() {
		return porcentaje;
	}

	/**
	 * M�todo getPrecio
	 * 
	 * @return
	 * double
	 */
	public double getPrecio() {
		return precio;
	}
	
	public String getEstadoRegistro() {
		return estadoRegistro;
	}

	public void setEstadoRegistro(String estadoRegistro) {
		this.estadoRegistro = estadoRegistro;
	}

}
