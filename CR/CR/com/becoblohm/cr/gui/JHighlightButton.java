/*
 * Creado el 02/11/2004
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.becoblohm.cr.gui;

import java.awt.Color;

import javax.swing.Action;
import javax.swing.JButton;

/**
 * @author Programa8
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class JHighlightButton extends JButton {
	
	protected Color normColor = null;
	protected Color focusColor = new Color(180, 180, 255);

	/**
	 * This method initializes 
	 * 
	 */
	public JHighlightButton() {
		super();
		initialize();
		normColor = getBackground();
	}
	
	public JHighlightButton(Action a) {
	    super(a);
		initialize();
		normColor = getBackground();
	}
	
	public JHighlightButton(Color fc) {
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

