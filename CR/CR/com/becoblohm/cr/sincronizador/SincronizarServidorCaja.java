/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.sincronizador
 * Programa   : SincronizarServidorCaja.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 19/03/2004 11:57:26 AM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 2.0
 * Fecha       : 23/01/2004 12:00:26 PM
 * Analista    : Programador8 - Arístides Castillo
 * Descripción : Preparación para sincronización por solicitud remota
 * =============================================================================
 * Versión     : 1.1.1
 * Fecha       : 11/11/2004 03:35:26 PM
 * Analista    : Programador8 - Arístides Castillo
 * Descripción : Adaptación de código para evitar la terminación del hilo por excepcion
 * 				 en el sincronizador
 * =============================================================================
 * Versión     : 1.1
 * Fecha       : 26/05/2004 03:35:26 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Eliminados los métodos syncProductos, syncEntidad y cargarDataInicial.
 * =============================================================================
 * Versión     : 1.1
 * Fecha       : 19/03/2004 11:57:26 AM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Implementación inicial.
 * =============================================================================
 */
package com.becoblohm.cr.sincronizador;

import java.sql.SQLException;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.extensiones.ManejoPagosFactory;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.mediadoresbd.BeansSincronizador;

/**
 * Descripción:
 * 
 */

public class SincronizarServidorCaja extends TimerTask {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(SincronizarServidorCaja.class);

	static {
		logger.setAdditivity(false);
	}
	public static Sincronizador sincronizador = null; 

	/**
	 * Constructor para SincronizarServidorCaja.java
	 *
	 * 
	 */
	public SincronizarServidorCaja(Sincronizador sync) {
		super();
		sincronizador = sync;
	}

	/**
	 * Método syncPromocion
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
/*	public void syncPromocion() throws SQLException, BaseDeDatosExcepcion {
		ResultSet promocionesSRV = null;
		try {
			promocionesSRV = BeansSincronizador.getPromociones(false);
			promocionesSRV.last();
			if (logger.isInfoEnabled()) {
				logger
						.info("syncPromocion() - Promociones sin actualizar a la CR: "
								+ promocionesSRV.getRow());
			}
			promocionesSRV.beforeFirst();
			if (Sesion.isCajaEnLinea()) BeansSincronizador.syncPromociones(promocionesSRV);
			
		} finally {
			if (promocionesSRV != null) {
				promocionesSRV.close();
				promocionesSRV = null;
			}
		}
	}


/* las llamadas a los diferentes metodos se colocaron dentro de un try y catch para dependieran de si mismas
 y si ocurriera un error en las primeras llamadas se continuaran sincronizando las demás. Wdiaz*/
	
