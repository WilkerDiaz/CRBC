/**
 * =============================================================================
 * Proyecto   : CajaRegistradora
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
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarventa.DetalleTransaccion;
import com.becoblohm.cr.manejarventa.Venta;
import com.becoblohm.cr.utiles.Control;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.crprinterdriver.PrinterNotConnectedException;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;

/** 
 * Descripción: 
 * 		Clase que maneja los perifericos del proyecto de CR. En principio maneja
 * únicamente la impresora. Restaría el manejo de Escaners, Visores, Teclados, etc.
 */
public class NotaDeDespacho extends Reporte{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(NotaDeDespacho.class);
	
	// Variables necesarias para la impresion
	private static int columnasImpresora = 47;
	private static int columnasImpresoraFiscal = 40;
	private static float artDespachados;
	
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
	private static void crearEncabezado(Venta ventaActual, int numServ) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezado(Venta, int) - start");
		}

		SimpleDateFormat fechaTrans = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat horaTrans = new SimpleDateFormat("HH:mm:ss");
		DecimalFormat enteroConCeros = new DecimalFormat("000");
		DecimalFormat numServicio = new DecimalFormat("00000000000");
		
		Sesion.crPrinterOperations.activarFuenteImpresionNegrita();
		Sesion.crPrinterOperations.alinear(1);
		Sesion.crPrinterOperations.enviarString(Sesion.getTienda().getRazonSocial()+"\n");
		
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
		
		try {
			if(InitCR.preferenciasCR.getConfigStringForParameter("sistema","estiloFactura").equals("CR")){
				Sesion.crPrinterOperations.enviarString(convertirCadena("CEDULA JURIDICA: " + Sesion.getTienda().getRif()+"\n"));
			}
			else{
				if ((Sesion.getTienda().getNit()!=null)&&(!Sesion.getTienda().getNit().equalsIgnoreCase("")))
					Sesion.crPrinterOperations.enviarString(convertirCadena("RIF:" + Sesion.getTienda().getRif()  + "   NIT:" + Sesion.getTienda().getNit()+"\n"));
				else
					Sesion.crPrinterOperations.enviarString(convertirCadena("RIF:" + Sesion.getTienda().getRif()+"\n"));
			}
		} catch (NoSuchNodeException e) {
			logger.error("crearEncabezado(Venta, int)", e);
		} catch (UnidentifiedPreferenceException e) {
			logger.error("crearEncabezado(Venta, int)", e);
		}		
		
		//Se imprime el teléfono de la tienda
		String codArea = "";
		String telfTienda = "";
		if(Sesion.getTienda().getCodArea()!=null)
			codArea = Sesion.getTienda().getCodArea();
		if(Sesion.getTienda().getNumTelefono()!= null)
			telfTienda = Sesion.getTienda().getNumTelefono();
		Sesion.crPrinterOperations.enviarString("Teléfono: " + codArea + "-" + telfTienda +"\n");
		
		
		Sesion.crPrinterOperations.alinear(0);
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");
		
		// Si existe Cliente en la Venta
		if (ventaActual.getCliente().getCodCliente()!=null) {
			// Creamos las lineas con los datos del Cliente
			String cliente = "Cliente: ";
			Vector<String> lineasNombre = dividirEnLineas(columnasImpresora - cliente.length(),ventaActual.getCliente().getNombreCompleto());
			String RIF = null;
			try {
				if(InitCR.preferenciasCR.getConfigStringForParameter("sistema","estiloFactura").equals("CR")){
					RIF = "IDENTIFICACION: " + ventaActual.getCliente().getCodCliente();
				}
				else{
					RIF = "CI/RIF: " + ventaActual.getCliente().getCodCliente();
				}
			} catch (NoSuchNodeException e) {
				logger.error("crearEncabezado(Venta, int)", e);
			} catch (UnidentifiedPreferenceException e) {
				logger.error("crearEncabezado(Venta, int)", e);
			}
			
			String NIT = ((ventaActual.getCliente().getNit()!=null)&&(!ventaActual.getCliente().getNit().equalsIgnoreCase("")))
						? "NIT:" + ventaActual.getCliente().getNit()
						: null;
						
			//Se envia el nombre del cliente
			Sesion.crPrinterOperations.enviarString(cliente + lineasNombre.elementAt(0)+"\n");
			for (int i=1; i<lineasNombre.size(); i++) {
				String lineaActual = "";
				for (int j=0;j<cliente.length();j++)
					lineaActual += " ";
				Sesion.crPrinterOperations.enviarString(lineaActual + (String)lineasNombre.elementAt(i)+"\n");
			}
			
			//Se envia el NIT y el RIF/CI del cliente
			if (NIT != null)
				Sesion.crPrinterOperations.enviarString(RIF + "  " + NIT+"\n");
			else
				Sesion.crPrinterOperations.enviarString(RIF+"\n");
			
			//Se imprime el teléfono del Cliente
			String codAreaCliente = "";
			String telfCliente = "";
			if(ventaActual.getCliente().getCodArea()!=null)
				codAreaCliente = ventaActual.getCliente().getCodArea();
			if(ventaActual.getCliente().getNumTelefono()!= null)
				telfCliente = ventaActual.getCliente().getNumTelefono();	
			Sesion.crPrinterOperations.enviarString("Teléfono: " + codAreaCliente + "-" + telfCliente +"\n");	
			
			Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");
		}
		
		Sesion.crPrinterOperations.enviarString(justificar("Cajero:" + Sesion.getUsuarioActivo().getNumFicha(), "POS: " + enteroConCeros.format(Sesion.getCaja().getNumero()), columnasImpresora)+"\n");
		
		String hora = horaTrans.format(ventaActual.getHoraFin());
		String fecha = fechaTrans.format(ventaActual.getFechaTrans());
		Sesion.crPrinterOperations.enviarString(justificar(fecha, hora, columnasImpresora)+"\n");
	
		Sesion.crPrinterOperations.enviarString(justificar("Tr.Asociada: " + ventaActual.getNumTransaccion(), "Despacho Nro: " + numServicio.format(numServ), columnasImpresora)+"\n");
		
		Sesion.crPrinterOperations.activarFuenteImpresionNegrita();
		Sesion.crPrinterOperations.alinear(1);
		Sesion.crPrinterOperations.enviarString("CLIENTE RETIRA\n");
		Sesion.crPrinterOperations.initializarPrinter();
		Sesion.crPrinterOperations.activarDocumentoNomal();
		Sesion.crPrinterOperations.alinear(0);

		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezado(Venta, int) - end");
		}
	}

	/**
	 * Crea el detalle de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generara el detalle.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	private static void crearDetalles(Venta ventaActual) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalles(Venta) - start");
		}

		DecimalFormat precio = new DecimalFormat("#,##0.00");
		DecimalFormat cantidad = new  DecimalFormat("#0.00");
		DetalleTransaccion detalleActual;
		artDespachados = 0;
		
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");
		// Colocamos el tipo de Moneda Base Justificado a la Derecha
		Sesion.crPrinterOperations.enviarString(alinearDerecha(Sesion.getTienda().getMonedaBase() != null ? Sesion.getTienda().getMonedaBase() : "", columnasImpresora)+"\n");		

		// Para cada detalle de la venta
		for (int i=0; i<ventaActual.getDetallesTransaccion().size(); i++) {
			detalleActual = (DetalleTransaccion)ventaActual.getDetallesTransaccion().elementAt(i);
			
			if (((DetalleTransaccion)ventaActual.getDetallesTransaccion().elementAt(i)).getTipoEntrega().equals(Sesion.ENTREGA_CLIENTE_RETIRA)) {
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
				
				Sesion.crPrinterOperations.enviarString(justificar(alinearDerecha(codProd,longitudCodigo) + " " 
								+ detalleActual.getProducto().getDescripcionCorta(), "1 " + detalleActual.getProducto().getAbreviadoUnidadVenta(),columnasImpresora)+"\n");
				if (!detalleActual.getCondicionVenta().equals(Sesion.condicionNormal)) {
					// Si se ha aplicado alguna rebaja (Promocion, Empaque, Cambio de Precio) se especifica
					Sesion.crPrinterOperations.enviarString(" PR:" + precio.format(detalleActual.getProducto().getPrecioRegular())
									+ " 0" + detalleActual.getCondicionVenta() + ":" + precio.format(detalleActual.getPrecioFinal())+"\n");
				}

				Sesion.crPrinterOperations.enviarString(alinearDerecha(cantidad.format(detalleActual.getCantidad()),9)
								+ alinearDerecha(precio.format(detalleActual.getPrecioFinal()), 19)
								+ alinearDerecha(precio.format(detalleActual.getCantidad() * detalleActual.getPrecioFinal()), 19)
								+ "\n");
				artDespachados += detalleActual.getCantidad();
			}
		}
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");

		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalles(Venta) - end");
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

		// Linea del SubTotal
		DecimalFormat cant = new DecimalFormat("#0.00");
		Sesion.crPrinterOperations.enviarString("   Total Art.: " + cant.format(artDespachados) + "\n");
		
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");
		Sesion.crPrinterOperations.enviarString(centrar("DOCUMENTO NO FISCAL\n", columnasImpresora));
		Sesion.crPrinterOperations.enviarString(centrar("COPIA " + numDocumento + " \n", columnasImpresora));
		Sesion.crPrinterOperations.enviarString(crearLineaDeDivision(columnasImpresora)+"\n");

		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePagina(int) - end");
		}
	}
	
	//************ ME´TODOS PARA IMPRESIORA FISCAL ***********************************************
	//********************************************************************************************
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
	private static void crearEncabezadoFiscal(Venta ventaActual, int numServ) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal(Venta, int) - start");
		}

		DecimalFormat enteroConCeros = new DecimalFormat("000");
		DecimalFormat numServicio = new DecimalFormat("00000000000");
		
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
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar((String)nuevasLineasDireccion.elementAt(i),columnasImpresoraFiscal), 0);
		}
		
		if ((Sesion.getTienda().getNit()!=null)&&(!Sesion.getTienda().getNit().equalsIgnoreCase("")))
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar("RIF:" + Sesion.getTienda().getRif()  + "   NIT:" + Sesion.getTienda().getNit(),columnasImpresoraFiscal), 0);
		else
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar("RIF:" + Sesion.getTienda().getRif(), columnasImpresoraFiscal),0);
		
		//Se imprime el teléfono de la tienda
		String codArea = "";
		String telfTienda = "";
		if(Sesion.getTienda().getCodArea()!=null)
			codArea = Sesion.getTienda().getCodArea();
		if(Sesion.getTienda().getNumTelefono()!= null)
			telfTienda = Sesion.getTienda().getNumTelefono();		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar("Teléfono: " + codArea + "-" + telfTienda, columnasImpresoraFiscal), 0);
		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal),0);
		
		// Si existe Cliente en la Venta
		if (ventaActual.getCliente().getCodCliente()!=null) {
			// Creamos las lineas con los datos del Cliente
			String cliente = "Cliente: ";
			Vector<String> lineasNombre = dividirEnLineas(columnasImpresoraFiscal - cliente.length(),ventaActual.getCliente().getNombreCompleto());
			String RIF = "CI/RIF: " + ventaActual.getCliente().getCodCliente();
			String NIT = ((ventaActual.getCliente().getNit()!=null)&&(!ventaActual.getCliente().getNit().equalsIgnoreCase("")))
						? "NIT:" + ventaActual.getCliente().getNit()
						: null;
						
			//Se envia el nombre del cliente
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(cliente + lineasNombre.elementAt(0),0);
			for (int i=1; i<lineasNombre.size(); i++) {
				String lineaActual = "";
				for (int j=0;j<cliente.length();j++)
					lineaActual += " ";
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(lineaActual + (String)lineasNombre.elementAt(i),0);
			}
			
			//Se envia el NIT y el RIF/CI del cliente
			if (NIT != null)
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(RIF + "  " + NIT,0);
			else
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(RIF,0);
			
			//Se envío el teléfono del cliente	
			String codAreaCliente = "";
			String telfCliente = "";
			if(ventaActual.getCliente().getCodArea()!=null)
				codAreaCliente = ventaActual.getCliente().getCodArea();
			if(ventaActual.getCliente().getNumTelefono()!= null)
				telfCliente = ventaActual.getCliente().getNumTelefono();
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Teléfono: " + codAreaCliente + "-" + telfCliente,0);

			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal),0);
		}
		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Cajero:" + Sesion.getUsuarioActivo().getNumFicha(), "POS: " + enteroConCeros.format(Sesion.getCaja().getNumero()), columnasImpresoraFiscal),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Tr.Asociada: " + ventaActual.getNumTransaccion(), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Despacho Nro: " + numServicio.format(numServ),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("CLIENTE RETIRA",1);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal),0);	

		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal(Venta, int) - end");
		}
	}

	/**
	 * Crea el detalle de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generara el detalle.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	private static void crearDetallesFiscal(Venta ventaActual) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearDetallesFiscal(Venta) - start");
		}

		DecimalFormat precio = new DecimalFormat("#,##0.00");
		DecimalFormat cantidad = new  DecimalFormat("#0.00");
		DetalleTransaccion detalleActual;
		artDespachados = 0;		

		// Colocamos el tipo de Moneda Base Justificado a la Derecha
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha(Sesion.getTienda().getMonedaBase() != null ? Sesion.getTienda().getMonedaBase() : "", columnasImpresoraFiscal),0);
			
		// Para cada detalle de la venta
		for (int i=0; i<ventaActual.getDetallesTransaccion().size(); i++) {
			detalleActual = (DetalleTransaccion)ventaActual.getDetallesTransaccion().elementAt(i);
			
			if (((DetalleTransaccion)ventaActual.getDetallesTransaccion().elementAt(i)).getTipoEntrega().equals(Sesion.ENTREGA_CLIENTE_RETIRA)) {
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
				
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar(alinearDerecha(codProd,longitudCodigo) + " " 
													+ detalleActual.getProducto().getDescripcionCorta(), "1 " + detalleActual.getProducto().getAbreviadoUnidadVenta(),columnasImpresoraFiscal),0);
				if (!detalleActual.getCondicionVenta().equals(Sesion.condicionNormal)) {
					// Si se ha aplicado alguna rebaja (Promocion, Empaque, Cambio de Precio) se especifica
					Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(" PR:" + precio.format(detalleActual.getProducto().getPrecioRegular())
									+ " " + detalleActual.getCondicionVenta() + ":" + precio.format(detalleActual.getPrecioFinal()),0);
				}

				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha(cantidad.format(detalleActual.getCantidad()),9)
								+ alinearDerecha(precio.format(detalleActual.getPrecioFinal()), 15)
								+ alinearDerecha(precio.format(detalleActual.getCantidad() * detalleActual.getPrecioFinal()), 16),0);
				artDespachados += detalleActual.getCantidad();
			}
		}
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal),0);

		if (logger.isDebugEnabled()) {
			logger.debug("crearDetallesFiscal(Venta) - end");
		}
	}
	
	/**
	 * Crea el pie de pagina de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generara el pie de Pagina.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	private static void crearPieDePaginaFiscal(int numDocumento) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePaginaFiscal(int) - start");
		}

		// Linea del SubTotal
		DecimalFormat cant = new DecimalFormat("#0.00");
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("   Total Art.: " + cant.format(artDespachados),0);
		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar("COPIA " + numDocumento, columnasImpresoraFiscal),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal),0);

		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePaginaFiscal(int) - end");
		}
	}
	
	/**
	 * Realiza la impresion de la factura de Venta indicada como parametro.
	 * @param ventaActual Venta que se imprimira.
	 */
	public static void imprimirComprobante(Venta ventaActual, int numServ) {
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirComprobante(Venta, int) - start");
		}
		
		//Se envía a imprimir la nota de despacho tantas veces como lo indiquen las variables correspondientes
		for (int i=0; i < Sesion.NRO_NOTAS_DESPACHO; i++) {
			if(Sesion.impresoraFiscal) {
				Sesion.crFiscalPrinterOperations.activarSlip();
				try {
					Sesion.crFiscalPrinterOperations.commit();
					MensajesVentanas.aviso("Impresión de Nota de Despacho (Copia " + (i+1) + ")\nIntroduzca el documento en la impresora");
					// Cadenas de secciones de la factura
					Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
					//Sesion.crFiscalPrinterOperations.activarSlip();
					crearEncabezadoFiscal(ventaActual, numServ);
					crearDetallesFiscal(ventaActual);
					crearPieDePaginaFiscal(i+1);
					Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
					Sesion.crFiscalPrinterOperations.commit();
				} catch (PrinterNotConnectedException e) {
					logger.error("imprimirComprobante(Venta, int)", e);
				}
			} else {
				Sesion.crPrinterOperations.initializarPrinter();
				Sesion.crPrinterOperations.activarDocumentoNomal();
				//Sesion.crPrinterOperations.abrirPuertoImpresora();
				try {
					Sesion.crPrinterOperations.commit();
					MensajesVentanas.aviso("Impresión de Nota de Despacho (Copia " + (i+1) + ")\nIntroduzca el documento en la impresora");
					// Cadenas de secciones de la factura
					Sesion.crPrinterOperations.limpiarBuffer();
					Sesion.crPrinterOperations.initializarPrinter();
					Sesion.crPrinterOperations.activarDocumentoNomal();
					crearEncabezado(ventaActual, numServ);
					crearDetalles(ventaActual);
					crearPieDePagina(i+1);
					Sesion.crPrinterOperations.cortarPapel();
					Sesion.crPrinterOperations.commit();
				} catch (PrinterNotConnectedException e) {
					logger.error("imprimirComprobante(Venta, int)", e);
				}
				//Sesion.crPrinterOperations.cerrarPuertoImpresora();
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("imprimirComprobante(Venta, int) - end");
		}
	}
}
