/*
 * $Id: CRFPResponseParser.java,v 1.2.2.4 2005/05/09 14:29:30 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerreter�a EPA C.A. 
 *
 * Proyecto		: CRFiscalPrinterDriver 1.1
 * Paquete		: com.epa.crprinterdriver
 * Programa		: CRFPResponseParser.java
 * Creado por	: Programa8
 * Creado en 	: 11-abr-2005 13:46:00
 * (C) Copyright 2005 SuperFerreter�a EPA C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Informaci�n de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisi�n	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: CRFPResponseParser.java,v $
 * Revision 1.2.2.4  2005/05/09 14:29:30  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.3  2005/05/09 14:28:23  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.2  2005/05/09 14:19:04  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.1  2005/05/06 18:17:04  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.2  2005/05/05 12:08:47  programa8
 * Versi�n 2.0.1 Final
 *
 * Revision 1.1.2.2  2005/04/13 21:56:58  programa8
 * Driver fiscal al 13/04/2005
 *
 * Revision 1.1.2.1  2005/04/11 20:59:06  programa8
 * CRFiscalPrinterDriver 2.0 - Analisis Preliminar
 *
 * ===========================================================================
 */
package com.epa.crprinterdriver;

import com.epa.crprinterdriver.event.ResponseListener;

/**
 * <p>
 * 	Esta interfaz es usada por el driver de la impresora fiscal 
 * para los procesos de interpretaci�n de las respuestas enviadas
 * por el dispositivo, que son muy dependientes del mismo.
 * </p>
 * <p>
 * <a href="CRFPResponseParser.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - Ar�stides Castillo Colmen�rez - $Author: programa8 $
 * @version $Revision: 1.2.2.4 $ - $Date: 2005/05/09 14:29:30 $
 * @since 11-abr-2005
 * 
 */
public interface CRFPResponseParser {
	
	/**
	 * Se�al Serial CTS
	 */
	public static final int SIGNAL_CTS = 0;
	/**
	 *  Se�al Serial CD: Carrier Detected
	 */
	public static final int SIGNAL_CD = 1;
	/**
	 * Se�al Serial DSR: Data State Ready
	 */
	public static final int SIGNAL_DSR = 2;
	/**
	 * Se�al Serial BI
	 */
	public static final int SIGNAL_BI = 3;
	/**
	 * Se�al Serial RI
	 */
	public static final int SIGNAL_RI = 4;
	/**
	 * Se�al Serial FE
	 */
	public static final int SIGNAL_FE = 5;
	/**
	 * Se�al Serial OE
	 */
	public static final int SIGNAL_OE = 6;
	/**
	 * Se�al Serial PE
	 */
	public static final int SIGNAL_PE = 7;
	
	/**
	 * Construye la respuesta de la impresora fiscal 
	 * NPF4610, TMU950 y compatibles
	 * @param c
	 */
	public void constructResponse(char c);
	
	/**
	 * Construye la respuesta de la impresora fiscal GD4
	 * @param response
	 */
	public void constructResponse(String response);
	
	/**
	 * Indica el objeto status que debe ser actualizado
	 * @param status Objeto de estado del driver
	 */
	public void setStatusToUpdate(CRFPStatus status);
	
	/**
	 * Notifica al interpretador de respuesta que se recibi� una se�al
	 * serial. El tipo es una de las constantes SIGNAL_XXX.
	 * @param type Tipo de se�al llegada
	 * @param value Nuevo valor de la se�al
	 */
	public void signalArrived(int type, boolean value);
	
	/**
	 * Agrega un escucha de eventos de respuesta de impresora fiscal
	 * @param listener
	 */
	public void addResponseListener(ResponseListener listener);
	
	/**
	 * Elimina un escucha de eventos de respuesta de impresora fiscal
	 * @param listener
	 */
	public void removeResponseListener(ResponseListener listener);
}
