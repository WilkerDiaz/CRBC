/**
 * =============================================================================
 * Proyecto   : PoliticaMant_ServCR
 * Paquete    : com.beco.ventas.politicasmantenimiento
 * Programa   : Controlador.java
 * Creado por : irojas
 * Creado en  : 17/06/2009 - 02:20:49 PM
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

package com.beco.ventas.politicasmantenimiento;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.manejadorauditoria.Auditoria;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.mediadoresbd.Conexiones;



/**
 *	Esta clase refiere a los objetos que representan Controlador. 
 */

public class Controlador {
	
	/**
	 * Carga los productos que se encuentran eliminados en la BD
	 * @param 
	 * @return Vector Productos a eliminar
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private static Vector<String> cargarProductosAEliminar(){
		Vector<String> productosAEliminar =  new Vector<String>();		
		ResultSet productos = null;
		
		String query = "SELECT codproducto " +
						"FROM producto " +
						"WHERE estadoproducto = 'E'";
		
		try{
			productos = Conexiones.realizarConsulta(query, true);
			productos.beforeFirst();
			while(productos.next()){
				String codProd = productos.getString("codproducto");
				productosAEliminar.addElement(codProd);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if (productos!=null){
				try{
					productos.close();
				}catch(SQLException e){
				}
				productos=null;
			}
		}
		return productosAEliminar;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private static Vector<String> cargarAfiliadosAEliminar(){
		Vector<String> afiliadosAEliminar =  new Vector<String>();
		
		ResultSet afiliados = null;
		String query = "SELECT numficha " +
		"FROM afiliado " +
		"WHERE numficha <>'' AND estadocolaborador='I'";
			
		try{
			afiliados = Conexiones.realizarConsulta(query, true);
			afiliados.beforeFirst();
			while(afiliados.next()){
				String numFicha = afiliados.getString("numficha");
				afiliadosAEliminar.addElement(numFicha);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if (afiliados!=null){
				try{
					afiliados.close();
				}catch(SQLException e){
				}
				afiliados=null;
			}
		}
		return afiliadosAEliminar;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private static Vector<Integer> cargarPromocionesVencidas(){
		Vector<Integer> promocionesAEliminar =  new Vector<Integer>();
		Calendar fechaHoy = Calendar.getInstance();
		fechaHoy.add(Calendar.MONTH, -1);
		SimpleDateFormat fechaFormat = new SimpleDateFormat("yyyy-MM-dd");
		String fechaComparacion = fechaFormat.format(fechaHoy.getTime()) ;
		
		ResultSet promociones = null;
		String query = "SELECT codpromocion " +
		"FROM promocion " +
		"WHERE fechafinaliza <'" + fechaComparacion + "'";
			
		try{
			promociones = Conexiones.realizarConsulta(query, true);
			promociones.beforeFirst();
			while(promociones.next()){
				int codPromocion = promociones.getInt("codpromocion");
				promocionesAEliminar.addElement(new Integer(codPromocion));
			}
		}catch(Exception ex){
		}finally{
			if (promociones!=null){
				try{
					promociones.close();
				}catch(SQLException e){
				}
				promociones=null;
			}
		}
		return promocionesAEliminar;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unused")
	private static Vector<Transaccion> cargarTransacciones(){
		Vector<Transaccion> transaccionesAEliminar =  new Vector<Transaccion>();
		Calendar fechaHoy = Calendar.getInstance();
		fechaHoy.set(Calendar.DAY_OF_MONTH, 1);
		String dia2Digitos =  (fechaHoy.get(Calendar.DAY_OF_MONTH) < 10) ? "0" + fechaHoy.get(Calendar.DAY_OF_MONTH) : ""+fechaHoy.get(Calendar.DAY_OF_MONTH);
		
		//********* Caso del mes 12 que no lo registra la clase Calendar como mes válido
		String mes2Digitos = "";
		if (fechaHoy.get(Calendar.MONTH) == 0) {
			mes2Digitos= "11";
		} else if (fechaHoy.get(Calendar.MONTH) == 11) {
			mes2Digitos = "10";
		} else {
			fechaHoy.set(Calendar.MONTH, (fechaHoy.get(Calendar.MONTH) - 1));
			mes2Digitos =  (fechaHoy.get(Calendar.MONTH) < 10) ? "0" + fechaHoy.get(Calendar.MONTH) : ""+fechaHoy.get(Calendar.MONTH);
		}
		//************
		
		String fechaComparacion = fechaHoy.get(Calendar.YEAR) + "-" + mes2Digitos + "-" + dia2Digitos ;
		
		ResultSet transacciones = null;
		String query = "SELECT numtienda, fecha, numcajafinaliza, numtransaccion, numcajainicia FROM transaccion " +
					   "WHERE fecha <'" + fechaComparacion + "'";		
		
		try{
			transacciones = Conexiones.realizarConsulta(query, true);
			transacciones.beforeFirst();
			while(transacciones.next()){
				int numTienda = transacciones.getInt("numtienda");
				Date fecha = transacciones.getDate("fecha");
				int numCaja = transacciones.getInt("numcajafinaliza");
				int numTransaccion = transacciones.getInt("numtransaccion");
				int numCajaInicia = transacciones.getInt("numcajainicia");
				Transaccion transaccion = new Transaccion(numTienda,fecha,numCaja,numTransaccion, numCajaInicia);
				
				transaccionesAEliminar.addElement(transaccion);
			}
		}catch(Exception ex){
		}finally{
			if (transacciones!=null){
				try{
					transacciones.close();
				}catch(SQLException e){
				}
				transacciones=null;
			}
		}
		return transaccionesAEliminar;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private static Vector<Servicio> cargarServiciosAEliminar(){
		Vector<Servicio> serviciosAEliminar =  new Vector<Servicio>();
		Calendar fechaHoy = Calendar.getInstance();
		fechaHoy.set(Calendar.MONTH, fechaHoy.get(Calendar.MONTH) + 1);
		fechaHoy.set(Calendar.DAY_OF_MONTH, 1);
		String mes2Digitos =  (fechaHoy.get(Calendar.MONTH) < 10) ? "0" + fechaHoy.get(Calendar.MONTH) : ""+fechaHoy.get(Calendar.MONTH);
		String dia2Digitos =  (fechaHoy.get(Calendar.DAY_OF_MONTH) < 10) ? "0" + fechaHoy.get(Calendar.DAY_OF_MONTH) : ""+fechaHoy.get(Calendar.DAY_OF_MONTH);
		
		//********* Caso del mes 12 que no lo registra la clase Calendar como mes válido
		if (fechaHoy.get(Calendar.MONTH) == 0) {
			mes2Digitos= "12";
		} else if (fechaHoy.get(Calendar.MONTH) == 11) {
			mes2Digitos = "11";
		} else {
			fechaHoy.set(Calendar.MONTH, fechaHoy.get(Calendar.MONTH));
			mes2Digitos =  (fechaHoy.get(Calendar.MONTH) < 10) ? "0" + fechaHoy.get(Calendar.MONTH) : ""+fechaHoy.get(Calendar.MONTH);
		}
		//************
		String fechaComparacion = fechaHoy.get(Calendar.YEAR) + "-" + mes2Digitos + "-" +  dia2Digitos;
		
		
		ResultSet servicios = null;
		String query = "SELECT numtienda,codtiposervicio,numservicio,fecha FROM servicio " +
						"WHERE (estadoservicio = 'A' OR estadoservicio = 'X' OR estadoservicio = 'F') " +
								"AND fecha < '" + fechaComparacion + "'";
			
		try{
			servicios = Conexiones.realizarConsulta(query, true);
			servicios.beforeFirst();
			while(servicios.next()){
				int numTienda = servicios.getInt("numtienda");
				String codTipoServ = servicios.getString("codtiposervicio");
				int numServicio = servicios.getInt("numservicio");
				Date fecha = servicios.getDate("fecha");
				
				Servicio servicio = new Servicio(numTienda,codTipoServ,numServicio,fecha);
				serviciosAEliminar.addElement(servicio);
			}
		}catch(Exception ex){
		}finally{
			if (servicios!=null){
				try{
					servicios.close();
				}catch(SQLException e){
				}
				servicios=null;
			}
		}
		return serviciosAEliminar;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private static Vector<Integer> cargarListasRegalosAEliminar(){
		Vector<Integer> listasAEliminar =  new Vector<Integer>();
		Calendar fechaHoy = Calendar.getInstance();
		fechaHoy.set(Calendar.DAY_OF_MONTH, 1);
		String dia2Digitos =  (fechaHoy.get(Calendar.DAY_OF_MONTH) < 10) ? "0" + fechaHoy.get(Calendar.DAY_OF_MONTH) : ""+fechaHoy.get(Calendar.DAY_OF_MONTH);
		
		//********* Caso del mes 12 que no lo registra la clase Calendar como mes válido
		String mes2Digitos = "";
		if (fechaHoy.get(Calendar.MONTH) == 11) {
			mes2Digitos= "12";
		} else if (fechaHoy.get(Calendar.MONTH) == 0) {
			mes2Digitos= "01";
		} else {
			fechaHoy.set(Calendar.MONTH, (fechaHoy.get(Calendar.MONTH) + 1));
			mes2Digitos =  (fechaHoy.get(Calendar.MONTH) < 10) ? "0" + fechaHoy.get(Calendar.MONTH) : ""+fechaHoy.get(Calendar.MONTH);
		}
		//************
		
		String fechaComparacion = fechaHoy.get(Calendar.YEAR) + "-" + mes2Digitos + "-" +  dia2Digitos;		
		
		ResultSet listas = null;
		String query = "SELECT codlista FROM listaregalos " +
						"WHERE (estado = 'C' OR estado = 'X') " +
								"AND fechacierre < '" + fechaComparacion + "'";
		
		try{
			listas = Conexiones.realizarConsulta(query, true);
			listas.beforeFirst();
			while(listas.next()){
				int codLista = listas.getInt("codlista");
				listasAEliminar.addElement(new Integer(codLista));
			}
		}catch(Exception ex){
		}finally{
			if (listas!=null){
				try{
					listas.close();
				}catch(SQLException e){
				}
				listas=null;
			}
		}
		return listasAEliminar;
	}	
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static void limpiarUsuarios() {
		Vector<String> afiliadosAEliminar = cargarAfiliadosAEliminar();
		if (afiliadosAEliminar != null && afiliadosAEliminar.size() > 0) {
			for (int i=0; i<afiliadosAEliminar.size(); i++) {
				String numFicha = ((String) afiliadosAEliminar.elementAt(i)).trim();	
			
				String sentenciaDelete = 
				"DELETE FROM usuario WHERE numficha = '" + numFicha + "'";
											
				try {
					Conexiones.realizarSentencia(sentenciaDelete, true);
				} catch (Exception e1) {
				} 
			}
			Auditoria.registrarAuditoria("*** Cantidad de USUARIOS eliminados: " + afiliadosAEliminar.size(),'O');
		} else{
			Auditoria.registrarAuditoria("*** Cantidad de USUARIOS eliminados: 0",'O');
		}
	}
	
	public static void limpiarAfiliado() {
		String sentenciaDelete = "DELETE FROM afiliado WHERE numficha <>'' AND estadocolaborador='I'";
													
			try {
				int cantidad = Conexiones.realizarSentencia(sentenciaDelete, true);
				Auditoria.registrarAuditoria("*** Cantidad de AFILIADOS eliminados: " + cantidad,'O');
			} catch (Exception e) {
			}
	}
	
	public static void limpiarAuditoria() {
		Calendar fechaHoy = Calendar.getInstance();
		fechaHoy.add(Calendar.MONTH, -6);
		fechaHoy.set(Calendar.HOUR_OF_DAY, 0);
		fechaHoy.set(Calendar.MINUTE, 0);
		fechaHoy.set(Calendar.SECOND, 0);
		fechaHoy.set(Calendar.MILLISECOND, 0);
		SimpleDateFormat fechaFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    	String fechaComparacion = fechaFormat.format(fechaHoy.getTime());
				
		String sentenciaDelete = "DELETE FROM auditoria WHERE fecha < '" + fechaComparacion+ "'";
			try {
				int cantidad = Conexiones.realizarSentencia(sentenciaDelete, true);
				Auditoria.registrarAuditoria("*** Cantidad de AUDITORIA eliminada: " + cantidad,'O');
			} catch (Exception e) {
			}
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static void limpiarProductos() {
		Vector<String> productosAEliminar = cargarProductosAEliminar();

		
		// ***** Eliminamos las transacciones con todos sus posibles detalles
		Statement loteSentencias  = null;
		
		if (productosAEliminar != null && productosAEliminar.size() > 0) {
			for (int i=0; i<productosAEliminar.size(); i++) {
				String codProducto = ((String) productosAEliminar.elementAt(i)).trim();	
			
				try {
					loteSentencias = Conexiones.crearSentencia(true);
				
				String sentenciaDelete2 = 
					"DELETE FROM prodseccion WHERE codProducto = = '" + codProducto + "'";
				loteSentencias.addBatch(sentenciaDelete2);
				
				String sentenciaDelete3 = 
					"DELETE FROM prodcodigoexterno WHERE codproducto = '" + codProducto + "'";
				loteSentencias.addBatch(sentenciaDelete3);
				
				String sentenciaDelete4 = 
					"DELETE FROM producto WHERE codproducto = '" + codProducto + "'";
				loteSentencias.addBatch(sentenciaDelete4);
				
				//Ahora ejecutamos todas las sentencias de forma transaccional en la caja
				int[] filasAfectadas = Conexiones.ejecutarLoteSentencias(loteSentencias, true, true);
				Auditoria.registrarAuditoria("*** Cantidad de PRODSECCION,PRODCODIGOEXTERNO Y PRODUCTO eliminados: " + filasAfectadas,'O');
				} catch (Exception e) {
					
				} finally {
					if (loteSentencias != null) {
						try {
							loteSentencias.close();
						} catch (SQLException e1) {
							
						}
						loteSentencias = null;
					}
				}			
			}
		} else {
			Auditoria.registrarAuditoria("*** Cantidad de PRODUCTOS eliminados: 0",'O');
		}
		// *****  
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static void limpiarPromociones() {
		Vector<Integer> promocionesVencidas = cargarPromocionesVencidas();
		
		// ***** Eliminamos las promociones vencidas con todos sus posibles detalles
		Statement loteSentencias  = null;
		
		if (promocionesVencidas != null && promocionesVencidas.size() > 0) {
			Auditoria.registrarAuditoria("*** Cantidad de PROMOCIONES vencidas a eliminar: " + promocionesVencidas.size(),'O');
			for (int i=0; i<promocionesVencidas.size(); i++) {
				int codPromocion = ((Integer) promocionesVencidas.elementAt(i)).intValue();	
			
				try {
					loteSentencias = Conexiones.crearSentencia(true);
					String sentenciaDelete1 = 
						"DELETE FROM detallepromocion WHERE codpromocion = " + codPromocion;
					loteSentencias.addBatch(sentenciaDelete1);
					
					String sentenciaDelete3 = 
						"DELETE FROM transaccionpremiada WHERE codpromocion = " + codPromocion;
					loteSentencias.addBatch(sentenciaDelete3);
					
					String sentenciaDelete5 = 
						"DELETE FROM regalosregistrados WHERE codPromocion = " + codPromocion;
					loteSentencias.addBatch(sentenciaDelete5);
					
					String sentenciaDelete2 = 
						"DELETE FROM detallepromocionext WHERE codPromocion = " + codPromocion;
					loteSentencias.addBatch(sentenciaDelete2);
					
					String sentenciaDelete = 
						"DELETE FROM promocion WHERE codpromocion = " + codPromocion;
					loteSentencias.addBatch(sentenciaDelete);
													
					//Ahora ejecutamos todas las sentencias de forma transaccional en la caja
					Conexiones.ejecutarLoteSentencias(loteSentencias, true, true);
				 
				} catch (Exception e) {
					
				} finally {
					if (loteSentencias != null) {
						try {
							loteSentencias.close();
						} catch (SQLException e1) {
							
						}
						loteSentencias = null;
					}
				}	
			}
		} else {
			Auditoria.registrarAuditoria("*** Cantidad de PROMOCIONES vencidas a eliminar: 0",'O');
			
		}
		// ***** 
		
		//	*** SET FOREIGN_KEY_CHECKS
		try {
			 Conexiones.realizarSentencia("SET FOREIGN_KEY_CHECKS=0", true);
		} catch (Exception e) {
		}
		
		// ***** Eliminamos los detalles de promoción que han sido anulados
		String sentenciaDelete4 = 
				"DELETE FROM detallepromocion WHERE estadoregistro= 'E'";
													
		int cantidad;
		try {
			cantidad = Conexiones.realizarSentencia(sentenciaDelete4, true);
			Auditoria.registrarAuditoria("*** Cantidad de DETALLESPROMOCIONES anuladas eliminadas: " + cantidad,'O');
		} catch (Exception e) {
			
		}
		
		// ***** 
		// ***** Eliminamos los detalles de promoción extendida que han sido anulados
		String sentenciaDelete5 = 
				"DELETE FROM detallepromocionext WHERE estadoRegistro= 'E'";
					
		try {
			cantidad= Conexiones.realizarSentencia(sentenciaDelete5, true);
			Auditoria.registrarAuditoria("*** Cantidad de DETALLESPROMOCIONESEXT anuladas eliminadas: " + cantidad,'O');
		} catch (Exception e) {
			
		}

		// ***** 
		
		//*** SET FOREIGN_KEY_CHECKS
		try {
			 Conexiones.realizarSentencia("SET FOREIGN_KEY_CHECKS=1", true);
		} catch (Exception e) {
		}
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static void limpiarTransacciones() {
		//Vector transaccionesAEliminar = cargarTransacciones();
		Vector<Transaccion> transaccionesAEliminar = new Vector<Transaccion>();
		boolean buscarMas=true;
		ResultSet transacciones = null;
		String query = null;
		int minFila =0;
		int maxFila = 50000;
		Calendar fechaHoy = Calendar.getInstance();
		fechaHoy.add(Calendar.MONTH, -3);
		
		SimpleDateFormat fechaFormat = new SimpleDateFormat("yyyy-MM-dd");
		String fechaComparacion = fechaFormat.format(fechaHoy.getTime()) ;
		
		//************* SELECT DE 5000 EN 5000 POR ERROR DE OUT OF MEMORY
		try{
			while (buscarMas) {
				
				query = "SELECT numtienda, fecha, numcajafinaliza, numtransaccion, numcajainicia FROM transaccion " +
				   "WHERE fecha <'" + fechaComparacion + "' limit " + minFila + ", " + maxFila;	
				transacciones = Conexiones.realizarConsulta(query, true);
				if (transacciones.first()) {
					transacciones.beforeFirst();
					while(transacciones.next()){
						int numTienda = transacciones.getInt("numtienda");
						Date fecha = transacciones.getDate("fecha");
						int numCaja = transacciones.getInt("numcajafinaliza");
						int numTransaccion = transacciones.getInt("numtransaccion");
						int numCajaInicia = transacciones.getInt("numcajainicia");
						Transaccion transaccion = new Transaccion(numTienda,fecha,numCaja,numTransaccion, numCajaInicia);
						
						transaccionesAEliminar.addElement(transaccion);
					}
					//Se incrementan las variables minFila y MaxFila para que en la próxima 
					//ejecución tome los siguientes 50000 registros
					minFila = minFila + 50000;
					maxFila = maxFila + 50000;
		
				//******** INICIO: PROCESO DE ELIMINACION***************

		
				// ***** Eliminamos las transacciones con todos sus posibles detalles
				Statement loteSentencias  = null;
				
				if (transaccionesAEliminar != null && transaccionesAEliminar.size() > 0) {
					Auditoria.registrarAuditoria("*** Cantidad de TRANSACCIONES a eliminar: " + transaccionesAEliminar.size(),'O');
					for (int i=0; i<transaccionesAEliminar.size(); i++) {
						Transaccion transaccion = (Transaccion) transaccionesAEliminar.elementAt(i);
						int numTienda = transaccion.getNumTienda();
						Date fecha = transaccion.getFecha();
						int numCaja = transaccion.getNumCaja();
						int numTransaccion = transaccion.getNumTransaccion();
						int numCajaInicia = transaccion.getNumCajaInicia();
					
						try {
							loteSentencias = Conexiones.crearSentencia(true);
						
						loteSentencias.addBatch ("SET FOREIGN_KEY_CHECKS=0");
						String sentenciaDelete2 = 
							"DELETE FROM transaccionpremiada WHERE numTienda = " + numTienda +
																   " and fecha = '" + fecha + "'" +
																   " and numCaja = " + numCaja +
																   " and numTransaccion = " + numTransaccion;
						loteSentencias.addBatch(sentenciaDelete2);
						
						String sentenciaDelete3 = 
							"DELETE FROM transaccionafiliadocrm WHERE numtienda = " + numTienda +
																   " and fechatransaccion = '" + fecha + "'" +
																   " and numcajafinaliza = " + numCaja +
																   " and numtransaccion = " + numTransaccion;
						loteSentencias.addBatch(sentenciaDelete3);
						
						String sentenciaDelete4 = 
							"DELETE FROM pagodonacion WHERE numTienda = " + numTienda +
																   " and fechaDonacion = '" + fecha + "'" +
																   " and numCaja = " + numCaja +
																   " and numTransaccion = " + numTransaccion;
						loteSentencias.addBatch(sentenciaDelete4);
						
						String sentenciaDelete5 = 
							"DELETE FROM donacionesregistradas WHERE numTienda = " + numTienda +
																   " and fechaDonacion = '" + fecha + "'" +
																   " and numCaja = " + numCaja +
																   " and numTransaccion = " + numTransaccion;
						loteSentencias.addBatch(sentenciaDelete5);
						
						String sentenciaDelete12 = 
							"DELETE FROM regalosregistrados WHERE numTienda = " + numTienda +
																   " and fechaTransaccion = '" + fecha + "'" +
																   " and numCaja = " + numCaja +
																   " and numTransaccion = " + numTransaccion;
						loteSentencias.addBatch(sentenciaDelete12);
						
						String sentenciaDelete6 = 
							"DELETE FROM detalletransaccioncondicion WHERE numtienda = " + numTienda +
																   " and fecha = '" + fecha + "'" +
																   " and numcajainicia = " + numCajaInicia +
																   " and numtransaccion = " + numTransaccion;
						loteSentencias.addBatch(sentenciaDelete6);
						
						String sentenciaDelete7 = 
							"DELETE FROM promocionregistrada WHERE numTienda = " + numTienda +
																   " and fecha = '" + fecha + "'" +
																   " and numCaja = " + numCaja +
																   " and numtransacion = " + numTransaccion;
						loteSentencias.addBatch(sentenciaDelete7);
						
						String sentenciaDelete8 = 
							"DELETE FROM devolucionventa WHERE numtiendaventa = " + numTienda +
																   " and fechaventa = '" + fecha + "'" +
																   " and numcajaventa = " + numCaja +
																   " and numtransaccionvta = " + numTransaccion;
						loteSentencias.addBatch(sentenciaDelete8);
						
						String sentenciaDelete9 = 
							"DELETE FROM pagodetransaccion WHERE numtienda = " + numTienda +
																   " and fecha = '" + fecha + "'" +
																   " and numcaja = " + numCaja +
																   " and numtransaccion = " + numTransaccion;
						loteSentencias.addBatch(sentenciaDelete9);
						
						String sentenciaDelete10 = 
							"DELETE FROM detalletransaccion WHERE numtienda = " + numTienda +
																   " and fecha = '" + fecha + "'" +
																   " and numcajafinaliza = " + numCaja +
																   " and numtransaccion = " + numTransaccion;
						loteSentencias.addBatch(sentenciaDelete10);
						
						String sentenciaDelete11 = 
							"DELETE FROM transaccion WHERE numtienda = " + numTienda +
																   " and fecha = '" + fecha + "'" +
																   " and numcajafinaliza = " + numCaja +
																   " and numtransaccion = " + numTransaccion;
						loteSentencias.addBatch(sentenciaDelete11);
						loteSentencias.addBatch ("SET FOREIGN_KEY_CHECKS=1");
						
						//Ahora ejecutamos todas las sentencias de forma transaccional en la caja
						Conexiones.ejecutarLoteSentencias(loteSentencias, true, true);
					 
						} catch (SQLException e) {						
						} finally {
							if (loteSentencias != null) {
								try {
									loteSentencias.close();
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								loteSentencias = null;
							}
						}			
					}
				} else {
					Auditoria.registrarAuditoria("*** Cantidad de TRANSACCIONES a eliminar: 0",'O');
				}
				// *****  
				//******** FIN: PROCESO DE ELIMINACION***************
		
				} else {
					buscarMas = false;
				}
			}
			
			//**** Borramos las transacciones en estado A = ABORTADA 
			try {
				Conexiones.realizarSentencia ("SET FOREIGN_KEY_CHECKS=0", true);
				String sentenciaDelete10 = 
					"DELETE FROM detalletransaccion WHERE numtransaccion is null and fecha < '" + fechaComparacion + "'";
				Conexiones.realizarSentencia(sentenciaDelete10, true);
				
				String sentenciaDelete11 = 
					"DELETE FROM transaccion WHERE numtransaccion is null and fecha < '" + fechaComparacion + "'";
				Conexiones.realizarSentencia(sentenciaDelete11, true);
				Conexiones.realizarSentencia("SET FOREIGN_KEY_CHECKS=1", true);
			 
			} catch (Exception e) {
				
			}			
			
			//**** Borramos las puntoAgilOperacion 
			String sentenciaDelete1 = 
				"DELETE FROM puntoAgilOperacion WHERE fecha < '" + fechaComparacion + "'";
			try {
				int cantidad = Conexiones.realizarSentencia(sentenciaDelete1, true);
				Auditoria.registrarAuditoria("*** Cantidad de PUNTOAGILOPERACION eliminadas: " + cantidad,'O');
			} catch (Exception e) {
				
			}
		
		}catch(Exception ex){
		}finally{
			if (transacciones!=null){
				try{
					transacciones.close();
				}catch(SQLException e){
				}
				transacciones=null;
			}
		}
	}
	
	public static void limpiarVouchers() {
		File archivo = new File(Sesion.pathVouchers);
		Calendar fechaComp = Calendar.getInstance();
		fechaComp.add(Calendar.MONTH, -2);
		int cantidadEliminada = 0;
		String [] listaArchivos = archivo.list(new Filtro(".txt"));
		String [] listaArchivos2 = archivo.list(new Filtro(".lck"));
		String [] listaArchivos3 = archivo.list(new Filtro(".log"));
		
		if (listaArchivos != null) {
			for(int i=0; i<listaArchivos.length; i++){
		         String nombreArchivo =  listaArchivos[i];
		         File archivo2 = new File(Sesion.pathVouchers + nombreArchivo);
		         
		         try {
		        	 Date fecha = new Date(archivo2.lastModified());
		 	         Calendar fechaArch = Calendar.getInstance();
		 	         fechaArch.setTime(fecha);
		 	         
			         if (fechaArch.before(fechaComp)) { 
			        	 File archivoABorrar = new File(Sesion.pathVouchers + nombreArchivo);
			        	 try {
			        		 archivoABorrar.delete();
			        		 cantidadEliminada ++;
			        	 } catch (SecurityException e){
			        		
			        	 }
			         }
		         }  catch (Exception e){
		        	 
		         }
			 }
		} else {
			
		}
		
		if (listaArchivos2 != null) {
			for(int i=0; i<listaArchivos2.length; i++){
		         String nombreArchivo =  listaArchivos2[i];
		         File archivo2 = new File(nombreArchivo);
		         
		         try {
		        	 Date fecha = new Date(archivo2.lastModified());
		 	         Calendar fechaArch = Calendar.getInstance();
		 	         fechaArch.setTime(fecha);
		 	         
			         if (fechaArch.before(fechaComp)) { 
			        	 File archivoABorrar = new File(Sesion.pathVouchers + nombreArchivo);
			        	 try {
			        		 archivoABorrar.delete();
			        		 cantidadEliminada ++;
			        	 } catch (SecurityException e){
			        		
			        	 }
			         }
		         }  catch (Exception e){
		        	 
		         }
			 }
		} else {
			 
		}
		
		if (listaArchivos3 != null) {
			for(int i=0; i<listaArchivos3.length; i++){
		         String nombreArchivo =  listaArchivos3[i];
		         File archivo2 = new File(nombreArchivo);
		         
		         try {
		        	 Date fecha = new Date(archivo2.lastModified());
		 	         Calendar fechaArch = Calendar.getInstance();
		 	         fechaArch.setTime(fecha);
		 	         
			         if (fechaArch.before(fechaComp)) { 
			        	 File archivoABorrar = new File(Sesion.pathVouchers + nombreArchivo);
			        	 try {
			        		 archivoABorrar.delete();
			        		 cantidadEliminada ++;
			        	 } catch (SecurityException e){
			        		
			        	 }
			         }
		         }  catch (Exception e){
		        	 
		         }
			 }
		} else {
			 
		}
		 Auditoria.registrarAuditoria("*** Cantidad de ARCHIVOS VOUCHERS eliminados: " + cantidadEliminada,'O');
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static void limpiarServicios() {
		Vector<Servicio> serviciosAEliminar = cargarServiciosAEliminar();

		// ***** Eliminamos los servicios con todos sus posibles detalles
		Statement loteSentencias  = null;
		
		if (serviciosAEliminar != null && serviciosAEliminar.size() > 0) {
			 Auditoria.registrarAuditoria("*** Cantidad de SERVICIOS a eliminar: " + serviciosAEliminar.size(),'O');
			
			for (int i=0; i<serviciosAEliminar.size(); i++) {
				Servicio servicio = (Servicio) serviciosAEliminar.elementAt(i);
				int numTienda = servicio.getNumTienda();
				String codTipoServ = servicio.getCodTipoServicio().trim();
				int numServicio = servicio.getNumServicio();
				Date fecha = servicio.getFecha();
			
				try {
					loteSentencias = Conexiones.crearSentencia(true);
				
				String sentenciaDelete2 = 
					"DELETE FROM pagodeabonos WHERE numtienda = " + numTienda +
														   " and numservicio = " + numServicio  +
														   " and fecha = '" + fecha + "'";
				loteSentencias.addBatch(sentenciaDelete2);
				
				String sentenciaDelete3 = 
					"DELETE FROM transaccionabono WHERE numtienda = " + numTienda +
															" and numservicio = " + numServicio  +
															" and fecha = '" + fecha + "'" + 
															" and codtiposervicio = '" + codTipoServ + "'";
				loteSentencias.addBatch(sentenciaDelete3);
				
				String sentenciaDelete4 = 
					"DELETE FROM detalleserviciocondicion WHERE numtienda = " + numTienda +
															" and numservicio = " + numServicio  +
															" and fecha = '" + fecha + "'" + 
															" and codtiposervicio = '" + codTipoServ + "'";
				loteSentencias.addBatch(sentenciaDelete4);
				
				String sentenciaDelete5 = 
					"DELETE FROM detalleservicio WHERE numtienda = " + numTienda +
															" and numservicio = " + numServicio  +
															" and fecha = '" + fecha + "'" + 
															" and codtiposervicio = '" + codTipoServ + "'";
				loteSentencias.addBatch(sentenciaDelete5);
				
				String sentenciaDelete6 = 
					"DELETE FROM servicio WHERE numtienda = " + numTienda +
															" and numservicio = " + numServicio  +
															" and fecha = '" + fecha + "'" + 
															" and codtiposervicio = '" + codTipoServ + "'";
				loteSentencias.addBatch(sentenciaDelete6);
								
				//Ahora ejecutamos todas las sentencias de forma transaccional en la caja
				Conexiones.ejecutarLoteSentencias(loteSentencias, true, true);
			 
				} catch (Exception e) {
					
				} finally {
					if (loteSentencias != null) {
						try {
							loteSentencias.close();
						} catch (SQLException e1) {
							
						}
						loteSentencias = null;
					}
				}			
			}
		} else {
			 Auditoria.registrarAuditoria("*** Cantidad de SERVICIOS a eliminar: 0",'O');
		}
		// *****  
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static void limpiarListasRegalos() {
		Vector<Integer> listasAEliminar = cargarListasRegalosAEliminar();

		// ***** Eliminamos las listas con todos sus posibles detalles
		Statement loteSentencias  = null;
		
		if (listasAEliminar != null && listasAEliminar.size() > 0) {
			 Auditoria.registrarAuditoria("*** Cantidad de LISTAS DE REGALOS a eliminar: " + listasAEliminar.size(),'O');
			
			for (int i=0; i<listasAEliminar.size(); i++) {
				int codLista = ((Integer)listasAEliminar.elementAt(i)).intValue();			
				try {
					loteSentencias = Conexiones.crearSentencia(true);
				
				String sentenciaDelete2 = 
					"DELETE FROM detalleoperacionlistaregalos WHERE codlista = " + codLista;
				loteSentencias.addBatch(sentenciaDelete2);
				
				String sentenciaDelete3 = 
					"DELETE FROM operacionlistaregalos WHERE codlista = " + codLista;
				loteSentencias.addBatch(sentenciaDelete3);
				
				String sentenciaDelete4 = 
					"DELETE FROM detallelistaregalos WHERE codlista = " + codLista;
				loteSentencias.addBatch(sentenciaDelete4);
				
				String sentenciaDelete5 = 
					"DELETE FROM listaregalos WHERE codlista = " + codLista;
				loteSentencias.addBatch(sentenciaDelete5);
								
				//Ahora ejecutamos todas las sentencias de forma transaccional en la caja
				Conexiones.ejecutarLoteSentencias(loteSentencias, true, true);
			 
				} catch (Exception e) {
				} finally {
					if (loteSentencias != null) {
						try {
							loteSentencias.close();
						} catch (SQLException e1) {
						}
						loteSentencias = null;
					}
				}			
			}
		} else {
			Auditoria.registrarAuditoria("*** Cantidad de LISTAS DE REGALOS a eliminar: 0",'O');
		}
		// *****  
	}
	
	public static void actualizarEjecucionPolitica(int idPolitica, String ultEjecucion, String proxEjecucion) {
		//	*** Update de la tabla PiliticaLimpieza para establecer proxima ejecución

		String sentenciaUpdate = 
			"update politicalimpieza set fechaultact = '" + ultEjecucion + "', fechaproxact = '" + proxEjecucion + "' WHERE id = " + idPolitica;

		try {
			Conexiones.realizarSentencia(sentenciaUpdate, true);
		} catch (Exception ex) {
			Auditoria.registrarAuditoria("Error al realizar sentencia " + sentenciaUpdate,'O');
		}
	}
	
	public static void resetMasterMYSQL() {
		String sentenciaResetMaster = "RESET MASTER";
		try {
			Conexiones.realizarSentencia(sentenciaResetMaster, true);
			Auditoria.registrarAuditoria("Ejecutada sentencia " + sentenciaResetMaster,'O');
			//System.out.println("*** Limpieza de LOGS de MYSQL ejecutado (RESET MASTER)");
		} catch (BaseDeDatosExcepcion e) {
		
		} catch (ConexionExcepcion e) {
		
		}
	}
	
}
