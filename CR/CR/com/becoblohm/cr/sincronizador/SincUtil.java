/*
 * $Id: SincUtil.java,v 1.2.2.1 2005/04/08 20:43:05 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.sincronizador
 * Programa		: SincUtil.java
 * Creado por	: Programa8
 * Creado en 	: 04-ene-2005 13:17:27
 * (C) Copyright 2005 SuperFerretería EPA C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisión	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: SincUtil.java,v $
 * Revision 1.2.2.1  2005/04/08 20:43:05  programa8
 * Sincronización de afiliado actualizado
 *
 * Revision 1.2  2005/03/10 15:54:40  programa8
 * CR al 10/03/2005. Merging desde branch STABLE
 *
 * Revision 1.1.2.8  2005/03/07 12:55:33  programa8
 * Integración Versiones Estable e Inestable al 07/03/2005
 *
 * Revision 1.1.4.1  2005/02/28 18:39:14  programa8
 * Version Inestable al 28/02/2005
 *     *-Preparación para trabajar sin administrador de ventanas.
 *     *-Reordenamiento de GUI
 *     *-Mejoras en scroll y pantallas de pagos
 *     *- Mantenimiento de estado en Cliente en Espera
 *     *- Avisos del sistema manejados por la aplicacion
 *     *- Desbloqueo de caja por otros usuarios reparado.
 *
 * Revision 1.1.2.7  2005/02/22 16:42:31  programa8
 * Ajustes en manejo de Clientes:
 * * División del nombre del cliente en nombre y apellido, para mejorar las
 * 	capacidades de búsqueda y facilitar la integración con los sistemas
 * 	de cliente frecuente
 * * Aplicación de extension de caja, para la actualizacion de clientes
 * en el servidor directamente a la estructura EPA
 *
 * Revision 1.1.2.6  2005/02/10 14:49:35  acastillo
 * Ajuste conexiones, resultsets & statements
 *
 * Revision 1.1.2.5  2005/02/02 20:23:12  acastillo
 * Limpieza de código. 0 trailing todo's
 *
 * Revision 1.1.2.4  2005/02/02 20:19:54  acastillo
 * Limpieza de código. 0 trailing todo's
 *
 * Revision 1.1.2.3  2005/01/28 19:56:36  acastillo
 * Caja Registradora EPA3 - CR RC3
 *
 * Revision 1.1.2.2  2005/01/18 20:26:42  acastillo
 * Ajustes EPA3
 *
 * Revision 1.1.2.1  2005/01/04 18:23:02  acastillo
 * Integracion CR EPA3 - Costa Rica
 *
 * ===========================================================================
 */
package com.becoblohm.cr.sincronizador;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.extensiones.BuscadorClienteServidor;
import com.becoblohm.cr.extensiones.BuscadorClienteServidorFactory;
import com.becoblohm.cr.extensiones.ManejoPagosFactory;
import com.becoblohm.cr.extensiones.RegistroCliente;
import com.becoblohm.cr.extensiones.RegistroClienteFactory;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.mediadoresbd.BeansSincronizador;

/**
 * <pre>
 * Proyecto: CR 
 * Clase: SincUtil
 * </pre>
 * <p>
 * <a href="SincUtil.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - $Author: programa8 $
 * @version $Revision: 1.2.2.1 $ - $Date: 2005/04/08 20:43:05 $
 * @since 04-ene-2005
 * @
 */
