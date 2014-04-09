package com.becoblohm.cr.sincronizador.dbbeans.servidor;
import java.sql.SQLException;

import com.ibm.db.beans.DBSelect;

/**
 * Esta clase representa una fila específica de un 
 * conjunto de resultados contenida en DBSelect.
 * Generated:  03/10/2003 02:27:32 PM
 */

public class GetMaetdaRow {
	private int rowNumber;
	private DBSelect select;

	/**
	 * Construye un objeto que representa una fila a partir de DBSelect.
	 */
	public GetMaetdaRow(DBSelect aRef, int aRowNumber) {
		select = aRef;
		rowNumber = aRowNumber;
	}

	/**
	 * Devuelve el valor de la columna MAETDA_RIFTIE en la fila representada por este objeto.
	 */
	public Object getMAETDA_RIFTIE() throws SQLException {
		return select.getCacheValueAt(rowNumber, 1);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_NITTIE en la fila representada por este objeto.
	 */
	public Object getMAETDA_NITTIE() throws SQLException {
		return select.getCacheValueAt(rowNumber, 2);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_NUMTIE en la fila representada por este objeto.
	 */
	public Object getMAETDA_NUMTIE() throws SQLException {
		return select.getCacheValueAt(rowNumber, 3);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_RAZSOC en la fila representada por este objeto.
	 */
	public Object getMAETDA_RAZSOC() throws SQLException {
		return select.getCacheValueAt(rowNumber, 4);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_DIREC1 en la fila representada por este objeto.
	 */
	public Object getMAETDA_DIREC1() throws SQLException {
		return select.getCacheValueAt(rowNumber, 5);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_DIREC2 en la fila representada por este objeto.
	 */
	public Object getMAETDA_DIREC2() throws SQLException {
		return select.getCacheValueAt(rowNumber, 6);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_DOMFIS en la fila representada por este objeto.
	 */
	public Object getMAETDA_DOMFIS() throws SQLException {
		return select.getCacheValueAt(rowNumber, 7);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_CODAR1 en la fila representada por este objeto.
	 */
	public Object getMAETDA_CODAR1() throws SQLException {
		return select.getCacheValueAt(rowNumber, 8);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_TLFNO1 en la fila representada por este objeto.
	 */
	public Object getMAETDA_TLFNO1() throws SQLException {
		return select.getCacheValueAt(rowNumber, 9);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_TLFNO2 en la fila representada por este objeto.
	 */
	public Object getMAETDA_TLFNO2() throws SQLException {
		return select.getCacheValueAt(rowNumber, 10);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_TLFNO3 en la fila representada por este objeto.
	 */
	public Object getMAETDA_TLFNO3() throws SQLException {
		return select.getCacheValueAt(rowNumber, 11);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_CODFAX en la fila representada por este objeto.
	 */
	public Object getMAETDA_CODFAX() throws SQLException {
		return select.getCacheValueAt(rowNumber, 12);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_NUMFAX en la fila representada por este objeto.
	 */
	public Object getMAETDA_NUMFAX() throws SQLException {
		return select.getCacheValueAt(rowNumber, 13);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_DIRSU1 en la fila representada por este objeto.
	 */
	public Object getMAETDA_DIRSU1() throws SQLException {
		return select.getCacheValueAt(rowNumber, 14);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_DIRSU2 en la fila representada por este objeto.
	 */
	public Object getMAETDA_DIRSU2() throws SQLException {
		return select.getCacheValueAt(rowNumber, 15);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_DOMSUC en la fila representada por este objeto.
	 */
	public Object getMAETDA_DOMSUC() throws SQLException {
		return select.getCacheValueAt(rowNumber, 16);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_CODASU en la fila representada por este objeto.
	 */
	public Object getMAETDA_CODASU() throws SQLException {
		return select.getCacheValueAt(rowNumber, 17);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_TLFSUC en la fila representada por este objeto.
	 */
	public Object getMAETDA_TLFSUC() throws SQLException {
		return select.getCacheValueAt(rowNumber, 18);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_CODFSU en la fila representada por este objeto.
	 */
	public Object getMAETDA_CODFSU() throws SQLException {
		return select.getCacheValueAt(rowNumber, 19);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_FAXSUC en la fila representada por este objeto.
	 */
	public Object getMAETDA_FAXSUC() throws SQLException {
		return select.getCacheValueAt(rowNumber, 20);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_PUBLI1 en la fila representada por este objeto.
	 */
	public Object getMAETDA_PUBLI1() throws SQLException {
		return select.getCacheValueAt(rowNumber, 21);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_PUBLI2 en la fila representada por este objeto.
	 */
	public Object getMAETDA_PUBLI2() throws SQLException {
		return select.getCacheValueAt(rowNumber, 22);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_PUBLI3 en la fila representada por este objeto.
	 */
	public Object getMAETDA_PUBLI3() throws SQLException {
		return select.getCacheValueAt(rowNumber, 23);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_PUBLI4 en la fila representada por este objeto.
	 */
	public Object getMAETDA_PUBLI4() throws SQLException {
		return select.getCacheValueAt(rowNumber, 24);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_PUBLI5 en la fila representada por este objeto.
	 */
	public Object getMAETDA_PUBLI5() throws SQLException {
		return select.getCacheValueAt(rowNumber, 25);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_FONDO en la fila representada por este objeto.
	 */
	public Object getMAETDA_FONDO() throws SQLException {
		return select.getCacheValueAt(rowNumber, 26);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_STAIMP en la fila representada por este objeto.
	 */
	public Object getMAETDA_STAIMP() throws SQLException {
		return select.getCacheValueAt(rowNumber, 27);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_PORIMP en la fila representada por este objeto.
	 */
	public Object getMAETDA_PORIMP() throws SQLException {
		return select.getCacheValueAt(rowNumber, 28);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_STADES en la fila representada por este objeto.
	 */
	public Object getMAETDA_STADES() throws SQLException {
		return select.getCacheValueAt(rowNumber, 29);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_PORDES en la fila representada por este objeto.
	 */
	public Object getMAETDA_PORDES() throws SQLException {
		return select.getCacheValueAt(rowNumber, 30);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_STACPC en la fila representada por este objeto.
	 */
	public Object getMAETDA_STACPC() throws SQLException {
		return select.getCacheValueAt(rowNumber, 31);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_HORSIS en la fila representada por este objeto.
	 */
	public Object getMAETDA_HORSIS() throws SQLException {
		return select.getCacheValueAt(rowNumber, 32);
	}

	/**
	 * Devuelve el valor de la columna MAETDA_FECSIS en la fila representada por este objeto.
	 */
	public Object getMAETDA_FECSIS() throws SQLException {
		return select.getCacheValueAt(rowNumber, 33);
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