/*
 * Creado el 21/10/2004
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.becoblohm.cr.visortransacciones;


import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.manejarventa.Anulacion;
import com.becoblohm.cr.manejarventa.DetalleTransaccion;
import com.becoblohm.cr.manejarventa.Devolucion;
import com.becoblohm.cr.manejarventa.Venta;
import com.becoblohm.cr.utiles.DOMSerializer;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author Programa8
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class ModeloEditorTransaccion {
	
	// Convertidor de POJO's en XML
	private XStream obj2xml;
	// Factory para el DocumentBuilder
	private DocumentBuilderFactory factory;
	// Builder de documentos. Sirve para hacer parsing de un archivo .xml 
	private DocumentBuilder builder;
	//Factory para transformaciones
	private TransformerFactory trFactory;
	// URL's de xslt
	private String urlXSLVenta;
	private String urlXSLAnulacion;
	private String urlXSLDevolucion;
	
	
	public ModeloEditorTransaccion() {
		// Instanciar los objetos
		obj2xml = new XStream(new DomDriver());
		factory = DocumentBuilderFactory.newInstance();
		try 
		{
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException pe) {
			pe.printStackTrace();
		}
		trFactory = TransformerFactory.newInstance();		
		configXML();
	}
	
	protected void configXML() {
		//  Establece las cadenas de identificacion para las clases manejadas
		obj2xml.alias("venta", Venta.class);
		obj2xml.alias("anulacion", Anulacion.class);
		obj2xml.alias("devolucion", Devolucion.class);
		obj2xml.alias("pago", Pago.class);
		obj2xml.alias("detalletransaccion", DetalleTransaccion.class);
	}
	
	protected String getHtmlTransaccion(Object o, String url) {
		String html = null;
		try {
			Document doc = builder.parse(new InputSource(new StringReader(obj2xml.toXML(o))));
			Transformer transformer = trFactory.newTransformer(new StreamSource(url));
			StringWriter strWriter = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(strWriter));		
			html = strWriter.toString();
		} catch (TransformerConfigurationException te) {
			te.printStackTrace();	
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return html;		
	}
	
	/**
	 * Método que retorna la representacion HTML de una venta
	 * @param v Venta a transformar
	 * @return String conteniendo el HTML transformado
	 */
	public String getHtmlTransaccion(Venta v) {
		return getHtmlTransaccion(v, urlXSLVenta);
	}
	
	/**
	 * Método que retorna la representacion HTML de una anulación
	 * @param a Anulación a transformar
	 * @return String conteniendo el HTML transformado
	 */
	public String getHtmlTransaccion(Anulacion a) {
		return getHtmlTransaccion(a, urlXSLAnulacion);
	}
	
	/**
	 * Método que retorna la representación HTML de una devolución
	 * @param d Devolución a transformar
	 * @return String conteniendo el HTML transformado
	 */
	public String getHtmlTransaccion(Devolucion d) {
		return getHtmlTransaccion(d, urlXSLDevolucion);
	}
	

	/**
	 * Establece el URL de la transformación XSL para las anulaciones
	 * @param url
	 */
	public void setUrlXSLAnulacion(String url) {
		urlXSLAnulacion = url;
	}

	/**
	 * Establece el URL de la transformación XSL para las devoluciones
	 * @param url
	 */
	public void setUrlXSLDevolucion(String url) {
		urlXSLDevolucion = url;
	}

	/**
	 * Establece el URL de la transformación XSL para las ventas
	 * @param url
	 */
	public void setUrlXSLVenta(String url) {
		urlXSLVenta = url;
	}
	
	public String getXML(Object o) {
		String xml = null;
		try {
			String xmlini = obj2xml.toXML(o);
			InputSource is = new InputSource(new StringReader(xmlini));
			Document doc = builder.parse(is);
			DOMSerializer ser = new DOMSerializer();
			StringWriter strWriter = new StringWriter();
			ser.serialize(doc, strWriter);
			xml = strWriter.toString();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return xml;		
	}

}
