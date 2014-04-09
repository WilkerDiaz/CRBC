/**
 * =============================================================================
 * Proyecto   : ColasCR
 * Paquete    : com.beco.colascr.servicios.mediadorbd
 * Programa   : MediadorBD.java
 * Creado por : rabreu
 * Creado en  : 01/11/2006
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 
 * Fecha       : 
 * Analista    : 
 * Descripción : 
 * =============================================================================
 */

package com.beco.colascr.servicios.mediadorbd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import net.n3.nanoxml.IXMLElement;
import net.n3.nanoxml.XMLElement;

import com.beco.colascr.servicios.Sesion;
import com.beco.colascr.servicios.excepciones.BaseDeDatosExcepcion;
import com.beco.colascr.servicios.excepciones.ConexionExcepcion;

public class MediadorBD {

	/**
	 * @param codLista
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public static IXMLElement obtenerLista(String codLista) throws ConexionExcepcion, BaseDeDatosExcepcion {
		String consulta = "";
		ResultSet result = null;
		IXMLElement respuesta = new XMLElement("respuesta");
		respuesta.setAttribute("tipo","listaregalos");
				
		consulta = "select * from listaregalos where codlista = "+codLista+" and estado = 'A'";
		
		result = Conexiones.realizarConsulta(consulta, true);
				
		try {
			IXMLElement cabecera = respuesta.createElement("cabecera");
			respuesta.addChild(cabecera);
			result.beforeFirst();
			if(result.first()){
				IXMLElement detalle;
				
				detalle = cabecera.createElement("codlista");
				cabecera.addChild(detalle);
				detalle.setContent(result.getString("codlista"));
						
				detalle = cabecera.createElement("tipolista");
				cabecera.addChild(detalle);
				detalle.setContent(result.getString("tipolista"));
						
				detalle = cabecera.createElement("codtitular");
				cabecera.addChild(detalle);
				detalle.setContent(result.getString("codtitular"));
						
				detalle = cabecera.createElement("fechaevento");
				cabecera.addChild(detalle);
				detalle.setContent(String.valueOf(result.getDate("fechaevento")));
						
				detalle = cabecera.createElement("tipoevento");
				cabecera.addChild(detalle);
				detalle.setContent(result.getString("tipoevento"));
						
				detalle = cabecera.createElement("titularsec");
				cabecera.addChild(detalle);
				detalle.setContent(result.getString("titularsec"));
		
				detalle = cabecera.createElement("fechaapertura");
				cabecera.addChild(detalle);
				detalle.setContent(result.getString("fechaapertura"));
		
				detalle = cabecera.createElement("numtiendaapertura");
				cabecera.addChild(detalle);
				detalle.setContent(String.valueOf(result.getInt("numtiendaapertura")));
		
				detalle = cabecera.createElement("numcajaapertura");
				cabecera.addChild(detalle);
				detalle.setContent(String.valueOf(result.getInt("numcajaapertura")));
		
				detalle = cabecera.createElement("codcajeroapertura");
				cabecera.addChild(detalle);
				detalle.setContent(result.getString("codcajeroapertura"));
		
				detalle = cabecera.createElement("fechacierre");
				cabecera.addChild(detalle);
				detalle.setContent(String.valueOf(result.getDate("fechacierre")));
		
				detalle = cabecera.createElement("numtiendacierre");
				cabecera.addChild(detalle);
				detalle.setContent(String.valueOf(result.getInt("numtiendacierre")));
		
				detalle = cabecera.createElement("numcajacierre");
				cabecera.addChild(detalle);
				detalle.setContent(String.valueOf(result.getInt("numcajacierre")));
		
				detalle = cabecera.createElement("codcajerocierre");
				cabecera.addChild(detalle);
				detalle.setContent(result.getString("codcajerocierre"));
		
				detalle = cabecera.createElement("montobase");
				cabecera.addChild(detalle);
				detalle.setContent(String.valueOf(result.getDouble("montobase")));
		
				detalle = cabecera.createElement("montoimpuesto");
				cabecera.addChild(detalle);
				detalle.setContent(String.valueOf(result.getDouble("montoimpuesto")));
		
				detalle = cabecera.createElement("cantproductos");
				cabecera.addChild(detalle);
				detalle.setContent(String.valueOf(result.getInt("cantproductos")));
		
				detalle = cabecera.createElement("montoabonoslista");
				cabecera.addChild(detalle);
				detalle.setContent(String.valueOf(result.getDouble("montoabonos")));
				
				detalle = cabecera.createElement("notificaciones");
				cabecera.addChild(detalle);
				detalle.setContent(String.valueOf(result.getString("notificaciones")));
				
				detalle = cabecera.createElement("permitirventa");
				cabecera.addChild(detalle);
				detalle.setContent(String.valueOf(result.getString("permitirventa")));
			} else
				throw new BaseDeDatosExcepcion("No existe la lista de regalos "+codLista);
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		consulta = "select * from detallelistaregalos where codlista = '"+codLista+"' and cantidad>0";
		Vector<Vector<Object>> detallesVector = new Vector<Vector<Object>>();
		
		result = Conexiones.realizarConsulta(consulta,true);
		try {
			result.beforeFirst();
			while(result.next()){
				Vector<Object> detalle = new Vector<Object>(13);
				String codlista = result.getString("codlista");
				detalle.addElement(codlista);
				String codproducto = result.getString("codproducto");
				detalle.addElement(codproducto);
				//detalle.addElement(result.getString("descproducto"));
				detalle.addElement(new Integer(result.getInt("correlativoitem")));
				detalle.addElement(new Integer(result.getInt("cantidad")));
				detalle.addElement(new Double(result.getDouble("precioregular")));
				detalle.addElement(new Double(result.getDouble("preciofinal")));
				detalle.addElement(new Double(result.getDouble("montoimpuesto")));
				detalle.addElement(result.getString("codtipocaptura"));
				detalle.addElement(new Integer(result.getInt("codpromocion")));
				detalle.addElement(new Integer(result.getInt("cantcomprado")));

				String query = "select sum(op.cantidad) as cantcancelados from operacionlistaregalos op where " +
							"op.tipooperacion = 'T' and op.codlista = "+codlista+" and op.codproducto = '"+codproducto+"'";
				Integer cantcancelados = new Integer (0);
				try {
					cantcancelados = new Integer((Conexiones.realizarConsulta(query,true)).getInt("cantcancelados"));
				} catch (Exception e) {}			
				detalle.addElement(cantcancelados);
				
				query = "select sum(montobase) as montoabonos from operacionlistaregalos where " +
							"tipooperacion = 'A' and codlista = '"+codlista+"' and codproducto = '"+codproducto+"'";
				Double montoabonos = new Double(0);
				try {
					montoabonos = new Double((Conexiones.realizarConsulta(query,true)).getDouble("montoabonos"));
				} catch (Exception e) {}
				detalle.addElement(montoabonos);
				detallesVector.addElement(detalle);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {result.close();}catch (Exception e){}
		}
	
		// Obtenemos los detalles producto de Ventas con consecutivos de precio distintos y los agregamos a los detalles actuales
		obtenerDetallesNoPedidos(codLista, detallesVector);

		try {
			IXMLElement detalles = respuesta.createElement("detalles");
			respuesta.addChild(detalles);
		
			for (int i=0; i<detallesVector.size(); i++){
				Vector<Object> detalleActual =  detallesVector.elementAt(i);
				IXMLElement producto = detalles.createElement("producto");
				detalles.addChild(producto);
				
				IXMLElement detalle;
						
				detalle = detalles.createElement("codproducto");
				producto.addChild(detalle);
				String codProducto = (String) detalleActual.elementAt(1);
				detalle.setContent(codProducto);
						
				detalle = detalles.createElement("correlativoitem");
				producto.addChild(detalle);
				detalle.setContent(String.valueOf(detalleActual.elementAt(2)));
						
				detalle = detalles.createElement("cantidad");
				producto.addChild(detalle);
				detalle.setContent(String.valueOf(detalleActual.elementAt(3)));
						
				detalle = detalles.createElement("precioregular");
				producto.addChild(detalle);
				detalle.setContent(String.valueOf(detalleActual.elementAt(4)));
						
				detalle = detalles.createElement("preciofinal");
				producto.addChild(detalle);
				detalle.setContent(String.valueOf(detalleActual.elementAt(5)));
						
				detalle = detalles.createElement("montoimpuesto");
				producto.addChild(detalle);
				detalle.setContent(String.valueOf(detalleActual.elementAt(6)));
						
				detalle = detalles.createElement("codtipocaptura");
				producto.addChild(detalle);
				detalle.setContent(String.valueOf(detalleActual.elementAt(7)));
						
				detalle = detalles.createElement("codpromocion");
				producto.addChild(detalle);
				detalle.setContent(String.valueOf(detalleActual.elementAt(8)));
						
				detalle = detalles.createElement("cantcomprado");
				producto.addChild(detalle);
				detalle.setContent(String.valueOf(detalleActual.elementAt(9)));

				detalle = detalles.createElement("cantcancelados");
				producto.addChild(detalle);
				detalle.setContent(String.valueOf(detalleActual.elementAt(10)));
				
				detalle = detalles.createElement("montoabonos");
				producto.addChild(detalle);
				detalle.setContent(String.valueOf(detalleActual.elementAt(11)));
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		IXMLElement operaciones = respuesta.createElement("operaciones");
	//	Primero extraigo las operaciones de abono a la lista
		consulta = "SELECT op.numoperacion, op.numtransaccion, op.codproducto, op.montobase, op.montoimpuesto, op.nomcliente, op.fecha, op.dedicatoria, "
						+ "op.numtienda, op.tipooperacion, op.codcajero FROM operacionlistaregalos AS op WHERE op.codlista = '"+codLista+"' AND "
						+ "op.codproducto = '000000000000' and op.tipooperacion = 'L'";

		result = Conexiones.realizarConsulta(consulta,true);
		
		respuesta.addChild(operaciones);
				
		try {
			result.beforeFirst();
			while(result.next()){
				IXMLElement operacion = operaciones.createElement("operacion");
				operaciones.addChild(operacion);
				
				IXMLElement detalle;
				
				detalle = operaciones.createElement("numoperacion");
				operacion.addChild(detalle);
				detalle.setContent(result.getString("numoperacion"));
				
				detalle = operaciones.createElement("numtransaccion");
				operacion.addChild(detalle);
				detalle.setContent(result.getString("numtransaccion"));
						
				detalle = operaciones.createElement("codproducto");
				operacion.addChild(detalle);
				detalle.setContent(result.getString("codproducto"));
						
				detalle = operaciones.createElement("descripcionlarga");
				operacion.addChild(detalle);
				detalle.setContent("ABONO A LISTA");	
						
				detalle = operaciones.createElement("nomcliente");
				operacion.addChild(detalle);
				detalle.setContent(result.getString("nomcliente"));
						
				detalle = operaciones.createElement("montobase");
				operacion.addChild(detalle);
				detalle.setContent(String.valueOf(result.getDouble("montobase")));	

				detalle = operaciones.createElement("montoimpuesto");
				operacion.addChild(detalle);
				detalle.setContent(String.valueOf(result.getDouble("montoimpuesto")));
									
				detalle = operaciones.createElement("cantidad");
				operacion.addChild(detalle);
				detalle.setContent(String.valueOf(1));
						
				detalle = operaciones.createElement("fecha");
				operacion.addChild(detalle);
				detalle.setContent(String.valueOf(result.getDate("fecha")));
						
				detalle = operaciones.createElement("dedicatoria");
				operacion.addChild(detalle);
				detalle.setContent(result.getString("dedicatoria"));
								
				detalle = operaciones.createElement("tipooperacion");
				operacion.addChild(detalle);
				detalle.setContent(result.getString("tipooperacion"));
				
				detalle = operaciones.createElement("numtienda");
				operacion.addChild(detalle);
				detalle.setContent(String.valueOf(result.getInt("numtienda")));
				
				detalle = operaciones.createElement("codcajero");
				operacion.addChild(detalle);
				detalle.setContent(String.valueOf(result.getInt("codcajero")));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}	
				
		try {
		//	Luego extraigo las operaciones de compras y abonos a productos
			consulta = "SELECT op.numoperacion,op.numtransaccion,op.codproducto, prod.descripcionlarga, op.nomcliente, op.montobase, op.montoimpuesto, op.cantidad, op.fecha, op.dedicatoria, " 
						+ "op.tipooperacion, op.numtienda, op.codcajero FROM operacionlistaregalos AS op INNER JOIN producto AS prod ON (op.codproducto = prod.codproducto) " 
						+ "WHERE op.codlista = '"+codLista+"'";

			result = Conexiones.realizarConsulta(consulta,true);
			
			result.beforeFirst();
			while(result.next()){
				IXMLElement operacion = respuesta.createElement("operacion");
				operaciones.addChild(operacion);
				
				IXMLElement detalle;
				
				detalle = operaciones.createElement("numoperacion");
				operacion.addChild(detalle);
				detalle.setContent(result.getString("numoperacion"));

				detalle = operaciones.createElement("numtransaccion");
				operacion.addChild(detalle);
				detalle.setContent(result.getString("numtransaccion"));
					
				detalle = operaciones.createElement("codproducto");
				operacion.addChild(detalle);
				detalle.setContent(result.getString("codproducto"));
						
				detalle = operaciones.createElement("descripcionlarga");
				operacion.addChild(detalle);
				detalle.setContent(result.getString("descripcionlarga"));	
										
				detalle = operaciones.createElement("nomcliente");
				operacion.addChild(detalle);
				detalle.setContent(result.getString("nomcliente"));
										
				detalle = operaciones.createElement("montobase");
				operacion.addChild(detalle);
				detalle.setContent(String.valueOf(result.getDouble("montobase")));

				detalle = operaciones.createElement("montoimpuesto");
				operacion.addChild(detalle);
				detalle.setContent(String.valueOf(result.getDouble("montoimpuesto")));
								
				detalle = operaciones.createElement("cantidad");
				operacion.addChild(detalle);
				detalle.setContent(String.valueOf(result.getInt("cantidad")));

				detalle = operaciones.createElement("fecha");
				operacion.addChild(detalle);
				detalle.setContent(String.valueOf(result.getDate("fecha")));
						
				detalle = operaciones.createElement("dedicatoria");
				operacion.addChild(detalle);
				detalle.setContent(result.getString("dedicatoria"));
											
				detalle = operaciones.createElement("tipooperacion");
				operacion.addChild(detalle);
				detalle.setContent(result.getString("tipooperacion"));	

				detalle = operaciones.createElement("numtienda");
				operacion.addChild(detalle);
				detalle.setContent(String.valueOf(result.getInt("numtienda")));

				detalle = operaciones.createElement("codcajero");
				operacion.addChild(detalle);
				detalle.setContent(String.valueOf(result.getInt("codcajero")));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		Conexiones.cerrarConexiones();
		
		return respuesta;
	}

	/**
	 * Obtiene los productos vendidos con distinto consecutivo al solicitado
	 * 
	 * @param numLista
	 * @param detallesOriginales
	 * @return
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public static void obtenerDetallesNoPedidos(String numLista, Vector<Vector<Object>> detallesOriginales) {
		String sentenciaSQL = "select * from detallelistaregalos where detallelistaregalos.codlista = '"+numLista+"' and cantidad=0";
		ResultSet result = null;
		
		try {
			result = Conexiones.realizarConsulta(sentenciaSQL,true);
			result.beforeFirst();
			while(result.next()){
				String codProducto = result.getString("codproducto");
				int compradosConsec = result.getInt("cantcomprado");
				
				// Obtener posición(es) del Vector donde se encuentra(n) el/los producto(s) similar(es) con otro consecutivo
				Vector<Integer> posiciones = obtenerRenglones(codProducto, detallesOriginales);
				
				int i=0;
				while ((compradosConsec>0) && (i<posiciones.size())) {
					Vector<Object> detalleActual = (Vector<Object>) detallesOriginales.elementAt((posiciones.elementAt(i)).intValue());
					//String producto = (String)detalleActual.elementAt(1);
					int pedidos = ((Integer)detalleActual.elementAt(3)).intValue();
					int vendidos = ((Integer)detalleActual.elementAt(9)).intValue();
					int restantes = pedidos - vendidos;
					if (restantes > 0) {
						if (compradosConsec <= restantes) {
							detalleActual.set(9, new Integer(vendidos + compradosConsec));
							compradosConsec = 0;
						} else {
							detalleActual.set(9, new Integer(vendidos + restantes));
							compradosConsec -= restantes;
						}
					}
					i++;
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try{result.close();}catch (Exception e) {}
		}
	}

	/**
	 * Obtiene los renglones de los productos originales
	 * 
	 * @param numLista
	 * @param detallesOriginales
	 * @return
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	public static Vector<Integer> obtenerRenglones(String codProducto,
			Vector<Vector<Object>> detallesOriginales) {
		Vector<Integer> resultado = new Vector<Integer>();
		for (int i=0; i<detallesOriginales.size(); i++) {
			Vector<Object> detalleActual = (Vector<Object>) detallesOriginales.elementAt(i);
			String producto = (String)detalleActual.elementAt(1);
			if (producto.substring(0, 9).equals(codProducto.substring(0, 9))) {
				resultado.add(new Integer(i));
			}
		}
		
		return resultado;
	}
		
	/**
	 * @param codafiliado
	 * @return
	 */
	public static IXMLElement obtenerAfiliado(String codAfiliado) throws ConexionExcepcion, BaseDeDatosExcepcion {
		String consulta = "";
		ResultSet result = null;
		IXMLElement respuesta = new XMLElement("respuesta");
		respuesta.setAttribute("tipo","afiliado");
		
		consulta = "select codafiliado,tipoafiliado,nombre,apellido,numtienda," +
						"numficha,coddepartamento,codcargo,nitcliente,direccion,direccionfiscal,email,codarea," +
						"numtelefono,codarea1,numtelefono1,fechaafiliacion,exentoimpuesto,registrado, contactar," +
						"codregion,estadoafiliado,estadocolaborador from afiliado where codafiliado='"+codAfiliado+"'";
		
		result = Conexiones.realizarConsulta(consulta, true);
		
		try {
			result.beforeFirst();
			if(result.first()){
				IXMLElement detalle;
				
				detalle = new XMLElement("codafiliado");
				respuesta.addChild(detalle);
				detalle.setContent(result.getString("codafiliado"));
				
				detalle = new XMLElement("tipoafiliado");
				respuesta.addChild(detalle);
				detalle.setContent(result.getString("tipoafiliado"));

				detalle = new XMLElement("nombre");
				respuesta.addChild(detalle);
				detalle.setContent(result.getString("nombre"));
				
				detalle = new XMLElement("apellido");
				respuesta.addChild(detalle);
				detalle.setContent(result.getString("apellido"));

				detalle = new XMLElement("numtienda");
				respuesta.addChild(detalle);
				detalle.setContent(result.getString("numtienda"));

				detalle = new XMLElement("numficha");
				respuesta.addChild(detalle);
				detalle.setContent(result.getString("numficha"));

				detalle = new XMLElement("coddepartamento");
				respuesta.addChild(detalle);
				detalle.setContent(result.getString("coddepartamento"));
				
				detalle = new XMLElement("codcargo");
				respuesta.addChild(detalle);
				detalle.setContent(result.getString("codcargo"));

				detalle = new XMLElement("nitcliente");
				respuesta.addChild(detalle);
				detalle.setContent(result.getString("nitcliente"));
				
				detalle = new XMLElement("direccion");
				respuesta.addChild(detalle);
				detalle.setContent(result.getString("direccion"));

				detalle = new XMLElement("direccionfiscal");
				respuesta.addChild(detalle);
				detalle.setContent(result.getString("direccionfiscal"));
	
				detalle = new XMLElement("email");
				respuesta.addChild(detalle);
				detalle.setContent(result.getString("email"));

				detalle = new XMLElement("codarea");
				respuesta.addChild(detalle);
				detalle.setContent(result.getString("codarea"));
				
				detalle = new XMLElement("numtelefono");
				respuesta.addChild(detalle);
				detalle.setContent(result.getString("numtelefono"));

				detalle = new XMLElement("codarea1");
				respuesta.addChild(detalle);
				detalle.setContent(result.getString("codarea1"));
				
				detalle = new XMLElement("numtelefono1");
				respuesta.addChild(detalle);
				detalle.setContent(result.getString("numtelefono1"));

				detalle = new XMLElement("fechaafiliacion");
				respuesta.addChild(detalle);
				detalle.setContent(result.getString("fechaafiliacion"));
				
				detalle = new XMLElement("exentoimpuesto");
				respuesta.addChild(detalle);
				detalle.setContent(result.getString("exentoimpuesto"));
				
				detalle = new XMLElement("registrado");
				respuesta.addChild(detalle);
				detalle.setContent(result.getString("registrado"));
				
				detalle = new XMLElement("contactar");
				respuesta.addChild(detalle);
				detalle.setContent(result.getString("contactar"));
				
				detalle = new XMLElement("codregion");
				respuesta.addChild(detalle);
				detalle.setContent(result.getString("codregion"));
				
				detalle = new XMLElement("estadoafiliado");
				respuesta.addChild(detalle);
				detalle.setContent(result.getString("estadoafiliado"));

				detalle = new XMLElement("estadocolaborador");
				respuesta.addChild(detalle);
				detalle.setContent(result.getString("estadocolaborador"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Conexiones.cerrarConexiones();

		return respuesta;
	}

	/**
	 * @param solicitud
	 */
	public static void modificarEncabezadoLista(IXMLElement solicitud) throws BaseDeDatosExcepcion {
		String sentenciaSQL = "";
		ResultSet resultado = null;
		Statement loteSentencias = null;
		int numTransaccion = 0, correlativoitem = 0;
		IXMLElement cabecera = solicitud.getFirstChildNamed("cabecera");

		try {
			String codlista = cabecera.getFirstChildNamed("codlista").getContent();
			String fechaevento = cabecera.getFirstChildNamed("fechaevento").getContent();
			String tipoevento = cabecera.getFirstChildNamed("tipoevento").getContent();
			String codtitular = cabecera.getFirstChildNamed("codtitular").getContent();
			String titular = cabecera.getFirstChildNamed("titular").getContent();
			String titularsec = cabecera.getFirstChildNamed("titularsec").getContent();
			String numtienda = cabecera.getFirstChildNamed("numtienda").getContent();
			String numcaja = cabecera.getFirstChildNamed("numcaja").getContent();
			String codcajero = cabecera.getFirstChildNamed("codcajero").getContent();
			String notificaciones = cabecera.getFirstChildNamed("notificaciones").getContent();
			String permitirventa = cabecera.getFirstChildNamed("permitirventa").getContent();
			String fecha = cabecera.getFirstChildNamed("fecha").getContent();

			loteSentencias = Conexiones.crearSentencia(true);

			sentenciaSQL = "select ifnull(max(numoperacion),0) as numoperacion, ifnull(max(correlativoitem),0) as correlativoitem from operacionlistaregalos "
							+ "where codlista = '" + codlista + "'";
					 				
			resultado = Conexiones.realizarConsulta(sentenciaSQL,true);
			if (resultado.first())
				try{
					numTransaccion = resultado.getInt("numoperacion") + 1;
					correlativoitem = resultado.getInt("correlativoitem") + 1;
				}catch (SQLException e){}
			
			String titularsectxt = (titularsec != null && titularsec.trim() != "")
								? "'"+titularsec+"'"
								: "null";

			sentenciaSQL = "update CR.listaregalos set "
						+ "fechaevento = '" + fechaevento + "', "
						+ "tipoevento = '" + tipoevento + "', "
						+ "titularsec = " + titularsectxt + ", "
						+ "notificaciones = " + notificaciones + ", "
						+ "permitirventa = " + permitirventa + " "
						+ "where codlista = " + codlista;
			loteSentencias.addBatch(sentenciaSQL);

			sentenciaSQL = "insert into CR.operacionlistaregalos(numoperacion, codcliente, nomcliente, "
				+ "codlista, fecha, tipooperacion, codproducto, montobase, cantidad, "
				+ "numtienda, numcaja, codcajero, correlativoitem) values ("
				+ numTransaccion + ", "
				+ "'" + codtitular + "', "
				+ "'" + titular + "', "
				+ codlista + ", "
				+ "'" + fecha + "', "
				+ "'" + "M" + "', "
				+ "'" + "', "
				+ "0" + ", "
				+ "1.0" + ", "
				+ numtienda + ", "
				+ numcaja + ", "
				+ "'" + codcajero + "', "
				+  correlativoitem  + ")";
					
			loteSentencias.addBatch(sentenciaSQL);
					
			Conexiones.ejecutarLoteSentencias(loteSentencias, true, true);
			
			// Actualizamos la lista en el servidor central
			sentenciaSQL = "update CR.listaregaloscentral set "
							+ "titular = '" + titular + "', "
							+ "titularsec = " + titularsectxt + ", "
							+ "tipoevento = '" + tipoevento + "', "
							+ "fechaevento = '" + fechaevento + "' "
							+ "where codlista = " + codlista;

			Conexiones.realizarSentencia(sentenciaSQL,false);
		} catch (SQLException e) {
			throw (new BaseDeDatosExcepcion("Error en sentencia SQL al guardar encabezado de la lista de regalos"));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de conexión con BD al guardar encabezado de la lista de regalos"));
		} finally {
			if (loteSentencias != null) {
				try {
					loteSentencias.close();
				} catch (SQLException e1) {
				}
				loteSentencias = null;
			}
			Conexiones.cerrarConexiones();
		}
	}
	
	/**
	 * @param solicitud
	 */
	@SuppressWarnings("unchecked")
	public static void modificarDetallesLista(IXMLElement solicitud) throws BaseDeDatosExcepcion {
		String sentenciaSQL = "";
		ResultSet resultado = null;
		Statement loteSentencias = null;
		int numTransaccion = 0, correlativoitemOLR = 0;
		IXMLElement cabecera = solicitud.getFirstChildNamed("cabecera");

		try {
			String codlista = cabecera.getFirstChildNamed("codlista").getContent();
			String codtitular = cabecera.getFirstChildNamed("codtitular").getContent();
			String montobase = cabecera.getFirstChildNamed("montobase").getContent();
			String montoimpuesto = cabecera.getFirstChildNamed("montoimpuesto").getContent();
			String cantproductos = cabecera.getFirstChildNamed("cantproductos").getContent();
			String montoabonos = cabecera.getFirstChildNamed("montoabonos").getContent();
			String numtienda = cabecera.getFirstChildNamed("numtienda").getContent();
			String numcaja = cabecera.getFirstChildNamed("numcaja").getContent();
			String codcajero = cabecera.getFirstChildNamed("codcajero").getContent();
			String fecha = cabecera.getFirstChildNamed("fecha").getContent();

			loteSentencias = Conexiones.crearSentencia(true);

			sentenciaSQL = "select ifnull(max(numoperacion),0) as numoperacion, ifnull(max(correlativoitem),0) as correlativoitem from operacionlistaregalos "
							+ "where codlista = '" + codlista + "'";
					 				
			resultado = Conexiones.realizarConsulta(sentenciaSQL,true);
			try{
					numTransaccion = resultado.getInt("numoperacion") + 1;
					correlativoitemOLR = resultado.getInt("correlativoitem") + 1;
				}catch (SQLException e){}

			sentenciaSQL = "update CR.listaregalos set "
						+ "montobase = " + montobase + ", "
						+ "montoimpuesto = " + montoimpuesto + ", "
						+ "cantproductos = " + cantproductos + ", "
						+ "montoabonos = " + montoabonos + " "
						+ "where codlista = " + codlista;
			loteSentencias.addBatch(sentenciaSQL);

			sentenciaSQL = "delete from CR.detallelistaregalos where codlista = " + codlista;
			loteSentencias.addBatch(sentenciaSQL);
		
			// Se extraen los detalles
			IXMLElement detalles = solicitud.getFirstChildNamed("detalles");
			Enumeration<IXMLElement> enumer = detalles.enumerateChildren();
			while(enumer.hasMoreElements()){
				Enumeration<IXMLElement> enumdetalle = ((IXMLElement)enumer.nextElement()).enumerateChildren();

				String codproducto = ((IXMLElement)enumdetalle.nextElement()).getContent();
				String correlativoitem = ((IXMLElement)enumdetalle.nextElement()).getContent();
				String cantidad = ((IXMLElement)enumdetalle.nextElement()).getContent();
				String precioregular = ((IXMLElement)enumdetalle.nextElement()).getContent();
				String preciofinal = ((IXMLElement)enumdetalle.nextElement()).getContent();
				String impuesto = ((IXMLElement)enumdetalle.nextElement()).getContent();
				String codtipocaptura = ((IXMLElement)enumdetalle.nextElement()).getContent();
				String codpromocion = ((IXMLElement)enumdetalle.nextElement()).getContent();
				String cantcomprado = ((IXMLElement)enumdetalle.nextElement()).getContent();
				String abonos = ((IXMLElement)enumdetalle.nextElement()).getContent();
				
				sentenciaSQL = "insert into CR.detallelistaregalos (codlista,codproducto,correlativoitem,cantidad," +
								"precioregular,preciofinal,montoimpuesto,codtipocaptura,codpromocion,cantcomprado,montoabonos) values ("
					+ codlista + ", " 
					+ "'" + codproducto + "', "
					+ correlativoitem + ", "
					+ cantidad + ", "
					+ precioregular + ", "
					+ preciofinal + ", "
					+ impuesto + ", "
					+ "'" + codtipocaptura + "', "
					+ codpromocion + ", "
					+ cantcomprado +", "
					+ abonos + ");";
				loteSentencias.addBatch(sentenciaSQL);
			}

			sentenciaSQL = "insert into CR.operacionlistaregalos(numoperacion,codcliente,codlista,"
				+ "fecha,tipooperacion,montobase,cantidad,numtienda,numcaja,codcajero,correlativoitem) values ( "
				+ numTransaccion + ", "
				+ "'" + codtitular + "', "
				+ codlista + ", "
				+ "'" + fecha + "', "
				+ "'" + "M" + "', "
				+ "0" + ", "
				+ "1.0" + ", "
				+ numtienda + ", "
				+ numcaja + ", "
				+ "'" + codcajero + "', "
				+  correlativoitemOLR + ")";
				
			loteSentencias.addBatch(sentenciaSQL);
				
			Conexiones.ejecutarLoteSentencias(loteSentencias, true, true);
		} catch (SQLException e) {
			throw (new BaseDeDatosExcepcion("Error en sentencia SQL al registrar la lista de regalos"));
		} catch (ConexionExcepcion e) {
			throw (new BaseDeDatosExcepcion("Error de conexión con BD al registrar la lista de regalos"));
		} finally {
			if (loteSentencias != null) {
				try {
					loteSentencias.close();
				} catch (SQLException e1) {
				}
				loteSentencias = null;
			}
			Conexiones.cerrarConexiones();
		}
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Enumeration' 
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unchecked")
	public static void registrarVentaLR(IXMLElement solicitud) throws ConexionExcepcion, BaseDeDatosExcepcion {
		String sentenciaSQL = "";
		ResultSet resultado = null;
		Statement loteSentencias = null;
		float cantComprado = 0;
		int numOperacion = 0;
		
		String numTransaccion = solicitud.getFirstChildNamed("numtransaccion").getContent();
		String codCliente = solicitud.getFirstChildNamed("codcliente").getContent();
		String nomCliente = solicitud.getFirstChildNamed("nomcliente").getContent();
		String codLista = solicitud.getFirstChildNamed("codlista").getContent();
		String numTienda = solicitud.getFirstChildNamed("numtienda").getContent();
		String numCaja = solicitud.getFirstChildNamed("numcaja").getContent();
		String codCajero = solicitud.getFirstChildNamed("codcajero").getContent();
		String dedicatoria = solicitud.getFirstChildNamed("dedicatoria").getContent();
		
		String fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
		
		try {
			loteSentencias = Conexiones.crearSentencia(true);

			sentenciaSQL = "select ifnull(max(numoperacion),0) as numoperacion from operacionlistaregalos where "
							+ "codlista = " + codLista;
					 				
			resultado = Conexiones.realizarConsulta(sentenciaSQL,true);
			if (resultado.first())
				try{
					numOperacion = (resultado.getInt("numoperacion")+1);
				}catch (SQLException e){
				}

			IXMLElement detallesVenta = solicitud.getFirstChildNamed("detallesventa");
			Enumeration<IXMLElement> detalles = detallesVenta.enumerateChildren();
			int correlativo = 1;
			while(detalles.hasMoreElements()){
				Enumeration<IXMLElement> detalle = ((IXMLElement)detalles.nextElement()).enumerateChildren();

				String codProd = ((IXMLElement)detalle.nextElement()).getContent();
				double montobase = Double.parseDouble(((IXMLElement)detalle.nextElement()).getContent());
				double montoimpuesto = Double.parseDouble(((IXMLElement)detalle.nextElement()).getContent());
				float cantidad =  Float.parseFloat(((IXMLElement)detalle.nextElement()).getContent());
				
				sentenciaSQL = "select cantcomprado from detallelistaregalos where codproducto = '"
							+ codProd + "' and codlista = '" + codLista + "'";
							
				resultado = Conexiones.realizarConsulta(sentenciaSQL,true);
				if (resultado.first())
					cantComprado = resultado.getInt("cantcomprado");
				
				cantComprado += cantidad;
				
				sentenciaSQL = "update detallelistaregalos set detallelistaregalos.cantcomprado = "
								+ cantComprado + " where codproducto = '" + codProd + "' and codlista = '"
								+ codLista + "'";

				loteSentencias.addBatch(sentenciaSQL);

				String codClientetxt = (codCliente != null)
									? "'"+codCliente+"'"
									: "null";
									
				String nomClientetxt = (nomCliente != null)
									? "'"+nomCliente+"'"
									: "null";
									
				String dedicatoriatxt = (dedicatoria != null)
									? "'"+dedicatoria+"'"
									: "null";

				sentenciaSQL = "insert into CR.operacionlistaregalos(numoperacion, numtransaccion, "
								+ "codcliente, nomcliente, codlista, fecha, tipooperacion, codproducto, "
								+ "montobase, montoimpuesto, cantidad, numtienda, numcaja, codcajero, dedicatoria, correlativoitem) values ("
								+ numOperacion + ", "
								+ numTransaccion + ", "
								+ codClientetxt + ", "
								+ nomClientetxt + ", "
								+ codLista + ", "
								+ "'" + fecha + "', "
								+ "'V'" + ", "
								+ "'" + codProd + "', "
								+ montobase + ", "
								+ montoimpuesto + ", "
								+ cantidad + ", "
								+ numTienda + ", "
								+ numCaja + ", "
								+ "'" + codCajero + "', "
								+ dedicatoriatxt + ", "
								+ correlativo + ")";
				loteSentencias.addBatch(sentenciaSQL);
				correlativo ++;
			}
			
			Conexiones.ejecutarLoteSentencias(loteSentencias, true, true);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally{
			Conexiones.cerrarConexiones();
		}
	}

	/**
	 * @param solicitud
	 */
	public static int registrarAbonoLR(IXMLElement solicitud) {
		ResultSet resultado = null;
		String sentenciaSQL = null;
		int numOperacion = 0,numTienda=0,numCaja=0;
		String numLista="",codCliente="",nomCliente="",codCajero="",fecha="",dedicatoria="";

		try {
			numLista = solicitud.getFirstChildNamed("codlista").getContent();
			codCliente = solicitud.getFirstChildNamed("codcliente").getContent();
			nomCliente = solicitud.getFirstChildNamed("nomcliente").getContent();
			numTienda = Integer.parseInt(solicitud.getFirstChildNamed("numtienda").getContent());
			numCaja = Integer.parseInt(solicitud.getFirstChildNamed("numcaja").getContent());
			codCajero = solicitud.getFirstChildNamed("codcajero").getContent();
			fecha = solicitud.getFirstChildNamed("fecha").getContent();
			dedicatoria = solicitud.getFirstChildNamed("dedicatoria").getContent();
			
			sentenciaSQL = "select ifnull(max(numoperacion),0) as numoperacion from operacionlistaregalos "
							+ "where codlista = '" + numLista + "'";
			
			resultado = Conexiones.realizarConsulta(sentenciaSQL,true);
			
			if (resultado.first())
				try{
					numOperacion = resultado.getInt("numoperacion") + 1;
				}catch (SQLException e){}
			
				// Registra el abono y verifica si se completó correctamente el registro.
				// Si el registro no está en la BD reintenta una vez el registro.
				registrarAbono(solicitud,numLista,numOperacion,codCliente,nomCliente,numTienda,numCaja,codCajero,fecha,dedicatoria);			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ConexionExcepcion e) {
			e.printStackTrace();
		} catch (BaseDeDatosExcepcion e) {
			e.printStackTrace();
		} finally {
			try {
				String sentenciaSQL2 = "select count(*) as cantoper from operacionlistaregalos where codlista="+numLista+" and numoperacion="+numOperacion;
				ResultSet resultado2 = Conexiones.realizarConsulta(sentenciaSQL2,true);

				int countOperaciones = resultado2.getInt("cantoper");
				if(countOperaciones == 0)
					registrarAbono(solicitud,numLista,numOperacion,codCliente,nomCliente,numTienda,numCaja,codCajero,fecha,dedicatoria);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ConexionExcepcion e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BaseDeDatosExcepcion e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Conexiones.cerrarConexiones();
		}
		return numOperacion;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Enumeration' 
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unchecked")
	private static void registrarAbono(IXMLElement solicitud,String numLista,int numOperacion,String codCliente,String nomCliente,
										int numTienda,int numCaja,String codCajero,String fecha,String dedicatoria) 
										throws ConexionExcepcion, BaseDeDatosExcepcion, SQLException {
		ResultSet resultado = null;
		String sentenciaSQL = null;
		double montoAbono = 0;
		Statement loteSentencias = Conexiones.crearSentencia(true);
		
		Enumeration<IXMLElement> detallesAbono = solicitud.getFirstChildNamed("detallesAbonos").enumerateChildren();
		int correlativoOLR = 1;
		while(detallesAbono.hasMoreElements()){
			IXMLElement detalle = (IXMLElement)detallesAbono.nextElement();
			String codProd = ((IXMLElement)detalle.getFirstChildNamed("codprod")).getContent();
			double montobase = Double.parseDouble(((IXMLElement)detalle.getFirstChildNamed("montoabono")).getContent());
			double montoimpuesto = Double.parseDouble(((IXMLElement)detalle.getFirstChildNamed("montoimpuesto")).getContent());
			String tipoabono = ((IXMLElement)detalle.getFirstChildNamed("tipoabono")).getContent();
			String cantidad = ((IXMLElement)detalle.getFirstChildNamed("cantidad")).getContent();
			
			if(codProd.equals("000000000000")){
				sentenciaSQL = "select montoabonos from CR.listaregalos where codlista = '" + numLista + "'";
	
				resultado = Conexiones.realizarConsulta(sentenciaSQL,true);												
	
				if (resultado.first())
					montoAbono = resultado.getDouble("montoabonos");
				montoAbono += (montobase + montoimpuesto) * Double.parseDouble(cantidad.trim());
	
				sentenciaSQL = "update CR.listaregalos set listaregalos.montoabonos = '"
								+ montoAbono + "' where codlista = '" + numLista + "'";
								
				loteSentencias.addBatch(sentenciaSQL);
	
			} else {
				sentenciaSQL = "select montoabonos from CR.detallelistaregalos where codproducto = '"
											+  codProd + "' and codlista = " + numLista;
				resultado = Conexiones.realizarConsulta(sentenciaSQL,true);												
	
				if (resultado.first())
					montoAbono = resultado.getDouble("montoabonos");
				montoAbono += (montobase + montoimpuesto) * Double.parseDouble(cantidad.trim());
											
				sentenciaSQL = "update CR.detallelistaregalos set detallelistaregalos.montoabonos = '"
								+ montoAbono + "' where codproducto = '" + codProd + "' and codlista = '"
								+ numLista + "'";
	
				loteSentencias.addBatch(sentenciaSQL);
			}
			
			String codClientetxt = (codCliente != null)
								? "'"+codCliente+"'"
								: "null";
								
			String nomClientetxt = (nomCliente != null)
								? "'"+nomCliente+"'"
								: "null";
								
			String dedicatoriatxt = (dedicatoria != null)
								? "'"+dedicatoria+"'"
								: "null";
	
			sentenciaSQL = "insert into CR.operacionlistaregalos(numoperacion, codcliente, nomcliente, "
				+ "codlista, fecha, tipooperacion, codproducto, montobase, montoimpuesto, cantidad, "
				+ "numtienda, numcaja, codcajero, dedicatoria, correlativoitem) values ("
				+ numOperacion + ", "
				+ codClientetxt + ", "
				+ nomClientetxt + ", "
				+ numLista + ", "
				+ "'" + fecha + "', "
				+ "'"+ tipoabono + "', "
				+ "'" + codProd + "', "
				+ montobase + ", "
				+ montoimpuesto + ", "
				+ cantidad + ", "
				+ numTienda + ", "
				+ numCaja + ", "
				+ "'" + codCajero + "'," 
				+ dedicatoriatxt + ", "
				+ correlativoOLR +")";
	
			loteSentencias.addBatch(sentenciaSQL);
			correlativoOLR ++;
		}

		Enumeration<IXMLElement> pagos = solicitud.getFirstChildNamed("pagos").enumerateChildren();

		while(pagos.hasMoreElements()){
			String codformadepago = ((IXMLElement)pagos.nextElement()).getContent();
			double monto = Double.parseDouble(((IXMLElement)pagos.nextElement()).getContent());
			double vuelto = Double.parseDouble(((IXMLElement)pagos.nextElement()).getContent());
			int correlativo = Integer.parseInt(((IXMLElement)pagos.nextElement()).getContent());
				
			sentenciaSQL = "insert into detalleoperacionlistaregalos (numoperacion,"
			+ "codlista,codformadepago,monto,montovuelto,correlativo) values ("
			+ numOperacion + ", "
			+ numLista + ", "
			+ "'" + codformadepago + "', " +
			+ monto + ", "
			+ vuelto + ", "
			+ correlativo + ")";
	
			loteSentencias.addBatch(sentenciaSQL);
		}
		Conexiones.ejecutarLoteSentencias(loteSentencias, true, true);
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Enumeration' 
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unchecked")
	public static void registrarCierreLR(IXMLElement solicitud) throws ConexionExcepcion, BaseDeDatosExcepcion {
		String sentenciaSQL = "";
		String codCliente = "", nomCliente = "";
		Statement loteSentencias = null;
		ResultSet resultado = null;
		int numOperacion = 0,cantComprado = 0;
		String codlista = solicitud.getFirstChildNamed("codlista").getContent();
		String fechaCierre = solicitud.getFirstChildNamed("fechacierre").getContent();
		String numtiendacierre = solicitud.getFirstChildNamed("numtiendacierre").getContent();
		String numcajacierre = solicitud.getFirstChildNamed("numcajacierre").getContent();
		String codcajerocierre = solicitud.getFirstChildNamed("codcajerocierre").getContent();
		String estado = solicitud.getFirstChildNamed("estado").getContent();

		try {
			loteSentencias = Conexiones.crearSentencia(true);

			sentenciaSQL = "select ifnull(max(numoperacion),0) as numoperacion from operacionlistaregalos where "
							+ "codlista = " + codlista;

			resultado = Conexiones.realizarConsulta(sentenciaSQL,true);
			if (resultado.first())
				try{
					numOperacion = resultado.getInt("numoperacion")+1;
				}catch (SQLException e){
				}
			
			sentenciaSQL = "select listaregalos.codtitular,afiliado.nombre from listaregalos inner join " +							"afiliado on (listaregalos.codtitular = afiliado.codafiliado) where " +
							"listaregalos.codlista = " + codlista;

			resultado = Conexiones.realizarConsulta(sentenciaSQL,true);
			if (resultado.first())
				try{
					codCliente = resultado.getString("codtitular");
					nomCliente = resultado.getString("nombre");
				}catch (SQLException e){
				}

			IXMLElement detallesVenta = solicitud.getFirstChildNamed("detallesventa");
			Enumeration<IXMLElement> detalles = detallesVenta.enumerateChildren();
			int correlativo = 1;
			while(detalles.hasMoreElements()){
				Enumeration<IXMLElement> detalle = ((IXMLElement)detalles.nextElement()).enumerateChildren();

				String codProd = ((IXMLElement)detalle.nextElement()).getContent();
				double montobase = Double.parseDouble(((IXMLElement)detalle.nextElement()).getContent());
				double montoimpuesto = Double.parseDouble(((IXMLElement)detalle.nextElement()).getContent());
				float cantidad =  Float.parseFloat(((IXMLElement)detalle.nextElement()).getContent());
				
				sentenciaSQL = "select cantcomprado from detallelistaregalos where codproducto = '"
							+ codProd + "' and codlista = '" + codlista + "'";
							
				resultado = Conexiones.realizarConsulta(sentenciaSQL,true);
				if (resultado.first())
					cantComprado = resultado.getInt("cantcomprado");
				
				cantComprado += cantidad;
				
				sentenciaSQL = "update detallelistaregalos set detallelistaregalos.cantcomprado = "
								+ cantComprado + " where codproducto = '" + codProd + "' and codlista = "
								+ codlista;
				loteSentencias.addBatch(sentenciaSQL);

				String codClientetxt = (codCliente != null)
									? "'"+codCliente+"'"
									: "null";
									
				String nomClientetxt = (nomCliente != null)
									? "'"+nomCliente+"'"
									: "null";

				sentenciaSQL = "insert into CR.operacionlistaregalos(numoperacion, codcliente, nomcliente, "
								+ "codlista, fecha, tipooperacion, codproducto, montobase, montoimpuesto, "
								+ "cantidad, numtienda, numcaja, codcajero, dedicatoria, correlativoitem) values ("
								+ numOperacion + ", "
								+ codClientetxt + ", "
								+ nomClientetxt + ", "
								+ codlista + ", "
								+ "'" + fechaCierre + "', "
								+ "'V'" + ", "
								+ "'" + codProd + "', "
								+ montobase + ", "
								+ montoimpuesto + ", "
								+ cantidad + ", "
								+ numtiendacierre + ", "
								+ numcajacierre + ", "
								+ "'" + codcajerocierre + "', "
								+ null + ", "
								+ correlativo + ")";
				loteSentencias.addBatch(sentenciaSQL);
				correlativo ++;
			}
			
			sentenciaSQL = "update CR.listaregalos set "
							+ "fechacierre = '" + fechaCierre + "', "
							+ "numtiendacierre = " + numtiendacierre + ", "
							+ "numcajacierre = " + numcajacierre + ", "
							+ "codcajerocierre = '" + codcajerocierre + "' "
							+ "where codlista = " + codlista;

			loteSentencias.addBatch(sentenciaSQL);
				
			Conexiones.ejecutarLoteSentencias(loteSentencias, true, true);

			String fecha = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());		
			sentenciaSQL = "update CR.listaregaloscentral set estado= '"+estado+"',"
						+ "fechaultestado='"+fecha+"',tiendaultestado="+numtiendacierre+" "
						+ "where codlista = " + codlista;
			Conexiones.realizarSentencia(sentenciaSQL,false);

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Conexiones.cerrarConexiones();	
		}
	}
	
	/**
	 * Obtiene los datos de la transacción de cierre del apartado
	 * @param numServicio
	 * @return Vector: [0] numtienda, [1] fecha, [2] numcajafinaliza, [3] numtransaccion
	 */
	public static Vector obtenerTransaccion(int numServicio){
		Vector datosTransaccion = new Vector();
		
		//Obteniendo cliente del apartado
		String sentenciaSQL0 = "Select codcliente, fecha from "+Sesion.getDbEsquema()+".servicio where numservicio="+numServicio+" and numtienda="+Sesion.getNumeroTda();
		ResultSet servicio = null, trxAbono=null, transaccion=null;
		String fechaApertura="";
		try {
			servicio = Conexiones.realizarConsulta(sentenciaSQL0, true);
			servicio.beforeFirst();
			String codCliente ="";
			if(servicio.first()){
				fechaApertura = new SimpleDateFormat("yyyy-MM-dd").format(((Date)servicio.getDate("fecha")));
				codCliente = servicio.getString("codcliente");
				//Obtengo transaccion abono para saber fecha y caja de cierre del apartado
				String sentenciaSQL = "SELECT numtienda, numcaja, fecha FROM "+Sesion.getDbEsquema()+".transaccionabono where " +
						" numtienda="+Sesion.getNumeroTda()+" and " +
						" numservicio="+numServicio+" and " +
						" tipotransaccionabono='F'";
				trxAbono = null;
			
				trxAbono = Conexiones.realizarConsulta(sentenciaSQL, true);
				trxAbono.beforeFirst();
				if(trxAbono.first()){
					int numtienda=trxAbono.getInt("numtienda");
					int numcaja = trxAbono.getInt("numcaja");	
					String fecha = new SimpleDateFormat("yyyy-MM-dd").format(trxAbono.getDate("fecha"));
					
					//Obteniendo datos de la transaccion
					String sentenciaSQL2 = "SELECT t.numtienda, t.fecha, t.numcajafinaliza, t.numtransaccion " +
						" FROM "+Sesion.getDbEsquema()+".transaccion t inner join "+Sesion.getDbEsquema()+".pagodetransaccion p " +
						" on (t.numtienda=p.numtienda and t.numcajafinaliza=p.numcaja and t.fecha=p.fecha and t.numtransaccion=p.numtransaccion) " +
						" WHERE " +
						" t.numtienda="+numtienda+" AND " +
						" t.fecha='"+fecha+"' AND "+
						" t.numcajafinaliza="+numcaja+" AND " +
						" t.codcliente LIKE '"+codCliente+"' and p.codformadepago='15' ";
					transaccion = Conexiones.realizarConsulta(sentenciaSQL2, true);
					transaccion.last();
					int cantResultados = transaccion.getRow();
					if(cantResultados==1){
						transaccion.beforeFirst();
						if(transaccion.first()){
							datosTransaccion.add(new Integer(transaccion.getInt("numtienda")));
							datosTransaccion.add(fecha);
							datosTransaccion.add(new Integer(transaccion.getInt("numcajafinaliza")));
							datosTransaccion.add(new Integer(transaccion.getInt("numtransaccion")));
						}
					}	
				}
			}
			
			if(datosTransaccion.size()==0) {
				System.out.println("Apartado n. "+numServicio+" no será actualizado "+fechaApertura);
			}
			
		} catch (ConexionExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
			return new Vector();
		} catch (BaseDeDatosExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
			return new Vector();
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
			return new Vector();
		} finally{
			try {
				trxAbono.close();
			} catch (Exception e) {
				
			}
			try {
				servicio.close();
			} catch (Exception e) {
				
			}
			try {
				transaccion.close();
			} catch (Exception e) {
				
			}
			Conexiones.cerrarConexiones();
		}
		return datosTransaccion;
		
	}
	
	/**
	 * Actualiza el estado del apartado a Finalizado
	 * @param datosTransaccionCierre Información de la venta con la que se finalizó el apartado
	 * @param numServicio Número del apartado a actualizar
	 */
	public static void actualizarEstadoServicio(Vector datosTransaccionCierre, int numServicio){
		if(datosTransaccionCierre.size()!=0){
			System.out.println("Actualizando apartado "+numServicio+" Por la transacción "+((Integer)datosTransaccionCierre.get(0)).intValue()+"-"+(String)datosTransaccionCierre.get(1)+"-"+((Integer)datosTransaccionCierre.get(2)).intValue()+"-"+((Integer)datosTransaccionCierre.get(3)).intValue());
			String sqlCR = "update CR.servicio set " +
					" numtransaccionventa="+((Integer)datosTransaccionCierre.get(3)).intValue()+", " +
					" fechatransaccionvnta='"+(String)datosTransaccionCierre.get(1)+"', " +
					" numcajaventa="+((Integer)datosTransaccionCierre.get(2)).intValue()+", " +
					" estadoservicio='F', " +
					" regactualizado='S' " +
					" where numtienda="+((Integer)datosTransaccionCierre.get(0)).intValue()+" and numservicio="+numServicio;
			
			String sqlCB = " update BECOFILE.atcm30 set " +
					" nummov="+((Integer)datosTransaccionCierre.get(3)).intValue()+", " +
					" apasal=0, " +
					" staapa=2 " +
					" where tienda="+((Integer)datosTransaccionCierre.get(0)).intValue()+" and numser="+numServicio;
			
			try {
				//Actualizo el apartado en el servidor de tienda
				int afectados = Conexiones.realizarSentencia(sqlCR, true);
				System.out.println("Afectados en el servidor de tienda "+afectados);
				afectados = Conexiones.realizarSentencia(sqlCR, false);
				System.out.println("Afectados en CR-AS400 "+afectados);
				afectados = Conexiones.realizarSentencia(sqlCB, false);
				System.out.println("Afectados en BECOFILE-AS400 "+afectados);				
			} catch (ConexionExcepcion e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			} catch (BaseDeDatosExcepcion e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			} finally{
				Conexiones.cerrarConexiones();
			}
		}
	}
	
	public static Vector getApartadosEnC(){
		Vector apartadosEnC = new Vector();
		String sentenciaSQL = " select numservicio from "+Sesion.getDbEsquema()+".servicio where estadoservicio='C' " +
				" and numtienda="+Sesion.getNumeroTda(); 
		ResultSet servicios=null;
		try {
			servicios = Conexiones.realizarConsulta(sentenciaSQL, true);
			if(servicios.first()){
				servicios.beforeFirst();
				while(servicios.next()){
					apartadosEnC.add(new Integer(servicios.getInt("numservicio")));
				}
			}
		} catch (ConexionExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (BaseDeDatosExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} finally {
			try {
				servicios.close();
			} catch (SQLException e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			}
		}
		return apartadosEnC;
	}
}