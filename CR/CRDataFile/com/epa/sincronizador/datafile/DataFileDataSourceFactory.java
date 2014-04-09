/*
 * $Id: DataFileDataSourceFactory.java,v 1.6 2005/11/24 18:29:55 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: GeneradorDataFile
 * Paquete		: com.epa.sincronizador.datafile
 * Programa		: DataFileDataSourceFactory.java
 * Creado por	: programa4
 * Creado en 	: 30/12/2004 03:06:04 PM
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
 * $Log: DataFileDataSourceFactory.java,v $
 * Revision 1.6  2005/11/24 18:29:55  programa8
 * Mejora para localizar en cada instalación las propiedades del sistema
 *
 * Revision 1.5  2005/03/09 18:53:29  programa4
 * Reorganizadas las clases en paquetes, ahora hay un paquete distinto para el client y para el server
 *
 * Revision 1.4  2005/03/01 18:58:48  programa4
 * Cambiado orden de metodos
 *
 * Revision 1.3  2005/02/24 21:33:35  programa4
 * Unificacion de propiedades
 *
 * Revision 1.2  2005/01/25 19:09:06  programa4
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
 * Revision 1.2  2005/01/06 23:01:22  programa4
 * Version funcional del generador de afiliados
 *
 * Revision 1.1  2004/12/31 00:26:06  programa4
 * Agregado metodos de obtener conexion
 * Y para leer archivos de inputstream
 *
 * ===========================================================================
 */
package com.epa.sincronizador.datafile;

import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.epa.data.sourceFactory.DataSourceFactory;

/**
 * Clase con fabrica de DataSources y Conexiones a servidor o cliente, de
 * acuerdo al modo en que se esté ejecutando el programa.
 * <p>
 * <a href="DataFileDataSourceFactory.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author programa4 - $Author: programa8 $
 * @version $Revision: 1.6 $ - $Date: 2005/11/24 18:29:55 $
 * @since 30/12/2004 @
 */
public class DataFileDataSourceFactory extends DataSourceFactory {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(DataFileDataSourceFactory.class);

    private static final String SERVER_DB = "db.server.properties";

    private static final String CLIENT_DB = "db.client.properties";
    
    private static final Properties SERVER_DB_PROPS = new Properties();
    
    private static final Properties CLIENT_DB_PROPS = new Properties();
    
    static {
        try {
            SERVER_DB_PROPS.load(PropiedadesAplicacion.cargarRecurso(SERVER_DB));
        } catch (FileNotFoundException e) {
            logger.error("Error en inicialización estática de DataSourceFactory", e);
        } catch (IOException e) {
            logger.error("Error en inicialización estática de DataSourceFactory", e);
        }
        try {
            CLIENT_DB_PROPS.load(PropiedadesAplicacion.cargarRecurso(CLIENT_DB));
        } catch (FileNotFoundException e1) {
            logger.error("Error en inicialización estática de DataSourceFactory", e1);
        } catch (IOException e1) {
            logger.error("Error en inicialización estática de DataSourceFactory", e1);
        }
    }
    

    /**
     * @return Conexión al servidor (generalmente AS400)
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static Connection getConnectionServer() throws SQLException,
            IOException, ClassNotFoundException, InstantiationException,
            IllegalAccessException {
        return getConnection(SERVER_DB_PROPS);
    }

    /**
     * @return DataSource al cliente servidor (generalmente AS400)
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static DataSource getDataSourceServer() throws IOException,
            ClassNotFoundException, InstantiationException,
            IllegalAccessException {
        return getDataSource(SERVER_DB_PROPS);
    }

    /**
     * @return Conexion al cliente (generalmente MySQL)
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static Connection getConnectionClient() throws SQLException,
            IOException, ClassNotFoundException, InstantiationException,
            IllegalAccessException {
        return getConnection(CLIENT_DB_PROPS);
    }

    /**
     * @return DataSource al cliente (generalmente MySQL)
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static DataSource getDataSourceClient() throws IOException,
            ClassNotFoundException, InstantiationException,
            IllegalAccessException {
        return getDataSource(CLIENT_DB_PROPS);
    }
}