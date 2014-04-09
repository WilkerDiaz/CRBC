package com.becoblohm.cr.sincronizador.dbbeans.servidor;
import java.sql.SQLException;

import com.ibm.db.beans.DBSelect;

/**
 * Esta clase representa una fila específica de un 
 * conjunto de resultados contenida en DBSelect.
 * Generated:  17/10/2003 10:36:47 AM
 */

public class GetTiendaSrvRow {
	private int rowNumber;
	private DBSelect select;

	/**
	 * Construye un objeto que representa una fila a partir de DBSelect.
	 */
	public GetTiendaSrvRow(DBSelect aRef, int aRowNumber) {
		select = aRef;
		rowNumber = aRowNumber;
	}

	/**
	 * Devuelve el valor de la columna MAECTDA_STATMTDA en la fila representada por este objeto.
	 */
	public Object getMAECTDA_STATMTDA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 1);
	}

	/**
	 * Devuelve el valor de la columna MAECTDA_NTDAMTDA en la fila representada por este objeto.
	 */
	public Object getMAECTDA_NTDAMTDA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 2);
	}

	/**
	 * Devuelve el valor de la columna MAECTDA_DESCMTDA en la fila representada por este objeto.
	 */
	public Object getMAECTDA_DESCMTDA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 3);
	}

	/**
	 * Devuelve el valor de la columna MAECTDA_NRIFMTDA en la fila representada por este objeto.
	 */
	public Object getMAECTDA_NRIFMTDA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 4);
	}

	/**
	 * Devuelve el valor de la columna MAECTDA_NNITMTDA en la fila representada por este objeto.
	 */
	public Object getMAECTDA_NNITMTDA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 5);
	}

	/**
	 * Devuelve el valor de la columna MAECTDA_DIR1MTDA en la fila representada por este objeto.
	 */
	public Object getMAECTDA_DIR1MTDA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 6);
	}

	/**
	 * Devuelve el valor de la columna MAECTDA_DIR2MTDA en la fila representada por este objeto.
	 */
	public Object getMAECTDA_DIR2MTDA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 7);
	}

	/**
	 * Devuelve el valor de la columna MAECTDA_DIR3MTDA en la fila representada por este objeto.
	 */
	public Object getMAECTDA_DIR3MTDA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 8);
	}

	/**
	 * Devuelve el valor de la columna MAECTDA_DIR4MTDA en la fila representada por este objeto.
	 */
	public Object getMAECTDA_DIR4MTDA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 9);
	}

	/**
	 * Devuelve el valor de la columna MAECTDA_DOMIMTDA en la fila representada por este objeto.
	 */
	public Object getMAECTDA_DOMIMTDA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 10);
	}

	/**
	 * Devuelve el valor de la columna MAECTDA_APOSMTDA en la fila representada por este objeto.
	 */
	public Object getMAECTDA_APOSMTDA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 11);
	}

	/**
	 * Devuelve el valor de la columna MAECTDA_CODAMTDA en la fila representada por este objeto.
	 */
	public Object getMAECTDA_CODAMTDA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 12);
	}

	/**
	 * Devuelve el valor de la columna MAECTDA_NFAXMTDA en la fila representada por este objeto.
	 */
	public Object getMAECTDA_NFAXMTDA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 13);
	}

	/**
	 * Devuelve el valor de la columna MAECTDA_TEL1MTDA en la fila representada por este objeto.
	 */
	public Object getMAECTDA_TEL1MTDA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 14);
	}

	/**
	 * Devuelve el valor de la columna MAECTDA_TEL2MTDA en la fila representada por este objeto.
	 */
	public Object getMAECTDA_TEL2MTDA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 15);
	}

	/**
	 * Devuelve el valor de la columna MAECTDA_TEL3MTDA en la fila representada por este objeto.
	 */
	public Object getMAECTDA_TEL3MTDA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 16);
	}

	/**
	 * Devuelve el valor de la columna MAECTDA_IMPUMTDA en la fila representada por este objeto.
	 */
	public Object getMAECTDA_IMPUMTDA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 17);
	}

	/**
	 * Devuelve el valor de la columna MAECTDA_FACPMTDA en la fila representada por este objeto.
	 */
	public Object getMAECTDA_FACPMTDA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 18);
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