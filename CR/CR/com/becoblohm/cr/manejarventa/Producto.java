/**
 * =============================================================================
 * Proyecto   : CajaRegistradora
 * Paquete    : com.becoblohm.producto
 * Programa   : Producto.java
 * Creado por : irojas
 * Creado en  : 06-oct-03 14:00:59
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.2
 * Fecha       : 08/07/2008 09:20 AM
 * Analista    : jgraterol
 * Descripción : Agregado el campo "tipoPromocionAplicada" para indicar
 * 				en la ejecución del algoritmo que promocion fue aplicada sobre el 
 * 				producto
 * =============================================================================
 * Versión     : 1.2
 * Fecha       : 02/07/2008 09:20 AM
 * Analista    : jgraterol
 * Descripción : Implementado el metodo clone
 * =============================================================================
 * Versión     : 1.1.1
 * Fecha       : 02/03/2004 09:20 AM
 * Analista    : gmartinelli
 * Descripción : Agregados atributos para el manejo de las unidades de venta.
 * 				Modificado contructor de Producto para que maneje los atributos de
 * 				las unidades de venta para chequear si permiten o no cantidades
 * 				fraccionadas.
 * =============================================================================
 * Versión     : 1.1 (según CVS antes del cambio) 
 * Fecha       : 10/02/2004 09:28 AM
 * Analista    : IROJAS
 * Descripción : Se modificaron las siguientes líneas
 *					Línea 32: Cambiado tipo de atributo codProducto de la clase 
 *							  de Long a String
 *					Línea 76: Constructor de la clase: Cambiado parámetro codProd 
 *							  de Long a String
 *					Línea 103: Método getCodProducto: cambiado tipo de retorno de 
 *							   Long a String
 * =============================================================================
 */
package com.becoblohm.cr.manejarventa;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

/** 
 * Descripción: 
 * 		Clase que representa los productos registrados en la Base de datos.
 * Posee los métodos para manipular sus atributos y para obtener los valores de los mismos.
 */
public class Producto implements Serializable {
	
	private String codProducto;
	private String descripcionCorta;
	private String descripcionLarga;
	
	// Atributos para el manejo de las unidades de venta
	private int codUnidadVenta;
	private String descUnidadVenta;
	private String abreviadoUnidadVenta;
	private boolean indicaFraccion;
	
	private String referenciaProveedor;
	private String marca;
	private String modelo;
	private String codDepartamento;
	private String lineaSeccion;
	private double costoLista;
	private double precioRegular;
	private float cantidadVentaEmpaque;
	private double desctoVentaEmpaque;
	private boolean indicaDesctoEmpleado;
	private double desctoVentaEmpleado;
	private char estado;
	private Date fechaActualizacion;
	private Time horaActualizacion;
	private Impuesto impuesto;
	private Vector<Promocion> promociones;
	private String tipoEntrega;
	
	//Actualizacion BECO: 8/07/2008 
	private char tipoPromocionAplicada;
	private int seccion=0;
	private String categoria="";

	/**
	 * Constructor de la clase Producto que inicializa los valores con los valores por defecto.
	 * @param codProd Long que indica el código del producto.
	 */
	public Producto(String codProd) {
		this.codProducto = codProd;
		this.descripcionCorta = "";
		this.descripcionLarga = "";
		this.codUnidadVenta = 0;
		this.descUnidadVenta = "";
		this.abreviadoUnidadVenta = "";
		this.indicaFraccion = false;
		this.referenciaProveedor = "";
		this.marca = "";
		this.modelo = "";
		this.codDepartamento = "";
		this.lineaSeccion = "";
		this.costoLista = 0;
		this.precioRegular = 0;
		this.cantidadVentaEmpaque = 0;
		this.desctoVentaEmpaque = 0;
		this.indicaDesctoEmpleado = false;
		this.desctoVentaEmpleado = 0;
		this.estado = 'A';
		this.fechaActualizacion = null;
		this.horaActualizacion = null;
		this.impuesto = null;
		this.promociones = new Vector<Promocion>();
		this.tipoEntrega = "";
	}

