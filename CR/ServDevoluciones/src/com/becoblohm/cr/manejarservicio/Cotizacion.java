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

import com.becoblohm.cr.excepciones.AfiliadoUsrExcepcion;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ClienteExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.ProductoExcepcion;

/** 
 * Descripción: 
 * 		Subclase de Servicio. Maneja la recuperacion de las Cotizaciones para su futura facturación.
 */
public class Cotizacion extends Servicio {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Cotizacion.class);
	
	private boolean cotizacionExentaS = false;
	private Vector entregar;
	private boolean clienteOriginalDeCotizacion;
    
	/**
	 * Constructor de la clase Cotizacion para cargar una cotización específica
	 */
	public Cotizacion (int tda, int numServ, Date fechaServ) throws AfiliadoUsrExcepcion, BaseDeDatosExcepcion, ClienteExcepcion, ConexionExcepcion {
		/*super();
		
		// Obtenemos la venta de la BaseDeDatos
		Vector servicioObtenido = BaseDeDatosServicio.obtenerCotizacion(tda, numServ, fechaServ);
		Vector cabeceraServicio = (Vector)servicioObtenido.elementAt(0);
		Vector detallesServicio = (Vector)servicioObtenido.elementAt(1);
	
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

		// Armamos el detalle del Apartado
		entregar = new Vector();
		for (int i=0; i<detallesServicio.size(); i++) {
			Vector detalleServicio = (Vector)detallesServicio.elementAt(i);
			String codProd = (String)detalleServicio.elementAt(0);
			String codCondVenta = (String)detalleServicio.elementAt(1);
			float cantidad = ((Float)detalleServicio.elementAt(2)).floatValue();
			double precioRegular = ((Double)detalleServicio.elementAt(3)).doubleValue();
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
			entregar.addElement(new Boolean(true));
		}*/
	}
	
	/**
	 * Constructor de la clase Cotizacion para cargar una cotización específica
	 */
	public Cotizacion (int tda, int numServ, Date fecha, String codCliente, double mtoBase, double mtoImpuesto,
					   int lineas, String cajero, int numTransVta, Date fechaTrans, Time horaInicia,
					   Time horaFin, char edoServ, Vector detallesServ) throws AfiliadoUsrExcepcion, BaseDeDatosExcepcion, ClienteExcepcion, ConexionExcepcion {
		/*super();
		
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
		
		// Armamos el detalle del Apartado
		entregar = new Vector();
		for (int i=0; i<detallesServ.size(); i++) {
			Vector detalleServicio = (Vector)detallesServ.elementAt(i);
			String codProd = (String)detalleServicio.elementAt(0);
			String codCondVenta = (String)detalleServicio.elementAt(1);
			float cantidad = ((Float)detalleServicio.elementAt(2)).floatValue();
			double precioRegular = ((Double)detalleServicio.elementAt(3)).doubleValue();
			double precioFinal = ((Double)detalleServicio.elementAt(4)).doubleValue();
			double mtoImp = ((Double)detalleServicio.elementAt(5)).doubleValue();
			String tipoCap = (String)detalleServicio.elementAt(6);
			int codPromo = ((Integer)detalleServicio.elementAt(7)).intValue();
			char estadoRegistro = ((String)detalleServicio.elementAt(8)).toCharArray()[0];
			
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
			entregar.addElement(new Boolean(true));
		}*/
	}

	public Vector finalizarServicio() throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("finalizarServicio() - start");
		}

