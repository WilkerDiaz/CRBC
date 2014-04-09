/*
 * $Id: EntitySynchronizer.java,v 1.1.2.1 2005/04/05 13:42:50 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerreter�a EPA C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.extensiones
 * Programa		: EntitySynchronizer.java
 * Creado por	: Programa8
 * Creado en 	: 04-abr-2005 7:55:38
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
 * $Log: EntitySynchronizer.java,v $
 * Revision 1.1.2.1  2005/04/05 13:42:50  programa8
 * Extensi�n de sincronizaci�n para uso personalizado de metodos de sincronizaci�n
 *
 * ===========================================================================
 */
package com.becoblohm.cr.extensiones;

import com.becoblohm.cr.mediadoresbd.EntidadBD;

/**
 * <pre>
 * Proyecto: CR 
 * Clase: EntitySynchronizer
 * </pre>
 * 
 * Interfaz de extensi�n para adaptaci�n de sincronizador externo. Esta extensi�n tiene
 * car�cter de prueba para determinar si es necesario y conveniente una modificacion total
 * del sincronizador de la CR
 * 
 * <p>
 * <a href="EntitySynchronizer.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - Ar�stides Castillo Colmen�rez - $Author: programa8 $
 * @version $Revision: 1.1.2.1 $ - $Date: 2005/04/05 13:42:50 $
 * @since 04-abr-2005
 * @
 */
public interface EntitySynchronizer extends CRExtension {
	/**
	 * Sincroniza una entidad de base de datos entre caja y servidor.
	 * @param entidad Descripci�n de la entidad a sincronizar
	 * @param origenLocal Indica si el origen de la sincronizacion es o no la caja registradora.
	 * @param reemplazar Indica si debe o no reemplazar los registros existentes
	 * @param esperar Indica si se debe esperar a finalizar el proceso, o debe realizarse en paralelo
	 */
	public void synchronize(EntidadBD entidad, boolean origenLocal, 
			boolean reemplazar, boolean esperar);
}
