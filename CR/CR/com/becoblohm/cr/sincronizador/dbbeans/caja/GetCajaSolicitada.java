package com.becoblohm.cr.sincronizador.dbbeans.caja;

import java.sql.SQLException;

import com.becoblohm.cr.manejadorsesion.Sesion;
import com.ibm.db.beans.DBParameterMetaData;
import com.ibm.db.beans.DBSelect;

/**
 * Esta clase establece los valores de la propiedad DBSelect. También proporciona 
 * métodos que ejecutan la sentencia SQL, devuelven
 * una referencia a DBSelect y una matriz de objetos que 
 * representan las filas del conjunto de resultados.
 * Generated:  22/10/2003 01:16:45 PM
 */

public class GetCajaSolicitada {
	private DBSelect select;

	/**
	 * Constructor para una clase DBSelect.
	 */
	public GetCajaSolicitada() {
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
			select.setCommand(
				"SELECT DISTINCT caja.idestadocaja, caja.codusuario, caja.numtransaccion, caja.numnofiscal, caja.numregistro, caja.numseqmerchant, caja.nivelauditoria, caja.fechareportez, caja.versionsistema, caja.modelo, caja.montorecaudado FROM caja WHERE caja.numtienda = :numTienda AND caja.numcaja = :numCaja");
			DBParameterMetaData parmMetaData = select.getParameterMetaData();
			parmMetaData.setParameter(
				1,
				"numTienda",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.SMALLINT,
				Short.class);
			parmMetaData.setParameter(
				2,
				"numCaja",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.SMALLINT,
				Short.class);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Ejecuta la sentencia SQL.
	 */
	public void execute(Short numTienda, Short numCaja) throws SQLException {
		try {
			select.setUsername(Sesion.getDbUsuario());
			select.setPassword(Sesion.getDbClave());
			select.setParameter("numTienda", numTienda);
			select.setParameter("numCaja", numCaja);
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
	public GetCajaSolicitadaRow[] getRows() {
		GetCajaSolicitadaRow[] rows =
			new GetCajaSolicitadaRow[select.getRowCount()];
		for (int i = 0; i <= select.getRowCount() - 1; i++) {
			rows[i] = new GetCajaSolicitadaRow(select, i + 1);
		};
		return rows;
	}
}