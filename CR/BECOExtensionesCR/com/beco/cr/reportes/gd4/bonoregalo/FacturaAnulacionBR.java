/**
 * =============================================================================
 * Proyecto   : CajaRegistradora
 * Paquete    : com.becoblohm.cr.reportes.bonoregalo
 * Programa   : FacturaAnulacionBR.java
 * Creado por : jgraterol
 * Creado en  : 28-may-04 14:07:10
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
import com.becoblohm.cr.gui.PantallaVuelto;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstadoVenta;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarbr.ComprobanteFiscal;
import com.becoblohm.cr.manejarbr.DetalleTransaccionBR;
import com.becoblohm.cr.manejarbr.VentaBR;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.crprinterdriver.CRFiscalPrinterOperations;
import com.epa.crprinterdriver.PrinterNotConnectedException;

/** 
 * Descripción: 
 * 		Clase que maneja los perifericos del proyecto de CR. En principio maneja
 * únicamente la impresora. Restaría el manejo de Escaners, Visores, Teclados, etc.
 */
public class FacturaAnulacionBR extends Reporte {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(FacturaAnulacionBR.class);
	
	//Variables necesarias para la impresion
	//private static int columnasImpresoraFiscal = 38;
	
	/*
	 * Devuelve el encoding necesario para imprimir el simbolo de la
	 * moneda local.
	 */
	public static String obtenerSimboloMonedaLocal(String monedaLocal){
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerSimboloMonedaLocal(String) - start");
		}

