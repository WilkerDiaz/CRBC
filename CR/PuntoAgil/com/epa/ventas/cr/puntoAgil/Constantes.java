/*
 * $Id: Constantes.java,v 1.12 2007/05/07 15:36:47 programa8 Exp $
 * ===========================================================================
 * Proyecto : PuntoAgil Paquete : com.epa.ventas.cr.puntoAgil Programa :
 * Constantes.java Creado por : programa4 Creado en : 15/06/2006 12:56:14 PM (C)
 * Copyright 2006 Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Información de Repositorio:
 * ---------------------------------------------------------------------------
 * Rama de Desarrollo : $Name:  $ Bloqueado por : $Locker:  $ Estado de Revisión :
 * $State: Exp $
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ---------------------------------------------------------------------------
 * $Log: Constantes.java,v $
 * Revision 1.12  2007/05/07 15:36:47  programa8
 * Version estable Punto Agil 2007-05-07
 *
 * Revision 1.11  2007/04/25 18:46:04  programa8
 * Version Estable Pto Agil, controlando cargos dos dobles , una sola instancia a datosExternos , control fuera de linea
 *
 * Revision 1.10  2006/11/09 17:53:09  programa4
 * Se agrega como parametro que se indique el banco para chequear que planes de lealtad se pueden consultar
 *
 * Revision 1.9  2006/09/28 19:08:29  programa4
 * Actualizacion por requerimientos credicard
 * Se cargan datos de programas de lealtad segun banco seleccionado
 * Modificados mensajes de error para hacerlos mas legibles
 * Verificados titulos para indicar version del punto agil
 *
 * Revision 1.8  2006/07/25 22:49:58  programa4
 * Agregado tipo de cuenta como requerimiento de consulta de puntos
 *
 * Revision 1.7  2006/07/21 18:44:17  programa4
 * Corregido bug que al haber excepcion en el guardado de base de datos no imprimia recibo
 * Movido textos a constantes
 * revisado toString de DatosOperacionPuntoAgil
 * formateo de codigo
 * Revision 1.6 2006/07/17 13:33:47 programa4
 * Corregido bug en enmascaramiento de tarjetas Revision 1.5 2006/07/13 16:07:29
 * programa4 Correcion de mensaje de "Punto Agil" a "El Punto Agil" Manejo de
 * autorizacion si no se utiliza punto agil No se solicita autorizacion si hubo
 * error VA (Falla de comunicacion con merchant), o se trata de cheque Asignados
 * mensajes a mostrar mientras se comunica con pinpad Revision 1.4 2006/07/05
 * 15:25:43 programa4 Agregado soporte a provimillas y actualizado anulacion
 * Revision 1.3 2006/06/27 15:51:58 programa4 Refactorizadas consultas Agregados
 * metodos sincronizacion Modificado manejo de excepciones Revision 1.2
 * 2006/06/26 16:06:06 programa4 Agregados javadoc de constantes, elevadas
 * algunas a publicas Revision 1.1 2006/06/16 21:30:38 programa4 Reubicadas
 * constantes a esta clase
 * ===========================================================================
 */
/**
 * Clase Constantes
 *
 * @author programa4 - $Author: programa8 $
 * @version $Revision: 1.12 $ - $Date: 2007/05/07 15:36:47 $
 * @since 15/06/2006
 */
package com.epa.ventas.cr.puntoAgil;

import ve.com.megasoft.VPos;

/**
 * @author programa4 - $Author: programa8 $
 * @version $Revision: 1.12 $ - $Date: 2007/05/07 15:36:47 $
 * @since 15/06/2006
 */
public class Constantes {

	/** Version de extension EPA */
	public final static String VERSION = "1.0.1";

	/** Version del Componente y extension EPA */
	public static final String TITULO_VERSION = " EL PUNTO AGIL - " + VERSION
			+ " / " + VPos.getVersion();

	/** Tipo proceso venta: 1 */
	public final static int TIPO_PROCESO_VENTA_INT = 1;

	/** Tipo proceso abono: 2 */
	public final static int TIPO_PROCESO_ABONO_INT = 2;

	/** Tipo proceso pago tdc: 3 */
	public final static int TIPO_PROCESO_PAGO_TC_EPA_INT = 3;

	/** Tipo proceso pseudo transaccion: 4 */
	public final static int TIPO_PROCESO_PSEUDO_TRANSACCION_PUNTO_AGIL_INT = 4;

	/** Tipo proceso venta: 1 */
	public final static Integer TIPO_PROCESO_VENTA = new Integer(
		TIPO_PROCESO_VENTA_INT);

	/** Tipo proceso abono: 2 */
	public final static Integer TIPO_PROCESO_ABONO = new Integer(
		TIPO_PROCESO_ABONO_INT);

	/** Tipo proceso pago tdc: 3 */
	public final static Integer TIPO_PROCESO_PAGO_TC_EPA = new Integer(
		TIPO_PROCESO_PAGO_TC_EPA_INT);

