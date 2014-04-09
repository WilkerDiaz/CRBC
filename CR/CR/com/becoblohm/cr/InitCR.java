/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr
 * Programa   : InitCR.java
 * Creado por : Victor Medina <linux@epa.com.ve>
 * Creado en  : Feb 10, 2004 - 9:48:08 AM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.3.1
 * Fecha       : 01/07/2004 11:19 AM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : - Modificado el valor inicial de los parámetros "url" del 
 * 				nodo "db".
 * 				 - Agregado el parámetro "verificarLinea" al nodo "sistema" para 
 * 				indicar el estado de ejecución del sistema con respecto a la 
 * 				conexión de red.
 * 				 - Agregado el método getPreferencias.
 * =============================================================================
 * Versión     : 1.3.0
 * Fecha       : 29/06/2004 10:30:34 AM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : - Invocado el método asignarUsuarioLogueado de la clase MediadorBD al crear conexión con la BD local.
 * 					 - Modificada la "Carga de data Inicial" de modo que al fallar se coloque la CR fuera de línea y no sea
 * 					una falla fatal.
 * 					 - Agregado un mensaje informativo al ocurrir una falla fatal, antes de cerrar el sistema.
 * 					 - Modificados valores iniciales de los parámetros de BD para la conexión con el Servidor Central.
 * =============================================================================
 * Versión     : 1.2.2
 * Fecha       : 09/06/2004 03:52:34 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : - Agregada la validación de la existencia del directorio temporal para la
 * 				auditoría y el proceso de sincronización.
 * 				- Suspendido temporalmente la recarga del sistema en caso de falla fatal al 
 * 				inicio del mismo.
 * =============================================================================
 * Versión     : 1.2.0
 * Fecha       : 26/05/2004 03:52:34 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : - Cambiado el ámbito del atributo verificador.
 * 				- Agregado el atributo preferenciasCR para el control global de las 
 * 				preferencias bajo el nombre de "ProyectoCR".
 * 				- Modificado el método bootScreen para reorganizar secuencia de inicio 
 * 				del sistema.
 * 				- Agregado el método cargarPreferencias que inicializa valores del nodo
 * 				"db" para las preferencias de base de datos en caso de no estar definidas 
 * 				en el sistema.
 * =============================================================================
 * Versión     : 1.1 (Segun CVS)
 * Fecha       : Feb 10, 2004
 * Analista    : Victor Medina <linux@epa.com.ve>
 * Descripción : Esta clase provee todos los métodos que inicializan la caja,
 * esta PESADAMENTE basado en la inicialización inicial de Alexis Guédez. 
 * 		Esta forma no debería ser la inicialización final, pero debería ser una idea 
 * bastante aproximada de lo que se quiere, el punto principal es ser lo mas 
 * elegante y limpio que se pueda.
 * =============================================================================
 */
package com.becoblohm.cr;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;

import javax.swing.JTextArea;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.extensiones.ActualizadorPreciosFactory;
import com.becoblohm.cr.extensiones.ManejoPagosFactory;
import com.becoblohm.cr.extensiones.PDAFactory;
import com.becoblohm.cr.manejadorauditoria.Auditoria;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstado;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarusuario.Usuario;
import com.becoblohm.cr.mediadoresbd.BaseDeDatosVenta;
import com.becoblohm.cr.mediadoresbd.BeansSincronizador;
//import com.becoblohm.cr.mediadoresbd.CargarAfiliados;
import com.becoblohm.cr.mediadoresbd.Conexiones;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.perifericos.Dispositivo;
import com.becoblohm.cr.sincronizador.HiloTransferenciasInmediatas;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.becoblohm.cr.verificador.Verificador;
import com.epa.crprinterdriver.PrinterNotConnectedException;
import com.epa.crserialinterface.Connection;
import com.epa.crserialinterface.Parameters;
import com.epa.crserialinterface.SerialExceptions;
import com.epa.preferencesproxy.EPAPreferenceProxy;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.NodeAlreadyExistsException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;

/**
 * Descripción:
 * 
 */
public class InitCR extends Thread {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(InitCR.class);

	public static Verificador verificador;
	public static EPAPreferenceProxy preferenciasCR;
	public static EPAPreferenceProxy prefsDispositivos;
	static boolean fallaFatal = false;
	static boolean fallaDispositivo = false;
	public static String estadoAlIniciar;
	public static String usuarioAlIniciar;
	public static boolean iniciando = true;
	
