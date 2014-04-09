package com.becoblohm.cr.sincronizador.dbbeans.caja;
import java.sql.SQLException;

import com.becoblohm.cr.manejadorsesion.Sesion;
import com.ibm.db.beans.DBModify;
import com.ibm.db.beans.DBParameterMetaData;

/**
 * Esta clase establece los valores de la propiedad DBModify. También 
 * proporciona métodos que ejecutan la sentencia SQL 
 * y devuelven una referencia a DBModify.
 * Generated:  21/10/2003 09:58:59 AM
 */

public class AddTienda {
	private DBModify modify;

	/**
	 * Constructor para una clase DBModify.
	 */
	public AddTienda() {
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
				"INSERT INTO tienda ( numtienda, nombre, razonsocial, rif, nit, direccion, codarea, numtelefono, codareafax, numfax, direccionfiscal, codareafiscal, numtelefonofiscal, codareafaxfiscal, numfaxfiscal, publicidadlinea1, publicidadlinea2, publicidadlinea3, publicidadlinea4, publicidadlinea5, monedabase, codregion, limiteentregacaja, tipoimpuestoaplicar, indicadesctoempleado, desctoventaempleado, cambioprecioencaja, fechatienda, utilizarvendedor, desctosacumulativos, dbclase, dburllocal, dburlservidor, dbusuario, dbclave ) VALUES ( :numTienda, :nombre, :razonsocial, :rif, :nit, :direccion, :codArea, :numTelefono, :codAreaFax, :numFax, :direccionFiscal, :codAreaFiscal, :numTelefonoFiscal, :codAreaFaxFiscal, :numFaxFiscal, :publicidadLinea1, :publicidadLinea2, :publicidadLinea3, :publicidadLinea4, :publicidadLinea5, :monedaBase, :codRegion, :limiteEntregaCaja, :tipoImpuestoAplicar, :indicaDesctoEmpleado, :desctoVentaEmpleado, :cambioPrecioEnCaja, :fechaTienda, :utilizarVendedor, :dbClase, :dbUrlLocal, :dbUrlServidor, :dbUsuario, :dbClave )");
			DBParameterMetaData parmMetaData = modify.getParameterMetaData();
			parmMetaData.setParameter(
				1,
				"numTienda",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.SMALLINT,
				Short.class);
			parmMetaData.setParameter(
				2,
				"nombre",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				3,
				"razonsocial",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				4,
				"rif",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				5,
				"nit",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				6,
				"direccion",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				7,
				"codArea",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				8,
				"numTelefono",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				9,
				"codAreaFax",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				10,
				"numFax",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				11,
				"direccionFiscal",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				12,
				"codAreaFiscal",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				13,
				"numTelefonoFiscal",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				14,
				"codAreaFaxFiscal",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				15,
				"numFaxFiscal",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				16,
				"publicidadLinea1",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				17,
				"publicidadLinea2",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				18,
				"publicidadLinea3",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				19,
				"publicidadLinea4",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				20,
				"publicidadLinea5",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				21,
				"monedaBase",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				22,
				"codRegion",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				23,
				"limiteEntregaCaja",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.DECIMAL,
				java.math.BigDecimal.class);
			parmMetaData.setParameter(
				24,
				"tipoImpuestoAplicar",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				25,
				"indicaDesctoEmpleado",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				26,
				"desctoVentaEmpleado",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.DECIMAL,
				java.math.BigDecimal.class);
			parmMetaData.setParameter(
				27,
				"cambioPrecioEnCaja",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				28,
				"fechaTienda",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.DATE,
				java.sql.Date.class);
			parmMetaData.setParameter(
				29,
				"utilizarVendedor",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				30,
				"desctosacumulativos",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				31,
				"dbClase",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				32,
				"dbUrlLocal",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				33,
				"dbUrlServidor",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				34,
				"dbUsuario",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				35,
				"dbClave",
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
		String nombre,
		String razonsocial,
		String rif,
		String nit,
		String direccion,
		String codArea,
		String numTelefono,
		String codAreaFax,
		String numFax,
		String direccionFiscal,
		String codAreaFiscal,
		String numTelefonoFiscal,
		String codAreaFaxFiscal,
		String numFaxFiscal,
		String publicidadLinea1,
		String publicidadLinea2,
		String publicidadLinea3,
		String publicidadLinea4,
		String publicidadLinea5,
		String monedaBase,
		String codRegion,
		java.math.BigDecimal limiteEntregaCaja,
		String tipoImpuestoAplicar,
		String indicaDesctoEmpleado,
		java.math.BigDecimal desctoVentaEmpleado,
		String cambioPrecioEnCaja,
		java.sql.Date fechaTienda,
		String utilizarVendedor,
		String desctosacumulativos,
		String dbClase,
		String dbUrlLocal,
		String dbUrlServidor,
		String dbUsuario,
		String dbClave)
		throws SQLException {
		try {
			modify.setUsername(Sesion.getDbUsuario());
			modify.setPassword(Sesion.getDbClave());
			modify.setParameter("numTienda", numTienda);
			modify.setParameter("nombre", nombre);
			modify.setParameter("razonsocial", razonsocial);
			modify.setParameter("rif", rif);
			modify.setParameter("nit", nit);
			modify.setParameter("direccion", direccion);
			modify.setParameter("codArea", codArea);
			modify.setParameter("numTelefono", numTelefono);
			modify.setParameter("codAreaFax", codAreaFax);
			modify.setParameter("numFax", numFax);
			modify.setParameter("direccionFiscal", direccionFiscal);
			modify.setParameter("codAreaFiscal", codAreaFiscal);
			modify.setParameter("numTelefonoFiscal", numTelefonoFiscal);
			modify.setParameter("codAreaFaxFiscal", codAreaFaxFiscal);
			modify.setParameter("numFaxFiscal", numFaxFiscal);
			modify.setParameter("publicidadLinea1", publicidadLinea1);
			modify.setParameter("publicidadLinea2", publicidadLinea2);
			modify.setParameter("publicidadLinea3", publicidadLinea3);
			modify.setParameter("publicidadLinea4", publicidadLinea4);
			modify.setParameter("publicidadLinea5", publicidadLinea5);
			modify.setParameter("monedaBase", monedaBase);
			modify.setParameter("codRegion", codRegion);
			modify.setParameter("limiteEntregaCaja", limiteEntregaCaja);
			modify.setParameter("tipoImpuestoAplicar", tipoImpuestoAplicar);
			modify.setParameter("indicaDesctoEmpleado", indicaDesctoEmpleado);
			modify.setParameter("desctoVentaEmpleado", desctoVentaEmpleado);
			modify.setParameter("cambioPrecioEnCaja", cambioPrecioEnCaja);
			modify.setParameter("fechaTienda", fechaTienda);
			modify.setParameter("utilizarVendedor", utilizarVendedor);
			modify.setParameter("desctosacumulativos", desctosacumulativos);
			modify.setParameter("dbClase", dbClase);
			modify.setParameter("dbUrlLocal", dbUrlLocal);
			modify.setParameter("dbUrlServidor", dbUrlServidor);
			modify.setParameter("dbUsuario", dbUsuario);
			modify.setParameter("dbClave", dbClave);
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