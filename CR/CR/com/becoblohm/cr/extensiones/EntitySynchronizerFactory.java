/*
 * $Id: EntitySynchronizerFactory.java,v 1.1.2.1 2005/04/05 13:42:49 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.extensiones
 * Programa		: EntitySynchronizerFactory.java
 * Creado por	: Programa8
 * Creado en 	: 04-abr-2005 11:35:48
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
 * $Log: EntitySynchronizerFactory.java,v $
 * Revision 1.1.2.1  2005/04/05 13:42:49  programa8
 * Extensión de sincronización para uso personalizado de metodos de sincronización
 *
 * ===========================================================================
 */
package com.becoblohm.cr.extensiones;

/**
 * <pre>
 * Proyecto: CR 
 * Clase: EntitySynchronizerFactory
 * </pre>
 * <p>
 * <a href="EntitySynchronizerFactory.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - $Author: programa8 $
 * @version $Revision: 1.1.2.1 $ - $Date: 2005/04/05 13:42:49 $
 * @since 04-abr-2005
 * @
 */
public class EntitySynchronizerFactory extends CRExtensionFactory {

	/**
	 * @since 04-abr-2005
	 * 
	 */
	public EntitySynchronizerFactory() {
		super();
	}
	
	/* (sin Javadoc)
	 * @see com.becoblohm.cr.extensiones.CRExtensionFactory#getDefaultImplClass()
	 * @return
	 * @since 04-abr-2005
	 */
	public String getDefaultImplClass() {
		return "com.becoblohm.cr.extensiones.impl.DefaultEntitySynchronizer";
	}

	/* (sin Javadoc)
	 * @see com.becoblohm.cr.extensiones.CRExtensionFactory#getExtensionIntfName()
	 * @return
	 * @since 04-abr-2005
	 */
	public String getExtensionIntfName() {
		return "EntitySynchronizer";
	}
	
	public EntitySynchronizer getInstance() {
		return (EntitySynchronizer)getExtensionInstance();
	}

}
