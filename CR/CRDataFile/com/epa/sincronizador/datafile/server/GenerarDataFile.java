/*
 * $Id: GenerarDataFile.java,v 1.4 2005/06/02 13:13:57 programa4 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: GeneradorDataFile
 * Paquete		: com.epa.sincronizador.datafile
 * Programa		: GenerarDataFile.java
 * Creado por	: programa4
 * Creado en 	: 18/01/2005 04:37:23 PM
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
 * $Log: GenerarDataFile.java,v $
 * Revision 1.4  2005/06/02 13:13:57  programa4
 * Agregadas referencias this
 *
 * Revision 1.3  2005/04/05 15:13:15  programa4
 * Se readapto para que tomara en cuenta el primer parametro
 *
 * Revision 1.2  2005/03/30 17:50:07  programa4
 * Actualizacion de javadoc
 *
 * Revision 1.1  2005/03/09 18:53:45  programa4
 * Reorganizadas las clases en paquetes, ahora hay un paquete distinto para el client y para el server
 *
 * Revision 1.9  2005/03/09 01:18:22  programa4
 * Optimizado para que se puedan procesar varias tablas paralelamente
 *
 * Revision 1.8  2005/03/07 12:27:23  programa4
 * Optimizado para que se pueda especificar en  que orden se escribiran los campos
 *
 * Revision 1.7  2005/03/04 15:19:35  programa4
 * Se mueven varias propiedades  a ser variables de objeto
 * Se definen varios nombres de propiedades como constantes en PropiedadesAplicacion
 *
 * Revision 1.6  2005/03/04 15:13:36  programa4
 * Las propiedades pasan a ser variables de objeto
 * Se crea el datafile solo si se consigue el script de preparar data (si se necesita)
 *
 * Revision 1.5  2005/03/01 22:41:54  programa4
 * Se actualiza server para procesar mejor los afiliados.
 * Se integró el script de actualizado
 * Se registra ahora la fecha de generación de archivos en el planifcola
 *
 * Revision 1.4  2005/03/01 18:51:36  programa4
 * Se muestra por logger la consulta ejecutada si ocurre algún error
 *
 * Revision 1.3  2005/02/24 21:33:36  programa4
 * Unificacion de propiedades
 *
 * Revision 1.2  2005/01/25 19:09:05  programa4
 * ObtenerDataFile funcional.
 * Actualmente funciona con ThreadPool. Detecta si es un archivo de texto y activa el script
 * para insertarlo.
 *
 * Se pueden determinar tamaño de buffer (cantidad de registros a procesar en bloque)
 *
 * El log esta configurado para enviar correos en errores
 *
 * Revision 1.1  2005/01/21 19:21:40  programa4
 * Version 2.0 del Generador Data File, ya puede funcionar con cualquier tabla
 *
 * ===========================================================================
 */
package com.epa.sincronizador.datafile.server;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

import javax.sql.DataSource;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;

import com.epa.data.script.SQLScript;
import com.epa.sincronizador.datafile.DataFileDataSourceFactory;
import com.epa.sincronizador.datafile.Navegador;
import com.epa.sincronizador.datafile.Planificador;
import com.epa.sincronizador.datafile.PropiedadesAplicacion;
import com.epa.sincronizador.datafile.server.fileFormats.DataFile;
import com.epa.sincronizador.datafile.server.fileFormats.MySQLDataFile;
import com.epa.sincronizador.datafile.server.fileFormats.MySQLTextDataFile;

/**
 * <p>
 * Genera DataFile de las tablas asignadas.
 * </p>
 * <p>
 * Para la conexión se basa en los parametros establecidos en el archivo
 * <code>db.server.properties</code> y para la configuración de cada tabla,
 * utiliza el archivo <code>GenerarDataFile.properties</code>.<code>
 *      Proyecto: GeneradorDataFile 
 *      Clase: GenerarDataFile
 * </code>
 * 
 * <p>
 * <a href="GenerarDataFile.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.4 $ - $Date: 2005/06/02 13:13:57 $
 * @since 18/01/2005 FIXME Documentar GenerarDataFile y clases asociadas en el
 *        paquete
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se comentó variable sin uso
* Fecha: agosto 2011
*/
public class GenerarDataFile implements Runnable {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(GenerarDataFile.class);

