
package com.beco.sinccompieretda.controlador;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Vector;

import com.beco.sinccompieretda.modelo.Dupla;
import com.beco.sinccompieretda.modelo.Tienda;

@SuppressWarnings("deprecation")
public class TransferenciaInmediata {
	
	static String rutapromociones = "transfInmedPromoCR_Ext";
	static String rutadetPromocionesExt = "transfInmedPromTdaExt";
	static String rutadetPromociones = "transfInmedPromTda_Ext";
	static String rutadonacion = "transfInmedDonacion";
	static String rutacondicionPromocion = "transfInmedCondPromocion";
	static String rutatransaccionPremControl = "transfInmedTranPremControl";
	static Time horaInicio = new Time(0,0,0);
	static Time horaFin = new Time(23, 59, 59);
	static Date fechaFalsa=new Date(00, 00, 01);
	static int nroLineaImpresora = 40;
	private static int codigo=0, detalle=0;
	private static String tienda = "00";
	static ConstructoraSinc c= new ConstructoraSinc();
	static ManejadorBDSinc mBD = new ManejadorBDSinc();
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static void sincronizarProductoDePda(String codprod, String tiendaASinc)throws Exception{

		detalle = Integer.parseInt(codprod);
		tienda = tiendaASinc;
		Tienda t= traerTienda(tienda);
		
		//Extensión para PDA
		Vector<Dupla> promocionesAActualizar = c.obtenerPromocionesDeProducto(TransferenciaInmediata.getDetalle());
		int tamVector = promocionesAActualizar.size();
		for(int i = 0; i < tamVector; ++i){
			Dupla prom = (Dupla)promocionesAActualizar.elementAt(i);
			c.obtenerPromociones(t.getCodigo(), t.getOrigen(), false, prom.second(),0);
			c.detallePromocion(t.getCodigo(), t.getOrigen(), false, prom.second());
			c.detallePromocionExt(t.getCodigo(), t.getOrigen(), false, prom.second(),prom.first());
			Transferencia.putFtpFile(t.getOrigen()+rutapromociones, t.getDirectorio(), t.getIp(), 
					t.getUsuario(), t.getPassword(), rutapromociones,true);
			Transferencia.putFtpFile(t.getOrigen()+rutadetPromociones, t.getDirectorio(), t.getIp(), 
							t.getUsuario(), t.getPassword(), rutadetPromociones,true);
			Transferencia.putFtpFile(t.getOrigen()+rutadetPromocionesExt, t.getDirectorio(), t.getIp(), 
									t.getUsuario(), t.getPassword(), rutadetPromocionesExt,true);
		}
		
	}
	
	public static void sincronizarPromocionDeCompiere(String codProm,String tiendaASinc,String detalleASinc)throws Exception{

		codigo = Integer.parseInt(codProm);
		detalle = Integer.parseInt(detalleASinc);
		tienda = tiendaASinc;
		Tienda t= traerTienda(tienda);
		
		c.obtenerPromociones(t.getCodigo(),t.getOrigen(), false, TransferenciaInmediata.getCodigo(), TransferenciaInmediata.getDetalle());
		c.detallePromocion(t.getCodigo(), t.getOrigen(), false, TransferenciaInmediata.getCodigo());
 		c.detallePromocionExt(t.getCodigo(), t.getOrigen(), false, TransferenciaInmediata.getCodigo(), TransferenciaInmediata.getDetalle());
		c.donacion(t.getCodigo(), t.getOrigen(), false, TransferenciaInmediata.getCodigo());
		c.transaccionPremControl(t.getCodigo(), t.getOrigen(), false, TransferenciaInmediata.getCodigo());
		c.condicionPromocion(t.getCodigo(), t.getOrigen(), false, TransferenciaInmediata.getCodigo());
		Transferencia.putFtpFile(t.getOrigen()+rutapromociones, t.getDirectorio(), t.getIp(), 
				t.getUsuario(), t.getPassword(), rutapromociones,true);
		Transferencia.putFtpFile(t.getOrigen()+rutadetPromocionesExt, t.getDirectorio(), t.getIp(), 
				t.getUsuario(), t.getPassword(), rutadetPromocionesExt,true);
		Transferencia.putFtpFile(t.getOrigen()+rutadetPromociones, t.getDirectorio(), t.getIp(), 
				t.getUsuario(), t.getPassword(), rutadetPromociones,true);
		Transferencia.putFtpFile(t.getOrigen()+rutadonacion, t.getDirectorio(), t.getIp(), 
				t.getUsuario(), t.getPassword(), rutadonacion,true);
		Transferencia.putFtpFile(t.getOrigen()+rutacondicionPromocion, t.getDirectorio(), t.getIp(), 
				t.getUsuario(), t.getPassword(), rutacondicionPromocion,true);
		Transferencia.putFtpFile(t.getOrigen()+rutatransaccionPremControl, t.getDirectorio(), t.getIp(), 
				t.getUsuario(), t.getPassword(), rutatransaccionPremControl,true);
	}
	
