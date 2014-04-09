/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.cr.pda
 * Programa   : HiloControl.java
 * Creado por : irojas
 * Creado en  : 10/08/2009 - 07:57:19 PM
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
package com.beco.cr.pda;

import java.lang.Thread;
import java.net.Socket;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.beco.sistemas.aplicacionespda.servidordetienda.Excepciones.ErrorEnCreacion;
import com.beco.sistemas.aplicacionespda.servidordetienda.red.ComunicarConCliente;
import com.becoblohm.cr.extensiones.PDAFactory;

/**
 * @author irojas
 *
 */
public class HiloControl extends Thread {
	/**
	 * Logger for this class
	 */ 
	private static final Logger logger = Logger.getLogger(HiloControl.class);
	private ComunicarConCliente comunicador;
	
	public HiloControl(Socket s) {
		if (logger.isDebugEnabled()) {
			logger.debug("HiloControl() - start");
		}
		
		try {
			comunicador = new ComunicarConCliente(s);
		} catch (ErrorEnCreacion e) {
			logger.error("HiloControl()", e);
		}
	}
	
	public void run() {
		if (logger.isDebugEnabled()) {
			logger.debug("HiloControl(), run()- start");
		}
		try {
			//*****Se captura la peticion del servidor PDA
			Document peticion = comunicador.obtenerXmlDelCliente();
			imprimirDocumento(peticion);
			//1) Se llama al traductor de angela para que interprete el Document
			MensajePDA mensaje = Traductor.traducir(peticion);
			//2) Se hacen las llamadas a CR
			(new PDAFactory()).getPDAInstance().consultaPDA(mensaje);
			//3) Llamar al traductor de angela para que transforme la respuesta a un Document XML
			Document documento = Traductor.traducir(mensaje);
			if(documento == null){
				throw new Exception("No se pudo generar una respuesta para esa operación");
			}
			//4) Se envía el XML de respuesta de CR
			imprimirDocumento(documento);
			comunicador.enviarXml(documento);
		} catch (Exception e) {
			try{//Se intenta informar al cliente del error...
				DocumentBuilderFactory docbuildfact = DocumentBuilderFactory.newInstance();
				docbuildfact.setNamespaceAware(true);
				docbuildfact.setCoalescing(true);
				docbuildfact.setExpandEntityReferences(true);
				docbuildfact.setIgnoringComments(true);
				docbuildfact.setIgnoringElementContentWhitespace(true);
				DocumentBuilder docbuild = docbuildfact.newDocumentBuilder();
				Document resp = docbuild.newDocument();
				Element nodo = resp.createElement("Operacion");
				Element error = resp.createElement("error");
				error.setTextContent("No se pudo ejecutar la opearcion en la caja: "+e.getMessage());
				nodo.appendChild(error);
				resp.appendChild(nodo);
				comunicador.enviarXml(resp);
			}catch(Exception e2){}			
			logger.error("HiloControl(), run()", e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Método convertirXml
	 * @author irojas
	 * @param root
	 * @return String
	 */
	public static String convertirXml(Node root){
		if(root.getNodeType() == Node.TEXT_NODE){
			return root.getNodeValue();
		}else if(root.getNodeType() == Node.ELEMENT_NODE){
			NodeList hijos = root.getChildNodes();
			String arbol = new String("<"+root.getNodeName()+">");
			for(int i = 0; i < hijos.getLength();++i){
				arbol = arbol.concat(convertirXml(hijos.item(i)));
			}
			arbol = arbol.concat("</"+root.getNodeName()+">");
			return arbol;
		}else{
			return "";
		}
	}

	/**
	 * Método convertirXml
	 * @author irojas
	 * @param xml
	 * @return String
	 */
	public static String convertirXml(Document xml){
		return(convertirXml(xml.getFirstChild()));
	}

	/**
	 * Método imprimirDocumento
	 * @author irojas
	 * @param doc 
	 * @return void
	 */
	public static void imprimirDocumento(Document doc){
		String xml = convertirXml(doc);
		System.out.println("Xml:\n"+xml);
	}

}
