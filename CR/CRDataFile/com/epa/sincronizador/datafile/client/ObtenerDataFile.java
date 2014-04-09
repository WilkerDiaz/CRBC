/*
 * $Id: ObtenerDataFile.java,v 1.7 2005/06/02 13:12:00 programa4 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: GeneradorDataFile
 * Paquete		: com.epa.sincronizador.datafile
 * Programa		: ObtenerDataFile.java
 * Creado por	: programa4
 * Creado en 	: 19/01/2005 02:31:23 PM
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
 * $Log: ObtenerDataFile.java,v $
 * Revision 1.7  2005/06/02 13:12:00  programa4
 * Agregadas referencias this
 *
 * Revision 1.6  2005/04/06 12:35:10  programa4
 * Arreglada situación donde el replace se aplicaba con modo temporary
 * Por ello al querer guardar las variaciones se perdian los datos que no
 * variaban
 *
 * Revision 1.5  2005/04/05 14:45:51  programa4
 * Actualizado javadoc
 * Se readapto para que tomara en cuenta el primer parametro
 * Se corrigio el mensaje de error de cuando se trabaja con un solo archivo temporal y asumia que no se bajaba ningun dato
 *
 * Revision 1.4  2005/04/04 23:27:34  programa4
 * Mayor detalle en datafile y arreglado que no
 * envie mensaje de error por falta de datafile.
 *
 * Revision 1.3  2005/04/04 19:48:37  programa4
 * Actualizacion de javadoc
 *
 * Revision 1.2  2005/03/30 17:50:05  programa4
 * Actualizacion de javadoc
 *
 * Revision 1.1  2005/03/09 18:53:37  programa4
 * Reorganizadas las clases en paquetes, ahora hay un paquete distinto para el client y para el server
 *
 * Revision 1.14  2005/03/08 23:57:15  programa4
 * Configurado para poder trabajar con archivos temporales intermedios.
 * Este será el esquema a utilizar con afiliado. donde se anexan lso registros a una tabla cuyo nombre
 * termine en _temp y que una vez completado el proceso se borra la tabla original y se sustituye
 * por la _temp
 *
 * Revision 1.13  2005/03/04 15:13:59  programa4
 * Eliminado mensaje solicitando un arreglo que ya fue efectuado
 *
 * Revision 1.12  2005/03/03 19:20:22  programa4
 * Se reactivo verificación de fecha del archivo
 *
 * Revision 1.11  2005/03/03 17:49:56  programa4
 * Agregado cierre de reader
 *
 * Revision 1.10  2005/03/03 17:44:17  programa4
 * Corregido problema de perdida de datos, este se causaba por que se creaba un BufferedReader
 * en cada llamada a obtenerDatos en CargarDataFile por ello ahora el BufferedReader se crea solo
 * desde la inicialización del CargarDataFile
 *
 * Revision 1.9  2005/03/03 15:13:06  programa4
 * Modificado para que cada archivo se procese en un hilo aparte.
 * Ahora este objeto no se construye directamente sino que debe hacerse a través de 2 métodos dispuestos para tal fin
 * Uno de ellos procesa los datafile sin esperar a que terminen los hilos
 * El otro espera a que todos los hilos hayan culminado.
 *
 * Revision 1.8  2005/03/03 13:18:04  programa4
 * Se mudan constantes que indican nombres propiedades a PropiedadesAplicacion,
 * Se corrigió que anteriormente no se arrojaba la excepción cuando se ejecutaba algún script
 * en CargarScriptDataFile
 *
 * Revision 1.7  2005/03/01 18:59:53  programa4
 * Se elimina el ThreadPool para poder controlar mejor si se actualiza exitosamente una tabla
 * Se baja totalmente un archivo, y el script va acumulando en una cola los subarchivos en
 * que se va seccionando.
 *
 * Revision 1.6  2005/02/25 00:17:05  programa4
 * Se agregó busqueda en planificador de fecha de ultima actualizacion y comparacion con fecha del archivo
 *
 * Revision 1.5  2005/02/24 22:00:37  programa4
 * Eliminada condicion de eliminar al Salir que corresponde a carga de DataFile
 *
 * Revision 1.4  2005/02/24 21:33:35  programa4
 * Unificacion de propiedades
 *
 * Revision 1.3  2005/01/26 13:34:24  programa4
 * Se agrega funcionalidad para que pueda soportar archivos que vengan predefinidos para
 * modo ignore o replace.
 * Esto se consigue colocando una preextension .ignore o .replace
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
package com.epa.sincronizador.datafile.client;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;

import org.apache.log4j.Logger;


import com.becoblohm.cr.mensajeria.ManejadorMensajes;
import com.epa.sincronizador.datafile.Navegador;
import com.epa.sincronizador.datafile.PropiedadesAplicacion;
import com.epa.sincronizador.datafile.client.dao.DAO;
import com.epa.sincronizador.datafile.client.dao.SincronizacionEntidad;
import com.epa.sincronizador.datafile.streams.GZIPLoggeableInputStream;

/**
 * <p>
 * Objeto que se encarga de obtener los DataFile de un origen que puede ser
 * cualquier URL definido en la propiedad <code>context</code>, aunque ya ha
 * sido comprobado con origenes samba (carpetas de Windows compartidas).
 * </p>
 * A través del método {@link #obtenerVariosDataFile(String[])}se pueden
 * obtener y procesar varios datafiles definidos en el array de
 * <code>String</code>, los para los cuales se abre un proceso para cada uno.
 * En este metodo se espera a que todos los procesos terminen para continuar.
 * </p>
 * <p>
 * Existe el metodo {@link #obtenerVariosDataFileSinEsperarlos(String[])}con
 * cual se puede iniciar el proceso de obtener y procesar varios datafile, pero
 * el programa continua sin esperar porque ellos terminen.
 * </p>
 * <p>
 * Los nombres de los archivos a descargar generalmente estaran en formato
 * <code><strong><em>Tabla</em></strong>.txt.gz</code>.
 * </p>
 * 
 * <p>
 * <a href="ObtenerDataFile.java.html"> <i>View Source </i> </a>
 * </p>
 * TODO eliminar ex-empleados
 * 
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.7 $ - $Date: 2005/06/02 13:12:00 $
 * @since 19/01/2005 FIXME Documentar ObtenerDataFile y clases asociadas en el
 *        paquete
 */
