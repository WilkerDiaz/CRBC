/*
 * $Id: CRFPCommand.java,v 1.2.2.5 2005/05/18 15:10:23 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CRFiscalPrinterDriver 1.1
 * Paquete		: com.epa.crprinterdriver.event
 * Programa		: CRFPCommand.java
 * Creado por	: Programa8 - Arístides Castillo Colmenárez
 * Creado en 	: 11-abr-2005 15:03:46
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
 * $Log: CRFPCommand.java,v $
 * Revision 1.2.2.5  2005/05/18 15:10:23  programa8
 * Ajuste de código para permitir trazabilidad en log
 *
 * Revision 1.2.2.4  2005/05/09 14:28:25  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.3  2005/05/09 14:19:07  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.2  2005/05/06 19:12:50  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.2.2.1  2005/05/06 18:17:04  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.2  2005/05/05 12:08:47  programa8
 * Versión 2.0.1 Final
 *
 * Revision 1.1.2.4  2005/04/18 16:39:14  programa8
 * Driver Fiscal al 18/04/2005 - 12m
 *
 * Revision 1.1.2.3  2005/04/13 21:56:57  programa8
 * Driver fiscal al 13/04/2005
 *
 * Revision 1.1.2.2  2005/04/12 19:21:32  programa8
 * Ajuste en prototipo del mapa de secuencias (Pase de parámetros)
 *
 * Revision 1.1.2.1  2005/04/11 20:59:06  programa8
 * CRFiscalPrinterDriver 2.0 - Analisis Preliminar
 *
 * ===========================================================================
 */
package com.epa.crprinterdriver;

import org.apache.log4j.Logger;


