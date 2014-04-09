/*
 * $Id: CRFPEngine.java,v 1.2.2.9 2005/08/08 12:19:36 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CRFiscalPrinterDriver 1.1
 * Paquete		: com.epa.crprinterdriver
 * Programa		: CRFPEngine.java
 * Creado por	: Programa8 - Arístides Castillo Colmenárez
 * Creado en 	: 11-abr-2005 16:08:13
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
 * $Log: CRFPEngine.java,v $
 * Revision 1.2.2.9  2005/08/08 12:19:36  programa8
 * Registro en log de comando en ejecución ante la ocurrencia de errores
 *
 * Revision 1.2.2.8  2005/06/27 16:23:53  programa8
 * Eliminación de bug por bloqueo entre GUI  e impresora fiscal
 *
 * Revision 1.2.2.7  2005/06/21 17:40:02  programa8
 * Considerar si hay algun documento pendiente por imprimir antes de cerrar la conexión con la impresora
 *
 * Revision 1.2.2.6  2005/06/07 13:36:43  programa8
 * Eliminación de bug al imprimir comprobantes fiscales de manera seguida,
 * fallando al notificar solo el fin del primer comprobante
 *
 * Revision 1.2.2.5  2005/05/18 15:11:50  programa8
 * *- Ajuste de código para permitir trazabilidad en log
 * *- Espera por finalización de trabajos pendientes en motor antes de
 * cerrar conexión serial con dispositivo
 *
 * Revision 1.2.2.4  2005/05/09 14:28:25  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.3  2005/05/09 14:19:09  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.2  2005/05/06 18:17:03  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.2.2.1  2005/05/05 22:05:44  programa8
 * Versión 2.1:
 * *- Preparación para funcionamiento con EPSON TMU950PF
 * *- Toma en cuenta la solicitud de incremento en espera por timeout
 * *- Considera independencia de dispositivo en colchones de tiempo
 * *- Considera funciones que pueden estar implementadas en un dispositivo
 * no necesariamente implementadas en otro
 * *- Separa Subsistema base del driver de la fachada usadas por las
 * aplicaciones
 * *- Maneja estatus de activación del slip. Considera colchones de tiempo
 * para comando enviados al slip.
 * *- Patron de diseño Abstract Factory para la selección de la implementación
 * de dispositivo a partir de propiedad de la aplicación
 *
 * Revision 1.2  2005/05/05 12:08:47  programa8
 * Versión 2.0.1 Final
 *
 * Revision 1.1.2.9  2005/05/02 12:57:04  programa8
 * *- Mejora para ignorar errores
 * *- Workaround para bug de impresora fiscal: Error falso en impresión de
 * cheques
 *
 * Revision 1.1.2.8  2005/04/25 19:47:32  programa8
 * *- Resistencia a errores para la supervivencia del hilo del driver
 * *- Resistencia al compromiso de documentos nulos
 *
 * Revision 1.1.2.7  2005/04/21 22:01:02  programa8
 * - Identificación de hilo de motor.
 * - Ajuste en manejo de timeout y control de impresora ocupada
 *
 * Revision 1.1.2.6  2005/04/21 13:47:56  programa8
 * Driver Fiscal 2.0 RC1 - Al 21/04/2004
 *
 * Revision 1.1.2.5  2005/04/19 19:08:30  programa8
 * Driver Fiscal al 19/04/2005 - Primer día de pruebas
 *
 * Revision 1.1.2.4  2005/04/18 16:39:16  programa8
 * Driver Fiscal al 18/04/2005 - 12m
 *
 * Revision 1.1.2.3  2005/04/13 21:57:00  programa8
 * Driver fiscal al 13/04/2005
 *
 * Revision 1.1.2.2  2005/04/12 20:59:17  programa8
 * Driver fiscal al 12/04/05
 *
 * Revision 1.1.2.1  2005/04/11 20:59:07  programa8
 * CRFiscalPrinterDriver 2.0 - Analisis Preliminar
 *
 * ===========================================================================
 */
package com.epa.crprinterdriver;

import gnu.io.SerialPortEvent;

