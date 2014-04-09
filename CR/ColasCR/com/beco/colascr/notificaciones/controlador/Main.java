package com.beco.colascr.notificaciones.controlador;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.beco.colascr.notificaciones.basededatos.BaseDeDatosCorreo;

public class Main {
	private static String remitente;
	private static String passwordRemitente;
	private static String recipienteLRG;
	private static DecimalFormat df = new DecimalFormat("#,##0.00");
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' y se comento codigo inalcanzable
	* Fecha: agosto 2011
	*/
	public static Vector<String> getSMTP() throws Exception{
		DocumentBuilderFactory docbuildfact;
		DocumentBuilder docbuild;
		docbuildfact = DocumentBuilderFactory.newInstance();
		docbuildfact.setNamespaceAware(true);
		docbuildfact.setCoalescing(true);
		docbuildfact.setExpandEntityReferences(true);
		docbuildfact.setIgnoringComments(true);
		docbuildfact.setIgnoringElementContentWhitespace(true);
		docbuild = docbuildfact.newDocumentBuilder();

	    File file;
	    file = new File("/opt/CR/notificaciones.config");
	    /*if(file == null){
	    	throw new Exception("No se encontro el archivo notificaciones.config");
	    }*/
	    Document doc = docbuild.parse(file);
	    file = null;
	    Node config = doc.getFirstChild();
	    if(!config.getNodeName().equals("configuracion")){
	    	throw new Exception("Archivo de configuracion con formato inadecuado");
	    }
	    Node smtp = obtenerHijo(config,"smtp");
	    String ip = obtenerCampo(smtp,"ip");
	    String puerto = obtenerCampo(smtp,"puerto");
	    //MediadorCorreo.setSmtpHost(ip, puerto);
	    
	   Vector<String> resultado = new Vector<String>();
	   resultado.addElement(ip);
	   resultado.addElement(puerto);
	   return resultado;

	}
	
	/**
	 * Busca entre los hijos del nodo n el elemento con nombre nombreNodo
	 * @param n
	 * @param nombreNodo
	 * @return String
	 */
	private static String obtenerCampo(Node n,String nombreNodo){
		NodeList elementos = n.getChildNodes();
			for(int i = 0; i < elementos.getLength(); ++i){
				Node hijo = elementos.item(i); 
				if(hijo.getNodeName().equals(nombreNodo)){
					return hijo.getFirstChild().getNodeValue();
				}
			}
		return null;
	}
	
	/**
	 * Dado un Node busca entre sus hijos el que tenga como nombre campo
	 * @param padre Node donde se empezará la búsqueda 
	 * @param campo nombre del Node que se está buscando
	 * @return Node o null si no se encuentra el campo buscado
	 */
	private static Node obtenerHijo(Node padre, String campo){
		NodeList hijos = padre.getChildNodes();
		Node hijo = null;
		int tam = hijos.getLength();
		for(int i = 0; i < tam; ++i ){
			hijo = hijos.item(i);
			if(hijo.getNodeName().equals(campo)){
				return hijo;
			}
		}
		return null;
	}
	/**
	 * Busca del archivo notificaciones.config el recipiente de lista de regalo 
	 * garantizada 
	 * @throws Exception
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se comento codico inalcanzable
	* Fecha: agosto 2011
	*/
	private static void obtenerRecipienteLRG() throws Exception{
		DocumentBuilderFactory docbuildfact;
		DocumentBuilder docbuild;
		docbuildfact = DocumentBuilderFactory.newInstance();
		docbuildfact.setNamespaceAware(true);
		docbuildfact.setCoalescing(true);
		docbuildfact.setExpandEntityReferences(true);
		docbuildfact.setIgnoringComments(true);
		docbuildfact.setIgnoringElementContentWhitespace(true);
		docbuild = docbuildfact.newDocumentBuilder();

	    File file;
	    file = new File("notificaciones.config");
	    /*if(file == null){
	    	throw new Exception("No se encontro el archivo notificaciones.config");
	    }*/
	    Document doc = docbuild.parse(file);
	    file = null;
	    Node config = doc.getFirstChild();
	    if(!config.getNodeName().equals("configuracion")){
	    	throw new Exception("Archivo de configuracion con formato inadecuado");
	    }
	    recipienteLRG = obtenerCampo(config,"recipienteLRG");
	}
	
