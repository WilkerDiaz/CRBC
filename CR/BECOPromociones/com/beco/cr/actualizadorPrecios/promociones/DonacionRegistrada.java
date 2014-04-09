/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.beco.cr.actualizadorPrecios.promociones.DonacionRegistrada
 * Programa   : DonacionRegistrada.java
 * Creado por : jgraterol, aavila
 * Creado en  : 10/06/2008 10:18:05 AM
 *
 * (c) CENTRO BECO, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * ----------------------------------------------------------------------------- 
 * =============================================================================
 * */
package com.beco.cr.actualizadorPrecios.promociones;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarventa.Venta;
import com.becoblohm.cr.mediadoresbd.Conexiones;

public class DonacionRegistrada {
	private double montoDonado;
	private Date fechaDonacion;
	private int numTransaccion;
	private int numCaja;
	private int numTienda;
	private int codigoDonacion;
	private Vector<PagoDonacion> formasPago;
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public DonacionRegistrada(double montoDonado, Date fechaDonacion, int numTransaccion, int numCaja, int numTienda, int codigoDonacion) {
		super();
		try{
		this.montoDonado = montoDonado;
		this.fechaDonacion = fechaDonacion;
		this.numTransaccion = numTransaccion;
		this.numCaja = numCaja;
		this.numTienda = numTienda;
		this.codigoDonacion = codigoDonacion;
		this.formasPago = new Vector<PagoDonacion>();
		}catch(Exception e){ e.printStackTrace();}
	}
	
