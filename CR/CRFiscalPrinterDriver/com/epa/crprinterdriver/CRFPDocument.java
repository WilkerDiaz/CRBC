/*
 * $Id: CRFPDocument.java,v 1.2.2.3 2005/05/09 14:28:26 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerreter�a EPA C.A. 
 *
 * Proyecto		: CRFiscalPrinterDriver 1.1
 * Paquete		: com.epa.crprinterdriver
 * Programa		: CRFPDocument.java
 * Creado por	: Programa8 - Ar�stides Castillo Colmen�rez
 * Creado en 	: 11-abr-2005 16:16:44
 * (C) Copyright 2005 SuperFerreter�a EPA C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Informaci�n de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisi�n	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: CRFPDocument.java,v $
 * Revision 1.2.2.3  2005/05/09 14:28:26  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.2  2005/05/09 14:19:08  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.1  2005/05/06 18:17:04  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.2  2005/05/05 12:08:48  programa8
 * Versi�n 2.0.1 Final
 *
 * Revision 1.1.2.4  2005/04/21 13:47:57  programa8
 * Driver Fiscal 2.0 RC1 - Al 21/04/2004
 *
 * Revision 1.1.2.3  2005/04/19 19:08:31  programa8
 * Driver Fiscal al 19/04/2005 - Primer d�a de pruebas
 *
 * Revision 1.1.2.2  2005/04/18 16:39:20  programa8
 * Driver Fiscal al 18/04/2005 - 12m
 *
 * Revision 1.1.2.1  2005/04/11 20:59:08  programa8
 * CRFiscalPrinterDriver 2.0 - Analisis Preliminar
 *
 * ===========================================================================
 */
package com.epa.crprinterdriver;

import org.apache.log4j.Logger;

import java.util.Vector;

/**
 * 
 * <p>
 * Entidad que se encarga de la agrupaci�n l�gica de comandos como un conjunto con
 * reglas y l�gica propias.
 * 
 * </p>
 * 
 * <p>
 * <a href="CRFPDocument.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - Ar�stides Castillo Colmen�rez - $Author: programa8 $
 * @version $Revision: 1.2.2.3 $ - $Date: 2005/05/09 14:28:26 $
 * @since 11-abr-2005
 * 
 */
/*
* En esta clase se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
* S�lo se parametriz� el tipo de dato contenido en los 'Vector' y se comentaron variables sin uso
* Fecha: agosto 2011
*/
public class CRFPDocument {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CRFPDocument.class);

	/**
	 * Estado del documento listo para imprimir
	 */
	public static final int STATUS_LISTO = 0;
	/**
	 * Estado del documento Pausado
	 */
	public static final int STATUS_PAUSADO = 1;
	private static int idGen = 0;
	
	private boolean fiscal;
	private int status;
	private int id;
	private Vector<CRFPCommand> comandos = new Vector<CRFPCommand>();
	private boolean listaRespuesta;
	
	// Propiedades para el manejo del estado mientras
	// se construye el documento
