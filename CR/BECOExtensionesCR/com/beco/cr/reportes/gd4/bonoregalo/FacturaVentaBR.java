/**
 * =============================================================================
 * Proyecto   : CajaRegistradora
 * Paquete    : com.becoblohm.cr.reportes.bonoregalo
 * Programa   : FacturaVentaBR.java
 * Creado por : jgraterol
 * Creado en  : 04-nov-03 11:56:10
 *
 * (c) CENTROBECO, C.A.
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
import org.apache.log4j.Logger;

import com.beco.cr.reportes.gd4.Reporte;
import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstadoVenta;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarbr.DetalleTransaccionBR;
import com.becoblohm.cr.manejarbr.VentaBR;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.utiles.MathUtil;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.crprinterdriver.CRFiscalPrinterOperations;
import com.epa.crprinterdriver.PrinterNotConnectedException;

/** 
 * Descripción: 
 * 		Clase que maneja los perifericos del proyecto de CR. En principio maneja
 * únicamente la impresora. Restaría el manejo de Escaners, Visores, Teclados, etc.
 */
public class FacturaVentaBR extends Reporte {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(FacturaVentaBR.class);
	//Variables necesarias para la impresion
	//private static int columnasImpresoraFiscal = 38;
	
	//************** Métodos para impresora Fiscal ********************
	//*****************************************************************
	/**
	 * Realiza la impresion del encabezado de la factura de Venta indicada como parametro.
	 * @param ventaActual Venta que se imprimira.
	 */
	private static void crearEncabezadoFiscal(VentaBR ventaActual) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal(Venta) - start");
		}

		DecimalFormat enteroConCeros = new DecimalFormat("000");
		DecimalFormat numFactura = new DecimalFormat("00000000000");
		String cajero = "Cajero:" + Sesion.getUsuarioActivo().getNumFicha();
		String caja = "POS:" + enteroConCeros.format(Sesion.getCaja().getNumero());
		String transaccion = "Tr:" + numFactura.format(ventaActual.getNumTransaccion());
				
		String lineaIzquierda = cajero +  centrar(transaccion,16);
				
		Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(justificar(lineaIzquierda, caja, columnasImpresoraFiscal));
		Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(crearLineaDeDivision(columnasImpresoraFiscal));

		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal(Venta) - end");
		}
	}
	
	/**
	 * Realiza la impresion de los detallles de la factura de Venta indicada como parametro.
	 * @param ventaActual Venta que se imprimira.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se eliminó la variable 'precio', que no se usaba
	* Fecha: agosto 2011
	*/
	private static void crearDetallesFiscal(VentaBR ventaActual) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearDetallesFiscal(Venta, Vector) - start");
		}
		
		// Para cada detalle de la venta
		DetalleTransaccionBR detalleActual;
		for (int i=0; i<ventaActual.getDetallesTransaccionBR().size(); i++) {
			detalleActual = (DetalleTransaccionBR)ventaActual.getDetallesTransaccionBR().elementAt(i);
				
			Sesion.crFiscalPrinterOperations.imprimirItemVenta("Tarjeta N. "+detalleActual.getCodTarjeta().substring(14,18), 1, detalleActual.getMonto(), 0);
					
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("crearDetallesFiscal(Venta, Vector) - end");
		}
	}
	
	/**
	 * Realiza la impresion de los Totales de la factura de Venta indicada como parametro.
	 * @param ventaActual Venta que se imprimira.
	 */
	private static void crearTotalesFiscal(VentaBR ventaActual) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearTotalesFiscal(Venta) - start");
		}

		Pago pagoActual;
		double montoAcumulado =0;
		for (int i=0; i<ventaActual.getPagos().size(); i++) {
			pagoActual = (Pago)ventaActual.getPagos().elementAt(i);
			if(i==ventaActual.getPagos().size()-1 && pagoActual.getMonto()<MathUtil.roundDouble(ventaActual.consultarMontoTrans()-montoAcumulado))
				pagoActual.setMonto(MathUtil.roundDouble(ventaActual.consultarMontoTrans()-montoAcumulado));
			else
				montoAcumulado+=pagoActual.getMonto();
			
			// Forma de Pago Tipo 17 la pasamos como Tipo 1 igualmente para que reconozca la Impresora Fiscal
			switch(pagoActual.getFormaPago().getTipo()){
			case 17:
				Sesion.crFiscalPrinterOperations.realizarPago(pagoActual.getFormaPago().getNombre(), pagoActual.getMonto(), 1, 0);
				break;
			case 20:
				Sesion.crFiscalPrinterOperations.realizarPago("Obsequio (OB)", pagoActual.getMonto(), 6, 0);
				break;
			default:
				Sesion.crFiscalPrinterOperations.realizarPago(pagoActual.getFormaPago().getNombre(), pagoActual.getMonto(),pagoActual.getFormaPago().getTipo(), 0);
				break;
			}
		}
	
		if (logger.isDebugEnabled()) {
			logger.debug("crearTotalesFiscal(Venta) - end");
		}
	}
	
	/**
	 * Crea el pie de pagina de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generara el pie de Pagina.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	private static void crearPieDePaginaFiscal(VentaBR venta) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePagina(int) - start");
		}


		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal)+"",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("VENTA NO SUJETA AL COBRO DE IVA, SEGUN",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("ART. 16, NUMERAL 2 DE LA LEY DE IVA",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("CONDICIONES:",1);
		//Ciclo sobre las condiciones
		for(int i=0;i<venta.getCondicionesServicio().size();i++){
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal((String)venta.getCondicionesServicio().elementAt(i),0);
		}
		

		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePagina(int) - end");
		}
	}
	
	/**
	 * Realiza la impresion del código de barra de la factura de Venta indicada como parametro.
	 * @param ventaActual Venta que se imprimira.
	 */
	private static void crearCodigoBarraFiscal(VentaBR ventaActual) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearCodigoBarraFiscal(Venta) - start");
		}

		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		DecimalFormat entero = new DecimalFormat("000");
		String codBarra = entero.format(ventaActual.getCodTienda())+df.format(ventaActual.getFechaTrans())+
						entero.format(ventaActual.getNumCajaFinaliza())+ventaActual.getNumTransaccion();
		if ((codBarra.length()%2)==0) {
			Sesion.crFiscalPrinterOperations.imprimirCodigoBarra(codBarra, true);
		} else {
			Sesion.crFiscalPrinterOperations.imprimirCodigoBarra(entero.format(ventaActual.getCodTienda()) +
															 df.format(ventaActual.getFechaTrans()) +
															 entero.format(ventaActual.getNumCajaFinaliza()) +
															 "0" +
															 ventaActual.getNumTransaccion(), true);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("crearCodigoBarraFiscal(Venta) - end");
		}
	}
	
	/**
	 * Realiza la impresion de la factura de Venta indicada como parametro.
	 * @param ventaActual Venta que se imprimira.
	 */
	public static void imprimirFactura(VentaBR ventaActual, boolean abrirGaveta) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirFactura(Venta, boolean, boolean) - start");
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
 
		//Se realiza la impresion de la factura. Se identifica si es por impresora Fiscal
		if(Sesion.impresoraFiscal) {
			Sesion.crFiscalPrinterOperations.setCalculoMetodoExclusivo(0);
			
			//********* Actualización: 09/02/2007. BECO: Cambios por razones de SENIAT
			//********* Se agrega la verificación de clientes de tipo empleados.
			//********* para estos casos no se imprimen como parte de la factura sino que se coloca
			//********* como nota al pie de página. Esto por requerimiento del SENIAT 
			
			Sesion.crFiscalPrinterOperations.abrirComprobanteFiscal();
		
			if(ventaActual.getCliente()!=null && !ventaActual.getCliente().getNombre().equalsIgnoreCase("")){
				//empleados o clientes no contribuyentes a pie de página
				if(ventaActual.getCliente().getNombreCompleto().length() > 20) { 
					Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("RAZON SOCIAL: " + ventaActual.getCliente().getNombreCompleto().substring(0,20));
					if(ventaActual.getCliente().getNombreCompleto().length() > 40) {
						Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("              " + ventaActual.getCliente().getNombreCompleto().substring(20,40));
						Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("              " + ventaActual.getCliente().getNombreCompleto().substring(40));
					} else 
						Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("              " + ventaActual.getCliente().getNombreCompleto().substring(20));
				} else
					Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("RAZON SOCIAL: " + ventaActual.getCliente().getNombreCompleto());
							
				Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("RIF/C.I: " + ventaActual.getCliente().getCodCliente().replace('N', 'V'));
			}
			
//			*****************************************
		
			crearEncabezadoFiscal(ventaActual);
			crearDetallesFiscal(ventaActual);
			
			//SubTotal de La Factura (Cierre Parcial)
			Sesion.crFiscalPrinterOperations.subTotalTransaccion(ventaActual.consultarMontoTrans(), 1, 3);
		
			crearTotalesFiscal(ventaActual);
			//crearPieDePaginaFiscal(ventaActual);
			Sesion.crFiscalPrinterOperations.cerrarComprobanteFiscalVentas("A");
			try {
				Sesion.crFiscalPrinterOperations.commit();
			} catch (PrinterNotConnectedException e) {
				logger.error("imprimirFactura(Venta, boolean, boolean)", e);
			}
			//ACTUALIZACION BECO: Impresora fiscal GD4
			while (CR.meVenta.getTransaccionPorImprimir()!=null || 
					CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
					MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Bloque catch generado automáticamente
					e.printStackTrace();
				}
				if(MaquinaDeEstadoVenta.errorAtencionUsuario){
					MensajesVentanas.aviso("Hay un documento en curso \nEspere un momento e intente nuevamente");
				}
			}
			if (Sesion.impresoraFiscal) {
				MaquinaDeEstadoVenta.setDocumentoNoFiscalPorImprimir(true);
			}
			
			crearCodigoBarraFiscal(ventaActual);

			try {
				Sesion.crFiscalPrinterOperations.commit();
			} catch (PrinterNotConnectedException e) {
				logger.error("imprimirFactura(Venta, boolean, boolean)", e);
			}
			
			//ACTUALIZACION BECO: Impresora fiscal GD4
			while (CR.meVenta.getTransaccionPorImprimir()!=null || 
					CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
					MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Bloque catch generado automáticamente
					e.printStackTrace();
				}
				if(MaquinaDeEstadoVenta.errorAtencionUsuario){
					MensajesVentanas.aviso("Hay un documento en curso \nEspere un momento e intente nuevamente");
				}
			}
			
			if (Sesion.impresoraFiscal) {
				MaquinaDeEstadoVenta.setDocumentoNoFiscalPorImprimir(true);
			}

			// Abrimos un Documento No Fiscal para Imprimir el Pie de Página de la Factura
			Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
			crearPieDePaginaFiscal(ventaActual);
			Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
			
			
			//Cerramos de nuevo el comprobante para que cote el papel y finalice el comprobante
			Sesion.crFiscalPrinterOperations.cortarPapel();
		
			try {
				Sesion.crFiscalPrinterOperations.commit();
			} catch (PrinterNotConnectedException e) {
				logger.error("imprimirFactura(Venta, boolean, boolean)", e);
			}
		} else {
			// Esta Implementaccion no maneja Impresora NoFiscal
		}

		//****Se muestra el vuelto por visor y por pantalla
		double vuelto;
		if(ventaActual.getMontoVuelto()*100 < 1)
			vuelto = 0;
		else{
				vuelto = ventaActual.getMontoVuelto();
			try { CR.crVisor.enviarString("CAMBIO",0,decf.format(vuelto),2); }
			catch (Exception e) {
				logger.error("imprimirFactura(Venta, boolean, boolean)", e);
			}
		}
							
		//Se muestra el mensaje del vuelto al cajero
		/*
		PantallaVuelto pantallaVuelto = new PantallaVuelto(vuelto);
		MensajesVentanas.centrarVentanaDialogo(pantallaVuelto); 
		*/
		//******
		
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirFactura(Venta, boolean, boolean) - end");
		}
	}
}
