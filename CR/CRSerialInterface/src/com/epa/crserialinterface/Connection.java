/*
 * $Id: Connection.java,v 1.12 2005/04/21 13:47:06 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerreterï¿½a EPA C.A. 
 *
 * Proyecto		: CRSerialInterface
 * Paquete		: com.epa.crserialinterface
 * Programa		: Connection.java
 * Creado por	: Victor Medina <linux@epa.com.ve>
 * Creado en 	: Mar 27, 2004 9:28:20 AM
 * (C) Copyright 2004 SuperFerreterï¿½a EPA C.A. Todos los Derechos Reservados
 * 
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: Connection.java,v $
 * Revision 1.12  2005/04/21 13:47:06  programa8
 * Agregado manejo de eventos y escuchas en llegadas de señales seriales
 *
 * Revision 1.11  2005/03/10 18:14:40  programa8
 * Ultimos toques Interfaz Serial
 *
 * Revision 1.10  2005/03/10 14:23:16  programa8
 * Mejora de SerialInterface para manejos de lectura de disp. seriales
 *
 * Revision 1.9  2005/03/09 20:50:05  programa8
 * CRSerialInterface al 09/03/2005
 *
 * Revision 1.6  2004/12/08 15:22:31  vmedina
 * *** empty log message ***
 *
 * Revision 1.5  2004/10/25 19:37:44  vmedina
 * *** empty log message ***
 *
 * Revision 1.1  2004/07/05 18:42:35  vmedina
 * *** empty log message ***
 *
 * Revision 1.12  2004/06/03 19:50:23  vmedina
 * *** empty log message ***
 *
 * Revision 1.11  2004/05/31 17:57:23  vmedina
 * *** empty log message ***
 *
 * Revision 1.10  2004/05/26 20:19:31  vmedina
 * *** empty log message ***
 *
 * Revision 1.9  2004/05/20 19:27:27  vmedina
 * *** empty log message ***
 *
 * Revision 1.8  2004/04/24 17:20:44  vmedina
 * *** empty log message ***
 *
 * Revision 1.7  2004/04/23 20:10:58  vmedina
 * Agregamos CRSIPreferencesProxy para leer y guardar las preferencias del puerto
 * Agregamos CRSILoggerProxy para Logear utilizando log4J
 * NoSuchPreferencesException fue agregada
 *
 * Revision 1.6  2004/04/20 20:37:17  vmedina
 * Agregamos soporte inicial a log4j
 * La clase ya devuelve un "dato" completo en el inputData no como antes que devolvia de 8 en 8 bits
 * Estamos usando asserts y prefs, para correr con asserts utilizar -ea en el command line cuando se ejecuta la clase
 * El nivel de logeo y verbosidad del mismo varia segun se tenga asserts prendidos o no
 *
 * Revision 1.5  2004/04/06 20:18:07  vmedina
 * *** empty log message ***
 *
 * Revision 1.4  2004/03/31 17:38:18  vmedina
 * Este es el primer comit funcional, todas las funciones basicas estan
 * implementadas y funcionales, esta version es tuilizable
 *
 * Revision 1.3  2004/03/29 19:45:32  vmedina
 * *** empty log message ***
 *
 * Revision 1.2  2004/03/29 19:40:11  vmedina
 * Entrada Inicial al CVS, solo tienen las estructuras basicas y los algoritmos principales
 * Falta comenzar la Integraciï¿½n
 *
 * Revision 1.1  2004/03/27 13:44:51  vmedina
 * Esta es la entrada inicial al CVS
 *
 * ===========================================================================
 */
package com.epa.crserialinterface;

import gnu.io.CommPortIdentifier;
import gnu.io.CommPortOwnershipListener;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.TooManyListenersException;
import java.util.Vector;

import javax.swing.JTextArea;

