/*
 * $Id: CargarTextDataFile.java,v 1.7 2005/11/02 21:15:07 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: GeneradorDataFile
 * Paquete		: com.epa.sincronizador.datafile
 * Programa		: CargarScriptDataFile.java
 * Creado por	: programa4
 * Creado en 	: 19/01/2005 01:39:55 PM
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
 * $Log: CargarTextDataFile.java,v $
 * Revision 1.7  2005/11/02 21:15:07  programa8
 * Permitir ejecución de sentencias personalizadas al finalizar carga de datos en archivos temporales
 *
 * Revision 1.6  2005/06/02 14:11:08  programa4
 * Corregido bug, en que se verificaba incorrectamente que propiedad
 * this.campos fuera null, cuando se debia verificar era el parametro
 *
 * Revision 1.5  2005/06/02 13:11:10  programa4
 * Agregadas validaciones y sincronizacion a metodos para lectura y obtencion
 * de valores de lista de campos
 *
 * Revision 1.4  2005/04/14 16:22:16  programa4
 * Se pone en comentario
 * la desactivacion de indices y el optimize table
 * Se elminia el addfile2 script en el init, ya que es probable que aun no se conozcan los campos
 *
 * Revision 1.3  2005/04/04 19:49:07  programa4
 * Toma en cuenta los indices y restricciones referenciales en tablas temporales
 * creadas para las tablas innodb
 *
 * Revision 1.2  2005/03/30 17:50:06  programa4
 * Actualizacion de javadoc
 *
 * Revision 1.1  2005/03/09 18:53:38  programa4
 * Reorganizadas las clases en paquetes, ahora hay un paquete distinto para el client y para el server
 *
 * Revision 1.11  2005/03/08 23:57:15  programa4
 * Configurado para poder trabajar con archivos temporales intermedios.
 * Este será el esquema a utilizar con afiliado. donde se anexan lso registros a una tabla cuyo nombre
 * termine en _temp y que una vez completado el proceso se borra la tabla original y se sustituye
 * por la _temp
 *
 * Revision 1.10  2005/03/08 18:49:45  programa4
 * Se volvió a activar que soporte un solo script para todos los archivos
 *
 * Revision 1.9  2005/03/07 12:29:34  programa4
 * Configurado para ejecutar un solo script por archivo, y un scriptrunner que los va ejecutando en secuencia
 *
 * Revision 1.8  2005/03/04 15:12:01  programa4
 * Reajustado orden del script y se verifica que el archivo contenga datos
 * antes de solicitar la operacion
 *
 * Revision 1.7  2005/03/03 19:19:33  programa4
 * Se cambio el orden de las sentencias en el script
 *
 * Revision 1.6  2005/03/03 17:44:16  programa4
 * Corregido problema de perdida de datos, este se causaba por que se creaba un BufferedReader
 * en cada llamada a obtenerDatos en CargarDataFile por ello ahora el BufferedReader se crea solo
 * desde la inicialización del CargarDataFile
 *
 * Revision 1.5  2005/03/03 15:00:26  programa4
 * Cambiado orden de metodos
 *
 * Revision 1.4  2005/03/03 13:18:04  programa4
 * Se mudan constantes que indican nombres propiedades a PropiedadesAplicacion,
 * Se corrigió que anteriormente no se arrojaba la excepción cuando se ejecutaba algún script
 * en CargarScriptDataFile
 *
 * Revision 1.3  2005/03/01 18:59:53  programa4
 * Se elimina el ThreadPool para poder controlar mejor si se actualiza exitosamente una tabla
 * Se baja totalmente un archivo, y el script va acumulando en una cola los subarchivos en
 * que se va seccionando.
 *
 * Revision 1.2  2005/02/24 21:33:36  programa4
 * Unificacion de propiedades
 *
 * Revision 1.1  2005/01/25 19:09:06  programa4
 * ObtenerDataFile funcional.
 * Actualmente funciona con ThreadPool. Detecta si es un archivo de texto y activa el script
 * para insertarlo.
 *
 * Se pueden determinar tamaño de buffer (cantidad de registros a procesar en bloque)
 *
 * El log esta configurado para enviar correos en errores
 *
 * Revision 1.1  2005/01/21 19:21:41  programa4
 * Version 2.0 del Generador Data File, ya puede funcionar con cualquier tabla
 *
 * ===========================================================================
 */
