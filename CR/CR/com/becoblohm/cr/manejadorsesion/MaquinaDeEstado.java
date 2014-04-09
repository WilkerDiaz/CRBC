/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.mediadorinterfaz
 * Programa   : MaquinaDeEstado.java
 * Creado por : apeinado
 * Creado en  : 06/10/2003 03:28:00 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 2.1
 * Fecha       : 06/11/2004 11:19 AM
 * Analista    : yzambrano
 * Descripción : - No solicitar información de serial fiscal si la impresora no 
 * 				   está conectada
 * =============================================================================
 * Versión     : 1.2.0
 * Fecha       : 01/07/2004 11:19 AM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : - Agregada la llamada para cambiar contraseña en el método 
 * 				desbloquearCaja.
 * 				 - Organizada la visualización de los mensajes de error.
 * 				 - Modificado el método isConexion para que reciba una dirección 
 * 				ip en lugar de un indicador de la conexión que se desea.
 * =============================================================================
 * Versión     : 1.8.0 
 * Fecha       : 29/06/2004 09:23 AM
 * Analista    : gmartinelli
 * Descripción : 	- Agregados los métodos cerrarSesionUsuario y cambiarClave.
 * 						- Modificada implementación del método cerrarSesión de modo que se verifique que la caja esta
 * 					abierta para facturación por lo que debe solicitar autorización para cerrar el sistema.
 * =============================================================================
 * Versión     : 1.7.4 
 * Fecha       : 25/05/2004 15:01 PM
 * Analista    : gmartinelli
 * Descripción : Agregado metodos para el manejo de devoluciones:
 * 					- iniciarDevolucion: Inicia una transaccion de devolucion.
 * 					- devolverProducto: Ingresa un producto a la devolucion.
 * 					- finalizarDevolucion: Finaliza y registra la transaccion de devolucion.
 * 					- anularDevolucionEnLinea: Anula la devolucion en línea.
 * 				 Agregado el atributo de devolucion que contiene la devolucion activa
 * 				 Modificado el metodo obtenerRenglones que, dependiendo de si existe una venta
 * 				o una devolucion activa busca los renglones en la devolucion o en la venta.
 * =============================================================================
 * Versión     : 1.7.3 
 * Fecha       : 22/03/2004 10:15 AM
 * Analista    : IROJAS
 * Descripción : Agregado verificaciones de los detalles de transaccion para evitar que
 * 				se registren ventas con detalles de transaccion vacios.
 * =============================================================================
 * Versión     : 1.7.2 
 * Fecha       : 11/03/2004 8:00 AM
 * Analista    : IROJAS
 * Descripción : Se captura otro tipo excepción:"PagoExcepcion".
 * =============================================================================
 * Versión     : 1.7.1 
 * Fecha       : 10/03/2004 8:00 AM
 * Analista    : GMARTINELLI
 * Descripción : Cambiado metodo finalizarVenta para que devuelva un vector.
 * =============================================================================
 * Versión     : 1.7 
 * Fecha       : 08/03/2004 11:40 AM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Se agregó el método "recuperarComanda" para que retorne las comandas 
 * 				 correspondientes al cliente indicado de modo que sea su detalle agregado
 * 				 al detalle de la facturación actual.
 * =============================================================================
 * Versión     : 1.6 
 * Fecha       : 05/03/2004 11:48 AM
 * Analista    : irojas
 * Descripción : Se cambió método "verificarAutorización" para que retorne el 
 * 				 número de ficha del usuario que autorizó la función que lo llamó.
 * 				 	Se cambió metodos "aplicarDesctosPorDefecto" y "cambiarPrecioDetalle"
 * 				 para que capture el número de ficha que devuelve el método "verificarAutorizacion"
 * 				 y se los pase a los métodos de la clase Venta para que sean asignados a los detalles nuevos.
 * =============================================================================
 * Versión     : 1.5.10 
 * Fecha       : 04/03/2004 01:27 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Eliminado el métodos iniciarUsuario y solicitarAutorizacion
 * 				 por estar sus funcionalidades ya implementadas en el método 
 * 				 accesoValido de la clase Control.
 * 					Agregado el método validarAcceso que permite emplear el método
 * 				 accesoValido de la clase Control.
 * =============================================================================
 * Versión     : 1.5.9 
 * Fecha       : 02/03/2004 11:27 AM
 * Analista    : irojas
 * Descripción : Modificada manera de solicitar autorización al nuevo concepto
 * 				 en los siguientes metodos:
 * 					* cambiarPrecioDetalle
 * 					* aplicarDesctoPorDefecto
 * 					* anularProducto
 * 					* ingresarLineaProducto
 * 					* cambiarCantidad
 * 					* consultarMontoTrans
 * 					* efectuarPago
 * 					* finalizarVenta
 * 					* validarVendedor
 * 					* anularVentaActiva
 * 					* asignarCliente
 * =============================================================================
 * Versión     : 1.5.8 
 * Fecha       : 02/03/2004 11:27 AM
 * Analista    : irojas
 * Descripción : Agregado método "verificarAutorizacion"
 * =============================================================================
 * Versión     : 1.5.7 
 * Fecha       : 02/03/2004 11:21 AM
 * Analista    : irojas
 * Descripción : En el metodo "solicitarAutorizacion" agregada línea que carga los 
 * 				 datos en memoria del usuarioAutorizante: 
				 Sesion.usuarioAutorizante = Sesion.usuarioAutorizante.cargarDatos(codBarraAutorizante);
 * =============================================================================
 * Versión    : 1.5.6 
 * Fecha       : 02/03/2004 10:52 AM
 * Analista    : irojas
 * Descripción : Línea 629: Agregado método iniciarUsuario
 * =============================================================================
 * Versión     : 1.5.5 
 * Fecha       : 02/03/2004 09:14 AM
 * Analista    : gmartinelli
 * Descripción : Cambio de cantidad de int a float
 * =============================================================================
 * Versión     : 1.5.4 
 * Fecha       : 02/03/2004 09:20 AM
 * Analista    : gmartinelli
 * Descripción : Agregado metodo obtenerRenglones que devuelve un vector con las 
 * 				posiciones del detalle donde se encuentra el producto
 * =============================================================================
 * Versión     : 1.5.3 
 * Fecha       : 20/02/2004 13:06 PM
 * Analista    : irojas
 * Descripción : Agregado método "asignarCliente"
 * =============================================================================
 * Versión     : 1.5.2 
 * Fecha       : 19/02/2004 15:31 PM
 * Analista    : gmartinelli
 * Descripción : Arreglos de comentarios para JavaDoc.
 * =============================================================================
 * Versión     : 1.5.1 
 * Fecha       : 19/02/2004 13:41 PM
 * Analista    : gmartinelli
 * Descripción : Agregados metodos recuperarFacturaEspera y colocarFacturaEspera
 * =============================================================================
 * Versión     : 1.5 (según CVS) 
 * Fecha       : 13/02/2004 09:20 AM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Modificaciones en el método CargarCatalogo. Sustituido el tipo
 * 				 retornado: de Catalogo(clase eliminada) a ModeloTabla.
 *  
 * 				 Optimización del llamado al registroAditoria. Sustituidos las 
 * 				llamadas:
 * 					Vector ubicacion = MediadorBD.obtenerMetModFun ("SEGURIDAD", "cambiarClave");
 *					int codModulo = ((Integer)ubicacion.elementAt(1)).intValue();
 *					int codFuncion = ((Integer)ubicacion.elementAt(2)).intValue();
 *				por:
 *					Sesion.setUbicacion("SEGURIDAD", "cambiarClave");
 *				y las llamadas del tipo:
 *					new ExcepcionCr(e.getMensaje(), e.getExcepcion(), codModulo, codFuncion));
 *				por:
 *					new ExcepcionCr(e.getMensaje(), e.getExcepcion());
 * =============================================================================
 * Versión     : 1.2 (según CVS antes del cambio)
 * Fecha       : 09/02/2004 04:19 PM
 * Analista    : Ileana Rojas
 * Descripción : Cambios de códigos de producto de Long a String
 * =============================================================================
 * Versión     : 1.0.3
 * Fecha       : 26/11/2003 01:56:41 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Modificaciones por requerimientos de integración BECO y EPA. 
 * =============================================================================
 * Versión     : 1.0.2
 * Fecha       : 18/11/2003 03:28:41 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Modificaciones por cambio en el diseño de la BD para las 
 * 				 entidades Modulo, Funcion y Metodo para EPA y BECO
 * =============================================================================
 * Versión     : 1.0.1
 * Fecha       : 31/10/2003 03:28:41 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Integración de la clase MáquinaDeEstado para EPA y BECO
 * =============================================================================
 */
package com.becoblohm.cr.manejadorsesion;

import java.awt.event.KeyEvent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.beco.cr.pda.TriDES;
import com.beco.ventas.politicasmantenimiento.Principal;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.excepciones.AutorizacionExcepcion;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ConexionServidorExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.IdentificarExcepcion;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.PerfilExcepcion;
import com.becoblohm.cr.excepciones.PerfilUsrExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.extensiones.ManejadorReportesFactory;
import com.becoblohm.cr.extensiones.ManejoPagosFactory;
import com.becoblohm.cr.extensiones.NotAvailableTimeServiceException;
import com.becoblohm.cr.extensiones.PDA;
import com.becoblohm.cr.extensiones.PDAFactory;
import com.becoblohm.cr.extensiones.TimeProxy;
import com.becoblohm.cr.extensiones.TimeProxyFactory;
import com.becoblohm.cr.gui.CambiarFechaSistema;
import com.becoblohm.cr.gui.CambioDeClave;
import com.becoblohm.cr.gui.ModeloTabla;
import com.becoblohm.cr.gui.PanelIconosCR;
import com.becoblohm.cr.gui.PanelMensajesCR;
import com.becoblohm.cr.gui.BarraTareaCR;
import com.becoblohm.cr.gui.SerialDispositivoFiscal;
import com.becoblohm.cr.gui.VentanaEspera;
import com.becoblohm.cr.manejadorauditoria.Auditoria;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.manejarbr.PromocionBR;
import com.becoblohm.cr.manejarsistema.Funcion;
import com.becoblohm.cr.manejarsistema.ManejarSistema;
import com.becoblohm.cr.manejarsistema.Metodo;
import com.becoblohm.cr.manejarsistema.Modulo;
import com.becoblohm.cr.manejarusuario.ListaFuncion;
import com.becoblohm.cr.manejarusuario.ManejarUsuarios;
import com.becoblohm.cr.manejarusuario.Perfil;
import com.becoblohm.cr.manejarusuario.Usuario;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.mediadoresbd.BaseDeDatosServicio;
import com.becoblohm.cr.mediadoresbd.BaseDeDatosVenta;
import com.becoblohm.cr.mediadoresbd.BeansSincronizador;
import com.becoblohm.cr.mediadoresbd.Conexiones;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.sincronizador.HiloSyncTransacciones;
import com.becoblohm.cr.sincronizador.Sincronizador;
import com.becoblohm.cr.sincronizador.SincronizarCajaServidor;
import com.becoblohm.cr.utiles.ComunicarConServidor;
import com.becoblohm.cr.utiles.Control;
import com.becoblohm.cr.utiles.FtpManager;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.crprinterdriver.CRFiscalPrinterOperations;
import com.epa.crprinterdriver.PrinterNotConnectedException;
import com.epa.preferencesproxy.EPAPreferenceProxy;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;


/** 
 * Descripción: 
 * 		Clase que maneja la transicion de estados de la Caja registradora asi como 
 * las autorizaciones y auditorias de las operaciones que se realicen en las 
 * cajas registradoras
 * 
 */
/*
* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Se comentó variable sin uso
* Fecha: agosto 2011
*/
@SuppressWarnings("unused")
public class MaquinaDeEstado {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(MaquinaDeEstado.class);

	//private static ManejarSistema xManejarSistema;
	
	private static ManejarUsuarios xManejarUsuarios;
	private static CambioDeClave cambiarClave = null;
	
	private static Hashtable promoMontoCantidad = new Hashtable();
		
