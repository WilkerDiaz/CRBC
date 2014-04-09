/**
 * =============================================================================
 * Proyecto   : BECOExtensionesCR
 * Paquete    : com.beco.cr.reportes.gd4
 * Programa   : UtilesReportes.java
 * Creado por : jgraterol
 * Creado en  : 05-abr-10 11:56:10
 *
 * (c) CENTROBECO, C.A.
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

import java.util.Vector;
import com.becoblohm.cr.manejadorsesion.Sesion;

public class UtilesReportes extends Reporte{

	/**
	 * Crea el encabezado no fiscal con la información de la tienda
	 * donde se imprime el reporte
	 */
	public static void crearEncabezadoNoFiscal(){
		Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar(Sesion.getTienda().getRazonSocial(),columnasImpresoraFiscal),1);
		String lineaDeDireccion = "";
		if((Sesion.getTienda().getDireccionFiscal() != null)&&(!Sesion.getTienda().getDireccionFiscal().equalsIgnoreCase("")))
			lineaDeDireccion += Sesion.getTienda().getDireccionFiscal()+" ";
		if((Sesion.getTienda().getNombreSucursal() != null)&&(!Sesion.getTienda().getNombreSucursal().equalsIgnoreCase("")))
			lineaDeDireccion += "Sucursal: " + Sesion.getTienda().getNombreSucursal() + "."+" ";
		if((Sesion.getTienda().getDireccion() != null)&&(!Sesion.getTienda().getDireccion().equalsIgnoreCase("")))
			lineaDeDireccion += Sesion.getTienda().getDireccion()+" ";
		if (!lineaDeDireccion.equalsIgnoreCase("")) {
			Vector<String> nuevasLineasDireccion = dividirEnLineas(columnasImpresora,lineaDeDireccion);
			for (int i=0; i<nuevasLineasDireccion.size(); i++)
				Sesion.crFiscalPrinterOperations.enviarLineaNoFiscal(centrar((String)nuevasLineasDireccion.elementAt(i),columnasImpresoraFiscal),1);
		}
		
	}
}
