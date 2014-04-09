/**
 * =============================================================================
 * Proyecto   : CRTrabajando
 * Paquete    : com.becoblohm.cr.manejadorsesion
 * Programa   : MaquinaDeEstadoServicio.java
 * Creado por : irojas
 * Creado en  : 26-abr-04 8:39:21
 *
 * (c) Becoblohm, C.A.
 * -----------------------------------------------------------------------------
 * 				Actualizaciones
 * -----------------------------------------------------------------------------
 * Versión     : 2.0
 * Fecha       : 25-jul-06 10:17
 * Analista    : yzambrano
 * Descripción : Buscar cliente temporal
 * =============================================================================
 * Versión     : 2.0
 * Fecha       : 11-jul-06 10:17
 * Analista    : yzambrano
 * Descripción : Colocar apartado en espera.
 * =============================================================================
 * Versión     : 1.2
 * Fecha       : 14-may-04 10:17
 * Analista    : gmartinelli
 * Descripción : Implementado manejo de Auditorias.
 * =============================================================================
 * Versión     : 1.1
 * Fecha       : 10-may-04 9:42
 * Analista    : gmartinelli
 * Descripción : Actualizados comentarios JavaDocs.
 * =============================================================================
 * Versión     : 1.0
 * Fecha       : 26-abr-04 8:39:21
 * Analista    : irojas
 * Descripción : Implementación inicial.
 * =============================================================================
 */
package com.becoblohm.cr.manejadorsesion;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.beco.cr.actualizadorPrecios.promociones.PromocionExt;
import com.becoblohm.cr.CR;
import com.becoblohm.cr.excepciones.AbonoExcepcion;
import com.becoblohm.cr.excepciones.AfiliadoUsrExcepcion;
import com.becoblohm.cr.excepciones.AnulacionExcepcion;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.BonoRegaloException;
import com.becoblohm.cr.excepciones.ClienteExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.ExcepcionLR;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.LineaExcepcion;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.PagoExcepcion;
import com.becoblohm.cr.excepciones.ProductoExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.gui.CargoPorServicio;
import com.becoblohm.cr.gui.bonoregalo.BRCargaServicio;
import com.becoblohm.cr.manejadorauditoria.Auditoria;
import com.becoblohm.cr.extensiones.ActualizadorPreciosFactory;
import com.becoblohm.cr.extensiones.ActualizadorPreciosSAC;
import com.becoblohm.cr.extensiones.ActualizadorPreciosSACFactory;
import com.becoblohm.cr.extensiones.ManejadorReportesFactory;
import com.becoblohm.cr.extensiones.ManejoPagosFactory;
import com.becoblohm.cr.extensiones.RegistroCliente;
import com.becoblohm.cr.extensiones.RegistroClienteFactory;
import com.becoblohm.cr.extensiones.impl.manejarpago.FormaDePago;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.manejarbr.DetalleTransaccionBR;
import com.becoblohm.cr.manejarbr.OpcionBR;
import com.becoblohm.cr.manejarbr.PromocionBR;
import com.becoblohm.cr.manejarbr.VentaBR;
import com.becoblohm.cr.manejarservicio.Abono;
import com.becoblohm.cr.manejarservicio.Apartado;
import com.becoblohm.cr.manejarservicio.Cotizacion;
import com.becoblohm.cr.manejarservicio.DetalleServicio;
import com.becoblohm.cr.manejarservicio.ListaRegalos;
import com.becoblohm.cr.manejarusuario.Usuario;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.manejarventa.DetalleTransaccion;
import com.becoblohm.cr.manejarventa.Producto;
import com.becoblohm.cr.manejarventa.Transaccion;
import com.becoblohm.cr.mediadoresbd.BaseDeDatosServicio;
import com.becoblohm.cr.mediadoresbd.BaseDeDatosVenta;
import com.becoblohm.cr.mediadoresbd.ConexionServCentral;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.utiles.MathUtil;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.becoblohm.cr.utiles.Validaciones;
import com.epa.crprinterdriver.CRFiscalPrinterOperations;
import com.epa.crprinterdriver.PrinterNotConnectedException;

/** 
 * Descripción: 
 * 		Clase que maneja la transición de estados de la Caja registradora del 
 * módulo de Servicio (Apartado, Cotizaciones, etc). También se manejan las
 * autorizaciones y auditorias de las operaciones que se realicen en las cajas
 * con respecto al módulo de Servicios.
 */
