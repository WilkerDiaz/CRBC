/**
 * =============================================================================
 * Proyecto   : CRBECOEPA
 * Paquete    : com.becoblohm.cr.manejarusuario.dbbeans
 * Programa   : AddPerfil.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 21/10/2003 10:01:57 AM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.0.1
 * Fecha       : 14/11/2003 04:15:41 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Ajuste por cambios en diseño de la Base de Datos
 * =============================================================================
 */
package com.becoblohm.cr.manejarusuario.dbbeans;
import java.sql.SQLException;

import com.becoblohm.cr.manejadorsesion.Sesion;
import com.ibm.db.beans.DBModify;
import com.ibm.db.beans.DBParameterMetaData;

/**
 * Esta clase establece los valores de la propiedad DBModify. También 
 * proporciona métodos que ejecutan la sentencia SQL 
 * y devuelven una referencia a DBModify.
 */

public class AddPerfil {
	private DBModify modify;

	/**
	 * Constructor para una clase DBModify.
	 */
	public AddPerfil() {
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
				"INSERT INTO perfil ( codperfil, descripcion, nivelauditoria, regvigente, actualizacion ) VALUES ( :codPerfil, :descripcion, :nivelAuditoria, :regVigente, :actualizacion )");
			DBParameterMetaData parmMetaData = modify.getParameterMetaData();
			parmMetaData.setParameter(
				1,
				"codPerfil",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				2,
				"descripcion",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				3,
				"nivelAuditoria",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				4,
				"regVigente",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				5,
				"actualizacion",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.TIMESTAMP,
				java.sql.Timestamp.class);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Ejecuta la sentencia SQL.
	 */
	public void execute(
		String codPerfil,
		String descripcion,
		String nivelAuditoria,
		String regVigente,
		java.sql.Timestamp actualizacion)
		throws SQLException {
		try {
			modify.setUsername(Sesion.getDbUsuario());
			modify.setPassword(Sesion.getDbClave());
			modify.setParameter("codPerfil", codPerfil);
			modify.setParameter("descripcion", descripcion);
			modify.setParameter("nivelAuditoria", nivelAuditoria);
			modify.setParameter("regVigente", regVigente);
			modify.setParameter("actualizacion", actualizacion);
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