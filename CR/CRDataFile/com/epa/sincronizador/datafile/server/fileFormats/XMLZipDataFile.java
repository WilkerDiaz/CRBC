/*
 * $Id: XMLZipDataFile.java,v 1.3 2005/06/02 13:13:55 programa4 Exp $
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
 * $Log: XMLZipDataFile.java,v $
 * Revision 1.3  2005/06/02 13:13:55  programa4
 * Agregadas referencias this
 *
 * Revision 1.2  2005/03/30 17:50:05  programa4
 * Actualizacion de javadoc
 *
 * Revision 1.1  2005/03/09 18:53:42  programa4
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
 * Revision 1.1  2004/12/30 19:10:18  programa4
 * Version Inicial. Contiene:
 * MySQLDataFile, que genera un archivo comprimido con data lista para ser cargada por el MySQL
 * GenerarScriptAfiliados, que contiene los metodos y sentencias para cargar una tabla TEMP_AFILIADOS con los datos de todos los afiliados
 * TEMP_AFILIADO.sql, que contiene las rutinas para generar la tabla TEMP_AFILIADOS
 *
 * + Pendiente: pruebas en AS400 y metodos para obtener conexion.
 *
 * Revision 1.1  2004/12/27 22:24:47  programa4
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
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;

/**
 * <pre>
 * 
 *  Proyecto: ColaCR 
 *  Clase: XMLZipDataFile
 *  
 * </pre>
 * 
 * <p>
 * <a href="XMLZipDataFile.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.3 $ - $Date: 2005/06/02 13:13:55 $
 * @since 27/12/2004 @
 */
public class XMLZipDataFile extends GZipDataFile {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(XMLZipDataFile.class);

    /**
     * @since 27/12/2004
     * @param outputfile
     * @throws IOException
     */
    public XMLZipDataFile(File outputfile) throws IOException {
        super(outputfile);
    }

    /**
     * @see com.epa.sincronizador.datafile.server.fileFormats.DataFile#printMap(java.lang.String,
     *      java.util.Map)
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Map'
	* Fecha: agosto 2011
	*/
    public void printMap(String nombreTabla, Map<String,Object> datos) {
        Iterator<String> itSet = datos.keySet().iterator();
        println("<" + nombreTabla + " item=\"" + (++this.item) + "\">");
        while (itSet.hasNext()) {
            String key =  itSet.next();
            Object value = datos.get(key);
            println("\t<" + key + " value=\"" + value + "\" type=\""
                    + value.getClass().getName() + "\"/>");
        }
        println("</" + nombreTabla + ">");
    }

    /**
     * @param args
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'TreeMap'
	* Fecha: agosto 2011
	*/
    public static void main(String[] args) {
        try {
            GZipDataFile zsf = new XMLZipDataFile(new File("prueba.gz"));
            for (int i = 0; i < 100000; i++) {
                TreeMap<String,Object> prueba = new TreeMap<String,Object>();
                prueba.put("nombre", RandomStringUtils.randomAlphabetic(100));
                prueba.put("apellido", RandomStringUtils.randomAlphabetic(100));
                prueba.put("cedula", new Integer(RandomUtils.nextInt(2000)));
                prueba.put("fechanac", new Date(System.currentTimeMillis()
                        - RandomUtils.nextInt(2000)));
                zsf.printMap("persona", prueba);
                //                logger.debug("agregado item: " + i);
            }
            zsf.close();
        } catch (IOException e) {
            logger.error("ERROR EN PRUEBA", e);
        }
    }
}