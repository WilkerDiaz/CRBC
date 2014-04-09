/**
 * =============================================================================
 * Proyecto   : CajaRegistradora
 * Paquete    : com.becoblohm.cr.reportes
 * Programa   : FacturaAnulacion.java
 * Creado por : gmartinelli
 * Creado en  : 28-may-04 14:07:10
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.2 
 * Fecha       : 25/08/2006 08:24 AM
 * Analista    : gmartinelli
 * Descripción : Políticas del Seniat. Cédulas inválidas no se imprimen en el
 * 			     Ticket, y las cédulas con N se reemplazan por V
 * =============================================================================
 */
package com.beco.cr.reportes.gd4;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.gui.PantallaVuelto;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstadoVenta;
import com.becoblohm.cr.manejadorsesion.Sesion;
//import com.becoblohm.cr.manejarpago.Pago;
import com.becoblohm.cr.manejarventa.Anulacion;
import com.becoblohm.cr.manejarventa.DetalleTransaccion;
import com.becoblohm.cr.mediadoresbd.BaseDeDatosVenta;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.crprinterdriver.CRFiscalPrinterOperations;
import com.epa.crprinterdriver.PrinterNotConnectedException;

/** 
 * Descripción: 
 * 		Clase que maneja los perifericos del proyecto de CR. En principio maneja
 * únicamente la impresora. Restaría el manejo de Escaners, Visores, Teclados, etc.
 */