	/**
	 * Contructor para MaquinaDeEstado
	 * 		Crea la sesion actual de la caja registradora e inicializa sus 
	 * atributos.
	 */
	public MaquinaDeEstado() throws UsuarioExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		xManejarUsuarios = new ManejarUsuarios();
	}
	
	/**
	 * Method verificarAutorización
	 * 		Verifica si el perfil del usuario activo está autorizado para ejecutar la función especificada.
	 * 		Ademas, si se requiere autorizacion, se chequea que el autorizante no sea el usuario activo
	 * ni el cliente pasado como parametro
	 * @param nombModulo - Nombre del módulo que se está ejecutando
	 * @param nombMetodo - Nombre del método que se está utilizando 
	 * @param cliente - Cliente activo en la Venta, Anulacion, Devolucion, Apartado, etc., Si está activo
	 * @throws ExcepcionCr Si existe problema con la definición del método,módulo y/o función.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList y Vector
	* Fecha: agosto 2011
	*/
	String verificarAutorizacion(String nombModulo, String nombMetodo, Cliente cliente) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger
					.debug("verificarAutorizacion(String, String, Cliente) - start");
		}

		Sesion.setUbicacion(nombModulo,nombMetodo);
		Vector<Integer> result = MediadorBD.obtenerMetModFun();
		int codFuncion = result.elementAt(2).intValue();
		int codModulo = result.elementAt(1).intValue();
		String codAutorizante = null;
		boolean autorizar = false;
				
		//Verificamos si entre las funciones del usuario activo está le solicitada
		boolean funcionPresente = false;
		ArrayList<ListaFuncion> funciones = Sesion.usuarioActivo.getFunciones();
		for(int i=0; i<funciones.size(); i++) {
			ListaFuncion usuarioFuncion = (ListaFuncion)funciones.get(i);
			if(usuarioFuncion.getFuncion().getCodFuncion() == codFuncion && usuarioFuncion.getFuncion().getCodModulo() == codModulo){
				funcionPresente = true;
				if(!usuarioFuncion.isHabilitado())
					throw (new MaquinaDeEstadoExcepcion ("La función no se encuentra habilitada para el usuario activo."));
				
				if(usuarioFuncion.getFuncion().isReqAutorizacion() || (!usuarioFuncion.isAutorizado())) {
					autorizar = true;
				}
				break;
			}
		}

		//Si la función está presente entre las que puede realizar el perfil del usuario activo entonces se verifica si está autorizado para la misma
		if (funcionPresente) {
			if (autorizar) { //se pregunta si la función requiere autorización
				Auditoria.registrarAuditoria("Solicitando autorizacion para la funcion "+codFuncion,'T');
				validarUsuario(true,false);

				if (Sesion.usuarioAutorizante != null){
					// Verificamos si el autorizante es distinto al usuario logueado
					if (!Sesion.usuarioActivo.getNumFicha().equals(Sesion.usuarioAutorizante.getNumFicha())) {
						// Verificamos que el usuario autorizante no sea el cliente activo (Si existe)
						if ((cliente==null)||(cliente.getNumFicha()==null)||(!cliente.getNumFicha().equals(Sesion.usuarioAutorizante.getNumFicha()))) {
						   // Verificamos Si el autorizante está autorizado para hacer esta función
						   boolean autorizado = false;
			
						   ArrayList<ListaFuncion> xFunciones = Sesion.usuarioAutorizante.getFunciones();
						   for(int i=0; i<xFunciones.size(); i++) {
							   ListaFuncion funcion= (ListaFuncion)xFunciones.get(i);
							   if(funcion.getFuncion().getCodFuncion() == codFuncion && funcion.getFuncion().getCodModulo() == codModulo){
									//Se verifica que el autorizante esté habilitado para realizar esta función
									if(!funcion.isHabilitado())
										throw (new MaquinaDeEstadoExcepcion ("La función no se encuentra habilitada para el usuario autorizante."));
								
								   if(funcion.isAutorizado()) {
									   autorizado = true;
									   break;
								   }
							   }
						   }
						   // Validamos que el autorizante esté autorizado. Si es así se autoriza la ejecución de la función
						   if (!autorizado){
							   throw (new AutorizacionExcepcion ("El autorizante "+Sesion.usuarioAutorizante.getNumFicha()+" no tiene la autorizacion para realizar esta operacion"));
						   } else 
						   		Auditoria.registrarAuditoria("Función "+ codFuncion +" autorizada por Usuario #"+Sesion.usuarioAutorizante.getNumFicha()+".", 'O');
						} else {
							throw (new AutorizacionExcepcion ("La función no pudo ser autorizada.\nEl autorizante debe ser distinto al cliente asignado."));
						}
					} else {
						throw (new AutorizacionExcepcion ("La función no pudo ser autorizada.\n El autorizante debe ser distinto al usuario activo."));
					}
					
					//Inicializamos la variable para pasar el numFicha de autorizante
				   codAutorizante = Sesion.usuarioAutorizante.getNumFicha();
				   
				} else {
					throw (new AutorizacionExcepcion ("La función no pudo ser autorizada"));
				}
			}
		} else {
			throw (new AutorizacionExcepcion ("El usuario no tiene asignada la función a su perfil"));
		}

		//Colocamos en nulo al usuarioAutorizante 
		 Sesion.usuarioAutorizante = null; 

		if (logger.isDebugEnabled()) {
			logger
					.debug("verificarAutorizacion(String, String, Cliente) - end");
		}
		 return codAutorizante;
	}

	/**
	 * Method verificarAutorización
	 * 		Verifica si el perfil del usuario activo está autorizado para ejecutar la función especificada.
	 * 		Ademas, si se requiere autorizacion, se chequea que el autorizante no sea el usuario activo 
	 * @param nombModulo - Nombre del módulo que se está ejecutando
	 * @param nombMetodo - Nombre del método que se está utilizando 
	 * @throws ExcepcionCr Si existe problema con la definición del método,módulo y/o función.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList y Vector
	* Fecha: agosto 2011
	*/
	public String verificarAutorizacion(String nombModulo, String nombMetodo) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("verificarAutorizacion(String, String) - start");
		}

		Sesion.setUbicacion(nombModulo,nombMetodo);
		Vector<Integer> result = MediadorBD.obtenerMetModFun();
		int codFuncion = result.elementAt(2).intValue();
		int codModulo = result.elementAt(1).intValue();
		String codAutorizante = null;
		boolean autorizar = false;
		
		
		Usuario usuario = Sesion.usuarioActivo;
		PDA moduloActivo = new PDAFactory().getPDAInstance();
		try{
			//Si se está usando PDA, se debe usar el usuario que esté haciendo la solicitud, no el usuario activo
			usuario = moduloActivo.usuarioActivoOUsuarioPDA(Sesion.usuarioActivo);
		}catch(Exception e){}
		
		//Verificamos si entre las funciones del usuario activo está la solicitada
		boolean funcionPresente = false;
		ArrayList<ListaFuncion> funciones = usuario.getFunciones();
		for(int i=0; i<funciones.size(); i++) {
			ListaFuncion usuarioFuncion = (ListaFuncion)funciones.get(i);
			if(usuarioFuncion.getFuncion().getCodFuncion() == codFuncion && usuarioFuncion.getFuncion().getCodModulo() == codModulo){
				funcionPresente = true;
				if(!usuarioFuncion.isHabilitado())
					throw (new MaquinaDeEstadoExcepcion ("La función no se encuentra habilitada para el usuario activo."));
				
				if(usuarioFuncion.getFuncion().isReqAutorizacion() || (!usuarioFuncion.isAutorizado())) {
					autorizar = true;
				}
				break;
			}
		}

		//Si la función está presente entre las que puede realizar el perfil del usuario activo entonces se verifica si está autorizado para la misma
		if (funcionPresente) {
			codAutorizante = usuario.getNumFicha();
			if (autorizar) { //se pregunta si la función requiere autorización
				
				//Codigo agregado para evitar problemas en el pda. Se indica que la autorizacion es requerida
				if(moduloActivo.esModuloPDA() ){
					throw new UsuarioExcepcion("No se tiene autorizacion para esta funcion!");
				}
				
				
				Auditoria.registrarAuditoria("Solicitando autorizacion para la funcion "+codFuncion,'T');
				validarUsuario(true,false);

				if (Sesion.usuarioAutorizante != null){
					// Verificamos si el autorizante es distinto al usuario logueado
					if (!usuario.getNumFicha().equals(Sesion.usuarioAutorizante.getNumFicha())) {
					   // Verificamos Si el autorizante está autorizado para hacer esta función
					   boolean autorizado = false;
		
					   ArrayList<ListaFuncion> xFunciones = Sesion.usuarioAutorizante.getFunciones();
					   for(int i=0; i<xFunciones.size(); i++) {
						   ListaFuncion funcion= (ListaFuncion)xFunciones.get(i);
						   if(funcion.getFuncion().getCodFuncion() == codFuncion && funcion.getFuncion().getCodModulo() == codModulo){
								//Se verifica que el autorizante esté habilitado para realizar esta función
								if(!funcion.isHabilitado())
									throw (new MaquinaDeEstadoExcepcion ("La función no se encuentra habilitada para el usuario autorizante."));
								
							   if(funcion.isAutorizado()) {
								   autorizado = true;
								   break;
							   }
						   }
					   }
					   // Validamos que el autorizante esté autorizado. Si es así se autoriza la ejecución de la función
					   if (!autorizado){
						   throw (new AutorizacionExcepcion ("El autorizante "+Sesion.usuarioAutorizante.getNumFicha()+" no tiene la autorizacion para realizar esta operacion"));
					   }
					   else Auditoria.registrarAuditoria("Función "+ codFuncion +" autorizada por Usuario #"+Sesion.usuarioAutorizante.getNumFicha()+".", 'O', true);
					} else {
						throw (new AutorizacionExcepcion ("La función no puede ser autorizada por el usuario activo."));
					}
					
					//Inicializamos la variable para pasar el numFicha de autorizante
				   codAutorizante = Sesion.usuarioAutorizante.getNumFicha();
				   
				} else {
					throw (new AutorizacionExcepcion ("La función no pudo ser autorizada"));
				}
			}
		} else {
			throw (new AutorizacionExcepcion ("El usuario no tiene asignada la función a su perfil"));
		}

		//Colocamos en nulo al usuarioAutorizante 
		 Sesion.usuarioAutorizante = null; 

		if (logger.isDebugEnabled()) {
			logger.debug("verificarAutorizacion(String, String) - end");
		}
		 return codAutorizante;
	}

	/**
	 * Method autorizarfuncion
	 * 		Solicita autorización para ejecutar la función indicada.
	 * @param nombModulo - Nombre del módulo que se está ejecutando
	 * @param nombMetodo - Nombre del método que se está utilizando 
	 * @throws ExcepcionCr Si existe problema con la definición del método,módulo y/o función.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList y Vector
	* Fecha: agosto 2011
	*/
	public static String autorizarFuncion(String nombModulo, String nombMetodo) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("autorizarFuncion(String, String) - start");
		}

		Sesion.setUbicacion(nombModulo,nombMetodo);
		Vector<Integer> result = MediadorBD.obtenerMetModFun();
		int codFuncion = result.elementAt(2).intValue();
		int codModulo = result.elementAt(1).intValue();
		boolean autorizar = false;
		String codAutorizante = null;
		Funcion funcion = new Funcion();
		funcion.setCodFuncion((short)codFuncion);
		funcion.setCodModulo((short)codModulo);
		funcion = (Funcion)funcion.obtenerDatos(funcion).get(0);
		if(funcion.isReqAutorizacion())
			autorizar = true;

		//Si la función está presente entre las que puede realizar el perfil del usuario activo entonces se verifica si está autorizado para la misma
		if (autorizar) { //se pregunta si la función requiere autorización
			boolean autorizado = false;
			Auditoria.registrarAuditoria("Solicitando autorizacion para la funcion "+codFuncion,'T');
			validarUsuario(true,false);
			if (Sesion.usuarioAutorizante != null){
				// Verificamos si el autorizante es distinto al usuario logueado
				ArrayList<ListaFuncion> xFunciones = Sesion.usuarioAutorizante.getFunciones();
				for(int i=0; i<xFunciones.size(); i++) {
					ListaFuncion menu= (ListaFuncion)xFunciones.get(i);
					if(menu.getFuncion().getCodFuncion() == codFuncion && menu.getFuncion().getCodModulo() == codModulo){
						if(menu.isAutorizado()) {
						   autorizado = true;
						   break;
						}
					}
				}
					   
				// Validamos que el autorizante esté autorizado. Si es así se autoriza la ejecución de la función
				if (!autorizado){
					throw (new AutorizacionExcepcion ("El autorizante "+Sesion.usuarioAutorizante.getNumFicha()+" no tiene la autorizacion para realizar esta operacion"));
				}
				else Auditoria.registrarAuditoria("Función "+ codFuncion +" autorizada por Usuario #"+Sesion.usuarioAutorizante.getNumFicha()+".", 'O');
				
				//Inicializamos la variable para pasar el numFicha de autorizante
				codAutorizante = Sesion.usuarioAutorizante.getNumFicha();
				} 
			else throw (new AutorizacionExcepcion ("La función no pudo ser autorizada"));
		}

		//Colocamos en nulo al usuarioAutorizante 
		 Sesion.usuarioAutorizante = null; 

		if (logger.isDebugEnabled()) {
			logger.debug("autorizarFuncion(String, String) - end");
		}
		 return codAutorizante;
	}

	/**
	 * Método emitirReporteX.
	 * 	Emite un reporte de Cajero con las operaciones realizadas por el mismo desde el último reporte Z
	 * @throws BaseDeDatosExcepcion Si ocurre un error de acceso a la base de datos
	 * @throws ProductoExcepcion Si el renglón es inválido
	 * @throws MaquinaDeEstadoExcepcion Si no se están en el estado correcto
	 * @throws ExcepcionCr Si falla la búsqueda de metodo y modulo en la base de datos para la auditoria 
	 */
	public void emitirReporteX(String codUsuario, Date fechaReporte) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("emitirReporteX(String, Date) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "emitirReporteX");
		
		Sesion.setUbicacion("UTILIDADES", "emitirReporteX");
		Auditoria.registrarAuditoria("Emitiendo Reporte X",'T');
		verificarAutorizacion ("UTILIDADES","emitirReporteX");
		
		ManejadorReportesFactory.getInstance().imprimirReporteX(codUsuario, fechaReporte);
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("emitirReporteX(String, Date) - end");
		}
	}
	
	/**
	 * Método emitirReporteZ.
	 * 	Emite el reporte de cierre de caja con las operaciones realizadas por la caja desde el último reporte Z
	 *  La emision del reporte Z cierra la sesión de la caja y desabilita la misma hasta el proximo día
	 * @throws BaseDeDatosExcepcion Si ocurre un error de acceso a la base de datos
	 * @throws ProductoExcepcion Si el renglón es inválido
	 * @throws MaquinaDeEstadoExcepcion Si no se están en el estado correcto
	 * @throws ExcepcionCr Si falla la búsqueda de metodo y modulo en la base de datos para la auditoria 
	 */
	public void emitirReporteZ() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("emitirReporteZ() - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "emitirReporteZ");
		
		Sesion.setUbicacion("UTILIDADES", "emitirReporteZ");
		
		Auditoria.registrarAuditoria("Emitiendo Reporte Z por Utilitarios",'T');
		verificarAutorizacion ("UTILIDADES","emitirReporteZ");
		
		//Se chequea si la fecha de cierre es distinta de nulo.Caso de el primer Z de la CR por Utilitarios
		Date fechaCierreCaja = Sesion.getCaja().getFechaUltRepZ();
		if (fechaCierreCaja==null) {
			Calendar fechaBaseZ = Calendar.getInstance();
			fechaBaseZ.set(1970,1,1);
			Sesion.getCaja().setFechaUltRepZ(fechaBaseZ.getTime());
		}
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);
		
		// Transferimos los archivos Timers de vPos al Servidor de Tienda
		this.transferirVPosTimers();
		
		// Eliminamos los archivos Voucher que tengan mas de 15 días
		this.eliminarVPosVouchers();

		// Cerramos la sesion del usuario
		cerrarSesion();

		// Esperamos por la impresión del cierre de cajero
		//ACTUALIZACION BECO: Impresora fiscal GD4
		while (CR.meVenta.getTransaccionPorImprimir()!=null || 
				CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
				MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			}
			if(MaquinaDeEstadoVenta.errorAtencionUsuario){
				MensajesVentanas.aviso("Hay un documento en curso \nEspere un momento e intente nuevamente");
			}
		}

		// Emitimos Reporte Z luego del cierre de sesion para permitir cerrar varias cajas en paralelo
		ManejadorReportesFactory.getInstance().imprimirReporteZ();
		
		if (logger.isDebugEnabled()) {
			logger.debug("emitirReporteZ() - end");
		}
	}	

	/**
	 * Método suspenderAvisoEntregaCaja.
	 * 	Reiniciliza el valor del monto recaudado en la caja a 0.
	 *  Se utiliza cuando un perfil autorizado hace el cambio en la caja luego que el cajero hizo la entrega a caja principal
	 * @throws BaseDeDatosExcepcion Si ocurre un error de acceso a la base de datos
	 * @throws MaquinaDeEstadoExcepcion Si no se están en el estado correcto
	 * @throws ExcepcionCr Si falla la búsqueda de metodo y modulo en la base de datos para la auditoria 
	 */
	public void suspenderAvisoEntregaCaja() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("suspenderAvisoEntregaCaja() - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "suspenderAvisoEntregaCaja");
		
		Sesion.setUbicacion("UTILIDADES", "suspenderAvisoEntregaCaja");
		String codAutorizante = verificarAutorizacion ("UTILIDADES","suspenderAvisoEntregaCaja");
		if (codAutorizante == null) {
			codAutorizante = Sesion.getUsuarioActivo().getNumFicha();
		}
		Auditoria.registrarAuditoria(codAutorizante, Sesion.getCaja().getUltimaTransaccion(), "Entrega parcial", "UTILIDADES", "suspenderAvisoEntregaCaja");
		
		//Se reinicializa el valor del monto recaudado en la caja a 0
		if(MensajesVentanas.preguntarSiNo("El valor del monto efectivo en caja se reinicializará.\nEsto debe hacerse por concepto de una entrega a Caja Principal.\n¿Está seguro que desea reinicializar el valor del monto efectivo en caja?") == 0) {
			Sesion.getCaja().setMontoRecaudado(0.00);
			MensajesVentanas.aviso("Valor modificado.\nMonto efectivo en Caja: 0.00");
		}
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("suspenderAvisoEntregaCaja() - end");
		}
	}
		
	public String verificarAutorizacionPago() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("verificarAutorizacionPago() - start");
		}

		String returnString = this.verificarAutorizacion("Pago",
				"EFECTUARPAGOAUTORIZADO");
		if (logger.isDebugEnabled()) {
			logger.debug("verificarAutorizacionPago() - end");
		}
		return returnString;
	}
	
	public String formatearCampoNumerico(String textoAnterior, KeyEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("formatearCampoNumerico(String, KeyEvent) - start");
		}

		DecimalFormat df = new DecimalFormat("#,###.00");
		DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
		if (textoAnterior.indexOf(dfs.getDecimalSeparator())==-1) {
			// Si se presiona un numero
			try {
				Integer.valueOf(String.valueOf(e.getKeyChar())).intValue();
				String texto = textoAnterior;
				String numero = "";
				for (int i=0; i<texto.length(); i++) {
					// Tratamos de convertir el caracter a numero
					try {
						int numeroAct = Integer.valueOf(texto.substring(i,i+1)).intValue();
						numero += numeroAct;
					} catch (Exception e1) {
						//logger.error(
						//		"formatearCampoNumerico(String, KeyEvent)", e1);
					}
				}
				DecimalFormat df2 = new DecimalFormat("#,##0");
				String returnString = df2.format(new Double(numero)
						.doubleValue());
				if (logger.isDebugEnabled()) {
					logger
							.debug("formatearCampoNumerico(String, KeyEvent) - end");
				}
				return returnString;
			} catch (Exception e1) {
				//logger.error("formatearCampoNumerico(String, KeyEvent)", e1);

				if (e.getKeyCode()==110) {
					String returnString = textoAnterior.substring(0,
							textoAnterior.length() - 1)
							+ dfs.getDecimalSeparator();
					if (logger.isDebugEnabled()) {
						logger
								.debug("formatearCampoNumerico(String, KeyEvent) - end");
					}
					return returnString;
				} else {
					if (logger.isDebugEnabled()) {
						logger
								.debug("formatearCampoNumerico(String, KeyEvent) - end");
					}
					return null;
				}
			}
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("formatearCampoNumerico(String, KeyEvent) - end");
			}
			return null;
		}
	}
	
	public String formatoNumerico(String texto) {
		if (logger.isDebugEnabled()) {
			logger.debug("formatoNumerico(String) - start");
		}

		DecimalFormat df = new DecimalFormat("#,###.00");
		DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
		String resultado = "";
		texto = texto.trim();
		for (int i=0; i<texto.length(); i++) {
			if (texto.charAt(i)==dfs.getDecimalSeparator())
				resultado += ".";
			else
				if (texto.charAt(i)!=dfs.getGroupingSeparator())
					resultado += String.valueOf(texto.charAt(i));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("formatoNumerico(String) - end");
		}
		return resultado;
	}
		
	/*
	 * ***********************************************************************************
	 * Métodos definidos por Equipos de Desarrollo EPA - Módulo Administración de usuarios
	 * ***********************************************************************************
	 */

	/**
	 * Método cambiarClave
	 * 
	 */
	public static void cambiarClave() {
		if (logger.isDebugEnabled()) {
			logger.debug("cambiarClave() - start");
		}

		Sesion.setUbicacion("SEGURIDAD", "cambiarClave");
		cambiarClave = null;
		cambiarClave = new CambioDeClave();
		MensajesVentanas.centrarVentanaDialogo(cambiarClave);

		if (logger.isDebugEnabled()) {
			logger.debug("cambiarClave() - end");
		}
	}

	/**
	 * Método cambiarClave
	 * 
	 */
	public static void cambiarClaveInicio() {
		if (logger.isDebugEnabled()) {
			logger.debug("cambiarClave() - start");
		}

		Sesion.setUbicacion("SEGURIDAD", "cambiarClave");
		cambiarClave = null;
		cambiarClave = new CambioDeClave(true);
		MensajesVentanas.centrarVentanaDialogo(cambiarClave);

		if (logger.isDebugEnabled()) {
			logger.debug("cambiarClave() - end");
		}
	}
	
	/**
	 * Método cambiarClave.
	 * 		Cambia la contraseña de acceso del usuario por voluntad propia
	 * o por exigencia del sistema.
	 * @param clave - Clave actual del usuario activo
	 * @param nuevaClave - Nueva clave para el usuario activo
	 * @return boolean - Indica que la operación concluyó exitosamente
	 * @throws ExcepcionCr - Arroja excepciones en la conexión con la 
	 * 		base de	datos o por la no validez de la clave de acceso
	 */
	public boolean cambiarClave(String clave, String nuevaClave) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("cambiarClave(String, String) - start");
		}

		boolean cambioOk = false;

		try{
			if (!(nuevaClave == null)){
				Usuario xUser = new Usuario();
				xUser.setClave(clave);
				xUser.setNuevaClave(nuevaClave);
				cambioOk = ManejarUsuarios.cambiarClave(xUser);
				// TEMPORAL!!! 
				// Indica al sincronizador que actualizce estas entidades inmediatamente
//				BeansSincronizador.syncUsuario(true);
			}
		} catch (UsuarioExcepcion e) {
			logger.error("cambiarClave(String, String)", e);

			throw (new UsuarioExcepcion(e.getMensaje(), e.getExcepcion()));
		} catch (MaquinaDeEstadoExcepcion e) {
			logger.error("cambiarClave(String, String)", e);

			throw (new MaquinaDeEstadoExcepcion(e.getMensaje(), e.getExcepcion()));
		} catch (ExcepcionCr e) {
			logger.error("cambiarClave(String, String)", e);

			throw (new ExcepcionCr(e.getMensaje(), e.getExcepcion()));
		} /* catch (SQLException e) {
			e.printStackTrace();
		}*/

		if (logger.isDebugEnabled()) {
			logger.debug("cambiarClave(String, String) - end");
		}
		return cambioOk;
	}

	/**
	 * Método validarUsuario.
	 * 		Permite validar el login de un usuario.
	 * @param login - Login (número de ficha) del usuario o Información 
	 * 		contenida en el código de barra del carnet
	 * @param clave - Clave inicial de acceso en caso de digitar los datos
	 * @param entradaManual - Indicador de lectura manual o por escáner
	 * @throws ExcepcionCr - Excepción arrojada en caso de falla en el acceso
	 */
