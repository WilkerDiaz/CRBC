/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.sincronizador
 * Programa   : Sincronizador.java
 * Creado por : Programador3 - Alexis Guédez López
 * Creado en  : 22/03/2004 01:29:13 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 2.0
 * Fecha       : 22/01/2005 02:35:13 PM
 * Analista    : Programador8 - Arístides Castillo
 * Descripción : Nueva versión del sincronizador, donde se agrega el esquema de
 *  			 de inicio de sincronización en respuesta a un mensaje enviado
 * 				 sea por la red como por comando desde el sistema operativo de
 * 				 la caja.
 * =============================================================================
 * Versión     : 1.2
 * Fecha       : 11/11/2004 04:05:13 PM
 * Analista    : Programador8 - Arístides Castillo
 * Descripción : Adaptado el sincronizador para que la tarea de Verificar Procesos
 * 				 pueda llevarse a cabo sólo cuando la caja se encuentra fuera de
 * 				 línea. 
 * =============================================================================
 *  * Versión     : 1.1.1
 * Fecha       : 26/05/2004 02:29:13 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Agregada la tarea "Verificar Proceso" para probar la verificación del 
 * 				estado de conexión de la caja. 
 * =============================================================================
 * Versión     : 1.1
 * Fecha       : 22/03/2004 01:29:13 PM
 * Analista    : Programador3 - Alexis Guédez López
 * Descripción : Implementación inicial.
 * =============================================================================
 */
package com.becoblohm.cr.sincronizador;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.GregorianCalendar;
import java.util.Timer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.MandatoErradoExcepcion;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.mediadoresbd.BeansSincronizador;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;

/**
 * Descripción:
 * 	Esta clase planifica las tareas de actualización de las BD en ambos sentidos
 * Servidor -> CR y CR -> Servidor el cual se repite durante el tiempo de vida de 
 * la aplicación de la CR en el intervalo de tiempo indicado.
 */

