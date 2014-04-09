/*
 * $Id: DefaultBuscadorCliente.java,v 1.3.2.1 2005/04/08 20:33:13 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.extensiones.impl
 * Programa		: DefaultBuscadorCliente.java
 * Creado por	: Programa8
 * Creado en 	: 23-dic-2004 9:08:48
 * (C) Copyright 2004 SuperFerretería EPA C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisión	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: DefaultBuscadorCliente.java,v $
 * Revision 1.3.2.1  2005/04/08 20:33:13  programa8
 * Sincronización de afiliados al actualizarse en el servidor para
 * implantar búsqueda solo local de clientes
 *
 * Revision 1.3  2005/03/16 18:56:13  programa8
 * Version 1.0 - Release Candidate 1
 * *- Log de errores en temp/errorsCR.log
 * *- Ajuste de excepciones en curso normal de inicio de aplicacion y repintado de factura
 * *- Reintento de obtención de comprobante fiscal
 * *- Ajuste en monto recaudado de caja
 *
 * Revision 1.2  2005/03/10 15:54:36  programa8
 * CR al 10/03/2005. Merging desde branch STABLE
 *
 * Revision 1.1.6.6  2005/03/07 13:22:23  programa8
 * Integración Versiones Estable e Inestable al 07/03/2005
 *
 * Revision 1.1.2.2  2005/02/28 18:13:50  programa8
 * Version Inestable al 28/02/2005
 *     *-Preparación para trabajar sin administrador de ventanas.
 *     *-Reordenamiento de GUI
 *     *-Mejoras en scroll y pantallas de pagos
 *     *- Mantenimiento de estado en Cliente en Espera
 *     *- Avisos del sistema manejados por la aplicacion
 *     *- Desbloqueo de caja por otros usuarios reparado.
 *
 * Revision 1.1.6.5  2005/02/22 16:42:30  programa8
 * Ajustes en manejo de Clientes:
 * * División del nombre del cliente en nombre y apellido, para mejorar las
 * 	capacidades de búsqueda y facilitar la integración con los sistemas
 * 	de cliente frecuente
 * * Aplicación de extension de caja, para la actualizacion de clientes
 * en el servidor directamente a la estructura EPA
 *
 * Revision 1.1.6.4  2005/02/10 14:49:35  acastillo
 * Ajuste conexiones, resultsets & statements
 *
 * Revision 1.1.6.3  2005/02/02 20:23:12  acastillo
 * Limpieza de código. 0 trailing todo's
 *
 * Revision 1.1.6.2  2005/02/02 20:19:53  acastillo
 * Limpieza de código. 0 trailing todo's
 *
 * Revision 1.1.6.1  2005/01/04 18:23:02  acastillo
 * Integracion CR EPA3 - Costa Rica
 *
 * Revision 1.1.4.1  2005/01/04 16:07:16  acastillo
 * Integracion CR EPA3 - Costa Rica
 *
 * Revision 1.1.2.1  2005/01/03 20:45:53  acastillo
 * Arquitectura de extensiones
 *
 * ===========================================================================
 */
package com.becoblohm.cr.extensiones.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.extensiones.BuscadorCliente;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.mediadoresbd.MediadorBD;

/**
 * <pre>
 * Proyecto: CR 
 * Clase: DefaultBuscadorCliente
 * </pre>
 * <p>
 * <a href="DefaultBuscadorCliente.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - $Author: programa8 $
 * @version $Revision: 1.3.2.1 $ - $Date: 2005/04/08 20:33:13 $
 * @since 23-dic-2004
 * @
 */
public class DefaultBuscadorCliente implements BuscadorCliente {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(DefaultBuscadorCliente.class);

	/**
	 * @since 23-dic-2004
	 * 
	 */
	public DefaultBuscadorCliente() {
		super();
	}

	/* (sin Javadoc)
	 * @see com.becoblohm.cr.extensiones.BuscadorCliente#buscarCliente(java.lang.String)
	 * @param codigo
	 * @return
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 * @since 23-dic-2004
	 */
	
	public Cliente buscarCliente(String codigo) throws ConexionExcepcion,
			BaseDeDatosExcepcion {
		return buscarCliente(codigo, true);
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	public Cliente buscarCliente(String codigo, boolean local) throws ConexionExcepcion,
			BaseDeDatosExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarCliente(String, boolean) - start");
		}

		String codCliente;
		char tipoCliente;
		String ficha;
		String numTienda;
		String nombre;
		String apellido;
		String nit;
		String direccion;
		String dirFiscal;
		String codArea;
		String numTelefono;
		Date fechaAfiliacion;
		boolean exento;
		char estadoCliente;
		char estadoColaborador;
		Cliente clienteRetorno;
		String sentenciaSQL;
		ResultSet rs = MediadorBD.buscarCliente(codigo, local);
		try {
			if(rs.first()) {
				codCliente = rs.getString("codafiliado");
				tipoCliente = (char)(rs.getString("tipoafiliado").toCharArray()[0]);
				ficha = rs.getString("numficha");
				numTienda = rs.getString("numtienda");
				nombre = rs.getString("nombre");
				apellido = rs.getString("apellido");
				nit = rs.getString("nitcliente");
				direccion = rs.getString("direccion");
				dirFiscal = rs.getString("direccionfiscal");
				codArea = rs.getString("codarea");
				numTelefono = rs.getString("numtelefono");
				fechaAfiliacion = rs.getDate("fechaafiliacion");
				exento = (rs.getString("exentoimpuesto").toCharArray()[0] == Sesion.SI)
													? true : false;
				estadoCliente = (char)(rs.getString("estadoafiliado").toCharArray()[0]);
				estadoColaborador = (char)(rs.getString("estadocolaborador").toCharArray()[0]);
					
				// Creamos el cliente a retornar
				clienteRetorno = new Cliente(codCliente, tipoCliente, ficha, numTienda, nombre, apellido, nit, direccion, dirFiscal, codArea, numTelefono, fechaAfiliacion, exento, estadoCliente, estadoColaborador);

				ArrayList <StringBuffer>mensajes = MediadorBD.obtenerMensajesCliente(codCliente);
				clienteRetorno.setMensajes(mensajes);
			} else {
				clienteRetorno = null;
			}
		} catch (SQLException e) {
			logger.error("buscarCliente(String, boolean)", e);

			throw new BaseDeDatosExcepcion("Error al buscar cliente", e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e1) {
				logger.error("buscarCliente(String, boolean)", e1);
			}
			rs = null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("buscarCliente(String, boolean) - end");
		}
		return clienteRetorno;
	}

}
