/*
 * $Id: DefaultEntitySynchronizer.java,v 1.1.2.2 2005/04/06 12:25:17 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.extensiones.impl
 * Programa		: DefaultEntitySynchronizer.java
 * Creado por	: Programa8
 * Creado en 	: 04-abr-2005 11:39:38
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
 * $Log: DefaultEntitySynchronizer.java,v $
 * Revision 1.1.2.2  2005/04/06 12:25:17  programa8
 * Sincronización de afiliados a través de la extensión
 *
 * Revision 1.1.2.1  2005/04/05 13:43:01  programa8
 * Extensión de sincronización para uso personalizado de metodos de sincronización
 *
 * ===========================================================================
 */
package com.becoblohm.cr.extensiones.impl;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.extensiones.EntitySynchronizer;
import com.becoblohm.cr.mediadoresbd.BeansSincronizador;
import com.becoblohm.cr.mediadoresbd.EntidadBD;

/**
 * <pre>
 * Proyecto: CR 
 * Clase: DefaultEntitySynchronizer
 * </pre>
 * <p>
 * <a href="DefaultEntitySynchronizer.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - $Author: programa8 $
 * @version $Revision: 1.1.2.2 $ - $Date: 2005/04/06 12:25:17 $
 * @since 04-abr-2005
 * @
 */
public class DefaultEntitySynchronizer implements EntitySynchronizer {

	/**
	 * @since 04-abr-2005
	 * 
	 */
	public DefaultEntitySynchronizer() {
		super();
	}

	/* (sin Javadoc)
	 * @see com.becoblohm.cr.extensiones.EntitySynchronizer#synchronize(com.becoblohm.cr.mediadoresbd.EntidadBD, boolean, boolean)
	 * @param entidad
	 * @param origenLocal
	 * @param reemplazar
	 * @since 04-abr-2005
	 */
	public void synchronize(EntidadBD entidad, boolean origenLocal,
			boolean reemplazar, boolean esperar) {
		if (entidad.getNombre().equals("prodcodigoexterno")||
				entidad.getNombre().equals("afiliado")) {
			useSyncEntidadMaestra(entidad, origenLocal, reemplazar);
		} 
	}
	
	private void useSyncEntidadMaestra(EntidadBD entidad, boolean origenLocal,
			boolean reemplazar) {
		BeansSincronizador.syncEntidadMaestra(entidad, origenLocal, reemplazar);
		
	}
	
	private void useSyncEntidad(EntidadBD entidad, boolean origenLocal,
			boolean reemplazar) {
		BeansSincronizador.syncEntidad(entidad, origenLocal, reemplazar);
	}
	
	private void useSincronizarEntidad(EntidadBD entidad, boolean origenLocal) {
		try {
			BeansSincronizador.sincronizarEntidad(entidad, origenLocal);
		} catch (BaseDeDatosExcepcion e) {
			e.printStackTrace();
		}
	}
	

}
