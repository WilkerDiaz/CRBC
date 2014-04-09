/**
 * =============================================================================
 * Proyecto   : PoliticaMant_ServCR
 * Paquete    : com.beco.ventas.politicasmantenimiento
 * Programa   : Principal.java
 * Creado por : irojas
 * Creado en  : 17/06/2009 - 02:20:49 PM
 *
 * (c) CENTROBECO, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versi�n     : 
 * Fecha       : 
 * Analista    : 
 * Descripci�n : 
 * =============================================================================
 */

package com.beco.ventas.politicasmantenimiento;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.becoblohm.cr.manejadorauditoria.Auditoria;



/**
 *	Esta clase refiere a los objetos que representan Principal. 
 */
public class Principal {
	 private final static int ANUAL = 1;                 // Frecuencia de Sincronizaci�n anual. Cada 30/06
	 private final static int MENSUAL = 2;               // Frecuencia de Sincronizaci�n mensual. Fin de mes
	 private final static int DIARIO = 3;                // Frecuencia de Sincronizaci�n diaria. Fin de d�a

	/**
	 * @param args
	 */
	public static void limpieza (int frecuencia) {

	    try 
	    { 		  
        	/**** Calculo de ltima ejecucin de la poltica de limpieza ANUAL****/
        	Calendar fechaUltAct = Calendar.getInstance();	
        	SimpleDateFormat fechaUlt = new SimpleDateFormat("yyyy-MM-dd");
        	String fechaUltActString=  fechaUlt.format(fechaUltAct.getTime());
    		/*****************************************************************/
		      switch( frecuencia ) 
		      {
	
		        case ANUAL:
		        	//Productos
		        	Auditoria.registrarAuditoria("\n********* POLITICA DE LIMPIEZA ANUAL: " + Calendar.getInstance().getTime(),'O');
		        	Controlador.limpiarProductos();
		    		
			        	/**** Calculo de pr�xima ejecuci�n de la pol�tica de limpieza ANUAL****/
			        	Calendar fechaProxAct = Calendar.getInstance();
			        	fechaProxAct.set(Calendar.DAY_OF_MONTH, 30);
			        	fechaProxAct.set(Calendar.MONTH, Calendar.JUNE);
			        	fechaProxAct.add(Calendar.YEAR, 1);
			        	SimpleDateFormat fechaProxAnual = new SimpleDateFormat("yyyy-MM-dd");
			        	String fechaProxActAnualString = fechaProxAnual.format(fechaProxAct.getTime());
			        	/*********/
		        	//**** Actualizaci�n de tabla de control de pol�ticas de limpieza
		    		Controlador.actualizarEjecucionPolitica(ANUAL, fechaUltActString, fechaProxActAnualString);
		    		break;
	
		        case MENSUAL:
		        	Auditoria.registrarAuditoria("\n********* POLITICA DE LIMPIEZA MENSUAL: " + Calendar.getInstance().getTime(),'O');
		        	//Usuarios y Afiliados
		        	Controlador.limpiarUsuarios();
		        	//Controlador.limpiarAfiliado();
		        	
		        	//Resto de las tareas
		        	Controlador.limpiarAuditoria();
		        	Controlador.limpiarPromociones();
		        	Controlador.limpiarTransacciones(); 
		        	//Controlador.limpiarServicios();
		        	//Controlador.limpiarListasRegalos();
		        	
			        	/**** Calculo de pr�xima ejecuci�n de la pol�tica de limpieza MENSUAL****/
			        	Calendar fechaProxActMens = Calendar.getInstance();
			        	fechaProxActMens.set(Calendar.DAY_OF_MONTH, 1);
			        	fechaProxActMens.add(Calendar.MONTH, 1);
			        	SimpleDateFormat fechaProxMensual = new SimpleDateFormat("yyyy-MM-dd");
			        	String fechaProxActMensualString = fechaProxMensual.format(fechaProxActMens.getTime());
			        	/*********/
		    		
		        	//**** Actualizaci�n de tabla de control de pol�ticas de limpieza
		    		Controlador.actualizarEjecucionPolitica(MENSUAL, fechaUltActString, fechaProxActMensualString);
		        	break;
		        	
		        case DIARIO:
		        	Auditoria.registrarAuditoria("\n********* POLITICA DE LIMPIEZA DIARIO: " + Calendar.getInstance().getTime(),'O');
		        	//11/03/2011 IROJAS: Agregada ejecuci�n de LOGS de MYSQL en la pol�tica diaria
		        	Controlador.resetMasterMYSQL(); 

		        	Controlador.limpiarVouchers();

		        	/**** Calculo de prxima ejecucin de la poltica de limpieza DIARIA****/
		        	Calendar fechaProxActDia = Calendar.getInstance();
		        	fechaProxActDia.add(Calendar.DAY_OF_MONTH, 1);			        	
		        	SimpleDateFormat fechaProx = new SimpleDateFormat("yyyy-MM-dd");
		        	String fechaProxActDiarioString = fechaProx.format(fechaProxActDia.getTime());
		        	/*********/
		    		
		        	//**** Actualizaci�n de tabla de control de pol�ticas de limpieza
		    		Controlador.actualizarEjecucionPolitica(DIARIO, fechaUltActString, fechaProxActDiarioString);
		            break;
	
		        default:
		          // Error de par�metro inv�lido
			      System.exit(1);
		      }
	    
		 } catch ( NumberFormatException e) {
			 System.exit( 2 );
	    } catch (Exception e) {
		}
	}
}
