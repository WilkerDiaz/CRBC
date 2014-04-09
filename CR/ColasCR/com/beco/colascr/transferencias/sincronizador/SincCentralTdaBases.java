/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.colascr.transferencias.sincronizador
 * Programa   : SincCentralTdaBases.java
 * Creado por : Ileana Rojas, Gabriel Martinelli
 * Creado en  : 21/02/2005 04:16:59 PM
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
package com.beco.colascr.transferencias.sincronizador;

import java.sql.SQLException;
import java.util.TimerTask;

import com.beco.colascr.transferencias.comunicacionbd.BeansSincronizador;
import com.beco.colascr.transferencias.comunicacionbd.Conexiones;
import com.beco.colascr.transferencias.configuracion.PoliticaTarea;
import com.beco.colascr.transferencias.configuracion.Sesion;
import com.beco.colascr.transferencias.excepciones.BaseDeDatosExcepcion;

/**
 * Descripción:
 * 
 */

public class SincCentralTdaBases extends TimerTask {

	public static Sincronizador sincronizador = null; 

	/**
	 * Constructor para SincronizarServidorCaja.java
	 *
	 * 
	 */
	public SincCentralTdaBases(Sincronizador sync) {
		super();
		sincronizador = sync;
	}

	/**
	 * Método run
	 *
	 */
	public void run() {
		long inicio = System.currentTimeMillis();
		try {
			Sesion.getFrecuenciaSincBases().setEstadoTarea(PoliticaTarea.INICIADA);
			System.out.println("\n ***** Iniciando Ciclo de Sincronización de Entidades Bases *****");
			try {
			BeansSincronizador.syncEntidadServCentralServTda("banco");
			} catch (BaseDeDatosExcepcion e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
			BeansSincronizador.syncEntidadServCentralServTda("cargo");
			} catch (BaseDeDatosExcepcion e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
			BeansSincronizador.syncEntidadServCentralServTda("condicionventa");
			} catch (BaseDeDatosExcepcion e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
			BeansSincronizador.syncEntidadServCentralServTda("departamento");
			} catch (BaseDeDatosExcepcion e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
			BeansSincronizador.syncEntidadServCentralServTda("estadodecaja");
			} catch (BaseDeDatosExcepcion e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
			BeansSincronizador.syncEntidadServCentralServTda("formadepago");
			} catch (BaseDeDatosExcepcion e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
			BeansSincronizador.syncEntidadServCentralServTda("lineaseccion");
			} catch (BaseDeDatosExcepcion e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
			BeansSincronizador.syncEntidadServCentralServTda("metodos");
			} catch (BaseDeDatosExcepcion e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
			BeansSincronizador.syncEntidadServCentralServTda("modulos");
			} catch (BaseDeDatosExcepcion e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
			BeansSincronizador.syncEntidadServCentralServTda("maquinadeestado");
			} catch (BaseDeDatosExcepcion e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
			BeansSincronizador.syncEntidadServCentralServTda("perfil");
			} catch (BaseDeDatosExcepcion e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
			BeansSincronizador.syncEntidadServCentralServTda("funcion");
			} catch (BaseDeDatosExcepcion e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
			BeansSincronizador.syncEntidadServCentralServTda("funcionperfil");
			} catch (BaseDeDatosExcepcion e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
			BeansSincronizador.syncEntidadServCentralServTda("funcionmetodos");
			} catch (BaseDeDatosExcepcion e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
			BeansSincronizador.syncEntidadServCentralServTda("region");
			} catch (BaseDeDatosExcepcion e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
			BeansSincronizador.syncEntidadServCentralServTda("impuestoregion");
			} catch (BaseDeDatosExcepcion e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
			BeansSincronizador.syncEntidadServCentralServTda("tipocaptura");
			} catch (BaseDeDatosExcepcion e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
			BeansSincronizador.syncEntidadServCentralServTda("tiposervicio");
			} catch (BaseDeDatosExcepcion e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			/**
			 * IROJAS, 04/08/2009. Sincronización de tipos de apartados especiales.
			 * Requerimiento nuevo
			 **/
			try {
				BeansSincronizador.syncEntidadServCentralServTda("tipoapartado");
			} catch (BaseDeDatosExcepcion e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
				
			try {
			BeansSincronizador.syncEntidadServCentralServTda("unidaddeventa");
			} catch (BaseDeDatosExcepcion e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
			BeansSincronizador.syncEntidadServCentralServTda("caja");
			} catch (BaseDeDatosExcepcion e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
			BeansSincronizador.syncEntidadServCentralServTda("tipoeventolistaregalos");
			} catch (BaseDeDatosExcepcion e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
			BeansSincronizador.syncEntidadServCentralServTda("servidortienda");
			} catch (BaseDeDatosExcepcion e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// IROJAS 05/03/2009. Agregada sincronización de tabla catdep necesaria
			// para el módulo de promociones
			try {
				BeansSincronizador.syncEntidadServCentralServTda("catdep");
			} catch (BaseDeDatosExcepcion e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			//llamada para sincronizar Solicitud de Clientes.. Nuevo CRM
			try {
				BeansSincronizador.syncEntidadServCentralServTda("solicitudcliente");
			} catch (BaseDeDatosExcepcion e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}		
			//fin

			// GMARTINELLI 15/09/2010. Agregada sincronización de tabla br_opcionhabilitada
			// para el módulo de Bono Regalo Electronico
			try {
				BeansSincronizador.syncEntidadServCentralServTda("br_opcionhabilitada");
			} catch (BaseDeDatosExcepcion e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// Agregada sincronización de tabla br_condiciones para el módulo de 
			// Bono Regalo Electronico
			try {
				BeansSincronizador.syncEntidadServCentralServTda("br_condiciones");
			} catch (BaseDeDatosExcepcion e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// Agregada sincronización de tabla br_promocion para el módulo 
			// promocional de Bono Regalo Electronico
			try {
				BeansSincronizador.syncEntidadServCentralServTda("br_promocion");
			} catch (BaseDeDatosExcepcion e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			Conexiones.cerrarConexionesSync();
		}
		long fin = System.currentTimeMillis();
		System.out.println(" ***** Fin de Sincronización Entidades Bases. Tiempo Total: "  + (fin - inicio) + " MiliSegs. *****");
		Sesion.getFrecuenciaSincBases().setDuracionSinc(fin-inicio);
		Sesion.getFrecuenciaSincBases().setEstadoTarea(PoliticaTarea.FINALIZANDO);
		/*ServidorCR.sincronizador.configurarTareaProgramada(Sesion.getFrecuenciaSincBases());
		ServidorCR.sincronizador.actualizarMensajesDetalles();*/
	}
}