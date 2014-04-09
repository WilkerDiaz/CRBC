package com.becoblohm.cr.sincronizador.dbbeans.servidor;
import java.sql.SQLException;

import com.becoblohm.cr.manejadorsesion.Sesion;
import com.ibm.db.beans.DBSelect;

/**
 * Esta clase establece los valores de la propiedad DBSelect. Tambi�n proporciona 
 * m�todos que ejecutan la sentencia SQL, devuelven
 * una referencia a DBSelect y una matriz de objetos que 
 * representan las filas del conjunto de resultados.
 * Generated:  17/10/2003 10:36:47 AM
 */

public class GetTiendaSrv {
	private DBSelect select;

	/**
	 * Constructor para una clase DBSelect.
	 */
	public GetTiendaSrv() {
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
			select.setCommand("SELECT DISTINCT * FROM COMPRAS.MAECTDA");
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
	public GetTiendaSrvRow[] getRows() {
		GetTiendaSrvRow[] rows = new GetTiendaSrvRow[select.getRowCount()];
		for (int i = 0; i <= select.getRowCount() - 1; i++) {
			rows[i] = new GetTiendaSrvRow(select, i + 1);
		};
		return rows;
	}
}