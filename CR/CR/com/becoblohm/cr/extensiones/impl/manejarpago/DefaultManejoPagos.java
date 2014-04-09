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

import org.apache.log4j.Logger;

import ve.com.megasoft.universal.error.VposUniversalException;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.BonoRegaloException;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.MontoPagoExcepcion;
import com.becoblohm.cr.excepciones.PagoExcepcion;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarbr.OpcionBR;
import com.becoblohm.cr.manejarbr.VentaBR;
import com.becoblohm.cr.manejarservicio.Abono;
import com.becoblohm.cr.manejarservicio.ListaRegalos;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.manejarventa.Venta;
import com.becoblohm.cr.utiles.Control;
import com.becoblohm.cr.utiles.MathUtil;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.becoblohm.cr.extensiones.ManejoPagos;
import com.becoblohm.cr.extensiones.ManejoPagosFactory;

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
	private static final Logger logger = Logger.getLogger(ManejoPagos.class);

	private PantallaPagos pantallaPagos = null;
	
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

	/**
	 * @param montoTrans
	 * @param pagosAnteriores
	 * @param cliente
	 * @return PantallaEliminacionPagos
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public PantallaEliminacionPagos instancePantallaEliminacionPago(double montoTrans,
			Vector<Pago> pagosAnteriores, Cliente cliente) {
		PantallaEliminacionPagos pep = new PantallaEliminacionPagos(montoTrans,
				pagosAnteriores, cliente);
		return pep;
	}

	/**
	 * @param montoTrans
	 * @param pagosAnteriores
	 * @param cliente
	 * @return PantallaEliminacionPagos
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public PantallaReversoDevoluciones instancePantallaReversoDevoluciones(double mtoDevolucion, Vector<Pago> pagosAnteriores, 
	Vector<FormaDePago> formasDePagoNoBancarias, Cliente cliente) {
		PantallaReversoDevoluciones prdev = new PantallaReversoDevoluciones(mtoDevolucion, pagosAnteriores, 
											formasDePagoNoBancarias, cliente);
		return prdev;
	}
	
	/**
	 * void
	 */
	protected void disposePantallaPagos() {
		if (this.pantallaPagos != null) {
			this.pantallaPagos.dispose();
		}
		this.pantallaPagos = null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#instancePantallaPago(com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago,
	 *      double)
	 */
	public PantallaDatosAdicionales instancePantallaDatosAdicionales(
			FormaDePago formaPago, double mto) throws PagoExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("Entrada Pantalla-Datos Adicionales 135");
		}
		PantallaDatosAdicionales pda = new PantallaDatosAdicionales(formaPago,
				mto);
		if (logger.isDebugEnabled()) {
			logger.debug("salida  Pantalla-Datos Adicionales 140");
		}
		return pda;
	}
	
	/**
	 * @param montoMaximo
	 * @param pagosAnteriores
	 * @param cliente
	 * @return PantallaPagos
	 */
	protected PantallaPagos instancePantallaPago(double montoMaximo,
			Vector<Pago> pagosAnteriores, Cliente cliente) {
		MensajesVentanas.aviso(Sesion.getCaja().getNombreEstado());
		return instancePantallaPago(montoMaximo, pagosAnteriores, cliente,
				cargarTodasFormasDePago());
	}

	/**
	 * @param montoMinimo
	 * @param pagosAnteriores
	 * @param cliente
	 * @param formasPago
	 *            void
	 * @return PantallaPagos
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	protected PantallaPagos instancePantallaPago(double montoMinimo,
			Vector<Pago> pagosAnteriores, Cliente cliente, Vector<FormaDePago> formasPago) {
		if (logger.isDebugEnabled()) {
			logger.debug("Entrada PantallaPagos Pago 91");
		}
		disposePantallaPagos();
		this.pantallaPagos = new PantallaPagos(montoMinimo, pagosAnteriores,
				formasPago, cliente);
		MensajesVentanas.centrarVentanaDialogo(this.pantallaPagos);
        
		if (logger.isDebugEnabled()) {
			logger.debug("Salida  PantallaPago 97");
		}
		return this.pantallaPagos;
	}
	
	/**
	 * @param montoMinimo
	 * @param pagosAnteriores
	 * @param cliente
	 * @param formasPago
	 * @param numTrans
	 *            void
	 * @return PantallaPagos
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	protected PantallaPagos instancePantallaPago(double montoMinimo,
			Vector<Pago> pagosAnteriores, Vector<FormaDePago> formasPago, Cliente cliente, int numTrans) {
		disposePantallaPagos();
		this.pantallaPagos = new PantallaPagos(montoMinimo, pagosAnteriores,
				formasPago, cliente, numTrans);
		MensajesVentanas.centrarVentanaDialogo(this.pantallaPagos);        
		return this.pantallaPagos;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Object> realizarPago(double montoMinimo, Cliente cliente, int numTrans) throws PagoExcepcion, MontoPagoExcepcion {
		return realizarPago(montoMinimo, cliente, numTrans, false);
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Object> realizarPago(double montoMinimo, Cliente cliente, int numTrans, boolean abonoApartado) throws PagoExcepcion, MontoPagoExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("realizarPago(double, Cliente) - start");
		}

		Vector<Pago> pagos = new Vector<Pago>();
		Vector<Object> returnVector = realizarPago(montoMinimo, pagos, cliente, numTrans, abonoApartado);
		if (logger.isDebugEnabled()) {
			logger.debug("realizarPago(double, Cliente) - end");
		}
		return returnVector;
	}
	
	/* Retorna en la posicion 0 los pagos y en la posicion 1 el vuelto */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Object> realizarPago(double montoMinimo, Vector<Pago> pagosAnteriores, Cliente cliente, int numTrans) throws PagoExcepcion, MontoPagoExcepcion {
		return realizarPago(montoMinimo, pagosAnteriores, cliente, numTrans, false);
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Object> realizarPago(double montoMinimo, Vector<Pago> pagosAnteriores, Cliente cliente, int numTrans, boolean abonoApartado) throws PagoExcepcion, MontoPagoExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("realizarPago(double, Vector, Cliente) - start");
		}
		

		double monto = 0;
		for (int i=0; i<pagosAnteriores.size(); i++)
			monto += ((Pago)pagosAnteriores.elementAt(i)).getMonto();

		if ((montoMinimo-monto)<=0.01)
			throw new MontoPagoExcepcion("Pagos Completados.\nNo se pueden realizar nuevos pagos");
		
		instancePantallaPago(montoMinimo, pagosAnteriores, this.getInstanceBaseDeDatosPago().cargarFormasDePago(abonoApartado),cliente, numTrans);
		monto = 0;
		for (int i=0; i<pagosAnteriores.size(); i++)
			monto += ((Pago)pagosAnteriores.elementAt(i)).getMonto();
		// Calculamos el Vuelto
		double vuelto = monto - montoMinimo;
		// Si no se completa el pago minimo se lanza un error
		if (this.pantallaPagos.isPagosAgregados()&&(vuelto <= -0.01)) 
			throw new PagoExcepcion("No se ha realizado el pago minimo de " + montoMinimo);
		
		Vector<Object> resultado = new Vector<Object>();
		resultado.addElement(pagosAnteriores);
		resultado.addElement(new Double(vuelto));
		resultado.addElement(new Boolean(this.pantallaPagos.isPagosAgregados()));

		if (logger.isDebugEnabled()) {
			logger.debug("realizarPago(double, Vector, Cliente) - end");
		}
		return resultado;
	}

	/* Retorna en la posicion 0 los pagos y en la posicion 1 el vuelto */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Object> realizarNuevoPago(double montoMaximo, Vector<Pago> pagosAnteriores, Cliente cliente) throws PagoExcepcion, MontoPagoExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("realizarNuevoPago(double, Vector, Cliente) - start");
		}
		
		
		double monto = 0;
		for (int i=0; i<pagosAnteriores.size(); i++)
			monto += ((Pago)pagosAnteriores.elementAt(i)).getMonto();

		if ((montoMaximo-monto)<=0)
			throw new MontoPagoExcepcion("Pagos Completados.\nNo se pueden realizar nuevos pagos");

		instancePantallaPago (montoMaximo, pagosAnteriores,  cliente, this.getInstanceBaseDeDatosPago().cargarFormasDePago(false));
        
		monto = 0;
		for (int i=0; i<pagosAnteriores.size(); i++)
			monto += ((Pago)pagosAnteriores.elementAt(i)).getMonto();
		// Calculamos el Vuelto
		double vuelto = monto - montoMaximo;
		
		Vector<Object> resultado = new Vector<Object>();
		resultado.addElement(pagosAnteriores);
		resultado.addElement(new Double(vuelto));

		if (logger.isDebugEnabled()) {
			logger.debug("realizarNuevoPago(double, Vector, Cliente) - end");
		}
		return resultado;
	}

	/**
	 * Método realizarPagoAbonos
	 * 
	 * @param montoAbonos
	 * @return Pago
	 * @throws PagoExcepcion
	 */
	public Pago realizarPagoAbonos(double montoAbonos) throws PagoExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("realizarPagoAbonos(double) - start");
		}

		//Calculamos el monto de los abonos
		FormaDePago formaDePago =  this.getInstanceBaseDeDatosPago().cargarFormaDePagoAbono();
		Pago p = new Pago(formaDePago, montoAbonos, null, null, null, null, 0, null);

		if (logger.isDebugEnabled()) {
			logger.debug("realizarPagoAbonos(double) - end");
		}
		return p;
	}


	/**
	 * Método realizarPagoAbonos
	 * 
	 * @param montoAbonos
	 * @return Pago
	 * @throws PagoExcepcion
	 */
	public Pago realizarPagoDevolucion(double montoAbonos) throws PagoExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("realizarPagoDevolucion(double) - start");
		}

		//Calculamos el monto de los abonos
		FormaDePago formaDePago =  this.getInstanceBaseDeDatosPago().cargarFormaDePago(Sesion.FORMA_PAGO_EFECTIVO);
		Pago p = new Pago(formaDePago, montoAbonos, null, null, null, null, 0, null);
		
		if (logger.isDebugEnabled()) {
			logger.debug("realizarPagoAbonos(double) - end");
		}
		return p;
	}
	
	

	/**
	 * Método realizarReversosParaDevoluciones
	 * 		Realiza el manejo de los pagos referentes a la forma de reverso de las devoluciones.
	 * Unicamente se podrán seleccionar entre las formas depago no bancarias. 
	 * @param mtoDevolucion Monto de la devolucion.
	 * @param pagosAnteriores Pagos realizados anteriormente en la devolucion.
	 * @return int 0 si no se agregaron pagos (Es un subtotal), 1 si se agregaron pagos y se presionó Escape, 2 si se termino el ingreso de pagos
	 * @throws PagoExcepcion - Si ocurre un error en el manejo de los pagos.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public int realizarReversosParaDevoluciones(double mtoDevolucion, Vector<Pago> pagosAnteriores, Cliente cliente) throws PagoExcepcion {
		if (logger.isDebugEnabled()) {
			logger
					.debug("realizarReversosParaDevoluciones(double, Vector, Cliente) - start");
		}
		
		PantallaReversoDevoluciones pr = instancePantallaReversoDevoluciones(mtoDevolucion, pagosAnteriores, this.getInstanceBaseDeDatosPago().cargarFormasDePagoNoBancarias(), cliente);
		MensajesVentanas.centrarVentanaDialogo(pr);

		int returnint = pr.getResultado();
		if (logger.isDebugEnabled()) {
			logger
					.debug("realizarReversosParaDevoluciones(double, Vector, Cliente) - end");
		}
		return returnint;
	}

	/* Retorna en la posicion 0 los pagos y en la posicion 1 el vuelto */
	public Vector<Object> eliminarPago(double montoTrans, Vector<Pago> pagosAnteriores, Cliente cliente) {
		if (logger.isDebugEnabled()) {
			logger.debug("eliminarPago(double, Vector, Cliente) - start");
		}
		
		PantallaEliminacionPagos pep = instancePantallaEliminacionPago(montoTrans, pagosAnteriores, cliente);
		MensajesVentanas.centrarVentanaDialogo(pep);

		//Verifico si el pago eliminado era en una devolución por cambio y además era en efectivo
		if (CR.meVenta.getDevolucion() != null) //Es una devolución
		{
			boolean pagoEfectivo = false;
		
			//Busco pago en efectivo si lo encuentra devuelvo verdadero
			for (int i=0; i<pagosAnteriores.size(); i++) {
				FormaDePago forma = ((Pago)pagosAnteriores.elementAt(i)).getFormaPago();
					if (forma.getCodigo().equals(Sesion.FORMA_PAGO_EFECTIVO)){
						pagoEfectivo = true;
						if (((Pago)pagosAnteriores.elementAt(i)).getMonto() == 0) //Actualizo el monto en efectivo de la devolución
							((Pago)pagosAnteriores.elementAt(i)).setMonto(CR.meVenta.getDevolucion().getMontoBase() + CR.meVenta.getDevolucion().getMontoImpuesto());
						else
							((Pago)pagosAnteriores.elementAt(i)).setMonto(CR.meVenta.getDevolucion().getMontoBase() + CR.meVenta.getDevolucion().getMontoImpuesto() + ((Pago)pagosAnteriores.elementAt(i)).getMonto());
					}
			}
			if (!pagoEfectivo){
				try {
					CR.meVenta.getVenta().getPagos().addElement(realizarPagoDevolucion(CR.meVenta.getDevolucion().getMontoBase() + CR.meVenta.getDevolucion().getMontoImpuesto()));
				} catch (PagoExcepcion e) {
					logger.error(e.getMensaje());
				}
			}
		}
		
		double monto = 0;
		double montoPVuelto = 0;
		for (int i=0; i<pagosAnteriores.size(); i++) {
			monto += ((Pago)pagosAnteriores.elementAt(i)).getMonto();
			if (((Pago)pagosAnteriores.elementAt(i)).getFormaPago().isPermiteVuelto())
				montoPVuelto += ((Pago)pagosAnteriores.elementAt(i)).getMonto();
		}
		// Calculamos el Vuelto
		double vuelto = (montoPVuelto>=(monto - montoTrans)) ? monto - montoTrans : -1;
		
		Vector<Object> resultado = new Vector<Object>();
		resultado.addElement(pagosAnteriores);
		resultado.addElement(new Double(vuelto));

		

		if (logger.isDebugEnabled()) {
			logger.debug("eliminarPago(double, Vector, Cliente) - end");
		}
		return resultado;
	}



	/* Retorna en la posicion 0 los pagos y en la posicion 1 el vuelto */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Pago realizarPagoRetencion(double montoImpuesto, Vector<Pago> pagosVenta) throws PagoExcepcion {
        if (logger.isDebugEnabled()) {
            logger.debug("realizarPagoRetencion(double, Vector) - start");
        }

        Pago pago = null;
        FormaDePago formaPago = this.getInstanceBaseDeDatosPago()
                .cargarFormaDePagoRetencion();

        // Si existe la forma de pago retencion la eliminamos del vector de
        // pagos.
        for (int i = 0; i < pagosVenta.size(); i++) {
            if (((Pago) pagosVenta.elementAt(i)).getFormaPago().getCodigo()
                    .equalsIgnoreCase(formaPago.getCodigo())) {
                pagosVenta.removeElementAt(i);
                break;
            }
        }
        // Chequeamos si se requieren datos adicionales
        double mto = MathUtil.roundDouble((montoImpuesto * Sesion
                .getPorcentajeRetencionIVA()) / 100);
            
            PantallaDatosAdicionales pda = instancePantallaDatosAdicionales(
                    formaPago, mto);
            MensajesVentanas.centrarVentanaDialogo(pda);
            pago = pda.obtenerDatosAdicionales();
      

        try {
            CR.crVisor.enviarString(pago.getFormaPago().getNombre()
                    .toUpperCase(), 0, Control.formatearMonto(mto), 2);
        } catch (Exception e) {
            logger.error("realizarPagoRetencion(double, Vector)", e);
        }

        pagosVenta.addElement(pago);

        if (logger.isDebugEnabled()) {
            logger.debug("realizarPagoRetencion(double, Vector) - end");
        }
        return pago;
	}

	/* Retorna en la posicion 0 los pagos y en la posicion 1 el vuelto */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void recalcularPagoRetencion(double montoImpuesto, Vector<Pago> pagosVenta) {
		if (logger.isDebugEnabled()) {
			logger.debug("recalcularPagoRetencion(double, Vector) - start");
		}

		try {
			FormaDePago formaPago = this.getInstanceBaseDeDatosPago().cargarFormaDePagoRetencion();
			double mto = MathUtil.roundDouble((montoImpuesto * Sesion.getPorcentajeRetencionIVA()) / 100);
			// Si existe la forma de pago retencion la eliminamos del vector de pagos.
			for (int i=0; i<pagosVenta.size(); i++) {
				Pago pago = (Pago)pagosVenta.elementAt(i);
				if (pago.getFormaPago().getCodigo().equalsIgnoreCase(formaPago.getCodigo())) {
					pago.setMonto(mto);
					break;
				}
			}
		} catch (Exception e1) {
			logger.error("recalcularPagoRetencion(double, Vector)", e1);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("recalcularPagoRetencion(double, Vector) - end");
		}
	}
	
	/* Retorna en la posicion 0 los pagos y en la posicion 1 el vuelto */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void recalcularPagoDevolucion(double montoAnterior, double montoNuevo, Vector<Pago> pagosVenta) {
		if (logger.isDebugEnabled()) {
			logger.debug("recalcularPagoRetencion(double, Vector) - start");
		}

		try {
			FormaDePago formaPago = this.getInstanceBaseDeDatosPago().cargarFormaDePagoEfectivo();
			double mto = MathUtil.roundDouble(montoNuevo);
			// Si existe la forma de pago retencion la eliminamos del vector de pagos.
			for (int i=0; i<pagosVenta.size(); i++) {
				Pago pago = (Pago)pagosVenta.elementAt(i);
				if (pago.getFormaPago().getCodigo().equalsIgnoreCase(formaPago.getCodigo())) {
					//Resto el monto anterior de devolución y sumo el nuevo valor de devolución
					pago.setMonto((pago.getMonto() - montoAnterior) + mto);
					CR.meVenta.getDevolucion().getPagos().removeAllElements();
					CR.meVenta.getDevolucion().getPagos().addElement(this.realizarPagoDevolucion(mto));
					break;
				}
			}
		} catch (Exception e1) {
			logger.error("recalcularPagoRetencion(double, Vector)", e1);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("recalcularPagoRetencion(double, Vector) - end");
		}
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<FormaDePago> cargarTodasFormasDePago() {
		return this.getInstanceBaseDeDatosPago().cargarTodasFormasDePago();
	}

	public FormaDePago cargarFormaDePago(String codFPago) {
		return this.getInstanceBaseDeDatosPago().cargarFormaDePago(codFPago);
	}

	public boolean validarSaldoCliente(Cliente cliente, double mto) throws PagoExcepcion {
		return this.getInstanceBaseDeDatosPago().validarSaldoCliente(cliente,mto);
	}

	public void disminuirSaldoCliente(Cliente cliente, double mto) throws PagoExcepcion {
		this.getInstanceBaseDeDatosPago().disminuirSaldoCliente(cliente,mto);
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#obtenerBancos()
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Banco> obtenerBancos() {
		return this.getInstanceBaseDeDatosPago().obtenerBancos();
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#incrementarSaldoCliente(com.becoblohm.cr.manejarventa.Cliente, double)
	 */
	public void incrementarSaldoCliente(Cliente cliente, double mto) throws PagoExcepcion {
		this.getInstanceBaseDeDatosPago().incrementarSaldoCliente(cliente, mto);
		
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#registrarPagos(com.becoblohm.cr.manejarventa.Venta,
	 *      java.sql.Statement)
	 */
	public double registrarPagos(Statement _loteSentenciasCR, Venta _venta)
			throws SQLException {
		return this.getInstanceBaseDeDatosPago().registrarPagos(
				_loteSentenciasCR, _venta);
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#registrarPagos(java.sql.Statement, com.becoblohm.cr.manejarservicio.Abono, int)
	 */
	public void registrarPagos(Statement _loteSentenciasCR, Abono _ab,
			int _numServ) throws SQLException {
		this.getInstanceBaseDeDatosPago().registrarPagos(_loteSentenciasCR,
				_ab, _numServ);
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#registrarPago(java.sql.Statement, com.becoblohm.cr.manejarservicio.Abono, int, com.becoblohm.cr.extensiones.impl.manejarpago.Pago, int)
	 */
	public void registrarPago(Statement _loteSentenciasCR, Abono _ab,
			int _numServ, Pago _pagoActual, int _correlativoItem)
			throws SQLException {
		this.getInstanceBaseDeDatosPago().registrarPago(_loteSentenciasCR, _ab,
				_numServ, _pagoActual, _correlativoItem);
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#registrarPagos(java.sql.Statement, com.becoblohm.cr.manejarservicio.ListaRegalos, int, double)
	 */
	public void registrarPagos(Statement loteSentenciasCR, ListaRegalos lista, int numOperacion, double vuelto) throws SQLException {
		this.getInstanceBaseDeDatosPago().registrarPagos(loteSentenciasCR, lista,
		numOperacion, vuelto);
		
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#registrarPago(java.sql.Statement, com.becoblohm.cr.manejarservicio.ListaRegalos, int, com.becoblohm.cr.extensiones.impl.manejarpago.Pago, int, double)
	 */
	public void registrarPago(Statement loteSentencias, ListaRegalos lista, int numOperacion, Pago pago, int correlativoItem, double vuelto) throws SQLException {
		this.getInstanceBaseDeDatosPago().registrarPago(loteSentencias, lista, numOperacion, pago, correlativoItem, 
		vuelto);
		
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#sincronizarDatosMaestroPagos()
	 */
	public void sincronizarDatosMaestroPagos() throws BaseDeDatosExcepcion {
		// TODO Apéndice de método generado automáticamente
		
	}
	
	public void sincronizarDatosExtraPagos() throws BaseDeDatosExcepcion {
		// EN LA IMPLEMENTACION POR DEFECTO NO HACE NADA.
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#efectuarCierre()
	 */
	public void efectuarCierre() {
		// EN LA IMPLEMENTACION POR DEFECTO NO HACE NADA.
		
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.extensiones.ManejoPagos#registrarPagosLRRemota(com.becoblohm.cr.manejarservicio.ListaRegalos, net.n3.nanoxml.IXMLElement, double)
	 */
/*	public void registrarPagosLRRemota(ListaRegalos lista, IXMLElement solicitud, double vuelto) throws SQLException {
		this.getInstanceBaseDeDatosPago().registrarPagosLRRemota(lista, solicitud, vuelto);
	}*/
	
	public FormaDePago obtenerFormaDePagoCuponDescuento() throws PagoExcepcion{
		return  (new BaseDeDatosPago()).obtenerFormaDePagoCuponDescuento();
	}

	public Pago obtenerPagoOperacion(FormaDePago formaPago, double mto, String cedula) throws PagoExcepcion, VposUniversalException {
		PantallaDatosAdicionales pda = ManejoPagosFactory.getInstance().instancePantallaDatosAdicionales(
			formaPago, mto);
		MensajesVentanas.centrarVentanaDialogo(pda);
		return pda.obtenerDatosAdicionales();
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void configurarOpcionesCards(Vector<OpcionBR> opcionesActivas) throws ExcepcionCr {
	}

	public void consultaSaldoCards() throws BonoRegaloException{
	}

	
	public double registrarPagos(Statement loteSentenciasCR, VentaBR venta) throws SQLException {
		return (new BaseDeDatosPago()).registrarPagosBR(loteSentenciasCR, venta);
	}

	public Vector<Object> cargaRecargaSaldoCards(double monto, String cedula) throws BonoRegaloException{
		return new Vector<Object>();
	}

	public int procesarAnulacionTarjeta(String codTarjeta, int numSeq) throws BonoRegaloException {
		// TODO Apéndice de método generado automáticamente
		return 0;
	}
}
