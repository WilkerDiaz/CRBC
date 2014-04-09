/**
 * =============================================================================
 * Proyecto   : CRBECOEPA
 * Paquete    : com.becoblohm.cr.manejarusuario.dbbeans
 * Programa   : GetMetodoRow.java
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

public class GetMetodoRow {
	private int rowNumber;
	private DBSelect select;

	/**
	 * Construye un objeto que representa una fila a partir de DBSelect.
	 */
	public GetMetodoRow(DBSelect aRef, int aRowNumber) {
		select = aRef;
		rowNumber = aRowNumber;
	}

	/**
	 * Devuelve el valor de la columna Metodos_codmetodo en la fila representada por este objeto.
	 */
	public Object getMetodos_codmetodo() throws SQLException {
		return select.getCacheValueAt(rowNumber, 1);
	}

	/**
	 * Devuelve el valor de la columna Metodos_descripcion en la fila representada por este objeto.
	 */
	public Object getMetodos_descripcion() throws SQLException {
		return select.getCacheValueAt(rowNumber, 2);
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