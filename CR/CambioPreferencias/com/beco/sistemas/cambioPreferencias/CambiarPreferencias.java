package com.beco.sistemas.cambioPreferencias;

import com.epa.preferencesproxy.EPAPreferenceProxy;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;

public class CambiarPreferencias {

	private static EPAPreferenceProxy eppCR;
	
	public static  void cambiarPreferenciaNombreApellido(int longitudNombre, int longitudApellido){
		try {
			eppCR = new EPAPreferenceProxy("proyectocr");
			CambiarPreferencias.eppCR.setConfigStringForParameter("sistema","longitudApellidoCliente", longitudApellido+"");
			CambiarPreferencias.eppCR.setConfigStringForParameter("sistema","longitudNombreCliente", longitudNombre+"");		
		} catch (NoSuchNodeException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (UnidentifiedPreferenceException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
	}
	
	public static void cambiarPreferenciasCheque(){
		try {
			eppCR = new EPAPreferenceProxy("proyectocr");
			eppCR.setConfigStringForParameter("facturacion","imprimirDorsoCheque", "N");
		} catch (NoSuchNodeException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (UnidentifiedPreferenceException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args){
		CambiarPreferencias.cambiarPreferenciaNombreApellido(100, 100);
	}
}
