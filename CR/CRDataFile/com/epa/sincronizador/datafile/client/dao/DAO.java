/*
 * $Id: DAO.java,v 1.2 2005/06/02 13:09:59 programa4 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: GeneradorDataFile
 * Paquete		: com.epa.sincronizador.datafile.dto
 * Programa		: DAO.java
 * Creado por	: programa4
 * Creado en 	: 24/02/2005 06:19:24 PM
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
 * $Log: DAO.java,v $
 * Revision 1.2  2005/06/02 13:09:59  programa4
 * Agregadas referencias this
 *
 * Revision 1.1  2005/03/09 18:53:26  programa4
 * Reorganizadas las clases en paquetes, ahora hay un paquete distinto para el client y para el server
 *
 * Revision 1.3  2005/03/08 23:58:16  programa4
 * Configurado para poder trabajar con archivos temporales intermedios.
 * Este será el esquema a utilizar con afiliado. donde se anexan lso registros a una tabla cuyo nombre
 * termine en _temp y que una vez completado el proceso se borra la tabla original y se sustituye
 * por la _temp
 *
 * Revision 1.2  2005/03/01 19:01:40  programa4
 * Se corrigen ciertos detalles para cuando no exista ningún registro previo de la entidad
 *
 * Revision 1.1  2005/02/25 00:17:04  programa4
 * Se agregó busqueda en planificador de fecha de ultima actualizacion y comparacion con fecha del archivo
 *
 * ===========================================================================
 */
package com.epa.sincronizador.datafile.client.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.log4j.Logger;

import com.epa.sincronizador.datafile.DataFileDataSourceFactory;

/**
 * Posee ciertas funciones para localizar o actualizar alguna información en la
 * BD.
 * 
 * <p>
 * <a href="DAO.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.2 $ - $Date: 2005/06/02 13:09:59 $
 * @since 24/02/2005 @
 */
public class DAO {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(DAO.class);

    /**
     * <code>TIMESTAMP_1980_01_01</code> representa la fecha 01-01-1980 para
     * fechas vacías.
     */
    public static final Timestamp TIMESTAMP_1980_01_01 = new Timestamp(10 * 365
            * 24 * 3600 * 1000);

    private Connection conn;

    private PreparedStatement psObtenerSincronizacionEntidad;

    private PreparedStatement psContarSincronizacionEntidad;

    private PreparedStatement psActualizarSincronizacionEntidad;

    private BeanHandler dataCajaHandler = new BeanHandler(DataCaja.class);

    private BeanHandler dataEntidadHandler = new BeanHandler(
            SincronizacionEntidad.class);

    private DataCaja dataCaja;

    /**
     * @since 24/02/2005
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public DAO() throws SQLException, IOException, ClassNotFoundException,
            InstantiationException, IllegalAccessException {
        init();
    }

    /**
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void init() throws SQLException, IOException,
            ClassNotFoundException, InstantiationException,
            IllegalAccessException {
        this.conn = DataFileDataSourceFactory.getConnectionClient();
        this.psObtenerSincronizacionEntidad = this.conn
                .prepareStatement(SincronizacionEntidad.SQL_SELECT);
        this.psContarSincronizacionEntidad = this.conn
                .prepareStatement(SincronizacionEntidad.SQL_SELECT_COUNT);
        this.psActualizarSincronizacionEntidad = this.conn
                .prepareStatement(SincronizacionEntidad.SQL_REPLACE);
        this.dataCaja = getDataCaja();
        this.psObtenerSincronizacionEntidad.setInt(1, this.dataCaja
                .getNumtienda().intValue());
        this.psObtenerSincronizacionEntidad.setInt(2, this.dataCaja
                .getNumcaja().intValue());

        this.psContarSincronizacionEntidad.setInt(1, this.dataCaja
                .getNumtienda().intValue());
        this.psContarSincronizacionEntidad.setInt(2, this.dataCaja.getNumcaja()
                .intValue());

        this.psActualizarSincronizacionEntidad.setInt(1, this.dataCaja
                .getNumtienda().intValue());
        this.psActualizarSincronizacionEntidad.setInt(2, this.dataCaja
                .getNumcaja().intValue());

    }

    /**
     * @return informacion de la caja
     */
    public DataCaja getDataCaja() {
        DataCaja newDataCaja = null;
        ResultSet rsDataCaja = null;
        PreparedStatement psDataCaja = null;
        try {
            psDataCaja = this.conn.prepareStatement(DataCaja.SQL);
            rsDataCaja = psDataCaja.executeQuery();
            newDataCaja = (DataCaja) this.dataCajaHandler.handle(rsDataCaja);
        } catch (SQLException e) {
            logger.error("ERROR EJECUTANDO psDataCaja", e);
        } finally {
            if (rsDataCaja != null) {
                try {
                    rsDataCaja.close();
                } catch (SQLException e1) {
                    logger.error("ERROR CERRANDO rsDataCaja", e1);
                }
            }
            if (psDataCaja != null) {
                try {
                    psDataCaja.close();
                } catch (SQLException e1) {
                    logger.error("ERROR CERRANDO psDataCaja", e1);
                }
            }

        }
        return newDataCaja;
    }

