/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.utiles
 * Programa   : VectorUtiles.java
 * Creado por : jgraterol
 * Creado en  : 20/08/2008 10:53:41 PM
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
package com.becoblohm.cr.utiles;

import java.util.Vector;

public class VectorUtil {

	/***
	 * Verifica si en entero x esta dentro de el vector v
	 * @param v
	 * @param x
	 * @return boolean
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static boolean contieneInt(Vector<Integer> v, int x){
		boolean contiene = false;
		try{
			for(int i=0;i<v.size();i++){
				if((v.elementAt(i)).intValue() == x){
					return true;
				}
			}
		} catch(ClassCastException e){
			//No es un vecetor de integers
		}
		return contiene;
	}
	
}
