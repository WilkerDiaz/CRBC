/**
 * =============================================================================
 * Proyecto   : CRBECOEPA
 * Paquete    : com.becoblohm.cr.manejarusuario
 * Programa   : Funcion.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 06/10/2003 01:59:52 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1
 * Fecha       : 04/03/2004 01:30 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Reorganizados los trhows para todos los métodos de modo que no sólo 
 * 				 arroje una ExcepcionCr sino cada una de las excepciones específicas.
 * =============================================================================
 */
package com.becoblohm.cr.manejarsistema;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.ValidarExcepcion;
import com.becoblohm.cr.gui.ModeloTabla;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarusuario.dbbeans.AddModulo;
import com.becoblohm.cr.manejarusuario.dbbeans.UpdModulo;
import com.becoblohm.cr.mediadoresbd.MediadorBD;

/** 
 * Descripción: 
 * 		Esta clase vincula los valores de los datos de la tabla FuncionPerfil
 * que corresponden al sistema de caja registradora. También proporciona la 
 * confirmación de existencia de un usuario indicado, la recuperación de sus 
 * datos y el control de cada una de sus propiedades.
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato de los distintos ArrayList
* Fecha: agosto 2011
*/
public class Modulo {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Modulo.class);

	private short codModulo;
	private String descripcion;
	private ArrayList<Funcion> funciones;
	private boolean regVigente;
	private Date fechaActualizacion;
	private Time horaActualizacion;
	
	/**
	 * Constructor para Funcion.
	 */
	public Modulo() {
	}

	/**
	 * Método existe.
	 * 		Retorna verdadero si el módulo está registrado en la base de 
	 * datos de la Caja Registradora.
	 * @param xModulo - Código identificador del modulo
	 * @return boolean - Retorna verdadero si el modulo está activo y 
	 * falso en caso contrario.
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr
	 */
	public static boolean existe(Modulo xModulo) throws BaseDeDatosExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("existe(Modulo) - start");
		}

		ResultSet resultado = null;
		String fallaFuncion = "No pudo cargarse información del módulo.";

		boolean existe = false;
		try {
			resultado = MediadorBD.realizarConsulta("SELECT count(*) as cuantos FROM modulos WHERE descripcion = '"+xModulo.getDescripcion()+"' OR codmodulo = "+xModulo.getCodModulo());
			if (resultado.getInt("cuantos") > 0) existe = true;
		} catch (SQLException e) {
			logger.error("existe(Modulo)", e);

			throw new BaseDeDatosExcepcion(fallaFuncion);
		} finally {
			if (resultado != null) {
				try {
					resultado.close();
				} catch (SQLException e1) {
					logger.error("existe(Modulo)", e1);
				}
				resultado = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("existe(Modulo) - end");
		}
		return existe;
	}

	/**
	 * Método obtenerDatos.
	 * 		Retorna la información de la función indicada, registrada en 
	 * la base de datos de Caja Registradora.
	 * @param xModulo - Código identificador de la función a buscar
	 * @return Vector - Objeto vector con los datos del módulo en la posición
	 * inicial y en las siguientes posiciones las funciones disponibles en el
	 * módulo correspondiente
	 * @throws BaseDeDatosExcepcion
	 * @throws ValidarExcepcion
	 * @throws FuncionExcepcion
	 * @throws ExcepcionCr - Arroja una excepción de conexión/acceso a la
	 * 		base de datos o una de existencia en caso de no existir el
	 * 		módulo solicitado
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Object> obtenerDatos(Modulo xModulo) throws BaseDeDatosExcepcion, ValidarExcepcion, FuncionExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerDatos(Modulo) - start");
		}

		Vector<Object> arbolFuncion = new Vector<Object>();
		Funcion xFuncion = new Funcion();
		ResultSet resultado	= null;
		String fallaModulo = "No pudo cargarse información del módulo.";
		String fallaFuncion = "El módulo no tiene asignada funciones para usuarios.";
		String fallaRegistro = "Módulo no registrado o no activo en el sistema.";
		
		try {
			resultado = MediadorBD.realizarConsulta("SELECT DISTINCT * FROM modulos WHERE codmodulo = "+xModulo.getCodModulo()+" OR descripcion = '"+xModulo.getDescripcion()+"' AND regvigente = '" + Sesion.SI + "'");
			int cuantos = MediadorBD.getFilas();
			if(cuantos == 0){ throw new FuncionExcepcion(fallaRegistro); }
			resultado.first();
			xModulo.setCodModulo(resultado.getShort("codmodulo"));
			xModulo.setDescripcion(resultado.getString("descripcion"));
			xModulo.setFechaActualizacion(resultado.getDate("actualizacion"));
			xModulo.setHoraActualizacion(resultado.getTime("actualizacion"));
			boolean regVigente = resultado.getString("regvigente").toUpperCase().equals("S") ? true:false;
			xModulo.setRegVigente(regVigente);
			xModulo.cargarFunciones();
			arbolFuncion.addElement(xModulo);

			resultado = MediadorBD.realizarConsulta("SELECT DISTINCT * FROM funcion WHERE codmodulo = "+xModulo.getCodModulo()+" AND regvigente = '" + Sesion.SI + "'");
			cuantos = MediadorBD.getFilas();
			if(cuantos == 0){ throw new ValidarExcepcion(fallaFuncion); }
			while (resultado.next()){
				xFuncion.setCodFuncion(resultado.getShort("codfuncion"));
				xFuncion.setCodModulo(resultado.getShort("codmodulo"));
				xFuncion.setDescripcion(resultado.getString("descripcion"));
				xFuncion.setFechaActualizacion(resultado.getDate("actualizacion"));
				xFuncion.setHoraActualizacion(resultado.getTime("actualizacion"));
				char nivelAuditoria = resultado.getString("nivelauditoria").toUpperCase().trim().charAt(0);
				xFuncion.setNivelAuditoria(nivelAuditoria);
				regVigente = resultado.getString("regvigente").toUpperCase().equals("S") ? true:false;
				xFuncion.setRegVigente(regVigente);
				arbolFuncion.addElement(xFuncion);
				xFuncion = new Funcion();
			}
		} catch (SQLException e) {
			logger.error("obtenerDatos(Modulo)", e);

			throw new BaseDeDatosExcepcion(fallaModulo);
		} finally {
			if (resultado != null) {
				try {
					resultado.close();
				} catch (SQLException e1) {
					logger.error("obtenerDatos(Modulo)", e1);
				}
				resultado = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerDatos(Modulo) - end");
		}
		return arbolFuncion;
	}

	/**
	 * Method actualizarDatos.
	 * 		Recibe una instancia del objeto módulo con los datos que se 
	 * desean actualizar, en caso de un módulo registrado efectúa una 
	 * actualización de lo contrario añade un nuevo registro a la tabla 
	 * Modulos de la caja registradora iniciada.
	 * @param xModulo - Objeto modulo con los datos que se desean añadir
	 * 		o actualizar a la tabla Modulos de la caja registradora
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr - Arroja una excepción de conexión/acceso a la
	 * 		base de datos en caso de fallar
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se eliminaron variables sin uso
	* Fecha: agosto 2011
	*/
	public void actualizarDatos(Modulo xModulo) throws ExcepcionCr, BaseDeDatosExcepcion, ConexionExcepcion{
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarDatos(Modulo) - start");
		}

		AddModulo addModulo = new AddModulo();
		UpdModulo updModulo = new UpdModulo();
		String fallaModulo = "No se pueden actualizar datos del módulo.";
		ResultSet resultado = null;
		int inc=0;
		
		String regVigente = xModulo.isRegVigente()== true ? "S":"N";
		try {
			resultado = MediadorBD.realizarConsulta("SELECT *,count(*) as cuantos FROM modulos WHERE codmodulo = '"+xModulo.getCodModulo()+"' OR descripcion = '"+xModulo.getDescripcion()+"' GROUP BY codmodulo");
			int cuantos = resultado.getInt("cuantos");
			if (cuantos > 0){
				this.setCodModulo(resultado.getShort("codModulo"));
				this.setDescripcion(resultado.getString("descripcion"));
				updModulo.execute(
					new Short(xModulo.getCodModulo()),
					(String)xModulo.getDescripcion(),
					(String)regVigente);
				if (!(xModulo.isRegVigente())){
					MediadorBD.ejecutar("UPDATE funcion SET regvigente = 'N' WHERE codmodulo = '"+xModulo.getCodModulo());
				}
			}
			else
			{
				resultado = MediadorBD.realizarConsulta("SELECT MAX(codModulo) as maxCodigo FROM modulos");
				int maxCodModulo = resultado.getInt("maxCodigo");
				resultado = MediadorBD.realizarConsulta("SELECT MAX(codfuncion) as maxCodigo FROM funcion");
				int maxCodFuncion = resultado.getInt("maxCodigo");
				if (maxCodModulo > maxCodFuncion){
					inc = maxCodModulo;
				} else inc = maxCodFuncion;
				inc = inc + 1;
				Integer codModulo = new Integer(inc);
				this.setCodModulo(codModulo.shortValue());
				addModulo.execute(
					new Short(xModulo.getCodModulo()),
					(String)xModulo.getDescripcion(),
					(String)"S");
			}
		} catch (SQLException e) {
			logger.error("actualizarDatos(Modulo)", e);

			throw new BaseDeDatosExcepcion(fallaModulo);
		} finally {
			if (resultado != null) {
				try {
					resultado.close();
				} catch (SQLException e1) {
					logger.error("actualizarDatos(Modulo)", e1);
				}
				resultado = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarDatos(Modulo) - end");
		}
	}


	/**
	 * Método vincularFunciones.
	 * 		Recibe una lista de objetos Función de modo se asignen cada
	 * una de las funciones al módulo
	 * @param funciones - Listado de objetos Función
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr
	 */
	public void vincularFunciones(ArrayList<Funcion> funciones) throws BaseDeDatosExcepcion, ExcepcionCr, ConexionExcepcion{
		if (logger.isDebugEnabled()) {
			logger.debug("vincularFunciones(ArrayList) - start");
		}

		Funcion funcion = new Funcion();
		String fallaFuncionModulo = "No pudo almacenarse información a la base de datos.";
		
		try {
			for(int i=0; i<funciones.size(); i++){
				funcion = (Funcion)funciones.get(i);
				funcion.setNuevoModulo(this.getCodModulo());
				funcion.cambiarModulo();
				funcion = new Funcion();
			}
		} catch (ExcepcionCr e) {
			logger.error("vincularFunciones(ArrayList)", e);

			throw new BaseDeDatosExcepcion(fallaFuncionModulo);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("vincularFunciones(ArrayList) - end");
		}
	}

	/**
	 * Método cargarFunciones.
	 * 		Carga el atributo funciones del objeto perfil con el listado de 
	 * funciones y su respectiva configuración de acceso.
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	public void cargarFunciones() throws BaseDeDatosExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("cargarFunciones() - start");
		}

		ArrayList<Funcion> funciones = new ArrayList<Funcion>();
		Funcion xFuncion = new Funcion();
		ResultSet registros = null;
		String falla = "No se pueden cargar funciones vinculadas al perfil.";

		try {
			String tiraSql = "SELECT DISTINCT funcion.codfuncion, funcion.codmodulo, funcion.descripcion, funcion.reqautorizacion FROM modulos, funcion WHERE (modulos.codmodulo = funcion.codmodulo) AND (funcion.regvigente = '" + Sesion.SI + "') AND (funcion.codmodulo = "+this.getCodModulo()+")";
			registros = MediadorBD.realizarConsulta(tiraSql);
			if (MediadorBD.getFilas() > 0){
				registros.beforeFirst();
				while(registros.next()){
					xFuncion = new Funcion();
					xFuncion.setDescripcion(registros.getString("descripcion"));
					boolean reqAutorizacion = registros.getString("reqAutorizacion").trim().toUpperCase().equals("S") ? true:false;
					xFuncion.setReqAutorizacion(reqAutorizacion);
					funciones.add(xFuncion);
				}	
					this.setFunciones(funciones);
			}	
		} catch (SQLException e) {
			logger.error("cargarFunciones()", e);

			throw new BaseDeDatosExcepcion(falla);
		} finally {
			if (registros != null) {
				try {
					registros.close();
				} catch (SQLException e1) {
					logger.error("cargarFunciones()", e1);
				}
				registros = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cargarFunciones() - end");
		}
	}

	/**
	 * Método cargarRegistros.
	 * 		Devuelve los datos correspondientes a los módulos registrados.
	 * @param vigentes - Verdadero para filtrar resultados por registros vigentes
	 * @return Vector - Objeto vector contenedor de los objetos Modulo
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr - Arroja excepciones en la conexión con la base de datos
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Modulo> cargarRegistros(boolean vigentes) throws BaseDeDatosExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("cargarRegistros(boolean) - start");
		}

		Vector<Modulo> resultados = new Vector<Modulo>();
		Modulo xModulo = new Modulo();
		String fallaRegistros = "No pudo obtenerse información de la base de datos.";
		String tiraSql = new String();
		ResultSet datos = null;
		try {
			if (vigentes){
				tiraSql = "SELECT DISTINCT * FROM modulos WHERE regvigente = '" + Sesion.SI + "'";
			}
			else{
				tiraSql = "SELECT DISTINCT * FROM modulos";
			}	
			datos = MediadorBD.realizarConsulta(tiraSql);
			datos.beforeFirst();
			while (datos.next()){
				xModulo.setCodModulo(datos.getShort("codmodulo"));
				xModulo.setDescripcion(datos.getString("descripcion"));
				xModulo.setFechaActualizacion(datos.getDate("actualizacion"));
				xModulo.setHoraActualizacion(datos.getTime("actualizacion"));
				boolean regVigente = datos.getString("regvigente").toUpperCase().equals("S") ? true:false;
				xModulo.setRegVigente(regVigente);
				resultados.addElement(xModulo);
				xModulo = new Modulo();
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
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static ModeloTabla cargarCatalogo(boolean vigentes) throws BaseDeDatosExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarCatalogo(boolean) - start");
		}

		Vector<Modulo> resultado = new Vector<Modulo>();
		ModeloTabla xCatalogo = new ModeloTabla();
		Modulo xModulo = new Modulo();
		String[] titulos;
		Object[][] datos;
		int i = 0;
		
		resultado = cargarRegistros(vigentes);
		if (vigentes == false){
			titulos = new String[3];
			titulos[0] = "Código";
			titulos[1] = "Nombre";
			titulos[2] = "Vigente";
			Iterator<Modulo> ciclo = resultado.iterator();
			datos = new Object[resultado.size()][3];
			while(ciclo.hasNext())
			{    			
				xModulo = ciclo.next();
				datos[i][0] = new Short(xModulo.getCodModulo());
				datos[i][1] = new String(xModulo.getDescripcion());
				datos[i][2] = new Boolean(xModulo.isRegVigente());
				i++;
			}
		}
		else {
			titulos = new String[2];
			titulos[0] = "Código";
			titulos[1] = "Nombre";
			Iterator<Modulo> ciclo = resultado.iterator();
			datos = new Object[resultado.size()][3];
			while(ciclo.hasNext())
			{    			
				xModulo = ciclo.next();
				datos[i][0] = new Short(xModulo.getCodModulo());
				datos[i][1] = new String(xModulo.getDescripcion());
				i++;
			}
		}
		xCatalogo.setTitulos(titulos);
		xCatalogo.setDatos(datos);

		if (logger.isDebugEnabled()) {
			logger.debug("cargarCatalogo(boolean) - end");
		}
		return xCatalogo;
	}

	/**
	 * Devuelve el código identificador del módulo.
	 * @return short
	 */
	public short getCodFuncion() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCodFuncion() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getCodFuncion() - end");
		}
		return codModulo;
	}

	/**
	 * Devuelve el código identificador del módulo correspondiente 
	 * al módulo funcional superior.
	 * @return short
	 */
	public short getCodModulo() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCodModulo() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getCodModulo() - end");
		}
		return codModulo;
	}

	/**
	 * Devuelve la descripción o nombre del módulo.
	 * @return String
	 */
	public String getDescripcion() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDescripcion() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDescripcion() - end");
		}
		return descripcion;
	}

	/**
	 * Devuelve la fecha (AAAA-MM-DD) de la última actualización al registro
	 * en la base de datos.
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
	 * Devuelve la hora (HH:MM:SS) de la última actualización al registro
	 * en la base de datos.
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
	 * Devuelve un indicador "S"-Si / "N"-No para si el registro en la base 
	 * de datos esta activo o no. 
	 * @return boolean - Verdadero si es "S", falso si es "N"
	 */
	public boolean isRegVigente() {
		if (logger.isDebugEnabled()) {
			logger.debug("isRegVigente() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isRegVigente() - end");
		}
		return regVigente;
	}

	/**
	 * Establece el código identificador del módulo.
	 * @param codModulo
	 */
	public void setCodModulo(short codModulo) {
		if (logger.isDebugEnabled()) {
			logger.debug("setCodModulo(short) - start");
		}

		this.codModulo = codModulo;

		if (logger.isDebugEnabled()) {
			logger.debug("setCodModulo(short) - end");
		}
	}

	/**
	 * Establece la descripción o nombre del módulo.
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		if (logger.isDebugEnabled()) {
			logger.debug("setDescripcion(String) - start");
		}

		this.descripcion = descripcion;

		if (logger.isDebugEnabled()) {
			logger.debug("setDescripcion(String) - end");
		}
	}

	/**
	 * Establece la fecha (AAAA-MM-DD) de la última actualización al registro
	 * en la base de datos.
	 * @param fechaActualizacion The fechaActualizacion to set
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
	 * Establece la hora (HH:MM:SS) de la última actualización al registro
	 * en la base de datos.
	 * @param horaActualizacion The horaActualizacion to set
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
	 * Establece un indicador "S"-Si / "N"-No para si el registro en la base 
	 * de datos esta activo o no. 
	 * @param regVigente - Verdadero si es "S", falso si es "N"
	 */
	public void setRegVigente(boolean regVigente) {
		if (logger.isDebugEnabled()) {
			logger.debug("setRegVigente(boolean) - start");
		}

		this.regVigente = regVigente;

		if (logger.isDebugEnabled()) {
			logger.debug("setRegVigente(boolean) - end");
		}
	}

	/**
	 * Devuelve la lista de funciones del sistema vinculadas al perfil.
	 * @return ArrayList - Listado de objetos Funcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	public ArrayList<Funcion> getFunciones() {
		if (logger.isDebugEnabled()) {
			logger.debug("getFunciones() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getFunciones() - end");
		}
		return funciones;
	}

	/**
	 * Establece la lista de funciones del sistema vinculadas al perfil.
	 *  @param funciones  - Listado de objetos Funcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	public void setFunciones(ArrayList<Funcion> funciones) {
		if (logger.isDebugEnabled()) {
			logger.debug("setFunciones(ArrayList) - start");
		}

		this.funciones = funciones;

		if (logger.isDebugEnabled()) {
			logger.debug("setFunciones(ArrayList) - end");
		}
	}

}