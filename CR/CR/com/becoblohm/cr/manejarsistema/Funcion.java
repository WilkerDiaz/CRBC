/**
 * =============================================================================
 * Proyecto   : CRBECOEPA
 * Paquete    : com.becoblohm.cr.manejarusuario
 * Programa   : Funcion.java
 * Creado por : Programador3 - Alexis Gu�dez L�pez
 * Creado en  : 06/10/2003 01:59:52 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versi�n     : 1.1
 * Fecha       : 04/03/2004 01:30 PM
 * Analista    : Programador3 - Alexis Gu�dez L�pez
 * Descripci�n : Reorganizados los trhows para todos los m�todos de modo que no s�lo 
 * 				 arroje una ExcepcionCr sino cada una de las excepciones espec�ficas.
 * =============================================================================
 * Versi�n     : 1.0.1
 * Fecha       : 18/11/2003 03:28:41 PM
 * Analista    : Programador3 - Alexis Gu�dez L�pez
 * Descripci�n : Modificaciones por cambio en el dise�o de la BD para las 
 * 				 entidades Modulo, Funcion y Metodo para EPA y BECO
 * =============================================================================
 */
package com.becoblohm.cr.manejarsistema;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.gui.ModeloTabla;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarusuario.dbbeans.AddFuncion;
import com.becoblohm.cr.manejarusuario.dbbeans.AddFuncionMetodo;
import com.becoblohm.cr.manejarusuario.dbbeans.UpdFuncion;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.utiles.Control;

/** 
 * Descripci�n: 
 * 		Esta clase vincula los valores de los datos de la tabla FuncionPerfil
 * que corresponden al sistema de caja registradora. Tambi�n proporciona la 
 * confirmaci�n de existencia de un usuario indicado, la recuperaci�n de sus 
 * datos y el control de cada una de sus propiedades.
 */
