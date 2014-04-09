/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui
 * Programa   : ClockLabel.java
 * Creado por : irojas
 * Creado en  : 06-may-04 14:18:25
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1
 * Fecha       : 06-may-04 14:18:25
 * Analista    : irojas
 * Descripción : Implementación inicial.
 * =============================================================================
 */
package com.becoblohm.cr.gui; 

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * Descripción: 
 * 		Clase que se encarga de mantener la hora actualizada en las pantallas que así lo requieran
 * 
 */

public class ClockLabelCR extends JLabel implements ActionListener{
	
	  public ClockLabelCR() {
		super(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", new Locale("es","VE")).format(new Date()));
		Timer t = new Timer(500, this);
		t.start();
	  }

	  public void actionPerformed(ActionEvent ae) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", new Locale("es","VE"));
		setText(formato.format(new Date()));
	  }

}
