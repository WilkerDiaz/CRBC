/**
 * =============================================================================
 * Proyecto   : CR
 * Paquete    : com.becoblohm.cr.manejarservicio
 * Programa   : ListaRegalos.java
 * Creado por : rabreu
 * Creado en  : 19/06/2006
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
package com.becoblohm.cr.manejarservicio;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.becoblohm.cr.excepciones.AfiliadoUsrExcepcion;
import com.becoblohm.cr.excepciones.AutorizacionExcepcion;
import com.becoblohm.cr.excepciones.BaseDeDatosExcepcion;
import com.becoblohm.cr.excepciones.ClienteExcepcion;
import com.becoblohm.cr.excepciones.ConexionExcepcion;
import com.becoblohm.cr.excepciones.ExcepcionCr;
import com.becoblohm.cr.excepciones.ExcepcionLR;
import com.becoblohm.cr.excepciones.FuncionExcepcion;
import com.becoblohm.cr.excepciones.MaquinaDeEstadoExcepcion;
import com.becoblohm.cr.excepciones.PagoExcepcion;
import com.becoblohm.cr.excepciones.ProductoExcepcion;
import com.becoblohm.cr.excepciones.SesionExcepcion;
import com.becoblohm.cr.excepciones.UsuarioExcepcion;
import com.becoblohm.cr.excepciones.XmlExcepcion;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.manejarventa.Venta;
import com.epa.crprinterdriver.PrinterNotConnectedException;

/**
 * @author rabreu
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
public class ListaRegalos extends Servicio implements Cloneable,Serializable {

	private static final Logger logger = Logger.getLogger(ListaRegalos.class);
	private DecimalFormat df = new DecimalFormat("#,##0.00");	
	public static char GARANTIZADA = 'G';
	public static char NOGARANTIZADA = 'N';
	public static char REGISTRO = 'R';
	public static char CONSULTA_INVITADO = 'I';
	public static char CONSULTA_TITULAR = 'T';
	public static char CIERRE_LISTA = 'C';
	public static char ABONO = 'A';
	public static char VENTA = 'V';
	public static char ABONO_TOTAL = 'T';
	public static String dbPdtUrlServidor, dbPdtUsuario, dbPdtClave;
	protected double montoVuelto;
	private static String CIERRE = "CERRADA";
	private static String ANULACION = "CERRADA";
	private boolean notificaciones = true;
	private boolean permitirVenta = true;
	private char tipoLista;
	private int codTiendaApertura,numCajaApertura,codTiendaCierre,numCajaCierre;
	private int cantPedidos = 0;
	private int cantAbonadosTotales = 0,cantVendidos = 0;
	private int diasAperturaLG = 0;
	private double montoAbonos = 0,montoVendidos = 0;
	private double montoRestantes = 0,montoAbonadosTotales = 0;
	private double montoMinimoAperturaLG = 0, montoAbonosLista = 0,montoAbonosProds = 0;
	private Cliente titular;
	private Date fechaApertura,fechaCierre,fechaEvento;
	private String nombreTitularSec = null,nombreInvitado = null;
	private String codInvitado = null,codCajeroApertura = null,codCajeroCierre = null;
	private String tipoEvento = null, dedicatoria = null;
	private Vector operacionesVendidos;
	private Vector operacionesNoVendidos;
	private Vector operacionesAbonadosTotales;
	private Vector detalleAbonadosTotales;
	private Vector detalleVendidos;
	private Vector operaciones = null;
	private Vector detalleAbonos = null;
	private Vector pagos = new Vector();
	private Vector detalleAnterior = null;
	private Venta ventaParcialDeCierre1;
	private Venta ventaParcialDeCierre2;
	private Venta ventaParcialDeCierre3;
	private Venta ventasParciales12;

	/**
	 * @throws SesionExcepcion
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	public ListaRegalos() throws SesionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion{
//		super(Sesion.getTienda().getNumero(), Sesion.LISTAREGALOS, Sesion.getFechaSistema(),
//			Sesion.getHoraSistema(),Sesion.usuarioActivo.getNumFicha(), Sesion.LISTAREGALOS_ACTIVA);
//		
//		Sesion.setUbicacion("LISTA DE REGALOS","crearListaRegalos");
//		Auditoria.registrarAuditoria("Creando Lista de Regalos para Tienda " + this.codTienda, 'T');
//		this.codCajero = Sesion.getCaja().getUsuarioLogueado();
//		
//		//Se cargan las preferencias de este servicio
//		this.cargarPreferenciasLR();
	}

	/**
	 * Constructor de Lista de Regalos que recupera una lista existente en la BD
	 * 
	 * @param numServicio Número de lista a recuperar
	 * @throws AutorizacionExcepcion
	 * @throws BaseDeDatosExcepcion
	 * @throws ClienteExcepcion
	 * @throws ProductoExcepcion
	 * @throws ConexionExcepcion
	 */
	public ListaRegalos(String numServicio) throws ConexionExcepcion, ExcepcionCr{
//		super(Sesion.getTienda().getNumero(), Sesion.LISTAREGALOS, Sesion.getFechaSistema(),
//				Sesion.getHoraSistema(),Sesion.usuarioActivo.getNumFicha(), Sesion.LISTAREGALOS_ACTIVA);
//
//		this.cargarPreferenciasLR();
//		
//		int numTiendaOrigen = BaseDeDatosServicio.obtenerTiendaOrigen(numServicio);
//		this.codCajero = Sesion.getCaja().getUsuarioLogueado();
//		
//		Sesion.setUbicacion("LISTA DE REGALOS","cargarListaRegalos");
//		Auditoria.registrarAuditoria("Cargando lista de regalos numero " + numServicio + " de Tienda " + numTiendaOrigen + " en Caja " + Sesion.getCaja().getNumero(), 'T');
//		
//		if(numTiendaOrigen!=codTienda) {
//			ListaRegalosRemota(numServicio,numTiendaOrigen);
//		}
//		else {
//			Vector lista = BaseDeDatosServicio.obtenerListaRegalos(numServicio);
//			Vector cabecera = (Vector)lista.elementAt(0);
//			Vector detallesServicio = (Vector)lista.elementAt(1); 
//			Vector detalleOper = (Vector)lista.elementAt(2);
//	
//			super.codTienda = Sesion.getTienda().getNumero();
//			super.tipoServicio = Sesion.LISTAREGALOS;
//	
//			// Se extrae la cabecera
//			this.numServicio = Integer.parseInt(cabecera.get(0).toString());
//			this.tipoLista = ((String)cabecera.get(1)).toCharArray()[0];
//			try {
//				// El titular es distinto al cliente en el caso inicial (ConsultaListaRegalos)
//				this.asignarCliente((String)cabecera.get(2), "");
//				this.titular = this.cliente;
//				this.cliente = null;
//			} catch (AutorizacionExcepcion e1) {
//				e1.printStackTrace();
//			} catch (AfiliadoUsrExcepcion e1) {
//				e1.printStackTrace();
//			} catch (BaseDeDatosExcepcion e1) {
//				e1.printStackTrace();
//			} catch (ClienteExcepcion e1) {
//				e1.printStackTrace();
//			} catch (ConexionExcepcion e1) {
//				e1.printStackTrace();
//			}
//			this.fechaEvento = (Date)cabecera.get(3);
//			this.tipoEvento = ((String)cabecera.get(4));
//			this.nombreTitularSec = (String)cabecera.get(5);
//			this.fechaApertura = (Date)cabecera.get(6);
//			this.codTiendaApertura = Integer.parseInt(cabecera.get(7).toString());
//			this.numCajaApertura = ((Integer)cabecera.get(8)).intValue();
//			this.codCajero = (String)cabecera.get(9);
//			this.fechaCierre = (Date)cabecera.get(10);
//			this.codTiendaCierre = ((Integer)cabecera.get(11)).intValue();
//			this.numCajaCierre = ((Integer)cabecera.get(12)).intValue();
//			this.codCajeroCierre = (String)cabecera.get(13);
//			this.montoBase = ((Double)cabecera.get(14)).doubleValue();
//			this.montoImpuesto = ((Double)cabecera.get(15)).doubleValue();
//			this.cantPedidos = ((Integer)cabecera.get(16)).intValue();
//			this.montoAbonosLista = ((Double)cabecera.get(17)).doubleValue();
//			this.notificaciones = (((String)cabecera.get(18)).equals("S"))
//								? true
//								: false;
//			this.permitirVenta = (((String)cabecera.get(19)).equals("S"))
//								? true
//								: false;
//			this.fechaServicio = Sesion.getFechaSistema();
//			this.horaInicia = Sesion.getHoraSistema();
//			
//			// Se extraen los detalles
//			for(int i=0;i<detallesServicio.size();i++){
//				Vector detalle = (Vector)detallesServicio.get(i);
//				int j=0;
//				String codLista = (String)detalle.get(j++);
//				String codProd = (String)detalle.get(j++);
//				//String descProd = (String)detalle.get(j++);
//				int correlativoitem = ((Integer)detalle.get(j++)).intValue();
//				int cantidad = ((Integer)detalle.get(j++)).intValue();
//				double precioRegular = ((Double)detalle.get(j++)).doubleValue();
//				double precioFinal = ((Double)detalle.get(j++)).doubleValue();
//				double mtoImpuesto = ((Double)detalle.get(j++)).doubleValue();
//				String tipoCap = (String)detalle.get(j++);
//				int codPromo = ((Integer)detalle.get(j++)).intValue();
//				int cantComprado = ((Integer)detalle.get(j++)).intValue();
//				int cantCancelados = ((Integer)detalle.get(j++)).intValue();
//				double abonos = ((Double)detalle.get(j++)).doubleValue();
//
//				try {
//					this.detallesServicio.addElement(new DetalleServicio(codProd,tipoCap,cantidad,this.fechaApertura,
//							this.horaInicia,precioRegular,precioFinal,montoImpuesto,cantComprado, cantCancelados, abonos));
//				} catch (ProductoExcepcion e) {
//					e.printStackTrace();
//				} catch (BaseDeDatosExcepcion e) {
//					e.printStackTrace();
//				} catch (ConexionExcepcion e) {
//					e.printStackTrace();
//				}
//			}
//
//			// Se extraen las operaciones sobre la lista
//			operaciones = new Vector();
//			for(int i=0;i<detalleOper.size();i++){
//				Vector operacion = (Vector)detalleOper.get(i);
//				int j=0;
//				int numOperacion = ((Integer)operacion.get(j++)).intValue();
//				String codProd = (String)operacion.get(j++);
//				String descripcion = (String)operacion.get(j++);
//				String nomCliente = (String)operacion.get(j++);
//				double montoBase = ((Double)operacion.get(j++)).doubleValue();
//				double montoImpuesto = ((Double)operacion.get(j++)).doubleValue();
//				int cantidad = ((Integer)operacion.get(j++)).intValue();
//				String fechaString = new SimpleDateFormat("yyyy-MM-dd").format((Date)operacion.get(j++));
//				String dedicatoria = (String)operacion.get(j++);
//				char tipoOper = ((Character)operacion.get(j++)).charValue();
//				int numTienda = ((Integer)operacion.get(j++)).intValue();
//				
//				Date fecha = new Date();
//				try {
//					fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaString);
//				} catch (ParseException e) { }
//		
//				this.operaciones.addElement(new OperacionLR(numOperacion,codProd,descripcion,nomCliente,
//									montoBase,montoImpuesto,cantidad,fecha,dedicatoria,tipoOper,numTienda));
//				if((tipoOper == 'A' || tipoOper == 'T')&& !codProd.equals("00000000000"))
//					montoAbonosProds += montoBase;
//			}
//			
//			// Se cargan los demás vectores importantes
//			cargarDetallesVendidos();
//			cargarDetallesNoVendidos();
//			cargarDetallesAbonadosTotales();
//		}
	}
		
	private void ListaRegalosRemota(String numServicio, int numTiendaOrigen) throws BaseDeDatosExcepcion, ConexionExcepcion, ClienteExcepcion, ProductoExcepcion {
//		Auditoria.registrarAuditoria("Cargan Lista de Regalos Remota " + this.numServicio + " de Tienda " + this.codTienda,'T');
//		IXMLElement listaxml = BaseDeDatosServicio.obtenerListaRegalosRemota(numServicio,numTiendaOrigen);
//
//		String nombre = listaxml.getFullName();
//		if(nombre.equals("respuesta")) {
//			String tipo = listaxml.getAttribute("tipo",null);
//			if(tipo.equals("listaregalos")){
//				IXMLElement cabecera = listaxml.getFirstChildNamed("cabecera");
//				IXMLElement codListaxml = cabecera.getFirstChildNamed("codlista");
//				String codListax = codListaxml.getContent();
//				if(codListax.equals(numServicio)){
//					// Comienzo a cargar la lista recibida en XML
//					this.numServicio = Integer.parseInt(numServicio);
//					this.tipoLista = (cabecera.getFirstChildNamed("tipolista").getContent()).toCharArray()[0];
//					this.codTiendaApertura = Integer.parseInt(cabecera.getFirstChildNamed("numtiendaapertura").getContent());
//					String codTitular = cabecera.getFirstChildNamed("codtitular").getContent();
//					
//					// Asignamos el cliente titular
//					try {
//						this.asignarCliente(codTitular, "");
//						this.titular = this.cliente;
//						this.cliente = null;
//					} catch (ClienteExcepcion e) {
//						// Si el cliente no existe en esta tienda, lo sincronizamos
//						MediadorBD.sincronizarAfiliadoRemoto(codTitular,codTiendaApertura);
//						try {
//							this.asignarCliente(codTitular, "");
//							this.titular = this.cliente;
//							this.cliente = null;
//						} catch (Exception e2) {
//							throw new ClienteExcepcion("No se pudo recuperar titular de la lista");
//						}
//					} catch (ConexionExcepcion e) {
//						e.printStackTrace();
//					} catch (Exception e){
//						throw new ClienteExcepcion("Error asignando cliente a Lista de Regalos");
//					}
//					
//					try {
//						this.fechaEvento = new SimpleDateFormat("yyyy-MM-dd").parse(cabecera.getFirstChildNamed("fechaevento").getContent());
//					} catch (ParseException e) {
//						this.fechaEvento = new Date();
//					}
//					this.tipoEvento = cabecera.getFirstChildNamed("tipoevento").getContent();
//					this.nombreTitularSec = cabecera.getFirstChildNamed("titularsec").getContent();
//					try {
//						this.fechaApertura = new SimpleDateFormat("yyyy-MM-dd").parse(cabecera.getFirstChildNamed("fechaapertura").getContent());
//					} catch (ParseException e) {
//						this.fechaApertura = new Date();
//					}
//					this.numCajaApertura = Integer.parseInt(cabecera.getFirstChildNamed("numcajaapertura").getContent());
//					this.codCajeroApertura = cabecera.getFirstChildNamed("codcajeroapertura").getContent();
//					try {
//						this.fechaCierre = new SimpleDateFormat("yyyy-MM-dd").parse(cabecera.getFirstChildNamed("fechacierre").getContent());
//					} catch (ParseException e) {
//						this.fechaCierre = new Date();
//					}						
//					this.codTiendaCierre = Integer.parseInt(cabecera.getFirstChildNamed("numtiendacierre").getContent());
//					this.numCajaCierre = Integer.parseInt(cabecera.getFirstChildNamed("numcajacierre").getContent());
//					this.codCajeroCierre = cabecera.getFirstChildNamed("codcajerocierre").getContent();
//					this.montoBase = Double.parseDouble(cabecera.getFirstChildNamed("montobase").getContent());
//					this.montoImpuesto = Double.parseDouble(cabecera.getFirstChildNamed("montoimpuesto").getContent());
//					this.cantPedidos = Integer.parseInt(cabecera.getFirstChildNamed("cantproductos").getContent());
//					this.montoAbonosLista = Double.parseDouble(cabecera.getFirstChildNamed("montoabonoslista").getContent());
//					this.notificaciones = (cabecera.getFirstChildNamed("notificaciones").getContent().equals("S"))
//										? true
//										: false;
//					this.permitirVenta = (cabecera.getFirstChildNamed("permitirventa").getContent().equals("S"))
//										? true
//										: false;
//					
//					// Se extraen los detalles
//					IXMLElement detallesxml = listaxml.getFirstChildNamed("detalles");
//					Vector detalles = new Vector();
//					Enumeration enumdetalles = detallesxml.enumerateChildren();
//					while(enumdetalles.hasMoreElements()){
//						Vector detalle = new Vector();
//						Enumeration enumdetalle = ((IXMLElement)enumdetalles.nextElement()).enumerateChildren();
//						while(enumdetalle.hasMoreElements()){
//							String codProd = ((IXMLElement)enumdetalle.nextElement()).getContent();
//							int correlativoitem = Integer.parseInt(((IXMLElement)enumdetalle.nextElement()).getContent());
//							int cantidad = Integer.parseInt(((IXMLElement)enumdetalle.nextElement()).getContent());
//							double precioRegular = Double.parseDouble(((IXMLElement)enumdetalle.nextElement()).getContent());
//							double precioFinal = Double.parseDouble(((IXMLElement)enumdetalle.nextElement()).getContent());
//							double mtoImpuesto = Double.parseDouble(((IXMLElement)enumdetalle.nextElement()).getContent());
//							String tipoCap = ((IXMLElement)enumdetalle.nextElement()).getContent();
//							int codPromo = Integer.parseInt(((IXMLElement)enumdetalle.nextElement()).getContent());
//							int cantComprado = Integer.parseInt(((IXMLElement)enumdetalle.nextElement()).getContent());
//							int cantCancelados = Integer.parseInt(((IXMLElement)enumdetalle.nextElement()).getContent());
//							double abonos = Double.parseDouble(((IXMLElement)enumdetalle.nextElement()).getContent());
//							this.fechaServicio = Sesion.getFechaSistema();
//							this.horaInicia = Sesion.getHoraSistema();
//
//							try {
//								this.detallesServicio.addElement(new DetalleServicio(codProd,tipoCap,cantidad,this.fechaApertura,
//														this.horaInicia,precioRegular,precioFinal,mtoImpuesto,cantComprado,cantCancelados,abonos));
//							} catch (ProductoExcepcion e) {
//								e.printStackTrace();
//								throw new ProductoExcepcion("Producto Inexistente");
//							} catch (BaseDeDatosExcepcion e) {
//								e.printStackTrace();
//							} catch (ConexionExcepcion e) {
//								e.printStackTrace();
//							}
//						}
//					}
//					
//					// Se extraen las operaciones sobre la lista
//					operaciones = new Vector();
//					IXMLElement operacionesxml = listaxml.getFirstChildNamed("operaciones");
//					Enumeration enumoperaciones = operacionesxml.enumerateChildren();
//					while(enumoperaciones.hasMoreElements()){
//						Vector operacion = new Vector();
//						Enumeration enumoperacion = ((IXMLElement)enumoperaciones.nextElement()).enumerateChildren();
//						while(enumoperacion.hasMoreElements()){
//							int j=0;
//							
//							int numOperacion = Integer.parseInt(((IXMLElement)enumoperacion.nextElement()).getContent());
//							String codProd = ((IXMLElement)enumoperacion.nextElement()).getContent();
//							String descripcion = ((IXMLElement)enumoperacion.nextElement()).getContent();
//							String nomCliente = ((IXMLElement)enumoperacion.nextElement()).getContent();
//							double montoBase = Double.parseDouble(((IXMLElement)enumoperacion.nextElement()).getContent());
//							double montoImpuesto = Double.parseDouble(((IXMLElement)enumoperacion.nextElement()).getContent());
//							int cantidad = Integer.parseInt(((IXMLElement)enumoperacion.nextElement()).getContent());
//							String fechaString = ((IXMLElement)enumoperacion.nextElement()).getContent();
//							String dedicatoria = ((IXMLElement)enumoperacion.nextElement()).getContent();
//							char tipoOper = ((IXMLElement)enumoperacion.nextElement()).getContent().toCharArray()[0];
//							int numTienda = Integer.parseInt(((IXMLElement)enumoperacion.nextElement()).getContent());
//							
//							Date fecha = new Date();
//							try {
//								fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaString);
//							} catch (ParseException e) { }
//									
//							this.operaciones.addElement(new OperacionLR(numOperacion,codProd,descripcion,nomCliente,
//												montoBase,montoImpuesto,cantidad,fecha,dedicatoria,tipoOper,numTienda));
//							if((tipoOper == 'A' || tipoOper == 'T')&& !codProd.equals("00000000000"))
//								montoAbonosProds += montoBase;
//						}
//					}
//				}
//						
//				// Se cargan los demás vectores importantes
//				cargarDetallesVendidos();
//				cargarDetallesNoVendidos();
//				cargarDetallesAbonadosTotales();
//			}
//		}
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.manejarservicio.Servicio#anularProducto(int)
	 */
	public Vector anularProducto(int renglon) throws ProductoExcepcion {
//		DetalleServicio linea = null;
		Vector result = new Vector();
//		try {
//			// Obtenemos la linea a cambiar la cantidad
//			linea = (DetalleServicio)this.detallesServicio.elementAt(renglon);
//		} catch (Exception ex) {
//			throw (new ProductoExcepcion("Error al eliminar producto, no existe renglon " + renglon + " en la Lista de Regalos", ex));
//		}
//		
//		result.addElement(linea.getProducto().getDescripcionCorta());
//		result.addElement(new Double(linea.getPrecioFinal() + linea.getMontoImpuesto()));
//
//		Producto prod = linea.getProducto();
//		linea.anularProducto();
//		
//		// Cambiamos la condición de venta original para que sea recalculada
//		linea.setCondicionVenta(Sesion.condicionNormal);
//		
//		if (((DetalleServicio)detallesServicio.elementAt(renglon)).getCantidad() <= 0) {
//			//Auditoria.registrarAuditoria("Anulado(s) " + (((DetalleTransaccion)detallesTransaccion.elementAt(renglon)).getCantidad()+1) + " producto(s) " + linea.getProducto().getCodProducto() + " de la transaccion",'T');
//			detallesServicio.removeElementAt(renglon);
//		} /*else
//			Auditoria.registrarAuditoria("Anulado(s) 1 producto(s) " + linea.getProducto().getCodProducto() + " de la transaccion",'T');
//		*/
//		actualizarPreciosDetalle(prod,false);
//		actualizarMontoServ();
//		
//		cantPedidos--;
//		
		return result;
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.manejarservicio.Servicio#anularProducto(int)
	 */
	public Vector anularProducto(int renglon, float cantidad) throws ProductoExcepcion {
//		DetalleServicio linea = null;
		Vector result = new Vector();
//		
//		Sesion.setUbicacion("LISTA DE REGALOS","anularProducto");
//		
//		try {
//			// Obtenemos la linea a cambiar la cantidad
//			linea = (DetalleServicio)detallesServicio.elementAt(renglon);
//		} catch (Exception ex) {
//			throw (new ProductoExcepcion("Error al elimina producto, no existe renglon " + renglon + " en la Lista de Regalos", ex));
//		}
//		
//		result.addElement(linea.getProducto().getDescripcionCorta());
//		result.addElement(new Double(linea.getPrecioFinal() + linea.getMontoImpuesto()));
//		
//		// Verificamos si existen productos suficientes en el renglon
//		if (cantidad > linea.getCantRestantes())
//			throw (new ProductoExcepcion("Error al eliminar producto. No existe(n) " + cantidad + " producto(s) en el renglon"));
//
//		// Verificamos si el número es mayor a cero
//		if (cantidad <= 0)
//			throw (new ProductoExcepcion("Debe ingresar una cantidad mayor que cero (0)"));
//
//		// Chequeamos si la nueva cantidad es fraccionada, y si el producto permite cantidades fraccionadas
//		if ((!linea.getProducto().isIndicaFraccion())&&((cantidad % 1)!=0)) {
//			throw (new ProductoExcepcion("Error al cambiar cantidad de producto.\nEl producto " + linea.getProducto().getCodProducto() + " no admite cantidades fraccionadas (Decimales)"));
//		}
//		
//		Producto prod = linea.getProducto();
//		linea.anularProducto(cantidad);
//	
//		// Cambiamos la condición de venta original para que sea recalculada
//		linea.setCondicionVenta(Sesion.condicionNormal);
//	
//		if (((DetalleServicio)detallesServicio.elementAt(renglon)).getCantidad() <= 0) {
//			//Auditoria.registrarAuditoria("Anulado(s) " + (((DetalleServicio)detallesServicio.elementAt(renglon)).getCantidad()+cantidad) + " producto(s) " + linea.getProducto().getCodProducto() + " de la Lista de Regalos " + this.numServicio,'T');
//			detallesServicio.removeElementAt(renglon);
//		} else
//			//Auditoria.registrarAuditoria("Anulado(s) " + cantidad + " producto(s) " + linea.getProducto().getCodProducto() + " de la Lista de Regalos " + this.numServicio,'T');
//		actualizarPreciosDetalle(prod,false);
//		actualizarMontoServ();
//		
//		cantPedidos -= cantidad;
//		
		return result;
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.manejarservicio.Servicio#finalizarServicio()
	 */
	protected Vector finalizarServicio() throws BaseDeDatosExcepcion, ConexionExcepcion {
		return null;
	}

	/**
	 * @return
	 */
	private void cargarPreferenciasLR(){
//		double costoUT;
//		int montoMinimoLG;
//		LinkedHashMap preferencias = new LinkedHashMap();
//		
//		try {
//			preferencias = InitCR.preferenciasCR.getAllPreferecesForNode("listaregalos");
//			montoMinimoLG = Integer.parseInt(preferencias.get("MontoMinimoLG").toString());
//			costoUT = Integer.parseInt(preferencias.get("CostoUT").toString());
//			montoMinimoAperturaLG = montoMinimoLG * costoUT;
//			diasAperturaLG = Integer.parseInt(preferencias.get("MaxDiasAperturaLG").toString());
//			dbPdtUrlServidor = preferencias.get("DbPdtUrlServidor").toString();
//			dbPdtUsuario = preferencias.get("DbPdtUsuario").toString();
//			dbPdtClave = preferencias.get("DbPdtClave").toString();
//		} catch (NoSuchNodeException e) {
//			e.printStackTrace();
//		} catch (UnidentifiedPreferenceException e) {
//			e.printStackTrace();
//		}	
	}

	/**
	 * Completa el cierre de una lista de regalos
	 * 
	 * @return
	 */
	public void cerrarLista(double montoTrans, char estado,Vector detallesVenta) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ExcepcionCr, ConexionExcepcion, PrinterNotConnectedException {
//		Auditoria.registrarAuditoria("Cierre de Lista de Regalos " + this.numServicio + " de Tienda " + this.codTienda,'T');
//		this.fechaCierre = Sesion.getFechaSistema();
//		this.horaFin = Sesion.getHoraSistema();
//		this.numCajaCierre = Sesion.getCaja().getNumero();
//		this.codCajeroCierre = this.codCajero;
//		this.codTiendaCierre = Sesion.getTienda().getNumero();
//	
//		BaseDeDatosServicio.cerrarListaRegalos(this,estado,detallesVenta);
//	
//		detallesServicio=new Vector();
//		operaciones=new Vector();
//		operacionesAbonadosTotales=null;
//		operacionesVendidos=null;
//		operacionesNoVendidos=null;
//	
//		if(codTiendaApertura!=codTienda) {
//			IXMLElement listaxml = BaseDeDatosServicio.obtenerListaRegalosRemota(String.valueOf(numServicio),codTiendaApertura);
//
//			String nombre = listaxml.getFullName();
//			if(nombre.equals("respuesta")) {
//				String tipo = listaxml.getAttribute("tipo",null);
//				if(tipo.equals("listaregalos")){
//					// Se extraen los detalles
//					IXMLElement detallesxml = listaxml.getFirstChildNamed("detalles");
//					Vector detalles = new Vector();
//					Enumeration enumdetalles = detallesxml.enumerateChildren();
//					while(enumdetalles.hasMoreElements()){
//						Vector detalle = new Vector();
//						Enumeration enumdetalle = ((IXMLElement)enumdetalles.nextElement()).enumerateChildren();
//						while(enumdetalle.hasMoreElements()){
//							String codProd = ((IXMLElement)enumdetalle.nextElement()).getContent();
//							int correlativoitem = Integer.parseInt(((IXMLElement)enumdetalle.nextElement()).getContent());
//							int cantidad = Integer.parseInt(((IXMLElement)enumdetalle.nextElement()).getContent());
//							double precioRegular = Double.parseDouble(((IXMLElement)enumdetalle.nextElement()).getContent());
//							double precioFinal = Double.parseDouble(((IXMLElement)enumdetalle.nextElement()).getContent());
//							double mtoImpuesto = Double.parseDouble(((IXMLElement)enumdetalle.nextElement()).getContent());
//							String tipoCap = ((IXMLElement)enumdetalle.nextElement()).getContent();
//							int codPromo = Integer.parseInt(((IXMLElement)enumdetalle.nextElement()).getContent());
//							int cantComprado = Integer.parseInt(((IXMLElement)enumdetalle.nextElement()).getContent());
//							int cantCancelados = Integer.parseInt(((IXMLElement)enumdetalle.nextElement()).getContent());
//							double abonos = Double.parseDouble(((IXMLElement)enumdetalle.nextElement()).getContent());
//							this.fechaServicio = Sesion.getFechaSistema();
//							this.horaInicia = Sesion.getHoraSistema();
//		
//							try {
//								this.detallesServicio.addElement(new DetalleServicio(codProd,tipoCap,cantidad,this.fechaApertura,
//														this.horaInicia,precioRegular,precioFinal,mtoImpuesto,cantComprado,cantCancelados,abonos));
//							} catch (ProductoExcepcion e) {
//								e.printStackTrace();
//								throw new ProductoExcepcion("Producto Inexistente");
//							} catch (BaseDeDatosExcepcion e) {
//								e.printStackTrace();
//							} catch (ConexionExcepcion e) {
//								e.printStackTrace();
//							}
//						}
//					}
//							
//					// Se extraen las operaciones sobre la lista
//					operaciones = new Vector();
//					IXMLElement operacionesxml = listaxml.getFirstChildNamed("operaciones");
//					Enumeration enumoperaciones = operacionesxml.enumerateChildren();
//					while(enumoperaciones.hasMoreElements()){
//						Vector operacion = new Vector();
//						Enumeration enumoperacion = ((IXMLElement)enumoperaciones.nextElement()).enumerateChildren();
//						while(enumoperacion.hasMoreElements()){
//							int j=0;
//									
//							int numOperacion = Integer.parseInt(((IXMLElement)enumoperacion.nextElement()).getContent());
//							String codProd = ((IXMLElement)enumoperacion.nextElement()).getContent();
//							String descripcion = ((IXMLElement)enumoperacion.nextElement()).getContent();
//							String nomCliente = ((IXMLElement)enumoperacion.nextElement()).getContent();
//							double montoBase = Double.parseDouble(((IXMLElement)enumoperacion.nextElement()).getContent());
//							double montoImpuesto = Double.parseDouble(((IXMLElement)enumoperacion.nextElement()).getContent());
//							int cantidad = Integer.parseInt(((IXMLElement)enumoperacion.nextElement()).getContent());
//							String fechaString = ((IXMLElement)enumoperacion.nextElement()).getContent();
//							String dedicatoria = ((IXMLElement)enumoperacion.nextElement()).getContent();
//							char tipoOper = ((IXMLElement)enumoperacion.nextElement()).getContent().toCharArray()[0];
//							int numTienda = Integer.parseInt(((IXMLElement)enumoperacion.nextElement()).getContent());
//									
//							Date fecha = new Date();
//							try {
//								fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaString);
//							} catch (ParseException e) { }
//											
//							this.operaciones.addElement(new OperacionLR(numOperacion,codProd,descripcion,nomCliente,
//												montoBase,montoImpuesto,cantidad,fecha,dedicatoria,tipoOper,numTienda));
//						}
//					}
//				}
//								
//				// Se cargan los demás vectores importantes
//				cargarDetallesVendidos();
//				cargarDetallesNoVendidos();
//				cargarDetallesAbonadosTotales();
//			}
//		} else {
//			Vector lista = BaseDeDatosServicio.obtenerListaRegalos(String.valueOf(numServicio));
//			Vector detallesServicio = (Vector)lista.elementAt(1); 
//			Vector detalleOper = (Vector)lista.elementAt(2);
//			
//			// Se extraen los detalles
//			for(int i=0;i<detallesServicio.size();i++){
//				Vector detalle = (Vector)detallesServicio.get(i);
//				int j=0;
//				String codLista = (String)detalle.get(j++);
//				String codProd = (String)detalle.get(j++);
//				int correlativoitem = ((Integer)detalle.get(j++)).intValue();
//				int cantidad = ((Integer)detalle.get(j++)).intValue();
//				double precioRegular = ((Double)detalle.get(j++)).doubleValue();
//				double precioFinal = ((Double)detalle.get(j++)).doubleValue();
//				double mtoImpuesto = ((Double)detalle.get(j++)).doubleValue();
//				String tipoCap = (String)detalle.get(j++);
//				int codPromo = ((Integer)detalle.get(j++)).intValue();
//				int cantComprado = ((Integer)detalle.get(j++)).intValue();
//				int cantCancelados = ((Integer)detalle.get(j++)).intValue();
//				double abonos = ((Double)detalle.get(j++)).doubleValue();
//
//				try {
//					this.detallesServicio.addElement(new DetalleServicio(codProd,tipoCap,cantidad,this.fechaApertura,
//							this.horaInicia,precioRegular,precioFinal,montoImpuesto,cantComprado, cantCancelados, abonos));
//				} catch (ProductoExcepcion e) {
//					e.printStackTrace();
//				} catch (BaseDeDatosExcepcion e) {
//					e.printStackTrace();
//				} catch (ConexionExcepcion e) {
//					e.printStackTrace();
//				}
//			}
//
//			// Se extraen las operaciones sobre la lista
//			for(int i=0;i<detalleOper.size();i++){
//				Vector operacion = (Vector)detalleOper.get(i);
//				int j=0;
//				int numOperacion = ((Integer)operacion.get(j++)).intValue();
//				String codProd = (String)operacion.get(j++);
//				String descripcion = (String)operacion.get(j++);
//				String nomCliente = (String)operacion.get(j++);
//				double montoBase = ((Double)operacion.get(j++)).doubleValue();
//				double montoImpuesto = ((Double)operacion.get(j++)).doubleValue();
//				int cantidad = ((Integer)operacion.get(j++)).intValue();
//				String fechaString = new SimpleDateFormat("yyyy-MM-dd").format((Date)operacion.get(j++));
//				String dedicatoria = (String)operacion.get(j++);
//				char tipoOper = ((Character)operacion.get(j++)).charValue();
//				int numTienda = ((Integer)operacion.get(j++)).intValue();
//				
//				Date fecha = new Date();
//				try {
//					fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaString);
//				} catch (ParseException e) { }
//		
//				this.operaciones.addElement(new OperacionLR(numOperacion,codProd,descripcion,nomCliente,
//									montoBase,montoImpuesto,cantidad,fecha,dedicatoria,tipoOper,numTienda));
//			}
//			
//			// Se cargan los demás vectores importantes
//			cargarDetallesVendidos();
//			cargarDetallesNoVendidos();
//			cargarDetallesAbonadosTotales();
//		}
//		
//		imprimirReporteCierreLista();
//		
//		Vector detEntrega = new Vector();
//		for(int i=0;i<detallesVenta.size();i++){
//			DetalleTransaccion det = (DetalleTransaccion)detallesVenta.get(i);
//			if(det.getCodTipoEntrega()==Sesion.COD_ENTREGA_DOMICILIO){
//				detEntrega.add(det);
//			}
//		}
//		if(detEntrega.size()>0)
//			imprimirNotaDespacho(detEntrega);
	}

	/**
	 * Completa el registro de una lista de regalos
	 * 
	 * @return
	 */
	public void registrarListaRegalos() throws ExcepcionCr{
//		Sesion.setUbicacion("LISTA DE REGALOS","registrarListaRegalos");
//		
//		if(this.tipoLista == GARANTIZADA)
//			if(montoBase < montoMinimoAperturaLG)
//				throw new ExcepcionLR("El monto mínimo para una\n Lista Garantizada es "+Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meServ.getListaRegalos().getMontoAperturaLG())));
//		
//		Auditoria.registrarAuditoria("Registro de Lista de Regalos " + this.numServicio + " de Tienda " + this.codTienda,'T');
//		
//		//Registramos el servicio
//		this.lineasFacturacion = this.detallesServicio.size();
//		this.horaFin = Sesion.getHoraSistema();
//		this.fechaApertura = Sesion.getFechaSistema();
//		this.numCajaApertura = Sesion.getCaja().getNumero();
//		this.codTiendaApertura = Sesion.getTienda().getNumero();
//		try {
//			this.numServicio = BaseDeDatosServicio.registrarListaRegalos(this);
//			imprimirReporteLista();
//		} catch(BaseDeDatosExcepcion e) {
//			try {
//				BaseDeDatosServicio.registrarClienteTemporal(this.cliente,false);
//				this.numServicio = BaseDeDatosServicio.registrarListaRegalos(this);
//				imprimirReporteLista();
//			} catch (BaseDeDatosExcepcion e1) {
//				throw new BaseDeDatosExcepcion("No se pudo registrar la lista de regalos.\nNo se puede sincronizar el nuevo afiliado.");
//			} catch (AfiliadoUsrExcepcion e1) {
//				throw new BaseDeDatosExcepcion("No se puede sincronizar el nuevo afiliado \npara la lista de regalos.");
//			} catch (ConexionExcepcion e1) {
//				throw new BaseDeDatosExcepcion("No se pudo registrar la lista de regalos.\nNo se puede sincronizar el nuevo afiliado.");
//			}
//		}
	}
	
	/**
	 * Completa la venta de un producto de una lista de regalos
	 * 
	 * @return
	 */
	public void registrarVenta(int numTransaccion,double montoVenta, Cliente cliente, 
							String nomInvitado,String dedicatoria,Vector detallesVenta) throws ConexionExcepcion {
								
//		Auditoria.registrarAuditoria("Venta de Lista de Regalos " + this.numServicio + " de Tienda " + this.codTienda,'T');
//		int codTienda = Sesion.getTienda().getNumero();
//		int numCaja = Sesion.getCaja().getNumero();
//		this.nombreInvitado = nomInvitado;
//		this.dedicatoria = dedicatoria;
//		int codTiendaOrigen = this.codTiendaApertura;		
//
//		if(cliente.getCodCliente() != null)
//			BaseDeDatosServicio.registrarVentaLR(numTransaccion,codTienda,codTiendaOrigen,numCaja,codCajero,this.numServicio,
//									cliente.getCodCliente(),cliente.getNombreCompleto(),dedicatoria,detallesVenta);
//		else
//			BaseDeDatosServicio.registrarVentaLR(numTransaccion,codTienda,codTiendaOrigen,numCaja,codCajero,this.numServicio,
//									"",nombreInvitado,dedicatoria,detallesVenta);
	}

	/**
	 * Agrega un abono parcial a un producto de una lista de regalos
	 * Si montoAbono es igual al precio del producto, se asume un abono total
	 * 
	 * @param montoAbono
	 * @param renglon
	 */
	public void agregarAbono(double montoAbono, int renglon) throws ExcepcionLR{
//		boolean existe = false;
//		DetalleServicio detalle = (DetalleServicio)this.getDetallesServicio().get(renglon);
//		
//		if(detalleAbonos==null)
//			detalleAbonos = new Vector();
//		
//		int cantActual = 0;
//		double montoActual = 0;
//		
//		// Se suma la cantidad de abonos totales y monto de abonos parciales existentes en detalleAbonos
//		for(int i=0;i<detalleAbonos.size();i++) {
//			Abono abono = (Abono)detalleAbonos.get(i); 
//			cantActual += abono.getCantidad();
//			montoActual += abono.getMontoBase();
//		}
//		
//		if(montoAbono - detalle.getPrecioFinal() > 1){
//			throw new ExcepcionLR("No puede realizar un abono parcial \npor un monto mayor al precio del producto");
//		}
//		if((montoAbono+montoActual)/detalle.getPrecioFinal()>detalle.getCantRestantes()){
//			throw new ExcepcionLR("No se puede realizar este abono \nintente un monto menor");
//		}
//		
//		for(int i=0;i<detalleAbonos.size();i++){
//			Abono abono = (Abono)detalleAbonos.get(i);
//			if(abono.getProducto() != null) {
//				String codProd = abono.getProducto().getCodProducto();
//				if(codProd.equals(detalle.getProducto().getCodProducto())){
//					if(abono.getTipoTransAbono()==ABONO){
//						abono.incrementarMontoAbonado(montoAbono);
//						detalleAbonos.set(i,abono);
//						existe = true;
//						break;
//					}
//				}
//			}
//		}
//		if(!existe) {
//			Abono nuevoAbono = new Abono(detalle.getProducto(),Sesion.getTienda().getNumero(),Sesion.getCaja().getNumero(),
//									Sesion.getFechaSistema(), Sesion.getHoraSistema(),montoAbono, Sesion.getCaja().getUsuarioLogueado());
//			nuevoAbono.setTipoTransAbono(ABONO);
//			nuevoAbono.incrementarCantidad(1);
//			detalleAbonos.add(nuevoAbono);
//		}
//		this.montoAbonos += montoAbono;
	}
	
	/**
	 * Agrega un abono total a un producto
	 * 
	 * @param cantidad Cantidad de productos
	 * @param renglon
	 */
	public void agregarAbono(int cantidad, int renglon) throws ExcepcionLR{
//		boolean existe = false;
//		DetalleServicio detalle = (DetalleServicio)this.getDetallesServicio().get(renglon);
//		double precioItem = detalle.getPrecioFinal()*((detalle.getProducto().getImpuesto().getPorcentaje()/100)+1);
//
//		if(detalleAbonos==null)
//			detalleAbonos = new Vector();
//
//		int cantActual = 0;
//		double montoActual = 0;
//		// Se suma la cantidad de abonos totales y monto de abonos parciales existentes en detalleAbonos
//		for(int i=0;i<detalleAbonos.size();i++) {
//			Abono abono = (Abono)detalleAbonos.get(i); 
//			cantActual += abono.getCantidad();
//			montoActual += abono.getMontoBase();
//		}
//
//		if(cantActual+cantidad>detalle.getCantRestantes())
//			throw new ExcepcionLR("No se puede realizar un abono por esta cantidad. Intente una menor.");
//
//		for(int i=0;i<detalleAbonos.size();i++){
//			Abono abono = (Abono)detalleAbonos.get(i);
//			if(abono.getProducto() != null) {
//				String codProd = abono.getProducto().getCodProducto();
//				if(codProd.equals(detalle.getProducto().getCodProducto())){
//					if(abono.getTipoTransAbono()==ABONO_TOTAL){
//						abono.incrementarMontoAbonado(precioItem*cantidad);
//						abono.incrementarCantidad(cantidad);
//						detalleAbonos.set(i,abono);
//						existe = true;
//						break;
//					}
//				}
//			}
//		}
//		if(!existe) {
//			Abono nuevoAbono = new Abono(detalle.getProducto(),Sesion.getTienda().getNumero(),Sesion.getCaja().getNumero(),
//									Sesion.getFechaSistema(), Sesion.getHoraSistema(),precioItem*cantidad, Sesion.getCaja().getUsuarioLogueado());
//			nuevoAbono.setTipoTransAbono(ABONO_TOTAL);
//			nuevoAbono.incrementarCantidad(cantidad);
//			detalleAbonos.add(nuevoAbono);
//		}
//		this.montoAbonos += cantidad;
	}
	
	/**
	 * Agrega un abono a una lista de regalos
	 * 
	 * @param montoAbono
	 */
	public void agregarAbono(double montoAbono){
//		int i;
//		boolean existe = false;
//		
//		if(detalleAbonos==null)
//			detalleAbonos = new Vector();
//
//		for(i=0;i<detalleAbonos.size();i++){
//			Abono abono = (Abono)detalleAbonos.get(i);
//			if(abono.getProducto() == null){
//				abono.incrementarMontoAbonado(montoAbono);
//				detalleAbonos.set(i,abono);
//				existe = true;
//				break;
//			}
//		}
//		if(!existe) {
//			Abono nuevoAbono = new Abono(null,Sesion.getTienda().getNumero(),Sesion.getCaja().getNumero(),
//									Sesion.getFechaSistema(), Sesion.getHoraSistema(),montoAbono, Sesion.getCaja().getUsuarioLogueado());
//			nuevoAbono.setTipoTransAbono('L');
//			detalleAbonos.add(nuevoAbono);
//		}
//		this.montoAbonos += montoAbono;
	}
	
	/**
	 * Elimina el vector de detalles de abonos
	 */
	public void eliminarAbono(int renglon){
//		detalleAbonos = null;
	}
	
	/**
	 * @return Monto total de abonos en detalles
	 */
	public double getMontoAbonos(){
		double monto = 0;
//		if(detalleAbonos!=null){
//			for(int i=0;i<detalleAbonos.size();i++){
//				Abono abono = (Abono)detalleAbonos.get(i);
//				monto += abono.getMontoBase();
//			}
//		}
		return monto;
	}
	
	/**
	 * @return Vector de detalles de abonos
	 */
	public Vector getDetalleAbonos(){
		return detalleAbonos;
	}

	/**
	 * @param vuelto
	 * @param nomInvitado
	 * @param dedicatoria
	 * @return
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	public void finalizarAbonos(double vuelto,String nomInvitado,String dedicatoria) throws ConexionExcepcion, ExcepcionCr {
//		Vector resultado;
//		int numTransaccion = 0;
//		this.nombreInvitado = nomInvitado;
//		this.dedicatoria = dedicatoria;
//		int codTienda = Sesion.getTienda().getNumero();
//		int numCaja = Sesion.getCaja().getNumero();
//		this.horaFin = Sesion.getHoraSistema();
//		numTransaccion = BaseDeDatosServicio.registrarAbonoLR(codTienda,codCajero,this,codInvitado,
//							nombreInvitado, dedicatoria, detalleAbonos, vuelto);
//		
//		imprimirReporteAbono(numTransaccion);
	}
	
	/**
	 * @return Tipo de lista
	 */
	public char getTipoLista(){
		return tipoLista;
	}
	
	/**
	 * @return Si desea recibir notificaciones
	 */
	public boolean isNotificaciones(){
		return notificaciones;
	}
	
	/**
	 * @return Fecha del evento
	 */
	public Date getFechaEvento() {
		return fechaEvento;
	}
	
	/**
	 * Define la fecha del evento
	 */
	public void setFechaEvento(Date fecha) {
		fechaEvento = fecha;
	}

	/**
	 * @return Tipo del evento
	 */
	public String getTipoEvento() {
		return tipoEvento;
	}

	/**
	 * @return Cliente titular de la lista
	 */
	public Cliente getTitular() {
		return titular;
	}
	
	/**
	 * @return Cliente secundario de la lista
	 */
	public String getTitularSec() {
		return nombreTitularSec;
	}

	/**
	 * Define el nombre del titular secundario
	 */
	public void setTitularSec(String nombre) {
		nombreTitularSec = nombre;
	}
	
	/**
	 * Define el tipo de la lista
	 */
	public void setTipoLista(char tipo) {
		tipoLista = tipo;
	}
	
	/**
	 * Define el tipo de evento
	 */	
	public void setTipoEvento(String evento) {
		tipoEvento = evento;
	}
	
	/**
	 * Define si se envian notificaciones
	 */	
	public void setNotificaciones(boolean notificar) {
		notificaciones = notificar;
	}
	
	/**
	 * @return Fecha de apertura de la lista
	 */
	public Date getFechaApertura() {
		return fechaApertura;
	}

	/**
	 * @return Codigo de caja de apertura
	 */
	public int getCodCajaApertura() {
		return numCajaApertura;
	}

	/**
	 * @return Fecha de cierre de la lista
	 */
	public Date getFechaCierre() {
		return fechaCierre;
	}

	/**
	 * @return Codigo de tienda de cierre
	 */
	public int getCodTiendaCierre() {
		return codTiendaCierre;
	}

	/**
	 * @return Codigo de tienda de apertura
	 */
	public int getCodTiendaApertura() {
		return codTiendaApertura;
	}
	
	/**
	 * @return Codigo de caja de cierre
	 */
	public int getCodCajaCierre() {
		return numCajaCierre;
	}

	/**
	 * @return Codigo de cajero de cierre
	 */
	public String getCodCajeroCierre() {
		return codCajeroCierre;
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
//		Sesion.setUbicacion("LISTA DE REGALOS","ingresarProductoLR");
//		
//		DetalleServicio detalle = new DetalleServicio(codProd, tipoCaptura, 1, this.fechaServicio, this.horaInicia);
//		Producto prod = detalle.getProducto();
//		
//		//Se chequea si es necesaria ingresar una cantidad decimal.
//		//requerimiento para que el ingresar línea producto no agregue cantidad 1 por defecto para el caso de cantidades fraccionadas
//		if (prod.isIndicaFraccion()) {
//			CantidadFraccionada cf = new CantidadFraccionada();
//			MensajesVentanas.centrarVentanaDialogo(cf);
//			detalle.setCantidad(cf.getCantidad());
//		}
//		detallesServicio.addElement(detalle);
//		
//		actualizarPreciosDetalle(prod,false);
//		actualizarMontoServ();
//		
//		// Registramos la auditoría de esta función
//		Auditoria.registrarAuditoria("Captura de Producto " + codProd + " por " + tipoCaptura + " para Apartado",'T');
//		cantPedidos++;
	}
	
	/**
	 * Metodo que cambia la cantidad de un producto
	 * @param nvaCantidad Nueva cantidad a asignar
	 * @param renglon Renglon donde se encuentra el producto. Si existen menos productos en el renglon que el
	 *		especificado en nvaCantidad, se eliminan de otros renglones con el mismo producto. 		
	 * @throws ProductoExcepcion Si no existe el renglón especificado o el producto no admite cantidades fraccionadas
	 * 		y se coloca una cantidad fraccionada como parametro nvaCantidad. Si ocurre un error de resitro de auditoria.
	 * 		Si ocurre un error al obtener metodo, modulo y funcion
	 */
	public void agregarCantidad(float nvaCantidad, int renglon) throws ProductoExcepcion {
//		Sesion.setUbicacion("LISTA DE REGALOS", "cambiarCantidad");
//		Auditoria.registrarAuditoria("Cambiando cantidad de Renglon " + renglon + ". Agregado(s) " + nvaCantidad + " productos",'T');
//		
//		Producto pCambiar;
//		float cantidadRenglon;
//		
//		// Observamos si el renglon del producto existe
//		try {
//			// Obtenemos el Producto a cambiar la cantidad
//			pCambiar = ((DetalleServicio)detallesServicio.elementAt(renglon)).getProducto();
//		} catch (Exception ex) {
//			throw (new ProductoExcepcion("Error al cambiar cantidad de producto, no existe renglon " + renglon + " en la Lista de Regalos", ex));
//		}
//		
//		// Chequeamos  si la cantidad es fraccionada, y si el producto permite cantidades fraccionadas
//		if ((!pCambiar.isIndicaFraccion())&&((nvaCantidad % 1)!=0)) {
//			throw (new ProductoExcepcion("Error al cambiar cantidad de producto.\nEl producto " + pCambiar.getCodProducto() + " no admite cantidades fraccionadas (Decimales)"));
//		}
//		
//		cantidadRenglon = ((DetalleServicio)detallesServicio.elementAt(renglon)).getCantidad();
//		if (nvaCantidad > 0) nvaCantidad = cantidadRenglon + nvaCantidad;
//		else throw (new ProductoExcepcion("Error al cambiar cantidades de productos. No se pueden agregar cantidades negativas"));
//		((DetalleServicio)detallesServicio.elementAt(renglon)).setCantidad(nvaCantidad);
//		((DetalleServicio)detallesServicio.elementAt(renglon)).setCondicionVenta(Sesion.condicionNormal);
//						
//		actualizarPreciosDetalle(pCambiar,false);
//		actualizarMontoServ();
//		cantPedidos += (nvaCantidad-cantidadRenglon);
	}
	
	/**
	 * @return Vector de operaciones sobre la lista
	 */
	public Vector getOperaciones(){
		return operaciones;
	}
	
	/**
	 * @return Dedicatoria
	 */
	public String getDedicatoria(){
		return dedicatoria;
	}
	
	/**
	 * @return Vector de pagos de abono
	 */
	public Vector getPagosAbono(){
		return pagos;
	}

	/**
	 * @param pagoAct
	 */
	public double realizarPagoAbono(Pago pagoAct) {
		double resto = montoAbonos - obtenerMontoAbonoPagado();
//		if (resto <= 0) {
//			this.montoVuelto = MathUtil.roundDouble(-resto);
//		}
		return resto;
	}

	/**
	 * @return Monto total pagado en el abono
	 */
	public double obtenerMontoAbonoPagado() {
		double monto = 0.0;
//		for (int i=0; i<pagos.size(); i++) {
//			monto += ((Pago)pagos.elementAt(i)).getMonto();
//		}
		return monto;
	}

	/**
	 * @return Monto total pagado en la operación
	 */
	public double getMontoPagado(){
		double monto = 0.0;
//		for (int i=0; i<pagos.size(); i++) {
//			monto += ((Pago)pagos.elementAt(i)).getMonto();
//		}
		return monto;
	}
	
	
	public void setNombreInvitado(String nombreInvitado){
//		this.nombreInvitado = nombreInvitado;
	}
	
	
	public String getNombreInvitado(){
		return nombreInvitado;
	}
	/**
	 * @return
	 */
	public double getMontoAbonosLista(){
		return montoAbonosLista;
	}
	/**
	 * @return
	 */	
	public double getMontoAbonosProds(){
		return montoAbonosProds;
	}

	/**
	 * Verifica si el producto indicado pertenece a la lista de regalos
	 */
	public boolean perteneceProducto(String codProd, String tipoIngreso){
//		String codigo = "";
//		if(tipoIngreso.equals(Sesion.capturaEscaner))
//			codigo = codProd.substring(0,9);
//		else if(tipoIngreso.equals(Sesion.capturaTeclado) && codProd.length() > 3) {
//			int longitud = codProd.length();
//			codProd = codProd.substring(0,longitud-3);
//			while(codProd.length() < 9){
//				codProd = "0"+codProd;
//			}
//			codigo = codProd;
//		}
//		
//		for(int i=0;i<detallesServicio.size();i++){
//			DetalleServicio detalle = (DetalleServicio)detallesServicio.get(i);
//			String cod = detalle.getProducto().getCodProducto().substring(1,10);
//			
//			if(codigo.equals(cod)){
//				return true;
//			}
//		}
		return false;
	}
	
	public Object clone(){
		ListaRegalos lista=null;
//		try {
//			lista = (ListaRegalos)super.clone();
//		} catch(CloneNotSupportedException ex) { }
//		lista.detallesServicio = (Vector) lista.detallesServicio.clone();
//		for(int i=0;i<detallesServicio.size();i++){
//			DetalleServicio detalle = ((DetalleServicio)lista.detallesServicio.get(i));
//			lista.detallesServicio.set(i,detalle.clone());
//		}
		return lista;
	}
	/**
	 * @return Cantidad de productos pedidos
	 */
	public int getCantPedidos() {
		return cantPedidos;
	}
	
	/**
	 * @param codigo
	 * @param autorizante
	 * @throws AutorizacionExcepcion
	 * @throws AfiliadoUsrExcepcion
	 * @throws BaseDeDatosExcepcion
	 * @throws ClienteExcepcion
	 * @throws ConexionExcepcion
	 */
	public void asignarTitular(String codigo, String autorizante) throws AutorizacionExcepcion, AfiliadoUsrExcepcion, BaseDeDatosExcepcion, ClienteExcepcion, ConexionExcepcion{
//		this.asignarCliente(codigo,autorizante);
//		this.titular = this.cliente;
	}
	/**
	 * @return Propiedad días mínimos previos de apertura 
	 * 			para Listas Garantizadas
	 */
	public int getDiasAperturaLG() {
		return diasAperturaLG;
	}

	/**
	 * @return Propiedad monto mínimo de apertura para
	 * 			Listas Garantizadas
	 */
	public double getMontoAperturaLG() {
		return montoMinimoAperturaLG;
	}
	
	
	/**
	 * @param codProd
	 * @return Precio regular del producto al momento de apertura
	 */
	public double getPrecioProd(String codProd){
//		DetalleServicio det = null;
		double precio = 0;
//		
//		for(int i=0;i<detallesServicio.size();i++){
//			det = (DetalleServicio)detallesServicio.get(i);
//			
//			if(codProd.equals(det.getProducto().getCodProducto())){
//				precio = det.getProducto().getPrecioRegular();
//				break;
//			}
//		}
		return precio;
	}
	/**
	 * @return
	 */
	public boolean isPermitirVenta() {
		return permitirVenta;
	}

	/**
	 * @param b
	 */
	public void setPermitirVenta(boolean b) {
		permitirVenta = b;
	}

	public DetalleServicio getDetalle(String codProd){
		DetalleServicio det = null;
//		for(int i=0;i<detallesServicio.size();i++){
//			det = (DetalleServicio)detallesServicio.get(i);
//			if(det.getProducto().getCodProducto().equals(codProd))
//				break;
//		}
		return det;
	}

	public Vector getDetalleVendidos(){
		return detalleVendidos;
	}
	
	public Vector getDetalleAbonadosTotales(){
		return detalleAbonadosTotales;
	}
	
	private void cargarDetallesAbonados(){
//		detalleAbonadosTotales = new Vector();
//		operacionesAbonadosTotales = new Vector();
//		for(int i=0;i<operaciones.size();i++){
//			OperacionLR op = (OperacionLR)operaciones.get(i);
//			if(op.getTipoOper()==ABONO_TOTAL){
//				detalleAbonadosTotales.add(this.getDetalle(op.getCodProducto()));
//				operacionesAbonadosTotales.add(op);
//				cantAbonadosTotales += op.getCantidad();
//				montoAbonadosTotales += op.getMontoBase();
//			}
//		}
	}
	
	public Vector getOperacionesAbonosTotales(){
		return operacionesAbonadosTotales;
	}

	private void cargarDetallesNoVendidos(){
//		operacionesNoVendidos = new Vector();
//		for(int i=0;i<detallesServicio.size();i++){
//			DetalleServicio det = (DetalleServicio)detallesServicio.get(i);
//			if(det.getCantRestantes()-det.getCantAbonadosT()>0)
//				operacionesNoVendidos.add(det);
//		}	
	}
	
	public Vector getDetalleNoVendidos(){
		return operacionesNoVendidos;
	}

	private void cargarDetallesVendidos(){
//		detalleVendidos = new Vector();
//		operacionesVendidos = new Vector();
//		for(int i=0;i<operaciones.size();i++){
//			OperacionLR op = (OperacionLR)operaciones.get(i);
//			if(op.getTipoOper()==VENTA){
//				detalleVendidos.add(this.getDetalle(op.getCodProducto()));
//				operacionesVendidos.add(op);
//				cantVendidos += op.getCantidad();
//				montoVendidos += op.getMontoBase();
//			}
//		}
	}

	public Vector getOperacionesVendidos(){
		Vector operacionesVendidos = new Vector();
//		for(int i=0;i<operaciones.size();i++){
//			OperacionLR op = (OperacionLR)operaciones.get(i);
//			if(op.getTipoOper()==VENTA){
//				operacionesVendidos.add(op);
//			}
//		}
		return operacionesVendidos;
	}

	/**
	 * 
	 */
	public void guardarVentaParcialCierre(Venta venta, int etapa) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, PagoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion{
//		if(etapa==1)
//			this.ventaParcialDeCierre1 = (Venta)venta.clone();
//		else if(etapa==2){
//			this.ventaParcialDeCierre2 = (Venta)venta.clone();
//			CR.meServ.getListaRegalos().concatenarVentasParciales1y2();
//		} else if(etapa==3)
//			this.ventaParcialDeCierre3 = (Venta)venta.clone();
	}
	/**
	 * @return
	 */
	public Venta getVentaParcialDeCierre1() {
		return ventaParcialDeCierre1;
	}

	/**
	 * @return
	 */
	public Venta getVentaParcialDeCierre2() {
		return ventaParcialDeCierre2;
	}
	
	/**
	 * @return
	 */
	public Venta getVentaParcialDeCierre3() {
		return ventaParcialDeCierre3;
	}
	
	/**
	 * Constructor de la clase Venta que crea una nueva basandose en la que se le pasa como parámetro pero con 
	 * sus nuevos números de registros.
	 */
	public void concatenarVentasParciales1y2() throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {		
//		ventasParciales12 = new Venta();
//		for (int i=0; i<ventaParcialDeCierre1.getDetallesTransaccion().size(); i++) {
//			DetalleTransaccion detalleActual = (DetalleTransaccion)ventaParcialDeCierre1.getDetallesTransaccion().elementAt(i);
//			ventasParciales12.getDetallesTransaccion().add(new DetalleTransaccion(ventaParcialDeCierre1.getCodCajero(), detalleActual.getProducto(), 
//					detalleActual.getCantidad(), detalleActual.getCondicionVenta(), detalleActual.getCodSupervisor(),
//					detalleActual.getPrecioFinal(), detalleActual.getMontoImpuesto(), detalleActual.getTipoCaptura(),
//					detalleActual.getCodPromocion(), detalleActual.getTipoEntrega()));
//		}
//		for (int i=0; i<ventaParcialDeCierre2.getDetallesTransaccion().size(); i++) {
//			DetalleTransaccion detalleActual = (DetalleTransaccion)ventaParcialDeCierre2.getDetallesTransaccion().elementAt(i);
//			ventasParciales12.getDetallesTransaccion().add(new DetalleTransaccion(ventaParcialDeCierre2.getCodCajero(), detalleActual.getProducto(), 
//					detalleActual.getCantidad(), detalleActual.getCondicionVenta(), detalleActual.getCodSupervisor(),
//					detalleActual.getPrecioFinal(), detalleActual.getMontoImpuesto(), detalleActual.getTipoCaptura(),
//					detalleActual.getCodPromocion(), detalleActual.getTipoEntrega()));
//		}
//		ventasParciales12.actualizarMontoTransaccion();
//		ventasParciales12.actualizarPreciosDetalle(false);
	}
	
	public Vector getVentasParciales1y2(){
		return ventasParciales12.getDetallesTransaccion();
	}

	/**
	 * @param codprod
	 * @return
	 */
	public Vector obtenerAbonos(String codprod) {
		Vector renglonesAbonos = new Vector();
//		if(detalleAbonos!=null){
//			for(int i=0;i<detalleAbonos.size();i++){
//				Abono abono = (Abono)detalleAbonos.get(i);
//				if(abono.getProducto()!=null && abono.getProducto().getCodProducto().equals(codprod))
//					renglonesAbonos.add(abono);
//			}
//		}
		return renglonesAbonos;
	}
	/**
	 * @return
	 */
	public int getCantAbonadosTotales() {
		return cantAbonadosTotales;
	}

	/**
	 * @return
	 */
	public int getCantRestantes() {
		int cantRestantes = cantPedidos>(cantVendidos+cantAbonadosTotales)
							 ? cantPedidos-cantVendidos-cantAbonadosTotales
							 : 0;
		return cantRestantes;
	}

	/**
	 * @return
	 */
	public double getMontoAbonadosTotales() {
		return montoAbonadosTotales;
	}

	/**
	 * @return
	 */
	public double getMontoRestantes() {
		double montoRestantes = montoBase>(montoVendidos+montoAbonadosTotales)
							  ? montoBase-montoVendidos-montoAbonadosTotales
							  : 0;
		return montoRestantes;
	}

	/**
	 * @return
	 */
	public double getMontoVendidos() {
		return montoVendidos;
	}

	/**
	 * @return
	 */
	public int getCantVendidos() {
		return cantVendidos;
	}

	/**
	 * @param numLote
	 */
	public Vector ingresarLoteProductos(String numLote) throws ConexionExcepcion, ExcepcionCr {
//		Vector loteProductos = BaseDeDatosServicio.obtenerLoteProductos(numLote);
		Vector noEncontrados = new Vector();
//		
//		for(int i=0;i<loteProductos.size();i++){
//			String[] producto = (String[])loteProductos.get(i);
//			String codProducto = producto[0];
//			int cantidad = Integer.parseInt(producto[1]); 
//			try{
//				this.ingresarLineaProducto(codProducto,"E");
//				if(cantidad > 1)
//					this.agregarCantidad(cantidad-1,detallesServicio.size()-1);
//			}catch(ProductoExcepcion e){
//				if(e.getMensaje().equals("Código no registrado")){
//					noEncontrados.add(producto);
//					loteProductos.set(i,null);
//				}
//			}
//		}
//		
//		// Si no se pueden agregar todos los productos se revierten los cambios
//		if(noEncontrados.size()>0){
//			for(int i=0;i<loteProductos.size();i++){
//				if(loteProductos.get(i)!=null){
//					String codigo = ((String[])loteProductos.get(i))[0];
//					float cantidad = Float.parseFloat(((String[])loteProductos.get(i))[1]);
//					Vector renglones = this.obtenerRenglones(codigo);
//					
//					for(int j=0;j<renglones.size();i++){
//						int renglon = ((Integer)renglones.get(i)).intValue();
//						float cantdetalle = ((Float)this.detallesServicio.get(renglon)).floatValue();
//						if(cantdetalle<cantidad){
//							this.anularProducto(renglon,cantdetalle);
//							cantidad -= cantdetalle;
//						}else{
//							this.anularProducto(renglon,cantidad);
//						}
//						if(cantidad==0)
//							break;
//					}
//				}				
//			}
//		}
		return noEncontrados;
	}

	/**
	 * 
	 */
	private void imprimirNotaDespacho(Vector detEntrega) throws ExcepcionCr {
//		ListaRegalos lista = CR.meServ.getListaRegalos();
//		Date fecha = lista.getFechaServicio();
//		String tipoEvento = lista.getTipoEvento();
//		int numTienda = lista.getCodTienda();
//		int numLista = lista.getNumServicio();
//		String nomCliente = lista.getTitular().getNombreCompleto();
//		String direccion = lista.getTitular().getDireccion();
//		String telefono = lista.getTitular().getNumTelefono();
//		String fechaEntrega = "";
//		String observaciones = "";
//		Vector detallesLista = lista.getDetallesServicio();
//		PrintUtilities.imprimirNotaDespacho(fecha,numTienda,numLista,nomCliente,
//				direccion,telefono,fechaEntrega,observaciones,detEntrega);
	}
	
	/**
	 * 
	 */
	public void imprimirReporteLista(boolean apertura) throws ExcepcionCr {
//		Date fechaApertura = this.getFechaApertura();
//		String tipoEvento = this.getTipoEvento();
//		int numTienda = this.getCodTienda();
//		int tiendaOrigen = this.getCodTiendaApertura();
//		int numLista = this.getNumServicio();
//		String titular = this.getTitular().getNombreCompleto();
//		String titularSec = this.getTitularSec();
//		Date fechaEvento = this.getFechaEvento();
//		Vector detallesLista = this.getDetallesServicio();
//		PrintUtilities.imprimirReporteLista(fechaApertura,tipoEvento,numTienda,numLista,
//					tiendaOrigen,titular,titularSec,fechaEvento,detallesLista);
	}

	/**
	 * 
	 */
	private void imprimirReporteCierreLista() throws ExcepcionCr {
//		Date fechaApertura = this.getFechaApertura();
//		String tipoEvento = this.getTipoEvento();
//		int numTienda = this.getCodTienda();
//		int tiendaOrigen = this.getCodTiendaApertura();
//		int numLista = this.getNumServicio();
//		String titular = this.getTitular().getNombreCompleto();
//		String titularSec = this.getTitularSec();
//		Date fechaEvento = this.getFechaEvento();
//		Vector detallesLista = this.getDetallesServicio();
//		PrintUtilities.imprimirReporteCierreLista(fechaApertura,tipoEvento,numTienda,numLista,
//						tiendaOrigen,titular,titularSec,fechaEvento,detallesLista);
	}
	/**
	 * 
	 */
	public void imprimirPrecierre() throws ExcepcionCr {
//		Date fecha = this.getFechaServicio();
//		String tipoEvento = this.getTipoEvento();
//		int numTienda = this.getCodTienda();
//		int numLista = this.getNumServicio();
//		String titular = this.getTitular().getNombreCompleto();
//		String titularSec = this.getTitularSec();
//		Date fechaEvento = this.getFechaEvento();
//		Vector detalleNoVendidos = this.getDetalleNoVendidos();
//		Vector operacionesVendidos = this.getOperacionesVendidos();
//		Vector operacionesAbonadosT = this.getOperacionesAbonadosTotales();
//		int tiendaOrigen = this.getCodTiendaApertura();
//
//		PrintUtilities.imprimirPrecierre(fecha,tipoEvento,numTienda,numLista,tiendaOrigen,
//				titular,titularSec,fechaEvento,detalleNoVendidos,operacionesVendidos,operacionesAbonadosT);
	}
	
	/**
	 * 
	 */
	public String getEstadoLista() {
		return null;//BaseDeDatosServicio.getEstadoListaRegalos(this.numServicio);
	}
	
	public Vector getOperacionesAbonosParciales(){
//		if(operacionesAbonadosParciales == null){
//			cargarDetallesAbonados();
//		}
		return null;//operacionesAbonadosParciales;
	}
	
	/**
	 * 
	 */
	private void imprimirAbonosAnteriores() {
		//AbonosAnterioresLR.imprimirComprobante(this);
	}
	

	/**
	 * 
	 */
	private void imprimirComprobanteAbono(int numTransaccion) throws ExcepcionCr {
		//PrintUtilities.imprimirReporteAbono(numTransaccion,this);
	}
	
	/**
	 * 
	 */	
	private void imprimirComprobanteBonoRegalo(double montoTrans, double montoAbonos) throws ExcepcionCr {
//		if(montoTrans<montoAbonos) {
//			double temp = (montoAbonos-montoTrans)/5000;
//			double montoBonos = Math.floor(temp)*5000; //Bonos en multiplo de 5000
//			double montoCambio = (temp-Math.floor(temp))*5000;  // Monto para cambio
//			imprimirComprobanteBonoRegalo(montoBonos,montoCambio);
//			PrintUtilities.imprimirComprobanteBonoRegalo(this,montoBonos,montoCambio);
//		}
	}
	
	/**
	 * Guarda las modificaciones realizadas a la lista en la base de datos.
	 */
	public void modificarDetallesListaRegalos() throws BaseDeDatosExcepcion, ConexionExcepcion, ExcepcionLR{
//		Sesion.setUbicacion("LISTA DE REGALOS","modificarListaRegalos");
//		
//		if(this.tipoLista == GARANTIZADA){
//			if(montoBase < montoMinimoAperturaLG)
//				throw new ExcepcionLR("El monto mínimo para una\n Lista Garantizada es "+Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meServ.getListaRegalos().getMontoAperturaLG())));
//		}
//		
//		Auditoria.registrarAuditoria("Modificando Detalles de Lista de Regalos " + this.numServicio + " de Tienda " + this.codTiendaApertura,'T');
//
//		BaseDeDatosServicio.modificarDetallesListaRegalos(this);
	}
	
	/**
	 * Guarda las modificaciones realizadas a la lista en la base de datos.
	 */
	public void modificarEncabezadoListaRegalos() throws BaseDeDatosExcepcion, ConexionExcepcion, ExcepcionLR{
//		Sesion.setUbicacion("LISTA DE REGALOS","modificarListaRegalos");
//		
//
//		Auditoria.registrarAuditoria("Modificando Encabezado de Lista de Regalos " + this.numServicio + " de Tienda " + this.codTiendaApertura,'T');
//
//		BaseDeDatosServicio.modificarEncabezadoListaRegalos(this);
	}
	
	/**
	 * Método recalculadoPromocionesApartado
	 * 
	 * @return
	 * boolean
	 */
	public boolean recalcularPromociones() {
		return false;
	}
	
	
	/**
	 * 
	 */
	public void setEstadoLista(char estado) throws BaseDeDatosExcepcion, ConexionExcepcion {
		//BaseDeDatosServicio.setEstadoListaRegalos(this.numServicio,estado);
	}
	
}