/*
 * $Id: NPF4610SequenceMap.java,v 1.2.2.10 2005/06/22 20:14:15 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerreterÌa EPA C.A. 
 *
 * Proyecto		: CRFiscalPrinterDriver 1.1
 * Paquete		: com.epa.crprinterdriver.impl
 * Programa		: NPF4610SequenceMap.java
 * Creado por	: Programa8
 * Creado en 	: 11-abr-2005 16:24:47
 * (C) Copyright 2005 SuperFerreterÌa EPA C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * InformaciÛn de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo	: $Name:  $
 * Bloqueado por		: $Locker:  $
 * Estado de RevisiÛn	: $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: NPF4610SequenceMap.java,v $
 * Revision 1.2.2.10  2005/06/22 20:14:15  programa8
 * EliminaciÛn de bug en redondeo de cantidades
 *
 * Revision 1.2.2.9  2005/06/21 17:38:22  programa8
 * Ajuste en manejo de errores. Ignorar error de secuencia (Comando repetido)
 *
 * Revision 1.2.2.8  2005/06/14 19:36:10  programa8
 * Ajuste de tiempos para NPF4610
 *
 * Revision 1.2.2.7  2005/06/07 13:35:26  programa8
 * Ajuste de tiempos de espera en NPF4610.
 * Bug de error 31 por repetir innecesariamente un cierre de documento
 *
 * Revision 1.2.2.6  2005/05/17 21:33:47  programa8
 * Ajuste en reiniciado de impresora
 *
 * Revision 1.2.2.5  2005/05/09 14:28:20  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.4  2005/05/09 14:18:59  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.2.2.3  2005/05/06 19:17:58  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.2.2.2  2005/05/06 18:26:44  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.2.2.1  2005/05/05 22:05:38  programa8
 * VersiÛn 2.1:
 * *- PreparaciÛn para funcionamiento con EPSON TMU950PF
 * *- Toma en cuenta la solicitud de incremento en espera por timeout
 * *- Considera independencia de dispositivo en colchones de tiempo
 * *- Considera funciones que pueden estar implementadas en un dispositivo
 * no necesariamente implementadas en otro
 * *- Separa Subsistema base del driver de la fachada usadas por las
 * aplicaciones
 * *- Maneja estatus de activaciÛn del slip. Considera colchones de tiempo
 * para comando enviados al slip.
 * *- Patron de diseÒo Abstract Factory para la selecciÛn de la implementaciÛn
 * de dispositivo a partir de propiedad de la aplicaciÛn
 *
 * Revision 1.2  2005/05/05 12:08:45  programa8
 * VersiÛn 2.0.1 Final
 *
 * Revision 1.1.2.7  2005/04/25 19:51:45  programa8
 * Ajuste en accion por error de max lineas texto fiscal
 *
 * Revision 1.1.2.6  2005/04/19 19:08:28  programa8
 * Driver Fiscal al 19/04/2005 - Primer dÌa de pruebas
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
import com.epa.crprinterdriver.utiles.MathUtil;


/**
 * <p>
 * ImplementaciÛn del Mapa de Secuencias para la impresora
 * IBM GD4
 * </p>
 * <p>
 * <a href="GD4SequenceMap.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Programa8 - ArÌstides Castillo Colmen·rez - $Author: programa8 $
 * @version $Revision: 1.2.2.10 $ - $Date: 2005/06/22 20:14:15 $
 * @since 11-abr-2005
 * 
 */
/*
* En esta clase se realizaron modificaciones referentes a la migraciÛn a java 1.6 por jperez
* SÛlo se comentaron variables sin uso
* Fecha: agosto 2011
*/
public class GD4SequenceMap implements CRFPSequenceMap {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(GD4SequenceMap.class);
	
	
	private static DataTablaImpuestos tablaImpuestos;
	
	/**
	 * Constantes de longitud de Par·metros del Driver.
	 * */
	private static final int MAX_LONGITUD_TEXTO_EA=38;
	private static final int MAX_LONGITUD_TEXTO_EB=86;
	private static final int MAX_LONGITUD_TEXTO_ITEM=20;
	private static final int MAX_LONGITUD_TEXTO_PAGO=10;
	private static final int MAX_LONGITUD_MONTO_ITEM=10;
	private static final int MAX_LONGITUD_MONTO_ITEM_NEG=10;
	private static final int MAX_LONGITUD_MONTO_SUBTOTAL=12;
	private static final int MAX_LONGITUD_LINEA_CHEQUE_HZ=86;
	//private static final int MAX_LONGITUD_LINEA_CHEQUE_VER=47;
	

	/**
	 * Par·metros est·ticos. Valores predeterminados.
	 * */
	
	/**
	 * Comentario para <code>CATEGORIA_IMPUESTO_EXENTO</code>
	 * Categoria de Impuesto Exento.
	 * Cambio del Valor: Depende de la Impresora Fiscal.
	 */
	private static final int CATEGORIA_IMPUESTO_EXENTO=1;
	/**
	 * Comentario para <code>TIPOGRAFIA_ITEM_DEFAULT</code>
	 * Tipografia predeterminada en los Item de Ventas
	 */
	private static final String TIPOGRAFIA_ITEM_DEFAULT="0x00";

	/**
	 * Comentario para <code>TIPO_CODIGO_BARRAS</code>
	 * Valor Actual: 0x22. EAN13, HRI ABAJO, Fuente 15CPI.
	 */
	private static final String TIPO_CODIGO_BARRAS="0x27"; 	
	
	/**
	 * Comentario para <code>direccionImpresionDI</code>
	 * Posibles Valores:  	0 -> Atras
	 * 						1 -> Adelante
	 */
	private int direccionImpresionDI=1;
	/**
	 * Comentario para <code>orientacionImpresionDI</code>
	 * Orientacion de Avance de Linea
	 * Posibles Valores:	2 -> Horizontal
	 * 						3 -> Vertical 
	 */				
	private int orientacionImpresionDI=3;
	
	/**
	 * Comentario para <code>IMPRESION_SLIP</code>
	 * Indica si los reportes no fiscales Son Impresos por la estaciÛn DI / RC
	 */
	private boolean IMPRESION_SLIP=false;
	/**
	 * Comentario para <code>ROLLO_AUDITORIA</code>
	 */
	private boolean ROLLO_AUDITORIA=false;
	/**
	 * Comentario para <code>metodoImpuesto</code>
	 * MÈtodo de Impuesto programado en la Impresora Fiscal
	 */
	private static int metodoImpuesto;
	/**
	 * Comentario para <code>calculoMetodoExclusivo</code>
	 * MËtodo de C·lculo SÛlo aplica para metodoImpuesto = Exclusivo. metodoImpuesto = 0
	 * Predeterminado 0x00 = Precios sin impuesto IncluÌdo 
	 */
	private static String calculoMetodoExclusivo="0x00";
	
	
	/**
	 * @since 11-abr-2005
	 * 
	 */
	//private CRFPStatus statusDriver;
	
    //private Random generadorDeSecuencia = new Random();
    private static final String VACIOS="          ";
    
    /**
	 * @return
	 */
	public static String getCalculoMetodoExclusivo() {
		return calculoMetodoExclusivo;
	}
	
	
	/**
	 * MÈtodo para establecer el tipo de C·lculo para MÈtodo Impuesto Exclusivo
	 * @param calculoMetodoExclusivo		0 -> Precios sin Impuesto IncluÌdo
	 * 										1 -> Precios con Impuesto IncluÌdo
	 * Si el mÈtodo de Impuesto es Incl. siempre debe establecerse como C·lculo de MÈtodo Exclusivo = 0x00
	 * Si el mÈtodo de Impuesto es Exclusivo el c·lculo puede variar de acuerdo a los par·metros recibidos
	 */
	public void setCalculoMetodoExclusivo(int calculoMetodoExclusivo) {
		if (logger.isDebugEnabled()) {
			logger.debug("setCalculoMetodoExclusivo(int) - start");
		}		

		GD4SequenceMap.calculoMetodoExclusivo = "0x00";
		if(GD4SequenceMap.getMetodoImpuesto()==0&&calculoMetodoExclusivo==1){
			GD4SequenceMap.calculoMetodoExclusivo = "0x01";
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("setCalculoMetodoExclusivo(int) - end");
		}		
		
	}
	
	/**
	 * @return
	 */
	public static int getMetodoImpuesto() {
		return metodoImpuesto;
	}

	/**
	 * @param metodoImpuesto
	 */
	public static void setMetodoImpuesto(int metodoImpuesto) {
		if (logger.isDebugEnabled()) {
			logger.debug("setMetodoImpuesto(int) - start");
		}		
		GD4SequenceMap.metodoImpuesto = metodoImpuesto;
		if (logger.isDebugEnabled()) {
			logger.debug("setMetodoImpuesto(int) - end");
		}		
		
	}

	/**
	 * Establece los valores de la Tabla de Impuestos en el sequenceMap. 
	 * Aplica para GD4SequenceMap
	 * @param tabla Tabla de Impuestos leida al momento de abrir conexiÛn
	 */
	public void setTabla(DataTablaImpuestos tabla) {
		if (logger.isDebugEnabled()) {
			logger.debug("setTabla(DataTablaImpuestos) - start");
		}		
		GD4SequenceMap.tablaImpuestos = tabla;
		if (logger.isDebugEnabled()) {
			logger.debug("setTabla(DataTablaImpuestos) - end");
		}		
	}

