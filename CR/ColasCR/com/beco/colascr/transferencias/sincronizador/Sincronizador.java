/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.colscr.transferencias.sincronizador
 * Programa   : Sincronizador.java
 * Creado por : Ileana Rojas, Gabriel Martinelli
 * Creado en  : 21/02/2005 03:24:59 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 
 * Fecha       : 
 * Analista    : 
 * Descripción : 
 * =============================================================================
 */
package com.beco.colascr.transferencias.sincronizador;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;

import com.beco.colascr.transferencias.ServidorCR;
import com.beco.colascr.transferencias.configuracion.PoliticaTarea;

/**
 * Descripción:
 * 	Esta clase planifica las tareas de actualización de las BD en ambos sentidos
 * ServidorTda -> ServidorCentral y servidorCentral -> ServidorTda el cual se repite durante un 
 * intervalo de tiempo indicado.
 */

public class Sincronizador {

	public static final int BASES = 1;
	public static final int AFILIADOS = 2;
	public static final int PRODUCTOS = 3;
	public static final int VENTAS = 4;
	public static final int LISTAREGALOS = 5;
	public static final int AFILIADOSTEMP = 6;

	private Timer planificador = null;
	//protected Timer planVerif = null;
	private SincCentralTdaBases sincServCentServTdaBases = null;
	private SincCentralTdaProds sincServCentServTdaProds = null;
	private SincAfiliados sincAfiliados = null;
	private SincAfiliadosTemporales sincAfiliadosTemporales = null;
	private SincTdaVentas sincServTdaServCent = null;
	private SincListaRegalos sincListaRegalos = null;
	
	private PoliticaTarea intervaloSincVentas = null;
	private PoliticaTarea intervaloSincProductos = null;
	private PoliticaTarea intervaloSincAfiliados = null;
	private PoliticaTarea intervaloSincAfiliadosTemporales = null;
	private PoliticaTarea intervaloSincBases = null;
	private PoliticaTarea intervaloSincListaRegalos = null;
	
	private boolean iniciadoSincronizador = false;
	private boolean iniciadoVerifLinea = false;
	private boolean syncCodExt = true;
	private boolean syncAfiliados = true;
	//private boolean actCajaTemporizada = true;
	private int puertoEscucha;
	public static final int defaultPort = 2005;
	//private HiloEscucha hiloEscucha;
	
	/**
	 * Constructor para Sincronizador.java
	 *
	 * @param frecuencia - Tiempo en minutos en que se repite el ciclo de sincronización.
	 */
	public Sincronizador(PoliticaTarea frecuenciaBases, PoliticaTarea frecuenciaProds, PoliticaTarea frecuenciaVta, PoliticaTarea frecuenciaAfiliados, PoliticaTarea frecuenciaListaRegalos, PoliticaTarea frecuenciaAfiliadosTemporales){
		this();
		intervaloSincBases = frecuenciaBases;
		intervaloSincProductos = frecuenciaProds;
		intervaloSincVentas = frecuenciaVta;
		intervaloSincAfiliados = frecuenciaAfiliados;
		intervaloSincAfiliadosTemporales = frecuenciaAfiliadosTemporales;
		intervaloSincListaRegalos = frecuenciaListaRegalos;
		sincServCentServTdaBases = new SincCentralTdaBases(this);
		sincServCentServTdaProds = new SincCentralTdaProds(this);
		sincAfiliados = new SincAfiliados(this);
		sincAfiliadosTemporales = new SincAfiliadosTemporales(this);
		sincListaRegalos = new SincListaRegalos(this);
		sincServTdaServCent = new SincTdaVentas(this);
		planificador = new Timer(false);
	}
	/**
	 * Constructor para iniciar el sincronizador en modo de atención de sockets 
	 * @since 22-ene-2005
	 *
	 */
	public Sincronizador() {
	/*	try {
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
		}*/
	}
	
	/**
	 * Método iniciar
	 * 
	 */
	
/*	private void initSyncCodExt() {
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
		try {
			actCajaTemporizada = InitCR.preferenciasCR.getConfigStringForParameter("sistema", "actCajaTemporizada").equals("S");
		} catch (NoSuchNodeException e) {
			logger.error("iniciar()", e);
		} catch (UnidentifiedPreferenceException e) {
			logger.error("iniciar()", e);
		}
	}*/