/*	public static boolean validarUsuario(String login, String clave, boolean entradaManual) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("validarUsuario(String, String, boolean) - start");
		}

		Usuario xUsuario = new Usuario();
		Sesion.setUbicacion("SEGURIDAD", "validarUsuario");
		
		try{
			String estadoFinal = getEstadoFinal("validarUsuario");
			int longitud = login.length();
			if (!entradaManual){
				ManejarUsuarios.validarCodigoUsuario(login,false); 
			}
			else{
				StringBuffer identificador = new StringBuffer(login);
				for (int i=0; i<Control.getLONGITUD_ID()-longitud; i++){
					identificador.insert(0,'0');
				}
				xUsuario.setNumFicha(identificador.substring(0, Control.getLONGITUD_ID()));
				xUsuario.setClave(clave);
				xUsuario.setCodigoBarra(identificador.toString());
				ManejarUsuarios.consultarDatos(xUsuario); 
			}
			setEstadoCaja(estadoFinal);			
		} catch (UsuarioExcepcion e) {
			logger.error("validarUsuario(String, String, boolean)", e);

			throw (new UsuarioExcepcion(e.getMensaje(), e.getExcepcion()));
		} catch (MaquinaDeEstadoExcepcion e) {
			logger.error("validarUsuario(String, String, boolean)", e);

			throw (new MaquinaDeEstadoExcepcion(e.getMensaje(), e.getExcepcion()));
		} catch (ExcepcionCr e) {
			logger.error("validarUsuario(String, String, boolean)", e);

			throw (new ExcepcionCr(e.getMensaje(), e.getExcepcion()));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validarUsuario(String, String, boolean) - end");
		}
		return true;
	}

	/**
	 * Método validarUsuario.
	 * 		Permite validar el ingreso de un usuario al sistema.
	 * @param codigoBarra - Identificación impresa en el carnet del usuario
	 * @return boolean
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr - Excepción arrojada en caso de falla en el acceso
	 */
