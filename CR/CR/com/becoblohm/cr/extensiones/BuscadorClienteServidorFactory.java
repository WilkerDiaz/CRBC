/*
 * $Id: BuscadorClienteServidorFactory.java,v 1.1.2.1 2005/04/08 20:32:23 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerreter�a EPA C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.extensiones
 * Programa		: BuscadorClienteServidorFactory.java
 * Creado por	: Programa8
 * Creado en 	: 07-abr-2005 10:31:29
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
 * $Log: BuscadorClienteServidorFactory.java,v $
 * Revision 1.1.2.1  2005/04/08 20:32:23  programa8
 * Sincronizaci�n de afiliados al actualizarse en el servidor para
 * implantar b�squeda solo local de clientes
 *
 * ===========================================================================
 */
package com.becoblohm.cr.extensiones;

/**
 * <pre>
 * Proyecto: CR 
 * Clase: BuscadorClienteServidorFactory
 * </pre>
 * <p>
 * <a href="BuscadorClienteServidorFactory.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - $Author: programa8 $
 * @version $Revision: 1.1.2.1 $ - $Date: 2005/04/08 20:32:23 $
 * @since 07-abr-2005
 * @
 */
public class BuscadorClienteServidorFactory extends CRExtensionFactory {

	/**
	 * @since 07-abr-2005
	 * 
	 */
	public BuscadorClienteServidorFactory() {
		super();
	}

	/* (sin Javadoc)
	 * @see com.becoblohm.cr.extensiones.CRExtensionFactory#getDefaultImplClass()
	 * @return
	 * @since 07-abr-2005
	 */
	public String getDefaultImplClass() {
		return "com.becoblohm.cr.extensiones.impl.DefaultBuscadorClienteServidor";
	}

	/* (sin Javadoc)
	 * @see com.becoblohm.cr.extensiones.CRExtensionFactory#getExtensionIntfName()
	 * @return
	 * @since 07-abr-2005
	 */
	public String getExtensionIntfName() {
		return "BuscadorClienteServidor";
	}
	
	
	public BuscadorClienteServidor getInstance() {
		return (BuscadorClienteServidor)getExtensionInstance();
	}

}
