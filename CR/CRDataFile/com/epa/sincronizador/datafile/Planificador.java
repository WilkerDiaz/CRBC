package com.epa.sincronizador.datafile;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Vector;


public class Planificador {


    /**
     * Función que busca en la base de datos las ultimas fechas en que 
     * fue actualizada la entidad dada.
     * @param conn
     * @param entidadDestino
     * @return Vector<Date> con las fechas de actualizacion de la entidad
     */
	public static Vector<Date> getUltimasActualizaciones(Connection conn,
			String entidadDestino) {
		Vector<Date> ultimasActualizaciones = new Vector<Date>();
		try {
			String consulta = "SELECT DATE(actualizacion) fecha FROM CR.caja c " +
					" JOIN CR.planificador p USING(numcaja) WHERE " +
					"entidad=? GROUP BY DATE(actualizacion)";
			PreparedStatement pstmt = conn.prepareStatement(consulta);
			pstmt.setString(1, entidadDestino+".txt.gz");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				ultimasActualizaciones.add(rs.getDate("fecha"));
			}
		} catch (SQLException e) {
			//AGREGARLOGJESSI
			e.printStackTrace();
		}

		return ultimasActualizaciones;
	}
	
    /**
     * Función que busca en la base de datos las si las cajas 
     * en la entidad caja tambien estan en planificador
     * @param conn
     * @param entidadDestino
     * @return boolean indicando si hay cajas que no estan en planificador
     */
	public static boolean estanCajasEnPlanificador(Connection conn,
			String entidadDestino) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"SELECT COUNT(*)=COUNT(entidad) FROM CR.caja c " +
					"LEFT OUTER JOIN CR.planificador p ON " +
					"c.numcaja = p.numcaja AND entidad=?");  
			pstmt.setString(1, entidadDestino+".txt.gz");
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				return(rs.getBoolean(1));
			}
		} catch (SQLException e) {
			//AGREGARLOGJESSI
			e.printStackTrace();
		}
		return false;
	}

}
