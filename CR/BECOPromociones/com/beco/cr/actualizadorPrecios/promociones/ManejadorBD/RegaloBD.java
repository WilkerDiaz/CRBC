package com.beco.cr.actualizadorPrecios.promociones.ManejadorBD;

import org.apache.log4j.Logger;


import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.mediadoresbd.Conexiones;
import com.beco.cr.actualizadorPrecios.promociones.Regalo;

import java.sql.SQLException;

import java.util.Date;
import java.util.Vector;
import java.sql.ResultSet;
import java.sql.Timestamp;


public class RegaloBD {
	
	private static final Logger logger = Logger.getLogger(RegaloBD.class);
	static Date d = new Date();
	public static Timestamp hoy = new Timestamp(d.getTime());
	public static Vector<Regalo> v = cargarRegalo(hoy);
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<Regalo> cargarRegalo(Timestamp hoy){
		if (logger.isDebugEnabled()){
			logger.debug("cargarRegalo - start");
		}
		Vector<Regalo> vregalos = new Vector<Regalo>();
		ResultSet regalos = null;
		try{
			System.out.print(hoy);
			regalos = Conexiones.realizarConsulta("select * from CR.promocion promo, CR.detallepromocionext detalle "+
					" where'"+hoy+"'>=promo.fechainicio and promo.fechafinaliza>='"+hoy+
					"' and detalle.estadoRegistro=1 and promo.codpromocion=detalle.codPromocion", true);
			System.out.print(hoy);
			System.out.print(regalos);
			regalos.beforeFirst();
			while(regalos.next()){
				Regalo regalo = resultSet2Regalo(regalos);
				System.out.print(regalo);
				vregalos.addElement(regalo);
			}
		}catch(Exception ex){
			System.out.println(vregalos);
			logger.error("cargarRegalo()", ex);
		}finally{
			if (regalos!=null){
				try{
					regalos.close();
				}catch(SQLException e){
					logger.error("cargarRegalo()",e);
				}
				regalos=null;
			}
		}
		if(logger.isDebugEnabled()){
			logger.debug("cargarRegalo() - end");
		}
		return vregalos;
	}
	public static Regalo resultSet2Regalo (ResultSet rsregalos)throws BaseDeDatosExcepcion{
		try{
			int estado = rsregalos.getInt("estado");
			System.out.println(estado);
			Date fechaFin = rsregalos.getDate("fecFinRegalo");
			System.out.println(fechaFin);
			Date fechaIni = rsregalos.getDate("fecIniRegalo");
			System.out.println(fechaIni);
			int codigo = rsregalos.getInt("codRegalo");
			System.out.println(codigo);
			String nombre = rsregalos.getString("nombreRegalo");
			System.out.println(nombre);
			double cantMin = rsregalos.getDouble("cantMinRegalo");
			System.out.println(cantMin);
			String tipo = rsregalos.getString("tipoRegalo");			
			System.out.println(tipo);
			double regBs = rsregalos.getDouble("regaloBs"); 
			Regalo regalo = new Regalo(estado, fechaFin, fechaIni, codigo, nombre, cantMin, tipo, regBs);
			System.out.println(regalo );
			return regalo;
		}catch(SQLException e){
			throw new BaseDeDatosExcepcion("ERROR CARGANDO REGALOS", e);
		}
	}
}



