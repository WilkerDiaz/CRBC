/*
 * $Id: TMU950ResponseParser.java,v 1.1.2.5 2005/05/09 14:28:21 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CRFiscalPrinterDriver 1.1
 * Paquete		: com.epa.crprinterdriver.impl
 * Programa		: NPF4610ResponseParser.java
 * Creado por	: Programa8
 * Creado en 	: 11-abr-2005 16:24:18
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
 * $Log: TMU950ResponseParser.java,v $
 * Revision 1.1.2.5  2005/05/09 14:28:21  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.1.2.4  2005/05/09 14:19:02  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.1.2.3  2005/05/06 19:17:59  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.1.2.2  2005/05/06 18:26:43  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.1.2.1  2005/05/05 22:05:40  programa8
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
 * Revision 1.2  2005/05/05 12:08:46  programa8
 * Versión 2.0.1 Final
 *
 * Revision 1.1.2.6  2005/04/21 13:47:58  programa8
 * Driver Fiscal 2.0 RC1 - Al 21/04/2004
 *
 * Revision 1.1.2.5  2005/04/19 19:08:28  programa8
 * Driver Fiscal al 19/04/2005 - Primer día de pruebas
 *
 * Revision 1.1.2.4  2005/04/18 16:39:28  programa8
 * Driver Fiscal al 18/04/2005 - 12m
 *
 * Revision 1.1.2.3  2005/04/13 21:57:07  programa8
 * Driver fiscal al 13/04/2005
 *
 * Revision 1.1.2.2  2005/04/12 20:59:16  programa8
 * Driver fiscal al 12/04/05
 *
 * Revision 1.1.2.1  2005/04/11 20:59:12  programa8
 * CRFiscalPrinterDriver 2.0 - Analisis Preliminar
 *
 * ===========================================================================
 */
package com.epa.crprinterdriver.impl;

import org.apache.log4j.Logger;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;


import com.epa.crprinterdriver.CRFPResponseParser;
import com.epa.crprinterdriver.CRFPStatus;
import com.epa.crprinterdriver.CRFiscalPrinterOperations;
import com.epa.crprinterdriver.data.DataStatusIF;
import com.epa.crprinterdriver.data.DataSubtotal;
import com.epa.crprinterdriver.data.ReporteMemoriaFiscal;
import com.epa.crprinterdriver.data.ReporteXZ;
import com.epa.crprinterdriver.event.CRFPResponseEvent;
import com.epa.crprinterdriver.event.ResponseListener;

/**
 * <p>
 * Implementación del int&eacute;rprete de respuestas para la impresora 
 * EPSON TMU950/PF
 * </p> 
 *   
 * <p>
 * <a href="TMU950ResponseParser.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - Arístides Castillo Colmenárez - $Author: programa8 $
 * @version $Revision: 1.1.2.5 $ - $Date: 2005/05/09 14:28:21 $
 * @since 11-abr-2005
 * 
 */
public class TMU950ResponseParser implements CRFPResponseParser {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(TMU950ResponseParser.class);

	
	// Variables de armado de respuesta
	private StringBuffer inputBuffer = new StringBuffer();
	private StringBuffer bccBuffer = new StringBuffer();
	private boolean recibiendoTrama = false;
	private boolean contandoBCC;
	private int contadorBCC;
	@SuppressWarnings("unused")
	private boolean tramaCompleta = false;

	// Listeners de Respuesta
	private HashSet<ResponseListener> responseListeners = new HashSet<ResponseListener>();
	
	// Manejo de las respuestas al driver
	private CRFPStatus driverStatus = null;
	
