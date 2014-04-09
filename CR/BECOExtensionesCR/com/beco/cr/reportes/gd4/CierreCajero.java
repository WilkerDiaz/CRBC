/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.reportes
 * Programa   : CierreCajero.java
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
package com.beco.cr.reportes.gd4;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstadoVenta;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarusuario.Usuario;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.crprinterdriver.CRFiscalPrinterOperations;
import com.epa.crprinterdriver.PrinterNotConnectedException;

/** 
 * Descripción: 
 * 		Clase que maneja los perifericos del proyecto de CR. En principio maneja
 * únicamente la impresora. Restaría el manejo de Escaners, Visores, Teclados, etc.
 */
public class CierreCajero extends Reporte {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CierreCajero.class);
	
	/**
	 * Crea el encabezado de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generara el encabezado.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	private static void crearEncabezadoFiscal(Usuario usuario) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal(Usuario) - start");
		}

		DecimalFormat enteroConCeros = new DecimalFormat("000");
		DecimalFormat numFactura = new DecimalFormat("00000000000");
		SimpleDateFormat fechaTrans = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat horaTrans = new SimpleDateFormat("HH:mm:ss");
		String hora = horaTrans.format(Sesion.getHoraSistema());
		String fecha = fechaTrans.format(Sesion.getFechaSistema());
	
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Sesion.getTienda().getRazonSocial(), 3);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar(fecha, hora, columnasImpresoraFiscal), 0);
	
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
			
			//ACTUALIZACION BECO: Impresora fiscal GD4
			while (CR.meVenta.getTransaccionPorImprimir()!=null || 
					CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
					MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Bloque catch generado automáticamente
					e.printStackTrace();
				}
				if(MaquinaDeEstadoVenta.errorAtencionUsuario){
					MensajesVentanas.aviso("Hay un documento en curso \nEspere un momento e intente nuevamente");
				}
			}
			if (Sesion.impresoraFiscal) {
				MaquinaDeEstadoVenta.setDocumentoNoFiscalPorImprimir(true);
			}
			Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
			crearEncabezadoFiscal(usuario);
			
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("CIERRE DE CAJERO", 3);
			Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
			
			Sesion.crFiscalPrinterOperations.cortarPapel();
			try {
				Sesion.crFiscalPrinterOperations.commit();
			} catch (PrinterNotConnectedException e) {
				logger.error("imprimirReporte(Usuario)", e);
			}
		} else {
			// Esta Implementaccion no maneja Impresora NoFiscal
		}

		if (logger.isDebugEnabled()) {
			logger.debug("imprimirReporte(Usuario) - end");
		}
	}
}
