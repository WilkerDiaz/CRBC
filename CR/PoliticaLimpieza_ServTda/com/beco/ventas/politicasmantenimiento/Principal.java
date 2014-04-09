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

import java.sql.SQLException;
import java.util.Calendar;



/**
 *	Esta clase refiere a los objetos que representan Principal. 
 */
public class Principal {
	 private static int  frecuencia = 0;                 // Frecuencia de Sincronizaci�n solicitada
	 private final static int ANUAL = 1;                 // Frecuencia de Sincronizaci�n anual. Cada 30/06
	 private final static int MENSUAL = 2;               // Frecuencia de Sincronizaci�n mensual. Fin de mes
	 private final static int DIARIO = 3;                // Frecuencia de Sincronizaci�n diaria. Fin de d�a

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    if( args.length == 0 ) 
	    {
	      System.out.println( "Se debe llamar al programa con la frecuencia de la pol�tica de limpieza." );
	      System.exit(1);
	    }

	    try 
	    { 	
	    	//Se cargan las preferencias y Nos conectamos a la BD 
			new Sesion();
			new Conexiones();

			
	    	  // Se obtiene la frecuencia de sincronizaci�n que se recibe como par�metro
		      frecuencia = Integer.parseInt( args[ 0 ]);
		      
		      switch( frecuencia ) 
		      {
	
		        case ANUAL:
		        	//Productos
		        	System.out.println("\n********* POLITICA DE LIMPIEZA ANUAL: " + Calendar.getInstance().getTime());
		        	Controlador.limpiarProductos();
		        	break;
	
		        case MENSUAL:
		        	System.out.println("\n********* POLITICA DE LIMPIEZA MENSUAL: " + Calendar.getInstance().getTime());
		        	//Usuarios y Afiliados
		        	Controlador.limpiarUsuarios();
		        	//Controlador.limpiarAfiliado();
		        	
		        	
		        	//Auditoria
		        	Controlador.limpiarAuditoria();
		        	Controlador.limpiarPromociones();
		        	Controlador.limpiarTransacciones();
		        	Controlador.limpiarServicios();
		        	Controlador.limpiarListasRegalos();
		        	break;
		        	
		        case DIARIO:
		        	System.out.println("\n********* POLITICA DE LIMPIEZA DIARIO: " + Calendar.getInstance().getTime());
		        	Controlador.resetMasterMYSQL();
		        	//Controlador.limpiarArchivosDetail(); //Comentado temporalmente mientras se aplican pol�ticas de BK de los archivos del Serv de Tda
		            break;
	
		        default:
		          // Error de par�metro inv�lido
		          System.out.println( "Par�metro Inv�lido" );
			      System.exit(1);
		      }
	    
		 } catch (ClassNotFoundException e) {
			 e.printStackTrace();
		 } catch (SQLException e) {
			 e.printStackTrace();
		 } catch ( NumberFormatException e) {
	      e.printStackTrace();	
	      System.out.println( "Par�metro Inv�lido. Debe ser un Entero" );
	      System.exit( 2 );
	    } catch (Exception e) {
			e.printStackTrace();
		}
	}
}