    /**
     * Cantidad máxima de registros a considerar para DataFile (si es igual a 0)
     * tomará el total de la consulta origen.
     * <p>
     * Este valor se debe modificar a nivel de codigo fuente.
     */
    public static final int PRUEBA = 1000;

    private final String mySQLVersion = PropiedadesAplicacion.getProperty(
            PropiedadesAplicacion.MySQL_VERSION, MySQLDataFile.VERSION_40);

    private final String className = PropiedadesAplicacion.getProperty(
            PropiedadesAplicacion.DATAFILE_CLASSNAME, MySQLTextDataFile.class
                    .getName());

//    private final boolean batchMode = PropiedadesAplicacion.getProperty(
//            PropiedadesAplicacion.BATCH_MODE, "true").equalsIgnoreCase("true");

    private DataSource dataSource;

    private Vector<String> scriptEjecutados = new Vector<String>();

    private final String myEntity;

    /**
     * Inicializa el objeto
     * 
     * @since 19/01/2005
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public GenerarDataFile() throws IOException, ClassNotFoundException,
            InstantiationException, IllegalAccessException {
        this.dataSource = DataFileDataSourceFactory.getDataSourceServer();
        this.myEntity = null;
    }

    /**
     * Inicializa el objeto con una entidad
     * 
     * @param entity
     *            entidad a generar DataFile
     * 
     * @since 19/01/2005
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public GenerarDataFile(String entity) throws IOException,
            ClassNotFoundException, InstantiationException,
            IllegalAccessException {
        this.dataSource = DataFileDataSourceFactory.getDataSourceServer();
        this.myEntity = entity;
    }

    /**
     * @param conn
     * @param sqlScript
     * @throws SQLException
     */
    protected synchronized void prepararData(Connection conn,
            SQLScript sqlScript) throws SQLException {
        StopWatch cron = new StopWatch();
        cron.start();
        sqlScript.setAutocommit(false);
        if (PropiedadesAplicacion.getProperty(PropiedadesAplicacion.BATCH_MODE,
                "true").equalsIgnoreCase("true")) {
            sqlScript.executeBatch(conn);
        } else {
            sqlScript.executeAll(conn);
        }
        cron.stop();
        if (logger.isInfoEnabled()) {
            logger.info("TARDO " + (cron.getTime() / 1000)
                    + " segs. en prepararData");
        }
    }

    /**
     *  
     */
    public void generarDataFile() {
        if (this.myEntity != null) {
            generarDataFile(this.myEntity);
        } else {
            throw new IllegalArgumentException("No se indico entidad");
        }
    }

    /**
     * @param entidadDestino
     */
    public void generarDataFile(String entidadDestino) {
        Connection conn = null;
        try {
            try {
                conn = this.dataSource.getConnection();
                generarDataFile(entidadDestino, conn);
            } finally {
                if (conn != null && !conn.isClosed()) {
                    conn.close();	
                }
            }
        } catch (SQLException e) {
            logger
                    .error("generarDataFile(String: '" + entidadDestino + "')",
                            e);
        }

    }

