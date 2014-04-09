/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.manejarservicio
 * Programa   : Cotizacion.java
 * Creado por : gmartinelli
 * Creado en  : 18/05/2004 09:30 AM
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
package com.becoblohm.cr.manejarservicio;

import java.sql.Time;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.AfiliadoUsrExcepcion;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ClienteExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.ProductoExcepcion;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.manejarventa.DetalleTransaccion;
import com.becoblohm.cr.mediadoresbd.BaseDeDatosServicio;
import com.becoblohm.cr.mediadoresbd.MediadorBD;

/** 
 * Descripción: 
 * 		Subclase de Servicio. Maneja la recuperacion de las Cotizaciones para su futura facturación.
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
* Fecha: agosto 2011
*/
public class Cotizacion extends Servicio {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Cotizacion.class);
	private Vector<Float> detalleVendidos;
	private Vector<Boolean> entregar;
	private boolean cotizacionExentaS = false;
	private boolean clienteOriginalDeCotizacion;
    
	/**
	 * Constructor de la clase Cotizacion para cargar una cotización específica
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Se comentó variable sin uso
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unchecked")
	public Cotizacion (int tda, int numServ, Date fechaServ) throws AfiliadoUsrExcepcion, BaseDeDatosExcepcion, ClienteExcepcion, ConexionExcepcion {
		super();
		
		// Obtenemos la venta de la BaseDeDatos
		Vector<Vector<?>> servicioObtenido = BaseDeDatosServicio.obtenerCotizacion(tda, numServ, fechaServ);
		Vector<Object> cabeceraServicio = (Vector<Object>)servicioObtenido.elementAt(0);
		Vector<Vector<Object>> detallesServicio = (Vector<Vector<Object>>)servicioObtenido.elementAt(1);
	
		// Armamos la cabecera de venta
		super.codTienda = ((Integer)cabeceraServicio.elementAt(0)).intValue();;
		super.tipoServicio = ((String)cabeceraServicio.elementAt(1));
		super.numServicio = ((Integer)cabeceraServicio.elementAt(2)).intValue();
		super.fechaServicio = ((Date) cabeceraServicio.elementAt(3));
		super.cliente = ((((String)cabeceraServicio.elementAt(4)) == null)||(((String)cabeceraServicio.elementAt(4)).trim().equals("")))
					? new Cliente()
					: MediadorBD.obtenerCliente((String)cabeceraServicio.elementAt(4));
		if(this.getCliente().getCodCliente() != null) {
			clienteOriginalDeCotizacion = true;
		} else {
			clienteOriginalDeCotizacion = false;
		}
		super.montoBase = ((Double)cabeceraServicio.elementAt(5)).doubleValue();
		super.montoImpuesto = ((Double)cabeceraServicio.elementAt(6)).doubleValue();
		super.lineasFacturacion = ((Integer)cabeceraServicio.elementAt(7)).intValue();
		super.codCajero = ((String)cabeceraServicio.elementAt(8));
		super.numTransaccionVenta = ((Integer)cabeceraServicio.elementAt(9)).intValue();
		super.fechaTransVta = ((Date) cabeceraServicio.elementAt(10));
		super.horaInicia = (Time)cabeceraServicio.elementAt(11);
		super.horaFin = (Time)cabeceraServicio.elementAt(12);
		super.estadoServicio = ((String)cabeceraServicio.elementAt(13)).toCharArray()[0];
		
		// Chequeamos que el cliente no sea el usuario activo
		if ((cliente.getNumFicha()!=null) && Sesion.getUsuarioActivo().getNumFicha().equalsIgnoreCase(cliente.getNumFicha()))
			throw new AfiliadoUsrExcepcion("La cotización posee como cliente al usuario activo.\nNo se pudo recuperar la cotización");

		// Armamos el detalle de la Cotizacion
		entregar = new Vector<Boolean>();
		detalleVendidos = new Vector<Float>();
		for (int i=0; i<detallesServicio.size(); i++) {
			Vector<Object> detalleServicio = detallesServicio.elementAt(i);
			String codProd = (String)detalleServicio.elementAt(0);
			String codCondVenta = (String)detalleServicio.elementAt(1);
			float cantidad = ((Float)detalleServicio.elementAt(2)).floatValue();
			//double precioRegular = ((Double)detalleServicio.elementAt(3)).doubleValue();
			double precioFinal = ((Double)detalleServicio.elementAt(4)).doubleValue();
			double mtoImpuesto = ((Double)detalleServicio.elementAt(5)).doubleValue();
			String tipoCap = (String)detalleServicio.elementAt(6);
			int codPromo = ((Integer)detalleServicio.elementAt(7)).intValue();
			char estadoRegistro = ((String)cabeceraServicio.elementAt(8)).toCharArray()[0];
			
			try {
				this.detallesServicio.addElement(new DetalleServicio(codProd, tipoCap, cantidad, this.fechaServicio, this.horaInicia));
			} catch (ProductoExcepcion e1) {
				logger.error("Cotizacion(int, int, Date)", e1);

				throw (new BaseDeDatosExcepcion("Error. Existen productos en la cotización que no se encuentran en la Base De Datos"));
			} catch (BaseDeDatosExcepcion e1) {
				logger.error("Cotizacion(int, int, Date)", e1);

				throw (new BaseDeDatosExcepcion("Error recuperando cotización"));
			}
		
			// Agregamos los otros parametros
			((DetalleServicio)this.detallesServicio.elementAt(this.detallesServicio.size()-1)).setCondicionVenta(codCondVenta);
			((DetalleServicio)this.detallesServicio.elementAt(this.detallesServicio.size()-1)).setPrecioFinal(precioFinal);
			((DetalleServicio)this.detallesServicio.elementAt(this.detallesServicio.size()-1)).setPrecioFinal(precioFinal);
			((DetalleServicio)this.detallesServicio.elementAt(this.detallesServicio.size()-1)).setMontoImpuesto(mtoImpuesto);
			((DetalleServicio)this.detallesServicio.elementAt(this.detallesServicio.size()-1)).setCodPromocion(codPromo);
			((DetalleServicio)this.detallesServicio.elementAt(this.detallesServicio.size()-1)).setEstadoRegistro(estadoRegistro);
			entregar.addElement(new Boolean(false));
			detalleVendidos.addElement(new Float(0));
		}
	}
	
	/**
	 * Constructor de la clase Cotizacion para cargar una cotización específica
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Se comentaron variables sin uso
	* Fecha: agosto 2011
	*/
	public Cotizacion (int tda, int numServ, Date fecha, String codCliente, double mtoBase, double mtoImpuesto,
					   int lineas, String cajero, int numTransVta, Date fechaTrans, Time horaInicia,
					   Time horaFin, char edoServ, Vector<Vector<Object>> detallesServ) throws AfiliadoUsrExcepcion, BaseDeDatosExcepcion, ClienteExcepcion, ConexionExcepcion {
		super();
		
		// Armamos la cabecera de la cotizacion
		super.codTienda = tda;
		super.tipoServicio = Sesion.COTIZACION;
		super.numServicio = numServ;
		super.fechaServicio = fecha;
		super.cliente = ((codCliente == null)||(codCliente.trim().equals("")))
					? new Cliente()
					: MediadorBD.obtenerCliente(codCliente);
		if(this.getCliente().getCodCliente() != null) {
			clienteOriginalDeCotizacion = true;
		} else {
			clienteOriginalDeCotizacion = false;
		}
		super.montoBase = mtoBase;
		super.montoImpuesto = mtoImpuesto;
		super.lineasFacturacion = lineas;
		super.codCajero = cajero;
		super.numTransaccionVenta = numTransVta;
		super.fechaTransVta = fechaTrans;
		super.horaInicia = horaInicia;
		super.horaFin = horaFin;
		super.estadoServicio = edoServ;
		
		// Armamos el detalle de la Cotizacion
		entregar = new Vector<Boolean>();
		detalleVendidos = new Vector<Float>();
		for (int i=0; i<detallesServ.size(); i++) {
			Vector<Object> detalleServicio = detallesServ.elementAt(i);
			String codProd = (String)detalleServicio.elementAt(0);
			String codCondVenta = (String)detalleServicio.elementAt(1);
			float cantidad = ((Float)detalleServicio.elementAt(2)).floatValue();
			//double precioRegular = ((Double)detalleServicio.elementAt(3)).doubleValue();
			double precioFinal = ((Double)detalleServicio.elementAt(4)).doubleValue();
			double mtoImp = ((Double)detalleServicio.elementAt(5)).doubleValue();
			String tipoCap = (String)detalleServicio.elementAt(6);
			int codPromo = ((Integer)detalleServicio.elementAt(7)).intValue();
			//char estadoRegistro = ((String)detalleServicio.elementAt(8)).toCharArray()[0];
			
			try {
				this.detallesServicio.addElement(new DetalleServicio(codProd, tipoCap, cantidad, this.fechaServicio, this.horaInicia));
			} catch (ProductoExcepcion e1) {
				logger
						.error(
								"Cotizacion(int, int, Date, String, double, double, int, String, int, Date, Time, Time, char, Vector)",
								e1);

				throw (new BaseDeDatosExcepcion("Error. Existen productos en la cotización que no se encuentran en la Base De Datos"));
			} catch (BaseDeDatosExcepcion e1) {
				logger
						.error(
								"Cotizacion(int, int, Date, String, double, double, int, String, int, Date, Time, Time, char, Vector)",
								e1);

				throw (new BaseDeDatosExcepcion("Error recuperando cotización"));
			}
		
			// Agregamos los otros parametros
			((DetalleServicio)this.detallesServicio.elementAt(this.detallesServicio.size()-1)).setCondicionVenta(codCondVenta);
			((DetalleServicio)this.detallesServicio.elementAt(this.detallesServicio.size()-1)).setPrecioFinal(precioFinal);
			((DetalleServicio)this.detallesServicio.elementAt(this.detallesServicio.size()-1)).setPrecioFinal(precioFinal);
			((DetalleServicio)this.detallesServicio.elementAt(this.detallesServicio.size()-1)).setMontoImpuesto(mtoImp);
			((DetalleServicio)this.detallesServicio.elementAt(this.detallesServicio.size()-1)).setCodPromocion(codPromo);
			entregar.addElement(new Boolean(false));
			detalleVendidos.addElement(new Float(0));
		}
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Object> finalizarServicio() throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("finalizarServicio() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("finalizarServicio() - end");
		}
		return null;
	}
	
	/**
	 * Método anularProducto
	 *
	 * @param renglon
	 * @throws ProductoExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	Vector<Object> anularProducto(int renglon) throws ProductoExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("anularProducto(int) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("anularProducto(int) - end");
		}
		return null;
	}

	/**
	 * Método anularProducto
	 *
	 * @param renglon
	 * @param cantidad
	 * @throws ProductoExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	Vector<Object> anularProducto(int renglon, float cantidad) throws ProductoExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("anularProducto(int, float) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("anularProducto(int, float) - end");
		}
		return null;
	}
		
	public void facturarCotizacion() throws ExcepcionCr, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("facturarCotizacion() - start");
		}

		CR.meVenta.facturarCotizacion(this);

		if (logger.isDebugEnabled()) {
			logger.debug("facturarCotizacion() - end");
		}
	}

	/**
	 * Método actualizarTipoEntrega
	 *		Actualiza el tipo de entrega del detalle especificado. esta puede ser: "Caja","Despacho","Domicilio" 
	 * @param renglon - Es la posición del detalle de la venta donde se quiere actualizar el campo de despachados
	 * @param tipoEntrega
	 */
	public void actualizarTipoEntrega (int renglon, String tipoEntrega) {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarTipoEntrega(int, String) - start");
		}
		
		((DetalleServicio)this.getDetallesServicio().elementAt(renglon)).setTipoEntrega(tipoEntrega);

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarTipoEntrega(int, String) - end");
		}
	}

	/**
	 * Método isClienteOriginalDeCotizacion
	 * 
	 * @return
	 * boolean
	 */
	public boolean isClienteOriginalDeCotizacion() {
		if (logger.isDebugEnabled()) {
			logger.debug("isClienteOriginalDeCotizacion() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isClienteOriginalDeCotizacion() - end");
		}
		return clienteOriginalDeCotizacion;
	}

	/**
	 * Método getEntregar
	 * 
	 * @return
	 * Vector
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Boolean> getEntregar() {
		if (logger.isDebugEnabled()) {
			logger.debug("getEntregar() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getEntregar() - end");
		}
		return entregar;
	}
	
	/**
	 * Método getDetalleVendidos
	 * 
	 * @return
	 * Vector
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Float> getDetalleVendidos() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDetalleVendidos() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDetalleVendidos() - end");
		}
		return detalleVendidos;
	}

	/**
	 * Método modificarVendido
	 * 	A partir de un código de producto ubica el mismo en el detalle de 
	 * cotizacion para asignar los valores vendidos en el atributo detalleVendidos
	 * @param codigo código de búsqueda.
	 * @return void
	 */
	public void modificarVendido(String codigo) {
		this.modificarVendido(codigo, 1,false);
	}
	
	/**
	 * Método modificarVendido
	 * 	A partir de un código de producto ubica el mismo en el detalle de 
	 * cotizacion para asignar los valores vendidos en el atributo detalleVendidos
	 * @param codigo código de búsqueda.
	 * @return void
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void modificarVendido(String codigo, float cantidad,boolean eliminar) {
		if(eliminar){
			cantidad = calcularCantEliminar(codigo,cantidad); 
			if(cantidad <= 0)
				return;
		}
		Vector<DetalleServicio> detalles = getDetallesServicio();
		String codSinCons = codigo.substring(0,codigo.length()-Sesion.tamConsecutivo);
		int tam = detalles.size();
		//Reviso si el código está en la lista de detalles
		for(int i = 0; i < tam; ++i){
			DetalleServicio d = (DetalleServicio)detalles.elementAt(i);
			float vend = ((Float)getDetalleVendidos().elementAt(i)).floatValue();
			int compareCant = eliminar  
								? Float.compare((float)0,vend)
								: Float.compare(vend, d.getCantidad());
			if(Long.parseLong(d.getProducto().getCodProducto())==Long.parseLong(codigo) && compareCant < 0){
				Float vendidos;
				if(eliminar){
					if(vend-cantidad < 0){
						vendidos = new Float((float)0);
						cantidad = vend-cantidad; 
					}else{
						vendidos = new Float(vend-cantidad);
						cantidad= 0;
					}
				}else{
					if(vend + cantidad > d.getCantidad()){
						vendidos = new Float(d.getCantidad());
						cantidad -= d.getCantidad()-vend;
					}else{
						vendidos = new Float(vend+cantidad);
						cantidad = 0;
					}
				}
				getDetalleVendidos().insertElementAt(vendidos, i);
				getDetalleVendidos().remove(i+1);
				if (Float.compare(vendidos.floatValue(), d.getCantidad())==0) {
					getEntregar().insertElementAt(new Boolean(true), i);
					getEntregar().remove(i+1);
				} else {
					getEntregar().insertElementAt(new Boolean(false), i);
					getEntregar().remove(i+1);
				}
			}
		}
		//si no se encuentra, se busca por el consecutivo
		if(cantidad>0){
			for(int i = 0; i < tam; ++i){
				DetalleServicio d = (DetalleServicio)detalles.elementAt(i);
				String codProd =d.getProducto().getCodProducto().substring(0,d.getProducto().getCodProducto().length()-Sesion.tamConsecutivo);
				float vend = ((Float)getDetalleVendidos().elementAt(i)).floatValue();
				int compareCant = eliminar 
									? Float.compare((float)0,vend)
									: Float.compare(vend, d.getCantidad());
				if(Long.parseLong(codProd)==Long.parseLong(codSinCons) && compareCant < 0){
					Float vendidos;
					if(eliminar){
						if(vend-cantidad < 0){
							vendidos = new Float((float)0);
							cantidad = vend-cantidad;
						}else{
							vendidos = new Float(vend-cantidad);
							cantidad = 0;
						}
					}else{
						if(vend + cantidad > d.getCantidad()){
							vendidos = new Float(d.getCantidad());
							cantidad -= d.getCantidad()-vend;
						}else{
							vendidos = new Float(vend+cantidad);
							cantidad = 0;
						}
					}
					getDetalleVendidos().insertElementAt(vendidos, i);
					getDetalleVendidos().remove(i+1);
					if (Float.compare(vendidos.floatValue(), d.getCantidad())==0) {
						getEntregar().insertElementAt(new Boolean(true), i);
						getEntregar().remove(i+1);
					} else {
						getEntregar().insertElementAt(new Boolean(false), i);
						getEntregar().remove(i+1);
					}
				}
			}
		}
	}
	
	/**
	 * Calcula la cantidad de elementos que se deben eliminar en la lista de 
	 * cotizaciones
	 * @param codigo código del elemento que se va a eliminar
	 * @param cantVenta cantidad de elementos que se encuentran en la lista
	 * de ventas que son IGUALES al código sin el consecutivo.
	 * @param cantEliminar cantidad que se va a eliminar en la lista de ventas
	 * @return float indicando el número de elementos que se van a borrar.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private float calcularCantEliminar(String codigo, float cantEliminar){
		float cantVenta = 0;
		codigo = codigo.trim();
		Vector<DetalleServicio> detalles = getDetallesServicio();
		String codSinCons = codigo.substring(0,codigo.length()-Sesion.tamConsecutivo);
		float cantCot = 0;
		Vector<DetalleTransaccion> detallesVenta = CR.meVenta.getVenta().getDetallesTransaccion();
		int tam = detallesVenta.size();
		for(int i= 0; i < tam; ++i){ 
			DetalleTransaccion d = (DetalleTransaccion)detallesVenta.elementAt(i);
			String codProducto = (d.getProducto().getCodProducto().trim()).substring(0,(d.getProducto().getCodProducto()).trim().length()-Sesion.tamConsecutivo);
			if(Long.parseLong(codProducto) == Long.parseLong(codSinCons)){
				cantVenta += d.getCantidad();
			}
		}
		tam = detalles.size();
		for(int i=0; i < tam; ++i){
			DetalleServicio d = (DetalleServicio)detalles.elementAt(i);
			String codProd =(d.getProducto().getCodProducto().trim()).substring(0,d.getProducto().getCodProducto().length()-Sesion.tamConsecutivo);
			if(Long.parseLong(codSinCons) == Long.parseLong(codProd)){
				cantCot += ((Float)getDetalleVendidos().elementAt(i)).floatValue();
			}
		}
		float cantidad = -(cantVenta-cantCot);
		return cantidad;
	}

	/*
	* Modificaciones referentes a la migración a java 1.6 por jperez
	* Getter y Setter de la variable 'cotizacionExentaS'
	* Fecha: agosto 2011
	*/
	public void setCotizacionExentaS(boolean cotizacionExentaS) {
		this.cotizacionExentaS = cotizacionExentaS;
	}

	public boolean isCotizacionExentaS() {
		return cotizacionExentaS;
	}
	
}