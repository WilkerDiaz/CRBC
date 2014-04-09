package com.becoblohm.cr.sincronizador.dbbeans.caja;

import java.sql.SQLException;

import com.becoblohm.cr.manejadorsesion.Sesion;
import com.ibm.db.beans.DBParameterMetaData;
import com.ibm.db.beans.DBSelect;

/**
 * Esta clase establece los valores de la propiedad DBSelect. También 
 * proporciona métodos que ejecutan la sentencia SQL, devuelven una 
 * referencia a DBSelect y una matriz de objetos que representan las 
 * filas del conjunto de resultados. Generated: 21/10/2003 09:42:10 AM 
 */

public class GetTiendaSolicitada {
	private DBSelect select;

	/**
	 * Constructor para una clase DBSelect.
	 */
	public GetTiendaSolicitada() {
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
				"SELECT DISTINCT tienda.nombresucursal, tienda.rif, tienda.nit, tienda.direccion, tienda.codarea, tienda.numtelefono, tienda.codareafax, tienda.numfax, tienda.direccionfiscal, tienda.codareafiscal, tienda.numtelefonofiscal, tienda.codareafaxfiscal, tienda.numfaxfiscal, tienda.monedabase, tienda.codregion, tienda.limiteentregacaja, tienda.tipoimpuestoaplicar, tienda.indicadesctoempleado, tienda.desctoventaempleado, tienda.cambioprecioencaja, tienda.fechatienda, tienda.utilizarvendedor, tienda.dbclase, tienda.dburllocal, tienda.dburlservidor, tienda.dbusuario, tienda.dbclave, tienda.razonsocial FROM tienda WHERE tienda.numtienda = :numTienda");
			DBParameterMetaData parmMetaData = select.getParameterMetaData();
			parmMetaData.setParameter(
				1,
				"numTienda",
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
	public void execute(Short numTienda) throws SQLException {
		try {
			select.setUsername(Sesion.getDbUsuario());
			select.setPassword(Sesion.getDbClave());
			select.setParameter("numTienda", numTienda);
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
	public GetTiendaSolicitadaRow[] getRows() {
		GetTiendaSolicitadaRow[] rows =
			new GetTiendaSolicitadaRow[select.getRowCount()];
		for (int i = 0; i <= select.getRowCount() - 1; i++) {
			rows[i] = new GetTiendaSolicitadaRow(select, i + 1);
		};
		return rows;
	}
}