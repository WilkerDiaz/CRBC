/**
 * =============================================================================
 * Proyecto   : TransferenciaInmediataProdsCR
 * Paquete    : com.beco.colascr.transferenciainmediata.mediadoresbd
 * Programa   : Mediador.java
 * Creado por : gmartinelli
 * Creado en  : 28-jun-05 8:51:08
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1
 * Fecha       : 28-jun-05 8:51:08
 * Analista    : gmartinelli
 * Descripción : Implementación inicial.
 * =============================================================================
 */
package com.beco.colascr.transferenciainmediata.mediadoresbd; 

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Vector;

import com.beco.colascr.transferenciainmediata.entidades.Ciudad;
import com.beco.colascr.transferenciainmediata.entidades.CodigoExterno;
import com.beco.colascr.transferenciainmediata.entidades.CondicionPromocion;
import com.beco.colascr.transferenciainmediata.entidades.DetallePromocion;
import com.beco.colascr.transferenciainmediata.entidades.DetallePromocionExt;
import com.beco.colascr.transferenciainmediata.entidades.Donacion;
import com.beco.colascr.transferenciainmediata.entidades.Estado;
import com.beco.colascr.transferenciainmediata.entidades.Producto;
import com.beco.colascr.transferenciainmediata.entidades.Promocion;
import com.beco.colascr.transferenciainmediata.entidades.TransaccionPremControl;
import com.beco.colascr.transferenciainmediata.sesion.Caja;
import com.beco.colascr.transferenciainmediata.sesion.Sesion;
import com.beco.colascr.transferenciainmediata.entidades.Urbanizacion;

/**
 * Descripción:
 * 
 */

public class Mediador {

