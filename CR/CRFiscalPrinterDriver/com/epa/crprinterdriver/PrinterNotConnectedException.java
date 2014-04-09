/*
 * $Id: PrinterNotConnectedException.java,v 1.2.4.1 2005/05/09 14:19:04 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerreter�a EPA C.A. 
 *
 * Proyecto		: CRPrinterDriver
 * Paquete		: com.epa.crprinterdriver
 * Programa		: PrinterNotConnected.java
 * Creado por	: Victor Medina - linux@epa.com.ve
 * Creado en 	: Jun 2, 2004 1:46:30 PM
 * (C) Copyright 2004 SuperFerreter�a EPA C.A. Todos los Derechos Reservados
 * 
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: PrinterNotConnectedException.java,v $
 * Revision 1.2.4.1  2005/05/09 14:19:04  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2  2005/02/02 20:28:14  acastillo
 * Limpieza de c�digo. 0 trailing todo's
 *
 * Revision 1.1  2005/02/01 20:31:41  acastillo
 * CRFiscalPrinterDriver 1.1
 *
 * Revision 1.1  2004/06/03 19:48:59  vmedina
 * *** empty log message ***
 *
 * ===========================================================================
 */
package com.epa.crprinterdriver;


/**
 * <p>Indicar aqu�, en una l�nea que hace la clase</p>
 * <p>Agregar parrafos que describen como se usa la Clase y un resumen de como 
 * funciona.</p>
 * @author $Author: programa8 $  
 * @version $Revision: 1.2.4.1 $<br>$Date: 2005/05/09 14:19:04 $
 * <!-- A continuaci�n indicar referencia a otras p�ginas o clases parecidas -->
 * <!-- o relacionados, pundiendo indicar inclusive los m�todos relacionados -->
 * <!-- por ejemplo: @see     <a href="package-summary.html#charenc">Character encodings</a> -->
 * <!-- por ejemplo: @see     java.lang.Object 								 -->
 * <!-- por ejemplo: @see     java.lang.Object#toNewString() 				 -->
 *  * @since   <!-- Indique desde que versi�n del Proyecto existe esta clase 	 -->
 */
public class PrinterNotConnectedException extends Exception {

    /*
     * ---------------------------------------------------------------------------
     * PrinterNotConnected()
     * Creado por: 	Victor Medina - linux@epa.com.ve
     * Creado el:	Jun 2, 2004 1:46:30 PM
     * ---------------------------------------------------------------------------
     */
    public PrinterNotConnectedException() {
        super();
    }

    /*
     * ---------------------------------------------------------------------------
     * PrinterNotConnected()
     * Creado por: 	Victor Medina - linux@epa.com.ve
     * Creado el:	Jun 2, 2004 1:46:30 PM
     * ---------------------------------------------------------------------------
     */
    public PrinterNotConnectedException(String message) {
        super(message);
    }

    /*
     * ---------------------------------------------------------------------------
     * PrinterNotConnected()
     * Creado por: 	Victor Medina - linux@epa.com.ve
     * Creado el:	Jun 2, 2004 1:46:30 PM
     * ---------------------------------------------------------------------------
     */
    public PrinterNotConnectedException(Throwable cause) {
        super(cause);
    }

    /*
     * ---------------------------------------------------------------------------
     * PrinterNotConnected()
     * Creado por: 	Victor Medina - linux@epa.com.ve
     * Creado el:	Jun 2, 2004 1:46:30 PM
     * ---------------------------------------------------------------------------
     */
    public PrinterNotConnectedException(String message, Throwable cause) {
        super(message, cause);
    }

}
