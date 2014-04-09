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
package com.beco.colascr.transferencias.configuracion;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Vector;

import com.beco.colascr.transferencias.comunicacionbd.Conexiones;
import com.beco.colascr.transferencias.excepciones.BaseDeDatosExcepcion;
import com.beco.colascr.transferencias.excepciones.ConexionExcepcion;

/** 
 * Descripci�n: 
 * 		Esta clase contiene los atributos propios de la tienda donde se encuentra 
 * uvicada la Caja Registradora para realizar sus operaciones
 * 
 */
/*
* En esta clase se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
* S�lo se parametriz� el tipo de dato contenido en los 'Vector' 
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
	 * Retorna el c�digo de la regi�n.
	 * @return String - El c�digo de la regi�n
	 */
	public String getCodRegion() {
		return codRegion;
	}

	/**
	 * Devuelve el n�mero identificador de la Tienda.
	 * @return int - Numero entero que identifica a la tienda
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * Establece el n�mero identificador de la tienda.
	 * @param numero - N�mero entero a establecer
	 */
	public void setNumero(int numero) {
		this.numero = numero;
	}

	/**
	 * M�todo setFecha
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
	 * Devuelve el c�digo de la moneda base.
	 * @return String - Cadena de 3 m�ximo caracteres
	 */
	public String getMonedaBase() {
		return monedaBase;
	}

	/**
	 * Devuelve el nit.
	 * @return String - Cadena de 12 m�ximo caracteres
	 */
	public String getNit() {
		return nit;
	}

	/**
	 * Devuelve el listado de lineas publicitarias.
	 * @return Vector
	 */
	/*
	* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
	* S�lo se parametriz� el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public Vector<String> getPublicidades() {
		return publicidades;
	}

	/**
	 * Devuelve el rif.
	 * @return String - Cadena de 12 m�ximo caracteres
	 */
	public String getRif() {
		return rif;
	}

	/**
	 * Devuelve la Raz�n Social de la compa��a.
	 * @return String - Cadena de 30 m�ximo caracteres
	 */
	public String getRazonSocial() {
		return razonSocial;
	}	
	
	/**
	 * Devuelve la direcci�n f�sica de la tienda.
	 * @return String - Cadena de 100 m�ximo caracteres
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Devuelve la direcci�n fiscal.
	 * @return String - Cadena de 100 m�ximo caracteres
	 */
	public String getDireccionFiscal() {
		return direccionFiscal;
	}

	/**
	 * Devuelve el nombre de la tienda f�sica "sucursal".
	 * @return String - Cadena de 30 m�ximo caracteres
	 */
	public String getNombreSucursal() {
		return nombreSucursal;
	}	

	/**
	 * M�todo getNumTelefono
	 * 
	 * @return
	 * String
	 */
	public String getNumTelefono() {
		return numTelefono;
	}	
	/**
	 * M�todo getCodArea
	 * 
	 * @return
	 * String
	 */
	public String getCodArea() {
		return codArea;
	}

	/**
	 * M�todo getLimiteEntregaCaja
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