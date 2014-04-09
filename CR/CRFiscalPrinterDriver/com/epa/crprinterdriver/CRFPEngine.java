/**
 * 
 */
package com.epa.crprinterdriver;

import gnu.io.SerialPortEvent;

import javax.swing.event.DocumentEvent;

import com.epa.crprinterdriver.event.CRFPResponseEvent;
import com.epa.crprinterdriver.event.FiscalPrinterListener;
import com.epa.crserialinterface.Connection;

/**
 * @autor rabreu
 *
 */
public interface CRFPEngine {

	// Constantes de acción ante errores
	/**
	 *  Constante de acción ante situación de error: Aborta el documento actual 
	 */
	public static final int ABORTAR_DOCUMENTO = 0;

	/**
	 *  Constante de acción ante situación de error: Comienza nuevamente impresión de documento 
	 */
	public static final int REPETIR_DOCUMENTO = 1;

	/**
	 *  Constante de acción ante situación de error: Repite el comando actual 
	 */
	public static final int REPETIR_COMANDO = 2;

	/**
	 *  Constante de acción ante situación de error:  Espera la confirmación de reintento por parte de la aplicación
	 */
	public static final int ESPERAR_APLICACION = 3;

	/**
	 *  Constante de acción ante situación de error: Realiza el reporte Z requerido 
	 */
	public static final int REALIZAR_Z = 4;

	/**
	 *  Constante de acción ante situación de error: Cierra el comprobante fiscal en proceso 
	 */
	public static final int CERRAR_CF = 5;

	/**
	 *  Constante de acción ante situación de error: Ignora el error y continua la impresión del documento 
	 */
	public static final int IGNORAR_ERROR = 6;

	/**
	 *  Constante de acción ante situación de error: Cancela Transacción de Ventas. 
	 */
	public static final int CANCELAR_TRANS_VENTAS = 7;

	/**
	 *  Constante de acción ante situación de error: Cancela Transacción de Ventas. 
	 */
	public static final int CANCELAR_DEVOLUCION = 8;
	
	/**
	 *  Constante de acción ante situación de error: Cancela Transacción de Ventas y Emite Reporte "Z". 
	 */
	public static final int CANCELAR_TRANS_EMITIRZ = 9;
	
	/**
	 *  Constante de acción ante situación de error: Realiza el reporte X requerido 
	 */
	public static final int REALIZAR_X = 10;
	
	/**
	 *  Constante de acción ante situación de error: Reinicia Impresora y Unidad Fiscal 
	 */
	public static final int REINICIAR_IMPRESORA = 11;

	/* (sin Javadoc)
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 * @param e
	 * @since 11-abr-2005
	 */
	public abstract void changedUpdate(DocumentEvent e);

	/* (sin Javadoc)
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 * @param e
	 * @since 11-abr-2005
	 */
	public abstract void insertUpdate(DocumentEvent e);

	/* (sin Javadoc)
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
	 * @param e
	 * @since 11-abr-2005
	 */
	public abstract void removeUpdate(DocumentEvent e);

	/* (sin Javadoc)
	 * @see com.epa.crserialinterface.SerialSignalListener#signalReceived(gnu.io.SerialPortEvent)
	 * @param event
	 * @since 11-abr-2005
	 */
	public abstract void signalReceived(SerialPortEvent event);

	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.event.ResponseListener#responseArrived(com.epa.crprinterdriver.event.CRFPResponseEvent)
	 * @param event
	 * @since 14-abr-2005
	 */
	public abstract void responseArrived(CRFPResponseEvent event);

	/* (sin Javadoc)
	 * @see com.epa.crprinterdriver.event.StatusChangeListener#changedStatus(int)
	 * @param paramChanged
	 * @since 14-abr-2005
	 */
	public abstract void changedStatus(int paramChanged);

	/* (sin Javadoc)
	 * @see java.lang.Runnable#run()
	 * 
	 * @since 11-abr-2005
	 */
	public abstract void run();

	/**
	 * @return Devuelve serialConnection.
	 */
	public abstract Connection getSerialConnection();

	/**
	 * Método para agregar al spool de impresión un documento.
	 * 
	 * @param doc
	 */
	public abstract int addDocument(CRFPDocument doc);

	/**
	 * Método de obtención de información tipo estatus desde la impresora fiscal
	 * 
	 * @param cmd Comando de solicitud de información
	 * @return Data retornada por la impresora fiscal
	 * @throws PrinterNotConnectedException si ocurre un error al recibir la respuesta
	 */
	public abstract Object getSimpleResponse(CRFPCommand cmd)
			throws PrinterNotConnectedException;

	/**
	 * Método de impresión de documentos que retornan información compleja, tales
	 * como reportes. Este método espera hasta que se realice la impresión del 
	 * documento y sea devuelta la información solicitada
	 * 
	 * @param doc Documento a imprimir
	 * @return Objeto de datos
	 * @throws PrinterNotConnectedException si ocurre un error al recibir la respuesta	 
	 */
	public abstract Object getComplexResponse(CRFPDocument doc)
			throws PrinterNotConnectedException;

	/**
	 * Inicia la operación del motor
	 *
	 */
	public abstract void iniciar();

	/**
	 * Finaliza la operación del motor
	 *
	 */
	public abstract void finalizar();

	/**
	 * Permite agregar clases de escucha para eventos de la impresora fiscal
	 * @param listener Objeto a escuchar eventos del dispositivo fiscal
	 */
	public abstract void addFiscalPrinterListener(FiscalPrinterListener listener);

	/**
	 * Permite remover objetos de escucha de eventos de la impresora fiscal
	 * @param listener Objeto a remover de la lista de escuchas de eventos
	 */
	public abstract void removeFiscalPrinterListener(FiscalPrinterListener listener);

	/**
	 * Indica si el motor se encuentra pausado o no
	 * @return Devuelve pausado.
	 */
	public abstract boolean isPausado();

	/**
	 * Pausa o activa el motor
	 * @param pausado El pausado a establecer.
	 */
	public abstract void setPausado(boolean pausado);

	/**
	 * Abre la conexión serial del motor
	 *
	 */
	public abstract void abrirConexion();

	/**
	 * Cierra la conexión serial
	 *
	 */
	public abstract void cerrarConexion(boolean iniciando);
	
	//AGREGADO POR CENTROBECO PARA IMPLEMENTACIÓN DE RESETPRINTER
	public abstract void ejecutarReset();
	
}
