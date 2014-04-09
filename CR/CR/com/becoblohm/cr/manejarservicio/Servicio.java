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

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.AfiliadoUsrExcepcion;
import com.becoblohm.cr.excepciones.AutorizacionExcepcion;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ClienteExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.ProductoExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.extensiones.ActualizadorPreciosSACFactory;
import com.becoblohm.cr.manejadorauditoria.Auditoria;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.manejarventa.CondicionVenta;
import com.becoblohm.cr.manejarventa.Detalle;
import com.becoblohm.cr.manejarventa.Producto;
import com.becoblohm.cr.manejarventa.Promocion;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.utiles.MathUtil;

/** 
 * Descripción: 
 * 		Superclase de Servicios. Maneja las operaciones con Servicios (Sean Apartados,
 * Cotizaciones, etc.)
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
* Fecha: agosto 2011
*/
public abstract class Servicio implements Serializable{
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
	protected Vector<DetalleServicio> detallesServicio;
	
	//	ACTUALIZACION BECO: 22/07/2008
	protected double descuentoCorporativoAplicado =0.0; 
	protected int codDescuentoCorporativo = 0;
	private Vector<?> detallesAEliminar = new Vector<Object>();
	private Promocion promocionProductoGratis;
	
	/**
	 * Constructor de la Clase Servicio.
	 * @param tda Numero de la Tienda.
	 * @param tipoServ String que representa el código del tipo de Servicio.
	 * @param fecha Fecha del creación del Servicio.
	 * @param hora Hora del creación del Servicio.
	 * @param cajero Codigo del cajero.
	 * @param edoServ Estado del Servicio (Activo, Anulado, Finalizado).
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
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
		this.detallesServicio = new Vector<DetalleServicio>();
	}
	
	/**
	 * 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Servicio() {
		this.detallesServicio = new Vector<DetalleServicio>();
	}
	
	/**
	 * Métodos abstractos de la clase
	 */

