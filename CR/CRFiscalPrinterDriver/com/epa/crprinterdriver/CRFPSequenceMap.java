/*
 * $Id: CRFPSequenceMap.java,v 1.2.2.3 2005/05/09 14:28:24 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CRFiscalPrinterDriver 1.1
 * Paquete		: com.epa.crprinterdriver
 * Programa		: CRFPSequenceMap.java
 * Creado por	: Programa8 - Arístides Castillo Colmenárez
 * Creado en 	: 11-abr-2005 13:43:20
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
 * $Log: CRFPSequenceMap.java,v $
 * Revision 1.2.2.3  2005/05/09 14:28:24  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.2  2005/05/09 14:19:06  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.1  2005/05/05 22:05:43  programa8
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
 * Revision 1.2  2005/05/05 12:08:47  programa8
 * Versión 2.0.1 Final
 *
 * Revision 1.1.2.4  2005/04/18 16:39:18  programa8
 * Driver Fiscal al 18/04/2005 - 12m
 *
 * Revision 1.1.2.3  2005/04/13 21:57:02  programa8
 * Driver fiscal al 13/04/2005
 *
 * Revision 1.1.2.2  2005/04/12 19:20:56  programa8
 * Agregado metodo de obtención de valores vacios o nulos
 *
 * Revision 1.1.2.1  2005/04/11 20:59:07  programa8
 * CRFiscalPrinterDriver 2.0 - Analisis Preliminar
 *
 * ===========================================================================
 */
package com.epa.crprinterdriver;

import com.epa.crprinterdriver.data.DataTablaImpuestos;

/**
 * <p>
 * Interfaz encargada de indicar las secuencias de escape del
 * dispositivo fiscal, así como la implementación de los procedimientos
 * de conversión de datos del protocolo fiscal
 * </p>
 * <p>
 * <a href="CRFPSequenceMap.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - Arístides Castillo Colmenárez - $Author: programa8 $
 * @version $Revision: 1.2.2.3 $ - $Date: 2005/05/09 14:28:24 $
 * @since 11-abr-2005
 * 
 */
public interface CRFPSequenceMap {
	/**
	 * Retorna la cadena de terminación dinámica de mensaje del protocolo fiscal
	 * 
	 * @param sequence Secuencia a partir de la cual se generará el sufijo
	 * @return Cadena con el sufijo de la secuencia
	 */
	public String getDynSuffixSequence(String sequence);
	
	/**
	 * Indica la cadena de comienzo de datos 
	 * @return Cadena de inicio de datos
	 */
	public String getDataStart();
	
	/**
	 * Retorna la cadena de fin de datos
	 * @return Cadena de fin de datos
	 */
	public String getDataEnd();
	
	/**
	 * Retorna la cadena de separación de campos de datos
	 * @return Cadena separadora de datos
	 */
	public String getDataSeparator();
	
	/**
	 * Retorna la cadena de secuencia de escape para un comando dado. 
	 * Los tipos de comandos pueden ser cualquiera de las variables de
	 * CMD_XXX de CRFiscalPrinterOperations
	 * @param type Tipo generico de comando
	 * @return Secuencia de escape de comando
	 */
	public String getCommand(int type);
	
	/**
	 * Retorna el identificador de la secuencia, necesario para el 
	 * protocolo fiscal
	 * @return Cadena de identificación de secuencia
	 */
	public String getSequenceId();
	
	/**
	 * Dado un codigo de error del dispositivo fiscal, retorna el mensaje
	 * de error correspondiente a dicho código
	 * @param errCode Entero que identifica al error ocurrido
	 * @return Cadena de mensaje de error correspondiente
	 */
	public String getErrorMessage(int errCode);
	
	/**
	 * Genera la secuencia de escape para un comando en particular basado en 
	 * el protocolo definido para el dispositivo fiscal de implementación
	 * @param type
	 * @param data
	 * @return Cadena de secuencia de escape
	 */
	public String getEscapeSequence(int type, Object[] data);
	
