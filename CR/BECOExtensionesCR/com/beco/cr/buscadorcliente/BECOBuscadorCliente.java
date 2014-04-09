/*
 * ===========================================================================
 * Material Propiedad CentroBeco C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.beco.cr.buscadorcliente
 * Programa		: BuscadorCliente.java
 * Creado por	: Ileana Rojas
 * Creado en 	: 08-sep-2005 9:51:00
 * (C) Copyright 2005 CentroBeco C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * ===========================================================================
 */
package com.beco.cr.buscadorcliente;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ClienteExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.extensiones.BuscadorCliente;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.utiles.Validaciones;

/**
 * <pre>
 * Proyecto: CR 
 * Clase: BuscadorCliente
 * </pre>
 * <p>
 * <a href="BuscadorCliente.java.html"><i>View Source</i></a>
 * </p>
 * 
 */
public class BECOBuscadorCliente implements BuscadorCliente {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(BuscadorCliente.class);

	/**
	 * 
	 * 
	 */
	public BECOBuscadorCliente() {
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
			BaseDeDatosExcepcion, ClienteExcepcion {
		return buscarCliente(codigo, true);
	}


	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	public Cliente buscarCliente(String codigo, boolean local) throws ConexionExcepcion,
			BaseDeDatosExcepcion, ClienteExcepcion {
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
		String codArea1;
		String numTelefono;
		String numTelefono1;
		Date fechaAfiliacion;
		boolean exento;
		boolean contactar;
		char estadoCliente;
		char estadoColaborador;
		//cambio CRM wdiaz noviembre 2008
		char sexo=' ';
		char estadoCivil=' ';
		Date fechaNaci;
		String zonaResidencial="";
		//fin
		String email;
		Cliente clienteRetorno;
		ResultSet rs = MediadorBD.buscarCliente(codigo.trim(), local);
		try {
			if(rs.first()) {
				codCliente = rs.getString("codafiliado").trim();
				tipoCliente = (char)(rs.getString("tipoafiliado").toCharArray()[0]);
				ficha = rs.getString("numficha") != null && rs.getString("numficha").equals("")? null:rs.getString("numficha");
				numTienda = rs.getString("numtienda").trim();
				nombre = rs.getString("nombre").trim();
				apellido = rs.getString("apellido")==null ? null:rs.getString("apellido").trim();
				nit = rs.getString("nitcliente")==null ? null:rs.getString("apellido").trim();
				direccion = rs.getString("direccion").trim();
				dirFiscal = rs.getString("direccionfiscal")==null ? null:rs.getString("direccionfiscal").trim();
				codArea = rs.getString("codarea");
				codArea1 = rs.getString("codarea1");
				numTelefono = rs.getString("numtelefono");
				numTelefono1 = rs.getString("numtelefono1");
				fechaAfiliacion = rs.getDate("fechaafiliacion");
				email = rs.getString("email")==null ? null:rs.getString("email").trim();
				exento = (rs.getString("exentoimpuesto").toCharArray()[0] == Sesion.SI)
													? true : false;
				estadoCliente = (char)(rs.getString("estadoafiliado").toCharArray()[0]);
				estadoColaborador = (char)(rs.getString("estadocolaborador").toCharArray()[0]);
				contactar = (rs.getString("contactar").toCharArray()[0] == Sesion.SI)
							? true : false;
							
							
				//****** Fecha Actualización 22/02/2007
				//Cambio de BECO para agregar validaciones de RIF y CI para el SENIAT
				//*******
				String ci = "";
				char tipoCte;
				 try {
					Integer.parseInt(codCliente.trim());
					ci = codCliente.trim();
					tipoCte = tipoCliente;
				 } catch (NumberFormatException e){
					tipoCte = (char)(codCliente.trim().toCharArray()[0]);
				/*	codCliente = codCliente.replace('N', ' ');
					codCliente = codCliente.replace('V', ' ');
					codCliente = codCliente.replace('E', ' ');
					codCliente = codCliente.replace('J', ' ');
					codCliente = codCliente.replace('G', ' ');
					codCliente = codCliente.replace('P', ' ');
					codCliente = codCliente.replace('D', ' ');
					codCliente = codCliente.replace('-', ' ');
					ci = codCliente.trim();*/
					ci = codCliente.trim().substring(2);
				 }
				 
				// Nuevos Campos en la Tabla afiliados
				 if ((rs.getString("genero")!=null && !(rs.getString("genero").equals(""))))
				     sexo = (char)(rs.getString("genero").toCharArray()[0]);
				 if (rs.getString("genero")!=null && !(rs.getString("estadocivil").equals("")))
					 estadoCivil=(char)(rs.getString("estadocivil").toCharArray()[0]);
				 
				 fechaNaci = rs.getDate("fechanacimiento");
				 zonaResidencial = rs.getString("direccionfiscal");
				 //fin de los nuevos campos. wdiaz
				 
				Validaciones validador = new Validaciones();
				boolean cedRifValido = validador.validarRifCedula(ci, tipoCte);
				if(!cedRifValido) {
					throw (new ClienteExcepcion("CI/RIF Inválido.\nNo se puede utilizar este cliente."));
				}
				//**************************
				
				// Creamos el cliente a retornar
				clienteRetorno = new Cliente(codCliente, tipoCliente, ficha, numTienda, nombre, apellido, nit, direccion, dirFiscal, codArea, numTelefono, codArea1, numTelefono1, fechaAfiliacion, email, exento, contactar, estadoCliente, estadoColaborador,sexo,estadoCivil,fechaNaci,zonaResidencial);
				
				ArrayList<StringBuffer> mensajes = MediadorBD.obtenerMensajesCliente(codCliente);
				clienteRetorno.setMensajes(mensajes);
			} else {
				clienteRetorno = null;
			}
		} catch (ClienteExcepcion e) {
			logger.error("buscarCliente(String, boolean)", e);
			throw e;
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
