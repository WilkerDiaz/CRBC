/*
 * $Id: CargarDataFile.java,v 1.3 2005/06/02 13:12:00 programa4 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: GeneradorDataFile
 * Paquete		: com.epa.sincronizador.datafile
 * Programa		: CargarDataFile.java
 * Creado por	: programa4
 * Creado en 	: 19/01/2005 11:03:26 AM
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
 * $Log: CargarDataFile.java,v $
 * Revision 1.3  2005/06/02 13:12:00  programa4
 * Agregadas referencias this
 *
 * Revision 1.2  2005/04/04 19:49:29  programa4
 * Activado autoflush para los PrintStream
 *
 * Revision 1.1  2005/03/09 18:53:37  programa4
 * Reorganizadas las clases en paquetes, ahora hay un paquete distinto para el client y para el server
 *
 * Revision 1.9  2005/03/07 12:29:34  programa4
 * Configurado para ejecutar un solo script por archivo, y un scriptrunner que los va ejecutando en secuencia
 *
 * Revision 1.8  2005/03/04 15:19:35  programa4
 * Se mueven varias propiedades  a ser variables de objeto
 * Se definen varios nombres de propiedades como constantes en PropiedadesAplicacion
 *
 * Revision 1.7  2005/03/03 19:18:54  programa4
 * Se hicieron synchronized metodos de escrituras a archivos
 *
 * Revision 1.6  2005/03/03 17:44:16  programa4
 * Corregido problema de perdida de datos, este se causaba por que se creaba un BufferedReader
 * en cada llamada a obtenerDatos en CargarDataFile por ello ahora el BufferedReader se crea solo
 * desde la inicialización del CargarDataFile
 *
 * Revision 1.5  2005/03/03 13:15:18  programa4
 * Las propiedades del properties, se cargan como constantes
 *
 * Revision 1.4  2005/03/01 18:59:53  programa4
 * Se elimina el ThreadPool para poder controlar mejor si se actualiza exitosamente una tabla
 * Se baja totalmente un archivo, y el script va acumulando en una cola los subarchivos en
 * que se va seccionando.
 *
 * Revision 1.3  2005/02/24 21:33:35  programa4
 * Unificacion de propiedades
 *
 * Revision 1.2  2005/01/25 19:09:05  programa4
 * ObtenerDataFile funcional.
 * Actualmente funciona con ThreadPool. Detecta si es un archivo de texto y activa el script
 * para insertarlo.
 *
 * Se pueden determinar tamaño de buffer (cantidad de registros a procesar en bloque)
 *
 * El log esta configurado para enviar correos en errores
 *
 * Revision 1.1  2005/01/21 19:21:40  programa4
 * Version 2.0 del Generador Data File, ya puede funcionar con cualquier tabla
 *
 * ===========================================================================
 */
package com.epa.sincronizador.datafile.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

import org.apache.log4j.Logger;

import com.epa.data.script.SQLScript;
import com.epa.data.script.SQLScriptRunner;
import com.epa.sincronizador.datafile.DataFileDataSourceFactory;
import com.epa.sincronizador.datafile.PropiedadesAplicacion;

/**
 * Objeto que se encargara de cargar el DataFile
 * <p>
 * <a href="CargarDataFile.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.3 $ - $Date: 2005/06/02 13:12:00 $
 * @since 19/01/2005 @
 */
