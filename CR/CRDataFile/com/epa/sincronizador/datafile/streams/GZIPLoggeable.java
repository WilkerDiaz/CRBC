/*
 * $Id: GZIPLoggeable.java,v 1.1 2005/03/09 18:53:35 programa4 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: GeneradorDataFile
 * Paquete		: com.epa.sincronizador.datafile.streams
 * Programa		: GZIPLoggeable.java
 * Creado por	: programa4
 * Creado en 	: 18/01/2005 02:04:28 PM
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
 * $Log: GZIPLoggeable.java,v $
 * Revision 1.1  2005/03/09 18:53:35  programa4
 * Reorganizadas las clases en paquetes, ahora hay un paquete distinto para el client y para el server
 *
 * Revision 1.2  2005/03/04 15:11:02  programa4
 * Se sube de int a long las variables que cuentan el tamaño de información procesada
 *
 * Revision 1.1  2005/01/21 19:21:48  programa4
 * Version 2.0 del Generador Data File, ya puede funcionar con cualquier tabla
 *
 * ===========================================================================
 */
package com.epa.sincronizador.datafile.streams;


/**
 * Metodos que proporcionan información acerca de la compresión.
 * <pre>
 * Proyecto: GeneradorDataFile 
 * Clase: GZIPLoggeable
 * </pre>
 * <p>
 * <a href="GZIPLoggeable.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.1 $ - $Date: 2005/03/09 18:53:35 $
 * @since 18/01/2005
 * @
 */
public interface GZIPLoggeable {
    /**
     * @return Bytes de datos de entrada
     */
    public long getTotalIn();

    /**
     * @return Bytes de datos de salida
     */
    public long getTotalOut();
    
    /**
     * @return InOutLog
     */
    public String getInOutLog();

}