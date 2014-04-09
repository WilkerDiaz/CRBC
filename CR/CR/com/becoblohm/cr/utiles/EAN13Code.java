/*
 * Creado el 18-nov-2004
 *
 */
package com.becoblohm.cr.utiles;

/**
 * Clase para la generación del código de barra de los usuarios, 
 * basado en el funcionamiento del código EAN13 y la forma de generación
 * del código usada por Menu EPA.
 * 
 * @author Programa8  - Arístides Castillo
 *
 */
public class EAN13Code {
	/**
	 *  Retorna la representación EAN13 de una cadena de dígitos.
	 *  Devuelve null en caso que contenga un caracter no númerico o 
	 *  la cadena sea mayor a 12 caracteres.
	 *  La cadena de retorno puede tener 12 o 13 caracteres basado en
	 *  un comportamiento particular de los lectores de código, que
	 *  ignoran el primer caracter del código en caso que sea '0'.
	 *  Este método devuelve la misma cadena que retornaria un scanner
	 *  Symbol LS1902T.
	 * 
	 * @param data La cadena numérica a ser convertida a EAN13
	 * @return Cadena de 12 o 13 caracteres en representacion EAN13.
	 */
	public static String getCode(String data) {
		StringBuffer buffer = new StringBuffer(data);
		if (data.length() <= 12) {
			while (buffer.length() < 12) {
				buffer.append("0");
			}
			try {
				int suma = 0;
				for (int i = 0; i < buffer.length(); i++) {
					int factorCorreccion = 1;
					if (i % 2 == 1) {
						// even
						factorCorreccion = 3;
					} 
					suma += Integer.parseInt(Character.toString(buffer.charAt(i))) * factorCorreccion;
				}
				int digitCtrl = (10 - (suma % 10)) % 10;
				buffer.append(digitCtrl);
				if (buffer.charAt(0) == '0'){
					buffer.deleteCharAt(0);
				}
			} catch (NumberFormatException e) {
				return null;
			}
		} else {
			return null;
		}
		return buffer.toString();
	}
	
	/**
	 *  Retorna la representacion EAN 13 para los usuarios cajeros de
	 *  ferreteria EPA, tal como es devuelta por los códigos de barra
	 *  generados por Menu EPA al ser leidos en el scanner.
	 *   
	 * 
	 * @param ficha Ficha del empleado
	 * @param clave Clave del empleado
	 * @return Cadena EAN13 de 12 o 13 caracteres.
	 * @see getCode(String)
	 */
	public static String getCode(String ficha, String clave){
		StringBuffer fichaBuf = new StringBuffer(ficha);
		StringBuffer claveBuf = new StringBuffer(clave);
		while (fichaBuf.length() < 5) {
			fichaBuf.insert(0, 0);
		}
		while (claveBuf.length() < 5) {
			claveBuf.insert(0, 0);
		}
		return getCode(claveBuf.toString() + fichaBuf.toString());
	}
	
	public static void main(String args[]) {
		if (args.length > 0) {
			if (args.length == 2) {
				System.out.println(getCode(args[0], args[1]));
			} else {
				System.out.println(getCode(args[0]));
			}
		}
	}

}

