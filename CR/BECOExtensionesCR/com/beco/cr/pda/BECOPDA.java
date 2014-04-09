/*
 * ===========================================================================
 * Material Propiedad CentroBeco C.A. 
 *
 * Proyecto		: CR
 * Paquete		: com.beco.cr.pda
 * Programa		: BECOPDA.java
 * Creado por	: varios autores
 * Creado en 	: 10-08-2009 
 * (C) Copyright 2005 CentroBeco C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * ===========================================================================
 */
package com.beco.cr.pda;

import java.util.LinkedHashMap;
import java.util.Vector;

import javax.swing.JDialog;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.PagoExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.extensiones.PDA;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstado;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarusuario.Usuario;
import com.becoblohm.cr.manejarventa.DetalleTransaccion;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;

public class BECOPDA implements PDA{
	//codigo de Barras utilizado para la operación iniciarsesion
	private static String codigoBarra;
	//clave utilizada para la operación iniciarsesion
	private static String clave;
	/**
	 * Método iniciarVerificador coloca el estado de caja en INICIADO y coloca un usuario por defecto 
	 * @author mmiyazono jgraterol
	 * @param params 
	 * @return void
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'LinkedHashMap'
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unchecked")
	public void iniciarVerificador(String[] params) {
 	
		// Cargamos la preferencia de Servidor de PDA
		LinkedHashMap<String,Object> preferencias;
		try {
			preferencias = InitCR.preferenciasCR.getAllPreferecesForNode("facturacion");
			Sesion.tiempoTimeOut = Integer.parseInt(preferencias.get("timeOutPDA").toString());
			Sesion.ipServidorPDA = preferencias.get("ipServidorPDA").toString();
			Sesion.puertoServidorPDA = Integer.parseInt(preferencias.get("puertoServidorPDA").toString());
			Sesion.puertoEscuchaCRPDA = Integer.parseInt(preferencias.get("puertoEscuchaCRPDA").toString());
		} catch (NoSuchNodeException e2) {
		} catch (UnidentifiedPreferenceException e2) {}
		
		//Actualizamos el estado de la caja
		try {
			Sesion.getCaja().setEstado(Sesion.INICIADA);
		} catch (BaseDeDatosExcepcion e1) {
			// TODO Bloque catch generado automáticamente
			e1.printStackTrace();
		} catch (ConexionExcepcion e1) {
			// TODO Bloque catch generado automáticamente
			e1.printStackTrace();
		}
		
		//Se abre la conexión con el socket de comunicación y se esperan las peticiones de PDA
		 new ManejadorPeticion(Sesion.puertoEscuchaCRPDA);
	}

	/**
	 * Método recuperarFacturaEnEspera recupera una venta en espera
	 * @author mmiyazono jgraterol
	 * @param mensaje 
	 * @return void
	 */
	public  void recuperarFacturaEnEspera(MensajePDA mensaje){
		try {
			CR.meVenta.recuperarFacturaEspera(mensaje.getIdEspera());
		} catch (UsuarioExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
			mensaje.setMensaje(e.getMensaje());
		} catch (MaquinaDeEstadoExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
			mensaje.setMensaje(e.getMensaje());
		} catch (ConexionExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
			mensaje.setMensaje(e.getMensaje());
		} catch (ExcepcionCr e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
			mensaje.setMensaje(e.getMensaje());
		}
		
	}
	
	/**
	 * Método totalizar llama al f4 de totalizar una venta para que se calculen las formas de pago
	 * @author mmiyazono jgraterol
	 * @return void
	 */
	public  void totalizar(){
			CR.meVenta.ejecucionPromocionesPDA();
	}
	
