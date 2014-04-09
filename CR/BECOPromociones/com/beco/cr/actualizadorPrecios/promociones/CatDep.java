/*
 * Creado el 20/05/2008
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.beco.cr.actualizadorPrecios.promociones;

/**
 * @author aavila
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class CatDep {

	private String codCat=null, codDep=null;
	
	public CatDep(String categoria, String departamento) {
		codCat=categoria;
		codDep=departamento;
	}
	public String getCodCat(){
		return codCat;
	}
	public String getCodDep(){
			return codDep;
		}

}
