package com.becoblohm.cr.sincronizador.dbbeans.servidor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
 
/**
 * Proporciona métodos que ejecutan la sentencia SQL, devuelven los objetos que 
 * representan las filas del conjunto de resultados.
 * Generated:  15/04/2004 03:49:51 PM
 */

public class GetClientes {
	ResultSet resultado;
	SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
	SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
	
	/**
	 * Constructor para una clase DBSelect.
	 */
	public GetClientes() {
		try{
			Class.forName("com.ibm.as400.access.AS400JDBCDriver");
			Connection conexionServidor;
			conexionServidor =	DriverManager.getConnection(
										"jdbc:as400://192.168.101.11",
										"",
										"");
			String sentencia = "SELECT DISTINCT MERCADEO.CABAFILIA.NOMAFI1,MERCADEO.CABAFILIA.NOMAFI2,MERCADEO.CABAFILIA.APEAFI1,MERCADEO.CABAFILIA.APEAFI2,MERCADEO.CABAFILIA.URBANI,MERCADEO.CABAFILIA.CALLAV,MERCADEO.CABAFILIA.CASEDI,MERCADEO.CABAFILIA.PISAPT,MERCADEO.CABAFILIA.CODARE,MERCADEO.CABAFILIA.HABOFC,MERCADEO.CABAFILIA.FAXCEL,MERCADEO.CABAFILIA.TIPAFI,MERCADEO.CABAFILIA.NUMTIE,MERCADEO.CABAFILIA.STATUS,MERCADEO.CABAFILIA.NUMAFIAG,MERCADEO.CABAFILIA.FECAFI,MERCADEO.CABAFILIA.ULTACT,MERCADEO.CABAFILIA.HORAFI,MERCADEO.CABAFILIA.HORACT FROM MERCADEO.CABAFILIA WHERE MERCADEO.CABAFILIA.TIPAFI <> 'J'";
			Statement sentenciaSql = conexionServidor.createStatement();
			resultado = sentenciaSql.executeQuery(sentencia);
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		//GetClienteNatural getClienteNatural = new GetClienteNatural();
	}

	/**
	 * Método getResultado
	 * 
	 * @return ResultSet
	 */
	public ResultSet getResultado(){
		return resultado;
	}	

	/**
	 * Devuelve el valor de la columna CABAFILIA_NOMAFI1 en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_NOMAFI1() throws SQLException {
		return resultado.getObject("NOMAFI1");
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_NOMAFI2 en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_NOMAFI2() throws SQLException {
		return resultado.getObject("NOMAFI2");
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_APEAFI1 en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_APEAFI1() throws SQLException {
		return resultado.getObject("APEAFI1");
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_APEAFI2 en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_APEAFI2() throws SQLException {
		return resultado.getObject("APEAFI2");
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_URBANI en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_URBANI() throws SQLException {
		return resultado.getObject("URBANI");
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_CALLAV en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_CALLAV() throws SQLException {
		return resultado.getObject("CALLAV");
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_CASEDI en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_CASEDI() throws SQLException {
		return resultado.getObject("CASEDI");
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_PISAPT en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_PISAPT() throws SQLException {
		return resultado.getObject("PISAPT");
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_CODARE en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_CODARE() throws SQLException {
		return resultado.getObject("CODARE");
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_HABOFC en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_HABOFC() throws SQLException {
		return resultado.getObject("HABOFC");
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_FAXCEL en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_FAXCEL() throws SQLException {
		return resultado.getObject("FAXCEL");
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_TIPAFI en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_TIPAFI() throws SQLException {
		return resultado.getObject("TIPAFI");
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_NUMTIE en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_NUMTIE() throws SQLException {
		return resultado.getObject("NUMTIE");
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_STATUS en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_STATUS() throws SQLException {
		return resultado.getObject("STATUS");
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_FECAFI en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_FECAFI() throws SQLException {
		String valorFecha = "";
		if((resultado.wasNull())||(resultado.getString("FECAFI") == null )||(resultado.getString("FECAFI").equals("        ")))
			valorFecha = Calendar.getInstance().getTime().toString();
		else try {
			valorFecha = formatoHora.format(formatoFecha.parse(resultado.getString("FECAFI").replace('.', '-')));
		} catch (ParseException e1) {
			valorFecha = Calendar.getInstance().getTime().toString();
		}
		return (Object)valorFecha;
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_HORAFI en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_HORAFI() throws SQLException {
		String valorHora = "";
		if((resultado.wasNull())||(resultado.getString("HORAFI") == null )||(resultado.getString("HORAFI").equals("        ")))
			valorHora = "12:00:00";
		else try {
			valorHora = formatoHora.format(formatoHora.parse(resultado.getString("HORAFI").replace('.', ':')));
		} catch (ParseException e1) {
			valorHora = "12:00:00";
		}
		return (Object)valorHora;
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_ULTACT en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_ULTACT() throws SQLException {
		String valorFecha = "";
		if((resultado.wasNull())||(resultado.getString("ULTACT") == null )||(resultado.getString("ULTACT").equals("        ")))
			valorFecha = Calendar.getInstance().getTime().toString();
		else try {
			valorFecha = formatoHora.format(formatoFecha.parse(resultado.getString("ULTACT").replace('.', '-')));
		} catch (ParseException e1) {
			valorFecha = Calendar.getInstance().getTime().toString();
		}
		return (Object)valorFecha;
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_HORACT en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_HORACT() throws SQLException {
		String valorHora = "";
		if((resultado.wasNull())||(resultado.getString("HORACT") == null )||(resultado.getString("HORACT").equals("        ")))
			valorHora = "12:00:00";
		else try {
			valorHora = formatoHora.format(formatoHora.parse(resultado.getString("HORACT").replace('.', ':')));
		} catch (ParseException e1) {
			valorHora = "12:00:00";
		}
		return (Object)valorHora;
	}

	/**
	 * Devuelve el valor de la columna CABAFILIA_NUMAFIAG en la fila representada por este objeto.
	 */
	public Object getCABAFILIA_NUMAFIAG() throws SQLException {
		return resultado.getObject("NUMAFIAG");
	}
	
}