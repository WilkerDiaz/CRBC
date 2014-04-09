/**
 * =============================================================================
 * Proyecto   : CRBECOEPA
 * Paquete    : com.becoblohm.cr.manejarusuario.dbbeans
 * Programa   : GetUsuarioValido.java
 * Creado por : Programador3 - Alexis Gu�dez L�pez
 * Creado en  : 21/10/2003 10:51:11 AM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versi�n     : 
 * Fecha       : 
 * Analista    : 
 * Descripci�n : 
 * =============================================================================
 */
package com.becoblohm.cr.manejarusuario.dbbeans;

import java.sql.SQLException;

import com.becoblohm.cr.manejadorsesion.Sesion;
import com.ibm.db.beans.DBParameterMetaData;
import com.ibm.db.beans.DBSelect;

/**
 * Descripci�n: 
 * 		Esta clase establece los valores de la propiedad DBSelect.
 * Tambi�n proporciona m�todos que ejecutan la sentencia SQL que selecciona
 * los datos de un usuario espec�fico en la data de la Caja Registradora, 
 * validado seg�n su clave secreta y devuelven una referencia a DBSelect 
 * y una matriz de objetos que representan las filas del conjunto de resultados.
 */

public class GetUsuarioValido {
	private DBSelect select;

	/**
	 * Constructor para una clase DBSelect.
	 */
	public GetUsuarioValido() {
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
				//"SELECT DISTINCT * FROM usuario WHERE usuario.numtienda = :numTienda AND usuario.numficha = :numFicha AND usuario.clave = MD5(:clave)");
			"SELECT DISTINCT * FROM usuario WHERE usuario.numtienda = :numTienda AND usuario.numficha = :numFicha AND usuario.clave = :clave");
			DBParameterMetaData parmMetaData = select.getParameterMetaData();
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
				"clave",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * M�todo execute.
	 * 		Ejecuta la sentencia SQL que selecciona los datos del usuario
	 * solicitado validandolo seg�n la clave secreta indicada.
	 * 
	 * @param numTienda - N�mero de la Tienda a la que pertenece el usuario.
	 * @param numFicha - N�mero de ficha o c�digo del usuario (caracter de 8).
	 * @param clave - Calve secreta del usuario (caracter de 8)
	 * @throws SQLException - Excepci�n arrojada en caso de fallar acceso a
	 * 		base de datos
	 */
	public void execute(Short numTienda, String numFicha, String clave)
		throws SQLException {
		try {
			select.setUsername(Sesion.getDbUsuario());
			select.setPassword(Sesion.getDbClave());
			select.setParameter("numTienda", numTienda);
			select.setParameter("numFicha", numFicha);
			select.setParameter("clave", clave);
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
	public GetUsuarioValidoRow[] getRows() {
		GetUsuarioValidoRow[] rows =
			new GetUsuarioValidoRow[select.getRowCount()];
		for (int i = 0; i <= select.getRowCount() - 1; i++) {
			rows[i] = new GetUsuarioValidoRow(select, i + 1);
		};
		return rows;
	}
}