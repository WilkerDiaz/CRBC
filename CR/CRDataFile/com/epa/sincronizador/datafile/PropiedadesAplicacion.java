/*
 * $Id: PropiedadesAplicacion.java,v 1.6 2005/11/24 18:29:56 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: GeneradorDataFile
 * Paquete		: com.epa.sincronizador.datafile
 * Programa		: PropiedadesAplicacion.java
 * Creado por	: programa4
 * Creado en 	: 24/02/2005 05:02:15 PM
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
 * $Log: PropiedadesAplicacion.java,v $
 * Revision 1.6  2005/11/24 18:29:56  programa8
 * Mejora para localizar en cada instalación las propiedades del sistema
 *
 * Revision 1.5  2005/03/09 18:53:26  programa4
 * Reorganizadas las clases en paquetes, ahora hay un paquete distinto para el client y para el server
 *
 * Revision 1.4  2005/03/04 15:19:36  programa4
 * Se mueven varias propiedades  a ser variables de objeto
 * Se definen varios nombres de propiedades como constantes en PropiedadesAplicacion
 *
 * Revision 1.3  2005/03/03 13:18:03  programa4
 * Se mudan constantes que indican nombres propiedades a PropiedadesAplicacion,
 * Se corrigió que anteriormente no se arrojaba la excepción cuando se ejecutaba algún script
 * en CargarScriptDataFile
 *
 * Revision 1.2  2005/02/24 22:00:50  programa4
 * Agregadas propiedades
 *
 * Revision 1.1  2005/02/24 21:33:35  programa4
 * Unificacion de propiedades
 *
 * ===========================================================================
 */
package com.epa.sincronizador.datafile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * Facilita la carga de las propiedades de la aplicacion.
 * <p>
 * <a href="PropiedadesAplicacion.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author programa4 - $Author: programa8 $
 * @version $Revision: 1.6 $ - $Date: 2005/11/24 18:29:56 $
 * @since 24/02/2005 @
 */
public class PropiedadesAplicacion {
    private static final String DEFAULT_PATH_1 = "/opt/CR/CR-DataFile/";
    private static final String DEFAULT_PATH_2 = "/opt/CR_1.0/";
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(PropiedadesAplicacion.class);

    /**
     * <code>PROPIEDADES</code> un <code>Properties</code>
     */
    public static Properties PROPIEDADES = null;

    static {
        if (PROPIEDADES == null) {
            PROPIEDADES = loadProperties();
        }
    }

    /**
     * <code>SCRIPTBUFFERSIZE</code> Cantidad de sentencias a acumular para
     * mandar a procesar.
     */
    public static final String SCRIPTBUFFERSIZE = "SCRIPTBUFFERSIZE";

    /**
     * Nombre de la propiedad que define el tamaño del bloque en los que
     * mostrara un mensaje por el logger indicando avance
     */
    public static final String BLOQUEDEBUG = "BLOQUEDEBUG";

    /**
     * Nombre de la propiedad que define el tamaño del bloque en los que se
     * agruparán varios Map en arrays.
     */
    public static final String MAPARRAYSIZE = "MAPARRAYSIZE";

	/**
	 * Nombre de la propiedad que define el tamaño del bloque en los que se
	 * agruparán varios Map en arrays.
	 */
	public static final String BATCHROWS = "BATCHROWS";

    /**
     * Nombre de la propiedad que indica si un archivo se borrará al terminar el
     * programa.
     */
    public static final String DELETE_ON_EXIT = "DataFile.deleteOnExit";

    /**
     * Nombre de la propiedad que donde se crearán los temporales en el cliente.
     */
    public static final String ROOT_FILE = "DataFile.rootFile";

    /**
     * Nombre de la propiedad que establece tamaño de buffer.
     */
    public static final String BUFFERSIZE = "buffersize";

    /**
     * Nombre de la propiedad que establece si se usará batch para los script.
     */
    public static final String BATCH_MODE = "DataFile.batchMode";

    /**
     * Nombre de la propiedad que establece la clase que utilizará para crear los datafile.
     */
    public static final String DATAFILE_CLASSNAME = "DataFile.className";

    /**
     * Nombre de la propiedad que establece la versión de MySQL para la cual se prepararán los script.
     */
    public static final String MySQL_VERSION = "DataFile.MySQL_VERSION";

    /**
     * Carga las propiedades del programa.
     * 
     * @return propiedades del programa.
     */
    public static Properties loadProperties() {
        Properties prop = new Properties();
        try {
            prop.load(cargarRecurso("GenerarDataFile.properties"));
        } catch (IOException e) {
            logger.fatal("NO SE PUDO CARGAR PROPIEDADES");
            System.exit(-1);
        }
        return prop;
    }

    /**
     * Busca leer el valor de la propiedad.
     * @param arg0
     * @return valor de propiedad
     */
    public static String getProperty(String arg0) {
        return PROPIEDADES.getProperty(arg0);
    }

    /**
     * Busca leer el valor de la propiedad, devolviendo un valor por defecto si 
     * no lo logra obtener. 
     * @param arg0
     *            nombre de propiedad
     * @param arg1
     *            valor por defecto
     * @return valor de propiedad o su valor por defecto
     */
    public static String getProperty(String arg0, String arg1) {
        return PROPIEDADES.getProperty(arg0, arg1);
    }
    
    /**
     * <p>
     * Trata de cargar un recurso (archivo). Lo busca en el siguiente orden
     * </p>
     * <ol>
     * <li>user.home</li>
     * <li>DEFAULT_PATH_1</li>
     * <li>DEFAULT_PATH_2</li>
     * <li>user.dir</li>
     * <li>class</li>
     * <li>classLoader</li>
     * 
     * @param recurso
     * @return InputStream al recurso solicitado
     * @throws FileNotFoundException
     */
    public static InputStream cargarRecurso(final String recurso)
            throws FileNotFoundException {
        InputStream resourceIS;
        // Busco en la carpeta de usuario
        String resourcePath = System.getProperty("user.home") + "/" + recurso;
        File resourceFile = new File(resourcePath);
        if (resourceFile.exists() && resourceFile.isFile()) {
            resourceIS = new FileInputStream(resourcePath);
        } else {
            resourcePath = DEFAULT_PATH_1 + recurso;
            resourceFile = new File(resourcePath);
            if (resourceFile.exists() && resourceFile.isFile()) {
                resourceIS = new FileInputStream(resourcePath);
            } else {
                resourcePath = DEFAULT_PATH_2 + recurso;
                resourceFile = new File(resourcePath);
                if (resourceFile.exists() && resourceFile.isFile()) {
                    resourceIS = new FileInputStream(resourcePath);
                } else {
                    resourceFile = new File(recurso);
                    if (resourceFile.exists() && resourceFile.isFile()) {
                        resourceIS = new FileInputStream(resourceFile);
                    } else {
                        resourceIS = PropiedadesAplicacion.class
                            	.getResourceAsStream(recurso);
                        if (resourceIS == null) {
                            resourceIS = PropiedadesAplicacion.class.getClassLoader()
                                	.getResourceAsStream(recurso);
                        }
                    }
                }
            } 
        }
        if (resourceIS == null) {
            logger.error("No se pudo cargar recurso:" + recurso);
        }
        return resourceIS;
    }
}