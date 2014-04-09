/*
 * $Id: CRPrinterParameters.java,v 1.1 2005/02/01 20:31:41 acastillo Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CRPrinterDriver
 * Paquete		: com.epa.crprinterdriver
 * Programa		: Parameters.java
 * Creado por	: vmedina
 * Creado en 	: May 16, 2004 9:07:41 AM
 * (C) Copyright 2004 SuperFerretería EPA C.A. Todos los Derechos Reservados
 * 
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: CRPrinterParameters.java,v $
 * Revision 1.1  2005/02/01 20:31:41  acastillo
 * CRFiscalPrinterDriver 1.1
 *
 * Revision 1.2  2004/05/26 20:19:21  vmedina
 * *** empty log message ***
 *
 * Revision 1.1  2004/05/20 19:26:35  vmedina
 * *** empty log message ***
 *
 * ===========================================================================
 */
package com.epa.crprinterdriver;

import com.epa.preferencesproxy.*;



/**
 * <p>Carga los parametros de la configuración serial del puerto de la 
 * impresora</p>
 * @author $Author: acastillo $  
 * @version $Revision: 1.1 $<br>$Date: 2005/02/01 20:31:41 $
 * @since   CRPrinterDriver ver. 1.0.0
 */
public class CRPrinterParameters {
    
    private EPAPreferenceProxy printerDeviceConfig;
    private String printerPort;
    private String baudRate;
    private String flowControlIn;
    private String flowControlOut;
    private String dataBits;
    private String stopBits;
    private String parity;

