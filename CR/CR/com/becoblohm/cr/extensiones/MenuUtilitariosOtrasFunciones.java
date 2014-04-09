/*
 * $Id: MenuUtilitariosOtrasFunciones.java,v 1.2 2006/07/10 19:38:04 programa8 Exp $
 * ===========================================================================
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.extensiones
 * Programa		: MenuUtilitariosOtrasFunciones.java
 * Creado por	: programa4
 * Creado en 	: 16/05/2006 10:43:31 AM
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
 * $Log: MenuUtilitariosOtrasFunciones.java,v $
 * Revision 1.2  2006/07/10 19:38:04  programa8
 * Manejo de pagos convertido en extension
 * Convertida en extension menu de utilitarios (F8 - F9)
 *
 * Revision 1.1.2.1  2006/05/22 14:27:02  programa4
 * Menu de Otras Funciones en Utilitarios, convertido en extension
 *
 *
 * ===========================================================================
 */
/**
 * Clase MenuUtilitariosOtrasFunciones
 *
 * @author programa4 - $Author: programa8 $
 * @version $Revision: 1.2 $ - $Date: 2006/07/10 19:38:04 $
 * @since 16/05/2006
 */
package com.becoblohm.cr.extensiones;

import javax.swing.JDialog;

import com.becoblohm.cr.gui.JHighlightButton;

/**
 * @author programa4 - $Author: programa8 $
 * @version $Revision: 1.2 $ - $Date: 2006/07/10 19:38:04 $
 * @since 16/05/2006
 */
public interface MenuUtilitariosOtrasFunciones extends CRExtension {

    /**
     * Instancia el boton que se usara para las otras opciones.
     *
     * @return JHighlightButton
     */
    public JHighlightButton instanciarBoton();

    /**
     * Levanta el menu de utilitarios.
     *
     * @param dialogUtilitarios
     *            void
     */
    public void mostrarVentanaInicial(JDialog dialogUtilitarios);
}
