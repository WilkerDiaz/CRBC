package com.becoblohm.cr.sincronizador.dbbeans.servidor;
import java.sql.SQLException;

import com.ibm.db.beans.DBSelect;

/**
 * Esta clase establece los valores de la propiedad DBSelect. También proporciona 
 * métodos que ejecutan la sentencia SQL, devuelven 
 * una referencia a DBSelect y una matriz de objetos que 
 * representan las filas del conjunto de resultados.
 * Generated:  10/03/2004 03:24:59 PM
 */

public class GetProductoSrv {
	private DBSelect select;

	/**
	 * Constructor para una clase DBSelect.
	 */
	public GetProductoSrv() {
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
			"SELECT DISTINCT artitda.codaat, articulo.descma, articulo.des1ma, articulo.des2ma, articulo.unvema, SUBSTR(VARCHAR(ARTITDA.CODAAT), 1, 2) as departamento, SUBSTR(VARCHAR(ARTITDA.CODAAT), 3, 2) as lineaseccion, costos.coslllca, artitda.prevat, artitda.impuat as impuesto, articulo.empvma as empaque, articulo.povema as descuentoempaque, articulo.deemma as descuentoempleado, articulo.esrema, articulo.fecama 			FROM SISTEMA.ARTICULO as articulo inner join COMPRAS.LMLCA03 as costos on (articulo.codama = costos.artillca) inner join COMPRAS.ARTITDA as artitda on (articulo.codama = artitda.codaat) 			WHERE artitda.codaat IN (SELECT articulo.codama FROM SISTEMA.ARTICULO as articulo, COMPRAS.ARTITDA as artitda 			WHERE (CAST (ARTITDA.TIENAT AS SMALLINT) = 3) AND (ARTICULO.ESREMA = 'V') AND (ARTITDA.EXACAT <> 0) AND ((SUBSTR(VARCHAR(ARTITDA.CODAAT), 1, 2) = '90') OR (ARTITDA.CODAAT < 3500000)) and (articulo.codama = artitda.codaat) )"); 
				//"SELECT * FROM COMPRAS.ARTITDA, SISTEMA.ARTICULO WHERE (COMPRAS.ARTITDA.CODAAT = SISTEMA.ARTICULO.CODAMA) AND (CAST (COMPRAS.ARTITDA.TIENAT AS SMALLINT) = 3) AND (SISTEMA.ARTICULO.ESREMA = 'V') AND (COMPRAS.ARTITDA.EXACAT <> 0) AND ((SUBSTR(VARCHAR(COMPRAS.ARTITDA.CODAAT), 1, 2) = '90') OR (COMPRAS.ARTITDA.CODAAT < 3500000))");
			//"SELECT count(*) FROM SISTEMA.ARTICULO as articulo inner join COMPRAS.ARTITDA as artitda on (articulo.codama = artitda.codaat) WHERE (CAST (ARTITDA.TIENAT AS SMALLINT) = 3) AND (ARTICULO.ESREMA = 'V') AND (ARTITDA.EXACAT <> 0) AND ((SUBSTR(VARCHAR(ARTITDA.CODAAT), 1, 2) = '90') OR (ARTITDA.CODAAT < 3500000))"			
			//DBParameterMetaData parmMetaData = select.getParameterMetaData();
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
	public GetProductoSrvRow[] getRows() {
		GetProductoSrvRow[] rows = new GetProductoSrvRow[select.getRowCount()];
		for (int i = 0; i <= select.getRowCount() - 1; i++) {
			rows[i] = new GetProductoSrvRow(select, i + 1);
		};
		return rows;
	}
}