/**
 * =============================================================================
 * Proyecto   : CRTdas
 * Paquete    : com.becoblohm.cr.utiles
 * Programa   : Validaciones.java
 * Creado por : irojas
 * Creado en  : 06/02/2007 - 09:49:39 AM
 *
 * (c) CENTROBECO, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 
 * Fecha       : 
 * Analista    : 
 * Descripción : Modificaciones a algoritmo de Control de EPA para agregar 
 * 			     adaptaciones de validación de BECO
 * =============================================================================
 */

/**
* =====================================================================
 * Material Propiedad Ferretería EPA C.A. 
 *
 * Proyecto		: EpaComun
 * Paquete		: com.epa.comun.utiles
 * Programa		: Validaciones.java
 * Creado por	: smercadeo
 * Creado el 	: 31/10/2005 10:15:32 AM
 *
 * (C) Copyright 2005 Ferretería EPA C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisión	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: Validaciones.java,v $
 * Revision 1.3  2006/11/06 13:55:52  smercadeo
 * *** empty log message ***
 *
 *
 * =====================================================================
 */
package com.becoblohm.cr.utiles;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * Validaciones de RIF y Cedulas
 * <p>
 * <a href="Validaciones.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author programa4 - $Author: smercadeo $
 * @version $Revision: 1.3 $ - $Date: 2006/11/06 13:55:52 $
 * @since 27/10/2005
 */
/*
* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se comentaron variables sin uso
* Fecha: agosto 2011
*/
public class Validaciones {
	private final char VENEZOLANO = 'V';
	//private final char JURIDICO = 'J';
	//private final char GUBERNAMENTAL = 'G';
	private final char EXTRANJERO = 'E';
	private final char PASAPORTE = 'P';
	private final char EMPLEADO = 'N';
	private final int minCI = 999;
	private final int maxCI = 70000000;
	private final int minCIExt = 99999;
	private final int maxCIExt = 85000000;
	
	/**
	 * Método de validación de Rif y Cédulas de clientes, basados en las
	 * indicaciones de Control (EPA)
	 * 
	 * @param rifCed
	 *            Rif o cédula a validar
	 * @param esJuridico
	 *            Indica si es o no un cliente jurídico
	 * @return Verdadero si la cédula o RIF es válido
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Set'
	* Fecha: agosto 2011
	*/
	public static boolean validarRifCedula(String rifCed, boolean esJuridico) {
		boolean result = true;
		Set<Character> hset = new HashSet<Character>();
		hset.add(new Character('J'));
		hset.add(new Character('V'));
		hset.add(new Character('E'));
		hset.add(new Character('G'));
		hset.add(new Character('P'));

		if (esJuridico) {
			if (rifCed == null || rifCed.length() != 10
					|| !hset.contains(new Character(rifCed.charAt(0)))
					|| !mod11Valido(rifCed)) {
				result = false;
			}
		} else {
			// Es una cedula - Persona Natural
			if (rifCed == null || rifCed.length() < 5
					|| !StringUtils.isNumeric(rifCed)) {
				result = false;
			}
		}
		return result;
	}
	
