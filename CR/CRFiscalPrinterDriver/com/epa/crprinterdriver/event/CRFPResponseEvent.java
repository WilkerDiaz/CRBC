/*
 * $Id: CRFPResponseEvent.java,v 1.2.2.4 2005/05/09 14:28:18 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CRFiscalPrinterDriver 1.1
 * Paquete		: com.epa.crprinterdriver.event
 * Programa		: CRFPResponseEvent.java
 * Creado por	: Programa8
 * Creado en 	: 13-abr-2005 9:21:40
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
 * $Log: CRFPResponseEvent.java,v $
 * Revision 1.2.2.4  2005/05/09 14:28:18  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.3  2005/05/09 14:18:56  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.2  2005/05/06 19:17:53  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.2.2.1  2005/05/06 19:12:47  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.2  2005/05/05 12:08:43  programa8
 * Versión 2.0.1 Final
 *
 * Revision 1.1.2.3  2005/04/19 19:08:29  programa8
 * Driver Fiscal al 19/04/2005 - Primer día de pruebas
 *
 * Revision 1.1.2.2  2005/04/18 16:39:30  programa8
 * Driver Fiscal al 18/04/2005 - 12m
 *
 * Revision 1.1.2.1  2005/04/13 21:57:08  programa8
 * Driver fiscal al 13/04/2005
 *
 * ===========================================================================
 */
package com.epa.crprinterdriver.event;

import org.apache.log4j.Logger;

/**
 * <p>
 * Clase que representa un evento de respuesta entre el interprete de respuestas
 * y quien requiera conocer la llegada de una respuesta por parte de la impresora
 * </p>
 * <p>
 * <a href="CRFPResponseEvent.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - Arístides Castillo Colmenárez - $Author: programa8 $
 * @version $Revision: 1.2.2.4 $ - $Date: 2005/05/09 14:28:18 $
 * @since 13-abr-2005
 * 
 */
public class CRFPResponseEvent {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(CRFPResponseEvent.class);

	/**
	 * Respuesta satisfactoria sin datos de retorno
	 */
	public static final int RESPONSE_OK = 0;
	/**
	 * Respuesta satisfactoria con data enviada por la impresora
	 */
	public static final int RESPONSE_DATA = 1;
	
	/**
	 * Respuesta valida de la impresora notificando error al ejecutar comando
	 */
	public static final int RESPONSE_ERROR = 2;
	
	/**
	 * Respuesta enviada por la impresora pero con falla en protocolo. 
	 */
	public static final int RESPONSE_INVALID = 3;
	
	private int responseType;
	private int commandType;
	private String sequenceId;
	private Object data;
	private int errorCode;
	
	/**
	 * Constructor de evento de respuesta
	 * 
	 * El tipo de respuesta puede ser una de las siguientes constantes
	 * 
	 * @since 13-abr-2005
	 * @param responseType Tipo de respuesta 
	 * @param commandType Tipo de comando
	 * @param sequenceId Identificador de secuencia
	 */
	public CRFPResponseEvent(int responseType, int commandType, String sequenceId) {
		if ( responseType < RESPONSE_OK  ||  RESPONSE_INVALID < responseType )
			throw new IllegalArgumentException("Tipo errado de respuesta");
		this.responseType = responseType;
		this.commandType = commandType;
		this.sequenceId = sequenceId;
	}
	
	/**
	 * Retorna el objeto de datos de respuesta llegada
	 * @return Devuelve data.
	 */
	public Object getData() {
		if (logger.isDebugEnabled()) {
			logger.debug("getData() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getData() - end");
		}
		return data;
	}
	/**
	 * Establece el objeto de datos retornado por la impresora
	 * @param data El data a establecer.
	 */
	public void setData(Object data) {
		if (logger.isDebugEnabled()) {
			logger.debug("setData(Object) - start");
		}

		this.data = data;

		if (logger.isDebugEnabled()) {
			logger.debug("setData(Object) - end");
		}
	}
	/**
	 * Retorna el codigo de error recibido desde la impresora
	 * @return Devuelve errorCode.
	 */
	public int getErrorCode() {
		if (logger.isDebugEnabled()) {
			logger.debug("getErrorCode() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getErrorCode() - end");
		}
		return errorCode;
	}
	/**
	 * Establece el codigo de error enviado por la impresora
	 * @param errorCode El errorCode a establecer.
	 */
	public void setErrorCode(int errorCode) {
		if (logger.isDebugEnabled()) {
			logger.debug("setErrorCode(int) - start");
		}

		this.errorCode = errorCode;

		if (logger.isDebugEnabled()) {
			logger.debug("setErrorCode(int) - end");
		}
	}
	/**
	 * Devuelve el tipo de comando que generó la respuesta actual
	 * @return Devuelve commandType.
	 */
	public int getCommandType() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCommandType() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getCommandType() - end");
		}
		return commandType;
	}
	/**
	 * Retorna el tipo de respuesta. Una de las constantes RESPONSE_XXX
	 * @return Devuelve responseType.
	 */
	public int getResponseType() {
		if (logger.isDebugEnabled()) {
			logger.debug("getResponseType() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getResponseType() - end");
		}
		return responseType;
	}
	/**
	 * Devuelve el identificador de secuencia del comando que generó la respuesta
	 * @return Devuelve sequenceId.
	 */
	public String getSequenceId() {
		if (logger.isDebugEnabled()) {
			logger.debug("getSequenceId() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getSequenceId() - end");
		}
		return sequenceId;
	}
}