	/**
	 * Método obtenerConexion
	 * 
	 * @param host Ip donde se requiere realizar la conexion
	 * @param bd Tipo de Base de Datos (MySql, as400)
	 * @param esquema Nombre de la Base de Datos
	 * @return
	 * Connection
	 */
	private Connection obtenerConexion(String host, String bd) {
		System.out.print("Conectando " + host + " " + bd);
		try {
			Class.forName((bd.equalsIgnoreCase("MySql")) ? "com.mysql.jdbc.Driver" : "com.ibm.as400.access.AS400JDBCDriver");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
		}
		try {
			DriverManager.setLoginTimeout(20);
			return DriverManager.getConnection("jdbc:" + bd.toLowerCase() + "://" + host.trim() + "/" + Sesion.getDbEsquema(), Sesion.getDbUsuario(), Sesion.getDbClave());
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}	

	/**
	 * Método obtenerCajas
	 * 
	 * @param numeroTienda Numero de la tienda a Sincronizar
	 * void
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public Vector<Caja> obtenerIpCajas(int numeroTienda) {
																								
		String sentenciaSql = "select numcaja, ipcaja from " + Sesion.getDbEsquema() + ".caja";// where numtienda = " + numeroTienda;
		Vector<Caja> cajas = new Vector<Caja>();
		ResultSet resultado = null;
		Statement query = null;
		Connection conexion = null;
		try {
			conexion = obtenerConexion(Sesion.IP_LOCAL, Sesion.BD_LOCAL);
			query = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultado = query.executeQuery(sentenciaSql);
			resultado.beforeFirst();
			while (resultado.next()) {
				Caja caja = new Caja(resultado.getInt("numcaja"), resultado.getString("ipcaja"));
				cajas.addElement(caja);
			}
			return cajas;
		} catch (Exception e1) {
//			e1.printStackTrace();
			// Error de Comunicacion.
			return null;
		} finally {
			try {resultado.close();} catch (Exception e) {}
			try {query.close();} catch (Exception e) {}
			try {conexion.close();} catch (Exception e) {}
		}
	}

	/**
	 * Método cargarProductos
	 * 
	 * 
	 * void
	 */
	public void cargarProductos(String pathProducto) {

		// Obtenemos los productos
		System.out.println(pathProducto);
		//String linea;
		String s;
		BufferedReader in = null;
		StringTokenizer registro = null;
		String separador, codProducto, descripcionCorta, descripcionLarga, codunidadventa, referenciaProveedor, 
			   marca, modelo, coddepartamento, codlineaseccion, costolista, precioregular, codimpuesto,
			   cantVtaEmpaq, desctoVEmpaque, indDctoEmplead, indDespa, estadoProd;
		//int numeroBatch = 200;
		
		try {
			/* Archivo de Entrada */
			FileInputStream farchivo = new FileInputStream(pathProducto);
			in = new BufferedReader(new InputStreamReader(farchivo));
		} catch (Exception e) {
			//NO SE ENCONTRÖ EL ARCHIVO ESPECIFICADO
		}
		
		try {
			System.out.print("\n ***** Parseando Productos ***** \n");
			//int i = 0;
			while((s = in.readLine()) != null){
				try{					
					registro = new StringTokenizer(s,String.valueOf(Sesion.SEP_CAMPO), true);
					
					codProducto  = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador  = (String)registro.nextElement();
					
					descripcionCorta = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador = (String)registro.nextElement();
					
					descripcionLarga = (String)registro.nextElement();
					descripcionLarga = (descripcionLarga.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : descripcionLarga.replace(Sesion.DEL_CAMPO, ' ');
					separador = (descripcionLarga != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					
					codunidadventa = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador = (String)registro.nextElement();

					referenciaProveedor = (String)registro.nextElement();
					referenciaProveedor = (referenciaProveedor.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null :  referenciaProveedor.replace(Sesion.DEL_CAMPO, ' ');
					separador = (referenciaProveedor != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);

					marca = (String)registro.nextElement();
					marca = (marca.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : marca.replace(Sesion.DEL_CAMPO, ' ');
					separador = (marca != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);

					modelo = (String)registro.nextElement();
					modelo = (modelo.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : modelo.replace(Sesion.DEL_CAMPO, ' ');
					separador = (modelo != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);

					coddepartamento = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador = (String)registro.nextElement();

					codlineaseccion = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador = (String)registro.nextElement();

					costolista = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador = (String)registro.nextElement();

					precioregular = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador = (String)registro.nextElement();

					codimpuesto = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador = (String)registro.nextElement();

					cantVtaEmpaq = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador = (String)registro.nextElement();

					desctoVEmpaque = (String)registro.nextElement();
					desctoVEmpaque = (desctoVEmpaque.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : desctoVEmpaque.replace(Sesion.DEL_CAMPO, ' ');
					separador = (desctoVEmpaque != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);

					indDctoEmplead = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador = (String)registro.nextElement();

					indDespa = (String)registro.nextElement();
					indDespa = (indDespa.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : indDespa.replace(Sesion.DEL_CAMPO, ' ');
					separador = (indDespa != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);

					estadoProd= ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');

					Producto producto = new Producto(codProducto,  descripcionCorta, descripcionLarga, Integer.parseInt(codunidadventa.trim()), 
							referenciaProveedor, marca, modelo, coddepartamento, codlineaseccion, Double.parseDouble(costolista.trim()), 
							Double.parseDouble(precioregular.trim()), codimpuesto, Integer.parseInt(cantVtaEmpaq.trim()), (desctoVEmpaque!=null)?Double.parseDouble(desctoVEmpaque.trim()):0,
							indDctoEmplead, indDespa, estadoProd/*, Timestamp.valueOf(actualizacion)*/);
					Sesion.agregarProducto(producto);
				}catch(NoSuchElementException e){
//					System.out.println("Numero Registro " + i);
					e.printStackTrace();
				}	
			}
			in.close();
		}
		catch(java.io.IOException io){io.printStackTrace();}
		catch (Exception ex){ex.printStackTrace();}
	}

	/**
	 * Método cargarProductos
	 * 
	 * 
	 * void
	 */
	public void cargarCodigosExternos(String pathProducto) {

		System.out.println(pathProducto);
		// Obtenemos los productos
		//String linea;
		String s;
		BufferedReader in = null;
		StringTokenizer registro = null;
		String separador, codProducto, codExterno;
		//int numeroBatch = 200;
		
		try {
			/* Archivo de Entrada */
			FileInputStream farchivo = new FileInputStream(pathProducto);
			in = new BufferedReader(new InputStreamReader(farchivo));
		} catch (Exception e) {
			//NO SE ENCONTRÖ EL ARCHIVO ESPECIFICADO
		}
		
		try {
			System.out.print("\n ***** Parseando Codigos Externos ***** \n");
			while((s = in.readLine()) != null){
				try{					
					registro = new StringTokenizer(s,String.valueOf(Sesion.SEP_CAMPO), true);
					
					codProducto  = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador  = (String)registro.nextElement();
					
					codExterno = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador = (String)registro.nextElement();
					
					CodigoExterno codigoExterno = new CodigoExterno(codProducto.trim(), codExterno.trim());
					Sesion.agregarCodExterno(codigoExterno);
				}catch(NoSuchElementException e){
//					System.out.println("Numero Registro " + i);
					e.printStackTrace();
				}				
			}
			in.close();
		}
		catch(java.io.IOException io){io.printStackTrace();}
		catch (Exception ex){ex.printStackTrace();}
	}
	
	/**
	 * Método cargarPromociones
	 * 
	 * void
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se eliminaron variables no usadas
	* Fecha: agosto 2011
	*/
	public void cargarPromociones(String pathProducto) {
		
		System.out.println(pathProducto);
		// Obtenemos los productos
		//String linea;
		String s;
		BufferedReader in = null;
		StringTokenizer registro = null;
		String separador, codPromocion, tipoPromocion, fechaInicio, horaInicio, fechaFinaliza, 
				horaFinaliza, prioridad;
		//int numeroBatch = 200;
		
		try {
			/* Archivo de Entrada */
			FileInputStream farchivo = new FileInputStream(pathProducto);
			in = new BufferedReader(new InputStreamReader(farchivo));
		} catch (Exception e) {
			//NO SE ENCONTRÖ EL ARCHIVO ESPECIFICADO
		}
		
		try {
			System.out.print("\n ***** Parseando Promociones ***** \n");
			while((s = in.readLine()) != null){
				try{					
					registro = new StringTokenizer(s,String.valueOf(Sesion.SEP_CAMPO), true);
					
					codPromocion  = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador  = (String)registro.nextElement();
					
					tipoPromocion = (String)registro.nextElement();
					tipoPromocion = (tipoPromocion.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : tipoPromocion.replace(Sesion.DEL_CAMPO, ' ');
					separador = (tipoPromocion != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					
					fechaInicio = (String)registro.nextElement();
					fechaInicio = (fechaInicio.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : fechaInicio.replace(Sesion.DEL_CAMPO, ' ');
					separador = (fechaInicio != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					
					horaInicio = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador = (String)registro.nextElement();

					fechaFinaliza = (String)registro.nextElement();
					fechaFinaliza = (fechaFinaliza.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null :  fechaFinaliza.replace(Sesion.DEL_CAMPO, ' ');
					separador = (fechaFinaliza != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);

					horaFinaliza = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador = (String)registro.nextElement();

					prioridad = (String)registro.nextElement();
					prioridad = (prioridad.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : prioridad.replace(Sesion.DEL_CAMPO, ' ');
					
					Promocion promocion = new Promocion(Integer.parseInt(codPromocion.trim()), tipoPromocion, fechaInicio, horaInicio, fechaFinaliza, horaFinaliza, prioridad);
					Sesion.agregarPromocion(promocion);
					//Promociones
					if (pathProducto.equals(Sesion.pathArchivos+ "transfInmedPromoCR_Ext")) 
						Sesion.setPromo(true);
					try {
						/* Archivo de Entrada */
						eliminarArchivoPromo(pathProducto);
					} catch (Exception e) {
						//NO SE ENCONTRÖ EL ARCHIVO ESPECIFICADO
					}
					
				}catch(NoSuchElementException e){
//					System.out.println("Numero Registro " + i);
					e.printStackTrace();
				}				
			}
			in.close();
		}
		catch(java.io.IOException io){io.printStackTrace();}
		catch (Exception ex){ex.printStackTrace();}
	}

	/**
	 * Método cargarDetallePromociones
	 * 
	 * void
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se eliminaron variables sin uso
	* Fecha: agosto 2011
	*/
	public void cargarDetallePromociones(String pathProducto) {
		System.out.println(pathProducto);
		// Obtenemos los productos
		String s;
		BufferedReader in = null;
		StringTokenizer registro = null;
		String separador, codPromocion, numDetalle, numCupon, codDpto, codLinSecc, 
				codProducto, porcentajeDescto, precioFinal, estadoRegistro;

		
		try {
			/* Archivo de Entrada */
			FileInputStream farchivo = new FileInputStream(pathProducto);
			in = new BufferedReader(new InputStreamReader(farchivo));
		} catch (Exception e) {
			//NO SE ENCONTRÖ EL ARCHIVO ESPECIFICADO
		}
		
		try {
			System.out.print("\n ***** Parseando Detalle Promocion ***** \n");
			while((s = in.readLine()) != null){
				try{					
					registro = new StringTokenizer(s,String.valueOf(Sesion.SEP_CAMPO), true);
					
					codPromocion  = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador  = (String)registro.nextElement();
					//System.out.println("codPromocion"+codPromocion);
					
					numDetalle = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador = (String)registro.nextElement();
					//System.out.println("numDetalle"+numDetalle);
					
					numCupon = (String)registro.nextElement();
					numCupon = (numCupon.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : numCupon.replace(Sesion.DEL_CAMPO, ' ').trim();
					separador = (numCupon != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					//System.out.println("numCupon"+numCupon);
					
					codDpto = (String)registro.nextElement();
					codDpto = (codDpto.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : codDpto.replace(Sesion.DEL_CAMPO, ' ').trim();
					separador = (codDpto != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					//System.out.println("codDpto"+codDpto);
					
					codLinSecc = (String)registro.nextElement();
					codLinSecc = (codLinSecc.equals(String.valueOf(Sesion.SEP_CAMPO))) ? "\\N": codLinSecc.replace(Sesion.DEL_CAMPO, ' ').trim();
					separador = (codLinSecc != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					//System.out.println("codLinSecc"+codLinSecc);
					
					codProducto = (String)registro.nextElement();
					codProducto = (codProducto.equals(String.valueOf(Sesion.SEP_CAMPO))) ? "\\N" : codProducto.replace(Sesion.DEL_CAMPO, ' ').trim();
					separador = (codProducto != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					//System.out.println("codProducto"+codProducto);
					
					porcentajeDescto = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ').trim();
					separador = (String)registro.nextElement();
					//System.out.println("porcentajeDescto"+porcentajeDescto);
					
					precioFinal = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ').trim();
					separador = (String)registro.nextElement();
					//System.out.println("precioFinal"+precioFinal);
					
					estadoRegistro = (String)registro.nextElement();
					estadoRegistro = (estadoRegistro.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : estadoRegistro.replace(Sesion.DEL_CAMPO, ' ').trim();
					
					
					DetallePromocion detallePromocion = new DetallePromocion(Integer.parseInt(codPromocion.trim()), Integer.parseInt(numDetalle.trim()),
						(numCupon!=null)?Integer.parseInt(numCupon.trim()):0, codDpto.trim(), codLinSecc.trim().equals("\\N")?null:codLinSecc.trim()
						, codProducto.trim().equals("\\N")?null:codProducto.trim(), 
						Double.parseDouble(porcentajeDescto.trim()), Double.parseDouble(precioFinal.trim()), estadoRegistro.trim());
					Sesion.agregarDetallePromocion(detallePromocion);
				}catch(NoSuchElementException e){
					//System.out.println("Numero Registro " + i);
					e.printStackTrace();
				}				
			}
			
			eliminarArchivoPromo(pathProducto);
			
		}
		catch(java.io.IOException io){io.printStackTrace();}
		catch (Exception ex){ex.printStackTrace();}
		finally{
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

	/**
	 * Método grabarProductos
	 * 
	 * @param ipBdDestino
	 * @param numeroCaja
	 * void
	 */
	public void grabarProductos(String ipBdDestino, int numeroCaja) {
		// Cargamos archivo de Productos
//		this.loadDataMySql(Sesion.pathArchivos + "transfInmedProdCR", "producto", ipBdDestino);
		// Cargamos archivo de Códigos externos de productos
//		this.loadDataMySql(Sesion.pathArchivos + "transfInmedCodExt", "prodcodigoexterno", ipBdDestino);
		// Cargamos archivo de Cabecera de Promociones
//		this.loadDataMySql(Sesion.pathArchivos + "transfInmedPromoCR", "promocion", ipBdDestino);
		// Cargamos archivo de Detalle de Promociones
//		this.loadDataMySql(Sesion.pathArchivos + "transfInmedPromTda", "detallepromocion", ipBdDestino);
		Statement query = null;
		Connection conexion = null;
		boolean hacerArchivo = true;
		try {
			
//			System.out.println("Iniciando " + ipBdDestino);
			conexion = obtenerConexion(ipBdDestino, "mysql");
			if (conexion!=null ) {
				query = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				query.executeUpdate("set foreign_key_checks=0");
				for (int i=0; i<Sesion.getProductos().size(); i++) {
					String sentenciaSql = "replace into " + Sesion.getDbEsquema() + ".producto (codproducto, " +										"descripcioncorta, descripcionlarga, codunidadventa, referenciaproveedor, " +										"marca, modelo, coddepartamento, codlineaseccion, costolista, precioregular, " +										"codimpuesto, cantidadventaempaque, desctoventaempaque, indicadesctoempleado, " +										"indicadespachar, estadoproducto) values (" +
										((Producto)Sesion.getProductos().elementAt(i)).getCodproducto() + "," + 
										((Producto)Sesion.getProductos().elementAt(i)).getDescripcionCorta() + "," + 
										((Producto)Sesion.getProductos().elementAt(i)).getDescripcionLarga() + "," + 
										((Producto)Sesion.getProductos().elementAt(i)).getCodUnidadVenta() + "," + 
										((Producto)Sesion.getProductos().elementAt(i)).getRefProveedor() + "," + 
										((Producto)Sesion.getProductos().elementAt(i)).getMarca() + "," + 
										((Producto)Sesion.getProductos().elementAt(i)).getModelo() + "," + 
										((Producto)Sesion.getProductos().elementAt(i)).getCodDepartamento() + "," + 
										((Producto)Sesion.getProductos().elementAt(i)).getCodLineaSecc() + "," + 
										((Producto)Sesion.getProductos().elementAt(i)).getCostoLista() + "," + 
										((Producto)Sesion.getProductos().elementAt(i)).getPrecioRegular() + "," + 
										((Producto)Sesion.getProductos().elementAt(i)).getCodImpuesto() + "," + 
										((Producto)Sesion.getProductos().elementAt(i)).getCantVtaEmpaque() + "," + 
										((Producto)Sesion.getProductos().elementAt(i)).getDsctoVtaEmpaque() + "," + 
										((Producto)Sesion.getProductos().elementAt(i)).getIndDsctoEmpleado() + "," + 
										((Producto)Sesion.getProductos().elementAt(i)).getIndicaDespachar() + "," + 
										((Producto)Sesion.getProductos().elementAt(i)).getEstadoProducto() + ")";//"," + 
										//((Producto)Sesion.getProductos().elementAt(i)).getActualizacion() + ")";
	//				System.out.println("Query " + i + " de Caja " + numeroCaja);
					try {
						//System.out.println(sentenciaSql);
						query.executeUpdate(sentenciaSql);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
	
				for (int i=0; i<Sesion.getCodExternos().size(); i++) {
					String sentenciaSql = "replace into " + Sesion.getDbEsquema() + ".prodcodigoexterno (codproducto, codexterno) values (" +
										((CodigoExterno)Sesion.getCodExternos().elementAt(i)).getCodProducto() + "," + 
										((CodigoExterno)Sesion.getCodExternos().elementAt(i)).getCodExterno() + ")";//"," +
										//((CodigoExterno)Sesion.getCodExternos().elementAt(i)).getActualizacion() + ")";
					try {
						query.executeUpdate(sentenciaSql);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				String eliminarExistentes2 = null;
				for (int i=0; i<Sesion.getPromociones().size(); i++) {
					String sentenciaSql = "replace into " + Sesion.getDbEsquema() + ".promocion values (" +
										((Promocion)Sesion.getPromociones().elementAt(i)).getCodPromocion() + "," + 
										((Promocion)Sesion.getPromociones().elementAt(i)).getTipoPromocion() + "," + 
										((Promocion)Sesion.getPromociones().elementAt(i)).getFechaInicio() + "," + 
										((Promocion)Sesion.getPromociones().elementAt(i)).getHoraInicio() + "," + 
										((Promocion)Sesion.getPromociones().elementAt(i)).getFechaFinal() + "," + 
										((Promocion)Sesion.getPromociones().elementAt(i)).getHoraFinal() + "," + 
										((Promocion)Sesion.getPromociones().elementAt(i)).getPrioridad() + ")";
					eliminarExistentes2 = "UPDATE  " + Sesion.getDbEsquema() + ".detallepromocionext SET estadoregistro = 'E'"+
							" where codpromocion="+((Promocion)Sesion.getPromociones().elementAt(i)).getCodPromocion();
	//				System.out.println("Query " + i + " de Caja " + numeroCaja);
					try {
						System.out.println(sentenciaSql);						
						int x = query.executeUpdate(sentenciaSql);
						if (x!=0)hacerArchivo&=false;
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}

				for (int i=0; i<Sesion.getDetallePromociones().size(); i++) {
					String sentenciaSql = "replace into " + Sesion.getDbEsquema() + ".detallepromocion values (" +
					((DetallePromocion)Sesion.getDetallePromociones().elementAt(i)).getCodPromocion() + "," +
					((DetallePromocion)Sesion.getDetallePromociones().elementAt(i)).getNumDetalle() + "," + 
					((DetallePromocion)Sesion.getDetallePromociones().elementAt(i)).getNumCupon() + "," + 
					((DetallePromocion)Sesion.getDetallePromociones().elementAt(i)).getCodDepartamento() + "," + 
					((DetallePromocion)Sesion.getDetallePromociones().elementAt(i)).getCodLineaSeccion() + "," + 
					((DetallePromocion)Sesion.getDetallePromociones().elementAt(i)).getCodProducto() + "," + 
					((DetallePromocion)Sesion.getDetallePromociones().elementAt(i)).getPorcentaje() + "," + 
					((DetallePromocion)Sesion.getDetallePromociones().elementAt(i)).getPrecio() + ",'" +
					((DetallePromocion)Sesion.getDetallePromociones().elementAt(i)).getEstadoRegistro() +"')";
	//				System.out.println("Query " + i + " de Caja " + numeroCaja);
					try {
						query.executeUpdate(sentenciaSql);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				
				int lastNumDetail =0;
				for (int i=0; i<Sesion.getDetallePromocionesExt().size(); i++) {
					String codCateg = ((DetallePromocionExt)Sesion.getDetallePromocionesExt().elementAt(i)).getCodCateg();
					codCateg = codCateg.equals("NULL")? codCateg : "'"+codCateg+"'";
					String codDpto = ((DetallePromocionExt)Sesion.getDetallePromocionesExt().elementAt(i)).getCodDpto();
					codDpto = codDpto.equals("NULL")? codDpto : "'"+codDpto+"'";
					String codLinea = ((DetallePromocionExt)Sesion.getDetallePromocionesExt().elementAt(i)).getCodLinea();
					codLinea = codLinea.equals("NULL")? codLinea : "'"+codLinea+"'";
					String codSeccion = ((DetallePromocionExt)Sesion.getDetallePromocionesExt().elementAt(i)).getCodSeccion();
					codSeccion = codSeccion.equals("NULL")? codSeccion : "'"+codSeccion+"'";
					String marca = ((DetallePromocionExt)Sesion.getDetallePromocionesExt().elementAt(i)).getMarca();
					marca = marca.equals("NULL")? marca : "'"+marca+"'";
					String refProv = ((DetallePromocionExt)Sesion.getDetallePromocionesExt().elementAt(i)).getRefProv();
					refProv =refProv.equals("NULL")? refProv : "'"+refProv+"'";
					String codProd = ((DetallePromocionExt)Sesion.getDetallePromocionesExt().elementAt(i)).getCodproducto();
					codProd=codProd.equals("NULL")? codProd : "'"+codProd+"'";
					
					
					String sentenciaSql = "replace into " + Sesion.getDbEsquema() + ".detallepromocionext values (" +
					((DetallePromocionExt)Sesion.getDetallePromocionesExt().elementAt(i)).getCodPromocion() + "," +
					((DetallePromocionExt)Sesion.getDetallePromocionesExt().elementAt(i)).getNumDetalle() + "," + 
					((DetallePromocionExt)Sesion.getDetallePromocionesExt().elementAt(i)).getPorcentajeDescto() + "," + 
					codCateg+ "," + 
					codDpto+ "," + 
					marca + "," + 
					codLinea + "," + 
					refProv + "," + 
					((DetallePromocionExt)Sesion.getDetallePromocionesExt().elementAt(i)).getMontoMinimo() + "," +
					((DetallePromocionExt)Sesion.getDetallePromocionesExt().elementAt(i)).getCantidadMinima() + "," +
					((DetallePromocionExt)Sesion.getDetallePromocionesExt().elementAt(i)).getCantidadRegalada() + "," +
					codProd + "," +
					((DetallePromocionExt)Sesion.getDetallePromocionesExt().elementAt(i)).getBsDescuento() + ",'" + 
					((DetallePromocionExt)Sesion.getDetallePromocionesExt().elementAt(i)).getEstadoRegistro()+ "','" +
					((DetallePromocionExt)Sesion.getDetallePromocionesExt().elementAt(i)).getNombrePromocion() + "'," + 
					((DetallePromocionExt)Sesion.getDetallePromocionesExt().elementAt(i)).getProdSinConsecutivo() + "," +
					codSeccion + ",'" + 
					((DetallePromocionExt)Sesion.getDetallePromocionesExt().elementAt(i)).getAcumulaPremio()+"')";
					//System.out.println("Query " + i + " de Caja " + numeroCaja);
					try {
						System.out.println(sentenciaSql);
						query.executeUpdate(sentenciaSql);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					sentenciaSql="replace into " + Sesion.getDbEsquema() + ".transferenciainmediatapromo values ("+
					((DetallePromocionExt)Sesion.getDetallePromocionesExt().elementAt(i)).getCodPromocion()+", "+
					((DetallePromocionExt)Sesion.getDetallePromocionesExt().elementAt(i)).getNumDetalle()+")";
					query.executeUpdate(sentenciaSql);
					System.out.println(sentenciaSql);
					lastNumDetail = ((DetallePromocionExt)Sesion.getDetallePromocionesExt().elementAt(i)).getNumDetalle();
				}
				if (lastNumDetail>0 && eliminarExistentes2!=null){
					System.out.println("ULTIMO DETALLE"+lastNumDetail);// Borrar o inhabilitar detalles mayores
					eliminarExistentes2 += " AND numDetalle>"+lastNumDetail;
					query.execute(eliminarExistentes2);
				}
				for (int i=0; i<Sesion.getDonacion().size(); i++) {
					String sentenciaSql = "replace into " + Sesion.getDbEsquema() + ".donacion values (" +
					((Donacion)Sesion.getDonacion().elementAt(i)).getCodDonacion() + "," +
					((Donacion)Sesion.getDonacion().elementAt(i)).getCodBarraProdDonacion() + ",'" + 
					((Donacion)Sesion.getDonacion().elementAt(i)).getFechaInicio() + "','" + 
					((Donacion)Sesion.getDonacion().elementAt(i)).getFechaFinaliza()+ "','" + 
					((Donacion)Sesion.getDonacion().elementAt(i)).getNombreDonacion() + "','" + 
					((Donacion)Sesion.getDonacion().elementAt(i)).getDescDonacion() + "'," + 
					((Donacion)Sesion.getDonacion().elementAt(i)).getTipoDonacion() + "," + 
					((Donacion)Sesion.getDonacion().elementAt(i)).getEstadoDonacion() + "," +
					((Donacion)Sesion.getDonacion().elementAt(i)).getCantidadPropuesta() + ",'" +
					((Donacion)Sesion.getDonacion().elementAt(i)).getRegactualizado() + "','" +
					((Donacion)Sesion.getDonacion().elementAt(i)).getMostrarAlTotalizar() + "')";
					System.out.println("Query " + i + " de Caja " + numeroCaja);
					try {
						System.out.println(sentenciaSql);
						query.executeUpdate(sentenciaSql);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				for (int i=0; i<Sesion.getTransaccionPremControl().size(); i++) {
					String sentenciaSql = "replace into " + Sesion.getDbEsquema() + ".transaccionpremcontrol values (" +
					((TransaccionPremControl)Sesion.getTransaccionPremControl().elementAt(i)).getNroTransacciones() + "," +
					((TransaccionPremControl)Sesion.getTransaccionPremControl().elementAt(i)).getNroTransaccionesXDia()+ "," + 
					((TransaccionPremControl)Sesion.getTransaccionPremControl().elementAt(i)).getMaxPremio() + "," + 
					((TransaccionPremControl)Sesion.getTransaccionPremControl().elementAt(i)).getMaxPremioXDia()+ "," + 
					((TransaccionPremControl)Sesion.getTransaccionPremControl().elementAt(i)).getCodPromocion() + ")";
	//				System.out.println("Query " + i + " de Caja " + numeroCaja);
					try {
						System.out.println(sentenciaSql);
						query.executeUpdate(sentenciaSql);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				for (int i=0; i<Sesion.getCondicionPromocion().size(); i++) {
					String sentenciaSql = "replace into " + Sesion.getDbEsquema() + ".condicionpromocion values (" +
					((CondicionPromocion)Sesion.getCondicionPromocion().elementAt(i)).getCodPromocion() + "," +
					((CondicionPromocion)Sesion.getCondicionPromocion().elementAt(i)).getOrden()+ ",'" + 
					((CondicionPromocion)Sesion.getCondicionPromocion().elementAt(i)).getLineaCondicion() + "')";
	//				System.out.println("Query " + i + " de Caja " + numeroCaja);
					try {
						//System.out.println(sentenciaSql);
						query.executeUpdate(sentenciaSql);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				query.executeUpdate("set foreign_key_checks=1");
				System.out.println("OK " + ipBdDestino);
			} else {
				System.out.println("Abortado por no conectarnos " + ipBdDestino);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("error " + ipBdDestino);
		} finally {
			try {query.close();} catch (Exception e) {}
			try {conexion.close();} catch (Exception e) {}
		}
	}

	/**
	 * Método cargarProductos
	 * 
	 * 
	 * void
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se eliminaron variables sin uso
	* Fecha: agosto 2011
	*/
	public void cargarEdoCiuUrb(String pathAtcm) {

		System.out.println(pathAtcm);
		// Obtenemos los estados, ciudades o urbanizaciones
		String s;
		BufferedReader in = null;
		StringTokenizer registro = null;
		String separador = "", tabla = "", codEdo = "", codCiu = "", codUrb = "", desEdo = "",
				desCiu = "", desUrb = "", zonPos = "", codArea1 = "", usrEli = "", staEli = "";
		
		try {
			/* Archivo de Entrada */
			FileInputStream farchivo = new FileInputStream(pathAtcm);
			in = new BufferedReader(new InputStreamReader(farchivo));
		} catch (Exception e) {
			//NO SE ENCONTRÖ EL ARCHIVO ESPECIFICADO
		}
		
		try {
			System.out.print("\n ***** Parseando Estados, Ciudades y Urbanizaciones ***** \n");
			while((s = in.readLine()) != null){
				try{					
					registro = new StringTokenizer(s,String.valueOf(Sesion.SEP_CAMPO), true);
					
					tabla = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador  = (String)registro.nextElement();
					
					if (tabla.trim().equalsIgnoreCase("atcm23")) {
						codEdo = (String)registro.nextElement();
						codEdo = (codEdo.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : codEdo.replace(Sesion.DEL_CAMPO, ' ');
						separador = (codEdo != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
						
						desEdo = (String)registro.nextElement();
						desEdo = (desEdo.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : desEdo.replace(Sesion.DEL_CAMPO, ' ');
						separador = (desEdo != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);

						staEli = (String)registro.nextElement();
						staEli = (staEli.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : staEli.replace(Sesion.DEL_CAMPO, ' ');
						separador = (staEli != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);

						usrEli = (String)registro.nextElement();
						usrEli = (usrEli.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : usrEli.replace(Sesion.DEL_CAMPO, ' ');

						Estado estado = new Estado(Integer.parseInt(codEdo.trim()), desEdo.trim(), staEli, usrEli);
						Sesion.agregarEstado(estado);
					} else {
						if (tabla.trim().equalsIgnoreCase("atcm24")) {
							codEdo = (String)registro.nextElement();
							codEdo = (codEdo.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : codEdo.replace(Sesion.DEL_CAMPO, ' ');
							separador = (codEdo != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
						
							codCiu = (String)registro.nextElement();
							codCiu = (codCiu.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : codCiu.replace(Sesion.DEL_CAMPO, ' ');
							separador = (codCiu != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);

							desCiu = (String)registro.nextElement();
							desCiu = (desCiu.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : desCiu.replace(Sesion.DEL_CAMPO, ' ');
							separador = (desCiu != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);

							codArea1 = (String)registro.nextElement();
							codArea1 = (codArea1.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : codArea1.replace(Sesion.DEL_CAMPO, ' ');
							separador = (codArea1 != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);

							staEli = (String)registro.nextElement();
							staEli = (staEli.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : staEli.replace(Sesion.DEL_CAMPO, ' ');
							separador = (staEli != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);

							usrEli = (String)registro.nextElement();
							usrEli = (usrEli.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : usrEli.replace(Sesion.DEL_CAMPO, ' ');

							Ciudad ciudad = new Ciudad(Integer.parseInt(codEdo.trim()), Integer.parseInt(codCiu.trim()), desCiu.trim(), Integer.parseInt(codArea1.trim()), staEli, usrEli);
							Sesion.agregarCiudad(ciudad);
						} else {
							if (tabla.trim().equalsIgnoreCase("atcm25")) {
								codEdo = (String)registro.nextElement();
								codEdo = (codEdo.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : codEdo.replace(Sesion.DEL_CAMPO, ' ');
								separador = (codEdo != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
						
								codCiu = (String)registro.nextElement();
								codCiu = (codCiu.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : codCiu.replace(Sesion.DEL_CAMPO, ' ');
								separador = (codCiu != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);

								codUrb = (String)registro.nextElement();
								codUrb = (codUrb.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : codUrb.replace(Sesion.DEL_CAMPO, ' ');
								separador = (codUrb != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);

								desUrb = (String)registro.nextElement();
								desUrb = (desUrb.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : desUrb.replace(Sesion.DEL_CAMPO, ' ');
								separador = (desUrb != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);

								zonPos = (String)registro.nextElement();
								zonPos = (zonPos.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : zonPos.replace(Sesion.DEL_CAMPO, ' ');
								separador = (zonPos != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);

								staEli = (String)registro.nextElement();
								staEli = (staEli.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : staEli.replace(Sesion.DEL_CAMPO, ' ');
								separador = (staEli != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);

								usrEli = (String)registro.nextElement();
								usrEli = (usrEli.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : usrEli.replace(Sesion.DEL_CAMPO, ' ');

								Urbanizacion urbanizacion = new Urbanizacion(Integer.parseInt(codEdo.trim()), Integer.parseInt(codCiu.trim()), Integer.parseInt(codUrb.trim()), desUrb.trim(), zonPos.trim(), staEli, usrEli);
								Sesion.agregarUrbanizacion(urbanizacion);
							}
						}
					}
					
				}catch(NoSuchElementException e){
//					System.out.println("Numero Registro " + i);
					e.printStackTrace();
				}				
			}
			in.close();
		}
		catch(java.io.IOException io){io.printStackTrace();}
		catch (Exception ex){ex.printStackTrace();}
	}

	/**
	 * Método grabarEstados
	 * 
	 * @param ipBdDestino
	 * @param numeroCaja
	 * void
	 */
	public void grabarEstados(String ipBdDestino, int numeroCaja) {
		Statement query = null;
		Connection conexion = null;
		try {
//			System.out.println("Iniciando " + ipBdDestino);
			conexion = obtenerConexion(ipBdDestino, "mysql");
			if (conexion!=null ) {
				query = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				query.executeUpdate("set foreign_key_checks=0");
				for (int i=0; i<Sesion.getEstados().size(); i++) {
					String sentenciaSql = "replace into " + Sesion.getDbEsquema() + ".atcm23 (codedo, desedo, " +
										"staeli, usreli) values (" +
										((Estado)Sesion.getEstados().elementAt(i)).getCodEstado() + "," + 
										((Estado)Sesion.getEstados().elementAt(i)).getDescripcion() + "," + 
										((Estado)Sesion.getEstados().elementAt(i)).getStatusEliminacion() + "," + 
										((Estado)Sesion.getEstados().elementAt(i)).getUsuarioEliminacion() + ")"; 
					try {
						//System.out.println(sentenciaSql);
						query.executeUpdate(sentenciaSql);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				query.executeUpdate("set foreign_key_checks=1");
				//System.out.println("Fino " + ipBdDestino);
			} else {
				System.out.println("Abortado por no conectarnos " + ipBdDestino);
			}
		} catch (Exception e1) {
			//e1.printStackTrace();
			//System.out.println("Chimbo " + ipBdDestino);
		} finally {
			try {query.close();} catch (Exception e) {}
			try {conexion.close();} catch (Exception e) {}
		}
	}


	/**
	 * Método grabarCiudades
	 * 
	 * @param ipBdDestino
	 * @param numeroCaja
	 * void
	 */
	public void grabarCiudades(String ipBdDestino, int numeroCaja) {
		Statement query = null;
		Connection conexion = null;
		try {
//			System.out.println("Iniciando " + ipBdDestino);
			conexion = obtenerConexion(ipBdDestino, "mysql");
			if (conexion!=null ) {
				query = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				query.executeUpdate("set foreign_key_checks=0");
				for (int i=0; i<Sesion.getCiudades().size(); i++) {
					String sentenciaSql = "replace into " + Sesion.getDbEsquema() + ".atcm24 (codedo, codciu, desciu, " +
										"codarea1, staeli, usreli) values (" +
										((Ciudad)Sesion.getCiudades().elementAt(i)).getCodEstado() + "," + 
										((Ciudad)Sesion.getCiudades().elementAt(i)).getCodCiudad() + "," + 
										((Ciudad)Sesion.getCiudades().elementAt(i)).getDescripcion() + "," + 
										((Ciudad)Sesion.getCiudades().elementAt(i)).getCodigoArea() + "," + 
										((Ciudad)Sesion.getCiudades().elementAt(i)).getStatusEliminacion() + "," + 
										((Ciudad)Sesion.getCiudades().elementAt(i)).getUsuarioEliminacion() + ")"; 
					try {
						//System.out.println(sentenciaSql);
						query.executeUpdate(sentenciaSql);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				query.executeUpdate("set foreign_key_checks=1");
				//System.out.println("Fino " + ipBdDestino);
			} else {
				System.out.println("Abortado por no conectarnos " + ipBdDestino);
			}
		} catch (Exception e1) {
			//e1.printStackTrace();
			//System.out.println("Chimbo " + ipBdDestino);
		} finally {
			try {query.close();} catch (Exception e) {}
			try {conexion.close();} catch (Exception e) {}
		}
	}


	/**
	 * Método grabarUrbanizaciones
	 * 
	 * @param ipBdDestino
	 * @param numeroCaja
	 * void
	 */
	public void grabarUrbanizaciones(String ipBdDestino, int numeroCaja) {
		Statement query = null;
		Connection conexion = null;
		try {
//			System.out.println("Iniciando " + ipBdDestino);
			conexion = obtenerConexion(ipBdDestino, "mysql");
			if (conexion!=null ) {
				query = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				query.executeUpdate("set foreign_key_checks=0");
				for (int i=0; i<Sesion.getUrbanizaciones().size(); i++) {
					String sentenciaSql = "replace into " + Sesion.getDbEsquema() + ".atcm25 (codedo, codciu, codurb, desurb, " +
										"zonpos, staeli, usreli) values (" +
										((Urbanizacion)Sesion.getUrbanizaciones().elementAt(i)).getCodEstado() + "," + 
										((Urbanizacion)Sesion.getUrbanizaciones().elementAt(i)).getCodCiudad() + "," + 
										((Urbanizacion)Sesion.getUrbanizaciones().elementAt(i)).getCodUrbanizacion() + "," + 
										((Urbanizacion)Sesion.getUrbanizaciones().elementAt(i)).getDescripcion() + "," + 
										((Urbanizacion)Sesion.getUrbanizaciones().elementAt(i)).getZonaPostal() + "," + 
										((Urbanizacion)Sesion.getUrbanizaciones().elementAt(i)).getStatusEliminacion() + "," + 
										((Urbanizacion)Sesion.getUrbanizaciones().elementAt(i)).getUsuarioEliminacion() + ")"; 
					try {
						//System.out.println(sentenciaSql);
						query.executeUpdate(sentenciaSql);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				query.executeUpdate("set foreign_key_checks=1");
				//System.out.println("Fino " + ipBdDestino);
			} else {
				System.out.println("Abortado por no conectarnos " + ipBdDestino);
			}
		} catch (Exception e1) {
			//e1.printStackTrace();
			//System.out.println("Chimbo " + ipBdDestino);
		} finally {
			try {query.close();} catch (Exception e) {}
			try {conexion.close();} catch (Exception e) {}
		}
	}
	/**
	 * Promociones 17/06/2009 
	 * @author aavila
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se eliminaron variables sin uso
	* Fecha: agosto 2011
	*/
	public void cargarDetallePromocionesExt(String pathProducto){
		System.out.println(pathProducto);
		String s;
		BufferedReader in = null;
		StringTokenizer registro = null;
		int i = 0;
		String separador, codPromocion, numDetalle, porcentajeDescto, codCateg, codDpto, codLinea, marca, refProv, montoMinimo
		, cantidadMinima, cantidadRegalada, codproducto,  bsDescuento, estadoRegistro, nombrePromocion, prodSinConsecutivo, codSeccion, acumulaPremio;
		try {
			/* Archivo de Entrada */
			FileInputStream farchivo = new FileInputStream(pathProducto);
			in = new BufferedReader(new InputStreamReader(farchivo));
		} catch (Exception e) {
			//NO SE ENCONTRÖ EL ARCHIVO ESPECIFICADO
			e.printStackTrace();
		}
		try {
			System.out.print("\n ***** Parseando DetallepromocioneExt***** \n");
			while((s = in.readLine()) != null){
				try{					
					registro = new StringTokenizer(s,String.valueOf(Sesion.SEP_CAMPO), true);	
					
					codPromocion  = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador  = (String)registro.nextElement();
					
					numDetalle = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador = (String)registro.nextElement();
					
					porcentajeDescto = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ').trim();
					porcentajeDescto = (porcentajeDescto.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : porcentajeDescto.replace(Sesion.DEL_CAMPO, ' ').trim();
					separador = (porcentajeDescto != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					
					codCateg = (String)registro.nextElement();
					codCateg = (codCateg.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null  : codCateg.replace(Sesion.DEL_CAMPO, ' ').trim();
					separador = (codCateg != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					
					codDpto = (String)registro.nextElement();
					codDpto = (codDpto.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : codDpto.replace(Sesion.DEL_CAMPO, ' ').trim();
					separador = (codDpto != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					
					marca = (String)registro.nextElement();
					marca = (marca.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : marca.replace(Sesion.DEL_CAMPO, ' ').trim();
					separador = (marca != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					
					codLinea = (String)registro.nextElement();
					codLinea = (codLinea.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : codLinea.replace(Sesion.DEL_CAMPO, ' ').trim();
					separador = (codLinea != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					
					refProv = (String)registro.nextElement();
					refProv = (refProv.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : refProv.replace(Sesion.DEL_CAMPO, ' ').trim();
					separador = (refProv != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					
					montoMinimo = (String)registro.nextElement();
					montoMinimo = (montoMinimo.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : montoMinimo.replace(Sesion.DEL_CAMPO, ' ').trim();
					separador = (montoMinimo != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					
					cantidadMinima = (String)registro.nextElement();
					cantidadMinima = (cantidadMinima.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : cantidadMinima.replace(Sesion.DEL_CAMPO, ' ').trim();
					separador = (cantidadMinima != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					
					cantidadRegalada = (String)registro.nextElement();
					cantidadRegalada = (cantidadRegalada.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : cantidadRegalada.replace(Sesion.DEL_CAMPO, ' ').trim();
					separador = (cantidadRegalada != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					
					codproducto = (String)registro.nextElement();
					codproducto = (codproducto.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : codproducto.replace(Sesion.DEL_CAMPO, ' ').trim();
					separador = (codproducto != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					
					bsDescuento = (String)registro.nextElement();
					bsDescuento = (bsDescuento.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : bsDescuento.replace(Sesion.DEL_CAMPO, ' ').trim();
					separador = (bsDescuento != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					
					estadoRegistro = (String)registro.nextElement();
					estadoRegistro = (estadoRegistro.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : estadoRegistro.replace(Sesion.DEL_CAMPO, ' ').trim();
					separador = (estadoRegistro != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					
					nombrePromocion = (String)registro.nextElement();
					nombrePromocion = (nombrePromocion.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : nombrePromocion.replace(Sesion.DEL_CAMPO, ' ').trim();
					separador = (nombrePromocion != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					
					prodSinConsecutivo = (String)registro.nextElement();
					prodSinConsecutivo = (prodSinConsecutivo.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : prodSinConsecutivo.replace(Sesion.DEL_CAMPO, ' ').trim();
					separador = (prodSinConsecutivo != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					
					codSeccion = (String)registro.nextElement();
					codSeccion = (codSeccion.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : codSeccion.replace(Sesion.DEL_CAMPO, ' ').trim();
					separador = (codSeccion != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					
					acumulaPremio = (String)registro.nextToken();
					acumulaPremio = (codSeccion.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : acumulaPremio.replace(Sesion.DEL_CAMPO, ' ').trim();
					separador = (acumulaPremio != null) ? (String)registro.nextToken() : String.valueOf(Sesion.SEP_CAMPO);
					
					int pSinConsecutivo =  (prodSinConsecutivo.trim().equals("NULL")? 0 :Integer.parseInt(prodSinConsecutivo.trim()));
					DetallePromocionExt detallePromocionExt = new DetallePromocionExt(Integer.parseInt(codPromocion.trim())
							, Integer.parseInt(numDetalle.trim()),Double.parseDouble(porcentajeDescto.trim()), codCateg.trim()
							, codDpto.trim(), marca.trim(), codLinea.trim(), refProv.trim(), Double.parseDouble(montoMinimo.trim())
							, Integer.parseInt(cantidadMinima.trim()), Integer.parseInt(cantidadRegalada.trim()), codproducto.trim()
							, Double.parseDouble(bsDescuento.trim()), estadoRegistro.trim(), nombrePromocion.trim()
							, pSinConsecutivo, codSeccion.trim(), acumulaPremio.trim());
					Sesion.agregarDetallePromocionExt(detallePromocionExt);
				}catch(NoSuchElementException e){
					System.out.println("Numero Registro " + i);
					e.printStackTrace();
				}				
			}
			
			eliminarArchivoPromo(pathProducto);
			
		}
		catch(java.io.IOException io){io.printStackTrace();}
		catch (Exception ex){ex.printStackTrace();}
		finally{
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se eliminaron variables sin uso
	* Fecha: agosto 2011
	*/
	public void cargarDonacion(String pathProducto){
		System.out.println(pathProducto);
		String s;
		BufferedReader in = null;
		StringTokenizer registro = null;
		String separador, codDonacion, codBarraProdDonacion, fechaInicio, fechaFinaliza, nombreDonacion, descDonacion, tipoDonacion
		, estadoDonacion, cantidadPropuesta, regactualizado, mostrarAlTotalizar;
		try {
			/* Archivo de Entrada */
			FileInputStream farchivo = new FileInputStream(pathProducto);
			in = new BufferedReader(new InputStreamReader(farchivo));
		} catch (Exception e) {
			//NO SE ENCONTRÖ EL ARCHIVO ESPECIFICADO
			e.printStackTrace();
		}
		try {
			System.out.print("\n ***** Parseando Donacion***** \n");
			int i = 0;
			while((s = in.readLine()) != null){
				try{					
					registro = new StringTokenizer(s,String.valueOf(Sesion.SEP_CAMPO), true);
					
					codDonacion  = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador  = (String)registro.nextElement();
					
					codBarraProdDonacion  = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					codBarraProdDonacion = (codBarraProdDonacion.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : codBarraProdDonacion.replace(Sesion.DEL_CAMPO, ' ').trim();
					separador  = (String)registro.nextElement();
					
					fechaInicio = (String)registro.nextElement();
					fechaInicio = (fechaInicio.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : fechaInicio.replace(Sesion.DEL_CAMPO, ' ');
					separador = (fechaInicio != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					
					fechaFinaliza = (String)registro.nextElement();
					fechaFinaliza = (fechaFinaliza.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null :  fechaFinaliza.replace(Sesion.DEL_CAMPO, ' ');
					separador = (fechaFinaliza != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					
					nombreDonacion = (String)registro.nextElement();
					nombreDonacion = (nombreDonacion.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : nombreDonacion.replace(Sesion.DEL_CAMPO, ' ').trim();
					separador = (nombreDonacion != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					
					descDonacion = (String)registro.nextElement();
					descDonacion = (descDonacion.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : descDonacion.replace(Sesion.DEL_CAMPO, ' ').trim();
					separador = (descDonacion != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					
					tipoDonacion = (String)registro.nextElement();
					tipoDonacion = (tipoDonacion.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : tipoDonacion.replace(Sesion.DEL_CAMPO, ' ').trim();
					separador = (tipoDonacion != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					
					estadoDonacion = (String)registro.nextElement();
					estadoDonacion = (estadoDonacion.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : estadoDonacion.replace(Sesion.DEL_CAMPO, ' ').trim();
					separador = (estadoDonacion != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					
					cantidadPropuesta = (String)registro.nextElement();
					cantidadPropuesta = (cantidadPropuesta.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : cantidadPropuesta.replace(Sesion.DEL_CAMPO, ' ').trim();
					separador = (cantidadPropuesta != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					
					regactualizado = (String)registro.nextElement();
					regactualizado = (regactualizado.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : regactualizado.replace(Sesion.DEL_CAMPO, ' ').trim();
					separador = (regactualizado != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					
					mostrarAlTotalizar = (String)registro.nextElement();
					mostrarAlTotalizar = (mostrarAlTotalizar.equals(String.valueOf(Sesion.SEP_CAMPO))) ? null : mostrarAlTotalizar.replace(Sesion.DEL_CAMPO, ' ').trim();
					separador = (mostrarAlTotalizar != null) ? (String)registro.nextElement() : String.valueOf(Sesion.SEP_CAMPO);
					
					Donacion donacion = new Donacion(Integer.parseInt(codDonacion.trim()), codBarraProdDonacion.trim()
							, fechaInicio.trim(), fechaFinaliza.trim(), nombreDonacion.trim(), descDonacion.trim()
							,  Integer.parseInt(tipoDonacion.trim()), Integer.parseInt(estadoDonacion.trim())
							, Double.parseDouble(cantidadPropuesta.trim()), regactualizado.trim(), mostrarAlTotalizar.trim());
					Sesion.agregarDonacion(donacion);
				}catch(NoSuchElementException e){
					System.out.println("Numero Registro " + i);
					e.printStackTrace();
				}				
			}
			in.close();
		}
		catch(java.io.IOException io){io.printStackTrace();}
		catch (Exception ex){ex.printStackTrace();}
	
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se eliminaron variables sin uso
	* Fecha: agosto 2011
	*/
	public void cargarTransaccionPremControl(String pathProducto){
		System.out.println(pathProducto);
		String s;
		BufferedReader in = null;
		StringTokenizer registro = null;
		String separador, codPromocion, nroTransacciones, nroTransaccionesXDia,MaxPremio,MaxPremioXDia, regactualizado;
		try {
			/* Archivo de Entrada */
			FileInputStream farchivo = new FileInputStream(pathProducto);
			in = new BufferedReader(new InputStreamReader(farchivo));
		} catch (Exception e) {
			//NO SE ENCONTRÖ EL ARCHIVO ESPECIFICADO
			e.printStackTrace();
		}
		try {
			System.out.print("\n ***** Parseando Transaccion Premiada Control***** \n");
			int i = 0;
			while((s = in.readLine()) != null){
				try{					
					registro = new StringTokenizer(s,String.valueOf(Sesion.SEP_CAMPO), true);	
					
					codPromocion  = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador  = (String)registro.nextElement();
					
					nroTransacciones  = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador  = (String)registro.nextElement();
					
					nroTransaccionesXDia  = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador  = (String)registro.nextElement();
					
					MaxPremio  = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador  = (String)registro.nextElement();
					
					MaxPremioXDia  = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador  = (String)registro.nextElement();
					
					regactualizado  = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador  = (String)registro.nextElement();
					
					TransaccionPremControl transaccionPremControl = new TransaccionPremControl(Integer.parseInt(codPromocion.trim())
							, Integer.parseInt(nroTransacciones.trim()), Integer.parseInt(nroTransaccionesXDia.trim())
							, Double.parseDouble(MaxPremio.trim()), Double.parseDouble(MaxPremioXDia.trim()), regactualizado.trim());
					Sesion.agregarTransaccionPremControl(transaccionPremControl);
				}catch(NoSuchElementException e){
					System.out.println("Numero Registro " + i);
					e.printStackTrace();
				}				
			}
			in.close();
		}
		catch(java.io.IOException io){io.printStackTrace();}
		catch (Exception ex){ex.printStackTrace();}
	
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se eliminaron variables sin uso
	* Fecha: agosto 2011
	*/
	public void cargarCondicionPromocion(String pathProducto){
		System.out.println(pathProducto);
		String s;
		BufferedReader in = null;
		StringTokenizer registro = null;
		String separador, codPromocion, orden, lineaCondicion, regactualizado;
		try {
			/* Archivo de Entrada */
			FileInputStream farchivo = new FileInputStream(pathProducto);
			in = new BufferedReader(new InputStreamReader(farchivo));
		} catch (Exception e) {
			//NO SE ENCONTRÖ EL ARCHIVO ESPECIFICADO
			e.printStackTrace();
		}
		try {
			System.out.print("\n ***** Parseando Condicion Promocion***** \n");
			int i = 0;
			while((s = in.readLine()) != null){
				try{
					registro = new StringTokenizer(s,String.valueOf(Sesion.SEP_CAMPO), true);
					
					codPromocion  = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador  = (String)registro.nextElement();
					
					lineaCondicion  = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador  = (String)registro.nextElement();
					
					orden  = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador  = (String)registro.nextElement();
					
					regactualizado  = ((String)registro.nextElement()).replace(Sesion.DEL_CAMPO, ' ');
					separador  = (String)registro.nextElement();
					
					CondicionPromocion condicionPromocion = new CondicionPromocion(Integer.parseInt(codPromocion.trim())
							, Integer.parseInt(lineaCondicion.trim()), Integer.parseInt(orden.trim()), regactualizado.trim());
					Sesion.agregarCondicionPromocion(condicionPromocion);
				}catch(NoSuchElementException e){
					System.out.println("Numero Registro " + i);
					e.printStackTrace();
				}				
			}
			in.close();
		}
		catch(java.io.IOException io){io.printStackTrace();}
		catch (Exception ex){ex.printStackTrace();}
	}


	public void eliminarArchivoPromo(String path)
	{
		/* Archivo de Entrada */
		   File archivo = new File(path);
			if(archivo.exists()){
				archivo.delete();
			}
	}
	
}
