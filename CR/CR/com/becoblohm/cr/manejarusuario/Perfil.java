/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.manejarusuario
 * Programa   : Perfil.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 06/10/2003 01:41:45 PM
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
 * Versión     : 1.0.1
 * Fecha       : 18/11/2003 03:28:41 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Modificaciones por cambio en el diseño de la BD para las 
 * 				 entidades Modulo, Funcion y Metodo para EPA y BECO
 * =============================================================================
 */
package com.becoblohm.cr.manejarusuario;

import org.apache.log4j.Logger;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.PerfilExcepcion;
import com.becoblohm.cr.gui.ModeloTabla;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarsistema.Funcion;
import com.becoblohm.cr.manejarsistema.Modulo;
import com.becoblohm.cr.manejarusuario.dbbeans.AddFuncionPerfil;
import com.becoblohm.cr.manejarusuario.dbbeans.AddPerfil;
import com.becoblohm.cr.manejarusuario.dbbeans.UpdFuncionPerfil;
import com.becoblohm.cr.manejarusuario.dbbeans.UpdPerfil;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.utiles.Control;

/** 
 * Descripción: 
 * 		Esta clase vincula los valores de los datos de la tabla Perfil que
 * corresponde a los grupos de usuarios con acceso al sistema. También 
 * proporciona métodos que permiten la confirmación de existencia de un 
 * perfil indicado, la recuperación de sus datos desde la base de datos y 
 * el control de cada una de sus propiedades.
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato de los distintos ArrayList
* Fecha: agosto 2011
*/
public class Perfil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Perfil.class);

	private String codPerfil;
	private String descripcion;
	private ArrayList<ListaFuncion> funciones;//ListaFuncion
	private ArrayList<Usuario> miembros;
	private String nivelAuditoria;
	private boolean regVigente;
	private Date fechaActualizacion;
	private Time horaActualizacion;

	/**
	 * Constructor para Perfil.
	 */
	public Perfil(){
		this.setCodPerfil("0");
		this.setDescripcion("");
		this.setNivelAuditoria("1");
		this.setRegVigente(true);
		this.funciones = null;
	}

	/**
	 * Método obtenerDatos.
	 * 		Retorna la información del perfil indicado, registrado en la 
	 * base de datos de Caja Registradora.
	 * @param xPerfil - Código identificador del perfil a buscar
	 * @return Perfil - Objeto perfil con los datos respectivos
	 * @throws BaseDeDatosExcepcion
	 * @throws PerfilExcepcion
	 * @throws ExcepcionCr - Arroja una excepción de conexión/acceso a la
	 * 		base de datos o una de existencia en caso de no existir el perfil
	 * 		solicitado
	 */
	public Perfil obtenerDatos(Perfil xPerfil) throws BaseDeDatosExcepcion, PerfilExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerDatos(Perfil) - start");
		}

		ResultSet resultado	= null;
		String fallaPerfil = "No pudo cargarse información del perfil.";
		String fallaRegistro = "Perfil no registrado o no activo en el sistema.";
		
		try {
			int longitud = xPerfil.getCodPerfil().length();
			StringBuffer codigo = new StringBuffer(xPerfil.getCodPerfil());
			for (int i=0; i<3-longitud; i++){
				codigo.insert(0,'0');
			}
			resultado = MediadorBD.realizarConsulta("SELECT * FROM perfil WHERE descripcion = '"+xPerfil.getDescripcion().toUpperCase()+"' OR codPerfil = "+codigo.toString()+" AND regvigente = '" + Sesion.SI + "'");
			int cuantos = MediadorBD.getFilas();
			if(cuantos == 0){ throw new PerfilExcepcion(fallaRegistro); }
			resultado.beforeFirst();
			while (resultado.next()){
				xPerfil.setCodPerfil(resultado.getString("codperfil"));
				xPerfil.setDescripcion(resultado.getString("descripcion"));
				xPerfil.setFechaActualizacion(resultado.getDate("actualizacion"));
				xPerfil.setHoraActualizacion(resultado.getTime("actualizacion"));
				xPerfil.setNivelAuditoria(resultado.getString("nivelauditoria").toUpperCase().trim());
				boolean regVigente = resultado.getString("regvigente").toUpperCase().equals("S") ? true:false;
				xPerfil.setRegVigente(regVigente);
			}
		} catch (SQLException e) {
			logger.error("obtenerDatos(Perfil)", e);

			throw new BaseDeDatosExcepcion(fallaPerfil);
		} finally {
			if (resultado != null) {
				try {
					resultado.close();
				} catch (SQLException e1) {
					logger.error("obtenerDatos(Perfil)", e1);
				}
				resultado = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerDatos(Perfil) - end");
		}
		return xPerfil;
	}

	/**
	 * Método existe.
	 * 		Retorna verdadero si esta registrado en la tabla Perfil de la 
	 * caja registradora el perfil solicitado.
	 * @param buscarCadena - Código o descripción del perfil a buscar
	 * @return boolean - Verdadero si esta registrado de lo contrario falso
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr - Arroja una excepción de conexión/acceso a la
	 * 		base de datos en caso de fallar
	 */
	static boolean existe(String buscarCadena) throws BaseDeDatosExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("existe(String) - start");
		}

		ResultSet resultado = null;
		String fallaPerfil = "No pudo cargarse información del perfil.";

		boolean existe = false;
		try {
			resultado = MediadorBD.realizarConsulta("SELECT count(*) as cuantos FROM perfil WHERE descripcion = '"+buscarCadena.toUpperCase()+"' OR codPerfil = '"+buscarCadena+"'");
			int cuantos = resultado.getInt("cuantos");
			if (cuantos > 0) existe = true;
		} catch (SQLException e) {
			logger.error("existe(String)", e);

			throw new BaseDeDatosExcepcion(fallaPerfil);
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
	 * Method actualizarDatos.
	 * 		Recibe una instancia del objeto perfil con los datos que se 
	 * desean actualizar, en caso de un perfil registrado efectúa una 
	 * actualización de lo contrario añade un nuevo registro a la tabla 
	 * Perfil de la caja registradora iniciada.
	 * @param xPerfil - Objeto perfil con los datos que se desean añadir
	 * 		o actualizar a la tabla Perfil de la caja registradora
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr - Arroja una excepción de conexión/acceso a la
	 * 		base de datos en caso de fallar
	 */
	public void actualizarDatos(Perfil xPerfil) throws BaseDeDatosExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarDatos(Perfil) - start");
		}

		AddPerfil addPerfil = new AddPerfil();
		UpdPerfil updPerfil = new UpdPerfil();
		ResultSet resultado = null;
		int inc=0;
		String fallaPerfil = "No se pueden actualizar datos del perfil.";
		
		String regVigente = xPerfil.isRegVigente()==true?"S":"N";
		Timestamp actualizacion = new Timestamp(Calendar.getInstance().getTime().getTime());
		try {
			resultado = MediadorBD.realizarConsulta("SELECT count(*) as cuantos FROM perfil WHERE codperfil = '"+xPerfil.getCodPerfil()+"' OR descripcion = '"+xPerfil.getDescripcion().toUpperCase()+"'");
			int cuantos = resultado.getInt("cuantos");
			if (cuantos > 0){
				resultado = MediadorBD.realizarConsulta("SELECT * FROM perfil WHERE codperfil = '"+xPerfil.getCodPerfil()+"' OR descripcion = '"+xPerfil.getDescripcion().toUpperCase()+"'");
				this.setCodPerfil(resultado.getString("codPerfil"));
				this.setDescripcion(resultado.getString("descripcion"));
				updPerfil.execute(
					(String)String.valueOf(xPerfil.getNivelAuditoria().toUpperCase().charAt(0)),
					(String)regVigente,
					(String)xPerfil.getCodPerfil(),
					(String)xPerfil.getDescripcion().toUpperCase());
			}
			else
			{
				resultado = MediadorBD.realizarConsulta("SELECT MAX(codperfil) as codMaximo FROM perfil");
				String codigo = resultado.getString("codMaximo");
				if (codigo != null){
					inc = new Integer(codigo.trim()).intValue();
				} else codigo = "0";
				inc = inc +1;
				Integer codPerfil = new Integer(inc);
				this.setCodPerfil(codPerfil.toString());
				addPerfil.execute(
					(String)xPerfil.getCodPerfil(),
					(String)xPerfil.getDescripcion().toUpperCase(),
					(String)String.valueOf(xPerfil.getNivelAuditoria().toUpperCase().charAt(0)),
					(String)"S",
					(Timestamp)actualizacion);
			}
		} catch (SQLException e) {
			logger.error("actualizarDatos(Perfil)", e);

			e.getMessage();
			throw new BaseDeDatosExcepcion(fallaPerfil);
		} finally {
			if (resultado != null) {
				try {
					resultado.close();
				} catch (SQLException e1) {
					logger.error("actualizarDatos(Perfil)", e1);
				}
				resultado = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarDatos(Perfil) - end");
		}
	}

	/**
	 * Método agregarMiembros.
	 * 		Recibe una lista de los números de fichas de los usuarios que
	 * pertenecerán al perfil correspondiente
	 * @param usuarios - Listado de números de fichas de usuarios registrados
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr - Arroja una excepción de Usuario en caso que
	 * 		que alguno de los números de ficha no esten registrados o una de Perfil
	 * 		en caso de falla en la conexión/acceso a la base de datos
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	public void agregarMiembros(ArrayList<?> usuarios) throws ExcepcionCr, BaseDeDatosExcepcion, ConexionExcepcion{
		if (logger.isDebugEnabled()) {
			logger.debug("agregarMiembros(ArrayList) - start");
		}

		String xActualizacion = Control.formatoTiempo.format(Sesion.getTimestampSistema());			
		if (!(usuarios.isEmpty())){
			MediadorBD.setPerfilUsuarios("UPDATE usuario SET codperfil = "+this.codPerfil+", actualizacion = "+xActualizacion+" WHERE numFicha = ?", usuarios);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("agregarMiembros(ArrayList) - end");
		}
	}

	/**
	 * Método vincularFunciones.
	 * 		Recibe una lista de objetos ListaFunción de modo se asignen cada
	 * una de las funciones al perfil con sus correspondientes restricciones
	 * @param funciones - Listado de objetos ListaFunción
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr
	 */
	public void vincularFunciones(ArrayList<ListaFuncion> funciones) throws BaseDeDatosExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("vincularFunciones(ArrayList) - start");
		}

		ResultSet resultado = null;
		AddFuncionPerfil addFuncionPerfil = new AddFuncionPerfil();
		UpdFuncionPerfil updFuncionPerfil = new UpdFuncionPerfil();
		StringBuffer habilitado = new StringBuffer("N");
		StringBuffer autorizado = new StringBuffer("N");
		ListaFuncion menu = new ListaFuncion();
		String fallaFuncionPerfil = "No pudo almacenarse información a la base de datos.";
		
		try {
			for(int i=0; i<funciones.size(); i++){
				menu = (ListaFuncion)funciones.get(i);//(ListaFuncion)funciones.elementAt(i);
				habilitado = menu.isHabilitado() == true ? new StringBuffer("S"):new StringBuffer("N");
				autorizado = menu.isAutorizado() == true ? new StringBuffer("S"):new StringBuffer("N");
				resultado = MediadorBD.realizarConsulta("SELECT count(*) as cuantos FROM funcionperfil WHERE (codfuncion = "+menu.getFuncion().getCodFuncion()+" AND codmodulo = "+menu.getFuncion().getCodModulo()+") AND codperfil = "+this.getCodPerfil());
				if (resultado.getInt("cuantos") > 0){
					updFuncionPerfil.execute(
						habilitado.toString(),
						autorizado.toString(),
						this.getCodPerfil(),
						new Short(menu.getFuncion().getCodModulo()),
						new Short(menu.getFuncion().getCodFuncion()));
				}
				else{
					addFuncionPerfil.execute(
						this.getCodPerfil(),
						new Short(menu.getFuncion().getCodModulo()),
						new Short(menu.getFuncion().getCodFuncion()),
						habilitado.toString(),
						autorizado.toString());
				}
			}
		} catch (SQLException e) {
			logger.error("vincularFunciones(ArrayList)", e);

			throw new BaseDeDatosExcepcion(fallaFuncionPerfil);
		} finally {
			if (resultado != null) {
				try {
					resultado.close();
				} catch (SQLException e1) {
					logger.error("vincularFunciones(ArrayList)", e1);
				}
				resultado = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("vincularFunciones(ArrayList) - end");
		}
	}
	
	/**
	 * Método cargarRegistros.
	 * 		Devuelve los datos correspondientes a los perfiles registrados.
	 * @param vigentes - Verdadero para filtrar resultados por registros vigentes
	 * @return Vector - Objeto vector contenedor de los objetos Perfil
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr - Arroja excepciones en la conexión con la base de datos
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Perfil> cargarRegistros(boolean vigentes) throws BaseDeDatosExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("cargarRegistros(boolean) - start");
		}

		ResultSet datos = null;
		Vector<Perfil> resultados = new Vector<Perfil>();
		Perfil xPerfil = new Perfil();
		String tiraSql = new String();
		String fallaRegistros = "No pudo obtenerse información de la base de datos.";
		try {
			if (vigentes){
				tiraSql = "SELECT DISTINCT * FROM perfil WHERE regvigente = '" + Sesion.SI + "'";
			}
			else{
				tiraSql = "SELECT DISTINCT * FROM perfil";
			}	
			datos = MediadorBD.realizarConsulta(tiraSql);
			datos.beforeFirst();
			while (datos.next()){
				xPerfil.setCodPerfil(datos.getString("codperfil"));
				xPerfil.setDescripcion(datos.getString("descripcion"));
				xPerfil.setFechaActualizacion(datos.getDate("actualizacion"));
				xPerfil.setHoraActualizacion(datos.getTime("actualizacion"));
				xPerfil.setNivelAuditoria(datos.getString("nivelauditoria").toUpperCase().trim());
				boolean regVigente = datos.getString("regvigente").toUpperCase().equals("S") ? true:false;
				xPerfil.setRegVigente(regVigente);
				resultados.addElement(xPerfil);
				xPerfil = new Perfil();
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

		Vector<Perfil> resultado = new Vector<Perfil>();
		ModeloTabla xCatalogo = new ModeloTabla();
		Perfil xPerfil = new Perfil();
		String[] titulos;
		Object[][] datos;
		int i = 0;
		
		resultado = cargarRegistros(vigentes);
		if (vigentes == false){
			titulos = new String[3];
			titulos[0] = "Código";
			titulos[1] = "Nombre";
			titulos[2] = "Vigente";
			Iterator<Perfil> ciclo = resultado.iterator();
			datos = new Object[resultado.size()][3];
			while(ciclo.hasNext())
			{    			
				xPerfil = ciclo.next();
				datos[i][0] = new String(xPerfil.getCodPerfil());
				datos[i][1] = new String(xPerfil.getDescripcion());
				datos[i][2] = new Boolean(xPerfil.isRegVigente());
				i++;
			}
		}
		else{
			titulos = new String[2];
			titulos[0] = "Código";
			titulos[1] = "Nombre";
			Iterator<Perfil> ciclo = resultado.iterator();
			datos = new Object[resultado.size()][3];
			while(ciclo.hasNext())
			{    			
				xPerfil = ciclo.next();
				datos[i][0] = new String(xPerfil.getCodPerfil());
				datos[i][1] = new String(xPerfil.getDescripcion());
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

		ArrayList<ListaFuncion> funciones = new ArrayList<ListaFuncion>();
		ListaFuncion xMenu = new ListaFuncion();
		ResultSet registros = null;
		String falla = "No se pueden cargar funciones vinculadas al perfil.";

		try {
			//String tiraSql = "SELECT DISTINCT funcionperfil.codfuncion, funcionperfil.codmodulo, funcionperfil.codperfil, funcionperfil.habilitado, funcionperfil.autorizado, modulos.descripcion as modulo, funcion.descripcion as funcion, funcion.reqautorizacion FROM perfil, funcionperfil, funcion, modulos WHERE (funcionperfil.codperfil = perfil.codperfil) AND (funcionperfil.codfuncion = funcion.codfuncion) AND (funcionperfil.codmodulo = modulos.codmodulo) AND (modulos.regvigente = 'S') AND (funcion.regvigente = 'S') AND (funcionperfil.codperfil = "+this.getCodPerfil()+")";
			String tiraSql = "SELECT DISTINCT funcionperfil.codfuncion, funcionperfil.codmodulo, funcionperfil.codperfil, funcionperfil.habilitado, funcionperfil.autorizado FROM funcionperfil inner join perfil on (funcionperfil.codperfil = perfil.codperfil) WHERE (funcionperfil.codperfil = "+this.getCodPerfil()+")";
			registros = MediadorBD.realizarConsulta(tiraSql);
			if (MediadorBD.getFilas() > 0){
				registros.beforeFirst();
				while(registros.next()){
					xMenu = new ListaFuncion();
					boolean habilitado = registros.getString("habilitado").toUpperCase().trim().toUpperCase().equals("S") ? true:false;
					boolean autorizado = registros.getString("autorizado").toUpperCase().trim().toUpperCase().equals("S") ? true:false;
					xMenu.setHabilitado(habilitado);
					xMenu.setAutorizado(autorizado);
					Funcion xFuncion = new Funcion();
					xFuncion.setCodFuncion(registros.getShort("codfuncion"));
					xFuncion.setCodModulo(registros.getShort("codmodulo"));
					xFuncion = (Funcion)xFuncion.obtenerDatos(xFuncion).get(0);
					Modulo xModulo = new Modulo();
					xModulo.setCodModulo(registros.getShort("codmodulo"));
					xModulo.obtenerDatos(xModulo);
					xFuncion.setRaiz(xModulo);
					xMenu.setFuncion(xFuncion);
					funciones.add(xMenu);
				}	
				this.setFunciones(funciones);
			}	
		} catch (SQLException e) {
			logger.error("cargarFunciones()", e);

			throw new BaseDeDatosExcepcion(falla);
		} finally {
			if (registros != null){
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
	 * Método cargarMiembros.
	 * 		Carga los usuarios asignados al perfil.
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	public void cargarMiembros() throws BaseDeDatosExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("cargarMiembros() - start");
		}

		ArrayList<Usuario> miembros = new ArrayList<Usuario>();
		Usuario xUsuario = new Usuario();
		ResultSet registros = null;
		String falla = "No se pueden cargar miembros asignados al perfil.";

		try {
			String tiraSql = "SELECT DISTINCT usuario.numficha, usuario.nombre, usuario.regvigente FROM usuario WHERE (usuario.codperfil = "+this.getCodPerfil()+")";
			registros = MediadorBD.realizarConsulta(tiraSql);
			if (MediadorBD.getFilas() > 0){
				registros.beforeFirst();
				while(registros.next()){
					xUsuario = new Usuario();
					xUsuario.setNumFicha(registros.getString("numficha"));
					xUsuario.setNombre(registros.getString("nombre"));
					if((!xUsuario.getNumFicha().equals("00000000")) && (!xUsuario.getNumFicha().equals("99999999"))){
						try{
							xUsuario.setDatosPersonales(xUsuario.obtenerDatos(xUsuario.getNumFicha()));
						} catch (BaseDeDatosExcepcion ex){
							logger.error("cargarMiembros()", ex);
						} catch (ExcepcionCr ex){
							logger.error("cargarMiembros()", ex);
						}
						miembros.add(xUsuario);
					}
				}	
				this.setMiembros(miembros);
			}	
		} catch (SQLException e) {
			logger.error("cargarMiembros()", e);

			throw new BaseDeDatosExcepcion(falla);
		} finally {
			if (registros != null) {
				try {
					registros.close();
				} catch (SQLException e1) {
					logger.error("cargarMiembros()", e1);
				}
				registros = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cargarMiembros() - end");
		}
	}

	/**
	 * Método quitarFuncion.
	 * 		Recibe el código identificador de la función que se quiere 
	 * desvincular con el actual perfil
	 * @param codFuncion
	 * @throws BaseDeDatosExcepcion
	 */
	public void quitarFuncion(short codFuncion) throws BaseDeDatosExcepcion, ConexionExcepcion{
		if (logger.isDebugEnabled()) {
			logger.debug("quitarFuncion(short) - start");
		}

		String xActualizacion = Control.formatoTiempo.format(Sesion.getTimestampSistema());
		MediadorBD.ejecutar("UPDATE funcionperfil SET codperfil = 0, actualizacion = "+xActualizacion+" WHERE codFuncion = "+codFuncion);

		if (logger.isDebugEnabled()) {
			logger.debug("quitarFuncion(short) - end");
		}
	}

	/**
	 * Devuelve el codPerfil.
	 * @return String - Cadena de 3 caracteres
	 */
	public String getCodPerfil() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCodPerfil() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getCodPerfil() - end");
		}
		return codPerfil;
	}

	/**
	 * Devuelve la descripcion o nombre del grupo de usuario.
	 * @return String - Cadena de 20 caracteres
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
	 * Devuelve la fecha de la última actualización.
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
	 * Devuelve la hora de la última actualización.
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
	 * Devuelve el nivel de auditoria ("1".."5") asignado.
	 * @return String - Cadena de 1 caracter
	 */
	public String getNivelAuditoria() {
		if (logger.isDebugEnabled()) {
			logger.debug("getNivelAuditoria() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getNivelAuditoria() - end");
		}
		return nivelAuditoria;
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
	 * Establece el código identificador del perfil.
	 * @param codPerfil - Cadena de 3 caracteres
	 */
	public void setCodPerfil(String codPerfil) {
		if (logger.isDebugEnabled()) {
			logger.debug("setCodPerfil(String) - start");
		}

		this.codPerfil = codPerfil;

		if (logger.isDebugEnabled()) {
			logger.debug("setCodPerfil(String) - end");
		}
	}

	/**
	 * Establece la descripcion o nombre del grupo de usuario.
	 * @param descripcion - Cadena de 20 caracteres
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
	 * Establece la fecha de la última actualización.
	 * @param fechaActualizacion - Fecha de actualizacion
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
	 * Establece la hora de la última actualización.
	 * @param horaActualizacion - Hora de actualizacion
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
	 * Establece el nivel de auditoria ("1".."5").
	 * @param nivelAuditoria - Cadena de 1 caracter
	 */
	public void setNivelAuditoria(String nivelAuditoria) {
		if (logger.isDebugEnabled()) {
			logger.debug("setNivelAuditoria(String) - start");
		}

		this.nivelAuditoria = nivelAuditoria;

		if (logger.isDebugEnabled()) {
			logger.debug("setNivelAuditoria(String) - end");
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
	public ArrayList<ListaFuncion> getFunciones() throws BaseDeDatosExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("getFunciones() - start");
		}

		if(funciones == null) this.cargarFunciones();

		if (logger.isDebugEnabled()) {
			logger.debug("getFunciones() - end");
		}
		return funciones;
	}

	/**
	 * Devuelve la lista de miembros del sistema asignados al perfil.
	 * @return ArrayList - Listado de objetos Usuario
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	public ArrayList<Usuario> getMiembros() throws BaseDeDatosExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("getMiembros() - start");
		}

		this.cargarMiembros();

		if (logger.isDebugEnabled()) {
			logger.debug("getMiembros() - end");
		}
		return miembros;
	}

	/**
	 * Establece la lista de funciones del sistema vinculadas al perfil.
	 *  @param funciones  - Listado de objetos Funcion
	 */
	public void setFunciones(ArrayList<ListaFuncion> funciones) {
		if (logger.isDebugEnabled()) {
			logger.debug("setFunciones(ArrayList) - start");
		}

		this.funciones = funciones;

		if (logger.isDebugEnabled()) {
			logger.debug("setFunciones(ArrayList) - end");
		}
	}

	/**
	 * Establece la lista de miembros del sistema asignados al perfil.
	 *  @param miembros - Listado de objetos Usuario
	 */
	public void setMiembros(ArrayList<Usuario> miembros) {
		if (logger.isDebugEnabled()) {
			logger.debug("setMiembros(ArrayList) - start");
		}

		this.miembros = miembros;

		if (logger.isDebugEnabled()) {
			logger.debug("setMiembros(ArrayList) - end");
		}
	}

}