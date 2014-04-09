/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.reportes
 * Programa   : AnulacionDeAbono.java
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
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.gui.PantallaVuelto;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarservicio.Abono;
import com.becoblohm.cr.manejarservicio.Apartado;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.crprinterdriver.PrinterNotConnectedException;

/** 
 * Descripción: 
 * 		Clase que maneja los perifericos del proyecto de CR. En principio maneja
 * únicamente la impresora. Restaría el manejo de Escaners, Visores, Teclados, etc.
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se comentó variable sin uso
* Fecha: agosto 2011
*/
public class AnulacionDeAbono extends Reporte {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(AnulacionDeAbono.class);
	
	// Variables necesarias para la impresion
	private static int columnasImpresora = 47;
	private static int columnasImpresoraFiscal = 40;
	//private static int numAbonoRealizado;
	
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
	private static void crearEncabezado(Apartado apartado, Abono abono) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezado(Apartado, Abono) - start");
		}

		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
		
		Sesion.crPrinterOperations.activarFuenteImpresionNegrita();
		Sesion.crPrinterOperations.alinear(1);
		Sesion.crPrinterOperations.enviarString(Sesion.getTienda().getRazonSocial()+"\n");
		Sesion.crPrinterOperations.initializarPrinter();
		Sesion.crPrinterOperations.activarDocumentoNomal();
		Sesion.crPrinterOperations.alinear(1);

		String lineaDeDireccion = "";
		if((Sesion.getTienda().getDireccionFiscal() != null)&&(!Sesion.getTienda().getDireccionFiscal().equalsIgnoreCase("")))
			lineaDeDireccion += Sesion.getTienda().getDireccionFiscal();
		if((Sesion.getTienda().getNombreSucursal() != null)&&(!Sesion.getTienda().getNombreSucursal().equalsIgnoreCase("")))
			lineaDeDireccion += " Sucursal:" + Sesion.getTienda().getNombreSucursal() + ".";
		if((Sesion.getTienda().getDireccion() != null)&&(!Sesion.getTienda().getDireccion().equalsIgnoreCase("")))
			lineaDeDireccion += Sesion.getTienda().getDireccion();
		if (!lineaDeDireccion.equalsIgnoreCase("")) {
			Vector<String> nuevasLineasDireccion = dividirEnLineas(columnasImpresora,lineaDeDireccion);
			for (int i=0; i<nuevasLineasDireccion.size(); i++)
				Sesion.crPrinterOperations.enviarString((String)nuevasLineasDireccion.elementAt(i)+"\n");
		}
		//Se imprime el teléfono de la tienda
		String codArea = "";
		String telfTienda = "";
		if(Sesion.getTienda().getCodArea()!=null)
			codArea = Sesion.getTienda().getCodArea();
		if(Sesion.getTienda().getNumTelefono()!= null)
			telfTienda = Sesion.getTienda().getNumTelefono();
		Sesion.crPrinterOperations.enviarString("Teléfono: " + codArea + "-" + telfTienda+"\n");
		
		if ((Sesion.getTienda().getNit()!=null)&&(!Sesion.getTienda().getNit().equalsIgnoreCase("")))
			Sesion.crPrinterOperations.enviarString("RIF:" + Sesion.getTienda().getRif()  + "   NIT:" + Sesion.getTienda().getNit()+"\n");
		else
			Sesion.crPrinterOperations.enviarString("RIF:" + Sesion.getTienda().getRif()+"\n");
		Sesion.crPrinterOperations.alinear(0);
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");
		Sesion.crPrinterOperations.activarFuenteImpresionNegrita();
		Sesion.crPrinterOperations.alinear(1);
		Sesion.crPrinterOperations.enviarString("ANULACION DE ABONO\n");
		Sesion.crPrinterOperations.initializarPrinter();
		Sesion.crPrinterOperations.activarDocumentoNomal();
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");		
		Sesion.crPrinterOperations.enviarString(justificar("Caja:" + Sesion.getCaja().getNumero(), "Cajero:" + Sesion.getUsuarioActivo().getNumFicha(), columnasImpresora));
		String fecha = "Fecha:" + formatoFecha.format(abono.getFechaAbono());
		String hora = "Hora:" + formatoHora.format(abono.getHoraInicia());
		String numAbono = "Abono:" + abono.getNumAbono();
		Sesion.crPrinterOperations.enviarString(justificar(fecha, hora, columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString(justificar("Servicio:" + apartado.getNumServicio(), numAbono, columnasImpresora)+"\n");

		// Imprimimos los datos del cliente del apartado/PE
		if (apartado.getCliente().getCodCliente()!=null) {
			// Creamos las lineas con los datos del Cliente
			String cliente = "Cliente: ";
			String direccion = "Dirección: ";
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
			logger.debug("crearEncabezado(Apartado, Abono) - end");
		}
	}
	
	/**
	 * Crea el detalle de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generara el detalle.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	private static void crearDetalle(Apartado apartado, Abono abono) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalle(Apartado, Abono) - start");
		}

		DecimalFormat precio = new DecimalFormat("#,##0.00");
		Sesion.crPrinterOperations.initializarPrinter();
		Sesion.crPrinterOperations.activarDocumentoNomal();
		Sesion.crPrinterOperations.enviarString(justificar("Monto Servicio", precio.format(apartado.consultarMontoServ()),columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString(justificar("Abonos Realizados", "(-)" + precio.format(apartado.montoAbonos()+abono.getMontoBase()),columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString(alinearDerecha("-----------------", columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString(justificar("Saldo Anterior", precio.format(apartado.consultarMontoServ()-(apartado.montoAbonos()+abono.getMontoBase())),columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString(justificar("Abono Anulado", "(+)" + precio.format(abono.getMontoBase()),columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString(alinearDerecha("-----------------", columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString(justificar("Saldo Actual", precio.format(apartado.consultarMontoServ()-apartado.montoAbonos()),columnasImpresora)+"\n");

		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalle(Apartado, Abono) - end");
		}
	}
	
	/**
	 * Método crearPieDePagina
	 * 
	 * 
	 * void
	 */
	private static void crearPieDePagina() {
		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePagina() - start");
		}

		Sesion.crPrinterOperations.initializarPrinter();
		Sesion.crPrinterOperations.activarDocumentoNomal();
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora));
		Sesion.crPrinterOperations.activarFuenteImpresionNegrita();
		Sesion.crPrinterOperations.alinear(1);
		Sesion.crPrinterOperations.enviarString("ANULACION DE ABONO\n");
		Sesion.crPrinterOperations.initializarPrinter();
		Sesion.crPrinterOperations.activarDocumentoNomal();
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");
		Sesion.crPrinterOperations.alinear(1);
		Sesion.crPrinterOperations.enviarString("DOCUMENTO NO FISCAL\n");
		Sesion.crPrinterOperations.alinear(0);
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");

		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePagina() - end");
		}
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private static void crearEncabezadoFiscal(Apartado apartado, Abono abono) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal(Apartado, Abono) - start");
		}

		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Sesion.getTienda().getRazonSocial(), 1);

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
		//Se imprime el teléfono de la tienda
		String codArea = "";
		String telfTienda = "";
		if(Sesion.getTienda().getCodArea()!=null)
			codArea = Sesion.getTienda().getCodArea();
		if(Sesion.getTienda().getNumTelefono()!= null)
			telfTienda = Sesion.getTienda().getNumTelefono();	
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar("Teléfono: " + codArea + "-" + telfTienda, columnasImpresoraFiscal), 0);
		
		if ((Sesion.getTienda().getNit()!=null)&&(!Sesion.getTienda().getNit().equalsIgnoreCase("")))
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar("RIF:" + Sesion.getTienda().getRif()  + "   NIT:" + Sesion.getTienda().getNit(), columnasImpresoraFiscal), 0);
		else
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar("RIF:" + Sesion.getTienda().getRif(), columnasImpresoraFiscal), 0);

		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("ANULACION DE ABONO", 1);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Caja:" + Sesion.getCaja().getNumero(), "Cajero:" + Sesion.getUsuarioActivo().getNumFicha(), columnasImpresoraFiscal), 0);
		String fecha = "Fecha:" + formatoFecha.format(abono.getFechaAbono());
		String hora = "Hora:" + formatoHora.format(abono.getHoraInicia());
		String numAbono = "Abono:" + abono.getNumAbono();
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar(fecha, hora, columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Servicio:" + apartado.getNumServicio(), numAbono, columnasImpresoraFiscal), 0);

		// Imprimimos los datos del cliente del apartado/PE
		if (apartado.getCliente().getCodCliente()!=null) {
			// Creamos las lineas con los datos del Cliente
			String cliente = "Cliente: ";
			String direccion = "Dirección: ";
			Vector<String> lineasNombre = dividirEnLineas(columnasImpresoraFiscal - 3 - cliente.length(),apartado.getCliente().getNombreCompleto());
			Vector<String> lineasDireccion = dividirEnLineas(columnasImpresoraFiscal - 3 - direccion.length(),apartado.getCliente().getDireccion());
			String RIF = "CI/RIF: " + apartado.getCliente().getCodCliente();
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
			logger.debug("crearEncabezadoFiscal(Apartado, Abono) - end");
		}
	}
	
	private static void crearDetalleFiscal(Apartado apartado, Abono abono) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalleFiscal(Apartado, Abono) - start");
		}

		DecimalFormat precio = new DecimalFormat("#,##0.00");
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Monto Servicio", precio.format(apartado.consultarMontoServ()),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Abonos Realizados", "(-)" + precio.format(apartado.montoAbonos()+abono.getMontoBase()),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha("-----------------", columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Saldo Anterior", precio.format(apartado.consultarMontoServ()-(apartado.montoAbonos()+abono.getMontoBase())),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Abono Anulado", "(+)" + precio.format(abono.getMontoBase()),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha("-----------------", columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Saldo Actual", precio.format(apartado.consultarMontoServ()-apartado.montoAbonos()),columnasImpresoraFiscal), 0);

		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalleFiscal(Apartado, Abono) - end");
		}
	}
	
	private static void crearPieDePaginaFiscal() {
		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePaginaFiscal() - start");
		}

		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("ANULACION DE ABONO", 1);

		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePaginaFiscal() - end");
		}
	}

	/**
	 * Método imprimirComprobate
	 * 	Realiza la impresion de la factura de Venta indicada como parametro.
	 * @param apartado
	 * @param renglonAbono
	 */
	public static void imprimirComprobate(Apartado apartado, int renglonAbono) {
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirComprobate(Apartado, int) - start");
		}

		DecimalFormat df = new DecimalFormat("#,##0.00");
		//********
		//Se verifica si la gaveta se abre por impresora o por el visor. Si es por impresora debemos abrir
		//primero la gaveta antes de enviar a imprimir porque si no, se queda la gaveta cerrada hasta que termina la impresion.
		
		if (Sesion.aperturaGavetaPorImpresora) {
			try {
				CR.me.abrirGaveta(false);
			} catch (Exception e){
				logger.error("imprimirFactura(Venta)", e);
			}
		 }
		//********		
		if(Sesion.impresoraFiscal) {
			Sesion.crFiscalPrinterOperations.activarSlip();
			try {
				Sesion.crFiscalPrinterOperations.commit();
				MensajesVentanas.aviso("Impresión de Aulación de Abono\nIntroduzca el documento en la impresora");
				// Cadenas de secciones de la factura
				Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
				//numAbonoRealizado = ((Abono)apartado.getAbonos().lastElement()).getNumAbono();
				
				crearEncabezadoFiscal(apartado, (Abono)apartado.getAbonos().elementAt(renglonAbono));
				crearDetalleFiscal(apartado, (Abono)apartado.getAbonos().elementAt(renglonAbono));
				crearPieDePaginaFiscal();
				Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
				Sesion.crFiscalPrinterOperations.commit();
			} catch (PrinterNotConnectedException e) {
				logger.error("imprimirComprobate(Apartado, int)", e);
			}
		} else {
			Sesion.crPrinterOperations.initializarPrinter();
			Sesion.crPrinterOperations.activarDocumentoNomal();
			//Sesion.crPrinterOperations.abrirPuertoImpresora();
			try {
				Sesion.crPrinterOperations.commit();
				MensajesVentanas.aviso("Impresión de Aulación de Abono\nIntroduzca el documento en la impresora");
				// Cadenas de secciones de la factura
				//numAbonoRealizado = ((Abono)apartado.getAbonos().lastElement()).getNumAbono();
				Sesion.crPrinterOperations.limpiarBuffer();
				Sesion.crPrinterOperations.initializarPrinter();
				Sesion.crPrinterOperations.activarDocumentoNomal();
				
				crearEncabezado(apartado, (Abono)apartado.getAbonos().elementAt(renglonAbono));
				crearDetalle(apartado, (Abono)apartado.getAbonos().elementAt(renglonAbono));
				crearPieDePagina();
				Sesion.crPrinterOperations.cortarPapel();
				Sesion.crPrinterOperations.commit();
			} catch (PrinterNotConnectedException e) {
				logger.error("imprimirComprobate(Apartado, int)", e);
			}
			//Sesion.crPrinterOperations.cerrarPuertoImpresora();
		}

		//****Se muestra el vuelto por visor y por pantalla
		// Se muestra el monto del vuelto al cliente por el visor
		double saldo;
		if(((Abono)apartado.getAbonos().elementAt(renglonAbono)).getMontoBase()*100 < 1)
			saldo = 0;
		else
			saldo = ((Abono)apartado.getAbonos().elementAt(renglonAbono)).getMontoBase();
		try { CR.crVisor.enviarString("SALDO CLIENTE",0,df.format(saldo),2); }
		catch (Exception e) {
			logger.error("imprimirComprobate(Apartado, int)", e);
		}
								
		//Se muestra el mensaje del vuelto al cajero
		PantallaVuelto pantallaVuelto = new PantallaVuelto(saldo, true);
		MensajesVentanas.centrarVentanaDialogo(pantallaVuelto);
		//***

		if (logger.isDebugEnabled()) {
			logger.debug("imprimirComprobate(Apartado, int) - end");
		}
	}
}