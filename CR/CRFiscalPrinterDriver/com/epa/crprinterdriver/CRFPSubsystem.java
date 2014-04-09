/*
 * $Id: CRFPSubsystem.java,v 1.1.2.5 2005/05/09 14:28:25 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerreter�a EPA C.A. 
 *
 * Proyecto		: CRFiscalPrinterDriver 2.0
 * Paquete		: com.epa.crprinterdriver
 * Programa		: CRFPSubsystem.java
 * Creado por	: Programa8
 * Creado en 	: 05-may-2004 11:58:56
 * (C) Copyright 2004 SuperFerreter�a EPA C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Informaci�n de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisi�n	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: CRFPSubsystem.java,v $
 * Revision 1.1.2.5  2005/05/09 14:28:25  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.1.2.4  2005/05/09 14:19:08  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.1.2.3  2005/05/06 19:18:03  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.1.2.2  2005/05/06 18:17:03  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.1.2.1  2005/05/05 22:05:43  programa8
 * Versi�n 2.1:
 * *- Preparaci�n para funcionamiento con EPSON TMU950PF
 * *- Toma en cuenta la solicitud de incremento en espera por timeout
 * *- Considera independencia de dispositivo en colchones de tiempo
 * *- Considera funciones que pueden estar implementadas en un dispositivo
 * no necesariamente implementadas en otro
 * *- Separa Subsistema base del driver de la fachada usadas por las
 * aplicaciones
 * *- Maneja estatus de activaci�n del slip. Considera colchones de tiempo
 * para comando enviados al slip.
 * *- Patron de dise�o Abstract Factory para la selecci�n de la implementaci�n
 * de dispositivo a partir de propiedad de la aplicaci�n
 *
 * ===========================================================================
 */
package com.epa.crprinterdriver;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.epa.crprinterdriver.util.ContadorConsumoPapel;
import com.epa.crserialinterface.Parameters;

/**
 * <p>
 * Clase que sirve como base del subsistema de impresi�n fiscal. Permite el encapsulamiento
 * de los componentes del subsistema de la aplicaci�n que usa al driver completo, y permite
 * acceso a cada uno de los componentes instanciados en el subsistema, permitiendo as�
 * que los mismos puedan ser consultados por cualquier otro de los componentes.
 * </p>
 * <p>
 * <a href="CRFPSubsystem.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - Ar�stides Castillo Colmen�rez - $Author: programa8 $
 * @version $Revision: 1.1.2.5 $ - $Date: 2005/05/09 14:28:25 $
 * @since 05-may-2004
 * 
 */
public class CRFPSubsystem {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CRFPSubsystem.class);

	/**
	 * @since 05-may-2004
	 * 
	 */
	
    Parameters serialParameters;
    CRPrinterParameters crPrinterParameters;
    CRFPEngine engine;
    CRFPStatus status;
	CRFPResponseParser responseParser;
	CRFPSequenceMap sequenceMap;
	ContadorConsumoPapel contador;
	boolean contarPapel = false;
	CRFiscalPrinterOperations operations;

	/**
	 * Constructor del subsistema de impresi�n fiscal
	 * @since 06-may-2004
	 * @param portName Nombre de puerto serial
	 * @param baudRate BaudRate del puerto serial
	 * @param flowControlIn Mecanismo de control de flujo de entrada
	 * @param flowControlOut Mecanismo de control de flujo de salida
	 * @param dataBits Numero de bits de datos 
	 * @param stopBits Numero de bits de parada
	 * @param parity Paridad del puerto serial
	 * @param operations Instancia de la fachada del subsistema
	 */
	public CRFPSubsystem(String portName, String baudRate, 
            String flowControlIn, String flowControlOut, String dataBits, 
            String stopBits, String parity, CRFiscalPrinterOperations operations) {
		super();
		loadProperties();
		this.operations = operations;
        crPrinterParameters = new CRPrinterParameters(portName, baudRate, 
                flowControlIn, flowControlOut, dataBits, stopBits, parity);
        serialParameters = new Parameters();
		contador = new ContadorConsumoPapel();
		status = new CRFPStatus();
		CRFPImplFactory implFactory = CRFPImplFactory.getInstance();
		implFactory.setSystemBase(this);
		sequenceMap = implFactory.getSequenceMapInstance();
		responseParser = implFactory.getResponseParserInstance();
		responseParser.setStatusToUpdate(status);
        engine = implFactory.getEngineInstance(this,serialParameters);//new CRFPEngine(this, serialParameters);
        engine.iniciar();
	}

    private void loadProperties() {
		if (logger.isDebugEnabled()) {
			logger.debug("loadProperties() - start");
		}

    	Properties props = new Properties();
    	try {
			props.load(CRFiscalPrinterOperations.class.getResourceAsStream("/com/epa/crprinterdriver/fiscalprinter.properties"));
		} catch (IOException e) {
			logger.error("loadProperties()", e);
		}
		contarPapel = props.getProperty("fiscalprinter.contarpapel", "N").equals("S");

		if (logger.isDebugEnabled()) {
			logger.debug("loadProperties() - end");
		}
    }
    
    public void setPrinterConfig() {
		if (logger.isDebugEnabled()) {
			logger.debug("setPrinterConfig() - start");
		}

        serialParameters.setPortName(crPrinterParameters.getPrinterPort());
        serialParameters.setBaudRate(crPrinterParameters.getBaudRate());
        serialParameters.setParity(crPrinterParameters.getParity());
        serialParameters.setFlowControlIn(crPrinterParameters.getFlowControlIn());
        serialParameters.setFlowControlOut(crPrinterParameters.getFlowControlOut());
        serialParameters.setDatabits(crPrinterParameters.getDataBits());
        serialParameters.setStopbits(crPrinterParameters.getStopBits());
    	
		if (logger.isDebugEnabled()) {
			logger.debug("setPrinterConfig() - end");
		}
    }
	
	/**
	 * Retorna el objeto contador de papel
	 * @return Devuelve contador.
	 */
	public ContadorConsumoPapel getContador() {
		return contador;
	}
	/**
	 * Indica si est� encendido la funcionalidad de conteo de papel
	 * @return Devuelve contarPapel.
	 */
	public boolean isContarPapel() {
		return contarPapel;
	}
	/**
	 * Devuelve la instancia del motor del driver
	 * @return Devuelve engine.
	 */
	public CRFPEngine getEngine() {
		return engine;
	}

	/**
	 * Devuelve la instancia del objeto fachada del subsistema
	 * @return Devuelve operations.
	 */
	public CRFiscalPrinterOperations getOperations() {
		return operations;
	}
	
	/**
	 * Devuelve la instancia del interprete de respuestas del dispositivo fiscal
	 * @return Devuelve responseParser.
	 */
	public CRFPResponseParser getResponseParser() {
		return responseParser;
	}
	/**
	 * Devuelve la instancia del mapa de secuencias del dispositivo fiscal
	 * @return Devuelve sequenceMap.
	 */
	public CRFPSequenceMap getSequenceMap() {
		return sequenceMap;
	}
	/**
	 * Devuelve los par�metros del puerto serial
	 * @return Devuelve serialParameters.
	 */
	public Parameters getSerialParameters() {
		return serialParameters;
	}
	/**
	 * Devuelve la instancia del objeto de estado del driver fiscal
	 * @return Devuelve status.
	 */
	public CRFPStatus getStatus() {
		return status;
	}
}
