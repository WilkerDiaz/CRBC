/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.excepciones
 * Programa   : SesionExcepcion.java
 * Creado por : Programador3
 * Creado en  : 20/10/2003 04:49:19 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1
 * Fecha       : 02/03/2004 11:23 AM
 * Analista    : gmartinelli
 * Descripción : Eliminado constructor
 * 				SesionExcepcion(String mensaje, Exception ex, int codModulo, int codFuncion)
 * 				Ahora el modulo de auditoria se encargara de buscar el modulo y la
 * 				funcion donde se genero el error.
 * =============================================================================
 * Versión     : 1.0.1
 * Fecha       : 27/11/2003 10:50:41 AM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Actualización por requerimientos de integración BECO-EPA.
 * 				 Constructor agregado ->
 * 					SesionExcepcion(String mensaje, Exception ex, int codModulo, int codFuncion)
 * =============================================================================
 */
package com.becoblohm.cr.excepciones;

/** 
 * Descripción: 
 * 		Esta clase corresponde a excepciones donde no puede iniciarse una 
 * sesión en un tienda y caja específica debido a la falla de la carga de 
 * los datos respectivos
 */

public class SesionExcepcion extends ExcepcionCr {

	/**
	 * Constructor para SesionExcepcion.
	 * @param mensaje
	 */
	public SesionExcepcion(String mensaje){
		super(mensaje);
	}

	/**
	 * Constructor para SesionExcepcion.
	 * @param mensaje Mensaje que describe el error presentado.
	 * @param ex Excepcion qe causó el error.
	 */
	public SesionExcepcion(String mensaje, Exception ex) {
		super(mensaje, ex);
	}
	
}