	public int getCodigoDonacion() {
		return codigoDonacion;
	}
	public void setCodigoDonacion(int codigoDonacion) {
		this.codigoDonacion = codigoDonacion;
	}
	public Date getFechaDonacion() {
		return fechaDonacion;
	}
	public void setFechaDonacion(Date fechaDonacion) {
		this.fechaDonacion = fechaDonacion;
	}
	public double getMontoDonado() {
		return montoDonado;
	}
	public void setMontoDonado(double montoDonado) {
		this.montoDonado = montoDonado;
	}
	public int getNumCaja() {
		return numCaja;
	}
	public void setNumCaja(int numCaja) {
		this.numCaja = numCaja;
	}
	public int getNumTienda() {
		return numTienda;
	}
	public void setNumTienda(int numTienda) {
		this.numTienda = numTienda;
	}
	public int getNumTransaccion() {
		return numTransaccion;
	}
	public void setNumTransaccion(int numTransaccion) {
		this.numTransaccion = numTransaccion;
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<PagoDonacion> getFormasPago() {
		return formasPago;
	}

	public void setFormasPago(Vector<PagoDonacion> formasPago) {
		this.formasPago = formasPago;
	}
	
	/**
	 * Asigna las formas de pago hasta que cubran el monto de una donacion
	 * **/
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void asignarFormasPago(Venta venta){
		
		Vector<Pago> pagos = venta.getPagosClonados();
		pagos = CR.meVenta.ordenarPagosPorCodigo(pagos);
		Iterator<Pago> iteraPagos = pagos.iterator();
		double montoTemp = this.getMontoDonado();
			while(iteraPagos.hasNext()){
				double pagoEstaDonacion = 0;
				Pago pago = (Pago)iteraPagos.next();
				if( pago.getMonto()!=0 && 
						!pago.getFormaPago().getCodigo().equalsIgnoreCase(Sesion.FORMA_PAGO_TRANSACCION_PREMIADA) &&
						!pago.getFormaPago().getCodigo().equalsIgnoreCase(Sesion.FORMA_PAGO_PROMO_MERCADEO) &&
						!pago.getFormaPago().getCodigo().equalsIgnoreCase(Sesion.FORMA_PAGO_PROMO_MERCHANDISING) &&
						!pago.getFormaPago().getCodigo().equalsIgnoreCase(Sesion.FORMA_PAGO_CUPON_DESCUENTO)){
					//Una Donacion no se puede registrar con transaccion premiada como forma de pago
					//TODO agregar al if de arriba las condiciones para que tampoco sean cupones de descuento
					if(pago.getMonto()>=montoTemp) pagoEstaDonacion = montoTemp;
					else pagoEstaDonacion = pago.getMonto();
					
					PagoDonacion pagoDonacion = new PagoDonacion(pago.getFormaPago(), pagoEstaDonacion);
					montoTemp-=pagoEstaDonacion;
					double monto = pago.getMonto()-pagoEstaDonacion;
					pago.setMonto(monto);
					this.formasPago.addElement(pagoDonacion);
					if(montoTemp==0){
						break;
					}
				}
			}
	}
	
	public Object clone(){
		DonacionRegistrada dr = new DonacionRegistrada(this.montoDonado,this.fechaDonacion,this.numTransaccion,this.numCaja,this.numTienda,this.codigoDonacion);
		for(int i=0;i<this.getFormasPago().size();i++){
			PagoDonacion pd = (PagoDonacion)this.getFormasPago().elementAt(i);
			dr.formasPago.addElement((PagoDonacion)pd.clone());
		}
		return dr;
	}
	
	/**
	 * Obtiene las donaciones registradas para una venta que corresponda con los datos indicados
	 * @param codTienda
	 * @param fechaTrans
	 * @param numCaja
	 * @param numTransaccion
	 * @param local
	 * @return Vector
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public static Vector<DonacionRegistrada> consultarDonacionesPorVenta(int codTienda, String fechaTrans, int numCaja, int numTransaccion, boolean local){
		Vector<DonacionRegistrada> donaciones = new Vector<DonacionRegistrada>();
		ResultSet result = null;
		String sentenciaSQL = "select * from donacionesregistradas dr " +
				" where dr.numTienda= " + codTienda + " and " +
						" dr.fechaDonacion = '" + fechaTrans + "' and " +
						" dr.numCaja = "+numCaja+" and " +
						" dr.numTransaccion = "+ numTransaccion+" order by codigo";
		try {
			result = Conexiones.realizarConsulta(sentenciaSQL,local);
			result.beforeFirst();
			int codigoDonacionRegistrada=1;
			while (result.next()) {
				DonacionRegistrada dr = new DonacionRegistrada(result.getDouble("montoDonado"),
						result.getDate("fechaDonacion"),
						result.getInt("numTransaccion"),
						result.getInt("numCaja"),
						result.getInt("numTienda"),
						result.getInt("codigoDonacion"));
				
				sentenciaSQL = "select * from pagodonacion pd INNER JOIN  formadepago f on (f.codformadepago=pd.codigoFormaPago) where " +
						"pd.fechaDonacion='"+dr.getFechaDonacion()+"' and " +
						"pd.numTransaccion="+dr.getNumTransaccion()+" and " +
						"pd.numCaja="+dr.getNumCaja()+" and " +
						"pd.numTienda="+dr.getNumTienda()+" and " +
						"pd.codigoDonacion="+dr.getCodigoDonacion()+" and " +
						"pd.codigoDonacionRegistrada="+codigoDonacionRegistrada;
				ResultSet result2=null;
				Vector<PagoDonacion> pagosDonaciones = new Vector<PagoDonacion>();
				try {
					result2 = Conexiones.realizarConsulta(sentenciaSQL,local);
					result2.beforeFirst();
					
					while (result2.next()) {
						FormaDePago forma = new FormaDePago(result2.getString("codformadepago"),
								Integer.parseInt(result2.getString("tipoformadepago")), 
								result2.getString("nombre"),
								result2.getString("codbanco"), 
								result2.getString("indicarbanco").equalsIgnoreCase(Sesion.SI+""),
								result2.getString("indicarnumdocumento").equalsIgnoreCase(Sesion.SI+""), 
								result2.getString("indicarnumcuenta").equalsIgnoreCase(Sesion.SI+""), 
								result2.getString("indicarnumconformacion").equalsIgnoreCase(Sesion.SI+""), 
								result2.getString("indicarnumreferencia").equalsIgnoreCase(Sesion.SI+""), 
								result2.getString("indicarcedulatitular").equalsIgnoreCase(Sesion.SI+""), 
								result2.getString("validarsaldocliente").equalsIgnoreCase(Sesion.SI+""), 
								result2.getString("permitevuelto").equalsIgnoreCase(Sesion.SI+""), 
								result2.getDouble("montominimo"),
								result2.getDouble("montomaximo"), 
								result2.getDouble("montocomision"), 
								result2.getString("entregaparcial").equalsIgnoreCase(Sesion.SI+""), 
								result2.getString("requiereautorizacion").equalsIgnoreCase(Sesion.SI+""));
						
						PagoDonacion pagoDonacion = new PagoDonacion(forma,result.getDouble("monto"));
						pagosDonaciones.addElement(pagoDonacion);
					}
					
				} catch (SQLException e2) {
					//Nada, puede que la tabla no exista porque no se tiene la extension de promociones
				} catch (BaseDeDatosExcepcion e) {
					e.printStackTrace();
				} catch (ConexionExcepcion e) {
					e.printStackTrace();
				} finally {
					try {
						result2.close();
					} catch (SQLException e) {
						
					}
					result2 = null;
				}
				dr.setFormasPago(pagosDonaciones);
				donaciones.addElement(dr);
			}
			
		} catch (SQLException e2) {
			//Nada, puede que la tabla no exista porque no se tiene la extension de promociones
		} catch (BaseDeDatosExcepcion e) {
			e.printStackTrace();
		} catch (ConexionExcepcion e) {
			e.printStackTrace();
		} finally {
			try {
				result.close();
			} catch (SQLException e) {
				
			}
			result = null;
		}
		return donaciones;
	}
}
