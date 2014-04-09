package com.beco.sinccompieretda.controlador;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Iterator;
import java.util.Vector;

import com.beco.sinccompieretda.modelo.Tienda;




@SuppressWarnings("deprecation")
public class PrincipalSinc {
	static ConstructoraSinc c= new ConstructoraSinc();
	/*Rutas donde se guardan los Archivos*/
	static String rutapromociones = "promociones_Ext";
	static String rutadetPromocionesExt = "detPromocionesExt";
	static String rutadetPromociones = "detPromociones_Ext";
	static String rutadonacion = "donacion";
	static String rutacondicionPromocion = "condicionPromocion";
	static String rutatransaccionPremControl = "transaccionPremControl";
	
	/**/
	/*Variables comunes*/
	static Time horaInicio = new Time(0,0,0);
	static Time horaFin = new Time(23, 59, 59);
	static Date fechaFalsa=new Date(00, 00, 01);
	static int nroLineaImpresora = 38;
	
	static ManejadorBDSinc mBD = new ManejadorBDSinc();
	
	public static void main (String args[]) throws Exception{
		/*Estas horas se usan para colocarlas en aquella promociones que traen null en la hora*/
		horaInicio.setHours(0);
		horaInicio.setMinutes(0);
		horaInicio.setSeconds(0);
		horaFin.setHours(23);
		horaFin.setMinutes(59);
		horaFin.setSeconds(59);
		fechaFalsa.setYear(00);
		fechaFalsa.setMonth(00);
		fechaFalsa.setDate(01);
		/***/
		ManejadorBDSinc mBD = new ManejadorBDSinc();
		mBD.conectarOXE();	
		traertiendas();
		c.actualizarBackOffice();
		
	}
	static void traertiendas() throws Exception{
		Vector<Tienda> v = new Vector<Tienda>();
		mBD.conectarOXE();	
		try{
			String query = "select * from tiendaftp where estado like 'A' order by codigo";
			ResultSet resultado= mBD.realizarConsultaOXE(query);
			resultado.beforeFirst();
			while(resultado.next()){
				String codigo = resultado.getString("codigo");
				String ip = resultado.getString("ip");
				String directorio = resultado.getString("directorio");
				String usuario = resultado.getString("usuario");
				String password = resultado.getString("password");
				String origen = resultado.getString("origen");
				v.addElement(new Tienda(codigo, ip, directorio, usuario, password, origen));
			}
		}catch(SQLException e){e.printStackTrace();}
		Iterator<Tienda> i = v.iterator();
		while (i.hasNext()){
			Tienda t =  i.next();
			//Generando los Archivos
			c.obtenerPromociones(t.getCodigo(),t.getOrigen(), true, 0, 0);
			c.donacion(t.getCodigo(),t.getOrigen(), true, 0);
			c.detallePromocionExt(t.getCodigo(),t.getOrigen(), true, 0, 0);
			c.transaccionPremControl(t.getCodigo(),t.getOrigen(), true, 0);
			c.detallePromocion(t.getCodigo(),t.getOrigen(), true, 0);
			c.condicionPromocion(t.getCodigo(), t.getOrigen(), true, 0);
		}
		Iterator<Tienda> j = v.iterator();
		while (j.hasNext()){
			Tienda t =  j.next();
			Transferencia.putFtpFile(t.getOrigen()+rutapromociones, t.getDirectorio(), t.getIp(), 
					t.getUsuario(), t.getPassword(), rutapromociones,false);
			Transferencia.putFtpFile(t.getOrigen()+rutadetPromocionesExt, t.getDirectorio(), t.getIp(), 
					t.getUsuario(), t.getPassword(), rutadetPromocionesExt,false);
			Transferencia.putFtpFile(t.getOrigen()+rutadetPromociones, t.getDirectorio(), t.getIp(), 
					t.getUsuario(), t.getPassword(), rutadetPromociones,false);
			Transferencia.putFtpFile(t.getOrigen()+rutadonacion, t.getDirectorio(), t.getIp(), 
					t.getUsuario(), t.getPassword(), rutadonacion,false);
			Transferencia.putFtpFile(t.getOrigen()+rutacondicionPromocion, t.getDirectorio(), t.getIp(), 
					t.getUsuario(), t.getPassword(), rutacondicionPromocion,false);
			Transferencia.putFtpFile(t.getOrigen()+rutatransaccionPremControl, t.getDirectorio(), t.getIp(), 
					t.getUsuario(), t.getPassword(), rutatransaccionPremControl,false);
		}
		//mBD.realizarSentenciaOXE("update XX_PromProductoDetalleCaja set regactualizado='Y'");
	}
}