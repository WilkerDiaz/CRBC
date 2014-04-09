/**
 * =============================================================================
 * Proyecto   : PoliticaMant_ServCR
 * Paquete    : com.beco.ventas.politicasmantenimiento
 * Programa   : Filtro.java
 * Creado por : irojas
 * Creado en  : 23/06/2009 - 06:27:27 PM
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

package com.beco.ventas.politicasmantenimiento;

/**
 *	Esta clase refiere a los objetos que representan Filtro. 
 */
import java.io.*;


public class Filtro implements FilenameFilter{
    String extension;
    Filtro(String extension){
        this.extension=extension;
    }
    public boolean accept(File dir, String name){
        return name.endsWith(extension);
    }
}