public class Sincronizador {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Sincronizador.class);
	static {
		logger.setAdditivity(false);
	}	

	private Timer planificador = null;
	protected Timer planVerif = null;
	private SincronizarServidorCaja sincronizarSrvCR = null;
	private SincronizarCajaServidor sincronizarCRSrv = null;
	private VerificarProcesos verificarProcesos = null;
	
	private StringWriter strwrtLOG = null;
	PrintWriter prtwrtLOG = null;
	int intervalo = 1 * 60; //Minutos en segundos
	private boolean iniciadoSincronizador = false;
	private boolean iniciadoVerifLinea = false;
	private boolean syncCodExt = true;
	private boolean syncAfiliados = true;
	private boolean actCajaTemporizada = true;
	private int puertoEscucha;
	public static final int defaultPort = 2005;
	private HiloEscucha hiloEscucha;
	
	/**
	 * Constructor para Sincronizador.java
	 *
	 * @param repetir - Tiempo en minutos en que se repite el ciclo de sincronización.
	 */
	public Sincronizador(int frecuencia){
		this();
		if (frecuencia > 0) intervalo = frecuencia * 60;
		strwrtLOG = new StringWriter();
		prtwrtLOG = new PrintWriter(strwrtLOG);
		initSyncCodExt();
		initSyncAfiliados();
		initActCajaTemp();
		sincronizarSrvCR = new SincronizarServidorCaja(this);
		sincronizarCRSrv = new SincronizarCajaServidor(this);
		planificador = new Timer(false);
	}
	/**
	 * Constructor para iniciar el sincronizador en modo de atención de sockets 
	 * @since 22-ene-2005
	 *
	 */
	public Sincronizador() {
		try {
			if (InitCR.preferenciasCR.getConfigStringForParameter("sistema", "servicioSyncCommander").equals("S"))
			{
				establecerPuertoEscucha();
				try {
					hiloEscucha = new HiloEscucha(this);
					hiloEscucha.start();
				} catch (IOException e) {
					logger.error("Sincronizador()", e);
				}
			}
		} catch (NoSuchNodeException e) {
			logger.error("Sincronizador()", e);
		} catch (UnidentifiedPreferenceException e) {
			logger.error("Sincronizador()", e);
		}
	}
	
	/**
	 * Método iniciar
	 * 
	 */
	
	private void initSyncCodExt() {
		try {
			if (InitCR.preferenciasCR.getConfigStringForParameter("db", "garantizarFacturacion").equals("S"))
			{
				syncCodExt = false; 
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger
						.debug("initSyncCodExt() - No se consiguio la preferencia de Garantia de Facturación");
			}
		}
	}

	private void initSyncAfiliados() {
		try {
			if (InitCR.preferenciasCR.getConfigStringForParameter("sistema", "sincronizarAfiliados").equals("N"))
			{
				syncAfiliados = false; 
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger
						.debug("initSyncCodExt() - No se consiguio la preferencia de Garantia de Facturación");
			}
		}
	}
	
	private void initActCajaTemp() {
		actCajaTemporizada = InitCR.getPreferencia("sistema", "actCajaTemporizada", "S").equals("S");
	}

	public void iniciar(){
		iniciadoSincronizador = true;
		GregorianCalendar hoy = new GregorianCalendar();
		
		// Inicia sincronización de los registros de BD
		this.prtwrtLOG.println("Sincronizador de Servidor -> CR inicia:\t" + hoy.getTime());
		planificador.scheduleAtFixedRate(sincronizarSrvCR, 10000, intervalo * 1000);

		hoy.set(GregorianCalendar.MINUTE, GregorianCalendar.MINUTE + 2);
		this.prtwrtLOG.println("Sincronizador de CR -> Servidor inicia:\t" + hoy.getTime());
		planificador.scheduleAtFixedRate(sincronizarCRSrv, 50000, intervalo * 1000);

		if (!Sesion.isCajaEnLinea()) {
			iniciarVerificarLinea();
		}
	}
	
	public void iniciarVerificarLinea() {
		if (iniciadoSincronizador && !iniciadoVerifLinea) {
			iniciadoVerifLinea = true;
			verificarProcesos = new VerificarProcesos(this);
			planVerif = new Timer();
			planVerif.scheduleAtFixedRate(verificarProcesos, 0, 30000);
		}
	}
	
	public void cancelarVerifLinea() {
		planVerif.cancel();
		verificarProcesos = null;
		planVerif = null;
		iniciadoVerifLinea = false;
	}

	/**
	 * Método detenerTareas
	 * 
	 * @throws InterruptedException
	 */
	public void detenerSync() throws InterruptedException {
		Sesion.finalizarSync = true;
		sincronizarCRSrv.cancel();
		sincronizarSrvCR.cancel();
	}


	/**
	 * Método getContenidoLOG
	 * 
	 * @return String
	 */
	public String getContenidoLOG() {
		String strLOG = strwrtLOG.toString();
		return strLOG;
	}

	/**
	 * Método setLOGEmpty
	 * 
	 */
	public void setLOGEmpty() {
		strwrtLOG = new StringWriter();
		prtwrtLOG = new PrintWriter(strwrtLOG);
	}

	/**
	 * Método getEstadoMemoria
	 * 
	 */
	public void getEstadoMemoria() {
		prtwrtLOG.println(
			"Estado de la Memoria: "
				+ Runtime.getRuntime().freeMemory() / 1024
				+ "/"
				+ Runtime.getRuntime().totalMemory() / 1024
				+ " KBs: "
				+ (Runtime.getRuntime().freeMemory()
					* 100
					/ Runtime.getRuntime().totalMemory())
				+ "%");
	}

	public boolean isSyncCodExt() {
		return syncCodExt;
	}
	
	
	
	public void syncCajaServ(){
		// Sincronizacion mínima para asegurar la total actualizacion
		// de datos en el servidor
		try {
			SyncCnxMonitor.getInstance().notifyIn();
			SincUtil.getInstance().syncTransacciones();
			SincUtil.getInstance().syncAbonos();
			BeansSincronizador.syncEntidadSrv("auditoria");
		} catch (Exception e) {
			logger.error("syncCajaServ()", e);
		} finally {
			SyncCnxMonitor.getInstance().notifyOut();
		}
	}
	
	private void establecerPuertoEscucha() {
		puertoEscucha = getConfigPtoEscucha();
	}
	
	public static int getConfigPtoEscucha() {
		String pto = null;
		int result = defaultPort;
		try {
			pto = InitCR.preferenciasCR.getConfigStringForParameter("sistema", "puertoSyncCommander");
		} catch (NoSuchNodeException e) {
			logger.error("getConfigPtoEscucha()", e);
		} catch (UnidentifiedPreferenceException e) {
			logger.error("getConfigPtoEscucha()", e);
		}
		if (pto != null) 
			try {
				result = Integer.parseInt(pto);
			} catch (NumberFormatException e) {
			} 
		return result;
	}
	
	public void servicioSync(String servicio, String param) {
		if (logger.isInfoEnabled()) {
			logger.info("servicioSync(String, String) - Ejecutando servicio: " + servicio + "("+ param+ ")");
		}

		if (servicio.equals("syncVentas")) {
			HiloSyncTransacciones.runAndWait();
			if (logger.isInfoEnabled()) {
				logger.info("servicioSync(String, String) - Finalizado servicio syncVentas");
			}
			return;
		} else if (servicio.equals("actCaja")) {
			try {
				sincronizarSrvCR.syncCaja(param);
			} catch (BaseDeDatosExcepcion e) {
				logger.error("servicioSync(String, String)", e);
			}

			if (logger.isInfoEnabled()) {
				logger.info("servicioSync(String, String) - Finalizado servicio actCaja:" + param);
			}
			return;
		} else if (servicio.equals("syncAfiliado")) { 
			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("servicioSync(String, String) - end");
		}
	}
	

	public int getPuertoEscucha() {
		return puertoEscucha;
	}
	public boolean isSyncAfiliados() {
		return syncAfiliados;
	}
	public boolean isActCajaTemporizada() {
		return actCajaTemporizada;
	}
}

