/*
 * $Id: ReporteXZ.java,v 1.2.2.5 2005/05/18 15:12:11 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CRFiscalPrinterDriver 1.1
 * Paquete		: com.epa.crprinterdriver.data
 * Programa		: ReporteZ.java
 * Creado por	: Programa8
 * Creado en 	: 12-abr-2005 16:33:26
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
 * $Log: ReporteXZ.java,v $
 * Revision 1.2.2.5  2005/05/18 15:12:11  programa8
 * Limpieza de código
 *
 * Revision 1.2.2.4  2005/05/09 14:28:20  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.3  2005/05/09 14:18:57  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.2  2005/05/06 19:17:57  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.2.2.1  2005/05/06 19:12:49  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.2  2005/05/05 12:08:44  programa8
 * Versión 2.0.1 Final
 *
 * Revision 1.1.2.2  2005/04/13 21:57:05  programa8
 * Driver fiscal al 13/04/2005
 *
 * Revision 1.1.2.1  2005/04/12 20:59:13  programa8
 * Driver fiscal al 12/04/05
 *
 * ===========================================================================
 */
package com.epa.crprinterdriver.data;

import java.util.Date;

/**
 * <p>
 * Clase que representa la respuesta enviada por la impresora en la emisión de los
 * reportes X y Z
 * </p>
 * <p>
 * <a href="ReporteZ.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - Arístides Castillo Colmenárez - $Author: programa8 $
 * @version $Revision: 1.2.2.5 $ - $Date: 2005/05/18 15:12:11 $
 * @since 12-abr-2005
 * 
 */
public class ReporteXZ {

	private float ventasExentas;
	private float ventasBaseA;
	private float impuestoBaseA;
	private float descuentos;
	private float impuestoDescuentos;
	private float devoluciones;
	private float impuestoDevoluciones;
	private float ventasBaseB;
	private float impuestoBaseB;
	private float ventasBaseC;
	private float impuestoBaseC;
	private Date fechaZ;
	
	/**
	 * Constructor de la clase
	 * @since 12-abr-2005
	 * 
	 */
	public ReporteXZ() {
		super();
	}

	/**
	 * Devuelve monto total de descuentos
	 * @return Devuelve descuentos.
	 */
	public float getDescuentos() {
		return descuentos;
	}
	/**
	 * Establece monto total de descuentos
	 * @param descuentos El descuentos a establecer.
	 */
	public void setDescuentos(float descuentos) {
		this.descuentos = descuentos;
	}
	/**
	 * Devuelve monto total de devoluciones
	 * @return Devuelve devoluciones.
	 */
	public float getDevoluciones() {
		return devoluciones;
	}
	/**
	 * Establece monto total de devoluciones
	 * @param devoluciones El devoluciones a establecer.
	 */
	public void setDevoluciones(float devoluciones) {
		this.devoluciones = devoluciones;
	}
	/**
	 * Devuelve el total en impuestos para la tasa tipo A
	 * @return Devuelve impuestoBaseA.
	 */
	public float getImpuestoBaseA() {
		return impuestoBaseA;
	}
	/**
	 * Establece el total en impuestos para la tasa tipo A
	 * @param impuestoBaseA El impuestoBaseA a establecer.
	 */
	public void setImpuestoBaseA(float impuestoBaseA) {
		this.impuestoBaseA = impuestoBaseA;
	}
	/**
	 * Devuelve el total en impuestos para la tasa tipo B
	 * @return Devuelve impuestoBaseB.
	 */
	public float getImpuestoBaseB() {
		return impuestoBaseB;
	}
	/**
	 * Establece el total en impuestos para la tasa tipo B
	 * @param impuestoBaseB El impuestoBaseB a establecer.
	 */
	public void setImpuestoBaseB(float impuestoBaseB) {
		this.impuestoBaseB = impuestoBaseB;
	}
	/**
	 * Devuelve el total en impuestos para la tasa tipo C
	 * @return Devuelve impuestoBaseC.
	 */
	public float getImpuestoBaseC() {
		return impuestoBaseC;
	}
	/**
	 * 	Establece el total en impuestos para la tasa tipo C
	 * 	@param impuestoBaseC El impuestoBaseC a establecer.
	 */
	public void setImpuestoBaseC(float impuestoBaseC) {
		this.impuestoBaseC = impuestoBaseC;
	}
	/**
	 * Devuelve monto total de impuesto en descuentos
	 * @return Devuelve impuestoDescuentos.
	 */
	public float getImpuestoDescuentos() {
		return impuestoDescuentos;
	}
	/**
	 * Establece monto total de impuesto en descuentos
	 * @param impuestoDescuentos El impuestoDescuentos a establecer.
	 */
	public void setImpuestoDescuentos(float impuestoDescuentos) {
		this.impuestoDescuentos = impuestoDescuentos;
	}
	/**
	 * Devuelve monto total de impuesto en devoluciones
	 * @return Devuelve impuestoDevoluciones.
	 */
	public float getImpuestoDevoluciones() {
		return impuestoDevoluciones;
	}
	/**
	 * Establece monto total de impuesto en devoluciones
	 * @param impuestoDevoluciones El impuestoDevoluciones a establecer.
	 */
	public void setImpuestoDevoluciones(float impuestoDevoluciones) {
		this.impuestoDevoluciones = impuestoDevoluciones;
	}

	/**
	 * Devuelve el monto gravable para el impuesto tipo A
	 * @return Devuelve ventaBaseA.
	 */
	public float getVentasBaseA() {
		return ventasBaseA;
	}
	/**
	 * Establece el monto gravable para el impuesto tipo A
	 * @param ventaBaseA El ventaBaseA a establecer.
	 */
	public void setVentasBaseA(float ventaBaseA) {
		this.ventasBaseA = ventaBaseA;
	}
	/**
	 * Devuelve el monto gravable para el impuesto tipo B
	 * @return Devuelve ventaBaseB.
	 */
	public float getVentasBaseB() {
		return ventasBaseB;
	}
	/**
	 * Establece el monto gravable para el impuesto tipo B
	 * @param ventaBaseB El ventaBaseB a establecer.
	 */
	public void setVentasBaseB(float ventaBaseB) {
		this.ventasBaseB = ventaBaseB;
	}
	/**
	 * Devuelve el monto gravable para el impuesto tipo C
	 * @return Devuelve ventaBaseC.
	 */
	public float getVentasBaseC() {
		return ventasBaseC;
	}
	/**
	 * Establece el monto gravable para el impuesto tipo C
	 * @param ventaBaseC El ventaBaseC a establecer.
	 */
	public void setVentasBaseC(float ventaBaseC) {
		this.ventasBaseC = ventaBaseC;
	}
	/**
	 * Devuelve el monto de ventas exentas
	 * @return Devuelve ventaExenta.
	 */
	public float getVentasExentas() {
		return ventasExentas;
	}
	/**
	 * Establece el monto de ventas exentas
	 * @param ventaExenta El ventaExenta a establecer.
	 */
	public void setVentasExentas(float ventaExenta) {
		this.ventasExentas = ventaExenta;
	}
	/**
	 * Devuelve la fecha del reporte Z
	 * @return Devuelve fechaZ.
	 */
	public Date getFechaZ() {
		return fechaZ;
	}
	/**
	 * Establece la fecha del reporte Z
	 * @param fechaZ El fechaZ a establecer.
	 */
	public void setFechaZ(Date fechaZ) {
		this.fechaZ = fechaZ;
	}
}