/*
* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Se comentó variable sin uso
* Fecha: agosto 2011
*/
@SuppressWarnings("unused")
public class MaquinaDeEstadoServicio {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MaquinaDeEstadoServicio.class);

	private Apartado apartado;
	//private Servicio servicio;
	private Cotizacion cotizacion;
	private ListaRegalos listaRegalos, listaRegalosRespaldo;
	private DecimalFormat df = new DecimalFormat("#,##0.00");
	private VentaBR ventaBR;
	private Transaccion transaccionPorImprimir;
	
	/**
	 * Matriz de productos patrocinantes
	 * clave = codigo de una promocion tipo F (ahorro en compra)
	 * valor = vector de productos patrocinantes
	 * **/
	/*
	* Modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en el 'TreeMap'
	* Fecha: agosto 2011
	*/
	private TreeMap<PromocionExt,Vector<Producto>> productosPatrocinantes = null;
	private ActualizadorPreciosSAC actualizadorPrecios;
	//private HashMap productosComplementario;
	
	/**
	 * Contructor para MaquinaDeEstadoSservicio
	 * 		Crea la ME para el manejo de servicios. Inicializa los atributos
	 */
	public MaquinaDeEstadoServicio() {
		apartado = null;
		listaRegalos = null;
		productosPatrocinantes = (new ActualizadorPreciosSACFactory()).getActualizadorPreciosSACInstance().cargarPatrocinantes();
		//productosComplementario = (new ActualizadorPreciosSACFactory()).getActualizadorPreciosSACInstance().cargarProductoComplementario();
	}
	
	/**
	 * Method obtenerCantidadProds
	 * 		Obtiene la cantidad de productos (items, unidades de venta) existentes en
	 * el servicio actual. 
	 * @return float - Cantidad de prodcutos existentes en el Detalle
	 */
	public float obtenerCantidadProds() {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCantidadProds() - start");
		}
	
		float cantProductos = this.apartado.obtenerCantidadProds();

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCantidadProds() - end");
		}
		return cantProductos;
	}
	
	/**
	 * Method obtenerEstado
	 * 		Obtener el nuevo estado para la caja registradora dependiendo del tipo de apartado a cargar. Si es
	 * apartado en espera va a registro de apartado si es un apartado ya registrado va a consulta de apartado.
	 * @param numApartado número de apartado almacenado en memoria
	 * @param tipoApartado tipo de apartado recuperado 'E' en espera o cualquier otro estado 
	 * @return String - Nuevo estado de caja registradora 
	 */	
	public String obtenerEstado (int numApartado, char tipoApartado)throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion, AfiliadoUsrExcepcion, PagoExcepcion, BaseDeDatosExcepcion, ConexionExcepcion, ExcepcionCr
	{
		if(tipoApartado == Sesion.ESPERA)
		{
			CR.me.verificarAutorizacion("APARTADO","cargarApartadoEspera");
			return (MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "cargarApartadoEspera"));
		}
		else
		{
			CR.me.verificarAutorizacion("APARTADO","cargarApartado");
			return (MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "cargarApartado"));
		}
	}
	
	
	/**
	 * Método cargarApartado.
	 * 		Carga la información de un apartado junto con su estados de cuenta (Abonos realizados,
	 * 	Abonos Anulados, Pagos de Abonos, Vigencia, etc.)
	 * @param numTda Número de tienda donde fue creado el Apartado/Pedido Especial.
	 * @param fecha
	 * @param numApartado Número de Apartado/Pedido Especial a recuperar.
	 * @param tiopApartado E para espera y P para pendiente
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws MaquinaDeEstadoExcepcion Si el estado de la caja es incorrecto para la recuperación de Apartados/Pedidos Especiales.
	 * @throws XmlExcepcion Si ocurre un error registrando la Auditoría Xml.
	 * @throws FuncionExcepcion Si la función no se encuentra en la Base de Datos.
	 * @throws AfiliadoUsrExcepcion Si ocurre un error autorizando la función. 
	 * @throws PagoExcepcion Si ocurre un error recuperando los abonos del Apartado/Pedido Especial
	 * @throws BaseDeDatosExcepcion Si ocurre un error con el acceso (sentencias) a la Base de Datos.
	 * @throws ConexionExcepcion Si ocurre un error de conexión con la Base de Datos.
	 * @throws ExcepcionCr Si se presenta un error de autorización y/o validación de usuario.
	 */
	public void cargarApartado(int numTda, String fecha, int numApartado, char tipo) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion, AfiliadoUsrExcepcion, PagoExcepcion, BaseDeDatosExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarApartado(int, String, int) - start");
		}

		String edoFinalCaja = obtenerEstado (numApartado, tipo);
		String fechaSql = fecha.substring(0,4) + "-" + fecha.substring(4,6) + "-" + fecha.substring(6);
		this.apartado = new Apartado(numTda, fechaSql, numApartado);	
	
		try {
			CR.me.verificarAutorizacion("APARTADO","cargarApartado",this.apartado.getCliente());
			try{ CR.crVisor.enviarString("APARTADO/P. ESPECIAL", apartado.getCliente().getNombreCompleto()); }
			catch (Exception e){
				logger.error("cargarApartado(int, String, int)", e);
			}
		} catch (ExcepcionCr e) {
			logger.error("cargarApartado(int, String, int)", e);
		}

		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("cargarApartado(int, String, int) - end");
		}
	}
	
	/**
	 * Método obtenerApartados.
	 * 		Este método  identiofica el tipo de código que se introdujo (el código de un cliente o el de un apartado)
	 * 		En caso de ser el de un cliente, busca todos los apartados para mostrarlos en una pantalla y permitir la selección de uno
	 * 		Si es el código del apartado se recupera directamente
	 * 		(Sólo carga las cabeceras de los apartados)
	 * @param identificador identificador del apartado/cliente
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws MaquinaDeEstadoExcepcion Si el estado de la caja es incorrecto para la recuperación de Apartados/Pedidos Especiales.
	 * @throws XmlExcepcion Si ocurre un error registrando la Auditoría Xml.
	 * @throws FuncionExcepcion Si la función no se encuentra en la Base de Datos.
	 * @throws AfiliadoUsrExcepcion Si ocurre un error autorizando la función. 
	 * @throws PagoExcepcion Si ocurre un error recuperando los abonos del Apartado/Pedido Especial
	 * @throws BaseDeDatosExcepcion Si ocurre un error con el acceso (sentencias) a la Base de Datos.
	 * @throws ConexionExcepcion Si ocurre un error de conexión con la Base de Datos.
	 * @throws ExcepcionCr Si se presenta un error de autorización y/o validación de usuario.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Apartado> obtenerApartados(String identificador) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerApartados(String) - start");
		}

		Sesion.setUbicacion("APARTADO","cargarApartado");
		Auditoria.registrarAuditoria("Buscando Apartados con identificador " + identificador,'T');
		Vector<Apartado> returnVector = BaseDeDatosServicio
				.obtenerApartados(identificador);
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerApartados(String) - end");
		}
		return returnVector;
	}
	
	/**
	 * Método getApartado.
	 * 		Obtiene el Apartado Activo.
	 * @return Apartado - Objeto Apartado que se está utilizado.
	 */
	public Apartado getApartado() {
		if (logger.isDebugEnabled()) {
			logger.debug("getApartado() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getApartado() - end");
		}
		return apartado;
	}
	
	/**
	 * Método ingresarProductoApartado.
	 * 		Ingresa un nuevo producto al Apartado
	 * @param codP Código de Producto.
	 * @param tipoC Tipo de Captura de Producto (Teclado, Escáner).
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws ProductoExcepcion Si el producto no se encuentra en la Base de Datos.
	 * @throws MaquinaDeEstadoExcepcion Si el estado de la caja es incorrecto para ingreso de Producto.
	 * @throws XmlExcepcion Si ocurre un error registrando la Auditoría Xml.
	 * @throws FuncionExcepcion Si la función no se encuentra en la Base de Datos.
	 * @throws BaseDeDatosExcepcion Si ocurre un error con el acceso (sentencias) a la Base de Datos.
	 * @throws ConexionExcepcion Si ocurre un error de conexión a la Base de Datos.
	 * @throws ExcepcionCr Si se presenta un error de autorización y/o validación de usuario.
	 */
	public void ingresarProductoApartado(String codP, String tipoC) throws UsuarioExcepcion, ProductoExcepcion, MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("ingresarProductoApartado(String, String) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "ingresarProductoApartado");
		
		CR.me.verificarAutorizacion("APARTADO","ingresarProductoApartado",this.apartado.getCliente());
		
		apartado.ingresarLineaProducto(codP, tipoC);
		try { CR.crVisor.enviarString(((DetalleServicio)apartado.getDetallesServicio().lastElement()).getProducto().getDescripcionCorta(),0,df.format(((DetalleServicio)apartado.getDetallesServicio().lastElement()).getPrecioFinal()+((DetalleServicio)apartado.getDetallesServicio().lastElement()).getMontoImpuesto()),2); }
		catch(Exception e) {
			logger.error("ingresarProductoApartado(String, String)", e);
}
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("ingresarProductoApartado(String, String) - end");
		}
	}

	/**
	 * Method aplicarDesctoPorDefecto.
	 * 		Permite aplicar el descuento por artículo defectuoso a un producto indicado. Se verifica si el usuario activo posee la autorización.
	 * 		Si no es así se solicita la misma.
	 * @param renglon Renglón del producto al que se le quiere aplicar el descuento.
	 * @param descto El descuento que se le quiere aplicar al producto. puede ser un porcentaje o un nuevo precio.
	 * @param cantidad Cantidad deProductos al que se le quiere aplicar dicho descuento
	 * @param esPorcentaje Boolean que indica si el descuento es un porcentaje o un nuevo precio.
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws ProductoExcepcion Si el producto indicado no existe
	 * @throws MaquinaDeEstadoExcepcion Si el estado de la caja es incorrecto para realizar rebajas.
	 * @throws XmlExcepcion Si ocurre un error registrando la Auditoría Xml.
	 * @throws FuncionExcepcion Si la función no se encuentra en la Base de Datos.
	 * @throws BaseDeDatosExcepcion Si ocurre un error con el acceso (sentencias) a la Base de Datos.
	 * @throws ConexionExcepcion Si ocurre un error de conexión a la Base de Datos.
	 * @throws ExcepcionCr Si no existe apartado activo o si la Tienda no permite realizar esta operación.
	 */
	public void aplicarDesctoPorDefecto(int renglon, double descto, float cantidad, boolean esPorcentaje) throws UsuarioExcepcion, ProductoExcepcion, MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarDesctoPorDefecto(int, double, float, boolean) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "aplicarDesctoPorDefecto");
		String codAutorizante = null;
		DetalleServicio linea = null;
		
		Sesion.setUbicacion("APARTADO","aplicarDesctoPorDefecto");
		if (Sesion.isCambiarPrecio()) {
			//Chequeos de datos necesarios antes de pedir la autorización. Es por eso que se realizan en la Máquina de estados
			//Observamos si el renglon del producto existe
			try {
				linea = (DetalleServicio) this.apartado.getDetallesServicio().elementAt(renglon);
			} catch (Exception ex) {
				logger.error(
						"aplicarDesctoPorDefecto(int, double, float, boolean)",
						ex);

				 throw (new ProductoExcepcion("Error al obtener producto, no existe renglon " + renglon + " en la transaccion", ex));
			}
			//Obtenemos el producto
			Producto prod = linea.getProducto();
		
			if((!prod.isIndicaFraccion()) && (cantidad%1)!=0){
				throw (new ProductoExcepcion("El producto " + prod.getCodProducto() + " NO admite cantidades fraccionadas(decimales)"));
			}
		
			if(cantidad <= 0 || cantidad > linea.getCantidad()) {
				throw (new ProductoExcepcion("Cantidad de productos invalida. Debe ser menor o igual a la del renglon."));
			}
		
			if (esPorcentaje) {
				//verificamos que el porcentaje a aplicar sea válido
				if(descto > 100 || descto <= 0) {
					throw (new ProductoExcepcion("Valor de Porcentaje a aplicar inválido"));
				}
			} else {
				//verificamos que el nuevo precio a aplicar sea válido
				if(descto <= 0 || descto >= linea.getPrecioFinal()) {
					throw (new ProductoExcepcion("Valor de Nuevo Precio a aplicar inválido"));
				}
			}
			
			// Verificamos si la función requiere autorización
			// Si es así realiza la operación si no es así lanza una excepción
			codAutorizante = CR.me.verificarAutorizacion ("APARTADO","aplicarDesctoPorDefecto",this.apartado.getCliente());
			this.apartado.aplicarDesctoPorDefecto(renglon, descto,cantidad, esPorcentaje, codAutorizante);
		} else {
			throw (new MaquinaDeEstadoExcepcion("No se puede realizar la operacion. La tienda no tiene activa esta opcion"));				
		}
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);	

		if (logger.isDebugEnabled()) {
			logger
					.debug("aplicarDesctoPorDefecto(int, double, float, boolean) - end");
		}
	}

	/**
	 * Method registrarApartado.
	 * 		Registra un nuevo Apartado en la Base de Datos.
	 * @param tipoAbonoInicial Tipo del Abono Inicial (30%, 25%, 50%, etc.).
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws MaquinaDeEstadoExcepcion Si el estado de la caja es incorrecto para el registro de Apartados/Pedidos Especiales.
	 * @throws ConexionExcepcion Si ocurre un error con el acceso a la Base de Datos.
	 * @throws ExcepcionCr Si no existe apartado activo o si la Tienda no permite realizar esta operación.
	 */
	public void registrarApartado(char tipoAbonoInicial) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("registrarApartado(char) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "registrarApartado");
		
		CR.me.verificarAutorizacion("APARTADO","registrarApartado",this.apartado.getCliente());
		
		Sesion.setUbicacion("APARTADO","registrarApartado");
		
		//Verificamos si existen detalles en el apartado para poder finalizarlo
		if (apartado.getDetallesServicio().size()<=0) {
			throw (new MaquinaDeEstadoExcepcion("No puede finalizar el apartado. No existen detalles."));
		}
		
		if(apartado.getCliente().getCodCliente() == null) {
			throw (new MaquinaDeEstadoExcepcion("No puede finalizar el apartado. Debe ingresar el afiliado."));
		}
		
		// Iniciamos la sincronizacion de Afiliado para que el afiliado del apartado suba al Serv de Tienda
		try {
			BaseDeDatosServicio.registrarClienteTemporal(apartado.getCliente(), false);
		} catch (Exception e) {
			e.printStackTrace();
		}

		apartado.registrarApartado(tipoAbonoInicial);
		
		try { CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ..."); }
		catch (Exception e) {
			logger.error("registrarApartado(char)", e);
}
		apartado = null;

		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("registrarApartado(char) - end");
		}
	}

	/**
	 * Method registrarAbonosApartado.
	 * 		Registra y/o actualiza los abonos de un Apartado/Pedido Especial
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws MaquinaDeEstadoExcepcion Si el estado de la caja es incorrecto para el registro/Actualización de Abonos de Apartados/Pedidos Especiales.
	 * @throws ConexionExcepcion Si ocurre un error con el acceso a la Base de Datos.
	 * @throws ExcepcionCr Si no existe apartado activo o si la Tienda no permite realizar esta operación.
	 */
	public void registrarAbonosApartado() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("registrarAbonosApartado() - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "registrarAbonosApartado");
		
		Sesion.setUbicacion("APARTADO","registrarAbonosApartado");
		
		CR.me.verificarAutorizacion("APARTADO","registrarAbonosApartado", this.apartado.getCliente());

		apartado.registrarAbonosApartado();
		
		apartado=null;
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("registrarAbonosApartado() - end");
		}
	}

	/**
	 * Method anularApartadoActivo.
	 * 		Anula el apartado Activo
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws MaquinaDeEstadoExcepcion Si el estado de la caja es incorrecto para anular un apartado.
	 * @throws ConexionExcepcion Si ocurre un error con el acceso a la Base de Datos.
	 * @throws ExcepcionCr Si no existe apartado activo o si la Tienda no permite realizar esta operación.
	 */
	
	public void anularApartadoActivo() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("anularApartadoActivo() - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "anularApartadoActivo");
		
		Sesion.setUbicacion("APARTADO","anularApartadoActivo");
		
		String autorizante = CR.me.verificarAutorizacion("APARTADO","anularApartadoActivo", this.apartado.getCliente());
		
		apartado.anularApartadoActivo();
		try { CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ..."); }
		catch(Exception e){
			logger.error("anularApartadoActivo()", e);
}
		apartado = null;

		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("anularApartadoActivo() - end");
		}
	}

	/**
	 * Method cambiarCantidad.
	 * 	Cambia la cantidad del último renglon del Apartado
	 * @param cantidad Cantidad a agregar al renglon
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws MaquinaDeEstadoExcepcion Si el estado de la caja es incorrecto para cambios de cantidad.
	 * @throws ConexionExcepcion Si ocurre un error con el acceso a la Base de Datos.
	 * @throws ExcepcionCr Si no existe apartado activo o si la Tienda no permite realizar esta operación.
	 */
	public void cambiarCantidad(float cantidad) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("cambiarCantidad(float) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "cambiarCantidad");
		
		Sesion.setUbicacion("APARTADO", "cambiarCantidad");
		CR.me.verificarAutorizacion ("APARTADO","cambiarCantidad", this.apartado.getCliente());
		this.apartado.agregarCantidad(cantidad, this.apartado.getDetallesServicio().size()-1);
		
		//Se envía al Visor la multiplicación de la cantidad agregada por el precio del producto
		String precio = df.format(((DetalleServicio)apartado.getDetallesServicio().lastElement()).getPrecioFinal() + ((DetalleServicio)apartado.getDetallesServicio().lastElement()).getMontoImpuesto());
		String cantXprecio = df.format(cantidad) + " X " +  precio;
		try { CR.crVisor.enviarString(((DetalleServicio)apartado.getDetallesServicio().lastElement()).getProducto().getDescripcionCorta(), 0, cantXprecio, 2); }
		catch (Exception e) {
			logger.error("cambiarCantidad(float)", e);
		}

		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("cambiarCantidad(float) - end");
		}
	}

	/**
	 * Method abonar.
	 * 	se encarga de agregar un abono
	 * @param montoMin Monto Mínimo del Abono
	 * @param pagos Vector de Pagos realizados anteriormente
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws MaquinaDeEstadoExcepcion Si el estado de la caja es incorrecto.
	 * @throws ConexionExcepcion Si ocurre un error con el acceso a la Base de Datos.
	 * @throws ExcepcionCr Si no existe apartado activo o si la Tienda no permite realizar esta operación.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/

	@SuppressWarnings("unchecked")
	public void abonar(double montoMin, Vector<Pago> pagos, boolean imprimir) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("abonar(double, Vector, boolean) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "abonar");
		Sesion.setUbicacion("APARTADO","abonar");
		
		CR.me.verificarAutorizacion ("APARTADO","abonar", this.apartado.getCliente());
		
		this.apartado.iniciarAbonoNuevo(Sesion.getTienda().getNumero(), 
										Sesion.getCaja().getNumero(), 
										Sesion.getFechaSistema(), 
										Sesion.getHoraSistema(),
										'A',
										Sesion.usuarioActivo.getNumFicha());
		
		try { CR.crVisor.enviarString("ABONO", 0, df.format(montoMin), 2); }
		catch (Exception e) {
			logger.error("abonar(double, Vector, boolean)", e);
		}
		//ACTUALIZACION BECO: Módulo de promociones
		if(CR.meServ.getApartado()!=null && CR.meServ.getApartado().montoAbonos()+montoMin >= CR.meServ.getApartado().consultarMontoServ()-0.01){
			for(int i=0;i<CR.meServ.getApartado().getPagosEspeciales().size();i++){
				pagos.addElement(CR.meServ.getApartado().getPagosEspeciales().elementAt(i));
			}
		}
		Vector<Object> pagosAbono = ManejoPagosFactory.getInstance().realizarPago(montoMin, pagos, apartado.getCliente(),0,true);
		
		for (int i=0; i<((Vector<Pago>)pagosAbono.elementAt(0)).size(); i++) {
			Pago p = (Pago)((Vector<Pago>)pagosAbono.elementAt(0)).elementAt(i);
			this.abonar(p);
		}

		this.apartado.finalizarAbono(Sesion.getHoraSistema(), ((Double)pagosAbono.elementAt(1)).doubleValue(), imprimir);
		
		//18/04/11 ACTUALIZACION PROYECTO BONO REGALO
		//Aplicar promociones asociadas al pago con bono regalo
		//jgraterol
		Cliente cliente = CR.meServ.getApartado().getCliente();
		Vector<Pago> pagosCR = new Vector<Pago>();
		for (int i=0; i<((Vector<Pago>)pagosAbono.elementAt(0)).size(); i++) {
			Pago p = (Pago)((Vector<Pago>)pagosAbono.elementAt(0)).elementAt(i);
			pagosCR.addElement(p);
		}
		if(apartado.montoAbonos()< apartado.consultarMontoServ()-0.01)
			CR.meServ.aplicarPromocionesBR(pagosCR, cliente);
		
		try { CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ..."); }
		catch (Exception e) {
			logger.error("abonar(double, Vector, boolean)", e);
}
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("abonar(double, Vector, boolean) - end");
		}
	}

	/**
	 * Method abonar.
	 * 	Se encarga de agregar un abono
	 * @param monto Monto del Abono
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws MaquinaDeEstadoExcepcion Si el estado de la caja es incorrecto.
	 * @throws ConexionExcepcion Si ocurre un error con el acceso a la Base de Datos.
	 * @throws ExcepcionCr Si no existe apartado activo o si la Tienda no permite realizar esta operación.
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unchecked")
	public void abonar(double monto, boolean imprimir) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("abonar(double, boolean) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "abonar");
		Sesion.setUbicacion("APARTADO","abonar");
		CR.me.verificarAutorizacion ("APARTADO","abonar", this.apartado.getCliente());
		
		Vector<Object> pagosAbono = new Vector<Object>();
		if (MathUtil.roundDouble(apartado.montoAbonos()+monto)<=apartado.consultarMontoServ()) {
			//ACTUALIZACION BECO: Módulo de promociones 4/5/2009
			if(apartado.montoAbonos()+monto >= apartado.consultarMontoServ()-0.01){
				ejecucionPromociones();
				monto = (new ActualizadorPreciosSACFactory()).getActualizadorPreciosSACInstance().sumarDonaciones(apartado,monto, true);
				
			}
			this.apartado.iniciarAbonoNuevo(Sesion.getTienda().getNumero(), 
											Sesion.getCaja().getNumero(), 
											Sesion.getFechaSistema(), 
											Sesion.getHoraSistema(),
											'A',
											Sesion.usuarioActivo.getNumFicha());
			try { CR.crVisor.enviarString("ABONO", 0, df.format(monto), 2); }
			catch (Exception e) {
				logger.error("abonar(double, boolean)", e);
			}
			//Si es el abono final agrego el pago especial
			double montoCupones = 0;
			if(apartado.montoAbonos()+monto >= apartado.consultarMontoServ()-0.01){
				for(int j=0;j<apartado.getPagosEspeciales().size();j++){
					montoCupones += ((Pago)apartado.getPagosEspeciales().elementAt(j)).getMonto();
				}
			}
			
			pagosAbono = ManejoPagosFactory.getInstance().realizarPago(monto-montoCupones,apartado.getCliente(),this.apartado.getNumServicio(),true);
		} else {
		
			/*
			 * Cambio conflictivo, guardando el cambio en caso que haya que echarlo hacia atras
 			 * 20/10/2004 - Victor Medina 10:20am
			 *throw new AbonoExcepcion("Solo restan por pagar " + (apartado.consultarMontoServ()-apartado.montoAbonos()));
			*/
		
			throw new AbonoExcepcion("Solo restan por pagar " + df.format(apartado.consultarMontoServ()-apartado.montoAbonos()));
		}
		for (int i=0; i<((Vector<Pago>)pagosAbono.elementAt(0)).size(); i++) {
			Pago p = (Pago)((Vector<Pago>)pagosAbono.elementAt(0)).elementAt(i);
			this.abonar(p);
		}
		
		/*
			 * Cambio conflictivo, guardando el cambio en caso que haya que echarlo hacia atras
 			 * 20/10/2004 - Victor Medina 10:20am
			 *DecimalFormat df = new DecimalFormat("#,##0.00");
			*/
		this.apartado.finalizarAbono(Sesion.getHoraSistema(), ((Double)pagosAbono.elementAt(1)).doubleValue(), imprimir);
		
		//18/04/11 ACTUALIZACION PROYECTO BONO REGALO
		//Aplicar promociones asociadas al pago con bono regalo
		//jgraterol
		Cliente cliente = CR.meServ.getApartado().getCliente();
		Vector<Pago> pagosCR = new Vector<Pago>();
		for (int i=0; i<((Vector<Pago>)pagosAbono.elementAt(0)).size(); i++) {
			Pago p = (Pago)((Vector<Pago>)pagosAbono.elementAt(0)).elementAt(i);
			pagosCR.addElement(p);
		}
		if(apartado.montoAbonos()+monto < apartado.consultarMontoServ()-0.01)
			CR.meServ.aplicarPromocionesBR(pagosCR, cliente);
		
		try { CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ..."); }
		catch (Exception e) {
			logger.error("abonar(double, boolean)", e);
}
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("abonar(double, boolean) - end");
		}
	}
	
	/**
	 * Method abonar.
	 * 	Realiza la finalizacion del pago para el abono del apartado
	 * @param pago Pago a agregar en el abono
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws MaquinaDeEstadoExcepcion Si el estado de la caja es incorrecto.
	 * @throws ConexionExcepcion Si ocurre un error con el acceso a la Base de Datos.
	 * @throws ExcepcionCr Si no existe apartado activo o si la Tienda no permite realizar esta operación.
	 */
	private void abonar(Pago pago) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("abonar(Pago) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "abonar");
		Sesion.setUbicacion("APARTADO","abonar");
		CR.me.verificarAutorizacion ("APARTADO","abonar", this.apartado.getCliente());
				
		//Verificamos si existen detalles en el apartado para poder efectuar algún abono
		if (apartado.getDetallesServicio().size()<=0) {
			throw (new MaquinaDeEstadoExcepcion("No puede efectuar pago. Debe tener productos su apartado."));
		}
			
		this.apartado.realizarPagoAbono(Sesion.getTienda().getNumero(), 
										Sesion.getCaja().getNumero(), 
										Sesion.getFechaSistema(), 
										Sesion.getHoraSistema(),
										'A',
										Sesion.usuarioActivo.getNumFicha(), 
										pago);
										
		// Actualizamos el estado de la caja
		 Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("abonar(Pago) - end");
		}
	}

	/**
	 * Método cambiarCantidad.
	 * 	Cambia la cantidad de un renglón
	 * @param cantidad Cantidad a agregar al renglon
	 * @param renglon Renglon a modificar la cantidad
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws ConexionExcepcion Si ocurre un error de acceso a la base de datos
	 * @throws ProductoExcepcion Si el renglón es inválido
	 * @throws MaquinaDeEstadoExcepcion Si no se están en el estado correcto
	 * @throws ExcepcionCr Si falla la búsqueda de metodo y modulo en la base de datos para la auditoria 
	 */
	public void cambiarCantidad(float cantidad, int renglon) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("cambiarCantidad(float, int) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "cambiarCantidad");
		
		Sesion.setUbicacion("APARTADO", "cambiarCantidad");
		CR.me.verificarAutorizacion ("APARTADO","cambiarCantidad", this.apartado.getCliente());
		this.apartado.agregarCantidad(cantidad, renglon);
		
		//Se envía al Visor la multiplicación de la cantidad agregada por el precio del producto
		String precio = df.format(((DetalleServicio)apartado.getDetallesServicio().lastElement()).getPrecioFinal() + ((DetalleServicio)apartado.getDetallesServicio().lastElement()).getMontoImpuesto());
		String cantXprecio = df.format(cantidad) + " X " +  precio;
		try { CR.crVisor.enviarString(((DetalleServicio)apartado.getDetallesServicio().lastElement()).getProducto().getDescripcionCorta(), 0, cantXprecio, 2); }
		catch (Exception e) {
			logger.error("cambiarCantidad(float, int)", e);
}

		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("cambiarCantidad(float, int) - end");
		}
	}
	
	/**
	 * Método calcularMontoAbonoInicial.
	 * 	Calcula el monto del abono inicial del apartado
	 * @return double - Monto mínimo del abono inicial
	 */
	public double calcularMontoAbonoInicial(boolean mostrarVisor) {
		if (logger.isDebugEnabled()) {
			logger.debug("calcularMontoAbonoInicial(boolean) - start");
		}

		double monto = this.apartado.montoAbonoInicial();
		if (mostrarVisor) {
			try { CR.crVisor.enviarString("ABONO INICIAL", 0, df.format(monto), 2); }
			catch (Exception e) {
				logger.error("calcularMontoAbonoInicial(boolean)", e);
}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("calcularMontoAbonoInicial(boolean) - end");
		}
		return monto;
	}

	/**
	 * Método calcularMontoAbonoInicial.
	 * 	Calcula el monto del abono inicial del apartado
	 * @return double - Monto mínimo del abono inicial
	 */
	public double calcularMontoAbonoInicial() {
		if (logger.isDebugEnabled()) {
			logger.debug("calcularMontoAbonoInicial() - start");
		}

		double returndouble = this.calcularMontoAbonoInicial(false);
		if (logger.isDebugEnabled()) {
			logger.debug("calcularMontoAbonoInicial() - end");
		}
		return returndouble;
	}

	/**
	 * Método obtenerRenglones.
	 * 	Obtiene las posiciones del detalle donde se encuentra un producto específico
	 * @param codProducto Código de producto a buscar
	 * @return Vector Vector con las posiciones donde se encuentra el producto
	 * @throws ProductoExcepcion Si el producto no se encuentra en el detalle
	 * @throws MaquinaDeEstadoExcepcion Si no se están en el estado correcto
	 * @throws XmlExcepcion Si ocurre un error al registrar en el archivo Xml de auditoria
	 * @throws FuncionExcepcion Si la funcion no se encuentra en la Base de Datos
	 * @throws BaseDeDatosExcepcion Si ocurre un error de acceso (sentencias) a la base de datos
	 * @throws ConexionExcepcion  Si ocurre un error de acceso a la base de datos
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Integer> obtenerRenglones(String codProducto, boolean isCodigoExterno) throws ProductoExcepcion, MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerRenglones(String) - start");
		}

		// Chequeamos el estado de la caja
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "obtenerRenglones");
	
		// Obtenemos el vector de renglones que poseen ese codigo de producto
		Vector<Integer> result = new Vector<Integer>();
		if (apartado!=null)
			result = apartado.obtenerRenglones(codProducto, isCodigoExterno);
		else if (listaRegalos!=null)
			result = listaRegalos.obtenerRenglones(codProducto, isCodigoExterno);
	
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerRenglones(String) - end");
		}
		return result;
	}

	/**
	 * Método anularProducto.
	 * 	Anula unproducto del Apartado
	 * @param renglon Renglon a modificar la cantidad
	 * @param cantidad Cantidad de productos a anular
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws ConexionExcepcion Si ocurre un error de acceso a la base de datos
	 * @throws ProductoExcepcion Si el renglón es inválido
	 * @throws MaquinaDeEstadoExcepcion Si no se están en el estado correcto
	 * @throws ExcepcionCr Si falla la búsqueda de metodo y modulo en la base de datos para la auditoria 
	 */
	public void anularProducto(int renglon, float cantidad) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("anularProducto(int, float) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "anularProducto");
		
		// Verificamos si la función a la que pertenece este método necesita autorización
		// Primero Verificamos el módulo que está activo
		if (apartado != null) { // Estamos en el módulo de APARTADO
			CR.me.verificarAutorizacion ("APARTADO","anularProducto", this.apartado.getCliente());
			Vector<Object> detProd = apartado.anularProducto(renglon, cantidad);
			try { CR.crVisor.enviarString((String)detProd.elementAt(0), 0, "-" + df.format(cantidad) + " X " + df.format(((Double)detProd.elementAt(1)).doubleValue()), 2); }
			catch (Exception e) {
				logger.error("anularProducto(int, float)", e);
			}
		}else if(listaRegalos != null) {
			CR.me.verificarAutorizacion ("LISTA DE REGALOS","anularProducto", this.listaRegalos.getCliente());
			Vector<Object> detProd = listaRegalos.anularProducto(renglon, cantidad);
			try { CR.crVisor.enviarString((String)detProd.elementAt(0), 0, "-" + df.format(cantidad) + " X " + df.format(((Double)detProd.elementAt(1)).doubleValue()), 2); }
			catch (Exception e) {
				logger.error("anularProducto(int, float)", e);
			}
		}
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("anularProducto(int, float) - end");
		}
	}

	/**
	 * Método finalizarConsultaApartado.
	 * 	Finaliza la consulta de un Apartado/Pedido Especial
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws ConexionExcepcion Si ocurre un error de acceso a la base de datos
	 * @throws MaquinaDeEstadoExcepcion Si no se están en el estado correcto
	 * @throws ExcepcionCr Si falla la búsqueda de metodo y modulo en la base de datos para la auditoria 
	 */
	public void finalizarConsultaApartado() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("finalizarConsultaApartado() - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "finalizarConsultaApartado");
		
		CR.me.verificarAutorizacion ("APARTADO","finalizarConsultaApartado", this.apartado.getCliente());
				
		Sesion.setUbicacion("APARTADO", "finalizarConsultaApartado");
		Auditoria.registrarAuditoria("Finalizada consulta de Apartado " + this.apartado.getCodTienda(),'T');
		try { CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ..."); }
		catch (Exception e) {
			logger.error("finalizarConsultaApartado()", e);
}
		this.apartado = null;
		
		// Actualizamos el estado de la caja
		 Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("finalizarConsultaApartado() - end");
		}
	}

	/**
	 * Método anularAbono.
	 * 	Se encarga de anular el abono especificado del apartado activo cambiandole
	 * el estado de 'Activo' a 'Anulado'
	 * @param renglonAbono Número de abono a Anular
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws ConexionExcepcion Si ocurre un error de acceso a la base de datos
	 * @throws MaquinaDeEstadoExcepcion Si no se están en el estado correcto
	 * @throws ExcepcionCr Si falla la búsqueda de metodo y modulo en la base de datos para la auditoria 
	 */
	public void anularAbono(int renglonAbono) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("anularAbono(int) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "anularAbono");
			
		Sesion.setUbicacion("APARTADO","anularAbono");
			
		String autorizante = CR.me.verificarAutorizacion("APARTADO","anularAbono", this.apartado.getCliente());
		if (autorizante == null) {
			autorizante = apartado.getCodCajero();
		}
		Auditoria.registrarAuditoria(autorizante, 
				((Abono)apartado.getAbonos().elementAt(renglonAbono)).getNumAbono(), 
				"Abono anulado", "APARTADO", "anularAbono");
		
		this.apartado.anularAbono(renglonAbono);		
		try { CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ..."); }
		catch (Exception e) {
			logger.error("anularAbono(int)", e);
}
		apartado = null;
			
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("anularAbono(int) - end");
		}
	}
		
	/**
	 * Método anularApartado.
	 * 	Se encarga de anular un apartado. Anulando para ello todos sus abonos y cambiando el estado del apartado a Anulado.
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws ConexionExcepcion Si ocurre un error de acceso a la base de datos
	 * @throws MaquinaDeEstadoExcepcion Si no se están en el estado correcto
	 * @throws ExcepcionCr Si falla la búsqueda de metodo y modulo en la base de datos para la auditoria 
	 */
	public void anularApartado() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("anularApartado() - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "anularApartado");
			
		Sesion.setUbicacion("APARTADO","anularApartado");
			
		String autorizante = CR.me.verificarAutorizacion("APARTADO","anularApartado", this.apartado.getCliente());
		if (autorizante == null) {
			autorizante = Sesion.getUsuarioActivo().getNumFicha();
		}
		Auditoria.registrarAuditoria(autorizante, apartado.getNumServicio(), "Anulación de Apartado", "APARTADO", "anularApartado");
		
		if(this.apartado.getEstadoServicio() == Sesion.APARTADO_VENCIDO) {
			//Se llama a la pantalla de cargo por servicio con el cobro del cargo obligatorio
			//Esta cambia el estado del servicio al de anulado con cobro
			CargoPorServicio cps = new CargoPorServicio(true);
			MensajesVentanas.centrarVentanaDialogo(cps);
		} else {
			//se llama a la pantalla de cobro de cargo por servicio no obligatorio
			//Esta cambia el estado del servicio
			CargoPorServicio cps = new CargoPorServicio(false);
			MensajesVentanas.centrarVentanaDialogo(cps);
		}
			
		//this.apartado.anularApartado();
		/*try { CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ..."); }
		catch (Exception e) {
			logger.error("anularApartado()", e);
		}
		apartado = null;
				*/
		// Actualizamos el estado de la caja si se anuló correctamente el apartado
		if (apartado == null)
			Sesion.getCaja().setEstado(edoFinalCaja);
		else
		{
			Auditoria.registrarAuditoria(autorizante, apartado.getNumServicio(), "Cancelada anulación de Apartado", "APARTADO", "anularApartado");
			throw new AnulacionExcepcion ("Cancelada anulación de apartado");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("anularApartado() - end");
		}
	}

	/**
		 * Método asignarCliente.
		 * 		Asigna el cliente indicado al Apartado. Verifica posible autorización que se requiera 
		 * 		para la realización de esta función.
		 * @param codigoBarra Codigo de Barra del Cliente.
		 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
		 * @throws ConexionExcepcion Si ocurre un error de acceso a la base de datos
		 * @throws MaquinaDeEstadoExcepcion Si no se están en el estado correcto
		 * @throws ExcepcionCr Si falla la búsqueda de metodo y modulo en la base de datos para la auditoria 
		 */
		public void asignarCliente(String codigo) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
			if (logger.isDebugEnabled()) {
				logger.debug("asignarCliente(String) - start");
			}

			String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "asignarCliente");
			boolean existiaApartadoActivo = (apartado != null) ? true : false;
	
			if (!existiaApartadoActivo)
				this.crearApartado("");

			//Asignamos el cliente al servicio
			String autorizante = CR.me.verificarAutorizacion ("APARTADO","asignarCliente");
		
			if(apartado.getCliente().getCodCliente() != null)
				if(!apartado.getCliente().getCodCliente().equals(codigo))
					CR.me.borrarAvisos();
			
				apartado.asignarCliente(codigo, autorizante);
			
				
			//Se verifica si el cliente se encuentra inactivo. Unicamente se muestra un mensaje para indicar la eventualidad
			if(apartado.getCliente().getEstadoCliente() != Sesion.ACTIVO)
				CR.me.mostrarAviso("El cliente se encuentra inactivo", true);
				
			try { CR.crVisor.enviarString("CLIENTE", 0, apartado.getCliente().getNombreCompleto(), 2); }
			catch (Exception e) {
				logger.error("asignarCliente(String)", e);
	}
				
			// Actualizamos el estado de la caja
			Sesion.getCaja().setEstado(edoFinalCaja);

			//Ahora verificamos si el cliente es exento de impuesto
			this.facturarSinImpuesto();

			if (logger.isDebugEnabled()) {
				logger.debug("asignarCliente(String) - end");
			}
		}





	/**
	 * Método asignarCliente.
	 * 		Asigna el cliente indicado al Apartado. Verifica posible autorización que se requiera 
	 * 		para la realización de esta función.
	 * @param codigoBarra Codigo de Barra del Cliente.
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws ConexionExcepcion Si ocurre un error de acceso a la base de datos
	 * @throws MaquinaDeEstadoExcepcion Si no se están en el estado correcto
	 * @throws ExcepcionCr Si falla la búsqueda de metodo y modulo en la base de datos para la auditoria 
	 */
	public void asignarCliente(String nombre, String apellido, String id, String telf, String codArea, 
							   String direccion, String telef2, String codArea2, String email, char tipoCliente, boolean contactar,char sexo,char estadoCivil,Date fechaNaci,String zonaResidencial) 
							   throws ClienteExcepcion, UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("asignarCliente(String) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "asignarCliente");
		boolean existiaApartadoActivo = (apartado != null) ? true : false;
	
		if (!existiaApartadoActivo)
			this.crearApartado("");

		//Asignamos el cliente al servicio
		String autorizante = CR.me.verificarAutorizacion ("APARTADO","asignarCliente");
		
		if(apartado.getCliente().getCodCliente() != null)
			if(!apartado.getCliente().getCodCliente().equals(id))
				CR.me.borrarAvisos();
		
//				****** Fecha Actualización 07/02/2007	
	  //Cambio de BECO para agregar validaciones de RIF y CI para el SENIAT
	  //*******
	 	Validaciones validador = new Validaciones();
		if(!validador.validarRifCedula(id, tipoCliente)) {
			throw (new ClienteExcepcion("CI/RIF Inválido"));
		}
		 //**************************		
				
				
		Cliente clienteTemp = new Cliente(id, tipoCliente, nombre, apellido, direccion, codArea, telf,'A', codArea2, telef2, email, contactar,sexo,estadoCivil,fechaNaci,zonaResidencial);
		BaseDeDatosServicio.registrarClienteTemporal(clienteTemp);
		apartado.asignarCliente(tipoCliente + "-" + id, autorizante);
		
		//Se verifica si el cliente se encuentra inactivo. Unicamente se muestra un mensaje para indicar la eventualidad
		if(apartado.getCliente().getEstadoCliente() != Sesion.ACTIVO)
			CR.me.mostrarAviso("El cliente se encuentra inactivo", true);
				
		try { CR.crVisor.enviarString("CLIENTE", 0, apartado.getCliente().getNombreCompleto(), 2); }
		catch (Exception e) {
			logger.error("asignarCliente(String)", e);
}
				
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		//Ahora verificamos si el cliente es exento de impuesto
		this.facturarSinImpuesto();

		if (logger.isDebugEnabled()) {
			logger.debug("asignarCliente(String) - end");
		}
	}
	
	/**
	 * Método registrarAfiliado.
	 * 		Asigna el cliente indicado al Apartado. Verifica posible autorización que se requiera 
	 * 		para la realización de esta función.
	 * @param codigoBarra Codigo de Barra del Cliente.
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws ConexionExcepcion Si ocurre un error de acceso a la base de datos
	 * @throws MaquinaDeEstadoExcepcion Si no se están en el estado correcto
	 * @throws ExcepcionCr Si falla la búsqueda de metodo y modulo en la base de datos para la auditoria 
	 */
	public void registrarAfiliado(String nombre, String apellido, String id, String telf, String codArea, 
							   String direccion, String telef2, String codArea2, String email, char tipoCliente, boolean contactar,char sexo, char estadoCivil, Date fechaNaci,String zonaResidencial) 
							   throws MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("asignarCliente(String) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "registrarAfiliado");
		
		Cliente clienteTemp = new Cliente(id, tipoCliente, nombre, apellido, direccion, codArea, telf,'A', codArea2, telef2, email, contactar,sexo,estadoCivil,fechaNaci,zonaResidencial);
		BaseDeDatosServicio.registrarClienteTemporal(clienteTemp);
		
//		Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("asignarCliente(String) - end");
		}
	}

	/**
	 * Método facturarSinImpuesto.
	 * 		Verifica si el cliente es exento de impuesto. Si es así setea seprocesa el apartado exento
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws ConexionExcepcion Si ocurre un error de acceso a la base de datos
	 * @throws MaquinaDeEstadoExcepcion Si no se están en el estado correcto
	 * @throws ExcepcionCr Si falla la búsqueda de metodo y modulo en la base de datos para la auditoria 
	 */
	private void facturarSinImpuesto() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("facturarSinImpuesto() - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "facturarSinImpuesto");
				
		if(apartado.getCliente().getCodCliente() != null && (apartado.getCliente().isExento())) {
			//Verificamos la autorización de la función
			CR.me.verificarAutorizacion ("APARTADO","facturarSinImpuesto", this.apartado.getCliente());
			Auditoria.registrarAuditoria("Facturando Apartado " + this.apartado.getNumServicio() + " exento",'T');
			apartado.setApartadoExento(true);
		} else {
			apartado.setApartadoExento(false);
		}
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("facturarSinImpuesto() - end");
		}
	}

	/**
	 * Método crearApartado.
	 * 		Crea una nueva instancia de Apartado
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws ConexionExcepcion Si ocurre un error de acceso a la base de datos
	 * @throws MaquinaDeEstadoExcepcion Si no se están en el estado correcto
	 * @throws ExcepcionCr Si falla la búsqueda de metodo y modulo en la base de datos para la auditoria 
	 */
	/*public void crearApartado() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("crearApartado() - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "crearApartado");
		esto ya no va
		Sesion.setUbicacion("APARTADO","crearApartado");
		
		if (!Sesion.isCajaEnLinea()) throw new LineaExcepcion("La caja se encuentra Fuera de Linea\nNo se pueden registrar Apartados");
		
		CR.me.verificarAutorizacion("APARTADO","crearApartado");
		
		apartado = new Apartado();
		
		try { CR.crVisor.enviarString("APARTADO/P. ESPECIAL"); }
		catch (Exception e) {
			logger.warn("crearApartado()", e);
		}
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("crearApartado() - end");
		}
	}*/
	
	/**
	 * Método crearApartado.
	 * 		Crea una nueva instancia de Apartado desde una venta que fue colocada en espera (codFactEspera)
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws ConexionExcepcion Si ocurre un error de acceso a la base de datos
	 * @throws MaquinaDeEstadoExcepcion Si no se están en el estado correcto
	 * @throws ExcepcionCr Si falla la búsqueda de metodo y modulo en la base de datos para la auditoria 
	 */
	//IROJAS 03/05/2010. Optimización de CR para permitir recuperación de ventas en espera para apartados
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unchecked")
	public void crearApartado(String codFactEspera) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("crearApartado() - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "crearApartado");
		
		Sesion.setUbicacion("APARTADO","crearApartado");
		
		if (!Sesion.isCajaEnLinea()) throw new LineaExcepcion("La caja se encuentra Fuera de Linea\nNo se pueden registrar Apartados");
		
		CR.me.verificarAutorizacion("APARTADO","crearApartado");
		
		this.apartado = new Apartado();
		
		
		if (codFactEspera != null && !codFactEspera.trim().equals("")) {
			//**********  SE OBTIENE LA VENTA EN ESPERA
			//Se recupera la venta en espera
			Vector<Vector<?>> ventaObtenida = new Vector<Vector<?>>();
			try {
				ventaObtenida = BaseDeDatosVenta.recuperarDeEspera(codFactEspera);
			} catch (ExcepcionCr e1) {
				apartado = null;
				throw  e1;
			}  catch (Exception e2) {
				apartado = null;
				throw (new ExcepcionCr(e2.getMessage()));
			}
		
			Vector<Object> cabeceraVenta = (Vector<Object>)ventaObtenida.elementAt(0);
			Vector<Vector<Object>> detallesVenta = (Vector<Vector<Object>>)ventaObtenida.elementAt(1);
			
			// Actualizamos el estado de la caja para poder ingersar productos y clientes al apartado
			Sesion.getCaja().setEstado(edoFinalCaja);
			
			int codTienda = ((Integer)cabeceraVenta.elementAt(0)).intValue();
			Date fechaTrans = Sesion.getFechaSistema();
			int numCajaInicia = ((Integer)cabeceraVenta.elementAt(1)).intValue();
			int numRegCajaInicia = ((Integer)cabeceraVenta.elementAt(2)).intValue();
			
			// Obtenemos el cliente si es distinto de nulo
			if (cabeceraVenta.elementAt(5) != null) {
				try {
					this.apartado.setCliente(MediadorBD.obtenerCliente((String)cabeceraVenta.elementAt(5)));
				} catch (ExcepcionCr e) {
					logger.error("Apartado(String)", e);
					this.apartado.setCliente(new Cliente());
				} catch (ConexionExcepcion e) {
					logger.error("Apartado(String)", e);
					this.apartado.setCliente(new Cliente());
				}
			}
			
			
			//Chequeamos que el cliente no sea el usuario activo
			if ((this.apartado.getCliente().getNumFicha()!=null) && Sesion.getUsuarioActivo().getNumFicha().equalsIgnoreCase(this.apartado.getCliente().getNumFicha())){
				apartado = null;
				throw new AfiliadoUsrExcepcion("La venta posee como cliente al Usuario Activo.\nNo se pudo recuperar la transacción");
			}
			
			// Armamos el detalle del apartado
			for (int i=0; i<detallesVenta.size(); i++) {
				Vector<Object> detalleVenta = detallesVenta.elementAt(i);
				String codProd = (String)detalleVenta.elementAt(0);
				float cant = ((Float)detalleVenta.elementAt(2)).floatValue();
				this.ingresarProductoApartado(codProd, Sesion.capturaTeclado);
				if (cant > 1)
					this.cambiarCantidad(cant-1);
			}
			BaseDeDatosVenta.borrarFacturaEspera(codFactEspera, numCajaInicia, codTienda, numRegCajaInicia, fechaTrans);
			//**********  
		} else {
			// Actualizamos el estado de la caja
			Sesion.getCaja().setEstado(edoFinalCaja);
		}
		
		
		try { CR.crVisor.enviarString("APARTADO/P. ESPECIAL"); }
		catch (Exception e) {
			logger.warn("crearApartado()", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("crearApartado() - end");
		}
	}
	
	/**
	 * Método recuperarCotizacion.
	 * 		Recupera una cotizacion
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws ConexionExcepcion Si ocurre un error de acceso a la base de datos
	 * @throws MaquinaDeEstadoExcepcion Si no se están en el estado correcto
	 * @throws ExcepcionCr Si falla la búsqueda de metodo y modulo en la base de datos para la auditoria 
	 */
	public void recuperarCotizacion(int tda, int numServicio, Date fechaServ) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("recuperarCotizacion(int, int, Date) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "recuperarCotizacion");
		
		Sesion.setUbicacion("COTIZACION","recuperarCotizacion");
		
		Auditoria.registrarAuditoria("Recuperando cotización " + numServicio,'T');
		
		cotizacion = new Cotizacion(tda, numServicio, fechaServ);
		
		try {
			CR.me.verificarAutorizacion("COTIZACION","recuperarCotizacion", this.cotizacion.getCliente());
		} catch (ConexionExcepcion e) {
			logger.error("recuperarCotizacion(int, int, Date)", e);

			cotizacion = null;
			throw e;
		} catch (ExcepcionCr e) {
			logger.error("recuperarCotizacion(int, int, Date)", e);

			cotizacion = null;
			if (e.getMensaje().indexOf("cliente")!=-1) {
				e.setMensaje("La cotización posee como cliente al Usuario Autorizante.\nNo se pudo recuperar la Cotización");
			}
			throw e;
		}
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);
		
		if(cotizacion.getCliente().getCodCliente() != null) {
			if (cotizacion.getEstadoServicio()==Sesion.COTIZACION_ACTIVA) {
				try {
					CR.meVenta.asignarCliente(cotizacion.getCliente().getCodCliente());
				} catch (ExcepcionCr e1) {
					logger.error("recuperarCotizacion(int, int, Date)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
				} catch (ConexionExcepcion e1) {
					logger.error("recuperarCotizacion(int, int, Date)", e1);

					MensajesVentanas.mensajeError(e1.getMensaje());
				}
			} else {
				if (cotizacion.getEstadoServicio()==Sesion.COTIZACION_FACTURADA) {
					CR.meVenta.asignarCliente(cotizacion.getCliente());
				}
			}
			try { CR.crVisor.enviarString("COTIZACION: " + cotizacion.getNumServicio(), 0, cotizacion.getCliente().getNombreCompleto(), 2); }
			catch (Exception e) {
				logger.error("recuperarCotizacion(int, int, Date)", e);
}
		} else {
			CR.meVenta.asignarCliente((Cliente)null);
			try { CR.crVisor.enviarString("COTIZACION: " + cotizacion.getNumServicio(), 0, "", 2); }
			catch (Exception e) {
				logger.error("recuperarCotizacion(int, int, Date)", e);
}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("recuperarCotizacion(int, int, Date) - end");
		}
	}
	/**
	 * Método obtenerCotizaciones.
	 * 		Obtiene todas las cotizaciones existentes para un indicador
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws ConexionExcepcion Si ocurre un error de acceso a la base de datos
	 * @throws MaquinaDeEstadoExcepcion Si no se están en el estado correcto
	 * @throws ExcepcionCr Si falla la búsqueda de metodo y modulo en la base de datos para la auditoria 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Cotizacion> obtenerCotizaciones(String identificador) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCotizaciones(String) - start");
		}

		Sesion.setUbicacion("COTIZACION","recuperarCotizacion");
		Auditoria.registrarAuditoria("Buscando Cotizaciones con identificador " + identificador,'T');
		Vector<Cotizacion> returnVector = BaseDeDatosServicio
				.obtenerCotizaciones(identificador);
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCotizaciones(String) - end");
		}
		return returnVector;
	}

	/**
	 * Método finalizarConsultaCotizacion.
	 * 	Finaliza la consulta de una Cotizacion
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws ConexionExcepcion Si ocurre un error de acceso a la base de datos
	 * @throws MaquinaDeEstadoExcepcion Si no se están en el estado correcto
	 * @throws ExcepcionCr Si falla la búsqueda de metodo y modulo en la base de datos para la auditoria 
	 */
	public void finalizarConsultaCotizacion() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("finalizarConsultaCotizacion() - start");
		}


		//Desbloquear cotizacion
		BaseDeDatosServicio.desbloquearCotizacion(CR.meServ.getCotizacion().getNumServicio());
		CR.me.verificarAutorizacion ("COTIZACION","finalizarConsultaCotizacion", this.cotizacion.getCliente());
				
		Sesion.setUbicacion("COTIZACION", "finalizarConsultaCotizacion");
		Auditoria.registrarAuditoria("Finalizada consulta de Cotizacion " + this.cotizacion.getNumServicio(),'T');
		this.cotizacion = null;
		
		if (CR.meVenta.getVenta()!=null) {
			CR.meVenta.anularVentaActiva();
		} else {
			String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "finalizarConsultaCotizacion");
			// Actualizamos el estado de la caja
			Sesion.getCaja().setEstado(edoFinalCaja);
		}
		
		
		try { CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ..."); }
		catch (Exception e) {
			logger.error("finalizarConsultaCotizacion()", e);
}

		if (logger.isDebugEnabled()) {
			logger.debug("finalizarConsultaCotizacion() - end");
		}
	}
	/**
	 * Método getCotizacion
	 * 
	 * @return
	 * Cotizacion
	 */
	public Cotizacion getCotizacion() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCotizacion() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getCotizacion() - end");
		}
		return cotizacion;
	}

	/**
	 * Método facturarCotizacion.
	 */
	public void facturarCotizacion() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("facturarCotizacion() - start");
		}
		
		// Chequeamos que se puede finalizar la venta (Pagos completos)
		// CR.me.verificarAutorizacion ("FACTURACION","crearVentaApartado");
		// Realizamos el pago de la cotizacion. Sino se hace el pago completo no se factura el servicio
		cotizacion.facturarCotizacion();
		cotizacion = null;
		try { CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ..."); }
		catch (Exception e) {
			logger.error("facturarCotizacion()", e);
}

		if (logger.isDebugEnabled()) {
			logger.debug("facturarCotizacion() - end");
		}
	}
	
	/**
	 * @param caso - Tipo de chequeo de recalculo
	 * 1 - Chequeo de Promociones Vigentes Nuevas
	 * 2 - Chequeo de Cambio de IVA
	 * @return boolean - True si se recalcula el saldo, False en caso contrario
	 */
	public boolean recalculadoSaldoApartado(int caso) {
		switch (caso) {
			case 1:	
				return this.apartado.recalculadoPromocionesApartado();
			case 2: 
				return this.apartado.recalculadoImpuestoApartado();
				
			//Caso agregado por centrobeco para modulo de promociones
			case 3:
				return this.apartado.recalculadoPromocionesNuevas();
			default: 
				return false;
		}
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

		apartado.reversarCalculoApartado();

		if (logger.isDebugEnabled()) {
			logger.debug("reversarCalculoApartado() - end");
		}
	}
	/**
	 * Método actualizarCondicionAbono
	 * 
	 * 
	 * void
	 */
	public void actualizarCondicionAbono(char condAbono) throws BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarCondicionAbono(char) - start");
		}

		BaseDeDatosServicio.actualizarCondicionAbono(this.getApartado(), condAbono, false);

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarCondicionAbono(char) - end");
		}
	}
	/**
	 * Método exonerarCargoPorServicio
	 * 
	 * 
	 * void
	 */
	public void exonerarCargoPorServicio() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("exonerarCargoPorServicio() - start");
		}
		
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "exonerarCargoPorServicio");
		
		CR.me.verificarAutorizacion ("APARTADO","exonerarCargoPorServicio", this.apartado.getCliente());
		this.apartado.setEstadoServicio(Sesion.APARTADO_ANULADO_EXONERADO);
		Auditoria.registrarAuditoria("Exonerado cobro de cargo por servicio para el apartado nro. " + this.apartado.getNumServicio(),'T');
		
		//Actualizamos el estado de la caja
		 Sesion.getCaja().setEstado(edoFinalCaja);	

		if (logger.isDebugEnabled()) {
			logger.debug("exonerarCargoPorServicio() - end");
		}
	}
	/**
	 * Método cobroCargoPorServicio
	 * 
	 * 
	 * void
	 */
	public void cobroCargoPorServicio() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("cobroCargoPorServicio() - start");
		}
		
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "cobroCargoPorServicio");
		
		CR.me.verificarAutorizacion ("APARTADO","cobroCargoPorServicio", this.apartado.getCliente());
		this.apartado.setEstadoServicio(Sesion.APARTADO_ANULADO_CON_CARGO);
		Auditoria.registrarAuditoria("Cobro de cargo por servicio para el apartado nro. " + this.apartado.getNumServicio(),'T');
		
		//Actualizamos el estado de la caja
		 Sesion.getCaja().setEstado(edoFinalCaja);	

		if (logger.isDebugEnabled()) {
			logger.debug("cobroCargoPorServicio() - end");
		}
	}
	
	public void iniciarMantenimiento() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr
	{
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "iniciarMantenimiento");
		CR.me.verificarAutorizacion("MANTENIMIENTO","iniciarMantenimiento");
		Sesion.getCaja().setEstado(edoFinalCaja);
	}
	
	/**
	 * Método crearListaRegalos.
	 * 		Crea una nueva instancia de Lista de Regalos
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws ConexionExcepcion Si ocurre un error de acceso a la base de datos
	 * @throws MaquinaDeEstadoExcepcion Si no se están en el estado correcto
	 * @throws ExcepcionCr Si falla la búsqueda de metodo y modulo en la base de datos para la auditoria 
	 */
	public void crearListaRegalos() throws UsuarioExcepcion,MaquinaDeEstadoExcepcion,ConexionExcepcion,ExcepcionCr {
		
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(),"crearListaRegalos");

		Sesion.setUbicacion("LISTA DE REGALOS", "crearListaRegalos");

		if (!Sesion.isCajaEnLinea())
			throw new LineaExcepcion("La caja se encuentra Fuera de Linea\nNo se pueden crear Listas de Regalos");

		CR.me.verificarAutorizacion("LISTA DE REGALOS","crearListaRegalos");

		listaRegalos = new ListaRegalos();

		try {
			CR.crVisor.enviarString("LISTA DE REGALOS");
		} catch (Exception e) {
			logger.error("crearListaRegalos()", e);
		}

		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);
	}

	/**
	 * Método cargarListaRegalos.
	 * 		Crea una nueva instancia de Lista de Regalos recuperando una lista existente
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws ConexionExcepcion Si ocurre un error de acceso a la base de datos
	 * @throws MaquinaDeEstadoExcepcion Si no se están en el estado correcto
	 * @throws ExcepcionCr Si falla la búsqueda de metodo y modulo en la base de datos para la auditoria 
	 */
	public void cargarListaRegalos(String codLista) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ExcepcionCr, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarListaRegalos(String) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(),"cargarListaRegalos");

		if (!Sesion.isCajaEnLinea())
			throw new LineaExcepcion("La caja se encuentra Fuera de Linea\nNo se pueden crear Listas de Regalos");
		CR.me.verificarAutorizacion("LISTA DE REGALOS", "cargarListaRegalos");

		listaRegalos = new ListaRegalos(codLista);

		try {
			CR.crVisor.enviarString("LISTA DE REGALOS", listaRegalos.getTitular().getNombreCompleto());
		} catch (Exception e){
			logger.warn("cargarListaRegalos(String)", e);
		}

		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);
	}
	
	/**
	 * Método cargarListaRegalos.
	 * 		Crea una nueva instancia de Lista de Regalos
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	public void cargarListaRegalos() throws UsuarioExcepcion,MaquinaDeEstadoExcepcion,ConexionExcepcion,ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarListaRegalos(String) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(),"cargarListaRegalos");

		CR.me.verificarAutorizacion("LISTA DE REGALOS", "cargarListaRegalos");
		try {
			CR.crVisor.enviarString("LISTA DE REGALOS", listaRegalos.getTitular().getNombreCompleto());
			//CR.crVisor.enviarString("LISTA DE REGALOS");
		} catch (Exception e){
			listaRegalos = null;
			logger.error("cargarListaRegalos(String)", e);
		}

		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);
	}

	/**
	 * Método getListaRegalos.
	 * 		Obtiene la Lista de Regalos Activa.
	 * @return Lista de Regalos - Objeto Lista de Regalos que se está utilizando.
	 */
	public ListaRegalos getListaRegalos() {
		if (logger.isDebugEnabled()) {
			logger.debug("getListaRegalos() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getListaRegalos() - end");
		}
		return listaRegalos;
	}
	
	/**
	 * Método getListaRegalosRespaldo.
	 * 		Obtiene el respaldo de la Lista de Regalos que está siendo modificada.
	 * 		Devuelve null si no se está modificando una lista. 
	 * @return Lista de Regalos - Objeto Lista de Regalos que se está utilizando.
	 */
	public ListaRegalos getListaRegalosRespaldo() {
		if (logger.isDebugEnabled()) {
			logger.debug("getListaRegalos() - start");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getListaRegalos() - end");
		}
		return listaRegalosRespaldo;
	}
	
	/**
	 * Método ingresarProductoLR.
	 * 		Ingresa un nuevo producto a la Lista de Regalos
	 * @param codP Código de Producto.
	 * @param tipoC Tipo de Captura de Producto (Teclado, Escáner).
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws ProductoExcepcion Si el producto no se encuentra en la Base de Datos.
	 * @throws MaquinaDeEstadoExcepcion Si el estado de la caja es incorrecto para ingreso de Producto.
	 * @throws XmlExcepcion Si ocurre un error registrando la Auditoría Xml.
	 * @throws FuncionExcepcion Si la función no se encuentra en la Base de Datos.
	 * @throws BaseDeDatosExcepcion Si ocurre un error con el acceso (sentencias) a la Base de Datos.
	 * @throws ConexionExcepcion Si ocurre un error de conexión a la Base de Datos.
	 * @throws ExcepcionCr Si se presenta un error de autorización y/o validación de usuario.
	 */
	public void ingresarProductoLR(String codP, String tipoC) throws UsuarioExcepcion, ProductoExcepcion, MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("ingresarProductoLR(String, String) - start");
		}
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "ingresarProductoLR");		
		
		CR.me.verificarAutorizacion("LISTA DE REGALOS","ingresarProductoLR",this.listaRegalos.getCliente());

		listaRegalos.ingresarLineaProducto(codP, tipoC);
		try {
			CR.crVisor.enviarString(((DetalleServicio)listaRegalos.getDetallesServicio().lastElement()).getProducto().getDescripcionCorta(),
										0,df.format(((DetalleServicio)listaRegalos.getDetallesServicio().lastElement()).getPrecioFinal()
										+((DetalleServicio)listaRegalos.getDetallesServicio().lastElement()).getMontoImpuesto()),2);
		}catch(Exception e) {
			logger.warn("ingresarProductoLR(String, String)", e);
		}
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("ingresarProductoLR(String, String) - end");
		}
	}
	
	/**
	 * Method registrarListaRegalos.
	 * 		Registra una nueva Lista de Regalos en la Base de Datos.
	 * @param tipoAbonoInicial Tipo del Abono Inicial (30%, 25%, 50%, etc.).
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws MaquinaDeEstadoExcepcion Si el estado de la caja es incorrecto para el registro de Apartados/Pedidos Especiales.
	 * @throws ConexionExcepcion Si ocurre un error con el acceso a la Base de Datos.
	 * @throws ExcepcionCr Si no existe apartado activo o si la Tienda no permite realizar esta operación.
	 */
	public void registrarListaRegalos() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, BaseDeDatosExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("registrarListaRegalos(char) - start");
		}
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "registrarListaRegalos");
		CR.me.verificarAutorizacion("LISTA DE REGALOS","registrarListaRegalos",this.listaRegalos.getCliente());
		
		Sesion.setUbicacion("LISTA DE REGALOS","registrarListaRegalos");
		
		if (listaRegalos.getDetallesServicio().size()<=0) {
			throw (new MaquinaDeEstadoExcepcion("No puede crear la lista de regalos. No existen detalles."));
		}
		
		if(listaRegalos.getCliente().getCodCliente() == null) {
			throw (new MaquinaDeEstadoExcepcion("No puede crear la lista de regalos. Debe ingresar el afiliado."));
		}
		
		listaRegalos.registrarListaRegalos();
		
		try {
			CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ...");
		}
		catch (Exception e) {
			logger.error("registrarListaRegalos(char)", e);
		}
		//listaRegalos = null;

		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("registrarListaRegalos(char) - end");
		}
	}
	
	/**
	 * Método cambiarCantidad.
	 * 	Cambia la cantidad de un renglón
	 * @param cantidad Cantidad a agregar al renglon
	 * @param renglon Renglon a modificar la cantidad
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws ConexionExcepcion Si ocurre un error de acceso a la base de datos
	 * @throws ProductoExcepcion Si el renglón es inválido
	 * @throws MaquinaDeEstadoExcepcion Si no se están en el estado correcto
	 * @throws ExcepcionCr Si falla la búsqueda de metodo y modulo en la base de datos para la auditoria 
	 */
	public void cambiarCantidadLR(float cantidad, int renglon) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "cambiarCantidadLR");

		Sesion.setUbicacion("LISTA DE REGALOS", "cambiarCantidadLR");
		
		CR.me.verificarAutorizacion ("LISTA DE REGALOS","cambiarCantidadLR", this.listaRegalos.getCliente());

		this.listaRegalos.agregarCantidad(cantidad, renglon);
		
		//Se envía al Visor la multiplicación de la cantidad agregada por el precio del producto
		String precio = df.format(((DetalleServicio)listaRegalos.getDetallesServicio().lastElement()).getPrecioFinal() + ((DetalleServicio)listaRegalos.getDetallesServicio().lastElement()).getMontoImpuesto());
		String cantXprecio = df.format(cantidad) + " X " +  precio;
		try { CR.crVisor.enviarString(((DetalleServicio)listaRegalos.getDetallesServicio().lastElement()).getProducto().getDescripcionCorta(), 0, cantXprecio, 2); }
		catch (Exception e) {
			logger.error("cambiarCantidad(float, int)", e);
		}

		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("cambiarCantidad(float, int) - end");
		}
	}
	
	/**
	 * Method cambiarCantidad.
	 * 	Cambia la cantidad del último renglon de Lista de Regalos
	 * @param cantidad Cantidad a agregar al renglon
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws MaquinaDeEstadoExcepcion Si el estado de la caja es incorrecto para cambios de cantidad.
	 * @throws ConexionExcepcion Si ocurre un error con el acceso a la Base de Datos.
	 * @throws ExcepcionCr Si no existe apartado activo o si la Tienda no permite realizar esta operación.
	 */
	public void cambiarCantidadLR(float cantidad) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "cambiarCantidadLR");

		Sesion.setUbicacion("LISTA DE REGALOS", "cambiarCantidadLR");
		
		CR.me.verificarAutorizacion ("LISTA DE REGALOS","cambiarCantidadLR", this.listaRegalos.getCliente());

		this.listaRegalos.agregarCantidad(cantidad, this.listaRegalos.getDetallesServicio().size()-1);
		
		//Se envía al Visor la multiplicación de la cantidad agregada por el precio del producto
		String precio = df.format(((DetalleServicio)listaRegalos.getDetallesServicio().lastElement()).getPrecioFinal() + ((DetalleServicio)listaRegalos.getDetallesServicio().lastElement()).getMontoImpuesto());
		String cantXprecio = df.format(cantidad) + " X " +  precio;
		try {
			CR.crVisor.enviarString(((DetalleServicio)listaRegalos.getDetallesServicio().lastElement()).getProducto().getDescripcionCorta(), 0, cantXprecio, 2); }
		catch (Exception e) {
			logger.error("cambiarCantidadLR(float)", e);
		}

		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);
	}
	
	/**
	 * Método anularProducto.
	 * 	Anula unproducto del Apartado
	 * @param renglon Renglon a modificar la cantidad
	 * @param cantidad Cantidad de productos a anular
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws ConexionExcepcion Si ocurre un error de acceso a la base de datos
	 * @throws ProductoExcepcion Si el renglón es inválido
	 * @throws MaquinaDeEstadoExcepcion Si no se están en el estado correcto
	 * @throws ExcepcionCr Si falla la búsqueda de metodo y modulo en la base de datos para la auditoria 
	 */
	public void anularProductoLR(int renglon, float cantidad) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("anularProductoLR(int, float) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "anularProducto");
		
		// Verificamos si la función a la que pertenece este método necesita autorización
		// Primero Verificamos el módulo que está activo
		if(listaRegalos != null) {
			CR.me.verificarAutorizacion ("LISTAREGALOS","anularProducto", this.listaRegalos.getCliente());
			Vector<Object> detProd = listaRegalos.anularProducto(renglon, cantidad);
			try {
				CR.crVisor.enviarString((String)detProd.elementAt(0), 0, "-" + df.format(cantidad) + " X " + df.format(((Double)detProd.elementAt(1)).doubleValue()), 2); }
			catch (Exception e) {
				logger.error("anularProductoLR(int, float)", e);
			}
		}
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("anularProducto(int, float) - end");
		}
	}
	
	/**
	 * Método anularListaRegalosActiva.
	 * 		
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws MaquinaDeEstadoExcepcion Si el estado de la caja es incorrecto para anular un apartado.
	 * @throws ConexionExcepcion Si ocurre un error con el acceso a la Base de Datos.
	 * @throws ExcepcionCr Si no existe apartado activo o si la Tienda no permite realizar esta operación.
	 */
	public void finalizarListaRegalos() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "cancelarListaRegalos");

		Sesion.setUbicacion("LISTA DE REGALOS","cancelarListaRegalos");
		
		//CR.me.verificarAutorizacion("LISTA DE REGALOS","cancelarListaRegalos", this.listaRegalos.getCliente());
		
		try {
			CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ..."); }
		catch(Exception e){
			//logger.error("cancelarListaRegalos()", e);
		}

		this.listaRegalos = null;

		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);
	}
	
	
	/**
	 * Método anularListaRegalosActiva.
	 * 		
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws MaquinaDeEstadoExcepcion Si el estado de la caja es incorrecto para anular un apartado.
	 * @throws ConexionExcepcion Si ocurre un error con el acceso a la Base de Datos.
	 * @throws ExcepcionCr Si no existe apartado activo o si la Tienda no permite realizar esta operación.
	 */
	public void cancelarCierreListaRegalos() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "cancelarListaRegalos");

		Sesion.setUbicacion("LISTA DE REGALOS","cancelarListaRegalos");
		
		//CR.me.verificarAutorizacion("LISTA DE REGALOS","cancelarListaRegalos", this.listaRegalos.getCliente());
		
		try {
			CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ..."); }
		catch(Exception e){
			logger.error("cancelarListaRegalos()", e);
		}
		ConexionServCentral.realizarSentencia("update CR.listaregaloscentral set estado='A' where codlista="+listaRegalos.getNumServicio());
	
		this.listaRegalos = null;

		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);
	}
	
	/**
	 * Método finalizarConsultaLR.
	 * 	Finaliza la consulta de una Lista de Regalos
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws ConexionExcepcion Si ocurre un error de acceso a la base de datos
	 * @throws MaquinaDeEstadoExcepcion Si no se están en el estado correcto
	 * @throws ExcepcionCr Si falla la búsqueda de metodo y modulo en la base de datos para la auditoria 
	 */
	public void finalizarConsultaLR() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "finalizarConsultaApartado");
		
		CR.me.verificarAutorizacion ("APARTADO","finalizarConsultaApartado", this.apartado.getCliente());
				
		Sesion.setUbicacion("APARTADO", "finalizarConsultaApartado");
		Auditoria.registrarAuditoria("Finalizada consulta de Lista de Regalos " + this.listaRegalos.getCodTienda(),'T');
		try { CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ..."); }
		catch (Exception e) {
			logger.error("finalizarConsultaApartado()", e);
		}
		this.listaRegalos = null;
		
		// Actualizamos el estado de la caja
		 Sesion.getCaja().setEstado(edoFinalCaja);

	}

	/**
	 * Método registrarAbonosLR.
	 * 		Registra y/o actualiza los abonos de una Lista de Regalos
	 * @throws UsuarioExcepcion Si el usuario activo y/o autorizante no puede realizar esta operación entre sus funciones.
	 * @throws MaquinaDeEstadoExcepcion Si el estado de la caja es incorrecto para el registro/Actualización de Abonos de Apartados/Pedidos Especiales.
	 * @throws ConexionExcepcion Si ocurre un error con el acceso a la Base de Datos.
	 * @throws ExcepcionCr Si no existe apartado activo o si la Tienda no permite realizar esta operación.
	 */
	public void registrarAbonosLR(double vuelto,String nomInvitado,String dedicatoria) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("registrarAbonosLR() - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "registrarAbonosLR");
		
		Sesion.setUbicacion("LISTA DE REGALOS","registrarAbonosLR");
		
		CR.me.verificarAutorizacion("LISTA DE REGALOS","registrarAbonosLR", this.listaRegalos.getCliente());

		if(listaRegalos.getEstadoLista().equals("B"))
			throw new ExcepcionLR("La lista ya se encuentra en proceso de cierre.\nNo se permiten más abonos.");
		else if(listaRegalos.getEstadoLista().equals("C"))
			throw new ExcepcionLR("La lista ya fue cerrada.\nNo se permiten más abonos.");
		else if(!listaRegalos.getEstadoLista().equals("A"))
			throw new ExcepcionLR("La lista no está activa.\nNo se permiten abonos.");
	
		listaRegalos.finalizarAbonos(vuelto,nomInvitado,dedicatoria);
		
		
		//18/04/11 ACTUALIZACION PROYECTO BONO REGALO
		//Aplicar promociones asociadas al pago con bono regalo
		//jgraterol
		Cliente cliente;
		try{
			 cliente = CR.meServ.getApartado().getCliente();
		} catch(NullPointerException np){
			cliente = null;
		}
		CR.meServ.aplicarPromocionesBR(listaRegalos.getPagosAbono(), cliente); //OJO revisar si estos son los abonos
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("registrarAbonosLR() - end");
		}
	}


	/**
	 * Ingresa un nuevo abono a un producto de la lista de regalos
	 * 
	 * @param montoAbono
	 * @param renglon
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	public void abonarListaRegalos(double montoAbono, int renglon) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr{
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "abonarListaRegalos");
		
		CR.me.verificarAutorizacion("LISTA DE REGALOS","abonarListaRegalos", this.listaRegalos.getCliente());
		
		this.listaRegalos.agregarAbono(montoAbono,renglon);
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);
	}
	
	/**
	 * Ingresa un nuevo abono a un producto de la lista de regalos
	 * 
	 * @param cantidad
	 * @param renglon
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	public void abonarListaRegalos(int cantidad, int renglon) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr{
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "abonarListaRegalos");
		
		CR.me.verificarAutorizacion("LISTA DE REGALOS","abonarListaRegalos", this.listaRegalos.getCliente());
		
		this.listaRegalos.agregarAbono(cantidad,renglon);
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);
	}
	
	/**
	 * Ingresa un nuevo abono a la lista de regalos
	 * 
	 * @param montoAbono
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	public void abonarListaRegalos(double montoAbono) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr{
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "abonarListaRegalos");
		
		CR.me.verificarAutorizacion("LISTA DE REGALOS","abonarListaRegalos", this.listaRegalos.getCliente());
		
		this.listaRegalos.agregarAbono(montoAbono);
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);
	}
	
	/**
	 * Elimina los abonos actuales realizados a la lista de regalos
	 * 
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
//	public void eliminarAbonosLR() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr{
//		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "eliminarAbonosLR");
//		
//		CR.me.verificarAutorizacion("LISTA DE REGALOS","eliminarAbonosLR", this.listaRegalos.getCliente());
//		
//		this.listaRegalos.eliminarAbonos();
//		
//		// Actualizamos el estado de la caja
//		Sesion.getCaja().setEstado(edoFinalCaja);
//	}
	
	/**
	 * Método buscarClienteTemporal
	 * 		Busca el cliente indicado y lo retorna para validar su existencia
	 * @param ci
	 * @return Cliente
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	public Cliente buscarClienteTemporal(String ci) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("buscarClienteTemporal(String) - start");
		}
		Cliente clienteTemp = null;	
		try {
			clienteTemp = MediadorBD.obtenerCliente(ci);

		} catch (ClienteExcepcion ce) {
			clienteTemp = null;	
			throw ce;
		} catch (ExcepcionCr e1) {

			clienteTemp = null;	

		} catch (ConexionExcepcion e1) {
				logger.error("asignarCliente(String)", e1);
				throw e1;
		}

	
		//Se registra la auditoria
		Auditoria.registrarAuditoria("Busqueda de posible Cliente No afiliado en la BD", 'T');
	
		// Actualizamos el estado de la caja

	
		if (logger.isDebugEnabled()) {
			logger.debug("buscarClienteTemporal(String) - end");
		}
		return clienteTemp;
	}
	
	/**
	 * Asigna un cliente a la lista de regalos activa
	 * 
	 * @param codigo
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	public void asignarClienteLR(String codigo) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr{
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "asignarCliente");
		
		CR.me.verificarAutorizacion("LISTA DE REGALOS","asignarCliente", this.listaRegalos.getCliente());
		
		//TODO Modificar Maquina de Estado para verificar que esto funciona
//		
//		if(Sesion.getCaja().getEstado().equals("21") && CR.meVenta.getVenta()!=null)
//			CR.meVenta.asignarCliente(codigo,Sesion.isIndicaDesctoEmpleado());
//		else
			this.listaRegalos.asignarCliente(codigo,"");
		
		//		Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);
	}
	
	/**
	 * Asigna un cliente a la lista de regalos activa como titular de la misma
	 * 
	 * @param codigo
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	public void asignarTitularLR(String codigo) throws ClienteExcepcion, UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr{
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "asignarCliente");
		
		CR.me.verificarAutorizacion("LISTA DE REGALOS","asignarCliente", this.listaRegalos.getCliente());
		this.listaRegalos.asignarTitular(codigo,"");
		
		//		Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);
	}
	
	/**
	 * Asigna un nuevo cliente a la lista de regalos activa
	 * 
	 * @param nombre
	 * @param apellido
	 * @param id
	 * @param telf
	 * @param codArea
	 * @param direccion
	 * @param telef2
	 * @param codArea2
	 * @param email
	 * @param tipoCliente
	 * @param contactar
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	public void asignarClienteLR(String nombre, String apellido, String id, String telf, String codArea, 
								   String direccion, String telef2, String codArea2, String email, char tipoCliente, boolean contactar,char sexo, char estadoCivil,Date fechaNaci,String zonaResidencial) 
								   throws ClienteExcepcion, UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
	
		if (logger.isDebugEnabled()) {
			logger.debug("asignarCliente(String) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "asignarCliente");

		//Asignamos el cliente al servicio
		String autorizante = CR.me.verificarAutorizacion("LISTA DE REGALOS","asignarCliente", this.listaRegalos.getCliente());

		if(listaRegalos.getCliente().getCodCliente() != null)
			if(!listaRegalos.getCliente().getCodCliente().equals(id))
				CR.me.borrarAvisos();
		
		// Si está en estado Titular de Lista de Regalos evita que cambie el Id del cliente
		if(Sesion.getCaja().getEstado().equals("22")) 
			id=this.listaRegalos.getTitular().getCodCliente().substring(2);
		
//			****** Fecha Actualización 07/02/2007
		//Cambio de BECO para agregar validaciones de RIF y CI para el SENIAT
		//*******
		Validaciones validador = new Validaciones();
		if(!validador.validarRifCedula(id, tipoCliente)) {
			throw (new ClienteExcepcion("CI/RIF Inválido"));
		}
		//**************************
		
		Cliente clienteTemp = new Cliente(id, tipoCliente, nombre, apellido, direccion, codArea, telf,'A', codArea2, telef2, email, contactar,sexo,estadoCivil,fechaNaci,zonaResidencial);
		BaseDeDatosServicio.registrarClienteTemporal(clienteTemp);
		this.listaRegalos.asignarCliente(tipoCliente + "-" + id, "");
		//Se verifica si el cliente se encuentra inactivo. Unicamente se muestra un mensaje para indicar la eventualidad
		if(listaRegalos.getCliente().getEstadoCliente() != Sesion.ACTIVO)
			CR.me.mostrarAviso("El cliente se encuentra inactivo", true);

		try {
			CR.crVisor.enviarString("CLIENTE", 0, listaRegalos.getCliente().getNombreCompleto(), 2); }
		catch (Exception e) {
			logger.error("asignarCliente(String)", e);
		}
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		//Ahora verificamos si el cliente es exento de impuesto
		//this.facturarSinImpuesto();
								   	
		if (logger.isDebugEnabled()) {
			logger.debug("asignarCliente(String) - end");
		}
	}
	
	/**
	 * Registra la venta de un regalo de una lista
	 * 
	 * @param numTransaccion
	 * @param montoVenta
	 * @param cliente
	 * @param nomInvitado
	 * @param dedicatoria
	 * @param detallesVenta
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void registrarVentaLR(int numTransaccion,double montoVenta, Cliente cliente, String nomInvitado, 
								String dedicatoria,Vector<DetalleTransaccion> detallesVenta) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ExcepcionCr, ConexionExcepcion, PrinterNotConnectedException {
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "finalizarVentaLR");

		CR.me.verificarAutorizacion("LISTA DE REGALOS","finalizarVentaLR", this.listaRegalos.getCliente());

		if(Sesion.getCaja().getEstado().equals("21")){
			if(listaRegalos.getEstadoLista().equals("B"))
				throw new ExcepcionLR("La lista ya se encuentra en proceso de cierre.\nNo se permiten más ventas.");
			else if(listaRegalos.getEstadoLista().equals("C"))
				throw new ExcepcionLR("La lista ya fue cerrada.\nNo se permiten más ventas.");
			else if(!listaRegalos.getEstadoLista().equals("A"))
				throw new ExcepcionLR("La lista no está activa.\nNo se permiten ventas.");
		}	

		this.listaRegalos.registrarVenta(numTransaccion,montoVenta,CR.meVenta.getVenta().getCliente(),
										nomInvitado,dedicatoria,CR.meVenta.getVenta().getDetallesTransaccion());
	
		//	Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);
	}

	/**
	 * Entra en modo de consulta de lista de regalos por parte del titular
	 * 
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	public void titularListaRegalos() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "titularListaRegalos");
		
		CR.me.verificarAutorizacion("LISTA DE REGALOS","titularListaRegalos", this.listaRegalos.getCliente());
		this.asignarClienteLR(this.listaRegalos.getTitular().getCodCliente());
				
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);		
	}

	/**
	 * Entra en modo de consulta de lista de regalos por parte del invitado
	 * 
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	public void invitadoListaRegalos() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "invitadoListaRegalos");
		
		CR.me.verificarAutorizacion("LISTA DE REGALOS","invitadoListaRegalos", this.listaRegalos.getCliente());
		this.listaRegalos.quitarCliente();
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);
	}
	
	/**
	 * Entra en modo de modificación de lista de regalos por parte del titular
	 * 
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	public void modificarListaRegalosInicia() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr{
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "modificarListaRegalos");
		
		CR.me.verificarAutorizacion("LISTA DE REGALOS","modificarListaRegalos",this.listaRegalos.getCliente());
		
		if(listaRegalos.getTipoLista()==ListaRegalos.GARANTIZADA){
			if(listaRegalos.getCodTiendaApertura()!=Sesion.getTienda().getNumero())
				throw new ExcepcionLR("Solo puede modificar una Lista Garantizada en la tienda de apertura");
			if(Math.round(((CR.meServ.getListaRegalos().getMontoVendidos()+CR.meServ.getListaRegalos().getMontoAbonadosTotales())/CR.meServ.getListaRegalos().getMontoBase())*100)<75)
				throw new ExcepcionLR("Solo puede modificar la Lista Garantizada si ya se ha\nvendido al menos el 75% del monto de apertura");
		}
		
		listaRegalosRespaldo = (ListaRegalos)this.listaRegalos.clone();
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);
	}
	
	/**
	 * Cancela el estado de modificación de lista de regalos por parte del titular
	 * 
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	public void modificarListaRegalosCancela() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr{
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "modificarListaRegalos");
		
		CR.me.verificarAutorizacion("LISTA DE REGALOS","modificarListaRegalos",this.listaRegalos.getCliente());
		
		listaRegalos = (ListaRegalos)this.listaRegalosRespaldo.clone();
		listaRegalosRespaldo = null;
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);
	}
	
	/**
	 * Sale del estado de modificación de lista de regalos por parte del titular
	 * y guarda los cambios.
	 * 
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	public void modificarDetallesListaRegalos() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "modificarListaRegalos");
		CR.me.verificarAutorizacion("LISTA DE REGALOS","modificarListaRegalos",this.listaRegalos.getCliente());
		
		listaRegalos.modificarDetallesListaRegalos();
		this.listaRegalosRespaldo = null;
//		
//		try {
//			CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ...");
//		}
//		catch (Exception e) {
//			logger.error("modificarListaRegalosFin()", e);
//		}

		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

	}

	/**
	 * Entra en estado cierre de lista de regalos por parte del titular
	 * 
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	public void cerrarListaRegalos() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if(listaRegalos.getCodTiendaApertura() != listaRegalos.getCodTienda() && listaRegalos.getTipoLista() == ListaRegalos.GARANTIZADA)
			throw new MaquinaDeEstadoExcepcion("Las Listas de Regalos Garantizadas solo \npueden ser cerradas en su tienda de apertura");

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "cerrarListaRegalos");
		CR.me.verificarAutorizacion("LISTA DE REGALOS","cerrarListaRegalos",this.listaRegalos.getCliente());

		listaRegalos.setEstadoLista(ListaRegalos.INICIA_CIERRE);

		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);
	}

	/**
	 * Entra en modo de cierre por anulación de lista de regalos por parte del titular
	 * 
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	public void anularListaRegalosInicia() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "anularListaRegalos");
		CR.me.verificarAutorizacion("LISTA DE REGALOS","anularListaRegalos",this.listaRegalos.getCliente());
		listaRegalos.setEstadoLista(ListaRegalos.INICIA_CIERRE);
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);
	}
	
	/**
	 * Cierra una lista por anulación.
	 * 
	 * @param numTransaccion
	 * @param montoTrans
	 * @param cliente
	 * @param string
	 * @param vector
	 */
	public void anularListaRegalos(Cliente cliente, Vector<DetalleTransaccion> detallesTransaccion) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr, PrinterNotConnectedException {
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "cerrarListaRegalos");
		CR.me.verificarAutorizacion("LISTA DE REGALOS","cerrarListaRegalos",this.listaRegalos.getCliente());
		Auditoria.registrarAuditoria("Anulación de Lista de Regalos " + this.listaRegalos.getNumServicio() + " de Tienda " + this.listaRegalos.getCodTienda(),'T');
		
