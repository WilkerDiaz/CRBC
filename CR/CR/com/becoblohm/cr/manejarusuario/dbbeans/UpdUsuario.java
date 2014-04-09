/**
 * =============================================================================
 * Proyecto   : CRBECOEPA
 * Paquete    : com.becoblohm.cr.manejarusuario.dbbeans
 * Programa   : UpdUsuario.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 15/10/2003 04:52:05 PM
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
 * proporciona métodos que ejecutan la sentencia SQL en la Caja Registradora
 * que actualiza los datos del usuario indicado, y que devuelven una 
 * referencia a DBModify.
 */

public class UpdUsuario {
	private DBModify modify;
	private DBModify modify1;
	private DBModify modify2;
	private DBModify modify3;

	/**
	 * Constructor para una clase DBModify.
	 */
	public UpdUsuario() {
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
				"UPDATE usuario SET codigobarra = MD5(:codigoBarra), codperfil = :codPerfil, clave = MD5(:clave), nivelauditoria = :nivelAuditoria, nombre = :nombre, puedecambiarclave = :puedeCambiarClave, indicacambiarclave = :indicaCambiarClave, ultimocambioclave = :ultimoCambioClave, tiempovigenciaclave = :tiempoVigenciaClave, regvigente = :regVigente, actualizacion = CURRENT_TIMESTAMP WHERE usuario.numtienda = :numTienda AND usuario.numficha = :numFicha");
			DBParameterMetaData parmMetaData = modify.getParameterMetaData();
			parmMetaData.setParameter(
				1,
				"codigoBarra",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				2,
				"codPerfil",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				3,
				"clave",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				4,
				"nivelAuditoria",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				5,
				"nombre",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				6,
				"puedeCambiarClave",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				7,
				"indicaCambiarClave",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				8,
				"ultimoCambioClave",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.TIMESTAMP,
				java.sql.Timestamp.class);
			parmMetaData.setParameter(
				9,
				"tiempoVigenciaClave",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.SMALLINT,
				Short.class);
			parmMetaData.setParameter(
				10,
				"regVigente",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				11,
				"numTienda",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.SMALLINT,
				Short.class);
			parmMetaData.setParameter(
				12,
				"numFicha",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
		} catch (SQLException ex) {
			//ex.printStackTrace();
		}
		
