/**
 * =============================================================================
 * Proyecto   : CajaRegistradora
 * Paquete    : com.becoblohm.manejarVenta
 * Programa   : Cliente.java
 * Creado por : irojas
 * Creado en  : 19-feb-04 13:41:56
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versi�n     : 2.0
 * Fecha       : 11-07-2006
 * Analista    : yzambrano
 * Descripci�n : Cambio en la estructura de direcci�n del afiliado para agregar
 * 				 estado, ciudad y urbanizaci�n. Se agregaron atributos para almacenar
 * 				 los c�digos respectivos.
 * =============================================================================
 * Versi�n     : 2.0
 * Fecha       : 20-06-2006
 * Analista    : yzambrano
 * Descripci�n : - Se agregaron nuevos atributos para clientes que responden a la 
 *                 definici�n de la nueva estructura de afiliados. Se agreg� tel�fono adicional
 *                 y direcci�n de correo electr�nico
 * 				 - Cambio en la estructura de direcci�n del afiliado para agregar
 * 				   estado, ciudad y urbanizaci�n.
 * =============================================================================
 */

package com.becoblohm.cr.manejarventa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.mediadoresbd.MediadorBD;

/** 
 * Descripci�n: 
 * 		Clase que maneja las propiedades de los clientes que se asignan a la venta.
 * Estos pueden ser clientes afiliados de la empresa, colaboradores o clientes usuales.  
 */
/*
* En esta clase se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
* S�lo se parametriz� el tipo de dato de los distintos ArrayList
* Fecha: agosto 2011
*/
public class Cliente implements Serializable {

	private String codCliente;
	private char tipoCliente;
	private String numFicha;
	private String numTienda;
	private String nombre;
	private String apellido;
	private String nit;
	private String direccion="";
	private String dirFiscal="";
	private String codArea;
	private String numTelefono;
	private String codAreaSec;
	private String numTelefonoSec;
	private String email;
	private Date fechaAfiliacion;
	private boolean exento;
	private char estadoCliente;
	private char estadoColaborador;
	private boolean contactar;
	private ArrayList <StringBuffer>mensajes = new ArrayList<StringBuffer>();
	private String avCalle;
	private String edfCasa;
	private String nroApto;
	private String codEdo = "";
	private String codCiu = "";
	private String codUrb = "";

	//Campos nuevos agregados a los clientes Wdiaz Crm
	private char sexo;
	private char estadoCivil;
	private Date fechaNaci;
	private boolean Contribuyente=false;
	
	public boolean isContribuyente() {
		return Contribuyente;
	}
	public void setContribuyente(boolean contribuyente) {
		Contribuyente = contribuyente;
	}

	
	/**
	 * Constructor para Cliente.
	 */
	public Cliente(){
		codCliente = null;
		nombre = null;
		apellido = null;
		estadoCliente = Sesion.ACTIVO ;
		tipoCliente = Sesion.CLIENTE_NATURAL;
		estadoColaborador = Sesion.ACTIVO;
		Contribuyente=false;
	}
	
	/** Constructor para la clase cliente
	 * @param codigo C�digo del cliente. Puede ser su cedula o su c�digo de afiliado de ser el caso.
	 */
	public Cliente(String codigo) {
		this();
		codCliente = codigo;
	}
	
	/** Constructor para la clase cliente
	 * @param codigo C�digo del cliente. Puede ser su cedula o su c�digo de afiliado de ser el caso.
	 */
	public Cliente(String codigo, String nombreCliente) {
		this();
		codCliente = codigo;
		nombre = nombreCliente;
	}
	
