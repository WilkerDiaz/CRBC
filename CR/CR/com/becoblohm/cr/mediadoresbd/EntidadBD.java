/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.mediadoresbd
 * Programa   : EntidadBD.java
 * Creado por : Programador3
 * Creado en  : 06/04/2004 11:58:55 AM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1
 * Fecha       : 06/04/2004 11:58:55 AM
 * Analista    : Programador3
 * Descripción : Implementación inicial.
 * =============================================================================
 */
package com.becoblohm.cr.mediadoresbd; 

/**
 * Descripción:
 * 		Esta clase define los atributos necesarios para generalizar el uso de todas 
 * las entidades con el mismo juego de sentencias SQL para sincronizar. 
 * 
 */

public class EntidadBD {
	String esquema;
	String nombre;
	String[] clave;
	boolean actualizacion;
	boolean marca;

	/**
	 * Constructor para EntidadBD
	 *		Define una entidad de la BD y sus características relevantes para la sincronización
	 * desde la CR al Servidor y viceversa.
	 * @param esquema - Nombre del esquema al que pertenece la entidad. 
	 * @param nombre - Nombre de la entidad.
	 * @param clave - Vector con los campos que componenen el indice o clave. 
	 * @param actualizacion - Indicador de la existencia del campo actualización en la entidad.
	 */
	
	
	public EntidadBD(String esquema, String nombre, String[] clave, boolean actualizacion){
		setEsquema(esquema);
		setNombre(nombre);
		setClave(clave);
		setActualizacion(actualizacion);
	}

	
	public EntidadBD(String esquema, String nombre, String[] clave, boolean actualizacion, boolean marca){
		this(esquema, nombre, clave, actualizacion);
		setMarca(marca);
	}
	
	/**
	 * Método getEsquema
	 * 
	 * @return String
	 */
	public String getEsquema() {
		return esquema;
	}

	/**
	 * Método getNombre
	 * 
	 * @return String
	 */
	public String getNombre() {
		return nombre.toLowerCase();
	}

	/**
	 * Método setEsquema
	 * 
	 * @param xEsquema
	 */
	public void setEsquema(String xEsquema) {
		esquema = xEsquema;
	}

	/**
	 * Método setNombre
	 * 
	 * @param xNombre
	 */
	public void setNombre(String xNombre) {
		nombre = xNombre;
	}

	/**
	 * Método getClave
	 * 
	 * @return String[]
	 */
	public String[] getClave() {
		return clave;
	}

	/**
	 * Método setClave
	 * 
	 * @param xClave
	 */
	public void setClave(String[] xClave) {
		clave = xClave;
	}

	/**
	 * Método isActualizacion
	 * 		Indica si la entidad contiene el campo actualizacion.
	 * @return boolean
	 */
	public boolean isActualizacion() {
		return actualizacion;
	}

	/**
	 * Método setActualizacion
	 * 		Establece la condición que indica si la entidad contiene el campo actualizacion. 
	 * @param xActualizacion
	 */
	public void setActualizacion(boolean xActualizacion) {
		actualizacion = xActualizacion;
	}

	/**
	 * Método isMarca
	 * 		Indica si la entidad contiene un campo "regactualizado" que indique
	 * si el registro fue o no actualizado (S/N)
	 * @return boolean
	 */
	public boolean isMarca() {
		return marca;
	}
	
	/**
	 * Método setMarca
	 * 		Establece que la entidad contiene un campo "regactualizado" que indica
	 * si el registro fue actualizado
	 * @param marca 
	 */
	public void setMarca(boolean marca) {
		this.marca = marca;
	}
}