	/**
	 * Obtiene del archivo notificaciones.config el remitente para los correos
	 * @throws Exception
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se comento código inalcanzable
	* Fecha: agosto 2011
	*/
	private static void obtenerRemitente() throws Exception{
		DocumentBuilderFactory docbuildfact;
		DocumentBuilder docbuild;
		docbuildfact = DocumentBuilderFactory.newInstance();
		docbuildfact.setNamespaceAware(true);
		docbuildfact.setCoalescing(true);
		docbuildfact.setExpandEntityReferences(true);
		docbuildfact.setIgnoringComments(true);
		docbuildfact.setIgnoringElementContentWhitespace(true);
		docbuild = docbuildfact.newDocumentBuilder();

	    File file;
	    file = new File("notificaciones.config");
	    /*if(file == null){
	    	throw new Exception("No se encontro el archivo notificaciones.config");
	    }*/
	    Document doc = docbuild.parse(file);
	    file = null;
	    Node config = doc.getFirstChild();
	    if(!config.getNodeName().equals("configuracion")){
	    	throw new Exception("Archivo de configuracion con formato inadecuado");
	    }
	    remitente = obtenerCampo(config,"remitente");
	    if(remitente == null || remitente == "")
	    	throw new Exception("No se encontro remitente");
	    passwordRemitente = obtenerCampo(config,"passwordRemitente");
	    if(passwordRemitente == null || passwordRemitente == "")
	    	throw new Exception("No se encontro password del remitente");
	}
	
