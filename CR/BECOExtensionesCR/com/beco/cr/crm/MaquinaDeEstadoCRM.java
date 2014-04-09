package com.beco.cr.crm;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.AfiliadoUsrExcepcion;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ClienteExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.extensiones.RegistroCliente;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstadoServicio;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.manejarventa.Venta;
import com.becoblohm.cr.mediadoresbd.BeansSincronizador;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.becoblohm.cr.utiles.Validaciones;

import java.sql.Statement;
import java.util.Vector;

public class MaquinaDeEstadoCRM implements RegistroCliente {
	
	private static final Logger logger = Logger.getLogger(MaquinaDeEstadoServicio.class);

	public static Cliente registrarAfiliado(String nombre, String apellido, String id, String telf, String codArea, 
			   String direccion, String telef2, String codArea2, String email, char tipoCliente, boolean contactar, boolean local) 
			   throws MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("asignarCliente(String) - start");}

		Cliente clienteTemp = new Cliente(id, tipoCliente, nombre, apellido, direccion, codArea, telf,'A', codArea2, telef2, email, contactar);
		ManejadorClienteCRM.registrarClienteTemporal(clienteTemp, local);
		
		if (logger.isDebugEnabled()) {
		logger.debug("asignarCliente(String) - end");}
		return clienteTemp;
	}
	
	/* (sin Javadoc)
	 * @see com.becoblohm.cr.extensiones.RegistroCliente#actualizarClientesSrv()
	 * 
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
		try {
			BeansSincronizador.syncEntidadSrv("transaccionafiliadocrm");
		} catch (BaseDeDatosExcepcion e) {
			logger.error("actualizarTransaccionafiliadocrm()", e);
		} catch (SQLException e) {
			logger.error("actualizarTransaccionafiliadocrm()", e);
		}
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void insertarTransaccionafiliadocrm(Venta venta,  Statement loteSentenciasCR, String fechaTransString) {
		//INSERT DE LA NUEVA TABLA TRANSACCIONAFILIADOCRM Crm Wdiaz
		String sentenciaSQL = "", Cedula = "";
		Vector<String> vector = new Vector<String>();
		char contribuyente=' ';
		
		try{
			if (venta.getCliente().getCodCliente() != null)
			{
				contribuyente = (venta.getCliente().isContribuyente())? 'S': 'N';
				sentenciaSQL = "INSERT INTO transaccionafiliadocrm (numtienda,fechatransaccion,numcajafinaliza,numtransaccion,codafiliado,contribuyente,horainiciacrm,horafinalizacrm) VALUES ("+ venta.getCodTienda() +", '"+ fechaTransString+"',"+venta.getNumCajaFinaliza()+","+venta.getNumTransaccion()+",'"+venta.getCliente().getCodCliente()+"','"+contribuyente+"','"+ RegistroClientesNuevosN.getTiempoInicioCrm()+"','"+ RegistroClientesNuevosN.getTiempoFinalCrm()+"')";
				loteSentenciasCR.addBatch(sentenciaSQL);
				vector.addElement(venta.getCliente().getCodCliente());
			}
			if(venta.getPagos().size() > 0)
			{
				RegistroClientesNuevosN.setTiempoInicioCrm(null);
				RegistroClientesNuevosN.setTiempoFinalCrm(null);
				for(int i=0;i < venta.getPagos().size();i++)
				{
					boolean cedRifValido = false;
					Pago p = new Pago();
				    p = (Pago)venta.getPagos().elementAt(i);
					if (p.getCedTitular()!=null)
					{
						contribuyente = 'N';
						Validaciones validador = new Validaciones();
						char aux[] ={Sesion.CLIENTE_VENEZOLANO, Sesion.CLIENTE_JURIDICO, Sesion.CLIENTE_EXTRANJERO};
						
						for (int j = 0; j < aux.length; j++)
						{
							    cedRifValido = validador.validarRifCedula(p.getCedTitular(), aux[j]);
								if(cedRifValido) {
									Cedula = aux[j]+"-"+p.getCedTitular();
									break;
								}
						}
						if (!(vector.contains(Cedula)) && (cedRifValido))
						{
							vector.addElement(Cedula);
							sentenciaSQL = "INSERT INTO transaccionafiliadocrm (numtienda,fechatransaccion,numcajafinaliza,numtransaccion,codafiliado,contribuyente,horainiciacrm,horafinalizacrm) VALUES ("+ venta.getCodTienda() +", '"+ fechaTransString+"',"+venta.getNumCajaFinaliza()+","+venta.getNumTransaccion()+",'"+Cedula+"','"+contribuyente+"','"+ RegistroClientesNuevosN.getTiempoInicioCrm()+"','"+ RegistroClientesNuevosN.getTiempoFinalCrm()+"')";
							loteSentenciasCR.addBatch(sentenciaSQL);
						}
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		//FIN DEL INSERT
	}
	
	public void MostrarPantallaCliente(boolean f4, int asignar)
	{
//		esta condicion se realizo para no volver a mostrar la Pantalla Registrar Clientes CRM cuando ya este asignado
		try{
		if (f4){
			if((CR.meVenta.getVenta().getCliente().getCodCliente() == null)&&(Sesion.Pedir_Datos_Cliente == 'S'))
			{
				RegistroClientesNuevosN Cliente=new RegistroClientesNuevosN();
				MensajesVentanas.centrarVentanaDialogo(Cliente);
				CR.meVenta.getVenta().actualizarMontoTransaccion();
			}
			else
			{
				if (asignar == 0)
				{
					RegistroClientesNuevosN Cliente=new RegistroClientesNuevosN(true);
					MensajesVentanas.centrarVentanaDialogo(Cliente); 
					CR.meVenta.getVenta().actualizarMontoTransaccion();
				} else {
					boolean empleado=false;
					if(CR.meVenta.getVenta().getCliente().getCodCliente() != null){
						empleado=CR.meVenta.getVenta().isAplicaDesctoEmpleado();
					}
					CR.meVenta.getVenta().actualizarPreciosDetalle(empleado);
				}
			}	
		}else
		{
			RegistroClientesNuevosN Cliente=new RegistroClientesNuevosN();
			MensajesVentanas.centrarVentanaDialogo(Cliente);
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isContribuyente(Venta venta){
		return venta.getCliente().isContribuyente();
	}

	public void MostrarPantallaCliente(boolean f4, int asignar, boolean local) {
		try{
			if (f4){
				if((CR.meVenta.getVenta().getCliente().getCodCliente() == null)&&(Sesion.Pedir_Datos_Cliente == 'S'))
				{
					RegistroClientesNuevosN Cliente=new RegistroClientesNuevosN();
					Cliente.setLocal(local);
					MensajesVentanas.centrarVentanaDialogo(Cliente);
					CR.meVenta.getVenta().actualizarMontoTransaccion();
				}
				else
				{
					if (asignar == 0)
					{
						RegistroClientesNuevosN Cliente=new RegistroClientesNuevosN(true);
						Cliente.setLocal(local);
						MensajesVentanas.centrarVentanaDialogo(Cliente); 
						CR.meVenta.getVenta().actualizarMontoTransaccion();
					} else {
						boolean empleado=false;
						if(CR.meVenta.getVenta().getCliente().getCodCliente() != null){
							empleado=CR.meVenta.getVenta().isAplicaDesctoEmpleado();
						}
						CR.meVenta.getVenta().actualizarPreciosDetalle(empleado);
					}
				}	
			}else
			{
				RegistroClientesNuevosN Cliente=new RegistroClientesNuevosN();
				Cliente.setLocal(local);
				MensajesVentanas.centrarVentanaDialogo(Cliente);
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	
	
	public void registrarAsignarCliente(String id, String nombre, String apellido, String ZonaResidencial, String CodArea, String Telefono, char tipoCliente, boolean contribuyente, Cliente clienteEmpleado) 
		throws MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr, AfiliadoUsrExcepcion, BaseDeDatosExcepcion, ClienteExcepcion, UsuarioExcepcion {
		registrarAsignarCliente(id, nombre, apellido, ZonaResidencial, CodArea, Telefono, tipoCliente, contribuyente, clienteEmpleado, true);
	}

	public void registrarAsignarCliente(String id, String nombre, String apellido, String ZonaResidencial, String CodArea, String Telefono, char tipoCliente, boolean contribuyente, Cliente clienteEmpleado, boolean local) throws MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr, AfiliadoUsrExcepcion, BaseDeDatosExcepcion, ClienteExcepcion, UsuarioExcepcion {
		
		Cliente clienteRegistrado = null;
		if ((clienteEmpleado == null)|| (clienteEmpleado.getNumFicha() == null || clienteEmpleado.getNumFicha().equals("")))		
			clienteRegistrado = MaquinaDeEstadoCRM.registrarAfiliado(nombre, apellido,id, Telefono,CodArea, 
					   ZonaResidencial, "", "", "", tipoCliente,false, local) ;
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
	
}
