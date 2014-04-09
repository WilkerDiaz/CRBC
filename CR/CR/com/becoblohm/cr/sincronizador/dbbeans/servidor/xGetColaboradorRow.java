package com.becoblohm.cr.sincronizador.dbbeans.servidor;
import java.sql.SQLException;

import com.ibm.db.beans.DBSelect;

/**
 * Esta clase representa una fila específica de un 
 * conjunto de resultados contenida en DBSelect.
 * Generated:  16/10/2003 02:07:20 PM
 */

public class xGetColaboradorRow {
	private int rowNumber;
	private DBSelect select;

	/**
	 * Construye un objeto que representa una fila a partir de DBSelect.
	 */
	public xGetColaboradorRow(DBSelect aRef, int aRowNumber) {
		select = aRef;
		rowNumber = aRowNumber;
	}

	/**
	 * Devuelve el valor de la columna USUAGNR_ESRMUG en la fila representada por este objeto.
	 */
	public Object getUSUAGNR_ESRMUG() throws SQLException {
		return select.getCacheValueAt(rowNumber, 1);
	}

	/**
	 * Devuelve el valor de la columna USUAGNR_USUMUG en la fila representada por este objeto.
	 */
	public Object getUSUAGNR_USUMUG() throws SQLException {
		return select.getCacheValueAt(rowNumber, 2);
	}

	/**
	 * Devuelve el valor de la columna USUAGNR_CEDMUG en la fila representada por este objeto.
	 */
	public Object getUSUAGNR_CEDMUG() throws SQLException {
		return select.getCacheValueAt(rowNumber, 3);
	}

	/**
	 * Devuelve el valor de la columna USUAGNR_NOMMUG en la fila representada por este objeto.
	 */
	public Object getUSUAGNR_NOMMUG() throws SQLException {
		return select.getCacheValueAt(rowNumber, 4);
	}

	/**
	 * Devuelve el valor de la columna USUAGNR_APEMUG en la fila representada por este objeto.
	 */
	public Object getUSUAGNR_APEMUG() throws SQLException {
		return select.getCacheValueAt(rowNumber, 5);
	}

	/**
	 * Devuelve el valor de la columna USUAGNR_SEXMUG en la fila representada por este objeto.
	 */
	public Object getUSUAGNR_SEXMUG() throws SQLException {
		return select.getCacheValueAt(rowNumber, 6);
	}

	/**
	 * Devuelve el valor de la columna USUAGNR_ESTMUG en la fila representada por este objeto.
	 */
	public Object getUSUAGNR_ESTMUG() throws SQLException {
		return select.getCacheValueAt(rowNumber, 7);
	}

	/**
	 * Devuelve el valor de la columna USUAGNR_NACMUG en la fila representada por este objeto.
	 */
	public Object getUSUAGNR_NACMUG() throws SQLException {
		return select.getCacheValueAt(rowNumber, 8);
	}

	/**
	 * Devuelve el valor de la columna USUAGNR_FICMUG en la fila representada por este objeto.
	 */
	public Object getUSUAGNR_FICMUG() throws SQLException {
		return select.getCacheValueAt(rowNumber, 9);
	}

	/**
	 * Devuelve el valor de la columna USUAGNR_DPTMUG en la fila representada por este objeto.
	 */
	public Object getUSUAGNR_DPTMUG() throws SQLException {
		return select.getCacheValueAt(rowNumber, 10);
	}

	/**
	 * Devuelve el valor de la columna USUAGNR_TDAMUG en la fila representada por este objeto.
	 */
	public Object getUSUAGNR_TDAMUG() throws SQLException {
		return select.getCacheValueAt(rowNumber, 11);
	}

	/**
	 * Devuelve el valor de la columna USUAGNR_FCNMUG en la fila representada por este objeto.
	 */
	public Object getUSUAGNR_FCNMUG() throws SQLException {
		return select.getCacheValueAt(rowNumber, 12);
	}

	/**
	 * Devuelve el valor de la columna USUAGNR_FCIMUG en la fila representada por este objeto.
	 */
	public Object getUSUAGNR_FCIMUG() throws SQLException {
		return select.getCacheValueAt(rowNumber, 13);
	}

	/**
	 * Devuelve el valor de la columna USUAGNR_FCEMUG en la fila representada por este objeto.
	 */
	public Object getUSUAGNR_FCEMUG() throws SQLException {
		return select.getCacheValueAt(rowNumber, 14);
	}

	/**
	 * Devuelve el valor de la columna MAEPDBC_FICHMDBC en la fila representada por este objeto.
	 */
	public Object getMAEPDBC_FICHMDBC() throws SQLException {
		return select.getCacheValueAt(rowNumber, 15);
	}

	/**
	 * Devuelve el valor de la columna MAEPDBC_CEDUMDBC en la fila representada por este objeto.
	 */
	public Object getMAEPDBC_CEDUMDBC() throws SQLException {
		return select.getCacheValueAt(rowNumber, 16);
	}

	/**
	 * Devuelve el valor de la columna MAEPDBC_FECNMDBC en la fila representada por este objeto.
	 */
	public Object getMAEPDBC_FECNMDBC() throws SQLException {
		return select.getCacheValueAt(rowNumber, 17);
	}

	/**
	 * Devuelve el valor de la columna MAEPDBC_CARGMDBC en la fila representada por este objeto.
	 */
	public Object getMAEPDBC_CARGMDBC() throws SQLException {
		return select.getCacheValueAt(rowNumber, 18);
	}

	/**
	 * Devuelve el valor de la columna MAEPDBC_TIENMDBC en la fila representada por este objeto.
	 */
	public Object getMAEPDBC_TIENMDBC() throws SQLException {
		return select.getCacheValueAt(rowNumber, 19);
	}

	/**
	 * Devuelve el valor de la columna MAEPDBC_DEPAMDBC en la fila representada por este objeto.
	 */
	public Object getMAEPDBC_DEPAMDBC() throws SQLException {
		return select.getCacheValueAt(rowNumber, 20);
	}

	/**
	 * Devuelve el valor de la columna MAEPDBC_NOMBMDBC en la fila representada por este objeto.
	 */
	public Object getMAEPDBC_NOMBMDBC() throws SQLException {
		return select.getCacheValueAt(rowNumber, 21);
	}

	/**
	 * Devuelve el valor de la columna MAEPDBC_APELMDBC en la fila representada por este objeto.
	 */
	public Object getMAEPDBC_APELMDBC() throws SQLException {
		return select.getCacheValueAt(rowNumber, 22);
	}

	/**
	 * Devuelve el valor de la columna MAEPDBC_NUMCMDBC en la fila representada por este objeto.
	 */
	public Object getMAEPDBC_NUMCMDBC() throws SQLException {
		return select.getCacheValueAt(rowNumber, 23);
	}

	/**
	 * Devuelve el valor de la columna MAEPDBC_TIPCMDBC en la fila representada por este objeto.
	 */
	public Object getMAEPDBC_TIPCMDBC() throws SQLException {
		return select.getCacheValueAt(rowNumber, 24);
	}

	/**
	 * Devuelve el valor de la columna MAEPDBC_BANCMDBC en la fila representada por este objeto.
	 */
	public Object getMAEPDBC_BANCMDBC() throws SQLException {
		return select.getCacheValueAt(rowNumber, 25);
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