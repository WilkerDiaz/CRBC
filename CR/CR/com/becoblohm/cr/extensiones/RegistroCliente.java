/*
 * $Id: RegistroCliente.java,v 1.2 2005/03/10 15:54:32 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.extensiones
 * Programa		: RegistroCliente.java
 * Creado por	: Programa8
 * Creado en 	: 21-feb-2005 8:41:19
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
 * $Log: RegistroCliente.java,v $
 * Revision 1.2  2005/03/10 15:54:32  programa8
 * CR al 10/03/2005. Merging desde branch STABLE
 *
 * Revision 1.1.2.2  2005/03/07 13:21:13  programa8
 * Integración Versiones Estable e Inestable al 07/03/2005
 *
 * Revision 1.1.4.1  2005/02/28 18:39:12  programa8
 * Version Inestable al 28/02/2005
 *     *-Preparación para trabajar sin administrador de ventanas.
 *     *-Reordenamiento de GUI
 *     *-Mejoras en scroll y pantallas de pagos
 *     *- Mantenimiento de estado en Cliente en Espera
 *     *- Avisos del sistema manejados por la aplicacion
 *     *- Desbloqueo de caja por otros usuarios reparado.
 *
 * Revision 1.1.2.1  2005/02/22 16:42:30  programa8
 * Ajustes en manejo de Clientes:
 * * División del nombre del cliente en nombre y apellido, para mejorar las
 * 	capacidades de búsqueda y facilitar la integración con los sistemas
 * 	de cliente frecuente
 * * Aplicación de extension de caja, para la actualizacion de clientes
 * en el servidor directamente a la estructura EPA
 *
 * ===========================================================================
 */
package com.becoblohm.cr.extensiones;

import java.sql.Statement;

import com.becoblohm.cr.excepciones.AfiliadoUsrExcepcion;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ClienteExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.manejarventa.Venta;

/**
 * <pre>
 * Proyecto: CR 
 * Clase: RegistroCliente
 * </pre>
 * <p>
 * Extensión del sistema para la actualización en el servidor de los clientes
 * registrados por la caja registradora.
 * <a href="RegistroCliente.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - Arístides Castillo Colmenárez
 * @version 1.0 - 21/02/2005
 * @since 21-feb-2005
 * @
 */
public interface RegistroCliente extends CRExtension {
	/**
	 *  Busca los clientes que han sido registrados en la caja registradora
	 *  y los registra en el servidor para su disponibilidad para las demás
	 *  cajas
	 *
	 */
	public void actualizarClientesSrv();
    public void actualizarTransaccionafiliadocrm();
    public void insertarTransaccionafiliadocrm(Venta venta,  Statement loteSentenciasCR, String fechaTransString);
    public void MostrarPantallaCliente(boolean f4, int asignar);
    public void MostrarPantallaCliente(boolean f4, int asignar, boolean local);
    public boolean isContribuyente(Venta venta);
    public void registrarAsignarCliente(String id, String nombre, String apellido, String ZonaResidencial, String CodArea, String Telefono, char tipoCliente, boolean contribuyente, Cliente clienteEmpleado) 
		throws MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr, AfiliadoUsrExcepcion, BaseDeDatosExcepcion, ClienteExcepcion, UsuarioExcepcion;
    public void registrarAsignarCliente(String id, String nombre, String apellido, String ZonaResidencial, String CodArea, String Telefono, char tipoCliente, boolean contribuyente, Cliente clienteEmpleado, boolean local) 
	throws MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr, AfiliadoUsrExcepcion, BaseDeDatosExcepcion, ClienteExcepcion, UsuarioExcepcion;
}
