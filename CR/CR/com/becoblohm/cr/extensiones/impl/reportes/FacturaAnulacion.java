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
package com.becoblohm.cr.extensiones.impl.reportes;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.gui.PantallaVuelto;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.manejarventa.Anulacion;
import com.becoblohm.cr.manejarventa.DetalleTransaccion;
import com.becoblohm.cr.manejarventa.Impuesto;
import com.becoblohm.cr.mediadoresbd.BaseDeDatosVenta;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.utiles.Control;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.crprinterdriver.PrinterNotConnectedException;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;

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
	
	// Variables necesarias para la impresion
	private static int columnasImpresora = 44;
	private static int columnasImpresoraFiscal = 40;	
	private static float artVendidos;
	private static Vector<Vector<Object>> impuestos;
	
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
	private static void crearEncabezado(Anulacion anulacion) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezado(Anulacion) - start");
		}

		SimpleDateFormat fechaTrans = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat horaTrans = new SimpleDateFormat("HH:mm:ss");
		DecimalFormat enteroConCeros = new DecimalFormat("000");
		DecimalFormat numFactura = new DecimalFormat("00000000000");
		
		enviarItemDuplicado(convertirCadena(crearLineaDeDivision(columnasImpresora)+"\n"));
		Sesion.crPrinterOperations.activarFuenteImpresionNegrita();
		Sesion.crPrinterOperations.alinear(1);
		enviarItemDuplicado(convertirCadena("A N U L A C I O N\n"));
		Sesion.crPrinterOperations.initializarPrinter();
		enviarItemDuplicado(convertirCadena(crearLineaDeDivision(columnasImpresora)+"\n"));

		Sesion.crPrinterOperations.activarFuenteImpresionNegrita();
		Sesion.crPrinterOperations.alinear(1);
		Sesion.crPrinterOperations.enviarString(convertirCadena(Sesion.getTienda().getRazonSocial()+"\n"));
		Sesion.crPrinterOperations.initializarPrinter();
		
		String lineaDeDireccion = "";
		if((Sesion.getTienda().getDireccionFiscal() != null)&&(!Sesion.getTienda().getDireccionFiscal().equalsIgnoreCase("")))
			lineaDeDireccion += Sesion.getTienda().getDireccionFiscal();
		if((Sesion.getTienda().getNombreSucursal() != null)&&(!Sesion.getTienda().getNombreSucursal().equalsIgnoreCase("")))
			lineaDeDireccion += " Sucursal:" + Sesion.getTienda().getNombreSucursal() + ".";
		if((Sesion.getTienda().getDireccion() != null)&&(!Sesion.getTienda().getDireccion().equalsIgnoreCase("")))
			lineaDeDireccion += Sesion.getTienda().getDireccion();
		
		Sesion.crPrinterOperations.alinear(1);
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
			logger.error("crearEncabezado(Anulacion)", e);
		} catch (UnidentifiedPreferenceException e) {
			logger.error("crearEncabezado(Anulacion)", e);
		}
				
		Sesion.crPrinterOperations.alinear(0);
		enviarItemDuplicado(convertirCadena(crearLineaDeDivision(columnasImpresora)+"\n"));
		
		// Si existe Cliente en la Venta
		if (anulacion.getCliente().getCodCliente()!=null) {
			// Creamos las lineas con los datos del Cliente
			String cliente = "Cliente: ";
			String direccion = "Dirección: ";
			Vector<String> lineasNombre = dividirEnLineas(columnasImpresora - 3 - cliente.length(),anulacion.getCliente().getNombreCompleto());
			Vector<String> lineasDireccion = dividirEnLineas(columnasImpresora - 3 - direccion.length(),anulacion.getCliente().getDireccion());
			String RIF="";
			
			try {
				if(InitCR.preferenciasCR.getConfigStringForParameter("sistema","estiloFactura").equals("CR")){
					RIF = "IDENTIFICACION: " + anulacion.getCliente().getCodCliente();
				}
				else{
					RIF = "CI/RIF: " + anulacion.getCliente().getCodCliente();
				}
			} catch (NoSuchNodeException e) {
				logger.error("crearEncabezado(Anulacion)", e);
			} catch (UnidentifiedPreferenceException e) {
				logger.error("crearEncabezado(Anulacion)", e);
			}
			
			String NIT = ((anulacion.getCliente().getNit()!=null)&&(!anulacion.getCliente().getNit().equalsIgnoreCase("")))
						? "NIT:" + anulacion.getCliente().getNit()
						: null;
			enviarItemDuplicado(convertirCadena(cliente + lineasNombre.elementAt(0)+"\n"));
			for (int i=1; i<lineasNombre.size(); i++) {
				String lineaActual = "";
				for (int j=0;j<cliente.length();j++)
					lineaActual += " ";
				enviarItemDuplicado(convertirCadena(lineaActual + (String)lineasNombre.elementAt(i)+"\n"));
			}
			enviarItemDuplicado(convertirCadena(direccion + lineasDireccion.elementAt(0)+"\n"));
			for (int i=1; i<lineasDireccion.size(); i++) {
				String lineaActual = "";
				for (int j=0;j<direccion.length();j++)
					lineaActual += " ";
				enviarItemDuplicado(convertirCadena(lineaActual + (String)lineasDireccion.elementAt(i)+"\n"));
			}
			if (NIT != null)
				enviarItemDuplicado(convertirCadena(RIF + "  " + NIT+"\n"));
			else
				enviarItemDuplicado(convertirCadena(RIF+"\n"));
			enviarItemDuplicado(convertirCadena(crearLineaDeDivision(columnasImpresora)+"\n"));
		}
		
		enviarItemDuplicado(convertirCadena(justificar("Cajero:" + Sesion.getUsuarioActivo().getNumFicha(), "POS: " + enteroConCeros.format(Sesion.getCaja().getNumero()), columnasImpresora)+"\n"));
		String hora = horaTrans.format(anulacion.getHoraFin());
		String fecha = fechaTrans.format(anulacion.getFechaTrans());
		String transaccion = "Tr:" + numFactura.format(anulacion.getNumTransaccion()); 
		enviarItemDuplicado(convertirCadena(
			justificar(hora	+ centrar(fecha, columnasImpresora - transaccion.length() - hora.length()),
					transaccion, columnasImpresora)+"\n"));
		Sesion.crPrinterOperations.activarFuenteImpresionNegrita();
		Sesion.crPrinterOperations.alinear(1);
		enviarItemDuplicado(convertirCadena("ANULA TR. " + numFactura.format(anulacion.getVentaOriginal().getNumTransaccion()) + "\n"));

		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezado(Anulacion) - end");
		}
	}
	
	
	
	private static void enviarItemDuplicado(String cadena){
		if (logger.isDebugEnabled()) {
			logger.debug("enviarItemDuplicado(String) - start");
		}
		
		Sesion.crPrinterOperations.activarTicketCliente();
		Sesion.crPrinterOperations.enviarString(cadena);
		Sesion.crPrinterOperations.activarReciboAuditoria();
		Sesion.crPrinterOperations.enviarString(cadena);
		Sesion.crPrinterOperations.activarTicketCliente();
		
		if (logger.isDebugEnabled()) {
			logger.debug("enviarItemDuplicado(String) - end");
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
	* Se comentaron variables no usadas
	* Fecha: agosto 2011
	*/
	private static void crearDetalle(Anulacion anulacion, Vector<Integer> correlativosOrd) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalle(Anulacion, Vector) - start");
		}

		DecimalFormat precio = new DecimalFormat("#,##0.00");
		DecimalFormat cantidad = new  DecimalFormat("#0.00");
		DetalleTransaccion detalleActual;
		
		Sesion.crPrinterOperations.initializarPrinter();
		enviarItemDuplicado(convertirCadena(crearLineaDeDivision(columnasImpresora)));
		// Colocamos el tipo de Moneda Base Justificado a la Derecha
		//enviarItemDuplicado(convertirCadena(alinearDerecha(Sesion.getTienda().getMonedaBase() != null ? Sesion.getTienda().getMonedaBase() : "", columnasImpresora)+"\n"));
		
		impuestos = new Vector<Vector<Object>>();
		artVendidos = 0;

		// Para cada detalle de la venta
		for (int i=0; i<correlativosOrd.size(); i++) {
			int posicion = ((Integer)correlativosOrd.elementAt(i)).intValue();
			detalleActual = (DetalleTransaccion)anulacion.getDetallesTransaccion().elementAt(posicion - 1);
			if (Sesion.isUtilizarVendedor())
				enviarItemDuplicado(convertirCadena(detalleActual.getCodVendedor()+"\n"));
				
			int longitudCodigo = Sesion.getLongitudCodigoInterno();
			if (!(longitudCodigo > -1))
				longitudCodigo = Control.getLONGITUD_CODIGO();
				
			//Se chequea la longitud del código del producto para que salga en el reporte igual que como en la interfaz
			String codProd = detalleActual.getProducto().getCodProducto().toString();
			int longitud = codProd.length();
			if ((Sesion.getLongitudCodigoInterno()>-1)&&(longitud>Sesion.getLongitudCodigoInterno())) {
				codProd = codProd.substring(codProd.length()-Sesion.getLongitudCodigoInterno()); 
				codProd = centrar(codProd, Control.getLONGITUD_CODIGO());
				String codP = codProd;
				for (int j=0;j<Control.getLONGITUD_CODIGO() - codProd.length();j++) {
					codP = codP + " "; 
				}
				codProd = codP;
			}
			
			enviarItemDuplicado(convertirCadena(justificar(alinearDerecha(codProd,longitudCodigo) + " " 
							+ detalleActual.getProducto().getDescripcionCorta(), "1 " + detalleActual.getProducto().getAbreviadoUnidadVenta(),columnasImpresora-3)+"\n"));
			if (!detalleActual.getCondicionVenta().equals(Sesion.condicionNormal)) {
				// Si se ha aplicado alguna rebaja (Promocion, Empaque, Cambio de Precio) se especifica
				enviarItemDuplicado(convertirCadena(" PR:" + precio.format(detalleActual.getProducto().getPrecioRegular())
								+ " " + detalleActual.getCondicionVenta() + ":" + precio.format(detalleActual.getPrecioFinal())+"\n"));
			}

			// Almacenamos el impuesto que se aplicó en este renglon
			int k;
			//String numImpuesto = "";
			for (k=0; k<impuestos.size(); k++) {
				if (((Impuesto)(impuestos.elementAt(k)).elementAt(0)).getCodImpuesto().equals(((Impuesto)detalleActual.getProducto().getImpuesto()).getCodImpuesto())) {
					double montoAnterior = ((Double)(impuestos.elementAt(k)).elementAt(1)).doubleValue();
					double montoNuevo = montoAnterior + (detalleActual.getMontoImpuesto()*detalleActual.getCantidad());
					(impuestos.elementAt(k)).set(1,new Double(montoNuevo));
					break;
				}
			}
			if (k == impuestos.size()) {
				Vector<Object> nuevoImpuesto = new Vector<Object>();
				nuevoImpuesto.addElement(detalleActual.getProducto().getImpuesto());
				nuevoImpuesto.addElement(new Double(detalleActual.getMontoImpuesto()*detalleActual.getCantidad()));
				impuestos.addElement(nuevoImpuesto);;
			}
			//numImpuesto = String.valueOf(k+1);			
			int codImpuesto = Integer.valueOf(detalleActual.getProducto().getImpuesto().getCodImpuesto()).intValue();
			if(detalleActual.getMontoImpuesto()==0.0){
				enviarItemDuplicado(convertirCadena(alinearDerecha(cantidad.format(detalleActual.getCantidad()),9)
						+ alinearDerecha(precio.format(detalleActual.getPrecioFinal()), 16)
						+ alinearDerecha(precio.format(detalleActual.getCantidad() * detalleActual.getPrecioFinal()), 16)
						+ " E\n"));						
			}
			else{
				enviarItemDuplicado(convertirCadena(alinearDerecha(cantidad.format(detalleActual.getCantidad()),9)
						+ alinearDerecha(precio.format(detalleActual.getPrecioFinal()), 16)
						+ alinearDerecha(precio.format(detalleActual.getCantidad() * detalleActual.getPrecioFinal()), 16)
						+ " G" + codImpuesto+"\n"));						
			}
			artVendidos += detalleActual.getCantidad();
		}
		enviarItemDuplicado(convertirCadena(crearLineaDeDivision(columnasImpresora)+"\n"));

		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalle(Anulacion, Vector) - end");
		}
	}
	
	/**
	 * Crea las lineas de los totales de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generaran los totales.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se eliminaron casteos innecesarios
	* Fecha: agosto 2011
	*/
	private static void crearTotales(Anulacion anulacion) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearTotales(Anulacion) - start");
		}

		// Linea del SubTotal
		DecimalFormat cant = new DecimalFormat("#0.00");
		DecimalFormat precio = new DecimalFormat("#,##0.00");
		enviarItemDuplicado(convertirCadena(justificar(alinearDerecha(cant.format(artVendidos),9), "SubTotal.." + obtenerSimboloMonedaLocal(Sesion.getTienda().getMonedaBase()) +alinearDerecha(precio.format(anulacion.getMontoBase()),17), columnasImpresora-3)+"\n"));
		
