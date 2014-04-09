/*
 * $Id: SQLScriptRunner.java,v 1.6 2005/03/30 17:51:01 programa4 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: DataSourceFactory
 * Paquete		: com.epa.data.script
 * Programa		: SQLScriptRunner.java
 * Creado por	: programa4
 * Creado en 	: 25/01/2005 09:40:41 AM
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
 * $Log: SQLScriptRunner.java,v $
 * Revision 1.6  2005/03/30 17:51:01  programa4
 * Actualizacion de javadoc
 *
 * Revision 1.5  2005/03/10 13:26:09  programa4
 * Documentacion
 *
 * Revision 1.4  2005/03/08 18:46:57  programa4
 * Permite la ejecucion de una lista de scripts
 *
 * Revision 1.3  2005/03/03 14:59:24  programa4
 * Cambios pequeños en el logging
 *
 * Revision 1.2  2005/03/01 18:44:45  programa4
 * Se extrajo metodo de ejecutar script para entornos donde no se requiere un hilo
 *
 * Revision 1.1  2005/01/31 23:37:02  programa4
 * Proyecto con utilidades varias para otros proyectos
 *
 * ===========================================================================
 */
package com.epa.data.script;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * Clase que ayuda en la ejecución de los <code>SQLScript</code>. Al
 * implementar runnable, permite que se puedan ejecutar los script en un proceso
 * o Thead separado.
 * 
 * <p>
 * <a href="SQLScriptRunner.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.6 $ - $Date: 2005/03/30 17:51:01 $
 * @since 25/01/2005 @
 * @see com.epa.data.script.SQLScript
 */
public class SQLScriptRunner implements Runnable {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(SQLScriptRunner.class);

    private DataSource datasource;

    private SQLScript[] script;

    private boolean batchMode = true;

    private String name;

    /**
     * Inicializa el runner
     * 
     * @since 25/01/2005
     * @param scriptname
     *            Nombre del Script o hilo
     * @param ds
     *            fuente de conexiones a la base de datos
     */
    public SQLScriptRunner(String scriptname, DataSource ds) {
        this.name = scriptname;
        this.datasource = ds;
    }

    /**
     * @see java.lang.Thread#run()
     * 
     * @since 19/01/2005
     */
    public void run() {
        try {
            logger.info("LEVANTANDO PROCESO: " + this.getName());
            runScript();
        } catch (SQLException e) {
            logger.error("PROBLEMA EJECUTANDO SCRIPT  " + this.getName(), e);
        }
    }

    /**
     * Ejecuta los scripts asignados.
     * 
     * @throws SQLException
     */
    public void runScript() throws SQLException {
        long t0 = System.currentTimeMillis();
        int regs[][] = new int[script.length][0];
        Connection conn = null;
        try {
            conn = datasource.getConnection();
            for (int i = 0; i < script.length; i++) {
                if (script[i] != null) {
                    if (this.isBatchMode()) {
                        regs[i] = script[i].executeBatch(conn);
                    } else {
                        regs[i] = script[i].executeAll(conn);
                    }
                }
            }
            if (logger.isInfoEnabled()) {
                if (regs.length > 0) {
                    int total = 0;
                    logger.info("REGISTROS MODIFICADOS CON SCRIPT:");
                    for (int i = 0; i < regs.length; i++) {
                        for (int j = 0; j < regs[i].length; j++) {
                            int k = regs[i][j];
                            total += k;
                            if (k > 0) {
                                logger.info("\t[" + i + "][" + j + "]: " + k);
                            }
                        }
                    }
                    logger.info("\tTOTAL: " + total);
                }
            }
        }catch (Exception e){
        	e.printStackTrace();
        	throw new SQLException();
        } finally {
            try {
                conn.close();
            } finally {
                long tF = System.currentTimeMillis();
                if (logger.isInfoEnabled()) {
                    long dT = tF - t0;
                    int sentencias = 0;
                    for (int i = 0; i < script.length; i++) {
                        sentencias += script[i].getSentenciasCount();
                    }
                    logger.info("TARDO " + (dT / 1000)
                            + " segs. EN EJECUTAR EL SCRIPT " + this.getName()
                            + " CON " + sentencias + " SENTENCIAS");
                }
            }
        }
    }

    /**
     * Devuelve el indicativo si los script se ejecutaran en modo batch.
     * 
     * @return Devuelve batchMode.
     */
    public boolean isBatchMode() {
        return this.batchMode;
    }

    /**
     * Asigna el indicativo si los script se ejecutaran en modo batch.
     * 
     * @param batchMode2
     *            Indica si el script se ejecutará en modo batch.
     */
    public void setBatchMode(boolean batchMode2) {
        this.batchMode = batchMode2;
    }

    /**
     * Devuelve nombre del proceso
     * 
     * @return Devuelve name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Devuelve nombre del proceso
     * 
     * @param scriptName
     *            El name a establecer.
     */
    public void setName(String scriptName) {
        this.name = scriptName;
    }

    /**
     * Agrega un nuevo script
     * 
     * @param i
     *            script a agregar
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
    public void addScript(SQLScript i) {
        if (this.script == null) {
            this.script = new SQLScript[0];
        }
        ArrayList<SQLScript> listaScript = new ArrayList<SQLScript>(script.length + 1);
        listaScript.addAll(Arrays.asList(script));
        listaScript.add(i);
        this.script = (SQLScript[]) listaScript
                .toArray(new SQLScript[script.length + 1]);
    }

}