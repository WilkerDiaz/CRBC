/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.manejarservicio
 * Programa   : OperacionLR.java
 * Creado por : rabreu
 * Creado en  : 15/08/2006
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
package com.becoblohm.cr.manejarservicio;

import java.io.Serializable;
import java.util.Date;

/**
 * @author rabreu
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class OperacionLR implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	private String codProd,descripcionProducto,nomCliente,dedicatoria;
	private char tipoOper;
	private int cantidad;
	private double montoBase;
	private double montoImpuesto;
	private Date fecha;
	private int numTransaccion;
	private int numOperacion;
	private int numTienda;
	private String codCajero;
	
	public static char ABONO_PARCIAL = 'A';
	public static char ABONO_LISTA = 'L';
	public static char VENTA = 'V';
	public static char MODIFICACION = 'M';
	public static char ABONO_TOTAL = 'T';
	
	public OperacionLR(int numOperacion,int numTransaccion,String codProd,String descripcion,String nomCliente,double montoBase,
				double montoImpuesto,int cantidad,Date fecha,String dedicatoria,char tipoOper,int numTienda, String codCajero){
		this.numTransaccion = numTransaccion;
		this.numOperacion = numOperacion;
		this.codProd = codProd;
		this.descripcionProducto = descripcion;
		this.tipoOper = tipoOper;
		this.cantidad = cantidad;
		this.montoBase = montoBase;
		this.montoImpuesto = montoImpuesto;
		this.nomCliente = nomCliente;
		this.fecha = fecha;
		this.dedicatoria = dedicatoria;
		this.numTienda = numTienda;
		this.codCajero = codCajero;
	}
	
	/**
	 * @return
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * @return
	 */
	public String getCodProducto() {
		return codProd;
	}

	/**
	 * @return
	 */
	public String getDedicatoria() {
		return dedicatoria;
	}

	/**
	 * @return
	 */
	public String getDescripcionProducto() {
		return descripcionProducto;
	}

	/**
	 * @return
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @return
	 */
	public double getMontoBase() {
		return montoBase;
	}

	/**
	 * @return
	 */
	public String getNomCliente() {
		return nomCliente;
	}

	/**
	 * @return
	 */
	public char getTipoOper() {
		return tipoOper;
	}

	/**
	 * @return Número de la transacción asociada a la venta
	 */
	public int getNumTransaccion() {
		return numTransaccion;
	}

	/**
	 * @return Numero de operación realizada en la Lista de Regalos 
	 */
	public int getNumOperacion() {
		return numOperacion;
	}

	/**
	 * @return
	 */
	public int getNumTienda() {
		return numTienda;
	}
	
	/**
	 * @return
	 */
	public String getCodCajero() {
		return codCajero;
	}

	/**
	 * @return
	 */
	public double getMontoImpuesto() {
		return montoImpuesto;
	}
	/* (no Javadoc)
	 * @see java.lang.Object#clone()
	 */
	protected Object clone() throws CloneNotSupportedException {
		OperacionLR obj=null;
		try {
			obj=(OperacionLR)super.clone();
		} catch(CloneNotSupportedException ex){}
		return obj;
	}

}
