/*
 * Cliente.java
 *
 * Ejemplo de muy simple de rmi
 */

package com.beco.cr.devolucion;
import java.rmi.RemoteException;
import java.sql.Time;
import java.util.Date;

import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.manejarventa.Devolucion;

/**
 * Ejemplo de cliente rmi nocivo, para aprovecharse de un servidor sin
 * SecurityManager.
 * @author  Javier Abellán
 */
public class PruebaCte {
    
    /** Crea nueva instancia de Cliente */
    public PruebaCte() 
    {
        try
        {
		// Lugar en el que está el objeto remoto.
		// Debe reemplazarse "localhost" por el nombre o ip donde
		// esté corriendo "rmiregistry".
		// Naming.lookup() obtiene el objeto remoto
            ObjetoDevolucion obj = new ObjetoDevolucion(); 
            Devolucion resultado = obj.obtenerDevolucion(3, "2010-05-19", 52, 38147, null, 3, new Date(), 52, 1, new Time(new Date().getTime()), "00000000");
            System.out.println (".getVentaOriginal().consultarMontoTrans()");
        }
        catch (ExcepcionCr e) 
        {
        	e.printStackTrace();
        } catch (RemoteException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (ConexionExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new PruebaCte();
    }
    
}
