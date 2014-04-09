/*
 * $Id: CRFPStatus.java,v 1.2.2.5 2005/06/21 17:39:20 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CRFiscalPrinterDriver 1.1
 * Paquete		: com.epa.crprinterdriver
 * Programa		: CRFPStatus.java
 * Creado por	: Programa8 - Arístides Castillo Colmenárez
 * Creado en 	: 11-abr-2005 14:05:51
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
 * $Log: CRFPStatus.java,v $
 * Revision 1.2.2.5  2005/06/21 17:39:20  programa8
 * Ajuste en declaración de volatilidad del estatus de impresión del driver
 *
 * Revision 1.2.2.4  2005/05/09 14:28:26  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.3  2005/05/09 14:19:07  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.2  2005/05/06 18:17:04  programa8
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
 * Revision 1.2  2005/05/05 12:08:48  programa8
 * Versión 2.0.1 Final
 *
 * Revision 1.1.2.6  2005/04/21 13:47:57  programa8
 * Driver Fiscal 2.0 RC1 - Al 21/04/2004
 *
 * Revision 1.1.2.5  2005/04/19 19:08:30  programa8
 * Driver Fiscal al 19/04/2005 - Primer día de pruebas
 *
 * Revision 1.1.2.4  2005/04/18 16:39:22  programa8
 * Driver Fiscal al 18/04/2005 - 12m
 *
 * Revision 1.1.2.3  2005/04/13 21:57:03  programa8
 * Driver fiscal al 13/04/2005
 *
 * Revision 1.1.2.2  2005/04/12 20:59:17  programa8
 * Driver fiscal al 12/04/05
 *
 * Revision 1.1.2.1  2005/04/11 20:59:10  programa8
 * CRFiscalPrinterDriver 2.0 - Analisis Preliminar
 *
 * ===========================================================================
 */
package com.epa.crprinterdriver;

import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.Vector;

import com.epa.crprinterdriver.event.StatusChangeListener;

/**
 * 
 * <p>
 * 	Objeto que guarda el estatus actual del driver fiscal. Este objeto es 
 * actualizado por cada distinto comando ejecutado sobre el dispositivo de 
 * impresión fiscal
 * </p>
 * <p>
 * <a href="CRFPStatus.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - Arístides Castillo Colmenárez - $Author: programa8 $
 * @version $Revision: 1.2.2.5 $ - $Date: 2005/06/21 17:39:20 $
 * @since 11-abr-2005
 * 
 */
