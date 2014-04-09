/*
 * $Id: SincronizadorPuntoAgil.java,v 1.5 2007/04/25 18:46:04 programa8 Exp $
 * ===========================================================================
 *
 * Proyecto		: PuntoAgil
 * Paquete		: com.epa.ventas.cr.puntoAgil.manejarpago
 * Programa		: SincronizadorPuntoAgil.java
 * Creado por	: programa4
 * Creado en 	: 20/06/2006 08:26:46 AM
 * (C) Copyright 2006 Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisión	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: SincronizadorPuntoAgil.java,v $
 * Revision 1.5  2007/04/25 18:46:04  programa8
 * Version Estable Pto Agil, controlando cargos dos dobles , una sola instancia a datosExternos , control fuera de linea
 *
 * Revision 1.4  2006/09/28 19:08:25  programa4
 * Actualizacion por requerimientos credicard
 * Se cargan datos de programas de lealtad segun banco seleccionado
 * Modificados mensajes de error para hacerlos mas legibles
 * Verificados titulos para indicar version del punto agil
 *
 * Revision 1.3  2006/07/10 19:25:59  programa4
 * Organizada importaciones
 *
 * Revision 1.2  2006/06/27 15:51:33  programa4
 * Refactorizadas consultas
 * Agregados metodos sincronizacion
 * Modificado manejo de excepciones
 *
 * Revision 1.1  2006/06/26 16:08:00  programa4
 * Agregada sincronizacion de datos extra de pagos
 *
 *
 * ===========================================================================
 */
/**
 * Clase SincronizadorPuntoAgil
 *
 * @author programa4 - $Author: programa8 $
 * @version $Revision: 1.5 $ - $Date: 2007/04/25 18:46:04 $
 * @since 20/06/2006
 */
package com.epa.ventas.cr.puntoAgil.manejarpago;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.extensiones.ManejoPagosFactory;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.mediadoresbd.BeansSincronizador;
import com.becoblohm.cr.mediadoresbd.EntidadBD;
import com.epa.sincronizador.datafile.client.ObtenerDataFile;

/**
 * @author programa4 - $Author: programa8 $
 * @version $Revision: 1.5 $ - $Date: 2007/04/25 18:46:04 $
 * @since 20/06/2006
 */
public class SincronizadorPuntoAgil {

	/**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(SincronizadorPuntoAgil.class);

	private ManejoPagosPuntoAgil mppa = (ManejoPagosPuntoAgil) ManejoPagosFactory
		.getInstance();

	private BaseDeDatosPagoPuntoAgil bppa = this.mppa
		.getInstanceBaseDeDatosPagoPuntoAgil();

	private static SincronizadorPuntoAgil instance;

	/**
	 * Instancia singleton del objeto.
	 *
	 * @return SincronizadorPuntoAgil singleton
	 */
	public static SincronizadorPuntoAgil getInstance() {
		if (instance == null) {
			instance = new SincronizadorPuntoAgil();
		}
		return instance;
	}

	/*
	 * Sincroniza las operaciones de punto agil de la caja al servidor.
	 */
	void sincronizarOperaciones() throws BaseDeDatosExcepcion {
		this.bppa.sincronizarDatosOperacionPuntoAgil();
	}

	void sincronizarMaestros() throws BaseDeDatosExcepcion {
		Sesion.chequearLineaCaja();
		if (Sesion.isCajaEnLinea()) {
			String[] tablas = { "CR.puntoAgilCuentasEspeciales.txt.gz",
					"CR.puntoAgilFormadePago.txt.gz",
					"CR.puntoAgilPlanCreditoEPA.txt.gz",
					"CR.puntoAgilProcesoEstadoCaja.txt.gz",
					"CR.puntoAgilTipoCuenta.txt.gz" };
			try {
				ObtenerDataFile.obtenerVariosDataFile(tablas);
			} catch (Exception e) {
				throw new BaseDeDatosExcepcion(e.getMessage(), e);
			}
		} else {
			logger
					.error("No se sincronizaron los maestros de Punto Agil, caja fuera de linea");
		}
	}

	void sincronizarPuntoAgilCaja() throws BaseDeDatosExcepcion {
		String[] clave = { "numtienda", "numcaja" };
		EntidadBD entidad = new EntidadBD(Sesion.getDbEsquema(),
				"puntoagilcaja", clave, false);
		BeansSincronizador.syncEntidad(entidad, true, true);
	}
}
