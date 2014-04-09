/*
 * $Id: BuscadorClienteFactory.java,v 1.2 2005/03/10 15:54:31 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.extensiones
 * Programa		: BuscadorClienteFactory.java
 * Creado por	: Programa8
 * Creado en 	: 23-dic-2004 9:03:01
 * (C) Copyright 2004 SuperFerretería EPA C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisión	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: BuscadorClienteFactory.java,v $
 * Revision 1.2  2005/03/10 15:54:31  programa8
 * CR al 10/03/2005. Merging desde branch STABLE
 *
 * Revision 1.1.6.3  2005/03/07 13:21:13  programa8
 * Integración Versiones Estable e Inestable al 07/03/2005
 *
 * Revision 1.1.2.2  2005/02/28 18:13:10  programa8
 * Version Inestable al 28/02/2005
 *     *-Preparación para trabajar sin administrador de ventanas.
 *     *-Reordenamiento de GUI
 *     *-Mejoras en scroll y pantallas de pagos
 *     *- Mantenimiento de estado en Cliente en Espera
 *     *- Avisos del sistema manejados por la aplicacion
 *     *- Desbloqueo de caja por otros usuarios reparado.
 *
 * Revision 1.1.6.2  2005/02/22 18:18:52  programa4
 * *** empty log message ***
 *
 * Revision 1.1.6.1  2005/01/04 18:23:01  acastillo
 * Integracion CR EPA3 - Costa Rica
 *
 * Revision 1.1.4.1  2005/01/04 16:07:16  acastillo
 * Integracion CR EPA3 - Costa Rica
 *
 * Revision 1.1.2.1  2005/01/03 20:45:42  acastillo
 * Arquitectura de extensiones
 *
 * ===========================================================================
 */
package com.becoblohm.cr.extensiones;

/**
 * <pre>
 * Proyecto: CR 
 * Clase: BuscadorClienteFactory
 * </pre>
 * <p>
 * <a href="BuscadorClienteFactory.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - $Author: programa8 $
 * @version $Revision: 1.2 $ - $Date: 2005/03/10 15:54:31 $
 * @since 23-dic-2004
 * @
 */
public class BuscadorClienteFactory extends CRExtensionFactory {

	/**
	 * @since 23-dic-2004
	 * 
	 */
	public BuscadorClienteFactory() {
		super();
	}

	/* (sin Javadoc)
	 * @see com.becoblohm.cr.extensiones.CRExtensionFactory#getDefaultImplClass()
	 * @return
	 * @since 23-dic-2004
	 */
	public String getDefaultImplClass() {
		return "com.becoblohm.cr.extensiones.impl.DefaultBuscadorCliente";
	}

	/* (sin Javadoc)
	 * @see com.becoblohm.cr.extensiones.CRExtensionFactory#getExtensionIntfName()
	 * @return
	 * @since 23-dic-2004
	 */
	public String getExtensionIntfName() {
		return "BuscadorCliente";
	}
	
	public BuscadorCliente getBuscadorInstance() {
		return (BuscadorCliente)getExtensionInstance();
	}

}
