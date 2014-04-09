package com.becoblohm.cr.sincronizador.dbbeans.servidor;

import java.sql.SQLException;

import com.ibm.db.beans.DBSelect;

/**
 * Esta clase establece los valores de la propiedad DBSelect. También proporciona 
 * métodos que ejecutan la sentencia SQL, devuelven 
 * una referencia a DBSelect y una matriz de objetos que 
 * representan las filas del conjunto de resultados.
 * Generated:  15/04/2004 03:49:51 PM
 */

public class GetClienteNatural {
	private DBSelect select;

	/**
	 * Constructor para una clase DBSelect.
	 */
	public GetClienteNatural() {
		super();
		initializer();
	}

	/**
	 * Crea una instancia DBSelect e inicializa sus propiedades.
	 */
	protected void initializer() {
		select = new DBSelect();
		try {
			select.setDriverName("com.ibm.as400.access.AS400JDBCDriver");
			select.setUrl("jdbc:as400://192.168.101.14");
			select.setCommand(
			"SELECT DISTINCT MERCADEO.CABAFILIA.NOMAFI1,MERCADEO.CABAFILIA.NOMAFI2,MERCADEO.CABAFILIA.APEAFI1,MERCADEO.CABAFILIA.APEAFI2,MERCADEO.CABAFILIA.URBANI,MERCADEO.CABAFILIA.CALLAV,MERCADEO.CABAFILIA.CASEDI,MERCADEO.CABAFILIA.PISAPT,MERCADEO.CABAFILIA.CODARE,MERCADEO.CABAFILIA.HABOFC,MERCADEO.CABAFILIA.FAXCEL,MERCADEO.CABAFILIA.TIPAFI,MERCADEO.CABAFILIA.NUMTIE,MERCADEO.CABAFILIA.STATUS,MERCADEO.CABAFILIA.NUMAFIAG,MERCADEO.CABAFILIA.FECAFI,MERCADEO.CABAFILIA.ULTACT,MERCADEO.CABAFILIA.HORAFI,MERCADEO.CABAFILIA.HORACT FROM MERCADEO.CABAFILIA WHERE MERCADEO.CABAFILIA.TIPAFI = 'N'");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Ejecuta la sentencia SQL.
	 */
	public void execute() throws SQLException {
		try {
			select.setUsername("INTRANET");
			select.setPassword("INTRANET");
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
	public GetClienteNaturalRow[] getRows() {
		GetClienteNaturalRow[] rows =
			new GetClienteNaturalRow[select.getRowCount()];
		for (int i = 0; i <= select.getRowCount() - 1; i++) {
			rows[i] = new GetClienteNaturalRow(select, i + 1);
		};
		return rows;
	}
}