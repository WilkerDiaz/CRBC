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
 * Versión     : 
 * Fecha       : 
 * Analista    : 
 * Descripción : 
 * =============================================================================
 */

package com.beco.ventas.politicasmantenimiento;

import java.sql.SQLException;
import java.util.Calendar;



/**
 *	Esta clase refiere a los objetos que representan Principal. 
 */
public class Principal {
	 private static int  frecuencia = 0;                 // Frecuencia de Sincronización solicitada
	 private final static int ANUAL = 1;                 // Frecuencia de Sincronización anual. Cada 30/06
	 private final static int MENSUAL = 2;               // Frecuencia de Sincronización mensual. Fin de mes
	 private final static int DIARIO = 3;                // Frecuencia de Sincronización diaria. Fin de día

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    if( args.length == 0 ) 
	    {
	      System.out.println( "Se debe llamar al programa con la frecuencia de la política de limpieza." );
	      System.exit(1);
	    }

	    try 
	    { 	
	    	//Se cargan las preferencias y Nos conectamos a la BD 
			new Sesion();
			new Conexiones();

			
	    	  // Se obtiene la frecuencia de sincronización que se recibe como parámetro
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
		        	//Controlador.limpiarArchivosDetail(); //Comentado temporalmente mientras se aplican políticas de BK de los archivos del Serv de Tda
		            break;
	
		        default:
		          // Error de parámetro inválido
		          System.out.println( "Parámetro Inválido" );
			      System.exit(1);
		      }
	    
		 } catch (ClassNotFoundException e) {
			 e.printStackTrace();
		 } catch (SQLException e) {
			 e.printStackTrace();
		 } catch ( NumberFormatException e) {
	      e.printStackTrace();	
	      System.out.println( "Parámetro Inválido. Debe ser un Entero" );
	      System.exit( 2 );
	    } catch (Exception e) {
			e.printStackTrace();
		}
	}
}
