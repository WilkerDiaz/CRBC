package com.beco.cr.actualizadorPrecios.promociones.ManejadorBD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.mediadoresbd.Conexiones;

/**
 * @author aavila
 *
 */
public class BeansSincronizador {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(BeansSincronizador.class);

	
	static {
		logger.setAdditivity(false);
	}
	/**
	 * Método syncTransacciones
	 * 		Sincroniza las transacciones (cabecera, detalle de productos y pagos) desde la Caja Registradora
	 * hacia el Servidor de tienda.
	 * @param numTransacciones - Lista de los numeros correspondientes a las transacciones de la caja que no
	 * 		han sido actualizadas, sin considerar fecha del sistema.
	 * @throws ConexionExcepcion 
	 * @throws SQLException 
	 * @throws BaseDeDatosExcepcio
	 * @modificado aavila
	 * Promociones
	 * 11/11/2008
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato de los distintos ArrayList
	*/
	public static synchronized void syncTransaccionesExt(int numTransaccion, 
			Statement loteSentenciasSrv, Statement loteSentenciasCR, 
			HashMap<String,Object> criterioClave) throws BaseDeDatosExcepcion, 
			ConexionExcepcion, SQLException{
		ResultSet regalosRegistrados=null, donacionesRegistradas = null, promocionRegistrada = null, pagoDonacion = null, transaccionPremiada = null, detalletransaccioncondicion = null;
		ArrayList <ArrayList<Object>>registros;
		ArrayList <Object>registro;
		String sentenciaSql;
		String[] xLoteSentencias;
		StringBuffer xCampos, xValores;

 				registros = new ArrayList<ArrayList<Object>>();
				xCampos = new StringBuffer("");
				xValores = new StringBuffer("");
					regalosRegistrados = com.becoblohm.cr.mediadoresbd.BeansSincronizador.getRegalosRegistrados(true, numTransaccion);
					int numColumnas;
					try {
						numColumnas = regalosRegistrados.getMetaData().getColumnCount();
						for (int i=0; i<numColumnas; i++){
							xCampos.append(regalosRegistrados.getMetaData().getColumnName(i+1));
							xValores.append("?");
							if(i+1 < numColumnas){
								xCampos.append(", ");
								xValores.append(", ");
							}
						}
						sentenciaSql = new String("insert into regalosregistrados ("+xCampos+") values ("+xValores+")");
	
						while (regalosRegistrados.next()){
							registro = new ArrayList<Object>();
							for (int i=0; i<numColumnas; i++)
								registro.add(regalosRegistrados.getObject(regalosRegistrados.getMetaData().getColumnName(i+1).toLowerCase()));
							registros.add(registro);
						}
						xLoteSentencias = Conexiones.crearLoteSentencias(regalosRegistrados.getMetaData(), sentenciaSql, registros, false, true);
						for (int i=0; i<xLoteSentencias.length; i++){
							if (xLoteSentencias!=null)
								loteSentenciasSrv.addBatch(xLoteSentencias[i]);
							//System.out.println(xLoteSentencias[i]);
						}
					
						registros = new ArrayList<ArrayList<Object>>();
						xCampos = new StringBuffer("");
						xValores = new StringBuffer("");
						donacionesRegistradas = com.becoblohm.cr.mediadoresbd.BeansSincronizador.getDonacionesRegistradas(true, numTransaccion);
						try {
							numColumnas = donacionesRegistradas.getMetaData().getColumnCount();
							for (int i=0; i<numColumnas; i++){
								xCampos.append(donacionesRegistradas.getMetaData().getColumnName(i+1));
								xValores.append("?");
								if(i+1 < numColumnas){
									xCampos.append(", ");
									xValores.append(", ");
								}
							}
							sentenciaSql = new String("insert into donacionesregistradas ("+xCampos+") values ("+xValores+")");
				
							while (donacionesRegistradas.next()){
								registro = new ArrayList<Object>();
								for (int i=0; i<numColumnas; i++)
									registro.add(donacionesRegistradas.getObject(donacionesRegistradas.getMetaData().getColumnName(i+1).toLowerCase()));
								registros.add(registro);
							}
		
							xLoteSentencias = Conexiones.crearLoteSentencias(donacionesRegistradas.getMetaData(), sentenciaSql, registros, false, true);
							for (int i=0; i<xLoteSentencias.length; i++){
								if (xLoteSentencias!=null)
									loteSentenciasSrv.addBatch(xLoteSentencias[i]);
								//System.out.println(xLoteSentencias[i]);
							}

						
						registros = new ArrayList<ArrayList<Object>>();
						xCampos = new StringBuffer("");
						xValores = new StringBuffer("");
						promocionRegistrada = com.becoblohm.cr.mediadoresbd.BeansSincronizador.getPromocionRegistrada(true, numTransaccion);
						try {
							numColumnas = promocionRegistrada.getMetaData().getColumnCount();
							for (int i=0; i<numColumnas; i++){
								xCampos.append(promocionRegistrada.getMetaData().getColumnName(i+1));
								xValores.append("?");
								if(i+1 < numColumnas){
									xCampos.append(", ");
									xValores.append(", ");
								}
							}
							sentenciaSql = new String("insert into promocionregistrada ("+xCampos+") values ("+xValores+")");
				
							while (promocionRegistrada.next()){
								registro = new ArrayList<Object>();
								for (int i=0; i<numColumnas; i++)
									registro.add(promocionRegistrada.getObject(promocionRegistrada.getMetaData().getColumnName(i+1).toLowerCase()));
								registros.add(registro);
							}
		
							xLoteSentencias = Conexiones.crearLoteSentencias(promocionRegistrada.getMetaData(), sentenciaSql, registros, false, true);
							for (int i=0; i<xLoteSentencias.length; i++){
								if (xLoteSentencias!=null)
									loteSentenciasSrv.addBatch(xLoteSentencias[i]);
								//System.out.println(xLoteSentencias[i]);
							}

						
						registros = new ArrayList<ArrayList<Object>>();
						xCampos = new StringBuffer("");
						xValores = new StringBuffer("");
						pagoDonacion = com.becoblohm.cr.mediadoresbd.BeansSincronizador.getPagoDonacion(true, numTransaccion);
						try {
							numColumnas = pagoDonacion.getMetaData().getColumnCount();
							for (int i=0; i<numColumnas; i++){
								xCampos.append(pagoDonacion.getMetaData().getColumnName(i+1));
								xValores.append("?");
								if(i+1 < numColumnas){
									xCampos.append(", ");
									xValores.append(", ");
								}
							}
							sentenciaSql = new String("insert into pagodonacion ("+xCampos+") values ("+xValores+")");
				
							while (pagoDonacion.next()){
								registro = new ArrayList<Object>();
								for (int i=0; i<numColumnas; i++)
									registro.add(pagoDonacion.getObject(pagoDonacion.getMetaData().getColumnName(i+1).toLowerCase()));
								registros.add(registro);
							}
		
							xLoteSentencias = Conexiones.crearLoteSentencias(pagoDonacion.getMetaData(), sentenciaSql, registros, false, true);
							for (int i=0; i<xLoteSentencias.length; i++){
								if (xLoteSentencias!=null)
									loteSentenciasSrv.addBatch(xLoteSentencias[i]);
								//System.out.println(xLoteSentencias[i]);
							}

						registros = new ArrayList<ArrayList<Object>>();
						xCampos = new StringBuffer("");
						xValores = new StringBuffer("");
						transaccionPremiada = com.becoblohm.cr.mediadoresbd.BeansSincronizador.getTransaccionPremiada(true, numTransaccion);
						try {
							numColumnas = transaccionPremiada.getMetaData().getColumnCount();
							for (int i=0; i<numColumnas; i++){
								xCampos.append(transaccionPremiada.getMetaData().getColumnName(i+1));
								xValores.append("?");
								if(i+1 < numColumnas){
									xCampos.append(", ");
									xValores.append(", ");
								}
							}
							sentenciaSql = new String("insert into transaccionpremiada ("+xCampos+") values ("+xValores+")");
				
							while (transaccionPremiada.next()){
								registro = new ArrayList<Object>();
								for (int i=0; i<numColumnas; i++)
									registro.add(transaccionPremiada.getObject(transaccionPremiada.getMetaData().getColumnName(i+1).toLowerCase()));
								registros.add(registro);
							}
		
							xLoteSentencias = Conexiones.crearLoteSentencias(transaccionPremiada.getMetaData(), sentenciaSql, registros, false, true);
							for (int i=0; i<xLoteSentencias.length; i++){
								if (xLoteSentencias!=null)
									loteSentenciasSrv.addBatch(xLoteSentencias[i]);
								//System.out.println(xLoteSentencias[i]);
							}
							
							registros = new ArrayList<ArrayList<Object>>();
							xCampos = new StringBuffer("");
							xValores = new StringBuffer("");
							detalletransaccioncondicion = com.becoblohm.cr.mediadoresbd.BeansSincronizador.getDetalleTransaccionCondicion(true, numTransaccion);
							try {
								numColumnas = detalletransaccioncondicion.getMetaData().getColumnCount();
								for (int i=0; i<numColumnas; i++){
									xCampos.append(detalletransaccioncondicion.getMetaData().getColumnName(i+1));
									xValores.append("?");
									if(i+1 < numColumnas){
										xCampos.append(", ");
										xValores.append(", ");
									}
								}
								sentenciaSql = new String("insert into detalletransaccioncondicion ("+xCampos+") values ("+xValores+")");
					
								while (detalletransaccioncondicion.next()){
									registro = new ArrayList<Object>();
									for (int i=0; i<numColumnas; i++)
										registro.add(detalletransaccioncondicion.getObject(detalletransaccioncondicion.getMetaData().getColumnName(i+1).toLowerCase()));
									registros.add(registro);
								}
			
								xLoteSentencias = Conexiones.crearLoteSentencias(detalletransaccioncondicion.getMetaData(), sentenciaSql, registros, false, true);
								for (int i=0; i<xLoteSentencias.length; i++){
									if (xLoteSentencias!=null)
										loteSentenciasSrv.addBatch(xLoteSentencias[i]);
									//System.out.println(xLoteSentencias[i]);
								}

						// Indico actualización de los registros de transacción en la CR
						detalletransaccioncondicion.beforeFirst();
						if (detalletransaccioncondicion.next()){
							sentenciaSql = new String("update detalletransaccioncondicion set regactualizado='" + Sesion.SI + "' where (numtienda = "+criterioClave.get("numTienda")+") and (numcajainicia = "+criterioClave.get("numCajaFinaliza")+") and (numtransaccion = "+numTransaccion+") and (fecha = '"+criterioClave.get("fecha")+"')");
							loteSentenciasCR.addBatch(sentenciaSql);
						}
						regalosRegistrados.beforeFirst();
						if (regalosRegistrados.next()){
							sentenciaSql = new String("update regalosregistrados set regactualizado='" + Sesion.SI + "' where (numtienda = "+criterioClave.get("numTienda")+") and (numcaja = "+criterioClave.get("numCajaFinaliza")+") and (numtransaccion = "+numTransaccion+") and (fechatransaccion = '"+criterioClave.get("fecha")+"')");
							loteSentenciasCR.addBatch(sentenciaSql);
						}
						transaccionPremiada.beforeFirst();
						if (transaccionPremiada.next()){
							sentenciaSql = new String("update transaccionpremiada set regactualizado='" + Sesion.SI + "' where (numtienda = "+criterioClave.get("numTienda")+") and (numcaja = "+criterioClave.get("numCajaFinaliza")+") and (numtransaccion = "+numTransaccion+") and (fecha = '"+criterioClave.get("fecha")+"')");
							loteSentenciasCR.addBatch(sentenciaSql);
						}
						promocionRegistrada.beforeFirst();
						if (promocionRegistrada.next()){
							sentenciaSql = new String("update promocionregistrada set regactualizado='" + Sesion.SI + "' where (numtienda = "+criterioClave.get("numTienda")+") and (numcaja = "+criterioClave.get("numCajaFinaliza")+") and (numtransacion = "+numTransaccion+") and (fecha = '"+criterioClave.get("fecha")+"')");
							loteSentenciasCR.addBatch(sentenciaSql);
						}
						pagoDonacion.beforeFirst();
						if (pagoDonacion.next()){
							sentenciaSql = new String("update pagodonacion set regactualizado='" + Sesion.SI + "' where (numtienda = "+criterioClave.get("numTienda")+") and (numcaja = "+criterioClave.get("numCajaFinaliza")+") and (numtransaccion = "+numTransaccion+") and (fechadonacion = '"+criterioClave.get("fecha")+"')");
							loteSentenciasCR.addBatch(sentenciaSql);
						}
						donacionesRegistradas.beforeFirst();
						if (donacionesRegistradas.next()){
							sentenciaSql = new String("update donacionesregistradas set regactualizado='" + Sesion.SI + "' where (numtienda = "+criterioClave.get("numTienda")+") and (numcaja = "+criterioClave.get("numCajaFinaliza")+") and (numtransaccion = "+numTransaccion+") and (fechaDonacion = '"+criterioClave.get("fecha")+"')");
							loteSentenciasCR.addBatch(sentenciaSql);
						}
					} finally {
						if(detalletransaccioncondicion!=null)
							detalletransaccioncondicion.close();
					}
					} finally {
						if(transaccionPremiada!=null)
							transaccionPremiada.close();
					}
					} finally {
						if(pagoDonacion!=null)
							pagoDonacion.close();
					}
					} finally {
						if(promocionRegistrada!=null)
							promocionRegistrada.close();
					}
					} finally {
						if(donacionesRegistradas!=null)
							donacionesRegistradas.close();
					}
					} finally {
						if(regalosRegistrados!=null)
							regalosRegistrados.close();
					}
					
		}
}

