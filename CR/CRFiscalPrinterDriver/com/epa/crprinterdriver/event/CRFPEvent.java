/*
 * $Id: CRFPEvent.java,v 1.2.2.4 2005/05/09 14:28:17 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CRFiscalPrinterDriver 1.1
 * Paquete		: com.epa.crprinterdriver.event
 * Programa		: CRFPEvent.java
 * Creado por	: Programa8 - Arístides Castillo Colmenárez
 * Creado en 	: 11-abr-2005 14:49:13
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
 * $Log: CRFPEvent.java,v $
 * Revision 1.2.2.4  2005/05/09 14:28:17  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.3  2005/05/09 14:18:53  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.2  2005/05/06 19:17:52  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.2.2.1  2005/05/06 19:12:46  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.2  2005/05/05 12:08:42  programa8
 * Versión 2.0.1 Final
 *
 * Revision 1.1.2.2  2005/04/18 16:39:32  programa8
 * Driver Fiscal al 18/04/2005 - 12m
 *
 * Revision 1.1.2.1  2005/04/11 20:59:14  programa8
 * CRFiscalPrinterDriver 2.0 - Analisis Preliminar
 *
 * ===========================================================================
 */
package com.epa.crprinterdriver.event;

import org.apache.log4j.Logger;


/**
 * <p>
 * Clase que representa un evento de la impresora fiscal. Usado para la 
 * notificación y recepción de feedback  entre la aplicación y el driver fiscal
 * </p>
 * <p>
 * <a href="CRFPEvent.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - Arístides Castillo Colmenárez - $Author: programa8 $
 * @version $Revision: 1.2.2.4 $ - $Date: 2005/05/09 14:28:17 $
 * @since 11-abr-2005
 * 
 */