	/**
	 * Method bootScreen.
	 */
	public static void bootScreen() {
		if (logger.isDebugEnabled()) {
			logger.debug("bootScreen() - start");
		}

		try {
			try{
				MensajesVentanas.iniciarVerificadorFoco();
				//Carga las preferencias por defecto si no encuentra el archivo de configuracion
				cargarPreferencias();
				File pathTemporal = new File(preferenciasCR.getConfigStringForParameter("sistema", "pathtemporal"));
				if(!pathTemporal.exists()) {
					pathTemporal.mkdirs();
				} 
				// Cargamos el visor
				try{
					CR.cargarVisor();
					CR.crVisor.enviarString("INICIO DEL SISTEMA..","CAJA REGISTRADORA...");
				}
				catch(Exception ex){
					logger.error("bootScreen()", ex);
				}	

				try{ 
					  CR.crVisor.enviarString("INICIANDO SISTEMA ..","CARGANDO CLASES ..."); 
				}
				catch(Exception ex){
					logger.error("bootScreen()", ex);
				}
				Verificador.splash.showSplash();
				Verificador.splash.cambiarTexto("Cargando configuración...");
				Verificador.splash.activar(3);
				Verificador.splash.cambiarTexto("Cargando clases del sistema...");
				Verificador.splash.activar(2);
			} catch (UnidentifiedPreferenceException e) {
				logger.fatal("bootScreen()", e);

				Verificador.splash.desactivar(3);
				Auditoria.registrarAuditoria("Falla carga de las preferencias.", 'E');
				fallaFatal = true;
			} catch (NoSuchNodeException e) {
				logger.fatal("bootScreen()", e);

				Verificador.splash.desactivar(3);
				Auditoria.registrarAuditoria("Falla definición de preferencias de BD.", 'E');
				fallaFatal = true;
			}
			
			try{
				Verificador.splash.activar(1);
				Verificador.splash.cambiarTexto("Iniciando sistema de caja registradora...");
				Verificador.splash.activar(1);
				new Sesion();
			} catch (ConexionExcepcion e) {
				logger.fatal("bootScreen()", e);
				Verificador.splash.desactivar(1);
				fallaFatal = true;
	
			}
			
			try{
				try{ 
					 CR.crVisor.enviarString("INICIANDO SISTEMA...","CONECTANDO BD ..."); 
				}
				catch(Exception ex){
					logger.error("bootScreen()", ex);
				}
				Verificador.splash.cambiarTexto("Conectando con base de datos local...");
				Verificador.splash.activar(7);
				new Conexiones(true);
				MediadorBD.asignarUsuarioLogueado(null);
			} catch (ConexionExcepcion e) {
				logger.fatal("bootScreen()", e);

				Verificador.splash.desactivar(7);
				fallaFatal = true;
			}	

			try {
				Verificador.splash.cambiarTexto("Verificando conexión con servidor de tienda...");
				Verificador.splash.activar(8);
				new Conexiones(false);
				Sesion.setCajaEnLinea(true);
			} catch (ConexionExcepcion e) {
				logger.fatal("bootScreen()", e);

				Verificador.splash.desactivar(8);
				Sesion.setCajaEnLinea(false);
			}
			
			//WDIAZ:01-2013 Actualizar Número de Caja en el Servidor
			try{
				MediadorBD.actUsuarioCaja();
			} catch (Exception e) {
				e.printStackTrace();
			}
			

			try {
				Verificador.splash.cambiarTexto("Carga inicial de entidades base: Servidor -> CR");
				try{ 
					  CR.crVisor.enviarString("INICIANDO SISTEMA...","SINCRONIZANDO BD..."); 
				}
				catch(Exception ex){
					logger.error("bootScreen()", ex);
				}
				Verificador.splash.activar(4);
				try {
					/*if (getPreferencia("sistema", "sincronizarAfiliados", "N").equals("S") && getPreferencia("sistema", "actCajaTemporizada", "S").equals("N")) {*/
					if (getPreferencia("sistema", "sincronizarAfiliados", "N").equals("S") ) {
						BeansSincronizador.syncEntidadCR("afiliado");
						Verificador.splash.desactivar(4);
					}
					Verificador.splash.activar(4);
					BeansSincronizador.cargarEntidadesBaseCR();
					Verificador.splash.desactivar(4);
					Verificador.splash.activar(4);
					BeansSincronizador.actualizarEntidadesSistema(false);
					
					ManejoPagosFactory.getInstance().sincronizarDatosMaestroPagos();
				} finally {
					Conexiones.cerrarConexionesSync();
					//ACTUALIZACION BECO: 06/01/2009 - Módulo de promociones
					//Obtenemos los productos que estan promocionados en algun combo
					(new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().cargarPromocionesCombo();
					Sesion.HILO_TRANSFERENCIAS = new HiloTransferenciasInmediatas();
					Sesion.HILO_TRANSFERENCIAS.setName("Verificador de transferencias inmediatas");
					Sesion.HILO_TRANSFERENCIAS.start();
				}
				
				//BECO 12-07-2012: Se crea tabla que simula Vista para las promociones que se calculan al totalizar la Venta (Cupones, Ticket) para canjear o sorteos BANDERA1
				   (new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().crearTablaPromoTicket();
				//
				// Aborta transacciones que hayan quedado pendientes
				BaseDeDatosVenta.rollbackTransPendientesXImprimir();
				MaquinaDeEstado.verificarTransacciones("Hay ventas sin actualizar en el sistema.\n Notifique al administrador del sistema");
				//Verificador.splash.desactivar(5);
			} catch (Exception e) {
				logger.error("bootScreen()", e);

				Verificador.splash.desactivar(4);
				Sesion.setCajaEnLinea(false);
			}

			try{
				Verificador.splash.cambiarTexto("Iniciando máquina de estado del sistema...");
				try{ 
					  CR.crVisor.enviarString("INICIANDO SISTEMA...","INICIANDO ME ..."); 
				}
				catch(Exception ex){
					logger.error("bootScreen()", ex);
				}
				Verificador.splash.activar(4);
				new CR();
			} catch (ConexionExcepcion e) {
				logger.fatal("bootScreen()", e);

				Verificador.splash.desactivar(4);
				fallaFatal = true;
			}	

			try {
				Verificador.splash.cambiarTexto("Verificando dispositivos de la caja...");
				try{
					CR.crVisor.enviarString("INICIANDO SISTEMA...","VERIFICANDO DISP ...");
					CR.crVisor.cerrarConexion();
				} catch(Exception ex){
					logger.error("bootScreen()", ex);
				}	
				CR.crVisor = null;
				Verificador.splash.activar(6);
				JTextArea input;
				JTextArea output;
				Connection conexion;
				Parameters parametros;
				
				input = new JTextArea();
				output = new JTextArea();
				parametros = new Parameters();
				conexion = new Connection(parametros, output, input);
				boolean ignorarEscaner = preferenciasCR.getConfigStringForParameter("sistema", "ignorarEscaner").trim().toUpperCase().charAt(0) == Sesion.SI ? true:false;
				boolean ignorarImpresora = preferenciasCR.getConfigStringForParameter("sistema", "ignorarImpresora").trim().toUpperCase().charAt(0) == Sesion.SI ? true:false;
				boolean ignorarVisor = preferenciasCR.getConfigStringForParameter("sistema", "ignorarVisor").trim().toUpperCase().charAt(0) == Sesion.SI ? true:false;
				try {
					Dispositivo escaner = new Dispositivo("scanner");
					escaner.setPreferencias(parametros, prefsDispositivos);
					Verificador.splash.activar(6);
					try{
						conexion.openConnection();
						if(conexion.testDSR()!=true /*|| conexion.testDTR() !=true*/){	
							MensajesVentanas.mensajeError("El scanner no esta conectado");
							conexion.closeConnection();
							Verificador.splash.desactivar(6);
							setFallaDispositivo(true);
							if(ignorarEscaner) fallaFatal = false;
							else fallaFatal = true;
						}
						else{
							conexion.closeConnection();
						}
					}
					catch(SerialExceptions ex){
						logger.error("bootScreen()", ex);

						MensajesVentanas.mensajeError("Error con el puerto serial del scanner");
						Verificador.splash.desactivar(6);
						setFallaDispositivo(true);
						if(ignorarEscaner) fallaFatal = false;
						else fallaFatal = true;
					}
					//WDIAZ:Se comento porque estaba dando error para maquinas windows, y ya se esta realizando la conexion anteriormente
					/*
					Dispositivo impresora = new Dispositivo("impresora");
					parametros.setNombreDispositivo(Parameters.Dispositivo.impresora.name());
					impresora.setPreferencias(parametros, prefsDispositivos);
					Verificador.splash.activar(6);
					try{
						conexion.openConnection();
						if(conexion.testDSR()!=true){
							MensajesVentanas.mensajeError("La Impresora no esta Conectada");
							conexion.closeConnection();
							Verificador.splash.desactivar(6);
							setFallaDispositivo(true);
							if(ignorarImpresora) fallaFatal = false;
							else fallaFatal = true;
						}
						else{
							conexion.closeConnection();
						}
					}
					catch(SerialExceptions ex){
						logger.error("bootScreen()", ex);
						MensajesVentanas.mensajeError("Error con el puerto serial de la impresora");
						Verificador.splash.desactivar(6);
						setFallaDispositivo(true);
						if(ignorarImpresora) fallaFatal = false;
						else fallaFatal = true;
					}
*/
					Dispositivo visor = new Dispositivo("visor");
					visor.setPreferencias(parametros, prefsDispositivos);
					Verificador.splash.activar(6);
					try{
						conexion.openConnection();
						if(conexion.testDSR()!=true/* || conexion.testDTR() !=true*/){
							MensajesVentanas.mensajeError("El visor no esta Conectado");
							conexion.closeConnection();
							Verificador.splash.desactivar(6);
							setFallaDispositivo(true);
							if(ignorarVisor) fallaFatal = false;
							else fallaFatal = true;
						}
						else{
							conexion.closeConnection();
						}
					}
					catch(SerialExceptions ex){
						logger.error("bootScreen()", ex);

						MensajesVentanas.mensajeError("Error con el puerto serial del visor");
						Verificador.splash.desactivar(6);
						setFallaDispositivo(true);
						if(ignorarVisor) fallaFatal = false;
						else fallaFatal = true;
					}
				} catch (NoSuchNodeException e) {
					logger.error("bootScreen()", e);
				} catch (UnidentifiedPreferenceException e) {
					logger.error("bootScreen()", e);
				}
			} catch (Exception e) {
				logger.error("bootScreen()", e);

				Verificador.splash.desactivar(6);
				setFallaDispositivo(true);
				fallaFatal = false;
			}
			try {
				
				CR.cargarDispositivos();
				Sesion.iniciarChequeoDispositivos();
			} catch (PrinterNotConnectedException ep) {
				 fallaFatal = true;
			} catch (Exception e1) {
				logger.error("bootScreen()", e1);
			}
			try {
				Verificador.splash.cambiarTexto("Iniciando Verificador de Precios...");
				Verificador.splash.activar(5);
				InitCR.cargarVerificador();
			} catch (Exception e) {
				logger.error("bootScreen()", e);
				Verificador.splash.desactivar(5);
			}

			try {
				Verificador.splash.cambiarTexto("Iniciando Sincronizador de BD...");
				Verificador.splash.activar(5);
				CR.sincronizador.iniciar();
				iniciarExtensiones();
			} catch (Exception e) {
				logger.error("bootScreen()", e);

				Verificador.splash.desactivar(5);
			}
			
			try {
				//****** IROJAS: 20/04/2010 Modificación para ejecución de script de comandos de forma automática
				MaquinaDeEstado.ejecutarScriptComandos();
				
				//****** jgraterol-gmartinelli (Copia de irojas): cambio de preferencias automatico
				MaquinaDeEstado.ejecutarCambiarPreferencias();
				//****** Fin ***
			} catch (Exception e) {
				logger.error("bootScreen()", e);
			}
			try {
				//****** AAVILA: 20/04/2010 Modificación para ejecución de sentencias SQL
				MaquinaDeEstado.ejecutarSentenciaSQL();
				//****** Fin ***
			} catch (Exception e) {
				logger.error("bootScreen()", e);
			}
	
		} catch (BaseDeDatosExcepcion e) {
			logger.error("bootScreen()", e);

			MensajesVentanas.mensajeError(e.getMensaje());
			fallaFatal = true;
		} catch (ExcepcionCr e) {
			logger.error("bootScreen()", e);

			MensajesVentanas.mensajeError(e.getMensaje());
			fallaFatal = true;
		}

		Verificador.splash.hideSplash();
		try {
			if (preferenciasCR.getConfigStringForParameter("sistema", "mantenerFoco").equalsIgnoreCase("N"))
				MensajesVentanas.detenerVerificadorFoco();
		} catch (Exception e1) {
			// Por Defecto la Preferencia es para mantener el Foco
		}
		if(fallaFatal){
			MensajesVentanas.mensajeError("Falla del Sistema. Apague e inicie nuevamente!!");
			MaquinaDeEstado.apagarSistema();
		} else
			iniciando = false;
		//new CargarAfiliados();
		if (logger.isDebugEnabled()) {
			logger.debug("bootScreen() - end");
		}
	}
	
	/**
	 * Método cargarPreferencias
	 * 
	 * @throws NodeAlreadyExistsException
	 * @throws NoSuchNodeException
	 * @throws UnidentifiedPreferenceException
	 */
	public static boolean cargarPreferencias() throws NoSuchNodeException, UnidentifiedPreferenceException{
		if (logger.isDebugEnabled()) {
			logger.debug("cargarPreferencias() - start");
		}

		preferenciasCR = null;
		prefsDispositivos = null;
		preferenciasCR = new EPAPreferenceProxy("ProyectoCR");
		prefsDispositivos = new EPAPreferenceProxy("dispositivos");
		try {
			if (!preferenciasCR.isNodeConfigured("db")) { 
				preferenciasCR.addNewNodeToPreferencesTop("db");
				preferenciasCR.setConfigStringForParameter("db", "dbEsquema", "CR");
				preferenciasCR.setConfigStringForParameter("db", "dbClaseLocal", "com.mysql.jdbc.Driver");
				preferenciasCR.setConfigStringForParameter("db", "dbClaseServidor", "com.mysql.jdbc.Driver");
				preferenciasCR.setConfigStringForParameter("db", "dbClaseServidorCentral", "com.ibm.as400.access.AS400JDBCDriver");
				preferenciasCR.setConfigStringForParameter("db", "dbUrlLocal", "jdbc:mysql://127.0.0.1/"+preferenciasCR.getConfigStringForParameter("db", "dbEsquema"));
				preferenciasCR.setConfigStringForParameter("db", "dbUrlServidor", "jdbc:mysql://192.168.1.244/"+preferenciasCR.getConfigStringForParameter("db", "dbEsquema"));
				preferenciasCR.setConfigStringForParameter("db", "dbUrlServidorCentral", "jdbc:as400://192.168.1.2/"+preferenciasCR.getConfigStringForParameter("db", "dbEsquema"));
				preferenciasCR.setConfigStringForParameter("db", "dbUsuario", "rootcr");
				preferenciasCR.setConfigStringForParameter("db", "dbClave", "root2003");
				preferenciasCR.setConfigStringForParameter("db", "garantizarFacturacion", "N");
				preferenciasCR.setConfigStringForParameter("db", "objetoRMI", "//192.168.1.126/ServicioRMI/ObjetoDevolucion");
			}
			if (!preferenciasCR.isNodeConfigured("sistema")) {
				preferenciasCR.addNewNodeToPreferencesTop("sistema");
				preferenciasCR.setConfigStringForParameter("sistema", "pathtemporal", "temp/");
				preferenciasCR.setConfigStringForParameter("sistema", "urlPrincipal", "/com/becoblohm/cr/verificador/verificadorCR.html");
				preferenciasCR.setConfigStringForParameter("sistema", "urlVerificador", "");
				preferenciasCR.setConfigStringForParameter("sistema", "verificarLinea", "S");
				preferenciasCR.setConfigStringForParameter("sistema", "impresoraFiscal", "S");
				preferenciasCR.setConfigStringForParameter("sistema", "mensajeSplash", "Sistema CR - Version ");
				preferenciasCR.setConfigStringForParameter("sistema", "mensajeColorBlanco", "S");
				preferenciasCR.setConfigStringForParameter("sistema", "logo", "logoBECO.png"); //Logo de EPA
				preferenciasCR.setConfigStringForParameter("sistema", "colorFondoSplash", "colorBECO"); //ColorEPA
				preferenciasCR.setConfigStringForParameter("sistema", "colorBECO", "59,27,100"); //Colores BECO
				preferenciasCR.setConfigStringForParameter("sistema", "colorEPA", "0,51,153"); //Colores EPA
				preferenciasCR.setConfigStringForParameter("sistema", "imagenSplash", "splashBECO.png"); //SplashEPA
				preferenciasCR.setConfigStringForParameter("sistema", "longitudlineavisor", "20");
				preferenciasCR.setConfigStringForParameter("sistema", "saltolineavisor", "");
				preferenciasCR.setConfigStringForParameter("sistema", "puertoServCentral", "8471");
				preferenciasCR.setConfigStringForParameter("sistema", "puertoServ", "3306");
				preferenciasCR.setConfigStringForParameter("sistema", "PuertoSocket", "7000");
				preferenciasCR.setConfigStringForParameter("sistema", "cambiofecha", "N");
				preferenciasCR.setConfigStringForParameter("sistema", "apagarsistema", "S");
				preferenciasCR.setConfigStringForParameter("sistema", "reiniciarsistema", "N");
				preferenciasCR.setConfigStringForParameter("sistema", "estiloFactura","VE");
				preferenciasCR.setConfigStringForParameter("sistema","localizacionLenguaje", "es");
				preferenciasCR.setConfigStringForParameter("sistema", "localizacionPais","VE");
				preferenciasCR.setConfigStringForParameter("sistema", "longitudNombreCliente", "30");
				preferenciasCR.setConfigStringForParameter("sistema", "longitudApellidoCliente", "30");
				preferenciasCR.setConfigStringForParameter("sistema", "escanerRapido", "N");
				preferenciasCR.setConfigStringForParameter("sistema", "funcionalidad", "0"); 
				preferenciasCR.setConfigStringForParameter("sistema", "puedeFacturar", "S");
				preferenciasCR.setConfigStringForParameter("sistema", "ignorarEscaner", "S");
				preferenciasCR.setConfigStringForParameter("sistema", "ignorarImpresora", "S");
				preferenciasCR.setConfigStringForParameter("sistema", "ignorarVisor", "S");
				preferenciasCR.setConfigStringForParameter("sistema", "frecuenciaSincronizador", "5");
				preferenciasCR.setConfigStringForParameter("sistema", "servicioSyncCommander", "S");
				preferenciasCR.setConfigStringForParameter("sistema", "actCajaTemporizada", "S");
				preferenciasCR.setConfigStringForParameter("sistema", "puertoSyncCommander", "2005");
				preferenciasCR.setConfigStringForParameter("sistema", "sincronizarAfiliados", "S");
				preferenciasCR.setConfigStringForParameter("sistema", "activarVerificador", "S");
				preferenciasCR.setConfigStringForParameter("sistema", "manejoGaveta", "impresora");
			}
			
			
			/* 
			 * [0] Funcionalidad Completa
			 * [1] Anular/Devolver
			 * [2] Apartados/PedidosEspeciales
			 * [3] Seguridad
			 */
			
			
			
			if (!preferenciasCR.isNodeConfigured("facturacion")) {
				preferenciasCR.addNewNodeToPreferencesTop("facturacion");
				preferenciasCR.setConfigStringForParameter("facturacion", "longitudCodigoInterno", "11");
				preferenciasCR.setConfigStringForParameter("facturacion", "permitirRebajasAempleados", "N");
				preferenciasCR.setConfigStringForParameter("facturacion", "empaquesAcumulativos", "S");
				preferenciasCR.setConfigStringForParameter("facturacion", "porcentajeMensajeEmpaque", "50");
				preferenciasCR.setConfigStringForParameter("facturacion", "imprimirFrenteCheque", "S");
				preferenciasCR.setConfigStringForParameter("facturacion", "imprimirDorsoCheque", "S");
				preferenciasCR.setConfigStringForParameter("facturacion", "nombreBancoCheque", "");
				preferenciasCR.setConfigStringForParameter("facturacion", "numeroCuentaCheque", "");
				preferenciasCR.setConfigStringForParameter("facturacion", "tipoCuentaCheque", "Corriente");
				preferenciasCR.setConfigStringForParameter("facturacion", "requiereCliente", "N");
				preferenciasCR.setConfigStringForParameter("facturacion", "autorizarCierreCajero", "N");
				preferenciasCR.setConfigStringForParameter("facturacion", "porcentajeRetencionIVA", "75");
				preferenciasCR.setConfigStringForParameter("facturacion", "montoMinimoDevolucion", "50");
				preferenciasCR.setConfigStringForParameter("facturacion", "simboloMonedaLocal", ""+'\302'+'\242');
				preferenciasCR.setConfigStringForParameter("facturacion", "cedulaObligatoria", "N");
				preferenciasCR.setConfigStringForParameter("facturacion", "numeroConfirmacionObligatorio", "S");
				preferenciasCR.setConfigStringForParameter("facturacion", "permitirContribuyentesOrdinarios", "N");
				preferenciasCR.setConfigStringForParameter("facturacion", "colorCombo","69,170,252"); //Colores BECO
				preferenciasCR.setConfigStringForParameter("facturacion", "entregaDespacho","N");
				preferenciasCR.setConfigStringForParameter("facturacion", "entregaDomicilio","S");
				preferenciasCR.setConfigStringForParameter("facturacion", "entregaClienteRetira","N");
				preferenciasCR.setConfigStringForParameter("facturacion", "validarClaveAlSalir","S");
				
				//BECO: 11-2012 Carga por defecto preferencias de PDA y transferencia Inmediata, IP del Servidor se tiene que cambiar de acuerdo a la tienda.
				preferenciasCR.setConfigStringForParameter("facturacion", "timeOutPDA","5000");
				preferenciasCR.setConfigStringForParameter("facturacion", "ipServidorPDA","192.168.1.219");
				preferenciasCR.setConfigStringForParameter("facturacion", "puertoServidorPDA","35993");
				//FIN
			
			}
			
			if (!preferenciasCR.isNodeConfigured("apartado")) {
				preferenciasCR.addNewNodeToPreferencesTop("apartado");
				preferenciasCR.setConfigStringForParameter("apartado", "NumeroTiposDeAbonos", "1");
				preferenciasCR.setConfigStringForParameter("apartado", "Abono1", "30");
				preferenciasCR.setConfigStringForParameter("apartado", "TiempoVigencia", "30");
				preferenciasCR.setConfigStringForParameter("apartado", "TipoVigencia", "Dia");
				preferenciasCR.setConfigStringForParameter("apartado", "PermiteRebajas", "N");
				preferenciasCR.setConfigStringForParameter("apartado", "RecalcularSaldo", "S");
				preferenciasCR.setConfigStringForParameter("apartado", "CargoPorServicio", "10");
				preferenciasCR.setConfigStringForParameter("apartado", "MontoMinimoApartado", "50");
				preferenciasCR.setConfigStringForParameter("apartado", "PreguntarFacturar", "N");
			}
			
			if(!preferenciasCR.isNodeConfigured("listaregalos")){
				preferenciasCR.addNewNodeToPreferencesTop("listaregalos");
				preferenciasCR.setConfigStringForParameter("listaregalos", "MaxDiasAperturaLG", "45");
				preferenciasCR.setConfigStringForParameter("listaregalos", "CostoUT", "90");
				preferenciasCR.setConfigStringForParameter("listaregalos", "MontoMinimoLG", "120");
				String iplocal = InetAddress.getLocalHost().getHostAddress();
				String ipservidor = (iplocal.substring(0,iplocal.lastIndexOf('.')+1))+"3";
				preferenciasCR.setConfigStringForParameter("listaregalos", "DbPdtUrlServidor", "jdbc:mysql://"+ipservidor+"/mcomm");
				preferenciasCR.setConfigStringForParameter("listaregalos", "DbPdtUsuario", "root");
				preferenciasCR.setConfigStringForParameter("listaregalos", "DbPdtClave", "");
			}

			if (logger.isDebugEnabled()) {
				logger.debug("cargarPreferencias() - end");
			}
			return true;
		} catch (NodeAlreadyExistsException e) {
			logger.error("cargarPreferencias()", e);

			if (logger.isDebugEnabled()) {
				logger.debug("cargarPreferencias() - end");
			}
			return false;
		} catch (UnknownHostException e) {
			logger.error("cargarPreferencias()", e);

			if (logger.isDebugEnabled()) {
				logger.debug("cargarPreferencias() - end");
			}
			return false;
		}
	}

	/**
	 * Método getPreferencias
	 * 
	 * @return LinkedHashMap
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String,Object> getPreferencias(String nodo) throws NoSuchNodeException, 
	UnidentifiedPreferenceException{
		if (logger.isDebugEnabled()) {
			logger.debug("getPreferencias(String) - start");
		}

		LinkedHashMap<String,Object> returnLinkedHashMap = preferenciasCR
				.getAllPreferecesForNode(nodo);
		if (logger.isDebugEnabled()) {
			logger.debug("getPreferencias(String) - end");
		}
		return returnLinkedHashMap;
	}
	
	/**
	 * Método cargarVerificador
	 * 
	 */
	public static void cargarVerificador(){
		if (logger.isDebugEnabled()) {
			logger.debug("cargarVerificador() - start");
		}

		try{
			if (estadoAlIniciar.equals(Sesion.BLOQUEADA))
				Sesion.getCaja().setEstado(Sesion.BLOQUEADA);
				Sesion.getCaja().setUsuarioLogueado(usuarioAlIniciar);
				Usuario u = new Usuario();
				u = u.cargarDatos(usuarioAlIniciar);
				Sesion.setUsuarioActivo(u);
		} catch (Exception e) {
			logger.error("cargarVerificador()", e);
		}
		
		//******************* CARGA DEL VERIFICADOR DE PRECIOS ****************
		//PARAMETRIZAR CON UNA PREFERENCIA SI EL VERIFICADOR ES EL DE EPA O BECO Y LLAMAR AL CONTRUCTOR RESPECTIVO
		String urlVerificador ="";
		try {
			urlVerificador = new String(InitCR.preferenciasCR.getConfigStringForParameter("sistema", "urlVerificador"));
		} catch (NoSuchNodeException e1) {
			logger.error("Verificador()", e1);
			Auditoria.registrarAuditoria("No se pudo cargar la preferencia del url del verificador", 'E');
		} catch (UnidentifiedPreferenceException e1) {
			logger.error("Verificador()", e1);
			Auditoria.registrarAuditoria("Error desconocido en carga de preferencia del url del verificador", 'E');
		}

		String[] params = new String[5];
		params[0] = "800";
		params[1] = "600";
		params[2] = urlVerificador;
		params[3] = "3000";
		params[4] = "root";
		(new PDAFactory()).getPDAInstance().iniciarVerificador(params); 

		if (logger.isDebugEnabled()) {
			logger.debug("cargarVerificador() - end");
		}
	}


	
	/**
	 * Method fallaInicio.
	 */
	public static void fallaInicio() {
		if (logger.isDebugEnabled()) {
			logger.debug("fallaInicio() - start");
		}

		Verificador.splash.hideSplash();
		MensajesVentanas.esperar(7000);
		bootScreen();

		if (logger.isDebugEnabled()) {
			logger.debug("fallaInicio() - end");
		}
	}

	public void run() {
		if (logger.isDebugEnabled()) {
			logger.debug("run() - start");
		}

		bootScreen();

		if (logger.isDebugEnabled()) {
			logger.debug("run() - end");
		}
	}
		
	/**
	 * Método isFallaDispositivo
	 * 
	 * @return boolean
	 */
	public static boolean isFallaDispositivo() {
		if (logger.isDebugEnabled()) {
			logger.debug("isFallaDispositivo() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isFallaDispositivo() - end");
		}
		return fallaDispositivo;
	}
	/**
	 * Método setFallaDispositivo
	 * 
	 * @param b
	 */
	public static void setFallaDispositivo(boolean b) {
		if (logger.isDebugEnabled()) {
			logger.debug("setFallaDispositivo(boolean) - start");
		}

		fallaDispositivo = b;

		if (logger.isDebugEnabled()) {
			logger.debug("setFallaDispositivo(boolean) - end");
		}
	}
	
	public static void iniciarExtensiones(){
		if (logger.isDebugEnabled()) {
			logger.debug("iniciarExtensiones() - start");
		}

		MediadorBD.iniciarBuscador();

		if (logger.isDebugEnabled()) {
			logger.debug("iniciarExtensiones() - end");
		}
	}
	
	public static String getPreferencia(String nodo, String key, String defaultValue) {
		String result = null;
		try {
			result = preferenciasCR.getConfigStringForParameter(nodo, key);
		} catch (NoSuchNodeException e) {
			logger.error("getPreferencia(String, String, String)", e);
		} catch (UnidentifiedPreferenceException e) {
			logger.error("getPreferencia(String, String, String)", e);
		} finally {
			if (result == null) {
				result = defaultValue; 
			}
		}
		return result;
	}
	
}