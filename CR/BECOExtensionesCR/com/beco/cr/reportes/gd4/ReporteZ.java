
/**
 * =============================================================================
 * Proyecto   : CajaRegistradora
 * Paquete    : com.becoblohm.cr.reportes
 * Programa   : ReporteZ.java
 * Creado por : gmartinelli
 * Creado en  : 07-jun-04 14:00
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
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.extensiones.ManejoPagosFactory;
import com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.epa.crprinterdriver.PrinterNotConnectedException;

/** 
 * Descripción: 
 * 		Clase que maneja los perifericos del proyecto de CR. En principio maneja
 * únicamente la impresora. Restaría el manejo de Escaners, Visores, Teclados, etc.
 */
public class ReporteZ extends Reporte {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ReporteZ.class);
	
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
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
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

		String lineaDeDireccion = "";
		if((Sesion.getTienda().getDireccionFiscal() != null)&&(!Sesion.getTienda().getDireccionFiscal().equalsIgnoreCase("")))
			lineaDeDireccion += Sesion.getTienda().getDireccionFiscal();
		if((Sesion.getTienda().getNombreSucursal() != null)&&(!Sesion.getTienda().getNombreSucursal().equalsIgnoreCase("")))
			lineaDeDireccion += " Sucursal:" + Sesion.getTienda().getNombreSucursal() + ".";
		if((Sesion.getTienda().getDireccion() != null)&&(!Sesion.getTienda().getDireccion().equalsIgnoreCase("")))
			lineaDeDireccion += Sesion.getTienda().getDireccion();
		
		if (!lineaDeDireccion.equalsIgnoreCase("")) {
			Vector<String> nuevasLineasDireccion = dividirEnLineas(columnasImpresoraFiscal-3,lineaDeDireccion);
			for (int i=0; i<nuevasLineasDireccion.size(); i++)
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar((String)nuevasLineasDireccion.elementAt(i), columnasImpresoraFiscal), 0);
		}
		
		if ((Sesion.getTienda().getNit()!=null)&&(!Sesion.getTienda().getNit().equalsIgnoreCase("")))
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar("RIF:" + Sesion.getTienda().getRif()  + "   NIT:" + Sesion.getTienda().getNit(), columnasImpresoraFiscal), 0);
		else
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar("RIF:" + Sesion.getTienda().getRif(), columnasImpresoraFiscal), 0);

		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar("Reporte de Cierre de Caja", columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Caja: " + enteroConCeros.format(Sesion.getCaja().getNumero()),"Tienda: " + enteroConCeros.format(Sesion.getTienda().getNumero()),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Fecha: " + fecha.format(Sesion.getFechaSistema()), "Hora: " + hora.format(Sesion.getHoraSistema()), columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);

		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal() - end");
		}
	}
	
	/**
	 * Crea el detalle de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generara el detalle.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
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
		int[] transacciones = MediadorBD.obtenerTransaccionesDiarias(Sesion.getCaja().getFechaUltRepZ(),Sesion.getCaja().getNumero());
		double[] ventas = MediadorBD.obtenerTransacciones(Sesion.getCaja().getFechaUltRepZ(),Sesion.getCaja().getNumero(),Sesion.VENTA);
		double[] anulaciones = MediadorBD.obtenerTransacciones(Sesion.getCaja().getFechaUltRepZ(),Sesion.getCaja().getNumero(),Sesion.ANULACION);
		double[] devoluciones = MediadorBD.obtenerTransacciones(Sesion.getCaja().getFechaUltRepZ(),Sesion.getCaja().getNumero(),Sesion.DEVOLUCION);
		int nroVtas = MediadorBD.obtenerNumTransacciones(Sesion.getCaja().getFechaUltRepZ(),Sesion.VENTA);
		int nroDev = MediadorBD.obtenerNumTransacciones(Sesion.getCaja().getFechaUltRepZ(),Sesion.DEVOLUCION);
		int nroAnul = MediadorBD.obtenerNumTransacciones(Sesion.getCaja().getFechaUltRepZ(),Sesion.ANULACION);

		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar("Contador de Transacciones", columnasImpresoraFiscal), 0);

		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Contador Final: ", String.valueOf(transacciones[1]), columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Contador Inicial: ", "(-)" + transacciones[0], columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Nro. Transacciones: ", String.valueOf(transacciones[1]-transacciones[0]), columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);

		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar("Consolidado de Transacciones", columnasImpresoraFiscal), 0);

		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Vtas Exentas Brutas" , precio.format(ventas[2]),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Vtas Gravables Brutas" , precio.format(ventas[0]),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Tota_ Ventas Brutas" + alinearDerecha("(" + cantidad.format(nroVtas) + ")",6), precio.format(ventas[0]+ventas[2]),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Tot. Imp. Vtas Brutas" , precio.format(ventas[1]),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Anulaciones  " + alinearDerecha("(" + cantidad.format(nroAnul) + ")",11), precio.format(anulaciones[0]+anulaciones[2]),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Devoluciones " + alinearDerecha("(" + cantidad.format(nroDev) + ")",11), precio.format(devoluciones[0]+devoluciones[2]),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Tot. Anul + Dev" + alinearDerecha("(" + cantidad.format(nroAnul + nroDev) + ")",9), precio.format(anulaciones[0]+anulaciones[2] + devoluciones[0]+devoluciones[2]),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Tot. Imp. Asoc. Anul + Dev", precio.format(anulaciones[1]+devoluciones[1]),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha(crearLineaDeDivision(columnasImpresoraFiscal-15),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Venta Neta", precio.format(ventas[0]+ventas[2]-(anulaciones[0]+anulaciones[2])-(devoluciones[0]+devoluciones[2])),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha(crearLineaDeDivision(columnasImpresoraFiscal-15),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("  Ventas Exentas Netas", precio.format(ventas[2]-anulaciones[2]-devoluciones[2]),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("  Ventas Gravables Netas", precio.format(ventas[0]-anulaciones[0]-devoluciones[0]),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("  Impuesto Neto", precio.format(ventas[1]-anulaciones[1]-devoluciones[1]),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha(crearLineaDeDivision(columnasImpresoraFiscal-15),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Venta Tota_ Neta", precio.format(ventas[0]+ventas[2]-(anulaciones[0]+anulaciones[2])-(devoluciones[0]+devoluciones[2])+ventas[1]-anulaciones[1]-devoluciones[1]),columnasImpresoraFiscal), 0);

		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);

		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar("Detalle de Formas De Pago", columnasImpresoraFiscal), 0);

		Vector<FormaDePago> formasDePago = ManejoPagosFactory.getInstance().cargarTodasFormasDePago();
		Vector<Integer> transaccionesDeVenta = MediadorBD.obtenerNumeroDeTransacciones(Sesion.getCaja().getFechaUltRepZ(),Sesion.getCaja().getNumero(),Sesion.VENTA);
		Vector<Integer> transaccionesDeAnulacion = MediadorBD.obtenerNumeroDeTransacciones(Sesion.getCaja().getFechaUltRepZ(),Sesion.getCaja().getNumero(),Sesion.ANULACION);
		Vector<Integer> transaccionesDeDevolucion = MediadorBD.obtenerNumeroDeTransacciones(Sesion.getCaja().getFechaUltRepZ(),Sesion.getCaja().getNumero(),Sesion.DEVOLUCION);
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
			if ((pago.getCodigo().equalsIgnoreCase("1111111111"))||(pago.getCodigo().equalsIgnoreCase("01"))) {
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
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	public static void imprimirReporte() throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirReporte() - start");
		}
		
		if(Sesion.impresoraFiscal) {
			// Cadenas de secciones de la factura
			Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
			//Sesion.crFiscalPrinterOperations.desactivarProximoCorte();
			
			crearEncabezadoFiscal();
			crearDetalleFiscal();
			Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();

			try {
				Sesion.imprimiendoReporteZ=true;
				Sesion.reporteZOK = false;
				Sesion.crFiscalPrinterOperations.commit();
				
				while(Sesion.imprimiendoReporteZ && !Sesion.reporteZOK){
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
					}
				}
				if(Sesion.reporteZOK){
					Sesion.imprimiendoReporteZ=true;
					Sesion.reporteZOK = false;
					Sesion.crFiscalPrinterOperations.reporteZ();
					/*try {
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
					}*/
					Sesion.crFiscalPrinterOperations.cortarPapel();
					Sesion.crFiscalPrinterOperations.commit();
					while(Sesion.imprimiendoReporteZ && !Sesion.reporteZOK){
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
						}
					}
				}
			} catch (PrinterNotConnectedException e) {
				logger.error("imprimirReporte()", e);
			}
			//while (Sesion.crFiscalPrinterOperations.isImprimiendo()) ;
		} else {
			// Esta Implementaccion no maneja Impresora NoFiscal
		}
		if(Sesion.reporteZOK) {
			Sesion.getCaja().setFechaUltRepZ(Sesion.getFechaSistema());
			Sesion.reporteZOK = true;
			Sesion.imprimiendoReporteZ = false;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirReporte() - end");
		}
	}
}
