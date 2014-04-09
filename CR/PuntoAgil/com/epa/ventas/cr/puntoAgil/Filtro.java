/**
 * =============================================================================
 * Proyecto   : PuntoAgil
 * Paquete    : com.epa.ventas.cr.puntoAgil
 * Programa   : Filtro.java
 * Creado por : irojas
 * Creado en  : 22/03/2010 - 04:15:27 PM
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
package com.epa.ventas.cr.puntoAgil;

/**
 *	Esta clase refiere a los objetos que representan Filtro. 
 */
import java.io.*;


public class Filtro implements FilenameFilter{
    String secuencia;
    String secuencia2;
    
    Filtro(String secuencia, String secuencia2){
        this.secuencia=secuencia;
        this.secuencia2=secuencia2;
    }
    
    public boolean accept(File dir, String name){
       int encontradoFecha = name.indexOf(secuencia);
       int encontradoCierre = name.indexOf(secuencia2);
       if (encontradoFecha >= 0 && encontradoCierre >=0) {
    	   return true;
       } else {
    	   return false;
       }
    }
}

