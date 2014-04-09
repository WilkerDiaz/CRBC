/*
 * $Id: Principal.java,v 1.3 2005/04/05 15:13:15 programa4 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: GeneradorDataFile
 * Paquete		: com.epa.sincronizador
 * Programa		: Principal.java
 * Creado por	: programa4
 * Creado en 	: 06/01/2005 02:53:29 PM
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
 * $Log: Principal.java,v $
 * Revision 1.3  2005/04/05 15:13:15  programa4
 * Se readapto para que tomara en cuenta el primer parametro
 *
 * Revision 1.2  2005/04/04 19:47:59  programa4
 * Imprime mensaje por consola de que el CR-DataFile ya se ejecuto
 *
 * Revision 1.1  2005/03/09 18:53:30  programa4
 * Reorganizadas las clases en paquetes, ahora hay un paquete distinto para el client y para el server
 *
 * Revision 1.4  2005/03/07 12:27:43  programa4
 * Arreglado reporte de tiempo de duracion
 *
 * Revision 1.3  2005/02/24 21:33:38  programa4
 * Unificacion de propiedades
 *
 * Revision 1.2  2005/01/31 23:35:11  programa4
 * Renombrado paquete de DataSource y agregado verificacion de tiempo
 *
 * Revision 1.1  2005/01/25 19:09:12  programa4
 * ObtenerDataFile funcional.
 * Actualmente funciona con ThreadPool. Detecta si es un archivo de texto y activa el script
 * para insertarlo.
 *
 * Se pueden determinar tamaño de buffer (cantidad de registros a procesar en bloque)
 *
 * El log esta configurado para enviar correos en errores
 *
 * Revision 1.2  2005/01/21 19:21:49  programa4
 * Version 2.0 del Generador Data File, ya puede funcionar con cualquier tabla
 *
 * Revision 1.1  2005/01/06 23:01:11  programa4
 * Version funcional del generador de afiliados
 *
 * ===========================================================================
 */
package com.epa.sincronizador.datafile;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Time;
import java.util.Date;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.epa.sincronizador.datafile.client.ObtenerDataFile;
import com.epa.sincronizador.datafile.server.GenerarDataFile;

/**
 * Clase de ejecución principal. Los parametros a utilizar son
 * <code>[client/server] [archivos a descargar o tablas a generar...]</code>.
 * </p>
 * <ol>
 * <li><code>[client/server]</code> indica si se ejecutará en modo cliente
 * (obtener datafile e insertarlo o servidor (generar datafile).</li>
 * <li><code>[archivos a descargar o tablas a generar...]</code> cuales son
 * las tablas a descargar (modo cliente) o datafile a generar (modo servidor).
 * <br>
 * Los archivos a descargar generalmente estaran en formato
 * <code><strong><em>Tabla</em></strong>.txt.gz</code>.<br>
 * Los datafile a generar generalmente estaran en formato
 * <code><strong><em>Tabla</em></strong></code>.</li>
 * </ol>
 * <p>
 * Ejemplos:
 * <ul>
 * <li><code>client CR.prodcodigoexterno.txt.gz CR.afiliado.txt.gz</code>
 * </li>
 * <li><code>server CR.prodcodigoexterno CR.afiliado</code></li>
 * </ul>
 * <p>
 * Por defecto se trabajan varias tablas en paralelo, pero se espera a que se
 * terminen todos los procesos para cerrar el programa. Para trabajar de otra
 * forma ejecute directamente <code>ObtenerDataFile</code> o
 * <code>GenerarDataFile</code>.
 * <p>
 * <a href="Principal.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.3 $ - $Date: 2005/04/05 15:13:15 $
 * @since 06/01/2005
 * @see com.epa.sincronizador.datafile.server.GenerarDataFile
 * @see com.epa.sincronizador.datafile.client.ObtenerDataFile
 */
public class Principal {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(Principal.class);

    /**
     * Inicia el Sincronizador a traves de dataFile.
     * 
     * @param args
     *            Indique si se va a ejecutar en modo de [server] o [client]
     */
    public static void main(String[] args) {
        long t0 = System.currentTimeMillis();
        if (logger.isInfoEnabled()) {
            logger.info("INICIADA APLICACION A LAS: " + new Date());
        }
        if (logger.isDebugEnabled()) {
            StringWriter sWriter = new StringWriter();
            PrintWriter pWriter = new PrintWriter(sWriter);
            pWriter.println("PARAMETROS RECIBIDOS: ");
            for (int i = 0; i < args.length; i++) {
                String string = args[i];
                pWriter.println("\t" + string);
            }
            pWriter.println("PROPIEDADES DE SISTEMA: ");
            System.getProperties().list(pWriter);
            logger.debug(sWriter.toString());
        }
        if (args.length > 0) {
            String arg2[] = new String[args.length - 1];
            System.arraycopy(args, 1, arg2, 0, args.length - 1);
            if (args[0].equals("server")) {
                GenerarDataFile.main(arg2);
            } else if (args[0].equals("client")) {
                ObtenerDataFile.main(arg2);
            }
        } else {
            logger.error("Faltan parametros, solo se recibio:" + args.length);
        }
        long tF = System.currentTimeMillis();
        if (logger.isInfoEnabled()) {
            long dT = tF - t0;
            Time t = new Time(dT - TimeZone.getDefault().getRawOffset());
            logger.info("FINALIZADA APLICACION A LAS: " + new Date());
            logger.info("APLICACION DURO:" + t);
        }
        System.out.println("CR-Datafile ejecutado");
    }
}