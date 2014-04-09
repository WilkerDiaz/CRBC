package com.beco.colascr.servicios;

import java.util.Vector;

import com.beco.colascr.servicios.mediadorbd.MediadorBD;

/**
 * Analiza apartados inconsistentes para enlazarlos con su transacción
 * @author jgraterol
 * 08/03/2012
 */
public class EnlaceApartados {

	/**
	 * Obtiene el apartado a partir de su número de servicio
	 * @param numServicio
	 */
	public static void actualizarServicio(int numServicio){
		Vector datosTransaccionCierre = (Vector)MediadorBD.obtenerTransaccion(numServicio);
		MediadorBD.actualizarEstadoServicio(datosTransaccionCierre, numServicio);
	}
	
	/**
	 * Obtiene el listado de apartados en C de la tienda actual
	 * @return Vector
	 */
	public static Vector getServiciosC() {
		return MediadorBD.getApartadosEnC();		
	}
	
	public static void enlazarApartados(){
		Vector apartadosEnC = MediadorBD.getApartadosEnC();
		for(int i=0;i<apartadosEnC.size();i++){
			int numServicio = ((Integer)apartadosEnC.get(i)).intValue();
			actualizarServicio(numServicio);
		}
	}
	
	
	public static void main(String[] args){
		try {
			new Sesion();
			enlazarApartados();
		} catch (Exception e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
	}
	
}