    /**
     * Obtiene la data de sincronizacion de la entidad indicada
     * 
     * @param entidad
     *            a buscar data de sincronizacion
     * @return <code>SincronizacionEntidad</code> data de sincronizacion
     */
    public SincronizacionEntidad getSincronizacionEntidad(String entidad) {
        SincronizacionEntidad dataEntidad = null;
        ResultSet rsDataEntidad = null;
        ResultSet rsCuentaEntidad = null;
        try {
            int cuentaEntidad = 0;
            this.psContarSincronizacionEntidad.setString(3, entidad);
            rsCuentaEntidad = this.psContarSincronizacionEntidad.executeQuery();
            if (rsCuentaEntidad.next()) {
                cuentaEntidad = rsCuentaEntidad.getInt("cuenta");
            }
            if (cuentaEntidad > 0) {
                this.psObtenerSincronizacionEntidad.setString(3, entidad);
                rsDataEntidad = this.psObtenerSincronizacionEntidad
                        .executeQuery();
                dataEntidad = (SincronizacionEntidad) this.dataEntidadHandler
                        .handle(rsDataEntidad);
            }
        } catch (SQLException e) {
            logger.error("ERROR EJECUTANDO rsDataEntidad", e);
        } finally {
            if (rsCuentaEntidad != null) {
                try {
                    rsCuentaEntidad.close();
                } catch (SQLException e1) {
                    logger.error("ERROR CERRANDO rsCuentaEntidad", e1);
                }
            }
            if (rsDataEntidad != null) {
                try {
                    rsDataEntidad.close();
                } catch (SQLException e1) {
                    logger.error("ERROR CERRANDO rsDataEntidad", e1);
                }
            }
        }
        if (dataEntidad == null) {
            dataEntidad = new SincronizacionEntidad();
            dataEntidad.setNumtienda(this.dataCaja.getNumtienda());
            dataEntidad.setNumcaja(this.dataCaja.getNumcaja());
            dataEntidad.setEntidad(entidad);
            dataEntidad.setDestino("C");
            dataEntidad.setFallasincronizador("S");
            dataEntidad.setActualizacion(TIMESTAMP_1980_01_01);
        }
        return dataEntidad;
    }

    /**
     * Actualiza la entidad
     * 
     * @param dataEntidad
     * @param actualizacion
     */
    public void setSincronizacionEntidad(SincronizacionEntidad dataEntidad,
            Timestamp actualizacion) {
        int updatedRows = 0;
        if (actualizacion == null) {
            actualizacion = new Timestamp(System.currentTimeMillis());
        }
        dataEntidad.setActualizacion(actualizacion);
        try {
            this.psActualizarSincronizacionEntidad.setString(3, dataEntidad
                    .getEntidad()); 
            this.psActualizarSincronizacionEntidad.setTimestamp(4, dataEntidad
                    .getActualizacion());
            this.psActualizarSincronizacionEntidad.setString(5, dataEntidad
                    .getFallasincronizador());
            updatedRows = this.psActualizarSincronizacionEntidad
                    .executeUpdate();
        } catch (SQLException e) {
        	logger.error("ERROR EJECUTANDO psActualizarSincronizacionEntidad",
                    e);
        }
        if (updatedRows > 0) {
            logger.info("SE ACTUALIZARON " + updatedRows + " registros");
        }
    }

    /**
     * @param entidad
     * @return Script para generar la tabla
     */
    public String getCreateTable(String entidad) {
        Statement stmt = null;
        ResultSet rsCreateTable = null;
        try {
            stmt = this.conn.createStatement();
            rsCreateTable = stmt.executeQuery("SHOW CREATE TABLE " + entidad);
            if (rsCreateTable.next()) {
                return rsCreateTable.getString(2);
            }
        } catch (SQLException e) {
            logger.error("ERROR OBTENIENDO Create table de la entidad:"
                    + entidad, e);

        } finally {
            if (rsCreateTable != null) {
                try {
                    rsCreateTable.close();
                } catch (SQLException e1) {
                    logger.error("ERROR OBTENIENDO Create table de la entidad:"
                            + entidad, e1);
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e1) {
                    logger.error("ERROR OBTENIENDO Create table de la entidad:"
                            + entidad, e1);
                }
            }
        }
        return null;
    }

    /**
     * Cierra las conexiones y los PreparedStatement
     */
    public void close() {
        try {
            this.psObtenerSincronizacionEntidad.close();
        } catch (SQLException e) {
            logger.error("PROBLEMA CERRANDO psObtenerSincronizacionEntidad", e);
        }
        try {
            this.psActualizarSincronizacionEntidad.close();
        } catch (SQLException e) {
            logger.error("PROBLEMA CERRANDO psActualizarSincronizacionEntidad",
                    e);
        }
        try {
            this.conn.close();
        } catch (SQLException e) {
            logger.error("PROBLEMA CERRANDO conexión", e);
        }
    }
}