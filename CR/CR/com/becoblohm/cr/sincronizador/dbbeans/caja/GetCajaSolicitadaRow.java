package com.becoblohm.cr.sincronizador.dbbeans.caja;
import java.sql.SQLException;

import com.ibm.db.beans.DBSelect;

/**
 * Esta clase representa una fila específica de un 
 * conjunto de resultados contenida en DBSelect.
 * Generated:  22/10/2003 01:16:45 PM
 */

public class GetCajaSolicitadaRow {
	private int rowNumber;
	private DBSelect select;

	/**
	 * Construye un objeto que representa una fila a partir de DBSelect.
	 */
	public GetCajaSolicitadaRow(DBSelect aRef, int aRowNumber) {
		select = aRef;
		rowNumber = aRowNumber;
	}

	/**
	 * Devuelve el valor de la columna Caja_idestadocaja en la fila representada por este objeto.
	 */
	public Object getCaja_idestadocaja() throws SQLException {
		return select.getCacheValueAt(rowNumber, 1);
	}

	/**
	 * Devuelve el valor de la columna Caja_codusuario en la fila representada por este objeto.
	 */
	public Object getCaja_codusuario() throws SQLException {
		return select.getCacheValueAt(rowNumber, 2);
	}

	/**
	 * Devuelve el valor de la columna Caja_numtransaccion en la fila representada por este objeto.
	 */
	public Object getCaja_numtransaccion() throws SQLException {
		return select.getCacheValueAt(rowNumber, 3);
	}

	/**
	 * Devuelve el valor de la columna Caja_numnofiscal en la fila representada por este objeto.
	 */
	public Object getCaja_numnofiscal() throws SQLException {
		return select.getCacheValueAt(rowNumber, 4);
	}

	/**
	 * Devuelve el valor de la columna Caja_numregistro en la fila representada por este objeto.
	 */
	public Object getCaja_numregistro() throws SQLException {
		return select.getCacheValueAt(rowNumber, 5);
	}

	/**
	 * Devuelve el valor de la columna Caja_numseqmerchant en la fila representada por este objeto.
	 */
	public Object getCaja_numseqmerchant() throws SQLException {
		return select.getCacheValueAt(rowNumber, 6);
	}

	/**
	 * Devuelve el valor de la columna Caja_nivelauditoria en la fila representada por este objeto.
	 */
	public Object getCaja_nivelauditoria() throws SQLException {
		return select.getCacheValueAt(rowNumber, 7);
	}

	/**
	 * Devuelve el valor de la columna Caja_fechareportez en la fila representada por este objeto.
	 */
	public Object getCaja_fechareportez() throws SQLException {
		return select.getCacheValueAt(rowNumber, 8);
	}

	/**
	 * Devuelve el valor de la columna Caja_versionsistema en la fila representada por este objeto.
	 */
	public Object getCaja_versionsistema() throws SQLException {
		return select.getCacheValueAt(rowNumber, 9);
	}

	/**
	 * Devuelve el valor de la columna Caja_modelo en la fila representada por este objeto.
	 */
	public Object getCaja_modelo() throws SQLException {
		return select.getCacheValueAt(rowNumber, 10);
	}

	/**
	 * Devuelve el valor de la columna Caja_montorecaudado en la fila representada por este objeto.
	 */
	public Object getCaja_montorecaudado() throws SQLException {
		return select.getCacheValueAt(rowNumber, 11);
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