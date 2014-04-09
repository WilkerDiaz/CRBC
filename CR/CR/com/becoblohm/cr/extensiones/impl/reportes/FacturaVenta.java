/**
 * =============================================================================
 * Proyecto   : CajaRegistradora
 * Paquete    : com.becoblohm.cr.reportes
 * Programa   : ComprobanteDeApartado.java
 * Creado por : gmartinelli
 * Creado en  : 04-nov-03 11:56:10
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
 * Versión     : 1.3 
 * Fecha       : 27/05/2004 11:44 AM
 * Analista    : gmartinelli
 * Descripción : Implementado formato de factura propuesto por BECO a partir de 
 * 				la union de los formatos actuales de las facturas de BECO y EPA
 * =============================================================================
 * Versión     : 1.2 
 * Fecha       : 10/03/2004 14:57 PM
 * Analista    : gmartinelli
 * Descripción : Modificado metodo imprimirFactura para que devuelve un Vector.
 * =============================================================================
 * Versión     : 1.1 
 * Fecha       : 25/02/2004 14:57 PM
 * Analista    : gmartinelli
 * Descripción : Arreglado detalles en la creación de los encabezados. Se muestra un applet
 * 				con la factura generada para efectos visuales, las lineas de la 451 a la 455
 * 				deben ser cambiadas por el segmento de codigo a utilizar para enviar la factura
 * 				generada a una impresora fiscal. No se estan manejando secuencias de escapes 
 * 				todavia. Se genera un Vector de String con todas las líneas de la factura. Al 
 * 				implementar la impresión de la factura se puede borrar la clase Factura y sus 
 * 				imports.
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
import com.becoblohm.cr.manejadorauditoria.Auditoria;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.manejarventa.DetalleTransaccion;
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
public class FacturaVenta extends Reporte {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(FacturaVenta.class);
	
	// Variables necesarias para la impresion
	private static int columnasImpresora = 44;
	private static int columnasImpresoraFiscal = 40;
	private static float artVendidos;
	private static Vector<Vector<Object>> impuestos;
	
	/**
	 * Crea el encabezado de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generara el encabezado.
	 * @param lineas Vector de las lineas actuales de la factura.
	 * @throws UnidentifiedPreferenceException
	 * @throws NoSuchNodeException
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private static void crearEncabezado(Venta ventaActual, boolean reimpresion) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezado(Venta, boolean) - start");
		}
		
		SimpleDateFormat fechaTrans = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat horaTrans = new SimpleDateFormat("HH:mm:ss");
		DecimalFormat enteroConCeros = new DecimalFormat("000");
		DecimalFormat numFactura = new DecimalFormat("00000000000");

		
		Sesion.crPrinterOperations.activarFuenteImpresionNegrita();
		Sesion.crPrinterOperations.alinear(1);
		Sesion.crPrinterOperations.enviarString(convertirCadena(Sesion.getTienda().getRazonSocial()+"\n"));
		
		String lineaDeDireccion = "";
		if((Sesion.getTienda().getDireccionFiscal() != null)&&(!Sesion.getTienda().getDireccionFiscal().equalsIgnoreCase("")))
			lineaDeDireccion += Sesion.getTienda().getDireccionFiscal()+"\n";
		if((Sesion.getTienda().getNombreSucursal() != null)&&(!Sesion.getTienda().getNombreSucursal().equalsIgnoreCase("")))
			lineaDeDireccion += "Sucursal: " + Sesion.getTienda().getNombreSucursal() + "."+"\n";
		if((Sesion.getTienda().getDireccion() != null)&&(!Sesion.getTienda().getDireccion().equalsIgnoreCase("")))
			lineaDeDireccion += Sesion.getTienda().getDireccion()+"\n";
		Sesion.crPrinterOperations.initializarPrinter();
		Sesion.crPrinterOperations.alinear(1);
		if (!lineaDeDireccion.equalsIgnoreCase("")) {
			Vector<String> nuevasLineasDireccion = dividirEnLineas(columnasImpresora,lineaDeDireccion);
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
			logger.error("crearEncabezado(Venta, boolean)", e);
		} catch (UnidentifiedPreferenceException e) {
			logger.error("crearEncabezado(Venta, boolean)", e);
		}

		Sesion.crPrinterOperations.alinear(0);
		enviarItemDuplicado(convertirCadena(crearLineaDeDivision(columnasImpresora)+"\n"));				
		
		
		
		// Si existe Cliente en la Venta
		if (ventaActual.getCliente().getCodCliente()!=null) {
			// Creamos las lineas con los datos del Cliente
			String cliente = "Cliente: ";
			String direccion = "Dirección: ";
			Vector<String> lineasNombre = dividirEnLineas(columnasImpresora - cliente.length(),ventaActual.getCliente().getNombreCompleto());
			Vector<String> lineasDireccion = dividirEnLineas(columnasImpresora - direccion.length(),ventaActual.getCliente().getDireccion());
			String RIF="";
			
			try {
				if(InitCR.preferenciasCR.getConfigStringForParameter("sistema","estiloFactura").equals("CR")){
					RIF = "IDENTIFICACION: " + ventaActual.getCliente().getCodCliente();
				}
				else{
					RIF = "CI/RIF: " + ventaActual.getCliente().getCodCliente();
				}
			} catch (NoSuchNodeException e) {
				logger.error("crearEncabezado(Venta, boolean)", e);
			} catch (UnidentifiedPreferenceException e) {
				logger.error("crearEncabezado(Venta, boolean)", e);
			}
			
			String NIT = ((ventaActual.getCliente().getNit()!=null)&&(!ventaActual.getCliente().getNit().equalsIgnoreCase("")))
						? "NIT:" + ventaActual.getCliente().getNit()
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
		String hora = horaTrans.format(ventaActual.getHoraFin());
		String fecha = fechaTrans.format(ventaActual.getFechaTrans());
		String transaccion = "Tr:" + numFactura.format(ventaActual.getNumTransaccion()); 
		enviarItemDuplicado(convertirCadena(
			justificar(hora	+ centrar(fecha, columnasImpresora - transaccion.length() - hora.length()),
					transaccion, columnasImpresora)+"\n"));
		
		/*
		 * Si es una reimpresion, agregamos el mensaje que asi lo indique
		 */
		if(reimpresion == true){
			Sesion.crPrinterOperations.activarFuenteImpresionNegrita();
			Sesion.crPrinterOperations.alinear(1);
			Sesion.crPrinterOperations.enviarString("*** REIMPRESION ***\n");			
		}
		
		Sesion.crPrinterOperations.initializarPrinter();
		Sesion.crPrinterOperations.activarImpresionSimultanea();
		Sesion.crPrinterOperations.alinear(0);

		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezado(Venta, boolean) - end");
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
	* Sólo se comentaron variables no usadas y parametrizaron los tipos de datos contenidos en 'Vector'
	* Fecha: agosto 2011
	*/
	private static void crearDetalle(Venta ventaActual, Vector<Integer> correlativosOrd) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalle(Venta, Vector) - start");
		}

		DecimalFormat precio = new DecimalFormat("#,##0.00");
		DecimalFormat cantidad = new  DecimalFormat("#0.00");
		DetalleTransaccion detalleActual;
		
		
		enviarItemDuplicado(convertirCadena(crearLineaDeDivision(columnasImpresora)+"\n"));
		// Colocamos el tipo de Moneda Base Justificado a la Derecha
		// enviarItemDuplicado(convertirCadena(alinearDerecha(Sesion.getTienda().getMonedaBase() != null ? Sesion.getTienda().getMonedaBase() : "", columnasImpresora - 3)+"\n");
		
		impuestos = new Vector<Vector<Object>>();
		artVendidos = 0;

		// Para cada detalle de la venta
		for (int i=0; i<correlativosOrd.size(); i++) {
			int posicion = ((Integer)correlativosOrd.elementAt(i)).intValue();
			detalleActual = (DetalleTransaccion)ventaActual.getDetallesTransaccion().elementAt(posicion - 1);
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
			 
			String tipoEntrega = detalleActual.getTipoEntrega();
			if(tipoEntrega.equals(Sesion.ENTREGA_CAJA))
				tipoEntrega = "CJ";
			else if(tipoEntrega.equals(Sesion.ENTREGA_DESPACHO))
				tipoEntrega = "DS";
			else if(tipoEntrega.equals(Sesion.ENTREGA_DOMICILIO))
				tipoEntrega = "ED";
			else if (tipoEntrega.equals(Sesion.ENTREGA_CLIENTE_RETIRA))
				tipoEntrega = "CR";
			else
				tipoEntrega = "";
				
			enviarItemDuplicado(convertirCadena(justificar(alinearDerecha(codProd,longitudCodigo) + " " 
							+ detalleActual.getProducto().getDescripcionCorta(), "1 " + detalleActual.getProducto().getAbreviadoUnidadVenta(),columnasImpresora-3) + " " + tipoEntrega + "\n"));
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
				nuevoImpuesto.addElement(detalleActual.getProducto().getImpuesto());
				nuevoImpuesto.addElement(new Double(detalleActual.getMontoImpuesto() * detalleActual.getCantidad()));
				impuestos.addElement(nuevoImpuesto);;
			}
			
			//numImpuesto = String.valueOf(k+1);

			
			
			
			
			
			
			/*
			 * Originalmente habia planeado hacer distincion entre los estilos de las facturas entre costa rica y venezuela
			 * en la forma como se pintaban las facturas pero al parecer las voy a dejar iguales, a esto le llamo estilo 1 y
			 * no estan comentadas, el estilo 2 tiene el estilo original y esta comentadas, si en un futuro hay que hacer otro cambio
			 * solo hay que comentar el bloque identificado como estilo 1 y hacer los cambios en el estilo 2
			 * 																	
			 * 																						Vic.
			 */
			

			/*
			 * ESTILO 1
			 */
			
			
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

			
			
			
			/*
			 * Estilo 2 - DESACTIVADO!!! VZLA y Costa Rica ya no difieren en este punto, el ESTILO 1 es el DEFAULT
			 */
			