	/**
	 * Constructor para Cliente.java
	 *		Constructor para la clase cliente. S�lo para el registro de clientes temporales
	 * @param nombre
	 * @param apellido
	 * @param ci
	 * @param numTelf
	 * @param codArea
	 * @param tipoCliente
	 * @param contactar
	 * @param estadoCliente
	 */
	public Cliente(String nombre, String apellido,  String ci, String numTelf, String codArea, char tipoCliente, boolean contactar, char edoCliente) {
		this();
		this.codCliente = ci;
		this.tipoCliente = tipoCliente;
		this.nombre = nombre;
		this.apellido = apellido;
		this.codArea= codArea;
		this.numTelefono = numTelf;
		this.contactar = contactar;
		this.exento = false;
		this.estadoCliente = edoCliente;

	}
	
	/**
	 * Constructor para Cliente
	 *
	 * @param codCliente C�digo del cliente. Puede ser su cedula o su c�digo de afiliado de ser el caso.
	 * @param tipoCliente Char que indica si el cliente es un COLABORADOR (E), un cliente natural (N), un cliente jur�dico (J), un cliente gubernamental (G)
	 * @param numF N�mero de ficha del Afiliado (Solo aplica si es COLABORADOR
	 * @param numTienda N�mero de la tienda donde se afili� el cliente
	 * @param nombre Nombre del cliente
	 * @param nit NIT del cliente
	 * @param direccion Direcci�n de habitaci�n del cliente 
	 * @param dirFiscal Direcci�n fiscal
	 * @param codArea c�digo de area del n�mero telef�nico del cliente
	 * @param numTelefono N�mero telef�nico
	 * @param fechaAfiliacion Fecha de afiliaci�n del cliente
	 * @param exento Boolean que indica si el cliente est� exento de impuesto
	 * @param estadoCliente estado del cliente: Activo (A), No activo (N)
	 */
	public Cliente(String codCliente, char tipoCliente, String numF, String numTienda, String nombre, String apellido, String nit, String direccion, String dirFiscal, String codArea, String numTelefono, Date fechaAfiliacion, boolean exento, char estadoCliente, char estadoColaborador) {
		this();
		this.codCliente = codCliente;
		this.tipoCliente = tipoCliente;
		this.numFicha = numF;
		this.numTienda = numTienda;
		this.nombre = nombre;
		this.apellido = apellido;
		this.nit = nit;
		this.direccion = direccion;
		this.dirFiscal = dirFiscal;
		this.codArea= codArea;
		this.numTelefono = numTelefono;
		this.fechaAfiliacion = fechaAfiliacion;
		this.exento = exento;
		this.estadoCliente = estadoCliente;
		this.estadoColaborador = estadoColaborador;
	}
	
	/**
	 * Constructor para registro de clientes para servicios
	 * @param codCliente C�digo del cliente. Puede ser su cedula o su c�digo de afiliado de ser el caso.
	 * @param tipoCliente Char que indica si el cliente es un COLABORADOR (E), un cliente natural (N), un cliente jur�dico (J), un cliente gubernamental (G)
	 * @param nombre Nombre del cliente
	 * @param apellido Apellido del cliente
	 * @param direccion Direcci�n de habitaci�n del cliente
	 * @param codArea c�digo de area del n�mero telef�nico del cliente
	 * @param numTelefono N�mero telef�nico 
	 * @param numF N�mero de ficha del Afiliado (Solo aplica si es COLABORADOR
	 * @param estadoCliente estado del cliente: Activo (A), No activo (N) 
	 * @param codArea2 c�digo de area del n�mero telef�nico del cliente
	 * @param numTelefono2 N�mero telef�nico 
	 * @param correo Direcci�n de correo electr�nico
	 * 
	 */
	 
