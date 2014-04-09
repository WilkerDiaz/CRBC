package com.becoblohm.cr.manejarbr;

import java.util.Date;

public class ComprobanteFiscal {

	private VentaBR ventaAsociada;
	private int numComprobante;
	private Date fechaEmision;
	private char tipoComprobante;
	private String serialImpresora;
	
	
	
	public ComprobanteFiscal(VentaBR ventaAsociada, int numComprobante, Date fechaEmision, char tipoComprobante, String serialImpresora) {
		super();
		this.ventaAsociada = ventaAsociada;
		this.numComprobante = numComprobante;
		this.fechaEmision = fechaEmision;
		this.tipoComprobante = tipoComprobante;
		this.serialImpresora = serialImpresora;
	}
	
	public Date getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public int getNumComprobante() {
		return numComprobante;
	}
	public void setNumComprobante(int numComprobante) {
		this.numComprobante = numComprobante;
	}
	public String getSerialImpresora() {
		return serialImpresora;
	}
	public void setSerialImpresora(String serialImpresora) {
		this.serialImpresora = serialImpresora;
	}
	public char getTipoComprobante() {
		return tipoComprobante;
	}
	public void setTipoComprobante(char tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}
	public VentaBR getVentaAsociada() {
		return ventaAsociada;
	}
	public void setVentaAsociada(VentaBR ventaAsociada) {
		this.ventaAsociada = ventaAsociada;
	}
	
	
	
}
