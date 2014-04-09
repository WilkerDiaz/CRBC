/*
 * Creado el 05/05/2008
 *
 * Para cambiar la plantilla para este archivo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
package com.beco.cr.actualizadorPrecios.promociones.ManejadorBD;

/**
 * @author aavila
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.mediadoresbd.Conexiones;
import com.beco.cr.actualizadorPrecios.promociones.Donacion;
import com.beco.cr.actualizadorPrecios.promociones.interfaz.VentanaDonacion;
import com.becoblohm.cr.CR;

import java.sql.SQLException;

import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import java.sql.ResultSet;
import java.sql.Timestamp;


/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
* Fecha: agosto 2011
*/
public class DonacionBD {
	
	private static final Logger logger = Logger.getLogger(DonacionBD.class);
	static Date d = new Date();
	public static Vector<Donacion> v = cargarDonacion();
	VentanaDonacion vd = null;
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Donacion> cargarDonacion(){
		if (logger.isDebugEnabled())logger.debug("cargarDonacion - start");
		Vector<Donacion> vdonaciones = new Vector<Donacion>();
		ResultSet donaciones = null;
		try{
			donaciones = Conexiones.realizarConsulta("select * from CR.donacion where'"+new Timestamp(d.getTime())+"'>=fecIniDonacion and fecFinDonacion>='"+new Timestamp(d.getTime())+"' and estadoDonacion=1", true);
			donaciones.beforeFirst();
			while(donaciones.next()){
				Donacion donacion = resultSet2Donacion(donaciones);
				vdonaciones.addElement(donacion);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("cargarDonacion()", ex);
		}finally{
			if (donaciones!=null){
				try{
					donaciones.close();
				}catch(SQLException e){
					logger.error("cargarDonacion()",e);
				}
				donaciones=null;
			}
		}
		if(logger.isDebugEnabled())	logger.debug("cargarDonacion() - end");
		return vdonaciones;
	}
	
	private static Donacion resultSet2Donacion (ResultSet rsdonaciones)throws BaseDeDatosExcepcion{
		try{
			int codigo = rsdonaciones.getInt("codDonacion");
			int codBarra = rsdonaciones.getInt("codBarraProdDonacion");
			Date fechaIni = rsdonaciones.getDate("fecIniDonacion");
			Date fechaFin = rsdonaciones.getDate("fecFinDonacion");
			String nombre = rsdonaciones.getString("nomDonacion");
			String descripcion = rsdonaciones.getString("descDonacion");
			int tipo = rsdonaciones.getInt("tipoDonacion");			
			int estado = rsdonaciones.getInt("estadoDonacion");			
			double cantidad = rsdonaciones.getDouble("cantPropDonacion");
			int mostrarAlTotalizar = rsdonaciones.getInt("mostrarAlTotalizar");
			Donacion donacion = new Donacion(codigo, codBarra, fechaIni, fechaFin, nombre, descripcion, tipo, estado, cantidad, mostrarAlTotalizar==1);
			return donacion;
		}catch(SQLException e){
			throw new BaseDeDatosExcepcion("ERROR CARGANDO RDonaciones", e);
		}
	}
	
	/**
	 * Determina si hay donaciones de tipo distinto al 1 (vuelto
	 * @return boolean True en caso de que existan donaciones de tipo distinto a 1, false en caso contrario
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public boolean hayDonacionesNoTipo1(){
		boolean x = false,y = false;
		Iterator<Donacion> i = v.iterator();
		while (i.hasNext()){
			Donacion don = (Donacion)i.next();
			try{
				if (don.tipoDonacion==1){
					x = false;
				}else{y = true;}
			}
			catch(Exception e){
				System.out.println(e);
			}
			System.out.print(i);
		}
		x |= y;
		return x;
	}
	
	

	public boolean getHayDonaciones(){
		if (v.isEmpty()||!this.hayDonacionesNoTipo1()) return false;
			return true;							 	
	}	
	
	/**
	 * Obtiene donaciones tipo 1 en todas las donaciones registradas
	 * @return Vector donaciones tipo 1
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Donacion> getDonacionesTipo1(){
		Vector<Donacion> v =  new Vector<Donacion>();
		Iterator<Donacion> i = DonacionBD.v.iterator();
		while(i.hasNext()) {
			Donacion d =(Donacion)i.next(); 
			if(d.getTipoDonacion()==1) 
				v.addElement(d);
		} 
		return v;
	}
	
	/**
	 * Obtiene donaciones tipo 2 en todas las donaciones registradas
	 * @return Vector donaciones tipo 2
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Donacion> getDonacionesTipo2(boolean totalizar){
		Vector<Donacion> v =  new Vector<Donacion>();
		Iterator<Donacion> i = DonacionBD.v.iterator();
		while(i.hasNext()) {
			Donacion d =(Donacion)i.next(); 
			if(d.getTipoDonacion()==2 && 
					(d.isMostrarAlTotalizar()==totalizar) || CR.meServ.getApartado()!=null) 
				v.addElement(d);
		} 
		return v;
	}
}