	public void iniciar(int tarea){
		switch (tarea) {
			case Sincronizador.BASES: ServidorCR.sincronizador.intervaloSincBases.setProximaSincronizacion(new Date());
										  this.actualizarMensajesDetalles();
										  sincServCentServTdaBases = new SincCentralTdaBases(this);
										  planificador.schedule(sincServCentServTdaBases, 1000);
										  break;
			case Sincronizador.AFILIADOS: ServidorCR.sincronizador.intervaloSincAfiliados.setProximaSincronizacion(new Date());
										  this.actualizarMensajesDetalles();
										  sincAfiliados = new SincAfiliados(this);
										  planificador.schedule(sincAfiliados, 1000);
										  break;
			case Sincronizador.AFILIADOSTEMP: ServidorCR.sincronizador.intervaloSincAfiliadosTemporales.setProximaSincronizacion(new Date());
			  							  this.actualizarMensajesDetalles();
			  							  sincAfiliadosTemporales = new SincAfiliadosTemporales(this);
										  planificador.schedule(sincAfiliadosTemporales, 1000);
										  break;
			case Sincronizador.PRODUCTOS: ServidorCR.sincronizador.intervaloSincProductos.setProximaSincronizacion(new Date());
										  this.actualizarMensajesDetalles();
										  sincServCentServTdaProds = new SincCentralTdaProds(this);
										  planificador.schedule(sincServCentServTdaProds, 1000);
										  break;
			case Sincronizador.VENTAS: ServidorCR.sincronizador.intervaloSincVentas.setProximaSincronizacion(new Date());
										  this.actualizarMensajesDetalles();
										  sincServTdaServCent = new SincTdaVentas(this);
										  planificador.schedule(sincServTdaServCent, 1000);
										  break;
			case Sincronizador.LISTAREGALOS: ServidorCR.sincronizador.intervaloSincListaRegalos.setProximaSincronizacion(new Date());
										  this.actualizarMensajesDetalles();
										  sincListaRegalos = new SincListaRegalos(this);
										  planificador.schedule(sincListaRegalos, 1000);
										  break;
		}
		this.actualizarMensajesDetalles();
	}

	public void iniciar(){
		iniciadoSincronizador = true;
		GregorianCalendar hoy = new GregorianCalendar();
		planificador.cancel();
		planificador = new Timer(false);
		
		System.out.println("Sincronizador de ServidorCentral -> ServidorTda (Bases) inicia:\t" + hoy.getTime());
		intervaloSincBases.setProximaSincronizacion(new Date());
		//planificador.schedule(sincServCentServTdaBases, configurarTareaProgramada(intervaloSincBases));
		planificador.schedule(sincServCentServTdaBases, 1000);

		System.out.println("Sincronizador de ServidorCentral -> ServidorTda (Prods.) inicia:\t" + hoy.getTime());
		intervaloSincProductos.setProximaSincronizacion(new Date());
		//planificador.schedule(sincServCentServTdaProds, configurarTareaProgramada(intervaloSincProductos));
		planificador.schedule(sincServCentServTdaProds, 1000);

		System.out.println("Sincronizador de Afiliados. inicia:\t" + hoy.getTime());
		intervaloSincAfiliados.setProximaSincronizacion(new Date());
		//planificador.schedule(sincAfiliados, configurarTareaProgramada(intervaloSincAfiliados));
		planificador.schedule(sincAfiliados, 1000);
		
		System.out.println("Sincronizador de Afiliados Temporales. inicia:\t" + hoy.getTime());
		intervaloSincAfiliadosTemporales.setProximaSincronizacion(new Date());
		//planificador.schedule(sincAfiliados, configurarTareaProgramada(intervaloSincAfiliados));
		planificador.schedule(sincAfiliadosTemporales, 1000);

		System.out.println("Sincronizador de ServidorTda -> ServidorCentral (Ventas) inicia:\t" + hoy.getTime());
		intervaloSincVentas.setProximaSincronizacion(new Date());
		//planificador.schedule(sincServTdaServCent, configurarTareaProgramada(intervaloSincVentas));
		planificador.schedule(sincServTdaServCent, 1000);
		
		System.out.println("Sincronizador de Lista de Regalos. inicia:\t" + hoy.getTime());
		intervaloSincListaRegalos.setProximaSincronizacion(new Date());
		//planificador.schedule(sincListaRegalos, configurarTareaProgramada(intervaloSincListaRegalos));
		planificador.schedule(sincListaRegalos, 1000);
		
		this.actualizarMensajesDetalles();
	}

