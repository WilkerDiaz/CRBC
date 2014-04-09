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
package com.beco.cr.reportes.gd4;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.gui.PantallaVuelto;
import com.becoblohm.cr.manejadorauditoria.Auditoria;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstadoVenta;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.extensiones.ActualizadorPreciosFactory;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.manejarventa.CondicionVenta;
import com.becoblohm.cr.manejarventa.DetalleTransaccion;
import com.becoblohm.cr.manejarventa.Venta;
import com.becoblohm.cr.mediadoresbd.BaseDeDatosVenta;
import com.becoblohm.cr.utiles.MathUtil;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.crprinterdriver.CRFiscalPrinterOperations;
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
				
		String lineaIzquierda = cajero +  centrar(transaccion,16);
				
		Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(justificar(lineaIzquierda, caja, columnasImpresoraFiscal));
		Sesion.crFiscalPrinterOperations.imprimirTextoFiscal(crearLineaDeDivision(columnasImpresoraFiscal));

		if (logger.isDebugEnabled()) {
			logger.debug("crearEncabezadoFiscal(Venta) - end");
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
				
			Sesion.crFiscalPrinterOperations.imprimirItemVenta(justificar(detalleActual.getProducto().getDescripcionCorta() + " 1 " 
				+ detalleActual.getProducto().getAbreviadoUnidadVenta(), detalleActual.getTipoEntrega().toUpperCase(),columnasImpresoraFiscal), 0, 0, 0);
			
			if (!detalleActual.getCondicionVenta().equals(Sesion.condicionNormal)) {
				String obsequio="";
				Vector<String> condicionDescontado = new Vector<String>();
				condicionDescontado.addElement(Sesion.condicionDescontadoPorCombo);
				condicionDescontado.addElement(Sesion.condicionProductoGratis);
				Vector<String> condicionesPromocionDescuento = new Vector<String>();
				condicionesPromocionDescuento.addElement(Sesion.condicionProductoGratis);
				condicionesPromocionDescuento.addElement(Sesion.condicionComboPorCantidades);
				if(detalleActual.contieneAlgunaCondicion(condicionDescontado)){
					CondicionVenta cv = detalleActual.getPrimeraCondicion(condicionesPromocionDescuento);
					if(cv.getPorcentajeDescuento()==100)
						obsequio = "(OB)";
				}
				
				//IROJAS: 01/11/2013 Cambio temporal para no imprimir la línea de Pr y PF en el detalle cuando se encuentra una promoción de combo en el detalle
				if (!detalleActual.contieneAlgunaCondicion(Sesion.condicionesCombo) && !detalleActual.contieneAlgunaCondicion(Sesion.condicionesComboPrecioFinal)) {
				// Si se ha aplicado alguna rebaja (Promocion, Empaque, Cambio de Precio) se especifica
				Sesion.crFiscalPrinterOperations.imprimirItemVenta(" PR:" + precio.format(detalleActual.getProducto().getPrecioRegular())
								+ " " + detalleActual.getCondicionVenta() + ":" + precio.format(detalleActual.getPrecioFinal())+obsequio, 0, 0, 0);
				}
			}
	
			String codProd = detalleActual.getProducto().getCodProducto();
			int longitud = codProd.length();
			if ((Sesion.getLongitudCodigoInterno()>-1) && (longitud>Sesion.getLongitudCodigoInterno())) {
				codProd = codProd.substring(codProd.length()-Sesion.getLongitudCodigoInterno()); 
				for (int j=0;j<longitud-Sesion.getLongitudCodigoInterno();j++) {
					codProd = " " + codProd; 
				}
			}
			
			if (detalleActual.getCantidad() > 1) {
				Sesion.crFiscalPrinterOperations.imprimirItemVenta(detalleActual.getCantidad() + " x " + precio.format(detalleActual.getPrecioFinal()), 0, 0, 0);
			}
				
			if(ventaActual.isVentaExenta())
				Sesion.crFiscalPrinterOperations.imprimirItemVenta("COD: " + codProd, detalleActual.getCantidad(), detalleActual.getPrecioFinal(), 0);
			else	
				Sesion.crFiscalPrinterOperations.imprimirItemVenta("COD: " + codProd, detalleActual.getCantidad(), detalleActual.getPrecioFinal(), detalleActual.getProducto().getImpuesto().getPorcentaje());
			
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
		double montoAcumulado =0;
		for (int i=0; i<ventaActual.getPagos().size(); i++) {
			pagoActual = (Pago)ventaActual.getPagos().elementAt(i);
			if(i==ventaActual.getPagos().size()-1 && pagoActual.getMonto()<MathUtil.roundDouble(ventaActual.consultarMontoTrans()-montoAcumulado))
				pagoActual.setMonto(MathUtil.roundDouble(ventaActual.consultarMontoTrans()-montoAcumulado));
			else
				montoAcumulado+=pagoActual.getMonto();
			
			// Forma de Pago Tipo 17 la pasamos como Tipo 1 igualmente para que reconozca la Impresora Fiscal
			switch(pagoActual.getFormaPago().getTipo()){
			case 17:
				Sesion.crFiscalPrinterOperations.realizarPago(pagoActual.getFormaPago().getNombre(), pagoActual.getMonto(), 1, 0);
				break;
			case 20:	
				Sesion.crFiscalPrinterOperations.realizarPago("Obsequio (OB)", pagoActual.getMonto(), 6, 0);
				break;
			case 2:
				boolean imprimirFrente = true;
				boolean imprimirDorso = true;
				
				Sesion.crFiscalPrinterOperations.realizarPago(pagoActual.getFormaPago().getNombre(), pagoActual.getMonto(),pagoActual.getFormaPago().getTipo(), 0);
				
				try {
					String strImpF = InitCR.preferenciasCR.getConfigStringForParameter("facturacion", "imprimirFrenteCheque"); 
					String strImpD = InitCR.preferenciasCR.getConfigStringForParameter("facturacion", "imprimirDorsoCheque");
					if (strImpF != null)
						imprimirFrente = strImpF.equals("S");
					if (strImpD != null)
						imprimirDorso = strImpD.equals("S");
				} catch (NoSuchNodeException e1) {
					logger.error("imprimirReporte(double, Date)", e1);
				} catch (UnidentifiedPreferenceException e1) {
					logger.error("imprimirReporte(double, Date)", e1);
				}
				if (imprimirFrente || imprimirDorso) {
					if (MensajesVentanas.preguntarSiNo("¿Desea Imprimir el Cheque?")==0) {
						if (imprimirFrente) {
							Sesion.crFiscalPrinterOperations.impresionCheque(pagoActual.getMonto(), Sesion.getTienda().getRazonSocial(), ventaActual.getFechaTrans(), Sesion.getTienda().getMonedaBase());
						}
						if (imprimirDorso) {
							if (MensajesVentanas.preguntarSiNo("¿Desea Imprimir el Dorso?")==0) {
									Sesion.crFiscalPrinterOperations.imprimirEndosoCheque(Sesion.getTipoCuentaCheque() + " del " + Sesion.getNombreBancoCheque(),Sesion.getNumeroCuentaCheque(),Sesion.getTienda().getRazonSocial().trim());
							}
						}
					}
				}
				break;
				
			default:
				Sesion.crFiscalPrinterOperations.realizarPago(pagoActual.getFormaPago().getNombre(), pagoActual.getMonto(),pagoActual.getFormaPago().getTipo(), 0);
				break;
			}		
		}
	
		if (logger.isDebugEnabled()) {
			logger.debug("crearTotalesFiscal(Venta) - end");
		}
	}
	

	
	/**
	 * Realiza la impresion del pie de página de la factura de Venta indicada como parametro.
	 * @param ventaActual Venta que se imprimira.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'HashMap' e 'Iterator', 
	* se eliminaron variables no usadas
	* Fecha: agosto 2011
	*/
	private static void crearPieDePaginaFiscal(Venta ventaActual) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearPieDePaginaFiscal(Venta) - start");
		}
		/*
		 * Promociones
		 * */
		double montodonaciones=ventaActual.getMontoDonaciones();
		double montoahorrado=ventaActual.getMontoAhorrado();
		HashMap<String,Vector<Object>> obsequios = FacturaVenta.obtenerResumenObsequios(ventaActual);
		/**/
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
			
		
		//****IROJAS: 01/11/2013 Cambio para no imprimir la línea de división por el tema del ahorro de papel 
		//Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(crearLineaDeDivision(columnasImpresoraFiscal), 0);
		
		
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Prod(s) Cte.: " + cant.format(productosCaja),Sesion.isCajaEnLinea()?"CL":"FL",columnasImpresoraFiscal), 3);

		/*
		 * Promociones
		 * */
		if (montodonaciones>0.009)
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Gracias por su aporte de Bs:"," "+cant.format(montodonaciones),columnasImpresoraFiscal), 1);
		
		
		
		//****IROJAS: 01/11/2013 Cambio temporal para no imprimir la línea de Pr y PF en el detalle cuando se encuentra una promoción de combo en el detalle
		//****Se elimina la línea: Usted se ahorró
		//if (montoahorrado>0.009)
		//	Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Usted se ahorro Bs :", " "+cant.format(montoahorrado),columnasImpresoraFiscal), 1);
		
		
		//****IROJAS: 01/11/2013 Cambio temporal para no imprimir la línea de Pr y PF en el detalle cuando se encuentra una promoción de combo en el detalle
		//****Se elimina la línea: Monto comprado por promoción
		//Impresión de sorteo, jperez
	/*	int c = 0;
		try{
			if(CR.me.getPromoMontoCantidad().keySet().size()!=0){
				DecimalFormat df = new DecimalFormat("#0.00");
				Iterator<String> iteraPromo = CR.me.getPromoMontoCantidad().keySet().iterator();
				while (iteraPromo.hasNext()){	
					String clave = iteraPromo.next();
					ArrayList<Object> info =  (new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().getPromoSorteoInfo(clave);					
					if (info.size()>0){
						if(c==0)Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("Monto comprado por promoción: ","",columnasImpresoraFiscal), 1);
						String promo = (String) info.get(0);
						double monto = ((Double)info.get(1));
						Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar(promo,df.format(monto),columnasImpresoraFiscal), 1);
						//Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("",df.format(monto),columnasImpresoraFiscal), 1);
						c++;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}*/
		//ACTUALIZACION BECO: LISTADO DE PRODDUCTOS EN DESCUENTO POR OBSEQUIO DE ALGUNA PROMOCION
		if(obsequios.keySet().size()!=0){
	
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("UD. RECIBIO OBSEQUIO EN:", 1);
			Iterator<String> iteraObsequios = obsequios.keySet().iterator();
			while (iteraObsequios.hasNext()){	
				/*
				* 
				* clave='codproducto+codpromocion' 
				* valor='Vector(codproducto, cantidad, codpromocion, porcentajedescuento)'
				*/
				String clave =  iteraObsequios.next();
				Vector<Object> valor = obsequios.get(clave);

				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("PROD: "+(String)valor.elementAt(0)+" OBSEQUIO","",columnasImpresoraFiscal), 2);
			}
		}
			
		/**/
		if ((ventaActual.getCliente().getCodCliente() == null)||(!(CR.meVenta.isClienteContribuyente(ventaActual)))) 
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(" *** NO DA DERECHO A CREDITO FISCAL ***", 2);		
		if ((ventaActual.getCliente().getCodCliente() != null)&&(!(ventaActual.isImpEmpleadoPieDePag()) && (!(CR.meVenta.isClienteContribuyente(ventaActual)))))
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("GRACIAS POR SU COMPRA", 2);	
		
		//********* Actualización: 09/02/2007. BECO: Cambios por razones de SENIAT
		//********* Se agrega la verificación de clientes de tipo empleados.
		//********* para estos casos no se imprimen como parte de la factura sino que se coloca
		//********* como nota al pie de página. Esto por requerimiento del SENIAT 
		
		//Modificado por Cambio de Impresión CRM wdiaz

		if (((ventaActual.getCliente().getCodCliente() != null)&&(!(CR.meVenta.isClienteContribuyente(ventaActual))))||(ventaActual.isImpEmpleadoPieDePag())) 
		{ 
				String nombCte = ventaActual.getCliente().getNombreCompleto().trim();		
				if(nombCte.length() > 26) { 
					if(ventaActual.isImpEmpleadoPieDePag())
						Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar(convertirCadena("Colaborador: " + nombCte.substring(0,26)),"",columnasImpresoraFiscal), 0);	
					else
						Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar(convertirCadena(" " + nombCte.substring(0,26)),columnasImpresoraFiscal), 0);
					
					if(nombCte.length() > 40) {
						Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar(convertirCadena("              " + nombCte.substring(26,40)),"",columnasImpresoraFiscal), 0);//Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("              " + nombCte.substring(40));
						Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar(convertirCadena("              " + nombCte.substring(40)),"",columnasImpresoraFiscal), 0);
					} else 
					    Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar(convertirCadena("              " + nombCte.substring(26)),"",columnasImpresoraFiscal), 0);
				
					if(ventaActual.isImpEmpleadoPieDePag())
						Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar(convertirCadena("Codigo: " + ventaActual.getCliente().getNumFicha().trim()),"",columnasImpresoraFiscal), 0);
					
				} else
					if(ventaActual.isImpEmpleadoPieDePag()){
						Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar(convertirCadena("Colaborador: " + nombCte.trim()),"",columnasImpresoraFiscal), 0);	
						Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar(convertirCadena("Codigo: " + ventaActual.getCliente().getNumFicha().trim()),"",columnasImpresoraFiscal), 0);	
					}else
						Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar(convertirCadena(" " + nombCte), columnasImpresoraFiscal),0);
				
		}
		
		
	