	/** Tipo proceso pseudo transaccion: 4 */
	public final static Integer TIPO_PROCESO_PSEUDO_TRANSACCION_PUNTO_AGIL = new Integer(
		TIPO_PROCESO_PSEUDO_TRANSACCION_PUNTO_AGIL_INT);

	/** Tipo operacion es de pago */
	public static final Integer TIPO_OPERACION_PAGO = new Integer(1);

	/** Tipo operacion anulacion */
	public static final Integer TIPO_OPERACION_ANULACION = new Integer(2);

	/** Tipo operacion consulta de puntos */
	public static final Integer TIPO_OPERACION_CONSULTA = new Integer(3);

	/** Operacion Vigente */
	public static final String OPERACION_VIGENTE = "V";

	/** Operacion Anulada */
	public static final String OPERACION_ANULADA = "A";

	/** Registro creado, aun no insertado en servidor */
	public static final String REGISTRO_ACTUALIZADO_CREADO = "C";

	/** Registro actualizado, ya insertado en servidor */
	public static final String REGISTRO_ACTUALIZADO_SI = "S";

	/** Registro no ha sido actualizado, hay que actualizar en servidor */
	public static final String REGISTRO_ACTUALIZADO_NO = "N";
	
	/** Constante de limite inferior y superior de numero de tarjeta para clientes juridicos*/
	public static final int TCEPA_NUMER0 = 50;
	
	public static final String TCEPA_PLAN= "Rotativo";
	
	
	//PARA CONSULTAS DE PUNTOS SE ASUME QUE LA LONGITUD DEL COD. SEGURIDAD ES 3
	/** Forma de pago utilizada para consultar puntos con credito */
	public static final DatosFormaDePagoPuntoAgil FDP_CONSULTA_PUNTOS_CREDITO = new DatosFormaDePagoPuntoAgil(
		null, 0, null, null, true, true, false, false, false, true, false,
		false, 0, 0, 0, false, false, Constantes.TARJETA_CREDITO, true, true,
		true, false, true, false, 3);

	/** Forma de pago utilizada para consultar puntos con debito */
	public static final DatosFormaDePagoPuntoAgil FDP_CONSULTA_PUNTOS_DEBITO = new DatosFormaDePagoPuntoAgil(
		null, 0, null, null, true, true, false, false, false, true, false,
		false, 0, 0, 0, false, false, Constantes.TARJETA_DEBITO, true, true,
		true, false, true, true, 3);

	/** Forma de pago utilizada para realizar anulaciones */
	public static final DatosFormaDePagoPuntoAgil FDP_ANULACIONES = new DatosFormaDePagoPuntoAgil(
		null, 0, null, null, false, true, false, false, false, false, false,
		false, 0, 0, 0, false, false, null, true, false, true, false, false,
		false, 0);

	/**
	 * Ruta donde se encuentran los archivos de configuracion del VPos
	 */
	public static final String RUTA_CONF_VPOS = "/opt/CR/vPos/conf/";
	//public static final String RUTA_CONF_VPOS = "C:\\vPos\\conf\\";
	
	/** Valor tipo tarjeta credito */
	static final String TARJETA_CREDITO = "C";

	/** Valor tipo tarjeta debito */
	static final String TARJETA_DEBITO = "D";

	/** Valor tipo tarjeta privada (p.e. EPA) */
	static final String TARJETA_PRIVADA = "P";

	/** Archivo de Ultimo Voucher */
	public static final String ULTIMO_VOUCHER = "/opt/CR/vPos/voucher/vpos.txt";
	//public static final String ULTIMO_VOUCHER = "C:\\vPos\\voucher\\vpos.txt";
	
	/** Codigo de Respuesta de transaccion aprobada */
	public static final String COD_RESPUESTA_APROBADO = "00";

	/** Valor de cuenta especial correspondiente a provimillas */
	public static final String CTA_ESPECIAL_PROVIMILLAS = "1";

	/**
	 * Codigo de respuesta por falla de comunicacion con merchant
	 */
	public static final String COD_RESPUESTA_FALLA_COMUNICACION = "VA";

	/**
	 * Mensaje a mostrar cuando se establece comunicación con el merchant
	 */
	public static final String PROCESANDO_CON_EL_BANCO = "PROCESANDO CON EL BANCO";

	/**
	 * Mensaje a mostrar cuando se hace lectura de la tarjeta.
	 */
	public static final String OBSERVE_MENSAJE_EN_EL_PINPAD = "OBSERVE MENSAJE EN EL PINPAD";

	/** Constante integer de valor 0, para hacer comparaciones */
	public static final Integer INTEGER_ZERO = new Integer(0);
	
	
	
	/**
	 * Necesarias para vpos universal
	 */
	//CREDITO

	public static final String TARJETA_VISA_ELECTRON_PIN = "T";
	public static final String TARJETA_VISA_ELECTRON_NO_PIN = "V";
	
	
	//CESTATICKET
	public static final String TARJETA_CESTA_TICKET = "M";
	public static final String TARJETA_SODEXHO = "S";
	
	//BONO REGALO ELECTRONICO
	public static final String  TARJETA_BONO_REGALO = "P";

}
