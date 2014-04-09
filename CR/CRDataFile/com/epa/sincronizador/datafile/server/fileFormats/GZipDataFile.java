/*
 * $Id: GZipDataFile.java,v 1.3 2005/06/02 13:13:53 programa4 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: ColaCR
 * Paquete		: com.epa.sincronizador.datafile
 * Programa		: GZipDataFile.java
 * Creado por	: programa4
 * Creado en 	: 23/12/2004 01:03:17 PM
 * (C) Copyright 2004 SuperFerretería EPA C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisión	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: GZipDataFile.java,v $
 * Revision 1.3  2005/06/02 13:13:53  programa4
 * Agregadas referencias this
 *
 * Revision 1.2  2005/04/04 19:50:57  programa4
 * Aplicado formato a javadoc
 *
 * Revision 1.1  2005/03/09 18:53:41  programa4
 * Reorganizadas las clases en paquetes, ahora hay un paquete distinto para el client y para el server
 *
 * Revision 1.1  2005/01/21 19:21:44  programa4
 * Version 2.0 del Generador Data File, ya puede funcionar con cualquier tabla
 *
 * Revision 1.1  2004/12/30 19:10:18  programa4
 * Version Inicial. Contiene:
 * MySQLDataFile, que genera un archivo comprimido con data lista para ser cargada por el MySQL
 * GenerarScriptAfiliados, que contiene los metodos y sentencias para cargar una tabla TEMP_AFILIADOS con los datos de todos los afiliados
 * TEMP_AFILIADO.sql, que contiene las rutinas para generar la tabla TEMP_AFILIADOS
 *
 * + Pendiente: pruebas en AS400 y metodos para obtener conexion.
 *
 * Revision 1.2  2004/12/27 22:24:47  programa4
 * Lista implementacion que genera data file para ser importado en MySQL
 * Se logro un tiempo de generacion del .gz de 8 minutos para 1.000.000 de registros
 * de afiliados
 *
 * Revision 1.1  2004/12/23 18:54:21  programa4
 * Agregados nuevas consultas
 * Iniciado trabajo ed GZipDataFile
 *
 * ===========================================================================
 */
package com.epa.sincronizador.datafile.server.fileFormats;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import com.epa.sincronizador.datafile.streams.GZIPLoggeableOutputStream;

/**
 * Data File que almacena información comprimida en formato GZip <code>
 *     Proyecto: ColaCR 
 *     Clase: GZipDataFile
 * </code>
 * 
 * <p>
 * <a href="GZipDataFile.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.3 $ - $Date: 2005/06/02 13:13:53 $
 * @since 23/12/2004
 */
public abstract class GZipDataFile extends DataFile {
    private GZIPLoggeableOutputStream gzipOS;

    /**
     * Crea un GZipDataFile con el nombre dado. Se le anexa la extensión .gz al
     * archivo
     * 
     * @since 19/01/2005
     * @param outFile
     *            Nombre del archivo.
     * @throws IOException
     */
    public GZipDataFile(String outFile) throws IOException {
        super(outFile + ".gz");
        initGZipFile();
    }

    /**
     * Crea un GZipDataFile con el archivo dado. No se anexa ninguna extensión.
     * 
     * @since 19/01/2005
     * @param outFile
     *            Archivo de salida
     * @throws IOException
     */
    public GZipDataFile(File outFile) throws IOException {
        super(new File(outFile.getAbsolutePath()));
        initGZipFile();
    }

    /**
     * Inicializa GZipDataFile
     * 
     * @throws IOException
     */
    private void initGZipFile() throws IOException {
        this.gzipOS = new GZIPLoggeableOutputStream(this.foStream);
        this.output = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(this.gzipOS)), true);
    }

    /**
     * Retorna información acerca de la razón de compresión.
     * 
     * @return InOutLog información acerca de la razón de compresión.
     */
    public String getInOutLog() {
        return this.gzipOS.getInOutLog();
    }
}