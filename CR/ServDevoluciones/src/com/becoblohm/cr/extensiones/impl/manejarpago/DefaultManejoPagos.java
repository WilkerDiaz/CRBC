/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblomh.cr.manejarpago
 * Programa   : ManejoPagos.java
 * Creado por : gmartinell
 * Creado en  : 30-abr-04 07:48
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 
 * Fecha       : 20-jun-07 03:45 PM
 * Analista    : irojas
 * Descripción : Viene de conversión de clase ManejoPagos original en extensión
 * 				 Proyecto Merchant
 * =============================================================================
 */
package com.becoblohm.cr.extensiones.impl.manejarpago;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.MontoPagoExcepcion;
import com.becoblohm.cr.excepciones.PagoExcepcion;
import com.becoblohm.cr.extensiones.ManejoPagos;
import com.becoblohm.cr.manejarservicio.Abono;
import com.becoblohm.cr.manejarservicio.ListaRegalos;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.manejarventa.Venta;

/** 
 * Descripción: 
 * 		Maneja las instancias de las formas de pago realizadas a las transacciones
 * Por ahora se maneja unicamente efectivo. Las otras formas de pagos serán implementadas
 * luego por EPA
 */
public class DefaultManejoPagos implements ManejoPagos {
	/**
	 * Logger for this class
	 */
//	private static final Logger logger = Logger.getLogger(ManejoPagos.class);

//	private PantallaPagos pantallaPagos = null;
	
	protected static BaseDeDatosPago instanceBaseDeDatosPago;
	
	/**
	 * Instancia el DAO para interactuar con la data de pagos.
	 * NOTA: from EPA
	 * @return BaseDeDatosPago
	 */
	public BaseDeDatosPago getInstanceBaseDeDatosPago() {
		if (instanceBaseDeDatosPago == null) {
			instanceBaseDeDatosPago = new BaseDeDatosPago();
		}
		return instanceBaseDeDatosPago;
	}
	
	public Vector cargarTodasFormasDePago() {
		return this.getInstanceBaseDeDatosPago().cargarTodasFormasDePago();
	}

