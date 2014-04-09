/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.reportes
 * Programa   : CancelacionApartadoPedidoEspecial.java
 * Creado por : gmartinelli
 * Creado en  : 13-oct-04 15:24
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
package com.beco.cr.reportes.gd4;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.manejadorsesion.MaquinaDeEstadoVenta;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarservicio.Abono;
import com.becoblohm.cr.manejarservicio.Apartado;
import com.epa.crprinterdriver.PrinterNotConnectedException;

/** 
 * Descripción: 
 * 		Clase que maneja los perifericos del proyecto de CR. En principio maneja
 * únicamente la impresora. Restaría el manejo de Escaners, Visores, Teclados, etc.
 */
public class CancelacionApartadoPedidoEspecial extends Reporte {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(CancelacionApartadoPedidoEspecial.class);
	
	// Variables necesarias para la impresion
	private static int columnasImpresoraFiscal = 38;
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private static void crearEncabezadoFiscal(Apartado apartado) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal(Apartado) - start");
		}

		SimpleDateFormat fechaTrans = new SimpleDateFormat("dd/MM/yyyy");
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("FECHA: "+fechaTrans.format(Sesion.getFechaSistema()),"HORA: "+Sesion.getHoraSistema(), columnasImpresoraFiscal),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Sesion.getTienda().getRazonSocial(), 1);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);

		String lineaDeDireccion = "";
		if((Sesion.getTienda().getDireccionFiscal() != null)&&(!Sesion.getTienda().getDireccionFiscal().equalsIgnoreCase("")))
			lineaDeDireccion += Sesion.getTienda().getDireccionFiscal();
		if((Sesion.getTienda().getNombreSucursal() != null)&&(!Sesion.getTienda().getNombreSucursal().equalsIgnoreCase("")))
			lineaDeDireccion += " Sucursal:" + Sesion.getTienda().getNombreSucursal() + ".";
		if((Sesion.getTienda().getDireccion() != null)&&(!Sesion.getTienda().getDireccion().equalsIgnoreCase("")))
			lineaDeDireccion += Sesion.getTienda().getDireccion();

		if (!lineaDeDireccion.equalsIgnoreCase("")) {
			Vector<String> nuevasLineasDireccion = dividirEnLineas(columnasImpresoraFiscal,lineaDeDireccion);
			for (int i=0; i<nuevasLineasDireccion.size(); i++)
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar((String)nuevasLineasDireccion.elementAt(i), columnasImpresoraFiscal), 0);
		}
		
		if ((Sesion.getTienda().getNit()!=null)&&(!Sesion.getTienda().getNit().equalsIgnoreCase("")))
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar("RIF:" + Sesion.getTienda().getRif() + "   NIT:" + Sesion.getTienda().getNit(), columnasImpresoraFiscal), 0);
		else
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar("RIF:" + Sesion.getTienda().getRif(), columnasImpresoraFiscal), 0);
			
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(convertirCadena("Apartado de productos"), 3);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("CANCELACION APARTADO", 1);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("PEDIDO ESPECIAL", 1);
				
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);

		if (apartado.getCliente().getCodCliente()!=null) {
			// Creamos las lineas con los datos del Cliente
			String cliente = "Cliente: ";
			String direccion = "Dirección: ";
			Vector<String> lineasNombre = dividirEnLineas(columnasImpresoraFiscal - 3 - cliente.length(),apartado.getCliente().getNombreCompleto());
			Vector<String> lineasDireccion = dividirEnLineas(columnasImpresoraFiscal - 3 - direccion.length(),apartado.getCliente().getDireccion());
//			*****************************************
//			Codigo a modificar por politicas de Fisco.
			String RIF = "CI/RIF: " + apartado.getCliente().getCodCliente().replace('N','V');
			String NIT = ((apartado.getCliente().getNit()!=null)&&(!apartado.getCliente().getNit().equalsIgnoreCase("")))
						? "NIT:" + apartado.getCliente().getNit()
						: null;
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(cliente + lineasNombre.elementAt(0), 0);
			for (int i=1; i<lineasNombre.size(); i++) {
				String lineaActual = "";
				for (int j=0;j<cliente.length();j++)
					lineaActual += " ";
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(lineaActual + (String)lineasNombre.elementAt(i), 0);
			}
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(direccion + lineasDireccion.elementAt(0), 0);
			for (int i=1; i<lineasDireccion.size(); i++) {
				String lineaActual = "";
				for (int j=0;j<direccion.length();j++)
					lineaActual += " ";
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(lineaActual + (String)lineasDireccion.elementAt(i), 0);
			}
			if (NIT != null)
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(RIF + "  " + NIT, 0);
			else
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(RIF, 0);
		}
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);		

		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal(Apartado) - end");
		}
	}
	
	/**
	 * Crea el detalle de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generara el detalle.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	private static void crearDetalleFiscal(Apartado apartado, boolean apartadoCancelado) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalleFiscal(Apartado, boolean) - start");
		}

		SimpleDateFormat fechaTrans = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat horaTrans = new SimpleDateFormat("HH:mm:ss");
		DecimalFormat precio = new DecimalFormat("#,##0.00");

		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Apartado:" + apartado.getNumServicio(), "Fecha: " + fechaTrans.format(apartado.getFechaServicio()), columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("CAJERO: " + Sesion.usuarioActivo.getNumFicha(), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("ABONOS ANTERIORES", 0);
		int limite = (apartadoCancelado) ? apartado.getAbonos().size() : apartado.getAbonos().size()-1;
		double montoAbono = 0;
		for (int i=0; i<limite; i++) {
			Abono abono = (Abono) apartado.getAbonos().elementAt(i);
			if (abono.getEstadoAbono()==Sesion.ABONO_ACTIVO) {
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Abono " + (i+1), 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  " + fechaTrans.format(abono.getFechaAbono()) + "  " + horaTrans.format(abono.getHoraInicia()) + "  " + abono.getCodCajero(), 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  " + justificar("Monto Abono:", precio.format(abono.getMontoBase()), columnasImpresoraFiscal-2), 0);
				montoAbono += abono.getMontoBase();
			}
		}
		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Monto Apartado:", precio.format(apartado.consultarMontoServ()), columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Abonos Anteriores","(-)" + precio.format(montoAbono),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Último Abono","(-)" + precio.format(apartado.consultarMontoServ()-montoAbono),columnasImpresoraFiscal), 0);

		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("SALDO:", precio.format(apartado.consultarMontoServ()-montoAbono-(apartado.consultarMontoServ()-montoAbono)), columnasImpresoraFiscal), 0);

		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalleFiscal(Apartado, boolean) - end");
		}
	}

	/**
	 * Método imprimirComprobate
	 * 	Realiza la impresion de la factura de Venta indicada como parametro.
	 * @param apartado
	 */
	public static void imprimirComprobate(Apartado apartado, boolean apartadoCancelado) {
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirComprobate(Apartado, boolean) - start");
		}

		if(Sesion.impresoraFiscal) {
			//Sesion.crFiscalPrinterOperations.activarSlip();
			try {
				//MensajesVentanas.aviso("Impresión de Comprobante de Cancelación de Apartado/P.Especial\nIntroduzca el documento en la impresora");
				//Sesion.crFiscalPrinterOperations.commit();
				
				// Cadenas de secciones de la factura
				Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
				//UtilesReportes.crearEncabezadoNoFiscal();
				crearEncabezadoFiscal(apartado);
				crearDetalleFiscal(apartado, apartadoCancelado);
				Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
				Sesion.crFiscalPrinterOperations.cortarPapel();
				MaquinaDeEstadoVenta.setDocumentoNoFiscalPorImprimir(true);
				Sesion.crFiscalPrinterOperations.commit();
			} catch (PrinterNotConnectedException e) {
				logger.error("imprimirComprobate(Apartado, boolean)", e);
			}
		} else {
			// Esta Implementaccion no maneja Impresora NoFiscal
		}

		if (logger.isDebugEnabled()) {
			logger.debug("imprimirComprobate(Apartado, boolean) - end");
		}
	}
}