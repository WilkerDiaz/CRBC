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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Vector;

import net.n3.nanoxml.IXMLElement;

import org.apache.log4j.Logger;

import com.becoblohm.cr.CR;
import com.becoblohm.cr.InitCR;
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
import com.becoblohm.cr.gui.CantidadFraccionada;
import com.becoblohm.cr.gui.listaregalos.PrintUtilities;
import com.becoblohm.cr.manejadorauditoria.Auditoria;
import com.becoblohm.cr.manejadorsesion.Sesion;
import com.becoblohm.cr.extensiones.ManejadorReportesFactory;
import com.becoblohm.cr.extensiones.impl.manejarpago.Pago;
import com.becoblohm.cr.manejarventa.Cliente;
import com.becoblohm.cr.manejarventa.DetalleTransaccion;
import com.becoblohm.cr.manejarventa.Producto;
import com.becoblohm.cr.manejarventa.Promocion;
import com.becoblohm.cr.manejarventa.Venta;
import com.becoblohm.cr.mediadoresbd.BaseDeDatosServicio;
import com.becoblohm.cr.mediadoresbd.MediadorBD;
import com.becoblohm.cr.utiles.MathUtil;
import com.becoblohm.cr.utiles.MensajesVentanas;
import com.epa.crprinterdriver.PrinterNotConnectedException;
import com.epa.preferencesproxy.NoSuchNodeException;
import com.epa.preferencesproxy.UnidentifiedPreferenceException;

/**
 * @author rabreu
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
/**
 * @author rabreu
 *
 * Para cambiar la plantilla para este comentario de tipo generado vaya a
 * Ventana&gt;Preferencias&gt;Java&gt;Generación de código&gt;Código y comentarios
 */
