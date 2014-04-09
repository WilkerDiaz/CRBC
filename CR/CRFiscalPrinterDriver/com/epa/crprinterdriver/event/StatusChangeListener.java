/*
 * $Id: StatusChangeListener.java,v 1.2.2.3 2005/05/09 14:28:18 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CRFiscalPrinterDriver 1.1
 * Paquete		: com.epa.crprinterdriver.event
 * Programa		: StatusChangeListener.java
 * Creado por	: Programa8
 * Creado en 	: 11-abr-2005 14:23:42
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
 * $Log: StatusChangeListener.java,v $
 * Revision 1.2.2.3  2005/05/09 14:28:18  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.2  2005/05/09 14:18:54  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.1  2005/05/06 19:12:47  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.2  2005/05/05 12:08:43  programa8
 * Versión 2.0.1 Final
 *
 * Revision 1.1.2.1  2005/04/11 20:59:15  programa8
 * CRFiscalPrinterDriver 2.0 - Analisis Preliminar
 *
 * ===========================================================================
 */
package com.epa.crprinterdriver.event;

/**
 * <p>
 * Interfaz de escucha de cambios de estado en driver fiscal. Para uso interno del
 * driver fiscal
 * </p>
 * <p>
 * <a href="StatusChangeListener.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - Arístides Castillo Colmenárez -  $Author: programa8 $
 * @version $Revision: 1.2.2.3 $ - $Date: 2005/05/09 14:28:18 $
 * @since 11-abr-2005
 * 
 */
public interface StatusChangeListener {
	/**
	 * Metodo de atención de eventos de cambios de estado en driver fiscal
	 * @param paramChanged Tipo de parámetro cambiado en estado. Ver constantes CRFPStatus.PARAM_XXX
	 */
	public void changedStatus(int paramChanged);
}
