/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.colascr.transferencias.sincronizador
 * Programa   : SincCentralTdaProds.java
 * Creado por : Ileana Rojas, Gabriel Martinelli
 * Creado en  : 21/02/2005 04:16:59 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versi�n     : 
 * Fecha       : 
 * Analista    : 
 * Descripci�n :
 * =============================================================================
 */
package com.beco.colascr.transferencias.sincronizador;

import java.util.Hashtable;
import java.util.TimerTask;

import com.beco.colascr.transferencias.comunicacionbd.BeansSincronizador;
import com.beco.colascr.transferencias.configuracion.PoliticaTarea;
import com.beco.colascr.transferencias.configuracion.Sesion;
import com.epa.sincronizador.datafile.Principal;

/**
 * Descripci�n:
 * 
 */

public class SincCentralTdaProds extends TimerTask {

	public static Sincronizador sincronizador = null; 

	/**
	 * Constructor para SincronizarServidorCaja.java
	 *
	 *
	 * 
	 */
	public SincCentralTdaProds(Sincronizador sync) {
		super();
		sincronizador = sync;
	}
	/**
	 * M�todo run
	 *
	 */
	/*
	* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
	* S�lo se parametriz� el tipo de dato contenido en los 'HashTable' 
	* Fecha: agosto 2011
	*/
	public void run() {
		long inicioTotal = System.currentTimeMillis();
		try {
			Sesion.getFrecuenciaSincProductos().setEstadoTarea(PoliticaTarea.INICIADA);
			
			System.out.println("\n ***** Iniciando Ciclo de Sincronizaci�n ServCentral -> ServTienda *****");
			
			System.out.print(" Sincronizando Entidad CR.producto  ----->");
			long inicio = System.currentTimeMillis();
			BeansSincronizador.migrarArchivoProducto(Sesion.getPathArchivos() + "productos", "producto");
			long fin = System.currentTimeMillis();
			System.out.println(" Finalizada Sincronizaci�n. Tard� " + (fin-inicio) + " MiliSegs.");
			
			System.out.print(" Sincronizando Entidad CR.codexterno  ----->");
			inicio = System.currentTimeMillis();
			BeansSincronizador.migrarArchivoCodExterno(Sesion.getPathArchivos() + "codExternos", "prodcodigoexterno");
			fin = System.currentTimeMillis();
			System.out.println(" Finalizada Sincronizaci�n. Tard� " + (fin-inicio) + " MiliSegs.");
			
			System.out.print(" Sincronizando Entidad CR.promocion  ----->");
			inicio = System.currentTimeMillis();
			BeansSincronizador.migrarArchivoPromocion(Sesion.getPathArchivos() + "promociones", "promocion");
			//***** IROJAS 26/02/2009 Cambio de sincronizaci�n de archivos que vienen de compiere
			//***** a las tablas originales de CR: promociones y detallepromociones
			BeansSincronizador.migrarArchivoPromocion(Sesion.getPathArchivos() + "promociones_Ext", "promocion");
			//*****
			fin = System.currentTimeMillis();
			System.out.println(" Finalizada Sincronizaci�n. Tard� " + (fin-inicio) + " MiliSegs.");
			
			System.out.print(" Sincronizando Entidad CR.detallepromocion  ----->");
			inicio = System.currentTimeMillis();
			BeansSincronizador.migrarArchivoDetPromocion(Sesion.getPathArchivos() + "detPromociones", "detallepromocion");
			//***** IROJAS 26/02/2009 Cambio de sincronizaci�n de archivos que vienen de compiere
			//***** a las tablas originales de CR: promociones y detallepromociones
			BeansSincronizador.migrarArchivoDetPromocion(Sesion.getPathArchivos() + "detPromociones_Ext", "detallepromocion");
			//*****
			//***** IROJAS 18/03/2009 Cambio de sincronizaci�n de archivos que vienen de compiere
			//***** a las tablas originales de CR: promociones y detallepromociones
			BeansSincronizador.migrarArchivoDetPromocionExtendida(Sesion.getPathArchivos() + "detPromocionesExt", "detallepromocionext");
			BeansSincronizador.migrarArchivoDonacion(Sesion.getPathArchivos() + "donacion", "donacion");
			BeansSincronizador.migrarArchivoTransaccionPremiada(Sesion.getPathArchivos() + "transaccionPremControl", "transaccionpremcontrol");
			BeansSincronizador.migrarArchivoCondicionPromocion(Sesion.getPathArchivos() + "condicionPromocion", "condicionpromocion");
			BeansSincronizador.migrarArchivoProdSeccion(Sesion.getPathArchivos() + "prodSeccion", "prodseccion");
			//*****	
			
			Hashtable<String,Boolean> promocionesTransf = BeansSincronizador.crearTablaTmpDetPromocion(Sesion.getPathArchivos() + "detPromociones", "detallepromociontransf", "detallepromocion");
			BeansSincronizador.actualizarEdoPromociones();
			fin = System.currentTimeMillis();
			System.out.println(" Finalizada Sincronizaci�n. Tard� " + (fin-inicio) + " MiliSegs.");
			
			/*System.out.print(" Verificando promociones anuladas  ----->");
			inicio = System.currentTimeMillis();
			BeansSincronizador.verificarPromocionesAnuladas(promocionesTransf);
			fin = System.currentTimeMillis();
			System.out.println(" Finalizada verificaci�n de promociones anuladas. Tard� " + (fin-inicio) + " MiliSegs.");
			 Se comento ya que no se va a utilizar m�s..WDIAZ 01-2013
			 */
			// Generamos los DataFile de productos para la sincronizaci�n de las Cajas Registradoras
			//****
			//IROJAS: agregadas tablas de promociones m�dulo nuevo 04/03/2009
			//IROJAS: Agregadas las tablas de producto y prodcodigoexterno para que sean sincronizadas por DataFile
			String argsDataFile[] = {"server", "CR.promocion", "CR.detallepromocion", "CR.puntoAgilCuentasEspeciales", 
					"CR.puntoAgilFormadePago", "CR.puntoAgilProcesoEstadoCaja", "CR.puntoAgilTipoCuenta", "CR.detallepromocionext", 
					"CR.prodseccion", "CR.catdep", "CR.donacion", "CR.condicionpromocion", "CR.producto", "CR.prodcodigoexterno"};
			Principal.main(argsDataFile);
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
		long finTotal = System.currentTimeMillis();
		System.out.println("\n ***** Finalizada Sincronizaci�n ServCentral -> ServTienda. Tiempo Total: "  + (finTotal - inicioTotal) + " MiliSegs. *****");
		Sesion.getFrecuenciaSincProductos().setDuracionSinc(finTotal-inicioTotal);
		Sesion.getFrecuenciaSincProductos().setEstadoTarea(PoliticaTarea.FINALIZANDO);
		/*ServidorCR.sincronizador.configurarTareaProgramada(Sesion.getFrecuenciaSincProductos());
		ServidorCR.sincronizador.actualizarMensajesDetalles();*/
	}
}