/*
 * $Id: DefaultBuscadorClienteServidor.java,v 1.1.2.1 2005/04/08 20:33:12 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.extensiones.impl
 * Programa		: DefaultBuscadorClienteServidor.java
 * Creado por	: Programa8
 * Creado en 	: 07-abr-2005 10:29:05
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
 * $Log: DefaultBuscadorClienteServidor.java,v $
 * Revision 1.1.2.1  2005/04/08 20:33:12  programa8
 * Sincronización de afiliados al actualizarse en el servidor para
 * implantar búsqueda solo local de clientes
 *
 * ===========================================================================
 */
package com.becoblohm.cr.extensiones.impl;

import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.extensiones.BuscadorClienteServidor;
import com.becoblohm.cr.mediadoresbd.MediadorBD;

/**
 * <pre>
 * Proyecto: CR 
 * Clase: DefaultBuscadorClienteServidor
 * </pre>
 * <p>
 * <a href="DefaultBuscadorClienteServidor.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - $Author: programa8 $
 * @version $Revision: 1.1.2.1 $ - $Date: 2005/04/08 20:33:12 $
 * @since 07-abr-2005
 * @
 */
public class DefaultBuscadorClienteServidor implements BuscadorClienteServidor {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(DefaultBuscadorClienteServidor.class);

	/**
	 * @since 07-abr-2005
	 * 
	 */
	public DefaultBuscadorClienteServidor() {
		super();
	}
	
	
	
	/* (sin Javadoc)
	 * @see com.becoblohm.cr.extensiones.BuscadorClienteServidor#buscarDatosCliente(java.lang.String)
	 * @param afiliado
	 * @return
	 * @since 07-abr-2005
	 */
	public ResultSet buscarDatosCliente(String afiliado) throws BaseDeDatosExcepcion, ConexionExcepcion{
		return MediadorBD.buscarCliente(afiliado, false);
	}
}