public class ObtenerDataFile implements Runnable {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(ObtenerDataFile.class);

    private static final boolean borrarAlSalir = PropiedadesAplicacion
            .getProperty(PropiedadesAplicacion.DELETE_ON_EXIT, "true")
            .equalsIgnoreCase("true");

    private static final String rootFile = PropiedadesAplicacion.getProperty(
            PropiedadesAplicacion.ROOT_FILE, System
                    .getProperty("java.io.tmpdir"));

    private static final String context = PropiedadesAplicacion
            .getProperty("context");

    private DAO dao = null;

    private URLConnection root = null;

    private String entityName;

    /**
     * @param entity
     * @throws IOException
     * @since 24/01/2005
     */
    protected ObtenerDataFile(String entity) throws IOException {
        this.entityName = entity;
        if (context != null) {
            if (context.startsWith("smb")) {
                String domain = PropiedadesAplicacion
                        .getProperty("context.domain");
                String username = PropiedadesAplicacion
                        .getProperty("context.username");
                String password = PropiedadesAplicacion
                        .getProperty("context.password");
                if (username == null || password == null) {
                    this.root = new SmbFile(context);
                } else {
                    NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(
                            domain, username, password);
                    this.root = new SmbFile(context, auth);
                }
            } else {
                this.root = new URL(context).openConnection();
            }
        }
    }

