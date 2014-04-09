
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
package com.becoblohm.cr.extensiones.impl.reportes;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.gui.VentanaEspera;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.extensiones.ManejoPagosFactory;
import com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.epa.crprinterdriver.PrinterNotConnectedException;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;

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
	
	// Variables necesarias para la impresion
	private static int columnasImpresora = 44;
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
	private static void crearEncabezado() {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezado() - start");
		}

		SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");
		DecimalFormat enteroConCeros = new DecimalFormat("000");
		
		Sesion.crPrinterOperations.enviarString(convertirCadena(crearLineaDeDivision(columnasImpresora,'*')+"\n"));
		Sesion.crPrinterOperations.activarFuenteImpresionNegrita();
		Sesion.crPrinterOperations.alinear(1);
		Sesion.crPrinterOperations.enviarString(convertirCadena(Sesion.getTienda().getRazonSocial()+"\n"));
		Sesion.crPrinterOperations.initializarPrinter();
		Sesion.crPrinterOperations.enviarString(convertirCadena(crearLineaDeDivision(columnasImpresora,'*')+"\n"));
		Sesion.crPrinterOperations.alinear(1);
		String lineaDeDireccion = "";
		if((Sesion.getTienda().getDireccionFiscal() != null)&&(!Sesion.getTienda().getDireccionFiscal().equalsIgnoreCase("")))
			lineaDeDireccion += Sesion.getTienda().getDireccionFiscal();
		if((Sesion.getTienda().getNombreSucursal() != null)&&(!Sesion.getTienda().getNombreSucursal().equalsIgnoreCase("")))
			lineaDeDireccion += " Sucursal:" + Sesion.getTienda().getNombreSucursal() + ".";
		if((Sesion.getTienda().getDireccion() != null)&&(!Sesion.getTienda().getDireccion().equalsIgnoreCase("")))
			lineaDeDireccion += Sesion.getTienda().getDireccion();
		
		if (!lineaDeDireccion.equalsIgnoreCase("")) {
			Vector<String> nuevasLineasDireccion = dividirEnLineas(columnasImpresora-3,lineaDeDireccion);
			for (int i=0; i<nuevasLineasDireccion.size(); i++)
				Sesion.crPrinterOperations.enviarString(convertirCadena((String)nuevasLineasDireccion.elementAt(i)+"\n"));
		}
		
		
		/*
		 * Que tipo de factura estamos utlizando? Dependiendo de la respuesta tomamos la desicion de 
		 * colocar la info de formas distinta segun sea el pais.
		 */
		
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
			logger.error("crearEncabezado()", e);
		} catch (UnidentifiedPreferenceException e) {
			logger.error("crearEncabezado()", e);
		}
						
		Sesion.crPrinterOperations.alinear(0);
		Sesion.crPrinterOperations.enviarString(convertirCadena(crearLineaDeDivision(columnasImpresora)+"\n"));
		
		Sesion.crPrinterOperations.alinear(1);
		Sesion.crPrinterOperations.enviarString(convertirCadena("Reporte de Cierre de Caja\n"));
		Sesion.crPrinterOperations.alinear(0);
		Sesion.crPrinterOperations.enviarString(convertirCadena(crearLineaDeDivision(columnasImpresora)+"\n"));
		Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("Caja: " + enteroConCeros.format(Sesion.getCaja().getNumero()),"Tienda: " + enteroConCeros.format(Sesion.getTienda().getNumero()),columnasImpresora)+"\n"));
		Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("Fecha: " + fecha.format(Sesion.getFechaSistema()), "Hora: " + hora.format(Sesion.getHoraSistema()), columnasImpresora)+"\n"));
		Sesion.crPrinterOperations.enviarString(convertirCadena(crearLineaDeDivision(columnasImpresora)+"\n"));

		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezado() - end");
		}
	}
	
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
	private static void crearDetalle() {
		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalle() - start");
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

		Sesion.crPrinterOperations.alinear(1);
		Sesion.crPrinterOperations.enviarString(convertirCadena("Contador de Transacciones\n"));
		Sesion.crPrinterOperations.alinear(0);
		Sesion.crPrinterOperations.enviarString(convertirCadena(crearLineaDeDivision(columnasImpresora)+"\n"));
		Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("Contador Final: ", String.valueOf(transacciones[1]), columnasImpresora)+"\n"));
		Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("Contador Inicial: ", "(-)" + transacciones[0], columnasImpresora)+"\n"));
		Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("Nro. Transacciones: ", String.valueOf(transacciones[1]-transacciones[0]), columnasImpresora)+"\n"));
		Sesion.crPrinterOperations.enviarString(convertirCadena(crearLineaDeDivision(columnasImpresora)+"\n"));
		Sesion.crPrinterOperations.alinear(1);
		Sesion.crPrinterOperations.enviarString(convertirCadena("Consolidado de Transacciones\n"));
		Sesion.crPrinterOperations.alinear(0);
		Sesion.crPrinterOperations.enviarString(convertirCadena(crearLineaDeDivision(columnasImpresora)+"\n"));
		Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("Vtas Exentas Brutas" , precio.format(ventas[2]),columnasImpresora)+"\n"));
		Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("Vtas Gravables Brutas" , precio.format(ventas[0]),columnasImpresora)+"\n"));
		Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("Total Ventas Brutas" + alinearDerecha("(" + cantidad.format(nroVtas) + ")",6), precio.format(ventas[0]+ventas[2]),columnasImpresora)+"\n"));
		Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("Tot. Imp. Vtas Brutas" , precio.format(ventas[1]),columnasImpresora)+"\n"));
		Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("Anulaciones  " + alinearDerecha("(" + cantidad.format(nroAnul) + ")",11), precio.format(anulaciones[0]+anulaciones[2]),columnasImpresora)+"\n"));
		Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("Devoluciones " + alinearDerecha("(" + cantidad.format(nroDev) + ")",11), precio.format(devoluciones[0]+devoluciones[2]),columnasImpresora)+"\n"));
		Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("Tot. Anul + Dev" + alinearDerecha("(" + cantidad.format(nroAnul + nroDev) + ")",9), precio.format(anulaciones[0]+anulaciones[2] + devoluciones[0]+devoluciones[2]),columnasImpresora)+"\n"));
		Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("Tot. Imp. Asoc. Anul + Dev", precio.format(anulaciones[1]+devoluciones[1]),columnasImpresora)+"\n"));
		Sesion.crPrinterOperations.enviarString(convertirCadena(alinearDerecha(crearLineaDeDivision(columnasImpresora-15),columnasImpresora)+"\n"));
		Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("Venta Neta", precio.format(ventas[0]+ventas[2]-(anulaciones[0]+anulaciones[2])-(devoluciones[0]+devoluciones[2])),columnasImpresora)+"\n"));
		Sesion.crPrinterOperations.enviarString(convertirCadena(alinearDerecha(crearLineaDeDivision(columnasImpresora-15),columnasImpresora)+"\n"));
		Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("  Ventas Exentas Netas", precio.format(ventas[2]-anulaciones[2]-devoluciones[2]),columnasImpresora)+"\n"));
		Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("  Ventas Gravables Netas", precio.format(ventas[0]-anulaciones[0]-devoluciones[0]),columnasImpresora)+"\n"));
		Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("  Impuesto Neto", precio.format(ventas[1]-anulaciones[1]-devoluciones[1]),columnasImpresora)+"\n"));
		Sesion.crPrinterOperations.enviarString(convertirCadena(alinearDerecha(crearLineaDeDivision(columnasImpresora-15),columnasImpresora)+"\n"));
		Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("Venta Total Neta", precio.format(ventas[0]+ventas[2]-(anulaciones[0]+anulaciones[2])-(devoluciones[0]+devoluciones[2])+ventas[1]-anulaciones[1]-devoluciones[1]),columnasImpresora)+"\n"));
		Sesion.crPrinterOperations.enviarString(convertirCadena(crearLineaDeDivision(columnasImpresora)+"\n"));

		
		Sesion.crPrinterOperations.alinear(1);
		Sesion.crPrinterOperations.enviarString(convertirCadena("Detalle de Formas De Pago\n"));
		Sesion.crPrinterOperations.alinear(0);
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
			if ((pago.getCodigo().equalsIgnoreCase("1111111111")) ||(pago.getCodigo().equalsIgnoreCase("01")) ||(pago.getCodigo().equalsIgnoreCase("1"))) {
				negativo += vuelto;
				positivo += vueltoNegativo;
			}
			Sesion.crPrinterOperations.enviarString(convertirCadena(pago.getNombre()+"\n"));		
			try {
				if(pago.getTipo()==17){
					double dolares = positivo / MediadorBD.obtenerCambioDelDia();
					double cambioDelDia = MediadorBD.obtenerCambioDelDia();
					Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("  TOTAL EN DOLARES",precio.format(dolares),columnasImpresora)+"\n"));
					Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("  TIPO DE CAMBIO",precio.format(cambioDelDia),columnasImpresora)+"\n"));
					Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("  POSITIVO",precio.format(positivo),columnasImpresora)+"\n"));
					Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("  NEGATIVO",precio.format(-negativo),columnasImpresora)+"\n"));
					Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("  NETO:   ",precio.format(positivo-negativo),columnasImpresora)+"\n"));					
				}
				else{
					Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("  POSITIVO",precio.format(positivo),columnasImpresora)+"\n"));
					Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("  NEGATIVO",precio.format(-negativo),columnasImpresora)+"\n"));
					Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("  NETO:   ",precio.format(positivo-negativo),columnasImpresora)+"\n"));
				}
				
			} catch (Exception e) {
				logger.error("crearDetalle()", e);
			}
		}
		Sesion.crPrinterOperations.enviarString(convertirCadena(crearLineaDeDivision(columnasImpresora)+"\n"));

		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalle() - end");
		}
	}
	
	/**
	 * Crea el pie de pagina de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generara el pie de Pagina.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private static void crearPieDePagina() {
		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePagina() - start");
		}

		//Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("MH",Sesion.getCaja().getSerial(),columnasImpresora)+"\n");
		Sesion.crPrinterOperations.activarFuenteImpresionNegrita();
		Sesion.crPrinterOperations.alinear(1);
		Sesion.crPrinterOperations.enviarString(convertirCadena(Sesion.getTienda().getRazonSocial()+"\n"));
		Sesion.crPrinterOperations.initializarPrinter();
		Sesion.crPrinterOperations.alinear(1);
		String lineaDeDireccion = "";
		if((Sesion.getTienda().getDireccionFiscal() != null)&&(!Sesion.getTienda().getDireccionFiscal().equalsIgnoreCase("")))
			lineaDeDireccion += Sesion.getTienda().getDireccionFiscal();
		if((Sesion.getTienda().getNombreSucursal() != null)&&(!Sesion.getTienda().getNombreSucursal().equalsIgnoreCase("")))
			lineaDeDireccion += " Sucursal:" + Sesion.getTienda().getNombreSucursal() + ".";
		if((Sesion.getTienda().getDireccion() != null)&&(!Sesion.getTienda().getDireccion().equalsIgnoreCase("")))
			lineaDeDireccion += Sesion.getTienda().getDireccion();
		
		if (!lineaDeDireccion.equalsIgnoreCase("")) {
			Vector<String> nuevasLineasDireccion = dividirEnLineas(columnasImpresora-3,lineaDeDireccion);
			for (int i=0; i<nuevasLineasDireccion.size(); i++)
				Sesion.crPrinterOperations.enviarString(convertirCadena((String)nuevasLineasDireccion.elementAt(i)+"\n"));
		}
		
		
		/*
		 * Que tipo de factura estamos utlizando? Dependiendo de la respuesta tomamos la desicion de 
		 * colocar la info de formas distinta segun sea el pais.
		 */
		
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
			logger.error("crearPieDePagina()", e);
		} catch (UnidentifiedPreferenceException e) {
			logger.error("crearPieDePagina()", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePagina() - end");
		}
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
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Sesion.getTienda().getRazonSocial(), 1);
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
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Total Ventas Brutas" + alinearDerecha("(" + cantidad.format(nroVtas) + ")",6), precio.format(ventas[0]+ventas[2]),columnasImpresoraFiscal), 0);
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
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Venta Total Neta", precio.format(ventas[0]+ventas[2]-(anulaciones[0]+anulaciones[2])-(devoluciones[0]+devoluciones[2])+ventas[1]-anulaciones[1]-devoluciones[1]),columnasImpresoraFiscal), 0);

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
			Sesion.crFiscalPrinterOperations.desactivarProximoCorte();
			
			crearEncabezadoFiscal();
			crearDetalleFiscal();
			Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();

			try {
				Sesion.crFiscalPrinterOperations.commit();
				if (InitCR.iniciando)
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						logger.error("imprimirReporte()", e);
					}
				else
					VentanaEspera.esperar(30000);
				Sesion.crFiscalPrinterOperations.reporteZ();
				Sesion.crFiscalPrinterOperations.commit();
			} catch (PrinterNotConnectedException e) {
				logger.error("imprimirReporte()", e);
			}
			while (Sesion.crFiscalPrinterOperations.isImprimiendo()) ;
		} else {
			// Cadenas de secciones de la factura
			Sesion.crPrinterOperations.limpiarBuffer();
			Sesion.crPrinterOperations.initializarPrinter();
			
			crearEncabezado();
			crearDetalle();
			crearPieDePagina();
			Sesion.crPrinterOperations.cortarPapel();
			
			//Sesion.crPrinterOperations.abrirPuertoImpresora();
			try {
				Sesion.crPrinterOperations.commit();
			} catch (PrinterNotConnectedException e) {
				logger.error("imprimirReporte()", e); 
			}
			//Sesion.crPrinterOperations.cerrarPuertoImpresora();
		}
		Sesion.getCaja().setFechaUltRepZ(Sesion.getFechaSistema());

		if (logger.isDebugEnabled()) {
			logger.debug("imprimirReporte() - end");
		}
	}
}