/*
* En esta clase se realizaron modificaciones referentes a la migración a java 1.6 por jperez
* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
* Fecha: agosto 2011
*/
public class ListaRegalos extends Servicio implements Cloneable,Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ListaRegalos.class);
	private DecimalFormat df = new DecimalFormat("#,##0.00");	
	public static char GARANTIZADA = 'G';
	public static char NOGARANTIZADA = 'N';
	public static char REGISTRO = 'R';
	public static char CONSULTA_INVITADO = 'I';
	public static char CONSULTA_TITULAR = 'T';
	public static char CIERRE_LISTA = 'C';
	public static char INICIA_CIERRE = 'B';
	public static String dbPdtUrlServidor, dbPdtUsuario, dbPdtClave;
	protected double montoVuelto;
	private boolean notificaciones = true;
	private boolean permitirVenta = true;
	private char tipoLista;
	private int codTiendaApertura,numCajaApertura,codTiendaCierre,numCajaCierre;
	private int totalPedidos = 0;
	private int cantAbonadosTotales = 0,cantVendidos = 0;
	private int diasAperturaLG = 0;
	private double totalAbonos = 0,montoVendidos = 0;
	private double montoAbonosTotales = 0, montoAbonosLista = 0,montoAbonosProds = 0;
	private double montoMinimoAperturaLG = 0;
	private Cliente titular;
	private Date fechaApertura,fechaCierre,fechaEvento;
	private String nombreTitularSec = null,nombreInvitado = null;
	private String codInvitado = null,codCajeroCierre = null;
	private String tipoEvento = null, dedicatoria = null;
	private Vector<OperacionLR> operacionesVendidos;
	private Vector<DetalleServicio> operacionesNoVendidos;
	private Vector<OperacionLR> operacionesAbonosTotales;
	private Vector<OperacionLR> operacionesAbonosParciales;
	private Vector<OperacionLR> operacionesAbonosLista;
	private Vector<DetalleServicio> detalleAbonosTotales;
	private Vector<DetalleServicio> detalleVendidos;
	private Vector<OperacionLR> operaciones = null;
	private Vector<Abono> detalleAbonos = null;
	private Vector<Pago> pagos = new Vector<Pago>();
	private Venta ventaParcialCierre1;
	private Venta ventaParcialCierre2;
	private Venta ventaParcialCierre3;
	private Venta ventasParciales12;

	/**
	 * @throws SesionExcepcion
	 * @throws BaseDeDatosExcepcion
	 * @throws ConexionExcepcion
	 */
	public ListaRegalos() throws SesionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion{
		super(Sesion.getTienda().getNumero(), Sesion.LISTAREGALOS, Sesion.getFechaSistema(),
			Sesion.getHoraSistema(),Sesion.usuarioActivo.getNumFicha(), Sesion.LISTAREGALOS_ACTIVA);
		
		Sesion.setUbicacion("LISTA DE REGALOS","crearListaRegalos");
		Auditoria.registrarAuditoria("Creando Lista de Regalos para Tienda " + this.codTienda, 'T');
		this.codCajero = Sesion.getCaja().getUsuarioLogueado();
		
		//Se cargan las preferencias de este servicio
		this.cargarPreferenciasLR();
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
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unchecked")
	public ListaRegalos(String numServicio) throws ConexionExcepcion, ExcepcionCr{
		super(Sesion.getTienda().getNumero(), Sesion.LISTAREGALOS, Sesion.getFechaSistema(),
				Sesion.getHoraSistema(),Sesion.usuarioActivo.getNumFicha(), Sesion.LISTAREGALOS_ACTIVA);

		this.cargarPreferenciasLR();
		detalleAbonos = new Vector<Abono>();
		
		int numTiendaOrigen = BaseDeDatosServicio.obtenerTiendaOrigen(numServicio);
		this.codCajero = Sesion.getCaja().getUsuarioLogueado();
		
		Sesion.setUbicacion("LISTA DE REGALOS","cargarListaRegalos");
		Auditoria.registrarAuditoria("Cargando lista de regalos numero " + numServicio + " de Tienda " + numTiendaOrigen + " en Caja " + Sesion.getCaja().getNumero(), 'T');
		
		if(numTiendaOrigen!=codTienda) {
			ListaRegalosRemota(numServicio,numTiendaOrigen);
		} else {
			Vector<?> lista = BaseDeDatosServicio.obtenerListaRegalos(numServicio);
			Vector<Object> cabecera = (Vector<Object>)lista.elementAt(0);
			Vector<Vector<Object>> detallesServicio = (Vector<Vector<Object>>)lista.elementAt(1); 
			Vector<Vector<Object>> detalleOper = (Vector<Vector<Object>>)lista.elementAt(2);
	
			super.codTienda = Sesion.getTienda().getNumero();
			super.tipoServicio = Sesion.LISTAREGALOS;
	
			// Se extrae la cabecera
			this.numServicio = Integer.parseInt(cabecera.get(0).toString());
			this.tipoLista = ((String)cabecera.get(1)).toCharArray()[0];
			try {
				// El titular es distinto al cliente en el caso inicial (ConsultaListaRegalos)
				this.asignarCliente((String)cabecera.get(2), "");
				this.titular = this.cliente;
				this.cliente = null;
			} catch (AutorizacionExcepcion e1) {
				e1.printStackTrace();
			} catch (AfiliadoUsrExcepcion e1) {
				e1.printStackTrace();
			} catch (BaseDeDatosExcepcion e1) {
				e1.printStackTrace();
			} catch (ClienteExcepcion e1) {
				e1.printStackTrace();
			} catch (ConexionExcepcion e1) {
				e1.printStackTrace();
			}
			this.fechaEvento = (Date)cabecera.get(3);
			this.tipoEvento = ((String)cabecera.get(4));
			this.nombreTitularSec = (String)cabecera.get(5);
			this.fechaApertura = (Date)cabecera.get(6);
			this.codTiendaApertura = Integer.parseInt(cabecera.get(7).toString());
			this.numCajaApertura = ((Integer)cabecera.get(8)).intValue();
			//this.codCajeroApertura = (String)cabecera.get(9);
			this.fechaCierre = (Date)cabecera.get(10);
			this.codTiendaCierre = ((Integer)cabecera.get(11)).intValue();
			this.numCajaCierre = ((Integer)cabecera.get(12)).intValue();
			this.codCajeroCierre = (String)cabecera.get(13);
			this.montoBase = ((Double)cabecera.get(14)).doubleValue();
			this.montoImpuesto = ((Double)cabecera.get(15)).doubleValue();
			this.totalPedidos = ((Integer)cabecera.get(16)).intValue();
			this.montoAbonosLista = ((Double)cabecera.get(17)).doubleValue();
			this.notificaciones = (((String)cabecera.get(18)).equals("S"))
								? true
								: false;
			this.permitirVenta = (((String)cabecera.get(19)).equals("S"))
								? true
								: false;
			this.fechaServicio = Sesion.getFechaSistema();
			this.horaInicia = Sesion.getHoraSistema();
			
			// Se extraen los detalles
			for(int i=0;i<detallesServicio.size();i++){
				Vector<Object> detalle = detallesServicio.get(i);
				int j=0;
				j++; //codLista
				String codProd = (String)detalle.get(j++);
				j++; //correlativoitem
				int cantidad = ((Integer)detalle.get(j++)).intValue();
				double precioRegular = ((Double)detalle.get(j++)).doubleValue();
				double precioFinal = ((Double)detalle.get(j++)).doubleValue();
				double mtoImpuesto = ((Double)detalle.get(j++)).doubleValue();
				String tipoCaptura = (String)detalle.get(j++);
				j++; //codPromo
				int cantVendidos = ((Integer)detalle.get(j++)).intValue();
				int cantAbonadosT = ((Integer)detalle.get(j++)).intValue();
				double abonos = ((Double)detalle.get(j++)).doubleValue();

				try {
					this.detallesServicio.addElement(new DetalleServicio(codProd,tipoCaptura,cantidad,this.fechaApertura,
							this.horaInicia,precioRegular,precioFinal,mtoImpuesto,cantVendidos, cantAbonadosT, abonos));
				} catch (ProductoExcepcion e) {
					e.printStackTrace();
					throw new ExcepcionLR("Código de producto "+ codProd +" no registrado");
				} catch (BaseDeDatosExcepcion e) {
					e.printStackTrace();
					throw new ExcepcionLR("Error en base de datos cargando detalles de la lista");
				} catch (ConexionExcepcion e) {
					e.printStackTrace();
					throw new ExcepcionLR("Error de conexión cargando detalles de la lista");
				}
			}

			// Se extraen las operaciones sobre la lista
			operaciones = new Vector<OperacionLR>();
			for(int i=0;i<detalleOper.size();i++){
				Vector<Object> operacion = detalleOper.get(i);
				int j=0;
				int numOperacion = ((Integer)operacion.get(j++)).intValue();
				int numTransaccion = ((Integer)operacion.get(j++)).intValue();
				String codProd = (String)operacion.get(j++);
				String descripcion = (String)operacion.get(j++);
				String nomCliente = (String)operacion.get(j++);
				double montoOperacion = ((Double)operacion.get(j++)).doubleValue();
				double mtoImpuesto = ((Double)operacion.get(j++)).doubleValue();
				int cantidad = ((Integer)operacion.get(j++)).intValue();
				String fechaString = new SimpleDateFormat("yyyy-MM-dd").format((Date)operacion.get(j++));
				String dedicatoria = (String)operacion.get(j++);
				char tipoOper = ((Character)operacion.get(j++)).charValue();
				int numTienda = ((Integer)operacion.get(j++)).intValue();
				String codCajero = (String)operacion.get(j++);
				
				Date fecha = new Date();
				try {
					fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaString);
				} catch (ParseException e) { }
		
				this.operaciones.addElement(new OperacionLR(numOperacion,numTransaccion,codProd,descripcion,nomCliente,
									montoOperacion,mtoImpuesto,cantidad,fecha,dedicatoria,tipoOper,numTienda,codCajero));
			}
			
			// Se cargan los demás vectores importantes
			cargarDetallesVendidos();
			cargarDetallesNoVendidos();
			cargarDetallesAbonados();
			if(tipoLista==GARANTIZADA){
				//En garantizada se recalculan precios finales para validar promociones al abonar
				recalcularPromociones();
			}
		}
	}
		
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' y 'Enumeration'
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unchecked")
	private void ListaRegalosRemota(String numServicio, int numTiendaOrigen) throws BaseDeDatosExcepcion, ConexionExcepcion, ClienteExcepcion, ProductoExcepcion {
		Auditoria.registrarAuditoria("Cargando Lista de Regalos Remota " + this.numServicio + " de Tienda " + this.codTienda,'T');
		IXMLElement listaxml = BaseDeDatosServicio.obtenerListaRegalosRemota(numServicio,numTiendaOrigen);


		String nombre = listaxml.getFullName();
		if(nombre.equals("respuesta")) {
			String tipo = listaxml.getAttribute("tipo",null);
			if(tipo.equals("listaregalos")){
				IXMLElement cabecera = listaxml.getFirstChildNamed("cabecera");
				IXMLElement codListaxml = cabecera.getFirstChildNamed("codlista");
				String codListax = codListaxml.getContent();
				if(codListax.equals(numServicio)){
					// Comienzo a cargar la lista recibida en XML
					this.numServicio = Integer.parseInt(numServicio);
					this.tipoLista = (cabecera.getFirstChildNamed("tipolista").getContent()).toCharArray()[0];
					this.codTiendaApertura = Integer.parseInt(cabecera.getFirstChildNamed("numtiendaapertura").getContent());
					String codTitular = cabecera.getFirstChildNamed("codtitular").getContent();
					
					// Asignamos el cliente titular
					try {
						this.asignarCliente(codTitular, "");
						this.titular = this.cliente;
						this.cliente = null;
					} catch (ClienteExcepcion e) {
						// Si el cliente no existe en esta tienda, se intenta sincronizarlo
						MediadorBD.sincronizarAfiliadoRemoto(codTitular,codTiendaApertura);
						try {
							this.asignarCliente(codTitular, "");
							this.titular = this.cliente;
							this.cliente = null;
						} catch (Exception e2) {
							throw new ClienteExcepcion("No se pudo sincronizar titular de la lista");
						}
					} catch (ConexionExcepcion e) {
						e.printStackTrace();
					} catch (Exception e){
						throw new ClienteExcepcion("Error asignando cliente a Lista de Regalos");
					}
					
					try {
						this.fechaEvento = new SimpleDateFormat("yyyy-MM-dd").parse(cabecera.getFirstChildNamed("fechaevento").getContent());
					} catch (ParseException e) {
						this.fechaEvento = new Date();
					}
					this.tipoEvento = cabecera.getFirstChildNamed("tipoevento").getContent();
					this.nombreTitularSec = cabecera.getFirstChildNamed("titularsec").getContent();
					try {
						this.fechaApertura = new SimpleDateFormat("yyyy-MM-dd").parse(cabecera.getFirstChildNamed("fechaapertura").getContent());
					} catch (ParseException e) {
						this.fechaApertura = new Date();
					}
					this.numCajaApertura = Integer.parseInt(cabecera.getFirstChildNamed("numcajaapertura").getContent());
					//this.codCajeroApertura = cabecera.getFirstChildNamed("codcajeroapertura").getContent();
					try {
						this.fechaCierre = new SimpleDateFormat("yyyy-MM-dd").parse(cabecera.getFirstChildNamed("fechacierre").getContent());
					} catch (ParseException e) {
						this.fechaCierre = new Date();
					}						
					this.codTiendaCierre = Integer.parseInt(cabecera.getFirstChildNamed("numtiendacierre").getContent());
					this.numCajaCierre = Integer.parseInt(cabecera.getFirstChildNamed("numcajacierre").getContent());
					this.codCajeroCierre = cabecera.getFirstChildNamed("codcajerocierre").getContent();
					this.montoBase = Double.parseDouble(cabecera.getFirstChildNamed("montobase").getContent());
					this.montoImpuesto = Double.parseDouble(cabecera.getFirstChildNamed("montoimpuesto").getContent());
					this.totalPedidos = Integer.parseInt(cabecera.getFirstChildNamed("cantproductos").getContent());
					this.montoAbonosLista = Double.parseDouble(cabecera.getFirstChildNamed("montoabonoslista").getContent());
					this.notificaciones = (cabecera.getFirstChildNamed("notificaciones").getContent().equals("S"))
										? true
										: false;
					this.permitirVenta = (cabecera.getFirstChildNamed("permitirventa").getContent().equals("S"))
										? true
										: false;
					
					// Se extraen los detalles
					IXMLElement detallesxml = listaxml.getFirstChildNamed("detalles");
					Enumeration<IXMLElement> enumdetalles = detallesxml.enumerateChildren();
					while(enumdetalles.hasMoreElements()){
						Enumeration<IXMLElement> enumdetalle = ((IXMLElement)enumdetalles.nextElement()).enumerateChildren();
						while(enumdetalle.hasMoreElements()){
							String codProd = ((IXMLElement)enumdetalle.nextElement()).getContent();
							enumdetalle.nextElement(); //correlativoitem
							int cantidad = Integer.parseInt(((IXMLElement)enumdetalle.nextElement()).getContent());
							double precioRegular = Double.parseDouble(((IXMLElement)enumdetalle.nextElement()).getContent());
							double precioFinal = Double.parseDouble(((IXMLElement)enumdetalle.nextElement()).getContent());
							double mtoImpuesto = Double.parseDouble(((IXMLElement)enumdetalle.nextElement()).getContent());
							String tipoCap = ((IXMLElement)enumdetalle.nextElement()).getContent();
							enumdetalle.nextElement(); //codPromo
							int cantComprado = Integer.parseInt(((IXMLElement)enumdetalle.nextElement()).getContent());
							int cantCancelados = Integer.parseInt(((IXMLElement)enumdetalle.nextElement()).getContent());
							double abonos = Double.parseDouble(((IXMLElement)enumdetalle.nextElement()).getContent());
							this.fechaServicio = Sesion.getFechaSistema();
							this.horaInicia = Sesion.getHoraSistema();

							try {
								this.detallesServicio.addElement(new DetalleServicio(codProd,tipoCap,cantidad,this.fechaApertura,
													this.horaInicia,precioRegular,precioFinal,mtoImpuesto,cantComprado,cantCancelados,abonos));
							} catch (ProductoExcepcion e) {
								e.printStackTrace();
								throw new ProductoExcepcion("Producto " + codProd + " inexistente");
							} catch (BaseDeDatosExcepcion e) {
								e.printStackTrace();
							} catch (ConexionExcepcion e) {
								e.printStackTrace();
							}
						}
					}
					
					// Se extraen las operaciones sobre la lista
					operaciones = new Vector<OperacionLR>();
					IXMLElement operacionesxml = listaxml.getFirstChildNamed("operaciones");
					Enumeration<IXMLElement> enumoperaciones = operacionesxml.enumerateChildren();
					while(enumoperaciones.hasMoreElements()){
						Enumeration<IXMLElement> enumoperacion = ((IXMLElement)enumoperaciones.nextElement()).enumerateChildren();
						while(enumoperacion.hasMoreElements()){
							int numOperacion = Integer.parseInt(((IXMLElement)enumoperacion.nextElement()).getContent());
							String numTransTemp = ((IXMLElement)enumoperacion.nextElement()).getContent();
							int numTransaccion = numTransTemp==null
											   ? 0
											   : Integer.parseInt(numTransTemp);
							String codProd = ((IXMLElement)enumoperacion.nextElement()).getContent();
							String descripcion = ((IXMLElement)enumoperacion.nextElement()).getContent();
							String nomCliente = ((IXMLElement)enumoperacion.nextElement()).getContent();
							double montoOperacion = Double.parseDouble(((IXMLElement)enumoperacion.nextElement()).getContent());
							double montoImpuesto = Double.parseDouble(((IXMLElement)enumoperacion.nextElement()).getContent());
							int cantidad = Integer.parseInt(((IXMLElement)enumoperacion.nextElement()).getContent());
							String fechaString = ((IXMLElement)enumoperacion.nextElement()).getContent();
							String dedicatoria = ((IXMLElement)enumoperacion.nextElement()).getContent();
							char tipoOper = ((IXMLElement)enumoperacion.nextElement()).getContent().toCharArray()[0];
							int numTienda = Integer.parseInt(((IXMLElement)enumoperacion.nextElement()).getContent());
							String codCajero = ((IXMLElement)enumoperacion.nextElement()).getContent();
							
							Date fecha = new Date();
							try {
								fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaString);
							} catch (ParseException e) { }
									
							this.operaciones.addElement(new OperacionLR(numOperacion,numTransaccion,codProd,descripcion,nomCliente,
												montoOperacion,montoImpuesto,cantidad,fecha,dedicatoria,tipoOper,numTienda,codCajero));
							System.out.println("Cargando Operacion: " + numOperacion);
						}
					}
				}
						
				// Se cargan los demás vectores importantes
				cargarDetallesVendidos();
				cargarDetallesNoVendidos();
				cargarDetallesAbonados();
			}
		}
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.manejarservicio.Servicio#anularProducto(int)
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Object> anularProducto(int renglon) throws ProductoExcepcion {
		DetalleServicio linea = null;
		Vector<Object> result = new Vector<Object>();
		try {
			// Obtenemos la linea a cambiar la cantidad
			linea = (DetalleServicio)this.detallesServicio.elementAt(renglon);
		} catch (Exception ex) {
			throw (new ProductoExcepcion("Error al eliminar producto, no existe renglon " + renglon + " en la Lista de Regalos", ex));
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
		actualizarPreciosDetalle(prod,false);
		actualizarMontoServ();
		
		totalPedidos--;
		
		return result;
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.manejarservicio.Servicio#anularProducto(int)
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Object> anularProducto(int renglon, float cantidad) throws ProductoExcepcion {
		DetalleServicio linea = null;
		Vector<Object> result = new Vector<Object>();
		
		Sesion.setUbicacion("LISTA DE REGALOS","anularProducto");
		
		try {
			// Obtenemos la linea a cambiar la cantidad
			linea = (DetalleServicio)detallesServicio.elementAt(renglon);
		} catch (Exception ex) {
			throw (new ProductoExcepcion("Error al elimina producto, no existe renglon " + renglon + " en la Lista de Regalos", ex));
		}
		
		result.addElement(linea.getProducto().getDescripcionCorta());
		result.addElement(new Double(linea.getPrecioFinal() + linea.getMontoImpuesto()));
		
		// Verificamos si existen productos suficientes en el renglon
		if (cantidad > linea.getCantRestantes())
			throw (new ProductoExcepcion("Error al eliminar producto. No existe(n) " + cantidad + " producto(s) en el renglon"));

		// Verificamos si el número es mayor a cero
		if (cantidad <= 0)
			throw (new ProductoExcepcion("Debe ingresar una cantidad mayor que cero (0)"));

		// Chequeamos si la nueva cantidad es fraccionada, y si el producto permite cantidades fraccionadas
		if ((!linea.getProducto().isIndicaFraccion())&&((cantidad % 1)!=0)) {
			throw (new ProductoExcepcion("Error al cambiar cantidad de producto.\nEl producto " + linea.getProducto().getCodProducto() + " no admite cantidades fraccionadas (Decimales)"));
		}
		
		Producto prod = linea.getProducto();
		linea.anularProducto(cantidad);
	
		// Cambiamos la condición de venta original para que sea recalculada
		linea.setCondicionVenta(Sesion.condicionNormal);
	
		if (((DetalleServicio)detallesServicio.elementAt(renglon)).getCantidad() <= 0) {
			//Auditoria.registrarAuditoria("Anulado(s) " + (((DetalleServicio)detallesServicio.elementAt(renglon)).getCantidad()+cantidad) + " producto(s) " + linea.getProducto().getCodProducto() + " de la Lista de Regalos " + this.numServicio,'T');
			detallesServicio.removeElementAt(renglon);
		} else
			//Auditoria.registrarAuditoria("Anulado(s) " + cantidad + " producto(s) " + linea.getProducto().getCodProducto() + " de la Lista de Regalos " + this.numServicio,'T');
		actualizarPreciosDetalle(prod,false);
		actualizarMontoServ();
		
		totalPedidos -= cantidad;
		
		return result;
	}

	/* (no Javadoc)
	 * @see com.becoblohm.cr.manejarservicio.Servicio#finalizarServicio()
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	protected Vector<Object> finalizarServicio() throws BaseDeDatosExcepcion, ConexionExcepcion {
		return null;
	}

	/**
	 * @return
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'LinkedHashMap'
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unchecked")
	private void cargarPreferenciasLR(){
		double costoUT;
		int montoMinimoLG;
		LinkedHashMap<String,Object> preferencias = new LinkedHashMap<String,Object>();
		
		try {
			preferencias = InitCR.preferenciasCR.getAllPreferecesForNode("listaregalos");
			montoMinimoLG = Integer.parseInt(preferencias.get("MontoMinimoLG").toString());
			costoUT = Double.valueOf(preferencias.get("CostoUT").toString()).doubleValue();
			montoMinimoAperturaLG = montoMinimoLG * costoUT;
			diasAperturaLG = Integer.parseInt(preferencias.get("MaxDiasAperturaLG").toString());
			dbPdtUrlServidor = preferencias.get("DbPdtUrlServidor").toString();
			dbPdtUsuario = preferencias.get("DbPdtUsuario").toString();
			dbPdtClave = preferencias.get("DbPdtClave").toString();
		} catch (NoSuchNodeException e) {
			e.printStackTrace();
		} catch (UnidentifiedPreferenceException e) {
			e.printStackTrace();
		}	
	}

	/**
	 * Completa el cierre de una lista de regalos
	 * 
	 * @return
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public void cerrarLista(char estado,Vector<DetalleTransaccion> detallesVenta) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ExcepcionCr, ConexionExcepcion, PrinterNotConnectedException {
		this.fechaCierre = Sesion.getFechaSistema();
		this.horaFin = Sesion.getHoraSistema();
		this.numCajaCierre = Sesion.getCaja().getNumero();
		this.codCajeroCierre = this.codCajero;
		this.codTiendaCierre = Sesion.getTienda().getNumero();
		double montoTrans = CR.meVenta.consultarMontoTrans(false);	
		if(CR.meVenta.getVenta().getDetallesTransaccion().size()>0)
			CR.meVenta.finalizarVentaListaRegalos(CR.meServ.getListaRegalos().getTitular().getNombreCompleto(),"");

		BaseDeDatosServicio.cerrarListaRegalos(this,estado,detallesVenta);
		// Se vuelve a cargar la lista para actualizar los movimientos
		// del cierre.
		refrescarLista();
		// Se imprimen los reportes de cierre
		imprimirReporteCierreLista();
		//imprimirNotaDespacho(detallesVenta);
		imprimirComprobanteBonoRegalo(montoTrans);
		imprimirAbonosAnteriores();
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector' y 'Enumeration'
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unchecked")
	private void refrescarLista() throws BaseDeDatosExcepcion, ConexionExcepcion, ProductoExcepcion {
		detallesServicio=new Vector<DetalleServicio>();
		operaciones=new Vector<OperacionLR>();
		montoAbonosTotales=0;
		montoAbonosLista=0;
		montoAbonosProds=0;
		montoVendidos=0;
		
		if(codTiendaApertura!=codTiendaCierre) {
			IXMLElement listaxml = BaseDeDatosServicio.obtenerListaRegalosRemota(String.valueOf(numServicio),codTiendaApertura);

			String nombre = listaxml.getFullName();
			if(nombre.equals("respuesta")) {
				String tipo = listaxml.getAttribute("tipo",null);
				if(tipo.equals("listaregalos")){
					IXMLElement cabecera = listaxml.getFirstChildNamed("cabecera");
					try {
						this.fechaCierre = new SimpleDateFormat("yyyy-MM-dd").parse(cabecera.getFirstChildNamed("fechacierre").getContent());
					} catch (ParseException e) {
						this.fechaCierre = new Date();
					}						
					this.codTiendaCierre = Integer.parseInt(cabecera.getFirstChildNamed("numtiendacierre").getContent());
					this.numCajaCierre = Integer.parseInt(cabecera.getFirstChildNamed("numcajacierre").getContent());
					this.codCajeroCierre = cabecera.getFirstChildNamed("codcajerocierre").getContent();
					this.montoBase = Double.parseDouble(cabecera.getFirstChildNamed("montobase").getContent());
					this.montoImpuesto = Double.parseDouble(cabecera.getFirstChildNamed("montoimpuesto").getContent());
					this.totalPedidos = Integer.parseInt(cabecera.getFirstChildNamed("cantproductos").getContent());
					this.montoAbonosLista = Double.parseDouble(cabecera.getFirstChildNamed("montoabonoslista").getContent());
				
					// Se extraen los detalles
					IXMLElement detallesxml = listaxml.getFirstChildNamed("detalles");
					Enumeration<IXMLElement> enumdetalles = detallesxml.enumerateChildren();
					while(enumdetalles.hasMoreElements()){
						Enumeration<IXMLElement> enumdetalle = ((IXMLElement)enumdetalles.nextElement()).enumerateChildren();
						while(enumdetalle.hasMoreElements()){
							String codProd = ((IXMLElement)enumdetalle.nextElement()).getContent();
							enumdetalle.nextElement(); //correlativoitem
							int cantidad = Integer.parseInt(((IXMLElement)enumdetalle.nextElement()).getContent());
							double precioRegular = Double.parseDouble(((IXMLElement)enumdetalle.nextElement()).getContent());
							double precioFinal = Double.parseDouble(((IXMLElement)enumdetalle.nextElement()).getContent());
							double mtoImpuesto = Double.parseDouble(((IXMLElement)enumdetalle.nextElement()).getContent());
							String tipoCap = ((IXMLElement)enumdetalle.nextElement()).getContent();
							enumdetalle.nextElement(); //codPromo
							int cantComprado = Integer.parseInt(((IXMLElement)enumdetalle.nextElement()).getContent());
							int cantCancelados = Integer.parseInt(((IXMLElement)enumdetalle.nextElement()).getContent());
							double abonos = Double.parseDouble(((IXMLElement)enumdetalle.nextElement()).getContent());
							this.fechaServicio = Sesion.getFechaSistema();
							this.horaInicia = Sesion.getHoraSistema();
		
							try {
								this.detallesServicio.addElement(new DetalleServicio(codProd,tipoCap,cantidad,this.fechaApertura,
										this.horaInicia,precioRegular,precioFinal,mtoImpuesto,cantComprado,cantCancelados,abonos));
							} catch (ProductoExcepcion e) {
								e.printStackTrace();
								throw new ProductoExcepcion("Producto Inexistente");
							} catch (BaseDeDatosExcepcion e) {
								e.printStackTrace();
							} catch (ConexionExcepcion e) {
								e.printStackTrace();
							}
						}
					}
							
					// Se extraen las operaciones sobre la lista
					operaciones = new Vector<OperacionLR>();
					IXMLElement operacionesxml = listaxml.getFirstChildNamed("operaciones");
					Enumeration<IXMLElement> enumoperaciones = operacionesxml.enumerateChildren();
					while(enumoperaciones.hasMoreElements()){
						Enumeration<IXMLElement> enumoperacion = ((IXMLElement)enumoperaciones.nextElement()).enumerateChildren();
						while(enumoperacion.hasMoreElements()){
							int numOperacion = Integer.parseInt(((IXMLElement)enumoperacion.nextElement()).getContent());
							String numTransTemp = ((IXMLElement)enumoperacion.nextElement()).getContent();
							int numTransaccion = numTransTemp==null
											   ? 0
											   : Integer.parseInt(numTransTemp);							String codProd = ((IXMLElement)enumoperacion.nextElement()).getContent();
							String descripcion = ((IXMLElement)enumoperacion.nextElement()).getContent();
							String nomCliente = ((IXMLElement)enumoperacion.nextElement()).getContent();
							double montoBase = Double.parseDouble(((IXMLElement)enumoperacion.nextElement()).getContent());
							double montoImpuesto = Double.parseDouble(((IXMLElement)enumoperacion.nextElement()).getContent());
							int cantidad = Integer.parseInt(((IXMLElement)enumoperacion.nextElement()).getContent());
							String fechaString = ((IXMLElement)enumoperacion.nextElement()).getContent();
							String dedicatoria = ((IXMLElement)enumoperacion.nextElement()).getContent();
							char tipoOper = ((IXMLElement)enumoperacion.nextElement()).getContent().toCharArray()[0];
							int numTienda = Integer.parseInt(((IXMLElement)enumoperacion.nextElement()).getContent());
							String codCajero = ((IXMLElement)enumoperacion.nextElement()).getContent();
									
							Date fecha = new Date();
							try {
								fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaString);
							} catch (ParseException e) { }
											
							this.operaciones.addElement(new OperacionLR(numOperacion,numTransaccion,codProd,descripcion,nomCliente,
												montoBase,montoImpuesto,cantidad,fecha,dedicatoria,tipoOper,numTienda,codCajero));
						}
					}
				}
								
				// Se cargan los demás vectores importantes
				cargarDetallesVendidos();
				cargarDetallesNoVendidos();
				cargarDetallesAbonados();
			}
		} else {
			Vector<?> lista = BaseDeDatosServicio.obtenerListaRegalosCerrada(String.valueOf(numServicio));
			Vector<Object> cabecera = (Vector<Object>)lista.elementAt(0);
			Vector<Vector<Object>> detallesServicio = (Vector<Vector<Object>>)lista.elementAt(1); 
			Vector<Vector<Object>> detalleOper = (Vector<Vector<Object>>)lista.elementAt(2);
	
			// Se extrae la cabecera
			this.fechaCierre = (Date)cabecera.get(10);
			this.codTiendaCierre = ((Integer)cabecera.get(11)).intValue();
			this.numCajaCierre = ((Integer)cabecera.get(12)).intValue();
			this.codCajeroCierre = (String)cabecera.get(13);
			this.montoBase = ((Double)cabecera.get(14)).doubleValue();
			this.montoImpuesto = ((Double)cabecera.get(15)).doubleValue();
			this.totalPedidos = ((Integer)cabecera.get(16)).intValue();
			this.montoAbonosLista = ((Double)cabecera.get(17)).doubleValue();

			// Se extraen los detalles
			for(int i=0;i<detallesServicio.size();i++){
				Vector<Object> detalle = detallesServicio.get(i);
				int j=0;
				j++; //codLista
				String codProd = (String)detalle.get(j++);
				j++; //correlativoitem
				int cantidad = ((Integer)detalle.get(j++)).intValue();
				double precioRegular = ((Double)detalle.get(j++)).doubleValue();
				double precioFinal = ((Double)detalle.get(j++)).doubleValue();
				j++; //mtoImpuesto
				String tipoCap = (String)detalle.get(j++);
				j++; //codPromo 
				int cantComprado = ((Integer)detalle.get(j++)).intValue();
				int cantCancelados = ((Integer)detalle.get(j++)).intValue();
				double abonos = ((Double)detalle.get(j++)).doubleValue();

				try {
					this.detallesServicio.addElement(new DetalleServicio(codProd,tipoCap,cantidad,this.fechaApertura,
							this.horaInicia,precioRegular,precioFinal,montoImpuesto,cantComprado, cantCancelados, abonos));
				} catch (ProductoExcepcion e) {
					e.printStackTrace();
				} catch (BaseDeDatosExcepcion e) {
					e.printStackTrace();
				} catch (ConexionExcepcion e) {
					e.printStackTrace();
				}
			}

			// Se extraen las operaciones sobre la lista
			for(int i=0;i<detalleOper.size();i++){
				Vector<Object> operacion = detalleOper.get(i);
				int j=0;
				int numOperacion = ((Integer)operacion.get(j++)).intValue();
				int numTransaccion = ((Integer)operacion.get(j++)).intValue();
				String codProd = (String)operacion.get(j++);
				String descripcion = (String)operacion.get(j++);
				String nomCliente = (String)operacion.get(j++);
				double montoBase = ((Double)operacion.get(j++)).doubleValue();
				double montoImpuesto = ((Double)operacion.get(j++)).doubleValue();
				int cantidad = ((Integer)operacion.get(j++)).intValue();
				String fechaString = new SimpleDateFormat("yyyy-MM-dd").format((Date)operacion.get(j++));
				String dedicatoria = (String)operacion.get(j++);
				char tipoOper = ((Character)operacion.get(j++)).charValue();
				int numTienda = ((Integer)operacion.get(j++)).intValue();
				String codCajero = (String)operacion.get(j++);
				
				Date fecha = new Date();
				try {
					fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaString);
				} catch (ParseException e) { }
		
				this.operaciones.addElement(new OperacionLR(numOperacion,numTransaccion,codProd,descripcion,nomCliente,
									montoBase,montoImpuesto,cantidad,fecha,dedicatoria,tipoOper,numTienda,codCajero));
			}
			
			// Se cargan los demás vectores importantes
			cargarDetallesVendidos();
			cargarDetallesNoVendidos();
			cargarDetallesAbonados();
		}
	}

	/**
	 * Completa el registro de una lista de regalos
	 * 
	 * @return
	 */
	public void registrarListaRegalos() throws ExcepcionCr{
		Sesion.setUbicacion("LISTA DE REGALOS","registrarListaRegalos");
		
		if(this.tipoLista == GARANTIZADA)
			if(montoBase < montoMinimoAperturaLG)
				throw new ExcepcionLR("El monto mínimo para una\n Lista Garantizada es "+Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meServ.getListaRegalos().getMontoAperturaLG())));
		
		Auditoria.registrarAuditoria("Registro de Lista de Regalos " + this.numServicio + " de Tienda " + this.codTienda,'T');
		
		//Registramos el servicio
		this.lineasFacturacion = this.detallesServicio.size();
		this.horaFin = Sesion.getHoraSistema();
		this.fechaApertura = Sesion.getFechaSistema();
		this.numCajaApertura = Sesion.getCaja().getNumero();
		this.codTiendaApertura = Sesion.getTienda().getNumero();
		try {
			this.numServicio = BaseDeDatosServicio.registrarListaRegalos(this);
			imprimirReporteLista(true);
		} catch(BaseDeDatosExcepcion e) {
			try {
				BaseDeDatosServicio.registrarClienteTemporal(this.cliente,false);
				this.numServicio = BaseDeDatosServicio.registrarListaRegalos(this);
				imprimirReporteLista(true);
			} catch (BaseDeDatosExcepcion e1) {
				throw new BaseDeDatosExcepcion("No se pudo registrar la lista de regalos.\nNo se puede registrar el nuevo afiliado.");
			} catch (AfiliadoUsrExcepcion e1) {
				throw new BaseDeDatosExcepcion("No se puede registrar el nuevo afiliado \npara la lista de regalos.");
			} catch (ConexionExcepcion e1) {
				throw new BaseDeDatosExcepcion("No se pudo registrar la lista de regalos.\nNo se puede registrar el nuevo afiliado.");
			}
		}
	}
	
	/**
	 * Completa la venta de un producto de una lista de regalos
	 * 
	 * @return
	 */
	public void registrarVenta(int numTransaccion,double montoVenta, Cliente cliente, 
							String nomInvitado,String dedicatoria,Vector<DetalleTransaccion> detallesVenta) throws ConexionExcepcion {
								
		Auditoria.registrarAuditoria("Venta de Lista de Regalos " + this.numServicio + " de Tienda " + this.codTienda,'T');
		int codTienda = Sesion.getTienda().getNumero();
		int numCaja = Sesion.getCaja().getNumero();
		this.nombreInvitado = nomInvitado;
		this.dedicatoria = dedicatoria;
		int codTiendaOrigen = this.codTiendaApertura;		

		if(cliente.getCodCliente() != null)
			BaseDeDatosServicio.registrarVentaLR(numTransaccion,codTienda,codTiendaOrigen,numCaja,codCajero,this.numServicio,
									cliente.getCodCliente(),cliente.getNombreCompleto(),dedicatoria,detallesVenta);
		else
			BaseDeDatosServicio.registrarVentaLR(numTransaccion,codTienda,codTiendaOrigen,numCaja,codCajero,this.numServicio,
									"",nombreInvitado,dedicatoria,detallesVenta);
	}

	/**
	 * Agrega un abono parcial a un producto de una lista de regalos
	 * Si montoAbono es igual al precio del producto, se asume un abono total
	 * 
	 * @param montoAbono
	 * @param renglon
	 */
	public void agregarAbono(double montoAbono, int renglon) throws ExcepcionLR{
		boolean existe = false;
		DetalleServicio detalle = (DetalleServicio)this.getDetallesServicio().get(renglon);

		int cantActual = 0;
		double montoActual = 0;
		
		// Se suma la cantidad de abonos totales y monto de abonos parciales existentes en detalleAbonos
		for(int i=0;i<detalleAbonos.size();i++) {
			Abono abono = (Abono)detalleAbonos.get(i);
			if(abono.getTipoTransAbono()!='L' && abono.getProducto().getCodProducto().equals(detalle.getProducto().getCodProducto())){ 
				cantActual += abono.getCantidad();
				montoActual += abono.getMontoBase();
			}
		}
		double precio = detalle.getPrecioFinal()+detalle.getMontoImpuesto();
		
		if(detalle.getCantRestantes()<=0){
			throw new ExcepcionLR("No se le pueden realizar más abonos a este producto.");
		}
				
		if(montoAbono - precio > 1){
			throw new ExcepcionLR("No puede realizar un abono parcial \npor un monto mayor al precio del producto");
		}
		
		if((montoAbono+montoActual)/precio>detalle.getCantRestantes()){
			throw new ExcepcionLR("No se puede realizar este abono \nintente un monto menor");
		}
		
		for(int i=0;i<detalleAbonos.size();i++){
			Abono abono = (Abono)detalleAbonos.get(i);
			if(abono.getProducto() != null) {
				String codProd = abono.getProducto().getCodProducto();
				if(codProd.equals(detalle.getProducto().getCodProducto())){
					if(abono.getTipoTransAbono()==OperacionLR.ABONO_PARCIAL){
						abono.incrementarMontoAbonado(montoAbono);
						detalleAbonos.set(i,abono);
						existe = true;
						break;
					}
				}
			}
		}
		if(!existe) {
			Abono nuevoAbono = new Abono(detalle.getProducto(),Sesion.getTienda().getNumero(),Sesion.getCaja().getNumero(),
									Sesion.getFechaSistema(), Sesion.getHoraSistema(),montoAbono, 0,Sesion.getCaja().getUsuarioLogueado());
			nuevoAbono.setTipoTransAbono(OperacionLR.ABONO_PARCIAL);
			nuevoAbono.incrementarCantidad(1);
			detalleAbonos.add(nuevoAbono);
		}
		this.totalAbonos += montoAbono;
	}
	
	/**
	 * Agrega un abono total a un producto
	 * 
	 * @param cantidad Cantidad de productos
	 * @param renglon
	 */
	public void agregarAbono(int cantidad, int renglon) throws ExcepcionLR{
		boolean existe = false;
		DetalleServicio detalle = (DetalleServicio)this.getDetallesServicio().get(renglon);
		double precioItem = detalle.getPrecioFinal();
		double impuestoItem = precioItem*(detalle.getProducto().getImpuesto().getPorcentaje()/100);
		int cantAcum = 0;
		double montoAcum = 0;
		
		// Se suma la cantidad de abonos totales y monto de abonos parciales existentes en detalleAbonos
		for(int i=0;i<detalleAbonos.size();i++) {
			Abono abono = (Abono)detalleAbonos.get(i);
			if(abono.getTipoTransAbono()!='L' && detalle.getProducto().getCodProducto().equals(abono.getProducto().getCodProducto())){
				cantAcum += abono.getCantidad();
				montoAcum += abono.getMontoBase();
			}
		}
		
		if(detalle.getCantRestantes()<=0){
			throw new ExcepcionLR("No se le pueden realizar más abonos a este producto.");
		}

		if(cantAcum+cantidad>detalle.getCantRestantes())
			throw new ExcepcionLR("No se puede realizar este abono total.\nRestan "+((int)detalle.getCantRestantes())+" productos.");

		for(int i=0;i<detalleAbonos.size();i++) {
			Abono abono = (Abono)detalleAbonos.get(i);
			if(abono.getProducto() != null) {
				String codProd = abono.getProducto().getCodProducto();
				if(codProd.equals(detalle.getProducto().getCodProducto())) {
					if(abono.getTipoTransAbono()==OperacionLR.ABONO_TOTAL) {
						//abono.incrementarMontoAbonado((precioItem+impuestoItem)*cantidad);
						abono.incrementarCantidad(cantidad);
						detalleAbonos.set(i,abono);
						existe = true;
						break;
					}
				}
			}
		}
		
		if(!existe) {
			Abono nuevoAbono = new Abono(detalle.getProducto(),Sesion.getTienda().getNumero(),Sesion.getCaja().getNumero(),
									Sesion.getFechaSistema(), Sesion.getHoraSistema(), precioItem, impuestoItem, Sesion.getCaja().getUsuarioLogueado());
			nuevoAbono.setTipoTransAbono(OperacionLR.ABONO_TOTAL);
			nuevoAbono.incrementarCantidad(cantidad);
			detalleAbonos.add(nuevoAbono);
		}
		
		this.totalAbonos += cantidad;
	}
	
	/**
	 * Agrega un abono a una lista de regalos
	 * 
	 * @param montoAbono
	 */
	public void agregarAbono(double montoAbono){
		int i;
		boolean existe = false;

		for(i=0;i<detalleAbonos.size();i++){
			Abono abono = (Abono)detalleAbonos.get(i);
			if(abono.getProducto() == null){
				abono.incrementarMontoAbonado(montoAbono);
				detalleAbonos.set(i,abono);
				existe = true;
				break;
			}
		}
		if(!existe) {
			Abono nuevoAbono = new Abono(null,Sesion.getTienda().getNumero(),Sesion.getCaja().getNumero(),
									Sesion.getFechaSistema(), Sesion.getHoraSistema(),montoAbono, 0,Sesion.getCaja().getUsuarioLogueado());
			nuevoAbono.setTipoTransAbono(OperacionLR.ABONO_LISTA);
			nuevoAbono.incrementarCantidad(1);
			detalleAbonos.add(nuevoAbono);
		}
		this.totalAbonos += montoAbono;
	}
	
	/**
	 * Elimina el vector de detalles de abonos
	 */
	public void eliminarAbono(int renglon){
		detalleAbonos.remove(renglon);
	}
	
	/**
	 * @return Monto total de abonos en detalles
	 */
	public double getMontoAbonos(){
		double monto = 0;
		if(detalleAbonos!=null){
			for(int i=0;i<detalleAbonos.size();i++){
				Abono abono = (Abono)detalleAbonos.get(i);
				monto += (abono.getMontoBase()*abono.getCantidad());
				monto += (abono.getImpuestoProducto()*abono.getCantidad());
			}
		}
		return monto;
	}
	
	/**
	 * @return Monto total de abonos en detalles
	 */
	public int getCantAbonos(){
		int cant = 0;
		if(detalleAbonos!=null){
			for(int i=0;i<detalleAbonos.size();i++){
				Abono abono = (Abono)detalleAbonos.get(i);
				cant += abono.getCantidad();
			}
		}
		return cant;
	}
	
	/**
	 * @return Vector de detalles de abonos
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Abono> getDetalleAbonos(){
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
		int numTransaccion = 0;
		this.nombreInvitado = nomInvitado;
		this.dedicatoria = dedicatoria;
		int codTienda = Sesion.getTienda().getNumero();
		this.horaFin = Sesion.getHoraSistema();
		numTransaccion = BaseDeDatosServicio.registrarAbonoLR(codTienda,codCajero,this,codInvitado,
							nombreInvitado, dedicatoria, detalleAbonos, vuelto);
		
		ManejadorReportesFactory.getInstance().imprimirComprobanteAbono(numTransaccion,vuelto,this);
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
		Sesion.setUbicacion("LISTA DE REGALOS","ingresarProductoLR");
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
		actualizarPreciosDetalle(prod,false);
		actualizarMontoServ();
		
		// Registramos la auditoría de esta función
		Auditoria.registrarAuditoria("Captura de Producto " + codProd + " por " + tipoCaptura + " para Apartado",'T');
		totalPedidos++;
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
		Sesion.setUbicacion("LISTA DE REGALOS", "cambiarCantidad");
		Auditoria.registrarAuditoria("Cambiando cantidad de Renglon " + renglon + ". Agregado(s) " + nvaCantidad + " productos",'T');
		
		Producto pCambiar;
		float cantidadRenglon;
		
		// Observamos si el renglon del producto existe
		try {
			// Obtenemos el Producto a cambiar la cantidad
			pCambiar = ((DetalleServicio)detallesServicio.elementAt(renglon)).getProducto();
		} catch (Exception ex) {
			throw (new ProductoExcepcion("Error al cambiar cantidad de producto, no existe renglon " + renglon + " en la Lista de Regalos", ex));
		}
		
		// Chequeamos  si la cantidad es fraccionada, y si el producto permite cantidades fraccionadas
		if ((!pCambiar.isIndicaFraccion())&&((nvaCantidad % 1)!=0)) {
			throw (new ProductoExcepcion("Error al cambiar cantidad de producto.\nEl producto " + pCambiar.getCodProducto() + " no admite cantidades fraccionadas (Decimales)"));
		}
		
		cantidadRenglon = ((DetalleServicio)detallesServicio.elementAt(renglon)).getCantidad();
		if (nvaCantidad > 0) nvaCantidad = cantidadRenglon + nvaCantidad;
		else throw (new ProductoExcepcion("Error al cambiar cantidades de productos. No se pueden agregar cantidades negativas"));
		((DetalleServicio)detallesServicio.elementAt(renglon)).setCantidad(nvaCantidad);
		((DetalleServicio)detallesServicio.elementAt(renglon)).setCondicionVenta(Sesion.condicionNormal);
						
		actualizarPreciosDetalle(pCambiar,false);
		actualizarMontoServ();
		totalPedidos += (nvaCantidad-cantidadRenglon);
	}
	
	/**
	 * @return Vector de operaciones sobre la lista
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<OperacionLR> getOperaciones(){
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
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Pago> getPagosAbono(){
		return pagos;
	}

	/**
	 * @param pagoAct
	 */
	public double realizarPagoAbono(Pago pagoAct) {
		double resto = totalAbonos - obtenerMontoAbonoPagado();
		if (resto <= 0) {
			this.montoVuelto = MathUtil.roundDouble(-resto);
		}
		return resto;
	}

	/**
	 * @return Monto total pagado en el abono
	 */
	public double obtenerMontoAbonoPagado() {
		double monto = 0.0;
		for (int i=0; i<pagos.size(); i++) {
			monto += ((Pago)pagos.elementAt(i)).getMonto();
		}
		return monto;
	}

	/**
	 * @return Monto total pagado en la operación
	 */
	public double getMontoPagado(){
		double monto = 0.0;
		for (int i=0; i<pagos.size(); i++) {
			monto += ((Pago)pagos.elementAt(i)).getMonto();
		}
		return monto;
	}
	
	
	public void setNombreInvitado(String nombreInvitado){
		this.nombreInvitado = nombreInvitado;
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
	 * Verifica si el producto indicado pertenece a la lista de regalos y si existen cantidades disponibles
	 */
	public boolean perteneceProducto(String codProd, String tipoIngreso){
		String codigo = "";
		if(tipoIngreso.equals(Sesion.capturaEscaner))
			codigo = codProd.substring(0,9);
		else if(tipoIngreso.equals(Sesion.capturaTeclado) && codProd.length() > 3) {
			int longitud = codProd.length();
			codProd = codProd.substring(0,longitud-3);
			while(codProd.length() < 9){
				codProd = "0"+codProd;
			}
			codigo = codProd;
		}
		
		int cantDisponibles = 0;
		
		// Ubicamos el producto en las lineas de la lista y contamos la cantidad disponible para la venta
		for(int i=0;i<detallesServicio.size();i++){
			DetalleServicio detalle = (DetalleServicio)detallesServicio.get(i);
			String cod = detalle.getProducto().getCodProducto().substring(0,9);
			if(codigo.equals(cod)){
				cantDisponibles += detalle.getCantRestantes();
			}
		}

		// Verificamos si el producto se encuentra en el detalle de venta actual
		Venta ventaActual = CR.meVenta.getVenta();
		if (ventaActual != null) {
			for(int i=0;i<ventaActual.getDetallesTransaccion().size();i++){
				DetalleTransaccion detalle = (DetalleTransaccion)ventaActual.getDetallesTransaccion().get(i);
				String cod = detalle.getProducto().getCodProducto().substring(0,9);
				if(codigo.equals(cod)) {
					cantDisponibles -= detalle.getCantidad();
				}
			}
		}

		// retornamos si existe el producto y si además existen cantidades suficientes para vender
		return (cantDisponibles >= 1);
	}

	/**
	 * Guarda las modificaciones realizadas a la lista en la base de datos.
	 */
	public void modificarEncabezadoListaRegalos() throws BaseDeDatosExcepcion, ConexionExcepcion, ExcepcionLR{
		Sesion.setUbicacion("LISTA DE REGALOS","modificarListaRegalos");
		

		Auditoria.registrarAuditoria("Modificando Encabezado de Lista de Regalos " + this.numServicio + " de Tienda " + this.codTiendaApertura,'T');

		BaseDeDatosServicio.modificarEncabezadoListaRegalos(this);
	}

	/**
	 * Guarda las modificaciones realizadas a la lista en la base de datos.
	 */
	public void modificarDetallesListaRegalos() throws BaseDeDatosExcepcion, ConexionExcepcion, ExcepcionLR{
		Sesion.setUbicacion("LISTA DE REGALOS","modificarListaRegalos");
		
		if(this.tipoLista == GARANTIZADA){
			if(montoBase < montoMinimoAperturaLG)
				throw new ExcepcionLR("El monto mínimo para una\n Lista Garantizada es "+Sesion.getTienda().getMonedaBase()+" "+ String.valueOf(df.format(CR.meServ.getListaRegalos().getMontoAperturaLG())));
		}
		
		Auditoria.registrarAuditoria("Modificando Detalles de Lista de Regalos " + this.numServicio + " de Tienda " + this.codTiendaApertura,'T');

		BaseDeDatosServicio.modificarDetallesListaRegalos(this);
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se hizo el cast en la llamada a 'clone'
	* Fecha: agosto 2011
	*/
	@SuppressWarnings("unchecked")
	public Object clone(){
		ListaRegalos lista=null;
		try {
			lista = (ListaRegalos)super.clone();
		} catch(CloneNotSupportedException ex) { }
		lista.detallesServicio = (Vector<DetalleServicio>) lista.detallesServicio.clone();
		for(int i=0;i<detallesServicio.size();i++){
			DetalleServicio detalle = ((DetalleServicio)lista.detallesServicio.get(i));
			lista.detallesServicio.set(i,(DetalleServicio)detalle.clone());
		}
		return lista;
	}
	/**
	 * @return Cantidad de productos pedidos
	 */
	public int getCantPedidos() {
		return totalPedidos;
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
		this.asignarCliente(codigo,autorizante);
		this.titular = this.cliente;
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
		DetalleServicio det = null;
		double precio = 0;
		
		for(int i=0;i<detallesServicio.size();i++){
			det = (DetalleServicio)detallesServicio.get(i);
			
			if(codProd.equals(det.getProducto().getCodProducto())){
				precio = det.getProducto().getPrecioRegular();
				break;
			}
		}
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
		for(int i=0;i<detallesServicio.size();i++){
			det = (DetalleServicio)detallesServicio.get(i);
			if(det.getProducto().getCodProducto().equals(codProd))
				break;
		}
		return det;
	}

	public Vector<DetalleServicio> getDetalleVendidos(){
		if(detalleVendidos == null){
			cargarDetallesVendidos();
		}
		return detalleVendidos;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<DetalleServicio> getDetalleAbonadosTotales(){
		if(detalleAbonosTotales == null){
			cargarDetallesAbonados();
		}
		return detalleAbonosTotales;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private void cargarDetallesAbonados() {
		Set<DetalleServicio> conjAbonosTotales = new HashSet<DetalleServicio>();
		detalleAbonosTotales = new Vector<DetalleServicio>();
		operacionesAbonosTotales = new Vector<OperacionLR>();
		operacionesAbonosParciales = new Vector<OperacionLR>();
		operacionesAbonosLista = new Vector<OperacionLR>();
		
		for(int i=0;i<operaciones.size();i++){
			OperacionLR op = (OperacionLR)operaciones.get(i);
			if(op.getTipoOper()==OperacionLR.ABONO_TOTAL){
				conjAbonosTotales.add(this.getDetalle(op.getCodProducto()));
				operacionesAbonosTotales.add(op);
				cantAbonadosTotales += op.getCantidad();
				montoAbonosTotales += (op.getMontoBase()+op.getMontoImpuesto()) * op.getCantidad();
				montoAbonosProds += (op.getMontoBase()+op.getMontoImpuesto()) * op.getCantidad();
			}else if(op.getTipoOper()==OperacionLR.ABONO_PARCIAL){
				operacionesAbonosParciales.add(op);
				montoAbonosProds += op.getMontoBase();
			}else if(op.getTipoOper()==OperacionLR.ABONO_LISTA){
				operacionesAbonosLista.add(op);
				//montoAbonosLista lo toma directamente de la cabecera de la lista
			}
			
		}
		Iterator<DetalleServicio> it = conjAbonosTotales.iterator();
		while(it.hasNext()){
			detalleAbonosTotales.add((DetalleServicio)it.next());
		}
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<OperacionLR> getOperacionesAbonosTotales(){
		if(operacionesAbonosTotales == null){
			cargarDetallesAbonados();
		}
		return operacionesAbonosTotales;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<OperacionLR> getOperacionesAbonosParciales(){
		if(operacionesAbonosParciales == null){
			cargarDetallesAbonados();
		}
		return operacionesAbonosParciales;
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<OperacionLR> getOperacionesAbonosLista(){
		if(operacionesAbonosLista == null){
			cargarDetallesAbonados();
		}
		return operacionesAbonosLista;
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private void cargarDetallesNoVendidos(){
		operacionesNoVendidos = new Vector<DetalleServicio>();
		for(int i=0;i<detallesServicio.size();i++){
			DetalleServicio det = (DetalleServicio)detallesServicio.get(i);
			if(det.getCantPedidos()-det.getCantVendidos()-det.getCantAbonadosT()>0)
				operacionesNoVendidos.add(det);
		}	
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<DetalleServicio> getDetalleNoVendidos(){
		if(operacionesNoVendidos == null){
			cargarDetallesNoVendidos();
		}
		return operacionesNoVendidos;
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	private void cargarDetallesVendidos(){
		detalleVendidos = new Vector<DetalleServicio>();
		operacionesVendidos = new Vector<OperacionLR>();
		for(int i=0;i<operaciones.size();i++){
			OperacionLR op = (OperacionLR)operaciones.get(i);
			if(op.getTipoOper()==OperacionLR.VENTA){
				detalleVendidos.add(this.getDetalle(op.getCodProducto()));
				operacionesVendidos.add(op);
				cantVendidos += op.getCantidad();
				montoVendidos += ((op.getMontoBase() + op.getMontoImpuesto()) * op.getCantidad());
			}
		}
	}

	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<OperacionLR> getOperacionesVendidos(){
//		Vector operacionesVendidos = new Vector();
//		for(int i=0;i<operaciones.size();i++){
//			OperacionLR op = (OperacionLR)operaciones.get(i);
//			if(op.getTipoOper()==OperacionLR.VENTA){
//				operacionesVendidos.add(op);
//			}
//		}
		if(operacionesVendidos==null)
			cargarDetallesVendidos();
		return operacionesVendidos;
	}

	/**
	 * 
	 */
	public void guardarVentaParcialCierre(Venta venta, int etapa) throws UsuarioExcepcion, MaquinaDeEstadoExcepcion, PagoExcepcion, XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion{
		if(etapa==1)
			this.ventaParcialCierre1 = (Venta)venta.clone();
		else if(etapa==2){
			this.ventaParcialCierre2 = (Venta)venta.clone();
			CR.meServ.getListaRegalos().concatenarVentasParciales1y2();
		} else if(etapa==3)
			this.ventaParcialCierre3 = (Venta)venta.clone();
	}
	/**
	 * @return
	 */
	public Venta getVentaParcialDeCierre1() {
		return ventaParcialCierre1;
	}

	/**
	 * @return
	 */
	public Venta getVentaParcialDeCierre2() {
		return ventaParcialCierre2;
	}
	
	/**
	 * @return
	 */
	public Venta getVentaParcialDeCierre3() {
		return ventaParcialCierre3;
	}

	/**
	 * Constructor de la clase Venta que crea una nueva basandose en la que se le pasa como parámetro pero con 
	 * sus nuevos números de registros.
	 */
	public void concatenarVentasParciales1y2() throws XmlExcepcion, FuncionExcepcion, BaseDeDatosExcepcion, ConexionExcepcion {		
		ventasParciales12 = new Venta();
		for (int i=0; i<ventaParcialCierre1.getDetallesTransaccion().size(); i++) {
			DetalleTransaccion detalleActual = (DetalleTransaccion)ventaParcialCierre1.getDetallesTransaccion().elementAt(i);
			ventasParciales12.getDetallesTransaccion().add(new DetalleTransaccion(null, detalleActual.getProducto(), 
					detalleActual.getCantidad(), detalleActual.getCondicionVenta(), detalleActual.getCodSupervisor(),
					detalleActual.getPrecioFinal(), detalleActual.getMontoImpuesto(), detalleActual.getTipoCaptura(),
					detalleActual.getCodPromocion(), detalleActual.getTipoEntrega()));
		}
		for (int i=0; i<ventaParcialCierre2.getDetallesTransaccion().size(); i++) {
			DetalleTransaccion detalleActual = (DetalleTransaccion)ventaParcialCierre2.getDetallesTransaccion().elementAt(i);
			ventasParciales12.getDetallesTransaccion().add(new DetalleTransaccion(null, detalleActual.getProducto(), 
					detalleActual.getCantidad(), detalleActual.getCondicionVenta(), detalleActual.getCodSupervisor(),
					detalleActual.getPrecioFinal(), detalleActual.getMontoImpuesto(), detalleActual.getTipoCaptura(),
					detalleActual.getCodPromocion(), detalleActual.getTipoEntrega()));
		}
		ventasParciales12.actualizarMontoTransaccion();
		//ventasParciales12.actualizarPreciosDetalle(false);
	}
	
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<DetalleTransaccion> getVentasParciales1y2(){
		return ventasParciales12.getDetallesTransaccion();
	}

	/**
	 * @param codprod
	 * @return
	 */
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<Abono> obtenerAbonos(String codprod) {
		Vector<Abono> renglonesAbonos = new Vector<Abono>();
		if(detalleAbonos!=null){
			for(int i=0;i<detalleAbonos.size();i++){
				Abono abono = (Abono)detalleAbonos.get(i);
				if(abono.getProducto()!=null && abono.getProducto().getCodProducto().equals(codprod))
					renglonesAbonos.add(abono);
			}
		}
		return renglonesAbonos;
	}

	/**
	 * @return
	 */
	public int getCantRestantes() {
		int cantRestantes = totalPedidos>(cantVendidos+cantAbonadosTotales)
							 ? totalPedidos-cantVendidos-cantAbonadosTotales
							 : 0;
		return cantRestantes;
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
	public double getMontoAbonadosTotales() {
		return montoAbonosTotales;
	}

	/**
	 * @return
	 */
	public double getMontoRestantes() {
		double montoRestantes = (montoBase + montoImpuesto) > (montoVendidos+montoAbonosTotales)
							  ? (montoBase + montoImpuesto) - montoVendidos - montoAbonosTotales
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
	/*
	* En esta función se realizaron modificaciones referentes a la migración a java 1.6 por jperez
	* Sólo se parametrizó el tipo de dato contenido en los 'Vector'
	* Fecha: agosto 2011
	*/
	public Vector<String[]> ingresarLoteProductos(String numLote) throws ConexionExcepcion, ExcepcionCr {
		Vector<String[]> loteProductos = BaseDeDatosServicio.obtenerLoteProductos(numLote);
		Vector<String[]> noEncontrados = new Vector<String[]>();
		
		for(int i=0;i<loteProductos.size();i++){
			String[] producto = (String[])loteProductos.get(i);
			String codProducto = producto[0].substring(0,producto[0].length()-1);
			int cantidad = Integer.parseInt(producto[1]); 
			try{
				this.ingresarLineaProducto(codProducto,"E");
				if(cantidad > 1)
					this.agregarCantidad(cantidad-1,detallesServicio.size()-1);
			}catch(ProductoExcepcion e){
				if(e.getMensaje().equals("Código no registrado")){
					noEncontrados.add(producto);
					loteProductos.set(i,null);
				}
			}
		}
		
		// Si no se pueden agregar todos los productos se revierten los cambios
		if(noEncontrados.size()>0){
			for(int i=0;i<loteProductos.size();i++){
				if(loteProductos.get(i)!=null){
					String[] producto = (String[])loteProductos.get(i);
					String codigo = producto[0].substring(0,producto[0].length()-1);
					float cantidad = Float.parseFloat(((String[])loteProductos.get(i))[1]);
					Vector<Integer> renglones = this.obtenerRenglones(codigo, true);
					
					for(int j=0;j<renglones.size();j++){
						int renglon = ((Integer)renglones.get(j)).intValue();
						DetalleServicio detalle = (DetalleServicio)this.detallesServicio.get(renglon);
						float cantdetalle = (float)detalle.getCantidad();
						if(cantdetalle<cantidad){
							this.anularProducto(renglon,cantdetalle);
							cantidad -= cantdetalle;
						}else{
							this.anularProducto(renglon,cantidad);
						}
						if(cantidad==0)
							break;
					}
				}				
			}
		}
		return noEncontrados;
	}

	/**
	 * 
	 */
	public void imprimirReporteLista(boolean apertura) throws ExcepcionCr {
		if(apertura)
			PrintUtilities.imprimirReporteAperturaLista(this);
		else
			PrintUtilities.imprimirReporteLista(this);
	}

	/**
	 * 
	 */
	private void imprimirReporteCierreLista() throws ExcepcionCr {
		PrintUtilities.imprimirReporteCierreLista(this);
	}
	
	/**
	 * 
	 */
	public void imprimirPrecierre() throws ExcepcionCr {
		PrintUtilities.imprimirPrecierre(this);
	}
	
	/**
	 * 
	 */	
	private void imprimirComprobanteBonoRegalo(double montoTrans) throws ExcepcionCr {
		// Cabiada logica de manejo de multiplos de 5 para entregas de Bono Regalo
		// Se sustituye por una tarjeta electróncia
		double montoAbonos = this.getMontoAbonosLista()+this.getMontoAbonosProds();
		if(montoTrans<montoAbonos) {
			double montoBonos = montoAbonos-montoTrans;
			boolean reintentar = false;
			do {
				// Llamada al proceso de venta de bono regalo por el saldo a favor del cliente
				try {
					CR.meServ.realizarVentaBR("cierre de Lista", montoBonos, this.getTitular(), true);
					reintentar = false;
				} catch (Exception e) {
					int respuesta = MensajesVentanas.preguntarSiNo("Problema recargando tarjeta de bono regalo (Cierre)\n¿Desea intentar nuevamente la carga por " + montoBonos);
					if (respuesta == 0) {
						reintentar = true;
					}
					logger.error(e.getMessage());
				}
			} while (reintentar);
			PrintUtilities.imprimirComprobanteBonoRegalo(this,montoTrans,montoBonos,0);
		}
		if(tipoLista==NOGARANTIZADA) {
			double montoBonos = montoVendidos * 0.05;
			if (montoVendidos>0) {
				boolean reintentar = false;
				do {
					// Llamada al proceso de venta de bono regalo por el saldo a favor del cliente
					try {
						CR.meServ.realizarVentaBR("Obsequio Lista No Garantizada", montoBonos, this.getTitular(), true);
						reintentar = false;
					} catch (Exception e) {
						int respuesta = MensajesVentanas.preguntarSiNo("Problema recargando tarjeta de bono regalo (Obsequio)\n¿Desea intentar nuevamente la carga por " + montoBonos);
						if (respuesta == 0) {
							reintentar = true;
						}
						logger.error(e.getMessage());
					}
				} while (reintentar);
				
			}
			PrintUtilities.imprimirComprobanteBonoRegaloPromo(this,montoBonos);
		}
	}

	/**
	 * 
	 */
	private void imprimirAbonosAnteriores() {
		ManejadorReportesFactory.getInstance().imprimirAbonosAnterioresLR(this);
	}

	/**
	 * 
	 */
	public String getEstadoLista() {
		return BaseDeDatosServicio.getEstadoListaRegalos(this.numServicio);
	}
	
	/**
	 * 
	 */
	public void setEstadoLista(char estado) throws BaseDeDatosExcepcion, ConexionExcepcion {
		BaseDeDatosServicio.setEstadoListaRegalos(this.numServicio,estado);
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
	private boolean recalcularPromociones() {
		if (logger.isDebugEnabled()) {
			logger.debug("recalculadoPromocionesApartado() - start");
		}
		boolean recalculado = false;
		double montoImpuesto;	

		Vector<DetalleServicio> detalleAnterior = new Vector<DetalleServicio>();//(Vector)detallesServicio.clone();
		for (int i=0; i<detallesServicio.size(); i++) {
			DetalleServicio detalle = (DetalleServicio)detallesServicio.elementAt(i);
			//detalleAnterior.addElement(new DetalleServicio(detalle.getProducto(),detalle.getCantidad(),detalle.getCondicionVenta(),detalle.getCodSupervisor(),detalle.getPrecioFinal(),detalle.getMontoImpuesto(),detalle.getTipoCaptura(),detalle.getCodPromocion(),detalle.getTipoEntrega()));
			detalleAnterior.addElement((DetalleServicio)detalle.clone());
			if ((detalle.getProducto().getPromociones().size()>0)/*&&(this.recalcularSaldo)*/) {
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
						Auditoria.registrarAuditoria("Recalculando precio de detalle de Lista de Regalos. Nueva promoción aplicada al producto " + detalle.getProducto().getCodProducto(), 'T');
						detalle.setCodPromocion(prom.getCodPromocion());
						detalle.setCondicionVenta(Sesion.condicionPromocion);
						detalle.setPrecioFinal(pFinal);
						if ((detalle.getMontoImpuesto()==0)&&(this.getTitular().isExento())) {
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
							Auditoria.registrarAuditoria("Recalculando precio de detalle de Lista de Regalos. Nueva promoción aplicada al producto " + detalle.getProducto().getCodProducto(), 'T');
							detalle.setCodPromocion(prom.getCodPromocion());
							detalle.setCondicionVenta(Sesion.condicionPromocion);
							detalle.setPrecioFinal(precioProm);
							if ((detalle.getMontoImpuesto()==0)&&(this.getTitular().isExento())) {
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
								if ((detalle.getMontoImpuesto()==0)&&(this.getTitular().isExento())) {
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
	
	public int getPorcentajeVendidoAbonado(){
		return (int)Math.round(((montoVendidos + montoAbonosLista + montoAbonosProds) / (montoBase + montoImpuesto)) * 100);
	}
}
