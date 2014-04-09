package com.beco.cr.devolucion;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.Naming;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * @version 	1.0
 * @author
 */
public class LoadServer extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public InterfaceRemota objetoRemoto;
	
	/**
	* @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	*/
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
			
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
				//Dirección donde se encuentra en Java Instalado donde se montará en RMI
				Runtime.getRuntime().exec("/usr/java/jre1.6.0_27/bin/rmiregistry");
			} catch (Exception e) {
			  e.printStackTrace();
			}
			out.println("<p>Iniciado");
			out.println("<p>Voy a hacer el setProperty");
			//Dirección de las clases del proyecto, donde el Servidor de Aplicaciones las guarda en una carpeta
			System.setProperty(
				"java.rmi.server.codebase", 
				"file:/etc/apache-tomcat-6/webapps/ServDevolucion/WEB-INF/classes/");  
			out.println("<p>Hecho");
			out.println("<p>Voy a publicar el Objeto");
			boolean publicado = false;
			int repeticion = 5;
			int i = 0;
			while (!publicado) {
				try {
					if (i++ == repeticion)
						break;
					objetoRemoto = new ObjetoDevolucion();
					Naming.rebind("//192.168.1.126/ServicioRMI/ObjetoDevolucion", objetoRemoto);
					 out.println("---> ARRANCANDO: <br>");
					publicado = true;
				} catch (Exception e) {
				  out.println("---> ERROR:");
				  out.println(e.getMessage());
				  out.println("<br>");
				  //break;
				}
			}
			if (i < 5)
			{
				out.println("---> PUBLICADO");
				out.println("<p>Fin ..");
				out.println("</body>");
				out.println("</html>");
			}
			else
				out.println("---> OBJETO NO PUBLICADOOOOOOOOOO");
	}

	/**
	* @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	*/
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		System.out.println("hola");
	}

	
public void init() throws ServletException {
	
		super.init();

	}
	

}
