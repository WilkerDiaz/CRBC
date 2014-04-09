/*
 * ===========================================================================
 * Proyecto		: CRFiscalPrinterDriver 1.1
 * Paquete		: com.epa.crprinterdriver
 * Programa		: GD4Engine.java
 * Creado por	: 
 * Creado en 	: 
 * 
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisión	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 *
 * ===========================================================================
 */
package com.epa.crprinterdriver;


import gnu.io.SerialPortEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.apache.log4j.Logger;
import com.epa.crprinterdriver.data.DataTablaImpuestos;
import com.epa.crprinterdriver.event.CRFPEvent;
import com.epa.crprinterdriver.event.CRFPResponseEvent;
import com.epa.crprinterdriver.event.FiscalPrinterListener;
import com.epa.crprinterdriver.event.ResponseListener;
import com.epa.crprinterdriver.event.StatusChangeListener;
import com.epa.crserialinterface.Connection;
import com.epa.crserialinterface.Parameters;


/**
 * <p>
 * Objeto dedicado a la comunicación directa con el dispositivo fiscal. Es el 
 * motor principal del driver fiscal y el cerebro mismo del driver
 * </p>
 * <p>
 * <a href="CRFPEngine.java.html"><i>View Source</i></a>
 * </p>
 */
public class GD4Engine implements DocumentListener, Runnable, ResponseListener, StatusChangeListener, CRFPEngine {
	/**
	 * Logger for this class
	 */
	
	boolean      libraryFound      = true;              // Fiscal232 library found flag
	int          returnCode        = 0;                 // Last command Fiscal Return code
	byte[]       commandBuffer     = new byte[ 256 ];   // Command to sent buffer
	byte[]       responseBuffer    = new byte[ 256 ];   // Fiscal Unit response buffer
	static int   fiscalPrinterPort = 0;                 // RS-232 Fiscal Printer attached port number
	static int[] displayPort       = new int[ 2 ];      // Distributed Display attached ports number
	int[]        display1Index     = new int[ 1 ];      // RS-232 Distributed Display #1 function call index
	int[]        display2Index     = new int[ 1 ];      // RS-232 Distributed Display #2 function call index
	
	static int   fiscalPrinterPortSerial = 1;                 // Número de puerto para comunicación serial
	
	private static final Logger logger = Logger.getLogger(GD4Engine.class);

	// Constantes de motivos para toma de control por parte del motor
	protected static final int NUM_CIERRE_CF = 0;
	protected static final int REQUIERE_Z = 1;
	protected static final int RESUME_ERROR = 2;
	protected static final int CANCELA_TRANSACCION = 3;
	protected static final int CORTAR_PAPEL= 4;
	protected static final int CANCELA_TRANSACCION_EMITEZ = 5;
	protected static final int REQUIERE_X = 6;
	protected static final int REPITE_COMANDO = 7;
	
	protected DataTablaImpuestos tabla ;
	protected DataTablaImpuestos tablaSiguiente;
	protected char MetodoImpuesto;
	
	

	protected GD4NativeInterfaceProxy printerHandle; 
	private JTextArea inputText;
	private CRFPSubsystem parent;
	private Vector<CRFPDocument> documents = new Vector<CRFPDocument>();
	private Thread sender = null;
	private boolean pausado = false;
	private boolean controlMotor = false;
	private Parameters serialParameters;
	// Datos para manejo de solicitud realizada 
	private CRFPCommand solicitudInfo = null;
	private boolean infoRetornada = false;

	// Manejo de eventos de impresora fiscal
	private Vector<FiscalPrinterListener> fiscalPrnListeners = new Vector<FiscalPrinterListener>();

	// Bandera de manejo entre envio y respuesta
	private class RespuestaImpresora {
		private CRFPResponseEvent evento;
		private boolean llego = false;
		private boolean seguirEsperando = false;

		/**
		 * @return Devuelve evento.
		 */
		public CRFPResponseEvent getEvento() {
			if (logger.isDebugEnabled()) {
				logger.debug("getEvento() - start");
			}

			if (logger.isDebugEnabled()) {
				logger.debug("getEvento() - end");
			}
			return evento;
		}

		/**
		 * @param evento El evento a establecer.
		 */
		public synchronized void setEvento(CRFPResponseEvent evento) {
			if (logger.isDebugEnabled()) {
				logger.debug("setEvento(CRFPResponseEvent) - start");
			}

			this.evento = evento;
			if (evento != null) {
				this.llego = true;
				this.seguirEsperando = false;
				notify();
			}

			if (logger.isDebugEnabled()) {
				logger.debug("setEvento(CRFPResponseEvent) - end");
			}
		}

		/**
		 * @return Devuelve llego.
		 */
		public boolean isLlego() {
			if (logger.isDebugEnabled()) {
				logger.debug("isLlego() - start");
			}

			if (logger.isDebugEnabled()) {
				logger.debug("isLlego() - end");
			}
			return llego;
		}

		/**
		 * @param llego El llego a establecer.
		 */
		public void setLlego(boolean llego) {
			if (logger.isDebugEnabled()) {
				logger.debug("setLlego(boolean) - start");
			}

			this.llego = llego;
			if (!llego) {
				evento = null;
			}

			if (logger.isDebugEnabled()) {
				logger.debug("setLlego(boolean) - end");
			}
		}

		public void setSeguirEsperando(boolean seguir)  {
			this.seguirEsperando = seguir;
		}

		public boolean isSeguirEsperando() {
			return seguirEsperando;
		}
	}

	RespuestaImpresora respuesta = new RespuestaImpresora();

	/**
	 * @since 11-abr-2005
	 * 
	 */
	public GD4Engine(CRFPSubsystem parent, Parameters serialParameters) {
		super();
		//new GD4Engine(fiscalPrinterPort);
		
		
		this.parent = parent;
		byte[] bMajorVersion = new byte[1];
		byte[] bMinorVersion = new byte[1];

		/* Instanciemos la interfaz de la impresora */
		inputText = new JTextArea();
		printerHandle = new GD4NativeInterfaceProxy();
		int completionCode = printerHandle.JGetDLLVersion(bMajorVersion, bMinorVersion);
		if (completionCode != GD4NativeInterfaceProxy.RC_232_OK) {
		
			if (logger.isDebugEnabled()) {
				logger.debug("Error: " + completionCode + ". Imposible obtener versión del driver fiscal.");
			}
			printerHandle.JCloseFiscalPrinterComPort();
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("Driver Fiscal GD4 v" + bMajorVersion[0] + "." + bMinorVersion[0]);
		}

		// Registrarse como escucha de eventos del driver e impresora
		inputText.getDocument().addDocumentListener(this);
		parent.responseParser.addResponseListener(this);
		parent.status.addStatusChangeListener(this);
		this.serialParameters = serialParameters;

		// Crear el hilo de envio de comandos
		sender = new Thread(this);
		sender.setName("Fiscal Driver Engine");

	}

	/* (sin Javadoc)
	 * @see java.lang.Object#finalize()
	 * @throws java.lang.Throwable
	 * @since 11-abr-2005
	 */
	protected void finalize() throws Throwable {
		if (logger.isDebugEnabled()) {
			logger.debug("finalize() - start");
		}

		super.finalize();

		if (logger.isDebugEnabled()) {
			logger.debug("finalize() - end");
		}
	}

	/* (sin Javadoc)
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 * @param e
	 * @since 11-abr-2005
	 */
	public void changedUpdate(DocumentEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("changedUpdate(DocumentEvent) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("changedUpdate(DocumentEvent) - end");
		}
	}

