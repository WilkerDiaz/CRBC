/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.cr.pda
 * Programa   : Traductor.java
 * Creado por : aavila
 * Creado en  : 12/08/2009  
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

import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.becoblohm.cr.manejadorsesion.Sesion;

public class Traductor {
	
	//public static MensajePDA mensaje;
	//public static Document documento;
	public  static String nombreOperacion ;
	
	
	public Traductor(){
		//mensaje=null;
		//documento=null;
		nombreOperacion = "";
	}
	
	public static MensajePDA traducir (Document cadenaPDA)throws Exception{
		Node operacion = cadenaPDA.getFirstChild();
		nombreOperacion = operacion.getNodeName();
		if(nombreOperacion.equals(Sesion.pdaTipoMensajeIniciarSesion)){
			return iniciarSesion(cadenaPDA);
		}else
			return convertirAVenta(cadenaPDA);
	}
	
	public  static Document traducir (MensajePDA mensaje){
		return convertirAXML(mensaje);
	}
	
	/**
	 * Método convertirAVenta Convierte el Document en un XML
	 * @author aavila
	 * @param cadenaPDA
	 * @return void
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public  static MensajePDA convertirAVenta(Node cadenaPDA) {
		int n =0;
		String id="";
		Vector<DetalleProducto> detalles = new Vector<DetalleProducto>();
		Node operacion = cadenaPDA.getFirstChild();
		nombreOperacion = operacion.getNodeName();
		NodeList hijos = operacion.getChildNodes();
		n = hijos.getLength();
		for (int i=0; i<n; i++){
			Node hijo = hijos.item(i);
			if(hijo.getNodeName().equals("detalle")){
				String producto = obtenerCampo(hijo, Sesion.pdaCodProducto);
				double cantidad = Double.parseDouble(obtenerCampo(hijo, Sesion.pdaCantidad));
				detalles.add(new DetalleProducto(producto, cantidad));
			}else if(hijo.getNodeName().equals(Sesion.pdaIdVenta))
				id = hijo.getTextContent();
		}
		MensajePDA mensaje =  new MensajePDA(detalles,id);
		mensaje.setNombreOperacion(nombreOperacion);
		return mensaje;
	}
	
	public  static MensajePDA iniciarSesion(Document hijo)throws Exception{
		MensajePDA mensaje = new MensajePDA();
		NodeList operaciones = hijo.getFirstChild().getChildNodes();
		Node inicioSesion = null;
		for(int i=0; i < operaciones.getLength(); ++i){
			Node operacion = operaciones.item(i);
			if(operacion.getNodeName().equals(Sesion.pdaOperacionInicioSesion)){
				inicioSesion = operacion;
				break;
			}
		}
		NodeList datos = inicioSesion.getChildNodes();
		int numDatos = datos.getLength();
		byte[] claveDES = {6, 9, 8, 4, 9, 1, 9, 3, 0, 9, 6, 1, 6, 0, 8, 8, 8, 6, 7, 1, 8, 7, 5, 1};
		TriDES.prepare(claveDES);
		for(int i=0; i < numDatos; ++i){
			Node dato = datos.item(i);
			String nombreDato = dato.getNodeName();
			if(nombreDato.equals("codigodebarras")){
				mensaje.setCodigoDeBarras(TriDES.decrypt(dato.getTextContent()));
			}else if(nombreDato.equals("clave")){
				mensaje.setClave(TriDES.decrypt(dato.getTextContent()));
			}else if(nombreDato.equals("metodo")){
				mensaje.setNombreMetodo(dato.getTextContent());
			}else if(nombreDato.equals("modulo")){
				mensaje.setNombreModulo(dato.getTextContent());
			}
		}
		mensaje.setNombreOperacion(Sesion.pdaTipoMensajeIniciarSesion);
		mensaje.setHayError(false);
		return mensaje;
	}
	
	/**
	 * Método convertirAXML Convierte el MensajePDA en un XML
	 * @author aavila
	 * @param mensaje
	 * @return void
	 */
	private  static Document convertirAXML(MensajePDA mensaje) {
		Document documento = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		dbf.setCoalescing(true);
		dbf.setExpandEntityReferences(true);
		dbf.setIgnoringComments(true);
		dbf.setIgnoringElementContentWhitespace(true);
		DocumentBuilder db;
		if(!mensaje.isHayError()){
			if (mensaje.getTipoConsulta().equalsIgnoreCase(Sesion.pdaTipoMensajeVentaEspera)){
				try {
					db = dbf.newDocumentBuilder();
					Document docOperacionExitosa = db.newDocument();
					Element ventaEspera = docOperacionExitosa.createElement(Sesion.pdaTipoMensajeVentaEspera);
					Element operacionExitosa = docOperacionExitosa.createElement(Sesion.pdaOperacionExitosa);
					ventaEspera.appendChild(operacionExitosa);
					docOperacionExitosa.appendChild(ventaEspera);
					documento = docOperacionExitosa;
				} catch (ParserConfigurationException e) {e.printStackTrace();}
			} else if(mensaje.getTipoConsulta().equalsIgnoreCase(Sesion.pdaTipoMensajeIniciarSesion)){
				try{
					db = dbf.newDocumentBuilder();
					documento = db.newDocument();
					Element operacion = documento.createElement(nombreOperacion);
					Element inicioSesion = documento.createElement(Sesion.pdaTipoMensajeIniciarSesion);
					Element operacionExitosa = documento.createElement(Sesion.pdaOperacionExitosa);
					inicioSesion.appendChild(operacionExitosa);
					operacion.appendChild(inicioSesion);
					documento.appendChild(operacion);
				}catch (ParserConfigurationException e){e.printStackTrace();}
			}else{
				try{
					db = dbf.newDocumentBuilder();
					Document docConsultarPrecio = db.newDocument();
					Element consultarPrecio = docConsultarPrecio.createElement(mensaje.getTipoConsulta());
					Element monto = docConsultarPrecio.createElement("monto");
					monto.setTextContent(""+mensaje.getMontoTotal());
					Element formadepago = docConsultarPrecio.createElement("formadepago");
					Element descuento = docConsultarPrecio.createElement("descuento");
					descuento.setTextContent(""+mensaje.getMontoDescuento());
					Element nombreForma = docConsultarPrecio.createElement("nombre");
					nombreForma.setTextContent(mensaje.getDescFormaDePago());
					formadepago.appendChild(nombreForma);
					formadepago.appendChild(descuento);
					Vector<DetalleProducto> detalles = mensaje.getDetalles();
					int tam = detalles.size();
					for(int j=0; j < tam; ++j){
						DetalleProducto d = (DetalleProducto)detalles.get(j);
						Element detalle = docConsultarPrecio.createElement("detalle");
						Element codProducto = docConsultarPrecio.createElement("codproducto");
						codProducto.setTextContent(d.getCodProducto());
						Element nombreProd = docConsultarPrecio.createElement("nombre");
						nombreProd.setTextContent(d.getDescProducto());
						Element cantidad = docConsultarPrecio.createElement("cantidad");
						cantidad.setTextContent(""+d.getCantidad());
						Element precioRegular = docConsultarPrecio.createElement("precioregular");
						precioRegular.setTextContent(""+d.getPrecioRegular());
						Element precioFinal = docConsultarPrecio.createElement("preciofinal");
						precioFinal.setTextContent(""+d.getPrecioFinal());
						Element montoVenta = docConsultarPrecio.createElement("montoventa");
						montoVenta.setTextContent(""+d.getMontoVenta());
						Element condicionventa = docConsultarPrecio.createElement("condicionventa");
						condicionventa.setTextContent(d.getCondVenta());
						detalle.appendChild(codProducto);
						detalle.appendChild(nombreProd);
						detalle.appendChild(cantidad);
						detalle.appendChild(precioRegular);
						detalle.appendChild(precioFinal);
						detalle.appendChild(montoVenta);
						detalle.appendChild(condicionventa);
						consultarPrecio.appendChild(detalle);
					}
					consultarPrecio.appendChild(monto);
					consultarPrecio.appendChild(formadepago);
					docConsultarPrecio.appendChild(consultarPrecio);
					documento = docConsultarPrecio;
				}catch(Exception e){e.printStackTrace();}
			}
		}else{
			try {
				db = dbf.newDocumentBuilder();
				Document docError = db.newDocument();
				Element cabecera = docError.createElement(mensaje.getTipoConsulta());
				Element error = docError.createElement(Sesion.pdaError);
				error.setTextContent(mensaje.getMensaje());
				cabecera.appendChild(error);
				docError.appendChild(cabecera);
				documento = docError;
			} catch (ParserConfigurationException e) {e.printStackTrace();}
		}
		return documento;
	}
	
	/**
	 * Método obtenerCampo 
	 * @author aavila
	 * @param n
	 * @param nombreNodo
	 * @return String
	 */
	private static String obtenerCampo(Node n,String nombreNodo){
		NodeList elementos = n.getChildNodes();
			for(int i = 0; i < elementos.getLength(); ++i){
				Node hijo = elementos.item(i); 
				if(hijo.getNodeName().equals(nombreNodo)){
					return hijo.getTextContent();
				}
			}
		return null;
	}


}
