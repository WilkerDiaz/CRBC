/**
 * =============================================================================
 * Proyecto   : CRBECOEPA
 * Paquete    : com.becoblohm.cr.manejarusuario
 * Programa   : Metodo.java
 * Creado por : Programador3 - Alexis Gu�dez L�pez
 * Creado en  : 18/11/2003 01:41:45 PM
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
 */
package com.becoblohm.cr.manejarsistema;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.ValidarExcepcion;
import com.becoblohm.cr.gui.ModeloTabla;
import com.becoblohm.cr.manejarusuario.dbbeans.AddMetodo;
import com.becoblohm.cr.manejarusuario.dbbeans.UpdMetodo;
import com.becoblohm.cr.mediadoresbd.MediadorBD;

/** 
 * Descripci�n: 
 * 		Esta clase vincula los valores de los datos de la tabla Perfil que
 * corresponde a los grupos de usuarios con acceso al sistema. Tambi�n 
 * proporciona m�todos que permiten la confirmaci�n de existencia de un 
 * perfil indicado, la recuperaci�n de sus datos desde la base de datos y 
 * el control de cada una de sus propiedades.
 */

public class Metodo {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Metodo.class);

	private short codMetodo;
	private String descripcion;

	/**
	 * Constructor para Perfil.
	 */
	public Metodo(){
		this.setCodMetodo((short)0);
		this.setDescripcion("");
	}

	/**
	 * M�todo obtenerDatos.
	 * 		Retorna la informaci�n del m�todo indicado, registrado en la 
	 * base de datos de Caja Registradora.
	 * @param xMetodo - C�digo identificador del m�todo a buscar
	 * @return Metodo - Objeto m�todo con los datos respectivos
	 * @throws BaseDeDatosExcepcion
	 * @throws ValidarExcepcion
	 * @throws ExcepcionCr - Arroja una excepci�n de conexi�n/acceso a la
	 * 		base de datos o una de existencia en caso de no existir el m�todo
	 * 		solicitado
	 */
	public Metodo obtenerDatos(Metodo xMetodo) throws BaseDeDatosExcepcion, ValidarExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerDatos(Metodo) - start");
		}

		ResultSet resultado	= null;
		String fallaMetodo = "No pudo cargarse informaci�n del m�todo.";
		String fallaRegistro = "M�todo no registrado o no activo en el sistema.";
		
		try {
			resultado = MediadorBD.realizarConsulta("SELECT * FROM metodos WHERE descripcion = '"+xMetodo.getDescripcion().toUpperCase()+"' OR codMetodo = "+xMetodo.getCodMetodo());
			int cuantos = MediadorBD.getFilas();
			if(cuantos == 0){ throw new ValidarExcepcion(fallaRegistro); }
			resultado.beforeFirst();
			while (resultado.next()){
				xMetodo.setCodMetodo(resultado.getShort("codmetodo"));
				xMetodo.setDescripcion(resultado.getString("descripcion"));
			}
		} catch (SQLException e) {
			logger.error("obtenerDatos(Metodo)", e);

			throw new BaseDeDatosExcepcion(fallaMetodo);
		} finally {
			if (resultado != null) {
				try {
					resultado.close();
				} catch (SQLException e1) {
					logger.error("obtenerDatos(Metodo)", e1);
				}
				resultado = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerDatos(Metodo) - end");
		}
		return xMetodo;
	}

	/**
	 * M�todo existe.
	 * 		Retorna verdadero si esta registrado en la tabla Metodo de la 
	 * caja registradora el m�todo solicitado.
	 * @param xMetodo
	 * @return boolean - Verdadero si esta registrado de lo contrario falso
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr - Arroja una excepci�n de conexi�n/acceso a la
	 * 		base de datos en caso de fallar
	 */
	public static boolean existe(Metodo xMetodo) throws BaseDeDatosExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("existe(Metodo) - start");
		}

		ResultSet resultado = null;
		String fallaMetodo = "No pudo cargarse informaci�n del m�todo.";

		boolean existe = false;
		try {
			resultado = MediadorBD.realizarConsulta("SELECT count(*) as cuantos FROM metodos WHERE descripcion = '"+xMetodo.getDescripcion()+"' OR codmetodo = "+xMetodo.getCodMetodo());
			if (resultado.getInt("cuantos") > 0) existe = true;
		} catch (SQLException e) {
			logger.error("existe(Metodo)", e);

			throw new BaseDeDatosExcepcion(fallaMetodo);
		} finally {
			if (resultado != null) {
				try {
					resultado.close();
				} catch (SQLException e1) {
					logger.error("existe(Metodo)", e1);
				}
				resultado = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("existe(Metodo) - end");
		}
		return existe;
	}

	/**
	 * Method actualizarDatos.
	 * 		Recibe una instancia del objeto m�todo con los datos que se 
	 * desean actualizar, en caso de un m�todo registrado efect�a una 
	 * actualizaci�n de lo contrario a�ade un nuevo registro a la tabla 
	 * Metodo de la caja registradora iniciada.
	 * @param xMetodo - Objeto m�todo con los datos que se desean a�adir
	 * 		o actualizar a la tabla Metodo de la caja registradora
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr - Arroja una excepci�n de conexi�n/acceso a la
	 * 		base de datos en caso de fallar
	 */
	public void actualizarDatos(Metodo xMetodo) throws BaseDeDatosExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarDatos(Metodo) - start");
		}

		AddMetodo addMetodo = new AddMetodo();
		UpdMetodo updMetodo = new UpdMetodo();
		ResultSet resultado = null;
		int inc=0;
		String fallaMetodo = "No se pueden actualizar datos del m�todo.";
		
		try {
			resultado = MediadorBD.realizarConsulta("SELECT *,count(*) as cuantos FROM metodos WHERE codmetodo = "+xMetodo.getCodMetodo()+" OR descripcion = '"+xMetodo.getDescripcion().toUpperCase()+"' GROUP BY codmetodo");
			int cuantos = resultado.getInt("cuantos");
			if (cuantos > 0){
				this.setCodMetodo(resultado.getShort("codMetodo"));
				this.setDescripcion(resultado.getString("descripcion"));
				updMetodo.execute(
					new Short(xMetodo.getCodMetodo()),
					(String)xMetodo.getDescripcion().toUpperCase());
			}
			else
			{
				resultado = MediadorBD.realizarConsulta("SELECT MAX(codmetodo) as codMaximo FROM metodos");
				int codigo = resultado.getInt("codMaximo");
				inc = codigo;
				inc = inc +1;
				Integer codMetodo = new Integer(inc);
				this.setCodMetodo(codMetodo.shortValue());
				addMetodo.execute(
					new Short(xMetodo.getCodMetodo()),
					(String)xMetodo.getDescripcion().toUpperCase());
			}
		} catch (SQLException e) {
			logger.error("actualizarDatos(Metodo)", e);

			throw new BaseDeDatosExcepcion(fallaMetodo);
		} finally {
			if (resultado != null) {
				try {
					resultado.close();
				} catch (SQLException e1) {
					logger.error("actualizarDatos(Metodo)", e1);
				}
				resultado = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarDatos(Metodo) - end");
		}
	}
	
	/**
	 * M�todo cargarRegistros.
	 * 		Devuelve los datos correspondientes a los m�todos registrados.
	 * @return Vector - Objeto vector contenedor de los objetos Metodo
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr - Arroja excepciones en la conexi�n con la base de datos
	 */
	/*
	* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
	* S�lo se parametriz� el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Metodo> cargarRegistros() throws BaseDeDatosExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("cargarRegistros() - start");
		}

		Vector<Metodo> resultados = new Vector<Metodo>();
		Metodo xMetodo = new Metodo();
		String fallaRegistros = "No pudo obtenerse informaci�n de la base de datos.";
		String tiraSql = new String();
		ResultSet datos = null;
		try {
			tiraSql = "SELECT DISTINCT * FROM metodos";
			datos = MediadorBD.realizarConsulta(tiraSql);
			datos.beforeFirst();
			while (datos.next()){
				xMetodo.setCodMetodo(datos.getShort("codmetodo"));
				xMetodo.setDescripcion(datos.getString("descripcion"));
				resultados.addElement(xMetodo);
				xMetodo = new Metodo();
			}
		} catch (SQLException e) {
			logger.error("cargarRegistros()", e);

			throw new BaseDeDatosExcepcion(fallaRegistros);
		} finally {
			if (datos != null) {
				try {
					datos.close();
				} catch (SQLException e1) {
					logger.error("cargarRegistros()", e1);
				}
				datos = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cargarRegistros() - end");
		}
		return resultados;
	}

	/**
	 * M�todo cargarCatalogo.
	 * 		Devuelve los datos correspondientes a la entidad indicada adem�s de los 
	 * t�tulos de las columnas a visualizar.
	 * @return Catalogo - Objeto catalogo con los datos y t�tulos respectivos
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr - Arroja excepciones en la conexi�n con la base de datos
	 */
	/*
	* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
	* S�lo se parametriz� el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static ModeloTabla cargarCatalogo() throws BaseDeDatosExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarCatalogo() - start");
		}

		Vector<Metodo> resultado = new Vector<Metodo>();
		ModeloTabla xCatalogo = new ModeloTabla();
		Metodo xMetodo = new Metodo();
		int i = 0;
		
		resultado = cargarRegistros();
		String[] titulos = new String[2];
		titulos[0] = "C�digo";
		titulos[1] = "Nombre";
		Iterator<Metodo> ciclo = resultado.iterator();
		Object[][] datos = new Object[resultado.size()][3];
		while(ciclo.hasNext())
		{    			
			xMetodo = ciclo.next();
			datos[i][0] = new Short(xMetodo.getCodMetodo());
			datos[i][1] = new String(xMetodo.getDescripcion());
			i++;
		}
		xCatalogo.setTitulos(titulos);
		xCatalogo.setDatos(datos);

		if (logger.isDebugEnabled()) {
			logger.debug("cargarCatalogo() - end");
		}
		return xCatalogo;
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
	 * Establece la descripcion o nombre del m�todo.
	 * @return String - Cadena de 20 caracteres m�ximo
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
	 * Establece el identificador del m�todo.
	 * @param codMetodo - Valor num�rico (short)
	 */
	public void setCodMetodo(short codMetodo) {
		if (logger.isDebugEnabled()) {
			logger.debug("setCodMetodo(short) - start");
		}

		this.codMetodo = codMetodo;

		if (logger.isDebugEnabled()) {
			logger.debug("setCodMetodo(short) - end");
		}
	}

	/**
	 * Devuelve el identificador del m�todo.
	 * @return - Valor num�rico (short)
	 */
	public short getCodMetodo() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCodMetodo() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getCodMetodo() - end");
		}
		return codMetodo;
	}

}
