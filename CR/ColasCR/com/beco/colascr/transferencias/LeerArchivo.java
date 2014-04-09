package com.beco.colascr.transferencias;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import com.beco.colascr.transferencias.configuracion.Sesion;

/**
 * =============================================================================
 * Proyecto   : Prueba
 * Paquete    : 
 * Programa   : LeerArchivo.java
 * Creado por : irojas
 * Creado en  : 15-oct-03 13:22:19
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
/** 
 * Descripción: 
 * 
 * 
 */
public class LeerArchivo {
	
	/**
	 * Metodo escapear.
	 * 		Sustituye el parámetro en una cadena con el caracter ' precedido del caracter \
	 * @param mensaje Mensaje a ser modificado
	 * @return String - Nueva cadena con la modificación
	 */
	private static String escapear(String mensaje) {
		String result = "";
		
		if (mensaje != null) {
			for (int i=0; i<mensaje.length(); i++) {
				if (mensaje.substring(i,i+1).equals("'"))
					result += "\\";
				result += mensaje.substring(i,i+1);
			}
		}
		return result;
	}

	/**
	 * Método convertirLoad
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se eliminaron y comentaron variables sin uso
	* Fecha: agosto 2011
	*/
	public static void convertirLoad(String path1, String path2) {
		String s;
		BufferedReader in = null;
		BufferedWriter out = null;
		StringTokenizer registro = null;
		String separador, codAfiliado, tipoAfiliado, nombre, apellido, numTienda, numFicha, codDepartamento, 
			   codCargo, nitCliente, direccion, direccionFiscal, email, codArea, numTelefono, codArea1, 
			   numTelefono1, fechaAfiliacion, exentoImpuesto, registrado, contactar, codRegion, estadoAfiliado,
			   estadoColaborador, actualizacion1, /*actualizacion2,*/ actualizacion, genero, estadocivil, fechanacimiento;
		FileOutputStream farchivoOut;
		
		try {
			/* Archivo de Entrada */
			FileInputStream farchivo = new FileInputStream(path1);
			in = new BufferedReader(new InputStreamReader(farchivo));
			/* Archivo de Salida */
			farchivoOut = new FileOutputStream(path2);
			out = new BufferedWriter(new OutputStreamWriter(farchivoOut));
		} catch (Exception e) {
			//NO SE ENCONTRÖ EL ARCHIVO ESPECIFICADO
			e.printStackTrace();
			//System.exit(0);
		}
		
		try {
			System.out.print("\n ***** Parseando Afiliados ***** \n");
			int i = 0;
			while((s = in.readLine()) != null){
				try{					
					registro = new StringTokenizer(s,String.valueOf(Sesion.sepCampo), true);
					
					codAfiliado  = (String)registro.nextElement();
					separador  = (String)registro.nextElement();
					
					tipoAfiliado = (String)registro.nextElement();
					separador = (String)registro.nextElement();
					
					nombre = (String)registro.nextElement();
					separador = (String)registro.nextElement();
					
					apellido = escapear((String)registro.nextElement()).replace(Sesion.delCampo,' ').trim();
					/*if (!apellido.equals("[")) {
						System.out.println();
					}*/
					apellido = (apellido.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : apellido;
					separador = (apellido != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);

					numTienda = (String)registro.nextElement();
					separador = (String)registro.nextElement();
					
					numFicha = (String)registro.nextElement();
					numFicha = (numFicha.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : numFicha;
					separador = (numFicha != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);
					
					codDepartamento = (String)registro.nextElement();
					codDepartamento = (codDepartamento.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" :  codDepartamento;
					separador = (codDepartamento != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);

					codCargo = (String)registro.nextElement();
					codCargo = (codCargo.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : codCargo;
					separador = (codCargo != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);

					nitCliente = (String)registro.nextElement();
					nitCliente = (nitCliente.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : nitCliente;
					separador = (nitCliente != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);

					direccion = (String)registro.nextElement();
					separador = (String)registro.nextElement();

					direccionFiscal = (String)registro.nextElement();
					direccionFiscal = (direccionFiscal.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : direccionFiscal;
					separador = (direccionFiscal != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);

					email = escapear((String)registro.nextElement()).replace(Sesion.delCampo,' ').trim();
					email = (email.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : email;
					separador = (email != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);

					codArea = (String)registro.nextElement();
					codArea = (codArea.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : codArea;
					separador = (codArea != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);

					numTelefono = (String)registro.nextElement();
					numTelefono = (numTelefono.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : numTelefono;
					separador = (numTelefono != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);

					codArea1 = escapear((String)registro.nextElement()).replace(Sesion.delCampo,' ').trim();
					codArea1 = (codArea1.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : codArea1;
					separador = (codArea1 != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);

					numTelefono1 = escapear((String)registro.nextElement()).replace(Sesion.delCampo,' ').trim();
					numTelefono1 = (numTelefono1.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : numTelefono1;
					separador = (numTelefono1 != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);

					fechaAfiliacion = (String)registro.nextElement();
					separador = (String)registro.nextElement();

					exentoImpuesto = (String)registro.nextElement();
					separador = (String)registro.nextElement();

					registrado = (String)registro.nextElement();
					separador = (String)registro.nextElement();

					contactar = (String)registro.nextElement();
					separador = (String)registro.nextElement();

					codRegion = (String)registro.nextElement();
					separador = (String)registro.nextElement();

					estadoAfiliado = (String)registro.nextElement();
					separador = (String)registro.nextElement();

					estadoColaborador = (String)registro.nextElement();
					separador = (String)registro.nextElement();

					actualizacion1 = escapear((String)registro.nextElement()).trim();
					actualizacion = actualizacion1.substring(1,20) + Sesion.delCampo;
					separador = (String)registro.nextElement();
					
					//***Nuevo CRM wdiaz
					genero = ((String)registro.nextElement()).trim();
					genero = (genero.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : genero;
					separador = (genero != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);
					
					estadocivil = ((String)registro.nextElement()).trim();
					estadocivil = (estadocivil.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : estadocivil;
					separador = (estadocivil != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);
					
					try{
						fechanacimiento = ((String)registro.nextElement()).trim();
						fechanacimiento = (fechanacimiento.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : fechanacimiento;
					}catch (NoSuchElementException e) {
						fechanacimiento = Sesion.delCampo+"1900-01-01"+Sesion.delCampo;
					}
					
					//**********

					out.write(codAfiliado + Sesion.sepCampo + tipoAfiliado + Sesion.sepCampo +  nombre + 
							Sesion.sepCampo +  apellido + Sesion.sepCampo + numTienda + Sesion.sepCampo +
							numFicha + Sesion.sepCampo + codDepartamento + Sesion.sepCampo + codCargo + 
							Sesion.sepCampo + nitCliente + Sesion.sepCampo + direccion + Sesion.sepCampo + 
							direccionFiscal + Sesion.sepCampo + email + Sesion.sepCampo + codArea + 
							Sesion.sepCampo + numTelefono + Sesion.sepCampo + codArea1 + Sesion.sepCampo + 
							numTelefono1 + Sesion.sepCampo + fechaAfiliacion + Sesion.sepCampo + exentoImpuesto +
							Sesion.sepCampo + registrado + Sesion.sepCampo + contactar + Sesion.sepCampo +
							codRegion + Sesion.sepCampo + estadoAfiliado + Sesion.sepCampo + estadoColaborador +
							Sesion.sepCampo + actualizacion + String.valueOf(Sesion.sepCampo) +
							genero + Sesion.sepCampo + estadocivil + Sesion.sepCampo + fechanacimiento 
							+ String.valueOf(Sesion.sepCampo) + "\\N\r\n");
				}catch(NoSuchElementException e){
					System.out.println("Numero Registro " + i);
					e.printStackTrace();
				}catch(Exception e){
					System.out.println("Numero Registro " + i);
					e.printStackTrace();
				}				
			}
			in.close();
			out.close();
		}
		catch(java.io.IOException io){io.printStackTrace();}
		catch (Exception ex){ex.printStackTrace();}
	}

	/**
	 * Método convertirLoad
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se eliminaron y comentaron variables sin uso
	* Fecha: agosto 2011
	*/
	public static void convertirLoadProducto(String path1, String path2) {
		String s;
		BufferedReader in = null;
		BufferedWriter out = null;
		StringTokenizer registro = null;
		String separador, codProducto, descripcionCorta, descripcionLarga, codunidadventa, referenciaProveedor, 
			   marca, modelo, coddepartamento, codlineaseccion, costolista, precioregular, codimpuesto,
			   cantVtaEmpaq, desctoVEmpaque, indDctoEmplead, indDespa, estadoProd,
			   actualizacion1, /*actualizacion2,*/ actualizacion;
		FileOutputStream farchivoOut;
		
		try {
			/* Archivo de Entrada */
			FileInputStream farchivo = new FileInputStream(path1);
			in = new BufferedReader(new InputStreamReader(farchivo,"ISO-8859-1"));
			/* Archivo de Salida */
			farchivoOut = new FileOutputStream(path2);
			out = new BufferedWriter(new OutputStreamWriter(farchivoOut,"UTF8"));
		} catch (Exception e) {
			//NO SE ENCONTRÖ EL ARCHIVO ESPECIFICADO
			e.printStackTrace();
			//System.exit(0);
		}
		
		try {
			System.out.print("\n ***** Parseando Productos ***** \n");
			int i = 0;
			while((s = in.readLine()) != null){
				try{					
					registro = new StringTokenizer(s,String.valueOf(Sesion.sepCampo), true);
					
					codProducto  = (String)registro.nextElement();
					separador  = (String)registro.nextElement();
					
					descripcionCorta = (String)registro.nextElement();
					separador = (String)registro.nextElement();
					
					descripcionLarga = (String)registro.nextElement();
					descripcionLarga = (descripcionLarga.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : descripcionLarga;
					separador = (descripcionLarga != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);
					
					codunidadventa = (String)registro.nextElement();
					separador = (String)registro.nextElement();

					referenciaProveedor = (String)registro.nextElement();
					referenciaProveedor = (referenciaProveedor.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" :  referenciaProveedor;
					separador = (referenciaProveedor != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);

					marca = (String)registro.nextElement();
					marca = (marca.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : marca;
					separador = (marca != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);

					modelo = (String)registro.nextElement();
					modelo = (modelo.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : modelo;
					separador = (modelo != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);

					coddepartamento = (String)registro.nextElement();
					separador = (String)registro.nextElement();

					codlineaseccion = (String)registro.nextElement();
					separador = (String)registro.nextElement();

					costolista = (String)registro.nextElement();
					separador = (String)registro.nextElement();

					precioregular = (String)registro.nextElement();
					separador = (String)registro.nextElement();

					codimpuesto = (String)registro.nextElement();
					separador = (String)registro.nextElement();

					cantVtaEmpaq = (String)registro.nextElement();
					separador = (String)registro.nextElement();

					desctoVEmpaque = (String)registro.nextElement();
					desctoVEmpaque = (desctoVEmpaque.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : desctoVEmpaque;
					separador = (desctoVEmpaque != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);

					indDctoEmplead = (String)registro.nextElement();
					separador = (String)registro.nextElement();

					indDespa = (String)registro.nextElement();
					indDespa = (indDespa.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : indDespa;
					separador = (indDespa != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);

					estadoProd= (String)registro.nextElement();
					separador = (String)registro.nextElement();

					actualizacion1 = escapear((String)registro.nextElement()).trim();
					//System.out.println(actualizacion1);
					actualizacion = actualizacion1.substring(1,20) + Sesion.delCampo;

					out.write(new String((codProducto + Sesion.sepCampo + descripcionCorta + Sesion.sepCampo + 
							descripcionLarga + Sesion.sepCampo +  codunidadventa + Sesion.sepCampo +  
							referenciaProveedor + Sesion.sepCampo +  marca + Sesion.sepCampo +  
							modelo + Sesion.sepCampo + coddepartamento + Sesion.sepCampo + 
							codlineaseccion + Sesion.sepCampo + costolista + Sesion.sepCampo + 
							precioregular + Sesion.sepCampo + codimpuesto + Sesion.sepCampo + 
							cantVtaEmpaq + Sesion.sepCampo +desctoVEmpaque + Sesion.sepCampo + 
							indDctoEmplead + Sesion.sepCampo + indDespa + Sesion.sepCampo + 
							estadoProd + Sesion.sepCampo + 
							actualizacion + String.valueOf(Sesion.sepCampo) + "\\N\r\n").getBytes()));
				}catch(Exception e){
					System.out.println("Numero Registro " + i);
					e.printStackTrace();
				}				
			}
			in.close();
			out.close();
		}
		catch(java.io.IOException io){io.printStackTrace();}
		catch (Exception ex){ex.printStackTrace();}
	}

	/**
	 * Método convertirLoadCodExterno
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se comentaron y eliminaron variables sin uso
	* Fecha: agosto 2011
	*/
	public static void convertirLoadCodExterno(String path1, String path2) {
		String s;
		BufferedReader in = null;
		BufferedWriter out = null;
		StringTokenizer registro = null;
		String separador, codProducto, codExterno, actualizacion1, actualizacion;
		FileOutputStream farchivoOut;
		
		try {
			/* Archivo de Entrada */
			FileInputStream farchivo = new FileInputStream(path1);
			in = new BufferedReader(new InputStreamReader(farchivo));
			/* Archivo de Salida */
			farchivoOut = new FileOutputStream(path2);
			out = new BufferedWriter(new OutputStreamWriter(farchivoOut));
		} catch (Exception e) {
			//NO SE ENCONTRÖ EL ARCHIVO ESPECIFICADO
			e.printStackTrace();
			//System.exit(0);
		}
		
		try {
			System.out.print("\n ***** Parseando CodExternos ***** \n");
			int i = 0;
			while((s = in.readLine()) != null){
				try{					
					registro = new StringTokenizer(s,String.valueOf(Sesion.sepCampo), true);
					
					codProducto  = (String)registro.nextElement();
					separador  = (String)registro.nextElement();
					
					codExterno = (String)registro.nextElement();
					separador = (String)registro.nextElement();
					
					actualizacion1 = escapear((String)registro.nextElement()).trim();
					//System.out.println(actualizacion1);
					actualizacion = actualizacion1.substring(1,20) + Sesion.delCampo;

					out.write(codProducto + Sesion.sepCampo + codExterno + Sesion.sepCampo + actualizacion + String.valueOf(Sesion.sepCampo) + "\\N\r\n");
				}catch(Exception e){
					System.out.println("Numero Registro " + i);
					e.printStackTrace();
				}				
			}
			in.close();
			out.close();
		}
		catch(java.io.IOException io){io.printStackTrace();}
		catch (Exception ex){ex.printStackTrace();}
	}

	/**
	 * Método buscarDetallePromoEnArch
	 *  Detecta si el detalle de promocion de un producto transferido se pasó
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se comentaron y eliminaron variables sin uso
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unused")
	public static boolean buscarDetallePromoEnArch(String path1, String codProd, int codProm) {
		String s;
		BufferedReader in = null;
		StringTokenizer registro = null;
		String separador, codPromocion, numDetalle, numCupon, codDpto, codLinSecc, 
			   codProducto;
		boolean encontrado = false;
		
		try {
			/* Archivo de Entrada */
			FileInputStream farchivo = new FileInputStream(path1);
			in = new BufferedReader(new InputStreamReader(farchivo));
		} catch (Exception e) {
			//NO SE ENCONTRÖ EL ARCHIVO ESPECIFICADO
			e.printStackTrace();
			//System.exit(0);
		}
		
		try {
			System.out.print("\n ***** Leyendo Detalles de Promociones ***** \n");
			int i = 0;
			while((s = in.readLine()) != null){
				try{					
					registro = new StringTokenizer(s,String.valueOf(Sesion.sepCampo), true);
					
					codPromocion  = (String)registro.nextElement();
					separador  = (String)registro.nextElement();
					
					numDetalle = (String)registro.nextElement();
					separador = (String)registro.nextElement();
					
					numCupon = (String)registro.nextElement();
					numCupon = (numCupon.equals(String.valueOf(Sesion.sepCampo))) ? null : numCupon;
					separador = (numCupon != null) ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);
					
					codDpto = (String)registro.nextElement();
					codDpto = (codDpto.equals(String.valueOf(Sesion.sepCampo))) ? null : codDpto;
					separador = (codDpto != null) ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);

					codLinSecc = (String)registro.nextElement();
					codLinSecc = (codLinSecc.equals(String.valueOf(Sesion.sepCampo))) ? null : codLinSecc;
					separador = (codLinSecc != null) ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);

					codProducto = (String)registro.nextElement();
					codProducto = (codProducto.equals(String.valueOf(Sesion.sepCampo))) ? null : codProducto;
					separador = (codProducto != null) ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);
					
					codPromocion = codPromocion.replace(']',' ').trim();
					if (codProducto != null)
						codProducto = codProducto.replace(']',' ').trim();
					
					if (codProducto!= null && codProducto.equals(codProd.trim()) && Integer.parseInt(codPromocion) == codProm) {
						encontrado = true;
						break;
					}

				}catch(NoSuchElementException e){
					System.out.println("Numero Registro " + i);
					e.printStackTrace();
				}				
			}
			in.close();
		}
		catch(java.io.IOException io){io.printStackTrace();}
		catch (Exception ex){ex.printStackTrace();}
		
		return encontrado;
	}
	
	/**
	 * Método convertirLoadPromocion
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se comentaron/eliminaron variables sin uso
	* Fecha: agosto 2011
	*/
	public static void convertirLoadPromocion(String path1, String path2) {
		String s;
		BufferedReader in = null;
		BufferedWriter out = null;
		StringTokenizer registro = null;
		String separador, codPromocion, tipoPromocion, fechaInicio, horaInicio, fechaFinaliza, 
			   horaFinaliza, prioridad;
		FileOutputStream farchivoOut;
		
		try {
			/* Archivo de Entrada */
			FileInputStream farchivo = new FileInputStream(path1);
			in = new BufferedReader(new InputStreamReader(farchivo));
			/* Archivo de Salida */
			farchivoOut = new FileOutputStream(path2);
			//Se guarda en archivo en formato utf8 para ser cargado correctamente en la base de datos
			//jperez, 07-08-2012
			out = new BufferedWriter(new OutputStreamWriter(farchivoOut,"UTF8"));
		} catch (Exception e) {
			//NO SE ENCONTRÖ EL ARCHIVO ESPECIFICADO
			e.printStackTrace();
			//System.exit(0);
		}
		
		try {
			System.out.print("\n ***** Parseando Promociones ***** \n");
			int i = 0;
			while((s = in.readLine()) != null){
				try{					
					registro = new StringTokenizer(s,String.valueOf(Sesion.sepCampo), true);
					
					codPromocion  = (String)registro.nextElement();
					separador  = (String)registro.nextElement();
					
					tipoPromocion = (String)registro.nextElement();
					tipoPromocion = (tipoPromocion.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : tipoPromocion;
					separador = (tipoPromocion != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);

					
					fechaInicio = (String)registro.nextElement();
					fechaInicio = (fechaInicio.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : fechaInicio;
					separador = (fechaInicio != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);
					
					horaInicio = (String)registro.nextElement();
					separador = (String)registro.nextElement();

					fechaFinaliza = (String)registro.nextElement();
					fechaFinaliza = (fechaFinaliza.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" :  fechaFinaliza;
					separador = (fechaFinaliza != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);

					horaFinaliza = (String)registro.nextElement();
					separador = (String)registro.nextElement();

					prioridad = (String)registro.nextElement();
					prioridad = (prioridad.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : prioridad;

					out.write(codPromocion + Sesion.sepCampo + tipoPromocion + Sesion.sepCampo + fechaInicio + Sesion.sepCampo 
								+  horaInicio + Sesion.sepCampo +  fechaFinaliza + Sesion.sepCampo +  horaFinaliza + Sesion.sepCampo 
								+  prioridad + String.valueOf(Sesion.sepCampo) + "\\N\r\n");
				}catch(Exception e){
					System.out.println("Numero Registro " + i);
					e.printStackTrace();
				}				
			}
			in.close();
			out.close();
		}
		catch(java.io.IOException io){io.printStackTrace();}
		catch (Exception ex){ex.printStackTrace();}
	}
	
	/**
	 * Método convertirLoadDetPromocion
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se comentaron/eliminaron variables sin uso
	* Fecha: agosto 2011
	*/
	public static void convertirLoadDetPromocion(String path1, String path2) {
		String s;
		BufferedReader in = null;
		BufferedWriter out = null;
		StringTokenizer registro = null;
		String separador, codPromocion, numDetalle, numCupon, codDpto, codLinSecc, 
			   codProducto, porcentajeDescto, precioFinal;
		FileOutputStream farchivoOut;
		
		try {
			/* Archivo de Entrada */
			FileInputStream farchivo = new FileInputStream(path1);
			in = new BufferedReader(new InputStreamReader(farchivo));
			/* Archivo de Salida */
			farchivoOut = new FileOutputStream(path2);
			out = new BufferedWriter(new OutputStreamWriter(farchivoOut));
		} catch (Exception e) {
			//NO SE ENCONTRÖ EL ARCHIVO ESPECIFICADO
			e.printStackTrace();
			//System.exit(0);
		}
		
		try {
			System.out.print("\n ***** Parseando Detalles de Promociones ***** \n");
			int i = 0;
			while((s = in.readLine()) != null){
				try{					
					registro = new StringTokenizer(s,String.valueOf(Sesion.sepCampo), true);
					
					codPromocion  = (String)registro.nextElement();
					separador  = (String)registro.nextElement();
					
					numDetalle = (String)registro.nextElement();
					separador = (String)registro.nextElement();
					
					numCupon = (String)registro.nextElement();
					numCupon = (numCupon.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : numCupon;
					separador = (numCupon != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);
					
					codDpto = (String)registro.nextElement();
					codDpto = (codDpto.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : codDpto;
					separador = (codDpto != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);

					codLinSecc = (String)registro.nextElement();
					codLinSecc = (codLinSecc.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : codLinSecc;
					separador = (codLinSecc != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);

					codProducto = (String)registro.nextElement();
					codProducto = (codProducto.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : codProducto;
					separador = (codProducto != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);
					
					porcentajeDescto = (String)registro.nextElement();
					separador = (String)registro.nextElement();
					
					precioFinal = (String)registro.nextElement();

					out.write(codPromocion + Sesion.sepCampo + numDetalle + Sesion.sepCampo + numCupon + Sesion.sepCampo 
								+  codDpto + Sesion.sepCampo +  codLinSecc + Sesion.sepCampo +  codProducto + Sesion.sepCampo 
								+  porcentajeDescto + Sesion.sepCampo +  precioFinal  
								+  String.valueOf(Sesion.sepCampo) + "\\N\r\n");
				}catch(Exception e){
					System.out.println("Numero Registro " + i);
					e.printStackTrace();
				}				
			}
			in.close();
			out.close();
		}
		catch(java.io.IOException io){io.printStackTrace();}
		catch (Exception ex){ex.printStackTrace();}
	}
	
	/**
	 * Método convertirLoadDetPromocionExtendida
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void convertirLoadDetPromocionExtendida(String path1, String path2) {
		String s;
		BufferedReader in = null;
		BufferedWriter out = null;
		StringTokenizer registro = null;
		String separador, codPromocion, numDetalle, codDpto, codCat, codLinSecc, 
			   codProducto, porcentajeDescto, edoRegistro, marca, refProveedor,
			   montoMinimo, cantMinima,cantRegalada, montoDescuento, nombrePromo, prodSinConsec, codSeccion, acumulaPremio;
		FileOutputStream farchivoOut;
		
		try {
			/* Archivo de Entrada */
			FileInputStream farchivo = new FileInputStream(path1);
			in = new BufferedReader(new InputStreamReader(farchivo));
			/* Archivo de Salida */
			farchivoOut = new FileOutputStream(path2);
			out = new BufferedWriter(new OutputStreamWriter(farchivoOut));
		} catch (Exception e) {
			//NO SE ENCONTRÖ EL ARCHIVO ESPECIFICADO
			e.printStackTrace();
			//System.exit(0);
		}
		
		try {
			System.out.print("\n ***** Parseando Detalles de Promociones Extendidas ***** \n");
			int i = 0;
			while((s = in.readLine()) != null){
				try{					
					registro = new StringTokenizer(s,String.valueOf(Sesion.sepCampo), true);
					
					codPromocion  = (String)registro.nextElement();
					separador  = (String)registro.nextElement();
					
					numDetalle = (String)registro.nextElement();
					separador = (String)registro.nextElement();
					
					porcentajeDescto = (String)registro.nextElement();
					separador = (String)registro.nextElement();
					
					codCat = (String)registro.nextElement();
					codCat = (codCat.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : codCat;
					separador = (codCat != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);
					
					codDpto = (String)registro.nextElement();
					codDpto = (codDpto.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : codDpto;
					separador = (codDpto != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);
					
					marca = (String)registro.nextElement();
					marca = (marca.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : marca;
					separador = (marca != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);

					codLinSecc = (String)registro.nextElement();
					codLinSecc = (codLinSecc.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : codLinSecc;
					separador = (codLinSecc != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);

					refProveedor = (String)registro.nextElement();
					refProveedor = (refProveedor.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : refProveedor;
					separador = (refProveedor != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);
					
					montoMinimo = (String)registro.nextElement();
					separador = (String)registro.nextElement();
					
					cantMinima = (String)registro.nextElement();
					separador = (String)registro.nextElement();
					
					cantRegalada = (String)registro.nextElement();
					separador = (String)registro.nextElement();
					
					codProducto = (String)registro.nextElement();
					codProducto = (codProducto.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : codProducto;
					separador = (codProducto != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);
					
					montoDescuento = (String)registro.nextElement();
					separador = (String)registro.nextElement();
					
					edoRegistro = (String)registro.nextElement();
					edoRegistro = (edoRegistro.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : edoRegistro;
					separador = (edoRegistro != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);
					
					nombrePromo = (String)registro.nextElement();
					nombrePromo = (nombrePromo.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : nombrePromo;
					separador = (nombrePromo != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);
					
					prodSinConsec = (String)registro.nextElement();
					prodSinConsec = (prodSinConsec.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : prodSinConsec;
					separador = (prodSinConsec != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);
							
					codSeccion = (String)registro.nextElement();
					codSeccion = (codSeccion.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : codSeccion;
					separador = (codSeccion != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);
					
					acumulaPremio = (String)registro.nextElement();

					out.write(codPromocion + Sesion.sepCampo + numDetalle + Sesion.sepCampo + porcentajeDescto + Sesion.sepCampo 
								+  codCat + Sesion.sepCampo +  codDpto + Sesion.sepCampo +  marca + Sesion.sepCampo 
								+  codLinSecc + Sesion.sepCampo +  refProveedor  + Sesion.sepCampo +  montoMinimo
								+ Sesion.sepCampo +  cantMinima + Sesion.sepCampo +  cantRegalada + Sesion.sepCampo +  codProducto
								+ Sesion.sepCampo +  montoDescuento + Sesion.sepCampo +  edoRegistro + Sesion.sepCampo +  nombrePromo
								+ Sesion.sepCampo +  prodSinConsec + Sesion.sepCampo +  codSeccion + Sesion.sepCampo + acumulaPremio
								+ String.valueOf(Sesion.sepCampo) + "\\N\r\n");
					/*System.out.println(codPromocion + Sesion.sepCampo + numDetalle + Sesion.sepCampo + porcentajeDescto + Sesion.sepCampo 
								+  codCat + Sesion.sepCampo +  codDpto + Sesion.sepCampo +  marca + Sesion.sepCampo 
								+  codLinSecc + Sesion.sepCampo +  refProveedor  + Sesion.sepCampo +  montoMinimo
								+ Sesion.sepCampo +  cantMinima + Sesion.sepCampo +  cantRegalada + Sesion.sepCampo +  codProducto
								+ Sesion.sepCampo +  montoDescuento + Sesion.sepCampo +  edoRegistro + Sesion.sepCampo +  nombrePromo
								+ Sesion.sepCampo +  prodSinConsec + Sesion.sepCampo +  codSeccion + Sesion.sepCampo + acumulaPremio
								+ String.valueOf(Sesion.sepCampo) + "\\N\r\n");*/
				}catch(Exception e){
					System.out.println("Numero Registro " + i);
					e.printStackTrace();
				}				
			}
			in.close();
			out.close();
		}
		catch(java.io.IOException io){io.printStackTrace();}
		catch (Exception ex){ex.printStackTrace();}
	}

	/**
	 * Método convertirLoadDetPromocionExtendida
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se eliminaron variables sin uso
	* Fecha: agosto 2011
	*/
	public static void convertirLoadDonacion(String path1, String path2) {
		String s;
		BufferedReader in = null;
		BufferedWriter out = null;
		StringTokenizer registro = null;
		String separador, codDonacion, codBarraProd, fechaInicio, fechaFin,  nombreDonacion, 
			   descripcionDonacion, tipoDonacion, edoDonacion, cantPropDonacion, regActualizado, mostrarAlTotalizar;
		FileOutputStream farchivoOut;
		
		try {
			/* Archivo de Entrada */
			FileInputStream farchivo = new FileInputStream(path1);
			in = new BufferedReader(new InputStreamReader(farchivo));
			/* Archivo de Salida */
			farchivoOut = new FileOutputStream(path2);
			out = new BufferedWriter(new OutputStreamWriter(farchivoOut));
		} catch (Exception e) {
			//NO SE ENCONTRÖ EL ARCHIVO ESPECIFICADO
			e.printStackTrace();
			//System.exit(0);
		}
		
		try {
			System.out.print("\n ***** Parseando Datos Donaciones ***** \n");
			int i = 0;
			while((s = in.readLine()) != null){
				try{					
					registro = new StringTokenizer(s,String.valueOf(Sesion.sepCampo), true);
					
					codDonacion  = (String)registro.nextElement();
					separador  = (String)registro.nextElement();
					
					codBarraProd = (String)registro.nextElement();
					separador = (String)registro.nextElement();
					
					fechaInicio = (String)registro.nextElement();
					fechaInicio = (fechaInicio.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : fechaInicio;
					separador = (fechaInicio != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);
					

					fechaFin = (String)registro.nextElement();
					fechaFin = (fechaFin.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" :  fechaFin;
					separador = (fechaFin != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);
					
					
					nombreDonacion = (String)registro.nextElement();
					nombreDonacion = (nombreDonacion.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : nombreDonacion;
					separador = (nombreDonacion != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);
					
					descripcionDonacion = (String)registro.nextElement();
					descripcionDonacion = (descripcionDonacion.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : descripcionDonacion;
					separador = (descripcionDonacion != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);
					
					tipoDonacion = (String)registro.nextElement();
					separador = (String)registro.nextElement();
					
					edoDonacion = (String)registro.nextElement();
					separador = (String)registro.nextElement();
					
					cantPropDonacion = (String)registro.nextElement();
					separador = (String)registro.nextElement();
					
					regActualizado = (String)registro.nextElement();
					regActualizado = (regActualizado.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : regActualizado;

					mostrarAlTotalizar = (String)registro.nextElement();
					
					out.write(codDonacion + Sesion.sepCampo + codBarraProd + Sesion.sepCampo + fechaInicio + Sesion.sepCampo 
								+  fechaFin + Sesion.sepCampo +  nombreDonacion + Sesion.sepCampo +  descripcionDonacion + Sesion.sepCampo 
								+  tipoDonacion + Sesion.sepCampo +  edoDonacion  + Sesion.sepCampo +  cantPropDonacion
								+ Sesion.sepCampo +  regActualizado 
								+ Sesion.sepCampo +  mostrarAlTotalizar 
								+ String.valueOf(Sesion.sepCampo) + "\\N\r\n");
				}catch(Exception e){
					System.out.println("Numero Registro " + i);
					e.printStackTrace();
				}				
			}
			in.close();
			out.close();
		}
		catch(java.io.IOException io){io.printStackTrace();}
		catch (Exception ex){ex.printStackTrace();}
	}
	
	/**
	 * Método convertirLoadDetPromocionExtendida
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void convertirLoadTransaccionPremiada(String path1, String path2) {
		String s;
		BufferedReader in = null;
		BufferedWriter out = null;
		StringTokenizer registro = null;
		String separador, nroTrans, nroTransXDia, maxMontoPremio, maxMontoPremioXDia, codPromocion, regActualizado;
		FileOutputStream farchivoOut;
		
		try {
			/* Archivo de Entrada */
			FileInputStream farchivo = new FileInputStream(path1);
			in = new BufferedReader(new InputStreamReader(farchivo));
			/* Archivo de Salida */
			farchivoOut = new FileOutputStream(path2);
			out = new BufferedWriter(new OutputStreamWriter(farchivoOut));
		} catch (Exception e) {
			//NO SE ENCONTRÖ EL ARCHIVO ESPECIFICADO
			e.printStackTrace();
			//System.exit(0);
		}
		
		try {
			System.out.print("\n ***** Parseando Datos Transacciones Premiadas ***** \n");
			int i = 0;
			while((s = in.readLine()) != null){
				try{					
					registro = new StringTokenizer(s,String.valueOf(Sesion.sepCampo), true);
					
					nroTrans  = (String)registro.nextElement();
					separador  = (String)registro.nextElement();
					
					nroTransXDia = (String)registro.nextElement();
					separador = (String)registro.nextElement();
					
					maxMontoPremio = (String)registro.nextElement();
					separador = (String)registro.nextElement();
					
					maxMontoPremioXDia = (String)registro.nextElement();
					separador = (String)registro.nextElement();
					
					codPromocion = (String)registro.nextElement();
					separador = (String)registro.nextElement();
					
					regActualizado = (String)registro.nextElement();
					regActualizado = (regActualizado.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : regActualizado;

					out.write(nroTrans + Sesion.sepCampo + nroTransXDia + Sesion.sepCampo + maxMontoPremio + Sesion.sepCampo 
								+  maxMontoPremioXDia + Sesion.sepCampo +  codPromocion 
								+ Sesion.sepCampo +  regActualizado 
								+ String.valueOf(Sesion.sepCampo) + "\\N\r\n");
				}catch(Exception e){
					System.out.println("Numero Registro " + i);
					e.printStackTrace();
				}				
			}
			in.close();
			out.close();
		}
		catch(java.io.IOException io){io.printStackTrace();}
		catch (Exception ex){ex.printStackTrace();}
	}
	
	/**
	 * Método convertirLoadCondicionPromocion
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void convertirLoadCondicionPromocion(String path1, String path2) {
		String s;
		BufferedReader in = null;
		BufferedWriter out = null;
		StringTokenizer registro = null;
		String separador, codPromo, orden, lineaCondicion, regActualizado;
		FileOutputStream farchivoOut;
		
		try {
			/* Archivo de Entrada */
			FileInputStream farchivo = new FileInputStream(path1);
			in = new BufferedReader(new InputStreamReader(farchivo));
			/* Archivo de Salida */
			farchivoOut = new FileOutputStream(path2);
			out = new BufferedWriter(new OutputStreamWriter(farchivoOut));
		} catch (Exception e) {
			//NO SE ENCONTRÖ EL ARCHIVO ESPECIFICADO
			e.printStackTrace();
			//System.exit(0);
		}
		
		try {
			System.out.print("\n ***** Parseando Datos Transacciones Premiadas ***** \n");
			int i = 0;
			while((s = in.readLine()) != null){
				try{					
					registro = new StringTokenizer(s,String.valueOf(Sesion.sepCampo), true);
					
					codPromo  = (String)registro.nextElement();
					separador  = (String)registro.nextElement();
					
					orden = (String)registro.nextElement();
					separador = (String)registro.nextElement();
					
					lineaCondicion = (String)registro.nextElement();
					lineaCondicion = (lineaCondicion.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : lineaCondicion;
					separador = (lineaCondicion != "\\N") ? (String)registro.nextElement() : String.valueOf(Sesion.sepCampo);
					
					regActualizado = (String)registro.nextElement();
					regActualizado = (regActualizado.equals(String.valueOf(Sesion.sepCampo))) ? "\\N" : regActualizado;

					out.write(codPromo + Sesion.sepCampo + orden + Sesion.sepCampo + lineaCondicion + 
								+ Sesion.sepCampo +  regActualizado 
								+ String.valueOf(Sesion.sepCampo) + "\\N\r\n");
				}catch(Exception e){
					System.out.println("Numero Registro " + i);
					e.printStackTrace();
				}				
			}
			in.close();
			out.close();
		}
		catch(java.io.IOException io){io.printStackTrace();}
		catch (Exception ex){ex.printStackTrace();}
	}
	
	/**
	 * Método convertirLoadProdSeccion
	 * 
	 * @throws SQLException
	 * @throws BaseDeDatosExcepcion
	 */
	public static void convertirLoadProdSeccion(String path1, String path2) {
		String s;
		BufferedReader in = null;
		BufferedWriter out = null;
		StringTokenizer registro = null;
		String separador, codProducto, codDepartamento, codLinea, codSeccion;
		FileOutputStream farchivoOut;
		
		try {
			/* Archivo de Entrada */
			FileInputStream farchivo = new FileInputStream(path1);
			in = new BufferedReader(new InputStreamReader(farchivo));
			/* Archivo de Salida */
			farchivoOut = new FileOutputStream(path2);
			out = new BufferedWriter(new OutputStreamWriter(farchivoOut));
		} catch (Exception e) {
			//NO SE ENCONTRÖ EL ARCHIVO ESPECIFICADO
			e.printStackTrace();
			//System.exit(0);
		}
		
		try {
			System.out.print("\n ***** Parseando Datos Producto_Seccion ***** \n");
			int i = 0;
			while((s = in.readLine()) != null){
				try{					
					registro = new StringTokenizer(s,String.valueOf(Sesion.sepCampo), true);
					
					codProducto = (String)registro.nextElement();
					separador  = (String)registro.nextElement();
					
					codDepartamento = (String)registro.nextElement();
					separador = (String)registro.nextElement();
					
					codLinea = (String)registro.nextElement();
					separador = (String)registro.nextElement();
					
					codSeccion = (String)registro.nextElement();

					out.write(codProducto + Sesion.sepCampo + codDepartamento+ Sesion.sepCampo + codLinea  
								+ Sesion.sepCampo +  codSeccion 
								+ String.valueOf(Sesion.sepCampo) + "\\N\r\n");
				}catch(Exception e){
					System.out.println("Numero Registro " + i);
					e.printStackTrace();
				}				
			}
			in.close();
			out.close();
		}
		catch(java.io.IOException io){io.printStackTrace();}
		catch (Exception ex){ex.printStackTrace();}
	}
}
