/*
 * $Id: JHighlightComboBox.java,v 1.2 2005/03/10 15:54:42 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.gui
 * Programa		: JHighlightComboBox.java
 * Creado por	: Programa8
 * Creado en 	: 23-feb-2005 14:15:02
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
 * $Log: JHighlightComboBox.java,v $
 * Revision 1.2  2005/03/10 15:54:42  programa8
 * CR al 10/03/2005. Merging desde branch STABLE
 *
 * Revision 1.1.4.1  2005/03/07 13:28:51  programa8
 * Ajustes de detalles en GUI
 *
 * Revision 1.1.2.1  2005/02/28 18:39:13  programa8
 * Version Inestable al 28/02/2005
 *     *-Preparación para trabajar sin administrador de ventanas.
 *     *-Reordenamiento de GUI
 *     *-Mejoras en scroll y pantallas de pagos
 *     *- Mantenimiento de estado en Cliente en Espera
 *     *- Avisos del sistema manejados por la aplicacion
 *     *- Desbloqueo de caja por otros usuarios reparado.
 *
 * ===========================================================================
 */
package com.becoblohm.cr.gui;

import java.awt.Color;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

/**
 * <pre>
 * Proyecto: CR 
 * Clase: JHighlightComboBox
 * </pre>
 * <p>
 * <a href="JHighlightComboBox.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - $Author: programa8 $
 * @version $Revision: 1.2 $ - $Date: 2005/03/10 15:54:42 $
 * @since 23-feb-2005
 * @
 */
public class JHighlightComboBox extends JComboBox {

	protected Color normColor = null;
	protected Color focusColor = new Color(180, 180, 255);
	private JButton ancestor = null;
	
	/**
	 * @since 23-feb-2005
	 * 
	 */
	public JHighlightComboBox() {
		super();
		initialize();
		normColor = getBackground();
	}

	/**
	 * @since 23-feb-2005
	 * @param items
	 */
	public JHighlightComboBox(Object[] items) {
		super(items);
	}

	/**
	 * @since 23-feb-2005
	 * @param items
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public JHighlightComboBox(Vector<?> items) {
		super(items);
	}

	/**
	 * @since 23-feb-2005
	 * @param aModel
	 */
	public JHighlightComboBox(ComboBoxModel aModel) {
		super(aModel);
	}
	

	/**
	 * This method initializes 
	 * 
	 */
	
	public JHighlightComboBox(Color fc) {
		this();
		focusColor = fc;
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.addFocusListener(new java.awt.event.FocusAdapter() {   
        	public void focusLost(java.awt.event.FocusEvent e) {
        		setColorFocus(normColor);
        	} 
        	public void focusGained(java.awt.event.FocusEvent e) {
        		setColorFocus(focusColor);
        	}
        });
	}
	
	public void setBackground(Color c){
		super.setBackground(c);
		normColor = c;
	}
	
	public void setColorFocus(Color c) {
		super.setBackground(c);
	}
	
}
