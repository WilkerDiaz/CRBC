/**
 * =============================================================================
 * Proyecto   : CajaRegistradora
 * Paquete    : com.becoblohm.manejarservicio
 * Programa   : Apartado.java
 * Creado por : gmartinelli
 * Creado en  : 02/04/2004 09:33 AM
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.beco.cr.actualizadorPrecios.promociones.DonacionRegistrada;
import com.beco.cr.actualizadorPrecios.promociones.PromocionRegistrada;
import com.becoblohm.cr.CR;
import com.becoblohm.cr.InitCR;
import com.becoblohm.cr.excepciones.AbonoExcepcion;
import com.becoblohm.cr.excepciones.AfiliadoUsrExcepcion;
import com.becoblohm.cr.excepciones.AnulacionExcepcion;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ClienteExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.PagoExcepcion;
import com.becoblohm.cr.excepciones.ProductoExcepcion;
import com.becoblohm.cr.excepciones.SesionExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.gui.CantidadFraccionada;
import com.becoblohm.cr.gui.apartado.EtiquetasApartado;
import com.becoblohm.cr.manejadorauditoria.Auditoria;
import com.becoblohm.cr.manejadorsesion.MaquinaDeEstadoVenta;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.extensiones.ActualizadorPreciosSACFactory;
import com.becoblohm.cr.extensiones.ManejadorReportesFactory;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.manejarventa.CondicionVenta;
import com.becoblohm.cr.manejarventa.Producto;
import com.becoblohm.cr.manejarventa.Promocion;
import com.becoblohm.cr.mediadoresbd.BaseDeDatosServicio;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.utiles.MathUtil;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.crprinterdriver.CRFiscalPrinterOperations;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;

/** 
 * Descripción: 
 * 		Subclase de Servicio. Maneja los servicios de Apartados desde su estado inicial (Ingreso del primer
 * producto) hasta su finalizacion.
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
* Fecha: agosto 2011
*/
public class Apartado extends Servicio {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Apartado.class);
	
	private char condicionAbono;
	private Vector<Abono> abonos;
	private Abono abonoNuevo;
	private boolean apartadoExento = false;
	
	private int tiposDeAbonosInicial;
	private double[] porcentajesDeAbonos;
	private int tiempoVigencia;
	private String tipoVigencia;
	private boolean permiteRebajas = false;
	private boolean recalcularSaldo = false;
	private float cargoPorServicio = 0;
	private double mtoMinimoApartado = 0;
	private boolean preguntarFacturar = false;
    
	private Vector<DetalleServicio> detalleAnterior;
	private boolean apartadoRecalculado = false;
	public static Vector<DonacionRegistrada> donacionesRegistradas = new Vector<DonacionRegistrada>();
    public static Vector<PromocionRegistrada> promocionesRegistradas = new Vector<PromocionRegistrada>();
    public Vector<Pago> pagosEspeciales = new Vector<Pago>();
    
    //Modificado por apartados especiales
    private int tipoApartado;
    private Date fechaVencimiento;
    private String descripcionTipoApartado;
	
	/**
	 * Constructor que llama al constructor de la superclase (Servicio)
	 * y crea un Vector de abonos vacio.
	 */
	public Apartado() throws SesionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {		
		super(Sesion.getTienda().getNumero(), Sesion.APARTADO, Sesion.getFechaSistema(), Sesion.getHoraSistema(),
			Sesion.usuarioActivo.getNumFicha(), Sesion.APARTADO_ACTIVO);
		this.condicionAbono = ' ';
		this.abonos = new Vector<Abono>();
		this.abonoNuevo = null;

		Sesion.setUbicacion("APARTADO","crearApartado");
		Auditoria.registrarAuditoria("Creando Apartado para Tienda " + this.codTienda, 'T');
		
		//Se cargan las preferencias de este servicio
		this.cargarPreferenciasApartado();
		
		//Se actualiza el estado del apartado según su fecha de vencimiento
		this.tipoApartado=0;
		this.fechaVencimiento = this.verificarEstadoVencimiento();
	}
	
	/**
	 * Constructor de la clase Apartado para cargar el apartado especificado
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unchecked")
	public Apartado (int tda, String fecha, int numServicio) throws PagoExcepcion, SesionExcepcion, AfiliadoUsrExcepcion, BaseDeDatosExcepcion, ClienteExcepcion, ConexionExcepcion {
		super();
		
		this.cargarPreferenciasApartado();
		
		Sesion.setUbicacion("APARTADO","cargarApartado");
		Auditoria.registrarAuditoria("Cargando apartado numero " + numServicio + " de Tienda " + tda + " en Caja " + Sesion.getCaja().getNumero(), 'T');
		
		// Obtenemos la venta de la BaseDeDatos
		Vector<Object> servicioObtenido = BaseDeDatosServicio.obtenerApartado(tda, fecha, numServicio);
		Vector<Object> cabeceraServicio = (Vector<Object>)servicioObtenido.elementAt(0);
		Vector<Vector<Object>> detallesServicio = (Vector<Vector<Object>>)servicioObtenido.elementAt(1);
		//Caso agregado por módulo de promociones
		Vector<Vector<CondicionVenta>> condicionesVenta = (Vector<Vector<CondicionVenta>>)servicioObtenido.elementAt(2);
	
		// Armamos la cabecera de venta
		super.codTienda = tda;
		super.tipoServicio = ((String)cabeceraServicio.elementAt(1));
		super.numServicio = ((Integer)cabeceraServicio.elementAt(2)).intValue();
		super.fechaServicio = ((Date) cabeceraServicio.elementAt(3));
		super.cliente = ((String)cabeceraServicio.elementAt(4)) == null
					? new Cliente()
					: MediadorBD.obtenerCliente((String)cabeceraServicio.elementAt(4));
		super.montoBase = ((Double)cabeceraServicio.elementAt(5)).doubleValue();
		super.montoImpuesto = ((Double)cabeceraServicio.elementAt(6)).doubleValue();
		super.lineasFacturacion = ((Integer)cabeceraServicio.elementAt(7)).intValue();
		this.condicionAbono = ((String)cabeceraServicio.elementAt(8)) == null
							  ? ' '
							  : ((String)cabeceraServicio.elementAt(8)).toCharArray()[0];
		super.codCajero = ((String)cabeceraServicio.elementAt(9));
		super.numTransaccionVenta = ((Integer)cabeceraServicio.elementAt(10)).intValue();
		super.fechaTransVta = ((Date) cabeceraServicio.elementAt(11));
		super.horaInicia = (Time)cabeceraServicio.elementAt(12);
		super.horaFin = (Time)cabeceraServicio.elementAt(13);
		super.estadoServicio = ((String)cabeceraServicio.elementAt(14)).toCharArray()[0];
		this.tipoApartado=(((Integer)cabeceraServicio.elementAt(15)).intValue());
		this.fechaVencimiento=(((Date)cabeceraServicio.elementAt(16)));
		this.descripcionTipoApartado=(((String)cabeceraServicio.elementAt(17)));
		
		// Chequeamos que el cliente no sea el usuario activo
		if ((cliente.getNumFicha()!=null) && Sesion.getUsuarioActivo().getNumFicha().equalsIgnoreCase(cliente.getNumFicha()))
			throw new AfiliadoUsrExcepcion("El apartado posee como cliente al Usuario Activo.\nNo se pudo recuperar el apartado");
		
		// Armamos el detalle del Apartado
		for (int i=0; i<detallesServicio.size(); i++) {
			Vector<Object> detalleServicio = detallesServicio.elementAt(i);
			String codProd = (String)detalleServicio.elementAt(0);
			String codCondVenta = (String)detalleServicio.elementAt(1);
			float cantidad = ((Float)detalleServicio.elementAt(2)).floatValue();
			double precioRegular = ((Double)detalleServicio.elementAt(3)).doubleValue();
			double precioFinal = ((Double)detalleServicio.elementAt(4)).doubleValue();
			double mtoImpuesto = ((Double)detalleServicio.elementAt(5)).doubleValue();
			String tipoCap = (String)detalleServicio.elementAt(6);
			int codPromo = ((Integer)detalleServicio.elementAt(7)).intValue();
			
			try {
				//Actualizado por módulo de promociones
				DetalleServicio detalle = new DetalleServicio(codProd, tipoCap, cantidad, /*this.fechaServicio*/ new Date(), this.horaInicia, codPromo, precioRegular, precioFinal, mtoImpuesto, codCondVenta);
				try{
					detalle.setCondicionVentaPromociones(condicionesVenta.elementAt(i));
					CondicionVenta condicionVentaCombo = detalle.getPrimeraCondicion(Sesion.condicionesCombo);
					if(condicionVentaCombo!=null)
						detalle.setMontoDctoCombo(condicionVentaCombo.getMontoDctoCombo());
					Vector<String> condicionesCorporativas =  new Vector<String>();
					condicionesCorporativas.addElement(Sesion.condicionDescuentoEnProductos);
					CondicionVenta condicionCorporativa = detalle.getPrimeraCondicion(condicionesCorporativas);
					if(condicionCorporativa!=null)
						detalle.setMontoDctoCorporativo(MathUtil.roundDouble((detalle.getPrecioFinal()*100)/(100-condicionCorporativa.getPorcentajeDescuento())));
				} catch(ArrayIndexOutOfBoundsException ex){
					detalle.setCondicionesVentaPromociones(new Vector<CondicionVenta>());
				}
				detalle.setCondicionVenta(detalle.construirCondicionVentaString());
				this.detallesServicio.addElement(detalle);
				
			} catch (ProductoExcepcion e1) {
				logger.error("Apartado(int, String, int)", e1);

				throw (new BaseDeDatosExcepcion("Error. Existen productos en el apartado que no se encuentran en la Base De Datos"));
			} catch (BaseDeDatosExcepcion e1) {
				logger.error("Apartado(int, String, int)", e1);

				throw (new BaseDeDatosExcepcion("Error recuperando Apartado Número " + numServicio));
			}
		}
		
		// Actualizamos el monto del servicio
		actualizarMontoServ();
		
		// Ahora cargamos los abonos del apartado
		Vector<Abono> abonos = BaseDeDatosServicio.obtenerAbonos(tda, numServicio);
		this.abonos = abonos;
		
		//Se cargan las preferencias de este servicio
		 this.cargarPreferenciasApartado();
		
		//Se actualiza el estado del apartado según su fecha de vencimiento
		//Modificado para apartados especiales
		Date fechaVencimiento = this.verificarEstadoVencimiento();
		if(this.tipoApartado==0)
			this.fechaVencimiento = fechaVencimiento;
		
		//chequeamos si el apartado fue exento de impuesto
		if(this.montoImpuesto == 0 && this.getCliente().isExento()) {
			this.apartadoExento = true;
			//this.setApartadoExento(true);
		} else {
			this.apartadoExento = false;
			//this.setApartadoExento(false);
		}	

	}
	
	/**
	 * Constructor de la clase Apartado para cargar un apartado específico (Sólo cabecera)
	 */
	public Apartado (int tda, int numServ, Date fecha, String codCliente, double mtoBase, double mtoImpuesto,
					   int lineas, String cajero, int numTransVta, Date fechaTrans, Time horaInicia,
					   Time horaFin, char edoServ, Date fechaVence, int tipoApartado) throws AfiliadoUsrExcepcion, BaseDeDatosExcepcion, ClienteExcepcion, ConexionExcepcion, SesionExcepcion {
		super();
		
		// Armamos la cabecera del apartado
		super.codTienda = tda;
		super.tipoServicio = Sesion.APARTADO;
		super.numServicio = numServ;
		super.fechaServicio = fecha;
		super.cliente = ((codCliente == null)||(codCliente.trim().equals("")))
					? new Cliente()
					: MediadorBD.obtenerCliente(codCliente);
		super.montoBase = mtoBase;
		super.montoImpuesto = mtoImpuesto;
		super.lineasFacturacion = lineas;
		super.codCajero = cajero;
		super.numTransaccionVenta = numTransVta;
		super.fechaTransVta = fechaTrans;
		super.horaInicia = horaInicia;
		super.horaFin = horaFin;
		super.estadoServicio = edoServ;
		
		//Se cargan las preferencias de este servicio
		 this.cargarPreferenciasApartado();
		
		this.tipoApartado = tipoApartado;
		this.fechaVencimiento = fechaVence;
		
	}
	
	/**
	 * Ingresa nuevas lineas de productos. Lo busca desde la base de datos y lo agrega al servicio.
	 * @param codProd Codigo del producto a ingresar.
	 * @param tipoCaptura Tipo de captura del producto (Teclado, Escaner).
	 * @throws ProductoExcepcion Si el codigo del producto es invalido
	 * @throws BaseDeDatosExcepcion Si ocurre un error realizando la consulta a la Base de Datos
	 * @throws ConexionExcepcion Si ocurre un error de conexion con la base de datos local
	 */
	public void ingresarLineaProducto(String codProd, String tipoCaptura) throws ProductoExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("ingresarLineaProducto(String, String) - start");
		}

		Sesion.setUbicacion("APARTADO","ingresarProductoApartado");
		
		DetalleServicio detalle = new DetalleServicio(codProd, tipoCaptura, 1, this.fechaServicio, this.horaInicia);
		Producto prod = detalle.getProducto();
		//Se chequea si es necesaria ingresar una cantidad decimal.
		//requerimiento para que el ingresar línea producto no agregue cantidad 1 por defecto para el caso de cantidades fraccionadas
		if (prod.isIndicaFraccion()) {
			CantidadFraccionada cf = new CantidadFraccionada();
			MensajesVentanas.centrarVentanaDialogo(cf);
			detalle.setCantidad(cf.getCantidad());
		}
		detallesServicio.addElement(detalle);
		
		actualizarPreciosDetalle(prod);
		actualizarMontoServ();
		
		// Registramos la auditoría de esta función
		Auditoria.registrarAuditoria("Captura de Producto " + codProd + " por " + tipoCaptura + " para Apartado",'T');

		if (logger.isDebugEnabled()) {
			logger.debug("ingresarLineaProducto(String, String) - end");
		}
	}

	
	/**
	* Método actualizarPreciosDetalles.
	* 		Actuliza los precios de todoslos detalles contenidosen el vectordedetallesde transacción.
	* 		Con el boolean sabemos si se debe aplicar el descuento a COLABORADOR a los detalles de la venta.
	* @throws BaseDeDatosExcepcion - Si hay falla con la conexión a la BD
	* @throws MaquinaDeEstadoExcepcion - Si el estado es incorrecto
	* @throws AutorizacionExcpecion - Si no se pudo autorizar la función
	*/
	public void actualizarPreciosDetalle() {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarPreciosDetalle() - start");
		}

		// Para cada producto del detalle recalculamos el impuesto. 
		for(int i=0; i<detallesServicio.size(); i++){
			DetalleServicio detalle = (DetalleServicio)detallesServicio.elementAt(i);
			Producto prod = detalle.getProducto();
			((DetalleServicio)detallesServicio.elementAt(i)).setMontoImpuesto(this.calcularImpuesto(prod,detalle.getPrecioFinal()));
		}
		actualizarMontoServ();

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarPreciosDetalle() - end");
		}
	}

	/* (non-Javadoc)
	 * @see com.becoblohm.cr.manejarventa.Transaccion#anularProducto(long)
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Object> anularProducto(int renglon) throws ProductoExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("anularProducto(int) - start");
		}

		DetalleServicio linea = null;
		Vector<Object> result = new Vector<Object>();
		try {
			// Obtenemos la linea a cambiar la cantidad
			linea = (DetalleServicio)this.detallesServicio.elementAt(renglon);
		} catch (Exception ex) {
			logger.error("anularProducto(int)", ex);

			throw (new ProductoExcepcion("Error al eliminar producto, no existe renglon " + renglon + " en el Apartado", ex));
		}
		
		result.addElement(linea.getProducto().getDescripcionCorta());
		result.addElement(new Double(linea.getPrecioFinal() + linea.getMontoImpuesto()));

		Producto prod = linea.getProducto();
		linea.anularProducto();
		
		// Cambiamos la condición de venta original para que sea recalculada
		linea.setCondicionVenta(Sesion.condicionNormal);
		
		if (((DetalleServicio)detallesServicio.elementAt(renglon)).getCantidad() <= 0) {
			//Auditoria.registrarAuditoria("Anulado(s) " + (((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).getCantidad()+1) + " producto(s) " + linea.getProducto().getCodProducto() + " de la transaccion",'T');
			detallesServicio.removeElementAt(renglon);
		} /*else
			Auditoria.registrarAuditoria("Anulado(s) 1 producto(s) " + linea.getProducto().getCodProducto() + " de la transaccion",'T');
		*/
		actualizarPreciosDetalle(prod);
		actualizarMontoServ();

		if (logger.isDebugEnabled()) {
			logger.debug("anularProducto(int) - end");
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
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Object> anularProducto(int renglon, float cantidad) throws ProductoExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("anularProducto(int, float) - start");
		}

		DetalleServicio linea = null;
		Vector<Object> result = new Vector<Object>();
		
		Sesion.setUbicacion("APARTADO","anularProducto");
		
		try {
			// Obtenemos la linea a cambiar la cantidad
			linea = (DetalleServicio)detallesServicio.elementAt(renglon);
		} catch (Exception ex) {
			logger.error("anularProducto(int, float)", ex);

			throw (new ProductoExcepcion("Error al elimina producto, no existe renglon " + renglon + " en el Apartado", ex));
		}
		
		result.addElement(linea.getProducto().getDescripcionCorta());
		result.addElement(new Double(linea.getPrecioFinal() + linea.getMontoImpuesto()));
		
		// Verificamos si existen productos suficientes en el renglon
		if (linea.getCantidad() < cantidad)
			throw (new ProductoExcepcion("Error al eliminar producto. No existe(n) " + cantidad + " producto(s) en el renglon"));

		// Verificamos si el número es mayor a cero
		if (cantidad <= 0)
			throw (new ProductoExcepcion("El número debe ser un número mayor que cero (0)"));

		// Chequeamos si la nueva cantidad es fraccionada, y si el producto permite cantidades fraccionadas
		if ((!linea.getProducto().isIndicaFraccion())&&((cantidad % 1)!=0)) {
			throw (new ProductoExcepcion("Error al cambiar cantidad de producto.\nEl producto " + linea.getProducto().getCodProducto() + " no admite cantidades fraccionadas (Decimales)"));
		}
		
		Producto prod = linea.getProducto();
		linea.anularProducto(cantidad);
	
		// Cambiamos la condición de venta originalpara que sea recalculada
		linea.setCondicionVenta(Sesion.condicionNormal);
	
		if (((DetalleServicio)detallesServicio.elementAt(renglon)).getCantidad() <= 0 && !((DetalleServicio)detallesServicio.elementAt(renglon)).contieneAlgunaCondicion(Sesion.condicionesCombo)) {
			Auditoria.registrarAuditoria("Anulado(s) " + (((DetalleServicio)detallesServicio.elementAt(renglon)).getCantidad()+cantidad) + " producto(s) " + linea.getProducto().getCodProducto() + " del Apartado " + this.numServicio,'T');
			detallesServicio.removeElementAt(renglon);
		} else
			Auditoria.registrarAuditoria("Anulado(s) " + cantidad + " producto(s) " + linea.getProducto().getCodProducto() + " del Apartado " + this.numServicio,'T');
		actualizarPreciosDetalle(prod);
		actualizarMontoServ();

		if (logger.isDebugEnabled()) {
			logger.debug("anularProducto(int, float) - end");
		}
		return result;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Object> finalizarServicio() throws BaseDeDatosExcepcion, ConexionExcepcion {
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
		
		//Vector resultado = FacturaVenta.imprimirFactura(this);
		// Actualizamos el estado de la transaccion
		//BaseDeDatosVenta.actualizarEdoTransaccion(this.codTienda, this.fechaTrans, this.numCajaInicia, this.numRegCajaInicia, Sesion.FINALIZADA, true);
		
		/*return new Vector();//resultado;*/

		if (logger.isDebugEnabled()) {
			logger.debug("finalizarServicio() - end");
		}
		return null;
	}
	
	/**
	 * Método registrarApartado
	 * 
	 * @param tipoAbonoInicial
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 * @throws AbonoExcepcion
	 */
	public void registrarApartado(char tipoAbonoInicial) throws BaseDeDatosExcepcion, ConexionExcepcion, AbonoExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("registrarApartado(char) - start");
		}

		// Chequeamos si se ha realizado el abono inicial verificando el monto del mismo y que sea
		//mayor o igual al monto minimo del abono
		if (this.montoAbonos() < this.montoAbonoInicial()-0.01) {
			// No se ha realizado el abono inicial del apartado
			DecimalFormat df = new DecimalFormat("#.00");
			throw new AbonoExcepcion("No se ha realizado el abono inicial de " + df.format(this.montoAbonoInicial()));
		}
		
		// Registramos el servicio
		this.numServicio = Sesion.getCaja().getNuevoNumServicio(this.getTipoServicio());
		this.lineasFacturacion = this.detallesServicio.size();
		this.horaFin = Sesion.getHoraSistema();
		this.estadoServicio = (this.consultarMontoServ()>this.montoAbonos()) ? Sesion.APARTADO_ACTIVO : Sesion.APARTADO_FACTURADO;
		this.condicionAbono = tipoAbonoInicial;
		BaseDeDatosServicio.registrarApartado(this);
			
		// Mandamos a imprimir la factura pero antes registramos la auditoría de esta función
		Sesion.setUbicacion("APARTADO","registrarApartado");
		Auditoria.registrarAuditoria("Emitiendo Documento de Apartado Nro." + this.numServicio + " de Tienda " + this.codTienda,'T');
		
		// Se envía a imprimir el comprobante de apartado
		ManejadorReportesFactory.getInstance().imprimirComprobanteDeApartado(this);
		
		//Se muestra la pantalla que solicita el nro de etiquetas  de apartado que se desean imprimir
		//Cambio jperez 17-04-2012, se elimina la ventana de etiquetas de apartado
		/*EtiquetasApartado ea = new EtiquetasApartado();
		MensajesVentanas.centrarVentanaDialogo(ea);
		int nroEtiquetas = ea.getNroEtiquetas();
		if(nroEtiquetas > 0) {
			//Se envían a imprimir las etiquetas del apartado
			 try{
				 ManejadorReportesFactory.getInstance().imprimirEtiquetasApartado(this, nroEtiquetas);
			 } catch(Exception ex){
				logger.error("registrarApartado(char)", ex);

				 MensajesVentanas.mensajeError("Mensaje Temporal --> Falló Impresión");
			 }
		}*/

		if (logger.isDebugEnabled()) {
			logger.debug("registrarApartado(char) - end");
		}
	}

	/**
	 * Método registrarAbonosApartado
	 * 
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 * @throws AbonoExcepcion
	 */
	public void registrarAbonosApartado() throws BaseDeDatosExcepcion, ConexionExcepcion, AbonoExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("registrarAbonosApartado() - start");
		}

		Sesion.setUbicacion("APARTADO","registrarAbonosApartado");
		
		// Chequeamos si posee el abono inicial verificando el monto del mismo y que sea
		//mayor o igual al monto minimo del abono
		if (this.montoAbonos() < this.montoAbonoInicial()-0.01) {
			// No se ha realizado el abono inicial del apartado
			DecimalFormat df = new DecimalFormat("#.00");
			throw new AbonoExcepcion("No se ha realizado el abono inicial de " + df.format(this.montoAbonoInicial()));
		}
		
		// Mandamos a imprimir la factura pero antes registramos la auditoría de esta función
		Auditoria.registrarAuditoria("Registrando Abonos de Apartado " + this.numServicio + " de Tienda " + this.codTienda,'T');
		
		// Registramos el servicio
		BaseDeDatosServicio.registrarAbonosApartado(this);
			
		// Mandamos a imprimir la factura pero antes registramos la auditoría de esta función
		//Vector resultado = FacturaVenta.imprimirFactura(this);
		// Actualizamos el estado de la transaccion
		//BaseDeDatosVenta.actualizarEdoTransaccion(this.codTienda, this.fechaTrans, this.numCajaInicia, this.numRegCajaInicia, Sesion.FINALIZADA, true);
			
		//return new Vector();//resultado;
		//return null;

		if (logger.isDebugEnabled()) {
			logger.debug("registrarAbonosApartado() - end");
		}
	}

	/**
	 * Método calcularImpuesto.
	 * 		Calcula el impuesto aplicable al precio del producto. Verifica si el cliente 
	 * 		de la venta es un cliente exento y si es asd evuelve 0.0
	 * @param prod Producto al que se le va a aplicar el impuesto
	 * @param pFinal double que representa el precio final sobre el cual se va hacer el calculo del impuesto. 
	 * 		  Ejm. Con promociones, descto a COLABORADOR, etc.
	 * @throws
	 * 
	 *  NOTA jgraterol: Cambiado por módulo de promociones
	 */
	public double calcularImpuesto(Producto prod, double pFinal) {
		if (logger.isDebugEnabled()) {
			logger.debug("calcularImpuesto(Producto, double) - start");
		}

		double pImpuesto = 0.0;
		
		//Verificamos si en la venta el cliente activo es un cliente exento
		if(!this.apartadoExento) {
			// Calculamos el impuesto
			pImpuesto = MathUtil.stableMultiply(MathUtil.roundDouble(pFinal), ((prod.getImpuesto().getPorcentaje()) / 100));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("calcularImpuesto(Producto, double) - end");
		}
		return pImpuesto;
	}
		
	/**
	 * Método montoAbonoInicial.
	 * 		Calcula el monto del abono inicial
	 * @return double - El monto del abono inicial
	 */
	public double montoAbonoInicial() {
		if (logger.isDebugEnabled()) {
			logger.debug("montoAbonoInicial() - start");
		}

		double returndouble = this.consultarMontoServ()
				* (Sesion.PORC_MINIMO_ABONO / 100);
		if (logger.isDebugEnabled()) {
			logger.debug("montoAbonoInicial() - end");
		}
		return returndouble;
	}
		
	/**
	 * Método montoAbonos.
	 * 		Calcula el monto realizado por abonos
	 * @return double - El monto abonado al apartado
	 */
	public double montoAbonos() {
		if (logger.isDebugEnabled()) {
			logger.debug("montoAbonos() - start");
		}

		double montoAbonado = 0.0;
		for (int i=0; i<this.abonos.size(); i++) {
			if (((Abono)this.abonos.elementAt(i)).getEstadoAbono()==Sesion.ABONO_ACTIVO)
				montoAbonado += ((Abono)this.abonos.elementAt(i)).getMontoBase()/* - ((Abono)this.abonos.elementAt(i)).getMontoRem())*/;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("montoAbonos() - end");
		}
		return montoAbonado;
	}

	/**
	 * Método getCondicionAbono
	 * 
	 * @return char
	 */
	public char getCondicionAbono() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCondicionAbono() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getCondicionAbono() - end");
		}
		return condicionAbono;
	}

	/**
	 * Método getAbonos
	 * 
	 * @return Vector
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Abono> getAbonos() {
		if (logger.isDebugEnabled()) {
			logger.debug("getAbonos() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getAbonos() - end");
		}
		return abonos;
	}
	
	/**
	 * Método obtenerNumAbono
	 * 
	 * @return int
	 */
	private int obtenerNumAbono() {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerNumAbono() - start");
		}

		int returnint = (this.abonos.size() + 1);
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerNumAbono() - end");
		}
		return returnint;
	}

	/**
	 * Método realizarPagoAbono
	 * 
	 * @param tda
	 * @param numCaja
	 * @param fechaA
	 * @param horaI
	 * @param tipoAbono
	 * @param codCajero
	 * @param pago
	 */
	/*public void realizarPagoAbono(int tda, int numCaja, Date fechaA, Time horaI, char tipoAbono, String codCajero, String codFPago, double mto) throws PagoExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (abonoNuevo == null) {
			abonoNuevo = new Abono(tda, numCaja, obtenerNumAbono(), fechaA, horaI, tipoAbono, codCajero);
		}
		// Vemos si existe un pago con esa forma de Pago
		boolean existe = false;
		for (int i=0; i<abonoNuevo.getPagos().size(); i++) {
			if (((Pago)abonoNuevo.getPagos().elementAt(i)).getCodFormaPago().equalsIgnoreCase(codFPago)) {
				existe = true;
				((Pago)abonoNuevo.getPagos().elementAt(i)).actualizarMonto(mto);
				break;
			}
		}
		if (!existe) {
			Pago p = new Pago(codFPago, null, mto);
			this.abonoNuevo.getPagos().addElement(p);
		}
		
		this.abonoNuevo.incrementarMontoAbonado(mto);
	}
	*/
	public void realizarPagoAbono(int tda, int numCaja, Date fechaA, Time horaI, char tipoAbono, String codCajero, Pago pago) {
		if (logger.isDebugEnabled()) {
			logger
					.debug("realizarPagoAbono(int, int, Date, Time, char, String, Pago) - start");
		}

		if (abonoNuevo == null) {
			abonoNuevo = new Abono(tda, numCaja, obtenerNumAbono(), fechaA, horaI, tipoAbono, codCajero);
		}
		this.abonoNuevo.getPagos().addElement(pago);
		
		this.abonoNuevo.incrementarMontoAbonado(pago.getMonto());

		if (logger.isDebugEnabled()) {
			logger
					.debug("realizarPagoAbono(int, int, Date, Time, char, String, Pago) - end");
		}
	}

	/**
	 * Método finalizarAbono
	 * 
	 * @param horaFinal
	 * @param vuelto
	 * @param imprimir
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	public void finalizarAbono(Time horaFinal, double vuelto, boolean imprimir) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("finalizarAbono(Time, double, boolean) - start");
		}
		
		boolean incrementarLimiteEntrega = true;
		Sesion.setUbicacion("APARTADO","abonar");
		if ((abonoNuevo!=null)&&(abonoNuevo.getPagos().size()>0)) {
			Auditoria.registrarAuditoria("Realizando Abono sobre Apartado " + this.numServicio,'T');
			//Actualizado por CENTROBECO, módulo de promociones
			double montoPagosPromociones = 0;
			for(int i=0;i<this.pagosEspeciales.size();i++){
				Pago pago = (Pago)this.pagosEspeciales.elementAt(i);
				abonoNuevo.getPagos().addElement(pago);
				montoPagosPromociones+=pago.getMonto();
			}
			abonoNuevo.setMontoBase(abonoNuevo.getMontoBase()+montoPagosPromociones);
			
			// Calculamos el monto total abonado al apartado hasta la fecha contando el nuevo abono
			abonoNuevo.setMontoBase(abonoNuevo.getMontoBase()-vuelto);
			double montoAbonado = MathUtil.roundDouble(abonoNuevo.getMontoBase() + this.montoAbonos());
			abonoNuevo.setMontoVuelto(vuelto);
			abonoNuevo.setHoraFinaliza(horaFinal);
			//abonos.addElement(abonoNuevo);
			
			//Actualización BECO: Módulo de promociones
			/*double montoPagosPromociones = 0;
			for(int i=0;i<this.pagosEspeciales.size();i++){
				Pago pago = (Pago)this.pagosEspeciales.elementAt(i);
				montoPagosPromociones +=pago.getMonto();
				
			}*/
			
			if (montoAbonado/*+montoPagosPromociones*/ >= this.consultarMontoServ()-0.01) {
				this.estadoServicio = Sesion.APARTADO_PAGADO;
				BaseDeDatosServicio.actualizarEdoServicio(this.codTienda, this.tipoServicio, this.numServicio, this.fechaServicio, this.estadoServicio, this.fechaVencimiento, false);
				
				if (this.preguntarFacturar) {
					//Agregamos el último abono del apartado el vector de abonos
					abonos.addElement(abonoNuevo);
					if (MensajesVentanas.preguntarSiNo(" Se ha completado el monto del Apartado.\n Desea Facturar el Mismo?")==0) {
						// Si el monto del abono completa el monto del apartado creamos la venta.
						
						CR.meVenta.crearVentaApartado(this,montoAbonado,vuelto);
						//Cambiamos el estado del apartado a facturado
						this.estadoServicio = Sesion.APARTADO_FACTURADO;
						BaseDeDatosServicio.actualizarEdoServicio(this.codTienda, this.tipoServicio, this.numServicio, this.fechaServicio, this.estadoServicio, this.fechaVencimiento,false);
						imprimir = false; //No enviamos a imprimir el comprobante del abono porque al facturarlo se genera es la factura del apartado completo
						incrementarLimiteEntrega = false;
					}
				} else {
					//Agregamos el último abono del apartado el vector de abonos y le cambiamos el estado a "Abono Final"
					abonoNuevo.setTipoTransAbono(Sesion.ABONO_FINAL);
					abonos.addElement(abonoNuevo);

					// TODO Código agregado para detectar problema con Apartados
					try {
						// Si el monto del abono completa el monto del apartado creamos la venta.
						CR.meVenta.crearVentaApartado(this,montoAbonado,vuelto);
						//Cambiamos el estado del apartado a facturado
						this.estadoServicio = Sesion.APARTADO_FACTURADO;
						BaseDeDatosServicio.actualizarEdoServicio(this.codTienda, this.tipoServicio, this.numServicio, this.fechaServicio, this.estadoServicio, this.fechaVencimiento, false);
					} catch (Throwable e) {
						MensajesVentanas.mensajeError("Problema finalizando el apartado\n Intente nuevamente o notifique a Sistemas\n NO REINICIE");
						this.estadoServicio = Sesion.APARTADO_ACTIVO;
						BaseDeDatosServicio.actualizarEdoServicio(this.codTienda, this.tipoServicio, this.numServicio, this.fechaServicio, this.estadoServicio, this.fechaVencimiento, false);
						abonos.remove(abonos.size()-1);
						logger.error("Apartado en C", e);
						logger.fatal("Finalizar Apartado", e);
						e.printStackTrace();
						throw new ExcepcionCr(e.getMessage());
					}
					
					imprimir = false; //No enviamos a imprimir el comprobante del abono porque al facturarlo se genera es la factura del apartado completo
					incrementarLimiteEntrega = false;				
				}
			} else {
				abonos.addElement(abonoNuevo); //Se grega el abono realizado que no es el último
			}

			if (incrementarLimiteEntrega) {		
				// Calculamos los montos que incrementen el valor recaudado en la caja.
				double mtoRecaudado = -abonoNuevo.getMontoVuelto();
				for (int i=0; i<abonoNuevo.getPagos().size(); i++) {
					Pago pagoAct = (Pago)abonoNuevo.getPagos().elementAt(i);
					if (pagoAct.getFormaPago().isIncrementaEntregaParcial())
						mtoRecaudado += pagoAct.getMonto(); 
				}
				if (mtoRecaudado > 0) {
					// Incrementamos el monto recaudado de la caja por concepto de manejo de dinero de abonos
					Sesion.getCaja().incrementarMontoRecaudado(mtoRecaudado);
				} else {
					if (mtoRecaudado < 0) {
						// Disminuimos el monto recaudado de la caja por concepto de manejo de dinero de abonos
						Sesion.getCaja().disminuirMontoRecaudado(-mtoRecaudado);
					}
				}
			}
			while ((CR.meVenta.getTransaccionPorImprimir()!=null || 
					CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
					//Agregado por jgraterol: 28/09/2009
					MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()) && imprimir) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Bloque catch generado automáticamente
					e.printStackTrace();
				}
				if(MaquinaDeEstadoVenta.errorAtencionUsuario){
					MensajesVentanas.aviso("Hay un documento en curso \nEspere un momento e intente nuevamente");
				}
			}
			
			if (imprimir) {
				try{
					ManejadorReportesFactory.getInstance().imprimirComprobanteAbono(this);
				} catch(Exception ex){
					logger.error("finalizarAbono(Time, double, boolean)", ex);

					MensajesVentanas.mensajeError("Mensaje Temporal --> Falló Impresión");
				}
			}
			
			abonoNuevo = null;
		} else
			throw (new AbonoExcepcion("No se han realizado abonos nuevos"));

		if (logger.isDebugEnabled()) {
			logger.debug("finalizarAbono(Time, double, boolean) - end");
		}
	}
	
	/**
	 * Método isApartadoExento
	 * 
	 * @return boolean
	 */
	public boolean isApartadoExento() {
		if (logger.isDebugEnabled()) {
			logger.debug("isApartadoExento() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isApartadoExento() - end");
		}
		return apartadoExento;
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
		for (int i=0; i<this.detallesServicio.size();i++){
			cantTotal +=((DetalleServicio) this.getDetallesServicio().elementAt(i)).getCantidad();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCantidadProds() - end");
		}
		return cantTotal;
	}

	/**
	 * Método anularApartadoActivo
	 * 
	 */
	public void anularApartadoActivo() {
		if (logger.isDebugEnabled()) {
			logger.debug("anularApartadoActivo() - start");
		}

		// Registramos la auditoría de esta función
		Sesion.setUbicacion("APARTADO","anularApartadoActivo");
		Auditoria.registrarAuditoria("Anulado Apartado Activo de Tienda " + this.codTienda,'T');

		if (logger.isDebugEnabled()) {
			logger.debug("anularApartadoActivo() - end");
		}
	}

	/**
	 * Aplica el descuento por articulo defectuoso a un producto.
	 * @param renglon Posicion del producto al que se le queire cambiar el precio.
	 * @param descto Descuento a aplicar.
	 * @param esPorcentaje Indicador de si es un precio o un porcentaje de descuento.
	 * @throws ProductoExcepcion Si el producto no se encuentra en el detalle.
	 */
	public void aplicarDesctoPorDefecto(int renglon, double descto, float cantidad, boolean esPorcentaje, String codAutorizante) throws ProductoExcepcion{
		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarDesctoPorDefecto(int, double, float, boolean, String) - start");
		}

	DetalleServicio linea = (DetalleServicio) this.getDetallesServicio().elementAt(renglon);
	//Vector paraDescuento = new Vector(1);
	double impto = 0.0;
	DecimalFormat df2 = new DecimalFormat("#,##0.00");
		
	Sesion.setUbicacion("APARTADO","aplicarDesctoPorDefecto");
	
	//Obtenemos el producto
	Producto prod = linea.getProducto();
		
	if (esPorcentaje) {			
		// Se calcula el nuevo precio del detalle

		double mtoDcto = prod.getPrecioRegular() * (descto / 100);
		double pFinal = prod.getPrecioRegular() - mtoDcto;
		pFinal = MathUtil.roundDouble(pFinal);
			
		// Calculamos el impuesto
		impto = this.calcularImpuesto(prod, pFinal);
			
		// Creamos el nuevo detalle con el producto y su nuevo precio y lo agregamos al vector de detalles
		DetalleServicio detalleNuevo = new DetalleServicio(linea.getProducto(), cantidad, Sesion.condicionDesctoPorDefecto, codAutorizante, pFinal, impto, linea.getTipoCaptura(), -1, linea.getTipoEntrega());
		detallesServicio.addElement(detalleNuevo);

		//Enviamos al visor la información del descuento aplicado
		String precio = df2.format(pFinal+impto);
		String cantXprecio = Sesion.condicionDesctoPorDefecto + " " + df2.format(cantidad) + " X " +  precio;
		try { CR.crVisor.enviarString(((Producto)linea.getProducto()).getDescripcionCorta(), 0, cantXprecio, 2); }
		catch(Exception e){
			logger
					.error(
							"aplicarDesctoPorDefecto(int, double, float, boolean, String)",
							e);
}

		// Registramos la auditoría de esta función
		Sesion.setUbicacion("APARTADO","aplicarDesctoPorDefecto");
		Auditoria.registrarAuditoria("Rebaja Precio a Precio en Caja " + Sesion.getNumCaja() + ", Tienda " + Sesion.getNumTienda() + ", Prod. " + prod.getCodProducto() + ", P.Final. " + descto,'T');
		
		// Se elimina el producto del detalle original
		this.anularProducto(renglon,cantidad);
	} else {
		// Calculamos el impuesto
		descto = MathUtil.roundDouble(descto);
		
		impto = this.calcularImpuesto(prod, descto);
			
		// Creamos el nuevo detalle con el producto y su nuevo precio y lo agregamos al vector de detalles
		DetalleServicio detalleNuevo = new DetalleServicio(linea.getProducto(), cantidad, Sesion.condicionCambioPrecio, codAutorizante, descto, impto, linea.getTipoCaptura(), -1, linea.getTipoEntrega());
		detallesServicio.addElement(detalleNuevo);

		//Enviamos al visor la información del descuento aplicado
		String precio = df2.format(descto+impto);
		String cantXprecio = Sesion.condicionCambioPrecio + " " + df2.format(cantidad) + " X " +  precio;
		try { CR.crVisor.enviarString(((Producto)linea.getProducto()).getDescripcionCorta(), 0, cantXprecio, 2); }
		catch (Exception e) {
			logger
					.error(
							"aplicarDesctoPorDefecto(int, double, float, boolean, String)",
							e);
}
				
		// Registramos la auditoría de esta función
		Sesion.setUbicacion("APARTADO","aplicarDesctoPorDefecto");
		Auditoria.registrarAuditoria("Rebaja Precio a Precio en Caja " + Sesion.getNumCaja() + ", Tienda " + Sesion.getNumTienda() + ", Prod. " + prod.getCodProducto() + ", P.Final. " + descto,'T');
			
		// Se elimina el producto del detalle original
		this.anularProducto(renglon,cantidad);
	}
		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarDesctoPorDefecto(int, double, float, boolean, String) - end");
		}
}
	/**
	 * Metodo que cambia la cantidad de un producto
	 * @param nvaCantidad Nueva cantidad a asignar
	 * @param renglon Renglon donde se encuentra el producto. Si existen menos productos en el renglon que el
	 *		especificado en nvaCantidad, se eliminan de otros renglones con el mismo producto. 		
	 * @throws ExcepcionCr Si no existe el renglón especificado o el producto no admite cantidades fraccionadas
	 * 		y se coloca una cantidad fraccionada como parametro nvaCantidad. Si ocurre un error de resitro de auditoria.
	 * 		Si ocurre un error al obtener metodo, modulo y funcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void agregarCantidad(float nvaCantidad, int renglon) throws ProductoExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("agregarCantidad(float, int) - start");
		}

		Sesion.setUbicacion("APARTADO", "cambiarCantidad");
		Auditoria.registrarAuditoria("Cambiando cantidad de Renglon " + renglon + ". Agregado(s) " + nvaCantidad + " productos",'T');
		
		Producto pCambiar;
		float cantidadRenglon;
		
		// Observamos si el renglon del producto existe
		try {
			// Obtenemos el Producto a cambiar la cantidad
			pCambiar = ((DetalleServicio)detallesServicio.elementAt(renglon)).getProducto();
		} catch (Exception ex) {
			logger.error("agregarCantidad(float, int)", ex);

			throw (new ProductoExcepcion("Error al cambiar cantidad de producto, no existe renglon " + renglon + " en el Apartado", ex));
		}
		
		// Chequeamos  si la cantidad es fraccionada, y si el producto permite cantidades fraccionadas
		if ((!pCambiar.isIndicaFraccion())&&((nvaCantidad % 1)!=0)) {
			throw (new ProductoExcepcion("Error al cambiar cantidad de producto.\nEl producto " + pCambiar.getCodProducto() + " no admite cantidades fraccionadas (Decimales)"));
		}
		
		cantidadRenglon = ((DetalleServicio)detallesServicio.elementAt(renglon)).getCantidad();
		if (nvaCantidad > 0) nvaCantidad = cantidadRenglon + nvaCantidad;
		else throw (new ProductoExcepcion("Error al cambiar cantidades de productos. No se pueden agregar cantidades negativas"));
		((DetalleServicio)detallesServicio.elementAt(renglon)).setCantidad(nvaCantidad);
		if(!((DetalleServicio)detallesServicio.elementAt(renglon)).contieneAlgunaCondicion(Sesion.condicionesCombo)){
			((DetalleServicio)detallesServicio.elementAt(renglon)).setCondicionVenta(Sesion.condicionNormal);
			// Inicializamos la varable que depende de la condicíon de venta (en esta caso es el de la condición especial del detalle)
			((DetalleServicio)detallesServicio.elementAt(renglon)).setCondicionEspecial(false);
			((DetalleServicio)detallesServicio.elementAt(renglon)).setCondicionesVentaPromociones(new Vector<CondicionVenta>());
			((DetalleServicio)detallesServicio.elementAt(renglon)).setPrecioFinal(((DetalleServicio)detallesServicio.elementAt(renglon)).getProducto().getPrecioRegular());
		}
						
		actualizarPreciosDetalle(pCambiar);
		actualizarMontoServ();

		if (logger.isDebugEnabled()) {
			logger.debug("agregarCantidad(float, int) - end");
		}
	}
	
	/**
	 * Método cancelarAbonoActivo
	 * 		Su función es cancelar el abono activo cuando no ha sido finalizado
	 */
	public void cancelarAbonoActivo() {
		if (logger.isDebugEnabled()) {
			logger.debug("cancelarAbonoActivo() - start");
		}

		this.abonoNuevo = null;

		if (logger.isDebugEnabled()) {
			logger.debug("cancelarAbonoActivo() - end");
		}
	}

	/**
	 * Método getAbonoNuevo
	 * 
	 * @return Abono
	 */
	public Abono getAbonoNuevo() {
		if (logger.isDebugEnabled()) {
			logger.debug("getAbonoNuevo() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getAbonoNuevo() - end");
		}
		return abonoNuevo;
	}

	/**
	 * @param b
	 */
	public void setApartadoExento(boolean b) {
		if (logger.isDebugEnabled()) {
			logger.debug("setApartadoExento(boolean) - start");
		}

		apartadoExento = b;
		// actualizamos los detalles de transaccion para calcular los impuestos
		this.actualizarPreciosDetalle();

		if (logger.isDebugEnabled()) {
			logger.debug("setApartadoExento(boolean) - end");
		}
	}

	/**
	 * Método anularAbono
	 * 	Su función es la de anular el abono especificado del apartado activo cambiandole
	 * el estado de 'Activo' a 'Anulado'
	 * @param renglonAbono
	 */
	public void anularAbono(int renglonAbono) throws AbonoExcepcion, BaseDeDatosExcepcion, ConexionExcepcion{
		if (logger.isDebugEnabled()) {
			logger.debug("anularAbono(int) - start");
		}

		// Se verifican las condiciones de anulación de abonos antes de regsitrarlo
		
		Sesion.setUbicacion("APARTADO","anularAbono");
		// Chequeamos condicion de mismo dia
		String fechaAbono = (new SimpleDateFormat("dd-MM-yyyy")).format(((Abono)this.getAbonos().elementAt(renglonAbono)).getFechaAbono());
		String fechaSistema = (new SimpleDateFormat("dd-MM-yyyy")).format(Sesion.getFechaSistema());
		if (!fechaAbono.equalsIgnoreCase(fechaSistema)) {
			throw new AbonoExcepcion("La fecha del abono difiere de la actual. El abono fue realizado el " + ((Abono)this.getAbonos().elementAt(renglonAbono)).getFechaAbono());
		}

		// Chequemos condicion de misma caja
		if (((Abono)this.getAbonos().elementAt(renglonAbono)).getCaja()!= Sesion.getCaja().getNumero())
			throw new AbonoExcepcion("El abono debe ser anulado en la Caja " + ((Abono)this.getAbonos().elementAt(renglonAbono)).getCaja());

		// Chequeamos condicion de mismo cajero
		if (!((Abono)this.getAbonos().elementAt(renglonAbono)).getCodCajero().equalsIgnoreCase(Sesion.usuarioActivo.getNumFicha()))
			throw new AbonoExcepcion("El abono debe ser anulado por el Cajero " + ((Abono)this.getAbonos().elementAt(renglonAbono)).getCodCajero());

		Auditoria.registrarAuditoria("Anulando Abono numero " + ((Abono)this.abonos.elementAt(renglonAbono)).getNumAbono() + " de Apartado " + this.numServicio,'T');
		BaseDeDatosServicio.actualizarEdoAbono(this.getNumServicio(), ((Abono)this.abonos.elementAt(renglonAbono)), Sesion.ABONO_ANULADO);
		((Abono)this.abonos.elementAt(renglonAbono)).setEstadoAbono(Sesion.ABONO_ANULADO);
		
		try{
			ManejadorReportesFactory.getInstance().imprimirAnulacionDeAbono(this,renglonAbono);
		} catch(Exception ex){
			logger.error("anularAbono(int)", ex);

			MensajesVentanas.mensajeError("Mensaje Temporal --> Falló Impresión");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("anularAbono(int) - end");
		}
	}
	
	/**
	 * Método anularApartado
	 * 	Su función es la de anular el apartado activo cambiandole
	 * el estado de 'Activo'/'Vencido' a 'Anulado'
	 */
	public void anularApartado() throws BaseDeDatosExcepcion, ConexionExcepcion, AnulacionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("anularApartado() - start");
		}

		Sesion.setUbicacion("APARTADO","anularApartado");
		//if (this.getEstadoServicio()==Sesion.APARTADO_ACTIVO || this.getEstadoServicio()==Sesion.APARTADO_VENCIDO) {
			Auditoria.registrarAuditoria("Anulando Apartado nro. " + this.numServicio + " de tienda " + this.codTienda,'T');
			//Se toma el total de abonos "ACTIVOS" antes de anularlos para pasarlo como parámetro a la impresion para
			//saber el total de abonos realizados
			double totalAbonosActivos = this.montoAbonos();
             
			BaseDeDatosServicio.anularApartado(this);
			for (int i=0; i< this.getAbonos().size(); i++) {
				((Abono)this.getAbonos().elementAt(i)).setEstadoAbono(Sesion.ABONO_ANULADO);
			}
                
			//Se manda a imprimir el comprobante de anulación de apartado
			try{
				ManejadorReportesFactory.getInstance().imprimirComprobanteAnulacionApartado(this, totalAbonosActivos);
			} catch(Exception ex){
				logger.error("anularApartado()", ex);

				MensajesVentanas.mensajeError("Mensaje Temporal --> Falló Impresión");
			}
			Auditoria.registrarAuditoria("Emitiendo Comprobante de Anulación de Apartado nro. " + this.getNumServicio() + " en Caja " + Sesion.getCaja().getNumero(),'T');
		/*} else {
			  throw new AnulacionExcepcion("El Apartado no puede ser anulado\nSu estado no permite anulaciones");
		}*/

		if (logger.isDebugEnabled()) {
			logger.debug("anularApartado() - end");
		}
	}

	/**
	 * Método iniciarAbonoNuevo
	 * 
	 * @param tda
	 * @param numCaja
	 * @param fechaA
	 * @param horaI
	 * @param tipoAbono
	 * @param codCajero
	 */
	public void iniciarAbonoNuevo(int tda, int numCaja, Date fechaA, Time horaI, char tipoAbono, String codCajero) {
		if (logger.isDebugEnabled()) {
			logger
					.debug("iniciarAbonoNuevo(int, int, Date, Time, char, String) - start");
		}

		if (abonoNuevo == null) {
			abonoNuevo = new Abono(tda, numCaja, obtenerNumAbono(), fechaA, horaI, tipoAbono, codCajero);
		}

		if (logger.isDebugEnabled()) {
			logger
					.debug("iniciarAbonoNuevo(int, int, Date, Time, char, String) - end");
		}
	}
	
	/**
	 * Método cargarPreferenciasApartado
	 * 
	 * @throws SesionExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'LinkedHashMap'
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unchecked")
	private void cargarPreferenciasApartado() throws SesionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarPreferenciasApartado() - start");
		}

		LinkedHashMap<String,Object> preferencias = new LinkedHashMap<String,Object>();
			
		try {
			String actual;
			preferencias = InitCR.preferenciasCR.getAllPreferecesForNode("apartado");
			actual = preferencias.get("NumeroTiposDeAbonos").toString();
			this.tiposDeAbonosInicial = Integer.valueOf(actual).intValue();
			this.porcentajesDeAbonos = new double[tiposDeAbonosInicial+1];
			for (int i=0; i<tiposDeAbonosInicial; i++) {
				actual = preferencias.get("Abono" + (i+1)).toString();
				this.porcentajesDeAbonos[i] = Double.valueOf(actual).doubleValue();
			}
			tiempoVigencia = Integer.valueOf(preferencias.get("TiempoVigencia").toString()).intValue();
			tipoVigencia = preferencias.get("TipoVigencia").toString();
			permiteRebajas = (preferencias.get("PermiteRebajas").toString().toCharArray()[0] == Sesion.SI) ? true : false;
			recalcularSaldo = (preferencias.get("RecalcularSaldo").toString().toCharArray()[0] == Sesion.SI) ? true : false;
			cargoPorServicio = Integer.valueOf(preferencias.get("CargoPorServicio").toString()).intValue();
			mtoMinimoApartado = Double.valueOf(preferencias.get("MontoMinimoApartado").toString()).doubleValue();
			preguntarFacturar = (preferencias.get("PreguntarFacturar").toString().toCharArray()[0] == Sesion.SI) ? true : false;
		} catch (NoSuchNodeException e) {
			logger.error("cargarPreferenciasApartado()", e);

			throw new SesionExcepcion("Falla en la carga de preferencias de Abono Inicial de Apartado.\nNo se pueden realizar Apartados", e);
		} catch (UnidentifiedPreferenceException e) {
			logger.error("cargarPreferenciasApartado()", e);

			throw new SesionExcepcion("Falla en la carga de las preferencias de Apartados.\nNo se pueden realizar Apartados", e);
		} catch (Exception e) {
			logger.error("cargarPreferenciasApartado()", e);

			throw new SesionExcepcion("Falla en la carga de las preferencias de Apartados.\nNo se pueden realizar Apartados", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cargarPreferenciasApartado() - end");
		}
	}
	/**
	 * Método getPorcentajesDeAbonos
	 * 
	 * @return double[]
	 */
	public double[] getPorcentajesDeAbonos() {
		if (logger.isDebugEnabled()) {
			logger.debug("getPorcentajesDeAbonos() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getPorcentajesDeAbonos() - end");
		}
		return porcentajesDeAbonos;
	}

	/**
	 * Método getTiposDeAbonosInicial
	 * 
	 * @return int
	 */
	public int getTiposDeAbonosInicial() {
		if (logger.isDebugEnabled()) {
			logger.debug("getTiposDeAbonosInicial() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getTiposDeAbonosInicial() - end");
		}
		return tiposDeAbonosInicial;
	}

	/**
	 * Método getTiempoVigenciaDias
	 * 
	 * @return int
	 */
	public int getTiempoVigencia() {
		if (logger.isDebugEnabled()) {
			logger.debug("getTiempoVigenciaDias() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getTiempoVigenciaDias() - end");
		}
		return tiempoVigencia;
	}

	/**
	 * Método isPermiteRebajas
	 * 
	 * @return
	 * boolean
	 */
	public boolean isPermiteRebajas() {
		if (logger.isDebugEnabled()) {
			logger.debug("isPermiteRebajas() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isPermiteRebajas() - end");
		}
		return permiteRebajas;
	}

	/**
	 * Método recalculadoPromocionesApartado
	 * 
	 * @return
	 * boolean
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public boolean recalculadoPromocionesApartado() {
		if (logger.isDebugEnabled()) {
			logger.debug("recalculadoPromocionesApartado() - start");
		}

		boolean recalculado = false;
		double montoImpuesto;

		this.detalleAnterior = new Vector<DetalleServicio>();//(Vector)detallesServicio.clone();
		for (int i=0; i<detallesServicio.size(); i++) {
			DetalleServicio detalle = (DetalleServicio)detallesServicio.elementAt(i);
			detalleAnterior.addElement(new DetalleServicio(detalle.getProducto(),detalle.getCantidad(),detalle.getCondicionVenta(),detalle.getCodSupervisor(),detalle.getPrecioFinal(),detalle.getMontoImpuesto(),detalle.getTipoCaptura(),detalle.getCodPromocion(),detalle.getTipoEntrega()));
			if ((detalle.getProducto().getPromociones().size()>0)&&(this.recalcularSaldo)) {
				//jgraterol: Por que solo la primera promo? si hay varias por que no se toman en cuenta?
				//jgraterol: solo se actualizan las promociones P, no los empaques
				Promocion prom = (Promocion)detalle.getProducto().getPromociones().elementAt(0);
				double pFinal = 0.0;
				// El producto posee una promoción, vemos si es mejor a la actual
				if (detalle.getCondicionVenta().equalsIgnoreCase(Sesion.condicionNormal)) {
					if (prom.getPorcentajeDescuento()>0)
						pFinal = detalle.getProducto().getPrecioRegular()*(1-(prom.getPorcentajeDescuento()/100));
					else
						pFinal = prom.getPrecioFinal();
					if (pFinal<(detalle.getPrecioFinal()-0.01)) {
						recalculado = true;
						Auditoria.registrarAuditoria("Recalculando Saldo de Apartado. Nueva promoción aplicada al producto " + detalle.getProducto().getCodProducto(), 'T');
						detalle.setCodPromocion(prom.getCodPromocion());
						detalle.setCondicionVenta(Sesion.condicionPromocion);
						detalle.setPrecioFinal(pFinal);
						if ((detalle.getMontoImpuesto()==0)&&(this.getCliente().isExento())) {
							montoImpuesto = 0.0;
						} else {
							montoImpuesto = MathUtil.stableMultiply(detalle.getPrecioFinal(), detalle.getProducto().getImpuesto().getPorcentaje())/100;
						}						
						detalle.setMontoImpuesto(montoImpuesto);
					}
				} else {
					if (detalle.getCondicionVenta().equalsIgnoreCase(Sesion.condicionEmpaque)) {
						double precioEmpaque = detalle.getPrecioFinal();
						double precioProm = (prom.getPorcentajeDescuento()>0) ? detalle.getProducto().getPrecioRegular()*(1-(prom.getPorcentajeDescuento()/100)) : prom.getPrecioFinal();
						if (precioProm<(precioEmpaque-0.01)) {
							recalculado = true;
							Auditoria.registrarAuditoria("Recalculando Saldo de Apartado. Nueva promoción aplicada al producto " + detalle.getProducto().getCodProducto(), 'T');
							detalle.setCodPromocion(prom.getCodPromocion());
							detalle.setCondicionVenta(Sesion.condicionPromocion);
							detalle.setPrecioFinal(precioProm);
							if ((detalle.getMontoImpuesto()==0)&&(this.getCliente().isExento())) {
								montoImpuesto = 0.0;
							} else {
								montoImpuesto = MathUtil.stableMultiply(detalle.getPrecioFinal(), detalle.getProducto().getImpuesto().getPorcentaje())/100;
							}
							detalle.setMontoImpuesto(montoImpuesto);
						}
					} else {
						if (detalle.getCondicionVenta().equalsIgnoreCase(Sesion.condicionPromocion)) {
							double precioPromAnterior = detalle.getPrecioFinal();
							double precioProm = (prom.getPorcentajeDescuento()>0) ? detalle.getProducto().getPrecioRegular()*(1-(prom.getPorcentajeDescuento()/100)) : prom.getPrecioFinal();
							if (precioProm<(precioPromAnterior-0.01)) {
								recalculado = true;
								Auditoria.registrarAuditoria("Recalculando Saldo de Apartado. Nueva promoción aplicada al producto " + detalle.getProducto().getCodProducto(), 'T');
								detalle.setCodPromocion(prom.getCodPromocion());
								detalle.setCondicionVenta(Sesion.condicionPromocion);
								detalle.setPrecioFinal(precioProm);
								if ((detalle.getMontoImpuesto()==0)&&(this.getCliente().isExento())) {
									montoImpuesto = 0.0;
								} else {
									montoImpuesto = MathUtil.stableMultiply(detalle.getPrecioFinal(), detalle.getProducto().getImpuesto().getPorcentaje())/100;
								}
								detalle.setMontoImpuesto(montoImpuesto);
							}
						}
					}
				}
			}
		}
		this.actualizarMontoServ();
		if (logger.isDebugEnabled()) {
			logger.debug("recalculadoPromocionesApartado() - end");
		}
		return recalculado;
	}
	
	/**
	 * Recalculo de las promociones agregadas por la extensión de promociones
	 * @return boolean indica si se hizo algun recalculo en los detalles
	 */
	public boolean recalculadoPromocionesNuevas(){
		return (new ActualizadorPreciosSACFactory()).getActualizadorPreciosSACInstance().recalculadoPromociones();
	}

	/**
	 * Método recalculadoPromocionesApartado
	 * 
	 * @return
	 * boolean
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public boolean recalculadoImpuestoApartado() {
		if (logger.isDebugEnabled()) {
			logger.debug("recalculadoImpuestoApartado() - start");
		}

		boolean recalculado = false;
	
		this.detalleAnterior = new Vector<DetalleServicio>();
		for (int i=0; i<detallesServicio.size(); i++) {
			DetalleServicio detalle = (DetalleServicio)detallesServicio.elementAt(i);
			detalleAnterior.addElement(new DetalleServicio(detalle.getProducto(),detalle.getCantidad(),detalle.getCondicionVenta(),detalle.getCodSupervisor(),detalle.getPrecioFinal(),detalle.getMontoImpuesto(),detalle.getTipoCaptura(),detalle.getCodPromocion(),detalle.getTipoEntrega()));
			// Chequeamos que el monto impuesto sea el mismo.
			double tasaActual = detalle.getProducto().getImpuesto().getPorcentaje();
			double tasaApartado = (detalle.getMontoImpuesto() * 100) / detalle.getPrecioFinal();
			if (((tasaActual<(tasaApartado-0.01))||(tasaActual>(tasaApartado+0.01))) && (this.getMontoImpuesto()!=0 || !this.getCliente().isExento())) {
				Auditoria.registrarAuditoria("Recalculando Saldo de Apartado. Nueva Tasa de Impuesto Vigente", 'T');
				recalculado = true;
				//detalle.getProducto().getImpuesto().setPorcentaje(detalle.getProducto().getImpuesto().getPorcentaje());
				detalle.setMontoImpuesto(calcularImpuesto(detalle.getProducto(), detalle.getPrecioFinal()));
			}
		}
		this.actualizarMontoServ();

		if (logger.isDebugEnabled()) {
			logger.debug("recalculadoImpuestoApartado() - end");
		}
		return recalculado;
	}
	/**
	 * Método reversarCalculoApartado
	 * 
	 * 
	 * void
	 */
	public void reversarCalculoApartado() {
		if (logger.isDebugEnabled()) {
			logger.debug("reversarCalculoApartado() - start");
		}

		detallesServicio.clear();
		for (int i=0; i<detalleAnterior.size(); i++) {
			DetalleServicio detalle = (DetalleServicio)detalleAnterior.elementAt(i);
			detallesServicio.addElement(new DetalleServicio(detalle.getProducto(),detalle.getCantidad(),detalle.getCondicionVenta(),detalle.getCodSupervisor(),detalle.getPrecioFinal(),detalle.getMontoImpuesto(),detalle.getTipoCaptura(),detalle.getCodPromocion(),detalle.getTipoEntrega()));
		}
		this.actualizarMontoServ();
		detalleAnterior = null;

		if (logger.isDebugEnabled()) {
			logger.debug("reversarCalculoApartado() - end");
		}
	}
	/**
	 * Método isRecalcularSaldo
	 * 
	 * @return
	 * boolean
	 */
	public boolean isRecalcularSaldo() {
		if (logger.isDebugEnabled()) {
			logger.debug("isRecalcularSaldo() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("isRecalcularSaldo() - end");
		}
		return recalcularSaldo;
	}

	/**
	 * Método calcularCargoPorServicio
	 * 
	 * @return
	 * float
	 */
	public double calcularMtoCargoPorServicio() {
		if (logger.isDebugEnabled()) {
			logger.debug("calcularMtoCargoPorServicio() - start");
		}

		//double montoCargo = (this.getMontoBase()+this.getMontoImpuesto()) * (this.getCargoPorServicio() / 100);
		// CAMBIO, Cargo por Servicio debe ser calculado sobre el Monto Base (Sin Impuesto)
		double montoCargo = this.getMontoBase() * (this.getCargoPorServicio() / 100);

		if (logger.isDebugEnabled()) {
			logger.debug("calcularMtoCargoPorServicio() - end");
		}
		return montoCargo;
	}

	/**
	 * Método getCargoPorServicio
	 * 
	 * @return
	 * float
	 */
	public float getCargoPorServicio() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCargoPorServicio() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getCargoPorServicio() - end");
		}
		return cargoPorServicio;
	}
	
	/**
	 * Método verificarEstadoVencimiento
	 * 	verifca la fecha de vencimiento de los apartados activos y actualiza el estado del apartado en caso de estar vencido
	 * 
	 * @return
	 * float
	 */
	public Date verificarEstadoVencimiento() throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("verificarEstadoVencimiento() - start");
		}

		Calendar fechaVencimiento = Calendar.getInstance();
		if(this.getTipoApartado()==0){
			fechaVencimiento.setTime(this.getFechaServicio());
			if (this.getTipoVigencia().equalsIgnoreCase("Dia")) {
				fechaVencimiento.add(Calendar.DATE,this.getTiempoVigencia());
			} else {
				if (this.getTipoVigencia().equalsIgnoreCase("Mes")) {
					fechaVencimiento.add(Calendar.MONTH,this.getTiempoVigencia());
				}
			}
		} else {
			fechaVencimiento.setTime(this.getFechaVencimiento());
		}
		
		// Sumamos un día a la fecha de Vencimiento para validar estado de Vencimiento
		fechaVencimiento.add(Calendar.DATE, 1);
		
		//Si el apartado está activo chequemos la fecha de vencimiento
		if(this.getEstadoServicio() == Sesion.APARTADO_ACTIVO) {			
			 //Verificamos si el apartado recuperado se encuentra vencido e incializamos la variable correspondiente
			 Calendar fechaActual = Calendar.getInstance();
			 fechaActual.setTime(Sesion.getFechaSistema());
			 
			 if(fechaActual.after(fechaVencimiento)){
				this.setEstadoServicio(Sesion.APARTADO_VENCIDO);
				BaseDeDatosServicio.actualizarEdoServicio(this.getCodTienda(),Sesion.APARTADO,this.getNumServicio(),this.getFechaServicio(),Sesion.APARTADO_VENCIDO, fechaVencimiento.getTime(), false);			  
			 }
	   	}

		// Restamos un día a la fecha de Vencimiento
		fechaVencimiento.add(Calendar.DATE, -1);
		Date returnDate = fechaVencimiento.getTime();
		if (logger.isDebugEnabled()) {
			logger.debug("verificarEstadoVencimiento() - end");
		}
	   	return returnDate;
	}

	/**
	 * Método getMtoMinimoApartado
	 * 
	 * @return
	 * double
	 */
	public double getMtoMinimoApartado() {
		return mtoMinimoApartado;
	}

	/**
	 * Método getTipoVigencia
	 * 
	 * @return
	 * String
	 */
	public String getTipoVigencia() {
		return tipoVigencia;
	}
	
	/*****************************************************************************
	 * Métodos agregados por promociones
	 *****************************************************************************/

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<DetalleServicio> getDetalleAnterior() {
		return detalleAnterior;
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void setDetalleAnterior(Vector<DetalleServicio> detalleAnterior) {
		this.detalleAnterior = detalleAnterior;
	}

	public boolean isApartadoRecalculado() {
		return apartadoRecalculado;
	}

	public void setApartadoRecalculado(boolean apartadoRecalculado) {
		this.apartadoRecalculado = apartadoRecalculado;
	}
	

	/**
	 * Recorre todos los detalles del servicio para aplicar descuento corporativo
	 * @throws XmlExcepcion
	 * @throws FuncionExcepcion
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void actualizarPreciosDetallePromociones() throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
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
			DetalleServicio detalle = (DetalleServicio)this.getDetallesServicio().elementAt(i);
			//ACTUALIZACION BECO 31/07/2008 Lo mismo de arriba (quitar el descuento empleado) pero ahora trabajando 
			//con el vector de condiciones de venta agregado por el módulo de promociones
			if(!detalle.isCondicionEspecial() &&
					!detalle.contieneAlgunaCondicion(condicionesCorporativo)){
				detalle.setCondicionVentaPromociones(new Vector<CondicionVenta>());
				detalle.setCondicionVenta(Sesion.condicionNormal);
				detalle.setPrecioFinal(detalle.getProducto().getPrecioRegular());
				try{
					detalle.setMontoDctoCombo(0);
					detalle.setMontoDctoCorporativo(0);
					 
				} catch(NullPointerException e){
					
				}
			} 
						
			if (productos.size()== 0){
				productos.addElement(detalle);
			} else {
				registrado = false;
				for(int j=0; j<productos.size();j++) {
					codP = ((DetalleServicio)productos.elementAt(j)).getProducto().getCodProducto();
					if(codP.equals((detalle.getProducto()).getCodProducto())){
						registrado = true;	
						break;					
					}
				}
				if(registrado == false){
					productos.addElement(detalle);
				}			
			}
		}
	
		// ACTUALIZACION BECO: 30/05/2008
		// Ahora actualizamos los precios de los detalles por cada producto.
		// Es necesario para cada producto volver a cargarle sus promociones 
		for (int i=0; i<productos.size(); i++) {
			Producto prod = ((DetalleServicio)productos.elementAt(i)).getProducto();
						
			//si la extension beco de promociones no esta activa el vector nuevo es vacio
			/*Vector promociones = prod.getPromociones();
			promociones.addAll(actualizadorPrecios.getPromocionesPatrocinantesPorProducto(prod.getCodProducto(), promociones));
			*/
			
			(new ActualizadorPreciosSACFactory()).getActualizadorPreciosSACInstance().actualizarPrecios(prod, this, true);
		}
		
		this.actualizarMontoServ();

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarPrecios(boolean) - end");
		}
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Pago> getPagosEspeciales() {
		return pagosEspeciales;
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void setPagosEspeciales(Vector<Pago> pagosEspeciales) {
		this.pagosEspeciales = pagosEspeciales;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public int getTipoApartado() {
		return tipoApartado;
	}

	public void setTipoApartado(int tipoApartado) {
		this.tipoApartado = tipoApartado;
	}

	public String getDescripcionTipoApartado() {
		return descripcionTipoApartado;
	}

	public void setDescripcionTipoApartado(String descripcionTipoApartado) {
		this.descripcionTipoApartado = descripcionTipoApartado;
	}
}
