package com.beco.cr.sorteotrx.controlador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import java.util.prefs.Preferences;

import com.beco.cr.sorteotrx.modelo.Ganador;
import com.beco.cr.sorteotrx.modelo.Promocion;


public class PrincipalSorteo {

	
	public static Date d = new Date();
	static SimpleDateFormat fechaActual = new SimpleDateFormat("yyyy-MM-dd");
	static String fechaActualString = fechaActual.format(d);
	static ManejadorBDMySQL mBD = new ManejadorBDMySQL();
	public static int i, n = 0;
	public static long codformadepago = 0;
	static Vector<Promocion> promociones = new Vector<Promocion>();
	static String numtienda="";
	Preferences preferencesTopNode;
	
	/**
	 * @param args
	 * @param ipSAC 
	 * @throws SQLException 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' e 'Iterator'
	* Fecha: agosto 2011
	*/
	public static void main(String[] args) throws SQLException {
		// TODO Apéndice de método generado automáticamente
		Vector<String> vipSAC = new Vector<String>();
		int codpromocion = 0, numdetalle = 0;
		try {
			Class.forName("org.gjt.mm.mysql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		mBD.conectar();
		ResultSet resultado = mBD.realizarConsulta("select numtienda from caja");
		resultado.beforeFirst();
		if (resultado.first()) numtienda=resultado.getString("numtienda");
		String query = "select a.tipopromocion, a.horainicio, a.horafinaliza, a.codpromocion, b.numdetalle " 
			+" from promocion a inner join detallepromocionext b on a.codpromocion=b.codpromocion" 
			+" where ((fechainicio < '" +fechaActualString +"' and fechafinaliza > '" +fechaActualString+"') " 
			+"or (fechainicio = '" +fechaActualString +"' and fechafinaliza > '"+fechaActualString+"') " 
			+"or (fechainicio < '" +fechaActualString +"' and fechafinaliza = '" +fechaActualString+ "'))" 
			+"or (fechainicio = '" +fechaActualString +"' and fechafinaliza = '" +fechaActualString+"') and b.estadoRegistro = 'A'";
		resultado = mBD.realizarConsulta(query);
		resultado.beforeFirst();
		while (resultado.next()){
			String tipo = resultado.getString("tipopromocion");
			if (tipo.equals("A")) {
				Time horainicio = resultado.getTime("horainicio");
				Time horafinalizada = resultado.getTime("horafinaliza");
				codpromocion = resultado.getInt("codpromocion");
				numdetalle = resultado.getInt("numdetalle");
				cargarValores(codpromocion);
				Sorteo.generarHoras(horainicio, horafinalizada, codpromocion, numdetalle);
			}
		}
		mBD.desconectar();
		try {
			File f = new File( args[0] );
			BufferedReader entrada = new BufferedReader( new FileReader( f ) );	 
			 	if ( f.exists() ){
			 		while (!entrada.readLine().equals("fin")){
						String ipsac;					
						ipsac = entrada.readLine();
						vipSAC.add(ipsac);
			 		}
				}
			} catch (IOException e) {
				e.printStackTrace();
			
			}
			Iterator<Promocion> i = promociones.iterator();
			while (i.hasNext()){
				Promocion p = (Promocion)i.next();
				n += p.getTransacciones();
			
			}
		while(n>0){
			if (proximahora(new Time(Calendar.HOUR_OF_DAY, Calendar.MINUTE/10, 00))!=null){
				Sorteo.asignar(vipSAC, Ganador.getCodigo(), Ganador.getDetalle());
			
				n--;
				verificarCantidadRepartida(codpromocion, numdetalle);
			
			}else {
				try {
					Thread.sleep(300000);
					verificarCantidadRepartida(codpromocion, numdetalle);
			
					if(proximahora(new Time(Calendar.HOUR_OF_DAY, Calendar.MINUTE/10, 00))!=null)Sorteo.asignar(vipSAC, codpromocion, numdetalle);
				} catch (InterruptedException e) {e.printStackTrace();}
			}
			
		}
		terminarSorteo();
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' e 'Iterator'
	* Fecha: agosto 2011
	*/
	private static void verificarCantidadRepartida(int codpromocion, int numdetalle) {
		// TODO Apéndice de método generado automáticamente
		double monto=0.0;//, montoimpuesto=0.0;
		String query = "SELECT SUM(a.monto) FROM pagodetransaccion a INNER JOIN transaccionpremiada b ON a.numtransaccion=b.numTransaccion " +
				"AND a.numtienda=b.numTienda AND a.numcaja=b.numCaja AND a.fecha=b.fecha WHERE a.codformadepago = 9999999999  " +
				"AND b.codpromocion="+codpromocion+" AND a.fecha=CURRENT_DATE";
		ResultSet resultado = mBD.realizarConsulta(query);
		
		try {
			while(resultado.next()){
				monto += resultado.getDouble("monto");
				//montoimpuesto += resultado.getDouble("montoimpuesto");
			}
			Iterator<Promocion> i = promociones.iterator();
			while (i.hasNext()){
				Promocion p = (Promocion)i.next();
				if (p.getCodPromocion()==codpromocion)
					if (p.getBs()<=(monto/*montobase+montoimpuesto*/))
						terminarSorteo(codpromocion);
			}
			verificarVigencia(codpromocion);
			quedanHoras();
		} catch (SQLException e) {e.printStackTrace();}	
	}

	private static void verificarVigencia(int codpromocion) throws SQLException {
		// TODO Apéndice de método generado automáticamente
		String query2 = "SELECT * FROM promocion WHERE tipopromocion='A' AND " +
		"(fechafinaliza>CURRENT_DATE or horafinaliza>CURRENT_TIME) AND codPromocion="+codpromocion;
		ResultSet resultado = mBD.realizarConsulta(query2);
		resultado.beforeFirst();
		if (!resultado.next())terminarSorteo();
	}

	private static void terminarSorteo(int codpromocion) {
		// TODO Apéndice de método generado automáticamente
		try {
			ResultSet resultado = mBD.realizarConsulta("SELECT * FROM transaccionpremiada where numCaja is null and codpromocion<>"+codpromocion);
			resultado.beforeFirst();
			if (!resultado.first())terminarSorteo();
			mBD.realizarSentencia("delete FROM transaccionpremiada where numCaja is null and codpromocion<>"+codpromocion);
		} catch (SQLException e) {e.printStackTrace();}
	}

	private static void terminarSorteo() {
		// TODO Apéndice de método generado automáticamente
		mBD.desconectar();
		System.exit(0);
	}

	
	private static Ganador proximahora(Time horapremiada) {
		// TODO Apéndice de método generado automáticamente
		try {
			mBD.conectar();
			Calendar.getInstance();
			String query = "select horaganador, codPromocion, numDetalle, premioPorEntregar from transaccionpremiada " +
					"where horaganador = " +
					"MD5(CONCAT('''', DATE_FORMAT(CURRENT_DATE(),'%Y%m%d')" +
					", SUBSTRING( DATE_FORMAT(CURRENT_TIMESTAMP(), '%H%i'), 1, 3), '000',''''))";
			ResultSet resultado = mBD.realizarConsulta(query);
			
 			resultado.beforeFirst();
			if (resultado.next()){
				int codigo = resultado.getInt("codPromocion");
				int detalle = resultado.getInt("numDetalle");
				String horaGanador = resultado.getString("horaganador");
				//resultado.updateInt(2, 1);
				//resultado.deleteRow(); //porque da error
				//resultado.updateString("premiado", "S");
				//resultado.updateRow();
				mBD.realizarSentencia("delete from transaccionpremiada where horaganador = '"+horaGanador+"'");
				return new Ganador (new Time(Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND), codigo, detalle);
			}
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}
	
	public static void cargarValores(int codPromocion) {
		// TODO Apéndice de método generado automáticamente
		String query = "select NROTRANSACCIONESXDIA, MAXIPREMBSXDIA, codPromocion " +
				"from transaccionpremcontrol where codpromocion="+codPromocion;
		
		try {
			ResultSet resultado = mBD.realizarConsulta(query);
			resultado.beforeFirst();
			while (resultado.next()){
				int transacciones = resultado.getInt("NROTRANSACCIONESXDIA");
				double bs = resultado.getDouble("MAXIPREMBSXDIA");
				int codigo = resultado.getInt("codPromocion");
				promociones.add(new Promocion(transacciones, bs, codigo));
			}
		query = "Select codformadepago from formadepago where nombre like '%Transaccion Premiada%'";
		ResultSet resultado2 = mBD.realizarConsulta(query);
		if (resultado2.next())
			codformadepago = resultado2.getLong("codformadepago");
		} catch (SQLException e) {e.printStackTrace();}
	}
	private static void quedanHoras() throws SQLException {
		// TODO Apéndice de método generado automáticamente
		String query2 = "SELECT * FROM transaccionpremiada where numCaja is null";
		ResultSet resultado = mBD.realizarConsulta(query2);
		resultado.beforeFirst();
		if (!resultado.next())terminarSorteo();
	}

}