    /**
     * @param entidadDestino
     * @param conn
     */
    public void generarDataFile(String entidadDestino, Connection conn) {
    	if(entidadDestino=="CR.producto"){
    		generarDataFilePorFecha(entidadDestino,conn);
    	}else{
	        StopWatch cron = new StopWatch();
	        cron.start();
	        DataFile dataFile = null;
	        long registros = 0;
	        try {
	        	//JPEREZ  23-02-2012
	        	//Código comentado durante la optimización de sincronización de productos a CR
	        /*	//URL del script que crea las tablas temporales de las entidades
	            URL urlPrepareScript = getURLPrepareScript(entidadDestino);

	            if (urlPrepareScript != null) {
	                prepararData(conn, SQLScript.getSQLScript(urlPrepareScript));
	            }*/

	            dataFile = getDataFile(entidadDestino);
	            if (dataFile != null) {
	                registros = resultSet2DataFile(entidadDestino, conn, dataFile);
	            }
	           // registrarActualizacion(entidadDestino, conn);
	        } catch (Exception e) {
	        	e.printStackTrace();
	            logger.error("NO SE PUDO GENERAR DATAFILE PARA ENTIDAD: "
	                    + entidadDestino, e);
	        } finally {
	            if (dataFile != null) {
	                dataFile.close();
	            }
	        }
	
	        cron.stop();
	        if (logger.isInfoEnabled()) {
	            logger.info("SE TARDO " + (cron.getTime() / (1000))
	                    + " segs. en generar DataFile para " + entidadDestino
	                    + " con " + registros + " registros.");
	        }
    	}
    }
    
    /**
     * Función que genera archivos dataFile por fechas de actualizacion de la entidad. 
     * @param entidadDestino
     * @param conn
     * @author jperez
     */
    public void generarDataFilePorFecha(String entidadDestino, Connection conn) {
        StopWatch cron = new StopWatch(); 
        DataFile dataFile = null;
        long registros;
        eliminarDataFile(entidadDestino);
    	Vector<Date> ultimasActualizaciones = Planificador.getUltimasActualizaciones(conn,entidadDestino);
    	for(Date ultimaActualizacion:ultimasActualizaciones){
    		registros = 0;
    		cron.reset();
	        cron.start();
    		try{
        		dataFile = getDataFile(entidadDestino+ultimaActualizacion.toString());
        		if (dataFile != null) {
                    registros = resultSet2DataFilePorFecha(entidadDestino, conn, dataFile,ultimaActualizacion);
                    dataFile.close();
                }
    		}catch(Exception e){
            	e.printStackTrace();
                logger.error("NO SE PUDO GENERAR DATAFILE PARA ENTIDAD: "
                        + entidadDestino+ "EN LA FECHA "+ultimaActualizacion.toString(), e);
            } 
    		cron.stop();
            if (logger.isInfoEnabled()) {
                logger.info("SE TARDO " + (cron.getTime() / (1000))
                        + " segs. en generar DataFile para " + entidadDestino +
                        " de fecha "+ ultimaActualizacion.toString() + " con " 
                        + registros + " registros.");
            }
    	}
    	//if(!Planificador.estanCajasEnPlanificador(conn, entidadDestino)){
    	// Generando el lote completo
		cron.reset();
        cron.start();
        registros = 0;
        try {
            dataFile = getDataFile(entidadDestino);
            if (dataFile != null) {
                registros = resultSet2DataFile(entidadDestino, conn, dataFile);
            }
        } catch (Exception e) {
        	e.printStackTrace();
            logger.error("NO SE PUDO GENERAR DATAFILE PARA ENTIDAD: "
                    + entidadDestino, e);
        } finally {
            if (dataFile != null) {
                dataFile.close();
            }
        }
        cron.stop();
        if (logger.isInfoEnabled()) {
            logger.info("SE TARDO " + (cron.getTime() / (1000))
                    + " segs. en generar DataFile para " + entidadDestino
                    + " con " + registros + " registros.");
        }
    	//}
    }

    /**
     * Elimina los Datafile de la entidadDestino creados en la 
     * corrida anterior de colas.
     * @author jperez
     * @param entidadDestino
     */
    private void eliminarDataFile(String entidadDestino) {
    	String ruta = PropiedadesAplicacion.getProperty("DataFile.dir");
    	Navegador n = new Navegador(entidadDestino);
    	n.eliminarArchivos(ruta);
	}

