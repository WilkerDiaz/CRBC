/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.extensiones
 * Programa   : ManejoPagos.java
 * Creado por : irojas
 * Creado en  : 20/06/2007 - 03:35:35 PM
 *
 * (c) CENTROBECO, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 
 * Fecha       : 
 * Analista    : 
 * Descripción : 
 * =============================================================================
 */
package com.becoblohm.cr.extensiones;


import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import ve.com.megasoft.universal.error.VposUniversalException;


import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.BonoRegaloException;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.MontoPagoExcepcion;
import com.becoblohm.cr.excepciones.PagoExcepcion;
import com.becoblohm.cr.extensiones.impl.manejarpago.Banco;
import com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.extensiones.impl.manejarpago.PantallaDatosAdicionales;
import com.becoblohm.cr.manejarbr.OpcionBR;
import com.becoblohm.cr.manejarbr.VentaBR;
import com.becoblohm.cr.manejarservicio.Abono;
import com.becoblohm.cr.manejarservicio.ListaRegalos;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.manejarventa.Venta;


/**
 *	Esta clase refiere a los objetos que representan ManejoPagos. 
*/
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
* Fecha: agosto 2011
*/
public interface ManejoPagos extends CRExtension {
	
	Vector<Object> realizarPago(double montoMinimo, Cliente cliente, int numTrans) throws PagoExcepcion, MontoPagoExcepcion;

	Vector<Object> realizarPago(double montoMinimo, Cliente cliente, int numTrans, boolean abonoApartado) throws PagoExcepcion, MontoPagoExcepcion;
	
	/* Retorna en la posicion 0 los pagos y en la posicion 1 el vuelto */
	Vector<Object> realizarPago(double montoMinimo, Vector<Pago> pagosAnteriores, Cliente cliente, int numTrans) throws PagoExcepcion, MontoPagoExcepcion;

	Vector<Object> realizarPago(double montoMinimo, Vector<Pago> pagosAnteriores, Cliente cliente, int numTrans, boolean abonoApartado) throws PagoExcepcion, MontoPagoExcepcion;
	
	/* Retorna en la posicion 0 los pagos y en la posicion 1 el vuelto */
	Vector<Object> realizarNuevoPago(double montoMaximo, Vector<Pago> pagosAnteriores, Cliente cliente) throws PagoExcepcion, MontoPagoExcepcion;

	/**
	 * Método realizarPagoAbonos
	 * 
	 * @param montoAbonos
	 * @return Pago
	 * @throws PagoExcepcion
	 */
	Pago realizarPagoAbonos(double montoAbonos) throws PagoExcepcion;

	/**
	 * Método realizarPagoAbonos
	 * 
	 * @param montoAbonos
	 * @return Pago
	 * @throws PagoExcepcion
	 */
	Pago realizarPagoDevolucion(double montoAbonos) throws PagoExcepcion;
	
	/**
	 * Método realizarReversosParaDevoluciones
	 * 		Realiza el manejo de los pagos referentes a la forma de reverso de las devoluciones.
	 * Unicamente se podrán seleccionar entre las formas depago no bancarias. 
	 * @param mtoDevolucion Monto de la devolucion.
	 * @param pagosAnteriores Pagos realizados anteriormente en la devolucion.
	 * @return int 0 si no se agregaron pagos (Es un subtotal), 1 si se agregaron pagos y se presionó Escape, 2 si se termino el ingreso de pagos
	 * @throws PagoExcepcion - Si ocurre un error en el manejo de los pagos.
	 */
	int realizarReversosParaDevoluciones(double mtoDevolucion, Vector<Pago> pagosAnteriores, Cliente cliente) throws PagoExcepcion;

	/* Retorna en la posicion 0 los pagos y en la posicion 1 el vuelto */
	Vector<Object> eliminarPago(double montoTrans, Vector<Pago> pagosAnteriores, Cliente cliente);

	/* Retorna en la posicion 0 los pagos y en la posicion 1 el vuelto */
	Pago realizarPagoRetencion(double montoImpuesto, Vector<Pago> pagosVenta) throws PagoExcepcion;

	/* Retorna en la posicion 0 los pagos y en la posicion 1 el vuelto */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	void recalcularPagoRetencion(double montoImpuesto, Vector<Pago> pagosVenta);
	
	/* Retorna en la posicion 0 los pagos y en la posicion 1 el vuelto */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	void recalcularPagoDevolucion(double montoAnterior, double montoNuevo, Vector<Pago> pagosVenta);
	
	/**
	 * Valida que el cliente tenga saldo suficiente para realizar la operacion
	 *
	 * @param cliente
	 * @param mto
	 * @return boolean
	 * @throws PagoExcepcion
	 */
	boolean validarSaldoCliente(Cliente cliente, double mto)
			throws PagoExcepcion;
	
	/**
	 * Disminuye el monto dado del saldo del cliente
	 *
	 * @param cliente
	 * @param mto
	 * @throws PagoExcepcion
	 *
	 */
	void disminuirSaldoCliente(Cliente cliente, double mto)
			throws PagoExcepcion;

	/**
	 * @param codFPago
	 * @return
	 */
	FormaDePago cargarFormaDePago(String codFPago);

	/**
	 * @param
	 * @return Vector
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	Vector<FormaDePago> cargarTodasFormasDePago();
	
	/**
	 * @return
	 */
	Vector<Banco> obtenerBancos();

