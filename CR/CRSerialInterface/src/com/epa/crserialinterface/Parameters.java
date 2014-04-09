/*
 * $Id: Parameters.java,v 1.9 2005/03/09 20:50:05 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CRSerialInterface
 * Paquete		: com.epa.crserialinterface
 * Programa		: Parameters.java
 * Creado por	: Victor Medina <linux@epa.com.ve>
 * Creado en 	: Mar 27, 2004 9:34:19 AM
 * (C) Copyright 2004 SuperFerretería EPA C.A. Todos los Derechos Reservados
 * 
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: Parameters.java,v $
 * Revision 1.9  2005/03/09 20:50:05  programa8
 * CRSerialInterface al 09/03/2005
 *
 * Revision 1.1  2004/07/05 18:42:35  vmedina
 * *** empty log message ***
 *
 * Revision 1.8  2004/04/25 17:42:01  vmedina
 * *** empty log message ***
 *
 * Revision 1.7  2004/04/24 17:20:44  vmedina
 * *** empty log message ***
 *
 * Revision 1.6  2004/04/23 20:10:58  vmedina
 * Agregamos CRSIPreferencesProxy para leer y guardar las preferencias del puerto
 * Agregamos CRSILoggerProxy para Logear utilizando log4J
 * NoSuchPreferencesException fue agregada
 *
 * Revision 1.5  2004/04/20 20:37:17  vmedina
 * Agregamos soporte inicial a log4j
 * La clase ya devuelve un "dato" completo en el inputData no como antes que devolvia de 8 en 8 bits
 * Estamos usando asserts y prefs, para correr con asserts utilizar -ea en el command line cuando se ejecuta la clase
 * El nivel de logeo y verbosidad del mismo varia segun se tenga asserts prendidos o no
 *
 * Revision 1.4  2004/04/11 16:45:14  vmedina
 * *** empty log message ***
 *
 * Revision 1.3  2004/04/09 17:05:38  vmedina
 * *** empty log message ***
 *
 * Revision 1.2  2004/03/29 19:40:11  vmedina
 * Entrada Inicial al CVS, solo tienen las estructuras basicas y los algoritmos principales
 * Falta comenzar la Integración
 *
 * Revision 1.1  2004/03/27 16:31:43  vmedina
 * Esta es la entrada inicial al CVS
 *
 * ===========================================================================
 */
package com.epa.crserialinterface;

import gnu.io.SerialPort;




/**
 * <p>Indicar aquí, en una línea que hace la clase</p>
 * <p>Agregar parrafos que describen como se usa la Clase y un resumen de como 
 * funciona.</p>
 * @author Victor Medina - linux@epa.com.ve -   
 * @version $Revision: 1.9 $<br>$Date: 2005/03/09 20:50:05 $
 * <!-- A continuación indicar referencia a otras páginas o clases parecidas -->
 * <!-- o relacionados, pundiendo indicar inclusive los métodos relacionados -->
 * <!-- por ejemplo: @see     <a href="package-summary.html#charenc">Character encodings</a> -->
 * <!-- por ejemplo: @see     java.lang.Object 								 -->
 * <!-- por ejemplo: @see     java.lang.Object#toNewString() 				 -->
 * @since   CRSerialInterface 1.0
 */
public class Parameters
{
	private String 			portName;
	
	private int    			baudRate;
	private int    			flowControlIn;
	private int    			flowControlOut;
	private int    			databits;
	private int 	   		stopbits;
	private int	    		parity;
	
	private CRSIPreferencesProxy crsiPreferencesProxy = new CRSIPreferencesProxy();
		
	private CRSILoggerProxy crsiLoggerProxy;
	
	private String nombreDispositivo = "";


