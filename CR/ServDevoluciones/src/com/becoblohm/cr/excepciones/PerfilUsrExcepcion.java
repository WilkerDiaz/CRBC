/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.excepciones
 * Programa   : PerfilUsrExcepcion.java
 * Creado por : Programador3
 * Creado en  : 20/10/2003 04:49:19 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versi�n     : 1.1
 * Fecha       : 02/03/2004 11:27 AM
 * Analista    : gmartinelli
 * Descripci�n : Eliminado constructor
 * 				PerfilUsrExcepcion(String mensaje, Exception ex, int codModulo, int codFuncion)
 * 				Ahora el modulo de auditoria se encargara de buscar el modulo y la
 * 				funcion donde se genero el error.
 * =============================================================================
 * Versi�n     : 1.0.1
 * Fecha       : 27/11/2003 10:50:41 AM
 * Analista    : Programador3 - Alexis Gu�dez L�pez
 * Descripci�n : Actualizaci�n por requerimientos de integraci�n BECO-EPA.
 * 				 Constructor agregado ->
 * 					PerfilUsrExcepcion(String mensaje, Exception ex, int codModulo, int codFuncion)
 * =============================================================================
 */
package com.becoblohm.cr.excepciones;

/** 
 * Descripci�n: 
 * 		Esta clase corresponde a excepciones donde un usuario trata de 
 * ingresar al sistema y no tiene asignado un perfil v�lido
 */

public class PerfilUsrExcepcion extends ExcepcionCr {

	/**
	 * Constructor para PerfilExcepcion.
	 * @param mensaje
	 */
	public PerfilUsrExcepcion(String mensaje){
		super(mensaje);
	}

	/**
	 * Constructor para PerfilUsrExcepcion.
	 * @param mensaje Mensaje que describe el error presentado.
	 * @param ex Excepcion qe caus� el error.
	 */
	public PerfilUsrExcepcion(String mensaje, Exception ex) {
		super(mensaje, ex);
	}
	
}
