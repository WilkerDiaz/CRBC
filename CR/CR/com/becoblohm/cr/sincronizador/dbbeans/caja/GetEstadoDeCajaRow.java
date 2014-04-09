package com.becoblohm.cr.sincronizador.dbbeans.caja;
import java.sql.SQLException;

import com.ibm.db.beans.DBSelect;

/**
 * Esta clase representa una fila específica de un 
 * conjunto de resultados contenida en DBSelect.
 * Generated:  17/10/2003 11:42:07 AM
 */

public class GetEstadoDeCajaRow {
	private int rowNumber;
	private DBSelect select;

	/**
	 * Construye un objeto que representa una fila a partir de DBSelect.
	 */
	public GetEstadoDeCajaRow(DBSelect aRef, int aRowNumber) {
		select = aRef;
		rowNumber = aRowNumber;
	}

	/**
	 * Devuelve el valor de la columna Estadodecaja_idestado en la fila representada por este objeto.
	 */
	public Object getEstadodecaja_idestado() throws SQLException {
		return select.getCacheValueAt(rowNumber, 1);
	}

	/**
	 * Devuelve el valor de la columna Estadodecaja_descripcion en la fila representada por este objeto.
	 */
	public Object getEstadodecaja_descripcion() throws SQLException {
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