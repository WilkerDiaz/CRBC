/*
 * Creado el 12-nov-2004
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generaci�n de c�digo&gt;C�digo y comentarios
 */
package com.becoblohm.cr.extensiones;

import java.sql.Timestamp;

/**
 * Interfaz para el manejo de proxies de tiempo, para la sincronizaci�n de
 * hora y fecha en CR.
 * @author Programa8 - Ar&iacute;stides Castillo
 * 
 */
public interface TimeProxy extends CRExtension {
	
	/**
	 * Retorna fecha y hora en formato de Timestamp
	 * @return Fecha y hora del proveedor de tiempo
	 */
	public Timestamp getTimestamp();
	
}
