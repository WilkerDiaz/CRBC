package com.becoblohm.cr.sincronizador.dbbeans.servidor;
import java.sql.SQLException;

import com.ibm.db.beans.DBSelect;

/**
 * Esta clase representa una fila específica de un 
 * conjunto de resultados contenida en DBSelect.
 * Generated:  10/03/2004 03:24:59 PM
 */

public class GetProductoSrvRow {
	private int rowNumber;
	private DBSelect select;

	/**
	 * Construye un objeto que representa una fila a partir de DBSelect.
	 */
	public GetProductoSrvRow(DBSelect aRef, int aRowNumber) {
		select = aRef;
		rowNumber = aRowNumber;
	}

	/**
	 * Devuelve el valor de la columna ARTITDA_ESREAT en la fila representada por este objeto.
	 */
	public Object getARTITDA_ESREAT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 1);
	}

	/**
	 * Devuelve el valor de la columna ARTITDA_TIENAT en la fila representada por este objeto.
	 */
	public Object getARTITDA_TIENAT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 2);
	}

	/**
	 * Devuelve el valor de la columna ARTITDA_CODAAT en la fila representada por este objeto.
	 */
	public Object getARTITDA_CODAAT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 3);
	}

	/**
	 * Devuelve el valor de la columna ARTITDA_PREVAT en la fila representada por este objeto.
	 */
	public Object getARTITDA_PREVAT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 4);
	}

	/**
	 * Devuelve el valor de la columna ARTITDA_COSPAT en la fila representada por este objeto.
	 */
	public Object getARTITDA_COSPAT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 5);
	}

	/**
	 * Devuelve el valor de la columna ARTITDA_DOCUAT en la fila representada por este objeto.
	 */
	public Object getARTITDA_DOCUAT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 6);
	}

	/**
	 * Devuelve el valor de la columna ARTITDA_FECUAT en la fila representada por este objeto.
	 */
	public Object getARTITDA_FECUAT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 7);
	}

	/**
	 * Devuelve el valor de la columna ARTITDA_CAUCAT en la fila representada por este objeto.
	 */
	public Object getARTITDA_CAUCAT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 8);
	}

	/**
	 * Devuelve el valor de la columna ARTITDA_COBUAT en la fila representada por este objeto.
	 */
	public Object getARTITDA_COBUAT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 9);
	}

	/**
	 * Devuelve el valor de la columna ARTITDA_CONUAT en la fila representada por este objeto.
	 */
	public Object getARTITDA_CONUAT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 10);
	}

	/**
	 * Devuelve el valor de la columna ARTITDA_EXICAT en la fila representada por este objeto.
	 */
	public Object getARTITDA_EXICAT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 11);
	}

	/**
	 * Devuelve el valor de la columna ARTITDA_EXACAT en la fila representada por este objeto.
	 */
	public Object getARTITDA_EXACAT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 12);
	}

	/**
	 * Devuelve el valor de la columna ARTITDA_EXLCAT en la fila representada por este objeto.
	 */
	public Object getARTITDA_EXLCAT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 13);
	}

	/**
	 * Devuelve el valor de la columna ARTITDA_STMIAT en la fila representada por este objeto.
	 */
	public Object getARTITDA_STMIAT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 14);
	}

	/**
	 * Devuelve el valor de la columna ARTITDA_IMPUAT en la fila representada por este objeto.
	 */
	public Object getARTITDA_IMPUAT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 15);
	}

	/**
	 * Devuelve el valor de la columna ARTITDA_ROTAAT en la fila representada por este objeto.
	 */
	public Object getARTITDA_ROTAAT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 16);
	}

	/**
	 * Devuelve el valor de la columna ARTITDA_MARGAT en la fila representada por este objeto.
	 */
	public Object getARTITDA_MARGAT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 17);
	}

	/**
	 * Devuelve el valor de la columna ARTITDA_PASIAT en la fila representada por este objeto.
	 */
	public Object getARTITDA_PASIAT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 18);
	}

	/**
	 * Devuelve el valor de la columna ARTITDA_RACKAT en la fila representada por este objeto.
	 */
	public Object getARTITDA_RACKAT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 19);
	}

	/**
	 * Devuelve el valor de la columna ARTITDA_PDATAT en la fila representada por este objeto.
	 */
	public Object getARTITDA_PDATAT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 20);
	}

	/**
	 * Devuelve el valor de la columna ARTITDA_FPDPAT en la fila representada por este objeto.
	 */
	public Object getARTITDA_FPDPAT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 21);
	}

	/**
	 * Devuelve el valor de la columna ARTITDA_EXADAT en la fila representada por este objeto.
	 */
	public Object getARTITDA_EXADAT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 22);
	}

	/**
	 * Devuelve el valor de la columna ARTITDA_EXARAT en la fila representada por este objeto.
	 */
	public Object getARTITDA_EXARAT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 23);
	}

	/**
	 * Devuelve el valor de la columna ARTITDA_EXPEAT en la fila representada por este objeto.
	 */
	public Object getARTITDA_EXPEAT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 24);
	}

	/**
	 * Devuelve el valor de la columna ARTITDA_FIL1AT en la fila representada por este objeto.
	 */
	public Object getARTITDA_FIL1AT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 25);
	}

	/**
	 * Devuelve el valor de la columna ARTITDA_FIL2AT en la fila representada por este objeto.
	 */
	public Object getARTITDA_FIL2AT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 26);
	}

	/**
	 * Devuelve el valor de la columna ARTITDA_FIL3AT en la fila representada por este objeto.
	 */
	public Object getARTITDA_FIL3AT() throws SQLException {
		return select.getCacheValueAt(rowNumber, 27);
	}

	/**
	 * Devuelve el valor de la columna ARTICULO_ESREMA en la fila representada por este objeto.
	 */
	public Object getARTICULO_ESREMA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 28);
	}

	/**
	 * Devuelve el valor de la columna ARTICULO_CODAMA en la fila representada por este objeto.
	 */
	public Object getARTICULO_CODAMA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 29);
	}

	/**
	 * Devuelve el valor de la columna ARTICULO_DESCMA en la fila representada por este objeto.
	 */
	public Object getARTICULO_DESCMA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 30);
	}

	/**
	 * Devuelve el valor de la columna ARTICULO_DES1MA en la fila representada por este objeto.
	 */
	public Object getARTICULO_DES1MA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 31);
	}

	/**
	 * Devuelve el valor de la columna ARTICULO_DES2MA en la fila representada por este objeto.
	 */
	public Object getARTICULO_DES2MA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 32);
	}

	/**
	 * Devuelve el valor de la columna ARTICULO_UNVEMA en la fila representada por este objeto.
	 */
	public Object getARTICULO_UNVEMA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 33);
	}

	/**
	 * Devuelve el valor de la columna ARTICULO_EMPVMA en la fila representada por este objeto.
	 */
	public Object getARTICULO_EMPVMA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 34);
	}

	/**
	 * Devuelve el valor de la columna ARTICULO_TIPAMA en la fila representada por este objeto.
	 */
	public Object getARTICULO_TIPAMA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 35);
	}

	/**
	 * Devuelve el valor de la columna ARTICULO_CALAMA en la fila representada por este objeto.
	 */
	public Object getARTICULO_CALAMA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 36);
	}

	/**
	 * Devuelve el valor de la columna ARTICULO_NACIMA en la fila representada por este objeto.
	 */
	public Object getARTICULO_NACIMA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 37);
	}

	/**
	 * Devuelve el valor de la columna ARTICULO_COVEMA en la fila representada por este objeto.
	 */
	public Object getARTICULO_COVEMA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 38);
	}

	/**
	 * Devuelve el valor de la columna ARTICULO_POVEMA en la fila representada por este objeto.
	 */
	public Object getARTICULO_POVEMA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 39);
	}

	/**
	 * Devuelve el valor de la columna ARTICULO_DEEMMA en la fila representada por este objeto.
	 */
	public Object getARTICULO_DEEMMA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 40);
	}

	/**
	 * Devuelve el valor de la columna ARTICULO_ETIQMA en la fila representada por este objeto.
	 */
	public Object getARTICULO_ETIQMA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 41);
	}

	/**
	 * Devuelve el valor de la columna ARTICULO_FECAMA en la fila representada por este objeto.
	 */
	public Object getARTICULO_FECAMA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 42);
	}

	/**
	 * Devuelve el valor de la columna ARTICULO_FECIMA en la fila representada por este objeto.
	 */
	public Object getARTICULO_FECIMA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 43);
	}

	/**
	 * Devuelve el valor de la columna ARTICULO_OPERMA en la fila representada por este objeto.
	 */
	public Object getARTICULO_OPERMA() throws SQLException {
		return select.getCacheValueAt(rowNumber, 44);
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