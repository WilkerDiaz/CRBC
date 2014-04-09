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
import com.becoblohm.cr.manejarservicio.Abono;
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
public class ComprobanteDeApartado extends Reporte {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ComprobanteDeApartado.class);
	
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
		Sesion.crPrinterOperations.enviarString("A P A R T A D O\n");
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
		
		Sesion.crPrinterOperations.enviarString(justificar("Emisión: " + formatoFecha.format(Sesion.getFechaSistema()), "Vence: " + formatoFecha.format(vencimiento.getTime()),columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString("Cédula ó R.I.F.: " + apartado.getCliente().getCodCliente().replace('N','V')+"\n");
		Sesion.crPrinterOperations.enviarString("Cliente: " + apartado.getCliente().getNombreCompleto()+"\n");
		
		// Imprimimos la direccion del cliente
		String inicioLinea = "Dirección: ";
		Vector<String> direccion = dividirEnLineas(columnasImpresora-inicioLinea.length(),apartado.getCliente().getDireccion());
		for (int i=0; i<direccion.size();i++) {
			if (i==0)
				Sesion.crPrinterOperations.enviarString(inicioLinea + direccion.elementAt(i)+"\n");
			else
				Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(inicioLinea.length(),' ')+direccion.elementAt(i)+"\n");
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
	private static void crearTotales(Apartado apartado) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearTotales(Apartado) - start");
		}

		DecimalFormat precio = new DecimalFormat("#,##0.00");
		
		Sesion.crPrinterOperations.enviarString(alinearDerecha(justificar("SUB TOTAL     : ",precio.format(apartado.getMontoBase()),35),columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString(alinearDerecha(justificar("IMPUESTO      : ",precio.format(apartado.getMontoImpuesto()),35),columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString(alinearDerecha("--------------------",columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString(alinearDerecha(justificar("TOTAL APARTADO: ",precio.format(apartado.consultarMontoServ()),35),columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString(alinearDerecha(justificar("ABONO INICIAL : ","(-)" + precio.format(apartado.montoAbonos()),35),columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString(alinearDerecha("--------------------",columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString(alinearDerecha(justificar("SALDO         : ",precio.format(apartado.consultarMontoServ()-apartado.montoAbonos()),35),columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");

		if (logger.isDebugEnabled()) {
			logger.debug("crearTotales(Apartado) - end");
		}
	}
	
	/**
	 * Crea el pie de pagina de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generara el pie de Pagina.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	private static void crearPieDePagina(int numDocumento) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePagina(int) - start");
		}

		Sesion.crPrinterOperations.alinear(1);
		Sesion.crPrinterOperations.enviarString("DOCUMENTO NO FISCAL\n");
		Sesion.crPrinterOperations.alinear(0);
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString("CONDICIONES:\n");
		Sesion.crPrinterOperations.enviarString("- La fecha de retiro de apartado no podrá exce-\n");
		Sesion.crPrinterOperations.enviarString("  der de 30 días, contados a partir de la fecha\n");
		Sesion.crPrinterOperations.enviarString("  de emisión de este documento.\n");
		Sesion.crPrinterOperations.enviarString("- Transcurrido el plazo  para el retiro y pago,\n");
		Sesion.crPrinterOperations.enviarString("  si el Cliente no hubiere pagado y retirado el\n");
		Sesion.crPrinterOperations.enviarString("  producto, BECO podrá disponer del mismo.\n");
		Sesion.crPrinterOperations.enviarString("- Si el  Cliente anula la reservación o no paga\n");
		Sesion.crPrinterOperations.enviarString("  la  totalidad  del  precio  dentro  del plazo\n");
		Sesion.crPrinterOperations.enviarString("  establecido   como   fecha  de  retiro,  este\n");
		Sesion.crPrinterOperations.enviarString("  autoriza expresamente a BECO para cobrarle un\n");
		Sesion.crPrinterOperations.enviarString("  \"Cargo por Servicio\" del diez porciento (10%)\n");
		Sesion.crPrinterOperations.enviarString("  del valor total del producto reservado.\n");
		Sesion.crPrinterOperations.enviarString("- Es indispensable la presentación de esta Nota\n");
		Sesion.crPrinterOperations.enviarString("  de Servicio, tanto  para realizar \"abonos\" en\n");
		Sesion.crPrinterOperations.enviarString("  cuenta, como para \"retirar\" el producto.\n");
		Sesion.crPrinterOperations.enviarString("\n");
		Sesion.crPrinterOperations.enviarString("NOTA:\n");
		Sesion.crPrinterOperations.enviarString("Valor: Es el precio que  indique  al  bien para\n");
		Sesion.crPrinterOperations.enviarString("       la fecha de su reservación.\n");
		Sesion.crPrinterOperations.enviarString("Precio:Es el precio que indique el bien para la\n");
		Sesion.crPrinterOperations.enviarString("       fecha de  su  reservación, mas  el monto\n");
		Sesion.crPrinterOperations.enviarString("       correspondiente a los impuestos vigentes\n");
		Sesion.crPrinterOperations.enviarString("       para el momento del pago total.\n");
		Sesion.crPrinterOperations.enviarString("\n");
		Sesion.crPrinterOperations.enviarString("\n");
		String firmaCliente = "CLIENTE: ";
		for (int i=firmaCliente.length(); i<(columnasImpresora-1)/2; i++) firmaCliente+="_";
		String autorizadoPor = "AUTORIZADO: ";
		for (int i=autorizadoPor.length(); i<(columnasImpresora-1)/2; i++) autorizadoPor+="_";
		Sesion.crPrinterOperations.enviarString(firmaCliente + " " + autorizadoPor + "\n");
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");
		Sesion.crPrinterOperations.alinear(1);
		Sesion.crPrinterOperations.enviarString("DOCUMENTO NO FISCAL\n");
		Sesion.crPrinterOperations.enviarString("COPIA "+ numDocumento + "\n");
		Sesion.crPrinterOperations.alinear(0);
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");

		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePagina(int) - end");
		}
	}

	//*********************** MÉTODOS PARA IMPRESION CON IMPRESORA FISCAL ***********************
	//******************************************************************************************
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
	private static void crearEncabezadoFiscal(Apartado apartado) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal(Apartado) - start");
		}

		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		Calendar vencimiento = Calendar.getInstance();
		vencimiento.setTime(apartado.getFechaServicio());
		if (apartado.getTipoVigencia().equalsIgnoreCase("Dia")) {
			vencimiento.add(Calendar.DATE,apartado.getTiempoVigencia());
		} else {
			if (apartado.getTipoVigencia().equalsIgnoreCase("Mes")) {
				vencimiento.add(Calendar.MONTH,apartado.getTiempoVigencia());
			}
		}
		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("APARTADO NRO: " + apartado.getNumServicio(),1);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal),0);

		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Emisión: " + formatoFecha.format(Sesion.getFechaSistema()), "Vence: " + formatoFecha.format(vencimiento.getTime()),columnasImpresoraFiscal),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Cajero: " + Sesion.usuarioActivo.getNumFicha(),0);
		//Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Tienda: " + Sesion.getTienda().getNombreSucursal(),0);
		
		//Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Página: 1",0);
		
		//Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Emisión: " + formatoFecha.format(Sesion.getFechaSistema()), "Vence: " + formatoFecha.format(vencimiento.getTime()),columnasImpresoraFiscal),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Cédula ó R.I.F.: " + (apartado.getCliente().getCodCliente() == "N" ? "V":apartado.getCliente().getCodCliente()),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Cliente: " + apartado.getCliente().getNombreCompleto(),0);
		
		//Se envia el teléfono del cliente	
		String codAreaCliente = "";
		String telfCliente = "";
		if(apartado.getCliente().getCodArea()!=null)
			codAreaCliente = apartado.getCliente().getCodArea();
		if(apartado.getCliente().getNumTelefono()!= null)
			telfCliente = apartado.getCliente().getNumTelefono();	
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Teléfono: " + codAreaCliente + "-" + telfCliente,0);
		
		// Imprimimos la direccion del cliente
		String inicioLinea = "Dirección: ";
		Vector<String> direccion = dividirEnLineas(columnasImpresoraFiscal-inicioLinea.length(),apartado.getCliente().getDireccion());
		for (int i=0; i<direccion.size();i++) {
			if (i==0)
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(inicioLinea + direccion.elementAt(i),0);
			else
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(inicioLinea.length(),' ')+direccion.elementAt(i),0);
		}
		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal),0);

		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal(Apartado) - end");
		}
	}


	/**
	 * Crea el encabezado de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generara el encabezado.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	private static void crearEncabezadoFiscalDeCondiciones(Apartado apartado) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal(Apartado) - start");
		}

		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		Calendar vencimiento = Calendar.getInstance();
		vencimiento.setTime(apartado.getFechaServicio());
		if (apartado.getTipoVigencia().equalsIgnoreCase("Dia")) {
			vencimiento.add(Calendar.DATE,apartado.getTiempoVigencia());
		} else {
			if (apartado.getTipoVigencia().equalsIgnoreCase("Mes")) {
				vencimiento.add(Calendar.MONTH,apartado.getTiempoVigencia());
			}
		}
		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("APARTADO NRO: " + apartado.getNumServicio(),1);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal),0);

		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Emisión: " + formatoFecha.format(Sesion.getFechaSistema()), "Vence: " + formatoFecha.format(vencimiento.getTime()),columnasImpresoraFiscal),0);
		//Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Tienda: " + Sesion.getTienda().getNombreSucursal(),0);
		
	/*	Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Página: 1",0);
		
		//Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Emisión: " + formatoFecha.format(Sesion.getFechaSistema()), "Vence: " + formatoFecha.format(vencimiento.getTime()),columnasImpresoraFiscal),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Cédula ó R.I.F.: " + apartado.getCliente().getCodCliente(),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Cliente: " + apartado.getCliente().getNombreCompleto(),0);
		
		//Se envia el teléfono del cliente	
		String codAreaCliente = "";
		String telfCliente = "";
		if(apartado.getCliente().getCodArea()!=null)
			codAreaCliente = apartado.getCliente().getCodArea();
		if(apartado.getCliente().getNumTelefono()!= null)
			telfCliente = apartado.getCliente().getNumTelefono();	
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Teléfono: " + codAreaCliente + "-" + telfCliente,0);
		
		// Imprimimos la direccion del cliente
		String inicioLinea = "Dirección: ";
		Vector direccion = dividirEnLineas(columnasImpresoraFiscal-inicioLinea.length(),apartado.getCliente().getDireccion());
		for (int i=0; i<direccion.size();i++) {
			if (i==0)
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(inicioLinea + direccion.elementAt(i),0);
			else
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(inicioLinea.length(),' ')+direccion.elementAt(i),0);
		}
		*/
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal),0);

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
							+ detalleActual.getProducto().getDescripcionCorta(), "1 " + detalleActual.getProducto().getAbreviadoUnidadVenta(),columnasImpresoraFiscal-1),0);
			if (!detalleActual.getCondicionVenta().equals(Sesion.condicionNormal)) {
				// Si se ha aplicado alguna rebaja (Promocion, Empaque, Cambio de Precio) se especifica
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(" PR:" + precio.format(detalleActual.getProducto().getPrecioRegular())
								+ " " + detalleActual.getCondicionVenta() + ":" + precio.format(detalleActual.getPrecioFinal()),0);
			}
			
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha(cantidad.format(detalleActual.getCantidad()),9)
							+ alinearDerecha(precio.format(detalleActual.getPrecioFinal()), 15)
							+ alinearDerecha(precio.format(detalleActual.getCantidad() * detalleActual.getPrecioFinal()), 16),0);
		}
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal),0);

		if (logger.isDebugEnabled()) {
			logger.debug("crearDetallesFiscal(Apartado) - end");
		}
	}
	
	/**
	 * Crea las lineas de los totales de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generaran los totales.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	private static void crearTotalesFiscal(int numDocumento, Apartado apartado) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearTotalesFiscal(Apartado) - start");
		}

		DecimalFormat precio = new DecimalFormat("#,##0.00");
		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha(justificar("SUB TOTAL     : ",precio.format(apartado.getMontoBase()),28),columnasImpresoraFiscal),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha(justificar("IMPUESTO      : ",precio.format(apartado.getMontoImpuesto()),28),columnasImpresoraFiscal),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha("--------------------",columnasImpresoraFiscal),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha(justificar("TOTAL APARTADO: ",precio.format(apartado.consultarMontoServ()),28),columnasImpresoraFiscal),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha(justificar("ABONO INICIAL : ","(-)" + precio.format(apartado.montoAbonos()),28),columnasImpresoraFiscal),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha("--------------------",columnasImpresoraFiscal),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha(justificar("SALDO         : ",precio.format(apartado.consultarMontoServ()-apartado.montoAbonos()),28),columnasImpresoraFiscal),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal),0);

		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar("COPIA "+ numDocumento, columnasImpresoraFiscal),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal),0);

		if (logger.isDebugEnabled()) {
			logger.debug("crearTotalesFiscal(Apartado) - end");
		}
	}
	
	/**
	 * Crea el pie de pagina de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generara el pie de Pagina.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	private static void crearPieDePaginaFiscal(Apartado apartado) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePaginaFiscal(int) - start");
		}

/*		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("CONDICIONES:",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("- La  fecha  de  retiro  de  apartado no",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  podrá  exceder  de 30 días, contados a",0);		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  partir  de la fecha de emision de este",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  documento.",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("- Transcurrido  el plazo  para el retiro",0);		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  y  pago,  si  el  cliente  no  hubiere",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  pagado  y  retirado  el producto, BECO",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  podrá disponer del mismo.",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("- Si  el  Cliente anula la reservación o",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  no   paga   la  totalidad  del  precio",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  dentro  del  plazo  establecido   como",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  fecha   de   retiro,   este   autoriza",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  expresamente  a  BECO para cobrarle un",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  \"Cargo   por   Servicio\"   del  diez",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  porciento (10%) del  valor  total  del",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  producto reservado.",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("- Es  indispensable  la  presentación de",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  esta   Nota  de  Servicio, tanto  para",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  realizar  \"abonos\"  en  cuenta, como",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  para \"retirar\" el producto.",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(" ",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("NOTA:",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Valor: Es  el precio que indique al bien",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("       para la  fecha de su reservación.",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Precio: Es el precio que indique el bien",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("        para la fecha de su reservación,",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("        mas  el  monto correspondiente a",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("        los  impuestos  vigentes para el",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("        momento del pago total.",0);
	*/	Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(" ",0);
		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(" ",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Firma del Cliente:   ___________________",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("C.I./R.I.F.: " + apartado.getCliente().getCodCliente(),0);	
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(" ",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Firma Autorizada:    ___________________",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Cajero: " + Sesion.usuarioActivo.getNumFicha(),0);
	/*
	 	Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar("COPIA "+ numDocumento, columnasImpresoraFiscal),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal),0);
	*/

		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePaginaFiscal(int) - end");
		}
	}
	
	
	/**
	 * Crea el pie de pagina de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generara el pie de Pagina.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	private static void crearDorsoApartado(/*int numDocumento*/) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePaginaFiscal(int) - start");
		}

		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("CONDICIONES:",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("- Para retirar el producto es indispen-",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  sable pagar el precio completo.",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("- La fecha de pago y retiro del producto",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  no podra exceder de 30 días desde la",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  fecha de apertura del apartado.",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("- En caso de que el cliente anule el",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  apartado o si transcurrido el plazo de",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  30 días no ha pagado el precio comple-",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  to o retirado el producto, autoriza",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  expresamente a Centrobeco C.A. a dis-",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  poner del mismo, y a cobrarle un cargo",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  por servicio equivalente al 10% del",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  precio del producto excluido cualquier",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  impuesto.",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("- Para realizar abonos en cuenta y/o re-",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  tirar el apartado, es indispensable",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("  presentar este documento.",0);
	
		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePaginaFiscal(int) - end");
		}
	}
	
	/**
	 * Permite imprimir las etiquetas que se generan para identificar el apartado
	 * @param apartado Apartado al que se le van a imprimir las estiquetas
	 */
	private static void crearEtiquetasApartado(Apartado apartado) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEtiquetasApartado(Apartado) - start");
		}

		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		DecimalFormat monto = new DecimalFormat("#,##0.00");
		
		// Calculamos le fecha de vencimiento del apartado para mostrarla en las etiquetas
		Calendar fechaVencimiento = Calendar.getInstance();
		fechaVencimiento.setTime(apartado.getFechaServicio());
		if (apartado.getTipoVigencia().equalsIgnoreCase("Dia")) {
			fechaVencimiento.add(Calendar.DATE,apartado.getTiempoVigencia());
		} else {
			if (apartado.getTipoVigencia().equalsIgnoreCase("Mes")) {
				fechaVencimiento.add(Calendar.MONTH,apartado.getTiempoVigencia());
			}
		}
		
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");
		Sesion.crPrinterOperations.activarFuenteImpresionNegrita();
		Sesion.crPrinterOperations.enviarString("APARTADO: " + String.valueOf(apartado.getNumServicio())+"\n");
		Sesion.crPrinterOperations.enviarString("\n");
		
		Sesion.crPrinterOperations.initializarPrinter();
		Sesion.crPrinterOperations.activarDocumentoNomal();
		
		Sesion.crPrinterOperations.enviarString("Cédula/R.I.F.: " + apartado.getCliente().getCodCliente()+"\n");
		Sesion.crPrinterOperations.enviarString("Nombre: " + apartado.getCliente().getNombreCompleto()+"\n");
		
		//Se imprime el teléfono del cliente
		String codAreaCliente = "";
		String telfCliente = "";
		if(apartado.getCliente().getCodArea()!=null)
			codAreaCliente = apartado.getCliente().getCodArea();
		if(apartado.getCliente().getNumTelefono()!= null)
			telfCliente = apartado.getCliente().getNumTelefono();
		Sesion.crPrinterOperations.enviarString("Teléfonos: " + codAreaCliente + "-" + telfCliente +"\n");

		Sesion.crPrinterOperations.enviarString(justificar("Emision: " + formatoFecha.format(apartado.getFechaServicio()),"Vence: " + formatoFecha.format(fechaVencimiento.getTime()), columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString("Monto del Apartado: " + monto.format(apartado.getMontoBase()+apartado.getMontoImpuesto())+"\n");
	
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString("\n");
		Sesion.crPrinterOperations.enviarString("\n");

		if (logger.isDebugEnabled()) {
			logger.debug("crearEtiquetasApartado(Apartado) - end");
		}
	}
	
	/**
	 * Permite imprimir las etiquetas que se generan para identificar el apartado
	 * @param apartado Apartado al que se le van a imprimir las estiquetas
	 */
	private static void crearEtiquetasApartadoFiscal(Apartado apartado) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEtiquetasApartadoFiscal(Apartado) - start");
		}

		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		DecimalFormat monto = new DecimalFormat("#,##0.00");
		
		// Calculamos le fecha de vencimiento del apartado para mostrarla en las etiquetas
		Calendar fechaVencimiento = Calendar.getInstance();
		fechaVencimiento.setTime(apartado.getFechaServicio());
		if (apartado.getTipoVigencia().equalsIgnoreCase("Dia")) {
			fechaVencimiento.add(Calendar.DATE,apartado.getTiempoVigencia());
		} else {
			if (apartado.getTipoVigencia().equalsIgnoreCase("Mes")) {
				fechaVencimiento.add(Calendar.MONTH,apartado.getTiempoVigencia());
			}
		}
		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("APARTADO: " + String.valueOf(apartado.getNumServicio()),1);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(" ",0);
		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Cédula/R.I.F.: " + (apartado.getCliente().getCodCliente() == "N" ? "V":apartado.getCliente().getCodCliente()),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Nombre: " + apartado.getCliente().getNombreCompleto(),0);
		
		//Se envia el teléfono del cliente	
		String codAreaCliente = "";
		String telfCliente = "";
		if(apartado.getCliente().getCodArea()!=null)
			codAreaCliente = apartado.getCliente().getCodArea();
		if(apartado.getCliente().getNumTelefono()!= null)
			telfCliente = apartado.getCliente().getNumTelefono();
				
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Teléfono: " + codAreaCliente + "-" + telfCliente,0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Emision: " + formatoFecha.format(apartado.getFechaServicio()),"Vence: " + formatoFecha.format(fechaVencimiento.getTime()), columnasImpresoraFiscal),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Monto del Apartado: " + monto.format(apartado.getMontoBase()+apartado.getMontoImpuesto()),0);
	
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(" ",0);

		if (logger.isDebugEnabled()) {
			logger.debug("crearEtiquetasApartadoFiscal(Apartado) - end");
		}
	}
	
	/**
	 * Realiza la impresion del código de barra de la factura de Venta indicada como parametro.
	 * @param ventaActual Venta que se imprimira.
	 */
	
	@SuppressWarnings("unused")
	private static void crearCodigoBarraFiscal(Apartado apartado) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearCodigoBarraFiscal(Apartado) - start");
		}

		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		DecimalFormat entero = new DecimalFormat("000");
		
		String codBarra = entero.format(apartado.getCodTienda()) + df.format(apartado.getFechaServicio()) + apartado.getNumServicio();
		if ((codBarra.length()%2)==0) {
			Sesion.crFiscalPrinterOperations.imprimirCodigoBarra(codBarra);
		} else {
			Sesion.crFiscalPrinterOperations.imprimirCodigoBarra(entero.format(apartado.getCodTienda()) + df.format(apartado.getFechaServicio()) + "0" + apartado.getNumServicio());
		}				

		if (logger.isDebugEnabled()) {
			logger.debug("crearCodigoBarraFiscal(Apartado) - end");
		}
	}
	
	/**
	 * Método imprimirComprobante
	 * 	Realiza la impresion de la factura de Venta indicada como parametro.
	 * @param apartado
	 */
	public static void imprimirComprobante(Apartado apartado) {
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirComprobante(Apartado) - start");
		}

		DecimalFormat decf = new DecimalFormat("#,##0.00");

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
				
		//****Se muestra el vuelto por visor y por pantalla
		// Se muestra el monto del vuelto al cliente por el visor
		double vuelto;
		if(((Abono)apartado.getAbonos().lastElement()).getMontoVuelto()*100 < 1)
			vuelto = 0;
		else
			vuelto = ((Abono)apartado.getAbonos().lastElement()).getMontoVuelto();
		try { CR.crVisor.enviarString("CAMBIO",0,decf.format(vuelto),2); }
		catch (Exception e) {
			logger.error("imprimirComprobante(Apartado)", e);
		}
								
		//Se muestra el mensaje del vuelto al cajero
		PantallaVuelto pantallaVuelto = new PantallaVuelto(vuelto, true);
		MensajesVentanas.centrarVentanaDialogo(pantallaVuelto);
		//***
		
		// Se envía a imprimir el comprobante de apartado tants veces como lo indiquen las variables correspondientes
		for(int i=0; i<Sesion.NRO_COMPROBANTES_APARTADO; i++) {
			if(Sesion.impresoraFiscal) {
				Sesion.crFiscalPrinterOperations.activarSlip();
				try {
					Sesion.crFiscalPrinterOperations.commit();
					MensajesVentanas.aviso("Impresión de Comprobante de Apartado (Copia " + (i+1) + ")\nIntroduzca el documento en la impresora");
					// Cadenas de secciones de la factura
					Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
					crearEncabezadoFiscal(apartado);
					crearDetallesFiscal(apartado);
					crearTotalesFiscal(i+1, apartado);
			//		crearPieDePaginaFiscal(i+1);
					Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
					Sesion.crFiscalPrinterOperations.commit();
					
					// Imprimir condiciones de apartado
					Sesion.crFiscalPrinterOperations.activarSlip(); //Activar bandeja
					Sesion.crFiscalPrinterOperations.commit();
					MensajesVentanas.aviso("Impresión de Condiciones de Apartado (Copia " + (i+1) + ")\nIntroduzca el dorso del Reporte de Apartado en la impresora");
					// Cadenas de secciones de la factura
					Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
					crearEncabezadoFiscalDeCondiciones(apartado);
					crearDorsoApartado();
					crearPieDePaginaFiscal(apartado);
					Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
				
					//Se imprime el código de barra
				//	crearCodigoBarraFiscal(apartado);

					Sesion.crFiscalPrinterOperations.commit();
				} catch (PrinterNotConnectedException e) {
					logger.error("imprimirComprobante(Apartado)", e);
				}
			} else {
				Sesion.crPrinterOperations.initializarPrinter();
				Sesion.crPrinterOperations.activarDocumentoNomal();
				//Sesion.crPrinterOperations.abrirPuertoImpresora();
				try {
					Sesion.crPrinterOperations.commit();
					MensajesVentanas.aviso("Impresión de Comprobante de Apartado (Copia " + (i+1) + ")\nIntroduzca el documento en la impresora");
					// Cadenas de secciones de la factura
					Sesion.crPrinterOperations.limpiarBuffer();
					Sesion.crPrinterOperations.initializarPrinter();
					Sesion.crPrinterOperations.activarDocumentoNomal();
			
					crearEncabezado(apartado);
					crearDetalles(apartado);
					crearTotales(apartado);
					crearPieDePagina(i+1);
					Sesion.crPrinterOperations.alinear(1);
					Sesion.crPrinterOperations.estiloPresentacionCodigoBarras(2,1);
					SimpleDateFormat df =new SimpleDateFormat("yyyyMMdd");
					DecimalFormat entero = new DecimalFormat("000");
					String codBarra = entero.format(apartado.getCodTienda()) + df.format(apartado.getFechaServicio()) + apartado.getNumServicio();
					if ((codBarra.length()%2)==0) {
						Sesion.crPrinterOperations.imprimirCodigoDeBarras(codBarra+"\n",7);
					} else {
						Sesion.crPrinterOperations.imprimirCodigoDeBarras(
							entero.format(apartado.getCodTienda()) + df.format(apartado.getFechaServicio()) + "0" + apartado.getNumServicio()+"\n",7);
					}
					Sesion.crPrinterOperations.cortarPapel();
					
					Sesion.crPrinterOperations.commit();
				} catch (PrinterNotConnectedException e) {
					logger.error("imprimirComprobante(Apartado)", e);
				}
				//Sesion.crPrinterOperations.cerrarPuertoImpresora();
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("imprimirComprobante(Apartado) - end");
		}
	}
	/**
	 * Método imprimirEtiquetas
	 * 	Realiza la impresion de las etiquetas para identificar los productos de un apartado específico
	 * @param apartado
	 */
	public static void imprimirEtiquetas(Apartado apartado, int nroEtiquetas) {
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirEtiquetas(Apartado, int) - start");
		}
		
		if(Sesion.impresoraFiscal) {
			Sesion.crFiscalPrinterOperations.activarSlip();
			try {
				Sesion.crFiscalPrinterOperations.commit();
				MensajesVentanas.aviso("Impresión de Etiquetas del Apartado\nIntroduzca el documento en la impresora");
				// Cadenas de secciones de la factura
				Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
				
				// Se envía a imprimir tantas veces como se indique
				for (int i=0; i<nroEtiquetas; i++) {
					crearEtiquetasApartadoFiscal(apartado);
				}

				Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
				Sesion.crFiscalPrinterOperations.commit();
			} catch (PrinterNotConnectedException e) {
				logger.error("imprimirEtiquetas(Apartado, int)", e);
			}
		} else {
			Sesion.crPrinterOperations.initializarPrinter();
			Sesion.crPrinterOperations.activarDocumentoNomal();
			//Sesion.crPrinterOperations.abrirPuertoImpresora();
			try {
				Sesion.crPrinterOperations.commit();
				MensajesVentanas.aviso("Impresión de Etiquetas del Apartado\nIntroduzca el documento en la impresora");
				Sesion.crPrinterOperations.limpiarBuffer();
				
				// Se envía a imprimir tantas veces como se indique
				for (int i=0; i<nroEtiquetas; i++) {
					Sesion.crPrinterOperations.initializarPrinter();
					Sesion.crPrinterOperations.activarDocumentoNomal();
					crearEtiquetasApartado(apartado);
				}

				Sesion.crPrinterOperations.cortarPapel();
				
				Sesion.crPrinterOperations.commit();
			} catch (PrinterNotConnectedException e) {
				logger.error("imprimirEtiquetas(Apartado, int)", e);
			}
			
			//Sesion.crPrinterOperations.cerrarPuertoImpresora();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("imprimirEtiquetas(Apartado, int) - end");
		}
	}
}