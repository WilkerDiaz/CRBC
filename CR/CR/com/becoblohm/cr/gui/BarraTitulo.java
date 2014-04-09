/*
 * $Id: BarraTitulo.java,v 1.1 2005/08/25 19:13:41 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.gui
 * Programa		: BarraTitulo.java
 * Creado por	: Programa8
 * Creado en 	: 25/08/2005 02:09:11 PM
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
 * $Log: BarraTitulo.java,v $
 * Revision 1.1  2005/08/25 19:13:41  programa8
 * Ajuste en interfaz gráfica.
 * *-	Resuelto bug de pérdida de foco
 * *-	Adaptacion de interfaz gráfica para manejo sin Window Manager
 *
 * ===========================================================================
 */
package com.becoblohm.cr.gui;

import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * <pre>
 * Proyecto: CR 
 * Clase: BarraTitulo
 * </pre>
 * <p>
 * <a href="BarraTitulo.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - $Author: programa8 $
 * @version $Revision: 1.1 $ - $Date: 2005/08/25 19:13:41 $
 * @since 25/08/2005
 * @
 */
public class BarraTitulo extends JPanel {

	private JLabel etiqueta = null;
	private JDialog parent = null;
	/**
	 * This is the default constructor
	 */
	public BarraTitulo(JDialog window) {
		super();
		this.parent = window;
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
	    etiqueta = new JLabel();
		FlowLayout flowLayout1 = new FlowLayout();
		setLayout(flowLayout1);
		setBackground(new java.awt.Color(69,107,127));
		setPreferredSize(new java.awt.Dimension(810,15));
		setForeground(java.awt.Color.white);
		etiqueta.setText(parent.getTitle());
		etiqueta.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
		etiqueta.setForeground(java.awt.Color.white);
		flowLayout1.setVgap(0);
		add(etiqueta, null);
	}	
    /* (sin Javadoc)
     * @see java.awt.Component#paint(java.awt.Graphics)
     * @param g
     * @since 25/08/2005
     */
    public void paint(Graphics g) {
        this.etiqueta.setText(parent.getTitle());
        super.paint(g);
    }
}

/*
 
 	private javax.swing.JPanel panelTitulo = null;
	jContentPane.add(getPanelTitulo(), null);
	
	public javax.swing.JPanel getPanelTitulo() {
	    if (panelTitulo == null) {
	        panelTitulo = new BarraTitulo(this);
	    }
	    return panelTitulo;
	}

 
 */
