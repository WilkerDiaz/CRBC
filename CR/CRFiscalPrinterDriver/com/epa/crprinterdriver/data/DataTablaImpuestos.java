/*
 * Creado el 07/06/2007
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.epa.crprinterdriver.data;
import java.util.Date;


/**
 * @author lgomez
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class DataTablaImpuestos {
	/**
	 * Comentario para <code>tablaImpuestos</code>
	 * Tabla de Impuestos grabada en la Impresora Fiscal
	 * Posiciones Validas 2 a 4.
	 */
	private double [] tablaImpuestos = new double[5];
	/**
	 * Comentario para <code>fechaHoraIF</code>
	 * Fecha y Hora de la Impresora fiscal
	 */
	private Date fechaHoraIF;
	/**
	 * Comentario para <code>numeroCierre</code>
	 * Numero de Reportes Z en la vida de la IF.
	 */
	private int numeroCierre;
	
	public DataTablaImpuestos(Date fecha, int ncierre){
		super();
		this.fechaHoraIF=fecha;
		this.numeroCierre=ncierre;
	}
	
	public double getTablaImpuestos(int pos) throws IndexOutOfBoundsException {
		return tablaImpuestos[pos];
	}
	public void setTablaImpuestos(double valor, int pos) throws IndexOutOfBoundsException{
		this.tablaImpuestos[pos] = valor;
	}
	public double[] getTablaImpuestos() {
		return tablaImpuestos;
	}
	public void setTablaImpuestos(double[] tablaImpuestos) {
		this.tablaImpuestos = tablaImpuestos;
	}
	public Date getFechaHoraIF() {
		return fechaHoraIF;
	}
	public void setFechaHoraIF(Date fechaHoraIF) {
		this.fechaHoraIF = fechaHoraIF;
	}
	public int getNumeroCierre() {
		return numeroCierre;
	}
	public void setNumeroCierre(int numeroCierre) {
		this.numeroCierre = numeroCierre;
	}
	public String getPosicionTablaImpuestos(double valorTasaImpositiva, int categoriaExcento) throws IndexOutOfBoundsException{
		boolean impuestoValido=false;
		if(valorTasaImpositiva==0)
			return "0"+categoriaExcento;
		for(int i=0;i<5;i++){
			if(valorTasaImpositiva==tablaImpuestos[i]){
				impuestoValido=true;
				return "0"+i;
				}
		}
		if(!impuestoValido)
		{
			throw new IllegalArgumentException("Data Inválida::Valor de Impuesto no encontrado en Impresora Fiscal");
		}
		return "00";
	}
	
	public Object clone() {
		DataTablaImpuestos clon = new DataTablaImpuestos(this.fechaHoraIF,this.numeroCierre);
		double [] tablaImpuestosClon =  new double[5];
		for(int i=0;i<this.tablaImpuestos.length;i++){
			tablaImpuestosClon[i] = this.tablaImpuestos[i];
		}
		clon.setTablaImpuestos(tablaImpuestosClon);
		return clon;
	}

}