/**
 * <p>Connection es la clase que se encarga de abrir, cerrar, enviar y recibir datos del puerto serial</p>
 * <p>Esta clase debe ser instanciada con un objeto <code>Parameters</code> que contenga los parametros</p>
 * <p>necesarios para poder abrir y utilizar correctamente el puerto</p>
 * @author $Author: programa8 $  
 * @version $Revision: 1.12 $<br>$Date: 2005/04/21 13:47:06 $
 * <!-- A continuaciï¿½n indicar referencia a otras pï¿½ginas o clases parecidas -->
 * <!-- o relacionados, pundiendo indicar inclusive los mï¿½todos relacionados -->
 * <!-- por ejemplo: @see     <a href="package-summary.html#charenc">Character encodings</a> -->
 * <!-- por ejemplo: @see     java.lang.Object 								 -->
 * <!-- por ejemplo: @see     java.lang.Object#toNewString() 				 -->
 * @since   CRSerialInterface 1.0
 */
public class Connection implements  SerialPortEventListener, CommPortOwnershipListener
{
	private boolean				open;
	private Parameters			parameters;
	private OutputStream		os;
	private InputStream			is;
	private CommPortIdentifier	portID;
	private SerialPort			serialPort;
	private JTextArea			outputMessage;
	private JTextArea			inputMessage;
	private StringBuffer		tempBufer = new StringBuffer("");
	private CRSILoggerProxy 	crsiLoggerProxy;
	private char				finTrama = '\r';
	private int					tamanoTrama = 0;
	private boolean 			flushAtEnd = false;
	
	// Manejo de eventos de recepcion de señales
	private Vector				signalListeners = new Vector();
	
	
	/*
	 * ---------------------------------------------------------------------------
	 * Connection()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 10:07:07 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * <p>Constructor por defecto de la Clase <code>Connection</code> </p>
	 * <p>Toma como argumentos un objeto de tipo <code>Parent</code> y un objeto de tipo <code>Parameters</code></p>
	 * 
	 * @param parent 
	 * @param parameters
	 */
	public Connection(Parameters parameters, JTextArea outputMessage, JTextArea inputMessage){
		this.parameters = parameters;
		this.inputMessage = inputMessage;
		this.outputMessage = outputMessage;
		open = false;
		crsiLoggerProxy = new CRSILoggerProxy(getClass().getName());
	}


	/*
	 * ---------------------------------------------------------------------------
	 * void closeConnection()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 10:07:48 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oraciï¿½n que hace el mï¿½todo, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios pï¿½rrafos, cï¿½mo se usa y/o cï¿½mo funciona el mï¿½todo<p>
	 * Los pï¿½rrafos se separan con:  <p>
	 * <p>Se debe dejar una lï¿½nea en blanco entre ï¿½sta descripciï¿½n y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parï¿½metro1}	frase indicado que significa el parï¿½metro o para que se va a utilizar el valor de este parï¿½metro
	 * TODO @param {nombre_parï¿½m2}		Si inicias esta oraciï¿½n con mayï¿½sculas, terminala con punto (especificaciï¿½n de JavaDoc).
	 * 									La descripciï¿½n de parï¿½metro puede ocupar varias lï¿½neas si deseas.
	 * TODO @return void 		Describir que significa lo que se estï¿½ devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepciï¿½n.
	 * TODO @since 						Indicar desde que versiï¿½n del Archivo ï¿½xiste el mï¿½todo.
	 * TODO @deprecated 				Si la funciï¿½n se vuelve obsoleta se deja esta lï¿½nea, sino se elimina.
	 * 
	 */
	public void closeConnection() {

		/*
		 * Si el puerto ya esta cerrado, simplemente regresa
		 */
		if(!open){
			//crsiLoggerProxy.crsiDoLogging("El puerto ya estaba cerrado antes de intentar cerrarlo",1);
			return;
			
		}

		/*
		 * Limpiamos el Buffer antes de Cerrar
		 */
		outputMessage.setText("");

		 /*
		  * Nos aseguramos del que puerto tenga una referencia
		  * para evitar problemas antes de desconectarlo
		  */
		if(serialPort != null){
			try{
				/*
				 * Cerramos los I/O Streams
				 */
				 os.close();
				 is.close();
				 crsiLoggerProxy.crsiDoLogging("Cerramos el los I/O Streams",1);
			}
			catch(IOException e){
				crsiLoggerProxy.crsiDoLogging("Imposible cerrar los I/O Streams",3);
				crsiLoggerProxy.crsiDoLogging(e.getMessage(),3);
				crsiLoggerProxy.crsiDoLogging(e.getStackTrace().toString(),3);
			}
			serialPort.close();
			portID.removePortOwnershipListener(this);
		}
		open = false;
		outputMessage.setText(null);
		crsiLoggerProxy.crsiDoLogging("El puerto está cerrado",1);
	}

	
	/*
	 * ---------------------------------------------------------------------------
	 * boolean isOpen()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 10:10:13 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oraciï¿½n que hace el mï¿½todo, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios pï¿½rrafos, cï¿½mo se usa y/o cï¿½mo funciona el mï¿½todo<p>
	 * Los pï¿½rrafos se separan con:  <p>
	 * <p>Se debe dejar una lï¿½nea en blanco entre ï¿½sta descripciï¿½n y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parï¿½metro1}	frase indicado que significa el parï¿½metro o para que se va a utilizar el valor de este parï¿½metro
	 * TODO @param {nombre_parï¿½m2}		Si inicias esta oraciï¿½n con mayï¿½sculas, terminala con punto (especificaciï¿½n de JavaDoc).
	 * 									La descripciï¿½n de parï¿½metro puede ocupar varias lï¿½neas si deseas.
	 * TODO @return boolean 		Describir que significa lo que se estï¿½ devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepciï¿½n.
	 * TODO @since 						Indicar desde que versiï¿½n del Archivo ï¿½xiste el mï¿½todo.
	 * TODO @deprecated 				Si la funciï¿½n se vuelve obsoleta se deja esta lï¿½nea, sino se elimina.
	 * @return
	 */
	public boolean isOpen() {
		crsiLoggerProxy.crsiDoLogging("El puerto esta abierto?: "+new Boolean(open).toString(),1);
		return open;
	}



