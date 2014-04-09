/*
 * $Id: DataSubtotal.java,v 1.2.2.4 2005/05/09 14:28:20 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CRFiscalPrinterDriver 1.1
 * Paquete		: com.epa.crprinterdriver.data
 * Programa		: DataSubtotal.java
 * Creado por	: Programa8
 * Creado en 	: 13-abr-2005 9:19:19
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
 * $Log: DataSubtotal.java,v $
 * Revision 1.2.2.4  2005/05/09 14:28:20  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.3  2005/05/09 14:18:58  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.2  2005/05/06 19:17:58  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.2.2.1  2005/05/06 19:12:49  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.2  2005/05/05 12:08:45  programa8
 * Versión 2.0.1 Final
 *
 * Revision 1.1.2.1  2005/04/13 21:57:06  programa8
 * Driver fiscal al 13/04/2005
 *
 * ===========================================================================
 */
package com.epa.crprinterdriver.data;

/**
 * <p>
 * Clase que representa la respuesta del comando Subtotal de la impresora fiscal
 * </p>
 * <p>
 * <a href="DataSubtotal.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - Arístides Castillo Colmenárez - $Author: programa8 $
 * @version $Revision: 1.2.2.4 $ - $Date: 2005/05/09 14:28:20 $
 * @since 13-abr-2005
 * 
 */
public class DataSubtotal {
	
	private float ventaExenta;
	private float baseA;
	private float ventaBaseA;
	private float impuestoBaseA;
	private float baseB;
	private float ventaBaseB;
	private float impuestoBaseB;
	private float baseC;
	private float ventaBaseC;
	private float impuestoBaseC;

	/**
	 * Constructor de la clase
	 * @since 13-abr-2005
	 * 
	 */
	public DataSubtotal() {
		super();
	}

	/**
	 * Devuelve la tasa de impuesto tipo A
	 * @return Devuelve baseA.
	 */
	public float getBaseA() {
		return baseA;
	}
	/**
	 * Establece la tasa de impuesto tipo A
	 * @param baseA El baseA a establecer.
	 */
	public void setBaseA(float baseA) {
		this.baseA = baseA;
	}
	/**
	 * Devuelve la tasa de impuesto tipo B
	 * @return Devuelve baseB.
	 */
	public float getBaseB() {
		return baseB;
	}
	/**
	 * Establece la tasa de impuesto tipo B
	 * @param baseB El baseB a establecer.
	 */
	public void setBaseB(float baseB) {
		this.baseB = baseB;
	}
	/**
	 * Devuelve la tasa de impuesto tipo C
	 * @return Devuelve baseC.
	 */
	public float getBaseC() {
		return baseC;
	}
	/**
	 * Establece la tasa de impuesto tipo C
	 * @param baseC El baseC a establecer.
	 */
	public void setBaseC(float baseC) {
		this.baseC = baseC;
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
	 * Devuelve el monto gravable para el impuesto tipo A
	 * @return Devuelve ventaBaseA.
	 */
	public float getVentaBaseA() {
		return ventaBaseA;
	}
	/**
	 * Establece el monto gravable para el impuesto tipo A
	 * @param ventaBaseA El ventaBaseA a establecer.
	 */
	public void setVentaBaseA(float ventaBaseA) {
		this.ventaBaseA = ventaBaseA;
	}
	/**
	 * Devuelve el monto gravable para el impuesto tipo B
	 * @return Devuelve ventaBaseB.
	 */
	public float getVentaBaseB() {
		return ventaBaseB;
	}
	/**
	 * Establece el monto gravable para el impuesto tipo B
	 * @param ventaBaseB El ventaBaseB a establecer.
	 */
	public void setVentaBaseB(float ventaBaseB) {
		this.ventaBaseB = ventaBaseB;
	}
	/**
	 * Devuelve el monto gravable para el impuesto tipo C
	 * @return Devuelve ventaBaseC.
	 */
	public float getVentaBaseC() {
		return ventaBaseC;
	}
	/**
	 * Establece el monto gravable para el impuesto tipo C
	 * @param ventaBaseC El ventaBaseC a establecer.
	 */
	public void setVentaBaseC(float ventaBaseC) {
		this.ventaBaseC = ventaBaseC;
	}
	/**
	 * Devuelve el monto de ventas exentas
	 * @return Devuelve ventaExenta.
	 */
	public float getVentaExenta() {
		return ventaExenta;
	}
	/**
	 * Establece el monto de ventas exentas
	 * @param ventaExenta El ventaExenta a establecer.
	 */
	public void setVentaExenta(float ventaExenta) {
		this.ventaExenta = ventaExenta;
	}
}