package com.epa.sincronizador.datafile.client;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.epa.data.script.SQLScript;
import com.epa.sincronizador.datafile.PropiedadesAplicacion;

/**
 * <code>
 *           Proyecto: GeneradorDataFile 
 *           Clase: CargarScriptDataFile
 * </code>
 * 
 * <p>
 * <a href="CargarScriptDataFile.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author programa4 - $Author: programa8 $
 * @version $Revision: 1.7 $ - $Date: 2005/11/02 21:15:07 $
 * @since 19/01/2005 @
 */
class CargarTextDataFile extends CargarDataFile {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(CargarTextDataFile.class);

    /**
     * <code>MODO_REPLACE</code> un <code>CargarTextDataFile</code>
     */
    public static final String MODO_REPLACE = "REPLACE";

    /**
     * <code>MODO_IGNORE</code> un <code>CargarTextDataFile</code>
     */
    public static final String MODO_IGNORE = "IGNORE";

    private volatile String campos;

    private String table = null;

    private String realTable = null;
    
    private String tableName = null;

    private File file = null;

    private String modo = null;

    private SQLScript script = null;

    private String createTable = null;

    /**
     * @param reader
     * @param tableName
     *            nombre de Tabla
     * @param fileName
     *            nombre de Archivo
     * @param modoDuplicados
     *            Modo de manejar los duplicados, puede ser IGNORE (ignora los
     *            datos) o REPLACE (reemplaza los datos)
     * @param createScript
     * @since 19/01/2005
     */
    public CargarTextDataFile(Reader reader, String tableName, File fileName,
            String modoDuplicados, String createScript) {
        super(reader);
        init(tableName, fileName, modoDuplicados, createScript);
    }

    /**
     * @param is
     * @param tableName
     *            nombre de Tabla
     * @param fileName
     *            nombre de Archivo
     * @param modoDuplicados
     *            Modo de manejar los duplicados, puede ser IGNORE (ignora los
     *            datos) o REPLACE (reemplaza los datos)
     * @param createScript
     * @since 19/01/2005
     */
    public CargarTextDataFile(InputStream is, String tableName, File fileName,
            String modoDuplicados, String createScript) {
        super(is);
        init(tableName, fileName, modoDuplicados, createScript);
    }

    /**
     * @param tableName
     *            nombre de Tabla
     * @param fileName
     *            nombre de Archivo
     * @param modoDuplicados
     *            Modo de manejar los duplicados, puede ser IGNORE (ignora los
     *            datos) o REPLACE (reemplaza los datos)
     * @param createScript
     */
    private void init(String tableName, File fileName, String modoDuplicados,
            String createScript) {
        this.file = fileName;
        this.createTable = createScript;
        this.tableName = tableName;
        if (this.createTable != null
                && PropiedadesAplicacion.getProperty(
                        tableName + ".MySQL.temporarymode", "false")
                        .equalsIgnoreCase("true")) {
            this.realTable = tableName.substring(tableName.indexOf(".") + 1);
            this.table = this.realTable + "_temp";
        } else {
            this.table = tableName;
            this.realTable = null;
        }
        if (modoDuplicados.equals(MODO_IGNORE)
                || modoDuplicados.equals(MODO_REPLACE)) {
            this.modo = modoDuplicados;
        } else {
            logger.warn("Modo de manejar duplicados desconocido: "
                    + modoDuplicados + ", se habilitara modo IGNORE");
            this.modo = MODO_IGNORE;
        }
    }

    /**
     * Agrega un archivo al script
     */
    private void addFile2Script() {
        if (this.file.exists() && this.file.length() > 0) {
            if (this.script == null) {
                initScript();
            }
            if (this.getCampos() == null) {
                throw new IllegalArgumentException(
                        "Campos tiene un valor nulo.");
            }
            String sentencia ="LOAD DATA INFILE '"
                + StringEscapeUtils.escapeJava(this.file.getAbsolutePath())
                + "' " + this.modo + " INTO TABLE  " + this.table + " ("
                + this.getCampos() + ");";
            this.script.addSentencia(sentencia);
        }
    }

