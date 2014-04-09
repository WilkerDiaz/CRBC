package com.beco.sinccompieretda.modelo;

public class Tienda {
	
	private  String codigo;
	private  String ip;
	private  String directorio;
	private  String usuario;
	private  String password;
	private String origen;
	
	public Tienda(String codigo, String ip, String directorio, String usuario, String password, String origen) {
		// TODO Apéndice de constructor generado automáticamente
		this.codigo = codigo;
		this.ip = ip;
		this.directorio = directorio;
		this.usuario = usuario;
		this.password = password;
		this.origen = origen;
	}

	/**
	 * @return el codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @return el origen
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * @param origen el origen a establecer
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * @param codigo el codigo a establecer
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return el directorio
	 */
	public String getDirectorio() {
		return directorio;
	}

	/**
	 * @param directorio el directorio a establecer
	 */
	public void setDirectorio(String directorio) {
		this.directorio = directorio;
	}

	/**
	 * @return el ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip el ip a establecer
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return el password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password el password a establecer
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return el usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario el usuario a establecer
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}
