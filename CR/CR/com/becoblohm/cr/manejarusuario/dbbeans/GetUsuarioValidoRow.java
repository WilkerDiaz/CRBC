/**
 * =============================================================================
 * Proyecto   : CRBECOEPA
 * Paquete    : com.becoblohm.cr.manejarusuario.dbbeans
 * Programa   : GetUsuarioValidoRow.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 21/10/2003 10:51:11 AM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 
 * Fecha       : 
 * Analista    : 
 * Descripción : 
 * =============================================================================
 */
package com.becoblohm.cr.manejarusuario.dbbeans;

import java.sql.SQLException;

import com.ibm.db.beans.DBSelect;

/**
 * Descripción: 
 * 		Esta clase representa una fila específica de un conjunto de resultados 
 * de la consulta de un usuario específico, validado según su clave secreta,
 * a la tabla Usuario de la Caja Registradora; contenida en DBSelect.
 */

public class GetUsuarioValidoRow {
	private int rowNumber;
	private DBSelect select;

	/**
	 * Construye un objeto que representa una fila a partir de DBSelect.
	 */
	public GetUsuarioValidoRow(DBSelect aRef, int aRowNumber) {
		select = aRef;
		rowNumber = aRowNumber;
	}

	/**
	 * Devuelve el valor de la columna Usuario_numtienda en la fila representada por este objeto.
	 */
	public Object getUsuario_numtienda() throws SQLException {
		return select.getCacheValueAt(rowNumber, 1);
	}

	/**
	 * Devuelve el valor de la columna Usuario_numficha en la fila representada por este objeto.
	 */
	public Object getUsuario_numficha() throws SQLException {
		return select.getCacheValueAt(rowNumber, 2);
	}

	/**
	 * Devuelve el valor de la columna Usuario_codigobarra en la fila representada por este objeto.
	 */
	public Object getUsuario_codigobarra() throws SQLException {
		return select.getCacheValueAt(rowNumber, 3);
	}

	/**
	 * Devuelve el valor de la columna Usuario_codperfil en la fila representada por este objeto.
	 */
	public Object getUsuario_codperfil() throws SQLException {
		return select.getCacheValueAt(rowNumber, 4);
	}

	/**
	 * Devuelve el valor de la columna Usuario_clave en la fila representada por este objeto.
	 */
	public Object getUsuario_clave() throws SQLException {
		return select.getCacheValueAt(rowNumber, 5);
	}

	/**
	 * Devuelve el valor de la columna Usuario_nivelauditoria en la fila representada por este objeto.
	 */
	public Object getUsuario_nivelauditoria() throws SQLException {
		return select.getCacheValueAt(rowNumber, 6);
	}

	/**
	 * Devuelve el valor de la columna Usuario_nombre en la fila representada por este objeto.
	 */
	public Object getUsuario_nombre() throws SQLException {
		return select.getCacheValueAt(rowNumber, 7);
	}

	/**
	 * Devuelve el valor de la columna Usuario_puedecambiarclave en la fila representada por este objeto.
	 */
	public Object getUsuario_puedecambiarclave() throws SQLException {
		return select.getCacheValueAt(rowNumber, 8);
	}

	/**
	 * Devuelve el valor de la columna Usuario_indicacambiarclave en la fila representada por este objeto.
	 */
	public Object getUsuario_indicacambiarclave() throws SQLException {
		return select.getCacheValueAt(rowNumber, 9);
	}

	/**
	 * Devuelve el valor de la columna Usuario_fechacreacion en la fila representada por este objeto.
	 */
	public Object getUsuario_fechacreacion() throws SQLException {
		return select.getCacheValueAt(rowNumber, 10);
	}

	/**
	 * Devuelve el valor de la columna Usuario_ultimocambioclave en la fila representada por este objeto.
	 */
	public Object getUsuario_ultimocambioclave() throws SQLException {
		return select.getCacheValueAt(rowNumber, 11);
	}

	/**
	 * Devuelve el valor de la columna Usuario_tiempovigenciaclave en la fila representada por este objeto.
	 */
	public Object getUsuario_tiempovigenciaclave() throws SQLException {
		return select.getCacheValueAt(rowNumber, 12);
	}

	/**
	 * Devuelve el valor de la columna Usuario_regvigente en la fila representada por este objeto.
	 */
	public Object getUsuario_regvigente() throws SQLException {
		return select.getCacheValueAt(rowNumber, 13);
	}

	/**
	 * Devuelve el valor de la columna Usuario_actualizacion en la fila representada por este objeto.
	 */
	public Object getUsuario_actualizacion() throws SQLException {
		return select.getCacheValueAt(rowNumber, 14);
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