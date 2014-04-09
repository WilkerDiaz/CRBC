/**
 * 
 */
package com.beco.cr.sorteotrx.modelo;

import java.sql.Time;

/**
 * @author aavila
 *
 */
public class Ganador {
	
	private static Time time=null;
	private static int codigo=0;
	private static int detalle=0;

	public Ganador(Time time, int codigo, int detalle) {
		// TODO Apéndice de constructor generado automáticamente
		Ganador.time=time;
		Ganador.codigo=codigo;
		Ganador.detalle=detalle;
	}

	/**
	 * @return el codigo
	 */
	public static int getCodigo() {
		return codigo;
	}

	/**
	 * @return el detalle
	 */
	public static int getDetalle() {
		return detalle;
	}

	/**
	 * @return el time
	 */
	public static Time getTime() {
		return time;
	}

}
