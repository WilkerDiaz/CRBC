/**
 * =============================================================================
 * Proyecto   : CRBECOEPA
 * Paquete    : com.becoblohm.cr.manejarusuario.dbbeans
 * Programa   : GetUsuario.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 15/10/2003 04:18:57 PM
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
import com.ibm.db.beans.DBSelect;

/**
 * Descripción: 
 * 		Esta clase establece los valores de la propiedad DBSelect. También 
 * proporciona métodos que ejecutan la sentencia SQL que retorna los datos
 * de la tabla Usuario de la Caja Registradora, devuelven una referencia a 
 * DBSelect y una matriz de objetos que representan las filas del conjunto
 * de resultados.
 */

public class GetUsuario {
	private DBSelect select;

	/**
	 * Constructor para una clase DBSelect.
	 */
	public GetUsuario() {
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
				"SELECT DISTINCT usuario.numtienda, usuario.numficha, usuario.codigobarra, usuario.codperfil, usuario.clave, usuario.nivelauditoria, usuario.nombre, usuario.puedecambiarclave, usuario.indicacambiarclave, usuario.fechacreacion, usuario.ultimocambioclave, usuario.tiempovigenciaclave, usuario.regvigente, usuario.actualizacion FROM usuario");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Ejecuta la sentencia SQL que retorna todos los registros de la
	 * tabla Usuario.
	 */
	public void execute() throws SQLException {
		try {
			select.setUsername(Sesion.getDbUsuario());
			select.setPassword(Sesion.getDbClave());
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
	public GetUsuarioRow[] getRows() {
		GetUsuarioRow[] rows = new GetUsuarioRow[select.getRowCount()];
		for (int i = 0; i <= select.getRowCount() - 1; i++) {
			rows[i] = new GetUsuarioRow(select, i + 1);
		};
		return rows;
	}
}