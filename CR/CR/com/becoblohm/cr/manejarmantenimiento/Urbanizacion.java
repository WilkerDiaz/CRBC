/*
 * Creado el 21-jul-06
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.becoblohm.cr.manejarmantenimiento;

/**
 * @author yzambrano
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class Urbanizacion {
	private int codigo;
	private String descripcion;
	private String zonaPostal;
	private int codEstado;
	private int codCiudad;
	
	public Urbanizacion (int cod, String desUrb, String zona, int codEst, int codCiu)
	{
		this.codigo = cod;
		this.descripcion = desUrb;
		this.zonaPostal = zona;
		this.codEstado = codEst;
		this.codCiudad = codCiu;
	}
	/**
	 * @return
	 */
	public int getCodCiudad() {
		return codCiudad;
	}

	/**
	 * @return
	 */
	public int getCodEstado() {
		return codEstado;
	}

	/**
	 * @return
	 */
	public int getCodigo() {
		return codigo;
	}

	/**
	 * @return
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @return
	 */
	public String getZonaPostal() {
		return zonaPostal;
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
	 * @param string
	 */
	public void setZonaPostal(String string) {
		zonaPostal = string;
	}

}
