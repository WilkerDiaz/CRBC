/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.cr.actualizadorPrecios.promociones.extensiones
 * Programa   : ManejadorReportesPromocionesFactory.java
 * Creado por : jgraterol
 * Creado en  : 04/12/2008 - 03:43:35 PM
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


package com.beco.cr.actualizadorPrecios.promociones.extensiones;

public class ManejadorReportesPromocionesFactory extends CRExtensionFactoryPromociones {

    private static ManejadorReportesPromociones instance = null;


    public String getDefaultImplClass() {
        return "com.beco.cr.actualizadorPrecios.promociones.extensiones.impl.DefaultReportesPromociones";
    }


    public String getExtensionIntfName() {
        return "ManejadorReportesPromociones";
    }
    
    /**
     * @return DefaultManejoPagos
     */
    public ManejadorReportesPromociones getInstance() {
        if (instance == null) {
            ManejadorReportesPromocionesFactory factInstance = new ManejadorReportesPromocionesFactory();
            ManejadorReportesPromocionesFactory.instance = (ManejadorReportesPromociones) factInstance
                    .getExtensionInstance();
        }
        return instance;
    }
}
