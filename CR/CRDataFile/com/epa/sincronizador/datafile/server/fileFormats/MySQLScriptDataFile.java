/*
 * $Id: MySQLScriptDataFile.java,v 1.2 2005/03/30 17:50:04 programa4 Exp $
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
 * $Log: MySQLScriptDataFile.java,v $
 * Revision 1.2  2005/03/30 17:50:04  programa4
 * Actualizacion de javadoc
 *
 * Revision 1.1  2005/03/09 18:53:41  programa4
 * Reorganizadas las clases en paquetes, ahora hay un paquete distinto para el client y para el server
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
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Clase abstracta para Scripts de MySQL. <code>
 *           Proyecto: ColaCR 
 *           Clase: MySQLDataFile
 * </code>
 * 
 * <p>
 * <a href="MySQLDataFile.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.2 $ - $Date: 2005/03/30 17:50:04 $
 * @since 27/12/2004 @
 */
public abstract class MySQLScriptDataFile extends MySQLDataFile {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(MySQLScriptDataFile.class);

    /**
     * Crea un MySQLScriptDataFile hacia el archivo dado, formateando la
     * información de acuerdo a la versión de MySQL indicada.
     * 
     * @since 11/01/2005
     * @param outputfile
     * @param versionMySQL
     * @throws IOException
     */
    protected MySQLScriptDataFile(File outputfile, String versionMySQL)
            throws IOException {
        super(outputfile, versionMySQL);
        printBeginTransaction();
    }

    /**
     * Crea un MySQLScriptDataFile hacia el archivo dado, formateando la
     * información de acuerdo a la versión de MySQL indicada. Se añade la
     * extensión .sql
     * 
     * @since 11/01/2005
     * @param outputfile
     * @param versionMySQL
     * @throws IOException
     */
    protected MySQLScriptDataFile(String outputfile, String versionMySQL)
            throws IOException {
        super(outputfile + ".sql", versionMySQL);
        printBeginTransaction();
    }
    
    /**
     * Agrega al script ciertas lineas para iniciar 
     * 	y optimizar la transacción
     */
    protected void printBeginTransaction() {
        println("SET AUTOCOMMIT=0;");
        println("BEGIN;");
        println("SET FOREIGN_KEY_CHECKS = 0;");
        println("SET UNIQUE_CHECKS = 0;");
    }

    /**
     * Agrega al script ciertas lineas para terminar 
     * 	y optimizar la transacción
     */
    protected void printEndTransaction() {
        println("SET UNIQUE_CHECKS = 1;");
        println("SET FOREIGN_KEY_CHECKS = 1;");
        println("COMMIT;");
    }

    /**
     * @param datos
     * @return lista separada por comas, de los nombres de los campos.
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Map'
	* Fecha: agosto 2011
	*/
    protected String printMapFieldNames(Map<String,Object> datos) {
        String[] campos =  datos.keySet().toArray(new String[0]);
        return printCSV(campos, false);
    }

    /**
     * @param datos
     * @return Texto con lista de valores separados por comas ','
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Map'
	* Fecha: agosto 2011
	*/
    protected String printMapFieldValues(Map<String,Object> datos) {
        Object[] campos = datos.values().toArray();
        return printCSV(campos, true);
    }

    /**
     * @param campos
     * @param transform
     *            indica si se aplicará transformación
     * @return Texto con lista de valores separados por comas ','
     * @see MySQLScriptDataFile#transformValue(Object)
     */
    protected String printCSV(Object[] campos, boolean transform) {
        StringBuffer listaCampos = new StringBuffer();
        for (int i = 0; i < campos.length; i++) {
            Object field = campos[i];
            if (transform) {
                listaCampos.append(transformValue(field));
            } else {
                listaCampos.append(field);
            }
            if (i + 1 < campos.length) {
                listaCampos.append(", ");
            }
        }
        return listaCampos.toString();
    }

    /**
     * @param datos
     * @return Texto con lista de pares (campo = valor) separada por comas
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Map'
	* Fecha: agosto 2011
	*/
    protected String printSetValue(Map<String,Object> datos) {
        String[] campos = datos.keySet().toArray(new String[0]);
        StringBuffer listaCampos = new StringBuffer();
        for (int i = 0; i < campos.length; i++) {
            String fieldName = campos[i];
            Object value = datos.get(fieldName);
            listaCampos.append(fieldName);
            listaCampos.append(" = ");
            listaCampos.append(transformValue(value));
            if (i + 1 < campos.length) {
                listaCampos.append(", ");
            }
        }
        return listaCampos.toString();
    }

    /**
     * @param datos
     * @return Texto con lista de pares (campo = valor) separada por el operador
     *         'AND'
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Map'
	* Fecha: agosto 2011
	*/
    protected String printAndConditionValue(Map<String,Object> datos) {
        String[] campos = datos.keySet().toArray(new String[0]);
        StringBuffer listaCampos = new StringBuffer();
        listaCampos.append("(");
        for (int i = 0; i < campos.length; i++) {
            String fieldName = campos[i];
            Object value = datos.get(fieldName);
            listaCampos.append(fieldName);
            listaCampos.append(" = ");
            listaCampos.append(transformValue(value));
            if (i + 1 < campos.length) {
                listaCampos.append(" AND ");
            }
        }
        listaCampos.append(")");
        return listaCampos.toString();
    }

    protected Object transformValue(Object value) {
        if (value == null) {
            value = "NULL";
        } else {
            value = super.transformValue(value);
            if (value instanceof String) {
                String val = (String)value;
                if (val.indexOf(";") >= 0) {
                    logger.debug("ELIMINANDO CARACTER ';' DE " + value);
                    value = val.replaceAll(";",",");
                }
            }
            if (value instanceof String || value instanceof Date) {
                value = "'" + value + "'";
            }
        }
        return value;
    }

    /**
     * @param nombreTabla
     * @param datos
     * @see com.epa.sincronizador.datafile.server.fileFormats.MySQLScriptDataFile#getSQL(java.lang.String, java.util.Map[])
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Map'
	* Fecha: agosto 2011
	*/
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void printMap(String nombreTabla, Map<String,Object> datos) {
        Map[] data = { datos };
        printMapArray(nombreTabla, data);
    }

    /**
     * @param nombreTabla
     * @param datos
     * @see com.epa.sincronizador.datafile.server.fileFormats.MySQLScriptDataFile#getSQL(java.lang.String, java.util.Map[])
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Map'
	* Fecha: agosto 2011
	*/
    public void printMapArray(String nombreTabla, Map<String,Object> datos[]) {
        if (nombreTabla == null) {
            logger.warn("Los datos es nulo");
            return;
        }

        if (datos == null) {
            logger.warn("Los datos es nulo");
            return;
        }
        String sql = getSQL(nombreTabla, datos);
        //logger.debug("AGREGADO SQL:" + sql);
        println(sql);
    }

    /**
     * @param nombreTabla
     * @param datos
     * @return Sentencia SQL para realizar la operación con MySQL
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Map'
	* Fecha: agosto 2011
	*/
    public abstract String getSQL(String nombreTabla, Map<String,Object>[] datos);
    
    /**
     * @since 20/01/2005
     * @see com.epa.sincronizador.datafile.server.fileFormats.DataFile#close()
     */
    public void close() {
        printEndTransaction();
        super.close();
    }
}