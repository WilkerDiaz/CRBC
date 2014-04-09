/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr
 * Programa   : CR.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 27/11/2003 04:11:59 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.2
 * Fecha       : 26/05/2004 03:50 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Modificada lógica de inicio de hilos del sistema.
 * =============================================================================
 * Versión     : 1.1.1
 * Fecha       : 25/05/2004 01:27:34 PM
 * Analista    : GMARTINELLI
 * Descripción : Creación de los atributos meVenta y meServ.
 * =============================================================================
 * Versión     : 1.1
 * Fecha       : 04/03/2004 01:30 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Reorganizados los trhows para todos los métodos de modo que no sólo 
 * 				 arroje una ExcepcionCr sino cada una de las excepciones específicas.
 * =============================================================================
 */
package com.becoblohm.cr;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.JTextArea;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstado;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstadoServicio;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstadoVenta;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.sincronizador.Sincronizador;
import com.epa.crprinterdriver.PrinterNotConnectedException;
import com.epa.crserialinterface.Connection;
import com.epa.crserialinterface.Parameters;
import com.epa.crvisordriver.CRVisorOperations;
import com.epa.preferencesproxy.EPAPreferenceProxy;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;

/** 
 * Descripción: 
 * 
 * 
 */
public class CR {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CR.class);

	public static MaquinaDeEstado me;
	public static MaquinaDeEstadoVenta meVenta;
	public static MaquinaDeEstadoServicio meServ;
	public static Sincronizador sincronizador;
	
	public static JTextArea inputEscaner;
	public static JTextArea outputEscaner;
	private static Parameters parametrosEscaner;
	private static Connection conexionEscaner;
	private static EPAPreferenceProxy preferencesproxy;
	public static CRVisorOperations crVisor;
	public static String version;
	public static Date buildtime;
	
	/**
	 * Constructor for CR.
	 */
	public CR() throws UsuarioExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		me = new MaquinaDeEstado();
		meVenta = new MaquinaDeEstadoVenta();
		meServ = new MaquinaDeEstadoServicio();
		int frecuenciaSincronizador = 0;
		try {
			frecuenciaSincronizador =
				new Integer(
					InitCR.preferenciasCR.getConfigStringForParameter(
						"sistema",
						"frecuenciaSincronizador"))
					.intValue();
		} catch (Exception e) {
			logger.error("CR()", e);
		}
		sincronizador = new Sincronizador(frecuenciaSincronizador);
	}

	/**
	 * Método main.
	 * @param args
	 */
	public static void main(String args[]) {
		if (logger.isDebugEnabled()) {
			logger.debug("main(String) - start");
		}
		Properties propVersion = new Properties();
		try {
			propVersion.load(CR.class.getResourceAsStream("/com/becoblohm/cr/CRVersion.properties"));
		} catch (IOException e) {
			logger.error("main(String) - No se encontro archivo de versión", e);
		} finally {
			version = propVersion.getProperty("cr.version", "1.0.0");
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			try {
				buildtime = df.parse(propVersion.getProperty("cr.buildtime", "20050101"));
			} catch (ParseException e1) {
				logger.error("main(String)", e1);
				Calendar c = Calendar.getInstance();
				c.set(2005, 0, 1);
				buildtime = c.getTime();
			}
			//Usa tema de J2SDK 1.4 sobre J2SDK 1.5
			MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
		}
		InitCR iniciaPOS = new InitCR();
		try {
			iniciaPOS.start();
			if (logger.isDebugEnabled()) {
				logger.debug("main(String) - Iniciados hilos del sistema...");
			}
		} catch(ThreadDeath ex){
			if (logger.isDebugEnabled()) {
				logger.debug("main(String) - POS Muerto!!!");
			}
		} 

		if (logger.isDebugEnabled()) {
			logger.debug("main(String) - end");
		}
	}

	/**
	 * Método cargarVisor
	 * 
	 * 
	 */
	public static void cargarVisor() {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarVisor() - start");
		}

		// Cargamos el visor
		String portName, baudRate, fControlIn, fControlOut, dataBits, stopBits, parity, saltoLinea;
		int longitudLineaVisor;
		preferencesproxy = InitCR.prefsDispositivos;
		EPAPreferenceProxy prefCR = InitCR.preferenciasCR;
		try {
			portName = preferencesproxy.getConfigStringForParameter("visor","Puerto Serial");
			baudRate = preferencesproxy.getConfigStringForParameter("visor", "Baud Rate");
			fControlIn = preferencesproxy.getConfigStringForParameter("visor","Flow Control In");
			fControlOut = preferencesproxy.getConfigStringForParameter("visor", "Flow Control Out");
			dataBits = preferencesproxy.getConfigStringForParameter("visor", "Data Bits");
			stopBits = preferencesproxy.getConfigStringForParameter("visor", "Stop Bits");
			parity = preferencesproxy.getConfigStringForParameter("visor", "Parity");
			longitudLineaVisor = Integer.valueOf(prefCR.getConfigStringForParameter("sistema", "longitudlineavisor")).intValue();
			saltoLinea = prefCR.getConfigStringForParameter("sistema", "saltolineavisor");
			boolean compartirPuertoGaveta = true;
			try {
				// Tratamos de Obtener la preferencia de compartir puerto con Gaveta del Visor
				// La colocamos en TryCatch para que EPA siga funcionando
				compartirPuertoGaveta = preferencesproxy.getConfigStringForParameter("visor", "compartirPuertoVisorGaveta")=="N" ? false : true;
			} catch (Exception e){
				compartirPuertoGaveta = true;
			}
			if (compartirPuertoGaveta) {
				crVisor = new CRVisorOperations (portName, baudRate, fControlIn, fControlOut, dataBits, stopBits, parity, longitudLineaVisor, saltoLinea);
			} else {
				String portNameGaveta = preferencesproxy.getConfigStringForParameter("gaveta","Puerto Serial");
				String baudRateGaveta = preferencesproxy.getConfigStringForParameter("gaveta", "Baud Rate");
				String fControlInGaveta = preferencesproxy.getConfigStringForParameter("gaveta","Flow Control In");
				String fControlOutGaveta = preferencesproxy.getConfigStringForParameter("gaveta", "Flow Control Out");
				String dataBitsGaveta = preferencesproxy.getConfigStringForParameter("gaveta", "Data Bits");
				String stopBitsGaveta = preferencesproxy.getConfigStringForParameter("gaveta", "Stop Bits");
				String parityGaveta = preferencesproxy.getConfigStringForParameter("gaveta", "Parity");
				crVisor = new CRVisorOperations (portName, baudRate, fControlIn, fControlOut, dataBits, stopBits, parity, longitudLineaVisor, saltoLinea,
												portNameGaveta, baudRateGaveta, fControlInGaveta, fControlOutGaveta, dataBitsGaveta, stopBitsGaveta, parityGaveta);
			}
		} catch (NoSuchNodeException e) {
			logger.error("cargarVisor()", e);
			crVisor = null;
		} catch (UnidentifiedPreferenceException e) {
			logger.error("cargarVisor()", e);
			crVisor = null;
		}
		try {
			if(crVisor != null)
				crVisor.abrirPuerto();
			crVisor.incializarVisor();
			crVisor.apagarCursor();
			crVisor.limpiarVisor();
		} catch (com.epa.crserialinterface.SerialExceptions e) {
			logger.error("cargarVisor()", e);
			crVisor = null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cargarVisor() - end");
		}
	}

	/**
	 * Método cargarDispositivos
	 * 
	 * @throws PrinterNotConnectedException
	 */
	public static void cargarDispositivos() throws PrinterNotConnectedException {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarDispositivos() - start");
		}

		// Cargamos el escaner
		if (inputEscaner == null)
			abrirConexionEscaner();
		beep();

		// Cargamos el visor
		if (crVisor == null)
			cargarVisor();
			
		if (Sesion.impresoraFiscal)
			Sesion.crFiscalPrinterOperations.abrirPuertoImpresora();
		else
			Sesion.crPrinterOperations.abrirPuertoImpresora();
		
		// Obtenemos el serial de la impresora
		CR.me.cargarSerialFiscalCaja();

		if (logger.isDebugEnabled()) {
			logger.debug("cargarDispositivos() - end");
		}
	}

	/**
	 * Método cerrarConexiones
	 * 
	 * 
	 */
	public static void cerrarConexiones() {
		if (logger.isDebugEnabled()) {
			logger.debug("cerrarConexiones() - start");
		}

		try {
			conexionEscaner.closeConnection();
			conexionEscaner = null;
			crVisor.limpiarVisor();
			crVisor.cerrarConexion();
		} catch (Exception e) {
			logger.error("cerrarConexiones()", e);
		}

		if (Sesion.impresoraFiscal)
			Sesion.crFiscalPrinterOperations.cerrarPuertoImpresora();
		else
			Sesion.crPrinterOperations.cerrarPuertoImpresora();

		if (logger.isDebugEnabled()) {
			logger.debug("cerrarConexiones() - end");
		}
	}

	/**
	 * Método abrirConexionEscaner
	 * 
	 * 
	 */
	public static void abrirConexionEscaner() {
		if (logger.isDebugEnabled()) {
			logger.debug("abrirConexionEscaner() - start");
		}

		// Cargamos el escaner
		inputEscaner = new JTextArea();
		outputEscaner = new JTextArea();
		parametrosEscaner = new Parameters();
		conexionEscaner = new Connection(parametrosEscaner, outputEscaner, inputEscaner);
		preferencesproxy = InitCR.prefsDispositivos;
		try {
			parametrosEscaner.setPortName(preferencesproxy.getConfigStringForParameter("scanner","Puerto Serial"));
			parametrosEscaner.setBaudRate(preferencesproxy.getConfigStringForParameter("scanner", "Baud Rate"));
			parametrosEscaner.setDatabits(preferencesproxy.getConfigStringForParameter("scanner","Data Bits"));
			parametrosEscaner.setFlowControlIn(preferencesproxy.getConfigStringForParameter("scanner", "Flow Control In"));
			parametrosEscaner.setFlowControlOut(preferencesproxy.getConfigStringForParameter("scanner", "Flow Control Out"));
			parametrosEscaner.setParity(preferencesproxy.getConfigStringForParameter("scanner", "Parity"));
			parametrosEscaner.setStopbits(preferencesproxy.getConfigStringForParameter("scanner", "Stop Bits"));
		} catch (NoSuchNodeException e) {
			logger.error("abrirConexionEscaner()", e);
		} catch (UnidentifiedPreferenceException e) {
			logger.error("abrirConexionEscaner()", e);
		}
		try {
			conexionEscaner.openConnection();
		} catch (com.epa.crserialinterface.SerialExceptions e) {
			logger.error("abrirConexionEscaner()", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("abrirConexionEscaner() - end");
		}
	}

	/**
	 * Método cerrarConexionEscaner
	 * 
	 * 
	 */
	public static void cerrarConexionEscaner() {
		if (logger.isDebugEnabled()) {
			logger.debug("cerrarConexionEscaner() - start");
		}

		try {
			conexionEscaner.closeConnection();
		} catch (Exception e) {}
		
		conexionEscaner = null;

		if (logger.isDebugEnabled()) {
			logger.debug("cerrarConexionEscaner() - end");
		}
	}

	/**
	 * @return
	 */
	public static Connection getConexionEscaner() {
		if (logger.isDebugEnabled()) {
			logger.debug("getConexionEscaner() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getConexionEscaner() - end");
		}
		return conexionEscaner;
	}
	
	public static void beep() {
		if (logger.isDebugEnabled()) {
			logger.debug("beep() - start");
		}

		try {
			if (conexionEscaner != null){
				char[] c = {'\u0007'};
				String cad = new String(c);
				for (int i = 0; i < 3; i++) {
					outputEscaner.setText(cad);
					conexionEscaner.sendOutputBuffer(); 
				}
			}
		} catch (Exception e1) {
			logger.error("beep()", e1);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("beep() - end");
		}
	}
}