/*
* En esta clase se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
* S�lo se parametriz� el tipo de dato de los distintos ArrayList
* Fecha: agosto 2011
*/
public class Funcion {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Funcion.class);

	private short codFuncion;
	private String descripcion;
	private short codModulo;
	private Modulo raiz;
	private ArrayList<Metodo> metodos;
	private short nuevoModulo;
	private char nivelAuditoria;
	private boolean reqAutorizacion;
	private boolean regVigente;
	private Date fechaActualizacion;
	private Time horaActualizacion;
	
	/**
	 * Constructor para Funcion.
	 */
	public Funcion() {
		this.setNuevoModulo((short)-1);
	}

	/**
	 * M�todo existe.
	 * 		Retorna verdadero si la funci�n est� registrada en la base de 
	 * datos de la Caja Registradora.
	 * @param xFuncion - C�digo identificador de la funci�n
	 * @return boolean - Retorna verdadero si la funci�n est� activa y 
	 * falso en caso contrario.
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr
	 */
	public static boolean existe(Funcion xFuncion) throws BaseDeDatosExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("existe(Funcion) - start");
		}

		ResultSet resultado = null;
		String fallaFuncion = "No pudo cargarse informaci�n de la funci�n.";

		boolean existe = false;
		try {
			resultado = MediadorBD.realizarConsulta("SELECT count(*) as cuantos FROM funcion WHERE descripcion = '"+xFuncion.getDescripcion()+"' OR ((codfuncion = "+xFuncion.getCodFuncion()+")AND(codmodulo = "+xFuncion.getCodModulo()+"))");
			if (resultado.getInt("cuantos") > 0) existe = true;
		} catch (SQLException e) {
			logger.error("existe(Funcion)", e);

			throw new BaseDeDatosExcepcion(fallaFuncion);
		} finally {
			if (resultado != null) {
				try {
					resultado.close();
				} catch (SQLException e1) {
					logger.error("existe(Funcion)", e1);
				}
				resultado = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("existe(Funcion) - end");
		}
		return existe;
	}

	/**
	 * M�todo obtenerDatos.
	 * 		Retorna la informaci�n de la funci�n indicada, registrada en 
	 * la base de datos de Caja Registradora.
	 * @param xFuncion - C�digo identificador de la funci�n a buscar
	 * @return Vector - Objeto vector con los datos de la funci�n solicitada
	 * en la posici�n inicial y en las siguientes posiciones las subfunciones 
	 * asociadas a la funci�n
	 * @throws BaseDeDatosExcepcion
	 * @throws FuncionExcepcion
	 * @throws ExcepcionCr - Arroja una excepci�n de conexi�n/acceso a la
	 * 		base de datos o una de existencia en caso de no existir la 
	 * 		funci�n solicitada
	 */
	/*
	* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
	* S�lo se parametriz� el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Object> obtenerDatos(Funcion xFuncion) throws BaseDeDatosExcepcion, FuncionExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerDatos(Funcion) - start");
		}

		Vector<Object> arbolFuncion = new Vector<Object>();
		ResultSet resultado	= null;
		String fallaFuncion = "No pudo cargarse informaci�n de la funci�n "+xFuncion.getDescripcion()+".";
		String fallaMetodos = "Funci�n: "+xFuncion.getDescripcion()+" no tiene m�todos del sistema asociados - Consulte al Administrador del Sistema.";
		String fallaRegistro = "Funci�n: "+xFuncion.getDescripcion()+" no registrada o no activa en el sistema.";
		
		try {
			resultado = MediadorBD.realizarConsulta("SELECT DISTINCT * FROM funcion WHERE (descripcion = '"+xFuncion.getDescripcion()+"' OR codfuncion = "+xFuncion.getCodFuncion()+") AND codmodulo = "+xFuncion.getCodModulo()+" AND regvigente = '" + Sesion.SI + "'");
			int cuantos = MediadorBD.getFilas();
			if(cuantos == 0){ throw new FuncionExcepcion(fallaRegistro); }
			resultado.beforeFirst();
			while (resultado.next()){
				xFuncion = new Funcion();
				xFuncion.setCodFuncion(resultado.getShort("codfuncion"));
				xFuncion.setCodModulo(resultado.getShort("codmodulo"));
				xFuncion.setDescripcion(resultado.getString("descripcion"));
				xFuncion.setFechaActualizacion(resultado.getDate("actualizacion"));
				xFuncion.setHoraActualizacion(resultado.getTime("actualizacion"));
				nivelAuditoria = resultado.getString("nivelauditoria").toUpperCase().trim().charAt(0);
				xFuncion.setNivelAuditoria(nivelAuditoria);
				boolean regVigente = resultado.getString("regvigente").toUpperCase().equals("S") ? true:false;
				xFuncion.setRegVigente(regVigente);
				boolean reqAutorizacion = resultado.getString("reqautorizacion").toUpperCase().equals("S") ? true:false;
				xFuncion.setReqAutorizacion(reqAutorizacion);
				xFuncion.cargarMetodos();
				if (xFuncion.getMetodos() == null){
					throw new FuncionExcepcion(fallaMetodos);
				}
				arbolFuncion.addElement(xFuncion);
			}
		} catch (FuncionExcepcion e) {
			logger.error("obtenerDatos(Funcion)", e);

			throw new FuncionExcepcion(fallaMetodos);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("obtenerDatos(Funcion)", e);

			throw new FuncionExcepcion(fallaRegistro);
		} catch (SQLException e) {
			logger.error("obtenerDatos(Funcion)", e);

			throw new BaseDeDatosExcepcion(fallaFuncion);
		} finally {
			if (resultado != null) {
				try {
					resultado.close();
				} catch (SQLException e1) {
					logger.error("obtenerDatos(Funcion)", e1);
				}
				resultado = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerDatos(Funcion) - end");
		}
		return arbolFuncion;
	}

	/**
	 * Method actualizarDatos.
	 * 		Recibe una instancia del objeto funci�n con los datos que se 
	 * desean actualizar, en caso de una funci�n registrada efect�a una 
	 * actualizaci�n de lo contrario a�ade un nuevo registro a la tabla 
	 * Funcion de la caja registradora iniciada.
	 * @param xFuncion - Objeto funci�n con los datos que se desean a�adir
	 * 		o actualizar a la tabla Funci�n de la caja registradora
	 * @throws BaseDeDatosExcepcion
	 * @throws FuncionExcepcion
	 * @throws ExcepcionCr - Arroja una excepci�n de conexi�n/acceso a la
	 * 		base de datos en caso de fallar
	 */
	/*
	* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
	* S�lo se eliminaron variables sin uso
	* Fecha: agosto 2011
	*/
	public void actualizarDatos(Funcion xFuncion) throws FuncionExcepcion, ExcepcionCr, BaseDeDatosExcepcion, ConexionExcepcion{
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarDatos(Funcion) - start");
		}

		AddFuncion addFuncion = new AddFuncion();
		UpdFuncion updFuncion = new UpdFuncion();
		ResultSet resultado = null;
		int inc=0;
		String fallaFuncion = "No se pueden actualizar datos de la funci�n.";
		String fallaModulo = "El modulo indicado no ha sido registrado.";
		
		String regVigente = xFuncion.isRegVigente()== true ? "S":"N";
		String reqAutorizacion = xFuncion.isReqAutorizacion()== true ? "S":"N";
		try {
			resultado = MediadorBD.realizarConsulta("SELECT count(*) as cuantos FROM funcion WHERE ((codfuncion = "+xFuncion.getCodFuncion()+") OR (descripcion = '"+xFuncion.getDescripcion()+"')) AND (codmodulo = "+xFuncion.getCodModulo()+")");
			int cuantos = resultado.getInt("cuantos");
			if (cuantos > 0){
				resultado = MediadorBD.realizarConsulta("SELECT * FROM funcion WHERE ((codfuncion = "+xFuncion.getCodFuncion()+") OR (descripcion = '"+xFuncion.getDescripcion()+"')) AND (codmodulo = "+xFuncion.getCodModulo()+")");
				this.setCodFuncion(resultado.getShort("codFuncion"));
				this.setDescripcion(resultado.getString("descripcion"));
				if (xFuncion.getCodModulo() != 0){
					resultado = MediadorBD.realizarConsulta("SELECT count(*) as padre FROM modulos WHERE (codmodulo = "+xFuncion.getCodModulo()+")");
					int padreModulo = resultado.getShort("padre");
					if(!(padreModulo == 1)){
						throw new FuncionExcepcion(fallaModulo);
					}
				}	
				updFuncion.execute(
					(String)xFuncion.getDescripcion(),
					new Short(xFuncion.getCodModulo()),
					String.valueOf(xFuncion.getNivelAuditoria()).toUpperCase().trim(),
					(String)reqAutorizacion,
					(String)regVigente,
					new Short(xFuncion.getCodFuncion()));
				if ((!(xFuncion.isRegVigente()))&&(xFuncion.getCodModulo() == 0)){
					SimpleDateFormat formatoTiempo = new SimpleDateFormat("yyyyMMddHHmmss");
					Timestamp actualizacion = new Timestamp(Calendar.getInstance().getTime().getTime());
					String xActualizacion = formatoTiempo.format(actualizacion);

					MediadorBD.ejecutar("UPDATE funcion SET regvigente = 'N', actualizacion = "+xActualizacion+" WHERE codmodulo = '"+xFuncion.getCodFuncion());
				if (xFuncion.getNuevoModulo() != -1){
					xFuncion.cambiarModulo();
				}
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
				Integer codFuncion = new Integer(inc);
				this.setCodFuncion(codFuncion.shortValue());
				if (xFuncion.getCodModulo() != 0){
					resultado = MediadorBD.realizarConsulta("SELECT count(*) as padre FROM modulos WHERE (modulos.codmodulo = "+xFuncion.getCodModulo()+")");
					int modulo = resultado.getShort("padre");
					resultado = MediadorBD.realizarConsulta("SELECT count(*) as padre FROM funcion, modulos WHERE (funcion.codfuncion = "+xFuncion.getCodModulo()+" AND funcion.codmodulo = "+xFuncion.getCodModulo()+")");
					int funcion = resultado.getShort("padre");
					if(!(modulo == 1)&&!(funcion == 1)){
						throw new FuncionExcepcion(fallaModulo);
					}
				}	
				addFuncion.execute(
					new Short(xFuncion.getCodFuncion()),
					(String)xFuncion.getDescripcion(),
					new Short(xFuncion.getCodModulo()),
					String.valueOf(xFuncion.getNivelAuditoria()).toUpperCase().trim(),
					(String)"S",
					(String)reqAutorizacion);
			}
		} catch (SQLException e) {
			logger.error("actualizarDatos(Funcion)", e);

			throw new BaseDeDatosExcepcion(fallaFuncion);
		} finally {
			if (resultado != null) {
				try {
					resultado.close();
				} catch (SQLException e1) {
					logger.error("actualizarDatos(Funcion)", e1);
				}
				resultado = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarDatos(Funcion) - end");
		}
	}

	/**
	 * M�todo cambiarModulo.
	 * 		Recibe una instancia del objeto funcion con los datos que se desean
	 * actualizar.
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr - Arroja una excepci�n de conexi�n/acceso a la
	 * 		base de datos o una en caso que el usuario que quiera a�adirse no est�
	 * 		registrado como colaborador
	 */
	public void cambiarModulo() throws BaseDeDatosExcepcion, ExcepcionCr, ConexionExcepcion{
		if (logger.isDebugEnabled()) {
			logger.debug("cambiarModulo() - start");
		}

		String falla = "No se pueden actualizar datos de la funci�n.";
		
		try {
			String xActualizacion = Control.formatoTiempo.format(Sesion.getTimestampSistema());
			MediadorBD.ejecutar("UPDATE funcion SET codmodulo = "+this.getNuevoModulo()+", actualizacion = "+xActualizacion+" WHERE (codfuncion="+this.getCodFuncion()+" AND codmodulo="+this.getCodModulo()+")");
			this.setCodModulo(this.getNuevoModulo());
			this.setNuevoModulo((short)-1);

		} catch (BaseDeDatosExcepcion e) {
			logger.error("cambiarModulo()", e);

			throw new BaseDeDatosExcepcion(falla);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cambiarModulo() - end");
		}
	}

	/**
	 * M�todo vincularMetodos.
	 * 		Recibe una lista de objetos Metodo de modo se asignen a la 
	 * funci�n correspondientes
	 * @param metodos - Listado de objetos Metodo
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr
	 */
	/*
	* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
	* S�lo se parametriz� el tipo de dato contenido en los 'Vector'
	* Se eliminaron variables sin uso
	* Fecha: agosto 2011
	*/
	public void vincularMetodos(ArrayList<Metodo> metodos) throws BaseDeDatosExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("vincularMetodos(ArrayList) - start");
		}

		ResultSet resultado = null;
		AddFuncionMetodo addFuncionMetodo = new AddFuncionMetodo();
		//UpdFuncionMetodo updFuncionMetodo = new UpdFuncionMetodo();
		Metodo metodo = new Metodo();
		String fallaFuncionPerfil = "No pudo almacenarse informaci�n a la base de datos.";
		
		try {
			for(int i=0; i<metodos.size(); i++){
				metodo = (Metodo)metodos.get(i);
				short codFuncion = this.getCodFuncion();
				short codModulo = this.getCodModulo();
				short codMetodo = metodo.getCodMetodo();
				resultado = MediadorBD.realizarConsulta("SELECT count(*) as cuantos FROM funcionmetodos WHERE (codfuncion = "+codFuncion+") AND (codmodulo = "+codModulo+") AND (codmetodo = "+codMetodo+")");
				if (resultado.getInt("cuantos") > 0){
					resultado = MediadorBD.realizarConsulta("UPDATE funcionmetodos SET (codmetodo = "+codMetodo+") WHERE (codfuncion = "+codFuncion+") AND (codmodulo = "+codModulo+") AND ");
//					updFuncionMetodo.execute(
//						new Short(codMetodo),
//						new Short(codModulo),
//						new Short(codFuncion));
				}
				else{
					addFuncionMetodo.execute(
						new Short(codMetodo),
						new Short(codModulo),						
						new Short(codFuncion));
				}
			}
		} catch (SQLException e) {
			logger.error("vincularMetodos(ArrayList)", e);

			throw new BaseDeDatosExcepcion(fallaFuncionPerfil);
		} finally {
			if (resultado != null) {
				try {
					resultado.close();
				} catch (SQLException e1) {
					logger.error("vincularMetodos(ArrayList)", e1);
				}
				resultado = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("vincularMetodos(ArrayList) - end");
		}
	}

	/**
	 * M�todo cargarRegistros.
	 * 		Devuelve los datos correspondientes a las funciones registradas.
	 * @param vigentes - Verdadero para filtrar resultados por registros vigentes
	 * @return Vector - Objeto vector contenedor de los objetos Funcion
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr - Arroja excepciones en la conexi�n con la base de datos
	 */
	/*
	* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
	* S�lo se parametriz� el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Funcion> cargarRegistros(boolean vigentes) throws BaseDeDatosExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("cargarRegistros(boolean) - start");
		}

		Vector<Funcion> resultados = new Vector<Funcion>();
		Funcion xFuncion = new Funcion();
		String fallaRegistros = "No pudo obtenerse informaci�n de la base de datos.";
		String tiraSql = new String();
		ResultSet datos = null;
		try {
			if (vigentes){
				tiraSql = "SELECT DISTINCT * FROM funcion WHERE regvigente = '" + Sesion.SI + "'";
			}
			else{
				tiraSql = "SELECT DISTINCT * FROM funcion";
			}	
			datos = MediadorBD.realizarConsulta(tiraSql);
			datos.beforeFirst();
			while (datos.next()){
				xFuncion.setCodFuncion(datos.getShort("codfuncion"));
				xFuncion.setCodModulo(datos.getShort("codmodulo"));
				xFuncion.setDescripcion(datos.getString("descripcion"));
				xFuncion.setFechaActualizacion(datos.getDate("actualizacion"));
				xFuncion.setHoraActualizacion(datos.getTime("actualizacion"));
				xFuncion.setNivelAuditoria(datos.getString("nivelauditoria").toUpperCase().trim().charAt(0));
				boolean regVigente = datos.getString("regvigente").toUpperCase().equals("S") ? true:false;
				xFuncion.setRegVigente(regVigente);
				resultados.addElement(xFuncion);
				xFuncion = new Funcion();
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
	 * M�todo cargarMetodos.
	 * 		Carga el atributo metodos del objeto funcion con el listado de 
	 * metodos y su respectivos estados de la caja (inicial/final).
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr
	 */
	/*
	* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
	* S�lo se parametriz� el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	public void cargarMetodos() throws BaseDeDatosExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("cargarMetodos() - start");
		}

		ArrayList<Metodo> funcionMetodos = new ArrayList<Metodo>();
		Metodo xMetodo = new Metodo();
		ResultSet metodos = null;
		String falla = "No se pueden cargar opciones del men� del usuario.";

		try {
			String tiraSql = "SELECT DISTINCT MT.codmetodo, MT.descripcion FROM metodos as MT, funcionmetodos as FM, modulos as MO, funcion as FU WHERE (FM.codmetodo = MT.codmetodo) AND (FU.regvigente = '" + Sesion.SI + "') AND (MO.regvigente = '" + Sesion.SI + "') AND (FU.codfuncion = "+this.getCodFuncion()+") AND (FU.codmodulo = "+this.getCodModulo()+")";
			metodos = MediadorBD.realizarConsulta(tiraSql);
			if (MediadorBD.getFilas() > 0){
				metodos.beforeFirst();
				while(metodos.next()){
					xMetodo = new Metodo();
					xMetodo.setCodMetodo(metodos.getShort("codmetodo"));
					xMetodo.setDescripcion(metodos.getString("descripcion"));
					funcionMetodos.add(xMetodo);
				}	
				this.setMetodos(funcionMetodos);
			}	
		} catch (SQLException e) {
			logger.error("cargarMetodos()", e);

			throw new BaseDeDatosExcepcion(falla);
		} finally {
			if (metodos != null) {
				try {
					metodos.close();
				} catch (SQLException e1) {
					logger.error("cargarMetodos()", e1);
				}
				metodos = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cargarMetodos() - end");
		}
	}

	/**
	 * M�todo cargarCatalogo.
	 * 		Devuelve los datos correspondientes a la entidad indicada adem�s de los 
	 * t�tulos de las columnas a visualizar.
	 * @param vigentes - Verdadero para filtrar resultados por registros vigentes
	 * @return Catalogo - Objeto catalogo con los datos y t�tulos respectivos
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr - Arroja excepciones en la conexi�n con la base de datos
	 */
	/*
	* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
	* S�lo se parametriz� el tipo de dato contenido en los 'Vector' e 'Iterator'
	* Fecha: agosto 2011
	*/
	public static ModeloTabla cargarCatalogo(boolean vigentes) throws BaseDeDatosExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarCatalogo(boolean) - start");
		}

		Vector<Funcion> resultado = new Vector<Funcion>();
		ModeloTabla xCatalogo = new ModeloTabla();
		Funcion xFuncion = new Funcion();
		String[] titulos;
		Object[][] datos;
		int i = 0;
		
		resultado = cargarRegistros(vigentes);
		if (vigentes == false){
			titulos = new String[4];
			titulos[0] = "C�digo";
			titulos[1] = "Nombre";
			titulos[2] = "Requiere Autorizacion";
			titulos[3] = "Vigente";
			Iterator<Funcion> ciclo = resultado.iterator();
			datos = new Object[resultado.size()][4];
			while(ciclo.hasNext())
			{    			
				xFuncion = (Funcion)ciclo.next();
				datos[i][0] = new Short(xFuncion.getCodFuncion());
				datos[i][1] = new String(xFuncion.getDescripcion());
				datos[i][2] = new Boolean(xFuncion.isReqAutorizacion());
				datos[i][3] = new Boolean(xFuncion.isRegVigente());
				i++;
			}
		}
		else {
			titulos = new String[3];
			titulos[0] = "C�digo";
			titulos[1] = "Nombre";
			titulos[2] = "Requiere Autorizaci�n";
			Iterator<Funcion> ciclo = resultado.iterator();
			datos = new Object[resultado.size()][3];
			while(ciclo.hasNext())
			{    			
				xFuncion = (Funcion)ciclo.next();
				datos[i][0] = new Short(xFuncion.getCodFuncion());
				datos[i][1] = new String(xFuncion.getDescripcion());
				datos[i][2] = new Boolean(xFuncion.isReqAutorizacion());
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
	 * Devuelve el c�digo identificador de la funci�n.
	 * @return short
	 */
	public short getCodFuncion() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCodFuncion() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getCodFuncion() - end");
		}
		return codFuncion;
	}

	/**
	 * Devuelve el c�digo identificador de la funci�n correspondiente 
	 * al m�dulo funcional superior.
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
	 * Devuelve la descripci�n o nombre de la funci�n.
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
	 * Devuelve la fecha (AAAA-MM-DD) de la �ltima actualizaci�n al registro
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
	 * Devuelve la hora (HH:MM:SS) de la �ltima actualizaci�n al registro
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
	 * Establece el c�digo identificador de la funci�n.
	 * @param codFuncion
	 */
	public void setCodFuncion(short codFuncion) {
		if (logger.isDebugEnabled()) {
			logger.debug("setCodFuncion(short) - start");
		}

		this.codFuncion = codFuncion;

		if (logger.isDebugEnabled()) {
			logger.debug("setCodFuncion(short) - end");
		}
	}

	/**
	 * Establece el c�digo identificador de la funci�n correspondiente 
	 * al m�dulo funcional superior.
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
	 * Establece la descripci�n o nombre de la funci�n.
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
	 * Establece la fecha (AAAA-MM-DD) de la �ltima actualizaci�n al registro
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
	 * Establece la hora (HH:MM:SS) de la �ltima actualizaci�n al registro
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
	 * Devuelve el nivel de auditoria ("1".."5") asignado a la funci�n.
	 * @return char
	 */
	public char getNivelAuditoria() {
		if (logger.isDebugEnabled()) {
			logger.debug("getNivelAuditoria() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getNivelAuditoria() - end");
		}
		return nivelAuditoria;
	}

	/**
	 * Establece el nivel de auditoria ("1".."5") asignado a la funci�n.
	 * @param nivelAuditoria The nivelAuditoria to set
	 */
	public void setNivelAuditoria(char nivelAuditoria) {
		if (logger.isDebugEnabled()) {
			logger.debug("setNivelAuditoria(char) - start");
		}

		this.nivelAuditoria = nivelAuditoria;

		if (logger.isDebugEnabled()) {
			logger.debug("setNivelAuditoria(char) - end");
		}
	}

	/**
	 * Devuelve un indicador "S"-Si / "N"-No si la funci�n no permite 
	 * el rol "Supervidor como Cajero" o si. 
	 * @return boolean - Verdadero si es "S", falso si es "N"
	 */
	public boolean isReqAutorizacion() {
		if (logger.isDebugEnabled()) {
			logger.debug("isReqAutorizacion() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isReqAutorizacion() - end");
		}
		return reqAutorizacion;
	}

	/**
	 * Establece un indicador "S"-Si / "N"-No si la funci�n no permite 
	 * el rol "Supervidor como Cajero" o si. 
	 * @param reqAutorizacion - Verdadero si es "S", falso si es "N"
	 */
	public void setReqAutorizacion(boolean reqAutorizacion) {
		if (logger.isDebugEnabled()) {
			logger.debug("setReqAutorizacion(boolean) - start");
		}

		this.reqAutorizacion = reqAutorizacion;

		if (logger.isDebugEnabled()) {
			logger.debug("setReqAutorizacion(boolean) - end");
		}
	}

	/**
	 * Devuelve el valor del c�digo de m�dulo que quiere establecerse.
	 * @return - Valor num�rico
	 */
	public short getNuevoModulo() {
		if (logger.isDebugEnabled()) {
			logger.debug("getNuevoModulo() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getNuevoModulo() - end");
		}
		return nuevoModulo;
	}

	/**
	 * Establece el valor del nuevo c�digo de m�dulo asociado.
	 * @param nuevoModulo - Valor num�rico (short)
	 */
	public void setNuevoModulo(short nuevoModulo) {
		if (logger.isDebugEnabled()) {
			logger.debug("setNuevoModulo(short) - start");
		}

		this.nuevoModulo = nuevoModulo;

		if (logger.isDebugEnabled()) {
			logger.debug("setNuevoModulo(short) - end");
		}
	}

	/**
	 * Devuelve la lista de metodos asociados a la funci�n.
	 * @return ArrayList - Listado de objetos Metodo
	 */
	/*
	* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
	* S�lo se parametriz� el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	public ArrayList<Metodo> getMetodos() {
		if (logger.isDebugEnabled()) {
			logger.debug("getMetodos() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getMetodos() - end");
		}
		return metodos;
	}

	/**
	 * Establece la lista de metodos asociados a la funci�n.
	 * @param metodos - Listado de objetos Metodo
	 */
	/*
	* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
	* S�lo se parametriz� el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	public void setMetodos(ArrayList<Metodo> metodos) {
		if (logger.isDebugEnabled()) {
			logger.debug("setMetodos(ArrayList) - start");
		}

		this.metodos = metodos;

		if (logger.isDebugEnabled()) {
			logger.debug("setMetodos(ArrayList) - end");
		}
	}

	/**
	 * Devuelve la instancia de la clase Modulo correspondiente.
	 * @return Modulo
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr
	 */
	public Modulo getRaiz() throws BaseDeDatosExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("getRaiz() - start");
		}

		Modulo xModulo = new Modulo();
		ResultSet modulo = null;
		String falla = "No se pueden cargar datos del m�dulo contenedor de la funci�n.";

		try {
			String tiraSql = "SELECT DISTINCT * FROM modulos WHERE (codmodulo = "+this.getCodModulo()+") AND (modulos.regvigente = '" + Sesion.SI + "')";
			modulo = MediadorBD.realizarConsulta(tiraSql);
			if (MediadorBD.getFilas() > 0){
				modulo.beforeFirst();
				while(modulo.next()){
					xModulo = new Modulo();
					boolean regVigente = modulo.getString("regvigente").toUpperCase().trim().equals("S") ? true:false;
					xModulo.setCodModulo(modulo.getShort("codmodulo"));
					xModulo.setDescripcion(modulo.getString("descripcion"));
					xModulo.setRegVigente(regVigente);
				}	
				this.setRaiz(xModulo);
			}	
		} catch (BaseDeDatosExcepcion e) {
			logger.error("getRaiz()", e);

			throw new BaseDeDatosExcepcion(falla);
		} catch (SQLException e) {
			logger.error("getRaiz()", e);

			throw new BaseDeDatosExcepcion(falla);
		} finally {
			if (modulo != null) {
				try {
					modulo.close();
				} catch (SQLException e1) {
					logger.error("getRaiz()", e1);
				}
				modulo = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getRaiz() - end");
		}
		return raiz;
	}

	/**
	 * Establece la instancia de la clase Modulo correspondiente.
	 * @param raiz - Instancia de la clase Modulo
	 */
	public void setRaiz(Modulo raiz) {
		if (logger.isDebugEnabled()) {
			logger.debug("setRaiz(Modulo) - start");
		}

		this.raiz = raiz;

		if (logger.isDebugEnabled()) {
			logger.debug("setRaiz(Modulo) - end");
		}
	}

}