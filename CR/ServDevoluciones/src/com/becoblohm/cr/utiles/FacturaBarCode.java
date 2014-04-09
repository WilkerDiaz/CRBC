/*
 * $Id: FacturaBarCode.java,v 1.2 2005/03/10 15:54:44 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.utiles
 * Programa		: FacturaBarCode.java
 * Creado por	: Programa8
 * Creado en 	: 31-ene-2005 14:22:47
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
 * $Log: FacturaBarCode.java,v $
 * Revision 1.2  2005/03/10 15:54:44  programa8
 * CR al 10/03/2005. Merging desde branch STABLE
 *
 * Revision 1.1.2.3  2005/03/07 12:53:12  programa8
 * Integración Versiones Estable e Inestable al 07/03/2005
 *
 * Revision 1.1.4.1  2005/02/28 18:39:15  programa8
 * Version Inestable al 28/02/2005
 *     *-Preparación para trabajar sin administrador de ventanas.
 *     *-Reordenamiento de GUI
 *     *-Mejoras en scroll y pantallas de pagos
 *     *- Mantenimiento de estado en Cliente en Espera
 *     *- Avisos del sistema manejados por la aplicacion
 *     *- Desbloqueo de caja por otros usuarios reparado.
 *
 * Revision 1.1.2.2  2005/02/22 18:26:04  programa4
 * *** empty log message ***
 *
 * Revision 1.1.2.1  2005/02/01 20:26:51  acastillo
 * Ajuste de Menu Servicios
 *
 * ===========================================================================
 */
package com.becoblohm.cr.utiles;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * <pre>
 * Proyecto: CR 
 * Clase: FacturaBarCode
 * </pre>
 * <p>
 * <a href="FacturaBarCode.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - $Author: programa8 $
 * @version $Revision: 1.2 $ - $Date: 2005/03/10 15:54:44 $
 * @since 31-ene-2005
 * @
 */
public class FacturaBarCode {

	/**
	 * @since 31-ene-2005
	 * 
	 */
	private String lecturaCodigo;
	
	public FacturaBarCode(String lectura) {
		super();
		lecturaCodigo = lectura;
	}
	
	public int getNumTienda() {
		return Integer.parseInt(lecturaCodigo.substring(0,3));
	}
	public String getFechaSQL() {
		String fecha = lecturaCodigo.substring(3,11);
		return fecha.substring(0,4) + "-" + fecha.substring(4,6) + "-" + fecha.substring(6);
	}
	
	public Date getFecha() {
		Date d = null;
		DateFormat df = DateFormat.getDateInstance();
		try {
			d = df.parse(getFechaSQL());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}
	
	public int getNumCaja() {
		return Integer.parseInt(lecturaCodigo.substring(11,14));
	}
	
	public int getNumTransaccion() {
		return Integer.parseInt(lecturaCodigo.substring(14));
	}

}
