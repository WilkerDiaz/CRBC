/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.extensiones
 * Programa   : ManejadorReportesFactory.java
 * Creado por : gmartinelli
 * Creado en  : 27/03/2008 - 03:43:35 PM
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


public class ManejadorReportesFactory extends CRExtensionFactory {

    private static ManejadorReportes instance = null;


    public String getDefaultImplClass() {
        return "com.becoblohm.cr.extensiones.impl.reportes.DefaultManejadorReportes";
    }


    public String getExtensionIntfName() {
        return "ManejadorReportes";
    }

    /**
     * @return DefaultManejoPagos
     */
    public static ManejadorReportes getInstance() {
        if (instance == null) {
            ManejadorReportesFactory factInstance = new ManejadorReportesFactory();
            ManejadorReportesFactory.instance = (ManejadorReportes) factInstance
                    .getExtensionInstance();
        }
        return instance;
    }
}
