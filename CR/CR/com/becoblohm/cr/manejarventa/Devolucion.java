/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.manejarventa
 * Programa   : Devolucion.java
 * Creado por : gmartinelli
 * Creado en  : 18/03/2004 13:08:22 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versi�n     : 2.0
 * Fecha       : 11-09-2006
 * Analista    : yzambrano
 * Descripci�n : Devoluciones en espera
 * =============================================================================
 */
package com.becoblohm.cr.manejarventa;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.AfiliadoUsrExcepcion;
import com.becoblohm.cr.excepciones.AutorizacionExcepcion;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ClienteExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.DevolucionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.LineaExcepcion;
import com.becoblohm.cr.excepciones.ProductoExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.manejadorauditoria.Auditoria;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.extensiones.ActualizadorPreciosSACFactory;
import com.becoblohm.cr.extensiones.ManejadorReportesFactory;
import com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.mediadoresbd.BaseDeDatosVenta;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.sincronizador.HiloSyncTransacciones;
import com.becoblohm.cr.utiles.MathUtil;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.crprinterdriver.PrinterNotConnectedException;

/** 
 * Descripci�n: 
 * 		Subclase de Transaccion. Maneja las devoluciones. Se manejan accesos a base de datos para registrar
 * las mismas.
 */
/*
* En esta clase se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
* S�lo se parametriz� el tipo de dato contenido en los 'Vector'
* Fecha: agosto 2011
*/
public class Devolucion extends Transaccion implements Serializable {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Devolucion.class);
	
	private boolean clienteOriginalDeVenta = true;
	private Venta ventaOriginal;
	private Vector<Devolucion> devolucionesRealizadas;
	private Vector<Pago> pagos;
	//private Vector productosDevueltosPorDetalle;
	
	/**
	 *  Constructor de la clase usado para obtener una devolucion en modo consulta.
	 * @param tda N�mero de tienda
	 * @param fechaT Fecha de la transacci�n
	 * @param caja N�mero de la caja
	 * @param numTransacci�n N�mero de la transacci�n
	 * @param local Indica si la transacci�n debe ser recuperada de manera local o remota.
	 */
	public Devolucion (int tda, String fechaT, int caja, int numTransaccion, boolean local) throws BaseDeDatosExcepcion, ConexionExcepcion {
		this(BaseDeDatosVenta.obtenerDevolucion(tda, fechaT, caja, numTransaccion, local), null);
	}
	
	/*
	* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
	* S�lo se parametriz� el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Devolucion (Vector<Vector<?>> devolucionObtenida, Venta v) throws ConexionExcepcion, BaseDeDatosExcepcion {
		super();
		
		// Obtenemos la venta de la BaseDeDatos
		Vector<Object> cabeceraDevolucion = (Vector)devolucionObtenida.elementAt(0);
		Vector<Vector<Object>> detallesDevolucion = (Vector)devolucionObtenida.elementAt(1);
	
		// Armamos la cabecera de venta
		super.codTienda = ((Integer)cabeceraDevolucion.elementAt(0)).intValue();
		super.fechaTrans = ((Date)cabeceraDevolucion.elementAt(1));
		super.numCajaInicia = ((Integer)cabeceraDevolucion.elementAt(2)).intValue();
		super.numRegCajaInicia = ((Integer)cabeceraDevolucion.elementAt(3)).intValue();
		super.numCajaFinaliza = ((Integer)cabeceraDevolucion.elementAt(4)).intValue();;
		super.numTransaccion = ((Integer)cabeceraDevolucion.elementAt(5)).intValue();;
		super.tipoTransaccion = ((String)cabeceraDevolucion.elementAt(6)).toCharArray()[0];
		super.horaInicia = (Time)cabeceraDevolucion.elementAt(7);
		super.horaFin = (Time)cabeceraDevolucion.elementAt(8);
		if (((String)cabeceraDevolucion.elementAt(9)) == null) {
			super.cliente = new Cliente(); 
		} else {
			try {
				super.cliente = MediadorBD.obtenerCliente((String)cabeceraDevolucion.elementAt(9));
			} catch (ConexionExcepcion e) {
				logger.error("Devolucion(Vector, Venta)", e);
			} catch (BaseDeDatosExcepcion e) {
				logger.error("Devolucion(Vector, Venta)", e);
			} catch (AfiliadoUsrExcepcion e) {
				logger.error("Devolucion(Vector, Venta)", e);
			} catch (ClienteExcepcion e) {
				logger.error("Devolucion(Vector, Venta)", e);
			}
		}	
		super.codCajero = (String)cabeceraDevolucion.elementAt(10);
		super.montoBase = ((Double)cabeceraDevolucion.elementAt(12)).doubleValue();
		super.montoImpuesto = ((Double)cabeceraDevolucion.elementAt(13)).doubleValue();
		super.montoVuelto = ((Double)cabeceraDevolucion.elementAt(14)).doubleValue();
		super.montoRemanente = ((Double)cabeceraDevolucion.elementAt(15)).doubleValue();
		super.lineasFacturacion = ((Integer)cabeceraDevolucion.elementAt(16)).intValue();
		super.checksum = ((Integer)cabeceraDevolucion.elementAt(17)).intValue();
		super.estadoTransaccion = ((String)cabeceraDevolucion.elementAt(18)).toCharArray()[0];
		super.codAutorizante = (String)cabeceraDevolucion.elementAt(23);
		if (null == v) {
			// Para recuperar la venta original
			int tiendaVta = ((Integer)cabeceraDevolucion.elementAt(19)).intValue();
			Date fechaVta = (Date)cabeceraDevolucion.elementAt(20);
			int cajaVta = ((Integer)cabeceraDevolucion.elementAt(21)).intValue();
			int numTrVta = ((Integer)cabeceraDevolucion.elementAt(22)).intValue();
			ventaOriginal = new Venta(tiendaVta, SavedTransaccion.getFechaSQL(fechaVta), cajaVta, numTrVta, true);
		} else {
			// Simplemente la asignamos
			ventaOriginal = v;
		}
	
		// Armamos el detalle de la venta
		for (int i=0; i<detallesDevolucion.size(); i++) {
			Vector<Object> detalleDevolucion = detallesDevolucion.elementAt(i);
			String codProd = (String)detalleDevolucion.elementAt(0);
			String codVend = (String)detalleDevolucion.elementAt(3);
			String tipoCap = (String)detalleDevolucion.elementAt(8);
			float cant = ((Float)detalleDevolucion.elementAt(2)).floatValue();
			try {
				this.detallesTransaccion.addElement(new DetalleTransaccion(codProd, codVend, tipoCap, cant, this.fechaTrans, this.horaInicia, true));
			} catch (ProductoExcepcion e1) {
				logger.error("Devolucion(Vector, Venta)", e1);

				throw (new BaseDeDatosExcepcion("Error recuperando devoluciones realizadas sobre transaccion"));
			} catch (BaseDeDatosExcepcion e1) {
				logger.error("Devolucion(Vector, Venta)", e1);

				throw (new BaseDeDatosExcepcion("Error de conexion con BaseDeDatos recuperando devoluciones realizadas sobre transaccion"));
			}
		
			// Agregamos los otros parametros
			String condVenta = (String)detalleDevolucion.elementAt(1);
			String codSupervisor = ((String)detalleDevolucion.elementAt(4));
			double precioFinal = ((Double)detalleDevolucion.elementAt(5)).doubleValue();
			double impuesto = ((Double)detalleDevolucion.elementAt(6)).doubleValue();
			int codPromo = ((Integer)detalleDevolucion.elementAt(7)).intValue();
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setCondicionVenta(condVenta);
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setCodSupervisor(codSupervisor);
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setPrecioFinal(precioFinal);
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setMontoImpuesto(impuesto);
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setCodPromocion(codPromo);
		}		
	}
	/**
	 * Constructor de la clase Devolucion que llama al constructor de la superclase (Transaccion)
	 * y, ademas de la devolucion, obtiene la venta original y un vector de devoluciones que posee 
	 * la venta para ver qu� productos han sido devueltos y cuales no.
	 */
	/*
	* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
	* S�lo se parametriz� el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Devolucion(int tienda, String fechaT, int cajaOriginal, int numTransaccion, String autorizante,
					int nuevaTienda, Date nuevaFecha, int nuevaCaja, int nuevoRegistro, Time nuevaHora,
					String cajeroActivo)
		throws DevolucionExcepcion, AfiliadoUsrExcepcion, BaseDeDatosExcepcion, ClienteExcepcion, ConexionExcepcion {
		
		// Creamos la instancia de la nueva devolucion
		super(nuevaTienda, nuevaFecha, nuevaCaja, nuevoRegistro, nuevaHora, cajeroActivo, Sesion.DEVOLUCION, Sesion.PROCESO);
		
			// Verificamos que la transaccion no haya sido recuperada anteriormente para devolucion
			if (!BaseDeDatosVenta.isTransaccionRecuperada(tienda, fechaT, cajaOriginal, numTransaccion)) {
		
				// Cargamos los datos de la venta original
				ventaOriginal = new Venta(tienda, fechaT, cajaOriginal, numTransaccion, false);
			
				/*if (this.ventaOriginal.getCliente().getCodCliente() != null) 
					this.cliente = MediadorBD.obtenerCliente(this.ventaOriginal.getCliente().getCodCliente());
			
				// Chequeamos que el cliente no sea el usuario activo
				if ((this.cliente.getNumFicha()!=null) && cajeroActivo.equalsIgnoreCase(this.cliente.getNumFicha()))
					throw new AfiliadoUsrExcepcion("La Venta original posee como cliente al Usuario Activo.\nNo se pudo recuperar la venta para devolucion");
	
				// Chequeamos que el cliente no sea el usuario autorizante (Si aplica)
				if ((this.cliente.getNumFicha()!=null) && (autorizante!=null) && autorizante.equalsIgnoreCase(this.cliente.getNumFicha()))
					throw new AfiliadoUsrExcepcion("La Venta original posee como cliente al Usuario Autorizante.\nNo se pudo recuperar la venta para devolucion");
				*/
				setCodAutorizante(autorizante);
	
				// Observamos que no se haya anulado la venta original
				if (BaseDeDatosVenta.anuladaTransaccion(tienda, fechaT, cajaOriginal, numTransaccion, Sesion.ANULACION, true))
					throw new DevolucionExcepcion("La Transaccion " + numTransaccion + " se encuentra anulada");
				
				// Cargamos las devoluciones realizadas sobre la venta (Si existen)
				devolucionesRealizadas = BaseDeDatosVenta.cargarDevoluciones(tienda, this.ventaOriginal.fechaTrans, cajaOriginal, numTransaccion, ventaOriginal);
				
				for (int i=0; i<this.ventaOriginal.getDetallesTransaccion().size(); i++) {
					for (int j=0; j<devolucionesRealizadas.size(); j++) {
						for (int k=0; k<((Devolucion)devolucionesRealizadas.elementAt(j)).getDetallesTransaccion().size(); k++) {
							DetalleTransaccion detalleVen = (DetalleTransaccion)this.ventaOriginal.getDetallesTransaccion().elementAt(i);
							DetalleTransaccion detalleDev = (DetalleTransaccion)((Devolucion)this.devolucionesRealizadas.elementAt(j)).getDetallesTransaccion().elementAt(k);
							if ((detalleDev.getProducto().getCodProducto().equalsIgnoreCase(detalleVen.getProducto().getCodProducto())) 
							  &&(detalleDev.getCondicionVenta().equalsIgnoreCase(detalleVen.getCondicionVenta()))
							  &&(detalleDev.getPrecioFinal() == detalleVen.getPrecioFinal())
							  &&(detalleDev.getCantidad()<=detalleVen.getCantidad())) {
								detalleVen.setCantidad(detalleVen.getCantidad() - detalleDev.getCantidad());
							  } 	
						}
					}
				}
			
				for (int i=0; i<this.ventaOriginal.getDetallesTransaccion().size(); i++) {
					DetalleTransaccion detalleVen = (DetalleTransaccion)this.ventaOriginal.getDetallesTransaccion().elementAt(i);
					if (detalleVen.getCantidad() <= 0)
						this.ventaOriginal.getDetallesTransaccion().set(i,null);
				}
			
				while (this.ventaOriginal.getDetallesTransaccion().removeElement(null));
			
				// Si no se pueden hacer mas devoluciones sobre esta venta cancelamos la operacion
				if (this.ventaOriginal.getDetallesTransaccion().size()==0)
					throw new DevolucionExcepcion("Ya Fueron devueltos todos lo productos de esa transaccion");
				
				pagos = new Vector<Pago>();
			
				// Antes de Finalizar el constructor marcamos la transaccion en la tabla TRANSACCIONRECUPERADA
				BaseDeDatosVenta.marcarTransaccionRecuperada(tienda, fechaT, cajaOriginal, numTransaccion);
			} else {
				// La Devolucion se encuentra recuperada por alguna Caja en alguna Tienda
				throw new DevolucionExcepcion("Transacci�n " + numTransaccion + " ya Recuperada para Devolucion");
			}
	}
	
	/**
	 * Ingresa una nueva linea de producto. Verifica que exista en la venta original y que no ha
	 * sido devuelto antes.
	 * @param renglonVenta Codigo del producto a ingresar.
	 * @param tipoCaptura Tipo de captura del producto (Teclado, Escaner).
	 * @throws ProductoExcepcion Si no se encuentra el producto.
	 */
	/*
	* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
	* S�lo se parametriz� el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Object> devolverProducto(int renglonVenta, String tipoCaptura, float cantidad) throws ProductoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("devolverProducto(int, String, float) - start");
		}

		Vector<Object> result = new Vector<Object>();
		Sesion.setUbicacion("DEVOLUCION","devolverProducto");
		DetalleTransaccion detalle = (DetalleTransaccion)this.ventaOriginal.getDetallesTransaccion().elementAt(renglonVenta);
		
		// Chequeamos si la nueva cantidad es fraccionada, y si el producto permite cantidades fraccionadas
		if ((!detalle.getProducto().isIndicaFraccion())&&((cantidad % 1)!=0)) {
			throw (new ProductoExcepcion("Error al cambiar cantidad de producto.\nEl producto " + detalle.getProducto().getCodProducto() + " no admite cantidades fraccionadas (Decimales)"));
		}

		// Chequeamos si existen suficientes productos a devolver en el detalle especificado
		if (detalle.getCantidad() >= cantidad) {
			
			DetalleTransaccion dt = (DetalleTransaccion)detalle.clone();
			
			//Actualizado por jgraterol: 30/09/2009
			//No se admite devoluciones a producto con 100% de descuento
			Vector<String> condiciones = new Vector<String>();
			condiciones.addElement(Sesion.condicionDescontadoPorCombo);
			condiciones.addElement(Sesion.condicionProductoGratis);
			if(dt.contieneAlgunaCondicion(condiciones)){
				Vector<String> condicionesCC = new Vector<String>();
				condicionesCC.addElement(Sesion.condicionComboPorCantidades);
				condicionesCC.addElement(Sesion.condicionProductoGratis);
				CondicionVenta cv = dt.getPrimeraCondicion(condicionesCC);
				if(cv.getPorcentajeDescuento()==100.0){
					throw (new ProductoExcepcion("No se admiten devoluciones sobre el producto: "+dt.getProducto().getCodProducto()+"\n por haber recibido 100% de descuento en la venta original"));
				}
			}	
			/*if(dt.getPrecioFinal()==0){
				throw (new ProductoExcepcion("No se admiten devoluciones sobre el producto: "+dt.getProducto().getCodProducto()+" por haber recibido 100% de descuento en la venta original"));
			}*/
			
			dt.setCantidad(cantidad);
			this.detallesTransaccion.addElement(dt);
			((DetalleTransaccion)this.detallesTransaccion.lastElement()).setDctoEmpleado(detalle.getDctoEmpleado());
			detalle.setCantidad(detalle.getCantidad()-cantidad);
			// Registramos la auditor�a de esta funci�n
			Auditoria.registrarAuditoria("Seleccionado(s) " + cantidad + " producto(s) " + detalle.getProducto().getCodProducto() + " para devolver por " + tipoCaptura,'T');
			result.addElement(detalle.getProducto().getDescripcionCorta());
			result.addElement(new Double(detalle.getPrecioFinal() + detalle.getMontoImpuesto()));
			if (detalle.getCantidad()<=0) this.ventaOriginal.getDetallesTransaccion().remove(renglonVenta);
			this.acumularDetalles();
			while(this.detallesTransaccion.remove(null));
			this.actualizarMontoTransaccion();

			if (logger.isDebugEnabled()) {
				logger.debug("devolverProducto(int, String, float) - end");
			}
			return result;
		} else {
			throw (new ProductoExcepcion("La cantidad de producto excede a la cantidad en la transaccion original"));
		}
	}


	/**
	 * Ingresa una nueva linea de producto. Verifica que exista en la venta original y que no ha
	 * sido devuelto antes.
	 * @param renglonVenta Codigo del producto a ingresar.
	 * @param tipoCaptura Tipo de captura del producto (Teclado, Escaner).
	 * @throws ProductoExcepcion Si no se encuentra el producto.
	 */
	/*
	* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
	* S�lo se parametriz� el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Object> devolverProductoXCambio(int renglonVenta, String tipoCaptura, float cantidad) throws ProductoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("devolverProducto(int, String, float) - start");
		}

		Vector<Object> result = new Vector<Object>();
		Sesion.setUbicacion("DEVOLUCION","devolverProducto");
		DetalleTransaccion detalle = (DetalleTransaccion)this.ventaOriginal.getDetallesTransaccion().elementAt(renglonVenta);
		
		// Chequeamos si la nueva cantidad es fraccionada, y si el producto permite cantidades fraccionadas
		if ((!detalle.getProducto().isIndicaFraccion())&&((cantidad % 1)!=0)) {
			throw (new ProductoExcepcion("Error al cambiar cantidad de producto.\nEl producto " + detalle.getProducto().getCodProducto() + " no admite cantidades fraccionadas (Decimales)"));
		}

		// Chequeamos si existen suficientes productos a devolver en el detalle especificado
		if (detalle.getCantidad() >= cantidad) {
			this.detallesTransaccion.addElement(new DetalleTransaccion(detalle.getCodVendedor(), detalle.getProducto(),
				cantidad, detalle.getCondicionVenta(), detalle.getCodSupervisor(), detalle.getPrecioFinal(),
				detalle.getMontoImpuesto(), tipoCaptura, detalle.getCodPromocion(), detalle.getTipoEntrega(),cantidad));
			((DetalleTransaccion)this.detallesTransaccion.lastElement()).setDctoEmpleado(detalle.getDctoEmpleado());
			// Registramos la auditor�a de esta funci�n
			Auditoria.registrarAuditoria("Seleccionado(s) " + cantidad + " producto(s) " + detalle.getProducto().getCodProducto() + " para devolver por " + tipoCaptura,'T');
			result.addElement(detalle.getProducto().getDescripcionCorta());
			result.addElement(new Double(detalle.getPrecioFinal() + detalle.getMontoImpuesto()));
			this.actualizarMontoTransaccionXCambio();

			if (logger.isDebugEnabled()) {
				logger.debug("devolverProducto(int, String, float) - end");
			}
			return result;
		} else {
			throw (new ProductoExcepcion("La cantidad de producto excede a la cantidad en la transaccion original"));
		}
	}


	
	/**
	 * 
	 */
	private void acumularDetalles() {
		if (logger.isDebugEnabled()) {
			logger.debug("acumularDetalles() - start");
		}

		(new ActualizadorPreciosSACFactory()).getActualizadorPreciosSACInstance().acumularDetallesDevolucion(this); 
		/*for (int i=this.detallesTransaccion.size()-1;i>=0;i--) {
			if (this.detallesTransaccion.elementAt(i) != null) {
				for (int j=i-1; j>=0; j--) {
					DetalleTransaccion detalle1 = (DetalleTransaccion)this.detallesTransaccion.elementAt(i);
					DetalleTransaccion detalle2 = (DetalleTransaccion)this.detallesTransaccion.elementAt(j);
					if ((detalle2!=null)
					  &&(detalle1.getProducto().getCodProducto().equalsIgnoreCase(detalle2.getProducto().getCodProducto())) 
					  &&(detalle1.getCondicionVenta().equalsIgnoreCase(detalle2.getCondicionVenta()))
					  &&(detalle1.getPrecioFinal() == detalle2.getPrecioFinal())) {
						detalle1.setCantidad(detalle1.getCantidad()+detalle2.getCantidad());
						this.detallesTransaccion.set(j, null);
					}
				}
			}
		}
		while (this.detallesTransaccion.removeElement(null));*/

		if (logger.isDebugEnabled()) {
			logger.debug("acumularDetalles() - end");
		}
	}
	private void acumularDetallesVenta() {
		if (logger.isDebugEnabled()) {
			logger.debug("acumularDetallesVenta() - start");
		}
		
		for (int i=this.ventaOriginal.getDetallesTransaccion().size()-1;i>=0;i--) {
			if (this.ventaOriginal.getDetallesTransaccion().elementAt(i) != null) {
				for (int j=i-1; j>=0; j--) {
					DetalleTransaccion detalle1 = (DetalleTransaccion)this.ventaOriginal.getDetallesTransaccion().elementAt(i);
					DetalleTransaccion detalle2 = (DetalleTransaccion)this.ventaOriginal.getDetallesTransaccion().elementAt(j);
					if ((detalle2!=null)
					  &&(detalle1.getProducto().getCodProducto().equalsIgnoreCase(detalle2.getProducto().getCodProducto())) 
					  &&(detalle1.getCondicionVenta().equalsIgnoreCase(detalle2.getCondicionVenta()))
					  &&(detalle1.getPrecioFinal() == detalle2.getPrecioFinal()) 
					  && detalle1.contieneMismasCondiciones(detalle2)) {
						detalle1.setCantidad(detalle1.getCantidad()+detalle2.getCantidad());
						this.ventaOriginal.getDetallesTransaccion().set(j, null);
					}
				}
			}
		}
		while (this.ventaOriginal.getDetallesTransaccion().removeElement(null));

		if (logger.isDebugEnabled()) {
			logger.debug("acumularDetallesVenta() - end");
		}
	}
	
