/*
 * $Id: BuscadorClienteServidor.java,v 1.1.2.1 2005/04/08 20:32:22 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.extensiones
 * Programa		: BuscadorClienteServidor.java
 * Creado por	: Programa8
 * Creado en 	: 07-abr-2005 10:20:23
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
 * $Log: BuscadorClienteServidor.java,v $
 * Revision 1.1.2.1  2005/04/08 20:32:22  programa8
 * Sincronización de afiliados al actualizarse en el servidor para
 * implantar búsqueda solo local de clientes
 *
 * ===========================================================================
 */
package com.becoblohm.cr.extensiones;

import java.sql.ResultSet;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;



/**
 * <pre>
 * Proyecto: CR 
 * Clase: BuscadorClienteServidor
 * </pre>
 * 
 * Interfaz de Extensión para buscar un cliente en el servidor
 * 
 * <p>
 * <a href="BuscadorClienteServidor.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - $Author: programa8 $
 * @version $Revision: 1.1.2.1 $ - $Date: 2005/04/08 20:32:22 $
 * @since 07-abr-2005
 * @
 */
public interface BuscadorClienteServidor extends CRExtension {
	public ResultSet buscarDatosCliente(String codAfiliado) throws BaseDeDatosExcepcion, ConexionExcepcion;
}