//			try {
//				if(InitCR.preferenciasCR.getConfigStringForParameter("sistema","estiloFactura").equals("CR")){
//					if(detalleActual.getMontoImpuesto()==0.0){
//						enviarItemDuplicado(convertirCadena(alinearDerecha(cantidad.format(detalleActual.getCantidad()),9)
//								+ alinearDerecha(precio.format(detalleActual.getPrecioFinal()), 16)
//								+ alinearDerecha(precio.format(detalleActual.getCantidad() * detalleActual.getPrecioFinal()), 16)
//								+ " E\n"));						
//					}
//					else{
//						enviarItemDuplicado(convertirCadena(alinearDerecha(cantidad.format(detalleActual.getCantidad()),9)
//								+ alinearDerecha(precio.format(detalleActual.getPrecioFinal()), 16)
//								+ alinearDerecha(precio.format(detalleActual.getCantidad() * detalleActual.getPrecioFinal()), 16)
//								+ " G" + numImpuesto+"\n"));						
//					}
//				}
//				else{
//					enviarItemDuplicado(convertirCadena(alinearDerecha(cantidad.format(detalleActual.getCantidad()),9)
//							+ alinearDerecha(precio.format(detalleActual.getPrecioFinal()), 16)
//							+ alinearDerecha(precio.format(detalleActual.getCantidad() * detalleActual.getPrecioFinal()), 16)
//							+ " I" + numImpuesto+"\n"));
//				}
//			} catch (NoSuchNodeException e) {
//				e.printStackTrace();
//			} catch (UnidentifiedPreferenceException e) {
//				e.printStackTrace();
//			}

			artVendidos += detalleActual.getCantidad();
		}
		enviarItemDuplicado(convertirCadena(crearLineaDeDivision(columnasImpresora-3)+"\n"));

		if (logger.isDebugEnabled()) {
			logger.debug("crearDetalle(Venta, Vector) - end");
		}
	}
	
	/**
	 * Crea las lineas de los totales de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generaran los totales.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private static void crearTotales(Venta ventaActual) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearTotales(Venta) - start");
		}

		// Linea del SubTotal
		DecimalFormat cant = new DecimalFormat("#0.00");
		DecimalFormat precio = new DecimalFormat("#,##0.00");

		enviarItemDuplicado(convertirCadena(justificar(alinearDerecha(cant.format(artVendidos),9), "SUBTOTAL...." + obtenerSimboloMonedaLocal(Sesion.getTienda().getMonedaBase()) +alinearDerecha(precio.format(ventaActual.getMontoBase()),17), columnasImpresora-3)+"\n"));
		                                                                                               
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
				logger.error("crearTotales(Venta)", e);
			} catch (UnidentifiedPreferenceException e) {
				logger.error("crearTotales(Venta)", e);
			}
			
		}
		
		// Agregamos el total
		enviarItemDuplicado(convertirCadena(alinearDerecha("TOTAL......."+ obtenerSimboloMonedaLocal(Sesion.getTienda().getMonedaBase()) +alinearDerecha(precio.format(ventaActual.consultarMontoTrans()),17),columnasImpresora-3)+"\n"));
		enviarItemDuplicado(convertirCadena(alinearDerecha(crearLineaDeDivision(17),columnasImpresora-3)+"\n"));

		if (logger.isDebugEnabled()) {
			logger.debug("crearTotales(Venta) - end");
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
	 * Crea las lineas de los pagos de la factura. En el vector de lineas se agregan las lineas generadas.
	 * @param ventaActual Venta a partir de la cual se generaran los pagos.
	 * @param lineas Vector de las lineas actuales de la factura.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se comentaron variables sin uso
	* Fecha: agosto 2011
	*/
	private static void crearPagos(Venta ventaActual) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearPagos(Venta) - start");
		}

		DecimalFormat precio = new DecimalFormat("#,##0.00");
		Pago pagoActual;
		String lineaIzq;
		String lineaDer;
		Sesion.crPrinterOperations.activarImpresionSimultanea();
		
		for (int i=0; i<ventaActual.getPagos().size(); i++) {
			/*
			 * Que tipo de factura estamos utlizando? Dependiendo de la respuesta tomamos la desicion de 
			 * colocar la info de formas distinta segun sea el pais.
			 */
			
			try {
				if(InitCR.preferenciasCR.getConfigStringForParameter("sistema","estiloFactura").equals("CR")){
					/*
					 * Costa Rica
					 */
					pagoActual = (Pago)ventaActual.getPagos().elementAt(i);
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
							lineaIzq = pagoActual.getFormaPago().getNombre().toUpperCase() + "..:$";
							lineaDer = precio.format(pagoActual.getMonto() / MediadorBD.obtenerCambioDelDia());
							enviarItemDuplicado(convertirCadena(alinearDerecha(lineaIzq + alinearDerecha(lineaDer, 17),columnasImpresora - 3)+"\n"));
							
							/*
							 * Luego de imprimir la cantidad de dolares recibidos, imprimimos el cambio del dia
							 */        
							lineaIzq = "Tipo de Cambio..:"+'\275';
							lineaDer = precio.format(MediadorBD.obtenerCambioDelDia());
							enviarItemDuplicado(convertirCadena(alinearDerecha(lineaIzq + alinearDerecha(lineaDer, 17),columnasImpresora - 3)+"\n"));						
							
							/*
							 * Ahora imprimimos el total en moneda local
							 */
							lineaIzq = "            :"+'\275';
							lineaDer = precio.format(pagoActual.getMonto());
							enviarItemDuplicado(convertirCadena(alinearDerecha(lineaIzq + alinearDerecha(lineaDer, 17),columnasImpresora - 3)+"\n"));													
						} catch (BaseDeDatosExcepcion e1) {
							logger.error("crearPagos(Venta)", e1);
						} catch (ConexionExcepcion e1) {
							logger.error("crearPagos(Venta)", e1);
						} catch (SQLException e1) {
							logger.error("crearPagos(Venta)", e1);
						}						
					}
					else{
						lineaIzq = pagoActual.getFormaPago().getNombre().toUpperCase() + "..:"+'\275';
						lineaDer = precio.format(pagoActual.getMonto());
						enviarItemDuplicado(convertirCadena(alinearDerecha(lineaIzq + alinearDerecha(lineaDer, 17),columnasImpresora - 3)+"\n"));						
					}
					
				}
				else{
					/*
					 * Venezuela, por defecto
					 */
					pagoActual = (Pago)ventaActual.getPagos().elementAt(i);
					lineaIzq = pagoActual.getFormaPago().getNombre().toUpperCase() + ":";
					lineaDer = precio.format(pagoActual.getMonto());
					enviarItemDuplicado(convertirCadena(alinearDerecha(lineaIzq + alinearDerecha(lineaDer, 17),columnasImpresora - 3)+"\n"));
				}
			} catch (NoSuchNodeException e) {
				logger.error("crearPagos(Venta)", e);
			} catch (UnidentifiedPreferenceException e) {
				logger.error("crearPagos(Venta)", e);
			}
						
		}

		if (ventaActual.getMontoVuelto() > 0) {
			lineaIzq = "CAMBIO:";
			lineaDer = precio.format(ventaActual.getMontoVuelto());
			enviarItemDuplicado(convertirCadena(alinearDerecha(lineaIzq + alinearDerecha(lineaDer, 17),columnasImpresora - 3)+"\n"));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("crearPagos(Venta) - end");
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
	private static void crearPieDePagina(Venta ventaActual, boolean reimpresion) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePagina(Venta, boolean) - start");
		}

		float productosCaja = 0;
		float productosEntregaDom = 0;
		float productosDespacho = 0;
		
		Sesion.crPrinterOperations.initializarPrinter();
		DecimalFormat cant = new DecimalFormat("#0.00");
		
		for (int i=0; i<ventaActual.getLineasFacturacion(); i++){
			if (((DetalleTransaccion)ventaActual.getDetallesTransaccion().elementAt(i)).getTipoEntrega().equalsIgnoreCase(Sesion.ENTREGA_CAJA))
				productosCaja += ((DetalleTransaccion)ventaActual.getDetallesTransaccion().elementAt(i)).getCantidad();
			if (((DetalleTransaccion)ventaActual.getDetallesTransaccion().elementAt(i)).getTipoEntrega().equals(Sesion.ENTREGA_DESPACHO))
				productosDespacho += ((DetalleTransaccion)ventaActual.getDetallesTransaccion().elementAt(i)).getCantidad();
			if (((DetalleTransaccion)ventaActual.getDetallesTransaccion().elementAt(i)).getTipoEntrega().equals(Sesion.ENTREGA_DOMICILIO))
				productosEntregaDom += ((DetalleTransaccion)ventaActual.getDetallesTransaccion().elementAt(i)).getCantidad();	
		}
		
		Sesion.crPrinterOperations.enviarString(convertirCadena(crearLineaDeDivision(columnasImpresora)+"\n"));
		Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("Prod(s) Cte.: " + cant.format(productosCaja),Sesion.isCajaEnLinea()?"CL":"FL",columnasImpresora)+"\n"));
		
		//Se registra en la ausitoria el nro de productos que se lleva el cliente.Requerimiento solicitado
		Auditoria.registrarAuditoria("Nro. de productos que lleva el cliente: " + cant.format(productosCaja),'T');
		if (productosEntregaDom > 0) {
			//Se registra en la ausitoria el nro de productos que se facturó para entrega a domicilio.Requerimiento solicitado
			Auditoria.registrarAuditoria("Nro. de productos para entrega a domicilio: " + cant.format(productosEntregaDom),'T');
		}
		if(productosDespacho > 0) {
			//Se registra en la ausitoria el nro de productos que se facturó para entrega a domicilio.Requerimiento solicitado
			Auditoria.registrarAuditoria("Nro. de productos para despacho fuera de caja: " + cant.format(productosDespacho),'T');
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

		// Imprimimos los impuestos aplicados
		DecimalFormat porcImp = new DecimalFormat("#0.00");
		try {
			if(InitCR.preferenciasCR.getConfigStringForParameter("sistema","estiloFactura").equals("CR")){
				Sesion.crPrinterOperations.enviarString("G:Gravado - E:Exento\n");
			}
		}catch (NoSuchNodeException e) {
			logger.error("crearPieDePagina(Venta, boolean)", e);
		} catch (UnidentifiedPreferenceException e) {
			logger.error("crearPieDePagina(Venta, boolean)", e);
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
				logger.error("crearPieDePagina(Venta, boolean)", e);
			} catch (UnidentifiedPreferenceException e) {
				logger.error("crearPieDePagina(Venta, boolean)", e);
			}
		}
		
		// Si no hay cliente en la venta, agregamos la linea "SIN DERECHO A CREDITO FISCAL"
		Sesion.crPrinterOperations.alinear(1);