/*	private void acumularDetallesDevolucion() {
		if (logger.isDebugEnabled()) {
			logger.debug("acumularDetallesVenta() - start");
		}

		for (int i=this.ventaOriginal.getDetallesTransaccion().size()-1;i>=0;i--) {
			if (this.ventaOriginal.getDetallesTransaccion().elementAt(i) != null) {
				for (int j=i-1; j>=0; j--) {
					DetalleTransaccion detalle1 = (DetalleTransaccion)this.ventaOriginal.getDetallesTransaccion().elementAt(i);
					DetalleTransaccion detalle2 = (DetalleTransaccion)this.ventaOriginal.getDetallesTransaccion().elementAt(j);
					if ((detalle2!=null)
					  &&(detalle1.getProducto().getCodProducto().equalsIgnoreCase(detalle2.getProducto().getCodProducto())) 
					  &&(detalle1.getCondicionVenta().equalsIgnoreCase(detalle2.getCondicionVenta()))
					  &&(detalle1.getPrecioFinal() == detalle2.getPrecioFinal())) {
						detalle1.setCantidad(detalle1.getCantidad()+detalle2.getCantidad());
						this.ventaOriginal.getDetallesTransaccion().set(j, null);
					}
				}
			}
		}
		while (this.ventaOriginal.getDetallesTransaccion().removeElement(null));

		if (logger.isDebugEnabled()) {
			logger.debug("acumularDetallesVenta() - end");
		}
	}	
	*/
	
	/* (non-Javadoc)
	 * @see com.becoblohm.cr.manejarventa.Transaccion#actualizarPreciosDetalle(Producto)
	 */
	protected void actualizarPreciosDetalle(Producto p) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarPreciosDetalle(Producto) - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarPreciosDetalle(Producto) - end");
		}
	}
	
	/* (non-Javadoc)
	 * @see com.becoblohm.cr.manejarventa.Transaccion#anularProducto(long)
	 */
	public void anularProducto(int renglon) throws ProductoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("anularProducto(int) - start");
		}

		DetalleTransaccion linea = null;
		float cantidadOriginal;
		try {
			// Obtenemos la linea a cambiar la cantidad
			linea = (DetalleTransaccion)detallesTransaccion.elementAt(renglon);
			cantidadOriginal = linea.getCantidad();
		} catch (Exception ex) {
			logger.error("anularProducto(int)", ex);

			throw (new ProductoExcepcion("Error al eliminar producto, no existe renglon " + renglon + " en la transaccion", ex));
		}
		
		// Verificamos si existen productos suficientes en el renglon
		if (cantidadOriginal < 1)
			throw (new ProductoExcepcion("Error al eliminar producto. No existe(n) 1 producto(s) en el renglon"));

		linea.anularProducto();		
		
		DetalleTransaccion nuevoDet = new DetalleTransaccion(linea.getCodVendedor(), linea.getProducto(), cantidadOriginal, linea.getCondicionVenta(),linea.getCodSupervisor(),linea.getPrecioFinal(),linea.getMontoImpuesto(),linea.getTipoCaptura(),linea.getCodPromocion(), linea.getTipoEntrega());
		nuevoDet.setDctoEmpleado(((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).getDctoEmpleado());
		if (((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).getCantidad() <= 0) {
			detallesTransaccion.removeElementAt(renglon);
			Auditoria.registrarAuditoria("Anulado(s) " + cantidadOriginal + " producto(s) " + linea.getProducto().getCodProducto() + " de la devolucion",'T');
		} else {
			nuevoDet.setCantidad(1);
			((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).setCantidad(cantidadOriginal - 1);
			Auditoria.registrarAuditoria("Anulado(s) 1 producto(s) " + linea.getProducto().getCodProducto() + " de la devolucion",'T');
		}
		this.ventaOriginal.getDetallesTransaccion().addElement(nuevoDet);
		acumularDetallesVenta();
		actualizarMontoTransaccion();

		if (logger.isDebugEnabled()) {
			logger.debug("anularProducto(int) - end");
		}
	}

	/**
	 * Elimina una cantidad de productos de un renglon.
	 * @param renglon Renglon del producto a eliminar.
	 * @param cantidad Cantidad de productos a eliminar. Si existen menos productos que el especificado en la
	 * cantidad se elimina el renglon.
	 */
	/*
	* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
	* S�lo se parametriz� el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Object> anularProducto(int renglon, float cantidad) throws ProductoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("anularProducto(int, float) - start");
		}

		DetalleTransaccion linea = null;
		float cantidadOriginal;
		Vector<Object> result = new Vector<Object>();
		try {
			// Obtenemos la linea a cambiar la cantidad
			linea = (DetalleTransaccion)detallesTransaccion.elementAt(renglon);
			cantidadOriginal = linea.getCantidad();
		} catch (Exception ex) {
			logger.error("anularProducto(int, float)", ex);

			throw (new ProductoExcepcion("Error al eliminar producto, no existe renglon " + renglon + " en la transaccion", ex));
		}
		
		result.addElement(linea.getProducto().getDescripcionCorta());
		result.addElement(new Double(linea.getPrecioFinal() + linea.getMontoImpuesto()));

		// Verificamos si existen productos suficientes en el renglon
		if (cantidadOriginal < cantidad)
			throw (new ProductoExcepcion("Error al eliminar producto. No existe(n) " + cantidad + " producto(s) en el renglon"));

		// Chequeamos si la nueva cantidad es fraccionada, y si el producto permite cantidades fraccionadas
		if ((!linea.getProducto().isIndicaFraccion())&&((cantidad % 1)!=0))
			throw (new ProductoExcepcion("Error al cambiar cantidad de producto.\nEl producto " + linea.getProducto().getCodProducto() + " no admite cantidades fraccionadas (Decimales)"));

		linea.anularProducto(cantidad);
		
		DetalleTransaccion nuevoDet = new DetalleTransaccion(linea.getCodVendedor(), linea.getProducto(), cantidadOriginal, linea.getCondicionVenta(),linea.getCodSupervisor(),linea.getPrecioFinal(),linea.getMontoImpuesto(),linea.getTipoCaptura(),linea.getCodPromocion(), linea.getTipoEntrega());
		nuevoDet.setDctoEmpleado(((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).getDctoEmpleado());
		if (((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).getCantidad() <= 0) {
			detallesTransaccion.removeElementAt(renglon);
			Auditoria.registrarAuditoria("Anulado(s) " + cantidadOriginal + " producto(s) " + linea.getProducto().getCodProducto() + " de la devolucion",'T');
		} else {
			nuevoDet.setCantidad(cantidad);
			((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).setCantidad(new Float(cantidadOriginal - cantidad).floatValue());
			Auditoria.registrarAuditoria("Anulado(s) " + cantidad + " producto(s) " + linea.getProducto().getCodProducto() + " de la devolucion",'T');
		}
		this.ventaOriginal.getDetallesTransaccion().addElement(nuevoDet);
		acumularDetallesVenta();
		actualizarMontoTransaccion();

		if (logger.isDebugEnabled()) {
			logger.debug("anularProducto(int, float) - end");
		}
		return result;
	}

	/**
	 * Elimina una cantidad de productos de un renglon.
	 * @param renglon Renglon del producto a eliminar.
	 * @param cantidad Cantidad de productos a eliminar. Si existen menos productos que el especificado en la
	 * cantidad se elimina el renglon.
	 */
	/*
	* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
	* S�lo se parametriz� el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Object> anularProductoXCambio(int renglon, float cantidad) throws ProductoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("anularProducto(int, float) - start");
		}

		DetalleTransaccion linea = null;
		float cantidadOriginal;
		Vector<Object> result = new Vector<Object>();
		try {
			// Obtenemos la linea a cambiar la cantidad
			linea = (DetalleTransaccion)ventaOriginal.getDetallesTransaccion().elementAt(renglon);
			int posicion = 0;
			int i;
			for (i = 0; i < getDetallesTransaccion().size(); i++)
			{
				if (((DetalleTransaccion)getDetallesTransaccion().elementAt(i)).isDevuelto(linea.getProducto().getCodProducto()))
				{
					posicion = i;
					i = getDetallesTransaccion().size();
				}
			}
		
			linea = (DetalleTransaccion)getDetallesTransaccion().elementAt(posicion);
			getDetallesTransaccion().removeElementAt(posicion);
			cantidadOriginal = linea.getCantidadADevolver();
		} catch (Exception ex) {
			logger.error("anularProducto(int, float)", ex);

			throw (new ProductoExcepcion("Error al eliminar producto, no existe renglon " + renglon + " en la transaccion", ex));
		}
		
		result.addElement(linea.getProducto().getDescripcionCorta());
		result.addElement(new Double(linea.getPrecioFinal() + linea.getMontoImpuesto()));

		// Verificamos si existen productos suficientes en el renglon
		if (cantidadOriginal < cantidad)
			throw (new ProductoExcepcion("Error al eliminar producto. No existe(n) " + cantidad + " producto(s) en el renglon"));

		// Chequeamos si la nueva cantidad es fraccionada, y si el producto permite cantidades fraccionadas
		if ((!linea.getProducto().isIndicaFraccion())&&((cantidad % 1)!=0))
			throw (new ProductoExcepcion("Error al cambiar cantidad de producto.\nEl producto " + linea.getProducto().getCodProducto() + " no admite cantidades fraccionadas (Decimales)"));

		linea.anularProductoXCambio(cantidad);
		
		DetalleTransaccion nuevoDet = new DetalleTransaccion(linea.getCodVendedor(), linea.getProducto(), linea.getCantidad(), linea.getCondicionVenta(),linea.getCodSupervisor(),linea.getPrecioFinal(),linea.getMontoImpuesto(),linea.getTipoCaptura(),linea.getCodPromocion(), linea.getTipoEntrega(),linea.getCantidadADevolver());
		/*if (((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).getCantidad() <= 0) {
			detallesTransaccion.removeElementAt(renglon);
			Auditoria.registrarAuditoria("Anulado(s) " + cantidadOriginal + " producto(s) " + linea.getProducto().getCodProducto() + " de la devolucion",'T');
		} else {
			nuevoDet.setCantidad(cantidad);
			((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).setCantidad(new Float(cantidadOriginal - cantidad).floatValue());
			Auditoria.registrarAuditoria("Anulado(s) " + cantidad + " producto(s) " + linea.getProducto().getCodProducto() + " de la devolucion",'T');
		}*/
		if(linea.getCantidadADevolver()>0)
			this.getDetallesTransaccion().addElement(nuevoDet);
		//acumularDetallesDevolucion();
		actualizarMontoTransaccionXCambio();

		if (logger.isDebugEnabled()) {
			logger.debug("anularProducto(int, float) - end");
		}
		return result;
	}



	/**
	 * Actualiza el Monto de a transaccion, calcula el nuevo monto a partir del detalle de la factura.
	 */
	private void actualizarMontoTransaccion() {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarMontoTransaccion() - start");
		}

		double precioFinal = 0.0;
		double montoImpuesto = 0.0;
		
		for (int i=0; i<detallesTransaccion.size(); i++) {
			precioFinal += ((DetalleTransaccion) detallesTransaccion.elementAt(i)).getPrecioFinal() * ((DetalleTransaccion) detallesTransaccion.elementAt(i)).getCantidad();
			montoImpuesto += ((DetalleTransaccion) detallesTransaccion.elementAt(i)).getMontoImpuesto() * ((DetalleTransaccion) detallesTransaccion.elementAt(i)).getCantidad();
		}
		
		this.montoImpuesto = MathUtil.roundDouble(montoImpuesto);
		this.montoBase = MathUtil.roundDouble(precioFinal);

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarMontoTransaccion() - end");
		}
	}
	
	/**
	 * Actualiza el Monto de a transaccion, calcula el nuevo monto a partir del detalle de la factura.
	 */
	private void actualizarMontoTransaccionXCambio() {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarMontoTransaccion() - start");
		}
	
		double precioFinal = 0.0;
		double montoImpuesto = 0.0;
			
		for (int i=0; i<detallesTransaccion.size(); i++) {
			precioFinal += ((DetalleTransaccion) detallesTransaccion.elementAt(i)).getPrecioFinal() * ((DetalleTransaccion) detallesTransaccion.elementAt(i)).getCantidadADevolver();
			montoImpuesto += ((DetalleTransaccion) detallesTransaccion.elementAt(i)).getMontoImpuesto() * ((DetalleTransaccion) detallesTransaccion.elementAt(i)).getCantidadADevolver();
		}
			
		this.montoImpuesto = MathUtil.roundDouble(montoImpuesto);
		this.montoBase = MathUtil.roundDouble(precioFinal);
	
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarMontoTransaccion() - end");
		}
	}	
		
	
	
	/* (non-Javadoc)
	 * @see com.becoblohm.cr.manejarventa.Transaccion#finalizarTransaccion()
	 */
	public void finalizarTransaccion() throws ConexionExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, PrinterNotConnectedException {
		if (logger.isDebugEnabled()) {
			logger.debug("finalizarTransaccion() - start");
		}

		this.numCajaFinaliza = Sesion.getCaja().getNumero();
		this.numTransaccion = Sesion.getCaja().getNuevoNumTransaccion();
		this.lineasFacturacion = this.detallesTransaccion.size();
		this.horaFin = Sesion.getHoraSistema();
		this.estadoTransaccion = Sesion.IMPRIMIENDO;
		
		double disponible = 0; 	//Monto disponible para pago con condicional
		double condicional = 0; //Posible monto a devolver sobre condicional
		double montoDevolucion = CR.meVenta.getDevolucion().getMontoBase() + CR.meVenta.getDevolucion().getMontoImpuesto();
		double efectivo = montoDevolucion; 	//Monto a devolver en efectivo
		double vuelto = CR.meVenta.getVenta()!= null ? montoDevolucion - (CR.meVenta.getVenta().getMontoBase() + CR.meVenta.getVenta().getMontoImpuesto()):montoDevolucion;  	//Monto a dovolver al cliente
		Pago p = null;
		if (vuelto > 0){
			Pago pagoVenta = null;	
			for (int i = 0; i < CR.meVenta.getDevolucion().getVentaOriginal().getPagos().size(); i++)
			{
				pagoVenta = (Pago)CR.meVenta.getDevolucion().getVentaOriginal().getPagos().elementAt(i);
				if (((FormaDePago)(pagoVenta.getFormaPago())).getCodigo().equals(Sesion.FORMA_PAGO_CONDICIONAL))
					disponible = pagoVenta.getMonto(); 
			}
			if (disponible > 0){ //Hab�a alg�n pago con condicional en la venta original
				//Verificar saldo condicional despues de alguna devoluci�n anterior
				for (int i = 0; i < CR.meVenta.getDevolucion().getDevolucionesRealizadas().size(); i++)
				{
					//double montoDevuelto = ((Devolucion)CR.meVenta.getDevolucion().getDevolucionesRealizadas().elementAt(i)).getMontoBase() + ((Devolucion)CR.meVenta.getDevolucion().getDevolucionesRealizadas().elementAt(i)).getMontoImpuesto();
					for (int j = 0; j < ((Devolucion)CR.meVenta.getDevolucion().getDevolucionesRealizadas().elementAt(i)).getPagos().size(); j++)
					pagoVenta = (Pago)((Devolucion)CR.meVenta.getDevolucion().getDevolucionesRealizadas().elementAt(i)).getPagos().elementAt(j);
					if (((FormaDePago)(pagoVenta.getFormaPago())).getCodigo().equals(Sesion.FORMA_PAGO_CONDICIONAL))
					//disponible de condicional le resto lo que se ha consumido en devoluciones
						disponible = disponible - pagoVenta.getMonto(); 
					//disponible = disponible - montoDevuelto;	
				}
				// Compara el monto disponible en condicional con el monto de la devoluci�n
				
				if (vuelto <= disponible)
				{
					//Cargo a Condicional
					condicional = vuelto;
					efectivo = montoDevolucion - vuelto;
				} else {
					//Cargo a Condicional
					condicional = disponible;
					//Cargo a efectivo
					efectivo = montoDevolucion - disponible;
				} 								
			}
			vuelto -= condicional;
		}		
		// Actualizo pagos de la devoluci�n
		// Elimino los pagos asociados a la devoluci�n
		CR.meVenta.getDevolucion().getPagos().removeAllElements();
		if (efectivo > 0) 
		{
			//Agrego el pago en efectivo	
			p = new Pago(Sesion.FORMA_PAGO_EFECTIVO,efectivo, null, null, null, null, 0, null);
			CR.meVenta.getDevolucion().getPagos().addElement(p);

		}
		if (condicional > 0) //Agrego pago con condicional, si hay
		{
			p = new Pago(Sesion.FORMA_PAGO_CONDICIONAL,condicional, null, null, null, null, 0, null);
			CR.meVenta.getDevolucion().getPagos().addElement(p);
		}
		
		// Registramos la devolucion
		BaseDeDatosVenta.registrarTransaccion(this);
		
		// Mandamos a imprimir la factura pero antes registramos la auditor�a de esta funci�n
		Auditoria.registrarAuditoria("Emitiendo Factura de Devolucion Nro." + this.numTransaccion + " de Caja " + this.numCajaFinaliza,'T');
		/*if (Sesion.impresoraFiscal) {
			CR.meVenta.setTransaccionPorImprimir(this);
			//MaquinaDeEstadoVenta.setDocumentoNoFiscalPorImprimir(true);
			//CRFiscalPrinterOperations.setImpresionFiscalEnCurso(true);
		}*/
		
		// Si es devolucion por Cambio no mostramos el Vuelto
		ManejadorReportesFactory.getInstance().imprimirFacturaDevolucion(this, CR.meVenta.getVenta() == null, condicional, efectivo);

		if (vuelto > 0) {
			boolean reintentar = false;
			
			do {
				// Llamada al proceso de venta de bono regalo por el saldo a favor del cliente
				try {
					CR.meServ.realizarVentaBR("devoluci�n", vuelto, CR.meVenta.getDevolucion().getCliente(), false);
					reintentar = false;
				} catch (ExcepcionCr e) {
					int respuesta = MensajesVentanas.preguntarSiNo("Problema recargando tarjeta de bono regalo\n�Desea intentar nuevamente la carga por " + vuelto);
					if (respuesta == 0) {
						reintentar = true;
					}
					logger.error(e.getMessage());
				}
			} while (reintentar);

		}
		if (!Sesion.impresoraFiscal) {
			commitTransaccion();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("finalizarTransaccion() - end");
		}
	}
	
	/**
	 * Anula la transaccion Actual.
	 * @throws BaseDeDatosExcepcion - Si ocurre un error al registrar la anulacion de venta en la BD.
	 * @throws ExcepcionCr - Si ocurre  un error al ingresar el registro de Auditoria.
	 */
	public void anularDevolucionActiva() throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("anularDevolucionActiva() - start");
		}

		// Registramos la auditor�a de esta funci�n
		Sesion.setUbicacion("DEVOLUCION","anularDevolucionActiva");
		Auditoria.registrarAuditoria("Anulada Transaccion de devolucion con RegInicial " + this.getNumRegCajaInicia() + " de Caja " + this.getNumCajaInicia(),'T');

		if (logger.isDebugEnabled()) {
			logger.debug("anularDevolucionActiva() - end");
		}
	}
	
	/**
	 * Asigna el cliente  indicado a la devoluci�n y lo registra en la BD con un tipo especial (No afiliado)
	 * @param clienteTemp Cliente No afiliado a registrar y a asociar a la devoluci�n
	 */
	public void asignarCliente(Cliente clienteTemp) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, AfiliadoUsrExcepcion, ConexionExcepcion  {
		if (logger.isDebugEnabled()) {
			logger.debug("asignarCliente(Cliente) - start");
		}

		//Registramos el cliente temporal en la BD
		BaseDeDatosVenta.registrarClienteTemporal(clienteTemp);
		
		//Asignamos el cliente a la venta
		this.setCliente(clienteTemp);
		this.clienteOriginalDeVenta = false;
		
		//Registramos la auditor�a de esta funci�n
		Auditoria.registrarAuditoria("Asignando y registrando Cliente" +  cliente.getCodCliente()+ " No afiliado a devoluci�n" ,'T');	

		if (logger.isDebugEnabled()) {
			logger.debug("asignarCliente(Cliente) - end");
		}
	}
	
	/**
	 * Asigna el cliente afiliado indicado a la devoluci�n. 
	 * Si el cliente no es un afiliado se lanza una excepci�n indicandola eventualidad. 
	 * @param codigo
	 */
	public void asignarCliente(String codigo, String autorizante) throws XmlExcepcion, FuncionExcepcion, AfiliadoUsrExcepcion, BaseDeDatosExcepcion, LineaExcepcion, AutorizacionExcepcion, ClienteExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("asignarCliente(String, String) - start");
		}

		Cliente cliente = null;

		//Verificamos si existe el afiliado en la base de datos
		try {
			cliente = MediadorBD.obtenerCliente(codigo);
		}catch (BaseDeDatosExcepcion e){
			logger.error("asignarCliente(String, String)", e);

			throw (new BaseDeDatosExcepcion(e.getMensaje(), e));
		} catch (AfiliadoUsrExcepcion ex){
			logger.error("asignarCliente(String, String)", ex);

			throw(ex);
		}
		
		// Verificamos que el cliente no sea el usuario actual
		if ((cliente.getNumFicha()!=null) && Sesion.getUsuarioActivo().getNumFicha().equalsIgnoreCase(cliente.getNumFicha()))
			throw new AfiliadoUsrExcepcion("El cliente no puede ser el cajero activo");
		
		// Verificamos que el cliente no sea el usuario que autoriza la funcion (Si aplica)
		if ((autorizante!=null) && (cliente!=null) && autorizante.equalsIgnoreCase(cliente.getNumFicha()))
			throw (new AutorizacionExcepcion ("La funci�n no pudo ser autorizada.\nEl autorizante debe ser distinto al cliente asignado."));

		//Registramos la auditor�a de esta funci�n
		Sesion.setUbicacion("DEVOLUCION","asignarClienteDevolucion");
		Auditoria.registrarAuditoria("Identificando Cliente afiliado " +  cliente.getCodCliente() + " para devoluci�n",'T');
		this.setCliente(cliente);
		this.clienteOriginalDeVenta = false;

		if (logger.isDebugEnabled()) {
			logger.debug("asignarCliente(String, String) - end");
		}
	}
	
	public double consultarMontoTrans(boolean mostrarSubTotal) {
		if (logger.isDebugEnabled()) {
			logger.debug("consultarMontoTrans(boolean) - start");
		}

		if (mostrarSubTotal) {
			DecimalFormat df = new DecimalFormat("#,##0.00");
			try { CR.crVisor.enviarString("NOTA DE CREDITO", 0, df.format(this.consultarMontoTrans()), 2); }
			catch (Exception e) {
				logger.error("consultarMontoTrans(boolean)", e);
}
		}
		double returndouble = this.consultarMontoTrans();
		if (logger.isDebugEnabled()) {
			logger.debug("consultarMontoTrans(boolean) - end");
		}
		return returndouble;
	}
	
	/**
	 * M�todo getVentaOriginal
	 * 
	 * @return Venta
	 */
	public Venta getVentaOriginal() {
		if (logger.isDebugEnabled()) {
			logger.debug("getVentaOriginal() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getVentaOriginal() - end");
		}
		return ventaOriginal;
	}

	/**
	 * M�todo getDevolucionesRealizadas
	 * 
	 * @return Vector
	 */
	public Vector<Devolucion> getDevolucionesRealizadas() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDevolucionesRealizadas() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDevolucionesRealizadas() - end");
		}
		return devolucionesRealizadas;
	}

	/**
	 * M�todo getPagos
	 * 
	 * @return Vector
	 */
	/*
	* En esta funci�n se realizaron modificaciones referentes a la migraci�n a java 1.6 por jperez
	* S�lo se parametriz� el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Pago> getPagos() {
		if (logger.isDebugEnabled()) {
			logger.debug("getPagos() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getPagos() - end");
		}
		return pagos;
	}

	/**
	 * M�todo isClienteOriginalDeVenta
	 * 
	 * @return
	 * boolean
	 */
	public boolean isClienteOriginalDeVenta() {
		if (logger.isDebugEnabled()) {
			logger.debug("isClienteOriginalDeVenta() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isClienteOriginalDeVenta() - end");
		}
		return clienteOriginalDeVenta;
	}

	/* (sin Javadoc)
	 * @see com.becoblohm.cr.manejarventa.Transaccion#commitTransaccion()
	 * 
	 * @since 20-abr-2005
	 */
	public void commitTransaccion() {
		if (logger.isDebugEnabled()) {
			logger.debug("commitTransaccion() - start");
		}

		try {
			// Actualizamos el estado de la transaccion
			if (Sesion.impresoraFiscal) {
				Sesion.getCaja().commitNumTransaccion();
			}
			BaseDeDatosVenta.actualizarEdoTransaccion(this.codTienda, this.fechaTrans, this.numCajaInicia, this.numRegCajaInicia, this.numComprobanteFiscal, Sesion.FINALIZADA, true);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("commitTransaccion()", e);
		} catch (ConexionExcepcion e) {
			logger.error("commitTransaccion()", e);
		}
		// Sincronizamos la transacci�n
		new HiloSyncTransacciones().iniciar();		
		Auditoria.registrarAuditoria(getAutorizanteAuditoria(), getNumTransaccion(), 
				"TIE=" + getVentaOriginal().getCodTienda() + 
				" CAJA=" + getVentaOriginal().getNumCajaFinaliza() + 
				" TR="+ getVentaOriginal().getNumTransaccion() +
				" F=" + getVentaOriginal().getFechaTrans(), "DEVOLUCION", "finalizarDevolucion");

		if (logger.isDebugEnabled()) {
			logger.debug("commitTransaccion() - end");
		}
	}
	/* (sin Javadoc)
	 * @see com.becoblohm.cr.manejarventa.Transaccion#rollbackTransaccion()
	 * 
	 * @since 20-abr-2005
	 */
	public void rollbackTransaccion() {
		if (logger.isDebugEnabled()) {
			logger.debug("rollbackTransaccion() - start");
		}

		try {
			// Actualizamos el estado de la transaccion
			Sesion.getCaja().rollbackNumTransaccion();
			BaseDeDatosVenta.rollbackAnulacionDevolucion(codTienda, fechaTrans, 
					numCajaFinaliza, numTransaccion);
			BaseDeDatosVenta.actualizarEdoTransaccion(this.codTienda, this.fechaTrans, 
					this.numCajaInicia, this.numRegCajaInicia, 0, 
					Sesion.ABORTADA, true);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("rollbackTransaccion()", e);
		} catch (ConexionExcepcion e) {
			logger.error("rollbackTransaccion()", e);
		}		

		if (logger.isDebugEnabled()) {
			logger.debug("rollbackTransaccion() - end");
		}
	}
	
	public void colocarEnEspera (Devolucion objetoDevolucion, Venta nuevaVenta) throws BaseDeDatosExcepcion, SQLException, ConexionExcepcion{
		
		BaseDeDatosVenta.devolucionEnEspera(objetoDevolucion, nuevaVenta);
	}
	

}
