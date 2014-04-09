/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.manejarventa
 * Programa   : Anulacion.java
 * Creado por : gmartinelli
 * Creado en  : 31/03/2004 10:34:15 AM
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
package com.becoblohm.cr.manejarventa;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.beco.cr.actualizadorPrecios.promociones.DonacionRegistrada;
import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.AfiliadoUsrExcepcion;
import com.becoblohm.cr.excepciones.AnulacionExcepcion;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ClienteExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.ProductoExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.extensiones.ManejadorReportesFactory;
import com.becoblohm.cr.manejadorauditoria.Auditoria;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.mediadoresbd.BaseDeDatosVenta;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.sincronizador.HiloSyncTransacciones;
import com.becoblohm.cr.utiles.MathUtil;
import com.epa.crprinterdriver.PrinterNotConnectedException;



/** 
 * Descripción: 
 * 		Subclase de Transaccion. Maneja las anulaciones de venta. Se manejan accesos a base de datos para registrar
 * las mismas.
 */
public class Anulacion extends Transaccion {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Anulacion.class);
	
	private Venta ventaOriginal;
	public static Vector<DonacionRegistrada> donacionesRegistradas = new Vector<DonacionRegistrada>();
	
	/**
	 * Constructor de la clase Anulacion que llama al constructor de la superclase (Transaccion)
	 * y, chequea si la venta cumple con las condiciones de anulacion (misma caja, mismo cajero, mismo dia
	 * y no tiene devoluciones).
	 */
	public Anulacion(int tienda, String fechaTr, int cajaOriginal, int numTransaccion, String autorizante) throws AnulacionExcepcion, AfiliadoUsrExcepcion, BaseDeDatosExcepcion, ClienteExcepcion, ConexionExcepcion {
		
		// Creamos la instancia de la nueva devolucion
		super(Sesion.getTienda().getNumero(), Sesion.getFechaSistema(), Sesion.getCaja().getNumero(),
			  Sesion.getCaja().getNumRegistro(), Sesion.getHoraSistema(), Sesion.usuarioActivo.getNumFicha(),
			  Sesion.ANULACION, Sesion.PROCESO);
		setCodAutorizante(autorizante);
		// Cargamos los datos de la venta original
		ventaOriginal = new Venta(tienda, fechaTr, cajaOriginal, numTransaccion, true);
		if ((this.ventaOriginal.getCliente().getCodCliente() != null) &&
			(this.ventaOriginal.getCliente().getCodCliente().length() > 7))
			this.cliente = MediadorBD.obtenerCliente(this.ventaOriginal.getCliente().getCodCliente());
		else { //Asignamos al cajero como cliente para la anulación de la venta
			this.cliente = new Cliente (Sesion.getUsuarioActivo().getDatosPersonales().getCodAfiliado(),Sesion.getUsuarioActivo().getDatosPersonales().getNombre());
		}
		
		// Chequemos condicion de misma tienda
		if (this.ventaOriginal.getCodTienda()!=Sesion.getTienda().getNumero())
			throw new AnulacionExcepcion("La venta debe ser anulada en la Tienda " + this.ventaOriginal.getCodTienda());

		// Chequemos condicion de misma caja
		if (this.ventaOriginal.getNumCajaFinaliza()!=Sesion.getCaja().getNumero())
			throw new AnulacionExcepcion("La venta debe ser anulada en la Caja " + this.ventaOriginal.getNumCajaFinaliza());

		// Chequeamos condicion de mismo cajero
		if (!this.ventaOriginal.getCodCajero().equalsIgnoreCase(Sesion.usuarioActivo.getNumFicha()))
			throw new AnulacionExcepcion("La venta debe ser anulada por el Cajero " + this.ventaOriginal.getCodCajero());
			
		// Chequeamos condicion de mismo dia
		String fechaT = (new SimpleDateFormat("dd-MM-yyyy")).format(this.ventaOriginal.getFechaTrans());
		String fechaA = (new SimpleDateFormat("dd-MM-yyyy")).format(Sesion.getFechaSistema());
		if (!fechaT.equalsIgnoreCase(fechaA)) {
			throw new AnulacionExcepcion("La fecha de venta difiere de la actual. La venta fue realizada el " + fechaT);
		}
		
		// Chequeamos que el cliente no sea el usuario activo
		if ((this.cliente.getNumFicha()!=null) && Sesion.getUsuarioActivo().getNumFicha().equalsIgnoreCase(this.cliente.getNumFicha()))
			throw new AfiliadoUsrExcepcion("La Venta original posee como cliente al Usuario Activo.\nNo se pudo recuperar la venta para anulación");

		// Chequeamos que el cliente no sea el usuario autorizante (Si aplica)
		if ((this.cliente.getNumFicha()!=null) && (autorizante!=null) && autorizante.equalsIgnoreCase(this.cliente.getNumFicha()))
			throw new AfiliadoUsrExcepcion("La Venta original posee como cliente al Usuario Autorizante.\nNo se pudo recuperar la venta para anulación");

		// Observamos que no se haya anulado la venta original
		if (BaseDeDatosVenta.anuladaTransaccion(tienda, fechaTr, cajaOriginal, numTransaccion, Sesion.ANULACION, false))
			throw new AnulacionExcepcion("La Transaccion " + numTransaccion + " se encuentra anulada.");
		
		// Observamos que no existan devoluciones sobre la transaccion
		if (BaseDeDatosVenta.tieneDevoluciones(tienda, fechaTr, cajaOriginal, numTransaccion, Sesion.DEVOLUCION, false))
			throw new AnulacionExcepcion("La Transaccion " + numTransaccion + " posee devoluciones.");
		
		// Cargamos los detalles de la venta original en la anulacion
		cargarDetalles();
		
	}
	/**
	 * Constructor de la clase. Usado para recuperar una anulación desde la BD, para su uso en modo
	 * consulta.
	 * @param tda Número de tienda
	 * @param fechaT Fecha de la transacción
	 * @param caja Número de la caja
	 * @param numTransacción Número de la transacción
	 * @param local Indica si la transacción debe ser recuperada de manera local o remota.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' e 'Iterator'
	* Fecha: agosto 2011
	*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Anulacion(int tda, String fechaT, int caja, int numTransaccion, boolean local) throws BaseDeDatosExcepcion, ConexionExcepcion {
		super();
		Vector<Vector<?>> cmpAnulacion = BaseDeDatosVenta.obtenerAnulacion(tda, fechaT, caja, numTransaccion, local);
		Vector<Object> cabecera = (Vector)cmpAnulacion.elementAt(0);
		Vector<Vector<Object>> detalles = (Vector)cmpAnulacion.elementAt(1);
	
		
		// Armar cabecera de la anulacion
		codTienda = ((Integer)cabecera.elementAt(0)).intValue();
		fechaTrans = (Date)cabecera.elementAt(1);
		numCajaInicia = ((Integer)cabecera.elementAt(2)).intValue();
		numRegCajaInicia = ((Integer)cabecera.elementAt(3)).intValue();
		numCajaFinaliza = ((Integer)cabecera.elementAt(4)).intValue();
		this.numTransaccion = ((Integer)cabecera.elementAt(5)).intValue();
		tipoTransaccion = (((String)cabecera.elementAt(6)).toCharArray())[0];
		horaInicia = (Time)cabecera.elementAt(7);
		horaFin = (Time)cabecera.elementAt(8);
		if (((String)cabecera.elementAt(9)) == null) {
			super.cliente = new Cliente(); 
		} else {
			try {
				super.cliente = MediadorBD.obtenerCliente((String)cabecera.elementAt(9));
			} catch (ConexionExcepcion e) {
				logger.error("Anulacion(int, String, int, int, boolean)", e);
			} catch (BaseDeDatosExcepcion e) {
				logger.error("Anulacion(int, String, int, int, boolean)", e);
			} catch (AfiliadoUsrExcepcion e) {
				logger.error("Anulacion(int, String, int, int, boolean)", e);
			} catch (ClienteExcepcion e) {
				logger.error("Anulacion(int, String, int, int, boolean)", e);
			}
		}		
		codCajero = (String)cabecera.elementAt(10);
		montoBase = ((Double)cabecera.elementAt(11)).doubleValue();
		montoImpuesto = ((Double)cabecera.elementAt(12)).doubleValue();
		montoVuelto = ((Double)cabecera.elementAt(13)).doubleValue();
		montoRemanente = ((Double)cabecera.elementAt(14)).doubleValue();
		lineasFacturacion = ((Integer)cabecera.elementAt(15)).intValue();
		checksum = ((Integer)cabecera.elementAt(16)).intValue();
		estadoTransaccion = ((String)cabecera.elementAt(17)).toCharArray()[0];
		// Para recuperar la venta original
		int tiendaVta = ((Integer)cabecera.elementAt(18)).intValue();
		Date fechaVta = (Date)cabecera.elementAt(19);
		int cajaVta = ((Integer)cabecera.elementAt(20)).intValue();
		int numTrVta = ((Integer)cabecera.elementAt(21)).intValue();
		codAutorizante = (String)cabecera.elementAt(22);
		ventaOriginal = new Venta(tiendaVta, SavedTransaccion.getFechaSQL(fechaVta), cajaVta, numTrVta, true);
		
		
		// Armar detalles de la anulacion
		for (Iterator<Vector<Object>> i = detalles.iterator(); i.hasNext();) {
			Vector<Object> detalle = i.next();
			String codProd = (String)detalle.elementAt(0);
			String codVend = (String)detalle.elementAt(3);
			String tipoCap = (String)detalle.elementAt(8);
			float cant = ((Float)detalle.elementAt(2)).floatValue();
			try {
				this.detallesTransaccion.addElement(new DetalleTransaccion(codProd, codVend, tipoCap, cant, this.fechaTrans, this.horaInicia, true));
			} catch (ProductoExcepcion e1) {
				logger.error("Anulacion(int, String, int, int, boolean)", e1);

				throw (new BaseDeDatosExcepcion("Error recuperando devoluciones realizadas sobre transaccion"));
			} catch (BaseDeDatosExcepcion e1) {
				logger.error("Anulacion(int, String, int, int, boolean)", e1);

				throw (new BaseDeDatosExcepcion("Error de conexion con BaseDeDatos recuperando devoluciones realizadas sobre transaccion"));
			}
		
			// Agregamos los otros parametros
			String condVenta = (String)detalle.elementAt(1);
			String codSupervisor = ((String)detalle.elementAt(4));
			double precioFinal = ((Double)detalle.elementAt(5)).doubleValue();
			double impuesto = ((Double)detalle.elementAt(6)).doubleValue();
			int codPromo = ((Integer)detalle.elementAt(7)).intValue();
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setCondicionVenta(condVenta);
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setCodSupervisor(codSupervisor);
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setPrecioFinal(precioFinal);
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setMontoImpuesto(impuesto);
			((DetalleTransaccion)this.detallesTransaccion.elementAt(this.detallesTransaccion.size()-1)).setCodPromocion(codPromo);
			
		}
	}
	
	private void cargarDetalles() {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarDetalles() - start");
		}

		while(this.ventaOriginal.getDetallesTransaccion().size()>0) {
			copiarDetalle(0);
		}
		this.actualizarMontoTransaccion();

		if (logger.isDebugEnabled()) {
			logger.debug("cargarDetalles() - end");
		}
	}

	/**
	 * Ingresa una nueva linea de producto. Verifica que exista en la venta original y que no ha
	 * sido devuelto antes.
	 * @param renglonVenta Codigo del producto a ingresar.
	 * @throws ProductoExcepcion Si no se encuentra el producto.
	 */
	public void copiarDetalle(int renglonVenta) {
		if (logger.isDebugEnabled()) {
			logger.debug("copiarDetalle(int) - start");
		}

		DetalleTransaccion detalle = (DetalleTransaccion)this.ventaOriginal.getDetallesTransaccion().elementAt(renglonVenta);
		
		this.detallesTransaccion.addElement(new DetalleTransaccion(detalle.getCodVendedor(), detalle.getProducto(),
			detalle.getCantidad(), detalle.getCondicionVenta(), detalle.getCodSupervisor(), detalle.getPrecioFinal(),
			detalle.getMontoImpuesto(), detalle.getTipoCaptura(), detalle.getCodPromocion(), detalle.getTipoEntrega()));
		((DetalleTransaccion)this.detallesTransaccion.lastElement()).setDctoEmpleado(detalle.getDctoEmpleado());
		this.ventaOriginal.getDetallesTransaccion().remove(renglonVenta);

		if (logger.isDebugEnabled()) {
			logger.debug("copiarDetalle(int) - end");
		}
	}
	
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

		if (logger.isDebugEnabled()) {
			logger.debug("anularProducto(int) - end");
		}
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
	
	/* (non-Javadoc)
	 * @see com.becoblohm.cr.manejarventa.Transaccion#finalizarTransaccion()
	 */
	public void finalizarTransaccion() throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion, PrinterNotConnectedException {
		if (logger.isDebugEnabled()) {
			logger.debug("finalizarTransaccion() - start");
		}

		this.numCajaFinaliza = Sesion.getCaja().getNumero();
		this.numTransaccion = Sesion.getCaja().getNuevoNumTransaccion();
		this.lineasFacturacion = this.detallesTransaccion.size();
		this.horaFin = Sesion.getHoraSistema();
		this.estadoTransaccion = Sesion.IMPRIMIENDO;
		
		// Registramos la anulacion
		BaseDeDatosVenta.registrarTransaccion(this);
		
		// Mandamos a imprimir la factura pero antes registramos la auditoría de esta función
		Auditoria.registrarAuditoria("Emitiendo Factura de Anulacion Nro." + this.numTransaccion + " de Caja " + this.numCajaFinaliza,'T');
		if (Sesion.impresoraFiscal) {
			CR.meVenta.setTransaccionPorImprimir(this);
		}
		
		try {
			ManejadorReportesFactory.getInstance().imprimirFacturaAnulacion(this);
			
		} catch (Exception e){
			
			if (Sesion.impresoraFiscal) {
				rollbackTransaccion();
			}
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
	public void anularAnulacionActiva() throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("anularAnulacionActiva() - start");
		}

		// Registramos la auditoría de esta función
		Sesion.setUbicacion("ANULACION","anularAnulacionActiva");
		Auditoria.registrarAuditoria("Anulada Transaccion de ANULACION con RegInicial " + this.getNumRegCajaInicia() + " de Caja " + this.getNumCajaInicia(),'T');

		if (logger.isDebugEnabled()) {
			logger.debug("anularAnulacionActiva() - end");
		}
	}
	/**
	 * Método getVentaOriginal
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
		// Sincronizamos la transacción
		new HiloSyncTransacciones().iniciar();		
		Auditoria.registrarAuditoria(getAutorizanteAuditoria(), getNumTransaccion(), 
				"TR=" + getVentaOriginal().getNumTransaccion() , "ANULACION", "finalizarAnulacion");

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
}