/**
 * <p>
 * Clase que representa un comando fiscal.  
 * </p>
 * <p>
 * <a href="CRFPCommand.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - Arístides Castillo Colmenárez - $Author: programa8 $
 * @version $Revision: 1.2.2.5 $ - $Date: 2005/05/18 15:10:23 $
 * @since 11-abr-2005
 * 
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se comentó variable sin uso
* Fecha: agosto 2011
*/
public class CRFPCommand {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CRFPCommand.class);

	//private CRFPSequenceMap sequenceMap;
	private String secuenciaEscape;
	private String sequenceId;
	private long espera = -1;
	private int type;
	private Object[] data;
	private int tipoRespEsperada;


	/**
	 * Constructor para comandos que no requieren parámetros
	 * 
	 * @since 11-abr-2005
	 * @param seqMap Mapa de secuencias de dispositivo actual
	 * @param type Constante de tipo de comando fiscal (CRFiscalOperations.CMD_XXX)
	 * @throws IllegalArgumentException si el comando requiere parámetros por parte del dispositivo 
	 */
	public CRFPCommand(CRFPSequenceMap seqMap, int type) {
		this(seqMap, type, null);
	}

	/**
	 * Constructor para comandos que requieren parámetros extras para ser enviados al
	 * dispositivo fiscal 
	 * @since 11-abr-2004
	 * @param seqMap Mapa de secuencias de dispositivo actual
	 * @param type Constante de tipo de comando fiscal (CRFiscalOperations.CMD_XXX)
	 * @param data Arreglo de objetos conteniendo los parámetros requeridos por el objeto
	 * @throws IllegalArgumentException si los parámetros no son los esperados por el comando
	 */
	public CRFPCommand(CRFPSequenceMap seqMap, int type, Object[] data) {
		super();
		this.type = type;
		//this.sequenceMap = seqMap;
		this.data = data;
		espera = seqMap.getTimeToSleep(type); 
		secuenciaEscape = seqMap.getEscapeSequence(type, data);
		sequenceId = seqMap.getSequenceId(secuenciaEscape);
		tipoRespEsperada = seqMap.getTipoRespuestaEsperada(type);
		
	}
	
	/**
	 * Método para decodificar la descripción del tipo de comando
	 * @param type Constante de tipo de comando
	 * @return Cadena de identificación del tipo de comando
	 */
	public static String getTypeDescription(int type) {
	    String result = "Desconocido";
	    switch (type) {
			case 0 : 
			    result = "CMD_ABRIR_CF";
				break;
			case 1 : 
			    result = "CMD_TEXTO_CF";	    	
			    break;
			case 2 : 
			    result = "CMD_ITEM_CF";	    	
			    break;
			case 3 : 
			    result = "CMD_SUB_CF";	    	
			    break;
			case 4 : 
			    result = "CMD_DESCUENTO_CF";	    	
			    break;
			case 5 : 
			    result = "CMD_CERRAR_CF";	    	
			    break;
			case 6 : 
			    result = "CMD_REPORTE_X";	    	
			    break;
			case 7 : 
			    result = "CMD_REPORTE_Z";	    	
			    break;
			case 8 : 
			    result = "CMD_ABRIR_DNF";	    	
			    break;
			case 9 : 
			    result = "CMD_TEXTO_DNF";	    	
			    break;
			case 10 : 
			    result = "CMD_CERRAR_DNF";	    	
			    break;
			case 11 : 
			    result = "CMD_ABRIR_CF_DEV";	    	
			    break;
			case 12 : 
			    result = "CMD_ACTIVAR_SLIP";	    	
			    break;
			case 13 : 
			    result = "CMD_DESACTIVAR_SLIP";	    	
			    break;
			case 14 : 
			    result = "CMD_CANC_CF";	    	
			    break;
			case 15 : 
			    result = "CMD_CORTAR_PAPEL";	    	
			    break;
			case 16 : 
			    result = "CMD_CHEQUE";	    	
			    break;
			case 17 : 
			    result = "CMD_ENDOSO_CHEQUE";	    	
			    break;
			case 18 : 
			    result = "CMD_PAGO_CF";	    	
			    break;
			case 19 : 
			    result = "CMD_RESET_PRINTER";	    	
			    break;
			case 20 : 
			    result = "CMD_STATUS_IF";	    	
			    break;
			case 21 : 
			    result = "CMD_CODIGO_BARRA";	    	
			    break;
			case 22 : 
			    result = "CMD_GET_FECHAHORA_IF";	    	
			    break;
			case 23 : 
			    result = "CMD_SET_FECHAHORA_IF";	    	
			    break;
			case 24 : 
			    result = "CMD_REPORTE_MF";	    	
			    break;
			case 25 : 
			    result = "CMD_REP_RANGO_Z";	    	
			    break;
			case 26 : 
			    result = "CMD_ENCABEZADO";	    	
			    break;
			case 27 : 
			    result = "CMD_PIE_PAGINA";	    	
			    break;
			case 28 : 
			    result = "CMD_DESACT_PROX_CORTE";	    	
			    break;
			case 29 : 
			    result = "CMD_AVANCE_PAPEL";	    	
			    break;
			case 30 : 
			    result = "CMD_GET_SERIAL";	    	
			    break;
			case 31 : 
			    result = "CMD_LOGO";	    	
			    break;
			case 32 :
				result = "CMD_ABRIR_GAVETA";
				break;
			case 33 :
				result = "CMD_CERRAR_CFV";
				break;
			case 34 :
				result = "CMD_CERRAR_CFNC";
				break;
			case 35 :
				result = "CMD_CANC_CFV";
				break;
			case 36 :
				result = "CMD_CANC_CFNC";
				break;
			case 37 :
				result = "CMD_ITEM_CFV";
				break;
			case 38 :
				result = "CMD_ITEM_CFNC";
				break;
			case 39 :
				result = "CMD_ITEM_NEG_CFV";
				break;
			case 40 :
				result = "CMD_ITEM_NEG_CFNC";
				break;
			case 41 :
				result = "CMD_TABLA_IMP";
				break;
			case 54 :
				result = "CMD_TABLA_IMP2";
				break;
			case 42 :
				result = "CMD_SUBTOTAL_CF";
				break;
			case 43:
				result = "CMD_READ_TOTAL";
				break;			
			case 44:
				result = "CMD_LINEA_CHEQUE";
				break;
			case 45:
				result = "CMD_CERRAR_CHEQUE";
				break;
			case 46:
				result = "CMD_CANC_CHEQUE";
				break;
			case 47:
				result = "CMD_EXPL_DOC";
				break;
			case 48: 
				result = "CMD_AVANCE_CHEQUE";
				break;
			case 49: 
				result = "CMD_VOLTEA_DOC";
				break;
			case 50: 
				result = "CMD_INFO_TIENDA";
				break;
	    }
	    return result;
	}
	
	
    /* (sin Javadoc)
     * @see java.lang.Object#toString()
     * @return
     * @since 18/05/2005
     */
    public String toString() {
        StringBuffer o = new StringBuffer(getTypeDescription(type));
        if (data != null  && data.length > 0) {
            o.append(": "); 
            for (int i = 0; i < data.length; i++){
                o.append(data[i]);
                if (i < data.length - 1) {
                    o.append(", ");
                }
            }
        }
        return o.toString();
    }
	/**
	 * Permite recuperar los datos con los que se construyo la secuencia de escape
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
	 * Retorna la secuencia de escape producto del comando actual, en el 
	 * dispositivo actual de impresión
	 * @return Devuelve secuenciaEscape.
	 */
	public String getSecuenciaEscape() {
		if (logger.isDebugEnabled()) {
			logger.debug("getSecuenciaEscape() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getSecuenciaEscape() - end");
		}
		return secuenciaEscape;
	}
	/**
	 * Indica el tipo de comando
	 * Una de las constantes CRFiscalPrinterOperations.CMD_XXX
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
	 * Retorna el identificador de secuencia requerido por el dispositivo fiscal
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
	
	/**
	 * Realiza la espera correspondiente al comando actual, en caso
	 * que deba esperar
	 */
	public void esperar() {
		if (logger.isDebugEnabled()) {
			logger.debug("esperar() - start");
		}

		if (espera != -1) {
			try {
				Thread.sleep(espera);
			} catch (InterruptedException e) {
				logger.error("esperar()", e);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("esperar() - end");
		}
	}
	/**
	 * Indica el tipo de respuesta que espera este comando.
	 * Es una de las constantes CRFPStatus.DATA_XXX
	 * @return Devuelve tipoRespEsperada.
	 */
	public int getTipoRespEsperada() {
		if (logger.isDebugEnabled()) {
			logger.debug("getTipoRespEsperada() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getTipoRespEsperada() - end");
		}
		return tipoRespEsperada;
	}
	
	/**
	 * Establece el tipo de respuesta esperada. Se usa para cambiar el comportamiento
	 * por defecto del comando   
	 * Es una de las constantes CRFPStatus.DATA_XXX
	 * @param tipoRespEsperada El tipoRespEsperada a establecer.
	 */
	public void setTipoRespEsperada(int tipoRespEsperada) {
		if (logger.isDebugEnabled()) {
			logger.debug("setTipoRespEsperada(int) - start");
		}

		this.tipoRespEsperada = tipoRespEsperada;

		if (logger.isDebugEnabled()) {
			logger.debug("setTipoRespEsperada(int) - end");
		}
	}
}