    /**
     * @param ultimaActualizacion
     *            ultima actualizacion que tuvo la entidad
     * @return Fecha de ultima modificación del archivo
     * @throws Exception
     */
    protected Timestamp obtenerDataFile(Timestamp ultimaActualizacion)
            throws Exception {
    	/* Condición agregada en la optimización de carga de productos
    	 * jperez 06-03-2012
    	 * Si la entidad a cargar es producto... y no es necesario 
    	 * cargar el pote completo, obtener los dataFile por fecha
    	 */
    	if(this.entityName.equals("CR.producto.txt.gz") && 
    			ultimaActualizacion.after(DAO.TIMESTAMP_1980_01_01)){
    		return obtenerDataFilePorFecha(ultimaActualizacion);
    	}else{
	        long t0 = System.currentTimeMillis();
	        Timestamp salida = new Timestamp(t0);
	        URLConnection f;      
	        if (this.root.getURL().getProtocol().equals("smb")) {
	            f = new SmbFile((SmbFile) this.root, this.entityName);
	        } else {
	            URL url = new URL(this.root.getURL(), this.entityName);
		        f = url.openConnection();
	        }
	
	        Timestamp ultimaModificacionArchivo = new Timestamp(f.getLastModified());
	        if (logger.isInfoEnabled()) {
	            logger.info("Se ubicará archivo: " + f.getURL() + ", de tamaño: "
	                    + f.getContentLength() + " y fecha: "
	                    + ultimaModificacionArchivo);
	        }
	
	        if (ultimaModificacionArchivo.after(ultimaActualizacion)) {
	            if (logger.isInfoEnabled()) {
	                logger.info("Se traerá archivo: " + f.getURL()
	                        + ", de tamaño: " + f.getContentLength());
	            }
	            Reader entrada = null;
	            if (this.entityName.endsWith(".gz")) {
	                this.entityName = this.entityName.substring(0, this.entityName
	                        .lastIndexOf(".gz"));
	                entrada = new InputStreamReader(new GZIPLoggeableInputStream(f
	                        .getInputStream()));
	            } else {
	                entrada = new InputStreamReader(f.getInputStream());
	            }
	
	            String extension = "";
	            int lastPointPos = this.entityName.lastIndexOf(".");
	            if (lastPointPos > 0) {
	                extension = this.entityName.substring(lastPointPos).trim();
	                this.entityName = this.entityName.substring(0, lastPointPos);
	            }
	            logger.info("La extension es: " + extension);
	
	            int numArchivo = 0;
	            File fileSalida = new File(rootFile + this.entityName + extension);
	            logger.info("ASIGNADA SALIDA: " + fileSalida.getAbsolutePath());
	            if (borrarAlSalir) {
	                fileSalida.deleteOnExit();
	            }
	
	            CargarDataFile carga = prepareCargaDataFile(entrada,
	                    this.entityName, extension, fileSalida);

	            while (carga.getData(new FileWriter(fileSalida))) {
	                if (logger.isInfoEnabled()) {
	                    logger.info("Se trajo archivo: "
	                            + fileSalida.getAbsolutePath() + ", de tamaño: "
	                            + fileSalida.length());
	                }
	                numArchivo++;
	                fileSalida = new File(rootFile + this.entityName + numArchivo
	                        + extension);
	                logger.info("ASIGNADA SALIDA: " + fileSalida.getAbsolutePath());
	                if (borrarAlSalir) {
	                    fileSalida.deleteOnExit();
	                }
	
	                if (carga instanceof CargarTextDataFile) {
	                    CargarTextDataFile textCarga = (CargarTextDataFile) carga;
	                    textCarga.setFile(fileSalida);
	                }
	            }
	            entrada.close();
	            if (logger.isInfoEnabled()) {
	                long tf = System.currentTimeMillis();
	                long dT = (tf - t0) / 1000;
	                logger.info("TARDO: " + dT + " segs. en traer archivo.");
	            }
	            if ((fileSalida.exists() && fileSalida.length() > 0)
	                    || numArchivo > 0) {
	                //SE LE SUMA UN SEGUNDO A LA ULT. MODIF. DEL ARCHIVO
	                salida = new Timestamp(
	                        ultimaModificacionArchivo.getTime() + 1000);
	            } else {
	                salida = ultimaActualizacion;
	                logger.error("NO SE GENERÓ NINGÚN ARCHIVO A PARTIR DE: "
	                        + this.entityName);
	            }
	        } else {
	            salida = ultimaActualizacion;
	            if (logger.isInfoEnabled()) {
	                logger.info("NO SE SINCRONIZARA LA ENTIDAD YA QUE LA "
	                        + "FECHA DE ULTIMA ACTUALIZACION ES SUPERIOR "
	                        + "A LA DEL ARCHIVO: \r\n\t" + ultimaActualizacion
	                        + " vs. " + ultimaModificacionArchivo);
	            }
	        }
	
	        if (logger.isInfoEnabled()) {
	            long tf = System.currentTimeMillis();
	            long dT = (tf - t0) / 1000;
	            logger.info("TARDO: " + dT + " segs. EL PROCESO CON LA ENTIDAD: "
	                    + this.entityName);
	        }
	        return salida;
    	}
    
    }
    
    /**
     * Función que obtiene los dataFile por fecha y carga la data
     * en la base de datos de la entidad.
     * @author jperez
     * @param ultimaActualizacion
     *            ultima actualizacion que tuvo la entidad
     * @return Fecha de ultima modificación del archivo
     * @throws Exception
     */
    protected Timestamp obtenerDataFilePorFecha(Timestamp ultimaActualizacion)
            throws Exception {

        long t0 = System.currentTimeMillis();
        Timestamp salida = new Timestamp(t0);
        Date ultAct =new Date(ultimaActualizacion.getTime());
        URLConnection f;
        ArrayList<SmbFile> archivos;
        String entity = this.entityName;
        Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.HOUR, -12);
        Date today = new Date(c1.getTime().getTime());

