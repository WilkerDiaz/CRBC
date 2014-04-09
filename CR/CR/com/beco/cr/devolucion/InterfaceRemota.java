package com.beco.cr.devolucion;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Time;
import java.util.Date;

import com.becoblohm.cr.excepciones.AfiliadoUsrExcepcion;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ClienteExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.DevolucionExcepcion;
import com.becoblohm.cr.manejarventa.Devolucion;

/*
 * Creado el 14-ago-06
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
public interface InterfaceRemota extends Remote{
	public Devolucion obtenerDevolucion(int tienda, String fechaT, int cajaOriginal, int numTransaccion, String autorizante,
									int nuevaTienda, Date nuevaFecha, int nuevaCaja, int nuevoRegistro, Time nuevaHora,
									String cajeroActivo) 
	throws DevolucionExcepcion, AfiliadoUsrExcepcion, BaseDeDatosExcepcion, ClienteExcepcion, ConexionExcepcion, RemoteException;  
}
