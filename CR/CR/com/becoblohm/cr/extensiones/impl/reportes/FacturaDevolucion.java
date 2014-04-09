/**
 * =============================================================================
 * Proyecto   : CajaRegistradora
 * Paquete    : com.becoblohm.cr.reportes
 * Programa   : FacturaDevolucion.java
 * Creado por : gmartinelli
 * Creado en  : 28-may-04 14:27:10
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
import com.becoblohm.cr.manejarventa.DetalleTransaccion;
import com.becoblohm.cr.manejarventa.Devolucion;
import com.becoblohm.cr.manejarventa.Impuesto;
import com.becoblohm.cr.manejarventa.Venta;
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
public class FacturaDevolucion extends Reporte {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(FacturaDevolucion.class);
	
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
	private static void crearEncabezado(Devolucion devolucion) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezado(Devolucion) - start");
		}

		SimpleDateFormat fechaTrans = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat horaTrans = new SimpleDateFormat("HH:mm:ss");
		DecimalFormat enteroConCeros = new DecimalFormat("000");
		DecimalFormat numFactura = new DecimalFormat("00000000000");
		
		enviarItemDuplicado(convertirCadena(crearLineaDeDivision(columnasImpresora)+"\n"));
		Sesion.crPrinterOperations.activarFuenteImpresionNegrita();
		Sesion.crPrinterOperations.alinear(1);
		enviarItemDuplicado(convertirCadena("D E V O L U C I O N\n"));
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
			logger.error("crearEncabezado(Devolucion)", e);
		} catch (UnidentifiedPreferenceException e) {
			logger.error("crearEncabezado(Devolucion)", e);
		}
		
		Sesion.crPrinterOperations.alinear(0);
		enviarItemDuplicado(convertirCadena(crearLineaDeDivision(columnasImpresora)+"\n"));
		
		// Si existe Cliente en la Venta
		if (devolucion.getCliente().getCodCliente()!=null) {
			// Creamos las lineas con los datos del Cliente
			String cliente = "Cliente: ";
			String direccion = "Dirección: ";
			Vector<String> lineasNombre = dividirEnLineas(columnasImpresora - 3 - cliente.length(),devolucion.getCliente().getNombreCompleto());
			Vector<String> lineasDireccion = dividirEnLineas(columnasImpresora - 3 - direccion.length(),devolucion.getCliente().getDireccion());
			String RIF="";
			
			try {
				if(InitCR.preferenciasCR.getConfigStringForParameter("sistema","estiloFactura").equals("CR")){
					RIF = "IDENTIFICACION: " + devolucion.getCliente().getCodCliente();
				}
				else{
					RIF = "CI/RIF: " + devolucion.getCliente().getCodCliente();
				}
			} catch (NoSuchNodeException e) {
				logger.error("crearEncabezado(Devolucion)", e);
			} catch (UnidentifiedPreferenceException e) {
				logger.error("crearEncabezado(Devolucion)", e);
			}

			String NIT = ((devolucion.getCliente().getNit()!=null)&&(!devolucion.getCliente().getNit().equalsIgnoreCase("")))
						? "NIT:" + devolucion.getCliente().getNit()
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
		String hora = horaTrans.format(devolucion.getHoraFin());
		String fecha = fechaTrans.format(devolucion.getFechaTrans());
		String transaccion = "Tr:" + numFactura.format(devolucion.getNumTransaccion()); 
		enviarItemDuplicado(convertirCadena(
			justificar(hora	+ centrar(fecha, columnasImpresora - transaccion.length() - hora.length()),
					transaccion, columnasImpresora)+"\n"));
		Sesion.crPrinterOperations.activarFuenteImpresionNegrita();
		Sesion.crPrinterOperations.alinear(1);
		Sesion.crPrinterOperations.initializarPrinter();
		Sesion.crPrinterOperations.alinear(0);

		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezado(Devolucion) - end");
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

	
	
	
	
	
	
	/**
	 * Crea el detalle de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generara el detalle.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Se comentaron variables sin uso
	* Fecha: agosto 2011
	*/
	private static void crearDetalle(Devolucion devolucion, Vector<Integer> correlativosOrd) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalle(Devolucion, Vector) - start");
		}

		DecimalFormat precio = new DecimalFormat("#,##0.00");
		DecimalFormat cantidad = new  DecimalFormat("#0.00");
		DetalleTransaccion detalleActual;
		
		Sesion.crPrinterOperations.initializarPrinter();
		//Sesion.crPrinterOperations.activarDocumentoNomal();
		enviarItemDuplicado(convertirCadena(crearLineaDeDivision(columnasImpresora)));
		// Colocamos el tipo de Moneda Base Justificado a la Derecha
		// enviarItemDuplicado(convertirCadena(alinearDerecha(Sesion.getTienda().getMonedaBase() != null ? Sesion.getTienda().getMonedaBase() : "", columnasImpresora)+"\n"));
		
		impuestos = new Vector<Vector<Object>>();
		artVendidos = 0;

		// Para cada detalle de la venta
		for (int i=0; i<correlativosOrd.size(); i++) {
			int posicion = ((Integer)correlativosOrd.elementAt(i)).intValue();			
			detalleActual = (DetalleTransaccion)devolucion.getDetallesTransaccion().elementAt(posicion - 1);
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
					double montoNuevo = montoAnterior + (detalleActual.getMontoImpuesto() * detalleActual.getCantidad());
					(impuestos.elementAt(k)).set(1,new Double(montoNuevo));
					break;
				}
			}
			if (k == impuestos.size()) {
				Vector<Object> nuevoImpuesto = new Vector<Object>();
				Impuesto imp = detalleActual.getProducto().getImpuesto();
				imp.setPorcentaje((detalleActual.getMontoImpuesto()*100)/detalleActual.getPrecioFinal());
				nuevoImpuesto.addElement(imp);
				nuevoImpuesto.addElement(new Double(detalleActual.getMontoImpuesto()*detalleActual.getCantidad()));
				impuestos.addElement(nuevoImpuesto);;
			}
			
			//numImpuesto = String.valueOf(k+1);			
			String codImpuesto = detalleActual.getProducto().getImpuesto().getCodImpuesto();
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
			logger.debug("crearDetalle(Devolucion, Vector) - end");
		}
	}
	
	/**
	 * Crea las lineas de los totales de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generaran los totales.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se eliminaron casteos inútiles
	* Fecha: agosto 2011
	*/
	private static void crearTotales(Devolucion devolucion) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearTotales(Devolucion) - start");
		}

		// Linea del SubTotal
		DecimalFormat cant = new DecimalFormat("#0.00");
		DecimalFormat precio = new DecimalFormat("#,##0.00");
		enviarItemDuplicado(convertirCadena(justificar(alinearDerecha(cant.format(artVendidos),9), "SubTotal.." +obtenerSimboloMonedaLocal(Sesion.getTienda().getMonedaBase()) +alinearDerecha(precio.format(devolucion.getMontoBase()),17), columnasImpresora-3)+"\n"));

	
		
		// Agregamos los impuestos aplicados
		for (int i=0; i<impuestos.size(); i++) {
			String montoImpuesto = precio.format(((Double)(impuestos.elementAt(i)).elementAt(1)).doubleValue());
			String nombreImpuesto;
			String codImpuesto = ((Impuesto)(impuestos.elementAt(i)).elementAt(0)).getCodImpuesto();

			
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
				logger.error("crearTotales(Devolucion)", e);
			} catch (UnidentifiedPreferenceException e) {
				logger.error("crearTotales(Devolucion)", e);
			}
			
		}

		// Agregamos el total
		enviarItemDuplicado(convertirCadena(alinearDerecha("Total....." + obtenerSimboloMonedaLocal(Sesion.getTienda().getMonedaBase()) +alinearDerecha(precio.format(devolucion.consultarMontoTrans()),17),columnasImpresora-3)+"\n"));		
		enviarItemDuplicado(convertirCadena(alinearDerecha(crearLineaDeDivision(17),columnasImpresora-3)+"\n"));
		
		
		if (logger.isDebugEnabled()) {
			logger.debug("crearTotales(Devolucion) - end");
		}
	}
	
	/**
	 * Crea las lineas de los pagos de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generaran los pagos.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	/*private static void crearPagos(Venta ventaActual, Vector lineas) {
		DecimalFormat precio = new DecimalFormat("#,##0.00");
		Pago pagoActual;
		String lineaIzq;
		String lineaDer;
		
		for (int i=0; i<ventaActual.getPagos().size(); i++) {
			pagoActual = (Pago)ventaActual.getPagos().elementAt(i);
			lineaIzq = pagoActual.getFormaPago().getNombre() + ":";
			lineaDer = precio.format(pagoActual.getMonto());
			lineas.addElement(alinearDerecha(lineaIzq + alinearDerecha(lineaDer, 17),columnasImpresora - 3));
		}

		if (ventaActual.getMontoRemanente() > 0) {
			lineaIzq = "CAMBIO:";
			lineaDer = precio.format(ventaActual.getMontoRemanente());
			lineas.addElement(alinearDerecha(lineaIzq + alinearDerecha(lineaDer, 17),columnasImpresora - 3));
		}
	}*/
	
	
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
	 * Crea el pie de pagina de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generara el pie de Pagina.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se eliminaron casteos no necesarios
	* Fecha: agosto 2011
	*/
	private static void crearPieDePagina(Devolucion devolucion) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePagina(Devolucion) - start");
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
			logger.error("crearPieDePagina(Devolucion)", e);
		} catch (UnidentifiedPreferenceException e) {
			logger.error("crearPieDePagina(Devolucion)", e);
		}
		
		for (int i=0; i<impuestos.size(); i++){			
			String codImpuesto = ((Impuesto)(impuestos.elementAt(i)).elementAt(0)).getCodImpuesto();
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
				logger.error("crearPieDePagina(Devolucion)", e);
			} catch (UnidentifiedPreferenceException e) {
				logger.error("crearPieDePagina(Devolucion)", e);
			}
		}
		
		// Si no hay cliente en la venta, agregamos la linea "SIN DERECHO A CREDITO FISCAL"
		Sesion.crPrinterOperations.alinear(1);
		Sesion.crPrinterOperations.activarFuenteImpresionNegrita();
		Sesion.crPrinterOperations.enviarString(convertirCadena("D E V O L U C I O N\n"));
		Sesion.crPrinterOperations.initializarPrinter();
		//Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("MH",Sesion.getCaja().getSerial(),columnasImpresora)+"\n");

		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePagina(Devolucion) - end");
		}
	}
	
	//************** Métodos para impresora Fiscal ********************
	//*****************************************************************
	/**
	 * Realiza la impresion del encabezado de la factura de Venta indicada como parametro.
	 * @param ventaActual Venta que se imprimira.
	 */
	private static void crearEncabezadoFiscal(Devolucion devolucion) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal(Devolucion) - start");
		}

		DecimalFormat enteroConCeros = new DecimalFormat("000");
		DecimalFormat numFactura = new DecimalFormat("00000000000");
		String cajero = "Cajero:" + Sesion.getUsuarioActivo().getNumFicha();
		String caja = "POS:" + enteroConCeros.format(Sesion.getCaja().getNumero());
		String transaccion = "Tr:" + numFactura.format(devolucion.getNumTransaccion());
				
		String lineaIzquierda = cajero +  centrar(transaccion,18) + " ";
				
		Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(justificar(lineaIzquierda, caja, columnasImpresoraFiscal));
		//Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(crearLineaDeDivision(columnasImpresoraFiscal));

		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal(Devolucion) - end");
		}
	}
	
	/**
	 * Realiza la impresion de los detallles de la factura de Venta indicada como parametro.
	 * @param ventaActual Venta que se imprimira.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private static void crearDetallesFiscal(Devolucion devolucion, Vector<Integer> correlativosOrd) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearDetallesFiscal(Devolucion, Vector) - start");
		}

		// Para cada detalle de la venta
		DetalleTransaccion detalleActual;
		DecimalFormat precio = new DecimalFormat("#,##0.00");
		for (int i=0; i<correlativosOrd.size(); i++) {
			int posicion = ((Integer)correlativosOrd.elementAt(i)).intValue();			
			detalleActual = (DetalleTransaccion)devolucion.getDetallesTransaccion().elementAt(posicion - 1);
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
					
			if((devolucion.getMontoImpuesto()==0)&&(devolucion.getCliente().isExento()))
				Sesion.crFiscalPrinterOperations.imprimirItem("COD: " + detalleActual.getProducto().getCodProducto(), detalleActual.getCantidad(),detalleActual.getPrecioFinal(), 0);
			else {
				double tasa = detalleActual.getProducto().getImpuesto().getPorcentaje();
				try {
					tasa = MediadorBD.obtenerImpuestoFecha(devolucion.getVentaOriginal().getFechaTrans(), detalleActual.getProducto().getImpuesto().getCodImpuesto(), Sesion.getTienda().getCodRegion());
				} catch (Exception e) {
					tasa = detalleActual.getProducto().getImpuesto().getPorcentaje();					
				}
				Sesion.crFiscalPrinterOperations.imprimirItem("COD: " + detalleActual.getProducto().getCodProducto(), detalleActual.getCantidad(),detalleActual.getPrecioFinal(), tasa);//MathUtil.cutDouble(tasa, 2, true));//tasa);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("crearDetallesFiscal(Devolucion, Vector) - end");
		}
	}
	
	/**
	 * Realiza la impresion del pie de página de la factura de Venta indicada como parametro.
	 * @param ventaActual Venta que se imprimira.
	 */
	private static void crearPieDePaginaFiscal(Devolucion devolucion) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePaginaFiscal(Devolucion) - start");
		}

		float productosCaja = 0;
		DecimalFormat numFactura = new DecimalFormat("00000000000");
		DecimalFormat cant = new DecimalFormat("#0.00");
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		DecimalFormat entero = new DecimalFormat("000");
		Venta ventaOriginal = devolucion.getVentaOriginal();
			
		for (int i=0; i<devolucion.getLineasFacturacion(); i++){
			productosCaja += ((DetalleTransaccion)devolucion.getDetallesTransaccion().elementAt(i)).getCantidad();
		}
			
		Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(crearLineaDeDivision(columnasImpresoraFiscal));
		Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(justificar("Total Art.: " + cant.format(productosCaja),Sesion.isCajaEnLinea()?"CL":"FL",columnasImpresoraFiscal));
			
		Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(crearLineaDeDivision(columnasImpresoraFiscal));
		Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(centrar("D E V U E L V E  TR. " + numFactura.format(devolucion.getVentaOriginal().getNumTransaccion()), columnasImpresoraFiscal));
		Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(centrar("Tda:" + ventaOriginal.getCodTienda() + " Fecha:" + df.format(ventaOriginal.getFechaTrans()) + " Caja:" + entero.format(ventaOriginal.getNumCajaFinaliza()), columnasImpresoraFiscal));
		Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(crearLineaDeDivision(columnasImpresoraFiscal));
		// Si existe publicidad de la tienda, la insertamos 
		if (Sesion.getTienda().getPublicidades().size()>0) {
			// Agregamos las publicidades de la Factura
			for (int i=0; i<Sesion.getTienda().getPublicidades().size();i++)
				Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(centrar((String)Sesion.getTienda().getPublicidades().elementAt(i),columnasImpresoraFiscal));
			Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(crearLineaDeDivision(columnasImpresoraFiscal));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePaginaFiscal(Devolucion) - end");
		}
	}
	
	/**
	 * Realiza la impresion del código de barra de la factura de Venta indicada como parametro.
	 * @param ventaActual Venta que se imprimira.
	 */
	private static void crearCodigoBarraFiscal(Devolucion devolucion) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearCodigoBarraFiscal(Devolucion) - start");
		}
		
		Venta ventaOriginal = devolucion.getVentaOriginal();

		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		DecimalFormat entero = new DecimalFormat("000");
		String codBarra = entero.format(ventaOriginal.getCodTienda())+df.format(ventaOriginal.getFechaTrans())+
						entero.format(ventaOriginal.getNumCajaFinaliza())+ventaOriginal.getNumTransaccion();
		if ((codBarra.length()%2)==0) {
			Sesion.crFiscalPrinterOperations.imprimirCodigoBarra(codBarra);
		} else {
			Sesion.crFiscalPrinterOperations.imprimirCodigoBarra(entero.format(ventaOriginal.getCodTienda()) +
															 df.format(ventaOriginal.getFechaTrans()) +
															 entero.format(ventaOriginal.getNumCajaFinaliza()) +
															 "0" +
															ventaOriginal.getNumTransaccion());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("crearCodigoBarraFiscal(Devolucion) - end");
		}
	}
	
	/**
	 * Método imprimirFactura
	 * 	Realiza la impresion de la factura de Venta indicada como parametro.
	 * @param devolucion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static void imprimirFactura(Devolucion devolucion, boolean mostrarVuelto, double condicional, double vuelto) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirFactura(Devolucion) - start");
		}

		Vector<Integer> correlativosOrd = BaseDeDatosVenta.obtenerDetallesOrdenados(devolucion);
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
			Sesion.crFiscalPrinterOperations.crearDocumento(true);
			Sesion.crFiscalPrinterOperations.imprimirLogo();

			if (devolucion.getCliente().getCodCliente() == null) { /* ||
				devolucion.getCliente().getCodCliente().length() <= 7) { */
					// FIXME: No se debe imprimir devoluciones por la impresora fiscal sin datos de cliente
					Sesion.crFiscalPrinterOperations.abrirComprobanteDevolucion(null, null, devolucion.getVentaOriginal().getNumComprobanteFiscal(), devolucion.getVentaOriginal().getSerialCaja(), devolucion.getVentaOriginal().getFechaTrans(), devolucion.getVentaOriginal().getHoraFin());
			} else {
				if(devolucion.getCliente().getNombreCompleto().length() > 20) 
					Sesion.crFiscalPrinterOperations.abrirComprobanteDevolucion(devolucion.getCliente().getNombreCompleto().substring(0,21), devolucion.getCliente().getCodCliente().replace('N', 'V'), devolucion.getVentaOriginal().getNumComprobanteFiscal(), devolucion.getVentaOriginal().getSerialCaja(), devolucion.getVentaOriginal().getFechaTrans(), devolucion.getVentaOriginal().getHoraFin());
				else
					Sesion.crFiscalPrinterOperations.abrirComprobanteDevolucion(devolucion.getCliente().getNombreCompleto(), devolucion.getCliente().getCodCliente().replace('N', 'V'), devolucion.getVentaOriginal().getNumComprobanteFiscal(), devolucion.getVentaOriginal().getSerialCaja(), devolucion.getVentaOriginal().getFechaTrans(), devolucion.getVentaOriginal().getHoraFin());
			}

			crearEncabezadoFiscal(devolucion);
			crearDetallesFiscal(devolucion, correlativosOrd);
			
			//Cerramos el comprobante Fiscal en modo "A" (Parcial)
			Sesion.crFiscalPrinterOperations.cerrarComprobanteFiscal("A");
			crearPieDePaginaFiscal(devolucion);
			crearCodigoBarraFiscal(devolucion);

			//Cerramos de nuevo el comprobante para que cote el papel y finalice el comprobante
			Sesion.crFiscalPrinterOperations.cerrarComprobanteFiscal("A");
		
			try {
				Sesion.crFiscalPrinterOperations.commit();
			} catch (PrinterNotConnectedException e) {
				logger.error("imprimirFactura(Devolucion)", e);
			}
		} else {
			// Cadenas de secciones de la factura
			//Vector lineasFactura = new Vector();
			Sesion.crPrinterOperations.limpiarBuffer();
			Sesion.crPrinterOperations.initializarPrinter();
			crearEncabezado(devolucion);
			crearDetalle(devolucion, correlativosOrd);
			crearTotales(devolucion);
			crearPieDePagina(devolucion);
			Sesion.crPrinterOperations.cortarPapel();
			//Sesion.crPrinterOperations.abrirPuertoImpresora();
			try {
				Sesion.crPrinterOperations.commit();
			} catch (PrinterNotConnectedException e) {
				logger.error("imprimirFactura(Devolucion)", e);
			}
			//Sesion.crPrinterOperations.cerrarPuertoImpresora();
		}

		DecimalFormat df = new DecimalFormat("#,##0.00");
		//****Se muestra el vuelto por visor y por pantalla
		// Se muestra el monto del vuelto al cliente por el visor
		if (condicional > 0)
		{
			MensajesVentanas.aviso("Se abona a Condicional: " + df.format(condicional));
		}
		double saldo;
		if (mostrarVuelto) {
			/*if (condicional > 0)
			{
				MensajesVentanas.aviso("Se abona a Condicional: " + df.format(condicional));
			}*/
			if((devolucion.getMontoBase() + devolucion.getMontoImpuesto())*100 < 1)
				saldo = 0;
			else
				saldo = vuelto;
			try { CR.crVisor.enviarString("SALDO CLIENTE",0,df.format(saldo),2); }
			catch (Exception e) {
				logger.error("imprimirFactura(Devolucion)", e);
			}
									
			//Se muestra el mensaje del vuelto al cajero
			PantallaVuelto pantallaVuelto = new PantallaVuelto(saldo, true);
			MensajesVentanas.centrarVentanaDialogo(pantallaVuelto);
			//***
		}
		

		if (logger.isDebugEnabled()) {
			logger.debug("imprimirFactura(Devolucion) - end");
		}
	}

}
