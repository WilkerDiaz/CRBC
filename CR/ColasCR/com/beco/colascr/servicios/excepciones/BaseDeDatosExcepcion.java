/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.excepciones
 * Programa   : BaseDeDatosExcepcion.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 20/10/2003 04:49:19 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1
 * Fecha       : 02/03/2004 11:07 AM
 * Analista    : gmartinelli
 * Descripción : Eliminado constructor
 * 				BaseDeDatosExcepcion(String mensaje, Exception ex, int codModulo, int codFuncion)
 * 				Ahora el modulo de auditoria se encargara de buscar el modulo y la
 * 				funcion donde se genero el error.
 * =============================================================================
 * Versión     : 1.0.2
 * Fecha       : 27/11/2003 10:35:41 AM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Actualización por modificaciones de gmartinelli, por requerimientos 
 * 			 	 del módulo de Auditoría.
 * 				 Constructor agregado ->
 * 					BaseDeDatosExcepcion(String mensaje, Exception ex, int codModulo, int codFuncion)
 * =============================================================================
 * Versión     : 1.0.1
 * Fecha       : 31/10/2003 09:19:41 AM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Integración de las clases de Excepciones creadas por EPA y BECO
 * =============================================================================
 */
package com.beco.colascr.servicios.excepciones;

/** 
 * Descripción: 
 * 		Esta clase maneja todas las excepciones provenientes del manejo 
 * de acceso/conexión a la base de datos. Fallas en la conexion, errores 
 * en las sentencias Sql, etc.
 */

public class BaseDeDatosExcepcion extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor para BaseDeDatosExcepcion.
	 * @param mensaje - Mensaje que describe la excepcion presentada
	 */
	public BaseDeDatosExcepcion(String mensaje){
		super(mensaje);
	}
	
	/**
	 * Constructor para BaseDeDatosExcepcion.
	 * @param mensaje - Mensaje que describe la excepcion presentada
	 * @param ex - Excepcion que causa la falla
	 */
	public BaseDeDatosExcepcion(String mensaje, Exception ex) {
		super(mensaje, ex);
	}
}