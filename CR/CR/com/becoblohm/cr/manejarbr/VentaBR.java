package com.becoblohm.cr.manejarbr;

import java.sql.Time;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.AfiliadoUsrExcepcion;
import com.becoblohm.cr.excepciones.AutorizacionExcepcion;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ClienteExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.LineaExcepcion;
import com.becoblohm.cr.excepciones.ProductoExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.manejadorauditoria.Auditoria;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.manejarventa.Producto;
import com.becoblohm.cr.manejarventa.Transaccion;
import com.becoblohm.cr.mediadoresbd.BaseDeDatosServicio;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.sincronizador.HiloSyncTransaccionesBR;
import com.becoblohm.cr.utiles.MathUtil;
import com.epa.crprinterdriver.PrinterNotConnectedException;

/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
* Fecha: agosto 2011
*/
public class VentaBR extends Transaccion{
	
	private static final long serialVersionUID = 1L;
	protected Vector<DetalleTransaccionBR> detallesTransaccionBR;
	private Vector<Pago> pagos;
	private boolean ventaExenta;
	private int tarjetasRecargadas;
	private Vector<String> condicionesServicio = new Vector<String>();
	private Cliente vendedor = null;
	private Vector<ComprobanteFiscal> comprobantesFiscales;
	private char accion;
	private Cliente clienteAnterior;
	
	/**
	 * Utilizado cuando la venta de BR es por una promoción al usar la tarjeta en alguna compra
	 */
	private String codTarjetaPagoPromocion;
	
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(VentaBR.class);
	