	public FormaDePago cargarFormaDePago(String codFPago) {
		return this.getInstanceBaseDeDatosPago().cargarFormaDePago(codFPago);
	}


	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#obtenerBancos()
	 */
	public Vector obtenerBancos() {
		return this.getInstanceBaseDeDatosPago().obtenerBancos();
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#realizarPago(double, com.becoblohm.cr.manejarventa.Cliente, int)
	 */
	public Vector realizarPago(double montoMinimo, Cliente cliente, int numTrans) throws PagoExcepcion, MontoPagoExcepcion {
		// TODO Apéndice de método generado automáticamente
		return null;
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#realizarPago(double, com.becoblohm.cr.manejarventa.Cliente, int, boolean)
	 */
	public Vector realizarPago(double montoMinimo, Cliente cliente, int numTrans, boolean abonoApartado) throws PagoExcepcion, MontoPagoExcepcion {
		// TODO Apéndice de método generado automáticamente
		return null;
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#realizarPago(double, java.util.Vector, com.becoblohm.cr.manejarventa.Cliente, int)
	 */
	public Vector realizarPago(double montoMinimo, Vector pagosAnteriores, Cliente cliente, int numTrans) throws PagoExcepcion, MontoPagoExcepcion {
		// TODO Apéndice de método generado automáticamente
		return null;
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#realizarPago(double, java.util.Vector, com.becoblohm.cr.manejarventa.Cliente, int, boolean)
	 */
	public Vector realizarPago(double montoMinimo, Vector pagosAnteriores, Cliente cliente, int numTrans, boolean abonoApartado) throws PagoExcepcion, MontoPagoExcepcion {
		// TODO Apéndice de método generado automáticamente
		return null;
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#realizarNuevoPago(double, java.util.Vector, com.becoblohm.cr.manejarventa.Cliente)
	 */
	public Vector realizarNuevoPago(double montoMaximo, Vector pagosAnteriores, Cliente cliente) throws PagoExcepcion, MontoPagoExcepcion {
		// TODO Apéndice de método generado automáticamente
		return null;
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#realizarPagoAbonos(double)
	 */
	public Pago realizarPagoAbonos(double montoAbonos) throws PagoExcepcion {
		// TODO Apéndice de método generado automáticamente
		return null;
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#realizarPagoDevolucion(double)
	 */
	public Pago realizarPagoDevolucion(double montoAbonos) throws PagoExcepcion {
		// TODO Apéndice de método generado automáticamente
		return null;
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#realizarReversosParaDevoluciones(double, java.util.Vector, com.becoblohm.cr.manejarventa.Cliente)
	 */
	public int realizarReversosParaDevoluciones(double mtoDevolucion, Vector pagosAnteriores, Cliente cliente) throws PagoExcepcion {
		// TODO Apéndice de método generado automáticamente
		return 0;
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#eliminarPago(double, java.util.Vector, com.becoblohm.cr.manejarventa.Cliente)
	 */
	public Vector eliminarPago(double montoTrans, Vector pagosAnteriores, Cliente cliente) {
		// TODO Apéndice de método generado automáticamente
		return null;
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#realizarPagoRetencion(double, java.util.Vector)
	 */
	public Pago realizarPagoRetencion(double montoImpuesto, Vector pagosVenta) throws PagoExcepcion {
		// TODO Apéndice de método generado automáticamente
		return null;
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#recalcularPagoRetencion(double, java.util.Vector)
	 */
	public void recalcularPagoRetencion(double montoImpuesto, Vector pagosVenta) {
		// TODO Apéndice de método generado automáticamente
		
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#recalcularPagoDevolucion(double, double, java.util.Vector)
	 */
	public void recalcularPagoDevolucion(double montoAnterior, double montoNuevo, Vector pagosVenta) {
		// TODO Apéndice de método generado automáticamente
		
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#validarSaldoCliente(com.becoblohm.cr.manejarventa.Cliente, double)
	 */
	public boolean validarSaldoCliente(Cliente cliente, double mto) throws PagoExcepcion {
		// TODO Apéndice de método generado automáticamente
		return false;
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#disminuirSaldoCliente(com.becoblohm.cr.manejarventa.Cliente, double)
	 */
	public void disminuirSaldoCliente(Cliente cliente, double mto) throws PagoExcepcion {
		// TODO Apéndice de método generado automáticamente
		
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#incrementarSaldoCliente(com.becoblohm.cr.manejarventa.Cliente, double)
	 */
	public void incrementarSaldoCliente(Cliente cliente, double mto) throws PagoExcepcion {
		// TODO Apéndice de método generado automáticamente
		
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#registrarPagos(java.sql.Statement, com.becoblohm.cr.manejarventa.Venta)
	 */
	public double registrarPagos(Statement loteSentenciasCR, Venta venta) throws SQLException {
		// TODO Apéndice de método generado automáticamente
		return 0;
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#registrarPagos(java.sql.Statement, com.becoblohm.cr.manejarservicio.Abono, int)
	 */
	public void registrarPagos(Statement loteSentenciasCR, Abono ab, int numServ) throws SQLException {
		// TODO Apéndice de método generado automáticamente
		
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#registrarPago(java.sql.Statement, com.becoblohm.cr.manejarservicio.Abono, int, com.becoblohm.cr.extensiones.impl.manejarpago.Pago, int)
	 */
	public void registrarPago(Statement loteSentenciasCR, Abono ab, int numServ, Pago pagoActual, int correlativoItem) throws SQLException {
		// TODO Apéndice de método generado automáticamente
		
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#registrarPagos(java.sql.Statement, com.becoblohm.cr.manejarservicio.ListaRegalos, int, double)
	 */
	public void registrarPagos(Statement loteSentenciasCR, ListaRegalos lista, int numOperacion, double vuelto) throws SQLException {
		// TODO Apéndice de método generado automáticamente
		
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#registrarPago(java.sql.Statement, com.becoblohm.cr.manejarservicio.ListaRegalos, int, com.becoblohm.cr.extensiones.impl.manejarpago.Pago, int, double)
	 */
	public void registrarPago(Statement loteSentencias, ListaRegalos lista, int numOperacion, Pago pago, int correlativoItem, double vuelto) throws SQLException {
		// TODO Apéndice de método generado automáticamente
		
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#sincronizarDatosMaestroPagos()
	 */
	public void sincronizarDatosMaestroPagos() throws BaseDeDatosExcepcion {
		// TODO Apéndice de método generado automáticamente
		
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#sincronizarDatosExtraPagos()
	 */
	public void sincronizarDatosExtraPagos() throws BaseDeDatosExcepcion {
		// TODO Apéndice de método generado automáticamente
		
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#efectuarCierre()
	 */
	public void efectuarCierre() {
		// TODO Apéndice de método generado automáticamente
		
	}
}