	private TMU950SequenceMap sequenceMap;
	
	
	/**
	 * Contructor de la clase
	 * @since 06-may-2004
	 * @param seqMap Mapa de Secuencias a usar
	 */
	public TMU950ResponseParser(TMU950SequenceMap seqMap) {
		super();
		sequenceMap = seqMap;
		iniciarEstadoLectura();
	}

	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.CRFPResponseParser#addResponseListener(com.epa.crprinterdriver.event.ResponseListener)
	 * @param listener
	 * @since 13-abr-2005
	 */
	public void addResponseListener(ResponseListener listener) {
		if (logger.isDebugEnabled()) {
			logger.debug("addResponseListener(ResponseListener) - start");
		}

		responseListeners.add(listener);

		if (logger.isDebugEnabled()) {
			logger.debug("addResponseListener(ResponseListener) - end");
		}
	}
	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.CRFPResponseParser#removeResponseListener()
	 * 
	 * @since 13-abr-2005
	 */
	public void removeResponseListener(ResponseListener listener) {
		if (logger.isDebugEnabled()) {
			logger.debug("removeResponseListener(ResponseListener) - start");
		}

		responseListeners.remove(listener);

		if (logger.isDebugEnabled()) {
			logger.debug("removeResponseListener(ResponseListener) - end");
		}
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Iterator'
	* Fecha: agosto 2011
	*/
	protected void doResponseArrived(CRFPResponseEvent event) {
		if (logger.isDebugEnabled()) {
			logger.debug("doResponseArrived(CRFPResponseEvent) - start");
		}

		for (Iterator<ResponseListener> i = responseListeners.iterator(); i.hasNext();) {
			ResponseListener listener = i.next();
			listener.responseArrived(event);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("doResponseArrived(CRFPResponseEvent) - end");
		}
	}
	
	
	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.CRFPResponseParser#setStatusToUpdate(com.epa.crprinterdriver.CRFPStatus)
	 * @param status
	 * @since 13-abr-2005
	 */
	public void setStatusToUpdate(CRFPStatus status) {
		if (logger.isDebugEnabled()) {
			logger.debug("setStatusToUpdate(CRFPStatus) - start");
		}

		driverStatus = status;

		if (logger.isDebugEnabled()) {
			logger.debug("setStatusToUpdate(CRFPStatus) - end");
		}
	}
	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.CRFPResponseParser#signalArrived(int, boolean)
	 * @param type
	 * @param value
	 * @since 13-abr-2005
	 */
	
	
	public void signalArrived(int type, boolean value) {
		if (logger.isDebugEnabled()) {
			logger.debug("signalArrived(int, boolean) - start");
		}

		switch (type) {
		case SIGNAL_DSR :
			// verdadero significa que puede recibir datos
			driverStatus.setImpresoraOcupada(!value);
			break;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("signalArrived(int, boolean) - end");
		}
	}


    private void iniciarEstadoLectura() {
		if (logger.isDebugEnabled()) {
			logger.debug("iniciarEstadoLectura() - start");
		}

    	contandoBCC = false;
    	recibiendoTrama = false;
    	tramaCompleta = false;
    	contadorBCC = 0;
    	inputBuffer.setLength(0);
    	bccBuffer.setLength(0);

		if (logger.isDebugEnabled()) {
			logger.debug("iniciarEstadoLectura() - end");
		}
    }

	
	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.CRFPResponseParser#constructResponse(char)
	 * @param c
	 * @since 11-abr-2005
	 */
	public void constructResponse(char c) {
		if (logger.isDebugEnabled()) {
			logger.debug("constructResponse(char) - start");
		}

		String trama = null;
		char carac = '\0';
		
		// Código de parsing de trama serial
		carac = c;
		driverStatus.setAumentarTimeout(false);
		if (carac == '\002') {
			// Un inicio de texto inicia la trama
			// y en caso de no haber terminado la anterior, reinicio
			if (recibiendoTrama) {
				iniciarEstadoLectura();
			}  
			recibiendoTrama = true;
		}
		
		if (recibiendoTrama) {
			// Actualizo el estado de lectura
			if (contandoBCC) {
				// Contamos los caracteres de control
				bccBuffer.append(carac);
				contadorBCC++;
			} else {
				// Agrego al final del buffer de entrada
				inputBuffer.append(carac);
			}
			if (!contandoBCC && carac == '\003') {
				// No puedo permitir un caracter ETX en el BCC
				contandoBCC = true;
				contadorBCC = 0;
			}
			if (contadorBCC == 4) {
				// Al llegar a 4 caracteres BCC, puedo procesar tranquilo
				String bcc = bccBuffer.toString();
				trama = inputBuffer.toString();
				tramaCompleta = true;
				// Validamos la respuesta recibida
				if (validarRespuesta(trama, bcc)) {
					interpretarRespuesta(tokenizeResponse(trama));
				} else {
					// si no es valida, notificamos
					doResponseArrived(new 
							CRFPResponseEvent(CRFPResponseEvent.RESPONSE_INVALID,
									driverStatus.getUltCommandTypeEnviado(), 
									driverStatus.getUltIDEnviado()));
				}
				// Y me preparo para una proxima trama.
				// Volvemos a comenzar
				iniciarEstadoLectura();
			}
		} else {
			if (carac == '\022') {
				driverStatus.setAumentarTimeout(true);
			} // Sino, se descartan todos los caracteres
		}

		if (logger.isDebugEnabled()) {
			logger.debug("constructResponse(char) - end");
		}
	}
	
	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.CRFPResponseParser#constructResponse(String)
	 * @param response
	 * @since 11-may-2007
	 */
	public void constructResponse(String response) {
		if (logger.isDebugEnabled()) {
			logger.debug("constructResponse(String = "+response +") - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("constructResponse(String) - end");
		}
	}
	
	private boolean validarRespuesta(String trama, String BCC) {
		if (logger.isDebugEnabled()) {
			logger.debug("validarRespuesta(String, String) - start");
		}

		boolean returnboolean = BCC.equals(sequenceMap
				.getDynSuffixSequence(trama));
		if (logger.isDebugEnabled()) {
			logger.debug("validarRespuesta(String, String) - end");
		}
		return returnboolean;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private Vector<String> tokenizeResponse(String trama) {
		if (logger.isDebugEnabled()) {
			logger.debug("tokenizeResponse(String) - start");
		}

		StringTokenizer st = new StringTokenizer(trama, ""+'\034'+'\002'+'\003');
		Vector<String> result = new Vector<String>();
		while (st.hasMoreElements()) {
			result.add((String)st.nextElement());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("tokenizeResponse(String) - end");
		}
		return result; 	
	}
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se eliminaron variables sin uso
	* Fecha: agosto 2011
	*/
	private Object ensamblarRespuesta(Vector<String> datos, int tipoComando, int tipoRespuesta) {
		if (logger.isDebugEnabled()) {
			logger.debug("ensamblarRespuesta(Vector, int, int) - start");
		}

		Object result = null;
		switch (tipoComando) {
			case CRFiscalPrinterOperations.CMD_CERRAR_CF :
			case CRFiscalPrinterOperations.CMD_CERRAR_CFV :
			case CRFiscalPrinterOperations.CMD_CERRAR_CFNC :
				Integer numCF = new Integer(datos.get(3));
				result = numCF;
				break;
			case CRFiscalPrinterOperations.CMD_GET_FECHAHORA_IF :
				Date fh2 = null; 
				try {
					SimpleDateFormat dffecha = new SimpleDateFormat("yyMMdd");
					Calendar cal = Calendar.getInstance();
					cal.setTime(dffecha.parse(datos.get(3)));
					String hora = datos.get(4);
					cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hora.substring(0, 2))); 
					cal.set(Calendar.MINUTE, Integer.parseInt(hora.substring(2, 4))); 
					cal.set(Calendar.SECOND, Integer.parseInt(hora.substring(4)));
					fh2 = cal.getTime();
				} catch (ParseException e) {
					logger.error("ensamblarRespuesta(Vector, int, int)", e);
				}
				result = fh2;
				break;
			case CRFiscalPrinterOperations.CMD_ITEM_CF  :
			case CRFiscalPrinterOperations.CMD_ITEM_CFV :
			case CRFiscalPrinterOperations.CMD_ITEM_CFNC:
				Integer numItem = new Integer(datos.get(3));
				result = numItem;
				break;
				
			case CRFiscalPrinterOperations.CMD_REPORTE_MF :
				int primer = Integer.parseInt(datos.get(3));
				int ultimo = Integer.parseInt(datos.get(4));
				result = new ReporteMemoriaFiscal(primer, ultimo);
				break;
			case CRFiscalPrinterOperations.CMD_REP_RANGO_Z :
			case CRFiscalPrinterOperations.CMD_REPORTE_Z :
				Date fecZ = new Date();
				try {
					SimpleDateFormat dffecha = new SimpleDateFormat("yyMMdd");
					Calendar cal = Calendar.getInstance();
					cal.setTime(dffecha.parse(datos.get(10)));
					fecZ = cal.getTime();
				} catch (ParseException e) {
					logger.error("ensamblarRespuesta(Vector, int, int)", e);
				}
				ReporteXZ repz = new ReporteXZ();
				repz.setVentasExentas(Float.parseFloat(datos.get(3)));
				repz.setVentasBaseA(Float.parseFloat(datos.get(4)));
				repz.setImpuestoBaseA(Float.parseFloat(datos.get(5)));
				repz.setDescuentos(Float.parseFloat(datos.get(6)));
				repz.setImpuestoDescuentos(Float.parseFloat(datos.get(7)));
				repz.setDevoluciones(Float.parseFloat(datos.get(8)));
				repz.setImpuestoDevoluciones(Float.parseFloat(datos.get(9)));
				repz.setFechaZ(fecZ);
				repz.setVentasBaseB(Float.parseFloat(datos.get(11)));
				repz.setImpuestoBaseB(Float.parseFloat(datos.get(12)));
				repz.setVentasBaseC(Float.parseFloat(datos.get(13)));
				repz.setImpuestoBaseC(Float.parseFloat(datos.get(14)));
				result = repz;
				break;
			case CRFiscalPrinterOperations.CMD_STATUS_IF :
				char numSeq = datos.get(3).charAt(0);
				int status = Integer.parseInt(datos.get(4));
				int cmdEjec = sequenceMap.getCommandType(datos.get(5).charAt(0));
				Date fh = new Date(); 
				try {
					SimpleDateFormat dffecha = new SimpleDateFormat("yyMMdd");
					Calendar cal = Calendar.getInstance();
					cal.setTime(dffecha.parse(datos.get(6)));
					String hora = datos.get(7);
					cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hora.substring(0, 2))); 
					cal.set(Calendar.MINUTE, Integer.parseInt(hora.substring(2, 4))); 
					cal.set(Calendar.SECOND, Integer.parseInt(hora.substring(4)));
					fh = cal.getTime();
				} catch (ParseException e) {
					logger.error("ensamblarRespuesta(Vector, int, int)", e);
				}
				DataStatusIF data = new DataStatusIF(numSeq, cmdEjec, fh, status);
				switch (tipoRespuesta) {
					case CRFPStatus.DATA_STATUS_N :
						data.setNumCFEmitidoDesdeZ(Integer.parseInt(datos.get(8)));
						data.setNumDNFEmitidoDesdeZ(Integer.parseInt(datos.get(9)));
						data.setNumCFTotal(Integer.parseInt(datos.get(10)));
						data.setNumDNFTotal(Integer.parseInt(datos.get(11)));
						data.setNumUltRepZ(Integer.parseInt(datos.get(12)));
						break;
					case CRFPStatus.DATA_STATUS_E :
						data.setVentasExentas(Float.parseFloat(datos.get(8)));
						break;
					case CRFPStatus.DATA_STATUS:
						data.setVentas(Float.parseFloat(datos.get(8)));
						data.setImpuestos(Float.parseFloat(datos.get(9)));
						break;
				}
				updateStatus(data);
				result = data;
				break;
			case CRFiscalPrinterOperations.CMD_SUB_CF :
				DataSubtotal subt = new DataSubtotal();
				subt.setVentaExenta(Float.parseFloat(datos.get(5)));
				subt.setBaseA(Float.parseFloat(datos.get(6)));
				subt.setVentaBaseA(Float.parseFloat(datos.get(7)));
				subt.setImpuestoBaseA(Float.parseFloat(datos.get(8)));
				subt.setBaseB(Float.parseFloat(datos.get(9)));
				subt.setVentaBaseB(Float.parseFloat(datos.get(10)));
				subt.setImpuestoBaseB(Float.parseFloat(datos.get(11)));
				subt.setBaseC(Float.parseFloat(datos.get(12)));
				subt.setVentaBaseC(Float.parseFloat(datos.get(13)));
				subt.setImpuestoBaseC(Float.parseFloat(datos.get(14)));
				result = subt;
				break;
			
			case CRFiscalPrinterOperations.CMD_GET_SERIAL :
				result = datos.get(3);
				driverStatus.setSerialImpresoraFiscal((String)result);
				break;
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("ensamblarRespuesta(Vector, int, int) - end");
		}
		return result;
	}
	
	private void updateStatus(String estadoPrn, String estadoFiscal, Object data, 
			int cmdType, int response) {
		if (logger.isDebugEnabled()) {
			logger.debug("updateStatus(String, String, Object) - start");
		}
		
		if (response == CRFPResponseEvent.RESPONSE_OK) {
			switch(cmdType) {
				case CRFiscalPrinterOperations.CMD_ACTIVAR_SLIP :
					driverStatus.setSlipActivo(true);
					break;
				case CRFiscalPrinterOperations.CMD_DESACTIVAR_SLIP:
					driverStatus.setSlipActivo(false);
					break;
			}
		}

		int edoPrn = Integer.parseInt(estadoPrn, 16);
		int edoFiscal = Integer.parseInt(estadoFiscal, 16);
		driverStatus.setEdoFiscal(edoFiscal);
		driverStatus.setEdoImpresora(edoPrn);
		driverStatus.setDataRespuesta(data);
		// Chequeo de error en impresora
		if ((edoPrn & 4) == 4) {
			driverStatus.setErrorImpresora(true);
		} else {
			driverStatus.setErrorImpresora(false);
		}
		
		// Chequeo de falta de papel
		if ((edoPrn & 16384) == 16384) {
			driverStatus.setHayPapel(false);
		} else {
			driverStatus.setHayPapel(true);
		}
		
		// Chequeo de memoria fiscal llena
		if ((edoFiscal & 129) == 129) {
			driverStatus.setMemFiscalLlena(true);
		} else {
			driverStatus.setMemFiscalLlena(false);
		}
		// Chequeo de numero máximo de items en CF
		if ((edoFiscal & 2048) == 2048) {
			driverStatus.setNumMaxItems(true);
		} else {
			driverStatus.setNumMaxItems(false);
		}
		
		// Chequeo de overflow en totales
		if ((edoFiscal & 64) == 64) {
			driverStatus.setTotalesDesbordados(true);
		} else {
			driverStatus.setTotalesDesbordados(false);
		}
		
		
		if (logger.isDebugEnabled()) {
			logger.debug("updateStatus(String, String, Object) - end");
		}
	}
	
	private void updateStatus(DataStatusIF status) {
		if (logger.isDebugEnabled()) {
			logger.debug("updateStatus(DataStatusIF) - start");
		}

		boolean errorImpresora = false;
		if (status.getStatus() == 4) {
			driverStatus.setRequiereZ(true);
		} else {
			driverStatus.setRequiereZ(false);
			if (status.getStatus() > 10) {
				errorImpresora = true;
			}
		}
		driverStatus.setErrorImpresora(errorImpresora);
		driverStatus.setUltCommandTypeEjecutado(status.getUltTipoComandoEjecutado());
		driverStatus.setNumComprobanteFiscal(status.getNumCFTotal());

		if (logger.isDebugEnabled()) {
			logger.debug("updateStatus(DataStatusIF) - end");
		}
	}
	
	// Método cerebro de la notificación de respuestas
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private void interpretarRespuesta(Vector<String> respuesta) {
		if (logger.isDebugEnabled()) {
			logger.debug("interpretarRespuesta(Vector) - start");
		}

		/*
		 * Estructura de respuesta enviada por la impresora:
		 * Indice		Significado
		 * ========================================
		 * 0			Cadena de 2 caracteres con el seqID + command
		 * 1			Estado de impresora
		 * 2			Estado fiscal
		 * 3 en adelante campos de datos
		 * 
		 * Si la respuesta no contiene datos y no es un error (Resp. 
		 * satisfactoria) solo contiene los campos 0, 1, 2
		 * 
		 * Si la respuesta indica error, en los campos 3 y 4 tiene
		 * respectivamente el codigo de error y una cadena que contiene la
		 * frase "ERROR=<codigoerror>" 
		 */ 
		// Tenemos una respuesta valida, revisamos si es un error.
		int tipoRespuesta;
		int errCode = 0;
		Object data = null;
		int tipoComando = sequenceMap.getCommandType(respuesta.get(0).charAt(1));
		String seqID = new Integer(respuesta.get(1).charAt(1)).toString();
		if (respuesta.size() > 3) {
			if (respuesta.size() >= 5 && respuesta.get(4).indexOf("ERROR") != -1) {
				// La respuesta indica un error
				tipoRespuesta = CRFPResponseEvent.RESPONSE_ERROR;
				errCode = Integer.parseInt(respuesta.get(3), 16);
			} else {
				// La respuesta es satisfactoria y contiene datos
				// Chequeamos si el driver esta esperando algun tipo de respuesta
				if (driverStatus.getTipoRespEsperada() != CRFPStatus.DATA_NONE) {
					// Generamos el objeto respuesta correspondiente a la data actual
					tipoRespuesta = CRFPResponseEvent.RESPONSE_DATA;
					data = ensamblarRespuesta(respuesta, tipoComando, 
							driverStatus.getTipoRespEsperada());
				} else {
					// Aunque contenga datos, simplemente la consideramos como ok
					tipoRespuesta = CRFPResponseEvent.RESPONSE_OK;
				}
			}
		} else {
			// Es una respuesta satisfactoria sin datos
			tipoRespuesta = CRFPResponseEvent.RESPONSE_OK;
		}
		
		// Si no hubo error, Actualizar estatus
//		if (CRFPResponseEvent.RESPONSE_ERROR != tipoRespuesta) {
			updateStatus(respuesta.get(1), 
					respuesta.get(2), data, 
					tipoComando, tipoRespuesta);
			
//		}
		// Actualizar respuesta
		CRFPResponseEvent event = new CRFPResponseEvent(tipoRespuesta, tipoComando, seqID);
		event.setData(data);
		event.setErrorCode(errCode);
		doResponseArrived(event);

		if (logger.isDebugEnabled()) {
			logger.debug("interpretarRespuesta(Vector) - end");
		}
	}
	

}