//		*****************************************
		
			
		
		if (ventaActual.isVentaExenta()) {
			Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar("VENTA EXENTA DE IVA", columnasImpresoraFiscal), 0);
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
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar((String)Sesion.getTienda().getPublicidades().elementAt(i),columnasImpresoraFiscal), 0);
	
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
			Sesion.crFiscalPrinterOperations.imprimirCodigoBarra(codBarra, true);
		} else {
			Sesion.crFiscalPrinterOperations.imprimirCodigoBarra(entero.format(ventaActual.getCodTienda()) +
															 df.format(ventaActual.getFechaTrans()) +
															 entero.format(ventaActual.getNumCajaFinaliza()) +
															 "0" +
															 ventaActual.getNumTransaccion(), true);
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
		
		//if (Sesion.aperturaGavetaPorImpresora) {
		
		//10/05/2010 IROJAS.. Modificación de apertura de gaveta para que siempre se abra antes de imprimir
		//ya que con la nueva impresora se bloquea la pantalla hasta finalizar la impresión y pantalla vuelto se abre al 
		//final de la misma (pantalla vuelto es quién apertura la gaveta originalmente). 
		
			try {
				CR.me.abrirGaveta(false);
			} catch (Exception e){
				logger.error("imprimirFactura(Venta)", e);
			}
			
		 //}
		//********
 
		//Se realiza la impresion de la factura. Se identifica si es por impresora Fiscal
		if(Sesion.impresoraFiscal) {
			Sesion.crFiscalPrinterOperations.setCalculoMetodoExclusivo(0);
			
			//********* Actualización: 09/02/2007. BECO: Cambios por razones de SENIAT
			//********* Se agrega la verificación de clientes de tipo empleados.
			//********* para estos casos no se imprimen como parte de la factura sino que se coloca
			//********* como nota al pie de página. Esto por requerimiento del SENIAT 
			
			Sesion.crFiscalPrinterOperations.abrirComprobanteFiscal();
		
			if ((ventaActual.getCliente().getCodCliente()!=null)&&(CR.meVenta.isClienteContribuyente(ventaActual)) &&(!ventaActual.isImpEmpleadoPieDePag()))
			{ 
				//empleados o clientes no contribuyentes a pie de página
				if(ventaActual.getCliente().getNombreCompleto().length() > 20) { 
					Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("RAZON SOCIAL: " + ventaActual.getCliente().getNombreCompleto().substring(0,20));
					if(ventaActual.getCliente().getNombreCompleto().length() > 40) {
						Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("              " + ventaActual.getCliente().getNombreCompleto().substring(20,40));
						Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("              " + ventaActual.getCliente().getNombreCompleto().substring(40));
					} else 
						Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("              " + ventaActual.getCliente().getNombreCompleto().substring(20));
				} else
					Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("RAZON SOCIAL: " + ventaActual.getCliente().getNombreCompleto());
							
				Sesion.crFiscalPrinterOperations.imprimirTextoFiscal("RIF/C.I: " + ventaActual.getCliente().getCodCliente().replace('N', 'V'));
			}
//			*****************************************
		
			crearEncabezadoFiscal(ventaActual);
			crearDetallesFiscal(ventaActual, correlativosOrdenados);
			
			//SubTotal de La Factura (Cierre Parcial)
			Sesion.crFiscalPrinterOperations.subTotalTransaccion(ventaActual.consultarMontoTrans(), 1, 3);
		
			crearTotalesFiscal(ventaActual);
			//crearPieDePaginaFiscal(ventaActual);
			Sesion.crFiscalPrinterOperations.cerrarComprobanteFiscalVentas("A");
			try {
				Sesion.crFiscalPrinterOperations.commit();
			} catch (PrinterNotConnectedException e) {
				logger.error("imprimirFactura(Venta, boolean, boolean)", e);
			}
			//ACTUALIZACION BECO: Impresora fiscal GD4
			while (CR.meVenta.getTransaccionPorImprimir()!=null || 
					CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
					MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()) {
				try {
					if(!Sesion.pantallaVueltoAceptada){
						//****Se muestra el vuelto por visor y por pantalla
						double vuelto;
						if(ventaActual.getMontoVuelto()*100 < 1)
							vuelto = 0;
						else{
								vuelto = ventaActual.getMontoVuelto();
							try { CR.crVisor.enviarString("CAMBIO",0,decf.format(vuelto),2); }
							catch (Exception e) {
								logger.error("imprimirFactura(Venta, boolean, boolean)", e);
							}
						}
											
						//Se muestra el mensaje del vuelto al cajero
						PantallaVuelto pantallaVuelto = new PantallaVuelto(vuelto, false);
						MensajesVentanas.centrarVentanaDialogo(pantallaVuelto);
						Sesion.pantallaVueltoAceptada=true; 
						//******
					} else {
						Thread.sleep(500);
					}
				} catch (InterruptedException e) {
					// TODO Bloque catch generado automáticamente
					e.printStackTrace();
				}
				if(MaquinaDeEstadoVenta.errorAtencionUsuario){
					MensajesVentanas.aviso("Hay un documento en curso \nEspere un momento e intente nuevamente");
				}
			}
			if (Sesion.impresoraFiscal) {
				Sesion.pantallaVueltoAceptada=false;
				MaquinaDeEstadoVenta.setDocumentoNoFiscalPorImprimir(true);
			}
			
			crearCodigoBarraFiscal(ventaActual);

			try {
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
			}

			// Abrimos un Documento No Fiscal para Imprimir el Pie de Página de la Factura
			Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
			crearPieDePaginaFiscal(ventaActual);
			Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
			
			
			//Cerramos de nuevo el comprobante para que cote el papel y finalice el comprobante
			Sesion.crFiscalPrinterOperations.cortarPapel();
		
			try {
				Sesion.crFiscalPrinterOperations.commit();
			} catch (PrinterNotConnectedException e) {
				logger.error("imprimirFactura(Venta, boolean, boolean)", e);
			}
		} else {
			// Esta Implementaccion no maneja Impresora NoFiscal
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirFactura(Venta, boolean, boolean) - end");
		}
	}
	
	/**
	 * Obtiene el resumen de los productos que recibieron obsequios
	 * @param venta
	 * @return HashMap 
	 * clave='codproducto+codpromocion' 
	 * valor='Vector(codproducto, cantidad, codpromocion, porcentajedescuento)'
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static HashMap<String,Vector<Object>> obtenerResumenObsequios(Venta venta){
		HashMap<String,Vector<Object>> obsequios = new HashMap<String,Vector<Object>>();
		Vector<String> condicionesObsequios = new Vector<String>();
		condicionesObsequios.addElement(Sesion.condicionDescontadoPorCombo);
		condicionesObsequios.addElement(Sesion.condicionProductoGratis);

		for(int i=0;i<venta.getDetallesTransaccion().size();i++){
			DetalleTransaccion detalle = (DetalleTransaccion)venta.getDetallesTransaccion().elementAt(i);
			if(detalle.contieneAlgunaCondicion(condicionesObsequios)){
				Vector<String> condicionesObsequiosCC =  new Vector<String>();
				condicionesObsequiosCC.addElement(Sesion.condicionComboPorCantidades);
				condicionesObsequiosCC.addElement(Sesion.condicionProductoGratis);
				CondicionVenta cv = detalle.getPrimeraCondicion(condicionesObsequiosCC);
				if(cv.getPorcentajeDescuento()==100){
					Vector<Object> valor =  new Vector<Object>();
					Vector<Object> obsequiosInstance = obsequios.get(detalle.getProducto().getCodProducto()+cv.getCodPromocion()); 
					if(obsequiosInstance==null){
						valor.addElement(detalle.getProducto().getCodProducto());
						valor.addElement(new Float(detalle.getCantidad()));
						valor.addElement(new Integer(cv.getCodPromocion()));
						valor.addElement(new Double(cv.getPorcentajeDescuento()));
						obsequios.put(detalle.getProducto().getCodProducto()+cv.getCodPromocion(), valor);
					} else {
						obsequiosInstance.set(1, new Float(((Float)obsequiosInstance.elementAt(1)).floatValue()+detalle.getCantidad()));
					}
				}
			}
		}
		return obsequios;
	}
}
