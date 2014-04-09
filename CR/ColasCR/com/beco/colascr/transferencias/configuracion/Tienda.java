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
package com.beco.colascr.transferencias.configuracion;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Vector;

import com.beco.colascr.transferencias.comunicacionbd.Conexiones;
import com.beco.colascr.transferencias.excepciones.BaseDeDatosExcepcion;
import com.beco.colascr.transferencias.excepciones.ConexionExcepcion;

/** 
 * Descripción: 
 * 		Esta clase contiene los atributos propios de la tienda donde se encuentra 
 * uvicada la Caja Registradora para realizar sus operaciones
 * 
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
* Fecha: agosto 2011
*/
public class Tienda {
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
	@SuppressWarnings("unused")
	private String abreviadoRegion;
	private double limiteEntregaCaja;
	private Date fecha;

	private String indicaDsctoEmp;
	private double desctoVtaEmpleado;
	private String cambioPrecioCaja;
	private String utilizarVendedor;
	private String dsctosAcumulativos;

	
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
	Tienda(int numTda, String nombre, String razonSoc, String rif, String nit, String dir, String codArea, String numTlf,
				String codAreaFax, String numFax, String dirFiscal, String codAreaFiscal, String numTlfFiscal,
				String codAreaFaxFiscal, String numFaxFiscal, Vector<String> pubs, String monedaBase, String codRegion,
				String nombRegion, String abrevRegion, double limitEntregaCaja, Date fecha, String indDctoEmp,
				double dctoEmp, String cambioPrecio, String utilizaVend, String dsctAcumul) {
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
		this.indicaDsctoEmp = indDctoEmp;
		this.desctoVtaEmpleado = dctoEmp;
		this.cambioPrecioCaja = cambioPrecio;
		this.utilizarVendedor = utilizaVend;
		this.dsctosAcumulativos = dsctAcumul;
	}
	
	/**
	 * Retorna el código de la región.
	 * @return String - El código de la región
	 */
	public String getCodRegion() {
		return codRegion;
	}

	/**
	 * Devuelve el número identificador de la Tienda.
	 * @return int - Numero entero que identifica a la tienda
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * Establece el número identificador de la tienda.
	 * @param numero - Número entero a establecer
	 */
	public void setNumero(int numero) {
		this.numero = numero;
	}

	/**
	 * Método setFecha
	 * 
	 * @param fecha
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	public void setFecha(Date fecha) throws BaseDeDatosExcepcion, ConexionExcepcion{
		this.fecha = fecha;
		//MediadorBD.setFechaTienda(fecha);
	}

	/**
	 * Devuelve el código de la moneda base.
	 * @return String - Cadena de 3 máximo caracteres
	 */
	public String getMonedaBase() {
		return monedaBase;
	}

	/**
	 * Devuelve el nit.
	 * @return String - Cadena de 12 máximo caracteres
	 */
	public String getNit() {
		return nit;
	}

	/**
	 * Devuelve el listado de lineas publicitarias.
	 * @return Vector
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public Vector<String> getPublicidades() {
		return publicidades;
	}

	/**
	 * Devuelve el rif.
	 * @return String - Cadena de 12 máximo caracteres
	 */
	public String getRif() {
		return rif;
	}

	/**
	 * Devuelve la Razón Social de la compañía.
	 * @return String - Cadena de 30 máximo caracteres
	 */
	public String getRazonSocial() {
		return razonSocial;
	}	
	
	/**
	 * Devuelve la dirección física de la tienda.
	 * @return String - Cadena de 100 máximo caracteres
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Devuelve la dirección fiscal.
	 * @return String - Cadena de 100 máximo caracteres
	 */
	public String getDireccionFiscal() {
		return direccionFiscal;
	}

	/**
	 * Devuelve el nombre de la tienda física "sucursal".
	 * @return String - Cadena de 30 máximo caracteres
	 */
	public String getNombreSucursal() {
		return nombreSucursal;
	}	

	/**
	 * Método getNumTelefono
	 * 
	 * @return
	 * String
	 */
	public String getNumTelefono() {
		return numTelefono;
	}	
	/**
	 * Método getCodArea
	 * 
	 * @return
	 * String
	 */
	public String getCodArea() {
		return codArea;
	}

