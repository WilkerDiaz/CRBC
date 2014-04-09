/*
 * $Id: SerialExceptions.java,v 1.3 2005/03/09 20:50:05 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerreter�a EPA C.A. 
 *
 * Proyecto		: CRSerialInterface
 * Paquete		: com.epa.crserialinterface
 * Programa		: SerialExceptions.java
 * Creado por	: Victor Medina <linux@epa.com.ve>
 * Creado en 	: Mar 27, 2004 9:29:14 AM
 * (C) Copyright 2004 SuperFerreter�a EPA C.A. Todos los Derechos Reservados
 * 
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: SerialExceptions.java,v $
 * Revision 1.3  2005/03/09 20:50:05  programa8
 * CRSerialInterface al 09/03/2005
 *
 * Revision 1.1  2004/07/05 18:42:35  vmedina
 * *** empty log message ***
 *
 * Revision 1.2  2004/03/29 19:45:32  vmedina
 * *** empty log message ***
 *
 * Revision 1.1  2004/03/29 19:40:11  vmedina
 * Entrada Inicial al CVS, solo tienen las estructuras basicas y los algoritmos principales
 * Falta comenzar la Integraci�n
 *
 * ===========================================================================
 */
package com.epa.crserialinterface;

/**
 * <p>SerialExceptions es el controlador de las excepciones de com.epa.crserialinterface</p>
 * @author $Author: programa8 $  
 * @version $Revision: 1.3 $<br>$Date: 2005/03/09 20:50:05 $
 * <!-- A continuaci�n indicar referencia a otras p�ginas o clases parecidas -->
 * <!-- o relacionados, pundiendo indicar inclusive los m�todos relacionados -->
 * <!-- por ejemplo: @see     <a href="package-summary.html#charenc">Character encodings</a> -->
 * <!-- por ejemplo: @see     java.lang.Object 								 -->
 * <!-- por ejemplo: @see     java.lang.Object#toNewString() 				 -->
 * @since   CRSerialInterface 1.0
 */
public class SerialExceptions extends Exception
{
	public SerialExceptions(String str) {
		super(str);
	}
	public SerialExceptions() {
		super();
	}
}
