/*
 * $Id: DataSourceFactory.java,v 1.6 2005/07/19 15:49:36 programa4 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: GeneradorDataFile
 * Paquete		: com.epa.sincronizador.generarScript
 * Programa		: DataSourceFactory.java
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
 * $Log: DataSourceFactory.java,v $
 * Revision 1.6  2005/07/19 15:49:36  programa4
 * Modificado para que la obtencion de conexion arroje RunTimeException cuando no sean excepciones de SQL
 *
 * Revision 1.5  2005/07/01 14:16:43  programa4
 * Se capturan excepciones inmanejables
 *
 * Revision 1.4  2005/03/10 13:26:10  programa4
 * Documentacion
 *
 * Revision 1.3  2005/03/01 18:45:38  programa4
 * Se activó un logger debug para ver el valor de las propiedades
 *
 * Revision 1.2  2005/02/03 23:29:46  programa4
 * Agregados metodos para generar DataSource a partir de un properties directamente,
 * en lugar de un archivo
 *
 * Revision 1.1  2005/01/31 23:37:03  programa4
 * Proyecto con utilidades varias para otros proyectos
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
package com.epa.data.sourceFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.DriverConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;

/**
 * <p>
 * Esta clase proporciona una fabrica para objetos
 * <code>javax.sql.DataSource</code>, o <code>java.sql.Connection</code> a
 * partir de informacion proporcionada en un archivo .properties.
 * </p>
 * <p>
 * La información o propiedades minimas requeridas son:
 * </p>
 * <ul>
 * <li>datasource.driver</li>
 * <li>datasource.url</li>
 * </ul>
 * Cualquier otra propiedad que requiera o utilice el driver, puede también
 * asignarlo en este mismo archivo. Por ejemplo los drivers de JTOpen y de
 * MySQL, exigen una propiedad: user y password.
 * <p>
 * Proyecto: GeneradorDataFile <br>
 * Clase: DataSourceFactory
 * <p>
 * <a href="DataSourceFactory.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.6 $ - $Date: 2005/07/19 15:49:36 $
 * @since 30/12/2004 @
 */
public class DataSourceFactory {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(DataSourceFactory.class);

    private static HashMap<Object,DataSource> dataSourceMap = new HashMap<Object,DataSource>();

    /**
     * Inicializa el DataSource. Para ello lee el archivo properties y genera el
     * dataSource. Adicionalmente guarda el DataSource en un mapa interno para
     * futuros accesos.
     * 
     * @param propertiesFile
     *            archivo con properties
     * @throws IOException
     *             en caso de problemas leyendo archivo
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static void initialize(String propertiesFile) throws IOException,
            ClassNotFoundException, InstantiationException,
            IllegalAccessException {
        DataSource dataSource = (DataSource) dataSourceMap.get(propertiesFile);
        if (dataSource == null) {
            dataSource = initDataSource(propertiesFile);
            dataSourceMap.put(propertiesFile, dataSource);
        }
    }

    /**
     * Inicializa el DataSource. Para ello lee el archivo properties y genera el
     * dataSource. Adicionalmente guarda el DataSource en un mapa interno para
     * futuros accesos.
     * 
     * @param properties
     *            Propiedades de configuracion
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static void initialize(Properties properties)
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException {
        DataSource dataSource = (DataSource) dataSourceMap.get(properties);
        if (dataSource == null) {
            dataSource = initDataSource(properties);
            dataSourceMap.put(properties, dataSource);
        }
    }

    /**
     * @param propertiesFile
     *            archivo con properties
     * @return DataSource
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IOException
     */
    protected static DataSource initDataSource(String propertiesFile)
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, IOException {

        Properties dataSourceProperties = new Properties();
        dataSourceProperties.load(DataSourceFactory.class.getClassLoader()
                .getResourceAsStream(propertiesFile));
        if (logger.isDebugEnabled()) {
            StringWriter sWriter = new StringWriter();
            PrintWriter pWriter = new PrintWriter(sWriter);
            dataSourceProperties.list(pWriter);
            logger.debug(sWriter.toString());
        }
        return initDataSource(dataSourceProperties);
    }

