/*
 * ===========================================================================
 * Material Propiedad CentroBeco C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.extensiones.impl
 * Programa		: DefaultPDA.java
 * Creado por	: varios autores
 * Creado en 	: 10-08-2009 
 * (C) Copyright 2005 CentroBeco C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * ===========================================================================
 */
package com.becoblohm.cr.extensiones.impl;

import javax.swing.JDialog;

import com.beco.cr.pda.MensajePDA;
import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.extensiones.PDA;
import com.becoblohm.cr.gui.VentanaAviso;
import com.becoblohm.cr.manejarusuario.Usuario;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.becoblohm.cr.verificador.Verificador;

/**
 * <pre>
 * Proyecto: CR 
 * Clase: DefaultPDA
 * </pre>
 * <p>
 * <a href="DefaultPDA.java.html"><i>View Source</i></a>
 * </p>
 */
public class DefaultPDA implements PDA {
	
	public void iniciarVerificador(String[] params) {
		InitCR.verificador = new Verificador(params);
		MensajesVentanas.centrarVentana(InitCR.verificador);
	}
	public boolean esModuloPDA(){
		return false;
	}
	public Usuario usuarioActivoOUsuarioPDA(Usuario usuarioActivo){
		return usuarioActivo;
	}
	public void recuperarFacturaEnEspera(MensajePDA mensaje){}
	
	public void totalizar(){}
	
	public void consultaPDA(MensajePDA mensajeEntrada){}
	
	public void mensajesPorPantalla(String titulo, String mensaje, String urlIcon, boolean inicio, JDialog ventanaActiva ) {
		VentanaAviso ventana = null;
		if (inicio) {
			ventana = new VentanaAviso(titulo, mensaje, urlIcon, true);
		} else if (ventanaActiva == null) {
			ventana = new VentanaAviso(titulo, mensaje, urlIcon, true);
		} else {
			ventana = new VentanaAviso(titulo, mensaje, urlIcon);
		}
		
		MensajesVentanas.centrarVentanaDialogo(ventana);
	}

	public void iniciarSesion(MensajePDA mensaje) {
		// TODO Apéndice de método generado automáticamente
		
	}
	
}
