/**
 * =============================================================================
 * Proyecto   : TransferenciaInmediataProdsCR
 * Paquete    : com.beco.colascr.transferenciainmediata
 * Programa   : TransferirProducto.java
 * Creado por : gmartinelli
 * Creado en  : 28-jun-05 7:53:16
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1
 * Fecha       : 28-jun-05 7:53:16
 * Analista    : gmartinelli
 * Descripción : Implementación inicial.
 * =============================================================================
 */
package com.beco.colascr.transferenciainmediata; 

import java.util.Vector;

import com.beco.colascr.transferenciainmediata.manejoprocesos.HiloSincronizacion;
import com.beco.colascr.transferenciainmediata.mediadoresbd.Mediador;
import com.beco.colascr.transferenciainmediata.sesion.Caja;
import com.beco.colascr.transferenciainmediata.sesion.Sesion;

/**
 * Descripción:
 * 
 */

public class TransferirProducto {

	private static TransferirProducto transferencia;
	private int numTienda = 0;
	private Mediador mediador = null;
	/**
	 * Constructor para TransferirProducto.java
	 *
	 * 
	 */
	public TransferirProducto(int numTda, String usuario, String clave, String esquema, String pathFtp) {
		super();
		this.numTienda = numTda;
		this.mediador = new Mediador();
		new Sesion(usuario, clave, esquema, pathFtp);
	}

	/**
	 * Método iniciarSincronizacion
	 *	Inicia el Proceso de Sincronizacion de los productos existentes en la BD Local.
	 * Crea un hilo de sincronizacion para el servidor de la tienda y un hilo por cada 
	 * una de las cajas de la tienda. Se generan hilos para evitar bloqueos producto de
	 * cajas que se encuentren apagadas  
	 * 
	 * void
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	private void iniciarSincronizacion() {
		// Obtenemos los productos y las promociones a sincronizar.
		
		mediador.cargarProductos(Sesion.pathArchivos + "transfInmedProdCR");
		mediador.cargarCodigosExternos(Sesion.pathArchivos + "transfInmedCodExt");
		mediador.cargarPromociones(Sesion.pathArchivos + "transfInmedPromoCR");		
		mediador.cargarDetallePromociones(Sesion.pathArchivos + "transfInmedPromTda");
		/**
		 * Promociones
		 * **/
		mediador.cargarPromociones(Sesion.pathArchivos + "transfInmedPromoCR_Ext");
		mediador.cargarDetallePromociones(Sesion.pathArchivos + "transfInmedPromTda_Ext");
		mediador.cargarDetallePromocionesExt(Sesion.pathArchivos + "transfInmedPromTdaExt");
		mediador.cargarDonacion(Sesion.pathArchivos + "transfInmedDonacion");
		mediador.cargarTransaccionPremControl(Sesion.pathArchivos + "transfInmedTranPremControl");
		mediador.cargarCondicionPromocion(Sesion.pathArchivos + "transfInmedCondPromocion");
		/**
		 * */
		
		// Obtenemos los Estados, ciudades y Urbanizaciones a Sincronizar
		mediador.cargarEdoCiuUrb(Sesion.pathArchivos + "transfInmedAtcm");
		
		// Buscamos las Cajas y Sincronizamos cada una de las cajas de la tienda
		Vector<Caja> cajas = mediador.obtenerIpCajas(this.numTienda);
		for (int i=0; i<cajas.size(); i++) {
			//Proceso de Sinconizacion de Caja
			//System.out.println("Encontrada Caja: " + ((Caja)cajas.elementAt(i)).getNumeroCaja() + ". Ip: " + ((Caja)cajas.elementAt(i)).getIpCaja());
			new HiloSincronizacion((Caja)cajas.elementAt(i), mediador);
		}
	}


	/**
	 * Método esperarFinalizacionHilos
	 *	Espera que los hilos terminen su ejecucion. 
	 * void
	 */
	private void esperarFinalizacionHilos() {
		//System.exit(0);
	}

	/**
	 * Método main
	 * 
	 * @param args Arreglo de Parámetros de entrada
	 *    args[0]: Número de la Tienda a Sincronizar
	 *    args[1]: Usuario de la Bd
	 *    args[2]: Clave de la Bd
	 *    args[3]: Nombre de la Bd
	 * void
	 */
	public static void main(String[] args) {
		System.out.println("Iniciando Java Transferir...");
		int numTda = 0;
		if (args.length>=5) {
			numTda = Integer.valueOf(args[0].trim()).intValue();
			transferencia = new TransferirProducto(numTda, args[1].trim(), args[2].trim(), args[3].trim(), args[4].trim());
			transferencia.iniciarSincronizacion();
			transferencia.esperarFinalizacionHilos();
		} else {
			// Se debe especificar Todos los parámetros de entrada. Finalizamos el programa
			System.out.println("Deben especificarse los 5 parámetros");
			System.exit(1);
		}
		System.out.println("Finalizando Java Transferir...");
	}

	/**
	 * Método getNumTienda
	 * 
	 * @return
	 * int
	 */
	public int getNumTienda() {
		return numTienda;
	}

	/**
	 * Método getTransferencia
	 * 
	 * @return
	 * TransferirProducto
	 */
	public static TransferirProducto getTransferencia() {
		return transferencia;
	}

}
