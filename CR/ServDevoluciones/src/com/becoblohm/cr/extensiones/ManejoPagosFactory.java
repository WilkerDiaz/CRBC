/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.extensiones
 * Programa   : ManejoPagosFactory.java
 * Creado por : irojas
 * Creado en  : 20/06/2007 - 03:35:35 PM
 *
 * (c) CENTROBECO, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 
 * Fecha       : 
 * Analista    : 
 * Descripción : 
 * =============================================================================
 */


package com.becoblohm.cr.extensiones;


public class ManejoPagosFactory extends CRExtensionFactory {

    private static ManejoPagos instance = null;


    public String getDefaultImplClass() {
        return "com.becoblohm.cr.extensiones.impl.manejarpago.DefaultManejoPagos";
    }


    public String getExtensionIntfName() {
        return "ManejoPagos";
    }

    /**
     * @return DefaultManejoPagos
     */
    public static ManejoPagos getInstance() {
        if (instance == null) {
            ManejoPagosFactory factInstance = new ManejoPagosFactory();
            ManejoPagosFactory.instance = (ManejoPagos) factInstance
                    .getExtensionInstance();
        }
        return instance;
    }
}
