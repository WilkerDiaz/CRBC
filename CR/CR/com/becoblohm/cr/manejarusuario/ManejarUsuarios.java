/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.manejarusuario
 * Programa   : ManejarUsuarios.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 06/10/2003 05:18:56 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.3
 * Fecha       : 04/03/2004 01:30 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Reorganizados los trhows para todos los métodos de modo que no sólo 
 * 				 arroje una ExcepcionCr sino cada una de las excepciones específicas.
 * =============================================================================
 * Versión     : 1.2 (según CVS)
 * Fecha       : 11/02/2003 03:28:41 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Modificaciones para adaptación al formato de auditoría establecido. 
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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.manejadorauditoria.Auditoria;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarsistema.ManejarSistema;
import com.becoblohm.cr.mediadoresbd.MediadorBD;

/** 
 * Descripción: 
 * 		Esta clase controla la funcionalidad de los componentes del paquete
 * "manejarusuarios" permitiendo la interfaz con la Máquina de Estados del
 * Sistema de Caja Registradora.
 */

public class ManejarUsuarios {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ManejarUsuarios.class);

	/**
	 * Constructor for ManejarUsuarios.
	 */
	public ManejarUsuarios() {
	}

	/**
	 * Método actualizarPerfil.
	 * 		Ejecuta la actualización/inserción de un perfil.
	 * @param xPerfil - Instancia de la clase perfil con los datos indicados.
	 * @param xMenu - Listado de instancia de la clase ListaFuncion.
	 * @param xMiembros - Listado de instancia de la clase Usuarios.
	 * @return boolean - Verdadero si el perfil estaba registrado
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr - Arroja excepciones en la conexión con la base de
	 * 		datos
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	public static boolean actualizarPerfil(Perfil xPerfil, 
			ArrayList<ListaFuncion> xMenu, ArrayList<Usuario> xMiembros) throws 
			BaseDeDatosExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger
					.debug("actualizarPerfil(Perfil, ArrayList, ArrayList) - start");
		}

		boolean existe = false;
		Sesion.setUbicacion("SEGURIDAD","actualizarPerfil");
		String mensaje;
		
		existe = Perfil.existe(xPerfil.getDescripcion());
		xPerfil.actualizarDatos(xPerfil);
		xPerfil.vincularFunciones(xMenu);
		xPerfil.agregarMiembros(xMiembros);
		if (existe){
			mensaje = new String("Actualizado el perfil ");
		} else mensaje = new String("Creado el perfil ");
		Auditoria.registrarAuditoria(mensaje+xPerfil.getDescripcion()+" - Identificador "+xPerfil.getCodPerfil(),'T');
		if (xMenu.size() > 0){
			Iterator<ListaFuncion> ciclo = xMenu.iterator();
			while (ciclo.hasNext()){
				ListaFuncion menu = (ListaFuncion)ciclo.next();
				Auditoria.registrarAuditoria("Función "+menu.getFuncion().getCodFuncion()+" del módulo "+menu.getFuncion().getRaiz().getCodFuncion()+" vinculada al perfil "+xPerfil.getCodPerfil(),'T');
			}
		}
		if (xMiembros.size() > 0){
			Iterator<Usuario> ciclo = xMiembros.iterator();
			while (ciclo.hasNext()){
				Usuario usuario = (Usuario)ciclo.next();
				Auditoria.registrarAuditoria("Usuario #"+usuario.getNumFicha()+" agregado al perfil "+xPerfil.getCodPerfil(),'T');
			}
		}

		if (logger.isDebugEnabled()) {
			logger
					.debug("actualizarPerfil(Perfil, ArrayList, ArrayList) - end");
		}
		return existe;
	}

	/**
	 * Método actualizarUsuario.
	 * 		Ejecuta la actualización/inserción de un usuario.
	 * @param xUsuario - Instancia del objeto usuario con los datos indicados.
	 * @return boolean - Verdadero si el usuario estaba registrado
	 * @throws BaseDeDatosExcepcion
	 * @throws UsuarioExcepcion
	 * @throws ExcepcionCr - Arroja excepciones en la conexión con la base de
	 * 		datos, la no validez del perfil indicado o sobre la no validez del
	 * 		colaborador
	 */
	public static boolean actualizarUsuario(Usuario xUsuario) throws BaseDeDatosExcepcion, UsuarioExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarUsuario(Usuario) - start");
		}

		boolean existe = false;
		Sesion.setUbicacion("SEGURIDAD", "actualizarUsuario");
		String mensaje;

		existe = xUsuario.existe(xUsuario.getNumTienda(),xUsuario.getNumFicha());
		xUsuario.actualizarDatos(xUsuario);
		if (existe){
			mensaje = new String("Actualizado usuario #");
		} else mensaje = new String("Registrado usuario #");
			Auditoria.registrarAuditoria(mensaje+xUsuario.getNumFicha()+", "+xUsuario.getNombre()+", Perfil "+xUsuario.getCodPerfil(),'T');

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarUsuario(Usuario) - end");
		}
		return existe;
	}

	/**
	 * Método cambiarClave.
	 * 		Cambia la contraseña de acceso del usuario por voluntad propia
	 * o por exigencia del sistema.
	 * @param xUsuario - Instancia de la clase usuario con los datos 
	 * 		requeridos
	 * @throws UsuarioExcepcion
	 * @throws ExcepcionCr - Arroja excepciones en la conexión con la 
	 * 		base de	datos o por la no validez de la clave de acceso
	 */
	public static boolean cambiarClave(Usuario xUsuario) throws UsuarioExcepcion, ConexionExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("cambiarClave(Usuario) - start");
		}

		ExcepcionCr fallaUsuario = new ExcepcionCr("No pudo cambiarse la clave. Clave incorrecta");
		boolean cambioOk = true;
		Sesion.setUbicacion("SEGURIDAD", "cambiarClave");

		try {
			if ((Sesion.usuarioActivo.getClave().equals(xUsuario.getClave())) && (Sesion.usuarioActivo.isPuedeCambiarClave())){
				Sesion.usuarioActivo.setNuevaClave(xUsuario.getNuevaClave());
				Sesion.usuarioActivo.cambiarClave();
				Auditoria.registrarAuditoria("Cambiada contraseña de usuario #"+Sesion.usuarioActivo.getNumFicha(),'T');
			} else throw(fallaUsuario);
		}
		catch (UsuarioExcepcion e) {
			logger.error("cambiarClave(Usuario)", e);

			throw e;
		}
		catch (ExcepcionCr e) {
			logger.error("cambiarClave(Usuario)", e);

			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cambiarClave(Usuario) - end");
		}
		return cambioOk;
	}	

	/**
	 * Método validarCodigoUsuario.
	 * 		Verifica la validez del código de barra de ingreso al sistema.
	 * @param codigoBarra - Código de barra a buscar (Cadena de 13 caracteres)
	 * @throws UsuarioExcepcion
	 * @throws ExcepcionCr - Arroja excepciones en la conexión con la base de
	 * 		datos o por la no validez del código indicado
	 */
	public static void validarCodigoUsuario(String codigoBarra, boolean autorizacion, String clave) throws UsuarioExcepcion, ConexionExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("validarCodigoUsuario(String) - start");
		}

		Usuario xUsuario = new Usuario();
		Sesion.setUbicacion("SEGURIDAD", "validarUsuario");
		
		try {
			xUsuario.setCodigoBarra(codigoBarra);
			String ficha = xUsuario.getNumFicha(codigoBarra);
			if (ficha != null) {
				xUsuario.setNumFicha(ficha); 
			} else 
			{
				throw new UsuarioExcepcion("Usuario no identificado");
			}
			boolean usuarioValido = xUsuario.validar(xUsuario.getCodigoBarra(), clave);
			Auditoria.registrarAuditoria("Identificando Usuario: "+xUsuario.getNumFicha(),'O');
			
			if (!autorizacion) {
				//Arreglo para verificar si existe el mismo usuario logueado en otra máquina
				Sesion.chequearLineaCaja();
				if (Sesion.isCajaEnLinea()) {
					String sentencia = "select count(*) as cuantos from caja where caja.codusuario='" + xUsuario.getNumFicha() + "' and caja.numcaja <> " + Sesion.getCaja().getNumero();
					ResultSet resultado = MediadorBD.realizarConsulta(sentencia,false);
					try {
						if(resultado.getInt("cuantos") > 0) //El usuario se encuentra logueado en otra caja
							throw new UsuarioExcepcion("El usuario se encuentra validado en otra caja. Verifique.");
					} catch (SQLException e1) {
						logger.error("validarCodigoUsuario(String)", e1);
					}
				}
			}
			Auditoria.registrarAuditoria("Inicio de Sesión - Usuario #"+xUsuario.getNumFicha(),'O');
		}
		catch (UsuarioExcepcion e) {
			logger.error("validarCodigoUsuario(String)", e);

			throw e;
		}
		catch (ExcepcionCr e) {
			logger.error("validarCodigoUsuario(String)", e);

			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validarCodigoUsuario(String) - end");
		}
	}	

	/**
	 * Método consultarDatos.
	 * 		Método temporal mientras se optimiza su uso con la clase ManejarSistema.
	 * Devuelve los datos correspondientes a la entidad indicada.
	 * @param entidad - Instancia (Funcion, Perfil, Usuario)con los criterios de 
	 * 		búsqueda(código o descripción).
	 * @return Vector - Objeto vector contenedor de los coincidentes
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr - Arroja excepciones en la conexión con la base de
	 * 		datos, la no validez de la entidad indicada
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Object> consultarDatos(Object entidad) throws BaseDeDatosExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("consultarDatos(Object) - start");
		}
		
		Vector<Object> resultado = new Vector<Object>();
		Sesion.setUbicacion("SEGURIDAD", "consultarDatos");
		resultado = ManejarSistema.consultarDatos(entidad);
		if (entidad instanceof Usuario){
			Auditoria.registrarAuditoria("Cargando opciones Usuario "+((Usuario)resultado.get(0)).getNumFicha(),'O');
		}

		if (logger.isDebugEnabled()) {
			logger.debug("consultarDatos(Object) - end");
		}
		return resultado;
	}
	
}