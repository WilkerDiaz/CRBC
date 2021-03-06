package com.becoblohm.cr.sincronizador.dbbeans.servidor;

import java.sql.SQLException;

import com.becoblohm.cr.manejadorsesion.Sesion;
import com.ibm.db.beans.DBSelect;

/**
 * Esta clase establece los valores de la propiedad DBSelect. Tambi�n proporciona 
 * m�todos que ejecutan la sentencia SQL, devuelven
 * una referencia a DBSelect y una matriz de objetos que 
 * representan las filas del conjunto de resultados.
 * Generated:  04/10/2003 05:39:26 PM
 */

public class GetColaborador {
	private DBSelect select;

	/**
	 * Constructor para una clase DBSelect.
	 */
	public GetColaborador() {
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
				"SELECT DISTINCT PERSONAL.MAEPDBC.FICHMDBC, PERSONAL.MAEPDBC.CEDUMDBC, PERSONAL.MAEPDBC.FECNMDBC, PERSONAL.MAEPDBC.CARGMDBC, PERSONAL.MAEPDBC.TIENMDBC, PERSONAL.MAEPDBC.DEPAMDBC, PERSONAL.MAEPDBC.NOMBMDBC, PERSONAL.MAEPDBC.APELMDBC FROM PERSONAL.MAEPDBC");
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
	public GetColaboradorRow[] getRows() {
		GetColaboradorRow[] rows = new GetColaboradorRow[select.getRowCount()];
		for (int i = 0; i <= select.getRowCount() - 1; i++) {
			rows[i] = new GetColaboradorRow(select, i + 1);
		};
		return rows;
	}
}