	/*public Cliente(String codCliente, char tipoCliente, String nombre, String apellido, String direccion,String correo) {
	this();
	this.codCliente = tipoCliente + "-" + codCliente;
	this.tipoCliente = tipoCliente;
	this.nombre = nombre;
	this.apellido = apellido;
	this.direccion = direccion;
	this.email = correo;
}*/
	//Constructor realizado para agregarle campos nuevos a registrar cliente por servicio al cliente
	public Cliente(String codCliente, char tipoCliente, String nombre, String apellido, String direccion, String codArea, 
				   String numTelefono, char estadoCliente, String codArea2, 
				   String numTelefono2, String correo, boolean contactar,char sexo,char estadoCivil,Date fechaNaci,String zonaResidencial) {
		this();
		this.codCliente = tipoCliente + "-" + codCliente;
		this.tipoCliente = tipoCliente;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
		this.codArea= codArea;
		this.numTelefono = numTelefono;
		this.estadoCliente = estadoCliente;
		this.codAreaSec = codArea2;
		this.numTelefonoSec = numTelefono2;
		this.email = correo;
		this.contactar = contactar;
		
		this.sexo = sexo;
		this.estadoCivil = estadoCivil;
		this.fechaNaci = fechaNaci;
		this.dirFiscal = zonaResidencial;
		
		StringTokenizer palabra = new StringTokenizer(direccion,">");
		try{
			this.avCalle = palabra.nextToken();
			this.edfCasa = palabra.nextToken();
			this.nroApto = palabra.nextToken();
			this.codUrb = palabra.nextToken();
			this.codCiu = palabra.nextToken();
			this.codEdo = palabra.nextToken();
		} catch (Exception e)
		{
			// Es colaborador	
		}
	}
	
	//constructor original
	
	public Cliente(String codCliente, char tipoCliente, String nombre, String apellido, String direccion, String codArea, 
			   String numTelefono, char estadoCliente, String codArea2, 
			   String numTelefono2, String correo, boolean contactar) {
	this();
	this.codCliente = tipoCliente + "-" + codCliente;
	this.tipoCliente = tipoCliente;
	this.nombre = nombre;
	this.apellido = apellido;
	this.direccion = direccion;
	this.codArea= codArea;
	this.numTelefono = numTelefono;
	this.estadoCliente = estadoCliente;
	this.codAreaSec = codArea2;
	this.numTelefonoSec = numTelefono2;
	this.email = correo;
	this.contactar = contactar;
	
	StringTokenizer palabra = new StringTokenizer(direccion,">");
	try{
		this.avCalle = palabra.nextToken();
		this.edfCasa = palabra.nextToken();
		this.nroApto = palabra.nextToken();
		this.codUrb = palabra.nextToken();
		this.codCiu = palabra.nextToken();
		this.codEdo = palabra.nextToken();
	} catch (Exception e)
	{
		// Es colaborador	
	}
	
}
	//fin del constructor

