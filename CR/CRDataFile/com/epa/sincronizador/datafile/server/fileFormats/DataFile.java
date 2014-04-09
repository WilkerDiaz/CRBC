/*
 * $Id: DataFile.java,v 1.2 2005/06/02 13:13:57 programa4 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: GeneradorDataFile
 * Paquete		: com.epa.sincronizador.datafile.fileFormats
 * Programa		: DataFile.java
 * Creado por	: programa4
 * Creado en 	: 18/01/2005 10:47:01 AM
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
 * $Log: DataFile.java,v $
 * Revision 1.2  2005/06/02 13:13:57  programa4
 * Agregadas referencias this
 *
 * Revision 1.1  2005/03/09 18:53:41  programa4
 * Reorganizadas las clases en paquetes, ahora hay un paquete distinto para el client y para el server
 *
 * Revision 1.6  2005/03/08 12:18:45  programa4
 * Cambiado lista de campos de hashset a linkedhashset para asegurar que se mantenga el orden
 *
 * Revision 1.5  2005/03/07 12:22:53  programa4
 * Optimizado para que se pueda especificar en  que orden se escribiran los campos
 *
 * Revision 1.4  2005/02/24 22:00:59  programa4
 * Eliminada condicion de eliminar al Salir que corresponde a carga de DataFile
 *
 * Revision 1.3  2005/02/24 21:33:39  programa4
 * Unificacion de propiedades
 *
 * Revision 1.2  2005/01/25 19:09:08  programa4
 * ObtenerDataFile funcional.
 * Actualmente funciona con ThreadPool. Detecta si es un archivo de texto y activa el script
 * para insertarlo.
 *
 * Se pueden determinar tamaño de buffer (cantidad de registros a procesar en bloque)
 *
 * El log esta configurado para enviar correos en errores
 *
 * Revision 1.1  2005/01/21 19:21:44  programa4
 * Version 2.0 del Generador Data File, ya puede funcionar con cualquier tabla
 *
 * ===========================================================================
 */
package com.epa.sincronizador.datafile.server.fileFormats;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.epa.sincronizador.datafile.PropiedadesAplicacion;

/**
 * <p>
 * Objeto que puede recibir información en forma de mapas y la almacena en un
 * archivo de datos para su exportación.
 * </p>
 * <p>
 * Con respecto bases de datos, se recomienda un datafile por tabla.
 * </p>
 * <code>
 * Proyecto: GeneradorDataFile 
 * Clase: DataFile
 * </code>
 * <p>
 * <a href="DataFile.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.2 $ - $Date: 2005/06/02 13:13:57 $
 * @since 18/01/2005 @
 */
public abstract class DataFile {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(DataFile.class);

    /**
     * <code>outputFile</code> Archivo de salida
     */
    protected File outputFile;

    /**
     * <code>foStream</code> OutputStream al archivo de salida
     */
    protected FileOutputStream foStream;

    /**
     * <code>output</code> PrintWriter al archivo de Salida
     */
    protected PrintWriter output = null;

    /**
     * <code>dataSize</code> Cantidad de información almacenada (bytes)
     */
    protected long dataSize = 1;

    /**
     * <code>item</code> Cantidad de items (registros) almacenados.
     */
    protected long item = 0;

    /**
     * Campos utilizados por los items
     */
    private Set<String> fieldsName = null;

    /**
     * Separador de Lineas
     */
    protected final static String LINESEPARATOR = System
            .getProperty("line.separator");

    /**
     * @since 19/01/2005
     * @param outFile
     *            nombre/ruta del archivo de salida.
     * @throws IOException
     */
    public DataFile(String outFile) throws IOException {
        initDataFile(new File(outFile));
    }

    /**
     * @since 19/01/2005
     * @param outFile
     *            archivo de salida
     * @throws IOException
     */
    public DataFile(File outFile) throws IOException {
        initDataFile(outFile);
    }

