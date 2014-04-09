package com.beco.cr.actualizadorPrecios.promociones.extensiones.impl.reportes;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;


import com.becoblohm.cr.CR;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstadoVenta;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.manejarventa.Venta;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago;
import com.becoblohm.cr.extensiones.impl.reportes.*;
import com.epa.crprinterdriver.CRFiscalPrinterOperations;
import com.epa.crprinterdriver.PrinterNotConnectedException;
import com.beco.cr.actualizadorPrecios.promociones.PromocionExt;
import com.beco.cr.actualizadorPrecios.promociones.Regalo;
import com.beco.cr.actualizadorPrecios.promociones.extensiones.ManejadorReportesPromociones;

public class ReportesPromocionesGD4 extends Reporte implements ManejadorReportesPromociones{
	
	private static final Logger logger = Logger.getLogger(Regalo.class); 

	private static int columnasImpresoraFiscal = 38;
	
	public void imprimeRegalos(PromocionExt pE) {
		int cantMin = pE.getCantMinima();
		double montoMinimo = pE.getMontoMinimo();
		char tipo = pE.getTipoPromocion();
		double regbs = pE.getBsDescuentoOBsBonoRegalo();
		Date fechaFin = pE.getFechaFinaliza();
		String nombrePromocion = pE.getNombrePromocion();
		Vector condicionesCupon = pE.getLineasCondiciones();
		try{
			DecimalFormat enteroConCeros = new DecimalFormat("000");
			DecimalFormat numFactura = new DecimalFormat("000000000");
			DecimalFormat diaMes = new DecimalFormat("00");
			DecimalFormat trans = new DecimalFormat("0000");
			DecimalFormat df = new DecimalFormat("#,##0.00");
			String caja = " Caja:" + enteroConCeros.format(Sesion.getNumCaja());
			String transaccion = " #Tr:" + numFactura.format(Sesion.getCaja().getNumTransaccion());
			String tienda = "Tienda:" + enteroConCeros.format(Sesion.getNumTienda());
			//Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(justificar(textoIzquierda, textoderecha, columnasImpresoraFiscal));
			double monto = pE.getSumMontoTotal();
			// CAMBIO WDIAZ 07-2012 se dividia entre cantMin y ahora montoMinimo
			int n = (int)(monto/montoMinimo);
			String m = df.format(n*regbs);

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
			
			
			if (tipo==Sesion.TIPO_PROMOCION_REGALO_CUPON && monto>=montoMinimo){
				if (pE.getAcumulaPremio().equalsIgnoreCase("Y"))
					n = 1;
				// Imprime los cupones para los sorteos			
				while (n>0){
					if (Sesion.impresoraFiscal) {
						MaquinaDeEstadoVenta.setDocumentoNoFiscalPorImprimir(true);
					}
					Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
						SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
						Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("FECHA: "+formatoFecha.format(Sesion.getFechaSistema()),"HORA: "+Sesion.getHoraSistema(), columnasImpresoraFiscal),0);
						Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Reporte.crearLineaDeDivision(columnasImpresoraFiscal), 0);
						Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(nombrePromocion,1);
						Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("     ", 0);
					Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Nombre: ______________________________", 0);
					Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Cedula/Rif: __________________________", 0);
					Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Telefono: ____________________________", 0);
					Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("email: _______________________________", 0);
					Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("      ", 0);
					Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("      ", 0);
					Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Firma: ____________________________", 0);
					Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(tienda+caja+transaccion, 0);
					Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Promoción BECO",1);
					Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Serial: "+enteroConCeros.format(Sesion.getNumTienda())
							+(Sesion.getFechaSistema().getYear()+1900)+
							diaMes.format(Sesion.getFechaSistema().getMonth()+1)+diaMes.format(Sesion.getFechaSistema().getDate())+
							enteroConCeros.format(Sesion.getNumCaja())+trans.format(Sesion.getCaja().getNumTransaccion()),1);
					Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Reporte.crearLineaDeDivision(columnasImpresoraFiscal), 0);
					//Modificado por jgraterol para agregar al cupón las condiciones del sorteo
					if(condicionesCupon.size()!=0){
						Iterator<String> i  =  condicionesCupon.iterator();
						while (i.hasNext()){
							String lineaCondicion = (String)i.next();
							Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Reporte.justificar(lineaCondicion, "  ", columnasImpresoraFiscal), 0);
						}
					}
					Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Reporte.crearLineaDeDivision(columnasImpresoraFiscal), 0);
					Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
					
					Sesion.crFiscalPrinterOperations.cortarPapel();
					try {
						Sesion.crFiscalPrinterOperations.commit();
					} catch (PrinterNotConnectedException e) {
						logger.error("error en regalo", e);
					}
					n--;
				
					//Ciclo para que no puedan realizar una venta mientras esta imprimiendo los  cupones
					while (CR.meVenta.getTransaccionPorImprimir()!=null || 
							CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
							MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()) {
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							// TODO Bloque catch generado automáticamente
							e.printStackTrace();
						}
					}
					
				}
				
			}else if(tipo==Sesion.TIPO_PROMOCION_REGALO_CALCOMANIA && monto>=cantMin){
				if (Sesion.impresoraFiscal) {
					MaquinaDeEstadoVenta.setDocumentoNoFiscalPorImprimir(true);
				}

				Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
				SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("FECHA: "+formatoFecha.format(Sesion.getFechaSistema()),"HORA: "+Sesion.getHoraSistema(), columnasImpresoraFiscal),0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Reporte.crearLineaDeDivision(columnasImpresoraFiscal), 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Usted se ha ganado:", 1);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Cantidad de calcomanias: "+n, 1);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Para canjear por la(s) calcomania(s)", 1);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("debe presentar este cupón en Servicios", 1);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("al Cliente con su factura de compra", 1);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("fin de la promocion:"+formatoFecha.format(fechaFin), 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(tienda+caja+transaccion, 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("BECO",1);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Reporte.crearLineaDeDivision(columnasImpresoraFiscal), 0);
				Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
				
				Sesion.crFiscalPrinterOperations.cortarPapel();
				try {
					Sesion.crFiscalPrinterOperations.commit();
					//Sesion.crFiscalPrinterOperations.resetPrinter();
				} catch (PrinterNotConnectedException e) {
					logger.error("error en regalo", e);
				}
			}else if(tipo==Sesion.TIPO_PROMOCION_REGALO_BONO_REGALO && monto>=cantMin){
				if (Sesion.impresoraFiscal) {
					MaquinaDeEstadoVenta.setDocumentoNoFiscalPorImprimir(true);
				}
				
				Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
				SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("FECHA: "+formatoFecha.format(Sesion.getFechaSistema()),"HORA: "+Sesion.getHoraSistema(), columnasImpresoraFiscal),0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Reporte.crearLineaDeDivision(columnasImpresoraFiscal), 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Usted se ha ganado:", 1);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Bs en Bonos Regalo: "+m, 1);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Para canjear por el/los bono(s)", 1);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("regalo debe presentar este cupón", 1);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("en Servicios al Cliente con su", 1);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("factura de compra", 1);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("fin de la promoción:"+formatoFecha.format(fechaFin), 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(tienda+caja+transaccion, 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Promoción BECO",1);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Reporte.crearLineaDeDivision(columnasImpresoraFiscal), 0);
				Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
				Sesion.crFiscalPrinterOperations.cortarPapel();
				
				try {
					Sesion.crFiscalPrinterOperations.commit();
				}catch (PrinterNotConnectedException e) {
					logger.error("error en regalo", e);
				}
			}else if(tipo==Sesion.TIPO_PROMOCION_REGALO_PRODUCTO){
				if (Sesion.impresoraFiscal) {
					MaquinaDeEstadoVenta.setDocumentoNoFiscalPorImprimir(true);
				}
				if(!Venta.regalosRegistrados.isEmpty()){
				StringTokenizer st = new StringTokenizer(nombrePromocion,"\n");
					Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
					SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
					Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("FECHA: "+formatoFecha.format(Sesion.getFechaSistema()),"HORA: "+Sesion.getHoraSistema(), columnasImpresoraFiscal),0);
					Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Reporte.crearLineaDeDivision(columnasImpresoraFiscal), 0);
					Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Usted se ha ganado:", 1);
					while (st.hasMoreTokens())
						Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal((st.nextToken()), 1);
					//Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Cantidad: "+pE.getNumObsequiosEntregables(CR.meVenta.getVenta()), 1);
					Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Cantidad: "+pE.getCantRegalada(), 1);
					Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Para canjear por el/los obsequio(s)", 1);
					Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("debe presentar este cupón en Servicios", 1);
					Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("al Cliente con su factura de compra.", 1);
					Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Los obsequios tienen cantidad limitada.", 1);
					Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("fin de la promocion:"+formatoFecha.format(fechaFin), 0);
					Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(tienda+caja+transaccion, 0);
					Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Promoción BECO",1);
					Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Reporte.crearLineaDeDivision(columnasImpresoraFiscal), 0);
					if(condicionesCupon.size()!=0){
						Iterator<String> i  =  condicionesCupon.iterator();
						while (i.hasNext()){
							String lineaCondicion = (String)i.next();
							Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Reporte.justificar(lineaCondicion, "  ", columnasImpresoraFiscal), 0);
						}
					}
					Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Reporte.crearLineaDeDivision(columnasImpresoraFiscal), 0);
					Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
					Sesion.crFiscalPrinterOperations.cortarPapel();
					try {
						Sesion.crFiscalPrinterOperations.commit();
					} catch (PrinterNotConnectedException e) {
						logger.error("error en regalo", e);
					}
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
			
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void imprimirProdComplementario(String nombre, String fecha, Vector<String> condiciones){
		try{
			StringTokenizer st = new StringTokenizer(nombre,"\n");
				DecimalFormat enteroConCeros = new DecimalFormat("000");
				DecimalFormat numFactura = new DecimalFormat("000000000");
				String caja = " caja:" + enteroConCeros.format(Sesion.getNumCaja());
				String transaccion = " #TR:" + numFactura.format(Sesion.getCaja().getNumTransaccion());
				String tienda = "Tienda:" + enteroConCeros.format(Sesion.getNumTienda());
				
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

				Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
				SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("FECHA: "+formatoFecha.format(Sesion.getFechaSistema()),"HORA: "+Sesion.getHoraSistema(), columnasImpresoraFiscal),0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Reporte.crearLineaDeDivision(columnasImpresoraFiscal), 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Usted se ha ganado:", 1);
				while (st.hasMoreTokens())
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal((st.nextToken()), 1);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("cambie este ticket por un cupon de",1);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("sorteo, presentando su factura en caja", 1);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Ciertas condiciones aplican.", 1);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("fin de la promocion: 20/05/2011", 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(tienda+caja+transaccion, 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Promoción BECO",1);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Reporte.crearLineaDeDivision(columnasImpresoraFiscal), 0);
				if(condiciones.size()!=0){
					Iterator<String> i  =  condiciones.iterator();
					while (i.hasNext()){
						String lineaCondicion = (String)i.next();
						Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Reporte.justificar(lineaCondicion, "  ", columnasImpresoraFiscal), 0);
					}
				}
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Reporte.crearLineaDeDivision(columnasImpresoraFiscal), 0);
				Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
				Sesion.crFiscalPrinterOperations.cortarPapel();
				try {
					Sesion.crFiscalPrinterOperations.commit();
				} catch (PrinterNotConnectedException e) {
					logger.error("error en regalo", e);
				}
		}catch(Exception f){System.out.print(f);}
	}
	
	/**
	 * Método imprimirComprobate
	 * 	Realiza la impresion de la factura de Venta indicada como parametro.
	 * @param cte
	 * @param codAutorizante
	 * @param fPago
	 * @param nombreDpto Nombre del departamento (mercadeo o merchandising) al que corresponde la forma de pago
	 */
	public void imprimirVoucherPago(Cliente cte, FormaDePago fPago, double monto, int numTrans) {
		if (logger.isDebugEnabled()) {
			logger
					.debug("imprimirVoucherPago(Cliente, String, FormaDePago, double, int) - start");
		}
		if(Sesion.impresoraFiscal) {
			try {
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
				Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();

				//Encabezado
				SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
				SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Sesion.getTienda().getRazonSocial(), 1);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
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
				
				//Detalle
				DecimalFormat precio = new DecimalFormat("#,##0.00");
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Monto Pago", precio.format(monto),columnasImpresoraFiscal), 0);
				
				Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
				
				Sesion.crFiscalPrinterOperations.cortarPapel();
				Sesion.crFiscalPrinterOperations.commit();
			} catch (PrinterNotConnectedException e) {
				logger
						.error(
								"imprimirVoucherPago(Cliente, String, FormaDePago, double, int)",
								e);
			}
		}

		if (logger.isDebugEnabled()) {
			logger
					.debug("imprimirVoucherPago(Cliente, String, FormaDePago, double, int) - end");
		}
	}
}
