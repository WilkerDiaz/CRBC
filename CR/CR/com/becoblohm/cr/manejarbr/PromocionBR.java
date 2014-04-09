package com.becoblohm.cr.manejarbr;

import java.util.Date;

public class PromocionBR {

	public Date fechaInicia;
	public Date fechaFinaliza;
	public double porcentaje;
	public boolean isActivo;
	public char tipo;
	
	public PromocionBR(Date fechaInicia, Date fechaFinaliza, double porcentaje, boolean isActivo, char tipo) {
		super();
		this.fechaInicia = fechaInicia;
		this.fechaFinaliza = fechaFinaliza;
		this.porcentaje = porcentaje;
		this.isActivo = isActivo;
		this.tipo = tipo;
	}
	
	public Date getFechaFinaliza() {
		return fechaFinaliza;
	}
	public void setFechaFinaliza(Date fechaFinaliza) {
		this.fechaFinaliza = fechaFinaliza;
	}
	public Date getFechaInicia() {
		return fechaInicia;
	}
	public void setFechaInicia(Date fechaInicia) {
		this.fechaInicia = fechaInicia;
	}
	public boolean isActivo() {
		return isActivo;
	}
	public void setActivo(boolean isActivo) {
		this.isActivo = isActivo;
	}
	public double getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(double porcentaje) {
		this.porcentaje = porcentaje;
	}
	public char getTipo() {
		return tipo;
	}
	public void setTipo(char tipo) {
		this.tipo = tipo;
	}
	
	
}