    /*
     * ---------------------------------------------------------------------------
     * Parameters()
     * Creado por: 	vmedina
     * Creado el:	May 18, 2004 9:07:11 AM
     * ---------------------------------------------------------------------------
     */
    /**
     * <p> El constructor debe saber que puerto debe abrir para trabajar con la 
     * impresora </p>
     * @since 						CRPrinterDriver ver. 1.0.0
     * @param serialPort El puerto serial con el que vamos a trabajar
     */
    public CRPrinterParameters(String serialPort){
        this.printerPort = serialPort;
        printerDeviceConfig = new EPAPreferenceProxy("CRPrinterDeviceConfig");
        
        /* Configuramos los parámetros del puerto serial de la impresora*/        
        setPrinterPort(this.printerPort);
        setBaudRate(getBaudRateFromJavaPrefs());
        setFlowControlIn(getFlowControlInFromJavaPrefs());
        setFlowControlOut(getFlowControlOutFromJavaPrefs());
        setDataBits(getDataBitsFromJavaPrefs());
        setStopBits(getStopBitsFromJavaPrefs());
        setParity(getParityFromJavaPrefs());
        
    }
    
    
    /*
     * ---------------------------------------------------------------------------
     * CRPrinterParameters()
     * Creado por: 	Victor Medina - linux@epa.com.ve
     * Creado el:	May 26, 2004 9:12:05 AM
     * ---------------------------------------------------------------------------
     */
    /**
     * <p>Constructor: por defecto este no extrae los parametros de las preferencias, 
     * es necesario indicarle de forma explicita todos los parametros</p>
     * @since  	CRPrinterDriver ver. 1.0.0	
     * @param serialPort  El puerto seial a ser utilizado
     * @param baudRate  El valor de Baud Rate a ser utilizado 
     * @param flowControlIn El valor de Flow Control In a ser utilizado
     * @param flowControlOut El valor de Flow Control Out a ser Utilizado
     * @param dataBits El valor de Data Bits a ser utilizado
     * @param stopBits El  valor de Stops Bits a ser utilizado
     * @param parity  El valor de Parity a ser utilizado
     */
    public CRPrinterParameters(String serialPort, String baudRate, String flowControlIn,
            String flowControlOut, String dataBits, String stopBits, String parity){
        
        /* Configuramos los parámetros del puerto serial de la impresora */
        setPrinterPort(serialPort);
        setBaudRate(baudRate);
        setFlowControlIn(flowControlIn);
        setFlowControlOut(flowControlOut);
        setDataBits(dataBits);
        setStopBits(stopBits);
        setParity(parity);
    }

    
    /**
     * @return Returns the baudRate.
     */
    public String getBaudRateFromJavaPrefs() {
        try {
            return printerDeviceConfig.getConfigStringForParameter(printerPort, "Baud Rate");
        } catch (NoSuchNodeException e) {
            e.printStackTrace();
        } catch (UnidentifiedPreferenceException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * @param baudRate The baudRate to set.
     */
    public void setBaudRate(String baudRate) {
        this.baudRate = baudRate;
    }
    /**
     * @return Returns the dataBits.
     */
    public String getDataBitsFromJavaPrefs(){
        try {
            return printerDeviceConfig.getConfigStringForParameter(printerPort, "Data Bits");
        } catch (NoSuchNodeException e) {
            e.printStackTrace();
        } catch (UnidentifiedPreferenceException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * @param dataBits The dataBits to set.
     */
    public void setDataBits(String dataBits) {
        this.dataBits = dataBits;
    }
    /**
     * @return Returns the flowControlIn.
     */
    public String getFlowControlInFromJavaPrefs() {
        try {
            return printerDeviceConfig.getConfigStringForParameter(printerPort, "Flow Control In");
        } catch (NoSuchNodeException e) {
            e.printStackTrace();
        } catch (UnidentifiedPreferenceException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    /**
     * @param flowControlIn The flowControlIn to set.
     */
    public void setFlowControlIn(String flowControlIn) {
        this.flowControlIn = flowControlIn;
    }
    /**
     * @return Returns the flowControlOut.
     */
    public String getFlowControlOutFromJavaPrefs() {
        try {
            return printerDeviceConfig.getConfigStringForParameter(printerPort, "Flow Control Out");
        } catch (NoSuchNodeException e) {
            e.printStackTrace();
        } catch (UnidentifiedPreferenceException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    /**
     * @param flowControlOut The flowControlOut to set.
     */
    public void setFlowControlOut(String flowControlOut) {
        this.flowControlOut = flowControlOut;
    }
    /**
     * @return Returns the parity.
     */
    public String getParityFromJavaPrefs() {
        try {
            return printerDeviceConfig.getConfigStringForParameter(printerPort, "Parity");
        } catch (NoSuchNodeException e) {
            e.printStackTrace();
        } catch (UnidentifiedPreferenceException e) {
            e.printStackTrace();
        }
        return null;
        
    }
    /**
     * @param parity The parity to set.
     */
    public void setParity(String parity) {
        this.parity = parity;
    }
    /**
     * @return Returns the printerPort.
     */
    public String getPrinterPort() {
        return printerPort;
    }
    /**
     * @param printerPort The printerPort to set.
     */
    public void setPrinterPort(String printerPort) {
        this.printerPort = printerPort;
    }
    /**
     * @return Returns the stopBits.
     */
    public String getStopBitsFromJavaPrefs() {
        try {
            return printerDeviceConfig.getConfigStringForParameter(printerPort, "Stop Bits");
        } catch (NoSuchNodeException e) {
            e.printStackTrace();
        } catch (UnidentifiedPreferenceException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * @param stopBits The stopBits to set.
     */
    public void setStopBits(String stopBits) {
        this.stopBits = stopBits;
    }
    /**
     * @return Returns the baudRate.
     */
    public String getBaudRate() {
        return baudRate;
    }
    /**
     * @return Returns the dataBits.
     */
    public String getDataBits() {
        return dataBits;
    }
    /**
     * @return Returns the flowControlIn.
     */
    public String getFlowControlIn() {
        return flowControlIn;
    }
    /**
     * @return Returns the flowControlOut.
     */
    public String getFlowControlOut() {
        return flowControlOut;
    }
    /**
     * @return Returns the parity.
     */
    public String getParity() {
        return parity;
    }
    /**
     * @return Returns the stopBits.
     */
    public String getStopBits() {
        return stopBits;
    }
}
