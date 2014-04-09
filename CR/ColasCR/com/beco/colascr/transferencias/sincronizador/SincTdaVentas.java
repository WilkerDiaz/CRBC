/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.colascr.transferencias.sincronizador
 * Programa   : SincTdaVentas.java
 * Creado por : Ileana Rojas, Gabriel Martinelli
 * Creado en  : 21/02/2005 04:09:59 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 
 * Fecha       : 
 * Analista    : 
 * Descripción :
 * =============================================================================
 */
package com.beco.colascr.transferencias.sincronizador;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimerTask;
import java.util.Vector;

import com.beco.colascr.transferencias.comunicacionbd.BeansSincronizador;
import com.beco.colascr.transferencias.comunicacionbd.Conexiones;
import com.beco.colascr.transferencias.configuracion.PoliticaTarea;
import com.beco.colascr.transferencias.configuracion.Sesion;
import com.beco.colascr.transferencias.excepciones.BaseDeDatosExcepcion;
import com.beco.colascr.transferencias.excepciones.ConexionExcepcion;

/**
 * Descripción:
 * 
 */

public class SincTdaVentas extends TimerTask {
	/**
	 * Logger for this class
	 */
	
	public static Sincronizador sincronizador = null;
	
	/**
	 * Constructor para SincronizarServidorCaja.java
	 *
	 * 
	 */
	public SincTdaVentas(Sincronizador sync) {
		super();
		sincronizador = sync;
	}