	public VentaBR(int tda, Date fecha, int cajaFinaliza,  Time horaInicia, String cajero,
			char tipoTrans, char edoTrans){
		super();
		/*try {
			this.numTransaccion = Sesion.getCaja().getNuevoNumTransaccionBR();
		} catch (BaseDeDatosExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (ConexionExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}*/
		pagos = new Vector<Pago>();
		detallesTransaccionBR = new Vector<DetalleTransaccionBR>();
		this.fechaTrans = fecha;
		this.codTienda = tda;
		this.numCajaFinaliza = cajaFinaliza;
		this.horaInicia = horaInicia;
		this.codCajero =  cajero;
		this.tipoTransaccion = tipoTrans;
		this.estadoTransaccion = edoTrans;

		//Obtener las condiciones del servicio de Bono Regalo.
		this.condicionesServicio = BaseDeDatosServicio.consultarCondicionesBR();
	}
	
	
	public VentaBR(
			int tda, 
			Date fecha, 
			int cajaFinaliza, 
			int numtransaccion, 
			char tipoTransaccion, 
			String cajero,
			char edoTrans,
			double montoBase,
			double montoImpuesto,
			double vueltoCliente,
			double montoRemanente,
			int lineasFacturacion,
			String codAutorizante,
			String codCorporativo
			){
		super();
		/*try {
			this.numTransaccion = Sesion.getCaja().getNuevoNumTransaccionBR();
		} catch (BaseDeDatosExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (ConexionExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}*/
		pagos = new Vector<Pago>();
		detallesTransaccionBR = new Vector<DetalleTransaccionBR>();
		this.fechaTrans = fecha;
		this.codTienda = tda;
		this.numCajaFinaliza = cajaFinaliza;
		this.codCajero =  cajero;
		this.tipoTransaccion = tipoTransaccion;
		this.estadoTransaccion = edoTrans;
		this.montoBase=montoBase;
		this.montoImpuesto=montoImpuesto;
		this.montoVuelto = vueltoCliente;
		this.montoRemanente = montoRemanente;
		this.lineasFacturacion = lineasFacturacion;
		this.codAutorizante = codAutorizante;
		this.numTransaccion = numtransaccion;
		try {
			if(codCorporativo!=null)
				this.asignarVendedorCorporativo(codCorporativo);
		} catch (AfiliadoUsrExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (BaseDeDatosExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (ClienteExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (ConexionExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
		
		//Obtener las condiciones del servicio de Bono Regalo.
		this.condicionesServicio = BaseDeDatosServicio.consultarCondicionesBR();
	}
	
	/**
	 * Crea registro de la nueva venta de Bonos Regalo
	 * @throws ConexionExcepcion 
	 * @throws BaseDeDatosExcepcion 
	 *
	 */
	/*public void crearVentaBR() throws BaseDeDatosExcepcion, ConexionExcepcion{
		if (logger.isDebugEnabled()) {
			logger.debug("crearVentaBR() - start");
		}
		BaseDeDatosServicio.registrarVentaBR(this);
		this.registrada = true;
		if (logger.isDebugEnabled()) {
			logger.debug("crearVentaBR() - end");
		}
	}*/
	
	protected void actualizarPreciosDetalle(Producto prod) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		// TODO Apéndice de método generado automáticamente
		
	}

	protected void anularProducto(int renglon) throws ProductoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		// TODO Apéndice de método generado automáticamente
		
	}

	/**
	 * Debera cambiar el estado de la transacción de "En Proceso" a "Finalizada"
	 * 
	 */
	public void commitTransaccion() {
		char tipoComprobante = 'V';
		switch(CR.meServ.getVentaBR().getAccion()){
		case Sesion.ACCION_BONO_REGALO_REIMPRESION:
			if(CR.meServ.getVentaBR().getEstadoTransaccion()==Sesion.BONO_REGALO_FACTURADA){
				CR.meServ.getVentaBR().setEstadoTransaccion(Sesion.BONO_REGALO_ANULADA);
				tipoComprobante = Sesion.TIPO_COMPROBANTE_BR_ANULACION;
			}else if(CR.meServ.getVentaBR().getEstadoTransaccion()==Sesion.BONO_REGALO_ANULADA){
				CR.meServ.getVentaBR().setEstadoTransaccion(Sesion.BONO_REGALO_FACTURADA);
				tipoComprobante = Sesion.TIPO_COMPROBANTE_BR_VENTA;
			}
			this.horaFin = Sesion.getHoraSistema();
			
			try {
				BaseDeDatosServicio.actualizarComprobanteFiscalBR(CR.meServ.getVentaBR(), tipoComprobante, false);
				BaseDeDatosServicio.actualizarVentaBRNoSincronizada(CR.meServ.getVentaBR(), false);
			} catch (BaseDeDatosExcepcion e) {
				logger.error("commitTransaccion()", e);
			}
			
			break;
		case Sesion.ACCION_BONO_REGALO_FACTURACION:
			//Cambiado por solicitud de facturar siempre las transacciones de bono regalo
			/*if(CR.meServ.getVentaBR().getEstadoTransaccion()==Sesion.BONO_REGALO_LISTO){
				CR.meServ.getVentaBR().setEstadoTransaccion(Sesion.BONO_REGALO_FACTURADA);
				tipoComprobante = Sesion.TIPO_COMPROBANTE_BR_VENTA;
			}*/
			if(CR.meServ.getVentaBR().getEstadoTransaccion()==Sesion.BONO_REGALO_COMPLETADA){
				CR.meServ.getVentaBR().setEstadoTransaccion(Sesion.BONO_REGALO_FACTURADA);
				tipoComprobante = Sesion.TIPO_COMPROBANTE_BR_VENTA;
			}
			try {
				BaseDeDatosServicio.actualizarComprobanteFiscalBR(CR.meServ.getVentaBR(), tipoComprobante, true);
				BaseDeDatosServicio.actualizarVentaBRNoSincronizada(CR.meServ.getVentaBR(), true);
				new HiloSyncTransaccionesBR().iniciar();
			} catch (BaseDeDatosExcepcion e) {
				logger.error("commitTransaccion()", e);
			}
			
			break;
		case Sesion.ACCION_BONO_REGALO_ANULACION:
			if(CR.meServ.getVentaBR().getEstadoTransaccion()==Sesion.BONO_REGALO_EN_PROCESO){
				CR.meServ.getVentaBR().setEstadoTransaccion(Sesion.BONO_REGALO_ELIMINADA);
				tipoComprobante = Sesion.TIPO_COMPROBANTE_BR_ANULACION;
			} else {
				CR.meServ.getVentaBR().setEstadoTransaccion(Sesion.BONO_REGALO_ANULADA);
				tipoComprobante = Sesion.TIPO_COMPROBANTE_BR_ANULACION;
			}
			
			try {
				BaseDeDatosServicio.actualizarComprobanteFiscalBR(CR.meServ.getVentaBR(), tipoComprobante, false);
				BaseDeDatosServicio.actualizarVentaBRNoSincronizada(CR.meServ.getVentaBR(), false);
			} catch (BaseDeDatosExcepcion e) {
				logger.error("commitTransaccion()", e);
			}
			
			break;
		}
	}

	/**
	 * Agrega un detalle de tarjeta de Bono Regalo.
	 * Si ya existe esa tarjeta incrementa el saldo
	 * Almacena inmediatamente en la base de datos
	 * @param detalle
	 * @throws ConexionExcepcion 
	 * @throws BaseDeDatosExcepcion 
	 */
	public void agregarDetalle(DetalleTransaccionBR detalle) throws BaseDeDatosExcepcion, ConexionExcepcion{
		if (logger.isDebugEnabled()) {
			logger.debug("agregarDetalle(DetalleTransaccionBR) - start");
		}
		/*String codTarjeta = detalle.getCodTarjeta();
		boolean existe = false;
		for(int i=0;i<this.getDetallesTransaccionBR().size();i++){
			DetalleTransaccionBR detalletemp = (DetalleTransaccionBR)this.getDetallesTransaccionBR().elementAt(i);
			if(detalletemp.getCodTarjeta().equalsIgnoreCase(codTarjeta)){
				existe=true;
				detalletemp.setMonto(detalletemp.getMonto()+detalle.getMonto());
				
				//Update a la base de datos
				BaseDeDatosServicio.actualizarDetalleBR(this, detalletemp, i);
				break;
			}
		}*/
		//if(!existe){
		//Almacenar detalle en memoria
		this.getDetallesTransaccionBR().addElement(detalle);
		
		//Almacenar detalle en la base de datos
		//BaseDeDatosServicio.insertarNuevoDetalleBR(this,detalle);
		//} 
		
		//BaseDeDatosServicio.actualizarVentaBRNoSincronizada(this);
		
		if (logger.isDebugEnabled()) {
			logger.debug("agregarDetalle(DetalleTransaccionBR) - end");
		}
	}
	
	public void finalizarTransaccion() throws ConexionExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, PrinterNotConnectedException {
		this.numCajaFinaliza = Sesion.getCaja().getNumero();
		this.lineasFacturacion = this.detallesTransaccionBR.size();
		this.horaFin = Sesion.getHoraSistema();
		this.estadoTransaccion = Sesion.BONO_REGALO_EN_PROCESO;
		try {
			this.numTransaccion = Sesion.getCaja().getNuevoNumTransaccionBR();
		} catch (BaseDeDatosExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (ConexionExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}

		this.estadoTransaccion = Sesion.BONO_REGALO_EN_PROCESO;
		BaseDeDatosServicio.registrarTransaccionBR(this);
		
		BaseDeDatosServicio.actualizarVentaBRNoSincronizada(this, true);
		
	}

	public void rollbackTransaccion() {
		// TODO Apéndice de método generado automáticamente
		
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<DetalleTransaccionBR> getDetallesTransaccionBR() {
		return detallesTransaccionBR;
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void setDetallesTransaccionBR(Vector<DetalleTransaccionBR> detallesTransaccionBR) {
		this.detallesTransaccionBR = detallesTransaccionBR;
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Pago> getPagos() {
		return pagos;
	}

	public void setPagos(Vector<Pago> pagos) {
		this.pagos = pagos;
	}

	/**
	 * Registra los pagos en efectivo.
	 * @param p
	 * @return double - El monto que falta por pagar. Si es positivo es lo que falta por pagar.
	 * Si es negativo es vuelto al cliente.
	 */
	public double efectuarPago(Pago p) throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("efectuarPago(Pago) - start");
		}
		
		double resto;
		
		resto = this.consultarMontoTrans() - obtenerMontoPagado();
		if (resto <= 0) {
			this.montoVuelto = MathUtil.roundDouble(-resto);
		}
		
		// Registramos la auditoría de esta función
		
		Auditoria.registrarAuditoria("Pago con " + p.getFormaPago().getNombre() + ". MontoPago " + p.getMonto(),'T');
		
		if (logger.isDebugEnabled()) {
			logger.debug("efectuarPago(Pago) - end");
		}
		return resto;

	}
	
	/**
	 * Obtiene el monto cancelado hasta el momento.
	 * @return double - El monto de la venta cancelado.
	 */
	public double obtenerMontoPagado() {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerMontoPagado() - start");
		}

		double monto = 0.0;
		for (int i=0; i<pagos.size(); i++) {
			monto += ((Pago)pagos.elementAt(i)).getMonto();
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerMontoPagado() - end");
		}
		return monto;
	}

	public boolean isVentaExenta() {
		return ventaExenta;
	}

	public void setVentaExenta(boolean ventaExenta) {
		this.ventaExenta = ventaExenta;
	}
	
	/**
	 * Asigna el cliente indicado a la venta. Si el cliente no es un afiliado se lanza una excepción indicandola eventualidad. 
	 * En caso de ser afiliado, valida que si es un COLABORADOR le aplica el descto a COLABORADOR a la venta.
	 * @param codigo
	 */
	public void asignarCliente(Cliente cliente, String autorizante) throws XmlExcepcion, FuncionExcepcion, AfiliadoUsrExcepcion, BaseDeDatosExcepcion, LineaExcepcion, AutorizacionExcepcion, ClienteExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("asignarCliente(String, String) - start");
		}

		// Verificamos que el cliente no sea el usuario actual
		if ((cliente.getNumFicha()!=null) && Sesion.getUsuarioActivo().getNumFicha().equalsIgnoreCase(cliente.getNumFicha()))
			throw new AfiliadoUsrExcepcion("El cliente no puede ser el cajero activo");
		
		// Verificamos que el cliente no sea el usuario que autoriza la funcion (Si aplica)
		if ((autorizante!=null) && (cliente!=null) && autorizante.equalsIgnoreCase(cliente.getNumFicha()))
			throw (new AutorizacionExcepcion ("La función no pudo ser autorizada.\nEl autorizante debe ser distinto al cliente asignado."));

		//Registramos la auditoría de esta función
		//Sesion.setUbicacion("FACTURACION","asignarCliente");
		Auditoria.registrarAuditoria("Identificando Cliente " +  cliente.getCodCliente(),'T');
		this.setCliente(cliente);
		this.verMensajesCliente();	

		if (logger.isDebugEnabled()) {
			logger.debug("asignarCliente(String, String) - end");
		}
	}
	
	/**
	 * Método verMensajesCliente
	 * 
	 * 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private void verMensajesCliente(){
		if (logger.isDebugEnabled()) {
			logger.debug("verMensajesCliente() - start");
		}

		if(CR.me != null){
			if(this.getCliente().getCodCliente() != null && (this.getCliente().getMensajes().size() > 0)) {
				Iterator<StringBuffer> ciclo = this.getCliente().getMensajes().iterator();
				while(ciclo.hasNext()){
					CR.me.mostrarAviso(ciclo.next().toString(), true);
				}
				
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("verMensajesCliente() - end");
		}
	}
	
	/**
	 * Método obtenerCantidadProds
	 * 		Retorna la cantidad de productos que se encuentran en el detalle.
	 * @return float - entero que representa el total de productos presentes en la transacción.
	 */
	public float obtenerCantidadProds() {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCantidadProds() - start");
		}

		float cantTotal = 0;
		for (int i=0; i<this.detallesTransaccionBR.size();i++){
			cantTotal++;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCantidadProds() - end");
		}
		return cantTotal;
	}