	/**
	 * Anula un producto de un renglon del Servicio.
	 * @param renglon Renglon del producto a anular.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	abstract Vector<Object> anularProducto(int renglon) throws ProductoExcepcion;

	/**
	 * Elimina una cantidad de productos de un renglon.
	 * @param renglon Renglon del producto a eliminar.
	 * @param cantidad Cantidad de productos a eliminar. Si existen menos productos que el 
	 * 		  especificado en la cantidad se elimina el renglon.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	abstract Vector<Object> anularProducto(int renglon, float cantidad) throws ProductoExcepcion;

	/**
	 * Finaliza el servicio. Registra los datos en la Base de datos.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'  //No se pudo determinar, la implementacion
	* siempre devuelve null y la función no es usada
	* Fecha: agosto 2011
	*/
	protected abstract Vector<Object> finalizarServicio() throws BaseDeDatosExcepcion, ConexionExcepcion;

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
	 * 
	 * NOTA: cambiado a public por jgraterol para módulo de promociones
	 */
	public void actualizarMontoServ() {
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
	 * Actualiza el Monto del Servicio, calcula el nuevo monto a partir del detalle.
	 * 
	 * NOTA: cambiado a public por jgraterol para módulo de promociones
	 */
	public double getMontoServ() {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarMontoServ() - start");
		}
		
		double base = 0.0;
		double precioFinal = 0.0;
		double montoImpuesto = 0.0;
		
		for (int i=0; i<detallesServicio.size(); i++) {
			precioFinal += ((DetalleServicio) detallesServicio.elementAt(i)).getPrecioFinal() * ((DetalleServicio) detallesServicio.elementAt(i)).getCantidad();
			montoImpuesto += ((DetalleServicio) detallesServicio.elementAt(i)).getMontoImpuesto() * ((DetalleServicio) detallesServicio.elementAt(i)).getCantidad();
		}
		
		montoImpuesto = MathUtil.roundDouble(montoImpuesto);
		base = MathUtil.roundDouble(precioFinal);

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarMontoServ() - end");
		}
		return base+montoImpuesto;
	}
	
	/**
	 * Verifica si se aplican promociones a los productos que se encuentran en el detalle de servicio.
	 * @param prod Producto a chequear promociones
	 * @return Vector - Vector con el codigo de promocion y precio final de producto si aplica promocion
	 */
	protected Vector<Object> verificarPromociones(Producto prod) {
		if (logger.isDebugEnabled()) {
			logger.debug("verificarPromociones(Producto) - start");
		}

		return (new ActualizadorPreciosSACFactory()).getActualizadorPreciosSACInstance().verificarPromociones(prod);
	}
	
	/**
	 * Asigna el cliente indicado a la venta. Si el cliente no es un afiliado se lanza una excepción indicandola eventualidad. 
	 * En caso de ser afiliado, valida que si es un COLABORADOR le aplica el descto a COLABORADOR a la venta.
	 * @param codigo
	 * @param autorizante
	 * */
	public void asignarCliente(String codigo, String autorizante) throws AutorizacionExcepcion, AfiliadoUsrExcepcion, BaseDeDatosExcepcion, ClienteExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
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
		}
	}

	/**
	 * Método getDetallesServicio
	 * 
	 * @return Vector
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' e 'Iterator'
	* Fecha: agosto 2011
	*/
	public Vector<DetalleServicio> getDetallesServicio() {
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
	/**
	 * @param string
	 */
	public void setCodCajero(String string) {
		codCajero = string;
	}
	/**
	 * Agrupa los detalles iguales y los coloca al final del vector de detalles original
	 * @param posiciones Vector de posiciones donde se encuentran los productos a agrupar
	 * @return void
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void acumularDetalles(Vector<Integer> posiciones) {
		if (logger.isDebugEnabled()) {
			logger.debug("acumularDetalles(Vector) - start");
		}

		Vector<Integer> paraCaptura = new Vector<Integer>(2);
		
		// Acumulamos los productos por vendedores (Si es el caso)
		for (int i=0; i<posiciones.size();i++) {
			if (((Integer)posiciones.elementAt(i))!=null) {
				int posicionI = ((Integer)posiciones.elementAt(i)).intValue();
			
				for (int j=i+1; j<posiciones.size(); j++) {
					if (((Integer)posiciones.elementAt(j))!=null) {
						int posicionJ = ((Integer)posiciones.elementAt(j)).intValue();

						// Primero verificamos los tipos de captura entre detalles
						paraCaptura.addElement(((Integer)posiciones.elementAt(i)));
						paraCaptura.addElement(((Integer)posiciones.elementAt(j)));
						verificarTipoCaptura(paraCaptura);
								
						((DetalleServicio)detallesServicio.elementAt(posicionI)).incrementarCantidad(((DetalleServicio)detallesServicio.elementAt(posicionJ)).getCantidad());
						//Abonos de DetalleServicio para Lista de Regalos
						detallesServicio.set(posicionJ,null);
						posiciones.set(j,null);
					}				
					paraCaptura.clear();
				}
				
			}
		}
			
		// Eliminamos los nulos del vector de posiciones de condiciones normales 'N'
		while (posiciones.removeElement(null));
			
		// Colocamos los detalles al final del vector original.
		DetalleServicio detalleServ = null;
		int pos;
			
		// Movemos los detalles para colocarlos al final del vector
		for (int i=0; i<posiciones.size(); i++){
			pos = ((Integer)posiciones.elementAt(i)).intValue();
			detalleServ = (DetalleServicio) detallesServicio.elementAt(pos);
			detallesServicio.addElement(detalleServ);
		
			detallesServicio.set(pos,null);
			posiciones.set(i,null);
		}
			
		if (logger.isDebugEnabled()) {
			logger.debug("acumularDetalles(Vector) - end");
		}
	}
	/**
	 * Dado un Vector de posiciones del detalle chequea los tipos de captura para colocarlos Teclado, 
	 * Escaner o Mixto.
	 * @param posiciones Vector a chequear los tipos de captura.
	 */
	public void verificarTipoCaptura (Vector<Integer> posiciones) {
		if (logger.isDebugEnabled()) {
			logger.debug("verificarTipoCaptura(Vector) - start");
		}

		// Verificamos el tipo de captura del producto. Si hubo más de un tipo de captura los coloca 'M'(Mixto) para todas las ocurrencias del mismo
		for (int i=0; i<posiciones.size();i++) {
			int posicionI = ((Integer)posiciones.elementAt(i)).intValue();
			
			for (int j=i+1; j<posiciones.size(); j++) {
				int posicionJ = ((Integer)posiciones.elementAt(j)).intValue();
				if (!(((DetalleServicio)detallesServicio.elementAt(posicionI)).getTipoCaptura()).equals(((DetalleServicio)detallesServicio.elementAt(posicionJ)).getTipoCaptura())) {
					((DetalleServicio)detallesServicio.elementAt(posicionI)).setTipoCaptura(Sesion.capturaMixto);
				}
			}
		}		

		if (logger.isDebugEnabled()) {
			logger.debug("verificarTipoCaptura(Vector) - end");
		}
	}
	
	public void actualizarPreciosDetalle (Producto p) {
		actualizarPreciosDetalle(p,true);
	}
	
	public void actualizarPreciosDetalle (Producto p, boolean aplicarPromociones) {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarPreciosDetalle(Producto) - start");
		}
		(new ActualizadorPreciosSACFactory()).getActualizadorPreciosSACInstance().actualizarPrecios(p, this, aplicarPromociones);

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarPreciosDetalle(Producto) - end");
		}
	}
	
	/**
	 * Chequea los descuentos aplicables al producto especificado, verifica prioridades de los mismos y aplica el descuento.
	 * @param prod Producto al que se le aplicara el descuento.
	 * @param detallesN Vector de posiciones del producto .
	 * @param cantidad Cantidad de productos presentes en el detalle.
	 * @return Vector - Vector donde la posición 0 son las posiciones de los detalles cuya condición es distinta a la de empaque y en la posición 1 se encuentran los que tienen condición empaque
	 */
	/*public Vector aplicarCondicionDeVenta(Producto prod, Vector detallesN, float cantidad) {
		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarCondicionDeVenta(Producto, Vector, float) - start");
		}
		
		// Variables utilizables para los calculos
		int puntoDeChequeo = 0;
		//float cantidadDetalleActual;
		
		// Variable de retorno
		Vector retorno = new Vector();
		Vector nuevosEmpaques = new Vector();
		Vector nuevosNoEmpaques = new Vector();
		
		// Buscamos las condiciones de venta que aplican al producto
		Vector descuentos = buscarCondicionesDeVenta(prod);
		
		// Verificamos si aplican condiciones de venta al producto, de no ser así se colocan como normales en los detalles
		if ((descuentos.size() > 0) && (cantidad > 0)) {
			// Obtenemos el mejor descuento para el producto
			int posicionMejor = 0;
			Vector dctoActual = new Vector();
			double precioFinal = Double.MAX_VALUE;
			for (int i=0; i<descuentos.size(); i++) {
				dctoActual = (Vector)descuentos.elementAt(i);
				if (precioFinal > ((Double)dctoActual.elementAt(0)).doubleValue()) {
					posicionMejor = i;
					precioFinal = ((Double)dctoActual.elementAt(0)).doubleValue();
				}
			}
				
			// Si se trata de empaque, aplicamos el empaque unicamente a la cantidad de productos exacta
			// el resto seguira con el algoritmo de calculo NoEmpaque
			if (((String)((Vector)descuentos.elementAt(posicionMejor)).elementAt(1)).equals(Sesion.condicionEmpaque)) {
				
				// Separamos en empaques y no empaques y aplicamos el descuento a los empaques
				float prodsEmpaque = cantidad - (cantidad % prod.getCantidadVentaEmpaque());	
				cantidad -= prodsEmpaque;
							
				while (prodsEmpaque > 0) {
					// Si existen empaques, creo el nuevo detalle con el empaque por cada detallesN
					DetalleServicio det = ((DetalleServicio)detallesServicio.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue()));
					String tipCaptura = det.getTipoCaptura();
					float cantidadDetalleActual = det.getCantidad();
					float cantidadAsignar;
					double pFinal = ((Double)dctoActual.elementAt(0)).doubleValue();
					
					if (prodsEmpaque >= cantidadDetalleActual) {
						cantidadAsignar = cantidadDetalleActual;
						puntoDeChequeo++;
					} else {
						cantidadAsignar = prodsEmpaque;
						det.setCantidad(cantidadDetalleActual - cantidadAsignar);
					}
					prodsEmpaque -= cantidadAsignar;
					pFinal = MathUtil.roundDouble(pFinal);
					DetalleServicio nuevoDetalle = new DetalleServicio(prod, cantidadAsignar,
								(String)dctoActual.elementAt(1), null, pFinal, this.calcularImpuesto(prod, pFinal),
								tipCaptura, -1, prod.getTipoEntrega());
					// Lista de Regalos - Inicio 
					nuevoDetalle.setAbonos(det.getAbonos());
					nuevoDetalle.setCantVendidos(det.getCantVendidos());
					// Lista de Regalos - Final 
					detallesServicio.addElement(nuevoDetalle);
					nuevosEmpaques.addElement(new Integer(detallesServicio.size() - 1));
				}
					
				// Elimino la condicion de venta empaque del vector de condiciones de venta
				descuentos.remove(posicionMejor);
					
				// Busco el mejor de los descuentos que restan
				posicionMejor = 0;
				precioFinal = Double.MAX_VALUE;
				for (int i=0; i<descuentos.size(); i++) {
					dctoActual = (Vector)descuentos.elementAt(i);
					if (precioFinal > ((Double)dctoActual.elementAt(0)).doubleValue()) {
						posicionMejor = i;
						precioFinal = ((Double)dctoActual.elementAt(0)).doubleValue();
					}
				}
			}
				
			// Si quedan productos y descuentos para ser aplicados
			if ((cantidad > 0) && (descuentos.size() > 0)) {
				while (puntoDeChequeo < detallesN.size()) {
					// Seguimos con los descuentos y aplicamos el mejor.
					DetalleServicio det = ((DetalleServicio)detallesServicio.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue()));
					String tipCaptura = det.getTipoCaptura();
					dctoActual = (Vector)descuentos.elementAt(posicionMejor);
					double pFinal = ((Double)dctoActual.elementAt(0)).doubleValue();
					float cantidadAsignar = det.getCantidad();
					DetalleServicio nuevoDetalleServ;
					if (((String)((Vector)descuentos.elementAt(posicionMejor)).elementAt(1)).equals(Sesion.condicionPromocion)) {
						// Si se trata de una promocion
						pFinal = MathUtil.roundDouble(pFinal);
						nuevoDetalleServ = new DetalleServicio(prod, cantidadAsignar,
											(String)dctoActual.elementAt(1), null, pFinal, 
											this.calcularImpuesto(prod, pFinal), tipCaptura,
											((Integer)dctoActual.elementAt(2)).intValue(), prod.getTipoEntrega());
						// Lista de Regalos - Inicio 
						nuevoDetalleServ.setAbonos(det.getAbonos());
						nuevoDetalleServ.setCantVendidos(det.getCantVendidos());
						// Lista de Regalos - Final 
						detallesServicio.addElement(nuevoDetalleServ);
						nuevosNoEmpaques.addElement(new Integer(detallesServicio.size() - 1));
						cantidad -= cantidadAsignar;
						puntoDeChequeo++;
					}
				}
			}
		}
		
		// Colocamos las condiciones de venta normales (Si existen)
		if (cantidad > 0) {
			while (puntoDeChequeo < detallesN.size()) {
				DetalleServicio det = ((DetalleServicio)detallesServicio.elementAt(((Integer)detallesN.elementAt(puntoDeChequeo)).intValue()));
				String tipCaptura = det.getTipoCaptura();
				double pFinal = prod.getPrecioRegular();
				pFinal = MathUtil.roundDouble(pFinal);
				float cantidadAsignar = det.getCantidad();
				DetalleServicio nuevoDetalleServ = new DetalleServicio(
								prod,
								cantidadAsignar,
								Sesion.condicionNormal,
								null,
								pFinal,
								this.calcularImpuesto(prod,pFinal),
								tipCaptura,
								-1, det.getTipoEntrega());
				// Lista de Regalos - Inicio 
				nuevoDetalleServ.setAbonos(det.getAbonos());
				nuevoDetalleServ.setCantVendidos(det.getCantVendidos());
				// Lista de Regalos - Final 
				detallesServicio.addElement(nuevoDetalleServ);
				nuevosNoEmpaques.addElement(new Integer(detallesServicio.size() - 1));
				puntoDeChequeo++;
			}
		}

		// Armamos el vector de retorno
		retorno.addElement(nuevosNoEmpaques);
		retorno.addElement(nuevosEmpaques);

		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarCondicionDeVenta(Producto, Vector, float) - end");
		}
		return retorno;
	}*/

	/**
	 * Construye la matriz de descuentos aplicables al producto especificado, con sus condiciones de venta respectivas.
	 * @param prod Producto al que se le aplicará el descuento.
	 * @return Vector Vector de dos o tres posiciones que indica: 
	 * 				   - posición 0 posee la información del descuento promocional.
	 * 				   - posición 1 posee la información del descuento a empaque
	 */
	/*private Vector buscarCondicionesDeVenta(Producto prod){
		if (logger.isDebugEnabled()) {
			logger.debug("buscarCondicionesDeVenta(Producto) - start");
		}

		double dctoEmpaque;
		Vector descuentos = new Vector();
		
		// Calculamos las promociones
		Vector datosPromocion = verificarPromociones(prod);
		if (datosPromocion.size() > 0) {
			// Existen promociones vigentes para el producto
			Vector promo = new Vector(2);
			promo.addElement((Double)datosPromocion.elementAt(1));
			promo.addElement(Sesion.condicionPromocion);
			promo.addElement((Integer)datosPromocion.elementAt(0));
			descuentos.addElement(promo);
		}
		
		// Calculamos el precio de condicion empaque
		dctoEmpaque = prod.getPrecioRegular() * (prod.getDesctoVentaEmpaque()/100);
		if (dctoEmpaque > 0) {
			// Agregamos el empaque al vector de condiciones de venta
			Vector empaque = new Vector(2);
			empaque.addElement(new Double(prod.getPrecioRegular() - dctoEmpaque));
			empaque.addElement(Sesion.condicionEmpaque);
			descuentos.addElement(empaque);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("buscarCondicionesDeVenta(Producto) - end");
		}
		return descuentos;
	}*/
	
	/**
	 * Método calcularImpuesto.
	 * 		Calcula el impuesto aplicable al precio del producto. Verifica si el cliente 
	 * 		de la venta es un cliente exento y si es asd evuelve 0.0
	 * @param prod Producto al que se le va a aplicar el impuesto
	 * @param pFinal double que representa el precio final sobre el cual se va hacer el calculo del impuesto. 
	 * 		  Ejm. Con promociones, descto a COLABORADOR, etc.
	 * @throws 
	 * 
	 * NOTA jgraterol: cambiado a public por modulo de promociones
	 */
	public double calcularImpuesto(Producto prod, double pFinal) {
		if (logger.isDebugEnabled()) {
			logger.debug("calcularImpuesto(Producto, double) - start");
		}

		double pImpuesto = 0.0;
		
		//Verificamos si en la venta el cliente activo es un cliente exento
		if(!this.getCliente().isExento()) {
			// Calculamos el impuesto
			pImpuesto = MathUtil.stableMultiply(MathUtil.roundDouble(pFinal), ((prod.getImpuesto().getPorcentaje()) / 100));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("calcularImpuesto(Producto, double) - end");
		}
		return pImpuesto;
	}
	
	/**
	 * Obtiene la posicion de los productos con codigo codProducto presente en el detalle de transaccion
	 * @param codProducto Codigo del producto buscado
	 * @return Vector - Vector con las posiciones del producto en el detalle 
	 * @throws ProductoExcepcion Si el producto no se encuentra en el detalle de la venta actual
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Integer> obtenerRenglones(String codProducto, boolean isCodigoExterno) throws ProductoExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerRenglones(String) - start");
		}

		Vector<Integer> result = new Vector<Integer>(); //Vector que contendrá el resultado del método
		String codInterno = isCodigoExterno ? MediadorBD.obtenerCodigoInterno(codProducto) : codProducto;
		if (codInterno == null) throw (new ProductoExcepcion("El producto " + codProducto + " no se encuentra en el detalle"));
		
		// Para cada detalle de transaccion presente en la venta
		for (int i=0; i<this.detallesServicio.size();i++) {
			String codProd = ((DetalleServicio)this.detallesServicio.elementAt(i)).getProducto().getCodProducto();
			// Si el codigo de producto es el que se tiene como parametro se agrega al vector de resultados
			if ((codProd.equalsIgnoreCase(codProducto))||(codProd.equalsIgnoreCase(codInterno)))
				result.addElement(new Integer(i));
		}
	
		if (result.size()>0)
		{
			if (logger.isDebugEnabled()) {
				logger.debug("obtenerRenglones(String) - end");
			}
			return result;
		}
		else
			throw (new ProductoExcepcion("El producto " + codProducto + " no se encuentra en el detalle"));
	}
	
	/**
	 *  
	 * 
	 * */
	public void quitarCliente() {
		Cliente clte = new Cliente();
		this.cliente = clte;	
	}
	
	/****************************************************************************************
	 * Métodos agregados por el módulo de promociones
	 ****************************************************************************************/
	
	/**
	 * Actualiza los precios de todos los detalles
	 * @author jgraterol
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void actualizarPreciosServicio() throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarPrecios(boolean) - start");
		}

		Vector<DetalleServicio> productos = new Vector<DetalleServicio>(); 
		boolean registrado= false;
		String codP = "";
	
		
		Vector<String> condicionesCorporativo = new Vector<String>();
		condicionesCorporativo.addElement(Sesion.condicionDescuentoCorporativo);
		
		// Recorremos el vector de detalles para obtener los distintos productos contenidos en el mismo 
		for(int i=0; i<this.getDetallesServicio().size(); i++){
			if(!((DetalleServicio)this.getDetallesServicio().elementAt(i)).isCondicionEspecial() &&
					!((DetalleServicio)this.getDetallesServicio().elementAt(i)).contieneAlgunaCondicion(condicionesCorporativo)){
				((DetalleServicio)this.getDetallesServicio().elementAt(i)).setCondicionVentaPromociones(new Vector<CondicionVenta>());
				((DetalleServicio)this.getDetallesServicio().elementAt(i)).setCondicionVenta(Sesion.condicionNormal);
			}
						
			if (productos.size()== 0){
				productos.addElement(this.getDetallesServicio().elementAt(i));
			} else {
				registrado = false;
				for(int j=0; j<productos.size();j++) {
					codP = ((DetalleServicio)productos.elementAt(j)).getProducto().getCodProducto();
					if(codP.equals((((DetalleServicio)this.getDetallesServicio().elementAt(i)).getProducto()).getCodProducto())){
						registrado = true;	
						break;					
					}
				}
				if(registrado == false){
					productos.addElement(this.getDetallesServicio().elementAt(i));
				}			
			}
		}
	
		for (int i=0; i<productos.size(); i++) {
			Producto prod = ((DetalleServicio)productos.elementAt(i)).getProducto();
				
			(new ActualizadorPreciosSACFactory()).getActualizadorPreciosSACInstance().actualizarPreciosExt(prod, this, true);
		}
		
		this.actualizarMontoServ();

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarPrecios(boolean) - end");
		}
	}

	/**
	 * @return el codDescuentoCorporativo
	 */
	public int getCodDescuentoCorporativo() {
		return codDescuentoCorporativo;
	}

	/**
	 * @param codDescuentoCorporativo el codDescuentoCorporativo a establecer
	 */
	public void setCodDescuentoCorporativo(int codDescuentoCorporativo) {
		this.codDescuentoCorporativo = codDescuentoCorporativo;
	}

	/**
	 * @return el descuentoCorporativoAplicado
	 */
	public double getDescuentoCorporativoAplicado() {
		return descuentoCorporativoAplicado;
	}

	/**
	 * @param descuentoCorporativoAplicado el descuentoCorporativoAplicado a establecer
	 */
	public void setDescuentoCorporativoAplicado(double descuentoCorporativoAplicado) {
		this.descuentoCorporativoAplicado = descuentoCorporativoAplicado;
	}

	/**
	 * @return el detallesAEliminar
	 */
	public Vector getDetallesAEliminar() {
		return detallesAEliminar;
	}

	/**
	 * @param detallesAEliminar el detallesAEliminar a establecer
	 */
	public void setDetallesAEliminar(Vector<?> detallesAEliminar) {
		this.detallesAEliminar = detallesAEliminar;
	}

	/**
	 * @return el promocionProductoGratis
	 */
	public Promocion getPromocionProductoGratis() {
		return promocionProductoGratis;
	}

	/**
	 * @param promocionProductoGratis el promocionProductoGratis a establecer
	 */
	public void setPromocionProductoGratis(Promocion promocionProductoGratis) {
		this.promocionProductoGratis = promocionProductoGratis;
	}

	@SuppressWarnings("unchecked")
	public void setDetallesServicio(Vector<? extends Detalle> detallesServicio) {
		this.detallesServicio = (Vector<DetalleServicio>)detallesServicio;
	}

	public void setLineasFacturacion(int lineasFacturacion) {
		this.lineasFacturacion = lineasFacturacion;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	/****************************************************************************************
	 * Fin métodos agregados por el módulo de promociones
	 ****************************************************************************************/
}