	/**
	 * Genera la cadena del valor nulo para el protocolo
	 * @return Cadena de representación de valor vacío
	 */
	public String getEmptyValue();
	
	/**
	 * Dada una secuencia generada, retorna el identificador de dicha secuencia
	 * @param secuencia
	 * @return Cadena iden
	 */
	public String getSequenceId(String secuencia);
	
	/**
	 * Dado un tipo de comando, indica el tiempo en milisegundos que debe
	 * esperarse a que el dispositivo fiscal lo procese
	 * @param type Tipo de comando - CRFiscalOperations.CMD_XXXXX
	 * @return Tiempo en milisegundos. Devuelve -1 si no debe esperarse nada 
	 * 			
	 */
	public long getTimeToSleep(int type);
	
	/**
	 * Retorna el tipo de respuesta esperada de un comando dado
	 * 
	 * @param cmdType Tipo de comando
	 * @return Tipo de respuesta. Ver constantes CRFPStatus.DATA_XXX
	 */
	public int getTipoRespuestaEsperada(int cmdType); 
	
	/**
	 * Indica que acci&oacute;n tomar en caso de ocurrencia de un error con codigo dado.
	 * El valor de retorno es una de las siguientes constantes definidas en 
	 * CRFPEngine:
	 * <ul>
	 *  	<il>ABORTAR_DOCUMENTO : El documento es abortado al ocurrir este error</il> 
	 * 		<il>REPETIR_DOCUMENTO : El documento es reiniciado desde su primer comando </il>
	 * 		<il>REPETIR_COMANDO : Se repite el comando que ocasionó el error</il> 
	 * 		<il>ESPERAR_APLICACION : Se notifica a la aplicaci&oacute;n, esperando por un reintento </il>
	 * 		<il>REALIZAR_Z : Realiza un Z de la impresora fiscal</il>
	 * 		<il>CERRAR_CF : Realiza un Z de la impresora fiscal</il>
	 * </ul>
	 * @param errCode
	 * @return Constante de accion 
	 */
	public int getActionOnError(int errCode);
	
	/**
	 * Indica el numero de milisegundos que debe esperarse cuando la impresora 
	 * notifica que debe incrementarse el timeout
	 * @return Numero de milisegundos más a esperar por una respuesta 
	 */
	public int getExtraTimetoSleep();
	
	/**
	 * Indica el tiempo que debe esperarse cuando las operaciones se realicen con el slip encendido
	 * @return Numero de milisegundos a agregar en operaciones con slip encendido
	 */
	public int getSlipTimeGap();
	
	/**
	 * Indica el tiempo que se debe esperar para declarar un timeout en la espera
	 * por una respuesta de la impresora
	 * @return Numero de milisegundos a esperar antes de declara timeout
	 */
	public int getEsperaTimeout();
	
	/**
	 * Indica si el comando está implementado en el dispositivo fiscal
	 * @param type Tipo de comando
	 * @return Verdadero si el comando está implementado
	 */
	public boolean implementedCommand(int type);
	
	/**
	 * Establece los valores de la Tabla de Impuestos en el sequenceMap. 
	 * Aplica para GD4SequenceMap
	 * @param tabla Tabla de Impuestos leida al momento de abrir conexión
	 */
	public void setTabla(DataTablaImpuestos tabla);
	
	/**
	 * Establece el método de impuesto configurado en la Impresora Fiscal
	 * Aplica sólo para GD4
	 * @param metodo
	 */
	public void setMetodoImpuesto(Integer metodo);
	
	/**
	 * Método para establecer el tipo de Cálculo para Método Impuesto Exclusivo
	 * @param calculoMetodoExclusivo		0 -> Precios sin Impuesto Incluído
	 * 										1 -> Precios con Impuesto Incluído
	 * Si el método de Impuesto es Incl. siempre debe establecerse como Cálculo de Método Exclusivo = 0x00
	 * Si el método de Impuesto es Exclusivo el cálculo puede variar de acuerdo a los parámetros recibidos
	 * Aplica solo para GD4
	 */
	public void setCalculoMetodoExclusivo(int calculoMetodoExclusivo);

}
