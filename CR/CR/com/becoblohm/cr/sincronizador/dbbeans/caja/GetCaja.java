package com.becoblohm.cr.sincronizador.dbbeans.caja;

import java.sql.SQLException;

import com.ibm.db.beans.DBSelect;
import com.becoblohm.cr.manejadorsesion.Sesion;

/**
 * Esta clase establece los valores de la propiedad DBSelect. Tambi�n proporciona 
 * m�todos que ejecutan la sentencia SQL, devuelven
 * una referencia a DBSelect y una matriz de objetos que 
 * representan las filas del conjunto de resultados.
 * Generated:  17/10/2003 08:41:17 AM
 */

public class GetCaja {
	private DBSelect select;

	/**
	 * Constructor para una clase DBSelect.
	 */
	public GetCaja() {
		super();
		initializer();
	}

	/**
	 * Crea una instancia DBSelect e inicializa sus propiedades.
	 */
	protected void initializer() {
		select = new DBSelect();
		try {
			select.setDriverName(Sesion.getDbClaseLocal());
			select.setUrl(Sesion.getDbUrlLocal());
			select.setCommand("SELECT DISTINCT * FROM caja");
		} catch (SQLException ex) {
			//ex.printStackTrace();
		}
	}

	/**
	 * Ejecuta la sentencia SQL.
	 */
	public void execute() throws SQLException {
		try {
			select.setUsername(Sesion.getDbUsuario());
			select.setPassword(Sesion.getDbClave());
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
	public GetCajaRow[] getRows() {
		GetCajaRow[] rows = new GetCajaRow[select.getRowCount()];
		for (int i = 0; i <= select.getRowCount() - 1; i++) {
			rows[i] = new GetCajaRow(select, i + 1);
		};
		return rows;
	}
}