	/**
	 * Establece los valores del mÈtodo de Impuesto de la Impresora Fiscal 
	 * Aplica para GD4SequenceMap
	 * @param metodo, MÈtodo de impuesto
	 * 	metodo 		0 -> Exclusivo. El impuesto no es IncluÌdo en el Precio.
	 * 				1 -> Inclusivo. Precios con Impuesto IncluÌdo
	 */
	public void setMetodoImpuesto(Integer metodo){
		if (logger.isDebugEnabled()) {
			logger.debug("setMetodoImpuesto(Integer) - start");
		}		
		String aChar = new Character((char)metodo.intValue()).toString();
		GD4SequenceMap.setMetodoImpuesto(Integer.parseInt(aChar));
		if(GD4SequenceMap.getMetodoImpuesto()==1){
			// Si es Inclusivo, se predetermina CalculoMetodoInclusivo=0x00
			GD4SequenceMap.calculoMetodoExclusivo="0x00";
		}
		if (logger.isDebugEnabled()) {
			logger.debug("setMetodoImpuesto(Integer) - end");
		}		
				
	}
	
	/**
	 * Metodo para convertir cadena en formato de texto valido para la Impresora GD4
	 * @param mensaje	Texto / Cadena
	 * @param longitud	Longitud maxima del campo de Texto en la Impresora Fiscal
	 * @param centrado	
	 * @return
	 */
	private String convertirCadena(String mensaje, int longitud, boolean centrado) {
		if (logger.isDebugEnabled()) {
			logger.debug("convertirCadena(String, int, boolean) - start");
		}
		String stringCaracteresAModificar="·ÈÌÛ˙¡…Õ”⁄Ò—∞∫`¥¸‹‡ËÏÚ˘¿»Ã“Ÿ ¬Œ‘€‚ÍÓÙ˚";
		String stringCaracteresCorrectos="aeiouAEIOUnNoo''uUaeiouAEIOUAEIOUaeiou";
		
		for(int i=0; i<mensaje.toCharArray().length; i++) {
			for(int y=0; y<stringCaracteresAModificar.toCharArray().length; y++) {
				if(mensaje.toLowerCase().charAt(i) == stringCaracteresAModificar.charAt(y)) {
					mensaje = mensaje.replace(mensaje.charAt(i), stringCaracteresCorrectos.charAt(y));
				}
			}
		}
		try{
			if (mensaje.length() >longitud) {
				mensaje= mensaje.substring(0,longitud);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		mensaje= mensaje.toUpperCase();
		if(centrado)
			mensaje = centrarCadena(mensaje,longitud);
		String returnString = validaLongitudTexto(mensaje,longitud);
		if (logger.isDebugEnabled()) {
			logger.debug("convertirCadena(String, int , boolean) - end");
		}
		return returnString;
	}
	/**
	 * Metodo para centrar cadenas de caracteres.
	 * @param cadena		Cadena de texto
	 * @param longitud		Longitud maxima de cadena
	 * @return
	 */
	private String centrarCadena(String cadena, int longitud){
		if (logger.isDebugEnabled()) {
			logger.debug("centrarCadena(String, int) - start");
		}
		String blancosx80="                                                                                ";
		int centro=	(longitud/2) - (cadena.length()/2);
		cadena = blancosx80.substring(0,centro)+cadena;
		if (logger.isDebugEnabled()) {
			logger.debug("centrarCadena(String, int) - end");
		}
		return cadena;
	}
	
	/**
	 * Filtra los caracteres especiales que no pueden ser interpretados por la 
	 * impresora fiscal
	 * @param mensaje Cadena de caracteres a convertir.
	 * @return Mensaje convertido
	 */
	@SuppressWarnings("unused")
	private String convertirCadena(String mensaje, int longitud, int x) {
		if (logger.isDebugEnabled()) {
			logger.debug("convertirCadena(String) - start");
		}
		String stringCaracteresAModificar="·ÈÌÛ˙¡…Õ”⁄Ò—∞∫`¥¸‹‡ËÏÚ˘¿»Ã“Ÿ ¬Œ‘€‚ÍÓÙ˚";
		String stringCaracteresCorrectos="aeiouAEIOUnNoo''uUaeiouAEIOUAEIOUaeiou";
		
		for(int i=0; i<mensaje.toCharArray().length; i++) {
			for(int y=0; y<stringCaracteresAModificar.toCharArray().length; y++) {
				if(mensaje.toLowerCase().charAt(i) == stringCaracteresAModificar.charAt(y)) {
					mensaje = mensaje.replace(mensaje.charAt(i), stringCaracteresCorrectos.charAt(y));
				}
			}
		}
		String returnString = validaLongitudTexto(mensaje.toUpperCase(),longitud);
		if (logger.isDebugEnabled()) {
			logger.debug("convertirCadena(String) - end");
		}
		return returnString;
	}
	/**
	 * Convierte los numeros en el formato reconocido por la Unidad Fiscal 
	 * (Ej. 100,50 = 10050)
	 * @param numero Numero a convertir
	 * @return MONTO Numero transformado
	 */
	public static String formatearNumero(double numero){
		if (logger.isDebugEnabled()) {
			logger.debug("formatearNumero(double) - start");
		}
		// Formatear N˙mero xxxxxx,xx
		DecimalFormat decimalFormat = new DecimalFormat("###0.00");
		String MONTO = decimalFormat.format(numero);
		// Eliminar Car·cter Coma. Buscar MÈtodo m·s eficiente
		MONTO= MONTO.substring(0,MONTO.indexOf(","))+MONTO.substring(MONTO.indexOf(",")+1,MONTO.length());

		if (logger.isDebugEnabled()) {
			logger.debug("formatearNumero(double) - end");
		}
		
		return MONTO;
	}

	/**
	 * Complementa las cadenas de texto con espacios en blanco para que sean 
	 * reconocidas por la unidad fiscal
	 * @param texto Texto a complementar
	 * @param lenght  Longitud maxima del campo descripcion
	 * @return texto Texto complementado con espacios en blanco
	 */
	private String completarBlanco(String texto, int lenght){
		if (logger.isDebugEnabled()) {
			logger.debug("completarBlanco(String, int) - start");
		}
		String blancosx80="                                                                                ";
		texto=texto+blancosx80;
		texto=texto.substring(0,lenght);
		if (logger.isDebugEnabled()) {
			logger.debug("completarBlanco(String, int) - end");
		}
		
		return texto;
		
	}
	/**
	 * Valida la longitud de las cadenas de texto a enviar en campos descripcion
	 * si es mayor la longitud las trunca hasta el m·ximo permitido
	 * @param texto
	 * @param longitud
	 * @return descripcion Retorna cadena validada
	 */
	private String validaLongitudTexto(String texto, int longitud){
		if (logger.isDebugEnabled()) {
			logger.debug("validaLongitudTexto(String, int) - start");
		}
		String descripcion=texto;
		try{
			if (descripcion.length() <longitud) {
				descripcion = completarBlanco(descripcion, longitud);
			}else{
				descripcion = descripcion.substring(0,longitud);
			}
		}catch(Exception ex){
			descripcion = completarBlanco(descripcion, longitud);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("validaLongitudTexto(String, int) - end");
		}
		
		return descripcion;
	}
	/**
	 * Convierte una cadena de caracteres en un String Hexadecimal 
	 * (Su representacion en Hexadecimal)
	 * @param cadena Cadena a Convertir
	 * @return cadenaBuilt Cadena convertida a String Hexadecimal
	 */
	public static String stringToHexString(String cadena){
		if (logger.isDebugEnabled()) {
			logger.debug("stringToHexString(String) - start");
		}
		int i=0;
		int z= cadena.length();
		String cadenaBuilt="";
		while(i!=z){
			cadenaBuilt+= completaHexString(Integer.toHexString(cadena.charAt(i)));
			i++;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("stringToHexString(String) - end");
		}
		
		return cadenaBuilt;
	}
	/**
	 * Complementa a cada valor hexadecimal el prefijo 0x<HH>
	 * @param hexa
	 * @return 
	 */
	public static String completaHexString(String hexa){
		if (logger.isDebugEnabled()) {
			logger.debug("completaHexString(String) - start");
		}
		hexa=hexa.toUpperCase();
		try{// Se completa la cadena a Hexadecimal para que el Engine la Reconozca
			if(hexa.length()>1)
			{	hexa="0x"+hexa;
			}else{//Completar con 0x0
				hexa="0x0"+hexa;
			}
		}catch(Exception ex){
			hexa="0x00";
		}
		if (logger.isDebugEnabled()) {
			logger.debug("completaHexString(String) - end");
		}
		return hexa;
		
	}
	
	/**
	 * Llena de ceros "0" a la izquierda los montos para ser entendidos
	 * por la unidad fiscal
	 * @param montoStr
	 * @param LONGITUD_MAXIMA
	 * @return
	 */
	@SuppressWarnings("unused")
	private String validaMonto(String montoStr, int LONGITUD_MAXIMA){
		if (logger.isDebugEnabled()) {
			logger.debug("validaMonto(String, int) - start");
		}
		String ceros="00000000000000000000";
		if(montoStr.length()> LONGITUD_MAXIMA){
			montoStr=montoStr.substring(montoStr.length()-LONGITUD_MAXIMA, montoStr.length());			
		}else{
			montoStr=ceros+montoStr;
			montoStr=montoStr.substring(montoStr.length()-LONGITUD_MAXIMA, montoStr.length());
		}
		if (logger.isDebugEnabled()) {
			logger.debug("validaMonto(String, int) - end");
		}
		
		return montoStr;
	}
	 
	/**
	 * @param byteSrc
	 * @return
	 */
	
	@SuppressWarnings("unused")
	private static String byteToHexString(byte byteSrc){
		if (logger.isDebugEnabled()) {
			logger.debug("byteToHexString(byte) - start");
		}
		String returnStr=Integer.toHexString(byteSrc).toUpperCase();
		try{// Se completa la cadena a Hexadecimal 0x00 para que el Engine la Reconozca
			if(returnStr.length()>1)
			{	returnStr="0x"+returnStr;
			}else{//Completar con 0x0
				returnStr="0x0"+returnStr;
			}
		}catch(Exception ex){
			returnStr="0x00";
		}
		if (logger.isDebugEnabled()) {
			logger.debug("byteToHexString(byte) - end");
		}
		
		return returnStr;
	}
	/**
	 * Metodo para convertir de entero a String hexadecimal
	 * @param source
	 * @return
	 */
	private static String intToHexString(int source){
		if (logger.isDebugEnabled()) {
			logger.debug("intToHexString(int) - start");
		}
		String returnStr=Integer.toHexString(source);
		try{// Se completa la cadena a Hexadecimal 0x00 para que el Engine la Reconozca
			if(returnStr.length()>1)
			{	returnStr="0x"+returnStr;
			}else{//Completar con 0x0
				returnStr="0x0"+returnStr;
			}
		}catch(Exception ex){
			returnStr="0x00";
		}
		if (logger.isDebugEnabled()) {
			logger.debug("intToHexString(int) - end");
		}
		
		return returnStr;
		
	}
	/**
	 * @param montoStr
	 * @param LONGITUD_MAXIMA
	 * @return
	 */
	public String validaLongitudMonto(String montoStr, int LONGITUD_MAXIMA){
		if (logger.isDebugEnabled()) {
			logger.debug("validaLongitudMonto(String, int) - start");
		}
		String cerosx20="000000000000000000";
		if(montoStr.length()> LONGITUD_MAXIMA){
			montoStr=montoStr.substring(montoStr.length()-LONGITUD_MAXIMA, montoStr.length());			
		}else{
			montoStr=cerosx20+montoStr;
			montoStr=montoStr.substring(montoStr.length()-LONGITUD_MAXIMA, montoStr.length());
		}
		if (logger.isDebugEnabled()) {
			logger.debug("validaLongitudMonto(String, int) - end");
		}
		
		return montoStr;
	}
	
	/**
	 * Constructor de la clase
	 * @since 06-may-2004
	 * @param status Objeto de estado del driver fiscal
	 */
	public GD4SequenceMap(CRFPStatus status) {
		super();
		//statusDriver = status;
	}

	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.CRFPSequenceMap#getDynSuffixSequence(java.lang.String)
	 * @param sequence
	 * @return
	 * @since 11-abr-2005
	 */
	public String getDynSuffixSequence(String sequence) {
		return null;
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

		String returnString = "0x1B" + "0x66";
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
		return null;
	}

	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.CRFPSequenceMap#getDataSeparator()
	 * @return
	 * @since 11-abr-2005
	 */
	public String getDataSeparator() {
		return null;
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
				result = "0x01";
				break;
			case CRFiscalPrinterOperations.CMD_ABRIR_CF_DEV:
				result = "0xBA";
				break;
			case CRFiscalPrinterOperations.CMD_ABRIR_DNF:
				result = "0xDD";
				break;
			case CRFiscalPrinterOperations.CMD_ACTIVAR_SLIP:
				result = "0xED";
				setIMPRESION_SLIP(true);
				break;
			case CRFiscalPrinterOperations.CMD_AVANCE_PAPEL:
				result = "0xEC";
				break;
			case CRFiscalPrinterOperations.CMD_CANC_CF:
			case CRFiscalPrinterOperations.CMD_CANC_CFV:
				result = "0x07";
				break;
			case CRFiscalPrinterOperations.CMD_CANC_CFNC:
				result = "0xBE";
				break;
			case CRFiscalPrinterOperations.CMD_CERRAR_CF:
			case CRFiscalPrinterOperations.CMD_CERRAR_CFV:
				result = "0x06" ;
				break;
			case 
				CRFiscalPrinterOperations.CMD_CERRAR_CFNC:
				result = "0xBD" ;
				break;
				
			case CRFiscalPrinterOperations.CMD_CERRAR_DNF:
				result = "0xDE" ;
				// 	Desactiva la impresion por el SLIP
				setIMPRESION_SLIP(false);
				break;
				//TODO: CMD_CODIGO_BARRA: Pendiente por probar. Falla en placa fiscal.
			case CRFiscalPrinterOperations.CMD_SET_CODIGO_BARRA:
				result = "0xC8";
				break;
			case CRFiscalPrinterOperations.CMD_CODIGO_BARRA:
				result = "0xC9";
				break;
			case CRFiscalPrinterOperations.CMD_CORTAR_PAPEL:
				result = "0xEE";
				break;
			case CRFiscalPrinterOperations.CMD_DESACTIVAR_SLIP:
				setIMPRESION_SLIP(false);
				break;
			case	
				CRFiscalPrinterOperations.CMD_DESCUENTO_CF:
				result = "0xD9";
				break;
			case CRFiscalPrinterOperations.CMD_ENCABEZADO:
				result = "0xD7" ;
				break;
			case CRFiscalPrinterOperations.CMD_ENDOSO_CHEQUE:	
				//TODO:VERIFICAR SI APLICA YA QUE HAY QUE CONSTRUIR EL CHEQUE
				//result = "" + '\253';
				break;
			case 
				//REALIZADA A TRAV…S LECTURA ELECTR“NICA DE CONTADORES Y ACUMULADORES 				//result = "0xDB"+"0x00"+"0x01"+"0x00"+"00";
				CRFiscalPrinterOperations.CMD_GET_FECHAHORA_IF:
				result = "0xDB";
				break;
			case 
				//REALIZADA A TRAV…S LECTURA ELECTR“NICA DE CONTADORES Y ACUMULADORES
				CRFiscalPrinterOperations.CMD_GET_SERIAL:
				result = "0xDB";
				break;
			case CRFiscalPrinterOperations.CMD_ITEM_CFV:
			case CRFiscalPrinterOperations.CMD_ITEM_CF:
				result = "0xD0";
				break;
			case CRFiscalPrinterOperations.CMD_ITEM_NEG_CFV:
				result = "0xD1";
				break;
			case CRFiscalPrinterOperations.CMD_ITEM_NEG_CFNC:
				result = "0xBC";
				break;
			case CRFiscalPrinterOperations.CMD_ITEM_CFNC:
				result = "0xBB";
				break;
            //NO APLICA
			case CRFiscalPrinterOperations.CMD_LOGO :
				break;
			case CRFiscalPrinterOperations.CMD_PAGO_CF:
				result = "0xD5";
				break;
			case CRFiscalPrinterOperations.CMD_PIE_PAGINA:
				result = "0x17";
				break;
			case //NO APLICA POR RANGOS
				CRFiscalPrinterOperations.CMD_REP_RANGO_Z:
				result = "0x13";
				break;
			case CRFiscalPrinterOperations.CMD_REPORTE_MF:
				result = "0x15";
				break;
			case CRFiscalPrinterOperations.CMD_REPORTE_X:
				result = "0x14";
				break;
			case CRFiscalPrinterOperations.CMD_REPORTE_Z:
				result = "0x13";
				break;	
			case CRFiscalPrinterOperations.CMD_INFO_TIENDA:
				result = "0x1F";
				break;
			case CRFiscalPrinterOperations.CMD_RESET_PRINTER:
				result = "0xFA";
				break;
			case CRFiscalPrinterOperations.CMD_SET_FECHAHORA_IF:
				result = "0x16" ;
				break;
			case CRFiscalPrinterOperations.CMD_STATUS_IF:
				result = "0xDB";
				break;
				
			case CRFiscalPrinterOperations.CMD_SUBTOTAL_CF:
				result = "0xD4";
				break;
			case CRFiscalPrinterOperations.CMD_TEXTO_CF:
				result = "0xEA" ;
				break;
			case CRFiscalPrinterOperations.CMD_TEXTO_DNF:
				if(!isIMPRESION_SLIP())
					result = "0xEA" ;
				else
					result = "0xEB";
				break;
			case CRFiscalPrinterOperations.CMD_TABLA_IMP:
			case CRFiscalPrinterOperations.CMD_TABLA_IMP2:
			case CRFiscalPrinterOperations.CMD_IMP_METHOD:
				result = "0xDA";
				break;
			case CRFiscalPrinterOperations.CMD_READ_TOTAL:
				result = "0xDA";
				break;
			case CRFiscalPrinterOperations.CMD_LINEA_CHEQUE:
				result = "0xC0";
				break;
			case CRFiscalPrinterOperations.CMD_CERRAR_CHEQUE:
				result = "0xC1";
				break;
			case CRFiscalPrinterOperations.CMD_CANC_CHEQUE:
				result = "0xC2";
				break;
			case CRFiscalPrinterOperations.CMD_EXPL_DOC:
				result = "0xEF";
				break;
			case CRFiscalPrinterOperations.CMD_AVANCE_CHEQUE:
				result = "0xC3";
				break;
			case CRFiscalPrinterOperations.CMD_VOLTEA_DOC:
				result = "0xCB";
				break;
			case CRFiscalPrinterOperations.CMD_SUB_CF:
				result = "0xDB";
				break;
				
			case CRFiscalPrinterOperations.CMD_ELECT_REPORTEZ:
				result = "0xDA";
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
		return null;
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
		switch(errCode){
			case 1: msgResult = "DESBORDAMIENTO - TOTAL DEVUELTO DE LA TRANSACCION EXCEDE MAX. PERMITIDO";
			break;
			case 2: msgResult = "DESBORDAMIENTO - TOTAL DEVOLUCION DE LA TRANSACCION EXCEDE MAX. PERMITIDO";
			break;
			case 3: msgResult = "DESBORDAMIENTO - TOTAL BONO DE LA TRANSACCION EXCEDE MAX. PERMITIDO";
			break;
			case 5: msgResult = "DESBORDAMIENTO - TOTAL VACIOS DE LA TRANSACCION EXCEDE MAX. PERMITIDO";
			break;
			case 8: msgResult = "DESBORDAMIENTO NEGATIVO - TOTAL DE LA TRANSACCION ACTUAL O TOTAL CAT. IMP. ES MENOR AL VALOR MIN. PERMITIDO";
			break;
			case 9:  msgResult = "DESBORDAMIENTO NEGATIVO - TOTAL DEVUELTO DE LA TRANSACCION ES MENOR AL VALOR MIN. PERMITIDO";
			break;
			case 10: msgResult = "DESBORDAMIENTO NEGATIVO - TOTAL DEVUELTO DE LA TRANSACCION ES MENOR AL VALOR MIN. PERMITIDO";
			break;
			case 11: msgResult = "DESBORDAMIENTO NEGATIVO - TOTAL BONOS DE LA TRANSACCION ES MENOR AL VALOR MIN. PERMITIDO";
			break;
			case 13: msgResult = "DESBORDAMIENTO NEGATIVO - TOTAL VACIOS DE LA TRANSACCION ES MENOR AL VALOR MIN. PERMITIDO";
			break;
			case 15: msgResult = "DESBORDAMIENTO - TOTAL PAGOS DE LA TRANSACCION EXCEDE AL VALOR MAX. PERMITIDO";
			break;
			case 16: msgResult = "DESBORDAMIENTO - TOTAL DIARIO O ALGUNA CAT. IMP. DEL TOTAL DE VENTAS DIARIAS EXCEDE AL VALOR MAX. PERMITIDO";
			break;
			case 17: msgResult = "DESBORDAMIENTO - TOTAL DIARIO DEVUELTO EXCEDE AL VALOR MAX. PERMITIDO AL FINALIZAR TRANS.";
			break;
			case 18: msgResult = "DESBORDAMIENTO - TOTAL DIARIO DEVOLUCION EXCEDE AL VALOR MAX. PERMITIDO AL FINALIZAR TRANS.";
			break;
			case 19: msgResult = "DESBORDAMIENTO - TOTAL DIARIO BONOS EXCEDE AL VALOR MAX. PERMITIDO AL FINALIZAR TRANS.";
			break;
			case 21: msgResult = "DESBORDAMIENTO - TOTAL DIARIO VACIOS EXCEDE AL VALOR MAX. PERMITIDO AL FINALIZAR TRANS.";
			break;
			case 24: msgResult = "ERROR - EL MONTO TOTAL NO ES IGUAL A MONTO TOTAL UNIDAD FISCAL";
			break;
			case 25: msgResult = "VIOLACION REGLAS FISCALES - PALABRA TOTAL O EQUIVALENTE NO ESTA PERMITIDA";
			break;
			case 27: msgResult = "DESBORDAMIENTO - DESC. A SUBTOTAL EXCEDE AL VALOR MAX. PERMITIDO";
			break;
			case 29: msgResult = "ERROR - EL TOTAL DE PAGO ES MENOR AL TOTAL DE LA TRANSACCION";
			break;
			case 30: msgResult = "ERROR - OPERACION DE ARTICULO ANULADO O NEGATIVO NO VALIDA";
			break;
			case 31: msgResult = "DESBORDAMIENTO NEGATIVO - SUMA DE LA OPERACION DESC. SUBTOTAL ES MENOR AL VALOR MIN. PERMITIDO";
			break;
			case 33: msgResult = "DESBORDAMIENTO NEGATIVO - TOTAL DIARIO DEVOLUCIONES ES MENOR AL VALOR MIN. PERMITIDO";
			break;
			case 34: msgResult = "DESBORDAMIENTO NEGATIVO - TOTAL DIARIO DEVOLUCION ES MENOR AL VALOR MIN. PERMITIDO";
			break;
			case 35: msgResult = "DESBORDAMIENTO - TOTAL DIARIO BONOS ES MENOR AL VALOR MIN. PERMITIDO";
			break;
			case 37: msgResult = "DESBORDAMIENTO - TOTAL DIARIO VACIOS ES MENOR AL VALOR MIN. PERMITIDO";
			break;
			case 40: msgResult = "ERROR - CAMPO CAT. IMP. NO ESTA EN BLANCO Y CAMPO MONTO ESTA EN BLANCO EN TRANS. VENTAS";
			break;
			case 41: msgResult = "ERROR - TABLA PORC. IMP. ACTUAL NO FUE VERIFICADA";
			break;
			case 42: msgResult = "ERROR - DIFERENCIA EN TABLA PORCENTAJE DE IMPUESTO";
			break;
			case 43: msgResult = "ERROR - TABLA DE PORC. IMP. LLENA";
			break;
			case 44: msgResult = "ERROR - CAT. IMP. ESPECIFICADA NO VALIDA";
			break;
			case 47: msgResult = "ERROR - PUNTO DECIMAL YA FUE MOVIDO";
			break;
			case 48: msgResult = "DESBORDAMIENTO - SUMA OPERACIONES DIARIAS DESC. SUBTOTAL EXCEDE EL VALOR MAX. PERMITIDO";
			break;
			case 50: msgResult = "DESBORDAMIENTO NEGATIVO- SUMA OPERACIONES DIARIAS DESC. SUBTOTAL ES MENOR AL VALOR MIN. PERMITIDO";
			break;
			case 52: msgResult = "ERROR - DESC. SUBTOTALES NO PERMITIDO CUANDO TOTAL TRANSACCION = 0";
			break;
			case 53: msgResult = "ERROR - HORA AL FIJAR FECHA INVALIDA";
			break;
			case 55: msgResult = "ERROR - LONG DEL MENSAJE ES MENOR AL VALOR MIN. REQUERIDO";
			break;
			case 56: msgResult = "DESBORDAMIENTO - TOTAL DIARIO TRANSACCIONES CANCELADO EXCEDE EL VALOR MAX. PERMITIDO";
			break;
			case 57: msgResult = "DESBORDAMIENTO NEGATIVO - TOTAL DIARIO TRANSACCIONES CANCELADO ES MENOR AL VALOR MIN. PERMITIDO";
			break;
			case 58: msgResult = "DESBORDAMIENTO - ACUMULADOR MONTO SIN PAGAR EXCEDE EL VALOR MAX. PERMITIDO";
			break;
			case 59: msgResult = "DESBORDAMIENTO NEGATIVO - ACUMULADOR MONTO SIN PAGAR MENOR AL VALOR MIN. PERMITIDO";
			break;
			case 61: msgResult = "DESBORDAMIENTO - TOTAL TRANSACCION DE UNA CAT. IMP. DEL TOTAL DE LA VENTA EXCEDE EL VALOR MAX. PERMITIDO";
			break;
			case 63: msgResult = "ERROR - INFORMACION TPV/TIENDA NO ESTA CARGADA";
			break;
			case 64: msgResult = "DESBORDAMIENTO - VALOR DEL MONTO RECIBIDO DE APLICACION EXCEDE EL VALOR MAX. PERMITIDO";
			break;
			case 65: msgResult = "ERROR - SOLICITUD Y COMANDO ENVIADO NO FUE RECONOCIDO";
			break;
			case 66: msgResult = "ERROR - SOLICITUD Y COMANDO ENVIADO NO FUE RECONOCIDO";
			break;
			case 67: msgResult = "INFO - COMANDO PROCESADO SATISFACTORIAMENTE";
			break;
			case 68: msgResult = "ERROR - NUMERO DE LINEAS EN IMPRESION DE CHEQUE/PLANILLA EXCEDE EL VALOR MAX. PERMITIDO";
			break;
			case 69: msgResult = "ERROR - SOLICITUD DE IMPRESION DE LINEAS SIMPLES EXCEDE EL VALOR MAX. PERMITIDO DENTRO DEL CF";
			break;
			case 70: msgResult = "ERROR - AVANCE PARCIAL POR PUNTOS FUERA DEL RANGO PERMITIDO";
			break;
			case 71: msgResult = "ERROR MIENTRAS SE IMPRIMIA EN EL RECIBO DEL CLIENTE";
			break;
			case 73: msgResult = "ERROR MIENTRAS SE IMPRIMIA EN LA ESTACI”N DE DOCUMENTOS";
			break;
			case 75: msgResult = "ERROR - IMPRESION EN ESTACION DI NO PERMITIDA DURANTE TRANSACCION DE VENTAS";
			break;
			case 76: msgResult = "ERROR - ESTACION DE IMPRESION INVALIDA";
			break;
			case 78: msgResult = "ERROR MIENTRAS SE IMPRIMIA EN LA ESTACION DE AUDITORIA";
			break;
			case 79: msgResult = "ERROR - AVANCE DE LINEA NO PERMITIDO DURANTE IMPRESION EN ESTACION DI";
			break;
			case 80: msgResult = "ERROR - AVANCE DE LINEA EN ESTACION DI NO PERMITIDO DURANTE TRANSACCION DE VENTAS";
			break;
			case 81: msgResult = "ERROR - TIPOGRAFIA NO VALIDA";
			break;
			case 82: msgResult = "ERROR - SOLICITUD DE IMPRESION DE LINEAS ORDINARIAS FUERA DE RECIBO FISCAL, NC O REPORTE NO FISCAL";
			break;
			case 83: msgResult = "ERROR IRRECUPERABLE AL LEER EL AREA DE ESTADO DE LA MEMORIA FISCAL";
			break;
			case 85: msgResult = "ERROR - CATEGORIA IMP. INVALIDA";
			break;
			case 86: msgResult = "ERROR - CONTRASE—A INVALIDA O NUMEROS MAX DE INTENTOS EXCEDIDO";
			break;
			case 87: msgResult = "ERROR - COMANDO INVALIDO";
			break;
			case 89: msgResult = "ERROR - MEMORIA FISCAL LLENA";
			break;
			case 90: msgResult = "ERROR - CIERRE DE PERIODO DE VENTAS SOLICITADO INVALIDO";
			break;
			case 91: msgResult = "ERROR MIENTRAS SE IMPRIMIA MENSAJE DE INICIO";
			break;
			case 92: msgResult = "ERROR - REGISTRO SOLICITADO NO ENCONTRADO";
			break;
			case 95: msgResult = "ERROR - RANGO DE DIRECCIONES INVALIDO";
			break;
			case 96: msgResult = "ERROR - CAMPO NUMERICO CONTIENE CARACTERES INVALIDOS";
			break;
			case 97: msgResult = "ERROR - ERROR EN RAM";
			break;
			case 98: msgResult = "ERROR - RAM RESTAURADA";
			break;
			case 99: msgResult = "ERROR - EL MAX. REPARACIONES HA SIDO ALCANZADO";
			break;
			case 100: msgResult = "ERROR EN LECTURA DE MEMORIA FISCAL";
			break;
			case 101: msgResult = "ERROR EN ESCRITURA EN MEMORIA FISCAL";
			break;
			case 103: msgResult = "ERROR - DATOS FUERA DE RANGO";
			break;
			case 110: msgResult = "ERROR - LOS DATOS DEL CODIGO DE BARRAS DEBEN SER TERMINADOS NULOS";
			break;
			case 111: msgResult = "ERROR - TAMANO DE CODIGO DE BARRAS INVALIDO";
			break;
			case 112: msgResult = "ERROR - IMPRESORA REINICIADA";
			break;
			case 113: msgResult = "ERROR IRRECUPERABLE DE IMPRESORA";
			break;
			case 114: msgResult = "ERROR - COMUNICACION PERDIDA";
			break;
			case 120: msgResult = "ERROR - TIMEOUT DE RESPUESTA AGOTADO";
			break;
			case 121: msgResult = "ERROR - TIMEOUT DE RESPUESTA AGOTADO";
			break;
			case 125: msgResult = "ERROR - SECUENCIA DE COMANDOS INVALIDA";
			break;
			case 128: msgResult = "ERROR - MEMORIA FISCAL NO SERIALIZADA";
			break;
			case 134: msgResult = "ERROR - ERROR INTERNO DE HARDWARE";
			break;
			case 135: msgResult = "ERROR - SOLICITUD NO PROCESADA. EMITA UN REPORTE X";
			break;
			case 140: msgResult = "ERROR - COMANDO RELACIONADO A RECIBO FISCAL FUE EMITIDO ANTES DE IMPRIMIR EL ENCABEZADO.";
			break;
			case 141: msgResult = "ERROR - SE EMITI” UN COMANDO QUE NO EST¡ PERMITIDO ANTES TOTAL/SUBTOTAL";
			break;
			case 154: msgResult = "ERROR - ERROR AL FINALIZAR NOTA DE CREDITO";
			break;
			case 155: msgResult = "ERROR - ERROR AL CANCELAR NOTA DE CREDITO";
			break;
			case 164: msgResult = "ERROR - SECUENCIA DE ENCENDIDO EN PROGRESO";
			break;
			case 168: msgResult = "ERROR - COMANDO NO RELACIONADO CON UNA TRANSACCION DE VENTAS FUE EMITIDO MIENTRAS UNA TRANSACCION DE VENTAS ESTA EN PROGRESO";
			break;
			case 174: msgResult = "ERROR - ESTE COMANDO NO PUEDE SER EJECUTADO MIENTRAS UN PAGO ESTA EN PROGRESO";
			break;
			case 184: msgResult = "ERROR - COMANDO NO PERMITIDO DURANTE REPORTES NO FISCALES";
			break;
			case 189: msgResult = "ERROR - NO SE PUEDE EJECUTAR COMANDO DURANTE LA IMPRESION DE CHEQUES";
			break;
			case 200: msgResult = "ERROR - VERIFIQUE QUE EL PAPEL ESTA COLOCADO CORRECTAMENTE.";
			break;
			case 201: msgResult = "ERROR - ALGUNA DE LAS CUBIERTAS RD, RC o DI DE LA IMPRESORA ESTA ABIERTA O UN SIN PAPEL OCURRIO EN LA ESTACION DE RECIBOS";
			break;
			case 202: msgResult = "ERROR - EL DOCUMENTO INSERTADO (DI) NO ESTA LISTO";
			break;
			case 203: msgResult = "ERROR - LA CUBIERTA DE LA IMPRESORA ESTA ABIERTA O EL RC NO TIENE PAPEL";
			break;
			case 204: msgResult = "ERROR INTERNO";
			break;
			case 205: msgResult = "ERROR - UN BOTON DE LA IMPRESORA ESTA PRESIONADO";
			break;
			case 206: msgResult = "ERROR - ERROR EN EL PAPEL. ASEGURESE QUE EL PAPEL ESTA COLOCADO CORRECTAMENTE";
			break;
			case 208: msgResult = "ERROR - ERROR INTERNO";
			break;
			case 209: msgResult = "ERROR - LA CUBIERTA DEL RA ESTA ABIERTA. LA SOLICITUD NO FUE PROCESADA";
			break;
			case 210: msgResult = "ERROR - COMPUERTA DE IMPRESION DE DOCUMENTOS DE LA IMPRESORA ESTA ABIERTA";
			break;
			case 214: msgResult = "ERROR - ERROR DE ALIMENTACION DE PAPEL";
			break;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getErrorMessage(int) - end");
		}
		return msgResult;
	}
	/**
	 * Metodo para obtener la secuencia de escape para el formato de las cadenas de caracteres.
	 * @param tipoLetra 0 -> Normal
	 * 					1 -> Resaltado
	 * 					2 -> Centrado
	 * 					3 -> Resaltado y Centrado
	 * 					4 -> Normal 12 CPI
	 * 					5 -> Resaltado 12 CPI
	 * @param rolloAuditoria. Especifica si se imprimira por el rollo de auditoria
	 * @return
	 */
	private int getTipoLetra(int tipoLetra, boolean rolloAuditoria) {
		if (logger.isDebugEnabled()) {
			logger.debug("getTipoLetra(int, boolean) - start");
		}		
		int tipo = 0x00;
		tipo=getTipografiaCode(tipoLetra)	;
		tipo=tipo<<3;
		if(rolloAuditoria&&!isIMPRESION_SLIP()){		//0x40 Rollo de Auditoria. Ver Documento: FVE90N11ESP p·g. 184.
			tipo+=64;
	    }
		if (logger.isDebugEnabled()) {
			logger.debug("getTipoLetra(int, boolean) - end");
		}		
		return tipo;
		}

	/**
	 * @param tipoLetra
	 * @return
	 */
	private static int getTipografiaCode(int tipoLetra){
		if (logger.isDebugEnabled()) {
			logger.debug("getTipografiaCode(int) - start");
		}		
		int tipo=0;
		switch (tipoLetra) {
			case 0:	tipo = 0;	break;
			case 1:	tipo = 4;	break;
			case 2:	tipo = 0;	break;
			case 3: tipo = 4;	break;
			case 4:	tipo = 1;	break;
			case 5:	tipo = 5;	break;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getTipografiaCode(int) - end");
		}		
		return tipo;
	
	}
	@SuppressWarnings("unused")
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
	
	@SuppressWarnings("unused")
	private int stableMultiply(float x, double y) {
		if (logger.isDebugEnabled()) {
			logger.debug("stableMultiply(double, double) - start");
		}

		BigDecimal bx = new BigDecimal(Float.toString(x));
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
	/*
	* En esta funciÛn se realizaron modificaciones referentes a la migraciÛn a java 1.6 por jperez
	* SÛlo se eliminaron variables sin uso
	* Fecha: agosto 2011
	*/
	public String getEscapeSequence(int type, Object[] data) {
		if (logger.isDebugEnabled()) {
			logger.debug("getEscapeSequence(int, Object[]) - start");
		}

		StringBuffer result = new StringBuffer();
		SimpleDateFormat fechaFmted = new SimpleDateFormat("ddMMyyyy");
		result.append(getDataStart());
		result.append(getCommand(type));
		switch (type) {
			case CRFiscalPrinterOperations.CMD_ABRIR_CF:
				//Byte 3. Metodo de Imp. CONSTANTE GLOBAL.				
				result.append(getCalculoMetodoExclusivo());
				//Byte 4 al 7. Reservado Siempre rrrr
				result.append("rrrr"); 
				//Byte 8 al 11. Operador. NO APLICA CR.
				result.append(convertirCadena(VACIOS,4,false));
				//Byte 12 al 15. Terminal. NO APLICA CR.
				result.append(convertirCadena(VACIOS,4,false));
				break;
			case CRFiscalPrinterOperations.CMD_ABRIR_DNF:
				//Byte 3. Estacion
				if(IMPRESION_SLIP){//Impresion estacion DI
						result.append(intToHexString(getOrientacionImpresionDI()));
				}else if (ROLLO_AUDITORIA)
							result.append("0x01");
  					   else
						  	result.append("0x00");
				break;
			case CRFiscalPrinterOperations.CMD_ABRIR_CF_DEV:
				//Byte 3. Metodo de Imp. CONSTANTE GLOBAL
				result.append(getCalculoMetodoExclusivo());
				//Byte 4 al 7. Reservado
				result.append("rrrr");
				//Byte 8 al 11. Operador. N/A
				result.append(convertirCadena(VACIOS,4,false));
				//Byte 12 al 15. Terminal. N/A
				result.append(convertirCadena(VACIOS,4,false));
				break;				
			case CRFiscalPrinterOperations.CMD_CERRAR_CF:
			case CRFiscalPrinterOperations.CMD_CERRAR_CFV:
				//Byte 3. Extension Comando.
				result.append("0x00");
				//Byte 4 al 15. Reservado
				result.append("rrrrrrrrrrrr"); 
				//Byte 16 al 53. Mensaje de Pie 1
				result.append(convertirCadena("", MAX_LONGITUD_TEXTO_EA,false));
				//Byte 54 al 91. Mensaje de Pie 2
				result.append(convertirCadena("", MAX_LONGITUD_TEXTO_EA, false));
				break;
			case CRFiscalPrinterOperations.CMD_CERRAR_CFNC:
				//Byte 3. Reservado
				result.append("0x00");
				//Byte 4 al 41.  Mensaje de Pie 1
				result.append(convertirCadena("", MAX_LONGITUD_TEXTO_EA, false));
				//Byte 42 al 79. Mensaje de Pie 2
				result.append(convertirCadena("", MAX_LONGITUD_TEXTO_EA, false));
				break;
			case CRFiscalPrinterOperations.CMD_CODIGO_BARRA:
				if (data != null && data.length == 1) {
					String datosBarra=(String)data[0]+'\0';
					//Byte 3. Fuente, HRI location, tipo cÛdigo. PREDEFINIDO EAN13 & HRI ABAJO. VER: FVEN90ESP11.PDF PAG. 177
					result.append(TIPO_CODIGO_BARRAS);
					//Byte 4 - n. Codigo
					result.append(datosBarra);
				} else {
					throw new IllegalArgumentException("Data inv·lida");
				}
				break;
			case CRFiscalPrinterOperations.CMD_SET_CODIGO_BARRA:
				//Byte 3. Tipo de Seteo 00 para setear alto y ancho
				result.append("0x00");
				//Byte 4. Ancho de Lineas Valor Actual 2
				result.append("3");
				//Byte 5-7. Alto de Lineas Valor Actual ASCII 080
				result.append("080");
				break;
			case CRFiscalPrinterOperations.CMD_DESCUENTO_CF:
				if(data!=null && data.length==4){
					//Byte 3. Tipo de Operacion. Siempre 0x00
					result.append("0x00");
					//Byte 4 al 23. Descripcion 
					result.append(convertirCadena((String)data[0],MAX_LONGITUD_TEXTO_ITEM, false));
					//Byte 24 al 25. Reservado
					result.append("rr");
					//Byte 26 al 37. Amount
					result.append(validaLongitudMonto(formatearNumero(((Double)data[1]).doubleValue()),MAX_LONGITUD_MONTO_ITEM+2)); //byte 4					
				}else{
					throw new IllegalArgumentException("Data Invalida");
				}
				break;
			case CRFiscalPrinterOperations.CMD_ENCABEZADO:
			case CRFiscalPrinterOperations.CMD_PIE_PAGINA:
				if (data!=null && data.length==2){
					//Byte 3. Linea a Incluir. ToDo: Tipografia.
					result.append(data[0].toString());
					//Byte 4 al 41. Descripcion.
					result.append(convertirCadena((String)data[1], MAX_LONGITUD_TEXTO_EA, false));
				}else{
					throw new IllegalArgumentException("Data Inv·lida");
				}
				break;
			case CRFiscalPrinterOperations.CMD_ITEM_CFNC:
				if (data != null && data.length == 5) {
					//double montoTotal=((Float)data[1]).floatValue()*((Double)data[2]).doubleValue();
					double montoTotal=(MathUtil.cutDouble(((Float)data[1]).floatValue(),2,true)*(((Double)data[2]).doubleValue()));
					montoTotal = MathUtil.cutDouble(montoTotal,2,true);
					//byte 3 Tipografia 
					result.append(TIPOGRAFIA_ITEM_DEFAULT); 	
					//byte 4 al 23 Descripcion
					result.append(convertirCadena((String)data[0],MAX_LONGITUD_TEXTO_ITEM, false));
					if(montoTotal==0){	//Articulo monto Cero. Descripcion m·s de una lÌnea
						result.append(convertirCadena("",MAX_LONGITUD_MONTO_ITEM+2, false));
					}else{		//Articulo Normal.
						//byte 28 al 37 AMOUNT
						result.append(validaLongitudMonto(formatearNumero(montoTotal),MAX_LONGITUD_MONTO_ITEM)); //byte 4
						//byte 38 al 39
						result.append(tablaImpuestos.getPosicionTablaImpuestos(((Double)data[3]).doubleValue(),CATEGORIA_IMPUESTO_EXENTO));	
					}					
/*					//byte 24 al 33 AMOUNT
					result.append(validaLongitudMonto(formatearNumero(montoTotal),MAX_LONGITUD_MONTO_ITEM)); //byte 4
					//byte 34 al 35
					result.append(tablaImpuestos.getPosicionTablaImpuestos(((Double)data[3]).doubleValue(),CATEGORIA_IMPUESTO_EXENTO));*/
				}else{
					throw new IllegalArgumentException("Data Inv·lida");
				}
				break;
			case CRFiscalPrinterOperations.CMD_ITEM_CF:
			case CRFiscalPrinterOperations.CMD_ITEM_CFV:				
				if (data != null && data.length == 5) {
					/*int tasaImp = stableMultiply(((Double)data[3]).doubleValue(), 100);
					int cant = stableMultiply(((Float)data[1]).floatValue(), 1000);
					int mto = stableMultiply(((Double)data[2]).doubleValue(), 100);*/
					//double montoTotal=((Float)data[1]).floatValue()*((Double)data[2]).doubleValue();
					double montoTotal=(MathUtil.cutDouble(((Float)data[1]).floatValue(),2,true)*(((Double)data[2]).doubleValue()));
					montoTotal = MathUtil.cutDouble(montoTotal,2,true);
					//byte 3 Tipografia 
					result.append(TIPOGRAFIA_ITEM_DEFAULT); 	
					//byte 4 al 23 Descripcion
					result.append(convertirCadena((String)data[0],MAX_LONGITUD_TEXTO_ITEM, false));
					//byte 24 al 27 RESERVADO
					result.append("rrrr");
					if(montoTotal==0){	//Articulo monto Cero. Descripcion m·s de una lÌnea
						result.append(convertirCadena("",MAX_LONGITUD_MONTO_ITEM+2, false));
					}else{		//Articulo Normal.
						//byte 28 al 37 AMOUNT
						result.append(validaLongitudMonto(formatearNumero(montoTotal),MAX_LONGITUD_MONTO_ITEM)); //byte 4
						//byte 38 al 39
						result.append(tablaImpuestos.getPosicionTablaImpuestos(((Double)data[3]).doubleValue(),CATEGORIA_IMPUESTO_EXENTO));	
					}
				}else{
					throw new IllegalArgumentException("Data Inv·lida");
				}
				break;
			case CRFiscalPrinterOperations.CMD_ITEM_NEG_CFV:
				if (data != null && data.length == 5) {
					double montoTotal=((Float)data[1]).floatValue()*((Double)data[2]).doubleValue();
					//byte 3 Tipografia 
					result.append(TIPOGRAFIA_ITEM_DEFAULT); 	
					//byte 4 al 23 Descripcion
					result.append(convertirCadena((String)data[0],MAX_LONGITUD_TEXTO_ITEM, false));
					//byte 24 al 27 RESERVADO
					result.append("rrrr");
					//byte 28 al 37 AMOUNT
					result.append(validaLongitudMonto(formatearNumero(montoTotal),MAX_LONGITUD_MONTO_ITEM_NEG)); //byte 4
					//byte 38 al 39
					result.append(tablaImpuestos.getPosicionTablaImpuestos(((Double)data[3]).doubleValue(),CATEGORIA_IMPUESTO_EXENTO));
				}else{
					throw new IllegalArgumentException("Data Inv·lida");
				}
				break;
			case CRFiscalPrinterOperations.CMD_ITEM_NEG_CFNC:
				if (data != null && data.length == 5) {
					double montoTotal=((Float)data[1]).floatValue()*((Double)data[2]).doubleValue();
					//byte 3 Tipografia 
					result.append(TIPOGRAFIA_ITEM_DEFAULT); 	
					//byte 4 al 23 Descripcion
					result.append(convertirCadena((String)data[0],MAX_LONGITUD_TEXTO_ITEM, false));
					//byte 24 al 33 AMOUNT
					result.append(validaLongitudMonto(formatearNumero(montoTotal),MAX_LONGITUD_MONTO_ITEM_NEG)); //byte 4
					//byte 34 al 35
					result.append(tablaImpuestos.getPosicionTablaImpuestos(((Double)data[3]).doubleValue(),CATEGORIA_IMPUESTO_EXENTO));
				}else{
					throw new IllegalArgumentException("Data Inv·lida");
				}
				
				break;
			case CRFiscalPrinterOperations.CMD_PAGO_CF:

				if (data != null && data.length <5 ) {
					int tipog=((Integer)data[2]).intValue();
					int tipop=((Integer)data[3]).intValue();
					//Byte 3. Tipografia <<3, Tipo de pago.
					result.append(intToHexString((tipog<<0x03)+(tipop)));
					//Byte 4 al 13.
					result.append(convertirCadena((String)data[0],MAX_LONGITUD_TEXTO_PAGO, false));
					//Byte 14 al 25. Amount
					result.append(validaLongitudMonto(formatearNumero(((Double)data[1]).doubleValue()),MAX_LONGITUD_MONTO_SUBTOTAL)); 
				} else {
					throw new IllegalArgumentException("Data inv·lida");
				}
				break;
			case CRFiscalPrinterOperations.CMD_REPORTE_MF:
				if (data != null) {
					//Byte 3. Rango. Verificar Rango
					result.append("0x02");
					//Byte 4 al 11.
					result.append(fechaFmted.format((Date)data[0]));
					//Byte 12 al 19
					result.append(fechaFmted.format((Date)data[1]));
					//Byte 20 al 23. ContraseÒa
					result.append(convertirCadena(((String)data[2]).toString(),4,false));
					//Byte 24 al 27. Operador.
					result.append(convertirCadena(((String)data[3]).toString(),4,false));
					//Byte 28 al 31. Terminal.
					result.append(convertirCadena(((String)data[4]).toString(),4,false));
					//Byte 32 al 35. Numero de Tienda.
					result.append(convertirCadena(((String)data[5]).toString(),4,false));
				} else {
					throw new IllegalArgumentException("Data inv·lida");
				}
				break;
			case CRFiscalPrinterOperations.CMD_SET_FECHAHORA_IF:
				if (data != null && data.length == 1) {
					//Byte 3. Reservado.
					result.append("0x00");
					SimpleDateFormat formaterr = new SimpleDateFormat("ddMMyyyyhhmmss");
					Date fechaHora = new Date();
					fechaHora = (Date)data[0];
					result.append(formaterr.format(fechaHora));
				} else {
					throw new IllegalArgumentException("Data inv·lida");
				}
				break;
			case CRFiscalPrinterOperations.CMD_STATUS_IF:
				if (data != null && data.length >= 1) {
					//	Byte 3. Reservado
					result.append("0x00");
					//  Byte 4. Diario
					result.append("0x01");
					//	Byte 5. Reservado
					result.append("0x00");
					char peticion = ((String)data[0]).toString().charAt(0);
					//  Byte 6 - 7. Categoria de Impuestos
					switch(peticion){
						case CRFiscalPrinterOperations.ESTADO_CONTADORES:
							//Sumatoria de Todas las Categorias 
							 result.append("0x000x00");
						break;
						case CRFiscalPrinterOperations.ESTADO_BASE_A_IMP:
							result.append("0x000x02");
							break;
						case CRFiscalPrinterOperations.ESTADO_BASE_B_IMP:
							result.append("0x000x04");
							break;
						case CRFiscalPrinterOperations.ESTADO_BASE_C_IMP:
							result.append("0x000x03");
							break;							
						case CRFiscalPrinterOperations.ESTADO_VTAS_EXENTAS:
							//Categoria de Impuestos Exenta
							 result.append("0x000x0"+CATEGORIA_IMPUESTO_EXENTO);
						break;
					}
				} else {
					throw new IllegalArgumentException("Data inv·lida");
				}
				break;
				
			case CRFiscalPrinterOperations.CMD_ELECT_REPORTEZ:
				if (data != null && data.length >= 1) {
					//	Byte 3. 
					result.append("0x00");
					//  Byte 4. 
					result.append(convertirCadena(((String)data[0]).toString(),7,false));
					
				} else {
					throw new IllegalArgumentException("Data inv·lida");
				}
				break;				
				
			case CRFiscalPrinterOperations.CMD_TEXTO_CF:
				//result.append(getDataSeparator());
				if (data != null && data.length == 3) {
					// Byte 3. Tipo de letra, RA
					data[2] = new Boolean(false);
					result.append(intToHexString(getTipoLetra(((Integer)data[1]).intValue(), ((Boolean)data[2]).booleanValue()))) ;
					// Byte 4 al 41. Texto a imprimir
					result.append(convertirCadena(data[0].toString(),MAX_LONGITUD_TEXTO_EA,false));
				} else {
					throw new IllegalArgumentException("Data inv·lida");
				}
				break;
			case CRFiscalPrinterOperations.CMD_TEXTO_DNF:
				if (data != null && data.length == 2) {
					// Byte 3. Tipo de letra, RA
					if(!isIMPRESION_SLIP())
						result.append(intToHexString(getTipoLetra(((Integer)data[1]).intValue(), false)));
					else
						result.append(intToHexString(getTipoLetra(((Integer)data[1]).intValue(), false)+getOrientacionImpresionDI()+3*getDireccionImpresionDI()));
					// Byte 4 al 41. Texto a imprimir
					boolean centrado=false;
					if(((Integer)data[1]).intValue()>=2){
						centrado=true;
					}
					result.append(
							convertirCadena(data[0].toString(),
									!isIMPRESION_SLIP() ? MAX_LONGITUD_TEXTO_EA:MAX_LONGITUD_TEXTO_EB,
											centrado));
				} else {
					throw new IllegalArgumentException("Data inv·lida");
				}
				
				break;
			case CRFiscalPrinterOperations.CMD_REPORTE_X:
			case CRFiscalPrinterOperations.CMD_REPORTE_Z:
			case CRFiscalPrinterOperations.CMD_INFO_TIENDA:
				//Byte 3. Extension del Comando
				result.append("0x00");
				//Byte 4 al 7. Reservado
				result.append("rrrr");
				//Byte 8 al 11. Asignar Terminal. N/A
				result.append(convertirCadena(VACIOS,4, false));
				//Byte 12 al 15. Asignar Terminal. N/A
				result.append(convertirCadena(VACIOS,4, false));
				break;
			case CRFiscalPrinterOperations.CMD_CORTAR_PAPEL:
				//Byte 3. 
				result.append("0x00");
				break;
			case CRFiscalPrinterOperations.CMD_RESET_PRINTER:
				//Byte 3. Implementar Reset Unidad / Impresora.
				result.append("0x01");
				break;
			case CRFiscalPrinterOperations.CMD_SUB_CF:
				//Byte 3. ExtesiÛn CMD
				result.append("0x00");
				//Byte 4. TransacciÛn
				result.append("0x00");
				//Byte 5. Reservado				
				result.append("0x00");
				//Byte 6. Categoria Impuesto (0 = Todas)				
				result.append("0x00");
				//Byte 7. Categoria Impuesto (0 = Todas)				
				result.append("0x00");
				break;
			case CRFiscalPrinterOperations.CMD_SUBTOTAL_CF:
				//Byte 3. Extension Cmd. Tipografia.
				double montoSubTotal=((Double)data[0]).doubleValue();
				int densidadImpresion = ((Integer)data[1]).intValue();
				int alturaCaracter= ((Integer)data[2]).intValue();
				//Bit 5 Densidad, bit 3 y 4 altura.
				result.append(intToHexString((densidadImpresion<<0x05)+(alturaCaracter<<0x03)));
				//Byte 4 al 15. Amount
				result.append(validaLongitudMonto(formatearNumero(montoSubTotal),MAX_LONGITUD_MONTO_SUBTOTAL)); 
				break;
			
			case CRFiscalPrinterOperations.CMD_GET_SERIAL:
				//Byte 3. Reservado
				result.append("0x00");
				//Byte 4. 
				result.append("0x00");
				//Byte 5.
				result.append("0x00");
				//Byte 6 - 7. Categoria de Impuesto.
				result.append("00");

				break;
			case CRFiscalPrinterOperations.CMD_LINEA_CHEQUE:
				//Byte 3. Tipo, Tipografia a Imprimir Orientacion de Impresion.
				int tipog= ((Integer)data[1]).intValue()<<0x03;
				//Establecer la Direccion de Avance de Linea
				setDireccionImpresionDI(((Integer)data[3]).intValue());
				//Establecer la Orientacion de Planilla Estacion DI
				setOrientacionImpresionDI(((Integer)data[2]).intValue());
				int dirAv= getOrientacionImpresionDI()+3*getDireccionImpresionDI();
				result.append(intToHexString(dirAv+tipog));
				//Byte 4.   
				result.append(convertirCadena(((String)data[0]).toString(),MAX_LONGITUD_LINEA_CHEQUE_HZ,((Boolean)data[4]).booleanValue()));
				break;
			case CRFiscalPrinterOperations.CMD_CERRAR_CHEQUE:
				//Byte 3. Tipo, Tipografia a Imprimir Orientacion de Impresion.
				int tipog1= ((Integer)data[1]).intValue()<<0x03;
				int dirAv1= getOrientacionImpresionDI()+3*getDireccionImpresionDI();
				result.append(intToHexString(dirAv1+tipog1));
				//Byte 4.   
				result.append(convertirCadena(((String)data[0]).toString(),MAX_LONGITUD_LINEA_CHEQUE_HZ,((Boolean)data[4]).booleanValue()));
				break;
				
		    case CRFiscalPrinterOperations.CMD_AVANCE_PAPEL:
		    	int estacion=((Integer)data[0]).intValue()<<0x04;
		    	int cantLineas=((Integer)data[1]).intValue();
		    	//Byte 3. Estacion, Cantidad de Lineas.
		    	result.append(intToHexString(estacion+cantLineas));
		    	break;
		    case CRFiscalPrinterOperations.CMD_EXPL_DOC:
		    	result.append(intToHexString(((Integer)data[0]).intValue()));
		    	break;
		    case CRFiscalPrinterOperations.CMD_AVANCE_CHEQUE:
		    	int direccion= 2+((Integer)data[1]).intValue();
		    	int cantidad = ((Integer)data[0]).intValue();
		    	direccion = direccion <<4;
		    	result.append(intToHexString(direccion+cantidad));
		    	result.append("0x03");
		    	//0 Adelante, 1 Atras
		    	break;
		    case CRFiscalPrinterOperations.CMD_CHEQUE:
		    	break;
			case CRFiscalPrinterOperations.CMD_ENDOSO_CHEQUE:
				break;
			case CRFiscalPrinterOperations.CMD_REP_RANGO_Z:
				break;
			case CRFiscalPrinterOperations.CMD_TABLA_IMP:
				result.append("0x020x000x000x000x00");
				break;
			case CRFiscalPrinterOperations.CMD_TABLA_IMP2:
				result.append("0x030x000x000x000x00");
				break;
			case CRFiscalPrinterOperations.CMD_IMP_METHOD:
				result.append("0x0E0x000x000x000x00");
				break;				
			case CRFiscalPrinterOperations.CMD_READ_TOTAL:
				result.append("0x010x000x000x000x01");
				break;
			default: 
				result.append("0x00");
				break;
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

		String returnString = "";
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
		return null;
	}
	int getCommandType (int procedimientoFiscalEnProgreso, int pasoEnProcedimientoActual){
		int result= -1;
		if (logger.isDebugEnabled()) {
			logger.debug("getCommandType(int, int) - start");
		}
		switch(procedimientoFiscalEnProgreso){
			case 0x00:
				
				break;
			case 0x01:	//Transaccion de Ventas en progreso.
					switch(pasoEnProcedimientoActual){
						case 128:	result=CRFiscalPrinterOperations.CMD_ABRIR_CF;
						break;
						case 192:	result=CRFiscalPrinterOperations.CMD_ITEM_CFV;
						break;
						case 224:	result=CRFiscalPrinterOperations.CMD_SUBTOTAL_CF;
						break;
						case 240:	result=CRFiscalPrinterOperations.CMD_PAGO_CF;
						break;
						case 248: 	result=CRFiscalPrinterOperations.CMD_CERRAR_CFV;
						break;
						//TODO: Faltan pruebas
						case 1<<2: 	result=CRFiscalPrinterOperations.CMD_CANC_CF;
						break;
						case 242: 	result=CRFiscalPrinterOperations.CMD_CHEQUE;
						break;
					}
					break;
			case 9:		//Cheque o Planilla de Credito en progreso.
				result=CRFiscalPrinterOperations.CMD_LINEA_CHEQUE;
				break;
			case 0x0A:	//Nota de crÈdito en progreso.
				switch(pasoEnProcedimientoActual){
				case 128:	result=CRFiscalPrinterOperations.CMD_ABRIR_CF_DEV;
				break;
				case 192:	result=CRFiscalPrinterOperations.CMD_ITEM_CFNC;
				break;
				case 248: 	result=CRFiscalPrinterOperations.CMD_CERRAR_CFNC;
				break;
			}
			break;
			case 0x05:	case 0x06:	case 0x07:	case 0x08:
				result = CRFiscalPrinterOperations.CMD_TEXTO_DNF;
				break;

		}
		if (logger.isDebugEnabled()) {
			logger.debug("getCommandType(int, int) - end");
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
			case CRFiscalPrinterOperations.CMD_RESET_PRINTER:  /*CMD_RESET_PRINTER:*/
				valor= 500;				
				break;
			case CRFiscalPrinterOperations.CMD_CORTAR_PAPEL : 
			 /*   result = "CMD_CORTAR_PAPEL";*/
				valor=500;
			    break;
			/*default:
				valor = -1;
				break;*/
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
		case CRFiscalPrinterOperations.CMD_DESACTIVAR_SLIP:
			result = CRFPStatus.NO_ENVIAR_COMANDO;
			break;
		case CRFiscalPrinterOperations.CMD_AVANCE_PAPEL:
			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_CANC_CF:
		case CRFiscalPrinterOperations.CMD_CANC_CFV:
		case CRFiscalPrinterOperations.CMD_CANC_CFNC:
		case CRFiscalPrinterOperations.CMD_CANC_CHEQUE:
			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_CERRAR_CF:
		case CRFiscalPrinterOperations.CMD_CERRAR_CFV:
		case CRFiscalPrinterOperations.CMD_CERRAR_CFNC:
		case CRFiscalPrinterOperations.CMD_CERRAR_CHEQUE:
			//result = CRFPStatus.DATA_CIERRE_CF;
			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_CERRAR_DNF:
			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_CHEQUE:
		case CRFiscalPrinterOperations.CMD_LINEA_CHEQUE:

			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_CODIGO_BARRA:
			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_CORTAR_PAPEL:
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
		case CRFiscalPrinterOperations.CMD_ITEM_CFNC:
		case CRFiscalPrinterOperations.CMD_ITEM_CFV:
		case CRFiscalPrinterOperations.CMD_ITEM_NEG_CFNC:
		case CRFiscalPrinterOperations.CMD_ITEM_NEG_CFV:
			//result = CRFPStatus.DATA_ITEM;
			result = CRFPStatus.DATA_NONE;
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
			//	result = CRFPStatus.DATA_REP_Z;
			break;
		case CRFiscalPrinterOperations.CMD_REPORTE_Z:
			//	result = CRFPStatus.DATA_REP_Z;
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
		case CRFiscalPrinterOperations.CMD_IMP_METHOD:			
			result = CRFPStatus.DATA_IMP_METHOD;
			break;
		case CRFiscalPrinterOperations.CMD_TABLA_IMP:
		case CRFiscalPrinterOperations.CMD_TABLA_IMP2:
			result = CRFPStatus.DATA_TABLA_IMP;
			break;
		case CRFiscalPrinterOperations.CMD_READ_TOTAL:
			result = CRFPStatus.DATA_TOTAL_DIARIO;
			break;
		case CRFiscalPrinterOperations.CMD_SUB_CF:
			result = CRFPStatus.DATA_ITEM;
			break;
		case CRFiscalPrinterOperations.CMD_SUBTOTAL_CF:
			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_TEXTO_CF:
			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_TEXTO_DNF:
			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_ABRIR_GAVETA:
			result = CRFPStatus.DATA_NONE;
			break;
		case CRFiscalPrinterOperations.CMD_ELECT_REPORTEZ:
			result = CRFiscalPrinterOperations.CMD_ELECT_REPORTEZ;
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
		switch(errCode){
			case 16:		case 17:		case 18:
			case 19:		case 21:		case 33:
			case 34:		case 35:		case 37:
			case 48:		case 50:		case 56:
			case 57:
				result = CRFPEngine.CANCELAR_TRANS_EMITIRZ;
				break;
			case 135: 
				result = CRFPEngine.REALIZAR_X;
				break;
			case 150:		case 154:	
			case 155:		
				result = CRFPEngine.CANCELAR_DEVOLUCION;
				break;
			case 167:
				result = CRFPEngine.REALIZAR_Z;
				break;
			case 1:			case 2:			case 3:
			case 5:			case 8: 		case 9:			
			case 10: 		case 11:		case 13:		
			case 24:		case 27:		case 29:		
			case 30:		case 31:		case 52:		
			case 61:		case 168:		case 36:
			//case 140:
				result = CRFPEngine.CANCELAR_TRANS_VENTAS;
				break;
			//TODO: Manejo de Errores de Programa AplicaciÛn, Configuracion de Impresora, y fallas tÈcnicas
			case 15:	case 40:	
			case 44:				//Categoria de Impuestos Invalida
			case 55:	case 58:	case 59:	case 64:
			case 65:	case 66:	case 68:	case 69:
			case 70:	case 75:	case 76:	case 79:
			case 80:	case 81:	case 82:	case 85:
			case 87:	case 90:	case 92:	case 95:
			case 96:	case 103:	case 110:	case 111:
			case 125:	case 141:	/*case 184:*/	case 189:
			case 140:		
				break;
			//Omitir error
			case 25:	case 47:
			case 112: 	case 164:
				result = CRFPEngine.IGNORAR_ERROR;
				break;
			case 41:	case 42: 	//Tabla Impuestos sin cargar
				break;
			case 43:				//Error en Tabla Impuestos (Reemplazo de Unidad Fiscal)
			case 53:	case 63:	case 71: 	case 73:
			case 78:	case 83:	case 86:	case 89:
			case 97:	case 98:	case 99: 	case 100:
			case 101:	case 113:	case 114:	case 128:
			case 134:	case 208:
			//case 204:	WDIAZ:Se comento porque no esta realizando ninguna funcion y la Impresora devuelve 204 cuando se abre la tapa
				break;
			case 91:	case 120:	case 121:
				result = CRFPEngine.REINICIAR_IMPRESORA;
				break;
			//TODO: Manejo de error: Impresora no est· lista Falla Papel
			case 200:	case 206:	case 214:
			case 201:				//cubierta de La impresora abierta
			case 202:				//cubierta de La impresora abierta				
			case 205:				//Boton de Impresora Presionado
			case 204: // WDIAZ: Se agregÛ aquÌ para que retorme Esperar_aplicacion y no se tenga que reiniciar la CR
			case 184: //prueba	
				result = CRFPEngine.ESPERAR_APLICACION;
				break;
			case 203:	case 209: 	case 210: 	//cubierta de La impresora abierta			
				result = CRFPEngine.ESPERAR_APLICACION;
				break;
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
		return 0;
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
		return true;
	}
	/**
	 * 
	 * @return
	 */
	public int getDireccionImpresionDI() {
		return direccionImpresionDI;
	}
	/**
	 * @param direccionImpresionDI
	 */
	public void setDireccionImpresionDI(int direccionImpresionDI) {
		this.direccionImpresionDI = direccionImpresionDI;
	}
	/**
	 * @return
	 */
	public int getOrientacionImpresionDI() {
		return orientacionImpresionDI;
	}
	/**
	 * @param orientacionImpresionDI
	 */
	public void setOrientacionImpresionDI(int orientacionImpresionDI) {
		this.orientacionImpresionDI = orientacionImpresionDI;
	}
	/**
	 * @return
	 */
	public boolean isIMPRESION_SLIP() {
		return IMPRESION_SLIP;
	}
	/**
	 * @param impresion_slip
	 */
	public void setIMPRESION_SLIP(boolean impresion_slip) {
		IMPRESION_SLIP = impresion_slip;
	}
	/**
	 * @return
	 */
	public boolean isROLLO_AUDITORIA() {
		return ROLLO_AUDITORIA;
	}
	/**
	 * @param rollo_auditoria
	 */
	public void setROLLO_AUDITORIA(boolean rollo_auditoria) {
		ROLLO_AUDITORIA = rollo_auditoria;
	}	
}
