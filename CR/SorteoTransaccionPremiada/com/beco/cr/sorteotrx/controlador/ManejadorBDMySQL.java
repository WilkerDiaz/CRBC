	package com.beco.cr.sorteotrx.controlador;
	import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.epa.preferencesproxy.EPAPreferenceProxy;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;

/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se comentó una variable sin uso
* Fecha: agosto 2011
*/
public class ManejadorBDMySQL {


		public Connection conexionMySQL;
		public Connection conexionMySQL2;
		
		public String user = "" ;
		public String password = "";
		//Preferences preferencesTopNode;
		//public final String IP = ""; 
		//private EPAPreferenceProxy preferenciasColasCR = null;

		public String url = "";

		
		public ManejadorBDMySQL(){
			EPAPreferenceProxy preferenciasColasCR = new EPAPreferenceProxy("proyectocr");
			try{
			user = preferenciasColasCR.getConfigStringForParameter("colasCR","dbUsuario");
			password = preferenciasColasCR.getConfigStringForParameter("colasCR","dbClave");
			url = preferenciasColasCR.getConfigStringForParameter("colasCR","dbUrlLocal");
			}
			catch (NoSuchNodeException e){
				e.printStackTrace();
				}
			catch (UnidentifiedPreferenceException e){
				e.printStackTrace();
				}
		}
		/**
		 * Método conectar.
		 * 		Realiza la conexion a la base de Datos.
		 * @throws ConexionExcepcion
		 */
		
		public void conectar(){
			try {
				// Creamos la nueva conexion
				Properties q = new Properties();
				q.put("user", user);
				q.put("password", password);
				q.put("translate binary", "true");
				conexionMySQL = DriverManager.getConnection(url, q);
				
			} catch (Exception e) {
				e.printStackTrace();
				conexionMySQL=null;
			}
		}
		public void conectar(String ip){
			try {
				// Creamos la nueva conexion
				Class.forName("org.gjt.mm.mysql.Driver");
				Properties q = new Properties();
				q.put("user", user);
				q.put("password", password);
				q.put("translate binary", "true");
				conexionMySQL = DriverManager.getConnection("jdbc:mysql://"+ip+"/CR", q);
				
			} catch (Exception e) {
				e.printStackTrace();
				conexionMySQL=null;
			}
		}
		public synchronized void ejecutarLoteSentencias(Statement consultas) {
			
			try{
				conectar();
				consultas.executeBatch();
			}catch(SQLException e){
				e.printStackTrace();
				try{
					conexionMySQL.rollback();
					conexionMySQL.setAutoCommit(true);
				}catch(SQLException e1){
					
				}

			}
			finally{desconectar();}
		}
		/**
		 * Método desconectar.
		 * 		Realiza la desconexion de la base de Datos.
		 * @throws ConexionExcepcion
		 */
		public  void desconectar() {
			try {
				conexionMySQL.close();
			} catch (Exception ex) {
				// No existian conexiones abiertas
			}
		}	
		public  void desconectar(String ip) {
			try {
				DriverManager.getConnection("jdbc:mysql://"+ip+"/CR", new Properties()).close();
			} catch (Exception ex) {
				// No existian conexiones abiertas
			}
		}
		/**
		 * Método realizarSentencia.
		 * 		Realiza las sentencias de insersion y actualizacion en la base de datos
		 * @param String sentenciaSql 
		 * @return int negativo = error
		 * 				positivo = correcto		
		 */
		public  int realizarSentencia(String sentenciaSql) throws SQLException {
			// Realizamos la operacion
			Statement sentencia = null;
			try {
				sentencia = conexionMySQL.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			} catch (SQLException exSQL) {
			}	
			return sentencia.executeUpdate(sentenciaSql);
		}

		/**
		 * Método realizarConsulta.
		 * 		Ejecuta una sentencia SQL de consulta de registros (Selects) con la capacidad de desplazamiento
		 * y actualización o no de los datos resultantes.
		 */

		public  ResultSet realizarConsulta(String sentenciaSQL) {
			ResultSet resultado = null;
			Statement sentencia = null;

			try {						
				sentencia = conexionMySQL.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				resultado = sentencia.executeQuery(sentenciaSQL);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e1) { 
				e1.printStackTrace();
			}
			return resultado;
		}
	}//Fin de clase
