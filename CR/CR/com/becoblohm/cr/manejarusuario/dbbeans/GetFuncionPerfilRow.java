/**
 * =============================================================================
 * Proyecto   : CRBECOEPA
 * Paquete    : com.becoblohm.cr.manejarusuario.dbbeans
 * Programa   : GetFuncionPerfilRow.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 21/10/2003 10:01:57 AM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.0.1
 * Fecha       : 14/11/2003 04:15:41 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Ajuste por cambios en diseño de la Base de Datos
 * =============================================================================
 */
package com.becoblohm.cr.manejarusuario.dbbeans;
import java.sql.SQLException;

import com.ibm.db.beans.DBSelect;

/**
 * Esta clase representa una fila específica de un 
 * conjunto de resultados contenida en DBSelect.
 */

public class GetFuncionPerfilRow {
	private int rowNumber;
	private DBSelect select;

	/**
	 * Construye un objeto que representa una fila a partir de DBSelect.
	 */
	public GetFuncionPerfilRow(DBSelect aRef, int aRowNumber) {
		select = aRef;
		rowNumber = aRowNumber;
	}

	/**
	 * Devuelve el valor de la columna Funcionperfil_codperfil en la fila representada por este objeto.
	 */
	public Object getFuncionperfil_codperfil() throws SQLException {
		return select.getCacheValueAt(rowNumber, 1);
	}

	/**
	 * Devuelve el valor de la columna Funcionperfil_codfuncion en la fila representada por este objeto.
	 */
	public Object getFuncionperfil_codfuncion() throws SQLException {
		return select.getCacheValueAt(rowNumber, 2);
	}

	/**
	 * Devuelve el valor de la columna Funcionperfil_habilitado en la fila representada por este objeto.
	 */
	public Object getFuncionperfil_habilitado() throws SQLException {
		return select.getCacheValueAt(rowNumber, 3);
	}

	/**
	 * Devuelve el valor de la columna Funcionperfil_autorizado en la fila representada por este objeto.
	 */
	public Object getFuncionperfil_autorizado() throws SQLException {
		return select.getCacheValueAt(rowNumber, 4);
	}

	/**
	 * Devuelve el valor de la columna Funcionperfil_actualizacion en la fila representada por este objeto.
	 */
	public Object getFuncionperfil_actualizacion() throws SQLException {
		return select.getCacheValueAt(rowNumber, 5);
	}

	/**
	 * Devuelve el valor de la columna Funcionperfil_codmodulo en la fila representada por este objeto.
	 */
	public Object getFuncionperfil_codmodulo() throws SQLException {
		return select.getCacheValueAt(rowNumber, 6);
	}

	/**
	 * Devuelve una serie que contiene todos los valores en la fila representada por este objeto.
	 */
	public String toString() {
		String string = "";
		try {
			for (int i = 1; i <= select.getColumnCount(); i++) {
				string += select.getCacheValueAt(rowNumber, i);
				string += "  ";
			}
		} catch (SQLException ex) {
			return null;
		}
		return string;
	}
}