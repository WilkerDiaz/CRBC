package com.epa.testdriver;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import com.epa.crprinterdriver.event.CRFPEvent;
import com.epa.crprinterdriver.event.FiscalPrinterListener;

public class PrinterListen implements FiscalPrinterListener{
	
	int docs;

	
	public PrinterListen(int num){
		docs=num;
	}

	public void commitedDoc() {
		this.docs--;
	
	}


	public void addDocs() {
		this.docs++;
	}


public void eventOccured(final CRFPEvent e) {
			
		
		 switch (e.getType()) {
			
			case CRFPEvent.IMPRESION_OK :
					System.out.println("commit transaccion, todo good");
					//this.commitedDoc();
					try {
					Thread.sleep(5000);
				} catch (InterruptedException e2) {
					// TODO Bloque catch generado automáticamente
					e2.printStackTrace();
				}
					
							
				break;
			case CRFPEvent.ERROR_ATENCION_USUARIO :
				
				final int[] result = new int[1];
				result[0] = -1;
					try {
						SwingUtilities.invokeAndWait(new Runnable(){
							public void run() {
								result[0] = MensajesVentanas.preguntarOpciones(
										e.getIdError()+" - " + e.getErrorMsg(), 
										"Reintentar", "Cancelar", 0);									                            
							}
						});
						
					} catch (InterruptedException e1) {
						System.out.println("eventOccured(CRFPEvent)");
					} catch (InvocationTargetException e1) {
						System.out.println("eventOccured(CRFPEvent)");
					}
				if(result[0] == 0) {
					e.setReintentar(true);					
				}
			
				break;
				
			case CRFPEvent.ERROR_CRITICO :
				
				
					SwingUtilities.invokeLater(new Runnable(){
						public void run() {                      
							MensajesVentanas.aviso(e.getErrorMsg());                      
						}
					});
				break;
			case CRFPEvent.FALTA_PAPEL :
				try {
					SwingUtilities.invokeAndWait(new Runnable(){
						public void run() {                  
							MensajesVentanas.aviso("Falta papel en la impresora.\n Verificar antes de continuar imprimiendo");                  
						}
					});
				} catch (InterruptedException e1) {
					//logger.error("eventOccured(CRFPEvent)", e1);
				} catch (InvocationTargetException e1) {
					//logger.error("eventOccured(CRFPEvent)", e1);
				}
				
				break;
			case CRFPEvent.REQUIERE_Z :
				try {
					SwingUtilities.invokeAndWait(new Runnable(){
						public void run() {                   
							MensajesVentanas.aviso("La impresora requiere un Z.\nSe procederá a la impresión del mismo");                   
						}
					});
				} catch (InterruptedException e1) {
					//logger.error("eventOccured(CRFPEvent)", e1);
				} catch (InvocationTargetException e1) {
					//logger.error("eventOccured(CRFPEvent)", e1);
				}
				
				break;
				}		
				
		}
		
	
}