public class CRFPStatus {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CRFPStatus.class);

	// Constantes de parametros de estatus para notificaciones
	/**
	 * Constante de notificacion de cambio en status: Hay Papel 
	 */
	public final static int PARAM_HAY_PAPEL = 0;
	/**
	 * Constante de notificacion de cambio en status: Desbordamiento de totales 
	 */
	public final static int PARAM_DESBORDAMIENTO_TOTALES = 1;
	/**
	 * Constante de notificacion de cambio en status: Memoria fiscal llena 
	 */
	public final static int PARAM_MEM_FISCAL_LLENA = 2;
	/**
	 * Constante de notificacion de cambio en status: Alcanzado tope de Items en CF 
	 */
	public final static int PARAM_MAX_ITEMS_CF = 3;
	/**
	 * Constante de notificacion de cambio en status: Error en Impresora 
	 */
	public final static int PARAM_ERROR_IMPRESORA = 4;
	/**
	 * Constante de notificacion de cambio en status: Aumento en tiempo de espera solicitado 
	 */
	public final static int PARAM_AUMENTAR_TIMEOUT = 5;
	/**
	 * Constante de notificacion de cambio en status: Impresora Ocupada 
	 */
	public final static int PARAM_IMPRESORA_OCUPADA = 6;

	// Constantes de estados para contador de papel
	/**
	 *  Estado fiscal en documento en buffer: Iniciado - Abierto
	 */
	public static final char EDO_FISCAL_INICIADO = 'I';
	/**
	 *  Estado fiscal en documento en buffer: Primer cierre realizado
	 */
	public static final char EDO_FISCAL_PARCIAL = 'P';
	/**
	 *  Estado fiscal en documento en buffer: Cierre completo - No hay documento fiscal abierto
	 */
	public static final char EDO_FISCAL_LISTO = 'L';
	
	// Constantes de tipos de respuestas esperadas
	/**
	 * Tipo de respuesta esperada: Ninguna
	 */
	public static final int DATA_NONE = 0;
	/**
	 * Tipo de respuesta esperada: Estado de impresora - Contadores
	 */
	public static final int DATA_STATUS_N = 1;
	/**
	 * Tipo de respuesta esperada: Estado de impresora - Ventas Exentas 
	 */
	public static final int DATA_STATUS_E = 2;

	/**
	 * Tipo de respuesta esperada: Estado de impresora - Totales 
	 */
	public static final int DATA_STATUS = 3;

	/**
	 * Tipo de respuesta esperada: Reporte Z 
	 */
	public static final int DATA_REP_Z = 4;

	/**
	 * Tipo de respuesta esperada: Reporte de Memoria Fiscal 
	 */
	public static final int DATA_REP_MF = 5;
	
	/**
	 * Tipo de respuesta esperada: Reportes Z por rango 
	 */
	public static final int DATA_RANGO_Z = 6;

	/**
	 * Tipo de respuesta esperada: Item fiscal 
	 */
	public static final int DATA_ITEM = 7;

	/**
	 * Tipo de respuesta esperada: Subtotal de Comprobante Fiscal 
	 */
	public static final int DATA_SUBTOTAL = 8;
	
	/**
	 * Tipo de respuesta esperada: Cierre de Comprobante Fiscal  
	 */
	public static final int DATA_CIERRE_CF = 9;
	/**
	 * Tipo de respuesta esperada: Fecha y hora de impresora fiscal 
	 */
	public static final int DATA_FECHAHORA = 10;
	/**
	 * Tipo de respuesta esperada: Serial de impresora fiscal 
	 */
	public static final int DATA_SERIAL = 11;
	/**
	 * Comentario para <code>NO_ENVIAR_COMANDO</code> * Indica al Motor que no debe enviar la secuencia de escape. 
	 */
	public static final int NO_ENVIAR_COMANDO = 12;
	/**
	 * Tipo de respuesta esperada: Serial de impresora fiscal 
	 */
	public static final int DATA_TABLA_IMP = 13;
	/**
	 * Tipo de respuesta esperada: Totales Diarios  
	 */
	public static final int DATA_TOTAL_DIARIO= 14;
	
	/**
	 * Tipo de Respuesta esperada: Método de Impuesto
	 */
	public static final int DATA_IMP_METHOD = 15;
	
	
	//Estados de placa fiscal
	private int numComprobanteFiscal;
	private String serialImpresoraFiscal;
	private String ultIDSequence = "0";
	private String ultIDEnviado = "0";
	private int ultCommandTypeEnviado;
	private int ultCommandTypeEjecutado;
	private boolean requiereZ;
	private boolean totalesDesbordados;
	private boolean memFiscalLlena;
	private boolean numMaxItems;
	
	// Estados de impresora
	private boolean hayPapel = true;
	private boolean impresoraOcupada;
	private boolean errorImpresora;
	private boolean slipActivo = false;
	// Valores crudos de estado de impresora fiscal
	private int edoFiscal;
	private int edoImpresora;
	
	// Estados de driver (Contador de papel)
	private boolean documentoFiscalAbierto = false;
	private boolean documentoAbierto = false;
	private Vector<StatusChangeListener> changeListeners = new Vector<StatusChangeListener>();
	private boolean impresionTicket = true;
	private char estadoFiscal = EDO_FISCAL_LISTO;
	private int numLineaNF = 0;
	private boolean primeraLineaCF = true;
	private boolean ultDocFiscal = false;
	
	// Estado de driver
	private volatile boolean imprimiendo;
	private boolean aumentarTimeout;
	private Object dataRespuesta;
	private int tipoRespEsperada;

	//CENTROBECO-AMAND: Indica el tipo del ultimo documento fiscal impreso
	private String tipoUltimoDocumentoImpreso = "";
	
	/**
	 * Constructor por defecto del status de la impresora fiscal
	 * @since 11-abr-2005
	 * 
	 */
	public CRFPStatus() {
		super();
	}
	
	/**
	 * Premite agregar un objeto de escucha de cambios en el estatus del driver
	 * fiscal. Solo aplica para los parámetros establecidos en las constantes
	 * PARAM_XXXX
	 * 
	 * @param listener Objeto de escucha de eventos
	 */
	public void addStatusChangeListener(StatusChangeListener listener){
		if (logger.isDebugEnabled()) {
			logger
					.debug("addStatusChangeListener(StatusChangeListener) - start");
		}

		changeListeners.add(listener);

		if (logger.isDebugEnabled()) {
			logger.debug("addStatusChangeListener(StatusChangeListener) - end");
		}
	}
	
	/**
	 * Remueve un objeto de escucha de cambio en el estatus del driver 
	 * @param listener Objeto a remover
	 */
	public void removeStatusChangeListener(StatusChangeListener listener) {
		if (logger.isDebugEnabled()) {
			logger
					.debug("removeStatusChangeListener(StatusChangeListener) - start");
		}

		changeListeners.remove(listener);

		if (logger.isDebugEnabled()) {
			logger
					.debug("removeStatusChangeListener(StatusChangeListener) - end");
		}
	}
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Iterator'
	* Fecha: agosto 2011
	*/
	protected void doStatusChanged(int paramChanged) {
		if (logger.isDebugEnabled()) {
			logger.debug("doStatusChanged(int) - start");
		}

		for (Iterator<StatusChangeListener> i = changeListeners.iterator(); i.hasNext();) {
			((StatusChangeListener)i.next()).changedStatus(paramChanged);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("doStatusChanged(int) - end");
		}
	}

	/**
	 * Indica si hay un documento fiscal abierto. 
	 * Este estado aplica para el momento de formación en el buffer
	 * @return Verdadero si hay un documento fiscal en el buffer.
	 */
	public boolean isDocumentoFiscalAbierto() {
		if (logger.isDebugEnabled()) {
			logger.debug("isDocumentoFiscalAbierto() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isDocumentoFiscalAbierto() - end");
		}
		return documentoFiscalAbierto;
	}
	/**
	 * Establece que hay un documento fiscal abierto.
	 * Este estado aplica para el momento de formación en el buffer
	 * @param open Verdadero si hay un documento fiscal en creación en el buffer
	 */
	public void setDocumentoFiscalAbierto(boolean open) {
		if (logger.isDebugEnabled()) {
			logger.debug("setDocumentoFiscalAbierto(boolean) - start");
		}

		this.documentoFiscalAbierto = open;

		if (logger.isDebugEnabled()) {
			logger.debug("setDocumentoFiscalAbierto(boolean) - end");
		}
	}
	/**
	 * Retorna el estado de disponibilidad del papel en la impresora fiscal
	 * Este estado es activado por la placa fiscal, sin embargo en algunas implementaciones
	 * el mismo no indica efectivamente la falta de papel
	 * @return Devuelve hayPapel.
	 */
	public boolean isHayPapel() {
		if (logger.isDebugEnabled()) {
			logger.debug("isHayPapel() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isHayPapel() - end");
		}
		return hayPapel;
	}
	/**
	 * Establece que hay o no papel en la impresora
	 * @param hayPapel El hayPapel a establecer.
	 */
	public void setHayPapel(boolean hayPapel) {
		if (logger.isDebugEnabled()) {
			logger.debug("setHayPapel(boolean) - start");
		}

		if (hayPapel != this.hayPapel) {
			this.hayPapel = hayPapel;
			doStatusChanged(PARAM_HAY_PAPEL);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setHayPapel(boolean) - end");
		}
	}
	/**
	 * Indica que la impresora se encuentra ocupada y no puede recibir 
	 * comandos en este momento
	 * @return Devuelve impresoraOcupada.
	 */
	public boolean isImpresoraOcupada() {
		if (logger.isDebugEnabled()) {
			logger.debug("isImpresoraOcupada() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isImpresoraOcupada() - end");
		}
		return impresoraOcupada;
	}
	/**
	 * Establece que la impresora se encuentra ocupada y no puede
	 * recibir datos en este momento
	 * @param impresoraOcupada El impresoraOcupada a establecer.
	 */
	public void setImpresoraOcupada(boolean impresoraOcupada) {
		if (logger.isDebugEnabled()) {
			logger.debug("setImpresoraOcupada(boolean) - start");
		}

		if (impresoraOcupada != this.impresoraOcupada) {
			this.impresoraOcupada = impresoraOcupada;
			doStatusChanged(PARAM_IMPRESORA_OCUPADA);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setImpresoraOcupada(boolean) - end");
		}
	}
	/**
	 * Devuelve el ultimo numero de comprobante fiscal
	 * @return Devuelve numComprobanteFiscal.
	 */
	public int getNumComprobanteFiscal() {
		if (logger.isDebugEnabled()) {
			logger.debug("getNumComprobanteFiscal() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getNumComprobanteFiscal() - end");
		}
		return numComprobanteFiscal;
	}
	/**
	 * Establece el ultimo numero de comprobante fiscal
	 * @param numComprobanteFiscal El numComprobanteFiscal a establecer.
	 */
	public void setNumComprobanteFiscal(int numComprobanteFiscal) {
		if (logger.isDebugEnabled()) {
			logger.debug("setNumComprobanteFiscal(int) - start");
		}

		this.numComprobanteFiscal = numComprobanteFiscal;

		if (logger.isDebugEnabled()) {
			logger.debug("setNumComprobanteFiscal(int) - end");
		}
	}
	/**
	 * Indica si la impresora requiere que se realice un reporte Z
	 * @return Devuelve requiereZ.
	 */
	public boolean isRequiereZ() {
		if (logger.isDebugEnabled()) {
			logger.debug("isRequiereZ() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isRequiereZ() - end");
		}
		return requiereZ;
	}
	/**
	 * Activa el estado indicando que la impresora requiere que se realice
	 * un reporte Z
	 * @param requiereZ El requiereZ a establecer.
	 */
	public void setRequiereZ(boolean requiereZ) {
		if (logger.isDebugEnabled()) {
			logger.debug("setRequiereZ(boolean) - start");
		}

		this.requiereZ = requiereZ;

		if (logger.isDebugEnabled()) {
			logger.debug("setRequiereZ(boolean) - end");
		}
	}
	/**
	 * Devuelve el ultimo tipo de comando enviado a la impresora
	 * @return Devuelve ultCommandType.
	 */
	public int getUltCommandTypeEnviado() {
		if (logger.isDebugEnabled()) {
			logger.debug("getUltCommandTypeEnviado() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getUltCommandTypeEnviado() - end");
		}
		return ultCommandTypeEnviado;
	}
	/**
	 * Establece el ultimo tipo de comando enviado a la impresora
	 * @param ultCommandType El ultCommandType a establecer.
	 */
	public void setUltCommandTypeEnviado(int ultCommandType) {
		if (logger.isDebugEnabled()) {
			logger.debug("setUltCommandTypeEnviado(int) - start");
		}

		this.ultCommandTypeEnviado = ultCommandType;

		if (logger.isDebugEnabled()) {
			logger.debug("setUltCommandTypeEnviado(int) - end");
		}
	}
	/**
	 * Devuelve el ultimo numero de secuencia usado en la formación de comando
	 * @return Devuelve ultIDSequence.
	 */
	public String getUltIDSequence() {
		if (logger.isDebugEnabled()) {
			logger.debug("getUltIDSequence() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getUltIDSequence() - end");
		}
		return ultIDSequence;
	}
	/**
	 * Establece el ultimo numero de secuencia usado en la formación de comandos
	 * @param ultIDSequence El ultIDSequence a establecer.
	 */
	public void setUltIDSequence(String ultIDSequence) {
		if (logger.isDebugEnabled()) {
			logger.debug("setUltIDSequence(String) - start");
		}

		this.ultIDSequence = ultIDSequence;

		if (logger.isDebugEnabled()) {
			logger.debug("setUltIDSequence(String) - end");
		}
	}
	/**
	 * Indica si la impresora fiscal envi&oacute; la solicitud de aumentar el
	 * tiempo de espera por una respuesta de ella 
	 * @return Devuelve aumentarTimeout.
	 */
	public boolean isAumentarTimeout() {
		if (logger.isDebugEnabled()) {
			logger.debug("isAumentarTimeout() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isAumentarTimeout() - end");
		}
		return aumentarTimeout;
	}
	/**
	 * Establece el estado indicando que la impresora solicit&oacute; un mayor
	 * tiempo de espera por la respuesta de la misma
	 * @param aumentarTimeout El aumentarTimeout a establecer.
	 */
	public void setAumentarTimeout(boolean aumentarTimeout) {
		if (logger.isDebugEnabled()) {
			logger.debug("setAumentarTimeout(boolean) - start");
		}

		this.aumentarTimeout = aumentarTimeout;
		if (aumentarTimeout) {
			doStatusChanged(PARAM_AUMENTAR_TIMEOUT);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setAumentarTimeout(boolean) - end");
		}
	}
	/**
	 * Retorna el objeto resultado de la respuesta de la impresora fiscal,
	 * luego de la ejecucion de un comando
	 * @return Devuelve dataRespuesta.
	 */
	public Object getDataRespuesta() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDataRespuesta() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDataRespuesta() - end");
		}
		return dataRespuesta;
	}
	/**
	 * Establece el objeto resultado de la respuesta de la impresora fiscal
	 * @param dataRespuesta El dataRespuesta a establecer.
	 */
	public void setDataRespuesta(Object dataRespuesta) {
		if (logger.isDebugEnabled()) {
			logger.debug("setDataRespuesta(Object) - start");
		}

		this.dataRespuesta = dataRespuesta;

		if (logger.isDebugEnabled()) {
			logger.debug("setDataRespuesta(Object) - end");
		}
	}
	/**
	 * Indica si hay un documento abierto.
	 * Este estado aplica para el momento de formación en el buffer
	 * @return Verdadero si en el buffer hay un documento abierto.
	 */
	public boolean isDocumentoAbierto() {
		if (logger.isDebugEnabled()) {
			logger.debug("isDocumentoAbierto() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isDocumentoAbierto() - end");
		}
		return documentoAbierto;
	}
	/**
	 * Establece que hay un documento abierto en el buffer del driver
	 * Este estado aplica para el momento de formación en el buffer
	 * @param documentoAbierto El documentoAbierto a establecer.
	 */
	public void setDocumentoAbierto(boolean documentoAbierto) {
		if (logger.isDebugEnabled()) {
			logger.debug("setDocumentoAbierto(boolean) - start");
		}

		this.documentoAbierto = documentoAbierto;

		if (logger.isDebugEnabled()) {
			logger.debug("setDocumentoAbierto(boolean) - end");
		}
	}
	/**
	 * Indica si hubo un error en la impresora fiscal en la impresión
	 * del ultimo comando
	 * @return Verdadero si hubo un error.
	 */
	public boolean isErrorImpresora() {
		if (logger.isDebugEnabled()) {
			logger.debug("isErrorImpresora() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isErrorImpresora() - end");
		}
		return errorImpresora;
	}
	/**
	 * Establece el estado de error en la impresora fiscal
	 * @param errorImpresora El errorImpresora a establecer.
	 */
	public void setErrorImpresora(boolean errorImpresora) {
		if (logger.isDebugEnabled()) {
			logger.debug("setErrorImpresora(boolean) - start");
		}

		this.errorImpresora = errorImpresora;
		if (errorImpresora) {
			doStatusChanged(PARAM_ERROR_IMPRESORA);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setErrorImpresora(boolean) - end");
		}
	}
	/**
	 * Indica el estado del comprobante fiscal en formación en el
	 * buffer del driver
	 * Este estado aplica para el momento de formación en el buffer
	 * Es una de las constantes EDO_FISCAL_XXXX
	 * @return Devuelve estadoFiscal.
	 */
	public char getEstadoFiscal() {
		if (logger.isDebugEnabled()) {
			logger.debug("getEstadoFiscal() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getEstadoFiscal() - end");
		}
		return estadoFiscal;
	}
	/**
	 * Establece el estado del comprobante fiscal en formación en el buffer
	 * Este estado aplica para el momento de formación en el buffer
	 * Es una de las constantes EDO_FISCAL_XXXX
	 * @param estadoFiscal El estadoFiscal a establecer.
	 */
	public void setEstadoFiscal(char estadoFiscal) {
		if (logger.isDebugEnabled()) {
			logger.debug("setEstadoFiscal(char) - start");
		}

		this.estadoFiscal = estadoFiscal;

		if (logger.isDebugEnabled()) {
			logger.debug("setEstadoFiscal(char) - end");
		}
	}
	/**
	 * Indica si el documento actual del buffer se imprimirá por la estación
	 * de tickets 
	 * Este estado aplica para el momento de formación en el buffer
	 * @return Verdadero si no se ha agregado al buffer el comando de activar slip.
	 */
	public boolean isImpresionTicket() {
		if (logger.isDebugEnabled()) {
			logger.debug("isImpresionTicket() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isImpresionTicket() - end");
		}
		return impresionTicket;
	}
	/**
	 * Establece si se va a imprimir o no los siguientes comandos por 
	 * la estación de ticket
	 * Este estado aplica para el momento de formación en el buffer
	 * @param impresionTicket El impresionTicket a establecer.
	 */
	public void setImpresionTicket(boolean impresionTicket) {
		if (logger.isDebugEnabled()) {
			logger.debug("setImpresionTicket(boolean) - start");
		}

		this.impresionTicket = impresionTicket;

		if (logger.isDebugEnabled()) {
			logger.debug("setImpresionTicket(boolean) - end");
		}
	}
	/**
	 * Indica si el motor del driver esta enviando algún documento o solicitud
	 * a la impresora fiscal
	 * @return Verdadero si el motor se encuentra ocupado imprimiendo
	 */
	public boolean isImprimiendo() {
		if (logger.isDebugEnabled()) {
			logger.debug("isImprimiendo() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isImprimiendo() - end");
		}
		return imprimiendo;
	}
	/**
	 * Establece si el motor del driver se encuentra imprimiendo o 
	 * no algun documento generado
	 * @param imprimiendo Verdadero si se esta imprimiendo un documento en este momento
	 */
	public void setImprimiendo(boolean imprimiendo) {
		if (logger.isDebugEnabled()) {
			logger.debug("setImprimiendo(boolean) - start");
		}

		this.imprimiendo = imprimiendo;

		if (logger.isDebugEnabled()) {
			logger.debug("setImprimiendo(boolean) - end");
		}
	}
	/**
	 * Devuelve el numero de lineas del documento no fiscal en formación 
	 * en el buffer
	 * Este estado aplica para el momento de formación en el buffer
	 * @return Devuelve numLineaNF.
	 */
	public int getNumLineaNF() {
		if (logger.isDebugEnabled()) {
			logger.debug("getNumLineaNF() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getNumLineaNF() - end");
		}
		return numLineaNF;
	}
	
	/**
	 * Incrementa el contador de número de líneas no fiscales
	 * Este estado aplica para el momento de formación en el buffer
	 */
	public void incNumLineaNF() {
		if (logger.isDebugEnabled()) {
			logger.debug("incNumLineaNF() - start");
		}

		this.numLineaNF++;

		if (logger.isDebugEnabled()) {
			logger.debug("incNumLineaNF() - end");
		}
	}
	
	/**
	 * Establece el numero de lineas no fiscales en el documento en formación (DNF) 
	 * Este estado aplica para el momento de formación en el buffer
	 * @param numLineaNF El numLineaNF a establecer.
	 */
	public void setNumLineaNF(int numLineaNF) {
		if (logger.isDebugEnabled()) {
			logger.debug("setNumLineaNF(int) - start");
		}

		this.numLineaNF = numLineaNF;

		if (logger.isDebugEnabled()) {
			logger.debug("setNumLineaNF(int) - end");
		}
	}
	/**
	 * Indica si ya se imprimió la primera linea en el comprobante fiscal, ya 
	 * sea de texto fiscal, o un item fiscal
	 * Este estado aplica para el momento de formación en el buffer
	 * @return Devuelve primeraLineaCF.
	 */
	public boolean isPrimeraLineaCF() {
		if (logger.isDebugEnabled()) {
			logger.debug("isPrimeraLineaCF() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isPrimeraLineaCF() - end");
		}
		return primeraLineaCF;
	}
	/**
	 * Establece que ya se imprimió la primera linea en el comprobante fiscal
	 * Este estado aplica para el momento de formación en el buffer
	 * @param primeraLineaCF El primeraLineaCF a establecer.
	 */
	public void setPrimeraLineaCF(boolean primeraLineaCF) {
		if (logger.isDebugEnabled()) {
			logger.debug("setPrimeraLineaCF(boolean) - start");
		}

		this.primeraLineaCF = primeraLineaCF;

		if (logger.isDebugEnabled()) {
			logger.debug("setPrimeraLineaCF(boolean) - end");
		}
	}
	/**
	 * Retorna el serial de la impresora fiscal
	 * @return Devuelve serialImpresoraFiscal.
	 */
	public String getSerialImpresoraFiscal() {
		if (logger.isDebugEnabled()) {
			logger.debug("getSerialImpresoraFiscal() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getSerialImpresoraFiscal() - end");
		}
		return serialImpresoraFiscal;
	}
	/**
	 * Establece el serial de la impresora fiscal
	 * @param serialImpresoraFiscal El serialImpresoraFiscal a establecer.
	 */
	public void setSerialImpresoraFiscal(String serialImpresoraFiscal) {
		if (logger.isDebugEnabled()) {
			logger.debug("setSerialImpresoraFiscal(String) - start");
		}

		this.serialImpresoraFiscal = serialImpresoraFiscal;

		if (logger.isDebugEnabled()) {
			logger.debug("setSerialImpresoraFiscal(String) - end");
		}
	}
	/**
	 * Indica el tipo de respuesta esperada del ultimo comando enviado
	 * a la  impresora fiscal
	 * @return Devuelve tipoRespEsperada.
	 */
	public int getTipoRespEsperada() {
		if (logger.isDebugEnabled()) {
			logger.debug("getTipoRespEsperada() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getTipoRespEsperada() - end");
		}
		return tipoRespEsperada;
	}
	/**
	 * Establece el tipo de respuesta esperada del comando enviado a la
	 * impresora fiscal
	 * @param tipoRespEsperada El tipoRespEsperada a establecer.
	 */
	public void setTipoRespEsperada(int tipoRespEsperada) {
		if (logger.isDebugEnabled()) {
			logger.debug("setTipoRespEsperada(int) - start");
		}

		this.tipoRespEsperada = tipoRespEsperada;

		if (logger.isDebugEnabled()) {
			logger.debug("setTipoRespEsperada(int) - end");
		}
	}
	/**
	 * Indica si hubo desborde de totales en el dispositivo fiscal
	 * @return Devuelve totalesDesbordados.
	 */
	public boolean isTotalesDesbordados() {
		if (logger.isDebugEnabled()) {
			logger.debug("isTotalesDesbordados() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isTotalesDesbordados() - end");
		}
		return totalesDesbordados;
	}
	/**
	 * Establece el estado de totales desbordados en el dispositivo fiscal
	 * @param totalesDesbordados El totalesDesbordados a establecer.
	 */
	public void setTotalesDesbordados(boolean totalesDesbordados) {
		if (logger.isDebugEnabled()) {
			logger.debug("setTotalesDesbordados(boolean) - start");
		}

		this.totalesDesbordados = totalesDesbordados;
		if (totalesDesbordados) {
			doStatusChanged(PARAM_DESBORDAMIENTO_TOTALES);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setTotalesDesbordados(boolean) - end");
		}
	}
	/**
	 * Indica si el ultimo documento generado fue o no un documento fiscal
	 * Este estado aplica para el momento de formación en el buffer
	 * @return Devuelve ultDocFiscal.
	 */
	public boolean isUltDocFiscal() {
		if (logger.isDebugEnabled()) {
			logger.debug("isUltDocFiscal() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isUltDocFiscal() - end");
		}
		return ultDocFiscal;
	}
	/**
	 * Establece si el ultimo documento generado fue o no un documento fiscal
	 * Este estado aplica para el momento de formación en el buffer
	 * @param ultDocFiscal El ultDocFiscal a establecer.
	 */
	public void setUltDocFiscal(boolean ultDocFiscal) {
		if (logger.isDebugEnabled()) {
			logger.debug("setUltDocFiscal(boolean) - start");
		}

		this.ultDocFiscal = ultDocFiscal;

		if (logger.isDebugEnabled()) {
			logger.debug("setUltDocFiscal(boolean) - end");
		}
	}
	/**
	 * Indica si la memoria fiscal del dispositivo se encuentra llena
	 * @return Devuelve memFiscalLlena.
	 */
	public boolean isMemFiscalLlena() {
		if (logger.isDebugEnabled()) {
			logger.debug("isMemFiscalLlena() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isMemFiscalLlena() - end");
		}
		return memFiscalLlena;
	}
	/**
	 * Establece que la memoria del dispositivo fiscal se encuentra llena o no
	 * @param memFiscalLlena El memFiscalLlena a establecer.
	 */
	public void setMemFiscalLlena(boolean memFiscalLlena) {
		if (logger.isDebugEnabled()) {
			logger.debug("setMemFiscalLlena(boolean) - start");
		}

		this.memFiscalLlena = memFiscalLlena;
		if (memFiscalLlena) {
			doStatusChanged(PARAM_MEM_FISCAL_LLENA);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setMemFiscalLlena(boolean) - end");
		}
	}
	/**
	 * Indica si se llego al tope maximo de items en un comprobante fiscal
	 * @return Devuelve numMaxItems.
	 */
	public boolean isNumMaxItems() {
		if (logger.isDebugEnabled()) {
			logger.debug("isNumMaxItems() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isNumMaxItems() - end");
		}
		return numMaxItems;
	}
	/**
	 * Establece que se llego o no al tope máximo de items en un comprobante fiscal
	 * @param numMaxItems El numMaxItems a establecer.
	 */
	public void setNumMaxItems(boolean numMaxItems) {
		if (logger.isDebugEnabled()) {
			logger.debug("setNumMaxItems(boolean) - start");
		}

		this.numMaxItems = numMaxItems;
		if (numMaxItems) {
			doStatusChanged(PARAM_MAX_ITEMS_CF);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setNumMaxItems(boolean) - end");
		}
	}
	/**
	 * Retorna el ultimo tipo de comando ejecutado en la impresora fiscal.
	 * La impresora ejecutó efectivamente un comando si el ultimo tipo de comando enviado
	 * es igual al ultimo tipo de comando ejecutado
	 * @return Devuelve ultCommandTypeEjecutado.
	 */
	public int getUltCommandTypeEjecutado() {
		if (logger.isDebugEnabled()) {
			logger.debug("getUltCommandTypeEjecutado() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getUltCommandTypeEjecutado() - end");
		}
		return ultCommandTypeEjecutado;
	}
	/**
	 * Establece el ultimo tipo de comando ejecutado en la impresora fiscal
	 * @param ultCommandTypeEjecutado El ultCommandTypeEjecutado a establecer.
	 */
	public void setUltCommandTypeEjecutado(int ultCommandTypeEjecutado) {
		if (logger.isDebugEnabled()) {
			logger.debug("setUltCommandTypeEjecutado(int) - start");
		}

		this.ultCommandTypeEjecutado = ultCommandTypeEjecutado;

		if (logger.isDebugEnabled()) {
			logger.debug("setUltCommandTypeEjecutado(int) - end");
		}
	}
	/**
	 * Retorna el numero entero representando la palabra binaria
	 * de estado fiscal.
	 * @return Devuelve edoFiscal.
	 */
	public int getEdoFiscal() {
		return edoFiscal;
	}
	/**
	 * Establece la palabra binaria del estado fiscal
	 * @param edoFiscal El edoFiscal a establecer.
	 */
	public void setEdoFiscal(int edoFiscal) {
		this.edoFiscal = edoFiscal;
	}
	/**
	 * Retorna la palabra binaria del estado de la impresora
	 * @return Devuelve edoImpresora.
	 */
	public int getEdoImpresora() {
		return edoImpresora;
	}
	/**
	 * Establece la palabra binaria del estado de la impresora
	 * @param edoImpresora El edoImpresora a establecer.
	 */
	public void setEdoImpresora(int edoImpresora) {
		this.edoImpresora = edoImpresora;
	}
	
	/**
	 * Indica si el slip se encuentra activo. Este valor cambia al enviar el comando
	 * de  activar slip y desactivar slip   
	 * @return Devuelve slipActivo.
	 */
	public boolean isSlipActivo() {
		return slipActivo;
	}
	/**
	 * Usado para la notificación de cambio de estado de activo del
	 * slip
	 * @param slipActivo El slipActivo a establecer.
	 */
	public void setSlipActivo(boolean slipActivo) {
		this.slipActivo = slipActivo;
	}
    /**
     * @return Devuelve ultIDEnviado.
     */
    public String getUltIDEnviado() {
        return ultIDEnviado;
    }
    /**
     * @param ultIDEnviado El ultIDEnviado a establecer.
     */
    public void setUltIDEnviado(String ultIDEnviado) {
        this.ultIDEnviado = ultIDEnviado;
    }

	/**
	 * @return el tipoUltimoDocumentoImpreso
	 */
	public String getTipoUltimoDocumentoImpreso() {
		return tipoUltimoDocumentoImpreso;
	}

	/**
	 * @param tipoUltimoDocumentoImpreso el tipoUltimoDocumentoImpreso a establecer
	 */
	public void setTipoUltimoDocumentoImpreso(String tipoUltimoDocumentoImpreso) {
		this.tipoUltimoDocumentoImpreso = tipoUltimoDocumentoImpreso;
	}
}
