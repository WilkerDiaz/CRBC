/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.manejadorsesion
 * Programa   : Tienda.java
 * Creado por : gmartinelli
 * Creado en  : 20-oct-03 7:45:01
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.0.3
 * Fecha       : 27/11/2003 10:34:41 AM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Modificaciones por requerimientos de integración BECO y EPA. 
 * =============================================================================
 * Versión     : 1.0.2
 * Fecha       : 31/10/2003 03:28:41 PM
 * Analista    : Gabriel Martinelli
 * Descripción : Modificación de la clase para requerimientos de BECO.
 * =============================================================================
 * Versión     : 1.0.1
 * Fecha       : 29/10/2003 05:13:53 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Integración de las clases Tienda creadas por EPA y BECO
 * =============================================================================
 */
package com.becoblohm.cr.manejadorsesion;

import java.sql.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.mediadoresbd.MediadorBD;

/** 
 * Descripción: 
 * 		Esta clase contiene los atributos propios de la tienda donde se encuentra 
 * uvicada la Caja Registradora para realizar sus operaciones
 * 
 */
@SuppressWarnings("unused")
public class Tienda {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Tienda.class);

	private int numero;
	private String nombreSucursal;
	private String razonSocial;
	private String rif;
	private String nit;
	private String direccion;
	private String codArea;
	private String numTelefono;
	private String codAreaFax;
	
	private String numFax;
	private String direccionFiscal;
	private String codAreaFiscal;
	private String numTelefonoFiscal;
	private String codAreaFaxFiscal;
	private String numFaxFiscal;
	private Vector<String> publicidades;
	private String monedaBase;
	private String codRegion;
	private String nombreRegion;
	private String abreviadoRegion;
	private double limiteEntregaCaja;
	private Date fecha;
	private String estado;

	
	/**
	 * Constructor para Tienda.
	 * @param numTda - Número de la tienda
	 * @param nomb - Nombre
	 * @param razonSocial - Razón Social de la Organización
	 * @param rif - Número de RIF
	 * @param nit - Número de NIT
	 * @param dir - Dirección fisica de la tienda
	 * @param codArea - Código de área del teléfono
	 * @param numTlf - Número de teléfono
	 * @param codAreaFax - Código de área del Fax
	 * @param numFax - Número de Fax
	 * @param dirFiscal - Dirección fiscal de la tienda
	 * @param codAreaFiscal - Código de área del teléfono fiscal
	 * @param numTlfFiscal - Número de teléfono fiscal
	 * @param codAreaFaxFiscal - Código de área del Fax fiscal
	 * @param numFaxFiscal - Número de Fax fiscal
	 * @param pubs - Vector con las líneas de publicidad de la tienda
	 * @param monedaBase - Moneda base utilizada por la tienda
	 * @param codRegion - Código de la región a la que pertenece la tienda
	 * @param nombRegion - Nombre de la región
	 * @param abrevRegion - Abreviado de la región
	 * @param limitEntregaCaja - Límite de entrega a Caja Principal
	 * @param fecha - Fecha actual para el sistema
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	Tienda(int numTda, String nombre, String razonSoc, String rif, String nit, String dir, String codArea, String numTlf,
				String codAreaFax, String numFax, String dirFiscal, String codAreaFiscal, String numTlfFiscal,
				String codAreaFaxFiscal, String numFaxFiscal, Vector<String> pubs, String monedaBase, String codRegion,
				String nombRegion, String abrevRegion, double limitEntregaCaja, Date fecha) {
		this.numero = numTda;
		this.nombreSucursal = nombre;
		this.razonSocial = razonSoc;
		this.rif = rif;
		this.nit = nit;
		this.direccion = dir;
		this.codArea = codArea;
		this.numTelefono = numTlf;
		this.codAreaFax = codAreaFax;
		this.numFax = numFax;
		this.direccionFiscal = dirFiscal;
		this.codAreaFiscal = codAreaFiscal;
		this.numTelefonoFiscal = numTlfFiscal;
		this.codAreaFaxFiscal = codAreaFaxFiscal;
		this.numFaxFiscal = numFaxFiscal;
		this.publicidades = pubs;
		this.monedaBase = monedaBase;
		this.codRegion = codRegion;
		this.nombreRegion = nombRegion;
		this.abreviadoRegion = abrevRegion;
		this.limiteEntregaCaja = limitEntregaCaja;
		this.fecha = fecha;
		this.estado = MediadorBD.obtenerEstadoTienda(codRegion);
	}
	
	/**
	 * Retorna el código de la región.
	 * @return String - El código de la región
	 */
	public String getCodRegion() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCodRegion() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getCodRegion() - end");
		}
		return codRegion;
	}

	/**
	 * Devuelve el número identificador de la Tienda.
	 * @return int - Numero entero que identifica a la tienda
	 */
	public int getNumero() {
		if (logger.isDebugEnabled()) {
			logger.debug("getNumero() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getNumero() - end");
		}
		return numero;
	}

	/**
	 * Establece el número identificador de la tienda.
	 * @param numero - Número entero a establecer
	 */
	public void setNumero(int numero) {
		if (logger.isDebugEnabled()) {
			logger.debug("setNumero(int) - start");
		}

		this.numero = numero;

		if (logger.isDebugEnabled()) {
			logger.debug("setNumero(int) - end");
		}
	}

	/**
	 * Método setFecha
	 * 
	 * @param fecha
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	public void setFecha(Date fecha) throws BaseDeDatosExcepcion, ConexionExcepcion{
		if (logger.isDebugEnabled()) {
			logger.debug("setFecha(Date) - start");
		}

		this.fecha = fecha;
		MediadorBD.setFechaTienda(fecha);

		if (logger.isDebugEnabled()) {
			logger.debug("setFecha(Date) - end");
		}
	}

	/**
	 * Devuelve el código de la moneda base.
	 * @return String - Cadena de 3 máximo caracteres
	 */
	public String getMonedaBase() {
		if (logger.isDebugEnabled()) {
			logger.debug("getMonedaBase() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getMonedaBase() - end");
		}
		return monedaBase;
	}

	/**
	 * Devuelve el nit.
	 * @return String - Cadena de 12 máximo caracteres
	 */
	public String getNit() {
		if (logger.isDebugEnabled()) {
			logger.debug("getNit() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getNit() - end");
		}
		return nit;
	}

	/**
	 * Devuelve el listado de lineas publicitarias.
	 * @return Vector
	 */
	public Vector<String> getPublicidades() {
		if (logger.isDebugEnabled()) {
			logger.debug("getPublicidades() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getPublicidades() - end");
		}
		return publicidades;
	}

	/**
	 * Devuelve el rif.
	 * @return String - Cadena de 12 máximo caracteres
	 */
	public String getRif() {
		if (logger.isDebugEnabled()) {
			logger.debug("getRif() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getRif() - end");
		}
		return rif;
	}

	/**
	 * Devuelve la Razón Social de la compañía.
	 * @return String - Cadena de 30 máximo caracteres
	 */
	public String getRazonSocial() {
		if (logger.isDebugEnabled()) {
			logger.debug("getRazonSocial() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getRazonSocial() - end");
		}
		return razonSocial;
	}	
	
	/**
	 * Devuelve la dirección física de la tienda.
	 * @return String - Cadena de 100 máximo caracteres
	 */
	public String getDireccion() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDireccion() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDireccion() - end");
		}
		return direccion;
	}

	/**
	 * Devuelve la dirección fiscal.
	 * @return String - Cadena de 100 máximo caracteres
	 */
	public String getDireccionFiscal() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDireccionFiscal() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDireccionFiscal() - end");
		}
		return direccionFiscal;
	}

	/**
	 * Devuelve el nombre de la tienda física "sucursal".
	 * @return String - Cadena de 30 máximo caracteres
	 */
	public String getNombreSucursal() {
		if (logger.isDebugEnabled()) {
			logger.debug("getNombreSucursal() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getNombreSucursal() - end");
		}
		return nombreSucursal;
	}	

	/**
	 * Método getNumTelefono
	 * 
	 * @return
	 * String
	 */
	public String getNumTelefono() {
		if (logger.isDebugEnabled()) {
			logger.debug("getNumTelefono() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getNumTelefono() - end");
		}
		return numTelefono;
	}	
	/**
	 * Método getCodArea
	 * 
	 * @return
	 * String
	 */
	public String getCodArea() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCodArea() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getCodArea() - end");
		}
		return codArea;
	}

	/**
	 * Método getLimiteEntregaCaja
	 * 
	 * @return
	 * double
	 */
	public double getLimiteEntregaCaja() {
		if (logger.isDebugEnabled()) {
			logger.debug("getLimiteEntregaCaja() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getLimiteEntregaCaja() - end");
		}
		return limiteEntregaCaja;
	}

	public String getEstado() {
		return estado;
	}

}