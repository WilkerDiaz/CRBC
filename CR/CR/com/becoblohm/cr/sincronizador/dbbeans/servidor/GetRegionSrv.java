/**
 * =============================================================================
 * Proyecto   : CRBECOEPA
 * Paquete    : com.becoblohm.cr.sincronizador
 * Programa   : GetRegionSrv.java
 * Creado por : Programador3 - Alexis Gu�dez L�pez
 * Creado en  : 04/10/2003 01:51:35 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versi�n     : 
 * Fecha       : 
 * Analista    : 
 * Descripci�n : 
 * =============================================================================
 */
package com.becoblohm.cr.sincronizador.dbbeans.servidor;

import java.sql.SQLException;

import com.becoblohm.cr.manejadorsesion.Sesion;
import com.ibm.db.beans.DBSelect;

/**
 * Esta clase establece los valores de la propiedad DBSelect. Tambi�n proporciona 
 * m�todos que ejecutan la sentencia SQL, devuelven
 * una referencia a DBSelect y una matriz de objetos que 
 * representan las filas del conjunto de resultados.
 * Generated:  03/10/2003 02:29:42 PM
 */

public class GetRegionSrv {
	private DBSelect select;

	/**
	 * Constructor para una clase DBSelect.
	 */
	public GetRegionSrv() {
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
				"SELECT DISTINCT COMPRAS.MAESZON.ZONAMZON, COMPRAS.MAESZON.DESCMZON FROM COMPRAS.MAESZON");
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
	public GetRegionSrvRow[] getRows() {
		GetRegionSrvRow[] rows = new GetRegionSrvRow[select.getRowCount()];
		for (int i = 0; i <= select.getRowCount() - 1; i++) {
			rows[i] = new GetRegionSrvRow(select, i + 1);
		};
		return rows;
	}
}