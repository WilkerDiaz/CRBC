package com.beco.cr.devolucion;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.Naming;

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
public class Servidor extends HttpServlet {
	
	public InterfaceRemota objetoRemoto;
	
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
	  try { 
		  Runtime.getRuntime().exec("rmiregistry");
		  //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  //out.println("<p>Esperamos 5 segundos " + df.format(new Date()));
		  //Thread.sleep(5000);
		  //out.println("---> Espera Finalizada " + df.format(new Date()) + ".");
	  } catch (Exception e) {
	  	e.printStackTrace();
	  }
	  out.println("<p>Iniciado");
	  out.println("<p>Voy a hacer el setProperty");
	  System.setProperty(
		  "java.rmi.server.codebase",
		  //"file:/www/crserver/webapps/ServicioRMI/WEB-INF/classes/");
	  		"file:/opt/glassfishv3/glassfish/domains/domain1/applications/ServDevoluciones/WEB-INF/classes/");
		  
	  out.println("<p>Hecho");
	  out.println("<p>Voy a publicar el Objeto");
	  try {
		  objetoRemoto = new ObjetoDevolucion();
		 // Naming.rebind("//192.168.1.215/ServicioRMI/ObjetoDevolucion", objetoRemoto);
		  Naming.rebind("//192.168.1.80:9090/ServDevoluciones/ObjetoDevolucion", objetoRemoto);
		  //http://192.168.1.80:9090/IndicadoresV/Index.html
	 	  out.println("---> Publicado");
	  } catch (Exception e) {
		out.println("---> Objeto NO Publicado ");
		out.println(e.toString());
	  }
	  
	  
	/*  out.println("<p>Tratamos de Recuperar la Devolucion");
	  try {
		Devolucion dev = new Devolucion(3, "2006-04-03", 57, 5, null,
				  3, new Date(), 57, 10, new Time(new Date().getTime()), "00000000");
		out.println("<p>Devolucion Recuperada");
		out.println("<p>El Monto es: " + dev.getVentaOriginal().consultarMontoTrans());
	} catch (DevolucionExcepcion e) {
		out.println("<p>Devolucion NO Recuperada");
		out.println("<p>" + e.getMensaje());
	} catch (AfiliadoUsrExcepcion e) {
		out.println("<p>Devolucion NO Recuperada");
		out.println("<p>" + e.getMensaje());
	} catch (BaseDeDatosExcepcion e) {
		out.println("<p>Devolucion NO Recuperada");
		out.println("<p>" + e.getMensaje());
	} catch (ClienteExcepcion e) {
		out.println("<p>Devolucion NO Recuperada");
		out.println("<p>" + e.getMensaje());
	} catch (ConexionExcepcion e) {
		out.println("<p>Devolucion NO Recuperada");
		out.println("<p>" + e.getMensaje());
	}*/
							
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
