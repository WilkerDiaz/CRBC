/*
 * $Id: CongeladorFacturaFactory.java,v 1.2 2005/03/10 15:54:32 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.extensiones
 * Programa		: CongeladorFacturaFactory.java
 * Creado por	: Programa8
 * Creado en 	: 26-ene-2005 12:56:43
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
 * $Log: CongeladorFacturaFactory.java,v $
 * Revision 1.2  2005/03/10 15:54:32  programa8
 * CR al 10/03/2005. Merging desde branch STABLE
 *
 * Revision 1.1.2.5  2005/03/07 13:21:11  programa8
 * Integración Versiones Estable e Inestable al 07/03/2005
 *
 * Revision 1.1.4.1  2005/02/28 18:39:12  programa8
 * Version Inestable al 28/02/2005
 *     *-Preparación para trabajar sin administrador de ventanas.
 *     *-Reordenamiento de GUI
 *     *-Mejoras en scroll y pantallas de pagos
 *     *- Mantenimiento de estado en Cliente en Espera
 *     *- Avisos del sistema manejados por la aplicacion
 *     *- Desbloqueo de caja por otros usuarios reparado.
 *
 * Revision 1.1.2.4  2005/02/22 18:18:51  programa4
 * *** empty log message ***
 *
 * Revision 1.1.2.3  2005/02/02 20:23:12  acastillo
 * Limpieza de código. 0 trailing todo's
 *
 * Revision 1.1.2.2  2005/02/02 20:19:53  acastillo
 * Limpieza de código. 0 trailing todo's
 *
 * Revision 1.1.2.1  2005/01/28 19:56:36  acastillo
 * Caja Registradora EPA3 - CR RC3
 *
 * ===========================================================================
 */
package com.becoblohm.cr.extensiones;

/**
 * <pre>
 * Proyecto: CR 
 * Clase: CongeladorFacturaFactory
 * </pre>
 * <p>
 * <a href="CongeladorFacturaFactory.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - $Author: programa8 $
 * @version $Revision: 1.2 $ - $Date: 2005/03/10 15:54:32 $
 * @since 26-ene-2005
 * @
 */
public class CongeladorFacturaFactory extends CRExtensionFactory {

	/**
	 * @since 26-ene-2005
	 * 
	 */
	public CongeladorFacturaFactory() {
		super();
	}

	/* (sin Javadoc)
	 * @see com.becoblohm.cr.extensiones.CRExtensionFactory#getDefaultImplClass()
	 * @return
	 * @since 26-ene-2005
	 */
	public String getDefaultImplClass() {
		return "com.becoblohm.cr.extensiones.impl.DefaultCongeladorFactura";
	}

	/* (sin Javadoc)
	 * @see com.becoblohm.cr.extensiones.CRExtensionFactory#getExtensionIntfName()
	 * @return
	 * @since 26-ene-2005
	 */
	public String getExtensionIntfName() {
		return "CongeladorFactura";
	}
	
	public CongeladorFactura getInstance() {
		return (CongeladorFactura)getExtensionInstance();
	}

}
