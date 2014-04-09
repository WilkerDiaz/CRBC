/*
 * $Id: TMU950SequenceMap.java,v 1.1.2.7 2005/05/09 15:05:48 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CRFiscalPrinterDriver 1.1
 * Paquete		: com.epa.crprinterdriver.impl
 * Programa		: NPF4610SequenceMap.java
 * Creado por	: Programa8
 * Creado en 	: 11-abr-2005 16:24:47
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
 * $Log: TMU950SequenceMap.java,v $
 * Revision 1.1.2.7  2005/05/09 15:05:48  programa8
 * Ajuste en tiempos de espera en comprobantes fiscales
 *
 * Revision 1.1.2.6  2005/05/09 14:28:22  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.1.2.5  2005/05/09 14:19:00  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.1.2.4  2005/05/06 19:18:00  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.1.2.3  2005/05/06 18:26:44  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.1.2.2  2005/05/06 18:17:06  programa8
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
 * Revision 1.2  2005/05/05 12:08:45  programa8
 * Versión 2.0.1 Final
 *
 * Revision 1.1.2.7  2005/04/25 19:51:45  programa8
 * Ajuste en accion por error de max lineas texto fiscal
 *
 * Revision 1.1.2.6  2005/04/19 19:08:28  programa8
 * Driver Fiscal al 19/04/2005 - Primer día de pruebas
 *
 * Revision 1.1.2.5  2005/04/18 16:39:27  programa8
 * Driver Fiscal al 18/04/2005 - 12m
 *
 * Revision 1.1.2.4  2005/04/13 21:57:07  programa8
 * Driver fiscal al 13/04/2005
 *
 * Revision 1.1.2.3  2005/04/12 20:59:16  programa8
 * Driver fiscal al 12/04/05
 *
 * Revision 1.1.2.2  2005/04/12 19:20:19  programa8
 * Secuencias de escape de NPF4610 - Completas
 *
 * Revision 1.1.2.1  2005/04/11 20:59:12  programa8
 * CRFiscalPrinterDriver 2.0 - Analisis Preliminar
 *
 * ===========================================================================
 */
package com.epa.crprinterdriver.impl;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.epa.crprinterdriver.CRFPEngine;
import com.epa.crprinterdriver.CRFPSequenceMap;
import com.epa.crprinterdriver.CRFPStatus;
import com.epa.crprinterdriver.CRFiscalPrinterOperations;
import com.epa.crprinterdriver.data.DataTablaImpuestos;

/**
 * <p>
 * Implementación del Mapa de Secuencias para la impresora
 * EPSON TMU950/PF
 * </p>
 * <p>
 * <a href="TMU950SequenceMap.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - Arístides Castillo Colmenárez - $Author: programa8 $
 * @version $Revision: 1.1.2.7 $ - $Date: 2005/05/09 15:05:48 $
 * @since 11-abr-2005
 * 
 */
public class TMU950SequenceMap implements CRFPSequenceMap {
	/**
	 * Logger for this class
	 */

	private static final Logger logger = Logger
			.getLogger(TMU950SequenceMap.class);

	/**
	 * @since 11-abr-2005
	 * 
	 */
	private CRFPStatus statusDriver;
    //private Random generadorDeSecuencia = new Random();
	
	
	/**
	 * Filtra los caracteres especiales que no pueden ser interpretados por la impresora fiscal
	 * @param mensaje Cadena de caracteres a convertir.
	 * @return Mensaje convertido
	 */
	private String convertirCadena(String mensaje) {
		if (logger.isDebugEnabled()) {
			logger.debug("convertirCadena(String) - start");
		}

		String stringCaracteresAModificar="áéíóúÁÉÍÓÚñÑ°º`´üÜàèìòùÀÈÌÒÙ";
		String stringCaracteresCorrectos="aeiouAEIOUnNoo''uUaeiouAEIOU";
		
		for(int i=0; i<mensaje.toCharArray().length; i++) {
			for(int y=0; y<stringCaracteresAModificar.toCharArray().length; y++) {
				if(mensaje.toLowerCase().charAt(i) == stringCaracteresAModificar.charAt(y)) {
					mensaje = mensaje.replace(mensaje.charAt(i), stringCaracteresCorrectos.charAt(y));
				}
			}
		}

		String returnString = mensaje.toUpperCase();
		if (logger.isDebugEnabled()) {
			logger.debug("convertirCadena(String) - end");
		}
		return returnString;
	}

	/**
	 * Constructor de la clase
	 * @since 06-may-2004
	 * @param status Objeto de estado del driver fiscal
	 */
	public TMU950SequenceMap(CRFPStatus status) {
		super();
		statusDriver = status;
	}

	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.CRFPSequenceMap#getDynSuffixSequence(java.lang.String)
	 * @param sequence
	 * @return
	 * @since 11-abr-2005
	 */
	public String getDynSuffixSequence(String sequence) {
		if (logger.isDebugEnabled()) {
			logger.debug("getDynSuffixSequence(String) - start");
		}

		String returnString = obtenerBCC(sequence);
		if (logger.isDebugEnabled()) {
			logger.debug("getDynSuffixSequence(String) - end");
		}
		return returnString;
	}

	
	private String obtenerBCC(String texto){
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerBCC(String) - start");
		}

