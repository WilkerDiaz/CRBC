/*
 * $Id: SQLScript.java,v 1.7 2005/03/30 17:51:02 programa4 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: ColaCR
 * Paquete		: com.epa.sincronizador.datafile
 * Programa		: SQLScript.java
 * Creado por	: programa4
 * Creado en 	: 29/12/2004 08:15:37 PM
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
 * $Log: SQLScript.java,v $
 * Revision 1.7  2005/03/30 17:51:02  programa4
 * Actualizacion de javadoc
 *
 * Revision 1.6  2005/03/10 13:26:10  programa4
 * Documentacion
 *
 * Revision 1.5  2005/03/09 00:04:31  programa4
 * Optimizado logger de executeAll
 *
 * Revision 1.4  2005/03/04 15:08:58  programa4
 * Se activa variable para activar o desactivar el autocommit en el script
 *
 * Revision 1.3  2005/03/03 14:58:49  programa4
 * Agregado logger de tiempo a executeAll para ver cuanto tarda cada sentencia
 *
 * Revision 1.2  2005/03/01 18:44:07  programa4
 * Se activa un relanzamiento de la excepcion que se necesita para saber si ocurrio algún problema
 *
 * Revision 1.1  2005/01/31 23:37:02  programa4
 * Proyecto con utilidades varias para otros proyectos
 *
 * Revision 1.1  2005/01/21 19:21:41  programa4
 * Version 2.0 del Generador Data File, ya puede funcionar con cualquier tabla
 *
 * Revision 1.3  2005/01/06 23:01:22  programa4
 * Version funcional del generador de afiliados
 *
 * Revision 1.2  2004/12/31 00:26:06  programa4
 * Agregado metodos de obtener conexion
 * Y para leer archivos de inputstream
 *
 * Revision 1.1  2004/12/30 19:10:19  programa4
 * Version Inicial. Contiene:
 * MySQLDataFile, que genera un archivo comprimido con data lista para ser cargada por el MySQL
 * GenerarScriptAfiliados, que contiene los metodos y sentencias para cargar una tabla TEMP_AFILIADOS con los datos de todos los afiliados
 * TEMP_AFILIADO.sql, que contiene las rutinas para generar la tabla TEMP_AFILIADOS
 *
 * + Pendiente: pruebas en AS400 y metodos para obtener conexion.
 *
 * Revision 1.1  2004/12/30 02:58:40  programa4
 * Optimizada consulta, generado SQLScript que le un archivo de script y da la opcion
 * de ejecutarlo en batch
 *
 * ===========================================================================
 */
package com.epa.data.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * <p>
 * Clase que puede leer y ejecutar un Script de SQL.
 * </p>
 * <p>
 * El archivo de Script se puede cargar al inicio de la clase. Adicionalmente se pueden ir agregando sentencias.
 * </p>
 * <p>
 * Se proporcionan metodos helper de creación a partir de <code>URL</code> y
 * de <code>File</code>.
 * </p>
 * 
 * Proyecto: ColaCR <br>
 * Clase: SQLScript
 * <p>
 * <a href="SQLScript.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.7 $ - $Date: 2005/03/30 17:51:02 $
 * @since 29/12/2004
 * @see URL#openStream()
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato de los distintos ArrayList
* Fecha: agosto 2011
*/
public class SQLScript {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(SQLScript.class);

    private StringBuffer sentenciasSQL = new StringBuffer();

    private List<String> sentencias = new ArrayList<String>();

    /**
     * <code>DEFAULT_ENCODING</code> encoding por defecto a utilizar en la
     * lectura del archivo de Script.
     */
    //JESSI
    public final String DEFAULT_ENCODING = "UTF8";

    private boolean myAutocommit = true;

    /**
     * Inicializa un SQLScript sin ningun origen para irle agregando sentencias.
     * 
     * @since 18/01/2005
     */
    public SQLScript() {
        super();
    }

