/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.manejarpago
 * Programa   : FormaDePago.java
 * Creado por : gmartinelli
 * Creado en  : 14-may-04 10:18:40
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.2
 * Fecha       : 17/06/2008 11:50 AM 
 * Analista    : jgraterol, aavila
 * Descripción : Agregados el resto de los setter y getter. Corregido el metodo clone
 * =============================================================================
 * Versión     : 1.1
 * Fecha       : 14-may-04 10:18:40
 * Analista    : gmartinelli
 * Descripción : Implementación inicial.
 * =============================================================================
 */
package com.becoblohm.cr.extensiones.impl.manejarpago; 

import java.io.Serializable;

/**
 * Descripción:
 * 
 */

public class FormaDePago implements Serializable {
	
	private String codigo;
	private int tipo;
	private String nombre;
	private String codigoBanco;
	private boolean indicarBanco;
	private boolean indicarNumDocumento;
	private boolean indicarNumCuenta;
	private boolean indicarNumConformacion;
	private boolean indicarNumReferencia;
	private boolean indicarCedulaTitular;
	private boolean validarSaldoCliente;
	private boolean permiteVuelto;
	private double montoMinimo;
	private double montoMaximo;
	private double montoComision;
	private boolean incrementaEntregaParcial;
	private boolean requiereAutorizacion;
	
	protected FormaDePago() {
		// SOLO PARA SER USADO POR SUBCLASES
	}
	
	public FormaDePago(String cod , int tipoF, String nomb, String codBanco, boolean indicarBco,
				boolean indNumDoc, boolean indNumCta, boolean indNumConf, boolean indNumRef, 
				boolean indCedTit, boolean validarSaldoCte, boolean permiteVto, double mtoMin,
				double mtoMax, double mtoCom, boolean entParcial, boolean reqAutorizacion) {
		this.codigo = cod;
		this.tipo = tipoF;
		this.nombre = nomb;
		this.codigoBanco = codBanco;
		this.indicarBanco = indicarBco;
		this.indicarNumDocumento = indNumDoc;
		this.indicarNumCuenta = indNumCta;
		this.indicarNumConformacion = indNumConf;
		this.indicarNumReferencia = indNumRef;
		this.indicarCedulaTitular = indCedTit;
		this.validarSaldoCliente = validarSaldoCte;
		this.permiteVuelto = permiteVto;
		this.montoMinimo = mtoMin;
		this.montoMaximo = mtoMax;
		this.montoComision = mtoCom;
		this.incrementaEntregaParcial = entParcial;
		this.requiereAutorizacion = reqAutorizacion;
	}
	
	/**
	 * Método getCodigo
	 * 
	 * @return
	 * String
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Método getCodigoBanco
	 * 
	 * @return
	 * String
	 */
	public String getCodigoBanco() {
		return codigoBanco;
	}

	/**
	 * Método isIndicarCedulaTitular
	 * 
	 * @return
	 * boolean
	 */
	public boolean isIndicarCedulaTitular() {
		return indicarCedulaTitular;
	}

	/**
	 * Método isIndicarNumConformacion
	 * 
	 * @return
	 * boolean
	 */
	public boolean isIndicarNumConformacion() {
		return indicarNumConformacion;
	}

	/**
	 * Método isIndicarNumCuenta
	 * 
	 * @return
	 * boolean
	 */
	public boolean isIndicarNumCuenta() {
		return indicarNumCuenta;
	}

	/**
	 * Método isIndicarNumDocumento
	 * 
	 * @return
	 * boolean
	 */
	public boolean isIndicarNumDocumento() {
		return indicarNumDocumento;
	}

	/**
	 * Método isIndicarNumReferencia
	 * 
	 * @return
	 * boolean
	 */
	public boolean isIndicarNumReferencia() {
		return indicarNumReferencia;
	}

	/**
	 * Método getMontoComision
	 * 
	 * @return
	 * double
	 */
	public double getMontoComision() {
		return montoComision;
	}

	/**
	 * Método getMontoMaximo
	 * 
	 * @return
	 * double
	 */
	public double getMontoMaximo() {
		return montoMaximo;
	}

	/**
	 * Método getMontoMinimo
	 * 
	 * @return
	 * double
	 */
	public double getMontoMinimo() {
		return montoMinimo;
	}

	/**
	 * Método getNombre
	 * 
	 * @return
	 * String
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método isValidarSaldoCliente
	 * 
	 * @return
	 * boolean
	 */
	public boolean isValidarSaldoCliente() {
		return validarSaldoCliente;
	}

	/**
	 * Método isIndicarBanco
	 * 
	 * @return
	 * boolean
	 */
	public boolean isIndicarBanco() {
		return indicarBanco;
	}

	/**
	 * Método isPermiteVuelto
	 * 
	 * @return
	 * boolean
	 */
	public boolean isPermiteVuelto() {
		return permiteVuelto;
	}