//		int numTransaccion = 0;
//		numTransaccion = CR.meVenta.finalizarVentaListaRegalos(CR.meServ.getListaRegalos().getTitular().getNombreCompleto(),"");
		this.listaRegalos.cerrarLista(Sesion.LISTAREGALOS_ANULADA, detallesTransaccion);
	
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);
		
	}

	/**
	 * Cierra normalmente una lista de regalos.
	 * 
	 * @param numTransaccion
	 * @param montoTrans
	 * @param cliente
	 * @param string
	 * @param vector
	 */
	public void finalizarCierreListaRegalos(Cliente cliente, Vector<DetalleTransaccion> detallesTransaccion) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ExcepcionCr, ConexionExcepcion, PrinterNotConnectedException {
		CR.me.verificarAutorizacion("LISTA DE REGALOS","cerrarListaRegalos",this.listaRegalos.getCliente());
		Auditoria.registrarAuditoria("Cierre de Lista de Regalos " + this.listaRegalos.getNumServicio() + " de Tienda " + this.listaRegalos.getCodTienda(),'T');

		this.listaRegalos.cerrarLista(Sesion.LISTAREGALOS_CERRADA, detallesTransaccion);

		this.finalizarListaRegalos();
	}

	/**
	 * Coloca en espera una lista de regalos.
	 * 
	 * @param numTienda
	 * @param numCaja
	 * @param identificacion
	 * @param tipoTransaccion
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	public void colocarListaEnEspera(int numTienda, int numCaja,String identificacion, char tipoTransaccion) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		String codAfiliado;
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "cancelarListaRegalos");
		CR.me.verificarAutorizacion("LISTA DE REGALOS","cancelarListaRegalos",this.listaRegalos.getCliente());
		
		codAfiliado = identificacion;
		int numOperacion = 0;
		BaseDeDatosServicio.colocarListaEnEspera(numTienda,numCaja,codAfiliado,numOperacion,tipoTransaccion);
		listaRegalos = null;
		if(CR.meVenta.getVenta()!=null)
			CR.meVenta.anularVentaActiva();
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);
	}
	
	/**
	 * Coloca en espera una lista de regalos
	 * 
	 * @param numTienda
	 * @param numCaja
	 * @param tipoTransaccion
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	public void colocarListaEnEspera(String codAfiliado, int numTienda, int numCaja,char tipoTransaccion) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "cancelarListaRegalos");
		CR.me.verificarAutorizacion("LISTA DE REGALOS","cancelarListaRegalos",this.listaRegalos.getCliente());

		//codAfiliado = this.listaRegalos.getCliente().getCodCliente();

		int numOperacion = 0;
		BaseDeDatosServicio.colocarListaEnEspera(numTienda,numCaja,codAfiliado,numOperacion,tipoTransaccion);
		listaRegalos = null;
		if(CR.meVenta.getVenta()!= null)
			CR.meVenta.anularVentaActiva();
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);
	}
	
	/**
	 * Recupera una lista en espera
	 * 
	 * @param codAfiliado
	 * @return
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws XmlExcepcion
	 * @throws FuncionExcepcion
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	public char recuperarListaEnEspera(String codAfiliado) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr{
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(),"recuperarListaEnEspera");
		Sesion.setUbicacion("LISTA DE REGALOS", "recuperarListaEnEspera");
		
		CR.me.verificarAutorizacion("LISTA DE REGALOS", "cargarListaRegalos");
		
		int numTienda = Sesion.getTienda().getNumero();
		int numCaja = Sesion.getCaja().getNumero();
		char tipoTransaccion = BaseDeDatosServicio.recuperarListaEnEspera(numTienda,numCaja,codAfiliado);
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);
		return tipoTransaccion;
	}
	
	/**
	 * Recupera el estado original de la lista luego de recuperarla de espera
	 * 
	 * @param estado
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	public void recuperarEstadoLista(char estado) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		String edoFinalCaja = "";
		Sesion.setUbicacion("LISTA DE REGALOS", "recuperarListaEnEspera");

		if(estado == ListaRegalos.REGISTRO){
			edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(),"crearListaRegalos");
			CR.me.verificarAutorizacion("LISTA DE REGALOS", "crearListaRegalos");
		}else if(estado == ListaRegalos.CONSULTA_INVITADO){
			edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(),"cargarListaRegalos");
			CR.me.verificarAutorizacion("LISTA DE REGALOS", "cargarListaRegalos");
		}else if(estado == ListaRegalos.CONSULTA_TITULAR){
			edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(),"titularListaRegalos");
			CR.me.verificarAutorizacion("LISTA DE REGALOS", "titularListaRegalos");
		}else if(estado == ListaRegalos.CIERRE_LISTA){
			edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(),"cerrarListaRegalos");
			CR.me.verificarAutorizacion("LISTA DE REGALOS", "cerrarListaRegalos");
		}
	
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);
	}
	
	/**
	 * Sustituye la lista de regalos actual por la indicada
	 * 
	 * @param lista
	 */
	public void setListaRegalos(ListaRegalos lista){
		this.listaRegalos = lista;
	}
	
	/**
	 * Clona el objeto ListaRegalos para guardar un respaldo temporal
	 * 
	 * @param listaRespaldo
	 */
	public void setListaRegalosRespaldo(ListaRegalos listaRespaldo){
		this.listaRegalosRespaldo = listaRespaldo;
	}

	/**
	 * @param numLote
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<String[]> ingresarLoteProductosLR(String numLote) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(),"ingresarLoteProductosLR");
		Sesion.setUbicacion("LISTA DE REGALOS", "ingresarLoteProductosLR");
		
		CR.me.verificarAutorizacion("LISTA DE REGALOS", "ingresarLoteProductosLR");

		Vector<String[]> noEncontrados = listaRegalos.ingresarLoteProductos(numLote);

		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		return noEncontrados;
	}
	
	/**
	 * Coloca Apartado en espera
	 * @param idEspera Codigo para colocar el apartado en espera
	 * @throws ExcepcionCr Si ocurre un error registrando del apartado
	 */
	public void colocarApartadoEspera(String idEspera) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("colocarApartadoEspera(String) - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "colocarApartadoEspera");
	
		CR.me.verificarAutorizacion("APARTADO","colocarApartadoEspera", this.apartado.getCliente());
		// Colocamos la venta en Espera en el servidor central de la tienda
		Sesion.setUbicacion("FACTURACION","colocarapartadoespera");
		if (idEspera.length()>8)
			idEspera = idEspera.substring(0,8);
		Auditoria.registrarAuditoria("Colocada en Espera Codigo " + idEspera + ". Registro Inicial " ,'T');
		try
		{	
			apartado.setEstadoServicio(Sesion.ESPERA);
			BaseDeDatosServicio.colocarEnEspera(apartado);
		}
		catch(BaseDeDatosExcepcion e1){
			//e1.printStackTrace();
			throw (new BaseDeDatosExcepcion("Error al colocar en espera el apartado"));
		}catch (ConexionExcepcion e1)
		{
			throw (new BaseDeDatosExcepcion("Error de conexión al registrar el apartado en espera"));
		}
		apartado = null;
		try{ CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ..."); }
		catch(Exception ex){
			logger.error("colocarFacturaEspera(String)", ex);
		}

		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("colocarFacturaEspera(String) - end");
		}
	}

	/**
	 * @param apartado
	 */
	public void setApartado(Apartado apartado) {
		this.apartado = apartado;
	}

	
	/**************************************************************************************
	 * Agregados por módulo de promociones
	 **************************************************************************************/
	/**
	 * Deshace las promociones que se crean al totalizar
	 * @param pagosRealizados
	 * @param pagosRealizadosAnteriormente
	 */	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void deshacerPromociones(Vector<Pago> pagosRealizados, Vector<Pago> pagosRealizadosAnteriormente){
		Apartado.donacionesRegistradas.clear();
		/*pagosRealizadosAnteriormente.clear();
		for (int i=0; i<pagosRealizados.size(); i++)
			pagosRealizadosAnteriormente.addElement(pagosRealizados.elementAt(i));
		
		Vector condicionProductoGratis = new Vector();
		condicionProductoGratis.addElement(Sesion.condicionProductoGratis);
		for(int i=0;i<CR.meServ.getApartado().getDetallesServicio().size();i++){
			if(((DetalleServicio)CR.meServ.getApartado().getDetallesServicio().elementAt(i)).contieneAlgunaCondicion(condicionProductoGratis)){
				(new ActualizadorPreciosSACFactory()).getActualizadorPreciosSACInstance().deshacerProductoGratis(
						(DetalleServicio)CR.meServ.getApartado().getDetallesServicio().elementAt(i)
				);
			}
		}*/
	}
	
	/**
	 * @return el productosPatrocinantes
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'TreeMap'
	* Fecha: agosto 2011
	*/
	public TreeMap<PromocionExt,Vector<Producto>> getProductosPatrocinantes() {
		return productosPatrocinantes;
	}

	/**
	 * @param productosPatrocinantes el productosPatrocinantes a establecer
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'TreeMap'
	* Fecha: agosto 2011
	*/
	public void setProductosPatrocinantes(TreeMap<PromocionExt,Vector<Producto>> productosPatrocinantes) {
		this.productosPatrocinantes = productosPatrocinantes;
	}
	
	/**
	 * Inicializa el actualizador de precios tomado de lo definido en las
	 * extensiones a utilizar
	 * @author jgraterol
	 * **/
	public void iniciarActualizadorPrecios(){
		if (logger.isDebugEnabled()) {
			logger.debug("iniciarBuscador() - start");
		}

		ActualizadorPreciosSACFactory  factory = new ActualizadorPreciosSACFactory();
		actualizadorPrecios = factory.getActualizadorPreciosSACInstance();		
		if (logger.isDebugEnabled()) {
			logger.debug("iniciarBuscador() - end");
		}	
	}
	
	/**
	 * Revisa todas las promociones aplicables al momento de totalizar
	 * @author jgraterol
	 */
	public void ejecucionPromociones(){
		if (actualizadorPrecios==null)iniciarActualizadorPrecios();
		actualizadorPrecios.ejecutarAhorroEnCompra();
		actualizadorPrecios.llamadaDeRegalos(1);
		//actualizadorPrecios.ejecutarProductoComplementario(1);
	}
