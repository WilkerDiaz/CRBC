package com.becoblohm.cr.sincronizador.dbbeans.caja;
import java.sql.SQLException;

import com.ibm.db.beans.DBModify;
import com.ibm.db.beans.DBParameterMetaData;

/**
 * Esta clase establece los valores de la propiedad DBModify. También 
 * proporciona métodos que ejecutan la sentencia SQL 
 * y devuelven una referencia a DBModify.
 * Generated:  10/03/2004 07:52:42 PM
 */

public class AddProducto {
	private DBModify modify;

	/**
	 * Constructor para una clase DBModify.
	 */
	public AddProducto() {
		super();
		initializer();
	}

	/**
	 * Crea una instancia DBModify e inicializa sus propiedades.
	 */
	protected void initializer() {
		modify = new DBModify();
		try {
			modify.setDriverName("com.mysql.jdbc.Driver");
			modify.setUrl("jdbc:mysql://localhost/CR");
			modify.setCommand(
				"INSERT INTO producto ( codproducto, descripcioncorta, descripcionlarga, codunidadventa, referenciaproveedor, marca, modelo, coddepartamento, codlineaseccion, costolista, precioregular, codimpuesto, cantidadventaempaque, desctoventaempaque, indicadesctoempleado, estadoproducto, actualizacion ) VALUES ( :codProducto, :descripcionCorta, :descripcionLarga, :codUnidadVenta, :referenciaProveedor, :marca, :modelo, :codDepartamento, :codLineaSeccion, :costoLista, :precioRegular, :codImpuesto, :cantidadVentaEmpaque, :desctoVentaEmpaque, :indicaDesctoEmpleado, :estadoProducto, :actualizacion )");
			DBParameterMetaData parmMetaData = modify.getParameterMetaData();
			parmMetaData.setParameter(
				1,
				"codProducto",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				2,
				"descripcionCorta",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				3,
				"descripcionLarga",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				4,
				"codUnidadVenta",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.SMALLINT,
				Short.class);
			parmMetaData.setParameter(
				5,
				"referenciaProveedor",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				6,
				"marca",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				7,
				"modelo",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.VARCHAR,
				String.class);
			parmMetaData.setParameter(
				8,
				"codDepartamento",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				9,
				"codLineaSeccion",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				10,
				"costoLista",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.DECIMAL,
				java.math.BigDecimal.class);
			parmMetaData.setParameter(
				11,
				"precioRegular",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.DECIMAL,
				java.math.BigDecimal.class);
			parmMetaData.setParameter(
				12,
				"codImpuesto",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				13,
				"cantidadVentaEmpaque",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.INTEGER,
				Integer.class);
			parmMetaData.setParameter(
				14,
				"desctoVentaEmpaque",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.DECIMAL,
				java.math.BigDecimal.class);
			parmMetaData.setParameter(
				15,
				"indicaDesctoEmpleado",
				java.sql.DatabaseMetaData.procedureColumnIn,
				java.sql.Types.CHAR,
				String.class);
			parmMetaData.setParameter(
				16,
				"estadoProducto",
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
		String codProducto,
		String descripcionCorta,
		String descripcionLarga,
		Short codUnidadVenta,
		String referenciaProveedor,
		String marca,
		String modelo,
		String codDepartamento,
		String codLineaSeccion,
		java.math.BigDecimal costoLista,
		java.math.BigDecimal precioRegular,
		String codImpuesto,
		Integer cantidadVentaEmpaque,
		java.math.BigDecimal desctoVentaEmpaque,
		String indicaDesctoEmpleado,
		String estadoProducto,
		java.sql.Timestamp actualizacion)
		throws SQLException {
		try {
			modify.setUsername("INTRANET");
			modify.setPassword("INTRANET");
			modify.setParameter("codProducto", codProducto);
			modify.setParameter("descripcionCorta", descripcionCorta);
			modify.setParameter("descripcionLarga", descripcionLarga);
			modify.setParameter("codUnidadVenta", codUnidadVenta);
			modify.setParameter("referenciaProveedor", referenciaProveedor);
			modify.setParameter("marca", marca);
			modify.setParameter("modelo", modelo);
			modify.setParameter("codDepartamento", codDepartamento);
			modify.setParameter("codLineaSeccion", codLineaSeccion);
			modify.setParameter("costoLista", costoLista);
			modify.setParameter("precioRegular", precioRegular);
			modify.setParameter("codImpuesto", codImpuesto);
			modify.setParameter("cantidadVentaEmpaque", cantidadVentaEmpaque);
			modify.setParameter("desctoVentaEmpaque", desctoVentaEmpaque);
			modify.setParameter("indicaDesctoEmpleado", indicaDesctoEmpleado);
			modify.setParameter("estadoProducto", estadoProducto);
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