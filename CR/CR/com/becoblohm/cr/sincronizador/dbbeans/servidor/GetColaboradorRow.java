package com.becoblohm.cr.sincronizador.dbbeans.servidor;
import java.sql.SQLException;

import com.ibm.db.beans.DBSelect;

/**
 * Esta clase representa una fila específica de un 
 * conjunto de resultados contenida en DBSelect.
 * Generated:  04/10/2003 05:39:26 PM
 */

public class GetColaboradorRow {
	private int rowNumber;
	private DBSelect select;

	/**
	 * Construye un objeto que representa una fila a partir de DBSelect.
	 */
	public GetColaboradorRow(DBSelect aRef, int aRowNumber) {
		select = aRef;
		rowNumber = aRowNumber;
	}

	/**
	 * Devuelve el valor de la columna MAEPDBC_FICHMDBC en la fila representada por este objeto.
	 */
	public Object getMAEPDBC_FICHMDBC() throws SQLException {
		return select.getCacheValueAt(rowNumber, 1);
	}

	/**
	 * Devuelve el valor de la columna MAEPDBC_CEDUMDBC en la fila representada por este objeto.
	 */
	public Object getMAEPDBC_CEDUMDBC() throws SQLException {
		return select.getCacheValueAt(rowNumber, 2);
	}

	/**
	 * Devuelve el valor de la columna MAEPDBC_FECNMDBC en la fila representada por este objeto.
	 */
	public Object getMAEPDBC_FECNMDBC() throws SQLException {
		return select.getCacheValueAt(rowNumber, 3);
	}

	/**
	 * Devuelve el valor de la columna MAEPDBC_CARGMDBC en la fila representada por este objeto.
	 */
	public Object getMAEPDBC_CARGMDBC() throws SQLException {
		return select.getCacheValueAt(rowNumber, 4);
	}

	/**
	 * Devuelve el valor de la columna MAEPDBC_TIENMDBC en la fila representada por este objeto.
	 */
	public Object getMAEPDBC_TIENMDBC() throws SQLException {
		return select.getCacheValueAt(rowNumber, 5);
	}

	/**
	 * Devuelve el valor de la columna MAEPDBC_DEPAMDBC en la fila representada por este objeto.
	 */
	public Object getMAEPDBC_DEPAMDBC() throws SQLException {
		return select.getCacheValueAt(rowNumber, 6);
	}

	/**
	 * Devuelve el valor de la columna MAEPDBC_NOMBMDBC en la fila representada por este objeto.
	 */
	public Object getMAEPDBC_NOMBMDBC() throws SQLException {
		return select.getCacheValueAt(rowNumber, 7);
	}

	/**
	 * Devuelve el valor de la columna MAEPDBC_APELMDBC en la fila representada por este objeto.
	 */
	public Object getMAEPDBC_APELMDBC() throws SQLException {
		return select.getCacheValueAt(rowNumber, 8);
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