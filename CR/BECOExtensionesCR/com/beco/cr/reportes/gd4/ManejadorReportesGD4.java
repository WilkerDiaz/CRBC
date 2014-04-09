/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblomh.cr.extensiones.impl.reportes
 * Programa   : ManejadorReportesGD4.java
 * Creado por : gmartinelli
 * Creado en  : 28-mar-08 09:47
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

import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.beco.cr.reportes.gd4.bonoregalo.ComprobanteBonoRegalo;
import com.beco.cr.reportes.gd4.bonoregalo.FacturaAnulacionBR;
import com.beco.cr.reportes.gd4.bonoregalo.FacturaVentaBR;
import com.beco.cr.reportes.gd4.bonoregalo.ConsolidadoBonoRegalo;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.extensiones.ManejadorReportes;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago;
import com.becoblohm.cr.manejarbr.VentaBR;
import com.becoblohm.cr.manejarservicio.Apartado;
import com.becoblohm.cr.manejarservicio.ListaRegalos;
import com.becoblohm.cr.manejarusuario.Usuario;
import com.becoblohm.cr.manejarventa.Anulacion;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.manejarventa.Devolucion;
import com.becoblohm.cr.manejarventa.Venta;

/** 
 * Descripción: 
 * 		Maneja las instancias para el manejo de Reportes de la Caja Registradora
 */
