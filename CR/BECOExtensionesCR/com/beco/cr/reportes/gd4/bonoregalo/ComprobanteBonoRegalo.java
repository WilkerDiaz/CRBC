/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.impresion
 * Programa   : ComprobanteBonoRegalo.java
 * Creado por : jgraterol
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
package com.beco.cr.reportes.gd4.bonoregalo;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.beco.cr.reportes.gd4.Reporte;
import com.becoblohm.cr.CR;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.gui.PantallaVuelto;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstadoVenta;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarbr.DetalleTransaccionBR;
import com.becoblohm.cr.manejarbr.VentaBR;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.crprinterdriver.PrinterNotConnectedException;

/** 
 * Descripción: 
 * 		Clase que maneja los perifericos del proyecto de CR. En principio maneja
 * únicamente la impresora. Restaría el manejo de Escaners, Visores, Teclados, etc.
 */
public class ComprobanteBonoRegalo extends Reporte {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ComprobanteBonoRegalo.class);
	
	// Variables necesarias para la impresion
	//private static int columnasImpresoraFiscal = 38;
	
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
	private static void crearEncabezado(VentaBR ventaBR, boolean duplicado, String titulo) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezado(Apartado) - start");
		}

		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
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
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar(titulo,columnasImpresoraFiscal),1);
		if(duplicado)
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar("DUPLICADO",columnasImpresoraFiscal),1);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal)+"",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("FECHA: "+formatoFecha.format(Sesion.getFechaSistema()),"HORA: "+Sesion.getHoraSistema(), columnasImpresoraFiscal),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Transaccion N°: " + ventaBR.getNumTransaccion(), "Fecha: " + formatoFecha.format(ventaBR.getFechaTrans()),columnasImpresoraFiscal)+"",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Caja: " + ventaBR.getNumCajaFinaliza(), " ",columnasImpresoraFiscal)+"",0);
		
		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal)+"",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Cédula ó R.I.F.: " + ventaBR.getCliente().getCodCliente().replace('N','V')+"",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Cliente: " + ventaBR.getCliente().getNombreCompleto()+"",0);
		
		// Imprimimos la direccion del cliente
		String inicioLinea = "Dirección: ";
		Vector<String> direccion = dividirEnLineas(columnasImpresoraFiscal-inicioLinea.length(),ventaBR.getCliente().getDireccion());
		for (int i=0; i<direccion.size();i++) {
			if (i==0)
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(inicioLinea + direccion.elementAt(i)+"",0);
			else
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(inicioLinea.length(),' ')+direccion.elementAt(i)+"",0);
		}
		//Se imprime el teléfono del cliente
		String codAreaCliente = "";
		String telfCliente = "";
		if(ventaBR.getCliente().getCodArea()!=null)
			codAreaCliente = ventaBR.getCliente().getCodArea();
		if(ventaBR.getCliente().getNumTelefono()!= null)
			telfCliente = ventaBR.getCliente().getNumTelefono();
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Teléfonos: " + codAreaCliente + "-" + telfCliente +"",0);

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
	* Se elimino la variable 'cantidad' po no usarse
	* Fecha: agosto 2011
	*/
	private static void crearDetalles(VentaBR ventaBR) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalles(Apartado) - start");
		}

		DecimalFormat precio = new DecimalFormat("#,##0.00");
		DetalleTransaccionBR detalleActual;
		
		// Para cada detalle de la venta
		for (int i=0; i<ventaBR.getDetallesTransaccionBR().size(); i++) {
			detalleActual = (DetalleTransaccionBR)ventaBR.getDetallesTransaccionBR().elementAt(i);

			
				
			//Se chequea la longitud del código del producto para que salga en el reporte igual que como en la interfaz
			String codProd = "Tarjeta N. "+detalleActual.getCodTarjeta().substring(14,18);
						
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar(codProd, precio.format(detalleActual.getMonto()),columnasImpresoraFiscal-1)+"",0);
			
		}
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal)+"",0);

		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalles(Apartado) - end");
		}
	}
	
	/**
	 * Crea el detalle de la factura para el vendedor corporativo.
	 * @param ventaActual Venta a partir de la cual se generara el detalle.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	private static void crearDetalleCorporativo(VentaBR ventaBR) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalles(Apartado) - start");
		}

		DecimalFormat precio = new DecimalFormat("#,##0.00");
		DecimalFormat cantidad = new  DecimalFormat("#0");

		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Vendedor Corporativo: " + ventaBR.getVendedor().getNumFicha(),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(ventaBR.getVendedor().getNombreCompleto().toUpperCase(),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Nro. Tarjetas: " + cantidad.format(ventaBR.getLineasFacturacion()),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Monto        : " + precio.format(ventaBR.getMontoBase()),0);

		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal)+"",0);

		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalles(Apartado) - end");
		}
	}

	/**
	 * Crea las lineas de los totales de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generaran los totales.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	private static void crearTotales(VentaBR venta) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearTotales(Apartado) - start");
		}

		DecimalFormat precio = new DecimalFormat("#,##0.00");
		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha(justificar("SUB TOTAL     : ",precio.format(venta.getMontoBase()),35),columnasImpresoraFiscal)+"",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha(justificar("IMPUESTO      : ",precio.format(venta.getMontoImpuesto()),35),columnasImpresoraFiscal)+"",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha("--------------------",columnasImpresoraFiscal)+"",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha(justificar("TOTAL         : ",precio.format(venta.consultarMontoTrans()),35),columnasImpresoraFiscal)+"",0);
		//Ciclo para formas de pago
		for(int i=0;i<venta.getPagos().size();i++){
			Pago pago = (Pago)venta.getPagos().elementAt(i);
			String nombrePago = pago.getFormaPago().getNombre();
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha(justificar(nombrePago.length()>=12? nombrePago.substring(0,12):nombrePago+"  : ",precio.format(pago.getMonto()),35),columnasImpresoraFiscal)+"",0);
		}
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar("Su cambio "+venta.getMontoVuelto(), columnasImpresoraFiscal), 0);
		if (logger.isDebugEnabled()) {
			logger.debug("crearTotales(Apartado) - end");
		}
	}
	
	/**
	 * Crea el pie de pagina de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generara el pie de Pagina.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	private static void crearPieDePagina(VentaBR venta) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePagina(int) - start");
		}


		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal)+"",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("CONDICIONES:",1);
		//Ciclo sobre las condiciones
		for(int i=0;i<venta.getCondicionesServicio().size();i++){
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal((String)venta.getCondicionesServicio().elementAt(i),0);
		}
		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("   ",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("   ",0);

		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePagina(int) - end");
		}
	}


	/**
	 * Realiza la impresion del código de barra de la factura de Venta indicada como parametro.
	 * @param ventaActual Venta que se imprimira.
	 */
	private static void crearCodigoBarraFiscal(VentaBR venta) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearCodigoBarraFiscal(Apartado) - start");
		}

		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		DecimalFormat entero = new DecimalFormat("000");
		
		String codBarra = entero.format(venta.getCodTienda()) + df.format(venta.getFechaTrans()) + entero.format(venta.getNumCajaFinaliza()) + venta.getNumTransaccion();
		if ((codBarra.length()%2)==0) {
			Sesion.crFiscalPrinterOperations.imprimirCodigoBarra(codBarra);
		} else {
			Sesion.crFiscalPrinterOperations.imprimirCodigoBarra(entero.format(venta.getCodTienda()) + df.format(venta.getFechaTrans()) + entero.format(venta.getNumCajaFinaliza()) + "0" + venta.getNumTransaccion());
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
	public static void imprimirComprobante(VentaBR venta, boolean duplicado, boolean mostrarVuelto, boolean abrirGaveta) {
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirComprobante(Apartado) - start");
		}

		DecimalFormat decf = new DecimalFormat("#,##0.00");

		//********
		//Se verifica si la gaveta se abre por impresora o por el visor. Si es por impresora debemos abrir
		//primero la gaveta antes de enviar a imprimir porque si no, se queda la gaveta cerrada hasta que termina la impresion.
		
		if (abrirGaveta) {
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
		if(venta.getMontoVuelto()*100 < 1)
			vuelto = 0;
		else
			vuelto = venta.getMontoVuelto();
		try { CR.crVisor.enviarString("CAMBIO",0,decf.format(vuelto),2); }
		catch (Exception e) {
			logger.error("imprimirComprobante(Apartado)", e);
		}
					
		if (mostrarVuelto) {
			//Se muestra el mensaje del vuelto al cajero
			PantallaVuelto pantallaVuelto = new PantallaVuelto(vuelto, abrirGaveta);
			MensajesVentanas.centrarVentanaDialogo(pantallaVuelto);
		}
		//***
		
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
					ComprobanteBonoRegalo.crearEncabezado(venta, duplicado, "COMPRA / RECARGA DE BONOS REGALO");
					ComprobanteBonoRegalo.crearDetalles(venta);
					ComprobanteBonoRegalo.crearTotales(venta);
					ComprobanteBonoRegalo.crearPieDePagina(venta);
					Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
					MaquinaDeEstadoVenta.setDocumentoNoFiscalPorImprimir(true);
					Sesion.crFiscalPrinterOperations.commit();	
					do{
						if(MaquinaDeEstadoVenta.errorAtencionUsuario){
							MensajesVentanas.aviso("Problema al imprimir el documento.");
						}
					} while(MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir());
					crearCodigoBarraFiscal(venta);
					Sesion.crFiscalPrinterOperations.cortarPapel();
					try {
						Sesion.crFiscalPrinterOperations.commit();
					} catch (PrinterNotConnectedException e) {
						logger.error("imprimirFactura(Venta, boolean, boolean)", e);
					}

					// Impresión de comprobante de Ventas Corporativas en caso que aplique
					if (venta.getVendedor() != null) {
						do{
							if(MaquinaDeEstadoVenta.errorAtencionUsuario){
								MensajesVentanas.aviso("Problema al imprimir el documento.");
							}
						} while(MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir());
						
						Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
						ComprobanteBonoRegalo.crearEncabezado(venta, duplicado, "VENTA CORPORATIVA");
						ComprobanteBonoRegalo.crearDetalleCorporativo(venta);
						ComprobanteBonoRegalo.crearTotales(venta);
						Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
						
						MaquinaDeEstadoVenta.setDocumentoNoFiscalPorImprimir(true);
						Sesion.crFiscalPrinterOperations.commit();	
						do{
							if(MaquinaDeEstadoVenta.errorAtencionUsuario){
								MensajesVentanas.aviso("Problema al imprimir el documento.");
							}
						} while(MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir());
						
						crearCodigoBarraFiscal(venta);
						Sesion.crFiscalPrinterOperations.cortarPapel();
						try {
							Sesion.crFiscalPrinterOperations.commit();
						} catch (PrinterNotConnectedException e) {
							logger.error("imprimirFactura(Venta, boolean, boolean)", e);
						}
					}
					
			} catch (PrinterNotConnectedException e) {
				e.printStackTrace();
				logger.error("imprimirComprobante(Apartado)", e);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("imprimirComprobante(Apartado) - end");
		}
	}
	
	
}