public class FacturaAnulacion extends Reporte {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(FacturaAnulacion.class);
	
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
	private static void crearEncabezadoFiscal(Anulacion anulacion) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal(Anulacion) - start");
		}

		DecimalFormat enteroConCeros = new DecimalFormat("000");
		DecimalFormat numFactura = new DecimalFormat("00000000000");
		String cajero = "Cajero:" + Sesion.getUsuarioActivo().getNumFicha();
		String caja = "POS:" + enteroConCeros.format(Sesion.getCaja().getNumero());
		String transaccion = "Tr:" + numFactura.format(anulacion.getNumTransaccion());
				
		String lineaIzquierda = cajero +  centrar(transaccion,16);

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
	private static void crearDetallesFiscal(Anulacion anulacion, Vector<Integer> correlativosOrd) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearDetallesFiscal(Anulacion, Vector) - start");
		}

		// Para cada detalle de la venta
		DetalleTransaccion detalleActual;
		DecimalFormat precio = new DecimalFormat("#,##0.00");
		for (int i=0; i<correlativosOrd.size(); i++) {
			int posicion = ((Integer)correlativosOrd.elementAt(i)).intValue();			
			detalleActual = (DetalleTransaccion)anulacion.getDetallesTransaccion().elementAt(posicion - 1);
			Sesion.crFiscalPrinterOperations.imprimirItemNotaCredito(detalleActual.getProducto().getDescripcionCorta() + " 1 " + detalleActual.getProducto().getAbreviadoUnidadVenta(), 0, 0, 0);
			if (!detalleActual.getCondicionVenta().equals(Sesion.condicionNormal)) {
				// Si se ha aplicado alguna rebaja (Promocion, Empaque, Cambio de Precio) se especifica
				Sesion.crFiscalPrinterOperations.imprimirItemNotaCredito(" PR:" + precio.format(detalleActual.getProducto().getPrecioRegular())
								+ " " + detalleActual.getCondicionVenta() + ":" + precio.format(detalleActual.getPrecioFinal()), 0, 0, 0);
			}
	
			String codProd = detalleActual.getProducto().getCodProducto();
			int longitud = codProd.length();
			if ((Sesion.getLongitudCodigoInterno()>-1) && (longitud>Sesion.getLongitudCodigoInterno())) {
				codProd = codProd.substring(codProd.length()-Sesion.getLongitudCodigoInterno()); 
				for (int j=0;j<longitud-Sesion.getLongitudCodigoInterno();j++) {
					codProd = " " + codProd; 
				}
			}
			
			if (detalleActual.getCantidad() > 1) {
				Sesion.crFiscalPrinterOperations.imprimirItemNotaCredito(detalleActual.getCantidad() + " x " + precio.format(detalleActual.getPrecioFinal()), 0, 0, 0);
			}
			
			if((anulacion.getMontoImpuesto()==0)&&(anulacion.getCliente().isExento()))		
				Sesion.crFiscalPrinterOperations.imprimirItemNotaCredito("COD: " + detalleActual.getProducto().getCodProducto(), detalleActual.getCantidad(), detalleActual.getPrecioFinal(), 0);
			else	
				Sesion.crFiscalPrinterOperations.imprimirItemNotaCredito("COD: " + detalleActual.getProducto().getCodProducto(), detalleActual.getCantidad(), detalleActual.getPrecioFinal(), detalleActual.getProducto().getImpuesto().getPorcentaje());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("crearDetallesFiscal(Anulacion, Vector) - end");
		}
	}
	
	/**
	 * Realiza la impresion del pie de página de la factura de Anulación indicada como parametro.
	 * @param anulacion Anulación que se imprimirá.
	 */
	private static void crearPieDePaginaFiscal(Anulacion anulacion) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePaginaFiscal(Anulacion) - start");
		}
	
		DecimalFormat numFactura = new DecimalFormat("00000000000");
		float nroItems = 0;
		DecimalFormat cant = new DecimalFormat("#0.00");
		
		//Se imprime el número de items de la factura
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		for (int i=0; i<anulacion.getLineasFacturacion(); i++){
				nroItems += ((DetalleTransaccion)anulacion.getDetallesTransaccion().elementAt(i)).getCantidad();	
		}
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Tota_ Art.: " + cant.format(nroItems), 0);
		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar("A N U L A  TR. " + numFactura.format(anulacion.getVentaOriginal().getNumTransaccion()), columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		// Si existe publicidad de la tienda, la insertamos 
		if (Sesion.getTienda().getPublicidades().size()>0) {
			// Agregamos las publicidades de la Factura
			for (int i=0; i<Sesion.getTienda().getPublicidades().size();i++)
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar((String)Sesion.getTienda().getPublicidades().elementAt(i),columnasImpresoraFiscal), 0);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePaginaFiscal(Anulacion) - end");
		}
	}
	
	/**
	 * Método imprimirFactura
	 * 	Realiza la impresion de la factura de Anulación indicada como parametro.
	 * @param anulacion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static void imprimirFactura(Anulacion anulacion) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirFactura(Anulacion) - start");
		}

		DecimalFormat df = new DecimalFormat("#,##0.00");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		//Antes de imprimir se ordena el detalle por tipo de entrega y por código de producto
		Vector<Integer> correlativosOrdenados = BaseDeDatosVenta.obtenerDetallesOrdenados(anulacion);
		//********
		//Se verifica si la gaveta se abre por impresora o por el visor. Si es por impresora debemos abrir
		//primero la gaveta antes de enviar a imprimir porque si no, se queda la gaveta cerrada hasta que termina la impresion.
		
		/*if (Sesion.aperturaGavetaPorImpresora) {
			try {
				CR.me.abrirGaveta(false);
			} catch (Exception e){
				logger.error("imprimirFactura(Venta)", e);
			}
		 }*/
		//********		
		//Se realiza la impresion de la factura. Se identifica si es por impresora Fiscal
		if(Sesion.impresoraFiscal) {
			
			//ACTUALIZACION BECO: Impresora fiscal GD4
			while (CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
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
				CRFiscalPrinterOperations.setImpresionFiscalEnCurso(true);
			}
			Sesion.crFiscalPrinterOperations.setCalculoMetodoExclusivo(0);
			//Sesion.crFiscalPrinterOperations.crearDocumento(true);
			//Sesion.crFiscalPrinterOperations.imprimirLogo();
			if (anulacion.getCliente().getCodCliente() == null ||
				anulacion.getCliente().getCodCliente().length() <= 7) {
				// FIXME: No se debe imprimir devoluciones por la impresora fiscal sin datos de cliente
				Sesion.crFiscalPrinterOperations.abrirComprobanteDevolucion(null, null, anulacion.getVentaOriginal().getNumComprobanteFiscal(), Sesion.getCaja().getSerial(), anulacion.getVentaOriginal().getFechaTrans(), anulacion.getVentaOriginal().getHoraFin());
			} else {		
				//anulacion.getCliente().setCodCliente(anulacion.getCliente().getCodCliente().replace('N', 'V'));
				Sesion.crFiscalPrinterOperations.abrirComprobanteDevolucion(anulacion.getCliente().getNombreCompleto(), anulacion.getCliente().getCodCliente().replace('N', 'V'), anulacion.getVentaOriginal().getNumComprobanteFiscal(), Sesion.getCaja().getSerial(), anulacion.getVentaOriginal().getFechaTrans(), anulacion.getVentaOriginal().getHoraFin());

				Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(crearLineaDeDivision(columnasImpresoraFiscal));
				
				if(anulacion.getCliente().getNombreCompleto().length() > 20) { 
					Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("RAZON SOCIAL: " + anulacion.getCliente().getNombreCompleto().substring(0,20));
					if(anulacion.getCliente().getNombreCompleto().length() > 40) {
						Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("              " + anulacion.getCliente().getNombreCompleto().substring(20,40));
						Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("              " + anulacion.getCliente().getNombreCompleto().substring(40));
					} else 
						Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("              " + anulacion.getCliente().getNombreCompleto().substring(20));
				} else
					Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("RAZON SOCIAL: " + anulacion.getCliente().getNombreCompleto());

				Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("CI/RIF: " + anulacion.getCliente().getCodCliente().replace('N', 'V'));

				Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("Maquina Fiscal Emision: " + Sesion.getCaja().getSerial());
				Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("Factura Original: " + anulacion.getVentaOriginal().getNumComprobanteFiscal());
				Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("Fecha Factura Original: "+ sdf.format(anulacion.getVentaOriginal().getFechaTrans()));
				Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(crearLineaDeDivision(columnasImpresoraFiscal));
			}
			
			crearEncabezadoFiscal(anulacion);
			crearDetallesFiscal(anulacion, correlativosOrdenados);
			
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
			crearPieDePaginaFiscal(anulacion);
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
		if((anulacion.getMontoBase() + anulacion.getMontoImpuesto())*100 < 1)
			saldo = 0;
		else
			saldo = (anulacion.getMontoBase() + anulacion.getMontoImpuesto());
		try { CR.crVisor.enviarString("SALDO CLIENTE",0,df.format(saldo),2); }
		catch (Exception e) {
			logger.error("imprimirFactura(Anulacion)", e);
		}
								
		//Se muestra el mensaje del vuelto al cajero
		PantallaVuelto pantallaVuelto = new PantallaVuelto(saldo, true);
		MensajesVentanas.centrarVentanaDialogo(pantallaVuelto);
		//***
		
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirFactura(Anulacion) - end");
		}
	}
}
