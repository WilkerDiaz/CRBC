/*
 * $Id: HiloTransferenciasInmediatas.java
 * ===========================================================================
 * Material Propiedad CENTROBECO, C.A.
 *
 * Proyecto		: CR
 * Paquete		: com.becoblohm.cr.sincronizador
 * Programa		: HiloTransferenciasInmediatas.java
 * Creado por	: jgraterol
 * Creado en 	: 19/06/2009 
 * (C) Copyright 2005 CENTROBECO, C.A. Todos los Derechos Reservados
 * ---------------------------------------------------------------------------
 * Actualizaciones:
 * ===========================================================================
 */
package com.becoblohm.cr.sincronizador;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.TreeMap;
import java.util.Vector;

import com.beco.cr.actualizadorPrecios.promociones.ComparadorPromociones;
import com.beco.cr.actualizadorPrecios.promociones.ProductoCombo;
import com.beco.cr.actualizadorPrecios.promociones.PromocionExt;
import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.extensiones.ActualizadorPreciosFactory;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarventa.Producto;
import com.becoblohm.cr.mediadoresbd.MediadorBD;

public class HiloTransferenciasInmediatas extends Thread {

	public static void main(String[] args){
		HiloTransferenciasInmediatas x = new HiloTransferenciasInmediatas();
		x.start();
	}
	public void run() {
		while (true){
			
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				
			}
			try {
				boolean procesado = false;
				
				/*File archivoInmediatas = new File("inmediatas.txt");
				BufferedReader input =  new BufferedReader(new FileReader(archivoInmediatas));*/
				//try {
				//Se realiza un vector con solo los detalles de la promocion
				Vector<ProductoCombo> productosComboMemoriaAux = new Vector<ProductoCombo>();
				Vector<Producto> productosAhorro = new Vector<Producto>();
				String query="select codigo, detalle from "+Sesion.getDbEsquema()+".transferenciainmediatapromo";
				ResultSet rs = MediadorBD.realizarConsulta(query);
				MediadorBD.ejecutar("delete from "+Sesion.getDbEsquema()+".transferenciainmediatapromo");
				rs.beforeFirst();
				int codPromocion=0, numDetalle=0;
				boolean promoticket = false;
				int type = 0;
		        while(rs.next()){
		        	if(codPromocion!=0 && codPromocion!=rs.getInt("codigo")){
		        		procesado = false;
		        	}
		        	
		        	codPromocion=rs.getInt("codigo");
		        	numDetalle=rs.getInt("detalle");
		        	/*if(!line.startsWith("#")){
			        	StringTokenizer strtoken = new StringTokenizer(line,";");
			        	String codPromocion = null;
			        	String numDetalle = null;
			        	if(strtoken.hasMoreElements()){
			        		codPromocion = strtoken.nextToken();
			        	}
			        	if(strtoken.hasMoreElements()){
			        		numDetalle = strtoken.nextToken();
			        	}*/
		        	//query="delete from "+Sesion.getDbEsquema()+".transferenciainmediatapromo where codigo="+codPromocion+" and detalle="+numDetalle;
		        	//System.out.println(query);
		        	//MediadorBD.ejecutar(query);
		        	//System.out.println("lo hizo");
		        	if (type==0 || type==1){
		        		procesado = (new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().recalcularProductosCombo(codPromocion, numDetalle,productosComboMemoriaAux);		         		
		        		//Si la promo era combo next = false
		        		if(procesado) type = 1;
		        	}
		        	if (type==0 || type == 2){
		        		//Actualizar patrocinantes (Ahorro en compra)
		        		procesado = (new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().recalcularAhorroEnCompra(codPromocion, numDetalle,productosAhorro);
		        		//Si la promo era patrocinante next = true
		        		if(procesado) type = 2;
		        	}
		        	if(type==0)
		        		promoticket=true;
	
		        	//Actualizar promoTicket
			        //Actualizar patrocinantes (Ahorro en compra)
		        	//}
		        }
		        if(promoticket){
		        	(new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().actualizarTablaPromoTicket(codPromocion);
		        }
		        if(productosComboMemoriaAux.size()>0){
		        	Sesion.productosCombo.put(codPromocion+"", productosComboMemoriaAux);
		        }
		        if(productosAhorro.size()>0){
		        	PromocionExt promo = PromocionExt.getAhorroEnCompra(codPromocion);
		        	CR.meServ.getProductosPatrocinantes().put(promo, productosAhorro);
		        	Vector <Producto> copy = new Vector<Producto>();
		        	for(Producto prod:productosAhorro){
		        		copy.add((Producto)prod.clone());
		        	}
		        	CR.meVenta.getProductosPatrocinantes().put(promo, copy);
		        }
		       /* if(codPromocion!=0){
		        	if(promoticket){
		        		
		        	}else if(!next){
		        		//Eliminar Productos combo desactivados
		        		(new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().recalcularProductosComboEliminados(codPromocion);
		        	}else{
		        		//eliminar Productos Ahorro en compra desactivados
		        	}
		        }
			      /*} finally {
			        input.close();
			        archivoInmediatas.delete();
			      }*/

			} catch (SQLException e/*FileNotFoundException e*/) {
				//No han escrito nada
			/*} catch(IOException e) {*/
		    	  
		    } catch (BaseDeDatosExcepcion e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			} catch (ConexionExcepcion e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			}

			
			
		}
		
	}
}
