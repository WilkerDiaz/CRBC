/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.colascr.transferencias.comunicacionbd
 * Programa   : EntidadBD.java
 * Creado por : Ileana Rojas, Gabriel Martinelli
 * Creado en  : 22/02/2005 10:53:59 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versi�n     : 
 * Fecha       : 
 * Analista    : 
 * Descripci�n :
 * =============================================================================
 */
package com.beco.colascr.transferencias.comunicacionbd; 

/**
 * Descripci�n:
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
	boolean solicitud  = false;

	/**
	 * Constructor para EntidadBD
	 *		Define una entidad de la BD y sus caracter�sticas relevantes para la sincronizaci�n
	 * desde la CR al Servidor y viceversa.
	 * @param esquema - Nombre del esquema al que pertenece la entidad. 
	 * @param nombre - Nombre de la entidad.
	 * @param clave - Vector con los campos que componenen el indice o clave. 
	 * @param actualizacion - Indicador de la existencia del campo actualizaci�n en la entidad.
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
	 * M�todo getEsquema
	 * 
	 * @return String
	 */
	public String getEsquema() {
		return esquema;
	}

	/**
	 * M�todo getNombre
	 * 
	 * @return String
	 */
	public String getNombre() {
		return nombre.toLowerCase();
	}

	/**
	 * M�todo setEsquema
	 * 
	 * @param xEsquema
	 */
	public void setEsquema(String xEsquema) {
		esquema = xEsquema;
	}

	/**
	 * M�todo setNombre
	 * 
	 * @param xNombre
	 */
	public void setNombre(String xNombre) {
		nombre = xNombre;
	}

	/**
	 * M�todo getClave
	 * 
	 * @return String[]
	 */
	public String[] getClave() {
		return clave;
	}

	/**
	 * M�todo setClave
	 * 
	 * @param xClave
	 */
	public void setClave(String[] xClave) {
		clave = xClave;
	}

	/**
	 * M�todo isActualizacion
	 * 		Indica si la entidad contiene el campo actualizacion.
	 * @return boolean
	 */
	public boolean isActualizacion() {
		return actualizacion;
	}

	/**
	 * M�todo setActualizacion
	 * 		Establece la condici�n que indica si la entidad contiene el campo actualizacion. 
	 * @param xActualizacion
	 */
	public void setActualizacion(boolean xActualizacion) {
		actualizacion = xActualizacion;
	}

	/**
	 * M�todo isMarca
	 * 		Indica si la entidad contiene un campo "regactualizado" que indique
	 * si el registro fue o no actualizado (S/N)
	 * @return boolean
	 */
	public boolean isMarca() {
		return marca;
	}
	
	/**
	 * M�todo setMarca
	 * 		Establece que la entidad contiene un campo "regactualizado" que indica
	 * si el registro fue actualizado
	 * @param marca 
	 */
	public void setMarca(boolean marca) {
		this.marca = marca;
	}


	public boolean isSolicitud() {
		return solicitud;
}

	public void setSolicitud(boolean solicitud) {
		this.solicitud = solicitud;
	}
}