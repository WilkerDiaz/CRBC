/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.reportes
 * Programa   : Reporte.java
 * Creado por : gmartinelli
 * Creado en  : 04-ago-04 12:00:16
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1
 * Fecha       : 04-ago-04 12:00:16
 * Analista    : gmartinelli
 * Descripción : Implementación inicial.
 * =============================================================================
 */
package com.beco.cr.reportes.gd4;

import java.util.Vector;

/**
 * Descripción:
 * 	Superclase que implementa los metodos comunes para todos los reportes
 * del sistema de CR. Por ejemplo, centrar, justificar, alinearDerecha, etc.
 */

public class Reporte {

	// Variables necesarias para la impresion
	protected static int columnasImpresora = 44;
	public static int columnasImpresoraFiscal = 38;

	/**
	 * Permite crear una linea de caracteres (-) de tamanio especifico.
	 * @param anchoColumna Ancho de la linea de separacion
	 * @return String - La linea de separacion
	 */
	public static String crearLineaDeDivision(int anchoColumna) {
		return crearLineaDeDivision(anchoColumna, '-');
	}

	/**
	 * Permite crear una linea de tamanio especifico de algún caracter.
	 * @param anchoColumna Ancho de la linea de separacion
	 * @param caracter Caracter con que se realizará la division
	 * @return String - La linea de separacion
	 */
	public static String crearLineaDeDivision(int anchoColumna, char caracter) {
		String linea = "";
		for (int i=0; i<anchoColumna; i++)
			linea += String.valueOf(caracter);
		return linea;
	}

	/**
	 * Permite centrar una linea de caracteres en una fila de tamanio especifico.
	 * @param linea Cadena de caracteres a centrar.
	 * @param anchoColumna Ancho de la columna donde se debe centrar la linea.
	 * @return String - String que representa la cadena centrada.
	 */
	public static String centrar (String linea, int anchoColumna) {
		String lineaCentrada = "";
		int espaciosEnBlanco = (anchoColumna - linea.length()) / 2;
		for (int i=0; i<espaciosEnBlanco; i++)
			lineaCentrada += " ";
		return lineaCentrada += linea;
	}
	
	/**
	 * Permite justificar una linea de caracteres en una fila de tamanio especifico.
	 * @param textoIzq Texto a agregar en el lado izquierdo de la linea.
	 * @param textoDer Texto a agregar en el lado derecho de la linea.
	 * @param anchoColumna Ancho de la columna donde se debe justificar la linea.
	 * @return String - String que representa la cadena justificada.
	 */
	public static String justificar (String textoIzq, String textoDer, int anchoColumna) {
		String lineaJustificada = textoIzq;
		int espaciosEnBlanco = anchoColumna - textoIzq.length() - textoDer.length();
		for (int j=0; j<espaciosEnBlanco; j++)
			lineaJustificada += " ";
		return lineaJustificada += textoDer;
	}
	
	/**
	 * Permite alinear una cadena de caracteres a la derecha.
	 * @param linea Cadena de caracteres que se desea alinear a la derecha.
	 * @param anchoColumna Ancho de la columna donde se debe alinear.
	 * @return String - String que representa la cadena justificada.
	 */
	public static String alinearDerecha (String linea, int anchoColumna){
		String lineaJustificada = "";
		int espaciosEnBlanco = anchoColumna - linea.length();
		for (int i=0; i<espaciosEnBlanco; i++)
			lineaJustificada += " ";
		return lineaJustificada += linea;
	}

	/**
	 * Divide una cadena de texto en frases de longitud igual o menor a tamanioLinea
	 * @param tamanioLinea Tamaño de la linea
	 * @param texto Cadena de texto a centrar
	 * @return Vector - Lineas divididas
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<String> dividirEnLineas(int tamanioLinea, String texto) {
		Vector<String> lineasResultado = new Vector<String>();
		int letras = 0;
		boolean dividir = false;
		do {

			// Colocamos las palabras de las lineas
			dividir = ((letras + tamanioLinea) < texto.length());
			String lineaActual = ((letras + tamanioLinea) < texto.length())
						? texto.substring(letras, letras + tamanioLinea)
						: texto.substring(letras);
			int j = lineaActual.length();

			if (dividir) {
				// Buscamos donde cortar las palabras para la siguiente linea
				while ((j>1)&&(lineaActual.charAt(j-1) != '.') && (lineaActual.charAt(j-1) != ' ') &&
					   (lineaActual.charAt(j-1) != ',') && (lineaActual.charAt(j-1) != ':'))
					j--;
			}
			
			// Si no se puede cortar la linea, se coloca igual
			if (j<=1)
				j = lineaActual.length();
			lineasResultado.addElement(lineaActual.substring(0,j));
			letras += j;
		} while (letras < texto.length());
		return lineasResultado;
	}
	
	public static String convertirCadena(String mensaje) {
		String stringCaracteresAModificar="áéíóúÁÉÍÓÚñÑ°º";
		String stringCaracteresCorrectos="aeiouAEIOUnNoo";
		
		for(int i=0; i<mensaje.toCharArray().length; i++) {
			for(int y=0; y<stringCaracteresAModificar.toCharArray().length; y++) {
				if(mensaje.toLowerCase().charAt(i) == stringCaracteresAModificar.charAt(y)) {
					mensaje = mensaje.replace(mensaje.charAt(i), stringCaracteresCorrectos.charAt(y));
				}
			}
		}

		return mensaje.toUpperCase();
	}
	
}
