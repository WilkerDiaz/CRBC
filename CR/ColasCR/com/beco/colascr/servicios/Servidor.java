/**
 * =============================================================================
 * Proyecto   : ColasCR
 * Paquete    : com.beco.colascr.servicios
 * Programa   : Servidor.java
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
package com.beco.colascr.servicios;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;

import net.n3.nanoxml.IXMLElement;
import net.n3.nanoxml.IXMLParser;
import net.n3.nanoxml.StdXMLReader;
import net.n3.nanoxml.XMLElement;
import net.n3.nanoxml.XMLParserFactory;
import net.n3.nanoxml.XMLWriter;

import com.beco.colascr.servicios.mediadorbd.MediadorBD;

/**
 * @author rabreu
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class Servidor {
	private static ServerSocket socket;
	private Socket cliente;
	private static int puerto = 5000;
	private boolean activo = true;

	public Servidor(){
		try {
			new Sesion();
			socket = new ServerSocket(puerto);
			while(activo){
				cliente = socket.accept();
				//cliente.setSoLinger(true, 10);
				//System.out.println("Conexion establecida con "+cliente.getInetAddress());
				Runnable atenderPeticion = new atenderPeticion(cliente);
				new Thread(atenderPeticion).start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void detener(){
		this.activo = false;
	}
	
	public static void main(String[] args) {
		new Servidor();
	}
}

class atenderPeticion implements Runnable {
	
	private Socket socket;
	private ObjectInputStream entrada; 
	private ObjectOutputStream salida; 
	
	public atenderPeticion(Socket socket){
		this.socket = socket;
	}
	
	public void run() {
		StringWriter sw = new StringWriter();

		try {
			entrada = new ObjectInputStream(socket.getInputStream());
			salida = new ObjectOutputStream(socket.getOutputStream());
			IXMLParser parser = XMLParserFactory.createDefaultXMLParser();
			String stringRecv = (String)entrada.readObject();
			parser.setReader(StdXMLReader.stringReader(stringRecv));
			IXMLElement solicitud = (IXMLElement)parser.parse();

			//XMLWriter writer = new XMLWriter(System.out);
			//writer.write(solicitud);
			
			if (solicitud.getFullName().equals("listaregalos")) {
				String tipo = solicitud.getAttribute("tipo",null);
				if(tipo.equals("solicitud")){
					System.out.println("Solicitud de lista de regalos desde "+socket.getInetAddress().getHostAddress());
					IXMLElement codigo = solicitud.getFirstChildNamed("codigo");
					String codLista = codigo.getContent();

					IXMLElement listaXML = MediadorBD.obtenerLista(codLista);
					XMLWriter writer = new XMLWriter(sw);
					writer.write(listaXML);
					String xmlString = new String(sw.getBuffer());
					salida.writeObject("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>"+xmlString);
				}else if(tipo.equals("modificarencabezado")){
					System.out.println("Modificacion del encabezado de lista de regalos desde "+socket.getInetAddress().getHostAddress());
					MediadorBD.modificarEncabezadoLista(solicitud);
					
					IXMLElement listo = new XMLElement("listo");
					XMLWriter writer = new XMLWriter(sw);
					writer.write(listo);
					String listoxml = new String(sw.getBuffer());
					salida.writeObject(listoxml);
				}else if(tipo.equals("modificardetalles")){
					System.out.println("Modificacion del detalle de lista de regalos desde "+socket.getInetAddress().getHostAddress());
					MediadorBD.modificarDetallesLista(solicitud);
						
					IXMLElement listo = new XMLElement("listo");
					XMLWriter writer = new XMLWriter(sw);
					writer.write(listo);
					String listoxml = new String(sw.getBuffer());
					salida.writeObject(listoxml);
				}else if(tipo.equals("abono")){
					System.out.println("Abono a lista de regalos desde "+socket.getInetAddress().getHostAddress());
					int numtransaccion = MediadorBD.registrarAbonoLR(solicitud);
					
					IXMLElement listo = new XMLElement("listo");
					IXMLElement numtrans = listo.createElement("numtransaccion");
					listo.addChild(numtrans);
					numtrans.setContent(String.valueOf(numtransaccion));
					XMLWriter writer = new XMLWriter(sw);
					writer.write(listo);
					String listoxml = new String(sw.getBuffer());
					salida.writeObject(listoxml);
				}else if(tipo.equals("venta")){
					System.out.println("Venta de lista de regalos desde "+socket.getInetAddress().getHostAddress());
					MediadorBD.registrarVentaLR(solicitud);
					
					IXMLElement listo = new XMLElement("listo");
					XMLWriter writer = new XMLWriter(sw);
					writer.write(listo);
					String listoxml = new String(sw.getBuffer());
					salida.writeObject(listoxml);
				}else if(tipo.equals("cierre")){
					System.out.println("Cierre de lista de regalos desde "+socket.getInetAddress().getHostAddress());
					MediadorBD.registrarCierreLR(solicitud);
	
					IXMLElement listo = new XMLElement("listo");
					XMLWriter writer = new XMLWriter(sw);
					writer.write(listo);
					String listoxml = new String(sw.getBuffer());
					salida.writeObject(listoxml);
				}
			} else if(solicitud.getFullName().equals("afiliado")) {
				String tipo = solicitud.getAttribute("tipo",null);
				if(tipo.equals("solicitud")){
					System.out.println("Sincronizacion de afiliado desde "+socket.getInetAddress().getHostAddress());
					IXMLElement codigo = solicitud.getFirstChildNamed("codafiliado");
					String codafiliado = codigo.getContent();
		
					IXMLElement listaXML = MediadorBD.obtenerAfiliado(codafiliado);
					XMLWriter writer = new XMLWriter(sw);
					writer.write(listaXML);
					String xmlString = new String(sw.getBuffer());
					salida.writeObject(xmlString);
				}
			}
  			entrada.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				IXMLElement error = new XMLElement("error");
				error.setContent(" "+e.getMessage());
				XMLWriter writer = new XMLWriter(sw);
				writer.write(error);
				String errorxml = new String(sw.getBuffer());
				salida.writeObject(errorxml);
			} catch (IOException e1) {}
			e.printStackTrace();
		}
	}
}