	/* (sin Javadoc)
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 * @param e
	 * @since 11-abr-2005
	 */
	public void insertUpdate(DocumentEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("insertUpdate(DocumentEvent) - start");
		}
		try {			
			//char hex = (char)Integer.decode(inputText.getText()).intValue();
			parent.responseParser.constructResponse(inputText.getText());

		} catch (Throwable t) {
			logger.fatal("insertUpdate()- Error al recibir respuesta de impresora", t);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("insertUpdate(DocumentEvent) - end");
		}
	}

	/* (sin Javadoc)
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
	 * @param e
	 * @since 11-abr-2005
	 */
	public void removeUpdate(DocumentEvent e) {
		if (logger.isDebugEnabled()) {
			logger.debug("removeUpdate(DocumentEvent) - start");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("removeUpdate(DocumentEvent) - end");
		}
	}

	/* (sin Javadoc)
	 * @see com.epa.crserialinterface.SerialSignalListener#signalReceived(gnu.io.SerialPortEvent)
	 * @param event
	 * @since 11-abr-2005
	 */
	public void signalReceived(SerialPortEvent event) {
		if (logger.isDebugEnabled()) {
			logger.debug("signalReceived(SerialPortEvent) - start");
		}

		int tipoEvento = -1;
		switch (event.getEventType()) {
		case SerialPortEvent.BI :
			tipoEvento = CRFPResponseParser.SIGNAL_BI;
			break;
		case SerialPortEvent.CD :
			tipoEvento = CRFPResponseParser.SIGNAL_CD;
			break;
		case SerialPortEvent.CTS :
			tipoEvento = CRFPResponseParser.SIGNAL_CTS;
			break;
		case SerialPortEvent.DSR :
			tipoEvento = CRFPResponseParser.SIGNAL_DSR;
			break;
		case SerialPortEvent.FE :
			tipoEvento = CRFPResponseParser.SIGNAL_FE;
			break;
		case SerialPortEvent.OE :
			tipoEvento = CRFPResponseParser.SIGNAL_OE;
			break;
		case SerialPortEvent.PE :
			tipoEvento = CRFPResponseParser.SIGNAL_PE;
			break;
		case SerialPortEvent.RI :
			tipoEvento = CRFPResponseParser.SIGNAL_RI;
			break;
		}

		parent.responseParser.signalArrived(tipoEvento, event.getNewValue());

		if (logger.isDebugEnabled()) {
			logger.debug("signalReceived(SerialPortEvent) - end");
		}
	}

	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.event.ResponseListener#responseArrived(com.epa.crprinterdriver.event.CRFPResponseEvent)
	 * @param event
	 * @since 14-abr-2005
	 */
	public void responseArrived(CRFPResponseEvent event) {
		if (logger.isDebugEnabled()) {
			logger.debug("responseArrived(CRFPResponseEvent) - start");
		}

		respuesta.setEvento(event);

		if (logger.isDebugEnabled()) {
			logger.debug("responseArrived(CRFPResponseEvent) - end");
		}
	}

	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.event.StatusChangeListener#changedStatus(int)
	 * @param paramChanged
	 * @since 14-abr-2005
	 */
	public void changedStatus(int paramChanged) {
		if (logger.isDebugEnabled()) {
			logger.debug("changedStatus(int) - start");
		}

		switch (paramChanged) {
		case CRFPStatus.PARAM_AUMENTAR_TIMEOUT :
			// Hay que considerar el aumento de timeout
			synchronized (respuesta) {
				logger.info("changedStatus() - La impresora notifica que debemos esperar más");
				respuesta.setSeguirEsperando(true);
				respuesta.notify();
			}
			break;
		case CRFPStatus.PARAM_DESBORDAMIENTO_TOTALES : 
			break;
		case CRFPStatus.PARAM_ERROR_IMPRESORA : 
			break;
		case CRFPStatus.PARAM_HAY_PAPEL :
			// Notificar la falta o colocación del papel
			if (!parent.status.isHayPapel()) {
				setPausado(true);
				doEventOccured(new CRFPEvent(CRFPEvent.FALTA_PAPEL, 0, false, 0));
				setPausado(false);
			}
			break;
		case CRFPStatus.PARAM_IMPRESORA_OCUPADA :
			break;
		case CRFPStatus.PARAM_MAX_ITEMS_CF : 
			break;
		case CRFPStatus.PARAM_MEM_FISCAL_LLENA : 
			break;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("changedStatus(int) - end");
		}
	}

	/* (sin Javadoc)
	 * @see java.lang.Runnable#run()
	 * 
	 * @since 11-abr-2005
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unused")
	public void run() {
		
		if (logger.isDebugEnabled()) {
			logger.debug("run() - start");
		}

		/*
		 *  Hilo de envío automático de comandos.
		 *  Al recibir un documento comienza el envío de sus comandos
		 */
		Vector<CRFPCommand> comandosMotor = new Vector<CRFPCommand>();

	//	CRFPCommand comandoMotor = null;
		int razonCtrlMotor = -1;
		int enviosMotor = 0;
		boolean notificarOK = false;
		boolean compFiscalRegistrado = false;
		CRFPDocument documentoActual = null;
		int numCompF = 0;
		int idDoc = 0;
