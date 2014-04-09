/*
 * $Id: EPAMenuUtilitariosOtrasFunciones.java,v 1.1 2006/05/17 20:32:38 programa4 Exp $
 * ===========================================================================
 *
 * Proyecto		: EPAMenuUtilitariosOtrasFunciones
 * Paquete		: com.epa.ventas.cr.menuutilitariosotrasfunciones
 * Programa		: EPAMenuUtilitariosOtrasFunciones.java
 * Creado por	: programa4
 * Creado en 	: 16/05/2006 11:04:50 AM
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
 * $Log: EPAMenuUtilitariosOtrasFunciones.java,v $
 * Revision 1.1  2006/05/17 20:32:38  programa4
 * Version Inicial de Extension MenuUtilitarios
 * Contempla llamada a Punto Agil y a Crédito EPA
 *
 *
 * ===========================================================================
 */
/**
 * Clase EPAMenuUtilitariosOtrasFunciones
 *
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.1 $ - $Date: 2006/05/17 20:32:38 $
 * @since 16/05/2006
 */
package com.epa.ventas.cr.menuutilitariosotrasfunciones;

import javax.swing.JDialog;

import com.becoblohm.cr.extensiones.impl.DefaultMenuUtilitariosOtrasFunciones;
import com.becoblohm.cr.gui.JHighlightButton;
import com.becoblohm.cr.utiles.MensajesVentanas;

/**
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.1 $ - $Date: 2006/05/17 20:32:38 $
 * @since 16/05/2006
 */
public class EPAMenuUtilitariosOtrasFunciones extends
        DefaultMenuUtilitariosOtrasFunciones {

    public JHighlightButton instanciarBoton() {
        JHighlightButton boton = super.instanciarBoton();
        boton.setEnabled(true);
        return boton;
    }

    public void mostrarVentanaInicial(JDialog dialogUtilitarios) {
        super.mostrarVentanaInicial(dialogUtilitarios);

        WindowsMenuUtilitariosOtrasFunciones windowsMenuUtilitariosOtrasFunciones = new WindowsMenuUtilitariosOtrasFunciones();
        MensajesVentanas.centrarVentanaDialogo(windowsMenuUtilitariosOtrasFunciones);

        dialogUtilitarios.dispose();
    }

}
