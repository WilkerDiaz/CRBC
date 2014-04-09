/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.tienda
 * Programa   : Caja.java
 * Creado por : yzambrano
 * Creado en  : 07-ago-06 
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 
 * Fecha       : 
 * Analista    : 
 * Descripción : 
 * =============================================================================
 */
package com.becoblohm.cr.manejarmantenimiento;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.mediadoresbd.MediadorBD;

/**
 * 
 * @author yzambrano
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class MantenimientoECU {
	
	
	/**
	 * Método almacenarEstado
	 * 		
	 * 
	 * @param descripcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws XmlExcepcion
	 * @throws FuncionExcepcion
	 * @throws ConexionExcepcion
	 */
	
	public void almacenarEstado (String descripcion) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr
	{
		try
		{
		//	String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "finalizarMantenimiento");
		//	CR.me.verificarAutorizacion("MANTENIMIENTO","finalizarMantenimiento");
			MediadorBD.almacenarEstado(descripcion);
			Sesion.cargarEstados();
		//	Sesion.getCaja().setEstado(edoFinalCaja);
		}catch (BaseDeDatosExcepcion bd)
		{
			bd.printStackTrace();
		} 
	}
	
	
	/**
	 * Método almacenarCiudad
	 * 		
	 * @param estado
	 * @param descripcion
	 * @param codigoArea
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws XmlExcepcion
	 * @throws FuncionExcepcion
	 * @throws ConexionExcepcion
	 */
	public void almacenarCiudad (int estado, String descripcion, String codigoArea) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr
	{
		try
		{
		//	String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "finalizarMantenimiento");
		//	CR.me.verificarAutorizacion("MANTENIMIENTO","finalizarMantenimiento");
			int codigoEstado;
			codigoEstado = ((Estado)Sesion.estados.elementAt(estado - 1)).getCodigo();
			MediadorBD.almacenarCiudad(codigoEstado, descripcion, codigoArea);
		//	Sesion.getCaja().setEstado(edoFinalCaja);
		}catch (BaseDeDatosExcepcion bd)
		{
			bd.printStackTrace();
		} 
	}	

	/**
	 * almacenarUrbanizacion
	 * @param estado
	 * @param ciudad
	 * @param descripcion
	 * @param zona
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws XmlExcepcion
	 * @throws FuncionExcepcion
	 * @throws ConexionExcepcion
	 */
	public void almacenarUrbanizacion (int estado, int ciudad, String descripcion, String zona) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr
	{
		try
		{
		//	String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "finalizarMantenimiento");
		//	CR.me.verificarAutorizacion("MANTENIMIENTO","finalizarMantenimiento");
			int codigoEstado;
			codigoEstado = ((Estado)Sesion.estados.elementAt(estado - 1)).getCodigo();
			int codigoCiudad = ((Ciudad)Sesion.ciudades.elementAt(ciudad - 1)).getCodigo();
			MediadorBD.almacenarUrbanizacion(codigoEstado, codigoCiudad, descripcion, zona);
		//	Sesion.getCaja().setEstado(edoFinalCaja);
		}catch (BaseDeDatosExcepcion bd)
		{
			bd.printStackTrace();
		} 
	}
	
	/**
	 * modificarEstado
	 * @param descripcion
	 * @param posicion
	 * @param modificar
	 */
	public void modificarEstado (String descripcion, int posicion, String modificar) throws UsuarioExcepcion, ExcepcionCr
	{
		try
		{
			//String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "finalizarMantenimiento");
			//CR.me.verificarAutorizacion("MANTENIMIENTO","finalizarMantenimiento");
			int codigoEstado;
			//StringTokenizer codigo = new StringTokenizer((String)Sesion.estados.elementAt(posicion - 1));
			//codigoEstado = Integer.parseInt(codigo.nextToken());
			codigoEstado = ((Estado)Sesion.estados.elementAt(posicion - 1)).getCodigo();
			MediadorBD.modificarEstado(descripcion, codigoEstado, modificar);
			Sesion.cargarEstados();
			//Sesion.getCaja().setEstado(edoFinalCaja);
		}catch (BaseDeDatosExcepcion bd)
		{
			bd.printStackTrace();
		}
	}
	
	/**
	 * modificarCiudad
	 * @param estado
	 * @param posicion
	 * @param descripcion
	 * @param codigoArea
	 * @param modificar
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws XmlExcepcion
	 * @throws FuncionExcepcion
	 * @throws ConexionExcepcion
	 */
	
	public void modificarCiudad (int estado, int posicion, String descripcion, String codigoArea, String modificar) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr
	{
		try
		{
		//	String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "finalizarMantenimiento");
		//	CR.me.verificarAutorizacion("MANTENIMIENTO","finalizarMantenimiento");
			Ciudad datosCiudad = null;
			int codigoCiudad = ((Ciudad)Sesion.ciudades.elementAt(posicion - 1)).getCodigo();
			int codigoEstado;
			codigoEstado = ((Estado)Sesion.estados.elementAt(estado - 1)).getCodigo();
			datosCiudad = new Ciudad(codigoCiudad,descripcion,codigoArea,codigoEstado);
			MediadorBD.modificarCiudad(datosCiudad, modificar);
		//	Sesion.getCaja().setEstado(edoFinalCaja);
		}catch (BaseDeDatosExcepcion bd)
		{
			bd.printStackTrace();
		} 
	}	

	/**
	 * modificarUrbanizacion
	 * @param estado
	 * @param ciudad
	 * @param posicion
	 * @param descripcion
	 * @param zona
	 * @param modificar
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws XmlExcepcion
	 * @throws FuncionExcepcion
	 * @throws ConexionExcepcion
	 */
	public void modificarUrbanizacion (int estado, int ciudad, int posicion, String descripcion, String zona, String modificar) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr
	{
		try
		{
		//	String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "finalizarMantenimiento");
		//	CR.me.verificarAutorizacion("MANTENIMIENTO","finalizarMantenimiento");
			Urbanizacion urbanizacion = null;
			int codigoEstado;
			codigoEstado = ((Estado)Sesion.estados.elementAt(estado - 1)).getCodigo();
			int codigoCiudad = ((Ciudad)Sesion.ciudades.elementAt(ciudad - 1)).getCodigo();
			int codigoUrbanizacion = ((Urbanizacion)Sesion.urbanizaciones.elementAt(posicion - 1)).getCodigo();
			urbanizacion = new Urbanizacion(codigoUrbanizacion,descripcion,zona,codigoEstado,codigoCiudad);
			MediadorBD.modificarUrbanizacion(urbanizacion, modificar);
		//	Sesion.getCaja().setEstado(edoFinalCaja);
		}catch (BaseDeDatosExcepcion bd)
		{
			bd.printStackTrace();
		} 

	}	
	
	/**
	 * eliminarEstado
	 * @param descripcion
	 * @param posicion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws XmlExcepcion
	 * @throws FuncionExcepcion
	 * @throws ConexionExcepcion
	 */

	public void eliminarEstado (String descripcion, int posicion) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr
	{
		try
		{
		//	String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "finalizarMantenimiento");
		//	CR.me.verificarAutorizacion("MANTENIMIENTO","finalizarMantenimiento");
			int codigoEstado;
			codigoEstado = ((Estado)Sesion.estados.elementAt(posicion - 1)).getCodigo();
			MediadorBD.modificarEstado(descripcion, codigoEstado, "9");
			Sesion.cargarEstados();
		//	Sesion.getCaja().setEstado(edoFinalCaja);
		}catch (BaseDeDatosExcepcion bd)
		{
			bd.printStackTrace();
		} 
	}
	
	/**
	 * eliminarCiudad
	 * @param estado
	 * @param posicion
	 * @param descripcion
	 * @param codigoArea
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws XmlExcepcion
	 * @throws FuncionExcepcion
	 * @throws ConexionExcepcion
	 */
	public void eliminarCiudad (int estado, int posicion, String descripcion, String codigoArea) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr
	{
		try
		{
	//		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "finalizarMantenimiento");
	//		CR.me.verificarAutorizacion("MANTENIMIENTO","finalizarMantenimiento");
			Ciudad datosCiudad = null;
			int codigoCiudad = ((Ciudad)Sesion.ciudades.elementAt(posicion - 1)).getCodigo();
			int codigoEstado;
			codigoEstado = ((Estado)Sesion.estados.elementAt(posicion - 1)).getCodigo();
			datosCiudad = new Ciudad(codigoCiudad,descripcion,codigoArea,codigoEstado);
			MediadorBD.modificarCiudad(datosCiudad, "9");
	//		Sesion.getCaja().setEstado(edoFinalCaja);
		}catch (BaseDeDatosExcepcion bd)
		{
			bd.printStackTrace();
		}
	}	
	/**
	 * eliminarUrbanizacion
	 * @param estado
	 * @param ciudad
	 * @param posicion
	 * @param descripcion
	 * @param zona
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws XmlExcepcion
	 * @throws FuncionExcepcion
	 * @throws ConexionExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se eliminó el cast de los vectores 'estados' 'ciudades' 'urbanizaciones'
	* Fecha: agosto 2011
	*/
	public void eliminarUrbanizacion (int estado, int ciudad, int posicion, String descripcion, String zona) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr
	{
		try
		{
		//	String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "finalizarMantenimiento");
		//	CR.me.verificarAutorizacion("MANTENIMIENTO","finalizarMantenimiento");
			Urbanizacion urbanizacion = null;
			int codigoEstado;
			codigoEstado = (Sesion.estados.elementAt(estado - 1)).getCodigo();
			int codigoCiudad = (Sesion.ciudades.elementAt(ciudad - 1)).getCodigo();
			int codigoUrbanizacion = (Sesion.urbanizaciones.elementAt(posicion - 1)).getCodigo();
			urbanizacion = new Urbanizacion(codigoUrbanizacion,descripcion,zona,codigoEstado,codigoCiudad);
			MediadorBD.modificarUrbanizacion(urbanizacion, "9");
		//	Sesion.getCaja().setEstado(edoFinalCaja);
		}catch (BaseDeDatosExcepcion bd)
		{
			bd.printStackTrace();
		} 

	}	


	public void finalizarMantenimiento(boolean servicio) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr
	{
		String edoFinalCaja;
		if (servicio){
			edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "finalizarMantenimiento");
			CR.me.verificarAutorizacion("MANTENIMIENTO","finalizarMantenimiento");
		} else {
			edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "cerrarMantenimiento");
			CR.me.verificarAutorizacion("MANTENIMIENTO","cerrarMantenimiento");
		}
		Sesion.getCaja().setEstado(edoFinalCaja);
	}
}