public class CRFPEvent {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CRFPEvent.class);
	/**
	 * Constante de tipo de evento: Falta papel en la impresora
	 */
	public static final int FALTA_PAPEL = 0;
	/**
	 * Constante de tipo de evento: La impresora requiere un reporte Z
	 */
	
	public static final int REQUIERE_Z = 1;
	/**
	 * Constante de tipo de evento: Se requiere atención del usuario
	 */
	public static final int ERROR_ATENCION_USUARIO = 2;
	
	/**
	 * Constante de tipo de evento: Error crítico
	 */
	public static final int ERROR_CRITICO = 3;

	/**
	 * Constante de tipo de evento: Impresión terminada satisfactoriamente
	 */
	public static final int IMPRESION_OK = 4;
	
	private int type;
	private int documentID;
	private boolean docFiscal;
	private int numComprobante;
	private int idError;
	private String errorMsg;
	private boolean comprobanteAbortado;
	private boolean reintentar = false;
	
	/**
	 * Constructor de la clase 
	 * @since 11-abr-2005
	 * @param eventType Tipo de evento
	 * @param docID Identificador de documento
	 * @param fiscal Indica si el documento es un comprobante fiscal
	 * @param numComp Numero de comprobante fiscal (si aplica)
	 */
	public CRFPEvent(int eventType, int docID, boolean fiscal, int numComp) {
		this (eventType, docID, fiscal, numComp, 0, null);
	}
	
	/**
	 * Constructor de la clase
	 * @since 11-abr-2005
	 * @param eventType Tipo de evento
	 * @param docID Identificador de documento
	 * @param fiscal Indica si el documento es un comprobante fiscal
	 * @param numComp Numero de comprobante fiscal (si aplica)
	 * @param error Numero de error de la impresora fiscal
	 * @param errMsg Mensaje de error traducido
	 */
	public CRFPEvent(int eventType, int docID, boolean fiscal, int numComp, 
			int error, String errMsg) {
		this(eventType, docID, fiscal, numComp, error, errMsg, false);
		
	}

	/**
	 * Constructor de la clase 
	 * @since 11-abr-2005
	 * @param eventType Tipo de evento
	 * @param docID Identificador de documento
	 * @param fiscal Indica si el documento es un comprobante fiscal
	 * @param numComp Numero de comprobante fiscal (si aplica)
	 * @param error Numero de error de la impresora fiscal
	 * @param errMsg Mensaje de error traducido
	 * @param compAbortado Indica que el comprobante fue o será abortado
	 */
	public CRFPEvent(int eventType, int docID, boolean fiscal, int numComp, 
			int error, String errMsg, boolean compAbortado) {
		super();
		type = eventType;
		documentID = docID;
		docFiscal = fiscal;
		numComprobante = numComp;
		idError = error;
		errorMsg = errMsg;
		comprobanteAbortado = compAbortado;
	}

	/**
	 * @return Devuelve comprobanteAbortado.
	 */
	public boolean isComprobanteAbortado() {
		if (logger.isDebugEnabled()) {
			logger.debug("isComprobanteAbortado() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isComprobanteAbortado() - end");
		}
		return comprobanteAbortado;
	}
	/**
	 * @param comprobanteAbortado El comprobanteAbortado a establecer.
	 */
	public void setComprobanteAbortado(boolean comprobanteAbortado) {
		if (logger.isDebugEnabled()) {
			logger.debug("setComprobanteAbortado(boolean) - start");
		}

		this.comprobanteAbortado = comprobanteAbortado;

		if (logger.isDebugEnabled()) {
			logger.debug("setComprobanteAbortado(boolean) - end");
		}
	}
	/**
	 * @return Devuelve docFiscal.
	 */
	public boolean isDocFiscal() {
		if (logger.isDebugEnabled()) {
			logger.debug("isDocFiscal() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isDocFiscal() - end");
		}
		return docFiscal;
	}
	/**
	 * Retorna el identificador del documento
	 * @return Devuelve documentID.
	 */
	public int getDocumentID() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDocumentID() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDocumentID() - end");
		}
		return documentID;
	}
	/**
	 * Retorna el mensaje de error
	 * @return Devuelve errorMsg.
	 */
	public String getErrorMsg() {
		if (logger.isDebugEnabled()) {
			logger.debug("getErrorMsg() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getErrorMsg() - end");
		}
		return errorMsg;
	}
	/**
	 * Retorna el numero de error nativo del dispositivo
	 * @return Devuelve idError.
	 * Si idError = -1, significa que es un error por falta de comunicación
	 */
	public int getIdError() {
		if (logger.isDebugEnabled()) {
			logger.debug("getIdError() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getIdError() - end");
		}
		return idError;
	}
	/**
	 * Retorna el numero del comprobante fiscal, si aplica
	 * @return Devuelve numComprobante.
	 */
	public int getNumComprobante() {
		if (logger.isDebugEnabled()) {
			logger.debug("getNumComprobante() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getNumComprobante() - end");
		}
		return numComprobante;
	}
	/**
	 * Devuelve el tipo de evento. Ver constantes en esta clase
	 * @return Devuelve type.
	 */
	public int getType() {
		if (logger.isDebugEnabled()) {
			logger.debug("getType() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getType() - end");
		}
		return type;
	}

	/**
	 * Retorna la respuesta del usuario para reintentar la impresión
	 * @return Devuelve reintentar.
	 */
	public boolean isReintentar() {
		if (logger.isDebugEnabled()) {
			logger.debug("isReintentar() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isReintentar() - end");
		}
		return reintentar;
	}
	/**
	 * Indica al driver que debe reintentar la impresión del documento
	 * @param reintentar El reintentar a establecer.
	 */
	public void setReintentar(boolean reintentar) {
		if (logger.isDebugEnabled()) {
			logger.debug("setReintentar(boolean) - start");
		}

		this.reintentar = reintentar;

		if (logger.isDebugEnabled()) {
			logger.debug("setReintentar(boolean) - end");
		}
	}
}
