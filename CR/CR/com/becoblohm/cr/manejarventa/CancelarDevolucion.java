/*
 * Creado el 13-sep-06
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.becoblohm.cr.manejarventa;

import java.sql.ResultSet;
import java.sql.SQLException;


import com.becoblohm.cr.mediadoresbd.ConexionServCentral;

/**
 * @author yzambrano
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class CancelarDevolucion extends Thread{

	/**
		 * Logger for this class
		 */
	/*	private static final Logger logger = Logger
				.getLogger(BaseDeDatosPago.class);*/

	private int numTienda;
	private String fecha;
	private int numCaja;
	private int numTransaccion;
	
	public CancelarDevolucion (int tienda, String fechaTrans, int caja, int transaccion){
		this.numTienda = tienda;
		this.fecha = fechaTrans;
		this.numCaja = caja;
		this.numTransaccion = transaccion;
	}
	public void run(){
				//String sentenciaSQL = "select * from saldocliente where saldocliente.codcliente='" + cliente.getCodCliente() 
				//			+ "' and saldocliente.saldobloqueado = '" + Sesion.NO + "'";
			boolean bandera = true;
			ResultSet renglonTransaccion  = null;
			String sentenciaSQL;
			while (bandera)
				try {
					sentenciaSQL = "delete from cr.transaccionrecuperada where numtienda=" + numTienda + " and fecha='" +						fecha + "' and numcaja=" + numCaja + " and numtransaccion=" + numTransaccion; 
					if (ConexionServCentral.realizarSentencia(sentenciaSQL) <= 0)
						Thread.sleep(60*1000);
					else
						bandera = false;
					
				}catch (Exception ex) {
				//	logger.error("disminuirSaldoCliente(Cliente, double)", ex);
					ex.printStackTrace();
					try {
						Thread.sleep(60*1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					//throw new PagoExcepcion("Error al disminuir saldo del Cliente " + cliente.getCodCliente(),ex);
				} finally {
					if (renglonTransaccion != null) {
						try {
							renglonTransaccion.close();
						} catch (SQLException e) {
							//logger.error("disminuirSaldoCliente(Cliente, double)", e);
						}
						renglonTransaccion = null;
					}
				}
			}
		
	}
