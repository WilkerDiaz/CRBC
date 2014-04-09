/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.excepciones
 * Programa   : UsuarioExcepcion.java
 * Creado por : Programador3
 * Creado en  : 20/10/2003 04:49:19 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versi�n     : 1.1
 * Fecha       : 02/03/2004 11:21 AM
 * Analista    : gmartinelli
 * Descripci�n : Eliminado constructor
 * 				UsuarioExcepcion(String mensaje, Exception ex, int codModulo, int codFuncion)
 * 				Ahora el modulo de auditoria se encargara de buscar el modulo y la
 * 				funcion donde se genero el error.
 * =============================================================================
 * Versi�n     : 1.0.1
 * Fecha       : 27/11/2003 10:50:41 AM
 * Analista    : Programador3 - Alexis Gu�dez L�pez
 * Descripci�n : Actualizaci�n por requerimientos de integraci�n BECO-EPA.
 * 				 Constructor agregado ->
 * 					UsuarioExcepcion(String mensaje, Exception ex, int codModulo, int codFuncion)
 * =============================================================================
 */
package com.becoblohm.cr.excepciones;

/** 
 * Descripci�n: 
 * 		Esta clase corresponde a excepciones con la conexi�n/acceso a la base
 * de datos, espec�ficamente a la tabla Usuario
 */

public class UsuarioExcepcion extends ExcepcionCr {

	/**
	 * Constructor para UsuarioExcepcion.
	 * @param mensaje - Mensaje que indica la causa de la excepcion durante
	 * 		el acceso a los datos de la tabla Usuario. 
	 */
	public UsuarioExcepcion(String mensaje) {
		super(mensaje);
	}

	/**
	 * Constructor para UsuarioExcepcion.
	 * @param mensaje Mensaje que describe el error presentado.
	 * @param ex Excepcion qe caus� el error.
	 */
	public UsuarioExcepcion(String mensaje, Exception ex) {
		super(mensaje, ex);
	}

}
