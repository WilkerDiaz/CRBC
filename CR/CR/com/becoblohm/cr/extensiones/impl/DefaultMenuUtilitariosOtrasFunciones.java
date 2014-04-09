/*
 * $Id: DefaultMenuUtilitariosOtrasFunciones.java,v 1.2 2006/07/10 19:38:19 programa8 Exp $
 * ===========================================================================
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.extensiones.impl
 * Programa		: DefaultMenuUtilitariosOtrasFunciones.java
 * Creado por	: programa4
 * Creado en 	: 16/05/2006 10:50:00 AM
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
 * $Log: DefaultMenuUtilitariosOtrasFunciones.java,v $
 * Revision 1.2  2006/07/10 19:38:19  programa8
 * Manejo de pagos convertido en extension
 * Convertida en extension menu de utilitarios (F8 - F9)
 *
 * Revision 1.1.2.1  2006/05/22 14:27:08  programa4
 * Menu de Otras Funciones en Utilitarios, convertido en extension
 *
 *
 * ===========================================================================
 */
/**
 * Clase DefaultMenuUtilitariosOtrasFunciones
 *
 * @author programa4 - $Author: programa8 $
 * @version $Revision: 1.2 $ - $Date: 2006/07/10 19:38:19 $
 * @since 16/05/2006
 */
package com.becoblohm.cr.extensiones.impl;

import javax.swing.JDialog;

import com.becoblohm.cr.extensiones.MenuUtilitariosOtrasFunciones;
import com.becoblohm.cr.gui.JHighlightButton;

/**
 * @author programa4 - $Author: programa8 $
 * @version $Revision: 1.2 $ - $Date: 2006/07/10 19:38:19 $
 * @since 16/05/2006
 */
public class DefaultMenuUtilitariosOtrasFunciones implements
        MenuUtilitariosOtrasFunciones {

    protected JHighlightButton jButton = new JHighlightButton();

    /*
     * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.MenuUtilitariosOtrasFunciones#mostrarVentanaInicial()
     */
    public void mostrarVentanaInicial(JDialog dialogUtilitarios) {
        // NO HACE NADA
    }

    /*
     * (non-Javadoc)
     *
     * @see com.becoblohm.cr.extensiones.MenuUtilitariosOtrasFunciones#instanciarBoton()
     */
    public JHighlightButton instanciarBoton() {
        this.jButton.setBackground(new java.awt.Color(226, 226, 222));
        this.jButton.setActionCommand("otrasF");
        this.jButton.setText("F9 - Otras funciones");
        this.jButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        this.jButton.setEnabled(false);
        return this.jButton;
    }

}