public class SincUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SincUtil.class);
	static {
		logger.setAdditivity(false);
	}
	/**
	 * @since 04-ene-2005
	 * 
	 */
	
	private static SincUtil instance;
	
	protected SincUtil() {
		super();
	}
	
	public static SincUtil getInstance() {
		if (instance == null) {
			instance = new SincUtil();
		}
		return instance;
	}
	
	/**
	 * Método syncTransaccion
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	private void syncTransaccion() throws SQLException, BaseDeDatosExcepcion {
		ResultSet transaccionesCR = null;
		try {
			transaccionesCR = BeansSincronizador.getTransacciones(true, true);
			transaccionesCR.last();
			if (logger.isInfoEnabled()) {
				logger
						.info("syncTransaccion() - Transacciones sin actualizar en CR: "
								+ transaccionesCR.getRow());
			}
			transaccionesCR.beforeFirst();
			if (Sesion.isCajaEnLinea()){
				BeansSincronizador.syncTransacciones(transaccionesCR);
				}
		} finally {
			if (transaccionesCR != null) {
				transaccionesCR.close();
				transaccionesCR = null;
			}
		}
	}

	/**
	 * Método syncAbono
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	private void syncAbono() throws SQLException, BaseDeDatosExcepcion {
		ResultSet abonosCR = null;
		try {
			abonosCR = BeansSincronizador.getAbonos(true, true);
			abonosCR.last();
			if (logger.isInfoEnabled()) {
				logger
						.info("syncAbono() - Transacciones de  Abonos sin actualizar en CR: "
								+ abonosCR.getRow());
			}
			abonosCR.beforeFirst();
			if (Sesion.isCajaEnLinea()) BeansSincronizador.syncAbonos(abonosCR);
		} finally {
			if (abonosCR != null) {
				abonosCR.close();
				abonosCR = null;
			}
		}
	}
	
	
	public synchronized void syncTransacciones() {
		RegistroClienteFactory factory = new RegistroClienteFactory();
		RegistroCliente registro = factory.getInstance();
		try {
			registro.actualizarClientesSrv();
		} catch (Exception e) {
			logger.error("syncTransacciones()", e);
		}
		try {
			syncTransaccion();
		} catch (Exception e) {
			logger.error("syncTransacciones()", e);
		}
		try {
			registro.actualizarTransaccionafiliadocrm();
		} catch (Exception e) {
			logger.error("syncTransacciones()", e);
		}
		try {
			BeansSincronizador.syncEntidadSrv("devolucionventa");
		} catch (Exception e) {
			logger.error("syncTransacciones()", e);
		}
		
		try {
			ManejoPagosFactory.getInstance().sincronizarDatosExtraPagos();
		} catch (BaseDeDatosExcepcion e1) {
			logger.error("syncTransacciones()", e1);
		}
	}
	
	public synchronized void syncAbonos() {
		try {
			syncAbono();
			BeansSincronizador.syncEntidadSrv("anulaciondeabonos");
			ManejoPagosFactory.getInstance().sincronizarDatosExtraPagos();
		} catch (BaseDeDatosExcepcion e) {
			logger.error("syncAbonos()", e);
		} catch (SQLException e) {
			logger.error("syncAbonos()", e);
		}
	}
	
	public synchronized void syncAfiliado (String codAfiliado) {
		if (logger.isDebugEnabled()) {
			logger.debug("syncAfiliado(String) - start");
		}

		BuscadorClienteServidorFactory factory = new BuscadorClienteServidorFactory();
		BuscadorClienteServidor buscador = factory.getInstance();
		try {
			ResultSet rs = buscador.buscarDatosCliente(codAfiliado);
			try {
				if (rs.next()) {
					BeansSincronizador.sincronizarCliente(rs);
				}
			} catch (SQLException e1) {
				logger.error("syncAfiliado(String)", e1);
			} finally {
				try {
					rs.close();
				} catch (SQLException e2) {
					logger.error("syncAfiliado(String)", e2);
				}
			}
		} catch (BaseDeDatosExcepcion e) {
			logger.error("syncAfiliado(String)", e);
		} catch (ConexionExcepcion e) {
			logger.error("syncAfiliado(String)", e);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("syncAfiliado(String) - end");
		}
	}

	/**
	 * Método syncTransaccionesBR
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public void syncTransaccionesBR() throws SQLException, BaseDeDatosExcepcion {
		ResultSet transaccionesBR_CR = null;
		try {
			transaccionesBR_CR = BeansSincronizador.getTransaccionesBR(true, true);
			transaccionesBR_CR.last();
			if (logger.isInfoEnabled()) {
				logger
						.info("syncTransaccionesBR() - Transacciones Bono Regalo sin actualizar en CR: "
								+ transaccionesBR_CR.getRow());
			}
			transaccionesBR_CR.beforeFirst();
			if (Sesion.isCajaEnLinea()){
				BeansSincronizador.syncTransaccionesBR(transaccionesBR_CR);
			}
		} finally {
			if (transaccionesBR_CR != null) {
				transaccionesBR_CR.close();
				transaccionesBR_CR = null;
			}
		}
	}

}
