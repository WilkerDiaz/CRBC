package com.becoblohm.cr.sincronizador.dbbeans.caja;

import java.sql.SQLException;

import com.becoblohm.cr.manejadorsesion.Sesion;
import com.ibm.db.beans.DBModify;
import com.ibm.db.beans.DBParameterMetaData;

/**
 * Esta clase establece los valores de la propiedad DBModify. También 
 * proporciona métodos que ejecutan la sentencia SQL 
 * y devuelven una referencia a DBModify.
 * Generated:  16/10/2003 01:48:22 PM
 */

public class AddAfiliado {
	private DBModify modify;

	/**
	 * Constructor para una clase DBModify.
	 */
	public AddAfiliado() {
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
				"INSERT INTO afiliado ( codafiliado, tipoafiliado, nombre, numtienda, numficha, coddepartamento, codcargo, nitcliente, direccion, direccionfiscal, codarea, numtelefono, fechaafiliacion, exentoimpuesto, estadoafiliado, estadocolaborador, actualizacion ) VALUES ( :codAfiliado, :tipoAfiliado, :nombre, :numTienda, :numFicha, :codDepartamento, :codCargo, :nitCliente, :direccion, :direccionFiscal, :codArea, :numTelefono, :fechaAfiliacion, :exentoImpuesto, :estadoAfiliado, :estadoColaborador, :actualizacion )");
			DBParameterMetaData parmMetaData = modify.getParameterMetaData();
			parmMetaData.setParameter(
				1,
				"codAfiliado",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				2,
				"tipoAfiliado",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				3,
				"nombre",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				4,
				"numTienda",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.SMALLINT,
				Short.class);
			parmMetaData.setParameter(
				5,
				"numFicha",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				6,
				"codDepartamento",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				7,
				"codCargo",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				8,
				"nitCliente",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				9,
				"direccion",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				10,
				"direccionFiscal",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				11,
				"codArea",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				12,
				"numTelefono",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				13,
				"fechaAfiliacion",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.DATE,
				java.sql.Date.class);
			parmMetaData.setParameter(
				14,
				"exentoImpuesto",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				15,
				"estadoAfiliado",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				16,
				"estadoColaborador",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				17,
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
		String codAfiliado,
		String tipoAfiliado,
		String nombre,
		Short numTienda,
		String numFicha,
		String codDepartamento,
		String codCargo,
		String nitCliente,
		String direccion,
		String direccionFiscal,
		String codArea,
		String numTelefono,
		java.sql.Date fechaAfiliacion,
		String exentoImpuesto,
		String estadoAfiliado,
		String estadoColaborador,
		java.sql.Timestamp actualizacion)
		throws SQLException {
		try {
			modify.setUsername(Sesion.getDbUsuario());
			modify.setPassword(Sesion.getDbClave());
			modify.setParameter("codAfiliado", codAfiliado);
			modify.setParameter("tipoAfiliado", tipoAfiliado);
			modify.setParameter("nombre", nombre);
			modify.setParameter("numTienda", numTienda);
			modify.setParameter("numFicha", numFicha);
			modify.setParameter("codDepartamento", codDepartamento);
			modify.setParameter("codCargo", codCargo);
			modify.setParameter("nitCliente", nitCliente);
			modify.setParameter("direccion", direccion);
			modify.setParameter("direccionFiscal", direccionFiscal);
			modify.setParameter("codArea", codArea);
			modify.setParameter("numTelefono", numTelefono);
			modify.setParameter("fechaAfiliacion", fechaAfiliacion);
			modify.setParameter("exentoImpuesto", exentoImpuesto);
			modify.setParameter("estadoAfiliado", estadoAfiliado);
			modify.setParameter("estadoColaborador", estadoColaborador);
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