	/*
	 * ---------------------------------------------------------------------------
	 * void openConnection()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 10:11:44 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oraciï¿½n que hace el mï¿½todo, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios pï¿½rrafos, cï¿½mo se usa y/o cï¿½mo funciona el mï¿½todo<p>
	 * Los pï¿½rrafos se separan con:  <p>
	 * <p>Se debe dejar una lï¿½nea en blanco entre ï¿½sta descripciï¿½n y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parï¿½metro1}	frase indicado que significa el parï¿½metro o para que se va a utilizar el valor de este parï¿½metro
	 * TODO @param {nombre_parï¿½m2}		Si inicias esta oraciï¿½n con mayï¿½sculas, terminala con punto (especificaciï¿½n de JavaDoc).
	 * 									La descripciï¿½n de parï¿½metro puede ocupar varias lï¿½neas si deseas.
	 * TODO @return void 		Describir que significa lo que se estï¿½ devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepciï¿½n.
	 * TODO @since 						Indicar desde que versiï¿½n del Archivo ï¿½xiste el mï¿½todo.
	 * TODO @deprecated 				Si la funciï¿½n se vuelve obsoleta se deja esta lï¿½nea, sino se elimina.
	 * @throws SerialTestDemoExceptions
	 */
	public void openConnection() throws SerialExceptions {

		/*
		 * Tratamos de Obtener un CommPortIdentifier object del puerto
		 * que deseamos abrir
		 */
		 try {
			crsiLoggerProxy.crsiDoLogging("Tratando de abrir el puerto: "+parameters.getPortName().toString(),1);
			portID = CommPortIdentifier.getPortIdentifier(parameters.getPortName());
		 } catch (NoSuchPortException e) {
		 	crsiLoggerProxy.crsiDoLogging("El puerto no existe",3);
		 	crsiLoggerProxy.crsiDoLogging(e.getMessage(),3);
		 	crsiLoggerProxy.crsiDoLogging(e.getStackTrace().toString(),3);
		 	throw new SerialExceptions(e.getMessage());
		 }

		 /*
		  * Abrimos un puerto representado por el objeto CommPortIdentifier.
		  * Le damos un time out relativamente largo en caso de que otras
		  * aplicaciones quieran accederlo
		  */
		  try{
		  	serialPort = (SerialPort) portID.open("SerialDemo",30000);
		  }catch(PortInUseException e){
		  	crsiLoggerProxy.crsiDoLogging("No puedo utilizar el puerto, alguien mas lo esta utilizando",3);
		  	crsiLoggerProxy.crsiDoLogging(e.getMessage(),3);
		  	crsiLoggerProxy.crsiDoLogging(e.getStackTrace().toString(),3);
		  	throw new SerialExceptions(e.getMessage());
		  }

		  /*
		   * Configuramos los parametros de la conexion, si no podemos hacerlo
		   * Cerramos el puerto antes de general la conexion
		   */
		   
			  try{
			   	configureSerialParameters();
			   	crsiLoggerProxy.crsiDoLogging("Configuramos los parametros del puerto serial",1);
			   }
			   catch(SerialExceptions e){
			   	crsiLoggerProxy.crsiDoLogging("Ocurrio un problema al tratar de configurar el puerto serial: "+parameters.getPortName().toString(),3);
			   	crsiLoggerProxy.crsiDoLogging(e.getMessage(),3);
			   	crsiLoggerProxy.crsiDoLogging(e.getStackTrace().toString(),3);
				serialPort.close();
				throw e;
			   }
		  
		   /*
			* Abrimos los input y output streams para la conexion
			* Si no abren, cerramos el puerto antes de generar la
			* exception
			*/
		   try{
			os = serialPort.getOutputStream();
			is = serialPort.getInputStream();
			crsiLoggerProxy.crsiDoLogging("Asignamos los I/O Streams a sus correspondientes objeos",1);
		   }
		   catch(IOException e){
		   	crsiLoggerProxy.crsiDoLogging("Ocurrio un problema al tratar de asignar los I/O Streams a los objetos",3);
		   	crsiLoggerProxy.crsiDoLogging(e.getMessage(),3);
		   	crsiLoggerProxy.crsiDoLogging(e.getStackTrace().toString(),3);
			serialPort.close();
			throw new SerialExceptions("Error al tratar de acceder los I/O streams");
		   }

		   /*
		    * Expresamos el deseo de ser notificados cuando haya datos de entrada
		    */
		   serialPort.notifyOnDataAvailable(true);

		   /*
		    * Expresamos el deseo de ser notificados cuando un Break Interrupt ocurra
		    */
		   serialPort.notifyOnBreakInterrupt(true);
			   
		   /*
		    * Expresamos el deseo de ser notificados cuando el bit de CD cambie
		    */
		   serialPort.notifyOnCarrierDetect(true);

		   /*
		    * Expresamos el deseo de ser notificados cuando el bit de CTS cambie de 
		    * estado
		    */
		   serialPort.notifyOnCTS(true);			   

		   /*
		    * Expresamos el deseo de ser notificados cuando el bit de DSR cambie 
		    * de estado
		    */
		   serialPort.notifyOnDSR(true);
			   
		   /*
		    * Expresamos el deseo de ser notificados cuando el bit de RI cambie de
		    * estado
		    */
		   serialPort.notifyOnRingIndicator(true);
		   
		   
		   try{
				serialPort.addEventListener(this);
				crsiLoggerProxy.crsiDoLogging("Agregamos el EventListener al serial port",1);
			 }
			 catch(TooManyListenersException e){
			 	crsiLoggerProxy.crsiDoLogging("Ocurrio un error al tratar de agregar un EvenListener al serial port",3);
			 	crsiLoggerProxy.crsiDoLogging(e.getMessage(),3);
			 	crsiLoggerProxy.crsiDoLogging(e.getStackTrace().toString(),3);
				serialPort.close();
				throw new SerialExceptions("he agregado demasiado listeners");
			 }
			   

			 /*
			  * Configuramos un timeout para permitir que nos salgamos
			  * de un loop durante un manejo de input
			  */
			  try{
				serialPort.enableReceiveTimeout(10);
				crsiLoggerProxy.crsiDoLogging("Agregamos un timeout al puerto serial",1);
			  }
			  catch(UnsupportedCommOperationException e){
			  	crsiLoggerProxy.crsiDoLogging("Ocurrio un error al tratar de agregar un timeout a las operaciones del puerto serial",3);
			  	crsiLoggerProxy.crsiDoLogging(e.getMessage(),3);
			  	crsiLoggerProxy.crsiDoLogging(e.getStackTrace().toString(),3);
			  }

			 /*
			  * El puerto esta abierto!
			  */
			  open = true;
			  crsiLoggerProxy.crsiDoLogging("El puerto esta abierto",1);
	}


