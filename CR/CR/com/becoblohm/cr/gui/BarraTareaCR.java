/*
 * Creado el 05-sep-06
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.becoblohm.cr.gui;

import javax.swing.JFrame;

/**
 * @author gmartinelli
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class BarraTareaCR extends JFrame {
	
	FacturacionPrincipal facturacion; 
	
	public BarraTareaCR () {
		setBounds(-1000,-1000,0,0); // Hide the frame!
 
		// Set the icon
		/*URL url = new URL("*** your icon here ****");
		if (url!=null) {
			ImageIcon imageicon = new ImageIcon(url);
			setIconImage(imageicon.getImage());
		}*/
 
		setTitle("Caja Registradora");
 
		
		show(); // needed for the taskbar.
		facturacion = new FacturacionPrincipal(this); // The real show
		//dispose(); // get rid of the taskbar.
	}
	/**
	 * @return
	 */
	public FacturacionPrincipal getFacturacion() {
		return facturacion;
	}
	
	public void repintarMenuPrincipal() {
		facturacion = null;
		facturacion = new FacturacionPrincipal(this);
	}
}
