/*
 * $Id: SerialSignalListener.java,v 1.1 2005/04/21 13:47:06 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CRSerialInterface
 * Paquete		: com.epa.crserialinterface
 * Programa		: SerialListener.java
 * Creado por	: Programa8
 * Creado en 	: 11-abr-2005 8:45:42
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
 * $Log: SerialSignalListener.java,v $
 * Revision 1.1  2005/04/21 13:47:06  programa8
 * Agregado manejo de eventos y escuchas en llegadas de señales seriales
 *
 * ===========================================================================
 */
package com.epa.crserialinterface;

import gnu.io.SerialPortEvent;


/**
 * <pre>
 * Proyecto: CRSerialInterface 
 * Clase: SerialListener
 * </pre>
 * <p>
 * <a href="SerialListener.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - $Author: programa8 $
 * @version $Revision: 1.1 $ - $Date: 2005/04/21 13:47:06 $
 * @since 11-abr-2005
 * @
 */
public interface SerialSignalListener {
	public void signalReceived(SerialPortEvent event);
}
