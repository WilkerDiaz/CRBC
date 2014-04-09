/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.manejadorauditoria
 * Programa   : MediadorAuditoria.java
 * Creado por : apeinado
 * Creado en  : 10/11/2003 02:20:48 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.4
 * Fecha       : 19-jul-04 09:15
 * Analista    : gmartinelli
 * Descripción : Uso de Esquema en los queryes CR.tabla.campo
 * =============================================================================
 * Versión     : 1.3.3
 * Fecha       : 24/03/2004 - 14:48 PM
 * Analista    : Gabriel Martinelli
 * Descripción : Modificado el metodo escribirArchivoTemp que escribe en el temporal para que si tiene que
 * 				crear el archivo Xml lo haga de manera tal que el primero nodo sea la fecha de creacion.
 * 				 Arreglada la llamada a la Excepcion de Funcion para que use los nuevos constructores que
 * 				reciben un boolean
 * =============================================================================
 * Versión     : 1.3.2
 * Fecha       : 02/03/2004 - 11:43 AM
 * Analista    : Gabriel Martinelli
 * Descripción : Manejo de Excepcion XmlExcepcion para los registros de Xml.
 * 				 Eliminado metodo crearNodo que creaba un nodo Xml, no era utilizado
 * =============================================================================
 * Versión     : 1.3.1
 * Fecha       : 06/02/2004 - 10:45:00 AM
 * Analista    : Gabriel Martinelli
 * Descripción : Agregado el método Escapear para garantizar que los apóstrofes en las sentencias
 *               no rompan la ejecución del registro de la auditorìa ya que "escapea" el mensaje 
 *              a registrar en la BD.
 * =============================================================================
 * Versión     : 1.2
 * Fecha       : 05/02/2004 - 03:45:00 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Lineas cambiadas 02  |  Lineas Eliminadas 01
 *                
 *                 Linea Cambiada:
 *                  antes: sentenciaSql += "'" + Sesion.NO + "',";
 *                  después: sentenciaSql += "'" + Sesion.NO + "')";
 * 				
 * 					antes: SimpleDateFormat fechaActual = new SimpleDateFormat("yyyyMMddHHmmss");
 * 					después: SimpleDateFormat fechaActual = new SimpleDateFormat("yyyyMMddHHmmssSS");
 * 
 * 				   Linea Eliminada: 
 *                  sentenciaSql += String.valueOf("'"+Math.random())+"'" + ")";
 *                  
 *                 **NOTA**
 *                  La tabla CR.auditoria, se adpató a este cambio.
 * 					Revisar el archivo DbCR_Logs.
 * =============================================================================
 * Versión     : 1.2
 * Fecha       : 31/01/2004 - 01:49:00 PM
 * Analista    : Víctor Medina <linux@epa.com.ve>
 * Descripción : La siguiente linea:
 *                  sentenciaSql += String.valueOf("'"+Math.random())+"'" + ")";
 *                  utiliza Math.random() para crear un número aleatorio que expande
 *                  y asegura la verdadera naturaleza unica de los keys de auditoria.
 *                
 *                  Existia un problema cuando se intentaba insertar registros a la auditoria
 *                  y los timestamps coincidian, mysql maneja los timestamps hasta el nivel de
 *                  segundos, si dos registros eran insertados en el mismo segundo(cosa bastante
 * 					usual) el primero de los registros eran procesado sin problema, pero el segundo
 * 					y uno tercero de error no porcedian por tener el mismo timestamp. 
 *                  
 *                  Una solucion a esto es agregar un numero al azaar al final del key, utilizando 
 * 					Math.random(). Aunque este metodo no es del todo a prueba de fallos, nos da un 
 * 					amplio margen de diferencia mientras implementamos una solucion mas eficiente, 
 * 					probablemente pudiesemos utilizar una clase getNumeroAlAzaar() en algun lugar del
 * 					codigo, probablemente en utiles, y alli utilizar java.util.random por ejemplo y 
 * 					controlar directamente el random-seed. Utilizando Math.random() reduje la posibilidad
 * 					de este mismo error, pero no es 100% segura en sistemas mas rapidos, y el error 
 * 					puede ocurrir otra vez, utilizar java.util.random nos aseeguraria una mejor solucion, 
 *                  mas elegante y segura. 
 *                
 *                  El beneficio; por otro lado de utilizar Math.random() es evidente, SOLO modifique 
 * 					una linea de la auditoria para que funcionara :) nice verdad? jejeje!
 *                
 *                  Lineas cambiadas 01  |  Lineas Agregadas 01
 *                
 *                  Linea Cambiada:
 *                   antes: sentenciaSql += codigoModulo+")";
 *                   despues: sentenciaSql += codigoModulo+",";
 *                
 *                  Linea Agregada: 
 *                   sentenciaSql += String.valueOf("'"+Math.random())+"'" + ")";
 *                  
 *                 **NOTA**
 *                  La tabla CR.auditoria, cambio tambien, se le agregó un campo extra
 *                  llamado "campoalazaar" y se modifico el PRIMARY KEY, para incluir el campo
 *                  nuevo. Revisar el changelog. 
 * =============================================================================
 */