	public Cliente(String codCliente, char tipoCliente, String numF, String numTienda, String nombre, 
				   String apellido, String nit, String direccion, String dirFiscal, String codArea, 
				   String numTelefono, String codArea1, String telefono1, Date fechaAfiliacion, String email,
				   boolean exento, boolean contactar, char estadoCliente, char estadoColaborador) {
		this();
		this.codCliente = codCliente;
		this.tipoCliente = tipoCliente;
		this.numFicha = numF;
		this.numTienda = numTienda;
		this.nombre = nombre;
		this.apellido = apellido;
		this.nit = nit;
		this.direccion = direccion;
		this.dirFiscal = dirFiscal;
		this.codArea= codArea;
		this.numTelefono = numTelefono;
		this.codAreaSec = codArea1;
		this.numTelefonoSec = telefono1;
		this.email = email;
		this.fechaAfiliacion = fechaAfiliacion;
		this.exento = exento;
		this.estadoCliente = estadoCliente;
		this.estadoColaborador = estadoColaborador;
		this.contactar = contactar;

		StringTokenizer palabra = new StringTokenizer(direccion,">");
		try{
			this.avCalle = palabra.nextToken();
			this.edfCasa = palabra.nextToken();
			this.nroApto = palabra.nextToken();
			this.codUrb = palabra.nextToken();
			this.codCiu = palabra.nextToken();
			this.codEdo = palabra.nextToken();
		} catch (Exception e)
		{
			// Es colaborador	
		}
	}
	
	
	//nuevo constructor para cargar los datos si el cliente existe..CRM
	public Cliente(String codCliente, char tipoCliente, String numF, String numTienda, String nombre, 
			   String apellido, String nit, String direccion, String dirFiscal, String codArea, 
			   String numTelefono, String codArea1, String telefono1, Date fechaAfiliacion, String email,
			   boolean exento, boolean contactar, char estadoCliente, char estadoColaborador,char sexo,char estadoCivil,Date fechaNaci,String zonaResidencial) {
	this();
	this.codCliente = codCliente;
	this.tipoCliente = tipoCliente;
	this.numFicha = numF;
	this.numTienda = numTienda;
	this.nombre = nombre;
	this.apellido = apellido;
	this.nit = nit;
	this.direccion = direccion;
	this.dirFiscal = dirFiscal;
	this.codArea= codArea;
	this.numTelefono = numTelefono;
	this.codAreaSec = codArea1;
	this.numTelefonoSec = telefono1;
	this.email = email;
	this.fechaAfiliacion = fechaAfiliacion;
	this.exento = exento;
	this.estadoCliente = estadoCliente;
	this.estadoColaborador = estadoColaborador;
	this.contactar = contactar;
	//nuevos CRM
	this.sexo=sexo;
	this.estadoCivil=estadoCivil;
	this.fechaNaci=fechaNaci;
	this.dirFiscal=zonaResidencial;

	StringTokenizer palabra = new StringTokenizer(direccion,">");
	try{
		this.avCalle = palabra.nextToken();
		this.edfCasa = palabra.nextToken();
		this.nroApto = palabra.nextToken();
		this.codUrb = palabra.nextToken();
		this.codCiu = palabra.nextToken();
		this.codEdo = palabra.nextToken();
	} catch (Exception e)
	{
		// Es colaborador	
	}
}// fin del nuevo constructor

	/**
	 * M�todo getCodCliente
	 * 
	 * @return String
	 */
	public String getCodCliente() {
		return codCliente;
	}

	/**
	 * M�todo getEstadoCliente
	 * 
	 * @return char
	 */
	public char getEstadoCliente() {
		return estadoCliente;
	}

	/**
	 * M�todo getEstadoColaborador
	 * 
	 * @return char
	 */
	public char getEstadoColaborador() {
		return estadoColaborador;
	}

	/**
	 * M�todo getNombre
	 * 
	 * @return String
	 */
	public String getNombreCompleto() {
		String nombCompleto = null;
		if (apellido != null)
			nombCompleto = nombre + " " + apellido;
		else
			nombCompleto = nombre;
		return nombCompleto;
	}

	/**
	 * M�todo getTipoCliente
	 * 
	 * @return char
	 */
	public char getTipoCliente() {
		return tipoCliente;
	}

	/**
	 * @param string
	 */
	public void setCodCliente(String string) {
		codCliente = string;
	}

	/**
	 * @param c
	 */
	public void setEstadoCliente(char c) {
		estadoCliente = c;
	}

	/**
	 * @param c
	 */
	public void setEstadoColaborador(char c) {
		estadoColaborador = c;
	}

	/**
	 * @param string
	 */
	public void setNombre(String string) {
		nombre = string;
	}

