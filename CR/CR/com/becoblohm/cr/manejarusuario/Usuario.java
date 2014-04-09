/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.manejarusuario
 * Programa   : Usuario.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 29/09/2003 05:35:21 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.6
 * Fecha       : 04/03/2004 01:30 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Reorganizados los trhows para todos los métodos de modo que no sólo 
 * 				 arroje una ExcepcionCr sino cada una de las excepciones específicas.
 * =============================================================================
 * Versión     : 1.5 (según CVS)
 * Fecha       : 17/02/2004 04:30 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Modificaciones en lógica de los métodos cargarCatalogo y cargarRegistros.
 * =============================================================================
 */
package com.becoblohm.cr.manejarusuario;

import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import com.becoblohm.cr.excepciones.AfiliadoUsrExcepcion;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.IdentificarExcepcion;
import com.becoblohm.cr.excepciones.PerfilUsrExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.excepciones.ValidarExcepcion;
import com.becoblohm.cr.gui.ModeloTabla;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarusuario.dbbeans.AddUsuario;
import com.becoblohm.cr.manejarusuario.dbbeans.UpdUsuario;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.utiles.Control;

/** 
 * Descripción: 
 * 		Esta clase vincula los valores de los datos de la tabla Usuario que
 * corresponden a los Colaboradores de la Organización. También proporciona 
 * métodos que permiten la confirmación de existencia de un usuario indicado,
 * la recuperación de sus datos desde la base de datos y el control de cada 
 * una de sus propiedades.
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato de los distintos ArrayList
* Fecha: agosto 2011
*/
public class Usuario extends Colaborador{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Usuario.class);

	private short numTienda;
	private String numFicha;
	private String codigoBarra;
	private String codPerfil;
	private ArrayList<ListaFuncion> funciones;
	private String clave;
	private String nuevaClave;
	private String nivelAuditoria;
	private String nombre;
	private Colaborador datosPersonales;
	private boolean puedeCambiarClave;
	private boolean indicaCambiarClave;
	private Date fechaCreacion;
	private Timestamp ultimoCambioClave;
	private short tiempoVigenciaClave;
	private boolean regVigente;
	private Timestamp actualizacion;
	
	//Atributos agregados para el módulo de usuarios: caso MD5 al recuperar y actualizar valores
	private boolean cambioClave = false;
	private boolean cambioCodbarra = false;
	/**
	 * Constructor para Usuario.
	 */
	public Usuario(){
		this.setNumTienda((short)Sesion.getTienda().getNumero());
	}
	
	/**
	 * Método validar.
	 * 		Retorna verdadero si la Información correspondiente al código
	 * de barra corresponde a un usuario activo.
	 * @param codigoBarra - Código de barra, correspondiente a la primera 
	 * 		fase de reconocimiento del usuario.
	 * @return boolean - Verdadero si el código existe y falso de lo contrario
	 * @throws BaseDeDatosExcepcion
	 * @throws UsuarioExcepcion
	 * @throws ExcepcionCr - Arroja una excepcion de validación si el 
	 * 		código no corresponde a un usuario activo o una excepción de 
	 * 		conexión/acceso a la base de datos.
	 */
	public boolean validar(String codigoBarra, String clave) throws BaseDeDatosExcepcion, UsuarioExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("validar(String) - start");
		}

		boolean valido = false;
		ResultSet registros = null;
		String fallaUsuario = "No pudo cargarse información del usuario.";
		String falla = "Usuario no existente o identificación no vigente.";
		
		try {
			registros = MediadorBD.realizarConsulta("SELECT numtienda, count(*) as cuantos FROM usuario WHERE (usuario.codigobarra = MD5('"+codigoBarra+"')) AND (usuario.regvigente = '" + Sesion.SI + "') GROUP BY numtienda");
			short numTienda = registros.getShort("numtienda");
			if (numTienda != (short)1){
				String sentencia = "SELECT count(*) as cuantos FROM usuario WHERE (usuario.numtienda = "+Sesion.getNumTienda()+") AND (usuario.codigobarra = MD5('"+codigoBarra+"')) AND (usuario.regvigente = '" + Sesion.SI + "')";
				registros = MediadorBD.realizarConsulta(sentencia);
			}
			registros.first();
			valido = registros.getInt("cuantos") == 1 ? true:false;
			if (!(valido)){
				throw new ValidarExcepcion(falla);
			}
		} catch (SQLException e) {
			logger.error("validar(String)", e);

			throw new UsuarioExcepcion(fallaUsuario);
		} finally {
			if (registros != null) {
				try {
					registros.close();
				} catch (SQLException e1) {
					logger.error("validar(String)", e1);
				}
				registros = null;
			}
		}
		// Verificamos la Clave del Usuario
		this.identificar(codigoBarra, clave);
		if (logger.isDebugEnabled()) {
			logger.debug("validar(String) - end");
		}
		return valido;
	}

	/**
	 * Método identificar.
	 * 		Retorna un objeto usuario con todos los datos correspondientes
	 * a los parámetros indicados.
	 * @param login - Número de ficha o código del usuario
	 * @param clave - Clave secreta de acceso al sistema
	 * @return Usuario - Objeto usuario con los datos correspondientes
	 * @throws BaseDeDatosExcepcion
	 * @throws FuncionExcepcion
	 * @throws PerfilUsrExcepcion
	 * @throws UsuarioExcepcion
	 * @throws ExcepcionCr - Arroja una excepción de conexión/acceso a la
	 * 		base de datos, una de identificación cuando la clave indicada es
	 * 		incorrecta o una de perfil asociado cuando el usuario solicitado
	 * 		no tiene asignado un perfil de acceso al sistema
	 */
	public Usuario identificar(String login, String clave) throws BaseDeDatosExcepcion, FuncionExcepcion, PerfilUsrExcepcion, UsuarioExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("identificar(String, String) - start");
		}

		Usuario UsuarioCr = new Usuario();
		ResultSet resultado = null;
		String fallaUsuario = "No pudo cargarse Información del usuario.\n Inténtelo nuevamente.";
		String fallaClave = "Usuario no registrado o contraseña incorrecta.";
		String fallaPerfil = "Usuario no ha sido asignado a un perfil del sistema.";
		String fallaMenu = "El perfil asignado al usuario no tiene configuradas las funciones.";
		
		try {
			StringBuffer idUsuario = new StringBuffer(login);
			int longitud = idUsuario.length();
			if((longitud > Control.getLONGITUD_ID())&&(login.startsWith(Control.getFORMATO_COLABORADOR())))
			{
				idUsuario = new StringBuffer(idUsuario.substring(Control.getFORMATO_COLABORADOR().length(),Control.getLONGITUD_CODIGO() - Control.getFORMATO_COLABORADOR().length()));
			}
			else
			{
				if(longitud < Control.getLONGITUD_ID())
					for (int i=0; i<Control.getLONGITUD_ID()-longitud; i++){
					idUsuario.insert(0,'0');
				}	
			}

			resultado = MediadorBD.realizarConsulta("SELECT DISTINCT * FROM usuario WHERE (usuario.numficha = "+idUsuario.toString()+" OR usuario.codigobarra = MD5('"+login.trim()+"') OR usuario.numficha = '"+idUsuario.toString()+"') AND ((usuario.clave = MD5('"+clave+"')))");
			short numTienda = resultado.getShort("numtienda");
			if (numTienda != (short)1){
				resultado = MediadorBD.realizarConsulta("SELECT DISTINCT * FROM usuario WHERE (usuario.numtienda = "+Sesion.getNumTienda()+") AND (usuario.numficha = "+idUsuario.toString()+" OR usuario.codigobarra = MD5('"+login.trim()+"') OR usuario.numficha = '"+idUsuario.toString()+"') AND ((usuario.clave = MD5('"+clave+"')))");
			}
			if (MediadorBD.getFilas() >= 1){
				try {
					boolean puedeCambiarClave = resultado.getString("puedecambiarclave").trim().charAt(0) == 'N' ? false:true;
					boolean indicaCambiarClave = resultado.getString("indicacambiarclave").trim().charAt(0) == 'N' ? false:true;
					boolean regVigente = resultado.getString("regvigente").trim().charAt(0) == 'N' ? false:true;
					Short tiempoVigenciaClave = new Short(resultado.getShort("tiempovigenciaclave"));
					UsuarioCr.setNumTienda(numTienda);
					UsuarioCr.setNumFicha(resultado.getString("numficha"));
					UsuarioCr.setCodigoBarra(resultado.getString("codigobarra"));
					UsuarioCr.setCodPerfil(resultado.getString("codperfil"));
					UsuarioCr.setClave(clave);
					UsuarioCr.setNivelAuditoria(resultado.getString("nivelauditoria"));
					UsuarioCr.setNombre(resultado.getString("nombre"));
					UsuarioCr.setPuedeCambiarClave(puedeCambiarClave);
					UsuarioCr.setIndicaCambiarClave(indicaCambiarClave);
					UsuarioCr.setFechaCreacion(resultado.getDate("fechacreacion"));
					UsuarioCr.setUltimoCambioClave(resultado.getTimestamp("ultimocambioclave"));
					UsuarioCr.setTiempoVigenciaClave(tiempoVigenciaClave.shortValue());
					UsuarioCr.setRegVigente(regVigente);
					UsuarioCr.setActualizacion(resultado.getTimestamp("actualizacion"));
					/*
					 * (Ojo) 
					 * Validar que no se permita el acceso a un usuario 
					 * no registrado como colaborador
					 */
					 try {
						UsuarioCr.datosPersonales = UsuarioCr.obtenerDatos(UsuarioCr.getNumFicha());
					} catch (ExcepcionCr e) {
						logger.error("identificar(String, String)", e);

						UsuarioCr.datosPersonales = new Colaborador();
					}
					if ((UsuarioCr.getCodPerfil() == null)||(UsuarioCr.getCodPerfil().equals(""))){
						throw new PerfilUsrExcepcion(fallaPerfil);
					}else { 
						UsuarioCr.cargarPerfil();
						if (UsuarioCr.getFunciones() == null){
							 throw new FuncionExcepcion(fallaMenu);
						}

						if (logger.isDebugEnabled()) {
							logger.debug("identificar(String, String) - end");
						}
						return UsuarioCr;
					}
				} catch (SQLException e) {
					logger.error("identificar(String, String)", e);

					throw new UsuarioExcepcion(fallaUsuario);
				}
			}
			else { throw new IdentificarExcepcion(fallaClave); }
		} catch (SQLException e) {
			logger.error("identificar(String, String)", e);

			throw new IdentificarExcepcion(fallaClave);
		} finally {
			if (resultado != null) {
				try {
					resultado.close();
				} catch (SQLException e1) {
					logger.error("identificar(String, String)", e1);
				}
				resultado = null;
			}
		}
	}

	/**
	 * Método cargarDatos.
	 * 		Retorna la Información del usuario indicado registrada en 
	 * la base de datos de Caja Registradora.
	 * @param identificador - Código de barra impreso del usuario a buscar
	 * @return Usuario - Objeto usuario con los datos respectivos
	 * @throws BaseDeDatosExcepcion
	 * @throws PerfilUsrExcepcion
	 * @throws FuncionExcepcion
	 * @throws ExcepcionCr - Arroja una excepción de conexión/acceso a la
	 * 		base de datos o una de existencia en caso de no existir el 
	 * 		usuario solicitado
	 */
	public Usuario cargarDatos(String identificador) throws BaseDeDatosExcepcion, PerfilUsrExcepcion, FuncionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarDatos(String) - start");
		}

		Usuario xUsuario = new Usuario();
		ResultSet resultado	= null;
		String fallaUsuario = "No pudo cargarse información del usuario.";
		String fallaRegistro = "Usuario no registrado.";
		String fallaPerfil = "Usuario no ha sido asignado a un perfil del sistema.";
		String fallaMenu = "El perfil asignado al usuario no tiene configuradas las funciones.";		

		try {
			int tipoCodigo = ((Integer)Control.codigoValido(identificador).get(0)).intValue();
			if ((tipoCodigo == Control.getCOLABORADOR())||(tipoCodigo == Control.getCODIGO_DESCONOCIDO())||(tipoCodigo == Control.getCODIGO_INVALIDO())){
				StringBuffer idUsuario = new StringBuffer(identificador);
				if(identificador.length() < Control.getLONGITUD_ID()){
					int longitud = idUsuario.length();
					for (int i=0; i<Control.getLONGITUD_ID()-longitud; i++){
						idUsuario.insert(0,'0');
					}	
				}	
				resultado = MediadorBD.realizarConsulta("SELECT DISTINCT * FROM usuario WHERE (usuario.codigobarra = MD5('"+identificador.trim()+"')) OR (usuario.codigobarra = '"+identificador.trim()+"') OR (usuario.numficha='"+identificador.trim()+"') OR (usuario.numficha='"+idUsuario.toString()+"')");
				short numTienda = resultado.getShort("numtienda");
				if (numTienda != (short)1){
					resultado = MediadorBD.realizarConsulta("SELECT DISTINCT * FROM usuario WHERE ((usuario.numtienda = "+Sesion.getNumTienda()+") AND ((usuario.codigobarra = MD5('"+identificador.trim()+"')) OR (usuario.codigobarra = '"+identificador.trim()+"') OR (usuario.numficha='"+identificador.trim()+"') OR (usuario.numficha='"+idUsuario.toString()+"')))");
				}
				int cuantos = MediadorBD.getFilas();
				if(cuantos == 0){ throw new UsuarioExcepcion(fallaRegistro); }
				resultado.beforeFirst();
				while (resultado.next()){
					boolean puedeCambiarClave = resultado.getString("puedecambiarclave").trim().charAt(0) == 'N' ? false:true;
					boolean indicaCambiarClave = resultado.getString("indicacambiarclave").trim().charAt(0) == 'N' ? false:true;
					boolean regVigente = resultado.getString("regvigente").trim().charAt(0) == 'N' ? false:true;
					Short tiempoVigenciaClave = new Short(resultado.getShort("tiempovigenciaclave"));
					xUsuario.setNumTienda(numTienda);
					xUsuario.setNumFicha(resultado.getString("numficha"));
					xUsuario.setCodigoBarra(resultado.getString("codigobarra"));
					xUsuario.setCodPerfil(resultado.getString("codperfil"));
					xUsuario.setClave(resultado.getString("clave"));
					xUsuario.setNivelAuditoria(resultado.getString("nivelauditoria"));
					xUsuario.setNombre(resultado.getString("nombre"));
					xUsuario.setPuedeCambiarClave(puedeCambiarClave);
					xUsuario.setIndicaCambiarClave(indicaCambiarClave);
					xUsuario.setFechaCreacion(resultado.getDate("fechacreacion"));
					xUsuario.setUltimoCambioClave(resultado.getTimestamp("ultimocambioclave"));
					xUsuario.setTiempoVigenciaClave(tiempoVigenciaClave.shortValue());
					xUsuario.setRegVigente(regVigente);
					xUsuario.setActualizacion(resultado.getTimestamp("actualizacion"));
					/*
					 * (Ojo) 
					 * Validar que no se permita el acceso a un usuario 
					 * no registrado como colaborador
					 */
					 try {
					 	if (!xUsuario.getNumFicha().equals("00000000") && 
					 			!xUsuario.getNumFicha().equals("99999999")) {
							xUsuario.datosPersonales = xUsuario.obtenerDatos(xUsuario.getNumFicha());
					 	}
					} catch (ExcepcionCr e) {
						logger.error("cargarDatos(String)", e);

						xUsuario.datosPersonales = new Colaborador();
					}
					if ((xUsuario.getCodPerfil() == null)||(xUsuario.getCodPerfil().equals(""))){
						throw new PerfilUsrExcepcion(fallaPerfil);
					}else { 
						xUsuario.cargarPerfil();
						if (xUsuario.getFunciones() == null){
							 throw new FuncionExcepcion(fallaMenu);
						}

						if (logger.isDebugEnabled()) {
							logger.debug("cargarDatos(String) - end");
						}
						return xUsuario;
					}
				}
			}
		} catch (ExcepcionCr e) {
			logger.error("cargarDatos(String)", e);

			throw new BaseDeDatosExcepcion(fallaUsuario);
		} catch (SQLException e) {
			logger.error("cargarDatos(String)", e);

			throw new BaseDeDatosExcepcion(fallaUsuario);
		} finally {
			if (resultado != null) {
				try {
					resultado.close();
				} catch (SQLException e1) {
					logger.error("cargarDatos(String)", e1);
				}
				resultado = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cargarDatos(String) - end");
		}
		return xUsuario;
	}

	/**
	 * Método existe.
	 * 		Retorna verdadero si esta registrado en la tabla Usuario de la 
	 * caja registradora el usuario solicitado.
	 * @param numTienda - Número de tienda asociada al usuario
	 * @param login - Número de ficha o código del usuario
	 * @return boolean - Verdadero si esta registrado de lo contrario falso
	 * @throws UsuarioExcepcion
	 * @throws ExcepcionCr - Arroja una excepción de conexión/acceso a la
	 * 		base de datos en caso de fallar
	 */
	boolean existe(short numTienda, String login) throws UsuarioExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("existe(short, String) - start");
		}

		ResultSet resultado = null;
		String fallaUsuario = "No pudo cargarse información del usuario.";

		boolean existe = false;
		try {
			resultado = MediadorBD.realizarConsulta("SELECT count(*) as cuantos FROM usuario WHERE numtienda = "+numTienda+" AND ((numficha = "+login+") OR (codigobarra = MD5('"+login+"')))");
			int cuantos = resultado.getInt("cuantos");
			if (cuantos > 0) existe = true;
		} catch (SQLException e) {
			logger.error("existe(short, String)", e);

			throw new UsuarioExcepcion(fallaUsuario);
		} finally {
			if (resultado != null) {
				try {
					resultado.close();
				} catch (SQLException e1) {
					logger.error("existe(short, String)", e1);
				}
				resultado = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("existe(short, String) - end");
		}
		return existe;
	}

	/**
	 * Método cambiarClave.
	 * 		Recibe una instancia del objeto usuario con los datos que se desean
	 * actualizar.
	 * @throws BaseDeDatosExcepcion
	 * @throws ExcepcionCr - Arroja una excepción de conexión/acceso a la
	 * 		base de datos o una en caso que el usuario que quiera añadirse no está
	 * 		registrado como colaborador
	 */
	public void cambiarClave() throws BaseDeDatosExcepcion, ExcepcionCr, ConexionExcepcion{
		if (logger.isDebugEnabled()) {
			logger.debug("cambiarClave() - start");
		}

		String falla = "No se pueden actualizar datos del usuario.";
		
		try {
			String xActualizacion = Control.formatoTiempo.format(Sesion.getTimestampSistema());
			
			MediadorBD.ejecutar("UPDATE usuario SET clave = MD5('"+new String(this.getNuevaClave())+"'), ultimocambioclave = "+xActualizacion+", actualizacion = "+xActualizacion+", indicacambiarclave='N' WHERE (numtienda = "+this.getNumTienda()+") AND ((numficha = "+this.getNumFicha()+") OR (numficha = '"+this.getNumFicha()+"'))");
			this.setClave(this.getNuevaClave());
			this.setNuevaClave("");
			indicaCambiarClave = false;

		} catch (BaseDeDatosExcepcion e) {
			logger.error("cambiarClave()", e);

			throw new BaseDeDatosExcepcion(falla);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cambiarClave() - end");
		}
	}

	/**
	 * Método actualizarDatos.
	 * 		Recibe una instancia del objeto usuario con los datos que se desean
	 * actualizar, en caso de un usuario registrado efectúa una actualización
	 * de lo contrario añade un nuevo registro a la tabla Usuario de la caja
	 * registradora iniciada.
	 * @param xUsuario - Objeto usuario con los datos que se desean añadir
	 * 		o actualizar a la tabla Usuario de la caja registradora
	 * @throws BaseDeDatosExcepcion
	 * @throws PerfilUsrExcepcion
	 * @throws AfiliadoUsrExcepcion
	 * @throws UsuarioExcepcion
	 * @throws ExcepcionCr - Arroja una excepción de conexión/acceso a la
	 * 		base de datos o una en caso que el usuario que quiera añadirse no está
	 * 		registrado como colaborador
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se comentó variable sin uso
	* Fecha: agosto 2011
	*/
	public void actualizarDatos(Usuario xUsuario) throws BaseDeDatosExcepcion, PerfilUsrExcepcion, AfiliadoUsrExcepcion, UsuarioExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarDatos(Usuario) - start");
		}

		AddUsuario addUsuario = new AddUsuario();
		UpdUsuario updUsuario = new UpdUsuario();
		ResultSet resultado = null;
		String falla = "No se pueden actualizar datos del usuario.";
		String fallaAfiliadoTienda = "El usuario no esta registrado como colaborador para la tienda "+xUsuario.getNumTienda()+".";
		String fallaAfiliado = "El usuario no esta registrado como colaborador.";
		String fallaPerfil = "El perfil indicado no ha sido registrado.";
		
		Short numTienda = new Short(xUsuario.getNumTienda());
		String puedeCambiarClave = xUsuario.isPuedeCambiarClave()==true?"S":"N";
		String indicaCambiarClave = xUsuario.isIndicaCambiarClave()==true?"S":"N";
		String regVigente = xUsuario.isRegVigente() == true?"S":"N";
		//java.util.Date hoy = (java.util.Date)Calendar.getInstance().getTime();
		Timestamp actualizacion = new Timestamp(Calendar.getInstance().getTime().getTime());

		try {
			if (!(Perfil.existe(xUsuario.getCodPerfil()))) throw new PerfilUsrExcepcion(fallaPerfil);
			boolean existe = xUsuario.existe(xUsuario.getNumTienda(),xUsuario.getNumFicha());
			if (existe){
				if (xUsuario.getNombre() == null){
					resultado = MediadorBD.realizarConsulta("SELECT nombre, regvigente FROM usuario WHERE numtienda = "+xUsuario.getNumTienda()+" AND numficha = "+xUsuario.getNumFicha()+"");
					xUsuario.setNombre(resultado.getString("nombre"));
				}	
				if (xUsuario.getClave() == null){
					resultado = MediadorBD.realizarConsulta("SELECT clave FROM usuario WHERE numtienda = "+xUsuario.getNumTienda()+" AND numficha = "+xUsuario.getNumFicha()+"");
					//xUsuario.setClave(xUsuario.getClave());
				}	
				if (xUsuario.getCodigoBarra() == null){
					resultado = MediadorBD.realizarConsulta("SELECT codigobarra, clave FROM usuario WHERE numtienda = "+xUsuario.getNumTienda()+" AND numficha = "+xUsuario.getNumFicha()+"");
					//xUsuario.setCodigoBarra(Control.getFORMATO_COLABORADOR()+xUsuario.getNumFicha()+"01");
				}
				
				if (xUsuario.isCambioClave() && xUsuario.isCambioCodbarra()) {
					updUsuario.execute(
						new String(xUsuario.getCodigoBarra()),
						new String(xUsuario.getCodPerfil()),
						new String(xUsuario.getClave()),
						new String(xUsuario.getNivelAuditoria()),
						new String(xUsuario.getNombre()),
						String.valueOf(puedeCambiarClave.toUpperCase().charAt(0)),
						String.valueOf(indicaCambiarClave.toUpperCase().charAt(0)),
						new Timestamp(xUsuario.getUltimoCambioClave().getTime()),
						new Short(xUsuario.getTiempoVigenciaClave()),
						(String)regVigente,
						(Short)numTienda,
						new String(xUsuario.getNumFicha()));
				} else if(xUsuario.isCambioClave()) {
					updUsuario.execute1(
						new String(xUsuario.getCodigoBarra()),
						new String(xUsuario.getCodPerfil()),
						new String(xUsuario.getClave()),
						new String(xUsuario.getNivelAuditoria()),
						new String(xUsuario.getNombre()),
						String.valueOf(puedeCambiarClave.toUpperCase().charAt(0)),
						String.valueOf(indicaCambiarClave.toUpperCase().charAt(0)),
						new Timestamp(xUsuario.getUltimoCambioClave().getTime()),
						new Short(xUsuario.getTiempoVigenciaClave()),
						(String)regVigente,
						(Short)numTienda,
						new String(xUsuario.getNumFicha()));
				} else if(xUsuario.isCambioCodbarra()) {
					updUsuario.execute2(
						new String(xUsuario.getCodigoBarra()),
						new String(xUsuario.getCodPerfil()),
						new String(xUsuario.getClave()),
						new String(xUsuario.getNivelAuditoria()),
						new String(xUsuario.getNombre()),
						String.valueOf(puedeCambiarClave.toUpperCase().charAt(0)),
						String.valueOf(indicaCambiarClave.toUpperCase().charAt(0)),
						new Timestamp(xUsuario.getUltimoCambioClave().getTime()),
						new Short(xUsuario.getTiempoVigenciaClave()),
						(String)regVigente,
						(Short)numTienda,
						new String(xUsuario.getNumFicha()));
				} else {
					updUsuario.execute3(
						new String(xUsuario.getCodigoBarra()),
						new String(xUsuario.getCodPerfil()),
						new String(xUsuario.getClave()),
						new String(xUsuario.getNivelAuditoria()),
						new String(xUsuario.getNombre()),
						String.valueOf(puedeCambiarClave.toUpperCase().charAt(0)),
						String.valueOf(indicaCambiarClave.toUpperCase().charAt(0)),
						new Timestamp(xUsuario.getUltimoCambioClave().getTime()),
						new Short(xUsuario.getTiempoVigenciaClave()),
						(String)regVigente,
						(Short)numTienda,
						new String(xUsuario.getNumFicha()));
				}
					
			}
			else{
				Colaborador xColaborador = new Colaborador();
				xColaborador = xColaborador.obtenerDatos(xUsuario.getNumFicha());
				int cuantos = 0;
				if (xColaborador.getCodAfiliado() != null){
					/*if(xColaborador.getNumTienda() != (short)1){
						resultado = MediadorBD.realizarConsulta("SELECT count(*) as cuantos FROM afiliado WHERE numtienda = "+xUsuario.getNumTienda()+" AND numficha = "+xUsuario.getNumFicha()+" AND estadocolaborador = '"+Sesion.ACTIVO+"'");
						cuantos = resultado.getInt("cuantos");
					} else*/ cuantos = 1;
					if (cuantos > 0){
						xUsuario.setNombre(xColaborador.getNombre());
						addUsuario.execute(
							new Short(new Integer(Sesion.getTienda().getNumero()).shortValue()),
							new String(xUsuario.getNumFicha()),
							new String(xUsuario.getCodigoBarra()),
							new String(xUsuario.getCodPerfil()),
							new String(xUsuario.getClave()),
							new String(xUsuario.getNivelAuditoria()),
							new String(xUsuario.getNombre()),
							String.valueOf(puedeCambiarClave.toUpperCase().charAt(0)),
							String.valueOf(indicaCambiarClave.toUpperCase().charAt(0)),
							new java.sql.Date(Calendar.getInstance().getTime().getTime()),
							(Timestamp)actualizacion,
							new Short(xUsuario.getTiempoVigenciaClave()),
							(String)"S",
							(Timestamp)actualizacion);
					}
					else{
						throw new AfiliadoUsrExcepcion(fallaAfiliadoTienda);
					}	
				}
				else{
					throw new BaseDeDatosExcepcion(fallaAfiliado);
				}	
			} 
		} catch (SQLException e) {
			logger.error("actualizarDatos(Usuario)", e);

			throw new UsuarioExcepcion(falla);
		} catch (PerfilUsrExcepcion e) {
			logger.error("actualizarDatos(Usuario)", e);

			throw new PerfilUsrExcepcion(fallaPerfil);
		} catch (AfiliadoUsrExcepcion e) {
			logger.error("actualizarDatos(Usuario)", e);

			throw new AfiliadoUsrExcepcion(fallaAfiliadoTienda);
		} catch (UsuarioExcepcion e) {
			logger.error("actualizarDatos(Usuario)", e);

			throw new UsuarioExcepcion(falla);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("actualizarDatos(Usuario)", e);

			throw new BaseDeDatosExcepcion(fallaAfiliado);
		} catch (ExcepcionCr e) {
			logger.error("actualizarDatos(Usuario)", e);

			throw new UsuarioExcepcion(falla);
		} finally {
			if (resultado != null) {
				try {
					resultado.close();
				} catch (SQLException e1) {
					logger.error("actualizarDatos(Usuario)", e1);
				}
				resultado = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarDatos(Usuario) - end");
		}
	}
	

	/**
	 * Método cargarPerfil.
	 * 		Carga el atributo funciones del objeto usuario con el listado de 
	 * funciones y su respectiva configuración de acceso, según el perfil
	 * correspondiente.
	 * @throws BaseDeDatosExcepcion
	 * @throws FuncionExcepcion
	 * @throws ExcepcionCr
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	public void cargarPerfil() throws BaseDeDatosExcepcion, FuncionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarPerfil() - start");
		}

		ArrayList<ListaFuncion> menu = new ArrayList<ListaFuncion>();
		ListaFuncion xMenu = new ListaFuncion();
		ResultSet funciones = null;
		String falla = "No se pueden cargar opciones del menú del usuario.";

		try {
			String tiraSql = "SELECT DISTINCT perfil.descripcion, perfil.nivelauditoria, funcionperfil.codmodulo, funcionperfil.codfuncion, funcionperfil.codperfil, funcionperfil.habilitado, funcionperfil.autorizado, funcionperfil.actualizacion, funcion.descripcion, modulos.descripcion, funcion.nivelauditoria FROM perfil, funcionperfil, funcion, modulos WHERE (funcionperfil.codperfil = perfil.codperfil) AND (funcionperfil.codfuncion = funcion.codfuncion) AND (funcionperfil.codmodulo = funcion.codmodulo) AND (funcion.codmodulo = modulos.codmodulo) AND (funcion.regvigente = '" + Sesion.SI + "') AND (modulos.regvigente = '" + Sesion.SI + "') AND (funcionperfil.codperfil = "+this.getCodPerfil()+")";
			funciones = MediadorBD.realizarConsulta(tiraSql);
			if (MediadorBD.getFilas() > 0){
				funciones.beforeFirst();
				while(funciones.next()){
					xMenu = new ListaFuncion();
					boolean habilitado = funciones.getString("habilitado").toUpperCase().trim().equals("S") ? true:false;
					boolean autorizado = funciones.getString("autorizado").toUpperCase().trim().equals("S") ? true:false;
					xMenu.obtenerDatos(funciones.getShort("codmodulo"), funciones.getShort("codfuncion"));
					xMenu.setHabilitado(habilitado);
					xMenu.setAutorizado(autorizado);
					menu.add(xMenu);
				}	
					this.setFunciones(menu);
			}	
		} catch (SQLException e) {
			logger.error("cargarPerfil()", e);

			throw new BaseDeDatosExcepcion(falla);
		} finally {
			if (funciones != null){
				try {
					funciones.close();
				} catch (SQLException e1) {
					logger.error("cargarPerfil()", e1);
				}
				funciones = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cargarPerfil() - end");
		}
	}

	/**
	 * Método cargarRegistros.
	 * 		Devuelve los datos correspondientes a los usuarios registrados.
	 * @param vigentes - Verdadero para filtrar resultados por registros vigentes
	 * @return Vector - Objeto vector contenedor de los objetos Usuario
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

		Vector<Colaborador> resultados = new Vector<Colaborador>();
		Usuario xUsuario = new Usuario();
		ResultSet datos = null;
		String fallaRegistros = "No pudo obtenerse información de la base de datos.";
		try {
			StringBuffer tiraSql = new StringBuffer("");
			if (vigentes){
				tiraSql = new StringBuffer("SELECT DISTINCT * FROM usuario WHERE regvigente = '" + Sesion.SI + "'");
			}	
			else{
				tiraSql = new StringBuffer("SELECT DISTINCT * FROM usuario");
			}	 	
			datos = MediadorBD.realizarConsulta(tiraSql.toString());
			datos.beforeFirst();
			while (datos.next()){
				xUsuario.setCodigoBarra(datos.getString("codigobarra"));
				xUsuario = xUsuario.cargarDatos(xUsuario.getCodigoBarra());
				resultados.addElement((Colaborador)xUsuario);
				xUsuario = new Usuario();
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
	 * @return VentanaCatalogo - Objeto catalogo con los datos y títulos respectivos
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

		Vector<Colaborador> resultado = new Vector<Colaborador>();
		ModeloTabla xCatalogo = new ModeloTabla();
		Usuario xUsuario = new Usuario();
		String[] titulos;
		Object[][] datos;
		int i = 0;
		
		resultado = cargarRegistros(vigentes);
		if (vigentes == false){
			titulos = new String[3];
			titulos[0] = "Identificador";
			titulos[1] = "Nombre";
			titulos[2] = "Activo";
			Iterator<Colaborador> ciclo = resultado.iterator();
			datos = new Object[resultado.size()][3];
			while(ciclo.hasNext())
			{    			
				xUsuario = (Usuario)ciclo.next();
				datos[i][0] = new String(xUsuario.getNumFicha());
				datos[i][1] = new String(xUsuario.getNombre());
				datos[i][2] = new Boolean(xUsuario.isRegVigente());
				i++;
			}
		}
		else {
			titulos = new String[2];
			titulos[0] = "Identificador";
			titulos[1] = "Nombre";
			Iterator<Colaborador> ciclo = resultado.iterator();
			datos = new Object[resultado.size()][3];
			while(ciclo.hasNext())
			{    			
				xUsuario = (Usuario)ciclo.next();
				datos[i][0] = new String(xUsuario.getNumFicha());
				datos[i][1] = new String(xUsuario.getNombre());
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
	 * Método asignarPerfil.
	 * 		Establece el perfil de usuario que se asigna al usuario 
	 * correspondiente.
	 * @param perfil - Código del perfil asignado
	 */
	public void asignarPerfil(String perfil){
		if (logger.isDebugEnabled()) {
			logger.debug("asignarPerfil(String) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("asignarPerfil(String) - end");
		}
	}

	/**
	 * Devuelve la fecha/hora (AAAA-MM-DD HH:MM:SS)de la última actualización
	 * hecha al registro de la base de datos asociado.
	 * @return Timestamp - Fecha/hora (AAAA-MM-DD HH:MM:SS)de la última actualización
	 */
	public Timestamp getActualizacion() {
		if (logger.isDebugEnabled()) {
			logger.debug("getActualizacion() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getActualizacion() - end");
		}
		return actualizacion;
	}

	/**
	 * Devuelve la clave de acceso al sistema.
	 * @return String - Cadena de 8 caracteres 
	 */
	public String getClave() {
		if (logger.isDebugEnabled()) {
			logger.debug("getClave() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getClave() - end");
		}
		return clave;
	}

	/**
	 * Devuelve la información del código de barra asignado al usuario.
	 * @return String - Cadena de 13 caracteres 
	 */
	public String getCodigoBarra() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCodigoBarra() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getCodigoBarra() - end");
		}
		return codigoBarra;
	}

	/**
	 * Devuelve el código del perfil asignado al usuario.
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
	 * Devuelve la fecha de creación del usuario en los registros del sistema.
	 * @return Date
	 */
	public Date getFechaCreacion() {
		if (logger.isDebugEnabled()) {
			logger.debug("getFechaCreacion() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getFechaCreacion() - end");
		}
		return fechaCreacion;
	}

	/**
	 * Devuelve un indicador "S"-Si / "N"-No para que el usuario cambie 
	 * su clave de acceso al sistema la próxima vez que ingrese.
	 * @return boolean - Verdadero si es "S", falso si es "N"
	 */
	public boolean isIndicaCambiarClave() {
		if (logger.isDebugEnabled()) {
			logger.debug("isIndicaCambiarClave() - start");
		}

		if (!indicaCambiarClave) {
			// Esto tiene que hacerse sólo si ya el campo indicaCambiarClave no esta en true
			int tiempo = (int)this.getTiempoVigenciaClave();
			if (tiempo != 0){
				Date ultimoCambio = this.getUltimoCambioClave();
				Date hoy = Calendar.getInstance().getTime();
				Calendar fechaVenceClave = Calendar.getInstance();
				fechaVenceClave.setTime(ultimoCambio);
				fechaVenceClave.add(Calendar.DATE, tiempo);
				// Verifica si hoy es antes que la fecha en que vence la clave
				if (fechaVenceClave.getTime().compareTo(hoy) > 0){
					this.indicaCambiarClave = false;
				} else this.indicaCambiarClave = true;
			} else this.indicaCambiarClave = false;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isIndicaCambiarClave() - end");
		}
		return indicaCambiarClave;
	}

	/**
	 * Devuelve el nivel de auditoría ("1".."5") asignado al usuario.
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
	 * Devuelve el nombre completo del usuario para el caso de usuarios 
	 * temporales.
	 * @return String - Cadena de 50 caracteres 
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
	 * Devuelve número de ficha o código del usuario.
	 * @return String - Cadena de 8 caracteres 
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
	 * Devuelve el número de la tienda donde está asignado el usuario.
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
	 * Devuelve un indicador "S"-Si / "N"-No para determinar si el usuario 
	 * puede cambiar su clave de acceso al sistema.
	 * @return boolean - Verdadero si es "S", falso si es "N"
	 */
	public boolean isPuedeCambiarClave() {
		if (logger.isDebugEnabled()) {
			logger.debug("isPuedeCambiarClave() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isPuedeCambiarClave() - end");
		}
		return puedeCambiarClave;
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
	 * Devuelve el tiempo (días) para la vigencia de la clave de acceso del
	 * sistema, para que sea cambiada. El valor 0 implica tiempo ilimitado.
	 * @return short
	 */
	public short getTiempoVigenciaClave() {
		if (logger.isDebugEnabled()) {
			logger.debug("getTiempoVigenciaClave() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getTiempoVigenciaClave() - end");
		}
		return tiempoVigenciaClave;
	}

	/**
	 * Devuelve la fecha/hora del último cambio de la clave secreta.
	 * @return Timestamp
	 */
	public Timestamp getUltimoCambioClave() {
		if (logger.isDebugEnabled()) {
			logger.debug("getUltimoCambioClave() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getUltimoCambioClave() - end");
		}
		return ultimoCambioClave;
	}

	/**
	 * Establece la fecha/hora (AAAA-MM-DD HH:MM:SS)de la última actualización
	 * hecha al registro de la base de datos asociado.
	 * @param actualizacion - Fecha/hora (AAAA-MM-DD HH:MM:SS)
	 */
	public void setActualizacion(Timestamp actualizacion) {
		if (logger.isDebugEnabled()) {
			logger.debug("setActualizacion(Timestamp) - start");
		}

		this.actualizacion = actualizacion;

		if (logger.isDebugEnabled()) {
			logger.debug("setActualizacion(Timestamp) - end");
		}
	}

	/**
	 * Establece la clave de acceso al sistema.
	 * @param clave - Cadena de 8 caracteres 
	 */
	public void setClave(String clave) {
		if (logger.isDebugEnabled()) {
			logger.debug("setClave(String) - start");
		}

		this.setUltimoCambioClave(new Timestamp(Calendar.getInstance().getTime().getTime()));
		this.clave = clave;

		if (logger.isDebugEnabled()) {
			logger.debug("setClave(String) - end");
		}
	}

	/**
	 * Establece la información del código de barra asignado al usuario.
	 * @param codigoBarra - Cadena de máximo 13 caracteres 
	 */
	public void setCodigoBarra(String codigoBarra) {
		if (logger.isDebugEnabled()) {
			logger.debug("setCodigoBarra(String) - start");
		}

/*		int longitud = codigoBarra.length();
		StringBuffer codigo = new StringBuffer(codigoBarra);
		if (longitud < Control.getLONGITUD_CODIGO()){
			for (int i=0; i<(Control.getLONGITUD_CODIGO()-Control.getFORMATO_COLABORADOR().length())-longitud; i++){
				codigo.insert(0,'0');
			}
			codigo.insert(0,Control.getFORMATO_COLABORADOR());
			codigoBarra = codigo.toString();
		}*/

		this.codigoBarra = codigoBarra;

		if (logger.isDebugEnabled()) {
			logger.debug("setCodigoBarra(String) - end");
		}
	}

	/**
	 * Establece el código del perfil asignado al usuario.
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
	 * Establece la fecha de creación del usuario en los registros del sistema.
	 * @param fechaCreacion - Fecha de creación a establecer
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		if (logger.isDebugEnabled()) {
			logger.debug("setFechaCreacion(Date) - start");
		}

		this.fechaCreacion = fechaCreacion;

		if (logger.isDebugEnabled()) {
			logger.debug("setFechaCreacion(Date) - end");
		}
	}

	/**
	 * Establece un indicador "S"-Si / "N"-No para que el usuario cambie 
	 * su clave de acceso al sistema la próxima vez que ingrese.
	 * @param indicaCambiarClave - Verdadero si es "S", falso si es "N"
	 */
	public void setIndicaCambiarClave(boolean indicaCambiarClave) {
		if (logger.isDebugEnabled()) {
			logger.debug("setIndicaCambiarClave(boolean) - start");
		}

		if (indicaCambiarClave == true){
			Calendar fecha;
			fecha = Calendar.getInstance();
			fecha.add(Calendar.YEAR, -1);
			this.setUltimoCambioClave(new Timestamp(fecha.getTime().getTime()));
			this.setPuedeCambiarClave(true);
		}
		this.indicaCambiarClave = indicaCambiarClave;


		if (logger.isDebugEnabled()) {
			logger.debug("setIndicaCambiarClave(boolean) - end");
		}
	}

	/**
	 * Establece el nivel de auditoría ("1".."5") asignado al usuario.
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
	 * Establece el nombre completo del usuario para el caso de usuarios 
	 * temporales.
	 * @param nombre - Cadena de 50 caracteres máximo
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
	 * Establece el número de ficha o código del usuario.
	 * @param numFicha - Cadena de caracteres 
	 */
	public void setNumFicha(String numFicha) {
		if (logger.isDebugEnabled()) {
			logger.debug("setNumFicha(String) - start");
		}

		int longitud = numFicha.length();
		StringBuffer codigo = new StringBuffer(numFicha);
		if (longitud < Control.getLONGITUD_ID()){
			for (int i=0; i<Control.getLONGITUD_ID()-longitud; i++){
				codigo.insert(0,'0');
			}
			numFicha = codigo.toString();
		}
		this.numFicha = numFicha;

		if (logger.isDebugEnabled()) {
			logger.debug("setNumFicha(String) - end");
		}
	}

	/**
	 * Devuelve el número de la tienda donde está asignado el usuario.
	 * @param numTienda The numTienda to set
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
	 * Establece un indicador "S"-Si / "N"-No para determinar si el usuario 
	 * puede cambiar su clave de acceso al sistema.
	 * @param puedeCambiarClave - Verdadero si es "S", falso si es "N"
	 */
	public void setPuedeCambiarClave(boolean puedeCambiarClave) {
		if (logger.isDebugEnabled()) {
			logger.debug("setPuedeCambiarClave(boolean) - start");
		}

		this.puedeCambiarClave = puedeCambiarClave;

		if (logger.isDebugEnabled()) {
			logger.debug("setPuedeCambiarClave(boolean) - end");
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
	 * Establece el tiempo (días) para la vigencia de la clave de acceso del
	 * sistema, para que sea cambiada. El valor 0 implica tiempo ilimitado.
	 * @param tiempoVigenciaClave - Tiempo en días de vigencia de clave
	 */
	public void setTiempoVigenciaClave(short tiempoVigenciaClave) {
		if (logger.isDebugEnabled()) {
			logger.debug("setTiempoVigenciaClave(short) - start");
		}

		this.tiempoVigenciaClave = tiempoVigenciaClave;

		if (logger.isDebugEnabled()) {
			logger.debug("setTiempoVigenciaClave(short) - end");
		}
	}

	/**
	 * Establece la fecha/hora del último cambio de la clave secreta.
	 * @param ultimoCambioClave - Fecha/Hora (AAAA-MM-DD HH:MM:SS)
	 */
	public void setUltimoCambioClave(Timestamp ultimoCambioClave) {
		if (logger.isDebugEnabled()) {
			logger.debug("setUltimoCambioClave(Timestamp) - start");
		}

		this.ultimoCambioClave = ultimoCambioClave;

		if (logger.isDebugEnabled()) {
			logger.debug("setUltimoCambioClave(Timestamp) - end");
		}
	}

	/**
	 * Devuelve el valor de la clave de acceso que quiere establecerse.
	 * @return String - Cadena de 8 caracteres
	 */
	public String getNuevaClave() {
		if (logger.isDebugEnabled()) {
			logger.debug("getNuevaClave() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getNuevaClave() - end");
		}
		return nuevaClave;
	}

	/**
	 * Establece el valor de la nueva clave de acceso.
	 * @param nuevaClave - Cadena de 8 caracteres
	 */
	public void setNuevaClave(String nuevaClave) {
		if (logger.isDebugEnabled()) {
			logger.debug("setNuevaClave(String) - start");
		}

		this.nuevaClave = nuevaClave;

		if (logger.isDebugEnabled()) {
			logger.debug("setNuevaClave(String) - end");
		}
	}

	/**
	 * Devuelve la lista de funciones del sistema con acceso para el usuario.
	 * @return ArrayList - Listado de objetos ListaFuncion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	public ArrayList<ListaFuncion> getFunciones() {
		if (logger.isDebugEnabled()) {
			logger.debug("getFunciones() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getFunciones() - end");
		}
		return funciones;
	}

	/**
	 * Establece la lista de funciones del sistema con acceso para el usuario.
	 * @param funciones - Listado de objetos tipo ListaFuncion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
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
	 * Devuelve los valores correspondientes al colaborador.
	 * @return Colaborador
	 */
	public Colaborador getDatosPersonales() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDatosPersonales() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDatosPersonales() - end");
		}
		return datosPersonales;
	}

	/**
	 * Establece los valores correspondientes al colaborador.
	 * @param datosPersonales - Instancia de la clase Colaborador
	 */
	public void setDatosPersonales(Colaborador datosPersonales) {
		if (logger.isDebugEnabled()) {
			logger.debug("setDatosPersonales(Colaborador) - start");
		}

		this.datosPersonales = datosPersonales;

		if (logger.isDebugEnabled()) {
			logger.debug("setDatosPersonales(Colaborador) - end");
		}
	}
	
	public String getNumFicha(String codBarra) {
		if (logger.isDebugEnabled()) {
			logger.debug("getNumFicha(String) - start");
		}

		String ficha = null;
		boolean val = false;
		ResultSet rs = null;
		try {
			String sentenciaSQL = "select numficha from usuario where codigobarra = MD5('" + codBarra + "')";
			rs = MediadorBD.realizarConsulta(sentenciaSQL);
			if (val = rs.first()) {
				ficha = rs.getString("numficha");
			} 
		} catch (BaseDeDatosExcepcion e) {
			logger.error("getNumFicha(String)", e);
		} catch (SQLException e) {
			logger.error("getNumFicha(String)", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e1) {
					logger.error("getNumFicha(String)", e1);
				}
				rs = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getNumFicha(String) - end");
		}
		return ficha;
	}
	/**
	 * Método isCambioClave
	 * 
	 * @return
	 * boolean
	 */
	public boolean isCambioClave() {
		return cambioClave;
	}

	/**
	 * Método isCambioCodbarra
	 * 
	 * @return
	 * boolean
	 */
	public boolean isCambioCodbarra() {
		return cambioCodbarra;
	}

	/**
	 * Método setCambioClave
	 * 
	 * @param b
	 * void
	 */
	public void setCambioClave(boolean b) {
		cambioClave = b;
	}

	/**
	 * Método setCambioCodbarra
	 * 
	 * @param b
	 * void
	 */
	public void setCambioCodbarra(boolean b) {
		cambioCodbarra = b;
	}

}
