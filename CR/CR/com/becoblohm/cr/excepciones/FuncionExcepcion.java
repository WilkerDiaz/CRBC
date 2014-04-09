/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.excepciones
 * Programa   : FuncionExcepcion.java
 * Creado por : Programador3
 * Creado en  : 20/10/2003 04:49:19 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versi�n     : 1.1.1
 * Fecha       : 24/03/2004 14:03 PM
 * Analista    : gmartinelli
 * Descripci�n : Agregado constructores que recibe un parametro boolean excepcion 
 * 				para que indiquen si los errores son buscando codigos de metodo y
 * 				funcion para no buscar esos valores en la base de datos.
 * =============================================================================
 * Versi�n     : 1.1
 * Fecha       : 02/03/2004 11:21 AM
 * Analista    : gmartinelli
 * Descripci�n : Eliminado constructor
 * 				FuncionExcepcion(String mensaje, Exception ex, int codModulo, int codFuncion)
 * 				Ahora el modulo de auditoria se encargara de buscar el modulo y la
 * 				funcion donde se genero el error.
 * =============================================================================
 * Versi�n     : 1.0.1
 * Fecha       : 27/11/2003 10:50:41 AM
 * Analista    : Programador3 - Alexis Gu�dez L�pez
 * Descripci�n : Actualizaci�n por requerimientos de integraci�n BECO-EPA.
 * 				 Constructor agregado ->
 * 					FuncionExcepcion(String mensaje, Exception ex, int codModulo, int codFuncion)
 * =============================================================================
 */
package com.becoblohm.cr.excepciones;

/** 
 * Descripci�n: 
 * 		Esta clase corresponde a excepciones con la conexi�n/acceso a la base
 * de datos, espec�ficamente a la tabla Funcion
 */

public class FuncionExcepcion extends ExcepcionCr {

	/**
	 * Constructor para SesionExcepcion.
	 * @param mensaje
	 */
	public FuncionExcepcion(String mensaje){
		super(mensaje, false);
	}

	/**
	 * Constructor para SesionExcepcion.
	 * @param mensaje
	 */
	public FuncionExcepcion(String mensaje, boolean esBuscandoCodigoModulo){
		super(mensaje, esBuscandoCodigoModulo);
	}

	/**
	 * Constructor para FuncionExcepcion.
	 * @param mensaje Mensaje que describe el error presentado.
	 * @param ex Excepcion qe caus� el error.
	 */
	public FuncionExcepcion(String mensaje, Exception ex) {
		super(mensaje, ex, false);
	}

	/**
	 * Constructor para FuncionExcepcion.
	 * @param mensaje Mensaje que describe el error presentado.
	 * @param ex Excepcion qe caus� el error.
	 */
	public FuncionExcepcion(String mensaje, Exception ex, boolean esBuscandoCodigoModulo) {
		super(mensaje, ex, esBuscandoCodigoModulo);
	}

}