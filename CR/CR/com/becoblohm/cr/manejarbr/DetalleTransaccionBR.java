package com.becoblohm.cr.manejarbr;



public class DetalleTransaccionBR {
	
	private String codTarjeta;
	private double monto;
	private double montoImpuesto;
	private int numSeq;
	private char estadoRegistro;

	public DetalleTransaccionBR(String codTarjeta, double monto, int numSeq, char estadoRegistro){
		this.codTarjeta = codTarjeta;
		this.monto = monto;
		this.numSeq = numSeq;
	}
	
	public String getCodTarjeta() {
		return codTarjeta;
	}

	public void setCodTarjeta(String codTarjeta) {
		this.codTarjeta = codTarjeta;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public double getMontoImpuesto() {
		return montoImpuesto;
	}

	public void setMontoImpuesto(double montoImpuesto) {
		this.montoImpuesto = montoImpuesto;
	}

	public int getNumSeq() {
		return numSeq;
	}

	public void setNumSeq(int numSeq) {
		this.numSeq = numSeq;
	}

	public char getEstadoRegistro() {
		return estadoRegistro;
	}

	public void setEstadoRegistro(char estadoRegistro) {
		this.estadoRegistro = estadoRegistro;
	}
	
	

}
