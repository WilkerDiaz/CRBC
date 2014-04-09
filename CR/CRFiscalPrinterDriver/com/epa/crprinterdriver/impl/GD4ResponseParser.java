/*
 * ===========================================================================
 *
 * Proyecto		: CRFiscalPrinterDriver 2.0
 * Paquete		: com.epa.crprinterdriver.impl
 * Programa		: GD4ResponseParser.java
 * Creado por	: 
 * Creado en 	: 
 * (C) Copyright 2007
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de Revisión	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * 
 * ===========================================================================
 */
package com.epa.crprinterdriver.impl;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;
import org.apache.log4j.Logger;

import com.epa.crprinterdriver.data.*;
import com.epa.crprinterdriver.CRFPResponseParser;
import com.epa.crprinterdriver.CRFPStatus;
import com.epa.crprinterdriver.CRFiscalPrinterOperations;
import com.epa.crprinterdriver.data.DataStatusIF;
import com.epa.crprinterdriver.data.DataTablaImpuestos;
import com.epa.crprinterdriver.data.ReporteMemoriaFiscal;
import com.epa.crprinterdriver.event.CRFPResponseEvent;
import com.epa.crprinterdriver.event.ResponseListener;

/**
 * 
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se comentaron variables sin uso
* Fecha: agosto 2011
*/
public class GD4ResponseParser implements CRFPResponseParser {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(GD4ResponseParser.class);

	// Variables de armado de respuesta
	private StringBuffer inputBuffer = new StringBuffer();
	@SuppressWarnings("unused")
	private boolean recibiendoTrama = false;
	@SuppressWarnings("unused")
	private boolean tramaCompleta = false;

	// Listeners de Respuesta
	private HashSet<ResponseListener> responseListeners = new HashSet<ResponseListener>();

	// Manejo de las respuestas al driver
	private CRFPStatus driverStatus = null;

	private GD4SequenceMap sequenceMap;
	
