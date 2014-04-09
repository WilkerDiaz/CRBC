/*
 * $Id: MySQLTextDataFile.java,v 1.3 2005/06/02 13:13:56 programa4 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: GeneradorDataFile
 * Paquete		: com.epa.sincronizador.datafile.fileFormats
 * Programa		: MySQLTextDataFile.java
 * Creado por	: programa4
 * Creado en 	: 21/01/2005 08:40:09 AM
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
 * $Log: MySQLTextDataFile.java,v $
 * Revision 1.3  2005/06/02 13:13:56  programa4
 * Agregadas referencias this
 *
 * Revision 1.2  2005/03/30 17:50:04  programa4
 * Actualizacion de javadoc
 *
 * Revision 1.1  2005/03/09 18:53:40  programa4
 * Reorganizadas las clases en paquetes, ahora hay un paquete distinto para el client y para el server
 *
 * Revision 1.4  2005/03/08 12:18:44  programa4
 * Cambiado lista de campos de hashset a linkedhashset para asegurar que se mantenga el orden
 *
 * Revision 1.3  2005/03/07 12:22:53  programa4
 * Optimizado para que se pueda especificar en  que orden se escribiran los campos
 *
 * Revision 1.2  2005/01/25 19:09:09  programa4
 * ObtenerDataFile funcional.
 * Actualmente funciona con ThreadPool. Detecta si es un archivo de texto y activa el script
 * para insertarlo.
 *
 * Se pueden determinar tamaño de buffer (cantidad de registros a procesar en bloque)
 *
 * El log esta configurado para enviar correos en errores
 *
 * Revision 1.1  2005/01/21 19:21:45  programa4
 * Version 2.0 del Generador Data File, ya puede funcionar con cualquier tabla
 *
 * ===========================================================================
 */
package com.epa.sincronizador.datafile.server.fileFormats;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * Genera un GZipDataFile con información para ser importada por el MySQL con el
 * comando
 * <code>LOAD DATA INFILE 'nombre y ruta archivo' REPLACE INTO TABLE tabla 
 * (lista campos,...)</code>.
 * <code>
 * Proyecto: GeneradorDataFile 
 * Clase: MySQLTextDataFile
 * </code>
 * <p>
 * <a href="MySQLTextDataFile.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.3 $ - $Date: 2005/06/02 13:13:56 $
 * @since 21/01/2005 @
 */
public class MySQLTextDataFile extends MySQLDataFile {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(MySQLTextDataFile.class);

    private boolean printedFieldsName = false;

    /**
     * @since 21/01/2005
     * @param outFile
     * @param versionMySQL
     * @throws IOException
     */
    public MySQLTextDataFile(String outFile, String versionMySQL)
            throws IOException {
        super(outFile + ".txt", versionMySQL);
    }

    /**
     * Crea un MySQLTextDataFile hacia el archivo dado, formateando la
     * información de acuerdo a la versión de MySQL indicada
     * 
     * @since 27/12/2004
     * @param outFile
     * @param versionMySQL
     * @throws IOException
     */
    public MySQLTextDataFile(File outFile, String versionMySQL)
            throws IOException {
        super(outFile, versionMySQL);
    }

    /**
     * @return String con la lista de nombres de campos separados por
     *         tabuladores.
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Iterator'
	* Fecha: agosto 2011
	*/
    protected String printFieldNames() {
        int i = 0;
        StringBuffer sbFieldNames = new StringBuffer();
        Iterator<String> itSet = getFieldsName().iterator();
        while (itSet.hasNext()) {
            String key = itSet.next();
            i++;
            sbFieldNames.append(key);
            if (logger.isInfoEnabled()) {
                logger.info("Printing key name: " + key);
            }
            if (i < getFieldsName().size()) {
                sbFieldNames.append("\t");
            }
        }
        return sbFieldNames.toString();
    }

    /**
     * @see com.epa.sincronizador.datafile.server.fileFormats.DataFile#printMap(java.lang.String,
     *      java.util.Map)
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Map' y 'Set'
	* Fecha: agosto 2011
	*/
    public void printMap(String nombreTabla, Map<String,Object> datos) {
        if (datos == null) {
            logger.warn("Los datos son nulos");
            return;
        }
        Set<String> fieldsName = getFieldsName();
        Set<String> datosFieldsName = datos.keySet();
        if (fieldsName == null || fieldsName.size() == 0) {
            setFieldsName(datosFieldsName);
            logger.info("Reasignando campos en: " + nombreTabla);
        } else {
            if (!fieldsName.equals(datosFieldsName)) {
                logger.warn("Los datos difieren de los campos a utilizar");
                return;
            }
        }
        if (!this.printedFieldsName) {
            println(printFieldNames());
            this.printedFieldsName = true;
        }
        StringBuffer sbFieldData = new StringBuffer();
        Iterator<String> itSet = getFieldsName().iterator();
        int i = 0;
        while (itSet.hasNext()) {
            String key = (String) itSet.next();
            Object value = datos.get(key);
            value = transformValue(value);
            i++;
            sbFieldData.append(value);
            if (i < getFieldsName().size()) {
                sbFieldData.append("\t");
            }
        }
        println(sbFieldData.toString());
    }
}