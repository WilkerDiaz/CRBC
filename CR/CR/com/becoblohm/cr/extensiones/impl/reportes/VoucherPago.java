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
package com.becoblohm.cr.extensiones.impl.reportes;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.crprinterdriver.PrinterNotConnectedException;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;

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
	private static int columnasImpresora = 47;
	private static int columnasImpresoraFiscal = 40;
	
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
	private static void crearEncabezado(FormaDePago fPago, Cliente cte) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezado(FormaDePago, Cliente) - start");
		}

		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
		
		Sesion.crPrinterOperations.activarFuenteImpresionNegrita();
		Sesion.crPrinterOperations.alinear(1);
		Sesion.crPrinterOperations.enviarString(Sesion.getTienda().getRazonSocial()+"\n");
		Sesion.crPrinterOperations.initializarPrinter();
		Sesion.crPrinterOperations.activarDocumentoNomal();
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");
		Sesion.crPrinterOperations.activarFuenteImpresionNegrita();
		Sesion.crPrinterOperations.alinear(1);
		Sesion.crPrinterOperations.enviarString("PAGOS CON AUTORIZACIÓN\n");
		Sesion.crPrinterOperations.initializarPrinter();
		Sesion.crPrinterOperations.activarDocumentoNomal();
		Sesion.crPrinterOperations.alinear(1);
		Sesion.crPrinterOperations.enviarString(fPago.getNombre() + "\n");
		Sesion.crPrinterOperations.initializarPrinter();
		Sesion.crPrinterOperations.activarDocumentoNomal();
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString("Tienda: " + Sesion.getTienda().getNombreSucursal() + " T #"+  Sesion.getTienda().getNumero() + "\n");
		Sesion.crPrinterOperations.enviarString(justificar("Caja: " + Sesion.getCaja().getNumero(), "Fecha: " + formatoFecha.format(new Date()), columnasImpresora) + "\n");
		Sesion.crPrinterOperations.enviarString(justificar("Cajero: " + Sesion.usuarioActivo.getNumFicha(), "Hora: "+ formatoHora.format(new Date()), columnasImpresora) + "\n");
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");
		// Imprimimos los datos del cliente
		if (cte.getCodCliente()!=null) {
			// Creamos las lineas con los datos del Cliente
			String cliente = "Cliente: ";
			String direccion = "Dirección: ";
			Vector<String> lineasNombre = dividirEnLineas(columnasImpresora - 3 - cliente.length(),cte.getNombreCompleto());
			Vector<String> lineasDireccion = dividirEnLineas(columnasImpresora - 3 - direccion.length(),cte.getDireccion());
			String RIF = null;
			try {
				if(InitCR.preferenciasCR.getConfigStringForParameter("sistema","estiloFactura").equals("CR")){
					RIF = "IDENTIFICACION: " + cte.getCodCliente();
				}
				else{
					RIF = "CI/RIF: " + cte.getCodCliente();
				}
			} catch (NoSuchNodeException e) {
				logger.error("crearEncabezado(FormaDePago, Cliente)", e);
			} catch (UnidentifiedPreferenceException e) {
				logger.error("crearEncabezado(FormaDePago, Cliente)", e);
			}
						String NIT = ((cte.getNit()!=null)&&(!cte.getNit().equalsIgnoreCase("")))
						? "NIT:" + cte.getNit()
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
			Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");		
		}

		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezado(FormaDePago, Cliente) - end");
		}
	}
	
	/**
	 * Crea el detalle de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generara el detalle.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	private static void crearDetalle(double montoPago) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalle(double) - start");
		}

		DecimalFormat precio = new DecimalFormat("#,##0.00");
		Sesion.crPrinterOperations.initializarPrinter();
		Sesion.crPrinterOperations.activarDocumentoNomal();
		Sesion.crPrinterOperations.enviarString(justificar("Monto Pago", precio.format(montoPago),columnasImpresora)+"\n");

		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalle(double) - end");
		}
	}
	
	/**
	 * Método crearPieDePagina
	 * 
	 * 
	 * void
	 */
	private static void crearPieDePagina(Cliente cte, int numCopia, String autorizante) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePagina(Cliente, int, String) - start");
		}

		String nomAutorizante = MediadorBD.obtenerNombreUsuario(autorizante);
		if (nomAutorizante!=null)
			Sesion.crPrinterOperations.enviarString("\nAutorizado Por:      " + nomAutorizante + "\n\n");
		else
			Sesion.crPrinterOperations.enviarString("\nAutorizado Por:      _____________________"+"\n\n");
		Sesion.crPrinterOperations.enviarString("Firma Autorizada:    _____________________"+"\n\n");
		if (cte.getCodCliente()==null)
			Sesion.crPrinterOperations.enviarString("Nombre Cliente:      _____________________"+"\n\n");
		Sesion.crPrinterOperations.enviarString("Firma del Cliente:   _____________________"+"\n\n");
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora));
		Sesion.crPrinterOperations.activarFuenteImpresionGrande();
		Sesion.crPrinterOperations.alinear(1);
		if (numCopia==1)
			Sesion.crPrinterOperations.enviarString("ORIGINAL\n");
		else
			Sesion.crPrinterOperations.enviarString("COPIA\n");
		Sesion.crPrinterOperations.initializarPrinter();
		Sesion.crPrinterOperations.activarDocumentoNomal();
		Sesion.crPrinterOperations.alinear(1);
		Sesion.crPrinterOperations.enviarString("DOCUMENTO NO FISCAL\n");
		Sesion.crPrinterOperations.alinear(0);
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");

		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePagina(Cliente, int, String) - end");
		}
	}

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
		if (cte.getCodCliente()!=null) {
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

		String nomAutorizante = MediadorBD.obtenerNombreUsuario(autorizante);
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
		if (cte.getCodCliente()==null)
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Nombre Cliente:    _____________________", 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(" ", 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Firma del Cliente: _____________________", 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		if (numCopia==1)
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("ORIGINAL", 1);
		else
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("COPIA", 1);

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
				Sesion.crFiscalPrinterOperations.activarSlip();
				try {
					Sesion.crFiscalPrinterOperations.commit();
					if ((i+1) == 1)
						MensajesVentanas.aviso("Impresión de Comprobante de Pago (Original)\nIntroduzca el documento en la impresora");
					else
						MensajesVentanas.aviso("Impresión de Comprobante de Pago (Copia " + (i+1) + ")\nIntroduzca el documento en la impresora");
					
					Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
					crearEncabezadoFiscal(fPago, cte, numTrans);
					crearDetalleFiscal(monto);
					crearPieDePaginaFiscal(cte, (i+1), codAutorizante);
					Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
					Sesion.crFiscalPrinterOperations.commit();
				} catch (PrinterNotConnectedException e) {
					logger
							.error(
									"imprimirComprobate(Cliente, String, FormaDePago, double, int)",
									e);
				}
			}
		} else {
			for (int i=0; i<numCopias; i++) {
				Sesion.crPrinterOperations.initializarPrinter();
				Sesion.crPrinterOperations.activarDocumentoNomal();
				//Sesion.crPrinterOperations.abrirPuertoImpresora();
//				try {
//					Sesion.crPrinterOperations.commit();
					if ((i+1) == 1)
						MensajesVentanas.aviso("Impresión de Comprobante de Pago (Original)\nIntroduzca el documento en la impresora");
					else
						MensajesVentanas.aviso("Impresión de Comprobante de Pago (Copia " + (i+1) + ")\nIntroduzca el documento en la impresora");
					Sesion.crPrinterOperations.limpiarBuffer();
					Sesion.crPrinterOperations.initializarPrinter();
					Sesion.crPrinterOperations.activarDocumentoNomal();
					
					crearEncabezado(fPago, cte);
					crearDetalle(monto);
					crearPieDePagina(cte, (i+1), codAutorizante);
					Sesion.crPrinterOperations.cortarPapel();
/*					Sesion.crPrinterOperations.commit();
				} catch (PrinterNotConnectedException e) {
					logger
							.error(
									"imprimirComprobate(Cliente, String, FormaDePago, double, int)",
									e);
				}
*/				//Sesion.crPrinterOperations.cerrarPuertoImpresora();
			}
		}

		if (logger.isDebugEnabled()) {
			logger
					.debug("imprimirComprobate(Cliente, String, FormaDePago, double, int) - end");
		}
	}

}