//		if (ventaActual.getCliente().getCodCliente()==null) {
//			/*
//			 * Quitamos esta linea no hace falta pues no es una factura fiscal
//			 */
//			// Sesion.crPrinterOperations.enviarString(convertirCadena("SIN DERECHO A CREDITO FISCAL\n");
//		}
		Sesion.crPrinterOperations.activarFuenteImpresionNegrita();
		Sesion.crPrinterOperations.enviarString(convertirCadena("V E N T A\n\n"));
		
		/*
		 * Si es una reimpresion, agregamos el mensaje que asi lo indique
		 */
		if(reimpresion == true){
			Sesion.crPrinterOperations.activarFuenteImpresionNegrita();
			Sesion.crPrinterOperations.alinear(1);
			Sesion.crPrinterOperations.enviarString("*** REIMPRESION ***\n");			
		}
		
		Sesion.crPrinterOperations.initializarPrinter();
		//Sesion.crPrinterOperations.enviarString(convertirCadena(justificar("MH",Sesion.getCaja().getSerial(),columnasImpresora)+"\n");

		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePagina(Venta, boolean) - end");
		}
	}
	
	//************** Métodos para impresora Fiscal ********************
	//*****************************************************************
	/**
	 * Realiza la impresion del encabezado de la factura de Venta indicada como parametro.
	 * @param ventaActual Venta que se imprimira.
	 */
	private static void crearEncabezadoFiscal(Venta ventaActual) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal(Venta) - start");
		}

		DecimalFormat enteroConCeros = new DecimalFormat("000");
		DecimalFormat numFactura = new DecimalFormat("00000000000");
		String cajero = "Cajero:" + Sesion.getUsuarioActivo().getNumFicha();
		String caja = "POS:" + enteroConCeros.format(Sesion.getCaja().getNumero());
		String transaccion = "Tr:" + numFactura.format(ventaActual.getNumTransaccion());
				
		String lineaIzquierda = cajero +  centrar(transaccion,18) + " ";
				
		Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(justificar(lineaIzquierda, caja, columnasImpresoraFiscal));