//		int returnCodeAnterior=-1;
		
		while (sender == Thread.currentThread()) {
			try {
				synchronized (sender) {
					// Bloqueo el motor si no hay documentos para imprimir
					while (pausado || (documents.isEmpty() && solicitudInfo == null)) {
						parent.status.setImprimiendo(false);
						try {
							sender.wait();
							if (logger.isInfoEnabled())
								logger.info("run() - Hilo despertado para trabajo");
						} catch (InterruptedException e) {
							logger.error("run()", e);
						}
					}
					parent.status.setImprimiendo(true);					
				}
				// El motor recibió una notificación de que tenemos
				// pendientes una solicitud de información o un documento por imprimir.
				documentoActual = obtenerProximoDoc();
				notificarOK = false;
				compFiscalRegistrado = false;
				numCompF = 0;
				// Mientras hayan documentos disponibles:
				while (documentoActual != null || solicitudInfo != null) {
					int i = -1;

					if (documentoActual != null) {
						i = 0;
					}
					
					
					// Por cada documento
					int reintentosAbortarDocumento =0;
					documento:
						while ((i >= 0 && i < documentoActual.size()) || solicitudInfo != null || comandosMotor.size()!=0) {
							
							// Por cada comando del documento actual							
							CRFPCommand comandoActual = null;
							if (controlMotor) {
								// El motor necesita enviar un comando por su cuenta lo hacemos
								
								comandoActual = (CRFPCommand)comandosMotor.firstElement();
								
								enviosMotor++;
								logger.debug("control motor, envios "+enviosMotor +" a punto de enviar  "+CRFPCommand.getTypeDescription(comandoActual.getType()));								
								
								
							} else if (solicitudInfo != null) {
								// Enviamos el comando de solicitud de información
								comandoActual = solicitudInfo;
							} else {
								// Proximo comando del documento actual
								comandoActual = documentoActual.get(i);
							}
							// Actualizamos status
							respuesta.setLlego(false);
							parent.status.setTipoRespEsperada(comandoActual.getTipoRespEsperada());
							parent.status.setUltCommandTypeEnviado(comandoActual.getType());
							parent.status.setUltIDEnviado(comandoActual.getSequenceId());
							//reenvioComando:
							for(int reenvios = 0; reenvios < 3; reenvios++) {
								
								
								long timeInicio = new Date().getTime();
								while (parent.status.isImpresoraOcupada() || pausado){
									if (pausado) {
										parent.status.setImprimiendo(false);
										synchronized(sender) {
											try {
												sender.wait();
											} catch (InterruptedException e) {
												logger.error("run()", e);
											}
										}
									} else if (respuesta.isSeguirEsperando()) {
										respuesta.setSeguirEsperando(false);
										Thread.sleep(parent.sequenceMap.getExtraTimetoSleep());
									} else if (new Date().getTime() - timeInicio > parent.sequenceMap.getEsperaTimeout()) {
										logger.warn("run() - El tiempo esperando que la impresora " +
												"se desocupe ha sobrepasado el limite: "+ 
												parent.sequenceMap.getEsperaTimeout() + " ms");
										break;
									}
								}
								//
								parent.status.setImprimiendo(true);
								enviarComando(comandoActual.getSecuenciaEscape());
								if (logger.isInfoEnabled()) {
										logger.info("run() - Comando enviado a la impresora: "  
												+ comandoActual + ", envios anteriores = " + reenvios +" control = "+controlMotor);
									
								}
								// Aplicamos la espera correspondiente
								comandoActual.esperar();
								if (comandoActual.getType() != CRFiscalPrinterOperations.CMD_RESET_PRINTER &&
										comandoActual.getType() != CRFiscalPrinterOperations.CMD_ACTIVAR_SLIP) {
							    // Curso de acción dependiendo si llegó la respuesta y qué tipo de respuesta llegó
									synchronized(respuesta) {
										int tiempoEspera = parent.sequenceMap.getEsperaTimeout();
										if (!respuesta.isLlego()) {
											try {
												respuesta.setSeguirEsperando(true);
												while (respuesta.isSeguirEsperando()) {
													respuesta.setSeguirEsperando(false);
													logger.info("run() - Esperamos respuesta de la impresora");
													if (parent.status.isSlipActivo()) {
														tiempoEspera += parent.sequenceMap.getSlipTimeGap();
													}
													respuesta.wait(tiempoEspera);
													if (respuesta.isSeguirEsperando()) {
														tiempoEspera = parent.sequenceMap.getExtraTimetoSleep();
													}
												}
											} catch (InterruptedException e) {
												logger.error("run()", e);
											}
										}
									}
									if (respuesta.isLlego()) {			
										
										//TODO descomentar siguiente linea								
										logger.debug("respuesta <----------------> "+respuesta.getEvento().getResponseType());
										
										
									switch (respuesta.getEvento().getResponseType()) { 
									//TODO comentar	siguiente linea					
									//	switch (2) { 				
										case CRFPResponseEvent.RESPONSE_INVALID :
											logger.warn("run() - Se recibió una respuesta inválida de la impresora");
											if (controlMotor) {
												// Intentamos continuar el curso normal de impresión
												switch(razonCtrlMotor) {
												case NUM_CIERRE_CF :
													if (enviosMotor > 3) {
														notifyOKCF(idDoc, -1);
														controlMotor = false;
														comandosMotor.removeElementAt(0);
												//		comandoMotor = null;
														razonCtrlMotor = -1;
														enviosMotor = 0;
														//comandosMotor.re
													}
													break;
												default :
													controlMotor = false;
											//		comandoMotor = null;
													razonCtrlMotor = -1;
													enviosMotor = 0;
													break;
												}
											} else {
												// Si es un comando (documento) fiscal, se verifica su ejecucion
												if (documentoActual.isFiscal()) {
													i++;
												} else {
													// Averiguamos si alguien estaba esperando alguna respuesta
													if (solicitudInfo != null) {
														synchronized(solicitudInfo) {
															solicitudInfo.notify();
															solicitudInfo = null;
														}
													} else if (parent.status.getTipoRespEsperada() != CRFPStatus.DATA_NONE) {
														synchronized(documentoActual) {
															documentoActual.notify();
														}
													} else {
														// Nadie esperaba respuesta, continuamos
														i++;
													}
												}
											}
											break;
										case CRFPResponseEvent.RESPONSE_ERROR :
											/* Chequear tipo de error y actuar según 
											 * ello
											 */
											// Registro en log de ocurrencia de error
											logger.debug("run() - Error ocurrido - Código: "+ 
													respuesta.getEvento().getErrorCode() +	" - " +
													parent.sequenceMap.getErrorMessage(respuesta.getEvento().getErrorCode()));
											
											boolean analizaRespuesta=true;
											
											if (controlMotor) {												
												analizaRespuesta=false;												
												
												// Recuperamos el estado de la aplicación
												switch(razonCtrlMotor) {
												case NUM_CIERRE_CF:
													if (enviosMotor > 3) {
														notifyOKCF(idDoc, -1);
														comandosMotor.removeElementAt(0);
														if(comandosMotor.size()==0){
															controlMotor = false;
															razonCtrlMotor = -1;
														}
												//		comandoMotor = null;
														
														enviosMotor = 0;
													}
													break;
												default :
															if(comandosMotor.size()==0){
																controlMotor = false;
																razonCtrlMotor = -1;
															}
												//			comandoMotor = null;															
															enviosMotor = 0;
															break;
												}
											} /*else*/ {  
												int accion = parent.sequenceMap.getActionOnError(
														respuesta.getEvento().getErrorCode());
												if (accion == IGNORAR_ERROR) {
												
													if (solicitudInfo != null || 
															parent.status.getTipoRespEsperada() 
															!= CRFPStatus.DATA_NONE) {
														// No se debe ignorar el error si alguien espera respuesta
														logger.error(
																"run() - No puede ignorarse el error si se espera respuesta - " +
																"Se abortará el documento. Comando: " + comandoActual, null);
														accion = ABORTAR_DOCUMENTO;
													} else {
														// Documento sin respuesta, continuamos
														i++;
														logger.warn("run() - Error ignorado - Se continua impresión de documento",null);
													}
													
												}
												
																							
												
												
												//TODO descomentar siguiente linea											
												switch (accion) {
												//TODO comentar	siguiente linea	
												//switch (9) {
												case CERRAR_CF :
													if (compFiscalRegistrado) {
														// Adelantamos los comandos 
														// hasta conseguir algo distinto a
														// texto fiscal
														while (((CRFPCommand)documentoActual.get(i)).getType() == 
															CRFiscalPrinterOperations.CMD_TEXTO_CF) {
															i++;
														}
														// Registramos log de la  accion tomada
														logger.warn("run() - Maximo de Lineas de Texto Fiscal Alcanzado - " +
																" Se saltó al siguiente tipo de comando",null);

													} else {
														// ** Abortamos el documento
														// Registramos log de abortado de documento
														logger.error("run() - Documento abortado " + 
																" Error: " + respuesta.getEvento().getErrorCode() + " - " 
																+ parent.sequenceMap.getErrorMessage(respuesta.getEvento().getErrorCode()),null);
														// Enviamos notificación de aborto de documento
														doEventOccured(new CRFPEvent(CRFPEvent.ERROR_CRITICO, 
																documentoActual.getId(),documentoActual.isFiscal(), 
																numCompF, respuesta.getEvento().getErrorCode(), 
																parent.sequenceMap.getErrorMessage(respuesta.getEvento().getErrorCode()),true));
														// Tomamos control y reiniciamos la impresora
														controlMotor = true;
														enviosMotor = 0;
													/*	comandoMotor = new CRFPCommand(parent.sequenceMap, 
																CRFiscalPrinterOperations.CMD_RESET_PRINTER);*/
														comandosMotor.add(new CRFPCommand(parent.sequenceMap, 
																CRFiscalPrinterOperations.CMD_RESET_PRINTER));
														razonCtrlMotor = RESUME_ERROR;
													}													
													break;
												case REALIZAR_Z :
													// Notificamos la impresión de un reporte Z
													doEventOccured(new CRFPEvent(CRFPEvent.REQUIERE_Z, 
															documentoActual.getId(), documentoActual.isFiscal(),numCompF));
													// Registramos en log la impresión en demanda del Z
													logger.warn("run() - Impresora requiere Z - " +
															" Se realizó la impresión del mismo",null);

													// Enviamos el comando de impresión Z
													controlMotor = true;
													enviosMotor = 0;
											
													comandosMotor.add(new CRFPCommand(parent.sequenceMap,CRFiscalPrinterOperations.CMD_REPORTE_Z));

													razonCtrlMotor = REQUIERE_Z;
													break;
												case REALIZAR_X :
													// Notificamos la impresión de un reporte Z
/*													doEventOccured(new CRFPEvent(CRFPEvent.REQUIERE_Z, 
															documentoActual.getId(), documentoActual.isFiscal(),numCompF));*/
													// Registramos en log la impresión en demanda del Z
													logger.warn("run() - Impresora requiere X - " +
															" Se realizó la impresión del mismo",null);

													// Enviamos el comando de impresión Z
													controlMotor = true;
													enviosMotor = 0;
													//comandoMotor = new CRFPCommand(parent.sequenceMap,CRFiscalPrinterOperations.CMD_REPORTE_X);
													comandosMotor.add(new CRFPCommand(parent.sequenceMap,CRFiscalPrinterOperations.CMD_REPORTE_X));
													razonCtrlMotor = REQUIERE_X;
													break;													
												case CANCELAR_TRANS_VENTAS:
													controlMotor = true;
													enviosMotor = 0;
													// Se ejecutaran dos Comandos: Primero Cancelar Transaccion y luego Cortar Papel.
/*													comandosMotor.add(new CRFPCommand(parent.sequenceMap,CRFiscalPrinterOperations.CMD_CANC_CFV));
													comandosMotor.add(new CRFPCommand(parent.sequenceMap,CRFiscalPrinterOperations.CMD_CORTAR_PAPEL));
													comandosMotorIt = comandosMotor.iterator();*/
											//		comandoMotor = new CRFPCommand(parent.sequenceMap,CRFiscalPrinterOperations.CMD_CANC_CFV);
													comandosMotor.add(new CRFPCommand(parent.sequenceMap,CRFiscalPrinterOperations.CMD_CANC_CFV));
													razonCtrlMotor = CANCELA_TRANSACCION;
													break;
												case CANCELAR_DEVOLUCION:
													controlMotor = true;
													enviosMotor = 0;
													//comandoMotor = new CRFPCommand(parent.sequenceMap,CRFiscalPrinterOperations.CMD_CANC_CFNC);
													comandosMotor.clear();
													if(reintentosAbortarDocumento < 3){
														if (reintentosAbortarDocumento == 0){
															comandosMotor.add(new CRFPCommand(parent.sequenceMap,CRFiscalPrinterOperations.CMD_CANC_CFNC));
															reintentosAbortarDocumento++;
														} else {
															/*Object[] data = new Object[3];
															data[0] = "   NOTA DE CREDITO ANULADA";
															data[1] = new Integer(3);
															data[2] = new Boolean(true);
															comandosMotor.addElement(new CRFPCommand(parent.sequenceMap, CRFiscalPrinterOperations.CMD_TEXTO_CF, data));*/
															comandosMotor.addElement(new CRFPCommand(parent.sequenceMap,CRFiscalPrinterOperations.CMD_CERRAR_CFNC));
															reintentosAbortarDocumento++;
															break;
														}
													} else {
														reintentosAbortarDocumento =0;
													}
													razonCtrlMotor = CANCELA_TRANSACCION;
													/*comandosMotor.add(new CRFPCommand(parent.sequenceMap,CRFiscalPrinterOperations.CMD_CANC_CFV));
													comandosMotor.add(new CRFPCommand(parent.sequenceMap,CRFiscalPrinterOperations.CMD_CORTAR_PAPEL));
													comandosMotorIt = comandosMotor.iterator();*/
													break;
												case CANCELAR_TRANS_EMITIRZ:
													controlMotor = true;
													enviosMotor = 0;
													//comandoMotor = new CRFPCommand(parent.sequenceMap,CRFiscalPrinterOperations.CMD_CANC_CFV);
													comandosMotor.add(new CRFPCommand(parent.sequenceMap,CRFiscalPrinterOperations.CMD_CANC_CFV));
													comandosMotor.add(new CRFPCommand(parent.sequenceMap,CRFiscalPrinterOperations.CMD_REPORTE_Z));
													razonCtrlMotor = CANCELA_TRANSACCION_EMITEZ;
													break;
												case REPETIR_COMANDO:
													controlMotor =true;
													enviosMotor=0;
													//comandoMotor = comandoActual;
													
													razonCtrlMotor=REPITE_COMANDO;
													break;
												case ABORTAR_DOCUMENTO :
													if (solicitudInfo == null) {
														// ** Abortamos el documento
														// Registramos log de abortado de documento
														logger.error("run() - Documento abortado. Comando: " + comandoActual + 
																" Error: " + respuesta.getEvento().getErrorCode() + 
																" - " + parent.sequenceMap.getErrorMessage(
																		respuesta.getEvento().getErrorCode()),null);
														// Enviamos notificación de aborto de documento
														if (parent.status.getTipoRespEsperada() != CRFPStatus.DATA_NONE) {
															synchronized(documentoActual) {
																documentoActual.notify();
															}
														} else {
															doEventOccured(new CRFPEvent(CRFPEvent.ERROR_CRITICO, 
																	documentoActual.getId(),documentoActual.isFiscal(), 
																	numCompF, respuesta.getEvento().getErrorCode(), 
																	parent.sequenceMap.getErrorMessage(respuesta.getEvento().getErrorCode()),true));
		
														}
														// Tomamos control y reiniciamos la impresora
														controlMotor = true;
														enviosMotor = 0;
														razonCtrlMotor = RESUME_ERROR;
														comandosMotor.clear();
														//comandoMotor = new CRFPCommand(parent.sequenceMap,CRFiscalPrinterOperations.CMD_RESET_PRINTER);
														if(documentoActual.isFiscal()){
															Object[] data = new Object[3];
															data[0] = "         FACTURA ANULADA";
															data[1] = new Integer(3);
															data[2] = new Boolean(true);
															switch (reintentosAbortarDocumento){
															case 0:
																comandosMotor.addElement(new CRFPCommand(parent.sequenceMap, CRFiscalPrinterOperations.CMD_TEXTO_CF, data));
																comandosMotor.addElement(new CRFPCommand(parent.sequenceMap,CRFiscalPrinterOperations.CMD_CERRAR_CF));
																//reenvios--;
																reintentosAbortarDocumento++;
																break;
															case 1:
																comandosMotor.addElement(new CRFPCommand(parent.sequenceMap,CRFiscalPrinterOperations.CMD_CANC_CFNC));
																//reenvios--;
																reintentosAbortarDocumento++;
																break;
															case 2:
																//comandosMotor.addElement(new CRFPCommand(parent.sequenceMap, CRFiscalPrinterOperations.CMD_TEXTO_CF, data));
																comandosMotor.addElement(new CRFPCommand(parent.sequenceMap,CRFiscalPrinterOperations.CMD_CERRAR_CFNC));
																reintentosAbortarDocumento++;
																break;
															default:
																reintentosAbortarDocumento =0;
																break;
															}
														} /*else if(!documentoActual.isFiscal()) {
															comandosMotor.addElement(new CRFPCommand(parent.sequenceMap,CRFiscalPrinterOperations.CMD_Ca_DNF));
															comandosMotor.add(new CRFPCommand(parent.sequenceMap,CRFiscalPrinterOperations.CMD_CORTAR_PAPEL));
														}*/
															
													} else {
														// Notificamos un error al solicitar información
														synchronized(solicitudInfo) {
															infoRetornada = false;
															solicitudInfo.notify();
															solicitudInfo = null;
														}
													}
													break;
												case ESPERAR_APLICACION :
													if (solicitudInfo != null) {													
														// Notificamos un error al solicitar información
														synchronized(solicitudInfo) {
															infoRetornada = false;
															solicitudInfo.notify();
															solicitudInfo = null;
														}										
													} else {														
														// Solicitamos intervención del usuario
														CRFPEvent event = new CRFPEvent(
																CRFPEvent.ERROR_ATENCION_USUARIO, 
																documentoActual.getId(),documentoActual.isFiscal(), 
																numCompF ,respuesta.getEvento().getErrorCode(), 
																parent.sequenceMap.getErrorMessage(respuesta.getEvento().getErrorCode()));
														doEventOccured(event);														
														if (event.isReintentar()) {
															reenvios = 0;
														} else {
															if(documentoActual.isFiscal()){
	//															** Abortamos el documento
																// Registramos log de abortado de documento
																logger.error("run() - Documento abortado " + 
																		" Error: " + respuesta.getEvento().getErrorCode() + " - " 
																		+ parent.sequenceMap.getErrorMessage(respuesta.getEvento().getErrorCode()),null);
																// Tomamos control y reiniciamos la impresora
																controlMotor = true;
																enviosMotor = 0;
																comandosMotor.add(new CRFPCommand(parent.sequenceMap, 
																		CRFiscalPrinterOperations.CMD_CANC_CF));
																razonCtrlMotor = RESUME_ERROR;
															} else {
															    //Abortamos el documento
																// Registramos log de abortado de documento
																logger.error("run() - Documento abortado " + 
																		" Error: " + respuesta.getEvento().getErrorCode() + " - " 
																		+ parent.sequenceMap.getErrorMessage(respuesta.getEvento().getErrorCode()),null);
																// Tomamos control y reiniciamos la impresora
																controlMotor = true;
																enviosMotor = 0;
																Object[] data = new Object[2];
																data[0] = "DOCUMENTO INCOMPLETO";
																data[1] = new Integer(3);
																comandosMotor.addElement(new CRFPCommand(parent.sequenceMap, 
																		CRFiscalPrinterOperations.CMD_TEXTO_DNF, data));
																comandosMotor.add(new CRFPCommand(parent.sequenceMap, 
																		CRFiscalPrinterOperations.CMD_CERRAR_DNF));
																comandosMotor.add(new CRFPCommand(parent.sequenceMap, 
																		CRFiscalPrinterOperations.CMD_CORTAR_PAPEL));
																/*comandosMotor.add(new CRFPCommand(parent.sequenceMap, 
																		CRFiscalPrinterOperations.CMD_RESET_PRINTER));*/
																razonCtrlMotor = RESUME_ERROR;
															}
														}
													}

													break;
												case REPETIR_DOCUMENTO :
													if (solicitudInfo == null)
														i = 0;
													break;
												case REINICIAR_IMPRESORA:
													
													controlMotor = true;
													enviosMotor = 0;
													//comandoMotor = new CRFPCommand(parent.sequenceMap,CRFiscalPrinterOperations.CMD_RESET_PRINTER);
													comandosMotor.add(new CRFPCommand(parent.sequenceMap,CRFiscalPrinterOperations.CMD_RESET_PRINTER));
													//razonCtrlMotor = CANCELA_TRANSACCION_EMITEZ;
													break;
												}	
												
											}
											notificarOK = false;										
											break;
										case CRFPResponseEvent.RESPONSE_DATA:
											if (controlMotor) {
												// Llego la respuesta que estabamos esperando. Actuar
												switch(razonCtrlMotor) {
												case NUM_CIERRE_CF:
													notifyOKCF(idDoc, parent.status.getNumComprobanteFiscal());													
													comandosMotor.removeElementAt(0);													
													break;
												}
												if(comandosMotor!= null && comandosMotor.size() > 0){
													controlMotor = true;
																										
												}else{
													controlMotor = false;
												}
											//	comandoMotor = null;
												razonCtrlMotor = -1;
												enviosMotor = 0;
											} else {
												if (solicitudInfo != null) {
													synchronized (solicitudInfo) {
														infoRetornada = true;
														solicitudInfo.notify();
														solicitudInfo = null;
													}
												} else {
													// El dato no es de una solicitud simple, 
													// sino una compleja
													synchronized (documentoActual) {
														documentoActual.setListaRespuesta(true);
														documentoActual.notify();
													}
													i++; 
												}
											}
											if (comandoActual.getType() == CRFiscalPrinterOperations.CMD_CERRAR_CF || comandoActual.getType() == CRFiscalPrinterOperations.CMD_CERRAR_CFV || comandoActual.getType() == CRFiscalPrinterOperations.CMD_CERRAR_CFNC) {
												//if (comandoActual.getType() == CRFiscalPrinterOperations.CMD_CERRAR_CF) {												
												compFiscalRegistrado = false;
											}
											notificarOK = false;
											break;
										case CRFPResponseEvent.RESPONSE_OK :
												i++;
												notificarOK = true;
												if(!(comandosMotor.size()>0)){
													controlMotor = false;
												}else{
													comandosMotor.removeElementAt(0);													
												}
											//	comandoMotor = null;
												enviosMotor = 0;
												razonCtrlMotor = -1; 
												if (comandoActual.getType() == CRFiscalPrinterOperations.CMD_CERRAR_CF || comandoActual.getType() == CRFiscalPrinterOperations.CMD_CERRAR_CFV || comandoActual.getType() == CRFiscalPrinterOperations.CMD_CERRAR_CFNC){
													//if (comandoActual.getType() == CRFiscalPrinterOperations.CMD_CERRAR_CF) {
													/* Recibido el cierre parcial de una factura
													 * Es decir, ya esta registrada en la memoria fiscal.
													 * Notificar a la aplicación
													 */
													// Tomamos el control y solicitamos el numero de comprobante fiscal
													if (!compFiscalRegistrado) {
														controlMotor = true;
														enviosMotor = 0;
														razonCtrlMotor = NUM_CIERRE_CF;
													/*	comandoMotor = parent.operations.getComandoEstatus(
																CRFiscalPrinterOperations.ESTADO_CONTADORES);*/
														comandosMotor.add(parent.operations.getComandoEstatus(
																CRFiscalPrinterOperations.ESTADO_CONTADORES));
														idDoc = documentoActual.getId();
													} 
													compFiscalRegistrado = true;
												} else if(comandoActual.getType() == CRFiscalPrinterOperations.CMD_ABRIR_CF){
													parent.status.setTipoUltimoDocumentoImpreso(CRFiscalPrinterOperations.DOC_FISCAL);
												} else if(comandoActual.getType() == CRFiscalPrinterOperations.CMD_ABRIR_CF_DEV){
													parent.status.setTipoUltimoDocumentoImpreso(CRFiscalPrinterOperations.DOC_FISCAL_NC);
												}
											break;
										}
										break;
										//If Respuesta.isLlego()
									} else {
										// Salimos por timeout, nunca llego la respuesta
										// Enviamos nuevamente, hasta tres envios
										if (reenvios >= 2) {
											// Superado limite de reenvios,
											// notificar a la aplicacion
											logger.warn("run() - Se superó el límite de reenvíos: " + comandoActual);
											if (controlMotor) {
												// Hace falta ver que debe ocurrir 
												// cuando se espera una respuesta
												switch(razonCtrlMotor) {
												case NUM_CIERRE_CF :
													notifyOKCF(idDoc, -1);
													break;
												}
												controlMotor = false;
												enviosMotor = 0;
										//		comandoMotor = null;
												razonCtrlMotor = -1;
											} else if (solicitudInfo != null) {
												synchronized(solicitudInfo) {
													infoRetornada = false;
													solicitudInfo.notify();
													solicitudInfo = null;
												}
											} else {
												// Solicitamos intervención del usuario
												CRFPEvent event = new CRFPEvent(CRFPEvent.ERROR_ATENCION_USUARIO, 
														documentoActual.getId(),documentoActual.isFiscal(), numCompF);
												doEventOccured(event);
												if (event.isReintentar()) {
													reenvios = 0;
												} else {
													// Abortamos documento
													logger.error("run() - Documento abortado por timeout ",null);
													// Enviamos notificación de aborto de documento
													doEventOccured(new CRFPEvent(CRFPEvent.ERROR_CRITICO, 
															documentoActual.getId(),documentoActual.isFiscal(), 
															numCompF, -1, "", true));
													// Tomamos control y reiniciamos la impresora
													controlMotor = true;
													razonCtrlMotor = RESUME_ERROR;
													enviosMotor = 0;
											//		comandoMotor = new CRFPCommand(parent.sequenceMap, CRFiscalPrinterOperations.CMD_RESET_PRINTER);
													comandosMotor.add( new CRFPCommand(parent.sequenceMap, CRFiscalPrinterOperations.CMD_RESET_PRINTER));
												}
											}
										} else { // 0 < reenvios < 2
											logger.warn("run() - Se está reenviando por " + (reenvios + 1) + "° vez: " + comandoActual);
										}
									}
								} else {
									// No se va a recibir respuesta alguna, puesto
									// que se apagó y encendió nuevamente la impresora

									// Si la reinicialización fue enviada por el motor
									// es debido a un error de documento abortado
									// Saltamos dicho documento
									if (controlMotor) {
										// Devolvemos el control a los comandos enviados
										// por la aplicacion
										controlMotor = false;
										enviosMotor = 0;
										//comandoMotor = null;
										razonCtrlMotor = -1;
										break documento;
									} else {
										i++;
										break;
									}
								}
							
								
							}//FOR: Reenvios
						}//WHILE: Documentos
					// Se acabo con el documento anterior
					if (notificarOK) {
						// Debemos crear el evento de notificación de
						// que el documento se imprimió correctamente
						doEventOccured(new CRFPEvent(CRFPEvent.IMPRESION_OK, 
								documentoActual.getId(), documentoActual.isFiscal(), numCompF));
						CRFiscalPrinterOperations.transaccionPorImprimir = false;
					}
					documents.remove(documentoActual);
					documentoActual = obtenerProximoDoc();
				}//While: Mientras hayan documentos disponibles.
			} catch (Throwable t) {
				logger.error("Error crítico en Driver Fiscal - Reiniciando estado de driver", t);
				if (documentoActual != null) {
					synchronized(documentoActual) {
						documentoActual.notifyAll();
					}
					documents.remove(documentoActual);
					documentoActual = null;
				}
				if (solicitudInfo != null) {
					synchronized(solicitudInfo) {
						solicitudInfo.notifyAll();
						solicitudInfo = null;
					}
				}
			//	comandoMotor = null;
				enviosMotor = 0;
				razonCtrlMotor = -1;
				controlMotor = false;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("run() - end");
		}
	}

	private void notifyOKCF(final int idDoc, final int numCF) {
		Runnable r = new Runnable() {
			public void run() {
				doEventOccured(new CRFPEvent(CRFPEvent.IMPRESION_OK,idDoc, true, numCF));
			}
		};
		new Thread(r, "Notificación de impresión OK").start();
	}

	private void enviarComando(String comando) {
		if (logger.isDebugEnabled()) {
			logger.debug("enviarSecuenciaEscape(String) - start");
		}

		//long bufferLength;
		byte[] outBuffer = new byte[256];
		int i=0,j=0;
		String textoAscii = "";

		while(i < comando.length() && comando.charAt(i)=='0' && comando.charAt(i+1)=='x') {
			i+=2; // Identificador hexadecimal <0x>HH
			outBuffer[j] = (byte) Integer.parseInt(comando.substring(i,i+2), 16);//Caracteres hexadecimales
			i+=2; // Par hexadecimal 0x<HH>
			j++; // Próximo par hexadecimal
		}
		logger.info("GD4Engine::enviarComando(comando) - Comando="+comando);

		if (logger.isDebugEnabled()) {
			logger.debug("GD4Engine::enviarComando(comando) - Comando="+comando);
		}
		if(i < comando.length()) {
			textoAscii = comando.substring(i);
			System.arraycopy(textoAscii.getBytes(), 0, outBuffer, j, textoAscii.getBytes().length);
		}
		int completionCode = printerHandle.JSendCommand(outBuffer, (long)(j+textoAscii.getBytes().length));
		// Si el comando fue enviado correctamente ahora analizamos la respuesta
		if (completionCode == GD4NativeInterfaceProxy.RC_232_OK) {
			parseResponse();
		} else {
			logger.error("GD4Engine:: enviarComando(String) - returnCode="+completionCode);
			//Modificado por CENTROBECO-AMAND PARA SOLUCIONAR PROBLEMA DE PERDIDA DE COMUNICACION CON LA IMPRESORA
			if(completionCode==GD4NativeInterfaceProxy.RC_232_PLD){
				//waitForIplCompletion();
				//WDIAZ:08-2013 Cambio para establecer comunicacion con nuevo microcodigo de la impresora KC4, ya que con los metodos anteriores no lo realizaba
				printerHandle.JCloseFiscalPrinterComPort();
				printerHandle.JOpenFiscalPrinterComPort(fiscalPrinterPortSerial);
				waitForIplCompletion();
			} if(completionCode == GD4NativeInterfaceProxy.RC_232_NOTHING_RECV){
				try{
					Thread.sleep(100);
				} catch(Exception e){}
			}	
		}
		if (logger.isDebugEnabled()) {
			logger.debug("enviarSecuenciaEscape(String) - end");
		}
	}
	/***************************************************************************
	 * Read Fiscal Unit status until a final status is received, or an
	 * unexpected error is sent from the RS-232 Fiscal Printer library. Every
	 * intermediate/asynchronous/final received status is shown on screen. It
	 * also looks for timeout (printer offline), IPL and PLD command recovery
	 * conditions WARNING: you should invoke this method only if a previously
	 * command execution was successful (JSendCommand() error was RC_232_OK),
	 * otherwise your application might fall into a deadlock condition by
	 * receiving the RC_232_NOTHING_RECV all the time.
	 * 
	 * @return RS-232 Library operation (not fiscal) error (see JFiscal232.java values)
	 */
	@SuppressWarnings("unused")
	private void parseResponse() {
		int completionCode; // Library operation error
		long responseLength[] = new long[1]; // Store the Fiscal Unit response length
		boolean iplInProgress = false; // Fiscal Printer turned on while reading status
		boolean pldDetected = false; // Fiscal Printer turned on after PLD
		boolean timeoutDetected = false; // Fiscal Printer offline flag
		boolean finalStatusReceived = false; // Final status flag
		int returnCode = 0; // Last command Fiscal Return code
		StringBuffer response = new StringBuffer();
		byte[] responseBuffer = new byte[262]; // Fiscal Unit response buffer
		
		do {
			// Wait 100 milliseconds in order to avoid too many
			// RC_232_NOTHING_RECV completion codes
			//TODO
			/*try {
				Thread.sleep(100);
			} catch( InterruptedException e ) {	}*/
			// Read status from Fiscal Printer
			completionCode = printerHandle.JReadStatus(responseBuffer,responseLength);

			// Check the RS-232 Fiscal Printer library completion (not fiscal) error
			switch (completionCode) {
			case GD4NativeInterfaceProxy.RC_232_NOTHING_RECV:
				// Nothing received from Fiscal Printer
				break;
			case GD4NativeInterfaceProxy.RC_232_OK:
			case GD4NativeInterfaceProxy.RC_232_INTERM_STATUS_RECV:
				/*
				 * Se debe limpiar el buffer ya que la respuesta 
				 * anterior es Invalida por no ser the final status.
				 * */
				response= new StringBuffer(); 
				for (int responseIndex = 0; responseIndex < (int) responseLength[0]; responseIndex++) {
					if ((responseBuffer[responseIndex] >= 0x00) && (responseBuffer[responseIndex] <= 0x0F)){
						response.append("0x0" + Integer.toHexString(responseBuffer[responseIndex] & 0xFF));
					}
					else {
						response.append("0x" + Integer.toHexString(responseBuffer[responseIndex] & 0xFF));
					}
				}

				// If it's a final status, store return code and exit
				if ((responseBuffer[8] & 0x60) == 0x00) { // Not a intermediate/asynchronous status
					finalStatusReceived = true;
					if ((int) responseBuffer[13] < 0)
						returnCode = 128 + (responseBuffer[13] & 0x7F);
					else
						returnCode = (int)responseBuffer[13];
				}
				// Clear any previous error flag
				//parent.status.setConnected(true);
				timeoutDetected = false;
				pldDetected = false;
				iplInProgress = false;
				break;
			case GD4NativeInterfaceProxy.RC_232_TIMEOUT:
				// Fiscal Printer went offline
				if (!timeoutDetected) {
					timeoutDetected = true;
					//parent.status.setConnected(false);
					if (logger.isDebugEnabled()) {
						logger.debug("Ensure Fiscal Printer La impresora está desconectada. Esperando reconexión...");
					}
					
				}
				break;
			case GD4NativeInterfaceProxy.RC_232_PLD:
				// Fiscal Printer was turned off and on during a command execution
				if (!pldDetected) {
					pldDetected = true;
					if (logger.isDebugEnabled()) {
						logger.debug("Recuperando comando despues de interrupción eléctrica");
					}
					
				}
				break;
			case GD4NativeInterfaceProxy.RC_232_IN_IPL:
				// Fiscal Printer comes online
				if (!iplInProgress) {
					iplInProgress = true;
					if (logger.isDebugEnabled()) {
						logger.debug("Inicializando impresora fiscal...");
					}
					
				}
				break;
			default:
				// Error accessing fiscal printer device
				logger.debug("Error accessing fiscal printer device...");
			}
		} while (!finalStatusReceived);
		
		inputText.setText(response.toString());
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Iterator'
	* Fecha: agosto 2011
	*/
	private CRFPDocument obtenerProximoDoc() {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerProximoDoc() - start");
		}

		CRFPDocument result = null;
		for (Iterator<CRFPDocument> i = documents.iterator(); i.hasNext() ; ) {
			result = (CRFPDocument)i.next();
			if (result != null && result.getStatus() == CRFPDocument.STATUS_LISTO) {
				break;
			} else {
				result = null;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerProximoDoc() - end");
		}
		return result;
	}
	
	/**
	 * @return Devuelve serialConnection.
	 */
	public Connection getSerialConnection() {
		if (logger.isDebugEnabled()) {
			logger.debug("getSerialConnection() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getSerialConnection() - end");
		}
		return null/*serialConnection*/;
	}

	/**
	 * Método para agregar al spool de impresión un documento.
	 * 
	 * @param doc
	 */
	public int addDocument(CRFPDocument doc) {
		if (logger.isDebugEnabled()) {
			logger.debug("addDocument(CRFPDocument) - start");
		}

		synchronized (sender) {
			documents.add(doc);
			sender.notify();
		}
		int returnint = doc.getId();
		if (logger.isDebugEnabled()) {
			logger.debug("addDocument(CRFPDocument) - end");
		}
		return returnint;
	}

	/**
	 * Método de obtención de información tipo estatus desde la impresora fiscal
	 * 
	 * @param cmd Comando de solicitud de información
	 * @return Data retornada por la impresora fiscal
	 * @throws PrinterNotConnectedException si ocurre un error al recibir la respuesta
	 */
	public synchronized Object getSimpleResponse(CRFPCommand cmd) throws PrinterNotConnectedException {
		if (logger.isDebugEnabled()) {
			logger.debug("getSimpleResponse(CRFPCommand) - start");
		}

		Object result = null;
		synchronized (sender) {
			solicitudInfo = cmd;
			infoRetornada = false;
			sender.notify();
		}
		synchronized (solicitudInfo) {
			try {
				if (!infoRetornada) 
					solicitudInfo.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
				logger.error("getSimpleResponse(CRFPCommand)", e);
			}
		}
		if (infoRetornada) {
			result = parent.status.getDataRespuesta();
		} else {
			// Hay que lanzar una excepcion porque la información 
			// no pudo ser entregada
			throw new PrinterNotConnectedException("No pudo obtenerse la información solicitada");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getSimpleResponse(CRFPCommand) - end");
		}
		return result;
	}

	/**
	 * Método de impresión de documentos que retornan información compleja, tales
	 * como reportes. Este método espera hasta que se realice la impresión del 
	 * documento y sea devuelta la información solicitada
	 * 
	 * @param doc Documento a imprimir
	 * @return Objeto de datos
	 * @throws PrinterNotConnectedException si ocurre un error al recibir la respuesta	 
	 */
	public Object getComplexResponse(CRFPDocument doc) throws PrinterNotConnectedException {
		if (logger.isDebugEnabled()) {
			logger.debug("getComplexResponse(CRFPDocument) - start");
		}
		Object data = null;
		synchronized(doc) {
			doc.setListaRespuesta(false);
			addDocument(doc);
			try {
				doc.wait();
			} catch (InterruptedException e) {
				logger.error("getComplexResponse(CRFPDocument)", e);
			}
			if (doc.isListaRespuesta()) {
				data = parent.status.getDataRespuesta();
			} else {
				logger.error("getComplexResponse() - No pudo obtenerse la información esperada",null);
				throw new PrinterNotConnectedException("No pudo obtenerse información solicitada");
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getComplexResponse(CRFPDocument) - end");
		}
		return data;
	}

	/**
	 * Inicia la operación del motor
	 *
	 */
	public void iniciar() {
		if (logger.isDebugEnabled()) {
			logger.debug("iniciar() - start");
		}

		sender.start();

		if (logger.isDebugEnabled()) {
			logger.debug("iniciar() - end");
		}
	}

	/**
	 * Finaliza la operación del motor
	 */
	public void finalizar() {
		if (logger.isDebugEnabled()) {
			logger.debug("finalizar() - start");
		}

		// Chequear que la impresora quede en un estado estable 
		// antes de finalizar el driver
		sender = null;

		if (logger.isDebugEnabled()) {
			logger.debug("finalizar() - end");
		}
	}

	/**
	 * Permite agregar clases de escucha para eventos de la impresora fiscal
	 * @param listener Objeto a escuchar eventos del dispositivo fiscal
	 */
	public void addFiscalPrinterListener(FiscalPrinterListener listener) {
		if (logger.isDebugEnabled()) {
			logger.debug("addFiscalPrinterListener(FiscalPrinterListener) - start");
		}

		fiscalPrnListeners.add(listener);

		if (logger.isDebugEnabled()) {
			logger.debug("addFiscalPrinterListener(FiscalPrinterListener) - end");
		}
	}

	/**
	 * Permite remover objetos de escucha de eventos de la impresora fiscal
	 * @param listener Objeto a remover de la lista de escuchas de eventos
	 */
	public void removeFiscalPrinterListener(FiscalPrinterListener listener) {
		if (logger.isDebugEnabled()) {
			logger.debug("removeFiscalPrinterListener(FiscalPrinterListener) - start");
		}

		fiscalPrnListeners.remove(listener);

		if (logger.isDebugEnabled()) {
			logger.debug("removeFiscalPrinterListener(FiscalPrinterListener) - end");
		}
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Iterator'
	* Fecha: agosto 2011
	*/
	protected void doEventOccured(CRFPEvent event) {
		if (logger.isDebugEnabled()) {
			logger.debug("doEventOccured(CRFPEvent) - start");
		}

		for (Iterator<FiscalPrinterListener> i = fiscalPrnListeners.iterator(); i.hasNext() ;) {
			FiscalPrinterListener listener = (FiscalPrinterListener)i.next();
			listener.eventOccured(event);

		}

		if (logger.isDebugEnabled()) {
			logger.debug("doEventOccured(CRFPEvent) - end");
		}
	}	

	/**
	 * Indica si el motor se encuentra pausado o no
	 * @return Devuelve pausado.
	 */
	public boolean isPausado() {
		if (logger.isDebugEnabled()) {
			logger.debug("isPausado() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isPausado() - end");
		}
		return pausado;
	}

	/**
	 * Pausa o activa el motor
	 * @param pausado El pausado a establecer.
	 */
	public void setPausado(boolean pausado) {
		if (logger.isDebugEnabled()) {
			logger.debug("setPausado(boolean) - start");
		}
		this.pausado = pausado;
		if (!pausado) {
			synchronized(sender) {
				sender.notify();
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("setPausado(boolean) - end");
		}
	}

	/**
	 * Abre la conexión serial del motor
	 */
	public void abrirConexion() {
		if (logger.isDebugEnabled()) {
			logger.debug("abrirConexion() - start");
		}
		int puertoSerial = fiscalPrinterPortSerial;        
		//puertoSerial = Integer.parseInt(serialParameters.getPortName().substring(serialParameters.getPortName().length()-1));	
		
		//printerHandle.JSetFiscalPrinterComPortBaudRate(serialParameters.getBaudRate());
		printerHandle.JOpenFiscalPrinterComPort(puertoSerial);
		waitForIplCompletion();
		
		try{
			Object[] data = new Object[1];
			data[0] = Character.toString('x');
			//Se lee la tabla de impuestos al abrir la conexion.
			tabla = (DataTablaImpuestos)getSimpleResponse(new CRFPCommand(parent.sequenceMap, CRFiscalPrinterOperations.CMD_TABLA_IMP,data));
			tablaSiguiente = (DataTablaImpuestos)getSimpleResponse(new CRFPCommand(parent.sequenceMap, CRFiscalPrinterOperations.CMD_TABLA_IMP,data));
			while(tablaSiguiente.getTablaImpuestos()[2]<99.0){
				tabla=(DataTablaImpuestos)tablaSiguiente.clone();
				tablaSiguiente = (DataTablaImpuestos)getSimpleResponse(new CRFPCommand(parent.sequenceMap, CRFiscalPrinterOperations.CMD_TABLA_IMP2,data));
			}
			//Envio seqMap la tabla de Impuestos
			parent.sequenceMap.setTabla(tabla);
		}catch(PrinterNotConnectedException ex){
			ex.printStackTrace();
		}
		try{
			Integer metodoImpuesto;
			Object[] data = new Object[1];
			data[0] = Character.toString('x');
			//Se lee la tabla de impuestos al abrir la conexion.
			metodoImpuesto = (Integer)getSimpleResponse(new CRFPCommand(parent.sequenceMap, CRFiscalPrinterOperations.CMD_IMP_METHOD,data));
			//Envio seqMap la tabla de Impuestos
			parent.sequenceMap.setMetodoImpuesto(metodoImpuesto);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("abrirConexion() - end");
		}
	}

	/***************************************************************************
	 * Wait for a Fiscal Printer ready status. This method should be called
	 * every time the application is started.
	 **************************************************************************/
	private void waitForIplCompletion() {
		int completionCode; // Library operation error
		int timeoutRetry = 5; // Max. attempts to communicate Fiscal Printer
		long responseLength[] = new long[1]; // Store the Fiscal Unit response length
		boolean iplInProgress = false; // IPL condition flag
		byte[] responseBuffer = new byte[262]; // Fiscal Unit response buffer
		int timeOutRetryD= 100;
		do {// Read status from Fiscal Printer
			completionCode = printerHandle.JReadStatus(responseBuffer, responseLength);
			// Wait 100 milliseconds in order to avoid too many RC_232_NOTHING_RECV completion codes
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				if (logger.isDebugEnabled()) {
					logger.debug("Program timeout JCaller");
				}
			
			}
			// Show IPL in progress message once
//			if ((completionCode == GD4NativeInterfaceProxy.RC_232_IN_IPL) && !iplInProgress) {
			if ((completionCode == GD4NativeInterfaceProxy.RC_232_IN_IPL) ) {
				if(!iplInProgress)
					if (logger.isDebugEnabled()) {
						logger.debug("Inicializando impresora fiscal...");
					}
				iplInProgress = true;
				timeOutRetryD--;
				if(timeOutRetryD==0){ // Espero 10 segundos para Time Out.
					break;	
				}
			}

			if (completionCode == GD4NativeInterfaceProxy.RC_232_TIMEOUT) {
				timeoutRetry--;
				if (logger.isDebugEnabled()) {
					logger.debug("Communication error. Ensure Fiscal Printer is turned on and connected");				
				}
				//parent.status.setConnected(false);
				if (timeoutRetry == 0) {
					
					break;
				}
			}
			

		} while ((completionCode == GD4NativeInterfaceProxy.RC_232_PLD) || (completionCode == GD4NativeInterfaceProxy.RC_232_TIMEOUT) || (completionCode == GD4NativeInterfaceProxy.RC_232_IN_IPL));
		
		if (completionCode != GD4NativeInterfaceProxy.RC_232_OK) {
			if (logger.isDebugEnabled()) {
				logger.debug("Error 0x" + Integer.toHexString(completionCode) + " while read Fiscal Printer status");
			}
			printerHandle.JCloseFiscalPrinterComPort();
			
		}
		
		if (completionCode == GD4NativeInterfaceProxy.RC_232_OK) {
			if (logger.isDebugEnabled()) {
				logger.debug("Listo.");
			}
			//parent.status.setConnected(true);
		}
	}
	/**
	 * Cierra la conexión serial
	 */
	public void cerrarConexion(boolean iniciando){
		if (logger.isDebugEnabled()) {
			logger.debug("cerrarConexion() - start");
		}
		// Debe esperar que el motor no se encuentre procesando
		// Documento alguno.

		while (parent.status.isImprimiendo() || !documents.isEmpty()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error("cerrarConexion()", e);
			}
		}

		printerHandle.JCloseFiscalPrinterComPort();

		if (logger.isDebugEnabled()) {
			logger.debug("cerrarConexion() - end");
		}
	}
	
	/**
	 * Ejecuta el comando reset printer manualmente
	 * AGREGADO POR CENTROBECO BASADOS EN LO REALIZADO POR EPA
	 * EN EL PROYECTO desbloquearImpresoraGD4
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'HashMap'
	* Fecha: agosto 2011
	*/
	public void ejecutarReset(){
		HashMap<String,?> estatus = parent.operations.obtenerEstatusImpresora();
		
		int comando = Integer.parseInt(estatus.get("estado").toString());
		System.out.println("Comando recibido -> "+comando);
		try {
			System.out.println("Impresora S/N "+parent.operations.obtenerSerialImpresora()+" conectada");
			System.out.println("Hora impresora: "+parent.operations.obtenerHoraImpresoraFiscal()+" Fecha Impresora: "+parent.operations.obtenerFechaImpresoraFiscal());
			
			switch (comando) {
			case 0:
				//00 = ningún Procedimiento en progreso
				System.out.println("Impresora Lista");
				break;
			case 1:
				//01 = Transacción de Ventas en progreso
				System.out.println("Anulando Comprobante de Venta");
				
				System.out.println("Ultimo Paso registrado ->"+estatus.get("paso").toString());
				
				if (Integer.parseInt(estatus.get("paso").toString()) > 240){
					
					System.out.println("Finalizando Factura");
					parent.operations.crearDocumento(true);
					parent.operations.imprimirTextoFiscal("          FACTURA ANULADA",3);
					parent.operations.cerrarComprobanteFiscalVentas("A");
					
					try {
						parent.operations.cortarPapel();
						parent.operations.commit();
					} catch (PrinterNotConnectedException e) {
						
						//e.printStackTrace();
					}
				}else{
				
					try{
						System.out.println("Anulando Factura");
						parent.operations.cancelarComprobanteVentas();
					}catch(Exception e){
						//System.out.println(e);
					}
					try {
						parent.operations.cortarPapel();
						parent.operations.commit();
					} catch (PrinterNotConnectedException e) {
						
						//e.printStackTrace();
					}
					
				}
				break;
			case 2:
				//02 = Reservado
				break;
			case 3:
				//03 = Reservado
				break;
			case 4:
				//04 = Reservado
				break;
			case 5:
				//05 = Appl.-Reporte Orig. en RC en progreso
				System.out.println("Anulando Comprobante No Fiscal");
				try{
					
					parent.operations.crearDocumento(false);
					parent.operations.enviarLineaNoFiscal("DOCUMENTO NO FISCAL ANULADO", 1);
					parent.operations.cerrarDocumentoNoFiscal();	
				}catch(Exception e){
					System.out.println(e);
				}
				try {
					parent.operations.cortarPapel();
					parent.operations.commit();
				} catch (PrinterNotConnectedException e) {
					//e.printStackTrace();
				}
				break;
			case 6:
				//06 = Appl.-Reporte Orig. en RA en progreso
				break;
			case 7:
				//07 = Appl.-Reporte Orig. en DI Horizontal en progreso
				parent.operations.crearDocumento(false);
				parent.operations.cerrarDocumentoNoFiscal();
				break;
			case 8:
				//08 = Appl.-Reporte Orig. En DI Vertical en progreso
				parent.operations.crearDocumento(false);
				parent.operations.cerrarDocumentoNoFiscal();
				break;
			case 9:
				//09 = Cheque or Planilla de Crédito en Progreso
				try{
					
					//TODO: Hay que levantar una ventana en CR que indique que introduzca un papel en DI
					parent.operations.crearDocumento(true);
					parent.operations.cancelarCheque();
					parent.operations.cancelarComprobanteVentas();
					//crFiscalPrinterOperations.cancelarComprobanteVentas();
					
				}catch(Exception e){
					System.out.println(e);
				}
				try {
					parent.operations.cortarPapel();
					parent.operations.commit();
				} catch (PrinterNotConnectedException e) {
					
					//e.printStackTrace();
				}
				break;
			case 10:
				//0A = Nota de Crédito en progreso
				System.out.println("Anulando Comprobante de Nota de Credito");
				
				System.out.println("Ultimo Paso registrado ->"+estatus.get("paso").toString());
				
				try{
					parent.operations.cancelarComprobanteNotaCredito();
				}catch(Exception e){
					System.out.println(e);
				}
				try {
					parent.operations.cortarPapel();
					parent.operations.commit();
				} catch (PrinterNotConnectedException e) {
					
					//e.printStackTrace();
				}
				break;
			}
		} catch (PrinterNotConnectedException e) {
			//e.printStackTrace();
		}	
	}
}
