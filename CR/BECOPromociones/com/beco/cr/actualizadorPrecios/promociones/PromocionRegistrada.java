package com.beco.cr.actualizadorPrecios.promociones;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.mediadoresbd.Conexiones;

public class PromocionRegistrada {
	private String nombre;
	private int codigo;
	private String codProducto;
	private float cantidad;
	
	/**
	 * Producto complementario que regala mercadeo
	 * @param nombre
	 * @param codigo
	 * @param codProducto
	 */
	public PromocionRegistrada(String nombre, int codigo, String codProducto, float cantidad) {
		super();
		this.nombre = nombre;
		this.codigo = codigo;
		this.codProducto = codProducto;
		this.cantidad = cantidad;
	}
	/**
	 * @return el codigo
	 */
	public int getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo el codigo a establecer
	 */
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return el nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre el nombre a establecer
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return el codProducto
	 */
	public String getCodProducto() {
		return codProducto;
	}
	/**
	 * @param codProducto el codProducto a establecer
	 */
	public void setCodProducto(String codProducto) {
		this.codProducto = codProducto;
	}
	public float getCantidad() {
		return cantidad;
	}
	public void setCantidad(float cantidad) {
		this.cantidad = cantidad;
	}
	
	/**
	 * Obtiene las promociones registradas en una determinada transacción
	 * @param codTienda
	 * @param fechaTrans
	 * @param numCaja
	 * @param numTransaccion
	 * @param local indica si la consulta se realiza en la caja o en el servidor
	 * @return
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<PromocionRegistrada> consultarPromocionesPorVenta(
			int codTienda, String fechaTrans, int numCaja, int numTransaccion, 
			boolean local){
		Vector<PromocionRegistrada> promociones = new Vector<PromocionRegistrada>();
		ResultSet result = null;
		String sentenciaSQL = "select * from promocionregistrada dr " +
				" where dr.numTienda= " + codTienda + " and " +
						" dr.fecha = '" + fechaTrans + "' and " +
						" dr.numCaja = "+numCaja+" and " +
						" dr.numtransacion = "+ numTransaccion+" order by codigo";
		try {
			result = Conexiones.realizarConsulta(sentenciaSQL,local);
			result.beforeFirst();
			while (result.next()) {
				PromocionRegistrada pr = new PromocionRegistrada("",
						result.getInt("codigo"),
						result.getString("codigoProducto"),
						result.getFloat("cantproducto"));
				promociones.addElement(pr);
			}
			
		} catch (SQLException e2) {
			//Nada, puede que la tabla no exista porque no se tiene la extension de promociones
		} catch (BaseDeDatosExcepcion e) {
			e.printStackTrace();
		} catch (ConexionExcepcion e) {
			e.printStackTrace();
		} finally {
			try {
				result.close();
			} catch (SQLException e) {
				
			}
			result = null;
		}
		return promociones;
	}
}
