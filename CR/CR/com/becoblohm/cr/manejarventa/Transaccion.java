/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.manejarventa
 * Programa   : Transaccion.java
 * Creado por : apeinado
 * Creado en  : 06/10/2003 03:12:50 PM
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 1.3.2
 * Fecha       : 22/07/2008 3:42pm
 * Analista    : jgraterol
 * Descripción : Agregado atributo para el descuento corporativo (modulo de promociones)
 * ============================================================================= 
 * Versión     : 1.3.1
 * Fecha       : 30/05/2008 3:42pm
 * Analista    : jgraterol
 * Descripción : Agregado el atributo actualizadorPrecios para  invocar a la extension
 * 				de promociones
 * ============================================================================= 
 * Versión     : 1.3
 * Fecha       : 30/05/2008 3:42pm
 * Analista    : jgraterol
 * Descripción : El metodo verificarPromociones fue cambiado a public para que
 * 				pueda ser invocado desde la extension del actualizador de precios
 * =============================================================================
 * Versión     : 1.2.1
 * Fecha       : 20/02/2004 11:32 am
 * Analista    : irojas
 * Descripción : - Agregado un nuevo metodo de anularProducto que reciba un float 
 * 				   para las anulaciones que quieren eliminar mas de un producto.
 *			  	 - Cambiado metodo finalizarTransaccion para que devuelve un vector 
 *			  	   con String de la Factura
 * =============================================================================
 * Versión     : 1.2.1
 * Fecha       : 20/02/2004 11:32 am
 * Analista    : irojas
 * Descripción : - Línea 50: Cambiado tipo de atributo cliente de "Afiliado" a tipo "Cliente"
 *				 - Línea 82: Cambiada construcción del objeto Afiliado en el atributo cliente 
 *					a la construcción de un objeto Cliente
 *				 - Agregados métodos get y set del atributo cliente
 * =============================================================================
 * Versión     : 1.2.1
 * Fecha       : 19/02/2004 13:31 pm
 * Analista    : gmartinelli
 * Descripción : Agregado constructor Transaccion() Linea 96
 * =============================================================================
 * Versión     : 1.2 (según CVS antes del cambio)
 * Fecha       : 09/02/2004 04:23 PM
 * Analista    : Ileana Rojas
 * Descripción : Cambios de códigos de producto de Long a String
 * =============================================================================
 */
package com.becoblohm.cr.manejarventa;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.ProductoExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.extensiones.ActualizadorPreciosFactory;
import com.becoblohm.cr.utiles.Control;
import com.becoblohm.cr.utiles.MathUtil;
import com.epa.crprinterdriver.PrinterNotConnectedException;

