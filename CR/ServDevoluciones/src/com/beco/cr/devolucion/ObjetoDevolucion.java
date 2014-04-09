package com.beco.cr.devolucion;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Time;
import java.util.Date;

import com.becoblohm.cr.excepciones.AfiliadoUsrExcepcion;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ClienteExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.DevolucionExcepcion;
import com.becoblohm.cr.manejarventa.Devolucion;

public class ObjetoDevolucion extends UnicastRemoteObject implements InterfaceRemota 
{ 
    /**
	 * @throws RemoteException
	 */
	protected ObjetoDevolucion() throws RemoteException {
		super();
		// TODO Apéndice de constructor generado automáticamente
	}

	public Devolucion obtenerDevolucion(int tienda, String fechaT, int cajaOriginal, int numTransaccion, String autorizante,
										int nuevaTienda, Date nuevaFecha, int nuevaCaja, int nuevoRegistro, Time nuevaHora,
										String cajeroActivo) 
	throws DevolucionExcepcion, AfiliadoUsrExcepcion, BaseDeDatosExcepcion, ClienteExcepcion, ConexionExcepcion, RemoteException  
    { 
        return new Devolucion(tienda, fechaT, cajaOriginal, numTransaccion, autorizante,
		nuevaTienda, nuevaFecha, nuevaCaja, nuevoRegistro, nuevaHora, cajeroActivo); 
    } 
}
 
