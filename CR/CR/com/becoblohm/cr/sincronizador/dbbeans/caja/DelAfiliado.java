package com.becoblohm.cr.sincronizador.dbbeans.caja;

import java.sql.SQLException;

import com.becoblohm.cr.manejadorsesion.Sesion;
import com.ibm.db.beans.DBModify;

/**
 * Esta clase establece los valores de la propiedad DBModify. Tambi�n 
 * proporciona m�todos que ejecutan la sentencia SQL 
 * y devuelven una referencia a DBModify.
 * Generated:  16/10/2003 02:56:28 PM
 */

public class DelAfiliado {
	private DBModify modify;

	/**
	 * Constructor para una clase DBModify.
	 */
	public DelAfiliado() {
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
			modify.setCommand("DELETE FROM afiliado");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Ejecuta la sentencia SQL.
	 */
	public void execute() throws SQLException {
		try {
			modify.setUsername(Sesion.getDbUsuario());
			modify.setPassword(Sesion.getDbClave());
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