	/**
     * @param entidadDestino
     * @return URL con Script de preparacion
     */
    private synchronized URL getURLPrepareScript(String entidadDestino) {
    	//Se busca la propiedad CR.entidad.SQLScriptFile en el archivo 
    	//de propiedades GenerarDataFile.properties
        String scriptFile = PropiedadesAplicacion.getProperty(entidadDestino
                + ".SQLScriptFile");
        URL urlScript = null;
        if (scriptFile != null) {
            if (!this.scriptEjecutados.contains(scriptFile)) {
                this.scriptEjecutados.add(scriptFile);
                urlScript = this.getClass().getClassLoader().getResource(
                        scriptFile);
            }
        }
        return urlScript;
    }

    /**
     * @param entidadDestino
     * @return DataFile
     * @throws Exception
     */
    private DataFile getDataFile(String entidadDestino) throws Exception {
        DataFile dataFile = null;
        try {
            Class<?> classDataFile = Class.forName(this.className);
            if (MySQLDataFile.class.isAssignableFrom(classDataFile)) {
                String[] params = { entidadDestino, this.mySQLVersion };
                dataFile = (DataFile) ConstructorUtils.invokeConstructor(
                        classDataFile, params);
            } else if (DataFile.class.isAssignableFrom(classDataFile)) {
                dataFile = (DataFile) ConstructorUtils.invokeConstructor(
                        classDataFile, entidadDestino);
            }
        } catch (Exception e) {
            logger.fatal("NO SE PUDO INSTANCIAR DATAFILE", e);
            throw e;
        }
        return dataFile;
    }

    /**
     * @param entidadDestino
     * @return SQL para leer data origen
     */
    private String getSQLOrigen(String entidadDestino) {
        StringBuffer sqlOrigen = new StringBuffer();
        String sql = PropiedadesAplicacion.getProperty(entidadDestino + ".SQL");

        if (sql != null) {
            sqlOrigen.append(sql);
        } else {
            String tablaOrigen = PropiedadesAplicacion
                    .getProperty(entidadDestino + ".tablaOrigen");
            String campos = PropiedadesAplicacion.getProperty(entidadDestino
                    + ".campos");

            sqlOrigen.append("SELECT ");
            if (campos != null) {
                sqlOrigen.append(campos);
            } else {
                sqlOrigen.append("*");
            }
            sqlOrigen.append(" FROM ");
            if (tablaOrigen != null) {
                sqlOrigen.append(tablaOrigen);
            } else {
                sqlOrigen.append(entidadDestino);
            }
        }

        String condiciones = PropiedadesAplicacion.getProperty(entidadDestino
                + ".condiciones");

        if (condiciones != null) {
            sqlOrigen.append(" WHERE " + condiciones);
        }
        logger.info("CONSULTA PREPARADA: " + sqlOrigen.toString());
        return sqlOrigen.toString();
    }
    
    /**
     * Devuelve el SQL a leer con filtro de fecha
     * @param entidadDestino
     * @return SQL para leer data origen
     */
    private String getSQLOrigenPorFecha(String entidadDestino, Date fecha) {
        StringBuffer sqlOrigen = new StringBuffer();
        String sql = PropiedadesAplicacion.getProperty(entidadDestino + ".SQL");

        if (sql != null) {
            sqlOrigen.append(sql);
        } else {
            String tablaOrigen = PropiedadesAplicacion
                    .getProperty(entidadDestino + ".tablaOrigen");
            String campos = PropiedadesAplicacion.getProperty(entidadDestino
                    + ".campos");

            sqlOrigen.append("SELECT ");
            if (campos != null) {
                sqlOrigen.append(campos);
            } else {
                sqlOrigen.append("*");
            }
            sqlOrigen.append(" FROM ");
            if (tablaOrigen != null) {
                sqlOrigen.append(tablaOrigen);
            } else {
                sqlOrigen.append(entidadDestino);
            }
        }

        String condiciones = PropiedadesAplicacion.getProperty(entidadDestino
                + ".condiciones");

        if (condiciones != null) {
            sqlOrigen.append(" WHERE actualizacion >= '"+fecha+ "' AND " + condiciones);
        }else{
        	sqlOrigen.append(" WHERE actualizacion >= '"+fecha+"'");
        }
        logger.info("CONSULTA PREPARADA: " + sqlOrigen.toString());
        return sqlOrigen.toString();
    }    