/*		this.numServicio = Sesion.getCaja().getNuevoNumServicio();
		this.lineasFacturacion = this.detallesServicio.size();
		this.horaFin = Sesion.horaSistema();
		this.estadoServicio = Sesion.APARTADO_ACTIVO;

		// Registramos el servicio
		BaseDeDatosServicio.registrarApartado(this);
		
		// Mandamos a imprimir la factura pero antes registramos la auditoría de esta función
		//Sesion.setUbicacion("FACTURACION","finalizarTransaccion");
		//Auditoria.registrarAuditoria("Emitiendo Factura Nro." + this.numTransaccion + " de Caja " + this.numCajaFinaliza,'T');
		
		//Vector resultado = ComprobanteDeApartado.imprimirFactura(this);
		// Actualizamos el estado de la transaccion
		//BaseDeDatosVenta.actualizarEdoTransaccion(this.codTienda, this.fechaTrans, this.numCajaInicia, this.numRegCajaInicia, Sesion.FINALIZADA, true);
		
		/*return new Vector();//resultado;*/

		if (logger.isDebugEnabled()) {
			logger.debug("finalizarServicio() - end");
		}
		return null;
	}
	
	/**
	 * Método obtenerCantidadProds
	 * Retorna la cantidad de productos que se encuentran en el detalle.
	 * @return int - entero que representa el total de productos presentes en la transacción.
	 */
	public float obtenerCantidadProds() {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCantidadProds() - start");
		}

		float cantTotal = 0;
		for (int i=0; i<this.detallesServicio.size();i++) {
			if (((Boolean)entregar.elementAt(i)).booleanValue())
				cantTotal +=((DetalleServicio) this.getDetallesServicio().elementAt(i)).getCantidad();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCantidadProds() - end");
		}
		return cantTotal;
	}

	/**
	 * Método anularProducto
	 *
	 * @param renglon
	 * @throws ProductoExcepcion
	 */
	Vector anularProducto(int renglon) throws ProductoExcepcion {
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
	Vector anularProducto(int renglon, float cantidad) throws ProductoExcepcion {
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
/*
		CR.meVenta.facturarCotizacion(this);
		boolean seleccionadoProducto = false;
		for (int i=0; i<entregar.size(); i++) {
			if (((Boolean)entregar.elementAt(i)).booleanValue()) {
				seleccionadoProducto = true;
				break;
			}
		}
		
		if (seleccionadoProducto) {
			Auditoria.registrarAuditoria("Facturada Cotizacion Nro. " + this.getNumServicio(),'T');
			this.estadoServicio = Sesion.COTIZACION_FACTURADA;
			BaseDeDatosServicio.actualizarEdoServicio(this.codTienda,this.tipoServicio,this.numServicio,this.fechaServicio,Sesion.COTIZACION_FACTURADA,false);
		}
*/
		if (logger.isDebugEnabled()) {
			logger.debug("facturarCotizacion() - end");
		}
	}

	/**
	 * Método cambiarSeleccionDetalle
	 * 
	 * @param linea
	 */
	public void cambiarSeleccionDetalle(int linea) {
		if (logger.isDebugEnabled()) {
			logger.debug("cambiarSeleccionDetalle(int) - start");
		}
/*
		entregar.setElementAt(new Boolean(!((Boolean)entregar.elementAt(linea)).booleanValue()),linea);
		// Recalculamos el monto del servicio y del impuesto
		double imp = 0.0;
		double mto = 0.0;
		for (int i=0; i<detallesServicio.size(); i++) {
			DetalleServicio detActual = (DetalleServicio) detallesServicio.elementAt(i);
			 if (((Boolean)entregar.elementAt(i)).booleanValue()) {
			 	imp += detActual.getMontoImpuesto() * detActual.getCantidad();
			 	mto += detActual.getPrecioFinal() * detActual.getCantidad();
		 	}
		}
		this.montoBase = mto;
		this.montoImpuesto = imp;
		
		CR.me.recalcularRetencionIVA();
*/
		if (logger.isDebugEnabled()) {
			logger.debug("cambiarSeleccionDetalle(int) - end");
		}
	}
	/**
	 * Método getEntregar
	 * 
	 * @return
	 * Vector
	 */
	public Vector getEntregar() {
		if (logger.isDebugEnabled()) {
			logger.debug("getEntregar() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getEntregar() - end");
		}
		return entregar;
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

}