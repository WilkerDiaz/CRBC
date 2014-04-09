package com.becoblohm.cr.sincronizador.dbbeans.caja;

import java.sql.SQLException;

import com.becoblohm.cr.manejadorsesion.Sesion;
import com.ibm.db.beans.DBModify;
import com.ibm.db.beans.DBParameterMetaData;

/**
 * Esta clase establece los valores de la propiedad DBModify. También 
 * proporciona métodos que ejecutan la sentencia SQL 
 * y devuelven una referencia a DBModify.
 * Generated:  08/10/2003 04:53:09 PM
 */

public class AddCargo {
	private DBModify modify;

	/**
	 * Constructor para una clase DBModify.
	 */
	public AddCargo() {
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
				"INSERT INTO cargo ( codcargo, nombre ) VALUES ( :codCargo, :nombre )");
			DBParameterMetaData parmMetaData = modify.getParameterMetaData();
			parmMetaData.setParameter(
				1,
				"codCargo",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				2,
				"nombre",
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
	public void execute(String codCargo, String nombre) throws SQLException {
		try {
			modify.setUsername(Sesion.getDbUsuario());
			modify.setPassword(Sesion.getDbClave());
			modify.setParameter("codCargo", codCargo);
			modify.setParameter("nombre", nombre);
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