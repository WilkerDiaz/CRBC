/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.manejarpago
 * Programa   : Pago.java
 * Creado por : irojas
 * Creado en  : 06-oct-03 14:11:00
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.4
 * Fecha       : 17/06/2008 11:50 AM 
 * Analista    : jgraterol, aavila
 * Descripción : Agregados el resto de los setter y getter. Corregido el metodo clone
 * =============================================================================
 * Versión     : 1.3
 * Fecha       : 14/05/2004 11:50 AM 
 * Analista    : gmartineli
 * Descripción : Agregado constructor que usa todos los campos del Pago.
 * =============================================================================
 * Versión     : 1.2
 * Fecha       : 11/03/2004 10:30 AM 
 * Analista    : IROJAS
 * Descripción : Se cambio el tipo del atributo "codFormaPago" de int a String.
 * =============================================================================
 * Versión     : 1.01
 * Fecha       : 19/02/2004 10:58 AM 
 * Analista    : gmartinelli
 * Descripción : Agregadas Lineas de 83 1 89
 * 				 Agregado método getCodFormaPago para implementacion
 * 				 de almacenamiento den Base de Datos de los pagos en
 * 				 efectivo
 * =============================================================================
 */
package com.becoblohm.cr.extensiones.impl.manejarpago;

import java.io.Serializable;

import com.becoblohm.cr.extensiones.ManejoPagosFactory;


/** 
 * Descripción: 
 * 		Maneja las instancias de las formas de pago realizadas a las transacciones
 * Por ahora se maneja unicamente efectivo. Las otras formas de pagos serán implementadas
 * luego por EPA
 */
public class Pago implements Serializable {
	
	private FormaDePago formaPago;
	private double monto;
	private String codBanco;
	private String numDocumento;
	private String numCuenta;
	private String numConformacion;
	private int numReferencia;
	private String cedTitular;
	private String codAutorizante;

	/**
	 * Constructor para Pago
	 *
	 * @param codFPago - Codigo de la forma de pago.
	 * @param mto - Monto de la forma de pago.
	 * @param codBco
	 * @param numDoc
	 * @param numCta
	 * @param numConf
	 * @param numRef
	 * @param cedTitular
	 */
	public Pago(String codFPago, double mto, String codBco, String numDoc, String numCta, 
				String numConf, int numRef, String cedTitular) {
		this.formaPago = ManejoPagosFactory.getInstance().cargarFormaDePago(codFPago);
		this.monto = mto;
		this.codBanco = codBco;
		this.numDocumento = numDoc;
		this.numCuenta = numCta;
		this.numConformacion = numConf;
		this.numReferencia = numRef;
		this.cedTitular = cedTitular;
	}
	
	/**
	 * Constructor para Pago
	 *
	 * @param codFPago - Codigo de la forma de pago.
	 * @param mto - Monto de la forma de pago.
	 * @param codBco
	 * @param numDoc
	 * @param numCta
	 * @param numConf
	 * @param numRef
	 * @param cedTitular
	 */
	public Pago(String codFPago, double mto, String codBco, String numDoc, String numCta, 
				int numConf, int numRef, String cedTitular) {
		this.formaPago = ManejoPagosFactory.getInstance().cargarFormaDePago(codFPago);
		this.monto = mto;
		this.codBanco = codBco;
		this.numDocumento = numDoc;
		this.numCuenta = numCta;
		this.numConformacion = Integer.toString(numConf);
		this.numReferencia = numRef;
		this.cedTitular = cedTitular;
	}
	
	/**
	 * Constructor para Pago.java
	 *
	 * @param fPago
	 * @param mto
	 * @param numDoc
	 * @param numCta
	 * @param numConf
	 * @param numRef
	 * @param cedTitular
	 */
	public Pago(FormaDePago fPago, double mto, String codBco, String numDoc, String numCta, 
				String numConf, int numRef, String cedTitular) {
		this.formaPago = fPago;
		this.monto = mto;
		this.codBanco = codBco;
		this.numDocumento = numDoc;
		this.numCuenta = numCta;
		this.numConformacion = numConf;
		this.numReferencia = numRef;
		this.cedTitular = cedTitular;
	}

