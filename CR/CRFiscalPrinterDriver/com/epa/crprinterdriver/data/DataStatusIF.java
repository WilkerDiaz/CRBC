/*
 * $Id: DataStatusIF.java,v 1.2.2.3 2005/05/09 14:28:19 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CRFiscalPrinterDriver 1.1
 * Paquete		: com.epa.crprinterdriver.data
 * Programa		: DataStatusIF.java
 * Creado por	: Programa8
 * Creado en 	: 13-abr-2005 13:15:41
 * (C) Copyright 2005 SuperFerretería EPA C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisión	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: DataStatusIF.java,v $
 * Revision 1.2.2.3  2005/05/09 14:28:19  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.2  2005/05/09 14:18:58  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.1  2005/05/06 19:12:48  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.2  2005/05/05 12:08:44  programa8
 * Versión 2.0.1 Final
 *
 * Revision 1.1.2.1  2005/04/13 21:57:05  programa8
 * Driver fiscal al 13/04/2005
 *
 * ===========================================================================
 */
package com.epa.crprinterdriver.data;

import java.util.Date;

/**
 * <p>
 * Clase que representa la respuesta del dispositivo fiscal al solicitarle su estado actual
 * </p>
 * <p>
 * <a href="DataStatusIF.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - Arístides Castillo Colmenárez $Author: programa8 $
 * @version $Revision: 1.2.2.3 $ - $Date: 2005/05/09 14:28:19 $
 * @since 13-abr-2005
 * 
 */
public class DataStatusIF {

	private int ultValorSecuencia;
	private int ultTipoComandoEjecutado;
	private int status;
	private int estadoFiscal;
	private Date fechaHoraIF;
	// Para todos los parametros
	private int numCFEmitidoDesdeZ;
	private int numDNFEmitidoDesdeZ;
	private int numNCEmitidoDesdeZ;
	private int numCFTotal;
	private int numDNFTotal;
	private int numUltRepZ;
	private int numNCTotal; //Total de Notas de Credito Aplica para IBMGD4
	// Para parametros E
	private float ventasExentas;
	// Para parámetros A, B, C, D y R
	private float ventas;
	private float impuestos;
	
	
	
	/**
	 * Constructor de la clase
	 * @since 13-abr-2005
	 * @param ultSeq Ultimo identificador de secuencia ejecutada
	 * @param ultCmd Ultimo tipo de comando ejecutado
	 * @param fechaHora Fecha y hora de la impresora fiscal
	 * @param status Estado de driver fiscal
	 */
	public DataStatusIF(int ultSeq, int ultCmd, Date fechaHora, int status) {
		super();
		ultValorSecuencia = ultSeq;
		ultTipoComandoEjecutado = ultCmd;
		fechaHoraIF = fechaHora;
		this.status = status;
	}

