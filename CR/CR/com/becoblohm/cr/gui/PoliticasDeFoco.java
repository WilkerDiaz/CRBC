 /**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : PoliticasDeFoco.java
 * Creado por : rabreu
 * Creado en  : 16/01/2007
 * Descripci�n: Permite asignar las pol�ticas de foco para un grupo de
 * 				componentes en una ventana. 
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versi�n     : 
 * Fecha       : 
 * Analista    : 
 * Descripci�n : 
 * =============================================================================
 * 
 * Tomado de http://forum.java.sun.com/thread.jspa?threadID=234532&messageID=1710043
 * 
 * Ejemplo de uso:
 *     Component[] compList = {jPasswordField,jButtonAceptar,jButtonCancelar};
 *	   this.setFocusTraversalPolicy(new PoliticasDeFoco(compList));
 */

package com.becoblohm.cr.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.Arrays;
import java.util.Vector;

/*
* En esta clase se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
* S�lo se parametriz� el tipo de dato contenido en los 'Vector'
* Fecha: agosto 2011
*/
public class PoliticasDeFoco extends FocusTraversalPolicy {

	private Vector<Component> _Components;
	private int _size=0;
	
	public PoliticasDeFoco(Component[] objs) {
		_Components = new Vector<Component>(Arrays.asList(objs));
		_size = objs.length;
	}
	
	public Component getComponentAfter(Container focusCycleRoot, Component aComponent) {
		int loc = _Components.indexOf(aComponent);
		return (loc == -1) ? null : (Component)_Components.elementAt((loc==(_size-1))?0:loc+1);
	}
	
	public Component getLastComponent(Container focusCycleRoot) {
		return (_size == 0) ? null : (Component)_Components.elementAt(_size-1);
	}
	public Component getFirstComponent(Container focusCycleRoot) {
		return (_size == 0) ? null : (Component)_Components.elementAt(0);
	}
	public Component getDefaultComponent(Container focusCycleRoot) {
		return getFirstComponent(focusCycleRoot);
	}
	public Component getComponentBefore(Container focusCycleRoot, Component aComponent) {
		int loc = _Components.indexOf(aComponent);
		return (loc == -1) ? null : (Component)_Components.elementAt((loc==0)?_size-1:loc-1);
	}
}