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
public class LoadServerDesarrollo extends HttpServlet {

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
			/*System.setProperty(
				"java.rmi.server.codebase",
				"file:/www/crserver/webapps/ServicioRMI/WEB-INF/classes/");*/
			System.setProperty(
				"java.rmi.server.codebase",
				"file:/www/jspserver/webapps/RMITest2/WEB-INF/classes/");
			out.println("<p>Hecho");
			out.println("<p>Voy a publicar el Objeto");
			try {
				objetoRemoto = new ObjetoDevolucion();
				//Naming.rebind("//192.168.1.2/ServicioRMI/ObjetoDevolucion", objetoRemoto);
				Naming.rebind("//192.168.1.10/example/ObjetoDevolucion", objetoRemoto);
				out.println("---> Publicado");
			} catch (Exception e) {
			  out.println("---> Objeto NO Publicado ");
			  out.println(e.toString());
			}
			out.println("<p>Fin ..");
			out.println("</body>");
			out.println("</html>");

	}

	/**
	* @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	*/
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {

	}

	/**
	* @see javax.servlet.GenericServlet#void ()
	*/
	public void init() throws ServletException {

		super.init();

	}

}
