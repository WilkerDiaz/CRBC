package com.beco.cr.devolucion;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 * Creado el 16-ago-06
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */

/**
 * @author gmartinelli
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class Prueba extends HttpServlet {
	
	//public InterfaceRemota objetoRemoto;
	
	/*public Prueba() {
		try {
			Runtime.getRuntime().exec("rmiregistry");
		} catch (Exception e) {
		}

		System.setProperty(
			"java.rmi.server.codebase",
			"file:/www/jspserver/webapps/example/WEB-INF/classes/");
	
		try {
			// Se publica el objeto remoto
			objetoRemoto = new ObjetoRemoto();
			Naming.rebind("//192.168.1.10/example/ObjetoRemoto", objetoRemoto);
		} catch (Exception e) {
		}
	}*/
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	  throws IOException, ServletException{

	  response.setContentType("text/html");
	  PrintWriter out = response.getWriter();
	  HttpSession session = request.getSession();
	  out.println("<html>");
	  out.println("<META HTTP-EQUIV='Expires' CONTENT='Mon, 01 Jan 1990 13:00:00 GMT'>");
	  out.println("<body>");
	  out.println("<head>");
	  out.println("<title>Hola Mundo</title>");
	  out.println("</head>");
	  out.println("Hola Mundo ...");
	  out.println("<p>Voy a Iniciar el RMIREGISTRY");
	  /*try { 
		  Runtime.getRuntime().exec("rmiregistry");
	  } catch (Exception e) {
	  	e.printStackTrace();
	  }*/
	  out.println("<p>Iniciado");
	  out.println("<p>Voy a hacer el setProperty");
	  /*System.setProperty(
		  "java.rmi.server.codebase",
		  "file:/www/jspserver/webapps/example/WEB-INF/classes/");*/
	  out.println("<p>Hecho");
	  out.println("<p>Voy a publicar el Objeto");
	  /*InterfaceRemota objetoRemoto = new ObjetoRemoto();
	  Naming.rebind("//192.168.1.10/example/ObjetoRemoto", objetoRemoto);*/
	  out.println("<p>Publicado");
	  out.println("<p>Fin ..");
	  out.println("</body>");
	  out.println("</html>");
	  
	}


	/**
	 * We are going to perform the same operations for POST requests
	 * as for GET methods, so this method just sends the request to
	 * the doGet method.
	 */
	public void doPost(HttpServletRequest request,HttpServletResponse response)
	  throws IOException, ServletException{

	  doGet(request, response);
	}
	

}