	/*
	 * ---------------------------------------------------------------------------
	 * Parameters()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 11:35:15 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						CRSerialInterfce 1.0
	 * 
	 */
	public Parameters(){
		this("", 9600, SerialPort.FLOWCONTROL_NONE,	SerialPort.FLOWCONTROL_NONE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		this.crsiLoggerProxy.crsiDoLogging("Parameters fue inicializado sin parametros, utilizando valores por defecto", 2);
	}


	/*
	 * ---------------------------------------------------------------------------
	 * Parameters()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 11:35:31 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						CRSerialInterface 1.0
	 * @param portName
	 * @param baudRate
	 * @param flowControlIn
	 * @param flowControlOut
	 * @param databits
	 * @param stopbits
	 * @param parity
	 */
	public Parameters(String portName, int baudRate, int flowControlIn,
						int flowControlOut, int databits, int stopbits,
						int parity) 
	{
		initCRSILogger();
		this.portName = portName;
		this.baudRate = baudRate;
		this.flowControlIn = flowControlIn;
		this.flowControlOut = flowControlOut;
		this.databits = databits;
		this.stopbits = stopbits;
		this.parity = parity;
		
		/*
		 * Iniciamos el logeo de la clase
		 */
		crsiLoggerProxy.crsiDoLogging("Port Name: "+portName,1);
		crsiLoggerProxy.crsiDoLogging("Baud Rate: "+baudRate,1);
		crsiLoggerProxy.crsiDoLogging("Flow Control In: "+flowControlIn,1);
		crsiLoggerProxy.crsiDoLogging("Flow Control Out: "+flowControlOut,1);
		crsiLoggerProxy.crsiDoLogging("Data Bits: "+databits,1);
		crsiLoggerProxy.crsiDoLogging("Stop Bits: "+stopbits,1);
		crsiLoggerProxy.crsiDoLogging("Parity: "+parity,1);
	}
	
	public Parameters(String portName){
		initCRSILogger();
		
		if(crsiPreferencesProxy.isSerialPortConfigured(portName)){
			this.portName = portName;
			
			try
			{
				this.baudRate = Integer.parseInt(crsiPreferencesProxy.getCurrentBaudRateConfigForPort(portName));
				
				this.flowControlIn = stringToFlow(crsiPreferencesProxy.getCurrentFlowControlInConfigForPort(portName));
				this.flowControlOut = stringToFlow(crsiPreferencesProxy.getCurrentFlowControlOutConfigForPort(portName));
				
				this.databits = Integer.parseInt(crsiPreferencesProxy.getCurrentDataBitsConfigForPort(portName));
				this.stopbits = Integer.parseInt(crsiPreferencesProxy.getCurrentStopBitConfigForPort(portName));
				
				this.parity =  stringToParity(crsiPreferencesProxy.getCurrentParityConfigForPort(portName));
			}
			catch (NumberFormatException e)
			{
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			}
			catch (NoSuchPreferenceException e)
			{
				crsiLoggerProxy.crsiDoLogging("Ocurrio un error al tratar de recuperar las preferencias del BackingStorage",3);
				crsiLoggerProxy.crsiDoLogging(e.getMessage(),3);
				e.printStackTrace();
			}
			
		}
		else{
			crsiLoggerProxy.crsiDoLogging("El puerto que esta tratando de instanciar no tiene una configuracion asignada, usando valores por defectos",3);
			this.portName = portName;
			this.baudRate = 9600;
			this.flowControlIn = SerialPort.FLOWCONTROL_NONE;
			this.flowControlOut = SerialPort.FLOWCONTROL_NONE;
			this.databits = SerialPort.DATABITS_8;
			this.stopbits = SerialPort.STOPBITS_1;
			this.parity = SerialPort.PARITY_NONE;
		}	
	}
	
	
	private void initCRSILogger(){
		if(crsiLoggerProxy == null){
			crsiLoggerProxy = new CRSILoggerProxy(getClass().getName());
		}
	}
	

	
	/*
	 * ---------------------------------------------------------------------------
	 * void setPortName()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 11:44:53 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param portName
	 */
	public void setPortName(String portName) {
		this.portName = portName;
		crsiLoggerProxy.crsiDoLogging("Port Name(Nuevo Valor): "+portName,1);
	}

	
	/*
	 * ---------------------------------------------------------------------------
	 * String getPortName()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 11:45:28 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getPortName() {
		return portName;
	}

	
	/*
	 * ---------------------------------------------------------------------------
	 * void setBaudRate()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 11:50:30 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param baudRate
	 */
	public void setBaudRate(int baudRate) {
		this.baudRate = baudRate;
		crsiLoggerProxy.crsiDoLogging("Baud Rate(Nuevo Valor): "+baudRate,1);
	}

	
	/*
	 * ---------------------------------------------------------------------------
	 * void setBaudRate()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 12:13:49 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param baudRate
	 */
	public void setBaudRate(String baudRate) {
		this.baudRate = Integer.parseInt(baudRate);
		crsiLoggerProxy.crsiDoLogging("Baud Rate(Nuevo Valor): "+baudRate,1);
	}

	
	/*
	 * ---------------------------------------------------------------------------
	 * int getBaudRate()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 11:53:38 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return int 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public int getBaudRate() {
		return baudRate;
	}

	
	/*
	 * ---------------------------------------------------------------------------
	 * String getBaudRateString()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 11:54:02 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getBaudRateString() {
		return Integer.toString(baudRate);
	}

	
	/*
	 * ---------------------------------------------------------------------------
	 * void setFlowControlIn()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 11:54:31 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param flowControlIn
	 */
	public void setFlowControlIn(int flowControlIn) {
		this.flowControlIn = flowControlIn;
		crsiLoggerProxy.crsiDoLogging("Flow Control In(Nuevo Valor): "+flowControlIn,1);
	}

	
	/*
	 * ---------------------------------------------------------------------------
	 * void setFlowControlIn()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 11:55:06 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param flowControlIn
	 */
	public void setFlowControlIn(String flowControlIn) {
		this.flowControlIn = stringToFlow(flowControlIn);
		crsiLoggerProxy.crsiDoLogging("Flow Control In(Nuevo Valor): "+flowControlIn,1);
	}

	
	/*
	 * ---------------------------------------------------------------------------
	 * int getFlowControlIn()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 11:55:35 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return int 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public int getFlowControlIn() {
		return flowControlIn;
	}


	/*
	 * ---------------------------------------------------------------------------
	 * String getFlowControlInString()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 11:56:17 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getFlowControlInString() {
		return flowToString(flowControlIn);
	}

	
	/*
	 * ---------------------------------------------------------------------------
	 * void setFlowControlOut()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 11:57:05 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param flowControlOut
	 */
	public void setFlowControlOut(int flowControlOut) {
		this.flowControlOut = flowControlOut;
		crsiLoggerProxy.crsiDoLogging("Flow Control Out(Nuevo Valor): "+flowControlOut,1);
	}

	
	/*
	 * ---------------------------------------------------------------------------
	 * void setFlowControlOut()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 11:57:23 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param flowControlOut
	 */
	public void setFlowControlOut(String flowControlOut) {
		this.flowControlOut = stringToFlow(flowControlOut);
		crsiLoggerProxy.crsiDoLogging("Flow Control Out(Nuevo Valor): "+flowControlOut,1);
	}

	
	/*
	 * ---------------------------------------------------------------------------
	 * int getFlowControlOut()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 11:57:47 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return int 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public int getFlowControlOut() {
		return flowControlOut;
	}


	/*
	 * ---------------------------------------------------------------------------
	 * String getFlowControlOutString()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 11:58:42 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getFlowControlOutString() {
		return flowToString(flowControlOut);
	}

	
	/*
	 * ---------------------------------------------------------------------------
	 * void setDatabits()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 11:59:14 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param databits
	 */
	public void setDatabits(int databits) {
		this.databits = databits;
		crsiLoggerProxy.crsiDoLogging("Data Bits(Nuevo Valor): "+databits,1);
	}

	
	/*
	 * ---------------------------------------------------------------------------
	 * void setDatabits()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 11:59:37 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param databits
	 */
	public void setDatabits(String databits) {
		if (databits.equals("5")) {
			this.databits = SerialPort.DATABITS_5;
		}
	
		if (databits.equals("6")) {
			this.databits = SerialPort.DATABITS_6;
		}
	
		if (databits.equals("7")) {
			this.databits = SerialPort.DATABITS_7;
		}
	
		if (databits.equals("8")) {
			this.databits = SerialPort.DATABITS_8;
		}
		crsiLoggerProxy.crsiDoLogging("Data Bits(Nuevo Valor): "+databits,1);
	}



	/*
	 * ---------------------------------------------------------------------------
	 * int getDatabits()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 12:01:14 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return int 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public int getDatabits() {
		return databits;
	}

	
	/*
	 * ---------------------------------------------------------------------------
	 * String getDatabitsString()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 12:02:03 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getDatabitsString() {
		switch (databits) {
	
		case SerialPort.DATABITS_5:
			return "5";
	
		case SerialPort.DATABITS_6:
			return "6";
	
		case SerialPort.DATABITS_7:
			return "7";
	
		case SerialPort.DATABITS_8:
			return "8";
	
		default:
			return "8";
		}
	}

	
	/*
	 * ---------------------------------------------------------------------------
	 * void setStopbits()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 12:02:30 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param stopbits
	 */
	public void setStopbits(int stopbits) {
		this.stopbits = stopbits;
	}

	
	/*
	 * ---------------------------------------------------------------------------
	 * void setStopbits()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 12:02:59 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param stopbits
	 */
	public void setStopbits(String stopbits) {
		if (stopbits.equals("1")) {
			this.stopbits = SerialPort.STOPBITS_1;
		}
	
		if (stopbits.equals("1.5")) {
			this.stopbits = SerialPort.STOPBITS_1_5;
		}
	
		if (stopbits.equals("2")) {
			this.stopbits = SerialPort.STOPBITS_2;
		}
		crsiLoggerProxy.crsiDoLogging("Stop Bits(Nuevo Valor): "+stopbits,1);
	}


	/*
	 * ---------------------------------------------------------------------------
	 * int getStopbits()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 12:03:31 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return int 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public int getStopbits() {
		return stopbits;
	}


	/*
	 * ---------------------------------------------------------------------------
	 * String getStopbitsString()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 12:04:18 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getStopbitsString() {
		switch (stopbits) {
	
		case SerialPort.STOPBITS_1:
			return "1";
	
		case SerialPort.STOPBITS_1_5:
			return "1.5";
	
		case SerialPort.STOPBITS_2:
			return "2";
	
		default:
			return "1";
		}
	}

	/*
	 * ---------------------------------------------------------------------------
	 * void setParity()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 12:05:01 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param parity
	 */
	public void setParity(int parity) {
		this.parity = parity;
		crsiLoggerProxy.crsiDoLogging("Parity(Nuevo Valor): "+parity,1);
	}

	
	/*
	 * ---------------------------------------------------------------------------
	 * void setParity()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 12:05:29 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return void 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param parity
	 */
	public void setParity(String parity) {
		if (parity.equals("None")) {
			this.parity = SerialPort.PARITY_NONE;
		}
	
		if (parity.equals("Even")) {
			this.parity = SerialPort.PARITY_EVEN;
		}
	
		if (parity.equals("Odd")) {
			this.parity = SerialPort.PARITY_ODD;
		}
		crsiLoggerProxy.crsiDoLogging("Parity(Nuevo Valor): "+parity,1);	
	}

	/*
	 * ---------------------------------------------------------------------------
	 * int getParity()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 12:06:21 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return int 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public int getParity() {
		return parity;
	}

	
	/*
	 * ---------------------------------------------------------------------------
	 * String getParityString()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 12:07:16 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @return
	 */
	public String getParityString() {
		switch (parity) {
	
		case SerialPort.PARITY_NONE:
			return "None";
	
		case SerialPort.PARITY_EVEN:
			return "Even";
	
		case SerialPort.PARITY_ODD:
			return "Odd";
	
		default:
			return "None";
		}
	}
	
	/*
	 * ---------------------------------------------------------------------------
	 * int stringToParity()
	 * Creado por: 	vmedina
	 * Creado el:	Apr 25, 2004 1:18:31 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return int 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param parity
	 * @return
	 */
	private int stringToParity(String parity){
		
		if(parity.equals("None")){
			return SerialPort.PARITY_NONE;
		}
		
		if(parity.equals("Even")){
			return SerialPort.PARITY_EVEN;
		}
		
		if(parity.equals("Odd")){
			return SerialPort.PARITY_ODD;
		}
		return SerialPort.PARITY_NONE;
		
	}
	

	/*
	 * ---------------------------------------------------------------------------
	 * int stringToFlow()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 12:09:20 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return int 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param flowControl
	 * @return
	 */
	private int stringToFlow(String flowControl) {
		if (flowControl.equals("None")) {
			return SerialPort.FLOWCONTROL_NONE;
		}
	
		if (flowControl.equals("Xon/Xoff Out")) {
			return SerialPort.FLOWCONTROL_XONXOFF_OUT;
		}
	
		if (flowControl.equals("Xon/Xoff In")) {
			return SerialPort.FLOWCONTROL_XONXOFF_IN;
		}
	
		if (flowControl.equals("RTS/CTS In")) {
			return SerialPort.FLOWCONTROL_RTSCTS_IN;
		}
	
		if (flowControl.equals("RTS/CTS Out")) {
			return SerialPort.FLOWCONTROL_RTSCTS_OUT;
		}
	
		return SerialPort.FLOWCONTROL_NONE;
	}


	/*
	 * ---------------------------------------------------------------------------
	 * String flowToString()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 12:11:05 PM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oración que hace el método, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios párrafos, cómo se usa y/o cómo funciona el método<p>
	 * Los párrafos se separan con:  <p>
	 * <p>Se debe dejar una línea en blanco entre ésta descripción y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parámetro1}	frase indicado que significa el parámetro o para que se va a utilizar el valor de este parámetro
	 * TODO @param {nombre_parám2}		Si inicias esta oración con mayúsculas, terminala con punto (especificación de JavaDoc).
	 * 									La descripción de parámetro puede ocupar varias líneas si deseas.
	 * TODO @return String 		Describir que significa lo que se está devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepción.
	 * TODO @since 						Indicar desde que versión del Archivo éxiste el método.
	 * TODO @deprecated 				Si la función se vuelve obsoleta se deja esta línea, sino se elimina.
	 * @param flowControl
	 * @return
	 */
	private String flowToString(int flowControl) {
		switch (flowControl) {
	
		case SerialPort.FLOWCONTROL_NONE:
			return "None";
	
		case SerialPort.FLOWCONTROL_XONXOFF_OUT:
			return "Xon/Xoff Out";
	
		case SerialPort.FLOWCONTROL_XONXOFF_IN:
			return "Xon/Xoff In";
	
		case SerialPort.FLOWCONTROL_RTSCTS_IN:
			return "RTS/CTS In";
	
		case SerialPort.FLOWCONTROL_RTSCTS_OUT:
			return "RTS/CTS Out";
	
		default:
			return "None";
		}
	}		
}
