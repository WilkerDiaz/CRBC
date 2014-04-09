/**
 * =============================================================================
 * Proyecto   : AplicacionesPDACliente
 * Paquete    : com.beco.sistemas.aplicacionespda.cliente.red
 * Programa   : ComunicarConServidor.java
 * Creado por : Marcos Grillo
 * Creado en  : 28/05/2009 - 14:26:30
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

package com.becoblohm.cr.utiles;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
/*import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
*/
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.becoblohm.cr.excepciones.*;
import com.becoblohm.cr.manejadorsesion.Sesion;
/**
 * 
 *	Esta clase maneja todo lo referente a la comunicación con el servidor.
 * @author Marcos Grillo
 * @version 1.0
 */
public class ComunicarConServidor{
    private OutputStream salida;
    private InputStream entrada;
    private Socket com;
    private int puerto;
    private String ipServidor;
    
    /**
     * Constructor de la clase, establece los parámetros iniciales y establece
     * un tiempo de espera para la conexión.
     * @param ipServidor contiene el ip del servidor al que se va a conectar,
     * 	si se pasa un string vacio, se asume que el servidor está corriendo 
     * localmente.
     * @param puerto contiene el puerto con que se conectará la aplicación
     * si el número no es válido, se coloca por defecto el valor 35992.
     * @throws ErrorEnCreacion Indica que no se pudo crear una conexión
     */
    public ComunicarConServidor(String ipServidor, int puerto)
    						throws ErrorEnCreacion{
    	if(puerto < 1 || puerto > 65536)
    	{
    		this.puerto = 35992;
    	}else{
    		this.puerto = puerto;
    	}
    	if(ipServidor.equals(""))
    	{
    		this.ipServidor = new String("localhost");
    	}else{
    		this.ipServidor = new String(ipServidor);
    	}
    	try{
    		com = new Socket(this.ipServidor, this.puerto);
    		entrada = com.getInputStream();
    		salida = com.getOutputStream();
    		com.setSoTimeout(Sesion.tiempoTimeOut);
    	} catch (UnknownHostException e) {
    		throw new ErrorEnCreacion("Dirección del servidor desconocida");
    		//throw new ErrorEnCreacion("***No se puede encontrar el servidor, "
    		//						+e.getMessage()+"***");
    	} catch (IOException e) {
    		throw new ErrorEnCreacion("No se pudo conectar con el servidor");
    		//throw new ErrorEnCreacion("***No se pudo crear la conexion, "
    		//						+e.getMessage()+"***");
    	}
    }

	/**
	 * Envía un mensaje al pda
	 * @param mensaje contiene el mensaje que se enviará al PDA
	 * @throws ErrorDeEscritura Si ocurre algun error con el buffer de salida
	 */
	public void enviarMensaje(byte[] mensaje) throws ErrorDeEscritura{
		try {
			salida.write(mensaje);
			salida.flush();
		} catch (IOException e) {
			throw new ErrorDeEscritura("***No se pudo enviar el mensaje "
									+e.getMessage()+"***");
		}
	}

	/**
	 * Envía lo que este en el buffer al servidor
	 * @throws ErrorDeEscritura Si ocurre algun error con el buffer de salida
	 */
	public void enviarMensaje() throws ErrorDeEscritura{
		try {
			salida.flush();
		} catch (IOException e) {
			System.out.println("***No se pudo enviar el mensaje, "
									+e.getMessage()+"***");
			throw new ErrorDeEscritura("No se pudo comunicar con el servidor, " +
					"revise las conexiones de red e intente de nuevo");
		}
	}
	
	/**
	 * Recibe un objeto de tipo Document que contiene un xml y lo envia al PDA.
	 * @param xml contiene el documento en formato xml que se va a enviar.
	 * @throws ErrorDeEscritura reporta si ocurrió algún error al momento de
	 * enviar la información.
	 */
	public void enviarXml(Document xml)throws ErrorDeEscritura{
		try {
			String res = convertirXml(xml.getFirstChild());
			try {
				acumularRespuesta(res.getBytes("UTF-8"));
				enviarMensaje("\n".getBytes());
			} catch (UnsupportedEncodingException e) {
				throw new ErrorDeEscritura("***No se pudo enviar el xml "
										+e.getMessage()+"***");
			}
		} catch (ErrorDeEscritura e) {
			System.out.println("***No se pudo enviar el mensaje, "
					+e.getMessage()+"***");
			throw new ErrorDeEscritura("No se pudo comunicar con el servidor, " +
					"revise las conexiones de red e intente de nuevo");
		}
	}
	
	/**
	 * Permite almacenar información en el buffer de salida, no envía
	 * información
	 * @param mensaje Contiene la información que se va a almacenar en el
	 * buffer
	 * @throws ErrorDeEscritura ocurre si no se puede escribir en el buffer de
	 * salida
	 * @see #enviarMensaje
	 */
	public void acumularRespuesta(byte[] mensaje) throws ErrorDeEscritura{
		try {
			salida.write(mensaje);
		} catch (IOException e) {
			throw new ErrorDeEscritura("***No se pudo almacenar en el buffer "
									+e.getMessage()+"***");
		}
	}
	
