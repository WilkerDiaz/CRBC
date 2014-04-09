/**
 * =============================================================================
 * Proyecto   : CRBECOEPA
 * Paquete    : com.becoblohm.cr.manejarusuario.dbbeans
 * Programa   : AddUsuario.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 21/10/2003 10:01:57 AM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 
 * Fecha       : 
 * Analista    : 
 * Descripción : 
 * =============================================================================
 */
package com.becoblohm.cr.manejarusuario.dbbeans;

import java.sql.SQLException;

import com.becoblohm.cr.manejadorsesion.Sesion;
import com.ibm.db.beans.DBModify;
import com.ibm.db.beans.DBParameterMetaData;

/**
 * Descripción: 
 * 		Esta clase establece los valores de la propiedad DBModify. También 
 * proporciona métodos que ejecutan la sentencia SQL que inserta los datos 
 * indicados a la tabla Usuario de la Caja Registradora y devuelven una 
 * referencia a DBModify.
 */

public class AddUsuario {
	private DBModify modify;

	/**
	 * Constructor para una clase DBModify.
	 */
	public AddUsuario() {
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
				"INSERT INTO usuario ( numtienda, numficha, codigobarra, codperfil, clave, nivelauditoria, nombre, puedecambiarclave, indicacambiarclave, fechacreacion, ultimocambioclave, tiempovigenciaclave, regvigente, actualizacion ) VALUES ( :numTienda, :numFicha, MD5(:codigoBarra), :codPerfil, MD5(:clave), :nivelAuditoria, :nombre, :puedeCambiarClave, :indicaCambiarClave, :fechaCreacion, :ultimoCambioClave, :tiempoVigenciaClave, :regVigente, :actualizacion )");
			DBParameterMetaData parmMetaData = modify.getParameterMetaData();
			parmMetaData.setParameter(
				1,
				"numTienda",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.SMALLINT,
				Short.class);
			parmMetaData.setParameter(
				2,
				"numFicha",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				3,
				"codigoBarra",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				4,
				"codPerfil",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				5,
				"clave",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				6,
				"nivelAuditoria",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				7,
				"nombre",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				8,
				"puedeCambiarClave",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				9,
				"indicaCambiarClave",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				10,
				"fechaCreacion",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.DATE,
				java.sql.Date.class);
			parmMetaData.setParameter(
				11,
				"ultimoCambioClave",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.TIMESTAMP,
				java.sql.Timestamp.class);
			parmMetaData.setParameter(
				12,
				"tiempoVigenciaClave",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.SMALLINT,
				Short.class);
			parmMetaData.setParameter(
				13,
				"regVigente",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				14,
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
		Short numTienda,
		String numFicha,
		String codigoBarra,
		String codPerfil,
		String clave,
		String nivelAuditoria,
		String nombre,
		String puedeCambiarClave,
		String indicaCambiarClave,
		java.sql.Date fechaCreacion,
		java.sql.Timestamp ultimoCambioClave,
		Short tiempoVigenciaClave,
		String regVigente,
		java.sql.Timestamp actualizacion)
		throws SQLException {
		try {
			modify.setUsername(Sesion.getDbUsuario());
			modify.setPassword(Sesion.getDbClave());
			modify.setParameter("numTienda", numTienda);
			modify.setParameter("numFicha", numFicha);
			modify.setParameter("codigoBarra", codigoBarra);
			modify.setParameter("codPerfil", codPerfil);
			modify.setParameter("clave", clave);
			modify.setParameter("nivelAuditoria", nivelAuditoria);
			modify.setParameter("nombre", nombre);
			modify.setParameter("puedeCambiarClave", puedeCambiarClave);
			modify.setParameter("indicaCambiarClave", indicaCambiarClave);
			modify.setParameter("fechaCreacion", fechaCreacion);
			modify.setParameter("ultimoCambioClave", ultimoCambioClave);
			modify.setParameter("tiempoVigenciaClave", tiempoVigenciaClave);
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