//		// Agregamos los impuestos aplicados
//		for (int i=0; i<impuestos.size(); i++) {
//			String montoImpuesto = precio.format(((Double)((Vector)impuestos.elementAt(i)).elementAt(1)).doubleValue());
//			String nombreImpuesto = ((Impuesto)((Vector)impuestos.elementAt(i)).elementAt(0)).getNombreImpuesto();
//			enviarItemDuplicado(convertirCadena(alinearDerecha(nombreImpuesto + ".." + alinearDerecha(montoImpuesto + " I" + (i+1),20),columnasImpresora)+"\n"));
//		}
		
		
		
		// Agregamos los impuestos aplicados
		for (int i=0; i<impuestos.size(); i++) {
			String montoImpuesto = precio.format(((Double)(impuestos.elementAt(i)).elementAt(1)).doubleValue());
			String nombreImpuesto;
			int codImpuesto = Integer.valueOf(((Impuesto)(impuestos.elementAt(i)).elementAt(0)).getCodImpuesto()).intValue();

			
			try {
				if(InitCR.preferenciasCR.getConfigStringForParameter("sistema","estiloFactura").equals("CR")){
					if(((Double)(impuestos.elementAt(i)).elementAt(1)).doubleValue()!=0.0){
						nombreImpuesto = "IMP. VENTAS";
						enviarItemDuplicado(convertirCadena(alinearDerecha(nombreImpuesto + "." + obtenerSimboloMonedaLocal(Sesion.getTienda().getMonedaBase()) + alinearDerecha(montoImpuesto + " G" + codImpuesto,20),columnasImpresora)+"\n"));						
					}
				}
				else{
					if(((Double)(impuestos.elementAt(i)).elementAt(1)).doubleValue()!=0.0){
						nombreImpuesto = ((Impuesto)(impuestos.elementAt(i)).elementAt(0)).getNombreImpuesto();
						enviarItemDuplicado(convertirCadena(alinearDerecha(nombreImpuesto + "........." + obtenerSimboloMonedaLocal(Sesion.getTienda().getMonedaBase()) + alinearDerecha(montoImpuesto + " I" + codImpuesto,20),columnasImpresora)+"\n"));						
					}
				}
			} catch (NoSuchNodeException e) {
				logger.error("crearTotales(Anulacion)", e);
			} catch (UnidentifiedPreferenceException e) {
				logger.error("crearTotales(Anulacion)", e);
			}
		}
		// Agregamos el total
		enviarItemDuplicado(convertirCadena(alinearDerecha("Total....." + obtenerSimboloMonedaLocal(Sesion.getTienda().getMonedaBase()) + alinearDerecha(precio.format(anulacion.consultarMontoTrans()),17),columnasImpresora-3)+"\n"));
		enviarItemDuplicado(convertirCadena(alinearDerecha(crearLineaDeDivision(17),columnasImpresora-3)+"\n"));

		if (logger.isDebugEnabled()) {
			logger.debug("crearTotales(Anulacion) - end");
		}
	}
	
	/**
	 * Crea las lineas de los pagos de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generaran los pagos.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se comentaron variables no usadas
	* Fecha: agosto 2011
	*/
	private static void crearPagos(Anulacion anulacion) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearPagos(Anulacion) - start");
		}

		DecimalFormat precio = new DecimalFormat("#,##0.00");
		Pago pagoActual;
		String lineaIzq;
		String lineaDer;
		for (int i=0; i<anulacion.getVentaOriginal().getPagos().size(); i++) {
			/*
			 * Que tipo de factura estamos utlizando? Dependiendo de la respuesta tomamos la desicion de 
			 * colocar la info de formas distinta segun sea el pais.
			 */
			
			try {
				if(InitCR.preferenciasCR.getConfigStringForParameter("sistema","estiloFactura").equals("CR")){
					/*
					 * Costa Rica
					 */
					pagoActual = (Pago)anulacion.getVentaOriginal().getPagos().elementAt(i);
					if(pagoActual.getFormaPago().getTipo() == 17){
						try {
							/*
							 * La forma de pago es DOLARES
							 */
							//String dolares = String.valueOf(pagoActual.getMonto() / MediadorBD.obtenerCambioDelDia());
							//String cambioDelDia = String.valueOf(MediadorBD.obtenerCambioDelDia());
							
							/*
							 * Imprimimos primero la cantidad de dolares que recibimos
							 */
							lineaIzq = pagoActual.getFormaPago().getNombre().toUpperCase() + "..........:$";
							lineaDer = precio.format(pagoActual.getMonto() / MediadorBD.obtenerCambioDelDia());
							enviarItemDuplicado(convertirCadena(alinearDerecha(lineaIzq + alinearDerecha(lineaDer, 17),columnasImpresora - 3)+"\n"));
							
							/*
							 * Luego de imprimir la cantidad de dolares recibidos, imprimimos el cambio del dia
							 */        
							lineaIzq = "Tipo de Cambio...:"+'\275';
							lineaDer = precio.format(MediadorBD.obtenerCambioDelDia());
							enviarItemDuplicado(convertirCadena(alinearDerecha(lineaIzq + alinearDerecha(lineaDer, 17),columnasImpresora - 3)+"\n"));						
							
							/*
							 * Ahora imprimimos el total en moneda local
							 */
							lineaIzq = "            :"+'\275';
							lineaDer = precio.format(pagoActual.getMonto());
							enviarItemDuplicado(convertirCadena(alinearDerecha(lineaIzq + alinearDerecha(lineaDer, 17),columnasImpresora - 3)+"\n"));													
						} catch (BaseDeDatosExcepcion e1) {
							logger.error("crearPagos(Anulacion)", e1);
						} catch (ConexionExcepcion e1) {
							logger.error("crearPagos(Anulacion)", e1);
						} catch (SQLException e1) {
							logger.error("crearPagos(Anulacion)", e1);
						}						
					}
					else{
						lineaIzq = pagoActual.getFormaPago().getNombre().toUpperCase() + ".........:"+'\275';
						lineaDer = precio.format(pagoActual.getMonto());
						enviarItemDuplicado(convertirCadena(alinearDerecha(lineaIzq + alinearDerecha(lineaDer, 17),columnasImpresora - 3)+"\n"));						
					}
					
				}
				else{
					/*
					 * Venezuela, por defecto
					 */
					pagoActual = (Pago)anulacion.getVentaOriginal().getPagos().elementAt(i);
					lineaIzq = pagoActual.getFormaPago().getNombre().toUpperCase() + ":";
					lineaDer = precio.format(pagoActual.getMonto());
					enviarItemDuplicado(convertirCadena(alinearDerecha(lineaIzq + alinearDerecha(lineaDer, 17),columnasImpresora - 3)+"\n"));
				}
			} catch (NoSuchNodeException e) {
				logger.error("crearPagos(Anulacion)", e);
			} catch (UnidentifiedPreferenceException e) {
				logger.error("crearPagos(Anulacion)", e);
			}
		}
		
		if (anulacion.getVentaOriginal().getMontoVuelto() > 0) {
			lineaIzq = "CAMBIO:";
			lineaDer = precio.format(anulacion.getVentaOriginal().getMontoVuelto());
			enviarItemDuplicado(convertirCadena(alinearDerecha(lineaIzq + alinearDerecha(lineaDer, 17),columnasImpresora - 3)+"\n"));
		}
				
		if (logger.isDebugEnabled()) {
			logger.debug("crearPagos(Anulacion) - end");
		}
	}
	
	/**
	 * Crea el pie de pagina de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generara el pie de Pagina.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se eliminaron casteos innecesarios
	* Fecha: agosto 2011
	*/
	private static void crearPieDePagina(Anulacion anulacion) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePagina(Anulacion) - start");
		}

		Sesion.crPrinterOperations.enviarString(convertirCadena(crearLineaDeDivision(columnasImpresora)+"\n"));
		// Si existe publicidad de la tienda, la insertamos 
		if (Sesion.getTienda().getPublicidades().size()>0) {
			// Agregamos las publicidades de la Factura
			Sesion.crPrinterOperations.alinear(1);
			for (int i=0; i<Sesion.getTienda().getPublicidades().size();i++)
				Sesion.crPrinterOperations.enviarString(convertirCadena((String)Sesion.getTienda().getPublicidades().elementAt(i)+"\n"));
			Sesion.crPrinterOperations.alinear(0);
			Sesion.crPrinterOperations.enviarString(convertirCadena(crearLineaDeDivision(columnasImpresora)+"\n"));
		}
		Sesion.crPrinterOperations.alinear(0);
		// Imprimimos los impuestos aplicados
		DecimalFormat porcImp = new DecimalFormat("#0.00");
		try {
			if(InitCR.preferenciasCR.getConfigStringForParameter("sistema","estiloFactura").equals("CR")){
				Sesion.crPrinterOperations.enviarString("G:Gravado - E:Exento\n");
			}
		}catch (NoSuchNodeException e) {
			logger.error("crearPieDePagina(Anulacion)", e);
		} catch (UnidentifiedPreferenceException e) {
			logger.error("crearPieDePagina(Anulacion)", e);
		}
		
		for (int i=0; i<impuestos.size(); i++){			
			int codImpuesto = Integer.valueOf(((Impuesto)(impuestos.elementAt(i)).elementAt(0)).getCodImpuesto()).intValue();
			/*
			 * Que tipo de factura estamos utlizando? Dependiendo de la respuesta tomamos la desicion de 
			 * colocar la info de formas distinta segun sea el pais.
			 */
			
			try {
				if(InitCR.preferenciasCR.getConfigStringForParameter("sistema","estiloFactura").equals("CR")){
					if(((Double)(impuestos.elementAt(i)).elementAt(1)).doubleValue()!=0.0){
						Sesion.crPrinterOperations.enviarString(convertirCadena("G" + codImpuesto + ":" + porcImp.format(((Impuesto)(impuestos.elementAt(i)).elementAt(0)).getPorcentaje()) + "%\n"));						
					}
					else{
						// Sesion.crPrinterOperations.enviarString(convertirCadena("E: 0,00%\n"));
					}					
				}
				else{
					if(((Double)(impuestos.elementAt(i)).elementAt(1)).doubleValue()!=0.0){
						Sesion.crPrinterOperations.enviarString(convertirCadena("I" + codImpuesto + ":" + porcImp.format(((Impuesto)(impuestos.elementAt(i)).elementAt(0)).getPorcentaje()) + "%\n"));
					}
					else{
						// Sesion.crPrinterOperations.enviarString(convertirCadena("E: 0,00%\n"));
					}					
				}
			} catch (NoSuchNodeException e) {
				logger.error("crearPieDePagina(Anulacion)", e);
			} catch (UnidentifiedPreferenceException e) {
				logger.error("crearPieDePagina(Anulacion)", e);
			}
		}

		
		Sesion.crPrinterOperations.activarFuenteImpresionNegrita();
		Sesion.crPrinterOperations.enviarString(convertirCadena("A N U L A C I O N\n"));
		Sesion.crPrinterOperations.initializarPrinter();
		//Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("MH",Sesion.getCaja().getSerial(),columnasImpresora)+"\n");

		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePagina(Anulacion) - end");
		}
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
				
		String lineaIzquierda = cajero +  centrar(transaccion,18) + " ";
				
		Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(justificar(lineaIzquierda, caja, columnasImpresoraFiscal));