    /**
     * Genera un dataFile para MySQL 4.0 a partir de un <code>ResultSet</code>.
     * <p>
     * Asigna como nombre del archivo <code>nombreTabla</code> .txt.gz.
     * 
     * @param nombreTabla
     *            nombre de la tabla a utilizar para nombre de archivo
     * @param conn
     * @param dataFile
     * @return Cantidad de registros procesados.
     * @throws SQLException
     *             en caso de problemas con la base de datos.
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
    @SuppressWarnings("unchecked")
	protected long resultSet2DataFile(String nombreTabla, Connection conn,
            DataFile dataFile) throws SQLException {
        StopWatch cron = new StopWatch();
        cron.start();

        int bloqueDebug = Integer.parseInt(PropiedadesAplicacion.getProperty(
                PropiedadesAplicacion.BLOQUEDEBUG, "10000"));
        int mapArraySize = Integer.parseInt(PropiedadesAplicacion.getProperty(
                PropiedadesAplicacion.MAPARRAYSIZE, "100"));
		int rowsBatch = Integer.parseInt(PropiedadesAplicacion.getProperty(
						PropiedadesAplicacion.BATCHROWS, "1000"));

        Statement stmt = conn.createStatement();
        String consulta = getSQLOrigen(nombreTabla);

        String campos = PropiedadesAplicacion.getProperty(nombreTabla
                + ".campos");
        if (campos != null) {
            dataFile.setFieldsName(campos.split(","));
        }

        long cuentaRegistros = 0;
        long cuentaRegistros2 = 0;

        ResultSet result = null;
        try {
            BasicRowProcessor rp = BasicRowProcessor.instance();
            ArrayList<Map<String,Object>> mapArray = new ArrayList<Map<String,Object>>(mapArraySize);
	
	
			// SEPARAMOS LOS RESULTADOS EN BLOQUES
			int numero = 0;
			boolean finalizado = false;
			System.out.println("Son en Bloques de " + rowsBatch);
			String consultaOriginal = consulta;
			while (!finalizado) {
				consulta = consultaOriginal + " limit " + numero + ", " + rowsBatch;
				System.out.println(consulta);
	            result = stmt.executeQuery(consulta);
	            result.last();
				if (result.getRow()>0) {
					result.beforeFirst();
		            while (result.next()) {
		                Map<String,Object> datos = rp.toMap(result);
		                mapArray.add(datos);
		                if (cuentaRegistros % mapArraySize == 0) {
		                    cuentaRegistros2 += mapArray2DataFile(nombreTabla,
		                            dataFile, mapArray);
		                }
		                cuentaRegistros++;
		                if (logger.isInfoEnabled()) {
		                    if (cuentaRegistros % bloqueDebug == 0) {
		                        logger.info("VAN " + cuentaRegistros + " REGISTROS");
		                        logger.info(dataFile.getInOutLog());
		                    }
		                }
		            }
		            cuentaRegistros2 += mapArray2DataFile(nombreTabla, dataFile,
		                    mapArray);
				} else {
					finalizado = true;
				}
	            numero += rowsBatch;
			}
        } catch (SQLException e) {
            logger.error("Error con consulta: " + consulta);
            throw e;
        } finally {
            if (result != null) {
                result.close();
            }
            stmt.close();
        }
        if (cuentaRegistros != cuentaRegistros2) {
            logger.warn("NO SE PROCESARON TODOS LOS REGISTROS: "
                    + cuentaRegistros + " vs. " + cuentaRegistros2);
        }
        cron.stop();
        if (logger.isInfoEnabled()) {
            logger.info("FUERON " + cuentaRegistros + " REGISTROS");
            logger.info("TARDO: " + (cron.getTime() / 1000)
                    + " SEGS. en generar datafile");
        }
        return cuentaRegistros;
    }

    
    /**
     * Genera un dataFile para MySQL 5.5 a partir de un <code>ResultSet</code>.
     * <p>
     * Asigna como nombre del archivo <code>nombreTablaFecha</code> .txt.gz.
     * 
     * @param nombreTabla
     *            nombre de la tabla a utilizar para obtener los datos
     * @param conn
     * @param dataFile
     * @return Cantidad de registros procesados.
     * @throws SQLException
     *             en caso de problemas con la base de datos.
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
    @SuppressWarnings("unchecked")
	protected long resultSet2DataFilePorFecha(String nombreTabla, Connection conn,
            DataFile dataFile, Date fecha) throws SQLException {
        StopWatch cron = new StopWatch();
        cron.start();

        int bloqueDebug = Integer.parseInt(PropiedadesAplicacion.getProperty(
                PropiedadesAplicacion.BLOQUEDEBUG, "10000"));
        int mapArraySize = Integer.parseInt(PropiedadesAplicacion.getProperty(
                PropiedadesAplicacion.MAPARRAYSIZE, "100"));
		int rowsBatch = Integer.parseInt(PropiedadesAplicacion.getProperty(
						PropiedadesAplicacion.BATCHROWS, "1000"));

        Statement stmt = conn.createStatement();
        String consulta = getSQLOrigenPorFecha(nombreTabla,fecha);
        String campos = PropiedadesAplicacion.getProperty(nombreTabla
                + ".campos");
        if (campos != null) {
            dataFile.setFieldsName(campos.split(","));
        }

        long cuentaRegistros = 0;
        long cuentaRegistros2 = 0;

        ResultSet result = null;
        try {
            BasicRowProcessor rp = BasicRowProcessor.instance();
            ArrayList<Map<String,Object>> mapArray = new ArrayList<Map<String,Object>>(mapArraySize);
	
	
			// SEPARAMOS LOS RESULTADOS EN BLOQUES
			int numero = 0;
			boolean finalizado = false;
			System.out.println("Son en Bloques de " + rowsBatch);
			String consultaOriginal = consulta;
			while (!finalizado) {
				consulta = consultaOriginal + " limit " + numero + ", " + rowsBatch;
				System.out.println(consulta);
	            result = stmt.executeQuery(consulta);
	            result.last();
				if (result.getRow()>0) {
					result.beforeFirst();
		            while (result.next()) {
		                Map<String,Object> datos = rp.toMap(result);
		                mapArray.add(datos);
		                if (cuentaRegistros % mapArraySize == 0) {
		                    cuentaRegistros2 += mapArray2DataFile(nombreTabla,
		                            dataFile, mapArray);
		                }
		                cuentaRegistros++;
		                if (logger.isInfoEnabled()) {
		                    if (cuentaRegistros % bloqueDebug == 0) {
		                        logger.info("VAN " + cuentaRegistros + " REGISTROS");
		                        logger.info(dataFile.getInOutLog());
		                    }
		                }
		            }
		            cuentaRegistros2 += mapArray2DataFile(nombreTabla, dataFile,
		                    mapArray);
					if (result != null) {
		                result.close();
		            }
				} else {
					finalizado = true;
				}
	            numero += rowsBatch;
			}
        } catch (SQLException e) {
            logger.error("Error con consulta: " + consulta);
            throw e;
        } finally {
            if (result != null) {
                result.close();
            }
            stmt.close();
        }
        if (cuentaRegistros != cuentaRegistros2) {
            logger.warn("NO SE PROCESARON TODOS LOS REGISTROS: "
                    + cuentaRegistros + " vs. " + cuentaRegistros2);
        }
        cron.stop();
        if (logger.isInfoEnabled()) {
            logger.info("FUERON " + cuentaRegistros + " REGISTROS");
            logger.info("TARDO: " + (cron.getTime() / 1000)
                    + " SEGS. en generar datafile");
        }
        return cuentaRegistros;
    }
    /**
     * @param nombreTabla
     * @param dataFile
     * @param mapArray
     * @return registros procesados
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
    @SuppressWarnings("unchecked")
	private long mapArray2DataFile(String nombreTabla, DataFile dataFile,
            ArrayList<Map<String,Object>> mapArray) {
        Map<String,Object>[] mapArr =  mapArray.toArray(new Map[mapArray.size()]);
        dataFile.printMapArray(nombreTabla, mapArr);
        long salida = mapArr.length;
        mapArray.clear();
        return salida;
    }

  //  private void registrarActualizacion(String entidad, Connection conn)
  //          throws SQLException {
/*        String script = "DELETE FROM CR.PLANIFCOLA WHERE UPPER(ENTIDAD) = '"
                + entidad.toUpperCase()
                + "' AND UPPER(DIRECCION) = 'CR'; "
                + "INSERT INTO CR.PLANIFCOLA (ENTIDAD,DIRECCION,ACTUALIZACION) VALUES ('"
                + entidad.toUpperCase() + "', 'CR', CURRENT_TIMESTAMP);";
        SQLScript scriptActualizacion = new SQLScript(script);
        if (this.batchMode) {
            scriptActualizacion.executeBatch(conn);
        } else {
            scriptActualizacion.executeAll(conn);
        }
*/
//	}

