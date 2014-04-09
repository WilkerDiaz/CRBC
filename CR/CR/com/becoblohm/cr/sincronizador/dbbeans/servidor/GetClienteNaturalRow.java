package com.becoblohm.cr.sincronizador.dbbeans.servidor;

import java.sql.SQLException;

import com.ibm.db.beans.DBSelect;

/**
 * Esta clase representa una fila específica de un 
 * conjunto de resultados contenida en DBSelect.
 * Generated:  15/04/2004 03:49:52 PM
 */

public class GetClienteNaturalRow {
	private int rowNumber;
	private DBSelect select;

	/**
	 * Construye un objeto que representa una fila a partir de DBSelect.
	 */
	public GetClienteNaturalRow(DBSelect aRef, int aRowNumber) {
		select = aRef;
		rowNumber = aRowNumber;
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_NOMAFI1 en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_NOMAFI1() throws SQLException {
		return select.getCacheValueAt(rowNumber, 1);
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_NOMAFI2 en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_NOMAFI2() throws SQLException {
		return select.getCacheValueAt(rowNumber, 2);
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_APEAFI1 en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_APEAFI1() throws SQLException {
		return select.getCacheValueAt(rowNumber, 3);
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_APEAFI2 en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_APEAFI2() throws SQLException {
		return select.getCacheValueAt(rowNumber, 4);
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_URBANI en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_URBANI() throws SQLException {
		return select.getCacheValueAt(rowNumber, 5);
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_CALLAV en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_CALLAV() throws SQLException {
		return select.getCacheValueAt(rowNumber, 6);
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_CASEDI en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_CASEDI() throws SQLException {
		return select.getCacheValueAt(rowNumber, 7);
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_PISAPT en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_PISAPT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 8);
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_CODARE en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_CODARE() throws SQLException {
		return select.getCacheValueAt(rowNumber, 9);
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_HABOFC en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_HABOFC() throws SQLException {
		return select.getCacheValueAt(rowNumber, 10);
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_FAXCEL en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_FAXCEL() throws SQLException {
		return select.getCacheValueAt(rowNumber, 11);
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_TIPAFI en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_TIPAFI() throws SQLException {
		return select.getCacheValueAt(rowNumber, 12);
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_NUMTIE en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_NUMTIE() throws SQLException {
		return select.getCacheValueAt(rowNumber, 13);
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_STATUS en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_STATUS() throws SQLException {
		return select.getCacheValueAt(rowNumber, 14);
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_FECAFI en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_FECAFI() throws SQLException {
		return select.getCacheValueAt(rowNumber, 15);
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_HORAFI en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_HORAFI() throws SQLException {
		return select.getCacheValueAt(rowNumber, 16);
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_ULTACT en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_ULTACT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 17);
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_HORACT en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_HORACT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 18);
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_NUMAFIAG en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_NUMAFIAG() throws SQLException {
		return select.getCacheValueAt(rowNumber, 19);
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