    /**
     * Carga un Script SQL a partir de un String
     * 
     * @since 18/01/2005
     * @param script
     */
    public SQLScript(String script) {
        addSentencia(script);
    }

    /**
     * Carga un Script SQL a partir de un InputStream. 
     * Se puede utilizar para cargar un script a partir de un archivo.
     * 
     * @since 18/01/2005
     * @param scriptInputStream
     * @throws IOException
     */
    public SQLScript(InputStream scriptInputStream) throws IOException {
        InputStreamReader iReader = new InputStreamReader(scriptInputStream);
        loadInputStreamReader(iReader);
    }

    /**
     * Carga un Script SQL a partir de un InputStreamReader. 
     * Se puede utilizar para cargar un script a partir de un archivo.

     * 
     * @since 18/01/2005
     * @param iReader
     * @throws IOException
     */
    public SQLScript(InputStreamReader iReader) throws IOException {
        loadInputStreamReader(iReader);
    }

    /**
     * Este constructor esta disponible para pasar el URL que devuelve el
     * <code>ClassLoader.getResource()</code>, o para usar con una conexión
     * HTTP.
     * 
     * @since 30/12/2004
     * @param scriptURL
     *            URL de Script
     * @return Instancia de SQLScript
     * @throws IOException
     * @see ClassLoader#getResource(java.lang.String)
     */
    public static SQLScript getSQLScript(URL scriptURL) throws IOException {
        if (scriptURL == null) {
            throw new IOException("URL inalcanzable: " + scriptURL);
        }
        return new SQLScript(scriptURL.openStream());
    }

    /**
     * Construye un SQLScript directamente a partir de un archivo.
     * 
     * @param scriptFile
     *            archivo de script
     * @return Instancia de SQLScript
     * @throws IOException
     * @since 29/12/2004
     */
    public static SQLScript getSQLScript(File scriptFile) throws IOException {
        InputStreamReader iReader = new FileReader(scriptFile);
        return new SQLScript(iReader);
    }

    private void loadInputStreamReader(InputStreamReader fileInputStreamReader)
            throws IOException {
        BufferedReader in = new BufferedReader(fileInputStreamReader);
        StringBuffer sbFile = new StringBuffer();
        String line;
        while ((line = in.readLine()) != null) {
            sbFile.append(line);
        }
        String originalFileContent = setSinComentarios(sbFile.toString());
        addSentencia(originalFileContent);
    }

    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'List'
	* Fecha: agosto 2011
	*/
    private List<String> getListSentencias() {
        return sentencias;
    }

    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList y List
	* Fecha: agosto 2011
	*/
    private List<String> separarSentencias() {
        String transformedScript = this.sentenciasSQL.toString();
        String cleanString = transformedScript.trim().replaceAll("   ", " ")
                .replaceAll("  ", " ");
        String sqlS[] = StringUtils.split(cleanString, ';');
        ArrayList<String> arraySentencias = new ArrayList<String>();
        for (int i = 0; i < sqlS.length; i++) {
            if (sqlS[i] == null || sqlS[i].trim().equals("")) {
                continue;
            }
            String toAdd = sqlS[i].trim();
            arraySentencias.add(sqlS[i].trim());
            if (logger.isDebugEnabled()) {
                String numSentencia = "AGREGADA / LEIDA SENTENCIA No. " + i;
                if (toAdd.length() < 80) {
                    logger.debug(numSentencia + ": " + toAdd);
                } else {
                    logger.debug(numSentencia);
                }
            }
        }
        return arraySentencias;
    }

    private String setSinComentarios(String originalScript) {
        String contenidoSinComentario = new String(originalScript);
        contenidoSinComentario = limpiarComentario(contenidoSinComentario,
                "/*", "*/");
        contenidoSinComentario = limpiarComentario(contenidoSinComentario,
                "--", ";");
        contenidoSinComentario = limpiarComentario(contenidoSinComentario,
                "--", "--");
        contenidoSinComentario = limpiarComentario(contenidoSinComentario,
                "--", "\n");
        //contenidoSinComentario = contenidoSinComentario.replace('\r', ' ');
        //contenidoSinComentario = contenidoSinComentario.replace('\n', ' ');
        //contenidoSinComentario = contenidoSinComentario.replace('\t', ' ');
        return contenidoSinComentario;
    }