	/**
	 * Envia los correos de notificación de vencimiento de apartados
	 * @throws Exception 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private static void enviarCorreoApartados(Vector<String> datosSMTP) throws Exception{
		Vector<Servicio> apartados = BaseDeDatosCorreo.obtenerApartadosVencidos();
		int numApartados = apartados.size();
		if(numApartados == 0)
			return;
		boolean [] apartadoEnviado = new boolean[numApartados];
		for(int i = 0; i < numApartados; ++i){
			apartadoEnviado[i] = false;
		}		
		for(int i=0; i < numApartados;++i){
			if(!apartadoEnviado[i]){
				apartadoEnviado[i] = true;
				Servicio serv = (Servicio)apartados.get(i);
				String correoCliente = serv.getCorreo();
				System.out.println("     Obtenido correo "+correoCliente);
				//Verificamos que el correo del cliente sea válido
				boolean correoValido = Main.isEmailValido(correoCliente);
				
			    if(correoValido){
			    	System.out.println("     Correo valido");
					Vector<Servicio> apartadosCliente = new Vector<Servicio>();
					for(int j=i+1; j < numApartados; ++j){
						Servicio serv2 = (Servicio)apartados.get(j);
						if(correoCliente.equals(serv2.getCorreo())){
							apartadosCliente.add(serv2);
							apartadoEnviado[j] = true;
						}
					}
					int numApartadosCliente = apartadosCliente.size();
					String mensaje = "";
					if(numApartadosCliente == 0){
						mensaje = "Hola "+serv.getNombreCliente()+",\n\t Le recordamos que en la tienda BECO "+serv.getNombreTienda()+" tiene un apartado con código "+serv.getNumServicio()+" que vencerá el día "+serv.getFechaServicio()+". El monto base de este apartado es de Bs. "+df.format(serv.getMontoBase())+" mas el impuesto de Bs. "+df.format(serv.getMontoImpuesto())+" suman Bs. "+df.format(serv.getMontoTotal())+" y su saldo restante por cancelar es de Bs. "+df.format(serv.getSaldoRestante())+". Ud. apartó los siguientes productos:\n\n";
						Vector<Detalle> productos = serv.getDetalles();
						int numProductos = productos.size();
						for(int j=0; j < numProductos; ++j){
							Detalle d = (Detalle)productos.elementAt(j);
							mensaje += "\t\t-"+d.getNombre()+"  "+d.getDescripcionLarga()+"\n";
						}
						mensaje += "\n\nDirección de la tienda: "+serv.getDireccionTienda()+", número de teléfono: "+serv.getNumTlf();
					}else{
						apartadosCliente.add(serv);
						numApartadosCliente++;
						mensaje = "Hola "+serv.getNombreCliente()+",\n Le informamos que en la tienda "+serv.getNombreTienda()+" tiene los siguientes apartados que vencerán el dia "+serv.getFechaServicio()+":\n";
						for(int j=0; j < numApartadosCliente;++j ){
							Servicio serv2 = (Servicio)apartadosCliente.elementAt(j);
							mensaje += "\n\nApartado número "+serv2.getNumServicio()+":\n\t El monto base de este apartado es de Bs. "+df.format(serv2.getMontoBase())+" mas el impuesto de Bs. "+df.format(serv2.getMontoImpuesto())+" suman Bs. "+df.format(serv2.getMontoTotal())+" y su saldo restante por cancelar es de Bs. "+serv.getSaldoRestante()+". Ud. apartó los siguientes productos:\n";
							Vector<Detalle> productos = serv2.getDetalles();
							int numProductos = productos.size();
							for(int l=0; l < numProductos; ++l){
								Detalle d = (Detalle)productos.elementAt(l);
								mensaje += "\t\t-"+d.getNombre()+"  "+d.getDescripcionLarga()+"\n";
							}
						}
						mensaje += "\n\nDirección de la tienda: "+serv.getDireccionTienda()+", número de teléfono: "+serv.getNumTlf();
					}
					EMail email = new EMail((String)datosSMTP.get(0), 
							Integer.parseInt((String)datosSMTP.get(1)),
							true, 
							remitente, 
							"Tiendas BECO - Servicios al cliente",  
							serv.getCorreo(), 
							serv.getNombreCliente(), 
							"Vencimiento de apartados en BECO "+serv.getNombreTienda(), 
							mensaje, null);
							
					email.createAuthenticator(remitente, passwordRemitente);
					email.send();
					System.out.println("     Correo enviado");
					//MediadorCorreo.send(remitente, serv.getCorreo(), "Vencimiento de apartados en BECO "+serv.getNombreTienda(), mensaje);
			    } else {
			    	System.out.println("Correo invalido: "+serv.getNombreCliente()+" ("+serv.getCorreo()+")");
			    }
			}
		}
	}
	
	/**
	 * Envía los correos de notificación de operaciones sobre listas de regalos
	 * @throws Exception
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private static void enviarCorreoListaDeRegalos(Vector<String> datosSMTP)throws Exception{
		Vector<OperacionSobreLista> lista = BaseDeDatosCorreo.obtenerOperacionesDeListas();
		int numOp = lista.size();
		if(numOp == 0)
			return;
		boolean [] operacionLista = new boolean[numOp];
		for(int i=0; i < numOp;++i)
			operacionLista[i] = false;
		for(int i=0; i < numOp; ++i){
			if(!operacionLista[i]){
				operacionLista[i] = true;
				String mensaje = "";
				Vector<OperacionSobreLista> opSobreUnaLista = new Vector<OperacionSobreLista>();
				OperacionSobreLista op = (OperacionSobreLista)lista.elementAt(i);
				if(Main.isEmailValido(op.getCorreoCliente())){
					int numeroLista = op.getNumeroLista();
					for(int j = i+1; j < numOp;++j){
						OperacionSobreLista otraOp = (OperacionSobreLista)lista.elementAt(j);
						if(numeroLista == otraOp.getNumeroLista()){
							operacionLista[j] = true;
							opSobreUnaLista.add(otraOp);
						}
					}
					int numOpSobreLista = opSobreUnaLista.size();
					if(numOpSobreLista == 0){
						mensaje = "Hola "+op.getNombreCliente()+",\n\nLe informamos que en la tienda BECO "+op.getNombreTienda()+" tiene una lista de regalos sobre la que "+op.getNombreInvitado()+" hizo ";
						if((op.getTipoOperacion()).equals("V")){
							mensaje += "la compra del producto:\n\n\t"+op.getNombreProducto();
						}else if((op.getTipoOperacion()).equals("A")){
							mensaje += "un abono parcial de "+df.format(op.getMontoAbono())+" del producto:\n\n\t"+op.getNombreProducto();
						}else if((op.getTipoOperacion()).equals("T")){
							mensaje += "un abono total del producto\n\n\t "+op.getNombreProducto();
						}else if((op.getTipoOperacion()).equals("L")){
							mensaje += "un abono sobre la lista de "+df.format(op.getMontoAbono());
						}
						mensaje += "\n\nTuvo la siguiente dedicatoria:\n\n\t\t"+op.getDedicatoria()+"\n\nDirección de la tienda: "+op.getDireccionTienda()+", número de teléfono: "+op.getNumTlf();
					}else{
						opSobreUnaLista.add(op);
						numOpSobreLista++;
						mensaje = "Hola "+op.getNombreCliente()+"\n\nLe informamos que en la tienda BECO "+op.getNombreTienda()+" tiene una lista de regalos sobre la que se realizaron las siguientes operaciones:";
						for(int j=0; j < numOpSobreLista; ++j){
							OperacionSobreLista operacion = (OperacionSobreLista)opSobreUnaLista.get(j);
							if((operacion.getTipoOperacion()).equals("V")){
								mensaje += "\n\nVenta de Producto\n\t"+operacion.getNombreInvitado()+" compró el producto: "+operacion.getNombreProducto();
							}else if((operacion.getTipoOperacion()).equals("A")){
								mensaje += "\n\nAbono Parcial\n\t"+operacion.getNombreInvitado()+" hizo un abono parcial de Bs. "+df.format(operacion.getMontoAbono())+" del producto: "+operacion.getNombreProducto();
							}else if((operacion.getTipoOperacion()).equals("T")){
								mensaje += "\n\nAbono Total\n\t"+operacion.getNombreInvitado()+" hizo un abono total del producto "+operacion.getNombreProducto();
							}else if((operacion.getTipoOperacion()).equals("L")){
								mensaje += "\n\nAbono Sobre la Lista\n\t"+operacion.getNombreInvitado()+" hizo un abono sobre la lista de Bs. "+df.format(operacion.getMontoAbono());
							}
							mensaje += "\n\n\tCon la dedicatoria:\n\n\t\t"+operacion.getDedicatoria();
						}
						mensaje += "\n\nDirección de la tienda: "+op.getDireccionTienda()+", número de teléfono: "+op.getNumTlf();
					}
					
					EMail email = new EMail((String)datosSMTP.get(0), 
							Integer.parseInt((String)datosSMTP.get(1)),
							true, 
							remitente, 
							"Tiendas BECO - Servicios al cliente",  
							op.getCorreoCliente(), 
							op.getNombreCliente(), 
							"Operaciones sobre la lista de regalos "+op.getNumeroLista(),
							mensaje, null);
					email.createAuthenticator(remitente, passwordRemitente);
					email.send();
					
					//MediadorCorreo.send(remitente,op.getCorreoCliente() , "Operaciones sobre la lista de regalos "+op.getNumeroLista(), mensaje);
				}
			}
			
		}
	}
	
	/**
	 * Envía los correos de notificación de venta de productos de lista de regalos garantizadas
	 * @throws Exception
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private static void enviarCorreoListaDeRegalosGarantizada(Vector<String> datosSMTP) throws Exception{
		obtenerRecipienteLRG();
		Vector<OperacionSobreLista> listas = BaseDeDatosCorreo.obtenerListasGarantizadas();
		int numOp = listas.size();
		if(numOp == 0)
			return;
		String mensaje = "Los siguientes productos han sido comprados:\n";
		OperacionSobreLista o = (OperacionSobreLista)listas.elementAt(0);
		int numLista = o.getNumeroLista();
		mensaje += "\tLista n°"+numLista+" del cliente: "+o.getNombreCliente()+"\n";
		for(int i=0; i < numOp; ++i){
			int nL = o.getNumeroLista();
			if(nL != numLista){
				numLista = nL;
				mensaje += "\nLista n° "+numLista+" del cliente: "+o.getNombreCliente()+"\n";
			}
			mensaje += "\t\t"+o.getCodProducto()+"\t"+o.getNombreProducto()+"\t Vendido en tienda n° "+o.getNumTienda()+"\n";
			o = (OperacionSobreLista)listas.elementAt(i);
		}
		EMail email = new EMail((String)datosSMTP.get(0), 
				Integer.parseInt((String)datosSMTP.get(1)), 
				true, 
				remitente, 
				"Tiendas BECO - Servicios al cliente",  
				recipienteLRG, 
				"Tiendas BECO - Servicios al cliente", 
				"Operaciones sobre la lista de regalos garantizadas",
				mensaje, null);
		email.createAuthenticator(remitente, passwordRemitente);
		email.send();
		
		
		//MediadorCorreo.send(remitente,recipienteLRG, "Operaciones sobre la lista de regalos garantizadas", mensaje);
		
	}
	
	/**
	 * Verificación de correo válido
	 */
	public static boolean isEmailValido(String email){
		if(email!=null){
			String[] tokens = email.split("@");
			boolean contienePunto = false;
			if(tokens.length==2)
				contienePunto = tokens[1].indexOf(".")!=-1;
			
			return tokens.length == 2 && contienePunto;
		} else {
			return false;
		}
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static void main(String args[]){
		try{
			obtenerRemitente();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("No se obtuvo el remitente de los correos... enviando correos con: BECO");
			remitente = "BECO";
		}
		Vector<String> resultadoSMTP = new Vector<String>();
		try{
			resultadoSMTP = getSMTP();
		}catch(Exception e){
			System.out.println("No se pudo determinar el ip y el puerto del servidor smtp...");
			System.exit(1);
		}
		try{
			System.out.println("Enviando correo de apartados");
			enviarCorreoApartados(resultadoSMTP);
		}catch(Exception e){
			System.out.println("No se pudo enviar los correos de apartados: "+e.getMessage());
			e.printStackTrace();
		}
		try{
			enviarCorreoListaDeRegalos(resultadoSMTP);
		}catch(Exception e){
			System.out.println("No se pudo enviar los correos de listas: "+e.getMessage());
			e.printStackTrace();
		}
		try{
			enviarCorreoListaDeRegalosGarantizada(resultadoSMTP);
		}catch(Exception e){
			System.out.println("No se pudo enviar el correo de lista garantizada");
			e.printStackTrace();
		}
	}
}