class CargarDataFile {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CargarDataFile.class);

    private static final int bloqueDebug = Integer
            .parseInt(PropiedadesAplicacion.getProperty(
                    PropiedadesAplicacion.BLOQUEDEBUG, "10000"));

    private static final int buffersize = Integer
            .parseInt(PropiedadesAplicacion.getProperty(
                    PropiedadesAplicacion.BUFFERSIZE, "0"));

    private static final boolean batchMode = PropiedadesAplicacion.getProperty(
            PropiedadesAplicacion.BATCH_MODE, "true").equalsIgnoreCase("true");

    private long numLineas = 0;

    /**
     * Objeto script que puede ser utilizado por clases herederas para
     * actualizar datos den una base de datos.
     */
    protected SQLScriptRunner runner;

    private BufferedReader myReader = null;

    /**
     * @since 03/03/2005
     * @param is
     */
    public CargarDataFile(InputStream is) {
        setReader(is);
    }

    /**
     * @since 03/03/2005
     * @param reader
     */
    public CargarDataFile(Reader reader) {
        setReader(reader);
    }

    /**
     * @param preReader
     */
    protected void setReader(Reader preReader) {
        BufferedReader reader = null;
        if (preReader instanceof BufferedReader) {
            reader = (BufferedReader) preReader;
        } else {
            reader = new BufferedReader(preReader);
        }
        this.myReader = reader;
    }

    /**
     * @param is
     */
    protected void setReader(InputStream is) {
        InputStreamReader isReader = new InputStreamReader(is);
        setReader(isReader);
    }

    /**
     * @param preWriter
     * @return <code>true</code> si hay mas datos pendientes por recibir
     * @throws Exception
     */
    public boolean getData(Writer preWriter) throws Exception {
        PrintWriter writer = null;
        if (preWriter instanceof PrintWriter) {
            writer = (PrintWriter) preWriter;
        } else if (preWriter instanceof BufferedWriter) {
            writer = new PrintWriter(preWriter, true);
        } else {
            writer = new PrintWriter(new BufferedWriter(preWriter), true);
        }

        return getData(writer);
    }

    /**
     * @param os
     * @return <code>true</code> si hay mas datos pendientes por recibir
     * @throws Exception
     */
    public boolean getData(OutputStream os) throws Exception {
        OutputStreamWriter osWriter = new OutputStreamWriter(os);
        return getData(osWriter);
    }

    /**
     * @param writer
     * @return <code>true</code> si hay mas datos pendientes por recibir
     * @throws Exception
     */
    protected synchronized boolean getData(PrintWriter writer) throws Exception {
        long t0 = System.currentTimeMillis();
        boolean isMoreData = false;

        String line;
        long numLineasThisWriter = 0;
        while ((line = this.myReader.readLine()) != null) {
            this.numLineas++;
            numLineasThisWriter++;
            printToWriter(writer, line);
            if (logger.isInfoEnabled()) {
                if (this.numLineas % bloqueDebug == 0) {
                    logger.info("VAN " + this.numLineas + " LINEAS");
                }
            }
            if (buffersize > 0 && numLineasThisWriter >= buffersize) {
                isMoreData = true;
                break;
            }
        }
        writer.flush();
        writer.close();
        long tf = System.currentTimeMillis();
        if (logger.isInfoEnabled()) {
            long dT = (tf - t0) / 1000;
            logger.info("TARDO: " + dT
                    + " segs. en transferir este bloque de datos.");
        }

        close(!isMoreData);
        return isMoreData;
    }

    /**
     * @param writer
     * @param line
     * @throws IOException
     * @throws Exception
     */
    protected synchronized void printToWriter(PrintWriter writer, String line)
            throws IOException, Exception {
        writer.println(line);
        if (writer.checkError()) {
            throw new IOException("Error escribiendo datos");
        }
    }

    /**
     * Realiza cualquier tarea postCierre
     * 
     * @param cerrarScript
     *            Indica si se debe cerrar el script
     * 
     * @throws Exception
     *             en caso de Cualquier excepcion
     */
    protected void close(boolean cerrarScript) throws Exception {
        if (cerrarScript) {
           this.runner.runScript();
        }
    }

    /**
     * @param nombre
     * @param script
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    protected void addScript(String nombre, SQLScript script)
            throws IOException, ClassNotFoundException, InstantiationException,
            IllegalAccessException {
        if (script != null) {
            if (this.runner == null) {
                this.runner = new SQLScriptRunner(nombre,
                        DataFileDataSourceFactory.getDataSourceClient());
                if (batchMode) {
                    this.runner.setBatchMode(true);
                } else {
                    this.runner.setBatchMode(false);
                }
            }
            this.runner.addScript(script);
        }
    }
}