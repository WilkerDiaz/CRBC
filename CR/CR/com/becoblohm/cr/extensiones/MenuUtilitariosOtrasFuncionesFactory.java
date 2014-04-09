/*
 * $Id: MenuUtilitariosOtrasFuncionesFactory.java,v 1.2 2006/07/10 19:37:59 programa8 Exp $
 * ===========================================================================
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.extensiones
 * Programa		: MenuUtilitariosOtrasFuncionesFactory.java
 * Creado por	: programa4
 * Creado en 	: 16/05/2006 10:45:58 AM
 * (C) Copyright 2006 Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisión	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: MenuUtilitariosOtrasFuncionesFactory.java,v $
 * Revision 1.2  2006/07/10 19:37:59  programa8
 * Manejo de pagos convertido en extension
 * Convertida en extension menu de utilitarios (F8 - F9)
 *
 * Revision 1.1.2.1  2006/05/22 14:27:04  programa4
 * Menu de Otras Funciones en Utilitarios, convertido en extension
 *
 *
 * ===========================================================================
 */
/**
 * Clase MenuUtilitariosOtrasFuncionesFactory
 *
 * @author programa4 - $Author: programa8 $
 * @version $Revision: 1.2 $ - $Date: 2006/07/10 19:37:59 $
 * @since 16/05/2006
 */
package com.becoblohm.cr.extensiones;

/**
 * @author programa4 - $Author: programa8 $
 * @version $Revision: 1.2 $ - $Date: 2006/07/10 19:37:59 $
 * @since 16/05/2006
 */
public class MenuUtilitariosOtrasFuncionesFactory extends CRExtensionFactory {

    /*
     * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.CRExtensionFactory#getDefaultImplClass()
     */
    public String getDefaultImplClass() {
        return "com.becoblohm.cr.extensiones.impl.DefaultMenuUtilitariosOtrasFunciones";
    }

    /*
     * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.CRExtensionFactory#getExtensionIntfName()
     */
    public String getExtensionIntfName() {
        return "MenuUtilitariosOtrasFunciones";
    }

    public MenuUtilitariosOtrasFunciones getInstance() {
        return (MenuUtilitariosOtrasFunciones) getExtensionInstance();
    }

}
