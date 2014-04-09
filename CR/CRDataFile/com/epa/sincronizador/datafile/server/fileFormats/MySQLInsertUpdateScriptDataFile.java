/*
 * $Id: MySQLInsertUpdateScriptDataFile.java,v 1.3 2005/06/02 13:13:55 programa4 Exp $
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
 * $Log: MySQLInsertUpdateScriptDataFile.java,v $
 * Revision 1.3  2005/06/02 13:13:55  programa4
 * Agregadas referencias this
 *
 * Revision 1.2  2005/03/30 17:50:04  programa4
 * Actualizacion de javadoc
 *
 * Revision 1.1  2005/03/09 18:53:39  programa4
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
 * Revision 1.1  2005/01/21 19:21:44  programa4
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
import java.util.Map;

/**
 * <code>
 *           Proyecto: ColaCR 
 *           Clase: MySQLDataFile
 * </code>
 * 
 * <p>
 * <a href="MySQLDataFile.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.3 $ - $Date: 2005/06/02 13:13:55 $
 * @since 27/12/2004 @
 */
public class MySQLInsertUpdateScriptDataFile extends MySQLScriptDataFile {
    /**
     * @since 11/01/2005
     * @param outputfile
     * @param versionMySQL
     * @throws IOException
     */
    public MySQLInsertUpdateScriptDataFile(File outputfile, String versionMySQL)
            throws IOException {
        super(outputfile, versionMySQL);
        if (versionMySQL.equals(VERSION_41)) {
            this.version = versionMySQL;
        } else {
            throw new IllegalArgumentException(
                    "Version de MySQL indicada Invalida, "
                            + "solo se permite VERSION_41 "
                            + "como valor para esta variable");
        }
    }

    /**
     * @see com.epa.sincronizador.datafile.server.fileFormats.MySQLScriptDataFile#getSQL(java.lang.String,
     *      java.util.Map[])
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Map'
	* Fecha: agosto 2011
	*/
    public String getSQL(String nombreTabla, Map<String,Object>[] datos) {
        StringBuffer sqlInsert = new StringBuffer();
        for (int i = 0; i < datos.length; i++) {
            Map<String,Object> map = datos[i];
            sqlInsert.append("INSERT INTO ");
            sqlInsert.append(nombreTabla);
            sqlInsert.append(" (" + printMapFieldNames(map) + ")");
            sqlInsert.append(" VALUES (" + printMapFieldValues(map) + ")");
            sqlInsert.append(" ON DUPLICATE KEY UPDATE (" + printSetValue(map)
                    + ")");
            sqlInsert.append(";");
            sqlInsert.append(LINESEPARATOR);
        }
        return sqlInsert.toString();
    }
}