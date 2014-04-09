/*
 * Creado el 05/05/2008
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
import java.util.Date;
import java.util.Iterator;

import com.beco.cr.actualizadorPrecios.promociones.ManejadorBD.DonacionBD;
import com.becoblohm.cr.manejarventa.Cliente;

public class Donacion {
	Cliente c = new Cliente();
	public  Date hoy = new Date();
	public  int codDonacion, tipoDonacion, estDonacion;
	public  String nomDonacion, descDonacion;
	public  double canProp;
	public  Date inicio, fin;
	public boolean mostrarAlTotalizar;
	
/**	Constructor **/
	public Donacion(int codigo, int codBarra, Date fechaIni, Date fechaFin, String nombre, String descripcion, int tipo, int estado, double cantidad, boolean mostrarAlTotalizar){
		codDonacion = codigo;
		estDonacion = estado;
		tipoDonacion = tipo;
		nomDonacion= nombre;
		descDonacion = descripcion;
		inicio = fechaIni;
		fin = fechaFin;
		canProp = cantidad;
		this.mostrarAlTotalizar = mostrarAlTotalizar;
	}
	public int getCodDonacion(){
		return codDonacion;
	}
	public int getEstDonacion(){
		return estDonacion;
	}
	public int getTipoDonacion(){
		return tipoDonacion;
	}
	public String getNomDonacion(){
		return nomDonacion;
	}
	public String getDescDonacion(){
		return descDonacion;
	}
	public Date getInicio(){
		return inicio;
	}
	public Date getFin(){
		return fin;
	}
	public double getcanProp(){
		return canProp;
	}
	
	/**
	 * Obtiene una donacion a partir del codigo
	 * @param codigo Codigo de la donacion a obtener
	 * @return Donacion donacion correspondiente al codigo
	 * 		null si el codigo no existe
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Iterator'
	* Fecha: agosto 2011
	*/
	public static Donacion getDonacionPorCodigo(int codigo){
		Iterator<Donacion> i = DonacionBD.v.iterator();
		while(i.hasNext()){
			Donacion d = (Donacion)i.next();
			if(d.codDonacion == codigo) return d;
		}
		return null;
	}
	public boolean isMostrarAlTotalizar() {
		return mostrarAlTotalizar;
	}
	public void setMostrarAlTotalizar(boolean mostrarAlTotalizar) {
		this.mostrarAlTotalizar = mostrarAlTotalizar;
	}
}