	/**
	 * Actualiza el Monto de a transaccion, calcula el nuevo monto a partir del detalle de la factura.
	 */
	public void actualizarMontoTransaccion() {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarMontoTransaccion() - start");
		}

		double precioFinal = 0.0;
		double montoImpuesto = 0.0;
		
		for (int i=0; i<detallesTransaccionBR.size(); i++) {
			precioFinal += ((DetalleTransaccionBR) detallesTransaccionBR.elementAt(i)).getMonto();
			montoImpuesto += ((DetalleTransaccionBR) detallesTransaccionBR.elementAt(i)).getMontoImpuesto();
		}
		
		this.montoImpuesto = MathUtil.roundDouble(montoImpuesto);
		this.montoBase = MathUtil.roundDouble(precioFinal);

		// Si existe una retencion la recalculamos.
		//CR.me.recalcularRetencionIVA();

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarMontoTransaccion() - end");
		}
	}

	public int getTarjetasRecargadas() {
		return tarjetasRecargadas;
	}

	public void setTarjetasRecargadas(int tarjetasRecargadas) {
		this.tarjetasRecargadas = tarjetasRecargadas;
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<String> getCondicionesServicio() {
		return condicionesServicio;
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void setCondicionesServicio(Vector<String> condicionesServicio) {
		this.condicionesServicio = condicionesServicio;
	}

	public Cliente getVendedor() {
		return vendedor;
	}

	public void setVendedor(Cliente vendedor) {
		this.vendedor = vendedor;
	}

	public void asignarVendedorCorporativo(String codEmpleado) throws AfiliadoUsrExcepcion, BaseDeDatosExcepcion, ClienteExcepcion, ConexionExcepcion {
		Cliente cte = MediadorBD.obtenerCliente(codEmpleado);
		
		if (cte.getNumFicha()==null) {
			throw new ClienteExcepcion("Código de Colaborador inválido");
		} else {
			this.setVendedor(cte);
		}
	}
	
	
	public void setEstadoTransaccion(char estado){
		this.estadoTransaccion = estado;
	}
	
	/**
	 * Determina si algun detalle tiene tarjetas ya recargadas (con codigo asignado)
	 * @return
	 */
	public boolean isTarjetasRecargadas(){
		for(int i=0;i<this.getDetallesTransaccionBR().size();i++){
			DetalleTransaccionBR detalle = (DetalleTransaccionBR)this.getDetallesTransaccionBR().elementAt(i);
			if(detalle.getCodTarjeta()!=null && !detalle.getCodTarjeta().equalsIgnoreCase("") && detalle.getEstadoRegistro()!=Sesion.DETALLE_BR_ELIMINADO){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Determina si algun detalle tiene tarjetas ya recargadas (con codigo asignado)
	 * @return
	 */
	public boolean isTarjetasAnuladas(){
		int contadorAnuladas = 0;
		for(int i=0;i<this.getDetallesTransaccionBR().size();i++){
			DetalleTransaccionBR detalle = (DetalleTransaccionBR)this.getDetallesTransaccionBR().elementAt(i);
			if(detalle.getCodTarjeta()==null || 
					detalle.getCodTarjeta().equalsIgnoreCase("") || 
					detalle.getEstadoRegistro()==Sesion.DETALLE_BR_ELIMINADO ||
					this.getFechaTrans().before(Sesion.getFechaSistema())){
				contadorAnuladas++;
			}
		}
		return contadorAnuladas==this.getDetallesTransaccionBR().size();
	}


	public Vector<ComprobanteFiscal> getComprobantesFiscales() {
		return comprobantesFiscales;
	}


	public void setComprobantesFiscales(Vector<ComprobanteFiscal> comprobantesFiscales) {
		this.comprobantesFiscales = comprobantesFiscales;
	}


	public char getAccion() {
		return accion;
	}


	public void setAccion(char accion) {
		this.accion = accion;
	}


	public Cliente getClienteAnterior() {
		return clienteAnterior;
	}


	public void setClienteAnterior(Cliente clienteAnterior) {
		this.clienteAnterior = clienteAnterior;
	}


	public String getCodTarjetaPagoPromocion() {
		return codTarjetaPagoPromocion;
	}


	public void setCodTarjetaPagoPromocion(String codTarjetaPagoPromocion) {
		this.codTarjetaPagoPromocion = codTarjetaPagoPromocion;
	}
}
