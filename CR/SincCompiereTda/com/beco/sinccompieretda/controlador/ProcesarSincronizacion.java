package com.beco.sinccompieretda.controlador;

import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ProcesarSincronizacion extends Thread {
	private static Vector<Document> colaDePeticiones;
	
	public static synchronized void crearVector(){
		if(colaDePeticiones==null){
			colaDePeticiones = new Vector<Document>();
		}
	}
	
	public static synchronized void agregarPeticion(Document xml) {
		colaDePeticiones.add(xml);
	}
	
	public void run(){
		while(true){
			if(colaDePeticiones.size() == 0){
				try{
					Thread.sleep(1000);
				}catch(Exception e){}
			}else{
				procesarXML(colaDePeticiones.firstElement());
				colaDePeticiones.remove(0);
			}
		}
	}
	
	
	public static void procesarXML(Document xml){
		Node operacion = xml.getFirstChild().getFirstChild();
		String nombreOperacion = operacion.getNodeName();
		if(nombreOperacion.equals("sincPDA")){
			String codprod = obtenerCampo(operacion,"codproducto");
			String tienda = obtenerCampo(operacion,"tienda");
			try{
				TransferenciaInmediata.sincronizarProductoDePda(codprod, tienda);
			}catch(Exception e){
				System.out.println("No se pudo realizar la sincronizacion: "+e.getMessage());
			}
		}else if(nombreOperacion.equals("sincCompiere")){
			String codProm = obtenerCampo(operacion,"codpromocion");
			String tienda = obtenerCampo(operacion,"tienda");
			String detalle = obtenerCampo(operacion,"detalle");
			try{
				TransferenciaInmediata.sincronizarPromocionDeCompiere(codProm, tienda, detalle);
			}catch(Exception e){
				System.out.println("No se pudo realizar la sincronizacion: "+e.getMessage());
			}
		}
	}
	
	/*
	 * Busca entre los hijos del nodo n el elemento con nombre nombreNodo
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
