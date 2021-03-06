package com.becoblohm.cr.sincronizador.dbbeans.caja;

import java.sql.SQLException;

import com.becoblohm.cr.manejadorsesion.Sesion;
import com.ibm.db.beans.DBModify;
import com.ibm.db.beans.DBParameterMetaData;

/**
 * Esta clase establece los valores de la propiedad DBModify. Tambi�n 
 * proporciona m�todos que ejecutan la sentencia SQL 
 * y devuelven una referencia a DBModify.
 * Generated:  08/10/2003 04:53:36 PM
 */

public class AddEstadoCaja {
	private DBModify modify;

	/**
	 * Constructor para una clase DBModify.
	 */
	public AddEstadoCaja() {
		super();
		initializer();
	}

	/**
	 * Crea una instancia DBModify e inicializa sus propiedades.
	 */
	protected void initializer() {
		modify = new DBModify();
		try {
			modify.setDriverName(Sesion.getDbClaseLocal());
			modify.setUrl(Sesion.getDbUrlLocal());
			modify.setCommand(
				"INSERT INTO estadodecaja ( idestado, descripcion ) VALUES ( :idEstado, :descripcion )");
			DBParameterMetaData parmMetaData = modify.getParameterMetaData();
			parmMetaData.setParameter(
				1,
				"idEstado",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				2,
				"descripcion",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Ejecuta la sentencia SQL.
	 */
	public void execute(String idEstado, String descripcion)
		throws SQLException {
		try {
			modify.setUsername(Sesion.getDbUsuario());
			modify.setPassword(Sesion.getDbClave());
			modify.setParameter("idEstado", idEstado);
			modify.setParameter("descripcion", descripcion);
			modify.execute();
		}

		// Liberar recursos del objeto modify.
		finally {
			modify.close();
		}
	}

	/**
	 * Devuelve una referencia a DBModify.
	 */
	public DBModify getDBModify() {
		return modify;
	}
}