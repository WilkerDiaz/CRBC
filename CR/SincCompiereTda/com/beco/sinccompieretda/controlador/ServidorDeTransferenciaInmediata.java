
package com.beco.sinccompieretda.controlador;

import java.io.ByteArrayOutputStream;
import java.net.ServerSocket;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.beco.sinccompieretda.red.ComunicarConCliente;

public class ServidorDeTransferenciaInmediata {
	private static int puerto = 35882;
	public static void main(String args[]){
		ProcesarSincronizacion.crearVector();
		new ProcesarSincronizacion().start();
		ServerSocket sock = null; 
		try{
			sock= new ServerSocket(puerto);
		}catch(Exception e){
			System.out.println("No se pudo crear el servidor en el puerto "+puerto+": "+e.getMessage());
		}
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setCoalescing(true);
        dbf.setExpandEntityReferences(true);
        dbf.setIgnoringComments(true);
        dbf.setIgnoringElementContentWhitespace(true);
        ComunicarConCliente com = null;
        System.out.println("Servidor esperando peticiones por el puerto: "+puerto);
		while(true){
			try{
				com = new ComunicarConCliente(sock.accept());
				System.out.println("Peticion recibida! xml:");
				Document xml = com.obtenerXml();
				imprimirDocumento(xml);
				ProcesarSincronizacion.agregarPeticion(xml);
		        DocumentBuilder db = dbf.newDocumentBuilder();
				xml = db.newDocument();
				Element sincronizador = xml.createElement("SincronizacionInmediata");
				Element operacionExitosa = xml.createElement("operacionExitosa");
				sincronizador.appendChild(operacionExitosa);
				xml.appendChild(sincronizador);
				com.enviarXml(xml);
				com.cerrarConexion();
			}catch(Exception e){
			    System.out.println("No se pudo agregar una nueva petición: "+e.getMessage());
			    try{
			        DocumentBuilder db = dbf.newDocumentBuilder();
			        Document xml = db.newDocument();
					Element sincronizador = xml.createElement("SincronizacionInmediata");
					Element error = xml.createElement("error");
					error.setTextContent("No se pudo agregar una nueva petición: "+e.getMessage());
					sincronizador.appendChild(error);
			    	com.enviarXml(xml);
			    }catch(Exception e2){}
			}
		}
	}/*
	 * Imprime un documento en la salida estandar
	 */
	public static void imprimirDocumento(Document doc){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			transformer.transform(new DOMSource(doc), new StreamResult(baos));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String xml = baos.toString();
		System.out.println("Xml:\n"+xml);
		
	}
}
