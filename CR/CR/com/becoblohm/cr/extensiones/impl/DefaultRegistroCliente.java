/*
 * $Id: DefaultRegistroCliente.java,v 1.2 2005/03/10 15:54:36 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.extensiones.impl
 * Programa		: DefaultRegistroCliente.java
 * Creado por	: Programa8
 * Creado en 	: 21-feb-2005 9:09:12
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
 * $Log: DefaultRegistroCliente.java,v $
 * Revision 1.2  2005/03/10 15:54:36  programa8
 * CR al 10/03/2005. Merging desde branch STABLE
 *
 * Revision 1.1.2.2  2005/03/07 13:22:22  programa8
 * Integración Versiones Estable e Inestable al 07/03/2005
 *
 * Revision 1.1.4.1  2005/02/28 18:39:16  programa8
 * Version Inestable al 28/02/2005
 *     *-Preparación para trabajar sin administrador de ventanas.
 *     *-Reordenamiento de GUI
 *     *-Mejoras en scroll y pantallas de pagos
 *     *- Mantenimiento de estado en Cliente en Espera
 *     *- Avisos del sistema manejados por la aplicacion
 *     *- Desbloqueo de caja por otros usuarios reparado.
 *
 * Revision 1.1.2.1  2005/02/22 16:42:31  programa8
 * Ajustes en manejo de Clientes:
 * * División del nombre del cliente en nombre y apellido, para mejorar las
 * 	capacidades de búsqueda y facilitar la integración con los sistemas
 * 	de cliente frecuente
 * * Aplicación de extension de caja, para la actualizacion de clientes
 * en el servidor directamente a la estructura EPA
 *
 * ===========================================================================
 */
package com.becoblohm.cr.extensiones.impl;

import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.beco.cr.crm.MaquinaDeEstadoCRM;
import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.AfiliadoUsrExcepcion;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ClienteExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.extensiones.RegistroCliente;
import com.becoblohm.cr.gui.EjecutarConCantidad;
import com.becoblohm.cr.gui.RegistroClientesTemp;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.manejarventa.Venta;
import com.becoblohm.cr.mediadoresbd.BeansSincronizador;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.utiles.MensajesVentanas;

/**
 * <pre>
 * Proyecto: CR 
 * Clase: DefaultRegistroCliente
 * </pre>
 * <p>
 * <a href="DefaultRegistroCliente.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - $Author: programa8 $
 * @version $Revision: 1.2 $ - $Date: 2005/03/10 15:54:36 $
 * @since 21-feb-2005
 * @
 */
public class DefaultRegistroCliente implements RegistroCliente {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(DefaultRegistroCliente.class);
	private static EjecutarConCantidad ec = null;
	private static RegistroClientesTemp clientTemp=null;

	/* (sin Javadoc)
	 * @see com.becoblohm.cr.extensiones.RegistroCliente#actualizarClientesSrv()
	 * z
	 * @since 21-feb-2005
	 */
	public void actualizarClientesSrv() {
		try {
			BeansSincronizador.syncEntidadSrv("afiliado");
		} catch (BaseDeDatosExcepcion e) {
			logger.error("actualizarClientesSrv()", e);
		} catch (SQLException e) {
			logger.error("actualizarClientesSrv()", e);
		}
	}
	public void actualizarTransaccionafiliadocrm() {
	}
	public void insertarTransaccionafiliadocrm(Venta venta, Statement loteSentenciasCR, String fechaTransString) {
	}
	public void MostrarPantallaCliente(boolean f4, int asignar) {
		if ((!(f4))&&(asignar!=1))
		{
			ec =null;
			ec = new EjecutarConCantidad("Asignar un Cliente:", "",asignar);
			MensajesVentanas.centrarVentanaDialogo(ec);
		}
		else
			if (asignar==1)
			{
				clientTemp = new RegistroClientesTemp();
				MensajesVentanas.centrarVentanaDialogo(clientTemp);
			}
	}
	
	public boolean isContribuyente(Venta venta){
		return true;
	}
	
	
	public void MostrarPantallaCliente(boolean f4, int asignar, boolean local) {
		if ((!(f4))&&(asignar!=1))
		{
			ec =null;
			ec = new EjecutarConCantidad("Asignar un Cliente:", "",asignar);
			MensajesVentanas.centrarVentanaDialogo(ec);
		}
		else
			if (asignar==1)
			{
				clientTemp = new RegistroClientesTemp();
				MensajesVentanas.centrarVentanaDialogo(clientTemp);
			}
		
	}
	
	public void registrarAsignarCliente(String id, String nombre, String apellido, String ZonaResidencial, String CodArea, String Telefono, char tipoCliente, boolean contribuyente, Cliente clienteEmpleado) throws MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr, AfiliadoUsrExcepcion, BaseDeDatosExcepcion, ClienteExcepcion, UsuarioExcepcion {
		Cliente clienteRegistrado = null;
		if ((clienteEmpleado == null)|| (clienteEmpleado.getNumFicha() == null || clienteEmpleado.getNumFicha().equals("")))		
			clienteRegistrado = MaquinaDeEstadoCRM.registrarAfiliado(nombre, apellido,id, Telefono,CodArea, 
					   ZonaResidencial, "", "", "", tipoCliente,false, true) ;
		else{
			clienteRegistrado = MediadorBD.obtenerCliente(id);
		}
				
		if(CR.meVenta.getVenta() != null || (CR.meVenta.getVenta() == null && CR.meVenta.getDevolucion() == null && CR.meServ.getVentaBR()==null))
		   CR.meVenta.asignarCliente(id);
		else
			if(CR.meVenta.getDevolucion() != null)
				CR.meVenta.asignarClienteDevolucion(id);
			else if (CR.meServ.getVentaBR()!=null)
				CR.meServ.asignarClienteVentaBR(clienteRegistrado);

		boolean existiaVentaActiva = (CR.meVenta.getVenta()!= null) ? true : false;
		if (existiaVentaActiva)
			CR.meVenta.getVenta().getCliente().setContribuyente(contribuyente);
		
	}
	public void registrarAsignarCliente(String id, String nombre, String apellido, String ZonaResidencial, String CodArea, String Telefono, char tipoCliente, boolean contribuyente, Cliente clienteEmpleado, boolean local) throws MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr, AfiliadoUsrExcepcion, BaseDeDatosExcepcion, ClienteExcepcion, UsuarioExcepcion {
		this.registrarAsignarCliente(id, nombre, apellido, ZonaResidencial, CodArea, Telefono, tipoCliente, contribuyente, clienteEmpleado);
	}
	

}