	/*
	public static void main (String args[]) throws Exception{
		if(args.length != 3){
			System.out.println("Error, solo hay " +args.length+" argumentos");
			System.exit(1);
		}
		codigo = Integer.parseInt(args[0]);
		detalle = Integer.parseInt(args[1]);
		tienda = args[2];
		Tienda t= traerTienda(tienda);
		
		//Extensión para PDA
		Vector promocionesAActualizar = c.obtenerPromocionesDeProducto(TransferenciaInmediata.getDetalle());
		int tamVector = promocionesAActualizar.size();
		for(int i = 0; i < tamVector; ++i){
			Dupla prom = (Dupla)promocionesAActualizar.elementAt(i);
			c.obtenerPromociones(t.getCodigo(), t.getOrigen(), false, prom.first(),0);
			c.detallePromocion(t.getCodigo(), t.getOrigen(), false, prom.first());
			c.detallePromocionExt(t.getCodigo(), t.getOrigen(), false, prom.first(),prom.second());
			Transferencia.putFtpFile(t.getOrigen()+rutapromociones, t.getDirectorio(), t.getIp(), 
					t.getUsuario(), t.getPassword(), rutapromociones,true);
			Transferencia.putFtpFile(t.getOrigen()+rutadetPromociones, t.getDirectorio(), t.getIp(), 
							t.getUsuario(), t.getPassword(), rutadetPromociones,true);
			Transferencia.putFtpFile(t.getOrigen()+rutadetPromocionesExt, t.getDirectorio(), t.getIp(), 
									t.getUsuario(), t.getPassword(), rutadetPromocionesExt,true);
		}
		
		
		
		c.obtenerPromociones(t.getCodigo(),t.getOrigen(), false, TransferenciaInmediata.getCodigo(), TransferenciaInmediata.getDetalle());
		c.detallePromocion(t.getCodigo(), t.getOrigen(), false, TransferenciaInmediata.getCodigo());
 		c.detallePromocionExt(t.getCodigo(), t.getOrigen(), false, TransferenciaInmediata.getCodigo(), TransferenciaInmediata.getDetalle());
		c.donacion(t.getCodigo(), t.getOrigen(), false, TransferenciaInmediata.getCodigo());
		c.transaccionPremControl(t.getCodigo(), t.getOrigen(), false, TransferenciaInmediata.getCodigo());
		c.condicionPromocion(t.getCodigo(), t.getOrigen(), false, TransferenciaInmediata.getCodigo());
		Transferencia.putFtpFile(t.getOrigen()+rutapromociones, t.getDirectorio(), t.getIp(), 
				t.getUsuario(), t.getPassword(), rutapromociones,true);
		Transferencia.putFtpFile(t.getOrigen()+rutadetPromocionesExt, t.getDirectorio(), t.getIp(), 
				t.getUsuario(), t.getPassword(), rutadetPromocionesExt,true);
		Transferencia.putFtpFile(t.getOrigen()+rutadetPromociones, t.getDirectorio(), t.getIp(), 
				t.getUsuario(), t.getPassword(), rutadetPromociones,true);
		Transferencia.putFtpFile(t.getOrigen()+rutadonacion, t.getDirectorio(), t.getIp(), 
				t.getUsuario(), t.getPassword(), rutadonacion,true);
		Transferencia.putFtpFile(t.getOrigen()+rutacondicionPromocion, t.getDirectorio(), t.getIp(), 
				t.getUsuario(), t.getPassword(), rutacondicionPromocion,true);
		Transferencia.putFtpFile(t.getOrigen()+rutatransaccionPremControl, t.getDirectorio(), t.getIp(), 
				t.getUsuario(), t.getPassword(), rutatransaccionPremControl,true);
	}
	*/
	
	public static int getCodigo() {
		return codigo;
	}

	public static void setCodigo(int codigo) {
		TransferenciaInmediata.codigo = codigo;
	}

	public static int getDetalle() {
		return detalle;
	}

	public static void setDetalle(int detalle) {
		TransferenciaInmediata.detalle = detalle;
	}

	public static String getTienda() {
		return tienda;
	}

	public static void setTienda(String tienda) {
		TransferenciaInmediata.tienda = tienda;
	}

	static Tienda traerTienda(String tienda){
		mBD.conectarOXE();	
		try{
			String query = "select * from tiendaftp where codigo = "+tienda;
			ResultSet resultado= mBD.realizarConsultaOXE(query);
			resultado.beforeFirst();
			if(resultado.next()){
				String codigo = resultado.getString("codigo");
				String ip = resultado.getString("ip");
				String directorio = resultado.getString("directorio");
				String usuario = resultado.getString("usuario");
				String password = resultado.getString("password");
				String origen = resultado.getString("origen");
				return new Tienda(codigo, ip, directorio, usuario, password, origen);
			}
			return null;
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
}