	/**
	 * Método getLimiteEntregaCaja
	 * 
	 * @return
	 * double
	 */
	public double getLimiteEntregaCaja() {
		return limiteEntregaCaja;
	}
	
	public void registrarTienda() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sentencia = "insert into region (codregion,descripcion) values ('" + codRegion + "', '" + nombreRegion + "')";

		try {
			Conexiones.realizarSentencia(sentencia, true);
		} catch (ConexionExcepcion e2) {
		} catch (BaseDeDatosExcepcion e2) {
		}
		
		sentencia = "insert into tienda values ( " + numero + ", '" + nombreSucursal + "', '" + razonSocial + "', ";
		sentencia += "'" + rif + "', '" + nit + "', '" + direccion + "', '" + codArea + "', '" + numTelefono + "', ";
		sentencia += "'" + codAreaFax + "', '" + numFax + "', '" + direccionFiscal + "', '" + codAreaFiscal + "', ";
		sentencia += "'" + numTelefonoFiscal + "', '" + codAreaFaxFiscal + "', '" + numFaxFiscal + "', '" + monedaBase + "', ";
		sentencia += "'" + codRegion + "', " + limiteEntregaCaja + ", ' ', '" + indicaDsctoEmp + "', " + desctoVtaEmpleado + ", ";
		sentencia += "'" + cambioPrecioCaja + "', '";
		sentencia += df.format(fecha);
		sentencia += "', '" + utilizarVendedor + "', '" + dsctosAcumulativos + "', null, null,null,null)";
		try {
			Conexiones.realizarSentencia(sentencia, true);
			// Ingresamos el Usuario del Sistema y su perfil
			sentencia = "insert into perfil (codperfil, descripcion, nivelauditoria, actualizacion) VALUES ('1', 'ADMINISTRADOR', '5', '19700101070000')";
			try{Conexiones.realizarSentencia(sentencia,true);}catch(Exception e1){e1.printStackTrace();}
			sentencia = "insert into usuario (numtienda, numficha, codigobarra, codperfil, clave, nivelauditoria, nombre, puedecambiarclave, indicacambiarclave, fechacreacion, ultimocambioclave, tiempovigenciaclave, actualizacion) VALUES(" + numero + ", '00000000', md5('2300000000016'), 1, md5('2301'), 1, 'USUARIO SISTEMA', 'S', 'N', '" + df.format(Sesion.getFechaSistema()) + "', '" + new Timestamp(Sesion.getFechaSistema().getTime()) + "', 90, '19700101070000')";
			try{Conexiones.realizarSentencia(sentencia,true);}catch(Exception e1){e1.printStackTrace();}
		} catch (Exception e) {
			sentencia = "update tienda set nombresucursal='" + nombreSucursal + "', razonsocial='" + razonSocial + "', ";
			sentencia += "rif='" + rif + "', nit='" + nit + "', direccion='" + direccion + "', codarea='" + codArea + "', numtelefono='" + numTelefono + "', ";
			sentencia += "codareafax='" + codAreaFax + "', numfax='" + numFax + "', direccionfiscal='" + direccionFiscal + "', codareafiscal='" + codAreaFiscal + "', ";
			sentencia += "numtelefonofiscal='" + numTelefonoFiscal + "', codareafaxfiscal='" + codAreaFaxFiscal + "', numfaxfiscal='" + numFaxFiscal + "', monedabase='" + monedaBase + "', ";
			sentencia += "codregion='" + codRegion + "', limiteentregacaja=" + limiteEntregaCaja + ", indicadesctoempleado='" + indicaDsctoEmp + "', desctoventaempleado=" + desctoVtaEmpleado + ", ";
			sentencia += "cambioprecioencaja='" + cambioPrecioCaja + "', ";
			sentencia += "utilizarvendedor='" + utilizarVendedor + "', desctosacumulativos='" + dsctosAcumulativos + "'";
			try {
				Conexiones.realizarSentencia(sentencia, true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		sentencia = "delete from tiendapublicidad";
		try {
			Conexiones.realizarSentencia(sentencia, true);
		} catch (Exception e) {
		}
		for (int i=0; i<publicidades.size(); i++) {
			sentencia = "insert into tiendapublicidad values (" + numero + ", '" + publicidades.elementAt(i) + "', " + (i+1) + ")";
			try {
				Conexiones.realizarSentencia(sentencia, true);
			} catch (Exception e) {
			}
		}
	}

}