	public void run() {
		if (Sesion.isCajaEnLinea()){
			try {
				SyncCnxMonitor.getInstance().notifyIn();
				if (sincronizador.isActCajaTemporizada()) {
					if (logger.isInfoEnabled()) {
						logger.info("run() - Sincronizando Servidor --> CR");
					}
					// Verificar preferencia de garantizar facturación
					char sincronizacionInicial=' ';
					try {
						sincronizacionInicial = InitCR.preferenciasCR.getConfigStringForParameter("db", "garantizarFacturacion").toUpperCase().charAt(0);
					} catch (Throwable t) {
						logger.error("run()", t);
					}
					if( sincronizacionInicial == Sesion.NO){
					
						if (sincronizador.isSyncAfiliados()) {
							try {
								BeansSincronizador.syncEntidadCR("afiliado");
							} catch (BaseDeDatosExcepcion e) {
								logger.error("run()", e);
								new BaseDeDatosExcepcion(e.getMensaje());
							} catch (SQLException e) {
								logger.error("run()", e);
								new BaseDeDatosExcepcion(e.getMessage());
							}
						}
				/*		//codigo nuevo crm wdiaz
						try {
							BeansSincronizador.syncEntidadCR("transaccionafiliadocrm");
						} catch (BaseDeDatosExcepcion e) {
							logger.error("run()", e);
							new BaseDeDatosExcepcion(e.getMensaje());
						} catch (SQLException e) {
							logger.error("run()", e);
							new BaseDeDatosExcepcion(e.getMessage());
						}
						//fin*/
						try {
							BeansSincronizador.syncEntidadCR("detalleafiliado");
						} catch (BaseDeDatosExcepcion e) {
							logger.error("run()", e);
							new BaseDeDatosExcepcion(e.getMensaje());
						} catch (SQLException e) {
							logger.error("run()", e);
							new BaseDeDatosExcepcion(e.getMessage());
						}
						try {
							BeansSincronizador.syncEntidadCR("producto");
						} catch (BaseDeDatosExcepcion e) {
							logger.error("run()", e);
							new BaseDeDatosExcepcion(e.getMensaje());
						} catch (SQLException e) {
							logger.error("run()", e);
							new BaseDeDatosExcepcion(e.getMessage());
						}
						try {
							BeansSincronizador.syncEntidadCR("prodcodigoexterno");
						} catch (BaseDeDatosExcepcion e) {
							logger.error("run()", e);
							new BaseDeDatosExcepcion(e.getMensaje());
						} catch (SQLException e) {
							logger.error("run()", e);
							new BaseDeDatosExcepcion(e.getMessage());
						}
						try {
							BeansSincronizador.syncEntidadCR("promocion");
						} catch (BaseDeDatosExcepcion e) {
							logger.error("run()", e);
							new BaseDeDatosExcepcion(e.getMensaje());
						} catch (SQLException e) {
							logger.error("run()", e);
							new BaseDeDatosExcepcion(e.getMessage());
						}
						try {
							BeansSincronizador.syncEntidadCR("detallepromocion");
						} catch (BaseDeDatosExcepcion e) {
							logger.error("run()", e);
							new BaseDeDatosExcepcion(e.getMensaje());
						} catch (SQLException e) {
							logger.error("run()", e);
							new BaseDeDatosExcepcion(e.getMessage());
						}					
						
						//*** 11/11/2008 IROJAS. Agregada sincronización de promociones adiconales para BECO
						BeansSincronizador.syncEntidadCR("promocionesextendidas");
						//***
						
						//WDIAZ: Esta funcion estaba fuera de la condicion y se ejecutaba cada vez que se activaba el hilo.
						try {
							ManejoPagosFactory.getInstance().sincronizarDatosMaestroPagos();
						} catch (BaseDeDatosExcepcion e) {
							logger.error("run()", e);
							new BaseDeDatosExcepcion(e.getMensaje());
						}
					}

					try {
						BeansSincronizador.syncEntidadCR("cambiodeldia");
					} catch (BaseDeDatosExcepcion e) {
						logger.error("run()", e);
						new BaseDeDatosExcepcion(e.getMensaje());
					} catch (SQLException e) {
						logger.error("run()", e);
						new BaseDeDatosExcepcion(e.getMessage());
					}
					
					//Entidades Bidireccionales
					try {
						BeansSincronizador.actualizarEntidadesSistema(true);
					} catch (BaseDeDatosExcepcion e) {
						logger.error("run()", e);
						new BaseDeDatosExcepcion(e.getMensaje());
					} catch (SQLException e) {
						logger.error("run()", e);
						new BaseDeDatosExcepcion(e.getMessage());
					}
					/*try {
						ManejoPagosFactory.getInstance().sincronizarDatosMaestroPagos();
					} catch (BaseDeDatosExcepcion e) {
						logger.error("run()", e);
						new BaseDeDatosExcepcion(e.getMensaje());
					}*/
				} else {
					try {
						BeansSincronizador.syncEntidadCR("usuario");
					} catch (BaseDeDatosExcepcion e) {
						logger.error("run()", e);
						new BaseDeDatosExcepcion(e.getMensaje());
					} catch (SQLException e) {
						logger.error("run()", e);
						new BaseDeDatosExcepcion(e.getMessage());
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
	
	public synchronized void syncCaja (String entidad) throws BaseDeDatosExcepcion {
		if (Sesion.isCajaEnLinea()) { 
			try {
				SyncCnxMonitor.getInstance().notifyIn();
				if (logger.isInfoEnabled()) {
					logger
							.info("syncCaja(String) - Sincronizando Servidor --> CR");
				}
				BeansSincronizador.syncEntidadCR(entidad);
			} catch (BaseDeDatosExcepcion e) {
				logger.error("run()", e);
				throw new BaseDeDatosExcepcion(e.getMensaje());
			} catch (SQLException e) {
				logger.error("run()", e);
				throw new BaseDeDatosExcepcion(e.getMessage());
			} catch (Throwable t) {
				logger.error("syncCaja(String)", t);
			} finally {
				SyncCnxMonitor.getInstance().notifyOut();
			}
		} else if(CR.me != null)
			CR.me.setCajaSincronizada(false);
	}

}