	/**
	 * Constructor de la clase
	 * @since 11-abr-2005
	 * @param seqMap Mapa de Secuencias a usar
	 */
	public GD4ResponseParser(GD4SequenceMap seqMap) {
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
	* Sólo se parametrizó el tipo de dato contenido en los 'HashSet'
	* Fecha: agosto 2011
	*/
	protected void doResponseArrived(CRFPResponseEvent event) {
		if (logger.isDebugEnabled()) {
			logger.debug("doResponseArrived(CRFPResponseEvent) - start");
		}

		for (Iterator<ResponseListener> i = responseListeners.iterator(); i.hasNext();) {
			ResponseListener listener = (ResponseListener)i.next();
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

		recibiendoTrama = false;
		tramaCompleta = false;
		inputBuffer.setLength(0);
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
			logger.debug("constructResponse(char = "+c +") - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("constructResponse(char) - end");
		}
	}
	
	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.CRFPResponseParser#constructResponse(String)
	 * @param c
	 * @since 11-may-2007
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void constructResponse(String response) {
		if (logger.isDebugEnabled()) {
			logger.debug("constructResponse(String = "+response +") - start");
		}
		// Código de parsing de trama serial
		driverStatus.setAumentarTimeout(false);

		tramaCompleta = true;

		Vector<String> result = new Vector<String>();
		for(int i=0;i<response.length();i+=4) {
			result.add(response.substring(i+2,i+4));
		}
		iniciarEstadoLectura();

		interpretarRespuesta(result);
		if (logger.isDebugEnabled()) {
			logger.debug("constructResponse(String) - end");
		}
	}
	   public static long byteArrayToLong(final byte[] bytes) throws IOException{
	   	 int byteLen = bytes.length;
	   	 if(byteLen>8){
	   	 	throw new IllegalArgumentException("El arreglo No puede superar 8 Bytes");
	   	 }
	   	 byte buffer[] = new byte[8];
	   	 int cont=0;
	   	 for(int i=8-byteLen;i<8;i++){
	   	 	buffer[i]=bytes[cont];
	   	 	cont++;
	   	 }
	     ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
	     DataInputStream in = new DataInputStream(bis);
	     return in.readLong();     
	   }
	
	 public static int byteArrayToInt(byte[] b, int offset) {
        int value = 0;
        int length = b.length;
        for (int i = 0; i < length; i++) {
            int shift = (length - 1 - i) * 8;
            value += (b[i + offset] & 0x000000FF) << shift;

        }
        return value;
    }
	 
	 /*
		* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
		* Sólo se parametrizó el tipo de dato contenido en los 'Vector' y 'HashMap'
		* Se eliminaron variables sin uso
		* Fecha: agosto 2011
		*/
	private Object ensamblarRespuesta(Vector<String> datos, int tipoComando, int tipoRespuesta) {
		if (logger.isDebugEnabled()) {
			logger.debug("ensamblarRespuesta(Vector, int, int) - start");
		}

		Object result = null;
		switch (tipoComando) {
		case CRFiscalPrinterOperations.CMD_CERRAR_CF :
			//TODO:Importante. Devolver numero de CF llamar comando ultimo CF
			/*Integer numCF = new Integer((String)datos.get(3));
			result = numCF;*/
			break;
		case CRFiscalPrinterOperations.CMD_GET_FECHAHORA_IF :
			Date fh2 = null; 
			try {
				String buffer="";
				for (int i=16; i< 30; i++)
					buffer += (char)Integer.parseInt(datos.get(i),16);
				Date fecha = new SimpleDateFormat("dd/MM/yy hh:mm").parse(buffer);
				Calendar cal = Calendar.getInstance();
				cal.setTime(fecha);
				fh2 = cal.getTime();
			} catch (ParseException e) {
				logger.error("ensamblarRespuesta(Vector, int, int)", e);
			}
			result = fh2;
			break;
		case CRFiscalPrinterOperations.CMD_ITEM_CF :
		case CRFiscalPrinterOperations.CMD_ITEM_CFV :
		case CRFiscalPrinterOperations.CMD_ITEM_CFNC :
			break;

		case CRFiscalPrinterOperations.CMD_REPORTE_MF :
			int primer = Integer.parseInt(datos.get(3));
			int ultimo = Integer.parseInt(datos.get(4));
			result = new ReporteMemoriaFiscal(primer, ultimo);
			break;
		case CRFiscalPrinterOperations.CMD_REP_RANGO_Z :
		case CRFiscalPrinterOperations.CMD_REPORTE_Z :
			//TODO: Desarrollo de Respuesta Respuesta RZ
			break;
			
		case CRFiscalPrinterOperations.CMD_STATUS_IF :
			// status = Estado de driver fiscal		
			
			int status = Integer.parseInt(datos.get(31));
		
			int cmdEjec = sequenceMap.getCommandType(Integer.parseInt(datos.get(31),16),Integer.parseInt((String)datos.get(32),16));
			// numSeq =  ultSeq Ultimo identificador de secuencia ejecutada
			char numSeq = (datos.get(15)).charAt(0);

			Date fh = new Date(); 
			try {
				String fechaHora = new String();
				for (int ix=16;ix<30;ix++){
					fechaHora += (char)Integer.parseInt(datos.get(ix),16);
				}
				Date fecha = new SimpleDateFormat("dd/MM/yy hh:mm").parse(fechaHora);
				Calendar cal = Calendar.getInstance();
				cal.setTime(fecha);
				fh = cal.getTime();
			} catch (ParseException e) {
				logger.error("ensamblarRespuesta(Vector, int, int)", e);
			}
			DataStatusIF data = new DataStatusIF(numSeq, cmdEjec, fh, status);
			switch (tipoRespuesta) {
			case CRFPStatus.DATA_STATUS_N :
				//CFEmitidoDesdeZ= Bytes 168, 169 OK
				try{
					byte cant [] = new byte [2];
					cant[0]=(byte)Integer.parseInt(datos.get(168),16);
					cant[1]=(byte)Integer.parseInt(datos.get(169),16);
					data.setNumCFEmitidoDesdeZ(byteArrayToInt(cant,0));
				}catch(Exception ex){
					ex.printStackTrace();
				}
				//NCEmitidoDesdeZ= Bytes 184, 185
				try{
					byte cant [] = new byte [2];
					cant[0]=(byte)Integer.parseInt(datos.get(184),16);
					cant[1]=(byte)Integer.parseInt(datos.get(185),16);
					data.setNumNCEmitidoDesdeZ(byteArrayToInt(cant,0));
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
				//CNFEmitidoDesdeZ= Bytes 172, 173 OK
				try{
					byte cant [] = new byte [2];
					cant[0]=(byte)Integer.parseInt(datos.get(172),16);
					cant[1]=(byte)Integer.parseInt(datos.get(173),16);
					data.setNumDNFEmitidoDesdeZ(byteArrayToInt(cant,0));
				}catch(Exception ex){
					ex.printStackTrace();
				}
				//Total Ventas del Dia: Bytes 37 al 42 OK
				//TODO: CMD_STATUS_IF: Probar para parámetros 'A', 'B', 'C', 'E'
				try{
					byte cant[] = new byte [6];
					cant[0]=(byte)Integer.parseInt(datos.get(37),16);
					cant[1]=(byte)Integer.parseInt(datos.get(38),16);
					cant[2]=(byte)Integer.parseInt(datos.get(39),16);
					cant[3]=(byte)Integer.parseInt(datos.get(40),16);
					cant[4]=(byte)Integer.parseInt(datos.get(41),16);
					cant[5]=(byte)Integer.parseInt(datos.get(42),16);
					data.setVentas(byteArrayToLong(cant)/(float)100);
				}catch(Exception ex){
					ex.printStackTrace();
				}
				//Total Impuestos del Dia: 49 al 54 OK
				try{
					byte cant[] = new byte [6];
					cant[0]=(byte)Integer.parseInt(datos.get(49),16);
					cant[1]=(byte)Integer.parseInt(datos.get(50),16);
					cant[2]=(byte)Integer.parseInt(datos.get(51),16);
					cant[3]=(byte)Integer.parseInt(datos.get(52),16);
					cant[4]=(byte)Integer.parseInt(datos.get(53),16);
					cant[5]=(byte)Integer.parseInt(datos.get(54),16);
					data.setImpuestos(byteArrayToLong(cant)/(float)100);
					
				}catch(Exception ex){
					ex.printStackTrace();
				}
				//TODO: CMD_STATUS_IF: Falla en Placa Fiscal. CFLifeTime= Bytes 247,248,249,250
				try{
					byte cant [] = new byte [4];
					cant[0]=(byte)Integer.parseInt(datos.get(247),16);
					cant[1]=(byte)Integer.parseInt(datos.get(248),16);
					cant[2]=(byte)Integer.parseInt(datos.get(249),16);
					cant[3]=(byte)Integer.parseInt(datos.get(250),16);/*
					cant[0]=(byte)Integer.parseInt((String)datos.get(245),16);
					cant[1]=(byte)Integer.parseInt((String)datos.get(246),16);
					cant[2]=(byte)Integer.parseInt((String)datos.get(247),16);
					cant[3]=(byte)Integer.parseInt((String)datos.get(248),16);*/
					data.setNumCFTotal(byteArrayToInt(cant,0));
				}catch(Exception ex){
					ex.printStackTrace();
				}
				//TODO: CMD_STATUS_IF: Falla en Placa Fiscal. NumUltRepZ Bytes 243, 244
				try{
					byte cant [] = new byte [2];
					/*cant[0]=(byte)Integer.parseInt((String)datos.get(243),16);
					cant[1]=(byte)Integer.parseInt((String)datos.get(244),16);*/
					cant[0]=(byte)Integer.parseInt(datos.get(241),16);
					cant[1]=(byte)Integer.parseInt(datos.get(242),16);
					data.setNumUltRepZ(byteArrayToInt(cant,0));
				}catch(Exception ex){
					ex.printStackTrace();
				}
				//TODO: CMD_STATUS_IF: Falla en Placa Fiscal. NumUltNC Bytes 
				try{
					byte cant [] = new byte [4];
					cant[0]=(byte)Integer.parseInt(datos.get(231),16);
					cant[1]=(byte)Integer.parseInt(datos.get(232),16);
					cant[2]=(byte)Integer.parseInt(datos.get(233),16);
					cant[3]=(byte)Integer.parseInt(datos.get(234),16);
					/*cant[0]=(byte)Integer.parseInt((String)datos.get(229),16);
					cant[1]=(byte)Integer.parseInt((String)datos.get(230),16);
					cant[2]=(byte)Integer.parseInt((String)datos.get(231),16);
					cant[3]=(byte)Integer.parseInt((String)datos.get(232),16);
					*/
					data.setNumNCTotal(byteArrayToInt(cant,0));
					
				}catch(Exception ex){
					ex.printStackTrace();
				}					
				//TODO: CMD_STATUS_IF: Procedimiento Fiscal en Progreso (0A:NC / 01:FACT / 00:None)
				try{
					byte estado [] = new byte [1];
					estado[0]=(byte)Integer.parseInt(datos.get(31),16);
					data.setEstadoFiscal(byteArrayToInt(estado,0));
					
				}catch(Exception ex){
					ex.printStackTrace();
				}					
				//TODO: CMD_STATUS_IF: Falla en Placa Fiscal. NumUltDNF Bytes 
				try{
					byte cant [] = new byte [4];
					/*cant[0]=(byte)Integer.parseInt((String)datos.get(231),16);
					cant[1]=(byte)Integer.parseInt((String)datos.get(232),16);
					cant[2]=(byte)Integer.parseInt((String)datos.get(233),16);
					cant[3]=(byte)Integer.parseInt((String)datos.get(234),16);
					**/
					cant[0]=(byte)Integer.parseInt(datos.get(229),16);
					cant[1]=(byte)Integer.parseInt(datos.get(230),16);
					cant[2]=(byte)Integer.parseInt(datos.get(231),16);
					cant[3]=(byte)Integer.parseInt(datos.get(232),16);
					data.setNumDNFTotal(byteArrayToInt(cant,0));
				}catch(Exception ex){
					ex.printStackTrace();
				}					
				
				break;
			case CRFPStatus.DATA_STATUS:
				try{
					byte cant[] = new byte [6];
					cant[0]=(byte)Integer.parseInt(datos.get(37),16);
					cant[1]=(byte)Integer.parseInt(datos.get(38),16);
					cant[2]=(byte)Integer.parseInt(datos.get(39),16);
					cant[3]=(byte)Integer.parseInt(datos.get(40),16);
					cant[4]=(byte)Integer.parseInt(datos.get(41),16);
					cant[5]=(byte)Integer.parseInt(datos.get(42),16);
					data.setVentas(byteArrayToLong(cant)/(float)100);
				}catch(Exception ex){
					ex.printStackTrace();
				}
				try{
					byte cant[] = new byte [6];
					cant[0]=(byte)Integer.parseInt(datos.get(49),16);
					cant[1]=(byte)Integer.parseInt(datos.get(50),16);
					cant[2]=(byte)Integer.parseInt(datos.get(51),16);
					cant[3]=(byte)Integer.parseInt(datos.get(52),16);
					cant[4]=(byte)Integer.parseInt(datos.get(53),16);
					cant[5]=(byte)Integer.parseInt(datos.get(54),16);
					data.setImpuestos(byteArrayToLong(cant)/(float)100);
				}catch(Exception ex){
					ex.printStackTrace();
				}	
				break;
			}
	/*	if (logger.isDebugEnabled()) {
			logger.debug("ensamblarRespuesta("Ventas="+data.getVentas() +
								"\nV. Exentas="+data.getVentasExentas() +
								"\nImpuestos="+data.getImpuestos()+
								"\nTotal CF="+data.getNumCFTotal() +
								"\nTotal NC="+data.getNumNCTotal()+
								"\nTotal DNF="+data.getNumDNFTotal()+
								"\nCF desd Z="+data.getNumCFEmitidoDesdeZ() +
								"\nNC desde Z="+data.getNumNCEmitidoDesdeZ()+
								"\nDNF desde Z="+data.getNumDNFEmitidoDesdeZ()+
								"\nEstatus="+data.getStatus()+
								"\nUltComando="+data.getUltTipoComandoEjecutado());
		}*/
			updateStatus(data);
			result = data;
			break;
		case CRFiscalPrinterOperations.CMD_SUB_CF :
			//DataSubtotal subt = new DataSubtotal();
			
			//byte resp[] = new byte[1];			
			
			//resp[0]=(byte)Integer.parseInt((String)datos.get(31),16);			
			
			//TODO: Desarrollar Interpretacion de Respuesta Subtotal Comprobante Fiscal, hay que hacerlo en 4 comandos (no vienen desglosados los montos / 1 comando por categoria de imp)
			//String respuesta = "";//new String(resp);
			
			byte canti [] = new byte [1];
			canti[0]=(byte)Integer.parseInt(datos.get(31),16);
			int res = byteArrayToInt(canti,0);
			
			canti  = new byte [1];
			canti[0]=(byte)Integer.parseInt(datos.get(32),16);			
			
			int res2 = byteArrayToInt(canti,0);
			HashMap<String,String> vec = new HashMap<String,String>();
			vec.put("estado",String.valueOf(res));
			vec.put("paso",String.valueOf(res2));
			
			result = vec;
			break;
		case CRFiscalPrinterOperations.CMD_GET_SERIAL :
			byte serialF[] = new byte [10];
			int cont = 0;
			//Bytes 252 - 259
			for (int i=252; i<= 261; i++){
				serialF[cont]=(byte)Integer.parseInt(datos.get(i),16);
				cont++;
			}
			String serial= new String(serialF);
			driverStatus.setSerialImpresoraFiscal(serial);
			result = serial;
			break;
		case CRFiscalPrinterOperations.CMD_TABLA_IMP2:
		case CRFiscalPrinterOperations.CMD_TABLA_IMP:
			Date fch = new Date(); 
			try {
				String fechaHora = new String();
				for (int ix=16;ix<30;ix++){
					fechaHora += (char)Integer.parseInt(datos.get(ix),16);
				}
				Date fecha = new SimpleDateFormat("dd/MM/yy hh:mm").parse(fechaHora);
				Calendar cal = Calendar.getInstance();
				cal.setTime(fecha);
				fch = cal.getTime();
			} catch (ParseException e) {
				logger.error("ensamblarRespuesta(Vector, int, int)", e);
			}
			int ncierre=0;
 			try{
				byte cant [] = new byte [4];
				cant[0]=(byte)Integer.parseInt(datos.get(40),16);
				cant[1]=(byte)Integer.parseInt(datos.get(41),16);
				cant[2]=(byte)Integer.parseInt(datos.get(42),16);
				cant[3]=(byte)Integer.parseInt(datos.get(43),16);
				ncierre =byteArrayToInt(cant,0);
			}catch(Exception ex){
				ex.printStackTrace();
			}
			DataTablaImpuestos tabla = new DataTablaImpuestos(fch,ncierre);
			byte imp[] = new byte [2];
			try {
					imp[0]=(byte)Integer.parseInt(datos.get(44),16);
					imp[1]=(byte)Integer.parseInt(datos.get(45),16);
					tabla.setTablaImpuestos(byteArrayToInt(imp,0)/(float)100,2);
					imp[0]=(byte)Integer.parseInt(datos.get(46),16);
					imp[1]=(byte)Integer.parseInt(datos.get(47),16);
					tabla.setTablaImpuestos(byteArrayToInt(imp,0)/(float)100,3);
					imp[0]=(byte)Integer.parseInt(datos.get(48),16);
					imp[1]=(byte)Integer.parseInt(datos.get(49),16);
					tabla.setTablaImpuestos(byteArrayToInt(imp,0)/(float)100,4);
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} catch (IndexOutOfBoundsException e1) {
					e1.printStackTrace();
				}
			result = tabla;
			break;
			case CRFiscalPrinterOperations.CMD_IMP_METHOD:
				Integer method= new Integer( Integer.parseInt(datos.get(49),16));
				result = method;
				break;
			case CRFiscalPrinterOperations.CMD_READ_TOTAL:
				ReporteXZ rxz = new ReporteXZ();
				try{
					//TODO: CMD_READ_TOTAL: Falla en Placa Fiscal. VentasExentas
					byte cant[] = new byte[6];
					cant[0] = (byte)Integer.parseInt(datos.get(49),16);
					cant[1] = (byte)Integer.parseInt(datos.get(50),16);
					cant[2] = (byte)Integer.parseInt(datos.get(51),16);
					cant[3] = (byte)Integer.parseInt(datos.get(52),16);
					cant[4] = (byte)Integer.parseInt(datos.get(53),16);
					cant[5] = (byte)Integer.parseInt(datos.get(54),16);
					rxz.setVentasExentas(byteArrayToLong(cant)/(float)100);
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
				try{
					//TODO: CMD_READ_TOTAL: Falla en Placa Fiscal. VentasBaseA
					byte cant[] = new byte[6];
					cant[0] = (byte)Integer.parseInt(datos.get(55),16);
					cant[1] = (byte)Integer.parseInt(datos.get(56),16);
					cant[2] = (byte)Integer.parseInt(datos.get(57),16);
					cant[3] = (byte)Integer.parseInt(datos.get(58),16);
					cant[4] = (byte)Integer.parseInt(datos.get(59),16);
					cant[5] = (byte)Integer.parseInt(datos.get(60),16);
					rxz.setVentasBaseA(byteArrayToLong(cant)/(float)100);
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
				try{
					//TODO: CMD_READ_TOTAL: Falla en Placa Fiscal. VentasBaseB
					byte cant[] = new byte[6];
					cant[0] = (byte)Integer.parseInt(datos.get(61),16);
					cant[1] = (byte)Integer.parseInt(datos.get(62),16);
					cant[2] = (byte)Integer.parseInt(datos.get(63),16);
					cant[3] = (byte)Integer.parseInt(datos.get(64),16);
					cant[4] = (byte)Integer.parseInt(datos.get(65),16);
					cant[5] = (byte)Integer.parseInt(datos.get(66),16);
					rxz.setVentasBaseB(byteArrayToLong(cant)/(float)100);
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
				try{
					//TODO: CMD_READ_TOTAL: Falla en Placa Fiscal. VentasBaseC
					byte cant[] = new byte[6];
					cant[0] = (byte)Integer.parseInt(datos.get(67),16);
					cant[1] = (byte)Integer.parseInt(datos.get(68),16);
					cant[2] = (byte)Integer.parseInt(datos.get(69),16);
					cant[3] = (byte)Integer.parseInt(datos.get(70),16);
					cant[4] = (byte)Integer.parseInt(datos.get(71),16);
					cant[5] = (byte)Integer.parseInt(datos.get(72),16);
					rxz.setVentasBaseC(byteArrayToLong(cant)/(float)100);
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
				try{
					//TODO: CMD_READ_TOTAL: Falla en Placa Fiscal. ImpuestoBaseA
					byte cant[] = new byte[6];
					cant[0] = (byte)Integer.parseInt(datos.get(73),16);
					cant[1] = (byte)Integer.parseInt(datos.get(74),16);
					cant[2] = (byte)Integer.parseInt(datos.get(75),16);
					cant[3] = (byte)Integer.parseInt(datos.get(76),16);
					cant[4] = (byte)Integer.parseInt(datos.get(77),16);
					cant[5] = (byte)Integer.parseInt(datos.get(78),16);
					rxz.setImpuestoBaseA(byteArrayToLong(cant)/(float)100);
				}
				catch(Exception ex){
					ex.printStackTrace();
				}				
				try{
					//TODO: CMD_READ_TOTAL: Falla en Placa Fiscal. ImpuestoBaseB
					byte cant[] = new byte[6];
					cant[0] = (byte)Integer.parseInt(datos.get(79),16);
					cant[1] = (byte)Integer.parseInt(datos.get(80),16);
					cant[2] = (byte)Integer.parseInt(datos.get(81),16);
					cant[3] = (byte)Integer.parseInt(datos.get(82),16);
					cant[4] = (byte)Integer.parseInt(datos.get(83),16);
					cant[5] = (byte)Integer.parseInt(datos.get(84),16);
					rxz.setImpuestoBaseB(byteArrayToLong(cant)/(float)100);
				}
				catch(Exception ex){
					ex.printStackTrace();
				}				
				try{
					//TODO: CMD_READ_TOTAL: Falla en Placa Fiscal. ImpuestoBaseC
					byte cant[] = new byte[6];
					cant[0] = (byte)Integer.parseInt(datos.get(85),16);
					cant[1] = (byte)Integer.parseInt(datos.get(86),16);
					cant[2] = (byte)Integer.parseInt(datos.get(87),16);
					cant[3] = (byte)Integer.parseInt(datos.get(88),16);
					cant[4] = (byte)Integer.parseInt(datos.get(89),16);
					cant[5] = (byte)Integer.parseInt(datos.get(90),16);
					rxz.setImpuestoBaseC(byteArrayToLong(cant)/(float)100);
				}
				catch(Exception ex){
					ex.printStackTrace();
				}				
		/*	if (logger.isDebugEnabled()) {
			logger.debug("VentasEx="+rxz.getVentasExentas()+
						"\nBaseA="+rxz.getVentasBaseA()+
						"\nBaseB="+rxz.getVentasBaseB()+
						"\nBaseC="+rxz.getVentasBaseC()+
						"\nImpA="+rxz.getImpuestoBaseA()+
						"\nImpB="+rxz.getImpuestoBaseB()+
						"\nImpC="+rxz.getImpuestoBaseC());
		}	*/
				result = rxz;		
			break;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ensamblarRespuesta(Vector, int, int) - end");
		}
		
		return result;
	}
	
	private void updateStatus(int returnCode, Object data, 
			int cmdType, int response) {
		if (logger.isDebugEnabled()) {
			logger.debug("updateStatus(int, Object, int, int) - start");
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

		driverStatus.setDataRespuesta(data);
		driverStatus.setErrorImpresora(false);
		driverStatus.setHayPapel(true);
		driverStatus.setMemFiscalLlena(false);
		driverStatus.setNumMaxItems(false);
		driverStatus.setTotalesDesbordados(false);
		
		switch(returnCode){
			// Impresora fuera de linea
			case 100:	driverStatus.setErrorImpresora(true); break;
			// Chequeo de falta de papel
			case 203:	case 206: 	case 214: 
				driverStatus.setHayPapel(false); break;	
			// Chequeo de memoria fiscal llena		
			case 129: 	driverStatus.setMemFiscalLlena(true); break;
			// Chequeo de numero máximo de items en CF
			case 69:	driverStatus.setNumMaxItems(true);	break;
			// Chequeo de overflow en totales
			case 16:  	case 17:  	case 18:	case 19:	case 21:
			case 33: 	case 34:	case 35:	case 37:	case 56: 
			case 57:
				driverStatus.setTotalesDesbordados(true);
			break;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("updateStatus(int, Object, int, int) - end");
		}
	}
	

/*
 *  Función Driver anterior
 * 	private void updateStatus(String estadoPrn, String estadoFiscal, Object data, 
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
		//Byte 0
		int edoPrn = Integer.parseInt(estadoPrn, 16);
		//Byte 8
		int edoFiscal = Integer.parseInt(estadoFiscal, 16);
		driverStatus.setEdoFiscal(edoFiscal);
		driverStatus.setEdoImpresora(edoPrn);
		driverStatus.setDataRespuesta(data);
		// Chequeo de error en impresora
		//  Impresora fuera de linea
		if ((edoPrn & 4) == 4) {
			driverStatus.setErrorImpresora(true);
		} else {
			driverStatus.setErrorImpresora(false);
		}

		//Chequeo de falta de papel
		
//		if ((edoPrn & 16384) == 16384) {
		if ((edoPrn & 64) == 64) {
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
		//Chequeo de numero máximo de items en CF
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
	}*/
	

	private void updateStatus(DataStatusIF status) {
		if (logger.isDebugEnabled()) {
			logger.debug("updateStatus(DataStatusIF) - start");
		}
		boolean errorImpresora = false;
		//TODO: Verificar en que status requiere Z. Código driver Anterior
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
		int statusComprobante = 0;
		if(driverStatus.getTipoUltimoDocumentoImpreso().equalsIgnoreCase(CRFiscalPrinterOperations.DOC_FISCAL_NC)){
			statusComprobante = status.getNumNCTotal();
		} else{
			statusComprobante = status.getNumCFTotal();
		}
		driverStatus.setNumComprobanteFiscal(statusComprobante);

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
		// Tenemos una respuesta valida, revisamos si es un error.
		int tipoRespuesta;
		int returnCode = 0;
		Object data = null;
		if ((Integer.parseInt((String)respuesta.get(8),16) & 0x60) == 0x00) { //Condicion ya validada en GD4Engine.parseResponse() no es necesaria 
			if (Integer.parseInt((String)respuesta.get(13),16) < 0)
				returnCode = 128 + (Integer.parseInt((String)respuesta.get(13),16) & 0x7F);
			else
				returnCode = Integer.parseInt((String)respuesta.get(13),16);
		}
		logger.debug("interpretarRespuesta(Vector) - returnCode="+returnCode);	
		if (logger.isDebugEnabled()) {
			logger.debug("GD4ResponseParser::interpretarRespuesta(Vector) - returnCode="+returnCode +" - Respuesta: " +respuesta);
		}
				
		if(returnCode!=67) {
			// La respuesta indica un error
			tipoRespuesta = CRFPResponseEvent.RESPONSE_ERROR;
		} else {
			
			// La respuesta es satisfactoria y puede que contenga datos
			// Chequeamos si el driver esta esperando algun tipo de respuesta
			if (driverStatus.getTipoRespEsperada() != CRFPStatus.DATA_NONE) {
				// Generamos el objeto respuesta correspondiente a la data actual
				tipoRespuesta = CRFPResponseEvent.RESPONSE_DATA;
				data = ensamblarRespuesta(respuesta, driverStatus.getUltCommandTypeEnviado(), driverStatus.getTipoRespEsperada());
			} else {
				// Aunque contenga datos, simplemente la consideramos como ok
				tipoRespuesta = CRFPResponseEvent.RESPONSE_OK;
			}
		}
		//updateStatus((String)respuesta.get(1),(String)respuesta.get(2), data, driverStatus.getUltCommandTypeEnviado(), tipoRespuesta);
		updateStatus(returnCode,  data, driverStatus.getUltCommandTypeEnviado(), tipoRespuesta);

		// Actualizar respuesta
		CRFPResponseEvent event = new CRFPResponseEvent(tipoRespuesta, driverStatus.getUltCommandTypeEnviado(), null);
		event.setData(data);
		event.setErrorCode(returnCode);
		doResponseArrived(event);

		if (logger.isDebugEnabled()) {
			logger.debug("interpretarRespuesta(Vector) - end");
		}
	}
}