/*
 * $Id: MySQLDataFile.java,v 1.2 2005/06/02 13:13:54 programa4 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: ColaCR
 * Paquete		: com.epa.sincronizador.datafile
 * Programa		: XMLZipDataFile.java
 * Creado por	: programa4
 * Creado en 	: 27/12/2004 08:54:34 AM
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
 * $Log: MySQLDataFile.java,v $
 * Revision 1.2  2005/06/02 13:13:54  programa4
 * Agregadas referencias this
 *
 * Revision 1.1  2005/03/09 18:53:39  programa4
 * Reorganizadas las clases en paquetes, ahora hay un paquete distinto para el client y para el server
 *
 * Revision 1.2  2005/03/07 12:22:52  programa4
 * Optimizado para que se pueda especificar en  que orden se escribiran los campos
 *
 * Revision 1.1  2005/01/21 19:21:43  programa4
 * Version 2.0 del Generador Data File, ya puede funcionar con cualquier tabla
 *
 * Revision 1.2  2005/01/06 23:01:59  programa4
 * Version funcional del generador de afiliados
 *
 * Revision 1.1  2004/12/30 19:10:18  programa4
 * Version Inicial. Contiene:
 * MySQLDataFile, que genera un archivo comprimido con data lista para ser cargada por el MySQL
 * GenerarScriptAfiliados, que contiene los metodos y sentencias para cargar una tabla TEMP_AFILIADOS con los datos de todos los afiliados
 * TEMP_AFILIADO.sql, que contiene las rutinas para generar la tabla TEMP_AFILIADOS
 *
 * + Pendiente: pruebas en AS400 y metodos para obtener conexion.
 *
 * Revision 1.2  2004/12/30 02:58:41  programa4
 * Optimizada consulta, generado SQLScript que le un archivo de script y da la opcion
 * de ejecutarlo en batch
 *
 * Revision 1.1  2004/12/27 22:24:48  programa4
 * Lista implementacion que genera data file para ser importado en MySQL
 * Se logro un tiempo de generacion del .gz de 8 minutos para 1.000.000 de registros
 * de afiliados
 *
 * ===========================================================================
 */
package com.epa.sincronizador.datafile.server.fileFormats;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

/**
 * <p>
 * Este objeto también sirve como padre para formatos con MySQL.
 * </p>
 * <code>
 *              Proyecto: ColaCR 
 *              Clase: MySQLDataFile
 * </code>
 * 
 * <p>
 * <a href="MySQLDataFile.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.2 $ - $Date: 2005/06/02 13:13:54 $
 * @since 27/12/2004 @
 */
public abstract class MySQLDataFile extends GZipDataFile {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(MySQLDataFile.class);

    /**
     * <code>version</code> version de MySQL. Este valor es importante para
     * conocer como se formatearán los datos.
     */
    protected String version = null;

    /**
     * <code>VERSION_40</code> Constante para indicar versión 4.0.x
     */
    public static final String VERSION_40 = "40";

    /**
     * <code>VERSION_41</code> Constante para indicar versión 4.1.x
     */
    public static final String VERSION_41 = "41";

    /**
     * Crea un MySQLDataFile hacia el archivo dado, formateando la información
     * de acuerdo a la versión de MySQL indicada
     * 
     * @since 27/12/2004
     * @param outFile
     * @param versionMySQL
     * @throws IOException
     */
    public MySQLDataFile(File outFile, String versionMySQL) throws IOException {
        super(outFile);
        initVersion(versionMySQL);
    }

    /**
     * Crea un MySQLDataFile hacia el archivo indicado, formateando la
     * información de acuerdo a la versión de MySQL indicada. Se recomienda que
     * posea la extensión .txt.
     * 
     * @since 27/12/2004
     * @param outFile
     * @param versionMySQL
     * @throws IOException
     */
    public MySQLDataFile(String outFile, String versionMySQL)
            throws IOException {
        super(outFile);
        initVersion(versionMySQL);
    }

    /**
     * @param versionMySQL
     */
    private void initVersion(String versionMySQL) {
        if (versionMySQL.equals(VERSION_40)) {
            this.version = versionMySQL;
        } else if (versionMySQL.equals(VERSION_41)) {
            this.version = versionMySQL;
        } else {
            throw new IllegalArgumentException(
                    "Version de MySQL indicada Invalida, "
                            + "use las constantes VERSION_40 o VERSION_41 "
                            + "como valor para esta variable");
        }
    }