public class ManejadorReportesGD4 implements ManejadorReportes {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ManejadorReportes.class);

	/*
	 *  Documentos relacionados con Reportes de Control de la CR 
	 */
	public void resetPrinter() {
		if (logger.isDebugEnabled()) 
			logger.debug("resetPrinter(Usuario) - start");
		
		Sesion.crFiscalPrinterOperations.crearDocumento(true);
		Sesion.crFiscalPrinterOperations.resetDocument();
		
		Sesion.crFiscalPrinterOperations.crearDocumento(false);
		Sesion.crFiscalPrinterOperations.resetDocument();
		
		if (logger.isDebugEnabled()) 
			logger.debug("resetPrinter(Usuario) - end");
	}
	
	public void imprimirAperturaCajero(Usuario usuario) {
		if (logger.isDebugEnabled()) 
			logger.debug("imprimirAperturaCajero(Usuario) - start");

		AperturaCajero.imprimirReporte(usuario);
		Sesion.reporteZOK = true;

		if (logger.isDebugEnabled()) 
			logger.debug("imprimirAperturaCajero(Usuario) - end");
	}

	public void imprimirCierreCajero(Usuario usuario) {
		if (logger.isDebugEnabled()) 
			logger.debug("imprimirCierreCajero(Usuario) - start");

		if(Sesion.reporteZOK) 
			CierreCajero.imprimirReporte(usuario);
		 else 
			 Sesion.reporteZOK =true;
			
		if (logger.isDebugEnabled()) 
			logger.debug("imprimirCierreCajero(Usuario) - end");
	}

	/*
	 *  Documentos relacionados con Reportes Fiscales (X, Z)
	 */
	public void imprimirReporteX(String codUsuario, Date fechaReporte) throws ExcepcionCr {
		if (logger.isDebugEnabled()) 
			logger.debug("imprimirReporteX(String, Date) - start");

		ReporteX.imprimirReporte(codUsuario, fechaReporte);

		if (logger.isDebugEnabled()) 
			logger.debug("imprimirReporteX(String, Date) - end");
	}

	public void imprimirReporteZ() throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) 
			logger.debug("imprimirReporteZ() - start");

		ReporteZ.imprimirReporte();

		if (logger.isDebugEnabled()) 
			logger.debug("imprimirReporteZ() - end");
	}

	/*
	 *  Documentos relacionados con Transacciones (Ventas, Anulaciones, Devoluciones) 
	 */
	public void imprimirFacturaVenta(Venta ventaActual, boolean reimpresion, boolean esContribuyenteOrdinario) 
	throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) 
			logger.debug("imprimirFacturaVenta(Venta, boolean, boolean) - start");
		
		FacturaVenta.imprimirFactura(ventaActual, reimpresion, esContribuyenteOrdinario);
		
		if (logger.isDebugEnabled()) 
			logger.debug("imprimirFacturaVenta(Venta, boolean, boolean) - end");
	}

	public void imprimirFacturaAnulacion(Anulacion anulacion) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) 
			logger.debug("imprimirFacturaAnulacion(Anulacion) - start");

		FacturaAnulacion.imprimirFactura(anulacion);

		if (logger.isDebugEnabled()) 
			logger.debug("imprimirFacturaAnulacion(Anulacion) - end");
	}

	public void imprimirFacturaDevolucion(Devolucion devolucion, boolean mostrarVuelto, double condicional, double vuelto) 
	throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) 
			logger.debug("imprimirFacturaDevolucion(Devolucion, boolean, double, double) - start");

		FacturaDevolucion.imprimirFactura(devolucion, mostrarVuelto, condicional, vuelto);
		//FacturaDevolucion.imprimirFactura(devolucion);
		
		if (logger.isDebugEnabled()) 
			logger.debug("imprimirFacturaDevolucion(Devolucion, boolean, double, double) - end");
	}
	
	public void imprimirComprobanteDesctoEmpleado(Cliente cte, String codAutorizante, double monto, int numCopias) {
		if (logger.isDebugEnabled()) 
			logger.debug("imprimirComprobanteDesctoEmpleado(Cliente, String, double, int) - start");

		//ComprobanteDesctoEmpleado.imprimirComprobate(cte, codAutorizante, monto, numCopias);

		if (logger.isDebugEnabled()) 
			logger.debug("imprimirComprobanteDesctoEmpleado(Cliente, String, double, int) - end");
	}

	/*
	 *  Documentos relacionados con el Procesamiento de Pagos 
	 */
	public void imprimirCheque(double monto, Date fecha) {
		if (logger.isDebugEnabled())
			logger.debug("imprimirCheque(double, Date) - start");

		//ImprimirCheque.imprimirReporte(monto, fecha);
		
		if (logger.isDebugEnabled()) 
			logger.debug("imprimirCheque(double, Date) - end");
	}
	
	public void imprimirVoucherPago(Cliente cte, String codAutorizante, FormaDePago fPago, double monto, int numCopias, int numTrans) {
		if (logger.isDebugEnabled())
			logger.debug("imprimirVoucherPago(Cliente, String, FormaDePago, double, int, int) - start");

		VoucherPago.imprimirComprobate(cte, codAutorizante, fPago, monto, numCopias, numTrans);

		if (logger.isDebugEnabled()) 
			logger.debug("imprimirVoucherPago(Cliente, String, FormaDePago, double, int, int) - end");
	}
	
	/*
	 * Documentos relacionados a Servicios de Apartados / Lista de Regalos
	 */
	public void imprimirComprobanteDeApartado(Apartado apartado) {
		if (logger.isDebugEnabled())
			logger.debug("imprimirComprobanteDeApartado(Apartado) - start");

		ComprobanteDeApartado.imprimirComprobante(apartado);

		if (logger.isDebugEnabled()) 
			logger.debug("imprimirComprobanteDeApartado(Apartado) - end");
	}
	
	public void imprimirEtiquetasApartado(Apartado apartado, int nroEtiquetas) {
		if (logger.isDebugEnabled())
			logger.debug("imprimirEtiquetasApartado(Apartado, int) - start");

		ComprobanteDeApartado.imprimirEtiquetas(apartado, nroEtiquetas);

		if (logger.isDebugEnabled())
			logger.debug("imprimirComprobanteDeApartado(Apartado, int) - start");
	}
	
	public void imprimirComprobanteAbono(Apartado apartado) {
		if (logger.isDebugEnabled())
			logger.debug("imprimirComprobanteAbono(Apartado) - start");
		ComprobanteAbono.imprimirComprobante(apartado);
		if (logger.isDebugEnabled()) 
			logger.debug("imprimirComprobanteAbono(Apartado) - end");
	}
	
	public void imprimirComprobanteAbono(int numTransaccion, double vuelto, ListaRegalos lista) {
		if (logger.isDebugEnabled())
			logger.debug("imprimirComprobanteAbono(int, double, ListaRegalos) - start");

		ComprobanteAbono.imprimirComprobante(numTransaccion, vuelto, lista);

		if (logger.isDebugEnabled()) 
			logger.debug("imprimirComprobanteAbono(int, double, ListaRegalos) - end");
	}
	
	public void imprimirCancelacionApartadoPedidoEspecial(Apartado apartado, boolean apartadoCancelado) {
		if (logger.isDebugEnabled())
			logger.debug("imprimirCancelacionApartadoPedidoEspecial(Apartado, boolean) - start");
		
		CancelacionApartadoPedidoEspecial.imprimirComprobate(apartado, apartadoCancelado);

		if (logger.isDebugEnabled())
			logger.debug("imprimirCancelacionApartadoPedidoEspecial(Apartado, boolean) - start");
	}

	public void imprimirComprobanteAnulacionApartado(Apartado apartado, double totalAbonosActivos) {
		if (logger.isDebugEnabled())
			logger.debug("imprimirComprobanteAnulacionApartado(Apartado, double) - start");
		
		ComprobanteAnulacionApartado.imprimirComprobante(apartado, totalAbonosActivos);

		if (logger.isDebugEnabled()) 
			logger.debug("imprimirComprobanteAnulacionApartado(Apartado, double) - end");
	}
	
	public void imprimirAnulacionDeAbono(Apartado apartado, int renglonAbono) {
		if (logger.isDebugEnabled())
			logger.debug("imprimirAnulacionDeAbono(Apartado, int) - start");

		AnulacionDeAbono.imprimirComprobate(apartado, renglonAbono);

		if (logger.isDebugEnabled()) 
			logger.debug("imprimirAnulacionDeAbono(Apartado, int) - end");
	}

	public void imprimirAbonosAnterioresLR(ListaRegalos lista) {
		if (logger.isDebugEnabled())
			logger.debug("imprimirAbonosAnterioresLR(ListaRegalos) - start");

		AbonosAnterioresLR.imprimirComprobante(lista);

		if (logger.isDebugEnabled()) 
			logger.debug("imprimirAbonosAnterioresLR(ListaRegalos) - end");
	}
	
	/*
	 * Documentos No Fiscales de Despacho y Entrega
	 */
	public void imprimirNotaDeDespacho(Venta ventaActual, int numServ) {
		if (logger.isDebugEnabled())
			logger.debug("imprimirNotaDeDespacho(Venta, int) - start");

		NotaDeDespacho.imprimirComprobante(ventaActual, numServ);

		if (logger.isDebugEnabled()) 
			logger.debug("imprimirNotaDeDespacho(Venta, int) - end");
	}

	public void imprimirNotaDeEntrega(Venta ventaActual, int numServ, String dirEntrega) {
		if (logger.isDebugEnabled())
			logger.debug("imprimirNotaDeEntrega(Venta, int, String) - start");

		NotaDeEntrega.imprimirComprobante(ventaActual, numServ, dirEntrega);

		if (logger.isDebugEnabled()) 
			logger.debug("imprimirNotaDeEntrega(Venta, int, String) - end");
	}

	/*
	 * Documentos No Fiscales de Bonos Regalo
	 */
	public void imprimirComprobanteBonoRegalo(VentaBR venta, boolean duplicado, boolean mostrarVuelto, boolean abrirGaveta) {
		if (logger.isDebugEnabled())
			logger.debug("imprimirComprobanteBonoRegalo(VentaBR venta, boolean duplicado, boolean mostrarVuelto) - start");

		ComprobanteBonoRegalo.imprimirComprobante(venta, duplicado, mostrarVuelto, abrirGaveta);

		if (logger.isDebugEnabled()) 
			logger.debug("imprimirComprobanteBonoRegalo(VentaBR venta, boolean duplicado, boolean mostrarVuelto) - end");
	}

	public void imprimirFacturaBR(VentaBR ventaBR, boolean abrirGaveta) throws BaseDeDatosExcepcion, ConexionExcepcion {
		FacturaVentaBR.imprimirFactura(ventaBR, abrirGaveta);
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public void imprimirConsolidadoBR(int nroComprobanteCP, Vector<Vector<Object>> fPagos, Usuario usuario, Date fecha) {
		if (logger.isDebugEnabled())
			logger.debug("imprimirConsolidadoBR() - start");

		ConsolidadoBonoRegalo.imprimirComprobante(nroComprobanteCP, fPagos, usuario, fecha);
		
		if (logger.isDebugEnabled()) 
			logger.debug("imprimirConsolidadoBR() - end");
	}

	public void imprimirAnulacionBR(VentaBR ventaBR, Cliente clienteAnterior, boolean mostrarVuelto, boolean abrirGaveta) throws BaseDeDatosExcepcion, ConexionExcepcion {
		FacturaAnulacionBR.imprimirFactura(ventaBR, clienteAnterior, mostrarVuelto, abrirGaveta);
		
	}
}