        Timestamp ultimaModificacionArchivo;
        //entity contiene la entidad concatenada con la fecha
        //Ej: CR.producto2012-02-03.txt.gz
        if (this.entityName.endsWith(".txt.gz")) {     	
            entity= this.entityName.substring(0, this.entityName
                    .lastIndexOf(".txt.gz"))+ultAct.toString()+".txt.gz";
        }
        // Si el protocolo a ser usado es samba
        if (this.root.getURL().getProtocol().equals("smb")) {        
	        //Busca el archivo en servidor
            f = new SmbFile((SmbFile) this.root,entity );
            //Si el archivo no existe
            if (!((SmbFile)f).exists()){
            	//Listar ordenadamente los archivos que cumplan el patrón 'entidad*.txt.gz'
            	Navegador n = new Navegador(this.entityName.substring(0, this.entityName
                        .lastIndexOf(".txt.gz")));  
            	archivos = n.listarArchivos(this.root);
            	if(archivos!=null){
	            	//Conseguir el archivo más reciente, anterior a la última fecha de actualización
	            	entity = getArchivoAnterior(archivos,ultAct,entity);
	            	//Obtener el datafile
	            	f = new SmbFile((SmbFile) this.root,entity );		
            	}else{
            		logger.error("NO FUE POSIBLE LISTAR LOS ARCHIVOS PARA LA ENTIDAD"+this.entityName);
            	}
            	//Notificar a soporte que hubo discrepancia entre caja y servidor
            	if (!((SmbFile)f).exists()){
            		if (ultAct.before(today))
            			ManejadorMensajes.mensajeErrorCarga(this.entityName);
            	}else{
            		if (ultAct.before(today))
            			ManejadorMensajes.mensajeErrorCarga(this.entityName, ultAct, entity);
            	}
            }
        } else {
            URL url = new URL(this.root.getURL(), entity);
	        f = url.openConnection();
        }

        ultimaModificacionArchivo = new Timestamp(f.getLastModified());
        if (logger.isInfoEnabled()) {
            logger.info("Se ubicará archivo: " + f.getURL() + ", de tamaño: "
                    + f.getContentLength() + " y fecha: "
                    + ultimaModificacionArchivo);
        }
        if (ultimaModificacionArchivo.after(ultimaActualizacion)) {
            if (logger.isInfoEnabled()) {
                logger.info("Se traerá archivo: " + f.getURL()
                        + ", de tamaño: " + f.getContentLength());
            }
            Reader entrada = null;
            if (this.entityName.endsWith(".gz")) {
                this.entityName = this.entityName.substring(0, this.entityName
                        .lastIndexOf(".gz"));
                entrada = new InputStreamReader(new GZIPLoggeableInputStream(f
                        .getInputStream()));
            } else {
                entrada = new InputStreamReader(f.getInputStream());
            }

            String extension = "";
            int lastPointPos = this.entityName.lastIndexOf(".");
            if (lastPointPos > 0) {
                extension = this.entityName.substring(lastPointPos).trim();
                this.entityName = this.entityName.substring(0, lastPointPos);
            }
            logger.info("La extension es: " + extension);

            int numArchivo = 0;
            File fileSalida = new File(rootFile + this.entityName + extension);
            logger.info("ASIGNADA SALIDA: " + fileSalida.getAbsolutePath());
            if (borrarAlSalir) {
                fileSalida.deleteOnExit();
            }
            CargarDataFile carga = prepareCargaDataFile(entrada,
                    this.entityName, extension, fileSalida);
            while (carga.getData(new FileWriter(fileSalida))) {
                if (logger.isInfoEnabled()) {
                    logger.info("Se trajo archivo: "
                            + fileSalida.getAbsolutePath() + ", de tamaño: "
                            + fileSalida.length());
                }
                numArchivo++;
                fileSalida = new File(rootFile + this.entityName + numArchivo
                        + extension);
                logger.info("ASIGNADA SALIDA: " + fileSalida.getAbsolutePath());
                if (borrarAlSalir) {
                    fileSalida.deleteOnExit();
                }

                if (carga instanceof CargarTextDataFile) {
                    CargarTextDataFile textCarga = (CargarTextDataFile) carga;
                    textCarga.setFile(fileSalida);
                }
            }
            entrada.close();
            if (logger.isInfoEnabled()) {
                long tf = System.currentTimeMillis();
                long dT = (tf - t0) / 1000;
                logger.info("TARDO: " + dT + " segs. en traer archivo.");
            }
            if ((fileSalida.exists() && fileSalida.length() > 0)
                    || numArchivo > 0) {
                //SE LE SUMA UN SEGUNDO A LA ULT. MODIF. DEL ARCHIVO
                salida = new Timestamp(
                        ultimaModificacionArchivo.getTime() + 1000);
            } else {
                salida = ultimaActualizacion;
                logger.error("NO SE GENERÓ NINGÚN ARCHIVO A PARTIR DE: "
                        + this.entityName);
            }
        } else {
            salida = ultimaActualizacion;
            if (logger.isInfoEnabled()) {
                logger.info("NO SE SINCRONIZARA LA ENTIDAD YA QUE LA "
                        + "FECHA DE ULTIMA ACTUALIZACION ES SUPERIOR "
                        + "A LA DEL ARCHIVO: \r\n\t" + ultimaActualizacion
                        + " vs. " + ultimaModificacionArchivo);
            }
        }

