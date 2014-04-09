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
 * Versi�n     : 1.0.3
 * Fecha       : 27/11/2003 10:34:41 AM
 * Analista    : Programador3 - Alexis Gu�dez L�pez
 * Descripci�n : Modificaciones por requerimientos de integraci�n BECO y EPA. 
 * =============================================================================
 * Versi�n     : 1.0.2
 * Fecha       : 31/10/2003 03:28:41 PM
 * Analista    : Gabriel Martinelli
 * Descripci�n : Modificaci�n de la clase para requerimientos de BECO.
 * =============================================================================
 * Versi�n     : 1.0.1
 * Fecha       : 29/10/2003 05:13:53 PM
 * Analista    : Programador3 - Alexis Gu�dez L�pez
 * Descripci�n : Integraci�n de las clases Tienda creadas por EPA y BECO
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
 * Descripci�n: 
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
	 * @param numTda - N�mero de la tienda
	 * @param nomb - Nombre
	 * @param razonSocial - Raz�n Social de la Organizaci�n
	 * @param rif - N�mero de RIF
	 * @param nit - N�mero de NIT
	 * @param dir - Direcci�n fisica de la tienda
	 * @param codArea - C�digo de �rea del tel�fono
	 * @param numTlf - N�mero de tel�fono
	 * @param codAreaFax - C�digo de �rea del Fax
	 * @param numFax - N�mero de Fax
	 * @param dirFiscal - Direcci�n fiscal de la tienda
	 * @param codAreaFiscal - C�digo de �rea del tel�fono fiscal
	 * @param numTlfFiscal - N�mero de tel�fono fiscal
	 * @param codAreaFaxFiscal - C�digo de �rea del Fax fiscal
	 * @param numFaxFiscal - N�mero de Fax fiscal
	 * @param pubs - Vector con las l�neas de publicidad de la tienda
	 * @param monedaBase - Moneda base utilizada por la tienda
	 * @param codRegion - C�digo de la regi�n a la que pertenece la tienda
	 * @param nombRegion - Nombre de la regi�n
	 * @param abrevRegion - Abreviado de la regi�n
	 * @param limitEntregaCaja - L�mite de entrega a Caja Principal
	 * @param fecha - Fecha actual para el sistema
	 */
	/*
	* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
	* S�lo se parametriz� el tipo de dato contenido en los 'Vector'
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
	 * Retorna el c�digo de la regi�n.
	 * @return String - El c�digo de la regi�n
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
	 * Devuelve el n�mero identificador de la Tienda.
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
	 * Establece el n�mero identificador de la tienda.
	 * @param numero - N�mero entero a establecer
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
	 * M�todo setFecha
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
	 * Devuelve el c�digo de la moneda base.
	 * @return String - Cadena de 3 m�ximo caracteres
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
	 * @return String - Cadena de 12 m�ximo caracteres
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
	 * @return String - Cadena de 12 m�ximo caracteres
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
	 * Devuelve la Raz�n Social de la compa��a.
	 * @return String - Cadena de 30 m�ximo caracteres
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
	 * Devuelve la direcci�n f�sica de la tienda.
	 * @return String - Cadena de 100 m�ximo caracteres
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
	 * Devuelve la direcci�n fiscal.
	 * @return String - Cadena de 100 m�ximo caracteres
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
	 * Devuelve el nombre de la tienda f�sica "sucursal".
	 * @return String - Cadena de 30 m�ximo caracteres
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
	 * M�todo getNumTelefono
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
	 * M�todo getCodArea
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
	 * M�todo getLimiteEntregaCaja
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