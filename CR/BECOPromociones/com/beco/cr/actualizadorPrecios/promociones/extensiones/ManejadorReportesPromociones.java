/*
 * $Id: ManejadorReportesPromociones.java,v 1.2 2005/03/10 15:54:31 jgraterol$
 * ===========================================================================
 * (c) CENTROBECO, C.A.
 *
 * Proyecto		: CR
 * Paquete		: com.beco.cr.actualizadorPrecios.promociones.extensiones
 * Programa		: ManejadorReportesPromociones.java
 * Creado por	: jgraterol
 * Creado en 	: 04-dic-2008 9:42:31
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisión	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 *
 * ===========================================================================
 */
package com.beco.cr.actualizadorPrecios.promociones.extensiones;

import java.util.Vector;
import com.beco.cr.actualizadorPrecios.promociones.PromocionExt;
import com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago;
import com.becoblohm.cr.manejarventa.Cliente;


/**
 * <pre>
 * Proyecto: Modulo de promociones 
 * Clase: ManejadorReportesPromociones
 * </pre>
 * <p>
 * <a href="ManejadorReportesPromociones.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author jgraterol - $Author: jgraterol$
 * @version $Revision: 1.0 $ - $Date: 2008/12/04 09:42:31 $
 * @since 04-dic-2008
 */
/*
* En esta interface se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
* Fecha: agosto 2011
*/
public interface ManejadorReportesPromociones extends CRExtensionPromociones {
	
	public void imprimeRegalos(PromocionExt pE);
	
	public void imprimirProdComplementario(String nombre, String fecha, Vector<String> condiciones);
	
	public void imprimirVoucherPago(Cliente cte, FormaDePago fPago, double monto, int numTrans);
}