    /**
     * @param origen
     * @param inicioComentario
     * @param finComentario
     * @return contenido limpio de comentarios
     */
    private String limpiarComentario(String origen, String inicioComentario,
            String finComentario) {
        String contenidoSinComentario = origen;
        do {
            String contenidoComentario = StringUtils.substringBetween(
                    contenidoSinComentario, inicioComentario, finComentario);
            if (contenidoComentario == null || contenidoComentario.equals("")) {
                break;
            }
            if (logger.isDebugEnabled()) {
                logger.debug("ELIMINANDO: " + inicioComentario
                        + contenidoComentario + finComentario);
            }
            contenidoSinComentario = StringUtils.replace(
                    contenidoSinComentario, inicioComentario
                            + contenidoComentario + finComentario, " ");
        } while (true);
        return contenidoSinComentario;
    }

    /**
     * @return cantidad de sentencias que existen en el script
     */
    public int getSentenciasCount() {
        return getListSentencias().size();
    }

    /**
     * Ejecuta todas las sentencias en un batch (metodo óptimo ya que se envia
     * el bloque completo de sentencias a la BD).
     * 
     * @param conn
     *            Conexión a utilizar para ejecutar el Batch.
     * @return <code>int</code> con los registros afectados por cada
     *         sentencia.
     * @throws SQLException
     * @see Statement#executeBatch()
     */
    public int[] executeBatch(Connection conn) throws SQLException {
        this.sentencias = this.separarSentencias();
        boolean autocommit = conn.getAutoCommit();
        if (myAutocommit) {
            conn.setAutoCommit(false);
        }
        Statement stmt = conn.createStatement();
        int[] salida = { 0 };
        try {
            for (int i = 0; i < sentencias.size(); i++) {
                String string = (String) sentencias.get(i);
                stmt.addBatch(string);
            }
            salida = stmt.executeBatch();
            if (myAutocommit) {
                conn.commit();
            }
        } catch (SQLException e) {
            if (myAutocommit) {
                conn.rollback();
            }
            throw e;
        } finally {
            stmt.close();
            if (myAutocommit) {
                conn.setAutoCommit(autocommit);
            }
        }
        for (int i = 0; i < salida.length; i++) {
            int integer = salida[i];
            if (logger.isDebugEnabled()) {
                logger.debug(integer + "\tregistros afectados por\t"
                        + sentencias.get(i));
            }
        }

        return salida;
    }

