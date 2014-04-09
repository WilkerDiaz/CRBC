package com.beco.cr.actualizadorPrecios.promociones.extensiones.impl.reportes;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;


import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago;
import com.becoblohm.cr.extensiones.impl.reportes.*;
import com.epa.crprinterdriver.PrinterNotConnectedException;
import com.beco.cr.actualizadorPrecios.promociones.PromocionExt;
import com.beco.cr.actualizadorPrecios.promociones.Regalo;
import com.beco.cr.actualizadorPrecios.promociones.extensiones.ManejadorReportesPromociones;

public class DefaultReportesPromociones extends Reporte implements ManejadorReportesPromociones{
	
	private static final Logger logger = Logger.getLogger(Regalo.class); 

	private static int columnasImpresoraFiscal = 40;
	
	public void imprimeRegalos(PromocionExt pE) {
		int cantMin = pE.getCantMinima();
		double regbs = pE.getBsDescuentoOBsBonoRegalo();
		Date fechaFin = pE.getFechaFinaliza();
		String nombrePromocion = pE.getNombrePromocion();
		Vector condicionesCupon = pE.getLineasCondiciones();
		char tipo = pE.getTipoPromocion();
		
		try{
			DecimalFormat enteroConCeros = new DecimalFormat("000");
			DecimalFormat numFactura = new DecimalFormat("00000000000");
			String caja = " P:" + enteroConCeros.format(Sesion.getNumCaja());
			String transaccion = " f:" + numFactura.format(Sesion.getCaja().getNumTransaccion()+1);
			String tienda = " t:" + enteroConCeros.format(Sesion.getNumTienda());
			//Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(justificar(textoIzquierda, textoderecha, columnasImpresoraFiscal));
			double monto = pE.getSumMontoTotal();
			int n = (int)(monto/cantMin);
			double m = n*regbs;
			if (tipo=='D'){
				// Imprime los cupones para los sorteos			
				while (n>0){
				Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Reporte.crearLineaDeDivision(columnasImpresoraFiscal), 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(nombrePromocion,1);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(" ", 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Nombre: ______________________________", 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Cedula/Rif: __________________________", 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("telefono: ____________________________", 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("      ", 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("      ", 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Firma: ____________________________", 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(tienda+caja+transaccion, 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Promoción BECO",1);
				//Modificado por jgraterol para agregar al cupón las condiciones del sorteo
				if(condicionesCupon.size()!=0){
					Iterator<String> i  =  condicionesCupon.iterator();
					while (i.hasNext()){
						String lineaCondicion = (String)i.next();
						Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Reporte.justificar(lineaCondicion, "", columnasImpresoraFiscal), 0);
					}
				}
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Reporte.crearLineaDeDivision(columnasImpresoraFiscal), 0);
				Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
				try {
					Sesion.crFiscalPrinterOperations.commit();
				} catch (PrinterNotConnectedException e) {
					logger.error("error en regalo", e);
				}
				n--;
				}
			}else if(tipo=='C'){
				//Imprime la cantidad de bono regalos
				Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Reporte.crearLineaDeDivision(columnasImpresoraFiscal), 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(nombrePromocion, 1);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Cantidad de calcomanias: "+n, 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("fin:"+fechaFin.toString()+tienda+caja+transaccion, 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Promoción BECO",1);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Reporte.crearLineaDeDivision(columnasImpresoraFiscal), 0);
				Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
				try {
					Sesion.crFiscalPrinterOperations.commit();
					//Sesion.crFiscalPrinterOperations.resetPrinter();
				} catch (PrinterNotConnectedException e) {
					logger.error("error en regalo", e);
				}
			}else if(tipo=='B'){
				Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Reporte.crearLineaDeDivision(columnasImpresoraFiscal), 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(nombrePromocion, 1);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Bs en BonoRegalos: "+m, 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("fin:"+fechaFin.toString()+tienda+caja+transaccion, 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Promoción BECO",1);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Reporte.crearLineaDeDivision(columnasImpresoraFiscal), 0);
				Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
				try {
					Sesion.crFiscalPrinterOperations.commit();
				}catch (PrinterNotConnectedException e) {
					logger.error("error en regalo", e);
				}
			}
		}catch(Exception f){System.out.print(f);
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
				DecimalFormat numFactura = new DecimalFormat("00000000000");
				String caja = " P:" + enteroConCeros.format(Sesion.getNumCaja());
				String transaccion = " f:" + numFactura.format(Sesion.getCaja().getNumTransaccion()+1);
				String tienda = " t:" + enteroConCeros.format(Sesion.getNumTienda());
				
				Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Reporte.crearLineaDeDivision(columnasImpresoraFiscal), 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Usted se ha ganado:", 1);
				while (st.hasMoreTokens())
					Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal((st.nextToken()), 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("fec:"+fecha+tienda+caja+transaccion, 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Promoción BECO",1);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Reporte.crearLineaDeDivision(columnasImpresoraFiscal), 0);
				Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
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
