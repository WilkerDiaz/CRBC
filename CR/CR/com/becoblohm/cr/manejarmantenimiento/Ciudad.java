/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.tienda
 * Programa   : Caja.java
 * Creado por : yzambrano
 * Creado en  : 07-ago-06 
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
package com.becoblohm.cr.manejarmantenimiento;

/**
 * @author yzambrano
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generaci�n de c�digo&gt;C�digo y comentarios
 */
public class Ciudad {
	
	private int codigo;
	private String descripcion;
	private String codArea;
	private int codEstado;
	
	/**
	 * Constructor de ciudad
	 * @param cod
	 * @param desCiu
	 * @param codArea
	 * @param codEst
	 */
	public Ciudad (int cod, String desCiu, String codArea, int codEst)
	{
		this.codigo = cod;
		this.descripcion = desCiu;
		this.codArea = codArea;
		this.codEstado = codEst;
	}
	
	/**
	 * @return c�digo de �rea de la ciudad
	 */
	public String getCodArea() {
		return codArea;
	}

	/**
	 * @return c�digo de la ciudad
	 */
	public int getCodigo() {
		return codigo;
	}

	/**
	 * @return descipcion de la ciudad
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param i
	 */
	public void setCodArea(String i) {
		codArea = i;
	}

	/**
	 * @param i
	 */
	public void setCodigo(int i) {
		codigo = i;
	}

	/**
	 * @param string
	 */
	public void setDescripcion(String string) {
		descripcion = string;
	}

	/**
	 * @return c�digo del estado
	 */
	public int getCodEstado() {
		return codEstado;
	}

}