//		Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(crearLineaDeDivision(columnasImpresoraFiscal));

		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal(Venta) - end");
		}
	}
	
	/**
	 * Realiza la impresion de los detallles de la factura de Venta indicada como parametro.
	 * @param ventaActual Venta que se imprimira.
	 */
	private static void crearDetallesFiscal(Venta ventaActual, Vector<Integer> correlativosOrd) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearDetallesFiscal(Venta, Vector) - start");
		}

		// Para cada detalle de la venta
		DetalleTransaccion detalleActual;
		DecimalFormat precio = new DecimalFormat("#,##0.00");
		for (int i=0; i<correlativosOrd.size(); i++) {
			int posicion = ((Integer)correlativosOrd.elementAt(i)).intValue();
			detalleActual = (DetalleTransaccion)ventaActual.getDetallesTransaccion().elementAt(posicion - 1);
				
			Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(justificar(detalleActual.getProducto().getDescripcionCorta() + " 1 " 
				+ detalleActual.getProducto().getAbreviadoUnidadVenta(), detalleActual.getTipoEntrega().toUpperCase(),columnasImpresoraFiscal));
			
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
				
			if(ventaActual.isVentaExenta())
				Sesion.crFiscalPrinterOperations.imprimirItem("COD: " + codProd, detalleActual.getCantidad(), detalleActual.getPrecioFinal(), 0);
			else	
				Sesion.crFiscalPrinterOperations.imprimirItem("COD: " + codProd, detalleActual.getCantidad(), detalleActual.getPrecioFinal(), detalleActual.getProducto().getImpuesto().getPorcentaje());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("crearDetallesFiscal(Venta, Vector) - end");
		}
	}
	
	/**
	 * Realiza la impresion de los Totales de la factura de Venta indicada como parametro.
	 * @param ventaActual Venta que se imprimira.
	 */
	private static void crearTotalesFiscal(Venta ventaActual) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearTotalesFiscal(Venta) - start");
		}

		Pago pagoActual;
		String lineaIzq;
		String lineaDer;
		DecimalFormat precio = new DecimalFormat("#,##0.00");
		
		for (int i=0; i<ventaActual.getPagos().size(); i++) {
			pagoActual = (Pago)ventaActual.getPagos().elementAt(i);
			lineaIzq = pagoActual.getFormaPago().getNombre() + ":";
			lineaDer = precio.format(pagoActual.getMonto());
			
			if(lineaIzq.length() > 17) {
				lineaIzq = lineaIzq.substring(0,15);
				lineaIzq = lineaIzq + ":";
			}
			
			Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(alinearDerecha(lineaIzq + alinearDerecha(lineaDer, 17),columnasImpresoraFiscal));
		}
	
		if (ventaActual.getMontoVuelto() > 0) {
			lineaIzq = "CAMBIO:";
			lineaDer = precio.format(ventaActual.getMontoVuelto());
			Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(alinearDerecha(lineaIzq + alinearDerecha(lineaDer, 17),columnasImpresoraFiscal));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("crearTotalesFiscal(Venta) - end");
		}
	}
	
	/**
	 * Realiza la impresion del pie de página de la factura de Venta indicada como parametro.
	 * @param ventaActual Venta que se imprimira.
	 */
	private static void crearPieDePaginaFiscal(Venta ventaActual) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePaginaFiscal(Venta) - start");
		}

		float productosCaja = 0;
		float productosEntregaDom = 0;
		float productosDespacho = 0;
			
		DecimalFormat cant = new DecimalFormat("#0.00");
			
		for (int i=0; i<ventaActual.getLineasFacturacion(); i++){
			if (((DetalleTransaccion)ventaActual.getDetallesTransaccion().elementAt(i)).getTipoEntrega().equalsIgnoreCase(Sesion.ENTREGA_CAJA))
				productosCaja += ((DetalleTransaccion)ventaActual.getDetallesTransaccion().elementAt(i)).getCantidad();
			if (((DetalleTransaccion)ventaActual.getDetallesTransaccion().elementAt(i)).getTipoEntrega().equals(Sesion.ENTREGA_DESPACHO))
				productosDespacho += ((DetalleTransaccion)ventaActual.getDetallesTransaccion().elementAt(i)).getCantidad();
			if (((DetalleTransaccion)ventaActual.getDetallesTransaccion().elementAt(i)).getTipoEntrega().equals(Sesion.ENTREGA_DOMICILIO))
				productosEntregaDom += ((DetalleTransaccion)ventaActual.getDetallesTransaccion().elementAt(i)).getCantidad();	
		}
			
		Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(crearLineaDeDivision(columnasImpresoraFiscal));
		Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(justificar("Prod(s) Cte.: " + cant.format(productosCaja),Sesion.isCajaEnLinea()?"CL":"FL",columnasImpresoraFiscal));
		
		if (ventaActual.getCliente().getCodCliente() == null || ventaActual.isImpEmpleadoPieDePag()) {
			Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(" *** NO DA DERECHO A CREDITO FISCAL ***");			
		}	
		
		//********* Actualización: 09/02/2007. BECO: Cambios por razones de SENIAT
		//********* Se agrega la verificación de clientes de tipo empleados.
		//********* para estos casos no se imprimen como parte de la factura sino que se coloca
		//********* como nota al pie de página. Esto por requerimiento del SENIAT 
		if (ventaActual.isImpEmpleadoPieDePag()) { 
			String nombCte = ventaActual.getCliente().getNombreCompleto().trim();
			if (nombCte.length() > 26) {
				nombCte = nombCte.substring(0,26);
			}
			Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(justificar(convertirCadena("Colaborador: " + nombCte),"",columnasImpresoraFiscal));	
			Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(justificar(convertirCadena("Codigo: " + ventaActual.getCliente().getNumFicha().trim()),"",columnasImpresoraFiscal));		
		}