	public Date configurarTareaProgramada(PoliticaTarea tarea) {
		Date hora = null;
		Calendar fechaServicio = Calendar.getInstance();
		fechaServicio.set(Calendar.SECOND, 0);
		Calendar fechaTarea = Calendar.getInstance();
		fechaTarea.set(Calendar.SECOND, 0);
		switch (tarea.getTipoTarea()) {
			case PoliticaTarea.AL_INICIAR:
				hora = fechaServicio.getTime();
				break;
			case PoliticaTarea.DIARIO:
				fechaTarea.set(fechaTarea.get(Calendar.YEAR), fechaTarea.get(Calendar.MONTH),
							  fechaTarea.get(Calendar.DATE), (tarea.getValorEjecucionTarea()/100),
							  (tarea.getValorEjecucionTarea()%100), 0);
				if (fechaTarea.before(fechaServicio)||fechaTarea.equals(fechaServicio)) { fechaTarea.add(Calendar.DATE, 1); fechaServicio=fechaTarea; }
				else fechaServicio=fechaTarea;
				hora = fechaServicio.getTime();
//				MensajesVentanas.mensajeUsuario("Configurada Tarea " + tarea.getNombreTarea() + ". Hora: " + hora);
				System.out.println("Configurada Tarea " + tarea.getNombreTarea() + ". Hora: " + hora);
				break;
			case PoliticaTarea.POR_TIEMPO:
				fechaServicio.add(Calendar.MINUTE, tarea.getValorEjecucionTarea());
				hora = fechaServicio.getTime();
//				MensajesVentanas.mensajeUsuario("Configurada Tarea " + tarea.getNombreTarea() + ". Hora: " + hora);
				break;
		}
		tarea.setUltimaSincronizacion(tarea.getProximaSincronizacion());
		tarea.setProximaSincronizacion(hora);
		tarea.setEstadoTarea(PoliticaTarea.FINALIZADA);
		return hora;
	}
	
	public void actualizarMensajesDetalles() {
		ServidorCR.getPantInicial().getDetalles().llenarTabla();
	}

	/**
	 * Método detenerTareas
	 * 
	 * @throws InterruptedException
	 */
	public void detenerSync() throws InterruptedException {
		sincServTdaServCent.cancel();
		sincServCentServTdaBases.cancel();
		sincServCentServTdaProds.cancel();
		sincAfiliados.cancel();
		sincAfiliadosTemporales.cancel();
		sincListaRegalos.cancel();
		planificador.cancel();
	}


	/**
	 * Método getContenidoLOG
	 * 
	 * @return String
	 */
/*	public String getContenidoLOG() {
		String strLOG = strwrtLOG.toString();
		return strLOG;
	}

	/**
	 * Método setLOGEmpty
	 * 
	 */
/*	public void setLOGEmpty() {
		strwrtLOG = new StringWriter();
		prtwrtLOG = new PrintWriter(strwrtLOG);
	}

	/**
	 * Método getEstadoMemoria
	 * 
	 */
/*	public void getEstadoMemoria() {
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
	}*/
/*	private void establecerPuertoEscucha() {
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
	*/
	/*public void servicioSync(String servicio, String param) {
		if (logger.isInfoEnabled()) {
			logger.info("servicioSync(String, String) - Ejecutando servicio: " + servicio + "("+ param+ ")");
		}

		if (servicio.equals("syncVentas")) {
			HiloSyncTransacciones.runAndWait();
			if (logger.isInfoEnabled()) {
				logger.info("servicioSync(String, String) - Finalizado servicio syncVentas");
			}
			return;
		}
		if (servicio.equals("actCaja")) {
			try {
				sincronizarSrvCR.syncCaja(param);
			} catch (BaseDeDatosExcepcion e) {
				logger.error("servicioSync(String, String)", e);
			}

			if (logger.isInfoEnabled()) {
				logger.info("servicioSync(String, String) - Finalizado servicio actCaja:" + param);
			}
			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("servicioSync(String, String) - end");
		}
	}
	*/

	public int getPuertoEscucha() {
		return puertoEscucha;
	}
	public boolean isSyncAfiliados() {
		return syncAfiliados;
	}
	
	/*public boolean isActCajaTemporizada() {
		return actCajaTemporizada;
	}*/

	/**
	 * Método getSincAfiliados
	 * 
	 * @return
	 * SincAfiliados
	 */
	public SincAfiliados getSincAfiliados() {
		return sincAfiliados;
	}

	/**
	 * Método getSincAfiliados
	 * 
	 * @return
	 * SincAfiliados
	 */
	public SincAfiliadosTemporales getSincAfiliadosTemporales() {
		return sincAfiliadosTemporales;
	}
	
	/**
	 * Método getSincServCentServTdaBases
	 * 
	 * @return
	 * SincCentralTdaBases
	 */
	public SincCentralTdaBases getSincServCentServTdaBases() {
		return sincServCentServTdaBases;
	}

	/**
	 * Método getSincServCentServTdaProds
	 * 
	 * @return
	 * SincCentralTdaProds
	 */
	public SincCentralTdaProds getSincServCentServTdaProds() {
		return sincServCentServTdaProds;
	}

	/**
	 * Método getSincServTdaServCent
	 * 
	 * @return
	 * SincTdaVentas
	 */
	public SincTdaVentas getSincServTdaServCent() {
		return sincServTdaServCent;
	}

