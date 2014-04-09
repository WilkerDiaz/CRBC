/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.manejarservicio
 * Programa   : Servicio.java
 * Creado por : gmartinelli
 * Creado en  : 02/04/2004 09:08 AM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.1
 * Fecha       : 14-may-04 10:17
 * Analista    : gmartinelli
 * Descripción : Implementado manejo de Auditorias.
 * =============================================================================
 */
package com.becoblohm.cr.manejarservicio;

import java.sql.Time;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.AfiliadoUsrExcepcion;
import com.becoblohm.cr.excepciones.AutorizacionExcepcion;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ClienteExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ProductoExcepcion;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.manejarventa.Producto;
import com.becoblohm.cr.manejarventa.Promocion;
import com.becoblohm.cr.utiles.MathUtil;

/** 
 * Descripción: 
 * 		Superclase de Servicios. Maneja las operaciones con Servicios (Sean Apartados,
 * Cotizaciones, etc.)
 */
public abstract class Servicio {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Servicio.class);
	
	protected int codTienda;
	protected String tipoServicio;
	protected int numServicio;
	protected Date fechaServicio;
	protected Time horaInicia;
	protected Time horaFin;
	protected String codCajero;
	protected Cliente cliente;
	protected double montoBase;
	protected double montoImpuesto;
	protected int lineasFacturacion;
	protected int numTransaccionVenta;
	protected Date fechaTransVta;
	protected char estadoServicio;
	protected Vector detallesServicio;

	/**
	 * Constructor de la Clase Servicio.
	 * @param tda Numero de la Tienda.
	 * @param tipoServ String que representa el código del tipo de Servicio.
	 * @param fecha Fecha del creación del Servicio.
	 * @param hora Hora del creación del Servicio.
	 * @param cajero Codigo del cajero.
	 * @param edoServ Estado del Servicio (Activo, Anulado, Finalizado).
	 */
	public Servicio(int tda, String tipoServ, Date fecha, Time hora, String cajero, char edoServ) {

		this.codTienda = tda;
		this.tipoServicio = tipoServ;
		this.numServicio = 0;
		this.fechaServicio = fecha;
		this.horaInicia = hora;
		this.horaFin = null;
		this.codCajero = cajero;
		this.cliente = new Cliente();
		this.montoBase = 0.0;
		this.montoImpuesto = 0.0;
		this.lineasFacturacion = 0;
		this.numTransaccionVenta = 0;
		this.fechaTransVta = null;
		this.estadoServicio = edoServ;
		this.detallesServicio = new Vector();
	}
	
	/**
	 * 
	 */
	public Servicio() {
		this.detallesServicio = new Vector();
	}
	
	/**
	 * Métodos abstractos de la clase
	 */

	/**
	 * Anula un producto de un renglon del Servicio.
	 * @param renglon Renglon del producto a anular.
	 */
	abstract Vector anularProducto(int renglon) throws ProductoExcepcion;

	/**
	 * Elimina una cantidad de productos de un renglon.
	 * @param renglon Renglon del producto a eliminar.
	 * @param cantidad Cantidad de productos a eliminar. Si existen menos productos que el 
	 * 		  especificado en la cantidad se elimina el renglon.
	 */
	abstract Vector anularProducto(int renglon, float cantidad) throws ProductoExcepcion;

	/**
	 * Finaliza el servicio. Registra los datos en la Base de datos.
	 */
	protected abstract Vector finalizarServicio() throws BaseDeDatosExcepcion, ConexionExcepcion;

	// Metodos de la clase Servicio
	
	public double consultarMontoServ() {
		if (logger.isDebugEnabled()) {
			logger.debug("consultarMontoServ() - start");
		}

		double returndouble = MathUtil.roundDouble(montoBase + montoImpuesto);
		if (logger.isDebugEnabled()) {
			logger.debug("consultarMontoServ() - end");
		}
		return returndouble;
	}
		
	/**
	 * Actualiza el Monto del Servicio, calcula el nuevo monto a partir del detalle.
	 */
	protected void actualizarMontoServ() {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarMontoServ() - start");
		}

		double precioFinal = 0.0;
		double montoImpuesto = 0.0;
		
		for (int i=0; i<detallesServicio.size(); i++) {
			precioFinal += ((DetalleServicio) detallesServicio.elementAt(i)).getPrecioFinal() * ((DetalleServicio) detallesServicio.elementAt(i)).getCantidad();
			montoImpuesto += ((DetalleServicio) detallesServicio.elementAt(i)).getMontoImpuesto() * ((DetalleServicio) detallesServicio.elementAt(i)).getCantidad();
		}
		
		this.montoImpuesto = MathUtil.roundDouble(montoImpuesto);
		this.montoBase = MathUtil.roundDouble(precioFinal);

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarMontoServ() - end");
		}
	}

	/**
	 * Verifica si se aplican promociones a los productos que se encuentran en el detalle de servicio.
	 * @param prod Producto a chequear promociones
	 * @return Vector - Vector con el codigo de promocion y precio final de producto si aplica promocion
	 */
	protected Vector verificarPromociones(Producto prod) {
		if (logger.isDebugEnabled()) {
			logger.debug("verificarPromociones(Producto) - start");
		}

		Promocion promocionAplicada = null;
		
		// Obtenemos las promociones del producto en el detalle de transaccion
		Vector promociones = new Vector();
		promociones = prod.getPromociones();
		
		// Buscamos la promocion con prioridad mas alta
		for (int i=0; i<promociones.size(); i++) {
			if (promocionAplicada == null) {
				promocionAplicada = (Promocion)promociones.elementAt(i);
			} else {
				if(((Promocion)promociones.elementAt(i)).getPrioridad() < promocionAplicada.getPrioridad()) {
					promocionAplicada = (Promocion)promociones.elementAt(i);
				}
			}
		}
		
		// Aplicamos la promocion (si existe) al producto
		double pFinal;
		double impto;
		Vector result = new Vector(2);
		if (promocionAplicada != null) {
			result.addElement(new Integer(promocionAplicada.getCodPromocion()));
			if (promocionAplicada.getPorcentajeDescuento()!= 0) { // Promocion de Descuento
				double mtoDcto = prod.getPrecioRegular() * (promocionAplicada.getPorcentajeDescuento() / 100);
				result.addElement(new Double(prod.getPrecioRegular() - mtoDcto));
			} else {
				if (promocionAplicada.getPrecioFinal() != 0) { // Promocion de Precio a Precio
					result.addElement(new Double(promocionAplicada.getPrecioFinal()));
				}
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("verificarPromociones(Producto) - end");
		}
		return result;
	}
	
	/**
	 * Asigna el cliente indicado a la venta. Si el cliente no es un afiliado se lanza una excepción indicandola eventualidad. 
	 * En caso de ser afiliado, valida que si es un COLABORADOR le aplica el descto a COLABORADOR a la venta.
	 * @param codigo
	 * @param autorizante
	 * */
	public void asignarCliente(String codigo, String autorizante) throws AutorizacionExcepcion, AfiliadoUsrExcepcion, BaseDeDatosExcepcion, ClienteExcepcion, ConexionExcepcion {
/*		if (logger.isDebugEnabled()) {
			logger.debug("asignarCliente(String, String) - start");
		}

		Cliente clte = null;

		Sesion.setUbicacion("APARTADO","asignarCliente");
		clte = MediadorBD.obtenerCliente(codigo);
		
		// Verificamos que el cliente no sea el usuario actual
		if ((clte.getNumFicha()!=null) && Sesion.getUsuarioActivo().getNumFicha().equalsIgnoreCase(clte.getNumFicha()))
			throw new AfiliadoUsrExcepcion("El cliente no puede ser el cajero activo");
			
		// Verificamos que el cliente no sea el usuario que autoriza la funcion (Si aplica)
		if ((autorizante!=null) && (clte!=null) && autorizante.equalsIgnoreCase(clte.getNumFicha()))
			throw (new AutorizacionExcepcion ("La función no pudo ser autorizada.\nEl autorizante debe ser distinto al cliente asignado."));

		Auditoria.registrarAuditoria("Asignado Cliente " + clte.getCodCliente() + " a Apartado " + this.numServicio,'T');
		this.cliente = clte;	

		if (logger.isDebugEnabled()) {
			logger.debug("asignarCliente(String, String) - end");
		}*/
	}

	/**
	 * Método getDetallesServicio
	 * 
	 * @return Vector
	 */
	public Vector getDetallesServicio() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDetallesServicio() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDetallesServicio() - end");
		}
		return detallesServicio;
	}

	/**
	 * Método getFechaServicio
	 * 
	 * @return Date
	 */
	public Date getFechaServicio() {
		if (logger.isDebugEnabled()) {
			logger.debug("getFechaServicio() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getFechaServicio() - end");
		}
		return fechaServicio;
	}

	/**
	 * Método getCliente
	 * 
	 * @return Cliente
	 */
	public Cliente getCliente() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCliente() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getCliente() - end");
		}
		return cliente;
	}

	/**
	 * Método getCodCajero
	 * 
	 * @return String
	 */
	public String getCodCajero() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCodCajero() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getCodCajero() - end");
		}
		return codCajero;
	}

	/**
	 * Método getCodTienda
	 * 
	 * @return int
	 */
	public int getCodTienda() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCodTienda() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getCodTienda() - end");
		}
		return codTienda;
	}

	/**
	 * Método getEstadoServicio
	 * 
	 * @return char
	 */
	public char getEstadoServicio() {
		if (logger.isDebugEnabled()) {
			logger.debug("getEstadoServicio() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getEstadoServicio() - end");
		}
		return estadoServicio;
	}

	/**
	 * Método getHoraFin
	 * 
	 * @return Time
	 */
	public Time getHoraFin() {
		if (logger.isDebugEnabled()) {
			logger.debug("getHoraFin() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getHoraFin() - end");
		}
		return horaFin;
	}

	/**
	 * Método getHoraInicia
	 * 
	 * @return Time
	 */
	public Time getHoraInicia() {
		if (logger.isDebugEnabled()) {
			logger.debug("getHoraInicia() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getHoraInicia() - end");
		}
		return horaInicia;
	}

	/**
	 * Método getLineasFacturacion
	 * 
	 * @return int
	 */
	public int getLineasFacturacion() {
		if (logger.isDebugEnabled()) {
			logger.debug("getLineasFacturacion() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getLineasFacturacion() - end");
		}
		return lineasFacturacion;
	}

	/**
	 * Método getMontoImpuesto
	 * 
	 * @return double
	 */
	public double getMontoImpuesto() {
		if (logger.isDebugEnabled()) {
			logger.debug("getMontoImpuesto() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getMontoImpuesto() - end");
		}
		return montoImpuesto;
	}

	/**
	 * Método getNumServicio
	 * 
	 * @return int
	 */
	public int getNumServicio() {
		if (logger.isDebugEnabled()) {
			logger.debug("getNumServicio() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getNumServicio() - end");
		}
		return numServicio;
	}

	/**
	 * Método getTipoServicio
	 * 
	 * @return String
	 */
	public String getTipoServicio() {
		if (logger.isDebugEnabled()) {
			logger.debug("getTipoServicio() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getTipoServicio() - end");
		}
		return tipoServicio;
	}

	/**
	 * Método getMontoBase
	 * 
	 * @return double
	 */
	public double getMontoBase() {
		if (logger.isDebugEnabled()) {
			logger.debug("getMontoBase() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getMontoBase() - end");
		}
		return montoBase;
	}

	/**
	 * Método setEstadoServicio
	 * 
	 * @param c
	 */
	public void setEstadoServicio(char c) {
		if (logger.isDebugEnabled()) {
			logger.debug("setEstadoServicio(char) - start");
		}

		estadoServicio = c;

		if (logger.isDebugEnabled()) {
			logger.debug("setEstadoServicio(char) - end");
		}
	}

	


}