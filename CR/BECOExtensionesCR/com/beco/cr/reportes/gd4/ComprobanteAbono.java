/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.reportes
 * Programa   : ComprobanteAbono.java
 * Creado por : gmartinelli
 * Creado en  : 07-jun-04 16:03
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

import com.becoblohm.cr.CR;
import com.becoblohm.cr.gui.PantallaVuelto;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstadoVenta;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarservicio.Abono;
import com.becoblohm.cr.manejarservicio.Apartado;
import com.becoblohm.cr.manejarservicio.DetalleServicio;
import com.becoblohm.cr.manejarservicio.ListaRegalos;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.crprinterdriver.PrinterNotConnectedException;

/** 
 * Descripción: 
 * 		Clase que maneja los perifericos del proyecto de CR. En principio maneja
 * únicamente la impresora. Restaría el manejo de Escaners, Visores, Teclados, etc.
 */
public class ComprobanteAbono extends Reporte {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ComprobanteAbono.class);
	
	// Variables necesarias para la impresion
	private static int columnasImpresoraFiscal = 38;
	private static int numAbonoRealizado;
	
	private static void crearEncabezadoFiscal(Apartado apartado) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal(Apartado) - start");
		}
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("FECHA: "+formatoFecha.format(Sesion.getFechaSistema()),"HORA: "+Sesion.getHoraSistema(), columnasImpresoraFiscal),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Sesion.getTienda().getRazonSocial(), 1);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(convertirCadena("Apartado de productos"), 3);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("A B O N O", 1);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		if (apartado.getCliente().getCodCliente()!=null) {
			// Creamos las lineas con los datos del Cliente
			String cliente = "Cliente: ";
			String direccion = "Dirección: ";
			Vector<String> lineasNombre = dividirEnLineas(columnasImpresoraFiscal - 3 - cliente.length(),apartado.getCliente().getNombreCompleto());
			Vector<String> lineasDireccion = dividirEnLineas(columnasImpresoraFiscal - 3 - direccion.length(),apartado.getCliente().getDireccion());
			String RIF = "CI/RIF: " + apartado.getCliente().getCodCliente().replace('N','V');
			String NIT = ((apartado.getCliente().getNit()!=null)&&(!apartado.getCliente().getNit().equalsIgnoreCase("")))
						? "NIT:" + apartado.getCliente().getNit()
						: null;
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(cliente + lineasNombre.elementAt(0), 0);
			for (int i=1; i<lineasNombre.size(); i++) {
				String lineaActual = "";
				for (int j=0;j<cliente.length();j++)
					lineaActual += " ";
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(lineaActual + (String)lineasNombre.elementAt(i), 0);
			}
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(direccion + lineasDireccion.elementAt(0), 0);
			for (int i=1; i<lineasDireccion.size(); i++) {
				String lineaActual = "";
				for (int j=0;j<direccion.length();j++)
					lineaActual += " ";
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(lineaActual + (String)lineasDireccion.elementAt(i), 0);
			}
			if (NIT != null)
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(RIF + "  " + NIT, 0);
			else
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(RIF, 0);
		}
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);		

		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal(Apartado) - end");
		}
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private static void crearEncabezadoFiscal(ListaRegalos lista) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal(Apartado) - start");
		}
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("FECHA: "+formatoFecha.format(Sesion.getFechaSistema()),"HORA: "+Sesion.getHoraSistema(), columnasImpresoraFiscal),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(Sesion.getTienda().getRazonSocial(), 1);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("ABONO LISTA REGALOS", 1);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		if (lista.getCliente()!=null && lista.getCliente().getCodCliente()!=null) {
			// Creamos las lineas con los datos del Cliente
			String cliente = "Invitado: ";
			String direccion = "Dirección: ";
			Vector<String> lineasNombre = dividirEnLineas(columnasImpresoraFiscal - 3 - cliente.length(),lista.getCliente().getNombreCompleto());
			Vector<String> lineasDireccion = dividirEnLineas(columnasImpresoraFiscal - 3 - direccion.length(),lista.getCliente().getDireccion());
			String RIF = "CI/RIF: " + lista.getCliente().getCodCliente().replace('N','V');
			String NIT = ((lista.getCliente().getNit()!=null)&&(!(lista.getCliente().getNit().trim()).equalsIgnoreCase(""))&&(!lista.getCliente().getNit().equals("null")))
						? "NIT: " + lista.getCliente().getNit()
						: null;
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(cliente + lineasNombre.elementAt(0), 0);
			for (int i=1; i<lineasNombre.size(); i++) {
				String lineaActual = "";
				for (int j=0;j<cliente.length();j++)
					lineaActual += " ";
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(lineaActual + (String)lineasNombre.elementAt(i), 0);
			}
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(direccion + lineasDireccion.elementAt(0), 0);
			for (int i=1; i<lineasDireccion.size(); i++) {
				String lineaActual = "";
				for (int j=0;j<direccion.length();j++)
					lineaActual += " ";
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(lineaActual + (String)lineasDireccion.elementAt(i), 0);
			}
			if (NIT != null)
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(RIF + "  " + NIT, 0);
			else
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(RIF, 0);
		}else{
			// Creamos las lineas con los datos del Cliente
			String cliente = "Cliente: ";
			//String direccion = "Dirección: ";
			Vector<String> lineasNombre = dividirEnLineas(columnasImpresoraFiscal - 3 - cliente.length(),lista.getNombreInvitado());
			//Vector lineasDireccion = dividirEnLineas(columnasImpresoraFiscal - 3 - direccion.length(),lista.getCliente().getDireccion());
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(cliente + lineasNombre.elementAt(0), 0);
			for (int i=1; i<lineasNombre.size(); i++) {
				String lineaActual = "";
				for (int j=0;j<cliente.length();j++)
					lineaActual += " ";
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(lineaActual + (String)lineasNombre.elementAt(i), 0);
			}
		}
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);		

		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal(Apartado) - end");
		}
	}
	
	/**
	 * Crea el detalle de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generara el detalle.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	private static void crearDetalleFiscal(Apartado apartado) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalleFiscal(Apartado) - start");
		}

		SimpleDateFormat fechaTrans = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat horaTrans = new SimpleDateFormat("HH:mm:ss");
		DecimalFormat precio = new DecimalFormat("#,##0.00");
		double montoAbonoActual = ((Abono)apartado.getAbonos().lastElement()).getMontoBase();

		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Apartado:" + apartado.getNumServicio(), "Apertura: " + fechaTrans.format(apartado.getFechaServicio()), columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Abono Nro." + numAbonoRealizado, "Cajero: " + ((Abono)apartado.getAbonos().lastElement()).getCodCajero(),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Fecha: " + fechaTrans.format(((Abono)apartado.getAbonos().lastElement()).getFechaAbono()),
						"Hora: " + horaTrans.format(((Abono)apartado.getAbonos().lastElement()).getHoraInicia()),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Monto Apartado",precio.format(apartado.consultarMontoServ()),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Abonos Realizados","(-)" + precio.format(apartado.montoAbonos()-montoAbonoActual),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha(crearLineaDeDivision(17),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Saldo Anterior",precio.format(apartado.consultarMontoServ()-apartado.montoAbonos()+montoAbonoActual),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Abono Actual","(-)" + precio.format(montoAbonoActual),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(alinearDerecha(crearLineaDeDivision(17),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Saldo Actual",precio.format(apartado.consultarMontoServ()-apartado.montoAbonos()),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);

		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalleFiscal(Apartado) - end");
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
	private static void crearDetalleFiscal(ListaRegalos lista) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalleFiscal(Apartado) - start");
		}

		Vector<Abono> detalleAbonos = lista.getDetalleAbonos();

		SimpleDateFormat fechaTrans = new SimpleDateFormat("dd/MM/yyyy");
		DecimalFormat precio = new DecimalFormat("#,##0.00");

		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Lista Nro.:" + lista.getNumServicio(),"Nro. Trans.:" + numAbonoRealizado, columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("Titular: "+lista.getTitular().getNombreCompleto(),0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Apertura:" + fechaTrans.format(lista.getFechaApertura()),"Tienda Apertura:"+lista.getCodTiendaApertura(), columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Tienda Op.:" + lista.getCodTienda(),"Evento:"+lista.getTipoEvento(), columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Fec.Evento:" + fechaTrans.format(lista.getFechaEvento()),"Cajero:" + lista.getCodCajero(),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		
		for(int i=0;i<detalleAbonos.size();i++){
			if(((Abono)detalleAbonos.get(i)).getTipoTransAbono()=='A'){
				double montoAbono = ((Abono)detalleAbonos.get(i)).getMontoBase();
				String codProd = ((Abono)detalleAbonos.get(i)).getProducto().getCodProducto();
				String descProd = ((Abono)detalleAbonos.get(i)).getProducto().getDescripcionCorta();
				String pregular;
				String pfinal;
				if(lista.getCliente()!=null && lista.getCliente().isExento()){
					pregular = precio.format(((Abono)detalleAbonos.get(i)).getProducto().getPrecioRegular());
					pfinal = precio.format(((DetalleServicio)lista.getDetalle(codProd)).getPrecioFinal());
				}else{
					pregular = precio.format(((Abono)detalleAbonos.get(i)).getProducto().getPrecioRegular()*(1+(((Abono)detalleAbonos.get(i)).getProducto().getImpuesto().getPorcentaje()/100)));
					pfinal = precio.format(((DetalleServicio)lista.getDetalle(codProd)).getPrecioFinal()*(1+(((Abono)detalleAbonos.get(i)).getProducto().getImpuesto().getPorcentaje()/100)));
				}
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Cod:"+codProd,descProd,columnasImpresoraFiscal), 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("P.REG: "+pregular,"P.FINAL: "+pfinal,columnasImpresoraFiscal), 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("ABONO PARCIAL",precio.format(montoAbono),columnasImpresoraFiscal), 0);
			}else if(((Abono)detalleAbonos.get(i)).getTipoTransAbono()=='T'){
				double montoAbono = ((Abono)detalleAbonos.get(i)).getMontoBase();
				String codProd = ((Abono)detalleAbonos.get(i)).getProducto().getCodProducto();
				String descProd = ((Abono)detalleAbonos.get(i)).getProducto().getDescripcionCorta();
				String pregular;
				String pfinal;
				if(lista.getCliente()!=null && lista.getCliente().isExento()){
					pregular = precio.format(((Abono)detalleAbonos.get(i)).getProducto().getPrecioRegular());
					pfinal = precio.format(((DetalleServicio)lista.getDetalle(codProd)).getPrecioFinal());
				}else{
					pregular = precio.format(((Abono)detalleAbonos.get(i)).getProducto().getPrecioRegular()*(1+(((Abono)detalleAbonos.get(i)).getProducto().getImpuesto().getPorcentaje()/100)));
					pfinal = precio.format(((DetalleServicio)lista.getDetalle(codProd)).getPrecioFinal()*(1+(((Abono)detalleAbonos.get(i)).getProducto().getImpuesto().getPorcentaje()/100)));
				}				int cant = (int)((Abono)detalleAbonos.get(i)).getCantidad();
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Cod:"+codProd,descProd,columnasImpresoraFiscal), 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("P.REG: "+pregular,"P.FINAL: "+pfinal,columnasImpresoraFiscal), 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("ABONO TOTAL x "+cant,precio.format(montoAbono * (1+(((Abono)detalleAbonos.get(i)).getProducto().getImpuesto().getPorcentaje()/100)) * cant),columnasImpresoraFiscal), 0);
			}else if(((Abono)detalleAbonos.get(i)).getTipoTransAbono()=='L'){
				double montoAbono = ((Abono)detalleAbonos.get(i)).getMontoBase();
				String codProd = "Cod:000000000000";
				String descProd = "ABONO A LISTA";
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar(codProd,descProd,columnasImpresoraFiscal), 0);
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("ABONO A LISTA",precio.format(montoAbono),columnasImpresoraFiscal), 0);
			}
		}
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Monto Abonos:",precio.format(lista.getMontoAbonos()),columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("No se efectuaran devoluciones del mon-",0);
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("to abonado.",0);
		
		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalleFiscal(Apartado) - end");
		}
	}

	/**
	 * Método imprimirComprobate
	 * 	Realiza la impresion de la factura de Venta indicada como parametro.
	 * @param apartado
	 */
	public static void imprimirComprobante(Apartado apartado) {
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirComprobate(Apartado) - start");
		}

		DecimalFormat df = new DecimalFormat("#,##0.00");
		//********
		//Se verifica si la gaveta se abre por impresora o por el visor. Si es por impresora debemos abrir
		//primero la gaveta antes de enviar a imprimir porque si no, se queda la gaveta cerrada hasta que termina la impresion.
		
		if (Sesion.aperturaGavetaPorImpresora) {
			try {
				CR.me.abrirGaveta(false);
			} catch (Exception e){
				logger.error("imprimirFactura(Venta)", e);
			}
		 }
		//********
		if(Sesion.impresoraFiscal) {
			try {
				do{
					if(MaquinaDeEstadoVenta.errorAtencionUsuario){
						MensajesVentanas.aviso("Problema al imprimir el documento.");
					}
				} while(MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir());
								
				// Cadenas de secciones de la factura
				numAbonoRealizado = ((Abono)apartado.getAbonos().lastElement()).getNumAbono();
				Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
				UtilesReportes.crearEncabezadoNoFiscal();
				crearEncabezadoFiscal(apartado);
				crearDetalleFiscal(apartado);
				Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
				Sesion.crFiscalPrinterOperations.cortarPapel();
				MaquinaDeEstadoVenta.setDocumentoNoFiscalPorImprimir(true);
				Sesion.crFiscalPrinterOperations.commit();
			} catch (PrinterNotConnectedException e) {
				logger.error("imprimirComprobate(Apartado)", e);
			}
		} else {
			// Esta Implementaccion no maneja Impresora NoFiscal
		}

		//****Se muestra el vuelto por visor y por pantalla
		// Se muestra el monto del vuelto al cliente por el visor
		double vuelto;
		if(((Abono)apartado.getAbonos().lastElement()).getMontoVuelto()*100 < 1)
			vuelto = 0;
		else
			vuelto = ((Abono)apartado.getAbonos().lastElement()).getMontoVuelto();
		try { CR.crVisor.enviarString("CAMBIO",0,df.format(vuelto),2); }
		catch (Exception e) {
			logger.error("imprimirComprobate(Apartado)", e);
		}
								
		//Se muestra el mensaje del vuelto al cajero
		PantallaVuelto pantallaVuelto = new PantallaVuelto(vuelto, true);
		MensajesVentanas.centrarVentanaDialogo(pantallaVuelto);
		//***		

		if (logger.isDebugEnabled()) {
			logger.debug("imprimirComprobate(Apartado) - end");
		}
	}
	
	/**
	 * Método imprimirComprobate
	 * 	Realiza la impresion de la factura de Abono indicada como parametro.
	 * @param apartado
	 */
	public static void imprimirComprobante(int numTransaccion, double vuelto, ListaRegalos lista) {
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirComprobante(int,double,ListaRegalos) - start");
		}

		DecimalFormat df = new DecimalFormat("#,##0.00");
		//********
		//Se verifica si la gaveta se abre por impresora o por el visor. Si es por impresora debemos abrir
		//primero la gaveta antes de enviar a imprimir porque si no, se queda la gaveta cerrada hasta que termina la impresion.
		
		if (Sesion.aperturaGavetaPorImpresora) {
			try {
				CR.me.abrirGaveta(false);
			} catch (Exception e){
				logger.error("imprimirComprobante(Venta)", e);
			}
		 }

		//****Se muestra el vuelto por visor y por pantalla
		// Se muestra el monto del vuelto al cliente por el visor
		double vueltoTxt;
		if(vuelto < 1)
			vueltoTxt = 0;
		else
			vueltoTxt = vuelto;
		try { CR.crVisor.enviarString("CAMBIO",0,df.format(vueltoTxt),2); }
		catch (Exception e) {
			logger.error("imprimirComprobante(Apartado)", e);
		}
								
		//Se muestra el mensaje del vuelto al cajero
		PantallaVuelto pantallaVuelto = new PantallaVuelto(vueltoTxt, true);
		MensajesVentanas.centrarVentanaDialogo(pantallaVuelto);
		//***	
		
		for(int i=0; i<Sesion.NRO_COMPROBANTES_ABONO_LR; i++) {
			if(Sesion.impresoraFiscal) {
				//Sesion.crFiscalPrinterOperations.activarSlip();
				try {
					do{
						if(MaquinaDeEstadoVenta.errorAtencionUsuario){
							MensajesVentanas.aviso("Problema al imprimir el documento.");
						}
					} while(MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir());
					/*Sesion.crFiscalPrinterOperations.commit();
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Bloque catch generado automáticamente
						e.printStackTrace();
					}*/
					// Cadenas de secciones de la factura
					numAbonoRealizado = numTransaccion;
					Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
					UtilesReportes.crearEncabezadoNoFiscal();
					crearEncabezadoFiscal(lista);
					crearDetalleFiscal(lista);
					Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
					Sesion.crFiscalPrinterOperations.cortarPapel();
					MaquinaDeEstadoVenta.setDocumentoNoFiscalPorImprimir(true);
					Sesion.crFiscalPrinterOperations.commit();
					
				} catch (PrinterNotConnectedException e) {
					logger.error("imprimirComprobante(int,double,ListaRegalos)", e);
				}
			} else {
				// Esta Implementaccion no maneja Impresora NoFiscal
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("imprimirComprobante(Apartado) - end");
		}
	}
}