	/**
	 * Devuelve el total de impuestos
	 * Depende del tipo de comando solicitado (A, B, C) 
	 * @return Devuelve impuestos.
	 */
	public float getImpuestos() {
		return impuestos;
	}
	/**
	 * Establece el monto total de impuestos
	 * @param impuestos El impuestos a establecer.
	 */
	public void setImpuestos(float impuestos) {
		this.impuestos = impuestos;
	}
	/**
	 * Devuelve el numero de comprobantes fiscales emitidos desde el ultimo Z
	 * @return Devuelve numCFEmitidoDesdeZ.
	 */
	public int getNumCFEmitidoDesdeZ() {
		return numCFEmitidoDesdeZ;
	}
	/**
	 * Establece el numero de comprobantes fiscales emitidos desde el ultimo Z
	 * @param numCFEmitidoDesdeZ El numCFEmitidoDesdeZ a establecer.
	 */
	public void setNumCFEmitidoDesdeZ(int numCFEmitidoDesdeZ) {
		this.numCFEmitidoDesdeZ = numCFEmitidoDesdeZ;
	}
	/**
	 * Devuelve el numero de comprobantes fiscales emitidos de por vida en la impresora fiscal
	 * @return Devuelve numCFTotal.
	 */
	public int getNumCFTotal() {
		return numCFTotal;
	}
	/**
	 * Establece el numero de comprobantes fiscales emitidos de por vida en la impresora fiscal
	 * @param numCFTotal El numCFTotal a establecer.
	 */
	public void setNumCFTotal(int numCFTotal) {
		this.numCFTotal = numCFTotal;
	}
	/**
	 * Devuelve el numero de documentos no fiscales emitidos desde el último Z 
	 * @return Devuelve numDNFEmitidoDesdeZ.
	 */
	public int getNumDNFEmitidoDesdeZ() {
		return numDNFEmitidoDesdeZ;
	}
	/**
	 * Establece el número de documentos no fiscales emitidos desde el ultimo Z
	 * @param numDNFEmitidoDesdeZ El numDNFEmitidoDesdeZ a establecer.
	 */
	public void setNumDNFEmitidoDesdeZ(int numDNFEmitidoDesdeZ) {
		this.numDNFEmitidoDesdeZ = numDNFEmitidoDesdeZ;
	}
	/**
	 * Devuelve el numero de documentos no fiscales emitidos de por vida
	 * @return Devuelve numDNFTotal.
	 */
	public int getNumDNFTotal() {
		return numDNFTotal;
	}
	/**
	 * Establece el numero de documentos no fiscales emitidos de por vida
	 * @param numDNFTotal El numDNFTotal a establecer.
	 */
	public void setNumDNFTotal(int numDNFTotal) {
		this.numDNFTotal = numDNFTotal;
	}
	/**
	 * Devuelve el numero del ultimo reporte Z emitido
	 * @return Devuelve numUltRepZ.
	 */
	public int getNumUltRepZ() {
		return numUltRepZ;
	}
	/**
	 * Establece el numero del ultimo reporte Z emitido
	 * @param numUltRepZ El numUltRepZ a establecer.
	 */
	public void setNumUltRepZ(int numUltRepZ) {
		this.numUltRepZ = numUltRepZ;
	}
	/**
	 * Devuelve el total de ventas.
	 * Depende del tipo de estado solicitado (A, B, C)
	 * @return Devuelve ventas.
	 */
	public float getVentas() {
		return ventas;
	}
	/**
	 * Establece el total de ventas
	 * @param ventas El ventas a establecer.
	 */
	public void setVentas(float ventas) {
		this.ventas = ventas;
	}
	/**
	 * Devuelve el total de ventas exentas 
	 * @return Devuelve ventasExentas.
	 */
	public float getVentasExentas() {
		return ventasExentas;
	}
	/**
	 * Establece el total de ventas exentas
	 * @param ventasExentas El ventasExentas a establecer.
	 */
	public void setVentasExentas(float ventasExentas) {
		this.ventasExentas = ventasExentas;
	}
	/**
	 * Devuelve la fecha y hora de la impresora fiscal
	 * @return Devuelve fechaHoraIF.
	 */
	public Date getFechaHoraIF() {
		return fechaHoraIF;
	}
	/**
	 * Devuelve el ultimo tipo de comando ejecutado
	 * @return Devuelve ultTipoComandoEjecutado.
	 */
	public int getUltTipoComandoEjecutado() {
		return ultTipoComandoEjecutado;
	}
	/**
	 * Devuelve el ultimo identificador de secuencia ejecutado
	 * @return Devuelve ultValorSecuencia.
	 */
	public int getUltValorSecuencia() {
		return ultValorSecuencia;
	}
	/**
	 * Devuelve el estado de la impresora fiscal
	 * @return Devuelve status.
	 */
	public int getStatus() {
		return status;
	}
	
	/**
	 * Devuelve la cantidad total de Notas de Crédito de la Impresora
	 * @return
	 */
	public int getNumNCTotal() {
		return numNCTotal;
	}
	/**
	 * Establece el número de Notas de Crédito (Aplica para GD4)
	 * @param numNCTotal
	 */
	public void setNumNCTotal(int numNCTotal) {
		this.numNCTotal = numNCTotal;
	}
	
	
	
	/**
	 * Devuelte el numero de notas de credito desde ultimo reporte z
	 * @return
	 */
	public int getNumNCEmitidoDesdeZ() {
		return numNCEmitidoDesdeZ;
	}
	/**
	 * Establece el numero de notas de credito desde ultimo reporte z
	 * @param numNCEmitidoDesdeZ
	 */
	public void setNumNCEmitidoDesdeZ(int numNCEmitidoDesdeZ) {
		this.numNCEmitidoDesdeZ = numNCEmitidoDesdeZ;
	}

	public int getEstadoFiscal() {
		return estadoFiscal;
	}

	public void setEstadoFiscal(int estadoFiscal) {
		this.estadoFiscal = estadoFiscal;
	}
}
