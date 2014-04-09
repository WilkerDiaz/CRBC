/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.impresion
 * Programa   : ComprobanteDeApartado.java
 * Creado por : gmartinelli
 * Creado en  : 08-jun-04 14:06
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
import java.util.Calendar;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.gui.PantallaVuelto;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarservicio.Apartado;
import com.becoblohm.cr.manejarservicio.DetalleServicio;
import com.becoblohm.cr.utiles.Control;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.crprinterdriver.PrinterNotConnectedException;

/** 
 * Descripción: 
 * 		Clase que maneja los perifericos del proyecto de CR. En principio maneja
 * únicamente la impresora. Restaría el manejo de Escaners, Visores, Teclados, etc.
 */
public class ComprobanteAnulacionApartado extends Reporte {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ComprobanteAnulacionApartado.class);
	
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
	private static void crearEncabezado(Apartado apartado) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezado(Apartado) - start");
		}

		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");
		Sesion.crPrinterOperations.activarFuenteImpresionNegrita();
		Sesion.crPrinterOperations.alinear(1);
		Sesion.crPrinterOperations.enviarString("ANULACION APARTADO\n");
		Sesion.crPrinterOperations.initializarPrinter();
		Sesion.crPrinterOperations.activarDocumentoNomal();
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");

		Sesion.crPrinterOperations.enviarString(justificar("Apartado N°: " + apartado.getNumServicio(), "Fecha: " + formatoFecha.format(apartado.getFechaServicio()),columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString(justificar("Tienda: " + Sesion.getTienda().getNombreSucursal(), "Página:" + alinearDerecha("1",11),columnasImpresora)+"\n");
		
		Calendar vencimiento = Calendar.getInstance();
		vencimiento.setTime(apartado.getFechaServicio());
		if (apartado.getTipoVigencia().equalsIgnoreCase("Dia")) {
			vencimiento.add(Calendar.DATE,apartado.getTiempoVigencia());
		} else {
			if (apartado.getTipoVigencia().equalsIgnoreCase("Mes")) {
				vencimiento.add(Calendar.MONTH,apartado.getTiempoVigencia());
			}
		}
		
		Sesion.crPrinterOperations.enviarString(justificar("Emisión: " + formatoFecha.format(apartado.getFechaServicio()), "Vence: " + formatoFecha.format(vencimiento.getTime()),columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString(justificar("Fecha Anulación: " + formatoFecha.format(Sesion.getFechaSistema()), "Cajero: " + Sesion.getUsuarioActivo().getNumFicha(),columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString("Cédula ó R.I.F.: " + apartado.getCliente().getCodCliente()+"\n");
		Sesion.crPrinterOperations.enviarString("Cliente: " + apartado.getCliente().getNombreCompleto()+"\n");
		
		// Picamos la direccion del Cliente
		String inicioLinea = "Dirección: ";
		Vector<String> dir = dividirEnLineas(columnasImpresora-inicioLinea.length(),apartado.getCliente().getDireccion());
		for (int i=0; i<dir.size();i++) {
			if (i==0)
				Sesion.crPrinterOperations.enviarString(inicioLinea + dir.elementAt(i)+"\n");
			else
				Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(inicioLinea.length(),' ')+dir.elementAt(i)+"\n");
		}
		
		//Se imprime el teléfono del cliente
		String codAreaCliente = "";
		String telfCliente = "";
		if(apartado.getCliente().getCodArea()!=null)
			codAreaCliente = apartado.getCliente().getCodArea();
		if(apartado.getCliente().getNumTelefono()!= null)
			telfCliente = apartado.getCliente().getNumTelefono();
		Sesion.crPrinterOperations.enviarString("Teléfonos: " + codAreaCliente + "-" + telfCliente +"\n");
		
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
	private static void crearDetalles(Apartado apartado) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalles(Apartado) - start");
		}

		DecimalFormat precio = new DecimalFormat("#,##0.00");
		DecimalFormat cantidad = new  DecimalFormat("#0.00");
		DetalleServicio detalleActual;
		
		// Para cada detalle de la venta
		for (int i=0; i<apartado.getDetallesServicio().size(); i++) {
			detalleActual = (DetalleServicio)apartado.getDetallesServicio().elementAt(i);

			int longitudCodigo = Sesion.getLongitudCodigoInterno();
			if (!(longitudCodigo > -1))
				longitudCodigo = Control.getLONGITUD_CODIGO();
				
			//Se chequea la longitud del código del producto para que salga en el reporte igual que como en la interfaz
			String codProd = detalleActual.getProducto().getCodProducto().toString();
			int longitud = codProd.length();
			if ((Sesion.getLongitudCodigoInterno()>-1)&&(longitud>Sesion.getLongitudCodigoInterno())) {
				codProd = codProd.substring(codProd.length()-Sesion.getLongitudCodigoInterno()); 
				codProd = centrar(codProd, Control.getLONGITUD_CODIGO());
				String codP = codProd;
				for (int j=0;j<Control.getLONGITUD_CODIGO() - codProd.length();j++) {
					codP = codP + " "; 
				}
				codProd = codP;
			}
			
			Sesion.crPrinterOperations.enviarString(justificar(alinearDerecha(codProd,longitudCodigo) + "   " 
							+ detalleActual.getProducto().getDescripcionCorta(), "1 " + detalleActual.getProducto().getAbreviadoUnidadVenta(),columnasImpresora-1)+"\n");
			if (!detalleActual.getCondicionVenta().equals(Sesion.condicionNormal)) {
				// Si se ha aplicado alguna rebaja (Promocion, Empaque, Cambio de Precio) se especifica
				Sesion.crPrinterOperations.enviarString(" PR:" + precio.format(detalleActual.getProducto().getPrecioRegular())
								+ " " + detalleActual.getCondicionVenta() + ":" + precio.format(detalleActual.getPrecioFinal())+"\n");
			}
			
			Sesion.crPrinterOperations.enviarString(alinearDerecha(cantidad.format(detalleActual.getCantidad()),9)
							+ alinearDerecha(precio.format(detalleActual.getPrecioFinal()), 19)
							+ alinearDerecha(precio.format(detalleActual.getCantidad() * detalleActual.getPrecioFinal()), 19)+"\n");
		}
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");

		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalles(Apartado) - end");
		}
	}
	
	/**
	 * Crea las lineas de los totales de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generaran los totales.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	private static void crearTotales(Apartado apartado, double totalAbonosActivos) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearTotales(Apartado, double) - start");
		}

		DecimalFormat precio = new DecimalFormat("#,##0.00");
		double cargoServicio = 0;
		
		if(apartado.getEstadoServicio() == Sesion.APARTADO_ANULADO_CON_CARGO)
			cargoServicio = apartado.calcularMtoCargoPorServicio();
			
		Sesion.crPrinterOperations.enviarString(alinearDerecha(justificar("SUB TOTAL          : ",precio.format(apartado.getMontoBase()),35),columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString(alinearDerecha(justificar("IMPUESTO           : ",precio.format(apartado.getMontoImpuesto()),35),columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString(alinearDerecha("--------------------",columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString(alinearDerecha(justificar("TOTAL APARTADO     : ",precio.format(apartado.consultarMontoServ()),35),columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString(alinearDerecha(justificar("ABONOS REALIZADOS  : ",precio.format(totalAbonosActivos),35),columnasImpresora)+"\n");
		
		Sesion.crPrinterOperations.enviarString(alinearDerecha(justificar("CARGO POR SERVICIO : ","(-)"+precio.format(cargoServicio),35),columnasImpresora)+"\n");

		Sesion.crPrinterOperations.enviarString(alinearDerecha("--------------------",columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString(alinearDerecha(justificar("TOTAL ANULACIÓN    : ",precio.format(totalAbonosActivos -  cargoServicio),35),columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");

		if (logger.isDebugEnabled()) {
			logger.debug("crearTotales(Apartado, double) - end");
		}
	}
	
	/**
	 * Crea el pie de pagina de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generara el pie de Pagina.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	private static void crearPieDePagina() {
		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePagina() - start");
		}

		Sesion.crPrinterOperations.enviarString("\nFirma Cliente    :   _____________________"+"\n\n");
		Sesion.crPrinterOperations.enviarString("Firma Autorizada :   _____________________"+"\n");
		
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");
		Sesion.crPrinterOperations.alinear(1);
		Sesion.crPrinterOperations.enviarString("DOCUMENTO NO FISCAL\n");
		Sesion.crPrinterOperations.alinear(0);
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");

		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePagina() - end");
		}
	}

	/**
	 * METODOS PARA LA IMPRESORA FISCAL
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private static void crearEncabezadoFiscal(Apartado apartado) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal(Apartado) - start");
		}

		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("ANULACION APARTADO", 1);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Apartado Nro: " + apartado.getNumServicio(), "Fecha: " + formatoFecha.format(apartado.getFechaServicio()),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Tienda: " + Sesion.getTienda().getNombreSucursal(), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Página:" + alinearDerecha("1",11), 0);
		
		Calendar vencimiento = Calendar.getInstance();
		vencimiento.setTime(apartado.getFechaServicio());
		if (apartado.getTipoVigencia().equalsIgnoreCase("Dia")) {
			vencimiento.add(Calendar.DATE,apartado.getTiempoVigencia());
		} else {
			if (apartado.getTipoVigencia().equalsIgnoreCase("Mes")) {
				vencimiento.add(Calendar.MONTH,apartado.getTiempoVigencia());
			}
		}
		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Emisión: " + formatoFecha.format(apartado.getFechaServicio()), "Vence: " + formatoFecha.format(vencimiento.getTime()),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Anulación: " + formatoFecha.format(Sesion.getFechaSistema()), "Cajero: " + Sesion.getUsuarioActivo().getNumFicha(),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Cédula ó R.I.F.: " + apartado.getCliente().getCodCliente().replace('N','V'), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Cliente: " + apartado.getCliente().getNombreCompleto(), 0);
		
		// Picamos la direccion del Cliente
		String direccion = apartado.getCliente().getDireccion();
		String inicioLinea = "Dirección: ";
		Vector<String> dir = dividirEnLineas(columnasImpresoraFiscal-inicioLinea.length(),apartado.getCliente().getDireccion());
		for (int i=0; i<dir.size();i++) {
			if (i==0)
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(inicioLinea + dir.elementAt(i), 0);
			else
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(inicioLinea.length(),' ')+dir.elementAt(i), 0);
		}
		
		//Se imprime el teléfono del cliente
		String codAreaCliente = "";
		String telfCliente = "";
		if(apartado.getCliente().getCodArea()!=null)
			codAreaCliente = apartado.getCliente().getCodArea();
		if(apartado.getCliente().getNumTelefono()!= null)
			telfCliente = apartado.getCliente().getNumTelefono();
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Teléfonos: " + codAreaCliente + "-" + telfCliente, 0);
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
	private static void crearDetallesFiscal(Apartado apartado) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearDetallesFiscal(Apartado) - start");
		}

		DecimalFormat precio = new DecimalFormat("#,##0.00");
		DecimalFormat cantidad = new  DecimalFormat("#0.00");
		DetalleServicio detalleActual;
		
		// Para cada detalle de la venta
		for (int i=0; i<apartado.getDetallesServicio().size(); i++) {
			detalleActual = (DetalleServicio)apartado.getDetallesServicio().elementAt(i);

			int longitudCodigo = Sesion.getLongitudCodigoInterno();
			if (!(longitudCodigo > -1))
				longitudCodigo = Control.getLONGITUD_CODIGO();
				
			//Se chequea la longitud del código del producto para que salga en el reporte igual que como en la interfaz
			String codProd = detalleActual.getProducto().getCodProducto().toString();
			int longitud = codProd.length();
			if ((Sesion.getLongitudCodigoInterno()>-1)&&(longitud>Sesion.getLongitudCodigoInterno())) {
				codProd = codProd.substring(codProd.length()-Sesion.getLongitudCodigoInterno()); 
				codProd = centrar(codProd, Control.getLONGITUD_CODIGO());
				String codP = codProd;
				for (int j=0;j<Control.getLONGITUD_CODIGO() - codProd.length();j++) {
					codP = codP + " "; 
				}
				codProd = codP;
			}
			
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar(alinearDerecha(codProd,longitudCodigo) + "   " 
							+ detalleActual.getProducto().getDescripcionCorta(), "1 " + detalleActual.getProducto().getAbreviadoUnidadVenta(),columnasImpresoraFiscal-1), 0);
			if (!detalleActual.getCondicionVenta().equals(Sesion.condicionNormal)) {
				// Si se ha aplicado alguna rebaja (Promocion, Empaque, Cambio de Precio) se especifica
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(" PR:" + precio.format(detalleActual.getProducto().getPrecioRegular())
								+ " " + detalleActual.getCondicionVenta() + ":" + precio.format(detalleActual.getPrecioFinal()), 0);
			}
			
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha(cantidad.format(detalleActual.getCantidad()),9)
							+ alinearDerecha(precio.format(detalleActual.getPrecioFinal()), 15)
							+ alinearDerecha(precio.format(detalleActual.getCantidad() * detalleActual.getPrecioFinal()), 16), 0);
		}
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);

		if (logger.isDebugEnabled()) {
			logger.debug("crearDetallesFiscal(Apartado) - end");
		}
	}
	
	/**
	 * Crea las lineas de los totales de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generaran los totales.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	private static void crearTotalesFiscal(Apartado apartado, double totalAbonosActivos) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearTotalesFiscal(Apartado, double) - start");
		}

		DecimalFormat precio = new DecimalFormat("#,##0.00");
		double cargoServicio = 0;
		
		if(apartado.getEstadoServicio() == Sesion.APARTADO_ANULADO_CON_CARGO)
			cargoServicio = apartado.calcularMtoCargoPorServicio();
			
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha(justificar("SUB TOTAL          : ",precio.format(apartado.getMontoBase()),35),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha(justificar("IMPUESTO           : ",precio.format(apartado.getMontoImpuesto()),35),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha("--------------------",columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha(justificar("TOTAL APARTADO     : ",precio.format(apartado.consultarMontoServ()),35),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha(justificar("ABONOS REALIZADOS  : ",precio.format(totalAbonosActivos),35),columnasImpresoraFiscal), 0);
		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha(justificar("CARGO POR SERVICIO : ","(-)"+precio.format(cargoServicio),35),columnasImpresoraFiscal), 0);

		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha("--------------------",columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha(justificar("TOTAL ANULACIÓN    : ",precio.format(totalAbonosActivos -  cargoServicio),35),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);

		if (logger.isDebugEnabled()) {
			logger.debug("crearTotalesFiscal(Apartado, double) - end");
		}
	}
	
	/**
	 * Crea el pie de pagina de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generara el pie de Pagina.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	private static void crearPieDePaginaFiscal() {
		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePaginaFiscal() - start");
		}

		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(" ", 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Firma Cliente   :  _____________________", 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(" ", 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Firma Autorizada:  _____________________", 0);

		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePaginaFiscal() - end");
		}
	}

	/**
	 * Método imprimirComprobante
	 * 	Realiza la impresion de la factura de Venta indicada como parametro.
	 * @param apartado
	 * @param totalAbonosActivos
	 */
	public static void imprimirComprobante(Apartado apartado, double totalAbonosActivos) {
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirComprobante(Apartado, double) - start");
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
				MensajesVentanas.aviso("Impresión de Comprobante de Anulación de Apartado\nIntroduzca el documento en la impresora");

				// Cadenas de secciones de la factura
				Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
				crearEncabezadoFiscal(apartado);
				crearDetallesFiscal(apartado);
				crearTotalesFiscal(apartado, totalAbonosActivos);
				crearPieDePaginaFiscal();
				Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
				Sesion.crFiscalPrinterOperations.commit();
			} catch (PrinterNotConnectedException e) {
				logger.error("imprimirComprobante(Apartado, double)", e);
			}
		} else {
			Sesion.crPrinterOperations.initializarPrinter();
			Sesion.crPrinterOperations.activarDocumentoNomal();
			//Sesion.crPrinterOperations.abrirPuertoImpresora();
			try {
				Sesion.crPrinterOperations.commit();
				MensajesVentanas.aviso("Impresión de Comprobante de Anulación de Apartado\nIntroduzca el documento en la impresora");
				// Cadenas de secciones de la factura
				Sesion.crPrinterOperations.limpiarBuffer();
				Sesion.crPrinterOperations.initializarPrinter();
				Sesion.crPrinterOperations.activarDocumentoNomal();
		
				crearEncabezado(apartado);
				crearDetalles(apartado);
				crearTotales(apartado, totalAbonosActivos);
				crearPieDePagina();
				Sesion.crPrinterOperations.cortarPapel();

				Sesion.crPrinterOperations.commit();
			} catch (PrinterNotConnectedException e) {
				logger.error("imprimirComprobante(Apartado, double)", e);
			}
			//Sesion.crPrinterOperations.cerrarPuertoImpresora();
		}
		if(apartado.getEstadoServicio() == Sesion.APARTADO_ANULADO_EXONERADO) {
			//****Se muestra el vuelto por visor y por pantalla
			// Se muestra el monto del vuelto al cliente por el visor
			double saldo;
			if(totalAbonosActivos*100 < 1)
				saldo = 0;
			else
				saldo = totalAbonosActivos;
			try { CR.crVisor.enviarString("SALDO CLIENTE",0,df.format(saldo),2); }
			catch (Exception e) {
				logger.error("imprimirComprobante(Apartado, double)", e);
			}
								
			//Se muestra el mensaje del vuelto al cajero
			PantallaVuelto pantallaVuelto = new PantallaVuelto(saldo, true);
			MensajesVentanas.centrarVentanaDialogo(pantallaVuelto);
			//***
		} else if (apartado.getEstadoServicio() == Sesion.APARTADO_ANULADO_CON_CARGO) {
			//****Se muestra el vuelto por visor y por pantalla
			// Se muestra el monto del vuelto al cliente por el visor
			double saldo;
			if((totalAbonosActivos - apartado.calcularMtoCargoPorServicio())*100 < 1)
				saldo = 0;
			else
				saldo = totalAbonosActivos - apartado.calcularMtoCargoPorServicio();
			try { CR.crVisor.enviarString("SALDO CLIENTE",0,df.format(saldo),2); }
			catch (Exception e) {
				logger.error("imprimirComprobante(Apartado, double)", e);
			}
								
			//Se muestra el mensaje del vuelto al cajero
			PantallaVuelto pantallaVuelto = new PantallaVuelto(saldo, true);
			MensajesVentanas.centrarVentanaDialogo(pantallaVuelto);
			//***
		}

		if (logger.isDebugEnabled()) {
			logger.debug("imprimirComprobante(Apartado, double) - end");
		}
	}
}
