/*
 * $Id: ReporteMemoriaFiscal.java,v 1.2.2.4 2005/05/09 14:28:19 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CRFiscalPrinterDriver 1.1
 * Paquete		: com.epa.crprinterdriver.data
 * Programa		: ReporteMemoriaFiscal.java
 * Creado por	: Programa8
 * Creado en 	: 12-abr-2005 16:33:11
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
 * $Log: ReporteMemoriaFiscal.java,v $
 * Revision 1.2.2.4  2005/05/09 14:28:19  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.3  2005/05/09 14:18:57  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.2  2005/05/06 19:17:55  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.2.2.1  2005/05/06 19:12:48  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.2  2005/05/05 12:08:44  programa8
 * Versión 2.0.1 Final
 *
 * Revision 1.1.2.2  2005/04/13 21:57:06  programa8
 * Driver fiscal al 13/04/2005
 *
 * Revision 1.1.2.1  2005/04/12 20:59:15  programa8
 * Driver fiscal al 12/04/05
 *
 * ===========================================================================
 */
package com.epa.crprinterdriver.data;

/**
 * 
 * <p>
 * Representa el resultado digital de la emisión del reporte de la memoria 
 * de la impresora fiscal
 * </p>
 * <p>
 * <a href="ReporteMemoriaFiscal.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - Arístides Castillo Colménarez -  $Author: programa8 $
 * @version $Revision: 1.2.2.4 $ - $Date: 2005/05/09 14:28:19 $
 * @since 12-abr-2005
 * 
 */
public class ReporteMemoriaFiscal {

	private int primerZPeriodo;
	private int ultimoZPeriodo;

	/**
	 * Constructor de la clase
	 * @since 12-abr-2005
	 * @param primer Primer reporte Z del periodo
	 * @param ultimo Ulitmo reporte Z del periodo
	 */
	public ReporteMemoriaFiscal(int primer, int ultimo) {
		super();
		primerZPeriodo = primer;
		ultimoZPeriodo = ultimo;
	}

	
	/**
	 * Devuelve el número del primer reporte Z del periodo
	 * @return Devuelve primerZPeriodo.
	 */
	public int getPrimerZPeriodo() {
		return primerZPeriodo;
	}
	/**
	 * Devuelve el número del último reporte Z del período
	 * @return Devuelve ultimoZPeriodo.
	 */
	public int getUltimoZPeriodo() {
		return ultimoZPeriodo;
	}

}
