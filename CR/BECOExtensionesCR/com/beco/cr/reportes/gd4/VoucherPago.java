/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.reportes
 * Programa   : VouchePago.java
 * Creado por : gmartinelli
 * Creado en  : 01-jul-04 13:26
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
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstadoVenta;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.crprinterdriver.CRFiscalPrinterOperations;
import com.epa.crprinterdriver.PrinterNotConnectedException;

/** 
 * Descripción: 
 * 		Clase que maneja los perifericos del proyecto de CR. En principio maneja
 * únicamente la impresora. Restaría el manejo de Escaners, Visores, Teclados, etc.
 */
public class VoucherPago extends Reporte {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(VoucherPago.class);
	
	// Variables necesarias para la impresion
	private static int columnasImpresoraFiscal = 38;
	
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
	private static void crearEncabezadoFiscal(FormaDePago fPago, Cliente cte, int numTrans) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal(FormaDePago, Cliente) - start");
		}

		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("FECHA: "+formatoFecha.format(Sesion.getFechaSistema()),"HORA: "+Sesion.getHoraSistema(), columnasImpresoraFiscal),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Sesion.getTienda().getRazonSocial(), 1);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("PAGO C/AUTORIZACIÓN", 1);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar(fPago.getNombre(), columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		StringBuffer num = new StringBuffer(Integer.toString(Sesion.getTienda().getNumero()));
		while (num.length() < 3) {
			num.insert(0, '0');
		}
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Tienda: " + Sesion.getTienda().getNombreSucursal() + " T# "+ num, 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Transacción: " + numTrans, 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Caja: " + Sesion.getCaja().getNumero(), "Fecha: " + formatoFecha.format(new Date()), columnasImpresoraFiscal) , 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Cajero: " + Sesion.usuarioActivo.getNumFicha(), "Hora: "+ formatoHora.format(new Date()), columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		// Imprimimos los datos del cliente
		if (cte!=null && cte.getCodCliente()!=null) {
			// Creamos las lineas con los datos del Cliente
			String cliente = "Cliente: ";
			String direccion = "Dirección: ";
			Vector<String> lineasNombre = dividirEnLineas(columnasImpresoraFiscal - 3 - cliente.length(),cte.getNombreCompleto());
			Vector<String> lineasDireccion = dividirEnLineas(columnasImpresoraFiscal - 3 - direccion.length(),cte.getDireccion());
			String RIF = "CI/RIF: " + cte.getCodCliente();
			String NIT = ((cte.getNit()!=null)&&(!cte.getNit().equalsIgnoreCase("")))
						? "NIT:" + cte.getNit()
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
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);		
		}

		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal(FormaDePago, Cliente) - end");
		}
	}
	
	/**
	 * Crea el detalle de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generara el detalle.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	private static void crearDetalleFiscal(double montoPago) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalleFiscal(double) - start");
		}

		DecimalFormat precio = new DecimalFormat("#,##0.00");
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Monto Pago", precio.format(montoPago),columnasImpresoraFiscal), 0);

		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalleFiscal(double) - end");
		}
	}
	
	/**
	 * Método crearPieDePagina
	 * 
	 * 
	 * void
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private static void crearPieDePaginaFiscal(Cliente cte, int numCopia, String autorizante) {
		if (logger.isDebugEnabled()) {
			logger
					.debug("crearPieDePaginaFiscal(Cliente, int, String) - start");
		}

		String nomAutorizante = autorizante!=null ? MediadorBD.obtenerNombreUsuario(autorizante):null;
		if (nomAutorizante!=null) {
			String lineaAutorizante = "Autorizado Por:    ";
			Vector<String> lineasAutorizante =  dividirEnLineas(columnasImpresoraFiscal - lineaAutorizante.length(), nomAutorizante);
			for (int i=0; i<lineasAutorizante.size(); i++) {
				if (i==0) 
					Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(lineaAutorizante + (String)lineasAutorizante.elementAt(i), 0);
				else {
					String linAct = "";
					for (int j=0; j<lineaAutorizante.length(); j++)
						linAct += " ";
					Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(linAct + (String)lineasAutorizante.elementAt(i), 0);
				}
			}
		} else
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Autorizado Por:    _____________________", 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Firma Autorizada:  _____________________", 0);
		if (cte!=null && cte.getCodCliente()==null)
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Nombre Cliente:    _____________________", 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("                  ", 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Firma del Cliente: _____________________", 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		if (numCopia==1)
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("ORIGINAL", 1);
		else
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("DUPLICADO", 1);

		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePaginaFiscal(Cliente, int, String) - end");
		}
	}

	/**
	 * Método imprimirComprobate
	 * 	Realiza la impresion de la factura de Venta indicada como parametro.
	 * @param cte
	 * @param codAutorizante
	 * @param fPago
	 * @param montoPago
	 * @param numCopia
	 */
	public static void imprimirComprobate(Cliente cte, String codAutorizante, FormaDePago fPago, double monto, int numCopias, int numTrans) {
		if (logger.isDebugEnabled()) {
			logger
					.debug("imprimirComprobate(Cliente, String, FormaDePago, double, int) - start");
		}

		if(Sesion.impresoraFiscal) {
			for (int i=0; i<numCopias; i++) {
				try {
					/*Sesion.crFiscalPrinterOperations.activarSlip();
					Sesion.crFiscalPrinterOperations.commit();*/
					while(CR.meVenta.getTransaccionPorImprimir()!=null || 
							CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
							MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()){
						
						/*if ((i+1) == 1)
							MensajesVentanas.aviso("Impresión de Comprobante de Pago (Original)\nIntroduzca el documento en la impresora");
						else
							MensajesVentanas.aviso("Impresión de Comprobante de Pago (Copia " + (i+1) + ")\nIntroduzca el documento en la impresora");*/
						if(MaquinaDeEstadoVenta.errorAtencionUsuario){
							MensajesVentanas.aviso("Problema al imprimir el documento.");
						}
					}
					MaquinaDeEstadoVenta.setDocumentoNoFiscalPorImprimir(true);
					Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
					UtilesReportes.crearEncabezadoNoFiscal();
					crearEncabezadoFiscal(fPago, cte, numTrans);
					crearDetalleFiscal(monto);
					crearPieDePaginaFiscal(cte, (i+1), codAutorizante);
					Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
					Sesion.crFiscalPrinterOperations.cortarPapel();
					Sesion.crFiscalPrinterOperations.commit();
				} catch (PrinterNotConnectedException e) {
					logger
							.error(
									"imprimirComprobate(Cliente, String, FormaDePago, double, int)",
									e);
				}
			}
		} else {
			// Esta Implementaccion no maneja Impresora NoFiscal
		}

		if (logger.isDebugEnabled()) {
			logger
					.debug("imprimirComprobate(Cliente, String, FormaDePago, double, int) - end");
		}
	}

}