	/**
	 * @param c
	 */
	public void setTipoCliente(char c) {
		tipoCliente = c;
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
	 * M�todo getDireccion
	 * 
	 * @return
	 * String
	 */
	public String getDireccion() {
		String direcc = "";
		String est, ciu, urb;
		try{
			StringTokenizer palabra = new StringTokenizer(direccion,">");
			direcc = palabra.nextToken() + ", " + palabra.nextToken() + ", " + palabra.nextToken() + ", ";
			urb = palabra.nextToken();
			ciu = palabra.nextToken();
			est = palabra.nextToken(); 
			direcc = direcc + MediadorBD.getUrbanizacion(est,ciu,urb).getDescripcion() + ", " + MediadorBD.getCiudad(est,ciu).getDescripcion() + ", " + MediadorBD.getEstado(est).getDescripcion();
		} catch (Exception e)
		{
		}
		return direcc;
	}

	public String getDireccionAlmacenada()
	{
		return direccion;
	}
	/**
	 * M�todo getDirFiscal
	 * 
	 * @return
	 * String
	 */
	public String getDirFiscal() {
		return dirFiscal;
	}

	/**
	 * M�todo isExento
	 * 
	 * @return
	 * boolean
	 */
	public boolean isExento() {
		return exento;
	}

	/**
	 * M�todo getFechaAfiliacion
	 * 
	 * @return
	 * Date
	 */
	public Date getFechaAfiliacion() {
		return fechaAfiliacion;
	}

	/**
	 * M�todo getNit
	 * 
	 * @return
	 * String
	 */
	public String getNit() {
		return nit;
	}

	/**
	 * M�todo getNumTelfono
	 * 
	 * @return
	 * String
	 */
	public String getNumTelefono() {
		return numTelefono;
	}

	/**
	 * M�todo getNumTienda
	 * 
	 * @return
	 * String
	 */
	public String getNumTienda() {
		return numTienda;
	}

	/**
	 * M�todo getNumFicha
	 * 
	 * @return
	 * String
	 */
	public String getNumFicha() {
		return numFicha;
	}

	/**
	 * M�todo isContactar
	 * 
	 * @return
	 * boolean
	 */
	public boolean isContactar() {
		return contactar;
	}

	/**
	 * M�todo getMensajes
	 * 
	 * @return ArrayList
	 */
	public ArrayList<StringBuffer> getMensajes() {
		return mensajes;
	}

	/**
	 * M�todo setMensajes
	 * 
	 * @param list
	 */
	public void setMensajes(ArrayList<StringBuffer> list) {
		mensajes = list;
	}

	/**
	 * @return Devuelve apellido.
	 */
	public String getApellido() {
		return apellido;
	}
	/**
	 * @param apellido El apellido a establecer.
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	/**
	 * @return Devuelve nombre.
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @return
	 */
	public String getCodAreaSec() {
		return codAreaSec;
	}

	/**
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return
	 */
	public String getNumTelefonoSec() {
		return numTelefonoSec;
	}

	/**
	 * @param string
	 */
	public void setCodAreaSec(String string) {
		codAreaSec = string;
	}

	/**
	 * @param string
	 */
	public void setEmail(String string) {
		email = string;
	}

	/**
	 * @param string
	 */
	public void setNumTelefonoSec(String string) {
		numTelefonoSec = string;
	}

	/**
	 * @return
	 */
	public String getAvCalle() {
		return avCalle;
	}

	/**
	 * @return
	 */
	public String getCodCiu() {
		return codCiu;
	}

	/**
	 * @return
	 */
	public String getCodEdo() {
		return codEdo;
	}

	/**
	 * @return
	 */
	public String getCodUrb() {
		return codUrb;
	}

	/**
	 * @return
	 */
	public String getEdfCasa() {
		return edfCasa;
	}

	/**
	 * @return
	 */
	public String getNroApto() {
		return nroApto;
	}

	/**
	 * @return el estadoCivil
	 */
	public char getEstadoCivil() {
		return estadoCivil;
	}

	/**
	 * @param estadoCivil el estadoCivil a establecer
	 */
	public void setEstadoCivil(char estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	/**
	 * @return el sexo
	 */
	public char getSexo() {
		return sexo;
	}

	/**
	 * @param sexo el sexo a establecer
	 */
	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	/**
	 * @return el fechaNaci
	 */
	public Date getFechaNaci() {
		return fechaNaci;
	}

	/**
	 * @param fechaNaci el fechaNaci a establecer
	 */
	public void setFechaNaci(Date fechaNaci) {
		this.fechaNaci = fechaNaci;
	}

}
