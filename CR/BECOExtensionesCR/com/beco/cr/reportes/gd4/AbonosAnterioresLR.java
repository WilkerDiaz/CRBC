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
package com.beco.cr.reportes.gd4;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarservicio.ListaRegalos;
import com.becoblohm.cr.manejarservicio.OperacionLR;
import com.epa.crprinterdriver.PrinterNotConnectedException;

/** 
 * Descripci�n: 
 * 		Clase que maneja los perifericos del proyecto de CR. En principio maneja
 * �nicamente la impresora. Restar�a el manejo de Escaners, Visores, Teclados, etc.
 */
public class AbonosAnterioresLR extends Reporte {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AbonosAnterioresLR.class);
	
	// Variables necesarias para la impresion
	private static int columnasImpresora = 47;
	private static int columnasImpresoraFiscal = 38;
	
	/*
	* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
	* S�lo se parametriz� el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private static void crearEncabezadoFiscal(ListaRegalos lista) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal(ListaRegalos) - start");
		}
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("FECHA: "+formatoFecha.format(Sesion.getFechaSistema()),"HORA: "+Sesion.getHoraSistema(), columnasImpresoraFiscal),0);
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

		if(lista.getEstadoLista().charAt(0)==Sesion.LISTAREGALOS_CERRADA)
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("CIERRE DE" ,1);
		else if(lista.getEstadoLista().charAt(0)==Sesion.LISTAREGALOS_ANULADA)
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("ANULACION DE" ,1);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("LISTA DE REGALOS" ,1);
				
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);

		if (lista.getCliente().getCodCliente()!=null) {
			// Creamos las lineas con los datos del Cliente
			String cliente = "Cliente: ";
			String direccion = "Direcci�n: ";
			Vector<String> lineasNombre = dividirEnLineas(columnasImpresoraFiscal - 3 - cliente.length(),lista.getCliente().getNombreCompleto());
			Vector<String> lineasDireccion = dividirEnLineas(columnasImpresoraFiscal - 3 - direccion.length(),lista.getCliente().getDireccion());
//			*****************************************
//			Codigo a modificar por politicas de Fisco.
			String RIF = "CI/RIF: " + lista.getCliente().getCodCliente().replace('N','V');
			String NIT = ((lista.getCliente().getNit()!=null)&&(!lista.getCliente().getNit().equalsIgnoreCase("")))
						? "NIT:" + lista.getCliente().getNit()
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
			logger.debug("crearEncabezadoFiscal(ListaRegalos) - end");
		}
	}
	
	/**
	 * Crea el detalle de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generara el detalle.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	private static void crearDetalleFiscal(ListaRegalos lista) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalleFiscal(ListaRegalos, boolean) - start");
		}

		SimpleDateFormat fechaTrans = new SimpleDateFormat("dd/MM/yyyy");
		DecimalFormat precio = new DecimalFormat("#,##0.00");

		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Lista de Regalos:" + lista.getNumServicio(), "Fecha: " + fechaTrans.format(lista.getFechaServicio()), columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("CAJERO: " + Sesion.usuarioActivo.getNumFicha(), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("ABONOS ANTERIORES", 0);

		double montoAbono = 0;
		double montoVenta = 0;
		int numOper = 0;
		int j=1; 
		
		for (int i=0; i<lista.getOperaciones().size(); i++) {
			OperacionLR op = (OperacionLR)lista.getOperaciones().get(i);
			if (op.getTipoOper()==OperacionLR.ABONO_PARCIAL || op.getTipoOper()==OperacionLR.ABONO_TOTAL || op.getTipoOper()==OperacionLR.ABONO_LISTA) {
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Abono " + (j++), 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  " + fechaTrans.format(op.getFecha())/* + "  " + horaTrans.format(op.getFecha()) */+ "    " + op.getCodCajero(), 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  " + justificar("Monto Abono:", precio.format((op.getMontoBase() + op.getMontoImpuesto()) * op.getCantidad()), columnasImpresoraFiscal), 0);
				montoAbono += (op.getMontoBase() + op.getMontoImpuesto()) * op.getCantidad();
			} 
			if (op.getTipoOper()==OperacionLR.VENTA && fechaTrans.format(op.getFecha()).equals(fechaTrans.format(Sesion.getFechaSistema()))) {
				if (op.getNumOperacion()==numOper) {
					montoVenta += (op.getMontoBase()+op.getMontoImpuesto())*op.getCantidad();
				}
				if (op.getNumOperacion() > numOper) {
					numOper = op.getNumOperacion();
					montoVenta = (op.getMontoBase()+op.getMontoImpuesto())*op.getCantidad();
				}
			}
		}
		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		//Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Monto ListaRegalos:", precio.format(lista.consultarMontoServ()), columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Monto Abonos",precio.format(montoAbono),columnasImpresoraFiscal), 0);
		if (montoVenta>=montoAbono) {
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Abonos Anteriores",precio.format(montoAbono),columnasImpresoraFiscal), 0);
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Monto BR",precio.format(0),columnasImpresoraFiscal), 0);
		} else {
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Abonos Anteriores",precio.format(montoVenta),columnasImpresoraFiscal), 0);
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Monto BR",precio.format(montoAbono-montoVenta),columnasImpresoraFiscal), 0);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalleFiscal(ListaRegalos, boolean) - end");
		}
	}

	/**
	 * M�todo imprimirComprobate
	 * 	Realiza la impresion de la factura de Venta indicada como parametro.
	 * @param lista
	 */
	public static void imprimirComprobante(ListaRegalos lista) {
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirComprobate(ListaRegalos, boolean) - start");
		}

		if(Sesion.impresoraFiscal) {
			//Sesion.crFiscalPrinterOperations.activarSlip();
			try {
				//Sesion.crFiscalPrinterOperations.commit();
				//MensajesVentanas.aviso("Impresi�n de Comprobante de Abonos Anteriores para Cierre de Lista de Regalos\nIntroduzca el documento en la impresora");
				// Cadenas de secciones de la factura
				Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
				crearEncabezadoFiscal(lista);
				crearDetalleFiscal(lista);
				Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
				Sesion.crFiscalPrinterOperations.cortarPapel();
				Sesion.crFiscalPrinterOperations.commit();
			} catch (PrinterNotConnectedException e) {
				logger.error("imprimirComprobante(ListaRegalos, boolean)", e);
			}
		} else {
			// Esta Implementaccion no maneja Impresora NoFiscal
		}

		if (logger.isDebugEnabled()) {
			logger.debug("imprimirComprobante(ListaRegalos, boolean) - end");
		}
	}
}
