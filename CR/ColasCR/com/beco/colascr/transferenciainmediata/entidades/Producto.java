/**
 * =============================================================================
 * Proyecto   : TransferenciaInmediataProdsCR
 * Paquete    : com.beco.colascr.transferenciainmediata.entidades
 * Programa   : Producto.java
 * Creado por : gmartinelli
 * Creado en  : 28-jun-05 10:15:32
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versi�n     : 1.1
 * Fecha       : 28-jun-05 10:15:32
 * Analista    : gmartinelli
 * Descripci�n : Implementaci�n inicial.
 * =============================================================================
 */
package com.beco.colascr.transferenciainmediata.entidades; 


/**
 * Descripci�n:
 * 
 */

public class Producto {

	private String codproducto;
	private String descripcionCorta;
	private String descripcionLarga;
	private int codUnidadVenta;
	private String refProveedor;
	private String marca;
	private String modelo;
	private String codDepartamento;
	private String codLineaSecc;
	private double costoLista;
	private double precioRegular;
	private String codImpuesto;
	private int cantVtaEmpaque;
	private double dsctoVtaEmpaque;
	private String indDsctoEmpleado;
	private String indicaDespachar;
	private String estadoProducto;
//	private String actualizacion;

	/**
	 * Constructor para Producto.java
	 *
	 * 
	 */
	public Producto(String codprod, String descCorta, String descLarga, int codUniVen, String refPro, String marca,
					String modelo, String codDep, String codLin, double costList, double preReg, String codImp,
					int cantVtaEmpaque, double dsctoVtaEmp, String indDsctoEmple, String indDespachar,
					String edoProd/*, Timestamp act*/) {
		this.codproducto = "'" + codprod.trim() + "'";
		this.descripcionCorta = "'" + descCorta.trim() + "'";
		this.descripcionLarga = (descLarga==null) ? "null" : "'" + descLarga.trim() + "'";
		this.codUnidadVenta = codUniVen;
		this.refProveedor = (refPro==null) ? "null" : "'" + refPro.trim() + "'";
		this.marca = (marca==null) ? "null" : "'" + marca.trim() + "'";
		this.modelo = (modelo==null) ? "null" : "'" + modelo.trim() + "'";
		this.codDepartamento = "'" + codDep.trim() + "'";
		this.codLineaSecc = "'" + codLin.trim() + "'";
		this.costoLista = costList;
		this.precioRegular = preReg;
		this.codImpuesto = "'" + codImp.trim() + "'";
		this.cantVtaEmpaque = cantVtaEmpaque;
		this.dsctoVtaEmpaque = dsctoVtaEmp;
		this.indDsctoEmpleado = "'" + indDsctoEmple.trim() + "'";
		this.indicaDespachar = (indDespachar==null) ? "null" : "'" + indDespachar.trim() + "'";
		this.estadoProducto = "'" + edoProd.trim() + "'";
//		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//		this.actualizacion = df.format(act);
	}

	/**
	 * M�todo getActualizacion
	 * 
	 * @return
	 * String
	 */
/*	public String getActualizacion() {
		return actualizacion;
	}
*/
	/**
	 * M�todo getCantVtaEmpaque
	 * 
	 * @return
	 * int
	 */
	public int getCantVtaEmpaque() {
		return cantVtaEmpaque;
	}

	/**
	 * M�todo getCodDepartamento
	 * 
	 * @return
	 * String
	 */
	public String getCodDepartamento() {
		return codDepartamento;
	}

	/**
	 * M�todo getCodImpuesto
	 * 
	 * @return
	 * String
	 */
	public String getCodImpuesto() {
		return codImpuesto;
	}

	/**
	 * M�todo getCodLineaSecc
	 * 
	 * @return
	 * String
	 */
	public String getCodLineaSecc() {
		return codLineaSecc;
	}

	/**
	 * M�todo getCodproducto
	 * 
	 * @return
	 * String
	 */
	public String getCodproducto() {
		return codproducto;
	}

	/**
	 * M�todo getCodUnidadVenta
	 * 
	 * @return
	 * int
	 */
	public int getCodUnidadVenta() {
		return codUnidadVenta;
	}

	/**
	 * M�todo getCostoLista
	 * 
	 * @return
	 * double
	 */
	public double getCostoLista() {
		return costoLista;
	}

	/**
	 * M�todo getDescripcionCorta
	 * 
	 * @return
	 * String
	 */
	public String getDescripcionCorta() {
		return descripcionCorta;
	}

	/**
	 * M�todo getDescripcionLarga
	 * 
	 * @return
	 * String
	 */
	public String getDescripcionLarga() {
		return descripcionLarga;
	}

	/**
	 * M�todo getDsctoVtaEmpaque
	 * 
	 * @return
	 * double
	 */
	public double getDsctoVtaEmpaque() {
		return dsctoVtaEmpaque;
	}

	/**
	 * M�todo getEstadoProducto
	 * 
	 * @return
	 * String
	 */
	public String getEstadoProducto() {
		return estadoProducto;
	}

	/**
	 * M�todo getIndDsctoEmpleado
	 * 
	 * @return
	 * String
	 */
	public String getIndDsctoEmpleado() {
		return indDsctoEmpleado;
	}

	/**
	 * M�todo getIndicaDespachar
	 * 
	 * @return
	 * String
	 */
	public String getIndicaDespachar() {
		return indicaDespachar;
	}

	/**
	 * M�todo getMarca
	 * 
	 * @return
	 * String
	 */
	public String getMarca() {
		return marca;
	}

	/**
	 * M�todo getModelo
	 * 
	 * @return
	 * String
	 */
	public String getModelo() {
		return modelo;
	}

	/**
	 * M�todo getPrecioRegular
	 * 
	 * @return
	 * double
	 */
	public double getPrecioRegular() {
		return precioRegular;
	}

	/**
	 * M�todo getRefProveedor
	 * 
	 * @return
	 * String
	 */
	public String getRefProveedor() {
		return refProveedor;
	}

}
