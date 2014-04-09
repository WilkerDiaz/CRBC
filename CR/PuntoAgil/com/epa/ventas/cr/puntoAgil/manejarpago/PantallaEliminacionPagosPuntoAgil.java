/*
 * $Id: PantallaEliminacionPagosPuntoAgil.java,v 1.3 2006/09/28 19:08:26 programa4 Exp $
 * ===========================================================================
 *
 * Proyecto		: PuntoAgil
 * Paquete		: com.epa.ventas.cr.puntoAgil.manejarpago
 * Programa		: PantallaEliminacionPagosPuntoAgil.java
 * Creado por	: programa4
 * Creado en 	: 28/06/2006 11:20:18 AM
 * (C) Copyright 2006 Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisión	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: PantallaEliminacionPagosPuntoAgil.java,v $
 * Revision 1.3  2006/09/28 19:08:26  programa4
 * Actualizacion por requerimientos credicard
 * Se cargan datos de programas de lealtad segun banco seleccionado
 * Modificados mensajes de error para hacerlos mas legibles
 * Verificados titulos para indicar version del punto agil
 *
 * Revision 1.2  2006/07/13 16:05:17  programa4
 * Correcion de mensaje de "Punto Agil" a "El Punto Agil"
 *
 * Revision 1.1  2006/07/05 15:25:53  programa4
 * Agregado soporte a provimillas y actualizado anulacion
 *
 *
 * ===========================================================================
 */
/**
 * Clase PantallaEliminacionPagosPuntoAgil
 *
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.3 $ - $Date: 2006/09/28 19:08:26 $
 * @since 28/06/2006
 */
package com.epa.ventas.cr.puntoAgil.manejarpago;

import java.util.Vector;

import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.extensiones.impl.manejarpago.PantallaEliminacionPagos;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.ventas.cr.puntoAgil.Constantes;
import com.epa.ventas.cr.puntoAgil.DatosPagoPuntoAgil;

/**
 * @author programa4 - $Author: programa4 $
 * @version $Revision: 1.3 $ - $Date: 2006/09/28 19:08:26 $
 * @since 28/06/2006
 */
public class PantallaEliminacionPagosPuntoAgil extends PantallaEliminacionPagos {

	/**
	 * @param _mtoTrans
	 * @param _realizados
	 * @param _cte
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public PantallaEliminacionPagosPuntoAgil(double _mtoTrans,
			Vector<Pago> _realizados, Cliente _cte) {
		super(_mtoTrans, _realizados, _cte);
	}

	protected void initialize() {
		super.initialize();
		this.setTitle(this.getTitle() + Constantes.TITULO_VERSION);
	}

	protected void eliminarPago(int _indice) {
		Pago pago = (Pago) this.pagosRealizados.get(_indice);
		if (pago instanceof DatosPagoPuntoAgil) {
			MensajesVentanas
				.mensajeError("ESTE PAGO FUE PROCESADO POR EL PUNTO AGIL, "
						+ "RECUERDE ANULAR LA OPERACION SI CORRESPONDE");
		}
		super.eliminarPago(_indice);
	}
}