package com.becoblohm.cr.mediadoresbd;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.manejadorauditoria.Auditoria;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.utiles.DOMSerializer;

/** 
 * Descripción: 
 * 		Maneja los accesos a la Base de Datos y a los archivos de texto para los registros de auditoria.
 */
public class MediadorAuditoria {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(MediadorAuditoria.class);

	/**
	 * Obtiene el nivel de auditoria de una función.
	 * @param codigoModulo Codigo del modulo.
	 * @param codFuncion Codigo de la funcion.
	 * @return int - Nivel de auditoria de la funcion.
	 * @throws BaseDeDatosExcepcion Si ocurre un error al acceder la Base de Datos.
	 * @throws FuncionExcepcion Si no se encuentra datos de la funcion en la base de datos.
	 */
	public static int obtenerNivelFuncion(int codigoModulo, int codFuncion) throws BaseDeDatosExcepcion, ConexionExcepcion, FuncionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerNivelFuncion(int, int) - start");
		}

		String sentenciaSql = "select funcion.nivelauditoria from funcion where funcion.codfuncion = " + codFuncion 
						+ " and funcion.codmodulo = " + codigoModulo;
		ResultSet resultado = Conexiones.realizarConsulta(sentenciaSql,true);
		try {
			if (resultado.first()) {
				int returnint = resultado.getInt(1);
				if (logger.isDebugEnabled()) {
					logger.debug("obtenerNivelFuncion(int, int) - end");
				}
				return returnint;
			} else {
				throw (new FuncionExcepcion("Funcion " + codFuncion + ". Modulo " + codigoModulo + " no encontrada en la Base de Datos", true));
			}
		} catch (SQLException e) {
			logger.error("obtenerNivelFuncion(int, int)", e);

			throw (new BaseDeDatosExcepcion("Error en la sentencia Sql: " + sentenciaSql.toLowerCase()));
		} finally {
			try {
				resultado.close();
			} catch (SQLException e1) {
				logger.error("obtenerNivelFuncion(int, int)", e1);
			}
			resultado = null;
		}
	}
	
	
	public static void registrarAuditoria(String numficha, int transaccion , String mensaje, 
				char tipoRegistro, int codigoModulo, int codFuncion, int nivelAuditoria)
	{
		if (logger.isDebugEnabled()) {
			logger
					.debug("registrarAuditoria(String, int, String, char, int, int, int) - start");
		}

		try {
			String sentenciaSql = "insert into auditoria (numtienda, numcaja,codusuario,tiporegistro,fecha,codmodulo,codfuncion,mensaje,nivelauditoria,regactualizado, numtransaccion) values (";
			sentenciaSql += Sesion.getTienda().getNumero() + ", ";
			sentenciaSql += Sesion.getCaja().getNumero() + ", ";
			sentenciaSql += "'" + numficha + "', ";
			sentenciaSql += "'" + tipoRegistro + "', '";
			SimpleDateFormat fechaActual = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String fechaActualString = fechaActual.format(Sesion.getFechaSistema());
			sentenciaSql += fechaActualString + "',";
			sentenciaSql += codigoModulo+",";
			sentenciaSql += codFuncion + ", ";
			sentenciaSql += "'" + escapear(mensaje) + "', ";
			sentenciaSql += nivelAuditoria + ", ";
			sentenciaSql += "'" + Sesion.NO + "', " + transaccion + " )";
			Conexiones.realizarSentencia(sentenciaSql,true);
			} catch (Exception e1) {
				logger
						.error(
								"registrarAuditoria(String, int, String, char, int, int, int)",
								e1);
			}

		if (logger.isDebugEnabled()) {
			logger
					.debug("registrarAuditoria(String, int, String, char, int, int, int) - end");
		}
	} 
	
	
	/**
	 * Registra un mensaje de auditoria en la Base de Datos.
	 * @param mensaje Mensaje del registro de auditoria.
	 * @param tipoRegistro Tipo de registro [A]uditoria, [T]ransaccional, [O]peracional, [E]rror.
	 * @param codigoModulo Codigo del Modulo.
	 * @param codFuncion Codigo de la Funcion.
	 * @param nivelAuditoria Nivel de Auditoria actual.
	 * @throws BaseDeDatosExcepcion Si ocurre un error con la Base de Datos.
	 */
	public static void registrarAuditoria(String mensaje, char tipoRegistro, int codigoModulo, int codFuncion,
											int nivelAuditoria, boolean autorizacion)/* throws BaseDeDatosExcepcion, ConexionExcepcion */{
		if (logger.isDebugEnabled()) {
			logger
					.debug("registrarAuditoria(String, char, int, int, int, boolean) - start");
		}

		try {
			String sentenciaSql = "insert into auditoria (numtienda, numcaja,codusuario,tiporegistro,fecha,codmodulo,codfuncion,mensaje,nivelauditoria,regactualizado) values (";
			sentenciaSql += Sesion.getTienda().getNumero() + ", ";
			sentenciaSql += Sesion.getCaja().getNumero() + ", ";
			if (autorizacion && Sesion.usuarioAutorizante != null) {
				sentenciaSql += "'" + Sesion.usuarioAutorizante.getNumFicha() + "', ";
			} else {
				sentenciaSql += "'" + Sesion.usuarioActivo.getNumFicha() + "', ";
			}
			sentenciaSql += "'" + tipoRegistro + "', '";
			SimpleDateFormat fechaActual = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String fechaActualString = fechaActual.format(Sesion.getFechaSistema());
	        sentenciaSql += fechaActualString + "',";
			sentenciaSql += codigoModulo+",";
			sentenciaSql += codFuncion + ", ";
			sentenciaSql += "'" + escapear(mensaje) + "', ";
			sentenciaSql += nivelAuditoria + ", ";
			sentenciaSql += "'" + Sesion.NO + "')";
	                
			Conexiones.realizarSentencia(sentenciaSql,true);
		} catch (Exception e1) {
			logger.error(
					"registrarAuditoria(String, char, int, int, int, boolean)",
					e1);
		}

		if (logger.isDebugEnabled()) {
			logger
					.debug("registrarAuditoria(String, char, int, int, int, boolean) - end");
		}
	} 

	
	public static void registrarAuditoria(String mensaje, char tipoRegistro, int codigoModulo, int codFuncion,
			int nivelAuditoria)/* throws BaseDeDatosExcepcion, ConexionExcepcion */{
		if (logger.isDebugEnabled()) {
			logger
					.debug("registrarAuditoria(String, char, int, int, int) - start");
		}

		registrarAuditoria(mensaje, tipoRegistro, codigoModulo, codFuncion, nivelAuditoria, false);

		if (logger.isDebugEnabled()) {
			logger
					.debug("registrarAuditoria(String, char, int, int, int) - end");
		}
	} 
	
	
	/**
	 * Metodo escapear.
	 * 		Sustituye el parámetro en una cadena con el caracter ' precedido del caracter \
	 * @param mensaje Mensaje a ser modificado
	 * @return String - Nueva cadena con la modificación
	 */
	private static String escapear(String mensaje) {
		if (logger.isDebugEnabled()) {
			logger.debug("escapear(String) - start");
		}

		String result = "";
		
		if (mensaje != null) {
			for (int i=0; i<mensaje.length(); i++) {
				if (mensaje.charAt(i) == '\'')
					result += "\\";
				result += mensaje.substring(i,i+1);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("escapear(String) - end");
		}
		return result;
	}

	/**
	 * Escribe en el archivo temporal los registro de auditoria.
	 * @param mensaje Mensaje del registro de auditoria.
	 * @param tipoRegistro Tipo de registro [A]uditoria, [T]ransaccional, [O]peracional, [E]rror.
	 * @param codigoModulo Codigo del Modulo.
	 * @param codFuncion Codigo de la Funcion.
	 * @param nivelAuditoria Nivel de Auditoria actual.
	 * @throws XmlExcepcion Si ocurre un error al tratar de escribir en el archivo de registro temporal.
	 */
	public static void escribirArchivoTemp(String mensaje, char tipoRegistro, int codigoModulo, int codFuncion,
											int nivelAuditoria, boolean manejarExcepcionesCr, boolean autorizacion) {
		if (logger.isDebugEnabled()) {
			logger
					.debug("escribirArchivoTemp(String, char, int, int, int, boolean, boolean) - start");
		}

		try {
			if (!existeTemporalXML()) {
				crearTemporalXML();
			}
			if (!existeTemporalTXT()) {
				crearTemporalTXT(); 
			}
			DocumentBuilderFactory xmlManager = DocumentBuilderFactory.newInstance();
			DocumentBuilder xmlActual = xmlManager.newDocumentBuilder();
			Document xmlDocument = xmlActual.newDocument();
			
			// Creamos el nuevo registro con los atributos de la Base de Datos
			Element registroNode = xmlDocument.createElement("Registro");
			registroNode.setAttribute("Tienda", (new Integer(Sesion.getTienda().getNumero())).toString());
			registroNode.setAttribute("Caja", (new Integer(Sesion.getCaja().getNumero())).toString());
			registroNode.setAttribute("Usuario", Sesion.getUsuarioActivo().getNumFicha());
			if (autorizacion && Sesion.usuarioAutorizante != null) {
				registroNode.setAttribute("Autorizante", Sesion.usuarioAutorizante.getNumFicha());
			}
			registroNode.setAttribute("TipoRegistro", "" + tipoRegistro);
			SimpleDateFormat fechaActual = new SimpleDateFormat("dd-MM-yyyy");
			registroNode.setAttribute("Fecha", fechaActual.format(Sesion.getFechaSistema()));
			fechaActual = new SimpleDateFormat("HH:mm:ss.SSS");
			registroNode.setAttribute("Hora", fechaActual.format(Sesion.getFechaSistema()));
			registroNode.setAttribute("Funcion", (new Integer(codFuncion)).toString());
			registroNode.setAttribute("Modulo", (new Integer(codigoModulo)).toString());
			registroNode.setAttribute("NivelAuditoriaActual", (new Integer(nivelAuditoria)).toString());
			
			// Agregamos el texto del mensaje
			registroNode.appendChild(xmlDocument.createTextNode(mensaje));
			
			// Escribimos el Xml en el Socket
			Auditoria.escribirSocket(registroNode);
			
			// Registramos el Xml en el archivo
			File texto = new File(InitCR.getPreferencia("sistema", "pathTemporal", "temp/") + "temporal.txt");
			OutputStreamWriter wtr = new OutputStreamWriter(new FileOutputStream(texto, true), "UTF-8");
			DOMSerializer serializer = new DOMSerializer();
			serializer.serializeNode(registroNode, wtr, "");
			
			wtr.close();
			
		} catch (Exception e) {
			logger
					.error(
							"escribirArchivoTemp(String, char, int, int, int, boolean, boolean)",
							e);

			/*if (manejarExcepcionesCr)
				throw (new XmlExcepcion("Error al escribir registro de auditoria temporal"));*/
		}

		if (logger.isDebugEnabled()) {
			logger
					.debug("escribirArchivoTemp(String, char, int, int, int, boolean, boolean) - end");
		}
	}
	
	public static void escribirArchivoTemp(String mensaje, char tipoRegistro, int codigoModulo, int codFuncion,
											int nivelAuditoria, boolean autorizacion) {
		if (logger.isDebugEnabled()) {
			logger
					.debug("escribirArchivoTemp(String, char, int, int, int, boolean) - start");
		}

		escribirArchivoTemp(mensaje,tipoRegistro,codigoModulo,codFuncion,nivelAuditoria,true, autorizacion);

		if (logger.isDebugEnabled()) {
			logger
					.debug("escribirArchivoTemp(String, char, int, int, int, boolean) - end");
		}
	}
	
	public static void escribirArchivoTemp(String mensaje, char tipoRegistro, int codigoModulo, int codFuncion,
			int nivelAuditoria) {
		if (logger.isDebugEnabled()) {
			logger
					.debug("escribirArchivoTemp(String, char, int, int, int) - start");
		}

		escribirArchivoTemp(mensaje,tipoRegistro,codigoModulo,codFuncion,nivelAuditoria,true, false);

		if (logger.isDebugEnabled()) {
			logger
					.debug("escribirArchivoTemp(String, char, int, int, int) - end");
		}
	}
	
	public static void crearTemporalXML() throws IOException {
		InputStream fin = Auditoria.class.getResourceAsStream("/com/becoblohm/cr/manejadorauditoria/plantilla.xml");
		File tempXML = new File(InitCR.getPreferencia("sistema", "pathTemporal", "temp/") + "Temporal.xml");
		tempXML.delete();
		tempXML.createNewFile();
		FileOutputStream fout = new FileOutputStream(tempXML);
		int aByte;
		while ((aByte = fin.read()) != -1)
			fout.write(aByte);
		fout.close();
		fin.close();
	}
	
	public static void crearTemporalTXT() throws Exception{
		File texto = new File(InitCR.getPreferencia("sistema", "pathTemporal", "temp/") + "temporal.txt");
		texto.delete();
		texto.createNewFile();

		// Creamos la parte del documento
		DocumentBuilderFactory xmlManager = DocumentBuilderFactory.newInstance();
		DocumentBuilder xmlActual;
		Element childNode = null;
		xmlActual = xmlManager.newDocumentBuilder();
		Document xmlDocument = xmlActual.newDocument();
		childNode = xmlDocument.createElement("Creado");
		SimpleDateFormat fechaCreacion = new SimpleDateFormat("yyyyMMdd");
		String creado = fechaCreacion.format(Sesion.getFechaSistema());
		childNode.setAttribute("fecha",creado);

		// Serializamos el nuevo elemento
		FileWriter wtr = new FileWriter(texto);
		DOMSerializer serializer = new DOMSerializer();
		serializer.serializeNode(childNode, wtr, "");
		wtr.close();
		
	}
	
	public static boolean existeTemporalTXT() {
		File texto = new File(InitCR.getPreferencia("sistema", "pathTemporal", "temp/") + "temporal.txt");
		return texto.exists();
	}
	
	public static boolean existeTemporalXML() {
		File xml = new File(InitCR.getPreferencia("sistema", "pathTemporal", "temp/") + "Temporal.xml");
		return xml.exists();
	}

}