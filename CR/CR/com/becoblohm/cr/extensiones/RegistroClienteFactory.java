/*
 * $Id: RegistroClienteFactory.java,v 1.2 2005/03/10 15:54:32 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerreter�a EPA C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.extensiones
 * Programa		: RegistroClienteFactory.java
 * Creado por	: Programa8
 * Creado en 	: 21-feb-2005 8:59:27
 * (C) Copyright 2005 SuperFerreter�a EPA C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Informaci�n de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisi�n	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: RegistroClienteFactory.java,v $
 * Revision 1.2  2005/03/10 15:54:32  programa8
 * CR al 10/03/2005. Merging desde branch STABLE
 *
 * Revision 1.1.2.2  2005/03/07 13:21:12  programa8
 * Integraci�n Versiones Estable e Inestable al 07/03/2005
 *
 * Revision 1.1.4.1  2005/02/28 18:39:12  programa8
 * Version Inestable al 28/02/2005
 *     *-Preparaci�n para trabajar sin administrador de ventanas.
 *     *-Reordenamiento de GUI
 *     *-Mejoras en scroll y pantallas de pagos
 *     *- Mantenimiento de estado en Cliente en Espera
 *     *- Avisos del sistema manejados por la aplicacion
 *     *- Desbloqueo de caja por otros usuarios reparado.
 *
 * Revision 1.1.2.1  2005/02/22 16:42:29  programa8
 * Ajustes en manejo de Clientes:
 * * Divisi�n del nombre del cliente en nombre y apellido, para mejorar las
 * 	capacidades de b�squeda y facilitar la integraci�n con los sistemas
 * 	de cliente frecuente
 * * Aplicaci�n de extension de caja, para la actualizacion de clientes
 * en el servidor directamente a la estructura EPA
 *
 * ===========================================================================
 */
package com.becoblohm.cr.extensiones;

/**
 * <pre>
 * Proyecto: CR 
 * Clase: RegistroClienteFactory
 * </pre>
 * <p>
 * <a href="RegistroClienteFactory.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - $Author: programa8 $
 * @version $Revision: 1.2 $ - $Date: 2005/03/10 15:54:32 $
 * @since 21-feb-2005
 * @
 */
public class RegistroClienteFactory extends CRExtensionFactory {
	 private static RegistroCliente instance = null;

	/* (sin Javadoc)
	 * @see com.becoblohm.cr.extensiones.CRExtensionFactory#getDefaultImplClass()
	 * @return
	 * @since 21-feb-2005
	 */
	public String getDefaultImplClass() {
		return "com.becoblohm.cr.extensiones.impl.DefaultRegistroCliente";
	}

	/* (sin Javadoc)
	 * @see com.becoblohm.cr.extensiones.CRExtensionFactory#getExtensionIntfName()
	 * @return
	 * @since 21-feb-2005
	 */
	public String getExtensionIntfName() {
		return "RegistroCliente";
	}
	
	public RegistroCliente getInstance() {
		return (RegistroCliente)getExtensionInstance();
	}

}