	/**
	 * Método isEntregaParcial
	 * 
	 * @return
	 * boolean
	 */
	public boolean isIncrementaEntregaParcial() {
		return incrementaEntregaParcial;
	}

	/**
	 * Método isRequiereAutorizacion
	 * 
	 * @return
	 * boolean
	 */
	public boolean isRequiereAutorizacion() {
		return requiereAutorizacion;
	}

	public int getTipo() {
		return tipo;
	}
	
	/**
	 * Implementado clone de object para ser usado en donaciones
	 * Actualizacion BECO 17/06/2008
	 * **/
	public Object clone(){
		FormaDePago formaPago=new FormaDePago();
		
		formaPago.setCodigo(this.codigo);
		formaPago.setCodigoBanco(this.codigoBanco);
		formaPago.setIncrementaEntregaParcial(this.incrementaEntregaParcial);
		formaPago.setIndicarBanco(this.indicarBanco);
		formaPago.setIndicarCedulaTitular(this.indicarCedulaTitular);
		formaPago.setIndicarNumConformacion(this.indicarNumConformacion);
		formaPago.setIndicarNumCuenta(this.indicarNumCuenta);
		formaPago.setIndicarNumDocumento(this.indicarNumDocumento);
		formaPago.setIndicarNumReferencia(this.indicarNumReferencia);
		formaPago.setMontoComision(this.montoComision);
		formaPago.setMontoMaximo(this.montoMaximo);
		formaPago.setMontoMinimo(this.montoMinimo);
		formaPago.setNombre(this.nombre);
		formaPago.setPermiteVuelto(this.permiteVuelto);
		formaPago.setRequiereAutorizacion(this.requiereAutorizacion);
		formaPago.setTipo(this.tipo);
		formaPago.setValidarSaldoCliente(this.validarSaldoCliente);
		
		return formaPago;
	}

	/**
	 * @param codigo el codigo a establecer
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @param codigoBanco el codigoBanco a establecer
	 */
	public void setCodigoBanco(String codigoBanco) {
		this.codigoBanco = codigoBanco;
	}

	/**
	 * @param incrementaEntregaParcial el incrementaEntregaParcial a establecer
	 */
	public void setIncrementaEntregaParcial(boolean incrementaEntregaParcial) {
		this.incrementaEntregaParcial = incrementaEntregaParcial;
	}

	/**
	 * @param indicarBanco el indicarBanco a establecer
	 */
	public void setIndicarBanco(boolean indicarBanco) {
		this.indicarBanco = indicarBanco;
	}

	/**
	 * @param indicarCedulaTitular el indicarCedulaTitular a establecer
	 */
	public void setIndicarCedulaTitular(boolean indicarCedulaTitular) {
		this.indicarCedulaTitular = indicarCedulaTitular;
	}

	/**
	 * @param indicarNumConformacion el indicarNumConformacion a establecer
	 */
	public void setIndicarNumConformacion(boolean indicarNumConformacion) {
		this.indicarNumConformacion = indicarNumConformacion;
	}

	/**
	 * @param indicarNumCuenta el indicarNumCuenta a establecer
	 */
	public void setIndicarNumCuenta(boolean indicarNumCuenta) {
		this.indicarNumCuenta = indicarNumCuenta;
	}

	/**
	 * @param indicarNumDocumento el indicarNumDocumento a establecer
	 */
	public void setIndicarNumDocumento(boolean indicarNumDocumento) {
		this.indicarNumDocumento = indicarNumDocumento;
	}

	/**
	 * @param indicarNumReferencia el indicarNumReferencia a establecer
	 */
	public void setIndicarNumReferencia(boolean indicarNumReferencia) {
		this.indicarNumReferencia = indicarNumReferencia;
	}

	/**
	 * @param montoComision el montoComision a establecer
	 */
	public void setMontoComision(double montoComision) {
		this.montoComision = montoComision;
	}

	/**
	 * @param montoMaximo el montoMaximo a establecer
	 */
	public void setMontoMaximo(double montoMaximo) {
		this.montoMaximo = montoMaximo;
	}

	/**
	 * @param montoMinimo el montoMinimo a establecer
	 */
	public void setMontoMinimo(double montoMinimo) {
		this.montoMinimo = montoMinimo;
	}

	/**
	 * @param nombre el nombre a establecer
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @param permiteVuelto el permiteVuelto a establecer
	 */
	public void setPermiteVuelto(boolean permiteVuelto) {
		this.permiteVuelto = permiteVuelto;
	}

	/**
	 * @param requiereAutorizacion el requiereAutorizacion a establecer
	 */
	public void setRequiereAutorizacion(boolean requiereAutorizacion) {
		this.requiereAutorizacion = requiereAutorizacion;
	}

	/**
	 * @param tipo el tipo a establecer
	 */
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	/**
	 * @param validarSaldoCliente el validarSaldoCliente a establecer
	 */
	public void setValidarSaldoCliente(boolean validarSaldoCliente) {
		this.validarSaldoCliente = validarSaldoCliente;
	}	
	
}