/** 
 * Descripción: 
 * 		Superclase de Transacciones. Maneja las operaciones con transacciones (Sean de Venta,
 * Devolucion o anulacion).
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
* Fecha: agosto 2011
*/
public abstract class Transaccion implements Serializable {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Transaccion.class);
	
	protected int codTienda;
	protected Date fechaTrans;
	protected int numCajaInicia;
	protected int numRegCajaInicia;
	protected  int numCajaFinaliza;
	protected int numTransaccion;
	protected Time horaInicia;
	protected Time horaFin;
	protected String codCajero;
	protected Cliente cliente;
	protected double montoBase;
	protected double montoImpuesto;
	protected double montoRemanente;
	protected double montoVuelto;
	protected int lineasFacturacion;
	protected int checksum;
	protected char tipoTransaccion;
	protected char estadoTransaccion;
	protected Vector<DetalleTransaccion> detallesTransaccion;
	protected long inicio = 0;
	protected long duracion = 0;
	protected int numComprobanteFiscal;
	protected String codAutorizante;
	
	//ACTUALIZACION BECO: 22/07/2008
	protected double descuentoCorporativoAplicado =0.0; 
	protected int codDescuentoCorporativo = 0;
	
	/**
	 * Constructor de la Clase Transaccion.
	 * @param tda Numero de la Tienda.
	 * @param fecha Fecha de la Transaccion.
	 * @param cajaInicia Numero de la caja donde se inicia la transaccion.
	 * @param regCajaInicia Numero de registro de la caja donde se inicia la transaccion.
	 * @param horaInicia Hora de Inicio.
	 * @param cajero Codigo del cajero.
	 * @param tipoTrans Tipo de transaccion (Venta, Anulacion, Devolucion, etc.).
	 * @param edoTrans Estado de la transaccion (EnProceso, Espera, Imprimiendo, Finalizada).
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Transaccion(int tda, Date fecha, int cajaInicia, int regCajaInicia, Time horaInicia, String cajero,
				char tipoTrans, char edoTrans) {
		this.inicio = Control.iniciarCronometro();
		this.codTienda = tda;
		this.fechaTrans = fecha;
		this.numCajaInicia = cajaInicia;
		this.numRegCajaInicia = regCajaInicia;
		this.numCajaFinaliza = 0;
		this.numTransaccion = 0;
		this.horaInicia = horaInicia;
		this.horaFin = null;
		this.codCajero = cajero;
		this.cliente = new Cliente(); // OJO ESTO HAY QUE CHEQUEARLO
		this.montoBase = 0.0;
		this.montoImpuesto = 0.0;
		this.montoVuelto = 0.0;
		this.montoRemanente = 0.0;
		this.lineasFacturacion = 0;
		this.checksum = 0; // HAY QUE VER QUE ES ESTE CAMPO
		this.tipoTransaccion = tipoTrans;
		this.estadoTransaccion = edoTrans;
		this.detallesTransaccion = new Vector<DetalleTransaccion>();
		descuentoCorporativoAplicado =0.0;
	}
	
	/**
	 * 
	 */
	public Transaccion() {
		this.inicio = Control.iniciarCronometro();
		this.detallesTransaccion = new Vector<DetalleTransaccion>();
		descuentoCorporativoAplicado =0.0;
	}
	
	/**
	 * Métodos abstractos de la clase Transacción
	 */

	/**
	 * Anula un producto de un renglon de la transaccion.
	 * @param codProd Codigo del producto a anular.
	 * @throws ProductoExcepcion Si el producto no existe en el Detalle de Transaccion.
	 */
	protected abstract void anularProducto(int renglon) throws ProductoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion;

	/**
	 * Actualiza los precios del detalle del transaccion.
	 * @param prod Producto agregado al detalle.
	 */
	protected abstract void actualizarPreciosDetalle(Producto prod) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion;
	
	/**
	 * Finaliza la transaccion, se imprime la factura y se registra en la base de datos.
	 * @throws BaseDeDatosExcepcion Si ocurre un error con la Base de Datos.
	 */
	protected abstract void finalizarTransaccion() throws ConexionExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, PrinterNotConnectedException;
	
	public abstract void commitTransaccion();
	
	public abstract void rollbackTransaccion();

	// Metodos de la clase Transaccion
	public double consultarMontoTrans() {
		if (logger.isDebugEnabled()) {
			logger.debug("consultarMontoTrans() - start");
		}

		double returndouble = MathUtil.roundDouble(montoBase + montoImpuesto);
		if (logger.isDebugEnabled()) {
			logger.debug("consultarMontoTrans() - end");
		}
		return returndouble;
	}
		
	/**
	 * Obtiene una linea de producto desde el detalle de transaccion.
	 * @param codProd Codigo del producto a buscar.
	 * @return DetalleTransaccion - Detalle de Transaccion que contiene el producto.
	 * @throws ProductoExcepcion - Si el producto no se encuentra en el detalle.
	 */
	protected DetalleTransaccion obtenerLineaProd(String codProd) throws ProductoExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerLineaProd(String) - start");
		}

		DetalleTransaccion detalleTrans = null;
		
		for (int i=0; i<detallesTransaccion.size(); i++) {
			Producto prod = ( detallesTransaccion.elementAt(i)).getProducto();
				if (prod.getCodProducto() == codProd) {
					detalleTrans =  detallesTransaccion.elementAt(i);
					 break;
				}
		}
		if (detalleTrans == null) {
			throw (new ProductoExcepcion("Producto " + codProd + " NO existe en la venta"));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerLineaProd(String) - end");
		}
		return detalleTrans;
	}
	
	/**
	 * Verifica si se aplican promociones a los productos que se encuentran en el detalle de transaccion.
	 * @param prod Producto a chequear promociones
	 * @return Vector - Vector con el codigo de promocion y precio final de producto si aplica promocion
	 */
	public Vector<Object> verificarPromociones(Producto prod) {
		return (new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().verificarPromociones(prod);
	}
	
	/**
	 * Obtiene el Checksum.
	 * @return int - CheckSum
	 */
	public int getChecksum() {
		if (logger.isDebugEnabled()) {
			logger.debug("getChecksum() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getChecksum() - end");
		}
		return checksum;
	}

	/**
	 * Obtiene el Codigo del Cajero.
	 * @return String - Codigo del cajero.
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
	 * Obtiene el Codigo de la Tienda.
	 * @return int - Codigo de la tienda.
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
	 * Obtiene el Vector de detalles de transaccion.
	 * @return Vector - Detalles de transaccion.
	 */
	public Vector<DetalleTransaccion> getDetallesTransaccion() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDetallesTransaccion() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getDetallesTransaccion() - end");
		}
		return detallesTransaccion;
	}

	/**
	 * Obtiene el Estado de la transaccion (EnProceso, EnEspera, Anulada, Finalizada).
	 * @return char - Estado de la transaccion.
	 */
	public char getEstadoTransaccion() {
		if (logger.isDebugEnabled()) {
			logger.debug("getEstadoTransaccion() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getEstadoTransaccion() - end");
		}
		return estadoTransaccion;
	}

	/**
	 * Obtiene la Fecha de la Transaccion.
	 * @return Date - Fecha de la transaccion.
	 */
	public Date getFechaTrans() {
		if (logger.isDebugEnabled()) {
			logger.debug("getFechaTrans() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getFechaTrans() - end");
		}
		return fechaTrans;
	}

	/**
	 * Obtiene la hora final de la transaccion.
	 * @return Time - Hora en que finalizó la transaccion.
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
	 * Obtiene la hora inicial de la transaccion.
	 * @return Time - Hora en que se inicio la transaccion.
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
	 * Obtiene el numero de lineas de facturacion.
	 * @return int - Numero de items del detalle.
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
	 * Obtiene el monto Base (Sin Impuesto).
	 * @return double - Monto Base.
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
	 * Obtiene el monto del impuesto.
	 * @return double - Monto del impuesto.
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
	 * Obtiene el numero de caja donde finalizo la transaccion.
	 * @return int - Numero de Caja.
	 */
	public int getNumCajaFinaliza() {
		if (logger.isDebugEnabled()) {
			logger.debug("getNumCajaFinaliza() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getNumCajaFinaliza() - end");
		}
		return numCajaFinaliza;
	}

	/**
	 * Obtiene el numero de caja donde se incio la transaccion.
	 * @return int - Numero de Caja.
	 */
	public int getNumCajaInicia() {
		if (logger.isDebugEnabled()) {
			logger.debug("getNumCajaInicia() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getNumCajaInicia() - end");
		}
		return numCajaInicia;
	}

	/**
	 * Obtiene el numero de registro de la caja incial.
	 * @return int - Numero de Registro.
	 */
	public int getNumRegCajaInicia() {
		if (logger.isDebugEnabled()) {
			logger.debug("getNumRegCajaInicia() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getNumRegCajaInicia() - end");
		}
		return numRegCajaInicia;
	}

	/**
	 * Obtiene el numero de transaccion.
	 * @return int - Numero de transaccion.
	 */
	public int getNumTransaccion() {
		if (logger.isDebugEnabled()) {
			logger.debug("getNumTransaccion() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getNumTransaccion() - end");
		}
		return numTransaccion;
	}

	/**
	 * Obtiene el tipo de transaccion (Venta, Devolucion, Anulacion).
	 * @return char - Tipo de transaccion
	 */
	public char getTipoTransaccion() {
		if (logger.isDebugEnabled()) {
			logger.debug("getTipoTransaccion() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getTipoTransaccion() - end");
		}
		return tipoTransaccion;
	}
	
	/**
	* Permite obtenerel cliente que se encuentra asignado ala venta (es del tipo Cliente)
	* @return el objeto Cliente que represneta el cliente de la venta
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
	 * Permite asignar un clientea la venta. Esto por medio del objeto Cliente
	 * @param cliente Representa el Cliente que se quiere asignar a la venta
	 */
	public void setCliente(Cliente cliente) {
		if (logger.isDebugEnabled()) {
			logger.debug("setCliente(Cliente) - start");
		}

		this.cliente = cliente;

		if (logger.isDebugEnabled()) {
			logger.debug("setCliente(Cliente) - end");
		}
	}

	/**
	 * Método setNumTransaccion
	 * 
	 * @param i
	 */
	public void setNumTransaccion(int i) {
		if (logger.isDebugEnabled()) {
			logger.debug("setNumTransaccion(int) - start");
		}

		numTransaccion = i;

		if (logger.isDebugEnabled()) {
			logger.debug("setNumTransaccion(int) - end");
		}
	}

	/**
	 * Método getMontoVuelto
	 * 
	 * @return
	 * double
	 */
	public double getMontoVuelto() {
		if (logger.isDebugEnabled()) {
			logger.debug("getMontoVuelto() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getMontoVuelto() - end");
		}
		return montoVuelto;
	}

	/**
	 * Método getMontoRemanente
	 * Obtiene el monto remanente (Vuelto).
	 * @return double - Monto remanente.
	 */
	public double getMontoRemanente() {
		if (logger.isDebugEnabled()) {
			logger.debug("getMontoRemanente() - start");
		}

		if(this.montoVuelto > 0)
			this.montoRemanente = 1 - (this.montoVuelto - StrictMath.floor(this.montoVuelto));
		else montoRemanente = 0.00;	

		if (logger.isDebugEnabled()) {
			logger.debug("getMontoRemanente() - end");
		}
		return montoRemanente;
	}

	/**
	 * Método setMontoRemanente
	 * 
	 * @param d
	 */
	public void setMontoRemanente(double d) {
		if (logger.isDebugEnabled()) {
			logger.debug("setMontoRemanente(double) - start");
		}

		montoRemanente = d;

		if (logger.isDebugEnabled()) {
			logger.debug("setMontoRemanente(double) - end");
		}
	}

	/**
	 * Método getDuracion
	 * 
	 * @return short
	 */
	public long getDuracion() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDuracion() - start");
		}

		long returnlong = duracion = duracion + Control.getCronometro(inicio);
		if (logger.isDebugEnabled()) {
			logger.debug("getDuracion() - end");
		}
		return returnlong;
	}

	/**
	 * Método setDuracion
	 * 
	 * @param s
	 */
	public void setDuracion(long segundos) {
		if (logger.isDebugEnabled()) {
			logger.debug("setDuracion(long) - start");
		}

		duracion = segundos;

		if (logger.isDebugEnabled()) {
			logger.debug("setDuracion(long) - end");
		}
	}

	/**
	 * Método getNumComprobanteFiscal
	 * 
	 * @return
	 * int
	 */
	public int getNumComprobanteFiscal() {
		if (logger.isDebugEnabled()) {
			logger.debug("getNumComprobanteFiscal() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getNumComprobanteFiscal() - end");
		}
		return numComprobanteFiscal;
	}

	/**
	 * Método setNumComprobanteFiscal
	 * 
	 * @param i
	 * void
	 */
	public void setNumComprobanteFiscal(int i) {
		if (logger.isDebugEnabled()) {
			logger.debug("setNumComprobanteFiscal(int) - start");
		}

		numComprobanteFiscal = i;

		if (logger.isDebugEnabled()) {
			logger.debug("setNumComprobanteFiscal(int) - end");
		}
	}
	public String getCodAutorizante() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCodAutorizante() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getCodAutorizante() - end");
		}
		return codAutorizante;
	}
	
	public String getAutorizanteAuditoria() {
		if (logger.isDebugEnabled()) {
			logger.debug("getAutorizanteAuditoria() - start");
		}

		if (codAutorizante != null)
		{
			if (logger.isDebugEnabled()) {
				logger.debug("getAutorizanteAuditoria() - end");
			}
			return codAutorizante;
		}
		else
		{
			if (logger.isDebugEnabled()) {
				logger.debug("getAutorizanteAuditoria() - end");
			}
			return codCajero;
		}
	}
	public void setCodAutorizante(String codAutorizante) {
		if (logger.isDebugEnabled()) {
			logger.debug("setCodAutorizante(String) - start");
		}

		this.codAutorizante = codAutorizante;

		if (logger.isDebugEnabled()) {
			logger.debug("setCodAutorizante(String) - end");
		}
	}
	/**
	 * @param string
	 */
	public void setCodCajero(String string) {
		codCajero = string;
	}
	
	
	/***************************************************************************
	 * METODOS AGREGADOS POR EL MÓDULO DE PROMOCIONES
	 ***************************************************************************/
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

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unchecked")
	public void setDetallesTransaccion(Vector<? extends Detalle> detallesTransaccion) {
		this.detallesTransaccion = (Vector<DetalleTransaccion>)detallesTransaccion;
	}

}