		String resultado="";
		if(monedaLocal.trim().equals("¢")){
			resultado = ""+'\275';		
		}
		else{
			resultado = monedaLocal;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerSimboloMonedaLocal(String) - end");
		}
		return resultado;
	}

	
	
	
	//************** Métodos para impresora Fiscal ********************
	//*****************************************************************
	/**
	 * Realiza la impresion del encabezado de la factura de Anulación indicada como parametro.
	 * @param anulacion Anulación que se imprimirá.
	 */
	private static void crearEncabezadoFiscal(VentaBR venta) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal() - start");
		}

		DecimalFormat enteroConCeros = new DecimalFormat("000");
		String cajero = "Cajero:" + Sesion.getUsuarioActivo().getNumFicha();
		String caja = "POS:" + enteroConCeros.format(Sesion.getCaja().getNumero());
	
				
		String lineaIzquierda = cajero;

		Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(justificar(lineaIzquierda, caja, columnasImpresoraFiscal));
		Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(crearLineaDeDivision(columnasImpresoraFiscal));

		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal(Anulacion) - end");
		}
	}
	
	/**
	 * Realiza la impresion de los detallles de la factura de Anulación indicada como parametro.
	 * @param anulacion Anulación que se imprimirá.
	 */
	private static void crearDetallesFiscal(VentaBR ventaBR) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearDetallesFiscal(Anulacion, Vector) - start");
		}

		// Para cada detalle de la venta
		DetalleTransaccionBR detalleActual;
		for (int i=0; i<ventaBR.getDetallesTransaccionBR().size(); i++) {
			detalleActual = (DetalleTransaccionBR)ventaBR.getDetallesTransaccionBR().elementAt(i);
			Sesion.crFiscalPrinterOperations.imprimirItemNotaCredito("Tarjeta N. "+detalleActual.getCodTarjeta().subSequence(14, 18), 1, detalleActual.getMonto(), 0);	
		}

		if (logger.isDebugEnabled()) {
			logger.debug("crearDetallesFiscal(Anulacion, Vector) - end");
		}
	}
	
	/**
	 * Realiza la impresion del pie de página de la factura de Anulación indicada como parametro.
	 * @param anulacion Anulación que se imprimirá.
	 */
	private static void crearPieDePaginaFiscal(VentaBR venta) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePaginaFiscal(Anulacion) - start");
		}
	
		DecimalFormat numFactura = new DecimalFormat("00000000000");
		float nroItems = 0;
		DecimalFormat cant = new DecimalFormat("#0.00");
		
		//Se imprime el número de items de la factura
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		nroItems = venta.getLineasFacturacion();
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Tota_ Art.: " + cant.format(nroItems), 0);
		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar("A N U L A  TR. " + numFactura.format(venta.getNumTransaccion()), columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("VENTA NO SUJETA AL COBRO DE IVA, DE",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("ACUERDO CON LO ESTABLECIDO EN EL",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("ARTICULO 16, NUMERAL 2, DE LA LEY DEL",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("IMPUESTO AL VALOR AGREGADO",0);
		/*Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("CONDICIONES:",1);
		//Ciclo sobre las condiciones
		for(int i=0;i<venta.getCondicionesServicio().size();i++){
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal((String)venta.getCondicionesServicio().elementAt(i),0);
		}*/

		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePaginaFiscal(Anulacion) - end");
		}
	}
	
	/**
	 * Método imprimirFactura
	 * 	Realiza la impresion de la factura de Anulación indicada como parametro.
	 * @param anulacion
	 */
	public static void imprimirFactura(VentaBR venta, Cliente clienteAnterior, boolean mostrarVuelto, boolean abrirGaveta) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirFactura(Anulacion) - start");
		}
		Cliente cliente = null;
		if(clienteAnterior==null)
			cliente=venta.getCliente();
		else 
			cliente=venta.getClienteAnterior();
		
		DecimalFormat df = new DecimalFormat("#,##0.00");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		if (abrirGaveta) {
			try {
				CR.me.abrirGaveta(false);
			} catch (Exception e){
				logger.error("imprimirFactura(Venta)", e);
			}
		}
		
		//Se realiza la impresion de la factura. Se identifica si es por impresora Fiscal
		if(Sesion.impresoraFiscal) {
			
			
			Sesion.crFiscalPrinterOperations.setCalculoMetodoExclusivo(0);
			if (cliente.getCodCliente() == null ||
				cliente.getCodCliente().length() <= 7) {
					Sesion.crFiscalPrinterOperations.abrirComprobanteDevolucion(null, null, venta.getNumComprobanteFiscal(), Sesion.getCaja().getSerial(), venta.getFechaTrans(), venta.getHoraFin());
			} else {		
				Sesion.crFiscalPrinterOperations.abrirComprobanteDevolucion(cliente.getNombreCompleto(), cliente.getCodCliente().replace('N', 'V'), venta.getNumComprobanteFiscal(), Sesion.getCaja().getSerial(), venta.getFechaTrans(), venta.getHoraFin());

				Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(crearLineaDeDivision(columnasImpresoraFiscal));
				
				if(cliente.getNombreCompleto().length() > 20) { 
					Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("RAZON SOCIAL: " + cliente.getNombreCompleto().substring(0,20));
					if(cliente.getNombreCompleto().length() > 40) {
						Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("              " + cliente.getNombreCompleto().substring(20,40));
						Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("              " + cliente.getNombreCompleto().substring(40));
					} else 
						Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("              " + cliente.getNombreCompleto().substring(20));
				} else
					Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("RAZON SOCIAL: " + cliente.getNombreCompleto());

				Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("CI/RIF: " + cliente.getCodCliente().replace('N', 'V'));

				Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("Maquina Fiscal Emision: " + ((ComprobanteFiscal)venta.getComprobantesFiscales().elementAt(venta.getComprobantesFiscales().size()-1)).getSerialImpresora());
				Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("Factura Original: " + ((ComprobanteFiscal)venta.getComprobantesFiscales().elementAt(venta.getComprobantesFiscales().size()-1)).getNumComprobante());
				Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("Fecha Factura Original: "+ sdf.format(venta.getFechaTrans()));
				Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(crearLineaDeDivision(columnasImpresoraFiscal));
			}
			
			crearEncabezadoFiscal(venta);
			crearDetallesFiscal(venta);
			
			//Cerramos el comprobante Fiscal en modo "A" (Parcial)
			Sesion.crFiscalPrinterOperations.cerrarComprobanteFiscalNotaCredito("A");
		
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
			crearPieDePaginaFiscal(venta);
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
		// Se muestra el monto del vuelto al cliente por el visor
		double saldo;
		if((venta.getMontoBase() + venta.getMontoImpuesto())*100 < 1)
			saldo = 0;
		else
			saldo = (venta.getMontoBase() + venta.getMontoImpuesto());
		try { CR.crVisor.enviarString("SALDO CLIENTE",0,df.format(saldo),2); }
		catch (Exception e) {
			logger.error("imprimirFactura(Anulacion)", e);
		}
								
		//Se muestra el mensaje del vuelto al cajero
		if(mostrarVuelto){
			PantallaVuelto pantallaVuelto = new PantallaVuelto(saldo, abrirGaveta);
			MensajesVentanas.centrarVentanaDialogo(pantallaVuelto);
		}
		//***
		
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirFactura(Anulacion) - end");
		}
	}
}
