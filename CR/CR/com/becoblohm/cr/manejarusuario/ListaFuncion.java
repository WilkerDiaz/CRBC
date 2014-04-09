/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.manejarusuario
 * Programa   : ListaFuncion.java
 * Creado por : Programador3 - Alexis Gu�dez L�pez
 * Creado en  : 06/10/2003 02:19:00 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versi�n     : 1.3
 * Fecha       : 04/03/2004 01:30 PM
 * Analista    : Programador3 - Alexis Gu�dez L�pez
 * Descripci�n : Reorganizados los trhows para todos los m�todos de modo que no s�lo 
 * 				 arroje una ExcepcionCr sino cada una de las excepciones espec�ficas.
 * =============================================================================
 * Versi�n     : 1.2 (Seg�n CVS) 
 * Fecha       : 02/03/2004 11:01 AM
 * Analista    : irojas
 * Descripci�n : L�nea 77: Carga del registro "reqAutorizacion" de la tabla Funcion en la BD
 * =============================================================================
 */
package com.becoblohm.cr.manejarusuario;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarsistema.Funcion;
import com.becoblohm.cr.mediadoresbd.MediadorBD;

/** 
 * Descripci�n: 
 * 		Esta clase vincula los valores de los datos de la tabla FuncionPerfil
 * que corresponden al sistema de caja registradora. Tambi�n proporciona 
 * m�todos que permiten el control de cada una de sus propiedades.
 */

public class ListaFuncion {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ListaFuncion.class);

	private Funcion funcion;
	private boolean habilitado;
	private boolean autorizado;

	/**
	 * Constructor para ListaFuncion.
	 */
	public ListaFuncion() {
		super();
	}

	/**
	 * M�todo obtenerDatos.
	 * 		Retorna la informaci�n de la configuraci�n de acceso a las 
	 * funciones del sistema para un perfil indicado, registrada en 
	 * la base de datos de Caja Registradora.
	 * @param codFuncion - C�digo identificador de la funci�n
	 * @param codModulo - C�digo identificador del m�dulo asociado
	 * @throws BaseDeDatosExcepcion
	 * @throws FuncionExcepcion
	 * @throws ExcepcionCr - Arroja una excepci�n de conexi�n/acceso a la
	 * 		base de datos o una de existencia en caso de no existir el perfil
	 * 		solicitado
	 */
	public void obtenerDatos(short codModulo, short codFuncion) throws BaseDeDatosExcepcion, FuncionExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerDatos(short, short) - start");
		}

		ResultSet resultado = null;
		Funcion xFuncion = new Funcion();
		String fallaEntidad = "No pudo cargarse la informaci�n desde la base de datos - Int�ntelo nuevamente.";
		String fallaFuncion = "Funci�n no registrada o no activa en el sistema.";
		
		try {
			resultado = MediadorBD.realizarConsulta("SELECT * FROM funcion WHERE (codfuncion = "+codFuncion+" AND codmodulo = "+codModulo+") AND regvigente = '" + Sesion.SI + "'");
			if(MediadorBD.getFilas() > 0){
				xFuncion.setCodFuncion(resultado.getShort("codfuncion"));
				xFuncion.setCodModulo(resultado.getShort("codmodulo"));
				xFuncion.setDescripcion(resultado.getString("descripcion"));
				xFuncion.setFechaActualizacion(resultado.getDate("actualizacion"));
				xFuncion.setHoraActualizacion(resultado.getTime("actualizacion"));
				xFuncion.setReqAutorizacion(resultado.getString("reqautorizacion").toUpperCase().trim().equals("S") ? true:false);
				char nivelAuditoria = resultado.getString("nivelauditoria").toUpperCase().trim().charAt(0);
				xFuncion.setNivelAuditoria(nivelAuditoria);
				this.setFuncion(xFuncion);
			}
			else throw new FuncionExcepcion(fallaFuncion);
		} catch (FuncionExcepcion e) {
			logger.error("obtenerDatos(short, short)", e);

			throw new FuncionExcepcion(fallaFuncion);
		} catch (SQLException e) {
			logger.error("obtenerDatos(short, short)", e);

			throw new BaseDeDatosExcepcion(fallaEntidad);
		} finally {
			if (resultado != null) {
				try {
					resultado.close();
				} catch (SQLException e1) {
					logger.error("obtenerDatos(short, short)", e1);
				}
				resultado = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerDatos(short, short) - end");
		}
	}

	/**
	 * Devuelve un indicador "S"-Si / "N"-No para el acceso sin autorizaci�n
	 * de supervisor del usuario a la funcionalidad respectiva.
	 * @return boolean - Verdadero si es "S", falso si es "N"
	 */
	public boolean isAutorizado() {
		if (logger.isDebugEnabled()) {
			logger.debug("isAutorizado() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isAutorizado() - end");
		}
		return autorizado;
	}

	/**
	 * Devuelve un indicador "S"-Si / "N"-No para la visualizaci�n de la 
	 * funcionalidad al usuario iniciado.
	 * @return boolean - Verdadero si es "S", falso si es "N"
	 */
	public boolean isHabilitado() {
		if (logger.isDebugEnabled()) {
			logger.debug("isHabilitado() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isHabilitado() - end");
		}
		return habilitado;
	}

	/**
	 * Establece un indicador "S"-Si / "N"-No para el acceso sin autorizaci�n
	 * de supervisor del usuario a la funcionalidad respectiva.
	 * @param autorizado - Verdadero si es "S", falso si es "N"
	 */
	public void setAutorizado(boolean autorizado) {
		if (logger.isDebugEnabled()) {
			logger.debug("setAutorizado(boolean) - start");
		}

		this.autorizado = autorizado;

		if (logger.isDebugEnabled()) {
			logger.debug("setAutorizado(boolean) - end");
		}
	}

	/**
	 * Establece un indicador "S"-Si / "N"-No para la visualizaci�n de la 
	 * funcionalidad al usuario iniciado.
	 * @param habilitado - Verdadero si es "S", falso si es "N"
	 */
	public void setHabilitado(boolean habilitado) {
		if (logger.isDebugEnabled()) {
			logger.debug("setHabilitado(boolean) - start");
		}

		this.habilitado = habilitado;

		if (logger.isDebugEnabled()) {
			logger.debug("setHabilitado(boolean) - end");
		}
	}

	/**
	 * Devuelve el objeto funci�n correspondiente
	 * @return Funcion - Objeto funci�n
	 */
	public Funcion getFuncion(){
		if (logger.isDebugEnabled()) {
			logger.debug("getFuncion() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getFuncion() - end");
		}
		return funcion;
	}

	/**
	 * Establece el objeto funci�n correspondiente
	 * @param funcion - Objeto funci�n
	 */
	public void setFuncion(Funcion funcion) {
		if (logger.isDebugEnabled()) {
			logger.debug("setFuncion(Funcion) - start");
		}

		this.funcion = funcion;

		if (logger.isDebugEnabled()) {
			logger.debug("setFuncion(Funcion) - end");
		}
	}

}