	/**
	 * Método de validación de Rif y Cédulas de clientes, basados en las
	 * indicaciones de Control (EPA). 
	 * Modificado por BECO para que valide siempre por CI y si no es válida
	 * verifique luego si es un RIF válido. 
	 * Eliminación de parámetro esJuridico para pasar el tipo de cliente
	 * V, J, G, P o E
	 * 
	 * @param rifCed
	 *            Rif o cédula a validar
	 * @param tipoCte
	 *            Indica el tipo de cliente: V,J,G,P o E
	 * @return Verdadero si la cédula o RIF es válido
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Set'
	* Fecha: agosto 2011
	*/
	public boolean validarRifCedula(String rifCed, char tipoCte) {
		boolean result = false;
		Set<Character> hset = new HashSet<Character>();
		hset.add(new Character('J'));
		hset.add(new Character('V'));
		hset.add(new Character('E'));
		hset.add(new Character('G'));
		hset.add(new Character('P'));
		//Se eliminan los espacios en blanco que pueda traer el campo
		//jperez 31-05-2012
		rifCed = rifCed.trim();
		int numRifCed = 0;

		//Contemplamos el caso de los clientes que son empleados: cso de TipoCte = 'N'
		if (tipoCte == this.EMPLEADO) {
			return true;
		} else {
			//Verificamos primero si es un tipo válido y un número de CI o RIF correcto
			if (hset.contains(new Character(tipoCte)) && rifCed != null && StringUtils.isNumeric(rifCed)) {
				numRifCed = (new Integer(rifCed.trim())).intValue();
				String rifCedX = tipoCte + rifCed.trim();
				
				//************* VERIFICACIÓN 1 ***********************
				//**** Para todos verificamos primero si es una CI de tipo V válida
				//****************************************************
				if (tipoCte == this.VENEZOLANO && numRifCed > this.minCI && numRifCed < this.maxCI) {
						result = true;	
				}
				
				//************* VERIFICACIÓN 2***********************
				//**** En caso de no ser una CI de tipo V válida. Verificamos que se un número de CI extranjera válido
				//****************************************************
				else if (tipoCte == this.EXTRANJERO && numRifCed > this.minCIExt && numRifCed < this.maxCIExt) {
					result = true;	
				}
				
				//************* VERIFICACIÓN 3**********************
				//En caso de no ser una CI de tipo V o E válida se chequea si es un RIF válido
				//***************************************************
			 	else if (rifCedX.length() == 10 && mod11Valido(rifCedX)) {
						result = true;
				}
		
				//************* VERIFICACIÓN 4***********************
				//En caso de no ser una CI de tipo V, E o RIF válido se chequea si es un posible pasaporte válido
				//***************************************************	
				else if (tipoCte == this.PASAPORTE && rifCed.trim().length() >= 5) {
					result = true;
				} 
			} else { //Es un tipo de cliente no válido o un número de CI o RIF inválido
				result = false;
			}
		}
		return result;
	}

	/**
	 * Método que determina si una cadena se ajusta al algoritmo del módulo 11,
	 * establecido para verificación de RIF por el Seniat
	 * 
	 * @param text
	 *            RIF a validar
	 * @return Verdadero si se ajusta al algoritmo del módulo 11
	 */
	private static boolean mod11Valido(String text) {
		boolean result = true;
		// Verificación del módulo 11
		if (StringUtils.isNumeric(text.substring(1))) {
			if (text.length() != 10) {
				return false;
			}
			//String a = text.substring(0, 9);
			String b = text.substring(9, 10);
			char a1 = text.charAt(0);
			char a2 = text.charAt(1);
			char a3 = text.charAt(2);
			char a4 = text.charAt(3);
			char a5 = text.charAt(4);
			char a6 = text.charAt(5);
			char a7 = text.charAt(6);
			char a8 = text.charAt(7);
			char a9 = text.charAt(8);
			char a10 = text.charAt(9);

			int x1 = (2 * Integer.parseInt("" + a9));
			int x2 = (3 * Integer.parseInt("" + a8));
			int x3 = (4 * Integer.parseInt("" + a7));
			int x4 = (5 * Integer.parseInt("" + a6));
			int x5 = (6 * Integer.parseInt("" + a5));
			int x6 = (7 * Integer.parseInt("" + a4));
			int x7 = (2 * Integer.parseInt("" + a3));
			int x8 = (3 * Integer.parseInt("" + a2));
			int x9 = 0;

			switch (a1) {
			case 'V':
				x9 = 4;
				break;
			case 'E':
				x9 = 8;
				break;
			case 'J':
				x9 = 12;
				break;
			case 'P':
				x9 = 16;
				break;
			case 'G':
				x9 = 20;
				break;
			default:
				return false;
			}

			int xt1 = x1 + x2 + x3 + x4 + x5 + x6 + x7 + x8;
			int xt = xt1 + x9;
			int resul2 = xt % 11;
			int resul = 11 - resul2;

			if (resul == 10 || resul == 11)
				resul = 0;

			if (a1 == 'V' || a1 == 'E') {
				if (a10 == Character.forDigit(resul, 10))
					result = true;
				else {
					result = false;
				}
			} else if (resul != Integer.parseInt(b)) {
				result = false;
			}
		} else {
			result = false;
		}
		return result;
	}
	
	public static String validarString(String valor1)
	{
		valor1 = valor1.replace('?',' ');
		valor1 = valor1.replace('¿',' ');
		valor1 = valor1.replace('"',' ');
		valor1 = valor1.replace('\'',' ');
		valor1 = valor1.replace('/',' ');
	return valor1;
	}
}
