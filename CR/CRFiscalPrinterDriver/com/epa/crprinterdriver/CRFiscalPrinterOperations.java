/*
 * $Id: CRFiscalPrinterOperations.java,v 1.14.2.6 2005/06/27 16:23:55 programa8 Exp $
 * ===========================================================================
 * Material Propiedad SuperFerretería EPA C.A. 
 *
 * Proyecto		: CRFiscalPrinterDriver 1.1
 * Paquete		: com.epa.crprinterdriver
 * Programa		: CRFiscalPrinterOperations.java
 * Creado por	: Programa8
 * Creado en 	: 11-abr-2005 15:22:29
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
 * $Log: CRFiscalPrinterOperations.java,v $
 * Revision 1.14.2.6  2005/06/27 16:23:55  programa8
 * Eliminación de bug por bloqueo entre GUI  e impresora fiscal
 *
 * Revision 1.14.2.5  2005/06/06 19:05:36  programa8
 * Eliminación de bug al imprimir endoso de cheque
 *
 * Revision 1.14.2.4  2005/05/09 14:28:23  programa8
 * Ajuste de Javadoc
 *
 * Revision 1.14.2.3  2005/05/06 19:18:02  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.14.2.2  2005/05/06 18:17:01  programa8
 * Ajustes en javadoc del driver
 *
 * Revision 1.14.2.1  2005/05/05 22:05:41  programa8
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
 * Revision 1.14  2005/05/05 12:08:46  programa8
 * Versión 2.0.1 Final
 *
 * Revision 1.13.2.9  2005/04/25 19:49:52  programa8
 * Resistencia al compromiso de documentos inexistentes
 * Posibilidad de cancelar un comprobante en un documento no creado
 *
 * Revision 1.13.2.8  2005/04/21 13:47:58  programa8
 * Driver Fiscal 2.0 RC1 - Al 21/04/2004
 *
 * Revision 1.13.2.7  2005/04/19 19:08:32  programa8
 * Driver Fiscal al 19/04/2005 - Primer día de pruebas
 *
 * Revision 1.13.2.6  2005/04/18 16:39:24  programa8
 * Driver Fiscal al 18/04/2005 - 12m
 *
 * Revision 1.13.2.5  2005/04/13 21:57:04  programa8
 * Driver fiscal al 13/04/2005
 *
 * Revision 1.13.2.4  2005/04/12 20:59:17  programa8
 * Driver fiscal al 12/04/05
 *
 * Revision 1.13.2.3  2005/04/12 19:22:06  programa8
 * Agregados comandos para avance de papel y desactivacion de proximo corte-
 *
 * Revision 1.13.2.2  2005/04/11 20:59:11  programa8
 * CRFiscalPrinterDriver 2.0 - Analisis Preliminar
 *
 * ===========================================================================
 */
package com.epa.crprinterdriver;

import java.sql.Time;
 
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import com.epa.crprinterdriver.data.DataSubtotal;
import com.epa.crprinterdriver.data.DataStatusIF;
import com.epa.crprinterdriver.event.FiscalPrinterListener;
import com.epa.crserialinterface.Parameters;


/**
 * 
 * Clase Operaciones de Impresora Fiscal. Este es el objeto que instancian las
 * aplicaciones que requieren los servicios del driver fiscal. Funciona como una 
 * fachada del subsistema de impresión fiscal, así como medio de encapsulamiento
 * de los diversos componentes de este subsistema.
 * 
 * Agregar una nueva funcionalidad de aplicación del driver fiscal requiere 
 * una extensión / actualización de esta clase.
 * 
 * Esta fachada esta diseñada para un esquema de post impresión, almacenando los
 * comandos en un buffer previo, y luego enviando asincrónicamente estos
 * comandos al dispositivo fiscal. El estatus del documento enviado es recibido
 * a trav&eacute;s de los eventos de impresora fiscal
 * 
 * <p>
 * <a href="CRFiscalPrinterOperations.java.html"><i>View Source</i></a> 
 * </p>
 * 
 * @author Programa8 - Gabriel Martinelli - Arístides Castillo Colmenárez - $Author: programa8 $
 * @version $Revision: 1.14.2.6 $ - $Date: 2005/06/27 16:23:55 $
 * @since 11-abr-2005
 * 
 */
public class CRFiscalPrinterOperations {
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(CRFiscalPrinterOperations.class);

	/**
	 * Constante de comando fiscal Abrir Comprobante Fiscal
	 */
	public static final int CMD_ABRIR_CF = 0;
	/**
	 * Constante de comando fiscal Imprimir Texto Fiscal
	 */
	public static final int CMD_TEXTO_CF = 1;
	/**
	 * Constante de comando fiscal Item Comprobante Fiscal
	 */
	public static final int CMD_ITEM_CF = 2;
	/**
	 * Constante de comando fiscal Subtotal en Comprobante Fiscal 
	 */
	public static final int CMD_SUB_CF = 3;
	/**
	 * Constante de comando fiscal Descuento en Comprobante Fiscal 
	 */
	public static final int CMD_DESCUENTO_CF = 4;
	/**
	 * Constante de comando fiscal Cerrar Comprobante Fiscal 
	 */
	public static final int CMD_CERRAR_CF = 5;
	/**
	 * Constante de comando fiscal Emitir Reporte X 
	 */
	public static final int CMD_REPORTE_X = 6;
	/**
	 * Constante de comando fiscal Emitir Reporte Z 
	 */
	public static final int CMD_REPORTE_Z = 7;
	/**
	 * Constante de comando fiscal Abrir Documento no Fiscal 
	 */
	public static final int CMD_ABRIR_DNF = 8;	
	/**
	 * Constante de comando fiscal Texto No Fiscal 
	 */
	public static final int CMD_TEXTO_DNF = 9;
	/**
	 * Constante de comando fiscal Cerrar Documento no Fiscal 
	 */
	public static final int CMD_CERRAR_DNF = 10;
	/**
	 * Constante de comando fiscal Abrir comprobante fiscal de Devolucion 
	 */
	public static final int CMD_ABRIR_CF_DEV = 11;
	/**
	 * Constante de comando fiscal Activar Slip 
	 */
	public static final int CMD_ACTIVAR_SLIP = 12;
	/**
	 * Constante de comando fiscal Desactivar Slip 
	 */
	public static final int CMD_DESACTIVAR_SLIP = 13;
	/**
	 * Constante de comando fiscal Cancelar Documento Fiscal 
	 */
	public static final int CMD_CANC_CF = 14;
	/**
	 * Constante de comando fiscal Cortar Papel 
	 */
	public static final int CMD_CORTAR_PAPEL = 15;
	/**
	 * Constante de comando fiscal Imprimir Cheque 
	 */
	public static final int CMD_CHEQUE = 16;
	/**
	 * Constante de comando fiscal Imprimir Endoso de Cheque 
	 */
	public static final int CMD_ENDOSO_CHEQUE = 17;
	/**
	 * Constante de comando fiscal Pago en Comprobante Fiscal 
	 */
	public static final int CMD_PAGO_CF = 18;
	/**
	 * Constante de comando fiscal Reinicializar Impresora 
	 */
	public static final int CMD_RESET_PRINTER = 19;
	/**
	 * Constante de comando fiscal Solicitud de Estado de Impresora Fiscal 
	 */
	public static final int CMD_STATUS_IF = 20;
	/**
	 * Constante de comando fiscal Impresión de Codigo de Barra 
	 */
	public static final int CMD_CODIGO_BARRA = 21;
	/**
	 * Constante de comando fiscal Obtener Fecha y Hora de Impresora Fiscal 
	 */
	public static final int CMD_GET_FECHAHORA_IF = 22;
	/**
	 * Constante de comando fiscal Establecer Fecha y Hora de Impresora Fiscal 
	 */
	public static final int CMD_SET_FECHAHORA_IF = 23;
	/**
	 * Constante de comando fiscal Reporte de Memoria Fiscal 
	 */
	public static final int CMD_REPORTE_MF = 24;
	/**
	 * Constante de comando fiscal Resumen de Reporte Z por Rangos 
	 */
	public static final int CMD_REP_RANGO_Z = 25;
	/**
	 * Constante de comando fiscal Establecer encabezado de Comprobante Fiscal  
	 */
	public static final int CMD_ENCABEZADO = 26;
	/**
	 * Constante de comando fiscal Establecer pie de página fiscal 
	 */
	public static final int CMD_PIE_PAGINA = 27;
	/**
	 * Constante de comando fiscal Desactivar proximo corte 
	 */
	public static final int CMD_DESACT_PROX_CORTE = 28;
	/**
	 * Constante de comando fiscal Avance de Papel 
	 */
	public static final int CMD_AVANCE_PAPEL = 29;
	/**
	 * Constante de comando fiscal Obtener Serial de Impresora Fiscal 
	 */
	public static final int CMD_GET_SERIAL = 30;
	/**
	 * Constante de comando fiscal Imprimir Logo 
	 */
	public static final int CMD_LOGO = 31;
	
	/**
	 * Comentario para <code>CMD_ABRIR_GAVETA</code>
	 */
	public static final int CMD_ABRIR_GAVETA = 32;
	
	//Comandos GD4
	/**
	 * Constante de comando fiscal Cerrar Comprobante Fiscal Factura Venta 
	 */
	public static final int CMD_CERRAR_CFV = 33;
	/**
	 * Constante de comando fiscal Cerrar Comprobante Fiscal Nota de Crédito
	 */
	public static final int CMD_CERRAR_CFNC = 34;
	/**
	 * Constante de comando fiscal Cancelar Documento Fiscal Ventas 
	 */
	public static final int CMD_CANC_CFV = 35;
	/**
	 * Constante de comando fiscal Cancelar Documento Fiscal Nota de Crédito
	 */
	public static final int CMD_CANC_CFNC = 36;
	/**
	 * Constante de comando fiscal Item Comprobante Fiscal Venta
	 */
	public static final int CMD_ITEM_CFV = 37;
	/**
	 * Constante de comando fiscal Item Comprobante Fiscal Nota de Crédito
	 */
	public static final int CMD_ITEM_CFNC = 38;
	/**
	 * Constante de comando fiscal Item Comprobante Fiscal Venta NEGATIVO
	 */
	public static final int CMD_ITEM_NEG_CFV = 39;
	/**
	 * Constante de comando fiscal Item Comprobante Fiscal Nota de Crédito NEGATIVO
	 */
	public static final int CMD_ITEM_NEG_CFNC = 40;
	/**
	 * Constante de comando fiscal Lectura Electrónia de Tablas de Memoria Fiscal. Tabla Impuesto 
	 */
	public static final int CMD_TABLA_IMP = 41;
	
	//Agregado por CENTROBECO: Cambio de IVA
	public static final int CMD_TABLA_IMP2 = 54;
	