    /**
     * @param args
     * @throws SQLException
     */
    public void generarVariosDataFile(String[] args) throws SQLException {
        Connection conn = null;
        try {
            for (int i = 0; i < args.length; i++) {
                String string = args[i];
                if (conn == null || conn.isClosed()) {
                    conn = this.dataSource.getConnection();
                }
                generarDataFile(string, conn);
            }
        } finally {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
    }

    /**
     * @param args
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
    public static void generarVariosDataFileParalelo(String[] args) {
        ArrayList<Thread> procesos = generarVariosDataFileParaleloSinEsperar(args);
        while (procesos.size() > 0) {
            if (logger.isDebugEnabled()) {
                logger.debug("Revisar si ya todos los procesos terminaron");
            }
            for (int j = 0; j < procesos.size(); j++) {
                Thread thProcesses = procesos.get(j);
                if (!thProcesses.isAlive()) {
                    logger.info(thProcesses.getName() + " ya procesado");
                    procesos.remove(j);
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("Monitor de procesos interrumpido");
                break;
            }
        }

    }

    /**
     * @param args
     * @return Lista con los procesos
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
    public static ArrayList<Thread> generarVariosDataFileParaleloSinEsperar(
            String[] args) {
        ArrayList<Thread> procesos = new ArrayList<Thread>(args.length);
        for (int i = 0; i < args.length; i++) {
            try {
                String string = args[i];
                GenerarDataFile dataFileProcess = new GenerarDataFile(string);
                Thread thProceso = new Thread(dataFileProcess, string);
                procesos.add(thProceso);
                thProceso.start();
                logger.info("Proceso: " + thProceso.getName() + " iniciado");
            } catch (Exception e) {
                logger.error("generarVariosDataFileParalelo(String[])", e);
            }
        }
        return procesos;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            logger.fatal("Debe indicar las tablas que desea exportar");
            System.exit(-1);
        }
        try {
            generarVariosDataFileParalelo(args);
        } catch (Exception e) {
            logger.error("main(String[])", e);
        }
    }

    /**
     * @see java.lang.Runnable#run()
     * 
     * @since 08/03/2005
     */
    public void run() {
        generarDataFile();
    }
}