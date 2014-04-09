/*
 * $Id: ResponseListener.java,v 1.2.2.3 2005/05/09 14:28:17 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CRFiscalPrinterDriver 1.1
 * Paquete		: com.epa.crprinterdriver.event
 * Programa		: ResponseListener.java
 * Creado por	: Programa8
 * Creado en 	: 13-abr-2005 9:22:03
 * (C) Copyright 2005 SuperFerretería EPA C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisión	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: ResponseListener.java,v $
 * Revision 1.2.2.3  2005/05/09 14:28:17  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.2  2005/05/09 14:18:55  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.1  2005/05/06 19:12:46  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.2  2005/05/05 12:08:43  programa8
 * Versión 2.0.1 Final
 *
 * Revision 1.1.2.1  2005/04/13 21:57:08  programa8
 * Driver fiscal al 13/04/2005
 *
 * ===========================================================================
 */
package com.epa.crprinterdriver.event;

/**
 * <p>
 * Interfaz para escucha de eventos de respuesta de impresora desde el 
 * intérprete de respuestas. Para uso interno del driver fiscal
 * </p>
 * <p>
 * <a href="ResponseListener.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - Arístides Castillo Colmenárez - $Author: programa8 $
 * @version $Revision: 1.2.2.3 $ - $Date: 2005/05/09 14:28:17 $
 * @since 13-abr-2005
 * 
 */
public interface ResponseListener {
	/**
	 * Método de atención de eventos de llegada de respuesta
	 * @param event Objeto de respuesta llegada desde impresora
	 */
	public void responseArrived(CRFPResponseEvent event);
}
