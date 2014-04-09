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
 * Versi�n     : 
 * Fecha       : 
 * Analista    : 
 * Descripci�n : 
 * =============================================================================
 */
package com.becoblohm.cr.extensiones.impl.reportes;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarservicio.Abono;
import com.becoblohm.cr.manejarservicio.Apartado;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.crprinterdriver.PrinterNotConnectedException;

/** 
 * Descripci�n: 
 * 		Clase que maneja los perifericos del proyecto de CR. En principio maneja
 * �nicamente la impresora. Restar�a el manejo de Escaners, Visores, Teclados, etc.
 */
public class CancelacionApartadoPedidoEspecial extends Reporte {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(CancelacionApartadoPedidoEspecial.class);
	
	// Variables necesarias para la impresion
	private static int columnasImpresora = 47;
	private static int columnasImpresoraFiscal = 40;
	
	/**
	 * Crea el encabezado de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generara el encabezado.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	/*
	* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
	* S�lo se parametriz� el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private static void crearEncabezado(Apartado apartado) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezado(Apartado) - start");
		}

		SimpleDateFormat fechaTrans = new SimpleDateFormat("dd/MM/yyyy");
		
		Sesion.crPrinterOperations.activarFuenteImpresionNegrita();
		Sesion.crPrinterOperations.alinear(1);
		Sesion.crPrinterOperations.enviarString(Sesion.getTienda().getRazonSocial()+"\n");
		Sesion.crPrinterOperations.initializarPrinter();
		Sesion.crPrinterOperations.activarDocumentoNomal();
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");

		String lineaDeDireccion = "";
		if((Sesion.getTienda().getDireccionFiscal() != null)&&(!Sesion.getTienda().getDireccionFiscal().equalsIgnoreCase("")))
			lineaDeDireccion += Sesion.getTienda().getDireccionFiscal();
		if((Sesion.getTienda().getNombreSucursal() != null)&&(!Sesion.getTienda().getNombreSucursal().equalsIgnoreCase("")))
			lineaDeDireccion += " Sucursal:" + Sesion.getTienda().getNombreSucursal() + ".";
		if((Sesion.getTienda().getDireccion() != null)&&(!Sesion.getTienda().getDireccion().equalsIgnoreCase("")))
			lineaDeDireccion += Sesion.getTienda().getDireccion();
		Sesion.crPrinterOperations.initializarPrinter();
		Sesion.crPrinterOperations.activarDocumentoNomal();
		Sesion.crPrinterOperations.alinear(1);
		if (!lineaDeDireccion.equalsIgnoreCase("")) {
			Vector<String> nuevasLineasDireccion = dividirEnLineas(columnasImpresora,lineaDeDireccion);
			for (int i=0; i<nuevasLineasDireccion.size(); i++)
				Sesion.crPrinterOperations.enviarString((String)nuevasLineasDireccion.elementAt(i)+"\n");
		}
		
		if ((Sesion.getTienda().getNit()!=null)&&(!Sesion.getTienda().getNit().equalsIgnoreCase("")))
			Sesion.crPrinterOperations.enviarString("RIF:" + Sesion.getTienda().getRif()  + "   NIT:" + Sesion.getTienda().getNit()+"\n");
		else
			Sesion.crPrinterOperations.enviarString("RIF:" + Sesion.getTienda().getRif()+"\n");
			
		Sesion.crPrinterOperations.alinear(0);
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");

		Sesion.crPrinterOperations.activarFuenteImpresionNegrita();
		Sesion.crPrinterOperations.alinear(1);
		Sesion.crPrinterOperations.enviarString("CANCELACION APARTADO" + "\n");
		Sesion.crPrinterOperations.enviarString("PEDIDO ESPECIAL" + "\n");
		Sesion.crPrinterOperations.initializarPrinter();
		Sesion.crPrinterOperations.activarDocumentoNomal();
		Sesion.crPrinterOperations.alinear(1);
		Sesion.crPrinterOperations.enviarString(fechaTrans.format(Sesion.getFechaSistema())+"\n");
				
		Sesion.crPrinterOperations.initializarPrinter();
		Sesion.crPrinterOperations.activarDocumentoNomal();
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");

		if (apartado.getCliente().getCodCliente()!=null) {
			// Creamos las lineas con los datos del Cliente
			String cliente = "Cliente: ";
			String direccion = "Direcci�n: ";
			Vector<String> lineasNombre = dividirEnLineas(columnasImpresora - 3 - cliente.length(),apartado.getCliente().getNombreCompleto());
			Vector<String> lineasDireccion = dividirEnLineas(columnasImpresora - 3 - direccion.length(),apartado.getCliente().getDireccion());
			String RIF = "CI/RIF: " + apartado.getCliente().getCodCliente();
			String NIT = ((apartado.getCliente().getNit()!=null)&&(!apartado.getCliente().getNit().equalsIgnoreCase("")))
						? "NIT:" + apartado.getCliente().getNit()
						: null;
			Sesion.crPrinterOperations.enviarString(cliente + lineasNombre.elementAt(0)+"\n");
			for (int i=1; i<lineasNombre.size(); i++) {
				String lineaActual = "";
				for (int j=0;j<cliente.length();j++)
					lineaActual += " ";
				Sesion.crPrinterOperations.enviarString(lineaActual + (String)lineasNombre.elementAt(i)+"\n");
			}
			Sesion.crPrinterOperations.enviarString(direccion + lineasDireccion.elementAt(0)+"\n");
			for (int i=1; i<lineasDireccion.size(); i++) {
				String lineaActual = "";
				for (int j=0;j<direccion.length();j++)
					lineaActual += " ";
				Sesion.crPrinterOperations.enviarString(lineaActual + (String)lineasDireccion.elementAt(i)+"\n");
			}
			if (NIT != null)
				Sesion.crPrinterOperations.enviarString(RIF + "  " + NIT+"\n");
			else
				Sesion.crPrinterOperations.enviarString(RIF+"\n");
		}
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");		

		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezado(Apartado) - end");
		}
	}
	
	/**
	 * Crea el detalle de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generara el detalle.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	private static void crearDetalle(Apartado apartado, boolean apartadoCancelado) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalle(Apartado, boolean) - start");
		}

		SimpleDateFormat fechaTrans = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat horaTrans = new SimpleDateFormat("HH:mm:ss");
		DecimalFormat precio = new DecimalFormat("#,##0.00");

		Sesion.crPrinterOperations.enviarString(justificar("Apartado:" + apartado.getNumServicio(), "Fecha: " + fechaTrans.format(apartado.getFechaServicio()), columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString("ABONOS\n");

		int limite = (apartadoCancelado) ? apartado.getAbonos().size() : apartado.getAbonos().size()-1;
		double montoAbono = 0;
		for (int i=0; i<limite; i++) {
			Abono abono = (Abono) apartado.getAbonos().elementAt(i);
			if (abono.getEstadoAbono()==Sesion.ABONO_ACTIVO) {
				Sesion.crPrinterOperations.enviarString("Abono " + (i+1) + "\n");
				Sesion.crPrinterOperations.enviarString("  " + fechaTrans.format(abono.getFechaAbono()) + "  " + horaTrans.format(abono.getHoraInicia()) + "  " + abono.getCodCajero() + "\n");
				Sesion.crPrinterOperations.enviarString("  " + justificar("Monto Abono:", precio.format(abono.getMontoBase()), columnasImpresora-2) + "\n");
				montoAbono += abono.getMontoBase();
			}
		}
		
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString(justificar("Monto Apartado:", precio.format(apartado.consultarMontoServ()), columnasImpresora) + "\n");
		Sesion.crPrinterOperations.enviarString(justificar("Monto Abonado","(-)" + precio.format(montoAbono),columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString(justificar("Resta",precio.format(apartado.consultarMontoServ()-montoAbono),columnasImpresora)+"\n");

		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");
		Sesion.crPrinterOperations.activarFuenteImpresionNegrita();
		Sesion.crPrinterOperations.alinear(1);
		Sesion.crPrinterOperations.enviarString("COMPROBANTE NO FISCAL\n");

		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalle(Apartado, boolean) - end");
		}
	}
	
	/*
	* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
	* S�lo se parametriz� el tipo de dato contenido en los 'Vector'
	* Se comentaron variables no usadas
	* Fecha: agosto 2011
	*/
	private static void crearEncabezadoFiscal(Apartado apartado) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal(Apartado) - start");
		}

		//SimpleDateFormat fechaTrans = new SimpleDateFormat("dd/MM/yyyy");
		
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

		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("CANCELACION APARTADO", 1);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("PEDIDO ESPECIAL", 1);
				
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);

		if (apartado.getCliente().getCodCliente()!=null) {
			// Creamos las lineas con los datos del Cliente
			String cliente = "Cliente: ";
			String direccion = "Direcci�n: ";
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
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("�ltimo Abono","(-)" + precio.format(apartado.consultarMontoServ()-montoAbono),columnasImpresoraFiscal), 0);

		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("SALDO:", precio.format(apartado.consultarMontoServ()-montoAbono-(apartado.consultarMontoServ()-montoAbono)), columnasImpresoraFiscal), 0);

		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalleFiscal(Apartado, boolean) - end");
		}
	}

	/**
	 * M�todo imprimirComprobate
	 * 	Realiza la impresion de la factura de Venta indicada como parametro.
	 * @param apartado
	 */
	public static void imprimirComprobate(Apartado apartado, boolean apartadoCancelado) {
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirComprobate(Apartado, boolean) - start");
		}

		if(Sesion.impresoraFiscal) {
			Sesion.crFiscalPrinterOperations.activarSlip();
			try {
				Sesion.crFiscalPrinterOperations.commit();
				MensajesVentanas.aviso("Impresi�n de Comprobante de Cancelaci�n de Apartado/P.Especial\nIntroduzca el documento en la impresora");
				// Cadenas de secciones de la factura
				Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
				crearEncabezadoFiscal(apartado);
				crearDetalleFiscal(apartado, apartadoCancelado);
				Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
				Sesion.crFiscalPrinterOperations.commit();
			} catch (PrinterNotConnectedException e) {
				logger.error("imprimirComprobate(Apartado, boolean)", e);
			}
		} else {
			Sesion.crPrinterOperations.initializarPrinter();
			Sesion.crPrinterOperations.activarDocumentoNomal();
			//Sesion.crPrinterOperations.abrirPuertoImpresora();
			try {
				Sesion.crPrinterOperations.commit();
				MensajesVentanas.aviso("Impresi�n de Comprobante de Cancelaci�n de Apartado/P.Especial\nIntroduzca el documento en la impresora");
				// Cadenas de secciones de la factura
				Sesion.crPrinterOperations.limpiarBuffer();
				Sesion.crPrinterOperations.initializarPrinter();
				Sesion.crPrinterOperations.activarDocumentoNomal();
				
				crearEncabezado(apartado);
				crearDetalle(apartado, apartadoCancelado);
				Sesion.crPrinterOperations.cortarPapel();
				Sesion.crPrinterOperations.commit();
			} catch (PrinterNotConnectedException e) {
				logger.error("imprimirComprobate(Apartado, boolean)", e);
			}
			//Sesion.crPrinterOperations.cerrarPuertoImpresora();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("imprimirComprobate(Apartado, boolean) - end");
		}
	}
}