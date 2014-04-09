/*
 * Cliente.java
 *
 * Ejemplo de muy simple de rmi
 */

package com.beco.cr.devolucion;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.rmi.Naming;
import java.sql.Time;
import java.util.Date;

import com.becoblohm.cr.manejarventa.Devolucion;

/**
 * Ejemplo de cliente rmi nocivo, para aprovecharse de un servidor sin
 * SecurityManager.
 * @author  Javier Abellán
 */
public class Cliente {
    
    /** Crea nueva instancia de Cliente 
     * @throws Exception */
    public Cliente() throws Exception 
    {
    	Devolucion devolucion = null;
    	
		InterfaceRemota objetoRemoto = null;
		try {
			objetoRemoto = (InterfaceRemota)Naming.lookup ("//192.168.1.10/example/ObjetoDevolucion");//"//192.168.1.2/ServicioRMI/ObjetoDevolucion");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Maloooo.....");
			String ruta = null;
        	// Error de Conexion, levantamos el RMI desde la caja y volvemos a intentar recuperar la recuperacion
			File rutaRMI = new File("RMI.txt");
		    if (rutaRMI.exists()) {
				BufferedReader entrada = new BufferedReader(new FileReader(rutaRMI));
				ruta = entrada.readLine();
				entrada.close();
		    } else
		    	throw e;
		   
		    System.out.println(ruta);
		    if (ruta != null && !ruta.equals("")) {
				String comando = new String("wget " + ruta); //http://192.168.1.2:8002/ServicioRMI/Servidor");
				System.out.println(comando);
				Process p = Runtime.getRuntime().exec(comando);
				
				int exitValue = -1;
				do {
					System.out.println(exitValue);
					try {
						Thread.sleep(1000);
						exitValue = p.exitValue();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} while (exitValue < 0);
	
				objetoRemoto = (InterfaceRemota)Naming.lookup ("//192.168.1.10/example/ObjetoDevolucion");//"//"//192.168.1.2/ServicioRMI/ObjetoDevolucion");
		    }
		}
		devolucion = objetoRemoto.obtenerDevolucion(3, "2009-12-16", 61, 707, null, 3, new Date(), 53, 1, new Time(new Date().getTime()), "00000000");
		//Validar cliente que el cliente de la venta original sea diferente al cajero y al autorizante
		System.out.println (devolucion.getVentaOriginal().consultarMontoTrans());
	

    }
    
    /**
     * @param args the command line arguments
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        new Cliente();
    }
    
}
