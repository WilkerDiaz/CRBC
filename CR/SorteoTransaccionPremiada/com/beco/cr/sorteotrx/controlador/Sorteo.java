/**
 * 
 */
package com.beco.cr.sorteotrx.controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Vector;

import com.beco.cr.sorteotrx.modelo.Promocion;


/**
 * @author aavila
 *
 */
public class Sorteo {
	
	public static ManejadorBDMySQL mBD = new ManejadorBDMySQL();
	public static Vector<String> vip;
	static int cant = 0;

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static void asignar(Vector<String> ipSAC, int codPromocion, int numdetalle){
		// TODO Apéndice de método generado automáticamente
		mBD.conectar();
		vip = new Vector<String>();
		ResultSet resultado = mBD.realizarConsulta("select ipcaja from caja where idestadocaja not in (1,5,7)");
		try {
			resultado.beforeFirst();
			while (resultado.next()){
				String ip = resultado.getString("ipcaja");
				cant++;
				ResultSet resultado2=null;
				try{
				mBD.conectar(ip);
				resultado2 = mBD.realizarConsulta("select idestadocaja from caja");
				}catch(Exception e){
					e.printStackTrace();
					
				}
				if (resultado2!=null){
					resultado2.beforeFirst();
					if(resultado2.first()){
						int idestadocaja = 0;
						idestadocaja = resultado2.getInt("idestadocaja");
						if (idestadocaja!=0) {
							Iterator<String> i = ipSAC.iterator();
							boolean x = true;
							while(i.hasNext()){
								String ipsac = (String)i.next();
								if (ip.equals(ipsac))x &= false;
							}
							if(x) vip.add(ip);//solo inserta si no es de servicio al cliente
						}
					}
				}
			}
			
			int cajapremiada = ((int) (Math.random() * cant));
			
			String ipganadora = vip.elementAt(cajapremiada).toString();
			
			mBD.conectar(ipganadora);
			String query="insert into transaccionpremiada (horaGanador, premioPorEntregar, codPromocion, numdetalle, regactualizado) " +
			"values (CURRENT_TIMESTAMP(), 1, "+codPromocion+", "+numdetalle+", 'N')";
			mBD.realizarSentencia(query);
			cant=0;
			
			vip.clear();
			Iterator<Promocion> i = PrincipalSorteo.promociones.iterator();
			while (i.hasNext()){
				Promocion p = (Promocion)i.next();
				if (p.getCodPromocion()==codPromocion)
					p.setTransacciones(p.getTransacciones()-1);
			}
			
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
			
		}
		mBD.desconectar();
	}

	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' e 'Iterator'
	* Fecha: agosto 2011
	*/
	public static void generarHoras(Time horainicio, Time horafinalizada, int codpromocion, int numdetalle) throws SQLException {
		// TODO Apéndice de método generado automáticamente
		int x = horainicio.getHours();
		int y = horafinalizada.getHours();
		int z = horainicio.getMinutes();
		int a = horafinalizada.getMinutes();
		int n = 0, error=0;
		int hora=0, minuto=0;
		DecimalFormat hym = new DecimalFormat("00");
		mBD.conectar();
		Iterator<Promocion> i = PrincipalSorteo.promociones.iterator();

		while (i.hasNext())
			n += ((Promocion)i.next()).getTransacciones();
		while (n!=0){
			String query1 = "SELECT SUBSTRING(TRUNCATE(TRUNCATE(RAND(),1)*"+((y-x)+1)+",0) + "+x+" + 100, 2, 2) AS hora" +
					", CONCAT((TRUNCATE(RAND()*5,0))* 10) AS minuto";
			ResultSet resultado= mBD.realizarConsulta(query1);
			resultado.beforeFirst();
			if (resultado.first()){
				 hora = resultado.getInt("hora");
				 minuto = resultado.getInt("minuto");
			}
			if (((hora==x && minuto > z) && x != y) 
					|| ((hora==y && minuto < a)&& x != y) 
					|| (hora > x && hora < y)
					|| (x==y && minuto > z && minuto < a)){
				
				try {
					String query = 	"INSERT INTO transaccionpremiada (horaganador, codpromocion, numdetalle, numtienda) values " +
									"(MD5(CONCAT('''', DATE_FORMAT(CURRENT_DATE(),'%Y%m%d'),'"+hym.format(hora)+minuto+"','00',''''))" +
									","+codpromocion+", "+numdetalle+", "+PrincipalSorteo.numtienda+")";
					n--;
					mBD.realizarSentencia(query);
					
				}catch(SQLException e){
					n++;
					error++;
					if (error==10) 
						n=0;
				}
			}
		}
	}//fin generarHoras
}//fin Sorteo
