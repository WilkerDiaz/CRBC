/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.reportes
 * Programa   : AperturaCajero.java
 * Creado por : gmartinelli
 * Creado en  : 31-may-04 14:21:10
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1	
 * Fecha       : 09-jun-04
 * Analista    : gmartinelli
 * Descripción : Integrado uso de impresora (imprime por el lado de los cheques por ahora)
 * =============================================================================
 */
package com.becoblohm.cr.extensiones.impl.reportes;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarusuario.Usuario;
import com.epa.crprinterdriver.PrinterNotConnectedException;

/** 
 * Descripción: 
 * 		Clase que maneja los perifericos del proyecto de CR. En principio maneja
 * únicamente la impresora. Restaría el manejo de Escaners, Visores, Teclados, etc.
 */
public class AperturaCajero extends Reporte {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AperturaCajero.class);
	
	// Variables necesarias para la impresion
	private static int columnasImpresora = 44;
	private static int columnasImpresoraFiscal = 40;

	/**
	 * Crea el encabezado de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generara el encabezado.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	private static void crearEncabezado(Usuario usuario) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezado(Usuario) - start");
		}

		SimpleDateFormat fechaTrans = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat horaTrans = new SimpleDateFormat("HH:mm:ss");
		DecimalFormat enteroConCeros = new DecimalFormat("000");
		DecimalFormat numFactura = new DecimalFormat("00000000000");
		Sesion.crPrinterOperations.activarFuenteImpresionNegrita();
		Sesion.crPrinterOperations.alinear(1);
		Sesion.crPrinterOperations.enviarString(Sesion.getTienda().getRazonSocial() + "\n");//, columnasImpresora - 3));
		Sesion.crPrinterOperations.initializarPrinter();
		Sesion.crPrinterOperations.activarReciboAuditoria();

		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora) + "\n");
		String hora = horaTrans.format(Sesion.getHoraSistema());
		String fecha = fechaTrans.format(Sesion.getFechaSistema());
		String cajero = "Cajero: " + usuario.getNumFicha();
		enteroConCeros.format(Sesion.getCaja().getNumero());
		String lineaIzquierda = fecha + centrar(hora,columnasImpresora-hora.length()-cajero.length());
		Sesion.crPrinterOperations.enviarString(justificar(lineaIzquierda, cajero, columnasImpresora) + "\n");

		String caja = "POS: " + enteroConCeros.format(Sesion.getCaja().getNumero());
		Sesion.crPrinterOperations.enviarString(justificar(caja, "Tr:" + numFactura.format(Sesion.getCaja().getUltimaTransaccion()), columnasImpresora) + "\n");

		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezado(Usuario) - end");
		}
	}
	
	/**
	 * Crea el encabezado de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generara el encabezado.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se comentaron variables no usadas
	* Fecha: agosto 2011
	*/
	private static void crearEncabezadoFiscal(Usuario usuario) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal(Usuario) - start");
		}

		//SimpleDateFormat fechaTrans = new SimpleDateFormat("dd/MM/yyyy");
		//SimpleDateFormat horaTrans = new SimpleDateFormat("HH:mm:ss");
		DecimalFormat enteroConCeros = new DecimalFormat("000");
		DecimalFormat numFactura = new DecimalFormat("00000000000");

		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Sesion.getTienda().getRazonSocial(), 1);

		String cajero = "Cajero: " + usuario.getNumFicha();
		String caja = "POS: " + enteroConCeros.format(Sesion.getCaja().getNumero());
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar(caja, cajero, columnasImpresoraFiscal), 0);
		String trx = "Ult. Tr Caja:" + numFactura.format(Sesion.getCaja().getUltimaTransaccion()); 
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar(trx, columnasImpresoraFiscal), 0);

		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal(Usuario) - end");
		}
	}

	/**
	 * Método imprimirReporte
	 * 	Realiza la impresion de la factura de Venta indicada como parametro.
	 * @param usuario
	 */
	public static void imprimirReporte(Usuario usuario) {
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirReporte(Usuario) - start");
		}

		if(Sesion.impresoraFiscal) {
			Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
			crearEncabezadoFiscal(usuario);
			
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("APERTURA DE CAJERO", 1);
			Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
			try {
				Sesion.crFiscalPrinterOperations.commit();
			} catch (PrinterNotConnectedException e) {
				logger.error("imprimirReporte(Usuario)", e);
			}
		} else {
			// Cadenas de secciones de la factura
			Sesion.crPrinterOperations.limpiarBuffer();
			Sesion.crPrinterOperations.initializarPrinter();
			Sesion.crPrinterOperations.activarReciboAuditoria();
			
			crearEncabezado(usuario);
			Sesion.crPrinterOperations.activarFuenteImpresionNegrita();
			Sesion.crPrinterOperations.alinear(1);
			Sesion.crPrinterOperations.enviarString("APERTURA DE CAJERO\n");
			Sesion.crPrinterOperations.initializarPrinter();
			Sesion.crPrinterOperations.activarReciboAuditoria();
	
			Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora) + "\n");
			Sesion.crPrinterOperations.alinear(1);
			Sesion.crPrinterOperations.enviarString("DOCUMENTO NO FISCAL\n");
			
			//Sesion.crPrinterOperations.abrirPuertoImpresora();
/*			try {
				Sesion.crPrinterOperations.commit();
			} catch (PrinterNotConnectedException e) {
				logger.error("imprimirReporte(Usuario)", e);
			}
*/		//Sesion.crPrinterOperations.cerrarPuertoImpresora();

		}

		if (logger.isDebugEnabled()) {
			logger.debug("imprimirReporte(Usuario) - end");
		}
	}
}