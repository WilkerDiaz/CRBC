/*
 * $Id: CajaFiscalDriver.java,v 1.2 2005/03/10 15:54:45 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.manejadorsesion
 * Programa		: CajaFiscalDriver.java
 * Creado por	: Programa8
 * Creado en 	: 03-mar-2005 14:29:29
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
 * $Log: CajaFiscalDriver.java,v $
 * Revision 1.2  2005/03/10 15:54:45  programa8
 * CR al 10/03/2005. Merging desde branch STABLE
 *
 * Revision 1.1.4.1  2005/03/07 13:24:18  programa8
 * Clase Wrapper para notificación de falta de papel en impresora fiscal
 *
 * Revision 1.1.2.1  2005/03/04 20:14:42  programa8
 * * Ajustes para trabajo sin administrador de ventanas
 * * Control de contadores de rollo de papel
 *
 * ===========================================================================
 */
package com.becoblohm.cr.manejadorsesion;

import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.crprinterdriver.CRFiscalPrinterOperations;
import com.epa.crprinterdriver.PrinterNotConnectedException;

/**
 * <pre>
 * Proyecto: CR 
 * Clase: CajaFiscalDriver
 * </pre>
 * <p>
 * <a href="CajaFiscalDriver.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - $Author: programa8 $
 * @version $Revision: 1.2 $ - $Date: 2005/03/10 15:54:45 $
 * @since 03-mar-2005
 * @
 */
public class CajaFiscalDriver extends CRFiscalPrinterOperations {

	/**
	 * @since 03-mar-2005
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @param arg4
	 * @param arg5
	 * @param arg6
	 */
	public CajaFiscalDriver(String arg0, String arg1, String arg2, String arg3,
			String arg4, String arg5, String arg6) {
		super(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
	}
	
	

	public void commit() throws PrinterNotConnectedException {
		if (isPocoPapelTicket()) {
			MensajesVentanas.aviso("Queda poco papel en el rollo de tickets");
		}
		if (isPocoPapelAuditoria()) {
			MensajesVentanas.aviso("Queda poco papel en el rollo de auditoría");
		}
		super.commit();
	}
}