	/**
	 * Método cancelar llama al f9 para que se limpie la memoria
	 * @author mmiyazono jgraterol
	 * @return void
	 */
	public void cancelar(){
		try {
			if(CR.meVenta.getVenta()!=null)
				CR.meVenta.anularVentaActiva();
		} catch (UsuarioExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (MaquinaDeEstadoExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (PagoExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (ConexionExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (ExcepcionCr e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
	}
	
	/**
	 * Método mensajesPorPantalla
	 * @author mmiyazono
	 * @param titulo
	 * @param mensaje
	 * @param urlIcon
	 * @param inicio
	 * @param ventanaActiva
	 * @return void
	 */
	public void mensajesPorPantalla(String titulo, String mensaje, String urlIcon, boolean inicio, JDialog ventanaActiva ) {}
	
	/**
	 * Método buscarDetallesProductos ingresa las lineas de producto en la venta activa
	 * @author mmiyazono jgraterol
	 * @param mensaje 
	 * @return void
	 */
	public void buscarDetallesProductos(MensajePDA mensaje){
		
		for (int i = 0; i < mensaje.getDetalles().size(); i++) {
			DetalleProducto producto = (DetalleProducto) mensaje.getDetalles().elementAt(i);
			try {
				for (int j = 0; j < producto.getCantidad(); j++) {
					
					try{
						CR.meVenta.ingresarLineaProducto(producto.getCodProducto(), null, Sesion.PROCESO + "", true);
					}catch(Exception ex1){
						CR.meVenta.ingresarLineaProducto(producto.getCodProducto().substring(0,producto.getCodProducto().length()-1), null, Sesion.PROCESO + "", false);
					}
				}
			} catch (UsuarioExcepcion e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
				cancelar();
				mensaje.setMensaje(e.getMensaje());
				break;
			} catch (MaquinaDeEstadoExcepcion e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
				cancelar();
				mensaje.setMensaje(e.getMensaje());
				break;
			} catch (ConexionExcepcion e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
				cancelar();
				mensaje.setMensaje(e.getMensaje());
				break;
			} catch (ExcepcionCr e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
				cancelar();
				mensaje.setMensaje(e.getMensaje() + ": " + producto.getCodProducto());
				break;
			}
		}
	}
	
	/**
	 * Método colocarEnEspera coloca la venta activa en espera
	 * @author mmiyazono jgraterol
	 * @param mensaje
	 * @return void
	 */
	public void colocarEnEspera(MensajePDA mensaje){
		try {
			CR.meVenta.colocarFacturaEspera(mensaje.getIdEspera()) ;
			mensaje.setMensaje("Operacion exitosa");
			mensaje.setHayError(false);
		} catch (MaquinaDeEstadoExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
			cancelar();
			mensaje.setMensaje(e.getMensaje());
		} catch (XmlExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
			cancelar();
			mensaje.setMensaje(e.getMensaje());
		} catch (FuncionExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
			cancelar();
			mensaje.setMensaje(e.getMensaje());
		} catch (BaseDeDatosExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
			cancelar();
			mensaje.setMensaje(e.getMensaje());
		} catch (PagoExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
			cancelar();
			mensaje.setMensaje(e.getMensaje());
		} catch (ConexionExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
			cancelar();
			mensaje.setMensaje(e.getMensaje());
		}
	}
	
	/**
	 * Método crearMensajeRetorno crea el mensaje de retorno para una solicitud de consulta de precios para el PDA.
	 * @author mmiyazono jgraterol
	 * @param mensaje
	 * @return void
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void crearMensajeRetorno(MensajePDA mensaje){
		Vector<DetalleProducto> detallesProductos = new Vector<DetalleProducto>();

		for (int i = 0; i < CR.meVenta.getVenta().getDetallesTransaccion().size(); i++) {
			DetalleTransaccion detalleTrans = (DetalleTransaccion) CR.meVenta.getVenta().getDetallesTransaccion().elementAt(i);
			DetalleProducto detalleProd = new DetalleProducto(detalleTrans.getProducto().getCodProducto(), 
					detalleTrans.getProducto().getDescripcionCorta(), 
					detalleTrans.getProducto().getPrecioRegular(), 
					detalleTrans.getPrecioFinal(), 
					detalleTrans.getCantidad(), 
					(detalleTrans.getPrecioFinal() * detalleTrans.getCantidad()), 
					detalleTrans.getCondicionVenta());
			detallesProductos.add(detalleProd);
		}
		mensaje.setDetalles(detallesProductos);
		Vector<Pago> promociones = CR.meVenta.getVenta().getPagos();
		double montoDescuento = 0.0;
		for (int i = 0; i < promociones.size(); i++) {
			Pago pago = (Pago) promociones.elementAt(i);
			montoDescuento = montoDescuento + pago.getMonto();
		}
		mensaje.setMontoDescuento(montoDescuento);
		try {
			mensaje.setMontoTotal(CR.meVenta.consultarMontoTransPDA());
			mensaje.setMensaje("Operacion exitosa");
			mensaje.setHayError(false);
		} catch (UsuarioExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
			cancelar();
		} catch (MaquinaDeEstadoExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
			cancelar();
		} catch (ConexionExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
			cancelar();
		} catch (ExcepcionCr e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
			cancelar();
		}
	}
	
	/**
	 * Maneja la verificación de la información de un usuario.
	 * 
	 * @author mgrillo
	 * @param mensajeEntrada
	 * @return void
	 */
	public void iniciarSesion(MensajePDA mensajeEntrada){
		try {
			String id = mensajeEntrada.getCodigoDeBarras();
			BECOPDA.codigoBarra = id;
			String clave = mensajeEntrada.getClave();
			BECOPDA.clave = clave;
			String metodo = mensajeEntrada.getNombreMetodo();
			String modulo = mensajeEntrada.getNombreModulo();
			//boolean usuarioCorrecto = (new Usuario()).validar(id,clave);
			//if(!usuarioCorrecto)
			//	throw new Exception("el codigo de barras no existe");
			MaquinaDeEstado.validarUsuario(id, clave, false);
			new MaquinaDeEstado().verificarAutorizacion(modulo,metodo);
		} catch (Exception e) {
			mensajeEntrada.setHayError(true);
			mensajeEntrada.setMensaje("inicio de sesión fallido: "+e.getMessage());
		}
	}
	/**
	 * 
	 */
	public boolean esModuloPDA(){
		return true;
	}
	/**
	 * Método consultaPDA realiza la consulta solicitada: Consulta precios, Coloca una venta en espera o Recupera una venta en espera o iniciar sesion
	 * @author mmiyazono jgraterol
	 * @param mensaje
	 * @return void
	 */
	public void consultaPDA(MensajePDA mensajeEntrada){

		if( mensajeEntrada.getNombreOperacion().equals(Sesion.pdaTipoMensajeIniciarSesion)){
			//Inicio Sesión
			iniciarSesion(mensajeEntrada);

		}else if( mensajeEntrada.getNombreOperacion().equals(Sesion.pdaTipoMensajeConsultaPrecio)){
			//Consulta precios
			buscarDetallesProductos(mensajeEntrada);
			mensajeEntrada.setTipoConsulta(Sesion.pdaTipoMensajeConsultaPrecio);
			if(mensajeEntrada.getMensaje().equalsIgnoreCase("")){
				totalizar();
				crearMensajeRetorno(mensajeEntrada);
				cancelar();
			}
		}else if (mensajeEntrada.getNombreOperacion().equals(Sesion.pdaTipoMensajeVentaEspera)){
			//Coloca venta en espera
			buscarDetallesProductos(mensajeEntrada);
			mensajeEntrada.setTipoConsulta(Sesion.pdaTipoMensajeVentaEspera);
			if(mensajeEntrada.getMensaje().equalsIgnoreCase("")){
				colocarEnEspera(mensajeEntrada);
			}
		}else if(mensajeEntrada.getNombreOperacion().equals(Sesion.pdaTipoMensajeRecuperarVenta)){
			//Recuperar venta en espera
			recuperarFacturaEnEspera(mensajeEntrada);
			mensajeEntrada.setTipoConsulta(Sesion.pdaTipoMensajeRecuperarVenta);
			if(mensajeEntrada.getMensaje().equalsIgnoreCase("")){
				totalizar();
				crearMensajeRetorno(mensajeEntrada);
				cancelar();
			} 
		}
		/*
		if( mensajeEntrada.getNombreOperacion().equals(Sesion.pdaTipoMensajeIniciarSesion)){
			//Inicio Sesión
			iniciarSesion(mensajeEntrada);

		}else if( mensajeEntrada.getIdEspera().equalsIgnoreCase("") && mensajeEntrada.getDetalles().size() > 0){
			//Consulta precios
			buscarDetallesProductos(mensajeEntrada);
			mensajeEntrada.setTipoConsulta(Sesion.pdaTipoMensajeConsultaPrecio);
			if(mensajeEntrada.getMensaje().equalsIgnoreCase("")){
				totalizar();
				crearMensajeRetorno(mensajeEntrada);
				cancelar();
			}
		}else if (!mensajeEntrada.getIdEspera().equalsIgnoreCase("") && mensajeEntrada.getDetalles().size() > 0){
			//Coloca venta en espera
			buscarDetallesProductos(mensajeEntrada);
			mensajeEntrada.setTipoConsulta(Sesion.pdaTipoMensajeVentaEspera);
			if(mensajeEntrada.getMensaje().equalsIgnoreCase("")){
				colocarEnEspera(mensajeEntrada);
			}
		}else if(!mensajeEntrada.getIdEspera().equalsIgnoreCase("") && mensajeEntrada.getDetalles().size() == 0){
			//Recuperar venta en espera
			recuperarFacturaEnEspera(mensajeEntrada);
			mensajeEntrada.setTipoConsulta(Sesion.pdaTipoMensajeRecuperarVenta);
			if(mensajeEntrada.getMensaje().equalsIgnoreCase("")){
				totalizar();
				crearMensajeRetorno(mensajeEntrada);
				cancelar();
			} 
		}
		*/
	}

	/**
	 * Función usuarioActivoOUsuarioPDA es llamada por la función verificarAutorizacion de la clase Usuario
	 * para determinar sobre que usuario se realizarán las verificaciones del pda. Se llama esta función ya que
	 * en esta clase se posee la información del usuario que se verificará en esa función.
	 * @param usuarioActivo posee el usuario activo de la caja registradora.
	 * @throws Exception si no se puede llenar el usuario manejado por el pda
	 */
	public Usuario usuarioActivoOUsuarioPDA(Usuario usuarioActivo)throws Exception {
		Usuario usuarioPDA = new Usuario();
		usuarioPDA.setCodigoBarra(BECOPDA.codigoBarra);
		usuarioPDA.setClave(BECOPDA.clave);
		try{
			usuarioPDA = usuarioPDA.identificar(BECOPDA.codigoBarra, BECOPDA.clave);
		}catch(Exception e){
			throw new Exception("No se pudo identificar al usuario "+BECOPDA.codigoBarra+", "+e.getMessage());
		}
		return usuarioPDA;
	}
}