	/**
	 * Espera por una respuesta de un cliente, bloquea el hilo hasta obtenerla
	 * @return String contiene la respuesta del cliente o nada si hubo algun 
	 * 					error
	 * @throws ErrorDeLectura si hubo algun error tratando de leer la respuesta
	 */
	public byte[] obtenerRespuesta() throws ErrorDeLectura{
		try{
			BufferedReader resp =new BufferedReader(
									new InputStreamReader(entrada,"UTF-8"));
			boolean yaleyo = false;
			char []cbuf = new char[8192];
			StringBuffer s = new StringBuffer();
			while(!yaleyo){ 
				if(entrada.available() == 0){
					break;
				}
				int terminar = resp.read(cbuf,0,8192);
				if(terminar <= 0){
					yaleyo = true;
				}else if(terminar >0){
					int init = 0;
					int end= terminar;
					for(int i=0; i < terminar; ++i){
						if(cbuf[i] == '\n' || cbuf[i] == '\r')
						{
							yaleyo = true;
							end = i;
							break;
						}
					}
					String aux = new String(cbuf,init,end);
					s.append(aux);
					aux = null;
				}
			}
			return (s.toString()).getBytes();
		}catch(Exception e){
			throw new ErrorDeLectura("***No se pudo leer la información, "+e.getMessage()+"***");
		}
	}
	
	/**
	 * Interpreta un mensaje enviado por el servidor como un archivo XML y lo
	 * convierte en un Document para que luego sea procesado. Para que la
	 * función resulte todo el xml debe estar en una linea y debe finalizar con
	 * el caracter \n
	 * @return El documento con el xml ya analizado.
	 */
	public Document obtenerXmlDeServidor() throws ErrorDeLectura{
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setCoalescing(true);
        dbf.setExpandEntityReferences(true);
        dbf.setIgnoringComments(true);
        dbf.setIgnoringElementContentWhitespace(true);
        DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			BufferedReader resp =new BufferedReader(
									new InputStreamReader(entrada,"UTF-8"));
			String xml = resp.readLine();
			Document result = db.parse(
							new ByteArrayInputStream(xml.getBytes("UTF-8")));
			return result;
		} catch (SocketTimeoutException e){
			throw new ErrorDeLectura("Finalización de sesión, tiempo de espera" +
			" de solicitud agotada");
		}catch(ParserConfigurationException e){
			System.out.println("configuración del Parser incorrecta: "+
			e.getMessage()+"***");
			throw new ErrorDeLectura("***No se pudo obtener la información " +
			"del servidor, mensaje incorrecto");
		}catch(UnsupportedEncodingException e){
				throw new ErrorDeLectura("***No se pudo obtener la información " +
						"del servidor, codificación no soportada(UTF-8)"+e.getMessage()+
					"***");
		}catch(IOException e){
			System.out.println("***No se pudo obtener la información " +
						"del servidor, no se pudo leer del socket: "+e.getMessage()+
						"***");
			throw new ErrorDeLectura("No se pudo obtener información del " +
					"servidor, revise las conexiones de red e intente de nuevo");
		}catch(SAXException e){
				throw new ErrorDeLectura("***No se pudo obtener la información " +
						"del servidor, formato de xml incorrecto: "+
						e.getMessage()+"***");
}
	}
	
	/**
	 * Método que cierra la conexión actual y se deshace de los buffers.
	 * 
	 */
	public void cerrarConexion(){
		try {
			salida.close();
			entrada.close();
			com.close();
		} catch (IOException e) {
		}
		salida = null;
		entrada = null;
		com = null;
	}
	
	
	/**
	 * Dado un árbol construye toda la información de un xml recursivamente.
	 * @param root nodo raíz del arbol.
	 * @return String que contiene un xml con la información del nodo de entrada y
	 * de todos sus hijos.
	 */
	public static String convertirXml(Node root){
		if(root.getNodeType() == Node.ELEMENT_NODE){
			NodeList hijos = root.getChildNodes();
			String arbol = new String("<"+root.getNodeName()+">");
			int numHijos = hijos.getLength();
			for(int i = 0; i < numHijos;++i){
				arbol = arbol.concat(convertirXml(hijos.item(i)));
			}
			arbol = arbol.concat("</"+root.getNodeName()+">");
			return arbol;
		}else if(root.getNodeType() == Node.TEXT_NODE){
			return root.getNodeValue();
		}else{
			return "";
		}
	}

	/**
	 * Utiliza la clase Transformer para generar un String a partir de un
	 * Document, no se usa internamente por posibles problemas de 
	 * compatibilidad en ciertas plataformas. 
	 * @param xml documento xml.
	 * @return String que contiene un xml con la información del nodo de entrada y
	 * de todos sus hijos.
	 */
	/*public static String convertirXml(Document xml){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			transformer.transform(new DOMSource(xml), new StreamResult(baos));
		} catch (Exception e) {
		}
			String res = new String(baos.toByteArray());
			return res;
	}*/
	

}