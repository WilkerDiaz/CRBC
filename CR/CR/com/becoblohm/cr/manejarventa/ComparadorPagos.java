package com.becoblohm.cr.manejarventa; 

import java.util.Comparator;

import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;


/*
* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
* S�lo se parametriz� el tipo de dato contenido en los 'Comparator'
* Fecha: agosto 2011
*/
public class ComparadorPagos implements Comparator<Object>{

	public int compare(Object arg0, Object arg1) {
		double codigo1 = Double.parseDouble(((Pago)arg0).getFormaPago().getCodigo());
		double codigo2 = Double.parseDouble(((Pago)arg1).getFormaPago().getCodigo());
		return Double.compare(codigo1, codigo2);
	}
	
}
