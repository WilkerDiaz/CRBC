/*
 * $Id: SyncCommander.java,v 1.2.2.1 2005/04/08 20:43:22 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr
 * Programa		: SyncCommander.java
 * Creado por	: Programa8
 * Creado en 	: 20-ene-2005 20:57:04
 * (C) Copyright 2005 SuperFerretería EPA C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisión	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: SyncCommander.java,v $
 * Revision 1.2.2.1  2005/04/08 20:43:22  programa8
 * Creación de servicio syncAfiliado
 *
 * Revision 1.2  2005/03/10 15:54:41  programa8
 * CR al 10/03/2005. Merging desde branch STABLE
 *
 * Revision 1.1.2.3  2005/03/07 12:55:33  programa8
 * Integración Versiones Estable e Inestable al 07/03/2005
 *
 * Revision 1.1.4.1  2005/02/28 18:39:15  programa8
 * Version Inestable al 28/02/2005
 *     *-Preparación para trabajar sin administrador de ventanas.
 *     *-Reordenamiento de GUI
 *     *-Mejoras en scroll y pantallas de pagos
 *     *- Mantenimiento de estado en Cliente en Espera
 *     *- Avisos del sistema manejados por la aplicacion
 *     *- Desbloqueo de caja por otros usuarios reparado.
 *
 * Revision 1.1.2.2  2005/02/22 18:26:11  programa4
 * *** empty log message ***
 *
 * Revision 1.1.2.1  2005/01/28 19:56:36  acastillo
 * Caja Registradora EPA3 - CR RC3
 *
 * ===========================================================================
 */
package com.becoblohm.cr.sincronizador;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

//import org.apache.crimson.tree.DOMImplementationImpl;
import org.w3c.dom.Document;
//import org.w3c.dom.Element;

import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.utiles.DOMSerializer;



/**
 * <pre>
 * Proyecto: CR 
 * Clase: SyncCommander
 * </pre>
 * <p>
 * <a href="SyncCommander.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - Arístides Castillo
 * @version 1.0 - 20012005
 * @since 20-ene-2005
 * @
 */
public class SyncCommander {

	/**
	 * @since 20-ene-2005
	 * 
	 */
	Socket conexion;
	PrintWriter out;
	
	public SyncCommander() throws Exception{
		super();
		try {
			InitCR.cargarPreferencias();
			conexion = new Socket("localhost", Sincronizador.getConfigPtoEscucha());
			out = new PrintWriter(conexion.getOutputStream(), true);
			
		} catch (UnknownHostException e) {
			throw new Exception("No se pudo conectar al servidor", e);
		} catch (IOException e) {
			throw new Exception("No se pudo conectar al servidor", e);
		}
		
	}

	public static void main(String[] args) {
		if ((args.length >= 1) && (args.length <= 2)) {
			try {
				SyncCommander sync = new SyncCommander();
				String servicio = args[0];
				String param = "";
				if (args.length == 2) 
					param = args[1];
				sync.enviarComando(servicio, param);
				sync.cerrar();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Uso: java SyncCommander <servicio> [<param>]");
			System.out.println("Donde:");
			System.out.println("	servicio = 'syncVentas | actCaja | syncAfiliado'");
			
		}
	}
	
	public void send(Document doc) {
		DOMSerializer serializer = new DOMSerializer();
		try {
			serializer.serialize(doc, conexion.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public void enviarComando(String servicio, String param) {
		/*DOMImplementationImpl domImpl = new DOMImplementationImpl();
		Document doc = domImpl.createDocument(null, "archivoMandatosCR", null);
		Element root = doc.getDocumentElement();
		Element e = doc.createElement("mandatoCR");
		e.setAttribute("servicio", servicio);
		e.setAttribute("param", param);
		root.appendChild(e);
		send(doc);*/
	}

	public void cerrar() {
		try {
			conexion.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
