/**
 * =============================================================================
 * Proyecto   : CRBECOEPA
 * Paquete    : com.becoblohm.cr.sincronizador
 * Programa   : GetMaetda.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 04/10/2003 01:51:35 PM
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
package com.becoblohm.cr.sincronizador.dbbeans.servidor;

import java.sql.SQLException;

import com.becoblohm.cr.manejadorsesion.Sesion;
import com.ibm.db.beans.DBSelect;

/**
 * Esta clase establece los valores de la propiedad DBSelect. También proporciona 
 * métodos que ejecutan la sentencia SQL, devuelven
 * una referencia a DBSelect y una matriz de objetos que 
 * representan las filas del conjunto de resultados.
 * Generated:  03/10/2003 02:27:32 PM
 */

public class GetMaetda {
	private DBSelect select;

	/**
	 * Constructor para una clase DBSelect.
	 */
	public GetMaetda() {
		super();
		initializer();
	}

	/**
	 * Crea una instancia DBSelect e inicializa sus propiedades.
	 */
	protected void initializer() {
		select = new DBSelect();
		try {
			select.setDriverName(Sesion.getDbClaseServidor());
			select.setUrl(Sesion.getDbUrlServidor());
			select.setCommand(
				"SELECT DISTINCT VENTAS.MAETDA.RIFTIE, VENTAS.MAETDA.NITTIE, VENTAS.MAETDA.NUMTIE, VENTAS.MAETDA.RAZSOC, VENTAS.MAETDA.DIREC1, VENTAS.MAETDA.DIREC2, VENTAS.MAETDA.DOMFIS, VENTAS.MAETDA.CODAR1, VENTAS.MAETDA.TLFNO1, VENTAS.MAETDA.TLFNO2, VENTAS.MAETDA.TLFNO3, VENTAS.MAETDA.CODFAX, VENTAS.MAETDA.NUMFAX, VENTAS.MAETDA.DIRSU1, VENTAS.MAETDA.DIRSU2, VENTAS.MAETDA.DOMSUC, VENTAS.MAETDA.CODASU, VENTAS.MAETDA.TLFSUC, VENTAS.MAETDA.CODFSU, VENTAS.MAETDA.FAXSUC, VENTAS.MAETDA.PUBLI1, VENTAS.MAETDA.PUBLI2, VENTAS.MAETDA.PUBLI3, VENTAS.MAETDA.PUBLI4, VENTAS.MAETDA.PUBLI5, VENTAS.MAETDA.FONDO, VENTAS.MAETDA.STAIMP, VENTAS.MAETDA.PORIMP, VENTAS.MAETDA.STADES, VENTAS.MAETDA.PORDES, VENTAS.MAETDA.STACPC, VENTAS.MAETDA.HORSIS, VENTAS.MAETDA.FECSIS FROM VENTAS.MAETDA");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Ejecuta la sentencia SQL.
	 */
	public void execute() throws SQLException {
		try {
			select.setUsername(Sesion.getDbUsuario());
			select.setPassword(Sesion.getDbClave());
			//select.setMaxRows(maxRows);
			select.execute();
		}

		// Liberar recursos del objeto select.
		finally {
			select.close();
		}
	}

	/**
	 * Devuelve una referencia a DBSelect.
	 */
	public DBSelect getDBSelect() {
		return select;
	}

	/**
	 * Devuelve una matriz de objetos que representan las filas en el conjunto de resultados.
	 */
	public GetMaetdaRow[] getRows() {
		GetMaetdaRow[] rows = new GetMaetdaRow[select.getRowCount()];
		for (int i = 0; i <= select.getRowCount() - 1; i++) {
			rows[i] = new GetMaetdaRow(select, i + 1);
		};
		return rows;
	}
}