	/**
	 * Comentario para <code>CMD_SUBTOTAL_CF</code>
	 */
	public static final int CMD_SUBTOTAL_CF = 42;
	/**
	 * Comentario para <code>CMD_READ_TOTAL</code>  Lectura Electrónica de Tablas de Totales
	 * diarios en la Unidad fiscal. 
	 */
	public static final int CMD_READ_TOTAL = 43; 
	/**
	 * Constante de comando fiscal Impresion linea de cheque 
	 */
	public static final int CMD_LINEA_CHEQUE = 44;
	/**
	 * Constante de comando fiscal Fin cheque 
	 */
	public static final int CMD_CERRAR_CHEQUE = 45;
	/**
	 * Constante de comando fiscal Cancelar cheque
	 */
	public static final int CMD_CANC_CHEQUE= 46;
	/**
	 * Comentario para <code>CMD_EXPL_DOC</code>
	 * Constante de Comando Expulsar Documento.
	 */
	public static final int CMD_EXPL_DOC = 47;
	/**
	 * Constante de comando Avance de Linea en Impresión de cheque.
	 */
	public static final int CMD_AVANCE_CHEQUE = 48;
	/**
	 * Constante de comando Voltear documento estacion DI.
	 */
	public static final int CMD_VOLTEA_DOC= 49;
	/**
	 * Comentario para <code>CMD_INFO_TIENDA</code>
	 * Constante de Comando para Emitir reporte de Informacion de TVP/Tienda
	 */
	public static final int CMD_INFO_TIENDA= 50;
	
	/**
	 * Constante de comando Lectura de Método de Impuesto de la Impresora
	 */
	public static final int CMD_IMP_METHOD = 51;
	
	/**
	 * Comentario para <code>CMD_ELECT_REPORTEZ</code>  Lectura Electrónica de reporte Z
	 */
	public static final int CMD_ELECT_REPORTEZ = 52; 	

	/**
	 * Constante de comando fiscal Seteo de Tamaño de Codigo de Barra 
	 */
	public static final int CMD_SET_CODIGO_BARRA = 53;
	
	
	// Manejo de contadores de papel
	// Variables de manejo de documentos
	private CRFPDocument document = null;
	private CRFPSubsystem system = null; 
	
	// Constantes de solicitud de estados
	public static final char ESTADO_CONTADORES = 'N';
	public static final char ESTADO_VTAS_EXENTAS = 'E';
	public static final char ESTADO_BASE_A_IMP = 'A';
	public static final char ESTADO_BASE_B_IMP = 'B';
	public static final char ESTADO_BASE_C_IMP = 'C';
	public static final char ESTADO_DCTOS_IMP = 'D';
	public static final char ESTADO_DEV_IMP = 'R';
	public static final char ESTADO_FIN_PAPEL = 'F';
	public static final char ESTADO_FIN_PAPEL_AUDIT = 'J';
	public static final char ESTADO_DESAC_AUTOCUT = 'S';
	public static final char ESTADO_PAPEL_SLIP = 'U';
	
	//Variables validas solo para GD4
	//CENTROBECO-AMAND: es necesario controlar si el papel ya salio de la bandeja de cheque
	public static boolean transaccionPorImprimir = false;
	public static final String DOC_FISCAL = "CF";
	public static final String DOC_FISCAL_NC = "CFNC";
	
	private static boolean impresionFiscalEnCurso = false;
	
	/**
	 * Constructor principal de la fachada del Driver Fiscal.
	 * @since 11-abr-2005
	 * 
	 */
    public CRFiscalPrinterOperations(String portName, String baudRate, 
            String flowControlIn, String flowControlOut, String dataBits, 
            String stopBits, String parity){
        
        system = new CRFPSubsystem(portName, baudRate, 
                flowControlIn, flowControlOut, dataBits, stopBits, parity, this);
	}
    
    
    
    /**
     * Almacena en el buffer el comando para abrir un documento fiscal de devolución
     * Todos los parámetros de este método son requeridos por la ley para la
     * impresión de comprobantes de devolución fiscal
     * 
     * @param razonSocial Nombre o Razon social del Cliente 
     * @param rifCliente RIF o Cédula del cliente
     * @param numTransVta Numero de Comprobante fiscal de la venta
     * @param serialVta Serial de la impresora fiscal que realizó la venta
     * @param fechaVta Fecha de Venta
     * @param horaVta Hora de Venta
     */
	public void abrirComprobanteDevolucion(String razonSocial, String rifCliente, 
			int numTransVta, String serialVta, Date fechaVta, Time horaVta){
		if (logger.isDebugEnabled()) {
			logger
					.debug("abrirComprobanteDevolucion(String, String, int, String, Date, Time) - start");
		}
		system.status.setDocumentoAbierto(true);
		system.status.setDocumentoFiscalAbierto(true);
		system.status.setPrimeraLineaCF(true);
		system.status.setEstadoFiscal(CRFPStatus.EDO_FISCAL_INICIADO);
		
		Object[] data = new Object[6];
		if (rifCliente != null && rifCliente.trim().length() > 0) {
			data[0] = razonSocial;
			data[1] = rifCliente;
		} else {
			data[0] = "";
			data[1] = "";
		}
		data[2] = new Long(numTransVta);
		data[3] = serialVta;
		data[4] = fechaVta;
		data[5] = horaVta;
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_ABRIR_CF_DEV, data);
		getActiveDocument(true).add(cmd);

		if (system.contarPapel) {
			// Actualizar los contadores
			system.contador.iniciarTransaccion();

			system.contador.consumirPapelAuditoria("IF_ABRIRDEVOLUCION");
			system.contador.consumirPapelTicket("IF_ABRIRDEVOLUCION");
		}
		