	/**
	 * @param cliente
	 * @param mto
	 */
	void incrementarSaldoCliente(Cliente cliente, double mto) throws PagoExcepcion;
	
	/**
	 * Registra los pagos asociados en una venta.
	 *
	 * @param loteSentenciasCR
	 *            Statement donde se agregara sentencia sql en batch
	 * @param venta
	 *            contenedor de los pagos
	 *
	 * @return double monto recaudado en la venta
	 * @throws SQLException
	 */
	double registrarPagos(Statement loteSentenciasCR, Venta venta)
			throws SQLException;
		
	/**
	 * Registra los pagos asociados a un abono
	 *
	 * @param loteSentenciasCR
	 *            Statement donde se agregara sentencia sql en batch
	 * @param ab
	 *            contenedor de los pagos
	 * @param numServ
	 *            numero del servicio asociado
	 *
	 * @throws SQLException
	 */
	void registrarPagos(Statement loteSentenciasCR, Abono ab, int numServ)
			throws SQLException;
			
	/**
	 * Registra los pagos asociados a un abono.
	 *
	 * @param loteSentenciasCR
	 * @param ab
	 *            contenedor de los pagos
	 * @param numServ
	 *            numero del servicio asociado
	 * @param pagoActual
	 *            pago Actual
	 * @param correlativoItem
	 *            correlativo del pago.
	 * @throws SQLException
	 */
	void registrarPago(Statement loteSentenciasCR, Abono ab, int numServ,
			Pago pagoActual, int correlativoItem) throws SQLException;

	/**
	 * Registra los pagos asociados a un abonoa a lista de regalos.
	 *
	 * @param loteSentenciasCR
	 * @param lista
	 *            Lista de regalos
	 * @param numOperacion
	 *            numero del pago asociado
	 * @param vuelto
	 *            monto del vuelto del pago
	 * @throws SQLException
	 */
	void registrarPagos(Statement loteSentenciasCR, ListaRegalos lista,
			int numOperacion, double vuelto) throws SQLException;

	/**
	 * Registra los pagos asociados a un abono a lista de regalos.
	 *
	 * @param loteSentenciasCR
	 * @param lista
	 *            Lista de regalos
	 * @param numOperacion
	 *            numero del pago asociado
	 * @param pago
	 *            pago Actual
	 * @param vuelto
	 *            monto del vuelto del pago
	 * @param correlativoItem
	 *            numero correlativo de la operación
	 * @throws SQLException
	 */			
	void registrarPago(Statement loteSentencias, ListaRegalos lista,
			int numOperacion, Pago pago, int correlativoItem, double vuelto)
			throws SQLException;
	
	/**
	 * Registra los pagos asociados a un abono a lista de regalos Remota.
	 *
	 * @param loteSentenciasCR
	 * @param lista
	 *            Lista de regalos
	 * @param solicitud
	 *            elemento XML que tiene el contenido de la lista de ragalo
	 * @param vuelto
	 *            monto del vuelto del pago
	 * @throws SQLException
	 */		
//	void registrarPagosLRRemota(ListaRegalos lista, IXMLElement solicitud, double vuelto) throws SQLException;
	
	/**
	 * Efectua la sincronizacion del servidor a la caja de los datos maestros de
	 * pagos.
	 *
	 * @throws BaseDeDatosExcepcion
	 */
	void sincronizarDatosMaestroPagos() throws BaseDeDatosExcepcion;	
	
	/**
	 * Efectua la sincronizacion de los datos extra asociados a los pagos (por
	 * ej. Punto Agil)
	 *
	 * @throws BaseDeDatosExcepcion
	 */
	void sincronizarDatosExtraPagos() throws BaseDeDatosExcepcion;
	
	/**
	 * Efectua el cierre el sistema de pagos que tenga implementado la caja.
	 */
	void efectuarCierre();	
	
    /**
     * Instancia la pantalla de datos adicionales de los pagos.
     *
     * @param formaPago
     * @param mto
     * @return PantallaDatosAdicionales
     * @throws PagoExcepcion
     */
    PantallaDatosAdicionales instancePantallaDatosAdicionales(
            FormaDePago formaPago, double mto) throws PagoExcepcion;
    
	FormaDePago obtenerFormaDePagoCuponDescuento() throws PagoExcepcion;
	
	public Pago obtenerPagoOperacion(FormaDePago formaDepago, double monto, String cedula) throws PagoExcepcion, VposUniversalException;
	
	void configurarOpcionesCards(Vector<OpcionBR> opcionesActivas) throws ExcepcionCr;
	
	public void consultaSaldoCards() throws BonoRegaloException;
	
	public Vector<Object> cargaRecargaSaldoCards(double monto, String codCliente) throws BonoRegaloException;
	
	public int procesarAnulacionTarjeta(String codTarjeta, int numSeq) throws BonoRegaloException;
	
	/**
	 * Registra los pagos asociados en una venta.
	 *
	 * @param loteSentenciasCR
	 *            Statement donde se agregara sentencia sql en batch
	 * @param venta
	 *            contenedor de los pagos
	 *
	 * @return double monto recaudado en la venta
	 * @throws SQLException
	 */
	double registrarPagos(Statement loteSentenciasCR, VentaBR venta)
			throws SQLException;
}