	/**
	 * Constructor de la clase Producto que inicializa los valores de la misma por los dados como parámetros.
	 * @param codProd Long que indica el código del producto.
	 * @param descCorta String que indica la descripción corta del producto.
	 * @param descLarga String que indica la descripción larga del producto.
	 * @param codUnidVta Entero que indica el código de la unidad de venta.
	 * @param descUnidVta String que representa la descripcion de la unidad de venta.
	 * @param abrevUnidVta String que contiene el abreviado de la unidad de venta.
	 * @param indFracc booleano que indica si el producto admite o no cantidades fraccionadas.
	 * @param refProv String que indica la referencia del proveedor.
	 * @param marca String que indica la marca del producto.
	 * @param mod String que indica el modelo del producto.
	 * @param codDep String que indica el código del departamento al que pertenece el producto.
	 * @param linSecc String que indica la linea/seccion a la que pertenece el producto.
	 * @param costoLista 
	 * @param precio Double que indica el precio regular del producto.
	 * @param cantidadEmpaque Entero que indica la cantidad de productos con los que se forma un Empaque.
	 * @param desctoEmpaque Double que indica el descuento a aplicar en caso de que se forme un Empaque.
	 * @param indicaDctoEmp Boolean que indica si al producto se le puede aplicar un descuento a COLABORADOR.
	 * @param desctoEmpleado Double que indica el descuento a aplicar en caso de que un COLABORADOR lo adquiera y se le pueda aplicar este descuento.
	 * @param edo Char que indica el estado del producto.
	 * @param fechaAct Date que indica la fecha de actualización del producto en la Base de datos.
	 * @param horaAct Time que indica la hora de actualización del producto en la Base de datos.
	 * @param imp Variable de tipo Impuesto que indica el impuesto que aplica al producto.
	 * @param proms Vector que contiene las promociones que se le aplican al producto.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Producto(String codProd, String descCorta, String descLarga, 
			int codUnidVta, String descUnidVta, String abrevUnidVta, 
			boolean indFracc, String refProv, String marca, String mod, 
			String codDep, String linSecc, double costoLista, double precio, 
			float cantidadEmpaque, double desctoEmpaque, boolean indicaDctoEmp, 
			double desctoEmpleado, char edo, Date fechaAct, Time horaAct, 
			Impuesto imp, Vector<Promocion> proms, String tipoEnt) {
		this.codProducto = codProd;
		this.descripcionCorta = descCorta;
		this.descripcionLarga = descLarga;
		this.codUnidadVenta = codUnidVta;
		this.descUnidadVenta = descUnidVta;
		this.abreviadoUnidadVenta = abrevUnidVta;
		this.indicaFraccion = indFracc;
		this.referenciaProveedor = refProv;
		this.marca = marca;
		this.modelo = mod;
		this.codDepartamento = codDep;
		this.lineaSeccion = linSecc;
		this.costoLista = costoLista;
		this.precioRegular = precio;
		this.cantidadVentaEmpaque = cantidadEmpaque;
		this.desctoVentaEmpaque = desctoEmpaque;
		this.indicaDesctoEmpleado = indicaDctoEmp;
		this.desctoVentaEmpleado = desctoEmpleado;
		this.estado = edo;
		this.fechaActualizacion = fechaAct;
		this.horaActualizacion = horaAct;
		this.impuesto = imp;
		this.promociones = proms;
		this.tipoEntrega = tipoEnt;
		//this.promociones = new Vector<Promocion>();
	}

	/**
	 * Permite obtener el código del producto representado por este objeto.
	 * @return long - long que indica el código del producto.
	 */
	public String getCodProducto() {
		return codProducto;
	}

	/**
	 * Permite obtener el Vector de promociones que aplican al producto.
	 * @return Vector - Vector que contiene las promociones.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Promocion> getPromociones() {
		return promociones;
	}

	/**
	 * Permite obtener el precio regular del producto.
	 * @return double - Double que indica el precio regular.
	 */
	
	public double getPrecioRegular() {
		return precioRegular;
	}

	/**
	 * Permite obtener el Impuesto que se le aplica al producto.
	 * @return Impuesto - variable de tipo impuesto que representa el impuesto del producto.
	 */
	public Impuesto getImpuesto() {
		return impuesto;
	}

	/**
	 * Permite obtener la cantidad de productos que forman un empaque.
	 * @return int - Entero que indica la cantidad del empaque.
	 */
	public float getCantidadVentaEmpaque() {
		return cantidadVentaEmpaque;
	}

	/**
	 * Permite obtener el descuento que se aplica al empaque. 
	 * @return double - Double que indica el descuento a empaque.
	 */
	public double getDesctoVentaEmpaque() {
		return desctoVentaEmpaque;
	}

	/**
	 * Permite obtener la descripcion corta del producto.
	 * @return String - String que contiene la descripcion corta del producto.
	 */
	public String getDescripcionCorta() {
		return descripcionCorta;
	}

	/**
	 * Obtiene el Costo lista del producto.
	 * @return double - El costo lista.
	 */
	public double getCostoLista() {
		return costoLista;
	}

	/**
	 * Returns the indicaDesctoEmpleado.
	 * @return boolean
	 */
	public boolean isIndicaDesctoEmpleado() {
		return indicaDesctoEmpleado;
	}

	/**
	 * Retorna si indicaFraccion.
	 * @return boolean
	 */
	public boolean isIndicaFraccion() {
		return indicaFraccion;
	}
	/**
	 * Método getAbreviadoUnidadVenta
	 * 
	 * @return
	 * String
	 */
	public String getAbreviadoUnidadVenta() {
		return abreviadoUnidadVenta;
	}