/*
	public HashMap getProductosComplementario() {
		return productosComplementario;
	}

	public void setProductosComplementario(HashMap productosComplementario) {
		this.productosComplementario = productosComplementario;
	}
	*/
	/**
	 * Solicita autorizacion para agregar una promoción corporativa a la venta
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */
	public void agregarPromocionCorporativa() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("agregarPromocionCorporativa() - start");
		}
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "agregarPromocionCorporativa");
		CR.me.verificarAutorizacion("FACTURACION", "agregarPromocionCorporativa");
		
		//Actualizamos el estado de la caja
		MaquinaDeEstado.setEstadoCaja(edoFinalCaja);
		
		if (logger.isDebugEnabled()) {
			logger.debug("agregarPromocionCorporativa() - end");
		}
	}
	
	/**
	 * Solicita autorizacion para agregar cupones de descuento
	 * @throws ExcepcionCr 
	 * @throws ConexionExcepcion 
	 * @throws MaquinaDeEstadoExcepcion 
	 * @throws UsuarioExcepcion 
	 * **/
	public void agregarCuponesDescuento() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("agregarCuponesDescuento() - start");
		}
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "agregarCuponesDescuento");
		CR.me.verificarAutorizacion("FACTURACION", "agregarCuponesDescuento");
		
		//Actualizamos el estado de la caja
		MaquinaDeEstado.setEstadoCaja(edoFinalCaja);
		
		if (logger.isDebugEnabled()) {
			logger.debug("agregarCuponesDescuento() - end");
		}
	}


	/**************************************************************************************
	 * Fin agregados por módulo de promociones
	 **************************************************************************************/

	/**************************************************************************************
	 * Métodos agregados por apartados especiales
	 **************************************************************************************/
	/**
	 * Obtiene tipos de apartados especiales
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Vector<Object>> obtenerTiposApartados(){
		Vector<Vector<Object>> tiposApartados = new Vector<Vector<Object>>();
		try {
			tiposApartados = BaseDeDatosServicio.obtenerTiposApartadosVigentes();
		} catch (BaseDeDatosExcepcion e) {
			logger.error("obtenerTiposApartados()", e);
		} catch (ConexionExcepcion e) {
			logger.error("obtenerTiposApartados()", e);
		}
		return tiposApartados;
	}
	
	/**
	 * Asigna las variables de apartado especial al servicio
	 * @param fechaVence
	 * @param tipo
	 */
	public void asignarVariablesApartadoEspecial(Date fechaVence, int tipo, String descripcion){
		CR.meServ.getApartado().setTipoApartado(tipo);
		if(tipo!=0)
			CR.meServ.getApartado().setFechaVencimiento(fechaVence);
		CR.meServ.getApartado().setDescripcionTipoApartado(descripcion);
		
	}
	
	/**************************************************************************************
	 * Fin de métodos agregados por apartados especiales
	 **************************************************************************************/
	
	/**
	 * Method configurarVPosCards
	 * 		Modifica el archivo de configuración de VPos para las opciones de CARDS
	 *      de acuerdo al metodo enviado como parametro
	 * @param nombModulo - Nombre del módulo que se está ejecutando
	 * @param nombMetodo - Nombre del método que se está utilizando 
	 * @throws ExcepcionCr 
	 * @throws ConexionExcepcion 
	 * @throws MaquinaDeEstadoExcepcion 
	 * @throws UsuarioExcepcion 
	 * @throws ConexionExcepcion 
	 * @throws BaseDeDatosExcepcion 
	 * @throws FuncionExcepcion 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void configurarVPosCards(String nombModulo, String nombMetodo) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("configurarVPosCards(String, String) - start");
		}

		CR.me.verificarAutorizacion(nombModulo, nombMetodo);

		Vector<Integer> result = MediadorBD.obtenerMetModFun();
		int codMetodo = ((Integer)result.elementAt(0)).intValue();

		// Obtenemos las opciones disponibles para el metodo especificado
		Vector<OpcionBR> opciones = BaseDeDatosServicio.obtenerOpcionesCards(codMetodo);
		
		// Llamada a la extension de vPos para edicion de archivo vpos
		ManejoPagosFactory.getInstance().configurarOpcionesCards(opciones);
		
		if (logger.isDebugEnabled()) {
			logger.debug("configurarVPosCards(String, String) - end");
		}
	}
	
	/**
	 * Consultar saldo de tarjeta de Bono Regalo
	 * @throws ExcepcionCr 
	 * @throws ConexionExcepcion 
	 * @throws MaquinaDeEstadoExcepcion 
	 * @throws UsuarioExcepcion 
	 */
	public void consultarSaldoBR() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("consultarSaldoBR() - start");
		}
		
		CR.meServ.configurarVPosCards("Bono Regalo", "CONSULTARSALDOBR");
		
		ManejoPagosFactory.getInstance().consultaSaldoCards();
		
		if (logger.isDebugEnabled()) {
			logger.debug("consultarSaldoBR() - start");
		}

	}

	public VentaBR getVentaBR() {
		return ventaBR;
	}

	public void setVentaBR(VentaBR ventaBR) {
		this.ventaBR = ventaBR;
	}
	
	/**
	 * Realizar una carga/recarga de saldo de tarjeta de Bono Regalo
	 * @throws ExcepcionCr 
	 * @throws ConexionExcepcion 
	 * @throws MaquinaDeEstadoExcepcion 
	 * @throws UsuarioExcepcion 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public boolean cargaRecargaSaldoBR(VentaBR venta) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr{
		if (logger.isDebugEnabled()) {
			logger.debug("cargaRecargaSaldoBR() - start");
		}
		
		//String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "cargaRecargaSaldoBR");
		CR.meServ.configurarVPosCards("Bono Regalo", "CARGARECARGASALDOBR");
		Vector<DetalleTransaccionBR> detalles = venta.getDetallesTransaccionBR();
		String codCliente;
		try{
			codCliente = venta.getCliente().getCodCliente();
		} catch(NullPointerException npe){
			codCliente="";
		}
		venta.setTarjetasRecargadas(0);
		boolean recargadas = true;
		for(int i=0;i<detalles.size();i++){
			DetalleTransaccionBR detalle = (DetalleTransaccionBR)detalles.elementAt(i);
			double monto = detalle.getMonto();
			try{
				//TODO: descomentar este codigo y eliminar la linea de mas abajo
				Vector<Object> datosOperacion = ManejoPagosFactory.getInstance().cargaRecargaSaldoCards(monto, codCliente);
				if(datosOperacion.size()!=0){
					String tarjeta = (String)(datosOperacion.elementAt(0));
					Integer numseq = ((Integer)(datosOperacion.elementAt(1)));
					//String tarjeta = "988888********7265";
					//Integer numseq = new Integer(0);
					if(tarjeta!=null && numseq!=null) {
						detalle.setCodTarjeta(tarjeta);
						detalle.setNumSeq(numseq.intValue());
						//Haacer update
						BaseDeDatosServicio.actualizarCodigosTarjetaBR(ventaBR, detalle, i, true);
						BaseDeDatosServicio.actualizarVentaBRNoSincronizada(ventaBR, true);
						venta.setTarjetasRecargadas(venta.getTarjetasRecargadas()+1);
					}
					
					if(i==detalles.size()-1 && venta.getTarjetasRecargadas()==detalles.size()){
						ventaBR.setEstadoTransaccion(Sesion.BONO_REGALO_COMPLETADA);
						BaseDeDatosServicio.actualizarEstadoVentaBR(venta, Sesion.BONO_REGALO_COMPLETADA,null,true);
						BaseDeDatosServicio.actualizarVentaBRNoSincronizada(ventaBR, true);
					}
				} else {
					recargadas=false;
				}
				
			} catch(BonoRegaloException br){
				MensajesVentanas.mensajeError("La tarjeta no pudo ser recargada\nEs necesario intentar nuevamente");
				i--;
			}
		}
		
		// Actualizamos el estado de la caja
		//Sesion.getCaja().setEstado(edoFinalCaja);
		if (logger.isDebugEnabled()) {
			logger.debug("cargaRecargaSaldoBR() - start");
		}
		return recargadas;
	}
	
	/**
	 * Método consultarMontoTrans
	 * 		Obtiene el subtotal de la transaccion.
	 * @return double - El monto actual de la transaccion
	 * @throws BaseDeDatosExcepcion - Si ocurre un error con la conexion 
	 * 		a la Base de Datos
	 * @throws MaquinaDeEstadoExcepcion - Si el estado es incorrecto para 
	 * 		Consultar SubTotal
	 */
	public double consultarMontoTransBR (boolean mostrarSubTotalVisor) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("consultarMontoTransBR(boolean) - start");
		}

		double subTotal = 0;
		
		subTotal = ventaBR.consultarMontoTrans();
		
		if (mostrarSubTotalVisor){
			try{ CR.crVisor.enviarString("TOTAL", 0, df.format(subTotal), 2); }
			catch(Exception ex){
				logger.error("consultarMontoTransBR(boolean)", ex);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("consultarMontoTransBR(boolean) - end");
		}
		return subTotal;
	}
	
	/**
	 * Método efectuarPago.
	 * 		Realiza el pago sobre la venta de bonos regalo
	 * @return double - El monto que falta por pagar. Si el resto es 
	 * 		positivo falta por pagar. Si el resto es negativo es el vuelto.
	 * @throws BaseDeDatosExcepcion - Si ocurre un error con la conexion 
	 * 		a la Base de Datos
	 * @throws MaquinaDeEstadoExcepcion - Si el estado es incorrecto para 
	 * 		Efectuar Pago
	 */
	public double efectuarPago(Pago p) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("efectuarPago(Pago) - start");
		}

		double resto = 0;
				
		//Verificamos si existen detalles en la venta para poder efectuar algún pago
		if (ventaBR.getDetallesTransaccionBR().size()<=0) {
			throw (new MaquinaDeEstadoExcepcion("No puede efectuar pago. No existen detalles en la venta"));
		}
		
		resto = this.ventaBR.efectuarPago(p);

		if (logger.isDebugEnabled()) {
			logger.debug("efectuarPago(Pago) - end");
		}
		return resto;
	}
	
	/**
	 * Registra la venta en la base de datos, recarga las tarjetas, imprime comprobantes y actualiza los codigos de tarjetas
	 * @param verificarEdos
	 * @param mostrarVuelto
	 * @return Numero de transaccion finalizada
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ExcepcionCr
	 * @throws XmlExcepcion
	 * @throws FuncionExcepcion
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 * @throws PrinterNotConnectedException
	 */
	public int finalizarVentaBR(boolean verificarEdos, boolean mostrarVuelto, boolean abrirGaveta) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ExcepcionCr, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion, PrinterNotConnectedException {
		if (logger.isDebugEnabled()) {
			logger.debug("finalizarVentaBR(boolean) - start");
		}
		int numTransaccion = 0; 
				
		//Verificamos si existen detalles en la venta para poder finalizarla
		if (ventaBR.getDetallesTransaccionBR().size()<=0) {
			throw (new MaquinaDeEstadoExcepcion("No puede finalizar la venta No existen detalles."));
		}
					
		// Chequeamos que se puede finalizar la venta (Pagos completos)
		double monto = ventaBR.consultarMontoTrans();
			
		if (monto - ventaBR.obtenerMontoPagado() < 0.01) {
				
			ventaBR.finalizarTransaccion();
			try{ CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ..."); }
			catch(Exception ex){
				logger.warn("finalizarVentaBR(boolean)", ex);
			}
			numTransaccion = ventaBR.getNumTransaccion();
			
			//Activar las tarjetas
			boolean recargadas = CR.meServ.cargaRecargaSaldoBR(CR.meServ.getVentaBR());

			if(recargadas){
				//Imprimir comprobante
				while (CR.meVenta.getTransaccionPorImprimir()!=null || 
						CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
						MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()) {
					MensajesVentanas.aviso("Hay un documento en curso \nEspere un momento e intente nuevamente");
				}
				if (Sesion.impresoraFiscal) {
					CR.meServ.setTransaccionPorImprimir(CR.meServ.getVentaBR());
					CRFiscalPrinterOperations.setImpresionFiscalEnCurso(true);
				}
				
				ventaBR.setAccion(Sesion.ACCION_BONO_REGALO_FACTURACION);
				ManejadorReportesFactory.getInstance().imprimirFacturaBR(ventaBR, abrirGaveta);
				
				for(int i=0;i<ventaBR.getPagos().size();i++){
					Pago p = (Pago)ventaBR.getPagos().elementAt(i);
					if(p.getFormaPago().getTipo()==Sesion.TIPO_PAGO_BECO){
						
						(new ActualizadorPreciosFactory()).getActualizadorPreciosInstance().imprimirFormasDePagoEspeciales(ventaBR.getCliente(), p.getFormaPago(), p.getMonto(), ventaBR.getNumTransaccion());
					}
				}
				
				/*ManejadorReportesFactory.getInstance().imprimirComprobanteBonoRegalo(ventaBR, false, mostrarVuelto, true);
				
				BaseDeDatosServicio.actualizarEstadoVentaBR(CR.meServ.getVentaBR(), Sesion.BONO_REGALO_LISTO,null, true);
				BaseDeDatosServicio.actualizarVentaBRNoSincronizada(ventaBR, true);*/
			}
			ventaBR = null;
		} else {
			// No se ha pagado completamente. Lanzamos una Excepcion
			throw (new PagoExcepcion("No se puede finalizar la venta. El monto del pago no abarca el total de la venta"));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("finalizarVentaBR(boolean) - end");
		}
		return numTransaccion;
	}
	
	/**
	 * Método anularVentaActiva.
	 * 		Anula la venta de bono regalo colocándola en nulo en la aplicación (No se registra en la Base de Datos).
	 * @throws BaseDeDatosExcepcion - Si ocurre un error con la conexion a la Base de Datos
	 * @throws MaquinaDeEstadoExcepcion - Si el estado es incorrecto para Anular la Venta Activa
	 * @throws ExcepcionCr - Si ocurre un error ingresando el registro de auditoria
	 */
	public void anularVentaBRActiva() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr, PagoExcepcion {
		
		
		boolean pagoCondicional = false;
		FormaDePago pago = null;
		
		if (logger.isDebugEnabled()) {
			logger.debug("anularVentaActiva() - start");
		}

		// Chequeamos que se puede finalizar la venta (Pagos completos)
		
		try{ CR.crVisor.enviarString("* CAJA ABIERTA *", "PROXIMO CLIENTE ..."); }
		catch(Exception ex){
			logger.error("anularVentaActiva()", ex);
		}
	
		int i = 0;
		for (i=0; (i < ventaBR.getPagos().size()) && (pagoCondicional == false); i++) {
			pago = (FormaDePago)((Pago)ventaBR.getPagos().elementAt(i)).getFormaPago();
			if (pago.isValidarSaldoCliente()){
				pagoCondicional = true; 
			}
		}
		if ((pagoCondicional)){//Si había pagos con condicional
			eliminarPagoCondicional((Pago)ventaBR.getPagos().elementAt(i-1));
		}
		ventaBR = null;
		
		if (logger.isDebugEnabled()) {
			logger.debug("anularVentaActiva() - end");
		}
	}
	
	/**
	 * Método eliminar pago con condicional
	 * 		Eliminar el pago con condicional al cliente asignado
	 */
	public void eliminarPagoCondicional (Pago pagoRealizado) throws PagoExcepcion
	{
		double monto = pagoRealizado.getMonto()*(-1);
		ManejoPagosFactory.getInstance().disminuirSaldoCliente(ventaBR.getCliente(), monto);
	}
	
	/**
	* Método quitarCliente
	* 		Permite quitar el cliente asignado a una venta. 
	* @return boolean
	 * @throws ExcepcionCr 
	*/
	public void quitarCliente() throws ConexionExcepcion, ExcepcionCr{
		boolean pagoCondicional = false;
		FormaDePago pago = null;
		//No se puede aplicar descuento empleado si ya la venta posee un pago especial
		
		for (int i=0; i<ventaBR.getPagos().size(); i++) {
			pago = (FormaDePago)((Pago)ventaBR.getPagos().elementAt(i)).getFormaPago();
			if (pago.isValidarSaldoCliente()){
				pagoCondicional = true; 	
			}
		}
		if (!pagoCondicional){//Verificar si había pagos con condicional
			//Eliminar el cliente
			Cliente cliente = new Cliente();
			ventaBR.setCliente(cliente);
		} else {
			//No se puede eliminar el cliente
			MensajesVentanas.aviso("Debe eliminar el pago con condicional para quitar el cliente");
		}
	}
	
	/**
	 * Asigna el cliente a la venta de Bono Regalo
	 * @param codigoBarra
	 * @throws UsuarioExcepcion
	 * @throws MaquinaDeEstadoExcepcion
	 * @throws ConexionExcepcion
	 * @throws ExcepcionCr
	 */	
	public void asignarClienteVentaBR(Cliente cliente) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		if (logger.isDebugEnabled()) {
			logger.debug("asignarCliente(String) - start");
		}
		
		boolean existiaVentaActiva = (ventaBR != null) ? true : false;
			
		//Asignamos el cliente a la venta
		try {
			String autorizante = CR.me.verificarAutorizacion ("FACTURACION","asignarCliente");				
			if(ventaBR.getCliente()!=null &&ventaBR.getCliente().getCodCliente() != null)
				if(!ventaBR.getCliente().getCodCliente().equals(cliente.getCodCliente()) && CR.me!=null)
					CR.me.borrarAvisos();
			
			ventaBR.asignarCliente(cliente, autorizante);
			
			//Se verifica si el cliente se encuentra inactivo. Unicamente se muestra un mensaje para indicar la eventualidad
			if(ventaBR.getCliente().getEstadoCliente() != Sesion.ACTIVO)
				CR.me.mostrarAviso("El cliente se encuentra inactivo", true);
				
			//Se envia al Visor la asignación del cliente
			String nombreCliente = ventaBR.getCliente().getNombreCompleto();
			try{ CR.crVisor.enviarString("CLIENTE", 0, nombreCliente, 2); }
			catch(Exception ex){
				logger.error("asignarCliente(String)", ex);
			}
		} catch (ConexionExcepcion e) {
			logger.error("asignarCliente(String)", e);
			if (!existiaVentaActiva) {
				ventaBR = null;
			}
			throw e;
		} catch (ExcepcionCr e) {
			logger.error("asignarCliente(String)", e);

			if (!existiaVentaActiva) {
				ventaBR = null;
			}
			throw e;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("asignarCliente(String) - end");
		}
	}
	
	/**
	 * Construye la venta de Bonos Regalo y la asigna a maquina de estado servicio
	 * @param tipoTransaccion
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 * @throws FuncionExcepcion 
	 * @throws XmlExcepcion 
	 * @throws MaquinaDeEstadoExcepcion 
	 */
	public void crearVentaBR(char tipoTransaccion, boolean verificarEdos) throws BaseDeDatosExcepcion, ConexionExcepcion, MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("crearVentaBR(char tipoTransaccion) - start");
		}
		
		String edoFinalCaja = null;
		if (verificarEdos){
			edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "crearVentaBR");
		}
		
		CR.meServ.setVentaBR(new VentaBR(Sesion.getNumTienda(),
				Sesion.getFechaSistema(), 
				Sesion.getNumCaja(), 
				Sesion.getHoraSistema(), 
				Sesion.getUsuarioActivo().getNumFicha(),
				tipoTransaccion, 
				Sesion.BONO_REGALO_EN_PROCESO));
		
		if (verificarEdos) {
			// Actualizamos el estado de la caja
			Sesion.getCaja().setEstado(edoFinalCaja);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("crearVentaBR(char tipoTransaccion) - end");
		}
	}
	
	/**
	 * Method obtenerCantidadProdsBR
	 * 		Obtiene la cantidad de productos de la venta de Bonos Regalos
	 * @return float - Cantidad de prodcutos existentes en el Detalle
	 */
	public float obtenerCantidadProdsBR() {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCantidadProds() - start");
		}
	
		float cantProductos = this.ventaBR.obtenerCantidadProds();

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCantidadProds() - end");
		}
		return cantProductos;
	}
	
	/**
	 * Agrega un detalle de transaccion de bono regalo 
	 * @param monto del detalle
	 */
	public void agregarLineaDetalleBR(double monto){
		CR.meServ.getVentaBR().getDetallesTransaccionBR().addElement(new DetalleTransaccionBR(null, monto, 0, Sesion.DETALLE_BR_ACTIVO));
		CR.meServ.getVentaBR().actualizarMontoTransaccion();
	}
	
	/**
	 * Suma o resta un monto a un detalle de venta de bono regalo,
	 * Si el monto a eliminar es igual al monto del detalle, elimina el detalle completo
	 * @param monto
	 * @param seleccionado
	 * @throws BonoRegaloException
	 */
	public void modificarLineaDetalleBR(double monto, int seleccionado) throws BonoRegaloException{
		DetalleTransaccionBR detalleBR = (DetalleTransaccionBR)CR.meServ.getVentaBR().getDetallesTransaccionBR().get(seleccionado);
		if(-monto>detalleBR.getMonto())
			throw new BonoRegaloException("La cantidad a restar debe ser menor o igual al monto del detalle seleccionado");
		if(detalleBR.getMonto()==-monto){
			CR.meServ.getVentaBR().getDetallesTransaccionBR().set(seleccionado, null);
			while(CR.meServ.getVentaBR().getDetallesTransaccionBR().remove(null));
		} else {
			detalleBR.setMonto(detalleBR.getMonto()+monto);
		}
		CR.meServ.getVentaBR().actualizarMontoTransaccion();
	}

	/**
	 * Asigna Vendedor Corporativo a Venta/Recarga de Bono Regalo electrónico
	 * @throws ConexionExcepcion 
	 * @throws ClienteExcepcion 
	 * @throws BaseDeDatosExcepcion 
	 * @throws AfiliadoUsrExcepcion 
	 * @throws BonoRegaloException 
	 */
	public void asignarVendedorCorporativoBR(String codEmpleado) throws AfiliadoUsrExcepcion, BaseDeDatosExcepcion, ClienteExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("asignarVendedorCorporativoBR() - start");
		}
		
		this.getVentaBR().asignarVendedorCorporativo(codEmpleado);

		
		
		if (logger.isDebugEnabled()) {
			logger.debug("asignarVendedorCorporativoBR() - end");
		}

	}

	/**
	 * Realiza la operacion de asignacion de monto a favor en LR y/o Devolucion a una venta de Bono Regalo
	 * @param operacion Operacion que se mostrara en el mensaje de confimacion
	 * @param montoAcreditar Monto a abreditar en la carga de Bono Regalo
	 * @param cliente Objeto Cliente asignado a la venta de bono regalo
	 * @param aviso Mostrar mensaje de aviso o mensaje de confirmacion.
	 * @throws ConexionExcepcion 
	 * @throws PrinterNotConnectedException 
	 * @throws ExcepcionCr 
	 * @throws FuncionExcepcion 
	 * @throws XmlExcepcion 
	 * @throws MaquinaDeEstadoExcepcion 
	 * @throws UsuarioExcepcion 
	 */
	public void realizarVentaBR(String operacion, double montoAcreditar, Cliente cliente, boolean aviso) throws ConexionExcepcion, UsuarioExcepcion, MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion, ExcepcionCr, PrinterNotConnectedException {
		if (logger.isDebugEnabled()) {
			logger.debug("realizarVentaBR() - start");
		}
		
		int respuesta = 0;
		if (aviso) {
			MensajesVentanas.aviso("Saldo a Favor: " + df.format(montoAcreditar) + "\n" +
				"Se asignará el saldo a favor de " + operacion + " a Bonos Regalo", false);
		} else {
			respuesta = MensajesVentanas.preguntarSiNo("Saldo a Favor: " + df.format(montoAcreditar) + "\n" +
					"¿Desea asignar el saldo a favor de " + operacion + " a Bonos Regalo?");
		}

		if (respuesta == 0) {
			// Llamada a la pantalla inicial de Venta de BR 
			BRCargaServicio brCarga = new BRCargaServicio();
			MensajesVentanas.centrarVentanaDialogo(brCarga);
			char tipoTransaccion = brCarga.getTipoTransaccion();
			brCarga = null;
			
			if (tipoTransaccion != '0') {
				// Seleccionada una opcion valida de carga/recarga
				this.crearVentaBR(tipoTransaccion, false);
				this.ventaBR.setCliente(cliente);
				this.agregarLineaDetalleBR(montoAcreditar);
				
				Pago p = new Pago(Sesion.FORMA_PAGO_EFECTIVO, montoAcreditar, null, null, null, null, 0, null);
				this.getVentaBR().getPagos().addElement(p);
			
				this.finalizarVentaBR(false, false, false);
			} else {
				MensajesVentanas.aviso("Cancelada la carga/recarga de tarjeta en " + operacion);
				throw new ExcepcionCr("Cancelada la carga/recarga de tarjeta en " + operacion);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("realizarVentaBR() - end");
		}

	}

	/**
	 * Recupera transacciones de bono regalo ya procesadas para emitir documentos,
	 * Se hace directo del servidor de tienda para evitar problemas de concurrencia y chequea 
	 * que no haya sido recuperada en otra caja
	 * @param tienda
	 * @param fecha
	 * @param caja
	 * @param numtransaccion
	 * @throws BonoRegaloException
	 * @throws ConexionExcepcion 
	 * @throws BaseDeDatosExcepcion 
	 * @throws FuncionExcepcion 
	 * @throws XmlExcepcion 
	 * @throws MaquinaDeEstadoExcepcion 
	 */
	public void recuperarVentaBR(int tienda, String fecha, int caja, int numtransaccion) throws BonoRegaloException, MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion{
		if (logger.isDebugEnabled()) {
			logger.debug("recuperarVentaBR() - start");
		}

		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "recuperarVentaBR");

		//Determinar si existe transaccion bloqueada
		int numCajaBloquea =0;
		numCajaBloquea= BaseDeDatosServicio.getNumCajaBloquea(tienda, fecha, caja, numtransaccion);
		
		if(numCajaBloquea!=0 && numCajaBloquea!=Sesion.getCaja().getNumero()){
			throw new BonoRegaloException("Transacción ya recuperada desde la caja N. "+numCajaBloquea);
		}
		ventaBR = BaseDeDatosServicio.recuperarVentaBR(tienda, fecha, caja, numtransaccion, numCajaBloquea);

		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("recuperarVentaBR() - end");
		}
	}
	
	/**
	 * Imprime la factura por venta o recarga de bonos regalo.
	 * @throws BonoRegaloException
	 */
	public void imprimirFacturaBR() throws BonoRegaloException{
		try {
			
			//Verificar si la transaccion ya se encuentra facturada
			if(ventaBR.getEstadoTransaccion()==Sesion.BONO_REGALO_FACTURADA)
				throw new BonoRegaloException("La transacción ya se encuentra facturada");
			CR.meServ.getVentaBR().setAccion(Sesion.ACCION_BONO_REGALO_FACTURACION);
			while (CR.meVenta.getTransaccionPorImprimir()!=null || 
					CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
					MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()) {
				MensajesVentanas.aviso("Hay un documento en curso \nEspere un momento e intente nuevamente");
			}
			if (Sesion.impresoraFiscal) {
				CR.meServ.setTransaccionPorImprimir(ventaBR);
				CRFiscalPrinterOperations.setImpresionFiscalEnCurso(true);
			}
			ManejadorReportesFactory.getInstance().imprimirFacturaBR(CR.meServ.getVentaBR(), false);
			
		} catch (BaseDeDatosExcepcion e) {
			e.printStackTrace();
		} catch (ConexionExcepcion e) {
			e.printStackTrace();
		}
	}

	public Transaccion getTransaccionPorImprimir() {
		return transaccionPorImprimir;
	}

	public void setTransaccionPorImprimir(Transaccion transaccionPorImprimir) {
		this.transaccionPorImprimir = transaccionPorImprimir;
	}
	
	/**
	 * Immprime comprobante no fiscal de venta o recarga de bono regalo
	 * @throws BaseDeDatosExcepcion
	 */
	public void imprimirComprobanteBR() throws BaseDeDatosExcepcion {
		
		while (CR.meVenta.getTransaccionPorImprimir()!=null || 
				CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
				MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()) {
			MensajesVentanas.aviso("Hay un documento en curso \nEspere un momento e intente nuevamente");
		}
		if (Sesion.impresoraFiscal) {
			CR.meServ.setTransaccionPorImprimir(CR.meServ.getVentaBR());
			CRFiscalPrinterOperations.setImpresionFiscalEnCurso(true);
		}
		ManejadorReportesFactory.getInstance().imprimirComprobanteBonoRegalo(CR.meServ.getVentaBR(), true, false, true);
		
		if(CR.meServ.getVentaBR().getEstadoTransaccion()==Sesion.BONO_REGALO_COMPLETADA){
			BaseDeDatosServicio.actualizarEstadoVentaBR(CR.meServ.getVentaBR(), Sesion.BONO_REGALO_LISTO,null,false);
			BaseDeDatosServicio.actualizarVentaBRNoSincronizada(ventaBR, false);
		}
	}
	
	/**
	 * Reimprimir nota factura de BR: Nota de credito (anulacion) y nueva venta (factura)
	 * @param clienteAnterior En caso de que se trate de una reimpresion de factura
	 * por cambio de cliente se pasa el cliente anterior para la nota de credito
	 * @throws BonoRegaloException
	 */
	public void reimprimirFacturaBR(Cliente clienteAnterior) throws BonoRegaloException {
		if(CR.meServ.getVentaBR().getEstadoTransaccion()!=Sesion.BONO_REGALO_FACTURADA)
			throw new BonoRegaloException("No es posible reimprimir la factura\nLa transacción aún no ha sido facturada");
		CR.meServ.getVentaBR().setAccion(Sesion.ACCION_BONO_REGALO_REIMPRESION);
		try {
			
			while (CR.meVenta.getTransaccionPorImprimir()!=null || 
					CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
					MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()) {
				MensajesVentanas.aviso("Hay un documento en curso \nEspere un momento e intente nuevamente");
			}
			if (Sesion.impresoraFiscal) {
				CR.meServ.setTransaccionPorImprimir(ventaBR);
				CRFiscalPrinterOperations.setImpresionFiscalEnCurso(true);
			}
			ManejadorReportesFactory.getInstance().imprimirAnulacionBR(CR.meServ.getVentaBR(), clienteAnterior, false, false);
			
			while (CR.meVenta.getTransaccionPorImprimir()!=null || 
					CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
					MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()) {
				MensajesVentanas.aviso("Hay un documento en curso \nEspere un momento e intente nuevamente");
			}
			if (Sesion.impresoraFiscal) {
				CR.meServ.setTransaccionPorImprimir(ventaBR);
				CRFiscalPrinterOperations.setImpresionFiscalEnCurso(true);
			}
			ManejadorReportesFactory.getInstance().imprimirFacturaBR(CR.meServ.getVentaBR(), false);
						
		} catch (BaseDeDatosExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (ConexionExcepcion e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
	}
	
	/**
	 * Elimina de la tabla de transacciones bloqueadas (recuperadas desde otra caja)
	 * La venta de BR actual
	 * @throws ConexionExcepcion 
	 * @throws BaseDeDatosExcepcion 
	 * @throws FuncionExcepcion 
	 * @throws XmlExcepcion 
	 * @throws MaquinaDeEstadoExcepcion 
	 */
	public void desbloquearTransaccionBR() throws MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {
		if (logger.isDebugEnabled()) {
			logger.debug("desbloquearTransaccionBR() - start");
		}
		
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "desbloquearTransaccionBR");
		
		BaseDeDatosServicio.desbloquearTransaccionBR(CR.meServ.getVentaBR());
		
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);

		if (logger.isDebugEnabled()) {
			logger.debug("desbloquearTransaccionBR() - start");
		}
}

	/**
	 * Obtiene las operaciones de Carga/Regarga de BR realizadas por el usuario
	 *	@return Vector: Pos 0: Campo Boolean que indica si existen operaciones de Bonos Regalos
	 *				    Pos 1: Campo Entero que indica el número de comprobante de Caja Principal
	 *					Pos 2: Campo Vector con las formas de pagos y los montos registrados
	 * @throws SQLException 
	 * @throws ConexionExcepcion 
	 * @throws BaseDeDatosExcepcion 
	 */
	public Vector<Object> obtenerOperacionesBR(Usuario usuarioActivo, Date fecha) throws BaseDeDatosExcepcion, ConexionExcepcion, SQLException {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerOperacionesBR() - start");
		}

		Vector<Object> resultado = BaseDeDatosServicio.obtenerOperacionesBR(usuarioActivo, fecha);

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerOperacionesBR() - end");
		}
		return resultado;
	}

	
	/**
	 * Realiza anulacion de venta o recarga de Bono Regalo
	 * Si esta facturada emite nota de credito
	 * Si no esta facturada, simplemente cambia el estado de la transaccion
	 * @throws ExcepcionCr 
	 * @throws ConexionExcepcion 
	 * @throws MaquinaDeEstadoExcepcion 
	 * @throws UsuarioExcepcion 
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void anularVentaBR() throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, ConexionExcepcion, ExcepcionCr {
		String edoFinalCaja = MediadorBD.obtenerEdoFinal(Sesion.getCaja().getEstado(), "ANULARVENTABR");
		String autorizante = CR.me.verificarAutorizacion ("Bono Regalo","ANULARVENTABR");			
		
		Vector <DetalleTransaccionBR> detalles = ventaBR.getDetallesTransaccionBR();
		SimpleDateFormat fechaFormat = new SimpleDateFormat("yyyy-MM-dd");
		String fechaTrans = fechaFormat.format(ventaBR.getFechaTrans()) ;
		String fechaSistema = fechaFormat.format(Sesion.getFechaSistema());
		//Anular las tarjetas recargadas
		if(fechaTrans.equalsIgnoreCase(fechaSistema)){
			for(int i=0;i<detalles.size();i++){
				DetalleTransaccionBR detalle = (DetalleTransaccionBR)detalles.elementAt(i);
				String codTarjeta = detalle.getCodTarjeta();
				if(codTarjeta!=null && detalle.getNumSeq()!=0) {
					try{
						int numSecuencia = ManejoPagosFactory.getInstance().procesarAnulacionTarjeta(codTarjeta,detalle.getNumSeq());
						if(numSecuencia!=0){
							detalle.setEstadoRegistro(Sesion.DETALLE_BR_ELIMINADO);
							try {
								BaseDeDatosServicio.anularCodigosTarjetaBR(ventaBR, detalle, i, false);
							} catch (BaseDeDatosExcepcion e) {
								throw new BonoRegaloException("No se pudo registrar la anulación de tarjeta en la base de datos");
							}
						}
					} catch(BonoRegaloException br){
						MensajesVentanas.mensajeError("La transacción no pudo ser anulada\nEs necesario intentar nuevamente");
						i--;
					}
				}
			}
		} else{
			MensajesVentanas.aviso("Importante:\n Aunque se registró la anulación, el saldo de las tarjetas no fue reversado.\n Notifique al personal encargado de realizar el reverso y resguarde las tarjetas");
		}
		
		if(ventaBR.isTarjetasAnuladas()){
			//se actualiza estado de la transaccion si no esta facturada
			if(CR.meServ.getVentaBR().getEstadoTransaccion()!=Sesion.BONO_REGALO_FACTURADA){
				if(CR.meServ.getVentaBR().getEstadoTransaccion()!=Sesion.BONO_REGALO_LISTO && CR.meServ.getVentaBR().getEstadoTransaccion()!=Sesion.BONO_REGALO_EN_PROCESO)
					throw new BonoRegaloException("No es posible reimprimir la factura\nLa transacción no se encuentra facturada");
				else {
					try {
						if(CR.meServ.getVentaBR().getEstadoTransaccion()==Sesion.BONO_REGALO_EN_PROCESO)
							BaseDeDatosServicio.actualizarEstadoVentaBR(CR.meServ.getVentaBR(),Sesion.BONO_REGALO_ELIMINADA,autorizante,false,Sesion.getFechaSistema());
						else
							BaseDeDatosServicio.actualizarEstadoVentaBR(CR.meServ.getVentaBR(),Sesion.BONO_REGALO_ANULADA,autorizante,false,Sesion.getFechaSistema());
						BaseDeDatosServicio.actualizarVentaBRNoSincronizada(CR.meServ.getVentaBR(), false);
					} catch (BaseDeDatosExcepcion e) {
						logger.error("anularVentaBR()", e);
					}
				}
					
			}
			
			//Si esta facturada se emite nota de credito. El estado se actualizará en el committransaccion
			if(CR.meServ.getVentaBR().getEstadoTransaccion()==Sesion.BONO_REGALO_FACTURADA){
				CR.meServ.getVentaBR().setAccion(Sesion.ACCION_BONO_REGALO_ANULACION);
				try{
					while (CR.meVenta.getTransaccionPorImprimir()!=null || 
							CRFiscalPrinterOperations.isImpresionFiscalEnCurso() ||
							MaquinaDeEstadoVenta.isDocumentoNoFiscalPorImprimir()) {
						MensajesVentanas.aviso("Hay un documento en curso \nEspere un momento e intente nuevamente");
					}
					try {
						BaseDeDatosServicio.actualizarEstadoVentaBR(CR.meServ.getVentaBR(),Sesion.BONO_REGALO_ANULADA,autorizante,false,Sesion.getFechaSistema());
						BaseDeDatosServicio.actualizarVentaBRNoSincronizada(CR.meServ.getVentaBR(), false);
					} catch (BaseDeDatosExcepcion e) {
						logger.error("anularVentaBR()", e);
					}
					if (Sesion.impresoraFiscal) {
						CR.meServ.setTransaccionPorImprimir(ventaBR);
						CRFiscalPrinterOperations.setImpresionFiscalEnCurso(true);
					}
					ManejadorReportesFactory.getInstance().imprimirAnulacionBR(CR.meServ.getVentaBR(), null, false, true);
				} catch(ConexionExcepcion e){
					logger.error("anularVentaBR(",e);
				} catch(BaseDeDatosExcepcion e){
					logger.error("anularVentaBR(",e);
				}
			}
		}
		// Actualizamos el estado de la caja
		Sesion.getCaja().setEstado(edoFinalCaja);
		
		
	}
	
	/**
	 * Cambia el cliente asignado a la venta de bono regalo, si se encuentra facturada realiza al reimpresion
	 * @throws BonoRegaloException
	 */
	public void cambiarClienteAsignado() throws BonoRegaloException{
		
		int respuesta = MensajesVentanas.preguntarSiNo("¿Desea cambiar el cliente asignado?");
		if (respuesta==0){ //Si desea eliminarlo
			try {
				ventaBR.setClienteAnterior(ventaBR.getCliente());
				CR.meServ.quitarCliente();
			} catch (XmlExcepcion e1) {
				e1.printStackTrace();
			} catch (FuncionExcepcion e1) {
				e1.printStackTrace();
			} catch (BaseDeDatosExcepcion e1) {
				e1.printStackTrace();
			} catch (ConexionExcepcion e1) {
				e1.printStackTrace();
			} catch (ExcepcionCr e1){
				MensajesVentanas.mensajeError(e1.getMensaje());
			}
		}
		RegistroCliente registro = new RegistroClienteFactory().getInstance();
		registro.MostrarPantallaCliente(false,6, false);
		
		if(ventaBR.getEstadoTransaccion()==Sesion.BONO_REGALO_FACTURADA){
			CR.meServ.getVentaBR().setAccion(Sesion.ACCION_BONO_REGALO_REIMPRESION);
			//Reimprimir documento
			reimprimirFacturaBR(ventaBR.getClienteAnterior());
		}
		//Actualizar la transaccion
		BaseDeDatosServicio.actualizarClienteAsignado(ventaBR);
		try {
			BaseDeDatosServicio.actualizarVentaBRNoSincronizada(ventaBR, false);
		} catch (BaseDeDatosExcepcion e) {
			logger.error("cambiarClienteAsignado()",e);
		}
	} 
	
	/**
	 * Deshace las promociones que se crean al totalizar
	 * @param pagosRealizados
	 * @param pagosRealizadosAnteriormente
	 */	
	public void deshacerPromocionesBR(Vector<Pago> pagosRealizados, Vector<Pago> pagosRealizadosAnteriormente){
		pagosRealizadosAnteriormente.clear();
		for (int i=0; i<pagosRealizados.size(); i++)
			pagosRealizadosAnteriormente.addElement(pagosRealizados.elementAt(i));
		
	}
	
	/**
	 * Aplica las promociones correspondientes a un pago realizado con forma de pago Bono Regalo
	 * @param pagos
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void aplicarPromocionesBR(Vector<Pago> pagos, Cliente cliente){
		for(int i=0;i<pagos.size();i++){
			Pago pago = (Pago)pagos.elementAt(i);
			if(pago.getFormaPago().getCodigo().equalsIgnoreCase(Sesion.FORMA_PAGO_BONOREGALO_E)){
				Vector<PromocionBR> promocionesActivasBR = CR.me.getPromocionesActivasBR();
				double monto = pago.getMonto();
				for(int j=0;j<promocionesActivasBR.size();j++){
					PromocionBR promocion = (PromocionBR)promocionesActivasBR.elementAt(j);
					switch(promocion.getTipo()){
					case Sesion.TIPO_PROMOCION_BR_PORC_RECARGA:
						double recarga = MathUtil.roundDouble((monto*promocion.getPorcentaje())/100);
						try {
							if(recarga>=1)
								CR.me.recargarTarjetaPorPromocion(recarga,cliente, pago.getNumDocumento());
						} catch (UsuarioExcepcion e) {
							e.printStackTrace();
						} catch (MaquinaDeEstadoExcepcion e) {
							e.printStackTrace();
						} catch (XmlExcepcion e) {
							e.printStackTrace();
						} catch (FuncionExcepcion e) {
							e.printStackTrace();
						} catch (ConexionExcepcion e) {
							e.printStackTrace();
						} catch (ExcepcionCr e) {
							e.printStackTrace();
						} catch (PrinterNotConnectedException e) {
							e.printStackTrace();
						}
						break;
					}
				}
			}
		}
	}
}