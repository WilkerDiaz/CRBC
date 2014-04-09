/*
 * ===========================================================================
 * Material Propiedad CentroBeco C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.extensiones
 * Programa		: PDA.java
 * Creado por	: varios autores
 * Creado en 	: 10-08-2009 
 * (C) Copyright 2005 CentroBeco C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * =============================================================================
 */

package com.becoblohm.cr.extensiones;

import javax.swing.JDialog;

import com.beco.cr.pda.MensajePDA;
import com.becoblohm.cr.manejarusuario.Usuario;


 

/**
 * <pre>
 * Proyecto: CR 
 * Clase: PDA
 * </pre>
 * <p>
 * <a href="PDA.java.html"><i>View Source</i></a>
 * </p>
 */
public interface PDA extends CRExtension {
	
	public abstract void iniciarVerificador(String[] params);
	
	public abstract void recuperarFacturaEnEspera(MensajePDA mensaje);
	
	public abstract void totalizar();
	
	public abstract void consultaPDA(MensajePDA mensajeEntrada);

	public abstract void mensajesPorPantalla(String titulo, String mensaje, String urlIcon, boolean inicio, JDialog ventanaActiva ) ;
	
	public abstract void iniciarSesion(MensajePDA mensaje);
	
	public abstract Usuario usuarioActivoOUsuarioPDA(Usuario usuarioActivo) throws Exception;
	
	public abstract boolean esModuloPDA();
 }
