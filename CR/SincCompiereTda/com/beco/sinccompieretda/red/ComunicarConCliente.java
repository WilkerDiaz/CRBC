package com.beco.sinccompieretda.red;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketTimeoutException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.beco.sinccompieretda.excepciones.*;
/**
 * 
 *	Esta clase maneja todo lo referente a la comunicación con el pda.
 *	Se asume que ya la conexión se estableció, por lo que es necesario pasar
 *	el socket que contiene la conexión en el constructor de la clase.
 *	NO SE DEBE CERRAR EL SOCKET QUE SE LE PASE AL CONSTRUCTOR DESDE EL CODIGO
 *	QUE LLAME A ESTA CLASE!!!!!!
 *
 * @author Marcos Grillo
 * @version 1.0
 */
public class ComunicarConCliente{
	//contiene el buffer que almacena la información a enviar 
	private OutputStream salida;
	//contiene el buffer en el que se recibirá información desde el PDA
	private InputStream entrada;
	//contiene el socket de la conexión
	private Socket sock;
    
	/**
     * Constructor de la clase, establece los parámetros iniciales y establece
     * un tiempo de espera para la conexión.
	 * @param s socket que contiene una conexion activa con un PDA.
	 * @throws ErrorEnCreacion Excepción que indica un fallo en la creación
	 *  del buffer de salida, no puede enviarse información	si esto ocurre.
	 */
	public ComunicarConCliente(Socket s) throws ErrorEnCreacion{
		try{
			entrada = s.getInputStream();
			salida = s.getOutputStream();
			sock = s;
		}catch(IOException e){
			throw new ErrorEnCreacion("***No se pudo inicializar un stream, "
								+e.getMessage()
								+", se debe reiniciar la conexion***"); 
		}
	}
	
	/**
     * Constructor de la clase, establece los parámetros iniciales y establece
     * un tiempo de espera para la conexión.
	 * @param s socket que contiene una conexion activa con un PDA.
	 * @param timeout tiempo que se esperará por una respuesta.
	 * @throws ErrorEnCreacion Excepción que indica un fallo en la creación
	 *  del buffer de salida, no puede enviarse información	si esto ocurre.
	 */
	public ComunicarConCliente(Socket s, int timeout) throws ErrorEnCreacion{
		try{
			entrada = s.getInputStream();
			salida = s.getOutputStream();
			sock = s;
    		sock.setSoTimeout(timeout);
		}catch(IOException e){
			throw new ErrorEnCreacion("***No se pudo inicializar un stream, "
								+e.getMessage()
								+", se debe reiniciar la conexion***"); 
		}
	}
	/**
	 * Envía un mensaje al pda.
	 * @param mensaje contiene el mensaje que se enviará al PDA.
	 * @throws ErrorDeEscritura Si ocurre algun error con el buffer de salida.
	 */
	public void enviarMensaje(byte[] mensaje) throws ErrorDeEscritura{
		try {
			salida.write(mensaje);
			salida.flush();
		} catch (IOException e) {
			throw new ErrorDeEscritura("***No se pudo enviar el mensaje, "
									+e.getMessage()+"***");
		}
	}
	
	/**
	 * Envía lo que este en el buffer al pda.
	 * @throws ErrorDeEscritura Si ocurre algun error con el buffer de salida.
	 */
	public void enviarMensaje() throws ErrorDeEscritura{
		try {
			salida.flush();
		} catch (IOException e) {
			throw new ErrorDeEscritura("***No se pudo enviar el mensaje, "
									+e.getMessage()+"***");
		}
	}
	
	/**
	 * Recibe un objeto de tipo Document que contiene un xml y lo envia al PDA.
	 * @param xml contiene el documento en formato xml que se va a enviar.
	 * @throws ErrorDeEscritura reporta si ocurrió algún error al momento de
	 * enviar la información.
	 */
	public void enviarXml(Document xml)throws ErrorDeEscritura{
		String res = convertirXml(xml.getFirstChild());
		if(res.length() > 695000){
			throw new ErrorDeEscritura("***No se pueden enviar mas de "+
							" 695000 caracteres al PDA***");
		}
		try {
			acumularRespuesta(res.getBytes("UTF-8"));
			enviarMensaje("\n".getBytes());
		} catch (UnsupportedEncodingException e) {
			throw new ErrorDeEscritura("***No se pudo enviar el xml "
									+e.getMessage()+"***");
		}
	}
	
	/**
	 * Permite almacenar información en el buffer de salida, no envía
	 * información.
	 * @param mensaje Contiene la información que se va a almacenar en el
	 * buffer.
	 * @throws ErrorDeEscritura ocurre si no se puede escribir en el buffer de
	 * salida.
	 * @see #enviarMensaje
	 */
	public void acumularRespuesta(byte[] mensaje)throws ErrorDeEscritura{
		try {
			salida.write(mensaje);
		} catch (IOException e) {
			throw new ErrorDeEscritura("***No se pudo escribir en el buffer "
									+e.getMessage()+"***");
		}
	}
	
	/**
	 * Espera por una respuesta de un cliente, bloquea el hilo hasta obtenerla.
	 * @return String contiene la respuesta del cliente o nada si hubo algun 
	 * error.
	 * @throws ErrorDeLectura si hubo algun error tratando de leer la
	 * respuesta.
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
	 * Interpreta un mensaje enviado por el PDA como un archivo XML y lo
	 * convierte en un Document para que luego sea procesado. Para que la
	 * función resulte todo el xml debe estar en una linea y debe finalizar con
	 * el caracter \n.
	 * @return El documento con el xml ya analizado.
	 */
	public Document obtenerXml() throws ErrorDeLectura{
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
		}catch (SocketTimeoutException e){
			throw new ErrorDeLectura("Finalización de sesión, tiempo de espera" +
					" de solicitud agotada");
		}catch(ParserConfigurationException e){
			throw new ErrorDeLectura("***No se pudo obtener la información " +
					"del Cliente, configuración del Parser incorrecta: "+
					e.getMessage()+"***");
		}catch(UnsupportedEncodingException e){
			throw new ErrorDeLectura("***No se pudo obtener la información " +
					"del Cliente, codificación no soportada(UTF-8)"+e.getMessage()+
					"***");
		}catch(IOException e){
			throw new ErrorDeLectura("***No se pudo obtener la información " +
					"del Cliente, no se pudo leer del socket: "+e.getMessage()+
					"***");
		}catch(SAXException e){
			throw new ErrorDeLectura("***No se pudo obtener la información " +
					"del Cliente, formato de xml incorrecto: "+
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
			sock.close();
		} catch (IOException e) {
			//No hace falta informar los errores, se está eliminando todo.
		}
		salida = null;
		entrada = null;
		sock = null;
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
	public static String convertirXml(Document xml){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			transformer.transform(new DOMSource(xml), new StreamResult(baos));
		} catch (Exception e) {
		}
			String res = new String(baos.toByteArray());
			return res;
	}
}


