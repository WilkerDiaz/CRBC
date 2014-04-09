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
		System.out.println("---- Productos a Eliminar: " + query);
		try{
			productos = Conexiones.realizarConsulta(query, true);
			productos.beforeFirst();
			while(productos.next()){
				String codProd = productos.getString("codproducto");
				productosAEliminar.addElement(codProd);
			}
		}catch(Exception ex){
			System.out.println("Error obteniendo productos eliminados");
			ex.printStackTrace();
		}finally{
			if (productos!=null){
				try{
					productos.close();
				}catch(SQLException e){
					e.printStackTrace();
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
			
		System.out.println("---- Usuarios a Eliminar: " + query);
		
		try{
			afiliados = Conexiones.realizarConsulta(query, true);
			afiliados.beforeFirst();
			while(afiliados.next()){
				String numFicha = afiliados.getString("numficha");
				afiliadosAEliminar.addElement(numFicha);
			}
		}catch(Exception ex){
			System.out.println("Error obteniendo afiliados colaboradores inactivos");
			ex.printStackTrace();
		}finally{
			if (afiliados!=null){
				try{
					afiliados.close();
				}catch(SQLException e){
					e.printStackTrace();
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
		
		System.out.println("---- Promociones Vencidas a Eliminar: " + query);
		
			
		try{
			promociones = Conexiones.realizarConsulta(query, true);
			promociones.beforeFirst();
			while(promociones.next()){
				int codPromocion = promociones.getInt("codpromocion");
				promocionesAEliminar.addElement(new Integer(codPromocion));
			}
		}catch(Exception ex){
			System.out.println("Error obteniendo promociones Vencidas");
			ex.printStackTrace();
		}finally{
			if (promociones!=null){
				try{
					promociones.close();
				}catch(SQLException e){
					e.printStackTrace();
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
		boolean buscarMas=true;
		int minFila =0;
		int maxFila = 50000;
		Calendar fechaHoy = Calendar.getInstance();
		fechaHoy.set(Calendar.MONTH, fechaHoy.get(Calendar.MONTH) - 4);
		fechaHoy.set(Calendar.DAY_OF_MONTH, 1);
		String mes2Digitos =  (fechaHoy.get(Calendar.MONTH) < 10) ? "0" + fechaHoy.get(Calendar.MONTH) : ""+fechaHoy.get(Calendar.MONTH);
		String dia2Digitos =  (fechaHoy.get(Calendar.DAY_OF_MONTH) < 10) ? "0" + fechaHoy.get(Calendar.DAY_OF_MONTH) : ""+fechaHoy.get(Calendar.DAY_OF_MONTH);
		String fechaComparacion = fechaHoy.get(Calendar.YEAR) + "-" + mes2Digitos + "-" + dia2Digitos ;
		
		ResultSet transacciones = null;
		String query = null;
		
		try{
			while (buscarMas) {
				
				query = "SELECT numtienda, fecha, numcajafinaliza, numtransaccion, numcajainicia FROM transaccion " +
				   "WHERE fecha <'" + fechaComparacion + "' limit " + minFila + ", " + maxFila;	
				System.out.println("---- Transacciones a Eliminar: " + query); 
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
					minFila = minFila + 50000;
					maxFila = maxFila + 50000;
				} else {
					buscarMas = false;
				}
			}
		}catch(Exception ex){
			System.out.println("Error obteniendo transacciones a eliminar");
			ex.printStackTrace();
		}finally{
			if (transacciones!=null){
				try{
					transacciones.close();
				}catch(SQLException e){
					e.printStackTrace();
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
		fechaHoy.add(Calendar.MONTH, -3);
		SimpleDateFormat fechaFormat = new SimpleDateFormat("yyyy-MM-dd");
		String fechaComparacion = fechaFormat.format(fechaHoy.getTime()) ;
		
		ResultSet servicios = null;
		String query = "SELECT numtienda,codtiposervicio,numservicio,fecha FROM servicio " +
						"WHERE (estadoservicio = 'A' OR estadoservicio = 'X' OR estadoservicio = 'F') " +
								"AND fecha < '" + fechaComparacion + "'";
		
		System.out.println("---- Servicios a Eliminar: " + query);
			
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
			System.out.println("Error obteniendo servicios a eliminar");
			ex.printStackTrace();
		}finally{
			if (servicios!=null){
				try{
					servicios.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
				servicios=null;
			}
		}
		return serviciosAEliminar;
	}
	
	private static Vector<Integer> cargarListasRegalosAEliminar(){
		Vector<Integer> listasAEliminar =  new Vector<Integer>();
		Calendar fechaHoy = Calendar.getInstance();
		fechaHoy.add(Calendar.MONTH, -3);
		SimpleDateFormat fechaFormat = new SimpleDateFormat("yyyy-MM-dd");
		String fechaComparacion = fechaFormat.format(fechaHoy.getTime()) ;
		
		ResultSet listas = null;
		String query = "SELECT codlista FROM listaregalos " +
						"WHERE (estado = 'C' OR estado = 'X') " +
								"AND fechacierre < '" + fechaComparacion + "'";
		
		System.out.println("---- Listas de Regalos a Eliminar: " + query);
		
		try{
			listas = Conexiones.realizarConsulta(query, true);
			listas.beforeFirst();
			while(listas.next()){
				int codLista = listas.getInt("codlista");
				listasAEliminar.addElement(new Integer(codLista));
			}
		}catch(Exception ex){
			System.out.println("Error obteniendo listas de regalos a eliminar");
			ex.printStackTrace();
		}finally{
			if (listas!=null){
				try{
					listas.close();
				}catch(SQLException e){
					e.printStackTrace();
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
					Conexiones.realizarSentencia(sentenciaDelete);
				} catch (SQLException e) {
					System.out.println("Error al realizar sentencia DELETE de usuario " + numFicha);
					e.printStackTrace();
				}
			}
			System.out.println("*** Cantidad de USUARIOS eliminados: " + afiliadosAEliminar.size());
		} else{
			System.out.println("*** Cantidad de USUARIOS eliminados: 0");
		}
	}
	
	public static void limpiarAfiliado() {
		String sentenciaDelete = "DELETE FROM afiliado WHERE numficha <>'' AND estadocolaborador='I'";
													
			try {
				int cantidad = Conexiones.realizarSentencia(sentenciaDelete);
				System.out.println("*** Cantidad de AFILIADOS eliminados: " + cantidad);
			} catch (SQLException e) {
				System.out.println("Error al realizar sentencia DELETE de Afiliado");
				e.printStackTrace();
			}
	}
	
	public static void limpiarAuditoria() {
		Calendar fechaHoy = Calendar.getInstance();
		fechaHoy.add(Calendar.YEAR,- 2);
		fechaHoy.set(Calendar.HOUR_OF_DAY, 0);
		fechaHoy.set(Calendar.MINUTE, 0);
		fechaHoy.set(Calendar.SECOND, 0);
		fechaHoy.set(Calendar.MILLISECOND, 0);
		SimpleDateFormat fechaFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    	String fechaComparacion = fechaFormat.format(fechaHoy.getTime());
		
		String sentenciaDelete = "DELETE FROM auditoria WHERE fecha < '" + fechaComparacion+ "'";
		System.out.println("---- Auditoria a Eliminar: " + sentenciaDelete);

			try {
				int cantidad = Conexiones.realizarSentencia(sentenciaDelete);
				System.out.println("*** Cantidad de AUDITORIA eliminada: " + cantidad);
			} catch (SQLException e) {
				System.out.println("Error al realizar sentencia DELETE de Auditoria");
				e.printStackTrace();
			}
	}
	
	public static void limpiarProductos() {
		Vector<String> productosAEliminar = cargarProductosAEliminar();

		
		// ***** Eliminamos las transacciones con todos sus posibles detalles
		Statement loteSentencias  = null;
		
		if (productosAEliminar != null && productosAEliminar.size() > 0) {
			for (int i=0; i<productosAEliminar.size(); i++) {
				String codProducto = ((String) productosAEliminar.elementAt(i)).trim();	
			
				try {
					loteSentencias = Conexiones.getConexionLocal().createStatement();
				
				String sentenciaDelete2 = 
					"DELETE FROM prodseccion WHERE codProducto = = '" + codProducto + "'";
				//System.out.println("---- " + sentenciaDelete2);
				loteSentencias.addBatch(sentenciaDelete2);
				
				String sentenciaDelete3 = 
					"DELETE FROM prodcodigoexterno WHERE codproducto = '" + codProducto + "'";
				//System.out.println("---- " + sentenciaDelete3);
				loteSentencias.addBatch(sentenciaDelete3);
				
				String sentenciaDelete4 = 
					"DELETE FROM producto WHERE codproducto = '" + codProducto + "'";
				//System.out.println("---- " + sentenciaDelete4);
				loteSentencias.addBatch(sentenciaDelete4);
				
				//Ahora ejecutamos todas las sentencias de forma transaccional en la caja
				int[] filasAfectadas = Conexiones.ejecutarLoteSentencias(loteSentencias, true, true);
				System.out.println("*** Cantidad de PRODSECCION,PRODCODIGOEXTERNO Y PRODUCTO eliminados: " + filasAfectadas);
			 
				} catch (SQLException e) {
					System.out.println("** Error eliminando batch de producto: " + codProducto + " en política de mantenimiento");
					e.printStackTrace();
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
			System.out.println("*** Cantidad de PRODUCTOS eliminados: 0");
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
			System.out.println("*** Cantidad de PROMOCIONES vencidas a eliminar: " + promocionesVencidas.size());
			for (int i=0; i<promocionesVencidas.size(); i++) {
				int codPromocion = ((Integer) promocionesVencidas.elementAt(i)).intValue();	
			
				try {
					loteSentencias = Conexiones.getConexionLocal().createStatement();
					String sentenciaDelete1 = 
						"DELETE FROM detallepromocion WHERE codpromocion = " + codPromocion;
					loteSentencias.addBatch(sentenciaDelete1);
					
					String sentenciaDelete2 = 
						"DELETE FROM detallepromocionext WHERE codPromocion = " + codPromocion;
					loteSentencias.addBatch(sentenciaDelete2);
				
					String sentenciaDelete3 = 
						"DELETE FROM detallepromociontransf WHERE codpromocion = " + codPromocion;
					loteSentencias.addBatch(sentenciaDelete3);
					
					String sentenciaDelete = 
						"DELETE FROM promocion WHERE codpromocion = " + codPromocion;
					loteSentencias.addBatch(sentenciaDelete);
													
					//Ahora ejecutamos todas las sentencias de forma transaccional en la caja
					Conexiones.ejecutarLoteSentencias(loteSentencias, true, true);
				 
				} catch (SQLException e) {
					System.out.println("** Error eliminando batch de promocion: " + codPromocion + " en politica de mantenimiento");
					e.printStackTrace();
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
			System.out.println("*** Cantidad de PROMOCIONES vencidas a eliminar: 0");
		}
		// ***** 
		
		//*** SET FOREIGN_KEY_CHECKS
		try {
			 Conexiones.realizarSentencia("SET FOREIGN_KEY_CHECKS=0");
		} catch (SQLException e) {
			System.out.println("Error al realizar SET FOREIGN_KEY_CHECKS=0");
			e.printStackTrace();
		}
		
		// ***** Eliminamos los detalles de promoción que han sido anulados
		String sentenciaDelete4 = 
				"DELETE FROM detallepromocion WHERE estadoregistro= 'E'";
													
			try {
				int cantidad= Conexiones.realizarSentencia(sentenciaDelete4);
				System.out.println("*** Cantidad de DETALLESPROMOCIONES anuladas eliminadas: " + cantidad);
			} catch (SQLException e) {
				System.out.println("Error al realizar sentencia DELETE de detallepromocion anuladas");
				e.printStackTrace();
			}

		// ***** 
		// ***** Eliminamos los detalles de promoción extendida que han sido anulados
		String sentenciaDelete5 = 
					"DELETE FROM detallepromocionext WHERE estadoRegistro= 'E'";
													
		try {
			int cantidad= Conexiones.realizarSentencia(sentenciaDelete5);
			System.out.println("*** Cantidad de DETALLESPROMOCIONESEXT anuladas eliminadas: " + cantidad);
		} catch (SQLException e) {
			System.out.println("Error al realizar sentencia DELETE de detallepromocionext anuladas");
			e.printStackTrace();
		}

		// ***** 
		// ***** Eliminamos los detalles de promoción transf que han sido anulados
		String sentenciaDelete6 = 
					"DELETE FROM detallepromociontransf WHERE estadoregistro= 'E'";
													
		try {
			int cantidad = Conexiones.realizarSentencia(sentenciaDelete6);
			System.out.println("*** Cantidad de DETALLESPROMOCIONESTRANSF  eliminadas: " + cantidad);
		} catch (SQLException e) {
			System.out.println("Error al realizar sentencia DELETE de detallepromociontransf anuladas");
			e.printStackTrace();
		}
		// ***** 
		
		//*** SET FOREIGN_KEY_CHECKS
		try {
			 Conexiones.realizarSentencia("SET FOREIGN_KEY_CHECKS=1");
		} catch (SQLException e) {
			System.out.println("Error al realizar SET FOREIGN_KEY_CHECKS=1");
			e.printStackTrace();
		}
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static void limpiarTransacciones() {
		Vector<Transaccion> transaccionesAEliminar =  new Vector<Transaccion>();
		boolean buscarMas=true;
		int minFila =0;
		int maxFila = 50000;
		ResultSet transacciones = null;
		String query = null;
		Calendar fechaHoy = Calendar.getInstance();
		fechaHoy.add(Calendar.MONTH, -6);
		SimpleDateFormat fechaFormat = new SimpleDateFormat("yyyy-MM-dd");
		String fechaComparacion = fechaFormat.format(fechaHoy.getTime()) ;
		
	
		//************* SELECT DE 5000 EN 5000 POR ERROR DE OUT OF MEMORY
		try{
			while (buscarMas) {
				
				query = "SELECT numtienda, fecha, numcajafinaliza, numtransaccion, numcajainicia FROM transaccion " +
				   "WHERE fecha <'" + fechaComparacion + "' limit " + minFila + ", " + maxFila;	
				System.out.println("---- Transacciones a Eliminar: " + query); 
				minFila = minFila + 50000;
				maxFila = maxFila + 50000;
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
		
				//******** INICIO: PROCESO DE ELIMINACION***************
				// ***** Eliminamos las transacciones con todos sus posibles detalles
				Statement loteSentencias  = null;
				if (transaccionesAEliminar != null && transaccionesAEliminar.size() > 0) {
					System.out.println("*** Cantidad de TRANSACCIONES a eliminar: " + transaccionesAEliminar.size());
					for (int i=0; i<transaccionesAEliminar.size(); i++) {
						Transaccion transaccion = (Transaccion) transaccionesAEliminar.elementAt(i);
						int numTienda = transaccion.getNumTienda();
						Date fecha = transaccion.getFecha();
						int numCaja = transaccion.getNumCaja();
						int numTransaccion = transaccion.getNumTransaccion();
						int numCajaInicia = transaccion.getNumCajaInicia();
					
						try {
							loteSentencias = Conexiones.getConexionLocal().createStatement();
						
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
							System.out.println("** Error eliminando batch de transaccion: " + numTransaccion + ", tienda: "+ numTienda+ ", fecha: " + fecha + ", caja: " + numCaja + " en politica de mantenimiento");
							e.printStackTrace();
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
					
					transaccionesAEliminar.clear();
				} else {
					System.out.println("*** Cantidad de TRANSACCIONES a eliminar: 0");
				}
				// *****  
				//******** FIN: PROCESO DE ELIMINACION***************
		
				} else {
					buscarMas = false;
				}
			}
			
			//**** Borramos las puntoAgilOperacion 
			String sentenciaDelete1 = 
				"DELETE FROM puntoAgilOperacion WHERE fecha < '" + fechaComparacion + "'";
			try {
				int cantidad = Conexiones.realizarSentencia(sentenciaDelete1);
				System.out.println("*** Cantidad de PUNTOAGILOPERACION eliminadas: " + cantidad);
			} catch (SQLException e) {
				System.out.println("Error al realizar sentencia DELETE de puntoAgilOperacion");
				e.printStackTrace();
			}
		}catch(Exception ex){
			System.out.println("Error obteniendo transacciones a eliminar");
			ex.printStackTrace();
		}finally{
			if (transacciones!=null){
				try{
					transacciones.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
				transacciones=null;
			}
		}
	}
	
	public static void limpiarArchivosDetail() {
		File archivo = new File(Sesion.getPathArchivos());
		Calendar fechaComp = Calendar.getInstance();
		fechaComp.add(Calendar.MONTH, -1);
		int cantidadEliminada = 0;
				
		String [] listaArchivos = archivo.list(new Filtro(".txt"));
		 for(int i=0; i<listaArchivos.length; i++){
	         String nombreArchivo =  listaArchivos[i];
	         try {
	        	 File archivo2 = new File(nombreArchivo);
		         
	        	 Date fecha = new Date(archivo2.lastModified());
	 	         Calendar fechaArch = Calendar.getInstance();
	 	         fechaArch.setTime(fecha);
		         if (fechaArch.before(fechaComp)) {
		        	 File archivoABorrar = new File(Sesion.getPathArchivos() + nombreArchivo);
		        	 try {
		        		 archivoABorrar.delete();
		        		 cantidadEliminada ++;
		        	 } catch (SecurityException e){
		        		 System.out.println("** No se puede borrar el Archivo: " + nombreArchivo + ", en la eliminación de archivos Detail");
		        		 e.printStackTrace();
		        	 }
		         } 
	         }  catch (Exception e){
	        	 System.out.println("** No se pudo borar el archivo: " + nombreArchivo);
	         }
		 }
		 System.out.println("*** Cantidad de ARCHIVOS DETAIL eliminados: " + cantidadEliminada);
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
			System.out.println("*** Cantidad de SERVICIOS a eliminar: " + serviciosAEliminar.size());
			for (int i=0; i<serviciosAEliminar.size(); i++) {
				Servicio servicio = (Servicio) serviciosAEliminar.elementAt(i);
				int numTienda = servicio.getNumTienda();
				String codTipoServ = servicio.getCodTipoServicio().trim();
				int numServicio = servicio.getNumServicio();
				Date fecha = servicio.getFecha();
			
				try {
					loteSentencias = Conexiones.getConexionLocal().createStatement();
				
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
			 
				} catch (SQLException e) {
					System.out.println("** Error eliminando batch de servicio: " + numServicio + ", tienda: "+ numTienda+ ", fecha: " + fecha + " en politica de mantenimiento");
					e.printStackTrace();
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
			System.out.println("*** Cantidad de SERVICIOS a eliminar: 0");
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
			System.out.println("*** Cantidad de LISTAS DE REGALOS a eliminar: " + listasAEliminar.size());
			for (int i=0; i<listasAEliminar.size(); i++) {
				int codLista = ((Integer)listasAEliminar.elementAt(i)).intValue();			
				try {
					loteSentencias = Conexiones.getConexionLocal().createStatement();
				
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
			 
				} catch (SQLException e) {
					System.out.println("** Error eliminando batch de lista de regalos: " + codLista + " en politica de mantenimiento");
					e.printStackTrace();
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
			System.out.println("*** Cantidad de LISTAS DE REGALOS a eliminar: 0");
		}
		// *****  
	}
	
	public static void resetMasterMYSQL() {
		String sentenciaResetMaster = "RESET MASTER";
		
		try {
			Conexiones.realizarSentencia(sentenciaResetMaster);
			System.out.println("*** Limpieza de LOGS de MYSQL ejecutado (RESET MASTER)");
		} catch (SQLException e) {
			System.out.println("Error al realizar sentencia RESET MASTER");
			e.printStackTrace();
		}
	}
	
}