		if (logger.isDebugEnabled()) {
			logger
					.debug("abrirComprobanteDevolucion(String, String, int, String, Date, Time) - end");
		}
	}

	/**
	 * Almacena en el buffer el comando para abrir un documento fiscal
	 * Los parámetros no son requeridos. Pueden ser nulos
	 * 
	 * @param razonSocial Nombre o Raz&oacute;n social del cliente
	 * @param rifCliente Rif o cédula del cliente
	 */
	public void abrirComprobanteFiscal(String razonSocial, String rifCliente){
		if (logger.isDebugEnabled()) {
			logger.debug("abrirComprobanteFiscal(String, String) - start");
		}
		
		system.status.setDocumentoAbierto(true);
		system.status.setDocumentoFiscalAbierto(true);
		system.status.setPrimeraLineaCF(true);
		system.status.setEstadoFiscal(CRFPStatus.EDO_FISCAL_INICIADO);
		if (system.contarPapel)
			system.contador.iniciarTransaccion();
		
		
		Object[] data = new Object[2];
		if (rifCliente != null && rifCliente.trim().length() > 0) {
			data[0] = razonSocial;
			data[1] = rifCliente;
			if (system.contarPapel) {
				if (system.status.isUltDocFiscal()) {
					system.contador.consumirPapelAuditoria("IF_ABRIRCOMPFISCAL_CLIENTE_AF");
					system.contador.consumirPapelTicket("IF_ABRIRCOMPFISCAL_CLIENTE_AF");
				} else {
					system.contador.consumirPapelAuditoria("IF_ABRIRCOMPFISCAL_CLIENTE_AN");
					system.contador.consumirPapelTicket("IF_ABRIRCOMPFISCAL_CLIENTE_AN");
				}
			}
			
		} else {
			data[0] = null;
			data[1] = null;
			if (system.contarPapel) {
				if (system.status.isUltDocFiscal()) {
					system.contador.consumirPapelAuditoria("IF_ABRIRCOMPFISCAL_AF");
					system.contador.consumirPapelTicket("IF_ABRIRCOMPFISCAL_AF");
				} else {
					system.contador.consumirPapelAuditoria("IF_ABRIRCOMPFISCAL_AN");
					system.contador.consumirPapelTicket("IF_ABRIRCOMPFISCAL_AN");
				}
			}
		}
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_ABRIR_CF, data);
		getActiveDocument(true).add(cmd);

		if (logger.isDebugEnabled()) {
			logger.debug("abrirComprobanteFiscal(String, String) - end");
		}
	}

	
	/**
	 * Almacena en el buffer el comando para abrir un documento fiscal
	 * Los parámetros no son requeridos. Pueden ser nulos
	 */
	public void abrirComprobanteFiscal(){
		if (logger.isDebugEnabled()) {
			logger.debug("abrirComprobanteFiscal(String, String) - start");
		}
		
		system.status.setDocumentoAbierto(true);
		system.status.setDocumentoFiscalAbierto(true);
		system.status.setPrimeraLineaCF(true);
		system.status.setEstadoFiscal(CRFPStatus.EDO_FISCAL_INICIADO);
		if (system.contarPapel)
			system.contador.iniciarTransaccion();
		
		
		Object[] data = new Object[2];
		if (system.contarPapel) {
			if (system.status.isUltDocFiscal()) {
				system.contador.consumirPapelAuditoria("IF_ABRIRCOMPFISCAL_AF");
				system.contador.consumirPapelTicket("IF_ABRIRCOMPFISCAL_AF");
			} else {
				system.contador.consumirPapelAuditoria("IF_ABRIRCOMPFISCAL_AN");
				system.contador.consumirPapelTicket("IF_ABRIRCOMPFISCAL_AN");
			}
		}
		
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_ABRIR_CF, data);
		getActiveDocument(true).add(cmd);

		if (logger.isDebugEnabled()) {
			logger.debug("abrirComprobanteFiscal(String, String) - end");
		}
	}
	
	
	/**
	 * Almacena en el buffer el comando para abrir docuemnto No fiscal
	 *
	 */
	public void abrirDocumentoNoFiscal() {
		if (logger.isDebugEnabled()) {
			logger.debug("abrirDocumentoNoFiscal() - start");
		}

		system.status.setDocumentoAbierto(true);
		system.status.setDocumentoFiscalAbierto(false);
		system.status.setNumLineaNF(0);
		
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_ABRIR_DNF);
		getActiveDocument(false).add(cmd);
		
		if (system.contarPapel && system.status.isImpresionTicket()) {
			system.contador.iniciarTransaccion();
			if (system.status.isUltDocFiscal()) {
				system.contador.consumirPapelAuditoria("IF_ABRIRDNF_AF");
				system.contador.consumirPapelTicket("IF_ABRIRDNF_AF");
			} else {
				system.contador.consumirPapelAuditoria("IF_ABRIRDNF_AN");
				system.contador.consumirPapelTicket("IF_ABRIRDNF_AN");
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("abrirDocumentoNoFiscal() - end");
		}
	}
	
    /**
     * <p>Abre el puerto de la impresora para que pueda se utilizado para la impresion</p>
     * @since		CR Printer Driver ver. 1.0.0
     */
    public void abrirPuertoImpresora(){
		if (logger.isDebugEnabled()) {
			logger.debug("abrirPuertoImpresora() - start");
		}

		system.engine.abrirConexion();

		if (logger.isDebugEnabled()) {
			logger.debug("abrirPuertoImpresora() - end");
		}
    }
    
	/**
	 * Metodo para envio de comando para activación de la estación 3 
	 * de la impresora (Bandeja de cheques)
	 */
	public void activarSlip() {
		if (logger.isDebugEnabled()) {
			logger.debug("activarSlip() - start");
		}

		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_ACTIVAR_SLIP);
		/* Validacion para Comando activarSlip en GD4, se maneja por banderas, no es
		 * necesario enviar comando.
		 * */ 
		if(cmd.getTipoRespEsperada()!=CRFPStatus.NO_ENVIAR_COMANDO){
			getActiveDocument(false).add(cmd);
		}
		system.status.setImpresionTicket(false);
		if (logger.isDebugEnabled()) {
			logger.debug("activarSlip() - end");
		}
	}
	
	/**
	 * Almacena en el buffer el comando para cancelar un comprobante activo
	 */
	public void cancelarComprobante (){
		if (logger.isDebugEnabled()) {
			logger.debug("cancelarComprobante() - start");
		}

		// Actualizar contadores
		if (system.contarPapel && system.status.isDocumentoFiscalAbierto()) {
			system.contador.consumirPapelAuditoria("IF_CANCCOMPFISCAL");
			system.contador.consumirPapelTicket("IF_CANCCOMPFISCAL");
		}
		
		// Actualizar estado
		system.status.setDocumentoAbierto(false);
		system.status.setDocumentoFiscalAbierto(false);
		system.status.setPrimeraLineaCF(true);
		system.status.setEstadoFiscal(CRFPStatus.EDO_FISCAL_LISTO);
		
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_CANC_CF);
		getActiveDocument(true).add(cmd);

		if (logger.isDebugEnabled()) {
			logger.debug("cancelarComprobante() - end");
		}
	}

	/**
	 * Almacena en el buffer el comando para cancelar un comprobante Ventas activo
	 */
	public void cancelarComprobanteVentas(){
		if (logger.isDebugEnabled()) {
			logger.debug("cancelarComprobante() - start");
		}

		// Actualizar contadores
		if (system.contarPapel && system.status.isDocumentoFiscalAbierto()) {
			system.contador.consumirPapelAuditoria("IF_CANCCOMPFISCAL");
			system.contador.consumirPapelTicket("IF_CANCCOMPFISCAL");
		}
		
		// Actualizar estado
		system.status.setDocumentoAbierto(false);
		system.status.setDocumentoFiscalAbierto(false);
		system.status.setPrimeraLineaCF(true);
		system.status.setEstadoFiscal(CRFPStatus.EDO_FISCAL_LISTO);
		
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_CANC_CFV);
		getActiveDocument(true).add(cmd);

		if (logger.isDebugEnabled()) {
			logger.debug("cancelarComprobante() - end");
		}
	}
	
	/**
	 * Almacena en el buffer el comando para cancelar un comprobante Nota de Credito activo
	 */
	public void cancelarComprobanteNotaCredito (){
		if (logger.isDebugEnabled()) {
			logger.debug("cancelarComprobante() - start");
		}

		// Actualizar contadores
		if (system.contarPapel && system.status.isDocumentoFiscalAbierto()) {
			system.contador.consumirPapelAuditoria("IF_CANCCOMPFISCAL");
			system.contador.consumirPapelTicket("IF_CANCCOMPFISCAL");
		}
		
		// Actualizar estado
		system.status.setDocumentoAbierto(false);
		system.status.setDocumentoFiscalAbierto(false);
		system.status.setPrimeraLineaCF(true);
		system.status.setEstadoFiscal(CRFPStatus.EDO_FISCAL_LISTO);
		
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_CANC_CFNC);
		getActiveDocument(true).add(cmd);

		if (logger.isDebugEnabled()) {
			logger.debug("cancelarComprobante() - end");
		}
	}

	
	/**
	 * Almacena en el buffer el comando para cerrar un documento fiscal
	 * @param tipoCierre Tipo de cierre de impresora: 
	 * 						A - Parcial
	 * 						E - Económico. Comienza a imprimir el encabezado del proximo ticket fiscal
	 */
	public void cerrarComprobanteFiscal(String tipoCierre){
		if (logger.isDebugEnabled()) {
			logger.debug("cerrarComprobanteFiscal(String) - start");
		}

		// Actualizar el estado
		if (system.status.getEstadoFiscal()== CRFPStatus.EDO_FISCAL_INICIADO) {
			system.status.setEstadoFiscal(CRFPStatus.EDO_FISCAL_PARCIAL);
		} else {
			// Finalizado documento
			system.status.setEstadoFiscal(CRFPStatus.EDO_FISCAL_LISTO);
			system.status.setDocumentoAbierto(false);
			system.status.setDocumentoFiscalAbierto(false);
			system.status.setPrimeraLineaCF(true);
			system.status.setUltDocFiscal(true);
		}		
		
		Object[] data = new Object[1];
		data[0] = tipoCierre;
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_CERRAR_CF, data);
		getActiveDocument(true, false).add(cmd);
		
		// Actualizar contadores
		if (system.contarPapel) {
			if (system.status.getEstadoFiscal()== CRFPStatus.EDO_FISCAL_PARCIAL) {
				system.contador.consumirPapelAuditoria("IF_CIERRECOMPFISCAL_PARCIAL");
				system.contador.consumirPapelTicket("IF_CIERRECOMPFISCAL_PARCIAL");
			} else {
				// Cierre total
				system.contador.consumirPapelAuditoria("IF_CIERRECOMPFISCAL_TOTAL");
				system.contador.consumirPapelTicket("IF_CIERRECOMPFISCAL_TOTAL");
			}
		}
		
		
		
		
		if (logger.isDebugEnabled()) {
			logger.debug("cerrarComprobanteFiscal(String) - end");
		}
	}
	/**
	 * Almacena en el buffer el comando para cerrar un documento de Ventas fiscal
	 * @param tipoCierre Tipo de cierre de impresora: 
	 * 						A - Parcial
	 * 						E - Económico. Comienza a imprimir el encabezado del proximo ticket fiscal
	 */
	public void cerrarComprobanteFiscalVentas(String tipoCierre){
		if (logger.isDebugEnabled()) {
			logger.debug("cerrarComprobanteVentasFiscal(String) - start");
		}

		// Actualizar el estado
		if (system.status.getEstadoFiscal()== CRFPStatus.EDO_FISCAL_INICIADO) {
			system.status.setEstadoFiscal(CRFPStatus.EDO_FISCAL_PARCIAL);
		} else {
			// Finalizado documento
			system.status.setEstadoFiscal(CRFPStatus.EDO_FISCAL_LISTO);
			system.status.setDocumentoAbierto(false);
			system.status.setDocumentoFiscalAbierto(false);
			system.status.setPrimeraLineaCF(true);
			system.status.setUltDocFiscal(true);
		}		
		
		Object[] data = new Object[1];
		data[0] = tipoCierre;
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_CERRAR_CFV, data);
		getActiveDocument(true, false).add(cmd);
		
		// Actualizar contadores
		if (system.contarPapel) {
			if (system.status.getEstadoFiscal()== CRFPStatus.EDO_FISCAL_PARCIAL) {
				system.contador.consumirPapelAuditoria("IF_CIERRECOMPFISCAL_PARCIAL");
				system.contador.consumirPapelTicket("IF_CIERRECOMPFISCAL_PARCIAL");
			} else {
				// Cierre total
				system.contador.consumirPapelAuditoria("IF_CIERRECOMPFISCAL_TOTAL");
				system.contador.consumirPapelTicket("IF_CIERRECOMPFISCAL_TOTAL");
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("cerrarComprobanteVentasFiscal(String) - end");
		}
		
	
	}	
	/**
	 * Almacena en el buffer el comando para cerrar un documento fiscal Nota de Crédito
	 * @param tipoCierre Tipo de cierre de impresora: 
	 * 						A - Parcial
	 * 						E - Económico. Comienza a imprimir el encabezado del proximo ticket fiscal
	 */
	public void cerrarComprobanteFiscalNotaCredito(String tipoCierre){
		if (logger.isDebugEnabled()) {
			logger.debug("cerrarNotaCreditoFiscal(String) - start");
		}

		// Actualizar el estado
		if (system.status.getEstadoFiscal()== CRFPStatus.EDO_FISCAL_INICIADO) {
			system.status.setEstadoFiscal(CRFPStatus.EDO_FISCAL_PARCIAL);
		} else {
			// Finalizado documento
			system.status.setEstadoFiscal(CRFPStatus.EDO_FISCAL_LISTO);
			system.status.setDocumentoAbierto(false);
			system.status.setDocumentoFiscalAbierto(false);
			system.status.setPrimeraLineaCF(true);
			system.status.setUltDocFiscal(true);
		}		
		
		Object[] data = new Object[1];
		data[0] = tipoCierre;
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_CERRAR_CFNC, data);
		getActiveDocument(true, false).add(cmd);
		
		// Actualizar contadores
		if (system.contarPapel) {
			if (system.status.getEstadoFiscal()== CRFPStatus.EDO_FISCAL_PARCIAL) {
				system.contador.consumirPapelAuditoria("IF_CIERRECOMPFISCAL_PARCIAL");
				system.contador.consumirPapelTicket("IF_CIERRECOMPFISCAL_PARCIAL");
			} else {
				// Cierre total
				system.contador.consumirPapelAuditoria("IF_CIERRECOMPFISCAL_TOTAL");
				system.contador.consumirPapelTicket("IF_CIERRECOMPFISCAL_TOTAL");
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("cerrarNotaCreditoFiscal(String) - end");
		}
	}
	/**
	 * Metodo para envio de comando para abrir docuemnto No fiscal
	 */
	public void cerrarDocumentoNoFiscal() {
		if (logger.isDebugEnabled()) {
			logger.debug("cerrarDocumentoNoFiscal() - start");
		}

		system.status.setDocumentoAbierto(false);
		system.status.setNumLineaNF(0);
		system.status.setUltDocFiscal(false);
		
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_CERRAR_DNF);
		getActiveDocument(false, false).add(cmd);

		if (system.contarPapel && system.status.isImpresionTicket()) {
			system.contador.consumirPapelAuditoria("IF_CIERREDNF");
			system.contador.consumirPapelTicket("IF_CIERREDNF");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("cerrarDocumentoNoFiscal() - end");
		}
	}
    
    /**
     * <p>Cierra el puerto de la impresora y blanquea el buffer de datos</p>
     * @since		CR Printer Driver ver. 1.0.0
     */
    public void cerrarPuertoImpresora(){
		if (logger.isDebugEnabled()) {
			logger.debug("cerrarPuertoImpresora() - start");
		}

		cerrarPuertoImpresora(false);

		if (logger.isDebugEnabled()) {
			logger.debug("cerrarPuertoImpresora() - end");
		}
    }

    public void cerrarPuertoImpresora(boolean iniciando){
		if (logger.isDebugEnabled()) {
			logger.debug("cerrarPuertoImpresora(boolean) - start");
		}

		system.engine.cerrarConexion(iniciando);

		if (logger.isDebugEnabled()) {
			logger.debug("cerrarPuertoImpresora(boolean) - end");
		}
    }
    

    /**
     * <p>Inicia el proceso de envío de comandos a la impresora fiscal.
     * Envia los comandos dispuestos en el buffer y se prepara para la
     * generación de un nuevo documento
     * </p>
     * @since 		CR Printer Driver ver. 1.0.0
     */
    public void commit() throws PrinterNotConnectedException {
		if (logger.isDebugEnabled()) {
			logger.debug("commit() - start");
		}
		if (document != null)
			system.engine.addDocument(document);
    	document = null;
    	
    	if (system.contarPapel && system.contador.isEnTransaccion())
    		system.contador.commit();
    	
		//Se verifica si se trabaja con el motor GD4Engine, para cargar el ultimo comprobante fiscal
		if(system.engine instanceof com.epa.crprinterdriver.GD4Engine &&
				system.status.isDocumentoFiscalAbierto()){
			//this.ultimoNumeroComprobanteFiscal();
			CRFiscalPrinterOperations.transaccionPorImprimir = true;
		}
    	
    	
		if (logger.isDebugEnabled()) {
			logger.debug("commit() - end");
		}
    }
    
    /**
	 * Metodo para envio de comando para cortar papel.
	 */
	public void cortarPapel() {
		if (logger.isDebugEnabled()) {
			logger.debug("cortarPapel() - start");
		}
		getActiveDocument().add(new CRFPCommand(system.sequenceMap, CMD_CORTAR_PAPEL));
		if (system.contarPapel) {
			system.contador.consumirPapelAuditoria("IF_CORTARPAPEL");
			system.contador.consumirPapelTicket("IF_CORTARPAPEL");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cortarPapel() - end");
		}
	}
	
	/**
	 * Envía las secuencias de escape necesarias para desactivar el próximo corte de papel.
	 */
	public void desactivarProximoCorte() {
		if (logger.isDebugEnabled()) {
			logger.debug("desactivarProximoCorte() - start");
		}

		Object[] data = new Object[1];
		data[0] = "" + ESTADO_DESAC_AUTOCUT;
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_STATUS_IF, data);
		cmd.setTipoRespEsperada(CRFPStatus.DATA_NONE);
		getActiveDocument().add(cmd);

		if (logger.isDebugEnabled()) {
			logger.debug("desactivarProximoCorte() - end");
		}
	}
	
	/**
	 * Metodo para envio de comando para desactivación de la estación 3 
	 * de la impresora (Bandeja de cheques)
	 */
	public void desactivarSlip() {
		if (logger.isDebugEnabled()) {
			logger.debug("desactivarSlip() - start");
		}

		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_DESACTIVAR_SLIP);
		if(cmd.getTipoRespEsperada()!=CRFPStatus.NO_ENVIAR_COMANDO){
			getActiveDocument(false).add(cmd);
		}
		system.status.setImpresionTicket(true);
		if (logger.isDebugEnabled()) {
			logger.debug("desactivarSlip() - end");
		}
	}

	/**
	 *  Metodo para envio de comando para el envio de línea No Fiscal
	 * @param textoNoFiscal Texto a imprimir
	 * @param tipoLetra 0 -> Normal
	 * 					1 -> Resaltado
	 * 					2 -> Centrado
	 * 					3 -> Resaltado y Centrado
	 */
	
	public void enviarLineaNoFiscal(String textoNoFiscal, int tipoLetra) {
		if (logger.isDebugEnabled()) {
			logger.debug("enviarLineaNoFiscal(String, int) - start");
		}
		
		system.status.incNumLineaNF();
		
		Object[] data = new Object[2];
		data[0] = textoNoFiscal;
		data[1] = new Integer(tipoLetra);
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_TEXTO_DNF, data);
		getActiveDocument(false, false).add(cmd);
		
		if (system.contarPapel && system.status.isImpresionTicket()) {
			if (system.status.getNumLineaNF() % 7 == 0 || (system.status.getNumLineaNF() == 1 && textoNoFiscal.length() > 3)) {
				system.contador.consumirPapelAuditoria("IF_LINEA_DOBLE");
				system.contador.consumirPapelTicket("IF_LINEA_DOBLE");
			} else {
				system.contador.consumirPapelAuditoria("IF_LINEA_SIMPLE");
				system.contador.consumirPapelTicket("IF_LINEA_SIMPLE");
			}
			
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("enviarLineaNoFiscal(String, int) - end");
		}
	}
	/**
	 * Solo Aplica para IBM GD4
	 * Otorga descuentos sobre el subtotal de la transacción de ventas
	 * @param monto.		Monto del descuento 
	 * @param descripcion	Descripcion
	 */
	public void descuentoEnSubtotal(double monto, String descripcion){
		if (logger.isDebugEnabled()) {
			logger.debug("descuentoEnSubtotal(double, String- start");
		}

		if (system.contarPapel) {
			if (system.status.isPrimeraLineaCF()) {
				system.contador.consumirPapelAuditoria("IF_PRIMERTEXTOFISCAL");
				system.contador.consumirPapelTicket("IF_PRIMERTEXTOFISCAL");
			} else {
				system.contador.consumirPapelAuditoria("IF_LINEA_SIMPLE");
				system.contador.consumirPapelTicket("IF_LINEA_SIMPLE");
			}
		}
		system.status.setPrimeraLineaCF(false);
		Object[] data = new Object[2];
		data[0] = new Double(monto);
		data[1] = new String(descripcion);
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_DESCUENTO_CF, data);
		getActiveDocument(true, false).add(cmd);
		if (logger.isDebugEnabled()) {
			logger.debug("descuentoEnSubtotal(double, String- end");
		}
	
	}
	/**
	 * @param monto
	 * @param densidadImpresion 	0 -> Normal
	 * 								1 -> Enfatizado 
	 * @param alturaCaracter		0 -> Altura Simple
	 * 								1 -> Reservado
	 * 								2 -> Reservado
	 * 								3 -> DobleAlto
	 */
	public void subTotalTransaccion(double monto, int densidadImpresion, int alturaCaracter){
		if (logger.isDebugEnabled()) {
			logger.debug("subtotalTransaccion(double) - start");
		}

		if (system.contarPapel) {
			if (system.status.isPrimeraLineaCF()) {
				system.contador.consumirPapelAuditoria("IF_PRIMERTEXTOFISCAL");
				system.contador.consumirPapelTicket("IF_PRIMERTEXTOFISCAL");
			} else {
				system.contador.consumirPapelAuditoria("IF_LINEA_SIMPLE");
				system.contador.consumirPapelTicket("IF_LINEA_SIMPLE");
			}
		}
		system.status.setPrimeraLineaCF(false);
		if(densidadImpresion<0 || densidadImpresion>1){
			throw new IllegalArgumentException("subTotalTransaccion(double, int, int):: Argumento Invalido (Altura de Caracter) - Valores permitidos: 0, 1.");
		}
		if(!(alturaCaracter==0 || alturaCaracter==3)){
			throw new IllegalArgumentException("subTotalTransaccion(double, int, int):: Argumento Invalido (Altura de Caracter) - Valores permitidos: 0, 3.");
		}
			
			
		Object[] data = new Object[3];
		data[0] = new Double(monto);
		data[1] = new Integer(densidadImpresion);
		data[2] = new Integer(alturaCaracter);
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_SUBTOTAL_CF, data);
		getActiveDocument(true, false).add(cmd);
		if (logger.isDebugEnabled()) {
			logger.debug("subtotalTransaccion(double) - end");
		}
	
	}
	public void reporteMemoriaFiscal(Date primeraFecha, Date ultimaFecha, String password, String operador, String terminal, String numeroTienda){
		if (logger.isDebugEnabled()) {
			logger.debug("subtotalTransaccion(double) - start");
		}
		Object[] data = new Object[6];
		data[0] = new Date(primeraFecha.getTime());
		data[1] = new Date(ultimaFecha.getTime());
		data[2] = new String(password);
		data[3] = new String(operador);
		data[4] = new String(terminal);
		data[5] = new String(numeroTienda);
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_REPORTE_MF, data);
		getActiveDocument(true, false).add(cmd);
		if (logger.isDebugEnabled()) {
			logger.debug("subtotalTransaccion(double) - end");
		}
	
	}
	
   public boolean estadoCD(){
	if (logger.isDebugEnabled()) {
		logger.debug("estadoCD() - start");
	}

	boolean returnboolean = system.engine.getSerialConnection().testCD();
	if (logger.isDebugEnabled()) {
		logger.debug("estadoCD() - end");
	}
        return returnboolean;
    }
    
    public boolean estadoCTS(){
		if (logger.isDebugEnabled()) {
			logger.debug("estadoCTS() - start");
		}

		boolean returnboolean = system.engine.getSerialConnection().testCTS();
		if (logger.isDebugEnabled()) {
			logger.debug("estadoCTS() - end");
		}
        return returnboolean;
    }
    
    public boolean estadoDSR(){
		if (logger.isDebugEnabled()) {
			logger.debug("estadoDSR() - start");
		}

		boolean returnboolean = system.engine.getSerialConnection().testDSR();
		if (logger.isDebugEnabled()) {
			logger.debug("estadoDSR() - end");
		}
        return returnboolean;
    }
    
    public boolean estadoDTR(){
		if (logger.isDebugEnabled()) {
			logger.debug("estadoDTR() - start");
		}

		boolean returnboolean = system.engine.getSerialConnection().testDTR();
		if (logger.isDebugEnabled()) {
			logger.debug("estadoDTR() - end");
		}
        return returnboolean;
    }
    
    public boolean estadoRI(){
		if (logger.isDebugEnabled()) {
			logger.debug("estadoRI() - start");
		}

		boolean returnboolean = system.engine.getSerialConnection().testRI();
		if (logger.isDebugEnabled()) {
			logger.debug("estadoRI() - end");
		}
        return returnboolean;
    }
    
    public boolean estadoRTS(){
		if (logger.isDebugEnabled()) {
			logger.debug("estadoRTS() - start");
		}

		boolean returnboolean = system.engine.getSerialConnection().testRTS();
		if (logger.isDebugEnabled()) {
			logger.debug("estadoRTS() - end");
		}
        return returnboolean;
    }
	

	/**
	 * Metodo para envio de comando para impresion de cheques
	 * @param monto Monto del cheque
	 * @param beneficiario Beneficiario del cheque
	 * @param fechaEmision Fecha de emisión
	 * @param moneda Tipo de moneda a ser impresa en el archivo
	 */
	public void impresionCheque(double monto, String beneficiario,
			Date fechaEmision, String moneda) {
		if (logger.isDebugEnabled()) {
			logger.debug("impresionCheque(double, String, Date) - start");
		}
		
		Object[] data = null; 
		
		if(system.engine instanceof com.epa.crprinterdriver.GD4Engine){
			this.construirCheque(monto, beneficiario, fechaEmision, moneda);
		}else{
			data = new Object[3];
			data[0] = new Double(monto);
			data[1] = beneficiario;
			data[2] = fechaEmision;
			CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_CHEQUE, data);
			getActiveDocument(false).add(cmd);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("impresionCheque(double, String, Date) - end");
		}
	}

	
	
	/**
	 * Envía las secuencias de escape necesarias para imprimir en el espacio de texto adicional el código de barra.
	 * El codigo enviado solo acepta caracteres num&eacute;ricos
	 * @param codigoBarra Cadena representando al número a ser impreso como codigo de barra
	 */
	public void imprimirCodigoBarra(String codigoBarra){
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirCodigoBarra(String) - start");
		}

		this.imprimirCodigoBarra(codigoBarra, false);
			
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirCodigoBarra(String) - end");
		}
	}
	
	/**
	 * Envía las secuencias de escape necesarias para imprimir en el espacio de texto adicional el código de barra.
	 * El codigo enviado solo acepta caracteres num&eacute;ricos
	 * @param codigoBarra Cadena representando al número a ser impreso como codigo de barra
	 */
	public void imprimirCodigoBarra(String codigoBarra, boolean setBarCodeSize){
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirCodigoBarra(String) - start");
		}

		// Establecemos el Alto y ancho del Código de Barras
		if  (setBarCodeSize) { 
			Object[] data = new Object[1];
			data[0] = codigoBarra;
			CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_SET_CODIGO_BARRA, data);
			getActiveDocument().add(cmd);
		}
		
		if  (system.sequenceMap.implementedCommand(CMD_CODIGO_BARRA)) { 
			Object[] data = new Object[1];
			data[0] = codigoBarra;
			CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_CODIGO_BARRA, data);
			getActiveDocument().add(cmd);
			if (system.contarPapel &&  system.status.isImpresionTicket()) {
				// El código de barra no se imprime en el rollo de auditoria
				system.contador.consumirPapelTicket("IF_CODIGOBARRA");
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirCodigoBarra(String) - end");
		}
	}
	
	/**
	 * Metodo para envio de comando para impresion de endoso de cheques.
	 *  
	 * @param tpoCta Tipo de Cuenta (Ahorro - Corriente)
	 * @param nroCta Numero de la cuenta
	 * @param datoAdicional Cadena de dato adicional (Banco)
	 */
	public void imprimirEndosoCheque(String tpoCta, String nroCta,
			String datoAdicional) {
		if (logger.isDebugEnabled()) {
			logger
					.debug("imprimirEndosoCheque(String, String, String) - start");
		}
		Object[] data = null;
		if(system.engine instanceof com.epa.crprinterdriver.GD4Engine){
			this.construirEndosoCheque(tpoCta, nroCta, datoAdicional);
		}else{
		
			data = new Object[4];
			data[0] = tpoCta;
			data[1] = nroCta;
			data[2] = datoAdicional;
			data[3] = "A";
			CRFPCommand cmd = new CRFPCommand(system.sequenceMap,
					CMD_ENDOSO_CHEQUE, data);
			getActiveDocument(false).add(cmd);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirEndosoCheque(String, String, String) - end");
		}
	}
	
	/**
 	 * Envía las secuencias de escape necesarias para imprimir un item en la factura
	 * @param descripcion Descripcion corta del item (20 Caracteres)
	 * @param cantidad Cantidad del item
	 * @param monto Precio unitario del item, sin impuesto
	 * @param tasaImpositiva Impuesto a aplicar 
	 */
	public void imprimirItem (String descripcion, float cantidad, double monto, double tasaImpositiva){
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirItem(String, float, double, double) - start");
		}

		system.status.setPrimeraLineaCF(false);
		
		Object[] data = new Object[5];
		data[0] = descripcion;
		data[1] = new Float(cantidad);
		data[2] = new Double(monto);
		data[3] = new Double(tasaImpositiva);
		data[4] = "M";
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_ITEM_CF, data);
		getActiveDocument(true, false).add(cmd);

		if (system.contarPapel) {
			if (cantidad == 1) {
				system.contador.consumirPapelAuditoria("IF_LINEA_SIMPLE");
				system.contador.consumirPapelTicket("IF_LINEA_SIMPLE");
			} else {
				system.contador.consumirPapelAuditoria("IF_LINEA_DOBLE");
				system.contador.consumirPapelTicket("IF_LINEA_DOBLE");
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("imprimirItem(String, float, double, double) - end");
		}
	}
	
	/**
 	 * Envía las secuencias de escape necesarias para imprimir un item en la factura
	 * @param descripcion Descripcion corta del item (20 Caracteres)
	 * @param cantidad Cantidad del item
	 * @param monto Precio unitario del item, sin impuesto
	 * @param tasaImpositiva Impuesto a aplicar 
	 */
	public void imprimirItemVenta (String descripcion, float cantidad, double monto, double tasaImpositiva){
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirItem(String, float, double, double) - start");
		}

		system.status.setPrimeraLineaCF(false);
		
		Object[] data = new Object[5];
		data[0] = descripcion;
		data[1] = new Float(cantidad);
		data[2] = new Double(monto);
		data[3] = new Double(tasaImpositiva);
		data[4] = "M";
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_ITEM_CFV, data);
		getActiveDocument(true, false).add(cmd);

		if (system.contarPapel) {
			if (cantidad == 1) {
				system.contador.consumirPapelAuditoria("IF_LINEA_SIMPLE");
				system.contador.consumirPapelTicket("IF_LINEA_SIMPLE");
			} else {
				system.contador.consumirPapelAuditoria("IF_LINEA_DOBLE");
				system.contador.consumirPapelTicket("IF_LINEA_DOBLE");
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("imprimirItem(String, float, double, double) - end");
		}
	}
	
	/**
 	 * Envía las secuencias de escape necesarias para imprimir un item en la factura
	 * @param descripcion Descripcion corta del item (20 Caracteres)
	 * @param cantidad Cantidad del item
	 * @param monto Precio unitario del item, sin impuesto
	 * @param tasaImpositiva Impuesto a aplicar 
	 */
	public void imprimirItemNotaCredito (String descripcion, float cantidad, double monto, double tasaImpositiva){
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirItem(String, float, double, double) - start");
		}

		system.status.setPrimeraLineaCF(false);
		
		Object[] data = new Object[5];
		data[0] = descripcion;
		data[1] = new Float(cantidad);
		data[2] = new Double(monto);
		data[3] = new Double(tasaImpositiva);
		data[4] = "M";
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_ITEM_CFNC, data);
		getActiveDocument(true, false).add(cmd);

		if (system.contarPapel) {
			if (cantidad == 1) {
				system.contador.consumirPapelAuditoria("IF_LINEA_SIMPLE");
				system.contador.consumirPapelTicket("IF_LINEA_SIMPLE");
			} else {
				system.contador.consumirPapelAuditoria("IF_LINEA_DOBLE");
				system.contador.consumirPapelTicket("IF_LINEA_DOBLE");
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("imprimirItem(String, float, double, double) - end");
		}
	}
	

	/**
	 * Envía las secuencias de escape necesarias para imprimir un texto adicional en el comprobante fiscal
	 * @param texto Texto fiscal a ser impreso
	 */
	public void imprimirTextoFiscal (String texto){
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirTextoFiscal(String) - start");
		}

		if (system.contarPapel) {
			if (system.status.isPrimeraLineaCF()) {
				system.contador.consumirPapelAuditoria("IF_PRIMERTEXTOFISCAL");
				system.contador.consumirPapelTicket("IF_PRIMERTEXTOFISCAL");
			} else {
				system.contador.consumirPapelAuditoria("IF_LINEA_SIMPLE");
				system.contador.consumirPapelTicket("IF_LINEA_SIMPLE");
			}
		}
		system.status.setPrimeraLineaCF(false);
		
		Object[] data = new Object[3];
		data[0] = texto;
		data[1] = new Integer(0);
		data[2] = new Boolean(true); 
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_TEXTO_CF, data);
		getActiveDocument(true, false).add(cmd);
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirTextoFiscal(String) - end");
		}
	}
	
	/**
	 * Indica si queda poco papel en el rollo de tickets
	 * @return Verdadero si el rollo de papel de ticket se encuentra en la 
	 * zona de alerta
	 */
	public boolean isPocoPapelTicket() {
		if (logger.isDebugEnabled()) {
			logger.debug("isPocoPapelTicket() - start");
		}

		boolean returnboolean = system.contarPapel && system.contador.isPocoPapelTicket();
		if (logger.isDebugEnabled()) {
			logger.debug("isPocoPapelTicket() - end");
		}
		return returnboolean;
	}
	
	/**
	 * Indica si queda poco papel en el rollo de auditoria
	 * @return Verdadero si el rollo de papel de auditoria se encuentra en la
	 * zona de alerta 
	 */
	public boolean isPocoPapelAuditoria() {
		if (logger.isDebugEnabled()) {
			logger.debug("isPocoPapelAuditoria() - start");
		}

		boolean returnboolean = system.contarPapel && system.contador.isPocoPapelAuditoria();
		if (logger.isDebugEnabled()) {
			logger.debug("isPocoPapelAuditoria() - end");
		}
		return returnboolean;
	}
	
	/**
	 * Inicializa a cero el contador de consumo de papel de auditoria
	 *
	 */
	public void inicializarContadorAuditoria() {
		if (logger.isDebugEnabled()) {
			logger.debug("inicializarContadorAuditoria() - start");
		}

		if (system.contarPapel) 
			system.contador.inicializarContadorAuditoria();

		if (logger.isDebugEnabled()) {
			logger.debug("inicializarContadorAuditoria() - end");
		}
	}
	
	/**
	 *  Inicializa a cero el contador de consumo de papel de tickets
	 *
	 */
	public void inicializarContadorTicket() {
		if (logger.isDebugEnabled()) {
			logger.debug("inicializarContadorTicket() - start");
		}

		if (system.contarPapel)
			system.contador.inicializarContadorTicket();

		if (logger.isDebugEnabled()) {
			logger.debug("inicializarContadorTicket() - end");
		}
	}

	public boolean isImprimiendo() {
		if (logger.isDebugEnabled()) {
			logger.debug("isImprimiendo() - start");
		}

		boolean returnboolean = system.status.isImprimiendo();
		if (logger.isDebugEnabled()) {
			logger.debug("isImprimiendo() - end");
		}
		return returnboolean;
	}
	
	public boolean isRequiereZ() {
		if (logger.isDebugEnabled()) {
			logger.debug("isRequiereZ() - start");
		}

		DataStatusIF respuesta = null;
		boolean result = false;
		try {
			respuesta = (DataStatusIF)system.engine.getSimpleResponse(
					getComandoEstatus(ESTADO_CONTADORES));
			result = respuesta.getStatus() == 4;
		} catch (PrinterNotConnectedException e) {
			logger.error("isRequiereZ()", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isRequiereZ() - end");
		}
		return result;
	}
	
	public String obtenerFechaImpresoraFiscal() {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerFechaImpresoraFiscal() - start");
		}

		DataStatusIF respuesta = null;
		String result = null;
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			respuesta = (DataStatusIF)system.engine.getSimpleResponse(
					getComandoEstatus(ESTADO_CONTADORES));
			
			result =  df.format(respuesta.getFechaHoraIF());
		} catch (PrinterNotConnectedException e) {
			logger.error("obtenerFechaImpresoraFiscal()", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerFechaImpresoraFiscal() - end");
		}
		return result;
	}
		 
	public String obtenerHoraImpresoraFiscal() {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerHoraImpresoraFiscal() - start");
		}

		DataStatusIF respuesta = null;
		String result = null;
		SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
		try {
			respuesta = (DataStatusIF)system.engine.getSimpleResponse(
					getComandoEstatus(ESTADO_CONTADORES));
			
			result =  df.format(respuesta.getFechaHoraIF());
		} catch (PrinterNotConnectedException e) {
			logger.error("obtenerHoraImpresoraFiscal()", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerHoraImpresoraFiscal() - end");
		}
		return result;
	}
	
	/**
	 * Envía las secuencias de escape necesarias para obtener el serial de la impresora Fiscal.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se eliminaron variables sin uso
	* Fecha: agosto 2011
	*/
	public synchronized String obtenerSerialImpresora() throws PrinterNotConnectedException{
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerSerialImpresora() - start");
		}

		Object respuesta = null;
		String result = null;
		respuesta = (String)system.engine.getSimpleResponse(
					new CRFPCommand(system.sequenceMap, CMD_GET_SERIAL));

		result = respuesta.toString();

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerSerialImpresora() - end");
		}
		return result;
	}

	
	/**
	 * Envía las secuencias de escape necesarias para obtener el ultimo número de comprobante fiscal
	 * antes de asignarlo.
	 */
	public synchronized int ultimoNumeroComprobanteFiscal() throws PrinterNotConnectedException {
		if (logger.isDebugEnabled()) {
			logger.debug("proximoNumeroComprobanteFiscal() - start");
		}

		DataStatusIF respuesta = null;
		int result = 0;
		respuesta = (DataStatusIF)system.engine.getSimpleResponse(
				getComandoEstatus(ESTADO_CONTADORES));
		result = respuesta.getNumCFTotal(); 

		if (logger.isDebugEnabled()) {
			logger.debug("proximoNumeroComprobanteFiscal() - end");
		}
		return result;
	}
		
	
	/**
	 * Envía las secuencias de escape necesarias para obtener el próximo número de comprobante fiscal
	 * antes de asignarlo.
	 */
	public synchronized int proximoNumeroComprobanteFiscal() throws PrinterNotConnectedException {
		if (logger.isDebugEnabled()) {
			logger.debug("proximoNumeroComprobanteFiscal() - start");
		}

		DataStatusIF respuesta = null;
		int result = 0;
		respuesta = (DataStatusIF)system.engine.getSimpleResponse(
				getComandoEstatus(ESTADO_CONTADORES));
		result = respuesta.getNumCFTotal() + 1; 

		if (logger.isDebugEnabled()) {
			logger.debug("proximoNumeroComprobanteFiscal() - end");
		}
		return result;
	}
	
	/**
	 * Envía las secuencias de escape necesarias para realizar un pago a la transacción.
	 */
	public void realizarPago (String descripcion,  double monto){
		if (logger.isDebugEnabled()) {
			logger.debug("realizarPago(String, double) - start");
		}

		Object[] data = new Object[3];
		data[0] = descripcion;
		data[1] = new Double(monto);
		data[2] = new Double(0);
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_PAGO_CF, data);
		getActiveDocument(true, false).add(cmd);
		if (logger.isDebugEnabled()) {
			logger.debug("realizarPago(String, double) - end");
		}
	}

	
	
	/**
	 * Envía las secuencias de escape necesarias para generar el reporte X de la caja.
	 */
	public void reporteX() {
		if (logger.isDebugEnabled()) {
			logger.debug("reporteX() - start");
		}

		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_REPORTE_X);
		getActiveDocument(false, true).add(cmd);
		if (system.contarPapel) {
			system.contador.consumirPapelAuditoria("IF_REPORTEX");
			system.contador.consumirPapelTicket("IF_REPORTEX");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("reporteX() - end");
		}
	}
	
	/**
	 * Envía las secuencias de escape necesarias para generar el reporte Z de la caja.
	 */
	public void reporteZ() {
		if (logger.isDebugEnabled()) {
			logger.debug("reporteZ() - start");
		}

		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_REPORTE_Z);
		getActiveDocument(true, true).add(cmd);
		if (system.contarPapel) {
			system.contador.consumirPapelAuditoria("IF_REPORTEZ");
			system.contador.consumirPapelTicket("IF_REPORTEZ");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("reporteZ() - end");
		}
	}
	
	public void reporteElectronicoZ (String numeroReporte){
		if (logger.isDebugEnabled()) {
			logger.debug("reporteElectronicoZ(String, double) - start");
		}

		Object[] data = new Object[1];
		data[0] = numeroReporte;

		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_ELECT_REPORTEZ, data);
		getActiveDocument(false, true).add(cmd);

		if (logger.isDebugEnabled()) {
			logger.debug("reporteElectronicoZ(String, double) - end");
		}
	}	
	
	
	/**
	 * Inicializa la impresora fiscal
	 *
	 */
	public void resetPrinter() {
		if (logger.isDebugEnabled()) {
			logger.debug("resetPrinter() - start");
		}
		resetDocument();
		(CRFPImplFactory.getInstance()).getEngineInstance(system, new Parameters()).ejecutarReset();
		
		//RESET PRINTER DEFAULT
		
		
		if (logger.isDebugEnabled()) {
			logger.debug("resetPrinter() - end");
		}
	}
	
    /**
     * <p>Configura con los parametros actuales el SerialInterfaceProxy para ser inmediatamente utilizado</p>
     * @since 	CR Printer Driver ver. 1.0.0
     * 
     */
    public void setPrinterConfig(){
		if (logger.isDebugEnabled()) {
			logger.debug("setPrinterConfig() - start");
		}
        
        system.setPrinterConfig();
		if (logger.isDebugEnabled()) {
			logger.debug("setPrinterConfig() - end");
		}
    }
	
    // Métodos de manejo de eventos
    
    /**
     * Permite agregar clases de escucha para eventos de la impresora fiscal
     * @param listener Objeto a escuchar eventos del dispositivo fiscal
     */
	public void addFiscalPrinterListener(FiscalPrinterListener listener) {
		if (logger.isDebugEnabled()) {
			logger
					.debug("addFiscalPrinterListener(FiscalPrinterListener) - start");
		}

		system.engine.addFiscalPrinterListener(listener);

		if (logger.isDebugEnabled()) {
			logger
					.debug("addFiscalPrinterListener(FiscalPrinterListener) - end");
		}
	}
	
	/**
	 * Permite remover objetos de escucha de eventos de la impresora fiscal
	 * @param listener Objeto a remover de la lista de escuchas de eventos
	 */
	public void removeFiscalPrinterListener(FiscalPrinterListener listener) {
		if (logger.isDebugEnabled()) {
			logger
					.debug("removeFiscalPrinterListener(FiscalPrinterListener) - start");
		}

		system.engine.removeFiscalPrinterListener(listener);

		if (logger.isDebugEnabled()) {
			logger
					.debug("removeFiscalPrinterListener(FiscalPrinterListener) - end");
		}
	}
	
	//Cambiado a publico por centrobeco para implementacion de resetprinter
	public CRFPDocument getActiveDocument() {
		if (logger.isDebugEnabled()) {
			logger.debug("getActiveDocument() - start");
		}

		CRFPDocument returnCRFPDocument = getActiveDocument('I', 'O');
		if (logger.isDebugEnabled()) {
			logger.debug("getActiveDocument() - end");
		}
		return returnCRFPDocument;
	}
	
	private CRFPDocument getActiveDocument(boolean fiscal) {
		if (logger.isDebugEnabled()) {
			logger.debug("getActiveDocument(boolean) - start");
		}

		CRFPDocument returnCRFPDocument = getActiveDocument(fiscal ? 'F' : 'N',
				'O');
		if (logger.isDebugEnabled()) {
			logger.debug("getActiveDocument(boolean) - end");
		}
		return returnCRFPDocument;
	}

	private CRFPDocument getActiveDocument(boolean fiscal, boolean nuevo) {
		if (logger.isDebugEnabled()) {
			logger.debug("getActiveDocument(boolean, boolean) - start");
		}

		CRFPDocument returnCRFPDocument = getActiveDocument(fiscal ? 'F' : 'N',
				nuevo ? 'N' : 'V');
		if (logger.isDebugEnabled()) {
			logger.debug("getActiveDocument(boolean, boolean) - end");
		}
		return returnCRFPDocument;
	}

	private CRFPDocument getActiveDocument(char fiscal, char nuevo) {
		if (logger.isDebugEnabled()) {
			logger.debug("getActiveDocument(char, char) - start");
		}
		if (document == null) {
			if (nuevo != 'V') {
				document = new CRFPDocument();
				document.setFiscal(fiscal == 'F');
			} else {
				throw new IllegalStateException("No hay un documento creado y el comando no crea un documento por si solo");
			}
		} else {
			if (nuevo == 'N') {
				throw new IllegalStateException("Ya hay un documento creado sin finalizar. Debe finalizar el anterior primero");
			} else {
				if (fiscal != 'I') {
					if (document.isFiscal() != (fiscal == 'F' )) {
						throw new IllegalStateException("Hay un documento con status fiscal distinto al comando solicitado ya creado");
					}
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getActiveDocument(char, char) - end");
		}
		return document;
	}
	
	/**
	 * 
	 * @param monto
	 * @param beneficiario
	 * @param fechaEmision
	 * @param moneda
	 */
	private void construirCheque(double monto, String beneficiario, Date fechaEmision, String moneda) {

		if (logger.isDebugEnabled()) {
			logger.debug("construirCheque(double, String, Date) - start");
		}


		DateFormat simpleDateFormat = SimpleDateFormat
				.getDateInstance(SimpleDateFormat.SHORT);
		NumberFormat numberFormat = new DecimalFormat("#,#00.00");

		this.imprimirLineaCheque("                 ", 0, 2, 0, false);
		this.avanceLineaCheque(10, 1);
		this.imprimirLineaCheque(simpleDateFormat.format(fechaEmision), 0, 2,
				0, false);
		this.avanceLineaCheque(1, 1);
		this.imprimirLineaCheque("-------------------", 0, 2, 0, false);
		this.avanceLineaCheque(1, 1);
		this.imprimirLineaCheque("** " + moneda + " " + numberFormat.format(monto) + " **", 0, 2, 0, false);
		this.avanceLineaCheque(1, 1);
		this.imprimirLineaCheque(beneficiario, 0, 2, 0, false);
		this.avanceLineaCheque(6, 1);
/*		this.imprimirLineaCheque(
				"                                                            "
						+ numberFormat.format(monto), 0, 2, 0, false);*/
		this.finCheque(
				"                                                                "
				+ numberFormat.format(monto), 0, 2, 0, false);

		this.expulsarDocumentoEstacionDI(0);

		if (logger.isDebugEnabled()) {
			logger.debug("construirCheque(double, String, Date) - end");
		}

	}
	
	/**
	 * 
	 * @param tpoCta
	 * @param nroCta
	 * @param datoAdicional
	 */
	private void construirEndosoCheque(String tpoCta, String nroCta, String datoAdicional) {
		String margenEndoso = "     ";
		if (logger.isDebugEnabled()) {
			logger
					.debug("construirEndosoCheque(String, String, String) - start");
		}
		this.imprimirLineaCheque(margenEndoso + "Unicamente para ser depositado en la", 0, 3, 1, false);
		this.imprimirLineaCheque(margenEndoso + "cuenta " + tpoCta , 0, 3, 1, false);
		this.imprimirLineaCheque(margenEndoso + " Nro." + nroCta, 0, 3, 1, false);
		this.finCheque(margenEndoso + datoAdicional, 0, 3, 1, false);
		this.expulsarDocumentoEstacionDI(0);
		/*this.cancelarCheque();*/

		if (logger.isDebugEnabled()) {
			logger.debug("construirEndosoCheque(String, String, String) - end");
		}

	}

	/**
	 * Genera las secuencias para imprimir el logo disponible en la impresora
	 */
	public void imprimirLogo() {
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirLogo(boolean) - start");
		}

		if (system.sequenceMap.implementedCommand(CMD_LOGO)) {
			CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_LOGO);
			getActiveDocument().add(cmd);
			if (system.contarPapel) {
				system.contador.consumirPapelAuditoria("IF_LOGO");
				system.contador.consumirPapelTicket("IF_LOGO");
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirLogo(boolean) - end");
		}
	}
	
	/**
	 * Crea un documento vacío en el driver
	 * @param docFiscal Indica si el documento debe ser fiscal o no
	 */
	public void crearDocumento(boolean docFiscal) {
		getActiveDocument(docFiscal, true);
	}
	
	CRFPCommand getComandoEstatus(char tipo) {
		Object[] data = new Object[1];
		data[0] = Character.toString(tipo);
		return new CRFPCommand(system.sequenceMap, CMD_STATUS_IF, data);
	}
	//TODO: Eliminar Metodo solo es para pruebas
	public void getStatus(char peticion){
		Object[] data = new Object[1];
		data[0] = Character.toString(peticion);
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_STATUS_IF,data);
		getActiveDocument().add(cmd);
	
	}
	public void getTablaTotales(){
		Object[] data = new Object[1];
		data[0] = Character.toString('x');
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_READ_TOTAL,data);
		getActiveDocument().add(cmd);
	}
	
	public void setFechaHora(){
		Object[] data = new Object[1];
		data[0] = new Date();
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_SET_FECHAHORA_IF,data);
		getActiveDocument().add(cmd);
	}

	/**
	 *  Restablece el documento construido y actualmente disponible en la 
	 * memoria del driver. 
	 */
	public void resetDocument() {
		if (document != null) {
			if (system.contador.isEnTransaccion()) {
				system.contador.rollback();
			}
			document.clear();
			document = null;
		}
	}

	/**
	 * Metodo para envio de comando para abrir gaveta desde la impresora.
	 */
	public void abrirGaveta() {
		if (logger.isDebugEnabled()) {
			logger.debug("abrirGaveta() - start");
		}

		getActiveDocument().add(new CRFPCommand(system.sequenceMap, CMD_ABRIR_GAVETA));

		if (logger.isDebugEnabled()) {
			logger.debug("abrirGaveta() - end");
		}
	}
	
	/**
	 * @author: Lenin Gómez.
	 * Metodo para envio de comando para seleccionar los tipos de pagos y para aplicar 
	 * el monto del pago.
	 * @param descripcion 	Descripcion del Pago
	 * @param monto			Monto
	 * @param tipoPago		Tipo de Pago:	0 -> Reservado
	 * 										1 -> Efectivo
	 * 										2 -> Cheque
	 *  									3 -> Tarjeta de Crédito
	 *  									4 -> Cupones
	 *  									5 -> Tarjeta de Débito
	 *  									6 -> Type 6
	 *  									7 -> Reservado
	 * @param tipografia					0 -> 15 CPI
	 * 	 									1 -> 12 CPI
	 *  									2 -> Reservado
	 *  									3 -> Reservado
	 * 										4 -> 15 CPI, enfatizado
	 *  									5 -> 12 CPI, enfatizado
	 *  									6 -> Reservado
	 *  									7 -> Reservado
	 */
	public void realizarPago(String descripcion, double monto, int tipoPago, int tipografia){
		if (logger.isDebugEnabled()) {
			logger.debug("realizarPago(String, double, int, int) - start");
		}
		if(tipoPago<1 || tipoPago>6){
			throw new IllegalArgumentException("realizarPago(String, double, int, int):: Argumento Invalido (Tipo de Pago) - Valores permitidos: 1-6");
		}
		if(tipografia<0 || tipografia>5){
			throw new IllegalArgumentException("realizarPago(String, double, int, int):: Argumento Invalido (Tipo de Pago) - Valores permitidos: 0-5");
		}
		Object[] data = new Object[4];
		data[0] = new String(descripcion);
		data[1] = new Double(monto);
		data[2] = new Integer(tipografia);
		data[3] = new Integer(tipoPago);
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_PAGO_CF, data);
		getActiveDocument(true, false).add(cmd);
		if (logger.isDebugEnabled()) {
			logger.debug("realizarPago(String, double, int, int) - end");
		}
	
	}
		
	/**
	 * @author: Lenin Gómez.
	 * Metodo para envio de comando de Impresion lineas dentro de un Cheque por estacion DI.
	 * Este metodo se debe Invocar luego de la operacion de pagos en las transacciones de
	 * ventas.
	 * @param descripcion	Texto en línea de cheque
	 * @param tipografia	Tipografía del texto a imprimir 
	 * 						0 -> 15 CPI
	 * 						1 -> 12 CPI
	 *  					2 -> Reservado
	 * 						3 -> Reservado 
	 * 						4 -> 15 CPI, enfatizado
	 * 						5 -> 12 CPI, enfatizado
	 * 						6 -> Reservado
	 * 						7 -> Reservado 
	 * @param orientacion	Orientación de Impresión
	 * 						2 -> Horizontal
	 * 						3 -> Vertical
	 * @param direccion 	Direccion de Avance de Linea
	 * 						0 -> Atras
	 * 						1 -> Adelante.
	 * Nota: Metodo solo implementado para IBM GD4
	 */
	public void imprimirLineaCheque(String descripcion, int tipografia, int orientacion, int direccion, boolean centrar){
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirLineaCheque(String, int, int, int) - start");
		}
		if(tipografia<0 || tipografia>5){
			throw new IllegalArgumentException("imprimirLineaCheque(String, int, int, int):: Argumento Invalido (Tipografia) - Valores permitidos: 0-5");
		}
		if(orientacion<2 || orientacion>3){
			throw new IllegalArgumentException("imprimirLineaCheque(String, int, int, int):: Argumento Invalido (Orientacion) - Valores permitidos: 2-3");
		}
		if(direccion<0 || direccion>1){
			throw new IllegalArgumentException("imprimirLineaCheque(String, int, int, int):: Argumento Invalido (DireccionAvanceLinea) - Valores permitidos: 0-1");
		}
		
		Object[] data = new Object[5];
		data[0] = new String(descripcion);
		data[1] = new Integer(tipografia);
		data[2] = new Integer(orientacion);
		data[3] = new Integer(direccion);
		data[4] = new Boolean(centrar);
		
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_LINEA_CHEQUE, data);

		getActiveDocument(true,false).add(cmd);
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirLineaCheque(String, int, int, int) - end");
		}
	
	}

	/**
	 * @author: Lenin Gómez.
	 * Metodo para envio de comando de Impresion de la ultima linea de un Cheque por 
	 * estacion DI. Este metodo se debe Invocar luego de la Impresion de Lineas en
	 * cheque imprimirLineaCheque(String, int, int, int, boolean) durante las transacciones
	 * de ventas.
	 * @param descripcion	Texto en línea de cheque
	 * @param tipografia	Tipografía del texto a imprimir 
	 * 						0 -> 15 CPI
	 * 						1 -> 12 CPI
	 *  					2 -> Reservado
	 * 						3 -> Reservado 
	 * 						4 -> 15 CPI, enfatizado
	 * 						5 -> 12 CPI, enfatizado
	 * 						6 -> Reservado
	 * 						7 -> Reservado 
	 * @param orientacion	Orientación de Impresión
	 * 						2 -> Horizontal
	 * 						3 -> Vertical
	 * @param direccion 	Direccion de Avance de Linea
	 * 						0 -> Atras
	 * 						1 -> Adelante
	 * @see imprimirLineaCheque(String, int, int, int, boolean)
	 * Nota: Metodo solo implementado para IBM GD4
	 */
	public void finCheque(String descripcion, int tipografia, int orientacion, int direccion, boolean centrar){
		if (logger.isDebugEnabled()) {
			logger.debug("finCheque(String, int, int) - start");
		}
		if(tipografia<0 || tipografia>5){
			throw new IllegalArgumentException("finCheque(String, int, int):: Argumento Invalido (Tipografia) - Valores permitidos: 0-5");
		}
		if(orientacion<0 || orientacion>6){
			throw new IllegalArgumentException("finCheque(String, int, int):: Argumento Invalido (Tipografia) - Valores permitidos: 0-6");
		}
		Object[] data = new Object[5];
		data[0] = new String(descripcion);
		data[1] = new Integer(tipografia);
		data[2] = new Integer(orientacion);
		data[3] = new Integer(direccion);
		data[4] = new Boolean(centrar);

		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_CERRAR_CHEQUE, data);
		getActiveDocument(true,false).add(cmd);
		if (logger.isDebugEnabled()) {
			logger.debug("finCheque(String, int, int) - end");
		}
	
	}

	/**
	 * @author: Lenin Gómez.
	 * Metodo para envio de comando de Cancelacion de Cheque por la estacion DI.
	 * Nota: Metodo solo implementado para IBM GD4
	 */
	public void cancelarCheque(){
		if (logger.isDebugEnabled()) {
			logger.debug("cancelarCheque() - start");
		}
		Object[] data = new Object[2];
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_CANC_CHEQUE,data);
		getActiveDocument(true,false).add(cmd);
		if (logger.isDebugEnabled()) {
			logger.debug("cancelarCheque() - end");
		}
	}
	
	/**
	 * Metodo que envia comando para ejecutar el avance de línea en Cheque.
	 * @author: Lenin Gómez.
	 * @param cantidadLineas. Valores permitidos 1 - 15.
	 * @param direccion		0 -> Hacia Adelante.
	 * 						1 -> Hacia Atras.
	 * Nota: Metodo solo implementado para IBM GD4
	 */
	public void avanceLineaCheque(int cantidadLineas, int direccion){
		if (logger.isDebugEnabled()) {
			logger.debug("avanceLineaCheque(int) - start");
		}
		if(direccion< 0 || direccion>1 ){
			throw new IllegalArgumentException("avanceLineaCheque(int, int):: Argumento Invalido (direccion) - Valores permitidos: 0-1");
		}
		if(cantidadLineas< 1 || cantidadLineas>15 ){
			throw new IllegalArgumentException("avanceLineaCheque(int, int):: Argumento Invalido (cantLineas) - Valores permitidos: 1-15");
		}
		
		Object[] data = new Object[2];
		data[0] = new Integer(cantidadLineas);
		data[1] = new Integer(direccion);
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_AVANCE_CHEQUE, data);
		getActiveDocument(true,false).add(cmd);
		
		if (logger.isDebugEnabled()) {
			logger.debug("avanceLineaCheque(int, int) - end");
		}
	
	}
	
	/**
	 * Metodo para envio de comando es usado para avanzar el papel un número de 
	 * líneas especificado en cualquiera de las estaciones de impresión.
	 * @author: Lenin Gómez.
	 * @param estacion 	Estacion en donde se avanzara el papel.
	 * 					0 -> Rollo Cliente
	 * 					1 -> Rollo Cliente y Rollo Auditoria
	 * 					2 -> DI Vertical - Hacia adelante
	 * 					3 -> DI Vertical - Hacia Atras.
	 * @param cantidad 	Cantidad de lineas a avanzar, valores permitidos 1 al 15
	 */
	public void avancePapel(int estacion, int cantidad){
		if (logger.isDebugEnabled()) {
			logger.debug("avancePapel() - start");
		}
		if(estacion < 0 || estacion >3 ){
			throw new IllegalArgumentException("avancePapel(int, int):: Argumento Invalido (estacion) - Valores permitidos: 0-3");
		}
		if(cantidad < 1 || cantidad >15 ){
			throw new IllegalArgumentException("avancePapel(int, int):: Argumento Invalido (Cantidad de lineas) - Valores permitidos: 1-15");
		}
		Object[] data = new Object[2];
		data[0] = new Integer(estacion);
		data[1] = new Integer(cantidad);
		if (system.contarPapel) {
			if (cantidad == 1) {
				system.contador.consumirPapelAuditoria("IF_LINEA_SIMPLE");
				system.contador.consumirPapelTicket("IF_LINEA_SIMPLE");
			} else {
				system.contador.consumirPapelAuditoria("IF_LINEA_DOBLE");
				system.contador.consumirPapelTicket("IF_LINEA_DOBLE");
			}
		}
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_AVANCE_PAPEL,data);
		getActiveDocument(false, false).add(cmd);

		if (logger.isDebugEnabled()) {
			logger.debug("avancePapel() - end");
		}
	
	}
	/**
	 * Metodo para el envio de comando para avanzar las líneas del documento hasta 
	 * que el sensor EOF es activado.
	 * @author: Lenin Gómez.
	 * @param direccion		0 -> Hacia adelante
	 * 						1 -> Hacia Atras
	 */
	public  void expulsarDocumentoEstacionDI(int direccion){
		Object[] data = new Object[2];
		data[0] = new Integer(direccion);
		if (logger.isDebugEnabled()) {
			logger.debug("expulsarDocumentoEstacionDI(int) - start");
		}
		if(direccion< 0 || direccion>1 ){
			throw new IllegalArgumentException("expulsarDocumentoEstacionDI(int):: Argumento Invalido (direccion) - Valores permitidos: 0-1");
		}

		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_EXPL_DOC,data);
		getActiveDocument(true, false).add(cmd);
		if (logger.isDebugEnabled()) {
			logger.debug("expulsarDocumentoEstacionDI(int) - end");
		}
	} 
	/**
	 * @author: Lenin Gómez.
	 * Metodo para el envio de comando para comando dar vuelta a un documento en la 
	 * estación DI para imprimir el lado opuesto después.
	 */
	public  void voltearDocumentoEstacionDI(){
		if (logger.isDebugEnabled()) {
			logger.debug("voltearDocumentoEstacionDI() - start");
		}
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_VOLTEA_DOC);
		getActiveDocument(true, false).add(cmd);
		if (logger.isDebugEnabled()) {
			logger.debug("voltearDocumentoEstacionDI() - end");
		}
	} 
	/**
	 * @author: Lenin Gómez.
	 * Metodo para el envio de comando  de emision de Reporte de identificacion 
	 * de TVP / Tienda
	 */
	public void reporteInformacionTienda(){
		if (logger.isDebugEnabled()) {
			logger.debug("reporteInformacionTienda() - start");
		}
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_INFO_TIENDA);
		getActiveDocument(true, true).add(cmd);
		if (system.contarPapel) {
			system.contador.consumirPapelAuditoria("IF_LINEA_SIMPLE");
			system.contador.consumirPapelTicket("IF_LINEA_SIMPLE");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("reporteInformacionTienda() - end");
		}
		
	
	}
	/**
	 * @author: Lenin Gomez.
	 * Metodo para obtener de la impresora fiscal el valor de las ventas acumuladas 
	 * de la categoria de impuestos especifica. 
	 * @param categoriaImpuesto		0 -> Sumatoria de todas las categorias de Impuesto.
	 * 								n -> Acumuladores de Categoria 'n' de Impuestos 
	 */
	public void obtenerAcumuladoresTransaccionPorCategoria(int categoriaImpuesto){
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerAcumuladoresTransaccionPorCategoria(int) - start");
		}		
		@SuppressWarnings("unused")
		DataSubtotal subtotal = new com.epa.crprinterdriver.data.DataSubtotal();
		Object[] data = new Object[1];
		data[0] = new Integer(categoriaImpuesto);
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_SUB_CF, data);
		try{
			subtotal= (DataSubtotal)system.engine.getSimpleResponse(cmd);	
		}catch(PrinterNotConnectedException ex){
			logger.error("obtenerAcumuladoresTransaccionPorCategoria(int) - Error al establecer conexion con la impresora Fiscal");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerAcumuladoresTransaccionPorCategoria(int) - end");
		}
		
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'HashMap'
	* Fecha: agosto 2011
	*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HashMap<String,?> obtenerEstatusImpresora(){
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerEstatusImpresora() - start");
		}		
		HashMap<String,?> respuesta = null;
		Object respuesta2 = null;
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_SUB_CF);
		try{
			respuesta2 =  system.engine.getSimpleResponse(cmd);
			respuesta = (HashMap) respuesta2;
		}catch(PrinterNotConnectedException ex){
			logger.error("obtenerAcumuladoresTransaccionPorCategoria(int) - Error al establecer conexion con la impresora Fiscal");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerEstatusImpresora() - end");
		}
		
		return respuesta;
		
	}
	
	/**
	 * @author lgomez
	 * Método para establecer el tipo de Cálculo para Método Impuesto Exclusivo
	 * @param calculoMetodoExclusivo		0 -> Precios sin Impuesto Incluído
	 * 										1 -> Precios con Impuesto Incluído
	 * Si el método de Impuesto es Incl. siempre debe establecerse como Cálculo de Método Exclusivo = 0x00
	 * Si el método de Impuesto es Exclusivo el cálculo puede variar de acuerdo a los parámetros recibidos
	 */
	public void setCalculoMetodoExclusivo(int calculoMetodoExclusivo){
		if (logger.isDebugEnabled()) {
			logger.debug("setCalculoMetodoExclusivo(int) - start");
		}		
		system.sequenceMap.setCalculoMetodoExclusivo(calculoMetodoExclusivo);
		if (logger.isDebugEnabled()) {
			logger.debug("setCalculoMetodoExclusivo(int) - end");
		}		

	}
	
	public int getEdo(){
		return this.system.status.getEdoImpresora();
		
	}
	
	public boolean isError(){
		return this.system.status.isErrorImpresora();
		
	}
	
	public CRFPSubsystem getSystem(){
		return this.system;
	}
	
	/**
	 * Envía las secuencias de escape necesarias para imprimir un texto adicional en el comprobante fiscal
	 * Permite ponerle formato al texto
	 * @param texto Texto fiscal a ser impreso
	 */
	public void imprimirTextoFiscal (String texto, int formato){
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirTextoFiscal(String) - start");
		}

		if (system.contarPapel) {
			if (system.status.isPrimeraLineaCF()) {
				system.contador.consumirPapelAuditoria("IF_PRIMERTEXTOFISCAL");
				system.contador.consumirPapelTicket("IF_PRIMERTEXTOFISCAL");
			} else {
				system.contador.consumirPapelAuditoria("IF_LINEA_SIMPLE");
				system.contador.consumirPapelTicket("IF_LINEA_SIMPLE");
			}
		}
		system.status.setPrimeraLineaCF(false);
		
		Object[] data = new Object[3];
		data[0] = texto;
		data[1] = new Integer(formato);
		data[2] = new Boolean(true); 
		CRFPCommand cmd = new CRFPCommand(system.sequenceMap, CMD_TEXTO_CF, data);
		getActiveDocument(true, false).add(cmd);
		if (logger.isDebugEnabled()) {
			logger.debug("imprimirTextoFiscal(String) - end");
		}
	}



	public static boolean isImpresionFiscalEnCurso() {
		return impresionFiscalEnCurso;
	}



	public static void setImpresionFiscalEnCurso(boolean impresionFiscalEnCurso) {
		CRFiscalPrinterOperations.impresionFiscalEnCurso = impresionFiscalEnCurso;
	}
}