    /**
     * Inicializa el archivo de salida, creando los OutputStream. En caso de
     * existir el archivo, lo elimina para crearlo de nuevo.
     * 
     * @param outFile
     *            archivo de salida
     * @throws IOException
     * @throws FileNotFoundException
     */
    protected void initDataFile(File outFile) throws IOException,
            FileNotFoundException {
        String directory = PropiedadesAplicacion.getProperty("DataFile.dir",
                System.getProperty("user.dir"));
        this.outputFile = new File(directory, outFile.getName());
        if (this.outputFile.exists()) {
            if (!this.outputFile.delete()) {
                throw new IOException("NO SE PUDO BORRAR ARCHIVO: "
                        + outFile.getAbsolutePath());
            }
            if (!this.outputFile.createNewFile()) {
                throw new IOException("NO SE PUDO CREAR ARCHIVO: "
                        + outFile.getAbsolutePath());
            }
        }
        this.foStream = new FileOutputStream(this.outputFile);
        this.dataSize = 1;
        this.item = 0;
        if (logger.isInfoEnabled()) {
            logger.info("ASIGNADO WRITER A: "
                    + this.outputFile.getAbsolutePath());
        }
    }

    /**
     * @return Tamaño que lleva archivo de salida.
     */
    public long getFileSize() {
        if (this.outputFile == null) {
            return -1;
        }
        return this.outputFile.length();
    }

    /**
     * Información acerca de la cantidad de información procesada.
     * 
     * @return InOutLog
     */
    public abstract String getInOutLog();

    /**
     * Agrega el string al output
     * 
     * @param string
     */
    protected void print(String string) {
        this.output.print(string);
        this.dataSize += (string.getBytes().length);
    }

    /**
     * agrega un string y un salto de linea al Output
     * 
     * @param string
     */
    protected void println(String string) {
        this.output.println(string);
        this.dataSize += (string.getBytes().length);
    }

    /**
     * Las clases que hereden de esta clase deben implementar este metodo para
     * imprimir el mapa en un formato deseado.
     * 
     * @param nombreTabla
     * @param datos
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Map'
	* Fecha: agosto 2011
	*/
    public abstract void printMap(String nombreTabla, Map<String,Object> datos);

    /**
     * Imprime un conjunto de mapas en un solo lote.
     * 
     * @param nombreTabla
     * @param datos
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Map'
	* Fecha: agosto 2011
	*/
    public void printMapArray(String nombreTabla, Map<String,Object> datos[]) {
        for (int i = 0; i < datos.length; i++) {
            printMap(nombreTabla, datos[i]);
        }
    }

    /**
     * @return Devuelve un <code>Set</code> con los nombres de los campos.
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Set'
	* Fecha: agosto 2011
	*/
    public Set<String> getFieldsName() {
        return this.fieldsName;
    }

    /**
     * @param set
     *            El fieldsName a establecer.
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Set'
	* Fecha: agosto 2011
	*/
    protected void setFieldsName(Set<String> set) {
        this.fieldsName = set;
        if (logger.isInfoEnabled()) {
            logger.info("Reasignado fieldsName: " + set.getClass().getName()
                    + set);
        }
    }

    /**
     * Asigna una lista de campos a partir de un array de String
     * 
     * @param fields
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Set' y 'LinkedHashSet'
	* Fecha: agosto 2011
	*/
    public void setFieldsName(String[] fields) {
        Set<String> fieldSet = new LinkedHashSet<String>(fields.length);
        for (int i = 0; i < fields.length; i++) {
            String string = fields[i];
            if (string != null) {
                fieldSet.add(string.trim().toLowerCase());
            }
        }
        setFieldsName(fieldSet);
    }

    /*
     * (sin Javadoc)
     * 
     * @see java.lang.Object#finalize() @throws java.lang.Throwable
     * @since 23/12/2004
     */
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }

    /**
     * Hace un flush y close del OutputStream.
     */
    public void close() {
        this.output.flush();
        this.output.close();
    }

}