    /**
     * Ejecuta todas las sentencias pero cada una en un
     * <code>executeUpdate</code> distinto.
     * 
     * @param conn
     *            Conexión a utilizar para ejecutar el Batch.
     * @return <code>int</code> con los registros afectados por cada
     *         sentencia.
     * @throws SQLException
     * @see Statement#executeUpdate(String)
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
    public int[] executeAll(Connection conn) throws SQLException {
        this.sentencias = this.separarSentencias();
        boolean autocommit = conn.getAutoCommit();
        if (myAutocommit) {
            conn.setAutoCommit(false);
        }
        Statement stmt = conn.createStatement();
        int[] salida = { 0 };
        ArrayList<Integer> arraySalida = new ArrayList<Integer>();
        try {
            for (int i = 0; i < sentencias.size(); i++) {
                String string = (String) sentencias.get(i);
                int j = 0;
                long t0 = System.currentTimeMillis();
                try {
                    j = stmt.executeUpdate(string);
                    Integer J = new Integer(j);
                    arraySalida.add(J);
                } catch (SQLException e) {
                    logger.error("PROBLEMA CON CONSULTA: " + i + "/" + string);
                    throw e;
                }
                long tF = System.currentTimeMillis();
                long dT = tF - t0;
                long segs = (dT / 1000);
                if (j > 10 || logger.isDebugEnabled()) {
                    logger.info(j + "\tregistros afectados por\t" + string);
                }
                if (segs > 10 || logger.isDebugEnabled()) {
                    logger.info("TARDO " + segs
                            + " segs. en ejecutar la sentencia " + string);
                }

            }
            if (myAutocommit) {
                conn.commit();
            }
        } catch (SQLException e) {
            if (myAutocommit) {
                conn.rollback();
            }
            throw e;
        } finally {
            stmt.close();
            if (myAutocommit) {
                conn.setAutoCommit(autocommit);
            }
        }
        Integer[] intArr = (Integer[]) arraySalida.toArray(new Integer[0]);
        salida = new int[intArr.length];
        for (int i = 0; i < intArr.length; i++) {
            Integer integer = intArr[i];
            salida[i] = integer.intValue();
        }
        return salida;
    }

    /**
     * Agrega una sentencia SQL al script o un conjunto de sentencias separadas
     * por punto y coma ';'.
     * 
     * @param sentenciaSQL
     */
    public void addSentencia(String sentenciaSQL) {
        if (sentenciaSQL == null || sentenciaSQL.trim().length() == 0) {
            throw new IllegalArgumentException(
                    "No puede agregar sentencias nulas o vacias");
        }
        this.sentenciasSQL.append(sentenciaSQL);
        if (sentenciaSQL.endsWith(";")) {
            this.sentencias = this.separarSentencias();
        }
    }

    /**
     * Exporta el script a un Writer
     * 
     * @param outputWriter
     *            Writer donde guardara los script.
     * @param closeWriter
     *            indica si se cerrará el writer al finalizar la exportación (se
     *            puede dejar en <code>false</code> si exportará de nuevo, en
     *            este Writer posteriormente.
     */
    public void exportToWriter(Writer outputWriter, boolean closeWriter) {
        PrintWriter pWriter;
        if (outputWriter instanceof PrintWriter) {
            pWriter = (PrintWriter) outputWriter;
        } else {
            pWriter = new PrintWriter(outputWriter);
        }
        for (int i = 0; i < sentencias.size(); i++) {
            String string = (String) sentencias.get(i);
            pWriter.println(string + ";");
        }
        if (closeWriter) {
            pWriter.close();
        }
    }

    /**
     * Exporta el script a un String.
     * 
     * @return String con el SQL.
     */
    public String exportToString() {
        StringWriter sWriter = new StringWriter();
        exportToWriter(sWriter, true);
        return sWriter.toString();
    }

    /**
     * Metodo de prueba para ver como carga un script
     * 
     * @param args
     */
    public static void main(String[] args) {
        URL script = SQLScript.class.getClassLoader().getResource(
                "TEMP_AFILIADO.sql");
        try {
            SQLScript scriptFile = SQLScript.getSQLScript(script);
            if (logger.isDebugEnabled()) {
                logger.debug("Cargadas " + scriptFile.getSentenciasCount()
                        + " sentencias");
            }
        } catch (IOException e) {
            logger.error("ERROR", e);
        }

    }

    /**
     * @see java.lang.Object#toString()
     * @see SQLScript#exportToString()
     * @return String con las sentencias SQL.
     * @since 18/01/2005
     */
    public String toString() {
        return exportToString();
    }

    /**
     * Devuelve el indicativo si se ejecutaran las sentencias en modo
     * autocommit.
     * @return Devuelve myAutocommit.
     */
    public boolean isAutocommit() {
        return this.myAutocommit;
    }

    /**
     * Asigna el valor de autocommit para las transacciones o sentencias.
     * @param autocommit
     *            El myAutocommit a establecer.
     */
    public void setAutocommit(boolean autocommit) {
        this.myAutocommit = autocommit;
    }
}