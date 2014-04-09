package com.beco.sinccompieretda.modelo;

import java.sql.Date;

public class Donacion {
	private int codigo;
	private int detalle;
	private String name;
	private String tipodonacion;
	private double montodonacion;
	private String tienda;
	private String activo;
	private String activo1;
	private Date fechainicio;
	private Date fechafin;
	private String alTotalizar;
	
	public Donacion(int codigo, int detalle, String name, String tipodonacion
			, double montodonacion, String tienda, String activo, String activo1
			, Date fechainicio, Date fechafin, String alTotalizar) {
		this.codigo = codigo;
		this.detalle = detalle;
		this.name = name;
		this.tipodonacion = tipodonacion;
		this.montodonacion = montodonacion;
		this.tienda = tienda;
		this.activo = activo;
		this.activo1 = activo1;
		this.fechainicio = fechainicio;
		this.fechafin = fechafin;
		this.alTotalizar = alTotalizar;
	}

	/**
	 * @return el codigo
	 */
	public int getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo el codigo a establecer
	 */
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return el detalle
	 */
	public int getDetalle() {
		return detalle;
	}

	/**
	 * @param detalle el detalle a establecer
	 */
	public void setDetalle(int detalle) {
		this.detalle = detalle;
	}

	/**
	 * @return el montodonacion
	 */
	public double getMontodonacion() {
		return montodonacion;
	}

	/**
	 * @param montodonacion el montodonacion a establecer
	 */
	public void setMontodonacion(double montodonacion) {
		this.montodonacion = montodonacion;
	}

	/**
	 * @return el name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name el name a establecer
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return el tienda
	 */
	public String getTienda() {
		return tienda;
	}

	/**
	 * @param tienda el tienda a establecer
	 */
	public void setTienda(String tienda) {
		this.tienda = tienda;
	}

	/**
	 * @return el tipodonacion
	 */
	public String getTipodonacion() {
		if (tipodonacion.equals("1000000")) return "1";
		return "2";
	}

	/**
	 * @param tipodonacion el tipodonacion a establecer
	 */
	public void setTipodonacion(String tipodonacion) {
		this.tipodonacion = tipodonacion;
	}

	/**
	 * @return el activo
	 */
	public int getActivo() {
		if (activo.equals("Y")) return 1;
		return 0;
	}
		
	public int getActivo1() {
		if (activo1.equals("Y")) return 1;
		return 0;
	}

	/**
	 * @return el fechafin
	 */
	public Date getFechafin() {
		return fechafin;
	}

	/**
	 * @return el fechainicio
	 */
	public Date getFechainicio() {
		return fechainicio;
	}

	/**
	 * @param fechafin el fechafin a establecer
	 */
	public void setFechafin(Date fechafin) {
		this.fechafin = fechafin;
	}

	/**
	 * @return el alTotalizar
	 */
	public int getAlTotalizar() {
		if (alTotalizar.equals("Y")) return 1;
		return 0;
	}


}