		int bccDecimal = 0;
		for (int i=0; i< texto.length(); i++)
			bccDecimal += texto.charAt(i);
		String bccHexa = Integer.toHexString(bccDecimal);
		while (bccHexa.length()<4)
			bccHexa = "0" + bccHexa;
		String returnString = bccHexa.toUpperCase();
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerBCC(String) - end");
		}
		return returnString;
	}
	
	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.CRFPSequenceMap#getDataStart()
	 * @return
	 * @since 11-abr-2005
	 */
	public String getDataStart() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDataStart() - start");
		}

		String returnString = "" + '\002';
		if (logger.isDebugEnabled()) {
			logger.debug("getDataStart() - end");
		}
		return returnString;
	}

	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.CRFPSequenceMap#getDataEnd()
	 * @return
	 * @since 11-abr-2005
	 */
	public String getDataEnd() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDataEnd() - start");
		}

		String returnString = "" + '\003';
		if (logger.isDebugEnabled()) {
			logger.debug("getDataEnd() - end");
		}
		return returnString;
	}

	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.CRFPSequenceMap#getDataSeparator()
	 * @return
	 * @since 11-abr-2005
	 */
	public String getDataSeparator() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDataSeparator() - start");
		}

		String returnString = "" + '\034';
		if (logger.isDebugEnabled()) {
			logger.debug("getDataSeparator() - end");
		}
		return returnString;
	}

	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.CRFPSequenceMap#getCommand(int)
	 * @param type
	 * @return
	 * @since 11-abr-2005
	 */
	public String getCommand(int type) {
		if (logger.isDebugEnabled()) {
			logger.debug("getCommand(int) - start");
		}

		String result = null;
		switch (type) {
			case CRFiscalPrinterOperations.CMD_ABRIR_CF:
				result = "" + '\100';
				break;
			case CRFiscalPrinterOperations.CMD_ABRIR_CF_DEV:
				result = "" + '\100';
				break;
			case CRFiscalPrinterOperations.CMD_ABRIR_DNF:
				result = "" + '\110';
				break;
			case CRFiscalPrinterOperations.CMD_ACTIVAR_SLIP:
				result = "" + '\240';
				break;
			case CRFiscalPrinterOperations.CMD_AVANCE_PAPEL:
				result = "" + '\120';
				break;
			case CRFiscalPrinterOperations.CMD_CANC_CF:
			case CRFiscalPrinterOperations.CMD_CANC_CFV:
			case CRFiscalPrinterOperations.CMD_CANC_CFNC:
				result = "" + '\104' + '\034' + "CANCELAR CF" 
					+'\034' + 0 + '\034' + '\103' + '\034' + 0;
				break;
			case CRFiscalPrinterOperations.CMD_CERRAR_CF:
			case CRFiscalPrinterOperations.CMD_CERRAR_CFV:
			case CRFiscalPrinterOperations.CMD_CERRAR_CFNC:				
				result = "" + '\105';
				break;
			case CRFiscalPrinterOperations.CMD_CERRAR_DNF:
				result = "" + '\112' + '\034' + '\177';
				break;
			case CRFiscalPrinterOperations.CMD_CHEQUE:
				result = "" + '\252';
				break;
			case CRFiscalPrinterOperations.CMD_CODIGO_BARRA:
				result = "" + '\124';
				break;
			case CRFiscalPrinterOperations.CMD_CORTAR_PAPEL:
				result = "K";
				break;
			case CRFiscalPrinterOperations.CMD_DESACTIVAR_SLIP:
				result = "" + '\241';
				break;
			case CRFiscalPrinterOperations.CMD_DESACT_PROX_CORTE:
				result = "" + '\070' + '\034' + "S";
				break;
			case CRFiscalPrinterOperations.CMD_DESCUENTO_CF:
				result = "" + '\104';
				break;
			case CRFiscalPrinterOperations.CMD_ENCABEZADO:
				result = "" + '\135';
				break;
			case CRFiscalPrinterOperations.CMD_ENDOSO_CHEQUE:
				result = "" + '\253';
				break;
			case CRFiscalPrinterOperations.CMD_GET_FECHAHORA_IF:
				result = "" + '\131';
				break;
			case CRFiscalPrinterOperations.CMD_GET_SERIAL:
				result = "" + '\200';
				break;
			case CRFiscalPrinterOperations.CMD_ITEM_CF:
			case CRFiscalPrinterOperations.CMD_ITEM_CFV:
			case CRFiscalPrinterOperations.CMD_ITEM_CFNC:
				result = "" + '\102';
				break;
			case CRFiscalPrinterOperations.CMD_LOGO :
				result = "U";
				break;
			case CRFiscalPrinterOperations.CMD_PAGO_CF:
				result = "" + '\104';
				break;
			case CRFiscalPrinterOperations.CMD_PIE_PAGINA:
				result = "" + '\136';
				break;
			case CRFiscalPrinterOperations.CMD_REP_RANGO_Z:
				result = "" + '\073';
				break;
			case CRFiscalPrinterOperations.CMD_REPORTE_MF:
				result = "" + '\072';
				break;
			case CRFiscalPrinterOperations.CMD_REPORTE_X:
				result = "" + '\071' + getDataSeparator() + "X";
				break;
			case CRFiscalPrinterOperations.CMD_REPORTE_Z:
				result = "" + '\071' + getDataSeparator() + "Z";
				break;
			case CRFiscalPrinterOperations.CMD_RESET_PRINTER:
				result = "" + '\007' + '\010' + '\011' + '\012' + 
					'\013' + '\014' + '\015' + '\016' + '\017' + '\020' + 
					'\021' + '\022' + '\023' + '\024' + '\025' + '\026' + 
					'\027';
				break;
			case CRFiscalPrinterOperations.CMD_SET_FECHAHORA_IF:
				result = "" + '\130';
				break;
			case CRFiscalPrinterOperations.CMD_STATUS_IF:
				result = "" + '\070';
				break;
			case CRFiscalPrinterOperations.CMD_SUB_CF:
				result = "" + '\103'+ getDataSeparator() + 
					getEmptyValue() + getDataSeparator() + 
					getEmptyValue();
				break;
			case CRFiscalPrinterOperations.CMD_TEXTO_CF:
				result = "" + '\101';
				break;
			case CRFiscalPrinterOperations.CMD_TEXTO_DNF:
				result = "" + '\111';
				break;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getCommand(int) - end");
		}
		return result;
	}

	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.CRFPSequenceMap#getSequenceId()
	 * @return
	 * @since 11-abr-2005
	 */
	public String getSequenceId() {
		if (logger.isDebugEnabled()) {
			logger.debug("getSequenceId() - start");
		}

		String returnString = Character
				.toString((char) obtenerNuevoNumeroSecuencia());
		if (logger.isDebugEnabled()) {
			logger.debug("getSequenceId() - end");
		}
		return returnString;
	}
	
	private int obtenerNuevoNumeroSecuencia(){
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerNuevoNumeroSecuencia() - start");
		}

		int numero = Integer.parseInt(statusDriver.getUltIDSequence());
		numero -= 31;
		numero = ((numero < 0 ? -numero : numero) % 96) + 32;

		statusDriver.setUltIDSequence(Integer.toString(numero));
		
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerNuevoNumeroSecuencia() - end");
		}
		return numero;
	}
	

	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.CRFPSequenceMap#getErrorMessage(int)
	 * @param errCode
	 * @return
	 * @since 11-abr-2005
	 */
	public String getErrorMessage(int errCode) {
		if (logger.isDebugEnabled()) {
			logger.debug("getErrorMessage(int) - start");
		}

		String msgResult = null;
		if (errCode < 20) {
			if (errCode == 0) {
				msgResult = "ERRORES EN CAMPOS DE DATOS";
			} else {
				msgResult = "ERROR CAMPO " + errCode;
			}
		} else {
			switch (errCode) {
			case 20:
				msgResult = "ERRORES BUS I2C";
				break;
			case 21:
				msgResult = "ERRORES BUS I2C - LINEA I2C";
				break;
			case 22:
				msgResult = "ERRORES BUS I2C - BCC RAM";
				break;
			case 23 :
				msgResult = "ERRORES BUS I2C - BCC ROM 0";
				break;
			case 24 :
				msgResult = "ERRORES BUS I2C - BCC ROM 4";
				break;
			case 30 :
				msgResult = "ERROR COMANDO";
				break;
			case 31 :
				msgResult = "ERROR COMANDO - ERROR AL VERIFICAR COMANDO";
				break;
			case 32 :
				msgResult = "ERROR COMANDO - SECUENCIA";
				break;
			case 40 :
				msgResult = "ERRORES DE IMPRESIÓN";
				break;
			case 41 :
				msgResult = "ERROR AL IMPRIMIR";
				break;
			case 70 :
				msgResult = "ERROR DE TOTALES";
				break;
			case 71 :
				msgResult = "ERROR DE TOTALES - DESBORDE DE TOTALES";
				break;
			case 90 :
				msgResult = "ERROR CRITICO";
				break;
			case 91 :
				msgResult = "ERROR CRITICO - LIMITE MEMORIA FISCAL";
				break;
			case 92 :
				msgResult = "ERROR CRITICO - ERROR EN LOS VALORES DE LA RAM";
				break;
			case 95 :
				msgResult = "ERROR CRITICO - ERROR EN TRAMA DE DATOS (BCC)";
				break;
			case 96 :
				msgResult = "ERROR CRITICO - LA TRAMA INCLUYE UN ESC";
				break;
			case 97 :
				msgResult = "ERROR CRITICO - ERROR EN FORMATO DE DATOS AL REALIZAR UN Z";
				break;
			case 98 :
				msgResult = "ERROR CRITICO - ERROR EQUIPO SIN FISCALIZAR";
				break;
			case 99 :
				msgResult = "ERROR CRITICO - ERROR EN SUBTOTAL";
				break;
			case 100 :
				msgResult = "ABRIR_CF";
				break;
			case 101 :
				msgResult = "ABRIR_CF - SON NECESARIOS LOS DATOS DEL CLIENTE";
				break;
			case 104 :
				msgResult = "ABRIR_CF - ES NECESARIO UN REPORTE Z";
				break;
			case 110 :
				msgResult = "TEXTO_CF";
				break;
			case 111 :
				msgResult = "TEXTO_CF - MAX. CANTIDAD DE LINEAS DE TEXTO NO FISCAL";
				break;
			case 120 :
				msgResult = "ITEM_CF";
				break;
			case 121 :
				msgResult = "ITEM_CF - TASA NO VALIDA";
				break;
			case 124 :
				msgResult = "ITEM_CF - DESBORDE CANTIDAD x MONTO";
				break;
			case 125 :
				msgResult = "ITEM_CF - MONTO MAX x ARTICULO";
				break;
			case 130 :
				msgResult = "CERRAR_CF";
				break;
			case 131 :
				msgResult = "CERRAR_CF - SUBTOTAL CERO";
				break;
			case 140 :
				msgResult = "DESCUENTOS";
				break;
			case 141 :
				msgResult = "DESCUENTOS - ERROR DE DESBORDE MONTO MAX. PARA DESCUENTOS";
				break;
			case 143 :
				msgResult = "DESCUENTOS - EMITIENDO UN PAGO EN UNA DEVOLUCION";
				break;
			case 144 :
				msgResult = "DESCUENTOS - DESCUENTOS EN UNA DEVOLUCION";
				break;
			case 145 :
				msgResult = "DESCUENTOS - YA SE HA EFECTUADO UN DESCUENTO PREVIO";
				break;
			case 150 :
				msgResult = "REPORTE DE MEMORIA FISCAL";
				break;
			case 151 :
				msgResult = "REPORTE DE MEMORIA FISCAL - NO SE ENCONTRO EL REPORTE SOLICITADO";
				break;
			case 152 :
				msgResult = "REPORTE DE MEMORIA FISCAL - NO SE ENCONTRO EL REPORTE SOLICITADO";
				break;
			case 153 :
				msgResult = "REPORTE DE MEMORIA FISCAL - EL EQUIPO NO ESTA CERTIFICADO";
				break;
			case 160 :
				msgResult = "RELOJ";
				break;
			case 161 :
				msgResult = "RELOJ - PERIODO NO VALIDO. FALLA RESPECTO A LA RAM";
				break;
			case 162 :
				msgResult = "RELOJ - PERIODO NO VALIDO. FALLA RESPECTO A LA ROM";
				break;
			case 170 :
				msgResult = "DIRECCION";
				break;
			case 171 :
				msgResult = "DIRECCION - ERROR AL ESCRIBIR DIRECCION";
				break;
			case 180 :
				msgResult = "SLIP";
				break;
			case 181 :
				msgResult = "SLIP - ERROR EN SLIP CHEQUE";
				break;
			case 182 :
				msgResult = "SLIP - ERROR EN SLIP ENDOSO";
				break;
			case 183 :
				msgResult = "SLIP - COMANDO NO VALIDO. SLIP ACTIVO";
				break;
			case 190 :
				msgResult = "CORTA PAPEL";
				break;
			case 191 :
				msgResult = "CORTA PAPEL - ERROR EN COMANDO";
				break;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getErrorMessage(int) - end");
		}
		return msgResult;
	}

	private char getTipoLetra(int tipoLetra) {
		if (logger.isDebugEnabled()) {
			logger.debug("getTipoLetra(int) - start");
		}

		char tipo = '\000';
		switch (tipoLetra) {
			case 0:
				tipo = '\360';
				break;
			case 1:
				tipo = '\361';
				break;
			case 2:
				tipo = '\362';
				break;
			case 3:
				tipo = '\363';
				break;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getTipoLetra(int) - end");
		}
		return tipo;
	}
	
	private int stableMultiply(double x, double y) {
		if (logger.isDebugEnabled()) {
			logger.debug("stableMultiply(double, double) - start");
		}

		BigDecimal bx = new BigDecimal(Double.toString(x));
		BigDecimal by = new BigDecimal(Double.toString(y));
		bx = bx.multiply(by);
		int returnint = bx.intValue();
		if (logger.isDebugEnabled()) {
			logger.debug("stableMultiply(double, double) - end");
		}
		return returnint;
	}
	
	
	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.CRFPSequenceMap#getEscapeSequence(int, java.lang.Object)
	 * @param type
	 * @param data
	 * @return
	 * @since 11-abr-2005
	 */
	public String getEscapeSequence(int type, Object[] data) {
		if (logger.isDebugEnabled()) {
			logger.debug("getEscapeSequence(int, Object[]) - start");
		}

		StringBuffer result = new StringBuffer();
		SimpleDateFormat fechaFmt = new SimpleDateFormat("dd/MM/yy");
		SimpleDateFormat horaFmt = new SimpleDateFormat("HH:mm");
		
		if (type != CRFiscalPrinterOperations.CMD_RESET_PRINTER) {
			result.append(getDataStart());
			result.append(getSequenceId());
		}
		result.append(getCommand(type));
		switch (type) {
			case CRFiscalPrinterOperations.CMD_ABRIR_CF:
				result.append(getDataSeparator());
				if (data != null && data.length > 0) {
					if (data[1] != null)
						// Nombre
						result.append(convertirCadena((String)data[0]));
					else 
						result.append(getEmptyValue());
					result.append(getDataSeparator());
					if (data.length > 1 && data[1] != null)
						//RIF
						result.append(convertirCadena((String)data[1]));
					else
						result.append(getEmptyValue());
				} else {
					result.append(getEmptyValue());
					result.append(getDataSeparator());
					result.append(getEmptyValue());
				}
				result.append(getDataSeparator());
				result.append(getEmptyValue());
				result.append(getDataSeparator());
				result.append(getEmptyValue());
				result.append(getDataSeparator());
				result.append(getEmptyValue());
				result.append(getDataSeparator());
				result.append(getEmptyValue());
				result.append(getDataSeparator());
				result.append(getEmptyValue());
				result.append(getDataSeparator());
				result.append(getEmptyValue());
				result.append(getDataSeparator());
				result.append(getEmptyValue());
				break;
			case CRFiscalPrinterOperations.CMD_ABRIR_CF_DEV:
				result.append(getDataSeparator());
				if (data != null && data.length == 6) {
					DecimalFormat trFmt = new DecimalFormat("00000000");
					// Nombre
					result.append(convertirCadena((String)data[0]));
					result.append(getDataSeparator());
					// Rif
					result.append(convertirCadena((String)data[1]));
					result.append(getDataSeparator());
					// Transaccion en devolucion
					result.append(trFmt.format((Long)data[2]));
					result.append(getDataSeparator());
					// Serial de impresora que la vendió
					result.append((String)data[3]);
					result.append(getDataSeparator());
					// Fecha de venta
					result.append(fechaFmt.format((Date)data[4]));
					result.append(getDataSeparator());
					// Hora de venta
					result.append(horaFmt.format((Date)data[5]));
					result.append(getDataSeparator());
					result.append("D");
					result.append(getDataSeparator());
					result.append(getEmptyValue());
					result.append(getDataSeparator());
					result.append(getEmptyValue());
				} else {
					throw new IllegalArgumentException("Data inválida");
				}
				break;				
			case CRFiscalPrinterOperations.CMD_CERRAR_CF:
			case CRFiscalPrinterOperations.CMD_CERRAR_CFV:
			case CRFiscalPrinterOperations.CMD_CERRAR_CFNC:
				result.append(getDataSeparator());
				if (data != null && data.length == 1) {
					// Tipo de cierre
					result.append(data[0].toString());
				} else {
					throw new IllegalArgumentException("Data inválida");
				}
				break;
			case CRFiscalPrinterOperations.CMD_CHEQUE:
				result.append(getDataSeparator());
				if (data != null && data.length == 3) {
					DecimalFormat df = new DecimalFormat("#,##0.00");
					// Monto
					result.append(convertirCadena(df.format((Double)data[0])));
					result.append(getDataSeparator());
					// Beneficiario
					result.append(convertirCadena((String)data[1]));
					result.append(getDataSeparator());
					// Fecha
					result.append(fechaFmt.format((Date)data[2]));
					result.append(getDataSeparator());
					result.append("R");
					result.append(getDataSeparator());
					result.append(getEmptyValue());
					result.append(getDataSeparator());
					result.append(getEmptyValue());
					result.append(getDataSeparator());
					result.append(getEmptyValue());
					result.append(getDataSeparator());
					result.append(getEmptyValue());
				} else {
					throw new IllegalArgumentException("Data inválida");
				}				
				break;
			case CRFiscalPrinterOperations.CMD_CODIGO_BARRA:
				result.append(getDataSeparator());
				if (data != null && data.length == 1) {
					// Numero para codigo de barra
					result.append((String)data[0]);
				} else {
					throw new IllegalArgumentException("Data inválida");
				}
				break;
			case CRFiscalPrinterOperations.CMD_DESCUENTO_CF:
				result.append(getDataSeparator());
				if (data != null && data.length == 4) {
					// Descripcion
					result.append(convertirCadena((String)data[0]));
					result.append(getDataSeparator());
					int tasaImp = stableMultiply(((Double)data[2]).doubleValue(), 100);
					int mto = stableMultiply(((Double)data[1]).doubleValue(), 100);
					// Monto
					result.append(mto);
					result.append(getDataSeparator());
					// Tipo de operacion
					result.append(data[3].toString());
					result.append(getDataSeparator());
					// Tasa impositiva
					result.append(tasaImp);
				} else {
					throw new IllegalArgumentException("Data inválida");
				}				
				break;
			case CRFiscalPrinterOperations.CMD_ENCABEZADO:
				result.append(getDataSeparator());
				if (data != null && data.length == 2) {
					// 	Numero de linea
					result.append(data[0].toString());
					result.append(getDataSeparator());
					// Testo a incluir
					result.append(convertirCadena((String)data[1]));
					
				} else {
					throw new IllegalArgumentException("Data inválida");
				}
				break;
			case CRFiscalPrinterOperations.CMD_ENDOSO_CHEQUE:
				result.append(getDataSeparator());
				if (data != null && data.length == 4) {
					// Linea 1
					result.append(convertirCadena((String)data[0]));
					result.append(getDataSeparator());
					// Linea 2
					result.append(convertirCadena((String)data[1]));
					result.append(getDataSeparator());
					// Linea 3
					result.append(convertirCadena((String)data[2]));
					result.append(getDataSeparator());
					// Tipo de operacion
					result.append(data[3].toString());
				} else {
					throw new IllegalArgumentException("Data inválida");
				}				
				break;
			case CRFiscalPrinterOperations.CMD_ITEM_CF:
			case CRFiscalPrinterOperations.CMD_ITEM_CFV:
			case CRFiscalPrinterOperations.CMD_ITEM_CFNC:
				result.append(getDataSeparator());
				if (data != null && data.length == 5) {
					// Descripción del item
					result.append(convertirCadena((String)data[0]));
					result.append(getDataSeparator());
					int tasaImp = stableMultiply(((Double)data[3]).doubleValue(), 100);
					int cant = stableMultiply(((Float)data[1]).doubleValue(), 1000);
					int mto = stableMultiply(((Double)data[2]).doubleValue(), 100);
					// Cantidad
					result.append(cant);
					result.append(getDataSeparator());
					// Monto
					result.append(mto);
					result.append(getDataSeparator());
					// Tasa impositiva
					result.append(tasaImp);
					result.append(getDataSeparator());
					// Tipo de detalle
					result.append(data[4].toString());
					result.append(getDataSeparator());
					result.append(getEmptyValue());
					result.append(getDataSeparator());
					result.append(getEmptyValue());
					result.append(getDataSeparator());
					result.append(getEmptyValue());
				} else {
					throw new IllegalArgumentException("Data inválida");
				}
				break;
			case CRFiscalPrinterOperations.CMD_PAGO_CF:
				result.append(getDataSeparator());
				if (data != null && data.length == 3) {
					// Descripcion
					result.append(convertirCadena((String)data[0]));
					result.append(getDataSeparator());
					int tasaImp = stableMultiply(((Double)data[2]).doubleValue(), 100);
					int mto = stableMultiply(((Double)data[1]).doubleValue(), 100);
					// Monto
					result.append(mto);
					result.append(getDataSeparator());
					// Tipo de operacion
					result.append("T");
					result.append(getDataSeparator());
					// Tasa impositiva
					result.append(tasaImp);
				} else {
					throw new IllegalArgumentException("Data inválida");
				}				
				break;
			case CRFiscalPrinterOperations.CMD_PIE_PAGINA:
				result.append(getDataSeparator());
				if (data != null && data.length == 2) {
					// 	Numero de linea
					result.append(data[0].toString());
					result.append(getDataSeparator());
					// Testo a incluir
					result.append(convertirCadena((String)data[1]));
					
				} else {
					throw new IllegalArgumentException("Data inválida");
				}
				break;
			case CRFiscalPrinterOperations.CMD_REP_RANGO_Z:
				result.append(getDataSeparator());
				if (data != null && data.length == 3) {
					// Z inicio
					result.append(data[0].toString());
					result.append(getDataSeparator());
					// Z fin
					result.append(data[1].toString());
					result.append(getDataSeparator());
					// Al PC o no
					String tipo = ((Boolean)data[2]).booleanValue() ?
							"C" : " ";
					result.append(tipo);
				} else {
					throw new IllegalArgumentException("Data inválida");
				}
				break;
			case CRFiscalPrinterOperations.CMD_REPORTE_MF:
				result.append(getDataSeparator());
				if (data != null && data.length == 3) {
					// Fecha inicio
					result.append(fechaFmt.format((Date)data[0]));
					result.append(getDataSeparator());
					// Fecha fin
					result.append(fechaFmt.format((Date)data[1]));
					result.append(getDataSeparator());
					// Tipo de reporte
					result.append(data[2].toString());
				} else {
					throw new IllegalArgumentException("Data inválida");
				}
				break;
			case CRFiscalPrinterOperations.CMD_SET_FECHAHORA_IF:
				result.append(getDataSeparator());
				if (data != null && data.length == 1) {
					// Fecha y hora
					result.append(fechaFmt.format((Date)data[0]));
					result.append(getDataSeparator());
					result.append(horaFmt.format((Date)data[0]));
				} else {
					throw new IllegalArgumentException("Data inválida");
				}
				break;
			case CRFiscalPrinterOperations.CMD_STATUS_IF:
				result.append(getDataSeparator());
				if (data != null && data.length >= 1) {
					// Tipo de consulta
					result.append(data[0].toString());
				} else {
					throw new IllegalArgumentException("Data inválida");
				}
				break;
			case CRFiscalPrinterOperations.CMD_TEXTO_CF:
				result.append(getDataSeparator());
				if (data != null && data.length == 3) {
					// Tipo de letra
					result.append(getTipoLetra(((Integer)data[1]).intValue()));
					// Texto a imprimir
					result.append(convertirCadena(data[0].toString()));
					result.append(getDataSeparator());
					// Imprimir en auditoria o no
					if (((Boolean)data[2]).booleanValue()) {
						result.append("S");
					} else {
						result.append(getEmptyValue());
					}
					
				} else {
					throw new IllegalArgumentException("Data inválida");
				}
				break;
			case CRFiscalPrinterOperations.CMD_TEXTO_DNF:
				result.append(getDataSeparator());
				if (data != null && data.length == 2) {
					// Tipo de letra
					result.append(getTipoLetra(((Integer)data[1]).intValue()));
					// Texto a imprimir
					result.append(convertirCadena(data[0].toString()));
					
				} else {
					throw new IllegalArgumentException("Data inválida");
				}
				
				break;
		}
		if (type != CRFiscalPrinterOperations.CMD_RESET_PRINTER) {
			result.append(getDataEnd());
			result.append(getDynSuffixSequence(result.toString()));
		}
		String returnString = result.toString();
		if (logger.isDebugEnabled()) {
			logger.debug("getEscapeSequence(int, Object[]) - end");
		}
		return returnString;
	}

	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.CRFPSequenceMap#getEmptyValue()
	 * @return
	 * @since 12-abr-2005
	 */
	public String getEmptyValue() {
		if (logger.isDebugEnabled()) {
			logger.debug("getEmptyValue() - start");
		}

		String returnString = "" + '\177';
		if (logger.isDebugEnabled()) {
			logger.debug("getEmptyValue() - end");
		}
		return returnString;
	}
	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.CRFPSequenceMap#getSequenceId(java.lang.String)
	 * @param secuencia
	 * @return
	 * @since 13-abr-2005
	 */
	public String getSequenceId(String secuencia) {
		if (logger.isDebugEnabled()) {
			logger.debug("getSequenceId(String) - start");
		}

		char seqChar = secuencia.charAt(1);
		String returnString = new Integer(seqChar).toString();
		if (logger.isDebugEnabled()) {
			logger.debug("getSequenceId(String) - end");
		}
		return returnString;
	}
	
	int getCommandType(char seqCommand) {
		if (logger.isDebugEnabled()) {
			logger.debug("getCommandType(char) - start");
		}

		int result = -1;
		switch (seqCommand) {
		case '\100' :
			result = CRFiscalPrinterOperations.CMD_ABRIR_CF;
			break;
		case '\110':
			result = CRFiscalPrinterOperations.CMD_ABRIR_DNF;
			break;
		case '\240':
			result = CRFiscalPrinterOperations.CMD_ACTIVAR_SLIP;
			break;
		case '\120':
			result = CRFiscalPrinterOperations.CMD_AVANCE_PAPEL;
			break;
		case '\104':
			result = CRFiscalPrinterOperations.CMD_CANC_CF;
			break;
		case '\105':
		case '\023':
			result = CRFiscalPrinterOperations.CMD_CERRAR_CF;
			break;
		case '\112':
			result = CRFiscalPrinterOperations.CMD_CERRAR_DNF;
			break;
		case '\252':
			result = CRFiscalPrinterOperations.CMD_CHEQUE;
			break;
		case '\124':
			result = CRFiscalPrinterOperations.CMD_CODIGO_BARRA;
			break;
		case 'K':
			result = CRFiscalPrinterOperations.CMD_CORTAR_PAPEL;
			break;
		case '\241':
			result = CRFiscalPrinterOperations.CMD_DESACTIVAR_SLIP;
			break;
		case '\135':
			result = CRFiscalPrinterOperations.CMD_ENCABEZADO;
			break;
		case '\253':
			result = CRFiscalPrinterOperations.CMD_ENDOSO_CHEQUE;
			break;
		case '\131':
			result = CRFiscalPrinterOperations.CMD_GET_FECHAHORA_IF;
			break;
		case '\102':
			result = CRFiscalPrinterOperations.CMD_ITEM_CF;
			break;
		case '\136':
			result = CRFiscalPrinterOperations.CMD_PIE_PAGINA;
			break;
		case '\073':
			result = CRFiscalPrinterOperations.CMD_REP_RANGO_Z;
			break;
		case '\072':
			result = CRFiscalPrinterOperations.CMD_REPORTE_MF;
			break;
		case '\071':
			result = CRFiscalPrinterOperations.CMD_REPORTE_Z;
			break;
		case '\130':
			result = CRFiscalPrinterOperations.CMD_SET_FECHAHORA_IF;
			break;
		case '\070':
			result = CRFiscalPrinterOperations.CMD_STATUS_IF;
			break;
		case '\103':
			result = CRFiscalPrinterOperations.CMD_SUB_CF;
			break;
		case '\101':
			result = CRFiscalPrinterOperations.CMD_TEXTO_CF;
			break;
		case '\111':
			result = CRFiscalPrinterOperations.CMD_TEXTO_DNF;
			break;
		case '\200':
			result = CRFiscalPrinterOperations.CMD_GET_SERIAL;
			break;
		case 'U':
			result = CRFiscalPrinterOperations.CMD_LOGO;
			break;
	}

		if (logger.isDebugEnabled()) {
			logger.debug("getCommandType(char) - end");
		}
		return result;
	}
	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.CRFPSequenceMap#getTimeToSleep(int)
	 * @param type
	 * @return
	 * @since 14-abr-2005
	 */
	public long getTimeToSleep(int type) {
		if (logger.isDebugEnabled()) {
			logger.debug("getTimeToSleep(int) - start");
		}

		long valor = -1;
		switch (type) {
			case CRFiscalPrinterOperations.CMD_ABRIR_CF :
			case CRFiscalPrinterOperations.CMD_ABRIR_CF_DEV :
				valor = 3000;
				break;
			case CRFiscalPrinterOperations.CMD_ABRIR_DNF :
			case CRFiscalPrinterOperations.CMD_ENDOSO_CHEQUE :
			case CRFiscalPrinterOperations.CMD_CERRAR_DNF :
				valor = 800;
				break;
			case CRFiscalPrinterOperations.CMD_CERRAR_CF :
			case CRFiscalPrinterOperations.CMD_CERRAR_CFV :
			case CRFiscalPrinterOperations.CMD_CERRAR_CFNC :
				
			case CRFiscalPrinterOperations.CMD_REP_RANGO_Z:
			case CRFiscalPrinterOperations.CMD_REPORTE_MF:
			case CRFiscalPrinterOperations.CMD_REPORTE_X:
			case CRFiscalPrinterOperations.CMD_REPORTE_Z:
				valor = 1000;
			case CRFiscalPrinterOperations.CMD_RESET_PRINTER :
				valor = 2000;
				break;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getTimeToSleep(int) - end");
		}
		return valor;
	}
	
	
	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.CRFPSequenceMap#getTipoRespuestaEsperada(int)
	 * @param cmdType
	 * @return
	 * @since 15-abr-2005
	 */
	public int getTipoRespuestaEsperada(int cmdType) {
		if (logger.isDebugEnabled()) {
			logger.debug("getTipoRespuestaEsperada(int) - start");
		}

		int result = 0;
		switch (cmdType) {
		case CRFiscalPrinterOperations.CMD_ABRIR_CF:
			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_ABRIR_CF_DEV:
			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_ABRIR_DNF:
			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_ACTIVAR_SLIP:
			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_AVANCE_PAPEL:
			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_CANC_CF:
		case CRFiscalPrinterOperations.CMD_CANC_CFV:
		case CRFiscalPrinterOperations.CMD_CANC_CFNC:
			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_CERRAR_CF:
		case CRFiscalPrinterOperations.CMD_CERRAR_CFV:
		case CRFiscalPrinterOperations.CMD_CERRAR_CFNC:
			result = CRFPStatus.DATA_CIERRE_CF;
			break;
		case CRFiscalPrinterOperations.CMD_CERRAR_DNF:
			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_CHEQUE:
			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_CODIGO_BARRA:
			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_CORTAR_PAPEL:
			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_DESACTIVAR_SLIP:
			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_DESACT_PROX_CORTE:
			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_DESCUENTO_CF:
			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_ENCABEZADO:
			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_ENDOSO_CHEQUE:
			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_GET_FECHAHORA_IF:
			result = CRFPStatus.DATA_FECHAHORA;
			break;
		case CRFiscalPrinterOperations.CMD_GET_SERIAL:
			result = CRFPStatus.DATA_SERIAL;
			break;
		case CRFiscalPrinterOperations.CMD_ITEM_CF:
		case CRFiscalPrinterOperations.CMD_ITEM_CFV:
		case CRFiscalPrinterOperations.CMD_ITEM_CFNC:
			result = CRFPStatus.DATA_ITEM;
			break;
		case CRFiscalPrinterOperations.CMD_LOGO :
			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_PAGO_CF:
			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_PIE_PAGINA:
			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_REP_RANGO_Z:
			result = CRFPStatus.DATA_RANGO_Z;
			break;
		case CRFiscalPrinterOperations.CMD_REPORTE_MF:
			result = CRFPStatus.DATA_REP_MF;
			break;
		case CRFiscalPrinterOperations.CMD_REPORTE_X:
			result = CRFPStatus.DATA_REP_Z;
			break;
		case CRFiscalPrinterOperations.CMD_REPORTE_Z:
			result = CRFPStatus.DATA_REP_Z;
		break;
		case CRFiscalPrinterOperations.CMD_RESET_PRINTER:
			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_SET_FECHAHORA_IF:
			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_STATUS_IF:
			result = CRFPStatus.DATA_STATUS_N;
			break;
		case CRFiscalPrinterOperations.CMD_SUB_CF:
			result = CRFPStatus.DATA_SUBTOTAL;
			break;
		case CRFiscalPrinterOperations.CMD_TEXTO_CF:
			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_TEXTO_DNF:
			result = CRFPStatus.DATA_NONE;
			break;
	}

		if (logger.isDebugEnabled()) {
			logger.debug("getTipoRespuestaEsperada(int) - end");
		}
	return result;
	}
	
	
	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.CRFPSequenceMap#getActionOnError(int)
	 * @param errCode
	 * @return
	 * @since 15-abr-2005
	 */
	public int getActionOnError(int errCode) {
		if (logger.isDebugEnabled()) {
			logger.debug("getActionOnError(int) - start");
		}

		int result = 0;
		if (errCode < 20) {
			// Error en campo de datos
			result = CRFPEngine.ABORTAR_DOCUMENTO;
		} else {
			switch (errCode) {
			case 20:
				// "ERRORES BUS I2C";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 21:
				// "ERRORES BUS I2C - LINEA I2C";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 22:
				// "ERRORES BUS I2C - BCC RAM";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 23 :
				// "ERRORES BUS I2C - BCC ROM 0";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 24 :
				// "ERRORES BUS I2C - BCC ROM 4";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 30 :
				// "ERROR COMANDO";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 31 :
				// "ERROR COMANDO - ERROR AL VERIFICAR COMANDO";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 32 :
				// "ERROR COMANDO - SECUENCIA";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 40 :
				// "ERRORES DE IMPRESIÓN";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 41 :
				// "ERROR AL IMPRIMIR";
				result = CRFPEngine.ESPERAR_APLICACION;
				break;
			case 70 :
				// "ERROR DE TOTALES";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 71 :
				// "ERROR DE TOTALES - DESBORDE DE TOTALES";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 90 :
				// "ERROR CRITICO";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 91 :
				// "ERROR CRITICO - LIMITE MEMORIA FISCAL";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 92 :
				// "ERROR CRITICO - ERROR EN LOS VALORES DE LA RAM";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 95 :
				// "ERROR CRITICO - ERROR EN TRAMA DE DATOS (BCC)";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 96 :
				// "ERROR CRITICO - LA TRAMA INCLUYE UN ESC";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 97 :
				// "ERROR CRITICO - ERROR EN FORMATO DE DATOS AL REALIZAR UN Z";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 98 :
				// "ERROR CRITICO - ERROR EQUIPO SIN FISCALIZAR";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 99 :
				// "ERROR CRITICO - ERROR EN SUBTOTAL";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 100 :
				// "ABRIR_CF";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 101 :
				// "ABRIR_CF - SON NECESARIOS LOS DATOS DEL CLIENTE";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 104 :
				// "ABRIR_CF - ES NECESARIO UN REPORTE Z";
				result = CRFPEngine.REALIZAR_Z;
				break;
			case 110 :
				// "TEXTO_CF";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 111 :
				// "TEXTO_CF - MAX. CANTIDAD DE LINEAS DE TEXTO NO FISCAL";
				result = CRFPEngine.CERRAR_CF;
				break;
			case 120 :
				// "ITEM_CF";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 121 :
				// "ITEM_CF - TASA NO VALIDA";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 124 :
				// "ITEM_CF - DESBORDE CANTIDAD x MONTO";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 125 :
				// "ITEM_CF - MONTO MAX x ARTICULO";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 130 :
				// "CERRAR_CF";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 131 :
				// "CERRAR_CF - SUBTOTAL CERO";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 140 :
				// "DESCUENTOS";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 141 :
				// "DESCUENTOS - ERROR DE DESBORDE MONTO MAX. PARA DESCUENTOS";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 143 :
				// "DESCUENTOS - EMITIENDO UN PAGO EN UNA DEVOLUCION";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 144 :
				// "DESCUENTOS - DESCUENTOS EN UNA DEVOLUCION";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 145 :
				// "DESCUENTOS - YA SE HA EFECTUADO UN DESCUENTO PREVIO";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 150 :
				// "REPORTE DE MEMORIA FISCAL";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 151 :
				// "REPORTE DE MEMORIA FISCAL - NO SE ENCONTRO EL REPORTE SOLICITADO";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 152 :
				// "REPORTE DE MEMORIA FISCAL - NO SE ENCONTRO EL REPORTE SOLICITADO";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 153 :
				// "REPORTE DE MEMORIA FISCAL - EL EQUIPO NO ESTA CERTIFICADO";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 160 :
				// "RELOJ";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 161 :
				// "RELOJ - PERIODO NO VALIDO. FALLA RESPECTO A LA RAM";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 162 :
				// "RELOJ - PERIODO NO VALIDO. FALLA RESPECTO A LA ROM";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 170 :
				// "DIRECCION";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 171 :
				// "DIRECCION - ERROR AL ESCRIBIR DIRECCION";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 180 :
				// "SLIP";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 181 :
				// "SLIP - ERROR EN SLIP CHEQUE";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 182 :
				// "SLIP - ERROR EN SLIP ENDOSO";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 183 :
				// "SLIP - COMANDO NO VALIDO. SLIP ACTIVO";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 190 :
				// "CORTA PAPEL";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			case 191 :
				// "CORTA PAPEL - ERROR EN COMANDO";
				result = CRFPEngine.ABORTAR_DOCUMENTO;
				break;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getActionOnError(int) - end");
		}
		return result;
	}
	
	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.CRFPSequenceMap#getExtraTimetoSleep()
	 * @return
	 * @since 05-may-2004
	 */
	public int getExtraTimetoSleep() {
		return 800;
	}
	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.CRFPSequenceMap#getSlipTimeGap()
	 * @return
	 * @since 05-may-2004
	 */
	public int getSlipTimeGap() {
		return 1000;
	}
	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.CRFPSequenceMap#getEsperaTimeout()
	 * @return
	 * @since 05-may-2004
	 */
	public int getEsperaTimeout() {
		return 5000;
	}
	
	
	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.CRFPSequenceMap#implementedCommand(int)
	 * @param type
	 * @return
	 * @since 05-may-2004
	 */
	public boolean implementedCommand(int type) {
		boolean result = true;
		switch(type) {
			case CRFiscalPrinterOperations.CMD_CODIGO_BARRA :
				result = false;
				break;
			case CRFiscalPrinterOperations.CMD_LOGO :
				result = false;
				break;
		}
		return result;
	}
	/* (no Javadoc)
	 * Sólo aplica para GD4
	 * @see com.epa.crprinterdriver.CRFPSequenceMap#setTabla(com.epa.crprinterdriver.data.DataTablaImpuestos)
	 */
	public void setTabla(DataTablaImpuestos tabla) {
		//NPF4610SequenceMap.tabla=tabla;
	}

	/* (no Javadoc)
	 * Sólo aplica para GD4
	 * @see com.epa.crprinterdriver.CRFPSequenceMap#setTabla(com.epa.crprinterdriver.data.DataTablaImpuestos)
	 */
	public void setMetodoImpuesto(Integer metodo){
		
	}

	/* (no Javadoc)
	 * Sólo aplica para GD4
	 * @see com.epa.crprinterdriver.CRFPSequenceMap#setTabla(com.epa.crprinterdriver.data.DataTablaImpuestos)
	 */
	public void setCalculoMetodoExclusivo(int valor){
		
	}	
}
