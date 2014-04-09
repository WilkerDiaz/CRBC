package com.beco.sinccompieretda.controlador;
import java.sql.*;
import java.util.Properties;

public class ManejadorBDSinc {
	public Connection conexionOXE;
	public final String userOXE = "comp350" ;
	public final String passwordOXE = "comp350";
	public final String IPOXE = "192.168.1.23"; 

	public final String urlOXE = "jdbc:oracle:thin:@192.168.1.23:1521:compTest";
	
	/**
	 * Método conectar.
	 * 		Realiza la conexion a la base de Datos.
	 * @throws ConexionExcepcion
	 */
	
	public void conectarOXE(){
		try {
			// Creamos la nueva conexion
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Properties q = new Properties();
			q.put("user", userOXE);
			q.put("password", passwordOXE);
			q.put("translate binary", "true");
			conexionOXE = DriverManager.getConnection(urlOXE, q);
			
		} catch (Exception e) {
			e.printStackTrace();
			conexionOXE=null;
		}
	}
	public synchronized void ejecutarLoteSentenciasOXE(Statement consultas) {
		
		try{
			conectarOXE();
			consultas.executeBatch();
		}catch(SQLException e){
			e.printStackTrace();
			try{
				conexionOXE.rollback();
				conexionOXE.setAutoCommit(true);
			}catch(SQLException e1){
				
			}

		}
		finally{desconectarOXE();}
	}
	

	/**
	 * Método desconectar.
	 * 		Realiza la desconexion de la base de Datos.
	 * @throws ConexionExcepcion
	 */
	public  void desconectarOXE() {
		try {
			conexionOXE.close();
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
	public  int realizarSentenciaOXE(String sentenciaSql) throws SQLException {
		// Realizamos la operacion
		Statement sentenciaOXE = null;
		try {
			sentenciaOXE = conexionOXE.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException exSQL) {
		}
		int result=sentenciaOXE.executeUpdate(sentenciaSql);
		try{	
		sentenciaOXE.close();
		}catch(SQLException e){e.printStackTrace();}
		return result;
	}

	/**
	 * Método realizarConsulta.
	 * 		Ejecuta una sentencia SQL de consulta de registros (Selects) con la capacidad de desplazamiento
	 * y actualización o no de los datos resultantes.
	 */

	public  ResultSet realizarConsultaOXE(String sentenciaSQL) {
		ResultSet resultado = null;
		Statement sentenciaOXE = null;

		try {						
			sentenciaOXE = conexionOXE.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultado = sentenciaOXE.executeQuery(sentenciaSQL);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e1) { 
			e1.printStackTrace();
		}
		return resultado;
	}
	public  ResultSet realizarConsultaOXE(String sentenciaSQL, Statement sentenciaOXE) {
		ResultSet resultado = null;

		try {						
			//sentenciaOXE = conexionOXE.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultado = sentenciaOXE.executeQuery(sentenciaSQL);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e1) { 
			e1.printStackTrace();
		}
		return resultado;
	}
	public Statement getStatement() throws SQLException{
		return conexionOXE.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
	}
}//Fin de clase