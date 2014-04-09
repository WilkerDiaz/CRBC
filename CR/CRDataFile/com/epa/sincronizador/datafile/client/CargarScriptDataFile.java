/*
 * $Id: CargarScriptDataFile.java,v 1.2 2005/06/02 13:12:00 programa4 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: GeneradorDataFile
 * Paquete		: com.epa.sincronizador.datafile
 * Programa		: CargarScriptDataFile.java
 * Creado por	: programa4
 * Creado en 	: 19/01/2005 01:39:55 PM
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
 * $Log: CargarScriptDataFile.java,v $
 * Revision 1.2  2005/06/02 13:12:00  programa4
 * Agregadas referencias this
 *
 * Revision 1.1  2005/03/09 18:53:37  programa4
 * Reorganizadas las clases en paquetes, ahora hay un paquete distinto para el client y para el server
 *
 * Revision 1.7  2005/03/07 12:29:35  programa4
 * Configurado para ejecutar un solo script por archivo, y un scriptrunner que los va ejecutando en secuencia
 *
 * Revision 1.6  2005/03/03 17:44:17  programa4
 * Corregido problema de perdida de datos, este se causaba por que se creaba un BufferedReader
 * en cada llamada a obtenerDatos en CargarDataFile por ello ahora el BufferedReader se crea solo
 * desde la inicialización del CargarDataFile
 *
 * Revision 1.5  2005/03/03 13:18:04  programa4
 * Se mudan constantes que indican nombres propiedades a PropiedadesAplicacion,
 * Se corrigió que anteriormente no se arrojaba la excepción cuando se ejecutaba algún script
 * en CargarScriptDataFile
 *
 * Revision 1.4  2005/03/01 18:59:53  programa4
 * Se elimina el ThreadPool para poder controlar mejor si se actualiza exitosamente una tabla
 * Se baja totalmente un archivo, y el script va acumulando en una cola los subarchivos en
 * que se va seccionando.
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
 * ===========================================================================
 */
package com.epa.sincronizador.datafile.client;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;

import org.apache.log4j.Logger;

import com.epa.data.script.SQLScript;

/**
 * <code>
 *           Proyecto: GeneradorDataFile 
 *           Clase: CargarScriptDataFile
 * </code>
 * 
 * <p>
 * <a href="CargarScriptDataFile.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.2 $ - $Date: 2005/06/02 13:12:00 $
 * @since 19/01/2005 @
 */
class CargarScriptDataFile extends CargarDataFile {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(CargarScriptDataFile.class);

    private int cuentaBloques = 0;

    private SQLScript script = new SQLScript();

    /**
     * @param is
     * @since 19/01/2005
     */
    public CargarScriptDataFile(InputStream is) {
        super(is);
    }

    /**
     * @param reader
     * @since 19/01/2005
     */
    public CargarScriptDataFile(Reader reader) {
        super(reader);
    }

    /*
     * (sin Javadoc)
     * 
     * @see com.epa.sincronizador.datafile.CargarDataFile#printToWriter(java.io.PrintWriter,
     *      java.lang.String) @param writer @param line
     * @since 19/01/2005
     */
    protected void printToWriter(PrintWriter writer, String line)
            throws Exception {
        super.printToWriter(writer, line);

        if (this.script == null) {
            this.script = new SQLScript();
        }
        this.script.addSentencia(line);
        if (line.endsWith(";")) {
            this.cuentaBloques++;
        }
    }

    protected void close(boolean cerrarScript) throws Exception {
        String nombre = "BLOQUE " + this.cuentaBloques;
        if (cerrarScript) {
            logger.info("AGREGANDO SCRIPT A EJECUCION: " + this.cuentaBloques);
            addScript(nombre, this.script);
            this.script = null;
        }
        super.close(cerrarScript);
    }
}