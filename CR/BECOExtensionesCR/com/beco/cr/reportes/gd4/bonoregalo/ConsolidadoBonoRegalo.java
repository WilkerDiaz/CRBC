/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.impresion
 * Programa   : ConsolidadoBonoRegalo.java
 * Creado por : gmartinelli
 * Creado en  : 15-NOV-10 14:06
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
package com.beco.cr.reportes.gd4.bonoregalo;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.extensiones.impl.reportes.Reporte;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstadoVenta;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarusuario.Usuario;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.crprinterdriver.PrinterNotConnectedException;

/** 
 * Descripción: 
 * 		Clase que maneja los perifericos del proyecto de CR. En principio maneja
 * únicamente la impresora. Restaría el manejo de Escaners, Visores, Teclados, etc.
 */
public class ConsolidadoBonoRegalo extends Reporte {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ConsolidadoBonoRegalo.class);
	
	// Variables necesarias para la impresion
	private static int columnasImpresoraFiscal = 38;
	private static SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
	private static DecimalFormat formatoMonto = new DecimalFormat("#,##0.00");
	
	
	/**
	 * Crea el encabezado de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generara el encabezado.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private static void crearEncabezado(int nroComprobanteCP, Usuario usuario, Date fecha) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezado(Apartado) - start");
		}

		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar(Sesion.getTienda().getRazonSocial(),columnasImpresoraFiscal),0);
		String lineaDeDireccion = "";
		if((Sesion.getTienda().getDireccionFiscal() != null)&&(!Sesion.getTienda().getDireccionFiscal().equalsIgnoreCase("")))
			lineaDeDireccion += Sesion.getTienda().getDireccionFiscal()+" ";
		if((Sesion.getTienda().getNombreSucursal() != null)&&(!Sesion.getTienda().getNombreSucursal().equalsIgnoreCase("")))
			lineaDeDireccion += "Sucursal: " + Sesion.getTienda().getNombreSucursal() + "."+" ";
		if((Sesion.getTienda().getDireccion() != null)&&(!Sesion.getTienda().getDireccion().equalsIgnoreCase("")))
			lineaDeDireccion += Sesion.getTienda().getDireccion()+" ";
		if (!lineaDeDireccion.equalsIgnoreCase("")) {
			Vector<String> nuevasLineasDireccion = dividirEnLineas(columnasImpresoraFiscal,lineaDeDireccion);
			for (int i=0; i<nuevasLineasDireccion.size(); i++)
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar((String)nuevasLineasDireccion.elementAt(i),columnasImpresoraFiscal),0);
		}

		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal)+"",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar("CONSOLIDADO DIARIO - VENTAS BR",columnasImpresoraFiscal),1);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar("Comprobante de Ingreso: " + nroComprobanteCP, columnasImpresoraFiscal), 1);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal)+"",0);

		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Fecha: " + formatoFecha.format(fecha), "Hora: " + formatoHora.format(Sesion.getHoraSistema()), columnasImpresoraFiscal)+"",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Cajero: " + usuario.getNumFicha(), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(          ("        " + usuario.getNombre()),0);
		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal)+"",0);
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezado(Apartado) - end");
		}
	}

	/**
	 * Crea el detalle de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generara el detalle.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	private static void crearDetalles(Vector<Vector<Object>>  fPagos) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalles(BR) - start");
		}

		Vector<Object> fPagoActual;
		double total = 0;
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar("Consolidado Formas de Pago", columnasImpresoraFiscal), 1);
		
		// Para cada detalle de Forma de Pago
		for (int i=0; i<fPagos.size(); i++) {
			fPagoActual = fPagos.elementAt(i);
			String descActual = (String)fPagoActual.elementAt(0);
			double montoActual = ((Double)fPagoActual.elementAt(1)).doubleValue();
			total += montoActual;
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar(descActual, formatoMonto.format(montoActual), columnasImpresoraFiscal), 0);
		}
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha(crearLineaDeDivision(15), columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha("TOTAL:   " + formatoMonto.format(total), columnasImpresoraFiscal), 1);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal)+"",0);

		// Pie de Consolidado Diario
		String firmaAsesor = "ASESOR: ";
		for (int i=firmaAsesor.length(); i<(columnasImpresoraFiscal-10); i++) firmaAsesor+="_";
		String firmaCPpal = "Caja Ppal: ";
		for (int i=firmaCPpal.length(); i<(columnasImpresoraFiscal-12); i++) firmaCPpal+="_";
		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(" ",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(" ",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(" ",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(firmaAsesor,0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(" ",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(" ",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(" ",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(firmaCPpal,0);
		
		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalles(BR) - end");
		}
	}
	
	/**
	 * Método imprimirComprobante
	 * 	Realiza la impresion de la factura de Venta indicada como parametro.
	 * @param apartado
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public static void imprimirComprobante(int nroComprobanteCP, Vector<Vector<Object>> fPagos, Usuario usuario, Date fecha) {
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirComprobante(BR) - start");
		}

		// Se envía a imprimir el comprobante de apartado tants veces como lo indiquen las variables correspondientes
		if(Sesion.impresoraFiscal) {
			try {
				do{
					if(MaquinaDeEstadoVenta.errorAtencionUsuario){
						MensajesVentanas.aviso("Problema al imprimir el documento.");
					}
				} while(MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir());
				
				// Cadenas de secciones de la factura
				Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
				ConsolidadoBonoRegalo.crearEncabezado(nroComprobanteCP, usuario, fecha);
				ConsolidadoBonoRegalo.crearDetalles(fPagos);
				Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
				MaquinaDeEstadoVenta.setDocumentoNoFiscalPorImprimir(true);
				Sesion.crFiscalPrinterOperations.commit();	
				do{
					if(MaquinaDeEstadoVenta.errorAtencionUsuario){
						MensajesVentanas.aviso("Problema al imprimir el documento.");
					}
				} while(MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir());
				Sesion.crFiscalPrinterOperations.cortarPapel();
				try {
					Sesion.crFiscalPrinterOperations.commit();
				} catch (PrinterNotConnectedException e) {
					logger.error("imprimirComprobante(BR)", e);
				}
			} catch (PrinterNotConnectedException e) {
				e.printStackTrace();
				logger.error("imprimirComprobante(BR)", e);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("imprimirComprobante(BR) - end");
		}
	}
	
}