	/**
	 * Método planificarBases
	 * 
	 * @param proximaEjecucion
	 * void
	 */
	public void planificarBases(Date proximaEjecucion) {
		if (intervaloSincBases.getTipoTarea()!=PoliticaTarea.AL_INICIAR) {
			sincServCentServTdaBases = new SincCentralTdaBases(this);
			planificador.schedule(sincServCentServTdaBases, proximaEjecucion);
		} else {
			intervaloSincBases.setProximaSincronizacion(null);
			actualizarMensajesDetalles();
		}
	}
	/**
	 * Método planificarAfiliados
	 * 
	 * @param proximaEjecucion
	 * void
	 */
	public void planificarAfiliados(Date proximaEjecucion) {
		if (intervaloSincAfiliados.getTipoTarea()!=PoliticaTarea.AL_INICIAR) {
			sincAfiliados = new SincAfiliados(this);
			planificador.schedule(sincAfiliados, proximaEjecucion);
		} else {
			intervaloSincAfiliados.setProximaSincronizacion(null);
			actualizarMensajesDetalles();
		}
	}
	
	/**
	 * Método planificarAfiliadosTemporales
	 * 
	 * @param proximaEjecucion
	 * void
	 */
	public void planificarAfiliadosTemporales(Date proximaEjecucion) {
		if (intervaloSincAfiliadosTemporales.getTipoTarea()!=PoliticaTarea.AL_INICIAR) {
			sincAfiliadosTemporales = new SincAfiliadosTemporales(this);
			planificador.schedule(sincAfiliadosTemporales, proximaEjecucion);
		} else {
			intervaloSincAfiliadosTemporales.setProximaSincronizacion(null);
			actualizarMensajesDetalles();
		}
	}
	
	
	
	/**
	 * Método planificarProductos
	 * 
	 * @param proximaEjecucion
	 * void
	 */
	public void planificarProductos(Date proximaEjecucion) {
		if (intervaloSincProductos.getTipoTarea()!=PoliticaTarea.AL_INICIAR) {
			sincServCentServTdaProds = new SincCentralTdaProds(this);
			planificador.schedule(sincServCentServTdaProds, proximaEjecucion);
		} else {
			intervaloSincProductos.setProximaSincronizacion(null);
			actualizarMensajesDetalles();
		}
	}
	/**
	 * Método planificarVentas
	 * 
	 * @param proximaEjecucion
	 * void
	 */
	public void planificarVentas(Date proximaEjecucion) {
		if (intervaloSincVentas.getTipoTarea()!=PoliticaTarea.AL_INICIAR) {
			sincServTdaServCent = new SincTdaVentas(this);
			planificador.schedule(sincServTdaServCent, proximaEjecucion);
		} else {
			intervaloSincVentas.setProximaSincronizacion(null);
			actualizarMensajesDetalles();
		}
	}

	/**
	 * Método getIntervaloSincAfiliados
	 * 
	 * @return
	 * PoliticaTarea
	 */
	public PoliticaTarea getIntervaloSincAfiliados() {
		return intervaloSincAfiliados;
	}
	
	/**
	 * Método getIntervaloSincAfiliados
	 * 
	 * @return
	 * PoliticaTarea
	 */
	public PoliticaTarea getIntervaloSincAfiliadosTemporales() {
		return intervaloSincAfiliadosTemporales;
	}

	/**
	 * Método getIntervaloSincBases
	 * 
	 * @return
	 * PoliticaTarea
	 */
	public PoliticaTarea getIntervaloSincBases() {
		return intervaloSincBases;
	}

	/**
	 * Método getIntervaloSincProductos
	 * 
	 * @return
	 * PoliticaTarea
	 */
	public PoliticaTarea getIntervaloSincProductos() {
		return intervaloSincProductos;
	}

	/**
	 * Método getIntervaloSincVentas
	 * 
	 * @return
	 * PoliticaTarea
	 */
	public PoliticaTarea getIntervaloSincVentas() {
		return intervaloSincVentas;
	}

	/**
	 * @return
	 */
	public PoliticaTarea getIntervaloSincListaRegalos() {
		return intervaloSincListaRegalos;
	}

	/**
	 * @return
	 */
	public SincListaRegalos getSincListaRegalos() {
		return sincListaRegalos;
	}

	/**
	 * Método planificarVentas
	 * 
	 * @param proximaEjecucion
	 * void
	 */
	public void planificarListaRegalos(Date proximaEjecucion) {
		if (intervaloSincListaRegalos.getTipoTarea()!=PoliticaTarea.AL_INICIAR) {
			sincListaRegalos = new SincListaRegalos(this);
			planificador.schedule(sincListaRegalos, proximaEjecucion);
		} else {
			intervaloSincListaRegalos.setProximaSincronizacion(null);
			actualizarMensajesDetalles();
		}
	}
}

/*
class HiloEscucha extends Thread {
	
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
	//private static final Logger logger = Logger.getLogger(HiloAtencion.class);

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
*/