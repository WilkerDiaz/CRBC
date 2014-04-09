/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.excepciones
 * Programa   : ProductoExcepcion.java
 * Creado por : gmartinelli
 * Creado en  : 30-oct-03 10:20:28
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1
 * Fecha       : 02/03/2004 11:15 AM
 * Analista    : gmartinelli
 * Descripción : Eliminado constructor
 * 				ProductoExcepcion(String mensaje, Exception ex, int codModulo, int codFuncion)
 * 				Ahora el modulo de auditoria se encargara de buscar el modulo y la
 * 				funcion donde se genero el error.
 * =============================================================================
 * Versión     : 1.0.1
 * Fecha       : 27/11/2003 10:35:41 AM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Actualización por modificaciones de gmartinelli, por requerimientos 
 * 			 	 del módulo de Auditoría.
 * 				 Constructor agregado ->
 * 					ProductoExcepcion(String mensaje, Exception ex, int codModulo, int codFuncion)
 * =============================================================================
 */
package com.becoblohm.cr.excepciones;

/** 
 * Descripción: 
 * 		Esta clase maneja todas las excepciones provenientes de la busqueda de datos
 * de un producto.
 * 
 */
public class ProductoExcepcion extends ExcepcionCr {

	/**
	 * Constructor para ProductoExcepcion.
	 * @param mensaje Mensaje que describe el error presentado.
	 */
	public ProductoExcepcion(String mensaje) {
		super(mensaje);
	}
	
	/**
	 * Constructor para ProductoExcepcion.
	 * @param mensaje Mensaje que describe el error presentado.
	 * @param ex Excepcion qe causo el error.
	 */
	public ProductoExcepcion(String mensaje, Exception ex) {
		super(mensaje, ex);
	}

}
