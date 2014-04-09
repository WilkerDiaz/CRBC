package com.becoblohm.cr.sincronizador.dbbeans.caja;
import java.sql.SQLException;

import com.ibm.db.beans.DBSelect;

/**
 * Esta clase representa una fila específica de un 
 * conjunto de resultados contenida en DBSelect.
 * Generated:  22/10/2003 12:11:21 PM
 */

public class GetTiendaSolicitadaRow {
	private int rowNumber;
	private DBSelect select;

	/**
	 * Construye un objeto que representa una fila a partir de DBSelect.
	 */
	public GetTiendaSolicitadaRow(DBSelect aRef, int aRowNumber) {
		select = aRef;
		rowNumber = aRowNumber;
	}

	/**
	 * Devuelve el valor de la columna Tienda_nombre en la fila representada por este objeto.
	 */
	public Object getTienda_nombresucursal() throws SQLException {
		return select.getCacheValueAt(rowNumber, 1);
	}

	/**
	 * Devuelve el valor de la columna Tienda_rif en la fila representada por este objeto.
	 */
	public Object getTienda_rif() throws SQLException {
		return select.getCacheValueAt(rowNumber, 2);
	}

	/**
	 * Devuelve el valor de la columna Tienda_nit en la fila representada por este objeto.
	 */
	public Object getTienda_nit() throws SQLException {
		return select.getCacheValueAt(rowNumber, 3);
	}

	/**
	 * Devuelve el valor de la columna Tienda_direccion en la fila representada por este objeto.
	 */
	public Object getTienda_direccion() throws SQLException {
		return select.getCacheValueAt(rowNumber, 4);
	}

	/**
	 * Devuelve el valor de la columna Tienda_codarea en la fila representada por este objeto.
	 */
	public Object getTienda_codarea() throws SQLException {
		return select.getCacheValueAt(rowNumber, 5);
	}

	/**
	 * Devuelve el valor de la columna Tienda_numtelefono en la fila representada por este objeto.
	 */
	public Object getTienda_numtelefono() throws SQLException {
		return select.getCacheValueAt(rowNumber, 6);
	}

	/**
	 * Devuelve el valor de la columna Tienda_codareafax en la fila representada por este objeto.
	 */
	public Object getTienda_codareafax() throws SQLException {
		return select.getCacheValueAt(rowNumber, 7);
	}

	/**
	 * Devuelve el valor de la columna Tienda_numfax en la fila representada por este objeto.
	 */
	public Object getTienda_numfax() throws SQLException {
		return select.getCacheValueAt(rowNumber, 8);
	}

	/**
	 * Devuelve el valor de la columna Tienda_direccionfiscal en la fila representada por este objeto.
	 */
	public Object getTienda_direccionfiscal() throws SQLException {
		return select.getCacheValueAt(rowNumber, 9);
	}

	/**
	 * Devuelve el valor de la columna Tienda_codareafiscal en la fila representada por este objeto.
	 */
	public Object getTienda_codareafiscal() throws SQLException {
		return select.getCacheValueAt(rowNumber, 10);
	}

	/**
	 * Devuelve el valor de la columna Tienda_numtelefonofiscal en la fila representada por este objeto.
	 */
	public Object getTienda_numtelefonofiscal() throws SQLException {
		return select.getCacheValueAt(rowNumber, 11);
	}

	/**
	 * Devuelve el valor de la columna Tienda_codareafaxfiscal en la fila representada por este objeto.
	 */
	public Object getTienda_codareafaxfiscal() throws SQLException {
		return select.getCacheValueAt(rowNumber, 12);
	}

	/**
	 * Devuelve el valor de la columna Tienda_numfaxfiscal en la fila representada por este objeto.
	 */
	public Object getTienda_numfaxfiscal() throws SQLException {
		return select.getCacheValueAt(rowNumber, 13);
	}

	/**
	 * Devuelve el valor de la columna Tienda_monedabase en la fila representada por este objeto.
	 */
	public Object getTienda_monedabase() throws SQLException {
		return select.getCacheValueAt(rowNumber, 14);
	}

	/**
	 * Devuelve el valor de la columna Tienda_codregion en la fila representada por este objeto.
	 */
	public Object getTienda_codregion() throws SQLException {
		return select.getCacheValueAt(rowNumber, 15);
	}

	/**
	 * Devuelve el valor de la columna Tienda_limiteentregacaja en la fila representada por este objeto.
	 */
	public Object getTienda_limiteentregacaja() throws SQLException {
		return select.getCacheValueAt(rowNumber, 16);
	}

	/**
	 * Devuelve el valor de la columna Tienda_tipoimpuestoaplicar en la fila representada por este objeto.
	 */
	public Object getTienda_tipoimpuestoaplicar() throws SQLException {
		return select.getCacheValueAt(rowNumber, 17);
	}

	/**
	 * Devuelve el valor de la columna Tienda_indicadesctoempleado en la fila representada por este objeto.
	 */
	public Object getTienda_indicadesctoempleado() throws SQLException {
		return select.getCacheValueAt(rowNumber, 18);
	}

	/**
	 * Devuelve el valor de la columna Tienda_desctoventaempleado en la fila representada por este objeto.
	 */
	public Object getTienda_desctoventaempleado() throws SQLException {
		return select.getCacheValueAt(rowNumber, 19);
	}

	/**
	 * Devuelve el valor de la columna Tienda_cambioprecioencaja en la fila representada por este objeto.
	 */
	public Object getTienda_cambioprecioencaja() throws SQLException {
		return select.getCacheValueAt(rowNumber, 20);
	}

	/**
	 * Devuelve el valor de la columna Tienda_fechatienda en la fila representada por este objeto.
	 */
	public Object getTienda_fechatienda() throws SQLException {
		return select.getCacheValueAt(rowNumber, 21);
	}

	/**
	 * Devuelve el valor de la columna Tienda_utilizarvendedor en la fila representada por este objeto.
	 */
	public Object getTienda_utilizarvendedor() throws SQLException {
		return select.getCacheValueAt(rowNumber, 22);
	}

	/**
	 * Devuelve el valor de la columna Tienda_dbclase en la fila representada por este objeto.
	 */
	public Object getTienda_dbclase() throws SQLException {
		return select.getCacheValueAt(rowNumber, 23);
	}

	/**
	 * Devuelve el valor de la columna Tienda_dburllocal en la fila representada por este objeto.
	 */
	public Object getTienda_dburllocal() throws SQLException {
		return select.getCacheValueAt(rowNumber, 24);
	}

	/**
	 * Devuelve el valor de la columna Tienda_dburlservidor en la fila representada por este objeto.
	 */
	public Object getTienda_dburlservidor() throws SQLException {
		return select.getCacheValueAt(rowNumber, 25);
	}

	/**
	 * Devuelve el valor de la columna Tienda_dbusuario en la fila representada por este objeto.
	 */
	public Object getTienda_dbusuario() throws SQLException {
		return select.getCacheValueAt(rowNumber, 26);
	}

	/**
	 * Devuelve el valor de la columna Tienda_dbclave en la fila representada por este objeto.
	 */
	public Object getTienda_dbclave() throws SQLException {
		return select.getCacheValueAt(rowNumber, 27);
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