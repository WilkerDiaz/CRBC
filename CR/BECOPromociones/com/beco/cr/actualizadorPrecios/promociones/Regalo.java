package com.beco.cr.actualizadorPrecios.promociones;

import java.util.Date;

public class Regalo {
	@SuppressWarnings("unused")
	private Date hoy = new Date();
	private int codRegalo,  estadoRegalo;
	private String tipoRegalo, nombreRegalo;
	private double cantMinCompra, regbs;
	private Date inicio, fin;
	
/**	Constructor **/
	public Regalo(int estado, Date fechaFin, Date fechaIni, int codigo, String nombre, double cantMin, String tipo, double regaloBs){
		codRegalo = codigo;
		estadoRegalo = estado;
		tipoRegalo = tipo;
		nombreRegalo = nombre;
		cantMinCompra = cantMin;
		inicio = fechaIni;
		fin = fechaFin;
		regbs = regaloBs;
	}
	public int getCodRegalo(){
		return codRegalo;
	}
	public int getEstadoRegalo(){
		return estadoRegalo;
	}
	public String getTipoRegalo(){
		return tipoRegalo;
	}
	public String getNombre(){
		return nombreRegalo;
	}
	public double getCantMinima(){
		return cantMinCompra;
	}
	public Date getInicio(){
		return inicio;
	}
	public Date getFin(){
			return fin;
	}
	public double getRegbs(){
		return regbs;
	}
/**	Calcula la cantidad de tickets que se deben dar por cada transaccion**/
	/**public int cantARegalar (double m, double cant){
		int x = (int)(m/cant);
		return x;
	}**/
/** Imprimir en la factura los regalos correspondientes a la ves que se le avisa al cajero**/
	
//	Fin de clase**************************************************************************************************************************************
}