	/*
	 * ---------------------------------------------------------------------------
	 * void serialEvent()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 9:55:40 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oraciï¿½n que hace el mï¿½todo, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios pï¿½rrafos, cï¿½mo se usa y/o cï¿½mo funciona el mï¿½todo<p>
	 * Los pï¿½rrafos se separan con:  <p>
	 * <p>Se debe dejar una lï¿½nea en blanco entre ï¿½sta descripciï¿½n y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parï¿½metro1}	frase indicado que significa el parï¿½metro o para que se va a utilizar el valor de este parï¿½metro
	 * TODO @param {nombre_parï¿½m2}		Si inicias esta oraciï¿½n con mayï¿½sculas, terminala con punto (especificaciï¿½n de JavaDoc).
	 * 									La descripciï¿½n de parï¿½metro puede ocupar varias lï¿½neas si deseas.
	 * TODO @return void 		Describir que significa lo que se estï¿½ devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepciï¿½n.
	 * TODO @since 						Indicar desde que versiï¿½n del Archivo ï¿½xiste el mï¿½todo.
	 * TODO @deprecated 				Si la funciï¿½n se vuelve obsoleta se deja esta lï¿½nea, sino se elimina.
	 * @param arg0
	 */
	/* (non-Javadoc)
	 * @see gnu.io.SerialPortEventListener#serialEvent(gnu.io.SerialPortEvent)
	 */
	public void serialEvent(SerialPortEvent arg0)
	{
		/*
		 * Creamos un StringBuffer y un int para recibir
		 * la datos de entrada
		 */
		int newData = 0;

		/*
		 * Determinamos el Tipo de Evento
		 */
		switch(arg0.getEventType()){

			/*
			 * Leamos datos mientras nuevaData sea
			 * distinto a -1.
			 * Si recibimos \r substituimos \n por el
			 * manejador de \n correcto para insertar una
			 * nueva lï¿½nea
			 */
			case SerialPortEvent.DATA_AVAILABLE:
				do {
					try{
						newData = is.read();
						if (-1 != newData) {
							tempBufer.append((char) newData);
							if (tamanoTrama != 0) {
								// Comunicación basada en info de un tamaño dado
								if (tempBufer.length() == tamanoTrama) {
									inputMessage.setText(tempBufer.toString());
									tempBufer.setLength(0);
								}
							} else {
								// Comunicación basado en un fin de trama
								if (tempBufer.charAt(tempBufer.length()-1) == finTrama) {
									// Enviamos y limpiamos
									inputMessage.setText(tempBufer.toString());
									tempBufer.setLength(0);
								}
							}
							
						}
					}
					catch(IOException ex){
						crsiLoggerProxy.crsiDoLogging("Ocurrio un Error al leer del puerto serial",3);
						crsiLoggerProxy.crsiDoLogging(ex.getMessage(),3);
						crsiLoggerProxy.crsiDoLogging(ex.getStackTrace().toString(),3);
						return;
					}
				} while(newData != -1);
				if (flushAtEnd && tempBufer.length() != 0) {
					inputMessage.setText(tempBufer.toString());
					tempBufer.setLength(0);
				}
				break;
			default:
				doSignalReceived(arg0);
				break;
		}
	}


	
	/*
	 * ---------------------------------------------------------------------------
	 * void sendBreak()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 10:59:37 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oraciï¿½n que hace el mï¿½todo, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios pï¿½rrafos, cï¿½mo se usa y/o cï¿½mo funciona el mï¿½todo<p>
	 * Los pï¿½rrafos se separan con:  <p>
	 * <p>Se debe dejar una lï¿½nea en blanco entre ï¿½sta descripciï¿½n y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parï¿½metro1}	frase indicado que significa el parï¿½metro o para que se va a utilizar el valor de este parï¿½metro
	 * TODO @param {nombre_parï¿½m2}		Si inicias esta oraciï¿½n con mayï¿½sculas, terminala con punto (especificaciï¿½n de JavaDoc).
	 * 									La descripciï¿½n de parï¿½metro puede ocupar varias lï¿½neas si deseas.
	 * TODO @return void 		Describir que significa lo que se estï¿½ devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepciï¿½n.
	 * TODO @since 						Indicar desde que versiï¿½n del Archivo ï¿½xiste el mï¿½todo.
	 * TODO @deprecated 				Si la funciï¿½n se vuelve obsoleta se deja esta lï¿½nea, sino se elimina.
	 * 
	 */
	public void sendBreak() {
	   serialPort.sendBreak(1000);
	}


	
	/*
	 * ---------------------------------------------------------------------------
	 * void configureSerialParameters()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 11:04:24 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oraciï¿½n que hace el mï¿½todo, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios pï¿½rrafos, cï¿½mo se usa y/o cï¿½mo funciona el mï¿½todo<p>
	 * Los pï¿½rrafos se separan con:  <p>
	 * <p>Se debe dejar una lï¿½nea en blanco entre ï¿½sta descripciï¿½n y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parï¿½metro1}	frase indicado que significa el parï¿½metro o para que se va a utilizar el valor de este parï¿½metro
	 * TODO @param {nombre_parï¿½m2}		Si inicias esta oraciï¿½n con mayï¿½sculas, terminala con punto (especificaciï¿½n de JavaDoc).
	 * 									La descripciï¿½n de parï¿½metro puede ocupar varias lï¿½neas si deseas.
	 * TODO @return void 		Describir que significa lo que se estï¿½ devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepciï¿½n.
	 * TODO @since 						Indicar desde que versiï¿½n del Archivo ï¿½xiste el mï¿½todo.
	 * TODO @deprecated 				Si la funciï¿½n se vuelve obsoleta se deja esta lï¿½nea, sino se elimina.
	 * @throws SerialExceptions
	 */
	public void configureSerialParameters() throws SerialExceptions {

		/*
		 * Guardamos el estado anterior del puerto antes de intentar
		 * configurarlo
		 */
		int oldBaudRate			= serialPort.getBaudRate();
		int oldDataBits			= serialPort.getDataBits();
		int oldStopBits			= serialPort.getStopBits();
		int oldParity			= serialPort.getParity();
		int oldFlowControl		= serialPort.getFlowControlMode();

		/*
		 * Configuramos los parametros de conexion
		 */
		try{
			
			System.out.println("***************** "+parameters.getBaudRate()+" - "+parameters.getDatabits()+" - "+ parameters.getStopbits()+ " - "+ parameters.getParity());
			
			serialPort.setSerialPortParams(
			parameters.getBaudRate(),
			parameters.getDatabits(),
			parameters.getStopbits(),
			parameters.getParity()
			);
		}catch(UnsupportedCommOperationException e){
			crsiLoggerProxy.crsiDoLogging("Ocurrio un Error al tratar de configurar el puerto",3);
			crsiLoggerProxy.crsiDoLogging(e.getMessage(),3);
			crsiLoggerProxy.crsiDoLogging(e.getStackTrace().toString(),3);
			parameters.setBaudRate(oldBaudRate);
			parameters.setDatabits(oldDataBits);
			parameters.setStopbits(oldStopBits);
			parameters.setParity(oldParity);
			throw new SerialExceptions("Parametros equivocados!");
		}

		/*
		 * Configuramos el Flow Control
		 */

		 try{
			serialPort.setFlowControlMode(parameters.getFlowControlIn() | parameters.getFlowControlOut());
			crsiLoggerProxy.crsiDoLogging("Configure Flow Control IN/OUT",1);
		 }
		 catch (UnsupportedCommOperationException e){
		 	crsiLoggerProxy.crsiDoLogging("Ocurrio un error al tratar de configurar Flow Control IN/OUT",3);
		 	crsiLoggerProxy.crsiDoLogging(e.getMessage(),3);
		 	crsiLoggerProxy.crsiDoLogging(e.getStackTrace().toString(),3);
			throw new SerialExceptions("Valor de Flow Control Erroneo");
		 }
	}