/*	public static boolean validarUsuario(String codigoBarra) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("validarUsuario(String) - start");
		}

		Sesion.setUbicacion("SEGURIDAD", "validarUsuario");
		
		try{
			String estadoFinal = getEstadoFinal("validarUsuario");

			ManejarUsuarios.validarCodigoUsuario(codigoBarra);
			setEstadoCaja(estadoFinal); 
		} catch (UsuarioExcepcion e) {
			logger.error("validarUsuario(String)", e);

			throw (new UsuarioExcepcion(e.getMensaje(), e.getExcepcion()));
		} catch (MaquinaDeEstadoExcepcion e) {
			logger.error("validarUsuario(String)", e);

			throw (new MaquinaDeEstadoExcepcion(e.getMensaje(), e.getExcepcion()));
		} catch (ExcepcionCr e) {
			logger.error("validarUsuario(String)", e);

			throw (new ExcepcionCr(e.getMensaje(), e.getExcepcion()));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validarUsuario(String) - end");
		}
		return true;
	}

	/**
	 * Método validarUsuario.
	 * 		Permite validar el ingreso de un usuario al sistema.
	 * @param codigoBarra - Identificación impresa en el carnet del usuario
	 * @return boolean
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr - Excepción arrojada en caso de falla en el acceso
	 */
	public static boolean validarUsuario(String codigoBarra, String clave, boolean autorizacion) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("validarUsuario(String) - start");
		}

		Sesion.setUbicacion("SEGURIDAD", "validarUsuario");
		
		try{
			String estadoFinal = getEstadoFinal("validarUsuario");
			ManejarUsuarios.validarCodigoUsuario(codigoBarra,autorizacion,clave);
			setEstadoCaja(estadoFinal); 
		} catch (UsuarioExcepcion e) {
			logger.error("validarUsuario(String)", e);

			throw (new UsuarioExcepcion(e.getMensaje(), e.getExcepcion()));
		} catch (MaquinaDeEstadoExcepcion e) {
			logger.error("validarUsuario(String)", e);

			throw (new MaquinaDeEstadoExcepcion(e.getMensaje(), e.getExcepcion()));
		} catch (ExcepcionCr e) {
			logger.error("validarUsuario(String)", e);

			throw (new ExcepcionCr(e.getMensaje(), e.getExcepcion()));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validarUsuario(String) - end");
		}
		return true;
	}

	/**
	 * Método consultarDatos.
	 * 		Devuelve los datos correspondientes a la entidad indicada.
	 * @param entidad - Instancia del objeto con los criterios de 
	 * 		búsqueda(código o Descripción).
	 * @return Vector - Lista de objetos con los datos solicitados, en caso
	 * 		de modulos, retorna las funciones dependientes
	 * @throws ExcepcionCr - Arroja excepciones en la conexión con la base de
	 * 		datos, la no validez de la función indicado
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Object> consultarDatos(Object entidad) throws UsuarioExcepcion, PerfilExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("consultarDatos(Object) - start");
		}
		
		Vector<Object> resultado = new Vector<Object>();
		Sesion.setUbicacion("SEGURIDAD", "consultarDatos");
		
		try{
			//String estadoFinal = getEstadoFinal("consultarDatos");
//			if (entidad instanceof Funcion){
//				//invocarFuncion("cargarDatosFuncion");
//			}
//			else if (entidad instanceof Modulo){
//				//invocarFuncion("cargarDatosModulo");
//			}
//			else if (entidad instanceof Metodo){
//				//invocarFuncion("cargarDatosMetodo");
//			}
//			else if (entidad instanceof Perfil){
//				//invocarFuncion("cargarDatosPerfil");
//			}
//			else if (entidad instanceof Usuario){
//				//invocarFuncion("cargarDatosUsuario");
//			}
//			else if (entidad instanceof Colaborador){
//				//invocarFuncion("cargarDatosColaborador");
//			}
			resultado = ManejarSistema.consultarDatos(entidad);
			//setEstadoCaja(estadoFinal);
		} catch (UsuarioExcepcion e) {
			logger.error("consultarDatos(Object)", e);

			throw (new UsuarioExcepcion(e.getMensaje(), e.getExcepcion()));
		} catch (PerfilExcepcion e) {
			logger.error("consultarDatos(Object)", e);

			throw (new PerfilExcepcion(e.getMensaje(), e.getExcepcion()));
		} catch (FuncionExcepcion e) {
			logger.error("consultarDatos(Object)", e);

			throw (new FuncionExcepcion(e.getMensaje(), e.getExcepcion()));
		} catch (BaseDeDatosExcepcion e) {
			logger.error("consultarDatos(Object)", e);

			throw (new BaseDeDatosExcepcion(e.getMensaje(), e.getExcepcion()));
		} catch (MaquinaDeEstadoExcepcion e) {
			logger.error("consultarDatos(Object)", e);

			throw (new MaquinaDeEstadoExcepcion(e.getMensaje(), e.getExcepcion()));
		} catch (ExcepcionCr e) {
			logger.error("consultarDatos(Object)", e);

			throw (new ExcepcionCr(e.getMensaje(), e.getExcepcion()));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("consultarDatos(Object) - end");
		}
		return resultado;
	}

	/**
	 * Método cargarRegistros.
	 * 		Devuelve los datos registrados correspondientes a la entidad indicada.
	 * @param vigentes - Verdadero para filtrar resultados por registros vigentes
	 * @return Vector - Objeto vector contenedor de los registros 
	 * @throws ExcepcionCr - Arroja excepciones en la conexión con la base de
	 * 		datos
	 */
	public static Vector<?> cargarRegistros(Object entidad, boolean vigentes) throws ConexionExcepcion, MaquinaDeEstadoExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarRegistros(Object, boolean) - start");
		}

		Vector<?> resultados = new Vector<Object>();
		Sesion.setUbicacion("SEGURIDAD", "cargarRegistros");
		
		try{
			String estadoFinal = getEstadoFinal("cargarRegistros");
//			if (entidad instanceof Funcion){
//				invocarFuncion("cargarFunciones");
//			}
//			else if (entidad instanceof Modulo){
//				//invocarFuncion("cargarModulos");
//			}
//			else if (entidad instanceof Metodo){
//				invocarFuncion("cargarMetodos");
//			}
//			else if (entidad instanceof Perfil){
//				invocarFuncion("cargarPerfiles");
//			}
//			else if (entidad instanceof Usuario){
//				invocarFuncion("cargarUsuarios");
//			}
//			else if (entidad instanceof Colaborador){
//				invocarFuncion("cargarColaboradores");
//			}
			resultados = ManejarSistema.cargarRegistros(entidad, vigentes);
			setEstadoCaja(estadoFinal);
		} catch (MaquinaDeEstadoExcepcion e) {
			logger.error("cargarRegistros(Object, boolean)", e);

			throw (new MaquinaDeEstadoExcepcion(e.getMensaje(), e.getExcepcion()));
		} catch (ExcepcionCr e) {
			logger.error("cargarRegistros(Object, boolean)", e);

			throw (new ExcepcionCr(e.getMensaje(), e.getExcepcion()));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cargarRegistros(Object, boolean) - end");
		}
		return resultados;
	}

	/**
	 * Método cargarCatalogo.
	 * 		Devuelve los datos correspondientes a la entidad indicada además de los 
	 * títulos de las columnas a visualizar.
	 * @param entidad - Instancia con los criterios de búsqueda(código o Descripción).
	 * @return Catalogo - Objeto catalogo con los datos y títulos respectivos
	 * @throws ExcepcionCr - Arroja excepciones en la conexión con la base de
	 * 		datos, la no validez de la entidad indicada
	 */
	public ModeloTabla cargarCatalogo(Object entidad, boolean vigentes) throws ConexionExcepcion, MaquinaDeEstadoExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarCatalogo(Object, boolean) - start");
		}

		ModeloTabla xCatalogo = new ModeloTabla();
		Sesion.setUbicacion("SEGURIDAD", "cargarCatalogo");

		try{
			String estadoFinal = getEstadoFinal("cargarCatalogo");
//			if (entidad instanceof Funcion){
//				invocarFuncion("cargarFunciones");
//			}
//			else if (entidad instanceof Modulo){
//				//invocarFuncion("cargarModulos");
//				invocarFuncion("cargarRegistros");
//			}
//			else if (entidad instanceof Metodo){
//				invocarFuncion("cargarMetodos");
//			}
//			else if (entidad instanceof Perfil){
//				invocarFuncion("cargarPerfiles");
//			}
//			else if (entidad instanceof Usuario){
//				invocarFuncion("cargarUsuarios");
//			}
//			else if (entidad instanceof Colaborador){
//				invocarFuncion("cargarColaboradores");
//			}
			xCatalogo = ManejarSistema.cargarCatalogo(entidad, vigentes);
			setEstadoCaja(estadoFinal);
		} catch (MaquinaDeEstadoExcepcion e) {
			logger.error("cargarCatalogo(Object, boolean)", e);

			throw (new MaquinaDeEstadoExcepcion(e.getMensaje(), e.getExcepcion()));
		} catch (ExcepcionCr e) {
			logger.error("cargarCatalogo(Object, boolean)", e);

			throw (new ExcepcionCr(e.getMensaje(), e.getExcepcion()));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cargarCatalogo(Object, boolean) - end");
		}
		return xCatalogo;
	}

	/**
	 * Método actualizarDatos.
	 * 		Ejecuta la actualización/inserción de una instancia de una clase(Perfil,
	 * Función, Usuario, Metodo, Modulo).
	 * @param entidad - Instancia de la clase con los datos indicados.
	 * @return boolean - Verdadero si la instancia estaba registrado
	 * @throws ExcepcionCr - Arroja excepciones en la conexión con la base de
	 * 		datos, la no validez de la instancia indicada
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	public boolean actualizarDatos(Object entidad) throws PerfilUsrExcepcion, UsuarioExcepcion, FuncionExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarDatos(Object) - start");
		}

		ArrayList<Object> valoresVinculados1 = new ArrayList<Object>();
		ArrayList<String> valoresVinculados2 = new ArrayList<String>();

		boolean existe = this.actualizarDatos(entidad, valoresVinculados1, valoresVinculados2);

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarDatos(Object) - end");
		}
		return existe;
	}

	/**
	 * Método actualizarDatos.
	 * 		Ejecuta la actualización/inserción de una instancia de una clase(Perfil,
	 * Función, Usuario, Metodo, Modulo).
	 * @param entidad - Instancia de la clase con los datos indicados.
	 * @param valoresVinculados1 - Indicar listado de valores de funciones para la clase
	 * 		Perfil o de métodos para la clase Funcion.
	 * @return boolean - Verdadero si la instancia estaba registrado
	 * @throws ExcepcionCr - Arroja excepciones en la conexión con la base de
	 * 		datos, la no validez de la instancia indicada
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	public boolean actualizarDatos(Object entidad, ArrayList<Object> valoresVinculados1) throws PerfilUsrExcepcion, UsuarioExcepcion, FuncionExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarDatos(Object, ArrayList) - start");
		}

		ArrayList<String> valoresVinculados2 = new ArrayList<String>();

		boolean existe = this.actualizarDatos(entidad, valoresVinculados1, valoresVinculados2);

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarDatos(Object, ArrayList) - end");
		}
		return existe;
	}

	/**
	 * Método actualizarDatos.
	 * 		Ejecuta la actualización/inserción de una instancia de una clase(Perfil,
	 * Función, Usuario, Metodo, Modulo).
	 * @param entidad - Instancia de la clase con los datos indicados.
	 * @param valores1 - Indicar listado de valores de funciones para la clase
	 * 		Perfil o de métodos para la clase Funcion o funciones para la clase Modulo.
	 * @param valores2 - Indicar listado de valores de usuarios para la clase Perfil.
	 * @return boolean - Verdadero si la instancia estaba registrado
	 * @throws ExcepcionCr - Arroja excepciones en la conexión con la base de
	 * 		datos, la no validez de la instancia indicada
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	//valores se parametriza ´Object´ dado que puede ser un ArrayList de ListaFuncion, Metodo, Funcion
	@SuppressWarnings("unchecked")
	public boolean actualizarDatos(Object entidad, ArrayList<?> valores1, ArrayList<?> valores2) throws 
	PerfilUsrExcepcion, UsuarioExcepcion, FuncionExcepcion, 
	MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger
					.debug("actualizarDatos(Object, ArrayList, ArrayList) - start");
		}

		boolean existe = false;
		String edoFinalCaja = new String();
		Sesion.setUbicacion("SEGURIDAD", "cargarCatalogo");

		try{
			if (entidad instanceof Funcion){
				edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "actualizarFuncion");
				existe = ManejarSistema.actualizarFuncion((Funcion)entidad, (ArrayList<Metodo>)valores1);
			}
			else if (entidad instanceof Modulo){
				edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "actualizarModulo");
				existe = ManejarSistema.actualizarModulo((Modulo)entidad, (ArrayList<Funcion>)valores1);
			}
			else if (entidad instanceof Metodo){
				edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "actualizarMetodo");
				existe = ManejarSistema.actualizarMetodo((Metodo)entidad);
			}
			else if (entidad instanceof Perfil){
				edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "actualizarPerfil");
				existe = ManejarUsuarios.actualizarPerfil((Perfil)entidad,(ArrayList<ListaFuncion>)valores1,(ArrayList<Usuario>)valores2);
			}
			else if (entidad instanceof Usuario){
				edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "actualizarUsuario");
				existe = ManejarUsuarios.actualizarUsuario((Usuario)entidad);
			}
		} catch (IdentificarExcepcion e) {
			logger.error("actualizarDatos(Object, ArrayList, ArrayList)", e);

			throw (new IdentificarExcepcion(e.getMensaje(), e.getExcepcion()));
		} catch (PerfilUsrExcepcion e) {
			logger.error("actualizarDatos(Object, ArrayList, ArrayList)", e);

			throw (new PerfilUsrExcepcion(e.getMensaje(), e.getExcepcion()));
		} catch (UsuarioExcepcion e) {
			logger.error("actualizarDatos(Object, ArrayList, ArrayList)", e);

			throw (new UsuarioExcepcion(e.getMensaje(), e.getExcepcion()));
		} catch (FuncionExcepcion e) {
			logger.error("actualizarDatos(Object, ArrayList, ArrayList)", e);

			throw (new FuncionExcepcion(e.getMensaje(), e.getExcepcion()));
		} catch (MaquinaDeEstadoExcepcion e) {
			logger.error("actualizarDatos(Object, ArrayList, ArrayList)", e);

			throw (new MaquinaDeEstadoExcepcion(e.getMensaje(), e.getExcepcion()));
		} catch (ExcepcionCr e) {
			logger.error("actualizarDatos(Object, ArrayList, ArrayList)", e);

			throw (new ExcepcionCr(e.getMensaje(), e.getExcepcion()));
		}

		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarDatos(Object, ArrayList, ArrayList) - end");
		}
		return existe;
	}

	/**
	 * Método getEstadoFinal
	 * 		Retorna el estado final al que pasará la caja.
	 * @param nombreMetodo
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws XmlExcepcion
	 * @throws FuncionExcepcion
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 * String
	 */
	public static String getEstadoFinal(String nombreMetodo) throws MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("getEstadoFinal(String) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), nombreMetodo);

		if (logger.isDebugEnabled()) {
			logger.debug("getEstadoFinal(String) - end");
		}
		return edoFinalCaja;
	}

	/**
	 * Método cambiarEstadoCaja
	 *		Actualiza el estado actual de la caja. 
	 * @param estadoCaja
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws XmlExcepcion
	 * @throws FuncionExcepcion
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 * void
	 */
	public static void setEstadoCaja(String estadoCaja) throws MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("setEstadoCaja(String) - start");
		}

		Sesion.getCaja().setEstado(estadoCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("setEstadoCaja(String) - end");
		}
	}

	/**
	 * Método cerrarSesionUsuario
	 * 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unchecked")
	public static void cerrarSesionUsuario() {
		if (logger.isDebugEnabled()) {
			logger.debug("cerrarSesionUsuario() - start");
		}
		
		try {
			boolean reporteZ = false;
			
			// Verificamos si se realizaron operaciones de Ventas de BR, de ser asi
			// se solicita la impresion del consolidado diario de BR
			// Pos 0: Campo Boolean que indica si existen operaciones de Bonos Regalos
			//     1: Campo Entero que indica el número de comprobante de Caja Principal
			//     2: Campo Vector con las formas de pagos y los montos registrados
			Vector<Object> operaciones = null;
			try {
				operaciones = CR.meServ.obtenerOperacionesBR(Sesion.getUsuarioActivo(), Sesion.getFechaSistema());
			} catch (Exception e) {
				logger.error(e);
				operaciones = new Vector<Object>();
				operaciones.add(new Boolean(false));
				operaciones.add(new Integer(0));
				operaciones.add(new Vector<Vector<Object>>());
			}
			boolean exitenOperacionesBR = ((Boolean)operaciones.elementAt(0)).booleanValue();
			if (exitenOperacionesBR) {
				if (MensajesVentanas.preguntarSiNo(
						"Usuario Nro. " + Sesion.getUsuarioActivo().getNumFicha() + " posee transacciones de Bonos Regalo\n"+
						"¿Desea imprimir el consolidado diario de movimientos de Bonos Regalo?")==0) {
					int nroComprobanteCP = ((Integer)operaciones.elementAt(1)).intValue();
					Vector<Vector<Object>> fPagos = (Vector<Vector<Object>>) operaciones.elementAt(2);
					ManejadorReportesFactory.getInstance().imprimirConsolidadoBR(nroComprobanteCP, fPagos, Sesion.getUsuarioActivo(), Sesion.getFechaSistema());
				}
			}
			//Verificar si se está emitiendo reporte Z 
			if (Sesion.getMetodo().equals("emitirReporteZ"))
				reporteZ = true;
			Sesion.setUbicacion("SEGURIDAD", "cerrarSesion");
			String estadoFinal;
			estadoFinal = getEstadoFinal("cerrarSesion");
			Auditoria.registrarAuditoria("Cierre de sesion. Usuario #"+Sesion.usuarioActivo.getNumFicha(),'O');
			if (!reporteZ)
				ManejadorReportesFactory.getInstance().imprimirCierreCajero(Sesion.usuarioActivo);
			Sesion.setUsuarioActivo(MediadorBD.obtenerUsuarioSistema());
			MediadorBD.asignarUsuarioLogueado(null);
			Sesion.getCaja().setMontoRecaudado(0.0);
			setEstadoCaja(estadoFinal);
		} catch (MaquinaDeEstadoExcepcion e) {
			logger.error("cerrarSesionUsuario()", e);
		} catch (XmlExcepcion e) {
			logger.error("cerrarSesionUsuario()", e);
		} catch (FuncionExcepcion e) {
			logger.error("cerrarSesionUsuario()", e);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("cerrarSesionUsuario()", e);
		} catch (ConexionExcepcion e) {
			logger.error("cerrarSesionUsuario()", e);
		} catch (ExcepcionCr e) {
			logger.error("cerrarSesionUsuario()", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cerrarSesionUsuario() - end");
		}
	}

	/**
	 * Método cerrarSesion.
	 * 		Cierra la sesión del usuario actual.
	 * @throws ExcepcionCr - Arroja excepciones en la conexión con la base de
	 * 		datos
	 */
	public static boolean cerrarSesion() throws MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("cerrarSesion() - start");
		}

		ResultSet datosCaja = null;
		boolean cerrar = false;
		try {
			datosCaja = MediadorBD.obtenerDatosCaja(true);
			boolean autorizarCierreSesion = true;
			try {
				autorizarCierreSesion = InitCR.preferenciasCR.getConfigStringForParameter("facturacion","autorizarCierreCajero").trim().toUpperCase().equals("S")?true:false;
			} catch (NoSuchNodeException e1) {
				logger.error("cerrarSesion()", e1);

				MensajesVentanas.mensajeError("Falla carga de preferencias para Cierre de Cajero");
			} catch (UnidentifiedPreferenceException e1) {
				logger.error("cerrarSesion()", e1);
			}
			if((autorizarCierreSesion) && (datosCaja.getString("cierreCajero").toUpperCase().equals("S"))){
				if(CR.me.verificarAutorizacion("SEGURIDAD", "cerrarSesion") != null);
					cerrarSesionUsuario();
			} else cerrarSesionUsuario();
			cerrar = true;
			try{ CR.crVisor.enviarString("** CAJA CERRADA **", "IR A LA PROXIMA ..."); }
			catch(Exception ex){
				logger.error("cerrarSesion()", ex);
			}
		} catch (BaseDeDatosExcepcion e) {
			logger.error("cerrarSesion()", e);

			Auditoria.registrarAuditoria(e.getMensaje(), 'E');
			MensajesVentanas.aviso(e.getMensaje());
		} catch (SQLException e) {
			logger.error("cerrarSesion()", e);

			Auditoria.registrarAuditoria(e.getMessage(), 'E');
		} catch (ConexionExcepcion e) {
			logger.error("cerrarSesion()", e);

			Auditoria.registrarAuditoria(e.getMensaje(), 'E');
			MensajesVentanas.aviso(e.getMensaje());
		} catch (MaquinaDeEstadoExcepcion e) {
			logger.error("cerrarSesion()", e);

			Auditoria.registrarAuditoria(e.getMensaje(), 'E');
			MensajesVentanas.aviso(e.getMensaje());
		} catch (FuncionExcepcion e) {
			logger.error("cerrarSesion()", e);

			Auditoria.registrarAuditoria(e.getMensaje(), 'E');
			MensajesVentanas.aviso(e.getMensaje());
		} catch (UsuarioExcepcion e) {
			logger.error("cerrarSesion()", e);

			Auditoria.registrarAuditoria(e.getMensaje(), 'E');
			MensajesVentanas.aviso(e.getMensaje());
		} catch(ExcepcionCr ex){
			logger.error("cerrarSesion()", ex);

			MensajesVentanas.mensajeError(ex.getMensaje());
			MensajesVentanas.aviso("Caja abierta para facturación. Avise al Asesor para Cajeros.");
		} finally{
			if(datosCaja != null) 
			try {
				datosCaja.close();
				datosCaja = null;
			} catch (SQLException e1) {
				logger.error("cerrarSesion()", e1);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cerrarSesion() - end");
		}
		return cerrar;
	}

	/**
	 * Método sacarRecuperarLinea.
	 * 		Saca o recupera el estado de conexión de la caja.
	 * @throws ExcepcionCr - Arroja excepciones en la conexión con la base de
	 * 		datos
	 */
	public void sacarRecuperarLinea() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("sacarRecuperarLinea() - start");
		}

		CR.me.verificarAutorizacion("SEGURIDAD", "sacarRecuperarLinea");
		String estadoFinal;
		estadoFinal = getEstadoFinal("sacarRecuperarLinea");
		Sesion.setVerificarLinea(!Sesion.isVerificarLinea());
		String estadoConexion = Sesion.isVerificarLinea() == true ? "En Línea" : "Fuera de Línea";  
		Sesion.isCajaEnLinea();
		Auditoria.registrarAuditoria("Cambio de estado de conexión de la caja a "+estadoConexion,'O');
		setEstadoCaja(estadoFinal);

		if (logger.isDebugEnabled()) {
			logger.debug("sacarRecuperarLinea() - end");
		}
	}

	/**
	 * Método cambiarFecha
	 * 
	 * @param nuevaFecha
	 * @param nuevaHora
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
/*	public static void cambiarFecha(Date nuevaFecha, Time nuevaHora) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("cambiarFecha(Date, Time) - start");
		}

		String autorizante = autorizarFuncion("INICIO DEL SISTEMA", "cambiarFecha");
		if (autorizante == null) {
			autorizante = "00000";
		}
		Auditoria.registrarAuditoria(autorizante, Sesion.getCaja().getUltimaTransaccion(), 
				"FECHA=" + nuevaFecha + " HORA=" + nuevaHora, 
				"INICIO DEL SISTEMA", "cambiarFecha");
		String estadoFinal;
		estadoFinal = getEstadoFinal("cambiarFecha");
		if((nuevaFecha != null)&&(nuevaHora != null)){
			setClockCR(nuevaFecha, nuevaHora);
			Auditoria.registrarAuditoria("Cambio de fecha y hora de la Caja",'O');
		} else Auditoria.registrarAuditoria("Diferencia de fecha entre la Caja y la Tienda",'O');
		setEstadoCaja(estadoFinal);

		if (logger.isDebugEnabled()) {
			logger.debug("cambiarFecha(Date, Time) - end");
		}
	}

	/**
	 * Método iraSeguridad.
	 * 		Habilita operaciones del módulo Seguridad.
	 * @throws ExcepcionCr - Arroja excepciones en la conexión con la base de
	 * 		datos
	 */
	public void iraSeguridad() throws MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("iraSeguridad() - start");
		}

		if(verificarAutorizacion("SEGURIDAD", "iraSeguridad") != null){
			String estadoFinal = getEstadoFinal("iraseguridad");
			Auditoria.registrarAuditoria("Accesando al modulo [Seguridad]",'O');
			setEstadoCaja(estadoFinal);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("iraSeguridad() - end");
		}
	}

	/**
	 * Método volverInicio.
	 * 		Regresa al menú inicial.
	 * @throws ExcepcionCr - Arroja excepciones en la conexión con la base de
	 * 		datos
	 */
	public void volverInicio() throws MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("volverInicio() - start");
		}

		Sesion.setUbicacion("SEGURIDAD", "iraInicio");

		String estadoFinal = getEstadoFinal("irainicio");
		Auditoria.registrarAuditoria("Retornando al modo [Iniciada]",'O');
		setEstadoCaja(estadoFinal);

		if (logger.isDebugEnabled()) {
			logger.debug("volverInicio() - end");
		}
	}
	
	/**
	 * Método validarUsuario
	 * 		Permite la validación del usuario con la GUI para iniciar la sesión en el sistema o
	 * para autorizar la ejecución de las funciones que lo ameriten. 
	 * @param autorizante - Indicador para el tipo de validación de usuario que se desea.
	 * @param paraDesbloqueo 
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ExcepcionCr
	 * @return boolean
	 */
	public static boolean validarUsuario(boolean autorizante, boolean paraDesbloqueo) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("validarUsuario(boolean, boolean) - start");
		}

		if(!autorizante && !paraDesbloqueo)
			Sesion.setUbicacion("INICIO DEL SISTEMA", "iniciarSesion");
		boolean returnboolean = Control.accesoValido(autorizante,
				paraDesbloqueo);
		if (logger.isDebugEnabled()) {
			logger.debug("validarUsuario(boolean, boolean) - end");
		}
		return returnboolean;
	}

	/**
	 * Método validarTipoCodigo.
	 * 	Valida el código ingresado por el usuario y retorna un indicador del tipo de código según
	 * lógica de negocio establecida por los parámetros: LONGITUD_CODIGO, FORMATO_COLABORADOR,
	 * FORMATO_PRODUCTO, FORMATO_CLIENTE. 
	 * @param codigo - Código capurado por la GUI del sistema.(Producto, Cliente o Colaborador)
	 * @return ArrayList - Lista que guarda en la posición 1 el indicador del tipo de código,
	 * 			en la posición 2 el valor significativo del código y en la posición 3 un indicador
	 * 			del tipo de captura manual(verdadero/falso). 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	* Fecha: agosto 2011
	*/
	public static ArrayList<Object> validarCodigo(String codigo) {
		if (logger.isDebugEnabled()) {
			logger.debug("validarCodigo(String) - start");
		}

		ArrayList<Object> returnArrayList = Control.codigoValido(codigo);
		if (logger.isDebugEnabled()) {
			logger.debug("validarCodigo(String) - end");
		}
		return returnArrayList;
	}

	/**
	 * Método bloquearCaja
	 * 		Permite bloquear la caja. Verifica si necesita autorizaciòn para realizar esta funciòn.
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ExcepcionCr
	 * @return boolean
	 */
	public void bloquearCaja() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("bloquearCaja() - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "bloquearcaja");
		
		Sesion.setUbicacion("UTILIDADES", "bloquearcaja");
		verificarAutorizacion ("UTILIDADES","bloquearcaja");
		
		// Actualizamos el estado de la caja
		
		InitCR.verificador.setBloqueada(true);
		Sesion.getCaja().setEstado(edoFinalCaja);
		Auditoria.registrarAuditoria("Caja bloqueada por el usuario " + Sesion.usuarioActivo.getNumFicha(),'O');
		
		// Abrimos la gaveta
		try{ CR.me.abrirGaveta(false); 
		} catch(Exception ex){
			logger.error("bloquearCaja()", ex);
}

		try{ CR.crVisor.enviarString("** CAJA CERRADA **", "IR A LA PROXIMA ..."); }
		catch(Exception ex){
			logger.error("bloquearCaja()", ex);
}

		if (logger.isDebugEnabled()) {
			logger.debug("bloquearCaja() - end");
		}
	}
	
	/**
	 * Método desbloquearCaja
	 * 		Permite la validación de la clave del usuario activo en la caja con la especificada.
	 * 		Esto para poder hacer un desbloqueo de la caja.
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ExcepcionCr
	 * @return boolean
	 */
	public boolean desbloquearCaja() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr  {
		if (logger.isDebugEnabled()) {
			logger.debug("desbloquearCaja() - start");
		}

		String edoCaja = Sesion.getCaja().getEstado();
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "desbloquearcaja");
		Sesion.setUbicacion("UTILIDADES", "desbloquearcaja");
		verificarAutorizacion ("UTILIDADES","desbloquearcaja");
	
		boolean valido = false;
		validarUsuario(true, true);
		
		// Verificamos si el usuario que trata de desbloquear la caja es distinto al usuario logueado
		if(Sesion.usuarioAutorizante != null){
			if (!Sesion.usuarioActivo.getNumFicha().equals(Sesion.usuarioAutorizante.getNumFicha())) {
				Sesion.getCaja().setEstado(edoCaja);
				//Se verifica si alguien con poder(autorizado y habilitado para la función) intenta desbloquear la caja del usuario activo
				valido = this.desbloquearCajaAUsuario();
			} else {
				valido = true;
				Sesion.usuarioAutorizante = null;

				Auditoria.registrarAuditoria("Caja desbloqueada por el usuario " + Sesion.usuarioActivo.getNumFicha(),'O');
			
				// Actualizamos el estado de la caja
				Sesion.getCaja().setEstado(edoFinalCaja);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("desbloquearCaja() - end");
		}
		return valido;
	}

	/**
	 * Método desbloquearCajaAUsuario
	 * 		Permite la validación de la clave del usuario activo en la caja con la especificada.
	 * 		Esto para poder hacer un desbloqueo de la caja de otro usuario activo.
	 * 		Se da si el usuario que intenta desbloquear la caja de otro tiene la aotirizaciòn y 
	 * 		está habilitado para hacer esa funciòn.
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ExcepcionCr
	 * @return boolean validos= indica si el usuario que intenta desbloquear la caja de otro puede hacerlo
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public boolean desbloquearCajaAUsuario() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr  {
		if (logger.isDebugEnabled()) {
			logger.debug("desbloquearCajaAUsuario() - start");
		}

		String edoDeBloqueo = Sesion.getCaja().getEstado();
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "desbloquearcajaausuario");
		Sesion.setUbicacion("UTILIDADES", "desbloquearcajaausuario");
	
		boolean valido = false;

		//verifica si el usuario que intenta desbloquear la caja puede hacerlo
		Vector<Integer> result = MediadorBD.obtenerMetModFun();
		int codFuncion = result.elementAt(2).intValue();
			
		Usuario usuarioEntrante = Sesion.usuarioAutorizante; //Se respalda al usuario entrante para luego como colocarlo como el nuevo activo

		 // Verificamos Si está autorizado para hacer esta función
		 boolean autorizado = false;
			
		   for(int i=0; i<Sesion.usuarioAutorizante.getFunciones().size(); i++) {
			   ListaFuncion funcion= (ListaFuncion)Sesion.usuarioAutorizante.getFunciones().get(i);
			   if(funcion.getFuncion().getCodFuncion() == codFuncion){				
				   if(funcion.isAutorizado() && funcion.isHabilitado()) {
					   autorizado = true;
					   break;
				   }
			   }
		   }

		   // Validamos que el usuario esté autorizado. Si es así se autoriza la ejecución de la función
		   if (!autorizado){
			   throw (new UsuarioExcepcion ("No está autorizado o habilitado para hacer un desbloqueo de caja a otro usuario"));
		   } else {
				if(MensajesVentanas.preguntarSiNo("Se cerrará la sesión del usuario: " + Sesion.usuarioActivo.getNumFicha() + "\n¿Desea continuar?", true) == 0) {
					Sesion.usuarioAutorizante = null;
					cerrarSesion(); //AQUÍ YA SE PIERDE AL USUARIO AUTORIZANTE. 
										 //EN EL MOMENTO EN EL QUE SE PIDE AUTORIZACIÓN PARA REALIZAR ESTA FUNCIÒN
					
					
					// Actualizamos el estado de la caja
					Sesion.getCaja().setEstado(edoFinalCaja);
					 
					 //Actualizamos el usuario activo en la nueva sesion
					Sesion.setUsuarioActivo(usuarioEntrante);

					if ((Sesion.usuarioActivo.isIndicaCambiarClave()&&(Sesion.usuarioActivo.isPuedeCambiarClave()))){
						MensajesVentanas.aviso("Debe cambiar clave de acceso", true);
						MaquinaDeEstado.cambiarClaveInicio();
					}
					valido = true;
					Auditoria.registrarAuditoria("Caja desbloqueada por el usuario " + Sesion.usuarioActivo.getNumFicha(),'O');
				} else {
					valido = false;
					Sesion.usuarioAutorizante = null;
					Auditoria.registrarAuditoria("Cancelada función de desbloqueo de caja a otro usuario",'O');
					//Se retorna el estado de la caja al estado original de bloqueada.
					//Esto se hace porque cuando se llamó a validar usuario se cambio 
					//el estado de la caja a "Iniciada" ya que la validación fue satisfactoria.
					Sesion.getCaja().setEstado(edoDeBloqueo);
				}
		   }
		
		if (logger.isDebugEnabled()) {
			logger.debug("desbloquearCajaAUsuario() - end");
		}
		return valido;
	}

	/**
	 * Método desbloquearCajaAutorizado
	 * 		Permite la validación de la clave del usuario activo en la caja con la especificada.
	 * 		Esto para poder hacer un desbloqueo de la caja de otro usuario activo.
	 * 		Se da si el usuario que intenta desbloquear la caja de otro tiene la aotirizaciòn y 
	 * 		está habilitado para hacer esa funciòn.
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ExcepcionCr
	 * @return boolean validos= indica si el usuario que intenta desbloquear la caja de otro puede hacerlo
	 */
	public boolean desbloquearCajaAutorizado() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr  {
		if (logger.isDebugEnabled()) {
			logger.debug("desbloquearCajaAUsuario() - start");
		}
	
		String edoInicial = Sesion.getCaja().getEstado();
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "cerrarsesionautorizado");
		Sesion.setUbicacion("UTILIDADES", "cerrarsesionautorizado");
		String codAutorizante = verificarAutorizacion(Sesion.getModulo(), Sesion.getMetodo());
		boolean valido = false;
	
		if(MensajesVentanas.preguntarSiNo("Se cerrará la sesión del usuario: " + Sesion.usuarioActivo.getNumFicha() + "\n¿Desea continuar?", true) == 0) {
			Sesion.usuarioAutorizante = null;
			cerrarSesion(); //AQUÍ YA SE PIERDE AL USUARIO AUTORIZANTE. 
							//EN EL MOMENTO EN EL QUE SE PIDE AUTORIZACIÓN PARA REALIZAR ESTA FUNCIÒN
					
			// Actualizamos el estado de la caja
			Sesion.getCaja().setEstado(edoFinalCaja);
			valido = true;
			Auditoria.registrarAuditoria("Caja desbloqueada por el usuario " + Sesion.usuarioActivo.getNumFicha(),'O');
		} else {
			valido = false;
			Sesion.usuarioAutorizante = null;
			Auditoria.registrarAuditoria("Cancelada función de desbloqueo de caja a otro usuario",'O');
		}
			
		if (logger.isDebugEnabled()) {
			logger.debug("desbloquearCajaAUsuario() - end");
		}
		return valido;
	}

	/**
	 * Método isConexion
	 *		Verifica si esta activa la conexión con el PC indicado. 
	 * @param ip - Dirección IP del equipo con el cual verificar conexión.
	 * @return boolean
	 */
	public static boolean isConexion(String ip, int port) {
		if (logger.isDebugEnabled()) {
			logger.debug("isConexion(String, int) - start");
		}

		boolean returnboolean = MediadorBD.isConexion(ip, port);
		if (logger.isDebugEnabled()) {
			logger.debug("isConexion(String, int) - end");
		}
		return returnboolean;
	}	

	/**
	 * Método cerrarCaja
	 * 		Método donde se invocan los métodos necesarios para dejar al sistema
	 * preparado para la próxima jornada.
	 * 
	 */
	public static void cerrarCaja(){
		if (logger.isDebugEnabled()) {
			logger.debug("cerrarCaja() - start");
		}
		
		try {
			// **** 18/03/2008.BECO:PuntoAgil. Se chequea la fecha del último reporte Z de la caja.
			// Esto por el caso de reinicios de la caja en el día 
			// No se quiere que se imprima el reporte de cierre del punto en esos casos
			Calendar fechaReporte = Calendar.getInstance();
			fechaReporte.setTime(Sesion.getCaja().getFechaUltRepZ());
			SimpleDateFormat fechaRep = new SimpleDateFormat("yyyy-MM-dd");
        	String fechaReporteString = fechaRep.format(fechaReporte.getTime());
			//************
		
        	Calendar fechaActual = Calendar.getInstance();	
        	String fechaHoyString=  fechaRep.format(fechaActual.getTime());
			
			//***************************************************
			//***** VERIFICACIÓN DE SI YA SE REALIZÓ EL REPORTE Z
			//***************************************************
			if(Sesion.getCaja().getFechaUltRepZ()!= null && (fechaHoyString.equals(fechaReporteString))) {	
				//Ejecuta las Políticas de Limpieza
				ejecutarPoliticasLimpieza();
				
				//***** EJECUTA EL CIERRE DEL PUNTO AGIL
				ManejoPagosFactory.getInstance().efectuarCierre();
			}
			
		} catch (Exception e) {
			logger.error("cerrarCaja()", e);
		} catch (Throwable t) {
			logger.error("cerrarCaja()", t);
		}
		
		try {
			MensajesVentanas.detenerVerificadorFoco();
			//BaseDeDatosVenta.eliminarComandas();
			if (Sesion.impresoraFiscal) {
				BaseDeDatosVenta.rollbackTransPendientesXImprimir();
			}
			SincronizarCajaServidor.sincronizador.detenerSync();
			VentanaEspera.ejecutarEsperar(Sincronizador.class.getDeclaredMethod("syncCajaServ", new Class[] {}), CR.sincronizador, null);

			// NUEVO: Subir todos los Afiliados Asociados a Transacciones
			//BeansSincronizador.cargarAfiliadosTemporales();
			// FIN NUEVO

			verificarTransacciones("No se pudo actualizar todas las ventas en el servidor.\n Notifique al administrador del sistema");			
		} catch (BaseDeDatosExcepcion e) {
			logger.error("cerrarCaja()", e);

			MensajesVentanas.mensajeError(e.getMensaje());
		} catch (ConexionExcepcion e) {
			logger.error("cerrarCaja()", e);

			MensajesVentanas.mensajeError(e.getMensaje());
		} catch (InterruptedException e) {
			logger.error("cerrarCaja()", e);
		} catch (Exception e) {
			logger.error("cerrarCaja()", e);
		} catch (Throwable t) {
			logger.error("cerrarCaja()", t);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("cerrarCaja() - end");
		}
		// NUEVO: Subir todos los Afiliados Asociados a Transacciones
		BeansSincronizador.cargarAfiliadosTemporales();
		// FIN NUEVO		
		
	}

	/**
	 * Método buscarFuncion
	 *	Método para obtener la descripción de la función en la que se está al momento de llamarlo
	 *	Se utiliza para mostrar en la pantalla de LoginDeUsuario de la GUI la descripción de la función 
	 *	que se está autorizando/ejecutando	
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static String buscarFuncion() throws FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion, SQLException{
		if (logger.isDebugEnabled()) {
			logger.debug("buscarFuncion() - start");
		}

		Vector<Integer> result = MediadorBD.obtenerMetModFun();
		int codFuncion = result.elementAt(2).intValue();
		String descFun = MediadorBD.buscarDescripcionFuncion(codFuncion);

		if (logger.isDebugEnabled()) {
			logger.debug("buscarFuncion() - end");
		}
		return descFun;		
	}
	
	/**
	 * Método sincronizarFechaHora
	 * 
	 * @return boolean
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se eliminó variable sin uso
	* Fecha: agosto 2011
	*/
	public static boolean sincronizarFechaHora(){
		if (logger.isDebugEnabled()) {
			logger.debug("sincronizarFechaHora() - start");
		}

		try {
			Date fechaServidor,  fechaCaja;
			Time horaServidor, horaCaja;
			Date fechaCierreCaja = Sesion.getCaja().getFechaUltRepZ();
		
			boolean cierreTienda = false;
			CambiarFechaSistema cambiarFecha = null;
			Calendar fecLimInf = Calendar.getInstance();
			fecLimInf.add(Calendar.DATE, -1);
			boolean confCambioFecha = true;
			java.sql.Date fechaTienda = new java.sql.Date(Sesion.getFechaSistema().getTime());
			
			try {
				confCambioFecha = (InitCR.preferenciasCR.getConfigStringForParameter("sistema", "cambiofecha").equals("S"));
			} catch (Exception e) {
				logger.error("sincronizarFechaHora()", e);
			}
			//Recupero fecha y hora del servidor de la tienda
			//try {
			//	long ts = getClockServer().getTime();
			//	fechaServidor = new Date(ts);
			//	horaServidor = new Time(ts);
/****************************************/
			ResultSet resultado = null;
			
			if(Sesion.isCajaEnLinea()){
				try {
					String sentenciaSQL = "select YEAR(CURRENT_DATE), MONTH(CURRENT_DATE), DAYOFMONTH(CURRENT_DATE)";
					resultado = Conexiones.realizarConsulta(sentenciaSQL, false);
					resultado.first();
					String fechaServidorMySQLL = resultado.getString(1) + "-" + resultado.getString(2) + "-" + resultado.getString(3);
					String fechaServidorMySQLW = resultado.getString(3) + "-" + resultado.getString(2) + "-" + resultado.getString(1); 
					sentenciaSQL = "select HOUR(CURRENT_TIME), MINUTE(CURRENT_TIME), SECOND(CURRENT_TIME)";
					resultado = Conexiones.realizarConsulta(sentenciaSQL, false);
					String horaServidorMySQL = resultado.getString(1) + ":" + resultado.getString(2) + ":" + resultado.getString(3);
					resultado.first();
					setClockCR(fechaServidorMySQLL, fechaServidorMySQLW, horaServidorMySQL);
					
							
				} catch (BaseDeDatosExcepcion e) {
					logger.error("getTransaccionesNoSync()", e);
				} catch (ConexionExcepcion e) {
					logger.error("getTransaccionesNoSync()", e);
				} catch (SQLException e) {
					logger.error("getTransaccionesNoSync()", e);
				}
				resultado = null;
			} else {
				try {
					String sentenciaSQL = "select YEAR(CURRENT_DATE), MONTH(CURRENT_DATE), DAYOFMONTH(CURRENT_DATE)";
					resultado = Conexiones.realizarConsulta(sentenciaSQL, true);
					resultado.first();
					String fechaServidorMySQLL = resultado.getString(1) + "-" + resultado.getString(2) + "-" + resultado.getString(3);
					String fechaServidorMySQLW = resultado.getString(3) + "-" + resultado.getString(2) + "-" + resultado.getString(1); 
					sentenciaSQL = "select HOUR(CURRENT_TIME), MINUTE(CURRENT_TIME), SECOND(CURRENT_TIME)";
					resultado = Conexiones.realizarConsulta(sentenciaSQL, true);
					String horaServidorMySQL = resultado.getString(1) + ":" + resultado.getString(2) + ":" + resultado.getString(3);
					resultado.first();
					setClockCR(fechaServidorMySQLL, fechaServidorMySQLW, horaServidorMySQL);
							
				} catch (BaseDeDatosExcepcion e) {
					logger.error("getTransaccionesNoSync()", e);
				} catch (ConexionExcepcion e) {
					logger.error("getTransaccionesNoSync()", e);
				} catch (SQLException e) {
					logger.error("getTransaccionesNoSync()", e);
				}
				resultado = null;
			}


/****************************************/
				//Actualizo fecha y hora de la caja
				//setClockCR(fechaServidor, horaServidor);
//			} catch (ConexionServidorExcepcion e) {
//				logger.error("sincronizarFechaHora()", e);

//				if (confCambioFecha)
//					cambiarFecha = new CambiarFechaSistema(fechaTienda, Sesion.getFechaSistema(), fecLimInf.getTime());
//			}			
			if(Sesion.isCajaEnLinea()){
				//Recupero fecha de cierre del día desde el servidor de la tienda
				fechaTienda = MediadorBD.getFechaTienda(false);
				Sesion.getTienda().setFecha(fechaTienda);
			}

			/*boolean fechaOk = false;
			while((!fechaOk)&&(cambiarFecha != null)){
				MensajesVentanas.centrarVentanaDialogo(cambiarFecha,false);
				valores = cambiarFecha.getValores();
				Timestamp valor = new Timestamp(Sesion.getFechaSistema().getTime());
				Date nuevaFecha = null;
				Time nuevaHora = null;

				if (valores.size() > 0){
					valor = new Timestamp(((Timestamp)valores.get(0)).getTime());
					nuevaFecha = new Date(valor.getTime());
					nuevaHora = new Time(valor.getTime());
				}

				if((nuevaFecha != null)&&(nuevaHora != null)){
					if((nuevaFecha.before(fechaTienda)) || (nuevaFecha.before(fechaCierreCaja)))
						fechaOk = false;
					else fechaOk = true;

					fechaCaja = Sesion.getFechaSistema();
					horaCaja = Sesion.getHoraSistema();
					//if((fechaOk) && ((fechaCaja.compareTo(nuevaFecha) != 0) || (horaCaja.compareTo(nuevaHora) != 0))){
					if ((Control.formatoFecha.format(fechaCaja).compareTo(Control.formatoFecha.format(nuevaFecha)) != 0) || (Control.formatoHora.format(horaCaja).compareTo(Control.formatoHora.format(nuevaHora)) != 0)){
						try{
							MaquinaDeEstado.cambiarFecha(nuevaFecha, nuevaHora);
						} catch (ExcepcionCr e1) {
							logger.error("sincronizarFechaHora()", e1);

							MensajesVentanas.mensajeError(e1.getMensaje());
							fechaOk = false;
						}
					}
				} else{
					try{
						MaquinaDeEstado.cambiarFecha(nuevaFecha, nuevaHora);
						fechaOk = true;
					} catch (ExcepcionCr e1) {
						logger.error("sincronizarFechaHora()", e1);

						MensajesVentanas.mensajeError(e1.getMensaje());
						fechaOk = false;
					}
				}
			}	*/
		} catch (BaseDeDatosExcepcion e2) {
			logger.error("sincronizarFechaHora()", e2);

			MensajesVentanas.mensajeError(e2.getMensaje());

			if (logger.isDebugEnabled()) {
				logger.debug("sincronizarFechaHora() - end");
			}
			return false;
		} catch (ConexionExcepcion e2) {
			logger.error("sincronizarFechaHora()", e2);

			MensajesVentanas.mensajeError(e2.getMensaje());

			if (logger.isDebugEnabled()) {
				logger.debug("sincronizarFechaHora() - end");
			}
			return false;
		} catch (SQLException e2) {
			logger.error("sincronizarFechaHora()", e2);

			MensajesVentanas.mensajeError(e2.getMessage()+"");

			if (logger.isDebugEnabled()) {
				logger.debug("sincronizarFechaHora() - end");
			}
			return false;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("sincronizarFechaHora() - end");
		}
		return true;
	}


	/**
	 * Método realizarCierreCaja.
	 * 	Emite el reporte de cierre de caja con las operaciones realizadas por la caja desde el último reporte Z
	 *  La emision del reporte Z cierra la sesión de la caja y desabilita la misma hasta el proximo día
	 * @throws BaseDeDatosExcepcion Si ocurre un error de acceso a la base de datos
	 * @throws ProductoExcepcion Si el renglón es inválido
	 * @throws MaquinaDeEstadoExcepcion Si no se están en el estado correcto
	 * @throws ExcepcionCr Si falla la búsqueda de metodo y modulo en la base de datos para la auditoria 
	 */
	public static void realizarCierreCaja(boolean inicioCaja) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("realizarCierreCaja(boolean) - start");
		}
		
		if (inicioCaja) {
			Auditoria.registrarAuditoria("Emitiendo Reporte Z al inicio de CR",'T');
			if (Sesion.impresoraFiscal) {
				//Sesion.crFiscalPrinterOperations.abrirPuertoImpresora();
				Sesion.usuarioAutorizante = null;
				CR.me.verificarAutorizacion("UTILIDADES","emitirReporteZ");
				InitCR.iniciando = true;
				ManejadorReportesFactory.getInstance().imprimirReporteZ();
				InitCR.iniciando = false;
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -1);
				Sesion.getCaja().setFechaUltRepZ(cal.getTime());
				//Sesion.crFiscalPrinterOperations.cerrarPuertoImpresora();
			} else {
				Sesion.crPrinterOperations.abrirPuertoImpresora();
				ManejadorReportesFactory.getInstance().imprimirReporteZ();
				Sesion.crPrinterOperations.cerrarPuertoImpresora();
			}

		} else {
			String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "emitirReporteZ");
			Auditoria.registrarAuditoria("Emitiendo Reporte Z por Utilitarios",'T');
			Sesion.usuarioAutorizante = null;
			CR.me.verificarAutorizacion("UTILIDADES","emitirReporteZ");
			// Actualizamos el estado de la caja
			Sesion.getCaja().setEstado(edoFinalCaja);
			// Cerramos la sesion del usuario
			cerrarSesion();
			// Esperamos por la impresión del cierre de cajero
			//ACTUALIZACION BECO: Impresora fiscal GD4
			while (CR.meVenta.getTransaccionPorImprimir()!=null || 
					CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
					MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Bloque catch generado automáticamente
					e.printStackTrace();
				}
				if(MaquinaDeEstadoVenta.errorAtencionUsuario){
					MensajesVentanas.aviso("Hay un documento en curso \nEspere un momento e intente nuevamente");
				}
			}

			// Emitimos Reporte Z luego del cierre de sesion para permitir cerrar varias cajas en paralelo
			ManejadorReportesFactory.getInstance().imprimirReporteZ();
		}
		Calendar fechaReporte = Calendar.getInstance();
		fechaReporte.setTime(Sesion.getFechaSistema());
		fechaReporte.add(Calendar.DATE,-1);
		Sesion.getCaja().setFechaUltRepZ(fechaReporte.getTime());
		
		if (logger.isDebugEnabled()) {
			logger.debug("realizarCierreCaja(boolean) - end");
		}
	}
	
	public static void realizarZImpresora() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr, PrinterNotConnectedException {
		if (logger.isDebugEnabled()) {
			logger.debug("realizarZImpresora() - start");
		}

		try {
			Auditoria.registrarAuditoria("Emitiendo Reporte Z de impresora",'T');
			Sesion.usuarioAutorizante = null;
			CR.me.verificarAutorizacion("UTILIDADES","emitirReporteZ");
			Sesion.crFiscalPrinterOperations.reporteZ();
			Sesion.crFiscalPrinterOperations.commit();
			while (Sesion.crFiscalPrinterOperations.isImprimiendo());
		} catch (PrinterNotConnectedException e) {
			logger.error("realizarZImpresora()", e);
			throw (e);
		}		

		if (logger.isDebugEnabled()) {
			logger.debug("realizarZImpresora() - end");
		}
	}

	/**
	 * Método realizarCierreCaja.
	 * 	Emite el reporte de cierre de caja con las operaciones realizadas por la caja desde el último reporte Z
	 *  La emision del reporte Z cierra la sesión de la caja y desabilita la misma hasta el proximo día
	 * @throws BaseDeDatosExcepcion Si ocurre un error de acceso a la base de datos
	 * @throws ProductoExcepcion Si el renglón es inválido
	 * @throws MaquinaDeEstadoExcepcion Si no se están en el estado correcto
	 * @throws ExcepcionCr Si falla la búsqueda de metodo y modulo en la base de datos para la auditoria 
	 */
	public static void realizarCierreCaja() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("realizarCierreCaja() - start");
		}

		realizarCierreCaja(false);

		if (logger.isDebugEnabled()) {
			logger.debug("realizarCierreCaja() - end");
		}
	}	

	/**
	 * Método apagarSistema
	 * 
	 * @param shutdownPOS - Indica si debe apagarse el POS al cerrar el sistema de caja registradora
	 * @param reiniciarPOS - Indica si debe reiniciarse el POS al cerrar el sistema de caja registradora 
	 */
	public static void apagarSistema(boolean shutdownPOS, boolean reiniciarPOS) {
		if (logger.isDebugEnabled()) {
			logger.debug("apagarSistema(boolean, boolean) - start");
		}

		try{
			Sesion.getCaja().setEstado(Sesion.APAGADA);
			Auditoria.registrarAuditoria("Cerrando Sistema de Caja Registradora", 'O');
		}
		catch(Exception ex){
			logger.error("apagarSistema(boolean, boolean)", ex);
}
					
		if(shutdownPOS || reiniciarPOS){
			try {
				String comandoApagar = "";
				String comandoReiniciar = "";
				if(InitCR.preferenciasCR.getSystemOSName().toLowerCase().indexOf("windows") != -1){
					comandoApagar = new String("shutdown -s -f -t 05 -c \"Apagado del equipo Caja Registradora\"");
					comandoReiniciar = new String("shutdown -r -f -t 05 -c \"Reiniciar Sistema de Caja Registradora\"");
				}
				else if(InitCR.preferenciasCR.getSystemOSName().toLowerCase().indexOf("linux") != -1){
					//comandoApagar = new String("/sbin/shutdown -h now");
					comandoApagar = new String("sh apagar.sh");
					//comandoReiniciar = new String("/sbin/shutdown -r now");
					comandoReiniciar = new String("sh reiniciar.sh");
				}
				if(shutdownPOS) 
					{
						Runtime.getRuntime().exec(comandoApagar); 
						System.exit(0);
					} 
				else {
					Runtime.getRuntime().exec(comandoReiniciar); 
					System.exit(0);
				} 
			} catch (IOException e) {
				logger.error("apagarSistema(boolean, boolean)", e);
			}
		} else System.exit(0); 

		if (logger.isDebugEnabled()) {
			logger.debug("apagarSistema(boolean, boolean) - end");
		}
	}
	
	public static void apagarSistema() {
		if (logger.isDebugEnabled()) {
			logger.debug("apagarSistema() - start");
		}
		boolean reiniciar = false;
		boolean apagar = false;

		if (!InitCR.iniciando) {
			//***** 21-11-2007: irojas
			//***** Agregado apertura de puerto de impresora para solucionar problema
			//***** de reporte de cierre del pinpad al apagar el sistema
			if (Sesion.impresoraFiscal) {
				Sesion.crFiscalPrinterOperations.abrirPuertoImpresora();
				Sesion.crFiscalPrinterOperations.resetPrinter();
			} else {
				Sesion.crPrinterOperations.initializarPrinter();
			}
			
			MaquinaDeEstado.cerrarCaja();

			//***** 21-11-2007: irojas
			//***** Agregado cierre de puerto de impresora para solucionar problema
			//***** de reporte de cierre del pinpad al apagar el sistema
			if (Sesion.impresoraFiscal) {
				Sesion.crFiscalPrinterOperations.cerrarPuertoImpresora();
			} else {
				Sesion.crPrinterOperations.cerrarPuertoImpresora();
			}
		}
		try {
			if (InitCR.preferenciasCR.getConfigStringForParameter("sistema", "apagarsistema").equals("S")) {
				apagar = true;
			}
			if (InitCR.preferenciasCR.getConfigStringForParameter("sistema", "reiniciarsistema").equals("S")) {
				reiniciar = true;
			}
		} catch(NoSuchNodeException e1) {
			if (logger.isDebugEnabled()) {
				logger
						.debug("apagarSistema() - No se encontró la preferencia de apagado o reiniciado de sistema");
			}
		} catch(UnidentifiedPreferenceException e1) {
			logger.error("apagarSistema()", e1);
		} catch (Exception e) {
			logger.error("apagarSistema()", e);
		}

		apagarSistema(apagar, reiniciar);

		if (logger.isDebugEnabled()) {
			logger.debug("apagarSistema() - end");
		}
	}

	/**
	 * Método getClockServer
	 * 
	 * @return Timestamp
	 */
	public static Timestamp getClockServer() throws ConexionServidorExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("getClockServer() - start");
		}

		Timestamp ts = null;
		TimeProxyFactory factory = new TimeProxyFactory();
		TimeProxy proxy = factory.getTimeProxyInstance();
		try {
			ts = proxy.getTimestamp(); 
		} catch (NotAvailableTimeServiceException e) {
			logger.error("getClockServer()", e);

			// Si la caja no logra sincronizarse con el servidor
			// asume que está fuera de línea
			throw new ConexionServidorExcepcion(e.getMessage());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getClockServer() - end");
		}
		return ts;
	}
	
	/**
	 * Método getClockCR
	 * 
	 * @return Timestamp
	 */
	public static Timestamp getClockCR(){
		if (logger.isDebugEnabled()) {
			logger.debug("getClockCR() - start");
		}

		Timestamp tiempo = null;
		tiempo = new Timestamp(Calendar.getInstance().getTime().getTime());

		if (logger.isDebugEnabled()) {
			logger.debug("getClockCR() - end");
		}
		return tiempo;
	}

	/**
	 * Método setClockCR
	 * 
	 * @param fecha
	 * @param hora
	 * @return boolean
	 */