//		*****************************************
		
			
		
		if (ventaActual.isVentaExenta()) {
			Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(centrar("VENTA EXENTA DE IVA", columnasImpresoraFiscal));
		}
		//Se registra en la auDitoria el nro de productos que se lleva el cliente.Requerimiento solicitado
		Auditoria.registrarAuditoria("Nro. de productos que lleva el cliente: " + cant.format(productosCaja),'T');
		if (productosEntregaDom > 0) {
			//Se registra en la ausitoria el nro de productos que se facturó para entrega a domicilio.Requerimiento solicitado
			Auditoria.registrarAuditoria("Nro. de productos para entrega a domicilio: " + cant.format(productosEntregaDom),'T');
		}
		if(productosDespacho > 0) {
			//Se registra en la ausitoria el nro de productos que se facturó para entrega a domicilio.Requerimiento solicitado
			Auditoria.registrarAuditoria("Nro. de productos para despacho fuera de caja: " + cant.format(productosDespacho),'T');
		}
			
//		Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(crearLineaDeDivision(columnasImpresoraFiscal));
		// Si existe publicidad de la tienda, la insertamos 
		if (Sesion.getTienda().getPublicidades().size()>0) {
			// Agregamos las publicidades de la Factura
			for (int i=0; i<Sesion.getTienda().getPublicidades().size();i++)
				Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(centrar((String)Sesion.getTienda().getPublicidades().elementAt(i),columnasImpresoraFiscal));
	
//			Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(crearLineaDeDivision(columnasImpresoraFiscal));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePaginaFiscal(Venta) - end");
		}
	}
	
	/**
	 * Realiza la impresion del código de barra de la factura de Venta indicada como parametro.
	 * @param ventaActual Venta que se imprimira.
	 */
	private static void crearCodigoBarraFiscal(Venta ventaActual) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearCodigoBarraFiscal(Venta) - start");
		}

		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		DecimalFormat entero = new DecimalFormat("000");
		String codBarra = entero.format(ventaActual.getCodTienda())+df.format(ventaActual.getFechaTrans())+
						entero.format(ventaActual.getNumCajaFinaliza())+ventaActual.getNumTransaccion();
		if ((codBarra.length()%2)==0) {
			Sesion.crFiscalPrinterOperations.imprimirCodigoBarra(codBarra);
		} else {
			Sesion.crFiscalPrinterOperations.imprimirCodigoBarra(entero.format(ventaActual.getCodTienda()) +
															 df.format(ventaActual.getFechaTrans()) +
															 entero.format(ventaActual.getNumCajaFinaliza()) +
															 "0" +
															 ventaActual.getNumTransaccion());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("crearCodigoBarraFiscal(Venta) - end");
		}
	}
	
	/**
	 * Realiza la impresion de la factura de Venta indicada como parametro.
	 * @param ventaActual Venta que se imprimira.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static void imprimirFactura(Venta ventaActual, boolean reimpresion, boolean esContribuyenteOrdinario) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirFactura(Venta, boolean, boolean) - start");
		}

		DecimalFormat decf = new DecimalFormat("#,##0.00");

		//Antes de imprimir se ordena el detalle por tipo de entrega y por código de producto
		Vector<Integer> correlativosOrdenados = BaseDeDatosVenta.obtenerDetallesOrdenados(ventaActual);
		
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
			
			//********* Actualización: 09/02/2007. BECO: Cambios por razones de SENIAT
			//********* Se agrega la verificación de clientes de tipo empleados.
			//********* para estos casos no se imprimen como parte de la factura sino que se coloca
			//********* como nota al pie de página. Esto por requerimiento del SENIAT 
			
			if (ventaActual.getCliente().getCodCliente() == null ||
				ventaActual.isImpEmpleadoPieDePag()) { //empleados a pie de página
				Sesion.crFiscalPrinterOperations.abrirComprobanteFiscal(null, null);
			} else {
				//ventaActual.getCliente().setCodCliente(ventaActual.getCliente().getCodCliente().replace('N', 'V'));
				if(ventaActual.getCliente().getNombreCompleto().length() > 20) 
					Sesion.crFiscalPrinterOperations.abrirComprobanteFiscal(ventaActual.getCliente().getNombreCompleto().substring(0,21), ventaActual.getCliente().getCodCliente().replace('N', 'V'));
				else
					Sesion.crFiscalPrinterOperations.abrirComprobanteFiscal(ventaActual.getCliente().getNombreCompleto(), ventaActual.getCliente().getCodCliente().replace('N', 'V'));
			}
//			*****************************************
		
			crearEncabezadoFiscal(ventaActual);
			crearDetallesFiscal(ventaActual, correlativosOrdenados);
			
			//Cerramos el comprobante Fiscal en modo "A" (Parcial)
			Sesion.crFiscalPrinterOperations.cerrarComprobanteFiscal("A");
		
			//Imprimimos en Modo de texto fiscal los forma de pago y el pie de página
			Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(alinearDerecha(crearLineaDeDivision(17),columnasImpresoraFiscal));
			
			crearTotalesFiscal(ventaActual);
			crearPieDePaginaFiscal(ventaActual);
			crearCodigoBarraFiscal(ventaActual);

			//Cerramos de nuevo el comprobante para que cote el papel y finalice el comprobante
			Sesion.crFiscalPrinterOperations.cerrarComprobanteFiscal("A");
		
			try {
				Sesion.crFiscalPrinterOperations.commit();
			} catch (PrinterNotConnectedException e) {
				logger.error("imprimirFactura(Venta, boolean, boolean)", e);
			}
		} else {
			// Cadenas de secciones de la factura
			Sesion.crPrinterOperations.limpiarBuffer();
			Sesion.crPrinterOperations.initializarPrinter();
			crearEncabezado(ventaActual, reimpresion);
			crearDetalle(ventaActual, correlativosOrdenados);
			crearTotales(ventaActual);
			crearPagos(ventaActual);
			crearPieDePagina(ventaActual, reimpresion);
			Sesion.crPrinterOperations.alinear(1);
			Sesion.crPrinterOperations.estiloPresentacionCodigoBarras(2,1);
			SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
			DecimalFormat entero = new DecimalFormat("000");
			String codBarra = entero.format(ventaActual.getCodTienda())+df.format(ventaActual.getFechaTrans())+
							entero.format(ventaActual.getNumCajaFinaliza())+ventaActual.getNumTransaccion();
			if ((codBarra.length()%2)==0) {
				Sesion.crPrinterOperations.imprimirCodigoDeBarras(codBarra+"\n",7);
			} else {
				Sesion.crPrinterOperations.imprimirCodigoDeBarras(entero.format(ventaActual.getCodTienda()) +
																 df.format(ventaActual.getFechaTrans()) +
							  									 entero.format(ventaActual.getNumCajaFinaliza()) +
							  									 "0" +
							  									 ventaActual.getNumTransaccion()+"\n",7);
			}
			Sesion.crPrinterOperations.alinear(2);
			Sesion.crPrinterOperations.enviarString(convertirCadena(MediadorBD.obtenerSerialCaja()+"\n\n"));	
			Sesion.crPrinterOperations.cortarPapel();
			//Sesion.crPrinterOperations.abrirPuertoImpresora();
			//Abrimos el comprobante fiscal
/*			try {
				Sesion.crPrinterOperations.commit();
				Sesion.crPrinterOperations.activarReciboAuditoria();
				Sesion.crPrinterOperations.commit();
			} catch (PrinterNotConnectedException e) {
				logger.error("imprimirFactura(Venta, boolean, boolean)", e);
			}
*/			Sesion.crPrinterOperations.limpiarBuffer();
			Sesion.crPrinterOperations.initializarPrinter();
			//Sesion.crPrinterOperations.cerrarPuertoImpresora();
		}

		//****Se muestra el vuelto por visor y por pantalla
		double vuelto;
		if(ventaActual.getMontoVuelto()*100 < 1)
			vuelto = 0;
		else
			vuelto = ventaActual.getMontoVuelto();
		try { CR.crVisor.enviarString("CAMBIO",0,decf.format(vuelto),2); }
		catch (Exception e) {
			logger.error("imprimirFactura(Venta, boolean, boolean)", e);
		}
							
		//Se muestra el mensaje del vuelto al cajero
		PantallaVuelto pantallaVuelto = new PantallaVuelto(vuelto, true);
		MensajesVentanas.centrarVentanaDialogo(pantallaVuelto);
		//******
		
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirFactura(Venta, boolean, boolean) - end");
		}
	}
}
