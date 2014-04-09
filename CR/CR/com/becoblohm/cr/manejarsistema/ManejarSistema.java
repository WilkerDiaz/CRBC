/**
 * =============================================================================
 * Proyecto   : CRBECOEPA
 * Paquete    : com.becoblohm.cr.manejarsistema
 * Programa   : ManejarSistema.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 20/11/2003 08:45:56 AM
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
 * Fecha       : 11/02/2003 11:01:41 AM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Modificaciones para adaptación al formato de auditoría establecido. 
 * =============================================================================
 */
package com.becoblohm.cr.manejarsistema;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.PerfilExcepcion;
import com.becoblohm.cr.excepciones.PerfilUsrExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.excepciones.ValidarExcepcion;
import com.becoblohm.cr.gui.ModeloTabla;
import com.becoblohm.cr.manejadorauditoria.Auditoria;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarusuario.Colaborador;
import com.becoblohm.cr.manejarusuario.Perfil;
import com.becoblohm.cr.manejarusuario.Usuario;

/** 
 * Descripción: 
 * 		Esta clase controla la funcionalidad de los componentes del paquete
 * "manejarsistema" permitiendo la interfaz con la Máquina de Estados del
 * Sistema de Caja Registradora.
 */

public class ManejarSistema {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ManejarSistema.class);

	/**
	 * Constructor for ManejarSistema.
	 */
	public ManejarSistema() {
	}

	/**
	 * Método actualizarFuncion.
	 * 		Ejecuta la actualización/inserción de una función.
	 * @param xFuncion - Instancia del objeto funcion con los datos indicados.
	 * @return boolean - Verdadero si la función estaba registrado
	 * @throws BaseDeDatosExcepcion
	 * @throws FuncionExcepcion
	 * @throws ExcepcionCr - Arroja excepciones en la conexión con la base de
	 * 		datos, la no validez de la función indicada
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	public static boolean actualizarFuncion(Funcion xFuncion, ArrayList<Metodo> xMetodos) throws BaseDeDatosExcepcion, FuncionExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarFuncion(Funcion, ArrayList) - start");
		}

		boolean existe = false;
		Sesion.setUbicacion("SEGURIDAD", "actualizarFuncion");
		String mensaje;
		
		existe = Funcion.existe(xFuncion);
		xFuncion.actualizarDatos(xFuncion);
		xFuncion.vincularMetodos(xMetodos);
		if (existe){
			mensaje = new String("Actualizada Función ");
		} else mensaje = new String("Creada Función ");
		Auditoria.registrarAuditoria(mensaje+xFuncion.getDescripcion()+" - Identificador "+xFuncion.getCodFuncion()+" en el módulo "+xFuncion.getRaiz().getCodFuncion(),'T');
		if (xMetodos.size() > 0){
			Iterator<Metodo> ciclo = xMetodos.iterator();
			while (ciclo.hasNext()){
				Metodo metodo = ciclo.next();
				Auditoria.registrarAuditoria("Método "+metodo.getDescripcion()+" - Identificador "+xFuncion.getCodFuncion()+" vinculado a la función "+xFuncion.getCodFuncion(),'T');
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarFuncion(Funcion, ArrayList) - end");
		}
		return existe;
	}

	/**
	 * Método actualizarModulo.
	 * 		Ejecuta la actualización/inserción de un módulo.
	 * @param xModulo - Instancia del objeto módulo con los datos indicados.
	 * @return boolean - Verdadero si el módulo estaba registrado
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr - Arroja excepciones en la conexión con la base de
	 * 		datos, la no validez del módulo indicado
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList e Iterator
	* Fecha: agosto 2011
	*/
	public static boolean actualizarModulo(Modulo xModulo, ArrayList<Funcion> xFunciones) throws BaseDeDatosExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarModulo(Modulo, ArrayList) - start");
		}

		boolean existe = false;
		Sesion.setUbicacion("SEGURIDAD", "actualizarModulo");
		String mensaje;
		
		existe = Modulo.existe(xModulo);
		xModulo.actualizarDatos(xModulo);
		xModulo.vincularFunciones(xFunciones);
		if (existe){
			mensaje = new String("Actualizado Módulo ");
		} else mensaje = new String("Creado Módulo ");
		Auditoria.registrarAuditoria(mensaje+xModulo.getDescripcion()+" - Identificador "+xModulo.getCodModulo(),'T');
		if (xFunciones.size() > 0){
			Iterator<Funcion> ciclo = xFunciones.iterator();
			while (ciclo.hasNext()){
				Funcion funcion = (Funcion)ciclo.next();
				Auditoria.registrarAuditoria("Función "+funcion.getDescripcion()+" vinculada al módulo "+xModulo.getCodFuncion(),'T');
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarModulo(Modulo, ArrayList) - end");
		}
		return existe;
	}

	/**
	 * Método actualizarMetodo.
	 * 		Ejecuta la actualización/inserción de un método.
	 * @param xMetodo - Instancia del objeto método con los datos indicados.
	 * @return boolean - Verdadero si el método estaba registrado
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr - Arroja excepciones en la conexión con la base de
	 * 		datos, la no validez del método indicado
	 */
	public static boolean actualizarMetodo(Metodo xMetodo) throws BaseDeDatosExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarMetodo(Metodo) - start");
		}

		boolean existe = false;
		Sesion.setUbicacion("SEGURIDAD", "actualizarMetodo");
		String mensaje;
		
		existe = Metodo.existe(xMetodo);
		xMetodo.actualizarDatos(xMetodo);
		if (existe){
			mensaje = new String("Actualizado método ");
		} else mensaje = new String("Registrado método ");
		Auditoria.registrarAuditoria(mensaje+xMetodo.getDescripcion()+" - Identificador "+xMetodo.getCodMetodo(),'T');

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarMetodo(Metodo) - end");
		}
		return existe;
	}
	
	/**
	 * Método cargarRegistros.
	 * 		Devuelve los datos registrados correspondientes a la entidad (Funcion, Perfil, 
	 * Usuario) indicada.
	 * @param entidad - Instancia del objeto correspondiente
	 * @param vigentes - Verdadero para filtrar resultados por registros vigentes
	 * @return Vector - Objeto vector contenedor de los registros de la entidad dada
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr - Arroja excepciones en la conexión con la base de
	 * 		datos, la no validez de la entidad indicada
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<?> cargarRegistros(Object entidad, boolean vigentes) throws BaseDeDatosExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarRegistros(Object, boolean) - start");
		}

		Vector<?> resultados = new Vector<Object>();
		Sesion.setUbicacion("SEGURIDAD", "cargarRegistros");
		String mensaje = new String();
		
		if (entidad instanceof Funcion){
			mensaje = new String("Funciones ");
			resultados = Funcion.cargarRegistros(vigentes);
		}
		else if (entidad instanceof Modulo){
			mensaje = new String("Módulos ");
			resultados = Modulo.cargarRegistros(vigentes);
		}
		else if (entidad instanceof Metodo){
			mensaje = new String("Métodos ");
			resultados = Metodo.cargarRegistros();
		}
		else if (entidad instanceof Perfil){
			mensaje = new String("Perfiles ");
			resultados = Perfil.cargarRegistros(vigentes);
		}
		else if (entidad instanceof Usuario){
			mensaje = new String("Usuarios ");
			resultados = Usuario.cargarRegistros(vigentes);
		}		
		else if (entidad instanceof Colaborador){
			mensaje = new String("Colaboradores ");
			resultados = Usuario.cargarRegistros(vigentes);
		}		
		String msgVigentes = vigentes == true ? "Vigentes ":"No Vigentes ";
		Auditoria.registrarAuditoria("Cargando registros de "+mensaje+msgVigentes,'T');

		if (logger.isDebugEnabled()) {
			logger.debug("cargarRegistros(Object, boolean) - end");
		}
		return resultados;
	}

	/**
	 * Método consultarDatos.
	 * 		Devuelve los datos correspondientes a la entidad indicada.
	 * @param entidad - Instancia (Funcion, Perfil, Usuario)con los criterios de 
	 * 		búsqueda(código o descripción).
	 * @return Vector - Objeto vector contenedor de los coincidentes
	 * @throws BaseDeDatosExcepcion
	 * @throws FuncionExcepcion
	 * @throws PerfilUsrExcepcion
	 * @throws PerfilExcepcion
	 * @throws ValidarExcepcion
	 * @throws UsuarioExcepcion
	 * @throws ExcepcionCr - Arroja excepciones en la conexión con la base de
	 * 		datos, la no validez de la entidad indicada
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Object> consultarDatos(Object entidad) throws BaseDeDatosExcepcion, FuncionExcepcion, PerfilUsrExcepcion, PerfilExcepcion, ValidarExcepcion, UsuarioExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("consultarDatos(Object) - start");
		}
		
		Vector<Object> resultado = new Vector<Object>();
		if (entidad instanceof Funcion){
			Funcion xFuncion = (Funcion)entidad;
			resultado = xFuncion.obtenerDatos(xFuncion);
		}
		else if (entidad instanceof Modulo){
			Modulo xModulo = (Modulo)entidad;
			resultado.add(0, xModulo.obtenerDatos(xModulo));
		}
		else if (entidad instanceof Metodo){
			Metodo xMetodo = (Metodo)entidad;
			resultado.add(0, xMetodo.obtenerDatos(xMetodo));
		}
		else if (entidad instanceof Perfil){
			Perfil xPerfil = (Perfil)entidad;
			resultado.add(0, xPerfil.obtenerDatos(xPerfil));
		}
		else if (entidad instanceof Usuario){
			Usuario xUsuario = (Usuario)entidad;
			String login = new String();
			if (xUsuario.getNumFicha() != null){
				login = new String(xUsuario.getNumFicha());
			}
			else login = new String(xUsuario.getNumFicha(xUsuario.getCodigoBarra()));
			resultado.add(0, xUsuario.identificar(login,xUsuario.getClave()));
		}
		else if (entidad instanceof Colaborador){
			Colaborador xColaborador = (Colaborador)entidad;
			resultado.add(0, xColaborador.obtenerDatos(xColaborador.getNumFicha()));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("consultarDatos(Object) - end");
		}
		return resultado;
	}

	/**
	 * Método cargarCatalogo.
	 * 		Devuelve los datos correspondientes a la entidad indicada además de los 
	 * títulos de las columnas a visualizar.
	 * @param entidad - Instancia con los criterios de búsqueda(código o descripción).
	 * @return Catalogo - Objeto catalogo con los datos y títulos respectivos
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr - Arroja excepciones en la conexión con la base de
	 * 		datos, la no validez de la entidad indicada
	 */
	public static ModeloTabla cargarCatalogo(Object entidad, boolean vigentes) throws BaseDeDatosExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarCatalogo(Object, boolean) - start");
		}
		
		ModeloTabla xCatalogo = new ModeloTabla();
		Sesion.setUbicacion("SEGURIDAD", "cargarCatalogo");
		String mensaje = new String();

		if (entidad instanceof Funcion){
			mensaje = new String("Funciones ");
			xCatalogo = Funcion.cargarCatalogo(vigentes);
		}
		else if (entidad instanceof Modulo){
			mensaje = new String("Módulos ");
			xCatalogo = Modulo.cargarCatalogo(vigentes);
		}
		else if (entidad instanceof Metodo){
			mensaje = new String("Métodos ");
			xCatalogo = Metodo.cargarCatalogo();
		}
		else if (entidad instanceof Perfil){
			mensaje = new String("Perfiles ");
			xCatalogo = Perfil.cargarCatalogo(vigentes);
		}
		else if (entidad instanceof Usuario){
			mensaje = new String("Usuarios ");
			xCatalogo = Usuario.cargarCatalogo(vigentes);
		}		
		Auditoria.registrarAuditoria("Consultando registros de "+mensaje,'T');

		if (logger.isDebugEnabled()) {
			logger.debug("cargarCatalogo(Object, boolean) - end");
		}
		return xCatalogo;
	}

}