    /**
     */
    private void initScript() {
        this.script = new SQLScript();
        if (this.realTable != null) {
            this.script
                    .addSentencia("DROP TABLE IF EXISTS " + this.table + ";");
            String newCreateTable = this.createTable.replaceAll("`"
                    + this.realTable + "`", "`" + this.table + "`")
                    + ";";
            if (newCreateTable.indexOf("CONSTRAINT `new_") > 0) {
                newCreateTable = newCreateTable.replaceAll("CONSTRAINT `new_",
                        "CONSTRAINT `");
            } else if (newCreateTable.indexOf("CONSTRAINT `") > 0) {
                newCreateTable = newCreateTable.replaceAll("CONSTRAINT `",
                        "CONSTRAINT `new_");
            }

            this.script.addSentencia(newCreateTable);
        }
        this.script.addSentencia("SET AUTOCOMMIT=0;");
        this.script.addSentencia("SET FOREIGN_KEY_CHECKS = 0;");
        this.script.addSentencia("SET UNIQUE_CHECKS = 0;");
        this.script.addSentencia("FLUSH TABLES;");
        this.script.addSentencia("START TRANSACTION;");
    }

    /**
     */
    private void endScript() {
        this.script.addSentencia("COMMIT;");
        if (this.realTable != null) {
            if (PropiedadesAplicacion.getProperty(
                        tableName + ".MySQL.execstmtonend", "false")
                .equalsIgnoreCase("true")) {
                this.script.addSentencia(PropiedadesAplicacion.getProperty(
                        tableName + ".MySQL.stmtonend"));
            }
            this.script.addSentencia("DROP TABLE IF EXISTS " + this.realTable
                    + ";");
            this.script.addSentencia("RENAME TABLE " + this.table + " TO "
                    + this.realTable + ";");
        }
        this.script.addSentencia("SET UNIQUE_CHECKS = 1;");
        this.script.addSentencia("SET FOREIGN_KEY_CHECKS = 1;");
        this.script.addSentencia("FLUSH TABLES;");
    }

    /*
     * (sin Javadoc)
     * 
     * @see com.epa.sincronizador.datafile.CargarDataFile#printToWriter(java.io.PrintWriter,
     *      java.lang.String) @param writer @param line
     * @since 24/01/2005
     */
    protected synchronized void printToWriter(PrintWriter writer, String line)
            throws Exception {
        if (line != null) {
            if (this.getCampos() == null) {
                this.setCampos(line.trim().replace('\t', ','));
                if (logger.isInfoEnabled()) {
                    logger.info("Asignado campos: " + this.getCampos());
                }
            } else {
                super.printToWriter(writer, line);
            }
        }
    }

    /*
     * (sin Javadoc)
     * 
     * @see com.epa.sincronizador.datafile.CargarDataFile#commonClose()
     * 
     * @since 24/01/2005
     */
    protected void close(boolean cerrarScript) throws Exception {
        addFile2Script();
        if (script != null) {
            if (cerrarScript) {
                endScript();
                addScript("DATAFILE: " + this.getFile().getName(), this.script);
                logger.info("Script Asignado: " + this.script);
                this.script = null;
            }
            super.close(cerrarScript);
        }
    }

    /**
     * @return Devuelve file.
     */
    public File getFile() {
        return this.file;
    }

    /**
     * @param textFile
     *            El file a establecer.
     */
    public void setFile(File textFile) {
        this.file = textFile;
    }

    /**
     * @return <code>String</code> Devuelve valor de campos.
     */
    private synchronized final String getCampos() {
        return this.campos;
    }

    /**
     * @param _campos
     *            El valor de campos a establecer.
     */
    private synchronized final void setCampos(String _campos) {
        if (_campos == null) {
            throw new IllegalArgumentException("Campos no puede ser nulo");
        }
        this.campos = _campos;
    }
}