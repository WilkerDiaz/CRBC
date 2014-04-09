/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.perifericos
 * Programa   : Dispositivo.java
 * Creado por : Programador3
 * Creado en  : 26/07/2004 10:47:54 AM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versi�n     : 1.1
 * Fecha       : 26/07/2004 10:47:54 AM
 * Analista    : Programador3
 * Descripci�n : Implementaci�n inicial.
 * =============================================================================
 */
package com.becoblohm.cr.perifericos; 

import com.epa.crserialinterface.Parameters;
import com.epa.preferencesproxy.EPAPreferenceProxy;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;

/**
 * Descripci�n:
 * 
 */

public class Dispositivo {
	String nombre = "";
	
	public Dispositivo(String periferico){
		setNombre(periferico);
	}
	
	/**
	 * M�todo setPreferencias
	 * 
	 * @param parametros
	 * @throws NoSuchNodeException
	 * @throws UnidentifiedPreferenceException
	 */
	public void setPreferencias(Parameters parametros, EPAPreferenceProxy preferencesproxy) throws NoSuchNodeException, UnidentifiedPreferenceException{
		parametros.setPortName(preferencesproxy.getConfigStringForParameter(getNombre(),"Puerto Serial"));
		parametros.setBaudRate(preferencesproxy.getConfigStringForParameter(getNombre(), "Baud Rate"));
		parametros.setDatabits(preferencesproxy.getConfigStringForParameter(getNombre(),"Data Bits"));
		parametros.setFlowControlIn(preferencesproxy.getConfigStringForParameter(getNombre(), "Flow Control In"));
		parametros.setFlowControlOut(preferencesproxy.getConfigStringForParameter(getNombre(), "Flow Control Out"));
		parametros.setParity(preferencesproxy.getConfigStringForParameter(getNombre(), "Parity"));
		parametros.setStopbits(preferencesproxy.getConfigStringForParameter(getNombre(), "Stop Bits"));
	}
	
	/**
	 * M�todo getNombre
	 * 
	 * @return String
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * M�todo setNombre
	 * 
	 * @param string
	 */
	public void setNombre(String string) {
		nombre = new String(string);
	}

}