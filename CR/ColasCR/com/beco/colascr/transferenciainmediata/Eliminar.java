/**
 * =============================================================================
 * Proyecto   : TransferenciaInmediataProdsCR
 * Paquete    : com.beco.colascr.transferenciainmediata
 * Programa   : Prueba.java
 * Creado por : gmartinelli
 * Creado en  : 22-nov-05 10:33:15
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1
 * Fecha       : 22-nov-05 10:33:15
 * Analista    : gmartinelli
 * Descripción : Implementación inicial.
 * =============================================================================
 */
package com.beco.colascr.transferenciainmediata; 

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;


/**
 * Descripción:
 * 
 */

public class Eliminar {

	public static void main(String[] args) {
		System.out.println("Iniciando Java Eliminar Tranf...");
		/* Archivo de Salida */
		File f = new File(args[0].trim());
		String pathArchivos = f.getAbsolutePath() + File.separatorChar;
		pathArchivos = pathArchivos.replace(File.separatorChar,'/');
		pathArchivos = pathArchivos.replaceAll("/","//");
		System.out.println(pathArchivos);
		try {
			FileOutputStream farchivoOut = new FileOutputStream(pathArchivos + "transfInmedProdCR");
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(farchivoOut));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			FileOutputStream farchivoOut = new FileOutputStream(pathArchivos + "transfInmedCodExt");
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(farchivoOut));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			FileOutputStream farchivoOut = new FileOutputStream(pathArchivos + "transfInmedPromoCR");
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(farchivoOut));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			FileOutputStream farchivoOut = new FileOutputStream(pathArchivos + "transfInmedPromTda");
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(farchivoOut));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			FileOutputStream farchivoOut = new FileOutputStream(pathArchivos + "transfInmedAtcm");
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(farchivoOut));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//promociones
		try {
			FileOutputStream farchivoOut = new FileOutputStream(pathArchivos + "transfInmedCondPromocion");
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(farchivoOut));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			FileOutputStream farchivoOut = new FileOutputStream(pathArchivos + "transfInmedDonacion");
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(farchivoOut));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			FileOutputStream farchivoOut = new FileOutputStream(pathArchivos + "transfInmedPromTdaExt");
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(farchivoOut));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			FileOutputStream farchivoOut = new FileOutputStream(pathArchivos + "transfInmedPromTda_Ext");
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(farchivoOut));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			FileOutputStream farchivoOut = new FileOutputStream(pathArchivos + "transfInmedPromoCR_Ext");
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(farchivoOut));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			FileOutputStream farchivoOut = new FileOutputStream(pathArchivos + "transfInmedTranPremControl");
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(farchivoOut));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
