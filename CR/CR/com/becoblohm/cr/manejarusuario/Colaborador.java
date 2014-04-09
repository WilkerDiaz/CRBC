/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.manejarusuario
 * Programa   : Colaborador.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 03/10/2003 06:15:16 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.2
 * Fecha       : 04/03/2004 01:30 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Reorganizados los trhows para todos los métodos de modo que no sólo 
 * 				 arroje una ExcepcionCr sino cada una de las excepciones específicas.
 * =============================================================================
 * Versión     : 1.1 (según CVS)
 * Fecha       : 17/02/2004 04:26 PM
 * Analista    : Programador3 - Alexis Guédez López 
 * Descripción : Generalización de la lógica de los identificadores referenciando
 * 				 a las constantes de la clase Control.
 * =============================================================================
 */
package com.becoblohm.cr.manejarusuario;

import org.apache.log4j.Logger;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Iterator;
import java.util.Vector;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.gui.ModeloTabla;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.utiles.Control;

/** 
 * Descripción: 
 * 		Esta clase vincula los valores de los datos de la tabla Afiliado que
 * corresponden a los Colaboradores de la Organización. También proporciona 
 * métodos que permiten la confirmación de existencia de un colaborador 
 * indicado, la recuperación de sus datos desde la base de datos y el 
 * control de cada una de sus propiedades.
 */

