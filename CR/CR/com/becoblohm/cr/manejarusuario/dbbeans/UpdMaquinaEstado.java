/**
 * =============================================================================
 * Proyecto   : CRBECOEPA
 * Paquete    : com.becoblohm.cr.manejarusuario.dbbeans
 * Programa   : UpdMaquinaEstado.java
 * Creado por : Programador3 - Alexis Gu�dez L�pez
 * Creado en  : 15/10/2003 04:52:05 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versi�n     : 1.0.1
 * Fecha       : 14/11/2003 04:15:41 PM
 * Analista    : Programador3 - Alexis Gu�dez L�pez
 * Descripci�n : Ajuste por cambios en dise�o de la Base de Datos
 * =============================================================================
 */
package com.becoblohm.cr.manejarusuario.dbbeans;
import java.sql.SQLException;

import com.becoblohm.cr.manejadorsesion.Sesion;
import com.ibm.db.beans.DBModify;
import com.ibm.db.beans.DBParameterMetaData;

/**
 * Esta clase establece los valores de la propiedad DBModify. Tambi�n 
 * proporciona m�todos que ejecutan la sentencia SQL 
 * y devuelven una referencia a DBModify.
 */

public class UpdMaquinaEstado {
	private DBModify modify;

	/**
	 * Constructor para una clase DBModify.
	 */
	public UpdMaquinaEstado() {
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
				"UPDATE maquinadeestado SET edofinal = :edoFinal WHERE maquinadeestado.codmetodo = :codMetodo AND maquinadeestado.edoinicial = :edoInicial");
			DBParameterMetaData parmMetaData = modify.getParameterMetaData();
			parmMetaData.setParameter(
				1,
				"edoFinal",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				2,
				"codMetodo",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.SMALLINT,
				Short.class);
			parmMetaData.setParameter(
				3,
				"edoInicial",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Ejecuta la sentencia SQL.
	 */
	public void execute(String edoFinal, Short codMetodo, String edoInicial)
		throws SQLException {
		try {
			modify.setUsername(Sesion.getDbUsuario());
			modify.setPassword(Sesion.getDbClave());
			modify.setParameter("edoFinal", edoFinal);
			modify.setParameter("codMetodo", codMetodo);
			modify.setParameter("edoInicial", edoInicial);
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