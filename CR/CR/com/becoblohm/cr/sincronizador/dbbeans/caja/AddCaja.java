package com.becoblohm.cr.sincronizador.dbbeans.caja;

import java.sql.SQLException;

import com.becoblohm.cr.manejadorsesion.Sesion;
import com.ibm.db.beans.DBModify;
import com.ibm.db.beans.DBParameterMetaData;

/**
 * Esta clase establece los valores de la propiedad DBModify. También 
 * proporciona métodos que ejecutan la sentencia SQL 
 * y devuelven una referencia a DBModify.
 * Generated:  08/10/2003 04:52:57 PM
 */

public class AddCaja {
	private DBModify modify;

	/**
	 * Constructor para una clase DBModify.
	 */
	public AddCaja() {
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
				"INSERT INTO caja ( numtienda, numcaja, idestadocaja, codusuario, numtransaccion, numnofiscal, numregistro, numseqmerchant, nivelauditoria, fechareportez, versionsistema, modelo ) VALUES ( :numTienda, :numCaja, :idEstadoCaja, :codUsuario, :numTransaccion, :numNoFiscal, :numRegistro, :numSeqMerchant, :nivelAuditoria, :fechaReporteZ, :versionSistema, :modelo )");
			DBParameterMetaData parmMetaData = modify.getParameterMetaData();
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
			parmMetaData.setParameter(
				3,
				"idEstadoCaja",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				4,
				"codUsuario",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				5,
				"numTransaccion",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.INTEGER,
				Integer.class);
			parmMetaData.setParameter(
				6,
				"numNoFiscal",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.INTEGER,
				Integer.class);
			parmMetaData.setParameter(
				7,
				"numRegistro",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.INTEGER,
				Integer.class);
			parmMetaData.setParameter(
				8,
				"numSeqMerchant",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.INTEGER,
				Integer.class);
			parmMetaData.setParameter(
				9,
				"nivelAuditoria",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				10,
				"fechaReporteZ",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.DATE,
				java.sql.Date.class);
			parmMetaData.setParameter(
				11,
				"versionSistema",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				12,
				"modelo",
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
	public void execute(
		Short numTienda,
		Short numCaja,
		String idEstadoCaja,
		String codUsuario,
		Integer numTransaccion,
		Integer numNoFiscal,
		Integer numRegistro,
		Integer numSeqMerchant,
		String nivelAuditoria,
		java.sql.Date fechaReporteZ,
		String versionSistema,
		String modelo)
		throws SQLException {
		try {
			modify.setUsername(Sesion.getDbUsuario());
			modify.setPassword(Sesion.getDbClave());
			modify.setParameter("numTienda", numTienda);
			modify.setParameter("numCaja", numCaja);
			modify.setParameter("idEstadoCaja", idEstadoCaja);
			modify.setParameter("codUsuario", codUsuario);
			modify.setParameter("numTransaccion", numTransaccion);
			modify.setParameter("numNoFiscal", numNoFiscal);
			modify.setParameter("numRegistro", numRegistro);
			modify.setParameter("numSeqMerchant", numSeqMerchant);
			modify.setParameter("nivelAuditoria", nivelAuditoria);
			modify.setParameter("fechaReporteZ", fechaReporteZ);
			modify.setParameter("versionSistema", versionSistema);
			modify.setParameter("modelo", modelo);
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