/**
 * =============================================================================
 * Proyecto   : CajaRegistradora
 * Paquete    : com.becoblohm.cr.reportes
 * Programa   : AperturaCajero.java
 * Creado por : gmartinelli
 * Creado en  : 31-may-04 14:21:10
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1	
 * Fecha       : 09-jun-04
 * Analista    : gmartinelli
 * Descripción : Integrado uso de impresora (imprime por el lado de los cheques por ahora)
 * =============================================================================
 */
package com.becoblohm.cr.extensiones.impl.reportes;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.crprinterdriver.PrinterNotConnectedException;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;

/** 
 * Descripción: 
 * 		Clase que maneja los perifericos del proyecto de CR. En principio maneja
 * únicamente la impresora. Restaría el manejo de Escaners, Visores, Teclados, etc.
 */
public class ImprimirCheque extends Reporte {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ImprimirCheque.class);
	
	/**
	 * Método imprimirReporte
	 * 	Realiza la impresion de la factura de Venta indicada como parametro.
	 * @param monto
	 * @param fecha
	 */
	public static void imprimirReporte(double monto, Date fecha) {
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirReporte(double, Date) - start");
		}

		boolean imprimirFrente = true;
		boolean imprimirDorso = true;
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
				if(Sesion.impresoraFiscal) {
					try {
						Sesion.crFiscalPrinterOperations.commit();
						if (imprimirFrente) {
							Sesion.crFiscalPrinterOperations.activarSlip();
							MensajesVentanas.aviso("Introduzca el documento en la impresora");
							Sesion.crFiscalPrinterOperations.impresionCheque(monto,Sesion.getTienda().getRazonSocial(),fecha, Sesion.getTienda().getMonedaBase());
							Sesion.crFiscalPrinterOperations.commit();
						}
						if (imprimirDorso) {
							if (MensajesVentanas.preguntarSiNo("¿Desea Imprimir el Dorso?")==0) {
								Sesion.crFiscalPrinterOperations.activarSlip(); 
								MensajesVentanas.aviso("Introduzca el dorso del documento");
								Sesion.crFiscalPrinterOperations.imprimirEndosoCheque(Sesion.getTipoCuentaCheque() + " del " + Sesion.getNombreBancoCheque(),Sesion.getNumeroCuentaCheque(),Sesion.getTienda().getRazonSocial().trim());
								Sesion.crFiscalPrinterOperations.commit();
								
								//***** Datos adicionales para el endoso 01/11/05..BECO
								Sesion.crFiscalPrinterOperations.abrirDocumentoNoFiscal();
								Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(justificar("   Tienda: " + Sesion.getTienda().getNumero(), "Caja: " + Sesion.getCaja().getNumero(), 40),0);
								Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal("   Cajero: " + Sesion.getUsuarioActivo().getNumFicha(), 0);
								Sesion.crFiscalPrinterOperations.cerrarDocumentoNoFiscal();
								Sesion.crFiscalPrinterOperations.commit();
//								*****
							}
						}

						Sesion.crFiscalPrinterOperations.desactivarSlip();
						Sesion.crFiscalPrinterOperations.commit();
					} catch (Exception e) {
						logger.error("imprimirReporte(double, Date)", e);
					}
				} else {
					// Cadenas de secciones de la factura
					Sesion.crPrinterOperations.limpiarBuffer();
					Sesion.crPrinterOperations.initializarPrinter();
					SimpleDateFormat diaMes = new SimpleDateFormat("dd/MM");
					SimpleDateFormat anio = new SimpleDateFormat("yyyy");
					DecimalFormat precio = new DecimalFormat("#,##0.00");
			
					Sesion.crPrinterOperations.initializarPrinter();
					//Sesion.crPrinterOperations.abrirPuertoImpresora();
					try {
						if (imprimirFrente) {
							MensajesVentanas.aviso("Introduzca el documento en la impresora");
							Sesion.crPrinterOperations.activarDocumentoApaisado();
							Sesion.crPrinterOperations.commit();
							Sesion.crPrinterOperations.limpiarBuffer();
							Sesion.crPrinterOperations.activarEspacioEntreLineasSencillo();
							Sesion.crPrinterOperations.activarFuenteImpresionNegrita();
							Sesion.crPrinterOperations.alinear(2);
							Sesion.crPrinterOperations.enviarString("-- NO ENDOSABLE --   \n");
							Sesion.crPrinterOperations.alinear(0);
							Sesion.crPrinterOperations.agregarLineaEnBlanco();
							Sesion.crPrinterOperations.agregarLineaEnBlanco();
							Sesion.crPrinterOperations.agregarLineaEnBlanco();
							Sesion.crPrinterOperations.agregarLineaEnBlanco();
							Sesion.crPrinterOperations.enviarString(diaMes.format(fecha) + "        " + anio.format(fecha) + "\n");
							Sesion.crPrinterOperations.agregarLineaEnBlanco();
							Sesion.crPrinterOperations.agregarLineaEnBlanco();
							Sesion.crPrinterOperations.enviarString("** "  + Sesion.getTienda().getMonedaBase() + " " + precio.format(monto) + " **\n");
							Sesion.crPrinterOperations.alinear(1);
							Sesion.crPrinterOperations.enviarString(Sesion.getTienda().getRazonSocial()+ "\n");
							Sesion.crPrinterOperations.agregarLineaEnBlanco();
							Sesion.crPrinterOperations.agregarLineaEnBlanco();
							Sesion.crPrinterOperations.agregarLineaEnBlanco();
							Sesion.crPrinterOperations.alinear(2);
							Sesion.crPrinterOperations.activarFuenteImpresionNormal();
							Sesion.crPrinterOperations.enviarString(precio.format(monto) + "\n");
							Sesion.crPrinterOperations.initializarPrinter();
							Sesion.crPrinterOperations.commit();
						}
						if (imprimirDorso) {
							MensajesVentanas.aviso("Introduzca el dorso del documento");
							Sesion.crPrinterOperations.limpiarBuffer();
							Sesion.crPrinterOperations.activarDocumentoNomal();
							Sesion.crPrinterOperations.alinear(1);
							Sesion.crPrinterOperations.enviarString("Unicamente para ser depositado en la\n");
							Sesion.crPrinterOperations.enviarString("cuenta " + Sesion.getTipoCuentaCheque() + "\n");
							Sesion.crPrinterOperations.enviarString("Nro. " + Sesion.getNumeroCuentaCheque() + "\n");
							Sesion.crPrinterOperations.enviarString("del Banco " + Sesion.getNombreBancoCheque() + "\n");
							Sesion.crPrinterOperations.enviarString(Sesion.getTienda().getRazonSocial());
							Sesion.crPrinterOperations.cortarPapel();
							Sesion.crPrinterOperations.initializarPrinter();
							Sesion.crPrinterOperations.commit();
							
						}
					} catch (PrinterNotConnectedException e) {
						logger.error("imprimirReporte(double, Date)", e);
					}
					//Sesion.crPrinterOperations.cerrarPuertoImpresora();
				}
			}
			
		}

		if (logger.isDebugEnabled()) {
			logger.debug("imprimirReporte(double, Date) - end");
		}
	}
}
