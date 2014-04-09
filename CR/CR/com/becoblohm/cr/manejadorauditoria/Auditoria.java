/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.manejadorauditoria
 * Programa   : Auditoria.java
 * Creado por : apeinado
 * Creado en  : 03/11/2003 03:36:46 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.3.3
 * Fecha       : 18/03/2004 - 11:14 AM
 * Analista    : Gabriel Martinelli
 * Descripción : Se borra el Xml cada 7 días. Cada vez que se inicia la caja (Se llama a new Auditoria)
 * 				se chequea la fecha de creacion del archivo Xml y si han pasado mas de 7 dias desde su creacion 
 * 				se inicializa el archivo.
 * 				 Se creo el metodo crearArchivoXml que crea el archivo con el nodo de la fecha de creacion.
 * 				 Se creo el metodo contarDias que devuelve el numero de días "APROXIMADO" entre dos fechas 
 * =============================================================================
 * Versión     : 1.3.2
 * Fecha       : 02/03/2004 13:23 PM
 * Analista    : gmartinelli
 * Descripción : Arreglos en el manejo de Excepciones
 * =============================================================================
 * Versión     : 1.3.1
 * Fecha       : 02/03/2004 11:46 AM
 * Analista    : gmartinelli
 * Descripción : El metodo
 * 				- registrarAuditoria (String mensaje, char tipoRegistro, int codigoModulo, int codFuncion)
 * 				ha sido cambiado a "private" ya que la auditoria es la encargada de buscar el modulo y
 * 				la funcion en el metodo 
 * 				- registrarAuditoria (String mensaje, char tipoRegistro)
 * =============================================================================
 * Versión     : 1.3 (según CVS)
 * Fecha       : 11/02/2004 08:50:41 AM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Polimorfismo para el método registrarAuditoria.
 * 				 Parámetros eliminados ->
 * 					codModulo, codMetodo
 * =============================================================================
 */
package com.becoblohm.cr.manejadorauditoria;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.SesionExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.mediadoresbd.MediadorAuditoria;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.utiles.MensajesVentanas;

/** 
 * Descripción: 
 * 		Esta clase maneja todas las operaciones de registro de auditorias. Se manejan
 * los registros temporales (Archivos) y los registros permanentes filtrados por niveles
 * de auditoria (Base de Datos).
 */