	/**
	 * Método getTipoEntrega
	 * 
	 * @return
	 * String
	 */
	public String getTipoEntrega() {
		return tipoEntrega;
	}

	/**
	 * Método setPrecioRegular
	 * 
	 * @param d
	 * void
	 */
	public void setPrecioRegular(double d) {
		precioRegular = d;
	}

	/**
	 * Método getDescripcionLarga
	 * 
	 * @return String
	 */
	public String getDescripcionLarga() {
		return descripcionLarga;
	}

	/**
	 * Método setDescripcionLarga
	 * 
	 * @param string
	 */
	public void setDescripcionLarga(String string) {
		descripcionLarga = string;
	}
	
	public boolean isPermiteRebaja() {
		return !codDepartamento.equals("90");
	}
	
	public boolean equals(Producto prod) {
		return prod.getCodProducto().equals(codProducto);
	}

	/**
	 * @return
	 */
	public String getCodDepartamento() {
		return codDepartamento;
	}

	public String getLineaSeccion() {
		return lineaSeccion;
	}

	public void setLineaSeccion(String lineaSeccion) {
		this.lineaSeccion = lineaSeccion;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getReferenciaProveedor() {
		return referenciaProveedor;
	}

	public void setReferenciaProveedor(String referenciaProveedor) {
		this.referenciaProveedor = referenciaProveedor;
	}
	
	/**
	 * Implementa el metodo clone de object
	 * OJO: NO esta clonando el vector de promociones porque
	 * por ahora no es necesario
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Object clone(){
		/*Vector promocionesClonadas = new Vector();
		for(int i=0;i<promociones.size();i++){
			promocionesClonadas.addElement(((Promocion)promociones.elementAt(i)).clone());
		}*/
		Producto p = null;
		if (this.impuesto!=null){
			p = new Producto(this.codProducto, 
					this.descripcionCorta, 
					this.descripcionLarga, 
					this.codUnidadVenta, 
					this.descUnidadVenta, 
					this.abreviadoUnidadVenta,
					this.indicaFraccion, 
					this.referenciaProveedor, 
					this.marca, 
					this.modelo, 
					this.codDepartamento, 
					this.lineaSeccion,
					this.costoLista, 
					this.precioRegular, 
					this.cantidadVentaEmpaque, 
					this.desctoVentaEmpaque, 
					this.indicaDesctoEmpleado, 
					this.desctoVentaEmpleado, 
					this.estado, 
					this.fechaActualizacion, 
					this.horaActualizacion, 
					(Impuesto)this.impuesto.clone(),
					new Vector<Promocion>(), 
					this.tipoEntrega);
			p.setTipoPromocionAplicada(this.tipoPromocionAplicada);
			p.setCategoria(this.categoria);
			p.setSeccion(this.seccion);
		}else{
			p = new Producto(this.codProducto, 
					this.descripcionCorta, 
					this.descripcionLarga, 
					this.codUnidadVenta, 
					this.descUnidadVenta, 
					this.abreviadoUnidadVenta,
					this.indicaFraccion, 
					this.referenciaProveedor, 
					this.marca, 
					this.modelo, 
					this.codDepartamento, 
					this.lineaSeccion,
					this.costoLista, 
					this.precioRegular, 
					this.cantidadVentaEmpaque, 
					this.desctoVentaEmpaque, 
					this.indicaDesctoEmpleado, 
					this.desctoVentaEmpleado, 
					this.estado, 
					this.fechaActualizacion, 
					this.horaActualizacion, 
					null,
					new Vector<Promocion>(), 
					this.tipoEntrega);
			p.setTipoPromocionAplicada(this.tipoPromocionAplicada);
			p.setCategoria(this.categoria);
			p.setSeccion(this.seccion);
		}
		return p;
	}

	/**
	 * @return el tipoPromocionAplicada
	 */
	public char getTipoPromocionAplicada() {
		return tipoPromocionAplicada;
	}

	/**
	 * @param tipoPromocionAplicada el tipoPromocionAplicada a establecer
	 */
	public void setTipoPromocionAplicada(char tipoPromocionAplicada) {
		this.tipoPromocionAplicada = tipoPromocionAplicada;
	}
	
	/**
	 * Obtiene la promocion de este producto que corresponde con el codigo indicado
	 * @param codPromocion
	 * @return
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Iterator'
	* Fecha: agosto 2011
	*/
	public Promocion getPromocionPorCodigo(int codPromocion){
		Iterator<Promocion> iteraPromo = promociones.iterator();
		while(iteraPromo.hasNext()){
			Promocion p = (Promocion)iteraPromo.next();
			if(p.getCodPromocion() == codPromocion)
				return p;
		}
		return null;
	}

	public int getSeccion() {
		return seccion;
	}

	public void setSeccion(int seccion) {
		this.seccion = seccion;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

}