package com.beco.sinccompieretda.modelo;
import java.sql.Date;
import java.sql.Time;


public class Promociones {
	private int xx_promociones_id;
	private String tipopromocion;
	private Date fechainicio;
	private Time horainicio; 
	private Date fechafinalizada;
	private Time horafinalizada;
	private int prioridad;
	private String activo;
	private int tipo;
	private int tipo2;
	
	public Promociones(int xx_promociones_id, String tipopromocion, Date fechainicio, Time horainicio, Date fechafinalizada
			, Time horafinalizada, int prioridad, String activo, int tipo, int tipo2) {
		super();
		this.xx_promociones_id = xx_promociones_id;
		this.tipopromocion = tipopromocion;
		this.fechainicio = fechainicio;
		this.horainicio = horainicio;
		this.fechafinalizada = fechafinalizada;
		this.horafinalizada = horafinalizada;
		this.prioridad = prioridad;
		this.activo = activo;
		this.tipo = tipo;
		this.tipo2=tipo2;
	}

	public Promociones(int codigo, String tipo3) {
		this.xx_promociones_id=codigo;
		this.tipopromocion=tipo3;
		// TODO Apéndice de constructor generado automáticamente
	}

	/**
	 * @return el fechafinalizada
	 */
	public Date getFechafinalizada() {
		return fechafinalizada;
	}
	/**
	 * @param fechafinalizada el fechafinalizada a establecer
	 */
	public void setFechafinalizada(Date fechafinalizada) {
		this.fechafinalizada = fechafinalizada;
	}
	/**
	 * @return el fechainicio
	 */
	public Date getFechainicio() {
		return fechainicio;
	}
	/**
	 * @param fechainicio el fechainicio a establecer
	 */
	public void setFechainicio(Date fechainicio) {
		this.fechainicio = fechainicio;
	}
	/**
	 * @return el horafinalizada
	 */
	public Time getHorafinalizada() {
		return horafinalizada;
	}
	/**
	 * @param horafinalizada el horafinalizada a establecer
	 */
	public void setHorafinalizada(Time horafinalizada) {
		this.horafinalizada = horafinalizada;
	}
	/**
	 * @return el horainicio
	 */
	public Time getHorainicio() {
		return horainicio;
	}
	/**
	 * @param horainicio el horainicio a establecer
	 */
	public void setHorainicio(Time horainicio) {
		this.horainicio = horainicio;
	}
	/**
	 * @return el prioridad
	 */
	public int getPrioridad() {
		return prioridad;
	}
	/**
	 * @param prioridad el prioridad a establecer
	 */
	public void setPrioridad(int prioridad) {
		this.prioridad = prioridad;
	}
	/**
	 * @return el tipopromocion
	 */
	public String getTipopromocion() {
		return tipopromocion;
	}
	/**
	 * @param tipopromocion el tipopromocion a establecer
	 */
	public void setTipopromocion(String tipopromocion) {
		this.tipopromocion = tipopromocion;
	}
	/**
	 * @return el activo
	 */
	public String getActivo() {
		return activo;
	}
	/**
	 * @return el xx_promociones_id
	 */
	public int getXx_promociones_id() {
		return xx_promociones_id;
	}
	/**
	 * @return el tipo
	 */
	public int getTipo() {
		return tipo;
	}
	/**
	 * @return el tipo2
	 */
	public int getTipo2() {
		return tipo2;
	}
}