/*	private boolean compFiscalCerrado = false;
	private boolean docIniciado = false;
	private boolean docFinalizado = false;*/
	/**
	 * @since 11-abr-2005
	 * 
	 */
	public CRFPDocument() {
		super();
		id = idGen++;
		status = STATUS_LISTO;
	}
	
	
	/**
	 * Agrega un comando al documento
	 * @param o Comando a enviar
	 * @return Verdadero si se realizo satisfactoriamente la operaci�n
	 */
	public boolean add(CRFPCommand o) {
		if (logger.isDebugEnabled()) {
			logger.debug("add(CRFPCommand) - start");
		}
		
		boolean returnboolean = comandos.add(o);
		if (logger.isDebugEnabled()) {
			logger.debug("add(CRFPCommand) - end");
		}
		return returnboolean;
	}
	
	
	/**
	 * Indica el numero de comandos en el documento actual
	 * @return Tama�o en cantidad de comandos
	 */
	public int size() {
		if (logger.isDebugEnabled()) {
			logger.debug("size() - start");
		}

		int returnint = comandos.size();
		if (logger.isDebugEnabled()) {
			logger.debug("size() - end");
		}
		return returnint;
	}
	
	/**
	 * Devuelve el comando en la posici�n actual del documento
	 * La primera posici�n es la 0 
	 * @param index Indice del comando
	 * @return Comando solicitado
	 */
	public CRFPCommand get(int index) {
		if (logger.isDebugEnabled()) {
			logger.debug("get(int) - start");
		}

		CRFPCommand returnCRFPCommand = comandos.get(index);
		if (logger.isDebugEnabled()) {
			logger.debug("get(int) - end");
		}
		return returnCRFPCommand;
	}
	
	
	/**
	 * Elimina todos los comandos del documento actual
	 */
	public void clear() {
		if (logger.isDebugEnabled()) {
			logger.debug("clear() - start");
		}

		comandos.clear();

		if (logger.isDebugEnabled()) {
			logger.debug("clear() - end");
		}
	}
	/**
	 * Indica que el documento es un comprobante fiscal
	 * @return Devuelve fiscal.
	 */
	public boolean isFiscal() {
		if (logger.isDebugEnabled()) {
			logger.debug("isFiscal() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isFiscal() - end");
		}
		return fiscal;
	}
	
	/**
	 * Establece este documento como comprobante fiscal
	 * @param fiscal El fiscal a establecer.
	 */
	public void setFiscal(boolean fiscal) {
		if (logger.isDebugEnabled()) {
			logger.debug("setFiscal(boolean) - start");
		}

		this.fiscal = fiscal;

		if (logger.isDebugEnabled()) {
			logger.debug("setFiscal(boolean) - end");
		}
	}
	
	/**
	 * Retorna el identificador del documento
	 * @return Devuelve id.
	 */
	public int getId() {
		if (logger.isDebugEnabled()) {
			logger.debug("getId() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getId() - end");
		}
		return id;
	}
	
	/**
	 * Establece el identificador del documento
	 * @param id El id a establecer.
	 */
	public void setId(int id) {
		if (logger.isDebugEnabled()) {
			logger.debug("setId(int) - start");
		}

		this.id = id;

		if (logger.isDebugEnabled()) {
			logger.debug("setId(int) - end");
		}
	}
	
	/**
	 * Retorna el estado del documento.
	 * Representa una de las variables siguientes:
	 * 			STATUS_LISTO : Indica que el documento se encuentra listo para ser impreso
	 * 			STATUS_PAUSADO : Indica que el documento est� pausado y no ser� impreso aun
	 * @return Devuelve status.
	 */
	public int getStatus() {
		if (logger.isDebugEnabled()) {
			logger.debug("getStatus() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getStatus() - end");
		}
		return status;
	}
	
	/**
	 * Establece el estatus del documento. 
	 * Es una de las constantes STATUS_XXXX
	 * @param status El status a establecer.
	 */
	public void setStatus(int status) {
		if (logger.isDebugEnabled()) {
			logger.debug("setStatus(int) - start");
		}

		this.status = status;

		if (logger.isDebugEnabled()) {
			logger.debug("setStatus(int) - end");
		}
	}
	/**
	 * Indica si se recibio satisfactoriamente la respuesta esperada 
	 * del documento actual
	 * @return Devuelve listaRespuesta.
	 */
	public boolean isListaRespuesta() {
		return listaRespuesta;
	}
	/**
	 * Establece que la respuesta del documento actual esta o no lista
	 * @param listaRespuesta El listaRespuesta a establecer.
	 */
	public synchronized void setListaRespuesta(boolean listaRespuesta) {
		this.listaRespuesta = listaRespuesta;
		if (listaRespuesta)
			notifyAll();
	}
}
