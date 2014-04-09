package com.becoblohm.cr.manejarbr;

public class OpcionBR {
	
	private int idMetodo;
	private int orden;
	private String nombreOpcion;
	private String rutaImagen;
	private String tecla;
	private String habilitado;
	
	public OpcionBR(int idMetodo, int orden, String nombreOpcion, String rutaImagen, String tecla, String habilitado) {
		this.idMetodo = idMetodo;
		this.orden = orden;
		this.nombreOpcion = nombreOpcion;
		this.rutaImagen = rutaImagen;
		this.tecla = tecla;
		this.habilitado = habilitado;
	}

	public int getIdModulo() {
		return idMetodo;
	}

	public String getNombreOpcion() {
		return nombreOpcion;
	}

	public int getOrden() {
		return orden;
	}

	public String getRutaImagen() {
		return rutaImagen;
	}

	public String getTecla() {
		return tecla;
	}

	public String getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(String habilitado) {
		this.habilitado = habilitado;
	}

	public int getIdMetodo() {
		return idMetodo;
	}

	public void setIdMetodo(int idMetodo) {
		this.idMetodo = idMetodo;
	}

	public void setNombreOpcion(String nombreOpcion) {
		this.nombreOpcion = nombreOpcion;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}

	public void setTecla(String tecla) {
		this.tecla = tecla;
	}
}