	/*
	 * ---------------------------------------------------------------------------
	 * void ownershipChange()
	 * Creado por: 	vmedina
	 * Creado el:	Mar 27, 2004 9:57:22 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * TODO Indicar en esta oraciï¿½n que hace el mï¿½todo, iniciar con verbo infinitivo o 3ra persona.<p>
	 * TODO Describir en uno o varios pï¿½rrafos, cï¿½mo se usa y/o cï¿½mo funciona el mï¿½todo<p>
	 * Los pï¿½rrafos se separan con:  <p>
	 * <p>Se debe dejar una lï¿½nea en blanco entre ï¿½sta descripciï¿½n y los tags javadoc</p>
	 * 
	 * TODO @param {nombre_parï¿½metro1}	frase indicado que significa el parï¿½metro o para que se va a utilizar el valor de este parï¿½metro
	 * TODO @param {nombre_parï¿½m2}		Si inicias esta oraciï¿½n con mayï¿½sculas, terminala con punto (especificaciï¿½n de JavaDoc).
	 * 									La descripciï¿½n de parï¿½metro puede ocupar varias lï¿½neas si deseas.
	 * TODO @return void 		Describir que significa lo que se estï¿½ devolviendo.
	 * TODO @exception 					Indicar el significado o posible causa de la excepciï¿½n.
	 * TODO @since 						Indicar desde que versiï¿½n del Archivo ï¿½xiste el mï¿½todo.
	 * TODO @deprecated 				Si la funciï¿½n se vuelve obsoleta se deja esta lï¿½nea, sino se elimina.
	 * @param arg0
	 */
	/* (non-Javadoc)
	 * @see gnu.io.CommPortOwnershipListener#ownershipChange(int)
	 */
	public void ownershipChange(int arg0)
	{
		if (arg0 == CommPortOwnershipListener.PORT_OWNERSHIP_REQUESTED) {
			closeConnection();
		}
	}