	/**
	 * Constructor para Pago.java
	 *
	 * @param fPago
	 * @param mto
	 * @param numDoc
	 * @param numCta
	 * @param numConf
	 * @param numRef
	 * @param cedTitular
	 */
	public Pago(FormaDePago fPago, double mto, String codBco, String numDoc, String numCta, 
				int numConf, int numRef, String cedTitular) {
		this.formaPago = fPago;
		this.monto = mto;
		this.codBanco = codBco;
		this.numDocumento = numDoc;
		this.numCuenta = numCta;
		this.numConformacion = Integer.toString(numConf);
		this.numReferencia = numRef;
		this.cedTitular = cedTitular;
	}
	
	/**
	 * Constructor vacio para pago
	 * **/
	public Pago(){
		
	}
	
	/**
	 * Actualiza el monto de la forma de pago cuando por ejemplo se repiten formas de pago (Efectivo).
	 * @param montoPagoActual Monto del nuevo pago efectuado.
	 */
	public void actualizarMonto(double montoPagoActual) {
		monto += montoPagoActual;
	}

	/**
	 * Obtiene el monto cancelado con esa forma de pago.
	 * @return double - El monto cancelado.
	 */
	public double getMonto() {
		return monto;
	}

	/**
	 * Método getCedTitular
	 * 
	 * @return String
	 */
	public String getCedTitular() {
		return cedTitular;
	}

	/**
	 * Método getNumConformacion
	 * 
	 * @return int
	 */
	public String getNumConformacion() {
		return numConformacion;
	}

	/**
	 * Método getNumCuenta
	 * 
	 * @return String
	 */
	public String getNumCuenta() {
		return numCuenta;
	}

	/**
	 * Método getNumDocumento
	 * 
	 * @return String
	 */
	public String getNumDocumento() {
		return numDocumento;
	}

	/**
	 * Método getNumReferencia
	 * 
	 * @return int
	 */
	public int getNumReferencia() {
		return numReferencia;
	}

	/**
	 * Método getFormaPago
	 * 
	 * @return FormaDePago
	 */
	public FormaDePago getFormaPago() {
		return formaPago;
	}

	/**
	 * Método getCodBanco
	 * 
	 * @return String
	 */
	public String getCodBanco() {
		return codBanco;
	}

	/**
	 * @param d
	 */
	public void setMonto(double d) {
		monto = d;
	}
	public String getCodAutorizante() {
		return codAutorizante;
	}
	public void setCodAutorizante(String codAutorizante) {
		this.codAutorizante = codAutorizante;
	}
	
	
	/**
	 * Implementado clone de object para ser usado en donaciones
	 * Acutalizacion beco del 17/06/2008
	 * **/
	public Object clone(){
		Pago pago= new Pago();
	
		pago.setCedTitular(this.cedTitular);
		pago.setCodAutorizante(this.codAutorizante);
		pago.setCodBanco(this.codBanco);
		pago.setFormaPago((FormaDePago)this.formaPago.clone());
		pago.setMonto(this.monto);
		pago.setNumConformacion(this.numConformacion);
		pago.setNumCuenta(this.numCuenta);
		pago.setNumDocumento(this.numDocumento);
		pago.setNumReferencia(this.numReferencia);	
		return pago;
	}

	/**
	 * @param cedTitular el cedTitular a establecer
	 */
	public void setCedTitular(String cedTitular) {
		this.cedTitular = cedTitular;
	}

	/**
	 * @param codBanco el codBanco a establecer
	 */
	public void setCodBanco(String codBanco) {
		this.codBanco = codBanco;
	}

	/**
	 * @param formaPago el formaPago a establecer
	 */
	public void setFormaPago(FormaDePago formaPago) {
		this.formaPago = formaPago;
	}

	/**
	 * @param numConformacion el numConformacion a establecer
	 */
	public void setNumConformacion(String numConformacion) {
		this.numConformacion = numConformacion;
	}

	/**
	 * @param numCuenta el numCuenta a establecer
	 */
	public void setNumCuenta(String numCuenta) {
		this.numCuenta = numCuenta;
	}

	/**
	 * @param numDocumento el numDocumento a establecer
	 */
	public void setNumDocumento(String numDocumento) {
		this.numDocumento = numDocumento;
	}

	/**
	 * @param numReferencia el numReferencia a establecer
	 */
	public void setNumReferencia(int numReferencia) {
		this.numReferencia = numReferencia;
	}
}
