/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.sincronizador
 * Programa   : SincronizarCajaServidor.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 22/03/2004 09:05:26 AM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1
 * Fecha       : 20/06/2006 
 * Analista    : yzambrano
 * Descripción : Se agregó la sincronización de clientes temporales y afiliados
 * =============================================================================
 * Versión     : 1.1.2
 * Fecha       : 11/11/2004 03:35:26 PM
 * Analista    : Programador8 - Arístides Castillo
 * Descripción : Adaptación de código para evitar la terminación del hilo por excepcion
 * 				 en el sincronizador
 * =============================================================================
 * Versión     : 1.1.1
 * Fecha       : 26/05/2004 11:57:26 AM
 * Analista    : Programador3
 * Descripción : Agregado el método syncAbono que permite la sincronización de las 
 * 				transacciones de abono de forma atómica por cada transacción.
 * =============================================================================
 * Versión     : 1.1
 * Fecha       : 19/03/2004 11:57:26 AM
 * Analista    : Programador3
 * Descripción : Implementación inicial.
 * =============================================================================

 */
package com.becoblohm.cr.sincronizador;

import java.sql.SQLException;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.manejadorauditoria.Auditoria;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.mediadoresbd.BeansSincronizador;

/**
 * Descripción:
 * 
 */

public class SincronizarCajaServidor extends TimerTask {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(SincronizarCajaServidor.class);
	static {
		logger.setAdditivity(false);
	}
	public static Sincronizador sincronizador = null;
	
	/**
	 * Constructor para SincronizarServidorCaja.java
	 *
	 * 
	 */
	public SincronizarCajaServidor(Sincronizador sync) {
		super();
		sincronizador = sync;
	}

// las llamadas a los diferentes metodos se colocaron dentro de un try y catch para dependieran de si mismas
// y si ocurriera un error en las primeras llamadas se continuaran sincronizando las demás. Wdiaz
	public void run() {
		if (Sesion.isCajaEnLinea() ){
			try {
			
				SyncCnxMonitor.getInstance().notifyIn();
				if (logger.isDebugEnabled()) {
					logger.debug("run() - Sincronizando CR --> Servidor");
				}
				try {
					BeansSincronizador.syncEntidadSrv("caja");
				} catch (BaseDeDatosExcepcion e) {
					Auditoria.registrarAuditoria("Falla de conexión BD en sincronización CR (caja) -> Servidor.", 'E');			
					logger.error("run()", e);
				} catch (SQLException e) {
					Auditoria.registrarAuditoria("Falla de acceso a BD en sincronización CR (caja) -> Servidor.", 'E');
					logger.error("run()", e);
				}
				try {
					BeansSincronizador.syncEntidadSrv("servicio");
				} catch (BaseDeDatosExcepcion e) {
					Auditoria.registrarAuditoria("Falla de conexión BD en sincronización CR (servicio) -> Servidor.", 'E');			
					logger.error("run()", e);
				} catch (SQLException e) {
					Auditoria.registrarAuditoria("Falla de acceso a BD en sincronización CR (servicio) -> Servidor.", 'E');
					logger.error("run()", e);
				}
				try {
					BeansSincronizador.syncEntidadSrv("detalleservicio");
				} catch (BaseDeDatosExcepcion e) {
					Auditoria.registrarAuditoria("Falla de conexión BD en sincronización CR (detalleservicio) -> Servidor.", 'E');			
					logger.error("run()", e);
				} catch (SQLException e) {
					Auditoria.registrarAuditoria("Falla de acceso a BD en sincronización CR (detalleservicio) -> Servidor.", 'E');
					logger.error("run()", e);
				}
				SincUtil.getInstance().syncAbonos();
				try {
					BeansSincronizador.syncEntidadSrv("auditoria");
				} catch (BaseDeDatosExcepcion e) {
					Auditoria.registrarAuditoria("Falla de conexión BD en sincronización CR (auditoria) -> Servidor.", 'E');			
					logger.error("run()", e);
				} catch (SQLException e) {
					Auditoria.registrarAuditoria("Falla de acceso a BD en sincronización CR (auditoria) -> Servidor.", 'E');
					logger.error("run()", e);
				}
				try {
					BeansSincronizador.syncEntidadSrv("planificador");
				} catch (BaseDeDatosExcepcion e) {
					Auditoria.registrarAuditoria("Falla de conexión BD en sincronización CR (planificacion) -> Servidor.", 'E');			
					logger.error("run()", e);
				} catch (SQLException e) {
					Auditoria.registrarAuditoria("Falla de acceso a BD en sincronización CR (planificacion) -> Servidor.", 'E');
					logger.error("run()", e);
				}
				try {
					BeansSincronizador.syncClientesTemporales(true);
				} catch (BaseDeDatosExcepcion e) {
					Auditoria.registrarAuditoria("Falla de conexión BD en sincronización CR (afiliado) -> Servidor.", 'E');			
					logger.error("run()", e);
				} catch (SQLException e) {
					Auditoria.registrarAuditoria("Falla de acceso a BD en sincronización CR (afiliado) -> Servidor.", 'E');
					logger.error("run()", e);
				}
				try {
					BeansSincronizador.syncEntidadSrv("detalleafiliado");
				} catch (BaseDeDatosExcepcion e) {
					Auditoria.registrarAuditoria("Falla de conexión BD en sincronización CR (detalleafiliado) -> Servidor.", 'E');			
					logger.error("run()", e);
				} catch (SQLException e) {
					Auditoria.registrarAuditoria("Falla de acceso a BD en sincronización CR (detalleafiliado) -> Servidor.", 'E');
					logger.error("run()", e);
				}
				
				// Sincronizacion de Bonos Regalo Electronico
				try {
					SincUtil.getInstance().syncTransaccionesBR();
				} catch (BaseDeDatosExcepcion e) {
					Auditoria.registrarAuditoria("Falla de conexión BD en sincronización CR -> Servidor.", 'E');			
					logger.error("run()", e);
				} catch (SQLException e) {
					Auditoria.registrarAuditoria("Falla de acceso a BD en sincronización CR -> Servidor.", 'E');
					logger.error("run()", e);
				}

				if (sincronizador.isActCajaTemporizada()) {
					try {
						BeansSincronizador.actualizarEntidadesSistema(false);
					} catch (BaseDeDatosExcepcion e) {
						Auditoria.registrarAuditoria("Falla de conexión BD en sincronización CR -> Servidor.", 'E');			
						logger.error("run()", e);
					} catch (SQLException e) {
						Auditoria.registrarAuditoria("Falla de acceso a BD en sincronización CR -> Servidor.", 'E');
						logger.error("run()", e);
					}
				} else {
					try {
						BeansSincronizador.syncEntidadSrv("usuario");
					} catch (BaseDeDatosExcepcion e) {
						Auditoria.registrarAuditoria("Falla de conexión BD en sincronización CR (usuario) -> Servidor.", 'E');			
						logger.error("run()", e);
					} catch (SQLException e) {
						Auditoria.registrarAuditoria("Falla de acceso a BD en sincronización CR (usuario) -> Servidor.", 'E');
						logger.error("run()", e);
					}
				}
			} catch (Throwable t) {
			   logger.error("run()", t);
			   } finally {
			     SyncCnxMonitor.getInstance().notifyOut();
				}
		} else if(CR.me != null)
			CR.me.setCajaSincronizada(false); 
		
	}
		

}