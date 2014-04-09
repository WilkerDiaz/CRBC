/*
 * $Id: GZIPLoggeableOutputStream.java,v 1.3 2005/06/02 13:14:07 programa4 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: GeneradorDataFile
 * Paquete		: com.epa.sincronizador.datafile.streams
 * Programa		: GZIPLoggeableOutputStream.java
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
 * $Log: GZIPLoggeableOutputStream.java,v $
 * Revision 1.3  2005/06/02 13:14:07  programa4
 * Agregadas referencias this
 *
 * Revision 1.2  2005/03/30 17:50:06  programa4
 * Actualizacion de javadoc
 *
 * Revision 1.1  2005/03/09 18:53:35  programa4
 * Reorganizadas las clases en paquetes, ahora hay un paquete distinto para el client y para el server
 *
 * Revision 1.3  2005/03/04 15:11:02  programa4
 * Se sube de int a long las variables que cuentan el tamaño de información procesada
 *
 * Revision 1.2  2005/03/03 17:44:49  programa4
 * Corregido detalle cuando el la división para obtener % de compresión tenía denominador 0
 *
 * Revision 1.1  2005/01/21 19:21:48  programa4
 * Version 2.0 del Generador Data File, ya puede funcionar con cualquier tabla
 *
 * ===========================================================================
 */
package com.epa.sincronizador.datafile.streams;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * GZIPOutputStream que retorna información acerca de la razón de compresión.
 * <code>
 * Proyecto: GeneradorDataFile 
 * Clase: GZIPLoggeableOutputStream
 * </code>
 * <p>
 * <a href="GZIPLoggeableOutputStream.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.3 $ - $Date: 2005/06/02 13:14:07 $
 * @since 18/01/2005 @
 */
public class GZIPLoggeableOutputStream extends GZIPOutputStream implements
        GZIPLoggeable {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(GZIPLoggeableOutputStream.class);

    /**
     * @since 18/01/2005
     * @param output
     * @throws IOException
     * @see GZIPOutputStream#GZIPOutputStream(java.io.OutputStream)
     */
    public GZIPLoggeableOutputStream(OutputStream output) throws IOException {
        super(output);
    }

    /**
     * @since 18/01/2005
     * @param output
     * @param size
     * @throws IOException
     * @see GZIPOutputStream#GZIPOutputStream(java.io.OutputStream, int)
     */
    public GZIPLoggeableOutputStream(OutputStream output, int size)
            throws IOException {
        super(output, size);
    }

    /**
     * @return Cantidad de datos de entrada (sin comprimir)
     */
    public long getTotalIn() {
        return this.def.getTotalIn();
    }

    /**
     * @return Cantidad de datos de salida (comprimida)
     */
    public long getTotalOut() {
        return this.def.getTotalOut();
    }

    /**
     * @see java.util.zip.GZIPOutputStream#write(byte[], int, int)
     * @throws java.io.IOException
     * @since 18/01/2005
     */
    public synchronized void write(byte[] buf1, int off, int len)
            throws IOException {
        super.write(buf1, off, len);
        if (logger.isDebugEnabled()) {
            logger.debug(getInOutLog());
        }
    }

    /**
     * @return InOutLog Información acerca de la compresión
     */
    public String getInOutLog() {
        if (getTotalIn() > 0) {
            return "DATA INPUT: " + getTotalIn() + " \t DATA OUTPUT: "
                    + getTotalOut() + " \t DATA COMPRESS: "
                    + ((getTotalOut() * 100) / getTotalIn()) + "%";

        }
        return "DATA INPUT: " + getTotalIn() + " \t DATA OUTPUT: "
                + getTotalOut();
    }

}