//		Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(crearLineaDeDivision(columnasImpresoraFiscal));

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
			Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(detalleActual.getProducto().getDescripcionCorta() + " 1 " + detalleActual.getProducto().getAbreviadoUnidadVenta());
			if (!detalleActual.getCondicionVenta().equals(Sesion.condicionNormal)) {
				// Si se ha aplicado alguna rebaja (Promocion, Empaque, Cambio de Precio) se especifica
				Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(" PR:" + precio.format(detalleActual.getProducto().getPrecioRegular())
								+ " " + detalleActual.getCondicionVenta() + ":" + precio.format(detalleActual.getPrecioFinal()));
			}
	
			String codProd = detalleActual.getProducto().getCodProducto();
			int longitud = codProd.length();
			if ((Sesion.getLongitudCodigoInterno()>-1) && (longitud>Sesion.getLongitudCodigoInterno())) {
				codProd = codProd.substring(codProd.length()-Sesion.getLongitudCodigoInterno()); 
				for (int j=0;j<longitud-Sesion.getLongitudCodigoInterno();j++) {
					codProd = " " + codProd; 
				}
			}
					
			if((anulacion.getMontoImpuesto()==0)&&(anulacion.getCliente().isExento()))		
				Sesion.crFiscalPrinterOperations.imprimirItem("COD: " + detalleActual.getProducto().getCodProducto(), detalleActual.getCantidad(), detalleActual.getPrecioFinal(), 0);
			else	
				Sesion.crFiscalPrinterOperations.imprimirItem("COD: " + detalleActual.getProducto().getCodProducto(), detalleActual.getCantidad(), detalleActual.getPrecioFinal(), detalleActual.getProducto().getImpuesto().getPorcentaje());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("crearDetallesFiscal(Anulacion, Vector) - end");
		}
	}
	
	/**
	 * Realiza la impresion de los Totales de la factura de Anulación indicada como parametro.
	 * @param anulacion Anulación que se imprimirá.
	 */
	private static void crearTotalesFiscal(Anulacion anulacion) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearTotalesFiscal(Anulacion) - start");
		}

		Pago pagoActual;
		String lineaIzq;
		String lineaDer;
		DecimalFormat precio = new DecimalFormat("#,##0.00");
		
		for (int i=0; i<anulacion.getVentaOriginal().getPagos().size(); i++) {
			pagoActual = (Pago)anulacion.getVentaOriginal().getPagos().elementAt(i);
			lineaIzq = pagoActual.getFormaPago().getNombre() + ":";
			lineaDer = precio.format(pagoActual.getMonto());
			
			if(lineaIzq.length() > 17) {
				lineaIzq = lineaIzq.substring(0,15);
				lineaIzq = lineaIzq + ":";
			}
			
			Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(alinearDerecha(lineaIzq + alinearDerecha(lineaDer, 17),columnasImpresoraFiscal));
		}
	
		if (anulacion.getVentaOriginal().getMontoVuelto() > 0) {
			lineaIzq = "CAMBIO:";
			lineaDer = precio.format(anulacion.getVentaOriginal().getMontoVuelto());
			Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(alinearDerecha(lineaIzq + alinearDerecha(lineaDer, 17),columnasImpresoraFiscal));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("crearTotalesFiscal(Anulacion) - end");
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
		Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(crearLineaDeDivision(columnasImpresoraFiscal));
		for (int i=0; i<anulacion.getLineasFacturacion(); i++){
				nroItems += ((DetalleTransaccion)anulacion.getDetallesTransaccion().elementAt(i)).getCantidad();	
		}
		Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("Total Art.: " + cant.format(nroItems));
		
		Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(crearLineaDeDivision(columnasImpresoraFiscal));
		Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(centrar("A N U L A  TR. " + numFactura.format(anulacion.getVentaOriginal().getNumTransaccion()), columnasImpresoraFiscal));
		Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(crearLineaDeDivision(columnasImpresoraFiscal));
		// Si existe publicidad de la tienda, la insertamos 
		if (Sesion.getTienda().getPublicidades().size()>0) {
			// Agregamos las publicidades de la Factura
			for (int i=0; i<Sesion.getTienda().getPublicidades().size();i++)
				Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(centrar((String)Sesion.getTienda().getPublicidades().elementAt(i),columnasImpresoraFiscal));
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
		
		//Antes de imprimir se ordena el detalle por tipo de entrega y por código de producto
		Vector<Integer> correlativosOrdenados = BaseDeDatosVenta.obtenerDetallesOrdenados(anulacion);
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
		//Se realiza la impresion de la factura. Se identifica si es por impresora Fiscal
		if(Sesion.impresoraFiscal) {
			Sesion.crFiscalPrinterOperations.crearDocumento(true);
			Sesion.crFiscalPrinterOperations.imprimirLogo();
			if (anulacion.getCliente().getCodCliente() == null ||
				anulacion.getCliente().getCodCliente().length() <= 7) {
				// FIXME: No se debe imprimir devoluciones por la impresora fiscal sin datos de cliente
				Sesion.crFiscalPrinterOperations.abrirComprobanteDevolucion(null, null, anulacion.getVentaOriginal().getNumComprobanteFiscal(), Sesion.getCaja().getSerial(), anulacion.getVentaOriginal().getFechaTrans(), anulacion.getVentaOriginal().getHoraFin());
			} else {		
				//anulacion.getCliente().setCodCliente(anulacion.getCliente().getCodCliente().replace('N', 'V'));
				if(anulacion.getCliente().getNombreCompleto().length() > 20) 
					Sesion.crFiscalPrinterOperations.abrirComprobanteDevolucion(anulacion.getCliente().getNombreCompleto().substring(0,21), anulacion.getCliente().getCodCliente().replace('N', 'V'), anulacion.getVentaOriginal().getNumComprobanteFiscal(), Sesion.getCaja().getSerial(), anulacion.getVentaOriginal().getFechaTrans(), anulacion.getVentaOriginal().getHoraFin());
				else
					Sesion.crFiscalPrinterOperations.abrirComprobanteDevolucion(anulacion.getCliente().getNombreCompleto(), anulacion.getCliente().getCodCliente().replace('N', 'V'), anulacion.getVentaOriginal().getNumComprobanteFiscal(), Sesion.getCaja().getSerial(), anulacion.getVentaOriginal().getFechaTrans(), anulacion.getVentaOriginal().getHoraFin());
			}
			
			crearEncabezadoFiscal(anulacion);
			crearDetallesFiscal(anulacion, correlativosOrdenados);
			
			//Cerramos el comprobante Fiscal en modo "A" (Parcial)
			Sesion.crFiscalPrinterOperations.cerrarComprobanteFiscal("A");
		
			//Imprimimos en Modo de texto fiscal los forma de pago y el pie de página
			Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(alinearDerecha(crearLineaDeDivision(17),columnasImpresoraFiscal));
			
			crearTotalesFiscal(anulacion);
			crearPieDePaginaFiscal(anulacion);

			//Cerramos de nuevo el comprobante para que cote el papel y finalice el comprobante
			Sesion.crFiscalPrinterOperations.cerrarComprobanteFiscal("A");
		
			try {
				Sesion.crFiscalPrinterOperations.commit();
			} catch (PrinterNotConnectedException e) {
				logger.error("imprimirFactura(Anulacion)", e);
			}
		} else {
			// Cadenas de secciones de la factura
			//Vector lineasFactura = new Vector();
			Sesion.crPrinterOperations.limpiarBuffer();
			Sesion.crPrinterOperations.initializarPrinter();
			crearEncabezado(anulacion);
			crearDetalle(anulacion, correlativosOrdenados);
			crearTotales(anulacion);
			crearPagos(anulacion);
			crearPieDePagina(anulacion);
			Sesion.crPrinterOperations.cortarPapel();
			//Sesion.crPrinterOperations.abrirPuertoImpresora();
			try {
				Sesion.crPrinterOperations.commit();
			} catch (PrinterNotConnectedException e) {
				logger.error("imprimirFactura(Anulacion)", e);
			}
			//Sesion.crPrinterOperations.cerrarPuertoImpresora();
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
