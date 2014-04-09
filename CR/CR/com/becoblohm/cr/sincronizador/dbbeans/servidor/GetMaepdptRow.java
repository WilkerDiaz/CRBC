package com.becoblohm.cr.sincronizador.dbbeans.servidor;
import java.sql.SQLException;

import com.ibm.db.beans.DBSelect;

/**
 * Esta clase representa una fila específica de un 
 * conjunto de resultados contenida en DBSelect.
 * Generated:  17/10/2003 03:15:00 PM
 */

public class GetMaepdptRow {
	private int rowNumber;
	private DBSelect select;

	/**
	 * Construye un objeto que representa una fila a partir de DBSelect.
	 */
	public GetMaepdptRow(DBSelect aRef, int aRowNumber) {
		select = aRef;
		rowNumber = aRowNumber;
	}

	/**
	 * Devuelve el valor de la columna MAEPDPT_CODDMDPT en la fila representada por este objeto.
	 */
	public Object getMAEPDPT_CODDMDPT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 1);
	}

	/**
	 * Devuelve el valor de la columna MAEPDPT_DESDMDPT en la fila representada por este objeto.
	 */
	public Object getMAEPDPT_DESDMDPT() throws SQLException {
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