    /**
     * @param dataSourceProperties
     * @return DataSource
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    /*
	* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato 'Class'
	* Fecha: agosto 2011
	*/
    protected static DataSource initDataSource(Properties dataSourceProperties)
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        Class<?> driverClass = Class.forName(dataSourceProperties
                .getProperty("datasource.driver"));
        Driver driverInstance = (Driver) driverClass.newInstance();
        GenericObjectPool connectionPool = new GenericObjectPool(null);
        connectionPool.setMaxActive(50);
        connectionPool.setMaxIdle(10);
        DriverConnectionFactory connectionFactory = new DriverConnectionFactory(
                driverInstance, dataSourceProperties
                        .getProperty("datasource.url"), dataSourceProperties);
        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(
                connectionFactory, connectionPool, null, null, false, true);
        PoolingDataSource poolDataSource = new PoolingDataSource(connectionPool);
        poolableConnectionFactory.equals(poolableConnectionFactory);
        return poolDataSource;
    }

    /**
     * Devuelve una conexion de acuerdo a las propiedades indicadas en el
     * archivo properties. Una vez inicializada, el parametro
     * <code>propertiesFile</code> se utiliza es para identificar el
     * datasource.
     * 
     * @param propertiesFile
     *            archivo con properties
     * @return Conexión a la base de datos
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static Connection getConnection(String propertiesFile)
            throws SQLException, IOException, ClassNotFoundException,
            InstantiationException, IllegalAccessException {
        return getDataSource(propertiesFile).getConnection();
    }

    /**
     * Devuelve una conexion de acuerdo a los parámetros indicados en
     * <code>properties</code>, posteriormente esos datos se utilizan para
     * identificar el datasource.
     * 
     * @param properties
     *            propiedades de configuración
     * @return Conexión a la base de datos
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static Connection getConnection(Properties properties)
            throws SQLException, ClassNotFoundException,
            InstantiationException, IllegalAccessException {
        return getDataSource(properties).getConnection();
    }

    /**
     * Devuelve una conexión por defecto. Internamente se guarda un archivo
     * <code>defaultConnection.properties</code>. Esta conexión se puede
     * utilizar a modo de pruebas.
     * 
     * @return Conexión a base de datos.
     * @throws SQLException
     */
    public static Connection getDefaultConnection() throws SQLException {
        Connection salida = null;
        try {
            salida = getConnection("defaultConnection.properties");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return salida;
    }

    /**
     * Devuelve un DataSource de acuerdo a las propiedades indicadas en el
     * archivo properties. Una vez inicializada, el parametro
     * <code>propertiesFile</code> se utiliza es para identificar el
     * datasource.
     * 
     * @param propertiesFile
     * @return DataSource
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static DataSource getDataSource(String propertiesFile)
            throws IOException, ClassNotFoundException, InstantiationException,
            IllegalAccessException {
        initialize(propertiesFile);
        DataSource dataSource = (DataSource) dataSourceMap.get(propertiesFile);
        return dataSource;
    }

    /**
     * Devuelve un DataSource de acuerdo a los parámetros indicadao en
     * properties. Una vez inicializada, el parametro <code>properties</code>
     * se utiliza es para identificar el datasource.
     * 
     * @param properties
     * @return DataSource
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static DataSource getDataSource(Properties properties)
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException {
        initialize(properties);
        DataSource dataSource = (DataSource) dataSourceMap.get(properties);
        return dataSource;
    }

    /**
     * Devuelve un DataSource de acuerdo a las propiedades indicadas en el
     * archivo defaultConnection.properties.
     * 
     * @return DataSource DataSource por defecto
     */
    public static DataSource getDefaultDataSource() {
        DataSource salida = null;
        try {
            salida = getDataSource("defaultConnection.properties");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return salida;
    }
}