        if (logger.isInfoEnabled()) {
            long tf = System.currentTimeMillis();
            long dT = (tf - t0) / 1000;
            logger.info("TARDO: " + dT + " segs. EL PROCESO CON LA ENTIDAD: "
                    + this.entityName);
        }

        return salida;
    }

    /**
     * @param reader
     * @param nombre
     * @param extension
     * @param fileSalida
     * @return Objeto que se encargara de cargar el DataFile
     */
    protected CargarDataFile prepareCargaDataFile(Reader reader, String nombre,
            String extension, File fileSalida) {
        CargarDataFile carga = null;
        if (extension.equalsIgnoreCase(".sql")) {
            carga = new CargarScriptDataFile(reader);
        } else if (extension.equalsIgnoreCase(".txt")) {
            String createTableScript = null;
            if (PropiedadesAplicacion.getProperty(
                    nombre + ".MySQL.temporarymode", "false").equalsIgnoreCase(
                    "true")) {
                createTableScript = this.dao.getCreateTable(nombre);
            }

            final String IGNORE = ".ignore";
            final String REPLACE = ".replace";
            String modo = PropiedadesAplicacion.getProperty(nombre + ".modo",
                    CargarTextDataFile.MODO_IGNORE);
            if (nombre.lastIndexOf(IGNORE) > 0) {
                nombre = nombre.substring(0, nombre.lastIndexOf(IGNORE));
                modo = CargarTextDataFile.MODO_IGNORE;
            } else if (nombre.lastIndexOf(REPLACE) > 0) {
                nombre = nombre.substring(0, nombre.lastIndexOf(REPLACE));
                modo = CargarTextDataFile.MODO_REPLACE;
            }
            carga = new CargarTextDataFile(reader, nombre, fileSalida, modo,
                    createTableScript);

        } else {
            carga = new CargarDataFile(reader);
        }
        return carga;
    }

    /**
     * @param entidad
     */
    private void procesarDataFile(String entidad) {
        try {
            this.dao = new DAO();
            SincronizacionEntidad dataEntidad = this.dao
                    .getSincronizacionEntidad(entidad);
            Timestamp actualizacion = null;
            try {
            	//Si el planificador está vacio
                if (dataEntidad.getFallasincronizador().equalsIgnoreCase("S") && 
                		!dataEntidad.getActualizacion().after(DAO.TIMESTAMP_1980_01_01)) {
                    dataEntidad.setActualizacion(DAO.TIMESTAMP_1980_01_01);
                }
                actualizacion = obtenerDataFile(dataEntidad.getActualizacion()); 
                dataEntidad.setActualizacion(actualizacion);
                dataEntidad.setFallasincronizador("N");
            } catch (Exception e1) {
                logger.error("ERROR OBTENIENDO ENTIDAD: " + entidad, e1);
                e1.printStackTrace();
                dataEntidad.setFallasincronizador("S");
                //Comentada la linea de resetear la ultima fecha de actualizacion
                // a TIMESTAMP_1980_01_01. por jperez durante la optimización de carga.
                //actualizacion = DAO.TIMESTAMP_1980_01_01;
            }
            this.dao.setSincronizacionEntidad(dataEntidad, actualizacion);
        } catch (Exception e) {
            logger.error("ERROR REGISTRANDO SINCRONIZACION DE ENTIDAD: "
                    + entidad, e);
        } finally {
            if (this.dao != null) {
                this.dao.close();
            }
        }
        this.dao = null;
    }

    /**
     * Inicia la tarea de procesar el archivo asignado
     * 
     * @see java.lang.Runnable#run()
     * 
     * @since 03/03/2005
     */
    public void run() {
        procesarDataFile(this.entityName);
    }

    /**
     * <p>
     * Procesa varios datafile, cada uno en un proceso aparte. A través de éste
     * método se esperará a que se procesen todos.
     * </p>
     * 
     * @param listaDataFile
     *            lista de archivos a procesar. La lista debe contener los
     *            nombres de los archivos, así que procure colocar la extensión
     *            tal como se encuentra en el servidor.
     * 
     * @throws Exception
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
    public static void obtenerVariosDataFile(String[] listaDataFile)
            throws Exception {
        ArrayList<Thread> procesos = obtenerVariosDataFileSinEsperarlos(listaDataFile);
        while (procesos.size() > 0) {
            if (logger.isDebugEnabled()) {
                logger.debug("Revisar si ya todos los procesos terminaron");
            }
            for (int j = 0; j < procesos.size(); j++) {
                Thread thProceso = (Thread) procesos.get(j);
                if (!thProceso.isAlive()) {
                    logger.info(thProceso.getName() + " ya procesado");
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
     * Procesa varios datafile, cada uno en un proceso aparte. A través de éste
     * método no se esperará a que se procesen todos.
     * 
     * @param listaDataFile
     *            lista de archivos a procesar. La lista debe contener los
     *            nombres de los archivos, así que procure colocar la extensión
     *            tal como se encuentra en el servidor.
     * @return Lista con <code>Thread</code>, cada uno procesando un datafile
     * @throws IOException
     */
    /*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
    public static ArrayList<Thread> obtenerVariosDataFileSinEsperarlos(
            String[] listaDataFile) throws IOException {
        ArrayList<Thread> procesos = new ArrayList<Thread>(listaDataFile.length);
        for (int i = 0; i < listaDataFile.length; i++) {
            String string = listaDataFile[i];
            ObtenerDataFile dataFileProcess = new ObtenerDataFile(string);
            Thread thProceso = new Thread(dataFileProcess, string);
            procesos.add(thProceso);
            thProceso.start();
            logger.info("Proceso: " + thProceso.getName() + " iniciado");
        }
        return procesos;
    }
    
    /**
     * Función que dada una lista de archivos y una entidad consigue el archivo
     * siguiente en la lista con fecha de actualizacion anterior más cercana
     * a la fecha de última actualización
     * @author jperez
     * @param archivos
     * @param fecha
     * @param entity
     * @return
     */
    public String getArchivoAnterior(ArrayList<SmbFile> archivos,Date fecha,String entity){
		int index = 0;
		String fechaString;
		Date fechaArchivo;
		//Para cada archivo en la lista
    	for(SmbFile archivo:archivos){
    		fechaString = archivo.getName().substring(this.entityName.substring(0, this.entityName
                    .lastIndexOf(".txt.gz")).length(), archivo.getName().lastIndexOf(".txt.gz"));
    		//Si no es el pote completo
    		if(!fechaString.equals("")){
    			fechaArchivo = Date.valueOf(fechaString);
    			//y su fecha es menor a la fecha de actualización
        		if(fechaArchivo.before(fecha)){
        			//asignar el nuevo nombre a entity
        			entity = archivos.get(index).getName();
        			break;
        		}
        	//Si es el pote completo
    		}else{
    			//asigna directamente el nuevo nombre a entity
    			entity = archivos.get(index).getName();
    			break;
    		}	
    		index++;
    	}
		return entity;
	}

    /**
     * @param args
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            logger.fatal("Debe indicar los archivos que desea descargar");
            System.exit(-1);
        }
        try {
            logger
                    .warn("SI UTILIZA DATA FILES DE GRAN MAGNITUD, LE RECOMENDAMOS"
                            + " INCREMENTAR EL VALOR DE innodb_lock_wait_timeout EN MySQL");
            ObtenerDataFile.obtenerVariosDataFile(args);
            logger
                    .warn("SI UTILIZA DATA FILES DE GRAN MAGNITUD, LE RECOMENDAMOS"
                            + " INCREMENTAR EL VALOR DE innodb_lock_wait_timeout EN MySQL");
        } catch (Exception e) {
            logger.error("main(String[])", e);
        }
    }

}