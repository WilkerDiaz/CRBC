/**
 * =============================================================================
 * Proyecto   : CajaRegistradora
 * Paquete    : com.becoblohm.cr.reportes
 * Programa   : ReporteX.java
 * Creado por : gmartinelli
 * Creado en  : 31-may-04 14:46:10
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
package com.beco.cr.reportes.gd4;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstadoVenta;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.extensiones.ManejoPagosFactory;
import com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.crprinterdriver.CRFiscalPrinterOperations;
import com.epa.crprinterdriver.PrinterNotConnectedException;

/** 
 * Descripción: 
 * 		Clase que maneja los perifericos del proyecto de CR. En principio maneja
 * únicamente la impresora. Restaría el manejo de Escaners, Visores, Teclados, etc.
 */
public class ReporteX extends Reporte {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ReporteX.class);

	// Variables necesarias para la impresion
	private static String codUsuario;
	private static Date fechaReporte;
	private static String nombreUsuario;
	
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
			resultado = "Bs.";
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerSimboloMonedaLocal(String) - end");
		}
		return resultado;
	}

	/* Metodos Para impresora Fiscal */
	private static void crearEncabezadoFiscal() {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal() - start");
		}

		SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");
		DecimalFormat enteroConCeros = new DecimalFormat("000");
		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal,'*'), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Sesion.getTienda().getRazonSocial(), 3);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal,'*'), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar("Reporte de Cajero. Fecha: " + fecha.format(fechaReporte), columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Caja/Tda: " + enteroConCeros.format(Sesion.getCaja().getNumero()) + "/" +enteroConCeros.format(Sesion.getTienda().getNumero()),
						"Cajero: " + codUsuario, columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Nombre: " + nombreUsuario, 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar(fecha.format(Sesion.getFechaSistema()) + "  " + hora.format(Sesion.getHoraSistema()),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);

		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal() - end");
		}
	}
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private static void crearDetalleFiscal() {
		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalleFiscal() - start");
		}

		DecimalFormat precio = new DecimalFormat("#,##0.00");
		DecimalFormat cantidad = new DecimalFormat("#,##0");
		double[] ventas = MediadorBD.obtenerTransacciones(codUsuario, fechaReporte, Sesion.getCaja().getNumero(),Sesion.VENTA);
		double[] anulaciones = MediadorBD.obtenerTransacciones(codUsuario, fechaReporte, Sesion.getCaja().getNumero(),Sesion.ANULACION);
		double[] devoluciones = MediadorBD.obtenerTransacciones(codUsuario, fechaReporte, Sesion.getCaja().getNumero(),Sesion.DEVOLUCION);
		int nroVtas = MediadorBD.obtenerNumTransacciones(codUsuario, fechaReporte, Sesion.VENTA);
		int nroDev = MediadorBD.obtenerNumTransacciones(codUsuario, fechaReporte, Sesion.DEVOLUCION);
		int nroAnul = MediadorBD.obtenerNumTransacciones(codUsuario, fechaReporte, Sesion.ANULACION);
		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar("Detalle de Movimientos", columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Ventas Brutas" + alinearDerecha("(" + cantidad.format(nroVtas) + ")",13), precio.format(ventas[0]+ventas[2]),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Anulaciones  " + alinearDerecha("(" + cantidad.format(nroAnul) + ")",13), precio.format(anulaciones[0]+anulaciones[2]),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Devoluciones " + alinearDerecha("(" + cantidad.format(nroDev) + ")",13), precio.format(devoluciones[0]+devoluciones[2]),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha(crearLineaDeDivision(columnasImpresoraFiscal-15),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Venta Neta", precio.format(ventas[0]+ventas[2]-(anulaciones[0]+anulaciones[2])-(devoluciones[0]+devoluciones[2])),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha(crearLineaDeDivision(columnasImpresoraFiscal-15),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("  Ventas Exentas", precio.format(ventas[2]-anulaciones[2]-devoluciones[2]),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("  Ventas Gravables", precio.format(ventas[0]-anulaciones[0]-devoluciones[0]),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("  Impuesto", precio.format(ventas[1]-anulaciones[1]-devoluciones[1]),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha(crearLineaDeDivision(columnasImpresoraFiscal-15),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Venta Tota_", precio.format(ventas[0]+ventas[2]-(anulaciones[0]+anulaciones[2])-(devoluciones[0]+devoluciones[2])+ventas[1]-anulaciones[1]-devoluciones[1]),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar("Detalle de Formas De Pago", columnasImpresoraFiscal), 0);
		Vector<FormaDePago> formasDePago = ManejoPagosFactory.getInstance().cargarTodasFormasDePago();
		Vector<Integer> transaccionesDeVenta = MediadorBD.obtenerNumeroDeTransacciones(codUsuario, fechaReporte,Sesion.getCaja().getNumero(),Sesion.VENTA);
		Vector<Integer> transaccionesDeAnulacion = MediadorBD.obtenerNumeroDeTransacciones(codUsuario, fechaReporte,Sesion.getCaja().getNumero(),Sesion.ANULACION);
		Vector<Integer> transaccionesDeDevolucion = MediadorBD.obtenerNumeroDeTransacciones(codUsuario, fechaReporte,Sesion.getCaja().getNumero(),Sesion.DEVOLUCION);
		String wherePositivo = "";
		String whereNegativo = "";
		for (int j=0; j<transaccionesDeVenta.size(); j++) {
			if (j==0)
				wherePositivo = "(p.numtransaccion = " + ((Integer)transaccionesDeVenta.elementAt(j)).intValue();
			else
				wherePositivo += " or p.numtransaccion = " + ((Integer)transaccionesDeVenta.elementAt(j)).intValue();
		}
		if (!wherePositivo.equalsIgnoreCase("")) wherePositivo+=")";
		for (int j=0; j<transaccionesDeAnulacion.size(); j++) {
			if (j==0)
				whereNegativo = "(p.numtransaccion = " + ((Integer)transaccionesDeAnulacion.elementAt(j)).intValue();
			else
				whereNegativo += " or p.numtransaccion = " + ((Integer)transaccionesDeAnulacion.elementAt(j)).intValue();
		}
		for (int j=0; j<transaccionesDeDevolucion.size(); j++) {
			if ((j==0)&&(whereNegativo.equals("")))
				whereNegativo = "(p.numtransaccion = " + ((Integer)transaccionesDeDevolucion.elementAt(j)).intValue();
			else
				whereNegativo += " or p.numtransaccion = " + ((Integer)transaccionesDeDevolucion.elementAt(j)).intValue();
		}
		if (!whereNegativo.equals(""))
			whereNegativo += ")";
			
		// Obtenemos el monto dado por vuelto para restárselo al efectivo
		double vuelto = 0;
		if (!wherePositivo.equalsIgnoreCase(""))
			vuelto = MediadorBD.obtenerVueltos(wherePositivo);
			
		// Obtenemos el monto dado por vuelto para restárselo al efectivo
		double vueltoNegativo = 0;
		if (!whereNegativo.equalsIgnoreCase(""))
			vueltoNegativo = MediadorBD.obtenerVueltos(whereNegativo);
			
		// Por cada Forma de Pago
		for (int i=0; i<formasDePago.size(); i++) {
			FormaDePago pago = (FormaDePago)formasDePago.elementAt(i);
			double positivo = 0;
			double negativo = 0;
			if (!wherePositivo.equalsIgnoreCase(""))
				positivo = MediadorBD.obtenerPagosDeTransacciones(wherePositivo,pago.getCodigo());
			if (!whereNegativo.equalsIgnoreCase(""))
				negativo = MediadorBD.obtenerPagosDeTransacciones(whereNegativo,pago.getCodigo());
			if ((pago.getCodigo().equalsIgnoreCase("1111111111"))||(pago.getCodigo().equalsIgnoreCase("01")) || (pago.getCodigo().equalsIgnoreCase("1"))) {
				negativo += vuelto;
				positivo += vueltoNegativo;
			}
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(pago.getNombre(), 0);
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("  POSITIVO",precio.format(positivo),columnasImpresoraFiscal), 0);
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("  NEGATIVO",precio.format(-negativo),columnasImpresoraFiscal), 0);
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("  NETO:   ",precio.format(positivo-negativo),columnasImpresoraFiscal), 0);
		}
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);

		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalleFiscal() - end");
		}
	}

	/**
	 * Método imprimirReporte
	 * 	Realiza la impresion de la factura de Venta indicada como parametro.
	 * 
	 */
	public static void imprimirReporte(String usuarioRep, Date fechaR) throws ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirReporte(String, Date) - start");
		}

		codUsuario = usuarioRep;
		fechaReporte = fechaR;
		nombreUsuario = MediadorBD.obtenerNombreUsuario(codUsuario);
		
		if (nombreUsuario==null) 
			throw new ExcepcionCr("Usuario " + usuarioRep + " Inexistente");
			
		if(Sesion.impresoraFiscal) {
			// Cadenas de secciones de la factura
			
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
			crearEncabezadoFiscal();
			crearDetalleFiscal();
			Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
			
			Sesion.crFiscalPrinterOperations.cortarPapel();
			
			try {
				Sesion.crFiscalPrinterOperations.commit();
			} catch (PrinterNotConnectedException e) {
				logger.error("imprimirReporte(String, Date)", e);
			}
		} else {
			// Esta Implementaccion no maneja Impresora NoFiscal
		}

		if (logger.isDebugEnabled()) {
			logger.debug("imprimirReporte(String, Date) - end");
		}
	}
}
