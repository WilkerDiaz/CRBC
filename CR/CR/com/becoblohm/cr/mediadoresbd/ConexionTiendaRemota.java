/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.gui.listaregalos
 * Programa   : ConexionTiendaRemota.java
 * Creado por : rabreu
 * Creado en  : 03/09/2006
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
package com.becoblohm.cr.mediadoresbd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import net.n3.nanoxml.IXMLElement;
import net.n3.nanoxml.IXMLParser;
import net.n3.nanoxml.StdXMLReader;
import net.n3.nanoxml.XMLElement;
import net.n3.nanoxml.XMLException;
import net.n3.nanoxml.XMLParserFactory;
import net.n3.nanoxml.XMLWriter;

import com.becoblohm.cr.excepciones.ConexionExcepcion;

/** 
 * 		Maneja las operaciones de conexiones a las base de datos, tanto . Funciona 
 * como mediador entre la aplicacion y la Base de Datos. Ejecuta sentencias 
 * simples y sentencias preparadas para actualización de tablas en la base 
 * de datos. También proporciona métodos para inicialización de la conexión 
 * conexion la base de datos y para el control de cada una de sus propiedades.
 */
public class ConexionTiendaRemota {
	
	private static Socket socket;
	private static ObjectInputStream entradaXML;
	private static ObjectOutputStream salidaXML;
		
	/**
	 * Constructor para Conexiones.
	 * 		Establece los parámetros (clase, url, usuario, clave) para 
	 * conexión a la base de datos tanto local como del servidor central de
	 * la tienda.
	 */
	private static void conectar(String ip) throws ConexionExcepcion{
		try {
			InetAddress direccion = InetAddress.getByName(ip);
			int puerto = 5000;
			SocketAddress direccionSocket = new InetSocketAddress(direccion, puerto);
			socket = new Socket();
			socket.connect(direccionSocket, 10000);
			salidaXML = new ObjectOutputStream(socket.getOutputStream());
			entradaXML = new ObjectInputStream(socket.getInputStream());
		} catch (UnknownHostException e) {
			throw new ConexionExcepcion("Servidor de tienda no encontrado");
		} catch (SocketTimeoutException e) {
			throw new ConexionExcepcion("Error conectando con\nservidor de tienda remoto");
		} catch (IOException e) {
			throw new ConexionExcepcion("Error comunicandose con\nservidor de tienda remoto");
		}
	}
	
	private static void desconectar(){
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static IXMLElement realizarConsulta(IXMLElement solicitud, String ipTienda) throws ConexionExcepcion {
		IXMLElement resultado = new XMLElement("resultado");
		String xmlString;
		StringWriter sw = new StringWriter();
		
		// Si la conexion no esta activa la creamos
		try {
			conectar(ipTienda);
				
			XMLWriter xmlwriter = new XMLWriter(sw);
			xmlwriter.write(solicitud);
			xmlString = new String(sw.getBuffer());	
			salidaXML.writeObject(xmlString);
			
			IXMLParser parser = XMLParserFactory.createDefaultXMLParser();
			parser.setReader(StdXMLReader.stringReader((String)entradaXML.readObject()));
			resultado = (IXMLElement)parser.parse();
			if(resultado.getFullName().equals("error")){
				throw new ConexionExcepcion("Error en servidor: "+resultado.getContent());
			}
			
			desconectar();
		} catch (IOException e) {
			throw new ConexionExcepcion("Error comunicandose con servidor remoto");
		} catch (ClassNotFoundException e) {
			throw new ConexionExcepcion("Error procesando solicitud");
		} catch (InstantiationException e) {
			throw new ConexionExcepcion("Error procesando solicitud");
		} catch (IllegalAccessException e) {
			throw new ConexionExcepcion("Error procesando solicitud");
		} catch (XMLException e) {
			throw new ConexionExcepcion("Error xml procesando solicitud");
		}
		return resultado;
	}
	
	public static void ejecutarSentencia(IXMLElement solicitud, String ipTienda) throws ConexionExcepcion {
		IXMLElement resultado = new XMLElement("resultado");
		String xmlString;
		StringWriter sw = new StringWriter();

		try {
			conectar(ipTienda);
			
			XMLWriter xmlwriter = new XMLWriter(sw);
			xmlwriter.write(solicitud);
			xmlString = new String(sw.getBuffer());
			salidaXML.writeObject(xmlString);
			
			IXMLParser parser = XMLParserFactory.createDefaultXMLParser();
			parser.setReader(StdXMLReader.stringReader((String)entradaXML.readObject()));
			resultado = (IXMLElement)parser.parse();
			if(resultado.getFullName().equals("error")){
				throw new ConexionExcepcion("Error en servidor: "+resultado.getContent());
			}
			
			desconectar();
		} catch (IOException e) {
			throw new ConexionExcepcion("Error comunicandose con servidor remoto");
		} catch (ClassNotFoundException e) {
			throw new ConexionExcepcion("Error procesando solicitud");
		} catch (InstantiationException e) {
			throw new ConexionExcepcion("Error procesando solicitud");
		} catch (IllegalAccessException e) {
			throw new ConexionExcepcion("Error procesando solicitud");
		} catch (XMLException e) {
			throw new ConexionExcepcion("Error xml procesando solicitud");
		}
	}
		
	public static int ejecutarSentenciaNumTransaccion(IXMLElement solicitud, String ipTienda) throws ConexionExcepcion {
		IXMLElement resultado = new XMLElement("resultado");
		String xmlString;
		StringWriter sw = new StringWriter();

		try {
			conectar(ipTienda);
		
			XMLWriter xmlwriter = new XMLWriter(sw);
			xmlwriter.write(solicitud);
			xmlString = new String(sw.getBuffer());	
			salidaXML.writeObject(xmlString);
		
			IXMLParser parser = XMLParserFactory.createDefaultXMLParser();
			parser.setReader(StdXMLReader.stringReader((String)entradaXML.readObject()));
			resultado = (IXMLElement)parser.parse();
			if(resultado.getFullName().equals("error")){
				throw new ConexionExcepcion("Error en servidor: "+resultado.getContent());
			}
			IXMLElement numtransaccion = resultado.getFirstChildNamed("numtransaccion");
			int respuesta = Integer.parseInt(numtransaccion.getContent());
			desconectar();
			return respuesta;
		} catch (IOException e) {
			throw new ConexionExcepcion("Error comunicandose con servidor remoto");
		} catch (ClassNotFoundException e) {
			throw new ConexionExcepcion("Error procesando solicitud");
		} catch (InstantiationException e) {
			throw new ConexionExcepcion("Error procesando solicitud");
		} catch (IllegalAccessException e) {
			throw new ConexionExcepcion("Error procesando solicitud");
		} catch (XMLException e) {
			throw new ConexionExcepcion("Error xml procesando solicitud");
		}

	}
}