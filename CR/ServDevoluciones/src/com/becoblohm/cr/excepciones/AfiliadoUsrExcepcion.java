/**
 * =============================================================================
 * Proyecto   : CRBECOEPA
 * Paquete    : com.becoblohm.cr.excepciones
 * Programa   : AfiliadoUsrExcepcion.java
 * Creado por : Programador3
 * Creado en  : 20/10/2003 04:49:19 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1
 * Fecha       : 02/03/2004 11:21 AM
 * Analista    : gmartinelli
 * Descripción : Eliminado constructor
 * 				AfiliadoUsrExcepcion(String mensaje, Exception ex, int codModulo, int codFuncion)
 * 				Ahora el modulo de auditoria se encargara de buscar el modulo y la
 * 				funcion donde se genero el error.
 * =============================================================================
 * Versión     : 1.0.1
 * Fecha       : 27/11/2003 10:50:41 AM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Actualización por requerimientos de integración BECO-EPA.
 * 				 Constructor agregado ->
 * 					AfiliadoUsrExcepcion(String mensaje, Exception ex, int codModulo, int codFuncion)
 * =============================================================================
 */
package com.becoblohm.cr.excepciones;

/** 
 * Descripción: 
 * 		Esta clase corresponde a excepciones donde un usuario que se trata 
 * de agregar no ha sido registrado como un colaborador válido o no está
 * asignado en la tienda iniciada.
 */

public class AfiliadoUsrExcepcion extends ExcepcionCr {

	/**
	 * Constructor para AfiliadoExcepcion.
	 * @param mensaje
	 */
	public AfiliadoUsrExcepcion(String mensaje){
		super(mensaje);
	}

	/**
	 * Constructor para AfiliadoUsrExcepcion.
	 * @param mensaje Mensaje que describe el error presentado.
	 * @param ex Excepcion qe causó el error.
	 */
	public AfiliadoUsrExcepcion(String mensaje, Exception ex) {
		super(mensaje, ex);
	}

}