class HiloEscucha extends Thread {
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(HiloEscucha.class);
	private ServerSocket svrSocket;
	private Sincronizador parent;
	private boolean continuar = true;
	
	public HiloEscucha (Sincronizador p) throws IOException {
		super();
		svrSocket = new ServerSocket(p.getPuertoEscucha(), 3);
		parent = p;
	}
	
	public void run() {
		while (continuar) {
			try {
				Socket cliente = svrSocket.accept();
				HiloAtencion h = new HiloAtencion(parent, cliente);
				h.start();
			} catch (IOException e) {
				logger.error("run()", e);
			}
			
		}
	}

	public void detener() {
		try {
			continuar = false;
			svrSocket.close();
		} catch (IOException e) {
			logger.error("detener()", e);
		}
	}
}

class HiloAtencion extends Thread {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(HiloAtencion.class);

	private Socket cliente;
	
	public Sincronizador parent;

	public HiloAtencion(Sincronizador p, Socket c) {
		super();
		parent = p;
		cliente = c;
	}

	public void run() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(cliente.getInputStream());
			parseCommand(doc);
		} catch (ParserConfigurationException e) {
			logger.error("HiloAtencion - run()", e);
		} catch (SAXException e) {
			logger.error("HiloAtencion - run()", e);
		} catch (IOException e) {
			logger.error("HiloAtencion - run()", e);
		} catch (MandatoErradoExcepcion e) {
			logger.error("HiloAtencion - run()", e);
		}
	
	}
	
	public void parseCommand(Document doc) throws MandatoErradoExcepcion {
		NodeList list = doc.getDocumentElement().
				getElementsByTagName("mandatoCR");
		for (int i = 0; i < list.getLength(); i++) {
			Element mandato = (Element)list.item(i);
			String servicio = mandato.getAttribute("servicio");
			String param = mandato.getAttribute("param");
			if (servicio == null || param == null) {
				throw new MandatoErradoExcepcion("Mandato CR # " + (i + 1) 
						+ " mal formado");
			}
			parent.servicioSync(servicio, param);
		}
	}
	
}