    /**
     * Devuelve un objeto (generalmente String), formateado de forma que pueda
     * pueda ser procesado.
     * 
     * @param value
     * @return Objeto transformado.
     */
    protected Object transformValue(Object value) {
        if (value == null) {
            value = "\\N";
        } else if (value instanceof String) {
            value = transformString(value);
        } else if (value instanceof Date) {
            value = transformDate(value);
        }
        return value;
    }

    /**
     * Se usa el método <code> escapeJava </code> Para convertir los tabs y
     * saltos de linea en secuencias escapadas (con \t y \n). El metodo
     * escapeSql convierte las comillas sencillas en 2 comillas sencillas
     * seguidas.
     * 
     * @param value
     * @return transformString
     */
    protected Object transformString(Object value) {
        String transformedValue = (String) value;
        transformedValue = StringEscapeUtils.escapeJava(transformedValue);
        transformedValue = StringEscapeUtils.escapeSql(transformedValue);
        return transformedValue;
    }

    /**
     * <p>
     * Formatea los objetos Date, para que puedan ser procesados.
     * </p>
     * <p>
     * En el caso de versión 4.1 se aplica para TimeStamp el formato
     * <code>yyyy-MM-dd HH:mm:ss</code> y en el caso de versión 4.0
     * <code>yyyyMMddHHmmss</code>.
     * 
     * @param value
     * @return value transformado
     */
    protected Object transformDate(Object value) {
        Date dateValue = (Date) value;
        if (dateValue instanceof java.sql.Date) {
            value = DateFormatUtils.format(dateValue, "yyyy-MM-dd");
        } else if (dateValue instanceof java.sql.Time) {
            value = DateFormatUtils.format(dateValue, "HH:mm:ss");
        } else {
            /* En este caso puede ser un Date generico o un Timestamp */
            //if (dateValue instanceof java.sql.Timestamp) {
            if (this.version != null
                    && this.version.equalsIgnoreCase(VERSION_41)) {
                value = DateFormatUtils
                        .format(dateValue, "yyyy-MM-dd HH:mm:ss");
            } else if (this.version != null
                    && this.version.equalsIgnoreCase(VERSION_40)) {
                value = DateFormatUtils.format(dateValue, "yyyyMMddHHmmss");
            }
        }
        return value;
    }

    /**
     * Genera un MySQLDataFile con data de prueba
     * 
     * @param args
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList, TreeMap,
	* Fecha: agosto 2011
	*/
    @SuppressWarnings("unchecked")
	public static void main(String[] args) {
        try {
            MySQLInsertScriptDataFile zsf = new MySQLInsertScriptDataFile(
                    "prueba", MySQLDataFile.VERSION_40);
            ArrayList<TreeMap<String,Object>> arrMap = new ArrayList<TreeMap<String,Object>>();
            for (int i = 0; i < 1000; i++) {
                TreeMap<String,Object> prueba = new TreeMap<String,Object>();
                prueba.put("nombre", RandomStringUtils
                        .randomAlphabetic(RandomUtils.nextInt(100)));
                prueba.put("apellido", RandomStringUtils
                        .randomAlphabetic(RandomUtils.nextInt(100)));
                prueba
                        .put("cedula", new Integer(RandomUtils
                                .nextInt(20000000)));
                long currentTime = System.currentTimeMillis();
                long randomLong = RandomUtils.nextLong();
                while (randomLong > currentTime) {
                    randomLong -= currentTime;
                }
                prueba.put("fechanac", new Date(currentTime - randomLong));

                arrMap.add(prueba);
                if ((i + 1) % 100 == 0) {
                    SortedMap<String,Object>[] mapArr =  arrMap
                            .toArray(new SortedMap[arrMap.size()]);
                    zsf.printMapArray("persona", mapArr);
                    arrMap.clear();
                }
                if ((i + 1) % 100 == 0) {
                    logger.debug("agregado item: " + (i + 1));
                }
            }
            zsf.close();
        } catch (IOException e) {
            logger.error("ERROR EN PRUEBA", e);
        }
    }
}