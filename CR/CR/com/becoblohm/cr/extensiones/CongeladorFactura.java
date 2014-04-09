/*
 * $Id: CongeladorFactura.java,v 1.2 2005/03/10 15:54:33 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.extensiones
 * Programa		: CongeladorFactura.java
 * Creado por	: Programa8
 * Creado en 	: 26-ene-2005 12:45:13
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
 * $Log: CongeladorFactura.java,v $
 * Revision 1.2  2005/03/10 15:54:33  programa8
 * CR al 10/03/2005. Merging desde branch STABLE
 *
 * Revision 1.1.2.4  2005/03/07 13:21:12  programa8
 * Integración Versiones Estable e Inestable al 07/03/2005
 *
 * Revision 1.1.4.1  2005/02/28 18:39:13  programa8
 * Version Inestable al 28/02/2005
 *     *-Preparación para trabajar sin administrador de ventanas.
 *     *-Reordenamiento de GUI
 *     *-Mejoras en scroll y pantallas de pagos
 *     *- Mantenimiento de estado en Cliente en Espera
 *     *- Avisos del sistema manejados por la aplicacion
 *     *- Desbloqueo de caja por otros usuarios reparado.
 *
 * Revision 1.1.2.3  2005/02/22 18:18:52  programa4
 * *** empty log message ***
 *
 * Revision 1.1.2.2  2005/02/10 14:49:35  acastillo
 * Ajuste conexiones, resultsets & statements
 *
 * Revision 1.1.2.1  2005/01/28 19:56:36  acastillo
 * Caja Registradora EPA3 - CR RC3
 *
 * ===========================================================================
 */
package com.becoblohm.cr.extensiones;

import java.util.Date;
import java.util.Vector;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.manejarventa.Venta;

/**
 * <pre>
 * Proyecto: CR 
 * Clase: CongeladorFactura
 * </pre>
 * <p>
 * <a href="CongeladorFactura.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - $Author: programa8 $
 * @version $Revision: 1.2 $ - $Date: 2005/03/10 15:54:33 $
 * @since 26-ene-2005
 * @
 */
public interface CongeladorFactura extends CRExtension {
	public void colocarEnEspera (Venta venta, String identificacion) throws BaseDeDatosExcepcion, ConexionExcepcion;
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Vector<?>> recuperarDeEspera (String identificacion) throws BaseDeDatosExcepcion, ConexionExcepcion;
	public void borrarFacturaEnEspera(String identificacion, int cajaInicia, int tienda, int regCajaInicia, Date fecha) throws BaseDeDatosExcepcion, ConexionExcepcion;

}