		//***********************************
		// Modify 1
		//***********************************
		modify1 = new DBModify();
		try {
			modify1.setDriverName(Sesion.getDbClaseLocal());
			modify1.setUrl(Sesion.getDbUrlLocal());
			modify1.setCommand(
				"UPDATE usuario SET codigobarra = MD5(:codigoBarra), codperfil = :codPerfil, clave = MD5(:clave), nivelauditoria = :nivelAuditoria, nombre = :nombre, puedecambiarclave = :puedeCambiarClave, indicacambiarclave = :indicaCambiarClave, ultimocambioclave = :ultimoCambioClave, tiempovigenciaclave = :tiempoVigenciaClave, regvigente = :regVigente, actualizacion = CURRENT_TIMESTAMP WHERE usuario.numtienda = :numTienda AND usuario.numficha = :numFicha");
			DBParameterMetaData parmMetaData1 = modify1.getParameterMetaData();
			parmMetaData1.setParameter(
				1,
				"codigoBarra",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData1.setParameter(
				2,
				"codPerfil",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData1.setParameter(
				3,
				"clave",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData1.setParameter(
				4,
				"nivelAuditoria",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData1.setParameter(
				5,
				"nombre",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData1.setParameter(
				6,
				"puedeCambiarClave",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData1.setParameter(
				7,
				"indicaCambiarClave",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData1.setParameter(
				8,
				"ultimoCambioClave",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.TIMESTAMP,
				java.sql.Timestamp.class);
			parmMetaData1.setParameter(
				9,
				"tiempoVigenciaClave",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.SMALLINT,
				Short.class);
			parmMetaData1.setParameter(
				10,
				"regVigente",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData1.setParameter(
				11,
				"numTienda",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.SMALLINT,
				Short.class);
			parmMetaData1.setParameter(
				12,
				"numFicha",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
		} catch (SQLException ex) {
			//ex.printStackTrace();
		}

		//***********************************
		// Modify 2
		//***********************************		
		modify2 = new DBModify();
		try {
			modify2.setDriverName(Sesion.getDbClaseLocal());
			modify2.setUrl(Sesion.getDbUrlLocal());
			modify2.setCommand(
				"UPDATE usuario SET codigobarra = MD5(:codigoBarra), codperfil = :codPerfil, clave = MD5(:clave), nivelauditoria = :nivelAuditoria, nombre = :nombre, puedecambiarclave = :puedeCambiarClave, indicacambiarclave = :indicaCambiarClave, ultimocambioclave = :ultimoCambioClave, tiempovigenciaclave = :tiempoVigenciaClave, regvigente = :regVigente, actualizacion = CURRENT_TIMESTAMP WHERE usuario.numtienda = :numTienda AND usuario.numficha = :numFicha");
			DBParameterMetaData parmMetaData2 = modify2.getParameterMetaData();
			parmMetaData2.setParameter(
				1,
				"codigoBarra",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData2.setParameter(
				2,
				"codPerfil",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData2.setParameter(
				3,
				"clave",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData2.setParameter(
				4,
				"nivelAuditoria",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData2.setParameter(
				5,
				"nombre",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData2.setParameter(
				6,
				"puedeCambiarClave",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData2.setParameter(
				7,
				"indicaCambiarClave",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData2.setParameter(
				8,
				"ultimoCambioClave",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.TIMESTAMP,
				java.sql.Timestamp.class);
			parmMetaData2.setParameter(
				9,
				"tiempoVigenciaClave",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.SMALLINT,
				Short.class);
			parmMetaData2.setParameter(
				10,
				"regVigente",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData2.setParameter(
				11,
				"numTienda",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.SMALLINT,
				Short.class);
			parmMetaData2.setParameter(
				12,
				"numFicha",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
		} catch (SQLException ex) {
			//ex.printStackTrace();
		}
		
		//***********************************
		// Modify 3
		//***********************************
		modify3 = new DBModify();
		try {
			modify3.setDriverName(Sesion.getDbClaseLocal());
			modify3.setUrl(Sesion.getDbUrlLocal());
			modify3.setCommand(
				"UPDATE usuario SET codigobarra = MD5(:codigoBarra), codperfil = :codPerfil, clave = MD5(:clave), nivelauditoria = :nivelAuditoria, nombre = :nombre, puedecambiarclave = :puedeCambiarClave, indicacambiarclave = :indicaCambiarClave, ultimocambioclave = :ultimoCambioClave, tiempovigenciaclave = :tiempoVigenciaClave, regvigente = :regVigente, actualizacion = CURRENT_TIMESTAMP WHERE usuario.numtienda = :numTienda AND usuario.numficha = :numFicha");
			DBParameterMetaData parmMetaData3 = modify3.getParameterMetaData();
			parmMetaData3.setParameter(
				1,
				"codigoBarra",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData3.setParameter(
				2,
				"codPerfil",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData3.setParameter(
				3,
				"clave",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData3.setParameter(
				4,
				"nivelAuditoria",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData3.setParameter(
				5,
				"nombre",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData3.setParameter(
				6,
				"puedeCambiarClave",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData3.setParameter(
				7,
				"indicaCambiarClave",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData3.setParameter(
				8,
				"ultimoCambioClave",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.TIMESTAMP,
				java.sql.Timestamp.class);
			parmMetaData3.setParameter(
				9,
				"tiempoVigenciaClave",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.SMALLINT,
				Short.class);
			parmMetaData3.setParameter(
				10,
				"regVigente",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData3.setParameter(
				11,
				"numTienda",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.SMALLINT,
				Short.class);
			parmMetaData3.setParameter(
				12,
				"numFicha",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
		} catch (SQLException ex) {
			//ex.printStackTrace();
		}
		
	}

	/**
	 * Ejecuta la sentencia SQL que actualiza los datos
	 * del usuario indicado.
	 */
	public void execute(
		String codigoBarra,
		String codPerfil,
		String clave,
		String nivelAuditoria,
		String nombre,
		String puedeCambiarClave,
		String indicaCambiarClave,
		java.sql.Timestamp ultimoCambioClave,
		Short tiempoVigenciaClave,
		String regVigente,
		Short numTienda,
		String numFicha)
		throws SQLException {
		try {
			modify.setUsername(Sesion.getDbUsuario());
			modify.setPassword(Sesion.getDbClave());
			modify.setParameter("codigoBarra", codigoBarra);
			modify.setParameter("codPerfil", codPerfil);
			modify.setParameter("clave", clave);
			modify.setParameter("nivelAuditoria", nivelAuditoria);
			modify.setParameter("nombre", nombre);
			modify.setParameter("puedeCambiarClave", puedeCambiarClave);
			modify.setParameter("indicaCambiarClave", indicaCambiarClave);
			modify.setParameter("ultimoCambioClave", ultimoCambioClave);
			modify.setParameter("tiempoVigenciaClave", tiempoVigenciaClave);
			modify.setParameter("regVigente", regVigente);
			modify.setParameter("numTienda", numTienda);
			modify.setParameter("numFicha", numFicha);
			modify.execute();
		}

		// Liberar recursos del objeto modify.
		finally {
			modify.close();
		}
	}

	/**
	 * Ejecuta la sentencia SQL que actualiza los datos
	 * del usuario indicado.
	 */
	public void execute1(
		String codigoBarra,
		String codPerfil,
		String clave,
		String nivelAuditoria,
		String nombre,
		String puedeCambiarClave,
		String indicaCambiarClave,
		java.sql.Timestamp ultimoCambioClave,
		Short tiempoVigenciaClave,
		String regVigente,
		Short numTienda,
		String numFicha)
		throws SQLException {
		try {
			modify1.setUsername(Sesion.getDbUsuario());
			modify1.setPassword(Sesion.getDbClave());
			modify1.setParameter("codigoBarra", codigoBarra);
			modify1.setParameter("codPerfil", codPerfil);
			modify1.setParameter("clave", clave);
			modify1.setParameter("nivelAuditoria", nivelAuditoria);
			modify1.setParameter("nombre", nombre);
			modify1.setParameter("puedeCambiarClave", puedeCambiarClave);
			modify1.setParameter("indicaCambiarClave", indicaCambiarClave);
			modify1.setParameter("ultimoCambioClave", ultimoCambioClave);
			modify1.setParameter("tiempoVigenciaClave", tiempoVigenciaClave);
			modify1.setParameter("regVigente", regVigente);
			modify1.setParameter("numTienda", numTienda);
			modify1.setParameter("numFicha", numFicha);
			modify1.execute();
		}

		// Liberar recursos del objeto modify.
		finally {
			modify1.close();
		}
	}

	/**
	 * Ejecuta la sentencia SQL que actualiza los datos
	 * del usuario indicado.
	 */
	public void execute2(
		String codigoBarra,
		String codPerfil,
		String clave,
		String nivelAuditoria,
		String nombre,
		String puedeCambiarClave,
		String indicaCambiarClave,
		java.sql.Timestamp ultimoCambioClave,
		Short tiempoVigenciaClave,
		String regVigente,
		Short numTienda,
		String numFicha)
		throws SQLException {
		try {
			modify2.setUsername(Sesion.getDbUsuario());
			modify2.setPassword(Sesion.getDbClave());
			modify2.setParameter("codigoBarra", codigoBarra);
			modify2.setParameter("codPerfil", codPerfil);
			modify2.setParameter("clave", clave);
			modify2.setParameter("nivelAuditoria", nivelAuditoria);
			modify2.setParameter("nombre", nombre);
			modify2.setParameter("puedeCambiarClave", puedeCambiarClave);
			modify2.setParameter("indicaCambiarClave", indicaCambiarClave);
			modify2.setParameter("ultimoCambioClave", ultimoCambioClave);
			modify2.setParameter("tiempoVigenciaClave", tiempoVigenciaClave);
			modify2.setParameter("regVigente", regVigente);
			modify2.setParameter("numTienda", numTienda);
			modify2.setParameter("numFicha", numFicha);
			modify2.execute();
		}

		// Liberar recursos del objeto modify.
		finally {
			modify2.close();
		}
	}

	/**
	 * Ejecuta la sentencia SQL que actualiza los datos
	 * del usuario indicado.
	 */
	public void execute3(
		String codigoBarra,
		String codPerfil,
		String clave,
		String nivelAuditoria,
		String nombre,
		String puedeCambiarClave,
		String indicaCambiarClave,
		java.sql.Timestamp ultimoCambioClave,
		Short tiempoVigenciaClave,
		String regVigente,
		Short numTienda,
		String numFicha)
		throws SQLException {
		try {
			modify3.setUsername(Sesion.getDbUsuario());
			modify3.setPassword(Sesion.getDbClave());
			modify3.setParameter("codigoBarra", codigoBarra);
			modify3.setParameter("codPerfil", codPerfil);
			modify3.setParameter("clave", clave);
			modify3.setParameter("nivelAuditoria", nivelAuditoria);
			modify3.setParameter("nombre", nombre);
			modify3.setParameter("puedeCambiarClave", puedeCambiarClave);
			modify3.setParameter("indicaCambiarClave", indicaCambiarClave);
			modify3.setParameter("ultimoCambioClave", ultimoCambioClave);
			modify3.setParameter("tiempoVigenciaClave", tiempoVigenciaClave);
			modify3.setParameter("regVigente", regVigente);
			modify3.setParameter("numTienda", numTienda);
			modify3.setParameter("numFicha", numFicha);
			modify3.execute();
		}

		// Liberar recursos del objeto modify.
		finally {
			modify3.close();
		}
	}
	
	/**
	 * Devuelve una referencia a DBModify.
	 */
	public DBModify getDBModify() {
		return modify;
	}
}