public class Auditoria {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Auditoria.class);

	private static int nivelActual;
	private static AuditoriaRemota ar;
	
	/**
	 * Contructor para Auditoria
	 * 		Obtiene el nivel de auditoria de la Caja y lo establece como nivel
	 * actual. Ademas inicializa el mediador de auditoria para las conexiones de la DB.
	 * Por último elimina el registro temporal de auditoria creado en la última ejecucion
	 * de la Caja Registradora.
	 */
	public Auditoria () throws XmlExcepcion, BaseDeDatosExcepcion {
		nivelActual = Sesion.getNivelAuditoria();
		
		try {
			File archivoActual = new File(InitCR.preferenciasCR.getConfigStringForParameter("sistema", "pathtemporal")+"Temporal.xml");
			SimpleDateFormat fecha = new SimpleDateFormat("yyyyMMdd");
			double hoy = new Double(fecha.format(Sesion.getFechaSistema())).doubleValue();
			
			// Obtenemos el nodo con la fecha de creacion del archivo Xml
			if (archivoActual.exists()) {
				double creado;
				DocumentBuilderFactory xmlManager = DocumentBuilderFactory.newInstance();
				DocumentBuilder xmlActual = xmlManager.newDocumentBuilder();
				Document xmlDocument;
				try {
					xmlDocument = xmlActual.parse(archivoActual);
				} catch (Exception e) {
					logger.error("Auditoria()", e);

					crearArchivoXml(archivoActual);
				}
				
				xmlDocument = xmlActual.parse(archivoActual);
				NodeList nodes = xmlDocument.getElementsByTagName("Creado");
				if (nodes.getLength()>0) {
					Element node = (Element)nodes.item(0);
					String atributo = node.getAttribute("fecha");
					creado = (new Double(atributo)).doubleValue();
					int diasAuditados = contarDias(creado,hoy);
					if (diasAuditados >= 7)
						crearArchivoXml(archivoActual,xmlDocument,creado);
				} else {
					crearArchivoXml(archivoActual);
				}
			} else {
				crearArchivoXml(archivoActual);
			}
		} catch (Exception e) {
			logger.error("Auditoria()", e);

			throw (new XmlExcepcion("Error al incializar archivo de auditoria temporal", e));
		}
		
		// Abrimos una conexion por Socket para la auditoria remota
		try {
			ar = new AuditoriaRemota();
		} catch (SesionExcepcion e1) {
			logger.error("Auditoria()", e1);
		}
	}
	
	/**
	 * Método contarDias
	 * 
	 * @param creado
	 * @param hoy
	 * @return int
	 */
	private int contarDias(double creado, double hoy) {
		if (logger.isDebugEnabled()) {
			logger.debug("contarDias(double, double) - start");
		}

		int diasMes[] = {31,28,31,30,31,30,31,31,30,31,30,31};
		int diaHoy = (int)(hoy%100); int diaCreado = (int) (creado%100);
		int mesHoy = (int) ((hoy/100)%100); int mesCreado = (int) ((creado/100)%100);
		int anioHoy = (int) (hoy/10000); int anioCreado = (int) (creado/10000);
		int dias = 0;
		
		while (anioHoy > anioCreado) {
			dias += 360;
			anioHoy--;
		}
		while (mesHoy > mesCreado) {
			dias += diasMes[mesHoy-1];
			mesHoy--;
		}
		dias += (diaHoy - diaCreado);

		if (logger.isDebugEnabled()) {
			logger.debug("contarDias(double, double) - end");
		}
		return dias;
	}

	/**
	 * Método crearArchivoXml
	 * 
	 * @param archivoActual
	 * @throws Exception
	 */
	private void crearArchivoXml(File archivoActual) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("crearArchivoXml(File) - start");
		}
		
		// Inicializamos el contenido del archivo Xml
		try {
			MediadorAuditoria.crearTemporalXML();
			MediadorAuditoria.crearTemporalTXT();
		} catch (FileNotFoundException e) {
			logger.error("crearArchivoXml(File)", e);
			
		} catch (IOException e) {
			logger.error("crearArchivoXml(File)", e);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("crearArchivoXml(File) - end");
		}
	}

	/**
	 * Método crearArchivoXml
	 * 
	 * @param archivoActual
	 * @param d
	 * @param creacion
	 * @throws Exception
	 */
	private void crearArchivoXml(File archivoActual, Document d, double creacion) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("crearArchivoXml(File, Document, double) - start");
		}
		
		// Creamos un Backup del Archivo anterior.
		// Inicializamos el contenido del archivo Xml
		DecimalFormat df = new DecimalFormat("00000000");
		File archivo = new File(InitCR.preferenciasCR.getConfigStringForParameter("sistema", "pathtemporal")+"Backup" + df.format(creacion) + ".xml");
		Source source = new DOMSource(d);
		Result result = new StreamResult(archivo);
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.transform(source, result);

		// Creamos el nuevo archivo Xml
		MediadorAuditoria.crearTemporalTXT();
		if (logger.isDebugEnabled()) {
			logger.debug("crearArchivoXml(File, Document, double) - end");
		}
	}

	/**
	 * Metodo verificarNivelAuditoria.
	 * 		Verifica si se debe realizar un cambio en el nivel de auditoria y si se debe
	 * insertar el registro de auditoria en la BD de acuerdo al nivel de la Caja y del usuario
	 * (Si existe)
	 * @param codigoModulo Codigo del modulo que llama al registro de auditoria.
	 * @param codigoFuncion Codigo de la funcion que está siendo ejecutada.
	 * @return boolean - Indicador de si se debe o no registrar en la tabla de auditoria.
	 * @throws BaseDeDatosExcepcion Si ocurre un error al escribir los registros de auditoria en la BD
	 * @throws FuncionExcepcion Si no se encuentran los datos de la funcion en la Base de Datos
	 * @throws XmlExcepcion Si ocurre un error al escribir los registros de auditoria temporal
	 */
	private static boolean verificarNivelAuditoria(int codigoModulo, int codigoFuncion) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("verificarNivelAuditoria(int, int) - start");
		} 
	
		// Obtenemos el Nivel de Auditoria de la Función, buscado por el Mediador de Auditoria, TABLA FUNCIÓN 
		int nivelAudFuncion = MediadorAuditoria.obtenerNivelFuncion(codigoModulo,codigoFuncion);
	
		// Obtenemos el Nivel de Auditoria de la Caja 
		int nivelAudCaja = Sesion.getNivelAuditoria();
	
    	// Seteamos el nivel de Auditoria del Usuario, Se realiza por la sencilla razón que pueden requerir la llamada de
    	// auditoria sin que haya un Usuario Activo en la Cr.
		// Obtenemos el Nivel de Audtoria del usuario de la Caja
		int nivelAudUsuario = (Sesion.usuarioActivo.getNivelAuditoria() != null)
					? new Integer(Sesion.usuarioActivo.getNivelAuditoria()).intValue()
					: 5;
		
		// Obtenemos el nivel de auditoria menor entre Caja y Usuario para asignarlo como nivel actual
		int nivelMenor = (nivelAudUsuario < nivelAudCaja)
					? nivelAudUsuario
					: nivelAudCaja;
					
		// Solo el Perfil de Usuario puede cambiar el NivelActual
		if (nivelMenor != nivelActual) { 
			// Cambiamos el Nivel de Auditoria
			nivelActual= nivelMenor;
			
			// Se Registra el Cambio de Nivel de Auditoria, con Tipo de Registro "A" 
			String mensaje="Se ha cambiado el Nivel de Auditoria a: " + nivelActual;
	   
			// Escribe el Registro en el Temporal 
			MediadorAuditoria.escribirArchivoTemp(mensaje,'A',codigoModulo, codigoFuncion,nivelActual);
	   
			// Escribe el Registro en la tabla AUDITORIA 
			MediadorAuditoria.registrarAuditoria(mensaje,'A',codigoModulo, codigoFuncion,nivelActual);
		}
	 
		// Verifica el Nivel de Auditoria de la Función, para escribir el registro Filtrado   	
		if ((6 - nivelAudFuncion) >= nivelActual)
		{
			if (logger.isDebugEnabled()) {
				logger.debug("verificarNivelAuditoria(int, int) - end");
			} 
			return true;
		}
		else
		{
			if (logger.isDebugEnabled()) {
				logger.debug("verificarNivelAuditoria(int, int) - end");
			} 
			return false;
		}
	}

	/**
	 * Metodo registrarAuditoria.
	 * 		Registra los mensajes de auditoria en la Base de Datos y en el archivo temporal.
	 * @param mensaje Mensaje del registro de auditoria.
	 * @param tipoRegistro Tipo de registro [A]uditoria, [T]ransaccional, [O]peracional, [E]rror.
	 * @param codigoModulo Código del módulo que se está ejecutando.
	 * @param codFuncion Código de la función que se está ejecutando
	 * @throws XmlExcepcion Si ocurre un error al escribir los registros de auditoria temporal
	 * @throws BaseDeDatosExcepcion Si ocurre un error al escribir los registros de auditoria en la BD
	 * @throws FuncionExcepcion Si no se encuentran los datos de la funcion en la Base de Datos
	 */
	private static void registrarAuditoria (String mensaje, char tipoRegistro, int codigoModulo, int codFuncion, boolean autorizacion) {
		if (logger.isDebugEnabled()) {
			logger
					.debug("registrarAuditoria(String, char, int, int, boolean) - start");
		}
 
		try {
			// Escribe el Registro en el Temporal 
			MediadorAuditoria.escribirArchivoTemp(mensaje, tipoRegistro, codigoModulo, codFuncion, nivelActual, autorizacion);
			
			//Primero verificamos si el nivel de auditoría de la función cambia el nivel actual
			boolean verifica = verificarNivelAuditoria (codigoModulo, codFuncion);
			
			// Si el Nivel de Auditoria Actual ha sido cambiado por el chequeo del Metodo verificarNivelAuditoria se Registra es nuestra tabla de Auditoria   
			if (verifica)
				MediadorAuditoria.registrarAuditoria(mensaje, tipoRegistro, codigoModulo, codFuncion, nivelActual, autorizacion);
		} catch (Exception e1) {
			logger.error("registrarAuditoria(String, char, int, int, boolean)",
					e1);
		}
			
		if (logger.isDebugEnabled()) {
			logger
					.debug("registrarAuditoria(String, char, int, int, boolean) - end");
		}
	}

	/**
	 * Método registrarAuditoria.
	 * 	Método alternativo que permite registrar la auditoría de un proceso sin necesidad de invocar
	 * al método obtenerMetModFun en cada clase que registre una auditoría.
	 * @param mensaje Mensaje del registro de auditoria.
	 * @param tipoRegistro Tipo de registro [A]uditoria, [T]ransaccional, [O]peracional, [E]rror.
	 * @throws XmlExcepcion Si ocurre un error al escribir los registros de auditoria temporal
	 * @throws FuncionExcepcion Si no se encuentran los datos de la funcion en la Base de Datos
	 * @throws BaseDeDatosExcepcion Si ocurre un error al escribir los registros de auditoria en la BD
	 */
	public static void registrarAuditoria (String mensaje, char tipoRegistro) {
		if (logger.isDebugEnabled()) {
			logger.debug("registrarAuditoria(String, char) - start");
		}
 
		registrarAuditoria(mensaje, tipoRegistro, false);

		if (logger.isDebugEnabled()) {
			logger.debug("registrarAuditoria(String, char) - end");
		}
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static void registrarAuditoria (String mensaje, char tipoRegistro, boolean autorizacion) {
		if (logger.isDebugEnabled()) {
			logger.debug("registrarAuditoria(String, char, boolean) - start");
		}

		try {
			Vector<Integer> ubicacion;
			ubicacion =	MediadorBD.obtenerMetModFun();
			int codigoModulo = ((Integer)ubicacion.elementAt(1)).intValue();
			int codFuncion = ((Integer)ubicacion.elementAt(2)).intValue();
				
			registrarAuditoria(mensaje, tipoRegistro, codigoModulo, codFuncion, autorizacion);
		} catch (Exception e) {
			logger.error("registrarAuditoria(String, char, boolean)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("registrarAuditoria(String, char, boolean) - end");
		}
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static void registrarAuditoria(String numficha, int transaccion, String mensaje, String modulo, String metodo) {
		if (logger.isDebugEnabled()) {
			logger
					.debug("registrarAuditoria(String, int, String, String, String) - start");
		}

		//TODO: Auditoria paralela. Debe integrarse a la auditoria del sistema 
		String modAnt = Sesion.getModulo();
		String metAnt = Sesion.getMetodo();
		Sesion.setUbicacion(modulo, metodo);
		Vector<Integer> ubicacion;
		try {
			ubicacion =	MediadorBD.obtenerMetModFun();
			int codigoModulo = ((Integer)ubicacion.elementAt(1)).intValue();
			int codFuncion = ((Integer)ubicacion.elementAt(2)).intValue();
			MediadorAuditoria.registrarAuditoria(numficha, transaccion , mensaje, 'T', codigoModulo, codFuncion, 1);
//			MediadorAuditoria.escribirArchivoTemp(mensaje, 'T', codigoModulo, codFuncion, nivelActual, true);
		} catch (Exception e) {
			MensajesVentanas.aviso("No se pudo registrar la auditoria del sistema: Modulo: " + modulo + " Metodo: " + metodo);
			logger.error(
					"registrarAuditoria(String, int, String, String, String)",
					e);
		} finally {
			Sesion.setUbicacion(modAnt, metAnt);
		}
		
		if (logger.isDebugEnabled()) {
			logger
					.debug("registrarAuditoria(String, int, String, String, String) - end");
		}
	}
	
	/**
	 * Método escribirSocket
	 * 
	 * @param registro
	 */
	public static void escribirSocket(Element registro) {
		if (logger.isDebugEnabled()) {
			logger.debug("escribirSocket(Element) - start");
		}

		ar.escribirSocket(registro);

		if (logger.isDebugEnabled()) {
			logger.debug("escribirSocket(Element) - end");
		}
	}
}