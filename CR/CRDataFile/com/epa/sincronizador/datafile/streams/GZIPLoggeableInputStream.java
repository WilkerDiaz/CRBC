/*
 * $Id: GZIPLoggeableInputStream.java,v 1.3 2005/06/02 13:14:08 programa4 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: GeneradorDataFile
 * Paquete		: com.epa.sincronizador.datafile.streams
 * Programa		: GZIPLoggeableInputStream.java
 * Creado por	: programa4
 * Creado en 	: 18/01/2005 11:09:36 AM
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
 * $Log: GZIPLoggeableInputStream.java,v $
 * Revision 1.3  2005/06/02 13:14:08  programa4
 * Agregadas referencias this
 *
 * Revision 1.2  2005/03/30 17:50:06  programa4
 * Actualizacion de javadoc
 *
 * Revision 1.1  2005/03/09 18:53:35  programa4
 * Reorganizadas las clases en paquetes, ahora hay un paquete distinto para el client y para el server
 *
 * Revision 1.3  2005/03/04 15:11:01  programa4
 * Se sube de int a long las variables que cuentan el tamaño de información procesada
 *
 * Revision 1.2  2005/03/03 17:44:49  programa4
 * Corregido detalle cuando el la división para obtener % de compresión tenía denominador 0
 *
 * Revision 1.1  2005/01/21 19:21:49  programa4
 * Version 2.0 del Generador Data File, ya puede funcionar con cualquier tabla
 *
 * ===========================================================================
 */
package com.epa.sincronizador.datafile.streams;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 * GZIPInputStream que retorna información acerca de la razón de compresión.
 * <code>
 * Proyecto: GeneradorDataFile 
 * Clase: GZIPLoggeableInputStream
 * </code>
 * <p>
 * <a href="GZIPLoggeableInputStream.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.3 $ - $Date: 2005/06/02 13:14:08 $
 * @since 18/01/2005 @
 */
public class GZIPLoggeableInputStream extends GZIPInputStream implements
        GZIPLoggeable {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(GZIPLoggeableInputStream.class);

    /**
     * @since 18/01/2005
     * @param input
     * @throws IOException
     * @see GZIPInputStream#GZIPInputStream(java.io.InputStream)
     */
    public GZIPLoggeableInputStream(InputStream input) throws IOException {
        super(input);
    }

    /**
     * @since 18/01/2005
     * @param input
     * @param size
     * @throws IOException
     * @see GZIPInputStream#GZIPInputStream(java.io.InputStream, int)
     */
    public GZIPLoggeableInputStream(InputStream input, int size)
            throws IOException {
        super(input, size);
    }

    /**
     * @return Cantidad de datos de entrada (comprimida)
     */
    public long getTotalIn() {
        return this.inf.getTotalIn();
    }

    /**
     * @return Cantidad de datos de salida (sin comprimir)
     */
    public long getTotalOut() {
        return this.inf.getTotalOut();
    }

    /**
     * @param buf1
     * @param off1
     * @param len1
     * @return
     * @throws IOException
     * @see java.util.zip.GZIPInputStream#read(byte[], int, int) 
     * @since 18/01/2005
     */
    public synchronized int read(byte[] buf1, int off1, int len1)
            throws IOException {
        int salida = super.read(buf1, off1, len1);
        if (logger.isDebugEnabled()) {
            logger.debug(getInOutLog());
        }
        return salida;
    }

    /**
     * @return InOutLog Información acerca de la compresión
     */
    public String getInOutLog() {
        if (getTotalOut() > 0) {
            return "DATA INPUT: " + getTotalIn() + " \t DATA OUTPUT: "
                    + getTotalOut() + " \t DATA COMPRESS: "
                    + (getTotalIn() * 100 / getTotalOut()) + "%";
        }
        return "DATA INPUT: " + getTotalIn() + " \t DATA OUTPUT: "
                + getTotalOut();
    }
}