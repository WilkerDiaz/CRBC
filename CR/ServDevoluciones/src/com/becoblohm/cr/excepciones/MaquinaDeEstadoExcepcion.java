/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.excepciones
 * Programa   : MaquinaDeEstadoExcepcion.java
 * Creado por : gmartinelli
 * Creado en  : 30-oct-03 10:29:43
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versi�n     : 1.1
 * Fecha       : 02/03/2004 11:21 AM
 * Analista    : gmartinelli
 * Descripci�n : Eliminado constructor
 * 				MaquinaDeEstadoExcepcion(String mensaje, Exception ex, int codModulo, int codFuncion)
 * 				Ahora el modulo de auditoria se encargara de buscar el modulo y la
 * 				funcion donde se genero el error.
 * =============================================================================
 * Versi�n     : 1.0.1
 * Fecha       : 27/11/2003 10:35:41 AM
 * Analista    : Programador3 - Alexis Gu�dez L�pez
 * Descripci�n : Actualizaci�n por modificaciones de gmartinelli, por requerimientos 
 * 			 	 del m�dulo de Auditor�a.
 * 				 Constructor agregado ->
 * 					MaquinaDeEstadoExcepcion(String mensaje, Exception ex, int codModulo, int codFuncion)
 * =============================================================================
 */
package com.becoblohm.cr.excepciones;

/** 
 * Descripci�n: 
 * 		Esta clase maneja todas las excepciones provenientes de la maquina de 
 * estado, ejemplo: estados incorrectos, autorizaciones requeridas, etc.
 * 
 */
public class MaquinaDeEstadoExcepcion extends ExcepcionCr {

	/**
	 * Constructor para MaquinaDeEstadoExcepcion.
	 * @param mensaje Mensaje que describe el error presentado.
	 */
	public MaquinaDeEstadoExcepcion(String mensaje) {
		super(mensaje);
	}
	
	/**
	 * Constructor para MaquinaDeEstadoExcepcion.
	 * @param mensaje Mensaje que describe el error presentado.
	 * @param ex Excepcion qe causo el error.
	 */
	public MaquinaDeEstadoExcepcion(String mensaje, Exception ex) {
		super(mensaje, ex);
	}
	
}