public class Colaborador {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Colaborador.class);

	private String codAfiliado;
	private char tipoAfiliado;
	private String nombre;
	private short numTienda;
	private String numFicha;
	private String codDepartamento;
	private String Departamento;
	private String codCargo;
	private String cargo;
	private char estadoColaborador;
	private Date fechaActualizacion;
	private Time horaActualizacion;
	
	/**
	 * Constructor para Colaborador.
	 */
	public Colaborador(){
	}

	/**
	 * Método existe.
	 * 		Retorna verdadero si el colaborador esta registrado en la base de 
	 * datos de la Caja Registradora.
	 * @param buscarCadena - Número de ficha o código del colaborador a buscar.
	 * @return boolean - Retorna verdadero si es un colaborador activo y falso
	 * 		en caso contrario.
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr
	 */
	public boolean existe(String buscarCadena) throws BaseDeDatosExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("existe(String) - start");
		}

		ResultSet resultado = null;
		String fallaColaborador = "No pudo cargarse información del colaborador.";

		boolean existe = false;
		try {
			resultado = MediadorBD.realizarConsulta("SELECT count(*) as cuantos FROM afiliado WHERE numficha = "+buscarCadena+" OR codafiliado = '"+buscarCadena+"'");
			int cuantos = resultado.getInt("cuantos");
			if (cuantos > 0) existe = true;
		} catch (SQLException e) {
			logger.error("existe(String)", e);

			throw new BaseDeDatosExcepcion(fallaColaborador);
		} finally {
			if (resultado != null) {
				try {
					resultado.close();
				} catch (SQLException e1) {
					logger.error("existe(String)", e1);
				}
				resultado = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("existe(String) - end");
		}
		return existe;
	}

	/**
	 * Método obtenerDatos.
	 * 		Retorna la información del colaborador indicado registrada en 
	 * la base de datos de Caja Registradora.
	 * @param buscarCadena - Número de ficha del colaborador a buscar
	 * @return Colaborador - Objeto colaborador con los datos respectivos
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr - Arroja una excepción de conexión/acceso a la
	 * 		base de datos o una de existencia en caso de no existir el 
	 * 		colaborador solicitado
	 */
	public Colaborador obtenerDatos(String buscarCadena) throws BaseDeDatosExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerDatos(String) - start");
		}

		Colaborador xColaborador = new Colaborador();
		ResultSet resultado	= null;
		String fallaColaborador = "No pudo cargarse información del colaborador.";
		String fallaRegistro = "Colaborador no registrado o no activo en el sistema.";
		StringBuffer cedula = new StringBuffer(buscarCadena);
		StringBuffer ficha = new StringBuffer(buscarCadena);
		
		try {
			int longitud = buscarCadena.length();
			if ((buscarCadena.toUpperCase().startsWith("V-"))||(buscarCadena.toUpperCase().startsWith("E-"))){
				if (longitud < Control.getLONGITUD_CODIGO()){
					for (int i=0; i < Control.getLONGITUD_CODIGO()-longitud; i++){
						cedula.insert(Control.getFORMATO_COLABORADOR().length(),'0');
					}
				}	
			}
			else if (longitud < Control.getLONGITUD_ID()){
				for (int i=0; i < Control.getLONGITUD_ID()-longitud; i++){
					ficha.insert(0,'0');
				}
			}

			resultado = MediadorBD.realizarConsulta("SELECT a.codafiliado, a.nombre, a.numtienda, " +
					"a.numficha, a.coddepartamento, a.codcargo, " +
					"c.nombre as cargo, a.actualizacion FROM afiliado a inner join cargo c on " +
					"(c.codcargo = a.codcargo) WHERE numficha= '"+ficha.toString()+"' and estadocolaborador = 'A' ");
					//"(numficha = '"+ficha.toString()+ "' OR codafiliado='"+cedula.toString()+"')");
			int cuantos = MediadorBD.getFilas();
			if(cuantos == 0){ throw new UsuarioExcepcion(fallaRegistro); }
			resultado.beforeFirst();
			while (resultado.next()){
				xColaborador.setCodAfiliado(resultado.getString("codafiliado"));
				String nombreCompleto;
				/*if (resultado.getString("apellido") != null) 
					nombreCompleto = resultado.getString("nombre") + ' ' + resultado.getString("apellido");
				else*/
					nombreCompleto = resultado.getString("nombre");
				xColaborador.setNombre(nombreCompleto);
				xColaborador.setNumTienda(resultado.getShort("numtienda"));
				xColaborador.setNumFicha(resultado.getString("numficha"));
				xColaborador.setCodDepartamento(resultado.getString("coddepartamento"));
				//xColaborador.setDepartamento(resultado.getString("departamento"));
				xColaborador.setCodCargo(resultado.getString("codcargo"));
				xColaborador.setCargo(resultado.getString("cargo"));
				xColaborador.setFechaActualizacion(resultado.getDate("actualizacion"));
				xColaborador.setHoraActualizacion(resultado.getTime("actualizacion"));
			}
		} catch (ExcepcionCr e) {
			logger.error("obtenerDatos(String)", e);

			throw new BaseDeDatosExcepcion(fallaColaborador);
		} catch (SQLException e) {
			logger.error("obtenerDatos(String)", e);

			throw new BaseDeDatosExcepcion(fallaColaborador);
		} finally {
			if (resultado != null){
				try {
					resultado.close();
				} catch (SQLException e1) {
					logger.error("obtenerDatos(String)", e1);
				}
				resultado = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerDatos(String) - end");
		}
		return xColaborador;
	}

	/**
	 * Método cargarRegistros.
	 * 		Devuelve los datos correspondientes a los colaboradores registrados.
	 * @param vigentes - Verdadero para filtrar resultados por registros vigentes
	 * @return Vector - Objeto vector contenedor de los objetos Colaborador
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr - Arroja excepciones en la conexión con la base de datos
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Colaborador> cargarRegistros(boolean vigentes) throws BaseDeDatosExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("cargarRegistros(boolean) - start");
		}

		ResultSet datos = null;
		Vector<Colaborador> resultados = new Vector<Colaborador>();
		Colaborador xColaborador = new Colaborador();
		String fallaRegistros = "No pudo obetnerse información de la base de datos.";
		try {
			StringBuffer tiraSql = new StringBuffer("");
			if (vigentes){
				tiraSql = new StringBuffer("SELECT DISTINCT * FROM afiliado WHERE (tipoafiliado = 'E' AND estadocolaborador = 'A')");
			}	
			else{
				tiraSql = new StringBuffer("SELECT DISTINCT * FROM afiliado WHERE (tipoafiliado = 'E')");
			}	 	
			datos = MediadorBD.realizarConsulta(tiraSql.toString());
			datos.beforeFirst();
			while (datos.next()){
				xColaborador.setCodAfiliado(datos.getString("codafiliado"));
				xColaborador = xColaborador.obtenerDatos(xColaborador.getCodAfiliado());
				resultados.addElement(xColaborador);
				xColaborador = new Colaborador();
			}
		} catch (SQLException e) {
			logger.error("cargarRegistros(boolean)", e);

			throw new BaseDeDatosExcepcion(fallaRegistros);
		} finally {
			if (datos != null) {
				try {
					datos.close();
				} catch (SQLException e1) {
					logger.error("cargarRegistros(boolean)", e1);
				}
				datos = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cargarRegistros(boolean) - end");
		}
		return resultados;
	}

	/**
	 * Método cargarCatalogo.
	 * 		Devuelve los datos correspondientes a la entidad indicada además de los 
	 * títulos de las columnas a visualizar.
	 * @param vigentes - Verdadero para filtrar resultados por registros vigentes
	 * @return Catalogo - Objeto catalogo con los datos y títulos respectivos
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr - Arroja excepciones en la conexión con la base de datos
	 */
	public static ModeloTabla cargarCatalogo(boolean vigentes) throws BaseDeDatosExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("cargarCatalogo(boolean) - start");
		}

		Vector<Colaborador> resultado = new Vector<Colaborador>();
		ModeloTabla xCatalogo = new ModeloTabla();
		Colaborador xColaborador = new Colaborador();
		int i = 0;
		
		resultado = cargarRegistros(vigentes);
		String[] titulos = new String[4];
		titulos[0] = "Identificador";
		titulos[1] = "Nombre";
		titulos[2] = "Tienda";
		titulos[3] = "Cargo";
		Iterator<Colaborador> ciclo = resultado.iterator();
		Object[][] datos = new Object[resultado.size()][3];
		while(ciclo.hasNext())
		{    			
			xColaborador = (Colaborador)ciclo.next();
			datos[i][0] = new String(xColaborador.getCodAfiliado());
			datos[i][1] = new String(xColaborador.getNombre());
			datos[i][2] = new Short(xColaborador.getNumTienda());
			datos[i][3] = new String(xColaborador.getCargo());
			i++;
		}
		xCatalogo.setTitulos(titulos);
		xCatalogo.setDatos(datos);

		if (logger.isDebugEnabled()) {
			logger.debug("cargarCatalogo(boolean) - end");
		}
		return xCatalogo;
	}

	/**
	 * Devuelve el valor del código del afiliado del objeto colaborador.
	 * @return String
	 */
	public String getCodAfiliado() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCodAfiliado() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getCodAfiliado() - end");
		}
		return codAfiliado;
	}

	/**
	 * Devuelve el valor del código del departamento del objeto Colaborador.
	 * @return String
	 */
	public String getCodDepartamento() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCodDepartamento() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getCodDepartamento() - end");
		}
		return codDepartamento;
	}

	/**
	 * Devuelve el valor del indicador de la situación del colaborador 
	 * ("A"-Activo / "B"-Dado de Baja).
	 * @return char
	 */
	public char getEstadoColaborador() {
		if (logger.isDebugEnabled()) {
			logger.debug("getEstadoColaborador() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getEstadoColaborador() - end");
		}
		return estadoColaborador;
	}

	/**
	 * Devuelve el nombre del objeto colaborador.
	 * @return String
	 */
	public String getNombre() {
		if (logger.isDebugEnabled()) {
			logger.debug("getNombre() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getNombre() - end");
		}
		return nombre;
	}

	/**
	 * Devuelve el valor del número de ficha o código del objeto 
	 * Colaborador.
	 * @return String
	 */
	public String getNumFicha() {
		if (logger.isDebugEnabled()) {
			logger.debug("getNumFicha() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getNumFicha() - end");
		}
		return numFicha;
	}

	/**
	 * Devuelve el valor del número de la tienda a la que está asignado
	 * el objeto Colaborador.
	 * @return short
	 */
	public short getNumTienda() {
		if (logger.isDebugEnabled()) {
			logger.debug("getNumTienda() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getNumTienda() - end");
		}
		return numTienda;
	}

	/**
	 * Devuelve el valor del tipo de afiliado, para el caso de los 
	 * colaboradores es "E"-Empleado.
	 * @return char
	 */
	public char getTipoAfiliado() {
		if (logger.isDebugEnabled()) {
			logger.debug("getTipoAfiliado() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getTipoAfiliado() - end");
		}
		return tipoAfiliado;
	}

	/**
	 * Establece valor de la cédula del afiliado del objeto Colaborador.
	 * @param codAfiliado - Valor del código del afiliado (caracter de
	 * 		12 con nacionalidad "V-"-Venezolano / "E-"Extranjero)
	 */
	public void setCodAfiliado(String codAfiliado) {
		if (logger.isDebugEnabled()) {
			logger.debug("setCodAfiliado(String) - start");
		}

		this.codAfiliado = codAfiliado;

		if (logger.isDebugEnabled()) {
			logger.debug("setCodAfiliado(String) - end");
		}
	}

	/**
	 * Establece el valor del código del departamento del objeto Colaborador.
	 * @param codDepartamento - Valor del código del departamento, debe estar
	 * 		registrado en la tabla Departamento
	 */
	public void setCodDepartamento(String codDepartamento) {
		if (logger.isDebugEnabled()) {
			logger.debug("setCodDepartamento(String) - start");
		}

		this.codDepartamento = codDepartamento;

		if (logger.isDebugEnabled()) {
			logger.debug("setCodDepartamento(String) - end");
		}
	}

	/**
	 * Establece el valor del indicador de la situación del colaborador 
	 * ("A"-Activo / "B"-Dado de Baja).
	 * @param estadoColaborador - Indicador de la situación del colaborador
	 */
	public void setEstadoColaborador(char estadoColaborador) {
		if (logger.isDebugEnabled()) {
			logger.debug("setEstadoColaborador(char) - start");
		}

		this.estadoColaborador = estadoColaborador;

		if (logger.isDebugEnabled()) {
			logger.debug("setEstadoColaborador(char) - end");
		}
	}

	/**
	 * Establece el nombre del objeto colaborador.
	 * @param nombre - Nombres y apellidos del colaborador
	 */
	public void setNombre(String nombre) {
		if (logger.isDebugEnabled()) {
			logger.debug("setNombre(String) - start");
		}

		this.nombre = nombre;

		if (logger.isDebugEnabled()) {
			logger.debug("setNombre(String) - end");
		}
	}

	/**
	 * Establece el valor del número de ficha o código del objeto 
	 * Colaborador.
	 * @param numFicha - Número de ficha o código del colaborador
	 */
	public void setNumFicha(String numFicha) {
		if (logger.isDebugEnabled()) {
			logger.debug("setNumFicha(String) - start");
		}

		this.numFicha = numFicha;

		if (logger.isDebugEnabled()) {
			logger.debug("setNumFicha(String) - end");
		}
	}

	/**
	 * Establece el valor del número de la tienda a la que está 
	 * asignado	el objeto Colaborador.
	 * @param numTienda - Número de tienda correspondiente
	 */
	public void setNumTienda(short numTienda) {
		if (logger.isDebugEnabled()) {
			logger.debug("setNumTienda(short) - start");
		}

		this.numTienda = numTienda;

		if (logger.isDebugEnabled()) {
			logger.debug("setNumTienda(short) - end");
		}
	}

	/**
	 * Establece el valor del tipo de afiliado, para el caso de los 
	 * colaboradores es "E"-Empleado.
	 * @param tipoAfiliado - Indicador del tipo de afiliado
	 */
	public void setTipoAfiliado(char tipoAfiliado) {
		if (logger.isDebugEnabled()) {
			logger.debug("setTipoAfiliado(char) - start");
		}

		this.tipoAfiliado = tipoAfiliado;

		if (logger.isDebugEnabled()) {
			logger.debug("setTipoAfiliado(char) - end");
		}
	}

	/**
	 * Devuelve la fecha (AAAA-MM-DD) de la última actualización de 
	 * los datos del colaborador según cambios a la base de datos en 
	 * el servidor.
	 * @return Date
	 */
	public Date getFechaActualizacion() {
		if (logger.isDebugEnabled()) {
			logger.debug("getFechaActualizacion() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getFechaActualizacion() - end");
		}
		return fechaActualizacion;
	}

	/**
	 * Devuelve la hora (HH:MM:SS) de la última actualización de 
	 * los datos del colaborador según cambios a la base de datos en 
	 * el servidor.
	 * @return Time
	 */
	public Time getHoraActualizacion() {
		if (logger.isDebugEnabled()) {
			logger.debug("getHoraActualizacion() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getHoraActualizacion() - end");
		}
		return horaActualizacion;
	}

	/**
	 * Establece la fecha (AAAA-MM-DD) de la última actualización de 
	 * los datos del colaborador según cambios a la base de datos en 
	 * el servidor.
	 * @param fechaActualizacion - Fecha de la última actualizacion
	 */
	public void setFechaActualizacion(Date fechaActualizacion) {
		if (logger.isDebugEnabled()) {
			logger.debug("setFechaActualizacion(Date) - start");
		}

		this.fechaActualizacion = fechaActualizacion;

		if (logger.isDebugEnabled()) {
			logger.debug("setFechaActualizacion(Date) - end");
		}
	}

	/**
	 * Establece la hora (HH:MM:SS) de la última actualización de 
	 * los datos del colaborador según cambios a la base de datos en 
	 * el servidor.
	 * @param horaActualizacion - Hora de la última actualizacion
	 */
	public void setHoraActualizacion(Time horaActualizacion) {
		if (logger.isDebugEnabled()) {
			logger.debug("setHoraActualizacion(Time) - start");
		}

		this.horaActualizacion = horaActualizacion;

		if (logger.isDebugEnabled()) {
			logger.debug("setHoraActualizacion(Time) - end");
		}
	}

	/**
	 * Devuelve el nombre del cargo que ocupa el colaborador
	 * @return String - Nombre del cargo. Cadena de 40 caracteres max.
	 */
	public String getCargo() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCargo() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getCargo() - end");
		}
		return cargo;
	}

	/**
	 * Establece el nombre del cargo que ocupa el colaborador
	 * @param cargo - Nombre del cargo. Cadena de 40 caracteres max.
	 */
	public void setCargo(String cargo) {
		if (logger.isDebugEnabled()) {
			logger.debug("setCargo(String) - start");
		}

		this.cargo = cargo;

		if (logger.isDebugEnabled()) {
			logger.debug("setCargo(String) - end");
		}
	}

	/**
	 * Devuelve el código identificador del cargo que ocupa el colaborador
	 * @return String - Código del cargo. Cadena de 4 caracteres max.
	 */
	public String getCodCargo() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCodCargo() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getCodCargo() - end");
		}
		return codCargo;
	}

	/**
	 * Establece el código identificador del cargo que ocupa el colaborador
	 * @param codCargo - Código del cargo. Cadena de 4 caracteres max.
	 */
	public void setCodCargo(String codCargo) {
		if (logger.isDebugEnabled()) {
			logger.debug("setCodCargo(String) - start");
		}

		this.codCargo = codCargo;

		if (logger.isDebugEnabled()) {
			logger.debug("setCodCargo(String) - end");
		}
	}

	/**
	 * Devuelve el nombre del departamento al que está asignado el colaborador
	 * @return String - Nombre del departamento. Cadena de 30 caracteres max.
	 */
	public String getDepartamento() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDepartamento() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDepartamento() - end");
		}
		return Departamento;
	}

	/**
	 * Establece el nombre del departamento al que está asignado el colaborador
	 * @param departamento - Nombre del departamento. Cadena de 30 caracteres max.
	 */
	public void setDepartamento(String departamento) {
		if (logger.isDebugEnabled()) {
			logger.debug("setDepartamento(String) - start");
		}

		Departamento = departamento;

		if (logger.isDebugEnabled()) {
			logger.debug("setDepartamento(String) - end");
		}
	}

}
