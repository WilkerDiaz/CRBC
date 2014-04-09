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

	// Constantes de acci�n ante errores
	/**
	 *  Constante de acci�n ante situaci�n de error: Aborta el documento actual 
	 */
	public static final int ABORTAR_DOCUMENTO = 0;

	/**
	 *  Constante de acci�n ante situaci�n de error: Comienza nuevamente impresi�n de documento 
	 */
	public static final int REPETIR_DOCUMENTO = 1;

	/**
	 *  Constante de acci�n ante situaci�n de error: Repite el comando actual 
	 */
	public static final int REPETIR_COMANDO = 2;

	/**
	 *  Constante de acci�n ante situaci�n de error:  Espera la confirmaci�n de reintento por parte de la aplicaci�n
	 */
	public static final int ESPERAR_APLICACION = 3;

	/**
	 *  Constante de acci�n ante situaci�n de error: Realiza el reporte Z requerido 
	 */
	public static final int REALIZAR_Z = 4;

	/**
	 *  Constante de acci�n ante situaci�n de error: Cierra el comprobante fiscal en proceso 
	 */
	public static final int CERRAR_CF = 5;

	/**
	 *  Constante de acci�n ante situaci�n de error: Ignora el error y continua la impresi�n del documento 
	 */
	public static final int IGNORAR_ERROR = 6;

	/**
	 *  Constante de acci�n ante situaci�n de error: Cancela Transacci�n de Ventas. 
	 */
	public static final int CANCELAR_TRANS_VENTAS = 7;

	/**
	 *  Constante de acci�n ante situaci�n de error: Cancela Transacci�n de Ventas. 
	 */
	public static final int CANCELAR_DEVOLUCION = 8;
	
	/**
	 *  Constante de acci�n ante situaci�n de error: Cancela Transacci�n de Ventas y Emite Reporte "Z". 
	 */
	public static final int CANCELAR_TRANS_EMITIRZ = 9;
	
	/**
	 *  Constante de acci�n ante situaci�n de error: Realiza el reporte X requerido 
	 */
	public static final int REALIZAR_X = 10;
	
	/**
	 *  Constante de acci�n ante situaci�n de error: Reinicia Impresora y Unidad Fiscal 
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
	 * M�todo para agregar al spool de impresi�n un documento.
	 * 
	 * @param doc
	 */
	public abstract int addDocument(CRFPDocument doc);

	/**
	 * M�todo de obtenci�n de informaci�n tipo estatus desde la impresora fiscal
	 * 
	 * @param cmd Comando de solicitud de informaci�n
	 * @return Data retornada por la impresora fiscal
	 * @throws PrinterNotConnectedException si ocurre un error al recibir la respuesta
	 */
	public abstract Object getSimpleResponse(CRFPCommand cmd)
			throws PrinterNotConnectedException;

	/**
	 * M�todo de impresi�n de documentos que retornan informaci�n compleja, tales
	 * como reportes. Este m�todo espera hasta que se realice la impresi�n del 
	 * documento y sea devuelta la informaci�n solicitada
	 * 
	 * @param doc Documento a imprimir
	 * @return Objeto de datos
	 * @throws PrinterNotConnectedException si ocurre un error al recibir la respuesta	 
	 */
	public abstract Object getComplexResponse(CRFPDocument doc)
			throws PrinterNotConnectedException;

	/**
	 * Inicia la operaci�n del motor
	 *
	 */
	public abstract void iniciar();

	/**
	 * Finaliza la operaci�n del motor
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
	 * Abre la conexi�n serial del motor
	 *
	 */
	public abstract void abrirConexion();

	/**
	 * Cierra la conexi�n serial
	 *
	 */
	public abstract void cerrarConexion(boolean iniciando);
	
	//AGREGADO POR CENTROBECO PARA IMPLEMENTACI�N DE RESETPRINTER
	public abstract void ejecutarReset();
	
}