	/**
	 * Método run
	 * 
	 */
	public void run() {
		// Verificamos la hora. Ejecutamos la sincronizacion si es antes de las 11 pm
		Calendar fechaAct = Calendar.getInstance();
		Calendar fechaMinima = Calendar.getInstance();
		Calendar fechaTope = Calendar.getInstance();
		fechaTope.set(fechaTope.get(Calendar.YEAR), fechaTope.get(Calendar.MONTH), fechaTope.get(Calendar.DATE),Integer.parseInt(Sesion.getLimiteSupSinc().substring(0,2)),Integer.parseInt(Sesion.getLimiteSupSinc().substring(2)),0);
		fechaMinima.set(fechaMinima.get(Calendar.YEAR), fechaMinima.get(Calendar.MONTH), fechaMinima.get(Calendar.DATE),Integer.parseInt(Sesion.getLimiteInfSinc().substring(0,2)),Integer.parseInt(Sesion.getLimiteInfSinc().substring(2)),0);
		if (fechaAct.before(fechaTope) && fechaAct.after(fechaMinima)) {
			long inicioTotal = System.currentTimeMillis();
			try {
				Sesion.getFrecuenciaSincVentas().setEstadoTarea(PoliticaTarea.INICIADA);
				
				System.out.println("\n ***** Iniciando Ciclo de Sincronización ServTda -> ServCentral *****");
				
				System.out.print(" Sincronizando Entidad CR.transaccion  ----->");
				SincUtil.getInstance().syncTransacciones();
				//Generamos el Archivo Detail de las transacciones del día actual
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				crearDetail(df.format(Sesion.getFechaSistema()));
				System.out.println();
				
				System.out.print(" Sincronizando Entidad CR.devolucionventa  ----->");
				BeansSincronizador.syncDevolucionVenta();
				System.out.println();
				
				System.out.print(" Sincronizamos los Estados, Urbanizaciones y Ciudades  ----->");
				BeansSincronizador.syncEntidadesATCM(Sesion.SINC_SERVCENTRAL);
				System.out.println();

				System.out.print(" Sincronizamos los Afiliados que se encuentren en Serivios sin Sincronizar  ----->");
				try {
					BeansSincronizador.syncAfiliadosNuevos();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				System.out.println();

				System.out.println(" Obtieniendo servicios a sincronizar ---->");
				Vector<Vector<Integer>> servicios = getServiciosNoSincronizados();
				
				System.out.print(" Sincronizando Entidad CR.servicio  ----->");
				BeansSincronizador.syncServicios();
				System.out.println();
				
				System.out.println(" Eliminando detalles de servicio ya existentes");
				BeansSincronizador.deleteDetallesServicio(servicios);
				
				System.out.print(" Sincronizando Entidad CR.detalleservicio  ----->");
				BeansSincronizador.syncDetallesServicios();
				System.out.println();
	
				System.out.print(" Sincronizando Entidad CR.abono  ----->");
				SincUtil.getInstance().syncAbonos();
				System.out.println();
				
				System.out.print(" Sincronizando Entidad CR.anulacionabono  ----->");
				SincUtil.getInstance().syncAnulacionAbonos();
				System.out.println();
				
				System.out.print(" Sincronizando Entidad CR.usuario  ----->");
				BeansSincronizador.syncUsuario(Sesion.SINC_SERVCENTRAL);
				System.out.println();
	
				System.out.print(" Sincronizando Entidades de Bono Regalo Electronico  ----->");
				BeansSincronizador.syncTransaccionesBR();
				System.out.println();

				System.out.print(" Sincronizando Entidad BR_COMPROBANTEFISCAL  ----->");
				BeansSincronizador.syncComprobanteFiscalBR();
				System.out.println();

			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Throwable t) {
				t.printStackTrace();
			} finally {
				Conexiones.cerrarConexionesSync();
			}		
			long finTotal = System.currentTimeMillis();
			System.out.println("\n ***** Finalizado Ciclo de Sincronización ServTda -> ServCentral. Tiempo Total: "  + (finTotal - inicioTotal) + " MiliSegs. *****");
			Sesion.getFrecuenciaSincVentas().setDuracionSinc(finTotal-inicioTotal);
			Sesion.getFrecuenciaSincVentas().setEstadoTarea(PoliticaTarea.FINALIZANDO);
		} else {
			System.out.println("No es hora de Sincronizar. Son las " + fechaAct.getTime());
			Sesion.getFrecuenciaSincVentas().setEstadoTarea(PoliticaTarea.FINALIZANDO);
		}
	}


	/**
	 * Método crearDetail
	 * 
	 * @param string
	 * void
	 */
	private void crearDetail(String fechaAct) {
		BufferedWriter out = null;
		FileOutputStream farchivoOut;
		DecimalFormat dfCaja = new DecimalFormat("00");
		DecimalFormat dfCorrelativo = new DecimalFormat("000");
		SimpleDateFormat dfFecha = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat dfHora = new SimpleDateFormat("HHmm");
		DecimalFormat dfTrx = new DecimalFormat("000000");
		DecimalFormat dfProd = new DecimalFormat("00000000000000");
		DecimalFormat dfPrec = new DecimalFormat("00000000.00");
		DecimalFormat dfCant = new DecimalFormat("0000");
		String sentenciaSql = "select t.numcajafinaliza, d.correlativoitem, cv.tiptra, t.fecha, " +
							"t.horafinaliza, t.numtransaccion, d.codproducto, d.precioregular, " +
							"d.preciofinal+d.desctoempleado as pfinal, d.cantidad, " +
							"t.numtienda from transaccion t, detalletransaccion d, condvtatiptra cv, usuario u where " +
							"t.numtienda=d.numtienda and t.numcajafinaliza=d.numcajafinaliza and t.fecha=d.fecha and " + 
							"d.numtransaccion=t.numtransaccion and d.codcondicionventa=cv.condventa and t.fecha='" + fechaAct + "' " +
							"and t.estadotransaccion='" + Sesion.FINALIZADA + "' and t.tipotransaccion='" + Sesion.VENTA + "' and " +
							"u.numficha=t.codcajero and u.codperfil<>'4'";
		ResultSet result;
		try {
			result = Conexiones.obtenerTransacciones(sentenciaSql, true);
		} catch (ConexionExcepcion e2) {
			e2.printStackTrace();
			result = null;
		} catch (BaseDeDatosExcepcion e2) {
			e2.printStackTrace();
			result = null;
		}
		if (result!=null) {
			try {
				/* Archivo de Salida */
				farchivoOut = new FileOutputStream(Sesion.getPathArchivos() + "detail.txt");
				out = new BufferedWriter(new OutputStreamWriter(farchivoOut));
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				result.beforeFirst();
				while(result.next()) {
					String caja = dfCaja.format(result.getInt("t.numcajafinaliza"));
					String cajero = "0000";
					String corTra = dfCorrelativo.format(result.getInt("d.correlativoitem"));
					String tipTra = result.getString("cv.tiptra");
					String fecha = dfFecha.format(result.getDate("t.fecha"));
					String hora = dfHora.format(result.getTime("t.horafinaliza"));
					String trx = dfTrx.format(result.getInt("t.numtransaccion"));
					String vendedor = "0000";
					String codDep = "00";
					String prod = dfProd.format(result.getInt("d.codproducto"));
					String prec = dfPrec.format(result.getDouble("d.precioregular")).replace(',','.');
					String precFin = dfPrec.format(result.getDouble("pfinal")).replace(',','.');
					if (precFin.equalsIgnoreCase(prec)) precFin = dfPrec.format(0).replace(',','.');
					String cant = dfCant.format(result.getDouble("d.cantidad"));
					String signOf = "0";
					String tda = dfCant.format(result.getInt("t.numtienda"));
					String lineaDetail = caja+cajero+corTra+tipTra+fecha+hora+trx+vendedor+codDep+prod+prec+precFin+cant+signOf+tda;
					out.write(lineaDetail + "\n");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			} finally {
				try {
					result.close();
				} catch (SQLException e3) {
					e3.printStackTrace();
				}
			}
			
			try {out.close();}catch(Exception e1) {e1.printStackTrace();}
		}
	}
	
	/**
	 * Obtiene los numeros de servicio que deben ser sincronizados
	 * @return Vector
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' 
	* Fecha: agosto 2011
	*/
	private Vector<Vector<Integer>> getServiciosNoSincronizados(){
		Vector<Vector<Integer>> result = new Vector<Vector<Integer>>();
		ResultSet rs = null;
		String sentenciaSQL = "select numtienda, numservicio from servicio where regactualizado='"+ Sesion.NO +"' and codtiposervicio='" + Sesion.APARTADO + "'";
		try {
			rs = Conexiones.obtenerTransacciones(sentenciaSQL, true);
			rs.beforeFirst();
			while (rs.next()) {
				Vector<Integer> servicio = new Vector<Integer>();
				servicio.add(new Integer(rs.getInt("numtienda")));
				servicio.add(new Integer(rs.getInt("numservicio")));
				result.addElement(servicio);
			}
		} catch (SQLException e) {
			e.printStackTrace();					
		} catch (ConexionExcepcion e) {
			e.printStackTrace();
		} catch (BaseDeDatosExcepcion e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			rs = null;
		}
		return result;
	}
	
}