import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import com.epa.crprinterdriver.event.CRFPEvent;
import com.epa.crprinterdriver.event.CRFPResponseEvent;
import com.epa.crprinterdriver.event.FiscalPrinterListener;
import com.epa.crprinterdriver.event.ResponseListener;
import com.epa.crprinterdriver.event.StatusChangeListener;
import com.epa.crserialinterface.Connection;
import com.epa.crserialinterface.Parameters;
import com.epa.crserialinterface.SerialExceptions;
import com.epa.crserialinterface.SerialSignalListener;

/**
 * <p>
 * Objeto dedicado a la comunicación directa con el dispositivo fiscal. Es el 
 * motor principal del driver fiscal y el cerebro mismo del driver
 * </p>
 * <p>
 * <a href="CRFPEngine.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - Arístides Castillo Colmenárez - $Author: programa8 $
 * @version $Revision: 1.2.2.9 $ - $Date: 2005/08/08 12:19:36 $
 * @since 11-abr-2005
 * 
 */
public class NPF4610Engine implements DocumentListener, SerialSignalListener, Runnable, ResponseListener, StatusChangeListener, CRFPEngine {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(NPF4610Engine.class);
	
	// Constantes de motivos para toma de control por parte del motor
	protected static final int NUM_CIERRE_CF = 0;
	protected static final int REQUIERE_Z = 1;
	protected static final int RESUME_ERROR = 2;
	
	// Constantes de acción ante errores
	/**
	 *  Constante de acción ante situación de error: Aborta el documento actual 
	 */
	public static final int ABORTAR_DOCUMENTO = 0;
	/**
	 *  Constante de acción ante situación de error: Comienza nuevamente impresión de documento 
	 */
	public static final int REPETIR_DOCUMENTO = 1;
	
	/**
	 *  Constante de acción ante situación de error: Repite el comando actual 
	 */
	public static final int REPETIR_COMANDO = 2;

	/**
	 *  Constante de acción ante situación de error:  Espera la confirmación de reintento por parte de la aplicación
	 */
	public static final int ESPERAR_APLICACION = 3;

	/**
	 *  Constante de acción ante situación de error: Realiza el reporte Z requerido 
	 */
	public static final int REALIZAR_Z = 4;

	/**
	 *  Constante de acción ante situación de error: Cierra el comprobante fiscal en proceso 
	 */
	public static final int CERRAR_CF = 5;

	/**
	 *  Constante de acción ante situación de error: Ignora el error y continua la impresión del documento 
	 */
	public static final int IGNORAR_ERROR = 6;
	
	/*
	 * Constante de tiempo de espera en milisegundos antes de declarar timeout
	 */
	