/*	public static boolean setClockCR(Date fecha, Time hora) {
		if (logger.isDebugEnabled()) {
			logger.debug("setClockCR(Date, Time) - start");
		}

		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
		try {
			if(InitCR.preferenciasCR.getSystemOSName().toLowerCase().indexOf("windows") != -1){
				String comandoFecha = "cmd /c \"date "+formatoFecha.format(fecha)+"\"";
				Runtime.getRuntime().exec(comandoFecha);
				String comandoHora = "cmd /c \"time "+formatoHora.format(hora)+"\"";
				Runtime.getRuntime().exec(comandoHora);
			}
			else if(InitCR.preferenciasCR.getSystemOSName().toLowerCase().indexOf("linux") != -1){
				String[] comandoFecha = new String[] {"/bin/date", "--set" ,formatoFecha.format(fecha)+" "+formatoHora.format(hora)};
				Runtime.getRuntime().exec(comandoFecha);
			}
		} catch (IOException e) {
			logger.error("setClockCR(Date, Time)", e);
		} 

		if (logger.isDebugEnabled()) {
			logger.debug("setClockCR(Date, Time) - end");
		}
		return true;
	}
/**
	 * Método setClockCR
	 * 
	 * @param fecha
	 * @param hora
	 * @return boolean
	 */
	public static boolean setClockCR(String fechaL, String fechaW, String hora) {
		if (logger.isDebugEnabled()) {
			logger.debug("setClockCR(Date, Time) - start");
		}
		
		try{
			if(InitCR.preferenciasCR.getSystemOSName().toLowerCase().indexOf("windows") != -1){
				
				String comandoFecha = "cmd /c date "+ fechaW;
				
				Runtime.getRuntime().exec(comandoFecha);
				
				String comandoHora = "cmd /c time "+ hora;
				Runtime.getRuntime().exec(comandoHora);
			}
			else
			if(InitCR.preferenciasCR.getSystemOSName().toLowerCase().indexOf("linux") != -1)
			{	
				File wd = new File("/bin");
				Process proc = null;
				BufferedReader in = null;
				PrintWriter out = null;
				try {
					   proc = Runtime.getRuntime().exec("/bin/bash", null, wd);
					   if (proc != null) {
						    in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
						    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(proc.getOutputStream())), true);
						   out.println("sudo date --set '"+fechaL+ " " +hora + "'");
						   out.println("exit");
					       proc.waitFor();
					       proc.destroy();
					   }
					}catch (Exception e) {
				      e.printStackTrace();
					}finally{
						try {
							in.close();
							out.close();
						}catch (IOException e){
						 e.printStackTrace();
						}
					}
			}
				/*String[] comandoFecha =   {"/bin/date", "--set" ,fechaL + " " + hora};
				//MensajesVentanas.aviso(comandoFecha[0]+comandoFecha[1]+comandoFecha[2]);
				
			 	if(InitCR.preferenciasCR.getSystemOSName().toLowerCase().indexOf("linux") != -1){
					Runtime.getRuntime().exec(comandoFecha);
				}*/
				//MensajesVentanas.aviso("Salió");
			
		}catch (IOException e) {
			logger.error("setClockCR(Date, Time)", e);
		} 

		if (logger.isDebugEnabled()) {
			logger.debug("setClockCR(Date, Time) - end");
		}
		return true;
	}
	/**
	 * Método isAccesoFuncion
	 * 
	 * @param codFuncion
	 * @param codModulo
	 * @return boolean
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Iterator'
	* Fecha: agosto 2011
	*/
	public static boolean isAccesoFuncion(int codFuncion, int codModulo){
		if (logger.isDebugEnabled()) {
			logger.debug("isAccesoFuncion(int, int) - start");
		}

		boolean ok = false;
		try {
			ListaFuncion xListaFuncion = new ListaFuncion();
			xListaFuncion.obtenerDatos((short)codModulo, (short)codFuncion);
			Iterator<ListaFuncion> ciclo = Sesion.usuarioActivo.getFunciones().iterator();
			while (ciclo.hasNext()){
				Funcion xFuncion = new Funcion();
				ListaFuncion xMenu = new ListaFuncion();
				xMenu = (ListaFuncion)ciclo.next();
				xFuncion = xMenu.getFuncion();
				if((xFuncion.getCodFuncion() == xListaFuncion.getFuncion().getCodFuncion()) && (xFuncion.getCodModulo() == xListaFuncion.getFuncion().getCodModulo())){
					boolean returnboolean = xMenu.isHabilitado();
					if (logger.isDebugEnabled()) {
						logger.debug("isAccesoFuncion(int, int) - end");
					}
					return returnboolean;
				}
			}
		} catch (ExcepcionCr e) {
			logger.error("isAccesoFuncion(int, int)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isAccesoFuncion(int, int) - end");
		}
		return ok;
	}
	
	/**
	 * Método mostrarAviso
	 * 
	 * @param xTexto
	 * @param urgente
	 */
	public void mostrarAviso(String xTexto, boolean urgente){
		if (logger.isDebugEnabled()) {
			logger.debug("mostrarAviso(String, boolean) - start");
		}

		PanelMensajesCR.mostrarAviso(xTexto, urgente);

		if (logger.isDebugEnabled()) {
			logger.debug("mostrarAviso(String, boolean) - end");
		}
	}

	/**
	 * Método mostrarEstadoCaja
	 * 
	 * @param nombreEstado
	 */
	public void mostrarEstadoCaja(String nombreEstado){
		if (logger.isDebugEnabled()) {
			logger.debug("mostrarEstadoCaja(String) - start");
		}

		PanelMensajesCR.mostrarEstadoCaja(nombreEstado);	

		if (logger.isDebugEnabled()) {
			logger.debug("mostrarEstadoCaja(String) - end");
		}
	}
	
	/**
	 * Método borrarAvisos
	 * 
	 * 
	 */
	public void borrarAvisos(){
		if (logger.isDebugEnabled()) {
			logger.debug("borrarAvisos() - start");
		}

		PanelMensajesCR.borrarAvisos();

		if (logger.isDebugEnabled()) {
			logger.debug("borrarAvisos() - end");
		}
	}

	/**
	 * Método iniciarAvisos
	 * 
	 * 
	 */
	public void iniciarAvisos(){
		if (logger.isDebugEnabled()) {
			logger.debug("iniciarAvisos() - start");
		}

		PanelMensajesCR.iniciarAvisos();

		if (logger.isDebugEnabled()) {
			logger.debug("iniciarAvisos() - end");
		}
	}

	/**
	 * Método setPrinter
	 * 
	 * @param ok
	 */
	public void setPrinter(boolean ok){
		if (logger.isDebugEnabled()) {
			logger.debug("setPrinter(boolean) - start");
		}

		if(ok)
			PanelIconosCR.setPrinterOk();
		else 
			PanelIconosCR.setPrinterError();

		if (logger.isDebugEnabled()) {
			logger.debug("setPrinter(boolean) - end");
		}
	}

	/**
	 * Método setEscaner
	 * 
	 * @param ok
	 */
	public void setEscaner(boolean ok){
		if (logger.isDebugEnabled()) {
			logger.debug("setEscaner(boolean) - start");
		}

		if(ok)
			PanelIconosCR.setEscanerOk();
		else 
			PanelIconosCR.setEscanerError();

		if (logger.isDebugEnabled()) {
			logger.debug("setEscaner(boolean) - end");
		}
	}

	/**
	 * Método setVisor
	 * 
	 * @param ok
	 */
	public void setVisor(boolean ok){
		if (logger.isDebugEnabled()) {
			logger.debug("setVisor(boolean) - start");
		}

		if(ok)
			PanelIconosCR.setVisorOk();
		else 
			PanelIconosCR.setVisorError();

		if (logger.isDebugEnabled()) {
			logger.debug("setVisor(boolean) - end");
		}
	}

	/**
	 * Método setLinea
	 * 
	 * @param enLinea
	 */
	public void setLinea(boolean enLinea){
		if (logger.isDebugEnabled()) {
			logger.debug("setLinea(boolean) - start");
		}

		if(enLinea)
			PanelIconosCR.setLineaOn();
		else 
			PanelIconosCR.setLineaOff();

		if (logger.isDebugEnabled()) {
			logger.debug("setLinea(boolean) - end");
		}
	}

	/**
	 * Método setSincronizacion
	 * 
	 * @param sincronizada
	 */
	public void setCajaSincronizada(boolean sincronizada){
		if (logger.isDebugEnabled()) {
			logger.debug("setCajaSincronizada(boolean) - start");
		}

		if(sincronizada)
			PanelIconosCR.setCajaSincronizada();
		else PanelIconosCR.setCajaNoSincronizada();

		if (logger.isDebugEnabled()) {
			logger.debug("setCajaSincronizada(boolean) - end");
		}
	}
	
	/**
	 * Método setSyncronizacion
	 * 
	 * 
	 */
	public void setSyncronizacion(){
		if (logger.isDebugEnabled()) {
			logger.debug("setSyncronizacion() - start");
		}

		PanelIconosCR.setCajaSincronizando();

		if (logger.isDebugEnabled()) {
			logger.debug("setSyncronizacion() - end");
		}
	}

	/**
	 * Método setAviso
	 * 
	 * @param encender
	 */
	public void setAviso(boolean encender){
		if (logger.isDebugEnabled()) {
			logger.debug("setAviso(boolean) - start");
		}

		if(encender)
			PanelIconosCR.setAvisoOn();
		else
			PanelIconosCR.setAvisoOff();

		if (logger.isDebugEnabled()) {
			logger.debug("setAviso(boolean) - end");
		}
	}

	/**
	 * Método setEntregaParcial
	 * 
	 * @param encender
	 */
	public void setEntregaParcial(boolean encender){
		if (logger.isDebugEnabled()) {
			logger.debug("setEntregaParcial(boolean) - start");
		}

		if(encender)
			PanelIconosCR.setEntregaParcialOn();
		else
			PanelIconosCR.setEntregaParcialOff();

		if (logger.isDebugEnabled()) {
			logger.debug("setEntregaParcial(boolean) - end");
		}
	}

	/**
	 * Método setExento
	 * 
	 * @param encender
	 */
	public void setExento(boolean encender){
		if (logger.isDebugEnabled()) {
			logger.debug("setExento(boolean) - start");
		}

		if(encender)
			PanelIconosCR.setExentoOn();
		else
			PanelIconosCR.setExentoOff();

		if (logger.isDebugEnabled()) {
			logger.debug("setExento(boolean) - end");
		}
	}

	/**
	 * Método setCliente
	 * 
	 * @param nombre
	 * @param codigo
	 */
	public void setCliente(String nombre, String codigo){
		if (logger.isDebugEnabled()) {
			logger.debug("setCliente(String, String) - start");
		}

		PanelIconosCR.setCliente(nombre, codigo);

		if (logger.isDebugEnabled()) {
			logger.debug("setCliente(String, String) - end");
		}
	}

	/**
	 * Método setUsuario
	 * 
	 * @param nombre
	 */
	public void setUsuario(String nombre){
		if (logger.isDebugEnabled()) {
			logger.debug("setUsuario(String) - start");
		}

		PanelIconosCR.setUsuario(nombre);

		if (logger.isDebugEnabled()) {
			logger.debug("setUsuario(String) - end");
		}
	}

	/**
	 * Método setCalendario
	 * 
	 * @param encender
	 */
	public void setCalendario(boolean encender){
		if (logger.isDebugEnabled()) {
			logger.debug("setCalendario(boolean) - start");
		}

		if(encender)
			PanelIconosCR.setCalendarOn();
		else
			PanelIconosCR.setCalendarOff();

		if (logger.isDebugEnabled()) {
			logger.debug("setCalendario(boolean) - end");
		}
	}

	/**
	 * Método ocultarMenuPrincipal
	 * 
	 * 
	 */
	public void ocultarMenuPrincipal(){
		if (logger.isDebugEnabled()) {
			logger.debug("ocultarMenuPrincipal() - start");
		}

		if(InitCR.verificador.menuPrincipal != null){
			InitCR.verificador.menuPrincipal.dispose();
			InitCR.verificador.menuPrincipal = null;
		}	

		if (logger.isDebugEnabled()) {
			logger.debug("ocultarMenuPrincipal() - end");
		}
	}

	/**
	 * Método repintarIconos
	 * 
	 * 
	 */
	public void repintarIconos(){
		if (logger.isDebugEnabled()) {
			logger.debug("repintarIconos() - start");
		}

		//Inicializamos los iconos
		this.setCliente("","");
		this.setExento(false);
		this.setCalendario(false);

		//Borramos los mensajes de avisos mostrados en las pantallas anteriores a la de Facturación Principal
		this.iniciarAvisos();

		if (logger.isDebugEnabled()) {
			logger.debug("repintarIconos() - end");
		}
	}

	/**
	 * Método repintarMenuPrincipal
	 * 
	 * 
	 */
	public void repintarMenuPrincipal(){
		if (logger.isDebugEnabled()) {
			logger.debug("repintarMenuPrincipal() - start");
		}

		ocultarMenuPrincipal();
		
		//Se repintan los iconos
		this.repintarIconos();
		
		//Creamos la pantalla de Facturación Principal nuevamente
		//InitCR.verificador.menuPrincipal = new FacturacionPrincipal();
		if (InitCR.verificador.barraTareaFacturacion == null)
			InitCR.verificador.barraTareaFacturacion = new BarraTareaCR();
		else 
			InitCR.verificador.barraTareaFacturacion.repintarMenuPrincipal();
		//menuPrincipal = new FacturacionPrincipal();
		InitCR.verificador.menuPrincipal = InitCR.verificador.barraTareaFacturacion.getFacturacion();

		
		//BarraTareaCR pf = new BarraTareaCR();
		//menuPrincipal = new FacturacionPrincipal();
		//InitCR.verificador.menuPrincipal = pf.getFacturacion();
		MensajesVentanas.centrarVentanaDialogo(InitCR.verificador.menuPrincipal);

		if (logger.isDebugEnabled()) {
			logger.debug("repintarMenuPrincipal() - end");
		}
	}

	/**
	 * Método abrirGaveta
	 * 
	 * @param validar
	 * @param aperturaPrevia: Verifica si la gaveta fue abierta previamnete. Si es true no se vuelve a abrir
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	public void abrirGaveta(boolean validar) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("abrirGaveta(boolean) - start");
		}

		if(validar){
			String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "abrirGaveta");
		
			Sesion.setUbicacion("UTILIDADES", "abrirGaveta");
			verificarAutorizacion ("UTILIDADES","abrirGaveta");
			try{ CR.me.abrirGaveta(false); }
			catch(Exception ex){
				logger.error("abrirGaveta(boolean)", ex);
			}

			Sesion.getCaja().setEstado(edoFinalCaja);
		} else {
			//Se verifica el manejo de la gaveta para ver por donde se va a enviar a abrir
			if (Sesion.aperturaGavetaPorImpresora) {
			 	Sesion.crFiscalPrinterOperations.abrirGaveta();
				try {
					Sesion.crFiscalPrinterOperations.commit();
				} catch (PrinterNotConnectedException e) {
					logger.error("abrirGaveta(boolean)", e);
				}
			 } else {
				try{ 
					CR.crVisor.abrirGaveta(1); 
				}
				catch(Exception ex){
					logger.error("abrirGaveta(boolean)", ex);
				}
			 }
		}

		if (logger.isDebugEnabled()) {
			logger.debug("abrirGaveta(boolean) - end");
		}
	}	
	
	/**
	 * Método consultaProducto
	 * 
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	public void consultaProducto() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("consultaProducto() - start");
		}

		verificarAutorizacion ("UTILIDADES","consultaProducto");

		if (logger.isDebugEnabled()) {
			logger.debug("consultaProducto() - end");
		}
	}
	
	/**
	 * Método consultaCliente
	 * 
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	public void consultaCliente() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("consultaCliente() - start");
		}
	
		verificarAutorizacion ("UTILIDADES","consultaCliente");
	
		if (logger.isDebugEnabled()) {
			logger.debug("consultaCliente() - end");
		}
	}
	
	public void cargarSerialFiscalCaja() throws PrinterNotConnectedException {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarSerialFiscalCaja() - start");
		}

		if(Sesion.impresoraFiscal) {
			try {
				String serialFiscal = Sesion.crFiscalPrinterOperations.obtenerSerialImpresora();
				try {
					Sesion.getCaja().setSerial(serialFiscal);
				} catch (BaseDeDatosExcepcion e1) {
					logger.error("cargarSerialFiscalCaja()", e1);
				} catch (ConexionExcepcion e1) {
					logger.error("cargarSerialFiscalCaja()", e1);
				}
			} catch (PrinterNotConnectedException e1) {
				logger.error("cargarSerialFiscalCaja()", e1);

				MensajesVentanas.aviso("No pudo obtenerse la información Fiscal");
				//Si no consigue que la impresora le de el número del serial lo pide de forma manual
				//SerialDispositivoFiscal sdf = new SerialDispositivoFiscal();
				//MensajesVentanas.centrarVentanaDialogo(sdf);
				throw (new PrinterNotConnectedException("No se ha cargado información del Serial Fiscal."));
				/*try {
					Sesion.getCaja().setSerial(sdf.getSerial());
				} catch (BaseDeDatosExcepcion e2) {
					logger.error("cargarSerialFiscalCaja()", e1);
				} catch (ConexionExcepcion e2) {
					logger.error("cargarSerialFiscalCaja()", e1);
				}*/
			}
		} else {
			String numserial=MediadorBD.obtenerSerialCaja();

			/*
			 * verificamos si el serial existe, si no existe lo pedimos
			 * y luego lo almacenamos
			 */
			if(numserial.equals("")){
				/*
				 * No existe el serial fiscal, lo pedimos y lo almacenamos
				 */
				SerialDispositivoFiscal sdf = new SerialDispositivoFiscal();
				MensajesVentanas.centrarVentanaDialogo(sdf);
				try {
					Sesion.getCaja().setSerial(sdf.getSerial());
				} catch (BaseDeDatosExcepcion e1) {
					logger.error("cargarSerialFiscalCaja()", e1);
				} catch (ConexionExcepcion e1) {
					logger.error("cargarSerialFiscalCaja()", e1);
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cargarSerialFiscalCaja() - end");
		}
	}
	
	public void efectuarRetencionIVA() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("efectuarRetencionIVA() - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "efectuarRetencionIVA");
		if (CR.meVenta.getVenta().getCliente().getCodCliente() == null) {
			MensajesVentanas.aviso("Para hacer una retención de IVA debe ingresar el afiliado");
		} else {
			Sesion.setUbicacion("FACTURACION", "efectuarRetencionIVA");
			verificarAutorizacion ("FACTURACION","efectuarRetencionIVA");
			
			Pago nvoPago;
			if (CR.meServ.getCotizacion()!=null) {
				nvoPago = ManejoPagosFactory.getInstance().realizarPagoRetencion(CR.meVenta.getVenta().getMontoImpuesto() + CR.meServ.getCotizacion().getMontoImpuesto(), CR.meVenta.getVenta().getPagos());
				CR.meVenta.getVenta().efectuarPago(nvoPago);
			} else {
				nvoPago = ManejoPagosFactory.getInstance().realizarPagoRetencion(CR.meVenta.getVenta().getMontoImpuesto(), CR.meVenta.getVenta().getPagos());
				CR.meVenta.getVenta().efectuarPago(nvoPago);
			}		
			// Actualizamos el estado de la caja
			Sesion.getCaja().setEstado(edoFinalCaja);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("efectuarRetencionIVA() - end");
		}
	}

	public void recalcularRetencionIVA() {
		if (logger.isDebugEnabled()) {
			logger.debug("recalcularRetencionIVA() - start");
		}

		if (CR.meServ.getCotizacion()!=null)
			ManejoPagosFactory.getInstance().recalcularPagoRetencion(CR.meVenta.getVenta().getMontoImpuesto() + CR.meServ.getCotizacion().getMontoImpuesto(), CR.meVenta.getVenta().getPagos());
		else
			ManejoPagosFactory.getInstance().recalcularPagoRetencion(CR.meVenta.getVenta().getMontoImpuesto(), CR.meVenta.getVenta().getPagos());
			
		if (logger.isDebugEnabled()) {
			logger.debug("recalcularRetencionIVA() - end");
		}
	}
	
	public static void verificarTransacciones(String errorMsg) {
		if (logger.isDebugEnabled()) {
			logger.debug("verificarTransacciones(String) - start");
		}

		if (MediadorBD.getTransaccionesNoSync() > 0) {
			HiloSyncTransacciones.runAndWait();
			if (MediadorBD.getTransaccionesNoSync() > 0) {
				MensajesVentanas.aviso(errorMsg);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("verificarTransacciones(String) - end");
		}
	}
	
	/**
	 * Método validarCierre
	 * 		Permite la validación del password del usuario que se encuentra logueado
	 * en la CR al momento de presionar "F12 Salir" para evitar cierres por parte de
	 * otras personas.
	 * @param password Clave a ser validada con la del usuario activo
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ExcepcionCr
	 * @return boolean
	 */
	public static void validarCierre(String password) throws UsuarioExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("validarCierre(String) - start");
		}
		
		if (!Sesion.getUsuarioActivo().getClave().equals(password)) {
			throw new UsuarioExcepcion("Clave inválida. Intente de nuevo.");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("validarCierre(String) - end");
		}
	}
	
	public boolean verificarAperturaCajaPrincipal() throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("verificarAperturaCajaPrincipal() - start");
		}
		
		if(MaquinaDeEstado.isAccesoFuncion(36, 47)) {
			// El Usuario puede efectuar operaciones de Servicios al Cliente.
			// Verificamos apertura de Caja Principal
			return MediadorBD.isCajaPrincipalAperturada();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("verificarAperturaCajaPrincipal() - end");
		}
		
		return true;
	}

	/**
	 * Transfiere por Ftp al servidor de la Tienda los archivos vPos
	 */
	private void transferirVPosTimers() {
		// Creamos la instancia del Manejador de Ftp
		FtpManager ftp = new FtpManager("soporte", "sistemas");
		
		// El directorio remoto será vPorTimerFiles
		String remoteDir[] = {"vPosTimerFiles"};
		
		// Obtenemos el url del Servidor de la Tienda
		String host = Sesion.getDbUrlServidor().split("/")[2];
		
		// Nos ubicamos en el directorio de vPos/timer
		String dirArchivos = "./vPos/timer";
		File dirVPos = new File(dirArchivos);
		if (dirVPos.isDirectory())  {
			
			// Obtenermos todos los archivos que se encuentran en el Directorio
			File listaArchivos[] = dirVPos.listFiles();
			for (int i=0; i< listaArchivos.length; i++) {
				//String nomArchActual = listaArchivos[i];
				
				// Para cada archivo encontrado transmitimos por ftp y lo eliminamos
				File archivoActual = listaArchivos[i];
				if (!archivoActual.isDirectory()) {
					
					// Transferimos el archivo encontrado
					try {
						ftp.putFtpFile(archivoActual.getAbsolutePath(), archivoActual.getName(), host, remoteDir);
						
						// Si transfirio correctamente eliminamos el archivo
						archivoActual.delete();
					} catch (Exception e) {
						// No se transfirio el Archivo, no lo eliminamos
					}				
				}
			}
		}  else  {
	    		System.out.println(dirArchivos + " no es un directorio");
		}
	}

	/**
	 * Transfiere por Ftp al servidor de la Tienda los archivos vPos
	 */
	private void eliminarVPosVouchers() {
		// Nos ubicamos en el directorio de vPos/voucher
		String dirArchivos = "./vPos/voucher";
		File dirVPos = new File(dirArchivos);
		if (dirVPos.isDirectory())  {
			
			// Obtenermos todos los archivos que se encuentran en el Directorio
			File listaArchivos[] = dirVPos.listFiles();
			for (int i=0; i< listaArchivos.length; i++) {

				// Para cada archivo encontrado transmitimos por ftp y lo eliminamos
				File archivoActual = listaArchivos[i];
				if (!archivoActual.isDirectory()) {
					Calendar fechaLimite = Calendar.getInstance();
					fechaLimite.add(Calendar.DATE, -15);
					
					Calendar fechaArchivo = Calendar.getInstance();
					fechaArchivo.setTimeInMillis(archivoActual.lastModified());
					
					if (fechaArchivo.before(fechaLimite)) {
						// Tenemos que eliminar el archivo
						archivoActual.delete();
					}
				}
			}
		}  else  {
	    		System.out.println(dirArchivos + " no es un directorio");
		}
	}
	
	/**
	 * IROJAS 22/04/2010: Ejecuta, en caso de ser necesario, los comnados del script indicado
	 * @param
	 * @throws ExcepcionCr Si ocurre un error en la recuperacion
	 */
	public static void ejecutarScriptComandos() throws ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("ejecutarScriptComandos(String) - start");
		}
		//*******************************************************************
		//******************************************************************
		Calendar fechaHoy = Calendar.getInstance();
		//********* Caso del mes 12 que no lo registra la clase Calendar como mes válido
		String mes2Digitos = "";
		if (fechaHoy.get(Calendar.MONTH) == 11) {
			mes2Digitos= "12";
		} else if (fechaHoy.get(Calendar.MONTH) == 0) {
			mes2Digitos= "01";
		} else {
			fechaHoy.set(Calendar.MONTH, (fechaHoy.get(Calendar.MONTH) + 1));
			mes2Digitos =  (fechaHoy.get(Calendar.MONTH) < 10) ? "0" + fechaHoy.get(Calendar.MONTH) : ""+fechaHoy.get(Calendar.MONTH);
		}
		//************
		String dia2Digitos =  (fechaHoy.get(Calendar.DAY_OF_MONTH) < 10) ? "0" + fechaHoy.get(Calendar.DAY_OF_MONTH) : ""+fechaHoy.get(Calendar.DAY_OF_MONTH);
		String fechaActualizacion = fechaHoy.get(Calendar.YEAR) + mes2Digitos + dia2Digitos +  "000000";
		//*******************************************************************
		//******************************************************************
		File archivo = new File(Sesion.scriptsComandos);

		Date fechaEjecucionScript = MediadorBD.consultarEjecucionScript("scriptcomandos");
		if (fechaEjecucionScript != null) {
		     try {
		       	 Date fecha = new Date(archivo.lastModified());
			         Calendar fechaArch = Calendar.getInstance();
			         fechaArch.setTime(fecha);
			         fechaArch.set(Calendar.HOUR, 0);
			         fechaArch.set(Calendar.MINUTE, 0);
			         fechaArch.set(Calendar.SECOND, 0);
			         fechaArch.set(Calendar.MILLISECOND, 0);
			        
			         Calendar fechaEjecucionScriptCalendar = Calendar.getInstance();
			         fechaEjecucionScriptCalendar.setTime(fechaEjecucionScript);
			  
			         if (fechaArch.after(fechaEjecucionScriptCalendar)) { 
			        	 logger.error("El archivo fue modificado");
			    		LineNumberReader reader = null;
			    		String linea = null;
			    		try {
			    			reader = new LineNumberReader(new FileReader(archivo));
			    			while ((linea = reader.readLine()) != null) {
				    			Thread.sleep(3000);
				    			String lineaAux=linea.trim();
				    			lineaAux=lineaAux.replaceAll("\"", "\\\"");
				    			logger.error("Ejecutando el comando: "+lineaAux);
				    			
			    				Process p = Runtime.getRuntime().exec(lineaAux);
	    				
			    			}
				        	 //Se actualiza planificador para que modifiquela última fecha de ejecución.
				        	 MediadorBD.actualizarPlanificadorScript(fechaActualizacion, "scriptcomandos");
			    			 MensajesVentanas.aviso("Versión de CR Actualizada");
			    			 logger.error("Version actualizada");
			    		} catch (Exception e) { 
			    			MensajesVentanas.aviso(" Error ejecutando comando: " + linea);
			    			logger.error(" Error ejecutando comando: " + linea);
			    		}finally {
			    			if (reader != null) {
			    				reader.close();
			    			}
			    		}

			         }
		        }  catch (Exception e){
		       	 	logger.error("** No se pudo ejecutar el archivo de comandos: ",e);
		        }
			
		} else {
			//Si no existe la tabla o el registro de scriptComandos se ejecuta el script siempre
			// se crea la tabla en caso de que no exista
			try {
				LineNumberReader reader = null;
	    		String linea = null;
	    		try {
	    			reader = new LineNumberReader(new FileReader(archivo));
	    			while ((linea = reader.readLine()) != null) {
	    				Thread.sleep(3000);
	    				Runtime.getRuntime().exec(linea.trim());
	    			}
		        	 //Se actualiza planificador para que modifiquela última fecha de ejecución.
		        	 MediadorBD.actualizarPlanificadorScript(fechaActualizacion, "scriptcomandos");
	    			 MensajesVentanas.aviso("Versión de CR Actualizada");
	    		} catch (Exception e) { 
	    			logger.error(" Error ejecutando comando: " + linea);
	    		}finally {
	    			if (reader != null) {
	    				reader.close();
	    			}
	    		}
			 }  catch (Exception e){
				 logger.error("** No se pudo ejecutar el archivo de comandos ",e);
		     }
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("ejecutarScriptComandos() - end");
		}
	}
	/**
	 * aavila 26/08/2010: Ejecuta SQL del archivo aSentenciaSQL.
	 * */
	public static void ejecutarSentenciaSQL() throws ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("ejecutarSentenciaSQL(String) - start");
		}
		Calendar fechaHoy = Calendar.getInstance();
		//********* Caso del mes 12 que no lo registra la clase Calendar como mes válido
		String mes2Digitos = "";
		if (fechaHoy.get(Calendar.MONTH) == 11) {
			mes2Digitos= "12";
		} else if (fechaHoy.get(Calendar.MONTH) == 0) {
			mes2Digitos= "01";
		} else {
			fechaHoy.set(Calendar.MONTH, (fechaHoy.get(Calendar.MONTH) + 1));
			mes2Digitos =  (fechaHoy.get(Calendar.MONTH) < 10) ? "0" + fechaHoy.get(Calendar.MONTH) : ""+fechaHoy.get(Calendar.MONTH);
		}
		String dia2Digitos =  (fechaHoy.get(Calendar.DAY_OF_MONTH) < 10) ? "0" + fechaHoy.get(Calendar.DAY_OF_MONTH) : ""+fechaHoy.get(Calendar.DAY_OF_MONTH);
		String fechaActualizacion = fechaHoy.get(Calendar.YEAR) + mes2Digitos + dia2Digitos +  "000000";
		File archivo = new File(Sesion.sentenciaSQL);
		Date fechaEjecucionScript = MediadorBD.consultarEjecucionScript("ejecutarSentenciaSQL");
		if (fechaEjecucionScript != null) {
		     try {
		       	 Date fecha = new Date(archivo.lastModified());
			         Calendar fechaArch = Calendar.getInstance();
			         fechaArch.setTime(fecha);
			         fechaArch.set(Calendar.HOUR, 0);
			         fechaArch.set(Calendar.MINUTE, 0);
			         fechaArch.set(Calendar.SECOND, 0);
			         fechaArch.set(Calendar.MILLISECOND, 0);
			        
			         Calendar fechaEjecucionScriptCalendar = Calendar.getInstance();
			         fechaEjecucionScriptCalendar.setTime(fechaEjecucionScript);
			  
			         if (fechaArch.after(fechaEjecucionScriptCalendar)) { 
			        	 logger.error("El archivo fue modificado");
			    		LineNumberReader reader = null;
			    		String linea = null;
			    		try {
			    			reader = new LineNumberReader(new FileReader(archivo));
			    			while ((linea = reader.readLine()) != null) {
				    			Thread.sleep(3000);
				    			String lineaAux=linea.trim();
				    			lineaAux=lineaAux.replaceAll("\"", "\\\"");
				    			logger.error("Ejecutando el comando: "+lineaAux);
				    			try{
				    				MediadorBD.ejecutar(lineaAux, true);	
				    			}catch(Exception e){logger.error(" Error ejecutando comando: " + linea);}
			    			}
				        	 //Se actualiza planificador para que modifiquela última fecha de ejecución.
				        	 MediadorBD.actualizarPlanificadorScript(fechaActualizacion, "ejecutarSentenciaSQL");
			    			 MensajesVentanas.aviso("Versión de CR Actualizada");
			    			 logger.error("Version actualizada");
			    		} catch (Exception e) { 
			    			logger.error(" Error ejecutando comando: " + linea);
			    		}finally {
			    			if (reader != null) {
			    				reader.close();
			    			}
			    		}

			         }
		        }  catch (Exception e){
		       	 	logger.error("** No se pudo ejecutar el archivo de comandos: ",e);
		        }
			
		} else {
			//Si no existe la tabla o el registro de scriptComandos se ejecuta el script siempre
			try {
				LineNumberReader reader = null;
	    		String linea = null;
	    		try {
	    			reader = new LineNumberReader(new FileReader(archivo));
	    			while ((linea = reader.readLine()) != null) {
	    				Thread.sleep(3000);
	    				String lineaAux=linea.trim();
		    			lineaAux=lineaAux.replaceAll("\"", "\\\"");
		    			try{
		    				MediadorBD.ejecutar(lineaAux, true);
		    			}catch(Exception e){logger.error(" Error ejecutando comando: " + linea);}
	    			}
		        	 //Se actualiza planificador para que modifiquela última fecha de ejecución.
		        	 MediadorBD.actualizarPlanificadorScript(fechaActualizacion, "ejecutarSentenciaSQL");
	    			 MensajesVentanas.aviso("Versión de CR Actualizada");
	    		} catch (Exception e) { 
	    			logger.error(" Error ejecutando comando: " + linea);
	    		}finally {
	    			if (reader != null) {
	    				reader.close();
	    			}
	    		}
			 }  catch (Exception e){
				 logger.error("** No se pudo ejecutar el archivo de comandos ",e);
		     }
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("ejecutarScriptComandos() - end");
		}
	}	
	/**
	 * JGRATEROL Y GMARTINELLI (COPIA DE IROJAS)
	 * Actualización automaticamente las preferencias de caja
	 * @param
	 * @throws ExcepcionCr Si ocurre un error en la recuperacion
	 */
	public static void ejecutarCambiarPreferencias() throws ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("ejecutarScriptComandos(String) - start");
		}
		//*******************************************************************
		//******************************************************************
		Calendar fechaHoy = Calendar.getInstance();
		//********* Caso del mes 12 que no lo registra la clase Calendar como mes válido
		String mes2Digitos = "";
		if (fechaHoy.get(Calendar.MONTH) == 11) {
			mes2Digitos= "12";
		} else if (fechaHoy.get(Calendar.MONTH) == 0) {
			mes2Digitos= "01";
		} else {
			fechaHoy.set(Calendar.MONTH, (fechaHoy.get(Calendar.MONTH) + 1));
			mes2Digitos =  (fechaHoy.get(Calendar.MONTH) < 10) ? "0" + fechaHoy.get(Calendar.MONTH) : ""+fechaHoy.get(Calendar.MONTH);
		}
		//************
		String dia2Digitos =  (fechaHoy.get(Calendar.DAY_OF_MONTH) < 10) ? "0" + fechaHoy.get(Calendar.DAY_OF_MONTH) : ""+fechaHoy.get(Calendar.DAY_OF_MONTH);
		String fechaActualizacion = fechaHoy.get(Calendar.YEAR) + mes2Digitos + dia2Digitos +  "000000";
		//*******************************************************************
		//******************************************************************
		File archivo = new File(Sesion.cambioPreferencias);
		Date fechaCambioPreferencias = MediadorBD.consultarEjecucionScript("cambiopreferencias");
		if (fechaCambioPreferencias != null) {
		     try {
		       	 Date fecha = new Date(archivo.lastModified());
			         Calendar fechaArch = Calendar.getInstance();
			         fechaArch.setTime(fecha);
			         fechaArch.set(Calendar.HOUR, 0);
			         fechaArch.set(Calendar.MINUTE, 0);
			         fechaArch.set(Calendar.SECOND, 0);
			         fechaArch.set(Calendar.MILLISECOND, 0);
			        
			         Calendar fechaCambioPreferenciasCalendar = Calendar.getInstance();
			         fechaCambioPreferenciasCalendar.setTime(fechaCambioPreferencias);
			  
			         if (fechaArch.after(fechaCambioPreferenciasCalendar)) { 
			    		LineNumberReader reader = null;
			    		String linea = null;
			    		try {
			    			reader = new LineNumberReader(new FileReader(archivo));
			    			while ((linea = reader.readLine()) != null) {
			    				//Dividir la linea por tabuladores
			    				StringTokenizer st = new StringTokenizer(linea, '\t'+"");
			    				Vector<String> preferencia =  new Vector<String>();
			    				while(st.hasMoreElements()){
			    					preferencia.addElement(st.nextToken());
			    				}
				    			//Cambiar la preferencia del archivo
			    				EPAPreferenceProxy eppCR = new EPAPreferenceProxy(preferencia.elementAt(0));
			    				eppCR.setConfigStringForParameter(preferencia.elementAt(1),preferencia.elementAt(2), preferencia.elementAt(3));
			    			}
				        	 //Se actualiza planificador para que modifiquela última fecha de ejecución.
				        	 MediadorBD.actualizarPlanificadorScript(fechaActualizacion, "cambiopreferencias");
			    			 MensajesVentanas.aviso("Versión de CR Actualizada");
			    		} catch (Exception e) { 
			    			logger.error(" Error ejecutando comando: " + linea);
			    		}finally {
			    			if (reader != null) {
			    				reader.close();
			    			}
			    		}

			         }
		        }  catch (Exception e){
		       	 	logger.error("** No se pudo ejecutar el archivo de comandos: ",e);
		        }
			
		} else {
			//Si no existe la tabla o el registro de scriptComandos se ejecuta el script siempre
			try {
				LineNumberReader reader = null;
	    		String linea = null;
	    		try {
	    			reader = new LineNumberReader(new FileReader(archivo));
	    			while ((linea = reader.readLine()) != null) {
	    				//Dividir la linea por tabuladores
	    				StringTokenizer st = new StringTokenizer(linea, '\t'+"");
	    				Vector<String> preferencia =  new Vector<String>();
	    				while(st.hasMoreElements()){
	    					preferencia.addElement(st.nextToken());
	    				}
		    			//Cambiar la preferencia del archivo
	    				EPAPreferenceProxy eppCR = new EPAPreferenceProxy(preferencia.elementAt(0));
	    				eppCR.setConfigStringForParameter(preferencia.elementAt(1),preferencia.elementAt(2), preferencia.elementAt(3));
	    			}
		        	 //Se actualiza planificador para que modifiquela última fecha de ejecución.
		        	 MediadorBD.actualizarPlanificadorScript(fechaActualizacion, "cambiopreferencias");
	    			 MensajesVentanas.aviso("Versión de CR Actualizada");
	    		} catch (Exception e) { 
	    			logger.error(" Error ejecutando comando: " + linea);
	    		}finally {
	    			if (reader != null) {
	    				reader.close();
	    			}
	    		}
			 }  catch (Exception e){
				 logger.error("** No se pudo ejecutar el archivo de comandos ",e);
		     }
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("ejecutarScriptComandos() - end");
		}
	}
	
	
	/**
	 * Sincroniza un producto del servidor central a los servidores de tienda
	 * @param codigo
	 * @param consecutivo
	 * @throws Exception
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'LinkedHashMap'
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unchecked")
	public void transferirProducto(String codigo) throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug("transferirProducto() - start");
		}
		
		CR.me.verificarAutorizacion("Utilidades","TRANSFERIRPRODUCTO");
	    DocumentBuilder db;
		DocumentBuilderFactory dbf;
		dbf = DocumentBuilderFactory.newInstance();
	    dbf.setNamespaceAware(true);
	    dbf.setCoalescing(true);
	    dbf.setExpandEntityReferences(true);
	    dbf.setIgnoringComments(true);
	    dbf.setIgnoringElementContentWhitespace(true);
	    db = dbf.newDocumentBuilder();

		ComunicarConServidor com = new ComunicarConServidor(Sesion.ipServidorPDA, Sesion.puertoServidorPDA);
		Document doc = db.newDocument();
		Element sincronizador = doc.createElement("Sincronizador");
		Element clase = doc.createElement("nombreDeClase");
		clase.appendChild(doc.createTextNode("com.beco.sistemas.aplicacionespda.servidordetienda.controlador.SincronizarProducto"));
		Element sincProd = doc.createElement("sincronizarProd");
		Element codprod = doc.createElement("codproducto");
		codprod.appendChild(doc.createTextNode(codigo));
		sincProd.appendChild(codprod);
		sincronizador.appendChild(clase);
		sincronizador.appendChild(sincProd);
		doc.appendChild(sincronizador);
		com.enviarXml(doc);
		doc = com.obtenerXmlDeServidor();
		String nombreEtiq = doc.getFirstChild().getFirstChild().getNodeName();
		nombreEtiq.toLowerCase();
		if(nombreEtiq.equals("error")){
			throw new Exception(doc.getFirstChild().getFirstChild().getFirstChild().getNodeValue());
			//throw new Exception("Error en la transferencia");
		}
		cerrarConexion(db,com);
		if (logger.isDebugEnabled())
			logger.debug("transferirProducto() - end");
	}
	
	/**
     * Maneja el inicio de sesión.
     * @param id código de identificación del usuario del sistema
     * @param clave clave del usuario del sistema
     * @param numOperacion Operación que se realizará luego del inicio de sesión.
     * @throws Exception
     */
    private void iniciarSesion(String id, String clave,int numOperacion,ComunicarConServidor com,DocumentBuilder db)throws Exception{
		Document doc = db.newDocument();
		String metodo = "",nClase = "";
   		if(numOperacion == Sesion.OperacionVenta){
   			metodo = "VENTAS";
   			nClase = "MoverACaja";
   		}else if(numOperacion == Sesion.OperacionIndicadores){
   			metodo = "INDICADORES";
   			nClase = "Indicadores";
   		}else if(numOperacion == Sesion.OperacionTransferencia){
   			metodo = "SOLICITUDDECODIGO";
   			nClase = "SincronizarProducto";
   		}else if(numOperacion == Sesion.OperacionCarteles){
   			metodo = "CARTELES";
   			nClase = "GestionDeCarteles";
   		}else if(numOperacion == Sesion.OperacionCotizacion){
   			metodo = "COTIZACIONES";
   			nClase = "Cotizaciones";
   		}
		Element iniciarSesion = doc.createElement("IniciarSesion");
		Element clase = doc.createElement("nombreDeClase");
		clase.appendChild(doc.createTextNode("com.beco.sistemas.aplicacionespda.servidordetienda.controlador."+nClase));
		Element inicioSesion = doc.createElement("inicioSesion");
		Element idC = doc.createElement("codigodebarras");
		Element claveC = doc.createElement("clave");
		Element metodoS = doc.createElement("metodo");
		Element moduloS = doc.createElement("modulo");
		byte[] claveDES = {6, 9, 8, 4, 9, 1, 9, 3, 0, 9, 6, 1, 6, 0, 8, 8, 8, 6, 7, 1, 8, 7, 5, 1};
		TriDES.prepare(claveDES);
		String idCrypt = "<![CDATA["+TriDES.encrypt(id)+"]]>";//id;
		String claveCrypt = "<![CDATA["+TriDES.encrypt(clave)+"]]>";//clave;
		idC.appendChild(doc.createTextNode(idCrypt));
		claveC.appendChild(doc.createTextNode(claveCrypt));
		metodoS.appendChild(doc.createTextNode(metodo));
		moduloS.appendChild(doc.createTextNode("PDA"));
		inicioSesion.appendChild(idC);
		inicioSesion.appendChild(claveC);
		inicioSesion.appendChild(moduloS);
		inicioSesion.appendChild(metodoS);
		iniciarSesion.appendChild(clase);
		iniciarSesion.appendChild(inicioSesion);
		doc.appendChild(iniciarSesion);
		com.enviarXml(doc);
		doc = com.obtenerXmlDeServidor();
		System.out.println("Se recibió: "+convertirXml(doc.getFirstChild()));
		String nombreEtiq = doc.getFirstChild().getFirstChild().getNodeName();
		nombreEtiq.toLowerCase();
		if(nombreEtiq.equals("error")){
			//throw new Exception("Error en la transferencia");
			throw new Exception(doc.getFirstChild().getFirstChild().getFirstChild().getNodeValue());
		}
    }
    
	/**
	 * Cierra la conexión con el servidor
	 */
	private void cerrarConexion(DocumentBuilder db,ComunicarConServidor com){
	    Document doc = db.newDocument();
		Element end = doc.createElement("operacionConcluida");
		end.appendChild(doc.createTextNode("over"));
		doc.appendChild(end);
		try{
			com.enviarXml(doc);
		}catch(Exception e){
			
		}
		com.cerrarConexion();
		com = null;
		db = null;
	}
	public static String convertirXml(Node root){
		if(root.getNodeType() == Node.ELEMENT_NODE){
			NodeList hijos = root.getChildNodes();
			String arbol = new String("<"+root.getNodeName()+">");
			int numHijos = hijos.getLength();
			for(int i = 0; i < numHijos;++i){
				arbol = arbol.concat(convertirXml(hijos.item(i)));
			}
			arbol = arbol.concat("</"+root.getNodeName()+">");
			return arbol;
		}else if(root.getNodeType() == Node.TEXT_NODE){
			return root.getNodeValue();
		}else{
			return "";
		}
	}
	
	public static void ejecutarPoliticasLimpieza(){
		SimpleDateFormat fechaRep = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar fechaActual = Calendar.getInstance();	
    	String fechaHoyString=  fechaRep.format(fechaActual.getTime());
    	//******** 04/01/2010
		//******** IROJAS: Agregada ejecución de las políticas de limpieza al apagar CR y luego del Reporte Z
		//********
		try { 
			Calendar fechaLimpiezaAnual = Calendar.getInstance();
			fechaLimpiezaAnual.setTime(MediadorBD.fechaProxlimpiezaAnual());
			SimpleDateFormat fechaFormat = new SimpleDateFormat("yyyy-MM-dd");
			String fechaLimpAnualString = fechaFormat.format(fechaLimpiezaAnual.getTime()) ;
			
			Calendar fechaLimpiezaMensual = Calendar.getInstance();
			fechaLimpiezaMensual.setTime(MediadorBD.fechaProxlimpiezaMensual());
			String fechaLimpMensualString = fechaFormat.format(fechaLimpiezaMensual.getTime()) ;
			
			Calendar fechaLimpiezaDiaria = Calendar.getInstance();
			fechaLimpiezaDiaria.setTime(MediadorBD.fechaProxlimpiezaDiaria());
			String fechaLimpDiariaString = fechaFormat.format(fechaLimpiezaDiaria.getTime()) ;
			
			if(fechaHoyString.equals(fechaLimpAnualString) || fechaActual.after(fechaLimpiezaAnual) || fechaLimpAnualString.equals("1970-12-31")) {
				Principal.limpieza (Sesion.POLITICA_ANUAL);
			} 	
			
			if(fechaHoyString.equals(fechaLimpMensualString) || fechaActual.after(fechaLimpiezaMensual) || fechaLimpMensualString.equals("1970-12-31")) {
				Principal.limpieza (Sesion.POLITICA_MENSUAL);
			} 	
			
			if(fechaHoyString.equals(fechaLimpDiariaString) || fechaActual.after(fechaLimpiezaDiaria) || fechaLimpDiariaString.equals("1970-12-31")) {
				Principal.limpieza (Sesion.POLITICA_DIARIO);
				
			} 	
		} catch (Exception e) {
			try {				
				MediadorBD.crearTablePoliticasLimpieza();
			} catch (Exception e1) {logger.error("ejecutarPoliticasLimpieza()", e);}
			  catch (Throwable e1) {logger.error("ejecutarPoliticasLimpieza()", e);}
			logger.error("ejecutarPoliticasLimpieza()", e);
		} catch (Throwable t) {
			try {			
				MediadorBD.crearTablePoliticasLimpieza();
			} catch (Exception e1) {logger.error("ejecutarPoliticasLimpieza()", e1);}
			  catch (Throwable e1) {logger.error("ejecutarPoliticasLimpieza()", e1);}
			logger.error("ejecutarPoliticasLimpieza()", t);
		}
		//********
	}
	
	/**
	 * Obtiene las promociones de Bono Regalo activas
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<PromocionBR> getPromocionesActivasBR(){
		//Filtrar por tipo de promocion
		try {
			return BaseDeDatosServicio.getPromocionesActivasBR();
		} catch (BaseDeDatosExcepcion e) {
			MensajesVentanas.mensajeError(e.getMensaje());
			return new Vector<PromocionBR>();
		}
	}
	
	/**
	 * Recarga tarjeta de bono por concepto de promoción
	 * @param operacion
	 * @param montoAcreditar
	 * @param cliente
	 * @param aviso
	 * @throws ConexionExcepcion
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws XmlExcepcion
	 * @throws FuncionExcepcion
	 * @throws ExcepcionCr
	 * @throws PrinterNotConnectedException
	 */
	public void recargarTarjetaPorPromocion(double montoAcreditar, Cliente cliente, String codTarjeta) throws ConexionExcepcion, UsuarioExcepcion, MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion, ExcepcionCr, PrinterNotConnectedException {
		if (logger.isDebugEnabled()) {
			logger.debug("realizarVentaBR() - start");
		}
		
		DecimalFormat df = new DecimalFormat("#,##0.00");
		MensajesVentanas.aviso("A continuación se recargará Bs.: " + df.format(montoAcreditar) + "\n" +
				"a la tarjeta N. "+codTarjeta, false);
			
		// Seleccionada una opcion valida de carga/recarga
		CR.meServ.crearVentaBR(Sesion.TIPO_TRANSACCION_BR_RECARGA_PROMO, false);
		/*if(cliente.getNombre()==null || cliente.getNombre().equalsIgnoreCase("")){
			RegistroCliente registro = (new RegistroClienteFactory()).getInstance();
			registro.MostrarPantallaCliente(false,6);
		} */
		
		
		CR.meServ.agregarLineaDetalleBR(montoAcreditar);
		
		Pago p = new Pago(Sesion.FORMA_PAGO_OBSEQUIO_BR, montoAcreditar, null, null, null, null, 0, null);
		CR.meServ.getVentaBR().getPagos().addElement(p);
	
		CR.meServ.getVentaBR().setCodTarjetaPagoPromocion(codTarjeta);
		
		CR.meServ.finalizarVentaBR(true, false, false);
		
		if (logger.isDebugEnabled()) {
			logger.debug("realizarVentaBR() - end");
		}

	}

	public Hashtable getPromoMontoCantidad() {
		return promoMontoCantidad;
	}

	public void setPromoMontoCantidad(Hashtable promoMontoCantidad) {
		MaquinaDeEstado.promoMontoCantidad = promoMontoCantidad;
	}
	
}