	/*
	 * ---------------------------------------------------------------------------
	 * boolean isBufferEmpty()
	 * Creado por: 	Victor Medina - linux@epa.com.ve
	 * Creado el:	May 31, 2004 10:32:09 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * <p>Verifica que el Output Buffer este vacio, de lo contrario regresa false</p>
	 * @since  		CRSerialInterface 1.0
	 * @return		TRUE si el buffer esta avcio, FALSE si contiene algun tipo de caracter o cadena
	 */
	public boolean isOutputBufferEmpty(){
        if(outputMessage.getText() == ""){
            return true;
        }
        else{
            return false;
        }
	}
	
	/*
	 * ---------------------------------------------------------------------------
	 * void cleanBuffer()
	 * Creado por: 	Victor Medina - linux@epa.com.ve
	 * Creado el:	May 31, 2004 10:40:20 AM
	 * ---------------------------------------------------------------------------
	 */
	/**
	 * <p>Elimina todos los datos que se encuentren en el Output buffer</p>
	 * @since		CRSerialInterface 1.0
	 */
	public void cleanBuffer(){
	    outputMessage.setText("");
	}
		
    /*
     * ---------------------------------------------------------------------------
     * void sendOutputBuffer()
     * Creado por: 	Victor Medina - linux@epa.com.ve
     * Creado el:	May 31, 2004 9:11:22 AM
     * ---------------------------------------------------------------------------
     */
    /**
     * <p>Envia los datos acumulados en el Output Buffer al puerto, luego blanquea los datos</p>
     * @since 		CRSerialInterface 1.0
     * 
     */
    public void sendOutputBuffer(){
        if(outputMessage.getText().toCharArray().length != 0){
            if(!outputMessage.getText().equals(null)){
    	        for (int i = 0; i<outputMessage.getText().toCharArray().length; i++){
    	            try {
    	            	char valor = outputMessage.getText().toCharArray()[i];
    	                os.write((int)valor);
    	            } catch (IOException e1) {
    	                // TODO Bloque catch generado automï¿½ticamente
    	                e1.printStackTrace();
    	            }
    	        }
    	        try {
					os.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
    	        outputMessage.setText("");
            }
        }
    }
    
    /*
     * ---------------------------------------------------------------------------
     * boolean testCD()
     * Creado por: 	Victor Medina - linux@epa.com.ve
     * Creado el:	Jun 2, 2004 10:21:36 AM
     * ---------------------------------------------------------------------------
     */
    /**
     * <p>Obtiene el estado del bit CD desde el UART</p>
     * @since   CRSerialInterface 1.0
     * @return boolean Estado del BIT
     */
    public boolean testCD(){
        return serialPort.isCD();
    }

    /*
     * ---------------------------------------------------------------------------
     * boolean testCTS()
     * Creado por: 	Victor Medina - linux@epa.com.ve
     * Creado el:	Jun 2, 2004 10:23:07 AM
     * ---------------------------------------------------------------------------
     */
    /**
     * <p>Obtiene el estado del bit  del CTS desde el UART</p>
     * @since    CRSerialInterface 1.0
     * @return boolean Estado del bit
     */
    public boolean testCTS(){
        return serialPort.isCTS();
    }
    
    /*
     * ---------------------------------------------------------------------------
     * boolean testDSR()
     * Creado por: 	Victor Medina - linux@epa.com.ve
     * Creado el:	Jun 2, 2004 10:25:10 AM
     * ---------------------------------------------------------------------------
     */
    /**
     * <p>Obtiene el estado del DSR desde el UART</p>
     * @since 	CRSerialInterface 1.0
     * @return boolean Estado del bit
     */
    public boolean testDSR(){
        return serialPort.isDSR();
    }
    
    /*
     * ---------------------------------------------------------------------------
     * boolean testDTR()
     * Creado por: 	Victor Medina - linux@epa.com.ve
     * Creado el:	Jun 2, 2004 10:34:14 AM
     * ---------------------------------------------------------------------------
     */
    /**
     * <p>Obtiene el estado del DTR desde el UART</p>
     * @since		CRSerialInterface 1.0
     * @return boolean Estado del bit
     */
    public boolean testDTR(){
        return serialPort.isDTR();
    }
    
    /*
     * ---------------------------------------------------------------------------
     * boolean testRI()
     * Creado por: 	Victor Medina - linux@epa.com.ve
     * Creado el:	Jun 2, 2004 10:35:28 AM
     * ---------------------------------------------------------------------------
     */
    /**
     * <p>Obtiene el estado del RI desde el UART</p>
     * @since		CRSerialInterface 1.0
     * @return boolean Estado del bit
     */
    public boolean testRI(){
        return serialPort.isRI();
    }
    
    /*
     * ---------------------------------------------------------------------------
     * boolean testRTS()
     * Creado por: 	Victor Medina - linux@epa.com.ve
     * Creado el:	Jun 2, 2004 10:36:33 AM
     * ---------------------------------------------------------------------------
     */
    /**
     * <p>Obtiene el estado del RTS desde el UART</p>
     * @since		CRSerialInterface 1.0
     * @return boolean Estado del bit
     */
    public boolean testRTS(){
        return serialPort.isRTS();
    }    
    
	/**
	 * @return Devuelve el caracter de fin de trama.
	 */
	public char getFinTrama() {
		return finTrama;
	}
	
	/**
	 * Establece el caracter de fin de trama, el cual representará el disparador
	 * del evento de entrada de datos por el puerto serial
	 * 
	 * @param finTrama El caracter a establecer.
	 */
	public void setFinTrama(char finTrama) {
		this.finTrama = finTrama;
	}
	
	/**
	 * @return Devuelve el tamaño fijo de la trama.  
	 */
	public int getTamanoTrama() {
		return tamanoTrama;
	}
	
	/**
	 * Establece un tamaño fijo de trama a ser retornado en la lectura del
	 * puerto serial. Si se establece un tamaño igual a 0, se adoptará
	 * el esquema de funcionamiento por caracter de fin de trama
	 * 
	 * @param tamanoTrama El tamaño de la trama a establecer.
	 * @see setFinTrama(char) 
	 */
	public void setTamanoTrama(int tamanoTrama) {
		this.tamanoTrama = tamanoTrama;
	}
	
	/**
	 * @return Devuelve flushAtEnd.
	 */
	public boolean isFlushAtEnd() {
		return flushAtEnd;
	}
	/**
	 * @param flushAtEnd El flushAtEnd a establecer.
	 */
	public void setFlushAtEnd(boolean flushAtEnd) {
		this.flushAtEnd = flushAtEnd;
	}
	
	// Metodos de manejo de eventos de señales recibidas
	public void addSerialSignalListener(SerialSignalListener listener) {
		signalListeners.add(listener);
	}
	
	public void removeSerialSignalListener(SerialSignalListener listener) {
		signalListeners.remove(listener);
	}
	
	protected void doSignalReceived(SerialPortEvent event) {
		for (Iterator i = signalListeners.iterator(); i.hasNext(); ) {
			SerialSignalListener listener = (SerialSignalListener)i.next();
			listener.signalReceived(event);
		}
	}
	
	
}