	protected Connection serialConnection;
    private JTextArea inputText;
    private JTextArea outputText;
    private CRFPSubsystem parent;
    private Vector<CRFPDocument> documents = new Vector<CRFPDocument>();
    private Thread sender = null;
    private boolean pausado = false;
    private boolean controlMotor = false;
    
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
	public NPF4610Engine(CRFPSubsystem parent, Parameters serialParameters) {
		super();
		this.parent = parent;
        /* Instanciemos la interfaz de la impresora */
        inputText = new JTextArea();
        outputText = new JTextArea();
		serialConnection = new Connection(serialParameters, outputText, inputText);
		serialConnection.setTamanoTrama(1);
		serialConnection.setFlushAtEnd(true);
		// Registrarse como escucha de eventos del driver e impresora
		inputText.getDocument().addDocumentListener(this);
		serialConnection.addSerialSignalListener(this);
		parent.responseParser.addResponseListener(this);
		parent.status.addStatusChangeListener(this);
		
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

		serialConnection.removeSerialSignalListener(this);
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
			parent.responseParser.constructResponse(inputText.getText().charAt(0));
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
	public void run() {
		if (logger.isDebugEnabled()) {
			logger.debug("run() - start");
		}

		/*
		 *  Hilo de envío automático de comandos.
		 *  Al recibir un documento comienza el envío de sus comandos
		 */
		CRFPCommand comandoMotor = null;
		int razonCtrlMotor = -1;
		int enviosMotor = 0;
		boolean notificarOK = false;
		boolean compFiscalRegistrado = false;
		CRFPDocument documentoActual = null;
		int numCompF = 0;
		int idDoc = 0;
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
				// Tenemos pendientes una solicitud de información
				// ó un documento por imprimir.
				documentoActual = obtenerProximoDoc();
				notificarOK = false;
				compFiscalRegistrado = false;
				numCompF = 0;
//				documentosDisponibles:
				while (documentoActual != null || solicitudInfo != null) {
					// Por cada documento
					int i = -1;
					if (documentoActual != null) {
						i = 0;
					}
					documento :
					while ((i >= 0 && i < documentoActual.size()) || solicitudInfo != null) {
						// Por cada comando del documento actual
						CRFPCommand comandoActual = null;
						if (controlMotor) {
							// El motor necesita enviar un comando por su cuenta
							// lo hacemos
							comandoActual = comandoMotor;
							enviosMotor++;
						} else {
							if (solicitudInfo != null) {
								// Enviamos el comando de solicitud de información
								comandoActual = solicitudInfo;
							}  else {
								// Proximo comando del documento actual
								comandoActual = documentoActual.get(i);
							}
						}
						// Actualizamos status
						respuesta.setLlego(false);
						parent.status.setTipoRespEsperada(comandoActual.getTipoRespEsperada());
						parent.status.setUltCommandTypeEnviado(comandoActual.getType());
						parent.status.setUltIDEnviado(comandoActual.getSequenceId());
//						reenvioComando :
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
								} else {
									if (respuesta.isSeguirEsperando()) {
										respuesta.setSeguirEsperando(false);
										Thread.sleep(parent.sequenceMap.getExtraTimetoSleep());
									} else {
										if (new Date().getTime() - timeInicio > parent.sequenceMap.getEsperaTimeout()) {
										    logger.warn("run() - El tiempo esperando que la impresora " +
										    		"se desocupe ha sobrepasado el limite: "+ 
										    		parent.sequenceMap.getEsperaTimeout() + " ms");
											break;
										}
									}
								}
							}
							parent.status.setImprimiendo(true);
							enviarSecuenciaEscape(comandoActual.getSecuenciaEscape());
							if (logger.isInfoEnabled()) {
							    logger.info("run() - Comando enviado a la impresora: " + comandoActual + ", envios anteriores = " + reenvios);
							}
							// Aplicamos la espera correspondiente
							comandoActual.esperar();
							if (comandoActual.getType() != 
								CRFiscalPrinterOperations.CMD_RESET_PRINTER) {
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
									switch (respuesta.getEvento().getResponseType()) { 
										case CRFPResponseEvent.RESPONSE_INVALID :
										    logger.warn("run() - Se recibió una respuesta inválida de la impresora");
											if (controlMotor) {
												// Intentamos continuar el curso normal de impresión
												switch(razonCtrlMotor) {
													case NUM_CIERRE_CF :
														if (enviosMotor > 2) {
															notifyOKCF(idDoc, -1);
															controlMotor = false;
															comandoMotor = null;
															razonCtrlMotor = -1;
															enviosMotor = 0;
														}
														break;
													default :
														controlMotor = false;
														comandoMotor = null;
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
											logger.warn("run() - Error ocurrido - Código: "+ 
													respuesta.getEvento().getErrorCode() + 
													" - " + 
													parent.sequenceMap.getErrorMessage(
															respuesta.getEvento().getErrorCode())
													, null);
											if (controlMotor) {
												// Recuperamos el estado de la aplicación
												switch(razonCtrlMotor) {
												case NUM_CIERRE_CF :
													if (enviosMotor > 2) {
														notifyOKCF(idDoc, -1);
														controlMotor = false;
														comandoMotor = null;
														razonCtrlMotor = -1;
														enviosMotor = 0;
													}
													break;
												default :
													controlMotor = false;
													comandoMotor = null;
													razonCtrlMotor = -1;
													enviosMotor = 0;
													break;
												}
											} else {
												int accion = parent.sequenceMap.getActionOnError(
														respuesta.getEvento().getErrorCode());
												if (accion == IGNORAR_ERROR) {
													if (solicitudInfo != null || 
															parent.status.getTipoRespEsperada() 
															!= CRFPStatus.DATA_NONE) {
														// No se debe ignorar el error si alguien espera respuesta
														logger
														.error(
																"run() - No puede ignorarse el error si se espera respuesta - " +
																"Se abortará el documento. Comando: " + comandoActual,
																null);
														accion = ABORTAR_DOCUMENTO;
													} else {
														// Documento sin respuesta, continuamos
														i++;
														logger
														.warn(
																"run() - Error ignorado - " +
																" Se continua impresión de documento",
																null);
													}
												}
												switch (accion) {
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
															logger
															.warn(
																	"run() - Maximo de Lineas de Texto Fiscal Alcanzado - " +
																	" Se saltó al siguiente tipo de comando",
																	null);
															
														} else {
															// ** Abortamos el documento
															// Registramos log de abortado de documento

																logger
																		.error(
																				"run() - Documento abortado " + 
																				" Error: " + 
																				respuesta.getEvento().getErrorCode() + 
																				" - " + 
																				parent.sequenceMap.getErrorMessage(
																						respuesta.getEvento().getErrorCode()),
																				null);

															// Enviamos notificación de aborto de documento
															doEventOccured(new CRFPEvent(
																	CRFPEvent.ERROR_CRITICO, 
																	documentoActual.getId(), 
																	documentoActual.isFiscal(), 
																	numCompF, 
																	respuesta.getEvento().
																		getErrorCode(), 
																	parent.sequenceMap.
																		getErrorMessage(
																			respuesta.getEvento().
																				getErrorCode()), 
																	true));
															// Tomamos control y reiniciamos la impresora
															controlMotor = true;
															enviosMotor = 0;
															comandoMotor = new CRFPCommand(
																	parent.sequenceMap, 
																	CRFiscalPrinterOperations.CMD_RESET_PRINTER);
															razonCtrlMotor = RESUME_ERROR;
														}
														break;
													case REALIZAR_Z :
														// Notificamos la impresión de un reporte Z
														doEventOccured(new CRFPEvent(CRFPEvent.REQUIERE_Z, 
																documentoActual.getId(), 
																documentoActual.isFiscal(), 
																numCompF));
														// Registramos en log la impresión en demanda del Z
														logger
														.warn(
																"run() - Impresora requiere Z - " +
																" Se realizó la impresión del mismo",
																null);
														
														// Enviamos el comando de impresión Z
														controlMotor = true;
														enviosMotor = 0;
														comandoMotor = new CRFPCommand(parent.sequenceMap, 
																CRFiscalPrinterOperations.CMD_REPORTE_Z);
														razonCtrlMotor = REQUIERE_Z;
														break;
													case ABORTAR_DOCUMENTO :
														if (solicitudInfo == null) {
															// ** Abortamos el documento
															// Registramos log de abortado de documento
															logger
															.error(
																	"run() - Documento abortado. Comando: " + comandoActual + 
																	" Error: " + 
																	respuesta.getEvento().getErrorCode() + 
																	" - " + 
																	parent.sequenceMap.getErrorMessage(
																			respuesta.getEvento().getErrorCode()),
																	null);
															// Enviamos notificación de aborto de documento
															if (parent.status.getTipoRespEsperada() != CRFPStatus.DATA_NONE) {
																synchronized(documentoActual) {
																	documentoActual.notify();
																}
															} else {
																doEventOccured(new CRFPEvent(
																		CRFPEvent.ERROR_CRITICO, 
																		documentoActual.getId(), 
																		documentoActual.isFiscal(), 
																		numCompF, 
																		respuesta.getEvento().
																			getErrorCode(), 
																		parent.sequenceMap.
																			getErrorMessage(
																				respuesta.getEvento().
																					getErrorCode()), 
																		true));
															}
															// Tomamos control y reiniciamos la impresora
															controlMotor = true;
															enviosMotor = 0;
															razonCtrlMotor = RESUME_ERROR;
															comandoMotor = new CRFPCommand(
																	parent.sequenceMap, 
																	CRFiscalPrinterOperations.CMD_RESET_PRINTER);
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
															
														} else {
															// Solicitamos intervención del usuario
															CRFPEvent event = new CRFPEvent(
																	CRFPEvent.ERROR_ATENCION_USUARIO, 
																	documentoActual.getId(), 
																	documentoActual.isFiscal(), numCompF ,respuesta.getEvento().
																	getErrorCode(), 
																	parent.sequenceMap.
																		getErrorMessage(
																			respuesta.getEvento().
																				getErrorCode()));
															doEventOccured(event);
															if (event.isReintentar()) {
																reenvios = 0;
															} else {
																// Abortamos documento
																logger
																.error(
																		"run() - Documento abortado por timeout ",
																		null);
																// Enviamos notificación de aborto de documento
																doEventOccured(new CRFPEvent(
																		CRFPEvent.ERROR_CRITICO, 
																		documentoActual.getId(), 
																		documentoActual.isFiscal(), 
																		numCompF, 
																		respuesta.getEvento().
																		getErrorCode(), 
																	parent.sequenceMap.
																		getErrorMessage(
																			respuesta.getEvento().
																				getErrorCode()),
																		true));
																// Tomamos control y reiniciamos la impresora
																controlMotor = true;
																razonCtrlMotor = RESUME_ERROR;
																enviosMotor = 0;
																comandoMotor = new CRFPCommand(
																		parent.sequenceMap, 
																		CRFiscalPrinterOperations.CMD_RESET_PRINTER);
															}
															
														}
														
														break;
													case REPETIR_DOCUMENTO :
														if (solicitudInfo == null)
															i = 0;
														break;
												}
											}
											notificarOK = false;
											break;
										case CRFPResponseEvent.RESPONSE_DATA :
											if (controlMotor) {
												// Llego la respuesta que 
												// estabamos esperando. Actuar
												switch(razonCtrlMotor) {
													case NUM_CIERRE_CF :
														notifyOKCF(idDoc, 
																parent.status.
																getNumComprobanteFiscal());
														break;
												}
												
												controlMotor = false;
												comandoMotor = null;
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
											if (comandoActual.getType() == CRFiscalPrinterOperations.CMD_CERRAR_CF) {
											    compFiscalRegistrado = false;
											}
											notificarOK = false;
											break;
										case CRFPResponseEvent.RESPONSE_OK :
											// Enviar proximo comando
											i++;
											notificarOK = true;
											controlMotor = false;
											comandoMotor = null;
											enviosMotor = 0;
											razonCtrlMotor = -1; 
											if (comandoActual.getType() == CRFiscalPrinterOperations.CMD_CERRAR_CF) {
												/* Recibido el cierre parcial de una factura
												 * Es decir, ya esta registrada en la memoria fiscal.
												 * Notificar a la aplicación
												 */
												// Tomamos el control y solicitamos el numero de comprobante fiscal
												if (!compFiscalRegistrado) {
													controlMotor = true;
													enviosMotor = 0;
													razonCtrlMotor = NUM_CIERRE_CF;
													comandoMotor = parent.operations.getComandoEstatus(
															CRFiscalPrinterOperations.ESTADO_CONTADORES);
													idDoc = documentoActual.getId();
												} 
												compFiscalRegistrado = true;
											}
											break;
									}
									break;
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
											comandoMotor = null;
											razonCtrlMotor = -1;
										} else {
											if (solicitudInfo != null) {
												synchronized(solicitudInfo) {
													infoRetornada = false;
													solicitudInfo.notify();
													solicitudInfo = null;
												}
											} else {
												// Solicitamos intervención del usuario
												CRFPEvent event = new CRFPEvent(
														CRFPEvent.ERROR_ATENCION_USUARIO, 
														documentoActual.getId(), 
														documentoActual.isFiscal(), numCompF);
												doEventOccured(event);
												if (event.isReintentar()) {
													reenvios = 0;
												} else {
													// Abortamos documento
													logger
													.error(
															"run() - Documento abortado por timeout ",
															null);
													// Enviamos notificación de aborto de documento
													doEventOccured(new CRFPEvent(
															CRFPEvent.ERROR_CRITICO, 
															documentoActual.getId(), 
															documentoActual.isFiscal(), 
															numCompF, 
															-1,
															"",
															true));
													// Tomamos control y reiniciamos la impresora
													controlMotor = true;
													razonCtrlMotor = RESUME_ERROR;
													enviosMotor = 0;
													comandoMotor = new CRFPCommand(
															parent.sequenceMap, 
															CRFiscalPrinterOperations.CMD_RESET_PRINTER);
												}
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
									/* Devolvemos el control a los comandos enviados
									 * por la aplicacion
									 */
									controlMotor = false;
									enviosMotor = 0;
									comandoMotor = null;
									razonCtrlMotor = -1;
									break documento;
								} else {
									i++;
									break;
								}
							}
						}
					}
					// Se acabo con el documento anterior
					if (notificarOK) {
						// Debemos crear el evento de notificación de
						// que el documento se imprimió correctamente
						doEventOccured(new CRFPEvent(CRFPEvent.IMPRESION_OK, 
								documentoActual.getId(), 
								documentoActual.isFiscal(), 
								numCompF));
					}
					documents.remove(documentoActual);
					documentoActual = obtenerProximoDoc();
				}
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
				comandoMotor = null;
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
				doEventOccured(new CRFPEvent(CRFPEvent.IMPRESION_OK, 
						idDoc, 
						true, 
						numCF));
			}
		};
		new Thread(r, "Notificación de impresión OK").start();
	}
	
	private void enviarSecuenciaEscape(String secuencia) {
		if (logger.isDebugEnabled()) {
			logger.debug("enviarSecuenciaEscape(String) - start");
		}

		outputText.setText(secuencia);
		serialConnection.sendOutputBuffer(); 		

		if (logger.isDebugEnabled()) {
			logger.debug("enviarSecuenciaEscape(String) - end");
		}
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
		return serialConnection;
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
				logger
						.error(
								"getComplexResponse() - No pudo obtenerse la información esperada",
								null);

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
	 *
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
			logger
					.debug("addFiscalPrinterListener(FiscalPrinterListener) - start");
		}

		fiscalPrnListeners.add(listener);

		if (logger.isDebugEnabled()) {
			logger
					.debug("addFiscalPrinterListener(FiscalPrinterListener) - end");
		}
	}
	
	/**
	 * Permite remover objetos de escucha de eventos de la impresora fiscal
	 * @param listener Objeto a remover de la lista de escuchas de eventos
	 */
	public void removeFiscalPrinterListener(FiscalPrinterListener listener) {
		if (logger.isDebugEnabled()) {
			logger
					.debug("removeFiscalPrinterListener(FiscalPrinterListener) - start");
		}

		fiscalPrnListeners.remove(listener);

		if (logger.isDebugEnabled()) {
			logger
					.debug("removeFiscalPrinterListener(FiscalPrinterListener) - end");
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
	 *
	 */
	public void abrirConexion() {
		if (logger.isDebugEnabled()) {
			logger.debug("abrirConexion() - start");
		}

        try {
            serialConnection.openConnection();
        } catch (SerialExceptions e) {
			logger.error("abrirConexion()", e);
        }		

		if (logger.isDebugEnabled()) {
			logger.debug("abrirConexion() - end");
		}
	}
	
	/**
	 * Cierra la conexión serial
	 *
	 */
	public void cerrarConexion(boolean iniciando){
		if (logger.isDebugEnabled()) {
			logger.debug("cerrarConexion() - start");
		}
		// Debe esperar que el motor no se encuentre procesando
		// Documento alguno.
		long inicio = new Date().getTime();
		while (parent.status.isImprimiendo() || !documents.isEmpty()) {
		    try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("cerrarConexion()", e);
            }
            if (!iniciando) {
                if (new Date().getTime() - inicio >  5000) {
                    throw new RuntimeException("No se puede cerrar la conexion");
                }
            }
		}
		serialConnection.closeConnection();

		if (logger.isDebugEnabled()) {
			logger.debug("cerrarConexion() - end");
		}
	}
	
	/**
	 * Ejecuta el comando resetPrinter
	 */
	public void ejecutarReset(){
		//ESTE ERA EL DEFAULT QUE ESTABA ANTES EN CRFISCALPRINTEROPERATIONS
		CRFPCommand cmd = new CRFPCommand(parent.sequenceMap, CRFiscalPrinterOperations.CMD_RESET_PRINTER);
		parent.operations.getActiveDocument().add(cmd);
		if (parent.contarPapel) {
			parent.contador.consumirPapelTicket("IF_INICIALIZAR");
			parent.contador.consumirPapelAuditoria("IF_INICIALIZAR");
		}
		try {
			parent.operations.commit();
		} catch (PrinterNotConnectedException e) {
			logger.error("resetPrinter()", e);
		}
	}
}
