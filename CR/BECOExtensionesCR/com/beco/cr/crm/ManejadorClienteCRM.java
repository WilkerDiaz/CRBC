package com.beco.cr.crm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.AfiliadoUsrExcepcion;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstadoServicio;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.mediadoresbd.Conexiones;
import com.becoblohm.cr.utiles.Validaciones;

public class ManejadorClienteCRM {
	
	private static final Logger logger = Logger.getLogger(MaquinaDeEstadoServicio.class);
	
	public static void registrarClienteTemporal(Cliente cliente) throws BaseDeDatosExcepcion, ConexionExcepcion, AfiliadoUsrExcepcion {
		registrarClienteTemporal(cliente, true);
	}
	
	/** wilker Díaz
	 * Registra el cliente Para el Proyecto CRM en la Base de Datos.
	 * @param cliente Cliente que se registrara en la tabla "Afiliado" en la base de datos con tipoAfiliado="C"(Caja).
	 * @param local Destino de Registro del Cliente (Local o Remoto).
	 * @throws BaseDeDatosExcepcion Si ocurre un error con la insercion en la Base de Datos.
	 */
	public static void registrarClienteTemporal(Cliente cliente, boolean local) throws BaseDeDatosExcepcion, ConexionExcepcion, AfiliadoUsrExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("registrarClienteTemporal(Cliente) - start");
		}

		// Registramos al cliente
		try {		
			SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
			String fechaReg = fecha.format(Sesion.getHoraSistema());
			char contactar = ' ';
			if(cliente.isContactar())
				contactar = Sesion.SI;
			else 
				contactar = Sesion.NO;

			Timestamp actualizacion = new Timestamp(Calendar.getInstance().getTime().getTime());				
		
			//Verificamos si ya existía este cliente temporal
			 String sentenciaSQL = "select * from afiliado where codafiliado = '" + cliente.getCodCliente() + "'";
			 ResultSet resultado = Conexiones.realizarConsulta(sentenciaSQL,local);
			 try {
				 if (!resultado.first()) {
					sentenciaSQL = "insert into afiliado (codafiliado,tipoafiliado,nombre,apellido,numtienda," +
						"direccionfiscal,direccion,email,codarea,numtelefono,codarea1,numtelefono1," +
						"fechaafiliacion,registrado, contactar, actualizacion) "
						+ "values ('" + cliente.getCodCliente().trim() + "', " 
						+ "'" + cliente.getTipoCliente() + "', "
						+ "'" + Validaciones.validarString(cliente.getNombre())+ "', "
						+ "'" + Validaciones.validarString(cliente.getApellido())+ "', "
						+ Sesion.getNumTienda() + ", "
						+ "'" + Validaciones.validarString(cliente.getDireccionAlmacenada())+ "', "
						+ "'>>>>>', "
						+ "'" + Validaciones.validarString(cliente.getEmail()) + "', "
						+ "'" + cliente.getCodArea() + "', "
						+ "'" + cliente.getNumTelefono() + "', "
						+ "'" + cliente.getCodAreaSec() + "', "
						+ "'" + cliente.getNumTelefonoSec() + "', "
						+ "'" + fechaReg + "', "
						+ "'" + Sesion.CLIENTE_REGISTRADO + "', "
						+ "'" + contactar + "', "
						+ "'" + actualizacion + "')";
					
					Conexiones.realizarSentencia(sentenciaSQL,local);
				 } else {
					//char registrado = (char)(resultado.getString("registrado").toCharArray()[0]);
					//Verficamos si el cliente es registrado (temporal) o es un cliente afiliado
					//Si es un afiliado se lanza una excepción para indicar que no se puede hacer esta operación
					 sentenciaSQL = "update afiliado set " 
							+ "tipoafiliado = '" + cliente.getTipoCliente() + "', "
							+ "nombre = '" + Validaciones.validarString(cliente.getNombre()) + "', "
							+ "apellido = '" + Validaciones.validarString(cliente.getApellido())+ "', "
							+ "direccionfiscal = '" + Validaciones.validarString(cliente.getDireccionAlmacenada()) + "', "
							+ "codarea = '" + cliente.getCodArea() + "', "
							+ "numtelefono = '" + cliente.getNumTelefono() + "', "
							+ "actualizacion = '" + actualizacion + "'"
							+ " where codafiliado = '" + cliente.getCodCliente() + "'";
					Conexiones.realizarSentencia(sentenciaSQL,local);
				 }
			 } finally {
				resultado.close();
			}
		} catch (ConexionExcepcion e) {
			logger.error("registrarClienteTemporal(Cliente)", e);

			throw (new BaseDeDatosExcepcion("Error de conexión con BD al registrar cliente NO afiliado"));
		} catch (BaseDeDatosExcepcion e) {
			logger.error("registrarClienteTemporal(Cliente)", e);

			throw (new BaseDeDatosExcepcion("Error en ejecución del query al registrar cliente NO afiliado"));
		} catch (SQLException e) {
			logger.error("registrarClienteTemporal(Cliente)", e);

			throw (new BaseDeDatosExcepcion("Error en sentencia SQL al registrar cliente NO afiliado"));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("registrarClienteTemporal(Cliente) - end");
		}
	}
}
